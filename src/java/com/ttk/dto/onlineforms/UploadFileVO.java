/**
 * @ (#) 1352 november 2013
 * Project       : TTK HealthCare Services
 * File          : UploadFileVO.java
 * author        : satya
 * Reason        :File Upload console in Employee Login
 */

package com.ttk.dto.onlineforms;

import org.apache.struts.upload.FormFile;

import com.ttk.dto.BaseVO;

public class UploadFileVO extends BaseVO {

private String strFileName="";
private	FormFile file=null;
private String  strDocumentType="";
private String strRemarks="";
private String policyGroupSeqID;
private String memberSeqID;
private String policySeqID;
private String relationTypeID;

private String strPolicyNbr="";
private String strEnrollmentID="";



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


}
