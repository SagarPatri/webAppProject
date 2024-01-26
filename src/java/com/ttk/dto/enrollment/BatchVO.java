/**
 * @ (#) BatchVO.java Jan 31, 2006
 * Project       : TTK HealthCare Services
 * File          : BatchVO.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Jan 31, 2006
 * @author       : Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dto.enrollment;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class BatchVO extends BaseVO {

	private Long lngBatchSeqID = null;
	private Long lngOfficeSeqID = null;
	private String strOfficeCode = "";
	private String strCompanyName = "";
	private String strBatchNbr = "";
	private String strLetterRefNbr = "";
	private Date dtLetterDate = null;
	private Date dtRecdDate = null;
	private String strRecdTime = "";
	private String strRecdDay = "";
	private Integer intRecdNbrPolicy = null;
	private Integer intEnteredNbrPolicy = null;
	private String strClarifyTypeID = "";
	private Date dtClarifiedDate = null;
	private String strClarifyStatusDesc = "";
	private String strNbrOfPolicies = "";
	private String strRemarks = "";
	private Long lngInsuranceSeqID = null;
	private String strEntryCompleteYn = "";
	private String strBatchCompletedYN = "";
	private String strResolvedDiscYn = "";
    private String strPrintStatus = "";
    private Date dtInwardCompleted = null;
    private Date dtDataCompleted = null;
    private Long lngProductSeqID = null;
    private String strAgentCode = "";
    private Long lngGroupRegnSeqID = null;
    private String strBatchMode = "";
    private String strEnrolTypeID = "";
    private String strGroupName = "";
    private String strGroupID = "";
    private Date dtBatchDate = null;
    private String strEntryMode = "";
    private String strCardTypeID = "";
    private String strCardImageName ="HistoryIcon";
    private String strCardImageTitle ="Card Detail";
    private String strSendMailName ="Blank";
    private String strSendMailTitle = "";
    private String batchType = "";
    
    /** Retrieve the CardImageName
     * @return Returns the strCardImageName.
     */
    public String getCardDetailImageName() {
        return strCardImageName;
    }//end of getCardDetailImageName()

    /** Sets the  CardImageName
     * @param strCardImageName The strCardImageName to set.
     */
    public void setCardDetailImageName(String strCardImageName) {
        this.strCardImageName = strCardImageName;
    }//end of setCardDetailImageName(String strCardImageName)

	/** Retrieve the strSendMailTitle
	 * @return Returns the strSendMailTitle.
	 */
	public String getSendMailTitle() {
		return strSendMailTitle;
	}//end of getSendMailTitle()

	/** Sets the strSendMailTitle
	 * @param strSendMail The strSendMail to set.
	 */
	public void setSendMailTitle(String strSendMailTitle) {
		this.strSendMailTitle = strSendMailTitle;
	}//end of setSendMailTitle(String strSendMailTitle)

	/** Retrieve the strSendMailName
	 * @return Returns the strSendMailName.
	 */
	public String getSendMailName() {
		return strSendMailName;
	}//end of getSendMailName()

	/** Sets the strSendMailName
	 * @param strSendMailName The strSendMailName to set.
	 */
	public void setSendMailName(String strSendMailName) {
		this.strSendMailName = strSendMailName;
	}//end of setSendMailName(String strSendMailName)

	/** Retrieve the strCardImageTitle
     * @return Returns the strCardImageTitle.
     */
    public String getCardDetailImageTitle() {
        return strCardImageTitle;
    }//end of getCardDetailImageTitle()

    /** Sets the  CardImageTitle
     * @param strCardImageTitle The strCardImageTitle to set.
     */
    public void setCardDetailImageTitle(String strCardImageTitle) {
        this.strCardImageTitle = strCardImageTitle;
    }//end of setCardDetailImageTitle(String strCardImageTitle)


    /** Retrieve the CardTypeID
	 * @return Returns the strCardTypeID.
	 */
	public String getCardTypeID() {
		return strCardTypeID;
	}//end of getCardTypeID()

	/** Sets the CardTypeID
	 * @param strCardTypeID The strCardTypeID to set.
	 */
	public void setCardTypeID(String strCardTypeID) {
		this.strCardTypeID = strCardTypeID;
	}//end of setCardTypeID(String strCardTypeID)

	/** Retrieve the Entry Mode
	 * @return Returns the strEntryMode.
	 */
	public String getEntryMode() {
		return strEntryMode;
	}//end of getEntryMode()

	/** Sets the Entry Mode
	 * @param strEntryMode The strEntryMode to set.
	 */
	public void setEntryMode(String strEntryMode) {
		this.strEntryMode = strEntryMode;
	}//end of setEntryMode(String strEntryMode)

	/** Retrieve the BatchDate
	 * @return Returns the dtBatchDate.
	 */
	public Date getCourierBatchDate() {
		return dtBatchDate;
	}//end of getCourierBatchDate()

    /** Retrieve the BatchDate
	 * @return Returns the dtBatchDate.
	 */
	public String getBatchDate() {
		return TTKCommon.getFormattedDate(dtBatchDate);
	}//end of getBatchDate()

	/** Retrieve the BatchDate
	 * @return Returns the dtBatchDate.
	 */
	public String getCardBatchDate() {
		return TTKCommon.getFormattedDateHour(dtBatchDate);
	}//end of getCardBatchDate()

	/** Sets the BatchDate
	 * @param dtBatchDate The dtBatchDate to set.
	 */
	public void setBatchDate(Date dtBatchDate) {
		this.dtBatchDate = dtBatchDate;
	}//end of setBatchDate(Date dtBatchDate)

	/** Retrieve the Batch Completed YN
	 * @return Returns the strBatchCompletedYN.
	 */
	public String getBatchCompletedYN() {
		return strBatchCompletedYN;
	}//end of getBatchCompletedYN()

	/** Sets the Batch Completed YN
	 * @param strBatchCompletedYN The strBatchCompletedYN to set.
	 */
	public void setBatchCompletedYN(String strBatchCompletedYN) {
		this.strBatchCompletedYN = strBatchCompletedYN;
	}//end of setBatchCompletedYN(String strBatchCompletedYN)

	/** This method returns the Letter Date
	 * @return Returns the dtLetterDate.
	 */
	public Date getLetterDate() {
		return dtLetterDate;
	}// End of getLetterDate()

	/** This method sets the Letter date
	 * @param dtLetterDate The dtLetterDate to set.
	 */
	public void setLetterDate(Date dtLetterDate) {
		this.dtLetterDate = dtLetterDate;
	}// end of setLetterDate(Date dtLetterDate)

	/** This method returns the Number of policies entered
	 * @return Returns the intEnteredNbrPolicy.
	 */
	public Integer getEnteredNbrPolicy() {
		return intEnteredNbrPolicy;
	}// End of getEnteredNbrPolicy()

	/** This method sets the Number of policies entered
	 * @param intEnteredNbrPolicy The intEnteredNbrPolicy to set.
	 */
	public void setEnteredNbrPolicy(Integer intEnteredNbrPolicy) {
		this.intEnteredNbrPolicy = intEnteredNbrPolicy;
	}// End of setEnteredNbrPolicy(Integer intEnteredNbrPolicy)

	/** This method returns the Number of policies recieved
	 * @return Returns the intRecdNbrPolicy.
	 */
	public Integer getRecdNbrPolicy() {
		return intRecdNbrPolicy;
	}// End of getRecdNbrPolicy()

	/** This method sets the Number of policies recieved
	 * @param intRecdNbrPolicy The intRecdNbrPolicy to set.
	 */
	public void setRecdNbrPolicy(Integer intRecdNbrPolicy) {
		this.intRecdNbrPolicy = intRecdNbrPolicy;
	}//End of setRecdNbrPolicy(Integer intRecdNbrPolicy)

	/** This method returns the Batch Sequence  ID
	 * @return Returns the lngBatchSeqID.
	 */
	public Long getBatchSeqID() {
		return lngBatchSeqID;
	}//End of getBatchSeqID()

	/** This method sets the Batch Sequence  ID
	 * @param lngBatchSeqID The lngBatchSeqID to set.
	 */
	public void setBatchSeqID(Long lngBatchSeqID) {
		this.lngBatchSeqID = lngBatchSeqID;
	}//End of setBatchSeqID(Long lngBatchSeqID)

	/** This method returns the Office Sequence ID
	 * @return Returns the lngOfficeSeqID.
	 */
	public Long getOfficeSeqID() {
		return lngOfficeSeqID;
	}// End of getOfficeSeqID()

	/** This method sets the Office Sequence ID
	 * @param lngOfficeSeqID The lngOfficeSeqID to set.
	 */
	public void setOfficeSeqID(Long lngOfficeSeqID) {
		this.lngOfficeSeqID = lngOfficeSeqID;
	}// End of setOfficeSeqID(Long lngOfficeSeqID)

	/** This method returns the Batch Number
	 * @return Returns the strBatchNbr.
	 */
	public String getBatchNbr() {
		return strBatchNbr;
	}// End of getBatchNbr()

	/** This method sets the Batch Number
	 * @param strBatchNbr The strBatchNbr to set.
	 */
	public void setBatchNbr(String strBatchNbr) {
		this.strBatchNbr = strBatchNbr;
	}//End of setBatchNbr(String strBatchNbr)

	/** This method returns the Company Name
	 * @return Returns the strCompanyName.
	 */
	public String getCompanyName() {
		return strCompanyName;
	}// End of getCompanyName()

	/** This method sets the Company Name
	 * @param strCompanyName The strCompanyName to set.
	 */
	public void setCompanyName(String strCompanyName) {
		this.strCompanyName = strCompanyName;
	}// End of setCompanyName(String strCompanyName)

	/** This method returns the letter reference number
	 * @return Returns the strLetterRefNbr.
	 */
	public String getLetterRefNbr() {
		return strLetterRefNbr;
	}//End of getLetterRefNbr()

	/** This method sets the letter reference number
	 * @param strLetterRefNbr The strLetterRefNbr to set.
	 */
	public void setLetterRefNbr(String strLetterRefNbr) {
		this.strLetterRefNbr = strLetterRefNbr;
	}//End of setLetterRefNbr(String strLetterRefNbr)

	/** This method returns the Office Code
	 * @return Returns the strOfficeCode.
	 */
	public String getOfficeCode() {
		return strOfficeCode;
	}//End of getOfficeCode()

	/** This method sets the Office Code
	 * @param strOfficeCode The strOfficeCode to set.
	 */
	public void setOfficeCode(String strOfficeCode) {
		this.strOfficeCode = strOfficeCode;
	}//End of setOfficeCode(String strOfficeCode)

	/** This method returns the Clarification General Type ID
	 * @return Returns the strClarifyTypeID.
	 */
	public String getClarifyTypeID() {
		return strClarifyTypeID;
	}// End of getClarifyTypeID()

	/** This method sets the Clarification General Type ID
	 * @param strClarifyTypeID The strClarifyTypeID to set.
	 */
	public void setClarifyTypeID(String strClarifyTypeID) {
		this.strClarifyTypeID = strClarifyTypeID;
	}//End of setClarifyTypeID(String strClarifyTypeID)

	/** This method returns the Clarified Date
	 * @return Returns the dtClarifiedDate.
	 */
	public Date getClarifiedDate() {
		return dtClarifiedDate;
	}//End of getClarifiedDate()

	/** This method sets the Clarified Date
	 * @param dtClarifiedDate The dtClarifiedDate to set.
	 */
	public void setClarifiedDate(Date dtClarifiedDate) {
		this.dtClarifiedDate = dtClarifiedDate;
	}// End of setClarifiedDate(Date dtClarifiedDate)

	/** This method returns the Recieved Date
	 * @return Returns the strRecdDate.
	 */
	public Date getRecdDate() {
		return dtRecdDate;
	}// End of getRecdDate()

	/** This method returns the Recieved Date
	 * @return Returns the strRecdDate.
	 */
	public String getBatchRecdDate() {
		return TTKCommon.getFormattedDate(dtRecdDate);
	}// End of getRecdDate()

	/** This method sets the Recieved Date
	 * @param strRecdDate The strRecdDate to set.
	 */
	public void setRecdDate(Date dtRecdDate) {
		this.dtRecdDate = dtRecdDate;
	}// End of setRecdDate(String strRecdDate)

	/** This method returns the Recieved Day
	 * @return Returns the strRecdDay.
	 */
	public String getRecdDay() {
		return strRecdDay;
	}// End of  getRecdDay()

	/** This method sets the Recieved Day
	 * @param strRecdDay The strRecdDay to set.
	 */
	public void setRecdDay(String strRecdDay) {
		this.strRecdDay = strRecdDay;
	}// End of setRecdDay(String strRecdDay)

	/** This method returns the Recieved Time
	 * @return Returns the strRecdTime.
	 */
	public String getRecdTime() {
		return strRecdTime;
	}// End of getRecdTime()

	/** This method sets the strRecdTime
	 * @param strRecdTime The strRecdTime to set.
	 */
	public void setRecdTime(String strRecdTime) {
		this.strRecdTime = strRecdTime;
	}//End of setRecdTime(String strRecdTime)

	/** This method returns the Number of policies recieved against entered
	 * @return Returns the strNbrOfPolicies.
	 */
	public String getNbrOfPolicies() {
		return strNbrOfPolicies;
	}//End of getNbrOfPolicies()

	/** This method sets the Number of policies recieved against entered
	 * @param strNbrOfPolicies The strNbrOfPolicies to set.
	 */
	public void setNbrOfPolicies(String strNbrOfPolicies) {
		this.strNbrOfPolicies = strNbrOfPolicies;
	}//End of setNbrOfPolicies(String strNbrOfPolicies)

	/** This method returns the Clarification Remarks
	 * @return Returns the strRemarks.
	 */
	public String getRemarks() {
		return strRemarks;
	}// End of getRemarks()

	/** This method sets the Clarification Remarks
	 * @param strRemarks The strRemarks to set.
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}// End of setRemarks(String strRemarks)

	/** This method returns the Insurance Sequence ID
	 * @return Returns the lngInsuranceSeqID.
	 */
	public Long getInsuranceSeqID() {
		return lngInsuranceSeqID;
	}// End of getInsuranceSeqID()

	/** This method sets the Insurance Sequence ID
	 * @param lngInsuranceSeqID The lngInsuranceSeqID to set.
	 */
	public void setInsuranceSeqID(Long lngInsuranceSeqID) {
		this.lngInsuranceSeqID = lngInsuranceSeqID;
	}// End of setInsuranceSeqID(Long lngInsuranceSeqID)

	/** This method returns the Batch Entry Complete Yes or No
	 * @return Returns the strEntryCompleteYn.
	 */
	public String getEntryCompleteYn() {
		return strEntryCompleteYn;
	}// End of getEntryCompleteYn()

	/** This method sets the Batch Entry Complete Yes or No
	 * @param strEntryCompleteYn The strEntryCompleteYn to set.
	 */
	public void setEntryCompleteYn(String strEntryCompleteYn) {
		this.strEntryCompleteYn = strEntryCompleteYn;
	}// End of setEntryCompleteYn(String strEntryCompleteYn)

	/** This method returns the Discussion Resolved Yes or No
	 * @return Returns the strResolvedDiscYn.
	 */
	public String getResolvedDiscYn() {
		return strResolvedDiscYn;
	}// End of getResolvedDiscYn()

	/** This method sets the Discussion resolved Yes or NO
	 * @param strResolvedDiscYn The strResolvedDiscYn to set.
	 */
	public void setResolvedDiscYn(String strResolvedDiscYn) {
		this.strResolvedDiscYn = strResolvedDiscYn;
	}// End of setResolvedDiscYn(String strResolvedDiscYn)

    /** This method returns the Print Status
     * @return Returns the strPrintStatus.
     */
    public String getPrintStatus() {
        return strPrintStatus;
    }//end of getPrintStatus()

    /** This method sets the Print Status
     * @param strPrintStatus The strPrintStatus to set.
     */
    public void setPrintStatus(String strPrintStatus) {
        this.strPrintStatus = strPrintStatus;
    }//end of setPrintStatus(String strPrintStatus)

	/** This method returns the Inward Entry Completed
	 * @return Returns the dtInwardCompleted.
	 */
	public Date getInwardCompleted() {
		return dtInwardCompleted;
	}//End of  getInwardCompleted()

	/** This method sets the Inward Entry Completed
	 * @param dtInwardCompleted The dtInwardCompleted to set.
	 */
	public void setInwardCompleted(Date dtInwardCompleted) {
		this.dtInwardCompleted = dtInwardCompleted;
	}//End of setInwardCompleted(Date dtInwardCompleted)

	/** This method returns the Data Entry Completed On
	 * @return Returns the dtDataCompleted.
	 */
	public Date getDataCompleted() {
		return dtDataCompleted;
	}//End of getDataCompleted()

	/** This method sets the Data Entry Completed On
	 * @param dtDataCompleted The dtDataCompleted to set.
	 */
	public void setDataCompleted(Date dtDataCompleted) {
		this.dtDataCompleted = dtDataCompleted;
	}//End of setDataCompleted(Date dtDataCompleted)

    /** This method returns the Agent Code
     * @return Returns the strAgentCode.
     */
    public String getAgentCode() {
        return strAgentCode;
    }//end of getAgentCode()

    /** This method sets the Agent Code
     * @param strAgentCode The strAgentCode to set.
     */
    public void setAgentCode(String strAgentCode) {
        this.strAgentCode = strAgentCode;
    }//end of setAgentCode(String strAgentCode)

    /** This method returns the Product Sequence ID
     * @return Returns the lngProductSeqID.
     */
    public Long getProductSeqID() {
        return lngProductSeqID;
    }//end of getProductSeqID()

    /** This method sets the Product Sequence ID
     * @param lngProductSeqID The lngProductSeqID to set.
     */
    public void setProductSeqID(Long lngProductSeqID) {
        this.lngProductSeqID = lngProductSeqID;
    }//end of setProductSeqID(Long lngProductSeqID)

    /** This method returns the Group Registration Seq ID
     * @return Returns the lngGroupRegnSeqID.
     */
    public Long getGroupRegnSeqID() {
        return lngGroupRegnSeqID;
    }//end of getGroupRegnSeqID()

    /** This method sets the Group Registration Seq ID
     * @param lngGroupRegnSeqID The lngGroupRegnSeqID to set.
     */
    public void setGroupRegnSeqID(Long lngGroupRegnSeqID) {
        this.lngGroupRegnSeqID = lngGroupRegnSeqID;
    }//end of setGroupRegnSeqID(Long lngGroupRegnSeqID)

    /** This method returns the Batch Mode
     * @return Returns the strBatchMode.
     */
    public String getBatchMode() {
        return strBatchMode;
    }//end of getBatchMode()

    /** This method sets the Batch Mode
     * @param strBatchMode The strBatchMode to set.
     */
    public void setBatchMode(String strBatchMode) {
        this.strBatchMode = strBatchMode;
    }//end of setBatchMode(String strBatchMode)

    /** This method returns the Enroll Type ID
     * @return Returns the strEnrolTypeID.
     */
    public String getEnrolTypeID() {
        return strEnrolTypeID;
    }//end of getEnrolTypeID()

    /** This method sets the Enroll Type ID
     * @param strEnrolTypeID The strEnrolTypeID to set.
     */
    public void setEnrolTypeID(String strEnrolTypeID) {
        this.strEnrolTypeID = strEnrolTypeID;
    }//end of setEnrolTypeID(String strEnrolTypeID)

    /** This method returns the Group ID
     * @return Returns the strGroupID.
     */
    public String getGroupID() {
        return strGroupID;
    }//end of getGroupID()

    /** This method sets the Group ID
     * @param strGroupID The strGroupID to set.
     */
    public void setGroupID(String strGroupID) {
        this.strGroupID = strGroupID;
    }//end of setGroupID(String strGroupID)

    /** This method returns the Group Name
     * @return Returns the strGroupName.
     */
    public String getGroupName() {
        return strGroupName;
    }//end of getGroupName()

    /** This method sets the Group Name
     * @param strGroupName The strGroupName to set.
     */
    public void setGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }//end of setGroupName(String strGroupName)

	/** Retrieve the Clarify Status Description
	 * @return Returns the strClarifyStatusDesc.
	 */
	public String getClarifyStatusDesc() {
		return strClarifyStatusDesc;
	}//end of getClarifyStatusDesc()

	/** Sets the Clarify Status Description
	 * @param strClarifyStatusDesc The strClarifyStatusDesc to set.
	 */
	public void setClarifyStatusDesc(String strClarifyStatusDesc) {
		this.strClarifyStatusDesc = strClarifyStatusDesc;
	}//end of setClarifyStatusDesc(String strClarifyStatusDesc)

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
}//End of BatchVO
