/**
 * @ (#) InvoicesAction.java 26 Oct 2007
 * Project       : TTK HealthCare Services
 * File          : InvoicesAction.java
 * Author        : Yogesh S.C
 * Company       : Span Systems Corporation
 * Date Created  : 26 Oct 2007
 *
 * @author       : Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */

 package com.ttk.action.finance;

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
 import com.ttk.business.finance.FloatAccountManager;
 import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

 public class InvoicesAction extends TTKAction{
	private static Logger log = Logger.getLogger(InvoicesAction.class );
//	Modes of Float List
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//Action mapping forwards
	private static final String strinvoicesdetails="invoicesdetails";
	private static final String straddinvoices="addinvoices";	
	//Exception Message Identifier
	private static final String strInvoices="Invoices";

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
			setLinks(request);
			log.debug("Inside InvoicesAction doDefault");
			TableData  tableData =TTKCommon.getTableData(request);
			DynaActionForm frmInvoices = (DynaActionForm)form;
			
            frmInvoices.initialize(mapping);//reset the form data

            //create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("InvoicesTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
						
			return this.getForward(strinvoicesdetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strInvoices));
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
			setLinks(request);
			log.debug("Inside InvoiceAction doSearch");
			TableData  tableData =TTKCommon.getTableData(request);
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strinvoicesdetails));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				log.debug("inside search action");
				//create the required grid table
				tableData.createTableInfo("InvoicesTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.setSortData("0001");
				tableData.setSortColumnName("INVOICE_SEQ_ID");
				tableData.setSortOrder("DESC");
				tableData.modifySearchData("search");
			}//end of else

			ArrayList alInvoices= floatAccountManagerObject.getInvoiceList(tableData.getSearchData());
			tableData.setData(alInvoices, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strinvoicesdetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			//expTTK.getRootCause().printStackTrace();
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return this.processExceptions(request,mapping,new TTKException(exp,strInvoices));
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
    		setLinks(request);
    		log.debug("Inside InvoicesAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
			ArrayList alInvoiceList = floatAccountManagerObject.getInvoiceList(tableData.getSearchData());
			tableData.setData(alInvoiceList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strinvoicesdetails, mapping, request);//finally return to the grid screen
		}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strInvoices));
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
    					HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		TableData  tableData =TTKCommon.getTableData(request);
    		log.debug("Inside InvoicesAction doBackward");
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alInvoiceList = floatAccountManagerObject.getInvoiceList(tableData.getSearchData());
			tableData.setData(alInvoiceList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strinvoicesdetails, mapping, request);//finally return to the grid screen
		}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strInvoices));
		}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doAddInvoices(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAddInvoices method of InvoicesAction");
			setLinks(request);
			DynaActionForm frmInvoices = (DynaActionForm)form;
			frmInvoices.initialize(mapping);			
			return this.getForward(straddinvoices, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvoices));
		}//end of catch(Exception exp)
	}//end of doAddInvoices(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)
	
    /**
	 * Returns the FloatAccountManager session object for invoking methods on it.
	 * @return FloatAccountManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getfloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if(floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}//end of if(contactManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strInvoices);
		}//end of catch
		return floatAccountManager;
	}//end getfloatAccountManagerObject()
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmInvoices formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmInvoices,HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();		
		alSearchParams.add(TTKCommon.replaceSingleQots(frmInvoices.getString("sInvoiceNo")));
		alSearchParams.add(TTKCommon.replaceSingleQots(frmInvoices.getString("sFromDate")));
		alSearchParams.add(TTKCommon.replaceSingleQots(frmInvoices.getString("sToDate")));
		alSearchParams.add(TTKCommon.replaceSingleQots(frmInvoices.getString("sStatus")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmFloatAccounts,HttpServletRequest request)

}//end of class InvoicesAction
