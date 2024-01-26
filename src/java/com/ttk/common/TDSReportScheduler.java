/**
 * @ (#) TDSReportScheduler.java August 10, 2009
 * Project 	     : TTK HealthCare Services
 * File          : TDSReportScheduler.java
 * Author        : BALAKRISHNA E
 * Company       : Span Systems Corporation
 * Date Created  : August 10, 2009
 *
 * @author       :	BALAKRISHNA E
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.common;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.ttk.dao.impl.reports.TTKReportDataSource;

public class TDSReportScheduler implements Job {
	
	private static final Logger log = Logger.getLogger(TDSReportScheduler.class);
	private static final String strTDSError="ScheduledTDSReport";
	
	/** This Method automatically called based on the scheduler process constring time  
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) {	
		try{
			String strDate = TTKCommon.getServerDate();	
			String strJrxmlFileName =  "generalreports/TDSReport.jrxml";
			JasperReport jasperReport = null;
			JasperPrint  jasperPrint = null;	
			String strExcelFileName="TIPS";
			String strReportID ="TDSSchedRpt";
			
			String strFileName =TTKPropertiesReader.getPropertyValue("tdsdir")+strExcelFileName+"_"+strDate+".xls";
			jasperReport = JasperCompileManager.compileReport(strJrxmlFileName);
			
			TTKReportDataSource ttkReportDataSource = new TTKReportDataSource(strReportID);
			if(ttkReportDataSource.getResultData().next())
			{
				ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap<Object, Object>(),ttkReportDataSource);
			}//end of if(ttkReportDataSource.getResultData().next())
			log.info(" strFileName "+strFileName);
			//JasperExportManager.exportReportToPdfFile(jasperPrint,strFileName);
			JRXlsExporter jrXlsExporter = new JRXlsExporter();
			jrXlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
			jrXlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strFileName);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			jrXlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			jrXlsExporter.exportReport();			 
			log.info(" : Done : ");
		}//end of try
		catch(Exception e){
			new Exception(strTDSError);
		}//end of catch(Exception e)
	}//end of execute()
		
}//end of TDSReportScheduler
