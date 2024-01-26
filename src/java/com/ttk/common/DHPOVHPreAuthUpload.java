
/**
 * @ (#) DHPOVHPreAuthUpload.java Feb 27 2017
 * Project      : TTK HealthCare Services
 * File         : DHPOVHPreAuthUpload.java
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

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.business.webservice.dhpo.ValidateTransactions;
import com.ttk.business.webservice.dhpo.ValidateTransactionsSoap;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.DhpoWebServiceVO;

public class DHPOVHPreAuthUpload implements Job {
	
	private static final String strDHPOVHPreAuthUploadError="DHPOVHPreAuthUpload";
	private static final Logger log = Logger.getLogger( DHPOVHPreAuthUpload.class );
	
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;
		try
		{	
		if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.VH.PU.RUN.MODE"))){
			
			log.info("DHPO VH PreAuth Upload Started..........");
			
			maintenanceManager = this.getMaintenanceManagerObject();
			
			ArrayList<DhpoWebServiceVO> preAuthList=maintenanceManager.getDhpoVHPriorAuthorizationList();
			
		    String userID="";
		    String password="";
		   // String licenceNO=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.licence.NO");
		    
			ValidateTransactions transactions 	=	new ValidateTransactions();
			ValidateTransactionsSoap soapDHPOStub		=	transactions.getValidateTransactionsSoap();
			
			ArrayList<DhpoWebServiceVO> preAuthUploadedStatusList=new ArrayList<>();
			
			DhpoWebServiceVO dhpoWebServiceVOStatus=null;
			
if(preAuthList!=null&&preAuthList.size()>0){
	
	for(DhpoWebServiceVO dhpoWebServiceVO:preAuthList){
		
		try{
		
		Holder<Integer> uploadTransactionResult	=	new Holder<Integer>();
		Holder<String> errorMessage				=	new Holder<String>();
		Holder<byte[]> errorReport				=	new Holder<>();

		if(TTKCommon.checkNull(dhpoWebServiceVO.getDhpoLicenceNO()).equals(TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.licence.NO"))){
	 
		userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.userID");
	    password=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.password");
		
		}else{
		
		userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.userID");
	    password=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.password");

		}
	
		
		if(dhpoWebServiceVO.getXmlFileReader()!=null){
		
			SAXReader saxReader	=	new SAXReader();

		   Document document=saxReader.read(dhpoWebServiceVO.getXmlFileReader());	
		   
		soapDHPOStub.uploadTransaction(userID, password, document.asXML().getBytes(), dhpoWebServiceVO.getFileName(), uploadTransactionResult, errorMessage, errorReport);
		
		//setting uploaded status
		dhpoWebServiceVOStatus=new DhpoWebServiceVO();
		if(uploadTransactionResult.value==0||uploadTransactionResult.value==1){
			dhpoWebServiceVOStatus.setPreAuthUploadStatus("Y");
		}else{
			dhpoWebServiceVOStatus.setPreAuthUploadStatus("N");
		}
		dhpoWebServiceVOStatus.setPreAuthSeqID(dhpoWebServiceVO.getPreAuthSeqID());
		dhpoWebServiceVOStatus.setFileID(dhpoWebServiceVO.getFileID());
		dhpoWebServiceVOStatus.setErrorMessage(errorMessage.value);
		dhpoWebServiceVOStatus.setErrorReport(errorReport.value);
		dhpoWebServiceVOStatus.setFileName(dhpoWebServiceVO.getFileName());
		dhpoWebServiceVOStatus.setDhpoLicenceNO(dhpoWebServiceVO.getDhpoLicenceNO());
		preAuthUploadedStatusList.add(dhpoWebServiceVOStatus);
		
	}//if(dhpoWebServiceVO.getXmlFileReader()!=null){

}catch (Exception e) {
			e.printStackTrace();
		}
	}//for(DhpoWebServiceVO dhpoWebServiceVO:preAuthList){
	
}//if(preAuthList!=null&&preAuthList.size()>0){
			
									
Object[] resObj=maintenanceManager.saveVHDhpoPreauthUploadDetails(preAuthUploadedStatusList);
										
			
	log.info("DHPO VH PreAuth Upload  Ended.........."+resObj);
		
		}//if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.VH.PU.RUN.MODE"))){
}catch(Exception e){
			e.printStackTrace();
			new Exception(strDHPOVHPreAuthUploadError);
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
			throw new TTKException(exp, strDHPOVHPreAuthUploadError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	
}//end of DHPONewTransaction
