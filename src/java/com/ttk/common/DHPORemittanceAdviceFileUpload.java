package com.ttk.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
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
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.business.webservice.dhpo.ValidateTransactionsSoap;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.DhpoWebServiceVO;

public class DHPORemittanceAdviceFileUpload implements Job {

	private static final String strDHPORemittanceAdviceFileUploadError="DHPORemittanceAdviceFileUpload";
	private static final Logger log = Logger.getLogger( DHPORemittanceAdviceFileUpload.class );

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		MaintenanceManager maintenanceManager = null;
		List<DhpoWebServiceVO> uploadRAList = null;
		
		try
		{
			if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.CL.RA.RUN.MODE"))){
				
				log.info("DHPO Remittance Advice FileUpload Started ..........");
				
			maintenanceManager = this.getMaintenanceManagerObject();	
			uploadRAList=maintenanceManager.getRemittanceAdviceDHPOFileUpload();
			
			String GNUserID=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.userID");
			String GNPassword=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.password");
			
			String VHUserID=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.userID");
			String VHPassword=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.password");
			
			String HaadUserID=TTKPropertiesReader.getPropertyValue("HAADWebService.GN.userID");
			String HaadPassword=TTKPropertiesReader.getPropertyValue("HAADWebService.GN.password");
			 SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyy HH:mm");
			if(uploadRAList!=null){		    	

				ValidateTransactionsSoap soapDHPOStub		=	DHPOStub.getStub();
				com.ttk.business.webservice.haad.WebservicesSoap soapHAADStub		=	DHPOStub.getHaadStub();

				for(DhpoWebServiceVO dhpoWebServiceVO : uploadRAList){

					
					Holder<String> errorMessage				=	new Holder<String>();
					Holder<Integer> uploadRemitanceAdviceResult	=	new Holder<Integer>();
					Holder<byte[]> errorReport				=	new Holder<byte[]>();
					
					if(dhpoWebServiceVO.getXmlFileReader()!=null){
						//uploading procced files in DHPO
						SAXReader saxReader	=	new SAXReader();
						Document document=saxReader.read(dhpoWebServiceVO.getXmlFileReader());	
						
			    		        Node header = document.selectSingleNode("/"+document.getRootElement().getName()+"/Header" );
			    		        header.selectSingleNode("TransactionDate").setText(sd.format(new Date()));
			    		        
                        byte[] xmlData=document.asXML().getBytes();
                    
                        if("TPA033".equals(dhpoWebServiceVO.getSenderID())){
						soapDHPOStub.uploadTransaction(VHUserID, VHPassword,xmlData, dhpoWebServiceVO.getFileName(), uploadRemitanceAdviceResult, errorMessage, errorReport);
                        }
                        else if("TPA021".equals(dhpoWebServiceVO.getSenderID())){
						soapDHPOStub.uploadTransaction(GNUserID, GNPassword,xmlData, dhpoWebServiceVO.getFileName(), uploadRemitanceAdviceResult, errorMessage, errorReport);
                        }
                        else if("C016".equals(dhpoWebServiceVO.getSenderID())){
						//skipping haad files
                        	 soapHAADStub.uploadTransaction(HaadUserID, HaadPassword,xmlData, dhpoWebServiceVO.getFileName(), uploadRemitanceAdviceResult, errorMessage, errorReport);
	                   } 
                       else{
                        	errorMessage.value="Sender ID Not Valid ";
                        }
                        
						if(uploadRemitanceAdviceResult.value==0 || uploadRemitanceAdviceResult.value==1){
						    
							dhpoWebServiceVO.setFlag_YN("Y");
							dhpoWebServiceVO.setErrorMessage("success");						

						}else{
							
							dhpoWebServiceVO.setFlag_YN("N");
							dhpoWebServiceVO.setErrorMessage(errorMessage.value);
							if("C016".equals(dhpoWebServiceVO.getSenderID())){
								dhpoWebServiceVO.setErrorReport(unZipeErrorFile(errorReport.value));	
							}else{
								dhpoWebServiceVO.setErrorReport(errorReport.value);	
							}						
							
						}
						//update the ra uploaded status list

						maintenanceManager.getRemittanceAdviceDHPOStatus(dhpoWebServiceVO);
					}//if(dhpoobj.getXmlFileReader()!=null){
					
				}//for(DhpoWebServiceVO dhpoWebServiceVO : uploadRAList){


			}//if(uploadRAList!=null){	
			log.info("DHPO Remittance Advice FileUpload Ended ..........");
		}//if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.CL.RA.RUN.MODE"))){
		}//try{
		catch(Exception e)
		{
			e.printStackTrace();
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
			throw new TTKException(exp, strDHPORemittanceAdviceFileUploadError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	

	private  byte[] unZipeErrorFile(byte[] zipedData)throws Exception{
		byte unzipedData[]=null;
		try{
			unzipedData="Default".getBytes();
		ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(zipedData);
		ZipInputStream  zis=new ZipInputStream(byteArrayInputStream);
		
		byte []buffer=new byte[256];
		 ZipEntry zipEntry=zis.getNextEntry();
		
		 while(zipEntry!=null){
			 ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			
             int len;
             while ((len = zis.read(buffer)) > 0) {
            	 byteArrayOutputStream.write(buffer, 0, len);
             }
			 zis.closeEntry();
			 zipEntry=zis.getNextEntry();
			 zis.close();
			 unzipedData=byteArrayOutputStream.toByteArray();
		 }
		}catch(Exception  exception){
			exception.printStackTrace();
			unzipedData=exception.getMessage().getBytes();
		}
		return unzipedData;
}
}
