/** 
 * @ (#) PreAuthSearch.java 23rd Nov 2015 
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 23rd Nov 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 23rd Nov 2015
 * Reason        :
 *
 */
package com.ttk.dto.onlineforms.providerLogin;

import com.ttk.dto.BaseVO;


public class PreAuthSearchVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String preAuthNo;
	private String receivedDate;
	private String patientName;
	private String benefitType;
	private String treatingDoctor;
	private String approvalNo;
	private String status;
	private String statusID;
	private String patientCardId;
	private Long patAuthSeqId;
	
	//view Auth Parameters
	private String preAuthType;
	private String admissionDate;
	private String previousApprAmt;
	private String requestedAmt;
	private String vidalBranch;
	private String probableDischargeDt;
	private String beneficiaryId;
	private String providerName;
	private String empanelId;	
	private String emirate;
	private String city;
	private String apprStatus;
	private String decisionDt;
	
	//aDDITIONAL PARAMETERS FOR CLAIMS
	private String batchNo;
	private String invoiceNo;
	private String chequeNo;
	private Long clmSeqId;;
	private Long clmBatchSeqId;
	private String claimType;
	private String preAuthRefNo;
	private String emirateID;
	
	
	//claim summary report
	
	private String claimnumber;
	private String datefrom;
	private String fromDate;
	private String toDate;
	/*private String benefitType;
	private String invoiceNo;
	private String batchNo;
	private String status;*/
	private String calimType;
	
	private String patientname;
	private String patient_idcard_no;
	private String insurancycompany;
	private String corporate_name;
	private String diagnosis;
	private String doctor_name;
	private String req_amount;
	
	private String agreed_tariff;
	private String discount;
	private String discounted_gross;
	private String patient_share;
	private String enhanceYN;
	private String enhanceImageName="Blank";
	private String enhanceImageTitle="";
	private String countryName;
	private String enhance; 
	private String claimSubmittedDate;
	private String claimedAmount;
	private String treatmentDate;
	private String eventReferenceNo;
	private String strShortfallImageName = "Blank";
	private String strShortfallImageTitle = "";
	private String medicalOpinionRemarks = "";


	public String getStrShortfallImageTitle() {
		return strShortfallImageTitle;
	}
	public void setStrShortfallImageTitle(String strShortfallImageTitle) {
		this.strShortfallImageTitle = strShortfallImageTitle;
	} 

	public String getStrShortfallImageName() {
		return strShortfallImageName;
	}
	public void setStrShortfallImageName(String strShortfallImageName) {
		this.strShortfallImageName = strShortfallImageName;
	}


	public String getEventReferenceNo() {
		return eventReferenceNo;
	}

	public void setEventReferenceNo(String eventReferenceNo) {
		this.eventReferenceNo = eventReferenceNo;
	}




	public String getTreatmentDate() {
		return treatmentDate;
	}
	public void setTreatmentDate(String treatmentDate) {
		this.treatmentDate = treatmentDate;
	}


	public String getClaimedAmount() {
		return claimedAmount;
	}
	public void setClaimedAmount(String claimedAmount) {
		this.claimedAmount = claimedAmount;
	}

	
	public String getClaimSubmittedDate() {
		return claimSubmittedDate;
	}
	public void setClaimSubmittedDate(String claimSubmittedDate) {
		this.claimSubmittedDate = claimSubmittedDate;
	}
	
	 public String getEnhance() {
		 return enhance;
	 }
	 public void setEnhance(String enhance) {
		 this.enhance = enhance;
	 }

	 public String getCountryName() {
		 return countryName;
	 }
	 public void setCountryName(String countryName) {
		 this.countryName = countryName;
	 }

	 public String getEnhanceImageName() {
		 return enhanceImageName;
	 }
	 public void setEnhanceImageName(String enhanceImageName) {
		 this.enhanceImageName = enhanceImageName;
	 }
	 public String getEnhanceImageTitle() {
		 return enhanceImageTitle;
	 }
	 public void setEnhanceImageTitle(String enhanceImageTitle) {
		 this.enhanceImageTitle = enhanceImageTitle;
	 }

	public String getEnhanceYN() {
		return enhanceYN;
	}
	public void setEnhanceYN(String enhanceYN) {
		this.enhanceYN = enhanceYN;
	}
	public String getAgreed_tariff() {
		return agreed_tariff;
	}
	public void setAgreed_tariff(String agreed_tariff) {
		this.agreed_tariff = agreed_tariff;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getDiscounted_gross() {
		return discounted_gross;
	}
	public void setDiscounted_gross(String discounted_gross) {
		this.discounted_gross = discounted_gross;
	}
	public String getPatient_share() {
		return patient_share;
	}
	public void setPatient_share(String patient_share) {
		this.patient_share = patient_share;
	}
	private String rej_amount;
	private String patient_share_amount;
	private String payable;
	private String rej_reason;
	private String ACTIVITY_CODE;
	
	
	public String getPatientname() {
		return patientname;
	}
	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}
	public String getPatient_idcard_no() {
		return patient_idcard_no;
	}
	public void setPatient_idcard_no(String patient_idcard_no) {
		this.patient_idcard_no = patient_idcard_no;
	}
	public String getInsurancycompany() {
		return insurancycompany;
	}
	public void setInsurancycompany(String insurancycompany) {
		this.insurancycompany = insurancycompany;
	}
	public String getCorporate_name() {
		return corporate_name;
	}
	public void setCorporate_name(String corporate_name) {
		this.corporate_name = corporate_name;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getReq_amount() {
		return req_amount;
	}
	public void setReq_amount(String req_amount) {
		this.req_amount = req_amount;
	}
	public String getRej_amount() {
		return rej_amount;
	}
	public void setRej_amount(String rej_amount) {
		this.rej_amount = rej_amount;
	}
	public String getPatient_share_amount() {
		return patient_share_amount;
	}
	public void setPatient_share_amount(String patient_share_amount) {
		this.patient_share_amount = patient_share_amount;
	}
	public String getPayable() {
		return payable;
	}
	public void setPayable(String payable) {
		this.payable = payable;
	}
	public String getRej_reason() {
		return rej_reason;
	}
	public void setRej_reason(String rej_reason) {
		this.rej_reason = rej_reason;
	}
	public String getACTIVITY_CODE() {
		return ACTIVITY_CODE;
	}
	public void setACTIVITY_CODE(String aCTIVITY_CODE) {
		ACTIVITY_CODE = aCTIVITY_CODE;
	}

	

	
	public String getClaimnumber() {
		return claimnumber;
	}
	public void setClaimnumber(String claimnumber) {
		this.claimnumber = claimnumber;
	}
	public String getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCalimType() {
		return calimType;
	}
	public void setCalimType(String calimType) {
		this.calimType = calimType;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Long getClmSeqId() {
		return clmSeqId;
	}
	public void setClmSeqId(Long clmSeqId) {
		this.clmSeqId = clmSeqId;
	}
	public Long getClmBatchSeqId() {
		return clmBatchSeqId;
	}
	public void setClmBatchSeqId(Long clmBatchSeqId) {
		this.clmBatchSeqId = clmBatchSeqId;
	}
	public String getPreviousApprAmt() {
		return previousApprAmt;
	}
	public void setPreviousApprAmt(String previousApprAmt) {
		this.previousApprAmt = previousApprAmt;
	}
	public String getRequestedAmt() {
		return requestedAmt;
	}
	public void setRequestedAmt(String requestedAmt) {
		this.requestedAmt = requestedAmt;
	}
	public String getVidalBranch() {
		return vidalBranch;
	}
	public void setVidalBranch(String vidalBranch) {
		this.vidalBranch = vidalBranch;
	}
	public String getProbableDischargeDt() {
		return probableDischargeDt;
	}
	public void setProbableDischargeDt(String probableDischargeDt) {
		this.probableDischargeDt = probableDischargeDt;
	}
	public String getBeneficiaryId() {
		return beneficiaryId;
	}
	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getEmpanelId() {
		return empanelId;
	}
	public void setEmpanelId(String empanelId) {
		this.empanelId = empanelId;
	}
	public String getEmirate() {
		return emirate;
	}
	public void setEmirate(String emirate) {
		this.emirate = emirate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getApprStatus() {
		return apprStatus;
	}
	public void setApprStatus(String apprStatus) {
		this.apprStatus = apprStatus;
	}
	public String getDecisionDt() {
		return decisionDt;
	}
	public void setDecisionDt(String decisionDt) {
		this.decisionDt = decisionDt;
	}

	public String getPreAuthNo() {
		return preAuthNo;
	}
	public void setPreAuthNo(String preAuthNo) {
		this.preAuthNo = preAuthNo;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getBenefitType() {
		return benefitType;
	}
	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}
	public String getTreatingDoctor() {
		return treatingDoctor;
	}
	public void setTreatingDoctor(String treatingDoctor) {
		this.treatingDoctor = treatingDoctor;
	}
	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPatientCardId() {
		return patientCardId;
	}
	public void setPatientCardId(String patientCardId) {
		this.patientCardId = patientCardId;
	}
	/**
	 * @return the admissionDate
	 */
	public String getAdmissionDate() {
		return admissionDate;
	}
	/**
	 * @param admissionDate the admissionDate to set
	 */
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	/**
	 * @return the preAuthType
	 */
	public String getPreAuthType() {
		return preAuthType;
	}
	/**
	 * @param preAuthType the preAuthType to set
	 */
	public void setPreAuthType(String preAuthType) {
		this.preAuthType = preAuthType;
	}
	/**
	 * @return the statusID
	 */
	public String getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID the statusID to set
	 */
	public void setStatusID(String statusID) {
		this.statusID = statusID;
	}
	/**
	 * @return the patAuthSeqId
	 */
	public Long getPatAuthSeqId() {
		return patAuthSeqId;
	}
	/**
	 * @param patAuthSeqId the patAuthSeqId to set
	 */
	public void setPatAuthSeqId(Long patAuthSeqId) {
		this.patAuthSeqId = patAuthSeqId;
	}
	/**
	 * @return the claimType
	 */
	public String getClaimType() {
		return claimType;
	}
	/**
	 * @param claimType the claimType to set
	 */
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	/**
	 * @return the chequeNo
	 */
	public String getChequeNo() {
		return chequeNo;
	}
	
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	/**
	 * @param chequeNo the chequeNo to set
	 */
	
	public String getPreAuthRefNo() {
		return preAuthRefNo;
	}
	public void setPreAuthRefNo(String preAuthRefNo) {
		this.preAuthRefNo = preAuthRefNo;
	}
	public String getEmirateID() {
		return emirateID;
	}
	public void setEmirateID(String emirateID) {
		this.emirateID = emirateID;
	}
	public String getMedicalOpinionRemarks() {
		return medicalOpinionRemarks;
	}
	public void setMedicalOpinionRemarks(String medicalOpinionRemarks) {
		this.medicalOpinionRemarks = medicalOpinionRemarks;
	}
}
