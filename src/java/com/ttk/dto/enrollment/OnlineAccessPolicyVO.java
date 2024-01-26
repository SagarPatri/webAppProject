/**
 * @ (#) OnlineAccessPolicyVO.java Aug 3, 2006
 * Project      : TTK HealthCare Services
 * File         : OnlineAccessPolicyVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Aug 3, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.enrollment;

import java.util.Date;

import com.ttk.common.TTKCommon;

/**
 *  This Data transfer object is used to hold the Polciy Information
 *  when user works with Policy in OnlineAccess Flow
 *
 */
public class OnlineAccessPolicyVO extends PolicyVO {

    private Date dtFromDate=null;                       //EFFECTIVE_FROM_DATE
    private Date dtEndDate=null;                        //EFFECTIVE_TO_DATE
    private String strOnlineAccessYN="N";               //ONLINE_ACCESS_PRESENT_YN      by default access is blocked
    private String strMemberAdditionPrivelegeYN="N";    //MEMBER_ADDTION_PRIVILEGE_YN   by default Privelege is false
    private Integer intModificationAllowedDays=null;    //MODIFICATION_ALLOWED_DAYS
    private String strLoginType="";                     //identifies who logged in  H for HR/I for Individual/E for Corporate Employee
    private String strSummaryImageName ="SummaryIcon";  //Summary Icon
    private String strSummaryImageTitle ="Summary";
    private String strEnrollmentAddYN="N";              //Enrollment Add Privelege   by default Privelege is false
    private String strEmployeeNbr = "";
    private String strHREmpAddYN="N";
    private String strPolicyStatusTypeID = "";  //POLICY_STATUS_GENERAL_TYPE_ID - for checking whether Policy is Cancelled or not
    private String strGroupCntCancelGenTypeID="";
    private String strLocation = ""; 
    private String strCompanyName = ""; 
    
    /** Retrieve the GroupCntCancelGenTypeID
	 * @return Returns the strGroupCntCancelGenTypeID.
	 */
	public String getGroupCntCancelGenTypeID() {
		return strGroupCntCancelGenTypeID;
	}//end of getGroupCntCancelGenTypeID()

	/** Sets the GroupCntCancelGenTypeID
	 * @param strGroupCntCancelGenTypeID The strGroupCntCancelGenTypeID to set.
	 */
	public void setGroupCntCancelGenTypeID(String strGroupCntCancelGenTypeID) {
		this.strGroupCntCancelGenTypeID = strGroupCntCancelGenTypeID;
	}//end of setGroupCntCancelGenTypeID(String strGroupCntCancelGenTypeID)
    
    /** Retrieve the PolicyStatusTypeID
	 * @return Returns the strPolicyStatusTypeID.
	 */
	public String getPolicyStatusTypeID() {
		return strPolicyStatusTypeID;
	}//end of getPolicyStatusTypeID()

	/** Sets the PolicyStatusTypeID
	 * @param strPolicyStatusTypeID The strPolicyStatusTypeID to set.
	 */
	public void setPolicyStatusTypeID(String strPolicyStatusTypeID) {
		this.strPolicyStatusTypeID = strPolicyStatusTypeID;
	}//end of setPolicyStatusTypeID(String strPolicyStatusTypeID)

	/** This method returns the Employee add YN for HR
     * @return Returns the strHREmpAddYN.
     */
    public String getEmployeeAddYN() {
        return strHREmpAddYN;
    }//end of getEmployeeAddYN()

    /** This method sets the Employee add YN for HR
     * @param strEmployeeNbr strHREmpAddYN to set.
     */
    public void setEmployeeAddYN(String strHREmpAddYN) {
        this.strHREmpAddYN = strHREmpAddYN;
    }//end of setEmployeeNbr(String strEmployeeNbr)

    /** This method returns the Employee Number
     * @return Returns the strEmployeeNbr.
     */
    public String getEmployeeNbr() {
        return strEmployeeNbr;
    }//end of getEmployeeNbr()

    /** This method sets the Employee Number
     * @param strEmployeeNbr The strEmployeeNbr to set.
     */
    public void setEmployeeNbr(String strEmployeeNbr) {
        this.strEmployeeNbr = strEmployeeNbr;
    }//end of setEmployeeNbr(String strEmployeeNbr)

    /** Retrieve the Summary Image Name
     * @return Returns the strSummaryImageName.
     */
    public String getSummaryImageName() {
        return strSummaryImageName;
    }//end of getShortfallImageName()

    /** Sets the Summary Image Name
     * @param strSummaryImageName The strSummaryImageName to set.
     */
    public void setSummaryImageName(String strSummaryImageName) {
        this.strSummaryImageName = strSummaryImageName;
    }//end of setSummaryImageName(String strSummaryImageName)

    /** Retrieve the Summary Image Title
     * @return Returns the strSummaryImageTitle.
     */
    public String getSummaryImageTitle() {
        return strSummaryImageTitle;
    }//end of getSummaryImageTitle()

    /** Sets the Summary Image Title
     * @param strSummaryImageTitle The strSummaryImageTitle to set.
     */
    public void setSummaryImageTitle(String strSummaryImageTitle) {
        this.strSummaryImageTitle = strSummaryImageTitle;
    }//end of setSummaryImageTitle(String strSummaryImageTitle)

    /**
     * Retrieve the Login Type
     * @return  strLoginType String
     */
    public String getLoginType() {
        return strLoginType;
    }//end of getLoginType()

    /**
     * Sets the Login Type
     * @param  strLoginType String
     */
    public void setLoginType(String strLoginType) {
        this.strLoginType = strLoginType;
    }//end of setLoginType(String strLoginType)

    /**
     * Retrieve the Effective End Date of Policy
     * @return  dtEndDate Date
     */
    public Date getEndDate() {
        return dtEndDate;
    }//end of getEndDate()

    /**
     * Retrieve the Effective End Date of Policy in DD/MM/YYYY format
     * @return  dtEndDate Date
     */
    public String  getEffectiveEndDate() {
        return TTKCommon.getFormattedDate(dtEndDate);
    }//end of getEndDate()

    /**
     * Sets the Effective End Date of Policy
     * @param  dtEndDate Date
     */
    public void setEndDate(Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }//end of setEndDate(Date dtEndDate)

    /**
     * Retrieve the Effective From Date of Policy
     * @return  dtFromDate Date
     */
    public Date getFromDate() {
        return dtFromDate;
    }//end of getFromDate()

    /**
     * Retrieve the Effective From Date of Policy in DD/MM/YYYY format
     * @return  dtFromDate Date
     */
    public String getEffectiveFromDate() {
        return TTKCommon.getFormattedDate(dtFromDate);
    }//end of getFromDate()

    /**
     * Sets the dtFromDate
     * @param  dtFromDate Date
     */
    public void setFromDate(Date dtFromDate) {
        this.dtFromDate = dtFromDate;
    }//end of setFromDate(Date dtFromDate)

    /**
     * Retrieve the Modification Allowed Days left
     * @return  intModificationAllowedDays Integer
     */
    public Integer getModificationAllowedDays() {
        return intModificationAllowedDays;
    }//end of getModificationAllowedDays()

    /**
     * Sets the Modification Allowed Days left
     * @param  intModificationAllowedDays Integer
     */
    public void setModificationAllowedDays(Integer intModificationAllowedDays) {
        this.intModificationAllowedDays = intModificationAllowedDays;
    }//end of setModificationAllowedDays(Integer intModificationAllowedDays)

    /**
     * Retrieve the Member Addition Privelege status
     * @return  strMemberAdditionPrivelegeYN String
     */
    public String getMemberAdditionPrivelegeYN() {
        return strMemberAdditionPrivelegeYN;
    }//end of getMemberAdditionPrivelegeYN()

    /**
     * Sets the Member Addition Privelege status
     * @param  strMemberAdditionPrivelegeYN String
     */
    public void setMemberAdditionPrivelegeYN(String strMemberAdditionPrivelegeYN) {
        this.strMemberAdditionPrivelegeYN = strMemberAdditionPrivelegeYN;
    }//end of setMemberAdditionPrivelegeYN(String strMemberAdditionPrivelegeYN)

    /**
     * Retrieve the Enrollment Addition Privelege status
     * @return  strEnrollmentAddYN String
     */
    public String getEnrollmentAddYN() {
        return strEnrollmentAddYN;
    }//end of getEnrollmentAddYN()

    /**
     * Sets the Enrollment Add Privelege status
     * @param  strEnrollmentAddYN String
     */
    public void setEnrollmentAddYN(String strEnrollmentAddYN) {
        this.strEnrollmentAddYN = strEnrollmentAddYN;
    }//end of setEnrollmentAddYN(String strEnrollmentAddYN)

    /**
     * Retrieve the Online Access status of the User
     * @return  strOnlineAccessYN String
     */
    public String getOnlineAccessYN() {
        return strOnlineAccessYN;
    }//end of getOnlineAccessYN()

    /**
     * Sets the Online Access status of the User
     * @param  strOnlineAccessYN String
     */
    public void setOnlineAccessYN(String strOnlineAccessYN) {
        this.strOnlineAccessYN = strOnlineAccessYN;
    }//end of setOnlineAccessYN(String strOnlineAccessYN)
    
    //kocb 
    public String getLocation() {
		return strLocation;
	}//end of getGroupCntCancelGenTypeID()

	public void setLocation(String strLocation) {
		this.strLocation = strLocation;
	}//end of setGroupCntCancelGenTypeID(String strGroupCntCancelGenTypeID)
	
	//added new for kocbroker
	/**
     * Retrieve the Company Name of the Broker
     * @return  strCompanyName String
     */
    public String getBroCompanyName() {
		return strCompanyName;
	}//end of getCompanyName()
    
    /**
     * Sets the Company Name of the Broker
     * @param  strCompanyName String
     */
	public void setBroCompanyName(String strCompanyName) {
		this.strCompanyName = strCompanyName;
	}//end of setCompanyName(String strGroupCntCancelGenTypeID)
}//end of OnlineAccessPolicyVO
