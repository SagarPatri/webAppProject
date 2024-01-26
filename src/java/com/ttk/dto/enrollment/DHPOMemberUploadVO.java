package com.ttk.dto.enrollment;

import com.ttk.dto.BaseVO;

public class DHPOMemberUploadVO extends BaseVO
{
private int sno;
private String fileName;
private String insurenceCompany;
private String insurenceCompanyCode;
private String uploadedDate;
private String regulatoryAuthority;
public int getSno() {
	return sno;
}
public void setSno(int sno) {
	this.sno = sno;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getInsurenceCompany() {
	return insurenceCompany;
}
public void setInsurenceCompany(String insurenceCompany) {
	this.insurenceCompany = insurenceCompany;
}

public String getInsurenceCompanyCode() {
	return insurenceCompanyCode;
}
public void setInsurenceCompanyCode(String insurenceCompanyCode) {
	this.insurenceCompanyCode = insurenceCompanyCode;
}
public String getUploadedDate() {
	return uploadedDate;
}
public void setUploadedDate(String uploadedDate) {
	this.uploadedDate = uploadedDate;
}
public String getRegulatoryAuthority() {
	return regulatoryAuthority;
}
public void setRegulatoryAuthority(String regulatoryAuthority) {
	this.regulatoryAuthority = regulatoryAuthority;
}
}
