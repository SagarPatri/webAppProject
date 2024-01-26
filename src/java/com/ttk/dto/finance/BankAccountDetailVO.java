/**
 * @ (#) BankAccountDetailVO.java Jun 7, 2006
 * Project       : TTK HealthCare Services
 * File          : BankAccountDetailVO.java
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

//import java.math.BigDecimal;
import java.util.Date;

/**
 * This VO contains bank detail informations.
 */

public class BankAccountDetailVO extends BankAccountVO {
    
	private Date dtCreatedDate;  //Created Date
	private Date dtClosedDate;   //Closed Date	
	private String strRemarks;//Remarks
	private String strAcctType = "";//Account Type
	private String strStatusTypeID = "";//Status
	private Long lngOfficeSeqID = null;//TTK Branch
	private String strTdsPurposeYN; //TDS Purpose	

	private Date strAccOpenDate; //intX
	private Date strAccCloseDate; //intX

	/*public Date getAccCloseDate() {
		return strAccCloseDate;
	}

	public void setAccCloseDate(Date strAccCloseDate) {
		this.strAccCloseDate = strAccCloseDate;
	}*/
	public Date getAccOpenDate() {
		return strAccOpenDate;
	}

	public void setAccOpenDate(Date strAccOpenDate) {
		this.strAccOpenDate = strAccOpenDate;
	}

    /** Retrieve the Office Seq ID
	 * @return Returns the lngOfficeSeqID.
	 */
	public Long getOfficeSeqID() {
		return lngOfficeSeqID;
	}//end of getOfficeSeqID()

	/** Sets the Office Seq ID
	 * @param lngOfficeSeqID The lngOfficeSeqID to set.
	 */
	public void setOfficeSeqID(Long lngOfficeSeqID) {
		this.lngOfficeSeqID = lngOfficeSeqID;
	}//end of setOfficeSeqID(Long lngOfficeSeqID)

	/** Retrieve the Account Type 
	 * @return Returns the strAcctType.
	 */
	public String getAcctType() {
		return strAcctType;
	}//end of getAcctType()

	/** Sets the Account Type 
	 * @param strAcctType The strAcctType to set.
	 */
	public void setAcctType(String strAcctType) {
		this.strAcctType = strAcctType;
	}//end of setAcctType(String strAcctType)

	/** Retrieve the Status Type ID
	 * @return Returns the strStatusTypeID.
	 */
	public String getStatusTypeID() {
		return strStatusTypeID;
	}//end of getStatusTypeID()

	/** Sets the Status Type ID
	 * @param strStatusTypeID The strStatusTypeID to set.
	 */
	public void setStatusTypeID(String strStatusTypeID) {
		this.strStatusTypeID = strStatusTypeID;
	}//end of setStatusTypeID(String strStatusTypeID)
	
    /**Retrieve the Closed Date.
     * @return Returns the dtClosedDate.
     */
    public Date getClosedDate() {
        return dtClosedDate;
    }//end of getClosedDate()
    
    /**Sets the Closed Date.
     * @param dtClosedDate The dtClosedDate to set.
     */
    public void setClosedDate(Date dtClosedDate) {
        this.dtClosedDate = dtClosedDate;
    }//end of setClosedDate(Date dtClosedDate)
    
    /**Retrieve the Created Date.
     * @return Returns the dtCreatedDate.
     */
    public Date getCreatedDate() {
        return dtCreatedDate;
    }//end of getCreatedDate()
    
    /**Sets the Created Date.
     * @param dtCreatedDate The dtCreatedDate to set.
     */
    public void setCreatedDate(Date dtCreatedDate) {
        this.dtCreatedDate = dtCreatedDate;
    }//end of setCreatedDate(Date dtCreatedDate)
        
    /**Retrieve the Remarks.
     * @return Returns the strRemarks.
     */
    public String getRemarks() {
        return strRemarks;
    }//end of getRemarks()
    
    /**Sets the Remarks.
     * @param strRemarks The strRemarks to set.
     */
    public void setRemarks(String strRemarks) {
        this.strRemarks = strRemarks;
    }//end of setRemarks(String strRemarks)
    
    /** Retrieve the TDS Purpose
	 * @return Returns the strTdsPurposeYN.
	 */
	public String getTdsPurposeYN() {
		return strTdsPurposeYN;
	}//end of getTdsPurposeYN()

	/** Sets the TDS Purpose
	 * @param strTdsPurposeYN The strTdsPurposeYN to set.
	 */
	public void setTdsPurposeYN(String strTdsPurposeYN) {
		this.strTdsPurposeYN = strTdsPurposeYN;
	}//end of setTdsPurposeYN(String strTdsPurposeYN)
}//end of BankAccountDetailVO