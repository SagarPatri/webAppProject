
//Created as per KOC 1142(Copay Restriction) to display authorization (star)
package com.ttk.dto.preauth;


import java.math.BigDecimal;

public class BalanceCopayDeductionVO {
	
	
	private BigDecimal bdApprovedAmt =null;
	private BigDecimal bdMaxCopayAmt = null;
	/**
	 * @param bdMaxCopayAmt the bdMaxCopayAmt to set
	 */
	public void setBdMaxCopayAmt(BigDecimal bdMaxCopayAmt) {
		this.bdMaxCopayAmt = bdMaxCopayAmt;
	}
	/**
	 * @return the bdMaxCopayAmt
	 */
	public BigDecimal getBdMaxCopayAmt() {
		return bdMaxCopayAmt;
	}
	/**
	 * @param bdApprovedAmt the bdApprovedAmt to set
	 */
	public void setBdApprovedAmt(BigDecimal bdApprovedAmt) {
		this.bdApprovedAmt = bdApprovedAmt;
	}
	/**
	 * @return the bdApprovedAmt
	 */
	public BigDecimal getBdApprovedAmt() {
		return bdApprovedAmt;
	}
	

}
