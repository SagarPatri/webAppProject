

/**
 * @ (#) ClaimDetailsVO.java 31st August 2015
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 31st August 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 31st August 2015
 * Reason        :
 *
 */
package com.ttk.dto.onlineforms.insuranceLogin;

import java.util.Date;
import java.math.BigDecimal;

public class AuthDetailsVO extends AuthorizationVO  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String authNumber;
	private String preAuthNumber;
	private String hospName;
	private Date encounterDateFrom;
	private Date encounterDateTo;
	private Date startDate;
	private String diagnosysCode;
	private String shortDesc;
	private BigDecimal totApprovedAmount;
	private BigDecimal deductibleAmount;
	private BigDecimal copayAmount;
	private BigDecimal benefitAmount;
	private String activityBenifit;
	private String encounterType;
	private String activityStatus;
	private String activityStatusDescription;
	private String claimReceived;
	public String getAuthNumber() {
		return authNumber;
	}
	public void setAuthNumber(String authNumber) {
		this.authNumber = authNumber;
	}
	public String getHospName() {
		return hospName;
	}
	public void setHospName(String hospName) {
		this.hospName = hospName;
	}
	public Date getEncounterDateFrom() {
		return encounterDateFrom;
	}
	public void setEncounterDateFrom(Date encounterDateFrom) {
		this.encounterDateFrom = encounterDateFrom;
	}
	public Date getEncounterDateTo() {
		return encounterDateTo;
	}
	public void setEncounterDateTo(Date encounterDateTo) {
		this.encounterDateTo = encounterDateTo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getDiagnosysCode() {
		return diagnosysCode;
	}
	public void setDiagnosysCode(String diagnosysCode) {
		this.diagnosysCode = diagnosysCode;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public BigDecimal getTotApprovedAmount() {
		return totApprovedAmount;
	}
	public void setTotApprovedAmount(BigDecimal totApprovedAmount) {
		this.totApprovedAmount = totApprovedAmount;
	}
	public BigDecimal getDeductibleAmount() {
		return deductibleAmount;
	}
	public void setDeductibleAmount(BigDecimal deductibleAmount) {
		this.deductibleAmount = deductibleAmount;
	}
	public BigDecimal getCopayAmount() {
		return copayAmount;
	}
	public void setCopayAmount(BigDecimal copayAmount) {
		this.copayAmount = copayAmount;
	}
	public BigDecimal getBenefitAmount() {
		return benefitAmount;
	}
	public void setBenefitAmount(BigDecimal benefitAmount) {
		this.benefitAmount = benefitAmount;
	}
	public String getActivityBenifit() {
		return activityBenifit;
	}
	public void setActivityBenifit(String activityBenifit) {
		this.activityBenifit = activityBenifit;
	}
	public String getEncounterType() {
		return encounterType;
	}
	public void setEncounterType(String encounterType) {
		this.encounterType = encounterType;
	}
	public String getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}
	public String getActivityStatusDescription() {
		return activityStatusDescription;
	}
	public void setActivityStatusDescription(String activityStatusDescription) {
		this.activityStatusDescription = activityStatusDescription;
	}
	public String getClaimReceived() {
		return claimReceived;
	}
	public void setClaimReceived(String claimReceived) {
		this.claimReceived = claimReceived;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPreAuthNumber() {
		return preAuthNumber;
	}
	public void setPreAuthNumber(String preAuthNumber) {
		this.preAuthNumber = preAuthNumber;
	}

	
	

}
