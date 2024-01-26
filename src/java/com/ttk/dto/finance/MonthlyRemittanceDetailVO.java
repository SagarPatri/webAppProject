/**
 * @ (#) MonthlyRemittanceDetailVO.java August 11, 2009
 * Project 	     : TTK HealthCare Services
 * File          : MonthlyRemittanceDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : August 11, 2009
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.finance;

import java.util.Date;

/** This is a VO class used for Monthly Remittance.
 * @author ramakrishna_km
 *
 */
public class MonthlyRemittanceDetailVO extends MonthlyRemittanceVO{
	
	/**
	 *  long serialVersionUID.
	 */
	private static final long serialVersionUID = 2670641963903487473L;
	    	
	private String strInsID = null;
	private Date dtRemittanceDate = null;	
	private Date dtChallanDate = null;
	private String strChallanTransVouNbr = null;
	private String strBsrCode = null;
	private String strBankTransactionNo = null;	
	private boolean bMonthlyRemitModify;
	
	/** Retrieve the InsID.
	 * @return Returns the strInsID.
	 */
	public String getInsID() {
		return strInsID;
	}//end of getInsID()
	
	/** Sets the InsID.
	 * @param strInsID The strInsID to set.
	 */
	public void setInsID(String strInsID) {
		this.strInsID = strInsID;
	}//end of setInsID(String strInsID)
	
	/** Retrieve the RemittanceDate.
	 * @return Returns the dtRemittanceDate.
	 */
	public Date getRemittanceDate() {
		return dtRemittanceDate;
	}//end of getRemittanceDate()
	
	/** Sets the RemittanceDate.
	 * @param dtRemittanceDate The dtRemittanceDate to set.
	 */
	public void setRemittanceDate(Date dtRemittanceDate) {
		this.dtRemittanceDate = dtRemittanceDate;
	}//end of setRemittanceDate(Date dtRemittanceDate)
	
	/** Retrieve the ChallanDate.
	 * @return Returns the dtChallanDate.
	 */
	public Date getChallanDate() {
		return dtChallanDate;
	}//end of getChallanDate()
	
	/** Sets the ChallanDate.
	 * @param dtChallanDate The dtChallanDate to set.
	 */
	public void setChallanDate(Date dtChallanDate) {
		this.dtChallanDate = dtChallanDate;
	}//end of setChallanDate(Date dtChallanDate)
	
	/** Retrieve the ChallanTransVouNbr.
	 * @return Returns the strChallanTransVouNbr.
	 */
	public String getChallanTransVouNbr() {
		return strChallanTransVouNbr;
	}//end of getChallanTransVouNbr()
	
	/** Sets the ChallanTransVouNbr.
	 * @param strChallanTransVouNbr The strChallanTransVouNbr to set.
	 */
	public void setChallanTransVouNbr(String strChallanTransVouNbr) {
		this.strChallanTransVouNbr = strChallanTransVouNbr;
	}//end of setChallanTransVouNbr(String strChallanTransVouNbr)
	
	/** Retrieve the BsrCode.
	 * @return Returns the strBsrCode.
	 */
	public String getBsrCode() {
		return strBsrCode;
	}//end of getBsrCode()
	
	/** Sets the BsrCode.
	 * @param strBsrCode The strBsrCode to set.
	 */
	public void setBsrCode(String strBsrCode) {
		this.strBsrCode = strBsrCode;
	}//end of setBsrCode(String strBsrCode)
	
	/** Retrieve the BankTransactionNo.
	 * @return Returns the strBankTransactionNo.
	 */
	public String getBankTransactionNo() {
		return strBankTransactionNo;
	}//end of getBankTransactionNo()
	
	/** Sets the BankTransactionNo.
	 * @param strBankTransactionNo The strBankTransactionNo to set.
	 */
	public void setBankTransactionNo(String strBankTransactionNo) {
		this.strBankTransactionNo = strBankTransactionNo;
	}//end of setBankTransactionNo(String strBankTransactionNo)
	
	/** Retrieve the MonthlyRemitModify.
	 * @return Returns the bMonthlyRemitModify.
	 */
	public boolean getMonthlyRemitModify() {
		return bMonthlyRemitModify;
	}//end of getMonthlyRemitModify()
	
	/** Sets the MonthlyRemitModify.
	 * @param bMonthlyRemitModify The bMonthlyRemitModify to set.
	 */
	public void setMonthlyRemitModify(boolean bMonthlyRemitModify) {
		this.bMonthlyRemitModify = bMonthlyRemitModify;
	}//end of setMonthlyRemitModify(String bMonthlyRemitModify)
}//end of MonthlyRemittanceDetailVO
