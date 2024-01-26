package com.ttk.dto.enrollment;

import com.ttk.dto.BaseVO;

public class DohLogfileVO  extends BaseVO{

	private int sno;
	private String fileName;
	private String insurenceCompany;
	private String insurenceCompanyCode;
	private String uploadedDate;
	private String regulatoryAuthority;
	
	
	
	private String fieldname;
	private String valueBeforeModification;
	private String valueAfterModification;
	private String dateOfModification;
	private String custEndorsementNo;
	private String modifiedUserName;
	private String modifiedUserRole;
	private String fileCode;
	
	
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public String getValueBeforeModification() {
		return valueBeforeModification;
	}
	public void setValueBeforeModification(String valueBeforeModification) {
		this.valueBeforeModification = valueBeforeModification;
	}
	public String getValueAfterModification() {
		return valueAfterModification;
	}
	public void setValueAfterModification(String valueAfterModification) {
		this.valueAfterModification = valueAfterModification;
	}
	public String getDateOfModification() {
		return dateOfModification;
	}
	public void setDateOfModification(String dateOfModification) {
		this.dateOfModification = dateOfModification;
	}
	public String getCustEndorsementNo() {
		return custEndorsementNo;
	}
	public void setCustEndorsementNo(String custEndorsementNo) {
		this.custEndorsementNo = custEndorsementNo;
	}
	public String getModifiedUserName() {
		return modifiedUserName;
	}
	public void setModifiedUserName(String modifiedUserName) {
		this.modifiedUserName = modifiedUserName;
	}
	public String getModifiedUserRole() {
		return modifiedUserRole;
	}
	public void setModifiedUserRole(String modifiedUserRole) {
		this.modifiedUserRole = modifiedUserRole;
	}
	
	
	
	
	
	
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
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	
	
}
