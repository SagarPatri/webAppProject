/**
 * @ (#) ClaimBillVO.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimBillVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 15, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.claims;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.preauth.PreAuthVO;

public class ClaimBillVO extends PreAuthVO{

	private Long lngBillSeqID = null;
	private String strBillNbr = "";
	private Date dtBillDate = null;
	private String strBillTypeDesc = "";
	private BigDecimal bdBillAmount = null;
	private BigDecimal bdApprovedBillAmt = null;
	private String strImageName = "ListItems";
	private String strImageTitle = "Line Items";
	private String strBillIncludedYN = "";

	/** Retrieve the BillIncludedYN
	 * @return Returns the strBillIncludedYN.
	 */
	public String getBillIncludedYN() {
		return strBillIncludedYN;
	}//end of getBillIncludedYN()

	/** Sets the BillIncludedYN
	 * @param strBillIncludedYN The strBillIncludedYN to set.
	 */
	public void setBillIncludedYN(String strBillIncludedYN) {
		this.strBillIncludedYN = strBillIncludedYN;
	}//end of setBillIncludedYN(String strBillIncludedYN)

	/** Retrieve the ApprovedBillAmt
	 * @return Returns the bdApprovedBillAmt.
	 */
	public BigDecimal getApprovedBillAmt() {
		return bdApprovedBillAmt;
	}//end of getApprovedBillAmt()

	/** Sets the ApprovedBillAmt
	 * @param bdApprovedBillAmt The bdApprovedBillAmt to set.
	 */
	public void setApprovedBillAmt(BigDecimal bdApprovedBillAmt) {
		this.bdApprovedBillAmt = bdApprovedBillAmt;
	}//end of setApprovedBillAmt(BigDecimal bdApprovedBillAmt)

	/** Retrieve the ImageName
	 * @return Returns the strImageName.
	 */
	public String getImageName() {
		return strImageName;
	}//end of getImageName()

	/** Sets the ImageName
	 * @param strImageName The strImageName to set.
	 */
	public void setImageName(String strImageName) {
		this.strImageName = strImageName;
	}//end of setImageName(String strImageName)

	/** Retrieve the ImageTitle
	 * @return Returns the strImageTitle.
	 */
	public String getImageTitle() {
		return strImageTitle;
	}//end of getImageTitle()

	/** Sets the ImageTitle
	 * @param strImageTitle The strImageTitle to set.
	 */
	public void setImageTitle(String strImageTitle) {
		this.strImageTitle = strImageTitle;
	}//end of setImageTitle(String strImageTitle)

	/** Retrieve the Bill Amount
	 * @return Returns the bdBillAmount.
	 */
	public BigDecimal getBillAmount() {
		return bdBillAmount;
	}//end of getBillAmount()

	/** Sets the Bill Amount
	 * @param bdBillAmount The bdBillAmount to set.
	 */
	public void setBillAmount(BigDecimal bdBillAmount) {
		this.bdBillAmount = bdBillAmount;
	}//end of setBillAmount(BigDecimal bdBillAmount)

	/** Retrieve the Bill Date
	 * @return Returns the dtBillDate.
	 */
	public Date getBillDate() {
		return dtBillDate;
	}//end of getBillDate()

	/** Retrieve the Bill Date
	 * @return Returns the dtBillDate.
	 */
	public String getClaimBillDate() {
		return TTKCommon.getFormattedDate(dtBillDate);
	}//end of getClaimBillDate()

	/** Sets the Bill Date
	 * @param dtBillDate The dtBillDate to set.
	 */
	public void setBillDate(Date dtBillDate) {
		this.dtBillDate = dtBillDate;
	}//end of setBillDate(Date dtBillDate)

	/** Retrieve the BillSeqID
	 * @return Returns the lngBillSeqID.
	 */
	public Long getBillSeqID() {
		return lngBillSeqID;
	}//end of getBillSeqID()

	/** Sets the BillSeqID
	 * @param lngBillSeqID The lngBillSeqID to set.
	 */
	public void setBillSeqID(Long lngBillSeqID) {
		this.lngBillSeqID = lngBillSeqID;
	}//end of setBillSeqID(Long lngBillSeqID)

	/** Retrieve the BillNbr
	 * @return Returns the strBillNbr.
	 */
	public String getBillNbr() {
		return strBillNbr;
	}//end of getBillNbr()

	/** Sets the BillNbr
	 * @param strBillNbr The strBillNbr to set.
	 */
	public void setBillNbr(String strBillNbr) {
		this.strBillNbr = strBillNbr;
	}//end of setBillNbr(String strBillNbr)

	/** Retrieve the Bill Type Description
	 * @return Returns the strBillTypeDesc.
	 */
	public String getBillTypeDesc() {
		return strBillTypeDesc;
	}//end of getBillTypeDesc()

	/** Sets the Bill Type Description
	 * @param strBillTypeDesc The strBillTypeDesc to set.
	 */
	public void setBillTypeDesc(String strBillTypeDesc) {
		this.strBillTypeDesc = strBillTypeDesc;
	}//end of setBillTypeDesc(String strBillTypeDesc)

}//end of ClaimBillVO
