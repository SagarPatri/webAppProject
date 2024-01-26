/**
 * @ (#) ClaimBenefitVO.java Jul 2, 2008
 * Project 	     : TTK HealthCare Services
 * File          : ClaimBenefitVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 2, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * @author ramakrishna_km
 *
 */
public class ClaimBenefitVO extends BaseVO{
	
	private Long lngClaimSeqID = null;
	private String strClaimNbr = "";
	private String strClaimSettlementNbr = "";
	private String strClaimantName = "";
	private Long lngParentClaimSeqID = null;
	private Long lngClmCashBenefitSeqID = null;
	private String strClaimFileNbr = "";
	private String strParentClaimNbr = "";
	private Date dtParentClmApprDate = null;
	private Date dtCashBenefitApprDate = null;
	private String strBenefitType = "";
	
	/** Retrieve the BenefitType
	 * @return Returns the strBenefitType.
	 */
	public String getBenefitType() {
		return strBenefitType;
	}//end of getBenefitType()

	/** Sets the BenefitType
	 * @param strBenefitType The strBenefitType to set.
	 */
	public void setBenefitType(String strBenefitType) {
		this.strBenefitType = strBenefitType;
	}//end of setBenefitType(String strBenefitType)

	/** Retrieve the Cash Benefit Approved Date
	 * @return Returns the dtCashBenefitApprDate.
	 */
	public Date getCashBenefitApprDate() {
		return dtCashBenefitApprDate;
	}//end of getCashBenefitApprDate()
	
	/** Retrieve the Cash Benefit Approved Date
	 * @return Returns the dtCashBenefitApprDate.
	 */
	public String getCBApprDate() {
		return TTKCommon.getFormattedDateHour(dtCashBenefitApprDate);
	}//end of getCBApprDate()
	
	/** Retrieve the Cash Benefit Approved Date in dd/mm/yyyy
	 * @return Returns the dtCashBenefitApprDate.
	 */
	public String getCBApprovedDate() {
		return TTKCommon.getFormattedDate(dtCashBenefitApprDate);
	}//end of getCBApprDate()
	
	/** Sets the Cash Benefit Approved Date
	 * @param dtCashBenefitApprDate The dtCashBenefitApprDate to set.
	 */
	public void setCashBenefitApprDate(Date dtCashBenefitApprDate) {
		this.dtCashBenefitApprDate = dtCashBenefitApprDate;
	}//end of setCashBenefitApprDate(Date dtCashBenefitApprDate)
	
	/** Retrieve the Parent Claim Approved Date
	 * @return Returns the dtParentClmApprDate.
	 */
	public Date getParentClmApprDate() {
		return dtParentClmApprDate;
	}//end of getParentClmApprDate()
	
	/** Sets the Parent Claim Approved Date
	 * @param dtParentClmApprDate The dtParentClmApprDate to set.
	 */
	public void setParentClmApprDate(Date dtParentClmApprDate) {
		this.dtParentClmApprDate = dtParentClmApprDate;
	}//end of setParentClmApprDate(Date dtParentClmApprDate)
	
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
	
	/** Retrieve the Claim Cash Benefit SeqID
	 * @return Returns the lngClmCashBenefitSeqID.
	 */
	public Long getClmCashBenefitSeqID() {
		return lngClmCashBenefitSeqID;
	}//end of getClmCashBenefitSeqID()
	
	/** Sets the Claim Cash Benefit SeqID
	 * @param lngClmCashBenefitSeqID The lngClmCashBenefitSeqID to set.
	 */
	public void setClmCashBenefitSeqID(Long lngClmCashBenefitSeqID) {
		this.lngClmCashBenefitSeqID = lngClmCashBenefitSeqID;
	}//end of setClmCashBenefitSeqID(Long lngClmCashBenefitSeqID)
	
	/** Retrieve the ParentClaimSeqID
	 * @return Returns the lngParentClaimSeqID.
	 */
	public Long getParentClaimSeqID() {
		return lngParentClaimSeqID;
	}//end of getParentClaimSeqID()
	
	/** Sets the ParentClaimSeqID
	 * @param lngParentClaimSeqID The lngParentClaimSeqID to set.
	 */
	public void setParentClaimSeqID(Long lngParentClaimSeqID) {
		this.lngParentClaimSeqID = lngParentClaimSeqID;
	}//end of setParentClaimSeqID(Long lngParentClaimSeqID)
	
	/** Retrieve the CashBenefitClaimNbr
	 * @return Returns the strCashBenefitClaimNbr.
	 */
	public String getParentClaimNbr() {
		return strParentClaimNbr;
	}//end of getParentClaimNbr()
	
	/** Sets the CashBenefitClaimNbr
	 * @param strCashBenefitClaimNbr The strCashBenefitClaimNbr to set.
	 */
	public void setParentClaimNbr(String strParentClaimNbr) {
		this.strParentClaimNbr = strParentClaimNbr;
	}//end of setParentClaimNbr(String strParentClaimNbr)
	
	/** Retrieve the ClaimantName
	 * @return Returns the strClaimantName.
	 */
	public String getClaimantName() {
		return strClaimantName;
	}//end of getClaimantName()
	
	/** Sets the ClaimantName
	 * @param strClaimantName The strClaimantName to set.
	 */
	public void setClaimantName(String strClaimantName) {
		this.strClaimantName = strClaimantName;
	}//end of setClaimantName(String strClaimantName)
	
	/** Retrieve the ClaimFileNbr
	 * @return Returns the strClaimFileNbr.
	 */
	public String getClaimFileNbr() {
		return strClaimFileNbr;
	}//end of getClaimFileNbr()
	
	/** Sets the ClaimFileNbr
	 * @param strClaimFileNbr The strClaimFileNbr to set.
	 */
	public void setClaimFileNbr(String strClaimFileNbr) {
		this.strClaimFileNbr = strClaimFileNbr;
	}//end of setClaimFileNbr(String strClaimFileNbr)
	
	/** Retrieve the ClaimNbr
	 * @return Returns the strClaimNbr.
	 */
	public String getClaimNbr() {
		return strClaimNbr;
	}//end of getClaimNbr()
	
	/** Sets the ClaimNbr
	 * @param strClaimNbr The strClaimNbr to set.
	 */
	public void setClaimNbr(String strClaimNbr) {
		this.strClaimNbr = strClaimNbr;
	}//end of setClaimNbr(String strClaimNbr)
	
	/** Retrieve the ClaimSettlementNbr
	 * @return Returns the strClaimSettlementNbr.
	 */
	public String getClaimSettlementNbr() {
		return strClaimSettlementNbr;
	}//end of getClaimSettlementNbr()
	
	/** Sets the ClaimSettlementNbr
	 * @param strClaimSettlementNbr The strClaimSettlementNbr to set.
	 */
	public void setClaimSettlementNbr(String strClaimSettlementNbr) {
		this.strClaimSettlementNbr = strClaimSettlementNbr;
	}//end of setClaimSettlementNbr(String strClaimSettlementNbr)

}//end of ClaimBenefitVO
