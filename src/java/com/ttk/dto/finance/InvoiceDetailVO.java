/**
 *   @ (#) InviceVO.java Nov 06, 2006
 *   Project      : TTK HealthCare Services
 *   File         : InviceVO.java
 *   Author       : Krishna K H
 *   Company      : Span Systems Corporation
 *   Date Created : Nov 06, 2006
 *
 *   @author       : Krishna K H
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dto.finance;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This VO contains Invoice Detail.
 */

public class InvoiceDetailVO extends InvoiceVO{

    private String strOfficeCode= "";
	private String strAbbrevationCode= "";
	private String strEnrollmentDesc= "";
	private String strEnrollmentNbr = "";
    private String strInsCompCode= "";
    private String strInsuredName = "";
    private String strDependents = "";
    private Date dtPolicyIssueDate =null;
    private BigDecimal bdTotalSumInsured = null;

    private Long lngTenure = null;

   /** Retrieve the Office Code
	 * @return Returns the strOfficeCode.
	 */
	public String getOfficeCode() {
		return strOfficeCode;
	}//end of getOfficeCode()

	/** Sets the Office Code
	 * @param strOfficeCode The strOfficeCode to set.
	 */
	public void setOfficeCode(String strOfficeCode) {
		this.strOfficeCode = strOfficeCode;
	}//end of setOfficeCode(String strOfficeCode)

	/** Retrieve the Tenure
	 * @return Returns the lngTenure.
	 */
	public Long getTenure() {
		return lngTenure;
	}//end of getTenure()

	/** Sets the Tenure
	 * @param lngTenure The lngTenure to set.
	 */
	public void setTenure(Long lngTenure) {
		this.lngTenure = lngTenure;
	}//end of setTenure(Long lngTenure)

	/** Retrieve the Abbrevation Code
	 * @return Returns the strAbbrevationCode.
	 */
	public String getAbbrevationCode() {
		return strAbbrevationCode;
	}//end of getAbbrevationCode()

	/** Sets the Abbrevation Code
	 * @param strAbbrevationCode The strAbbrevationCode to set.
	 */
	public void setAbbrevationCode(String strAbbrevationCode) {
		this.strAbbrevationCode = strAbbrevationCode;
	}//end of setAbbrevationCode(String strAbbrevationCode)

	/** Retrieve the Enrollment Description
	 * @return Returns the strEnrollmentDesc.
	 */
	public String getEnrollmentDesc() {
		return strEnrollmentDesc;
	}//end of getEnrollmentDesc()

	/** Sets the Enrollment Description
	 * @param strEnrollmentDesc The strEnrollmentDesc to set.
	 */
	public void setEnrollmentDesc(String strEnrollmentDesc) {
		this.strEnrollmentDesc = strEnrollmentDesc;
	}//end of setEnrollmentDesc(String strEnrollmentDesc)

    /**Retrieve the Enrollment Number.
     * @return Returns the strAddress.
     */
    public String getEnrollmentNbr() {
        return strEnrollmentNbr;
    }//end of getEnrollmentNbr()

    /**Sets the Enrollment Number.
     * @param strEnrollmentNbr The strEnrollmentNbr to set.
     */
    public void setEnrollmentNbr(String strEnrollmentNbr) {
        this.strEnrollmentNbr = strEnrollmentNbr;
    }//end of setEnrollmentNbr(String strEnrollmentNbr)

    /**Sets the InsCompCode.
     * @return Returns the strInsCompCode.
     */
    public String getInsCompCode() {
        return strInsCompCode;
    }//end of getInsCompCode()

    /**Retrieve the InsCompCode.
     * @param strInsCompCode The strInsCompCode to set.
     */
    public void setInsCompCode(String strInsCompCode) {
        this.strInsCompCode = strInsCompCode;
    }//end of setInsCompCode(String strInsCompCode)

    /**Sets the Insured Name.
     * @return Returns the strInsuredName.
     */
    public String getInsuredName() {
        return strInsuredName;
    }//end of getInsuredName()

    /**Retrieve the Insured Name.
     * @param strInsuredName The strInsuredName to set.
     */
    public void setInsuredName(String strInsuredName) {
        this.strInsuredName = strInsuredName;
    }//end of setInsuredName(String strInsuredName)

    /**Sets the Dependents.
     * @return Returns the strDependents.
     */
    public String getDependents() {
        return strDependents;
    }//end of getDependents()

    /**Retrieve the Dependents.
     * @param strDependents The strDependents to set.
     */
    public void setDependents(String strDependents) {
        this.strDependents = strDependents;
    }//end of setDependents(String strDependents)

    /**Sets the Policy Issue Date.
     * @return Returns the dtPolicyIssueDate.
     */
    public Date getPolicyIssueDate() {
        return dtPolicyIssueDate;
    }//end of getDependents()

    /**Retrieve the Policy Issue Date.
     * @param dtPolicyIssueDate The dtPolicyIssueDate to set.
     */
    public void setPolicyIssueDate(Date dtPolicyIssueDate) {
        this.dtPolicyIssueDate = dtPolicyIssueDate;
    }//end of setDependents(String dtPolicyIssueDate)

    /** This method returns the Total Sum Insured
     * @return Returns the bdTotalSumInsured.
     */
    public BigDecimal getTotalSumInsured() {
        return bdTotalSumInsured;
    }//end of getTotalSumInsured()

    /** This method sets the Total Sum Insured
     * @param bdTotalSumInsured The bdTotalSumInsured to set.
     */
    public void setTotalSumInsured(BigDecimal bdTotalSumInsured) {
        this.bdTotalSumInsured = bdTotalSumInsured;
    }//end of setTotalSumInsured(BigDecimal bdTotalSumInsured)
}//end of InviceVO