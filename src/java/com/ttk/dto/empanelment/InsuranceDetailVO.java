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

import java.math.BigDecimal;
import java.util.Date;

//import com.ttk.common.TTKCommon;

public class InsuranceDetailVO extends InsuranceVO {
	private Long lngParentSeqID =null;
	 private  String strCompanyCode="";
	 private String strFundDisbursalCode="";
     private String strFrequencyCode="";
     private Long lngReplenishmentPeriod = null;
	 private Date dtStartDate=null;
	 private  Date dtEndDate= null;
     private AddressVO address=null;
     private Long lngOfficeSequenceID = null;
     private  Date dtRegDate= null; 
     private MailNotificationVO mailnotification = null;
     
     private String insCategory;
     
     
     private String strAuthStandard="";
     private String struserRestrictionGroup = "";
     private String regAuthority ="";
     private String sCompanyCode ="";
     private String vatTrnCode ="";
     private double vatPercent;
     
     
     private String productSeqId ="";
     private String productName ="";
     private String productCode ="";
     private String productType ="";
     private String networkType ="";
     private String description ="";
     
     private String productIdList ="";
     
     private String policygracePeriod = "";
     private String policygapPeriod = "";
     private String effectivefromdateforpolicygracePeriod="";
     private String effectivefromdateforpolicygapPeriod="";
     public String getAuthStandard() {
 		return strAuthStandard;
 	}

 	public void setAuthStandard(String strAuthStandard) {
 		this.strAuthStandard = strAuthStandard;
 	}
 	public void setuserRestrictionGroup(String struserRestrictionGroup) {
		this.struserRestrictionGroup = struserRestrictionGroup;
	}

	public String getuserRestrictionGroup() {
		return struserRestrictionGroup;
	}

 	//KOC Cigna_insurance_resriction
//added for broker cr kocb

	public Date getRegDate() {
		return dtRegDate;
	}

	public void setRegDate(Date dtRegDate) {
		this.dtRegDate = dtRegDate;
	}
   
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

	
	/** This method returns the Insurance Company Code
	 * @return Returns the strCompanyCode.
	 */
	public String getCompanyCode() {
		return strCompanyCode;
	}// End of getCompanyCode() 
	
	/** This method sets the Insurance Company Code
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
    //added for Mail-SMS Template for Cigna
    public void setMailnotification(MailNotificationVO mailnotification) {
		this.mailnotification = mailnotification;
	}

	public MailNotificationVO getMailnotification() {
		return mailnotification;
	}
	public String getInsCategory() {
		return insCategory;
	}

	
	public void setInsCategory(String insCategory) {
		this.insCategory = insCategory;
	}

	/**
	 * @return the regAuthority
	 */
	public String getRegAuthority() {
		return regAuthority;
	}

	/**
	 * @param regAuthority the regAuthority to set
	 */
	public void setRegAuthority(String regAuthority) {
		this.regAuthority = regAuthority;
	}

	/**
	 * @return the sCompanyCode
	 */
	public String getsCompanyCode() {
		return sCompanyCode;
	}

	/**
	 * @param sCompanyCode the sCompanyCode to set
	 */
	public void setsCompanyCode(String sCompanyCode) {
		this.sCompanyCode = sCompanyCode;
	}

	public String getVatTrnCode() {
		return vatTrnCode;
	}

	public void setVatTrnCode(String vatTrnCode) {
		this.vatTrnCode = vatTrnCode;
	}

	/*public int getVatPercent() {
		return vatPercent;
	}

	public void setVatPercent(int vatPercent) {
		this.vatPercent = vatPercent;
	}*/

	public String getProductSeqId() {
		return productSeqId;
	}

	public void setProductSeqId(String productSeqId) {
		this.productSeqId = productSeqId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getVatPercent() {
		return vatPercent;
	}

	public void setVatPercent(double vatPercent) {
		this.vatPercent = vatPercent;
	}

	public String getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(String productIdList) {
		this.productIdList = productIdList;
	}

	public String getPolicygracePeriod() {
		return policygracePeriod;
	}

	public void setPolicygracePeriod(String policygracePeriod) {
		this.policygracePeriod = policygracePeriod;
	}

	public String getPolicygapPeriod() {
		return policygapPeriod;
	}

	public void setPolicygapPeriod(String policygapPeriod) {
		this.policygapPeriod = policygapPeriod;
	}

	public String getEffectivefromdateforpolicygracePeriod() {
		return effectivefromdateforpolicygracePeriod;
	}

	public void setEffectivefromdateforpolicygracePeriod(
			String effectivefromdateforpolicygracePeriod) {
		this.effectivefromdateforpolicygracePeriod = effectivefromdateforpolicygracePeriod;
	}

	public String getEffectivefromdateforpolicygapPeriod() {
		return effectivefromdateforpolicygapPeriod;
	}

	public void setEffectivefromdateforpolicygapPeriod(
			String effectivefromdateforpolicygapPeriod) {
		this.effectivefromdateforpolicygapPeriod = effectivefromdateforpolicygapPeriod;
	}

	
	
}// End of InsuranceDetailVO
