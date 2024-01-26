/**
 * @ (#) CallCenterListVO.java Oct 5, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CallCenterListVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 5, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.customercare;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class CallCenterListVO extends BaseVO{
	
	public String getChildCallType() {
		return strChildCallType;
	}

	public void setChildCallType(String strChildCallType) {
		this.strChildCallType = strChildCallType;
	}

	private String strLogNumber = "";
	private Date dtLogDate = null;
	private String strLogTime = "";
	private String strLogDay = "";
	private String strRecordedBy = "";
	private String strStatusTypeID = "";
	private String strStatusDesc = "";
	private String strDescription = "";
	private String strChildCallContent = "";
	private String strChildMobileNbr = "";
	private String strChildCallType = "";//intx
	 private String strEscalationYN	= "";//intx
	    
	    public String getEscalationYN() {
			return strEscalationYN;
		}

		public void setEscalationYN(String strEscalationYN) {
			this.strEscalationYN = strEscalationYN;
		}
	
	/** Retrieve the CallContent
	 * @return Returns the strChildCallContent.
	 */
	public String getChildCallContent() {
		return strChildCallContent;
	}//end of getChildCallContent()

	/** Sets the CallContent
	 * @param strChildCallContent The strChildCallContent to set.
	 */
	public void setChildCallContent(String strChildCallContent) {
		this.strChildCallContent = strChildCallContent;
	}//end of setChildCallContent(String strChildCallContent)

	/** Retrieve the MobileNbr
	 * @return Returns the strChildMobileNbr.
	 */
	public String getChildMobileNbr() {
		return strChildMobileNbr;
	}//end of getChildMobileNbr()

	/** Sets the MobileNbr
	 * @param strChildMobileNbr The strChildMobileNbr to set.
	 */
	public void setChildMobileNbr(String strChildMobileNbr) {
		this.strChildMobileNbr = strChildMobileNbr;
	}//end of setChildMobileNbr(String strChildMobileNbr)
	
	/** Retrieve the StatusTypeID
	 * @return Returns the strStatusTypeID.
	 */
	public String getStatusTypeID() {
		return strStatusTypeID;
	}//end of getStatusTypeID()

	/** Sets the StatusTypeID
	 * @param strStatusTypeID The strStatusTypeID to set.
	 */
	public void setStatusTypeID(String strStatusTypeID) {
		this.strStatusTypeID = strStatusTypeID;
	}//end of setStatusTypeID(String strStatusTypeID)

	/** Retrieve the LogDate
	 * @return Returns the dtLogDate.
	 */
	public Date getLogDate() {
		return dtLogDate;
	}//end of getLogDate()
	
	/** Retrieve the LogDate
	 * @return Returns the dtLogDate.
	 */
	public String getLogRecordDate() {
		return TTKCommon.getFormattedDate(dtLogDate);
	}//end of getLogRecordDate()
	
	/** Retrieve the LogDate
	 * @return Returns the dtLogDate.
	 */
	public String getLogAttendedDate() {
		return TTKCommon.getFormattedDateHour(dtLogDate);
	}//end of getLogAttendedDate()
	
	/** Sets the LogDate
	 * @param dtLogDate The dtLogDate to set.
	 */
	public void setLogDate(Date dtLogDate) {
		this.dtLogDate = dtLogDate;
	}//end of setLogDate(Date dtLogDate)
	
	/** Retrieve the Description
	 * @return Returns the strDescription.
	 */
	public String getDescription() {
		return strDescription;
	}//end of getDescription()
	
	/** Sets the Description
	 * @param strDescription The strDescription to set.
	 */
	public void setDescription(String strDescription) {
		this.strDescription = strDescription;
	}//end of setDescription(String strDescription)
	
	/** Retrieve the LogDay
	 * @return Returns the strLogDay.
	 */
	public String getLogDay() {
		return strLogDay;
	}//end of getLogDay()
	
	/** Sets the LogDay
	 * @param strLogDay The strLogDay to set.
	 */
	public void setLogDay(String strLogDay) {
		this.strLogDay = strLogDay;
	}//end of setLogDay(String strLogDay)
	
	/** Retrieve the LogNumber
	 * @return Returns the strLogNumber.
	 */
	public String getLogNumber() {
		return strLogNumber;
	}//end of getLogNumber()
	
	/** Sets the LogNumber
	 * @param strLogNumber The strLogNumber to set.
	 */
	public void setLogNumber(String strLogNumber) {
		this.strLogNumber = strLogNumber;
	}//end of setLogNumber(String strLogNumber)
	
	/** Retrieve the LogTime
	 * @return Returns the strLogTime.
	 */
	public String getLogTime() {
		return strLogTime;
	}//end of getLogTime()
	
	/** Sets the LogTime
	 * @param strLogTime The strLogTime to set.
	 */
	public void setLogTime(String strLogTime) {
		this.strLogTime = strLogTime;
	}//end of setLogTime(String strLogTime)
	
	/** Retrieve the RecordedBy
	 * @return Returns the strRecordedBy.
	 */
	public String getRecordedBy() {
		return strRecordedBy;
	}//end of getRecordedBy()
	
	/** Sets the RecordedBy
	 * @param strRecordedBy The strRecordedBy to set.
	 */
	public void setRecordedBy(String strRecordedBy) {
		this.strRecordedBy = strRecordedBy;
	}//end of setRecordedBy(String strRecordedBy)
	
	/** Retrieve the StatusDescription
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()
	
	/** Sets the StatusDescription
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)
	
}//end of CallCenterListVO
