
/**
 * @ (#) DHPOClaimDownload.java Feb 1 2016
 * Project      : TTK HealthCare Services
 * File         : DHPONewTransaction.java
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
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.business.webservice.dhpo.ValidateTransactions;
import com.ttk.business.webservice.dhpo.ValidateTransactionsSoap;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.DhpoWebServiceVO;

public class DHPOClaimDownload implements Job {
	
	private static final String strDHPOClaimDownloadError="DHPOClaimDownload";
	private static final Logger log = Logger.getLogger( DHPOClaimDownload.class );
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;
		FileWriter fileWriter=null;
		try
		{	
		
		
		if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.CD.WS.RUN.MODE"))){
			
			log.info("DHPO Claim Download Started..........");
			String strNewTransactionPath=	TTKPropertiesReader.getPropertyValue("path.dhpoClaimDownload");
			
			File newTransactionFile=new File(strNewTransactionPath);
			if(!newTransactionFile.exists())newTransactionFile.mkdirs();
			
			SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
			String logFileName=dateFormat.format(new Date());
			fileWriter=new FileWriter(strNewTransactionPath+"/"+logFileName+".txt",true);			
			
			maintenanceManager = this.getMaintenanceManagerObject();	
			SAXReader saxReader	=	new SAXReader();
		    String userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.userID");
		    String password=TTKPropertiesReader.getPropertyValue("DHPOWebService.password");
			
			ValidateTransactions transactions 	=	new ValidateTransactions();
			ValidateTransactionsSoap soapDHPOStub		=	transactions.getValidateTransactionsSoap();
			Holder<String> errorMessage				=	new Holder<String>();
			//Holder<Integer> getNewPriorAuthorizationTransactionsResult	=	new Holder<Integer>();
			Holder<Integer> getNewTransactionsResult	=	new Holder<Integer>();			
			Holder<Integer> downloadTransactionFileResult	=	new Holder<Integer>();
			Holder<String> xmlTransaction				=	new Holder<String>();			
			
			
			int direction =2;//2 for not download
			String callerLicense=null;
			String ePartner=null;
			int transactionID=2;//2 for claims 
			int transactionStatus=1;
			String transactionFileName=null;
			String transactionFromDate=null;
			String transactionToDate=null;
			int minRecordCount =-1;
			int maxRecordCount =-1;
			Holder<Integer> searchTransactionsResult=new Holder<>();
			
			//log data file writting
			fileWriter.write("New Transaction::"+new SimpleDateFormat("YYYY-MM-dd-HH-mm-sss aa").format(new Date()));
			fileWriter.write("\n");
			fileWriter.write("=================================================");
			fileWriter.write("\n");
			fileWriter.write("getNewTransactions()...started");
			fileWriter.write("\n");
			
			//check the transaction in DHPO
			//soapDHPOStub.getNewPriorAuthorizationTransactions(userID,password, getNewPriorAuthorizationTransactionsResult, xmlTransaction, errorMessage);
			soapDHPOStub.getNewTransactions(userID,password, getNewTransactionsResult, xmlTransaction, errorMessage);
			//soapDHPOStub.searchTransactions(userID, password, direction, callerLicense, ePartner, transactionID, transactionStatus, transactionFileName, transactionFromDate, transactionToDate, minRecordCount, maxRecordCount, searchTransactionsResult, xmlTransaction, errorMessage);
			
			fileWriter.write("getNewTransactions()...ended");
			fileWriter.write("\n");
			fileWriter.write("getNewTransactionsResult::"+getNewTransactionsResult.value);
			fileWriter.write("\n");
			fileWriter.write("errorMessage::"+errorMessage.value);
			fileWriter.write("\n");
			
			String strXmlTransaction=xmlTransaction.value;
			
			fileWriter.write("Xml File::"+strXmlTransaction);
			fileWriter.write("\n");
			if(strXmlTransaction!=null&&strXmlTransaction.length()>1){
				
				StringReader strReader1=new StringReader(strXmlTransaction);
				Document document1=	saxReader.read(strReader1);
			
				List<Node> nodeList=document1.selectNodes("Files/File");
				if(nodeList!=null&&nodeList.size()>0){
					int noOfFiles=nodeList.size();
					int noOfDownload=0;
					fileWriter.write("\n");
					fileWriter.write("No Of Files::"+noOfFiles);
					fileWriter.write("\n");
					
					for(Node node:nodeList){
					
					if("False".equalsIgnoreCase(node.valueOf("@IsDownloaded"))){
						
						try{
							
						Holder<byte[]> fileContent=new Holder<byte[]>();
						
						Holder<String> fileName				=	new Holder<String>(node.valueOf("@FileName"));
						Holder<String> errorMessage2				=	new Holder<String>();
						Holder<String> errorMessage3				=	new Holder<String>();
						Holder<Integer> setTransactionDownloadedResult				=	new Holder<Integer>();
						
						String fileID=node.valueOf("@FileID");
						
						fileWriter.write("Download Transaction::"+new SimpleDateFormat("YYYY-MM-dd-HH-mm-sss aa").format(new Date()));
						fileWriter.write("\n");
						fileWriter.write("=================================================");
						fileWriter.write("\n");
						fileWriter.write("downloadTransactionFile()...started");
						fileWriter.write("\n");
						//log.info("downloadTransactionFile()...started");
						//download the transaction in DHPO
						
						soapDHPOStub.downloadTransactionFile(userID,password,fileID, downloadTransactionFileResult,fileName, fileContent, errorMessage2);
												
						fileWriter.write("downloadTransactionFile()...ended");
						fileWriter.write("\n");
						fileWriter.write("downloadTransactionFileResult::"+downloadTransactionFileResult.value);
						fileWriter.write("\n");
						fileWriter.write("FileID:: "+fileID+" FileName::"+node.valueOf("@FileName"));
						fileWriter.write("\n");
						fileWriter.write("errorMessage::"+errorMessage2.value);
						fileWriter.write("\n");
						if(downloadTransactionFileResult!=null&&downloadTransactionFileResult.value!=null){							
							
							if(fileContent!=null&&fileContent.value!=null){
								String strFileContent=new String(fileContent.value);
								
						if(strFileContent.contains("ClaimSubmission.xsd")||strFileContent.contains("Claim.Submission")){
																
								
							DhpoWebServiceVO dhpoWebServiceVO=new DhpoWebServiceVO();
							
							dhpoWebServiceVO.setFileID(fileID);
							dhpoWebServiceVO.setFileName(fileName.value);
							
							dhpoWebServiceVO.setXmlFileContent(strFileContent);
							
					        dhpoWebServiceVO.setFileType("CLM");
							
							dhpoWebServiceVO.setTransactionResult(downloadTransactionFileResult.value);
							dhpoWebServiceVO.setErrorMessage(errorMessage2.value);
							
							if(downloadTransactionFileResult.value==1||downloadTransactionFileResult.value==0){
								dhpoWebServiceVO.setDownloadStatus("YES");
								
								//calling procedure
								Object[] resObj=maintenanceManager.uploadDhpoNewTransactonDetails(dhpoWebServiceVO);
								
								fileWriter.write("\n");
								fileWriter.write("Insert Status::"+resObj[0]);
								fileWriter.write("\n");
								
								fileWriter.write("Setting Transaction Mode::"+new SimpleDateFormat("YYYY-MM-dd-HH-mm-sss aa").format(new Date()));
								fileWriter.write("\n");
								fileWriter.write("=================================================");
								fileWriter.write("\n");
								fileWriter.write("setTransactionDownloaded()...started");
								fileWriter.write("\n");
								
								//set the downloaded file as true in DHPO
								soapDHPOStub.setTransactionDownloaded(userID,password, fileID, setTransactionDownloadedResult, errorMessage3);
								
								noOfDownload++;
								fileWriter.write("setTransactionDownloaded()...ended");
								fileWriter.write("\n");
								fileWriter.write("setTransactionDownloadedResult::"+setTransactionDownloadedResult.value);
								fileWriter.write("\n");
								fileWriter.write("FileID:: "+fileID+" FileName::"+fileName.value);
								fileWriter.write("\n");
								fileWriter.write("errorMessage::"+errorMessage3.value);
								fileWriter.write("\n");
							}else{
								dhpoWebServiceVO.setDownloadStatus("NO");
								maintenanceManager.uploadDhpoNewTransactonDetails(dhpoWebServiceVO);
							}
						}//if(strFileContent.contains("ClaimSubmission.xsd")||strFileContent.contains("Claim.Submission")){
						}//if(fileContent!=null&&fileContent.value!=null){
						}//if(downloadTransactionFileResult!=null&&downloadTransactionFileResult.value!=null){
					
						}catch(Exception e){
							fileWriter.write("\n");
							fileWriter.write("Error Occurred::"+e.getMessage());
							fileWriter.write("\n");
							
						}
						}//if("False".equalsIgnoreCase(node.valueOf("@IsDownloaded"))){
				}//for(Node node:nodeList){
					
					fileWriter.write("\n");
					fileWriter.write("No Of Files Downloaded::"+noOfDownload);
					fileWriter.write("\n");
					
				}//if(nodeList!=null&&nodeList.size()>0){
			}//if(strXmlTransaction!=null&&strXmlTransaction.length()>1){
				fileWriter.write("======================================================================================================================");
				fileWriter.write("\n");
				fileWriter.flush();
			if(fileWriter!=null)fileWriter.close();
		log.info("DHPO Claim Download  Ended..........");
			}
		}catch(Exception e){
			try{
				fileWriter.write("Error Occurred::"+e.getMessage());
				fileWriter.write("\n");
				fileWriter.flush();			
				if(fileWriter!=null)fileWriter.close();
			}catch(Exception ee){}
			new Exception(strDHPOClaimDownloadError);
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
			throw new TTKException(exp, strDHPOClaimDownloadError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	
}//end of DHPONewTransaction
