/**
 *   @ (#) InviceVO.java Nov 06, 2006
 *   Project      : TTK HealthCare Services
 *   File         : InviceVO.java
 *   Author       : Krishna K H
 *   Company      : Span Systems Corporation
 *   Date Created : Nov 06, 2006
 *
 *   @author       : Krishna K H
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dto.finance;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO contains bank information.
 */

public class InvoiceVO extends BaseVO{

    private Long lngSeqID = null; // Invoice Seq ID
    private Long lngPolicySeqID = null; // Policy Seq ID
    private String strPolicyNbr= "";
    private String strProductName= "";
    private BigDecimal bdTotalPremium = null;
    private BigDecimal bdCommision = null;
    private BigDecimal bdCommissionAmt = null;
    
    private String strInvoiceNbr = "";
	private Date dtFromDate = null;
	private Date dtToDate = null;
	private String strStatusTypeID = "";
	private String strStatusDesc = "";
	private String strIncludeOldYN = "";
	private String strPolicyCount = "";
	private Long lngBatchSeqID = null;
	
	/** Retrieve the BatchSeqID
	 * @return Returns the lngBatchSeqID.
	 */
	public Long getBatchSeqID() {
		return lngBatchSeqID;
	}//end of getBatchSeqID()

	/** Sets the BatchSeqID
	 * @param lngBatchSeqID The lngBatchSeqID to set.
	 */
	public void setBatchSeqID(Long lngBatchSeqID) {
		this.lngBatchSeqID = lngBatchSeqID;
	}//end of setBatchSeqID(Long lngBatchSeqID)

	/** Retrieve the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}//end of getPolicySeqID()

	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}//end of setPolicySeqID(Long lngPolicySeqID)

	/** Retrieve the StatusDesc
	 * @return Returns the strStatusDesc.
	 */
	public String getStatusDesc() {
		return strStatusDesc;
	}//end of getStatusDesc()

	/** Sets the StatusDesc
	 * @param strStatusDesc The strStatusDesc to set.
	 */
	public void setStatusDesc(String strStatusDesc) {
		this.strStatusDesc = strStatusDesc;
	}//end of setStatusDesc(String strStatusDesc)

	/** Retrive the From Date
	 * @return the dtFromDate
	 */
	public Date getFromDate() {
		return dtFromDate;
	}//end of getFromDate()

	/** Retrive the From Date
	 * @return the FromDate
	 */
	public String getInvFromDate() {
		return TTKCommon.getFormattedDate(dtFromDate);
	}//end of getInvFromDate()
	
	/** Sets the From Date
	 * @param dtFromDate the dtFromDate to set
	 */
	public void setFromDate(Date dtFromDate) {
		this.dtFromDate = dtFromDate;
	}//end of setFromDate(Date dtFromDate)

	/** Retrive the To Date
	 * @return the dtToDate
	 */
	public Date getToDate() {
		return dtToDate;
	}//end of getToDate()
	
	/** Retrive the To Date
	 * @return the ToDate
	 */
	public String getInvToDate() {
		return TTKCommon.getFormattedDate(dtToDate);
	}//end of getInvToDate()

	/** Sets the To Date
	 * @param dtToDate the dtToDate to set
	 */
	public void setToDate(Date dtToDate) {
		this.dtToDate = dtToDate;
	}//end of setToDate(Date dtToDate)

	/** Retrive the Include Old YN
	 * @return the strIncludeOldYN
	 */
	public String getIncludeOldYN() {
		return strIncludeOldYN;
	}//end of getIncludeOldYN()

	/** Sets the Include Old YN 
	 * @param strIncludeOldYN the strIncludeOldYN to set
	 */
	public void setIncludeOldYN(String strIncludeOldYN) {
		this.strIncludeOldYN = strIncludeOldYN;
	}//end of setIncludeOldYN(String strIncludeOldYN)

	/** Retrive the Invoice Number
	 * @return the strInvoiceNbr
	 */
	public String getInvoiceNbr() {
		return strInvoiceNbr;
	}//end of getInvoiceNbr()

	/** Sets the Invoice Nubmer
	 * @param strInvoiceNbr the strInvoiceNbr to set
	 */
	public void setInvoiceNbr(String strInvoiceNbr) {
		this.strInvoiceNbr = strInvoiceNbr;
	}//end of setInvoiceNbr(String strInvoiceNbr)

	/** Retrieve the Status Type ID
	 * @return the strStatusTypeID
	 */
	public String getStatusTypeID() {
		return strStatusTypeID;
	}//end of getStatusTypeID()

	/** Sets the Status Type ID
	 * @param strStatusTypeID the strStatusTypeID to set
	 */
	public void setStatusTypeID(String strStatusTypeID) {
		this.strStatusTypeID = strStatusTypeID;
	}//end of setStatusTypeID(String strStatusTypeID)

	/** Retrieve the Invoice Seq ID
     * @return Returns the lngSeqID.
     */
    public Long getSeqID() {
        return lngSeqID;
    }//end of getSeqID()

    /** Sets the Invoice Seq ID
     * @param lngSeqID The lngSeqID to set.
     */
    public void setSeqID(Long lngSeqID) {
        this.lngSeqID = lngSeqID;
    }//end of setSeqID(Long lngInvoiceSeqID)

   /** Retrieve the Product Name
	 * @return Returns the strProductName.
	 */
	public String getProductName() {
		return strProductName;
	}//end of getProductName()

	/** Sets the Product Name
	 * @param strProductName The strProductName to set.
	 */
	public void setProductName(String strProductName) {
		this.strProductName = strProductName;
	}//end of setProductName(String strProductName)

	/**Sets the Policy Number.
     * @return Returns the strPolicyNbr.
     */
    public String getPolicyNbr() {
        return strPolicyNbr;
    }//end of getPolicyNbr()

    /**Retrieve the Policy Number.
     * @param strPolicyNbr The strPolicyNbr to set.
     */
    public void setPolicyNbr(String strPolicyNbr) {
        this.strPolicyNbr = strPolicyNbr;
    }//end of setPolicyNbr(String strPolicyNbr)

    /** This method returns the Total Premium
     * @return Returns the bdTotalPremium.
     */
    public BigDecimal getTotalPremium() {
        return bdTotalPremium;
    }//end of getTotalPremium()

    /** This method sets the Total Premium
     * @param bdTotalPremium The bdTotalPremium to set.
     */
    public void setTotalPremium(BigDecimal bdTotalPremium) {
        this.bdTotalPremium = bdTotalPremium;
    }//end of setTotalPremium(BigDecimal bdTotalPremium)

    /** This method returns the Commision
     * @return Returns the bdCommision.
     */
    public BigDecimal getCommision() {
        return bdCommision;
    }//end of getCommision()

    /** This method sets the Commision
     * @param bdCommision The bdCommision to set.
     */
    public void setCommision(BigDecimal bdCommision) {
        this.bdCommision = bdCommision;
    }//end of setCommision(BigDecimal bdCommision)

    /** This method returns the Commision Amount
     * @return Returns the bdCommissionAmt.
     */
    public BigDecimal getCommissionAmt() {
        return bdCommissionAmt;
    }//end of getCommissionAmt()

    /** This method sets the Commission Amount
     * @param bdCommissionAmt The bdCommissionAmt to set.
     */
    public void setCommissionAmt(BigDecimal bdCommissionAmt) {
        this.bdCommissionAmt = bdCommissionAmt;
    }//end of setCommissionAmt(BigDecimal bdCommissionAmt)

	/** Retrieve the PolicyCount
	 * @return Returns the strPolicyCount.
	 */
	public String getPolicyCount() {
		return strPolicyCount;
	}//end of getPolicyCount()

	/** Sets the PolicyCount
	 * @param strPolicyCount The strPolicyCount to set.
	 */
	public void setPolicyCount(String strPolicyCount) {
		this.strPolicyCount = strPolicyCount;
	}//end of setPolicyCount(String strPolicyCount)

}//end of InviceVO