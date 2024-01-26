/**
 * @ (#) MRPendReportScheduler.java October 13, 2008
 * Project      : TTK HealthCare Services
 * File         : MRPendReportScheduler.java
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : October 13, 2008
 *
 * @author       : Balakrishna Erram
 * Modified by   : 
 * Modified date : 
 * Reason        :
 */

package com.ttk.common;

import java.util.HashMap;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.maintenance.CustomizeConfigVO;

public class MRPendReportScheduler implements Job {
	
	private static final Logger log = Logger.getLogger(MRPendReportScheduler.class);
	private static final String strMRCLMError="ScheduledCallPendingSRTFallReport";
	private static final String strPendFileName = "generalreports/MRClaimsPendingReport.jrxml";
	private static final String strPendSrtfallFileName = "generalreports/MRClaimsPendingSrtfallReport.jrxml";
	private static final String strPendReportID ="CallPendMRRpt";
	private static final String strPendSrtfallReportID ="CallPendMRShrtfallRpt";
	private static final String strPendMSGID = "MR_CLAIMS_PENDING_REPORT";
	private static final String strPendSrtfallMSGID = "MR_CLAIMS_PENDING_SRTFALL_REPORT";
	
	/** This Method automatically called based on the scheduler process constring time  
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;	
		try
		{
			maintenanceManager = this.getMaintenanceManagerObject();	
			//ArrayList<Object> alCustomize = new ArrayList<Object>();			
			CustomizeConfigVO customConfigVO =maintenanceManager.getMrClaimsPendConfig(strPendMSGID);
			
			String strConfigParam1 =TTKCommon.checkNull(Integer.toString(customConfigVO.getConfigParam1()));			
			String strConfigParam2 = TTKCommon.checkNull(Integer.toString(customConfigVO.getConfigParam2()));
			String strConfigParam3 = TTKCommon.checkNull(Integer.toString(customConfigVO.getConfigParam3()));
			String strMailID1 = TTKCommon.checkNull(customConfigVO.getPrimaryMailID1());
			String strMailID2 = TTKCommon.checkNull(customConfigVO.getPrimaryMailID2());
			String strMailID3 = TTKCommon.checkNull(customConfigVO.getPrimaryMailID3());
			log.info("---ConfigParam1 :"+strConfigParam1);
			log.info("---ConfigParam2 :"+strConfigParam2);
			log.info("---ConfigParam3 :"+strConfigParam3);
			log.info("---strMailID1   :"+strMailID1);
			log.info("---strMailID2   :"+strMailID2);
			log.info("---strMailID3   :"+strMailID3);
			if(!TTKCommon.checkNull(strConfigParam1).equals(""))
			{
				mrClaimsPendRpt(strPendMSGID,strPendReportID,strPendFileName,strConfigParam1,
						strMailID1,maintenanceManager);
			}//End of if(TTKCommon.checkNull(ConfigParam1).equals(""))
			if(!TTKCommon.checkNull(strConfigParam2).equals(""))
			{
				mrClaimsPendRpt(strPendMSGID,strPendReportID,strPendFileName,strConfigParam2,
						strMailID2,maintenanceManager);
			}//End of if(TTKCommon.checkNull(ConfigParam2).equals(""))
			if(!TTKCommon.checkNull(strConfigParam3).equals(""))
			{
				mrClaimsPendRpt(strPendMSGID,strPendReportID,strPendFileName,strConfigParam3,
						strMailID3,maintenanceManager);
			}//End of if(TTKCommon.checkNull(ConfigParam3).equals(""))
			
			CustomizeConfigVO customConfigMRVO =maintenanceManager.getMrClaimsPendConfig(strPendSrtfallMSGID);
			
			String strConfigParamMR1 =TTKCommon.checkNull(Integer.toString(customConfigMRVO.getConfigParam1()));			
			String strConfigParamMR2 = TTKCommon.checkNull(Integer.toString(customConfigMRVO.getConfigParam2()));
			String strConfigParamMR3 = TTKCommon.checkNull(Integer.toString(customConfigMRVO.getConfigParam3()));
			String strMailIDMR1 = TTKCommon.checkNull(customConfigMRVO.getPrimaryMailID1());
			String strMailIDMR2 = TTKCommon.checkNull(customConfigMRVO.getPrimaryMailID2());
			String strMailIDMR3 = TTKCommon.checkNull(customConfigMRVO.getPrimaryMailID3());
			log.info("---ConfigParamMR1 :"+strConfigParamMR1);
			log.info("---ConfigParamMR2 :"+strConfigParamMR2);
			log.info("---ConfigParamMR3 :"+strConfigParamMR3);
			log.info("---strMailIDMR1   :"+strMailIDMR1);
			log.info("---strMailIDMR2   :"+strMailIDMR2);
			log.info("---strMailIDMR3   :"+strMailIDMR3);
			if(!TTKCommon.checkNull(strConfigParamMR1).equals(""))
			{
				mrClaimsPendRpt(strPendSrtfallMSGID,strPendSrtfallReportID,strPendSrtfallFileName,
						strConfigParamMR1,strMailIDMR1,maintenanceManager);
			}//End of if(TTKCommon.checkNull(strConfigParamMR1).equals(""))
			if(!TTKCommon.checkNull(strConfigParamMR2).equals(""))
			{
				mrClaimsPendRpt(strPendSrtfallMSGID,strPendSrtfallReportID,strPendSrtfallFileName,
						strConfigParamMR2,strMailIDMR2,maintenanceManager);
			}//End of if(TTKCommon.checkNull(strConfigParamMR2).equals(""))
			if(!TTKCommon.checkNull(strConfigParamMR3).equals(""))
			{
				mrClaimsPendRpt(strPendSrtfallMSGID,strPendSrtfallReportID,strPendSrtfallFileName,
						strConfigParamMR3,strMailIDMR3,maintenanceManager);
			}//End of if(TTKCommon.checkNull(ConfigParamMR3).equals(""))			
						
		}catch(Exception e){
			new Exception(strMRCLMError);
			TTKCommon.logStackTrace(e);
		}//end of catch(Exception e)		
	}//end of run method
	
	/** This method Generate the callpending Report branch wise everyday 
	 * @param strMSGID String MSG Id
	 * @param strReportID String Report Id
	 * @param strJrxmlFileName String Jrxml File Name
	 * @param strConfigParam String Cofig Param
	 * @param strOfficeCode String Office Code
	 * @param strMailID String  Mail ID
	 * @param alCallPenByBranRpt ArrayList which contains Config params and primary mail ids
	 * @param maintenanceManager MaintenanceManager Obect 
	 */	
	public static void mrClaimsPendRpt(String strMSGID,String strReportID, String strJrxmlFileName, 
			String strConfigParam, String strMailID,MaintenanceManager maintenanceManager) throws TTKException
	{
		try{
			log.info("************inside mrClaimsPendRpt");
			String strDate = TTKCommon.getServerDate();	
			JasperReport jasperReport = null;
			JasperPrint  jasperPrint = null;	
			String strPDFFileName="";
			if(TTKCommon.checkNull(strMSGID).equals("MR_CLAIMS_PENDING_REPORT"))
			{
				strPDFFileName = "MR_CLAIMS_PENDING_";
			}//end of if(TTKCommon.checkNull(strMSGID).equals("MR_CLAIMS_PENDING_REPORT"))
			else
			{
				strPDFFileName = "MR_CLAIMS_PENDING_SRT_";
			}//end of else
			String strFileName =TTKPropertiesReader.getPropertyValue("callpendingdir")+strPDFFileName+strConfigParam+"_"+strDate+".pdf";
			jasperReport = JasperCompileManager.compileReport(strJrxmlFileName);
			jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap(),
														new TTKReportDataSource(strReportID,strConfigParam));
			log.info(" strFileName "+strFileName);
			log.info(" strConfigParam "+strConfigParam);
			JasperExportManager.exportReportToPdfFile(jasperPrint,strFileName);
			maintenanceManager.insertRecord(strMSGID,strConfigParam,strMailID);
			log.info(" : Done : ");
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMRCLMError);
		}//end of catch(Exception exp)
	}//end of mrClaimsPendRpt()
	
	/**
	 * Returns the MaintenanceManager session object for invoking methods on it.
	 * @return MaintenanceManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MaintenanceManager getMaintenanceManagerObject() throws TTKException
	{
		MaintenanceManager maintenanceManager = null;
		try
		{
			if(maintenanceManager == null)
			{
				InitialContext ctx = new InitialContext();
				maintenanceManager = (MaintenanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/MaintenanceManagerBean!com.ttk.business.maintenance.MaintenanceManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMRCLMError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	
}//end of MRPendReportScheduler
