/**
 * @ (#) PolicyAccountInfoVO.java Jul 26, 2007
 * Project      : TTK HealthCare Services
 * File         : PolicyAccountInfoVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 26, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.accountinfo;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 *
 *
 */
public class PolicyAccountInfoVO extends BaseVO{

    private Long lngPolicySeqID = null;
    private Long lngPrevPolicySeqID=null;
    private Long lngPolicyGroupSeqID = null;
    private Long lngPrevPolicyGroupSeqID = null;
    private String strEnrollmentID = "";
    private String strPolicyNbr = "";
    private String strEnrollmentNbr = "";
    private String strMemberName = "";
    private String strGroupName = "";
    private String strPrevPolicyNbr = "";
    private String strPolicyTypeID = "";
    private String strPolicyTypeDesc = "";
    private String strImageName = "Blank";
    private String strImageTitle = "";
    private String strPreauthExist = "";
    private String strClaimExist = "";
    private Date dtStartDate = null;
	private Date dtEndDate = null;
	private String strZoneCode="";
	private String sEmiratesID="";
	private Long lngMemberSeqID = null;
	
	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public Date getStartDate() {
		return dtStartDate;
	}//end of getStartDate()

	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public String getListStartDate() {
		return TTKCommon.getFormattedDate(dtStartDate);
	}//end of getStartDate()

	/** This method sets the Start Date
	 * @param dtStartDate The Start Date to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}//end of setStartDate(Date dtStartDate)

	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public Date getEndDate() {
		return dtEndDate;
	}//end of getEndDate()

	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public String getListEndDate() {
		return TTKCommon.getFormattedDate(dtEndDate);
	}//end of getEndDate()

	/** This method sets the End Date
	 * @param dtEndDate The End Date to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}//end of setEndDate(Date dtEndDate)

    /** This method returns the Enrollment ID
	 * @return Returns the strEnrollmentID.
	 */
	public String getEnrollmentID() {
		return strEnrollmentID;
	}//end of getEnrollmentID()

	/** This method sets the Enrollment ID
	 * @param strEnrollmentID The Enrollment ID to set.
	 */
	public void setEnrollmentID(String strEnrollmentID) {
		this.strEnrollmentID = strEnrollmentID;
	}//end of setEnrollmentID(String strEnrollmentID)

    /** This method returns the Preauth Exist Yes/No
     * @return Returns the strPolicyNbr.
     */
    public String getPreauthExist() {
        return strPreauthExist;
    }//end of getPreauthExist()

    /** This method sets the Preauth Exist Yes/No
     * @param strPolicyNbr to set.
     */
    public void setPreauthExist(String strPreauthExist) {
        this.strPreauthExist = strPreauthExist;
    }//end of setPreauthExist(String strPolicyNbr)

    /** This method returns the Claim Exist Yes/No
     * @return Returns the strClaimExist.
     */
    public String getClaimExist() {
        return strClaimExist;
    }//end of getClaimMadeExist()

    /** This method sets the Claim Exist Yes/No
     * @param strPolicyNbr to set.
     */
    public void setClaimExist(String strClaimExist) {
        this.strClaimExist = strClaimExist;
    }//end of setClaimExist(String strClaimExist)


    /** This method returns the Policy Sequence ID
     * @return Returns the lngPolicySeqID.
     */
    public Long getPolicySeqID() {
        return lngPolicySeqID;
    }//end of getPolicySeqID()

    /** This method sets the Policy Sequence ID
     * @param lngPolicySeqID to set.
     */
    public void setPolicySeqID(Long lngPolicySeqID) {
        this.lngPolicySeqID = lngPolicySeqID;
    }//end of setPolicySeqID(Long lngPolicySeqID)

    /** This method returns the Previous Policy Sequence ID
     * @return Returns the lngPrevPolicySeqID.
     */
    public Long getPrevPolicySeqID() {
        return lngPrevPolicySeqID;
    }//end of getPolicySeqID()

    /** This method sets the Previous Policy Sequence ID
     * @param lngPrevPolicySeqID to set
     */
    public void setPrevPolicySeqID(Long lngPrevPolicySeqID) {
        this.lngPrevPolicySeqID = lngPrevPolicySeqID;
    }//end of setPrevPolicySeqID(Long lngPrevPolicySeqID)

    /** This method returns the Policy Group Seq ID
     * @return Returns the lngPolicyGroupSeqID.
     */
    public Long getPolicyGroupSeqID() {
        return lngPolicyGroupSeqID;
    }//end of getPolicyGroupSeqID()

    /** This method sets the Policy Group Seq ID
     * @param lngPolicyGroupSeqID The lngPolicyGroupSeqID to set.
     */
    public void setPolicyGroupSeqID(Long lngPolicyGroupSeqID) {
        this.lngPolicyGroupSeqID = lngPolicyGroupSeqID;
    }//end of setPolicyGroupSeqID(Long lngPolicyGroupSeqID)

    /** This method returns the Previous Policy Group Seq ID
     * @return Returns the lngPrevPolicyGroupSeqID.
     */
    public Long getPrevPolicyGroupSeqID() {
        return lngPrevPolicyGroupSeqID;
    }//end of getPrevPolicyGroupSeqID()

    /** This method sets the Previous Policy Group Seq ID
     * @param lngPrevPolicyGroupSeqID to set.
     */
    public void setPrevPolicyGroupSeqID(Long lngPrevPolicyGroupSeqID) {
        this.lngPrevPolicyGroupSeqID = lngPrevPolicyGroupSeqID;
    }//end of setPrevPolicyGroupSeqID(Long lngPrevPolicyGroupSeqID)

    /** This method returns the Policy Number
     * @return Returns the strPolicyNbr.
     */
    public String getPolicyNbr() {
        return strPolicyNbr;
    }//end of getPolicyNbr()

    /** This method sets the Policy Number
     * @param strPolicyNbr to set.
     */
    public void setPolicyNbr(String strPolicyNbr) {
        this.strPolicyNbr = strPolicyNbr;
    }//end of setPolicyNbr(String strPolicyNbr)

    /** This method returns the Enrollment Number
     * @return Returns the strEnrollmentNbr.
     */
    public String getEnrollmentNbr() {
        return strEnrollmentNbr;
    }//end of getEnrollmentNbr()

    /** This method sets the Enrollment Number
     * @param strEnrollmentNbr to set.
     */
    public void setEnrollmentNbr(String strEnrollmentNbr) {
        this.strEnrollmentNbr = strEnrollmentNbr;
    }//end of setEnrollmentNbr(String strEnrollmentNbr)

    /** This method returns the Member Name
     * @return Returns the strMemberName.
     */
    public String getMemberName() {
        return strMemberName;
    }//end of getMemberName()

    /** This method sets the Member Name
     * @param strMemberName set.
     */
    public void setMemberName(String strMemberName) {
        this.strMemberName = strMemberName;
    }//end of setMemberName(String strMemberName)

    /** This method returns the Group Name
     * @return Returns the strGroupName.
     */
    public String getGroupName() {
        return strGroupName;
    }//end of getGroupName()

    /** This method sets the Group Name
     * @param strGroupName to set.
     */
    public void setGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }//end of setGroupName(String strGroupName)

    /** This method returns the Previous Policy Number
     * @return Returns the strPrevPolicyNbr.
     */
    public String getPrevPolicyNbr() {
        return strPrevPolicyNbr;
    }//end of getPrevPolicyNbr()

    /** This method sets the Previous Policy Number
     * @param strPrevPolicyNbr to set.
     */
    public void setPrevPolicyNbr(String strPrevPolicyNbr) {
        this.strPrevPolicyNbr = strPrevPolicyNbr;
    }//end of setPrevPolicyNbr(String strPrevPolicyNbr)

    /** This method returns the Policy Type ID
     * @return Returns the strPolicyTypeID.
     */
    public String getPolicyTypeID() {
        return strPolicyTypeID;
    }//end of getPolicyTypeID()

    /** This method sets the Policy Type ID
     * @param strPolicyTypeID to set.
     */
    public void setPolicyTypeID(String strPolicyTypeID) {
        this.strPolicyTypeID = strPolicyTypeID;
    }//end of setPolicyTypeID(String strPolicyTypeID)

    /** This method returns the Policy Type
     * @return Returns the strPolicyTypeDesc.
     */
    public String getPolicyTypeDesc() {
        return strPolicyTypeDesc;
    }//end of getPolicyTypeDesc()

    /** This method sets the Policy Type
     * @param strPolicyTypeDesc to set.
     */
    public void setPolicyTypeDesc(String strPolicyTypeDesc) {
        this.strPolicyTypeDesc = strPolicyTypeDesc;
    }//end of setPolicyTypeDesc(String strPolicyTypeDesc)

    /** This method returns the Image Name
     * @return Returns the strImageName.
     */
    public String getImageName() {
        return strImageName;
    }//end of getImageName()

    /** This method sets the Image Name
     * @param strImageName to set.
     */
    public void setImageName(String strImageName) {
        this.strImageName = strImageName;
    }//end of setImageName(String strImageName)

    /** This method returns the Image Title
     * @return Returns the strImageTitle.
     */
    public String getImageTitle() {
        return strImageTitle;
    }//end of getImageTitle()

    /** This method sets the Image Title
     * @param strImageTitle to set.
     */
    public void setImageTitle(String strImageTitle) {
        this.strImageTitle = strImageTitle;
    }//end of setImageTitle(String strImageTitle)

	/** This method returns the ZoneCode
	 * @return the strZoneCode
	 */
	public String getZoneCode() {
		return strZoneCode;
	}// end of getZoneCode()

	/** This method sets the ZoneCode
	 * @param strZoneCode the strZoneCode to set
	 */
	public void setZoneCode(String strZoneCode) {
		this.strZoneCode = strZoneCode;
	}// end of setZoneCode(String strZoneCode)

	public String getsEmiratesID() {
		return sEmiratesID;
	}

	public void setsEmiratesID(String sEmiratesID) {
		this.sEmiratesID = sEmiratesID;
	}

	public Long getMemberSeqID() {
		return lngMemberSeqID;
	}

	public void setMemberSeqID(Long lngMemberSeqID) {
		this.lngMemberSeqID = lngMemberSeqID;
	}
}//end of PolicyAccountInfoVO.java
