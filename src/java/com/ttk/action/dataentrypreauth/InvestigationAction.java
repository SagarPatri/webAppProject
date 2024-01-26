/**
 * @ (#) InvestigationAction.java May 4, 2006
 * Project 	     : TTK HealthCare Services
 * File          : InvestigationAction.java
 * Author        : Raghavendra T M
 * Company       : Span Systems Corporation
 * Date Created  : May 4, 2006
 *
 * @author       : Raghavendra T M
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
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.preauth.PreAuthSupportManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.preauth.InvestigationVO;
import com.ttk.dto.preauth.SupportVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for searching of List of Investigations in support flow.
 *  This class also provides option for adding investigation information in support, pre-auth and claims flow.
 */

public class InvestigationAction extends TTKAction  {

	private static Logger log = Logger.getLogger( InvestigationAction.class ); // Getting Logger for this Class file

	private  static final String strForward="Forward";
	private  static final String strBackward="Backward";

	//  Action mapping forwards
	private  static final String strInvestigation="invslist";
	private  static final String strInvestigationDetails="invsdetails";

	//Exception Message Identifier
    private static final String strInvestigationError="Investigation";

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
			log.debug("Inside InvestigationAction  doDefault");
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmInvestigation=(DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("InvestigationTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			//clear the dynaform if visting from left links for the first time
			((DynaActionForm)form).initialize(mapping);//reset the form data
			frmInvestigation.set("OfficeSeqID",strDefaultBranchID);
			return this.getForward(strInvestigation, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside InvestigationAction  doSearch");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ArrayList alInvestigationList = null;
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))

			//else get the dynaform data from session
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strInvestigation));
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
				tableData.createTableInfo("InvestigationTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				//sorting based on investSeqID in descending order
				tableData.setSortData("0001");
				tableData.setSortColumnName("INVEST_SEQ_ID");
				tableData.setSortOrder("DESC");
				tableData.modifySearchData("search");
			}//end of else
			alInvestigationList= preAuthSupportManagerObject.getSupportInvestigationList(tableData.getSearchData());
			tableData.setData(alInvestigationList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strInvestigation, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
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
			log.debug("Inside InvestigationAction doForward");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ArrayList alInvestigationList = null;
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			tableData.modifySearchData(strForward);//modify the search data
			alInvestigationList = preAuthSupportManagerObject.getSupportInvestigationList(tableData.getSearchData());
			tableData.setData(alInvestigationList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strInvestigation, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doInvestigationList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
			log.debug("Inside InvestigationAction  doBackward");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ArrayList alInvestigationList = null;
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			tableData.modifySearchData(strBackward);//modify the search data
			alInvestigationList = preAuthSupportManagerObject.getSupportInvestigationList(tableData.getSearchData());
			tableData.setData(alInvestigationList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strInvestigation, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
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
															HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside Investigation Action doAdd");
			String strLink = TTKCommon.getActiveLink(request);
			InvestigationVO investigationVO = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			StringBuffer strCaption = new StringBuffer();
			String strEnrollmentID="";
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			{
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
												//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			else if(strLink.equals("Claims"))
			{
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && !"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && !"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
													//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			investigationVO = new InvestigationVO();
			DynaActionForm  frmInvestigation = (DynaActionForm)FormUtils.setFormValues("frmInvestigation",
																		investigationVO, this, mapping, request);
			strCaption.append(" - [ "+strClaimantName+" ] "+"[ "+strWebBoardDesc+" ]").append(strEnrollmentID);
			frmInvestigation.set("caption",strCaption.toString());
			frmInvestigation.set("editYN","Y");
			frmInvestigation.set("supportlink","N");
			request.setAttribute("frmInvestigation",frmInvestigation);
			return mapping.findForward(strInvestigationDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
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
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside Investigation Action doView");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			InvestigationVO investigationVO = null;
			ArrayList<Object> alInvestigationList = null;
			String strModeForward = null;
			String supportlink = null;
			String strLinkMode = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			Long lngPreAuthSeqID = null;
			Long lngInvestSeqID = null;
			Long lngClaimsSeqID = null;
			StringBuffer strCaption = new StringBuffer();
			String strEnrollmentID="";
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			{
				strLinkMode = "PAT";
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
													//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && !"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && !"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
											//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			investigationVO = null;
			SupportVO supportVO = null;
			alInvestigationList = new ArrayList<Object>();
			// setting the batch details values based on batchSeqID from form/rownum to the appropriate modes
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				supportVO = (SupportVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				if(strLink.equals("Pre-Authorization"))
				{
					supportlink = "N";
					lngPreAuthSeqID = PreAuthWebBoardHelper.getPreAuthSeqId(request);
					strModeForward = "invsdetailspre";
				}//end of if(strLink.equals("Pre-Authorization"))
				else if(strLink.equals("Claims"))
				{
					supportlink = "N";
					//lngPreAuthSeqID = PreAuthWebBoardHelper.getPreAuthSeqId(request);
					lngClaimsSeqID = ClaimsWebBoardHelper.getClaimsSeqId(request);
					strModeForward = "invsdetailsclaims";
				}//end of else if(strLink.equals("Claims"))
				else if(strLink.equals("Support"))
				{
					supportlink = "Y";
					lngPreAuthSeqID = supportVO.getPreAuthSeqID();
					lngClaimsSeqID = supportVO.getClaimSeqID();
					strModeForward = "invsdetails";
				}//end of else if(strLink.equals("Support"))
				lngInvestSeqID = supportVO.getSeqID();
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.investigation.required");
				throw expTTK;
			}//end of else
			alInvestigationList.add(strLinkMode);
			alInvestigationList.add(lngInvestSeqID);
			alInvestigationList.add(lngPreAuthSeqID);
			alInvestigationList.add(lngClaimsSeqID);
			alInvestigationList.add(TTKCommon.getUserSeqId(request));

			investigationVO=preAuthSupportManagerObject.getInvestigationDetail(alInvestigationList);
			DynaActionForm  frmInvestigation = (DynaActionForm)FormUtils.setFormValues("frmInvestigation",
																		investigationVO, this, mapping, request);
			strCaption.append(" - [ "+strClaimantName+" ] "+"[ "+strWebBoardDesc+" ]").append(strEnrollmentID);
			frmInvestigation.set("caption",strCaption.toString());
			frmInvestigation.set("supportlink",supportlink);
			request.setAttribute("frmInvestigation",frmInvestigation);
			return this.getForward(strModeForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This methdo is used to save the Invs.
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
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside InvestigationAction  doSave");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			InvestigationVO investigationVO = null;
			ArrayList<Object> alInvestigationList = null;
			String strModeForward = null;
			String supportlink = null;
			String strLinkMode = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			StringBuffer strCaption = new StringBuffer();
			String strEnrollmentID="";
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			{
				strLinkMode = "PAT";
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
											//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && !"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && !"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
											//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			DynaActionForm frmInvestigation=(DynaActionForm)form;
			investigationVO=(InvestigationVO)FormUtils.getFormValues(frmInvestigation, "frmInvestigation",
																				this, mapping, request);
			investigationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			if(strLink.equals("Pre-Authorization"))
			{
				supportlink = "N";
				investigationVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				strModeForward = "saveinvsdetailspre";
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				supportlink = "N";
				investigationVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
				strModeForward = "saveclaiminvsdetails";
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Support"))
			{
				supportlink = "Y";
				strModeForward = "saveinvsdetails";
			}//end of else if(strLink.equals("Support"))
			Long lngResult =preAuthSupportManagerObject.saveInvestigation(investigationVO);
			if((lngResult!=0))
			{
				//setting updated message in the request
				if(!(TTKCommon.checkNull((String)frmInvestigation.get("investSeqID")).equals("")))
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}// end of if(!(TTKCommon.checkNull((String)frmInvestigation.get("investSeqID")).equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(iResult!=0)
			alInvestigationList = new ArrayList<Object>();
			alInvestigationList.add(strLinkMode);
			alInvestigationList.add(lngResult);
			alInvestigationList.add(investigationVO.getPreAuthSeqID());
			alInvestigationList.add(investigationVO.getClaimSeqID());
			alInvestigationList.add(TTKCommon.getUserSeqId(request));
			investigationVO=preAuthSupportManagerObject.getInvestigationDetail(alInvestigationList);
			DynaActionForm  frmInvestigationdetails = (DynaActionForm)FormUtils.setFormValues("frmInvestigation",
																			investigationVO, this, mapping, request);
			strCaption.append(" - [ "+strClaimantName+" ] "+"[ "+strWebBoardDesc+" ]").append(strEnrollmentID);
			frmInvestigationdetails.set("caption",strCaption.toString());
			frmInvestigationdetails.set("supportlink",supportlink);
			request.setAttribute("frmInvestigation",frmInvestigationdetails);
			return this.getForward(strModeForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Reset the values.
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
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside InvestigationAction doReset");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			InvestigationVO investigationVO = null;
			ArrayList<Object> alInvestigationList = null;
			String strModeForward = null;
			String supportlink = null;
			String strLinkMode = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			Long lngPreAuthSeqID = null;
			Long lngInvestSeqID = null;
			Long lngClaimsSeqID = null;
			StringBuffer strCaption = new StringBuffer();
			String strEnrollmentID="";
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			{
				strLinkMode = "PAT";
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
											//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && !"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && !"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
											//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
			DynaActionForm frmInvestigation=(DynaActionForm)form;
			investigationVO = new InvestigationVO();
			supportlink = "N";
			if(!(TTKCommon.checkNull(frmInvestigation.getString("investSeqID")).equals("")))
			{
				lngInvestSeqID=Long.parseLong(frmInvestigation.getString("investSeqID"));
				alInvestigationList = new ArrayList<Object>();
				//calling business layer to get the batch detail
				if(strLink.equals("Pre-Authorization"))
				{
					supportlink = "N";
					lngPreAuthSeqID = PreAuthWebBoardHelper.getPreAuthSeqId(request);
					strModeForward = "invsdetailspre";
				}//end of if(strLink.equals("Pre-Authorization"))
				else if(strLink.equals("Claims"))
				{
					supportlink = "N";
					//lngPreAuthSeqID = PreAuthWebBoardHelper.getPreAuthSeqId(request);
					lngClaimsSeqID = ClaimsWebBoardHelper.getClaimsSeqId(request);
					strModeForward = "invsdetailsclaims";
				}//end of else if(strLink.equals("Claims"))
				else if(strLink.equals("Support"))
				{
					supportlink = "Y";
					lngPreAuthSeqID = TTKCommon.getLong(frmInvestigation.getString("preAuthSeqID"));
					lngClaimsSeqID = TTKCommon.getLong(frmInvestigation.getString("claimSeqID"));
					strModeForward = "invsdetails";
				}//end of else if(strLink.equals("Support"))
				alInvestigationList.add(strLinkMode);
				alInvestigationList.add(lngInvestSeqID);
				alInvestigationList.add(lngPreAuthSeqID);
				alInvestigationList.add(lngClaimsSeqID);
				alInvestigationList.add(TTKCommon.getUserSeqId(request));
				investigationVO = preAuthSupportManagerObject.getInvestigationDetail(alInvestigationList);
			}//end of if(frmInvestigation.get("investSeqID")!=null && !frmInvestigation.get("investSeqID").equals(""))
			else
			{
				if(strLink.equals("Pre-Authorization"))
				{
					strModeForward = "invsdetailspre";//forwarding to add mode while resets
				}//end of if(strLink.equals("Pre-Authorization"))
				else if(strLink.equals("Claims"))
				{
					strModeForward = "invsdetailsclaims";//forwarding to add mode while resets
				}//end of else if(strLink.equals("Claims"))
			}//end of else
			DynaActionForm frmInvestigationdetails =(DynaActionForm)FormUtils.setFormValues("frmInvestigation",
																				investigationVO, this,mapping,request);
			// setting viewmode and caption in reset mode
			strCaption.append(" - [ "+strClaimantName+" ] "+"[ "+strWebBoardDesc+" ]").append(strEnrollmentID);
			frmInvestigationdetails.set("caption",strCaption.toString());
			frmInvestigationdetails.set("supportlink",supportlink);
			if((TTKCommon.checkNull(frmInvestigation.getString("investSeqID"))==null) ||
								(TTKCommon.checkNull(frmInvestigation.getString("investSeqID")).equals("")))
			{
				frmInvestigationdetails.set("editYN","Y");
			}//end of if
			request.setAttribute("frmInvestigation",frmInvestigationdetails);
			return mapping.findForward(strModeForward);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Close the Investidation detials.
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
			log.debug("Inside Investigation Action ......MODE is doClose");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ArrayList alInvestigationList = null;
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if(tableData.getSearchData()!=null && tableData.getSearchData().size()>0)
			{
				alInvestigationList= preAuthSupportManagerObject.getSupportInvestigationList(tableData.getSearchData());
				tableData.setData(alInvestigationList, "search");
				//set the table data object to session
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData()!=null && tableData.getSearchData().size()>0)
			return this.getForward(strInvestigation, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmCardPrinting DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmInvestigation,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInvestigation.get("InvestigationNo")));
		alSearchParams.add((String)frmInvestigation.get("MarkedDate"));
		alSearchParams.add((String)frmInvestigation.get("InvestAgencyTypeID"));
		alSearchParams.add((String)frmInvestigation.get("StatusTypeID"));
		alSearchParams.add((String)frmInvestigation.get("TypeDesc"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInvestigation.get("PreAuthClaimNo")));
		alSearchParams.add((String)frmInvestigation.get("OfficeSeqID"));
		alSearchParams.add(new Long(TTKCommon.getUserSeqId(request).toString()));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmInvestigation,HttpServletRequest request)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthSupportManager getPreAuthSupportManagerObject() throws TTKException
	{
		PreAuthSupportManager preAuthSupportManager = null;
		try
		{
			if(preAuthSupportManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthSupportManager = (PreAuthSupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthSupportManagerBean!com.ttk.business.preauth.PreAuthSupportManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strInvestigationError);
		}//end of catch
		return preAuthSupportManager;
	}//end of getPreAuthSupportManagerObject()
}//end of Investigation