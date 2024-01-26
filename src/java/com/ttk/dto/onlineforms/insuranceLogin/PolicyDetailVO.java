/**
 * @ (#) PolicyDetailVO.java 27th August 2015
 * Author      : KISHOR KUMAR S H
 * Company     : RCS TECHNOLOGIES
 * Date Created: 27th August 2015
 *
 * @author 		 : KISHOR KUMAR S H
 * Modified by   : KISHOR KUMAR S H
 * Modified date : 27th August 2015
 * Reason        :
 *
 */
package com.ttk.dto.onlineforms.insuranceLogin;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.dto.BaseVO;

public class PolicyDetailVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	
	private String policyNo;
	private String policySeqId;
	
	private Date dtDateOfInception 			= null;
    private Date dtExpiryDate 				= null;
    private Date dtJoiningDate 				= null;
    private Date dtExitDate 				= null;
    private BigDecimal bdAggSumInsured 		= null;
    private BigDecimal bdBalSumInsured 		= null;
    private String strEmployeeNbr 			=	"";
    private String strNetwork 				=	"";
    private String strProductName			=	"";
    private String strSpecialExclusions		=	"";
    private String strWaitingPeriod			=	"";
    private String strEndorsStatus			=	"";
    private String strDependent				=	"";
    private String strInsured				=	"";
    private String strStatus				=	"";
    
    
    //TOB
    private String strTob;
    
    //Benifit Tree
    private String iIpLimit	 				=	"";
    private String iOpLimit 					=	"";
    private String iChronicLimit				=	"";
    private String iMaternityNormal			=	"";
    private String iMaternityCSection			=	"";
    private String iDentalLimit				=	"";
    private String iOpticalLimit				=	"";
    private String iAlternativeMedicineLimit	=	"";
    private String iPhysiotherapyLimit			=	"";
    private String iPharmacy					=	"";
    private String iMRIandCTscan				=	"";
    private String maternity				=	"";
    private String enrolTypeId;
    
    
	public Date getDateOfInception() {
		return dtDateOfInception;
	}
	public void setDateOfInception(Date dtDateOfInception) {
		this.dtDateOfInception = dtDateOfInception;
	}
	public Date getExpiryDate() {
		return dtExpiryDate;
	}
	public void setExpiryDate(Date dtExpiryDate) {
		this.dtExpiryDate = dtExpiryDate;
	}
	public Date getJoiningDate() {
		return dtJoiningDate;
	}
	public void setJoiningDate(Date dtJoiningDate) {
		this.dtJoiningDate = dtJoiningDate;
	}
	public Date getExitDate() {
		return dtExitDate;
	}
	public void setExitDate(Date dtExitDate) {
		this.dtExitDate = dtExitDate;
	}
	public BigDecimal getAggSumInsured() {
		return bdAggSumInsured;
	}
	public void setAggSumInsured(BigDecimal bdAggSumInsured) {
		this.bdAggSumInsured = bdAggSumInsured;
	}
	public BigDecimal getBalSumInsured() {
		return bdBalSumInsured;
	}
	public void setBalSumInsured(BigDecimal bdBalSumInsured) {
		this.bdBalSumInsured = bdBalSumInsured;
	}
	public String getEmployeeNbr() {
		return strEmployeeNbr;
	}
	public void setEmployeeNbr(String strEmployeeNbr) {
		this.strEmployeeNbr = strEmployeeNbr;
	}
	public String getNetwork() {
		return strNetwork;
	}
	public void setNetwork(String strNetwork) {
		this.strNetwork = strNetwork;
	}
	public String getProductName() {
		return strProductName;
	}
	public void setProductName(String strProductName) {
		this.strProductName = strProductName;
	}
	public String getSpecialExclusions() {
		return strSpecialExclusions;
	}
	public void setSpecialExclusions(String strSpecialExclusions) {
		this.strSpecialExclusions = strSpecialExclusions;
	}
	public String getWaitingPeriod() {
		return strWaitingPeriod;
	}
	public void setWaitingPeriod(String strWaitingPeriod) {
		this.strWaitingPeriod = strWaitingPeriod;
	}
	public String getIpLimit() {
		return iIpLimit;
	}
	public void setIpLimit(String iIpLimit) {
		this.iIpLimit = iIpLimit;
	}
	public String getOpLimit() {
		return iOpLimit;
	}
	public void setOpLimit(String iOpLimit) {
		this.iOpLimit = iOpLimit;
	}
	public String getChronicLimit() {
		return iChronicLimit;
	}
	public void setChronicLimit(String iChronicLimit) {
		this.iChronicLimit = iChronicLimit;
	}
	public String getMaternityNormal() {
		return iMaternityNormal;
	}
	public void setMaternityNormal(String iMaternityNormal) {
		this.iMaternityNormal = iMaternityNormal;
	}
	public String getMaternityCSection() {
		return iMaternityCSection;
	}
	public void setMaternityCSection(String iMaternityCSection) {
		this.iMaternityCSection = iMaternityCSection;
	}
	public String getDentalLimit() {
		return iDentalLimit;
	}
	public void setDentalLimit(String iDentalLimit) {
		this.iDentalLimit = iDentalLimit;
	}
	public String getOpticalLimit() {
		return iOpticalLimit;
	}
	public void setOpticalLimit(String iOpticalLimit) {
		this.iOpticalLimit = iOpticalLimit;
	}
	public String getAlternativeMedicineLimit() {
		return iAlternativeMedicineLimit;
	}
	public void setAlternativeMedicineLimit(String iAlternativeMedicineLimit) {
		this.iAlternativeMedicineLimit = iAlternativeMedicineLimit;
	}
	public String getPhysiotherapyLimit() {
		return iPhysiotherapyLimit;
	}
	public void setPhysiotherapyLimit(String iPhysiotherapyLimit) {
		this.iPhysiotherapyLimit = iPhysiotherapyLimit;
	}
	public String getPharmacy() {
		return iPharmacy;
	}
	public void setPharmacy(String iPharmacy) {
		this.iPharmacy = iPharmacy;
	}
	public String getMriandCTscan() {
		return iMRIandCTscan;
	}
	public void setMriandCTscan(String iMRIandCTscan) {
		this.iMRIandCTscan = iMRIandCTscan;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getPolicySeqId() {
		return policySeqId;
	}
	public void setPolicySeqId(String policySeqId) {
		this.policySeqId = policySeqId;
	}
	public String getTob() {
		return strTob;
	}
	public void setTob(String strTob) {
		this.strTob = strTob;
	}
	public String getEndorsStatus() {
		return strEndorsStatus;
	}
	public void setEndorsStatus(String strEndorsStatus) {
		this.strEndorsStatus = strEndorsStatus;
	}
	public String getDependent() {
		return strDependent;
	}
	public void setDependent(String strDependent) {
		this.strDependent = strDependent;
	}
	public String getInsured() {
		return strInsured;
	}
	public void setInsured(String strInsured) {
		this.strInsured = strInsured;
	}
	public String getStatus() {
		return strStatus;
	}
	public void setStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	public String getMaternity() {
		return maternity;
	}
	public void setMaternity(String maternity) {
		this.maternity = maternity;
	}
	public String getEnrolTypeId() {
		return enrolTypeId;
	}
	public void setEnrolTypeId(String enrolTypeId) {
		this.enrolTypeId = enrolTypeId;
	}


}
