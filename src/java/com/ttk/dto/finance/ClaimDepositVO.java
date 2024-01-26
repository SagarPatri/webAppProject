/**
 * @ (#) ClaimDepositVO.java Sep 12, 2007
 * Project 	     : TTK HealthCare Services
 * File          : ClaimDepositVO.java
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

import com.ttk.dto.BaseVO;

public class ClaimDepositVO extends BaseVO{
	
	private Long lngClaimSeqID = null;
	private String strClaimSettleNbr = "";
	private BigDecimal bdApprovedAmt = null;
	private BigDecimal bdTotalAmtPaid = null;
	private BigDecimal bdTransAmtPaid = null;
	private BigDecimal bdPendingAmt = null;
	private String strTransTypeID = "";
	private BigDecimal bdCurrentAmt = null;
	private String strTranCompleteYN = "";
	
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
	
	/** Retrieve the ApprovedAmt
	 * @return Returns the bdApprovedAmt.
	 */
	public BigDecimal getApprovedAmt() {
		return bdApprovedAmt;
	}//end of getApprovedAmt()
	
	/** Sets the ApprovedAmt
	 * @param bdApprovedAmt The bdApprovedAmt to set.
	 */
	public void setApprovedAmt(BigDecimal bdApprovedAmt) {
		this.bdApprovedAmt = bdApprovedAmt;
	}//end of setApprovedAmt(BigDecimal bdApprovedAmt)
	
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
	
	/** Retrieve the TotalAmtPaid
	 * @return Returns the bdTotalAmtPaid.
	 */
	public BigDecimal getTotalAmtPaid() {
		return bdTotalAmtPaid;
	}//end of getTotalAmtPaid()
	
	/** Sets the TotalAmtPaid
	 * @param bdTotalAmtPaid The bdTotalAmtPaid to set.
	 */
	public void setTotalAmtPaid(BigDecimal bdTotalAmtPaid) {
		this.bdTotalAmtPaid = bdTotalAmtPaid;
	}//end of setTotalAmtPaid(BigDecimal bdTotalAmtPaid)
	
	/** Retrieve the TransAmtPaid
	 * @return Returns the bdTransAmtPaid.
	 */
	public BigDecimal getTransAmtPaid() {
		return bdTransAmtPaid;
	}//end of getTransAmtPaid()
	
	/** Sets the TransAmtPaid
	 * @param bdTransAmtPaid The bdTransAmtPaid to set.
	 */
	public void setTransAmtPaid(BigDecimal bdTransAmtPaid) {
		this.bdTransAmtPaid = bdTransAmtPaid;
	}//end of setTransAmtPaid(BigDecimal bdTransAmtPaid)
	
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
	
	/** Retrieve the ClaimSettleNbr
	 * @return Returns the strClaimSettleNbr.
	 */
	public String getClaimSettleNbr() {
		return strClaimSettleNbr;
	}//end of getClaimSettleNbr()
	
	/** Sets the ClaimSettleNbr
	 * @param strClaimSettleNbr The strClaimSettleNbr to set.
	 */
	public void setClaimSettleNbr(String strClaimSettleNbr) {
		this.strClaimSettleNbr = strClaimSettleNbr;
	}//end of setClaimSettleNbr(String strClaimSettleNbr)
	
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
}//end of ClaimDepositVO
