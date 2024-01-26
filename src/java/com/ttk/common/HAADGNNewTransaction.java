
/**
 * @ (#) HAADGNNewTransaction.java Feb 27 2017
 * Project      : TTK HealthCare Services
 * File         : DHPOGNNewTransaction.java
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.naming.InitialContext;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.business.common.messageservices.CommunicationManager;


public class HAADGNNewTransaction implements Job {
	private static final String strEmailScheduler="EmailScheduler";
	private static final String strHAADGNNewTransactionError="HAADNewTransaction";
	private static final Logger log = Logger.getLogger( HAADGNNewTransaction.class );
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;
		CommunicationManager communicationManager = null;
		try
		{	
		if(new Boolean(TTKPropertiesReader.getPropertyValue("HAAD.WS.GN.NT.RUN.MODE"))){
			
			log.info("HAADGNNewTransaction Started..........");
			
			String msgId = "WEBSERVICE_CLAIM_EXCEPTION";
		
			maintenanceManager = this.getMaintenanceManagerObject();
			communicationManager  = this.getCommunicationManagerObject();
			SAXReader saxReader	=	new SAXReader();
		    String userID=TTKPropertiesReader.getPropertyValue("HAADWebService.GN.userID");
		    String password=TTKPropertiesReader.getPropertyValue("HAADWebService.GN.password");
		    String licenceNO=TTKPropertiesReader.getPropertyValue("HAADWebService.GN.licence.NO");
		    String haadNotProccessFilesPath=TTKPropertiesReader.getPropertyValue("haadNotProccessFiles");
		    com.ttk.business.webservice.haad.WebservicesSoap soapHAADStub		=	DHPOStub.getHaadStub();
			Holder<String> errorMessage				=	new Holder<String>();
			//Holder<Integer> getNewPriorAuthorizationTransactionsResult	=	new Holder<Integer>();
			Holder<Integer> getNewTransactionsResult	=	new Holder<Integer>();			
			Holder<Integer> downloadTransactionFileResult	=	new Holder<Integer>();
			Holder<String> xmlTransaction				=	new Holder<String>();			
			
			//check the transaction in DHPO
			//soapDHPOStub.getNewPriorAuthorizationTransactions(userID,password, getNewPriorAuthorizationTransactionsResult, xmlTransaction, errorMessage);
			soapHAADStub.getNewTransactions(userID,password, getNewTransactionsResult, xmlTransaction, errorMessage);
			String strXmlTransaction=xmlTransaction.value;
          	if(strXmlTransaction!=null&&strXmlTransaction.length()>1){
				
				StringReader strReader1=new StringReader(strXmlTransaction);
				Document document1=	saxReader.read(strReader1);
			
				List<Node> nodeList=document1.selectNodes("Files/File");
				if(nodeList!=null&&nodeList.size()>0){

					int i=1;
					
					for(Node node:nodeList){

			//	if("False".equalsIgnoreCase(node.valueOf("@IsDownloaded"))){
							String fileID=node.valueOf("@FileID");
							String fileName=node.valueOf("@FileName");
							try{
								
								ByteArrayInputStream bais=getDownloadedFile(soapHAADStub, userID, password, licenceNO, fileName, fileID);
								if(fileName.toLowerCase().endsWith(".xml")){
									try
									{
										saveDownloadedFile(soapHAADStub, userID, password, maintenanceManager, licenceNO, fileName, fileID,bais,"xml");
									}
									catch(Exception exception)
									{
										String issueType = "PARSING";
										communicationManager.sendFailXmlAlert(msgId,issueType,fileName,fileID,licenceNO);
										throw exception;
									}
								}else if(fileName.toLowerCase().endsWith(".zip")){
									
								
									 ArrayList<Object[]> zipFileList=getFiles(bais);
									int fileCont=1;
									 for(Object[] objArr:zipFileList) {
										String zipFileID=fileID+"-"+fileCont;
										String zipFileName=(String)objArr[0];	
							             try
							             {
							            	 saveDownloadedFile(soapHAADStub, userID, password, maintenanceManager, licenceNO, zipFileName, zipFileID,(ByteArrayInputStream)objArr[1],"zip");
							             }
							             catch(Exception exception)
							             {
							            	 String issueType = "PARSING";
							            	 communicationManager.sendFailXmlAlert(msgId,issueType,fileName,fileID,licenceNO);
											 throw exception;
							             }
										 fileCont++;
									 }
									 setFileIDFlagTxDownloaded(soapHAADStub, userID, password, fileID);
								}
								
									else{
											String issueType = "DOWNLOAD";
											FileOutputStream fout=new FileOutputStream(haadNotProccessFilesPath+fileID+"-Vidal-"+fileName);    
											byte[] respsData=new byte[bais.available()];
										 
											bais.read(respsData);
											fout.write(respsData);
											fout.close(); 
											setFileIDFlagTxDownloaded(soapHAADStub, userID, password, fileID);
											communicationManager.sendFailXmlAlert(msgId,issueType,fileName,fileID,licenceNO);
									}		
							
							}catch(Exception e){
								e.printStackTrace();
							}
							
				//	}//if("False".equalsIgnoreCase(node.valueOf("@IsDownloaded"))){
						i++;
						
						
					}//for(Node node:nodeList){				
					
				}//if(nodeList!=null&&nodeList.size()>0){
			}//if(strXmlTransaction!=null&&strXmlTransaction.length()>1){				
			
		log.info("HAADGNNewTransaction  Ended..........");
			}
		}catch(Exception e){
			e.printStackTrace();
			new Exception(strHAADGNNewTransactionError);
			TTKCommon.logStackTrace(e);
		}//end of catch(Exception e)		
	}//end of run method
	
	
	private static ByteArrayInputStream getDownloadedFile(com.ttk.business.webservice.haad.WebservicesSoap soapHAADStub,String userID,String password,String licenceNO,String strFileName,String fileID){
		Holder<byte[]> fileContent=new Holder<byte[]>();
		byte[] fileData=null;
		Holder<String> fileName				=	new Holder<String>(strFileName);
		Holder<String> errorMessage2				=	new Holder<String>();
		Holder<Integer> downloadTransactionFileResult2	=	new Holder<Integer>();
		//download the transaction in DHPO
	
		soapHAADStub.downloadTransactionFile(userID,password,fileID, downloadTransactionFileResult2,fileName, fileContent, errorMessage2);
	
		if(downloadTransactionFileResult2!=null&&downloadTransactionFileResult2.value!=null){
			fileData=fileContent.value;
		}
	if(fileData==null){
		fileData="".getBytes();
	}
				
			return new ByteArrayInputStream(fileData);
	}
	
	
	private static void saveDownloadedFile(com.ttk.business.webservice.haad.WebservicesSoap soapHAADStub ,String userID,String password,MaintenanceManager maintenanceManager,String licenceNO,String fileName,String fileID,ByteArrayInputStream bais,String fileExtns)throws Exception
	{
		SAXReader saxReader2=new SAXReader();
		Document PATorCLMdoc=saxReader2.read(bais);
	DhpoWebServiceVO dhpoWebServiceVO=new DhpoWebServiceVO();
	dhpoWebServiceVO.setDhpoLicenceNO(licenceNO);

	dhpoWebServiceVO.setFileID(fileID);
	dhpoWebServiceVO.setFileName(fileName);	
	dhpoWebServiceVO.setXmlFileContent(PATorCLMdoc.asXML());							
	dhpoWebServiceVO.setTransactionResult(1);
	dhpoWebServiceVO.setErrorMessage("");
	dhpoWebServiceVO.setDownloadStatus("YES");
	
		
	//if(strFileContent.contains("Claim.Submission")||strFileContent.contains("ClaimSubmission.xsd")){
	if("Claim.Submission".equals(PATorCLMdoc.getRootElement().getName())||"ClaimSubmission.xsd".equals(PATorCLMdoc.getRootElement().getName())){
	//	System.out.println("submission type : "+PATorCLMdoc.getRootElement().getName());
		dhpoWebServiceVO.setFileType("CLM");
	
		Object[] resObj=maintenanceManager.uploadDhpoGlobalNetNewTransactonDetails(dhpoWebServiceVO);
		System.out.println("--------------end claim..............");
		
		if("xml".equals(fileExtns)){
	//	soapDHPOStub.setTransactionDownloaded(userID,password, fileID, setTransactionDownloadedResult, errorMessage3);
			setFileIDFlagTxDownloaded(soapHAADStub, userID, password, fileID);
		}
	} else if("Prior.Request".equals(PATorCLMdoc.getRootElement().getName())||"PriorRequest.xsd".equals(PATorCLMdoc.getRootElement().getName())){
			dhpoWebServiceVO.setFileType("PAT");
			System.out.println("submission type : "+PATorCLMdoc.getRootElement().getName());
			List actNode=PATorCLMdoc.selectNodes("//Activity/Type[text()!='5']");	
			
			if(actNode!=null&&actNode.size()>0){
			
				Object[] resObj=maintenanceManager.saveDhpoGlobalnetPreauthDownloadDetails(dhpoWebServiceVO);
				if("xml".equals(fileExtns)){
					setFileIDFlagTxDownloaded(soapHAADStub, userID, password, fileID);
				}
	      }
	
	}//if("Prior.Request".equals(PATorCLMdoc.getRootElement())||"PriorRequest.xsd".equals(PATorCLMdoc.getRootElement())){
	
	}	
	public static void setFileIDFlagTxDownloaded(com.ttk.business.webservice.haad.WebservicesSoap soapHAADStub ,String userID,String password,String fileID){
		Holder<String> errorMessage3				=	new Holder<String>();
		Holder<Integer> setTransactionDownloadedResult				=	new Holder<Integer>();
		
		soapHAADStub.setTransactionDownloaded(userID,password, fileID, setTransactionDownloadedResult, errorMessage3);
	    
	}
	
	public static ArrayList<Object[]> getFiles(ByteArrayInputStream bais)throws Exception {
		
		ArrayList<Object[]> alFiles=new ArrayList<>();	
		int i=0;
		byte[] buffer = new byte[1024];
	    	
	    	ZipInputStream zis =new ZipInputStream(bais);
	        	//get the zipped file list entry
	        	ZipEntry ze = zis.getNextEntry();
	        	while(ze!=null){
	        	   String fileName = ze.getName();
	        	   if(  fileName.toUpperCase().endsWith(".XML") ||  fileName.toLowerCase().endsWith(".xml"))
	        	   {
	        	   i = i + 1;
	        	ByteArrayOutputStream baos=new ByteArrayOutputStream();
	        	zis.available();
	              int len;
	              while ((len = zis.read(buffer)) > 0) {
	            	  baos.write(buffer, 0, len);
	              }
	              
	              alFiles.add(new Object[] {fileName,new ByteArrayInputStream(baos.toByteArray())}); 
	        	   }
	                ze = zis.getNextEntry();	        	
	                }
	            zis.closeEntry();
	        	zis.close();
	return alFiles;
	}
	
	
	
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
			throw new TTKException(exp, strHAADGNNewTransactionError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()
	
	// for email-trigger
	private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strEmailScheduler);
		}//end of catch
		return communicationManager;
	}//end getCommunicationManagerObject()

}//end of DHPONewTransaction
