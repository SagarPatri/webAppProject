/**
 * @ (#) HistoryAction.java May 8th, 2006
 * Project 	     : TTK HealthCare Services
 * File          : HistoryAction.java
 * Author        : Raghavendra T M
 * Company       : Span Systems Corporation
 * Date Created  : May 8th, 2006
 *
 * @author       :  Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.dataentrypreauth;

import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.accountinfo.AccountInfoManager;
import com.ttk.business.preauth.MemberHistoryManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.preauth.CitibankHistoryVO;
import com.ttk.dto.preauth.ClaimantHistoryVO;
import com.ttk.dto.preauth.PolicyHistoryVO;
import com.ttk.dto.preauth.PreAuthHistoryVO;

/**
 * This class is reusable for searching of history of pre-auth and claim.
 *  This class also provides option for viewing the detail history of pre-auth/claim.
 */

public class HistoryAction extends TTKAction  {

	private static Logger log = Logger.getLogger( HistoryAction.class ); // Getting Logger for this Class file

	//	Modes
	private  static final String strForward="Forward";
	private  static final String strBackward="Backward";

	//  Action mapping forwards
	private static final String strPolicyHistory="historydetails";
	private static final String strClaimPolicyHistory="claimhistorydetails";
	private static final String strOnlinePolicyHistory="accountinfohistorydetails";

	//Exception Message Identifier
    private static final String strHistoryError="history";

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
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside HistoryAction doDefault");
			String strTableName ="";
			String strLink = TTKCommon.getActiveLink(request);
			TreeData treeData = TTKCommon.getTreeData(request);
			DynaActionForm frmHistoryDetail=(DynaActionForm)request.getAttribute("frmHistoryDetail");
			MemberVO memberVO = null;
			String strHistorylist = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			String strEnrollmentID = "";
			StringBuffer strCaption=new StringBuffer();
			DynaActionForm frmHistory=(DynaActionForm)form;
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)frmHistory).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			frmHistory.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
			frmHistory.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));
			if(!strLink.equals("Account Info"))
			{
				if((PreAuthWebBoardHelper.checkWebBoardId(request)==null)||
																(ClaimsWebBoardHelper.checkWebBoardId(request)==null))
				{
					TTKException expTTK = new TTKException();
					if(!strLink.equals("Claims")){
						expTTK.setMessage("error.PreAuthorization.required");
					}//end of if(!strLink.equals("Claims"))
					else{
						expTTK.setMessage("error.Claims.required");
					}//end of else
					throw expTTK;
				}//end of if
			}//end of if(!strLink.equals("Online Forms"))
			if(strLink.equals("Pre-Authorization"))
			{
				if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"))
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.codingflow");
					throw expTTK;
				}//end of if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"))
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.codingflow");
					throw expTTK;
				}//end of if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
			}//end of if(strLink.equals("Claims"))
			TableData tableData=TTKCommon.getTableData(request);
			if(strLink.equals("Pre-Authorization"))
			{
				strHistorylist = "prehistorylist";
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strHistorylist = "claimhistorylist";
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Account Info"))
			{
				String strSelectedRoot = (String)frmHistory.get("selectedroot");
				String strSelectedNode = (String)frmHistory.get("selectednode");
				strHistorylist = "accountinfohistorylist";
				if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
				{
					memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
					String strName =  memberVO.getName();
					String strName1 =  memberVO.getName();
					strClaimantName =  strName.substring(0,strName.indexOf("/")-1).trim();
					strWebBoardDesc = strName1.substring(strName1.indexOf("/")+1,strName1.lastIndexOf("/")-1).trim();
				}//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
			}//end of else if(strLink.equals("Online Forms"))*/
			if(!TTKCommon.checkNull(strClaimantName).equals("")){
				strCaption.append(" [ ").append(strClaimantName).append(" ]").toString();
			}//end of if(!TTKCommon.checkNull(strClaimantName).equals(""))
			if(strLink.equals("Account Info"))
			{
				frmHistory.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").toString());
			}//end of if(strLink.equals("Account Info"))
			else
			{
				frmHistory.set("caption",strCaption.append(strEnrollmentID).toString());
			}//end of else
			if(frmHistoryDetail != null){
				frmHistoryDetail.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").append(strEnrollmentID).toString());
			}//end of if(frmHistoryDetail != null)
			DynaActionForm frmHistoryList=(DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			strTableName = "HistoryTable";
			if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
			{
				strTableName = "PolicyTable";
			}//end of if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
			{
				strTableName = "ClaimTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("ENS"))
			{
				strTableName = "EndorsementSummaryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("ENS"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("BST"))
			{
				strTableName = "BufferSummaryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("BST"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HMP"))
			{
				strTableName = "ManualPreAuthHistoryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HMP"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
			{
				strTableName = "CitibankEnrolHistoryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))
			{
				strTableName = "CitibankClaimsHistoryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))

			tableData.createTableInfo(strTableName,new ArrayList());
			if(strLink.equals("Claims")&&((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
			{
				//((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
				((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(false);
			}//end of if(strLink.equals("Claims")&&((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL")
			if(!(strLink.equals("Claims"))&&((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
			{
				//((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(true);
				((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(true);
			}//end of else
			request.getSession().setAttribute("tableData",tableData);
			//frmHistory.set("caption",frmHistoryDetail.getString("caption"));
			frmHistoryList.set("caption",frmHistory.getString("caption"));
			if(frmHistoryDetail != null){
				frmHistoryDetail.set("caption",frmHistoryDetail.getString("caption"));
			}//end of if(frmHistoryDetail != null)
			return this.getForward(strHistorylist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
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
																HttpServletResponse response) throws Exception {
		log.debug("Inside HistoryAction doChangeWebBoard");
		return doDefault(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)

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
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside HistoryAction doSearch");
			String strTableName ="";
			MemberHistoryManager memberHistoryManagerObject=this.getMemberHistoryManagerObject();
			AccountInfoManager accountInfoManagerObject=this.getAccountInfomanagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			TreeData treeData = TTKCommon.getTreeData(request);
			DynaActionForm frmHistoryDetail=(DynaActionForm)request.getAttribute("frmHistoryDetail");
			MemberVO memberVO = null;
			String strHistorylist = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			String strEnrollmentID = "";
			StringBuffer strCaption=new StringBuffer();
			ArrayList alHistoryList=new ArrayList();
			DynaActionForm frmHistory=(DynaActionForm)form;
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)frmHistory).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			frmHistory.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
			frmHistory.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));
			TableData tableData=TTKCommon.getTableData(request);
			if(strLink.equals("Pre-Authorization"))
			{
				strHistorylist = "prehistorylist";
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strHistorylist = "claimhistorylist";
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Account Info"))
			{
				String strSelectedRoot = (String)frmHistory.get("selectedroot");
				String strSelectedNode = (String)frmHistory.get("selectednode");
				strHistorylist = "accountinfohistorylist";
				if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
				{
					memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
					String strName =  memberVO.getName();
					String strName1 =  memberVO.getName();
					strClaimantName =  strName.substring(0,strName.indexOf("/")-1).trim();
					strWebBoardDesc = strName1.substring(strName1.indexOf("/")+1,strName1.lastIndexOf("/")-1).trim();
				}//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
			}//end of else if(strLink.equals("Online Forms"))
			if(!TTKCommon.checkNull(strClaimantName).equals("")){
				strCaption.append(" [ ").append(strClaimantName).append(" ]").toString();
			}//end of if(!TTKCommon.checkNull(strClaimantName).equals(""))
			if(strLink.equals("Account Info"))
			{
				frmHistory.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").toString());
			}//end of if(strLink.equals("Account Info"))
			else
			{
				frmHistory.set("caption",strCaption.append(strEnrollmentID).toString());
			}//end of else
			if(frmHistoryDetail != null){
				frmHistoryDetail.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").append(strEnrollmentID).toString());
			}//end of if(frmHistoryDetail != null)
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
			{
				strTableName = "HistoryTable";
			}//end of if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
			{
				strTableName = "PolicyTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
			{
				strTableName = "ClaimTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("ENS"))
			{
				strTableName = "EndorsementSummaryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("ENS"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("BST"))
			{
				strTableName = "BufferSummaryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("BST"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HMP"))
			{
				strTableName = "ManualPreAuthHistoryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HMP"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
			{
				strTableName = "CitibankEnrolHistoryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
			else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))
			{
				strTableName = "CitibankClaimsHistoryTable";
			}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strHistorylist);
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo(strTableName,null);
				if(strLink.equals("Claims")&&((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
				{
					//((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
					((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(true);
				}//end of if(strLink.equals("Claims")&&((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL")
				if(!(strLink.equals("Claims"))&&((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
				{
					//((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(true);
					((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(true);
				}//end of else
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)frmHistory,request));
				//sorting based on PAT_RECEIVED_DATE in descending order
				if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
				{
					tableData.setSortData("0001");
					tableData.setSortColumnName("PAT_RECEIVED_DATE");
					tableData.setSortOrder("DESC");
				}//end of if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
				else
				{
					tableData.setSortData("0001");
					tableData.setSortOrder("DESC");
				}//end of else
				tableData.modifySearchData("search");
			}//end of else
			if(!strLink.equals("Account Info"))
			{
				 alHistoryList= memberHistoryManagerObject.getHistoryList(tableData.getSearchData());
			}//end of if(!strLink.equals("Account Info"))
			else
			{
				alHistoryList= accountInfoManagerObject.getHistoryList(tableData.getSearchData());
			}//end of else
			tableData.setData(alHistoryList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strHistorylist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside HistoryAction doForward");
			MemberHistoryManager memberHistoryManagerObject=this.getMemberHistoryManagerObject();
			AccountInfoManager accountInfoManagerObject=this.getAccountInfomanagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			TreeData treeData = TTKCommon.getTreeData(request);
			DynaActionForm frmHistoryDetail=(DynaActionForm)request.getAttribute("frmHistoryDetail");
			MemberVO memberVO = null;
			String strHistorylist = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			String strEnrollmentID = "";
			ArrayList alHistoryList= new ArrayList();
			StringBuffer strCaption=new StringBuffer();
			DynaActionForm frmHistory=(DynaActionForm)form;
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)frmHistory).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			frmHistory.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
			frmHistory.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));
			TableData tableData=TTKCommon.getTableData(request);
			if(strLink.equals("Pre-Authorization"))
			{
				strHistorylist = "prehistorylist";
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strHistorylist = "claimhistorylist";
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Account Info"))
			{
				String strSelectedRoot = (String)frmHistory.get("selectedroot");
				String strSelectedNode = (String)frmHistory.get("selectednode");
				strHistorylist = "accountinfohistorylist";
				if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
				{
					memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
					String strName =  memberVO.getName();
					String strName1 =  memberVO.getName();
					strClaimantName =  strName.substring(0,strName.indexOf("/")-1).trim();
					strWebBoardDesc = strName1.substring(strName1.indexOf("/")+1,strName1.lastIndexOf("/")-1).trim();
				}//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
			}//end of else if(strLink.equals("Online Forms"))
			if(!TTKCommon.checkNull(strClaimantName).equals("")){
				strCaption.append(" [ ").append(strClaimantName).append(" ]").toString();
			}//end of if(!TTKCommon.checkNull(strClaimantName).equals(""))
			if(strLink.equals("Account Info"))
			{
				frmHistory.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").toString());
			}//end of if(strLink.equals("Account Info"))
			else
			{
				frmHistory.set("caption",strCaption.append(strEnrollmentID).toString());
			}//end of else
			if(frmHistoryDetail != null){
				frmHistoryDetail.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").append(strEnrollmentID).toString());
			}//end of if(frmHistoryDetail != null)
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			if(!strLink.equals("Account Info"))
			{
				 alHistoryList= memberHistoryManagerObject.getHistoryList(tableData.getSearchData());
			}//end of if(!strLink.equals("Account Info"))
			else
			{
				alHistoryList= accountInfoManagerObject.getHistoryList(tableData.getSearchData());
			}//end of else
			
			tableData.setData(alHistoryList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strHistorylist, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside HistoryAction doBackward");
			MemberHistoryManager memberHistoryManagerObject=this.getMemberHistoryManagerObject();
			AccountInfoManager accountInfoManagerObject=this.getAccountInfomanagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			TreeData treeData = TTKCommon.getTreeData(request);
			DynaActionForm frmHistoryDetail=(DynaActionForm)request.getAttribute("frmHistoryDetail");
			MemberVO memberVO = null;
			String strHistorylist = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			String strEnrollmentID = "";
			StringBuffer strCaption=new StringBuffer();
			DynaActionForm frmHistory=(DynaActionForm)form;
			ArrayList alHistoryList= new ArrayList();
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)frmHistory).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			frmHistory.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
			frmHistory.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));
			TableData tableData=TTKCommon.getTableData(request);
			if(strLink.equals("Pre-Authorization"))
			{
				strHistorylist = "prehistorylist";
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strHistorylist = "claimhistorylist";
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Account Info"))
			{
				String strSelectedRoot = (String)frmHistory.get("selectedroot");
				String strSelectedNode = (String)frmHistory.get("selectednode");
				strHistorylist = "accountinfohistorylist";
				if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
				{
					memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
					String strName =  memberVO.getName();
					String strName1 =  memberVO.getName();
					strClaimantName =  strName.substring(0,strName.indexOf("/")-1).trim();
					strWebBoardDesc = strName1.substring(strName1.indexOf("/")+1,strName1.lastIndexOf("/")-1).trim();
				}//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
			}//end of else if(strLink.equals("Online Forms"))
			if(!TTKCommon.checkNull(strClaimantName).equals("")){
				strCaption.append(" [ ").append(strClaimantName).append(" ]").toString();
			}//end of if(!TTKCommon.checkNull(strClaimantName).equals(""))
			if(strLink.equals("Account Info"))
			{
				frmHistory.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").toString());
			}//end of if(strLink.equals("Account Info"))
			else
			{
				frmHistory.set("caption",strCaption.append(strEnrollmentID).toString());
			}//end of else
			if(frmHistoryDetail != null){
				frmHistoryDetail.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").append(strEnrollmentID).toString());
			}//end of if(frmHistoryDetail != null)
			tableData.modifySearchData(strBackward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			if(!strLink.equals("Account Info"))
			{
				 alHistoryList= memberHistoryManagerObject.getHistoryList(tableData.getSearchData());
			}//end of if(!strLink.equals("Account Info"))
			else
			{
				alHistoryList= accountInfoManagerObject.getHistoryList(tableData.getSearchData());
			}//end of else
			tableData.setData(alHistoryList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strHistorylist, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
     * This method is used to get History Details.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside HistoryAction doView");
			MemberHistoryManager memberHistoryManagerObject=this.getMemberHistoryManagerObject();
			AccountInfoManager accountInfoManagerObject=this.getAccountInfomanagerObject();
			//check if user trying to hit the tab directly with out selecting the hospital
			String strLink = TTKCommon.getActiveLink(request);
			TreeData treeData = TTKCommon.getTreeData(request);
			DynaActionForm frmHistoryDetail=(DynaActionForm)request.getAttribute("frmHistoryDetail");
			PreAuthHistoryVO preAuthHistoryVO=null;
			PolicyHistoryVO policyHistoryVO=null;
			ClaimantHistoryVO claimantHistoryVO=null;
			CitibankHistoryVO citibankHistoryVO= null;
			MemberVO memberVO = null;
			Document historyDoc=null;
			String strFwdHistory = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			String strEnrollmentID = "";
			StringBuffer strCaption=new StringBuffer();
			DynaActionForm frmHistory=(DynaActionForm)form;
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)frmHistory).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			frmHistory.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
			frmHistory.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));
			TableData tableData=TTKCommon.getTableData(request);
			if(strLink.equals("Pre-Authorization"))
			{
				strFwdHistory = strPolicyHistory;
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strFwdHistory = strClaimPolicyHistory;
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Account Info"))
			{
				String strSelectedRoot = (String)frmHistory.get("selectedroot");
				String strSelectedNode = (String)frmHistory.get("selectednode");
				/*log.info("strSelectedRoot"+strSelectedRoot);
				log.info("strSelectedNode"+strSelectedNode);*/
				if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
				{
					strFwdHistory = strOnlinePolicyHistory;
					memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
					String strName =  memberVO.getName();
					String strName1 =  memberVO.getName();
					strClaimantName =  strName.substring(0,strName.indexOf("/")-1).trim();
					strWebBoardDesc = strName1.substring(strName1.indexOf("/")+1,strName1.lastIndexOf("/")-1).trim();
				}//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
				
			}//end of else if(strLink.equals("Online Forms"))
			if(!TTKCommon.checkNull(strClaimantName).equals("")){
				strCaption.append(" [ ").append(strClaimantName).append(" ]").toString();
			}//end of if(!TTKCommon.checkNull(strClaimantName).equals(""))
			frmHistory.set("caption",strCaption.toString());
			if(frmHistoryDetail != null){
				frmHistoryDetail.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").append(strEnrollmentID).toString());
			}//end of if(frmHistoryDetail != null)
			DynaActionForm frmHistoryList=(DynaActionForm)request.getSession().getAttribute("frmHistoryList");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{	
				if(((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("HPR")||((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("HMP"))
				{
					String strPreAuthHistoryTypeID = "";
					preAuthHistoryVO=(PreAuthHistoryVO)tableData.getRowInfo(Integer.parseInt(
																				request.getParameter("rownum")));
					//add the selected item to the web board and make it as default selected
					if(strLink.equals("Account Info"))
					{
						historyDoc=accountInfoManagerObject.getPolicyHistory((String)frmHistoryList.get(
						   							"PreAuthHistoryTypeID"),preAuthHistoryVO.getPreAuthSeqID(),
						   							preAuthHistoryVO.getEnrollDtlSeqID());
					}
					else
					{
						if(((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("HMP"))
						{
							strPreAuthHistoryTypeID="HPR";
						}//end of if(((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("HMP"))
						else
						{
							strPreAuthHistoryTypeID=(String)frmHistoryList.get("PreAuthHistoryTypeID");
						}//end of else
						historyDoc=memberHistoryManagerObject.getPolicyHistory(strPreAuthHistoryTypeID,preAuthHistoryVO.getPreAuthSeqID(),
							preAuthHistoryVO.getEnrollDtlSeqID());
					}//end of else
				}//end of if(((String)frmHistoryList.get("preAuthHistoryTypeID")).equals("HPR"))
				else if(((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("CEH"))
				{
					String strPreAuthHistoryTypeID = "";
					strPreAuthHistoryTypeID=(String)frmHistoryList.get("PreAuthHistoryTypeID");
					citibankHistoryVO=(CitibankHistoryVO)tableData.getRowInfo(Integer.parseInt(
							request.getParameter("rownum")));
					if(strLink.equals("Account Info"))
					{
						historyDoc=accountInfoManagerObject.getPolicyHistory(strPreAuthHistoryTypeID,citibankHistoryVO.getCitiEnrolSeqID()
								,new Long(0));
					}//end of if(strLink.equals("Account Info"))
					else
					{	
						historyDoc=memberHistoryManagerObject.getPolicyHistory(strPreAuthHistoryTypeID,citibankHistoryVO.getCitiEnrolSeqID()
							,new Long(0));
					}//end of else
				}//end of else if(((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("CEH"))
				else if(((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("CCH"))
				{
					String strPreAuthHistoryTypeID = "";
					strPreAuthHistoryTypeID=(String)frmHistoryList.get("PreAuthHistoryTypeID");
					citibankHistoryVO=(CitibankHistoryVO)tableData.getRowInfo(Integer.parseInt(
							request.getParameter("rownum")));
					if(strLink.equals("Account Info"))
					{
						historyDoc=accountInfoManagerObject.getPolicyHistory(strPreAuthHistoryTypeID,citibankHistoryVO.getCitiClmSeqID()
								,new Long(0));
					}//end of if(strLink.equals("Account Info"))
					else
					{	
						historyDoc=memberHistoryManagerObject.getPolicyHistory(strPreAuthHistoryTypeID,citibankHistoryVO.getCitiClmSeqID()
								,new Long(0));
					}//end of else
				}//end of else if(((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("CCH"))
				else if(((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("POL"))
				{
					policyHistoryVO=(PolicyHistoryVO)tableData.getRowInfo(Integer.parseInt(
																					request.getParameter("rownum")));
					if(strLink.equals("Account Info"))
					{
						historyDoc=accountInfoManagerObject.getPolicyHistory((String)
								frmHistoryList.get("PreAuthHistoryTypeID"),policyHistoryVO.getMemberSeqID(),new Long(0));
					}//end of if(strLink.equals("Account Info"))
					else
					{
						historyDoc=memberHistoryManagerObject.getPolicyHistory((String)
							frmHistoryList.get("PreAuthHistoryTypeID"),policyHistoryVO.getMemberSeqID(),new Long(0));
					}//end of else
					
				}//end of else if(((String)frmHistoryList.get("preAuthHistoryTypeID")).equals("POL"))
				if(((String)frmHistoryList.get("PreAuthHistoryTypeID")).equals("HCL"))
				{
					claimantHistoryVO=(ClaimantHistoryVO)tableData.getRowInfo(Integer.parseInt(
																	request.getParameter("rownum")));
					//add the selected item to the web board and make it as default selected
					if(strLink.equals("Account Info"))
					{
						historyDoc=accountInfoManagerObject.getPolicyHistory((String)frmHistoryList.get(
															"PreAuthHistoryTypeID"),claimantHistoryVO.getClaimSeqID(),
															claimantHistoryVO.getClmEnrollDtlSeqID());
					
					}//end of if(strLink.equals("Account Info"))
					else
					{
					historyDoc=memberHistoryManagerObject.getPolicyHistory((String)frmHistoryList.get(
															"PreAuthHistoryTypeID"),claimantHistoryVO.getClaimSeqID(),
															claimantHistoryVO.getClmEnrollDtlSeqID());
					}//end of else
				}//end of if(((String)frmHistoryList.get("preAuthHistoryTypeID")).equals("HPR"))
				//log.info("XML"+historyDoc.asXML());
				request.setAttribute("historyDoc",historyDoc);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			frmHistory.set("caption",frmHistoryDetail.getString("caption"));
			request.setAttribute("PreAuthHistoryTypeID",frmHistoryList.get("PreAuthHistoryTypeID"));
			return this.getForward(strFwdHistory,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
     * This method is used to Close the History screen.
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
														HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			String strLink = TTKCommon.getActiveLink(request);
			DynaActionForm frmHistoryDetail=(DynaActionForm)request.getAttribute("frmHistoryDetail");
			String strHistorylist = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			String strEnrollmentID = "";
			StringBuffer strCaption=new StringBuffer();
			DynaActionForm frmHistory=(DynaActionForm)form;
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)frmHistory).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			frmHistory.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
			frmHistory.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));
			if(strLink.equals("Pre-Authorization"))
			{
				strHistorylist = "prehistorylist";
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strHistorylist = "claimhistorylist";
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Account Info"))
			{
				strHistorylist = "accountinfohistorylist";
			}//end of else if(strLink.equals("Account Info"))
			
			if(!TTKCommon.checkNull(strClaimantName).equals("")){
				strCaption.append(" [ ").append(strClaimantName).append(" ]").toString();
			}//end of if(!TTKCommon.checkNull(strClaimantName).equals(""))
			frmHistory.set("caption",strCaption.append(strEnrollmentID).toString());
			if(frmHistoryDetail != null){
				frmHistoryDetail.set("caption",strCaption.append(" [ "+strWebBoardDesc+ "]").append(strEnrollmentID).toString());
			}//end of if(frmHistoryDetail != null)
			
			return this.getForward(strHistorylist,mapping,request);
			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
     * This method is used to Close the History screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
	public ActionForward doSummaryClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			String strHistorylist="memberdetails";
			//String strLink = TTKCommon.getActiveLink(request);
			return mapping.findForward(strHistorylist);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmHistory DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmHistory,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		Long lngSeqId = null;
		TreeData treeData = TTKCommon.getTreeData(request);
		MemberVO memberVO = null;
		String strEnrollmentID = "";
		try{
			String strLink = TTKCommon.getActiveLink(request);
			if(strLink.equals("Pre-Authorization"))
			{
				strEnrollmentID = PreAuthWebBoardHelper.getEnrollmentId(request);
				if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
				{
					alSearchParams.add("HPR");
					lngSeqId = PreAuthWebBoardHelper.getEnrollmentDetailId(request);    //PAT_ENROLL_DETAIL_SEQ_ID
				}//end of if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HMP"))
				{
					alSearchParams.add("HMP");
					lngSeqId = PreAuthWebBoardHelper.getEnrollmentDetailId(request);
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HMP"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
				{
					alSearchParams.add("POL");
					lngSeqId = PreAuthWebBoardHelper.getPolicySeqId(request);
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
				{
					alSearchParams.add("HCL");
					lngSeqId = PreAuthWebBoardHelper.getMemberSeqId(request);
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
				{
					alSearchParams.add("CEH");
					lngSeqId = null;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))
				{
					alSearchParams.add("CCH");
					lngSeqId = null;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strEnrollmentID = ClaimsWebBoardHelper.getEnrollmentId(request);

				if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
				{
					alSearchParams.add("HPR");
					lngSeqId = ClaimsWebBoardHelper.getEnrollDetailSeqId(request);  //PAT_ENROLL_DETAIL_SEQ_ID
				}//end of if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
				{
					alSearchParams.add("POL");
					lngSeqId = ClaimsWebBoardHelper.getPolicySeqId(request);
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
				{
					alSearchParams.add("HCL");
					lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
				{
					alSearchParams.add("CEH");
					lngSeqId = null;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))
				{
					alSearchParams.add("CCH");
					lngSeqId = null;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Account Info"))
			{
				Long strMemberSeqID = null;
				Long strPolicySeqID = null;
				String strSelectedRoot = (String)frmHistory.get("selectedroot");
				String strSelectedNode = (String)frmHistory.get("selectednode");

				if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
				{
					memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
					strEnrollmentID =  memberVO.getEnrollmentID();
					
				}//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
				else
				{
					OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)
															request.getSession().getAttribute("SelectedPolicy");
					strEnrollmentID = onlinePolicyVO.getEnrollmentNbr();
					
				}//end of else
				if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
				{
					alSearchParams.add("HPR");
					lngSeqId = strMemberSeqID;
				}//end of if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HPR"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
				{
					alSearchParams.add("POL");
					lngSeqId = strPolicySeqID;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("POL"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
				{
					alSearchParams.add("HCL");
					lngSeqId = strMemberSeqID;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("HCL"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("ENS"))
				{
					alSearchParams.add("ENS");
					lngSeqId = strPolicySeqID;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("ENS"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("BST"))
				{
					alSearchParams.add("BST");
					lngSeqId = strPolicySeqID;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("BST"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
				{
					alSearchParams.add("CEH");
					lngSeqId = null;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CEH"))
				else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))
				{
					alSearchParams.add("CCH");
					lngSeqId = null;
				}//end of else if(((String)frmHistory.get("PreAuthHistoryTypeID")).equals("CCH"))
			}//end of else if(strLink.equals("Acount Info"))
			
			if(!strLink.equals("Account Info"))
					alSearchParams.add(lngSeqId);
			alSearchParams.add(strEnrollmentID);
			if(!TTKCommon.checkNull(frmHistory.getString("StartDate")).equals("")){
				alSearchParams.add((String)frmHistory.get("StartDate"));
			}//end of if(!TTKCommon.checkNull(frmHistory.getString("StartDate")).equals(""))
			else{
				alSearchParams.add(null);
			}//end of else
			if(!TTKCommon.checkNull(frmHistory.getString("EndDate")).equals("")){
				alSearchParams.add((String)frmHistory.get("EndDate"));
			}//end of if(!TTKCommon.checkNull(frmHistory.getString("EndDate")).equals(""))
			else{
				alSearchParams.add(null);
			}//end of else
			if(!strLink.equals("Account Info"))
			{
				alSearchParams.add(TTKCommon.getUserSeqId(request));
			}//end of if(!strLink.equals("Account Info"))
		}catch(Exception e){ e.printStackTrace();}
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm searchFeedbackForm,HttpServletRequest request)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MemberHistoryManager getMemberHistoryManagerObject() throws TTKException
	{
		MemberHistoryManager memberHistoryManager = null;
		try
		{
			if(memberHistoryManager == null)
			{
				InitialContext ctx = new InitialContext();
				memberHistoryManager = (MemberHistoryManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberHistoryManagerBean!com.ttk.business.preauth.MemberHistoryManager");
			}//end of if(memberHistoryManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strHistoryError);
		}//end of catch
		return memberHistoryManager;
	}//end of getMemberHistoryManagerObject()
	
	/**
	 * Returns the AccountInfoManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private AccountInfoManager getAccountInfomanagerObject() throws TTKException
	{
		AccountInfoManager accountInfoManager = null;
		try
		{
			if(accountInfoManager == null)
			{
				InitialContext ctx = new InitialContext();
				accountInfoManager = (AccountInfoManager) ctx.lookup("java:global/TTKServices/business.ejb3/AccountInfoManagerBean!com.ttk.business.accountinfo.AccountInfoManager");
			}//end of if(memberHistoryManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strHistoryError);
		}//end of catch
		return accountInfoManager;
	}//end of getMemberHistoryManagerObject()
}//end of HistoryAction