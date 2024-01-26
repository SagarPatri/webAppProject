/**
 * @ (#) BatchVO.java July 8, 2015
 * Project 	     : ProjectX
 * File          : BatchVO.java
 * Author        : Nagababu K
 * Company       : RCS Technologies
 * Date Created  : July 8, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class BatchVO extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long batchSeqID;
	private String batchNO;
	private String batchReceiveDate;
	private BigDecimal totalAmount;
	private Long providerSeqID;
	private String providerName;
	private Integer noOfClaimsReceived;
	private String encounterTypeId;
	private String submissionType;
	private String claimType;
	private String totalAmountCurrency;
	private String batchStatus;
	private String providerInvoiceNO;
	private Long claimSeqID;
	private String claimNO;
	private BigDecimal requestedAmount;
	private String validateYN;
	private String modeOfClaim="";
	private BigDecimal addedTotalAmount;
	private String overrideYN;
	private String completedYN;
	private String benefitType;
	private String previousClaimNO;
	private Long previousClaimNOSeqID;
	private String receivedTime;
	private String receiveDay;
	private String netWorkType;
	private String providerID;
	private String providerLisenceNO;
	private String enrollmentID;
	private String resubmissionRemarks;
	private String claimFrom;
	
	private Long enrollmentSeqID;
	private String processType = "";
	private String paymentTo = "";
	private String partnerName = "";
	private String eclaimOverrideYN = "";
	
	public BatchVO() {
		// TODO Auto-generated constructor stub
	}
	public BatchVO(Long claimSeqID,String providerInvoiceNO,String claimNO,BigDecimal requestedAmount,String previousClaimNO,Long previousClaimNOSeqID,String enrollmentID,Long enrollmentSeqID,String resubmissionRemarks) {
		this.claimSeqID=claimSeqID;
		this.providerInvoiceNO=providerInvoiceNO;
		this.claimNO=claimNO;
		this.requestedAmount=requestedAmount;
		this.previousClaimNO=previousClaimNO;
		this.previousClaimNOSeqID=previousClaimNOSeqID;
		this.enrollmentID=enrollmentID;
		this.enrollmentSeqID=enrollmentSeqID;
		this.resubmissionRemarks=resubmissionRemarks;
	}
	
	public Long getBatchSeqID() {
		return batchSeqID;
	}
	public void setBatchSeqID(Long batchSeqID) {
		this.batchSeqID = batchSeqID;
	}
	public String getBatchNO() {
		return batchNO;
	}
	public void setBatchNO(String batchNO) {
		this.batchNO = batchNO;
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Integer getNoOfClaimsReceived() {
		return noOfClaimsReceived;
	}
	public void setNoOfClaimsReceived(Integer noOfClaimsReceived) {
		this.noOfClaimsReceived = noOfClaimsReceived;
	}
	public Long getProviderSeqID() {
		return providerSeqID;
	}
	public void setProviderSeqID(Long providerSeqID) {
		this.providerSeqID = providerSeqID;
	}
	public String getEncounterTypeId() {
		return encounterTypeId;
	}
	public void setEncounterTypeId(String encounterTypeId) {
		this.encounterTypeId = encounterTypeId;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getSubmissionType() {
		return submissionType;
	}
	public void setSubmissionType(String submissionType) {
		this.submissionType = submissionType;
	}
	public String getTotalAmountCurrency() {
		return totalAmountCurrency;
	}
	public void setTotalAmountCurrency(String totalAmountCurrency) {
		this.totalAmountCurrency = totalAmountCurrency;
	}
	public String getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	public String getBatchReceiveDate() {
		return batchReceiveDate;
	}
	public void setBatchReceiveDate(String batchReceiveDate) {
		this.batchReceiveDate = batchReceiveDate;
	}
	public String getProviderInvoiceNO() {
		return providerInvoiceNO;
	}
	public void setProviderInvoiceNO(String providerInvoiceNO) {
		this.providerInvoiceNO = providerInvoiceNO;
	}
	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}
	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}
	public Long getClaimSeqID() {
		return claimSeqID;
	}
	public void setClaimSeqID(Long claimSeqID) {
		this.claimSeqID = claimSeqID;
	}
	public String getClaimNO() {
		return claimNO;
	}
	public void setClaimNO(String claimNO) {
		this.claimNO = claimNO;
	}
	public String getValidateYN() {
		return validateYN;
	}
	public void setValidateYN(String validateYN) {
		this.validateYN = validateYN;
	}
	public String getModeOfClaim() {
		return modeOfClaim;
	}
	public void setModeOfClaim(String modeOfClaim) {
		this.modeOfClaim = modeOfClaim;
	}
	public BigDecimal getAddedTotalAmount() {
		return addedTotalAmount;
	}
	public void setAddedTotalAmount(BigDecimal addedTotalAmount) {
		this.addedTotalAmount = addedTotalAmount;
	}
	public String getOverrideYN() {
		return overrideYN;
	}
	public void setOverrideYN(String overrideYN) {
		this.overrideYN = overrideYN;
	}
	public String getCompletedYN() {
		return completedYN;
	}
	public void setCompletedYN(String completedYN) {
		this.completedYN = completedYN;
	}
	public String getBenefitType() {
		return benefitType;
	}
	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}
	public String getPreviousClaimNO() {
		return previousClaimNO;
	}
	public void setPreviousClaimNO(String previousClaimNO) {
		this.previousClaimNO = previousClaimNO;
	}
	public String getReceiveDay() {
		return receiveDay;
	}
	public void setReceiveDay(String receiveDay) {
		this.receiveDay = receiveDay;
	}
	public String getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}
	public String getNetWorkType() {
		return netWorkType;
	}
	public void setNetWorkType(String netWorkType) {
		this.netWorkType = netWorkType;
	}
	public String getProviderID() {
		return providerID;
	}
	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}
	public String getProviderLisenceNO() {
		return providerLisenceNO;
	}
	public void setProviderLisenceNO(String providerLisenceNO) {
		this.providerLisenceNO = providerLisenceNO;
	}
	public String getEnrollmentID() {
		return enrollmentID;
	}
	public void setEnrollmentID(String enrollmentID) {
		this.enrollmentID = enrollmentID;
	}
	public String getResubmissionRemarks() {
		return resubmissionRemarks;
	}
	public void setResubmissionRemarks(String resubmissionRemarks) {
		this.resubmissionRemarks = resubmissionRemarks;
	}
	public Long getEnrollmentSeqID() {
		return enrollmentSeqID;
	}
	public void setEnrollmentSeqID(Long enrollmentSeqID) {
		this.enrollmentSeqID = enrollmentSeqID;
	}
	public Long getPreviousClaimNOSeqID() {
		return previousClaimNOSeqID;
	}
	public void setPreviousClaimNOSeqID(Long previousClaimNOSeqID) {
		this.previousClaimNOSeqID = previousClaimNOSeqID;
	}
	public String getClaimFrom() {
		return claimFrom;
	}
	public void setClaimFrom(String claimFrom) {
		this.claimFrom = claimFrom;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getPaymentTo() {
		return paymentTo;
	}
	public void setPaymentTo(String paymentTo) {
		this.paymentTo = paymentTo;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getEclaimOverrideYN() {
		return eclaimOverrideYN;
	}
	public void setEclaimOverrideYN(String eclaimOverrideYN) {
		this.eclaimOverrideYN = eclaimOverrideYN;
	}

}//end of BatchVO
