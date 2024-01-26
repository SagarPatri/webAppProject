/**
 * @ (#) AdditionalInfoVO.java Jan 10, 2006
 * Project       : TTK HealthCare Services
 * File          : AdditionalInfoVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Jan 10, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.usermanagement;

import java.math.BigDecimal;
import java.util.Date;

import com.ttk.dto.BaseVO;

public class AdditionalInfoVO extends BaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strEmployeeNbr =  null;
	private Integer intOfficeSeqID = null;
	private String strDepartmentID =  null;
	private String strContactTypeID =null;
	private String strRegistrationNbr = null;
	private String strResidentYN =  null;
	private String strQualification =  null;
	private String strSpecTypeID =null;
	private BigDecimal bdContactPALimit = null;
	private BigDecimal bdContactClaimLimit = null;
	private String strSoftcopyOtBranch= null;
	private String strSoftcopyAccessYN= null;	
	private Date dtDateOfJoining= null;
	private Date dtDateOfResgn= null;
	
	/** Retrieve the DateOfJoining
	 * @return the dtDateOfJoining
	 */
	public Date getDateOfJoining() {
		return dtDateOfJoining;
	}//end of getDateOfJoining()

	/** Sets the DateOfJoining
	 * @param dtDateOfJoining the dtDateOfJoining to set
	 */
	public void setDateOfJoining(Date dtDateOfJoining) {
		this.dtDateOfJoining = dtDateOfJoining;
	}//end of setDateOfJoining(Date dtDateOfJoining)

	/** Retrieve the DateOfResgn
	 * @return the dtDateOfResgn
	 */
	public Date getDateOfResgn() {
		return dtDateOfResgn;
	}//end of getDateOfResgn()

	/** Sets the DateOfResgn
	 * @param dtDateOfResgn the dtDateOfResgn to set
	 */
	public void setDateOfResgn(Date dtDateOfResgn) {
		this.dtDateOfResgn = dtDateOfResgn;
	}//end of setDateOfResgn(Date dtDateOfResgn)

	/** Retrieve the soft copy branch
	 * @return the strSoftcopyOtBranch
	 */
	public String getSoftcopyOtBranch() {
		return strSoftcopyOtBranch;
	}//end of getSoftcopyOtBranch()

	/** Sets the soft copy branch
	 * @param strSoftcopyOtBranch the strSoftcopyOtBranch to set
	 */
	public void setSoftcopyOtBranch(String strSoftcopyOtBranch) {
		this.strSoftcopyOtBranch = strSoftcopyOtBranch;
	}//end of setSoftcopyOtBranch(String strSoftcopyOtBranch)

	/** Retrieve the Soft copy Access YN
	 * @return the strSoftcopyAccessYN
	 */
	public String getSoftcopyAccessYN() {
		return strSoftcopyAccessYN;
	}//end of getSoftcopyAccessYN()

	/** Sets the Soft copy Access YN
	 * @param strSoftcopyAccessYN the strSoftcopyAccessYN to set
	 */
	public void setSoftcopyAccessYN(String strSoftcopyAccessYN) {
		this.strSoftcopyAccessYN = strSoftcopyAccessYN;
	}//end of setSoftcopyAccessYN(String strSoftcopyAccessYN)

	/** Retrieve the Contact Claim Limit
	 * @return Returns the bdContactClaimLimit.
	 */
	public BigDecimal getContactClaimLimit() {
		return bdContactClaimLimit;
	}//end of getContactClaimLimit()

	/** Sets the Contact Claim Limit
	 * @param bdContactClaimLimit The bdContactClaimLimit to set.
	 */
	public void setContactClaimLimit(BigDecimal bdContactClaimLimit) {
		this.bdContactClaimLimit = bdContactClaimLimit;
	}//end of setContactClaimLimit(BigDecimal bdContactClaimLimit)

	/** Retrieve the Contact PA Limit
	 * @return Returns the bdContactPALimit.
	 */
	public BigDecimal getContactPALimit() {
		return bdContactPALimit;
	}//end of getContactPALimit()

	/** Sets the Contact PA Limit
	 * @param bdContactPALimit The bdContactPALimit to set.
	 */
	public void setContactPALimit(BigDecimal bdContactPALimit) {
		this.bdContactPALimit = bdContactPALimit;
	}//end of setContactPALimit(BigDecimal bdContactPALimit)

	/** This method returns the Office Sequence ID
	 * @return Returns the intOfficeSeqID.
	 */
	public Integer getOfficeSeqID() {
		return intOfficeSeqID;
	}// End of getOfficeSeqID()
	
	/** This method sets the Office Sequence ID 
	 * @param intBranchID The intOfficeSeqID to set.
	 */
	public void setOfficeSeqID(Integer intOfficeSeqID) {
		this.intOfficeSeqID = intOfficeSeqID;
	}// End of setOfficeSeqID(Integer intOfficeSeqID)
	
	/** This method returns the Contact Type ID 
	 * @return Returns the strContactTypeID.
	 */
	public String getContactTypeID() {
		return strContactTypeID;
	}// End of getContactTypeID()
	
	/** This method sets the Contact Type ID 
	 * @param strContactTypeID The strContactTypeID to set.
	 */
	public void setContactTypeID(String strContactTypeID) {
		this.strContactTypeID = strContactTypeID;
	}// End of setContactTypeID(String strContactTypeID)
	
	/** This method returns the DepartMent ID 
	 * @return Returns the strDepartmentID.
	 */
	public String getDepartmentID() {
		return strDepartmentID;
	}// End of getDepartmentID()
	
	/** This method sets the Department ID 
	 * @param strDepartmentID The strDepartmentID to set.
	 */
	public void setDepartmentID(String strDepartmentID) {
		this.strDepartmentID = strDepartmentID;
	}// End of setDepartmentID(String strDepartmentID)
	
	/** This method returns the Employee Number
	 * @return Returns the strEmployeeNbr.
	 */
	public String getEmployeeNbr() {
		return strEmployeeNbr;
	}// End of getEmployeeNbr()
	
	/** This method sets the Employee Number
	 * @param strEmployeeNbr The strEmployeeNbr to set.
	 */
	public void setEmployeeNbr(String strEmployeeNbr) {
		this.strEmployeeNbr = strEmployeeNbr;
	}// End of setEmployeeNbr(String strEmployeeNbr)
	
	/** This method returns the Qualification
	 * @return Returns the strQualification.
	 */
	public String getQualification() {
		return strQualification;
	}// End of getQualification()
	
	/** This method sets the Qualification
	 * @param strQualification The strQualification to set.
	 */
	public void setQualification(String strQualification) {
		this.strQualification = strQualification;
	}// End of setQualification(String strQualification)
	
	/** This method returns the Registration Number
	 * @return Returns the strRegistrationNbr.
	 */
	public String getRegistrationNbr() {
		return strRegistrationNbr;
	}// End of getRegistrationNbr()
	
	/** This method sets the Registration Number
	 * @param strRegistrationNbr The strRegistrationNbr to set.
	 */
	public void setRegistrationNbr(String strRegistrationNbr) {
		this.strRegistrationNbr = strRegistrationNbr;
	}// End of setRegistrationNbr(String strRegistrationNbr)
	
	/** This method returns the Resident Yes or NO
	 * @return Returns the strResidentYN.
	 */
	public String getResidentYN() {
		return strResidentYN;
	}// End of getResidentYN() 
	
	/** This method sets the Resident Yes or NO
	 * @param strResidentYN The strResidentYN to set.
	 */
	public void setResidentYN(String strResidentYN) {
		this.strResidentYN = strResidentYN;
	}// End of setResidentYN(String strResidentYN)
	
	/** This method returns the Spec Type Id 
	 * @return Returns the strSpecTypeID.
	 */
	public String getSpecTypeID() {
		return strSpecTypeID;
	}// End of getSpecTypeID()
	
	/** This method sets the Specific Type ID 
	 * @param strSpecTypeID The strSpecTypeID to set.
	 */
	public void setSpecTypeID(String strSpecTypeID) {
		this.strSpecTypeID = strSpecTypeID;
	}// End of setSpecTypeID(String strSpecTypeID)
	
}// End of AdditionalInfoVO
