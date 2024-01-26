package com.ttk.dto.onlineforms.providerLogin; 

import java.util.Date;

import com.ttk.dto.BaseVO;

public class BatchListVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	private Date batchDate;
	private Long batchSeqId;
	private String batchNumber;
	private Integer NoOfInvs;
	public Double claimedAmt;
	public Integer underProcess;
	public Integer approved;
	public Integer rejected;
	public Integer shortFall;
	public Integer closed;
	
	//getters for BatchInv Details
	private String receivedDate;
	private String invNo;
	private String batchNo;
	private String treatmentDate;
	private String patientName;
	private String ttlNetClmd;
	private String ttlAmtApprd;
	
	
	//getters for Batch Invoice Report
	private Integer slno;
	private String insCompName;
	private  String DtOfHospitalization;
	private String claimStatus;
	
	//getters for OverDue Report

	private String dueDate;
	private String oveDueBy;
	
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getOveDueBy() {
		return oveDueBy;
	}
	public void setOveDueBy(String oveDueBy) {
		this.oveDueBy = oveDueBy;
	}
	public Integer getSlno() {
		return slno;
	}
	public void setSlno(Integer slno) {
		this.slno = slno;
	}
	public String getInsCompName() {
		return insCompName;
	}
	public void setInsCompName(String insCompName) {
		this.insCompName = insCompName;
	}
	public String getDtOfHospitalization() {
		return DtOfHospitalization;
	}
	public void setDtOfHospitalization(String dtOfHospitalization) {
		DtOfHospitalization = dtOfHospitalization;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public String getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getInvNo() {
		return invNo;
	}
	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}
	public String getTreatmentDate() {
		return treatmentDate;
	}
	public void setTreatmentDate(String treatmentDate) {
		this.treatmentDate = treatmentDate;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getTtlNetClmd() {
		return ttlNetClmd;
	}
	public void setTtlNetClmd(String ttlNetClmd) {
		this.ttlNetClmd = ttlNetClmd;
	}
	public String getTtlAmtApprd() {
		return ttlAmtApprd;
	}
	public void setTtlAmtApprd(String ttlAmtApprd) {
		this.ttlAmtApprd = ttlAmtApprd;
	}
	public Date getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Integer getNoOfInvs() {
		return NoOfInvs;
	}
	public void setNoOfInvs(Integer noOfInvs) {
		NoOfInvs = noOfInvs;
	}
	public Double getClaimedAmt() {
		return claimedAmt;
	}
	public void setClaimedAmt(Double claimedAmt) {
		this.claimedAmt = claimedAmt;
	}
	public Integer getUnderProcess() {
		return underProcess;
	}
	public void setUnderProcess(Integer underProces) {
		this.underProcess = underProces;
	}
	public Integer getApproved() {
		return approved;
	}
	public void setApproved(Integer approved) {
		this.approved = approved;
	}
	public Integer getRejected() {
		return rejected;
	}
	public void setRejected(Integer rejected) {
		this.rejected = rejected;
	}
	public Integer getShortFall() {
		return shortFall;
	}
	public void setShortFall(Integer shortFall) {
		this.shortFall = shortFall;
	}
	public Integer getClosed() {
		return closed;
	}
	public void setClosed(Integer closed) {
		this.closed = closed;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the batchSeqId
	 */
	public Long getBatchSeqId() {
		return batchSeqId;
	}
	/**
	 * @param batchSeqId the batchSeqId to set
	 */
	public void setBatchSeqId(Long batchSeqId) {
		this.batchSeqId = batchSeqId;
	}
	/**
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
}
