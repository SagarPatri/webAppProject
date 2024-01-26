package com.ttk.xml.administration;

import javax.xml.bind.annotation.XmlAttribute;

public class EmirateCopayDetails {


	private String emirateCode;
	private String emirateName;
	
	private String copay="";
	private String flatamount="";
	private String currency;
	private String minmax;
	private String perPolicyLimit="";
	private String perClaimLimit="";
	private String perDayLimit="";
	private String coveragePeriod="";
	private String coveragePeriodType="";
    private String applyOn="";
	
	@XmlAttribute
	public String getApplyOn() {
		return applyOn;
	}
	public void setApplyOn(String applyOn) {
		this.applyOn = applyOn;
	}
	
	
	@XmlAttribute
	public String getPerPolicyLimit() {
		return perPolicyLimit;
	}
	public void setPerPolicyLimit(String perPolicyLimit) {
		this.perPolicyLimit = perPolicyLimit;
	}
	@XmlAttribute
	public String getPerClaimLimit() {
		return perClaimLimit;
	}
	public void setPerClaimLimit(String perClaimLimit) {
		this.perClaimLimit = perClaimLimit;
	}
	@XmlAttribute
	public String getPerDayLimit() {
		return perDayLimit;
	}
	public void setPerDayLimit(String perDayLimit) {
		this.perDayLimit = perDayLimit;
	}
	@XmlAttribute
	public String getCoveragePeriod() {
		return coveragePeriod;
	}
	public void setCoveragePeriod(String coveragePeriod) {
		this.coveragePeriod = coveragePeriod;
	}
	@XmlAttribute
	public String getCoveragePeriodType() {
		return coveragePeriodType;
	}
	public void setCoveragePeriodType(String coveragePeriodType) {
		this.coveragePeriodType = coveragePeriodType;
	}
	
	
	public void setEmirateCode(String emirateCode) {
		this.emirateCode = emirateCode;
	}
	public void setEmirateName(String emirateName) {
		this.emirateName = emirateName;
	}
	@XmlAttribute
	public String getEmirateCode() {
		return emirateCode;
	}
	@XmlAttribute
	public String getEmirateName() {
		return emirateName;
	}
	@XmlAttribute
	public String getCopay() {
		return copay;
	}
	public void setCopay(String copay) {
		this.copay = copay;
	}
	@XmlAttribute
	public String getFlatamount() {
		return flatamount;
	}
	public void setFlatamount(String flatamount) {
		this.flatamount = flatamount;
	}
	@XmlAttribute
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@XmlAttribute
	public String getMinmax() {
		return minmax;
	}
	public void setMinmax(String minmax) {
		this.minmax = minmax;
	}



}
