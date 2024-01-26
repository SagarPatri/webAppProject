/**
 * @ (#) PEDVO.java Feb 6, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PEDVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 6, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.enrollment;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;


public class PEDVO extends BaseVO {
    
    private Long lngMemberSeqID = null;
    private Long lngPEDSeqID = null;
    private Long lngSeqID = null;//used in Preauth for MEM_PED_SEQ_ID/CLAIMANT_PED_SEQ_ID
    private String strPEDType = "";
    private String strEditYN = "";
    private Long lngPEDCodeID = null;
    private String strDuration = "";
    private String strDescription = "";
    private String strRemarks = "";
    private String strICDCode = "";
    private String strMemberName = "";
    private String strOtherDesc = "";
    private ArrayList alDescriptionList = null;
    private String strRuleAssociateYN = "";
    private Integer intPEDCount = null;
    private Integer intDurationYears = null;
    private Integer intDurationMonths = null;
    private String strDurationYrMonth = "";
    private String strPolicyNbr = "";
    //added for koc 1278
    private String strPersonalWaitingPeriod = "";
    private String strWaitingPeriod = "";
    private String strAilmentTypeID = "";
    private String strPersWtPeriod = "";
    
    /**
	 * @param strPersWtPeriod the strPersWtPeriod to set
	 */
	public void setPersWtPeriod(String strPersWtPeriod) {
		this.strPersWtPeriod = strPersWtPeriod;
	}

	/**
	 * @return the persWtPeriod
	 */
	public String getPersWtPeriod() {
		return strPersWtPeriod;
	}

	/**
	 * @param waitingPeriod the waitingPeriod to set
	 */
	public void setWaitingPeriod(String strWaitingPeriod) {
		this.strWaitingPeriod = strWaitingPeriod;
	}

	/**
	 * @return the waitingPeriod
	 */
	public String getWaitingPeriod() {
		return strWaitingPeriod;
	}

	/**
	 * @param strAilmentTypeID the strAilmentTypeID to set
	 */
	public void setAilmentTypeID(String strAilmentTypeID) {
		this.strAilmentTypeID = strAilmentTypeID;
	}

	/**
	 * @return the ailmentTypeID
	 */
	public String getAilmentTypeID() {
		return strAilmentTypeID;
	}

	/**
	 * @param personalWaitingPeriod the personalWaitingPeriod to set
	 */
	public void setPersonalWaitingPeriod(String strPersonalWaitingPeriod) {
		this.strPersonalWaitingPeriod = strPersonalWaitingPeriod;
	}

	/**
	 * @return the personalWaitingPeriod
	 */
	public String getPersonalWaitingPeriod() {
		return strPersonalWaitingPeriod;
	}
    //added for koc 1278    
    
    /** Retrieve the PolicyNbr
	 * @return Returns the strPolicyNbr.
	 */
	public String getPolicyNbr() {
		return strPolicyNbr;
	}//end of getPolicyNbr()

	/** Sets the PolicyNbr
	 * @param strPolicyNbr The strPolicyNbr to set.
	 */
	public void setPolicyNbr(String strPolicyNbr) {
		this.strPolicyNbr = strPolicyNbr;
	}//end of setPolicyNbr(String strPolicyNbr)

	/** Retrieve the DurationYrMonth
	 * @return Returns the strDurationYrMonth.
	 */
	public String getDurationYrMonth() {
		return strDurationYrMonth;
	}//end of getDurationYrMonth()

	/** Sets the DurationYrMonth
	 * @param strDurationYrMonth The strDurationYrMonth to set.
	 */
	public void setDurationYrMonth(String strDurationYrMonth) {
		this.strDurationYrMonth = strDurationYrMonth;
	}//end of setDurationYrMonth(String strDurationYrMonth)

	/** Retrieve the PEDCount
	 * @return Returns the intPEDCount.
	 */
	public Integer getPEDCount() {
		return intPEDCount;
	}//end of getPEDCount()

	/** Sets the PEDCount
	 * @param intPEDCount The intPEDCount to set.
	 */
	public void setPEDCount(Integer intPEDCount) {
		this.intPEDCount = intPEDCount;
	}//end of setPEDCount(Integer intPEDCount)

	/** Retrieve the RuleAssociateYN
	 * @return Returns the strRuleAssociateYN.
	 */
	public String getRuleAssociateYN() {
		return strRuleAssociateYN;
	}//end of getRuleAssociateYN()

	/** Sets the RuleAssociateYN
	 * @param strRuleAssociateYN The strRuleAssociateYN to set.
	 */
	public void setRuleAssociateYN(String strRuleAssociateYN) {
		this.strRuleAssociateYN = strRuleAssociateYN;
	}//end of setRuleAssociateYN(String strRuleAssociateYN)

	/** Retrieve the SeqID
	 * @return Returns the lngSeqID.
	 */
	public Long getSeqID() {
		return lngSeqID;
	}//end of getSeqID()

	/** Sets the SeqID
	 * @param lngSeqID The lngSeqID to set.
	 */
	public void setSeqID(Long lngSeqID) {
		this.lngSeqID = lngSeqID;
	}//end of setSeqID(Long lngSeqID)

	/** Retrieve the Edit YN
	 * @return Returns the strEditYN.
	 */
	public String getEditYN() {
		return strEditYN;
	}//end of getEditYN()

	/** Sets the Edit YN
	 * @param strEditYN The strEditYN to set.
	 */
	public void setEditYN(String strEditYN) {
		this.strEditYN = strEditYN;
	}//end of setEditYN(String strEditYN)

	/** Retrieve the PED Type
	 * @return Returns the strPEDType.
	 */
	public String getPEDType() {
		return strPEDType;
	}//end of getPEDType()

	/** Sets the PED Type
	 * @param strPEDType The strPEDType to set.
	 */
	public void setPEDType(String strPEDType) {
		this.strPEDType = strPEDType;
	}//end of setPEDType(String strPEDType)

	/** Retrieve the Description List
	 * @return Returns the alDescriptionList.
	 */
	public ArrayList getDescriptionList() {
		return alDescriptionList;
	}//end of getDescriptionList()

	/** Sets the Description List
	 * @param alDescriptionList The alDescriptionList to set.
	 */
	public void setDescriptionList(ArrayList alDescriptionList) {
		this.alDescriptionList = alDescriptionList;
	}//end of setDescriptionList(ArrayList alDescriptionList)

	/** This method returns the Member Seq ID
     * @return Returns the lngMemberSeqID.
     */
    public Long getMemberSeqID() {
        return lngMemberSeqID;
    }//end of getMemberSeqID()
    
    /** This method sets the Member Seq ID
     * @param lngMemberSeqID The lngMemberSeqID to set.
     */
    public void setMemberSeqID(Long lngMemberSeqID) {
        this.lngMemberSeqID = lngMemberSeqID;
    }//end of setMemberSeqID(Long lngMemberSeqID)
    
    /** This method returns the PED Seq ID
     * @return Returns the lngPEDSeqID.
     */
    public Long getPEDSeqID() {
        return lngPEDSeqID;
    }//end of getPEDSeqID()
    
    /** This method sets the PED Seq ID
     * @param lngPEDSeqID The lngPEDSeqID to set.
     */
    public void setPEDSeqID(Long lngPEDSeqID) {
        this.lngPEDSeqID = lngPEDSeqID;
    }//end of setPEDSeqID(Long lngPEDSeqID)
    
    /** This method returns the Description
     * @return Returns the strDescription.
     */
    public String getDescription() {
        return strDescription;
    }//end of getDescription()
    
    /** This method sets the Description
     * @param strDescription The strDescription to set.
     */
    public void setDescription(String strDescription) {
        this.strDescription = strDescription;
    }//end of setDescription(String strDescription)
    
    /** This method returns the Duration
     * @return Returns the strDuration.
     */
    public String getDuration() {
        return strDuration;
    }//end of getDuration()
    
    /** This method sets the Duration
     * @param strDuration The strDuration to set.
     */
    public void setDuration(String strDuration) {
        this.strDuration = strDuration;
    }//end of setDuration(String strDuration)
    
    /** This method returns the ICD Code
     * @return Returns the strICDCode.
     */
    public String getICDCode() {
        return strICDCode;
    }//end of getICDCode()
    
    /** This method sets the ICD Code
     * @param strICDCode The strICDCode to set.
     */
    public void setICDCode(String strICDCode) {
        this.strICDCode = strICDCode;
    }//end of setICDCode(String strICDCode)
    
    /** This method returns the Remarks
     * @return Returns the strRemarks.
     */
    public String getRemarks() {
        return strRemarks;
    }//end of getRemarks()
    
    /** This method sets the Remarks
     * @param strRemarks The strRemarks to set.
     */
    public void setRemarks(String strRemarks) {
        this.strRemarks = strRemarks;
    }//end of setRemarks(String strRemarks)
    
    /** This method returns the Member name
     * @return Returns the strMemberName.
     */
    public String getMemberName() {
        return strMemberName;
    }//end of getMemberName()
    
    /** This method sets the Member Name
     * @param strMemberName The strMemberName to set.
     */
    public void setMemberName(String strMemberName) {
        this.strMemberName = strMemberName;
    }//end of setMemberName(String strMemberName)
    
    /** This method returns the PED Code ID
     * @return Returns the lngPEDCodeID.
     */
    public Long getPEDCodeID() {
        return lngPEDCodeID;
    }//end of getPEDCodeID()
    
    /** This method sets the PED Code ID
     * @param lngPEDCodeID The lngPEDCodeID to set.
     */
    public void setPEDCodeID(Long lngPEDCodeID) {
        this.lngPEDCodeID = lngPEDCodeID;
    }//end of setPEDCodeID(Long lngPEDCodeID)
    
    /** This method returns the Other Description
     * @return Returns the strOtherDesc.
     */
    public String getOtherDesc() {
        return strOtherDesc;
    }//end of getOtherDesc()
    
    /** This method sets the Other Description
     * @param strOtherDesc The strOtherDesc to set.
     */
    public void setOtherDesc(String strOtherDesc) {
        this.strOtherDesc = strOtherDesc;
    }//end of setOtherDesc(String strOtherDesc)

	/** Retrieve the DurationMonths
	 * @return Returns the intDurationMonths.
	 */
	public Integer getDurationMonths() {
		return intDurationMonths;
	}//end of getDurationMonths()

	/** Sets the DurationMonths
	 * @param intDurationMonths The intDurationMonths to set.
	 */
	public void setDurationMonths(Integer intDurationMonths) {
		this.intDurationMonths = intDurationMonths;
	}//end of setDurationMonths(Integer intDurationMonths)

	/** Retrieve the DurationYears
	 * @return Returns the intDurationYears.
	 */
	public Integer getDurationYears() {
		return intDurationYears;
	}//end of getDurationYears()

	/** Sets the DurationYears
	 * @param intDurationYears The intDurationYears to set.
	 */
	public void setDurationYears(Integer intDurationYears) {
		this.intDurationYears = intDurationYears;
	}//end of setDurationYears(Integer intDurationYears)
}//end of PEDVO
