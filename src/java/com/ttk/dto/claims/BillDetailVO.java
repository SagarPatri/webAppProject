/**
 * @ (#) BillDetailVO.java Jul 28, 2006
 * Project 	     : TTK HealthCare Services
 * File          : BillDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 28, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.claims;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class BillDetailVO extends BaseVO{

    private Long lngWardAccGroupSeqID = null;
    private String strWardTypeID = "";
    private String strAccGroupDesc = "";
    private String strWardDesc = "";
    private BigDecimal bdBillAmount = null;
    private String strClaimBillAmt = "";
    private String strDiscountPercent = "";
    private String strApplyDiscountYN = "";
    private BigDecimal bdNetAmount = null;
    private String strClaimNetAmount = "";
    private BigDecimal bdMaxAmount = null;
    private String strClaimMaxAmount = "";
    private String strBillNotes = "";

    /** Retrieve the Notes
     * @return Returns the strNotes.
     */
    public String getBillNotes() {
        return strBillNotes;
    }//end of getNotes()

    /** Sets the Notes
     * @param strNotes The strNotes to set.
     */
    public void setBillNotes(String strBillNotes) {
        this.strBillNotes = strBillNotes;
    }//end of setNotes(String strNotes)

    /** Retrieve the DiscountPercent
     * @return Returns the strDiscountPercent.
     */
    public String getDiscountPercent() {
        return strDiscountPercent;
    }//end of getDiscountPercent()

    /** Sets the DiscountPercent
     * @param strDiscountPercent The strDiscountPercent to set.
     */
    public void setDiscountPercent(String strDiscountPercent) {
        this.strDiscountPercent = strDiscountPercent;
    }//end of setDiscountPercent(String strDiscountPercent)

    /** Retrieve the WardTypeID.
     * @return Returns the strWardTypeID.
     */
    public String getWardTypeID() {
        return strWardTypeID;
    }//end of getWardTypeID()

    /** Sets the WardTypeID.
     * @param strWardTypeID The strWardTypeID to set.
     */
    public void setWardTypeID(String strWardTypeID) {
        this.strWardTypeID = strWardTypeID;
    }//end of setWardTypeID(String strWardTypeID)

    /** Retrieve the Agreed Amount
     * @return Returns the bdAgreedAmount.
     */
    public BigDecimal getMaxAmount() {
        return bdMaxAmount;
    }//end of getMaxAmount()

    /** Sets the Agreed Amount
     * @param bdAgreedAmount The bdAgreedAmount to set.
     */
    public void setMaxAmount(BigDecimal bdAgreedAmount) {
        this.bdMaxAmount = bdAgreedAmount;
    }//end of setMaxAmount(BigDecimal bdAgreedAmount)

    /** Retrieve the Bill Amount
     * @return Returns the bdBillAmount.
     */
    public BigDecimal getBillAmount() {
        return bdBillAmount;
    }//end of getBillAmount()

    /** Sets the Bill Amount
     * @param bdBillAmt The bdBillAmt to set.
     */
    public void setBillAmount(BigDecimal bdBillAmount) {
        this.bdBillAmount = bdBillAmount;
    }//end of setBillAmount(BigDecimal bdBillAmount)

    /** Retrieve the NetAmount
     * @return Returns the bdNetAmount.
     */
    public BigDecimal getNetAmount() {
        return bdNetAmount;
    }//end of getNetAmount()

    /** Sets the NetAmount
     * @param bdNetAmount The bdNetAmount to set.
     */
    public void setNetAmount(BigDecimal bdNetAmount) {
        this.bdNetAmount = bdNetAmount;
    }//end of setNetAmount(BigDecimal bdNetAmount)

    /** Retrieve the WardAccGroupSeqID
     * @return Returns the lngWardAccGroupSeqID.
     */
    public Long getWardAccGroupSeqID() {
        return lngWardAccGroupSeqID;
    }//end of getWardAccGroupSeqID()

    /** Sets the WardAccGroupSeqID
     * @param lngWardAccGroupSeqID The lngWardAccGroupSeqID to set.
     */
    public void setWardAccGroupSeqID(Long lngWardAccGroupSeqID) {
        this.lngWardAccGroupSeqID = lngWardAccGroupSeqID;
    }//end of setWardAccGroupSeqID(Long lngWardAccGroupSeqID)

    /** Retrieve the Account Group Description
     * @return Returns the strAccGroupDesc.
     */
    public String getAccGroupDesc() {
        return strAccGroupDesc;
    }//end of getAccGroupDesc()

    /** Sets the Account Group Description
     * @param strAccGroupDesc The strAccGroupDesc to set.
     */
    public void setAccGroupDesc(String strAccGroupDesc) {
        this.strAccGroupDesc = strAccGroupDesc;
    }//end of setAccGroupDesc(String strAccGroupDesc)

    /** Retrieve the ApplyDiscountYN
     * @return Returns the strApplyDiscountYN.
     */
    public String getApplyDiscountYN() {
        return strApplyDiscountYN;
    }//end of getApplyDiscountYN()

    /** Sets the ApplyDiscountYN
     * @param strApplyDiscountYN The strApplyDiscountYN to set.
     */
    public void setApplyDiscountYN(String strApplyDiscountYN) {
        this.strApplyDiscountYN = strApplyDiscountYN;
    }//end of setApplyDiscountYN(String strApplyDiscountYN)

    /** Retrieve the ClaimAgreedAmount
     * @return Returns the strClaimAgreedAmount.
     */
    public String getClaimMaxAmount() {
        return strClaimMaxAmount;
    }//end of getClaimMaxAmount()

    /** Sets the ClaimAgreedAmount
     * @param strClaimAgreedAmount The strClaimAgreedAmount to set.
     */
    public void setClaimMaxAmount(String strClaimAgreedAmount) {
        this.strClaimMaxAmount = strClaimAgreedAmount;
    }//end of setClaimMaxAmount(String strClaimAgreedAmount)

    /** Retrieve the Claim Bill Amount
     * @return Returns the strClaimBillAmt.
     */
    public String getClaimBillAmt() {
        return strClaimBillAmt;
    }//end of getClaimBillAmt()

    /** Sets the Claim Bill Amount
     * @param strClaimBillAmt The strClaimBillAmt to set.
     */
    public void setClaimBillAmt(String strClaimBillAmt) {
        this.strClaimBillAmt = strClaimBillAmt;
    }//end of setClaimBillAmt(String strClaimBillAmt)

    /** Retrieve the ClaimNetAmount
     * @return Returns the strClaimNetAmount.
     */
    public String getClaimNetAmount() {
        return strClaimNetAmount;
    }//end of getClaimNetAmount()

    /** Sets the ClaimNetAmount
     * @param strClaimNetAmount The strClaimNetAmount to set.
     */
    public void setClaimNetAmount(String strClaimNetAmount) {
        this.strClaimNetAmount = strClaimNetAmount;
    }//end of setClaimNetAmount(String strClaimNetAmount)

    /** Retrieve the Ward Description
     * @return Returns the strWardDesc.
     */
    public String getWardDesc() {
        return strWardDesc;
    }//end of getWardDesc()

    /** Sets the Ward Description
     * @param strWardDesc The strWardDesc to set.
     */
    public void setWardDesc(String strWardDesc) {
        this.strWardDesc = strWardDesc;
    }//end of setWardDesc(String strWardDesc)

}//end of BillDetailVO
