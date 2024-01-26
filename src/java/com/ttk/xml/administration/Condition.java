package com.ttk.xml.administration;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType(propOrder = {"geoLocCopayDetails","countries","emirates","providers"})
public class Condition {
	
	private String claimType;
	private String claimTypeDesc;
	private String networkYN;
	private String networkYNDesc;
	private String providerType;
	private String providerTypeDesc;
	private String encountertypeId;
	private String providerFacilities;
	private String benefitType;
	private String benefitTypeDesc;
	private String encounterTypes;
	
	private GeoLocationCopayDetails geoLocCopayDetails;
	private Emirates emirates;
	private Countries countries;
	private Providers providers;

	
	
	@XmlAttribute
	public String getEncountertypeId() {
		return encountertypeId;
	}
	public void setEncountertypeId(String encountertypeId) {
		this.encountertypeId = encountertypeId;
	}
	@XmlElement
	public Emirates getEmirates() {
		return emirates;
	}
	public void setEmirates(Emirates emirates) {
		this.emirates = emirates;
	}
	public void setEncounterTypes(String encounterTypes) {
		this.encounterTypes = encounterTypes;
	}
	@XmlAttribute
	public String getEncounterTypes() {
		return encounterTypes;
	}
	public void setBenefitTypeDesc(String benefitTypeDesc) {
		this.benefitTypeDesc = benefitTypeDesc;
	}
	@XmlAttribute
	public String getBenefitTypeDesc() {
		return benefitTypeDesc;
	}

public void setBenefitType(String benefitType) {
	this.benefitType = benefitType;
}
@XmlAttribute
public String getBenefitType() {
	return benefitType;
}

	public void setProviders(Providers providers) {
		this.providers = providers;
	}
	@XmlElement
	public Providers getProviders() {
		return providers;
	}
	

	
	public void setGeoLocCopayDetails(GeoLocationCopayDetails geoLocCopayDetails) {
		this.geoLocCopayDetails = geoLocCopayDetails;
	}
	@XmlElement
	public GeoLocationCopayDetails getGeoLocCopayDetails() {
		return geoLocCopayDetails;
	}
	

	
	
	public void setProviderTypeDesc(String providerTypeDesc) {
		this.providerTypeDesc = providerTypeDesc;
	}
	@XmlAttribute
	public String getProviderTypeDesc() {
		return providerTypeDesc;
	}
	@XmlAttribute
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	@XmlAttribute
	public String getClaimTypeDesc() {
		return claimTypeDesc;
	}
	public void setClaimTypeDesc(String claimTypeDesc) {
		this.claimTypeDesc = claimTypeDesc;
	}
	@XmlAttribute
	public String getNetworkYN() {
		return networkYN;
	}
	public void setNetworkYN(String networkYN) {
		this.networkYN = networkYN;
	}
	@XmlAttribute
	public String getNetworkYNDesc() {
		return networkYNDesc;
	}
	public void setNetworkYNDesc(String networkYNDesc) {
		this.networkYNDesc = networkYNDesc;
	}	
	
	@XmlElement
	public Countries getCountries() {
		return countries;
	}
	public void setCountries(Countries countries) {
		this.countries = countries;
	}
	@XmlAttribute
	public String getProviderType() {
		return providerType;
	}
	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}
	@XmlAttribute
	public String getProviderFacilities() {
		return providerFacilities;
	}
	public void setProviderFacilities(String providerFacilities) {
		this.providerFacilities = providerFacilities;
	}

	
}
