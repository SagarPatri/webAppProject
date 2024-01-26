/** 
 *  @ (#)ClaimantVO.java Apr 10, 2006
 *  Project       : TTK HealthCare Services
 *  File          : ClaimantVO.java
 *  Author        : Arun K N
 *  Company       : Span Systems Corporation
 *  Date Created  : Apr 10, 2006
 * 
 *  @author       :  Arun K N
 *  Modified by   :  
 *  Modified date :  
 *  Reason        :  
 *   
 */

package com.ttk.dto.preauth;

import com.ttk.dto.BaseVO;




public class DiagnosisDetailsVO extends BaseVO{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
private String	primaryAilment;
private String	ailmentDescription;
private String	icdCode;
private Long icdCodeSeqId;
private String presentingComplaints;
private String preAuthNo;
private Long preAuthSeqID;
private Long claimSeqID;
private Long diagSeqId;
private String authType;
private String infoType;
private String infoCode;
private String relPresentIllness;
private String relevantFindings;
//Added For PED
private Integer durAilment=new Integer(0);
private String durationFlag="";
private String firstDiagnosedDate="";

private String preCronTypeID;
private String preCronTypeYN;

private String preOneMedicalCondition="";

public String getPreCronTypeID() {
	return preCronTypeID;
}
public void setPreCronTypeID(String preCronTypeID) {
	this.preCronTypeID = preCronTypeID;
}
public String getPreCronTypeYN() {
	return preCronTypeYN;
}
public void setPreCronTypeYN(String preCronTypeYN) {
	this.preCronTypeYN = preCronTypeYN;
}
public DiagnosisDetailsVO() {
	// TODO Auto-generated constructor stub
}
public DiagnosisDetailsVO(String primaryAilment, String ailmentDescription,
		String icdCode, Long icdCodeSeqId, String presentingComplaints,
		String preAuthNo, Long preAuthSeqID, Long diagSeqId) {
	super();
	this.primaryAilment = primaryAilment;
	this.ailmentDescription = ailmentDescription;
	this.icdCode = icdCode;
	this.icdCodeSeqId = icdCodeSeqId;
	this.presentingComplaints = presentingComplaints;
	this.preAuthNo = preAuthNo;
	this.preAuthSeqID = preAuthSeqID;
	this.diagSeqId = diagSeqId;	
}
public DiagnosisDetailsVO(String icdCode,String ailmentDescription,String primaryAilment,Long diagSeqId,Long icdCodeSeqId) {
	super();
	this.primaryAilment = primaryAilment;
	this.ailmentDescription = ailmentDescription;
	this.icdCode = icdCode;
	this.diagSeqId = diagSeqId;
	this.icdCodeSeqId=icdCodeSeqId;
}
public DiagnosisDetailsVO(String icdCode,String ailmentDescription,String primaryAilment) {
	super();
	this.primaryAilment = primaryAilment;
	this.ailmentDescription = ailmentDescription;
	this.icdCode = icdCode;
}



public DiagnosisDetailsVO(String icdCode,String ailmentDescription,String primaryAilment, 
	 Long diagSeqId, Long icdCodeSeqId, String infoType,
		String infoCode) {
	super();
	this.primaryAilment = primaryAilment;
	this.ailmentDescription = ailmentDescription;
	this.icdCode = icdCode;
	this.icdCodeSeqId = icdCodeSeqId;
	this.diagSeqId = diagSeqId;
	this.infoType = infoType;
	this.infoCode = infoCode;
}


public String getDurationFlag() {
	return durationFlag;
}
public void setDurationFlag(String durationFlag) {
	this.durationFlag = durationFlag;
}


public Integer getDurAilment() {
	return durAilment;
}
public void setDurAilment(Integer durAilment) {
	this.durAilment = durAilment;
}
public String getPrimaryAilment() {
	return primaryAilment;
}
public String getInfoType() {
	return infoType;
}
public void setInfoType(String infoType) {
	this.infoType = infoType;
}
public String getInfoCode() {
	return infoCode;
}
public void setInfoCode(String infoCode) {
	this.infoCode = infoCode;
}
public void setPrimaryAilment(String primaryAilment) {
	this.primaryAilment = primaryAilment;
}
public String getIcdCode() {
	return icdCode;
}
public void setIcdCode(String icdCode) {
	this.icdCode = icdCode;
}
public Long getIcdCodeSeqId() {
	return icdCodeSeqId;
}
public void setIcdCodeSeqId(Long icdCodeSeqId) {
	this.icdCodeSeqId = icdCodeSeqId;
}
public String getPresentingComplaints() {
	return presentingComplaints;
}
public void setPresentingComplaints(String presentingComplaints) {
	this.presentingComplaints = presentingComplaints;
}
public String getAilmentDescription() {
	return ailmentDescription;
}
public void setAilmentDescription(String ailmentDescription) {
	this.ailmentDescription = ailmentDescription;
}
public String getPreAuthNo() {
	return preAuthNo;
}
public void setPreAuthNo(String preAuthNo) {
	this.preAuthNo = preAuthNo;
}
public Long getPreAuthSeqID() {
	return preAuthSeqID;
}
public void setPreAuthSeqID(Long preAuthSeqID) {
	this.preAuthSeqID = preAuthSeqID;
}
public Long getDiagSeqId() {
	return diagSeqId;
}
public void setDiagSeqId(Long diagSeqId) {
	this.diagSeqId = diagSeqId;
}
public String getAuthType() {
	return authType;
}
public void setAuthType(String authType) {
	this.authType = authType;
}
public Long getClaimSeqID() {
	return claimSeqID;
}
public void setClaimSeqID(Long claimSeqID) {
	this.claimSeqID = claimSeqID;
}
public String getPreOneMedicalCondition() {
	return preOneMedicalCondition;
}
public void setPreOneMedicalCondition(String preOneMedicalCondition) {
	this.preOneMedicalCondition = preOneMedicalCondition;
}
public String getRelPresentIllness() {
	return relPresentIllness;
}
public void setRelPresentIllness(String relPresentIllness) {
	this.relPresentIllness = relPresentIllness;
}
public String getRelevantFindings() {
	return relevantFindings;
}
public void setRelevantFindings(String relevantFindings) {
	this.relevantFindings = relevantFindings;
}
public String getFirstDiagnosedDate() {
	return firstDiagnosedDate;
}
public void setFirstDiagnosedDate(String firstDiagnosedDate) {
	this.firstDiagnosedDate = firstDiagnosedDate;
}
   
}//end of ClaimantVO.java