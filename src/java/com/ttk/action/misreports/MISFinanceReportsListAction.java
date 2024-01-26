/**
  * @ (#) MISFinanceReportsListAction.java
  * Project      :Vidal Health TPA
  * File         : MISFinanceReportsListAction.java
  * Author       : Balaji C R B
  * Company      :Vidal Health TPA Services
  * Date Created : 5th August,2008
  *
  * @author      : Balaji C R B
  * Modified by  : Balakrishna Erram
  * Modified date: April 15, 2009
  * Company      : Span Infotech Pvt.Ltd.
  * Reason       : Code Review
  */

package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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



























import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.business.reports.TTKReportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.ReportVO;

/**
 * This action class is used to display the Corporate Reports.
 */
public class MISFinanceReportsListAction extends TTKAction {

	private static final Logger log = Logger.getLogger( MISFinanceReportsListAction.class );

	//String for forwarding
    private static final String strAllFinanceReportsList="allfinancereportslist";
    private static final String strDOBOClaimsDetailReports="doboclaimsdetailreport";
    private static final String strFGPendingReports="fgpendingreport";
    private static final String strOrientalPaymentAdviceReport="orientalpaymentadvicereport";
    private static final String strCitibankClaimsDetailRpt="citibankclaimsdetailrpt";
    private static final String strUniSampoPendRpt="unisampopendrpt";

    private static final String strDetailReports="detailedReports";
    private static final String strBankAccReports="bankAccReports";
    private static final String strRevenueReport="revenueReport";
    private static final String strFinanceRemittanceReports="remittanceadviceReports";
    private static final String strReportdisplay ="reportdisplay";
    private static final String strReportExp="report";
    private static final String strFinanceExchangeRate = "exchangeRateReport";
    
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    
    /**
	  * This method is used to initialize the screen.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
    public ActionForward doDefault(ActionMapping mapping, ActionForm form,HttpServletRequest request,
    		                       HttpServletResponse response) throws TTKException {
    	try {
    		log.debug("Inside the doDefault method of MISFinanceReportsListAction");
    		setLinks(request);
    		if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		{
    			((DynaActionForm) form).initialize(mapping);// reset the form data
    		}//end of if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		return this.getForward(strAllFinanceReportsList, mapping, request);
    	}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}// end of catch(Exception exp)
	}// end of doDefault

    /**
	  * This method is used to initialize the report parameters screen.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
    public ActionForward doViewClaimsDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request,
    		                         HttpServletResponse response) throws TTKException {
		try {
			log.debug("Inside the doViewClaimsDetail method of MISFinanceReportsListAction");
			setLinks(request);
			((DynaActionForm) form).initialize(mapping);// reset the form data
			return this.getForward(strDOBOClaimsDetailReports, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}// end of catch(Exception exp)
	}// end of doViewClaimsDetail()

    /**
	  * This method is used to initialize the report parameters screen.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
   public ActionForward doViewFGPendingReport(ActionMapping mapping, ActionForm form,HttpServletRequest request,
   		                         HttpServletResponse response) throws TTKException {
		try {
			log.debug("Inside the doViewFGPendingReport method of MISFinanceReportsListAction");
			setLinks(request);
			((DynaActionForm) form).initialize(mapping);// reset the form data
			return this.getForward(strFGPendingReports, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}// end of catch(Exception exp)
	}// end of doViewFGPendingReport()
   /**
	  * This method is used to initialize the report parameters screen.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
 public ActionForward doViewOrientalPaymentAdvice(ActionMapping mapping, ActionForm form,HttpServletRequest request,
 		                         HttpServletResponse response) throws TTKException {
		try {
			log.debug("Inside the doViewOrientalPaymentAdvice method of MISFinanceReportsListAction");
			setLinks(request);
			if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
    			((DynaActionForm) form).initialize(mapping);// reset the form data
			}//end of if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			return this.getForward(strOrientalPaymentAdviceReport, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}// end of catch(Exception exp)
	}// end of doViewOrientalPaymentAdvice()
 
 /**
  * This method is used to initialize the Citibank Claims Detail report parameters screen.
  * Finally it forwards to the appropriate view based on the specified forward mappings
  * @param mapping The ActionMapping used to select this instance
  * @param form The optional ActionForm bean for this request (if any)
  * @param request The HTTP request we are processing
  * @param response The HTTP response we are creating
  * @return ActionForward Where the control will be forwarded, after this request is processed
  * @throws TTKException if any error occurs
  */
public ActionForward doCitibankClaimsDetailRpt(ActionMapping mapping, ActionForm form,HttpServletRequest request,
		                         HttpServletResponse response) throws TTKException {
	try {
		log.debug("Inside the doCitibankClaimsDetailRpt method of MISFinanceReportsListAction");
		setLinks(request);
		if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		{
			((DynaActionForm) form).initialize(mapping);// reset the form data
		}//end of if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		return this.getForward(strCitibankClaimsDetailRpt, mapping, request);
	}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}// end of catch(Exception exp)
}// end of doCitibankClaimsDetailRpt()

/**
 * This method is used to initialize the Citibank Claims Detail report parameters screen.
 * Finally it forwards to the appropriate view based on the specified forward mappings
 * @param mapping The ActionMapping used to select this instance
 * @param form The optional ActionForm bean for this request (if any)
 * @param request The HTTP request we are processing
 * @param response The HTTP response we are creating
 * @return ActionForward Where the control will be forwarded, after this request is processed
 * @throws TTKException if any error occurs
 */
public ActionForward doUniversalSampo(ActionMapping mapping, ActionForm form,HttpServletRequest request,
		                         HttpServletResponse response) throws TTKException {
	try {
		log.info("Inside the doUniversalSampo method of MISFinanceReportsListAction");
		setLinks(request);
		if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		{
			((DynaActionForm) form).initialize(mapping);// reset the form data
		}//end of if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		return this.getForward(strUniSampoPendRpt, mapping, request);
	}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}// end of catch(Exception exp)
}// end of doUniversalSampo()



/**
 * This method is used to initialize the report parameters screen.
 * Finally it forwards to the appropriate view based on the specified forward mappings
 * @param mapping The ActionMapping used to select this instance
 * @param form The optional ActionForm bean for this request (if any)
 * @param request The HTTP request we are processing
 * @param response The HTTP response we are creating
 * @return ActionForward Where the control will be forwarded, after this request is processed
 * @throws Exception if any error occurs
 */
public ActionForward doDetailReport(ActionMapping mapping, ActionForm form,HttpServletRequest request,
		                         HttpServletResponse response) throws TTKException {
	try {
		log.debug("Inside the doDetailReport method of MISFinanceReportsListAction");
		setLinks(request);
		((DynaActionForm) form).initialize(mapping);// reset the form data
		
		request.setAttribute("logicType", request.getParameter("logicType"));
		return this.getForward(strDetailReports, mapping, request);
	}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}// end of catch(Exception exp)
}// end of doDetailReport()
 
public ActionForward doRevenueReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException
{
	try {
		log.debug("Inside the doRevenueReport method of MISFinanceReportsListAction");
		//setLinks(request);
		((DynaActionForm) form).initialize(mapping);// reset the form data
		request.setAttribute("logicType", request.getParameter("logicType"));
		request.setAttribute("reptype", "Report Parameters");
		request.setAttribute("reportname", "Invoice Report For Revenue");
		return this.getForward(strRevenueReport,mapping,request);
	}// end of try
	catch (TTKException expTTK) {
		
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}// end of catch(Exception exp)
}// end of doRevenueReport



/**
 * This method is used to initialize the report parameters screen.
 * Finally it forwards to the appropriate view based on the specified forward mappings
 * @param mapping The ActionMapping used to select this instance
 * @param form The optional ActionForm bean for this request (if any)
 * @param request The HTTP request we are processing
 * @param response The HTTP response we are creating
 * @return ActionForward Where the control will be forwarded, after this request is processed
 * @throws Exception if any error occurs
 */
public ActionForward doBankAccountReport(ActionMapping mapping, ActionForm form,HttpServletRequest request,
		                         HttpServletResponse response) throws TTKException {
	try {
		log.debug("Inside the doBankAccountReport method of MISFinanceReportsListAction");
		setLinks(request);
		DynaActionForm frmMISFinanceReports	=	(DynaActionForm)form;
		frmMISFinanceReports.initialize(mapping);
		frmMISFinanceReports.set("switchType", "Hospital");
		request.setAttribute("logicType", request.getParameter("logicType"));
		return this.getForward(strBankAccReports, mapping, request);
	}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}// end of catch(Exception exp)
}// end of doDetailReport()
 



public ActionForward doRemittanceAdvice(ActionMapping mapping, ActionForm form,HttpServletRequest request,
        HttpServletResponse response) throws TTKException {
	try {
		log.debug("Inside the doRemittanceAdvice method of MISFinanceReportsListAction");
		setLinks(request);
		DynaActionForm frmMISFinanceReports	=	(DynaActionForm)form;
		frmMISFinanceReports.initialize(mapping);
	
		return this.getForward(strFinanceRemittanceReports, mapping, request);
	}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}// end of catch(Exception exp)
}// end of doDetailReport()
 

/*
 * Do Switch btn Policy and Hospitals
 */

public ActionForward doSwitchTo(ActionMapping mapping, ActionForm form,HttpServletRequest request,
		        HttpServletResponse response) throws TTKException {
	try {
		log.debug("Inside the doSwitchTo method of MISFinanceReportsListAction");
		setLinks(request);
		DynaActionForm frmMISFinanceReports	=	(DynaActionForm)form;
		String switchType	=	(String) frmMISFinanceReports.get("switchType");
		frmMISFinanceReports.initialize(mapping);
		frmMISFinanceReports.set("switchType", switchType);
		return this.getForward(strBankAccReports, mapping, request);
	}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
	return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}// end of catch(Exception exp)
}// end of doDetailReport()



public ActionForward doGenerateDetailRemittanceReport(ActionMapping mapping, ActionForm form,HttpServletRequest request,
        HttpServletResponse response) throws TTKException {
	
	 try{
		log.info(" doGenerateDetailRemittanceReport in MISFinanceReportsListAction ");
		 setLinks(request);
		  //added for KOC-1276
		 try
		 { 
			 TTKReportManager tTKReportManager	=	null;
			 DynaActionForm frmMISReports	=	(DynaActionForm) form;
			 tTKReportManager	=	this.getTTKReportManager();
			 ArrayList alData	=	null;
			 String argList		=	request.getParameter("argList");
			 String repType		=	request.getParameter("repType");
			 alData				=	tTKReportManager.getDetailedReport(argList,repType);
			 	/*ReportVO reportVO	=	new ReportVO();
				ArrayList<String> al 	=	new ArrayList<>();
				ArrayList<ReportVO> al1 	=	new ArrayList<ReportVO>();
				al.add("kishor");
				al.add("kumar");
				reportVO.setAlColumns(al);
				al1.add(reportVO);*/
	
				request.setAttribute("alData", alData);
			 
			 return (mapping.findForward("financeutillization"));
		 }//end of try
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }//end of catch (Exception e)
		 return (mapping.findForward(strReportdisplay));
	 }//end of try
	 catch(TTKException expTTK)
	 {
		 return this.processExceptions(request, mapping, expTTK);
	 }//end of catch(TTKException expTTK)
	 catch(Exception exp)
	 {
		 return this.processExceptions(request, mapping, new TTKException(exp,"reportdetail"));
	 }//end of catch(Exception exp)

}


private TTKReportManager getTTKReportManager() throws TTKException
{
    TTKReportManager tTKReportManager = null;
    try
    {
        if(tTKReportManager == null)
        {
            InitialContext ctx = new InitialContext();
            tTKReportManager = (TTKReportManager) ctx.lookup("java:global/TTKServices/business.ejb3/TTKReportManagerBean!com.ttk.business.reports.TTKReportManager");
        }//end if
    }//end of try
    catch(Exception exp)
    {
        throw new TTKException(exp, "tTKReport");
    }//end of catch
    return tTKReportManager;
}//end getTTKReportManager() 







public ActionForward doExchangeRates(ActionMapping mapping, ActionForm form,HttpServletRequest request,
        HttpServletResponse response) throws TTKException {
	
	try {
		log.debug("Inside the doExchangeRates method of MISFinanceReportsListAction");
		setLinks(request);
		
		TableData tableData =TTKCommon.getTableData(request);
		
		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
			((DynaActionForm)form).initialize(mapping);//reset the form data
		}
		
		DynaActionForm frmMISFinanceReports	=	(DynaActionForm)form;
		
		tableData = new TableData();
		tableData.createTableInfo("ExchangeRatesTable",new ArrayList());
		request.getSession().setAttribute("tableData",tableData);
		((DynaActionForm)form).initialize(mapping);//reset the form data
	
		return this.getForward(strFinanceExchangeRate, mapping, request);
	}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}// end of catch(Exception exp)
	
}

public ActionForward doSearcExchangRates(ActionMapping mapping, ActionForm form,HttpServletRequest request,
        HttpServletResponse response) throws TTKException {
	
	try {
		log.debug("Inside the doSearcExchangRates method of MISFinanceReportsListAction");
		setLinks(request);
		
		 TTKReportManager tTKReportManager	=	null;
		TableData  tableData =TTKCommon.getTableData(request);
		tTKReportManager	=	this.getTTKReportManager();
		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		DynaActionForm frmMISFinanceReports = (DynaActionForm)form;
		String exchangeratesDate = frmMISFinanceReports.getString("exchangeratesDate");
		
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(strPageID.equals(""))
			{
				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData.modifySearchData("sort");//modify the search data
			}///end of if(!strPageID.equals(""))
			else
			{
				log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward(strFinanceExchangeRate));
			}//end of else
		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else
		{
			//create the required grid table
			tableData.createTableInfo("ExchangeRatesTable",null);
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)frmMISFinanceReports,request));
			tableData.modifySearchData("search");
		}//end of else
		

		
		if(exchangeratesDate.equals("")||exchangeratesDate.equals(null)){
			request.setAttribute("errorMsg", "Please Select Date");
			return this.getForward(strFinanceExchangeRate, mapping, request);
		}
		
		else{
			
			ArrayList alExchangeRaters=new ArrayList();
			
			   alExchangeRaters= tTKReportManager.getExchangeRatesList(tableData.getSearchData());	
				
			   tableData.setData(alExchangeRaters, "search");
				
			   request.getSession().setAttribute("tableData",tableData);
			
				return this.getForward(strFinanceExchangeRate, mapping, request);
			
		}
		
		
		
		
		
	
	}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}// end of catch(Exception exp)
	
}

private ArrayList<Object> populateSearchCriteria(DynaActionForm frmMISFinanceReports,HttpServletRequest request)  throws TTKException{
	
	
	ArrayList<Object> alSearchParams = new ArrayList<Object>();
	
	alSearchParams.add(frmMISFinanceReports.getString("exchangeratesDate"));
	alSearchParams.add(frmMISFinanceReports.getString("countryCode"));
	alSearchParams.add(TTKCommon.getUserSeqId(request));
	
    return alSearchParams;
    
}

public ActionForward doGenerateExchangeRatesReport(ActionMapping mapping, ActionForm form,HttpServletRequest request,
        HttpServletResponse response) throws TTKException {
			
	try {
		
		log.debug("Inside the doGenerateExchangeRatesReport method of MISFinanceReportsListAction");
		setLinks(request);
		
		DynaActionForm frmMISFinanceReports =(DynaActionForm)form;
		JasperReport mainJasperReport,emptyReport;
		TTKReportDataSource mainTtkReportDataSource = null;
		JasperPrint mainJasperPrint = null;
		
		String reportLink = request.getParameter("reportlinkname");
		String exchangeratesDate =  request.getParameter("exchangeratesDate");
		String countryCode =  request.getParameter("countryCode");
		Connection conn = null;
		ResultSet rsCurrencyType=null;
	    Statement stmt = null;
		 String currencyCode="";
		 conn = ResourceManager.getConnection();
		 stmt = conn.createStatement();
		 
		 if("".equals(countryCode)){
			 currencyCode="";
		 }
		 else{
			 String currencyType="select tc.currency_id from app.tpa_currency_code tc where tc.country_id="+countryCode;
			 rsCurrencyType = stmt.executeQuery(currencyType);
			 while(rsCurrencyType.next()){
				  currencyCode = rsCurrencyType.getString("currency_id");
			 }
			 rsCurrencyType.close();
			 stmt.close();
			 conn.close();
		 }
		
		String strParam = "|"+exchangeratesDate+"|"+currencyCode+"|";
		String jrxmlfile=null;
		jrxmlfile=request.getParameter("fileName");
		
		try {
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			HashMap<String,String> hashMap = new HashMap<String,String>();
			ByteArrayOutputStream boas=new ByteArrayOutputStream();
			hashMap.put("parameter",strParam.substring(2,strParam.length()));
			mainTtkReportDataSource = new TTKReportDataSource("exchangRatesReport", strParam);
			ResultSet main_report_RS = mainTtkReportDataSource.getResultData();
			mainJasperReport = JasperCompileManager.compileReport(jrxmlfile);

			if (main_report_RS != null && main_report_RS.next()) {
				main_report_RS.beforeFirst();
				mainJasperPrint = JasperFillManager.fillReport(mainJasperReport, hashMap, mainTtkReportDataSource);
				  				
			} else {
				
				mainJasperPrint = JasperFillManager.fillReport(emptyReport,hashMap, new JREmptyDataSource());
			}
			
			if("EXL".equals(request.getParameter("reportType")))
    		{
			JRXlsExporter jExcelApiExporter = new JRXlsExporter();
			jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,mainJasperPrint);
			jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
			jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			jExcelApiExporter.exportReport();
			
    		}
			
			request.setAttribute("boas",boas);
			frmMISFinanceReports.set("letterPath", reportLink);
			
		} 
		
		catch(Exception exp)
    	{
			return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
    	}
		return (mapping.findForward(strReportdisplay));
		
	} 
	
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
	}
}


public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
		setLinks(request);
		log.debug("Inside the doBackward method of MISFinanceReportsListAction");
		TableData tableData = TTKCommon.getTableData(request);
		 TTKReportManager tTKReportManager	=	null;
			tTKReportManager	=	this.getTTKReportManager();
		tableData.modifySearchData(strBackward);//modify the search data
		//fetch the data from the data access layer and set the data to table object
		ArrayList alExchangeRaters=new ArrayList();
	   alExchangeRaters= tTKReportManager.getExchangeRatesList(tableData.getSearchData());	
		tableData.setData(alExchangeRaters, strBackward);//set the table data
		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
		TTKCommon.documentViewer(request);
		return this.getForward(strFinanceExchangeRate, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,"reportdetail"));
	}//end of catch(Exception exp)
}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)




public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
		setLinks(request);
		log.debug("Inside the doForward method of MISFinanceReportsListAction");
		TableData tableData = TTKCommon.getTableData(request);
		 TTKReportManager tTKReportManager	=	null;
			tTKReportManager	=	this.getTTKReportManager();
		tableData.modifySearchData(strForward);//modify the search data
		//fetch the data from the data access layer and set the data to table object
		ArrayList alExchangeRaters=new ArrayList();
	   alExchangeRaters= tTKReportManager.getExchangeRatesList(tableData.getSearchData());	
		tableData.setData(alExchangeRaters, strForward);//set the table data
		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
		TTKCommon.documentViewer(request);
		return this.getForward(strFinanceExchangeRate, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,"reportdetail"));
	}//end of catch(Exception exp)
}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)







}//end of MISFinanceReportsListAction
