
/*
 * 
 * 1352 
 */

package com.ttk.dto.preauth;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.ttk.dto.BaseVO;

public class InsOverrideConfVO extends BaseVO {

private String strFileName="";
private	FormFile file=null;
private String  strDocumentType="";

private String policyGroupSeqID;
private String memberSeqID;
private String policySeqID;
private String relationTypeID;

private String strPolicyNbr="";
private String strEnrollmentID="";


private String strInsDecYN="";
//private Date dtOverriddenDate= null; //shortfall phase1
public String dtOverriddenDate="";
public String getOverriddenDate() {
	return dtOverriddenDate;
}
public void setOverriddenDate(String dtOverriddenDate) {
	this.dtOverriddenDate = dtOverriddenDate;
}
private String strInsReqType="";
private String  strOverrideYN="";
private String strRemarks="";
private String strMode="";
private Long lngPatClmSeqID = null;



public Long getPatClmSeqID() {
	return lngPatClmSeqID;
}
public void setPatClmSeqID(Long lngPatClmSeqID) {
	this.lngPatClmSeqID = lngPatClmSeqID;
}
public String getInsReqType() {
	return strInsReqType;
}
public void setInsReqType(String strInsReqType) {
	this.strInsReqType = strInsReqType;
}
/*public Date getOverriddenDate() {
	return dtOverriddenDate;
}
public void setOverriddenDate(Date dtOverriddenDate) {
	this.dtOverriddenDate = dtOverriddenDate;
}*/
public String getInsDecYN() {
	return strInsDecYN;
}
public void setInsDecYN(String strInsDecYN) {
	this.strInsDecYN = strInsDecYN;
}
public void setOverrideYN(String overrideYN) {
	strOverrideYN = overrideYN;
}
public String getOverrideYN() {
	return strOverrideYN;
}
public String getPolicyGroupSeqID() {
	return policyGroupSeqID;
}
public void setPolicyGroupSeqID(String policyGroupSeqID) {
	this.policyGroupSeqID = policyGroupSeqID;
}
public String getMemberSeqID() {
	return memberSeqID;
}
public void setMemberSeqID(String memberSeqID) {
	this.memberSeqID = memberSeqID;
}
public String getPolicySeqID() {
	return policySeqID;
}
public void setPolicySeqID(String policySeqID) {
	this.policySeqID = policySeqID;
}
public String getRelationTypeID() {
	return relationTypeID;
}
public void setRelationTypeID(String relationTypeID) {
	this.relationTypeID = relationTypeID;
}


/**
 * @param file the file to set
 */
public void setFile(FormFile file) {
	this.file = file;
}
/**
 * @return the file
 */
public FormFile getFile() {
	return file;
}
/**
 * @param documentType the documentType to set
 */
public void setDocumentType(String documentType) {
	strDocumentType = documentType;
}
/**
 * @return the documentType
 */
public String getDocumentType() {
	return strDocumentType;
}
/**
 * @param remarks the remarks to set
 */
public void setRemarks(String remarks) {
	strRemarks = remarks;
}
/**
 * @return the remarks
 */
public String getRemarks() {
	return strRemarks;
}
/**
 * @param policyNbr the policyNbr to set
 */
public void setPolicyNbr(String policyNbr) {
	strPolicyNbr = policyNbr;
}
/**
 * @return the policyNbr
 */
public String getPolicyNbr() {
	return strPolicyNbr;
}
/**
 * @param enrollmentID the enrollmentID to set
 */
public void setEnrollmentID(String enrollmentID) {
	strEnrollmentID = enrollmentID;
}
/**
 * @return the enrollmentID
 */
public String getEnrollmentID() {
	return strEnrollmentID;
}
/**
 * @param fileName the fileName to set
 */
public void setFileName(String fileName) {
	strFileName = fileName;
}
/**
 * @return the fileName
 */
public String getFileName() {
	return strFileName;
}
public String getMode() {
	return strMode;
}
public void setMode(String strMode) {
	this.strMode = strMode;
}


}
