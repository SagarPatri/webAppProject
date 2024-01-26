/**
 * @ (#) ClaimantHistoryVO.java Apr 11, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimantHistoryVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 11, 2006
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

public class ClaimantHistoryVO extends PreAuthVO {

    private Long lngSeqID = null;
    private String strHistoryTypeID = "";
    private String strAuthorizationNo = "";
    private BigDecimal bdApprovedAmount = null;
    private Date dtStartDate = null;
    private Date dtEndDate = null;
    private String strClaimNo ="";
    private BigDecimal bdRequestedAmount = null;
    private String strAilement = "";
    private Date dtClmDischargeDate = null;

    /** Retrieve the Ailement Description
	 * @return Returns the strAilement.
	 */
	public String getAilement() {
		return strAilement;
	}//end of getAilement()

	/** Sets the Ailement Description
	 * @param strAilement The strAilement to set.
	 */
	public void setAilement(String strAilement) {
		this.strAilement = strAilement;
	}//end of setAilement(String strAilement)

    /** This method returns the Claim No.
     * @return Returns the strClaimNo.
     */
    public String getClaimNo() {
        return strClaimNo;
    }//end of getClaimNo()

    /** This method sets the Claim No.
     * @param strClaimNo The strClaimNo to set.
     */
    public void setClaimNo(String strClaimNo) {
        this.strClaimNo = strClaimNo;
    }//end of setClaimNo(String strClaimNo)

    /** This method returns the Rrequested Amount
     * @return Returns the bdARrequestedAmount.
     */
    public BigDecimal getRequestedAmount() {
        return bdRequestedAmount;
    }//end of getRrequestedAmount()

    /** This method sets the Rrequested Amount
     * @param bdRrequestedAmount The bdRrequestedAmount to set.
     */
    public void setRequestedAmount(BigDecimal bdRrequestedAmount) {
        this.bdRequestedAmount = bdRrequestedAmount;
    }//end of setRrequestedAmount(BigDecimal bdRrequestedAmount)

    /** This method returns the Approved Amount
     * @return Returns the bdApprovedAmount.
     */
    public BigDecimal getApprovedAmount() {
        return bdApprovedAmount;
    }//end of getApprovedAmount()

    /** This method sets the Approved Amount
     * @param bdApprovedAmount The bdApprovedAmount to set.
     */
    public void setApprovedAmount(BigDecimal bdApprovedAmount) {
        this.bdApprovedAmount = bdApprovedAmount;
    }//end of setApprovedAmount(BigDecimal bdApprovedAmount)

    /** This method returns the End Date
     * @return Returns the dtEndDate.
     */
    public String getEndDate() {
        return TTKCommon.getFormattedDate(dtEndDate);
    }//end of getEndDate()

    /** This method sets the End Date
     * @param dtEndDate The dtEndDate to set.
     */
    public void setEndDate(Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }//end of setEndDate(Date dtEndDate)

    /** This method returns the Start Date
     * @return Returns the dtStartDate.
     */
    public String getStartDate() {
        return TTKCommon.getFormattedDate(dtStartDate);
    }//end of getStartDate()

    /** This method sets the Start Date
     * @param dtStartDate The dtStartDate to set.
     */
    public void setStartDate(Date dtStartDate) {
        this.dtStartDate = dtStartDate;
    }//end of setStartDate(Date dtStartDate)

    /** This method returns the Seq ID
     * @return Returns the lngSeqID.
     */
    public Long getSeqID() {
        return lngSeqID;
    }//end of getSeqID()

    /** This method sets the Seq ID
     * @param lngSeqID The lngSeqID to set.
     */
    public void setSeqID(Long lngSeqID) {
        this.lngSeqID = lngSeqID;
    }//end of setSeqID(Long lngSeqID)

    /** This method returns the Authorization No.
     * @return Returns the strAuthorizationNo.
     */
    public String getAuthorizationNo() {
        return strAuthorizationNo;
    }//end of getAuthorizationNo()

    /** This method sets the Authorization No.
     * @param strAuthorizationNo The strAuthorizationNo to set.
     */
    public void setAuthorizationNo(String strAuthorizationNo) {
        this.strAuthorizationNo = strAuthorizationNo;
    }//end of setAuthorizationNo(String strAuthorizationNo)

    /** This method returns the History Type ID
     * @return Returns the strHistoryTypeID.
     */
    public String getHistoryTypeID() {
        return strHistoryTypeID;
    }//end of getHistoryTypeID()

    /** This method sets the History Type ID
     * @param strHistoryTypeID The strHistoryTypeID to set.
     */
    public void setHistoryTypeID(String strHistoryTypeID) {
        this.strHistoryTypeID = strHistoryTypeID;
    }//end of setHistoryTypeID(String strHistoryTypeID)

	/** Retrieve the ClmDischargeDate
	 * @return Returns the dtClmDischargeDate.
	 */
	public Date getClmDischargeDate() {
		return dtClmDischargeDate;
	}//end of getClmDischargeDate()
	
	/** Retrieve the ClmDischargeDate
	 * @return Returns the dtClmDischargeDate.
	 */
	public String getClmDischargeDateTime() {
		return TTKCommon.getFormattedDateHour(dtClmDischargeDate);
	}//end of getClmDischargeDateTime()

	/** Sets the ClmDischargeDate
	 * @param dtClmDischargeDate The dtClmDischargeDate to set.
	 */
	public void setClmDischargeDate(Date dtClmDischargeDate) {
		this.dtClmDischargeDate = dtClmDischargeDate;
	}//end of setClmDischargeDate(Date dtClmDischargeDate)
}//end of MemberHistoryVO
