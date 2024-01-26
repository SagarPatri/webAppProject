/**
 * @ (#) DebitNoteDepositVO.java Sep 12, 2007
 * Project 	     : TTK HealthCare Services
 * File          : DebitNoteDepositVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 12, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.finance;

import java.math.BigDecimal;

public class DebitNoteDepositVO extends DebitNoteVO{
	
	private Long lngDebitNoteTransSeqID = null;
	private BigDecimal bdTotalDepositAmt = null;
	private BigDecimal bdDepositedAmt = null;
	private BigDecimal bdPendingAmt = null;
	private BigDecimal bdCurrentAmt = null;
	private String strTransTypeID = "";
	private Long lngFloatTransSeqID = null;
	private String strTranCompleteYN = "";
	
	/** Retrieve the CurrentAmt
	 * @return Returns the bdCurrentAmt.
	 */
	public BigDecimal getCurrentAmt() {
		return bdCurrentAmt;
	}//end of getCurrentAmt()

	/** Sets the CurrentAmt
	 * @param bdCurrentAmt The bdCurrentAmt to set.
	 */
	public void setCurrentAmt(BigDecimal bdCurrentAmt) {
		this.bdCurrentAmt = bdCurrentAmt;
	}//end of setCurrentAmt(BigDecimal bdCurrentAmt)

	/** Retrieve the TotalDepositAmt
	 * @return Returns the bdTotalDepositAmt.
	 */
	public BigDecimal getTotalDepositAmt() {
		return bdTotalDepositAmt;
	}//end of getTotalDepositAmt()

	/** Sets the TotalDepositAmt
	 * @param bdTotalDepositAmt The bdTotalDepositAmt to set.
	 */
	public void setTotalDepositAmt(BigDecimal bdTotalDepositAmt) {
		this.bdTotalDepositAmt = bdTotalDepositAmt;
	}//end of setTotalDepositAmt(BigDecimal bdTotalDepositAmt)

	/** Retrieve the TransTypeID
	 * @return Returns the strTransTypeID.
	 */
	public String getTransTypeID() {
		return strTransTypeID;
	}//end of getTransTypeID()

	/** Sets the TransTypeID
	 * @param strTransTypeID The strTransTypeID to set.
	 */
	public void setTransTypeID(String strTransTypeID) {
		this.strTransTypeID = strTransTypeID;
	}//end of setTransTypeID(String strTransTypeID)

	/** Retrieve the DepositedAmt
	 * @return Returns the bdDepositedAmt.
	 */
	public BigDecimal getDepositedAmt() {
		return bdDepositedAmt;
	}//end of getDepositedAmt()

	/** Sets the DepositedAmt
	 * @param bdDepositedAmt The bdDepositedAmt to set.
	 */
	public void setDepositedAmt(BigDecimal bdDepositedAmt) {
		this.bdDepositedAmt = bdDepositedAmt;
	}//end of setDepositedAmt(BigDecimal bdDepositedAmt)

	/** Retrieve the PendingAmt
	 * @return Returns the bdPendingAmt.
	 */
	public BigDecimal getPendingAmt() {
		return bdPendingAmt;
	}//end of getPendingAmt()

	/** Sets the PendingAmt
	 * @param bdPendingAmt The bdPendingAmt to set.
	 */
	public void setPendingAmt(BigDecimal bdPendingAmt) {
		this.bdPendingAmt = bdPendingAmt;
	}//end of setPendingAmt(BigDecimal bdPendingAmt)

	/** Retrieve the DebitNoteTransSeqID
	 * @return Returns the lngDebitNoteTransSeqID.
	 */
	public Long getDebitNoteTransSeqID() {
		return lngDebitNoteTransSeqID;
	}//end of getDebitNoteTransSeqID()

	/** Sets the DebitNoteTransSeqID
	 * @param lngDebitNoteTransSeqID The lngDebitNoteTransSeqID to set.
	 */
	public void setDebitNoteTransSeqID(Long lngDebitNoteTransSeqID) {
		this.lngDebitNoteTransSeqID = lngDebitNoteTransSeqID;
	}//end of setDebitNoteTransSeqID(Long lngDebitNoteTransSeqID)

	/** Retrieve the FloatTransSeqID
	 * @return Returns the lngFloatTransSeqID.
	 */
	public Long getFloatTransSeqID() {
		return lngFloatTransSeqID;
	}//end of getFloatTransSeqID()

	/** Sets the FloatTransSeqID
	 * @param lngFloatTransSeqID The lngFloatTransSeqID to set.
	 */
	public void setFloatTransSeqID(Long lngFloatTransSeqID) {
		this.lngFloatTransSeqID = lngFloatTransSeqID;
	}//end of setFloatTransSeqID(Long lngFloatTransSeqID)
	
	/** Retrieve the TranCompleteYN
	 * @return Returns the strTranCompleteYN.
	 */
	public String getTranCompleteYN() {
		return strTranCompleteYN;
	}//end of getTranCompleteYN()

	/** Sets the TranCompleteYN
	 * @param strTranCompleteYN The strTranCompleteYN to set.
	 */
	public void setTranCompleteYN(String strTranCompleteYN) {
		this.strTranCompleteYN = strTranCompleteYN;
	}//end of setTranCompleteYN(String strTranCompleteYN)

}//end of DebitNoteDepositVO
