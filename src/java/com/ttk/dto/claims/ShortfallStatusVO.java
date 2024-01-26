/**
 * @ (#) ShortfallStatusVO.java Jan 02, 2013
 * Project 	     : TTK HealthCare Services
 * File          : ShortfallStatusVO.java
 * Author        : Manohar 
 * Company       : RCS
 * Date Created  : Jan 02, 2013
 *
 * @author       : Manohar 
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.claims;

import com.ttk.dto.BaseVO;

public class ShortfallStatusVO extends BaseVO{

	private String strClaimNumber = "";
	private String strShortfallNumber="";
    private String strShortfallStatus="";
    private String strEmailIDStatus="";
    private String strShortfallEmailSeqId="";
    
    private String strMobileNo="";
    private String strBranch="";
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return this.strMobileNo;
	}
	/**
	 * @return the branch
	 */
	public String getBranch() {
		return this.strBranch;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.strMobileNo = mobileNo;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.strBranch = branch;
	}
	/** Retrieve the Claim Number
	 * @return the claimNumber
	 */
	public String getClaimNumber() {
		return this.strClaimNumber;
	}
	/** Sets the Claim Number
	 * @param claimNumber the claimNumber to set
	 */
	public void setClaimNumber(String claimNumber) {
		this.strClaimNumber = claimNumber;
	}
	/** Retrieve the Shortfall Number
	 * @return the shortfallNumber
	 */
	public String getShortfallNumber() {
		return this.strShortfallNumber;
	}
	/** Sets the Shortfall Number
	 * @param shortfallNumber the shortfallNumber to set
	 */
	public void setShortfallNumber(String shortfallNumber) {
		this.strShortfallNumber = shortfallNumber;
	}
	/** Retrieve the  Shortfall Status
	 * @return the shortfallStatus
	 */
	public String getShortfallStatus() {
		return this.strShortfallStatus;
	}
	/** Sets the Shortfall Status
	 * @param shortfallStatus the shortfallStatus to set
	 */
	public void setShortfallStatus(String shortfallStatus) {
		this.strShortfallStatus = shortfallStatus;
	}
	/** Retrieve the  EmailID Status
	 * @return the emailIDStatus
	 */
	public String getEmailIDStatus() {
		return this.strEmailIDStatus;
	}
	/** Sets the EmailID Status
	 * @param emailIDStatus the emailIDStatus to set
	 */
	public void setEmailIDStatus(String emailIDStatus) {
		this.strEmailIDStatus = emailIDStatus;
	}
	/** Retrieve the Shortfall Email SeqId
	 * @return the shortfallEmailSeqId
	 */
	public String getShortfallEmailSeqId() {
		return this.strShortfallEmailSeqId;
	}
	/** Sets the Shortfall Email SeqId
	 * @param shortfallEmailSeqId the shortfallEmailSeqId to set
	 */
	public void setShortfallEmailSeqId(String shortfallEmailSeqId) {
		this.strShortfallEmailSeqId = shortfallEmailSeqId;
	}       
}//end of ShortfallStatusVO
