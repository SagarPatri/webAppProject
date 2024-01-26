/**
 * @ (#) ReportScheduler.java June 17, 2008
 * Project      : TTK HealthCare Services
 * File         : EmailScheduler.java
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : June 17, 2008
 *
 * @author       : Balaji C R B
 * Modified by   : Balkrishna Erram
 * Modified date : Aug 07, 2008
 * Reason        :
 */

package com.ttk.common;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
//import org.apache.log4j.Logger;

import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.maintenance.CustomizeConfigVO;

public class ReportScheduler implements Job {
	
	private static final String strCallPendingError="ScheduledCallPendingReport";
	
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;	
		try
		{
			maintenanceManager = this.getMaintenanceManagerObject();	
			callPendGenerateRpt("","","",new ArrayList(),maintenanceManager);
			ArrayList<Object> alCallList = new ArrayList<Object>();
			alCallList = maintenanceManager.getCallpendingByBranch();
			CustomizeConfigVO customizeConfigVO = null;
			
			for(int i=0; i<alCallList.size(); i++)
			{
				customizeConfigVO = new CustomizeConfigVO();
				customizeConfigVO =(CustomizeConfigVO)alCallList.get(i);
				String strOfficeCode = customizeConfigVO.getOfficeCode();
				Long lngOfficeSeqID = customizeConfigVO.getOfficeSeqID();
				String strConfigParam1 = "";
				String strConfigParam2 = "";
				String strConfigParam3 = "";
				String strPrimaryMailID1= customizeConfigVO.getPrimaryMailID1();
				String strPrimaryMailID2= customizeConfigVO.getPrimaryMailID2();
				String strPrimaryMailID3= customizeConfigVO.getPrimaryMailID3();
				ArrayList<Object> alCallPenByBranRpt = new ArrayList<Object>();
				alCallPenByBranRpt.add(0,lngOfficeSeqID);						
				
				if(customizeConfigVO.getConfigParam1() != null && customizeConfigVO.getConfigParam1().intValue() > 0)
				{
					strConfigParam1 = Integer.toString(customizeConfigVO.getConfigParam1());
					alCallPenByBranRpt.add(1,strConfigParam1);
					callPendGenerateRpt(strOfficeCode,strConfigParam1,strPrimaryMailID1,alCallPenByBranRpt,
																							maintenanceManager);
				}//end of if(customizeConfigVO.getConfigParam1() != null && 
							//customizeConfigVO.getConfigParam1().intValue() > 0)
				if(customizeConfigVO.getConfigParam2() != null && customizeConfigVO.getConfigParam2().intValue() > 0)
				{
					strConfigParam2 = Integer.toString(customizeConfigVO.getConfigParam2());
					alCallPenByBranRpt.add(1, strConfigParam2);
					callPendGenerateRpt(strOfficeCode,strConfigParam2,strPrimaryMailID2,alCallPenByBranRpt,
																							maintenanceManager);
				}//end of if(customizeConfigVO.getConfigParam2() != null && 
							//customizeConfigVO.getConfigParam2().intValue() > 0)
				if(customizeConfigVO.getConfigParam3() != null && customizeConfigVO.getConfigParam3().intValue() > 0)
				{
					strConfigParam2 = Integer.toString(customizeConfigVO.getConfigParam3());
					alCallPenByBranRpt.add(1, strConfigParam3);
					callPendGenerateRpt(strOfficeCode,strConfigParam3,strPrimaryMailID3,alCallPenByBranRpt,
																							maintenanceManager);
				}//end of if(customizeConfigVO.getConfigParam3() != null && 
							//customizeConfigVO.getConfigParam3().intValue() > 0)
			}//end of for(int i=0; i<alCallList.size(); i++)
			//This has been configured be executed once in 24 hours. 1 min = 60,000 milliseconds
//			}//end of if(iHours == 2)					
			
		}catch(Exception e){
			new Exception(strCallPendingError);
			TTKCommon.logStackTrace(e);
		}//end of catch(Exception e)		
	}//end of run method
	
	/** This method Generate the callpending Report branch wise everyday 
	 * @param strOfficeCode String Office Code
	 * @param iConfigParam int Cofig Param
	 * @param strPrimaryMailID String Primary Mail ID
	 * @param alCallPenByBranRpt ArrayList which contains Config params and primary mail ids
	 * @param maintenanceManager MaintenanceManager Obect 
	 */	
	public static void callPendGenerateRpt(String strOfficeCode, String strConfigParam, String strPrimaryMailID, 
							ArrayList alCallPenByBranRpt, MaintenanceManager maintenanceManager) throws TTKException
	{
		try{
			String strFileName ="";
			HashMap hashMap= new HashMap();
			JasperReport jasperReport = null;
			JasperPrint  jasperPrint = null;
			jasperReport = JasperCompileManager.compileReport("generalreports/PendingCallReport.jrxml");
			String strMsgID = "";
			String strDate = TTKCommon.getServerDate();	
			TTKReportDataSource ttkReportDataSource = null;
			String strAddiParam = "";
			if(strOfficeCode.equals("") && strPrimaryMailID.equals("") )
			{
				strMsgID = "CALL_PENDING_REPORT";
				strFileName = TTKPropertiesReader.getPropertyValue("callpendingdir")+"CALL_PENDING_"+strDate+".pdf";
				ttkReportDataSource = new TTKReportDataSource("CallPenRpt");				
			}//end of if(strOfficeCode.equals("") && strPrimaryMailID.equals(""))
			else{
				strMsgID = "CALL_PENDING_BRANCHWISE_REPORT";
				strAddiParam =strConfigParam+"_"+strOfficeCode;
				strFileName = TTKPropertiesReader.getPropertyValue("callpendingdir")+"CALL_PENDING_"+
													strAddiParam+"_"+strDate+".pdf";
				ttkReportDataSource = new TTKReportDataSource("CallPenByBranRpt", alCallPenByBranRpt);				
			}//end of else
			if(ttkReportDataSource.getResultData().next())
			{
				ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
				JasperExportManager.exportReportToPdfFile(jasperPrint,strFileName);
				maintenanceManager.insertRecord(strMsgID,strAddiParam,strPrimaryMailID);
			}//end of if(ttkReportDataSource.getResultData().next()))		
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strCallPendingError);
		}//end of catch(Exception exp)
	}//end of  callPendGenerateRpt()
	
	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
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
			throw new TTKException(exp, strCallPendingError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	
}//end of ReportScheduler
