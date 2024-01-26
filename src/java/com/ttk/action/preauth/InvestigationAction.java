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

package com.ttk.action.preauth;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import com.ttk.action.administration.Exception;
//import com.ttk.action.finance.Exception;
//import com.ttk.action.finance.String;
//import com.ttk.action.claims.Exception;
//import com.ttk.action.claims.Object;
//import com.ttk.action.claims.String;
import com.ttk.action.table.Column;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.coding.CodingManager;
import com.ttk.business.preauth.PreAuthManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import com.ttk.action.TTKAction;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.action.table.TableData;
import com.ttk.business.preauth.PreAuthSupportManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.preauth.InvestigationVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.SupportVO;
import java.util.Calendar;
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
	private static final int ASSIGN_ICON=10;

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
	
	//koc 11 koc11
	public ActionForward doSearchPC(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside InvestigationAction  doSearchPC");
		//	setLinks(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward("preclmlist");
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
				tableData.createTableInfo("PreAuthTable",null);
				tableData.setSearchData(this.populateSearchCriteriaPC((DynaActionForm)form,request));
				this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			request.getSession().setAttribute("preAuthSearchData",tableData.getSearchData());
			ArrayList alPreauthList= preAuthObject.getPreAuthList(tableData.getSearchData());
			tableData.setData(alPreauthList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			checkWebboardVisabulity(request);
			//finally return to the grid screen
			return this.getForward("preclmlist", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));  //preauthsearch
		}//end of catch(Exception exp)
	}//end of doSearchPC(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)  //koc 11 koc11
	private void setColumnVisiblity(TableData tableData,DynaActionForm frmPreClmSearch,HttpServletRequest request)
	throws TTKException
	{
		String strAssignTo=frmPreClmSearch.getString("sAssignedTo");
		boolean blnVisibility=false; //blnVisibility=false;
		if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))//For Self Check the Assign Permission
		{
			blnVisibility=true;
		}//end of if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
		if(TTKCommon.isAuthorized(request,"Investigation"))//For Self Check the Assign Permission Investigation
		{
			blnVisibility=true;
		}//end of if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
		else        //Check for the special Permission to show ICON for Others and Unassigned Pre-auth
		{
			if(TTKCommon.isAuthorized(request,"AssignAll"))
			{
				blnVisibility=true;
			}//end of if(TTKCommon.isAuthorized(request,"AssignAll"))
		}//end of else
		((Column)((ArrayList)tableData.getTitle()).get(ASSIGN_ICON)).setVisibility(blnVisibility);  
		((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false); 
	}//end of setColumnVisiblity(TableData tableData,DynaActionForm frmPreAuthList,HttpServletRequest request)



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
	//koc11 koc 11 s
	public ActionForward doForwardPC(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside InvestigationAction doForwardPC");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alPreauthList = preAuthObject.getPreAuthList(tableData.getSearchData());
			tableData.setData(alPreauthList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward("preclmlist", mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doForwardPC(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	public ActionForward doForwardCP(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForwardCP method of InvestigationAction");
			setLinks(request);
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alClaimsList = claimManagerObject.getClaimList(tableData.getSearchData());
			tableData.setData(alClaimsList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward("preclmlist", mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doForwardCP(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	//koc11 koc 11 e

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
	//koc11 koc 11 s 
	public ActionForward doBackwardPC(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside InvestigationAction doBackwardPC");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alPreauthList = preAuthObject.getPreAuthList(tableData.getSearchData());
			tableData.setData(alPreauthList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward("preclmlist", mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doBackwardPC(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	public ActionForward doBackwardCP(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doBackwardCP method of InvestigationAction");
			setLinks(request);
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alClaimsList = claimManagerObject.getClaimList(tableData.getSearchData());
			tableData.setData(alClaimsList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward("preclmlist", mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doBackwardCP(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	//koc11 koc 11 e

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
			if(strLink.equals("Pre-Authorization"))
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
			//koc11 koc 11
			else if(strLink.equals("Support"))
			{
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				log.info("------Support--->"+strClaimantName);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
													//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			//koc11 koc 11
			investigationVO = new InvestigationVO();
			if(strLink.equals("Claims"))
				investigationVO.setTypeDesc(strLink);
			DynaActionForm  frmInvestigation = (DynaActionForm)FormUtils.setFormValues("frmInvestigation",
																		investigationVO, this, mapping, request);
			if(!(TTKCommon.checkNull(strClaimantName).equals("")))
			strCaption.append(" - [ "+TTKCommon.checkNull(strClaimantName)+" ] "+"[ "+strWebBoardDesc+" ]").append(strEnrollmentID);
			
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
			String strActiveTab=TTKCommon.getActiveTab(request);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			InvestigationVO investigationVO = null;
			ArrayList<Object> alInvestigationList = null;
			String strModeForward = null;
			String supportlink = null;
			String strLinkMode = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			String strcliamntname= "";
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
			if(strLink.equals("Pre-Authorization"))
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
			//koc11 koc 11 s
			else if(strLink.equals("Support"))
			{
				strLinkMode = "PAT";
				if(strActiveTab.equals("General1"))
				{
					if(request.getSession().getAttribute("switchType").equals("Claim"))
					{
						strLinkMode = "CLM";
					}
					
				}
				else
				if(TTKCommon.checkNull(request.getParameter("TypeDesc")).equals("CLM"))
				{
					strLinkMode = "CLM";
				}
				
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				log.info("------Support--->"+strClaimantName);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && !"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
													//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			//koc11 koc 11 e
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
				/*			else if(strLink.equals("Support"))
				{
					supportlink = "Y";
					lngPreAuthSeqID = supportVO.getPreAuthSeqID();
					lngClaimsSeqID = supportVO.getClaimSeqID();
					strModeForward = "invsdetails";
				}//end of else if(strLink.equals("Support"))	//String strActiveTab=TTKCommon.getActiveTab(request);			*/
				else if(strActiveTab.equals("General1"))
				{
					
					if(request.getSession().getAttribute("switchType").equals("Claim"))
					{
						supportlink = "N";
						lngPreAuthSeqID = supportVO.getClaimSeqID();
						strModeForward = "invsdetailspre1";
					}
					else
					{
						supportlink = "N";
						lngPreAuthSeqID = supportVO.getPreAuthSeqID();
						strModeForward = "invsdetailspre1";
						log.info("............switchType......doview......	Claim------------>			"+lngPreAuthSeqID); 					
					}
				}
				else if(strLink.equals("Support"))
				{
					supportlink = "Y";
					lngPreAuthSeqID = supportVO.getPreAuthSeqID();
					lngClaimsSeqID = supportVO.getClaimSeqID();
					strClaimantName = supportVO.getInvestigationNo();
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
			request.getSession().setAttribute("invInvestigationVO",investigationVO);
			request.getSession().setAttribute("LinkMode",strLinkMode);
			
			DynaActionForm  frmInvestigation = (DynaActionForm)FormUtils.setFormValues("frmInvestigation",
																		investigationVO, this, mapping, request);
			if(!(TTKCommon.checkNull(strClaimantName).equals("")))
			strCaption.append(" - [ "+TTKCommon.checkNull(strClaimantName)+" ] "+"[ "+strWebBoardDesc+" ]").append(strEnrollmentID);
			
			frmInvestigation.set("caption",strCaption.toString());
			frmInvestigation.set("supportlink",supportlink);
			if(investigationVO.getTypeDesc() != null)
			{
				if(investigationVO.getTypeDesc().equals("Claims"))
					frmInvestigation.set("typeDescss","Y");//Claims				
				else
					frmInvestigation.set("typeDescss","N");	
			}
			request.setAttribute("frmInvestigation",frmInvestigation);
				
			log.info("....request.getAttribute(switchType);..."+request.getSession().getAttribute("switchType")) ; 
	//		request.getSession().setAttribute("switchType",null);
			log.info("....request.getAttribute(switchType);.after seting...."+request.getSession().getAttribute("switchType")) ; 
			
			String flag="flase";
			String investDate= (String)frmInvestigation.get("investDate");
			if(investDate!="")
			{
			Calendar c = Calendar.getInstance();
			//c.add(Calendar.DAY_OF_MONTH,8);
			//c.add(Calendar.DAY_OF_MONTH,-3);
			Date currectdate = c.getTime();
			
			
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date date =formatter.parse(investDate);
				
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(date);
				cal.add(Calendar.DATE, 7); // add 7 days  
				date = cal.getTime(); 
				
				if(date.compareTo(currectdate)>0)
				{
	        		flag="true";
	        	}else//(date.compareTo(d)>0)
	        	{
	        		
	        		flag="flase";
	        	}
				}catch (ParseException e) {
					e.printStackTrace();
				}
			}			
			
			request.setAttribute("flag", flag);    			
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
	// koc11 koc 11 koc11										
	
	public ActionForward doView1(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside InvestigationAction  doView1");
			
			setLinks(request);
			DynaActionForm frmPreClmSearch = (DynaActionForm)form;
			frmPreClmSearch.initialize(mapping);
			
			return mapping.findForward("invsdetails1");								
		}//end of try
	//	catch(Exception expTTK)
	//	{
			//return this.processExceptions(request, mapping, expTTK);
	//	}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	public ActionForward doDefaultPC(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside InvestigationAction  doDefaultPC");				
			DynaActionForm frmProductDetail = (DynaActionForm)form;
			frmProductDetail.initialize(mapping);
			if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.PreAuthorizationClaimNO.required");
				throw expTTK;
			}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			return mapping.findForward("invsdetails2");								
		}//end of try	
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}
	
	public ActionForward doView2(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside InvestigationAction  doView2");			
			setLinks(request);			
			return mapping.findForward("invsdetails2");								
		}//end of try	
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request
	
	public ActionForward doViewPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside InvestigationAction  doViewPreauth");
			String strSwitchType = (String)request.getSession().getAttribute("switchType");
			//strSwitchType=strSwitchType.concat("Polc");
			this.setLinks(request,strSwitchType);
			//setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPreClmSearch=(DynaActionForm)form;
			String s=request.getParameter("switchType");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				PreAuthVO preAuthVO=(PreAuthVO)tableData.getRowInfo(Integer.parseInt(
						(String)(frmPreClmSearch).get("rownum")));
				this.addToWebBoard(preAuthVO, request,"Regular");
				
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward("preauthdetail");
		}//end of try  frmPreClmSearch  frmPreAuthList
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
		}//end of catch(Exception exp)
	}//end of doViewPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	private void addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO,strIdentifier)); //set the cacheID
		cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
		alCacheObject.add(cacheObject);
		//if the object(s) are added to the web board, set the current web board id
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		log.info(".........cacheObject.getCacheId()"+cacheObject.getCacheId());
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
		//request.setAttribute("toolbar", toolbar);
		//webboardinvoked attribute will be set as true in request scope
		//to avoid the replacement of web board id with old value if it is called twice in same request scope
		request.setAttribute("webboardinvoked", "true");
	}//end of addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException

	private String prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws TTKException
	{
		StringBuffer sbfCacheId=new StringBuffer();
		sbfCacheId.append(preAuthVO.getPreAuthSeqID()!=null? String.valueOf(preAuthVO.getPreAuthSeqID()):" ");
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getEnrollmentID()).equals("")? " ":preAuthVO.getEnrollmentID());
		sbfCacheId.append("~#~").append(preAuthVO.getEnrollDtlSeqID()!=null? String.valueOf(preAuthVO.getEnrollDtlSeqID()):" ");
		sbfCacheId.append("~#~").append(preAuthVO.getPolicySeqID()!=null? String.valueOf(preAuthVO.getPolicySeqID()):" ");
		sbfCacheId.append("~#~").append(preAuthVO.getMemberSeqID()!=null? String.valueOf(preAuthVO.getMemberSeqID()):" ");
		if(strIdentifier.equals("Regular"))// to check it is a regular pre auth
		{
			sbfCacheId.append("~#~").append("PAT");//if it is a reular preauth then append with string identifier
		}//end of if(strIdentifier.equals(strRegular))
		else if(strIdentifier.equals("Enhanced"))//to check it is a enhanced preauth
		{
			if(preAuthVO.getEnhanceIconYN().equals("Y"))// to check whethere there is enhanced icon
			{
				sbfCacheId.append("~#~").append("ICO");
			}//end of if(preAuthVO.getEnhanceIconYN().equals("Y"))
			else
			{
				sbfCacheId.append("~#~").append("PAT");
			}//end of else
		}// end of else if(strIdentifier.equals(Enhanced))
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getClaimantName()).equals("")? " ":preAuthVO.getClaimantName());
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals("")? " ":preAuthVO.getBufferAllowedYN());
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getShowBandYN()).equals("")? " ":preAuthVO.getShowBandYN());
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getCoding_review_yn()).equals("")? " ":preAuthVO.getCoding_review_yn());
		return sbfCacheId.toString();
	}//end of prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws TTKException

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
			String strActiveTab=TTKCommon.getActiveTab(request);
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
			if(strLink.equals("Pre-Authorization"))
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
			//koc11 koc 11 s
			else if(strLink.equals("Support"))
			{
				strLinkMode = "PAT";
				
				if(strActiveTab.equals("General1"))
				{
					if(request.getSession().getAttribute("switchType").equals("Claim"))
					{
						strLinkMode = "CLM";
					}
					
				}
				else
				if(TTKCommon.checkNull(request.getParameter("TypeDesc")).equals("CLM"))
				{
					strLinkMode = "CLM";
				}						
				
			}//end of if(strLink.equals("Pre-Authorization")||strLink.equals("Support"))
			//koc11 koc 11 e
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
			else if(strActiveTab.equals("General1"))
			{
				if(request.getSession().getAttribute("switchType")!= null) //koc11 koc 11 s
				{
					supportlink = "N";
					strModeForward = "saveinvsdetails";
					investigationVO.setPreAuthSeqID((Long)request.getSession().getAttribute("PreAuthSeqID1"));
					investigationVO.setClaimSeqID((Long)request.getSession().getAttribute("ClaimSeqID"));
										
				}
			}
			else if(strLink.equals("Support"))
			{
				supportlink = "Y";
				strModeForward = "saveinvsdetails";
				if(investigationVO.getPreAuthSeqID() != null)
				strLinkMode = "PAT";
				else
				strLinkMode = "CLM";					
			}//end of else if(strLink.equals("Support"))
			Long lngResult;
			if(strActiveTab.equals("General1"))
			{
				lngResult =preAuthSupportManagerObject.saveInvestigationSupport(investigationVO);
				
			}
			else	
			{
				lngResult =preAuthSupportManagerObject.saveInvestigation(investigationVO);
				
			}
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
			
			if(strActiveTab.equals("General") && TTKCommon.getActiveSubLink(request).equals("Investigation"))
			{
				strClaimantName = TTKCommon.checkNull(investigationVO.getInvestigationNo());
				
				strCaption.append(" - [ "+strClaimantName+" ] ").append(strEnrollmentID);
			}
			else if(!(TTKCommon.checkNull(strClaimantName).equals("")))
			{
				strClaimantName = TTKCommon.checkNull(strClaimantName);
				strCaption.append(" - [ "+strClaimantName+" ] "+"[ "+strWebBoardDesc+" ]").append(strEnrollmentID);
			}
			frmInvestigationdetails.set("caption",strCaption.toString());
			frmInvestigationdetails.set("supportlink",supportlink);
			frmInvestigationdetails.set("typeDescss","N");	
			request.setAttribute("frmInvestigation",frmInvestigationdetails);
						String flag="false";
			String investDate= (String)frmInvestigationdetails.get("investDate");
			if(investDate!="")
			{
			Calendar c = Calendar.getInstance();
			//c.add(Calendar.DAY_OF_MONTH,8);
			//c.add(Calendar.DAY_OF_MONTH,-3);
			Date currectdate = c.getTime();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date date =formatter.parse(investDate);
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(date);
				cal.add(Calendar.DATE, 7); // add 7 days 
				date = cal.getTime(); 				
				if(date.compareTo(currectdate)>0)
				{
	        		
	        		flag="true";
	        	}else//(date.compareTo(d)>0)
	        	{
	        		
	        		flag="false";
	        	}
				}catch (ParseException e) {
					e.printStackTrace();
				}
			}			
			
			request.setAttribute("flag", flag);           
			
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
	
	//Uat
	public ActionForward doChangeInvAgency(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside Investigation Action doChangeInvAgency");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ArrayList alInvestigationList = null;
			TableData tableData=TTKCommon.getTableData(request);
			DynaActionForm frmInvestigation = (DynaActionForm) form;				
			int agencyId=11; //(int)frmInvestigation.get("investAgencyTypeID");
			String investRate=preAuthSupportManagerObject.getInvestRate(frmInvestigation.get("investAgencyTypeID"),frmInvestigation.get("typeDesc"));
			
			frmInvestigation.set("investRate",investRate);
			return this.getForward("invsdetails", mapping, request);
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
	//uAt e
	//koc11 koc 11
    public ActionForward doSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside Investigation Action doSwitchTo");
        	log.info("Inside Investigation Action doSwitchTo");
            DynaActionForm frmPreClmSearch=(DynaActionForm)form;  //get the DynaActionForm instance   
            TableData tableData = TTKCommon.getTableData(request);
            String strSwitchType=TTKCommon.checkNull((String)frmPreClmSearch.get("switchType"));
            String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
            this.setLinks(request,strSwitchType);
             tableData = new TableData();
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //initialize the formbean when user switches the mode
            frmPreClmSearch.initialize(mapping);
            frmPreClmSearch.set("sTtkBranch",strDefaultBranchID);
            frmPreClmSearch.set("switchType",strSwitchType);
            request.getSession().setAttribute("switchType",strSwitchType);
            request.setAttribute("switchTypes", strSwitchType);
           
            //load the appropriate table and set the column visibality
            if(strSwitchType.equals("PreAuth"))
            {
            	tableData.createTableInfo("PreAuthTable",null);
            }//end of if(strSwitchType.equals(strClaim))
            else if(strSwitchType.equals("Claim"))
            {
            	tableData.createTableInfo("ClaimsTable",null);
            }//end of else if(strSwitchType.equals(strPolicy))
            //build the caption
      //      frmPreClmSearch.set("caption",buildCaption(strSwitchType));
            frmPreClmSearch.set("caption","Search List");
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward("preclmlist", mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    private PreAuthManager getPreAuthManagerObject() throws TTKException
	{
		PreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strInvestigationError);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()
    
    private ArrayList<Object> populateSearchCriteriaPC(DynaActionForm frmPreAuthList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sPreAuthNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sHospitalName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEnrollmentId")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sClaimantName")));
		alSearchParams.add((String)frmPreAuthList.getString("sRecievedDate"));
		alSearchParams.add((String)frmPreAuthList.getString("sTtkBranch"));
		alSearchParams.add((String)frmPreAuthList.getString("sAssignedTo"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sSpecifyName")));
		alSearchParams.add((String)frmPreAuthList.getString("sAmount"));
		alSearchParams.add((String)frmPreAuthList.getString("sSource"));
		alSearchParams.add((String)frmPreAuthList.getString("sStatus"));
		alSearchParams.add((String)frmPreAuthList.getString("sWorkFlow"));
		alSearchParams.add((String)frmPreAuthList.getString("sPreAuthType"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sPolicyNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEmployeeNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sCorporateName")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(TTKCommon.getUserGroupList(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sSchemeName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sCertificateNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sInsurerAppStatus")));//bajaj
		alSearchParams.add("");//KOC FOR Grievance cigna
    	alSearchParams.add("");//KOC FOR Grievance cigna
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

    public ActionForward doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside InvestigationAction doCopyToWebBoard");
    		//setLinks(request);
    		String strSwitchType = (String)request.getSession().getAttribute("switchType");    		
    		this.setLinks(request,strSwitchType);
    		this.populateWebBoard(request);
    		return this.getForward("preclmlist", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
    	}//end of catch(Exception exp)
    }//end of CopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    
    private void populateWebBoard(HttpServletRequest request)throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		TableData tableData = (TableData)request.getSession().getAttribute("tableData");
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = null;
		PreAuthVO preAuthVO=null;

		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				cacheObject = new CacheObject();
				preAuthVO=(PreAuthVO)tableData.getData().get(Integer.parseInt(strChk[i]));
				cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO,"Regular"));
				cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
				alCacheObject.add(cacheObject);
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		if(toolbar != null)
		{
			toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		}//end of if(toolbar != null)
	}//end of populateWebBoard(HttpServletRequest request)
    
    // koc 11 doSearchPC koc11
    public ActionForward doSearchCP(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside InvestigationAction doSearchCP");
    		//setLinks(request);
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		//get the tbale data from session if exists
    		TableData tableData =TTKCommon.getTableData(request);
    		//clear the dynaform if visting from left links for the first time
    		//else get the dynaform data from session
    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		{
    			((DynaActionForm)form).initialize(mapping);//reset the form data
    		}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		//if the page number or sort id is clicked
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return mapping.findForward("preclmlist");
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
    			tableData.createTableInfo("ClaimsTable",null);
    			tableData.setSearchData(this.populateSearchCriteriaCP((DynaActionForm)form,request));
    			this.setColumnVisiblityCP(tableData,(DynaActionForm)form,request);
    			tableData.modifySearchData("search");
    		}//end of else
    		request.getSession().setAttribute("claimsSearchData",tableData.getSearchData());
    		ArrayList alClaimsList= claimManagerObject.getClaimList(tableData.getSearchData());
    		tableData.setData(alClaimsList, "search");
    		//set the table data object to session
    		request.getSession().setAttribute("tableData",tableData);
    		checkWebboardVisabulity(request);
    		//finally return to the grid screen
    		return this.getForward("preclmlist", mapping, request);
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
    
    private ClaimManager getClaimManagerObject() throws TTKException
    {
    	ClaimManager claimManager = null;
    	try
    	{
    		if(claimManager == null)
    		{
    			InitialContext ctx = new InitialContext();
    			claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
    		}//end if
    	}//end of try
    	catch(Exception exp)
    	{
    		throw new TTKException(exp, strInvestigationError);
    	}//end of catch
    	return claimManager;
    }//end getClaimManagerObject()
    
    private ArrayList<Object> populateSearchCriteriaCP(DynaActionForm frmClaimsList,HttpServletRequest request)
    {
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sInwardNumber")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sClaimNumber")));
    	alSearchParams.add((String)frmClaimsList.getString("sClaimType"));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sClaimSettelmentNumber")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sClaimFileNumber")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sClaimantName")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sEnrollmentId")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sPolicyNumber")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sCorporateName")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sEmployeeNumber")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sEmployeeName")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sPolicyHolderName")));
    	alSearchParams.add((String)frmClaimsList.getString("sAssignedTo"));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sSpecifyName")));
    	alSearchParams.add((String)frmClaimsList.getString("sStatus"));
    	alSearchParams.add((String)frmClaimsList.getString("sMode"));
    	alSearchParams.add((String)frmClaimsList.getString("sTtkBranch"));
    	alSearchParams.add((String)frmClaimsList.getString("sWorkFlow"));
    	alSearchParams.add(TTKCommon.getUserSeqId(request));
    	alSearchParams.add(TTKCommon.getUserGroupList(request));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sSchemeName")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sCertificateNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimsList.getString("sInsurerAppStatus")));//bajaj
		alSearchParams.add("");//KOC FOR Grievance cigna
    	alSearchParams.add("");//KOC FOR Grievance cigna
    	return alSearchParams;
    }//end of populateSearchCriteriaCP(DynaActionForm frmClaimsList,HttpServletRequest request)
    
    private void setColumnVisiblityCP(TableData tableData,DynaActionForm frmClaimList,HttpServletRequest request)
    throws TTKException
    {
    	String strAssignTo=frmClaimList.getString("sAssignedTo");
    	boolean blnVisibility=false;
    	//For Self Check the Assign Permission
    	if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
    	{
    		blnVisibility=true;
    	}//end of if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
    	
    	if(TTKCommon.isAuthorized(request,"Investigation"))//For Self Check the Assign Permission Investigation
		{    		
			blnVisibility=true;
			((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
		}
    	else         //Check for the special Permission to show ICON for Others and Unassigned Claim
    	{    		
    		if(TTKCommon.isAuthorized(request,"AssignAll"))
    		{
    			blnVisibility=true;
    		}//end of if(TTKCommon.isAuthorized(request,"AssignAll"))
    	}//end of else   	
    	
    	((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(true);
    }//end of setColumnVisiblity(TableData tableData,DynaActionForm frmPreAuthList,HttpServletRequest request)
    
    public ActionForward doCopyToWebBoardCP(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside InvestigationAction  doCopyToWebBoardCP");
    
    		//setLinks(request);
    		String strSwitchType = (String)request.getSession().getAttribute("switchType");    		
    		this.setLinks(request,strSwitchType);
    		this.populateWebBoardCP(request);
    		return this.getForward("claimslist", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationError));
    	}//end of catch(Exception exp)
    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    private void populateWebBoardCP(HttpServletRequest request)throws TTKException
    {
    	String[] strChk = request.getParameterValues("chkopt");
    	TableData tableData = (TableData)request.getSession().getAttribute("tableData");
    	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
    	ArrayList<Object> alCacheObject = new ArrayList<Object>();
    	CacheObject cacheObject = null;
    	PreAuthVO preAuthVO=null;
    
    	if(strChk!=null&&strChk.length!=0)
    	{
    		for(int i=0; i<strChk.length;i++)
    		{
    			cacheObject = new CacheObject();
    			preAuthVO=(PreAuthVO)tableData.getData().get(Integer.parseInt(strChk[i]));
    			cacheObject.setCacheId(this.prepareWebBoardIdCP(preAuthVO));
    			cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
    			alCacheObject.add(cacheObject);
    		}//end of for(int i=0; i<strChk.length;i++)
    	}//end of if(strChk!=null&&strChk.length!=0)
    	if(toolbar != null)
    	{
    		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
    	}//end of if(toolbar != null)
    }//end of populateWebBoard(HttpServletRequest request)
    
    private String prepareWebBoardIdCP(PreAuthVO preAuthVO)throws TTKException
    {
    	StringBuffer sbfCacheId=new StringBuffer();
    	sbfCacheId.append(preAuthVO.getClaimSeqID()!=null? String.valueOf(preAuthVO.getClaimSeqID()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getEnrollmentID()).equals("")?" ":preAuthVO.getEnrollmentID());
    	sbfCacheId.append("~#~").append(preAuthVO.getEnrollDtlSeqID()!=null?String.valueOf(preAuthVO.getEnrollDtlSeqID()):" ");
    	sbfCacheId.append("~#~").append(preAuthVO.getPolicySeqID()!=null? String.valueOf(preAuthVO.getPolicySeqID()):" ");
    	sbfCacheId.append("~#~").append(preAuthVO.getMemberSeqID()!=null? String.valueOf(preAuthVO.getMemberSeqID()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getClaimantName()).equals("")? " ":preAuthVO.getClaimantName());
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals("")? " ":preAuthVO.getBufferAllowedYN());
    	sbfCacheId.append("~#~").append(preAuthVO.getClmEnrollDtlSeqID()!=null? String.valueOf(preAuthVO.getClmEnrollDtlSeqID()):" ");
    	sbfCacheId.append("~#~").append(preAuthVO.getAmmendmentYN()!=null? String.valueOf(preAuthVO.getAmmendmentYN()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getCoding_review_yn()).equals("")? " ":preAuthVO.getCoding_review_yn());
    	log.info("Inside the prepareWebBoardIdCP method......... of InvestigationAction");
    	return sbfCacheId.toString();
    }//end of prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws TTKException

    public ActionForward doViewClaimCP(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside InvestigationAction  doViewClaimCP");
    		//setLinks(request);
    		String strSwitchType = (String)request.getSession().getAttribute("switchType");    		
    		this.setLinks(request,strSwitchType);
    		//get the tbale data from session if exists
    		TableData tableData =TTKCommon.getTableData(request);
    		DynaActionForm frmClaimsList=(DynaActionForm)form;
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			PreAuthVO preAuthVO=(PreAuthVO)tableData.getRowInfo(Integer.parseInt((String)
    					(frmClaimsList).get("rownum")));
    			this.addToWebBoardCP(preAuthVO, request);
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		return mapping.findForward("claimdetail");
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationError));
    	}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    private void addToWebBoardCP(PreAuthVO preAuthVO, HttpServletRequest request)throws TTKException
    {
    	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
    	ArrayList<Object> alCacheObject = new ArrayList<Object>();
    	CacheObject cacheObject = new CacheObject();
    	cacheObject.setCacheId(this.prepareWebBoardIdCP(preAuthVO)); //set the cacheID
    	cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
    	alCacheObject.add(cacheObject);
    	//if the object(s) are added to the web board, set the current web board id
    	toolbar.getWebBoard().addToWebBoardList(alCacheObject);
    	toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

    	//webboardinvoked attribute will be set as true in request scope
    	//to avoid the replacement of web board id with old value if it is called twice in same request scope
    	request.setAttribute("webboardinvoked", "true");
    }//end of addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException
    
    public ActionForward doSend(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("inside Investigation doSend");
    
    		PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			InvestigationVO investigationVO = null;
    		String strForward="saveinvsdetails";
    		
    		String strSupport="Support";
    		
    		StringBuffer strCaption= new StringBuffer();
    		DynaActionForm frmInvestigation= (DynaActionForm)form;
    		
    		String strIdentifier = "";
    		long strSequenceid=0; 
    		
    		if(strLink.equals(strSupport))
    		{
    			investigationVO = (InvestigationVO)FormUtils.getFormValues(frmInvestigation, this,mapping, request);
    			if(investigationVO.getStatusTypeID().equals("ISN"))
    			{   				
    
    						if(investigationVO.getTypeDesc().equals("Claims"))
    						{
    							strSequenceid=investigationVO.getClaimSeqID();
    							strIdentifier = "CLAIM_INVEST_STATUS";
    						}
    						if(investigationVO.getTypeDesc().equals("Pre-Authorization"))
    						{
    							strSequenceid=investigationVO.getPreAuthSeqID();
    							strIdentifier = "PREAUTH_INVEST_STATUS";
    						}
    			}

    		}//end of if(strLink.equals(strSupport))

   		
    		Long lngUserID = TTKCommon.getUserSeqId(request);
    		
    		preAuthSupportManagerObject.sendInvestigation(strSequenceid,strIdentifier,lngUserID);
    		
    		return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strInvestigationError));
    	}//end of catch(Exception exp)
    }//end of doSendAuthorization(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    //HttpServletResponse response)
    private void checkWebboardVisabulity(HttpServletRequest request)throws TTKException
    {
  	    TableData tableData = (TableData)request.getSession().getAttribute("tableData");
  		
  		String strSwitchType="";
  		
  		strSwitchType=TTKCommon.checkNull((String)request.getSession().getAttribute("switchType"));
  		
  		this.setLinks(request,strSwitchType);
    }//if checkWebboardVisabulity
    
/*	public ActionForward doAdd1(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAdd1 method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmProductDetail = (DynaActionForm)form;
			frmProductDetail.initialize(mapping);
			frmProductDetail.set("caption","view");
			return this.getForward("strProductGeneralInfo", mapping, request);
		}//end of try	
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//	HttpServletResponse response)                  */      
  //koc11 koc 11 s
    public ActionForward doDefaultSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside Investigation Action doDefaultSwitchTo");
        	
            DynaActionForm frmPreClmSearch=(DynaActionForm)form;  //get the DynaActionForm instance   
            TableData tableData = TTKCommon.getTableData(request);
            String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
            String strSwitchType=TTKCommon.checkNull((String)frmPreClmSearch.get("switchType"));           
            
            //initialize the formbean when user switches the mode
            frmPreClmSearch.initialize(mapping);
            //frmPolicyList.set("sTtkBranch",strDefaultBranchID);
            frmPreClmSearch.set("switchType","Claim");  
            request.getSession().setAttribute("switchType","Claim");
            	tableData = new TableData();
            	tableData.createTableInfo("ClaimsTable",new ArrayList());                               
            	frmPreClmSearch.set("sTtkBranch",strDefaultBranchID);	
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward("preclmlist", mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doDefaultSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  //koc11 koc 11 e
    
    public ActionForward doCloseInv(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception
    		{
    	try
    	{
    		setLinks(request);
    		log.debug("Inside InvestigationAction doCloseInv");
    		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		
    		String strLink = TTKCommon.getActiveLink(request);
    		String strSubLink = TTKCommon.getActiveSubLink(request);
    		String strFwdMode = null;
    		TableData tableData =TTKCommon.getTableData(request);
    		//clear the dynaform if visiting from left links for the first time
    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
    			((DynaActionForm)form).initialize(mapping);//reset the form data
    		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
    		{
    			ArrayList alGeneralList=null;
    			if(strLink.equals("Support"))
    			{	
    				strFwdMode = "preclmlist";
    				if(request.getSession().getAttribute("switchType").equals("PreAuth"))
    				{    				
    					tableData.createTableInfo("PreAuthTable",null);
    					this.setColumnVisiblity(tableData,(DynaActionForm)form,request);    					
    					alGeneralList= preAuthObject.getPreAuthList((ArrayList)request.getSession().getAttribute("preAuthSearchData"));
    				}
    				else
    				{
    					tableData.createTableInfo("ClaimsTable",null);
    					this.setColumnVisiblityCP(tableData,(DynaActionForm)form,request);
    					alGeneralList= claimManagerObject.getClaimList((ArrayList)request.getSession().getAttribute("claimsSearchData"));
    				}
    			}//end of if(strLink.equals("Pre-Authorization"))   		    			
    			tableData.setData(alGeneralList,"search");
    			request.getSession().setAttribute("tableData",tableData);
    		}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
    		return this.getForward(strFwdMode, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, "closeInvestigation"));
    	}//end of catch(Exception exp)
    		}// end of doCloseInv(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

           
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