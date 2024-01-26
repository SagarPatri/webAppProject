/**
 * @ (#) StatusRptScheduler.java December 06, 2010
 * Project 	     : TTK HealthCare Services
 * File          : StatusRptScheduler.java
 * Author        : Manikanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : December 06, 2010
 *
 * @author       :	Manikanta Kumar G G
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
package com.ttk.common;


import java.util.Date;
import java.util.HashMap;

import javax.naming.InitialContext;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

//import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;


public class StatusRptScheduler implements Job{
	
	private static final String strStatusError="ScheduledStatusReport";

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CommunicationManager communicationManager = null;
		try{
			String strDate = TTKCommon.getServerDate();	
			String strJrxmlFileName =  "generalreports/JobStatusReport.jrxml";
			JasperReport jasperReport = null;
			JasperPrint  jasperPrint = null;	
			communicationManager = this.getCommunicationManagerObject();
			String strPDFFileName="JobStatusRpt";
			String strReportID ="JobStatusRpt";
			Date dt=TTKCommon.getDate();
			String strParam=TTKCommon.getFormattedDate(dt);
			String strFileName =TTKPropertiesReader.getPropertyValue("JobStatusdir")+strPDFFileName+"_"+strDate+".pdf";
			jasperReport = JasperCompileManager.compileReport(strJrxmlFileName);
			TTKReportDataSource ttkReportDataSource = new TTKReportDataSource(strReportID,strParam);
			if(ttkReportDataSource.getResultData().next())
			{
				ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap<Object, Object>(),ttkReportDataSource);
				JasperExportManager.exportReportToPdfFile(jasperPrint,strFileName);
				communicationManager.insertRecord("SCHEDULED_JOBS");
			}//end of if(ttkReportDataSource.getResultData().next())
		}//end of try
		catch(Exception e){
			new Exception(strStatusError);
			e.printStackTrace();
		}//end of catch(Exception e)
		
	}//end of execute(JobExecutionContext arg0)
	

	/**
	 * Returns the CommunicationManager session object for invoking methods on it.
	 * @return CommunicationManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
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
}//end of StatusRptScheduler
