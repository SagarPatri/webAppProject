
/**
  * @ (#) MISPaymentAdviceAction.java
  * Project      : TTK HealthCare Services
  * File         : MISPaymentAdviceAction.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : January 15,2009
  *
  * @author      : Ajay Kumar
  * Modified by  : Balakrishna Erram
  * Modified date: April 15, 2009
  * Company      : Span Infotech Pvt.Ltd.
  * Reason       : Code Review
  */

package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;

import com.ttk.business.misreports.ReportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.misreports.ReportsDataSource;
import com.ttk.dto.finance.ChequeVO;


public class MISPaymentAdviceAction extends TTKAction {

	private static final Logger log = Logger.getLogger(MISPaymentAdviceAction.class );

	//Modes of Float List
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//Action mapping forwards
	private static final String strAllFinanceReportsList="allfinancereportslist";
    private static final String strPaymentAdviceReport="orientalpaymentadvicereport";
    private static final String strReportdisplay="reportdisplay";
    private static final String strBank="bank";
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
    		HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside MISPaymentAdviceAction doDefault");
    		TableData  tableData =TTKCommon.getTableData(request);
    		//create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("PaymentBatchTable",new ArrayList<Object>());
    		request.getSession().setAttribute("tableData",tableData);
    		((DynaActionForm)form).initialize(mapping);//reset the form data
    		request.getSession().setAttribute("searchparam", null);
    		return this.getForward(strPaymentAdviceReport, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
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
    		HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside MISPaymentAdviceAction doSearch");
    		TableData  tableData =TTKCommon.getTableData(request);
    		ReportManager floatAccountManagerObject=this.getReportManager();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		//if the page number or sort id is clicked
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strPaymentAdviceReport));
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
    			tableData.createTableInfo("PaymentBatchTable",null);
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			tableData.setSortData("0101");
    			tableData.setSortColumnName("BATCH_DATE");
    			tableData.setSortOrder("DESC");
    			tableData.modifySearchData("search");
    		}//end of else

    		ArrayList<Object> alTransaction= floatAccountManagerObject.getViewPaymentAdviceList(tableData.getSearchData());
    		tableData.setData(alTransaction, "search");
    		//set the table data object to session
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strPaymentAdviceReport, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
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
    		HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside MISPaymentAdviceAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		ReportManager floatAccountManagerObject=this.getReportManager();
    		tableData.modifySearchData(strForward);//modify the search data
    		ArrayList<Object> alBankList = floatAccountManagerObject.getViewPaymentAdviceList(tableData.getSearchData());
    		tableData.setData(alBankList, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(strPaymentAdviceReport, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
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
    		HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside MISPaymentAdviceAction doBackward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		ReportManager floatAccountManagerObject=this.getReportManager();
    		tableData.modifySearchData(strBackward);//modify the search data
    		ArrayList<Object> alBankList = floatAccountManagerObject.getViewPaymentAdviceList(tableData.getSearchData());
    		tableData.setData(alBankList, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(strPaymentAdviceReport, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
	  * This method is used to generate the report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
public ActionForward doGenerateReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
        							  HttpServletResponse response) throws TTKException{
	try{
		log.debug("Inside the doGenerateReport method of MISPaymentAdviceAction");
		setLinks(request);
		//DynaActionForm frmReportDetail =(DynaActionForm)form;
		JasperReport jasperReport,emptyReport;
		JasperPrint jasperPrint;
		ReportsDataSource ttkReportsDataSource = null;
		Document reportsListDoc=null;
		String strBatchFileName = "";
		String reportID = "PAR";
		//String uPassword = "TTK";
		TableData  tableData =TTKCommon.getTableData(request);
		strBatchFileName = ((ChequeVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")))).getBatchNumber();
        log.info("File name information is strBatchFileName===:"+strBatchFileName);
        //ReportManager reportDetailObject=this.getReportManager();
		//get the reports list Document
		reportsListDoc=TTKCommon.getDocument("MisReports.xml");
		List reportsList = null;
		Element reportsElement = null;
		String strMaxValue = "";
		if(reportsListDoc != null)
		{
			reportsList = (List)reportsListDoc.selectNodes("//report");
			if(reportsList != null && reportsList.size() > 0)
			{
				for(int i=0 ;i<reportsList.size() ;i++)
				{
					reportsElement = (Element)reportsList.get(i);
					if((reportID).equalsIgnoreCase(reportsElement.valueOf("@type")))
					{
						strMaxValue = reportsElement.valueOf("@maxValue");

					}//end of if(request.getParameter("reportID").equalsIgnoreCase(reportsElement.valueOf("@type")))
				}//for(int i=0 ;i<reportsList.size() ;i++)
			}//if(reportsList != null && reportsList.size() > 0)
		}//if(reportsListDoc != null)

		String strParam = "|"+strBatchFileName+"|"+strMaxValue+"|";
		log.info("Parameter information is strParam===:"+strParam);
		String jrxmlfile=null;
		jrxmlfile="reports/misreports/PaymentAdviceOriental.jrxml";
		ttkReportsDataSource = new ReportsDataSource(strParam);
		try
		{
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			HashMap<String,String> hashMap = new HashMap<String,String>();
			ByteArrayOutputStream boas=new ByteArrayOutputStream();
			hashMap.put("strBatchFileName",strBatchFileName);
			hashMap.put("parameter",strParam.substring(2,strParam.length()));
			if(!(ttkReportsDataSource.getResultData().next()))
			{
				jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
			}// end of if(!(ttkReportsDataSource.getResultData().next()))
			else
			{

				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new ReportsDataSource(strParam));

			}// end of else
			    request.setAttribute("reportType","EXL");
			    JRXlsExporter jExcelApiExporter = new JRXlsExporter();
				jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
				//jExcelApiExporter.setParameter(JRXlsExporterParameter.PASSWORD, uPassword);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				jExcelApiExporter.exportReport();
			    request.setAttribute("boas",boas);
		}// end of try
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBank));
		}// end of catch(Exception exp)
		return (mapping.findForward(strReportdisplay));
	}// end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp, strBank));
	}// end of catch(Exception exp)
 }// end of doGenerateReport


    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmViewPayments formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmViewPayments,HttpServletRequest request)
    {
    	try{
    		//build the column names along with their values to be searched
    		ArrayList<Object> alSearchParams = new ArrayList<Object>();
    		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewPayments.get("sBatchNum")));
    		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewPayments.get("sBatchDate")));
    		return alSearchParams;
    	}catch(Exception exp)
    	{
    		return null;
    	}// end of catch(Exception exp)
    }//end of populateSearchCriteria(DynaActionForm frmFloatAccounts,HttpServletRequest request)


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
			                      HttpServletResponse response) throws TTKException{
		 try{
			 log.debug("Inside the doClose method of MISPaymentAdviceAction");
			 ((DynaActionForm)form).initialize(mapping);//reset the form data
			 return this.getForward(strAllFinanceReportsList,mapping,request);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }// end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
		 	 return this.processExceptions(request, mapping, new TTKException(exp, strBank));
		 }// end of catch(Exception exp)
	 }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	 /**
		 * Returns the ReportManager session object for invoking methods on it.
		 * @return ReportManager session object which can be used for method invokation
		 * @exception throws TTKException
		 */
	 private ReportManager getReportManager() throws TTKException
	    {
	       ReportManager reportManager = null;
	        try
	        {
	            if(reportManager == null)
	            {
	                InitialContext ctx = new InitialContext();
	               reportManager = (ReportManager) ctx.lookup("java:global/TTKServices/business.ejb3/ReportManagerBean!com.ttk.business.misreports.ReportManager");
	            }//end if
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, "tTKReport");
	        }//end of catch
	        return reportManager;
	    }//end getTTKReportManager()

}//end of MISPaymentAdviceAction
