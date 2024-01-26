/**
 * @ (#) LogVO.java Sep 15, 2005
 * Project      : 
 * File         : LogVO.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Sep 15, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.empanelment;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class LogVO extends BaseVO{
    
    private Long lLogId = null; //Modified by Suresh
    private String strLogType = "";
    private Date dtLogDate=null;
    private String strLogDesc = "";
    private String strUserName = "";
    private Long lHospSeqId = null;
    private Long lPtnrSeqId = null;
    private String strImageName="UserIcon";
    private String strImageTitle="User Log";
    private Long lngPolicyLogSeqID = null;
    private Long lngPolicySeqID = null;
    private Long lngMemberSeqID = null;
    private Long lngEndorsementSeqID = null;
    private String strSystemGenYN = "";
    private Long lngSeqID = null;//PolicySeqID/EndorsementSeqID
    private Long lngClaimSeqID = null;
    private String strLogTypeDesc = "";
        
    
    public Long getPtnrSeqId() {
  		return lPtnrSeqId;
  	}

  	public void setPtnrSeqId(Long lPtnrSeqId) {
  		this.lPtnrSeqId = lPtnrSeqId;
  	}
    
    /** Retrieve the LogTypeDesc
	 * @return Returns the strLogTypeDesc.
	 */
	public String getLogTypeDesc() {
		return strLogTypeDesc;
	}//end of getLogTypeDesc()

	/** Sets the LogTypeDesc
	 * @param strLogTypeDesc The strLogTypeDesc to set.
	 */
	public void setLogTypeDesc(String strLogTypeDesc) {
		this.strLogTypeDesc = strLogTypeDesc;
	}//end of setLogTypeDesc(String strLogTypeDesc)

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

	/**
     * Retrieve the Image Title
     * 
     * @return  strImageTitle String Image Title
     */
    public String getImageTitle() {
        return strImageTitle;
    }//end of getImageTitle()
    
    /**
     * Sets the Image Title
     * 
     * @param  strImageTitle String Image Title  
     */
    public void setImageTitle(String strImageTitle) {
        this.strImageTitle = strImageTitle;
    }//end of setImageTitle(String strImageTitle)
    
    /**
     * Store the log type
     * 
     * @param strLogType String log type
     */
    public void setLogType(String strLogType)
    {
        this.strLogType = strLogType;
    }//end of setLogType(String strLogType)
    
    /**
     * Retreive the log type
     * 
     * @return String log type
     */
    public String getLogType()
    {
        return strLogType;
    }//end of getLogType()
    
    /**
     * Store the log id
     * 
     * @param lLogId Long log id
     */
    public void setLogId(Long lLogId)
    {
        this.lLogId = lLogId;
    }//end of setLogId(Long lLogId)
    
    /**
     * Retreive the log id
     * 
     * @return Long log id
     */
    public Long getLogId()
    {
        return lLogId;
    }//end of getLogId()
    
    /**
     * Store the log date
     * 
     * @param dtLogDate Date log date
     */
    public void setLogDate(Date dtLogDate)
    {
        this.dtLogDate = dtLogDate;
    }//end of setLogDate(Date dtLogDate)
    
    /**
     * Retreive the log date
     * 
     * @return Date log date
     */
    public String getLogDate()
    {
        return TTKCommon.getFormattedDateHour(dtLogDate);
    }//end of getLogDate()
    
    /**
     * Store the log description
     * 
     * @param strLogDesc String log description
     */
    public void setLogDesc(String strLogDesc)
    {
        this.strLogDesc = strLogDesc;
    }//end of setLogDesc(String strLogDesc)
    
    /**
     * Retreive the log description
     * 
     * @return String log description
     */
    public String getLogDesc()
    {
        return strLogDesc;
    }//end of getLogDesc()
    
    /**
     * Store the name of the person who entered the log details 
     * 
     * @param strUserName String the user name
     */
    public void setUserName(String strUserName)
    {
        this.strUserName = strUserName;
    }//end of setUserName(String strUserName)
    
    /**
     * Retreive the name of the person who entered the log details 
     * 
     * @return String the user name
     */
    public String getUserName()
    {
        return strUserName;
    }//end of getUserName()

	/**
	 * This method returns the HospitalSequenceId fot the LogVO Object
	 * @return Returns the lHospSeqId.
	 */
	public Long getHospSeqId() {
		return lHospSeqId;
	}//end of getHospSeqId()
	
	/**
	 * This method sets HospitalSequenceId for the LogVO Object
	 * @param lHospSeqId The lHospSeqId to set.
	 */
	public void setHospSeqId(Long lHospSeqId) {
		this.lHospSeqId = lHospSeqId;
	}//end of setHospSeqId(Long lHospSeqId)
    
    /**
     * Retrieve the Image Name
     * 
     * @return  strImageName String Image Name
     */
    public String getImageName() {
        return strImageName;
    }//end of getImageName()
    
    /**
     * Sets the Image Name
     * 
     * @param  strImageName String Image Name 
     */
    public void setImageName(String strImageName) {
        this.strImageName = strImageName;
    }//end of setImageName(String strImageName)
    
    /** This method returns the Endorsement Seq ID 
     * @return Returns the lngEndorsementSeqID.
     */
    public Long getEndorsementSeqID() {
        return lngEndorsementSeqID;
    }//end of getEndorsementSeqID()
    
    /** This method sets the Endorsement Seq ID
     * @param lngEndorsementSeqID The lngEndorsementSeqID to set.
     */
    public void setEndorsementSeqID(Long lngEndorsementSeqID) {
        this.lngEndorsementSeqID = lngEndorsementSeqID;
    }//end of setEndorsementSeqID(Long lngEndorsementSeqID)
    
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
    
    /** This method returns the Policy Log Seq ID
     * @return Returns the lngPolicyLogSeqID.
     */
    public Long getPolicyLogSeqID() {
        return lngPolicyLogSeqID;
    }//end of getPolicyLogSeqID()
    
    /** This method sets the Policy Log Seq ID
     * @param lngPolicyLogSeqID The lngPolicyLogSeqID to set.
     */
    public void setPolicyLogSeqID(Long lngPolicyLogSeqID) {
        this.lngPolicyLogSeqID = lngPolicyLogSeqID;
    }//end of setPolicyLogSeqID(Long lngPolicyLogSeqID)
    
    /** This method returns the Policy Seq ID
     * @return Returns the lngPolicySeqID.
     */
    public Long getPolicySeqID() {
        return lngPolicySeqID;
    }//end of getPolicySeqID()
    
    /** This method sets the Policy Seq ID
     * @param lngPolicySeqID The lngPolicySeqID to set.
     */
    public void setPolicySeqID(Long lngPolicySeqID) {
        this.lngPolicySeqID = lngPolicySeqID;
    }//end of setPolicySeqID(Long lngPolicySeqID)
    
    /** This method returns the SystemGenYN
     * @return Returns the strSystemGenYN.
     */
    public String getSystemGenYN() {
        return strSystemGenYN;
    }//end of getSystemGenYN()
    
    /** This method sets the SystemGenYN
     * @param strSystemGenYN The strSystemGenYN to set.
     */
    public void setSystemGenYN(String strSystemGenYN) {
        this.strSystemGenYN = strSystemGenYN;
    }//end of setSystemGenYN(String strSystemGenYN)
    
    /** This method returns the PolicySeqID/EndorsementSeqID
     * @return Returns the lngSeqID.
     */
    public Long getSeqID() {
        return lngSeqID;
    }//end of getSeqID()
    
    /** This method sets the PolicySeqID/EndorsementSeqID
     * @param lngSeqID The lngSeqID to set.
     */
    public void setSeqID(Long lngSeqID) {
        this.lngSeqID = lngSeqID;
    }//end of setSeqID(Long lngSeqID)
}//end of class LogVO
