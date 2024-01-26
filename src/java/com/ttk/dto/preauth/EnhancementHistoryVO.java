/**
 * @ (#) EnhancementHistoryVO.java May 16, 2006
 * Project 	     : TTK HealthCare Services
 * File          : EnhancementHistoryVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : May 16, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.preauth;

public class EnhancementHistoryVO extends PreAuthVO {
	
	private String strApprovedAmount="";
    private String strStatusDesc = "";
    private String strRequestedAmount = "";
	
    /** Retrieve the Approved Amount
	 * @return Returns the strApprovedAmount.
	 */
	public String getApprovedAmount() {
		return strApprovedAmount;
	}//end of getApprovedAmount()
	
	/** Sets the Approved Amount
	 * @param strApprovedAmount The strApprovedAmount to set.
	 */
	public void setApprovedAmount(String strApprovedAmount) {
		this.strApprovedAmount = strApprovedAmount;
	}//end of setApprovedAmount(String strApprovedAmount)
	
	/** Retrieve the Requested Amount
	 * @return Returns the strRequestedAmount.
	 */
	public String getRequestedAmount() {
		return strRequestedAmount;
	}//end of getRequestedAmount()
	
	/** Sets the Requested Amount
	 * @param strRequestedAmount The strRequestedAmount to set.
	 */
	public void setRequestedAmount(String strRequestedAmount) {
		this.strRequestedAmount = strRequestedAmount;
	}//end of setRequestedAmount(String strRequestedAmount)
	
	/** Retrieve the Status Description
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()
	
	/** Sets the Status Description
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)
    
}//end of EnhancementHistoryVO
