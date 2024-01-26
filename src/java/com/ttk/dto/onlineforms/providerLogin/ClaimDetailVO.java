package com.ttk.dto.onlineforms.providerLogin;

/** 
 * @ (#) ClaimDetailVO.java 23rd Nov 2015 
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 23rd Nov 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 23rd Nov 2015
 * Reason        :
 *
 */

import java.util.Date;

import com.ttk.dto.BaseVO;



public class ClaimDetailVO extends BaseVO {
	private static final long serialVersionUID = 1L;

	
	private String claimNO;
	private String batchNo;
	private String status;
	private String providerName;
	private String providerlincese;
	private String empanelId;
	private String contactNo;
	private String emailId;
	private String patientCardId;
	private String patientName;
	private String gender;
	private String benefitType;
	private String mem_Dob;
	private String policyNo;
	private String dtOfTreatment;
	private String clmSeqId;
	private String settlementNo;
	private String clmStatus;

	//private DentalOrthoVO dentalOrthoVO;
	
	private String country;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getClaimNO() {
		return claimNO;
	}
	public void setClaimNO(String claimNO) {
		this.claimNO = claimNO;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getEmpanelId() {
		return empanelId;
	}
	public void setEmpanelId(String empanelId) {
		this.empanelId = empanelId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPatientCardId() {
		return patientCardId;
	}
	public void setPatientCardId(String patientCardId) {
		this.patientCardId = patientCardId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBenefitType() {
		return benefitType;
	}
	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}
	public String getDtOfTreatment() {
		return dtOfTreatment;
	}
	public void setDtOfTreatment(String dtOfTreatment) {
		this.dtOfTreatment = dtOfTreatment;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProviderlincese() {
		return providerlincese;
	}
	public void setProviderlincese(String providerlincese) {
		this.providerlincese = providerlincese;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getMem_Dob() {
		return mem_Dob;
	}
	public void setMem_Dob(String mem_Dob) {
		this.mem_Dob = mem_Dob;
	}
	public String getClmSeqId() {
		return clmSeqId;
	}
	public void setClmSeqId(String clmSeqId) {
		this.clmSeqId = clmSeqId;
	}
	public String getSettlementNo() {
		return settlementNo;
	}
	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}
	public String getClmStatus() {
		return clmStatus;
	}
	public void setClmStatus(String clmStatus) {
		this.clmStatus = clmStatus;
	}
	/*public DentalOrthoVO getDentalOrthoVO() {
		return dentalOrthoVO;
	}
	public void setDentalOrthoVO(DentalOrthoVO dentalOrthoVO) {
		this.dentalOrthoVO = dentalOrthoVO;
	}
	*/
}
