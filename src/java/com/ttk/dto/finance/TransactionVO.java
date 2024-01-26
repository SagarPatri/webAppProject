/**
 * @ (#) TransactionVO.java June 07, 2006
 * Project       : TTK HealthCare Services
 * File          : TransactionVO.java
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
 * This VO contains all details of of the transaction information.
 */

public class TransactionVO extends BaseVO {

	private Long lngTransSeqID; //Transaction Seq ID
	private BigDecimal bdTransAmt;//Transaction Amount
	private String strAccountNo;//Account Number
	private String strAccountName;//Account Name
	
	public String getAccountName() {
		return strAccountName;
	}

	public void setAccountName(String strAccountName) {
		this.strAccountName = strAccountName;
	}

	private String strTransNbr;//Transaction Number
	private String strTransTypeID;//Transaction Type ID
	private String strTransTypeDesc;//Transaction Type Description
	private Date dtTransDate;//Transaction Date
	private String strRemarks;//Remarks
	private BigDecimal bdFloatBalance;//Float Balance
	private Long lngAccountSeqID;  //bankaccount SeqID.
	private String strImageName = "Blank";
    private String strImageTitle = "";
    private String strFloatTypeID = "";
    private String strCurrency		=	"";
    
    public String getCurrency() {
		return strCurrency;
	}

	public void setCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	/** This method returns the Image Name
     * @return Returns the strImageName.
     */
    public String getImageName() {
        return strImageName;
    }//end of getImageName()
    
    /** This method sets the Image Name 
     * @param strImageName The strImageName to set.
     */
    public void setImageName(String strImageName) {
        this.strImageName = strImageName;
    }//end of setImageName(String strImageName)
    
    /** This method returns the Image Title
     * @return Returns the strImageTitle.
     */
    public String getImageTitle() {
        return strImageTitle;
    }//end of getImageTitle()
    
    /** This method sets the Image Title
     * @param strImageTitle The strImageTitle to set.
     */
    public void setImageTitle(String strImageTitle) {
        this.strImageTitle = strImageTitle;
    }//end of setImageTitle(String strImageTitle)

	/** This method returns the Bank From Date
	 * @return Returns the strFromDate.
	 */
	public String getBankTransDate() {
		return TTKCommon.getFormattedDate(dtTransDate);
	}// End of getRecdDate()

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

    /**Retrieve Transaction Amount.
     * @return Returns the bdTransAmt.
     */
    public BigDecimal getTransAmt() {
        return bdTransAmt;
    }//end of getTransAmt()

    /**Sets the Transaction Amount.
     * @param bdTransAmt The bdTransAmt to set.
     */
    public void setTransAmt(BigDecimal bdTransAmt) {
        this.bdTransAmt = bdTransAmt;
    }//end of setTransAmt(BigDecimal bdTransAmt)

    /**Retrieve Transaction Date.
     * @return Returns the dtTransDate.
     */
    public Date getTransDate() {
        return dtTransDate;
    }//end of getTransDate()

    /**Retrieve Transaction Date.
     * @return Returns the dtTransDate.
     */
    public String getTransactionDate() {
        return TTKCommon.getFormattedDate(dtTransDate);
    }//end of getTransactionDate()

    /**Sets the Transaction Date.
     * @param dtTransDate The dtTransDate to set.
     */
    public void setTransDate(Date dtTransDate) {
        this.dtTransDate = dtTransDate;
    }//end of setTransDate(Date dtTransDate)

    /**Retrieve Transaction SeqID.
     * @return Returns the lngTransSeqID.
     */
    public Long getTransSeqID() {
        return lngTransSeqID;
    }//end of getTransSeqID()

    /**Sets the Transaction SeqID.
     * @param lngTransSeqID The lngTransSeqID to set.
     */
    public void setTransSeqID(Long lngTransSeqID) {
        this.lngTransSeqID = lngTransSeqID;
    }//end of setTransSeqID(Long lngTransSeqID)

    /**Retrieve AccountNo.
     * @return Returns the strAccountNo.
     */
    public String getAccountNo() {
        return strAccountNo;
    }//end of getAccountNo()

    /**Sets the AccountNo.
     * @param strAccountNo The strAccountNo to set.
     */
    public void setAccountNo(String strAccountNo) {
        this.strAccountNo = strAccountNo;
    }//end of setAccountNo(String strAccountNo)

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

    /**Retrieve Transaction Number.
     * @return Returns the strTransNbr.
     */
    public String getTransNbr() {
        return strTransNbr;
    }//end of getTransNbr()

    /**Sets the Transaction Number.
     * @param strTransNbr The strTransNbr to set.
     */
    public void setTransNbr(String strTransNbr) {
        this.strTransNbr = strTransNbr;
    }//end of setTransNbr(String strTransNbr)

    /**Retrieve Transaction Type Description.
     * @return Returns the strTransTypeDesc.
     */
    public String getTransTypeDesc() {
        return strTransTypeDesc;
    }//end of getTransTypeDesc()

    /**Sets the Transaction Type Description.
     * @param strTransTypeDesc The strTransTypeDesc to set.
     */
    public void setTransTypeDesc(String strTransTypeDesc) {
        this.strTransTypeDesc = strTransTypeDesc;
    }//end of setTransTypeDesc(String strTransTypeDesc)

    /**Retrieve Transaction Type ID.
     * @return Returns the strTransTypeID.
     */
    public String getTransTypeID() {
        return strTransTypeID;
    }//end of getTransTypeID()

    /**Sets the Transaction Type ID.
     * @param strTransTypeID The strTransTypeID to set.
     */
    public void setTransTypeID(String strTransTypeID) {
        this.strTransTypeID = strTransTypeID;
    }//end of setTransTypeID(String strTransTypeID)

    /**Retrieve float balance.
     * @return Returns the bdFloatBalance.
     */
    public BigDecimal getFloatBalance() {
        return bdFloatBalance;
    }//end of getFloatBalance()

    /**Sets the float balance.
     * @param bdFloatBalance The bdFloatBalance to set.
     */
    public void setFloatBalance(BigDecimal bdFloatBalance) {
        this.bdFloatBalance = bdFloatBalance;
    }//end of setFloatBalance(BigDecimal bdFloatBalance)

	/** Retrieve the FloatTypeID
	 * @return Returns the strFloatTypeID.
	 */
	public String getFloatTypeID() {
		return strFloatTypeID;
	}//end of getFloatTypeID()

	/** Sets the FloatTypeID
	 * @param strFloatTypeID The strFloatTypeID to set.
	 */
	public void setFloatTypeID(String strFloatTypeID) {
		this.strFloatTypeID = strFloatTypeID;
	}//end of setFloatTypeID(String strFloatTypeID)

}//end of TransactionVO
