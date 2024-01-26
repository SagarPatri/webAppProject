/**
 * @ (#) ShortfallVO.java Apr 11, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ShortfallVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 11, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.preauth;

import java.util.Date;

import com.ttk.common.TTKCommon;
import org.dom4j.Document;


public class ShortfallVO extends SupportVO{

    private Long lngShortfallSeqID = null;
    private String strShortfallTypeID = "";
    private Date dtSentDate = null;

    private Document docShortfallQuestions = null;
    private String strReceivedTime = "";
    private String strReceivedDay = "";
    private String strSentTime = "";
    private String strSentDay = "";
    private Long lngClaimSeqID = null;
    private Date dtCorrespondenceDate = null;
    private String strCorrespondenceTime = "";
    private String strCorrespondenceDay = "";
    private String strCorrespondenceYN = "";
    private Integer intCorrespondenceCount = null; 
    private Long lngReminderLogSeqID = null;
 //Shortfall CR KOC1179
    private String strClause="";
    private String strActiveLink="";  
    private String strCurrentShortfallStatus="";
    private String strShortfallTypeIDNew = "";
    private Long strShortfallUnderClause = null;
    private String strShortfallTemplateType = "";
    //added for Mail-SMS Template for Cigna
    private String strCignaYN = "";
    //added for Checking Member claim YN for Cigna
    private String strMemberClaimYN = "";
    private String strClaimTypeDesc = "";//shortfall phase1
    private String recievedStatus= "";
  private String currentDate="";
  private String shortFallQueryIds;
    //shortfall phase1
    public String getClaimTypeDesc() {
		return strClaimTypeDesc;
	}
	public void setClaimTypeDesc(String strClaimTypeDesc) {
		this.strClaimTypeDesc = strClaimTypeDesc;
	}
	
	//shortfall phase1
	/** Retrieve the Clause Number
	 * @return Returns the strClause.
	 */
    public String getClause() {
		return strClause;
	}
    /** Sets the Clause Number
	 * @param strClause The strClause to set.
	 */
	public void setClause(String strClause) {
		this.strClause = strClause;
	}
	/** Retrieve the ActiveLink
	 * @return Returns the strActiveLink.
	 */
	public String getActiveLink() {
		return strActiveLink;
	}
	 /** Sets the Active Link
	 * @param strActiveLink The strActiveLink to set.
	 */
	public void setActiveLink(String strActiveLink) {
		this.strActiveLink = strActiveLink;
	}
	/** Retrieve the Current Shortfall Status
	 * @return Returns the strCurrentShortfallStatus.
	 */
	public String getCurrentShortfallStatus() {
		return strCurrentShortfallStatus;
	}
	 /** Sets the Current Shortfall Status
	 * @param strCurrentShortfallStatus The strCurrentShortfallStatus to set.
	 */
	public void setCurrentShortfallStatus(String strCurrentShortfallStatus) {
		this.strCurrentShortfallStatus = strCurrentShortfallStatus;
	}
	/** Retrieve the ShortfallType ID New
	 * @return Returns the strShortfallTypeIDNew.
	 */
    public String getShortfallTypeIDNew() {
		return strShortfallTypeIDNew;
	}
    /** Sets the Shortfall Type ID New
	 * @param strShortfallTypeIDNew The strShortfallTypeIDNew to set.
	 */
	public void setShortfallTypeIDNew(String strShortfallTypeIDNew) {
		this.strShortfallTypeIDNew = strShortfallTypeIDNew;
	}
	/** Retrieve the Shortfall Under Clause
	 * @return Returns the strShortfallUnderClause.
	 */
	public Long getShortfallUnderClause() {
		return strShortfallUnderClause;
	}
	 /** Sets the Shortfall Under Clause
	 * @param strShortfallUnderClause The strShortfallUnderClause to set.
	 */
	public void setShortfallUnderClause(Long strShortfallUnderClause) {
		this.strShortfallUnderClause = strShortfallUnderClause;
	}
	/** Retrieve the Shortfall Template Type
	 * @return Returns the strShortfallTemplateType.
	 */
	public String getShortfallTemplateType() {
		return strShortfallTemplateType;
	}
	 /** Sets the Shortfall Template Type
	 * @param strShortfallTemplateType The strShortfallTemplateType to set.
	 */
	public void setShortfallTemplateType(String strShortfallTemplateType) {
		this.strShortfallTemplateType = strShortfallTemplateType;
	}
	// End Shortfall CR KOC1179
    /** Retrieve the ReminderLogSeqID
	 * @return Returns the lngReminderLogSeqID.
	 */
	public Long getReminderLogSeqID() {
		return lngReminderLogSeqID;
	}//end of getReminderLogSeqID()

	/** Sets the ReminderLogSeqID
	 * @param lngReminderLogSeqID The lngReminderLogSeqID to set.
	 */
	public void setReminderLogSeqID(Long lngReminderLogSeqID) {
		this.lngReminderLogSeqID = lngReminderLogSeqID;
	}//end of setReminderLogSeqID(Long lngReminderLogSeqID)

	/** Retrieve the CorrespondenceCount
	 * @return Returns the intCorrespondenceCount.
	 */
	public Integer getCorrespondenceCount() {
		return intCorrespondenceCount;
	}//end of getCorrespondenceCount()

	/** Sets the CorrespondenceCount
	 * @param intCorrespondenceCount The intCorrespondenceCount to set.
	 */
	public void setCorrespondenceCount(Integer intCorrespondenceCount) {
		this.intCorrespondenceCount = intCorrespondenceCount;
	}//end of setCorrespondenceCount(Integer intCorrespondenceCount)

	/** Retrieve the CorrespondenceYN
	 * @return Returns the strCorrespondenceYN.
	 */
	public String getCorrespondenceYN() {
		return strCorrespondenceYN;
	}//end of getCorrespondenceYN()

	/** Sets the CorrespondenceYN
	 * @param strCorrespondenceYN The strCorrespondenceYN to set.
	 */
	public void setCorrespondenceYN(String strCorrespondenceYN) {
		this.strCorrespondenceYN = strCorrespondenceYN;
	}//end of setCorrespondenceYN(String strCorrespondenceYN)

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

	/** Retrieve the ReceivedDay
	 * @return Returns the strReceivedDay.
	 */
	public String getReceivedDay() {
		return strReceivedDay;
	}//end of getReceivedDay()

	/** Sets the ReceivedDay
	 * @param strReceivedDay The strReceivedDay to set.
	 */
	public void setReceivedDay(String strReceivedDay) {
		this.strReceivedDay = strReceivedDay;
	}//end of setReceivedDay(String strReceivedDay)

	/** Retrieve the ReceivedTime
	 * @return Returns the strReceivedTime.
	 */
	public String getReceivedTime() {
		return strReceivedTime;
	}//end of getReceivedTime()

	/** Sets the ReceivedTime
	 * @param strReceivedTime The strReceivedTime to set.
	 */
	public void setReceivedTime(String strReceivedTime) {
		this.strReceivedTime = strReceivedTime;
	}//end of setReceivedTime(String strReceivedTime)

	/** Retrieve the Shortfall Questions
	 * @return Returns the docShortfallQuestions.
	 */
	public Document getShortfallQuestions() {
		return docShortfallQuestions;
	}//end of getShortfallQuestions()

	/** Sets the Shortfall Questions
	 * @param docShortfallQuestions The docShortfallQuestions to set.
	 */
	public void setShortfallQuestions(Document docShortfallQuestions) {
		this.docShortfallQuestions = docShortfallQuestions;
	}//end of setShortfallQuestions(Document docShortfallQuestions)

	/** Retrieve the Sent Date
     * @return Returns the dtSentDate.
     */
    public String getShortfallSentDate(){
        return TTKCommon.getFormattedDate(dtSentDate);
    }//end of getShortfallSentDate()

    /** Retrieve the Sent Date
     * @return Returns the dtSentDate.
     */
    public Date getSentDate() {
        return dtSentDate;
    }//end of getSentDate()

    /** Sets the Sent Date
     * @param dtSentDate The dtSentDate to set.
     */
    public void setSentDate(Date dtSentDate) {
        this.dtSentDate = dtSentDate;
    }//end of setSentDate(Date dtSentDate)

    /** Retrieve the SentDay
     * @return Returns the strSentDay.
     */
    public String getSentDay() {
        return strSentDay;
    }//end of getSentDay()

    /** Sets the SentDay
     * @param strSentDay The strSentDay to set.
     */
    public void setSentDay(String strSentDay) {
        this.strSentDay = strSentDay;
     }//end of setSentDay(String strSentDay)

    /** Retrieve the SentTime
     * @return Returns the strSentTime.
     */
    public String getSentTime() {
        return strSentTime;
    }//end of getSentTime()

    /** Sets the SentTime
     * @param strSentTime The strSentTime to set.
     */
    public void setSentTime(String strSentTime) {
        this.strSentTime = strSentTime;
    }//end of setSentTime(String strSentTime)

    /** Retrieve the Shortfall Seq ID
     * @return Returns the lngShortfallSeqID.
     */
    public Long getShortfallSeqID() {
        return lngShortfallSeqID;
    }//end of getShortfallSeqID()

    /** Sets the Shortfall Seq ID
     * @param lngShortfallSeqID The lngShortfallSeqID to set.
     */
    public void setShortfallSeqID(Long lngShortfallSeqID) {
        this.lngShortfallSeqID = lngShortfallSeqID;
    }//end of setShortfallSeqID(Long lngShortfallSeqID)

    /** Retrieve the Shortfall Type ID
     * @return Returns the strShortfallTypeID.
     */
    public String getShortfallTypeID() {
        return strShortfallTypeID;
    }//end of getShortfallTypeID()

    /** Sets the Shortfall Type ID
     * @param strShortfallTypeID The strShortfallTypeID to set.
     */
    public void setShortfallTypeID(String strShortfallTypeID) {
        this.strShortfallTypeID = strShortfallTypeID;
    }//end of setShortfallTypeID(String strShortfallTypeID)

	/** Retrieve the CorrespondenceDate
	 * @return Returns the dtCorrespondenceDate.
	 */
	public Date getCorrespondenceDate() {
		return dtCorrespondenceDate;
	}//end of getCorrespondenceDate()
	
	/**
     * Retrieve the CorrespondenceDate
     * @return  dtCorrespondenceDate Date
     */
    public String getPATCorrespondenceDate() {
        return TTKCommon.getFormattedDate(dtCorrespondenceDate);
    }//end of getPATCorrespondenceDate()

	/** Sets the CorrespondenceDate
	 * @param dtCorrespondenceDate The dtCorrespondenceDate to set.
	 */
	public void setCorrespondenceDate(Date dtCorrespondenceDate) {
		this.dtCorrespondenceDate = dtCorrespondenceDate;
	}//end of setCorrespondenceDate(Date dtCorrespondenceDate)

	/** Retrieve the CorrespondenceDay
	 * @return Returns the strCorrespondenceDay.
	 */
	public String getCorrespondenceDay() {
		return strCorrespondenceDay;
	}//end of getCorrespondenceDay()

	/** Sets the CorrespondenceDay
	 * @param strCorrespondenceDay The strCorrespondenceDay to set.
	 */
	public void setCorrespondenceDay(String strCorrespondenceDay) {
		this.strCorrespondenceDay = strCorrespondenceDay;
	}//end of setCorrespondenceDay(String strCorrespondenceDay)

	/** Retrieve the CorrespondenceTime
	 * @return Returns the strCorrespondenceTime.
	 */
	public String getCorrespondenceTime() {
		return strCorrespondenceTime;
	}//end of getCorrespondenceTime()

	/** Sets the CorrespondenceTime
	 * @param strCorrespondenceTime The strCorrespondenceTime to set.
	 */
	public void setCorrespondenceTime(String strCorrespondenceTime) {
		this.strCorrespondenceTime = strCorrespondenceTime;
	}//end of setCorrespondenceTime(String strCorrespondenceTime)
	
	//added for Mail-SMS Template for Cigna
	public void setCignaYN(String strCignaYN) {
		this.strCignaYN = strCignaYN;
	}

	public String getCignaYN() {
		return strCignaYN;
	}
	public void setMemberClaimYN(String strMemberClaimYN) {
		this.strMemberClaimYN = strMemberClaimYN;
	}
	public String getMemberClaimYN() {
		return strMemberClaimYN;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getShortFallQueryIds() {
		return shortFallQueryIds;
	}
	public void setShortFallQueryIds(String shortFallQueryIds) {
		this.shortFallQueryIds = shortFallQueryIds;
	}
	public String getRecievedStatus() {
		return recievedStatus;
	}
	public void setRecievedStatus(String recievedStatus) {
		this.recievedStatus = recievedStatus;
	}
}//end of ShortfallVO
