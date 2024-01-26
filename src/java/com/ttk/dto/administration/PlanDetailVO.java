/**
 * @ (#) PlanDetailVO.java Jun 26, 2008
 * Project 	     : TTK HealthCare Services
 * File          : PlanDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 26, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;

/**
 * @author ramakrishna_km
 *
 */
public class PlanDetailVO extends PlanVO{
	
	private String strSchemeID = "";
	private Long lngGroupRegSeqID = null;
	private BigDecimal bdCashBenefitAmt = null;
	private Integer intBenefitAllowDays = null;
	private String strBenefitTypeID = "";
	
	/** Retrieve the CashBenefitAmt
	 * @return Returns the bdCashBenefitAmt.
	 */
	public BigDecimal getCashBenefitAmt() {
		return bdCashBenefitAmt;
	}//end of getCashBenefitAmt
	
	/** Sets the CashBenefitAmt
	 * @param bdCashBenefitAmt The bdCashBenefitAmt to set.
	 */
	public void setCashBenefitAmt(BigDecimal bdCashBenefitAmt) {
		this.bdCashBenefitAmt = bdCashBenefitAmt;
	}//end of setCashBenefitAmt(BigDecimal bdCashBenefitAmt)
	
	/** Retrieve the BenefitAllowDays
	 * @return Returns the intBenefitAllowDays.
	 */
	public Integer getBenefitAllowDays() {
		return intBenefitAllowDays;
	}//end of getBenefitAllowDays()
	
	/** Sets the BenefitAllowDays
	 * @param intBenefitAllowDays The intBenefitAllowDays to set.
	 */
	public void setBenefitAllowDays(Integer intBenefitAllowDays) {
		this.intBenefitAllowDays = intBenefitAllowDays;
	}//end of setBenefitAllowDays(Integer intBenefitAllowDays)
	
	/** Retrieve the GroupRegSeqID
	 * @return Returns the lngGroupRegSeqID.
	 */
	public Long getGroupRegSeqID() {
		return lngGroupRegSeqID;
	}//end of getGroupRegSeqID()
	
	/** Sets the GroupRegSeqID
	 * @param lngGroupRegSeqID The lngGroupRegSeqID to set.
	 */
	public void setGroupRegSeqID(Long lngGroupRegSeqID) {
		this.lngGroupRegSeqID = lngGroupRegSeqID;
	}//end of setGroupRegSeqID(Long lngGroupRegSeqID)
	
	/** Retrieve the BenefitTypeID
	 * @return Returns the strBenefitTypeID.
	 */
	public String getBenefitTypeID() {
		return strBenefitTypeID;
	}//end of getBenefitTypeID()
	
	/** Sets the BenefitTypeID
	 * @param strBenefitTypeID The strBenefitTypeID to set.
	 */
	public void setBenefitTypeID(String strBenefitTypeID) {
		this.strBenefitTypeID = strBenefitTypeID;
	}//end of setBenefitTypeID(String strBenefitTypeID)
	
	/** Retrieve the SchemeID
	 * @return Returns the strSchemeID.
	 */
	public String getSchemeID() {
		return strSchemeID;
	}//end of getSchemeID()
	
	/** Sets the SchemeID
	 * @param strSchemeID The strSchemeID to set.
	 */
	public void setSchemeID(String strSchemeID) {
		this.strSchemeID = strSchemeID;
	}//end of setSchemeID(String strSchemeID)
	
}//end of PlanDetailVO
