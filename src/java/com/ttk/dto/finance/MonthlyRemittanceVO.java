/**
 * @ (#) MonthlyRemittanceVO.java August 10th, 2009
 * Project 	     : TTK HealthCare Services
 * File          : MonthlyRemittanceVO.java
 * Author        : Navin Kumar R
 * Company       : Span Systems Corporation
 * Date Created  : August 10th, 2009
 *
 * @author       :  
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.finance;

import com.ttk.dto.BaseVO;

/** This is a VO class used for Monthly Remittance.
 * @author ramakrishna_km
 *
 */
public class MonthlyRemittanceVO extends BaseVO{

	/** long serialVersionUID */
	private static final long serialVersionUID = 625843391801801455L;	

	//Grid Display	
	private Long lngMasterSeqID = null;
	private String strInsCompanyName = null;
	private String strYear = null;
	private String strMonth = null;
	private String strChallanrefNbr = null;
	private String strMonthlyRemitImageName = "AddIcon";
    private String strMonthlyRemitImageTitle = "Monthly Remittance Info";
	
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
	
	/** This method returns the Year
	 * @return Returns the strYear.
	 */
	public String getYear() {
		return strYear;
	}//End of getYear()
	
	/**This Method sets the Year
	 * @param strYear The strYear to set.
	 */
	public void setYear(String strYear) {
		this.strYear = strYear;
	}//End of setYear(String strYear)
	
	/** This method returns the Month
	 * @return Returns the strMonth.
	 */
	public String getMonth() {
		return strMonth;
	}//End of getMonth()
	
	/**This Method sets the Month
	 * @param strMonth The strMonth to set.
	 */
	public void setMonth(String strMonth) {
		this.strMonth = strMonth;
	}//End of setMonth(String strMonth)
	
	/** Retrieve the ChallanrefNbr.
	 * @return Returns the strChallanrefNbr.
	 */
	public String getChallanrefNbr() {
		return strChallanrefNbr;
	}//end of getChallanrefNbr()
	
	/** Sets the ChallanrefNbr.
	 * @param strChallanrefNbr The strChallanrefNbr to set.
	 */
	public void setChallanrefNbr(String strChallanrefNbr) {
		this.strChallanrefNbr = strChallanrefNbr;
	}//end of setChallanrefNbr(String strChallanrefNbr)
	
	/** Retrieve the MonthlyRemitImageName
	 * @return Returns the strMonthlyRemitImageName.
	 */
	public String getMonthlyRemitImageName() {
		return strMonthlyRemitImageName;
	}//end of getMonthlyRemitImageName()

	/** Sets the MonthlyRemitImageName
	 * @param strMonthlyRemitImageName The strMonthlyRemitImageName to set.
	 */
	public void setMonthlyRemitImageName(String strMonthlyRemitImageName) {
		this.strMonthlyRemitImageName = strMonthlyRemitImageName;
	}//end of setMonthlyRemitImageName(String strMonthlyRemitImageName)

	/** Retrieve the MonthlyRemitImageTitle
	 * @return Returns the strMonthlyRemitImageTitle.
	 */
	public String getMonthlyRemitImageTitle() {
		return strMonthlyRemitImageTitle;
	}//end of getMonthlyRemitImageTitle()

	/** Sets the MonthlyRemitImageTitle
	 * @param strMonthlyRemitImageTitle The strMonthlyRemitImageTitle to set.
	 */
	public void setMonthlyRemitImageTitle(String strMonthlyRemitImageTitle) {
		this.strMonthlyRemitImageTitle = strMonthlyRemitImageTitle;
	}//end of setMonthlyRemitImageTitle(String strMonthlyRemitImageTitle)
}//end of MonthlyRemittanceVO 