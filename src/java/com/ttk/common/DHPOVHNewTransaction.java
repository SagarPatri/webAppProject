
/**
 * @ (#) DHPOVHNewTransaction.java Feb 27 2017
 * Project      : TTK HealthCare Services
 * File         : DHPOVHNewTransaction.java
 * Author       : Nagababu Kamadi
 * Company      : RCS TEchnologies
 * Date Created : Feb 27 2017
 *
 * @author       : Nagababu Kamadi
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import java.io.StringReader;
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

public class DHPOVHNewTransaction implements Job {
	
	private static final String strDHPOVHNewTransactionError="DHPOVHNewTransaction";
	private static final Logger log = Logger.getLogger( DHPOVHNewTransaction.class );
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;
		try
		{	
		if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.VH.NT.RUN.MODE"))){
			
			log.info("DHPOVHNewTransaction Started..........");
			
			maintenanceManager = this.getMaintenanceManagerObject();	
			SAXReader saxReader	=	new SAXReader();
		    String userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.userID");
		    String password=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.password");
		    String licenceNO=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.licence.NO");
			ValidateTransactions transactions 	=	new ValidateTransactions();
			ValidateTransactionsSoap soapDHPOStub		=	transactions.getValidateTransactionsSoap();
			Holder<String> errorMessage				=	new Holder<String>();
			//Holder<Integer> getNewPriorAuthorizationTransactionsResult	=	new Holder<Integer>();
			Holder<Integer> getNewTransactionsResult	=	new Holder<Integer>();			
			Holder<Integer> downloadTransactionFileResult	=	new Holder<Integer>();
			Holder<String> xmlTransaction				=	new Holder<String>();			
			
			//check the transaction in DHPO
			//soapDHPOStub.getNewPriorAuthorizationTransactions(userID,password, getNewPriorAuthorizationTransactionsResult, xmlTransaction, errorMessage);
			soapDHPOStub.getNewTransactions(userID,password, getNewTransactionsResult, xmlTransaction, errorMessage);
			//soapDHPOStub.searchTransactions(userID, password, direction, callerLicense, ePartner, transactionID, transactionStatus, transactionFileName, transactionFromDate, transactionToDate, minRecordCount, maxRecordCount, searchTransactionsResult, xmlTransaction, errorMessage);
			
			String strXmlTransaction=xmlTransaction.value;
		
			if(strXmlTransaction!=null&&strXmlTransaction.length()>1){
				
				StringReader strReader1=new StringReader(strXmlTransaction);
				Document document1=	saxReader.read(strReader1);
			
				List<Node> nodeList=document1.selectNodes("Files/File");
				if(nodeList!=null&&nodeList.size()>0){
					int i=1;
					for(Node node:nodeList){
					if("False".equalsIgnoreCase(node.valueOf("@IsDownloaded"))){
						
						try{
							
						Holder<byte[]> fileContent=new Holder<byte[]>();
						
						Holder<String> fileName				=	new Holder<String>(node.valueOf("@FileName"));
						Holder<String> errorMessage2				=	new Holder<String>();
						Holder<String> errorMessage3				=	new Holder<String>();
						Holder<Integer> setTransactionDownloadedResult				=	new Holder<Integer>();
						
						String fileID=node.valueOf("@FileID");
						//log.info("downloadTransactionFile()...started");
						//download the transaction in DHPO
						
						soapDHPOStub.downloadTransactionFile(userID,password,fileID, downloadTransactionFileResult,fileName, fileContent, errorMessage2);
						
						if(downloadTransactionFileResult!=null&&downloadTransactionFileResult.value!=null){							
							
							if(fileContent!=null&&fileContent.value!=null){
								String strFileContent=new String(fileContent.value);						
																
								
								DhpoWebServiceVO dhpoWebServiceVO=new DhpoWebServiceVO();
								dhpoWebServiceVO.setDhpoLicenceNO(licenceNO);
								dhpoWebServiceVO.setFileID(fileID);
								dhpoWebServiceVO.setFileName(fileName.value);								
								dhpoWebServiceVO.setXmlFileContent(strFileContent);								
								dhpoWebServiceVO.setTransactionResult(downloadTransactionFileResult.value);
								dhpoWebServiceVO.setErrorMessage(errorMessage2.value);
								
							if(downloadTransactionFileResult.value==1||downloadTransactionFileResult.value==0){
									dhpoWebServiceVO.setDownloadStatus("YES");
								}else{
									dhpoWebServiceVO.setDownloadStatus("NO");
								}
							
							
							if(strFileContent.contains("Claim.Submission")||strFileContent.contains("ClaimSubmission.xsd")){
								dhpoWebServiceVO.setFileType("CLM");							
								
								Object[] resObj=maintenanceManager.uploadDhpoNewTransactonDetails(dhpoWebServiceVO);
								//set the downloaded file as true in DHPO
								soapDHPOStub.setTransactionDownloaded(userID,password, fileID, setTransactionDownloadedResult, errorMessage3);
							}else	if(strFileContent.contains("Prior.Request")||strFileContent.contains("PriorRequest.xsd")){
								
								dhpoWebServiceVO.setFileType("PAT");
															
								Document priorDoc=	new SAXReader().read(new StringReader(strFileContent));

									List actNode=priorDoc.selectNodes("//Activity/Type[text()!='5']");	
									
									if(actNode!=null&&actNode.size()>0){
									
										Object[] resObj=maintenanceManager.saveDhpoPreauthDownloadDetails(dhpoWebServiceVO);
										
										//set the downloaded file as true in DHPO
										soapDHPOStub.setTransactionDownloaded(userID,password, fileID, setTransactionDownloadedResult, errorMessage3);
									}
					} 
								
							
						}//if(fileContent!=null&&fileContent.value!=null){
						}//if(downloadTransactionFileResult!=null&&downloadTransactionFileResult.value!=null){
					
						}catch(Exception e){
							e.printStackTrace();
						}
						
						}//if("False".equalsIgnoreCase(node.valueOf("@IsDownloaded"))){
					i++;
					
				}//for(Node node:nodeList){				
					
				}//if(nodeList!=null&&nodeList.size()>0){
			}//if(strXmlTransaction!=null&&strXmlTransaction.length()>1){				
			
		log.info("DHPOVHNewTransaction  Ended..........");
			}
		}catch(Exception e){
			e.printStackTrace();
			new Exception(strDHPOVHNewTransactionError);
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
			throw new TTKException(exp, strDHPOVHNewTransactionError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	
}//end of DHPONewTransaction
