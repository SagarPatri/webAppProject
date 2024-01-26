 /**
  * @ (#) OnlineReportsAction.java March 8, 2008
  * Project      : TTK HealthCare Services
  * File         : OnlineReportsAction.java
  * Author       : Balaji C R B
  * Company      : Span Systems Corporation
  * Date Created : March 8, 2008
  *
  * @author       :Balaji C R B
  * Modified by   :
  * Modified date :
  * Reason        :
  */

 package com.ttk.action.onlinereports;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.JXLException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRXmlUtils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.onlineforms.providerLogin.PreAuthSearchVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

 public class OnlineReportsAction extends TTKAction{

	 private static final Logger log = Logger.getLogger( OnlineReportsAction.class );

	 //declaration of forward paths
	 private static final String strHRReportList="hrreportlist";
	 private static final String strInsCompReportList="inscompreportlist";
	 private static final String strHRParameter="hrreportsparameter";
	 private static final String strBrokerRpt="brokerreports";

	 private static final String strAccentureCustomizedHRParameter="customisedreportsparameter";

	 private static final String strInsCompParameter="inscompreportsparameter";
	 private static final String strReportdisplay="reportdisplay";
	 private static final String strCustomisedRpt="customisedreports";
	 private static final String strBrokerParameter="brokerreportsparameter";
	 

	 //declaration of constants
	 private static final String strHRReports="HR";
	 private static final String strInsCompReports="Insurance Company";
	 private static final String strCustomisedReports="Customize Reports";
	 private static final String strBrokerReports="View Reports";

	 private static final String strHospitalReports="Hospital";
	 private static final String strHospitalReportList="hospreportlist";
	 private static final String strHospitalParameter="hospreportsparameter";


	 //Exception Message Identifier
	 private static final String strReportExp="onlinereport";

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
	 public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 //log.debug("Inside the doDefault method of OnlineReportsAction");
			 setOnlineLinks(request);
			 String strForwardPathList="";
			 String strActiveLink=TTKCommon.getActiveLink(request);
			 String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			 //set the Forward Paths based on the sublink
			 if(strActiveSubLink.equalsIgnoreCase(strHRReports))
			 {
				 strForwardPathList=strHRReportList;
			 }//end of if(strActiveSubLink.equalsIgnoreCase(strEnrollment))
			 else if(strActiveSubLink.equalsIgnoreCase(strInsCompReports))
			 {
				 strForwardPathList=strInsCompReportList;
			 }//end of if(strActiveSubLink.equalsIgnoreCase(strInsCompReports))
			 else if((strActiveLink.equals(strCustomisedReports)) && (strActiveSubLink.equals(strCustomisedReports)))
			 {
				 strForwardPathList=strCustomisedRpt;
			 }//end of else if((strActiveLink.equals(strCustomisedReports)) && (strActiveSubLink.equals(strCustomisedReports)))
			 else if((strActiveSubLink.equals(strHospitalReports)))
			 {
				 strForwardPathList=strHospitalReportList;
			 }//end of else if((strActiveLink.equals(strCustomisedReports)) && (strActiveSubLink.equals(strCustomisedReports)))
			 
			 else if((strActiveSubLink.equals(strBrokerReports)))
			 {
				 strForwardPathList=strBrokerRpt;
			 }//end of else if((strActiveLink.equals(strBrokerReports))
			 
			 Document reportsListDoc=null;
			 ((DynaActionForm)form).initialize(mapping);//reset the form data
			 //get the reports list Document
			 reportsListDoc=TTKCommon.getDocument("OnlineReportsList.xml");
			 request.setAttribute("ReportsListDoc",reportsListDoc);
			 return this.getForward(strForwardPathList,mapping,request);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processOnlineExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	 /**
	  * This method is used to view the report details.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doReportsDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 //log.debug("Inside the doReportsDetail method of ReportsAction");
			 setOnlineLinks(request);
			 String strReportParameter="";
			 String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			 //set the Forward Paths based on the sublink
			 if(strActiveSubLink.equalsIgnoreCase(strHRReports))
			 {
				 strReportParameter=strHRParameter;
			 }//end of if(strActiveSubLink.equalsIgnoreCase(strHRReports))
			 else if(strActiveSubLink.equalsIgnoreCase(strInsCompReports))
			 {
				 strReportParameter=strInsCompParameter;
			 }//end of else if(strActiveSubLink.equalsIgnoreCase(strInsCompReports))
			 else if(strActiveSubLink.equalsIgnoreCase(strBrokerReports))
			 {
				 strReportParameter=strBrokerParameter;
			 }//end of else if(strActiveSubLink.equalsIgnoreCase(strBrokerReports))
			 
			 else if((strActiveSubLink.equals(strHospitalReports)))
			 {
				 strReportParameter=strHospitalParameter;
			 }//end of else if((strActiveLink.equals(strCustomisedReports)) && (strActiveSubLink.equals(strCustomisedReports)))
			 DynaActionForm frmOnlineReport=(DynaActionForm)form;

			 /*log.info("pdfFileName is :"+ frmOnlineReport.getString("pdfFileName"));
			 log.info("xlsFileName is :"+ frmOnlineReport.getString("xlsFileName"));
			 log.info("reportID is :"+ frmOnlineReport.getString("reportID"));
			 log.info("reportName is :"+ frmOnlineReport.getString("reportName"));
			 log.info("parameterValues is :"+ frmOnlineReport.getString("parameterValues"));
			 log.info("startDate is :"+ frmOnlineReport.getString("startDate"));
			 log.info("endDate is :"+ frmOnlineReport.getString("endDate"));
			 log.info("outputfile is :"+ frmOnlineReport.getString("outputfile"));*/

			 //frmReport.set("reportName",request.getParameter("reportName"));
			//KOC 1353 for payment report
			 if(frmOnlineReport.getString("reportID").equals("INSPaymentReport"))
			 {
				 
				 Document parameterDoc=TTKCommon.getDocument(request.getParameter("csvFileName"));
				 request.setAttribute("parameterDoc",parameterDoc);
			 }
			// On select IDs kocbroker 
			 else if(frmOnlineReport.getString("reportID").equals("ReportOnlinePreAuthBroRpt"))
			 {
				
				 Document parameterDoc=TTKCommon.getDocument("onlinereports/broker/PreAuthRptBroPDF.jrxml");
				 request.setAttribute("parameterDoc",parameterDoc);
			 }
			 else if(frmOnlineReport.getString("reportID").equals("ReportPreAuthBroSummary"))
			 {
				 Document parameterDoc=TTKCommon.getDocument("onlinereports/broker/PreAuthSummaryBroPDF.jrxml");
				 request.setAttribute("parameterDoc",parameterDoc);
			 }
			 else if(frmOnlineReport.getString("reportID").equals("ReportClmRegBroSummary"))
			 {
				 Document parameterDoc=TTKCommon.getDocument("onlinereports/broker/ClaimRegSummaryBroPDF.jrxml");
				 request.setAttribute("parameterDoc",parameterDoc);
			 }
			 else if(frmOnlineReport.getString("reportID").equals("ReportClmRegBroDetail"))
			 {
				 Document parameterDoc=TTKCommon.getDocument("onlinereports/broker/ClaimsRegDetailBroPDF.jrxml");
				 request.setAttribute("parameterDoc",parameterDoc);
			 }
			 else if(frmOnlineReport.getString("reportID").equals("ReportListBroEmpDepPeriod"))
			 {
				 Document parameterDoc=TTKCommon.getDocument("onlinereports/broker/EmpDependentperPeriod.jrxml");
				 request.setAttribute("parameterDoc",parameterDoc);
			 }
			 else
			 {
			 Document parameterDoc=TTKCommon.getDocument(request.getParameter("pdfFileName"));
			 request.setAttribute("parameterDoc",parameterDoc);
			 }
			//KOC 1353 for payment report
			 return this.getForward(strReportParameter,mapping,request);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processOnlineExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doReportsDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	 //IBM Customized Report
	 /**
	  * This method is used to view the report details.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */

	 public ActionForward doAccentureCustomizedReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	 {
		 try{
			 //log.debug("Inside the doReportsDetail method of ReportsAction");
			 setOnlineLinks(request);
			 String strReportParameter="";
			 String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			 
			 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			 String strGroupID = userSecurityProfile.getGroupID().toString();
			 
			 ArrayList alCustomGroupList= new ArrayList();
			 OnlineAccessManager onlineAccessManagerObject= this.getOnlineAccessManagerObject();
			 alCustomGroupList = onlineAccessManagerObject.getSelectedPolicyNumber(strGroupID);

			//alCustomGroupList = onlineAccessManagerObject.getPolicyNumber(strGroupID);

			 request.getSession().setAttribute("alCustomGroupList",alCustomGroupList);
			 if(strActiveSubLink.equalsIgnoreCase(strCustomisedReports))
			 {
				 strReportParameter=strAccentureCustomizedHRParameter;
			 }//end of if(strActiveSubLink.equalsIgnoreCase(strHRReports))

			 //set the Forward Paths based on the sublink
			 return this.getForward(strReportParameter,mapping,request);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processOnlineExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }

	 /**
	    * Returns the onlineAccessManager session object for invoking methods on it.
	    * @return onlineAccessManager session object which can be used for method invocation
	    * @exception throws TTKException
	    */
	   private OnlineAccessManager getOnlineAccessManagerObject() throws TTKException
	   {
	   	OnlineAccessManager onlineAccessManager = null;
	   	try
	   	{
	   		if(onlineAccessManager == null)
	   		{
	   			InitialContext ctx = new InitialContext();
	   			onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
	   			log.debug("Inside getOnlineAccessManagerObject: onlineAccessManager: " + onlineAccessManager);
	   		}//end if
	   	}//end of try
	   	catch(Exception exp)
	   	{
	   		throw new TTKException(exp, "failure");
	   	}//end of catch
	   	return onlineAccessManager;
	   }//end of getOnlineAccessManagerObject()

	   /**	  Added for KOC-1255
		  * This method is used to generate the AccentureCustomizedreport.
		  * Finally it forwards to the appropriate view based on the specified forward mappings
		  *
		  * @param mapping The ActionMapping used to select this instance
		  * @param form The optional ActionForm bean for this request (if any)
		  * @param request The HTTP request we are processing
		  * @param response The HTTP response we are creating
		  * @return ActionForward Where the control will be forwarded, after this request is processed
		  * @throws Exception if any error occurs
		  */
	   public ActionForward doGenerateCustomAccentureReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
			 try{
				 //log.debug("Inside the doGenerateAdditioncutoffMaxRecRpt method of IBMReportAction");
				 setLinks(request);
				 TTKReportDataSource ttkReportsDataSource = null;
				 TTKReportDataSource ttkReportsDataSource1 = null;
			  	 TTKReportDataSource ttkReportsDataSource2 = null;
			  	 TTKReportDataSource ttkReportsDataSource3 = null;
			  	 TTKReportDataSource ttkReportsDataSource4 = null;
			  	 TTKReportDataSource ttkReportsDataSource5 = null;
			  	 String strReportID = "Accenture_Report_MC";

			  	 ttkReportsDataSource = new TTKReportDataSource(strReportID,request.getParameter("customPolicySeqID"),request.getParameter("sFrom"),request.getParameter("sTo"));

				 HSSFWorkbook wb = new HSSFWorkbook();
				 HSSFSheet sheet =wb.createSheet("Dashboard Claims MC");
				 HSSFRow title = sheet.createRow(0);
				 HSSFRow date = sheet.createRow(2);
				 HSSFRow head = sheet.createRow(4);
				 HSSFRow rowhead = sheet.createRow(5);

				 HSSFCellStyle cellStyle = setHeaderStyle(wb);
				 HSSFCellStyle cellStyle1 = setColumnStyle(wb);

				 HSSFCell T = title.createCell((short)2);
				 T.setCellStyle(cellStyle);
				 T.setCellValue(new HSSFRichTextString("VIDAL HEALTH TPA SERVICES PRIVATE LTD"));
				 sheet.addMergedRegion(new Region(0,(short)2,0,(short)6));

				 HSSFCell sFromDate = date.createCell((short)1);
				 sFromDate.setCellStyle(cellStyle);
				 sFromDate.setCellValue(new HSSFRichTextString("From Date:"));

				 HSSFCell FromDate = date.createCell((short)2);
				 FromDate.setCellStyle(cellStyle);
				 FromDate.setCellValue(new HSSFRichTextString(request.getParameter("sFrom")));

				 HSSFCell sToDate = date.createCell((short)6);
				 sToDate.setCellStyle(cellStyle);
				 sToDate.setCellValue(new HSSFRichTextString("To Date:"));

				 HSSFCell ToDate = date.createCell((short)7);
				 ToDate.setCellStyle(cellStyle);
				 ToDate.setCellValue(new HSSFRichTextString(request.getParameter("sTo")));

				 HSSFCell H = head.createCell((short)3);
				 H.setCellStyle(cellStyle);
				 H.setCellValue(new HSSFRichTextString("Member claim"));
				 sheet.addMergedRegion(new Region(4,(short)3,4,(short)4));

				 ResultSetMetaData metaData = ttkReportsDataSource.getResultData().getMetaData();
	 			 int rowCount = metaData.getColumnCount();
	 				for(int i=0;i<rowCount;i++)
					{
						HSSFCell r1 = rowhead.createCell((short)i);
	 					r1.setCellStyle(cellStyle);
	 					r1.setCellValue(new HSSFRichTextString(metaData.getColumnName(i+1)));
	 				}
	 			    int index = 6;
			  	    while(ttkReportsDataSource.getResultData().next())
			  	    {
			  	    	HSSFRow row = sheet.createRow(index);
			  	    	for(int i=0;i<rowCount;i++)
			 			{
			  	    		if(metaData.getColumnTypeName(i+1).equalsIgnoreCase("Number"))
			  	    		{
			  	    			HSSFCell c1 = row.createCell((short)i);
			  	    			c1.setCellStyle(cellStyle1);
			  	    			c1.setCellValue(ttkReportsDataSource.getResultData().getDouble(i+1));
							}
			  	    		else
			  	    		{
			  	    			HSSFCell c1 = row.createCell((short)i);
			  	    			c1.setCellStyle(cellStyle1);
			  	    			c1.setCellValue(new HSSFRichTextString(ttkReportsDataSource.getResultData().getString(i+1)));
							}

			 			}

			  	   	index++;
			  	   }
			  	    String strReportID1 = "Accenture_Report_CC";
					ttkReportsDataSource1 = new TTKReportDataSource(strReportID1,request.getParameter("customPolicySeqID"),request.getParameter("sFrom"),request.getParameter("sTo"));
					HSSFRow head1 = sheet.createRow(14);
					HSSFRow rowhead1 = sheet.createRow(15);

					HSSFCell H1 = head1.createCell((short)3);
					H1.setCellStyle(cellStyle);
					H1.setCellValue(new HSSFRichTextString("Cashless claim"));
					sheet.addMergedRegion(new Region(14,(short)3,14,(short)4));

					ResultSetMetaData metaData1 = ttkReportsDataSource1.getResultData().getMetaData();
	 				int rowCount1 = metaData1.getColumnCount();
	 				for(int j=0;j<rowCount1;j++)
	 				{
	 					HSSFCell r1 = rowhead1.createCell((short)j);
	 					r1.setCellStyle(cellStyle);
	 					r1.setCellValue(new HSSFRichTextString(metaData1.getColumnName(j+1)));
	 				}
	 				int index1 = 16;
	 				while(ttkReportsDataSource1.getResultData().next())
			  	      {
	 					HSSFRow row1 = sheet.createRow(index1);
	 					for(int j=0;j<rowCount1;j++)
			 			{
	 						if(metaData1.getColumnTypeName(j+1).equalsIgnoreCase("Number"))
	 						{
	 							HSSFCell c1 = row1.createCell((short)j);
			  	    			c1.setCellStyle(cellStyle1);
			  	    			c1.setCellValue(ttkReportsDataSource1.getResultData().getDouble(j+1));
							}
	 						else
	 						{
	 							HSSFCell c1 = row1.createCell((short)j);
								c1.setCellStyle(cellStyle1);
								c1.setCellValue(new HSSFRichTextString(ttkReportsDataSource1.getResultData().getString(j+1)));
	 						}

			 			}
			     		index1++;
			  	    }
	 				String strReportID2 = "Accenture_Report_SMC";
					ttkReportsDataSource2 = new TTKReportDataSource(strReportID2,request.getParameter("customPolicySeqID"),request.getParameter("sFrom"),request.getParameter("sTo"));
					HSSFRow head2 = sheet.createRow(24);
					HSSFRow rowhead2 = sheet.createRow(25);

					HSSFCell H2 = head2.createCell((short)2);
					H2.setCellStyle(cellStyle);
					H2.setCellValue(new HSSFRichTextString("shortfall Documents Recd for Member  Claim"));
					sheet.addMergedRegion(new Region(24,(short)2,24,(short)6));

					ResultSetMetaData metaData2 = ttkReportsDataSource2.getResultData().getMetaData();
	 				int rowCount2 = metaData2.getColumnCount();
	 				for(int k=0;k<rowCount2;k++)
	 				{
	 					HSSFCell r1 = rowhead2.createCell((short)k);
	 					r1.setCellStyle(cellStyle);
	 					r1.setCellValue(new HSSFRichTextString(metaData2.getColumnName(k+1)));
	 				}
	 				int index2 = 26;
	 				while(ttkReportsDataSource2.getResultData().next())
			  	    {
			  	    	HSSFRow row2 = sheet.createRow(index2);
			  	    	for(int k=0;k<rowCount2;k++)
			 			{
			  	    		if(metaData2.getColumnTypeName(k+1).equalsIgnoreCase("Number"))
			  	    		{
			  	    			HSSFCell c1 = row2.createCell((short)k);
			  	    			c1.setCellStyle(cellStyle1);
			  	    			c1.setCellValue(ttkReportsDataSource2.getResultData().getDouble(k+1));
			  	    		}
			  	    		else
			  	    		{
			  	    			HSSFCell c1 = row2.createCell((short)k);
								c1.setCellStyle(cellStyle1);
								c1.setCellValue(new HSSFRichTextString(ttkReportsDataSource2.getResultData().getString(k+1)));
			  	    		}

			 			}

			  	    	index2++;
			  	    }

	 				//afer working
	 				String strReportID3 = "Accenture_Report_SCC";
					ttkReportsDataSource3 = new TTKReportDataSource(strReportID3,request.getParameter("customPolicySeqID"),request.getParameter("sFrom"),request.getParameter("sTo"));
					HSSFRow head3 = sheet.createRow(33);
					HSSFRow rowhead3 = sheet.createRow(34);

					HSSFCell H3 = head3.createCell((short)2);
					H3.setCellStyle(cellStyle);
					H3.setCellValue(new HSSFRichTextString("shortfall Documents Recd for Cashless  Claim"));
					sheet.addMergedRegion(new Region(33,(short)2,33,(short)6));

			  	    ResultSetMetaData metaData3 = ttkReportsDataSource3.getResultData().getMetaData();
	 				int rowCount3 = metaData3.getColumnCount();
	 				for(int L=0;L<rowCount3;L++)
	 				{
	 					HSSFCell r1 = rowhead3.createCell((short)L);
	 					r1.setCellStyle(cellStyle);
	 					r1.setCellValue(new HSSFRichTextString(metaData3.getColumnName(L+1)));

	 				}
	 				int index3 = 35;
	 				while(ttkReportsDataSource3.getResultData().next())
			  	    {
			  	    	HSSFRow row3 = sheet.createRow(index3);
			  	    	for(int L=0;L<rowCount2;L++)
			 			{
			  	    		if(metaData3.getColumnTypeName(L+1).equalsIgnoreCase("Number"))
			  	    		{
			  	    			HSSFCell c1 = row3.createCell((short)L);
			  	    			c1.setCellStyle(cellStyle1);
			  	    			c1.setCellValue(ttkReportsDataSource3.getResultData().getDouble(L+1));

			  	    		}
			  	    		else
			  	    		{
			  	    			HSSFCell c1 = row3.createCell((short)L);
								c1.setCellStyle(cellStyle1);
								c1.setCellValue(new HSSFRichTextString(ttkReportsDataSource3.getResultData().getString(L+1)));

			  	    		}

			 			}

			  	    	index3++;
			  	    }
	 				//end
	 			//PreAuth Report
	 			String strReportID4 = "Accenture_Report_PRE";
	 			ttkReportsDataSource4 = new TTKReportDataSource(strReportID4,request.getParameter("customPolicySeqID"),request.getParameter("sFrom"),request.getParameter("sTo"));
				HSSFRow head4 = sheet.createRow(40);
				HSSFRow rowhead4 = sheet.createRow(41);
				HSSFCell H4 = head4.createCell((short)3);
				H4.setCellStyle(cellStyle);
				H4.setCellValue(new HSSFRichTextString("Pre-Auth Request"));
				sheet.addMergedRegion(new Region(40,(short)3,40,(short)4));

				ResultSetMetaData metaData4 = ttkReportsDataSource4.getResultData().getMetaData();
				int rowCount4 = metaData4.getColumnCount();
				for(int M=0;M<rowCount4;M++)
				{
					HSSFCell r1 = rowhead4.createCell((short)M);
					r1.setCellStyle(cellStyle);
					r1.setCellValue(new HSSFRichTextString(metaData4.getColumnName(M+1)));

				}
				int index4 = 42;
				while(ttkReportsDataSource4.getResultData().next())
		  	    	{
		  	    	HSSFRow row4 = sheet.createRow(index4);
		  	    	for(int M=0;M<rowCount4;M++)
		 			{
		  	    		if(metaData4.getColumnTypeName(M+1).equalsIgnoreCase("Number"))
		  	    		{
		  	    			HSSFCell c1 = row4.createCell((short)M);
		  	    			c1.setCellStyle(cellStyle1);
		  	    			c1.setCellValue(ttkReportsDataSource4.getResultData().getDouble(M+1));
		  	    		}
		  	    		else
		  	    		{
		  	    			HSSFCell c1 = row4.createCell((short)M);
							c1.setCellStyle(cellStyle1);
							c1.setCellValue(new HSSFRichTextString(ttkReportsDataSource4.getResultData().getString(M+1)));
						}

		 			}

		  	    	index4++;
		  	    }
				//end

				//call center Report
				String strReportID5 = "Accenture_Report_CALL";
	 			ttkReportsDataSource5 = new TTKReportDataSource(strReportID5,request.getParameter("customPolicySeqID"),request.getParameter("sFrom"),request.getParameter("sTo"));
				HSSFRow head5 = sheet.createRow(50);
				HSSFRow rowhead5 = sheet.createRow(51);
				HSSFCell H5 = head5.createCell((short)0);
				H5.setCellStyle(cellStyle);
				H5.setCellValue(new HSSFRichTextString("Customer care - Inbound Calls"));
				sheet.addMergedRegion(new Region(50,(short)0,50,(short)3));

		  	    ResultSetMetaData metaData5 = ttkReportsDataSource5.getResultData().getMetaData();
				int rowCount5 = metaData5.getColumnCount();
				for(int N=0;N<rowCount5;N++)
				{
					HSSFCell r1 = rowhead5.createCell((short)N);
					r1.setCellStyle(cellStyle);
					r1.setCellValue(new HSSFRichTextString(metaData5.getColumnName(N+1)));
				}
				int index5 = 52;
				while(ttkReportsDataSource5.getResultData().next())
		  	    {
		  	    	HSSFRow row5 = sheet.createRow(index5);
		  	    	for(int N=0;N<rowCount5;N++)
		 			{
		  	    		if(metaData5.getColumnTypeName(N+1).equalsIgnoreCase("Number"))
		  	    		{
		  	    			HSSFCell c1 = row5.createCell((short)N);
		  	    			c1.setCellStyle(cellStyle1);
		  	    			c1.setCellValue(ttkReportsDataSource5.getResultData().getDouble(N+1));
		  	    		}
		  	    		else
		  	    		{
		  	    			HSSFCell c1 = row5.createCell((short)N);
							c1.setCellStyle(cellStyle1);
							c1.setCellValue(new HSSFRichTextString(ttkReportsDataSource5.getResultData().getString(N+1)));
						}

		 			}

		  	    	index5++;
		  	    }

			   String File = "Dashboard Report";
		  	    // write it as an excel attachment
		  	   ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		  	   wb.write(outByteStream);
		  	   byte [] outArray = outByteStream.toByteArray();
		  	   response.setContentType("application/ms-excel");
		  	   response.setContentLength(outArray.length);
		  	   response.setHeader("Expires:", "0"); // eliminates browser caching
		  	   response.setHeader("Content-Disposition", "attachment; filename=Dashboard Report.xls");
		  	   OutputStream outStream = response.getOutputStream();
		  	   outStream.write(outArray);
		  	   outStream.flush();
			   

			 }//end of try
			 catch(TTKException expTTK)
			 {
				 return this.processExceptions(request, mapping, expTTK);
			 }//end of catch(TTKException expTTK)
			 catch(Exception exp)
			 {
				 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			 }//end of catch(Exception exp)*/
			 //return (mapping.findForward(strLoginChildBornReportdisplay));
			 return(mapping.findForward(strAccentureCustomizedHRParameter));
	 }//end of doGenerateDailyCancellationRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	   private HSSFCellStyle setHeaderStyle(HSSFWorkbook wb)
		{
			HSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			//font.setColor(IndexedColors.PLUM.getIndex());
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFont(font);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setFillForegroundColor(new HSSFColor.AQUA().getIndex());
			//cellStyle.setFillBackgroundColor(new HSSFColor.GREEN().getIndex());
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			return cellStyle;
		}

		private HSSFCellStyle setColumnStyle(HSSFWorkbook wb)
		{
			HSSFFont font = wb.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle cellStyle1 = wb.createCellStyle();
			cellStyle1.setFont(font);
			cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle1.setFillForegroundColor(new HSSFColor.PALE_BLUE().getIndex());
			cellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			return cellStyle1;
		}
		
		
		//added new for kocbroker
		/**
		  * This method is used to generate the report for Broker Login to view Claim Settlement Details.
		  * Finally it forwards to the appropriate view based on the specified forward mappings
		  *
		  * @param mapping The ActionMapping used to select this instance
		  * @param form The optional ActionForm bean for this request (if any)
		  * @param request The HTTP request we are processing
		  * @param response The HTTP response we are creating
		  * @return ActionForward Where the control will be forwarded, after this request is processed
		  * @throws Exception if any error occurs
		  */
		public ActionForward doGenerateSettlementReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
			 try{
				 log.debug("Inside the doGenerateSettlementReport method of OnlineReportsAction");
				 setLinks(request);
				 JasperReport jasperReport, jasperSubReport, emptyReport;
				 JasperPrint jasperPrint;
				 TTKReportDataSource ttkReportDataSource = null;
				 String jrxmlfile=request.getParameter("fileName");
				 ttkReportDataSource = new TTKReportDataSource(request.getParameter("reportID"),request.getParameter("parameter"));
				 try
				 {
					 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
					 jasperSubReport = JasperCompileManager.compileReport("generalreports/MediClaimSub.jrxml");
					 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
					 HashMap<String, Object> hashMap = new HashMap<String, Object>();
					 ByteArrayOutputStream boas=new ByteArrayOutputStream();
					 hashMap.put("MyDataSource",new TTKReportDataSource("MediClaimComLineItems",request.getParameter("parameter")));
					 hashMap.put("MediClaimSubReport",jasperSubReport);
					 if(ttkReportDataSource.getResultData().next())
					 {
						 ttkReportDataSource.getResultData().beforeFirst();
						 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap, ttkReportDataSource);					 
					 }//end of if(ttkReportDataSource.getResultData().next()))
					 else{
						 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
					 }//end of else
					 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
					 request.setAttribute("boas",boas);
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
				 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			 }//end of catch(Exception exp)
		 }//end of doGenerateCompReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
	 public ActionForward doGenerateReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 //log.debug("Inside the doGenerateReport method of OnlineReportsAction");
			 setOnlineLinks(request);
			 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			 JasperReport jasperReport=null,emptyReport,insjasperReport=null;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null;
			 DynaActionForm frmOnlineReport = (DynaActionForm) form;
			 log.info("pdfFileName is :"+ frmOnlineReport.getString("pdfFileName"));
			 log.info("xlsFileName is :"+ frmOnlineReport.getString("xlsFileName"));
			 log.info("reportID is :"+ frmOnlineReport.getString("reportID"));
			 log.info("reportName is :"+ frmOnlineReport.getString("reportName"));
			 log.info("parameterValues is :"+ frmOnlineReport.getString("parameterValues"));
			 log.info("startDate is :"+ frmOnlineReport.getString("startDate"));
			 log.info("endDate is :"+ frmOnlineReport.getString("endDate"));
			 log.info("outputfile is :"+ frmOnlineReport.getString("outputfile"));
			 String strReportID = request.getParameter("reportID");
			 String strReportType = request.getParameter("reportType");
			 String strParam = "";String strUserID="";
			 strParam = request.getParameter("parameter");
			 String jrxmlfile= null;
			 String insjrxmlfile=request.getParameter("fileName");
			 if(strReportType.equals("EXL")){
				 jrxmlfile = request.getParameter("xlsFileName");
				 //KOCPreauthreport 
			 }//end of if(strReportType.equals("EXL"))
			//KOC 1353 for payment report
			 else if(strReportType.equals("TXT")){		 				 
				 jrxmlfile = request.getParameter("csvFileName");
				 			 
			 }
			 //KOC 1353 for payment report
			 else {
				 jrxmlfile = request.getParameter("pdfFileName");
				 
			 }//end of else
			  //Added for KOC-1267
			 if(strReportID.equals("ClaimViewHis"))
			 {				
				 strUserID = userSecurityProfile.getUSER_ID();
				 strParam = strParam.concat((new StringBuffer(strUserID+"|")).toString());
			 }
			 //Ended
			 if(strReportID.equals("OnlinePreAuthRpt")||strReportID.equals("ClmRegSummary")||strReportID.equals("ClmRegDetail")||strReportID.equals("ClaimsIntimations")||strReportID.equals("INSPaymentReport")||strReportID.equals("PreauthReport")||strReportID.equals("ClaimReport")||strReportID.equals("PolicyReport") ||strReportID.equals("OnlinePreAuthBroRpt")||strReportID.equals("ClmRegBroSummary")||strReportID.equals("ClmRegBroDetail")||strReportID.equals("PreAuthBroSummary")||strReportID.equals("ReportOnlinePreAuthBroRpt")||strReportID.equals("ReportPreAuthBroSummary")||strReportID.equals("ReportClmRegBroSummary")||strReportID.equals("ReportClmRegBroDetail")||strReportID.equals("ListBroEmpDepPeriod")||strReportID.equals("ReportListBroEmpDepPeriod"))//KOCPreauthreport
			 {
				Long lngUserID = userSecurityProfile.getUSER_SEQ_ID();
				strParam = (new StringBuffer("|"+lngUserID).append(strParam)).toString();
			 }//end of if(request.getParameter("reportID"))
			  if(strReportID.equals("AppRejPreAuthList") ||strReportID.equals("AppRejClaimsList"))//1274 A
			 {
				 String strReportFromDate = TTKCommon.checkNull(request.getParameter("reportFromDate"));
				 String strReportToDate = TTKCommon.checkNull(request.getParameter("reportToDate"));
				 Long lngUserID = userSecurityProfile.getUSER_SEQ_ID();
				strParam = (new StringBuffer("|"+lngUserID).append("|").append(strParam).append("|").append(strReportFromDate).append("|").append(strReportToDate).append("|")).toString();
					//	.append(strReportFromDate).append("|").append(strReportToDate).append("|")).toString();
				
			 }//end of if(strReportID.equals("AppRejPreAuthList") ||strReportID.equals("AppRejClaimsList"))//1274 A			
			 
			 
			 ttkReportDataSource = new TTKReportDataSource(request.getParameter("reportID"),strParam.toString());
			
			
			 try
			 {
				 if(TTKCommon.getActiveLink(request).equals("Online Information")&& insjrxmlfile!=null)
				 {
					 insjasperReport = JasperCompileManager.compileReport(insjrxmlfile);
				 }//end of if(TTKCommon.getActiveLink(request).equals("Online Information")) //KOC 1353 for payment report				
				 else if(strReportID.equals("INSPaymentReport"))
				 {					 
					 jasperReport = JasperCompileManager.compileReport(request.getParameter("csvFileName"));					 
				 }
				 else if(strReportID.equals("PreauthReport") || strReportID.equals("ClaimReport") || strReportID.equals("PolicyReport"))
				 {					 
					 jasperReport = JasperCompileManager.compileReport(request.getParameter("xlsFileName"));					 			 
				 }
				 else
				 {
					 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
					 
				 }//end of else
				 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 hashMap.put("parameter",strParam.substring(2,strParam.length()));
				 if(strReportID.equals("ClmRegd")){
					 hashMap.put("Claims Registered From",request.getParameter("clmRegFromDate"));
					 hashMap.put("Claims Registered To",request.getParameter("clmRegToDate"));
				 }//end of if(strReportID.equals("ClmRegd"))
				 else if(strReportID.equals("ListEmpDepPeriod")|| strReportID.equals("ListEmpDepTill"))
				 {
					 hashMap.put("From Date",request.getParameter("fromDate"));
					 hashMap.put("To Date",request.getParameter("toDate"));
				 }//end of else if(strReportID.equals("ListEmpDepPeriod")|| strReportID.equals("ListEmpDepTill"))
				 //On select IDs kocbroker
				 else if(strReportID.equals("OnlinePreAuthRpt") || strReportID.equals("OnlinePreAuthBroRpt")||strReportID.equals("ReportOnlinePreAuthBroRpt")|| strReportID.equals("PreAuthBroSummary")||strReportID.equals("ReportPreAuthBroSummary")){
					 hashMap.put("PreAuth Received From",request.getParameter("preAuthFromDate"));
					 hashMap.put("PreAuth Received To",request.getParameter("preAuthToDate"));
				 }//end of else if(strReportID.equals("OnlinePreAuthRpt"))
				//On select IDs kocbroker
				 else if(strReportID.equals("ClmRegSummary") || strReportID.equals("ClmRegDetail")|| strReportID.equals("ClmRegBroSummary") || strReportID.equals("ClmRegBroDetail")||strReportID.equals("ReportClmRegBroSummary")||strReportID.equals("ReportClmRegBroDetail")){
				 
					 hashMap.put("Claims Received From",request.getParameter("clmFromDate"));
					 hashMap.put("Claims Received To",request.getParameter("clmToDate"));
				 }//end of else if(strReportID.equals("ClmRegSummary") || strReportID.equals("ClmRegDetail") ) //KOC 1339 for mail				

				else if(strReportID.equals("ListBroEmpDepPeriod")||strReportID.equals("ReportListBroEmpDepPeriod")){
					 hashMap.put("From Date",request.getParameter("fromDate"));
					 hashMap.put("To Date",request.getParameter("toDate"));
				 }//end of else if(strReportID.equals("ClaimsIntimations"))
				 
				 else if(strReportID.equals("ClaimsIntimations")){
					 hashMap.put("From Date",request.getParameter("fromDate"));
					 hashMap.put("To Date",request.getParameter("toDate"));
				 }//end of else if(strReportID.equals("ClaimsIntimations")) //KOC 1339 for mail //KOC 1353 for payment report					
				 else if(strReportID.equals("INSPaymentReport")){
					 log.info(" INSPaymentReport ");
					 hashMap.put("From Date",request.getParameter("payFromDate"));
					 hashMap.put("To Date",request.getParameter("payToDate"));
				 }//end of else if(strReportID.equals("INSPaymentReport"))
				 else if(strReportID.equals("PreauthReport")|| strReportID.equals("ClaimReport")){					
					// hashMap.put("Report From",request.getParameter("reportFromDate"));
					// hashMap.put("Report To",request.getParameter("reportToDate"));
				 }//end of else if(strReportID.equals("INSPaymentReport")) //KOC 1353 for payment report			
				 
				 if(!(ttkReportDataSource.getResultData().next()))
				 {
					 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());					
				 }//end of if(!(ttkReportDataSource.getResultData().next()))
				 else if(!TTKCommon.getActiveLink(request).equals("Online Information"))
				 {				
					 jasperPrint = JasperFillManager.fillReport( jasperReport,
							 			hashMap,new TTKReportDataSource(request.getParameter("reportID"),strParam.toString()));					
				 }//end of if(ttkReportDataSource.getResultData().next())				 
				 else
				 {
					
					 if(strReportID.equals("ClaimViewHis"))
					 {
						 
						 jasperPrint = JasperFillManager.fillReport( insjasperReport, hashMap,new TTKReportDataSource(request.getParameter("reportID"),strParam)); 
					 }
					 else
					 {
						 
						jasperPrint = JasperFillManager.fillReport( insjasperReport, hashMap,new TTKReportDataSource(request.getParameter("reportID"),request.getParameter("parameter")));
					 }
					 
				 }//end of else
				 if(strReportType.equals("EXL"))//if the report type is Excel
				 {
					
					 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE , Boolean.TRUE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					 jExcelApiExporter.exportReport();
				 }//end of if(request.getParameter("reportType").equals("EXL"))				
				 else if(strReportType.equals("TXT"))
					{
						 log.info(" TXT ");
						 	JRCsvExporter exporter = new JRCsvExporter(); 
			    			exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER,",");
			    			exporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER,"\r\n");
			    			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			    			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, boas);    
			    			exporter.exportReport();
					}//KOC 1353 for payment report				
				 else//if report type if PDF
				 {
					 
					 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
                 }//end of else				
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
			 catch(JXLException e)
			 {
				 e.printStackTrace();
			 }
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }//end of catch (Exception e)
			 return (mapping.findForward(strReportdisplay));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processOnlineExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doGenerateReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	 // Summary Report KOC1224
	 /**
	  * This method is used to generate the summary report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	  public ActionForward doGenerateSummaryReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
			 try
			 {
				 log.debug("Inside the doGenerateSummaryReport method of OnlineReportsAction");
				 setOnlineLinks(request);
				 int iNoOfCursor = 4;
				 JasperReport objJasperReport[] =new JasperReport[iNoOfCursor];
				 JasperReport emptyReport = null;
				 JasperPrint objJasperPrint[] = new JasperPrint[iNoOfCursor];
				 JasperPrint jasperPrint;
				 ArrayList<Object> alJasperPrintList = new ArrayList<Object>();
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 TTKReportDataSource ttkReportDataSource = null;

				 String strReportID = request.getParameter("reportID");
				 String strReportType = request.getParameter("reportType");
				 String strParam = "";
				 strParam = request.getParameter("parameter");
				 String insjrxmlfile=request.getParameter("fileName");
				 String jrxmlFiles[] = new String[]
					                                 {
							"onlinereports/hr/OnlineSummaryReportFamily.jrxml",
							"onlinereports/hr/OnlineSummaryReportMember.jrxml",
							"onlinereports/hr/OnlineSummaryReportClaim.jrxml",
							"onlinereports/hr/OnlineSummaryReportPreauth.jrxml"
					                                 };
				 String strSheetNames[] = new String[]
				                                 {
							"FAMILY DETAILS","MEMBER DETAILS","CLAIM STATUS","PRE-AUTH STATUS"
					                                    };
			 objJasperReport[0] = JasperCompileManager.compileReport(jrxmlFiles[0]);
			 objJasperReport[1] = JasperCompileManager.compileReport(jrxmlFiles[1]);
			 objJasperReport[2] = JasperCompileManager.compileReport(jrxmlFiles[2]);
			 objJasperReport[3] = JasperCompileManager.compileReport(jrxmlFiles[3]);
				 ttkReportDataSource = new TTKReportDataSource(request.getParameter("reportID"),strParam.toString(),(iNoOfCursor+""));
				 HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
				 hashMap.put("From Date",request.getParameter("fromDate"));
				 hashMap.put("To Date",request.getParameter("toDate"));
				 if(ttkReportDataSource.isResultSetArrayEmpty())
				 {
						emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
						jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
						alJasperPrintList.add(jasperPrint);
				 }//end of if(ttkReportDataSource.isResultSetArrayEmpty())
				 else
				 {
					 for(int i=0;i<iNoOfCursor;i++)
					 {
							ResultSet rs = ttkReportDataSource.getResultData(i);
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
				 if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
				 {
					 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
						jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST,alJasperPrintList);
						jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
						jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "c:\\hay.xls");
					//	jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFile.toString());
						jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
						jExcelApiExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, strSheetNames);
						jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
						jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
						jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
						jExcelApiExporter.exportReport();
				 }//end of if(request.getParameter("reportType").equals("EXL"))
				 request.setAttribute("boas",boas);
				 return (mapping.findForward(strReportdisplay));
			 }//end of try
			 catch(TTKException expTTK)
			 {
				 return this.processOnlineExceptions(request, mapping, expTTK);
			 }//end of catch(TTKException expTTK)
			 catch(Exception exp)
			 {
				 return this.processOnlineExceptions(request, mapping, new TTKException(exp,"strReportExp"));
			 }//end of catch(Exception exp)
		 }//end of doGenerateSummaryReport()

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
	 public ActionForward doGeneratePolicyHistory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 //log.debug("Inside the doGeneratePolicyHistory method of OnlineReportsAction");
			 setOnlineLinks(request);
			 JasperReport jasperReport,jasperSubReport,emptyReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null;
			 String subReportParam = request.getParameter("parameter").substring(2, request.getParameter("parameter").length()-1);
			 // String strParam = request.getParameter("parameter").replaceAll("_","&");
			 String jrxmlfile=request.getParameter("fileName");
			 ttkReportDataSource = new TTKReportDataSource(request.getParameter("reportID"),request.getParameter("parameter"));
			 try
			 {
				 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
				 jasperSubReport  = JasperCompileManager.compileReport("generalreports/PolicyHistorySub.jrxml");
				 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 hashMap.put("Start Date",request.getParameter("startDate"));
				 hashMap.put("End Date",request.getParameter("endDate"));
				 hashMap.put("MyDataSource",new TTKReportDataSource("PolicyHistorySub",subReportParam));
				 hashMap.put("PoilcyHistorySub", jasperSubReport);
				 if(!(ttkReportDataSource.getResultData().next()))
				 {
					 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
				 }//end of if(!(ttkReportDataSource.getResultData().next()))
				 else
				 {
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new TTKReportDataSource(request.getParameter("reportID"),request.getParameter("parameter")));
				 }//end of else
				JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }//end of catch (Exception e)
			 return (mapping.findForward(strReportdisplay));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processOnlineExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doGeneratePolicyHistory(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	 /**
	  * This method is used to generate the report .
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doReportLink(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 //log.debug("Inside the doReportLink method of OnlineReportsAction");
			 setOnlineLinks(request);
			 return (mapping.findForward(strReportdisplay));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processOnlineExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doReportLink(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	 public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 //log.debug("Inside the doClose method of OnlineReportsAction");
		 return doDefault(mapping,form,request,response);
	 }//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	 /**
	  * This method is used to generate the  Enrollment report in Online Reports Of HR Login.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
    public ActionForward doGenerateEnrolReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 setOnlineLinks(request);
				int iNoOfCursor = 2;
				JasperReport objJasperReport[] =new JasperReport[iNoOfCursor];
				JasperReport emptyReport;
				JasperPrint objJasperPrint[] = new JasperPrint[iNoOfCursor];
				JasperPrint objJasperPrint1,objJasperPrint2 = new JasperPrint();
				JasperPrint jasperPrint;
				ArrayList<Object> alJasperPrintList = new ArrayList<Object>();
				ByteArrayOutputStream boas=new ByteArrayOutputStream();
				TTKReportDataSource  ttkReportsDataSource,ttkReportsDataSource1,ttkReportsDataSource2 = null;

			    String jrxmlFiles[] = new String[]
				                                 {
						"onlinereports/hr/AccentureSummaryReport.jrxml",
						"onlinereports/hr/HRAccentureDetailReport.jrxml"
				                                 };
				String strSheetNames[] = new String[]
				                                    {
						"Group Plan Summary Report","Group Detail Report1","Group Detail Report2","Group Detail Report3"
				                                    };
			 String strFrom = request.getParameter("reportFromDate");
			 String strTo = request.getParameter("reportToDate");
			 String strReportType = request.getParameter("reportType");
			 String strParams[] = new String[] {"|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|1|40000|","|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|40001|80000|","|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|80001|120000|"
			 };
			 objJasperReport[0] = JasperCompileManager.compileReport(jrxmlFiles[0]);
			 objJasperReport[1] = JasperCompileManager.compileReport(jrxmlFiles[1]);
			 ttkReportsDataSource = new TTKReportDataSource("GrpEnrollRpt",strParams[0],(iNoOfCursor+""));
			 ttkReportsDataSource1 = new TTKReportDataSource("GrpEnrollRpt",strParams[1],(iNoOfCursor+""));
			 ttkReportsDataSource2 = new TTKReportDataSource("GrpEnrollRpt",strParams[2],(iNoOfCursor+""));
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
			 hashMap.put("Report From", request.getParameter("reportFromDate"));
			 hashMap.put("Report To", request.getParameter("reportToDate"));
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
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processOnlineExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
		 return (mapping.findForward(strReportdisplay));
	 }//end of doGenerateEnrolReport

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
  public ActionForward doGenerateGrpPreAuthRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                         HttpServletResponse response) throws TTKException{
  	try{
  		setOnlineLinks(request);
  		JasperReport jasperReport,emptyReport;
  		JasperPrint jasperPrint;
  		TTKReportDataSource ttkReportsDataSource = null;
  		String strGroupId = request.getParameter("groupId");
		String strFrom = request.getParameter("reportFromDate");
		String strTo = request.getParameter("reportToDate");
		String strReportType = request.getParameter("reportType");
		String strReportID = request.getParameter("reportID");
		String strStatus = request.getParameter("sStatus");
		String strParam= "|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|"+strStatus+"|";
  		String jrxmlfile=null;
  		jrxmlfile="onlinereports/hr/HRPreAuthReport.jrxml";
  	    ttkReportsDataSource = new TTKReportDataSource(strReportID,strParam);
  		try
  		{
  			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
  			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
  			HashMap<String,String> hashMap = new HashMap<String,String>();
  			ByteArrayOutputStream boas=new ByteArrayOutputStream();
  			hashMap.put("GroupID",strGroupId);
		    hashMap.put("Report From",strFrom);
			hashMap.put("Report To",strTo);
			hashMap.put("sReportType",strReportType);
  			if(!(ttkReportsDataSource.getResultData().next()))
  			{
  				jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
  			}// end of if(!(ttkReportsDataSource.getResultData().next()))
  			else
  			{
  				jasperPrint = JasperFillManager.fillReport(jasperReport,hashMap,new TTKReportDataSource(request.getParameter("reportID"),strParam));
  			}// end of else

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
  			else // if report type if PDF
  			{
  				JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
  			}// end of else
  			// keep the byte array out stream in request scope to write
  			// in the BinaryStreamServlet
  			request.setAttribute("boas",boas);
  		}// end of try
  		catch(Exception exp)
      	{
      		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strReportExp));
      	}// end of catch(Exception exp)
  		return (mapping.findForward(strReportdisplay));
  	}// end of try
  	catch(TTKException expTTK)
  	{
  		return this.processOnlineExceptions(request, mapping, expTTK);
  	}// end of catch(TTKException expTTK)
  	catch(Exception exp)
  	{
  		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strReportExp));
  	}// end of catch(Exception exp)
  }// end of doGenerateGrpPreAuthRpt

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

  public ActionForward doGenerateGrpOnlineTATRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,
          HttpServletResponse response) throws TTKException{
	try
	{
		 setOnlineLinks(request);
		 int iNo = 3;
		 JasperReport objJasperReport,emptyReport = null;
		 JasperPrint objJasperPrint[] = new JasperPrint[iNo];
		 JasperPrint jasperPrint;
		 ArrayList<JasperPrint> alJasperPrintList = new ArrayList<JasperPrint>();
		 TTKReportDataSource ttkReportsDataSource = null;
		 String strFrom = request.getParameter("reportFromDate");
		 String strTo = request.getParameter("reportToDate");
		 String strFeedbackType = request.getParameter("sFeedBackType");
		 String jrxmlfile = null;
		 String strReportType = request.getParameter("reportType");
		 String strPDFParameter="|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|1|40000|"+strFeedbackType+"|";
		 String strParams[] = new String[] {"|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|1|40000|"+strFeedbackType+"|","|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|40001|80000|"+strFeedbackType+"|","|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|80001|120000|"+strFeedbackType+"|"
		 };
		 if(strReportType.equals("EXL")){
			 jrxmlfile = request.getParameter("xlsFileName");
		 }//end of if(strReportType.equals("EXL"))
		 else {
			 jrxmlfile = request.getParameter("pdfFileName");
		 }//end of else
		 String strSheetNames[] = new String[] {
					"OnlineTATReport1",
					"OnlineTATReport2",
					"OnlineTATReport3"};
		 if(strReportType.equals("EXL"))
		 {
		    ttkReportsDataSource = new TTKReportDataSource(request.getParameter("reportID"),strParams[0]);
		 }//end of if(strReportType.equals("EXL"))

		 try
		 {
			 ByteArrayOutputStream boas=new ByteArrayOutputStream();
			 objJasperReport = JasperCompileManager.compileReport(jrxmlfile);
			 HashMap<String,String> hashMap = new HashMap<String, String>();
			 hashMap.put("Report From",strFrom);
			 hashMap.put("Report To",strTo);
			 if(request.getParameter("reportType").equals("EXL"))
			 {
			  if(!(ttkReportsDataSource.getResultData().next()))
	  			{
				 	emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
					jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
					alJasperPrintList.add(jasperPrint);
	  			}//end of if(!(ttkReportsDataSource.getResultData().next()))
	  		  else
	  		   {
  				 for(int i=0; i<iNo; i++)
  				  {
  					objJasperPrint[i] = JasperFillManager.fillReport(objJasperReport,hashMap,new TTKReportDataSource(request.getParameter("reportID"),strParams[i]));
  					alJasperPrintList.add(objJasperPrint[i]);
  				  }//end of for
	  		   }// end of else
			 }//end of if
			 else
			 {
			    ttkReportsDataSource = new TTKReportDataSource(request.getParameter("reportID"),strPDFParameter);
			    if(!(ttkReportsDataSource.getResultData().isBeforeFirst()))
			    {
			    	emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
					jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
			    }//end of if(!(ttkReportsDataSource.getResultData().next()))
			    else
			    {
			       jasperPrint = JasperFillManager.fillReport(objJasperReport,hashMap,ttkReportsDataSource);
			    }//end of else
			    JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			 }//end of else
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
		 catch (JRException e)
		 {
			 e.printStackTrace();
		 }//end of catch(JRException e)
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }//end of catch (Exception e)
		return (mapping.findForward(strReportdisplay));
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strReportExp));
	}// end of catch(Exception exp)
  }// end of doGenerateGrpOnlineTATRpt

	/**
	  * This method is used to View the document stored in server.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
  public ActionForward doCustomizedWebdoc(ActionMapping mapping,ActionForm form,HttpServletRequest request,
  		HttpServletResponse response) throws Exception{
  	try{
  		setOnlineLinks(request);
  		ByteArrayOutputStream baos=null;
  		OutputStream sos = null;
  		FileInputStream fis = null;
  		String strModule = request.getParameter("module");
  		String strFilePath = "";
  		String strFileName= null;
  		UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
				request.getSession().getAttribute("UserSecurityProfile"));
  		String strGroupID = userSecurityProfile.getGroupID();
  		String strPath = TTKPropertiesReader.getPropertyValue("WebloginCustomizedDocs")+"/"+strGroupID;
  		if(strModule.equals("customised"))
  		{
  			File file = new File(strPath);
  			if(file.exists())
  			{
  			FilenameFilter docFilterObj = new FilenameFilter() {
  				public boolean accept(File dir, String name) {
  						return ((name.endsWith(".xls")) || (name.endsWith(".pdf")) || (name.endsWith(".doc")) || (name.endsWith(".zip")));
  					}//end of accept(File dir, String name)
  				};//end of FilenameFilter docFilterObj = new FilenameFilter()
  			File[] filearry= file.listFiles(docFilterObj);
  			if(filearry.length > 0)
  			{
  			for(int i=0;i<filearry.length;i++)
  			 {
  				int position=filearry[i].getName().lastIndexOf(".");
  				String strFilename=filearry[i].getName().substring(0,position);
  				if((filearry[i].exists()) && ((strFilename.equals("Float and Premium Report"))))
  				{
  					strFileName = filearry[i].getName();
  					strFilePath=TTKPropertiesReader.getPropertyValue("WebloginCustomizedDocs")+"/"+strGroupID+"/"+strFileName;
 				}//end of if((filearry[i].exists()) && (filearry[i].isFile())&& (filearry[i].getName().contains("Float"))&& (filearry[i].getName().endsWith(".xls")))
  				else
  				{
  					strFileName= "EmptyReport.xls";
  					strFilePath=TTKPropertiesReader.getPropertyValue("WebloginCustomizedDocs")+"/"+strFileName;
  				}//end of else
  			 }//end of for(int i=0;i<filearry.length;i++)
  			}//end of if(filearry.length > 0)
  			else
			 {
					strFileName= "EmptyReport.xls";
					strFilePath=TTKPropertiesReader.getPropertyValue("WebloginCustomizedDocs")+"/"+strFileName;
			  }//end of else
  			}//end of if(file.exists())
  			else
			{
				strFileName= "EmptyReport.xls";
				strFilePath=TTKPropertiesReader.getPropertyValue("WebloginCustomizedDocs")+"/"+strFileName;
			}//end of else
  			if(strFileName.endsWith(".xls"))
  			{
  			  response.setContentType("application/vnd.ms-excel");
  			}//end of if(strFileName.endsWith(".xls"))
  			else if(strFileName.endsWith(".pdf"))
  			{
  			  response.setContentType("application/pdf");
  			}//end of else if(strFileName.endsWith(".pdf"))
  			else if(strFileName.endsWith(".doc"))
  			{
  				response.setContentType("application/msword");
  			}//end of else if(strFileName.endsWith(".doc"))
  			else
  			{
  				response.setContentType("application/zip");
  			}//end of else
			response.addHeader("Content-Disposition","attachment; filename="+strFileName);
  			fis= new FileInputStream(strFilePath);
  		}//end of if(strModule.equals("customised"))

  		BufferedInputStream bis = new BufferedInputStream(fis);
  		baos=new ByteArrayOutputStream();
  		int ch;
  		while ((ch = bis.read()) != -1)
  		{
  			baos.write(ch);
  		}//end of while ((ch = bis.read()) != -1)
  		sos = response.getOutputStream();
  		baos.writeTo(sos);
  	}//end of try
  	catch(TTKException expTTK)
  	{
  		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
  	catch(Exception exp)
  	{
  		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strReportExp));
  	}//end of catch(Exception exp)
  	return null;
  }//end of doCustomizedWebdoc(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
  
  /**
	  * This method is used to generate the summary report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	  public ActionForward doGenerateHospitalReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
			 try
			 {
				 log.debug("Inside the doGenerateSummaryReport method of OnlineReportsAction");
				 setOnlineLinks(request);
				 //int iNoOfCursor = 4;
				 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");

				 JasperReport jasperReport=null;
				 //JasperReport objJasperReport[] =new JasperReport[iNoOfCursor];
				 JasperReport emptyReport = null;
				// JasperPrint objJasperPrint[] = new JasperPrint[iNoOfCursor];
				 JasperPrint objJasperPrint=null;

				 JasperPrint jasperPrint;
				 ArrayList<Object> alJasperPrintList = new ArrayList<Object>();
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 TTKReportDataSource ttkReportDataSource = null;

				 String strReportID = request.getParameter("reportID");
				 String strReportType = request.getParameter("reportType");
			//	 StringBuffer strParam = 
			//	 String strParam="";
				// strParam = request.getParameter("parameter");
				 
				StringBuffer  strParam=new StringBuffer();
				strParam=strParam.append("|").append(userSecurityProfile.getHospSeqId().toString()).append(request.getParameter("parameter"));
				
				 String jrxmlfile= null;
				// String insjrxmlfile=request.getParameter("fileName");
				 if(strReportType.equals("EXL")){
					 jrxmlfile =request.getParameter("xlsFileName");

				 }//end of if(strReportType.equals("EXL"))
				 else {
					 jrxmlfile = request.getParameter("pdfFileName");
				 }//end of else
				 
				// String jrxmlFile="onlinereports/hospital/OnlineSummaryReportClaim.jrxml";

				 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
				 
				  emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				 ttkReportDataSource = new TTKReportDataSource(request.getParameter("reportID"),strParam.toString());
				 HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
				 hashMap.put("From Date",request.getParameter("fromDate"));
				 hashMap.put("To Date",request.getParameter("toDate"));
				 if(!(ttkReportDataSource.getResultData().next()))
				 {
	
				 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
				 }//end of if(!(ttkReportDataSource.getResultData().next()))
				    else
				    {
				       jasperPrint = JasperFillManager.fillReport(jasperReport,hashMap,new TTKReportDataSource(request.getParameter("reportID"),strParam.toString()));
				    }//end of else
				   
				
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
				 }//end of if(request.getParameter("reportType").equals("EXL"))
				 else
				 {
					 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 }

				 request.setAttribute("boas",boas);
				 return (mapping.findForward(strReportdisplay));
			 }//end of try
			 catch(TTKException expTTK)
			 {
				 return this.processOnlineExceptions(request, mapping, expTTK);
			 }//end of catch(TTKException expTTK)
			 catch(Exception exp)
			 {
				 return this.processOnlineExceptions(request, mapping, new TTKException(exp,"strReportExp"));
			 }//end of catch(Exception exp)
		 }//end of doGenerateSummaryReport()

  
  
  
  
  
  
  public ActionForward doOnlineShorfallXmlReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	  try{
	 			 log.debug("Inside the doOnlineShorfallXmlReport method of ReportsAction");
	 			 setLinks(request);
	 			 JasperReport jasperReport, xmljasperReport, emptyReport;
	 			 JasperPrint jasperPrint;
	 			 TTKReportDataSource ttkReportDataSource = null;
	 			 String shortfalltype=request.getParameter("shorfalltype");
	 			 String shortfallTemplateType="";
	 			 String strParam = "";
	 			 String strReportID="";
	 			 String jrxmlfile="";
	 			 String strParameterValue = request.getParameter("param");
	 			 
	 			 if(strParameterValue.contains("|PAT|"))
	 			 {
	 				 if(shortfalltype.equals("Medical"))
	 				 {
	 					 jrxmlfile="generalreports/ShortfallMedDoc.jrxml";
	 					 strReportID="ShortfallMid";				 
	 				 }//end of if(shortfalltype.equals("Medical"))
	 				 else if(shortfalltype.equals("Insurance"))
	 				 {
	 					 jrxmlfile="generalreports/ShortfallInsDoc.jrxml";
	 					 strReportID="ShortfallIns";
	 				 }//end of else if(shortfalltype.equals("Insurance"))
	 				 strParam="|PAT";
	 			 }//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
	 			 else if(strParameterValue.contains("|CLM|"))
	 			 {
	 				 if(shortfalltype.equals("Medical"))
	 				 {
	 					 jrxmlfile="generalreports/ShortfallDocumentMed.jrxml";
	 				 }//end of if(shortfalltype.equals("Medical"))
	 				 else if(shortfalltype.equals("Insurance"))
	 				 {
	 					 jrxmlfile="generalreports/ShortfallDocumentIns.jrxml";
	 				 }//end of else if(shortfalltype.equals("Insurance"))
	 				 else if(shortfalltype.equals("Missing Documents"))
	 				 {
	 					 jrxmlfile="generalreports/ShortfallDocument.jrxml";
	 				 }//end of else if(shortfalltype.equals("Missing Documents"))
	 				else if(shortfalltype.equalsIgnoreCase("Addressing Documents"))
	 					{
	 					shortfallTemplateType=request.getParameter("templateType");
	 						if(shortfallTemplateType.equalsIgnoreCase("NITN"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentNITN.jrxml";
	 						}
	 						else if(shortfallTemplateType.equalsIgnoreCase("DITN"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentDITN.jrxml";
	 						}
	 						else if(shortfallTemplateType.equalsIgnoreCase("NIDS"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentNIDS.jrxml";
	 						}
	 						else if(shortfallTemplateType.equalsIgnoreCase("DISO"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentDISO.jrxml";
	 						}
	 						else if(shortfallTemplateType.equalsIgnoreCase("NIOS"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentNIOS.jrxml";
	 						}
	 						else if(shortfallTemplateType.equalsIgnoreCase("DIOS"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentDIOS.jrxml";
	 						}
	 						else if(shortfallTemplateType.equalsIgnoreCase("NDSO"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentNDSO.jrxml";
	 						}
	 						else if(shortfallTemplateType.equalsIgnoreCase("DIDS"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentDIDS.jrxml";
	 						}
	 						else if(shortfallTemplateType.equalsIgnoreCase("DSCL"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentDSCL.jrxml";
	 						}	
	 						else if(shortfallTemplateType.equalsIgnoreCase("DSCS"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentDSCS.jrxml";
	 						}	
	 						else if(shortfallTemplateType.equalsIgnoreCase("DSFO"))
	 						{
	 							jrxmlfile="generalreports/ShortfallDocumentDSFO.jrxml";
	 						}	
	 					} 
	 				 if(shortfalltype.equalsIgnoreCase("Addressing Documents"))
	 				 {
	 					 strReportID="AddressingShortfall";
	 				 }
	 				 else{
	 					 strReportID="Shortfall";
	 				 }
	 				
	 				 strParam="|CLM";
	 			 }//end of else if(TTKCommon.getActiveLink(request).equals("Claims"))
	 			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
	 			 try
	 			 {
	 				 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
	 				 xmljasperReport = JasperCompileManager.compileReport("generalreports/ShortfallQuestions.jrxml");
	 				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
	 				 hashMap.put("param", strParam);
	 				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
	 				 ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("parameter"));
	 				 org.w3c.dom.Document document = null;
	 				 if(ttkReportDataSource.getResultData()!=null && ttkReportDataSource.getResultData().next())
	 				 {
	 					 String strQuery = ttkReportDataSource.getResultData().getString("questions");
	 					 if(strQuery != null)
	 					 {
	 						 document = JRXmlUtils.parse(new ByteArrayInputStream(strQuery.getBytes()));
	 						 hashMap.put("MyDataSource",new JRXmlDataSource(document,"//query"));
	 						 JasperFillManager.fillReport(xmljasperReport, hashMap, new JRXmlDataSource(document,"//query"));
	 						 hashMap.put("shortfalltest",xmljasperReport);
	 					 }//end of if(strQuery == null || strQuery.trim().length() == 0)
	 				 }//end of if(ttkReportDataSource.getResultData()!=null && ttkReportDataSource.getResultData().next())
	 				 String strQuery = ttkReportDataSource.getResultData().getString("questions");
	 				 if (strQuery == null || strQuery.trim().length() == 0)
	 				 {
	 					 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
	 				 }//end of if (strQuery == null || strQuery.trim().length() == 0)
	 				 else
	 				 {
	 					 ttkReportDataSource.getResultData().beforeFirst();
	 					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap, ttkReportDataSource);
	 				 }//end of else
	 				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
	 				 request.setAttribute("boas",boas);
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
	 			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
	 		 }//end of catch(Exception exp)
	 	 }//end of doOnlineShorfallXmlReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
  /*
   * Provider Login Reports
   */
  
  /**When the user clicks on the Download button in print Cheques - provider Login
	  * This method is used to generate the report .
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doDownLoadChequeReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the doDownLoadChequeReport method of ReportsAction");
			 setOnlineLinks(request);
			 JasperReport jasperReport;
			 JasperPrint jasperPrint;
			 String strParam = request.getParameter("chequeNo");			 
			 TTKReportDataSource ttkReportDataSource = null;
			 ResultSet rs = null;
			 String jrxmlfile= null;		 
			 ttkReportDataSource = new TTKReportDataSource("chequeWiseReport",strParam);
			 rs = ttkReportDataSource.getResultData();			 
			 try
			 {
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				  if(rs.next()){	
					 String strTemplateName = rs.getString("CHECK_NUM");
					 log.debug("Template Name is " + strTemplateName);
					 if(strTemplateName!=null)
					 {
						 jrxmlfile = "providerLogin/ChequeWiseReport.jrxml";
					 }//end of if(strTemplateName!=null)
					 rs.previous();
					 File f = new File(jrxmlfile);
					 if(f.exists()){
						 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new TTKReportDataSource(rs));
					 }//end of  if(f.exists())   compiling the template file
					 else {//template does not exists
						 jasperReport = JasperCompileManager.compileReport("generalreports/tna.jrxml");
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new JREmptyDataSource());
					 }//end of else					 
				 }//if(rs.next())
				 else {
					 jrxmlfile = "generalreports/EmptyReprot.jrxml";
					 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());
				 }//end of else
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 
				 String destFileName	=	TTKPropertiesReader.getPropertyValue("downloadHistoryDir")+"ChequeWiseReport-"+
		 					new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+".pdf";
				 JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
				 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				 OnlineAccessManager onlineAccessManagerObject= this.getOnlineAccessManagerObject();
				 int iRows = onlineAccessManagerObject.updateDownloadHistory(userSecurityProfile.getEmpanelNumber(),destFileName,"",userSecurityProfile.getUSER_SEQ_ID(),"");
				 
				 
				 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doDownLoadChequeReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 
	  /*
		  * Batch Reconciliation Report
		  */
		 
	 
	 public ActionForward doDownLoadInvoiceReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the doDownLoadInvoiceReport method of ReportsAction");
			 setOnlineLinks(request);
			 JasperReport jasperReport;
			 JasperPrint jasperPrint;
			 String strParam = request.getParameter("invNO");			 
			 TTKReportDataSource ttkReportDataSource = null;
			UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
			 ResultSet rs = null;
			 String jrxmlfile= null;
			 StringBuffer	emanelNo	=	new StringBuffer(userSecurityProfile.getEmpanelNumber());
			 ttkReportDataSource = new TTKReportDataSource("InvoiceWiseReport",strParam, emanelNo);
			 rs = ttkReportDataSource.getResultData();			 
			 try
			 {
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				  if(rs.next()){	
					 String strTemplateName = rs.getString("INVOICE_NUMBER");
					 log.debug("Template Name is " + strTemplateName);
					 if(strTemplateName!=null)
					 {
						 jrxmlfile = "providerLogin/InvoiceWiseReport.jrxml";
					 }//end of if(strTemplateName!=null)
					 rs.previous();
					 File f = new File(jrxmlfile);
					 if(f.exists()){
						 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new TTKReportDataSource(rs));
					 }//end of  if(f.exists())   compiling the template file
					 else {//template does not exists
						 jasperReport = JasperCompileManager.compileReport("generalreports/tna.jrxml");
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new JREmptyDataSource());
					 }//end of else					 
				 }//if(rs.next())
				 else {
					 jrxmlfile = "generalreports/EmptyReprot.jrxml";
					 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());
				 }//end of else
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 
				 String destFileName	=	TTKPropertiesReader.getPropertyValue("downloadHistoryDir")+"InvoiceWiseReport-"+
		 					new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+".pdf";
				 JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
				 OnlineAccessManager onlineAccessManagerObject= this.getOnlineAccessManagerObject();
				 int iRows = onlineAccessManagerObject.updateDownloadHistory(userSecurityProfile.getEmpanelNumber(),destFileName,"",userSecurityProfile.getUSER_SEQ_ID(),"");
				 
				 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doDownLoadInvoiceReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 
	 
  /*
	  * Batch Reconciliation Report
	  */
	 
	 public ActionForward doDownLoadBatchReconciliationReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the doDownLoadBatchReconciliationReport method of ReportsAction");
			 setOnlineLinks(request);
			 JasperReport jasperReport;
			 JasperPrint jasperPrint;
			 String batchSeqId = request.getParameter("batchSeqId");
			 String flag 	= request.getParameter("flag");
			 String empnlNo = request.getParameter("empnlNo");			 
			 TTKReportDataSource ttkReportDataSource = null;
			 ResultSet rs = null;
			 String jrxmlfile= null;		 
			 ttkReportDataSource = new TTKReportDataSource("batchReconciliation",batchSeqId,flag,empnlNo);
//			 ttkReportDataSource = new TTKReportDataSource("batchReconciliation","611","TTL","UAE-DXB-CPC-0031");
			 rs = ttkReportDataSource.getResultData();			 
			 try
			 {
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				  if(rs.next()){	
					 String strTemplateName = rs.getString("CLM_RECEIVED_DATE");
					 log.info("Template Name is " + strTemplateName);
					 if(strTemplateName!=null)
					 {
						 jrxmlfile = "providerLogin/BatchReconciliation.jrxml";
					 }//end of if(strTemplateName!=null)
					 rs.previous();
					 File f = new File(jrxmlfile);
					 if(f.exists()){
						 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new TTKReportDataSource(rs));
					 }//end of  if(f.exists())   compiling the template file
					 else {//template does not exists
						 jasperReport = JasperCompileManager.compileReport("generalreports/tna.jrxml");
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new JREmptyDataSource());
					 }//end of else					 
				 }//if(rs.next())
				 else {
					 jrxmlfile = "generalreports/EmptyReprot.jrxml";
					 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());
				 }//end of else
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 
				 String destFileName	=	TTKPropertiesReader.getPropertyValue("downloadHistoryDir")+"BatchReconciliation-"+
		 					new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+".pdf";
				 JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
				 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				 OnlineAccessManager onlineAccessManagerObject= this.getOnlineAccessManagerObject();
				 int iRows = onlineAccessManagerObject.updateDownloadHistory(userSecurityProfile.getEmpanelNumber(),destFileName,"",userSecurityProfile.getUSER_SEQ_ID(),"");
				 
				 
				 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doDownLoadBatchReconciliationReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 
	 
	 
	 
	 /*
	  * Batch Invoice Report
	  */
	 
	 public ActionForward doDownLoadBatchInvoicenReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the doDownLoadBatchInvoicenReport method of ReportsAction");
			 setOnlineLinks(request);
			 JasperReport jasperReport;
			 JasperPrint jasperPrint;
			 String invNo = request.getParameter("invNo");
			 String empnlNo = request.getParameter("empnlNo");			 
			 TTKReportDataSource ttkReportDataSource = null;
			 ResultSet rs = null;
			 String jrxmlfile= null;		 
			 ArrayList<String> alCallPendList	=	new ArrayList<>();
			 alCallPendList.add(empnlNo);
			 alCallPendList.add(invNo);
			 ttkReportDataSource = new TTKReportDataSource("batchInvoice",alCallPendList);
//			 ttkReportDataSource = new TTKReportDataSource("batchReconciliation","611","TTL","UAE-DXB-CPC-0031");
			 rs = ttkReportDataSource.getResultData();			 
			 try
			 {
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				  if(rs.next()){	
					 String strTemplateName = rs.getString("INS_COMP_NAME");
					 log.info("Template Name is " + strTemplateName);
					 if(strTemplateName!=null)
					 {
						 jrxmlfile = "providerLogin/BatchInvoiceReport.jrxml";
					 }//end of if(strTemplateName!=null)
					 rs.previous();
					 File f = new File(jrxmlfile);
					 if(f.exists()){
						 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new TTKReportDataSource(rs));
					 }//end of  if(f.exists())   compiling the template file
					 else {//template does not exists
						 jasperReport = JasperCompileManager.compileReport("generalreports/tna.jrxml");
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new JREmptyDataSource());
					 }//end of else					 
				 }//if(rs.next())
				 else {
					 jrxmlfile = "generalreports/EmptyReprot.jrxml";
					 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());
				 }//end of else
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 
				 String destFileName	=	TTKPropertiesReader.getPropertyValue("downloadHistoryDir")+"BatchInvoiceReport-"+
		 					new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+".pdf";
				 JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
				 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				 OnlineAccessManager onlineAccessManagerObject= this.getOnlineAccessManagerObject();
				 int iRows = onlineAccessManagerObject.updateDownloadHistory(userSecurityProfile.getEmpanelNumber(),destFileName,"",userSecurityProfile.getUSER_SEQ_ID(),"");

				 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doDownLoadBatchInvoicenReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 
	 
	 /*
	  * Provider Login Short Fall Reports - PreAuth Log
	  */
	 
	 public ActionForward doShowPreAuthShortfall(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) 
			 throws Exception{
		
		 try{
			 log.debug("Inside the doShowPreAuthShortfall method of ReportsAction");
			 	ByteArrayOutputStream baos=null;
	    		OutputStream sos = null;
	    		FileInputStream fis = null;
			 	response.setContentType("application/octet-stream");
		 		String fileName = request.getParameter("fileName");
		 		String reportType		=	request.getParameter("reportType");
		 		String strFile = "";
		 		String[] strFileName=null;
		 		String strOSName= System.getProperty("os.name");
		 		
		 		int indexDirNo	=	0;
	 		if("recentReports".equals(reportType))
	 		{
	 			strFile	=	TTKPropertiesReader.getPropertyValue("downloadHistoryDir")+fileName;
	 			indexDirNo	=	Integer.parseInt(TTKPropertiesReader.getPropertyValue("downloadHistoryDirNo"));
	 		}else{
	 			strFile	=	TTKPropertiesReader.getPropertyValue("shortfallrptdir")+fileName+".pdf";
	 			indexDirNo	=	Integer.parseInt(TTKPropertiesReader.getPropertyValue("shortfallrptdirNo"));
	 		}//else - For ShortFalls
		//This part is common for all Downloads
 		if(strOSName.contains("Windows"))
	    {
	      strFile.replaceAll("\\\\","\\\\\\\\");
	    }//end of if(strOSName.contains("Windows"))
	    if(strOSName.contains("Windows")){
	     strFileName=strFile.split("\\\\");
	    }//end of if(strOSName.contains("Windows"))
	    else
	    {
	     strFileName=strFile.split("/");
	    }//end of else
	    if(strFile.endsWith("pdf"))
 		{
 			response.setContentType("application/pdf");
 			response.addHeader("Content-Disposition","attachment; filename="+strFileName[indexDirNo]);
 		}//end of else if(strFile.endsWith("pdf"))
 		File f = new File(strFile);
		if(f.isFile() && f.exists()){
			fis = new FileInputStream(f);
		}//end of if(strFile !="")
		else
		{
			fis = new FileInputStream(TTKPropertiesReader.getPropertyValue("WebLoginDocs")+"/default/"+"Test.doc");
		}//end of else
		BufferedInputStream bis = new BufferedInputStream(fis);
		baos=new ByteArrayOutputStream();
		int ch;
		while ((ch = bis.read()) != -1)
		{
			baos.write(ch);
		}//end of while ((ch = bis.read()) != -1)
		sos = response.getOutputStream();
		baos.writeTo(sos);
		bis.close();
		baos.close();
		fis.close();
// 		 return (mapping.findForward(strReportdisplay));
		return null;//returning null - need to show the downloaded file
 		}//end of try
		 catch(Exception exp)
		 {
			System.out.println("eerror::"+exp);
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doShowPreAuthShortfall(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 
	 /*
	  * Provider Login Generate Letters - PreAuth Log
	  */
	 
	 public ActionForward doGeneratePreAuthLetter(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the doGeneratePreAuthLetter method of OnlineReportsAction");
			 setOnlineLinks(request);
			 JasperReport jasperReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null;
			 ResultSet rs = null;
			 String jrxmlfile= null;		 
			 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			 String patAuthSeqId	=	(String) request.getSession().getAttribute("patAuthSeqId").toString();
			 String patientCardId	=	(String) request.getSession().getAttribute("patientCardId").toString();
			 ttkReportDataSource = new TTKReportDataSource("generatePreAuthLetter",userSecurityProfile.getEmpanelNumber().toString(),patAuthSeqId,patientCardId);
			 rs = ttkReportDataSource.getResultData();			 
			 try
			 {
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				  if(rs.next()){
					 String strTemplateName = rs.getString("PREAUTH_TYPE");
					 log.debug("Template Name is " + strTemplateName);
					 if(strTemplateName!=null)
					 {
						 jrxmlfile = "providerLogin/PreAuthGenerateLetter.jrxml";
					 }//end of if(strTemplateName!=null)
					 rs.previous();
					 File f = new File(jrxmlfile);
					 if(f.exists()){
						 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new TTKReportDataSource(rs));
					 }//end of  if(f.exists())   compiling the template file
					 else {//template does not exists
						 jasperReport = JasperCompileManager.compileReport("generalreports/tna.jrxml");
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new JREmptyDataSource());
					 }//end of else					 
				 }//if(rs.next())
				 else {
					 jrxmlfile = "generalreports/EmptyReprot.jrxml";
					 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());
				 }//end of else
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doGeneratePreAuthLetter(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 
	 

	 /*
	  * Provider Login  - Finance DashBoard
	  */
	 
	 public ActionForward doDownLoadFinanceDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the doDownLoadFinanceDashBoard method of OnlineReportsAction");
			 setOnlineLinks(request);
			 JasperReport jasperReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null;
			 ResultSet rs = null;
			 String jrxmlfile= null;		 
			 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			 ttkReportDataSource = new TTKReportDataSource("financeDashBoard",userSecurityProfile.getEmpanelNumber(),request.getParameter("fromDate"),request.getParameter("toDate"));
			 rs = ttkReportDataSource.getResultData();			 
			 try
			 {
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				  if(rs.next()){
					 String strTemplateName = rs.getString("OPENING_AMT");
					 log.info("Template Name is " + strTemplateName);
					 if(strTemplateName!=null)
					 {
						 jrxmlfile = "providerLogin/FinanceDashBoard.jrxml";
					 }//end of if(strTemplateName!=null)+
					 else{
						 jrxmlfile = "providerLogin/EmptyReprot.jrxml";
					 }
					 rs.previous();
					 File f = new File(jrxmlfile);
					 if(f.exists()){
						 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new TTKReportDataSource(rs));
					 }//end of if(f.exists())   compiling the template file
					 else {//template does not exists
						 jasperReport = JasperCompileManager.compileReport("generalreports/tna.jrxml");
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new JREmptyDataSource());
					 }//end of else					 
				 }//if(rs.next())
				 else {
					 jrxmlfile = "generalreports/EmptyReprot.jrxml";
					 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());
				 }//end of else
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 String destFileName	=	TTKPropertiesReader.getPropertyValue("downloadHistoryDir")+"FinanceDashboard-"+
						 					new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+".pdf";
				 JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
				 
				 OnlineAccessManager onlineAccessManagerObject= this.getOnlineAccessManagerObject();
				 int iRows = onlineAccessManagerObject.updateDownloadHistory(userSecurityProfile.getEmpanelNumber(),destFileName,"",userSecurityProfile.getUSER_SEQ_ID(),"");
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doDownLoadFinanceDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 
	 
	 
	 /*
	  * Provider Login  - OverDue Report
	  */
	 
	 public ActionForward doDownLoadIOverdueReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the doDownLoadIOverdueReport method of OnlineReportsAction");
			 setOnlineLinks(request);
			 JasperReport jasperReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null;
			 ResultSet rs = null;
			 String jrxmlfile= null;		 
			 
			 String fromDate=	request.getParameter("fromDate");
			 String toDate	=	request.getParameter("toDate");
			 String invNo	=	request.getParameter("invNo");
			 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			 ttkReportDataSource = new TTKReportDataSource("overdueReport",userSecurityProfile.getEmpanelNumber(),fromDate,toDate,invNo);
			 rs = ttkReportDataSource.getResultData();			 
			 try
			 {
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				  if(rs.next()){
						 jrxmlfile = "providerLogin/OverdueReport.jrxml";
					 rs.previous();
					 File f = new File(jrxmlfile);
					 if(f.exists()){
						 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new TTKReportDataSource(rs));
					 }//end of if(f.exists())   compiling the template file
					 else {//template does not exists
						 jasperReport = JasperCompileManager.compileReport("generalreports/tna.jrxml");
						 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new JREmptyDataSource());
					 }//end of else					 
				 }//if(rs.next())
				 else {
					 jrxmlfile = "generalreports/EmptyReprot.jrxml";
					 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());
				 }//end of else
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 
				 String destFileName	=	TTKPropertiesReader.getPropertyValue("downloadHistoryDir")+"OverdueReport-"+
						 					new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+".pdf";
				 JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
				 OnlineAccessManager onlineAccessManagerObject= this.getOnlineAccessManagerObject();
				 int iRows = onlineAccessManagerObject.updateDownloadHistory(userSecurityProfile.getEmpanelNumber(),destFileName,"",userSecurityProfile.getUSER_SEQ_ID(),"");
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doDownLoadIOverdueReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 
	 
	 
	 
	 public ActionForward doViewCommonForAll(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		log.debug("inside Reports Action doViewCommonForAll method");
	    		setLinks(request);
	    		ByteArrayOutputStream baos=null;
	    		OutputStream sos = null;
	    		FileInputStream fis = null;
	    		response.setContentType("application/octet-stream");
	    		String strModule = request.getParameter("module");
	    		String fileName = request.getParameter("fileName");
	    		String strFile = "";
	    		String[] strFileName=null;
	    		String strOSName= System.getProperty("os.name");
	    		log.info("strModule-------"+strModule);
	    		 if("preAuthorizationFile".equals(strModule))
	    		{
 			
 			String rownum	=	(String)request.getParameter("rownum");
	    		
 			PreAuthSearchVO authSearchVO	=	null;
				TableData tableData = TTKCommon.getTableData(request);
				authSearchVO = (PreAuthSearchVO)tableData.getRowInfo(Integer.parseInt(rownum));
	    		
 			strFile	=	TTKPropertiesReader.getPropertyValue("authorizationrptdir")+authSearchVO.getApprovalNo()+".pdf";
	    		    if(strOSName.contains("Windows"))
	    		    {
	    		      strFile.replaceAll("\\\\","\\\\\\\\");
	    		    }//end of if(strOSName.contains("Windows"))
	    		    if(strOSName.contains("Windows")){
	    		     strFileName=strFile.split("\\\\");
	    		    }//end of if(strOSName.contains("Windows"))
	    		    else
	    		    {
	    		     strFileName=strFile.split("/");
	    		    }//end of else
	    		 if(strFile.endsWith("pdf"))
	    		{
	    			// System.out.println("strFileName==========="+strFileName.length);
	    			response.setContentType("application/pdf");
	    			response.addHeader("Content-Disposition","attachment; filename="+strFileName[0]);
	    		}//end of else if(strFile.endsWith("pdf"))
 		File f = new File(strFile);
 		if(f.isFile() && f.exists()){
 			fis = new FileInputStream(f);
 		}//end of if(strFile !="")
 		else
 		{
 			response.setContentType("application/msword");
 			response.addHeader("Content-Disposition","attachment; filename=Test.docx");
 			fis = new FileInputStream("E:\\home\\tipsint\\jboss-as-7.1.3.Final\\bin"+TTKPropertiesReader.getPropertyValue("WebLoginDocs")+"\\default\\"+"Test.docx");
 		}//end of else
		}
		 else if("preAuthorizationUploadedFile".equals(strModule))
    		{
    			
    			String rownum	=	(String)request.getParameter("rownum");
	    		
    			strFile	=	TTKPropertiesReader.getPropertyValue("tariffAdminUpload")+rownum;
    		    if(strOSName.contains("Windows"))
    		    {
    		      strFile.replaceAll("\\\\","\\\\\\\\");
    		    }//end of if(strOSName.contains("Windows"))
    		    if(strOSName.contains("Windows")){
    		     strFileName=strFile.split("\\\\");
    		    }//end of if(strOSName.contains("Windows"))
    		    else
    		    {
    		     strFileName=strFile.split("/");
    		    }//end of else
    		if(strFile.endsWith("doc") || strFile.endsWith("docx"))
    		{
    			response.setContentType("application/msword");
    			response.addHeader("Content-Disposition","attachment; filename="+strFileName[Integer.parseInt(TTKPropertiesReader.getPropertyValue("TariffFormatDirno"))]);
    		}//end of if(strFile.endsWith("doc"))
    		else if(strFile.endsWith("pdf"))
    		{
    			response.setContentType("application/pdf");
    			response.addHeader("Content-Disposition","attachment; filename="+strFileName[Integer.parseInt(TTKPropertiesReader.getPropertyValue("TariffFormatDirno"))]);
    		}//end of else if(strFile.endsWith("pdf"))
    		else if(strFile.endsWith("xls") || strFile.endsWith("xlsx"))
    		{
    			response.setContentType("application/vnd.ms-excel");
    			response.addHeader("Content-Disposition","attachment; filename="+strFileName[Integer.parseInt(TTKPropertiesReader.getPropertyValue("TariffFormatDirno"))]);
    		}//end of else if(strFile.endsWith("xls"))
    		else if(strFile.endsWith("txt")){
		    		response.setContentType("text/plain");
		    		response.setHeader("Content-Disposition","attachment;filename"+strFileName[Integer.parseInt(TTKPropertiesReader.getPropertyValue("TariffFormatDirno"))]);
    		}
    		File f = new File(strFile);
    		if(f.isFile() && f.exists()){
    			fis = new FileInputStream(f);
    		}//end of if(strFile !="")
    		else
    		{
    			fis = new FileInputStream(TTKPropertiesReader.getPropertyValue("WebLoginDocs")+"/default/"+"Test.doc");
    		}//end of else
		}
	    		 
	    		 
		BufferedInputStream bis = new BufferedInputStream(fis);
		baos=new ByteArrayOutputStream();
		int ch;
		while ((ch = bis.read()) != -1)
		{
			baos.write(ch);
		}//end of while ((ch = bis.read()) != -1)
		sos = response.getOutputStream();
		baos.writeTo(sos);
		bis.close();
		baos.close();
		fis.close();
	}//end of try
	catch(TTKException expTTK)
	 {
		 return this.processExceptions(request, mapping, expTTK);
	 }//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
	}//end of catch(Exception exp)
	return null;
}//end of doViewCommonForAll(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    

	 
	 
 }//end of OnlineReportsAction