package com.ttk.dto.administration;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;

public class ProviderRuleVO extends BaseVO{
	
	private static final long serialVersionUID = 3456863988176537498L;
	private String encountertypeId;

	private String benefitType;
	private String benefitTypeDesc;
	private String claimType;
	private String claimTypeDesc;
	private String networkYN;
	private String networkYNDesc;
	private String geoLocation;
	private String geoLocationName;
	private String countryIDs;
	private String providerType="";
	private String providerTypeDesc;
	private String providerFacilities;
	private String providerSeqIDs;	
	private String locationType;
	private String providerName;
	private String providerLicenseNO;
	private String linkMode;
	private Long seqID;
	private String encounterTypes;
	private String emirateIDs;
	
	
	private ArrayList<String> providerSeqIDList;
	
	private String benefittypeflag;
	
	
	
	
	
	
	public String getBenefittypeflag() {
		return benefittypeflag;
	}
	public void setBenefittypeflag(String benefittypeflag) {
		this.benefittypeflag = benefittypeflag;
	}
	public String getEncountertypeId() {
		return encountertypeId;
	}
	public void setEncountertypeId(String encountertypeId) {
		this.encountertypeId = encountertypeId;
	}
	public void setEncounterTypes(String encounterTypes) {
		this.encounterTypes = encounterTypes;
	}
	public String getEncounterTypes() {
		return encounterTypes;
	}
	
	
	
	public void setProviderSeqIDList(ArrayList<String> providerSeqIDList) {
		this.providerSeqIDList = providerSeqIDList;
	}
	public ArrayList<String> getProviderSeqIDList() {
		return providerSeqIDList;
	}
	
	public void setSeqID(Long seqID) {
		this.seqID = seqID;
	}
	public Long getSeqID() {
		return seqID;
	}
	public void setLinkMode(String linkMode) {
		this.linkMode = linkMode;
	}
	public String getLinkMode() {
		return linkMode;
	}
	
	public void setCountryIDs(String countryIDs) {
		this.countryIDs = countryIDs;
	}
	public String getCountryIDs() {
		return countryIDs;
	}
	public void setProviderSeqIDs(String providerSeqIDs) {
		this.providerSeqIDs = providerSeqIDs;
	}
	public String getProviderSeqIDs() {
		return providerSeqIDs;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderLicenseNO(String providerLicenseNO) {
		this.providerLicenseNO = providerLicenseNO;
	}
	public String getProviderLicenseNO() {
		return providerLicenseNO;
	}
	public void setProviderTypeDesc(String providerTypeDesc) {
		this.providerTypeDesc = providerTypeDesc;
	}
	public String getProviderTypeDesc() {
		return providerTypeDesc;
	}
	
	public void setNetworkYNDesc(String networkYNDesc) {
		this.networkYNDesc = networkYNDesc;
	}
	public String getNetworkYNDesc() {
		return networkYNDesc;
	}
public void setClaimTypeDesc(String claimTypeDesc) {
	this.claimTypeDesc = claimTypeDesc;
}
public String getClaimTypeDesc() {
	return claimTypeDesc;
}

	public void setGeoLocationName(String geoLocationName) {
		this.geoLocationName = geoLocationName;
	}
	public String getGeoLocationName() {
		return geoLocationName;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getLocationType() {
		return locationType;
	}
	
	public void setBenefitTypeDesc(String benefitTypeDesc) {
		this.benefitTypeDesc = benefitTypeDesc;
	}
	public String getBenefitTypeDesc() {
		return benefitTypeDesc;
	}
	

	public String getBenefitType() {
		return benefitType;
	}
	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getNetworkYN() {
		return networkYN;
	}
	public void setNetworkYN(String networkYN) {
		this.networkYN = networkYN;
	}
	public String getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}
	
	public String getProviderType() {
		return providerType;
	}
	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}
	public String getProviderFacilities() {
		return providerFacilities;
	}
	public void setProviderFacilities(String providerFacilities) {
		this.providerFacilities = providerFacilities;
	}
	public String getEmirateIDs() {
		return emirateIDs;
	}
	public void setEmirateIDs(String emirateIDs) {
		this.emirateIDs = emirateIDs;
	}
		
}
