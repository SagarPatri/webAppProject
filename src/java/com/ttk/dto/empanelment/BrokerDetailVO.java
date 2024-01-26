/**
 * @ (#) InsuranceDetailVO.java Oct 19, 2005
 * Project      : ttkHealthCareServices
 * File         : InsuranceDetailVO.java
 * Author       : Ravindra
 * Company      : SpanSystem Corp:
 * Date Created : Oct 19, 2005
 *
 * @author       :  Ravindra

 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.empanelment;

import java.util.Date;

//import com.ttk.common.TTKCommon;

public class BrokerDetailVO extends BrokerVO {
	
	 private Long lngParentSeqID =null;
	 private  String strCompanyCode="";
	 private String strFundDisbursalCode="";
     private String strFrequencyCode="";
     private Long lngReplenishmentPeriod = null;
	 private Date dtStartDate=null;
	 private  Date dtEndDate= null;
     private AddressVO address=null;
     public String getRegAuthority() {
		return regAuthority;
	}

	public void setRegAuthority(String regAuthority) {
		this.regAuthority = regAuthority;
	}

	private Long lngOfficeSequenceID = null;
     private String regAuthority ="";

	/** This method returns the End Date
	 * @return Returns the dtEndDate.
	 */
	public Date  getEndDate() {
		return dtEndDate;
	}// End of getEndDate()
	
	/** This method sets the End Date
	 * @param dtEndDate The dtEndDate to set.
	 */
	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}// End of setEndDate(Date dtEndDate)
	
	/** This method returns the Start Date
	 * @return Returns the dtStartDate.
	 */
	public Date getStartDate() {
		return dtStartDate;
	}// End of getStartDate()
	
	/** This method sets the Start Date
	 * @param dtStartDate The dtStartDate to set.
	 */
	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}// End of setStartDate(Date dtStartDate)
	
	/** This method returns the Insurance Company Address
	 * @return Returns the insuranceAddress.
	 */
	public AddressVO getAddress() {
		return address;
	}// End of getAddress()
    
	/** This method sets the Insurance Company Address
	 * @param insuranceCompanyAddress The insuranceAddress to set.
	 */
	public void setAddress(AddressVO insuranceAddress) {
		this.address = insuranceAddress;
	}// End of setAddress(AddressVO insuranceAddress)

	
	/** This method returns the Broker Code
	 * @return Returns the strCompanyCode.
	 */
	public String getCompanyCode() {
		return strCompanyCode;
	}// End of getCompanyCode() 
	
	/** This method sets the Broker Code
	 * @param strCompanyCode The strCompanyCode to set.
	 */
	public void setCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}// End of setCompanyCode(String strCompanyCode)
	
	/** This method returns the Funds Disbursal Code
	 * @return Returns the strFundDisbursalCode.
	 */
	public String getFundDisbursalCode() {
		return strFundDisbursalCode;
	}// End of getFundDisbursalCode()
	
	/** This method sets the  Funds Disbursal Code
	 * @param strFundDisbursalCode The strFundDisbursalCode to set.
	 */
	public void setFundDisbursalCode(String strFundDisbursalCode) {
		this.strFundDisbursalCode = strFundDisbursalCode;
	}// End of setFundDisbursalCode(String strFundDisbursalCode)
	
	/** This method returns the Funds Disbursal Frequency Code
	 * @return Returns the strFundDisbursalFrequencyCode.
	 */
	public String getFrequencyCode() {
		return strFrequencyCode;
	}// End of getFrequencyCode()
	
	/** This method sets the Funds Disbursal Frequency Code
	 * @param strFrequencyCode The strFrequencyCode to set.
	 */
	public void setFrequencyCode(String strFrequencyCode) {
		this.strFrequencyCode = strFrequencyCode;
	}// End of setFrequencyCode(String strFrequencyCode)
	
	/**  This method returns the Parent Insurance Company Sequence ID
	 * @return Returns the lngParentSeqID.
	 */
	public Long getParentInsSeqID() {
		return lngParentSeqID;
	}// End of getParentInsuranceCompanyID()
	
    /** This method sets the Parent Insurance Company Sequence ID
     * @param lngParentSeqID The lngParentSeqID to set.
     */
    public void setParentInsSeqID(Long lngParentSeqID) {
        this.lngParentSeqID= lngParentSeqID;
    }// End of setParentInsSeqID(Long lngParentInsSeqID)
    
    /** This method returns the Float Replenishment Period
     * @return Returns the lngReplenishmentPeriod.
     */
    public Long getReplenishmentPeriod() {
        return lngReplenishmentPeriod;
    }// End of getReplenishmentPeriod()
    
    /** This method sets the Float Replenishment Period
     * @param lngReplenishmentPeriod The lngReplenishmentPeriod to set.
     */
    public void setReplenishmentPeriod(Long lngReplenishmentPeriod) {
        this.lngReplenishmentPeriod = lngReplenishmentPeriod;
    }// End of setReplenishmentPeriod(Long lngReplenishmentPeriod)
    
    /** This method returns the Insurance Office Sequence ID
     * @return Returns the lngOfficeSequenceID.
     */
    public Long getOfficeSequenceID() {
        return lngOfficeSequenceID;
    }// End of getOfficeSequenceID()
    
    /** This method sets the Insurance Office Sequence ID
     * @param lngOfficeSequenceID The lngOfficeSequenceID to set.
     */
    public void setOfficeSequenceID(Long lngOfficeSequenceID) {
        this.lngOfficeSequenceID = lngOfficeSequenceID;
    }// End of setOfficeSequenceID(Long lngOfficeSequenceID)
}// End of InsuranceDetailVO
