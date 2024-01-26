package com.ttk.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.naming.InitialContext;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.business.webservice.dhpo.ValidateTransactionsSoap;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.DhpoWebServiceVO;

public class DhpoDownloadFiles {
	private static final String strEmailScheduler="EmailScheduler";
	private static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS"); 
	private static final String strDHPOGNNewTransactionError="DHPOGNNewTransaction";
	private static final Logger log = Logger.getLogger( DhpoDownloadFiles.class );
private static boolean  previousTaskStatus=false;
	private  DhpoDownloadFiles(){
		
	}
	
	public static void excecuteTask()throws Exception{
		
		 previousTaskStatus=true;//
		
		//System.out.println("*DHPO* Download Start Time="+dateFormat.format(new Date()));
		
		long startTime=new Date().getTime();
		
		MaintenanceManager maintenanceManager = null;
		CommunicationManager communicationManager = null;
		try{	
			
			log.info(" ---------------- Dhpo e-Preauth & e-calim  Schedular started----------------------");
			String msgId = "WEBSERVICE_CLAIM_EXCEPTION";
			maintenanceManager = getMaintenanceManagerObject();
			communicationManager  = getCommunicationManagerObject();
			SAXReader saxReader	=	new SAXReader();
		    String userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.userID");
		    String password=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.password");
		    String licenceNO=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.licence.NO");
		    String haadNotProccessFilesPath=TTKPropertiesReader.getPropertyValue("haadNotProccessFiles");
			ValidateTransactionsSoap soapDHPOStub		=	DHPOStub.getStub();
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
			if(strXmlTransaction!=null){
				
				StringReader strReader=new StringReader(strXmlTransaction);
				Document document=saxReader.read(strReader);
				List<Node> nodeList=document.selectNodes("Files/File");
					
				System.out.println("nodeList size = "+ nodeList.size());
				if(nodeList!=null&&nodeList.size()>0){
					int i=1;
					for(Node node:nodeList){
					if("False".equalsIgnoreCase(node.valueOf("@IsDownloaded"))){
						
						String fileID=node.valueOf("@FileID");	
						String fileName=node.valueOf("@FileName");
						System.out.println("Inside if  :-     fileID ="+fileID +"      fileName ="+fileName);
						System.out.println("loop count ="+i);
						try{
							ByteArrayInputStream bais=getDownloadedFile(soapDHPOStub, userID, password, licenceNO, fileName, fileID);
							
							if(fileName.toLowerCase().endsWith(".xml")){
								try
								{
									saveDownloadedFile(soapDHPOStub, userID, password, maintenanceManager, licenceNO, fileName, fileID,bais,"xml");
								}
								catch(Exception exception)
								{
									exception.printStackTrace();
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
										 saveDownloadedFile(soapDHPOStub, userID, password, maintenanceManager, licenceNO, zipFileName, zipFileID,(ByteArrayInputStream)objArr[1],"zip");
						             }
									 catch(Exception exception)
						             {
						            	 String issueType = "PARSING";
						            	 communicationManager.sendFailXmlAlert(msgId,issueType,fileName,fileID,licenceNO);
										 throw exception;
						             }
									 fileCont++;
								 }
								 setFileIDFlagTxDownloaded(soapDHPOStub, userID, password, fileID);
							}
							
							else{
									String issueType = "DOWNLOAD";
									FileOutputStream fout=new FileOutputStream(haadNotProccessFilesPath+fileID+"-Vidal-"+fileName);    
									byte[] respsData=new byte[bais.available()];
								 
									bais.read(respsData);
									fout.write(respsData);
									fout.close(); 
									setFileIDFlagTxDownloaded(soapDHPOStub, userID, password, fileID);
									communicationManager.sendFailXmlAlert(msgId,issueType,fileName,fileID,licenceNO);
							}
							
						}catch(Exception e){
							e.printStackTrace();
						}
						
						}//if("False".equalsIgnoreCase(node.valueOf("@IsDownloaded"))){
					i++;
					
				}//for(Node node:nodeList){				
					
				}//if(nodeList!=null&&nodeList.size()>0){
			}//if(strXmlTransaction!=null&&strXmlTransaction.length()>1){				
			
		
		
		}catch(Exception e){
			
			e.printStackTrace();
		}//end of catch(Exception e)		
	
		
		//System.out.println("*DHPO* Download End Time="+dateFormat.format(new Date()));
		long endTime=new Date().getTime();
		long procTime=endTime-startTime;
		
	//	System.out.println("*DHPO* Task Taken Time="+(procTime/1000)+" Sec");
		if(procTime<41000){
			//System.out.println("*DHPO* Waiting Time="+(41000-procTime)/1000+" Sec");
		//	Thread.sleep(41000-procTime);
		}//else System.out.println("*DHPO* Waiting Time=0 Sec");
		
		previousTaskStatus=false;
		log.info("---------------- Dhpo e-Preauth & e-calim  Schedular  Ended -----------------");
	}
	
	public static boolean isTaskExcecuting(){
		
		return previousTaskStatus;
	}
public static void setTaskExcecuteStatus(boolean taskStatus){
		
		 previousTaskStatus=taskStatus;
	}

private static ByteArrayInputStream getDownloadedFile(ValidateTransactionsSoap soapDHPOStub,String userID,String password,String licenceNO,String strFileName,String fileID){
	Holder<byte[]> fileContent=new Holder<byte[]>();
	byte[] fileData=null;
	Holder<String> fileName				=	new Holder<String>(strFileName);
	Holder<String> errorMessage2				=	new Holder<String>();
	Holder<Integer> downloadTransactionFileResult2	=	new Holder<Integer>();
	//download the transaction in DHPO
	
	soapDHPOStub.downloadTransactionFile(userID,password,fileID, downloadTransactionFileResult2,fileName, fileContent, errorMessage2);
	

	
	
	if(fileContent!=null&&fileContent.value!=null){							
		fileData=fileContent.value;

	}
if(fileData==null){
	fileData="".getBytes();

}
		return new ByteArrayInputStream(fileData);
}

private static void saveDownloadedFile(ValidateTransactionsSoap soapDHPOStub,String userID,String password,MaintenanceManager maintenanceManager,String licenceNO,String fileName,String fileID,ByteArrayInputStream bais,String fileExtns)throws Exception
{
//maintenanceManager, licenceNO, fileName, fileID,fileData
	
			
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
			
			dhpoWebServiceVO.setFileType("CLM");
			Object[] resObj=maintenanceManager.uploadDhpoGlobalNetNewTransactonDetails(dhpoWebServiceVO);
			System.out.println("e-claim :-"+ "File id :"+fileID + "       FileName :"+ fileName);
			//set the downloaded file as true in DHPO
			if("xml".equals(fileExtns)){
			setFileIDFlagTxDownloaded(soapDHPOStub, userID, password, fileID);
			}
		} else if("Prior.Request".equals(PATorCLMdoc.getRootElement().getName())||"PriorRequest.xsd".equals(PATorCLMdoc.getRootElement().getName())){
				dhpoWebServiceVO.setFileType("PAT");
				System.out.println("Pharmacy :-"+ "File id :"+fileID + "    FileName :"+ fileName);				
				//Document priorDoc=	new SAXReader().read(new StringReader(strFileContent));
				
				List actNode=PATorCLMdoc.selectNodes("//Activity/Type[text()!='5']");	
				
				if(actNode!=null&&actNode.size()>0){
				
					Object[] resObj=maintenanceManager.saveDhpoGlobalnetPreauthDownloadDetails(dhpoWebServiceVO);
					System.out.println("e-Preauth :-"+ "File id :"+fileID + "      FileName :"+ fileName);
					//set the downloaded file as true in DHPO
					if("xml".equals(fileExtns)){
					setFileIDFlagTxDownloaded(soapDHPOStub, userID, password, fileID);
					}
		      }
		
		}//if("Prior.Request".equals(PATorCLMdoc.getRootElement())||"PriorRequest.xsd".equals(PATorCLMdoc.getRootElement())){
		
	
}
public static void setFileIDFlagTxDownloaded(ValidateTransactionsSoap soapDHPOStub,String userID,String password,String fileID){
	Holder<String> errorMessage3				=	new Holder<String>();
	Holder<Integer> setTransactionDownloadedResult				=	new Holder<Integer>();
	
	soapDHPOStub.setTransactionDownloaded(userID,password, fileID, setTransactionDownloadedResult, errorMessage3);
    
}
public static ArrayList<Object[]> getFiles(ByteArrayInputStream bais)throws Exception {
	
	ArrayList<Object[]> alFiles=new ArrayList<>();	
	
	byte[] buffer = new byte[1024];
  //  	System.out.println("inside getFiles ");
    	ZipInputStream zis =new ZipInputStream(bais);
    	
        	//get the zipped file list entry
        	ZipEntry ze = zis.getNextEntry();
        	while(ze!=null){
        	   String fileName = ze.getName();
        	   if(  fileName.toUpperCase().endsWith(".XML") ||  fileName.toLowerCase().endsWith(".xml"))
        	   {
        	ByteArrayOutputStream baos=new ByteArrayOutputStream();
                
              int len;
              while ((len = zis.read(buffer)) > 0) {
            	  baos.write(buffer, 0, len);
              }
            //  alFiles.add(new Object[] {fileName,baos});
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
	private static MaintenanceManager getMaintenanceManagerObject() throws TTKException
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
			throw new TTKException(exp, strDHPOGNNewTransactionError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()
	
	// for email-trigger
		private static CommunicationManager getCommunicationManagerObject() throws TTKException
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
}
