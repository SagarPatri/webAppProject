
/**
 * @ (#) DHPOHistoricalClaimBifurcation.java NOV 11 2016
 * Project      : Project X
 * File         : DHPOHistoricalClaimBifurcation.java
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

public class DHPOHistoricalClaimBifurcation implements Job {
	
	private static final String strDHPONewTransactionError="DHPOHistoricalClaimBifurcation";
	private static final Logger log = Logger.getLogger( DHPOHistoricalClaimBifurcation.class );
	
	public void execute(JobExecutionContext arg0) {	
		MaintenanceManager maintenanceManager = null;
		try
		{	
			if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.HCB.WS.RUN.MODE"))){
			
		  
				log.info("DHPO Historical Claim Bifurcation Started..........");
			
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
						String dhpoCount=document.selectSingleNode("/Claim.Submission/Header/RecordCount").getText();
						String dhpoTxDate=document.selectSingleNode("/Claim.Submission/Header/TransactionDate").getText();
						
						StringBuilder gnClaimXml=new StringBuilder();
						StringBuilder shClaimXml=new StringBuilder();
						for(int n=0;n<claimNodes.size();n++){

							System.out.println((n+1)+" File Spliting Started");
							
							String claimAsString=claimNodes.get(n).asXML();
							
						     Document claimDocument=new SAXReader().read(new StringReader(claimAsString));	
							
							Node memNode=claimDocument.selectSingleNode("//Claim/MemberID");
							String memberID=memNode.getText();
							
							if(
							   memberID!=null
							         &&
							   (memberID.startsWith("DXB")||
							    memberID.startsWith("OIG")||
							    memberID.startsWith("OIF"))
							          &&
							   !memberID.contains("E0012")
							          &&
							   !memberID.contains("K0004")
							         &&
							   !memberID.contains("L0001")
							   &&
							   !memberID.contains("K0203")
							   ){								
								
								gnClaimXml.append(claimAsString);
								gnClaimXml.append("\n");
								gnCount++;								
							}else{								
								shClaimXml.append(claimAsString);
								shClaimXml.append("\n");
								shCount++;
							}	
						System.out.println((n+1)+" File Spliting Ended");
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
							gnServiceVO.setDhpoClaimRecCount(dhpoCount);
							gnServiceVO.setClaimRecCount(gnCount+"");
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
							shServiceVO.setDhpoClaimRecCount(dhpoCount);
							shServiceVO.setClaimRecCount(shCount+"");
							bifurcationFiles.add(shServiceVO);
						}
					
					
					}//if(serviceVO.getXmlFileReader()!=null){
					
				}//for(DhpoWebServiceVO serviceVO:listVOs){
				
				
				//saving Bifurcation Details
				maintenanceManager.saveBifurcationDetails(bifurcationFiles);
				
			}//if(listVOs.size()>0){
			
			log.info("DHPO Historical Claim Bifurcation Ended..........");
			}//if(new Boolean(TTKPropertiesReader.getPropertyValue("DHPO.CB.WS.RUN.MODE"))){
	   }//end of run method
		catch(Exception exception){
			
			
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
