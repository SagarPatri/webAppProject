/**
 * @ (#) IntimationAction.java March 17, 2008
 * Project 	     : TTK HealthCare Services
 * File          : IntimationAction.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : March 17, 2008
 *
 * @author       : Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.support;

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
import com.ttk.business.support.SupportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

/**
 * This class is reusable for searching of List of Investigations in support flow.
 *  This class also provides option for adding investigation information in support, pre-auth and claims flow.
 */
public class IntimationAction extends TTKAction  {

	private static Logger log = Logger.getLogger( IntimationAction.class ); // Getting Logger for this Class file

	private  static final String strForward="Forward";
	private  static final String strBackward="Backward";

	//  Action mapping forwards
	private  static final String strIntimation="intimlist";
	//private  static final String strInvestigationDetails="invsdetails";

	//Exception Message Identifier
    private static final String strIntimationError="Investigation";

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
			log.info("Inside IntimationAction  doDefault");
//			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
//											request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//DynaActionForm frmIntimationList=(DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("IntimationTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			//clear the dynaform if visting from left links for the first time
			((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strIntimation, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strIntimationError));
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
			SupportManager supportManager = this.getSupportManagerObject();
			ArrayList alIntimationList = null;
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
				if(strPageID.equals(""))
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data					
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strIntimation));
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("IntimationTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order
				
			}//end of else
			alIntimationList= supportManager.getIntimationList(tableData.getSearchData());
			tableData.setData(alIntimationList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strIntimation, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strIntimationError));
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
			SupportManager supportManager = this.getSupportManagerObject();
			ArrayList alIntimationList = null;
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			tableData.modifySearchData(strForward);//modify the search data
			alIntimationList = supportManager.getIntimationList(tableData.getSearchData());
			tableData.setData(alIntimationList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strIntimation, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strIntimationError));
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
			SupportManager supportManager = this.getSupportManagerObject();
			ArrayList alIntimationList = null;
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			tableData.modifySearchData(strBackward);//modify the search data
			alIntimationList = supportManager.getIntimationList(tableData.getSearchData());
			tableData.setData(alIntimationList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strIntimation, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strIntimationError));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			SupportManager supportManager = this.getSupportManagerObject();
			ArrayList alIntimationList = null;
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if(tableData.getSearchData()!=null && tableData.getSearchData().size()>0)
			{
				alIntimationList= supportManager.getIntimationList(tableData.getSearchData());
				tableData.setData(alIntimationList, "search");
				//set the table data object to session
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData()!=null && tableData.getSearchData().size()>0)
			return this.getForward(strIntimation, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strIntimationError));
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
		alSearchParams.add((String)frmInvestigation.get("StatusTypeID"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInvestigation.get("PolicyNo")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInvestigation.get("EnrollmentID")));
		alSearchParams.add((String)frmInvestigation.get("ReceivedAfter"));
		alSearchParams.add((String)frmInvestigation.get("RespondedDate"));
		alSearchParams.add(new Long(TTKCommon.getUserSeqId(request).toString()));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmInvestigation,HttpServletRequest request)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private SupportManager getSupportManagerObject() throws TTKException
	{
		SupportManager supportManager = null;
		try
		{
			if(supportManager == null)
			{
				InitialContext ctx = new InitialContext();
				supportManager = (SupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/SupportManagerBean!com.ttk.business.support.SupportManager");
			}//end of if(supportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strIntimationError);
		}//end of catch
		return supportManager;
	}//end of getsupportManagerObject()
}//end of IntimationAction