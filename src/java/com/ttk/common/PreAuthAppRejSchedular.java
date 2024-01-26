/**
 * @ (#) PreAuthAppRejSchedular.java Jan 02, 2013
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthAppRejSchedular.java
 * Author        : SATYA 
 *
 */
package com.ttk.common;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import javax.naming.InitialContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.onlineforms.OnlineFeedbackManager;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;

import com.ttk.dao.impl.reports.TTKReportDataSource;
//import com.ttk.dto.claims.ClaimShortfallVO;

public class PreAuthAppRejSchedular implements Job{

	private static final Logger log = Logger.getLogger(PreAuthAppRejSchedular.class);
	private static final String strStatusError="PreAuthAppRejSchedular";
	OnlineFeedbackManager feedbackManagerObject=null;
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		try{
			feedbackManagerObject=this.getFeedbackManagerObject();
			log.info("PreAuthAppRejSchedular in process");
			String strFileName="";
			String strMsgId="";
			String strSheetName="";
			String strPath=(String)TTKPropertiesReader.getPropertyValue("inspreauthdir");
			

			//CommunicationManager communicationManager = this.getCommunicationManagerObject();
			String jrxmlFiles[] = new String[]
			                                 {
					"generalreports/PreauthExcelReport.jrxml",
					"generalreports/PreauthExcelReport.jrxml",
					"generalreports/PreauthExcelReport.jrxml",
			                                 };		
		
			String [] strMsgIds = null;
	     	String strSheetNames[]=null;
			String strReportID="preauthintimation";
			String strParameter="BA";
			String iNoOfCursor="3";
			int noofcursors=3;
			//TTKReportDataSource ttkReportDataSource = new TTKReportDataSource(strReportID,strParameter,iNoOfCursor);
			TTKReportDataSource ttkReportsDataSource = new TTKReportDataSource(strReportID, strParameter,iNoOfCursor);
			JasperReport jasperReport,xmljasperReport,emptyReport;
			JasperReport objJasperReport[] =new JasperReport[noofcursors];
			JasperPrint jasperPrint;

			JasperPrint objJasperPrint[] = new JasperPrint[Integer.parseInt(iNoOfCursor)];
			//JasperPrint objJasperPrint1,objJasperPrint2 = new JasperPrint();
			
			//HashMap<String, Object> hashMap =null;
			ByteArrayOutputStream boas=new ByteArrayOutputStream();	
			JRBeanCollectionDataSource dataSource = null;
			org.w3c.dom.Document document = null;
			objJasperReport[0] = JasperCompileManager.compileReport(jrxmlFiles[0]);
			objJasperReport[1] = JasperCompileManager.compileReport(jrxmlFiles[1]);
			objJasperReport[2] = JasperCompileManager.compileReport(jrxmlFiles[2]);
			//	objJasperReport[3] = JasperCompileManager.compileReport(jrxmlFiles[3]);
			HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
			
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			if(ttkReportsDataSource.isResultSetArrayEmpty())
			{
				emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());

			}//end of if(ttkReportDataSource.isResultSetArrayEmpty())
			else
			{
				for(int i=0;i<Integer.parseInt(iNoOfCursor);i++){
					ResultSet rs = ttkReportsDataSource.getResultData(i);


					TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
					DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
					df.setTimeZone(tz);  
					String 	strTimeStamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
				   if(ttkReportsDataSource.getResultData(i)!=null)
					{
						if(i==0)
						{
						strFileName= "insconpreauth"+strTimeStamp+"_batch";
						strMsgId="PAT_INT_INSURANCE";
						strSheetNames=new String[]{"preauthlist"};
						}//end of if(i==0)
						else if(i==1)
						{
							strSheetNames=new String[]{"ins_app_preauths"};
							strFileName="patinsappttkspoc"+strTimeStamp+"_batch";
							strMsgId="PAT_APR_TTKSPOC";
							//strSheetName=strSheetNames[i];
						}//end of else if(i==1)
						else if(i==2)
						{
							strSheetNames=new String[]{"ins_reqinf_preauths"};
							strFileName="patinsreqinfttkspoc"+strTimeStamp+"_batch";
							strMsgId="PAT_REQ_TTKSPOC";
							//strSheetName=strSheetNames[i];
						}//end of else if(i==2)
					}//end of if(ttkReportsDataSource.getResultData(i)!=null)
					String fileLocation=(strPath+strFileName);
					if(rs.next())
					{
						rs.beforeFirst();
						objJasperPrint[i] = JasperFillManager.fillReport(objJasperReport[i],hashMap,new JRResultSetDataSource(rs));
						JRXlsExporter exporterXL = new JRXlsExporter();
						exporterXL.setParameter(JRXlsExporterParameter.JASPER_PRINT, objJasperPrint[i]);
						exporterXL.setParameter(JRXlsExporterParameter.SHEET_NAMES, strSheetNames);
						exporterXL.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME,(fileLocation+".xls"));
						exporterXL.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
						exporterXL.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
						exporterXL.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
						exporterXL.exportReport();
					


						int result=feedbackManagerObject.sendMailIntimation(strParameter, strMsgId, strFileName);
					}//end of if(rs.next())
					else 
					{
						objJasperPrint[i] = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
					}//end of else

				}//end of for(int i=0;i<iNoOfCursor;i++){
				log.info("done");
			}//end of if(ttkReportDataSource.getResultData().next())
		}//end of try
		catch (JRException e)
		{
			e.printStackTrace();
		}//end of catch(JRException e)
		catch(Exception e){
			new Exception(strStatusError);
			e.printStackTrace();
		}//end of catch(Exception e)

	}//end of execute(JobExecutionContext arg0)


	/**
	 * Returns the FeedbackManager session object for invoking methods on it.
	 * @return FeedbackManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private OnlineFeedbackManager getFeedbackManagerObject() throws TTKException
	{
		OnlineFeedbackManager feedbackManager = null;
		try
		{
			if(feedbackManager == null)
			{
				InitialContext ctx = new InitialContext();
				feedbackManager = (OnlineFeedbackManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineFeedbackManagerBean!com.ttk.business.onlineforms.OnlineFeedbackManager");
			}//end of if(feedbackManager == null)
		}//end of try

		catch(Exception exp)
		{
			throw new TTKException(exp,strStatusError);
		}//end of catch
		return feedbackManager;
	}//end of private OnlineFeedbackManager getFeedbackManagerObject() throws TTKException
	/*
	 *//**
	 * Returns the CommunicationManager session object for invoking methods on it.
	 * @return CommunicationManager session object which can be used for method invocation
	 * @exception throws TTKException
	 *//*
	private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}//end of if(communicationManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strStatusError);
		}//end of catch
		return communicationManager;
	}//end getCommunicationManagerObject()	
	  */}//end ofPreAuthAppRejSchedular 
