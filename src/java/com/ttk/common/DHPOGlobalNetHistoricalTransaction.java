
/**
 * @ (#) DHPOGlobalNetHistoricalNewTransaction.java JAN 05 2017
 * Project      : Project X
 * File         : DHPOGlobalNetNewTransaction.java
 * Author       : Nagababu Kamadi
 * Company      : RCS TEchnologies
 * Date Created : JAN 05 2017
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

public class DHPOGlobalNetHistoricalTransaction implements Job {
	
	private static final String strDHPOGNHitoricalTransactionError="DHPOGlobalNetHistoricalTransaction";
	private static final Logger log = Logger.getLogger( DHPOGlobalNetHistoricalTransaction.class );
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;
		FileWriter fileWriter=null;
		try
		{	
			if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.GNHT.WS.RUN.MODE"))){
			
			log.info("DHPO Historical GlobalNet Transaction Started..........");
			String strNewTransactionPath=	TTKPropertiesReader.getPropertyValue("path.GN.dhpoHistoricalTransaction");
			
			File newTransactionFile=new File(strNewTransactionPath);
			if(!newTransactionFile.exists())newTransactionFile.mkdirs();
			
			SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
			String logFileName=dateFormat.format(new Date());
			fileWriter=new FileWriter(strNewTransactionPath+"/"+logFileName+".txt",true);			
			
			maintenanceManager = this.getMaintenanceManagerObject();	
			SAXReader saxReader	=	new SAXReader();
		    String userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.userID");
		    String password=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.password");
			
			ValidateTransactions transactions 	=	new ValidateTransactions();
			ValidateTransactionsSoap soapDHPOStub		=	transactions.getValidateTransactionsSoap();
			Holder<String> errorMessage				=	new Holder<String>();
			//Holder<Integer> getNewPriorAuthorizationTransactionsResult	=	new Holder<Integer>();
			Holder<Integer> getNewTransactionsResult	=	new Holder<Integer>();			
			Holder<Integer> downloadTransactionFileResult	=	new Holder<Integer>();
			Holder<String> xmlTransaction				=	new Holder<String>();
			
			//log data file writting
			fileWriter.write("New Transaction::"+new SimpleDateFormat("YYYY-MM-dd-HH-mm-sss aa").format(new Date()));
			fileWriter.write("\n");
			fileWriter.write("=================================================");
			fileWriter.write("\n");
			fileWriter.write("getNewTransactions()...started");
			fileWriter.write("\n");
			
			//check the transaction in DHPO
			//soapDHPOStub.getNewPriorAuthorizationTransactions(userID,password, getNewPriorAuthorizationTransactionsResult, xmlTransaction, errorMessage);
			//soapDHPOStub.getNewTransactions(userID,password, getNewTransactionsResult, xmlTransaction, errorMessage);
			
			String startDate="/10/2016";
			String endDate="/10/2016";

			for(int i=0;i<30;i++){
				
				System.out.println("Histarical Download Started...startDate::"+(i+1)+startDate+" endDate::"+(i+2)+endDate);
				
				fileWriter.write("\n");
				fileWriter.write("Histarical Download Started...");
				fileWriter.write("startDate::"+(i+1)+startDate+" endDate::"+(i+2)+endDate);
				fileWriter.write("================================================================");
				fileWriter.write("\n");
				Holder<Integer> searchTransactionsResult=	new Holder<Integer>();
				Holder<String> foundTransactions=	new Holder<String>();
				
				soapDHPOStub.searchTransactions(userID, password, 2, null, null, 2, 2, null, (i+1)+startDate, (i+2)+endDate, -1, -1, searchTransactionsResult, foundTransactions, errorMessage);
			   
			
			
			String strXmlTransaction=foundTransactions.value;			
			
			
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
					
					if("true".equalsIgnoreCase(node.valueOf("@IsDownloaded"))){
						
						try{
							
						Holder<byte[]> fileContent=new Holder<byte[]>();
						
						Holder<String> fileName				=	new Holder<String>(node.valueOf("@FileName"));
						Holder<String> errorMessage2				=	new Holder<String>();
							
						String fileID=node.valueOf("@FileID");					
						
						soapDHPOStub.downloadTransactionFile(userID,password,fileID, downloadTransactionFileResult,fileName, fileContent, errorMessage2);
												
						
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
								
						if(strFileContent.contains("Claim.Submission")||strFileContent.contains("ClaimSubmission.xsd")){
							
							
							DhpoWebServiceVO dhpoWebServiceVO=new DhpoWebServiceVO();
							
							dhpoWebServiceVO.setFileID(fileID);
							dhpoWebServiceVO.setFileName(fileName.value);
							dhpoWebServiceVO.setXmlFileContent(strFileContent);			
						    dhpoWebServiceVO.setFileType("CLM");
							
							dhpoWebServiceVO.setTransactionResult(downloadTransactionFileResult.value);
							dhpoWebServiceVO.setErrorMessage(errorMessage2.value);
							
							if(downloadTransactionFileResult.value==1||downloadTransactionFileResult.value==0){
								dhpoWebServiceVO.setDownloadStatus("YES");
								
								dhpoWebServiceVO.setBifurcationYN("N");
								//calling procedure
								Object[] resObj=maintenanceManager.uploadDhpoGlobalNetNewTransactonDetails(dhpoWebServiceVO);
								
								fileWriter.write("\n");
								fileWriter.write("Insert Status::"+resObj[0]);
								fileWriter.write("\n");
								
									//soapDHPOStub.setTransactionDownloaded(userID,password, fileID, setTransactionDownloadedResult, errorMessage3);
								noOfDownload++;
								
								fileWriter.write("\n");
							}else{
								dhpoWebServiceVO.setDownloadStatus("NO");
								maintenanceManager.uploadDhpoGlobalNetNewTransactonDetails(dhpoWebServiceVO);
							}
							}//if(strFileContent.contains("Claim.Submission")&&strFileContent.contains("ClaimSubmission.xsd"))
						
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
				
			fileWriter.write("\n");
			
			fileWriter.write("startDate::"+(i+1)+startDate+" endDate::"+(i+2)+endDate);
			fileWriter.write("Histarical Download Ended");
			fileWriter.write("==================================================");
			fileWriter.write("\n");
			System.out.println("Histarical Download Started...startDate::"+(i+1)+startDate+" endDate::"+(i+2)+endDate);
			}//for(int i=0;i<29;i++){
			
			fileWriter.write("======================================================================================================================");
			fileWriter.write("\n");
			fileWriter.flush();
		if(fileWriter!=null)fileWriter.close();
		
		log.info("DHPO Historical GlobalNet Transaction Ended..........");
		}//if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.GNHT.WS.RUN.MODE"))){
		}catch(Exception e){
			try{
				fileWriter.write("Error Occurred::"+e.getMessage());
				fileWriter.write("\n");
				fileWriter.flush();			
				if(fileWriter!=null)fileWriter.close();
			}catch(Exception ee){}
			new Exception(strDHPOGNHitoricalTransactionError);
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
			throw new TTKException(exp, strDHPOGNHitoricalTransactionError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	
}//end of DHPONewTransaction
