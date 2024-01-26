package com.ttk.dto.enrollment;

import com.ttk.dto.BaseVO;

public class ReciverMailList extends BaseVO
{
private Long reciverSeqID;	
private String insuranceCompany;
private String insuranceCode;
private String name;
private String designation;
private String primaryEmailId;
private String secondaryEmailId;
private String officePhone;
private String reciverStatus;
private String imageName;
private String imageTitle;
private String deleteTitle;
private String regulatoryAuthority;
private String email;

public String getInsuranceCompany() {
	return insuranceCompany;
}
public void setInsuranceCompany(String insuranceCompany) {
	this.insuranceCompany = insuranceCompany;
}
public String getInsuranceCode() {
	return insuranceCode;
}
public void setInsuranceCode(String insuranceCode) {
	this.insuranceCode = insuranceCode;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public String getPrimaryEmailId() {
	return primaryEmailId;
}
public void setPrimaryEmailId(String primaryEmailId) {
	this.primaryEmailId = primaryEmailId;
}
public String getSecondaryEmailId() {
	return secondaryEmailId;
}
public void setSecondaryEmailId(String secondaryEmailId) {
	this.secondaryEmailId = secondaryEmailId;
}
public String getOfficePhone() {
	return officePhone;
}
public void setOfficePhone(String officePhone) {
	this.officePhone = officePhone;
}
public String getReciverStatus() {
	return reciverStatus;
}
public void setReciverStatus(String reciverStatus) {
	this.reciverStatus = reciverStatus;
}
public String getImageName() {
	return imageName;
}
public void setImageName(String imageName) {
	this.imageName = imageName;
}
public String getImageTitle() {
	return imageTitle;
}
public void setImageTitle(String imageTitle) {
	this.imageTitle = imageTitle;
}
public String getDeleteTitle() {
	return deleteTitle;
}
public void setDeleteTitle(String deleteTitle) {
	this.deleteTitle = deleteTitle;
}
public Long getReciverSeqID() {
	return reciverSeqID;
}
public void setReciverSeqID(Long reciverSeqID) {
	this.reciverSeqID = reciverSeqID;
}
public String getRegulatoryAuthority() {
	return regulatoryAuthority;
}
public void setRegulatoryAuthority(String regulatoryAuthority) {
	this.regulatoryAuthority = regulatoryAuthority;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
}
