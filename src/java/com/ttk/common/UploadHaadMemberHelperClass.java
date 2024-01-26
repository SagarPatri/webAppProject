package com.ttk.common;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import org.dom4j.io.SAXReader;

import com.ttk.action.enrollment.MemberUploadAction;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.business.webservice.dhpo.ValidateTransactionsSoap;
import com.ttk.common.DHPOStub;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.common.messageservices.EmailHelper;
import com.ttk.common.webservices.dhpomem.tempuri.IMemberRegistration;
import com.ttk.common.webservices.dhpomem.tempuri.MemberRegistration;
import com.ttk.dto.common.CommunicationOptionVO;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.common.HaadWebServiceVo;

public class UploadHaadMemberHelperClass {
	private static final String strHaadMemberFileUploadError="HaadMemberRegisterFileUpload";

	private static Logger log=Logger.getLogger(MemberUploadAction.class);
	String HaadLogFilePath=TTKPropertiesReader.getPropertyValue("HAADLOGFILES");
    String logFileName;
    File logFile = null;
    FileWriter fw;
    ArrayList<String>allData;
    BufferedWriter bw = null;
    private int uploadedMemberCount;
    private int failedMemberCount;
    private int allMemberCount;
	public  int UploadActiveMemberToHaad(List<HaadWebServiceVo> uploadList,ArrayList<String>allData,String addedBy) 
	{
		MaintenanceManager maintenanceManager = null;
		 MemberManager memberManager = null;
		int result=0;
		allData=this.allData;
		StringBuilder uploadedMembers=new StringBuilder();
	//	insCode=allData.get(0);
	//List<DhpoWebServiceVO> uploadRAList = null;
	
	try
	{
		if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.CL.RA.RUN.MODE"))){
			
			log.info("DHPO Remittance Advice FileUpload Started ..........");
			
		maintenanceManager = getMaintenanceManagerObject();	
		memberManager=this.getMemberManager();
		//uploadRAList=maintenanceManager.getRemittanceAdviceDHPOFileUpload();
		
		String GNUserID=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.userID");
		String GNPassword=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.password");
		
		String VHUserID=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.userID");
		String VHPassword=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.password");
		
		String HaadUserID=TTKPropertiesReader.getPropertyValue("HAADWebService.GN.userID");
		String HaadPassword=TTKPropertiesReader.getPropertyValue("HAADWebService.GN.password");
		
		if(uploadList!=null){		    	
			ValidateTransactionsSoap soapDHPOStub		=	DHPOStub.getStub();
			com.ttk.business.webservice.haad.WebservicesSoap soapHAADStub		=	DHPOStub.getHaadStub();

			for(HaadWebServiceVo dhpoWebServiceVO : uploadList){

				
				Holder<String> errorMessage				=	new Holder<String>();
				Holder<Integer> uploadRemitanceAdviceResult	=	new Holder<Integer>();
				Holder<byte[]> errorReport				=	new Holder<byte[]>();
				
				if(dhpoWebServiceVO.getXmlFileReader()!=null){
					//uploading procced files in DHPO
					allMemberCount++;
					SAXReader saxReader	=	new SAXReader();
					Document document=saxReader.read(dhpoWebServiceVO.getXmlFileReader());	
                    byte[] xmlData=document.asXML().getBytes();
                
                    if("TPA033".equals(dhpoWebServiceVO.getSenderID())){
					soapDHPOStub.uploadTransaction(VHUserID, VHPassword,xmlData, dhpoWebServiceVO.getFileName(), uploadRemitanceAdviceResult, errorMessage, errorReport);
                    }
                    else if("TPA021".equals(dhpoWebServiceVO.getSenderID())){
					soapDHPOStub.uploadTransaction(GNUserID, GNPassword,xmlData, dhpoWebServiceVO.getFileName(), uploadRemitanceAdviceResult, errorMessage, errorReport);
                    }
                    else if("C016".equals(dhpoWebServiceVO.getSenderID())){
					 soapHAADStub.uploadTransaction(HaadUserID, HaadPassword,xmlData, dhpoWebServiceVO.getFileName(), uploadRemitanceAdviceResult, errorMessage, errorReport);
					 
                   } 
                   else{
                    	errorMessage.value="Sender ID Not Valid ";
                    }
                    
					if(uploadRemitanceAdviceResult.value==0 || uploadRemitanceAdviceResult.value==1){
		    			uploadedMembers.append(dhpoWebServiceVO.getEnrollementId()+",");
		    			
						dhpoWebServiceVO.setFlag_YN("Y");
						dhpoWebServiceVO.setErrorMessage("success");	
						uploadedMemberCount++;

					}else{
						
						dhpoWebServiceVO.setFlag_YN("N");
						failedMemberCount++;
						dhpoWebServiceVO.setErrorMessage(errorMessage.value);
						if("C016".equals(dhpoWebServiceVO.getSenderID())){
							dhpoWebServiceVO.setErrorReport(this.unZipeErrorFile(errorReport.value));	
						}else{
							dhpoWebServiceVO.setErrorReport(errorReport.value);	
						}						
						
					}
					//update the  uploaded status list
					writeDataToBufferWrite();
					writeDataToBufferWrite("Enrolment ID::"+dhpoWebServiceVO.getEnrollementId());
					writeDataToBufferWrite(dhpoWebServiceVO.getErrorMessage());
					writeDataToBufferWrite(dhpoWebServiceVO.getErrorReport());
					maintenanceManager.getMemberUploadStatus(dhpoWebServiceVO);
					
	    	        bw.flush();
	    	        bw.close();
					result=1;
				}//if(dhpoobj.getXmlFileReader()!=null){
				
			}//for(DhpoWebServiceVO dhpoWebServiceVO : uploadRAList){
			writeDataToBufferWrite("**********************************************************************************************");
			writeDataToBufferWrite("Total Member Uploaded ::"+allMemberCount);
			writeDataToBufferWrite("Members Uploaded Succesfully ::"+uploadedMemberCount);
			writeDataToBufferWrite("Failed Members ::"+failedMemberCount);
			 bw.flush();
 	        bw.close();
 	        //send the log file to the mail ids
 	        if(logFile.getAbsolutePath()!=null)
 	        {
 	        	
 	        	ArrayList<String> allinformation=new ArrayList<>();
 	 	      allinformation.add(allData.get(0));
 	 	      allinformation.add(logFileName);
 	 	      allinformation.add(addedBy);
 	 	    SimpleDateFormat  sdate=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
 	 	      allinformation.add(sdate.format(new Date()));
 	 	    allinformation.add("HAAD");//Authority Type
 	 	    
 	 	        memberManager.DHPOLogFileNames(allinformation); // saving log file deatils to data base
 	        	
 	        	
			CommunicationOptionVO cOptionVO=new CommunicationOptionVO();
			cOptionVO.setSentFrom("lohith.m@vidalhealth.com");
			cOptionVO.setPrmRcptEmailList("lohith.m@vidalhealth.com");
			cOptionVO.setFilePathName(logFile.getAbsolutePath());
			cOptionVO.setMsgID("MEMBER_REGISTER_UPLOAD_LOG");
			cOptionVO.setMessage("PFA for Log File of Member Uploaded To HAAD Web Portal");
			EmailHelper.message(cOptionVO);
 	        }
 	        if(uploadedMembers!=null)memberManager.DHPOUplodedMembers(uploadedMembers);  // saving the uploaded date to DB
		}//if(uploadRAList!=null){	
		log.info("HAAD  FileUpload Ended ..........");
	}//if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.CL.RA.RUN.MODE"))){
	}//try{
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return result;
	
}
	private void writeDataToBufferWrite() throws IOException
	{
		logFileName=allData.get(0)+"-"+allData.get(1)+"-"+new Date().getTime()+".txt";
	      System.out.println("logFileName=="+HaadLogFilePath+logFileName);
	      logFile= new File(HaadLogFilePath);
	      if(!logFile.exists()) logFile.mkdirs();
	      logFile= new File(HaadLogFilePath+logFileName);
	      logFile.createNewFile();
	      fw=new FileWriter(logFile);
		     bw=new BufferedWriter(fw);
		     bw.write("                                                                     DHPO LOG FILE");
			  bw.newLine();
			  bw.write("                                                                 FILE NAME :"+logFileName);
			  bw.newLine();
			  bw.write("                                                                 UPLOADED DATE::"+new Date());
			  bw.newLine();
			  bw.write("*********************************************************************************************************************************************************************");
	
	}
	private void writeDataToBufferWrite(String data) throws IOException
	{
			bw.newLine();
			bw.write(data);
	}
	
	private void writeDataToBufferWrite(byte[] data) throws IOException
	{
		bw.newLine();
		for(int x = 0 ; x < data.length; x++) bw.write((char)data[x]);
		//bw.newLine();
	}

	private   byte[] unZipeErrorFile(byte[] zipedData)throws Exception{
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
	
	private  MaintenanceManager getMaintenanceManagerObject() throws TTKException
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
	
	
	
	private MemberManager getMemberManager() throws TTKException
    {
        MemberManager memberManager = null;
        try
        {
            if(memberManager == null)
            {
                InitialContext ctx = new InitialContext();
                memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "memberdetail");
        }//end of catch
        return memberManager;
    }//end getMemberManager()
	
}
