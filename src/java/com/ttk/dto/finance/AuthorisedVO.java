/**
 * @ (#) AuthorisedVO.java Jun 7, 2006
 * Project       : TTK HealthCare Services
 * File          : AuthorisedVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 7, 2006
 *
 * @author       : Srikanth H M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.finance;

import com.ttk.dto.usermanagement.UserListVO;
import java.util.Date;

/**
 * This VO contains authorissed informations.
 */

public class AuthorisedVO extends UserListVO{
    
    private Long lngAuthSeqID;
    private Date dtFromDate;
    private Date dtToDate;
    private Long lngBankAcctSeqID = null;
    private Double dTransLimit		=	null; 
    
    public Double getTransLimit() {
		return dTransLimit;
	}

	public void setTransLimit(Double dTransLimit) {
		this.dTransLimit = dTransLimit;
	}

	/** Retrieve the Bank Account Seq ID
	 * @return Returns the lngBankAcctSeqID.
	 */
	public Long getBankAcctSeqID() {
		return lngBankAcctSeqID;
	}//end of getBankAcctSeqID()

	/** Sets the Bank Account Seq ID
	 * @param lngBankAcctSeqID The lngBankAcctSeqID to set.
	 */
	public void setBankAcctSeqID(Long lngBankAcctSeqID) {
		this.lngBankAcctSeqID = lngBankAcctSeqID;
	}//end of setBankAcctSeqID(Long lngBankAcctSeqID)

	/**Retrieve the From Date.
     * @return Returns the dtFromDate.
     */
    public Date getFromDate() {
        return dtFromDate;
    }//end of getFromDate()
    
    /**Sets the From Date.
     * @param dtFromDate The dtFromDate to set.
     */
    public void setFromDate(Date dtFromDate) {
        this.dtFromDate = dtFromDate;
    }//end of setFromDate(Date dtFromDate)
    
    /**Retrieve the To Date.
     * @return Returns the dtToDate.
     */
    public Date getToDate() {
        return dtToDate;
    }//end of  getToDate()
    
    /**Sets the To Date.
     * @param dtToDate The dtToDate to set.
     */
    public void setToDate(Date dtToDate) {
        this.dtToDate = dtToDate;
    }//end of setToDate(Date dtToDate
    
    /**Retrieve the Auth SeqId
     * @return Returns the lngAuthSeqID.
     */
    public Long getAuthSeqID() {
        return lngAuthSeqID;
    }//end of getAuthSeqID() 
    
    /**Sets the Auth SeqId
     * @param lngAuthSeqID The lngAuthSeqID to set.
     */
    public void setAuthSeqID(Long lngAuthSeqID) {
    	this.lngAuthSeqID = lngAuthSeqID;
    }//end of  getAuthSeqID(Long lngAuthSeqID)
    
}//end of AuthorissedVO