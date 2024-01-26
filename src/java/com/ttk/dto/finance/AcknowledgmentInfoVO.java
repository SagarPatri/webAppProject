/**
 * @ (#) AcknowledgmentInfo.java August 12, 2009
 * Project 	     : TTK HealthCare Services
 * File          : AcknowledgmentInfo.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : August 12, 2009
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.finance;

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**This is a VO class used for Acknowledgment Information.
 * @author ramakrishna_km
 *
 */
public class AcknowledgmentInfoVO extends BaseVO {

	/**
	 * long serialVersionUID.
	 */
	
	private Long lngAckSeqID = null;
	private String strInsID = null;
	private String strInsCompanyName = null;
	private String strFinancialYear = null;
	private String strQuarterTypeID = null;
	private String strQuarter = null;
	private String strAckNbr = null;
	private Date dtAckDate = null;
	private String strChckBoxValueYN = "";
	
	
	
	/** Retrieve the check box value
	 * @return the strChckBoxValue
	 */
	public String getChckBoxValueYN() {
		return strChckBoxValueYN;
	}//end of getChckBoxValueYN()

	/** Sets the check box value
	 * @param strChckBoxValueYN the strChckBoxValueYN to set
	 */
	public void setChckBoxValueYN(String strChckBoxValueYN) {
		this.strChckBoxValueYN = strChckBoxValueYN;
	}//end of setChckBoxValueYN(String strChckBoxValueYN)

	/** Retrieve the AckSeqID.
	 * @return Returns the lngAckSeqID.
	 */
	public Long getAckSeqID() {
		return lngAckSeqID;
	}//end of getAckSeqID()
	
	/** Sets the AckSeqID.
	 * @param lngAckSeqID The lngAckSeqID to set.
	 */
	public void setAckSeqID(Long lngAckSeqID) {
		this.lngAckSeqID = lngAckSeqID;
	}//end of setAckSeqID(Long lngAckSeqID)
	
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
	
	/** Retrieve the InsCompanyName.
	 * @return Returns the strInsCompanyName.
	 */
	public String getInsCompanyName() {
		return strInsCompanyName;
	}//end of getInsCompanyName()
	
	/** Sets the InsCompanyName.
	 * @param strInsCompanyName The strInsCompanyName to set.
	 */
	public void setInsCompanyName(String strInsCompanyName) {
		this.strInsCompanyName = strInsCompanyName;
	}//end of setInsCompanyName(String strInsCompanyName)
	
	/** Retrieve the FinancialYear.
	 * @return Returns the strFinancialYear.
	 */
	public String getFinancialYear() {
		return strFinancialYear;
	}//end of getFinancialYear()
	
	/** Sets the FinancialYear.
	 * @param strFinancialYear The strFinancialYear to set.
	 */
	public void setFinancialYear(String strFinancialYear) {
		this.strFinancialYear = strFinancialYear;
	}//end of setFinancialYear(String strFinancialYear)
	
	/** Retrieve the QuarterTypeID.
	 * @return Returns the strQuarterTypeID.
	 */
	public String getQuarterTypeID() {
		return strQuarterTypeID;
	}//end of getQuarterTypeID()
	
	/** Sets the QuarterTypeID.
	 * @param strQuarterTypeID The strQuarterTypeID to set.
	 */
	public void setQuarterTypeID(String strQuarterTypeID) {
		this.strQuarterTypeID = strQuarterTypeID;
	}//end of setQuarterTypeID(String strQuarterTypeID)
	
	/** Retrieve the Quarter.
	 * @return Returns the strQuarter.
	 */
	public String getQuarter() {
		return strQuarter;
	}//end of getQuarter()
	
	/** Sets the Quarter.
	 * @param strQuarter The strQuarter to set.
	 */
	public void setQuarter(String strQuarter) {
		this.strQuarter = strQuarter;
	}//end of setQuarter(String strQuarter)
	
	/** Retrieve the AckNbr.
	 * @return Returns the strAckNbr.
	 */
	public String getAckNbr() {
		return strAckNbr;
	}//end of getAckNbr()
	
	/** Sets the AckNbr.
	 * @param strAckNbr The strAckNbr to set.
	 */
	public void setAckNbr(String strAckNbr) {
		this.strAckNbr = strAckNbr;
	}//end of setAckNbr(String strAckNbr)
	
	/** Retrieve the AckDate.
	 * @return Returns the dtAckDate.
	 */
	public Date getAckDate() {
		return dtAckDate;
	}//end of getAckDate()
	
	/** Retrieve the AckDate.
	 * @return Returns the dtAckDate.
	 */
	public String getAcknowledgeDate() {
		return TTKCommon.getFormattedDate(dtAckDate);
	}//end of getAckDate()
	
	/** Sets the AckDate.
	 * @param dtAckDate The dtAckDate to set.
	 */
	public void setAckDate(Date dtAckDate) {
		this.dtAckDate = dtAckDate;
	}//end of setAckDate(Date dtAckDate)
	
}//end of AcknowledgmentInfoVO
