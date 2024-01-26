/**
 * @ (#) ClaimShortfallVO.java Jan 01, 2013
 * Project 	     : TTK HealthCare Services
 * File          : ClaimShortfallVO.java
 * Author        : Manohar
 * Company       : RCS
 * Date Created  : Jan 01, 2013
 *
 * @author       : RamaKrishna K M
 * Modified by   : Manohar
 * Modified date :  
 * Reason        :
 */

package com.ttk.dto.claims;

import java.util.Date;

import com.ttk.dto.BaseVO;

public class ClaimShortfallVO extends BaseVO
{

	private String strTtk_address = "";
	private String strAddress1 = "";
	private String strAddress2 = "";	
	private String strAddress3 = "";
	private String strCITY = "";
	private String strShortfall_number = "";
	private String strClaim_number = "";
	//private Date   dtDate_of_admission;
	private String dtDate_of_admission;
	private String dtDate_of_discharge;
	private String strPolicy_number = "";
	private String strTpa_enrollment_id = "";
	private String strTpa_enrollment_number = "";
	private String strEmail_id = "";
	private String strHospital_name = "";
	private String strPatient_number = "";
	private String strReceived_date = "";
	private String strIntimation_date = "";
	private String strClm_docs_submit_in_days = "";
	private String strTtk_address_dtl = "";
	private String strReceiver_email_id = "";
	private String strClaimant_name = "";
	private String strReminder_letter_days = "";
	private String strClause_number = "";
	private String strCloser_notice_days = "";
	private String strIntl_shortfal_snt_date = "";
	private String strRmdr_req_snt_date = "";
	private String strApproval_closer_days = "";
	private String strClsr_notice_snt_date = "";
	private String strReminder_draft = "";  // Genearl or Partial Shortfall Reminder Letter 
	private String strClm_clsr_letter_snt_date = "";
	private String strRegrett_letter_days = "";
	private String strClaim_closer_letter_days = "";
	private String strToll_free_no = "";
	
	private String strShortfall_raised_by = "";
	private String strShortfall_raised_for = "";
	private String strClaim_type = "";
	private String strInsured_mailid = "";	
	private String strMobile_no = "";
	private String strIns_mobile_no = "";
	
	//added for Mail-SMS Template for Cigna
	private String strLTR_GEN_DATE = "";
	private String strMEM_PIN_CODE = "";
	private String strMEM_STATE = "";
	private String strPLAN_NAME = "";
	private String strCLM_REG_DATE = "";
	private String strADVISOR_NAME = "";
	private String strHOSP_ADDRESS = "";
	private String strreceiver_email_id = "";	
	//ended
	
	//shortfall letter added new value
	private String  strPolicy_no = "";
	private String  strSdate = "";
	//shortfall phase1
	
	private String strAilment_description = "";
	private String strCorporate = "";
	private String strClaim_status = "";
	private String strAgent_code = "";	
	private String strDev_office_code = "";
	private String strPolicy_type = "";
	private String strMem_age = "";	
	private String strGender = "";
	private String strPre_auth_number = "";
	private	String strPh_name = "";
	private String strSubmit_days= "";	
	private String stremp_id = "";
	private	String strpost_hosp_days = "";
	private String strclm_int_date= "";
	private String strrcvd_thru= "";
	private String strclm_int_num= "";

    public String getclm_int_num() {
        return strclm_int_num;
    }
    public void setclm_int_num(String strclm_int_num) {
        this.strclm_int_num = strclm_int_num;
    }

	private String strShortfall_partial_date= "";//for shortfall 
	
	//for shortfall 
	public void setShortfall_partial_date(String strShortfall_partial_date) {
		this.strShortfall_partial_date = strShortfall_partial_date;
	}
	public String getShortfall_partial_date() {
		return strShortfall_partial_date;
	}
	//for shortfall 
	
	public String getrcvd_thru() {
		return strrcvd_thru;
	}
	public void setrcvd_thru(String strrcvd_thru) {
		this.strrcvd_thru = strrcvd_thru;
	}
	public String getclm_int_date() {
		return strclm_int_date;
	}
	public void setclm_int_date(String strclm_int_date) {
		this.strclm_int_date = strclm_int_date;
	}
	public String getpost_hosp_days() {
		return strpost_hosp_days;
	}
	public void setpost_hosp_days(String strpost_hosp_days) {
		this.strpost_hosp_days = strpost_hosp_days;
	}
	public String getemp_id() {
		return stremp_id;
	}
	public void setemp_id(String stremp_id) {
		this.stremp_id = stremp_id;
	}
		
	public String getSubmit_days() {
		return strSubmit_days;
	}
	public void setSubmit_days(String strSubmitDays) {
		strSubmit_days = strSubmitDays;
	}
	
	public void setPh_name(String strPh_name) {
		this.strPh_name = strPh_name;
	}
	public String getPh_name() {
		return strPh_name;
	}
	public void setPre_auth_number(String strPre_auth_number) {
		this.strPre_auth_number = strPre_auth_number;
	}
	public String getPre_auth_number() {
		return strPre_auth_number;
	}
	public String getGender() {
		return strGender;
	}
	public void setGender(String strGender) {
		this.strGender = strGender;
	}
	
	public String getMem_age() {
		return strMem_age;
	}
	public void setMem_age(String strMemAge) {
		strMem_age = strMemAge;
	}
	
	public void setPolicy_type(String strPolicy_type) {
		this.strPolicy_type = strPolicy_type;
	}
	public String getPolicy_type() {
		return strPolicy_type;
	}
	public void setAgent_code(String strAgent_code) {
		this.strAgent_code = strAgent_code;
	}
	public String getAgent_code() {
		return strAgent_code;
	}
	public void setDev_office_code(String strDev_office_code) {
		this.strDev_office_code = strDev_office_code;
	}
	public String getDev_office_code() {
		return strDev_office_code;
	}
	public String getClaim_status() {
		return strClaim_status;
	}
	public void setClaim_status(String strClaimStatus) {
		strClaim_status = strClaimStatus;
	}
	
	public String getCorporate() {
		return strCorporate;
	}
	public void setCorporate(String strCorporate) {
		this.strCorporate = strCorporate;
	}
	
	public String getAilment_description() {
		return strAilment_description;
	}
	public void setAilment_description(String strAilmentDescription) {
		strAilment_description = strAilmentDescription;
	}
	
	
	
	public void setSdate(String sdate) {
		strSdate = sdate;
	}
	public String getSdate() {
		return strSdate;
	}
	
	public void setPolicy_no(String strPolicy_no) {
		this.strPolicy_no = strPolicy_no;
	}
	public String getPolicy_no() {
		return strPolicy_no;
	}
	//shortfall letter added new value
	/**
	 * @param insured_mailid the insured_mailid to set
	 */
	public void setInsured_mailid(String insured_mailid) {
		this.strInsured_mailid = insured_mailid;
	}
	/**
	 * @return the insured_mailid
	 */
	public String getInsured_mailid() {
		return strInsured_mailid;
	}
	/**
	 * @param claim_type the claim_type to set
	 */
	public void setClaim_type(String claim_type) {
		this.strClaim_type = claim_type;
	}
	/**
	 * @return the claim_type
	 */
	public String getClaim_type() {
		return strClaim_type;
	}
	/**
	 * @return the shrtfall_raised_for
	 */
	public String getShortfall_raised_for() {
		return this.strShortfall_raised_for;
	}
	/**
	 * @param shrtfall_raised_for the shrtfall_raised_for to set
	 */
	public void setShortfall_raised_for(String shortfallRaisedFor) {
		this.strShortfall_raised_for = shortfallRaisedFor;
	}
	/**
	 * @param shrtfall_raised_by the shrtfall_raised_by to set
	 */
	public void setShortfall_raised_by(String shortfall_raised_by) {
		this.strShortfall_raised_by = shortfall_raised_by;
	}
	/**
	 * @return the shrtfall_raised_by
	 */
	public String getShortfall_raised_by() {
		return strShortfall_raised_by;
	}
	/** Retrieve the Ttk address
	 * @return the strTtk_address
	 */
	public String getTtk_address() {
		return this.strTtk_address;
	}
	/** Sets the Ttk address
	 * @param strTtk_address the ttk_address to set
	 */
	public void setTtk_address(String ttkAddress) {
		this.strTtk_address = ttkAddress;
	}
	/** Retrieve the Address1
	 * @return the strAddress1
	 */
	public String getAddress1() {
		return this.strAddress1;
	}
	/** Sets the  Address1
	 * @param strAddress1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.strAddress1 = address1;
	}
	/**  Retrieve the Address1
	 * @return the address2
	 */
	public String getAddress2() {
		return this.strAddress2;
	}
	/**  Sets the Address2
	 * @param strAddress2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.strAddress2 = address2;
	}
	/** Retrieve the Address2
	 * @return the address3
	 */
	public String getAddress3() {
		return this.strAddress3;
	}
	/**  Sets the Address3
	 * @param strAddress3 the address3 to set
	 */
	public void setAddress3(String address3) {
		this.strAddress3 = address3;
	}
	
	public String getCITY() {
		return strCITY;
	}
	public void setCITY(String strCITY) {
		this.strCITY = strCITY;
	}
	/**  Retrieve the Shortfall Number
	 * @return the strShortfall_number
	 */
	public String getShortfall_number() {
		return this.strShortfall_number;
	}
	/** Sets the Shortfall Number
	 * @param strShortfall_number the shortfallNumber to set
	 */
	public void setShortfall_number(String shortfallNumber) {
		this.strShortfall_number = shortfallNumber;
	}
	/** Retrieve the Claim Number
	 * @return the strClaim_number
	 */
	public String getClaim_number() {
		return this.strClaim_number;
	}
	/**
	 * @param date_of_admission the date_of_admission to set
	 */
	public void setDate_of_admission(String date_of_admission) {
		this.dtDate_of_admission = date_of_admission;
	}
	/**
	 * @return the date_of_admission
	 */
	public String getDate_of_admission() {
		return dtDate_of_admission;
	}
	/**   Sets the Claim Number
	 * @param strClaim_number the claimNumber to set
	 */
	public void setClaim_number(String claimNumber) {
		this.strClaim_number = claimNumber;
	}
	/** Retrieve the  Date of admission
	 * @return the dtDate_of_admission
	 */
/*	public Date getDate_of_admission() {
		return this.dtDate_of_admission;
	}*/
	/** Sets the  Date of admission
	 * @param dtDate_of_admission the dateOfAdmission to set
	 */
	/*public void setDate_of_admission(Date dateOfAdmission) {
		this.dtDate_of_admission = dateOfAdmission;
	}*/
	/** Retrieve the  Date of discharge
	 * @return the dtDate_of_discharge
	 */
	public String getDate_of_discharge() {
		return this.dtDate_of_discharge;
	}
	/** Sets the Date of discharge
	 * @param dtDate_of_discharge the date_of_discharge to set
	 */
	public void setDate_of_discharge(String dateOfDischarge) {
		this.dtDate_of_discharge = dateOfDischarge;
	}
	/**  Retrieve the Policy Number
	 * @return the strPolicy_number
	 */
	public String getPolicy_number() {
		return this.strPolicy_number;
	}
	/** Sets the Policy Number
	 * @param strPolicy_number the policyNumber to set
	 */
	public void setPolicy_number(String policyNumber) {
		this.strPolicy_number = policyNumber;
	} 
	/** Retrieve the Tpa enrollment id
	 * @return the strTpa_enrollment_id
	 */
	public String getTpa_enrollment_id() {
		return this.strTpa_enrollment_id;
	}
	/** Sets the Tpa enrollment id
	 * @param strTpa_enrollment_id the tpaEnrollmentId to set
	 */
	public void setTpa_enrollment_id(String tpaEnrollmentId) {
		this.strTpa_enrollment_id = tpaEnrollmentId;
	}
	/** Retrieve the Tpa enrollment number
	 * @return the strTpa_enrollment_number
	 */
	public String getTpa_enrollment_number() {
		return this.strTpa_enrollment_number;
	}
	/** Sets the Tpa enrollment number
	 * @param strTpa_enrollment_number the tpaEnrollmentNumber to set
	 */
	public void setTpa_enrollment_number(String tpaEnrollmentNumber) {
		this.strTpa_enrollment_number = tpaEnrollmentNumber;
	}
	/**  Retrieve the Email id
	 * @return the strEmail_id
	 */
	public String getEmail_id() {
		return this.strEmail_id;
	}
	/** Sets the Email id
	 * @param strEmail_id the emailId to set
	 */
	public void setEmail_id(String emailId) {
		this.strEmail_id = emailId;
	}
	/** Retrieve the Hospital Name
	 * @return the strHospital_name
	 */
	public String getHospital_name() {
		return this.strHospital_name;
	}
	/** Sets the Hospital Name
	 * @param strHospital_name the strHospital_name to set
	 */
	public void setHospital_name(String hospitalName) {
		this.strHospital_name = hospitalName;
	}
	
	/** Retrieve the Patient Number
	 * @return the strReceived_date
	 */
	public String getPatient_number() {
		return this.strPatient_number;
	}
	/** Sets the Patient Number
	 * @param strPatient_number the patientNumber to set
	 */
	public void setPatient_number(String patientNumber) {
		this.strPatient_number = patientNumber;
	}
	/** Retrieve the  Received Date
	 * @return the strReceived_date
	 */
	public String getReceived_date() {
		return this.strReceived_date;
	}
	/** Sets the  Received Date
	 * @param strReceived_date the receivedDate to set
	 */
	public void setReceived_date(String receivedDate) {
		this.strReceived_date = receivedDate;
	}
	/** Retrieve the Intimation Date
	 * @return the strIntimation_date
	 */
	public String getIntimation_date() {
		return this.strIntimation_date;
	}
	/** Sets the Intimation Date
	 * @param strIntimation_date the intimationDate to set
	 */
	public void setIntimation_date(String intimationDate) {
		this.strIntimation_date = intimationDate;
	}
	/** Retrieve the Clm_docs_submit_in_days
	 * @return the strClm_docs_submit_in_days
	 */
	public String getClm_docs_submit_in_days() {
		return this.strClm_docs_submit_in_days;
	}
	/** Sets the Clm_docs_submit_in_days
	 * @param strClm_docs_submit_in_days the clmDocsSubmitInDays to set
	 */
	public void setClm_docs_submit_in_days(String clmDocsSubmitInDays) {
		this.strClm_docs_submit_in_days = clmDocsSubmitInDays;
	}
	/** Retrieve the  Ttk_address_dtl
	 * @return the strTtk_address_dtl
	 */
	public String getTtk_address_dtl() {
		return this.strTtk_address_dtl;
	}
	/** Sets the Ttk_address_dtl
	 * @param strTtk_address_dtl the ttkAddressDtl to set
	 */
	public void setTtk_address_dtl(String ttkAddressDtl) {
		this.strTtk_address_dtl = ttkAddressDtl;
	}
	/** Retrieve the  Receiver_email_id
	 * @return the strReceiver_email_id
	 */
	public String getReceiver_email_id() {
		return this.strReceiver_email_id;
	}
	/** Sets the Receiver_email_id
	 * @param strReceiver_email_id the receiverEmailId to set
	 */
	public void setReceiver_email_id(String receiverEmailId) {
		this.strReceiver_email_id = receiverEmailId;
	}
	/** Retrieve the  Claimant_name
	 * @return the strClaimant_name
	 */
	public String getClaimant_name() {
		return this.strClaimant_name;
	}
	/** Sets the Claimant_name
	 * @param strClaimant_name the claimantName to set
	 */
	public void setClaimant_name(String claimantName) {
		this.strClaimant_name = claimantName;
	}
	/** Retrieve the  Reminder_letter_days
	 * @return the strReminder_letter_days
	 */
	public String getReminder_letter_days() {
		return this.strReminder_letter_days;
	}
	/** Sets the Reminder_letter_days
	 * @param strReminder_letter_days the reminderLetterDays to set
	 */
	public void setReminder_letter_days(String reminderLetterDays) {
		this.strReminder_letter_days = reminderLetterDays;
	}
	/** Retrieve the Clause_number
	 * @return the strClause_number
	 */
	public String getClause_number() {
		return this.strClause_number;
	}
	/** Sets the Clause_number
	 * @param strClause_number the clauseNumber to set
	 */
	public void setClause_number(String clauseNumber) {
		this.strClause_number = clauseNumber;
	}
	/** Retrieve the Closer_notice_days
	 * @return the strCloser_notice_days
	 */
	public String getCloser_notice_days() {
		return this.strCloser_notice_days;
	}
	/** Sets the Closer_notice_days
	 * @param strCloser_notice_days the closerNoticeDays to set
	 */
	public void setCloser_notice_days(String closerNoticeDays) {
		this.strCloser_notice_days = closerNoticeDays;
	}
	/** Retrieve the Intl_shortfal_snt_date
	 * @return the strIntl_shortfal_snt_date
	 */
	public String getIntl_shortfal_snt_date() {
		return this.strIntl_shortfal_snt_date;
	}
	/** Sets the  Intl_shortfal_snt_date
	 * @param strIntl_shortfal_snt_date the intlShortfalSntDate to set
	 */
	public void setIntl_shortfal_snt_date(String intlShortfalSntDate) {
		this.strIntl_shortfal_snt_date = intlShortfalSntDate;
	}
	/** Retrieve the Rmdr_req_snt_date
	 * @return the strRmdr_req_snt_date
	 */
	public String getRmdr_req_snt_date() {
		return this.strRmdr_req_snt_date;
	}
	/** Sets the  Rmdr_req_snt_date
	 * @param strRmdr_req_snt_date the rmdrReqSntDate to set
	 */
	public void setRmdr_req_snt_date(String rmdrReqSntDate) {
		this.strRmdr_req_snt_date = rmdrReqSntDate;
	}
	/** Retrieve the Approval_closer_days
	 * @return the strApproval_closer_days
	 */
	public String getApproval_closer_days() {
		return this.strApproval_closer_days;
	}
	/** Sets the Approval_closer_days
	 * @param strApproval_closer_days the approvalCloserDays to set
	 */
	public void setApproval_closer_days(String approvalCloserDays) {
		this.strApproval_closer_days = approvalCloserDays;
	}
	/** Retrieve the  Clsr_notice_snt_date
	 * @return the strClsr_notice_snt_date
	 */
	public String getClsr_notice_snt_date() {
		return this.strClsr_notice_snt_date;
	}
	/** Sets the Clsr_notice_snt_date
	 * @param strClsr_notice_snt_date the clsrNoticeSntDate to set
	 */
	public void setClsr_notice_snt_date(String clsrNoticeSntDate) {
		this.strClsr_notice_snt_date = clsrNoticeSntDate;
	}
	/** Retrieve the Reminder_draft
	 * @return the strReminder_draft
	 */
	public String getReminder_draft() {
		return this.strReminder_draft;
	}
	/** Sets the Reminder_draft
	 * @param strReminder_draft the reminderDraft to set
	 */
	public void setReminder_draft(String reminderDraft) {
		this.strReminder_draft = reminderDraft;
	}
	/** Retrieve the Clm_clsr_letter_snt_date
	 * @return the strClm_clsr_letter_snt_date
	 */
	public String getClm_clsr_letter_snt_date() {
		return this.strClm_clsr_letter_snt_date;
	}
	/** Sets the Clm_clsr_letter_snt_date
	 * @param strClm_clsr_letter_snt_date the clmClsrLetterSntDate to set
	 */
	public void setClm_clsr_letter_snt_date(String clmClsrLetterSntDate) {
		this.strClm_clsr_letter_snt_date = clmClsrLetterSntDate;
	}
	/** Retrieve the Regrett_letter_days
	 * @return the strRegrett_letter_days
	 */
	public String getRegrett_letter_days() {
		return this.strRegrett_letter_days;
	}
	/** Sets the  Regrett_letter_days
	 * @param strRegrett_letter_days the regrettLetterDays to set
	 */
	public void setRegrett_letter_days(String regrettLetterDays) {
		this.strRegrett_letter_days = regrettLetterDays;
	}
	/**  Retrieve the  Claim_closer_letter_days
	 * @return the strClaim_closer_letter_days
	 */
	public String getClaim_closer_letter_days() {
		return this.strClaim_closer_letter_days;
	}
	/** Sets the  Claim_closer_letter_days
	 * @param strClaim_closer_letter_days the claimCloserLetterDays to set
	 */
	public void setClaim_closer_letter_days(String claimCloserLetterDays) {
		this.strClaim_closer_letter_days = claimCloserLetterDays;
	}
	
	/**  Retrieve the Ttk toll_free_no
	 * @return the toll_free_no
	 */
	public String getToll_free_no() {
		return this.strToll_free_no;
	}
	/**  Sets the tollFreeNo
	 * @param tollFreeNo the toll_free_no to set
	 */
	public void setToll_free_no(String tollFreeNo) {
		this.strToll_free_no = tollFreeNo;
	}
	public void setMobile_no(String mobile_no) {
		strMobile_no = mobile_no;
	}
	public String getMobile_no() {
		return strMobile_no;
	}
	public void setIns_mobile_no(String ins_mobile_no) {
		strIns_mobile_no = ins_mobile_no;
	}
	public String getIns_mobile_no() {
		return strIns_mobile_no;
	}
	//added for Mail-SMS Template for Cigna
	public String getLTR_GEN_DATE() {
		return strLTR_GEN_DATE;
	}
	public void setLTR_GEN_DATE(String strLTRGENDATE) {
		strLTR_GEN_DATE = strLTRGENDATE;
	}
	public String getMEM_PIN_CODE() {
		return strMEM_PIN_CODE;
	}
	public void setMEM_PIN_CODE(String strMEMPINCODE) {
		strMEM_PIN_CODE = strMEMPINCODE;
	}
	public String getMEM_STATE() {
		return strMEM_STATE;
	}
	public void setMEM_STATE(String strMEMSTATE) {
		strMEM_STATE = strMEMSTATE;
	}
	public String getPLAN_NAME() {
		return strPLAN_NAME;
	}
	public void setPLAN_NAME(String strPLANNAME) {
		strPLAN_NAME = strPLANNAME;
	}
	public String getCLM_REG_DATE() {
		return strCLM_REG_DATE;
	}
	public void setCLM_REG_DATE(String strCLMREGDATE) {
		strCLM_REG_DATE = strCLMREGDATE;
	}
	public String getADVISOR_NAME() {
		return strADVISOR_NAME;
	}
	public void setADVISOR_NAME(String strADVISORNAME) {
		strADVISOR_NAME = strADVISORNAME;
	}
	public String getHOSP_ADDRESS() {
		return strHOSP_ADDRESS;
	}
	public void setHOSP_ADDRESS(String strHOSPADDRESS) {
		strHOSP_ADDRESS = strHOSPADDRESS;
	}
	//ended
	
} // End ClaimsShortfallVO
