package com.ttk.dto.displayOfBenefits;

import com.ttk.dto.BaseVO;

public class BenefitsDetailsVO extends BaseVO {

private Long subBenefitSeqId = null;
private String benefitName;
private String subBenefit;
private Long configration = null;
private String strConfigration;
private String coverage;
private String limit = null;
private String copay = null;
private String deductible = null;
private String waitingPeriod = null;
private String sessionAllowed = null;
private String modeType;
private String otherRemarks;
private String sessionsAvailable = null;
private String limitUtilised = null;
private String limitAvailable = null;

public Long getSubBenefitSeqId() {
	return subBenefitSeqId;
}
public void setSubBenefitSeqId(Long subBenefitSeqId) {
	this.subBenefitSeqId = subBenefitSeqId;
}
public String getBenefitName() {
	return benefitName;
}
public void setBenefitName(String benefitName) {
	this.benefitName = benefitName;
}


public String getSubBenefit() {
	String x = "";
	if(subBenefit.contains("Medications, IV Fluids, Blood Transfusions, Analgesics, Surgical Implants, Chemotherapy")){
	x = subBenefit.substring(0, 23) + "<br>" + subBenefit.substring(23, 55)+"<br>"+subBenefit.substring(55, subBenefit.length());
	return x;
	}
	if(subBenefit.contains("Diagnostic ,Therapeutic Radiology, Ultrasounds, CT & MRI Scans")){
	x = subBenefit.substring(0, 34) + "<br>" + subBenefit.substring(34, subBenefit.length());
	return x;	
	}
	if(subBenefit.contains("Diagnostic & Therapeutic Radiology, Ultrasounds, CT & MRI Scans")){
	x = subBenefit.substring(0, 35) + "<br>" + subBenefit.substring(35, subBenefit.length());
	return x;	
	}
	if(subBenefit.contains("Surgery, Anaesthesiology & Surgeon's Fees")){
	x = subBenefit.substring(0, 26) + "<br>" + subBenefit.substring(26, subBenefit.length());
	return x;	
	}
	if(subBenefit.contains("Alcohol, Drug or Substance Abuse/Addiction")){
	x = subBenefit.substring(0, 17) + "<br>" + subBenefit.substring(17, subBenefit.length());
	return x;	
	}
	if(subBenefit.contains("Pollution, Nuclear & Radioactive Fuels & Waste")){
	x = subBenefit.substring(0, 18) + "<br>" + subBenefit.substring(18, subBenefit.length());
	return x;	
	}
	if(subBenefit.contains("Judo, Boxing, Wrestling, Other martial arts")){
	x = subBenefit.substring(0, 13) + "<br>" + subBenefit.substring(13, subBenefit.length());
	return x;
	}
	if(subBenefit.contains("International Emergency medical assistance")){
	x = subBenefit.substring(0, 24) + "<br>" + subBenefit.substring(24, subBenefit.length());
	return x;
	}
	else
	return subBenefit;
}

public String getSubBenefitNoBr(){
return subBenefit;
}

public void setSubBenefit(String subBenefit) {
	if(subBenefit!=null && subBenefit.contains("<br>")){
	String x = subBenefit.replaceAll("<br>", "");
	this.subBenefit = x;
	}
	else
	this.subBenefit = subBenefit;
}
public Long getConfigration() {
	return configration;
}
public void setConfigration(Long configration) {
	this.configration = configration;
}
public String getCoverage() {
	return coverage;
}
public void setCoverage(String coverage) {
	this.coverage = coverage;
}
public String getLimit() {
	return limit;
}
public void setLimit(String limit) {
	this.limit = limit;
}
public String getCopay() {
	return copay;
}
public void setCopay(String copay) {
	this.copay = copay;
}
public String getDeductible() {
	return deductible;
}
public void setDeductible(String deductible) {
	this.deductible = deductible;
}
public String getWaitingPeriod() {
	return waitingPeriod;
}
public void setWaitingPeriod(String waitingPeriod) {
	this.waitingPeriod = waitingPeriod;
}
public String getSessionAllowed() {
	return sessionAllowed;
}
public void setSessionAllowed(String sessionAllowed) {
	this.sessionAllowed = sessionAllowed;
}
public String getModeType() {
	return modeType;
}
public void setModeType(String modeType) {
	this.modeType = modeType;
}
public String getOtherRemarks() {
	return otherRemarks;
}
public void setOtherRemarks(String otherRemarks) {
	this.otherRemarks = otherRemarks;
}
public String getSessionsAvailable() {
	return sessionsAvailable;
}
public void setSessionsAvailable(String sessionsAvailable) {
	this.sessionsAvailable = sessionsAvailable;
}
public String getLimitUtilised() {
	return limitUtilised;
}
public void setLimitUtilised(String limitUtilised) {
	this.limitUtilised = limitUtilised;
}
public String getLimitAvailable() {
	return limitAvailable;
}
public void setLimitAvailable(String limitAvailable) {
	this.limitAvailable = limitAvailable;
}
public String getStrConfigration() {
	return strConfigration;
}
public void setStrConfigration(String strConfigration) {
	this.strConfigration = strConfigration;
}








}
