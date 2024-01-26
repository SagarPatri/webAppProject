/**
 * @ (#) CustomerCareReportsAction.java July 28, 2006
 * Project      : TTK HealthCare Services
 * File         : CustomerCareReportsAction.java
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : Nov 24, 2006
 *
 * @author       : Balaji C R B
 * Modified by   : Balakrishna E
 * Modified date :
 * Reason        :
 */
package com.ttk.action.reports;


 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.util.HashMap;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
 import net.sf.jasperreports.engine.JasperCompileManager;
 import net.sf.jasperreports.engine.JasperExportManager;
 import net.sf.jasperreports.engine.JasperFillManager;
 import net.sf.jasperreports.engine.JasperPrint;
 import net.sf.jasperreports.engine.JasperReport;
 import net.sf.jasperreports.engine.data.JRXmlDataSource;
 import net.sf.jasperreports.engine.util.JRXmlUtils;

 import org.apache.log4j.Logger;
 import org.apache.struts.action.ActionForm;
 import org.apache.struts.action.ActionForward;
 import org.apache.struts.action.ActionMapping;

 import com.ttk.action.TTKAction;
 import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;

 public class CustomerCareReportsAction extends TTKAction{
	 
	 private static Logger log = Logger.getLogger( CustomerCareReportsAction.class );
	 
	 //Exception Message Identifier
	 private static final String strReportExp="report";
	 
	 /**
	  * This method is used to view the customer report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the Default method of CustomerCareReportsAction");
			 setLinks(request);
			 String strParam = request.getParameter("parameter");
			 //constant declarations
			 String strReportdisplay = "reportdisplay";
			 String reportID = request.getParameter("reportID");
			 String jrxmlFile = null;
			 JasperReport jasperReport = null, shortFallQuestionsJasperReport;
			 JasperReport emptyReport;
			 JasperPrint jasperPrint = null;
			 ByteArrayOutputStream boas = new ByteArrayOutputStream();
			 HashMap<String, Object> hashMap = new HashMap<String, Object>();
			 jrxmlFile = request.getParameter("fileName");
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 TTKReportDataSource ttkReportDataSource = new TTKReportDataSource(request.getParameter("reportID"),strParam);
			 if(reportID.equalsIgnoreCase("GoBack"))
			 {
				 return (mapping.findForward(strReportdisplay));
			 }//end of if(reportID.equalsIgnoreCase("GoBack"))
			 if(!(ttkReportDataSource.getResultData().next()))
			 {
				 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
			 }//end of else
			 else if(reportID.equals("PreAuthHistoryList")||reportID.equals("PreAuthHistoryInvestigationList") ||reportID.equals("ClaimHistoryList") || reportID.equals("ClaimHistoryInvestigationList")||reportID.equals("InvestigationDetails")||reportID.equals("BufferList")||reportID.equals("BufferDetails")||reportID.equals("DisallowedBillList"))
			 {
				 ttkReportDataSource.getResultData().beforeFirst();
				 jasperReport = JasperCompileManager.compileReport(jrxmlFile);
				 hashMap.put("param", request.getParameter("parameter"));
				 hashMap.put("cignayn", request.getParameter("cignayn"));
				 if(reportID.equals("DisallowedBillList"))
				 {
					 String claimNumber = request.getParameter("claimNumber");
					 String enrollmentID = request.getParameter("enrollmentID");
					 String claimSettlNumber = request.getParameter("claimSettlNumber");
					 hashMap.put("claimNumber", claimNumber);
					 hashMap.put("enrollmentID",enrollmentID);
					 hashMap.put("claimSettlNumber", claimSettlNumber);
				 }//end of if(reportID.equals("DisallowedBillList"))
				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
			 }//end of if(reportID.equalsIgnoreCase("PreAuthHistoryList"))
			 else if(reportID.equals("EndorsementList"))
			 {
				 /*log.info("inside else if(reportID.equals(\"EndorsementList\"))");
				 log.info("jrxml file is"+jrxmlFile);*/
				 jasperReport = JasperCompileManager.compileReport(jrxmlFile);
				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new TTKReportDataSource(request.getParameter("reportID"),strParam));			 
			 }//end of else if(reportID.equals("PreAuthHistoryList"))
			 else if(reportID.equals("CodeRejClus"))
			 {
				 //log.info("jrxml file is"+jrxmlFile);
				 jasperReport = JasperCompileManager.compileReport(jrxmlFile);
				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new TTKReportDataSource(request.getParameter("reportID"),strParam));			 
			 }//end of else if(reportID.equals("PreAuthHistoryList"))
			 else if(reportID.equals("DisallowedBillList1"))
			 {
				 //log.info("jrxml file is"+jrxmlFile);
				 jasperReport = JasperCompileManager.compileReport(jrxmlFile);
				 String claimNumber = request.getParameter("claimNumber");
				 String enrollmentID = request.getParameter("enrollmentID");
				 String claimSettlNumber = request.getParameter("claimSettlNumber");
				 hashMap.put("claimNumber", claimNumber);
				 hashMap.put("enrollmentID",enrollmentID);
				 hashMap.put("claimSettlNumber", claimSettlNumber);
				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new TTKReportDataSource(request.getParameter("reportID"),strParam));			 
			 }//end of else if(reportID.equals("PreAuthHistoryList"))
			 else if(reportID.equalsIgnoreCase("ShortfallQuestions"))
			 {
				 TTKReportDataSource ttkReportDS = new TTKReportDataSource(request.getParameter("reportID"),strParam);
				 org.w3c.dom.Document document = null;
				 if(ttkReportDS.getResultData()!=null && ttkReportDS.getResultData().next())
				 {
					 String strQuery = ttkReportDS.getResultData().getString("questions");
					 if(strQuery != null)
					 {
						 document = JRXmlUtils.parse(new ByteArrayInputStream(strQuery.getBytes()));
					 }//end of if(strQuery == null || strQuery.trim().length() == 0)
				 }//end of if(ttkReportDS.getResultData()!=null && ttkReportDS.getResultData().next())
				 String strQuery = ttkReportDS.getResultData().getString("questions");
				 if (strQuery == null || strQuery.trim().length() == 0)
				 {
					 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
				 }//end of else
				 else{
					 shortFallQuestionsJasperReport = JasperCompileManager.compileReport("generalreports/ShortfallQuestionsSeparate.jrxml");
					 jasperPrint = JasperFillManager.fillReport(shortFallQuestionsJasperReport, hashMap, new JRXmlDataSource(document,"//query"));
				 }//end of else
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 request.setAttribute("boas",boas);
			 }//end of else if(reportID.equalsIgnoreCase("ShortfallQuestions"))
			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			 request.setAttribute("boas",boas);
			 return (mapping.findForward(strReportdisplay));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 /**
	  * This method is used to view the summary report from the Back link.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doGoBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the doGoBack method of CustomerCareReportsAction");
			 setLinks(request);
			 String strReportdisplay = "reportdisplay";
			 return (mapping.findForward(strReportdisplay));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of public ActionForward doGoBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
			
}//end of CustomerCareReportsAction

