/**
 * @ (#) OnlineAssistanceVO.java Oct 22, 2008
 * Project 	     : TTK HealthCare Services
 * File          : OnlineAssistanceVO.java
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
public class OnlineAssistanceVO extends BaseVO{
	
	private Long lngQueryHdrSeqId = null;
	private String strRequestID = "";
	private Date dtLatestReqDate = null;
	private Date dtRespondedDate = null;
	private String strStatus = "";
	private String strPolicyNbr = "";
	private String strEnrollmentNbr = "";
	private String strEmpname = "";
	private String strFeedBackResponse="";
	
	/** Retrieve the FeedBackResponse
	 * @return the strFeedBackResponse
	 */
	public String getFeedBackResponse() {
		return strFeedBackResponse;
	}//end of getFeedBackResponse()

	/** Sets the FeedBackResponse
	 * @param strFeedBackResponse the strFeedBackResponse to set
	 */
	public void setFeedBackResponse(String strFeedBackResponse) {
		this.strFeedBackResponse = strFeedBackResponse;
	}//end of setFeedBackResponse(String strFeedBackResponse)

	/** Retrieve the Empname
	 * @return Returns the strEmpname.
	 */
	public String getEmpname() {
		return strEmpname;
	}//end of getEmpname()

	/** Sets the Empname
	 * @param strEmpname The strEmpname to set.
	 */
	public void setEmpname(String strEmpname) {
		this.strEmpname = strEmpname;
	}//end of setEmpname(String strEmpname)

	/** Retrieve the EnrollmentNbr
	 * @return Returns the strEnrollmentNbr.
	 */
	public String getEnrollmentNbr() {
		return strEnrollmentNbr;
	}//end of getEnrollmentNbr()

	/** Sets the EnrollmentNbr
	 * @param strEnrollmentNbr The strEnrollmentNbr to set.
	 */
	public void setEnrollmentNbr(String strEnrollmentNbr) {
		this.strEnrollmentNbr = strEnrollmentNbr;
	}//end of setEnrollmentNbr(String strEnrollmentNbr)

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
		return TTKCommon.getFormattedDateHour(dtRespondedDate);
	}//end of getTTKRespondedDate()
	
	/** Sets the RespondedDate
	 * @param dtRespondedDate The dtRespondedDate to set.
	 */
	public void setRespondedDate(Date dtRespondedDate) {
		this.dtRespondedDate = dtRespondedDate;
	}//end of setRespondedDate(Date dtRespondedDate)
	
	/** Retrieve the Latest Requested Date
	 * @return Returns the dtLatestReqDate.
	 */
	public Date getLatestReqDate() {
		return dtLatestReqDate;
	}//end of getLatestRequestedDate()
	
	/** Retrieve the Latest Requested Date
	 * @return Returns the dtLatestReqDate.
	 */
	public String getAssLatestReqDate() {
		return TTKCommon.getFormattedDateHour(dtLatestReqDate);
	}//end of getAssLatestReqDate()
	
	/** Sets the Latest Requested Date
	 * @param dtLatestReqDate The dtLatestReqDate to set.
	 */
	public void setLatestReqDate(Date dtLatestReqDate) {
		this.dtLatestReqDate = dtLatestReqDate;
	}//end of setLatestReqDate(Date dtLatestReqDate)
	
	/** Retrieve the QueryHdrSeqId
	 * @return Returns the lngQueryHdrSeqId.
	 */
	public Long getQueryHdrSeqId() {
		return lngQueryHdrSeqId;
	}//end of getQueryHdrSeqId()
	
	/** Sets the QueryHdrSeqId
	 * @param lngQueryHdrSeqId The lngQueryHdrSeqId to set.
	 */
	public void setQueryHdrSeqId(Long lngQueryHdrSeqId) {
		this.lngQueryHdrSeqId = lngQueryHdrSeqId;
	}//end of setQueryHdrSeqId(Long lngQueryHdrSeqId)
	
	/** Retrieve the RequestID
	 * @return Returns the strRequestID.
	 */
	public String getRequestID() {
		return strRequestID;
	}//end of getRequestID()
	
	/** Sets the RequestID
	 * @param strRequestID The strRequestID to set.
	 */
	public void setRequestID(String strRequestID) {
		this.strRequestID = strRequestID;
	}//end of setRequestID(String strRequestID)
	
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
	
}//end of OnlineAssistanceVO
