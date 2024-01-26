/**
 * @ (#) EnrollmentAction.java Feb 10, 2006
 * Project 		: TTK HealthCare Services
 * File 		: EnrollmentAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created : Feb 10, 2006
 *
 * @author 		: Pradeep R
 * Modified by 	: Raghavendra T M
 * Modified date: July 30, 2007
 * Reason 		: doViewAccInfoEnrollment method added for accountinfo 
 */

package com.ttk.action.enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.administration.RuleManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PolicyDetailVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for adding enrollment information in corporate and non corporate policies in enrollment flow.
 * This class also provides option for deleting the selected enrollment.
 */

public class EnrollmentAction extends TTKAction {
	private  Logger log = Logger.getLogger( EnrollmentAction.class );
	private  static final String strAddNewEnrollment="doAdd";
	private  static final String strViewEnrollment="doViewEnrollment";
	private  static final String StrCorporate="corporate";
	private  static final String StrNONCorporate="noncorporate";
	private static final String strSaveEnrollmentMode="doSave";
	private static final String strReset="doReset";
	private static final String StrCorporateClose="corporateclose";
	//sub links
	private static final String strIndividualPolicy="Individual Policy";
	private static final String strIndPolicyGroup="Ind. Policy as Group";
	private static final String strCorporatePolicy="Corporate Policy";
	private static final String strNonCorpPolicy="Non-Corporate Policy";
	private static final String strOccupationCodes="occupCodeList";
	// For sub link name
	
	/**
	 *
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside EnrollmentAction doAdd");
			handleSetLink(request);
			String strSelectedRoot=request.getParameter("selectedroot");
			String strMember=getMember(request);
			String strForwards=getForwardPath(request);
			String strCaption="";
			String strCheck="";
			//String strCaption=null;
			StringBuffer sbfCaption=new StringBuffer();
			MemberManager memberManagerObject=this.getMemberManagerObject();
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			sbfCaption.append("[").append(TTKCommon.checkNull(WebBoardHelper.getPolicyNumber(request))).
			append("] [").append(TTKCommon.checkNull(WebBoardHelper.getGroupID(request))).append("] [").
			append(TTKCommon.checkNull(WebBoardHelper.getGroupName(request))).append("]");
			MemberDetailVO memberDetailVO= new MemberDetailVO();
			MemberAddressVO memberAddressVO=new MemberAddressVO();
			memberAddressVO.setCountryCode("123");	
			memberAddressVO.setStateCode("DXB");
			request.getSession().setAttribute("providerStates",TTKCommon.getStates("123"));			
			memberDetailVO.setMemberAddressVO(memberAddressVO);			
			strCaption=strMember+" - Add - "+sbfCaption.toString();
			strCheck="Add";
			DynaActionForm formEnrollment= (DynaActionForm)FormUtils.setFormValues("frmEnrollment",memberDetailVO,
					this,mapping,request);
			formEnrollment.set("memberAddressVO", FormUtils.setFormValues("frmMemberAddress",
					memberDetailVO.getMemberAddressVO(),this,mapping,request));
			//getting the designations from database and set to memberAddressVO form
			
			//memberDetailVO.setEmpStatusDesc("Active");
			formEnrollment.set("empStatusDesc","Active");
			formEnrollment.set("selectedRoot",strSelectedRoot);
			//using the values that are assigned to the "corporate", validation is done in the form-def
			formEnrollment.set("corporate",strForwards);
			formEnrollment.set("check",strCheck);
			formEnrollment.set("caption",strCaption);
			//if the active link is "online form flow" then get the policy seq id info from the onlinePolicyVO
			ArrayList alLocation=new ArrayList();
			alLocation=memberManagerObject.getLocation(WebBoardHelper.getPolicySeqId(request));
			request.getSession().setAttribute("alLocation",alLocation);
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
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doViewEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside EnrollmentAction doViewEnrollment");
			handleSetLink(request);
			String strSelectedRoot=request.getParameter("selectedroot");
			//String strCaption=null;
			StringBuffer sbfCaption=new StringBuffer();
			String strMember=getMember(request);
			String strForwards=getForwardPath(request);
			String strType=getType(request);
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			MemberManager memberManagerObject=this.getMemberManagerObject();
			String strCaption="";
			String strCheck="";
			String strSwitchType=getSwitchType(request);
			sbfCaption.append("[").append(TTKCommon.checkNull(WebBoardHelper.getPolicyNumber(request))).
			append("] [").append(TTKCommon.checkNull(WebBoardHelper.getGroupID(request))).append("] [").
			append(TTKCommon.checkNull(WebBoardHelper.getGroupName(request))).append("]");
			int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
			MemberDetailVO memberDetailVO= new MemberDetailVO();
			MemberAddressVO memberAddressVO=new MemberAddressVO();
			memberDetailVO.setMemberAddressVO(memberAddressVO);
			if(!strSelectedRoot.equals(""))// on clicking the edit link
			{
				MemberVO memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
				strCaption=strMember+" - Edit - "+sbfCaption.toString();
				strCheck="Edit";
				Long lngPolicyGroupSeqID=memberVO.getPolicyGroupSeqID();
				ArrayList<Object> alParameter=new ArrayList<Object>();
				alParameter.add(lngPolicyGroupSeqID);
				alParameter.add(strSwitchType);
				alParameter.add(strType);
				memberDetailVO=memberManagerObject.getEnrollment(alParameter);
			}//end of if(!strSelectedRoot.equals(""))
			DynaActionForm formEnrollment= (DynaActionForm)FormUtils.setFormValues("frmEnrollment",memberDetailVO,this,mapping,request);
			
			request.getSession().setAttribute("providerStates",TTKCommon.getStates(memberDetailVO.getMemberAddressVO().getCountryCode()));
			request.getSession().setAttribute("providerAreas",TTKCommon.getAreas(memberDetailVO.getMemberAddressVO().getStateCode()));
			
			formEnrollment.set("memberAddressVO", FormUtils.setFormValues("frmMemberAddress",
					memberDetailVO.getMemberAddressVO(),this,mapping,request));
			formEnrollment.set("selectedRoot",strSelectedRoot);
			//using the values that are assigned to the "corporate", validation is done in the form-def
			formEnrollment.set("corporate",strForwards);
			formEnrollment.set("check",strCheck);
			formEnrollment.set("caption",strCaption);			
			//if the active link is "online form flow" then get the policy seq id info from the onlinePolicyVO
			ArrayList alLocation=new ArrayList();
			//else if it is from "Enrollment flow" then get the info from the web-board itself.
				alLocation=memberManagerObject.getLocation(WebBoardHelper.getPolicySeqId(request));
			request.getSession().setAttribute("alLocation",alLocation);
			request.getSession().setAttribute("frmEnrollment",formEnrollment);
			
			
			 
            ArrayList<Object> alPolicy = new ArrayList<Object>(); 
            if(strSwitchType.equals("ENM"))
            {
           	 alPolicy.add(WebBoardHelper.getPolicySeqId(request));
            }//end of if(strSwitchType.equals(strEnrollment))
            else
            {
           	 alPolicy.add(WebBoardHelper.getEndorsementSeqId(request));
            }//end of else
			 String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			 PolicyManager policyObject=this.getPolicyManagerObject();
             PolicyDetailVO policyDetailVO=null;
             alPolicy.add(strSwitchType);//Enrollment or Endorsement
             alPolicy.add(strActiveSubLink);
             policyDetailVO= policyObject.getPolicy(alPolicy);   
             DynaActionForm frmPolicyDetails= (DynaActionForm)form;
             frmPolicyDetails= (DynaActionForm)FormUtils.setFormValues("frmPolicyDetails",policyDetailVO,this,mapping, request);
             request.getSession().setAttribute("frmPolicyDetails",frmPolicyDetails);
			 request.getSession().setAttribute("memberstatusdata", memberDetailVO.getEmpStatusTypeID());
             
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
	}//end of doViewEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	/**
	 * This method is used to add/update the record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
			log.debug("Inside EnrollmentAction doSave");
			handleSetLink(request);
			DynaActionForm frmEnrollment =(DynaActionForm)form;
			String strForwards=getForwardPath(request);
//			String strCheck="";
			String strSwitchType=getSwitchType(request);
			String strType=getType(request);
			MemberManager memberManagerObject=this.getMemberManagerObject();
			//String strCaption=frmEnrollment.getString("caption");
			String strCaption=frmEnrollment.getString("caption");
			MemberDetailVO memberDetailVO = new MemberDetailVO();
			MemberAddressVO memberAddressVO = new MemberAddressVO();
			// get the policy detail values from the form
			if(request.getParameter("proposalFormYN")==null)// if proposalFormYN checkbox in not selected
				frmEnrollment.set("proposalFormYN","N");
			if(request.getParameter("stopPatClmYN")==null)// if Stop Preauth/Caim checkbox in not selected
			{
				memberDetailVO.setStopPatClmYN("N");
			}//end of if(request.getParameter("stopPatClmYN")==null)
			String strSelectedRoot=frmEnrollment.getString("selectedRoot");
			memberDetailVO=(MemberDetailVO)FormUtils.getFormValues(frmEnrollment,this,mapping,request);
			ActionForm memberAddressForm=(ActionForm)frmEnrollment.get("memberAddressVO");
			memberAddressVO=(MemberAddressVO)FormUtils.getFormValues(memberAddressForm,"frmMemberAddress",
					this,mapping,request);
			memberDetailVO.setMemberAddressVO(memberAddressVO);
			memberDetailVO.getBankName().replaceAll("'","''");
			if(!strSelectedRoot.equals(""))//on save from edit mode
			{
				//For Editing this particular seq id.
				memberDetailVO.setPolicyGroupSeqID(memberDetailVO.getPolicyGroupSeqID());
			}//end of if(!strSelectedRoot.equals(""))
			else
			{
				memberDetailVO.setPolicyGroupSeqID(null);//adding new enrollment
			}//end of else
			
			//based on Erollment or Endosement in Enrollment flow get the corresponding seq id ..
			if(strSwitchType.equals("ENM"))
			{
				memberDetailVO.setPolicySeqID(WebBoardHelper.getPolicySeqId(request));
			}//end of if(strSwitchType.equals("ENM"))
			else
			{
				memberDetailVO.setPolicySeqID(WebBoardHelper.getPolicySeqId(request));
				memberDetailVO.setEndorsementSeqID(WebBoardHelper.getEndorsementSeqId(request));
			}//end of else
			memberDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			long lngCount=memberManagerObject.saveEnrollment(memberDetailVO,strSwitchType,strType);
			if(strSelectedRoot.equals(""))// for initalizing the form on click of save button in add flow.
			{
//				strCheck="Add";
				memberDetailVO= new MemberDetailVO();
				memberAddressVO=new MemberAddressVO();
				memberDetailVO.setMemberAddressVO(memberAddressVO);
				DynaActionForm formEnrollment= (DynaActionForm)FormUtils.setFormValues("frmEnrollment",memberDetailVO,
						this,mapping,request);
				formEnrollment.set("memberAddressVO", FormUtils.setFormValues("frmMemberAddress",
						memberDetailVO.getMemberAddressVO(),this,mapping,request));
				formEnrollment.set("caption",strCaption);
				//using the values that are assigned to the "corporate", validation is done in the form-def
				formEnrollment.set("corporate",strForwards);
				request.getSession().setAttribute("frmEnrollment",formEnrollment);
			}//end of if(strSelectedRoot.equals(""))
			// Added Rule related validation as per Sreeraj's instruction on 27/02/2008
			if(lngCount <0){
				log.info("Errors are there..........");
				RuleManager ruleManager=this.getRuleManagerObject();
				//RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
				ArrayList alValidationError=ruleManager.getValidationErrorList(new Long(lngCount));
				//prepare Error messages
				//ArrayList alErrorMessage=ruleXMLHelper.prepareErrorMessage(alValidationError,request);
				request.setAttribute("BUSINESS_ERRORS",alValidationError);
				return this.getForward(strForwards, mapping, request);
			}
			if(lngCount>0)
			{
				if(!strSelectedRoot.equals(""))//on save from edit mode
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(!strSelectedRoot.equals(""))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(iCount>0)
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"enrollment"));
		}//end of catch(Exception exp)
			}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside EnrollmentAction doReset");
			handleSetLink(request);
			DynaActionForm formEnrollment=(DynaActionForm)form;
			String strCaption=formEnrollment.getString("caption");
			String strSelectedRoot=formEnrollment.getString("selectedRoot");
			MemberDetailVO memberDetailVO= new MemberDetailVO();
			MemberAddressVO memberAddressVO=new MemberAddressVO();
			memberDetailVO.setMemberAddressVO(memberAddressVO);
			String strForwards=getForwardPath(request);
			String strType=getType(request);
			String strSwitchType=getSwitchType(request);
			MemberManager memberManagerObject=this.getMemberManagerObject();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			String strCheck="Add";
			if(!strSelectedRoot.equals(""))// on clicking the edit link
			{
				MemberVO memberVO=(MemberVO)treeData.getRootData().get(TTKCommon.getInt(strSelectedRoot));
				strCheck="Edit";
				Long lngPolicyGroupSeqID=memberVO.getPolicyGroupSeqID();
				ArrayList<Object> alParameter=new ArrayList<Object>();
				alParameter.add(lngPolicyGroupSeqID);
				alParameter.add(strSwitchType);
				alParameter.add(strType);
				memberDetailVO=memberManagerObject.getEnrollment(alParameter);
			}//end of if(!strSelectedRoot.equals(""))
			formEnrollment= (DynaActionForm)FormUtils.setFormValues("frmEnrollment",memberDetailVO,
					this,mapping,request);
			formEnrollment.set("memberAddressVO", FormUtils.setFormValues("frmMemberAddress",
					memberDetailVO.getMemberAddressVO(),this,mapping,request));
			formEnrollment.set("selectedRoot",strSelectedRoot);
			formEnrollment.set("corporate",strForwards);
			formEnrollment.set("caption",strCaption);
			formEnrollment.set("check",strCheck);
			request.getSession().setAttribute("frmEnrollment",formEnrollment);
			Connection con=ResourceManager.getConnection();
		      PreparedStatement statement=con.prepareStatement("select A.DESIGNATION_TYPE_ID,A.DESIGNATION_DESCRIPTION from   tpa_designation_code A WHERE A.HEADER_TYPE='EMP' ORDER BY A.SORT_NO");
		         ResultSet rs=statement.executeQuery();
		         HashMap<String,String> strDesignations=new HashMap<String,String>();
		         while(rs.next())strDesignations.put(rs.getString(1), rs.getString(2));
		         //DynaActionForm dynaActionForm= (DynaActionForm)form;
		           //dynaActionForm.set("designations",strDesignations);
		           formEnrollment.set("designations",strDesignations);
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
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to get the delete record from the detail screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside EnrollmentAction doDelete");
			handleSetLink(request);
			DynaActionForm formEnrollment=(DynaActionForm)form;
			String strSelectedRoot=formEnrollment.getString("selectedRoot");
			MemberVO memberVO= new MemberVO();
			String strSeqID = "";
			String strSwitchType=getSwitchType(request);
			String strType=getType(request);
			Long lngEndorsementSeqID=null;
			ArrayList<Object> alSearchParam=new ArrayList<Object>();
			MemberManager memberManagerObject=this.getMemberManagerObject();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			treeData.setSelectedRoot(-1);   //sets the selected rows
			List<String> iResult;
			if(!strSelectedRoot.equals(""))// on clicking the edit link
			{
				memberVO=(MemberVO)treeData.getRootData().get(TTKCommon.getInt(strSelectedRoot));
				strSeqID = memberVO.getPolicyGroupSeqID().toString();
			}
			if(strSwitchType.equals("END")) // for Endorsement flow get the Endorsenet Seq ID from webBoard
			{
				lngEndorsementSeqID = WebBoardHelper.getEndorsementSeqId(request);
			}//end of if(strSwitchType.equals("END"))
			//create parameter to delete the member detail
			alSearchParam.add(strSwitchType);
			alSearchParam.add(strType);
			alSearchParam.add("GROUP");
			alSearchParam.add(strSeqID);
			alSearchParam.add(lngEndorsementSeqID);
			alSearchParam.add(TTKCommon.getUserSeqId(request));
			iResult= memberManagerObject.deleteMember(alSearchParam);
			log.debug("iResult value is :"+iResult);
			return (mapping.findForward(StrCorporateClose));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"enrollment"));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to previous screen when closed button is clicked.
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
			log.debug("Inside EnrollmentAction doClose");
			handleSetLink(request);
			String strForward=null;
			strForward=StrCorporateClose;
			return (mapping.findForward(strForward));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"enrollment"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 * This method returns the type of policy.
	 *
	 * @param request The HTTP request we are processing.
	 * @return strType String which gives type of policy.
	 */
	private String getType(HttpServletRequest request)throws TTKException
	{
		String strType=null;
		try
		{
			String strMode=request.getParameter("mode");
			String strSubLinks=TTKCommon.getActiveSubLink(request);
			if(strMode.equalsIgnoreCase(strAddNewEnrollment)||strMode.equalsIgnoreCase(strSaveEnrollmentMode)||
					strMode.equalsIgnoreCase(strReset)||strMode.equalsIgnoreCase(strViewEnrollment) )
			{
				if(strSubLinks.equals(strIndividualPolicy))
				{
					strType="IP";
				}//end of if(strSubLinks.equals(strIndividualPolicy))
				else if(strSubLinks.equals(strIndPolicyGroup))
				{
					strType="IG";
				}//end of else if(strSubLinks.equals(strIndPolicyGroup))
				else if(strSubLinks.equals(strCorporatePolicy))
				{
					strType="CP";
				}//end of else if(strSubLinks.equals(strCorporatePolicy))
				else if(strSubLinks.equals(strNonCorpPolicy))
				{
					strType="NC";
				}//end of else if(strSubLinks.equals(strNonCorpPolicy))
			}//end of if(strMode.equalsIgnoreCase(strAddNewEnrollment)||
			//strMode.equalsIgnoreCase(strSaveEnrollmentMode)||strMode.equalsIgnoreCase(strReset) )
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch
		return strType;
	}//end of getType(HttpServletRequest request)
	
	/**
	 * This method returns validate flag as corporate/non-corporate
	 *
	 * @param request The HTTP request we are processing.
	 * @return strValidate String as validate flag as corporate/non-corporate.
	 
	private String getValidate(HttpServletRequest request)throws TTKException
	{
		String strValidate=null;
		try
		{
			String strActiveLinks=TTKCommon.getActiveLink(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strMode=request.getParameter("mode");
			if(strActiveLinks.equals("Online Forms"))
			{
				OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)
				request.getSession().getAttribute("SelectedPolicy");
				if(strMode.equalsIgnoreCase(strAddNewEnrollment)||strMode.equalsIgnoreCase(strSaveEnrollmentMode)||
						strMode.equalsIgnoreCase(strReset)||strMode.equalsIgnoreCase(strViewEnrollment) )
				{
					if(onlinePolicyVO.getEnrollmentType().equals(strCorPolicy))
					{
						strValidate=StrCorporate;
					} //end of if(strActiveSubLink.equals(strCorporatePolicy))
					else if(onlinePolicyVO.getEnrollmentType().equals(strNonCorporatePolicy))
					{
						strValidate=StrNONCorporate;
					}//end of if(strActiveSubLink.equals(strNonCorporatePolicy))
				}
			}//end of  if(strActiveLinks.equals("Online Forms"))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch
		return strValidate;
	}//end of getValidate(HttpServletRequest request)*/
	
	/**
	 * This method returns the enrollment forward path for next view based on the Flow
	 *
	 * @param request The HTTP request we are processing.
	 * @return strForwardPath String forward path for the next view
	 */
	private String getForwardPath(HttpServletRequest request)throws TTKException
	{
		String strForwards=null;
		try
		{
			String strSubLinks=TTKCommon.getActiveSubLink(request);
			String strMode=request.getParameter("mode");
			if(strMode.equalsIgnoreCase(strAddNewEnrollment)||strMode.equalsIgnoreCase(strSaveEnrollmentMode)||
					strMode.equalsIgnoreCase(strReset)||strMode.equalsIgnoreCase(strViewEnrollment) )
			{
				if(strSubLinks.equals(strCorporatePolicy))
				{
					strForwards=StrCorporate;
				}//end of if(strSubLinks.equals(strCorporatePolicy))
				else if(strSubLinks.equals(strNonCorpPolicy))
				{
					strForwards=StrNONCorporate;
				}//end of else if(strSubLinks.equals(strNonCorpPolicy))
			}//end of if(strMode.equalsIgnoreCase(strAddNewEnrollment)||
			//strMode.equalsIgnoreCase(strSaveEnrollmentMode)||strMode.equalsIgnoreCase(strReset) )
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch
		
		return strForwards;
	}//end of getForwardPath(HttpServletRequest request)
	
	/**
	 * This method returns the switch type as ENM/END.
	 *
	 * @param request The HTTP request we are processing.
	 * @return strSwitchType String as ENM/END.
	 */
	private String getSwitchType(HttpServletRequest request)throws TTKException
	{
		String strSwitchType="";
		try
		{
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch
		return strSwitchType;
	}//end of getSwitchType(HttpServletRequest request)
	
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
			String strSubLinks=TTKCommon.getActiveSubLink(request);
			if(strMode.equalsIgnoreCase(strAddNewEnrollment)||strMode.equalsIgnoreCase(strSaveEnrollmentMode)||
					strMode.equalsIgnoreCase(strReset) || strMode.equalsIgnoreCase(strReset) || strMode.equalsIgnoreCase(strViewEnrollment))
			{
				if(strSubLinks.equals(strCorporatePolicy))
				{
					strMember="Employee Details";
				}//end of if(strSubLinks.equals(strCorporatePolicy))
				else if(strSubLinks.equals(strNonCorpPolicy))
				{
					strMember="Member Details";
				}//end of else if(strSubLinks.equals(strNonCorpPolicy))
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
	 * This method used to set the setLinks.
	 *
	 * @param request The HTTP request we are processing.
	 * @return void
	 */
	private void handleSetLink(HttpServletRequest request)throws TTKException
	{
		try
		{
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
		}catch(Exception exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch
	}//end of handleSetLink(HttpServletRequest request)
	
	/**
	 * Returns the MemberManager session object for invoking methods on it.
	 * @return MemberManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MemberManager getMemberManagerObject() throws TTKException
	{
		MemberManager memberManager = null;
		try
		{
			if(memberManager == null)
			{
				InitialContext ctx = new InitialContext();
				memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
			}//end of if(PolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "newenrollment");
		}//end of catch
		return memberManager;
	}//end getMemberManagerObject()
	
	 // Added the following piece of code for integrating Rule related validation 
    // as per Sreeraj's instruction
	/**
     * Returns the RuleManager session object for invoking methods on it.
     * @return RuleManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private RuleManager getRuleManagerObject() throws TTKException
    {
        RuleManager ruleManager = null;
        try
        {
            if(ruleManager == null)
            {
                InitialContext ctx = new InitialContext();
                ruleManager = (RuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/RuleManagerBean!com.ttk.business.administration.RuleManager");
                log.debug("Inside RuleManager: RuleManager: " + ruleManager);
            }//end if(ruleManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "memberdetail");
        }//end of catch
        return ruleManager;
    }//end of getRuleManagerObject()
    
    
    
    
    private PolicyManager getPolicyManagerObject() throws TTKException
    {
        PolicyManager policyManager = null;
        try
        {
            if(policyManager == null)
            {
                InitialContext ctx = new InitialContext();
                policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
            }//end if(policyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "policydetail");
        }//end of catch
        return policyManager;
    }//end getHospitalManagerObject()
    
    public ActionForward doSelectoccCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSelectoccCode method of EnrollmentAction");
    		setLinks(request);
    		return mapping.findForward(strOccupationCodes);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"enrollment"));
		}//end of catch(Exception exp)
    }

      private HospitalManager getHospitalManagerObject() throws TTKException
    {
        HospitalManager hospManager = null;
        try
        {
            if(hospManager == null)
            {
                InitialContext ctx = new InitialContext();
                hospManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
            }//end if(hospManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "groupdetail");
        }//end of catch
        return hospManager;
    }//end getHospitalManagerObject()  
    
    
    
    
}//end of EnrollmentActions