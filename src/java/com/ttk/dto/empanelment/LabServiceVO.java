
/**
 * @ (#) LabServiceVO.java March 24, 2015
 * Project      : TTK HealthCare Services
 * File         : LabServiceVO
 *  Author       :Kishor kumar S h
 * Company      : Rcs Technologies
 * Date Created : 1st March 2015
 *
 * @author       :Kishor kumar S h
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dto.empanelment;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class LabServiceVO extends BaseVO{
	
	public String getPharmaDesc() {
		return pharmaDesc;
	}
	public void setPharmaDesc(String pharmaDesc) {
		this.pharmaDesc = pharmaDesc;
	}
	public String getPharmacyDescSearch() {
		return pharmacyDescSearch;
	}
	public void setPharmacyDescSearch(String pharmacyDescSearch) {
		this.pharmacyDescSearch = pharmacyDescSearch;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getRouteOfMediation() {
		return routeOfMediation;
	}
	public void setRouteOfMediation(String routeOfMediation) {
		this.routeOfMediation = routeOfMediation;
	}
	public String getPharmacyCode() {
		return pharmacyCode;
	}
	public void setPharmacyCode(String pharmacyCode) {
		this.pharmacyCode = pharmacyCode;
	}
	public String getPharmacyDesc() {
		return pharmacyDesc;
	}
	public void setPharmacyDesc(String pharmacyDesc) {
		this.pharmacyDesc = pharmacyDesc;
	}
	public int getQuanity() {
		return quanity;
	}
	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}
	public String getDosageForm() {
		return dosageForm;
	}
	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public int getFreqInADay() {
		return freqInADay;
	}
	public void setFreqInADay(int freqInADay) {
		this.freqInADay = freqInADay;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	private String strActivityCode	= 	"";
	private String strShortDesc		= 	"";
	private String strPrice			=	"";
	private String strMedicalTypeId =	"";
	private String serviceName 		=	"";
	private Long	ActivitySeqId	=	null;
	
	private BigDecimal unitPrice	=	null;
	private int quantity			=	0;
	private BigDecimal gross		=	null;
	private BigDecimal discount		=	null;
	private BigDecimal discGross	=	null;
	private BigDecimal patientShare	=	null;
	private BigDecimal netAmount	=	null;
	

	//Pharmacy Getters
	private String pharmacyCode		= 	"";
	private String pharmacyDesc		= 	"";
	private String strength			=	"";
	private int quanity				=	0;
	private String dosageForm 		=	"";
	private String dosage			=	"";
	private int freqInADay 			=	0;
	private int noOfDays			=	0;
	private String routeOfMediation	=	"";
	private String pharmacyDescSearch	=	"";
	private String pharmaDesc	=	"";
	
	//E N D Pharmacy Getters
	
	public Long getActivitySeqId() {
		return ActivitySeqId;
	}
	public void setActivitySeqId(Long activitySeqId) {
		ActivitySeqId = activitySeqId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getMedicalTypeId() {
		return strMedicalTypeId;
	}
	public void setMedicalTypeId(String strMedicalTypeId) {
		this.strMedicalTypeId = strMedicalTypeId;
	}
	public String getActivityCode() {
		return strActivityCode;
	}
	public void setActivityCode(String strActivityCode) {
		this.strActivityCode = strActivityCode;
	}
	public String getShortDesc() {
		return strShortDesc;
	}
	public void setShortDesc(String strShortDesc) {
		this.strShortDesc = strShortDesc;
	}
	public String getPrice() {
		return strPrice;
	}
	public void setPrice(String strPrice) {
		this.strPrice = strPrice;
	}
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getGross() {
		return gross;
	}
	public void setGross(BigDecimal gross) {
		this.gross = gross;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getDiscGross() {
		return discGross;
	}
	public void setDiscGross(BigDecimal discGross) {
		this.discGross = discGross;
	}
	public BigDecimal getPatientShare() {
		return patientShare;
	}
	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
}//end of LabServiceVO
