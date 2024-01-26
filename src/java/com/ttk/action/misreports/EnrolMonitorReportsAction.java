
/**
  * @ (#) EnrolMonitorReportsAction.java May 18, 2007
  * Project      : TTK HealthCare Services
  * File         : EnrolMonitorReportsAction.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author      :  Ajay Kumar
  * Modified by  : Balakrishna Erram
  * Modified date: April 14, 2009
  * Company      : Span Infotech Pvt.Ltd.
  * Reason       : Code Review
  */
package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
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
import com.ttk.business.misreports.ReportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.misreports.TTKInsDoBOSelect;
import com.ttk.dao.impl.misreports.ReportsDataSource;
import com.ttk.dto.misreports.ReportDetailVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
/**
 * This method is called from the struts framework.
 * This action class looks for the data access object and invokes methods on it to find, GenerateReport the users.
 * It invokes buildErrorObject() method to build the error object.
 * Finally it forwards to the appropriate view based on the specified forward mappings
 *
 * @param mapping The ActionMapping used to select this instance
 * @param form The optional ActionForm bean for this request (if any)
 * @param request The HTTP request we are processing
 * @param response The HTTP response we are creating
 * @return ActionForward Where the control will be forwarded, after this request is processed
 * @throws Exception if any error occurs
 */
public class EnrolMonitorReportsAction extends TTKAction {
	private static final Logger log = Logger.getLogger( EnrolMonitorReportsAction.class );
    //string for forwarding
	private static final String strIdcardsreportlist="idcardsreportlist";
	private static final String strEnrollmentMonitorform="enrollmentmonitorform";
	private static final String strReportdisplay="reportdisplay";
   //Exception Message Identifier
	 private static final String strReportExp="report";
	 /**
	 * This method is used to load the sub status based on the selected status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 *This method work for based on the TTKBranch selected the insurance company name and used for idcpsreports.jsp
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doChangeCompany(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    									 HttpServletResponse response) throws TTKException{
		 try{
			 log.debug("Inside the doChangeInsCompany method of EnrolMonitorReportsAction");
			setLinks(request);
			DynaActionForm frmTtkReports = (DynaActionForm) form;
            ArrayList<Object> alInsCompanyList = new ArrayList<Object>();
			alInsCompanyList = TTKInsDoBOSelect.getInsCompany(frmTtkReports);
			frmTtkReports.set("frmChanged", "changed");
			frmTtkReports.set("alInsCompanyList", alInsCompanyList);
             return this.getForward(strEnrollmentMonitorform,mapping,request);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doChangeInsCompany(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
    /**
	 * This method is used to load the sub status based on the selected status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 *This method work for based on the insurance company name selected the  DoBo code and used for idcpsreports.jsp
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doChangeComDoBo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    									 HttpServletResponse response) throws TTKException{
		 try{
			 log.debug("Inside the doChangeInsComDoBo method of EnrolMonitorReportsAction");
			setLinks(request);
			DynaActionForm frmTtkReports = (DynaActionForm) form;
			ArrayList<Object> alDoBoList = new ArrayList<Object>();
			alDoBoList = TTKInsDoBOSelect.getInsCompDoBo(frmTtkReports);
			frmTtkReports.set("frmChanged", "changed");
			frmTtkReports.set("alDoBoList", alDoBoList);
             return this.getForward(strEnrollmentMonitorform,mapping,request);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doChangeInsComDoBo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
	 * This method is used to load the sub status based on the selected status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 *This method work for based on the Type selected 
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doChangeStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    									HttpServletResponse response) throws TTKException{
    	try{
    		log.debug("Inside the doChangeStatus method of EnrolMonitorReportsAction");
    		setLinks(request);
    		DynaActionForm frmTtkReports = (DynaActionForm) form;
    		frmTtkReports.set("sStatus","ALL");
    		frmTtkReports.set("frmChanged", "changed");
    		return this.getForward(strEnrollmentMonitorform,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
    	}//end of catch(Exception exp)
    }//end of doChangeStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 
    /**
	 * This method is used to load the sub status based on the selected status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 *This method work for based on the Type selected 
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doChangeLabel(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								   HttpServletResponse response) throws TTKException{
    	try{
    		log.debug("Inside the doChangeLabel method of EnrolMonitorReportsAction");
    		setLinks(request);
    		DynaActionForm frmLabelReports = (DynaActionForm) form;
    		frmLabelReports.set("frmChanged", "changed");
//    		String strStatus = request.getParameter("sStatus");
    		return this.getForward(strEnrollmentMonitorform,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
    	}//end of catch(Exception exp)
    }//end of doChangeLabel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doGenerateReport(ActionMapping mapping,
           ActionForm form,
           HttpServletRequest request,
           HttpServletResponse response) throws TTKException{
    	try{
    		log.debug("Inside the doGenerateReport method of ClaimsReportsAction");
    		setLinks(request);
    		DynaActionForm frmReportDetail =(DynaActionForm)form;
    		JasperReport jasperReport,emptyReport;
    		JasperPrint jasperPrint;
    		ReportsDataSource ttkReportsDataSource = null;
    		Document reportsListDoc=null;
    		UserSecurityProfile userSecurityProfile=new UserSecurityProfile();
    		userSecurityProfile=(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
    		ReportDetailVO reportDetailVO =new ReportDetailVO();
    		ReportManager reportDetailObject=this.getReportManager();
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
    					if(request.getParameter("reportID").equalsIgnoreCase(reportsElement.valueOf("@type")))
    					{
    						strMaxValue = reportsElement.valueOf("@maxValue");
    						
    					}//end of if(request.getParameter("reportID").equalsIgnoreCase(reportsElement.valueOf("@type")))
    				}//for(int i=0 ;i<reportsList.size() ;i++)
    			}//if(reportsList != null && reportsList.size() > 0)
    		}//if(reportsListDoc != null)
    		
    		String strParam = request.getParameter("parameter")+strMaxValue+"|";
    		String jrxmlfile=null;
    		if(request.getParameter("reportType").equals("EXL"))
    		{
    			jrxmlfile=request.getParameter("fileName");
    		}// if(request.getParameter("reportType").equals("EXL")
    		if(request.getParameter("reportType").equals("PDF"))
    		{
    			jrxmlfile=request.getParameter("fileName");
    		}// if(request.getParameter("reportType").equals("PDF")
    		
    		reportDetailVO.setUSER_ID(userSecurityProfile.getUSER_ID());
    		reportDetailVO.setUserName(userSecurityProfile.getUserName());
    		reportDetailVO.setUserLocation(userSecurityProfile.getBranchName());
    		reportDetailVO.setTTKBranchName(request.getParameter("ttkbranchname"));
    		reportDetailVO.setInsCompanyName(request.getParameter("inscompname"));
    		reportDetailVO.setInsDoBOCode(request.getParameter("inscompbodo"));
    		reportDetailVO.setGroupPolicyNo(request.getParameter("sGroupPolicyNo"));
    		reportDetailVO.setAgentCode(request.getParameter("sAgentCode"));
    		reportDetailVO.setSType(request.getParameter("types"));
    		reportDetailVO.setSStatus(request.getParameter("status"));
    		reportDetailVO.setStartDate(request.getParameter("startDate"));
    		reportDetailVO.setEndDate(request.getParameter("endDate"));
    		reportDetailVO.setReportId((String) frmReportDetail.get("reportID"));
    		reportDetailVO.setReportName(request.getParameter("reportname"));
    		reportDetailVO.setReportType((String) frmReportDetail.get("reportType"));
    		reportDetailVO.setReportLink(request.getParameter("reportlinkname"));
    		reportDetailVO.seteType(request.getParameter("enrtype"));
            //call the DAO to save the Report parameter details
    		int iCount=reportDetailObject.saveReportDetails(reportDetailVO); 
    		if(iCount>=0)
			{
    			//log.info("Inside the doGenerateReport method of EnrolMonitorReportsAction in iCount");
    			ttkReportsDataSource = new ReportsDataSource(request.getParameter("reportID"),strParam);
			}//end of if(iCount>=0)
    		
    		//ttkReportsDataSource = new ReportsDataSource(request.getParameter("reportID"),strParam);
    		
    		try
    		{
    			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
    			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
    			HashMap<String,String> hashMap = new HashMap<String,String>();
    			ByteArrayOutputStream boas=new ByteArrayOutputStream();
    			hashMap.put("Start Date",request.getParameter("startDate"));
    			hashMap.put("End Date",request.getParameter("endDate"));
    			hashMap.put("ttkbranchname",request.getParameter("ttkbranchname"));
    			hashMap.put("inscompbodo",request.getParameter("inscompbodo"));
    			hashMap.put("inscompname",request.getParameter("inscompname"));
    			hashMap.put("sAgentCode",request.getParameter("sAgentCode"));
    			hashMap.put("types",request.getParameter("types"));
    			hashMap.put("status",request.getParameter("status"));
    			hashMap.put("enrtype",request.getParameter("enrtype"));
    			hashMap.put("sGroupPolicyNo",request.getParameter("sGroupPolicyNo"));
    			hashMap.put("startlabelvalue",request.getParameter("startlabelvalue"));
    			hashMap.put("endlabelvalue",request.getParameter("endlabelvalue"));
    			hashMap.put("parameter",strParam.substring(2,strParam.length()));
    			if(request.getParameter("Location").equals("ALL"))
    			{
    				hashMap.put("Location",request.getParameter("Location"));
    			}//end of if(request.getParameter("Location").equals("ALL"))
    			if(!(ttkReportsDataSource.getResultData().next()))
    			{
    				jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
    				
    			}// end of else
    			else
    			{
    				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new ReportsDataSource(request.getParameter("reportID"),strParam));
    				
    			}// end of if(ttkReportDataSource.getResultData().next())
    			
    			if(request.getParameter("reportType").equals("EXL"))// if the report type is Excel
    			{
    				JRXlsExporter jExcelApiExporter = new JRXlsExporter();
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    				jExcelApiExporter.exportReport();
    			}// end of  if(request.getParameter("reportType").equals("EXL"))
    			else// if report type if PDF
    			{
    				JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    				
    			}// end of else
    			// keep the byte array out stream in request scope to write
    			// in the BinaryStreamServlet
    			request.setAttribute("boas",boas);
    		}// end of try
    		catch(Exception exp)
        	{
        		return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
        	}// end of catch(Exception exp)
    		return (mapping.findForward(strReportdisplay));
    	}// end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}// end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
    	}// end of catch(Exception exp)
    }// end of doGenerateReport
    
    /**
	  * This method is used to generate the  accenture report in MIS Reports Module.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
    public ActionForward doGenerateAccentureRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 //log.debug("Inside the doGenerateAccentureRpt method ");
			 setLinks(request);
				int iNoOfCursor = 2;
				JasperReport objJasperReport[] =new JasperReport[iNoOfCursor];
				JasperReport emptyReport;
				JasperPrint objJasperPrint[] = new JasperPrint[iNoOfCursor];
				JasperPrint objJasperPrint1,objJasperPrint2 = new JasperPrint();
				JasperPrint jasperPrint;
				ArrayList<Object> alJasperPrintList = new ArrayList<Object>();
				ByteArrayOutputStream boas=new ByteArrayOutputStream();
			    ReportsDataSource ttkReportsDataSource,ttkReportsDataSource1,ttkReportsDataSource2 = null;
			 
			    String jrxmlFiles[] = new String[]
				                                 {
						"reports/misreports/AccentureSummaryReport.jrxml",
						"reports/misreports/AccentureDetailReport.jrxml"
				                                 };
				String strSheetNames[] = new String[]
				                                    {
						"Group Plan Summary Report","Group Detail Report1","Group Detail Report2","Group Detail Report3"
				                                    };
			 String  strReportID = "" ;
			 String strGroupId = request.getParameter("groupId");
			 String strFrom = request.getParameter("sFrom");
			 String strTo = request.getParameter("sTo");
			 String strReportType = request.getParameter("reportType");
			 strReportID = request.getParameter("reportID");
			 String strParams[] = new String[] {"|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|1|40000|","|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|40001|80000|","|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|80001|120000|"					 
			 };
			 objJasperReport[0] = JasperCompileManager.compileReport(jrxmlFiles[0]);
			 objJasperReport[1] = JasperCompileManager.compileReport(jrxmlFiles[1]);
			 ttkReportsDataSource = new ReportsDataSource("AccentureReport",strParams[0],(iNoOfCursor+""));
			 ttkReportsDataSource1 = new ReportsDataSource("AccentureReport",strParams[1],(iNoOfCursor+""));
			 ttkReportsDataSource2 = new ReportsDataSource("AccentureReport",strParams[2],(iNoOfCursor+""));
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
			 hashMap.put("GroupID",strGroupId);
			 hashMap.put("sFrom",strFrom);
			 hashMap.put("sTo",strTo);
			 hashMap.put("sReportType",strReportType);
			 if(ttkReportsDataSource.isResultSetArrayEmpty())
				{
					emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
					jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
					alJasperPrintList.add(jasperPrint);
				}//end of if(ttkReportDataSource.isResultSetArrayEmpty())
			 else
			 {
				 for(int i=0;i<iNoOfCursor;i++){
						ResultSet rs = ttkReportsDataSource.getResultData(i);
						if(rs.next())
						{
							rs.beforeFirst();
							objJasperPrint[i] = JasperFillManager.fillReport(objJasperReport[i],hashMap,new JRResultSetDataSource(rs));
						}//end of if(rs.next())
						else 
						{
							objJasperPrint[i] = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
						}//end of else
						alJasperPrintList.add(objJasperPrint[i]);
					}//end of for(int i=0;i<iNoOfCursor;i++){
				 
			 }//end of if(ttkReportDataSource.getResultData().next())
			 ResultSet rs1 = ttkReportsDataSource1.getResultData(1);
			 ResultSet rs2 = ttkReportsDataSource2.getResultData(1);
			 if(!ttkReportsDataSource1.isResultSetArrayEmpty())
				{
				objJasperPrint1 = JasperFillManager.fillReport(objJasperReport[1],hashMap,new JRResultSetDataSource(rs1));
					if((rs1!= null))
					{
						alJasperPrintList.add(objJasperPrint1);
					}//end of if((rs1!= null))
				}//end of if(!ttkReportsDataSource1.isResultSetArrayEmpty())
			 if(!ttkReportsDataSource2.isResultSetArrayEmpty())
			 {
				 objJasperPrint2 = JasperFillManager.fillReport(objJasperReport[1],hashMap,new JRResultSetDataSource(rs2));
				 if((rs2!= null))
					{
						alJasperPrintList.add(objJasperPrint2);
					}//end of if((rs2!= null))
			 }//end of  if(!ttkReportsDataSource2.isResultSetArrayEmpty())
			 if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
			 {
				 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
					jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST,alJasperPrintList);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "c:\\hay.xls");
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, strSheetNames);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					jExcelApiExporter.exportReport();
			 }//end of if(request.getParameter("reportType").equals("EXL"))
			 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
			 request.setAttribute("boas",boas);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)		
		 return (mapping.findForward(strReportdisplay));
	 }
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
	 public ActionForward doClose(ActionMapping mapping,ActionForm form,
			 HttpServletRequest request,HttpServletResponse response) throws TTKException{
		 try{
			 setLinks(request);
			 log.debug("Inside the doClose method of EnrolMonitorReportsAction");
			 ((DynaActionForm)form).initialize(mapping);//reset the form data
			 return this.getForward(strIdcardsreportlist,mapping,request);
		 }//end of try
		 catch(TTKException expTTK)
	     {
	    	 return this.processExceptions(request, mapping, expTTK);
	     }// end of catch(TTKException expTTK)
	     catch(Exception exp)
	     {
	    	 return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
	     }// end of catch(Exception exp)
	 }//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	 
}//end of class EnrolMonitorReportsAction
