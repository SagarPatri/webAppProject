/**
 * @ (#) IBMReportsAction.java Nov2, 2012
 * Project      : TTK HealthCare Services
 * File         : IBMRportsAction.java
 * Author       : PRAVEEN
 * Company      : RCS
 * Date Created : October 17, 2012
 *
 * @author       : PRAVEEN
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.reports;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
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
import org.apache.poi.hssf.usermodel.*;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import java.io.FileOutputStream;
import com.ttk.common.TTKPropertiesReader;
public class IBMReportsAction extends TTKAction
{

	private static Logger log = Logger.getLogger( IBMReportsAction.class );

	//declaration of forward paths
	private static final String strIBMRptDetails="ibmrptdetails";
	private static final String strIBMAdditioncutoffMaxRecRptDetails="ibmadditioncutoffmaxrecrptdetails";

	private static final String strIBMPreauthRptDetails="ibmpreauthrptdetails";
	private static final String strIBMReoptinRptDetails="ibmreoptinrptdetails";
	private static final String strIBMDailyRptDetails="ibmdailyrptdetails";
	private static final String strIBMReconRptDetails="ibmreconrptdetails";
	private static final String strIBMChildBornRptDetails="ibmchildbornrptdetails";
	private static final String strIBMNewDeletionRptDetails="ibmnewdeletionrptdetails";

	private static final String strCorporateRpts="corporaterpt";
	private static final String strSelectGroup="selectgroup";
	private static final String strReportdisplay="reportdisplay";

	private static final String strIBMReportdisplay="ibmreportdisplay";
	private static final String strReoptinReportdisplay="ibmreoptinreportdisplay";
	private static final String strDailyReportdisplay="ibmdailyreportdisplay";
	private static final String strReconReportdisplay="ibmreconreportdisplay";

	//Exception Message Identifier
	private static final String strReportExp="report";
	private static final String strWebConfig="webconfig";
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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		try
		{
			//log.debug("Inside the Default method of IBMReportsAction");
			setLinks(request);
			DynaActionForm frmReportList= (DynaActionForm) form;
			//log.debug("form is " + form);
			frmReportList.initialize(mapping);
			return this.getForward(strIBMRptDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		public ActionForward doIBMAdditioncutoffMaxRec(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception
		{
			try
			{
				//log.debug("Inside the Default method of doIBMAdditioncutoffMaxRec");
				setLinks(request);
				DynaActionForm frmReportList= (DynaActionForm) form;
				//log.debug("form is " + form);
				frmReportList.initialize(mapping);
				return this.getForward(strIBMAdditioncutoffMaxRecRptDetails,mapping,request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			}//end of catch(Exception exp)
		}//end of doIBMAdditioncutoffMaxRec(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
		public ActionForward doIBMPreauthDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception
		{
			try
			{
				//log.debug("Inside the Default method of doIBMAdditioncutoffMaxRec");
				setLinks(request);
				DynaActionForm frmReportList= (DynaActionForm) form;
				//log.debug("form is " + form);
				frmReportList.initialize(mapping);
				return this.getForward(strIBMPreauthRptDetails,mapping,request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			}//end of catch(Exception exp)
		}//end of doIBMPreauthDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		public ActionForward doIBMReoptinDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception
		{
			try
			{
				//log.debug("Inside the Default method of doIBMAdditioncutoffMaxRec");
				setLinks(request);
				DynaActionForm frmReportList= (DynaActionForm) form;
				//log.debug("form is " + form);
				frmReportList.initialize(mapping);
				return this.getForward(strIBMReoptinRptDetails,mapping,request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			}//end of catch(Exception exp)
		}//end of doIBMReoptinDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		public ActionForward doIBMDailyReportDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception
		{
			try
			{
				//log.debug("Inside the Default method of doIBMAdditioncutoffMaxRec");
				setLinks(request);
				DynaActionForm frmReportList= (DynaActionForm) form;
				//log.debug("form is " + form);
				frmReportList.initialize(mapping);
				return this.getForward(strIBMDailyRptDetails,mapping,request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			}//end of catch(Exception exp)
		}//end of doIBMDailyReportDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		public ActionForward doIBMReconReportDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception
		{
			try
			{
				//log.debug("Inside the Default method of doIBMAdditioncutoffMaxRec");
				setLinks(request);
				DynaActionForm frmReportList= (DynaActionForm) form;
				//log.debug("form is " + form);
				frmReportList.initialize(mapping);
				return this.getForward(strIBMReconRptDetails,mapping,request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			}//end of catch(Exception exp)
		}//end of doIBMReconReportDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		public ActionForward doIBMChildBornReportDetail (ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception
		{
			try
			{
				//log.debug("Inside the Default method of doIBMAdditioncutoffMaxRec");
				setLinks(request);
				DynaActionForm frmReportList= (DynaActionForm) form;
				//log.debug("form is " + form);
				frmReportList.initialize(mapping);
				return this.getForward(strIBMChildBornRptDetails,mapping,request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			}//end of catch(Exception exp)
		}//end of doIBMReconReportDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		public ActionForward doIBMNewDeletionReportDetail (ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception
		{
			try
			{
				//log.debug("Inside the Default method of doIBMAdditioncutoffMaxRec");
				setLinks(request);
				DynaActionForm frmReportList= (DynaActionForm) form;
				//log.debug("form is " + form);
				frmReportList.initialize(mapping);
				return this.getForward(strIBMNewDeletionRptDetails,mapping,request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			}//end of catch(Exception exp)
		}//end of doIBMReconReportDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



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
			 public ActionForward doGenerateIbmRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
				 try{
					 //log.debug("Inside the doGenerateIOBRpt method of doGenerateIbmRpt");
					 setLinks(request);
					 JasperReport jasperReport,emptyReport;
					 JasperPrint jasperPrint;
					 TTKReportDataSource ttkReportDataSource = null;
					 String jrxmlfile="", strReportID = "" ;
					 String strGroupId = request.getParameter("parameter1");
					 String strFrom =  request.getParameter("parameter2");
					 String strTo = request.getParameter("parameter3");
					 strReportID = request.getParameter("reportID");
					 String strReportType = request.getParameter("reportType");
					 jrxmlfile = request.getParameter("fileName");
					 //modified by Praveen for passing 3 parameters for the Reportdatasource.
					 ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("parameter1"),request.getParameter("parameter2"),request.getParameter("parameter3"));
					 HSSFWorkbook wb = new HSSFWorkbook();
					 HSSFSheet sheet = wb.createSheet("Deletioncutoff");
					 HSSFRow rowhead = sheet.createRow(0);
					 ResultSetMetaData metaData = ttkReportDataSource.getResultData().getMetaData();
			 		 int rowCount = metaData.getColumnCount();
			 		 for(int i=0;i<rowCount;i++)
			 		 {
			 			rowhead.createCell((short)i).setCellValue(new HSSFRichTextString(metaData.getColumnName(i+1)));
			 		 }
					int index = 1;
					while(ttkReportDataSource.getResultData().next())
				  	{
						HSSFRow row = sheet.createRow(index);
				  		for(int i=0;i<rowCount;i++)
				 		{
				  			
				  			if(metaData.getColumnTypeName(i+1).equalsIgnoreCase("Number"))
				  			{
				  				row.createCell((short)i).setCellValue(ttkReportDataSource.getResultData().getDouble(i+1));
				  			}
				  			else
				  			{
				  				row.createCell((short)i).setCellValue(new HSSFRichTextString(ttkReportDataSource.getResultData().getString(i+1)));
				  			}

				 		}
				  		index++;
				    }
				  	
			  	    String File = "Deletioncutoff";
			  	    request.setAttribute("File",File);
					String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xlsx";
					request.setAttribute("FileDest",fileDest);
					
					FileOutputStream fileOut = new FileOutputStream(fileDest);
					wb.write(fileOut);
					fileOut.close();
					

			 	 }//end of try
				 catch(TTKException expTTK)
				 {
					 return this.processExceptions(request, mapping, expTTK);
				 }//end of catch(TTKException expTTK)
				 catch(Exception exp)
				 {
					 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
				 }//end of catch(Exception exp)
				 return (mapping.findForward(strIBMReportdisplay)); }//end of doGenerateIbmRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
				 public ActionForward doGenerateAdditioncutoffMaxRecRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
					 try{
						 //log.debug("Inside the doGenerateAdditioncutoffMaxRecRpt method of IBMReportAction");
						 setLinks(request);
						 JasperReport jasperReport,emptyReport;
						 JasperPrint jasperPrint;
						 TTKReportDataSource ttkReportDataSource = null;
						 String jrxmlfile, strReportID = "" ;

						 String strGroupId = request.getParameter("parameter1");
						 String strFrom =  request.getParameter("parameter2");
						 String strTo = request.getParameter("parameter3");
						 strReportID = request.getParameter("reportID");
						 //modified by Praveen for passing 3 parameters for the Reportdatasource.
						 ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("parameter1"),request.getParameter("parameter2"),request.getParameter("parameter3"));
						 //ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("parameter"));

						 HSSFWorkbook wb = new HSSFWorkbook();
						 HSSFSheet sheet = wb.createSheet("Additioncutoff");
						 HSSFRow rowhead = sheet.createRow(0);
						 ResultSetMetaData metaData = ttkReportDataSource.getResultData().getMetaData();
				 		 int rowCount = metaData.getColumnCount();
				 		 for(int i=0;i<rowCount;i++)
				 		 {
				 			rowhead.createCell((short)i).setCellValue(new HSSFRichTextString(metaData.getColumnName(i+1)));
				 		 }
						int index = 1;
						while(ttkReportDataSource.getResultData().next())
					  	{
							HSSFRow row = sheet.createRow(index);
					  		for(int i=0;i<rowCount;i++)
					 		{
					  			
					  			if(metaData.getColumnTypeName(i+1).equalsIgnoreCase("Number"))
					  			{
					  				row.createCell((short)i).setCellValue(ttkReportDataSource.getResultData().getDouble(i+1));
					  			}
					  			else
					  			{
					  				row.createCell((short)i).setCellValue(new HSSFRichTextString(ttkReportDataSource.getResultData().getString(i+1)));
					  			}

					 		}
					  		index++;
					    }
						String File = "Additioncutoff";
				  	    request.setAttribute("File",File);
						String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xlsx";
						request.setAttribute("FileDest",fileDest);
						
						FileOutputStream fileOut = new FileOutputStream(fileDest);
						wb.write(fileOut);
						fileOut.close();
						

						 /*SXSSFWorkbook wb = new SXSSFWorkbook();
							//Sheet sheet = wb.createSheet("Excel2007");
						Sheet sheet = wb.createSheet("Additioncutoff");*/

						//ResultSetMetaData metaData = ttkReportDataSource.getResultData().getMetaData();
			 			//int rowCount = metaData.getColumnCount();
			 			/*for(int i=0;i<rowCount;i++)
			 			{
			 					rowhead.createCell(i).setCellValue(metaData.getColumnName(i+1));
			 			}*/


					 }//end of try
					 catch(TTKException expTTK)
					 {
						 return this.processExceptions(request, mapping, expTTK);
					 }//end of catch(TTKException expTTK)
					 catch(Exception exp)
					 {
						 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
					 }//end of catch(Exception exp)*/
					 return(mapping.findForward(strIBMReportdisplay));
			 }//end of doGenerateDailyCancellationRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			  public ActionForward doGeneratePreauthReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			                                         HttpServletResponse response) throws TTKException{
			  	try{
			  		setOnlineLinks(request);
			  		JasperReport jasperReport,emptyReport;
			  		JasperPrint jasperPrint;
			  		TTKReportDataSource ttkReportsDataSource = null;
			  		TTKReportDataSource ttkReportsDataSource1 = null;
			  		String strReportID = request.getParameter("reportID");
					ttkReportsDataSource = new TTKReportDataSource(strReportID,request.getParameter("Polgrpseqid"),request.getParameter("sFrom"),request.getParameter("sTo"),request.getParameter("status"));
					/*
					
					
					
					SXSSFWorkbook wb = new SXSSFWorkbook();
					Sheet sheet = wb.createSheet("PreauthDump");
					Row rowhead = sheet.createRow(0);*/

					HSSFWorkbook wb = new HSSFWorkbook();
					HSSFSheet sheet = wb.createSheet("PreauthDump");
					HSSFRow rowhead = sheet.createRow(0);

		       		ResultSetMetaData metaData = ttkReportsDataSource.getResultData().getMetaData();
	 				int rowCount = metaData.getColumnCount();
	 				for(int i=0;i<rowCount;i++)
	 				{
	 					rowhead.createCell((short)i).setCellValue(new HSSFRichTextString(metaData.getColumnName(i+1)));
	 				}
	 				int index = 1;
			  	    while(ttkReportsDataSource.getResultData().next())
			  	    {
			  	    	//Row row = sheet.createRow(index);
			  	    	HSSFRow row = sheet.createRow(index);
			  	    	for(int i=0;i<rowCount;i++)
			 			{
			  	    		if(metaData.getColumnTypeName(i+1).equalsIgnoreCase("Number"))
			  	    		{
			  	    			row.createCell((short)i).setCellValue(ttkReportsDataSource.getResultData().getDouble(i+1));
			  	    		}
			  	    		else
			  	    		{
			  	    			row.createCell((short)i).setCellValue(new HSSFRichTextString(ttkReportsDataSource.getResultData().getString(i+1)));
			  	    		}

			 			}
			  	    	index++;
			  	    }
			  	    String strReportID1 = request.getParameter("reportID1");
					
			  	    ttkReportsDataSource1 = new TTKReportDataSource(strReportID1,request.getParameter("Polgrpseqid"),request.getParameter("sFrom"),request.getParameter("sTo"),request.getParameter("status"));
					
					
					
					
					

					HSSFSheet sheet1 = wb.createSheet("ClaimDump");
					HSSFRow rowhead1 = sheet1.createRow(0);
			  	    ResultSetMetaData metaData1 = ttkReportsDataSource1.getResultData().getMetaData();
	 				int rowCount1 = metaData1.getColumnCount();
	 				for(int j=0;j<rowCount1;j++)
	 				{
	 					rowhead1.createCell((short)j).setCellValue(new HSSFRichTextString(metaData1.getColumnName(j+1)));
	 				}
	 				int index1 = 1;
	 				while(ttkReportsDataSource1.getResultData().next())
			  	    {
	 					HSSFRow row1 = sheet1.createRow(index1);
	 					for(int j=0;j<rowCount1;j++)
			 			{
	 						if(metaData1.getColumnTypeName(j+1).equalsIgnoreCase("Number"))
	 						{
	 							row1.createCell((short)j).setCellValue(ttkReportsDataSource1.getResultData().getDouble(j+1));
	 						}
	 						else
	 						{
	 							row1.createCell((short)j).setCellValue(new HSSFRichTextString(ttkReportsDataSource1.getResultData().getString(j+1)));
	 						}

			 			}
			  	    	index1++;
			  	    }

			  	    
			  	    String File = "Preauth-Claim Detailed Report";
			  	    request.setAttribute("File",File);
					String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xlsx";
					request.setAttribute("FileDest",fileDest);
					
					FileOutputStream fileOut = new FileOutputStream(fileDest);
					wb.write(fileOut);
					fileOut.close();
					
			  	  	return (mapping.findForward(strIBMReportdisplay));
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
				 public ActionForward doGenerateReoptinReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
					 try{
						 //log.debug("Inside the doGenerateAdditioncutoffMaxRecRpt method of IBMReportAction");
						 setLinks(request);
						 JasperReport jasperReport,emptyReport;
						 JasperPrint jasperPrint;String strReportID="";
						 TTKReportDataSource ttkReportDataSource = null;
						 strReportID = request.getParameter("reportID");
						 //modified by Praveen for passing 3 parameters for the Reportdatasource.
						 ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("Polgrpseqid"),request.getParameter("sFrom"),request.getParameter("sTo"));
						 HSSFWorkbook wb = new HSSFWorkbook();
						 HSSFSheet sheet =wb.createSheet("Reoptin");
						 HSSFRow rowhead = sheet.createRow(0);
						 /* SXSSFWorkbook wb = new SXSSFWorkbook();
						 //Sheet sheet = wb.createSheet("Excel2007");
						 Sheet sheet = wb.createSheet("Reoptin");
						 Row rowhead = sheet.createRow(0);*/
			       		 ResultSetMetaData metaData = ttkReportDataSource.getResultData().getMetaData();
		 				 int rowCount = metaData.getColumnCount();
		 				 for(int i=0;i<rowCount;i++)
		 				 {
		 					rowhead.createCell((short)i).setCellValue(new HSSFRichTextString(metaData.getColumnName(i+1)));
		 				 }
		 				int index = 1;
				  	    while(ttkReportDataSource.getResultData().next())
				  	    {
				  	    	HSSFRow row = sheet.createRow(index);
				  	    	for(int i=0;i<rowCount;i++)
				 			{
				  	    		if(metaData.getColumnTypeName(i+1).equalsIgnoreCase("Number"))
				  	    		{
				  	    			row.createCell((short)i).setCellValue(ttkReportDataSource.getResultData().getDouble(i+1));
				  	    		}
				  	    		else
				  	    		{
				  	    			row.createCell((short)i).setCellValue(new HSSFRichTextString(ttkReportDataSource.getResultData().getString(i+1)));
				  	    		}

				 			}
				  	    	index++;
				  	    }
				  	    String File = "Reoptin Report";
				  	    request.setAttribute("File",File);
						String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xlsx";
						request.setAttribute("FileDest",fileDest);
						
						FileOutputStream fileOut = new FileOutputStream(fileDest);
						wb.write(fileOut);
						fileOut.close();
						

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
					 return(mapping.findForward(strIBMReportdisplay));
			 }//end of doGenerateDailyCancellationRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
				 public ActionForward doGenerateDailyReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
					 try{
						 //log.debug("Inside the doGenerateAdditioncutoffMaxRecRpt method of IBMReportAction");
						 setLinks(request);
						 JasperReport jasperReport,emptyReport;
						 JasperPrint jasperPrint;
						 TTKReportDataSource ttkReportsDataSource = null;
						 TTKReportDataSource ttkReportsDataSource1 = null;
					  	 TTKReportDataSource ttkReportsDataSource2 = null;

					  	 String strReportID = request.getParameter("reportID");

					  	 ttkReportsDataSource = new TTKReportDataSource(strReportID,request.getParameter("Polgrpseqid"),request.getParameter("sFrom"),request.getParameter("sTo"));
					  	 
					  	 
						 
						 

						 HSSFWorkbook wb = new HSSFWorkbook();
						 HSSFSheet sheet =wb.createSheet("Opt out");
						 HSSFRow rowhead = sheet.createRow(0);
						/*SXSSFWorkbook wb = new SXSSFWorkbook();
						HSSFSheet sheet =wb.createSheet("Opt out");
						HSSFRow rowhead = sheet.createRow(0);*/
				       	ResultSetMetaData metaData = ttkReportsDataSource.getResultData().getMetaData();
			 			int rowCount = metaData.getColumnCount();
			 				for(int i=0;i<rowCount;i++)
			 				{
			 					rowhead.createCell((short)i).setCellValue(new HSSFRichTextString(metaData.getColumnName(i+1)));
			 				}
			 				int index = 1;
					  	    while(ttkReportsDataSource.getResultData().next())
					  	    {
					  	    	HSSFRow row = sheet.createRow(index);
					  	    	for(int i=0;i<rowCount;i++)
					 			{
					  	    		if(metaData.getColumnTypeName(i+1).equalsIgnoreCase("Number"))
					  	    		{
					  	    			row.createCell((short)i).setCellValue(ttkReportsDataSource.getResultData().getDouble(i+1));
					  	    		}
					  	    		else
					  	    		{
					  	    			row.createCell((short)i).setCellValue(new HSSFRichTextString(ttkReportsDataSource.getResultData().getString(i+1)));
					  	    		}

					 			}

					  	    	index++;
					  	    }
					  	    String strReportID1 = request.getParameter("reportID1");
							ttkReportsDataSource1 = new TTKReportDataSource(strReportID1,request.getParameter("Polgrpseqid"),request.getParameter("sFrom"),request.getParameter("sTo"));
							
							
							
							
							

							HSSFSheet sheet1 = wb.createSheet("Additional Coverage");
							HSSFRow rowhead1 = sheet1.createRow(0);
					  	    ResultSetMetaData metaData1 = ttkReportsDataSource1.getResultData().getMetaData();
			 				int rowCount1 = metaData1.getColumnCount();
			 				for(int j=0;j<rowCount1;j++)
			 				{
			 					rowhead1.createCell((short)j).setCellValue(new HSSFRichTextString(metaData1.getColumnName(j+1)));
			 				}
			 				int index1 = 1;
			 				while(ttkReportsDataSource1.getResultData().next())
					  	    {
			 					HSSFRow row1 = sheet1.createRow(index1);
			 					for(int j=0;j<rowCount1;j++)
					 			{
			 						if(metaData1.getColumnTypeName(j+1).equalsIgnoreCase("Number"))
			 						{
			 							row1.createCell((short)j).setCellValue(ttkReportsDataSource1.getResultData().getDouble(j+1));
			 						}
			 						else
			 						{
			 							row1.createCell((short)j).setCellValue(new HSSFRichTextString(ttkReportsDataSource1.getResultData().getString(j+1)));
			 						}

					 			}
					     		index1++;
					  	    }
			 				String strReportID2 = request.getParameter("reportID2");
							ttkReportsDataSource2 = new TTKReportDataSource(strReportID2,request.getParameter("Polgrpseqid"),request.getParameter("sFrom"),request.getParameter("sTo"));
							
							
							
							
							

					  	    HSSFSheet sheet2 = wb.createSheet("Parental Coverage");
					  	    HSSFRow rowhead2 = sheet2.createRow(0);
					  	    ResultSetMetaData metaData2 = ttkReportsDataSource2.getResultData().getMetaData();
			 				int rowCount2 = metaData2.getColumnCount();
			 				for(int k=0;k<rowCount2;k++)
			 				{
			 					rowhead2.createCell((short)k).setCellValue(new HSSFRichTextString(metaData2.getColumnName(k+1)));
			 				}
			 				int index2 = 1;
			 				while(ttkReportsDataSource2.getResultData().next())
					  	    {
					  	    	HSSFRow row2 = sheet2.createRow(index2);
					  	    	for(int k=0;k<rowCount2;k++)
					 			{
					  	    		if(metaData2.getColumnTypeName(k+1).equalsIgnoreCase("Number"))
					  	    		{
					  	    			row2.createCell((short)k).setCellValue(ttkReportsDataSource2.getResultData().getDouble(k+1));
					  	    		}
					  	    		else
					  	    		{
					  	    			row2.createCell((short)k).setCellValue(new HSSFRichTextString(ttkReportsDataSource2.getResultData().getString(k+1)));
					  	    		}

					 			}

					  	    	index2++;
					  	    }
			 				
					  	    String File = "Daily Report";
					  	    request.setAttribute("File",File);
							String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xlsx";
							request.setAttribute("FileDest",fileDest);
							
							FileOutputStream fileOut = new FileOutputStream(fileDest);
							wb.write(fileOut);
							fileOut.close();
							


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
					 return(mapping.findForward(strIBMReportdisplay));
			 }//end of doGenerateDailyCancellationRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
				 public ActionForward doGenerateReconReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
					 try{
						 //log.debug("Inside the doGenerateAdditioncutoffMaxRecRpt method of IBMReportAction");
						 setLinks(request);
						 JasperReport jasperReport,emptyReport;
						 JasperPrint jasperPrint;
						 String strReportID="";
						 TTKReportDataSource ttkReportDataSource = null;
						 strReportID = request.getParameter("reportID");
						 //modified by Praveen for passing 3 parameters for the Reportdatasource.
						 ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("Polgrpseqid"),request.getParameter("sFrom"),request.getParameter("sTo"));
						 HSSFWorkbook wb = new HSSFWorkbook();
						 HSSFSheet sheet =wb.createSheet("Recon");
						 HSSFRow rowhead = sheet.createRow(0);

						 /* SXSSFWorkbook wb = new SXSSFWorkbook();
						 //Sheet sheet = wb.createSheet("Excel2007");
						 Sheet sheet = wb.createSheet("Recon");
						 Row rowhead = sheet.createRow(0);*/
			       		 ResultSetMetaData metaData = ttkReportDataSource.getResultData().getMetaData();
		 				 int rowCount = metaData.getColumnCount();
		 				 for(int i=0;i<rowCount;i++)
		 				 {
		 					rowhead.createCell((short)i).setCellValue(new HSSFRichTextString(metaData.getColumnName(i+1)));
		 				 }
		 				int index = 1;
				  	    while(ttkReportDataSource.getResultData().next())
				  	    {
				  	    	HSSFRow row = sheet.createRow(index);
				  	    	for(int i=0;i<rowCount;i++)
				 			{
				  	    		if(metaData.getColumnTypeName(i+1).equalsIgnoreCase("Number"))
				  	    		{
				  	    			row.createCell((short)i).setCellValue(ttkReportDataSource.getResultData().getDouble(i+1));
				  	    		}
				  	    		else
				  	    		{
				  	    			row.createCell((short)i).setCellValue(new HSSFRichTextString(ttkReportDataSource.getResultData().getString(i+1)));
				  	    		}
				 			}
				  	      index++;
				  	    }
				  	    String File = "Recon Report";
				  	    request.setAttribute("File",File);
						String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xlsx";
						request.setAttribute("FileDest",fileDest);
						
						FileOutputStream fileOut = new FileOutputStream(fileDest);
						wb.write(fileOut);
						fileOut.close();
						

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
					 return(mapping.findForward(strIBMReportdisplay));
			 }//end of doGenerateDailyCancellationRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
				 public ActionForward doGenerateChildBornReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
					 try{
						 //log.debug("Inside the doGenerateAdditioncutoffMaxRecRpt method of IBMReportAction");
						 setLinks(request);
						 JasperReport jasperReport,emptyReport;
						 JasperPrint jasperPrint;
						 String strReportID="";
						 TTKReportDataSource ttkReportDataSource = null;
						 strReportID = request.getParameter("reportID");
						 //modified by Praveen for passing 3 parameters for the Reportdatasource.
						 ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("Polgrpseqid"),request.getParameter("sFrom"),request.getParameter("sTo"));
						 HSSFWorkbook wb = new HSSFWorkbook();
						 HSSFSheet sheet = wb.createSheet("ChildBorn");
						 HSSFRow rowhead = sheet.createRow(0);
						 /* SXSSFWorkbook wb = new SXSSFWorkbook();
						 //Sheet sheet = wb.createSheet("Excel2007");
						 Sheet sheet = wb.createSheet("ChildBorn");
						 Row rowhead = sheet.createRow(0);*/
			       		 ResultSetMetaData metaData = ttkReportDataSource.getResultData().getMetaData();
		 				 int rowCount = metaData.getColumnCount();
		 				 for(int i=0;i<rowCount;i++)
		 				 {
		 					rowhead.createCell((short)i).setCellValue(new HSSFRichTextString(metaData.getColumnName(i+1)));
		 				 }
		 				int index = 1;
				  	    while(ttkReportDataSource.getResultData().next())
				  	    {
				  	    	HSSFRow row = sheet.createRow(index);
				  	    	for(int i=0;i<rowCount;i++)
				 			{
				  	    		if(metaData.getColumnTypeName(i+1).equalsIgnoreCase("Number"))
				  	    		{
				  	    			row.createCell((short)i).setCellValue(ttkReportDataSource.getResultData().getDouble(i+1));
				  	    		}
				  	    		else
				  	    		{
				  	    			row.createCell((short)i).setCellValue(new HSSFRichTextString(ttkReportDataSource.getResultData().getString(i+1)));
				  	    		}

				 			}
				  	    	index++;
				  	    }
				  	    String File = "ChildBorn Report";
				  	    request.setAttribute("File",File);
						String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xlsx";
						request.setAttribute("FileDest",fileDest);
						
						FileOutputStream fileOut = new FileOutputStream(fileDest);
						wb.write(fileOut);
						fileOut.close();
						

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
					 return(mapping.findForward(strIBMReportdisplay));
			 }//end of doGenerateDailyCancellationRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

			public ActionForward doGenerateNewDeletionReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                         HttpServletResponse response) throws TTKException{
				try{
				 	setOnlineLinks(request);
				 	JasperReport jasperReport,emptyReport;
				 	JasperPrint jasperPrint;
				 	TTKReportDataSource ttkReportDataSource = null;
				 	String strReportID = request.getParameter("reportID");
				 	ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("Polgrpseqid"),request.getParameter("sFrom"),request.getParameter("sTo"),request.getParameter("status"));
				 	
				 	
				 	
				 	
				 	/*SXSSFWorkbook wb = new SXSSFWorkbook();
		 			Sheet sheet = wb.createSheet("NewDeletion");
		 			Row rowhead = sheet.createRow(0);*/

				 	HSSFWorkbook wb = new HSSFWorkbook();
					HSSFSheet sheet = wb.createSheet("NewDeletion");
					HSSFRow rowhead = sheet.createRow(0);
		 	        ResultSetMetaData metaData = ttkReportDataSource.getResultData().getMetaData();
		 	        int rowCount = metaData.getColumnCount();
		 	        for(int i=0;i<rowCount;i++)
		 	        {
		 	        	rowhead.createCell((short)i).setCellValue(new HSSFRichTextString(metaData.getColumnName(i+1)));
		 	        }
		 	        int index = 1;
		 	        while(ttkReportDataSource.getResultData().next())
		 	        {
		 	        	HSSFRow row = sheet.createRow(index);
		 	        	for(int i=0;i<rowCount;i++)
			 			{
			  	    		if(metaData.getColumnTypeName(i+1).equalsIgnoreCase("Number"))
			  	    		{
			  	    			row.createCell((short)i).setCellValue(ttkReportDataSource.getResultData().getDouble(i+1));
			  	    		}
			  	    		else
			  	    		{
			  	    			row.createCell((short)i).setCellValue(new HSSFRichTextString(ttkReportDataSource.getResultData().getString(i+1)));
			  	    		}

			 			}
		 	        	index++;
		 	        }
				 	
				 	String File = "New Deletion Report";
				 	request.setAttribute("File",File);
				 	String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xlsx";
				 	request.setAttribute("FileDest",fileDest);
				 	
				 	FileOutputStream fileOut = new FileOutputStream(fileDest);
				 	wb.write(fileOut);
				 	fileOut.close();
				 	
				 	return (mapping.findForward(strIBMReportdisplay));
	 	}// end of try
	 catch(TTKException expTTK)
	 {
		 return this.processOnlineExceptions(request, mapping, expTTK);
	 }// end of catch(TTKException expTTK)
	 catch(Exception exp)
	 {
		 return this.processOnlineExceptions(request, mapping, new TTKException(exp, strReportExp));
	 }// end of catch(Exception exp)
}// end of doGenerateNewDeletionReport


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
		public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				//log.debug("Inside the doClose method of AccentureReportsAction");
				setLinks(request);
				Document reportsListDoc=null;
				((DynaActionForm)form).initialize(mapping);//reset the form data
				//get the reports list Document
				reportsListDoc=TTKCommon.getDocument("ReportsList.xml");
				request.setAttribute("ReportsListDoc",reportsListDoc);
				return mapping.findForward(strCorporateRpts);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			}//end of catch(Exception exp)
		}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		/**
		     * This method is called on click of Select Group icon
		     * Finally it forwards to the appropriate view based on the specified forward mappings
		     *
		     * @param mapping The ActionMapping used to select this instance
		     * @param form The optional ActionForm bean for this request (if any)
		     * @param request The HTTP request we are processing
		     * @param response The HTTP response we are creating
		     * @return ActionForward Where the control will be forwarded, after this request is processed
		     * @throws Exception if any error occurs
		     */
		    public ActionForward doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		    										HttpServletResponse response) throws Exception{
		    	try{
		    		setLinks(request);
		    		//log.debug("Inside AccentureReportsAction doSelectGroup");
		    		DynaActionForm frmReportList=(DynaActionForm)form;
		    		request.getSession().setAttribute("frmReportList",frmReportList);
		    		return mapping.findForward(strSelectGroup);
		    	}//end of try
		    	catch(TTKException expTTK)
		    	{
		    		return this.processExceptions(request,mapping,expTTK);
		    	}//end of catch(TTKException expTTK)
		    	catch(Exception exp)
		    	{
		    		return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
		    	}//end of catch(Exception exp)
		    }//end of doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		     //HttpServletResponse response)

		/**
				 * Returns the ProductPolicyManager session object for invoking methods on it.
				 * @return productPolicyManager session object which can be used for method invokation
				 * @exception throws TTKException
				 */
				private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
				{
					ProductPolicyManager productPolicyManager = null;
					try
					{
						if(productPolicyManager == null)
						{
							InitialContext ctx = new InitialContext();
							productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
						}//end of if(productPolicyManager == null)
					}//end of try
					catch(Exception exp)
					{
						throw new TTKException(exp, strWebConfig);
					}//end of catch
					return productPolicyManager;
		}//end getProductPolicyManagerObject()


		/**
		     * This method is called on select of policy no. to load Report From and Report To fields
		     * Finally it forwards to the appropriate view based on the specified forward mappings
		     *
		     * @param mapping The ActionMapping used to select this instance
		     * @param form The optional ActionForm bean for this request (if any)
		     * @param request The HTTP request we are processing
		     * @param response The HTTP response we are creating
		     * @return ActionForward Where the control will be forwarded, after this request is processed
		     * @throws Exception if any error occurs
		     */
		    public ActionForward doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		    										HttpServletResponse response) throws Exception{
		    	try{
		    		setLinks(request);
		    		//log.debug("Inside AccentureReportsAction doSelectPolicy");
		    		DynaActionForm frmReportList=(DynaActionForm)form;
		    		DynaActionForm frmMISReports = (DynaActionForm)form;
		    		ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
		    		Object[] objArray = null;
		    		objArray = prodPolicyManager.getReportFromTo(TTKCommon.getLong(frmReportList.getString("policySeqID")));
		    		java.util.Date dtFrom = new java.util.Date();
		    		java.util.Date dtTo = new java.util.Date();
		    		java.text.SimpleDateFormat sdt;
		    		sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
		    		String strFrom="";
		    		String strTo="";
		    		if(objArray!=null){
		    			if( ((String)objArray[0]).length()!=0   ){
		    				dtFrom.setDate(Integer.parseInt((String)objArray[0])  );
		        			dtFrom.setMonth(dtTo.getMonth()-1);
		        			strFrom = sdt.format(dtFrom);
		    			}//end of if(!("".equals((String)objArray[0])))
		    			if( ((String)objArray[1]).length()!=0   ){
		    				dtTo.setDate( Integer.parseInt((String)objArray[1])  );
		    				strTo = sdt.format(dtTo);
		    			}

		    		}//end of if(!(("".equals((String)objArray[0])) && ("".equals((String)objArray[1])))){
		    		if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
		    		{
		    			//frmMISReports.set("sStartDate",strFrom );
		    			//frmMISReports.set("sEndDate", strTo);
		        		request.getSession().setAttribute("frmMISReports",frmMISReports);
		    		}//end of if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
		    		else
		    		{
		    			frmReportList.set("sReportFrom",strFrom );
		    			frmReportList.set("sReportTo", strTo);
		    			request.getSession().setAttribute("frmReportList",frmReportList);
		    		}//end of else
		    		return mapping.findForward(strIBMRptDetails);
		    	}//end of try
		    	catch(TTKException expTTK)
		    	{
		    		return this.processExceptions(request,mapping,expTTK);
		    	}//end of catch(TTKException expTTK)
		    	catch(Exception exp)
		    	{
		    		return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
		    	}//end of catch(Exception exp)
		    }//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		     //HttpServletResponse response)


				/**
			     * This method is called on select of policy no. to load Report From and Report To fields
			     * Finally it forwards to the appropriate view based on the specified forward mappings
			     *
			     * @param mapping The ActionMapping used to select this instance
			     * @param form The optional ActionForm bean for this request (if any)
			     * @param request The HTTP request we are processing
			     * @param response The HTTP response we are creating
			     * @return ActionForward Where the control will be forwarded, after this request is processed
			     * @throws Exception if any error occurs
			     */
			    public ActionForward doAdditioncutoffMaxRecSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			    										HttpServletResponse response) throws Exception{
			    	try{
			    		setLinks(request);
			    		//log.debug("Inside AccentureReportsAction doSelectPolicy");
			    		DynaActionForm frmReportList=(DynaActionForm)form;
			    		DynaActionForm frmMISReports = (DynaActionForm)form;
			    		ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
			    		Object[] objArray = null;
			    		objArray = prodPolicyManager.getReportFromTo(TTKCommon.getLong(frmReportList.getString("policySeqID")));
			    		java.util.Date dtFrom = new java.util.Date();
			    		java.util.Date dtTo = new java.util.Date();
			    		java.text.SimpleDateFormat sdt;
			    		sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
			    		String strFrom="";
			    		String strTo="";
			    		if(objArray!=null){
			    			if( ((String)objArray[0]).length()!=0   ){
			    				dtFrom.setDate(Integer.parseInt((String)objArray[0])  );
			        			dtFrom.setMonth(dtTo.getMonth()-1);
			        			strFrom = sdt.format(dtFrom);
			    			}//end of if(!("".equals((String)objArray[0])))
			    			if( ((String)objArray[1]).length()!=0   ){
			    				dtTo.setDate( Integer.parseInt((String)objArray[1])  );
			    				strTo = sdt.format(dtTo);
			    			}

			    		}//end of if(!(("".equals((String)objArray[0])) && ("".equals((String)objArray[1])))){
			    		if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
			    		{
			    			//frmMISReports.set("sStartDate",strFrom );
			    			//frmMISReports.set("sEndDate", strTo);
			        		request.getSession().setAttribute("frmMISReports",frmMISReports);
			    		}//end of if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
			    		else
			    		{
			    			frmReportList.set("sReportFrom",strFrom );
			    			frmReportList.set("sReportTo", strTo);
			    			request.getSession().setAttribute("frmReportList",frmReportList);
			    		}//end of else
			    		return mapping.findForward(strIBMAdditioncutoffMaxRecRptDetails);
			    	}//end of try
			    	catch(TTKException expTTK)
			    	{
			    		return this.processExceptions(request,mapping,expTTK);
			    	}//end of catch(TTKException expTTK)
			    	catch(Exception exp)
			    	{
			    		return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
			    	}//end of catch(Exception exp)
			    }//end of doAdditioncutoffMaxRecSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     //HttpServletResponse response)

		  public ActionForward doIBMPreauthSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
						HttpServletResponse response) throws Exception{
			try{
				setLinks(request);
				//log.debug("Inside AccentureReportsAction doSelectPolicy");
				DynaActionForm frmReportList=(DynaActionForm)form;
				DynaActionForm frmMISReports = (DynaActionForm)form;
				ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
				Object[] objArray = null;
				objArray = prodPolicyManager.getReportFromTo(TTKCommon.getLong(frmReportList.getString("policySeqID")));
				java.util.Date dtFrom = new java.util.Date();
				java.util.Date dtTo = new java.util.Date();
				java.text.SimpleDateFormat sdt;
				sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
				String strFrom="";
				String strTo="";
				 if(objArray!=null){
					if( ((String)objArray[0]).length()!=0   ){
								dtFrom.setDate(Integer.parseInt((String)objArray[0])  );
								dtFrom.setMonth(dtTo.getMonth()-1);
								strFrom = sdt.format(dtFrom);
			   }//end of if(!("".equals((String)objArray[0])))
			if( ((String)objArray[1]).length()!=0   ){
			dtTo.setDate( Integer.parseInt((String)objArray[1])  );
			strTo = sdt.format(dtTo);
			}

			}//end of if(!(("".equals((String)objArray[0])) && ("".equals((String)objArray[1])))){
			if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
			{
			//frmMISReports.set("sStartDate",strFrom );
			//frmMISReports.set("sEndDate", strTo);
			request.getSession().setAttribute("frmMISReports",frmMISReports);
			}//end of if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
			else
			{
				frmReportList.set("sReportFrom",strFrom );
				frmReportList.set("sReportTo", strTo);
				request.getSession().setAttribute("frmReportList",frmReportList);
				}//end of else
			return mapping.findForward(strIBMPreauthRptDetails);
			}//end of try
				catch(TTKException expTTK)
				{
					return this.processExceptions(request,mapping,expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
				}//end of catch(Exception exp)
 	}//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)

		public ActionForward doIBMReoptinSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
					HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside AccentureReportsAction doSelectPolicy");
			DynaActionForm frmReportList=(DynaActionForm)form;
			DynaActionForm frmMISReports = (DynaActionForm)form;
			ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
			Object[] objArray = null;
			objArray = prodPolicyManager.getReportFromTo(TTKCommon.getLong(frmReportList.getString("policySeqID")));
			java.util.Date dtFrom = new java.util.Date();
			java.util.Date dtTo = new java.util.Date();
			java.text.SimpleDateFormat sdt;
			sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String strFrom="";
			String strTo="";
			 if(objArray!=null){
				if( ((String)objArray[0]).length()!=0   ){
							dtFrom.setDate(Integer.parseInt((String)objArray[0])  );
							dtFrom.setMonth(dtTo.getMonth()-1);
							strFrom = sdt.format(dtFrom);
		   }//end of if(!("".equals((String)objArray[0])))
		if( ((String)objArray[1]).length()!=0   ){
		dtTo.setDate( Integer.parseInt((String)objArray[1])  );
		strTo = sdt.format(dtTo);
		}

		}//end of if(!(("".equals((String)objArray[0])) && ("".equals((String)objArray[1])))){
		if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
		{
		//frmMISReports.set("sStartDate",strFrom );
		//frmMISReports.set("sEndDate", strTo);
		request.getSession().setAttribute("frmMISReports",frmMISReports);
		}//end of if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
		else
		{
			frmReportList.set("sReportFrom",strFrom );
			frmReportList.set("sReportTo", strTo);
			request.getSession().setAttribute("frmReportList",frmReportList);
			}//end of else
		return mapping.findForward(strIBMReoptinRptDetails);
		}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request,mapping,expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
			}//end of catch(Exception exp)
}//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)

		public ActionForward doIBMDailyReportSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
	try{
		setLinks(request);
		//log.debug("Inside AccentureReportsAction doSelectPolicy");
		DynaActionForm frmReportList=(DynaActionForm)form;
		DynaActionForm frmMISReports = (DynaActionForm)form;
		ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
		Object[] objArray = null;
		objArray = prodPolicyManager.getReportFromTo(TTKCommon.getLong(frmReportList.getString("policySeqID")));
		java.util.Date dtFrom = new java.util.Date();
		java.util.Date dtTo = new java.util.Date();
		java.text.SimpleDateFormat sdt;
		sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
		String strFrom="";
		String strTo="";
		 if(objArray!=null){
			if( ((String)objArray[0]).length()!=0   ){
						dtFrom.setDate(Integer.parseInt((String)objArray[0])  );
						dtFrom.setMonth(dtTo.getMonth()-1);
						strFrom = sdt.format(dtFrom);
	   }//end of if(!("".equals((String)objArray[0])))
	if( ((String)objArray[1]).length()!=0   ){
	dtTo.setDate( Integer.parseInt((String)objArray[1])  );
	strTo = sdt.format(dtTo);
	}

	}//end of if(!(("".equals((String)objArray[0])) && ("".equals((String)objArray[1])))){
	if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
	{
	//frmMISReports.set("sStartDate",strFrom );
	//frmMISReports.set("sEndDate", strTo);
	request.getSession().setAttribute("frmMISReports",frmMISReports);
	}//end of if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
	else
	{
		frmReportList.set("sReportFrom",strFrom );
		frmReportList.set("sReportTo", strTo);
		request.getSession().setAttribute("frmReportList",frmReportList);
		}//end of else
	return mapping.findForward(strIBMDailyRptDetails);
	}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
}//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)

	public ActionForward doIBMReconSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
	try{
		setLinks(request);
		//log.debug("Inside AccentureReportsAction doSelectPolicy");
		DynaActionForm frmReportList=(DynaActionForm)form;
		DynaActionForm frmMISReports = (DynaActionForm)form;
		ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
		Object[] objArray = null;
		objArray = prodPolicyManager.getReportFromTo(TTKCommon.getLong(frmReportList.getString("policySeqID")));
		java.util.Date dtFrom = new java.util.Date();
		java.util.Date dtTo = new java.util.Date();
		java.text.SimpleDateFormat sdt;
		sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
		String strFrom="";
		String strTo="";
		 if(objArray!=null){
			if( ((String)objArray[0]).length()!=0   ){
						dtFrom.setDate(Integer.parseInt((String)objArray[0])  );
						dtFrom.setMonth(dtTo.getMonth()-1);
						strFrom = sdt.format(dtFrom);
	   }//end of if(!("".equals((String)objArray[0])))
	if( ((String)objArray[1]).length()!=0   ){
	dtTo.setDate( Integer.parseInt((String)objArray[1])  );
	strTo = sdt.format(dtTo);
	}

	}//end of if(!(("".equals((String)objArray[0])) && ("".equals((String)objArray[1])))){
	if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
	{
	//frmMISReports.set("sStartDate",strFrom );
	//frmMISReports.set("sEndDate", strTo);
	request.getSession().setAttribute("frmMISReports",frmMISReports);
	}//end of if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
	else
	{
		frmReportList.set("sReportFrom",strFrom );
		frmReportList.set("sReportTo", strTo);
		request.getSession().setAttribute("frmReportList",frmReportList);
		}//end of else
	return mapping.findForward(strIBMReconRptDetails);
	}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
}//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)

	public ActionForward doIBMChildBornSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
try{
	setLinks(request);
	//log.debug("Inside AccentureReportsAction doSelectPolicy");
	DynaActionForm frmReportList=(DynaActionForm)form;
	DynaActionForm frmMISReports = (DynaActionForm)form;
	ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
	Object[] objArray = null;
	objArray = prodPolicyManager.getReportFromTo(TTKCommon.getLong(frmReportList.getString("policySeqID")));
	java.util.Date dtFrom = new java.util.Date();
	java.util.Date dtTo = new java.util.Date();
	java.text.SimpleDateFormat sdt;
	sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
	String strFrom="";
	String strTo="";
	 if(objArray!=null){
		if( ((String)objArray[0]).length()!=0   ){
					dtFrom.setDate(Integer.parseInt((String)objArray[0])  );
					dtFrom.setMonth(dtTo.getMonth()-1);
					strFrom = sdt.format(dtFrom);
   }//end of if(!("".equals((String)objArray[0])))
if( ((String)objArray[1]).length()!=0   ){
dtTo.setDate( Integer.parseInt((String)objArray[1])  );
strTo = sdt.format(dtTo);
}

}//end of if(!(("".equals((String)objArray[0])) && ("".equals((String)objArray[1])))){
if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
{
//frmMISReports.set("sStartDate",strFrom );
//frmMISReports.set("sEndDate", strTo);
request.getSession().setAttribute("frmMISReports",frmMISReports);
}//end of if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
else
{
	frmReportList.set("sReportFrom",strFrom );
	frmReportList.set("sReportTo", strTo);
	request.getSession().setAttribute("frmReportList",frmReportList);
	}//end of else
return mapping.findForward(strIBMChildBornRptDetails);
}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request,mapping,expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
	}//end of catch(Exception exp)
}//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)


}//end of IBMReportsAction
