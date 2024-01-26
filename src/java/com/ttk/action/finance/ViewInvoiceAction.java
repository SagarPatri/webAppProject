/**
 * @ (#) ViewInvoiceAction Nov 9, 2006
 * Project      : TTK HealthCare Services
 * File         : ViewInvoiceAction
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 9, 2006
 *
 * @author       : Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

/*
 * Created on Nov 9, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ttk.action.finance;
import java.io.File;
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
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.InvoiceBatchVO;

/**
 * This class is used for searching of List of Invoice.
 * This class also allows to view the xl report
 */
public class ViewInvoiceAction extends TTKAction
{
	private static Logger log = Logger.getLogger(ViewInvoiceAction.class );
	//Modes of Float List
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//Action mapping forwards
	private static final String strViewInvoiceDetails="viewinvoicedetails";
	private static final String strReportdisplay="reportdisplay";
	private static final String strFloatAcct="floataccount";

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
			log.debug("Inside ViewInvoiceAction : doDefault");
			TableData  tableData =TTKCommon.getTableData(request);

            ((DynaActionForm)form).initialize(mapping);//reset the form data
            //create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("ViewInvoiceTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("searchparam", null);
			return this.getForward(strViewInvoiceDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			TableData  tableData =TTKCommon.getTableData(request);
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			log.debug("Inside ViewInvoiceAction : doSearch");
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strViewInvoiceDetails));
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
				tableData.createTableInfo("ViewInvoiceTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.setSortData("0101");
				tableData.setSortColumnName("BATCH_DATE");
				tableData.setSortOrder("DESC");
				tableData.modifySearchData("search");
			}//end of else

			ArrayList alTransaction= floatAccountManagerObject.getViewInvoiceBatch(tableData.getSearchData());
			tableData.setData(alTransaction, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strViewInvoiceDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
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
			log.debug("Inside ViewInvoiceAction : doForward");
			TableData  tableData =TTKCommon.getTableData(request);
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alBankList = floatAccountManagerObject.getViewInvoiceBatch(tableData.getSearchData());
			tableData.setData(alBankList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strViewInvoiceDetails, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
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
			log.debug("Inside ViewInvoiceAction : doBackward");
			TableData  tableData =TTKCommon.getTableData(request);
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alBankList = floatAccountManagerObject.getViewInvoiceBatch(tableData.getSearchData());
			tableData.setData(alBankList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strViewInvoiceDetails, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
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
	public ActionForward doViewInvoiceXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside ViewInvoiceAction : doViewInvoiceXL");
			return (mapping.findForward(strReportdisplay));
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
		}//end of catch(Exception exp)
	}//end of doViewInvoiceXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
																						//response)

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
	public ActionForward doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside ViewInvoiceAction : doViewFile");
			String strBankType = "";
			TableData  tableData =TTKCommon.getTableData(request);
			strBankType = ((InvoiceBatchVO)tableData.getRowInfo(Integer.parseInt(
																	request.getParameter("rownum")))).getBatchName();
			File file = null;
			
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				String strAuthpdf = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strBankType+".xls";
				file = new File(strAuthpdf);
				if(file.exists())
				{
					strAuthpdf = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strBankType+".xls";
					request.setAttribute("fileName",strAuthpdf);
				}//end of if(file.exists())
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return this.getForward(strViewInvoiceDetails, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
		}//end of catch(Exception exp)
	}//end of doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the ContactManager session object for invoking methods on it.
	 * @return ContactManager session object which can be used for method invokation
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
			throw new TTKException(exp, strFloatAcct);
		}//end of catch
		return floatAccountManager;
	}//end getfloatAccountManagerObject()

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param  formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmViewInvoice,HttpServletRequest request)
	throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewInvoice.getString("sBatchNum")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewInvoice.get("sFrmdate")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewInvoice.get("sTDate")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmFloatAccounts,HttpServletRequest request)
}// end of ViewInvoiceAction.java


