/**
 * @ (#) EnhancementVO.java May 12, 2006
 * Project 	     : TTK HealthCare Services
 * File          : EnhancementVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : May 12, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

import java.util.Date;

import com.ttk.common.TTKCommon;

public class EnhancementVO extends PreAuthVO {
	
	private Date dtStartDate = null;
	private Date dtEndDate = null;
	private String strSumInsured = null;
	
	/** Retrieve the SumInsured
	 * @return Returns the strSumInsured.
	 */
	public String getSumInsured() {
		return strSumInsured;
	}//end of getSumInsured()
	
	/** Sets the SumInsured
	 * @param strSumInsured The strSumInsured to set.
	 */
	public void setSumInsured(String strSumInsured) {
		this.strSumInsured = strSumInsured;
	}//end of setSumInsured(String strSumInsured)
	
	/** Retrieve the End Date
	 * @return Returns the dtEndDate.
	 */
	public String getEndDate() {
		return TTKCommon.getFormattedDate(dtEndDate);
	}//end of getEndDate()
	
	/** Sets the End Date
	 * @param dtEndDate The dtEndDate to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}//end of setEndDate(Date dtEndDate)
	
	/** Retrieve the Start Date
	 * @return Returns the dtStartDate.
	 */
	public String getStartDate() {
		return TTKCommon.getFormattedDate(dtStartDate);
	}//end of getStartDate()
	
	/** Sets the Start Date
	 * @param dtStartDate The dtStartDate to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}//end of setStartDate(Date dtStartDate)

}//end of EnhancementVO
