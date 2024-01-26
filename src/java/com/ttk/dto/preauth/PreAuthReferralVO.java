/** 
 *  @ (#)PreAuthReferralVO.java Apr 10, 2006
 *  Project       : TTK HealthCare Services
 * 	File        : PreAuthReferralVO.java
 * Author      : Kishor Kumar S H
 * Company     : RCS
 * Date Created: 08 Dec 2016
 *
 * @author 		 : Kishor Kumar S H
 * Modified by   :
 * Modified date :
 * Reason        : 
 *   
 */
package com.ttk.dto.preauth;

import java.util.Date;

import com.ttk.dto.BaseVO;

public class PreAuthReferralVO extends BaseVO {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String providerName;
private String providerId;
private String Address;
private String contactNo;
private String emailId;

private String casereferBy;
private String contentOfLetter;
private String memberId;
private String memOrPatName;
private String patCompName;
private String otherMessages;
private Long referralSeqId;

private String referralId;
private String ReferredDate;
private String user;
private String editImageName="EditIcon";
private String editImageTitle="Edit Referral";
private String status;
private String letterYN;
private String letterGenYN;
public String getContentOfLetter() {
	return contentOfLetter;
}
public void setContentOfLetter(String contentOfLetter) {
	this.contentOfLetter = contentOfLetter;
}
public String getMemberId() {
	return memberId;
}
public void setMemberId(String memberId) {
	this.memberId = memberId;
}
public String getMemOrPatName() {
	return memOrPatName;
}
public void setMemOrPatName(String memOrPatName) {
	this.memOrPatName = memOrPatName;
}
public String getPatCompName() {
	return patCompName;
}
public void setPatCompName(String patCompName) {
	this.patCompName = patCompName;
}
public String getOtherMessages() {
	return otherMessages;
}
public void setOtherMessages(String otherMessages) {
	this.otherMessages = otherMessages;
}
private String speciality;


public String getProviderName() {
	return providerName;
}
public void setProviderName(String providerName) {
	this.providerName = providerName;
}
public String getProviderId() {
	return providerId;
}
public void setProviderId(String providerId) {
	this.providerId = providerId;
}
public String getAddress() {
	return Address;
}
public void setAddress(String address) {
	Address = address;
}
public String getContactNo() {
	return contactNo;
}
public void setContactNo(String contactNo) {
	this.contactNo = contactNo;
}
public String getEmailId() {
	return emailId;
}
public void setEmailId(String emailId) {
	this.emailId = emailId;
}
public String getCasereferBy() {
	return casereferBy;
}
public void setCasereferBy(String casereferBy) {
	this.casereferBy = casereferBy;
}
public String getSpeciality() {
	return speciality;
}
public void setSpeciality(String speciality) {
	this.speciality = speciality;
}
public Long getReferralSeqId() {
	return referralSeqId;
}
public void setReferralSeqId(Long referralSeqId) {
	this.referralSeqId = referralSeqId;
}
public String getReferralId() {
	return referralId;
}
public void setReferralId(String referralId) {
	this.referralId = referralId;
}
public String getReferredDate() {
	return ReferredDate;
}
public void setReferredDate(String referredDate) {
	ReferredDate = referredDate;
}
public String getUser() {
	return user;
}
public void setUser(String user) {
	this.user = user;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getEditImageName() {
	return editImageName;
}
public void setEditImageName(String editImageName) {
	this.editImageName = editImageName;
}
public String getEditImageTitle() {
	return editImageTitle;
}
public void setEditImageTitle(String editImageTitle) {
	this.editImageTitle = editImageTitle;
}
public String getLetterYN() {
	return letterYN;
}
public void setLetterYN(String letterYN) {
	this.letterYN = letterYN;
}
public String getLetterGenYN() {
	return letterGenYN;
}
public void setLetterGenYN(String letterGenYN) {
	this.letterGenYN = letterGenYN;
}


}//end of PreAuthReferralVO