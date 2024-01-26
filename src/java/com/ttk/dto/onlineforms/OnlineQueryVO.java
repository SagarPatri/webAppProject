/**
 * @ (#) OnlineQueryVO.java Oct 22, 2008
 * Project 	     : TTK HealthCare Services
 * File          : OnlineQueryVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.onlineforms;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * @author ramakrishna_km
 *
 */
public class OnlineQueryVO extends BaseVO{
	
	private Long lngQueryDtlSeqID = null;
	private String strQueryDesc = "";
	private Date dtLatestReqDate = null;
	private Date dtRespondedDate = null;
	private String strTTKRemarks = "";
	private String strOnlineEditYN = "";
	private String strSupportEditYN = "";
	private String strSubmittedYN = "Y";
	private String strTtkSubmittedYN = "";
	private String strStatus = "";
	private String strLatestReqTime = "";
	private String strLatestReqDay = "";
	private String strRespondedTime = "";
	private String strRespondedDay = "";
	private String strQueryGnlTypeID="";//query_general_type_id
	private String strQueryTypeDesc = "";//QUERY_TYPE
	private String strQueryFeedbackTypeID ="";
	private String strFeedBackDesc="";
	private String strQueryRemarksDesc="";
	private String strFeedbackStatus="";
	private String strFeedBackSubmittedYN="Y";
	private String strFeedbackRemarks="";
	private String strQueryFeedbackStatusID="";
	private String strTtkfeedBackSubmittedYN="";
	private String strTtkfeedBackRemarks="";
	private Date dtFeedbackSubmitDate=null;
	private String strFeedbackSubmitTime="";
	private String strFeedbackSubmitDay="";
	private Date dtClarifiedDate=null;
	private String strClarifiedDay="";
	private String strClarifiedTime="";
	private String strFeedbackOnlineEditYN="";
	private String strFeedbackSupportEditYN="";
	private String strQueryFeedbackStatusDesc="";
	
	/** Retrieve the QueryFeedbackStatusDesc
	 * @return the strQueryFeedbackStatusDesc
	 */
	public String getQueryFeedbackStatusDesc() {
		return strQueryFeedbackStatusDesc;
	}//end of getQueryFeedbackStatusDesc()

	/** Sets the  QueryFeedbackStatusDesc
	 * @param strQueryFeedbackStatusDesc the strQueryFeedbackStatusDesc to set
	 */
	public void setQueryFeedbackStatusDesc(String strQueryFeedbackStatusDesc) {
		this.strQueryFeedbackStatusDesc = strQueryFeedbackStatusDesc;
	}//sets the setQueryFeedbackStatusDesc(String strQueryFeedbackStatusDesc)

	/** Retrieve the FeedbackOnlineEditYN
	 * @return the strFeedbackOnlineEditYN
	 */
	public String getFeedbackOnlineEditYN() {
		return strFeedbackOnlineEditYN;
	}//end of getFeedbackOnlineEditYN()

	/** Sets the FeedbackOnlineEditYN
	 * @param strFeedbackOnlineEditYN the strFeedbackOnlineEditYN to set
	 */
	public void setFeedbackOnlineEditYN(String strFeedbackOnlineEditYN) {
		this.strFeedbackOnlineEditYN = strFeedbackOnlineEditYN;
	}//end of setFeedbackOnlineEditYN(String strFeedbackOnlineEditYN)

	/** Retrieve the FeedbackSupportEditYN
	 * @return the strFeedbackSupportEditYN
	 */
	public String getFeedbackSupportEditYN() {
		return strFeedbackSupportEditYN;
	}//end of getFeedbackSupportEditYN()

	/** Sets the FeedbackSupportEditYN
	 * @param strFeedbackSupportEditYN the strFeedbackSupportEditYN to set
	 */
	public void setFeedbackSupportEditYN(String strFeedbackSupportEditYN) {
		this.strFeedbackSupportEditYN = strFeedbackSupportEditYN;
	}//end of setFeedbackSupportEditYN(String strFeedbackSupportEditYN)

	/** Retrieve the FeedbackSubmitDate
	 * @return the dtFeedbackSubmitDate
	 */
	public Date getFeedbackSubmitDate() {
		return dtFeedbackSubmitDate;
	}//end of getFeedbackSubmitDate()

	/** Sets the FeedbackSubmitDate
	 * @param dtFeedbackSubmitDate the dtFeedbackSubmitDate to set
	 */
	public void setFeedbackSubmitDate(Date dtFeedbackSubmitDate) {
		this.dtFeedbackSubmitDate = dtFeedbackSubmitDate;
	}//end of setFeedbackSubmitDate(Date dtFeedbackSubmitDate)

	/** Retrieve the FeedbackSubmitTime
	 * @return the strFeedbackSubmitTime
	 */
	public String getFeedbackSubmitTime() {
		return strFeedbackSubmitTime;
	}//end of getFeedbackSubmitTime()

	/** Sets the FeedbackSubmitTime
	 * @param strFeedbackSubmitTime the strFeedbackSubmitTime to set
	 */
	public void setFeedbackSubmitTime(String strFeedbackSubmitTime) {
		this.strFeedbackSubmitTime = strFeedbackSubmitTime;
	}//end of setFeedbackSubmitTime(String strFeedbackSubmitTime)

	/** Retrieve the FeedbackSubmitDay
	 * @return the strFeedbackSubmitDay
	 */
	public String getFeedbackSubmitDay() {
		return strFeedbackSubmitDay;
	}//end of getFeedbackSubmitDay()

	/** Sets the FeedbackSubmitDay
	 * @param strFeedbackSubmitDay the strFeedbackSubmitDay to set
	 */
	public void setFeedbackSubmitDay(String strFeedbackSubmitDay) {
		this.strFeedbackSubmitDay = strFeedbackSubmitDay;
	}//end of setFeedbackSubmitDay(String strFeedbackSubmitDay)

	/** Retrieve the ClarifiedDate
	 * @return the dtClarifiedDate
	 */
	public Date getClarifiedDate() {
		return dtClarifiedDate;
	}//end of getClarifiedDate() 

	/** Sets the ClarifiedDate
	 * @param dtClarifiedDate the dtClarifiedDate to set
	 */
	public void setClarifiedDate(Date dtClarifiedDate) {
		this.dtClarifiedDate = dtClarifiedDate;
	}//end of setClarifiedDate(Date dtClarifiedDate)

	/** Retrieve the ClarifiedDay
	 * @return the strClarifiedDay
	 */
	public String getClarifiedDay() {
		return strClarifiedDay;
	}//end of getClarifiedDay()

	/** sets the ClarifiedDay
	 * @param strClarifiedDay the strClarifiedDay to set
	 */
	public void setClarifiedDay(String strClarifiedDay) {
		this.strClarifiedDay = strClarifiedDay;
	}//end of setClarifiedDay(String strClarifiedDay)

	/** Retrieve the ClarifiedTime
	 * @return the strClarifiedTime
	 */
	public String getClarifiedTime() {
		return strClarifiedTime;
	}//end of getClarifiedTime()

	/** Sets the ClarifiedTime
	 * @param strClarifiedTime the strClarifiedTime to set
	 */
	public void setClarifiedTime(String strClarifiedTime) {
		this.strClarifiedTime = strClarifiedTime;
	}//end of setClarifiedTime(String strClarifiedTime)

	/** Retrieve the TtkfeedBackRemarks
	 * @return the strTtkfeedBackRemarks
	 */
	public String getTtkfeedBackRemarks() {
		return strTtkfeedBackRemarks;
	}//end of getTtkfeedBackRemarks()

	/** Sets the TtkfeedBackRemarks
	 * @param strTtkfeedBackRemarks the strTtkfeedBackRemarks to set
	 */
	public void setTtkfeedBackRemarks(String strTtkfeedBackRemarks) {
		this.strTtkfeedBackRemarks = strTtkfeedBackRemarks;
	}//end of setTtkfeedBackRemarks(String strTtkfeedBackRemarks)

	/** Retrieve the TtkfeedBackSubmittedYN
	 * @return the strTtkfeedBackSubmittedYN
	 */
	public String getTtkfeedBackSubmittedYN() {
		return strTtkfeedBackSubmittedYN;
	}//end of getTtkfeedBackSubmittedYN()

	/** Sets the TtkfeedBackSubmittedYN
	 * @param strTtkfeedBackSubmittedYN the strTtkfeedBackSubmittedYN to set
	 */
	public void setTtkfeedBackSubmittedYN(String strTtkfeedBackSubmittedYN) {
		this.strTtkfeedBackSubmittedYN = strTtkfeedBackSubmittedYN;
	}//end of setTtkfeedBackSubmittedYN(String strTtkfeedBackSubmittedYN)

	/** Retrieve the FeedBackDesc
	 * @return the strFeedBackDesc
	 */
	public String getFeedBackDesc() {
		return strFeedBackDesc;
	}//end of getFeedBackDesc()

	/** Sets the FeedBackDesc
	 * @param strFeedBackDesc the strFeedBackDesc to set
	 */
	public void setFeedBackDesc(String strFeedBackDesc) {
		this.strFeedBackDesc = strFeedBackDesc;
	}//end of setFeedBackDesc(String strFeedBackDesc)

	/** Retrieve the QueryFeedbackStatusID
	 * @return the strQueryFeedbackStatusID
	 */
	public String getQueryFeedbackStatusID() {
		return strQueryFeedbackStatusID;
	}//end of getQueryFeedbackStatusID()

	/** Sets the QueryFeedbackStatusID
	 * @param strQueryFeedbackStatusID the strQueryFeedbackStatusID to set
	 */
	public void setQueryFeedbackStatusID(String strQueryFeedbackStatusID) {
		this.strQueryFeedbackStatusID = strQueryFeedbackStatusID;
	}//end of setQueryFeedbackStatusID(String strQueryFeedbackStatusID)

	/** Retrieve the FeedbackRemarks
	 * @return the strFeedbackRemarks
	 */
	public String getFeedbackRemarks() {
		return strFeedbackRemarks;
	}//end of getFeedbackRemarks()

	/** Sets the FeedbackRemarks
	 * @param strFeedbackRemarks the strFeedbackRemarks to set
	 */
	public void setFeedbackRemarks(String strFeedbackRemarks) {
		this.strFeedbackRemarks = strFeedbackRemarks;
	}//end of setFeedbackRemarks(String strFeedbackRemarks)

	/** Retrieve the QueryFeedbackTypeID
	 * @return the strQueryFeedbackTypeID
	 */
	public String getQueryFeedbackTypeID() {
		return strQueryFeedbackTypeID;
	}//end of getQueryFeedbackTypeID()

	/** Sets the QueryFeedbackTypeID
	 * @param strQueryFeedbackTypeID the strQueryFeedbackTypeID to set
	 */
	public void setQueryFeedbackTypeID(String strQueryFeedbackTypeID) {
		this.strQueryFeedbackTypeID = strQueryFeedbackTypeID;
	}//end of setQueryFeedbackTypeID(String strQueryFeedbackTypeID)

	/** Retrieve the QueryRemarksDesc
	 * @return the strQueryRemarksDesc
	 */
	public String getQueryRemarksDesc() {
		return strQueryRemarksDesc;
	}//end of getQueryRemarksDesc()

	/** Sets the QueryRemarksDesc
	 * @param strQueryRemarksDesc the strQueryRemarksDesc to set
	 */
	public void setQueryRemarksDesc(String strQueryRemarksDesc) {
		this.strQueryRemarksDesc = strQueryRemarksDesc;
	}//end of setQueryRemarksDesc(String strQueryRemarksDesc)

	/** Retrieve the FeedbackStatus
	 * @return the strFeedbackStatus
	 */
	public String getFeedbackStatus() {
		return strFeedbackStatus;
	}//end of getFeedbackStatus()

	/** sets the FeedbackStatus
	 * @param strFeedbackStatus the strFeedbackStatus to set
	 */
	public void setFeedbackStatus(String strFeedbackStatus) {
		this.strFeedbackStatus = strFeedbackStatus;
	}//end of setFeedbackStatus(String strFeedbackStatus)

	/** Retrieve the FeedBackSubmittedYN
	 * @return the strFeedBackSubmittedYN
	 */
	public String getFeedBackSubmittedYN() {
		return strFeedBackSubmittedYN;
	}//end of getFeedBackSubmittedYN()

	/** Sets the FeedBackSubmittedYN
	 * @param strFeedBackSubmittedYN the strFeedBackSubmittedYN to set
	 */
	public void setFeedBackSubmittedYN(String strFeedBackSubmittedYN) {
		this.strFeedBackSubmittedYN = strFeedBackSubmittedYN;
	}//end of setFeedBackSubmittedYN(String strFeedBackSubmittedYN)

	/** Retrieve the QueryTypeDesc
	 * @return Returns the strQueryTypeDesc.
	 */
	public String getQueryTypeDesc() {
		return strQueryTypeDesc;
	}//end of getQueryTypeDesc()

	/** Sets the QueryTypeDesc
	 * @param strQueryTypeDesc The strQueryTypeDesc to set.
	 */
	public void setQueryTypeDesc(String strQueryTypeDesc) {
		this.strQueryTypeDesc = strQueryTypeDesc;
	}//end of setQueryTypeDesc(String strQueryTypeDesc)

	/** Retrieve the RespondedDay
	 * @return Returns the strRespondedDay.
	 */
	public String getRespondedDay() {
		return strRespondedDay;
	}//end of getRespondedDay()

	/** Sets the RespondedDay
	 * @param strRespondedDay The strRespondedDay to set.
	 */
	public void setRespondedDay(String strRespondedDay) {
		this.strRespondedDay = strRespondedDay;
	}//end of setRespondedDay(String strRespondedDay)

	/** Retrieve the RespondedTime
	 * @return Returns the strRespondedTime.
	 */
	public String getRespondedTime() {
		return strRespondedTime;
	}//end of getRespondedTime()

	/** Sets the RespondedTime
	 * @param strRespondedTime The strRespondedTime to set.
	 */
	public void setRespondedTime(String strRespondedTime) {
		this.strRespondedTime = strRespondedTime;
	}//end of setRespondedTime(String strRespondedTime)

	/** Retrieve the Latest Requested Day
	 * @return Returns the strLatestReqDay.
	 */
	public String getLatestReqDay() {
		return strLatestReqDay;
	}//end of getLatestReqDay()

	/** Sets the Latest Requested Day
	 * @param strLatestReqDay The strLatestReqDay to set.
	 */
	public void setLatestReqDay(String strLatestReqDay) {
		this.strLatestReqDay = strLatestReqDay;
	}//end of setLatestReqDay(String strLatestReqDay)

	/** Retrieve the Latest Requested Time
	 * @return Returns the strLatestReqTime.
	 */
	public String getLatestReqTime() {
		return strLatestReqTime;
	}//end of getLatestReqTime()

	/** Sets the Latest Requested Time
	 * @param strLatestReqTime The strLatestReqTime to set.
	 */
	public void setLatestReqTime(String strLatestReqTime) {
		this.strLatestReqTime = strLatestReqTime;
	}//end of setLatestReqTime(String strLatestReqTime)

	/** Retrieve the Status
	 * @return Returns the strStatus.
	 */
	public String getStatus() {
		return strStatus;
	}//end of getStatus()
	
	/** Sets the Status
	 * @param strStatus The strStatus to set.
	 */
	public void setStatus(String strStatus) {
		this.strStatus = strStatus;
	}//end of setStatus(String strStatus)
	
	/** Retrieve the LatestReqDate
	 * @return Returns the dtLatestReqDate.
	 */
	public Date getLatestReqDate() {
		return dtLatestReqDate;
	}//end of getLatestReqDate()
	
	/** Retrieve the LatestReqDate
	 * @return Returns the dtLatestReqDate.
	 */
	public String getAssLatestReqDate() {
		return TTKCommon.getFormattedDateHour(dtLatestReqDate);
	}//end of getAssLatestReqDate()

	/** Sets the LatestReqDate
	 * @param dtLatestReqDate The dtLatestReqDate to set.
	 */
	public void setLatestReqDate(Date dtLatestReqDate) {
		this.dtLatestReqDate = dtLatestReqDate;
	}//end of setLatestReqDate(Date dtLatestReqDate)

	/** Retrieve the RespondedDate
	 * @return Returns the dtRespondedDate.
	 */
	public Date getRespondedDate() {
		return dtRespondedDate;
	}//end of getRespondedDate()
	
	/** Retrieve the RespondedDate
	 * @return Returns the dtRespondedDate.
	 */
	public String getTTKRespondedDate() {
		return TTKCommon.getFormattedDate(dtRespondedDate);
	}//end of getRespondedDate()

	/** Sets the RespondedDate
	 * @param dtRespondedDate The dtRespondedDate to set.
	 */
	public void setRespondedDate(Date dtRespondedDate) {
		this.dtRespondedDate = dtRespondedDate;
	}//end of setRespondedDate(Date dtRespondedDate)
	

	/** Retrieve the QueryDtlSeqID
	 * @return Returns the lngQueryDtlSeqID.
	 */
	public Long getQueryDtlSeqID() {
		return lngQueryDtlSeqID;
	}//end of getQueryDtlSeqID()

	/** Sets the QueryDtlSeqID
	 * @param lngQueryDtlSeqID The lngQueryDtlSeqID to set.
	 */
	public void setQueryDtlSeqID(Long lngQueryDtlSeqID) {
		this.lngQueryDtlSeqID = lngQueryDtlSeqID;
	}//end of setQueryDtlSeqID(Long lngQueryDtlSeqID)

	/** Retrieve the OnlineEditYN
	 * @return Returns the strOnlineEditYN.
	 */
	public String getOnlineEditYN() {
		return strOnlineEditYN;
	}//end of getOnlineEditYN()

	/** Sets the OnlineEditYN
	 * @param strOnlineEditYN The strOnlineEditYN to set.
	 */
	public void setOnlineEditYN(String strOnlineEditYN) {
		this.strOnlineEditYN = strOnlineEditYN;
	}//end of setOnlineEditYN(String strOnlineEditYN)

	/** Retrieve the QueryDesc
	 * @return Returns the strQueryDesc.
	 */
	public String getQueryDesc() {
		return strQueryDesc;
	}//end of getQueryDesc()

	/** Sets the QueryDesc
	 * @param strQueryDesc The strQueryDesc to set.
	 */
	public void setQueryDesc(String strQueryDesc) {
		this.strQueryDesc = strQueryDesc;
	}//end of setQueryDesc(String strQueryDesc)

	/** Retrieve the SupportEditYN
	 * @return Returns the strSupportEditYN.
	 */
	public String getSupportEditYN() {
		return strSupportEditYN;
	}//end of getSupportEditYN()

	/** Sets the SupportEditYN
	 * @param strSupportEditYN The strSupportEditYN to set.
	 */
	public void setSupportEditYN(String strSupportEditYN) {
		this.strSupportEditYN = strSupportEditYN;
	}//end of setSupportEditYN(String strSupportEditYN)

	/** Retrieve the TTKRemarks
	 * @return Returns the strTTKRemarks.
	 */
	public String getTTKRemarks() {
		return strTTKRemarks;
	}//end of getTTKRemarks()

	/** Sets the TTKRemarks
	 * @param strTTKRemarks The strTTKRemarks to set.
	 */
	public void setTTKRemarks(String strTTKRemarks) {
		this.strTTKRemarks = strTTKRemarks;
	}//end of setTTKRemarks(String strTTKRemarks)

	/** Retrieve the SubmittedYN
	 * @return Returns the strSubmittedYN.
	 */
	public String getSubmittedYN() {
		return strSubmittedYN;
	}//end of getSubmittedYN()
	
	/** Sets the SubmittedYN
	 * @param strSubmittedYN The strSubmittedYN to set.
	 */
	public void setSubmittedYN(String strSubmittedYN) {
		this.strSubmittedYN = strSubmittedYN;
	}//end of setSubmittedYN(String strSubmittedYN)
	
	/** Retrieve the ttk SubmittedYN
	 * @return Returns the strSubmittedYN.
	 */
	public String getTtkSubmittedYN() {
		return strTtkSubmittedYN;
	}//end of getTtkSubmittedYN()
	
	/** Sets the ttk SubmittedYN
	 * @param strSubmittedYN The strSubmittedYN to set.
	 */
	public void setTtkSubmittedYN(String strTtkSubmittedYN) {
		this.strTtkSubmittedYN = strTtkSubmittedYN;
	}//end of setTtkSubmittedYN(String strTtkSubmittedYN)
	
	/** Retrieve the strQueryGnlTypeID
	 * @return Returns the strQueryGnlTypeID.
	 */
	public String getQueryGnlTypeID() {
		return strQueryGnlTypeID;
	}//end of getQueryGnlTypeID()
	
	/** Sets the strQueryGnlTypeID
	 * @param strQueryGnlTypeID The strQueryGnlTypeID to set.
	 */
	public void setQueryGnlTypeID(String strQueryGnlTypeID) {
		this.strQueryGnlTypeID = strQueryGnlTypeID;
	}//end of setQueryGnlTypeID(String strQueryGnlTypeID)

}//end of OnlineQueryVO
