/**
 *  @ (#)PreAuthVO.java Apr 10, 2006
 *  Project       : TTK HealthCare Services
 *  File          : PreAuthVO.java
 *  Author        : Arun K N
 *  Company       : Span Systems Corporation
 *  Date Created  : Apr 10, 2006
 *
 *  @author       :  Arun K N
 *  Modified by   :
 *  Modified date :
 *  Reason        :
 *
 */

package com.ttk.dto.onlineforms;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;


public class BajajAlliannzFormVO extends BaseVO{

    private Long lngPreAuthSeqID = null;
    private Long lngClaimSeqID = null;
    private String strPreAuthNo="";
    private String strHospitalName="";
    private String strEnrollmentID="";
    private String strClaimantName="";
    private String strPolicyNo="";
    private Date dtReceivedDate=null;
    private String strPreAuthTypeID="";
    private String strPriorityTypeID="";
    private String strPriorityImageName="Blank";
    private String strPriorityImageTitle="";
    private String strPreAuthImageName="Blank";
    private String strPreAuthImageTitle="";
    private String strRemarks="";
    private String strAssignedTo="";
    private Long lngOfficeSeqID = null;
    private String strOfficeName = "";
    private Long lngEnrollDtlSeqID = null;
    private String strEnhancedYN = "";
    private Long lngPolicySeqID = null;
    private Long lngInsuranceSeqID = null;
    private Long lngMemberSeqID = null;
    private Long lngParentGenDtlSeqID = null;
    private String strStatusTypeID="";
    private String strEnhanceIconYN = "";
    private String strAssignImageName = "UserIcon";
    private String strAssignImageTitle = "Assign";
    private Long lngAssignUserSeqID = null;
    private String strBufferAllowedYN = "";
    private String strShortfallYN = "";
    private String strReadyToProcessYN = "";
    private String strShortfallImageName="Blank";
    private String strShortfallImageTitle="";
    private String strAdminAuthTypeID = "";
    private String strDMSRefID = "";
    private Date dtClaimAdmnDate = null;
    private Long lngClmEnrollDtlSeqID = null;
    private String strAuthNbr = "";
    private Long lngClmParentSeqID = null;
    private Long lngInwardSeqID = null;
    private String strShowBandYN = "";
    private String strAmmendmentYN = "";
    private String strAssignedImageName="Blank";
    private String strAssignedImageTitle="";
    private String strCoding_review_yn = "";
    private String strRejImageName="Blank";
    private String strRejImageTitle="";
    
    private BigDecimal bdRequestedAmount=null;
    private BigDecimal bdTotalApprovedAmount=null;
    private String strAllowedYN="";
    private String strPreAuthClaimStatus="";
    private Date dtClaimRecommendedDate = null;
    private Date dtHospDate = null;
    private String strSwitchType="";
    private ClaimFormVO claimFormVO=null;
    /**
	 * @param switchType the switchType to set
	 */
	public void setSwitchType(String switchType) {
		strSwitchType = switchType;
	}

	/**
	 * @return the switchType
	 */
	public String getSwitchType() {
		return strSwitchType;
	}

	/**
	 * @param preAuthClaimStatus the preAuthClaimStatus to set
	 */
	public void setPreAuthClaimStatus(String preAuthClaimStatus) {
		strPreAuthClaimStatus = preAuthClaimStatus;
	}

	/**
	 * @return the preAuthClaimStatus
	 */
	public String getPreAuthClaimStatus() {
		return strPreAuthClaimStatus;
	}

	/**
	 * @param hospDate the hospDate to set
	 */
	public void setHospDate(Date hospDate) {
		dtHospDate = hospDate;
	}

	/**
	 * @return the hospDate
	 */
	public String getHospDate() {
		return TTKCommon.getFormattedDateHour(dtHospDate);
	}

	/**
	 * @param claimRecommendedDate the claimRecommendedDate to set
	 */
	public void setClaimRecommendedDate(Date claimRecommendedDate) {
		dtClaimRecommendedDate = claimRecommendedDate;
	}

	/**
	 * @return the claimRecommendedDate
	 */
	public String getClaimRecommendedDate() {
		return TTKCommon.getFormattedDateHour(dtClaimRecommendedDate);
	}

	
	/**
	 * @param allowedYN the allowedYN to set
	 */
	public void setAllowedYN(String allowedYN) {
		strAllowedYN = allowedYN;
	}

	/**
	 * @return the allowedYN
	 */
	public String getAllowedYN() {
		return strAllowedYN;
	}

	/**
	 * @param totalApprovedAmount the totalApprovedAmount to set
	 */
	public void setTotalApprovedAmount(BigDecimal totalApprovedAmount) {
		bdTotalApprovedAmount = totalApprovedAmount;
	}

	/**
	 * @return the totalApprovedAmount
	 */
	public BigDecimal getTotalApprovedAmount() {
		return bdTotalApprovedAmount;
	}

	/**
	 * @param requestedAmount the requestedAmount to set
	 */
	public void setRequestedAmount(BigDecimal requestedAmount) {
		bdRequestedAmount = requestedAmount;
	}

	/**
	 * @return the requestedAmount
	 */
	public BigDecimal getRequestedAmount() {
		return bdRequestedAmount;
	}

	/** Retrieve the RejImageName
	 * @return Returns the strRejImageName.
	 */
	public String getRejImageName() {
		return strRejImageName;
	}//end of getRejImageName()

	/** Sets the RejImageName
	 * @param strRejImageName The strRejImageName to set.
	 */
	public void setRejImageName(String strRejImageName) {
		this.strRejImageName = strRejImageName;
	}//end of setRejImageName(String strRejImageName)

	/** Retrieve the RejImageTitle
	 * @return Returns the strRejImageTitle.
	 */
	public String getRejImageTitle() {
		return strRejImageTitle;
	}//end of getRejImageTitle()

	/** Sets the RejImageTitle
	 * @param strRejImageTitle The strRejImageTitle to set.
	 */
	public void setRejImageTitle(String strRejImageTitle) {
		this.strRejImageTitle = strRejImageTitle;
	}//end of setRejImageTitle(String strRejImageTitle)

	/** Retrieve the Coding_review_yn YN
	 * @return Returns the strCoding_review_yn.
	 */
	public String getCoding_review_yn() {
		return strCoding_review_yn;
	}//end of getCoding_review_yn()

	/** Sets the strCoding_review_yn YN
	 * @param strCoding_review_yn The strCoding_review_yn to set.
	 */
	public void setCoding_review_yn(String strCoding_review_yn) {
		this.strCoding_review_yn = strCoding_review_yn;
	}//end of setCoding_review_yn(String strCoding_review_yn)
    
    
    /** Retrieve the Assigned Image Name
     * @return Returns the strAssignedImageName.
     */
    public String getAssignedImageName() {
        return strAssignedImageName;
    }//end of getAssignedImageName()

    /** Sets the Assigned Image Name
     * @param strAssignedImageName The strAssignedImageName to set.
     */
    public void setAssignedImageName(String strAssignedImageName) {
        this.strAssignedImageName = strAssignedImageName;
    }//end of setAssignedImageName(String strAssignedImageName)
    
    /** Retrieve the Image Title
     * @return Returns the strAssignedImageTitle.
     */
    public String getAssignedImageTitle() {
        return strAssignedImageTitle;
    }//end of getAssignedImageTitle()

    /** Sets the Image Title
     * @param strAssignedImageTitle The strAssignedImageTitle to set.
     */
    public void setAssignedImageTitle(String strAssignedImageTitle) {
        this.strAssignedImageTitle = strAssignedImageTitle;
    }//end of setAssignedImageTitle(String strAssignedImageTitle)
    

    /** Retrieve the AmmendmentYN
     * @return Returns the strAmmendmentYN.
     */
    public String getAmmendmentYN() {
        return strAmmendmentYN;
    }//end of getAmmendmentYN()

    /** Sets the AmmendmentYN
     * @param strAmmendmentYN The strAmmendmentYN to set.
     */
    public void setAmmendmentYN(String strAmmendmentYN) {
        this.strAmmendmentYN = strAmmendmentYN;
    }//end of setAmmendmentYN(String strAmmendmentYN)
    /** Retrieve the
	 * @return Returns the strShowBandYN.
	 */
	public String getShowBandYN() {
		return strShowBandYN;
	}

	/** Sets the
	 * @param strShowBandYN The strShowBandYN to set.
	 */
	public void setShowBandYN(String strShowBandYN) {
		this.strShowBandYN = strShowBandYN;
	}

	/** Retrieve the InwardSeqID
	 * @return Returns the lngInwardSeqID.
	 */
	public Long getInwardSeqID() {
		return lngInwardSeqID;
	}//end of getInwardSeqID()

	/** Sets the InwardSeqID
	 * @param lngInwardSeqID The lngInwardSeqID to set.
	 */
	public void setInwardSeqID(Long lngInwardSeqID) {
		this.lngInwardSeqID = lngInwardSeqID;
	}//end of setInwardSeqID(Long lngInwardSeqID)

	/** Retrieve the ClmParentSeqID
	 * @return Returns the lngClmParentSeqID.
	 */
	public Long getClmParentSeqID() {
		return lngClmParentSeqID;
	}//end of getClmParentSeqID()

	/** Sets the ClmParentSeqID
	 * @param lngClmParentSeqID The lngClmParentSeqID to set.
	 */
	public void setClmParentSeqID(Long lngClmParentSeqID) {
		this.lngClmParentSeqID = lngClmParentSeqID;
	}//end of setClmParentSeqID(Long lngClmParentSeqID)

	/** Retrieve the Authorization Number
	 * @return Returns the strAuthNbr.
	 */
	public String getAuthNbr() {
		return strAuthNbr;
	}//end of getAuthNbr()

	/** Sets the Authorization Number
	 * @param strAuthNbr The strAuthNbr to set.
	 */
	public void setAuthNbr(String strAuthNbr) {
		this.strAuthNbr = strAuthNbr;
	}//end of setAuthNbr(String strAuthNbr)

    /** Retrieve the ClmEnrollDtlSeqID
	 * @return Returns the lngClmEnrollDtlSeqID.
	 */
	public Long getClmEnrollDtlSeqID() {
		return lngClmEnrollDtlSeqID;
	}//end of getClmEnrollDtlSeqID()

	/** Sets the ClmEnrollDtlSeqID
	 * @param lngClmEnrollDtlSeqID The lngClmEnrollDtlSeqID to set.
	 */
	public void setClmEnrollDtlSeqID(Long lngClmEnrollDtlSeqID) {
		this.lngClmEnrollDtlSeqID = lngClmEnrollDtlSeqID;
	}//end of setClmEnrollDtlSeqID(Long lngClmEnrollDtlSeqID)

	/** Retrieve the ClaimSeqID
	 * @return Returns the lngClaimSeqID.
	 */
	public Long getClaimSeqID() {
		return lngClaimSeqID;
	}//end of getClaimSeqID()

	/** Sets the ClaimSeqID
	 * @param lngClaimSeqID The lngClaimSeqID to set.
	 */
	public void setClaimSeqID(Long lngClaimSeqID) {
		this.lngClaimSeqID = lngClaimSeqID;
	}//end of setClaimSeqID(Long lngClaimSeqID)

	/** Retrieve the Claim Admission Date
	 * @return Returns the dtClaimAdmnDate.
	 */
	public String getClaimAdmnDate() {
		return TTKCommon.getFormattedDateHour(dtClaimAdmnDate);
	}//end of getClaimAdmnDate()

	/** Retrieve the Claim Admission Date
	 * @return Returns the dtClaimAdmnDate.
	 */
	public String getClaimAdmissionDate() {
		return TTKCommon.getFormattedDateHour(dtClaimAdmnDate);
	}//end of getClaimAdmissionDate()
	
	/** Retrieve the Claim Admission Date
	 * @return Returns the dtClaimAdmnDate.
	 */
	public String getClaimAdmissionDateTime() {
		return TTKCommon.getFormattedDateHour(dtClaimAdmnDate);
	}//end of getClaimAdmissionDateTime()

	/** Sets the Claim Admission Date
	 * @param dtClaimAdmnDate The dtClaimAdmnDate to set.
	 */
	public void setClaimAdmnDate(Date dtClaimAdmnDate) {
		this.dtClaimAdmnDate = dtClaimAdmnDate;
	}//end of setClaimAdmnDate(Date dtClaimAdmnDate)

	/** Retrieve the PreauthDmsRefID
	 * @return Returns the strPreauthDmsRefID.
	 */
	public String getDMSRefID() {
		return strDMSRefID;
	}//end of getPreauthDmsRefID()

	/** Sets the PreauthDmsRefID
	 * @param strPreauthDmsRefID The strPreauthDmsRefID to set.
	 */
	public void setDMSRefID(String strDMSRefID) {
		this.strDMSRefID = strDMSRefID;
	}//end of setPreauthDmsRefID(String strDMSRefID)

	/** Retrieve the Admin Auth Type ID
	 * @return Returns the strAdminAuthTypeID.
	 */
	public String getAdminAuthTypeID() {
		return strAdminAuthTypeID;
	}//end of getAdminAuthTypeID()

	/** Sets the Admin Auth Type ID
	 * @param strAdminAuthTypeID The strAdminAuthTypeID to set.
	 */
	public void setAdminAuthTypeID(String strAdminAuthTypeID) {
		this.strAdminAuthTypeID = strAdminAuthTypeID;
	}//end of setAdminAuthTypeID(String strAdminAuthTypeID)

    /** Retrieve the Ready To Process YN
	 * @return Returns the strReadyToProcessYN.
	 */
	public String getReadyToProcessYN() {
		return strReadyToProcessYN;
	}//end of getReadyToProcessYN()

	/** Sets the Ready To Process YN
	 * @param strReadyToProcessYN The strReadyToProcessYN to set.
	 */
	public void setReadyToProcessYN(String strReadyToProcessYN) {
		this.strReadyToProcessYN = strReadyToProcessYN;
	}//end of setReadyToProcessYN(String strReadyToProcessYN)

	/** Retrieve the Shortfall Image Name
	 * @return Returns the strShortfallImageName.
	 */
	public String getShortfallImageName() {
		return strShortfallImageName;
	}//end of getShortfallImageName()

	/** Sets the Shortfall Image Name
	 * @param strShortfallImageName The strShortfallImageName to set.
	 */
	public void setShortfallImageName(String strShortfallImageName) {
		this.strShortfallImageName = strShortfallImageName;
	}//end of setShortfallImageName(String strShortfallImageName)

	/** Retrieve the Shortfall Image Title
	 * @return Returns the strShortfallImageTitle.
	 */
	public String getShortfallImageTitle() {
		return strShortfallImageTitle;
	}//end of getShortfallImageTitle()

	/** Sets the Shortfall Image Title
	 * @param strShortfallImageTitle The strShortfallImageTitle to set.
	 */
	public void setShortfallImageTitle(String strShortfallImageTitle) {
		this.strShortfallImageTitle = strShortfallImageTitle;
	}//end of setShortfallImageTitle(String strShortfallImageTitle)

	/** Retrieve the Shortfall YN
	 * @return Returns the strShortfallYN.
	 */
	public String getShortfallYN() {
		return strShortfallYN;
	}//end of getShortfallYN()

	/** Sets the Shortfall YN
	 * @param strShortfallYN The strShortfallYN to set.
	 */
	public void setShortfallYN(String strShortfallYN) {
		this.strShortfallYN = strShortfallYN;
	}//end of setShortfallYN(String strShortfallYN)

	/** Retrieve the Buffer Allowed YN.
	 * @return Returns the strBufferAllowedYN.
	 */
	public String getBufferAllowedYN() {
		return strBufferAllowedYN;
	}//end of getBufferAllowedYN()

	/** Sets the Buffer Allowed YN.
	 * @param strBufferAllowedYN The strBufferAllowedYN to set.
	 */
	public void setBufferAllowedYN(String strBufferAllowedYN) {
		this.strBufferAllowedYN = strBufferAllowedYN;
	}//end of setBufferAllowedYN(String strBufferAllowedYN)

    /** Retrieve the Assign User Seq ID
	 * @return Returns the lngAssignUserSeqID.
	 */
	public Long getAssignUserSeqID() {
		return lngAssignUserSeqID;
	}//end of getAssignUserSeqID()

	/** Sets the Assign User Seq ID
	 * @param lngAssignUserSeqID The lngAssignUserSeqID to set.
	 */
	public void setAssignUserSeqID(Long lngAssignUserSeqID) {
		this.lngAssignUserSeqID = lngAssignUserSeqID;
	}//end of setAssignUserSeqID(Long lngAssignUserSeqID)

	/** Retrieve the Assign Image Name
	 * @return Returns the strAssignImageName.
	 */
	public String getAssignImageName() {
		return strAssignImageName;
	}//end of getAssignImageName()

	/** Sets the Assign Image Name
	 * @param strAssignImageName The strAssignImageName to set.
	 */
	public void setAssignImageName(String strAssignImageName) {
		this.strAssignImageName = strAssignImageName;
	}//end of setAssignImageName(String strAssignImageName)

	/** Retrieve the Assign Image Title
	 * @return Returns the strAssignImageTitle.
	 */
	public String getAssignImageTitle() {
		return strAssignImageTitle;
	}//end of getAssignImageTitle()

	/** Sets the Assign Image Title
	 * @param strAssignImageTitle The strAssignImageTitle to set.
	 */
	public void setAssignImageTitle(String strAssignImageTitle) {
		this.strAssignImageTitle = strAssignImageTitle;
	}//end of setAssignImageTitle(String strAssignImageTitle)

	/** Retrieve the Enhance Icon YN
	 * @return Returns the strEnhanceIconYN.
	 */
	public String getEnhanceIconYN() {
		return strEnhanceIconYN;
	}//end of getEnhanceIconYN()

	/** Sets the Enhance Icon YN
	 * @param strEnhanceIconYN The strEnhanceIconYN to set.
	 */
	public void setEnhanceIconYN(String strEnhanceIconYN) {
		this.strEnhanceIconYN = strEnhanceIconYN;
	}//end of setEnhanceIconYN(String strEnhanceIconYN)

	/**
     * Retrieve the Status Type ID
     * @return  strStatusTypeID String
     */
    public String getStatusTypeID() {
        return strStatusTypeID;
    }//end of getStatusTypeID()

    /**
     * Sets the Status Type ID
     * @param  strStatusTypeID String
     */
    public void setStatusTypeID(String strStatusTypeID) {
        this.strStatusTypeID = strStatusTypeID;
    }//end of setStatusTypeID(String strStatusTypeID)

    /** Retrieve the Enroll Detail Seq ID
	 * @return Returns the lngEnrollDtlSeqID.
	 */
	public Long getEnrollDtlSeqID() {
		return lngEnrollDtlSeqID;
	}//end of getEnrollDtlSeqID()

	/** Sets the Enroll Detail Seq ID
	 * @param lngEnrollDtlSeqID The lngEnrollDtlSeqID to set.
	 */
	public void setEnrollDtlSeqID(Long lngEnrollDtlSeqID) {
		this.lngEnrollDtlSeqID = lngEnrollDtlSeqID;
	}//end of setEnrollDtlSeqID(Long lngEnrollDtlSeqID)

	/** Retrieve the Insurance Seq ID
	 * @return Returns the lngInsuranceSeqID.
	 */
	public Long getInsuranceSeqID() {
		return lngInsuranceSeqID;
	}//end of getInsuranceSeqID()

	/** Sets the Insurance Seq ID
	 * @param lngInsuranceSeqID The lngInsuranceSeqID to set.
	 */
	public void setInsuranceSeqID(Long lngInsuranceSeqID) {
		this.lngInsuranceSeqID = lngInsuranceSeqID;
	}//end of setInsuranceSeqID(Long lngInsuranceSeqID)

	/** Retrieve the Member Seq ID
	 * @return Returns the lngMemberSeqID.
	 */
	public Long getMemberSeqID() {
		return lngMemberSeqID;
	}//end of getMemberSeqID()

	/** Sets the Member Seq ID
	 * @param lngMemberSeqID The lngMemberSeqID to set.
	 */
	public void setMemberSeqID(Long lngMemberSeqID) {
		this.lngMemberSeqID = lngMemberSeqID;
	}//end of setMemberSeqID(Long lngMemberSeqID)

	/** Retrieve the Parent Gen Dtl SeqID
	 * @return Returns the lngParentGenDtlSeqID.
	 */
	public Long getParentGenDtlSeqID() {
		return lngParentGenDtlSeqID;
	}//end of getParentGenDtlSeqID()

	/** Sets the Parent Gen Dtl SeqID
	 * @param lngParentGenDtlSeqID The lngParentGenDtlSeqID to set.
	 */
	public void setParentGenDtlSeqID(Long lngParentGenDtlSeqID) {
		this.lngParentGenDtlSeqID = lngParentGenDtlSeqID;
	}//end of setParentGenDtlSeqID(Long lngParentGenDtlSeqID)

	/** Retrieve the Policy Seq ID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()

	/** Sets the Policy Seq ID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)

	/** Retrieve the Enhanced YN
	 * @return Returns the strEnhancedYN.
	 */
	public String getEnhancedYN() {
		return strEnhancedYN;
	}//end of getEnhancedYN()

	/** Sets the Enhanced YN
	 * @param strEnhancedYN The strEnhancedYN to set.
	 */
	public void setEnhancedYN(String strEnhancedYN) {
		this.strEnhancedYN = strEnhancedYN;
	}//end of setEnhancedYN(String strEnhancedYN)

	/** Retrieve the Office Seq ID
	 * @return Returns the lngOfficeSeqID.
	 */
	public Long getOfficeSeqID() {
		return lngOfficeSeqID;
	}//end of getOfficeSeqID()

	/** Sets the Office Seq ID
	 * @param lngOfficeSeqID The lngOfficeSeqID to set.
	 */
	public void setOfficeSeqID(Long lngOfficeSeqID) {
		this.lngOfficeSeqID = lngOfficeSeqID;
	}//end of setOfficeSeqID(Long lngOfficeSeqID)

	/** Retrieve the Office Name
	 * @return Returns the strOfficeName.
	 */
	public String getOfficeName() {
		return strOfficeName;
	}//end of getOfficeName()

	/** Sets the Office Name
	 * @param strOfficeName The strOfficeName to set.
	 */
	public void setOfficeName(String strOfficeName) {
		this.strOfficeName = strOfficeName;
	}//end of setOfficeName(String strOfficeName)

	/**
     * Retrieve the Assigned To
     * @return  strAssignedTo String
     */
    public String getAssignedTo() {
        return strAssignedTo;
    }//end of getAssignedTo()

    /**
     * Sets the Assigned To
     * @param  strAssignedTo String
     */
    public void setAssignedTo(String strAssignedTo) {
        this.strAssignedTo = strAssignedTo;
    }//end of setAssignedTo(String strAssignedTo)

    /**
     * Retrieve the Remarks
     * @return  strRemarks String
     */
    public String getRemarks() {
        return strRemarks;
    }//end of getRemarks()

    /**
     * Sets the Remarks
     * @param  strRemarks String
     */
    public void setRemarks(String strRemarks) {
        this.strRemarks = strRemarks;
    }//end of setRemarks(String strRemarks)

    /**
     * Retrieve the Received Date
     * @return  dtReceivedDate Date
     */
    public Date getReceivedDate() {
        return dtReceivedDate;
    }//end of getReceivedDate()

    /**
     * Retrieve the Received Date
     * @return  dtReceivedDate Date
     */
    public String getPreAuthReceivedDate() {
        return TTKCommon.getFormattedDateHour(dtReceivedDate);
    }//end of getReceivedDate()

    /**
     * Retrieve the Received Date
     * @return  dtReceivedDate Date
     */
    public String getPATReceivedDate() {
        return TTKCommon.getFormattedDateHour(dtReceivedDate);
    }//end of getPATReceivedDate()

    /**
     * Sets the Received Date
     * @param  dtReceivedDate Date
     */
    public void setReceivedDate(Date dtReceivedDate) {
        this.dtReceivedDate = dtReceivedDate;
    }//end of setReceivedDate(Date dtReceivedDate)

    /**
     * Retrieve the PreAuth Seq ID
     * @return  lngPreAuthSeqID Long
     */
    public Long getPreAuthSeqID() {
        return lngPreAuthSeqID;
    }//end of getPreAuthSeqID()

    /**
     * Sets the PreAuth Seq ID
     * @param  lngPreAuthSeqID Long
     */
    public void setPreAuthSeqID(Long lngPreAuthSeqID) {
        this.lngPreAuthSeqID = lngPreAuthSeqID;
    }//end of setPreAuthSeqID

    /**
     * Retrieve the Claimant Name
     * @return  strClaimantName String
     */
    public String getClaimantName() {
        return strClaimantName;
    }//end of getClaimantName()

    /**
     * Sets the Claimant Name
     * @param  strClaimantName String
     */
    public void setClaimantName(String strClaimantName) {
        this.strClaimantName = strClaimantName;
    }//end of  setClaimantName(String strClaimantName)

    /**
     * Retrieve the Enrollment ID
     * @return  strEnrollmentID String
     */
    public String getEnrollmentID() {
        return strEnrollmentID;
    }//end of getEnrollmentID()

    /**
     * Sets the Enrollment ID
     * @param  strEnrollmentID String
     */
    public void setEnrollmentID(String strEnrollmentID) {
        this.strEnrollmentID = strEnrollmentID;
    }//end of setEnrollmentID(String strEnrollmentID)

    /**
     * Retrieve the Hospital Name
     * @return  strHospitalName String
     */
    public String getHospitalName() {
        return strHospitalName;
    }//end of getHospitalName()

    /**
     * Sets the Hospital Name
     * @param  strHospitalName String
     */
    public void setHospitalName(String strHospitalName) {
        this.strHospitalName = strHospitalName;
    }//end of setHospitalName(String strHospitalName)

    /**
     * Retrieve the Policy No
     * @return  strPolicyNo String
     */
    public String getPolicyNo() {
        return strPolicyNo;
    }//end of getPolicyNo()

    /**
     * Sets the Policy No
     * @param  strPolicyNo String
     */
    public void setPolicyNo(String strPolicyNo) {
        this.strPolicyNo = strPolicyNo;
    }//end of setPolicyNo(String strPolicyNo)

    /**
     * Retrieve the PreAuth Image Name
     * @return  strPreAuthImageName String
     */
    public String getPreAuthImageName() {
        return strPreAuthImageName;
    }//end of getPreAuthImageName()

    /**
     * Sets the PreAuth Image Name
     * @param  strPreAuthImageName String
     */
    public void setPreAuthImageName(String strPreAuthImageName) {
        this.strPreAuthImageName = strPreAuthImageName;
    }//end of setPreAuthImageName(String strPreAuthImageName)

    /**
     * Retrieve the PreAuth Image Title
     * @return  strPreAuthImageTitle String
     */
    public String getPreAuthImageTitle() {
        return strPreAuthImageTitle;
    }//end of getPreAuthImageTitle()

    /**
     * Sets the PreAuth Image Title
     * @param  strPreAuthImageTitle String
     */
    public void setPreAuthImageTitle(String strPreAuthImageTitle) {
        this.strPreAuthImageTitle = strPreAuthImageTitle;
    }//end of setPreAuthImageTitle(String strPreAuthImageTitle)

    /**
     * Retrieve the PreAuth No
     * @return  strPreAuthNo String
     */
    public String getPreAuthNo() {
        return strPreAuthNo;
    }//end of getPreAuthNo()

    /**
     * Sets the PreAuth No
     * @param  strPreAuthNo String
     */
    public void setPreAuthNo(String strPreAuthNo) {
        this.strPreAuthNo = strPreAuthNo;
    }//end of setPreAuthNo(String strPreAuthNo)

    /**
     * Retrieve the PreAuth Type ID
     * @return  strPreAuthTypeID String
     */
    public String getPreAuthTypeID() {
        return strPreAuthTypeID;
    }//end of getPreAuthTypeID()

    /**
     * Sets the PreAuth Type ID
     * @param  strPreAuthTypeID String
     */
    public void setPreAuthTypeID(String strPreAuthTypeID) {
        this.strPreAuthTypeID = strPreAuthTypeID;
    }//end of setPreAuthTypeID(String strPreAuthTypeID)

    /**
     * Retrieve the Priority Image Name
     * @return  strPriorityImageName String
     */
    public String getPriorityImageName() {
        return strPriorityImageName;
    }//end of getPriorityImageName()

    /**
     * Sets the Priority Image Name
     * @param  strPriorityImageName String
     */
    public void setPriorityImageName(String strPriorityImageName) {
        this.strPriorityImageName = strPriorityImageName;
    }//end of setPriorityImageName(String strPriorityImageName)

    /**
     * Retrieve the Priority Image Title
     * @return  strPriorityImageTitle String
     */
    public String getPriorityImageTitle() {
        return strPriorityImageTitle;
    }//end of getPriorityImageTitle()

    /**
     * Sets the Priority Image Title
     * @param  strPriorityImageTitle String
     */
    public void setPriorityImageTitle(String strPriorityImageTitle) {
        this.strPriorityImageTitle = strPriorityImageTitle;
    }//end of setPriorityImageTitle(String strPriorityImageTitle)

    /**
     * Retrieve the Priority Type ID
     * @return  strPriorityTypeID String
     */
    public String getPriorityTypeID() {
        return strPriorityTypeID;
    }//end of getPriorityTypeID()

    /**
     * Sets the Priority Type ID
     * @param  strPriorityTypeID String
     */
    public void setPriorityTypeID(String strPriorityTypeID) {
        this.strPriorityTypeID = strPriorityTypeID;
    }//end of setPriorityTypeID(String strPriorityTypeID)

	/**
	 * @param claimFormVO the claimFormVO to set
	 */
	public void setClaimFormVO(ClaimFormVO claimFormVO) {
		this.claimFormVO = claimFormVO;
	}

	/**
	 * @return the claimFormVO
	 */
	public ClaimFormVO getClaimFormVO() {
		return claimFormVO;
	}
}//end of PreAuthVO.java