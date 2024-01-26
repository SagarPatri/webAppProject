/**
* @ (#) ClaimsSFCorrAction.java Jan 02, 2013
* Project       : TTK HealthCare Services
* File          : ClaimsSFCorrAction.java
* Author        : Manohar 
* Company       : RCS
* Date Created  : Jan 02, 2013

* @author       : Manohar
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.claims;

import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
//added for Mail-SMS Template for Cigna
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.naming.InitialContext;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
//added for Mail-SMS Template for Cigna
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRXmlUtils;
//added for Mail-SMS Template for Cigna
import net.sf.jasperreports.engine.JRException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.claims.ClaimShortfallVO;
import com.ttk.dto.claims.ShortfallRequestDetailsVO;
import com.ttk.dto.claims.ShortfallStatusVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of Claim Shortfall's to be sent Manually
 */

public class ClaimsSFCorrAction extends TTKAction
{
	private static Logger log = Logger.getLogger(ClaimsSFCorrAction.class);

	//Action mapping forwards.claimssfDetails
	private static final  String strClaimsSFCorr="claimssfcorr";
	private static final  String strClaimsSFEmail="shortfallsendemail";
	private static final  String strClaimsSFDetails="claimssfDetails";
	 private static final String strReportdisplay="reportdisplay";

    //Exception Message Identifier
    private static final String strClaimShortfallError="cliamsshortfall";

    /**
     * This method is used to initialize the search grid.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doDefault method of ClaimsAction");
    		setLinks(request);
    	//	String sEmailIDStatus=request.getParameter("sEmailIDStatus");
    		DynaActionForm frmClaimsSFCorr=(DynaActionForm)form;
    		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
			request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			
		
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("ClaimsSFCorrTable",new ArrayList());
			
			((DynaActionForm)form).initialize(mapping);//reset the form data
			frmClaimsSFCorr.set("sTtkBranch",strDefaultBranchID);
			request.getSession().setAttribute("sEmailIDStatus","NA");
			frmClaimsSFCorr.set("sEmailIDStatus","NA");
			request.getSession().setAttribute("sTtkBranch",strDefaultBranchID);
		    request.getSession().setAttribute("tableData",tableData);
			
			return this.getForward(strClaimsSFCorr, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
    /**
     * This method is used to generate the shortfall letter
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
		public ActionForward doGenerateSend(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								   HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside ClaimsSFCorrAction doGenerateSend");
    		DynaActionForm frmClaimsSFCorr = (DynaActionForm)form;
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    	
    		String sftype=request.getParameter("sShortfallStatus").trim();
    		String strShortfallEmailSeqID = getConcatenatedShortfallSeqID(request,(TableData)request.getSession().
    													  		   getAttribute("tableData"));
    		String strShortfallNoSeqID = getConcatenatedShortfallNoSeqID(request,(TableData)request.getSession().
			  		   getAttribute("tableData"));
    		
    		String strShortfallNoSeqIDArr[]= strShortfallNoSeqID.split("\\|");
    		String strShortfallEmailSeqIDArr[]=strShortfallEmailSeqID.split("\\|");
   
    		String EmailStatus=request.getParameter("sEmailIDStatus");
    		if(EmailStatus.equalsIgnoreCase("AVAI"))
    		{
    			EmailStatus="Email";
    		}
    		
    		ArrayList<Object> strArray = new ArrayList<Object>(); 
    		ArrayList<Object> strArray2 = new ArrayList<Object>();
    		String destFileName="";
    		StringBuffer strpassing=new StringBuffer();
    		for(int  i=0;i<strShortfallNoSeqIDArr.length;i++)
			{   			
    			strpassing.append("|").append(String.valueOf(strShortfallEmailSeqIDArr[i])); 			 
    			 destFileName=TTKPropertiesReader.getPropertyValue("shortfallrptdir")+sftype+"-"+strShortfallNoSeqIDArr[i]+".pdf";
    			strArray.add(destFileName); 
			}
    		strpassing.append("|");
    		String[] array = strArray.toArray(new String[strArray.size()]);
    		
    		JasperReport jasperReport,xmljasperReport,emptyReport;
    		JasperReport jasperReportAD;
    		JasperReport jasperReportPO;
			JasperPrint jasperPrint;
			JasperPrint jasperPrintAD;
			JasperPrint jasperPrintPO;
			TTKReportDataSource ttkReportDataSource= null;
			TTKReportDataSource ttkReportDataSource2= null;
			String jrxmlfile="";String jrxmlfileAD="";String jrxmlfilePO="";
			if(sftype.equalsIgnoreCase("RER"))
			{
				jrxmlfile="generalreports/ShortfallReminderLetter.jrxml";
				jrxmlfileAD="generalreports/CignaShortfallFirstRemainderAdv.jrxml"; 
				jrxmlfilePO="generalreports/CignaDocumentFirstShortfallRemainderPO.jrxml";
			}
			else if(sftype.equalsIgnoreCase("CNE"))
			{
				jrxmlfile="generalreports/ShortfallClosureNotice.jrxml";
				jrxmlfileAD="generalreports/CignaShortfallSecondRemainderAdv.jrxml";
				jrxmlfilePO="generalreports/CignaShortfallSecondRemainderPO.jrxml";
			}
			//other than cigna
			else if(sftype.equalsIgnoreCase("CLA"))
			{
				jrxmlfile="generalreports/ShortfallClosureApproval.jrxml";
			}
			else if(sftype.equalsIgnoreCase("CLL"))
			{
				jrxmlfile="generalreports/ShortfallClosureLetter.jrxml";
			}
			else if(sftype.equalsIgnoreCase("RGT"))
			{
				jrxmlfile="generalreports/ShortfallRegretLetter.jrxml";
			}
			else if(sftype.equalsIgnoreCase("ROR"))
			{
				jrxmlfile="generalreports/ShortfallReopenRecommending.jrxml";
			}
			else if(sftype.equalsIgnoreCase("RON"))
			{
				jrxmlfile="generalreports/ShortfallReopenNonRecommending.jrxml";
			}
			else if(sftype.equalsIgnoreCase("RWD"))
			{
				jrxmlfile="generalreports/ShortfallWaiver.jrxml";
			}
			else
			{
				jrxmlfile="generalreports/EmptyReprot.jrxml";	
			}
			//ended
			StringBuffer shortfallRequestType=new StringBuffer(sftype);
			//other than cigna case execute this procedure
			ttkReportDataSource = new TTKReportDataSource("AddressingShortfallRequests",strpassing.toString(),shortfallRequestType);
			//end
     		ttkReportDataSource2 = new TTKReportDataSource("CignaAddressingShortfallRequests",strpassing.toString(),shortfallRequestType);
			
     		//other than cigna compile this jrxml
     		//jasperReport = JasperCompileManager.compileReport(jrxmlfile);
     		//end
     		     		
			
			ByteArrayOutputStream boas=new ByteArrayOutputStream();	
			
			//other than cigna case
			ClaimShortfallVO element = null;
			ArrayList<ClaimShortfallVO> data = new ArrayList<ClaimShortfallVO>();		
			JRBeanCollectionDataSource dataSource = null;
			//end
						
			ClaimShortfallVO element2 = null;
			ArrayList<ClaimShortfallVO> data2 = new ArrayList<ClaimShortfallVO>();		
			//JRBeanCollectionDataSource dataSource = null;
			JRBeanCollectionDataSource dataSource2AD = null;
			JRBeanCollectionDataSource dataSource2PO = null;
			org.w3c.dom.Document document = null,document1 = null,document2 = null;
			
			String strPath = TTKPropertiesReader.getPropertyValue("SignatureImgPath");
			xmljasperReport = JasperCompileManager.compileReport("generalreports/ShortfallQuestions.jrxml");
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			
			//other than cigna case
			int count=0;
		    while(ttkReportDataSource.getResultData().next())
            {
            	 element = new ClaimShortfallVO();
            	 element.setTtk_address(ttkReportDataSource.getResultData().getString("ttk_address"));
            	 element.setAddress1(ttkReportDataSource.getResultData().getString("address1"));
            	 element.setAddress2(ttkReportDataSource.getResultData().getString("address2"));
            	 element.setAddress3(ttkReportDataSource.getResultData().getString("address3"));
            	 element.setCITY(ttkReportDataSource.getResultData().getString("city"));          	 
            	 element.setShortfall_number(ttkReportDataSource.getResultData().getString("shortfall_number"));
            	 element.setClaim_number(ttkReportDataSource.getResultData().getString("claim_number"));
            	 //element.setDate_of_admission(ttkReportDataSource.getResultData().getDate("date_of_admission"));
            	 element.setDate_of_admission(ttkReportDataSource.getResultData().getString("date_of_admission"));
            	 element.setDate_of_discharge(ttkReportDataSource.getResultData().getString("date_of_discharge"));
            	 element.setPolicy_number(ttkReportDataSource.getResultData().getString("policy_number"));          	 
            	 element.setTpa_enrollment_id(ttkReportDataSource.getResultData().getString("tpa_enrollment_id"));
            	 element.setTpa_enrollment_number(ttkReportDataSource.getResultData().getString("tpa_enrollment_number"));
            	 element.setEmail_id(ttkReportDataSource.getResultData().getString("email_id"));
            	 element.setHospital_name(ttkReportDataSource.getResultData().getString("hospital_name"));
            	 element.setPatient_number(ttkReportDataSource.getResultData().getString("patient_number"));
            	 element.setReceived_date(ttkReportDataSource.getResultData().getString("received_date"));
            	 element.setIntimation_date(ttkReportDataSource.getResultData().getString("intimation_date"));           	 
            	 element.setClm_docs_submit_in_days(ttkReportDataSource.getResultData().getString("clm_docs_submit_in_days"));
            	 element.setClm_docs_submit_in_days(ttkReportDataSource.getResultData().getString("reminder_letter_days"));
            	 element.setTtk_address_dtl(ttkReportDataSource.getResultData().getString("ttk_address_dtl"));
            	 element.setReceiver_email_id(ttkReportDataSource.getResultData().getString("receiver_email_id"));
            	 element.setClaimant_name(ttkReportDataSource.getResultData().getString("claimant_name"));
            	 element.setClause_number(ttkReportDataSource.getResultData().getString("clause_number"));
            	 element.setCloser_notice_days(ttkReportDataSource.getResultData().getString("closer_notice_days"));
            	 element.setIntl_shortfal_snt_date(ttkReportDataSource.getResultData().getString("intl_shortfal_snt_date"));
            	 element.setRmdr_req_snt_date(ttkReportDataSource.getResultData().getString("rmdr_req_snt_date"));   	
            	 element.setCloser_notice_days(ttkReportDataSource.getResultData().getString("closer_notice_days"));           	
            	 element.setApproval_closer_days(ttkReportDataSource.getResultData().getString("approval_closer_days"));
            	 element.setClsr_notice_snt_date(ttkReportDataSource.getResultData().getString("clsr_notice_snt_date")); 
            	 element.setReminder_draft(ttkReportDataSource.getResultData().getString("reminder_draft")); 
            	 element.setClm_clsr_letter_snt_date(ttkReportDataSource.getResultData().getString("clm_clsr_letter_snt_date")); 
            	 element.setRegrett_letter_days(ttkReportDataSource.getResultData().getString("regrett_letter_days"));
            	 element.setClaim_closer_letter_days(ttkReportDataSource.getResultData().getString("claim_closer_letter_days"));
            	 element.setToll_free_no(ttkReportDataSource.getResultData().getString("toll_free_no"));
            	 element.setShortfall_raised_by(ttkReportDataSource.getResultData().getString("shortfall_raised_by"));
            	 element.setShortfall_raised_for(ttkReportDataSource.getResultData().getString("shortfall_raised_for"));
            	 element.setClaim_type(ttkReportDataSource.getResultData().getString("claim_type"));
            	 element.setInsured_mailid(ttkReportDataSource.getResultData().getString("insured_mailid"));
            	 
            	 element.setMobile_no(ttkReportDataSource.getResultData().getString("mobile_no"));//added recently
             	 element.setIns_mobile_no((TTKCommon.checkNull(ttkReportDataSource.getResultData().getString("ins_mobile_no"))));//added recently
				 element.setPolicy_no(ttkReportDataSource.getResultData().getString("POLICY_NO"));//shortfall letter added new value
            	 element.setSdate(ttkReportDataSource.getResultData().getString("SDATE"));//shortfall letter added new value
            	 element.setAilment_description(ttkReportDataSource.getResultData().getString("ailment_description"));//shortfall phase1
            	 element.setCorporate(ttkReportDataSource.getResultData().getString("corporate"));//shortfall phase1
            	 element.setClaim_status(ttkReportDataSource.getResultData().getString("claim_status"));//shortfall phase1
            	 element.setAgent_code(ttkReportDataSource.getResultData().getString("agent_code"));//shortfall phase1
            	 element.setDev_office_code(ttkReportDataSource.getResultData().getString("dev_office_code"));//shortfall phase1
            	 element.setPolicy_type(ttkReportDataSource.getResultData().getString("policy_type"));//shortfall phase1
            	 element.setMem_age(ttkReportDataSource.getResultData().getString("mem_age"));//shortfall phase1
            	 element.setGender(ttkReportDataSource.getResultData().getString("gender"));//shortfall phase1
            	 element.setPre_auth_number(ttkReportDataSource.getResultData().getString("pre_auth_number"));//shortfall phase1
            	 element.setPh_name(ttkReportDataSource.getResultData().getString("ph_name"));//shortfall phase1
            	 element.setSubmit_days(ttkReportDataSource.getResultData().getString("submit_days"));//shortfall phase1
            	 element.setemp_id(ttkReportDataSource.getResultData().getString("emp_id"));//shortfall phase1
         	 	 element.setpost_hosp_days(ttkReportDataSource.getResultData().getString("post_hosp_days"));//shortfall phase1
         	 	 element.setclm_int_date(ttkReportDataSource.getResultData().getString("clm_int_date"));//shortfall phase1
         	 	element.setrcvd_thru(ttkReportDataSource.getResultData().getString("rcvd_thru"));//shortfall phase1
         	 	element.setShortfall_partial_date(ttkReportDataSource.getResultData().getString("shortfall_partial_date"));//for shortfall


            	 //added newly
            	 String shortfallNumber= (String)ttkReportDataSource.getResultData().getString("SHORTFALL_NUMBER");
            	 //ended	
            	 // Based on db value(General or Partial) generating a Shortfall Letter 
            	if(sftype.equalsIgnoreCase("RER") && element.getReminder_draft().equalsIgnoreCase("General"))
     			{
     				jrxmlfile="generalreports/ShortfallReminderLetter.jrxml";
     			}
            	else if(sftype.equalsIgnoreCase("RER") && element.getReminder_draft().equalsIgnoreCase("Partial"))
            	{
            		jrxmlfile="generalreports/ShortfallPartialReminderLetter.jrxml";
            	}
            	jasperReport = JasperCompileManager.compileReport(jrxmlfile);	
            	 
            	String strQuery = ttkReportDataSource.getResultData().getString("questions");
            	String strReceiveddocs = ttkReportDataSource.getResultData().getString("received_docs");
            	String strPendingdocs = ttkReportDataSource.getResultData().getString("pending_docs");
            	
            	data.add(element);
            		 document = JRXmlUtils.parse(new ByteArrayInputStream(strQuery.getBytes()));
					 document1 = JRXmlUtils.parse(new ByteArrayInputStream(strReceiveddocs.getBytes()));
					 document2 = JRXmlUtils.parse(new ByteArrayInputStream(strPendingdocs.getBytes()));
					 
					 HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
					 hashMap1.put("MyDataSource",new JRXmlDataSource(document,"//query"));
					 hashMap1.put("MyDataSource1",new JRXmlDataSource(document1,"//query"));
					 hashMap1.put("MyDataSource2",new JRXmlDataSource(document2,"//query"));
					 
					 hashMap1.get("MyDataSource");
					 JasperFillManager.fillReport(xmljasperReport, hashMap1, new JRXmlDataSource(document,"//query"));
					 JasperFillManager.fillReport(xmljasperReport, hashMap1, new JRXmlDataSource(document1,"//query"));
					 JasperFillManager.fillReport(xmljasperReport, hashMap1, new JRXmlDataSource(document2,"//query"));
					 hashMap1.put("shortfalltest",xmljasperReport);
					 	
					 //added by Satya aas per latest changes
					 hashMap1.put("SigPath",strPath);
					//added by Satya
	            	 dataSource = new JRBeanCollectionDataSource(data, false);  
	            	 jasperPrint = JasperFillManager.fillReport(jasperReport,hashMap1,dataSource);
	            	 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);		
	      			 //JasperExportManager.exportReportToPdfFile(jasperPrint,array[count].toString());
	            	 JasperExportManager.exportReportToPdfFile(jasperPrint,TTKPropertiesReader.getPropertyValue("shortfallrptdir")+sftype+"-"+shortfallNumber+".pdf");
					
	      			 data.remove(element);
	      			 count++;         
            }
            //ended
						
			int count2=0;
			while(ttkReportDataSource2.getResultData().next())
		    {
           	 	element2 = new ClaimShortfallVO();
           	 	element2.setAddress1(ttkReportDataSource2.getResultData().getString("address1"));
				element2.setAddress2(ttkReportDataSource2.getResultData().getString("address2"));
				element2.setAddress3(ttkReportDataSource2.getResultData().getString("address3"));
				element2.setClaim_number(ttkReportDataSource2.getResultData().getString("CLAIM_NUMBER"));
				
							
							//newly added							
				element2.setShortfall_number(ttkReportDataSource2.getResultData().getString("SHORTFALL_NUMBER"));
				element2.setCITY(ttkReportDataSource2.getResultData().getString("CITY"));
				element2.setPh_name(ttkReportDataSource2.getResultData().getString("PH_NAME"));
				element2.setLTR_GEN_DATE(ttkReportDataSource2.getResultData().getString("LTR_GEN_DATE"));
				element2.setMEM_PIN_CODE(ttkReportDataSource2.getResultData().getString("MEM_PIN_CODE"));
				element2.setMEM_STATE(ttkReportDataSource2.getResultData().getString("MEM_STATE"));
				element2.setPolicy_number(ttkReportDataSource2.getResultData().getString("POLICY_NUMBER"));
				element2.setPLAN_NAME(ttkReportDataSource2.getResultData().getString("PLAN_NAME"));
				element2.setCLM_REG_DATE(ttkReportDataSource2.getResultData().getString("CLM_REG_DATE"));
				element2.setAilment_description(ttkReportDataSource2.getResultData().getString("AILMENT_DESCRIPTION"));
				element2.setADVISOR_NAME(ttkReportDataSource2.getResultData().getString("ADVISOR_NAME"));
				//element2.setIntl_shortfal_snt_date(ttkReportDataSource2.getResultData().getString("intl_snt_date"));
				//element2.setRmdr_req_snt_date(ttkReportDataSource2.getResultData().getString("rmdr1_snt_date"));
				element2.setClaimant_name(ttkReportDataSource2.getResultData().getString("CLAIMANT_NAME"));
				element2.setHospital_name(ttkReportDataSource2.getResultData().getString("HOSPITAL_NAME"));
				element2.setHOSP_ADDRESS(ttkReportDataSource2.getResultData().getString("HOSP_ADDRESS"));
				element2.setReceiver_email_id(ttkReportDataSource2.getResultData().getString("receiver_email_id"));	
				element2.setIntl_shortfal_snt_date(ttkReportDataSource2.getResultData().getString("intl_shortfal_snt_date"));
     			element2.setRmdr_req_snt_date(ttkReportDataSource2.getResultData().getString("rmdr_req_snt_date"));
			    element2.setClsr_notice_snt_date(ttkReportDataSource2.getResultData().getString("clsr_notice_snt_date"));  
			   // element2.setClm_int_num(ttkReportDataSource2.getResultData().getString("CLM_INT_NUM"));
			    element2.setclm_int_num(ttkReportDataSource2.getResultData().getString("clm_int_num"));
				String shortfallNumber= (String)ttkReportDataSource2.getResultData().getString("SHORTFALL_NUMBER");
           	
				
			    jasperReportAD = JasperCompileManager.compileReport(jrxmlfileAD);
			    jasperReportPO = JasperCompileManager.compileReport(jrxmlfilePO);	
           	 
			    String strQuery = ttkReportDataSource2.getResultData().getString("questions");
			    data2.add(element2);
			    
			     
			    
			    //document3 = JRXmlUtils.parse(new ByteArrayInputStream(strQuery.getBytes()));
					 
			    dataSource2AD = new JRBeanCollectionDataSource(data2, false);  
			    dataSource2PO = new JRBeanCollectionDataSource(data2, false);

		            	 
			    //added for SubReport
			    HashMap<String, Object> hashMap =new HashMap<String, Object>();
			    xmljasperReport = JasperCompileManager.compileReport("generalreports/CignaDocumentShortAdvSub.jrxml");
			    hashMap.put("MyDataSource",new TTKReportDataSource("CignaSubAddressingShortfallRequests",strpassing.toString()));
			    hashMap.get("MyDataSource");
			    hashMap.put("DocumentShortAdvSub",xmljasperReport);
			    //JasperFillManager.fillReport(xmljasperReport, hashMap, new JRXmlDataSource(document3,"//query"));	
        				
			    jasperPrintAD = JasperFillManager.fillReport(jasperReportAD,hashMap,dataSource2AD);
			    jasperPrintPO = JasperFillManager.fillReport(jasperReportPO,hashMap,dataSource2PO);
			    //String finalFileName=sftype+"-"+shortfallNumber+".pdf";
               generateReport(jasperPrintAD,jasperPrintPO,TTKPropertiesReader.getPropertyValue("shortfallrptdir")+sftype+"-"+shortfallNumber+".pdf"); 
	            
			    data2.remove(element2);
			    count2++;         
           
		    }         
		    String USER_ID = "";
		  //  long UpdatedBy=0;
		    long UserSeqId= TTKCommon.getUserSeqId(request);
		    long rowsprocessed=claimManagerObject.sendShortfallDetails(strpassing.toString(),sftype,EmailStatus,UserSeqId);
		    if(rowsprocessed>0)
			{
				request.setAttribute("updated","message.saved");
				doSearch(mapping,form,request,response);
			}
    		return mapping.findForward(strClaimsSFCorr);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strClaimsSFCorr));
		}//end of catch(Exception exp)
    }//end of doGenerateSend(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)  

     /**
     * This method is used to generate and print the shortfall letter to the printer configured.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    
	  public ActionForward doGeneratePrint(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws Exception{
			try{
			setLinks(request);
			log.debug("Inside ClaimsSFCorrAction doGenerateSend");
			DynaActionForm frmClaimsSFCorr = (DynaActionForm)form;
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			
			String sftype=request.getParameter("sShortfallStatus").trim();
			String strShortfallEmailSeqID = getConcatenatedShortfallSeqID(request,(TableData)request.getSession().
											  		   getAttribute("tableData"));
			String strShortfallNoSeqID = getConcatenatedShortfallNoSeqID(request,(TableData)request.getSession().
			getAttribute("tableData"));
			
			String strShortfallNoSeqIDArr[]= strShortfallNoSeqID.split("\\|");
			String strShortfallEmailSeqIDArr[]=strShortfallEmailSeqID.split("\\|");
			
			String EmailStatus=request.getParameter("sEmailIDStatus");
			if(EmailStatus.equalsIgnoreCase("AVAI"))
			{
			EmailStatus="Email";
			}
			
			ArrayList<Object> strArray = new ArrayList<Object>(); 
			ArrayList<InputStream> filesShrtFullPath = new ArrayList<InputStream>();
			ArrayList<Object> strArray2 = new ArrayList<Object>();
			String destFileName="";
			StringBuffer strpassing=new StringBuffer();
			for(int  i=0;i<strShortfallNoSeqIDArr.length;i++)
			{   			
			strpassing.append("|").append(String.valueOf(strShortfallEmailSeqIDArr[i])); 			 
			destFileName=TTKPropertiesReader.getPropertyValue("shortfallrptdir")+sftype+"-"+strShortfallNoSeqIDArr[i]+".pdf";
			strArray.add(destFileName); 
			}
			strpassing.append("|");
			String[] array = strArray.toArray(new String[strArray.size()]);
			
			JasperReport jasperReport,xmljasperReport,emptyReport;
			JasperReport jasperReportAD;
			JasperReport jasperReportPO;
			JasperPrint jasperPrint;
			JasperPrint jasperPrintAD;
			JasperPrint jasperPrintPO;
			TTKReportDataSource ttkReportDataSource= null;
			TTKReportDataSource ttkReportDataSource2= null;
			String jrxmlfile="";String jrxmlfileAD="";String jrxmlfilePO="";
			if(sftype.equalsIgnoreCase("RER"))
			{
			jrxmlfile="generalreports/ShortfallReminderLetter.jrxml";
			jrxmlfileAD="generalreports/CignaShortfallFirstRemainderAdv.jrxml"; 
			jrxmlfilePO="generalreports/CignaDocumentFirstShortfallRemainderPO.jrxml";
			}
			else if(sftype.equalsIgnoreCase("CNE"))
			{
			jrxmlfile="generalreports/ShortfallClosureNotice.jrxml";
			jrxmlfileAD="generalreports/CignaShortfallSecondRemainderAdv.jrxml";
			jrxmlfilePO="generalreports/CignaShortfallSecondRemainderPO.jrxml";
			}
			//other than cigna
			else if(sftype.equalsIgnoreCase("CLA"))
			{
			jrxmlfile="generalreports/ShortfallClosureApproval.jrxml";
			}
			else if(sftype.equalsIgnoreCase("CLL"))
			{
			jrxmlfile="generalreports/ShortfallClosureLetter.jrxml";
			}
			else if(sftype.equalsIgnoreCase("RGT"))
			{
			jrxmlfile="generalreports/ShortfallRegretLetter.jrxml";
			}
			else if(sftype.equalsIgnoreCase("ROR"))
			{
			jrxmlfile="generalreports/ShortfallReopenRecommending.jrxml";
			}
			else if(sftype.equalsIgnoreCase("RON"))
			{
			jrxmlfile="generalreports/ShortfallReopenNonRecommending.jrxml";
			}
			else if(sftype.equalsIgnoreCase("RWD"))
			{
			jrxmlfile="generalreports/ShortfallWaiver.jrxml";
			}
			else
			{
			jrxmlfile="generalreports/EmptyReprot.jrxml";	
			}
			//ended
			StringBuffer shortfallRequestType=new StringBuffer(sftype);
			//other than cigna case execute this procedure
			ttkReportDataSource = new TTKReportDataSource("AddressingShortfallRequests",strpassing.toString(),shortfallRequestType);
			//end
			ttkReportDataSource2 = new TTKReportDataSource("CignaAddressingShortfallRequests",strpassing.toString(),shortfallRequestType);
			
			//other than cigna compile this jrxml
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			//end
			
			
			ByteArrayOutputStream boas=new ByteArrayOutputStream();	
			
			//other than cigna case
			ClaimShortfallVO element = null;
			ArrayList<ClaimShortfallVO> data = new ArrayList<ClaimShortfallVO>();		
			JRBeanCollectionDataSource dataSource = null;
			//end
			
			ClaimShortfallVO element2 = null;
			ArrayList<ClaimShortfallVO> data2 = new ArrayList<ClaimShortfallVO>();		
			//JRBeanCollectionDataSource dataSource = null;
			JRBeanCollectionDataSource dataSource2AD = null;
			JRBeanCollectionDataSource dataSource2PO = null;
			org.w3c.dom.Document document = null,document1 = null,document2 = null;
			
			String strPath = TTKPropertiesReader.getPropertyValue("SignatureImgPath");
			xmljasperReport = JasperCompileManager.compileReport("generalreports/ShortfallQuestions.jrxml");
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			
			//other than cigna case
			int count=0;
			while(ttkReportDataSource.getResultData().next())
			{ 
			element = new ClaimShortfallVO();
			element.setTtk_address(ttkReportDataSource.getResultData().getString("ttk_address"));
			element.setAddress1(ttkReportDataSource.getResultData().getString("address1"));
			element.setAddress2(ttkReportDataSource.getResultData().getString("address2"));
			element.setAddress3(ttkReportDataSource.getResultData().getString("address3"));
			element.setCITY(ttkReportDataSource.getResultData().getString("city"));          	 
			element.setShortfall_number(ttkReportDataSource.getResultData().getString("shortfall_number"));
			element.setClaim_number(ttkReportDataSource.getResultData().getString("claim_number"));
			//element.setDate_of_admission(ttkReportDataSource.getResultData().getDate("date_of_admission"));
			element.setDate_of_admission(ttkReportDataSource.getResultData().getString("date_of_admission"));
			element.setDate_of_discharge(ttkReportDataSource.getResultData().getString("date_of_discharge"));
			element.setPolicy_number(ttkReportDataSource.getResultData().getString("policy_number"));          	 
			element.setTpa_enrollment_id(ttkReportDataSource.getResultData().getString("tpa_enrollment_id"));
			element.setTpa_enrollment_number(ttkReportDataSource.getResultData().getString("tpa_enrollment_number"));
			element.setEmail_id(ttkReportDataSource.getResultData().getString("email_id"));
			element.setHospital_name(ttkReportDataSource.getResultData().getString("hospital_name"));
			element.setPatient_number(ttkReportDataSource.getResultData().getString("patient_number"));
			element.setReceived_date(ttkReportDataSource.getResultData().getString("received_date"));
			element.setIntimation_date(ttkReportDataSource.getResultData().getString("intimation_date"));           	 
			element.setClm_docs_submit_in_days(ttkReportDataSource.getResultData().getString("clm_docs_submit_in_days"));
			element.setClm_docs_submit_in_days(ttkReportDataSource.getResultData().getString("reminder_letter_days"));
			element.setTtk_address_dtl(ttkReportDataSource.getResultData().getString("ttk_address_dtl"));
			element.setReceiver_email_id(ttkReportDataSource.getResultData().getString("receiver_email_id"));
			element.setClaimant_name(ttkReportDataSource.getResultData().getString("claimant_name"));
			element.setClause_number(ttkReportDataSource.getResultData().getString("clause_number"));
			element.setCloser_notice_days(ttkReportDataSource.getResultData().getString("closer_notice_days"));
			element.setIntl_shortfal_snt_date(ttkReportDataSource.getResultData().getString("intl_shortfal_snt_date"));
			element.setRmdr_req_snt_date(ttkReportDataSource.getResultData().getString("rmdr_req_snt_date"));   	
			element.setCloser_notice_days(ttkReportDataSource.getResultData().getString("closer_notice_days"));           	
			element.setApproval_closer_days(ttkReportDataSource.getResultData().getString("approval_closer_days"));
			element.setClsr_notice_snt_date(ttkReportDataSource.getResultData().getString("clsr_notice_snt_date")); 
			element.setReminder_draft(ttkReportDataSource.getResultData().getString("reminder_draft")); 
			element.setClm_clsr_letter_snt_date(ttkReportDataSource.getResultData().getString("clm_clsr_letter_snt_date")); 
			element.setRegrett_letter_days(ttkReportDataSource.getResultData().getString("regrett_letter_days"));
			element.setClaim_closer_letter_days(ttkReportDataSource.getResultData().getString("claim_closer_letter_days"));
			element.setToll_free_no(ttkReportDataSource.getResultData().getString("toll_free_no"));
			element.setShortfall_raised_by(ttkReportDataSource.getResultData().getString("shortfall_raised_by"));
			element.setShortfall_raised_for(ttkReportDataSource.getResultData().getString("shortfall_raised_for"));
			element.setClaim_type(ttkReportDataSource.getResultData().getString("claim_type"));
			element.setInsured_mailid(ttkReportDataSource.getResultData().getString("insured_mailid"));
			
			element.setMobile_no(ttkReportDataSource.getResultData().getString("mobile_no"));//added recently
			element.setIns_mobile_no((TTKCommon.checkNull(ttkReportDataSource.getResultData().getString("ins_mobile_no"))));//added recently
			element.setPolicy_no(ttkReportDataSource.getResultData().getString("POLICY_NO"));//shortfall letter added new value
			element.setSdate(ttkReportDataSource.getResultData().getString("SDATE"));//shortfall letter added new value
			element.setAilment_description(ttkReportDataSource.getResultData().getString("ailment_description"));//shortfall phase1
       	 	element.setCorporate(ttkReportDataSource.getResultData().getString("corporate"));//shortfall phase1
       	 	element.setClaim_status(ttkReportDataSource.getResultData().getString("claim_status"));//shortfall phase1
       	 	element.setAgent_code(ttkReportDataSource.getResultData().getString("agent_code"));//shortfall phase1
       	 	element.setDev_office_code(ttkReportDataSource.getResultData().getString("dev_office_code"));//shortfall phase1
       	 	element.setPolicy_type(ttkReportDataSource.getResultData().getString("policy_type"));//shortfall phase1
       	 	element.setMem_age(ttkReportDataSource.getResultData().getString("mem_age"));//shortfall phase1
       	 	element.setGender(ttkReportDataSource.getResultData().getString("gender"));//shortfall phase1
       	 	element.setPre_auth_number(ttkReportDataSource.getResultData().getString("pre_auth_number"));//shortfall phase1
       	 	element.setPh_name(ttkReportDataSource.getResultData().getString("ph_name"));//shortfall phase1
       	 	element.setSubmit_days(ttkReportDataSource.getResultData().getString("submit_days"));//shortfall phase1
       	 	element.setemp_id(ttkReportDataSource.getResultData().getString("emp_id"));//shortfall phase1
    	 	element.setpost_hosp_days(ttkReportDataSource.getResultData().getString("post_hosp_days"));//shortfall phase1
    	 	element.setclm_int_date(ttkReportDataSource.getResultData().getString("clm_int_date"));//shortfall phase1
    	 	element.setrcvd_thru(ttkReportDataSource.getResultData().getString("rcvd_thru"));//shortfall phase1
    	 	element.setShortfall_partial_date(ttkReportDataSource.getResultData().getString("shortfall_partial_date"));//for shortfall

			
			String shortfallNumber= (String)ttkReportDataSource.getResultData().getString("SHORTFALL_NUMBER");
			// Based on db value(General or Partial) generating a Shortfall Letter 
			if(sftype.equalsIgnoreCase("RER") && element.getReminder_draft().equalsIgnoreCase("General"))
			{
			jrxmlfile="generalreports/ShortfallReminderLetter.jrxml";
			}
			else if(sftype.equalsIgnoreCase("RER") && element.getReminder_draft().equalsIgnoreCase("Partial"))
			{
			jrxmlfile="generalreports/ShortfallPartialReminderLetter.jrxml";
			}
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);	
			
			String strQuery = ttkReportDataSource.getResultData().getString("questions");
			String strReceiveddocs = ttkReportDataSource.getResultData().getString("received_docs");
			String strPendingdocs = ttkReportDataSource.getResultData().getString("pending_docs");
			
			data.add(element);
			document = JRXmlUtils.parse(new ByteArrayInputStream(strQuery.getBytes()));
			document1 = JRXmlUtils.parse(new ByteArrayInputStream(strReceiveddocs.getBytes()));
			document2 = JRXmlUtils.parse(new ByteArrayInputStream(strPendingdocs.getBytes()));
			
			HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
			hashMap1.put("MyDataSource",new JRXmlDataSource(document,"//query"));
			hashMap1.put("MyDataSource1",new JRXmlDataSource(document1,"//query"));
			hashMap1.put("MyDataSource2",new JRXmlDataSource(document2,"//query"));
			
			hashMap1.get("MyDataSource");
			JasperFillManager.fillReport(xmljasperReport, hashMap1, new JRXmlDataSource(document,"//query"));
			JasperFillManager.fillReport(xmljasperReport, hashMap1, new JRXmlDataSource(document1,"//query"));
			JasperFillManager.fillReport(xmljasperReport, hashMap1, new JRXmlDataSource(document2,"//query"));
			hashMap1.put("shortfalltest",xmljasperReport);
			
			//added by Satya aas per latest changes
			hashMap1.put("SigPath",strPath);
			//added by Satya
			dataSource = new JRBeanCollectionDataSource(data, false);  
			jasperPrint = JasperFillManager.fillReport(jasperReport,hashMap1,dataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint,boas);		
			JasperExportManager.exportReportToPdfFile(jasperPrint,TTKPropertiesReader.getPropertyValue("shortfallrptdir")+sftype+"-"+shortfallNumber+".pdf");
			filesShrtFullPath.add(new FileInputStream(new File(TTKPropertiesReader.getPropertyValue("shortfallrptdir")+sftype+"-"+shortfallNumber+".pdf")));
			data.remove(element);
			count++;         
			}
			//ended
			
			int count2=0;
			while(ttkReportDataSource2.getResultData().next())
			{
			element2 = new ClaimShortfallVO();
			element2.setAddress1(ttkReportDataSource2.getResultData().getString("address1"));
			element2.setAddress2(ttkReportDataSource2.getResultData().getString("address2"));
			element2.setAddress3(ttkReportDataSource2.getResultData().getString("address3"));
			element2.setClaim_number(ttkReportDataSource2.getResultData().getString("CLAIM_NUMBER"));						
			//newly added							
			element2.setShortfall_number(ttkReportDataSource2.getResultData().getString("SHORTFALL_NUMBER"));
			element2.setCITY(ttkReportDataSource2.getResultData().getString("CITY"));
			element2.setPh_name(ttkReportDataSource2.getResultData().getString("PH_NAME"));
			element2.setLTR_GEN_DATE(ttkReportDataSource2.getResultData().getString("LTR_GEN_DATE"));
			element2.setMEM_PIN_CODE(ttkReportDataSource2.getResultData().getString("MEM_PIN_CODE"));
			element2.setMEM_STATE(ttkReportDataSource2.getResultData().getString("MEM_STATE"));
			element2.setPolicy_number(ttkReportDataSource2.getResultData().getString("POLICY_NUMBER"));
			element2.setPLAN_NAME(ttkReportDataSource2.getResultData().getString("PLAN_NAME"));
			element2.setCLM_REG_DATE(ttkReportDataSource2.getResultData().getString("CLM_REG_DATE"));
			element2.setAilment_description(ttkReportDataSource2.getResultData().getString("AILMENT_DESCRIPTION"));
			element2.setADVISOR_NAME(ttkReportDataSource2.getResultData().getString("ADVISOR_NAME"));
			//element2.setIntl_shortfal_snt_date(ttkReportDataSource2.getResultData().getString("intl_snt_date"));
			//element2.setRmdr_req_snt_date(ttkReportDataSource2.getResultData().getString("rmdr1_snt_date"));
			element2.setClaimant_name(ttkReportDataSource2.getResultData().getString("CLAIMANT_NAME"));
			element2.setHospital_name(ttkReportDataSource2.getResultData().getString("HOSPITAL_NAME"));
			element2.setHOSP_ADDRESS(ttkReportDataSource2.getResultData().getString("HOSP_ADDRESS"));
			element2.setReceiver_email_id(ttkReportDataSource2.getResultData().getString("receiver_email_id"));	
			element2.setIntl_shortfal_snt_date(ttkReportDataSource2.getResultData().getString("intl_shortfal_snt_date"));
 			element2.setRmdr_req_snt_date(ttkReportDataSource2.getResultData().getString("rmdr_req_snt_date"));
		    element2.setClsr_notice_snt_date(ttkReportDataSource2.getResultData().getString("clsr_notice_snt_date"));  
		   // element2.setClm_int_num(ttkReportDataSource2.getResultData().getString("CLM_INT_NUM"));
		    element2.setclm_int_num(ttkReportDataSource2.getResultData().getString("clm_int_num"));
		
			String shortfallNumber= (String)ttkReportDataSource2.getResultData().getString("SHORTFALL_NUMBER");
			jasperReportAD = JasperCompileManager.compileReport(jrxmlfileAD);
			jasperReportPO = JasperCompileManager.compileReport(jrxmlfilePO);	
			
			String strQuery = ttkReportDataSource2.getResultData().getString("questions");
			data2.add(element2);
			//document3 = JRXmlUtils.parse(new ByteArrayInputStream(strQuery.getBytes()));
			
			dataSource2AD = new JRBeanCollectionDataSource(data2, false);  
			dataSource2PO = new JRBeanCollectionDataSource(data2, false);
			
			
			//added for SubReport
			HashMap<String, Object> hashMap =new HashMap<String, Object>();
			//xmljasperReport = JasperCompileManager.compileReport("generalreports/ShortfallQuestions.jrxml");
			xmljasperReport = JasperCompileManager.compileReport("generalreports/CignaDocumentShortAdvSub.jrxml");
			hashMap.put("MyDataSource",new TTKReportDataSource("CignaSubAddressingShortfallRequests",strpassing.toString()));
			hashMap.get("MyDataSource");
			hashMap.put("DocumentShortAdvSub",xmljasperReport);
			//JasperFillManager.fillReport(xmljasperReport, hashMap, new JRXmlDataSource(document3,"//query"));	
			
			jasperPrintAD = JasperFillManager.fillReport(jasperReportAD,hashMap,dataSource2AD);
			jasperPrintPO = JasperFillManager.fillReport(jasperReportPO,hashMap,dataSource2PO);
			//String finalFileName=sftype+"-"+shortfallNumber+".pdf";
			generateReport(jasperPrintAD,jasperPrintPO,TTKPropertiesReader.getPropertyValue("shortfallrptdir")+sftype+"-"+shortfallNumber+".pdf"); 
			filesShrtFullPath.add(new FileInputStream(new File(TTKPropertiesReader.getPropertyValue("shortfallrptdir")+sftype+"-"+shortfallNumber+".pdf")));
			data2.remove(element2);
			count2++;         
			
			}         
			String strResult="";
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
			df.setTimeZone(tz);  
		    String 	fl=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
		    String finalFileName="SLF_"+sftype+"_"+fl+"_Batch"+".pdf";
		    String bulkDestination=TTKPropertiesReader.getPropertyValue("bulkshortfalldir")+finalFileName;
		    OutputStream outputStream =new FileOutputStream(new File(bulkDestination)); 
		    long UserSeqId= TTKCommon.getUserSeqId(request);
		    long rowsprocessed=claimManagerObject.saveShortFallFileName(finalFileName,UserSeqId,strpassing.toString(),sftype);
		 
		    //  String filename=bulkDestination;
		    doMergePDF(filesShrtFullPath, outputStream);
		    String USER_ID = "";
	
			if(rowsprocessed>0)
			{
				request.setAttribute("fileName",bulkDestination);
				request.setAttribute("updated","message.saved");
				doSearch(mapping,form,request,response);
			}
			request.setAttribute("fileName",bulkDestination);
			request.setAttribute("batchNumber",rowsprocessed);
    		return mapping.findForward(strClaimsSFCorr);
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processExceptions(request, mapping, expTTK);
			}
			catch(Exception exp)
			{
			return this.processExceptions(request,mapping,new TTKException(exp,strClaimsSFCorr));
			}//end of catch(Exception exp)
		}//end of doGeneratePrint(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

//ended	
	
   
	public String generateReport(JasperPrint jasperPrint1, JasperPrint jasperPrint2,String strPdfFile) throws JRException {
		  //throw the JasperPrint Objects in a list
		  java.util.List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		  jasperPrintList.add(jasperPrint1);
		  jasperPrintList.add(jasperPrint2);
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();     
		  JRPdfExporter exporter = new JRPdfExporter();     
		  //Add the list as a Parameter
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		  //this will make a bookmark in the exported PDF for each of the reports
		//  exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
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
    
    public ByteArrayOutputStream generateReport(JasperPrint jasperPrint1, JasperPrint jasperPrint2) throws JRException {
		  //throw the JasperPrint Objects in a list
		  java.util.List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		  jasperPrintList.add(jasperPrint1);
		  jasperPrintList.add(jasperPrint2);


		  ByteArrayOutputStream baos = new ByteArrayOutputStream();     
		  JRPdfExporter exporter = new JRPdfExporter();     
		  //Add the list as a Parameter
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		  //this will make a bookmark in the exported PDF for each of the reports
		  exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
		  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);       
		  exporter.exportReport();      
		  //return baos.toByteArray();
		  return baos;
		}
    
    
     
    
    /**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getConcatenatedShortfallSeqID(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer();
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						sbfConcatenatedSeqId.append(String.valueOf(((ShortfallStatusVO)tableData.getRowInfo(
																Integer.parseInt(strChOpt[iCounter]))).getShortfallEmailSeqId()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((ShortfallStatusVO)tableData.getRowInfo(Integer.
																		parseInt(strChOpt[iCounter]))).getShortfallEmailSeqId()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
	//	sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	
	/**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getConcatenatedShortfallNoSeqID(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer();
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						sbfConcatenatedSeqId.append(String.valueOf(((ShortfallStatusVO)tableData.getRowInfo(
																Integer.parseInt(strChOpt[iCounter]))).getShortfallNumber()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((ShortfallStatusVO)tableData.getRowInfo(Integer.
																		parseInt(strChOpt[iCounter]))).getShortfallNumber()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		//sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)

	
    /**
     * This method is used to resend email and download shortfall letters generated so far.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doResendEmail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doResendEmail method of ClaimsSFCorrAction");
    		setLinks(request);
    					
    		String shortfallNo=request.getParameter("shortfallNo");   		
    		String shortfallSeqID=request.getParameter("shortfallSeqID");   		
    		String preAuthNo=request.getParameter("preAuthNo");    	
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		ShortfallRequestDetailsVO shortfallRequestDetailsVO=claimManagerObject.generateShortfallRequestDetails(shortfallSeqID);
    		if(shortfallRequestDetailsVO==null)
    		{
    			TTKException expTTK = new TTKException();
				expTTK.setMessage("error.claims.processing.resendemail");
				throw expTTK;
    		}
    		DynaActionForm frmShortfallResendEmail = (DynaActionForm)FormUtils.setFormValues("frmShortfallResendEmail",														   
    				shortfallRequestDetailsVO, this, mapping, request);
    		frmShortfallResendEmail.set("shrtSeqID",shortfallSeqID);
    		request.getSession().setAttribute("frmShortfallResendEmail",frmShortfallResendEmail);
			return this.getForward(strClaimsSFEmail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doResendEmail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * This method is used to initialize the search grid.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doResendShortfallEmail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doResendShortfallEmail method of ClaimsSFCorrAction");
    		setLinks(request);
    		ShortfallRequestDetailsVO shortfallRequestDetailsVO=null;
    		String ShortfallTypeDesc=request.getParameter("ShortfallTypeDesc");
    		String shortfallEmailSeqID=request.getParameter("shortfallEmailSeqID");  
    		String ShortfallType=request.getParameter("ShortfallType");
    		
    		DynaActionForm frmShortfallResendEmail=(DynaActionForm)form;
    		shortfallRequestDetailsVO = (ShortfallRequestDetailsVO)FormUtils.getFormValues(frmShortfallResendEmail, this, mapping, request);
    		if(ShortfallTypeDesc.equalsIgnoreCase("INITIAL_SHORTFALL_REQUEST"))
    		{
    			shortfallEmailSeqID=shortfallRequestDetailsVO.getshrtSeqID();
    		}
    		else
    			shortfallEmailSeqID=shortfallRequestDetailsVO.getshrtEmailSeqID();
    		
    		long UserSeqId=TTKCommon.getUserSeqId(request);
    
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		long rowsprocessed=claimManagerObject.resendShortfallEmails(ShortfallTypeDesc,shortfallEmailSeqID,ShortfallType,UserSeqId);
 
    		if(rowsprocessed>0)
			{
				request.setAttribute("updated","message.savedSuccessfully");
			}
    		
			return this.getForward(strClaimsSFEmail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doResendShortfallEmail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 
    //ended
  
    /**
     * This method is used to search the data with the given search criteria.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSearch method of ClaimsSFCorrAction");
    		setLinks(request);
    		
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		String fileName=request.getParameter("fileName");
    		
    		String sEmailIDStatus=request.getParameter("sEmailIDStatus");
    		DynaActionForm frmClaimsSFCorr=(DynaActionForm)form;
			
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strClaimsSFCorr);
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("ClaimsSFCorrTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
              //  this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			
			ArrayList alShortfallRequestsList= claimManagerObject.getShortfallRequests(tableData.getSearchData());
			tableData.setData(alShortfallRequestsList,"search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			
			//added newly thursday
			request.setAttribute("fileName", fileName);
			//frmClaimsSFCorr.set("sEmailIDStatus",sEmailIDStatus);
			request.getSession().setAttribute("sEmailIDStatus",sEmailIDStatus);
			//added newly thursday
			//finally return to the grid screen
			return this.getForward(strClaimsSFCorr, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * This method is used to search the data with the given search criteria.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewFile method of ClaimsSFCorrAction");
    		setLinks(request);
    		String str=request.getParameter("shortfallnoreq");		    		
    		String strFile = str+".pdf";    		
    		File file = null;  		
    		String strAuthpdf = TTKPropertiesReader.getPropertyValue("shortfallrptdir")+strFile;   		
    		file = new File(strAuthpdf);
    		if(file.exists())
    		{
    			strAuthpdf = TTKPropertiesReader.getPropertyValue("shortfallrptdir")+strFile;
    			request.setAttribute("fileName",strAuthpdf);
    		}//end of if(file.exists())
    		
    		return this.getForward(strClaimsSFEmail, mapping, request);//finally return to the grid screen
			
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to download the pdf file generated for claim shortfall.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewFilePdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewFilePdf method of ClaimsSFCorrAction");
    		setLinks(request);
    		return (mapping.findForward(strReportdisplay));
			
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doViewFilePdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * This method is used to search the data with the given search criteria.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doClose method of ClaimsSFCorrAction"); 	
			request.getSession().removeAttribute("frmShortfallResendEmail");
			return this.getForward(strClaimsSFDetails, mapping, request);	
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmClaimsList formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search os
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClaimsSFCorr,HttpServletRequest request)
    {
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	
    	alSearchParams.add((String)frmClaimsSFCorr.getString("sClaimNumber"));
    	alSearchParams.add((String)frmClaimsSFCorr.getString("sShortfallNumber"));
    	alSearchParams.add((String)frmClaimsSFCorr.getString("sShortfallStatus"));
    	alSearchParams.add((String)frmClaimsSFCorr.getString("sEmailIDStatus"));
    	// Added by satya
    	 alSearchParams.add((String)frmClaimsSFCorr.getString("sMobileStatus"));
    	 alSearchParams.add((String)frmClaimsSFCorr.getString("sTtkBranch"));
    	 
    	
    	
    	return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)
    
    /**
     * Returns the PreAuthManager session object for invoking methods on it.
     * @return PreAuthManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ClaimManager getClaimManagerObject() throws TTKException
    {
    	ClaimManager claimManager = null;
    	try
    	{
    		if(claimManager == null)
    		{
    			InitialContext ctx = new InitialContext();
    			claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");

    			//claimManager = (ClaimManager) ctx.lookup(ClaimManager.class.getName());
    		}//end if
    	}//end of try
    	catch(Exception exp)
    	{
    		throw new TTKException(exp, strClaimShortfallError);
    	}//end of catch
    	return claimManager;
    }//end getClaimManagerObject()  
    
    
    //Added as per1179 for Shortfall by SATYA
    public static void doMergePDF(List<InputStream> list, OutputStream outputStream)
	throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, outputStream);
		writer.open();
		document.open();

		PdfContentByte cb = writer.getDirectContent();
	
		for (InputStream in : list) {
		
			PdfReader reader = new PdfReader(in);
			
			for (int i = 1; i <=reader.getNumberOfPages(); i++) {
				
				document.newPage();
				//import the page from source pdf
				PdfImportedPage page = writer.getImportedPage(reader, i);
				//add the page to the destination pdf
				//cb.addTemplate(page, 0, 0);
				cb.addTemplate(page, .9f, 0, 0, .9f,0,-15);
			}
		}
		outputStream.flush();
		document.close();
		outputStream.close();
	}

    
    
    
    
}//end of ClaimsSFCorrAction