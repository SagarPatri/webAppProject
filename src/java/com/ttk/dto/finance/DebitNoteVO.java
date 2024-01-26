/**
 * @ (#) DebitNoteVO.java Sep 11, 2007
 * Project 	     : TTK HealthCare Services
 * File          : DebitNoteVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 11, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.finance;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO contains float DebitNote information.
 */
public class DebitNoteVO extends BaseVO{
	
	private Long lngFloatSeqID = null;
	private Long lngDebitNoteSeqID = null;
	private String strDebitNoteNbr = "";
	private String strDebitNoteTypeID = "";
	private String strDebitNoteDesc = "";
	private Date dtDebitNoteDate = null;
	private Date dtFinalDate = null;
	private String strRemarks = "";
	private BigDecimal bdDebitNoteAmt = null;
	
	/** Retrieve the DebitNoteAmt
	 * @return Returns the bdDebitNoteAmt.
	 */
	public BigDecimal getDebitNoteAmt() {
		return bdDebitNoteAmt;
	}//end of getDebitNoteAmt()
	
	/** Sets the DebitNoteAmt
	 * @param bdDebitNoteAmt The bdDebitNoteAmt to set.
	 */
	public void setDebitNoteAmt(BigDecimal bdDebitNoteAmt) {
		this.bdDebitNoteAmt = bdDebitNoteAmt;
	}//end of setDebitNoteAmt(BigDecimal bdDebitNoteAmt)
	

	/** Retrieve the DebitNoteDate
	 * @return Returns the dtDebitNoteDate.
	 */
		public Date getDebitNoteDate() {
		return dtDebitNoteDate;
	}//end of getDebitNoteDate()
	
	
		
	public String getDbNoteDate() {
		return TTKCommon.getFormattedDate(dtDebitNoteDate);
	}//end of getDbNoteDate()
	
	
	/** Sets the DebitNoteDate
	 * @param dtDebitNoteDate The dtDebitNoteDate to set.
	 */
	public void setDebitNoteDate(Date dtDebitNoteDate) {
		this.dtDebitNoteDate = dtDebitNoteDate;
	}//end of setDebitNoteDate(Date dtDebitNoteDate)
	
	public Date getFinalDate() {
		return dtFinalDate;
	}

	public void setFinalDate(Date dtFinalDate) {
		this.dtFinalDate = dtFinalDate;
	}

	public String getDbFinalDate() {
		return TTKCommon.getFormattedDate(dtFinalDate);
	}//end of getDraftDate
	
	/** Retrieve the DebitNoteSeqID
	 * @return Returns the lngDebitNoteSeqID.
	 */
	public Long getDebitNoteSeqID() {
		return lngDebitNoteSeqID;
	}//end of getDebitNoteSeqID()
	
	/** Sets the DebitNoteSeqID
	 * @param lngDebitNoteSeqID The lngDebitNoteSeqID to set.
	 */
	public void setDebitNoteSeqID(Long lngDebitNoteSeqID) {
		this.lngDebitNoteSeqID = lngDebitNoteSeqID;
	}//end of setDebitNoteSeqID(Long lngDebitNoteSeqID)
	
	/** Retrieve the DebitNoteDesc
	 * @return Returns the strDebitNoteDesc.
	 */
	public String getDebitNoteDesc() {
		return strDebitNoteDesc;
	}//end of getDebitNoteDesc()
	
	/** Sets the DebitNoteDesc
	 * @param strDebitNoteDesc The strDebitNoteDesc to set.
	 */
	public void setDebitNoteDesc(String strDebitNoteDesc) {
		this.strDebitNoteDesc = strDebitNoteDesc;
	}//end of setDebitNoteDesc(String strDebitNoteDesc)
	
	/** Retrieve the DebitNoteNbr
	 * @return Returns the strDebitNoteNbr.
	 */
	public String getDebitNoteNbr() {
		return strDebitNoteNbr;
	}//end of getDebitNoteNbr()
	
	/** Sets the DebitNoteNbr
	 * @param strDebitNoteNbr The strDebitNoteNbr to set.
	 */
	public void setDebitNoteNbr(String strDebitNoteNbr) {
		this.strDebitNoteNbr = strDebitNoteNbr;
	}//end of setDebitNoteNbr(String strDebitNoteNbr)
	
	/** Retrieve the DebitNoteTypeID
	 * @return Returns the strDebitNoteTypeID.
	 */
	public String getDebitNoteTypeID() {
		return strDebitNoteTypeID;
	}//end of getDebitNoteTypeID()
	
	/** Sets the DebitNoteTypeID
	 * @param strDebitNoteTypeID The strDebitNoteTypeID to set.
	 */
	public void setDebitNoteTypeID(String strDebitNoteTypeID) {
		this.strDebitNoteTypeID = strDebitNoteTypeID;
	}//end of setDebitNoteTypeID(String strDebitNoteTypeID)
	
	/** Retrieve the Remarks
	 * @return Returns the strRemarks.
	 */
	public String getRemarks() {
		return strRemarks;
	}//end of getRemarks()
	
	/** Sets the Remarks
	 * @param strRemarks The strRemarks to set.
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}//end of setRemarks(String strRemarks)

	/** Retrieve the FloatSeqID
	 * @return Returns the lngFloatSeqID.
	 */
	public Long getFloatSeqID() {
		return lngFloatSeqID;
	}//end of getFloatSeqID()

	/** Sets the FloatSeqID
	 * @param lngFloatSeqID The lngFloatSeqID to set.
	 */
	public void setFloatSeqID(Long lngFloatSeqID) {
		this.lngFloatSeqID = lngFloatSeqID;
	}//end of setFloatSeqID(Long lngFloatSeqID)
	
}//end of DebitNoteVO
