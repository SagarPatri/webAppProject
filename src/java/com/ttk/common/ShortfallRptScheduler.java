/**
 * @ (#) ShortfallRptScheduler.java Jan 02, 2013
 * Project 	     : TTK HealthCare Services
 * File          : ShortfallRptScheduler.java
 * Author        : Manohar 
 * Company       : RCS
 * Date Created  : Jan 02, 2013
 *
 * @author       :	Manohar
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
package com.ttk.common;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import javax.naming.InitialContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;


import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.claims.ClaimShortfallVO;

public class ShortfallRptScheduler implements Job{
	
	private static final Logger log = Logger.getLogger(ShortfallRptScheduler.class);
	private static final String strStatusError="ShortfallStatusReport";

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
		try{
			
			CommunicationManager communicationManager = this.getCommunicationManagerObject();
			String jrxmlFiles[] = new String[]
			                                 {
					"generalreports/ShortfallReminderLetter.jrxml",
					"generalreports/CignaShortfallFirstRemainderAdv.jrxml",
					"generalreports/ShortfallClosureNotice.jrxml",
					"generalreports/CignaShortfallSecondRemainderAdv.jrxml",
					"generalreports/ShortfallClosureApproval.jrxml",
					"generalreports/ShortfallClosureLetter.jrxml"
					
					//added for Mail-SMS Template for Cigna
					
					
					
			                                 };		
			String strReportID="AddressingDocuments";
			String strParameter="AddressingDocuments";
			//String iNoOfCursor="4";
			String iNoOfCursor="6";
 			//int noofcursors=4;
			int noofcursors=6;
            String shortfallNumber="";
			TTKReportDataSource ttkReportDataSource = new TTKReportDataSource(strReportID,strParameter,iNoOfCursor);
			JasperReport jasperReport,xmljasperReport,emptyReport;
			JasperReport jasperReport1;
			JasperReport objJasperReport[] =new JasperReport[noofcursors];
			JasperPrint jasperPrint=null;
			JasperPrint jasperPrint1=null;
			HashMap<String, Object> hashMap =null;
			ByteArrayOutputStream boas=new ByteArrayOutputStream();	
			ClaimShortfallVO element = null;
			ArrayList<ClaimShortfallVO> data = new ArrayList<ClaimShortfallVO>();		
			JRBeanCollectionDataSource dataSource = null;
			JRBeanCollectionDataSource dataSource1 = null;
			org.w3c.dom.Document document = null;
			objJasperReport[0] = JasperCompileManager.compileReport(jrxmlFiles[0]);
			objJasperReport[1] = JasperCompileManager.compileReport(jrxmlFiles[1]);
			objJasperReport[2] = JasperCompileManager.compileReport(jrxmlFiles[2]);
			objJasperReport[3] = JasperCompileManager.compileReport(jrxmlFiles[3]);
			objJasperReport[4] = JasperCompileManager.compileReport(jrxmlFiles[4]);
			objJasperReport[5] = JasperCompileManager.compileReport(jrxmlFiles[5]);
			 
		//	String strPath = TTKPropertiesReader.getPropertyValue("SignatureImgPath");
			xmljasperReport = JasperCompileManager.compileReport("generalreports/ShortfallQuestions.jrxml");
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 
			 if(ttkReportDataSource.isResultSetArrayEmpty())
			 {
					jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
					//alJasperPrintList.add(jasperPrint);
			 }//end of if(ttkReportDataSource.isResultSetArrayEmpty())
			 else
			 {
				 for(int i=0;i<noofcursors;i++)
				 {
					 
				 ResultSet rs = ttkReportDataSource.getResultData(i);
				 int count=0;
				    while(ttkReportDataSource.getResultData(i).next())
		            {
		            	 element = new ClaimShortfallVO();
		            	//element.setTtk_address(ttkReportDataSource.getResultData(i).getString("TTK_ADDRESS"));
						 element.setAddress1(ttkReportDataSource.getResultData(i).getString("ADDRESS1"));
						 element.setAddress2(ttkReportDataSource.getResultData(i).getString("ADDRESS2"));
						 element.setAddress3(ttkReportDataSource.getResultData(i).getString("ADDRESS3"));
				       			
						 element.setCITY(ttkReportDataSource.getResultData(i).getString("CITY"));          	 
				         element.setShortfall_number(ttkReportDataSource.getResultData(i).getString("SHORTFALL_NUMBER"));
				         element.setClaim_number(ttkReportDataSource.getResultData(i).getString("CLAIM_NUMBER"));
				         //element.setDate_of_admission(ttkReportDataSource.getResultData(i).getDate("DATE_OF_ADMISSION"));
						 element.setDate_of_admission(ttkReportDataSource.getResultData(i).getString("DATE_OF_ADMISSION"));
						 element.setDate_of_discharge(ttkReportDataSource.getResultData(i).getString("DATE_OF_DISCHARGE"));
		            	 element.setPolicy_number(ttkReportDataSource.getResultData(i).getString("POLICY_NUMBER"));          	 
		            	 element.setTpa_enrollment_id(ttkReportDataSource.getResultData(i).getString("TPA_enrollment_id"));
		            	 element.setTpa_enrollment_number(ttkReportDataSource.getResultData(i).getString("TPA_ENROLLMENT_NUMBER"));
		            	 element.setEmail_id(ttkReportDataSource.getResultData(i).getString("EMAIL_ID"));
		            	 element.setHospital_name(ttkReportDataSource.getResultData(i).getString("HOSPITAL_NAME"));
		            	 element.setHOSP_ADDRESS(ttkReportDataSource.getResultData(i).getString("HOSP_ADDRESS"));
		            	 element.setPLAN_NAME(ttkReportDataSource.getResultData(i).getString("PLAN_NAME"));
		            	 element.setPatient_number(ttkReportDataSource.getResultData(i).getString("PATIENT_NUMBER"));
		            	 element.setReceived_date(ttkReportDataSource.getResultData(i).getString("RECEIVED_DATE"));
		            	 element.setIntimation_date(ttkReportDataSource.getResultData(i).getString("INTIMATION_DATE"));           	 
		            	 element.setClm_docs_submit_in_days(ttkReportDataSource.getResultData(i).getString("CLM_DOCS_SUBMIT_IN_DAYS"));
		            	 element.setClm_docs_submit_in_days(ttkReportDataSource.getResultData(i).getString("REMINDER_LETTER_DAYS"));
		            	 element.setTtk_address_dtl(ttkReportDataSource.getResultData(i).getString("TTK_ADDRESS_DTL"));
		            	 element.setReceiver_email_id(ttkReportDataSource.getResultData(i).getString("RECEIVER_EMAIL_ID"));
		            	 element.setClaimant_name(ttkReportDataSource.getResultData(i).getString("CLAIMANT_NAME"));
		            	 element.setClause_number(ttkReportDataSource.getResultData(i).getString("CLAUSE_NUMBER"));
		            	 element.setCloser_notice_days(ttkReportDataSource.getResultData(i).getString("CLOSER_NOTICE_DAYS"));
		            	 element.setIntl_shortfal_snt_date(ttkReportDataSource.getResultData(i).getString("INTL_SHORTFAL_SNT_DATE"));
		            	 element.setRmdr_req_snt_date(ttkReportDataSource.getResultData(i).getString("RMDR_REQ_SNT_DATE"));   	
		            	 element.setCloser_notice_days(ttkReportDataSource.getResultData(i).getString("CLOSER_NOTICE_DAYS"));           	
		            	 element.setApproval_closer_days(ttkReportDataSource.getResultData(i).getString("APPROVAL_CLOSER_DAYS"));
		            	 element.setClsr_notice_snt_date(ttkReportDataSource.getResultData(i).getString("CLSR_NOTICE_SNT_DATE")); 
		            	 element.setReminder_draft(ttkReportDataSource.getResultData(i).getString("REMINDER_DRAFT")); 
		            	 element.setClm_clsr_letter_snt_date(ttkReportDataSource.getResultData(i).getString("CLM_CLSR_LETTER_SNT_DATE")); 
		            	 element.setRegrett_letter_days(ttkReportDataSource.getResultData(i).getString("REGRETT_LETTER_DAYS"));
		            	 element.setClaim_closer_letter_days(ttkReportDataSource.getResultData(i).getString("CLAIM_CLOSER_LETTER_DAYS"));
		            	 element.setClaim_type(ttkReportDataSource.getResultData(i).getString("CLAIM_TYPE"));
		            	 element.setInsured_mailid(ttkReportDataSource.getResultData(i).getString("INSURED_MAILID"));
		            	// String MobileNo=((ttkReportDataSource.getResultData(i).getString("MOBILE_NO").equalsIgnoreCase(""))?"NA":ttkReportDataSource.getResultData(i).getString("MOBILE_NO"));
		             	 element.setMobile_no(TTKCommon.checkNull(ttkReportDataSource.getResultData(i).getString("MOBILE_NO")));//added recently
		             	 element.setIns_mobile_no((TTKCommon.checkNull(ttkReportDataSource.getResultData(i).getString("INS_MOBILE_NO"))));//added recently
		             	 element.setPolicy_no(ttkReportDataSource.getResultData(i).getString("POLICY_NO"));//shortfall letter added new value
		             	 element.setSdate(ttkReportDataSource.getResultData(i).getString("SDATE"));//shortfall letter added new value
		             	 element.setAilment_description(ttkReportDataSource.getResultData(i).getString("ailment_description"));//shortfall phase1
		            	 element.setCorporate(ttkReportDataSource.getResultData(i).getString("corporate"));//shortfall phase1
		            	 element.setClaim_status(ttkReportDataSource.getResultData(i).getString("claim_status"));//shortfall phase1
		            	 element.setAgent_code(ttkReportDataSource.getResultData(i).getString("agent_code"));//shortfall phase1
		            	 element.setDev_office_code(ttkReportDataSource.getResultData(i).getString("dev_office_code"));//shortfall phase1
		            	 element.setPolicy_type(ttkReportDataSource.getResultData(i).getString("policy_type"));//shortfall phase1
		            	 element.setMem_age(ttkReportDataSource.getResultData(i).getString("mem_age"));//shortfall phase1
		            	 element.setGender(ttkReportDataSource.getResultData(i).getString("gender"));//shortfall phase1
		            	 element.setPre_auth_number(ttkReportDataSource.getResultData(i).getString("pre_auth_number"));//shortfall phase1
		            	 element.setPh_name(ttkReportDataSource.getResultData(i).getString("ph_name"));//shortfall phase1
		            	 element.setSubmit_days(ttkReportDataSource.getResultData(i).getString("submit_days"));//shortfall phase1
		            	// element.setClm_int_num(ttkReportDataSource.getResultData(i).getString("CLM_INT_NUM"));
		            	 element.setclm_int_num(ttkReportDataSource.getResultData(i).getString("clm_int_num"));
		            	 element.setemp_id(ttkReportDataSource.getResultData(i).getString("emp_id"));//shortfall phase1
		         	 	 element.setpost_hosp_days(ttkReportDataSource.getResultData(i).getString("post_hosp_days"));//shortfall phase1
		         	 	 element.setclm_int_date(ttkReportDataSource.getResultData(i).getString("clm_int_date"));//shortfall phase1
		         	 	element.setrcvd_thru(ttkReportDataSource.getResultData(i).getString("rcvd_thru"));//shortfall phase1
		         	 	element.setShortfall_partial_date(ttkReportDataSource.getResultData(i).getString("shortfall_partial_date"));//for shortfall


		            	 
           	
		    		     shortfallNumber= (String)ttkReportDataSource.getResultData(i).getString("SHORTFALL_NUMBER");
		            	 String sftype="";
		            	   
		            	 data.add(element);  
		            	 dataSource = new JRBeanCollectionDataSource(data, false);   
		            	 dataSource1 = new JRBeanCollectionDataSource(data, false);
		            	 
		            	 String strQuery = ttkReportDataSource.getResultData(i).getString("questions");
		            	 //data.add(element);
		            	 document = JRXmlUtils.parse(new ByteArrayInputStream(strQuery.getBytes()));
					     HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
					     hashMap1.put("MyDataSource",new JRXmlDataSource(document,"//query"));
					     hashMap1.get("MyDataSource");
					     hashMap1.put("DocumentShortAdvSub",xmljasperReport);
						 JasperFillManager.fillReport(xmljasperReport, hashMap1, new JRXmlDataSource(document,"//query"));
						 hashMap1.put("shortfalltest",xmljasperReport);
					     //dataSource = new JRBeanCollectionDataSource(data, false);  
						 TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
	            	     DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
	            	     df.setTimeZone(tz);  
	            	     String timestamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", "");
	     	 		     
	     	 		     //String scheduledFilesDestination=TTKPropertiesReader.getPropertyValue("shortfallrptdir")+finalFileName;
	     	 		     String scheduledFilesDestination = ""; 
	     	 		 
		            	   
		            	 if(jrxmlFiles[i].toString().equalsIgnoreCase("generalreports/ShortfallReminderLetter.jrxml"))
			             {
		            		 jasperPrint = JasperFillManager.fillReport(objJasperReport[i],hashMap1,dataSource);
		            		 sftype="RER";
		            		 String finalFileName=sftype+"-"+shortfallNumber+".pdf";
		            		 scheduledFilesDestination=TTKPropertiesReader.getPropertyValue("shortfallrptdir")+finalFileName;
		            		 JasperExportManager.exportReportToPdfFile(jasperPrint,scheduledFilesDestination);
			             }
		            	 //added for Mail-SMS Template
		            	 else  if(jrxmlFiles[i].toString().equalsIgnoreCase("generalreports/CignaShortfallFirstRemainderAdv.jrxml"))
			             {
		            		 jasperPrint = JasperFillManager.fillReport(objJasperReport[i],hashMap1,dataSource);
		            		 jasperReport1 = JasperCompileManager.compileReport("generalreports/CignaDocumentFirstShortfallRemainderPO.jrxml");
			     	 		 jasperPrint1 = JasperFillManager.fillReport(jasperReport1,hashMap1,dataSource1);
		            		 sftype="RER";
		            		 String finalFileName=sftype+"-"+shortfallNumber+".pdf";
		            		 scheduledFilesDestination=TTKPropertiesReader.getPropertyValue("shortfallrptdir")+finalFileName;
		            		 generateReport(jasperPrint,jasperPrint1,scheduledFilesDestination);
		            		 
			             }
			             else if(jrxmlFiles[i].toString().equalsIgnoreCase("generalreports/ShortfallClosureNotice.jrxml"))
			             {
			            	 jasperPrint = JasperFillManager.fillReport(objJasperReport[i],hashMap1,dataSource);
			            	 sftype="CNE";
			            	 String finalFileName=sftype+"-"+shortfallNumber+".pdf";
			            	 scheduledFilesDestination=TTKPropertiesReader.getPropertyValue("shortfallrptdir")+finalFileName;
			            	 JasperExportManager.exportReportToPdfFile(jasperPrint,scheduledFilesDestination);
				         }
			             else if(jrxmlFiles[i].toString().equalsIgnoreCase("generalreports/CignaShortfallSecondRemainderAdv.jrxml"))
			             {
			            	 jasperPrint = JasperFillManager.fillReport(objJasperReport[i],hashMap1,dataSource);
			            	 jasperReport1 = JasperCompileManager.compileReport("generalreports/CignaShortfallSecondRemainderPO.jrxml");
			     	 		 jasperPrint1 = JasperFillManager.fillReport(jasperReport1,hashMap1,dataSource1);
			            	 sftype="CNE";
			            	 String finalFileName=sftype+"-"+shortfallNumber+".pdf";
			            	 scheduledFilesDestination=TTKPropertiesReader.getPropertyValue("shortfallrptdir")+finalFileName;
			            	 generateReport(jasperPrint,jasperPrint1,scheduledFilesDestination);
				         }
			             else if(jrxmlFiles[i].toString().equalsIgnoreCase("generalreports/ShortfallClosureApproval.jrxml"))
			             {
			            	 jasperPrint = JasperFillManager.fillReport(objJasperReport[i],hashMap1,dataSource);
			            	 sftype="CLA";
			            	 String finalFileName=sftype+"-"+shortfallNumber+".pdf";
			            	 scheduledFilesDestination=TTKPropertiesReader.getPropertyValue("shortfallrptdir")+finalFileName;
			            	 JasperExportManager.exportReportToPdfFile(jasperPrint,scheduledFilesDestination);
				         }
			             else if(jrxmlFiles[i].toString().equalsIgnoreCase("generalreports/ShortfallClosureLetter.jrxml"))
			             {
			            	 jasperPrint = JasperFillManager.fillReport(objJasperReport[i],hashMap1,dataSource);
			            	 sftype="CLL";
			            	 String finalFileName=sftype+"-"+shortfallNumber+".pdf";
			            	 scheduledFilesDestination=TTKPropertiesReader.getPropertyValue("shortfallrptdir")+finalFileName;
			            	 JasperExportManager.exportReportToPdfFile(jasperPrint,scheduledFilesDestination);
				         }
		            	 
		     	 		 //String filename = generateReport(jasperPrint,jasperPrint1,scheduledFilesDestination);
                        //String filename = generateReport(jasperPrint,jasperPrint1,scheduledFilesDestination);
		     	 		 //JasperExportManager.exportReportToPdfStream(jasperPrint,boas);		            	
		     	 		 
		     	 		    
			   			 data.remove(element);
		      			 count++;         
		            }
 				    communicationManager.sendShortfallDetails();
 				    communicationManager.sendCignaPreauthShorfallDetails();
 				    log.info(" : Done : ");
				    
				 } 
				 
			 }//end of if(ttkReportDataSource.getResultData().next())
	
		}//end of try
		catch (JRException e)
		{
			e.printStackTrace();
		}//end of catch(JRException e)
		catch(Exception e){
			new Exception(strStatusError);
			e.printStackTrace();
		}//end of catch(Exception e)
		
	}//end of execute(JobExecutionContext arg0)
		 
	 public String generateReport(JasperPrint jasperPrint1, JasperPrint jasperPrint2,String strPdfFile) throws JRException {
		  //throw the JasperPrint Objects in a list
		  java.util.List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		  jasperPrintList.add(jasperPrint1);
		  jasperPrintList.add(jasperPrint2);

		  //ByteArrayOutputStream baos = new ByteArrayOutputStream();     
		  JRPdfExporter exporter = new JRPdfExporter();     
		  //Add the list as a Parameter
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		  //this will make a bookmark in the exported PDF for each of the reports
		  //exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
		  try {
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(new File(strPdfFile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  //exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);       
		  exporter.exportReport();      
		  //return baos.toByteArray();
		  return strPdfFile;
		}  
	 
	 
	 
	 
	 
	 
	/**
	 * Returns the CommunicationManager session object for invoking methods on it.
	 * @return CommunicationManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				 communicationManager = (CommunicationManager) ctx.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
        
			}//end of if(communicationManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strStatusError);
		}//end of catch
		return communicationManager;
	}//end getCommunicationManagerObject()	
}//end of ShortfallRptScheduler
