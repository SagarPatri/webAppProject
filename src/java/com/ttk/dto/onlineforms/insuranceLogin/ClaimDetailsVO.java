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

public class ClaimDetailsVO extends ClaimsVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String claimNumber;
	private Date clmReceivedDate;
	private String hospName;
	private String encounterProviderInvoiceNo;
	private Date startDate;
	private String claimPaymentId;
	private Date claimPaymentDate;
	private String diagnosysCode;
	private String shortDesc;
	private BigDecimal totDiscGrossAmount;
	private BigDecimal deductibleAmount;
	private BigDecimal copayAmount;
	private BigDecimal benefitAmount;
	private String claimRecipient;
	private String description;
	private String authNumber;
	private String clmStatusTypeId;
	private String claimStatusDescription;
	private String payeeName;
	private String chequeEftNumber;
	private Date ChequeEftDate;
	private Date chequeDispatchDate;
	private String nameOfCourier;
	private String courierConsignment;

	private Date encounterDateFrom;
	private Date encounterDateTo;
	
	
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
	
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public Date getClmReceivedDate() {
		return clmReceivedDate;
	}
	public void setClmReceivedDate(Date clmReceivedDate) {
		this.clmReceivedDate = clmReceivedDate;
	}
	public String getHospName() {
		return hospName;
	}
	public void setHospName(String hospName) {
		this.hospName = hospName;
	}
	public String getEncounterProviderInvoiceNo() {
		return encounterProviderInvoiceNo;
	}
	public void setEncounterProviderInvoiceNo(String encounterProviderInvoiceNo) {
		this.encounterProviderInvoiceNo = encounterProviderInvoiceNo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getClaimPaymentId() {
		return claimPaymentId;
	}
	public void setClaimPaymentId(String claimPaymentId) {
		this.claimPaymentId = claimPaymentId;
	}
	public Date getClaimPaymentDate() {
		return claimPaymentDate;
	}
	public void setClaimPaymentDate(Date claimPaymentDate) {
		this.claimPaymentDate = claimPaymentDate;
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
	public BigDecimal getTotDiscGrossAmount() {
		return totDiscGrossAmount;
	}
	public void setTotDiscGrossAmount(BigDecimal totDiscGrossAmount) {
		this.totDiscGrossAmount = totDiscGrossAmount;
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
	public String getClaimRecipient() {
		return claimRecipient;
	}
	public void setClaimRecipient(String claimRecipient) {
		this.claimRecipient = claimRecipient;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthNumber() {
		return authNumber;
	}
	public void setAuthNumber(String authNumber) {
		this.authNumber = authNumber;
	}
	public String getClmStatusTypeId() {
		return clmStatusTypeId;
	}
	public void setClmStatusTypeId(String clmStatusTypeId) {
		this.clmStatusTypeId = clmStatusTypeId;
	}
	public String getClaimStatusDescription() {
		return claimStatusDescription;
	}
	public void setClaimStatusDescription(String claimStatusDescription) {
		this.claimStatusDescription = claimStatusDescription;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getChequeEftNumber() {
		return chequeEftNumber;
	}
	public void setChequeEftNumber(String chequeEftNumber) {
		this.chequeEftNumber = chequeEftNumber;
	}
	public Date getChequeEftDate() {
		return ChequeEftDate;
	}
	public void setChequeEftDate(Date ChequeEftDate) {
		this.ChequeEftDate = ChequeEftDate;
	}
	public Date getChequeDispatchDate() {
		return chequeDispatchDate;
	}
	public void setChequeDispatchDate(Date chequeDispatchDate) {
		this.chequeDispatchDate = chequeDispatchDate;
	}
	public String getNameOfCourier() {
		return nameOfCourier;
	}
	public void setNameOfCourier(String nameOfCourier) {
		this.nameOfCourier = nameOfCourier;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCourierConsignment() {
		return courierConsignment;
	}
	public void setCourierConsignment(String courierConsignment) {
		this.courierConsignment = courierConsignment;
	}
	
	
	
	
}


