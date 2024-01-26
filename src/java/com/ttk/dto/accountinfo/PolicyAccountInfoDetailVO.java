/**
 * @ (#) PolicyAccountInfoDetailVO.java Jul 26, 2007
 * Project      : TTK HealthCare Services
 * File         : PolicyAccountInfoDetailVO.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 26, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.accountinfo;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.dto.empanelment.AddressVO;


public class PolicyAccountInfoDetailVO extends PolicyAccountInfoVO {

    private Date dtStartDate = null;
    private Date dtEndDate = null;
    private Date dtProposalDate = null;
    private BigDecimal bdTotalSumInsured = null;
    private String strProductName = "";
    private String strGroupID = "";
    private String strHomePhoneNbr ="";
    private String strMobileNbr ="";
    private String strRemarks="";
    private String strCompanyName = "";
    private  String strCompanyCode="";
    private String strPolicySubType = "";
    private String strTPAStatusType = "";
    private AddressVO familyAddressObj=null;// family Address details
    private AddressVO insuranceAddressObj=null;// insurance Address details
    private AddressVO groupAddressObj=null;// group Address details
    private Integer intEndorsementCnt = null;

    /** This method returns the to endorsement count
	 * @return Returns the intUpdateMemberCnt.
	 */
	public Integer getEndorsementCnt() {
		return intEndorsementCnt;
	}//End of getEndorsementCnt()

	/** This method sets the endorsement count
	 * @param intEndorsementCnt The intEndorsementCnt to set.
	 */
	public void setEndorsementCnt(Integer intEndorsementCnt) {
		this.intEndorsementCnt = intEndorsementCnt;
	}//End of setEndorsementCnt(Integer intEndorsementCnt)


    /**
     * Retrieve the Family Address
     *
     * @return  familyAddressObj AddressVO Family Address
     */
    public AddressVO getFamilyAddressObj() {
        return familyAddressObj;
    }//end of getFamilyAddressObj()

    /**
     * Sets the Family Address
     *
     * @param  familyAddressObj AddressVO Family Address
     */
    public void setFamilyAddressObj(AddressVO familyAddressObj) {
        this.familyAddressObj = familyAddressObj;
    }//end of setFamilyAddressObj(AddressVO familyAddressObj)

    /**
     * Retrieve the Insurance Address
     *
     * @return  insuranceAddressObj AddressVO Insurance Address
     */
    public AddressVO getInsuranceAddressObj() {
        return insuranceAddressObj;
    }//end of getPayOrdBankAddressVO()

    /**
     * Sets the Insurance Address
     *
     * @param  insuranceAddressObj AddressVO Insurance Address
     */
    public void setInsuranceAddressObj(AddressVO insuranceAddressObj) {
        this.insuranceAddressObj = insuranceAddressObj;
    }//end of setInsuranceAddressObj(AddressVO insuranceAddressObj)

    /**
     * Retrieve the Group Address
     *
     * @return  groupAddressObj AddressVO Group Address
     */
    public AddressVO getGroupAddressObj() {
        return groupAddressObj;
    }//end of getGroupAddressObj()

    /**
     * Sets the Group Address
     *
     * @param  groupAddressObj AddressVO Group Address
     */
    public void setGroupAddressObj(AddressVO groupAddressObj) {
        this.groupAddressObj = groupAddressObj;
    }//end of setGroupAddressObj(AddressVO groupAddressObj)

    /** This method returns the TPA Status General Type ID
     * @return Returns the strTPAStatusTypeID.
     */
    public String getTPAStatusType() {
        return strTPAStatusType;
    }//end of getTPAStatusTypeID()

    /** This method sets the TPA Status General Type ID
     * @param strTPAStatusTypeID The strTPAStatusTypeID to set.
     */
    public void setTPAStatusType(String strTPAStatusType) {
        this.strTPAStatusType = strTPAStatusType;
    }//end of setTPAStatusTypeID(String strTPAStatusTypeID)


    /** This method returns the Policy Sub Type
	 * @return Returns the strPolicySubType.
	 */
	public String getPolicySubType() {
		return strPolicySubType;
	}//End of getPolicySubType()

	/** This method sets the Policy Sub Tpe
	 * @param strPolicySubType The strPolicySubType to set.
	 */
	public void setPolicySubType(String strPolicySubType) {
		this.strPolicySubType = strPolicySubType;
	}//End of setPolicySubType(String strPolicySubType)

    /** This method returns the Start Date
     * @return Returns the dtStartDate.
     */
    public Date getStartDate() {
        return dtStartDate;
    }//end of getStartDate()

    /** This method sets the Start Date
     * @param dtStartDate to set.
     */
    public void setStartDate(Date dtStartDate) {
        this.dtStartDate = dtStartDate;
    }//end of setStartDate(Date dtStartDate)

    /** This method returns the End Date
     * @return Returns the dtEndDate.
     */
    public Date getEndDate() {
        return dtEndDate;
    }//end of getEndDate()

    /** This method sets the End Date
     * @param dtEndDate to set.
     */
    public void setEndDate(Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }//end of setEndDate(Date dtEndDate)

    /** This method returns the Total Sum Insured
     * @return Returns the bdTotalSumInsured.
     */
    public BigDecimal getTotalSumInsured() {
        return bdTotalSumInsured;
    }//end of getTotalSumInsured()

    /** This method sets the Total Sum Insured
     * @param bdTotalSumInsured to set.
     */
    public void setTotalSumInsured(BigDecimal bdTotalSumInsured) {
        this.bdTotalSumInsured = bdTotalSumInsured;
    }//end of setTotalSumInsured(BigDecimal bdTotalSumInsured)

    /** This method returns the Product Name
     * @return Returns the strProductName.
     */
    public String getProductName() {
        return strProductName;
    }// End of getProductName()

    /** This method sets the Product Name
     * @param strProductName to set.
     */
    public void setProductName(String strProductName) {
        this.strProductName = strProductName;
    }//End of setProductName(String strProductName)

    /** This method returns the Home Phone Number
     * @return Returns the strHomePhoneNbr.
     */
    public String getHomePhoneNbr() {
        return strHomePhoneNbr;
    }//end of getHomePhoneNbr()

    /** This method sets the Home Phone Number
     * @param strHomePhoneNbr to set.
     */
    public void setHomePhoneNbr(String strHomePhoneNbr) {
        this.strHomePhoneNbr = strHomePhoneNbr;
    }//end of setHomePhoneNbr(String strHomePhoneNbr)

    /** This method returns the Mobile Number
     * @return Returns the strMobileNbr.
     */
    public String getMobileNbr() {
        return strMobileNbr;
    }//end of getMobileNbr()

    /** This method sets the Mobile Number
     * @param strMobileNbr to set.
     */
    public void setMobileNbr(String strMobileNbr) {
        this.strMobileNbr = strMobileNbr;
    }//end of setMobileNbr(String strMobileNbr)

    /**
     * This method sets the remarks
     * @param strRemarks String
     */
    public void setRemarks(String strRemarks)
    {
        this.strRemarks = strRemarks;
    }//end of setRemarks(String strRemarks)

    /**
     * This method returns remarks
     * @return strRemarks String
     */
    public String getRemarks()
    {
        return strRemarks;
    }//end of getRemarks()

    /** This method returns the Group ID
     * @return Returns the strGroupID.
     */
    public String getGroupID() {
        return strGroupID;
    }//end of getGroupID()

    /** This method sets the Group ID
     * @param strGroupID to set.
     */
    public void setGroupID(String strGroupID) {
        this.strGroupID = strGroupID;
    }//end of setGroupID(String strGroupID)

    /** This method returns the Insurance Company Name
     * @return Returns the strCompanyName.
     */
    public String getCompanyName() {
        return strCompanyName;
    }//end of getCompanyName()

    /** This method sets the Insurance Company Name
     * @param strCompanyName to set.
     */
    public void setCompanyName(String strCompanyName) {
        this.strCompanyName = strCompanyName;
    }//end of setCompanyName(String strCompanyName)

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

	/**
	 * @return Returns the dtProposalDate.
	 */
	public Date getProposalDate() {
		return dtProposalDate;
	}

	/**
	 * @param dtProposalDate The dtProposalDate to set.
	 */
	public void setProposalDate(Date dtProposalDate) {
		this.dtProposalDate = dtProposalDate;
	}
}//end of PolicyAccountInfoDetailVO.java
