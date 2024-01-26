/**
 * @ (#) SIBreakupInfoVO.java Sep 14, 2009
 * Project 	     : TTK HealthCare Services
 * File          : SIBreakupInfoVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 14, 2009
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
import com.ttk.dto.BaseVO;

/**This VO Class is used to display the SI Info in Preauth & Claims.
 * @author ramakrishna_km
 *
 */
public class SIBreakupInfoVO extends BaseVO{

	/**Serial Version ID.
	 * 
	 */
	private static final long serialVersionUID = 522337030347025027L;
	
	private Date dtSIEffDate=null;
	private BigDecimal bdMemSumInsured=null;
	private BigDecimal bdMemBonus = null;
	private String strRowNbr = "";
	
	/** Retrieve the RowNbr.
	 * @return Returns the strRowNbr.
	 */
	public String getRowNbr() {
		return strRowNbr;
	}//end of getRowNbr()

	/** Sets the RowNbr.
	 * @param strRowNbr The strRowNbr to set.
	 */
	public void setRowNbr(String strRowNbr) {
		this.strRowNbr = strRowNbr;
	}//end of setRowNbr(String strRowNbr)

	/** Retrieve the SIEffDate.
	 * @return Returns the dtSIEffDate.
	 */
	public Date getSIEffDate() {
		return dtSIEffDate;
	}//end of getSIEffDate()
	
	/** Retrieve the SIEffDate.
	 * @return Returns the dtSIEffDate.
	 */
	public String getPolSIEffDate() {
		return TTKCommon.getFormattedDate(dtSIEffDate);
	}//end of getPolSIEffDate()
	
	/** Sets the SIEffDate.
	 * @param dtSIEffDate The dtSIEffDate to set.
	 */
	public void setSIEffDate(Date dtSIEffDate) {
		this.dtSIEffDate = dtSIEffDate;
	}//end of setSIEffDate(Date dtSIEffDate)
	
	/** Retrieve the MemSumInsured.
	 * @return Returns the bdMemSumInsured.
	 */
	public BigDecimal getMemSumInsured() {
		return bdMemSumInsured;
	}//end of getMemSumInsured()
	
	/** Sets the MemSumInsured.
	 * @param bdMemSumInsured The bdMemSumInsured to set.
	 */
	public void setMemSumInsured(BigDecimal bdMemSumInsured) {
		this.bdMemSumInsured = bdMemSumInsured;
	}//end of setMemSumInsured(BigDecimal bdMemSumInsured)
	
	/** Retrieve the MemBonus.
	 * @return Returns the bdMemBonus.
	 */
	public BigDecimal getMemBonus() {
		return bdMemBonus;
	}//end of getMemBonus()
	
	/** Sets the MemBonus
	 * @param bdMemBonus The bdMemBonus to set.
	 */
	public void setMemBonus(BigDecimal bdMemBonus) {
		this.bdMemBonus = bdMemBonus;
	}//end of setMemBonus(BigDecimal bdMemBonus)
	
}//end of SIBreakupInfoVO
