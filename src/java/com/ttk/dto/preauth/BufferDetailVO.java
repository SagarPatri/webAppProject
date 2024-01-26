/**
 * @ (#) BufferDetailVO.java Jun 27, 2006
 * Project 	     : TTK HealthCare Services
 * File          : BufferDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 27, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;

public class BufferDetailVO extends BufferVO{

	private BigDecimal bdAvailBufferAmt = null;

	private Date dtApprovedDate = null;
	private String strApprovedTime = "";
	private String strApprovedDay = "";
	private String strApprovedBy = "";
	private String strRejectedBy = "";
	private Date dtRejectedDate = null;
	private String strRejectedTime = "";
	private String strRejectedDay = "";
	private Long lngPolicyGrpSeqID = null;
	private Long lngBufferHdrSeqID = null;
	private String strBufferRemarks = "";

	
//Modifications as per koc 1216B Change Request
	/* buffer_mode,
		hr_appr_buffer,
		member_buffer,
		utilized_buffer 
		family_buffer
  */
	private String strBufferMode ="";
	private BigDecimal strMemberBufferAmt = null;//total member buffer
	private BigDecimal bdUtilizedMemberBuffer = null;//total member buffer
	private BigDecimal bdHrInsurerBuffAmount=null;
	private BigDecimal bdBufferFamilyCap = null;
	//Modifications as per koc 1216B Change Request
	
	

	/**
	 * @param bufferFamilyCap the bufferFamilyCap to set
	 */
	public void setBufferFamilyCap(BigDecimal bufferFamilyCap) {
		bdBufferFamilyCap = bufferFamilyCap;
	}

	/**
	 * @return the bufferFamilyCap
	 */
	public BigDecimal getBufferFamilyCap() {
		return bdBufferFamilyCap;
	}

	/**
	 * @param hrInsurerBuffAmount the hrInsurerBuffAmount to set
	 */
	public void setHrInsurerBuffAmount(BigDecimal hrInsurerBuffAmount) {
		bdHrInsurerBuffAmount = hrInsurerBuffAmount;
	}

	/**
	 * @return the hrInsurerBuffAmount
	 */
	public BigDecimal getHrInsurerBuffAmount() {
		return bdHrInsurerBuffAmount;
	}

	/**
	 * @param utilizedMemberBuffer the utilizedMemberBuffer to set
	 */
	public void setUtilizedMemberBuffer(BigDecimal utilizedMemberBuffer) {
		bdUtilizedMemberBuffer = utilizedMemberBuffer;
	}

	/**
	 * @return the utilizedMemberBuffer
	 */
	public BigDecimal getUtilizedMemberBuffer() {
		return bdUtilizedMemberBuffer;
	}

	/**
	 * @param memberBufferAmt the memberBufferAmt to set
	 */
	public void setMemberBufferAmt(BigDecimal memberBufferAmt) {
		strMemberBufferAmt = memberBufferAmt;
	}

	/**
	 * @return the memberBufferAmt
	 */
	public BigDecimal getMemberBufferAmt() {
		return strMemberBufferAmt;
	}

	/**
	 * @param bufferMode the bufferMode to set
	 */
	public void setBufferMode(String bufferMode) {
		strBufferMode = bufferMode;
	}

	/**
	 * @return the bufferMode
	 */
	public String getBufferMode() {
		return strBufferMode;
	}


	/** Retrieve the Buffer Header Seq ID
	 * @return Returns the lngBufferHdrSeqID.
	 */
	public Long getBufferHdrSeqID() {
		return lngBufferHdrSeqID;
	}//end of getBufferHdrSeqID()

	/** Sets the Buffer Header Seq ID
	 * @param lngBufferHdrSeqID The lngBufferHdrSeqID to set.
	 */
	public void setBufferHdrSeqID(Long lngBufferHdrSeqID) {
		this.lngBufferHdrSeqID = lngBufferHdrSeqID;
	}//end of setBufferHdrSeqID(Long lngBufferHdrSeqID)

	/** Retrieve the Policy Group Seq ID
	 * @return Returns the lngPolicyGrpSeqID.
	 */
	public Long getPolicyGrpSeqID() {
		return lngPolicyGrpSeqID;
	}//end of getPolicyGrpSeqID()

	/** Sets the Policy Group Seq ID
	 * @param lngPolicyGrpSeqID The lngPolicyGrpSeqID to set.
	 */
	public void setPolicyGrpSeqID(Long lngPolicyGrpSeqID) {
		this.lngPolicyGrpSeqID = lngPolicyGrpSeqID;
	}//end of setPolicyGrpSeqID(Long lngPolicyGrpSeqID)

	/** Retrieve the Rejected Date
	 * @return Returns the dtRejectedDate.
	 */
	public Date getRejectedDate() {
		return dtRejectedDate;
	}//end of getRejectedDate()

	/** Retrieve the Rejected Date
	 * @return Returns the dtRejectedDate.
	 */
	public String getRejectedDateTime() {
		return TTKCommon.getFormattedDate(dtRejectedDate);
	}//end of getRejectedDateTime()

	/** Sets the Rejected Date
	 * @param dtRejectedDate The dtRejectedDate to set.
	 */
	public void setRejectedDate(Date dtRejectedDate) {
		this.dtRejectedDate = dtRejectedDate;
	}//end of setRejectedDate(Date dtRejectedDate)

	/** Retrieve the Rejected Day
	 * @return Returns the strRejectedDay.
	 */
	public String getRejectedDay() {
		return strRejectedDay;
	}//end of getRejectedDay()

	/** Sets the Rejected Day
	 * @param strRejectedDay The strRejectedDay to set.
	 */
	public void setRejectedDay(String strRejectedDay) {
		this.strRejectedDay = strRejectedDay;
	}//end of setRejectedDay(String strRejectedDay)

	/** Retrieve the Rejected Time
	 * @return Returns the strRejectedTime.
	 */
	public String getRejectedTime() {
		return strRejectedTime;
	}//end of getRejectedTime()

	/** Sets the Rejected Time
	 * @param strRejectedTime The strRejectedTime to set.
	 */
	public void setRejectedTime(String strRejectedTime) {
		this.strRejectedTime = strRejectedTime;
	}//end of setRejectedTime(String strRejectedTime)

	/** Retrieve the Available Buffer Amount
	 * @return Returns the bdAvailBufferAmt.
	 */
	public BigDecimal getAvailBufferAmt() {
		return bdAvailBufferAmt;
	}//end of getAvailBufferAmt()

	/** Sets the Available Buffer Amount
	 * @param bdAvailBufferAmt The bdAvailBufferAmt to set.
	 */
	public void setAvailBufferAmt(BigDecimal bdAvailBufferAmt) {
		this.bdAvailBufferAmt = bdAvailBufferAmt;
	}//end of setAvailBufferAmt(BigDecimal bdAvailBufferAmt)

	/** Retrieve the Approved Date
	 * @return Returns the dtApprovedDate.
	 */
	public Date getApprovedDate() {
		return dtApprovedDate;
	}//end of getApprovedDate()

	/** Retrieve the Approved Date
	 * @return Returns the dtApprovedDate.
	 */
	public String getApprovedDateTime() {
		return TTKCommon.getFormattedDate(dtApprovedDate);
	}//end of getApprovedDateTime()

	/** Sets the Approved Date
	 * @param dtApprovedDate The dtApprovedDate to set.
	 */
	public void setApprovedDate(Date dtApprovedDate) {
		this.dtApprovedDate = dtApprovedDate;
	}//end of setApprovedDate(Date dtApprovedDate)

	/** Retrieve the ApprovedBy
	 * @return Returns the strApprovedBy.
	 */
	public String getApprovedBy() {
		return strApprovedBy;
	}//end of getApprovedBy()

	/** Sets the ApprovedBy
	 * @param strApprovedBy The strApprovedBy to set.
	 */
	public void setApprovedBy(String strApprovedBy) {
		this.strApprovedBy = strApprovedBy;
	}//end of setApprovedBy(String strApprovedBy)

	/** Retrieve the Approved Day
	 * @return Returns the strApprovedDay.
	 */
	public String getApprovedDay() {
		return strApprovedDay;
	}//end of getApprovedDay()

	/** Sets the Approved Day
	 * @param strApprovedDay The strApprovedDay to set.
	 */
	public void setApprovedDay(String strApprovedDay) {
		this.strApprovedDay = strApprovedDay;
	}//end of setApprovedDay(String strApprovedDay)

	/** Retrieve the Approved Time
	 * @return Returns the strApprovedTime.
	 */
	public String getApprovedTime() {
		return strApprovedTime;
	}//end of getApprovedTime()

	/** Sets the Approved Time
	 * @param strApprovedTime The strApprovedTime to set.
	 */
	public void setApprovedTime(String strApprovedTime) {
		this.strApprovedTime = strApprovedTime;
	}//end of setApprovedTime(String strApprovedTime)

	/** Retrieve the RejectedBy
	 * @return Returns the strRejectedBy.
	 */
	public String getRejectedBy() {
		return strRejectedBy;
	}//end of getRejectedBy()

	/** Sets the RejectedBy
	 * @param strRejectedBy The strRejectedBy to set.
	 */
	public void setRejectedBy(String strRejectedBy) {
		this.strRejectedBy = strRejectedBy;
	}//end of setRejectedBy(String strRejectedBy)

	/** Retrieve the Buffer Remarks
	 * @return Returns the strBufferRemarks.
	 */
	public String getBufferRemarks() {
		return strBufferRemarks;
	}//end of getBufferRemarks()

	/** Sets the Buffer Remarks
	 * @param strBufferRemarks The strBufferRemarks to set.
	 */
	public void setBufferRemarks(String strBufferRemarks) {
		this.strBufferRemarks = strBufferRemarks;
	}//end of setBufferRemarks(String strBufferRemarks)

}//end of BufferDetailVO
