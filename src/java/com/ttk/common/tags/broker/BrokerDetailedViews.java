package com.ttk.common.tags.broker;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;

import com.ttk.dto.brokerlogin.BrokerVO;
import com.ttk.dto.onlineforms.insuranceLogin.DependentVO;

public class BrokerDetailedViews extends TagSupport{
	private static final Logger log=LogManager.getLogger(BrokerDetailedViews.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5857711697335259171L;
	
	@Override
	public int doStartTag() throws JspException {
		try{			
			JspWriter	out=pageContext.getOut();
			DynaActionForm frmBroDashBoard=(DynaActionForm)pageContext.getSession().getAttribute("frmBroDashBoard");
			HashMap<String,Object> corporateDetailedMap=(HashMap<String,Object>)pageContext.getSession().getAttribute("corporateDetailedMap");
		if(frmBroDashBoard !=null)	{
			String summaryView=frmBroDashBoard.getString("summaryView");
			if("PRI".equals(summaryView)){
			out.println("<table id='textTableTooSmall'>");
			out.println("<caption id='tableCaption'>PERSONAL INFORMATION</caption>");
			out.println("<tr>");
			out.println("<th>Name</th><td>"+corporateDetailedMap.get("MEM_NAME")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<th>Date of birth</th><td>"+corporateDetailedMap.get("MEM_DOB")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<th>Age</th><td>"+corporateDetailedMap.get("MEM_AGE")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<th>Gender</th><td>"+corporateDetailedMap.get("GENDER_GENERAL_TYPE_ID")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("</table>");
			/*out.println("<th>Dependent details</th><td>"+corporateDetailedMap.get("MARITAL_STATUS_ID")+"</td>");*/

			out.println("<table id='textTableTooSmall'>");
			out.println("<caption id='tableCaption'>Dependent details</caption>");
			out.println("<tr>");
			out.println("<th>Name</th>");
			out.println("<th>Age</th>");
			out.println("<th>Gender</th>");
			out.println("</tr>");
			ArrayList<DependentVO> alBro=(ArrayList<DependentVO>)corporateDetailedMap.get("ALDEPENDANTS");
			if(alBro!=null){
				for(DependentVO dependentVO:alBro){
					out.println("<tr>");
					out.println("<td>"+dependentVO.getName()+"</td>");
					out.println("<td>"+dependentVO.getAge()+"</td>");
					out.println("<td>"+dependentVO.getGender()+"</td>");
					out.println("</tr>");
				}
			}
			
			out.println("</table>");
			}else if("CLM".equals(summaryView)){
				out.println("<table id='textTableLarge'>");
				out.println("<caption id='tableCaption'>CLAIMS</caption>");
				out.println("<tr>");
				out.println("<th>Name</th>");
				out.println("<th>Relation</th>");
				out.println("<th>Claim Number</th>");
				out.println("<th>Date From</th>");
				out.println("<th>Date To</th>");
				out.println("<th>Claimed Amount</th>");
				out.println("<th>Settled Amount</th>");
				out.println("<th>Status</th>");
				out.println("</tr>");
				ArrayList<BrokerVO> alBro=(ArrayList<BrokerVO>)corporateDetailedMap.get("CLAIM_LIST");
				if(alBro!=null){
					for(BrokerVO brokerVO:alBro){
						out.println("<tr>");
						out.println("<td>"+brokerVO.getMemberName()+"</td>");
						out.println("<td>"+brokerVO.getRelationShip()+"</td>");
						out.println("<td><a href='#' onclick=\"onViewClaimDetails('"+brokerVO.getClaimNumber()+"','CLD');\">"+brokerVO.getClaimNumber()+"</a></td>");
						out.println("<td>"+brokerVO.getDateOfHospitalization()+"</td>");
						out.println("<td>"+brokerVO.getDateOfDischarge()+"</td>");
						out.println("<td>"+brokerVO.getTotalApprovedAmt()+"</td>");
						out.println("<td>"+brokerVO.getTotalApprovedAmt()+"</td>");
						out.println("<td>"+brokerVO.getStatus()+"</td>");
						out.println("</tr>");
					}
				}
				out.println("</table>");
				}else if("CLD".equals(summaryView)){
					out.println("<table id='textTableSmall'>");
					out.println("<caption id='tableCaption'>CLAIM DETAILS</caption>");
					out.println("<tr>");
					out.println("<th>Claim Number</th><td>"+corporateDetailedMap.get("CLAIM_NUMBER")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Claim Received Date</th><td>"+corporateDetailedMap.get("CLM_RECEIVED_DATE")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Claim Provider</th><td>"+corporateDetailedMap.get("HOSP_NAME")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Encounter Provider Invoice NO</th><td>"+corporateDetailedMap.get("ENCOUNTER_PROVIDER_INVOICE_NO")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Activity Service Date</th><td>"+corporateDetailedMap.get("START_DATE")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Claim Payment ID</th><td>"+corporateDetailedMap.get("CLAIM_PAYMENT_ID")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Claim Payment Date</th><td>"+corporateDetailedMap.get("CLAIM_PAYMENT_DATE")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Diagnosis Code</th><td>"+corporateDetailedMap.get("DIAGNOSYS_CODE")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Diagnosis Description</th><td>"+corporateDetailedMap.get("SHORT_DESC")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Claim Amount</th><td>"+corporateDetailedMap.get("TOT_DISC_GROSS_AMOUNT")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Deductable Amount</th><td>"+corporateDetailedMap.get("DEDUCTIBLE_AMOUNT")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Copay Amount</th><td>"+corporateDetailedMap.get("COPAY_AMOUNT")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Benefit Amount</th><td>"+corporateDetailedMap.get("BENEFIT_AMOUNT")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Claim Recipient</th><td>"+corporateDetailedMap.get("CLAIM_RECIPIENT")+"</td>");
					out.println("</tr>");
					/*out.println("<tr>");
					out.println("<th>Premium Type</th><td></td>");//corporateDetailedMap.get("")
					out.println("</tr>");*/
					out.println("<tr>");
					out.println("<th>Authorization Number</th><td>"+corporateDetailedMap.get("AUTH_NUMBER")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Claim Status</th><td>"+corporateDetailedMap.get("CLM_STATUS_TYPE_ID")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Claim Status Description</th><td>"+corporateDetailedMap.get("CLAIM_STATUS_DESCRIPTION")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Payee Name</th><td>"+corporateDetailedMap.get("PAYEE_NAME")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Cheque NO/EFT Transaction NO</th><td>"+corporateDetailedMap.get("CHEQUE_EFT_NUMBER")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Cheque/EFT Date</th><td>"+corporateDetailedMap.get("CHEQUE_EFT_DATE")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Cheque Dispatch Date</th><td>"+corporateDetailedMap.get("CHEQUE_DISPATCH_DATE")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<th>Name Of Courier</th><td>"+corporateDetailedMap.get("NAME_OF_COURIER")+"</td>");
					out.println("</tr>");					
					out.println("</table>");
					
				}else if("PAT".equals(summaryView)){
					out.println("<table id='textTableLarge'>");
					out.println("<caption id='tableCaption'>PREAUTHORIZATION</caption>");
					out.println("<tr>");
					out.println("<th>Name</th>");
					out.println("<th>Relation</th>");
					out.println("<th>Auth Number</th>");
					out.println("<th>Date From</th>");
					out.println("<th>Date To</th>");
					out.println("<th>Claimed Amount</th>");
					out.println("<th>Settled Amount</th>");
					out.println("<th>Status</th>");
					out.println("</tr>");
					ArrayList<BrokerVO> alBro=(ArrayList<BrokerVO>)corporateDetailedMap.get("PREAUTH_LIST");
					if(alBro!=null){
						for(BrokerVO brokerVO:alBro){
							out.println("<tr>");
							out.println("<td>"+brokerVO.getMemberName()+"</td>");
							out.println("<td>"+brokerVO.getRelationShip()+"</td>");
							out.println("<td><a href='#' onclick=\"onViewPreauthDetails('"+brokerVO.getPreAuthNumber()+"','PAD');\">"+brokerVO.getAuthNumber()+"</a></td>");
							out.println("<td>"+brokerVO.getDateOfHospitalization()+"</td>");
							out.println("<td>"+brokerVO.getDateOfDischarge()+"</td>");
							out.println("<td>"+brokerVO.getTotalApprovedAmt()+"</td>");
							out.println("<td>"+brokerVO.getTotalApprovedAmt()+"</td>");
							out.println("<td>"+brokerVO.getStatus()+"</td>");
							out.println("</tr>");
						}
					}
					out.println("</table>");
					}else if("PAD".equals(summaryView)){

						out.println("<table id='textTableSmall'>");
						out.println("<caption id='tableCaption'>PREAUTHORIZATION DETAILS</caption>");
						out.println("<tr>");
						out.println("<th>Authorization Number</th><td>"+corporateDetailedMap.get("AUTH_NUMBER")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Name Of The Provider</th><td>"+corporateDetailedMap.get("HOSP_NAME")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Encounter Date From</th><td>"+corporateDetailedMap.get("ENCOUNTER_DATE_FROM")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Encounter Date To</th><td>"+corporateDetailedMap.get("ENCOUNTER_DATE_TO")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Service Date</th><td>"+corporateDetailedMap.get("START_DATE")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Diagnosis Code</th><td>"+corporateDetailedMap.get("DIAGNOSYS_CODE")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Diagnosis Description</th><td>"+corporateDetailedMap.get("SHORT_DESC")+"</td>");
						out.println("</tr>");
						/*out.println("<tr>");
						out.println("<th>Tariff Description</th><td>"+corporateDetailedMap.get("SHORT_DESC")+"</td>");
						out.println("</tr>");*/
						out.println("<tr>");
						out.println("<th>Authorization Amount</th><td>"+corporateDetailedMap.get("TOT_APPROVED_AMOUNT")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Deductable Amount</th><td>"+corporateDetailedMap.get("DEDUCTIBLE_AMOUNT")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Copay Amount</th><td>"+corporateDetailedMap.get("COPAY_AMOUNT")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Benefit Amount</th><td>"+corporateDetailedMap.get("BENEFIT_AMOUNT")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Activity Benefit</th><td>"+corporateDetailedMap.get("ACTIVITY_BENEFIT")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Encounter Type</th><td>"+corporateDetailedMap.get("ENCOUNTER_TYPE")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Activity Status</th><td>"+corporateDetailedMap.get("ACTIVITY_STATUS")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Activity Status Description</th><td>"+corporateDetailedMap.get("ACTIVITY_STATUS_DESCRIPTION")+"</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>Claim Recieved</th><td>"+corporateDetailedMap.get("CLAIM_RECEIVED")+"</td>");
						out.println("</tr>");
						out.println("</table>");
					}else if("TOB".equals(summaryView)){
						out.println("<table id='textTableLarge'>");
						out.println("<caption id='tableCaption'>TABLE OF BENEFIT</caption>");
						out.println("<tr>");
						out.println("<th>Description</th>");
						out.println("<th>File Name</th>");
						out.println("<th>Date And Time</th>");
						out.println("</tr>");
						ArrayList<BrokerVO> alBro=(ArrayList<BrokerVO>)corporateDetailedMap.get("TOB_LIST");
						if(alBro!=null){
							for(BrokerVO brokerVO:alBro){
								out.println("<tr>");
								out.println("<td>"+brokerVO.getFileType()+"</td>");
								out.println("<td><a href='#' onclick=\"onFileDownload('"+brokerVO.getFileName()+"');\">"+brokerVO.getFileName()+"</a></td>");
								out.println("<td>"+brokerVO.getAddedDateAsString()+"</td>");
								out.println("</tr>");
							}
						}
						out.println("</table>");
						}else if("END".equals(summaryView)){
							out.println("<table id='textTableLarge'>");
							out.println("<caption id='tableCaption'>ENDORSEMENT</caption>");
							out.println("<tr>");
							out.println("<th>Policy NO</th>");
							out.println("<th>Insured</th>");
							out.println("<th>Dependents</th>");
							out.println("<th>Endorsement Effective Date</th>");
							out.println("<th>Status</th>");
							out.println("</tr>");
							ArrayList<BrokerVO> alBro=(ArrayList<BrokerVO>)corporateDetailedMap.get("ENDORSE_LIST");
							if(alBro!=null){
								for(BrokerVO brokerVO:alBro){
									out.println("<tr>");
									out.println("<td>"+brokerVO.getPolicyNumber()+"</td>");
									out.println("<td>"+brokerVO.getEmployeeName()+"</td>");
									out.println("<td>"+brokerVO.getMemberName()+"</td>");
									out.println("<td>"+brokerVO.getDateOfInception()+"</td>");
									out.println("<td style=\"text-align: left;\">"+brokerVO.getStatus()+"</td>");
									out.println("</tr>");
								}
							}
							out.println("</table>");
							}
		}//if(frmBroDashBoard!=null)	{
	}catch(Exception exception){
			log.error(exception);
   }
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;
	}

}
