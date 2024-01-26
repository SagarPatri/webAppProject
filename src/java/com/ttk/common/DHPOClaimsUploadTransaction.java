
/**
 * @ (#) DHPOClaimsUploadTransaction.java Feb 1 2016
 * Project      : TTK HealthCare Services
 * File         : DHPOClaimsUploadTransaction.java
 * Author       : Nagababu Kamadi
 * Company      : RCS TEchnologies
 * Date Created : Feb 1 2016
 *
 * @author       : Nagababu Kamadi
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.InitialContext;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.business.webservice.dhpo.ValidateTransactions;
import com.ttk.business.webservice.dhpo.ValidateTransactionsSoap;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.DhpoWebServiceVO;

public class DHPOClaimsUploadTransaction implements Job {
	
	private static final String strDHPOClaimsUploadTransactionError="DHPOClaimsUploadTransaction";
	private static final Logger log = Logger.getLogger( DHPOClaimsUploadTransaction.class );
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;
		FileWriter fileWriter=null;
		try
		{	
			log.info("DHPO Claims Upload Transaction Started..........");
			
			String strClaimSubmissionUploadPath=	TTKPropertiesReader.getPropertyValue("path.dhpoClaimSubmissionUpload");
			
			File claimSubmissionUploadFile=new File(strClaimSubmissionUploadPath);
			if(!claimSubmissionUploadFile.exists())claimSubmissionUploadFile.mkdirs();
			
			SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
			String logFileName=dateFormat.format(new Date());
			fileWriter=new FileWriter(strClaimSubmissionUploadPath+"/"+logFileName+".txt",true);
			
			maintenanceManager = this.getMaintenanceManagerObject();	
		    String userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.userID");
		    String password=TTKPropertiesReader.getPropertyValue("DHPOWebService.password");
			
		    HashMap<String, byte[]>  proccessedFiles=maintenanceManager.getClaimsProccessedFiles();
		    if(proccessedFiles!=null&proccessedFiles.size()>=1){
		    	
		    	 Set<Entry<String, byte[]>> set=proccessedFiles.entrySet();
		    	//creating stub
		    	ValidateTransactions transactions 	=	new ValidateTransactions();
				ValidateTransactionsSoap soapDHPOStub		=	transactions.getValidateTransactionsSoap();
				Holder<String> errorMessage				=	new Holder<String>();
				Holder<Integer> uploadTransactionResult	=	new Holder<Integer>();
				Holder<byte[]> errorReport				=	new Holder<byte[]>();
		   for(Entry<String, byte[]> entry:set){
			 
			   //log data file writting
				fileWriter.write("Claims Upload Transaction::"+new SimpleDateFormat("YYYY-MM-dd-HH-mm-sss aa").format(new Date()));
				fileWriter.write("\n");
				fileWriter.write("=================================================");
				fileWriter.write("\n");
				fileWriter.write("uploadTransaction()...started");
				fileWriter.write("\n");
				//uploading procced files in DHPO
			   soapDHPOStub.uploadTransaction(userID, password,entry.getValue() , entry.getKey(), uploadTransactionResult, errorMessage, errorReport);
			   
			    fileWriter.write("uploadTransaction()...ended");
				fileWriter.write("\n");
				fileWriter.write("uploadTransactionResult::"+uploadTransactionResult.value);
				fileWriter.write("\n");
				fileWriter.write("errorMessage::"+errorMessage.value);
				fileWriter.write("\n");
				fileWriter.write("errorReport::"+new String(errorReport.value));
				fileWriter.write("\n");
				
				DhpoWebServiceVO dhpoWebServiceVO=new DhpoWebServiceVO();
				dhpoWebServiceVO.setFileName(entry.getKey());
				dhpoWebServiceVO.setTransactionResult(uploadTransactionResult.value);
				dhpoWebServiceVO.setErrorMessage(errorMessage.value);
				dhpoWebServiceVO.setErrorReport(errorReport.value);
				dhpoWebServiceVO.setFileType("CLM");
				//Updating the upload file status in our DB
				Object[]result=maintenanceManager.updateDhpoUplodedFileStatus(dhpoWebServiceVO);
				fileWriter.write("Updated Claims Status In Our DB::"+result[0]);
				fileWriter.write("\n");
		   }
				
		    
		    }
			
				fileWriter.write("======================================================================================================================");
				fileWriter.write("\n");
				fileWriter.flush();
			if(fileWriter!=null)fileWriter.close();
		log.info("DHPO Claims Upload Transaction Ended..........");
		}catch(Exception e){
			try{
				fileWriter.write("Error Occurred::"+e.getMessage());
				fileWriter.write("\n");
				fileWriter.flush();			
				if(fileWriter!=null)fileWriter.close();
			}catch(Exception ee){}
			new Exception(strDHPOClaimsUploadTransactionError);
			TTKCommon.logStackTrace(e);
		}//end of catch(Exception e)		
	}//end of run method
	
	
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
			throw new TTKException(exp, strDHPOClaimsUploadTransactionError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	
}//end of DHPOClaimsUploadTransaction
