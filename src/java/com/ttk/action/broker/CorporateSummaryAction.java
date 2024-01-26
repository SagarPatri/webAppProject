/**
 *   @ (#) CorporateSummaryAction.java Dec 29, 2015
 *   Project 	   : Dubai Project
 *   File          : CorporateSummaryAction.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Dec 29, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */
package com.ttk.action.broker;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.broker.OnlineBrokerManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.brokerlogin.BrokerVO;

/**
 * This class is used for searching of pre-auth.
 * This class also provides option for deletion of pre-auth.
 */

public class CorporateSummaryAction extends TTKAction
{
	private static Logger log = Logger.getLogger(CorporateSummaryAction.class);
	//declrations of modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";

	//Action mapping forwards.
	private static final String strPolicyList="policyList";
	private static final String strPolicySelection="policySelection";
	 private static final String strCorporateSearch="corporateSearch";
	 private static final String strCorporateSummary="CorporateSummary";
	 
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
		try{
			setOnlineLinks(request);
			log.debug("Inside CorporateSummaryAction doDefault");
			DynaActionForm frmBroDashBoard=(DynaActionForm)form;
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("BrokerCorporateTable",new ArrayList<Object>());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)frmBroDashBoard).initialize(mapping);//reset the form data
			return this.getForward(strPolicyList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPolicyList));
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
																	HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateSummaryAction doSearch");
			setOnlineLinks(request);
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			//String broUserId=(String)request.getSession().getAttribute("broUserId");
			String brokerCode=(String)request.getSession().getAttribute("brokerCode");
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
					return mapping.findForward(strPolicyList);
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else{
				//create the required grid table
				tableData.createTableInfo("BrokerCorporateTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,brokerCode));
				//tableData.setSortColumnName("PRE_AUTH_NUMBER");
				//this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			ArrayList<Object> alPolicyList= brokerManager.getPolicyList(tableData.getSearchData());
			tableData.setData(alPolicyList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPolicyList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPolicyList));
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
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateSummaryAction doForward");
			setOnlineLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList<Object> alPolicyList = brokerManager.getPolicyList(tableData.getSearchData());
			tableData.setData(alPolicyList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strPolicyList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPolicyList));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
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
    public ActionForward goSelectedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the corporateSearch method of BrokerAction");
            setOnlineLinks(request);
            String strForward="";
            String btnMode=request.getParameter("btnMode");
            if("Summary".equals(btnMode))strForward="CorporateSummary";
            else if("Detailed".equals(btnMode))strForward="CorporateDetailedSearch";
            else if("Back".equals(btnMode))strForward=strPolicyList;
            return mapping.findForward(strForward);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPolicyList));
        }//end of catch(Exception exp)
    }//end of corporateSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doSummaryView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateSummaryAction doSummaryView");
			setOnlineLinks(request);
			HttpSession session=request.getSession();
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			DynaActionForm frmBroDashBoard=(DynaActionForm)form;
			String summaryView=frmBroDashBoard.getString("summaryView");
			String brokerCode=(String)session.getAttribute("brokerCode");
			BrokerVO	brokerVO=(BrokerVO)session.getAttribute("selectedBrokerVO");
			Long groupSeqID=brokerVO.getCorporateSeqID();
			HashMap<String,String> corporateSummaryMap = brokerManager.getSummaryViewDetails(brokerCode,"COR",groupSeqID,summaryView);
             session.setAttribute("corporateSummaryMap", corporateSummaryMap);
             session.setAttribute("frmBroDashBoard", frmBroDashBoard);
			return this.getForward(strCorporateSummary, mapping, request); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strCorporateSummary));
		}//end of catch(Exception exp)
	}//end of doSummaryView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside CorporateSummaryAction doBackward");
			setOnlineLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList<Object> alPolicyList = brokerManager.getPolicyList(tableData.getSearchData());
			tableData.setData(alPolicyList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strPolicyList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPolicyList));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doViewPolicyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateSummaryAction doViewPolicyDetails");
			setOnlineLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				BrokerVO brokerVO=(BrokerVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				request.getSession().setAttribute("selectedBrokerVO", brokerVO);
				//this.addToWebBoard(preAuthVO, request,strRegular);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strPolicySelection);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPolicyList));
		}//end of catch(Exception exp)
	}//end of doViewPolicyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)
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
    public ActionForward goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the goBack method of CorporateSummaryAction");
            setOnlineLinks(request);            
            DynaActionForm frmBroDashBoard =(DynaActionForm)form;
            frmBroDashBoard.initialize(mapping);     //reset the form data
            String strFarward="Broker.Home.DashBoard";
            if("Home".equals(request.getParameter("backID")))strFarward="Broker.Home.DashBoard";
            else if("SelectionView".equals(request.getParameter("backID")))strFarward=strPolicySelection;
            return mapping.findForward(strFarward);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPolicyList));
        }//end of catch(Exception exp)
    }//end of goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmBroDashBoard,String brokerCode)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBroDashBoard.getString("corporateName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBroDashBoard.getString("policyNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBroDashBoard.getString("policyYear")));
		alSearchParams.add(brokerCode);
    	return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

	/**
	 * Returns the BrokerManager session object for invoking methods on it.
	 * @return BrokerManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineBrokerManager getBrokerManagerObject() throws TTKException
	{
		OnlineBrokerManager brokerManager = null;
		try
		{
			if(brokerManager == null)
			{
				InitialContext ctx = new InitialContext();
				brokerManager = (OnlineBrokerManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineBrokerManagerBean!com.ttk.business.broker.OnlineBrokerManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strCorporateSearch);
		}//end of catch
		return brokerManager;
	}//end getBrokerManagerObject()
}//end of CorporateSummaryAction