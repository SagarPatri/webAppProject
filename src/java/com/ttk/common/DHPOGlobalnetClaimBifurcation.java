
/**
 * @ (#) DHPOBifurcation.java NOV 11 2016
 * Project      : Project X
 * File         : DHPOBifurcation.java
 * Author       : Nagababu Kamadi
 * Company      : RCS TEchnologies
 * Date Created : NOV 11 2016
 *
 * @author       : Nagababu Kamadi
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.DhpoWebServiceVO;

public class DHPOGlobalnetClaimBifurcation implements Job {
	
	private static final String strDHPONewTransactionError="DHPOGlobalnetClaimBifurcation";
	private static final Logger log = Logger.getLogger(DHPOGlobalnetClaimBifurcation.class );
	
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;
		try
		{	
			if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.WS.GN.CL.BI.RUN.MODE"))){
			log.info("DHPO Globalnet Claim Bifurcation Started..........");
			
			maintenanceManager = this.getMaintenanceManagerObject();	
			List<DhpoWebServiceVO> listVOs=maintenanceManager.getDhpoNewTransactonDetails();
			ArrayList<DhpoWebServiceVO> bifurcationFiles=new ArrayList<DhpoWebServiceVO>();
			String strRootElm="<Claim.Submission xsi:schemaLocation=\"http://www.eClaimLink.ae/DHD/CommonTypes.xsd http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.eClaimLink.ae/DHD/CommonTypes/ClaimSubmission.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
			String strEndRootElm="</Claim.Submission>";
			if(listVOs.size()>0){
				
				for(DhpoWebServiceVO serviceVO:listVOs){
					
					if(serviceVO.getXmlFileReader()!=null){
					
						SAXReader saxReader	=	new SAXReader();

					   Document document=saxReader.read(serviceVO.getXmlFileReader());	
					  
						List<Node>  claimNodes=document.selectNodes("/Claim.Submission/Claim");
												
						int gnCount=0;
						int shCount=0;
						int vhCount=0;
						
						String dhpoCount=document.selectSingleNode("/Claim.Submission/Header/RecordCount").getText();
						String dhpoTxDate=document.selectSingleNode("/Claim.Submission/Header/TransactionDate").getText();
						String senderId=document.selectSingleNode("/Claim.Submission/Header/SenderID").getText();
						
						StringBuilder gnClaimXml=new StringBuilder();
						StringBuilder shClaimXml=new StringBuilder();
						StringBuilder vhClaimXml=new StringBuilder();
						
						for(int n=0;n<claimNodes.size();n++){

							String claimAsString=claimNodes.get(n).asXML();
							
						     Document claimDocument=new SAXReader().read(new StringReader(claimAsString));	
							
							/*Node memNode=claimDocument.selectSingleNode("//Claim/MemberID");
							String memberID=memNode.getText();
							String isMemBelongsTo=maintenanceManager.isMemberBelongs(memberID);*/
						     	Node memNode=claimDocument.selectSingleNode("//Claim/MemberID");
	                            Node eidNONode=claimDocument.selectSingleNode("//Claim/EmiratesIDNumber");
	                            String memberID="";//memberID
	                            String emirateID="";//EmirateID
	                            if(memNode!=null) memberID=memNode.getText();
	                            if(eidNONode!=null) emirateID=eidNONode.getText();
	                            String isMemBelongsTo=maintenanceManager.isMemberBelongs(memberID,emirateID);  
							
							if("SH".equals(isMemBelongsTo)){								
								
								shClaimXml.append(claimAsString);
								shClaimXml.append("\n");
								shCount++;
								
							}else if("VH".equals(isMemBelongsTo)){
								vhClaimXml.append(claimAsString);
								vhClaimXml.append("\n");
								vhCount++;								
							}else{
								gnClaimXml.append(claimAsString);
								gnClaimXml.append("\n");
								gnCount++;
							}											
						}//for(Node claimNode:claimNodes){
						
						if(gnCount>0){
							
							StringBuilder gnBuilder=new StringBuilder();
							gnBuilder.append(strRootElm);
							gnBuilder.append("\n");
							
							Node headerNode=document.selectSingleNode("//Header");					
							headerNode.selectSingleNode("//RecordCount").setText(new Integer(gnCount).toString());
							
							gnBuilder.append(headerNode.asXML());
							gnBuilder.append("\n");
							
							gnBuilder.append(gnClaimXml);
							
							gnBuilder.append("\n");
							gnBuilder.append(strEndRootElm);							
							
							DhpoWebServiceVO gnServiceVO=new DhpoWebServiceVO();
							gnServiceVO.setFileID(serviceVO.getFileID());
							gnServiceVO.setFileName(serviceVO.getFileName());							
							gnServiceVO.setXmlFileContent(gnBuilder.toString());
							gnServiceVO.setClaimFrom("GN");
							gnServiceVO.setBifurcationYN("Y");
							gnServiceVO.setFileType(serviceVO.getFileType());
							gnServiceVO.setTransactionResult(serviceVO.getTransactionResult());
							gnServiceVO.setErrorMessage(serviceVO.getErrorMessage());
							gnServiceVO.setDownloadStatus(serviceVO.getDownloadStatus());
							gnServiceVO.setDhpoTxDate(dhpoTxDate);
							gnServiceVO.setProviderID(senderId);
							gnServiceVO.setDhpoClaimRecCount(dhpoCount);
							gnServiceVO.setClaimRecCount(gnCount+"");							
							gnServiceVO.setDhpoLicenceNO(serviceVO.getDhpoLicenceNO());
							bifurcationFiles.add(gnServiceVO);
							
						}
						
                     if(shCount>0){
                    	 
							StringBuilder shBuilder=new StringBuilder();
							shBuilder.append(strRootElm);
							shBuilder.append("\n");
							
							Node headerNode=document.selectSingleNode("//Header");								
							headerNode.selectSingleNode("//RecordCount").setText(new Integer(shCount).toString());
							
							shBuilder.append(headerNode.asXML());
							shBuilder.append("\n");
							shBuilder.append(shClaimXml);
							shBuilder.append("\n");
							shBuilder.append(strEndRootElm);
							
							DhpoWebServiceVO shServiceVO=new DhpoWebServiceVO();
							shServiceVO.setFileID(serviceVO.getFileID());
							shServiceVO.setFileName(serviceVO.getFileName());
							shServiceVO.setXmlFileContent(shBuilder.toString());
							shServiceVO.setClaimFrom("SH");
							shServiceVO.setBifurcationYN("Y");
							shServiceVO.setFileType(serviceVO.getFileType());
							shServiceVO.setTransactionResult(serviceVO.getTransactionResult());
							shServiceVO.setErrorMessage(serviceVO.getErrorMessage());
							shServiceVO.setDownloadStatus(serviceVO.getDownloadStatus());
							shServiceVO.setDhpoTxDate(dhpoTxDate);
							shServiceVO.setProviderID(senderId);
							shServiceVO.setDhpoClaimRecCount(dhpoCount);
							shServiceVO.setClaimRecCount(shCount+"");
							shServiceVO.setDhpoLicenceNO(serviceVO.getDhpoLicenceNO());
							shServiceVO.setDhpoLicenceNO(serviceVO.getDhpoLicenceNO());
							bifurcationFiles.add(shServiceVO);
						}
					
                     if(vhCount>0){
                    	 
							StringBuilder vhBuilder=new StringBuilder();
							vhBuilder.append(strRootElm);
							vhBuilder.append("\n");
							
							Node headerNode=document.selectSingleNode("//Header");								
							headerNode.selectSingleNode("//RecordCount").setText(new Integer(vhCount).toString());
							
							vhBuilder.append(headerNode.asXML());
							vhBuilder.append("\n");
							vhBuilder.append(vhClaimXml);
							vhBuilder.append("\n");
							vhBuilder.append(strEndRootElm);
							
							DhpoWebServiceVO vhServiceVO=new DhpoWebServiceVO();
							vhServiceVO.setFileID(serviceVO.getFileID());
							vhServiceVO.setFileName(serviceVO.getFileName());
							vhServiceVO.setXmlFileContent(vhBuilder.toString());
							vhServiceVO.setClaimFrom("VH");
							vhServiceVO.setBifurcationYN("Y");
							vhServiceVO.setFileType(serviceVO.getFileType());
							vhServiceVO.setTransactionResult(serviceVO.getTransactionResult());
							vhServiceVO.setErrorMessage(serviceVO.getErrorMessage());
							vhServiceVO.setDownloadStatus(serviceVO.getDownloadStatus());
							vhServiceVO.setDhpoTxDate(dhpoTxDate);
							vhServiceVO.setProviderID(senderId);
							vhServiceVO.setDhpoClaimRecCount(dhpoCount);
							vhServiceVO.setClaimRecCount(vhCount+"");
							vhServiceVO.setDhpoLicenceNO(serviceVO.getDhpoLicenceNO());
							bifurcationFiles.add(vhServiceVO);
						}
                     
					}//if(serviceVO.getXmlFileReader()!=null){
					
				}//for(DhpoWebServiceVO serviceVO:listVOs){
				
				
				//saving Bifurcation Details
				maintenanceManager.saveBifurcationDetails(bifurcationFiles);
				
			}//if(listVOs.size()>0){
			
			log.info("DHPO Globalnet Claim Bifurcation Ended..........");
			}//if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.GNCB.WS.RUN.MODE"))){
	   }//end of run method
		catch(Exception exception){
			
			System.out.println("DHPOGlobalnetClaimBifurcation::"+exception.getCause());
		}
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
			throw new TTKException(exp, strDHPONewTransactionError);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()	
}//end of DHPONewTransaction
