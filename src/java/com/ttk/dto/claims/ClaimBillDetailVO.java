/**
 * @ (#) ClaimBillDetailVO.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimBillDetailVO.java
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
import java.util.ArrayList;

public class ClaimBillDetailVO extends ClaimBillVO{

	private String strBillIssuedBy = "";
	private BigDecimal bdTotalRequestedAmt = null;
	private BigDecimal bdTotalAllowedAmt = null;
	private BigDecimal bdTotalDisAllowedAmt = null;
	private String strBillWithPrescription = "";
	private LineItemVO lineItemVO = null;
	private ArrayList alLineItemList = null;
	private String strCompletedYN = "";
	 //added for CR KOC-Decoupling
	private String strBillsCompleteYN="";
	private String strDonorExpYN = "";//added for donor expenses
	//added for KOC-1273
	private String strAccountType="";
	//added for CR KOC-Decoupling
	private String strNextbillSeqId = "";
	private String strNextbillNo = "";
	private String strBillCompleteYN = "";
 //physiotherapy cr 1320
    private String strClaimSubTypeID = "";
    
    /**
	 * @return the strClaimSubTypeID
	 */
	public String getClaimSubTypeID() {
		return strClaimSubTypeID;
	}

	/**
	 * @param strClaimSubTypeID the strClaimSubTypeID to set
	 */
	public void setClaimSubTypeID(String strClaimSubTypeID) {
		this.strClaimSubTypeID = strClaimSubTypeID;
	}
	//physiotherapy cr 1320

	/** Retrieve the LineItemVO
	 * @return Returns the lineItemVO.
	 * 
	 * 
	 */
	
	public String getDonorExpYN() {
		return strDonorExpYN;
	}//end of getDonorExpYN()

	/** Sets the BillWithPrescription
	 * @param strBillWithPrescription The strBillWithPrescription to set.
	 */
	public void setDonorExpYN(String strDonorExpYN) {
		this.strDonorExpYN = strDonorExpYN;
	}//end of setDonorExpYN(String strBillWithPrescription)

	
	
	
	// end added for donor expenses
	public LineItemVO getLineItemVO() {
		return lineItemVO;
	}//end of getLineItemVO()

	

	/** Sets the LineItemVO
	 * @param lineItemVO The lineItemVO to set.
	 */
	public void setLineItemVO(LineItemVO lineItemVO) {
		this.lineItemVO = lineItemVO;
	}//end of setLineItemVO(LineItemVO lineItemVO)

	/** Retrieve the BillIssuedBy
	 * @return Returns the strBillIssuedBy.
	 */
	public String getBillIssuedBy() {
		return strBillIssuedBy;
	}//end of getBillIssuedBy()

	/** Sets the BillIssuedBy
	 * @param strBillIssuedBy The strBillIssuedBy to set.
	 */
	public void setBillIssuedBy(String strBillIssuedBy) {
		this.strBillIssuedBy = strBillIssuedBy;
	}//end of setBillIssuedBy(String strBillIssuedBy)

	/** Retrieve the BillWithPrescription
	 * @return Returns the strBillWithPrescription.
	 */
	public String getBillWithPrescription() {
		return strBillWithPrescription;
	}//end of getBillWithPrescription()

	/** Sets the BillWithPrescription
	 * @param strBillWithPrescription The strBillWithPrescription to set.
	 */
	public void setBillWithPrescription(String strBillWithPrescription) {
		this.strBillWithPrescription = strBillWithPrescription;
	}//end of setBillWithPrescription(String strBillWithPrescription)

	/** Retrieve the Total Allowed Amount
	 * @return Returns the bdTotalAllowedAmt.
	 */
	public BigDecimal getTotalAllowedAmt() {
		return bdTotalAllowedAmt;
	}//end of getTotalAllowedAmt()

	/** Sets the Total Allowed Amount
	 * @param bdTotalAllowedAmt The bdTotalAllowedAmt to set.
	 */
	public void setTotalAllowedAmt(BigDecimal bdTotalAllowedAmt) {
		this.bdTotalAllowedAmt = bdTotalAllowedAmt;
	}//end of setTotalAllowedAmt(BigDecimal bdTotalAllowedAmt)

	/** Retrieve the Total DisAllowed Amount
	 * @return Returns the bdTotalDisAllowedAmt.
	 */
	public BigDecimal getTotalDisAllowedAmt() {
		return bdTotalDisAllowedAmt;
	}//end of getTotalDisAllowedAmt()

	/** Sets the Total DisAllowed Amount
	 * @param bdTotalDisAllowedAmt The bdTotalDisAllowedAmt to set.
	 */
	public void setTotalDisAllowedAmt(BigDecimal bdTotalDisAllowedAmt) {
		this.bdTotalDisAllowedAmt = bdTotalDisAllowedAmt;
	}//end of setTotalDisAllowedAmt(BigDecimal bdTotalDisAllowedAmt)

	/** Retrieve the Total Requested Amount
	 * @return Returns the bdTotalRequestedAmt.
	 */
	public BigDecimal getTotalRequestedAmt() {
		return bdTotalRequestedAmt;
	}//end of getTotalRequestedAmt()

	/** Sets the Total Requested Amount
	 * @param bdTotalRequestedAmt The bdTotalRequestedAmt to set.
	 */
	public void setTotalRequestedAmt(BigDecimal bdTotalRequestedAmt) {
		this.bdTotalRequestedAmt = bdTotalRequestedAmt;
	}//end of setTotalRequestedAmt(BigDecimal bdTotalRequestedAmt)

	/** Retrieve the LineItemList
	 * @return Returns the alLineItemList.
	 */
	public ArrayList getLineItemList() {
		return alLineItemList;
	}//end of getLineItemList()

	/** Sets the LineItemList
	 * @param alLineItemList The alLineItemList to set.
	 */
	public void setLineItemList(ArrayList alLineItemList) {
		this.alLineItemList = alLineItemList;
	}//end of setLineItemList(ArrayList alLineItemList)
	/**
	 * @return Returns the strCompletedYN.
	 */
	public String getCompletedYN() {
		return strCompletedYN;
	}
	/**
	 * @param strCompletedYN The strCompletedYN to set.
	 */
	public void setCompletedYN(String strCompletedYN) {
		this.strCompletedYN = strCompletedYN;
	}
	public String getAccountType() {
		return strAccountType;
	}

	public void setAccountType(String strAccountType) {
		this.strAccountType = strAccountType;
	}
	//added for CR KOC-Decoupling
	public void setBillsCompleteYN(String strBillsCompleteYN) {
		this.strBillsCompleteYN = strBillsCompleteYN;
	}

	public String getBillsCompleteYN() {
		return strBillsCompleteYN;
	}

	public void setNextbillSeqId(String strNextbillSeqId) {
		this.strNextbillSeqId = strNextbillSeqId;
	}

	public String getNextbillSeqId() {
		return strNextbillSeqId;
	}

	public void setNextbillNo(String strNextbillNo) {
		this.strNextbillNo = strNextbillNo;
	}

	public String getNextbillNo() {
		return strNextbillNo;
	}

	public void setBillCompleteYN(String strBillCompleteYN) {
		this.strBillCompleteYN = strBillCompleteYN;
	}

	public String getBillCompleteYN() {
		return strBillCompleteYN;
	}
}//end of ClaimBillDetailVO
