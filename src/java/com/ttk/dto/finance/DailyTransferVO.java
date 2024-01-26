/**
 * @ (#) DailyTransferVO.java August 8, 2009
 * Project 	     : TTK HealthCare Services
 * File          : DailyTransferVO.java
 * Author        : Navin Kumar R
 * Company       : Span Systems Corporation
 * Date Created  : August 8, 2009
 *
 * @author       :  
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.finance;


import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

public class DailyTransferVO extends BaseVO{

	/** long serialVersionUID. */
	private static final long serialVersionUID = -8443809206890455805L;
	
	//Grid Display	
	private Long lngMasterSeqID = null;
	private String strClaimSettleNo = null;
	private String strFloatAccountNo = null;
	private String strChequeNo = null;
	private Date dtChequeIssDate = null;
	private String strInsCompanyName = null;
	private Date dtDailyTransferDate = null;
	private boolean bDailyTransferModify;
	
	/** Retrieve the MasterSeqID
	 * @return Returns the lngMasterSeqID.
	 */
	public Long getMasterSeqID() {
		return lngMasterSeqID;
	}//end of getMasterSeqID()

	/** Sets the MasterSeqID
	 * @param lngMasterSeqID The lngMasterSeqID to set.
	 */
	public void setMasterSeqID(Long lngMasterSeqID) {
		this.lngMasterSeqID = lngMasterSeqID;
	}//end of setMasterSeqID(Long lngMasterSeqID)
	
	/** This method returns the Claim Settlement Number
	 * @return Returns the strClaimSettleNo.
	 */
	public String getClaimSettleNo() {
		return strClaimSettleNo;
	}//End of getClaimSettleNo()
	
	/**This Method sets the Claim Settlement Number
	 * @param strClaimSettleNo The strClaimSettleNo to set.
	 */
	public void setClaimSettleNo(String strClaimSettleNo) {
		this.strClaimSettleNo = strClaimSettleNo;
	}//End of setClaimSettleNo(String strClaimSettleNo)
	
	/** This method returns the Float Account Number
	 * @return Returns the strFloatAccountNo.
	 */
	public String getFloatAccountNo() {
		return strFloatAccountNo;
	}//End of getFloatAccountNo()
	
	/**This Method sets the Float Account Number
	 * @param strFloatAccountNo The strFloatAccountNo to set.
	 */
	public void setFloatAccountNo(String strFloatAccountNo) {
		this.strFloatAccountNo = strFloatAccountNo;
	}//End of setFloatAccountNo(String strFloatAccountNo)
	
	/** This method returns the Cheque Number.
	 * @return Returns the strChequeNo.
	 */
	public String getChequeNo() {
		return strChequeNo;
	}//End of getChequeNo()
	
	/**This Method sets the Cheque Number
	 * @param strChequeNo The strChequeNo to set.
	 */
	public void setChequeNo(String strChequeNo) {
		this.strChequeNo = strChequeNo;
	}//End of setChequeNo(String strChequeNo)
		
	/** This method returns the Cheque Issue Date
     * @return Returns the dtChequeIssDate.
     */
	public Date getChequeIssDate() {
		return dtChequeIssDate;
	}// End of getChequeIssDate()
		
	/** This method sets the Cheque Issue Date
	 * @param dtChequeIssDate The dtChequeIssDate to set.
	 */
	public void setChequeIssDate(Date dtChequeIssDate) {
		this.dtChequeIssDate = dtChequeIssDate;
	}// End of setChequeIssDate(Date dtChequeIssDate)
	
	/** This method returns the Cheque Issue Date
     * @return Returns the dtChequeIssDate.
     */
	public String getChequeDate() {
		return TTKCommon.getFormattedDate(dtChequeIssDate);
	}// End of getChequeDate()
	
	/** This method returns the Insurance Company name
	 * @return Returns the strInsCompanyName.
	 */
	public String getInsCompanyName() {
		return strInsCompanyName;
	}//End of getInsCompanyName()
	
	/**This Method sets the Insurance Company name
	 * @param strInsCompanyName The strInsCompanyName to set.
	 */
	public void setInsCompanyName(String strInsCompanyName) {
		this.strInsCompanyName = strInsCompanyName;
	}//End of setInsCompanyName(String strInsCompanyName)

	/** This method returns the Daily Transfer Date
     * @return Returns the dtDailyTransferDate.
     */
	public Date getDailyTransferDate() {
		return dtDailyTransferDate;
	}// End of getDailyTransferDate()
	
	/** This method returns the Daily Transfer Date
     * @return Returns the dtDailyTransferDate.
     */
	public String getDailyTransDate() {
		return TTKCommon.getFormattedDate(dtDailyTransferDate);
	}// End of getDailyTransDate()
		
	/** This method sets the Daily Transfer Date
	 * @param dtDailyTransferDate The dtDailyTransferDate to set.
	 */
	public void setDailyTransferDate(Date dtDailyTransferDate) {
		this.dtDailyTransferDate = dtDailyTransferDate;
	}// End of setDailyTransferDate(Date dtDailyTransferDate)
	
	/** This method returns the Daily Transfer Modify.
	 * @return Returns the bDailyTransferModify.
	 */
	public boolean getDailyTransferModify() {
		return bDailyTransferModify;
	}//End of getDailyTransferModify()
	
	/**This Method sets the Daily Transfer Modify.
	 * @param bDailyTransferModify The bDailyTransferModify to set.
	 */
	public void setDailyTransferModify(boolean bDailyTransferModify) {
		this.bDailyTransferModify = bDailyTransferModify;
	}//End of setDailyTransferModify(String bDailyTransferModify)
}//end of DailyTransferVO
