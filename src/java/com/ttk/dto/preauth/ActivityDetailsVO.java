
/**
 * @ (#) ClaimBatchManager.java July 7, 2015
 * Project 	     : ProjectX
 * File          : ClaimBatchManager.java
 * Author        : Nagababu K
 * Company       : RCS Technologies
 * Date Created  : July 7, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.ttk.dto.BaseVO;




public class ActivityDetailsVO extends BaseVO{

private static final long serialVersionUID = 1L;


private BigDecimal allowedAmt;
private String obsYN;



private BigDecimal baseRate;
private String defaultAED;
private BigDecimal negotiationFactor;
private String irdrgRelativeWt;
private String irdrgPymntProcess;
private BigDecimal outlierPmntThresholdLmt;
private BigDecimal margin;
private String defaultPercentage;
private BigDecimal avgActivityCost;
private String irdrgProcessYN;
private String irdrgInlierFlagYN;
private BigDecimal preauthRequestAmt;

private ArrayList<ObservationDetailsVO> observList=null;

private String claimtype;
private Integer  serialNo;
private String activityCodeDesc="";
private String activityCode="";
private BigDecimal copay;
private String modifier="";
private String searchType="";
private BigDecimal coinsurance;
private String unitType="";
private BigDecimal deductible;
private Float quantity;
private Float approvedQuantity;
private BigDecimal outOfPocket;
private String startDate="";
private BigDecimal patientShare;
private BigDecimal grossAmount;
private BigDecimal netAmount;
private BigDecimal discount;
private BigDecimal discountPerUnit;
private BigDecimal approvedAmount;
private BigDecimal allowedAmount;
private BigDecimal discountedGross;
private BigDecimal unitPrice;
private BigDecimal disAllowedAmount;
private BigDecimal providerRequestedAmt;

private BigDecimal rdisAllowedAmount;//r for rules
private BigDecimal routOfPocket;
private BigDecimal rdeductible;
private BigDecimal rcoinsurance;
private BigDecimal rcopay;
private String denialRemarks="";
private String activityRemarks="";
private String bundleId="";
private String internalCode="";
private String denialCode="";
private String denialDescription="";
private String packageId="";
private Long activityCodeSeqId;
private String activityTypeId;
private String preAuthNo;
private String claimNo;
private Long preAuthSeqID;
private Long claimSeqID;
private Date activityStartDate;
private String clinicianId;
private Long activityDtlSeqId;
private Long activitySeqId;
private String amountAllowed="";
private String activityTariffStatus="";
private String tariffYN="";
private String displayMsg="";
private String remarks="";
private String authType="";
private String overrideYN="";
private String networkProviderType;
private String clinicianName;
private String overrideRemarks;
private String activityStatus;
private Integer medicationDays;
private String posology;
private BigDecimal ricopar;
private BigDecimal ucr;
  

private String activityServiceType;
private String activityServiceCode;
private String activityServiceCodeDesc;
private String benefitType;              
private BigDecimal nonNetworkCopay;
private String unitPriceCurrencyType;

private BigDecimal additionalDisallowances;
//private BigDecimal convertedUnitPrice;
private String convertedUnitPrice;
private String convertedUnitPricecurrencyType;
private BigDecimal conversionRate;
private String haad_YN="";
private String edit_YN="";

private String editStatus;
private String activityId;
private String preauthReciveTypeID;
private BigDecimal providerAreCopay;
private String overrideAllowYN; 
private String sCategory;
private String ActivityTypeCode;
private String activityTypeCodeDesc;
//Dental Benefit
private String toothNo;
private String internalDesc="";
private String toothNoReqYN;
private String newPharmacyYN;
private Long tariffSeqId;
private Integer quantityInt;
private String dateOfApproval;
private Integer duration;
private String erxRef;
private String erxInstruction;

private String hosp_validity_flag;
private String currencyType;
private BigDecimal convertedProviderReqAmt;
private BigDecimal appliedCopay;
private String mohPatientShareErrMsg;

private String globalFlagYN;
private String generalFlagYN;
private String clinicalFlagYN;
private String processorInternalRemarks;
private String observationFlag;
private String condDenialCode;
private String payerAuthority;



private Double vatPercent;
private Double vatAed;
private Double vatAddedReqAmnt;
private Double vatAddedApprAmnt;


private Double appliedVatPercent;
private Double appliedVatAed;

private String appliedVatAEDPER; 
private String vatFlag; 
private String allowedAmtVatAdded; 
private BigDecimal providerRequestedAmtVatAdded;
private BigDecimal ActivityunitPrice;
private BigDecimal ActivityDiscount;
private BigDecimal availableSuminsured;
private BigDecimal utilizeSuminsured;
private String VBCDescription;

public BigDecimal getUtilizeSuminsured() {
	return utilizeSuminsured;
}

public void setUtilizeSuminsured(BigDecimal utilizeSuminsured) {
	this.utilizeSuminsured = utilizeSuminsured;
}

public BigDecimal getAvailableSuminsured() {
	return availableSuminsured;
}

public void setAvailableSuminsured(BigDecimal availableSuminsured) {
	this.availableSuminsured = availableSuminsured;
}

public void setObservList(ArrayList<ObservationDetailsVO> observList) {
	this.observList = observList;
}

public ArrayList<ObservationDetailsVO> getObservList() {
	return observList;
}

public void setMohPatientShareErrMsg(String mohPatientShareErrMsg) {
	this.mohPatientShareErrMsg = mohPatientShareErrMsg;
}
public String getMohPatientShareErrMsg() {
	return mohPatientShareErrMsg;
}
private String irdrgWarnMsg;


public void setIrdrgWarnMsg(String irdrgWarnMsg) {
	this.irdrgWarnMsg = irdrgWarnMsg;
}
public String getIrdrgWarnMsg() {
	return irdrgWarnMsg;
}

public String getErxInstruction() {
	return erxInstruction;
}
public void setErxInstruction(String erxInstruction) {
	this.erxInstruction = erxInstruction;
}
public String getErxRef() {
	return erxRef;
}
public void setErxRef(String erxRef) {
	this.erxRef = erxRef;
}


public void setDuration(Integer duration) {
	this.duration = duration;
}
public Integer getDuration() {
	return duration;
}


public String getDateOfApproval() {
	return dateOfApproval;
}
public void setDateOfApproval(String dateOfApproval) {
	this.dateOfApproval = dateOfApproval;
}


public Integer getQuantityInt() {
	return quantityInt;
}
public void setQuantityInt(Integer quantityInt) {
	this.quantityInt = quantityInt;
}


public Long getTariffSeqId() {
	return tariffSeqId;
}
public void setTariffSeqId(Long tariffSeqId) {
	this.tariffSeqId = tariffSeqId;
}


public String getNewPharmacyYN() {
	return newPharmacyYN;
}
public void setNewPharmacyYN(String newPharmacyYN) {
	this.newPharmacyYN = newPharmacyYN;
}

public void setOverrideAllowYN(String overrideAllowYN) {
	this.overrideAllowYN = overrideAllowYN;
}
public String getOverrideAllowYN() {
	return overrideAllowYN;
}
public void setProviderAreCopay(BigDecimal providerAreCopay) {
	this.providerAreCopay = providerAreCopay;
}
public BigDecimal getProviderAreCopay() {
	return providerAreCopay;
}
public BigDecimal getAdditionalDisallowances() {
	return additionalDisallowances;
}
public void setAdditionalDisallowances(BigDecimal additionalDisallowances) {
	this.additionalDisallowances = additionalDisallowances;
}


//private String totalAmountCurrency;
public ActivityDetailsVO() {
	// TODO Auto-generated constructor stub
}
public ActivityDetailsVO(Integer serialNo,String activityCode,String activityTypeCodeDesc,String activityCodeDesc,String modifier,String unitType,String startDate,String activityRemarks,String denialCode,String denialDescription,
		Float quantity,Float approvedQuantity,Long activitySeqId,Long activityDtlSeqId,
		                 BigDecimal grossAmount,BigDecimal discount,BigDecimal discountedGross,BigDecimal patientShare,BigDecimal netAmount,BigDecimal approvedAmount,BigDecimal disAllowedAmt,String preAuthNo) {
	this.serialNo=serialNo;
	this.activityCode=activityCode;
	this.activityTypeCodeDesc=activityTypeCodeDesc;
	this.modifier=modifier;
	this.unitType=unitType;
	this.quantity=quantity;
	this.startDate=startDate;
	this.grossAmount=grossAmount;
	this.discountedGross=discountedGross;
	this.patientShare=patientShare;
	this.netAmount=netAmount;
	this.approvedAmount=approvedAmount;
	this.disAllowedAmount=disAllowedAmt;
	this.denialDescription=denialDescription;
	this.activityRemarks=activityRemarks;
	this.activitySeqId=activitySeqId;
	this.activityDtlSeqId=activityDtlSeqId;
	this.activityCodeDesc=activityCodeDesc;
	this.denialCode=denialCode;
	this.discount=discount;
	this.approvedQuantity=approvedQuantity;
	this.preAuthNo	=	preAuthNo;
	
}

public ActivityDetailsVO(String activityCode,String modifiers,String unityType,Float quantity,Float approvedQuantity,String startDate,BigDecimal grossAmt,BigDecimal discount,BigDecimal discountGross,
		BigDecimal patientShare,BigDecimal netAmt,BigDecimal allowedAmt,BigDecimal approAmt,String denialCodes,String activityRemarks ,Long activityDtlSeqId,String actDesc, String denialDescription) {

	this.activityCode=activityCode;
	this.modifier=modifiers;
	this.unitType=unityType;
	this.quantity=quantity;
	this.approvedQuantity=approvedQuantity;
	this.startDate=startDate;
	this.grossAmount=grossAmt;
	this.discount=discount;
	this.discountedGross=discountGross;
	this.patientShare=patientShare;
	this.netAmount=netAmt;
	this.allowedAmt=allowedAmt;
	this.approvedAmount=approAmt;
	this.denialCode=denialCodes;
	this.activityRemarks=activityRemarks;
	this.activityDtlSeqId=activityDtlSeqId;
	this.activityCodeDesc=actDesc;
	this.denialDescription=denialDescription;
}
public String getToothNoReqYN() {
	return toothNoReqYN;
}
public void setToothNoReqYN(String toothNoReqYN) {
	this.toothNoReqYN = toothNoReqYN;
}
public String getInternalDesc() {
	return internalDesc;
}
public void setInternalDesc(String internalDesc) {
	this.internalDesc = internalDesc;
}


public String getToothNo() {
	return toothNo;
}
public void setToothNo(String toothNo) {
	this.toothNo = toothNo;
}

public void setBenefitType(String benefitType) {
	this.benefitType = benefitType;
}
public String getBenefitType() {
	return benefitType;
}
public void setActivityServiceCode(String activityServiceCode) {
	this.activityServiceCode = activityServiceCode;
}
public String getActivityServiceCode() {
	return activityServiceCode;
}
public void setActivityServiceType(String activityServiceType) {
	this.activityServiceType = activityServiceType;
}
public String getActivityServiceType() {
	return activityServiceType;
}
public Integer getSerialNo() {
	return serialNo;
}
public void setSerialNo(Integer serialNo) {
	this.serialNo = serialNo;
}
public String getActivityCode() {
	return activityCode;
}
public void setActivityCode(String activityCode) {
	this.activityCode = activityCode;
}
public BigDecimal getCopay() {
	return copay;
}
public void setCopay(BigDecimal copay) {
	this.copay = copay;
}
public String getModifier() {
	return modifier;
}
public void setModifier(String modifier) {
	this.modifier = modifier;
}
public BigDecimal getCoinsurance() {
	return coinsurance;
}
public void setCoinsurance(BigDecimal coinsurance) {
	this.coinsurance = coinsurance;
}
public String getUnitType() {
	return unitType;
}
public void setUnitType(String unitType) {
	this.unitType = unitType;
}
public BigDecimal getDeductible() {
	return deductible;
}
public void setDeductible(BigDecimal deductible) {
	this.deductible = deductible;
}

public BigDecimal getOutOfPocket() {
	return outOfPocket;
}
public void setOutOfPocket(BigDecimal outOfPocket) {
	this.outOfPocket = outOfPocket;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public BigDecimal getPatientShare() {
	return patientShare;
}
public void setPatientShare(BigDecimal patientShare) {
	this.patientShare = patientShare;
}
public BigDecimal getGrossAmount() {
	return grossAmount;
}
public void setGrossAmount(BigDecimal grossAmount) {
	this.grossAmount = grossAmount;
}
public BigDecimal getNetAmount() {
	return netAmount;
}
public void setNetAmount(BigDecimal netAmount) {
	this.netAmount = netAmount;
}
public BigDecimal getDiscount() {
	return discount;
}
public void setDiscount(BigDecimal discount) {
	this.discount = discount;
}
public BigDecimal getApprovedAmount() {
	return approvedAmount;
}
public void setApprovedAmount(BigDecimal approvedAmount) {
	this.approvedAmount = approvedAmount;
}
public BigDecimal getDiscountedGross() {
	return discountedGross;
}
public void setDiscountedGross(BigDecimal discountedGross) {
	this.discountedGross = discountedGross;
}

public String getBundleId() {
	return bundleId;
}
public void setBundleId(String bundleId) {
	this.bundleId = bundleId;
}
public String getInternalCode() {
	return internalCode;
}
public void setInternalCode(String internalCode) {
	this.internalCode = internalCode;
}

public String getPackageId() {
	return packageId;
}
public void setPackageId(String packageId) {
	this.packageId = packageId;
}
public Long getActivityCodeSeqId() {
	return activityCodeSeqId;
}
public void setActivityCodeSeqId(Long activityCodeSeqId) {
	this.activityCodeSeqId = activityCodeSeqId;
}
public String getActivityTypeId() {
	return activityTypeId;
}
public void setActivityTypeId(String activityTypeId) {
	this.activityTypeId = activityTypeId;
}
public String getPreAuthNo() {
	return preAuthNo;
}
public void setPreAuthNo(String preAuthNo) {
	this.preAuthNo = preAuthNo;
}
public Long getPreAuthSeqID() {
	return preAuthSeqID;
}
public void setPreAuthSeqID(Long preAuthSeqID) {
	this.preAuthSeqID = preAuthSeqID;
}
public Date getActivityStartDate() {
	return activityStartDate;
}
public void setActivityStartDate(Date activityStartDate) {
	this.activityStartDate = activityStartDate;
}
public String getClinicianId() {
	return clinicianId;
}
public void setClinicianId(String clinicianId) {
	this.clinicianId = clinicianId;
}
public String getActivityRemarks() {
	return activityRemarks;
}
public void setActivityRemarks(String activityRemarks) {
	this.activityRemarks = activityRemarks;
}
public String getActivityCodeDesc() {
	return activityCodeDesc;
}
public void setActivityCodeDesc(String activityCodeDesc) {
	this.activityCodeDesc = activityCodeDesc;
}
public Long getActivityDtlSeqId() {
	return activityDtlSeqId;
}
public void setActivityDtlSeqId(Long activityDtlSeqId) {
	this.activityDtlSeqId = activityDtlSeqId;
}
public Long getActivitySeqId() {
	return activitySeqId;
}
public void setActivitySeqId(Long activitySeqId) {
	this.activitySeqId = activitySeqId;
}
public String getDenialDescription() {
	return denialDescription;
}
public void setDenialDescription(String denialDescription) {
	this.denialDescription = denialDescription;
}
public BigDecimal getAllowedAmount() {
	return allowedAmount;
}
public void setAllowedAmount(BigDecimal allowedAmount) {
	this.allowedAmount = allowedAmount;
}
public String getAmountAllowed() {
	return amountAllowed;
}
public void setAmountAllowed(String amountAllowed) {
	this.amountAllowed = amountAllowed;
}

public String getSearchType() {
	return searchType;
}
public void setSearchType(String searchType) {
	this.searchType = searchType;
}
public String getActivityTariffStatus() {
	return activityTariffStatus;
}
public void setActivityTariffStatus(String activityTariffStatus) {
	this.activityTariffStatus = activityTariffStatus;
}
public BigDecimal getUnitPrice() {
	return unitPrice;
}
public void setUnitPrice(BigDecimal unitPrice) {
	this.unitPrice = unitPrice;
}
public String getTariffYN() {
	return tariffYN;
}
public void setTariffYN(String tariffYN) {
	this.tariffYN = tariffYN;
}
public String getDisplayMsg() {
	return displayMsg;
}
public void setDisplayMsg(String displayMsg) {
	this.displayMsg = displayMsg;
}
public Float getQuantity() {
	return quantity;
}
public void setQuantity(Float quantity) {
	this.quantity = quantity;
}
public Float getApprovedQuantity() {
	return approvedQuantity;
}
public void setApprovedQuantity(Float approvedQuantity) {
	this.approvedQuantity = approvedQuantity;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public String getDenialCode() {
	return denialCode;
}
public void setDenialCode(String denialCode) {
	this.denialCode = denialCode;
}
public Long getClaimSeqID() {
	return claimSeqID;
}
public void setClaimSeqID(Long claimSeqID) {
	this.claimSeqID = claimSeqID;
}
public String getClaimNo() {
	return claimNo;
}
public void setClaimNo(String claimNo) {
	this.claimNo = claimNo;
}
public String getAuthType() {
	return authType;
}
public void setAuthType(String authType) {
	this.authType = authType;
}
public BigDecimal getRdisAllowedAmount() {
	return rdisAllowedAmount;
}
public void setRdisAllowedAmount(BigDecimal rdisAllowedAmount) {
	this.rdisAllowedAmount = rdisAllowedAmount;
}
public BigDecimal getRdeductible() {
	return rdeductible;
}
public void setRdeductible(BigDecimal rdeductible) {
	this.rdeductible = rdeductible;
}
public BigDecimal getRoutOfPocket() {
	return routOfPocket;
}
public void setRoutOfPocket(BigDecimal routOfPocket) {
	this.routOfPocket = routOfPocket;
}
public BigDecimal getRcoinsurance() {
	return rcoinsurance;
}
public void setRcoinsurance(BigDecimal rcoinsurance) {
	this.rcoinsurance = rcoinsurance;
}
public BigDecimal getRcopay() {
	return rcopay;
}
public void setRcopay(BigDecimal rcopay) {
	this.rcopay = rcopay;
}
public BigDecimal getDisAllowedAmount() {
	return disAllowedAmount;
}
public void setDisAllowedAmount(BigDecimal disAllowedAmount) {
	this.disAllowedAmount = disAllowedAmount;
}
public BigDecimal getDiscountPerUnit() {
	return discountPerUnit;
}
public void setDiscountPerUnit(BigDecimal discountPerUnit) {
	this.discountPerUnit = discountPerUnit;
}
public String getOverrideYN() {
	return overrideYN;
}
public void setOverrideYN(String overrideYN) {
	this.overrideYN = overrideYN;
}
public String getNetworkProviderType() {
	return networkProviderType;
}
public void setNetworkProviderType(String networkProviderType) {
	this.networkProviderType = networkProviderType;
}
public String getClinicianName() {
	return clinicianName;
}
public void setClinicianName(String clinicianName) {
	this.clinicianName = clinicianName;
}
public String getOverrideRemarks() {
	return overrideRemarks;
}
public void setOverrideRemarks(String overrideRemarks) {
	this.overrideRemarks = overrideRemarks;
}
public String getActivityStatus() {
	return activityStatus;
}
public void setActivityStatus(String activityStatus) {
	this.activityStatus = activityStatus;
}
public Integer getMedicationDays() {
	return medicationDays;
}
public void setMedicationDays(Integer medicationDays) {
	this.medicationDays = medicationDays;
}
public String getPosology() {
	return posology;
}
public void setPosology(String posology) {
	this.posology = posology;
}
public BigDecimal getProviderRequestedAmt() {
	return providerRequestedAmt;
}
public void setProviderRequestedAmt(BigDecimal providerRequestedAmt) {
	this.providerRequestedAmt = providerRequestedAmt;
}
public String getActivityServiceCodeDesc() {
	return activityServiceCodeDesc;
}
public void setActivityServiceCodeDesc(String activityServiceCodeDesc) {
	this.activityServiceCodeDesc = activityServiceCodeDesc;
}

public BigDecimal getRicopar() {
	return ricopar;
}
public void setRicopar(BigDecimal ricopar) {
	this.ricopar = ricopar;
}
public BigDecimal getUcr() {
	return ucr;
}
public void setUcr(BigDecimal ucr) {
	this.ucr = ucr;
}
public String getClaimtype() {
	return claimtype;
}
public void setClaimtype(String claimtype) {
	this.claimtype = claimtype;
}
public String getDenialRemarks() {
	return denialRemarks;
}
public void setDenialRemarks(String denialRemarks) {
	this.denialRemarks = denialRemarks;
}
public BigDecimal getNonNetworkCopay() {
	return nonNetworkCopay;
}
public void setNonNetworkCopay(BigDecimal nonNetworkCopay) {
	this.nonNetworkCopay = nonNetworkCopay;
}
public String getHaad_YN() {
	return haad_YN;
}
public void setHaad_YN(String haad_YN) {
	this.haad_YN = haad_YN;
}
public String getEdit_YN() {
	return edit_YN;
}
public void setEdit_YN(String edit_YN) {
	this.edit_YN = edit_YN;
}
public String getEditStatus() {
	return editStatus;
}
public void setEditStatus(String editStatus) {
	this.editStatus = editStatus;
}

/*public BigDecimal getConvertedUnitPrice() {
	return convertedUnitPrice;
}
public void setConvertedUnitPrice(BigDecimal convertedUnitPrice) {
	this.convertedUnitPrice = convertedUnitPrice;
}*/

public BigDecimal getConversionRate() {
	return conversionRate;
}
public void setConversionRate(BigDecimal conversionRate) {
	this.conversionRate = conversionRate;
}
public String getUnitPriceCurrencyType() {
	return unitPriceCurrencyType;
}
public void setUnitPriceCurrencyType(String unitPriceCurrencyType) {
	this.unitPriceCurrencyType = unitPriceCurrencyType;
}
public String getConvertedUnitPricecurrencyType() {
	return convertedUnitPricecurrencyType;
}
public void setConvertedUnitPricecurrencyType(
		String convertedUnitPricecurrencyType) {
	this.convertedUnitPricecurrencyType = convertedUnitPricecurrencyType;
}
public String getActivityId() {
	return activityId;
}
public void setActivityId(String activityId) {
	this.activityId = activityId;
}
public String getPreauthReciveTypeID() {
	return preauthReciveTypeID;
}
public void setPreauthReciveTypeID(String preauthReciveTypeID) {
	this.preauthReciveTypeID = preauthReciveTypeID;
}
public String getsCategory() {
	return sCategory;
}
public void setsCategory(String sCategory) {
	this.sCategory = sCategory;
}
public String getActivityTypeCode() {
	return ActivityTypeCode;
}
public void setActivityTypeCode(String activityTypeCode) {
	ActivityTypeCode = activityTypeCode;
}
public String getHosp_validity_flag() {
	return hosp_validity_flag;
}
public void setHosp_validity_flag(String hosp_validity_flag) {
	this.hosp_validity_flag = hosp_validity_flag;
}
public String getActivityTypeCodeDesc() {
	return activityTypeCodeDesc;
}
public void setActivityTypeCodeDesc(String activityTypeCodeDesc) {
	this.activityTypeCodeDesc = activityTypeCodeDesc;
}
public String getConvertedUnitPrice() {
	return convertedUnitPrice;
}
public void setConvertedUnitPrice(String convertedUnitPrice) {
	this.convertedUnitPrice = convertedUnitPrice;
}
public String getGlobalFlagYN() {
	return globalFlagYN;
}
public void setGlobalFlagYN(String globalFlagYN) {
	this.globalFlagYN = globalFlagYN;
}
public String getGeneralFlagYN() {
	return generalFlagYN;
}
public void setGeneralFlagYN(String generalFlagYN) {
	this.generalFlagYN = generalFlagYN;
}
public String getClinicalFlagYN() {
	return clinicalFlagYN;
}
public void setClinicalFlagYN(String clinicalFlagYN) {
	this.clinicalFlagYN = clinicalFlagYN;
}

public String getCurrencyType() {
	return currencyType;
}
public void setCurrencyType(String currencyType) {
	this.currencyType = currencyType;
}
public BigDecimal getConvertedProviderReqAmt() {
	return convertedProviderReqAmt;
}
public void setConvertedProviderReqAmt(BigDecimal convertedProviderReqAmt) {
	this.convertedProviderReqAmt = convertedProviderReqAmt;
}
public BigDecimal getAppliedCopay() {
	return appliedCopay;
}
public void setAppliedCopay(BigDecimal appliedCopay) {
	this.appliedCopay = appliedCopay;
}
public String getProcessorInternalRemarks() {
	return processorInternalRemarks;
}
public void setProcessorInternalRemarks(String processorInternalRemarks) {
	this.processorInternalRemarks = processorInternalRemarks;
}
public String getObservationFlag() {
	return observationFlag;
}
public void setObservationFlag(String observationFlag) {
	this.observationFlag = observationFlag;
}
public String getCondDenialCode() {
	return condDenialCode;
}
public void setCondDenialCode(String condDenialCode) {
	this.condDenialCode = condDenialCode;
}
public String getPayerAuthority() {
	return payerAuthority;
}
public void setPayerAuthority(String payerAuthority) {
	this.payerAuthority = payerAuthority;
}
public BigDecimal getBaseRate() {
	return baseRate;
}
public void setBaseRate(BigDecimal baseRate) {
	this.baseRate = baseRate;
}
public BigDecimal getNegotiationFactor() {
	return negotiationFactor;
}
public void setNegotiationFactor(BigDecimal negotiationFactor) {
	this.negotiationFactor = negotiationFactor;
}
public String getIrdrgRelativeWt() {
	return irdrgRelativeWt;
}
public void setIrdrgRelativeWt(String irdrgRelativeWt) {
	this.irdrgRelativeWt = irdrgRelativeWt;
}
public String getIrdrgPymntProcess() {
	return irdrgPymntProcess;
}
public void setIrdrgPymntProcess(String irdrgPymntProcess) {
	this.irdrgPymntProcess = irdrgPymntProcess;
}
public BigDecimal getOutlierPmntThresholdLmt() {
	return outlierPmntThresholdLmt;
}
public void setOutlierPmntThresholdLmt(BigDecimal outlierPmntThresholdLmt) {
	this.outlierPmntThresholdLmt = outlierPmntThresholdLmt;
}
public BigDecimal getMargin() {
	return margin;
}
public void setMargin(BigDecimal margin) {
	this.margin = margin;
}
public BigDecimal getAvgActivityCost() {
	return avgActivityCost;
}
public void setAvgActivityCost(BigDecimal avgActivityCost) {
	this.avgActivityCost = avgActivityCost;
}
public String getIrdrgProcessYN() {
	return irdrgProcessYN;
}
public void setIrdrgProcessYN(String irdrgProcessYN) {
	this.irdrgProcessYN = irdrgProcessYN;
}
public String getIrdrgInlierFlagYN() {
	return irdrgInlierFlagYN;
}
public void setIrdrgInlierFlagYN(String irdrgInlierFlagYN) {
	this.irdrgInlierFlagYN = irdrgInlierFlagYN;
}

public BigDecimal getPreauthRequestAmt() {
	return preauthRequestAmt;
}
public void setPreauthRequestAmt(BigDecimal preauthRequestAmt) {
	this.preauthRequestAmt = preauthRequestAmt;
}
public BigDecimal getAllowedAmt() {
	return allowedAmt;
}
public void setAllowedAmt(BigDecimal allowedAmt) {
	this.allowedAmt = allowedAmt;
}
public Double getVatAed() {
	return vatAed;
}
public void setVatAed(Double vatAed) {
	this.vatAed = vatAed;
}
public Double getVatAddedReqAmnt() {
	return vatAddedReqAmnt;
}
public void setVatAddedReqAmnt(Double vatAddedReqAmnt) {
	this.vatAddedReqAmnt = vatAddedReqAmnt;
}
public Double getVatAddedApprAmnt() {
	return vatAddedApprAmnt;
}
public void setVatAddedApprAmnt(Double vatAddedApprAmnt) {
	this.vatAddedApprAmnt = vatAddedApprAmnt;
}
public Double getAppliedVatPercent() {
	return appliedVatPercent;
}
public void setAppliedVatPercent(Double appliedVatPercent) {
	this.appliedVatPercent = appliedVatPercent;
}
public Double getAppliedVatAed() {
	return appliedVatAed;
}
public void setAppliedVatAed(Double appliedVatAed) {
	this.appliedVatAed = appliedVatAed;
}
public Double getVatPercent() {
	return vatPercent;
}
public void setVatPercent(Double vatPercent) {
	this.vatPercent = vatPercent;
}
public String getAppliedVatAEDPER() {
	return appliedVatAEDPER;
}
public void setAppliedVatAEDPER(String appliedVatAEDPER) {
	this.appliedVatAEDPER = appliedVatAEDPER;
}
public String getVatFlag() {
	return vatFlag;
}
public void setVatFlag(String vatFlag) {
	this.vatFlag = vatFlag;
}
public String getAllowedAmtVatAdded() {
	return allowedAmtVatAdded;
}
public void setAllowedAmtVatAdded(String allowedAmtVatAdded) {
	this.allowedAmtVatAdded = allowedAmtVatAdded;
}
public BigDecimal getProviderRequestedAmtVatAdded() {
	return providerRequestedAmtVatAdded;
}
public void setProviderRequestedAmtVatAdded(BigDecimal providerRequestedAmtVatAdded) {
	this.providerRequestedAmtVatAdded = providerRequestedAmtVatAdded;
}

public String getObsYN() {
	return obsYN;
}

public void setObsYN(String obsYN) {
	this.obsYN = obsYN;
}

public BigDecimal getActivityunitPrice() {
	return ActivityunitPrice;
}

public void setActivityunitPrice(BigDecimal activityunitPrice) {
	ActivityunitPrice = activityunitPrice;
}

public BigDecimal getActivityDiscount() {
	return ActivityDiscount;
}

public void setActivityDiscount(BigDecimal activityDiscount) {
	ActivityDiscount = activityDiscount;
}

public String getVBCDescription() {
	return VBCDescription;
}

public void setVBCDescription(String vBCDescription) {
	VBCDescription = vBCDescription;
}
}//end of ClaimantVO.java