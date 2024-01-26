/**
 * @ (#) PolicySearchAction.java May 6th 2006
 * Project 		: TTK HealthCare Services
 * File 		: PolicySearchAction.java
 * Author 		: Krupa J
 * Company 		: Span Systems Corporation
 * Date Created : May 6th 2006
 *
 * @author 		: Krupa J
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.preauth;

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
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.ClaimantVO;

/**
 * This class is reusable for searching the policy information in pre-auth and claims flow.
 * This class also provides option for selecting the any policy information.
 */

public class PolicySearchAction extends TTKAction {

	private static Logger log = Logger.getLogger( PolicySearchAction.class );

	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private static final String strPreAuthDetail="preauthdetail";
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";

	//forwards
	private static final String strPreAuthPolicyList="preauthpolicylist";
	private static final String strClaimPolicyList="claimpolicylist";
	private static final String strClaimDetail="claimdetail";

	//Exception Message Identifier
    private static final String strPolicySearchError="policysearch";

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
			log.debug("Inside PolicySearchAction doDefault");
			TableData  tableData =TTKCommon.getTableData(request);
			String strList="";
			strList= this.getForwardPath(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("PolicySearchTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			return this.getForward(strList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strPolicySearchError));
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
			log.debug("Inside PolicySearchAction doSearch");
			TableData  tableData =TTKCommon.getTableData(request);
			String strList="";
			PolicyManager policyManagerObject=this.getPolicyManagerObject();
			strList= this.getForwardPath(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strPreAuthPolicyList));
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
				tableData.createTableInfo("PolicySearchTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alPolicy= policyManagerObject.getPreAuthPolicyList(tableData.getSearchData());
			tableData.setData(alPolicy, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strPolicySearchError));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to select the policy.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside PolicySearchAction doSelectPolicy");
			TableData  tableData =TTKCommon.getTableData(request);
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strDetail="";
			ClaimantVO claimantVO=null;
			DynaActionForm frmPreAuthGeneral=null;
			DynaActionForm frmClaimantDetails=null;
			Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
			if(strActiveLink.equals(strPreAuthorization))
			{
				strDetail=strPreAuthDetail;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			if(strActiveLink.equals(strClaims))
			{
				strDetail=strClaimDetail;
			}//end of if(strActiveLink.equals(strClaims))
			frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				claimantVO=(ClaimantVO)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
				frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
				//set the policy related information to the form
				frmClaimantDetails.set("policySeqID",claimantVO.getPolicySeqID().toString());
				frmClaimantDetails.set("policyNbr",claimantVO.getPolicyNbr());
				frmClaimantDetails.set("policyTypeID",claimantVO.getPolicyTypeID());
				frmClaimantDetails.set("termStatusID",claimantVO.getTermStatusID());
				frmClaimantDetails.set("policySubTypeID",claimantVO.getPolicySubTypeID());
				frmClaimantDetails.set("policyHolderName",claimantVO.getPolicyHolderName());
				frmClaimantDetails.set("phone",claimantVO.getPhone());
				frmClaimantDetails.set("startDate",TTKCommon.getFormattedDate(claimantVO.getStartDate()));
				frmClaimantDetails.set("endDate",TTKCommon.getFormattedDate(claimantVO.getEndDate()));
				frmClaimantDetails.set("companyName",claimantVO.getCompanyName());
				frmClaimantDetails.set("companyCode",claimantVO.getCompanyCode());
				frmClaimantDetails.set("insSeqID",claimantVO.getInsSeqID().toString());
				if(claimantVO.getGroupRegnSeqID()!=null)
				{
					frmClaimantDetails.set("groupRegnSeqID",claimantVO.getGroupRegnSeqID().toString());
				}//end of if(claimantVO.getGroupRegnSeqID()!=null)
				frmClaimantDetails.set("productSeqID",claimantVO.getProductSeqID().toString());
				frmClaimantDetails.set("groupID",claimantVO.getGroupID());
				frmClaimantDetails.set("groupName",claimantVO.getGroupName());
				frmClaimantDetails.set("insScheme",claimantVO.getInsScheme());
				frmClaimantDetails.set("certificateNo",claimantVO.getCertificateNo());
				frmPreAuthGeneral.set("frmChanged","changed");
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
																frmPreAuthGeneral.get("discPresentYN").equals("Y"));
			return this.getForward(strDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strPolicySearchError));
		}//end of catch(Exception exp)
	}//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)

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
			log.debug("Inside PolicySearchActio doForward");
			TableData  tableData =TTKCommon.getTableData(request);
			String strList="";
			PolicyManager policyManagerObject=this.getPolicyManagerObject();
			strList= this.getForwardPath(request);
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alPolicy= policyManagerObject.getPreAuthPolicyList(tableData.getSearchData());
			tableData.setData(alPolicy, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strPolicySearchError));
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
			log.debug("Inside PolicySearchAction doBackward");
			TableData  tableData =TTKCommon.getTableData(request);
			String strList="";
			PolicyManager policyManagerObject=this.getPolicyManagerObject();
			strList= this.getForwardPath(request);
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alPolicy= policyManagerObject.getPreAuthPolicyList(tableData.getSearchData());
			tableData.setData(alPolicy, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strPolicySearchError));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns a string which contains the Forward Path
	 * @param request HttpServletRequest object which contains information required for buiding the Forward Path
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String getForwardPath(HttpServletRequest request) throws TTKException{
		String strList="";
		String strActiveLink=TTKCommon.getActiveLink(request);
		try{
			if(strActiveLink.equals(strPreAuthorization))
			{
				strList=strPreAuthPolicyList;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			if(strActiveLink.equals(strClaims))
			{
				strList=strClaimPolicyList;
			}//end of if(strActiveLink.equals(strClaims))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPolicySearchError);
		}//end of catch
		return strList;
	}//end of getForwardPath(HttpServletRequest request)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPolicySearch DynaActionForm
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPolicySearch,HttpServletRequest request)
																							throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPolicySearch.get("policyNo")));
		alSearchParams.add((String)frmPolicySearch.get("insuranceSeqID"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPolicySearch.get("corporateName")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmPolicySearch)

	/**
	 * Returns the PolicyManager session object for invoking DAO methods on it.
	 * @return PolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PolicyManager getPolicyManagerObject() throws TTKException
	{
		PolicyManager policyManager = null;
		try
		{
			if(policyManager == null)
			{
				InitialContext ctx = new InitialContext();
				policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPolicySearchError);
		}//end of catch
		return policyManager;
	}//end getPolicyManagerObject()
}//end of PolicySearchAction