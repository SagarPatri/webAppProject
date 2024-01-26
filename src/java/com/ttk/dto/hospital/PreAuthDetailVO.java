/** 
 *  @ (#)PreAuthDetailVO.java 
 *  Project       : TTK HealthCare Services
 *  File          :PreAuthDetailVO.java
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dto.hospital;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.ttk.common.TTKCommon;
import com.ttk.dto.claims.ClaimDetailVO;


public class PreAuthDetailVO extends PreAuthVO {
    
    private String strHospitalizedTypeID="";
    private BigDecimal bdPrevApprovedAmount = null;
    private BigDecimal bdClaimRequestAmount = null;
    private BigDecimal bdRequestAmount = null;
    private Date dtAdmissionDate = null;
    private Date dtClmAdmissionDate = null;
    private String strReceivedTime="";
    private String strReceivedDay="";
    private String strAdmissionTime="";
    private String strClmAdmissionTime="";
    private String strAdmissionDay="";
    private String strDoctorName="";
    private String strPreAuthRecvTypeID="";
    private String strInvestigationReqdYN="";
    private String strHospitalPhone="";
    private String strRemarks="";
    private Integer intReviewCount = null;
    private Long lngEventSeqID = null;
    private Integer intRequiredReviewCnt = null;
    private String strReview = "";
    private String strCompletedYN = "";
    private String strProcessCompletedYN = "";
    private String strDataDiscPresentYN = "";
    private String strCloseProximityYN="";
    private String strProximityStatusTypeID = "";
    private Date dtProximityDate = null;
    private String strPreAuthEnhancedYN = "";
    private String strConflictTypeID = "";
    private PreAuthHospitalVO preAuthHospitalVO = null;
    private ClaimantVO claimantVO = null;
    private AdditionalDetailVO additionalDetailVO = null;
    private ClaimDetailVO claimDetailVO = null;
    private String strEventName = "";
    private String strDiscPresentYN = "";
    private BigDecimal bdApprovedAmt = null;
    private String strProcessingBranch = "";
    private String strPreauthTypeDesc = "";
    private Date dtDischargeDate = null;
	private String strDischargeTime = "";
	private String strDischargeDay = "";
	private Long lngHospAssocSeqID = null;
	private ArrayList alPrevClaimList = null;
	private ArrayList alPrevHospList = null;
	private HashMap hmPrevHospDetails = new HashMap();
	private Long lngPrevHospClaimSeqID = null;
	private String strPrevClaimNbr = "";
	private String strCodingReviewYN = "";
	private String strEmailID ="";
	private String strShowCodingOverrideYN = "";
	private String strEnrolChangeMsg = "";
	private String strShowReAssignIDYN="";
	//added for Critical Illness KOC-1273 
	private String strShowCriticalMsg = "";
	private String strUser = "";
	//ended
      
	//koc for griavance
	private String strSeniorCitizenYN = "";
	public void setSeniorCitizenYN(String strSeniorCitizenYN) {
		this.strSeniorCitizenYN = strSeniorCitizenYN;
	}

	public String getSeniorCitizenYN() {
		return strSeniorCitizenYN;
	}
	//koc for griavance
	/** Retrieve the EnrolChangeMsg
	 * @return Returns the strEnrolChangeMsg.
	 */
	public String getEnrolChangeMsg() {
		return strEnrolChangeMsg;
	}//end of getEnrolChangeMsg()

	/** Sets the EnrolChangeMsg
	 * @param strEnrolChangeMsg The strEnrolChangeMsg to set.
	 */
	public void setEnrolChangeMsg(String strEnrolChangeMsg) {
		this.strEnrolChangeMsg = strEnrolChangeMsg;
	}//end of setEnrolChangeMsg(String strEnrolChangeMsg)

	/** Retrieve the ShowCodingOverrideYN
	 * @return Returns the strShowCodingOverrideYN.
	 */
	public String getShowCodingOverrideYN() {
		return strShowCodingOverrideYN;
	}//end of getShowCodingOverrideYN()

	/** Sets the ShowCodingOverrideYN
	 * @param strShowCodingOverrideYN The strShowCodingOverrideYN to set.
	 */
	public void setShowCodingOverrideYN(String strShowCodingOverrideYN) {
		this.strShowCodingOverrideYN = strShowCodingOverrideYN;
	}//end of setShowCodingOverrideYN(String strShowCodingOverrideYN)

	/** Retrieve the EmailID
	 * @return Returns the strEmailID.
	 */
	public String getEmailID() {
		return strEmailID;
	}//end of getEmailID()

	/** Sets the EmailID
	 * @param strEmailID The strEmailID to set.
	 */
	public void setEmailID(String strEmailID) {
		this.strEmailID = strEmailID;
	}//end of setEmailID(String strEmailID)

	/** Retrieve the PrevClaimNbr
	 * @return Returns the strPrevClaimNbr.
	 */
	public String getPrevClaimNbr() {
		return strPrevClaimNbr;
	}//end of getPrevClaimNbr()

	/** Sets the PrevClaimNbr
	 * @param strPrevClaimNbr The strPrevClaimNbr to set.
	 */
	public void setPrevClaimNbr(String strPrevClaimNbr) {
		this.strPrevClaimNbr = strPrevClaimNbr;
	}//end of setPrevClaimNbr(String strPrevClaimNbr)

	/** Retrieve the Claim Admission Date
	 * @return Returns the dtClmAdmissionDate.
	 */
	public Date getClmAdmissionDate() {
		return dtClmAdmissionDate;
	}//end of getClmAdmissionDate()
	
	/** Retrieve the Claim Admission Date
	 * @return Returns the dtClmAdmissionDate.
	 */
	public String getClmHospAdmissionDate() {
		return TTKCommon.getFormattedDate(dtClmAdmissionDate);
	}//end of getClmHospAdmissionDate()

	/** Sets the Claim Admission Date
	 * @param dtClmAdmissionDate The dtClmAdmissionDate to set.
	 */
	public void setClmAdmissionDate(Date dtClmAdmissionDate) {
		this.dtClmAdmissionDate = dtClmAdmissionDate;
	}//end of setClmAdmissionDate(Date dtClmAdmissionDate)

	/** Retrieve the Claim Admission Time
	 * @return Returns the strClmAdmissionTime.
	 */
	public String getClmAdmissionTime() {
		return strClmAdmissionTime;
	}//end of getClmAdmissionTime()

	/** Sets the Claim Admission Time
	 * @param strClmAdmissionTime The strClmAdmissionTime to set.
	 */
	public void setClmAdmissionTime(String strClmAdmissionTime) {
		this.strClmAdmissionTime = strClmAdmissionTime;
	}//end of setClmAdmissionTime(String strClmAdmissionTime)

	/** Retrieve the PrevHospClaimSeqID
	 * @return Returns the lngPrevHospClaimSeqID.
	 */
	public Long getPrevHospClaimSeqID() {
		return lngPrevHospClaimSeqID;
	}//end of getPrevHospClaimSeqID()

	/** Sets the PrevHospClaimSeqID
	 * @param lngPrevHospClaimSeqID The lngPrevHospClaimSeqID to set.
	 */
	public void setPrevHospClaimSeqID(Long lngPrevHospClaimSeqID) {
		this.lngPrevHospClaimSeqID = lngPrevHospClaimSeqID;
	}//end of setPrevHospClaimSeqID(Long lngPrevHospClaimSeqID)

	/** Retrieve the PrevHospDetails
	 * @return Returns the hmPrevHospDetails.
	 */
	public HashMap getPrevHospDetails() {
		return hmPrevHospDetails;
	}//end of getPrevHospDetails()

	/** Sets the PrevHospDetails
	 * @param hmPrevHospDetails The hmPrevHospDetails to set.
	 */
	public void setPrevHospDetails(HashMap hmPrevHospDetails) {
		this.hmPrevHospDetails = hmPrevHospDetails;
	}//end of setPrevHospDetails(HashMap hmPrevHospDetails)

	/** Retrieve the PrevHospList
	 * @return Returns the alPrevHospList.
	 */
	public ArrayList getPrevHospList() {
		return alPrevHospList;
	}//end of getPrevHospList()

	/** Sets the PrevHospList
	 * @param alPrevHospList The alPrevHospList to set.
	 */
	public void setPrevHospList(ArrayList alPrevHospList) {
		this.alPrevHospList = alPrevHospList;
	}//end of setPrevHospList(ArrayList alPrevHospList)

	/** Retrieve the PrevClaimList
	 * @return Returns the alPrevClaimList.
	 */
	public ArrayList getPrevClaimList() {
		return alPrevClaimList;
	}//end of getPrevClaimList()

	/** Sets the PrevClaimList
	 * @param alPrevClaimList The alPrevClaimList to set.
	 */
	public void setPrevClaimList(ArrayList alPrevClaimList) {
		this.alPrevClaimList = alPrevClaimList;
	}//end of setPrevClaimList(ArrayList alPrevClaimList)

	/** Retrieve the Hospital Associate SeqID
	 * @return Returns the lngHospAssocSeqID.
	 */
	public Long getHospAssocSeqID() {
		return lngHospAssocSeqID;
	}//end of getHospAssocSeqID()
	
	/** Sets the Hospital Associate SeqID
	 * @param lngHospAssocSeqID The lngHospAssocSeqID to set.
	 */
	public void setHospAssocSeqID(Long lngHospAssocSeqID) {
		this.lngHospAssocSeqID = lngHospAssocSeqID;
	}//end of setHospAssocSeqID(Long lngHospAssocSeqID)
	
	/** Retrieve the Discharge Date
	 * @return Returns the dtDischargeDate.
	 */
	public Date getDischargeDate() {
		return dtDischargeDate;
	}//end of getDischargeDate()
	
	/** Retrieve the Discharge Date
	 * @return Returns the dtDischargeDate.
	 */
	public String getClaimDischargeDate() {
		return TTKCommon.getFormattedDate(dtDischargeDate);
	}//end of getClaimDischargeDate()

	/** Sets the Discharge Date
	 * @param dtDischargeDate The dtDischargeDate to set.
	 */
	public void setDischargeDate(Date dtDischargeDate) {
		this.dtDischargeDate = dtDischargeDate;
	}//end of setDischargeDate(Date dtDischargeDate)

	/** Retrieve the Discharge Day
	 * @return Returns the strDischargeDay.
	 */
	public String getDischargeDay() {
		return strDischargeDay;
	}//end of getDischargeDay()

	/** Sets the Discharge Day
	 * @param strDischargeDay The strDischargeDay to set.
	 */
	public void setDischargeDay(String strDischargeDay) {
		this.strDischargeDay = strDischargeDay;
	}//end of setDischargeDay(String strDischargeDay)

	/** Retrieve the Discharge Time
	 * @return Returns the strDischargeTime.
	 */
	public String getDischargeTime() {
		return strDischargeTime;
	}//end of getDischargeTime()

	/** Sets the Discharge Time
	 * @param strDischargeTime The strDischargeTime to set.
	 */
	public void setDischargeTime(String strDischargeTime) {
		this.strDischargeTime = strDischargeTime;
	}//end of setDischargeTime(String strDischargeTime)
	
    /** Retrieve the PreauthTypeDesc
	 * @return Returns the strPreauthTypeDesc.
	 */
	public String getPreauthTypeDesc() {
		return strPreauthTypeDesc;
	}//end of getPreauthTypeDesc()

	/** Sets the PreauthTypeDesc
	 * @param strPreauthTypeDesc The strPreauthTypeDesc to set.
	 */
	public void setPreauthTypeDesc(String strPreauthTypeDesc) {
		this.strPreauthTypeDesc = strPreauthTypeDesc;
	}//end of setPreauthTypeDesc(String strPreauthTypeDesc)

	/** Retrieve the ProcessingBranch
	 * @return Returns the strProcessingBranch.
	 */
	public String getProcessingBranch() {
		return strProcessingBranch;
	}//end of getProcessingBranch()

	/** Sets the ProcessingBranch
	 * @param strProcessingBranch The strProcessingBranch to set.
	 */
	public void setProcessingBranch(String strProcessingBranch) {
		this.strProcessingBranch = strProcessingBranch;
	}//end of setProcessingBranch(String strProcessingBranch)

	/** Retrieve the ApprovedAmt
	 * @return Returns the bdApprovedAmt.
	 */
	public BigDecimal getApprovedAmt() {
		return bdApprovedAmt;
	}//end of getApprovedAmt()

	/** Sets the ApprovedAmt
	 * @param bdApprovedAmt The bdApprovedAmt to set.
	 */
	public void setApprovedAmt(BigDecimal bdApprovedAmt) {
		this.bdApprovedAmt = bdApprovedAmt;
	}//end of setApprovedAmt(BigDecimal bdApprovedAmt)

	/** Retrieve the ClaimDetailVO
	 * @return Returns the claimDetailVO.
	 */
	public ClaimDetailVO getClaimDetailVO() {
		return claimDetailVO;
	}//end of getClaimDetailVO()

	/** Sets the ClaimDetailVO
	 * @param claimDetailVO The claimDetailVO to set.
	 */
	public void setClaimDetailVO(ClaimDetailVO claimDetailVO) {
		this.claimDetailVO = claimDetailVO;
	}//end of setClaimDetailVO(ClaimDetailVO claimDetailVO)

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

	/** Retrieve the Event Name
	 * @return Returns the strEventName.
	 */
	public String getEventName() {
		return strEventName;
	}//end of getEventName()

	/** Sets the Event Name
	 * @param strEventName The strEventName to set.
	 */
	public void setEventName(String strEventName) {
		this.strEventName = strEventName;
	}//end of setEventName(String strEventName)

	/** Retrieve the Process Completed YN
	 * @return Returns the strProcessCompletedYN.
	 */
	public String getProcessCompletedYN() {
		return strProcessCompletedYN;
	}//end of 

	/** Sets the Process Completed YN
	 * @param strProcessCompletedYN The strProcessCompletedYN to set.
	 */
	public void setProcessCompletedYN(String strProcessCompletedYN) {
		this.strProcessCompletedYN = strProcessCompletedYN;
	}//end of setProcessCompletedYN(String strProcessCompletedYN)

	/** Retrieve the Conflict Type ID
	 * @return Returns the strConflictTypeID.
	 */
	public String getConflictTypeID() {
		return strConflictTypeID;
	}//end of getConflictTypeID()

	/** Sets the Conflict Type ID
	 * @param strConflictTypeID The strConflictTypeID to set.
	 */
	public void setConflictTypeID(String strConflictTypeID) {
		this.strConflictTypeID = strConflictTypeID;
	}//end of setConflictTypeID(String strConflictTypeID)

	/** Retrieve the Completed YN
	 * @return Returns the strCompletedYN.
	 */
	public String getCompletedYN() {
		return strCompletedYN;
	}//end of getCompletedYN()

	/** Sets the Completed YN
	 * @param strCompletedYN The strCompletedYN to set.
	 */
	public void setCompletedYN(String strCompletedYN) {
		this.strCompletedYN = strCompletedYN;
	}//end of setCompletedYN(String strCompletedYN)

	/** Retrieve the PreAuth Enhanced YN
	 * @return Returns the strPreAuthEnhancedYN.
	 */
	public String getPreAuthEnhancedYN() {
		return strPreAuthEnhancedYN;
	}//end of getPreAuthEnhancedYN()

	/** Sets the PreAuth Enhanced YN
	 * @param strPreAuthEnhancedYN The strPreAuthEnhancedYN to set.
	 */
	public void setPreAuthEnhancedYN(String strPreAuthEnhancedYN) {
		this.strPreAuthEnhancedYN = strPreAuthEnhancedYN;
	}//end of setPreAuthEnhancedYN(String strPreAuthEnhancedYN)

	/** Retrieve the Data Discrepany Present YN
	 * @return Returns the strDataDiscPresentYN.
	 */
	public String getDataDiscPresentYN() {
		return strDataDiscPresentYN;
	}//end of getDataDiscPresentYN()

	/** Sets the Data Discrepancy Present YN
	 * @param strDataDiscPresentYN The strDataDiscPresentYN to set.
	 */
	public void setDataDiscPresentYN(String strDataDiscPresentYN) {
		this.strDataDiscPresentYN = strDataDiscPresentYN;
	}//end of setDataDiscPresentYN(String strDataDiscPresentYN)

	/**
     * Retrieve the Close Proximity indicator
     * @return  strCloseProximityYN String
     */
    public String getCloseProximityYN() {
        return strCloseProximityYN;
    }//end of getCloseProximityYN()
    
    /**
     * Sets the Close Proximity indicator
     * @param  strCloseProximityYN String  
     */
    public void setCloseProximityYN(String strCloseProximityYN) {
        this.strCloseProximityYN = strCloseProximityYN;
    }//end of setCloseProximityYN(String strCloseProximityYN)
    
    /** Retrieve the AdditionalDetailVO
	 * @return Returns the additionalDetailVO.
	 */
	public AdditionalDetailVO getAdditionalDetailVO() {
		return additionalDetailVO;
	}//end of getAdditionalDetailVO()

	/** Sets the AdditionalDetailVO
	 * @param additionalDetailVO The additionalDetailVO to set.
	 */
	public void setAdditionalDetailVO(AdditionalDetailVO additionalDetailVO) {
		this.additionalDetailVO = additionalDetailVO;
	}//end of setAdditionalDetailVO(AdditionalDetailVO additionalDetailVO)
    
    /** Retrieve the Proximity Date
     * @return Returns the dtProximityDate.
     */
    public Date getProximityDate() {
        return dtProximityDate;
    }//end of getProximityDate()
    
    /** Sets the Proximity Date
     * @param dtProximityDate The dtProximityDate to set.
     */
    public void setProximityDate(Date dtProximityDate) {
        this.dtProximityDate = dtProximityDate;
    }//end of setProximityDate(Date dtProximityDate)
    
    /** Retrieve the Proximity Status Type ID
     * @return Returns the strProximityStatusTypeID.
     */
    public String getProximityStatusTypeID() {
        return strProximityStatusTypeID;
    }//end of getProximityStatusTypeID()
    
    /** Sets the Proximity Status Type ID
     * @param strProximityStatusTypeID The strProximityStatusTypeID to set.
     */
    public void setProximityStatusTypeID(String strProximityStatusTypeID) {
        this.strProximityStatusTypeID = strProximityStatusTypeID;
    }//end of setProximityStatusTypeID(String strProximityStatusTypeID)
    
    /** Retrieve the Review
     * @return Returns the strReview.
     */
    public String getReview() {
        return strReview;
    }//end of getReview()
    
    /** Sets the Review
     * @param strReview The strReview to set.
     */
    public void setReview(String strReview) {
        this.strReview = strReview;
    }//end of setReview(String strReview)
    
    /** Retrieve the Required Review Count
     * @return Returns the intRequiredReviewCnt.
     */
    public Integer getRequiredReviewCnt() {
        return intRequiredReviewCnt;
    }//end of getRequiredReviewCnt()
    
    /** Sets the Required Review Count
     * @param intRequiredReviewCnt The intRequiredReviewCnt to set.
     */
    public void setRequiredReviewCnt(Integer intRequiredReviewCnt) {
        this.intRequiredReviewCnt = intRequiredReviewCnt;
    }//end of setRequiredReviewCnt(Integer intRequiredReviewCnt)
    
    /** Retrieve the Review Count
     * @return Returns the intReviewCount.
     */
    public Integer getReviewCount() {
        return intReviewCount;
    }//end of getReviewCount()
    
    /** Sets the Review Count
     * @param intReviewCount The intReviewCount to set.
     */
    public void setReviewCount(Integer intReviewCount) {
        this.intReviewCount = intReviewCount;
    }//end of setReviewCount(Integer intReviewCount)
    
    /** Retriev the Event Seq ID
     * @return Returns the lngEventSeqID.
     */
    public Long getEventSeqID() {
        return lngEventSeqID;
    }//end of getEventSeqID()
    
    /** Sets the Event Seq ID
     * @param lngEventSeqID The lngEventSeqID to set.
     */
    public void setEventSeqID(Long lngEventSeqID) {
        this.lngEventSeqID = lngEventSeqID;
    }//end of setEventSeqID(Long lngEventSeqID)
    
    /**
     * Retrieve the Received Time
     * @return  strReceivedTime String
     */
    public String getReceivedTime() {
        return strReceivedTime;
    }//end of getReceivedTime()
    
    /**
     * Sets the Received Time
     * @param  strReceivedTime String  
     */
    public void setReceivedTime(String strReceivedTime) {
        this.strReceivedTime = strReceivedTime;
    }//end of setReceivedTime(String strReceivedTime)
    
    /**
     * Retrieve the Received Day
     * @return  strReceivedDay String
     */
    public String getReceivedDay() {
        return strReceivedDay;
    }//end of getReceivedDay()
    
    /**
     * Sets the Received Day
     * @param  strReceivedDay String  
     */
    public void setReceivedDay(String strReceivedDay) {
        this.strReceivedDay = strReceivedDay;
    }//end of setReceivedDay(String strReceivedDay)
    
    /**
     * Retrieve the Admission Day
     * @return  strAdmissionDay String
     */
    public String getAdmissionDay() {
        return strAdmissionDay;
    }//end of getAdmissionDay()
    
    /**
     * Sets the Admission Day
     * @param  strAdmissionDay String  
     */
    public void setAdmissionDay(String strAdmissionDay) {
        this.strAdmissionDay = strAdmissionDay;
    }//end of setAdmissionDay(String strAdmissionDay) 
    
    /**
     * Retrieve the Admission Time
     * @return  strAdmissionTime String
     */
    public String getAdmissionTime() {
        return strAdmissionTime;
    }//end of getAdmissionTime() 
    
    /**
     * Sets the Admission Time
     * @param  strAdmissionTime String  
     */
    public void setAdmissionTime(String strAdmissionTime) {
        this.strAdmissionTime = strAdmissionTime;
    }//end of setAdmissionTime(String strAdmissionTime)
    
    /**
     * Retrieve the Previous Approved Amount
     * @return  bdPrevApprovedAmount BigDecimal
     */
    public BigDecimal getPrevApprovedAmount() {
        return bdPrevApprovedAmount;
    }//end of getPrevApprovedAmount()
    
    /**
     * Sets the Previous Approved Amount
     * @param  bdPrevApprovedAmount BigDecimal  
     */
    public void setPrevApprovedAmount(BigDecimal bdPrevApprovedAmount) {
        this.bdPrevApprovedAmount = bdPrevApprovedAmount;
    }//end of setPrevApprovedAmount(BigDecimal bdPrevApprovedAmount)
    
    /**
     * Retrieve the Request Amount
     * @return  bdRequestAmount BigDecimal
     */
    public BigDecimal getRequestAmount() {
        return bdRequestAmount;
    }//end of getRequestAmount()
    
    /**
     * Sets the Request Amount
     * @param  bdRequestAmount BigDecimal  
     */
    public void setRequestAmount(BigDecimal bdRequestAmount) {
        this.bdRequestAmount = bdRequestAmount;
    }//end of setRequestAmount(BigDecimal bdRequestAmount)
    
    /**
     * Retrieve  claimantVO object which contains members information
     * @return  claimantVO ClaimantVO
     */
    public ClaimantVO getClaimantVO() {
        return claimantVO;
    }//end of getClaimantVO()
    
    /**
     * Sets the claimantVO object which contains members information
     * @param  claimantVO ClaimantVO  
     */
    public void setClaimantVO(ClaimantVO claimantVO) {
        this.claimantVO = claimantVO;
    }//end of setClaimantVO(ClaimantVO claimantVO)
    
    /**
     * Retrieve the Admission Date
     * @return  dtAdmissionDate Date
     */
    public Date getAdmissionDate() {
        return dtAdmissionDate;
    }//end of getAdmissionDate()
    
    /**
     * Retrieve the Admission Date
     * @return  dtAdmissionDate Date
     */
    public String getHospitalAdmissionDate() {
        return TTKCommon.getFormattedDate(dtAdmissionDate);
    }//end of getHospitalAdmissionDate()
    
    /**
     * Retrieve the Admission Date
     * @return  dtAdmissionDate Date
     */
    public String getAuthAdmissionDate() {
        return TTKCommon.getFormattedDateHour(dtAdmissionDate);
    }//end of getAuthAdmissionDate()
    
    /**
     * Sets the Admission Date
     * @param  dtAdmissionDate Date  
     */
    public void setAdmissionDate(Date dtAdmissionDate) {
        this.dtAdmissionDate = dtAdmissionDate;
    }//end of setAdmissionDate(Date dtAdmissionDate)
    
    /**
     * Retrieve the preAuthHospitalVO object Which contains 
     * hospital information for which member admitted.
     * @return  preAuthHospitalVO PreAuthHospitalVO
     */
    public PreAuthHospitalVO getPreAuthHospitalVO() {
        return preAuthHospitalVO;
    }//end of getPreAuthHospitalVO()
    
    /**
     * Sets the the preAuthHospitalVO object Which contains 
     * hospital information for which member admitted.
     * @param  preAuthHospitalVO PreAuthHospitalVO  
     */
    public void setPreAuthHospitalVO(PreAuthHospitalVO preAuthHospitalVO) {
        this.preAuthHospitalVO = preAuthHospitalVO;
    }//end of setPreAuthHospitalVO(PreAuthHospitalVO preAuthHospitalVO)
    
    /**
     * Retrieve the Doctor's Name
     * @return  strDoctorName String
     */
    public String getDoctorName() {
        return strDoctorName;
    }//end of getDoctorName()
    
    /**
     * Sets the Doctor's Name
     * @param  strDoctorName String  
     */
    public void setDoctorName(String strDoctorName) {
        this.strDoctorName = strDoctorName;
    }//end of setDoctorName(String strDoctorName)
    
    /**
     * Retrieve the Hospitalized Type ID
     * @return  strHospitalizedTypeID String
     */
    public String getHospitalizedTypeID() {
        return strHospitalizedTypeID;
    }//end of getHospitalizedTypeID()
    
    /**
     * Sets the Hospitalized Type ID
     * @param  strHospitalizedTypeID String  
     */
    public void setHospitalizedTypeID(String strHospitalizedTypeID) {
        this.strHospitalizedTypeID = strHospitalizedTypeID;
    }//end of setHospitalizedTypeID(String strHospitalizedTypeID)
    
    /**
     * Retrieve the InvestigationReqdYN
     * @return  strInvestigationReqdYN String
     */
    public String getInvestigationReqdYN() {
        return strInvestigationReqdYN;
    }//end of getInvestigationReqdYN()
    /**
     * Sets the InvestigationReqdYN
     * @param  strInvestigationReqdYN String  
     */
    public void setInvestigationReqdYN(String strInvestigationReqdYN) {
        this.strInvestigationReqdYN = strInvestigationReqdYN;
    }//end of setInvestigationReqdYN(String strInvestigationReqdYN)
    
    /**
     * Retrieve the Phone NO
     * @return  strHospitalPhone String
     */
    public String getHospitalPhone() {
        return strHospitalPhone;
    }//end of getHospitalPhone()
    
    /**
     * Sets the Phone NO
     * @param  strHospitalPhone String  
     */
    public void setHospitalPhone(String strHospitalPhone) {
        this.strHospitalPhone = strHospitalPhone;
    }//end of setHospitalPhone(String strHospitalPhone)
    
    /**
     * Retrieve the PreAuthReceived Type ID
     * @return  strPreAuthRecvTypeID String
     */
    public String getPreAuthRecvTypeID() {
        return strPreAuthRecvTypeID;
    }//end of getPreAuthRecvTypeID()
    
    /**
     * Sets the PreAuthReceived Type ID
     * @param  strPreAuthRecvTypeID String  
     */
    public void setPreAuthRecvTypeID(String strPreAuthRecvTypeID) {
        this.strPreAuthRecvTypeID = strPreAuthRecvTypeID;
    }//end of setPreAuthRecvTypeID(String strPreAuthRecvTypeID)
    
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

	/** Retrieve the ClaimRequestAmount
	 * @return Returns the bdClaimRequestAmount.
	 */
	public BigDecimal getClaimRequestAmount() {
		return bdClaimRequestAmount;
	}//end of getClaimRequestAmount()

	/** Sets the ClaimRequestAmount
	 * @param bdClaimRequestAmount The bdClaimRequestAmount to set.
	 */
	public void setClaimRequestAmount(BigDecimal bdClaimRequestAmount) {
		this.bdClaimRequestAmount = bdClaimRequestAmount;
	}//end of setClaimRequestAmount(BigDecimal bdClaimRequestAmount)

	/** Retrieve the CodingReviewYN
	 * @return Returns the strCodingReviewYN.
	 */
	public String getCodingReviewYN() {
		return strCodingReviewYN;
	}//end of getCodingReviewYN()

	/** Sets the CodingReviewYN
	 * @param strCodingReviewYN The strCodingReviewYN to set.
	 */
	public void setStrCodingReviewYN(String strCodingReviewYN) {
		this.strCodingReviewYN = strCodingReviewYN;
	}//end of setStrCodingReviewYN(String strCodingReviewYN)
	/** Retrieve the ShowReAssignIDYN
     * @return Returns the strShowReAssignIDYN.
     */
    public String getShowReAssignIDYN() {
        return strShowReAssignIDYN;
    }//end of getShowReAssignIDYN()

    /** Sets the ShowReAssignIDYN
     * @param strShowReAssignIDYN The strShowReAssignIDYN to set.
     */
    public void setShowReAssignIDYN(String strShowReAssignIDYN) {
        this.strShowReAssignIDYN = strShowReAssignIDYN;
    }//end of setShowReAssignIDYN(String strShowReAssignIDYN)
	//added for Document Viewer KOC-1267
	public String getUser() {
		return strUser;
	}

	public void setUser(String strUser) {
		this.strUser = strUser;
	}
	//Ended for Document Viewer
	public void setShowCriticalMsg(String strShowCriticalMsg) {
		this.strShowCriticalMsg = strShowCriticalMsg;
	}

	public String getShowCriticalMsg() {
		return strShowCriticalMsg;
	}
}//end of PreAuthDetailVO