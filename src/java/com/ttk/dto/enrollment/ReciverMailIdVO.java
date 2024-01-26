package com.ttk.dto.enrollment;
public class ReciverMailIdVO 
{
private String companyName;
private String officeCode;
private String reciverName;
private String designation;
private String primaryEmailID;
private String secondaryEmailID;
private String reciverStatus;
private String officePhone;
private Long reciverSeqID=null;
private Long addedBY;
private String regulatoryAuthority;
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getOfficeCode() {
	return officeCode;
}
public void setOfficeCode(String officeCode) {
	this.officeCode = officeCode;
}
public String getReciverName() {
	return reciverName;
}
public void setReciverName(String reciverName) {
	this.reciverName = reciverName;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public String getPrimaryEmailID() {
	return primaryEmailID;
}
public void setPrimaryEmailID(String primaryEmailID) {
	this.primaryEmailID = primaryEmailID;
}
public String getSecondaryEmailID() {
	return secondaryEmailID;
}
public void setSecondaryEmailID(String secondaryEmailID) {
	this.secondaryEmailID = secondaryEmailID;
}
public String getReciverStatus() {
	return reciverStatus;
}
public void setReciverStatus(String reciverStatus) {
	this.reciverStatus = reciverStatus;
}
public String getOfficePhone() {
	return officePhone;
}
public void setOfficePhone(String officePhone) {
	this.officePhone = officePhone;
}
public Long getAddedBY() {
	return addedBY;
}
public void setAddedBY(Long addedBY) {
	this.addedBY = addedBY;
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

}
