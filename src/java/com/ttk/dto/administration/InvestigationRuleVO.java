package com.ttk.dto.administration;

import org.dom4j.Document;

import com.ttk.dto.BaseVO;

public class InvestigationRuleVO extends BaseVO{
	
	private static final long serialVersionUID = 3456863988176537498L;
	
	private String benefitType;
	private String benefitTypeDesc;
	private String actMasterCode;
	
	private String preApprvReqYN;
	private String preApprvLimit;
	private String providerTypes;
	private String geoLocationID;
	private String geoLocationName;
	private String countryIDs;	
	private String emiratesIDs;
	private String encounterTypes;
	private String perPolicyLimit;
	private String perClaimLimit;
	private String ovrCopay;
	private String ovrDeductible;
	private String ovrApplOn;
	private String ovrMinMaxFlag;
	private String clncConsYN;
	private String followUpPeriod;
	private String followUpPeriodType;
	private String perPolicyCpyDdctLimit;
	private String perClaimCpyDdctLimit;
	private String clinicianCopayDetails;
	private String proPolRuleSeqID;
	private String investgationSeqID;
	private String benefitID;
	private String invsType;
	
	private Document staticDoc;
	private String investTypeName;
	private String investTypeLabel;
	
	private String noOfSessAllowPerPolicy;
	private String limitPerSession;
	private String copayDedPerSessionLimit;
	private String noOfdaysAllowPerCalim;
	private String maxLimitAllowPerDay;
	private String ageRangeFrom;
	private String ageRangeTo;
	private String hospType;
	
	public void setHospType(String hospType) {
		this.hospType = hospType;
	}
	public String getHospType() {
		return hospType;
	}
	public String getAgeRangeFrom() {
		return ageRangeFrom;
	}
	public void setAgeRangeFrom(String ageRangeFrom) {
		this.ageRangeFrom = ageRangeFrom;
	}
	public String getAgeRangeTo() {
		return ageRangeTo;
	}
	public void setAgeRangeTo(String ageRangeTo) {
		this.ageRangeTo = ageRangeTo;
	}
	public void setMaxLimitAllowPerDay(String maxLimitAllowPerDay) {
		this.maxLimitAllowPerDay = maxLimitAllowPerDay;
	}
	public String getMaxLimitAllowPerDay() {
		return maxLimitAllowPerDay;
	}
	public void setNoOfdaysAllowPerCalim(String noOfdaysAllowPerCalim) {
		this.noOfdaysAllowPerCalim = noOfdaysAllowPerCalim;
	}
	public String getNoOfdaysAllowPerCalim() {
		return noOfdaysAllowPerCalim;
	}
	
	// vaccination
	private String claimType;
	private String networkYN;
	private String providerTypesID;
	private String minAge;
	private String maxAge;
	private String allowedAgeType;
	// till vaccination
	// alternate treatments
	private String altTmtTypes;
	
	//above properties added by govind while developing Special Benefits
	
	public String getAllowedAgeType() {
		return allowedAgeType;
	}
	public String getAltTmtTypes() {
		return altTmtTypes;
	}
	public void setAltTmtTypes(String altTmtTypes) {
		this.altTmtTypes = altTmtTypes;
	}
	public void setAllowedAgeType(String allowedAgeType) {
		this.allowedAgeType = allowedAgeType;
	}
	
	public String getNetworkYN() {
		return networkYN;
	}
	public void setNetworkYN(String networkYN) {
		this.networkYN = networkYN;
	}
	public String getProviderTypesID() {
		return providerTypesID;
	}
	public void setProviderTypesID(String providerTypesID) {
		this.providerTypesID = providerTypesID;
	}
	public String getMinAge() {
		return minAge;
	}
	public void setMinAge(String minAge) {
		this.minAge = minAge;
	}
	public String getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	
	
	// above added by govind
	
	public void setNoOfSessAllowPerPolicy(String noOfSessAllowPerPolicy) {
		this.noOfSessAllowPerPolicy = noOfSessAllowPerPolicy;
	}
	public String getNoOfSessAllowPerPolicy() {
		return noOfSessAllowPerPolicy;
	}
	public void setLimitPerSession(String limitPerSession) {
		this.limitPerSession = limitPerSession;
	}
	public String getLimitPerSession() {
		return limitPerSession;
	}
	public void setCopayDedPerSessionLimit(String copayDedPerSessionLimit) {
		this.copayDedPerSessionLimit = copayDedPerSessionLimit;
	}
	public String getCopayDedPerSessionLimit() {
		return copayDedPerSessionLimit;
	}
	public void setInvestTypeLabel(String investTypeLabel) {
		this.investTypeLabel = investTypeLabel;
	}
	public String getInvestTypeLabel() {
		return investTypeLabel;
	}
	public void setInvestTypeName(String investTypeName) {
		this.investTypeName = investTypeName;
	}
	public String getInvestTypeName() {
		return investTypeName;
	}
	
	public void setStaticDoc(Document staticDoc) {
		this.staticDoc = staticDoc;
	}
	public Document getStaticDoc() {
		return staticDoc;
	}
	
	//private ArrayList<String[]> clinicianCopayDetails;
	
	public void setInvsType(String invsType) {
		this.invsType = invsType;
	}
	public String getInvsType() {
		return invsType;
	}
	
	public void setActMasterCode(String actMasterCode) {
		this.actMasterCode = actMasterCode;
	}
	public String getActMasterCode() {
		return actMasterCode;
	}
	
	public void setBenefitID(String benefitID) {
		this.benefitID = benefitID;
	}
	public String getBenefitID() {
		return benefitID;
	}
	
	public void setInvestgationSeqID(String investgationSeqID) {
		this.investgationSeqID = investgationSeqID;
	}
	public String getInvestgationSeqID() {
		return investgationSeqID;
	}
	public void setProPolRuleSeqID(String proPolRuleSeqID) {
		this.proPolRuleSeqID = proPolRuleSeqID;
	}
	public String getProPolRuleSeqID() {
		return proPolRuleSeqID;
	}
public void setClinicianCopayDetails(String clinicianCopayDetails) {
	this.clinicianCopayDetails = clinicianCopayDetails;
}
public String getClinicianCopayDetails() {
	return clinicianCopayDetails;
}
	public String getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}

	public String getBenefitTypeDesc() {
		return benefitTypeDesc;
	}

	public void setBenefitTypeDesc(String benefitTypeDesc) {
		this.benefitTypeDesc = benefitTypeDesc;
	}

	public String getPreApprvReqYN() {
		return preApprvReqYN;
	}

	public void setPreApprvReqYN(String preApprvReqYN) {
		this.preApprvReqYN = preApprvReqYN;
	}

	public String getPreApprvLimit() {
		return preApprvLimit;
	}

	public void setPreApprvLimit(String preApprvLimit) {
		this.preApprvLimit = preApprvLimit;
	}

	public String getProviderTypes() {
		return providerTypes;
	}

	public void setProviderTypes(String providerTypes) {
		this.providerTypes = providerTypes;
	}

	public String getGeoLocationID() {
		return geoLocationID;
	}

	public void setGeoLocationID(String geoLocationID) {
		this.geoLocationID = geoLocationID;
	}

	public String getGeoLocationName() {
		return geoLocationName;
	}

	public void setGeoLocationName(String geoLocationName) {
		this.geoLocationName = geoLocationName;
	}

	public String getCountryIDs() {
		return countryIDs;
	}

	public void setCountryIDs(String countryIDs) {
		this.countryIDs = countryIDs;
	}

	public String getEmiratesIDs() {
		return emiratesIDs;
	}

	public void setEmiratesIDs(String emiratesIDs) {
		this.emiratesIDs = emiratesIDs;
	}

	public String getEncounterTypes() {
		return encounterTypes;
	}

	public void setEncounterTypes(String encounterTypes) {
		this.encounterTypes = encounterTypes;
	}

	public String getPerPolicyLimit() {
		return perPolicyLimit;
	}

	public void setPerPolicyLimit(String perPolicyLimit) {
		this.perPolicyLimit = perPolicyLimit;
	}

	public String getPerClaimLimit() {
		return perClaimLimit;
	}

	public void setPerClaimLimit(String perClaimLimit) {
		this.perClaimLimit = perClaimLimit;
	}

	public String getOvrCopay() {
		return ovrCopay;
	}

	public void setOvrCopay(String ovrCopay) {
		this.ovrCopay = ovrCopay;
	}

	public String getOvrDeductible() {
		return ovrDeductible;
	}

	public void setOvrDeductible(String ovrDeductible) {
		this.ovrDeductible = ovrDeductible;
	}

	public String getOvrApplOn() {
		return ovrApplOn;
	}

	public void setOvrApplOn(String ovrApplOn) {
		this.ovrApplOn = ovrApplOn;
	}

	public String getOvrMinMaxFlag() {
		return ovrMinMaxFlag;
	}

	public void setOvrMinMaxFlag(String ovrMinMaxFlag) {
		this.ovrMinMaxFlag = ovrMinMaxFlag;
	}

	public String getClncConsYN() {
		return clncConsYN;
	}

	public void setClncConsYN(String clncConsYN) {
		this.clncConsYN = clncConsYN;
	}

	public String getFollowUpPeriod() {
		return followUpPeriod;
	}

	public void setFollowUpPeriod(String followUpPeriod) {
		this.followUpPeriod = followUpPeriod;
	}

	public String getFollowUpPeriodType() {
		return followUpPeriodType;
	}

	public void setFollowUpPeriodType(String followUpPeriodType) {
		this.followUpPeriodType = followUpPeriodType;
	}

	public String getPerPolicyCpyDdctLimit() {
		return perPolicyCpyDdctLimit;
	}

	public void setPerPolicyCpyDdctLimit(String perPolicyCpyDdctLimit) {
		this.perPolicyCpyDdctLimit = perPolicyCpyDdctLimit;
	}

	public String getPerClaimCpyDdctLimit() {
		return perClaimCpyDdctLimit;
	}

	public void setPerClaimCpyDdctLimit(String perClaimCpyDdctLimit) {
		this.perClaimCpyDdctLimit = perClaimCpyDdctLimit;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
