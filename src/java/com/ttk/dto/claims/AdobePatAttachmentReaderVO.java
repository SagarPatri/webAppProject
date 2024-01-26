package com.ttk.dto.claims;

import java.util.Comparator;
import java.util.Date;

import javax.mail.Address;

public class AdobePatAttachmentReaderVO {
	

private String strSubject="";
private Address[] strCCList=null;
private String strPrimaryList=null;
private String strFromAddress="";
private String strToAddress="";
private String strFromContent="";
private String strFileName="";
private Date dtSentDate=null;
private Date dtRcvdDate=null;


private String strClaimType="";

private String strProcessingStatus="";
private String strInsurerRemarks="";
private String strApproval="";
private String strClaimNo="";
private String strInsuranceCompany="";
private String strPreAuthNo="";

public String getClaimType() {
	return strClaimType;
}
public void setClaimType(String claimType) {
	strClaimType = claimType;
}
public String getInsurerRemarks() {
	return strInsurerRemarks;
}
public void setInsurerRemarks(String insurerRemarks) {
	strInsurerRemarks = insurerRemarks;
}
public String getApproval() {
	return strApproval;
}
public void setApproval(String approval) {
	strApproval = approval;
}
public String getClaimNo() {
	return strClaimNo;
}
public void setClaimNo(String claimNo) {
	strClaimNo = claimNo;
}
public String getInsuranceCompany() {
	return strInsuranceCompany;
}
public void setInsuranceCompany(String insuranceCompany) {
	strInsuranceCompany = insuranceCompany;
}
/**
 * @param subject the subject to set
 */
public void setSubject(String subject) {
	strSubject = subject;
}
/**
 * @return the subject
 */
public String getSubject() {
	return strSubject;
}
/**
 * @param toAddress the toAddress to set
 */
public void setToAddress(String toAddress) {
	strToAddress = toAddress;
}
/**
 * @return the toAddress
 */
public String getToAddress() {
	return strToAddress;
}
/**
 * @param fromAddress the fromAddress to set
 */
public void setFromAddress(String fromAddress) {
	strFromAddress = fromAddress;
}
/**
 * @return the fromAddress
 */
public String getFromAddress() {
	return strFromAddress;
}


/**
 * @param fromContent the fromContent to set
 */
public void setFromContent(String fromContent) {
	strFromContent = fromContent;
}
/**
 * @return the fromContent
 */
public String getFromContent() {
	return strFromContent;
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
/**
 * @param cCList the cCList to set
 */
public void setCCList(Address[] cCList) {
	strCCList = cCList;
}
/**
 * @return the cCList
 */
public Address[] getCCList() {
	return strCCList;
}
/**
 * @param primaryList the primaryList to set
 */
public void setPrimaryList(String primaryList) {
	strPrimaryList = primaryList;
}
/**
 * @return the primaryList
 */
public String getPrimaryList() {
	return strPrimaryList;
}
/**
 * @param sentDate the sentDate to set
 */
public void setSentDate(Date sentDate) {
	dtSentDate = sentDate;
}
/**
 * @return the sentDate
 */
public Date getSentDate() {
	return dtSentDate;
}
/**
 * @param rcvdDate the rcvdDate to set
 */
public void setRcvdDate(Date rcvdDate) {
	dtRcvdDate = rcvdDate;
}
/**
 * @return the rcvdDate
 */
public Date getRcvdDate() {
	return dtRcvdDate;
}
public void setPreAuthNo(String preAuthNo) {
	strPreAuthNo = preAuthNo;
}
public String getPreAuthNo() {
	return strPreAuthNo;
}
public void setProcessingStatus(String processingStatus) {
	strProcessingStatus = processingStatus;
}
public String getProcessingStatus() {
	return strProcessingStatus;
}


}
