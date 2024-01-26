package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class PremiumConfigurationVO extends BaseVO
{
private int Sno;
private Integer minAge;
private Integer maxAge;
private String maritalStatus;
private String gender;
private BigDecimal grossPremium;
private String relation;
private String productName;
private Integer ruleSeqID;
private String maritalStatusCode;
private String genderCode;
private String relationCode;
private Long premiumConfigSeqId;
private String imageName;
private String imageTitle;
private String deleteTitle;
private String capitationYN="";
private String salaryBand = "";
private String authorityProductId ="";
private String capitationtype="";
private String auditLog="";
private String auditImageName="";
private String updatedRemarks="";
private String updtby="";
private int reverse_num;
private String remarks="";


private String asoEffectiveFromDate="";

private String vatYN="";

private double medicalPremium;
private double insurerMargin;
private double maternityPremium;
private double brokerMargin;
private double opticalPremium;
private double tapMargin;
private double dentalPremium;
private double reInsBrkMargin;
private double wellnessPremium;
private double otherMargin;
private double grossPremium1;
private double ipNetPremium;
private double netPremium;
private double actualPremium;
private double insurerMarginAED;
private double brokerMarginAED;
private double tapMarginAED;
private double reInsBrkMarginAED;
private double otherMarginAED;
private double insurerMarginPER;
private double brokerMarginPER;
private double tapMarginPER;
private double reInsBrkMarginPER;
private double otherMarginPER;
private String marginAEDPER;
//private String opNetPremium;

private double opNetPremium;

private String asoFromDate;
private String asoToDate;
private String asPlusFromDate;
private String asPlusToDate;

private String newAsoFromDate;
private String newAsoToDate;
private String newAsPlusFromDate;
private String newAsPlusToDate;



private String insurerMarginAEDPER;
private String brokerMarginAEDPER;
private String reInsBrkMarginAEDPER;
private String otherMarginAEDPER;
private String tpaFee;
private String tpaFeeType;
private String tpaFeeAEDPER;
private String tpaActualFee;

public String getRelationCode() {
	return relationCode;
}

public void setRelationCode(String relationCode) {
	this.relationCode = relationCode;
}

public String getVatYN() {
	return vatYN;
}

public void setVatYN(String vatYN) {
	this.vatYN = vatYN;
}

public double getMedicalPremium() {
	return medicalPremium;
}

public void setMedicalPremium(double medicalPremium) {
	this.medicalPremium = medicalPremium;
}

public double getInsurerMargin() {
	return insurerMargin;
}

public void setInsurerMargin(double insurerMargin) {
	this.insurerMargin = insurerMargin;
}

public double getMaternityPremium() {
	return maternityPremium;
}

public void setMaternityPremium(double maternityPremium) {
	this.maternityPremium = maternityPremium;
}

public double getBrokerMargin() {
	return brokerMargin;
}

public void setBrokerMargin(double brokerMargin) {
	this.brokerMargin = brokerMargin;
}

public double getOpticalPremium() {
	return opticalPremium;
}

public void setOpticalPremium(double opticalPremium) {
	this.opticalPremium = opticalPremium;
}

public double getTapMargin() {
	return tapMargin;
}

public void setTapMargin(double tapMargin) {
	this.tapMargin = tapMargin;
}

public double getDentalPremium() {
	return dentalPremium;
}

public void setDentalPremium(double dentalPremium) {
	this.dentalPremium = dentalPremium;
}

public double getReInsBrkMargin() {
	return reInsBrkMargin;
}

public void setReInsBrkMargin(double reInsBrkMargin) {
	this.reInsBrkMargin = reInsBrkMargin;
}

public double getWellnessPremium() {
	return wellnessPremium;
}

public void setWellnessPremium(double wellnessPremium) {
	this.wellnessPremium = wellnessPremium;
}

public double getOtherMargin() {
	return otherMargin;
}

public void setOtherMargin(double otherMargin) {
	this.otherMargin = otherMargin;
}

public double getGrossPremium1() {
	return grossPremium1;
}

public void setGrossPremium1(double grossPremium1) {
	this.grossPremium1 = grossPremium1;
}

public double getIpNetPremium() {
	return ipNetPremium;
}

public void setIpNetPremium(double ipNetPremium) {
	this.ipNetPremium = ipNetPremium;
}

public double getNetPremium() {
	return netPremium;
}

public void setNetPremium(double netPremium) {
	this.netPremium = netPremium;
}

public double getActualPremium() {
	return actualPremium;
}

public void setActualPremium(double actualPremium) {
	this.actualPremium = actualPremium;
}

public double getInsurerMarginAED() {
	return insurerMarginAED;
}

public void setInsurerMarginAED(double insurerMarginAED) {
	this.insurerMarginAED = insurerMarginAED;
}

public double getBrokerMarginAED() {
	return brokerMarginAED;
}

public void setBrokerMarginAED(double brokerMarginAED) {
	this.brokerMarginAED = brokerMarginAED;
}

public double getTapMarginAED() {
	return tapMarginAED;
}

public void setTapMarginAED(double tapMarginAED) {
	this.tapMarginAED = tapMarginAED;
}

public double getReInsBrkMarginAED() {
	return reInsBrkMarginAED;
}

public void setReInsBrkMarginAED(double reInsBrkMarginAED) {
	this.reInsBrkMarginAED = reInsBrkMarginAED;
}

public double getOtherMarginAED() {
	return otherMarginAED;
}

public void setOtherMarginAED(double otherMarginAED) {
	this.otherMarginAED = otherMarginAED;
}

public double getInsurerMarginPER() {
	return insurerMarginPER;
}

public void setInsurerMarginPER(double insurerMarginPER) {
	this.insurerMarginPER = insurerMarginPER;
}

public double getBrokerMarginPER() {
	return brokerMarginPER;
}

public void setBrokerMarginPER(double brokerMarginPER) {
	this.brokerMarginPER = brokerMarginPER;
}

public double getTapMarginPER() {
	return tapMarginPER;
}

public void setTapMarginPER(double tapMarginPER) {
	this.tapMarginPER = tapMarginPER;
}

public double getReInsBrkMarginPER() {
	return reInsBrkMarginPER;
}

public void setReInsBrkMarginPER(double reInsBrkMarginPER) {
	this.reInsBrkMarginPER = reInsBrkMarginPER;
}

public double getOtherMarginPER() {
	return otherMarginPER;
}

public void setOtherMarginPER(double otherMarginPER) {
	this.otherMarginPER = otherMarginPER;
}

public String getMarginAEDPER() {
	return marginAEDPER;
}

public void setMarginAEDPER(String marginAEDPER) {
	this.marginAEDPER = marginAEDPER;
}

public double getOpNetPremium() {
	return opNetPremium;
}

public void setOpNetPremium(double opNetPremium) {
	this.opNetPremium = opNetPremium;
}

public String getAsoFromDate() {
	return asoFromDate;
}

public void setAsoFromDate(String asoFromDate) {
	this.asoFromDate = asoFromDate;
}

public String getAsoToDate() {
	return asoToDate;
}

public void setAsoToDate(String asoToDate) {
	this.asoToDate = asoToDate;
}

public String getAsPlusFromDate() {
	return asPlusFromDate;
}

public void setAsPlusFromDate(String asPlusFromDate) {
	this.asPlusFromDate = asPlusFromDate;
}

public String getAsPlusToDate() {
	return asPlusToDate;
}

public void setAsPlusToDate(String asPlusToDate) {
	this.asPlusToDate = asPlusToDate;
}

public String getNewAsoFromDate() {
	return newAsoFromDate;
}

public void setNewAsoFromDate(String newAsoFromDate) {
	this.newAsoFromDate = newAsoFromDate;
}

public String getNewAsoToDate() {
	return newAsoToDate;
}

public void setNewAsoToDate(String newAsoToDate) {
	this.newAsoToDate = newAsoToDate;
}

public String getNewAsPlusFromDate() {
	return newAsPlusFromDate;
}

public void setNewAsPlusFromDate(String newAsPlusFromDate) {
	this.newAsPlusFromDate = newAsPlusFromDate;
}

public String getNewAsPlusToDate() {
	return newAsPlusToDate;
}

public void setNewAsPlusToDate(String newAsPlusToDate) {
	this.newAsPlusToDate = newAsPlusToDate;
}

public void setRuleSeqID(Integer ruleSeqID) {
	this.ruleSeqID = ruleSeqID;
}


public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public int getReverse_num() {
	return reverse_num;
}

public void setReverse_num(int reverse_num) {
	this.reverse_num = reverse_num;
}

public String getUpdtby() {
	return updtby;
}

public void setUpdtby(String updtby) {
	this.updtby = updtby;
}

public String getUpdatedRemarks() {
	return updatedRemarks;
}

public void setUpdatedRemarks(String updatedRemarks) {
	this.updatedRemarks = updatedRemarks;
}

public String getAuditImageName() {
	return auditImageName;
}

public void setAuditImageName(String auditImageName) {
	this.auditImageName = auditImageName;
}

public String getAuditLog() {
	return auditLog;
}

public void setAuditLog(String auditLog) {
	this.auditLog = auditLog;
}

public String getAuthorityProductId() {
	return authorityProductId;
}

public void setAuthorityProductId(String authorityProductId) {
	this.authorityProductId = authorityProductId;
}

public String getSalaryBand() {
	return salaryBand;
}

public void setSalaryBand(String salaryBand) {
	this.salaryBand = salaryBand;
}




public String getCapitationYN() {
	return capitationYN;
}
public void setCapitationYN(String capitationYN) {
	this.capitationYN = capitationYN;
}
public int getSno() {
	return Sno;
}
public void setSno(int sno) {
	Sno = sno;
}
public Integer getMinAge() {
	return minAge;
}
public void setMinAge(Integer minAge) {
	this.minAge = minAge;
}
public Integer getMaxAge() {
	return maxAge;
}
public void setMaxAge(Integer maxAge) {
	this.maxAge = maxAge;
}
public String getMaritalStatus() {
	return maritalStatus;
}
public void setMaritalStatus(String maritalStatus) {
	this.maritalStatus = maritalStatus;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public BigDecimal getGrossPremium() {
	return grossPremium;
}
public void setGrossPremium(BigDecimal grossPremium) {
	this.grossPremium = grossPremium;
}
public String getRelation() {
	return relation;
}
public void setRelation(String relation) {
	this.relation = relation;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public Integer getRuleSeqID() {
	return ruleSeqID;
}
public void setRuleSeqID(int ruleSeqID) {
	this.ruleSeqID = ruleSeqID;
}
public String getMaritalStatusCode() {
	return maritalStatusCode;
}
public void setMaritalStatusCode(String maritalStatusCode) {
	this.maritalStatusCode = maritalStatusCode;
}
public String getGenderCode() {
	return genderCode;
}
public void setGenderCode(String genderCode) {
	this.genderCode = genderCode;
}
public Long getPremiumConfigSeqId() {
	return premiumConfigSeqId;
}
public void setPremiumConfigSeqId(Long premiumConfigSeqId) {
	this.premiumConfigSeqId = premiumConfigSeqId;
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
public String getCapitationtype() {
	return capitationtype;
}
public void setCapitationtype(String capitationtype) {
	this.capitationtype = capitationtype;
}

public String getAsoEffectiveFromDate() {
	return asoEffectiveFromDate;
}

public void setAsoEffectiveFromDate(String asoEffectiveFromDate) {
	this.asoEffectiveFromDate = asoEffectiveFromDate;
}

public String getInsurerMarginAEDPER() {
	return insurerMarginAEDPER;
}

public void setInsurerMarginAEDPER(String insurerMarginAEDPER) {
	this.insurerMarginAEDPER = insurerMarginAEDPER;
}

public String getBrokerMarginAEDPER() {
	return brokerMarginAEDPER;
}

public void setBrokerMarginAEDPER(String brokerMarginAEDPER) {
	this.brokerMarginAEDPER = brokerMarginAEDPER;
}

public String getReInsBrkMarginAEDPER() {
	return reInsBrkMarginAEDPER;
}

public void setReInsBrkMarginAEDPER(String reInsBrkMarginAEDPER) {
	this.reInsBrkMarginAEDPER = reInsBrkMarginAEDPER;
}

public String getOtherMarginAEDPER() {
	return otherMarginAEDPER;
}

public void setOtherMarginAEDPER(String otherMarginAEDPER) {
	this.otherMarginAEDPER = otherMarginAEDPER;
}

public String getTpaFee() {
	return tpaFee;
}

public void setTpaFee(String tpaFee) {
	this.tpaFee = tpaFee;
}

public String getTpaFeeType() {
	return tpaFeeType;
}

public void setTpaFeeType(String tpaFeeType) {
	this.tpaFeeType = tpaFeeType;
}

public String getTpaFeeAEDPER() {
	return tpaFeeAEDPER;
}

public void setTpaFeeAEDPER(String tpaFeeAEDPER) {
	this.tpaFeeAEDPER = tpaFeeAEDPER;
}

public String getTpaActualFee() {
	return tpaActualFee;
}

public void setTpaActualFee(String tpaActualFee) {
	this.tpaActualFee = tpaActualFee;
}


}
