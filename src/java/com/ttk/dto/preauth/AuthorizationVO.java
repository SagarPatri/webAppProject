/**
 * @ (#) AuthorizationVO.java Apr 11, 2006
 * Project      : TTK HealthCare Services
 * File         : AuthorizationVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Apr 11, 2006
 *
 * @author       :  Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;

public class AuthorizationVO extends PreAuthVO {
    
    private String strAuthorizationNo="";
    private BigDecimal bdPrevApprovedAmount=null;
    private BigDecimal bdTotalSumInsured=null;
    private BigDecimal bdAvailSumInsured=null;
    private BigDecimal bdAuthorizedAmount=null;
    private BigDecimal bdRequestedAmount=null;
    private BigDecimal bdAvailBufferAmount=null;
    private BigDecimal bdApprovedBufferAmount=null;
    private BigDecimal bdAvailCumBonus = null;
    private BigDecimal bdApprovedAmount = null;
    private Date dtApprovedDate=null;
    private String strApprovedTime="";
    private String strApprovedDay="";
    private String strReasonTypeID="";
    private String strApprovedBy = "";
    private String strAdmnAuthority = "";
    private Long lngBufferDtlSeqID = null;
    private Date dtAuthorizedDate=null;
    private String strAuthorizedTime="";
    private String strAuthorizedDay="";
    private String strAuthorizedBy = "";
    private BigDecimal bdMaxAllowedAmt = null;
    private String strAuthPermittedBy = "";
    private BigDecimal bdPAApprovedAmt = null;
    private Long lngBufferHdrSeqID = null;
    private String strDiscPresentYN = "";
    private String strCompletedYN = "";
    private Long lngBalanceSeqID = null;
    private String strClaimTypeID = "";
    private String strClaimSubTypeID = "";
    private BigDecimal bdDiscountAmount=null;
    private BigDecimal bdCopayAmount=null;
    private BigDecimal bdAvailDomTrtLimit=null;
    private String strEnrolTypeID = "";
    private String strDVMessageYN = "";
    private String strClauseRemarks="";
    private Integer intUnAvailableSuminsured = null;
    private String strAuthLtrTypeID = "";//AUTH_LETTER_GENERAL_TYPE_ID
    private BigDecimal bdPACopayAmt = null;//preauth_co_payment_amount
    private BigDecimal bdPADiscountAmt = null;//preauth_discount_amount
    private BigDecimal bdDepositAmt = null;//deposit_amount
    private BigDecimal bdFinalApprovedAmt = null; //Used in Claims->Settlement Details
    private BigDecimal bdPATariffAppAmt = null;//Preauth Approved Tariff Amount
    private Long lngPolicyGrpSeqID = null;
    private BigDecimal bdRequestedTaxAmt = null;
    private BigDecimal bdTaxAmtPaid = null;
    private String strCalcButDispYN ="";
    private String strPressButManYN ="";
    private BigDecimal bdSerTaxCalPer =null;
    //Added as per KOC 1216B Change Request 
    private BigDecimal bdCopayBufferamount=null;
    private BigDecimal bdPreCopayBufferamount=null;
    private BigDecimal bdTotcopayAmount=null;
    //KOC 1286 for OPD 
    private String strOPDAmountYN = "";
  //added for Policy Deductable - KOC-1277
    private BigDecimal bdBalanceDedAmount = null;
    private String strPolicy_deductable_yn = "";
    private String strPreauthDifference = "";
    private String strPreauthYN="";
 //ended
    // KOC1216A
	//added for KOC-1273
    private String strClaimSubGeneralTypeId = "";
	public String getClaimSubGeneralTypeId() {
	return strClaimSubGeneralTypeId;
	}

	public void setClaimSubGeneralTypeId(String strClaimSubGeneralTypeId) {
		this.strClaimSubGeneralTypeId = strClaimSubGeneralTypeId;
	}
	//ended


    private String strAdditionalDomicialaryYN ="";
    //added for CR Mail-SMS for Cigna
    private String strMailNotifyYN = "";
    //Mail-SMS for Cigna
    private String strCigna_Ins_Cust = "";
  
  
	private BigDecimal memberRestrictedSIAmt= null;//preauth_co_payment_amount
    private BigDecimal avaRestrictedSIAmt = null;//Start Modification Added as per KOC 1140(SUM INSURED RESTRICTION)
    private String famRestrictedYN ="N";//Start Modification Added as per KOC 1140(SUM INSURED RESTRICTION)
	 //bajaj xchanges
    private String strInsurerApprovedStatus="";
    private String strInsremarks="";
    private String strinsurerStatusYN="";
    //koc for griavance
	private String strSeniorCitizenYN = "";
    
    private BigDecimal invDisallowdedAmt= null; //INVESTIGATION UAT
    private String clmapramt= ""; 
    
    private String strInsDecisionyn="";//Bajaj Enhan
    
    private String strDenialBanyn="";//denial process
  //denial process
   	public String getDenialBanyn() {
		return strDenialBanyn;
	}

	public void setDenialBanyn(String strDenialBanyn) {
		this.strDenialBanyn = strDenialBanyn;
	}
	//denial process
	public String getInsDecisionyn() {
   		return strInsDecisionyn;
   	}

   	public void setInsDecisionyn(String strInsDecisionyn) {
   		this.strInsDecisionyn = strInsDecisionyn;
   	}

  
    public String getClmapramt() {
		return clmapramt;
	}

	public void setClmapramt(String clmAprAmt) {
		clmapramt = clmAprAmt;
	}

	public BigDecimal getInvDisallowdedAmt() {
		return invDisallowdedAmt;
	}

	public void setInvDisallowdedAmt(BigDecimal invDisallowdedAmt) {
		this.invDisallowdedAmt = invDisallowdedAmt;
	}
  
	public void setSeniorCitizenYN(String strSeniorCitizenYN) {
		this.strSeniorCitizenYN = strSeniorCitizenYN;
	}

	public String getSeniorCitizenYN() {
		return strSeniorCitizenYN;
	}
	//koc for griavance
    /**
	 * @param insurerApprovedStatus the insurerApprovedStatus to set
	 */
	public void setInsurerApprovedStatus(String insurerApprovedStatus) {
		strInsurerApprovedStatus = insurerApprovedStatus;
	}

	/**
	 * @return the insurerApprovedStatus
	 */
	public String getInsurerApprovedStatus() {
		return strInsurerApprovedStatus;
	}

	public String getInsremarks() {
		return strInsremarks;
	}

	public void setInsremarks(String insremarks) {
		strInsremarks = insremarks;
	}

	public String getStrinsurerStatusYN() {
		return strinsurerStatusYN;
	}

	public void setStrinsurerStatusYN(String strinsurerStatusYN) {
		this.strinsurerStatusYN = strinsurerStatusYN;
	}

    /**
	 * @param avaRestrictedSIAmt the avaRestrictedSIAmt to set
	 */
	public void setAvaRestrictedSIAmt(BigDecimal avaRestrictedSIAmt) {
		this.avaRestrictedSIAmt = avaRestrictedSIAmt;
	}
	/**
	 * @return the avaRestrictedSIAmt
	 */
	public BigDecimal getAvaRestrictedSIAmt() {
		return avaRestrictedSIAmt;
	}
	/**
	 * @param famRestrictedYN the famRestrictedYN to set
	 */
	public void setFamRestrictedYN(String famRestrictedYN) {
		this.famRestrictedYN = famRestrictedYN;
	}
	/**
	 * @return the famRestrictedYN
	 */
	public String getFamRestrictedYN() {
		return famRestrictedYN;
	}
	/**
	 * @param memberRestrictedSIAmt the memberRestrictedSIAmt to set
	 */
	public void setMemberRestrictedSIAmt(BigDecimal memberRestrictedSIAmt) {
		this.memberRestrictedSIAmt = memberRestrictedSIAmt;
	}
	/**
	 * @return the memberRestrictedSIAmt
	 */
	public BigDecimal getMemberRestrictedSIAmt() {
		return memberRestrictedSIAmt;
	}

	

	
	
  
    
  
    /**
	 * @param strOPDAmountYN the strOPDAmountYN to set
	 */
	public void setOPDAmountYN(String strOPDAmountYN) {
		this.strOPDAmountYN = strOPDAmountYN;
	}

	/**
	 * @return the strOPDAmountYN
	 */
	public String getOPDAmountYN() {
		return strOPDAmountYN;
	}
	 //KOC 1286 for OPD 
    
    /** Retrieve the Additional Domicialary Status
	 * @return Returns the strAdditionalDomicialaryYN.
	 */
    public String getAdditionalDomicialaryYN() {
		return strAdditionalDomicialaryYN;
	}
   
    /** Sets the Additional Domicialary Status
	 * @param strAdditionalDomicialaryYN The strAdditionalDomicialaryYN to set.
	 */
	public void setAdditionalDomicialaryYN(String strAdditionalDomicialaryYN) {
		this.strAdditionalDomicialaryYN = strAdditionalDomicialaryYN;
	}
	/**
	 * @param totcopayAmount the totcopayAmount to set
	 */
	public void setTotcopayAmount(BigDecimal totcopayAmount) {
		bdTotcopayAmount = totcopayAmount;
	}

	/**
	 * @return the totcopayAmount
	 */
	public BigDecimal getTotcopayAmount() {
		return bdTotcopayAmount;
	}

	/**
	 * @param preCopayBufferamount the preCopayBufferamount to set
	 */
	public void setPreCopayBufferamount(BigDecimal preCopayBufferamount) {
		bdPreCopayBufferamount = preCopayBufferamount;
	}

	/**
	 * @return the preCopayBufferamount
	 */
	public BigDecimal getPreCopayBufferamount() {
		return bdPreCopayBufferamount;
	}

	/**
	 * @param copayBufferamount the copayBufferamount to set
	 */
	public void setCopayBufferamount(BigDecimal copayBufferamount) {
		bdCopayBufferamount = copayBufferamount;
	}

	/**
	 * @return the copayBufferamount
	 */
	public BigDecimal getCopayBufferamount() {
		return bdCopayBufferamount;
	}
	  //Added as per KOC 1216B Change Request 
    
	
	/** Retrieve the PolicyGrpSeqID
	 * @return Returns the lngPolicyGrpSeqID.
	 */
	public Long getPolicyGrpSeqID() {
		return lngPolicyGrpSeqID;
	}//end of getPolicyGrpSeqID()

	/** Sets the PolicyGrpSeqID
	 * @param lngPolicyGrpSeqID The lngPolicyGrpSeqID to set.
	 */
	public void setPolicyGrpSeqID(Long lngPolicyGrpSeqID) {
		this.lngPolicyGrpSeqID = lngPolicyGrpSeqID;
	}//end of setPolicyGrpSeqID(Long lngPolicyGrpSeqID)

	/** Retrieve the PATariffAppAmt
	 * @return Returns the bdPATariffAppAmt.
	 */
	public BigDecimal getPATariffAppAmt() {
		return bdPATariffAppAmt;
	}//end of getPATariffAppAmt()

	/** Sets the PATariffAppAmt
	 * @param bdPATariffAppAmt The bdPATariffAppAmt to set.
	 */
	public void setPATariffAppAmt(BigDecimal bdPATariffAppAmt) {
		this.bdPATariffAppAmt = bdPATariffAppAmt;
	}//end of setPATariffAppAmt(BigDecimal bdPATariffAppAmt)

	/** Retrieve the DepositAmt
	 * @return Returns the bdDepositAmt.
	 */
	public BigDecimal getDepositAmt() {
		return bdDepositAmt;
	}//end of getDepositAmt()

	/** Sets the DepositAmt
	 * @param bdDepositAmt The bdDepositAmt to set.
	 */
	public void setDepositAmt(BigDecimal bdDepositAmt) {
		this.bdDepositAmt = bdDepositAmt;
	}//end of setDepositAmt(BigDecimal bdDepositAmt)

	/** Retrieve the FinalApprovedAmt - This method is for Claims Final Approved Amt
	 * @return Returns the bdFinalApprovedAmt.
	 */
	public BigDecimal getFinalApprovedAmt() {
		return bdFinalApprovedAmt;
	}//end of getFinalApprovedAmt()

	/** Sets the FinalApprovedAmt - This method is for Claims Final Approved Amt
	 * @param bdFinalApprovedAmt The bdFinalApprovedAmt to set.
	 */
	public void setFinalApprovedAmt(BigDecimal bdFinalApprovedAmt) {
		this.bdFinalApprovedAmt = bdFinalApprovedAmt;
	}//end of setFinalApprovedAmt(BigDecimal bdFinalApprovedAmt)

	/** Retrieve the PACopayAmt
	 * @return Returns the bdPACopayAmt.
	 */
	public BigDecimal getPACopayAmt() {
		return bdPACopayAmt;
	}//end of getPACopayAmt()

	/** Sets the PACopayAmt
	 * @param bdPACopayAmt The bdPACopayAmt to set.
	 */
	public void setPACopayAmt(BigDecimal bdPACopayAmt) {
		this.bdPACopayAmt = bdPACopayAmt;
	}//end of setPACopayAmt(BigDecimal bdPACopayAmt)

	/** Retrieve the PADiscountAmt
	 * @return Returns the bdPADiscountAmt.
	 */
	public BigDecimal getPADiscountAmt() {
		return bdPADiscountAmt;
	}//end of getPADiscountAmt()

	/** Sets the PADiscountAmt
	 * @param bdPADiscountAmt The bdPADiscountAmt to set.
	 */
	public void setPADiscountAmt(BigDecimal bdPADiscountAmt) {
		this.bdPADiscountAmt = bdPADiscountAmt;
	}//end of setPADiscountAmt(BigDecimal bdPADiscountAmt)

	/** Retrieve the AuthLtrTypeID
	 * @return Returns the strAuthLtrTypeID.
	 */
	public String getAuthLtrTypeID() {
		return strAuthLtrTypeID;
	}//end of getAuthLtrTypeID()

	/** Sets the AuthLtrTypeID
	 * @param strAuthLtrTypeID The strAuthLtrTypeID to set.
	 */
	public void setAuthLtrTypeID(String strAuthLtrTypeID) {
		this.strAuthLtrTypeID = strAuthLtrTypeID;
	}//end of setAuthLtrTypeID(String strAuthLtrTypeID)

	/** This method returns the UnAvailable Sum insured Count
     * @return Returns the intUnAvailableSuminsuredt.
     */
    public Integer getUnAvailableSuminsured() {
        return intUnAvailableSuminsured;
    }//end of getReviewCount()
    
    /** This method sets the Un Available Sum insured Count
     * @param intUnAvailableSuminsuredt The intUnAvailableSuminsuredt to set.
     */
    public void setUnAvailableSuminsured(Integer intUnAvailableSuminsured) {
        this.intUnAvailableSuminsured = intUnAvailableSuminsured;
    }//end of setReviewCount(String intUnAvailableSuminsuredt)
    
    /** Retrieve the Clause Remarks
	 * @return Returns the strClauseRemarks.
	 */
    public String getClauseRemarks() {
		return strClauseRemarks;
	}//end of getClauseRemarks()
    
    /** Sets the Clause Remarks
	 * @param strClauseRemarks The strClauseRemarks to set.
	 */
	public void setClauseRemarks(String strClauseRemarks) {
		this.strClauseRemarks = strClauseRemarks;
	}//end of setClauseRemarks(String strClauseRemarks)

	/** Retrieve the DVMessageYN
	 * @return Returns the strDVMessageYN.
	 */
	public String getDVMessageYN() {
		return strDVMessageYN;
	}//end of getDVMessageYN()

	/** Sets the DVMessageYN
	 * @param strDVMessageYN The strDVMessageYN to set.
	 */
	public void setDVMessageYN(String strDVMessageYN) {
		this.strDVMessageYN = strDVMessageYN;
	}//end of setDVMessageYN(String strDVMessageYN)

	/** Retrieve the EnrolTypeID
	 * @return Returns the strEnrolTypeID.
	 */
	public String getEnrolTypeID() {
		return strEnrolTypeID;
	}//end of getEnrolTypeID()

	/** Sets the EnrolTypeID
	 * @param strEnrolTypeID The strEnrolTypeID to set.
	 */
	public void setEnrolTypeID(String strEnrolTypeID) {
		this.strEnrolTypeID = strEnrolTypeID;
	}//end of setEnrolTypeID(String strEnrolTypeID)

	/** Retrieve the Available Domicilary Treatment Limit
	 * @return Returns the bdAvailDomTrtLimit.
	 */
	public BigDecimal getAvailDomTrtLimit() {
		return bdAvailDomTrtLimit;
	}//end of getAvailDomTrtLimit()

	/** Sets the Available Domicilary Treatment Limit
	 * @param bdAvailDomTrtLimit The bdAvailDomTrtLimit to set.
	 */
	public void setAvailDomTrtLimit(BigDecimal bdAvailDomTrtLimit) {
		this.bdAvailDomTrtLimit = bdAvailDomTrtLimit;
	}//end of setAvailDomTrtLimit(BigDecimal bdAvailDomTrtLimit)

	/** Retrieve the ClaimSubTypeID
	 * @return Returns the strClaimSubTypeID.
	 */
	public String getClaimSubTypeID() {
		return strClaimSubTypeID;
	}//end of getClaimSubTypeID()

	/** Sets the ClaimSubTypeID
	 * @param strClaimSubTypeID The strClaimSubTypeID to set.
	 */
	public void setClaimSubTypeID(String strClaimSubTypeID) {
		this.strClaimSubTypeID = strClaimSubTypeID;
	}//end of setClaimSubTypeID(String strClaimSubTypeID)

	/** Retrieve the BalanceSeqID
	 * @return Returns the lngBalanceSeqID.
	 */
	public Long getBalanceSeqID() {
		return lngBalanceSeqID;
	}//end of getBalanceSeqID()

	/** Sets the BalanceSeqID
	 * @param lngBalanceSeqID The lngBalanceSeqID to set.
	 */
	public void setBalanceSeqID(Long lngBalanceSeqID) {
		this.lngBalanceSeqID = lngBalanceSeqID;
	}//end of setBalanceSeqID(Long lngBalanceSeqID)

	/** Retrieve the CompletedYN
	 * @return Returns the strCompletedYN.
	 */
	public String getCompletedYN() {
		return strCompletedYN;
	}//end of getCompletedYN()

	/** Sets the CompletedYN
	 * @param strCompletedYN The strCompletedYN to set.
	 */
	public void setCompletedYN(String strCompletedYN) {
		this.strCompletedYN = strCompletedYN;
	}//end of setCompletedYN(String strCompletedYN)

	/** Retrieve the Discrepancy Present YN
	 * @return Returns the strDiscPresentYN.
	 */
	public String getDiscPresentYN() {
		return strDiscPresentYN;
	}//end of getDiscPresentYN()

	/** Sets the Discrepancy Present YN
	 * @param strDiscPresentYN The strDiscPresentYN to set.
	 */
	public void setDiscPresentYN(String strDiscPresentYN) {
		this.strDiscPresentYN = strDiscPresentYN;
	}//end of setDiscPresentYN(String strDiscPresentYN)

	/** Retrieve the 
	 * @return Returns the lngBufferHdrSeqID.
	 */
	public Long getBufferHdrSeqID() {
		return lngBufferHdrSeqID;
	}

	/** Sets the 
	 * @param lngBufferHdrSeqID The lngBufferHdrSeqID to set.
	 */
	public void setBufferHdrSeqID(Long lngBufferHdrSeqID) {
		this.lngBufferHdrSeqID = lngBufferHdrSeqID;
	}

	/** Retrieve the PAApprovedAmt
	 * @return Returns the bdPAApprovedAmt.
	 */
	public BigDecimal getPAApprovedAmt() {
		return bdPAApprovedAmt;
	}//end of getPAApprovedAmt()

	/** Sets the PAApprovedAmt
	 * @param bdPAApprovedAmt The bdPAApprovedAmt to set.
	 */
	public void setPAApprovedAmt(BigDecimal bdPAApprovedAmt) {
		this.bdPAApprovedAmt = bdPAApprovedAmt;
	}//end of setPAApprovedAmt(BigDecimal bdPAApprovedAmt)

	/** Retrieve the Max Allowed Amount
	 * @return Returns the bdMaxAllowedAmt.
	 */
	public BigDecimal getMaxAllowedAmt() {
		return bdMaxAllowedAmt;
	}//end of getMaxAllowedAmt()

	/** Sets the Max Allowed Amount
	 * @param bdMaxAllowedAmt The bdMaxAllowedAmt to set.
	 */
	public void setMaxAllowedAmt(BigDecimal bdMaxAllowedAmt) {
		this.bdMaxAllowedAmt = bdMaxAllowedAmt;
	}//end of setMaxAllowedAmt(BigDecimal bdMaxAllowedAmt)

	/** Retrieve the Authorization Permitted By
	 * @return Returns the strAuthPermittedBy.
	 */
	public String getAuthPermittedBy() {
		return strAuthPermittedBy;
	}//end of getAuthPermittedBy()

	/** Sets the Authorization Permitted By
	 * @param strAuthPermittedBy The strAuthPermittedBy to set.
	 */
	public void setAuthPermittedBy(String strAuthPermittedBy) {
		this.strAuthPermittedBy = strAuthPermittedBy;
	}//end of setAuthPermittedBy(String strAuthPermittedBy)

	/** Retrieve the AuthorizedBy
	 * @return Returns the strAuthorizedBy.
	 */
	public String getAuthorizedBy() {
		return strAuthorizedBy;
	}//end of getAuthorizedBy()

	/** Sets the AuthorizedBy
	 * @param strAuthorizedBy The strAuthorizedBy to set.
	 */
	public void setAuthorizedBy(String strAuthorizedBy) {
		this.strAuthorizedBy = strAuthorizedBy;
	}//end of setAuthorizedBy(String strAuthorizedBy)

	/** Retrieve the Authorized Date
	 * @return Returns the dtAuthorizedDate.
	 */
	public Date getAuthorizedDate() {
		return dtAuthorizedDate;
	}//end of getAuthorizedDate()
	
	/** Retrieve the Authorized Date
	 * @return Returns the dtAuthorizedDate.
	 */
	public String getAuthorizedDateTime() {
		return TTKCommon.getFormattedDateHour(dtAuthorizedDate);
	}//end of getAuthorizedDateTime()

	/** Sets the Authorized Date
	 * @param dtAuthorizedDate The dtAuthorizedDate to set.
	 */
	public void setAuthorizedDate(Date dtAuthorizedDate) {
		this.dtAuthorizedDate = dtAuthorizedDate;
	}//end of setAuthorizedDate(Date dtAuthorizedDate)

	/** Retrieve the Authorized Day
	 * @return Returns the strAuthorizedDay.
	 */
	public String getAuthorizedDay() {
		return strAuthorizedDay;
	}//end of getAuthorizedDay()

	/** Sets the Authorized Day
	 * @param strAuthorizedDay The strAuthorizedDay to set.
	 */
	public void setAuthorizedDay(String strAuthorizedDay) {
		this.strAuthorizedDay = strAuthorizedDay;
	}//end of setAuthorizedDay(String strAuthorizedDay)

	/** Retrieve the Authorized Time
	 * @return Returns the strAuthorizedTime.
	 */
	public String getAuthorizedTime() {
		return strAuthorizedTime;
	}//end of getAuthorizedTime()

	/** Sets the Authorized Time
	 * @param strAuthorizedTime The strAuthorizedTime to set.
	 */
	public void setAuthorizedTime(String strAuthorizedTime) {
		this.strAuthorizedTime = strAuthorizedTime;
	}//end of setAuthorizedTime(String strAuthorizedTime)

	/** Retrieve the Buffer Dtl Seq ID
	 * @return Returns the lngBufferDtlSeqID.
	 */
	public Long getBufferDtlSeqID() {
		return lngBufferDtlSeqID;
	}//end of getBufferDtlSeqID()

	/** Sets the Buffer Dtl Seq ID
	 * @param lngBufferDtlSeqID The lngBufferDtlSeqID to set.
	 */
	public void setBufferDtlSeqID(Long lngBufferDtlSeqID) {
		this.lngBufferDtlSeqID = lngBufferDtlSeqID;
	}//end of setBufferDtlSeqID(Long lngBufferDtlSeqID)

	/** Retrieve the Administering Authority
	 * @return Returns the strAdmnAuthority.
	 */
	public String getAdmnAuthority() {
		return strAdmnAuthority;
	}//end of getAdmnAuthority()

	/** Sets the Administering Authority
	 * @param strAdmnAuthority The strAdmnAuthority to set.
	 */
	public void setAdmnAuthority(String strAdmnAuthority) {
		this.strAdmnAuthority = strAdmnAuthority;
	}//end of setAdmnAuthority(String strAdmnAuthority)

	/** Retrieve the Approved Amount
	 * @return Returns the bdApprovedAmount.
	 */
	public BigDecimal getApprovedAmount() {
		return bdApprovedAmount;
	}//end of getApprovedAmount()

	/** Sets the Approved Amount
	 * @param bdApprovedAmount The bdApprovedAmount to set.
	 */
	public void setApprovedAmount(BigDecimal bdApprovedAmount) {
		this.bdApprovedAmount = bdApprovedAmount;
	}//end of setApprovedAmount(BigDecimal bdApprovedAmount)

	/** Retrieve the Available Cumulative Bonus
	 * @return Returns the bdAvailCumBonus.
	 */
	public BigDecimal getAvailCumBonus() {
		return bdAvailCumBonus;
	}//end of getAvailCumBonus()

	/** Sets the Available Cumulative Bonus
	 * @param bdAvailCumBonus The bdAvailCumBonus to set.
	 */
	public void setAvailCumBonus(BigDecimal bdAvailCumBonus) {
		this.bdAvailCumBonus = bdAvailCumBonus;
	}//end of setAvailCumBonus(BigDecimal bdAvailCumBonus)

	/**
     * Retrieve the ApprovedBy
     * @return  strApprovedBy String
     */
    public String getApprovedBy() {
		return strApprovedBy;
	}//end of getApprovedBy()
    
    /**
     * Sets the ApprovedBy
     * @param  strApprovedBy String  
     */
	public void setApprovedBy(String strApprovedBy) {
	this.strApprovedBy = strApprovedBy;
	}//end of setApprovedBy(String strApprovedBy)
	
	/**
     * Retrieve the Reason Type ID
     * @return  strReasonTypeID String
     */
    public String getReasonTypeID() {
        return strReasonTypeID;
    }//end of getReasonTypeID()
    
    /**
     * Sets the Reason Type ID
     * @param  strReasonTypeID String  
     */
    public void setReasonTypeID(String strReasonTypeID) {
        this.strReasonTypeID = strReasonTypeID;
    }//end of setReasonTypeID(String strReasonTypeID)
    
    /**
     * Retrieve the Approved Buffer Amount
     * @return  bdApprovedBufferAmount BigDecimal
     */
    public BigDecimal getApprovedBufferAmount() {
        return bdApprovedBufferAmount;
    }//end of getApprovedBufferAmount()
    
    /**
     * Sets the Approved Buffer Amount
     * @param  bdApprovedBufferAmount BigDecimal  
     */
    public void setApprovedBufferAmount(BigDecimal bdApprovedBufferAmount) {
        this.bdApprovedBufferAmount = bdApprovedBufferAmount;
    }//end of setApprovedBufferAmount(BigDecimal bdApprovedBufferAmount)
    
    /**
     * Retrieve the Authorized Amount
     * @return  bdAuthorizedAmount BigDecimal
     */
    public BigDecimal getAuthorizedAmount() {
        return bdAuthorizedAmount;
    }//end of getAuthorizedAmount()
    
    /**
     * Sets the Authorized Amount
     * @param  bdAuthorizedAmount BigDecimal  
     */
    public void setAuthorizedAmount(BigDecimal bdAuthorizedAmount) {
        this.bdAuthorizedAmount = bdAuthorizedAmount;
    }//end of setAuthorizedAmount(BigDecimal bdAuthorizedAmount)
    
    /**
     * Retrieve the Available Buffer Amount
     * @return  bdAvailBufferAmount BigDecimal
     */
    public BigDecimal getAvailBufferAmount() {
        return bdAvailBufferAmount;
    }//end of getAvailBufferAmount()
    
    /**
     * Sets the Available Buffer Amount
     * @param  bdAvailBufferAmount BigDecimal  
     */
    public void setAvailBufferAmount(BigDecimal bdAvailBufferAmount) {
        this.bdAvailBufferAmount = bdAvailBufferAmount;
    }//end of setAvailBufferAmount(BigDecimal bdAvailBufferAmount)
    
    /**
     * Retrieve the Available SumInsured
     * @return  bdAvailSumInsured BigDecimal
     */
    public BigDecimal getAvailSumInsured() {
        return bdAvailSumInsured;
    }//end of getAvailSumInsured()
    
    /**
     * Sets the Available SumInsured
     * @param  bdAvailSumInsured BigDecimal  
     */
    public void setAvailSumInsured(BigDecimal bdAvailSumInsured) {
        this.bdAvailSumInsured = bdAvailSumInsured;
    }//end of setAvailSumInsured(BigDecimal bdAvailSumInsured)
    
    /**
     * Retrieve the Previos Approved Amount
     * @return  bdPrevApprovedAmount BigDecimal
     */
    public BigDecimal getPrevApprovedAmount() {
        return bdPrevApprovedAmount;
    }//end of getPrevApprovedAmount()
    
    /**
     * Sets the Previos Approved Amount
     * @param  bdPrevApprovedAmount BigDecimal  
     */
    public void setPrevApprovedAmount(BigDecimal bdPrevApprovedAmount) {
        this.bdPrevApprovedAmount = bdPrevApprovedAmount;
    }//end of setPrevApprovedAmount(BigDecimal bdPrevApprovedAmount)
    
    /**
     * Retrieve the Requested Amount
     * @return  bdRequestedAmount BigDecimal
     */
    public BigDecimal getRequestedAmount() {
        return bdRequestedAmount;
    }//end of getRequestedAmount()
    
    /**
     * Sets the Requested Amount
     * @param  bdRequestedAmount BigDecimal  
     */
    public void setRequestedAmount(BigDecimal bdRequestedAmount) {
        this.bdRequestedAmount = bdRequestedAmount;
    }//end of setRequestedAmount(BigDecimal bdRequestedAmount)
    
    /**
     * Retrieve the Total SumInsured
     * @return  bdTotalSumInsured BigDecimal
     */
    public BigDecimal getTotalSumInsured() {
        return bdTotalSumInsured;
    }//end of getTotalSumInsured()
    
    /**
     * Sets the Total SumInsured
     * @param  bdTotalSumInsured BigDecimal  
     */
    public void setTotalSumInsured(BigDecimal bdTotalSumInsured) {
        this.bdTotalSumInsured = bdTotalSumInsured;
    }//end of setTotalSumInsured(BigDecimal bdTotalSumInsured)
    
    /**
     * Retrieve the Approved Date
     * @return  dtApprovedDate Date
     */
    public Date getApprovedDate() {
        return dtApprovedDate;
    }//end of getApprovedDate()
    
    /**
     * Retrieve the Approved Date
     * @return  dtApprovedDate Date
     */
    public String getBufferApprovedDate() {
        return TTKCommon.getFormattedDate(dtApprovedDate);
    }//end of getBufferApprovedDate()
    
    /**
     * Sets the Approved Date
     * @param  dtApprovedDate Date  
     */
    public void setApprovedDate(Date dtApprovedDate) {
        this.dtApprovedDate = dtApprovedDate;
    }//end of setApprovedDate(Date dtApprovedDate)
    
    /**
     * Retrieve the Approved Day
     * @return  strApprovedDay String
     */
    public String getApprovedDay() {
        return strApprovedDay;
    }//end of getApprovedDay()
   
    /**
     * Sets the Approved Day
     * @param  strApprovedDay String  
     */
    public void setApprovedDay(String strApprovedDay) {
        this.strApprovedDay = strApprovedDay;
    }//end of setApprovedDay(String strApprovedDay)setApprovedDay(String strApprovedDay)
    
    /**
     * Retrieve the Approved Time
     * @return  strApprovedTime String
     */
    public String getApprovedTime() {
        return strApprovedTime;
    }//end of getApprovedTime()
    
    /**
     * Sets the Approved Time
     * @param  strApprovedTime String  
     */
    public void setApprovedTime(String strApprovedTime) {
        this.strApprovedTime = strApprovedTime;
    }//end of setApprovedTime(String strApprovedTime)
    
    /**
     * Retrieve the Authorization No
     * @return  strAuthorizationNo String
     */
    public String getAuthorizationNo() {
        return strAuthorizationNo;
    }//end of getAuthorizationNo()
    
    /**
     * Sets the Authorization No
     * @param  strAuthorizationNo String  
     */
    public void setAuthorizationNo(String strAuthorizationNo) {
        this.strAuthorizationNo = strAuthorizationNo;
    }//end of setAuthorizationNo(String strAuthorizationNo)

	/** Retrieve the Copay Amount
	 * @return Returns the bdCopayAmount.
	 */
	public BigDecimal getCopayAmount() {
		return bdCopayAmount;
	}//end of getCopayAmount()

	/** Sets the Copay Amount
	 * @param bdCopayAmount The bdCopayAmount to set.
	 */
	public void setCopayAmount(BigDecimal bdCopayAmount) {
		this.bdCopayAmount = bdCopayAmount;
	}//end of setCopayAmount(BigDecimal bdCopayAmount)

	/** Retrieve the Discount Amount
	 * @return Returns the bdDiscountAmount.
	 */
	public BigDecimal getDiscountAmount() {
		return bdDiscountAmount;
	}//end of getDiscountAmount()

	/** Sets the Discount Amount
	 * @param bdDiscountAmount The bdDiscountAmount to set.
	 */
	public void setDiscountAmount(BigDecimal bdDiscountAmount) {
		this.bdDiscountAmount = bdDiscountAmount;
	}//end of setDiscountAmount(BigDecimal bdDiscountAmount)

	/** Retrieve the ClaimTypeID
	 * @return Returns the strClaimTypeID.
	 */
	public String getClaimTypeID() {
		return strClaimTypeID;
	}//end of getClaimTypeID()

	/** Sets the ClaimTypeID
	 * @param strClaimTypeID The strClaimTypeID to set.
	 */
	public void setClaimTypeID(String strClaimTypeID) {
		this.strClaimTypeID = strClaimTypeID;
	}//end of setClaimTypeID(String strClaimTypeID)
	
	/** Retrieve the RequestedTaxAmt
	 * @return the bdRequestedTaxAmt
	 */
	public BigDecimal getRequestedTaxAmt() {
		return bdRequestedTaxAmt;
	}//end of getRequestedTaxAmt()

	/** Sets the RequestedTaxAmt
	 * @param bdRequestedTaxAmt the bdRequestedTaxAmt to set
	 */
	public void setRequestedTaxAmt(BigDecimal bdRequestedTaxAmt) {
		this.bdRequestedTaxAmt = bdRequestedTaxAmt;
	}//end of setRequestedTaxAmt(BigDecimal bdRequestedTaxAmt)

	/** Retrieve the TaxAmtPaid
	 * @return the bdTaxAmtPaid
	 */
	public BigDecimal getTaxAmtPaid() {
		return bdTaxAmtPaid;
	}//end of getTaxAmtPaid()

	/** Sets the TaxAmtPaid
	 * @param bdTaxAmtPaid the bdTaxAmtPaid to set
	 */
	public void setTaxAmtPaid(BigDecimal bdTaxAmtPaid) {
		this.bdTaxAmtPaid = bdTaxAmtPaid;
	}//end of setTaxAmtPaid(BigDecimal bdTaxAmtPaid)

	/** Retrieve the CalcButDispYN
	 * @return the strCalcButDispYN
	 */
	public String getCalcButDispYN() {
		return strCalcButDispYN;
	}//end of getCalcButDispYN()

	/** Sets the CalcButDispYN 
	 * @param strCalcButDispYN the strCalcButDispYN to set
	 */
	public void setCalcButDispYN(String strCalcButDispYN) {
		this.strCalcButDispYN = strCalcButDispYN;
	}//end of setCalcButDispYN(String strCalcButDispYN)

	/** Retrieve the PressButManYN
	 * @return the strPressButManYN
	 */
	public String getPressButManYN() {
		return strPressButManYN;
	}//end of getPressButManYN()

	/** sets the PressButManYN
	 * @param strPressButManYN the strPressButManYN to set
	 */
	public void setPressButManYN(String strPressButManYN) {
		this.strPressButManYN = strPressButManYN;
	}//end of setPressButManYN(String strPressButManYN)

	/** Retieve the SerTaxCalPer()
	 * @return the bdSerTaxCalPer
	 */
	public BigDecimal getSerTaxCalPer() {
		return bdSerTaxCalPer;
	}//end of getSerTaxCalPer()

	/** sets the SerTaxCalPer
	 * @param bdSerTaxCalPer the bdSerTaxCalPer to set
	 */
	public void setSerTaxCalPer(BigDecimal bdSerTaxCalPer) {
		this.bdSerTaxCalPer = bdSerTaxCalPer;
	}//end of setSerTaxCalPer(BigDecimal bdSerTaxCalPer)
		//added for POlicy Deductable
	 public BigDecimal getBalanceDedAmount() {
			return bdBalanceDedAmount;
	}

	//Mail-SMS for Cigna
    public String getCigna_Ins_Cust() {
		return strCigna_Ins_Cust;
	}

	public void setCigna_Ins_Cust(String strCignaInsCust) {
		strCigna_Ins_Cust = strCignaInsCust;
	}
	//added for CR - KOC Mail-SMS for Cigna
	public void setMailNotifyYN(String strMailNotifyYN) {
		this.strMailNotifyYN = strMailNotifyYN;
	}

	public String getMailNotifyYN() {
		return strMailNotifyYN;
	}
	
	public void setBalanceDedAmount(BigDecimal bdBalanceDedAmount) {
			this.bdBalanceDedAmount = bdBalanceDedAmount;
	}
	public String getPolicy_deductable_yn() {
		return strPolicy_deductable_yn;
	}

	public void setPolicy_deductable_yn(String strPolicyDeductableYn) {
		strPolicy_deductable_yn = strPolicyDeductableYn;
	}

	public void setPreauthDifference(String strPreauthDifference) {
		this.strPreauthDifference = strPreauthDifference;
	}

	public String getPreauthDifference() {
		return strPreauthDifference;
	}

	public void setPreauthYN(String strPreauthYN) {
		this.strPreauthYN = strPreauthYN;
	}

	public String getPreauthYN() {
		return strPreauthYN;
	}


}//end of AuthorizationVO.java
