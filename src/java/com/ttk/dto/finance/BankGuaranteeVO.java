/**
 * @ (#) BankGuaranteeVO.java June 07, 2006
 * Project       : TTK HealthCare Services
 * File          : BankGuaranteeVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : June 07, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.finance;

import java.math.BigDecimal;
import java.util.Date;
import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO contains bank guarantee information.
 */

public class BankGuaranteeVO  extends BaseVO
{
	private Long lngAccountSeqID;  //bankaccount SeqId.
	private Long lngGuaranteeSeqID;  //Guarantee SeqId.
	private String strBankerName;//Banker Name
	private Date dtFromDate;//From Date
	private Date dtToDate;//To Date
	private BigDecimal bdBanGuarAmt;//Bank Guarantee Amt.

	private Date dtIssueDate;//issueDate
	private String strBankGuaranteeNo;//Bank Guarantee Number.
	private String strBgType;//BG Type.
	private String strBgStatus;//BG Status.
	private String strRemarks;
	private Date dtBgHandOverDate;
	private Date dtBgReturnDate;


	public Date getIssueDate() {
		return dtIssueDate;
	}

	public void setIssueDate(Date dtIssueDate) {
		this.dtIssueDate = dtIssueDate;
	}

	public String getBankGuaranteeNo() {
		return strBankGuaranteeNo;
	}

	public void setBankGuaranteeNo(String strBankGuaranteeNo) {
		this.strBankGuaranteeNo = strBankGuaranteeNo;
	}

	public String getBgType() {
		return strBgType;
	}

	public void setBgType(String strBgType) {
		this.strBgType = strBgType;
	}

	public String getBgStatus() {
		return strBgStatus;
	}

	public void setBgStatus(String strBgStatus) {
		this.strBgStatus = strBgStatus;
	}

	public String getRemarks() {
		return strRemarks;
	}

	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public Date getBgHandOverDate() {
		return dtBgHandOverDate;
	}

	public void setBgHandOverDate(Date dtBgHandOverDate) {
		this.dtBgHandOverDate = dtBgHandOverDate;
	}

	public Date getBgReturnDate() {
		return dtBgReturnDate;
	}

	public void setBgReturnDate(Date dtBgReturnDate) {
		this.dtBgReturnDate = dtBgReturnDate;
	}

	
	/** This method returns the Bank From Date
	 * @return Returns the strFromDate.
	 */
	public String getBankFromDate() {
		return TTKCommon.getFormattedDate(dtFromDate);
	}// End of getRecdDate()

	/** This method returns the Bank From Date
	 * @return Returns the strFromDate.
	 */
	public String getBankToDate() {
		return TTKCommon.getFormattedDate(dtToDate);
	}// End of getRecdDate()

	/**Retrieve the Bank Guarantee SeqID.
     * @return Returns the lngGuaranteeSeqID.
     */
    public Long getGuaranteeSeqID() {
        return lngGuaranteeSeqID;
    }//end of getAccountSeqID()

    /**Sets the Bank Guarantee SeqID.
     * @param lngGuaranteeSeqID The lngGuaranteeSeqID to set.
     */
    public void setGuaranteeSeqID(Long lngGuaranteeSeqID) {
        this.lngGuaranteeSeqID = lngGuaranteeSeqID;
    }//end of setAccountSeqID(Long lngAccountSeqID)

	/**Retrieve the Account SeqID.
     * @return Returns the lAccountSeqID.
     */
    public Long getAccountSeqID() {
        return lngAccountSeqID;
    }//end of getAccountSeqID()

    /**Sets the Account SeqID.
     * @param lngAccountSeqID The lngAccountSeqID to set.
     */
    public void setAccountSeqID(Long lngAccountSeqID) {
        this.lngAccountSeqID = lngAccountSeqID;
    }//end of setAccountSeqID(Long lngAccountSeqID)

	/** Retrieve the  BankGuaranteed Amont.
     * @param bdBanGuarAmt The bdBanGuarAmt to set.
     */
    public BigDecimal getBanGuarAmt() {
        return bdBanGuarAmt;
    }//end of getBanGuarAmt()

    /** Sets the BankGuaranteed Amont.
     * @return Returns the bdBanGuarAmt.
     */
    public void setBanGuarAmt(BigDecimal bdBanGuarAmt) {
        this.bdBanGuarAmt = bdBanGuarAmt;
    }//end of setBanGuarAmt(BigDecimal bdBanGuarAmt)

	/**Retrieve the To Date.
     * @return Returns the dtToDate.
     */
    public Date getToDate() {
        return dtToDate;
    }//end of getToDate()

    /**Sets the To Date.
     * @param dtToDate The dtToDate to set.
     */
    public void setToDate(Date dtToDate) {
        this.dtToDate = dtToDate;
    }//end of setToDate(Date dtToDate)

	 /**Retrieve the From Date.
     * @return Returns the dtFrmDate.
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

	/**Retrieve the Banker Name.
     * @return Returns the strBankerName.
     */
    public String getBankerName() {
        return strBankerName;
    }//end of getBankerName()

    /**Sets the Banker Name.
     * @param strBankerName The strBankerName to set.
     */
    public void setBankerName(String strBankerName) {
        this.strBankerName = strBankerName;
    }//end of setBankerName(String strBankerName)

}
