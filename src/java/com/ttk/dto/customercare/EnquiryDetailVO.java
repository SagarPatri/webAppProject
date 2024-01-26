/**
 * @ (#) EnquiryDetailVO.java Nov 14, 2006
 * Project 	     : TTK HealthCare Services
 * File          : EnquiryDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Nov 14, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.customercare;

import com.ttk.dto.BaseVO;

public class EnquiryDetailVO extends BaseVO{
	
	private String strName = "";//HospitalName/InsuranceCompany/TTKOffice
	private String strShortName = "";
	private String strAbbrCode = "";//CompanyAbbrevation/OfficeCode
	private String strReportTo = "";
	private String strOfficeType = "";
	private String strOfficePhone1 = "";
	private String strOfficePhone2 = "";
	private String strEmailID = "";
	private String strAlternateEmailID = "";
	private String strFax1 = "";
	private String strAlternateFax = "";
	private String strStdCode = "";
	private String strTollFreeNbr = "";
	private String strActiveYN = "";
	private String strAddress1="";// Address1
    private String strAddress2="";// Address2
    private String strAddress3="";// Address3
    private String strPinCode="";// Pin Code
    private String strCityDesc = "";
    private String strStateName = "";
    private String strCountryName = "";
    private String strLandmark = "";
	
	/** Retrieve the ActiveYN
	 * @return Returns the strActiveYN.
	 */
	public String getActiveYN() {
		return strActiveYN;
	}//end of getActiveYN()

	/** Sets the ActiveYN
	 * @param strActiveYN The strActiveYN to set.
	 */
	public void setActiveYN(String strActiveYN) {
		this.strActiveYN = strActiveYN;
	}//end of setActiveYN(String strActiveYN)

	/** Retrieve the Address1
	 * @return Returns the strAddress1.
	 */
	public String getAddress1() {
		return strAddress1;
	}//end of getAddress1()

	/** Sets the Address1
	 * @param strAddress1 The strAddress1 to set.
	 */
	public void setAddress1(String strAddress1) {
		this.strAddress1 = strAddress1;
	}//end of setAddress1(String strAddress1)

	/** Retrieve the Address2
	 * @return Returns the strAddress2.
	 */
	public String getAddress2() {
		return strAddress2;
	}//end of getAddress2()

	/** Sets the Address2
	 * @param strAddress2 The strAddress2 to set.
	 */
	public void setAddress2(String strAddress2) {
		this.strAddress2 = strAddress2;
	}//end of setAddress2(String strAddress2)

	/** Retrieve the Address3
	 * @return Returns the strAddress3.
	 */
	public String getAddress3() {
		return strAddress3;
	}//end of getAddress3()

	/** Sets the Address3
	 * @param strAddress3 The strAddress3 to set.
	 */
	public void setAddress3(String strAddress3) {
		this.strAddress3 = strAddress3;
	}//end of setAddress3(String strAddress3)

	/** Retrieve the Alternate EmailID
	 * @return Returns the strAlternateEmailID.
	 */
	public String getAlternateEmailID() {
		return strAlternateEmailID;
	}//end of getAlternateEmailID()

	/** Sets the Alternate EmailID
	 * @param strAlternateEmailID The strAlternateEmailID to set.
	 */
	public void setAlternateEmailID(String strAlternateEmailID) {
		this.strAlternateEmailID = strAlternateEmailID;
	}//end of setAlternateEmailID(String strAlternateEmailID)

	/** Retrieve the Alternate Fax
	 * @return Returns the strAlternateFax.
	 */
	public String getAlternateFax() {
		return strAlternateFax;
	}//end of getAlternateFax()

	/** Sets the Alternative Fax
	 * @param strAlternateFax The strAlternateFax to set.
	 */
	public void setAlternateFax(String strAlternateFax) {
		this.strAlternateFax = strAlternateFax;
	}//end of setAlternateFax(String strAlternateFax)

	/** Retrieve the City Description
	 * @return Returns the strCityDesc.
	 */
	public String getCityDesc() {
		return strCityDesc;
	}//end of getCityDesc()

	/** Sets the City Description
	 * @param strCityDesc The strCityDesc to set.
	 */
	public void setCityDesc(String strCityDesc) {
		this.strCityDesc = strCityDesc;
	}//end of setCityDesc(String strCityDesc)

	/** Retrieve the CountryName
	 * @return Returns the strCountryName.
	 */
	public String getCountryName() {
		return strCountryName;
	}//end of getCountryName()

	/** Sets the CountryName
	 * @param strCountryName The strCountryName to set.
	 */
	public void setCountryName(String strCountryName) {
		this.strCountryName = strCountryName;
	}//end of setCountryName(String strCountryName)

	/** Retrieve the EmailID
	 * @return Returns the strEmailID.
	 */
	public String getEmailID() {
		return strEmailID;
	}//end of getEmailID()

	/** Sets the EmailID
	 * @param strEmailID The strEmailID to set.
	 */
	public void setEmailID(String strEmailID) {
		this.strEmailID = strEmailID;
	}//end of setEmailID(String strEmailID)

	/** Retrieve the Fax1
	 * @return Returns the strFax1.
	 */
	public String getFax1() {
		return strFax1;
	}//end of getFax1()

	/** Sets the Fax1
	 * @param strFax1 The strFax1 to set.
	 */
	public void setFax1(String strFax1) {
		this.strFax1 = strFax1;
	}//end of setFax1(String strFax1)

	/** Retrieve the Landmark
	 * @return Returns the strLandmark.
	 */
	public String getLandmark() {
		return strLandmark;
	}//end of getLandmark()

	/** Sets the Landmark
	 * @param strLandmark The strLandmark to set.
	 */
	public void setLandmark(String strLandmark) {
		this.strLandmark = strLandmark;
	}//end of setLandmark(String strLandmark)

	/** Retrieve the Name
	 * @return Returns the strName.
	 */
	public String getName() {
		return strName;
	}//end of getName()

	/** Sets the Name
	 * @param strName The strName to set.
	 */
	public void setName(String strName) {
		this.strName = strName;
	}//end of setName(String strName)

	/** Retrieve the OfficePhone1
	 * @return Returns the strOfficePhone1.
	 */
	public String getOfficePhone1() {
		return strOfficePhone1;
	}//end of getOfficePhone1()

	/** Sets the OfficePhone1
	 * @param strOfficePhone1 The strOfficePhone1 to set.
	 */
	public void setOfficePhone1(String strOfficePhone1) {
		this.strOfficePhone1 = strOfficePhone1;
	}//end of setOfficePhone1(String strOfficePhone1)

	/** Retrieve the OfficePhone2
	 * @return Returns the strOfficePhone2.
	 */
	public String getOfficePhone2() {
		return strOfficePhone2;
	}//end of getOfficePhone2()

	/** Sets the OfficePhone2
	 * @param strOfficePhone2 The strOfficePhone2 to set.
	 */
	public void setOfficePhone2(String strOfficePhone2) {
		this.strOfficePhone2 = strOfficePhone2;
	}//end of setOfficePhone2(String strOfficePhone2)

	/** Retrieve the OfficeType
	 * @return Returns the strOfficeType.
	 */
	public String getOfficeType() {
		return strOfficeType;
	}//end of getOfficeType()

	/** Sets the OfficeType
	 * @param strOfficeType The strOfficeType to set.
	 */
	public void setOfficeType(String strOfficeType) {
		this.strOfficeType = strOfficeType;
	}//end of setOfficeType(String strOfficeType)

	/** Retrieve the PinCode
	 * @return Returns the strPinCode.
	 */
	public String getPinCode() {
		return strPinCode;
	}//end of getPinCode()

	/** Sets the PinCode
	 * @param strPinCode The strPinCode to set.
	 */
	public void setPinCode(String strPinCode) {
		this.strPinCode = strPinCode;
	}//end of setPinCode(String strPinCode)

	/** Retrieve the StateName
	 * @return Returns the strStateName.
	 */
	public String getStateName() {
		return strStateName;
	}//end of getStateName()

	/** Sets the StateName
	 * @param strStateName The strStateName to set.
	 */
	public void setStateName(String strStateName) {
		this.strStateName = strStateName;
	}//end of setStateName(String strStateName)

	/** Retrieve the StdCode
	 * @return Returns the strStdCode.
	 */
	public String getStdCode() {
		return strStdCode;
	}//end of getStdCode()

	/** Sets the StdCode
	 * @param strStdCode The strStdCode to set.
	 */
	public void setStdCode(String strStdCode) {
		this.strStdCode = strStdCode;
	}//end of setStdCode(String strStdCode)

	/** Retrieve the TollFreeNbr
	 * @return Returns the strTollFreeNbr.
	 */
	public String getTollFreeNbr() {
		return strTollFreeNbr;
	}//end of getTollFreeNbr()

	/** Sets the TollFreeNbr
	 * @param strTollFreeNbr The strTollFreeNbr to set.
	 */
	public void setTollFreeNbr(String strTollFreeNbr) {
		this.strTollFreeNbr = strTollFreeNbr;
	}//end of setTollFreeNbr(String strTollFreeNbr)

	/** Retrieve the AbbrCode
	 * @return Returns the strAbbrCode.
	 */
	public String getAbbrCode() {
		return strAbbrCode;
	}//end of getAbbrCode()
	
	/** Sets the AbbrCode
	 * @param strAbbrCode The strAbbrCode to set.
	 */
	public void setAbbrCode(String strAbbrCode) {
		this.strAbbrCode = strAbbrCode;
	}//end of setAbbrCode(String strAbbrCode)
	
	/** Retrieve the ReportTo
	 * @return Returns the strReportTo.
	 */
	public String getReportTo() {
		return strReportTo;
	}//end of getReportTo()
	
	/** Sets the ReportTo
	 * @param strReportTo The strReportTo to set.
	 */
	public void setReportTo(String strReportTo) {
		this.strReportTo = strReportTo;
	}//end of setReportTo(String strReportTo)
	
	/** Retrieve the ShortName
	 * @return Returns the strShortName.
	 */
	public String getShortName() {
		return strShortName;
	}//end of getShortName()
	
	/** Sets the ShortName
	 * @param strShortName The strShortName to set.
	 */
	public void setShortName(String strShortName) {
		this.strShortName = strShortName;
	}//end of setShortName(String strShortName)
	
}//end of EnquiryDetailVO
