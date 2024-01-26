package com.ttk.dto.onlineforms.providerLogin; 

import java.math.BigDecimal;
import java.util.ArrayList;

import oracle.sql.CLOB;

import com.ttk.dto.BaseVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;

public class PbmPreAuthVO extends BaseVO {

	private static final long serialVersionUID = 1L;
	private Long hospitalSeqID;
    private Long preAuthSeqID;
    private String preAuthNO;
	private String enrolmentID;
	private String qatarID;
	private String dobDate;
	private String insCompanyID;
	private String clinicianID;
	private String clinicianName;	
	private String dateOfTreatment;
	private String icdCode;
	private Long icdCodeSeqID;
	private String icdCodeDesc;
	private Long icdDtlSeqID;
	private String drugDesc;
	private String drugCode;
	private Long drugCodeSeqID;
	private Long drugDtlSeqID;
	private String quantity;
	private Long qnty;
	private String unitType;
	private String days;
	private Long memSeqID;
	private String btnMode;
	private String transactionDate;
	private String insuranceCompanyName;
	private String denialComments;
	private String authorizationNO;
	private String erxRef;
	private String commonts;
	private String status;
	private String memberName;
	private String memGender;
	
	private String finalStatus;
	
	private String preAuthStatus;
	private Long clinicianSeqID;
	private BigDecimal totalReqAmt;
	private BigDecimal totalApprAmt;
	private String inputPBMXML;
	private String responsePBMXML;
	private String drugCodeQuantity;
	private BigDecimal totalReqAmtforApp;
	
	private String batchNo;
	private String invoiceNo;
	private String claimNo;
	private String chequeNo;
	private Long clmSeqId;;
	private Long clmBatchSeqId;
	private String claimType;
	private String claimStatus;
	
	private String claimFlag;
	
	private String claimSubmittedDate;

	private String treatmentDate;
	private String claimedAmount;
	private String dispenseStatus;
	
	private String eventRefNo;
	private String paymentRefNo;
	private BigDecimal discountAmt;
	private BigDecimal disAlwdAmt;
	private BigDecimal agreedAmt;
	private String denialReason;
	private BigDecimal patientShare;
	
	private String policyNo;
	private String settlementNO;
	private String clmPayStatus;
	
	public String getClmPayStatus() {
		return clmPayStatus;
	}
	public void setClmPayStatus(String clmPayStatus) {
		this.clmPayStatus = clmPayStatus;
	}
	public String getSettlementNO() {
		return settlementNO;
	}
	public void setSettlementNO(String settlementNO) {
		this.settlementNO = settlementNO;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	
	
	
	public Long getQnty() {
		return qnty;
	}
	public void setQnty(Long qnty) {
		this.qnty = qnty;
	}
	public BigDecimal getPatientShare() {
		return patientShare;
	}
	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}
	public String getPaymentRefNo() {
		return paymentRefNo;
	}
	public void setPaymentRefNo(String paymentRefNo) {
		this.paymentRefNo = paymentRefNo;
	}
	public BigDecimal getDiscountAmt() {
		return discountAmt;
	}
	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
	}
	public BigDecimal getDisAlwdAmt() {
		return disAlwdAmt;
	}
	public void setDisAlwdAmt(BigDecimal disAlwdAmt) {
		this.disAlwdAmt = disAlwdAmt;
	}
	public BigDecimal getAgreedAmt() {
		return agreedAmt;
	}
	public void setAgreedAmt(BigDecimal agreedAmt) {
		this.agreedAmt = agreedAmt;
	}
	public String getDenialReason() {
		return denialReason;
	}
	public void setDenialReason(String denialReason) {
		this.denialReason = denialReason;
	}
	public String getDispenseStatus() {
		return dispenseStatus;
	}
	public String getEventRefNo() {
		return eventRefNo;
	}
	public void setEventRefNo(String eventRefNo) {
		this.eventRefNo = eventRefNo;
	}
	public String Status() {
		return dispenseStatus;
	}
	public void setDispenseStatus(String dispenseStatus) {
		this.dispenseStatus = dispenseStatus;
	}
	public String getClaimFlag() {
		return claimFlag;
	}
	public void setClaimFlag(String claimFlag) {
		this.claimFlag = claimFlag;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getClaimSubmittedDate() {
		return claimSubmittedDate;
	}
	public void setClaimSubmittedDate(String claimSubmittedDate) {
		this.claimSubmittedDate = claimSubmittedDate;
	}
	public String getClaimedAmount() {
		return claimedAmount;
	}
	public void setClaimedAmount(String claimedAmount) {
		this.claimedAmount = claimedAmount;
	}
public BigDecimal getTotalReqAmtforApp() {
		return totalReqAmtforApp;
	}
	public void setTotalReqAmtforApp(BigDecimal totalReqAmtforApp) {
		this.totalReqAmtforApp = totalReqAmtforApp;
	}
public String getInputPBMXML() {
	return inputPBMXML;
}
public void setInputPBMXML(String inputPBMXML) {
	this.inputPBMXML = inputPBMXML;
}
public String getResponsePBMXML() {
	return responsePBMXML;
}
public void setResponsePBMXML(String responsePBMXML) {
	this.responsePBMXML = responsePBMXML;
}


	
	public BigDecimal getTotalApprAmt() {
		return totalApprAmt;
	}
	public void setTotalApprAmt(BigDecimal totalApprAmt) {
		this.totalApprAmt = totalApprAmt;
	}
	private ArrayList<DiagnosisDetailsVO> icdDetails;
	private ArrayList<ActivityDetailsVO> drugDetails;
	
	
	public void setTotalReqAmt(BigDecimal totalReqAmt) {
		this.totalReqAmt = totalReqAmt;
	}
	public BigDecimal getTotalReqAmt() {
		return totalReqAmt;
	}
	public void setClinicianSeqID(Long clinicianSeqID) {
		this.clinicianSeqID = clinicianSeqID;
	}
	public Long getClinicianSeqID() {
		return clinicianSeqID;
	}
	
	public void setPreAuthStatus(String preAuthStatus) {
		this.preAuthStatus = preAuthStatus;
	}
	public String getPreAuthStatus() {
		return preAuthStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	public String getMemGender() {
		return memGender;
	}
	public void setMemGender(String memGender) {
		this.memGender = memGender;
	}
	public void setCommonts(String commonts) {
		this.commonts = commonts;
	}
	public String getCommonts() {
		return commonts;
	}
	public void setErxRef(String erxRef) {
		this.erxRef = erxRef;
	}
	public String getErxRef() {
		return erxRef;
	}
	
	public void setAuthorizationNO(String authorizationNO) {
		this.authorizationNO = authorizationNO;
	}
	public String getAuthorizationNO() {
		return authorizationNO;
	}
	public void setDenialComments(String denialComments) {
		this.denialComments = denialComments;
	}
	public String getDenialComments() {
		return denialComments;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	
	public void setDrugDtlSeqID(Long drugDtlSeqID) {
		this.drugDtlSeqID = drugDtlSeqID;
	}
	public Long getDrugDtlSeqID() {
		return drugDtlSeqID;
	}
	public void setPreAuthNO(String preAuthNO) {
		this.preAuthNO = preAuthNO;
	}
	public String getPreAuthNO() {
		return preAuthNO;
	}
	
	public void setHospitalSeqID(Long hospitalSeqID) {
		this.hospitalSeqID = hospitalSeqID;
	}
	
	public Long getHospitalSeqID() {
		return hospitalSeqID;
	}
	
	public void setIcdDtlSeqID(Long icdDtlSeqID) {
		this.icdDtlSeqID = icdDtlSeqID;
	}
	public Long getIcdDtlSeqID() {
		return icdDtlSeqID;
	}
	public void setDrugDetails(ArrayList<ActivityDetailsVO> drugDetails) {
		this.drugDetails = drugDetails;
	}
	public ArrayList<ActivityDetailsVO> getDrugDetails() {
		return drugDetails;
	}
	
	public void setIcdDetails(ArrayList<DiagnosisDetailsVO> icdDetails) {
		this.icdDetails = icdDetails;
	}
	public ArrayList<DiagnosisDetailsVO> getIcdDetails() {
		return icdDetails;
	}
	
	
	public void setPreAuthSeqID(Long preAuthSeqID) {
		this.preAuthSeqID = preAuthSeqID;
	}
	public Long getPreAuthSeqID() {
		return preAuthSeqID;
	}
	public void setBtnMode(String btnMode) {
		this.btnMode = btnMode;
	}
	public String getBtnMode() {
		return btnMode;
	}
	public void setMemSeqID(Long memSeqID) {
		this.memSeqID = memSeqID;
	}
	public Long getMemSeqID() {
		return memSeqID;
	}
	
	
	public void setClinicianName(String clinicianName) {
		this.clinicianName = clinicianName;
	}
	public String getClinicianName() {
		return clinicianName;
	}
	
	public Long getIcdCodeSeqID() {
		return icdCodeSeqID;
	}
	public void setIcdCodeSeqID(Long icdCodeSeqID) {
		this.icdCodeSeqID = icdCodeSeqID;
	}
	public String getDrugCode() {
		return drugCode;
	}
	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}
	public Long getDrugCodeSeqID() {
		return drugCodeSeqID;
	}
	public void setDrugCodeSeqID(Long drugCodeSeqID) {
		this.drugCodeSeqID = drugCodeSeqID;
	}
	public String getEnrolmentID() {
		return enrolmentID;
	}
	public void setEnrolmentID(String enrolmentID) {
		this.enrolmentID = enrolmentID;
	}
	public String getQatarID() {
		return qatarID;
	}
	public void setQatarID(String qatarID) {
		this.qatarID = qatarID;
	}
	public String getDobDate() {
		return dobDate;
	}
	public void setDobDate(String dobDate) {
		this.dobDate = dobDate;
	}
	public String getInsCompanyID() {
		return insCompanyID;
	}
	public void setInsCompanyID(String insCompanyID) {
		this.insCompanyID = insCompanyID;
	}
	public String getClinicianID() {
		return clinicianID;
	}
	public void setClinicianID(String clinicianID) {
		this.clinicianID = clinicianID;
	}
	public String getDateOfTreatment() {
		return dateOfTreatment;
	}
	public void setDateOfTreatment(String dateOfTreatment) {
		this.dateOfTreatment = dateOfTreatment;
	}
	public String getIcdCode() {
		return icdCode;
	}
	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}
	public String getIcdCodeDesc() {
		return icdCodeDesc;
	}
	public void setIcdCodeDesc(String icdCodeDesc) {
		this.icdCodeDesc = icdCodeDesc;
	}
	public String getDrugDesc() {
		return drugDesc;
	}
	public void setDrugDesc(String drugDesc) {
		this.drugDesc = drugDesc;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	

	public String getDrugCodeQuantity() {
		return drugCodeQuantity;
	}


	public void setDrugCodeQuantity(String drugCodeQuantity) {
		this.drugCodeQuantity = drugCodeQuantity;
	}
	
}
