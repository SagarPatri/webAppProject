/**
 * @ (#) BufferVO.java Jun 27, 2006
 * Project 	     : TTK HealthCare Services
 * File          : BufferVO.java
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

import org.apache.struts.upload.FormFile;

import com.ttk.common.TTKCommon;

public class BufferVO extends PreAuthVO{

	private Long lngBufferDtlSeqID = null;
	private String strBufferNbr = "";
	private String strStatusDesc = "";
	private Date dtRequestedDate = null;
	private String strRequestedTime = "";
	private String strRequestedDay = "";
	private BigDecimal bdRequestedAmt = null;
    private BigDecimal bdApprovedAmt = null;
	private String strEditYN = "";
	private String strBufferType = "";
	private String strBufferType1 = "";
	private String strClaimType = "";
	private String strFileName="";
	
	private	FormFile file=null;
	private String  strDocumentType="";
	private String strHrAppYN = "";
	/** Retrieve the RequestedDay
	 * @return Returns the strRequestedDay.
	 */
	public String getRequestedDay() {
		return strRequestedDay;
	}//end of getRequestedDay()

	/** Sets the RequestedDay
	 * @param strRequestedDay The strRequestedDay to set.
	 */
	public void setRequestedDay(String strRequestedDay) {
		this.strRequestedDay = strRequestedDay;
	}//end of setRequestedDay(String strRequestedDay)

	/** Retrieve the RequestedTime
	 * @return Returns the strRequestedTime.
	 */
	public String getRequestedTime() {
		return strRequestedTime;
	}//end of getRequestedTime()

	/** Sets the RequestedTime
	 * @param strRequestedTime The strRequestedTime to set.
	 */
	public void setRequestedTime(String strRequestedTime) {
		this.strRequestedTime = strRequestedTime;
	}//end of setRequestedTime(String strRequestedTime)

	/** Retrieve the EditYN
	 * @return Returns the strEditYN.
	 */
	public String getEditYN() {
		return strEditYN;
	}//end of getEditYN()

	/** Sets the EditYN
	 * @param strEditYN The strEditYN to set.
	 */
	public void setEditYN(String strEditYN) {
		this.strEditYN = strEditYN;
	}//end of setEditYN(String strEditYN)

	/** Retrieve the Requested Amount
	 * @return Returns the bdRequestedAmt.
	 */
	public BigDecimal getRequestedAmt() {
		return bdRequestedAmt;
	}//end of getRequestedAmt()

	/** Sets the Requested Amount
	 * @param bdRequestedAmt The bdRequestedAmt to set.
	 */
	public void setRequestedAmt(BigDecimal bdRequestedAmt) {
		this.bdRequestedAmt = bdRequestedAmt;
	}//end of setRequestedAmt(BigDecimal bdRequestedAmt)

    /** Retrieve the Approved Amount
     * @return Returns the bdApprovedAmt.
     */
    public BigDecimal getApprovedAmt() {
        return bdApprovedAmt;
    }//end of getApprovedAmt()

    /** Sets the Approved Amount
     * @param bdApprovedAmt The bdApprovedAmt to set.
     */
    public void setApprovedAmt(BigDecimal bdApprovedAmt) {
        this.bdApprovedAmt = bdApprovedAmt;
    }//end of setApprovedAmt(BigDecimal bdApprovedAmt)
	/** Retrieve the Requested Date
	 * @return Returns the dtRequestedDate.
	 */
	public String getBufferRequestedDate() {
		return TTKCommon.getFormattedDate(dtRequestedDate);
	}//end of getBufferRequestedDate()

	/** Retrieve the Requested Date
	 * @return Returns the dtRequestedDate.
	 */
	public Date getRequestedDate() {
		return dtRequestedDate;
	}//end of getRequestedDate()

	/** Retrieve the Requested Date
	 * @return Returns the dtRequestedDate.
	 */
	public String getRequestedDateTime() {
		return TTKCommon.getFormattedDateHour(dtRequestedDate);
	}//end of getRequestedDateTime()

	/** Sets the Requested Date
	 * @param dtRequestedDate The dtRequestedDate to set.
	 */
	public void setRequestedDate(Date dtRequestedDate) {
		this.dtRequestedDate = dtRequestedDate;
	}//end of setRequestedDate(Date dtRequestedDate)

	/** Retrieve the BufferDtlSeqID
	 * @return Returns the lngBufferDtlSeqID.
	 */
	public Long getBufferDtlSeqID() {
		return lngBufferDtlSeqID;
	}//end of getBufferDtlSeqID()

	/** Sets the BufferDtlSeqID
	 * @param lngBufferDtlSeqID The lngBufferDtlSeqID to set.
	 */
	public void setBufferDtlSeqID(Long lngBufferDtlSeqID) {
		this.lngBufferDtlSeqID = lngBufferDtlSeqID;
	}//end of setBufferDtlSeqID(Long lngBufferDtlSeqID)

	/** Retrieve the BufferNbr
	 * @return Returns the strBufferNbr.
	 */
	public String getBufferNbr() {
		return strBufferNbr;
	}//end of getBufferNbr()

	/** Sets the BufferNbr
	 * @param strBufferNbr The strBufferNbr to set.
	 */
	public void setBufferNbr(String strBufferNbr) {
		this.strBufferNbr = strBufferNbr;
	}//end of setBufferNbr(String strBufferNbr)

	/** Retrieve the Status Description
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()

	/** Sets the Status Description
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)

	public String getBufferType() {
		return strBufferType;
	}

	public void setBufferType(String strBufferType) {
		this.strBufferType = strBufferType;
	}

	public String getClaimType() {
		return strClaimType;
	}

	public void setClaimType(String strClaimType) {
		this.strClaimType = strClaimType;
	}

	public String getFileName() {
		return strFileName;
	}

	public void setFileName(String strFileName) {
		this.strFileName = strFileName;
	}

	public String getBufferType1() {
		return strBufferType1;
	}

	public void setBufferType1(String strBufferType1) {
		this.strBufferType1 = strBufferType1;
	}

	public String getDocumentType() {
		return strDocumentType;
	}

	public void setDocumentType(String strDocumentType) {
		this.strDocumentType = strDocumentType;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getHrAppYN() {
		return strHrAppYN;
	}

	public void setHrAppYN(String strHrAppYN) {
		this.strHrAppYN = strHrAppYN;
	}

}//end of BufferVO
