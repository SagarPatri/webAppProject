/**
 * @ (#) CustomerCallBackSchedular.java 29 May 2015
 * Project      : TTK HealthCare Services
 * File         : CustomerCallBackSchedular.java
 * Author       : Kishor kumar S H
 * Company      : RCS TEchnologies
 * Date Created : 29 May 2015
 *
 * @author       :  Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */


package com.ttk.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

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

public class CustomerCallBackSchedular implements Job {
	
	private static final String strCallPendingError="ScheduledCallPendingReport";
	
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;	
		try
		{	
			maintenanceManager = this.getMaintenanceManagerObject();	
			//callPendGenerateRpt("","","",new ArrayList(),maintenanceManager);
			ArrayList<Object> alCallList = new ArrayList<Object>();
		//	alCallList = maintenanceManager.getCallPendingByAttendedBy();
			CustomizeConfigVO customizeConfigVO = null;
			long tempSeqId	=	0;
			
			//gg			for(int i=0; i<alCallList.size(); i++)
			//gg			{
				customizeConfigVO = new CustomizeConfigVO();
				//gg customizeConfigVO =(CustomizeConfigVO)alCallList.get(0);
				String strAttendedBy = "";
				String strCallLOgSeqId = "";
				String strPrimaryMailID1= customizeConfigVO.getPrimaryMailID1();
				ArrayList<Object> alCallPenByBranRpt = new ArrayList<Object>();
				alCallPenByBranRpt.add(0,strAttendedBy);	
				
				HashSet<Long> hashSet 	=	new HashSet<Long>();
				hashSet.add(customizeConfigVO.getAddedBy());
				//gg/*if(customizeConfigVO.getAddedBy() != null)
				//gg{*/
					tempSeqId		=	0;
					alCallPenByBranRpt.add(1,strPrimaryMailID1);
					alCallPenByBranRpt.add(2,customizeConfigVO.getAddedBy());
					alCallPenByBranRpt.add(3,tempSeqId);
					callPendGenerateRpt(strAttendedBy,strCallLOgSeqId,strPrimaryMailID1,alCallPenByBranRpt,
																							maintenanceManager);
					//gg}//end of if(customizeConfigVO.getConfigParam1() != null && 
							//customizeConfigVO.getConfigParam1().intValue() > 0)
				
			//}//end of for(int i=0; i<alCallList.size(); i++)
			//This has been configured be executed once in 24 hours. 1 min = 60,000 milliseconds
					//gg			}//end of if(iHours == 2)					
			
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
			String strFileName ="",strFilePath="";
			HashMap hashMap= new HashMap();
			JasperReport jasperReport = null;
			JasperPrint  jasperPrint = null;
			//maintenanceManager.insertRecord("CALL_PENDING_REPORT",alCallPenByBranRpt,"0");
			
			jasperReport = JasperCompileManager.compileReport("generalreports/CustomerCallBack.jrxml");
			String strMsgID = "";
			//String strDate = TTKCommon.getServerDate();
			String strDate = new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date());
			TTKReportDataSource ttkReportDataSource = null;
			String strAddiParam = "";
			
			
			strMsgID = "CALL_PENDING_REPORT";
			
			strFileName = "CALL_PENDING_"+strDate+".pdf";
			strFilePath = TTKPropertiesReader.getPropertyValue("callpendingdir")+strFileName;
			ttkReportDataSource = new TTKReportDataSource("CustCallBack",alCallPenByBranRpt.get(3).toString());	
			
			if(ttkReportDataSource.getResultData().next())
			{
				ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
				JasperExportManager.exportReportToPdfFile(jasperPrint,strFilePath);
				maintenanceManager.insertCustomerRecord(strMsgID,alCallPenByBranRpt,strPrimaryMailID,strFileName);
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
}//end of CustomerCallBackSchedular
