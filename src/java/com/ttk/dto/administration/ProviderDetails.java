package com.ttk.dto.administration;

public class ProviderDetails {
	
private String providerSeqID;
private String providerName;
private String providerLicenceID;
private String providerCountryID;
private String providerCountryName;
private String providerStateOrEmirateName;
private String providerType="";
private String providerFacilityType;
private String supportedNetworks="";
private String checkStatus;
public void setCheckStatus(String checkStatus) {
	this.checkStatus = checkStatus;
}
public String getCheckStatus() {
	return checkStatus;
}
public void setSupportedNetworks(String supportedNetworks) {
	this.supportedNetworks = supportedNetworks;
}
public String getSupportedNetworks() {
	return supportedNetworks;
}
public void setProviderCountryID(String providerCountryID) {
	this.providerCountryID = providerCountryID;
}
public String getProviderCountryID() {
	return providerCountryID;
}
public void setProviderCountryName(String providerCountryName) {
	this.providerCountryName = providerCountryName;
}
public String getProviderCountryName() {
	return providerCountryName;
}

public String getProviderSeqID() {
	return providerSeqID;
}
public void setProviderSeqID(String providerSeqID) {
	this.providerSeqID = providerSeqID;
}
public String getProviderName() {
	return providerName;
}
public void setProviderName(String providerName) {
	this.providerName = providerName;
}
public String getProviderLicenceID() {
	return providerLicenceID;
}
public void setProviderLicenceID(String providerLicenceID) {
	this.providerLicenceID = providerLicenceID;
}

public String getProviderType() {
	return providerType;
}
public void setProviderType(String providerType) {
	this.providerType = providerType;
}
public String getProviderFacilityType() {
	return providerFacilityType;
}
public void setProviderFacilityType(String providerFacilityType) {
	this.providerFacilityType = providerFacilityType;
}
public String getProviderStateOrEmirateName() {
	return providerStateOrEmirateName;
}
public void setProviderStateOrEmirateName(String providerStateOrEmirateName) {
	this.providerStateOrEmirateName = providerStateOrEmirateName;
}

}
