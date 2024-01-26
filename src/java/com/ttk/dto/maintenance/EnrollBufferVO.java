package com.ttk.dto.maintenance;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class EnrollBufferVO extends BaseVO {

		
	private BigDecimal bdTotBufferAmt = null;
	private Long lngPolicySeqId = null;
	private Long lngPolicyGroupSeqId = null;
	private Long lngMemberSeqId = null;
	private Long lngMemberBuffSeqId = null;
	private String strRefNbr = "";
	private Date dtBufferDate = null;
	private Date dtBufferDate1 = null;
	private String strModeDesc = "";
	private BigDecimal bdBufferAmt = null;//approving amount(configure) and previously configured amount
	private String strRemarks = "";
	private String strApprovedBy = "";
	private String strEnrollmentId = "";
	private String strUserId = "";
	private String strModeTypeId = "";
	private BigDecimal strAvCorpBuffer = null;//ava corp buffer
	private BigDecimal strAvFamilyBuffer = null;//family buffer
	
	private BigDecimal strAvMemberBuffer = null;//total member buffer
	private BigDecimal strMemberBufferAlloc = null;//total member buffer
	private BigDecimal strHrInsurerBuffAmount=null;
	

	private String editYN="";
	//added for hyundai buffer
	private String strClaimType="";
	private String strBufferType="";
	private String strBufferType1="";
	private String strClaimTypeDesc="";
	private String strBufferTypeDesc="";
	private BigDecimal strMemberMedBufferAlloc = null;//total member buffer
	private BigDecimal strMemberCritBufferAlloc = null;//total member buffer
	private BigDecimal strMemberCritCorpBufferAlloc = null;//total member buffer
	private BigDecimal strMemberCritMedBufferAlloc = null;//total member buffer
	
	
	
	
	
	
	/**
	 * @param bufferDate the bufferDate to set
	 */
	public void setBufferDate(Date bufferDate) {
		dtBufferDate = bufferDate;
	}
	/**
	 * @return the bufferDate
	 */
	public Date getBufferDate() {
		return dtBufferDate;
	}
	/**
	 * 
	 * @param bufferDate1 the bufferDate1 to set
	 */
	public void setBufferDate1(Date bufferDate1) {
		dtBufferDate1 = bufferDate1;
	}
	/**
	 * @return the bufferDate1
	 */
	public String getBufferDate1() {
		return TTKCommon.getFormattedDate(dtBufferDate1);
	}



	
	
	/**
	 * @param hrInsurerBuffAmount the hrInsurerBuffAmount to set
	 */
	public void setHrInsurerBuffAmount(BigDecimal hrInsurerBuffAmount) {
		this.strHrInsurerBuffAmount = hrInsurerBuffAmount;
	}
	/**
	 * @return the hrInsurerBuffAmount
	 */
	public BigDecimal getHrInsurerBuffAmount() {
		return strHrInsurerBuffAmount;
	}
	/**
	 * @param avMemberBuffer the avMemberBuffer to set
	 */
	public void setAvMemberBuffer(BigDecimal avMemberBuffer) {
		this.strAvMemberBuffer = avMemberBuffer;
	}
	/**
	 * @return the avMemberBuffer
	 */
	public BigDecimal getAvMemberBuffer() {
		return strAvMemberBuffer;
	}
	/**
	 * @param editYN the editYN to set
	 */
	public void setEditYN(String editYN) {
		this.editYN = editYN;
	}
	/**
	 * @return the editYN
	 */
	public String getEditYN() {
		return editYN;
	}
	/**
	 * @param totBufferAmt the totBufferAmt to set
	 */
	public void setTotBufferAmt(BigDecimal totBufferAmt) {
		this.bdTotBufferAmt = totBufferAmt;
	}
	/**
	 * @return the totBufferAmt
	 */
	public BigDecimal getTotBufferAmt() {
		return bdTotBufferAmt;
	}

	/**
	 * @param refNbr the refNbr to set
	 */
	public void setRefNbr(String refNbr) {
		this.strRefNbr = refNbr;
	}
	/**
	 * @return the refNbr
	 */
	public String getRefNbr() {
		return strRefNbr;
	}
	
	
	/**
	 * @param modeDesc the modeDesc to set
	 */
	public void setModeDesc(String modeDesc) {
		this.strModeDesc = modeDesc;
	}
	/**
	 * @return the modeDesc
	 */
	public String getModeDesc() {
		return strModeDesc;
	}
	/**
	 * @param bufferAmt the bufferAmt to set
	 */
	public void setBufferAmt(BigDecimal bufferAmt) {
		this.bdBufferAmt = bufferAmt;
	}
	/**
	 * @return the bufferAmt
	 */
	public BigDecimal getBufferAmt() {
		return bdBufferAmt;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.strRemarks = remarks;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return strRemarks;
	}
	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.strApprovedBy = approvedBy;
	}
	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return strApprovedBy;
	}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.strUserId = userId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return strUserId;
	}
	/**
	 * @param enrollmentId the enrollmentId to set
	 */
	public void setEnrollmentId(String enrollmentId) {
		this.strEnrollmentId = enrollmentId;
	}
	/**
	 * @return the enrollmentId
	 */
	public String getEnrollmentId() {
		return strEnrollmentId;
	}
	/**
	 * @param avCorpBuffer the avCorpBuffer to set
	 */
	public void setAvCorpBuffer(BigDecimal avCorpBuffer) {
		this.strAvCorpBuffer = avCorpBuffer;
	}
	/**
	 * @return the avCorpBuffer
	 */
	public BigDecimal getAvCorpBuffer() {
		return strAvCorpBuffer;
	}
	/**
	 * @param avFamilyBuffer the avFamilyBuffer to set
	 */
	public void setAvFamilyBuffer(BigDecimal avFamilyBuffer) {
		this.strAvFamilyBuffer = avFamilyBuffer;
	}
	/**
	 * @return the avFamilyBuffer
	 */
	public BigDecimal getAvFamilyBuffer() {
		return strAvFamilyBuffer;
	}
	/**
	 * @param memberBufferAlloc the memberBufferAlloc to set
	 */
	public void setMemberBufferAlloc(BigDecimal memberBufferAlloc) {
		this.strMemberBufferAlloc = memberBufferAlloc;
	}
	/**
	 * @return the memberBufferAlloc
	 */
	public BigDecimal getMemberBufferAlloc() {
		return strMemberBufferAlloc;
	}
	/**
	 * @param modeTypeId the modeTypeId to set
	 */
	public void setModeTypeId(String modeTypeId) {
		this.strModeTypeId = modeTypeId;
	}
	/**
	 * @return the modeTypeId
	 */
	public String getModeTypeId() {
		return strModeTypeId;
	}
	/**
	 * @param policySeqId the policySeqId to set
	 */
	public void setPolicySeqId(Long policySeqId) {
		this.lngPolicySeqId = policySeqId;
	}
	/**
	 * @return the policySeqId
	 */
	public Long getPolicySeqId() {
		return lngPolicySeqId;
	}
	/**
	 * @param policyGroupSeqId the policyGroupSeqId to set
	 */
	public void setPolicyGroupSeqId(Long policyGroupSeqId) {
		this.lngPolicyGroupSeqId = policyGroupSeqId;
	}
	/**
	 * @return the policyGroupSeqId
	 */
	public Long getPolicyGroupSeqId() {
		return lngPolicyGroupSeqId;
	}
	/**
	 * @param memberSeqId the memberSeqId to set
	 */
	public void setMemberSeqId(Long memberSeqId) {
		this.lngMemberSeqId = memberSeqId;
	}
	/**
	 * @return the memberSeqId
	 */
	public Long getMemberSeqId() {
		return lngMemberSeqId;
	}
	/**
	 * @param memberBuffSeqId the memberBuffSeqId to set
	 */
	public void setMemberBuffSeqId(Long memberBuffSeqId) {
		this.lngMemberBuffSeqId = memberBuffSeqId;
	}
	/**
	 * @return the memberBuffSeqId
	 */
	public Long getMemberBuffSeqId() {
		return lngMemberBuffSeqId;
	}
	public String getClaimType() {
		return strClaimType;
	}
	public void setClaimType(String strClaimType) {
		this.strClaimType = strClaimType;
	}
	public String getBufferType() {
		return strBufferType;
	}
	public void setBufferType(String strBufferType) {
		this.strBufferType = strBufferType;
	}
	public String getBufferType1() {
		return strBufferType1;
	}
	public void setBufferType1(String strBufferType1) {
		this.strBufferType1 = strBufferType1;
	}
	public String getClaimTypeDesc() {
		return strClaimTypeDesc;
	}
	public void setClaimTypeDesc(String strClaimTypeDesc) {
		this.strClaimTypeDesc = strClaimTypeDesc;
	}
	public String getBufferTypeDesc() {
		return strBufferTypeDesc;
	}
	public void setBufferTypeDesc(String strBufferTypeDesc) {
		this.strBufferTypeDesc = strBufferTypeDesc;
	}
	public BigDecimal getMemberMedBufferAlloc() {
		return strMemberMedBufferAlloc;
	}
	public void setMemberMedBufferAlloc(BigDecimal strMemberMedBufferAlloc) {
		this.strMemberMedBufferAlloc = strMemberMedBufferAlloc;
	}
	public BigDecimal getMemberCritBufferAlloc() {
		return strMemberCritBufferAlloc;
	}
	public void setMemberCritBufferAlloc(BigDecimal strMemberCritBufferAlloc) {
		this.strMemberCritBufferAlloc = strMemberCritBufferAlloc;
	}
	public BigDecimal getMemberCritCorpBufferAlloc() {
		return strMemberCritCorpBufferAlloc;
	}
	public void setMemberCritCorpBufferAlloc(
			BigDecimal strMemberCritCorpBufferAlloc) {
		this.strMemberCritCorpBufferAlloc = strMemberCritCorpBufferAlloc;
	}
	public BigDecimal getMemberCritMedBufferAlloc() {
		return strMemberCritMedBufferAlloc;
	}
	public void setMemberCritMedBufferAlloc(BigDecimal strMemberCritMedBufferAlloc) {
		this.strMemberCritMedBufferAlloc = strMemberCritMedBufferAlloc;
	}
	

	

}
