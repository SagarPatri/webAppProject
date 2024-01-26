
package com.ttk.action.finance;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.icu.text.SimpleDateFormat;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.finance.ChequeVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
/**
 * This class is used for searching the Payment.
 * This class also provides option for generating the xl report.
 */

public class PaymentAdviceAction extends TTKAction
{
	private static Logger log = Logger.getLogger(PaymentAdviceAction.class );
	//Modes of Float List
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//Action mapping forwards
	private static final String stracclistdetails="acclistdetails";
	private static final String strReportdisplay="reportdisplay";
	private static final String strBank="bank";
	private static final String struploadbatchdetaillog="uploadbatchdetail";
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
			log.debug("Inside PaymentAdviceAction doDefault");
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.floatno.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			TableData  tableData =TTKCommon.getTableData(request);
			DynaActionForm frmFloatAccounts = (DynaActionForm)form;
			String strDefaultBranchID  = String.valueOf((
						(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("ClaimsSearchTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			frmFloatAccounts.set("sTTKBranch",strDefaultBranchID);
			return this.getForward(stracclistdetails, mapping, request);
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
    		HttpServletResponse response) throws Exception{
    	log.debug("Inside PaymentAdviceAction doChangeWebBoard");
    	return	doDefault(mapping,form,request,response);
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
    		HttpServletResponse response) throws Exception{
    	try{
    		
    		setLinks(request);
    		log.debug("Inside PaymentAdviceAction doSearch");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		DynaActionForm frmFloatAccounts = (DynaActionForm)form;
    		String strIncuredCurencyFormat  = TTKCommon.checkNull(frmFloatAccounts.getString("incuredCurencyFormat"));
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
    				log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(stracclistdetails));
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			//create the required grid table
    			tableData.createTableInfo("ClaimsSearchTable",null);
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			tableData.modifySearchData("search");
    		}//end of else
    		//start changes for cr koc1103 and 1105
    		String strPaymentmethod=(String)frmFloatAccounts.get("paymethod");
    		
    		ArrayList alTransaction=new ArrayList();
    		if(strPaymentmethod.equals("EFT"))
    		{
    			
    			alTransaction= floatAccountManagerObject.getEftPaymentAdviceList(tableData.getSearchData());	
    			
    		}
    		else
    		{
    			
    		alTransaction= floatAccountManagerObject.getPaymentAdviceList(tableData.getSearchData());
    		}
    		
    		
    		
    		
    		
    		//end changes for cr koc1103 and 1105
    		tableData.setData(alTransaction, "search");
    		if(!alTransaction.isEmpty()){
        		if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("CITI")){
        			frmFloatAccounts.set("sBankDesc","CITI");
        		}//end of if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("CITI")){
        		else if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("UTI")){
        			frmFloatAccounts.set("sBankDesc","UTI");
        		}//end of else if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("UTI"))
        		else if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("ICICI")){
        			frmFloatAccounts.set("sBankDesc","ICICI");
        		}//end of else if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("ICICI"))
        		else if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("HDFC")){
        			frmFloatAccounts.set("sBankDesc","HDFC");
        		}//end of else if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("HDFC"))
				else if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("BOA")){
        			frmFloatAccounts.set("sBankDesc","BOA");
        		}//end of else if(((ChequeVO)tableData.getRowInfo(0)).getBankName().equalsIgnoreCase("BOA"))
        		String strAbbCode = ((ChequeVO)tableData.getRowInfo(0)).getAbbrevationCode();
        		frmFloatAccounts.set("sAbbrCode",strAbbCode);
        		}//end of if(!alTransaction.isEmpty()){
    		//set the table data object to session
    		frmFloatAccounts.set("incuredCurencyFormat",strIncuredCurencyFormat);
    		request.getSession().setAttribute("tableData",tableData);
    		request.getSession().setAttribute("frmFloatAccounts",frmFloatAccounts);
    		//finally return to the grid screen
    		return this.getForward(stracclistdetails, mapping, request);
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
    					HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside PaymentAdviceAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		ArrayList alBankList = floatAccountManagerObject.getPaymentAdviceList(tableData.getSearchData());
    		tableData.setData(alBankList, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(stracclistdetails, mapping, request);//finally return to the grid screen
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
    							HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside PaymentAdviceAction doBackward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		ArrayList alBankList = floatAccountManagerObject.getPaymentAdviceList(tableData.getSearchData());
    		tableData.setData(alBankList, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(stracclistdetails, mapping, request);//finally return to the grid screen
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
    public ActionForward doPaymentAdviceXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    					HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside PaymentAdviceAction doPaymentAdviceXL");
    		return (mapping.findForward(strReportdisplay));
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doPaymentAdviceXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    																				//HttpServletResponse response)
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
    public ActionForward doGenerateUTIXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside PaymentAdviceAction onGenerateUTIXL");
    		String IpAddr=request.getRemoteHost();
    		log.info("Action doGenerateUTIXL start-->"+new Date()+"IPAdress-->"+IpAddr);
    		DynaActionForm frmFloatAccounts = (DynaActionForm)form;
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		JasperReport jasperReport = null;
    		JasperPrint jasperPrint = null;
    		ArrayList<Object> alGenerateXL = new ArrayList<Object>();
    		String strReportFormat = (String)((DynaActionForm)form).get("sreportFormat");
    		String strBankType = getBankType(request,(TableData)request.getSession().getAttribute("tableData"));
    		//String strSheetNames[] = null;
    		TTKReportDataSource reportDataSource = null;
    		Date dtCurrentDate = new Date();
    		String strPaymentSeqID = getConcatenatedSeqID(request,(TableData)
    				request.getSession().getAttribute("tableData"));
    		//prepare the parameters to generate the Payment Advice
    		alGenerateXL.add(strPaymentSeqID);
    		alGenerateXL.add(TTKCommon.getWebBoardId(request));
    		alGenerateXL.add(strBankType);
    		alGenerateXL.add(new Timestamp(dtCurrentDate.getTime()));
    		alGenerateXL.add(TTKCommon.getUserSeqId(request));
			// 5 param is strReportFormat added for Axis bank CR KOC1212
    		alGenerateXL.add(strReportFormat);
			
    		log.info("parameters for generating payment advice***********************************************");
    		log.info("Payment seq id : " + strPaymentSeqID);
    		log.info("bank type " +TTKCommon.getWebBoardId(request));
    		log.info("-------"+strBankType);
    		log.info("user seq id is " + TTKCommon.getUserSeqId(request));
    		log.info("***********end of parameter list*******************");
    		String strBatchNumer = strBankType+"-"
    		+TTKCommon.getFormattedDateTimeSec(dtCurrentDate)+"-"+TTKCommon.getUserSeqId(request);
    		String strFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strBankType+"-"
    		+TTKCommon.getFormattedDateTimeSec(dtCurrentDate)+"-"+TTKCommon.getUserSeqId(request)+".xls";
    		log.info("strBatchNumer is ::::::::: "+ strBatchNumer);

    		
    		File file1 = new File("generalreports/PayAdviceMaster.jrxml");
    		File file2 = new File("generalreports/PayAdviceDetail.jrxml");
    		
    		
    		if(strReportFormat.equalsIgnoreCase("UTI Mumbai New Format"))
    		{
    			log.info("inside if(((DynaActionForm)form).get(\"sreportFormat\").equals(\"UTI Mumbai New Format\")){");
    			jasperReport = JasperCompileManager.compileReport("generalreports/UTIPaymentAdviseSummaryMum.jrxml");
    		}// end of if(strReportFormat.equalsIgnoreCase("UTI Mumbai New Format"))
    		else if(strReportFormat.equalsIgnoreCase("Multi Location Format"))
    		{
    			log.info("inside else if(strReportFormat.equalsIgnoreCase(Multi Location Format))");
    			jasperReport = JasperCompileManager.compileReport("generalreports/UTIPaymentAdviseMultiLocation.jrxml");
    		}//end of else if(strReportFormat.equalsIgnoreCase("Multi Location Format"))
    		else if(strReportFormat.equalsIgnoreCase("OIC Report"))
    		{
    			log.info("inside else if(strReportFormat.equalsIgnoreCase(OIC Report))");
    			jasperReport = JasperCompileManager.compileReport("generalreports/PaymentAdviceOriental.jrxml");

    		}//end of else if(strReportFormat.equalsIgnoreCase("OIC Report"))
			// Change added for Axis Bank CR KOC1212
    		else if(strReportFormat.equalsIgnoreCase("Axis Pay Pro Advice"))
    		{
    			log.info("inside else if(strReportFormat.equalsIgnoreCase(Axis Pay Pro Advice))");
    			jasperReport = JasperCompileManager.compileReport("generalreports/AxisPayProAdvice.jrxml");
    		}//End change added for Axis Bank CR KOC1212  //end of else if(strReportFormat.equalsIgnoreCase("Axis Pay Pro Advice"))
    		else
    		{
    			log.info("inside else");
    			jasperReport = JasperCompileManager.compileReport("generalreports/UTIPaymentAdviseSummary.jrxml");
    		}// end of else

    		HashMap hashMap = new HashMap();
    		String strNewFile="";
    		String strSheetNames[]=null;
    		if(((ChequeVO)tableData.getRowInfo(0)).getBankName()!=null)
    		{
    			if(file1.exists() && file2.exists())
    			{    	
    				if(strReportFormat.equalsIgnoreCase("OIC Report"))
    				{
    					reportDataSource = floatAccountManagerObject.generateOICPaymentAdvice(alGenerateXL);
    					strSheetNames = new String[] {"PayementAdviceOriental"};
    				}
    				else{
    					reportDataSource = floatAccountManagerObject.generateChequeAdviceXL(alGenerateXL);
    					strSheetNames = new String[] {"Sheet1"};
    				}//end of else
    				//get the pending Payment Advice List after generating the XL sheet.
    				if(reportDataSource!=null)
    				{
    					tableData.modifySearchData("search");
    					ArrayList alPaymentAdvice= floatAccountManagerObject.getPaymentAdviceList(
    							tableData.getSearchData());
    					tableData.setData(alPaymentAdvice,"search");
    					//set the table data object to session
    					request.getSession().setAttribute("tableData",tableData);
    				}//end of if(reportDataSource!=null)
    			}//End of if(file1s.exists())
    			else
    			{
    				TTKException expTTK = new TTKException();
    				expTTK.setMessage("error.jrxmlfile.required");
    				throw expTTK;
    			}//end of else
    		}//end of if(((ChequeVO)tableData.getRowInfo(0)).getFileName()!=null)

    		if(!(reportDataSource.equals("null")))
    		{
    			request.setAttribute("GenerateXL","message.GenerateXLSuccessfully");
    		}//end of if(!(reportDataSource.equals("null")))


    		jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,reportDataSource);

    		if(strBankType.equalsIgnoreCase("UTI"))
    		{
    			strNewFile = floatAccountManagerObject.getBatchFileName(strBatchNumer);
    			strNewFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strNewFile+".xls";
    		}else
    		{
    			strNewFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strBankType+"-"
        		+TTKCommon.getFormattedDateTimeSec(dtCurrentDate)+"-"+TTKCommon.getUserSeqId(request)+".xls";
    		}
    		request.setAttribute("reportType","EXL");
    		JRXlsExporter exporterXL = new JRXlsExporter();
    		exporterXL.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    		exporterXL.setParameter(JRXlsExporterParameter.SHEET_NAMES, strSheetNames);
    		exporterXL.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strNewFile.toString());
    		exporterXL.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    		exporterXL.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    		exporterXL.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    		exporterXL.exportReport();
    		File fNewFile = new File(strNewFile);
    		if(fNewFile.exists())
    		{
    			request.setAttribute("fileName",strNewFile);
    		}//end of if(file.exists())
    		request.getSession().setAttribute("frmFloatAccounts",frmFloatAccounts);
    		log.info("Action doGenerateUTIXL end-->"+new Date()+"IP Adress"+IpAddr);
    		return this.getForward(stracclistdetails, mapping, request);
    	}catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doGenerateUTIXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    public ActionForward doPaymentAdviceUploadBatchdetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{ 
    	try{
            log.debug("Inside ClaimsSearchAction doPaymentAdviceUploadBatchdetail");
    		String strNotify = null;
    		String remarks = null;
    		String incurredCurrencyFormat=null;
    		String noOfClaimSettlementNumber=null;
    		Date dtCurrentDate = new Date();
    		HashMap<String, TTKReportDataSource> hmDataSource	=	new HashMap<String, TTKReportDataSource>();
    		String strFile="";
    		setLinks(request);
    		Object[] excelData=null;
    		DynaActionForm frmFloatAccounts=(DynaActionForm)form;
    		FormFile formFile = null;
    		String appendClaimsettlementnumber=null;
    		ArrayList<Object> alGenerateXL = new ArrayList<Object>();
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		String accnumber = null;
    		DynaActionForm frmFloatAccDetails=(DynaActionForm) request.getSession().getAttribute("frmFloatAccDetails");
    		TableData  tableData =TTKCommon.getTableData(request);
    		if(frmFloatAccDetails.get("accountNO")!=null)
    			accnumber=(String) frmFloatAccDetails.get("accountNO");
    		accnumber=accnumber.substring(accnumber.length()-3);


    		String DBFileName="ENBD"+"-"+accnumber+"-"+TTKCommon.getFormattedDateTimeSec(dtCurrentDate);
    		strFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
    				+/*strBankType*/DBFileName/*+"-"*/
    				/*+TTKCommon.getUserSeqId(request)*/+".txt";
    		JasperPrint jasperPrint = null,jasperPrintDetail = null,jasperPrintBOADetail=null,jasperPrintCons = null;
    		JasperReport jasperReport = null, jasperReportDetail = null,jasperReportBOADetail=null,jasperReportCons=null;	
    		TTKReportDataSource reportDataSource = null ,reportDataSourceDetail = null,reportDataSourceBOADetail=null,reportDataSourceCons = null;
    		File file = new File(strFile);
    		File fileDetail = null,fileDetailCons=null;
    		String strFileDetail ="",strFileDetailCons="";
    		strFileDetail = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+DBFileName+"Detail"+".xls";

    		formFile = (FormFile)frmFloatAccounts.get("stmFile");
    		if(formFile==null||formFile.getFileSize()==0)
    		{
    			strNotify="Please select the xls or xlsx file";
    			request.getSession().setAttribute("notify", strNotify);
    		}
    		else
    		{
    			String[] arr=formFile.getFileName().split("[.]");
    			String fileType=arr[arr.length-1];
    			if(!("xls".equalsIgnoreCase(fileType)||"xlsx".equalsIgnoreCase(fileType)))
    			{
    				strNotify="File Type should be xls or xlsx";	        	  
    				request.getSession().setAttribute("notify", strNotify);           
    			}

    		}
    		if(formFile.getFileSize()>(1024*1024*1024)) 
    		{
    			strNotify="File Length Lessthan 3GB";	        	  
    			request.getSession().setAttribute("notify", strNotify);

    		}
    		if(strNotify!=null && strNotify.length()!=0)
    		{
    			return (mapping.findForward(stracclistdetails));
    		}
    		else
    		{
    			incurredCurrencyFormat=frmFloatAccounts.getString("incuredCurencyFormat");
    			String[] arr=formFile.getFileName().split("[.]");
    			String fileType=arr[arr.length-1];
    			excelData=this.getExcelData(request,formFile,fileType,9);
    			noOfClaimSettlementNumber=(String)excelData[0];
    			frmFloatAccounts.set("noofclaimssettlementnum",noOfClaimSettlementNumber);
    			ArrayList<ArrayList<String>> excelDataRows=(ArrayList<ArrayList<String>>)excelData[1];
    			appendClaimsettlementnumber =  this.getConcatenatedClaimSettlementNumber(excelDataRows);
    			alGenerateXL.add(appendClaimsettlementnumber);
    			alGenerateXL.add(TTKCommon.getWebBoardId(request));
    			alGenerateXL.add(TTKCommon.getUserSeqId(request));
    			alGenerateXL.add(new Timestamp(dtCurrentDate.getTime()));
    			alGenerateXL.add(DBFileName);
    			alGenerateXL.add(incurredCurrencyFormat);
    			strFileDetailCons = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+DBFileName+/*"-"+TTKCommon.getUserSeqId(request)+*/"Consolidated"+".xls";
    			fileDetail = new File(strFileDetail);
    			fileDetailCons = new File(strFileDetailCons);
    			File file1 = new File("generalreports/PayAdviceMaster.jrxml");
    			File file2 = new File("generalreports/PayAdviceDetail.jrxml");
    			jasperReport = JasperCompileManager.compileReport("generalreports/ENBDPayAdviceMasterNew.jrxml");//detail report
    			jasperReportCons= JasperCompileManager.compileReport("generalreports/ENBDPayAdviceConsolidated.jrxml");//consolidated report
    			String strReportFormat  = (String) frmFloatAccounts.get("sreportFormat");
    			if(strReportFormat.length()==0)
    			{
    				strReportFormat="ENBD Format";
    			}
    			
    			if(file1.exists() && file2.exists())
    			{
    				if(strReportFormat.equalsIgnoreCase("ENBD Format")) {
                        hmDataSource		= floatAccountManagerObject.generateChequeAdviceUploadENBDXL(alGenerateXL);
    					reportDataSource	=	hmDataSource.get("Detail");
    					reportDataSourceCons=	hmDataSource.get("Cons");
    				}


    			if(reportDataSource!=null)
    				{
    				   if(tableData.getSearchData().size()>4)
    				   {
    					tableData.modifySearchData("search");
    			
    					ArrayList alPaymentAdvice= floatAccountManagerObject.getPaymentAdviceList(tableData.getSearchData());
    					tableData.setData(alPaymentAdvice,"search");
    					//set the table data object to session
    					request.getSession().setAttribute("tableData",tableData);
    				   }
    				}//end of if(reportDataSource!=null)
    			

    			}
    			else
    			{

    				TTKException expTTK = new TTKException();
    				expTTK.setMessage("error.jrxmlfile.required");
    				throw expTTK;
    			}//end of else
    			if(!(reportDataSource.equals("null")))
    			{
    				request.setAttribute("GenerateXL","message.GenerateXLSuccessfully");
    			}//end of if(!(reportDataSource.equals("null")))

    			HashMap hashMap = new HashMap();
    			//hashMap.put("BENEFICIARY_PAYMENT_DETAILS",hmDataSource.get("BENEFICIARY_PAYMENT_DETAILS"));
    			jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,reportDataSource);
    			jasperPrintCons = JasperFillManager.fillReport( jasperReportCons, hashMap,reportDataSourceCons);
    			request.setAttribute("reportType","EXL");
    			JRCsvExporter exporter = new JRCsvExporter(); 
    			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER,",");
    			exporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER,"\r\n");
    			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrintCons);
    			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, strFile.toString());    
    			exporter.exportReport();
    			JRXlsExporter exporterXLDetail = new JRXlsExporter();
    			exporterXLDetail.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFileDetail.toString());
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    			exporterXLDetail.exportReport();

    			JRXlsExporter exporterXLDetailCons = new JRXlsExporter();
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrintCons);
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFileDetailCons.toString());
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    			exporterXLDetailCons.exportReport();
    			if(file.exists())
    			{
    				strFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
    						+DBFileName/*+"-"
            				+TTKCommon.getUserSeqId(request)*/+".txt";
    				request.setAttribute("fileName",strFile);
    				//strAlternateFileName="ENBD-"+accnumber+"-"+dateFormater.format(new Date()).toString()+"-"+enbdCount+".txt";
    				request.setAttribute("alternateFileName",DBFileName+".txt");
    			}
    			if(fileDetail.exists())
    			{
    				strFileDetail = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
    						+DBFileName/*+"-"
            				+TTKCommon.getUserSeqId(request)*/+"Detail"+".xls";
    				request.setAttribute("fileName",strFileDetail);
    				request.setAttribute("alternateFileName",DBFileName+"Detail.xls");
    			}//end of if(fileDetail.exists())

    			if(fileDetailCons.exists())
    			{
    				strFileDetailCons = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
    						+DBFileName+"Consolidated"+".xls";
    				request.setAttribute("fileName",strFileDetailCons);
    				request.setAttribute("alternateFileName",DBFileName+"Consolidated.xls");
    			}
    			request.getSession().setAttribute("frmFloatAccounts",frmFloatAccounts);

    		}
    		return this.getForward(stracclistdetails, mapping, request);
    	}catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }





    private String getConcatenatedClaimSettlementNumber(ArrayList<ArrayList<String>> excelDataRows) {
		// TODO Auto-generated method stub
		StringBuffer sbfConcatenatedClaimSettlementNum=new StringBuffer("|");
		Iterator<ArrayList<String>>rit=excelDataRows.iterator();
		int iCounter=0;
		while(rit.hasNext())
		{
			ArrayList<String> rlist=rit.next();
             if((rlist!=null)&& rlist.size()!=0)
			{
				if(rlist.get(0)!=null)
				{
					if(iCounter==0)
					{
						sbfConcatenatedClaimSettlementNum.append(rlist.get(0));
						
                     }//end of if(iCounter==0)
					else
					{
						
						sbfConcatenatedClaimSettlementNum.append("|").append(rlist.get(0));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
					} // end of if((strChOpt!=null)&& strChOpt.length!=0)
			iCounter++;
		}
		sbfConcatenatedClaimSettlementNum.append("|");
		return sbfConcatenatedClaimSettlementNum.toString();
	}

    private Object[] getExcelData(HttpServletRequest request,FormFile formFile, String fileType, int column) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		InputStream fis=null;
    	HSSFSheet sheet = null;// i; // sheet can be used as common for XSSF and HSSF WorkBook
    	Reader reader		=	null;
    	Object object[]=new Object[2];
    	int numclaimsettlementnumber=0;
 	     ArrayList<ArrayList<String>> excelDatar=new ArrayList<>();
		FileWriter fileWriter=	null;
		HSSFWorkbook workbook = null;
		fis = formFile.getInputStream(); 
		workbook =  (HSSFWorkbook) new HSSFWorkbook(fis);
    	  //log("xls="+wb_hssf.getSheetName(0));
    	 sheet = workbook.getSheetAt(0);
    	
 	        //Initializing the XML document
 	    	final Pattern REGEX_PATTERN = Pattern.compile("[^\\p{ASCII}]+");
 	        if(sheet==null)
 	        	request.getSession().setAttribute("notify", "Please upload proper File");
 	        else
 	        {
 	        	  Iterator<?> rows     = sheet.rowIterator ();

/*      	        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();*/
 	        
 	     	    ArrayList<String> excelDatac;
      	      //  ArrayList<TariffXMLTag> arrayList	=	null;
      	        //TariffXMLTag tariffXMLTag			=	null;
      	        //Tariffdetails tariffdetails			=	null;
      	        //arrayList	=	new ArrayList<TariffXMLTag>();
      	        while (rows.hasNext ()) 
      	        {
      	        HSSFRow row =  (HSSFRow) rows.next(); 

      	            if(row.getRowNum()==0)
      	            	continue;
      	            
      	        // int rowNumber = row.getRowNum ();
      	            // display row number

      	            // get a row, iterate through cells.
      	            Iterator<?> cells = row.cellIterator (); 
      	            ArrayList<String> rowData = new ArrayList<String>();
      	            for(short i=0;i<=9;i++)
      	            {
      	            	HSSFCell cell	=	row.getCell(i);
      	            	
      	            	if(cell==null)
      	            		rowData.add("");
      	            	else
      	            		{ 
      	            		switch (cell.getCellType ())
  	     	                {
  		     	                case HSSFCell.CELL_TYPE_NUMERIC :
  		     	                {
  		     	                    // Date CELL TYPE
  		     	                    if(HSSFDateUtil.isCellDateFormatted((HSSFCell) cell)){
  		     	                    	rowData.add(new SimpleDateFormat("dd-MM-YYYY").format(cell.getDateCellValue()));
  		     	                    }
  		     	                    else // NUMERIC CELL TYPE
  		     	                    	rowData.add(cell.getNumericCellValue () + "");
  		     	                    break;
  		     	                }
  		     	                case HSSFCell.CELL_TYPE_STRING :
  		     	                {
  		     	                    // STRING CELL TYPE
  		     	                    //String richTextString = cell.getStringCellValue().trim().replaceAll("[^\\x00-\\x7F]", "");
  		     	                	String richTextString = cell.getStringCellValue().trim();
  		     	                	richTextString	=	REGEX_PATTERN.matcher(richTextString).replaceAll("").trim();
  		     	                    rowData.add(richTextString);
  		     	                    break;
  		     	                }
  		     	                case HSSFCell.CELL_TYPE_BLANK :
  		     	                {	//HSSFRichTextString blankCell	=	cell.get.getRichStringCellValue();
  		     	                	String blankCell	=	cell.getStringCellValue().trim().replaceAll("[^\\x00-\\x7F]", "");
  		     	                	rowData.add(blankCell);
  		     	                	break;
  		     	                }
  		     	                default:
  		     	                {
  		     	                    // types other than String and Numeric.
  		     	                    
  		     	                    break;
  		     	                }
  	     	                } // end switch
  	            		}//else
      	            	
      	            }//for
      	        
      	          
      	          excelDatar.add(rowData);//adding Excel data to ArrayList
      	        numclaimsettlementnumber++; 
      	        } //end while
      	     
 	        }
 	        object[0]=numclaimsettlementnumber+"";//adding no. of policies
			object[1]=excelDatar;//adding all rows data
 		     return object;
	}

    
    
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
    public ActionForward doGenerateXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		String IPAddr=request.getRemoteHost();
    		log.info("Action doGenerateXL Date in starting"+new Date()+"IP Address"+IPAddr);
    		setLinks(request);
    		log.debug("Inside PaymentAdviceAction doGenerateXL");
    		DynaActionForm frmFloatAccounts = (DynaActionForm)form;
    		
    		TableData  tableData =TTKCommon.getTableData(request);
    		
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		
    		JasperReport jasperReport = null, jasperReportDetail = null,jasperReportBOADetail=null,jasperReportCons=null;
    		JasperPrint jasperPrint = null,jasperPrintDetail = null,jasperPrintBOADetail=null,jasperPrintCons = null;
    		ArrayList<Object> alGenerateXL = new ArrayList<Object>();
    		String strReportFormat = (String)((DynaActionForm)form).get("sreportFormat");
    		String strBankType = getBankType(request,(TableData)request.getSession().getAttribute("tableData"));
    		String strSheetNames[] = new String[] {"Sheet1"};
    		TTKReportDataSource reportDataSource = null ,reportDataSourceDetail = null,reportDataSourceBOADetail=null,reportDataSourceCons = null;
    		HashMap<String, TTKReportDataSource> hmDataSource	=	new HashMap<String, TTKReportDataSource>();
    		Date dtCurrentDate = new Date();
    		String strPaymentSeqID = getConcatenatedSeqID(request,(TableData)
  			request.getSession().getAttribute("tableData"));
    		//prepare the parameters to generate the Payment Advice
    		alGenerateXL.add(strPaymentSeqID);
    		alGenerateXL.add(TTKCommon.getWebBoardId(request));
    		alGenerateXL.add(strBankType);
    		alGenerateXL.add(new Timestamp(dtCurrentDate.getTime()));
    		alGenerateXL.add(TTKCommon.getUserSeqId(request));
    		if(strReportFormat.equalsIgnoreCase("Multi Location Format")){
    			alGenerateXL.add("Multi");
    		}//end of if(strReportFormat.equalsIgnoreCase("Multi Location Format"))
    		else{
    			alGenerateXL.add(strReportFormat);
    		}//end of else
    		request.setAttribute("alternateFileName",null);
    		log.info("parameters for generating payment advice***********************************************");
    		log.info("Payment seq id : " + strPaymentSeqID);
    		log.info("float seq id " +TTKCommon.getWebBoardId(request));
    		log.info(strBankType);
    		log.info("user seq id is " + TTKCommon.getUserSeqId(request));
    		log.info("Report Format is : "+strReportFormat);
    		log.info("***********end of parameter list*******************");
    		
			// Start changes added for BOA CR KOC1220		
                 
    		DynaActionForm frmFloatAccDetails=(DynaActionForm) request.getSession().getAttribute("frmFloatAccDetails");
			String accnumber = null;
			String strAlternateFileName;
    		if(frmFloatAccDetails.get("accountNO")!=null)
    		{
    		accnumber=(String) frmFloatAccDetails.get("accountNO");
    		}
    		accnumber=accnumber.substring(accnumber.length()-3);
			String DBFileName="ENBD"+"-"+accnumber+"-"+TTKCommon.getFormattedDateTimeSec(dtCurrentDate);
			alGenerateXL.add(DBFileName);
    		String strFile="";
    		if(strBankType.equalsIgnoreCase("BOA"))
    		{
    			strFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strBankType+""
    			+TTKCommon.getFormattedDateTimeSecNoHyphen(dtCurrentDate)+""+TTKCommon.getUserSeqId(request)+".txt";
    		}
    		else if(strBankType.equalsIgnoreCase("CITI")){
    			strFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
        				+/*strBankType*/DBFileName/*+"-"*/
        				/*+TTKCommon.getUserSeqId(request)*/+".txt";
    		}
    		else
    		{
    			strFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strBankType+"-"
    			+TTKCommon.getFormattedDateTimeSec(dtCurrentDate)+"-"+TTKCommon.getUserSeqId(request)+".xls";
    		}	
    		
    		File file = new File(strFile);
    		File fileDetail = null,fileDetailCons=null;
    		String strFileDetail ="",strFileDetailCons="";
    		if(strBankType.equalsIgnoreCase("BOA"))
    		{
    			strFileDetail = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strBankType+""
    			+TTKCommon.getFormattedDateTimeSecNoHyphen(dtCurrentDate)+""+TTKCommon.getUserSeqId(request)+"Detail"+".xls";
    		}else if(strBankType.equalsIgnoreCase("CITI"))
    		{
    			strFileDetail = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+DBFileName+/*"-"+TTKCommon.getUserSeqId(request)+*/"Detail"+".xls";
    			strFileDetailCons = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+DBFileName+/*"-"+TTKCommon.getUserSeqId(request)+*/"Consolidated"+".xls";
    		}
    		else
    		{
    			strFileDetail = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strBankType+"-"
    			+TTKCommon.getFormattedDateTimeSec(dtCurrentDate)+"-"+TTKCommon.getUserSeqId(request)+"Detail"+".xls";
    		}
    		fileDetail = new File(strFileDetail);
    		fileDetailCons = new File(strFileDetailCons);
    		File file1 = new File("generalreports/PayAdviceMaster.jrxml");
			File file2 = new File("generalreports/PayAdviceDetail.jrxml");
			
			
    		if(strBankType.equalsIgnoreCase("CITI"))
    		{
    			if(strReportFormat.equalsIgnoreCase("New Format")) {
    				jasperReport = JasperCompileManager.compileReport("generalreports/PayAdviceMasterNew.jrxml");
    			}//end of if(strReportFormat.equalsIgnoreCase("New Format")) {
    			if(strReportFormat.equalsIgnoreCase("ENBD Format")) {
    				jasperReport = JasperCompileManager.compileReport("generalreports/ENBDPayAdviceMasterNew.jrxml");//detail report
    				jasperReportCons= JasperCompileManager.compileReport("generalreports/ENBDPayAdviceConsolidated.jrxml");//consolidated report
    			}//end of if(strReportFormat.equalsIgnoreCase("New Format")) {
    			else {
    				jasperReport = JasperCompileManager.compileReport("generalreports/PayAdviceMaster.jrxml");
    			}//end of else
    		}//end of if(strBankType.equalsIgnoreCase("CITI"))
    		
    		else if(strBankType.equalsIgnoreCase("ICICI"))
    		{
    			log.info("inside else if(strBankType.equalsIgnoreCase(\"ICICI\"))"+"*****************************");
    			if(strReportFormat.equalsIgnoreCase("Multi Location Format"))
        		{
        			log.debug("inside if(strReportFormat.equalsIgnoreCase(Multi Location Format))");
        			jasperReport = JasperCompileManager.compileReport("generalreports/ICICIPaymentAdviseMultiLocation.jrxml");
        		}//end of if(strReportFormat.equalsIgnoreCase("Multi Location Format"))
        		else
        		{
        			jasperReport = JasperCompileManager.compileReport("generalreports/ICICIPayAdviceMaster.jrxml");
        		}//end of else
    		}//end of else if(strBankType.equalsIgnoreCase("ICICI"))
    		else if(strBankType.equalsIgnoreCase("HDFC"))
    		{
    			log.info("inside else if(strBankType.equalsIgnoreCase(\"HDFC\"))"+"*****************************");
    			if(strReportFormat.equalsIgnoreCase("Default Format")) {
    				jasperReport = JasperCompileManager.compileReport("generalreports/HDFCPayAdviceMaster.jrxml");
    			}//end of if(strReportFormat.equalsIgnoreCase("Default Format"))
    		}//end of else if(strBankType.equalsIgnoreCase("ICICI"))
    		else if(strBankType.equalsIgnoreCase("BOA"))
    		{
    		    log.info("inside else if(strBankType.equalsIgnoreCase(\"BOA\"))"+"*****************************");
    		    if(strReportFormat.equalsIgnoreCase("Default Format")) {
    		    jasperReport = JasperCompileManager.compileReport("generalreports/BOAPayAdviceMaster.jrxml");
    		    }//end of if(strBankType.equalsIgnoreCase("Default Format"))  
    		}
    		else if(strReportFormat.equalsIgnoreCase("OIC Report"))
    		{
    			log.debug("inside else if(strReportFormat.equalsIgnoreCase(OIC Report))");
    			jasperReport = JasperCompileManager.compileReport("generalreports/PaymentAdviceOriental.jrxml");

    		}//end of else if(strReportFormat.equalsIgnoreCase("OIC Report"))
    		log.info("strBankType is :"+strBankType);
			log.info("jasperReport is :"+jasperReport);
			log.info("jasperReportCOns is :"+jasperReportCons);
    		if(((ChequeVO)tableData.getRowInfo(0)).getBankName()!=null)
    		{
    			if(file1.exists() && file2.exists())
    			{
    				if(strReportFormat.equalsIgnoreCase("OIC Report"))
    				{
    					log.debug("inside if block");
    					
    					reportDataSource = floatAccountManagerObject.generateOICPaymentAdvice(alGenerateXL);
    					
    					
    				}//end of if(strReportFormat.equalsIgnoreCase("OIC Report"))
    				else if(strReportFormat.equalsIgnoreCase("ENBD Format")) {
    					hmDataSource		= floatAccountManagerObject.generateChequeAdviceENBDXL(alGenerateXL);
    					reportDataSource	=	hmDataSource.get("Detail");
    					reportDataSourceCons=	hmDataSource.get("Cons");
    					ResultSet detRs=reportDataSource.getResultData();
    					detRs.next();
    					
    					
    					detRs.beforeFirst();
    					
    					
    					
    				//	reportDataSourceCons = floatAccountManagerObject.generateChequeAdviceENBDConsXL(alGenerateXL);
    				//	reportDataSource 	 = floatAccountManagerObject.generateChequeAdviceENBDXL(alGenerateXL);
    				}
    				else{
    					reportDataSource = floatAccountManagerObject.generateChequeAdviceXL(alGenerateXL);
    					
    					
    					
    				}//end of else
    				//get the pending Payment Advice List after generating the XL sheet.
    				if(reportDataSource!=null)
    				{
    					tableData.modifySearchData("search");
    					ArrayList alPaymentAdvice= floatAccountManagerObject.getPaymentAdviceList(
    							tableData.getSearchData());
    					
    					
    					
    					
    					
    					
    					tableData.setData(alPaymentAdvice,"search");
    					//set the table data object to session
    					request.getSession().setAttribute("tableData",tableData);
    				}//end of if(reportDataSource!=null)
    			}//End of if(file1s.exists())
    			else
    			{
    				TTKException expTTK = new TTKException();
    				expTTK.setMessage("error.jrxmlfile.required");
    				throw expTTK;
    			}//end of else
    		}//end of if(((ChequeVO)tableData.getRowInfo(0)).getFileName()!=null)
    	
    		if(!(reportDataSource.equals("null")))
    		{
    			request.setAttribute("GenerateXL","message.GenerateXLSuccessfully");
    		}//end of if(!(reportDataSource.equals("null")))
    		String mode="generatereport";
    /*	    long enbdCount=floatAccountManagerObject.getENBDCountandAccNum((long) alGenerateXL.get(1),mode);*/
         
           
            
	    	HashMap hashMap = new HashMap();
	    	//hashMap.put("BENEFICIARY_PAYMENT_DETAILS",hmDataSource.get("BENEFICIARY_PAYMENT_DETAILS"));
    		jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,reportDataSource);
    		jasperPrintCons = JasperFillManager.fillReport( jasperReportCons, hashMap,reportDataSourceCons);
    		request.setAttribute("reportType","EXL");
    		
			if(strBankType.equalsIgnoreCase("BOA") || strBankType.equalsIgnoreCase("CITI"))
    		{
    			JRCsvExporter exporter = new JRCsvExporter(); 
    			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER,",");
    			exporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER,"\r\n");
    			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrintCons);
    			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, strFile.toString());    
    			exporter.exportReport();
    			
    			if(strBankType.equalsIgnoreCase("CITI")){//CONSIDERING CITI BANK AS DEFAULT FOR ENBD FORMAT
    			JRXlsExporter exporterXLDetail = new JRXlsExporter();
    			exporterXLDetail.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFileDetail.toString());
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    			exporterXLDetail.exportReport();
    			
    			if(strReportFormat.equalsIgnoreCase("ENBD Format")) {
    			//For Consolidated report
    			//jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,reportDataSource);
    			JRXlsExporter exporterXLDetailCons = new JRXlsExporter();
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrintCons);
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFileDetailCons.toString());
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    			exporterXLDetailCons.exportReport();
    			}
    			/*
    			jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,reportDataSource);
    			JRXlsExporter exporterXLDetailCons = new JRXlsExporter();
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFileDetailCons.toString());
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    			exporterXLDetailCons.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    			exporterXLDetailCons.exportReport();*/
    			}
    			
    		}
    		else
    		{
    			JRXlsExporter exporterXL = new JRXlsExporter();
    			exporterXL.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    			exporterXL.setParameter(JRXlsExporterParameter.SHEET_NAMES, strSheetNames);
    			exporterXL.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFile.toString());
    			exporterXL.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    			exporterXL.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    			exporterXL.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    			exporterXL.exportReport();
    		}
    		/*if(strBankType.equalsIgnoreCase("CITI"))
    		{
    			reportDataSourceDetail = floatAccountManagerObject.generateChequeAdviceXLDetails(alGenerateXL);
    			jasperReportDetail = JasperCompileManager.compileReport("generalreports/PayAdviceDetail.jrxml");
    			HashMap hashMap1 = new HashMap();
        		jasperPrintDetail = JasperFillManager.fillReport(jasperReportDetail , hashMap1 , reportDataSourceDetail);
    		}//end of if(strBankType.equalsIgnoreCase("CITI"))
    		if(strBankType.equalsIgnoreCase("CITI")) {
    			JRXlsExporter exporterXLDetail = new JRXlsExporter();
    			exporterXLDetail.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrintDetail);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFileDetail.toString());
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    			exporterXLDetail.exportReport();
    		}// end of if(strBankType.equalsIgnoreCase("CITI"))
			*/
			if(strBankType.equalsIgnoreCase("BOA"))
    		{
    			reportDataSourceBOADetail = floatAccountManagerObject.generateBOAXLDetails(alGenerateXL);
    			jasperReportBOADetail = JasperCompileManager.compileReport("generalreports/BOAPayAdviceMasterXLDetail.jrxml");
    			HashMap hashMap1 = new HashMap();
    			jasperPrintBOADetail = JasperFillManager.fillReport(jasperReportBOADetail , hashMap1 , reportDataSourceBOADetail);
    		}//end of if(strBankType.equalsIgnoreCase("BOA"))
			
    		if(strBankType.equalsIgnoreCase("BOA")) {
    			JRXlsExporter exporterXLDetail = new JRXlsExporter();
    			exporterXLDetail.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrintBOADetail);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFileDetail.toString());
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    			exporterXLDetail.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    			exporterXLDetail.exportReport();
    		}// end of if(strBankType.equalsIgnoreCase("BOA"))
			if(strBankType.equalsIgnoreCase("BOA"))
    		{
    			if(file.exists())
    			{
    				strFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
    				+strBankType+""+TTKCommon.getFormattedDateTimeSecNoHyphen(dtCurrentDate)+""
    				+TTKCommon.getUserSeqId(request)+".txt";
    				request.setAttribute("fileName",strFile);
    				
    			}
    			if(fileDetail.exists())
    			{
        			strFileDetail = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
        			+strBankType+""+TTKCommon.getFormattedDateTimeSecNoHyphen(dtCurrentDate)+""
        			+TTKCommon.getUserSeqId(request)+"Detail"+".xls";
        			request.setAttribute("fileNameDetail",strFileDetail);
        			
    			}
    		}//end of if(file.exists() & fileDetail.exists())
			else if(strBankType.equalsIgnoreCase("CITI")) {
				if(file.exists())
    			{
    				strFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
    				+DBFileName/*+"-"
    				+TTKCommon.getUserSeqId(request)*/+".txt";
    				request.setAttribute("fileName",strFile);
    				//strAlternateFileName="ENBD-"+accnumber+"-"+dateFormater.format(new Date()).toString()+"-"+enbdCount+".txt";
        			request.setAttribute("alternateFileName",DBFileName+".txt");
    			}
    			if(fileDetail.exists())
    			{
    				strFileDetail = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
    				+DBFileName/*+"-"
    				+TTKCommon.getUserSeqId(request)*/+"Detail"+".xls";
					request.setAttribute("fileName",strFileDetail);
					//strAlternateFileName="ENBD-"+accnumber+"-"+dateFormater.format(new Date()).toString()+"-"+enbdCount+"Detail.xls";
	    			request.setAttribute("alternateFileName",DBFileName+"Detail.xls");
    			}//end of if(fileDetail.exists())
    			if(fileDetailCons.exists())
    			{
    				strFileDetailCons = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
    				+DBFileName+"Consolidated"+".xls";
					request.setAttribute("fileName",strFileDetailCons);
					//strAlternateFileName="ENBD-"+accnumber+"-"+dateFormater.format(new Date()).toString()+"-"+enbdCount+"Consolidated.xls";
	    			request.setAttribute("alternateFileName",DBFileName+"Consolidated.xls");
    			}
    			
    		}//end of if(strBankType.equalsIgnoreCase("CITI"))
			else
			{
				if(file.exists())
				{
					strFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
					+strBankType+"-"+TTKCommon.getFormattedDateTimeSec(dtCurrentDate)+"-"
					+TTKCommon.getUserSeqId(request)+".xls";
					request.setAttribute("fileName",strFile);
				}//end of if(file.exists())
			}
			
    		request.getSession().setAttribute("frmFloatAccounts",frmFloatAccounts);
    		log.info("Action doGenerateXL Date in end"+new Date()+"IP Address"+IPAddr);
    		return this.getForward(stracclistdetails, mapping, request);
    	}catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doGenerateXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
			throw new TTKException(exp, "floataccount");
		}//end of catch
		return floatAccountManager;
	}//end getfloatAccountManagerObject()
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmFloatAccounts formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmFloatAccounts,HttpServletRequest request)
																							throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.getWebBoardId(request));
		//alSearchParams.add(TTKCommon.getLong((String)frmFloatAccounts.get("sBankName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatAccounts.getString("sFloatName")));
		alSearchParams.add(TTKCommon.getLong((String)frmFloatAccounts.get("sTTKBranch")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatAccounts.getString("sbankaccountNbr")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatAccounts.getString("incuredCurencyFormat")));
        return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmFloatAccounts,HttpServletRequest request)

	
	
	
	
	
	
	/**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getConcatenatedSeqID(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer("|");
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						sbfConcatenatedSeqId.append(String.valueOf(((ChequeVO)tableData.getRowInfo(
															Integer.parseInt(strChOpt[iCounter]))).getSeqID()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((ChequeVO)tableData.getRowInfo
																(Integer.parseInt(strChOpt[iCounter]))).getSeqID()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)

	
	
	
	public ActionForward doLogDetailsExcelUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException {

		log.debug("Inside PaymentAdviceAction doLogDetailsExcelUploads");
		try
		{
			setLinks(request);
			String strActiveLink=TTKCommon.getActiveLink(request);
			DynaActionForm frmClaims=(DynaActionForm)form;
			String flag = request.getParameter("Flag");
			String startDate = frmClaims.getString("startDate");
			String endDate = frmClaims.getString("endDate");
			ArrayList alData	=	null; 
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			alData=floatAccountManagerObject.getLogDetailsExcelUpload(startDate,endDate,flag);
			request.getSession().setAttribute("alData", alData);
			return (mapping.findForward(struploadbatchdetaillog));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)

	}




	/**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getBankType(HttpServletRequest request,TableData tableData) {
		String strBankType=null;
		int iCounter = 0;
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			if(strChOpt[iCounter]!=null)
			{
				strBankType = ((ChequeVO)tableData.getRowInfo(Integer.parseInt(strChOpt[iCounter]))).getBankName();
			}//end of if(strChOpt[iCounter]!=null)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		return strBankType;
	} // end of getBankType(HttpServletRequest request,TableData tableData)
		
}// end of PaymentSearchAction
