package com.ttk.common.webservices;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.action.enrollment.MemberUploadAction;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.common.UploadHaadMemberHelperClass;
import com.ttk.dto.common.DhpoWebServiceVO;

public class UploadMemberToHaadScheduler implements Job 
{
	private static final String strHaadMemberFileUploadError="HaadMemberRegisterFileUpload";
	@Override
	public void execute(JobExecutionContext arg0)  
	{
		
		MaintenanceManager maintenanceManager = null;
		
		int result=0;
	List<DhpoWebServiceVO> uploadRAList = null;
		String message = null;
	try
	{
		UploadHaadMemberHelperClass uploadHaadMemberHelperClass=new UploadHaadMemberHelperClass();
		maintenanceManager=getMaintenanceManagerObject();
		uploadRAList=maintenanceManager.getRemittanceAdviceDHPOFileUpload();
		
        //if(uploadRAList!=null)uploadHaadMemberHelperClass.UploadActiveMemberToHaad(uploadRAList);
       
		}
		catch (TTKException e) 
		   {
			TTKCommon.logStackTrace(e);
		   }

	}
	
	
	
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
			throw new TTKException(exp, strHaadMemberFileUploadError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	

	
}
