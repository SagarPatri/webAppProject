/**
 * @ (#) TpaOfficeVO.java Mar 18, 2006
 * Project       : TTK HealthCare Services
 * File          : TpaOfficeVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Mar 18, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.administration;

import java.util.ArrayList;

import com.ttk.dto.BaseVO;
import com.ttk.dto.empanelment.AddressVO;

public class TpaOfficeVO extends BaseVO {

	private Long lngOfficeSequenceID = null ;
	private Long lngParentOfficeSequenceID = null;
	private String strOfficeTypeID = "";
	private String strOfficeTypeDesc = "";
	private String strOfficeName = "";
	private String strOfficeCode = "";
	private AddressVO addressVO = null;
	private String strOfficePhone1 = "";
	private String strOfficePhone2 = "";
	private String strEmailID = "";
	private String strAlternativeEmailID = "";
	private String strFax1 = "";
	private String strFax2 = "";
	private String strStdCode = "";
	private String strGeneralTypeID = "";
	private String strTollFreeNbr = "";
	private String strActiveYN = "";
    private ArrayList alOfficeList = null;
    private String strRegistrationNbr = "";
    private String strPanNo = "";
    private String strTaxDeductionAcctNo = "";

	
	/** This method returns the addressVO
	 * @return Returns the addressVO.
	 */
	public AddressVO getAddressVO() {
		return addressVO;
	}// end of getAddressVO()
	
	/** This method sets the AddressVO
	 * @param addressVO The addressVO to set.
	 */
	public void setAddressVO(AddressVO addressVO) {
		this.addressVO = addressVO;
	}// End of setAddressVO(AddressVO addressVO)
	
	/** This method returns the TPA office sequence id
	 * @return Returns the lngOfficeSequenceID.
	 */
	public Long getOfficeSequenceID() {
		return lngOfficeSequenceID;
	}// end of getOfficeSequenceID() 
	
	/** This method sets the TPA ofice sequence ID
	 * @param lngOfficeSequenceID The lngOfficeSequenceID to set.
	 */
	public void setOfficeSequenceID(Long lngOfficeSequenceID) {
		this.lngOfficeSequenceID = lngOfficeSequenceID;
	}// End of setOfficeSequenceID(Long lngOfficeSequenceID)
	
	/** This method returns the Parent TPA Office Sequence ID
	 * @return Returns the lngParentOfficeSequenceID.
	 */
	public Long getParentOfficeSequenceID() {
		return lngParentOfficeSequenceID;
	}// End of getParentOfficeSequenceID()
	
	/** This method sets the parent TPA Office Sequence ID
	 * @param lngParentOfficeSequenceID The lngParentOfficeSequenceID to set.
	 */
	public void setParentOfficeSequenceID(Long lngParentOfficeSequenceID) {
		this.lngParentOfficeSequenceID = lngParentOfficeSequenceID;
	}// End of setParentOfficeSequenceID(Long lngParentOfficeSequenceID)
	
	/** This method returns the Email ID
	 * @return Returns the strEmailID.
	 */
	public String getEmailID() {
		return strEmailID;
	}// End of getEmailID()
	
	/** This method sets the EMAIL ID
	 * @param strEmailID The strEmailID to set.
	 */
	public void setEmailID(String strEmailID) {
		this.strEmailID = strEmailID;
	}// end of setEmailID(String strEmailID)
	
	/** This method returns the Office Fax Number 1 
	 * @return Returns the strFax.
	 */
	public String getFax1() {
		return strFax1;
	}// End of getFax()
	
	/** This method sets the Office Fax Number 1 
	 * @param strFax The strFax to set.
	 */
	public void setFax1(String strFax1) {
		this.strFax1 = strFax1;
	}// end of setFax1(String strFax1)
	
	/** This method returns the TPA office code
	 * @return Returns the strOfficeCode.
	 */
	public String getOfficeCode() {
		return strOfficeCode;
	}// end of getOfficeCode()
	
	/** This method sets the TPA office code 
	 * @param strOfficeCode The strOfficeCode to set.
	 */
	public void setOfficeCode(String strOfficeCode) {
		this.strOfficeCode = strOfficeCode;
	}// end of setOfficeCode(String strOfficeCode)
	
	/** This method returns the TPA office name
	 * @return Returns the strOfficeName.
	 */
	public String getOfficeName() {
		return strOfficeName;
	}// end of getOfficeName()
	
	/** This method sets the TPA office name
	 * @param strOfficeName The strOfficeName to set.
	 */
	public void setOfficeName(String strOfficeName) {
		this.strOfficeName = strOfficeName;
	}// End of setOfficeName(String strOfficeName)
	
	/** This method returns the Office phone number1 
	 * @return Returns the strOfficePhone1.
	 */
	public String getOfficePhone1() {
		return strOfficePhone1;
	}// End of getOfficePhone1()
	
	/** This method sets the Office phone number1
	 * @param strOfficePhone1 The strOfficePhone1 to set.
	 */
	public void setOfficePhone1(String strOfficePhone1) {
		this.strOfficePhone1 = strOfficePhone1;
	}//end of setOfficePhone1(String strOfficePhone1)
	
	/** This method returns the Office phone number2
	 * @return Returns the strOfficePhone2.
	 */
	public String getOfficePhone2() {
		return strOfficePhone2;
	}// End of getOfficePhone2()
	
	/** This method sets the Office phone number 2
	 * @param strOfficePhone2 The strOfficePhone2 to set.
	 */
	public void setOfficePhone2(String strOfficePhone2) {
		this.strOfficePhone2 = strOfficePhone2;
	}//end of setOfficePhone2(String strOfficePhone2)
	
	/** This method returns the TPA Office Type ID
	 * @return Returns the strOfficeTypeID.
	 */
	public String getOfficeTypeID() {
		return strOfficeTypeID;
	}// End of getOfficeType()
	
	/** This method sets the TPA Office Type ID
	 * @param strOfficeType The strOfficeType to set.
	 */
	public void setOfficeTypeID(String strOfficeTypeID) {
		this.strOfficeTypeID = strOfficeTypeID;
	}//End of setOfficeType(String strOfficeType)

	/** This method returns the STD Code
	 * @return Returns the strStdCode.
	 */
	public String getStdCode() {
		return strStdCode;
	}// End of getStdCode()

	/** This method sets the STD Code
	 * @param strStdCode The strStdCode to set.
	 */
	public void setStdCode(String strStdCode) {
		this.strStdCode = strStdCode;
	}//End of setStdCode(String strStdCode)

	/** This method returns the Office Type Description
	 * @return Returns the strOfficeTypeDesc.
	 */
	public String getOfficeTypeDesc() {
		return strOfficeTypeDesc;
	}// End of getOfficeTypeDesc()

	/** This method sets the Office Type Description
	 * @param strOfficeTypeDesc The strOfficeTypeDesc to set.
	 */
	public void setOfficeTypeDesc(String strOfficeTypeDesc) {
		this.strOfficeTypeDesc = strOfficeTypeDesc;
	}// End of setOfficeTypeDesc(String strOfficeTypeDesc)

	/** This method returns the Alternative Email ID
	 * @return Returns the strAlternativeEmailID.
	 */
	public String getAlternativeEmailID() {
		return strAlternativeEmailID;
	}//getAlternativeEmailID()

	/** This method sets the Alternative Email ID
	 * @param strAlternativeEmailID The strAlternativeEmailID to set.
	 */
	public void setAlternativeEmailID(String strAlternativeEmailID) {
		this.strAlternativeEmailID = strAlternativeEmailID;
	}// end of setAlternativeEmailID(String strAlternativeEmailID)

	/** This method returns the General Type ID
	 * @return Returns the strGeneralTypeID.
	 */
	public String getGeneralTypeID() {
		return strGeneralTypeID;
	}// End of getGeneralTypeID()

	/** This method sets the General Type ID
	 * @param strGeneralTypeID The strGeneralTypeID to set.
	 */
	public void setGeneralTypeID(String strGeneralTypeID) {
		this.strGeneralTypeID = strGeneralTypeID;
	}// End of setGeneralTypeID(String strGeneralTypeID)

	/** This method returns the Office fax Number 2
	 * @return Returns the strFax2.
	 */
	public String getFax2() {
		return strFax2;
	}// End of getFax2()

	/** This method sets the Office fax Number 2
	 * @param strFax2 The strFax2 to set.
	 */
	public void setFax2(String strFax2) {
		this.strFax2 = strFax2;
	}// End of setFax2(String strFax2)

	/** This method returns the Toll Free number
	 * @return Returns the strTollFreeNbr.
	 */
	public String getTollFreeNbr() {
		return strTollFreeNbr;
	}// End of getTollFreeNbr()

	/** This method sets the Toll Free number
	 * @param strTollFreeNbr The strTollFreeNbr to set.
	 */
	public void setTollFreeNbr(String strTollFreeNbr) {
		this.strTollFreeNbr = strTollFreeNbr;
	}// End of setTollFreeNbr(String strTollFreeNbr)

	/** This method returns the Active Yes or No
	 * @return Returns the strActiveYN.
	 */
	public String getActiveYN() {
		return strActiveYN;
	}// End of getActiveYN()

	/** This method sets the Active Yes or No
	 * @param strActiveYN The strActiveYN to set.
	 */
	public void setActiveYN(String strActiveYN) {
		this.strActiveYN = strActiveYN;
	}// End of setActiveYN(String strActiveYN)
  
    /** This method returns the List of Branch Office
     * @return Returns the alOfficeList.
     */
    public ArrayList getOfficeList() {
        return alOfficeList;
    }//end of getOfficeList()
    
    /** This method sets the Branch Office list
     * @param alOfficeList The Branch Office list.
     */
    public void setOfficeList(ArrayList alOfficeList) {
        this.alOfficeList = alOfficeList;
    }//end of setOfficeList(ArrayList alOfficeList)

	/** This method returns the Registration Number
	 * @return Returns the strRegistrationNbr.
	 */
	public String getRegistrationNbr() {
		return strRegistrationNbr;
	}// end of getRegistrationNbr()

	/** This method sets the Registration Number
	 * @param strRegistrationNbr The strRegistrationNbr to set.
	 */
	public void setRegistrationNbr(String strRegistrationNbr) {
		this.strRegistrationNbr = strRegistrationNbr;
	}// end of setRegistrationNbr(String strRegistrationNbr)
	
	/** This method returns the PAN Number
	 * @return Returns the strPanNo.
	 */
	public String getPanNo() {
		return strPanNo;
	}// end of getPanNo()

	/** This method sets the PAN Number
	 * @param strPanNo The strPanNo to set.
	 */
	public void setPanNo(String strPanNo) {
		this.strPanNo = strPanNo;
	}// end of setPanNo(String strPanNo)
		
	/** This method returns the Tax Deduction Account Number
	 * @return Returns the strTaxDeductionAcctNo.
	 */
	public String getTaxDeductionAcctNo() {
		return strTaxDeductionAcctNo;
	}// end of getTaxDeductionAcctNo()

	/** This method sets the Tax Deduction Account Number
	 * @param strTaxDeductionAcctNo The strTaxDeductionAcctNo to set.
	 */
	public void setTaxDeductionAcctNo(String strTaxDeductionAcctNo) {
		this.strTaxDeductionAcctNo = strTaxDeductionAcctNo;
	}// end of setTaxDeductionAcctNo(String strTaxDeductionAcctNo)
	
}//End of TpaOfficeVO
