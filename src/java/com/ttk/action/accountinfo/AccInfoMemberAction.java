/**
 * @ (#) AccInfoMemberAction.java July 26th, 2007
 * Project 		: Vidal Health TPA Services
 * File 		: AccInfoMemberAction.java
 * Author 		: Raghavendra T M
 * Company 		: Span Systems Corporation
 * Date Created : July 26th, 2007
 *
 * @author 		: Raghavendra T M
 * Modified by 	: Mohan
 * Modified date: 
 * Reason 		:  TEST
 */

package com.ttk.action.accountinfo;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.accountinfo.AccountInfoManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.PolicyAccInfoWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.preauth.PreAuthVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used to search for members,add/edit member details,approve for card printing,
 *  validate the defined rules for each family in enrollment and teendorsement flow.
 * This class also provides option for deleting the enrollment.test12
 */


public class AccInfoMemberAction extends TTKAction
{
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private  static final String strViewAccInfoEnrollment="doViewAccInfoEnrollment";
	
	// For sub link name
	private static final String strEnrollment="Enrollment";
	
	// For policy type
	private static final String strIndividualPolicy="IND";
	private static final String strIndPolicyasGroup="ING";
	private static final String strCorporatePolicy="COR";
	private static final String strNonCorporatePolicy="NCR";
	
	private static final String strMemberdetails="memberdetails";
	private static final String strPreauthHistoryList = "preauthHistoryList";
	private  static final String StrCorporate="corporate";
	private  static final String StrNONCorporate="noncorporate";
	
	private static final String strClaimViewHistory = "claimViewHistory";
	private static final String strPreAuthViewHistory = "preauthViewHistory";
	
	
	
	//declaration of constants specifying the position of icons to be disabled based on condtions and permissions
	private static final int R00T_CHANGEPWD_ICON=0;
	
	private static Logger log = Logger.getLogger( AccInfoMemberAction.class );
	/**
	 * This method is used to initialize the search grid.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try
		{
			log.debug("Inside doDefault method of AccInfoMemberAction");
			this.setLinks(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath=getForwardPath(strActiveSubLink,request);
			String path = "Account Info.Enrollment.Members.History";
			HttpSession session = request.getSession();
			String reforward = request.getParameter("reforward");
			DynaActionForm frmMember=(DynaActionForm)form;
			frmMember.set("display",null);//this attribute is used in JSP to show the error message.
			if(PolicyAccInfoWebBoardHelper.checkWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				frmMember.set("display","display");//this attribute is used in JSP to show the error message.
				request.getSession().setAttribute("treeData",null);
				expTTK.setMessage("error.enrollment.required");
				throw expTTK;
			}//end of if(WebBoardHelper.checkWebBoardId(request)==null)
			String strPolicyType = PolicyAccInfoWebBoardHelper.getPolicyTypeID(request);
			Long strMemberSeqID = PolicyAccInfoWebBoardHelper.getMemberSeqId(request);
			request.getSession().setAttribute("memberSeqId", strMemberSeqID);
			String strTreeName = getTreeName(request);
			AccountInfoManager accountInfoManager=this.getAccountInfoManager();
			TreeData treeData = TTKCommon.getTreeData(request);
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			alSearchParam.add(PolicyAccInfoWebBoardHelper.getPolicySeqId(request));
			
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			
			String strCaption="["+TTKCommon.checkNull(PolicyAccInfoWebBoardHelper.getWebBoardDesc(request))+"]";
			treeData = new TreeData();
			//create the required tree
			treeData.createTreeInfo(strTreeName,null,true);
			
			//check the permision and set the tree for not to display respective icon
			this.checkTreePermission(request,treeData);
			request.getSession().setAttribute("treeData",null);
			frmMember.set("caption",strCaption);
			treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,
					request));
			treeData.modifySearchData("search");
			frmMember.set("display","display");
			//get the meberlist from database
			ArrayList alMembers = accountInfoManager.getMemberList(treeData.getSearchData());
			treeData.setSearchData(null);
			frmMember.set("display",null);//this attribute is used in JSP to show the error message.
			
			//get the data from database for Individual Policy and Ind. Policy as Group tab
			// For Corporate Policy and Non-Corporate Policy tab on search load the member list
			// If user click on Enrollment No. then policyVO will be in request scope then display that Enrollment detail
			if((strPolicyType != null && !"".equals(strPolicyType.trim())) 
					|| request.getAttribute("policyVO")!=null)
			{
				treeData.setRootData(alMembers);
			}//end of if(strIndividualPolicy.equals(strPolicyType) || strIndPolicyasGroup.equals(strPolicyType|| request.getAttribute("policyVO")!=null)
			//set the tree data object to session
			request.getSession().setAttribute("treeData",treeData);
			
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to get the details of the selected record from web-board.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.debug("Inside doChangeWebBoard method of MemberAction");
		return doDefault(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doSearch method of AccInfoMemberAction");
			this.setLinks(request);
			TreeData treeData = TTKCommon.getTreeData(request);
			
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath=getForwardPath(strActiveSubLink,request);
			String strTreeName = getTreeName(request);
			
			DynaActionForm frmMember=(DynaActionForm)form;
			frmMember.set("display",null);//this attribute is used in JSP to show the error message.
			
			AccountInfoManager accountInfoManager=this.getAccountInfoManager();
			
			String strCaption="["+TTKCommon.checkNull(PolicyAccInfoWebBoardHelper.getWebBoardDesc(request))+"]";
			//if the page number is clicked, display the appropriate page
			if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			{
				treeData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				treeData.setSelectedRoot(-1);
				return this.getForward(strForwardPath, mapping, request);
			}//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			
			//create the required tree
			treeData = new TreeData();
			treeData.createTreeInfo(strTreeName,null,true);
			//check the permision and set the tree for not to display respective icon
			this.checkTreePermission(request,treeData);
			treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
			treeData.modifySearchData("search");
			//get the meberlist from database
			ArrayList alMembers = accountInfoManager.getMemberList(treeData.getSearchData());
			treeData.setData(alMembers, "search");
			//set the tree data object to session
			request.getSession().setAttribute("treeData",treeData);
			frmMember.set("caption",strCaption);
			
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to show/Hide the dependent member list of the family
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doShowHide method of AccInfoMemberAction");
			this.setLinks(request);
			TreeData treeData = TTKCommon.getTreeData(request);
			
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath=getForwardPath(strActiveSubLink,request);
			
			DynaActionForm frmMember=(DynaActionForm)form;
			frmMember.set("display",null);//this attribute is used in JSP to show the error message.
			
			AccountInfoManager accountInfoManager=this.getAccountInfoManager();
			MemberVO memberVO = null;
			String strCaption="["+TTKCommon.checkNull(PolicyAccInfoWebBoardHelper.getWebBoardDesc(request))+"]";
			int iSelectedRoot = Integer.parseInt((String)frmMember.get("selectedroot"));
			//create the required tree
			ArrayList alNodeMembers = new ArrayList();
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			memberVO = ((MemberVO)treeData.getRootData().get(iSelectedRoot));
			
			//create search parameter to get the dependent list
			alSearchParam.add(memberVO.getPolicyGroupSeqID());
			
			//get the dependent list from database
			alNodeMembers=accountInfoManager.getDependentList(alSearchParam);
			treeData.setSelectedRoot(iSelectedRoot);
			((MemberVO)treeData.getRootData().get(iSelectedRoot)).setMemberList(alNodeMembers);
			treeData.setSelectedRoot(iSelectedRoot);
			request.getSession().setAttribute("treeData",treeData);
			frmMember.set("caption",strCaption);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));
		}//end of catch(Exception exp)
	}//end of doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to get the previous set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doBackward method of AccInfoMemberAction");
			this.setLinks(request);
			
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath=getForwardPath(strActiveSubLink,request);
			
			TreeData treeData = TTKCommon.getTreeData(request);
			AccountInfoManager accountInfoManager=this.getAccountInfoManager();
			
			treeData.modifySearchData(strBackward);//modify the search data
			//get the meberlist from database
			ArrayList alMembers = accountInfoManager.getMemberList(treeData.getSearchData());
			treeData.setData(alMembers, strBackward);//set the table data
			request.getSession().setAttribute("treeData",treeData);//set the table data object to session
			
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to get the next set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doForward method of AccInfoMemberAction");
			this.setLinks(request);
			
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath=getForwardPath(strActiveSubLink,request);
			
			TreeData treeData = TTKCommon.getTreeData(request);
			AccountInfoManager accountInfoManager=this.getAccountInfoManager();
			
			treeData.modifySearchData(strForward);//modify the search data
			//get the meberlist from database
			ArrayList alMembers = accountInfoManager.getMemberList(treeData.getSearchData());
			treeData.setData(alMembers, strForward);//set the table data
			request.getSession().setAttribute("treeData",treeData);//set the table data object to session
			
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	public ActionForward historyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			PreAuthVO preauthVO = null;
			log.info("Inside AccountInfoMemberAction historyList ");
			
			if (PreAuthWebBoardHelper.checkWebBoardId(request) == null) {
				ActionMessages actionMessages = new ActionMessages();
				ActionMessage actionMessage = new ActionMessage("error.PreAuthorization.required");
		        actionMessages.add("global.error",actionMessage); 
		        saveErrors(request,actionMessages);
		        return mapping.findForward("failure");
				
			}// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			
			HttpSession session = request.getSession();
			DynaActionForm frmHistoryList = (DynaActionForm) form;
			AccountInfoManager accountInfoObject = null;
			accountInfoObject = this.getAccountInfoManager();
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;//session.getAttribute("frmPreAuthGeneral");

			if ("Y".equals(request.getParameter("Entry")))
				frmHistoryList.set("historyMode", "PAT");
			preauthVO = new PreAuthVO();
			preauthVO.setMemberSeqID(PolicyAccInfoWebBoardHelper.getMemberSeqId(request));
			preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
			preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));

			frmHistoryList.set("memberSeqID", String.valueOf(preauthVO.getMemberSeqID()));
			
			if (frmPreAuthGeneral == null
					|| frmHistoryList.getString("memberSeqID") == null
					|| frmHistoryList.getString("memberSeqID").length() < 1) {
				session.setAttribute("preauthHistoryList", null);
				request.setAttribute("errorMsg", "Select Pre-Auth Details");
				return this.getForward(strPreauthHistoryList, mapping, request);
			}

			// call the business layer to get the Pre-Auth detail
			// frmHistoryList.set("preAuthSeqID",
			// frmPreAuthGeneral.getString("preAuthSeqID"));
			ArrayList<String[]> authorizationList = accountInfoObject
					.getPreauthHistoryList(
							new Long(frmHistoryList.getString("memberSeqID")),
							frmHistoryList.getString("historyMode"),null);
            request.setAttribute("historyListFlag", "MembersTab");
			session.setAttribute("preauthHistoryList", authorizationList);
			session.setAttribute("frmHistoryList", frmHistoryList);
			session.setAttribute("tabFlag", "MembersTab");
			request.setAttribute("tabFlag", request.getParameter("tabFlag"));
			return this.getForward(strPreauthHistoryList, mapping, request);
		}// end of try
		
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreauthHistoryList));
		}// end of catch(Exception exp)
	}// end of historyList(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)
	
	public ActionForward doViewHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doViewHistory");
			AccountInfoManager accountInfoObject = this.getAccountInfoManager();
			DynaActionForm frmHistoryList = (DynaActionForm) form;
			// check if user trying to hit the tab directly with out selecting
			// the hospital
			Document historyDoc = null;
			String authSeqID = request.getParameter("authSeqID");
			authSeqID = (authSeqID == null || authSeqID.length() < 1) ? "0"
					: authSeqID;
			String historyMode = frmHistoryList.getString("historyMode");
		//	request.getSession().setAttribute("WorkFlow", "Preauth");
			if ("PAT".equals(historyMode)) {
				historyDoc = accountInfoObject
						.getPreAuthHistory(new Long(authSeqID));
				request.setAttribute("preAuthHistoryDoc", historyDoc);
				return this.getForward(strPreAuthViewHistory, mapping, request);
			} else if ("CLM".equals(historyMode)) {
				historyDoc = accountInfoObject.getClaimHistory(new Long(authSeqID));
				request.setAttribute("claimHistoryDoc", historyDoc);
				return this.getForward(strClaimViewHistory, mapping, request);
			}
			
			return this.getForward(strPreAuthViewHistory, mapping, request);// it's
																			// not
																			// reach
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreauthHistoryList));
		}// end of catch(Exception exp)
	}// end of doViewHistory(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewAccInfoEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside EnrollmentAction doViewAccInfoEnrollment");
			setLinks(request);
			String strSelectedRoot=request.getParameter("selectedroot");
			StringBuffer sbfCaption=new StringBuffer();
			String strMember=getMember(request);
			String strForwards=getForwardPath("",request);
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			
			AccountInfoManager accountInfoManager=this.getAccountInfoManager();
			String strCaption="";
			String strCheck="";
			
			sbfCaption.append("[").append(TTKCommon.checkNull(PolicyAccInfoWebBoardHelper.getWebBoardDesc(request))).
			append("]");
			
			int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
			MemberDetailVO memberDetailVO= new MemberDetailVO();
			MemberAddressVO memberAddressVO=new MemberAddressVO();
			memberDetailVO.setMemberAddressVO(memberAddressVO);
			if(!strSelectedRoot.equals(""))// on clicking the edit link
			{
				MemberVO memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
				strCaption=strMember+" - "+sbfCaption.toString();
				strCheck="Edit";
				Long lngPolicyGroupSeqID=memberVO.getPolicyGroupSeqID();
				ArrayList<Object> alParameter=new ArrayList<Object>();
				alParameter.add(lngPolicyGroupSeqID);
				memberDetailVO=accountInfoManager.getEnrollment(alParameter);
			}//end of if(!strSelectedRoot.equals(""))
			DynaActionForm formEnrollment= (DynaActionForm)FormUtils.setFormValues("frmEnrollment",memberDetailVO,
					this,mapping,request);
			formEnrollment.set("memberAddressVO", FormUtils.setFormValues("frmMemberAddress",
					memberDetailVO.getMemberAddressVO(),this,mapping,request));
			formEnrollment.set("selectedRoot",strSelectedRoot);
			//using the values that are assigned to the "corporate", validation is done in the form-def
			formEnrollment.set("corporate",strForwards);
			
			formEnrollment.set("check",strCheck);
			formEnrollment.set("caption",strCaption);
			request.getSession().setAttribute("frmEnrollment",formEnrollment);
			return this.getForward(strForwards,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"enrollment"));
		}//end of catch(Exception exp)
	}//end of doViewAccInfoEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	/**
	 * This method is used to view/edit the  member details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewMember(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try
		{
			log.debug("Inside doViewMember method of AccInfoMemberAction");
			this.setLinks(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			return this.getForward(getMemberDetailPath(strActiveSubLink), mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));
		}//end of catch(Exception exp)
	}//end of doViewMember(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to previous screen
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doClose method of AccInfoMemberAction");
			setLinks(request);
			return mapping.findForward("corporateclose");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));
		}//end of catch(Exception exp)
	}//end of doClose method
	
	/**
	 * This method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPolicyList current instance of form bean
	 * @param request HttpServletRequest object
	 * @param strActiveSubLink current Active sublink
	 * @return alSearchObjects ArrayList of search params
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		if(request.getAttribute("policyVO")!=null)
		{
			PolicyVO policyVO =(PolicyVO)request.getAttribute("policyVO");
			if(policyVO!=null)
			{
				frmSearchUser.set("sEnrollmentNbr",policyVO.getEnrollmentNbr());
			}//end of if(policyVO!=null)
		}//end of if(request.getParameter("policyVO")!=null)
		alSearchParams.add(PolicyAccInfoWebBoardHelper.getPolicyGroupSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request)
	
	/**
	 * Returns the AccountInfoManager session object for invoking methods on it.
	 * @return AccountInfoManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private AccountInfoManager getAccountInfoManager() throws TTKException
	{
		AccountInfoManager accountInfoManager = null;
		try
		{
			if(accountInfoManager == null)
			{
				InitialContext ctx = new InitialContext();
				accountInfoManager = (AccountInfoManager) ctx.lookup("java:global/TTKServices/business.ejb3/AccountInfoManagerBean!com.ttk.business.accountinfo.AccountInfoManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "memberdetail");
		}//end of catch
		return accountInfoManager;
	}//end getAccountInfoManager()
	
	/**
	 * Check the ACL permission and set the display property of icons.
	 * @param request The HTTP request we are processing
	 * @param treeData TreeData for which permission has to check
	 */
	private void checkTreePermission(HttpServletRequest request,TreeData treeData)
	throws TTKException
	{
		log.debug("--------- inside check Tree permission --------------");
		String strPolicyType = PolicyAccInfoWebBoardHelper.getPolicyTypeID(request);
		if(strCorporatePolicy.equals(strPolicyType) || strNonCorporatePolicy.equals(strPolicyType)) 
			treeData.getTreeSetting().getRootNodeSetting().setIsLink(true);
		treeData.getTreeSetting().getChildNodeSetting().setIsLink(true);
		if(!(strCorporatePolicy.equals(strPolicyType)))
		{
			treeData.getTreeSetting().getRootNodeSetting().setIconVisible(R00T_CHANGEPWD_ICON,false);
		}//end of if(!(strCorporatePolicy.equals(strPolicyType)))
	}//end of checkTreePermission(HttpServletRequest request,TreeData treeData,String strSwitchType)
	
	/**
	 * This method returns the forward path for next view based on the Flow
	 *
	 * @param strActiveSubLink String current sublink
	 * @return strForwardPath String forward path for the next view
	 */
	private String getForwardPath(String strActiveSubLink,HttpServletRequest request)throws TTKException
	{
		String strForwardPath=null;
		String strMode=request.getParameter("mode");
		try
		{
			if(strActiveSubLink.equals(strEnrollment))
			{
				strForwardPath=strMemberdetails;
				
			}//end of if(strActiveSubLink.equals(strEnrollment))
			if(strMode.equalsIgnoreCase(strViewAccInfoEnrollment) )
			{
				String strPolicyType = PolicyAccInfoWebBoardHelper.getPolicyTypeID(request);// for mode is strViewAccInfoEnrollment
				if(strCorporatePolicy.equals(strPolicyType))
				{
					strForwardPath=StrCorporate;
				}
				else if(strNonCorporatePolicy.equals(strPolicyType))
				{
					strForwardPath=StrNONCorporate;
				}
			}//end of if(strMode.equalsIgnoreCase(strViewAccInfoEnrollment) )
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch
		
		return strForwardPath;
	}//end of getForwardPath(String strActiveSubLink)
	
	/**
	 * This method returns the employee/member details.
	 *
	 * @param request The HTTP request we are processing.
	 * @return strMember String employee/member detais.
	 */
	private String getMember(HttpServletRequest request)throws TTKException
	{
		String strMember="";
		try
		{
			String strMode=request.getParameter("mode");
			if(strMode.equalsIgnoreCase(strViewAccInfoEnrollment))
			{
				String strPolicyType = PolicyAccInfoWebBoardHelper.getPolicyTypeID(request);// for mode is strViewAccInfoEnrollment
				if(strCorporatePolicy.equals(strPolicyType))
				{
					strMember="Employee Details";
				}
				else if(strNonCorporatePolicy.equals(strPolicyType))
				{
					strMember="Member Details";
				}
			}//end of if(strMode.equalsIgnoreCase(strAddNewEnrollment)||
			//strMode.equalsIgnoreCase(strSaveEnrollmentMode)||strMode.equalsIgnoreCase(strReset) )
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch
		return strMember;
	}//end of getMember(HttpServletRequest request)
	
	/**
	 * This method returns the forward path for next view based on the Flow
	 *
	 * @param strActiveSubLink String current sublink
	 * @return strForwardPath String forward path for the next view
	 */
	private String getMemberDetailPath(String strActiveSubLink)
	{
		String strForwardPath=null;
		if(strActiveSubLink.equals(strEnrollment))
		{
			strForwardPath=strMemberdetails;
			
		}//end of if(strActiveSubLink.equals(strEnrollment))
		return strForwardPath;
	}//end of getMemberDetailPath(String strActiveSubLink)
	
	
	/**
	 * This method returns the Tree name to get the data from the session
	 *
	 * @param strActiveSubLink String current sublink
	 * @return strForwardPath String forward path for the next view
	 */
	private String getTreeName(HttpServletRequest request) throws Exception
	{
		String strTreeName=null;
		String strPolicyType = PolicyAccInfoWebBoardHelper.getPolicyTypeID(request);
		if(strIndividualPolicy.equals(strPolicyType) || strIndPolicyasGroup.equals(strPolicyType) || request.getAttribute("policyVO")!=null) 
		{
			strTreeName="AccInfoMemberTree";
		}//end of if(strIndividualPolicy.equals(strPolicyType) || strIndPolicyasGroup.equals(strPolicyType) 
		else
		{
			strTreeName="AccInfoCorporateMemberTree";
		}
		return strTreeName;
	}//end of getForwardPath(String strActiveSubLink)
	
}//end of class AccInfoMemberAction


