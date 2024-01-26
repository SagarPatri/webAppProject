/**
 * @ (#) PasswordVO.java Apr 19, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PasswordVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 19, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.usermanagement;

import com.ttk.dto.BaseVO;

public class PasswordVO extends BaseVO {
	
	private String strOldPassword = "";
	private String strNewPassword = "";
	private String strConfirmPassword = "";
	private String strUserID = "";
	private String strEmpName = "";
	private String strEmpNbr = "";
	private String strCurrentPwd = "";
	private String strEnrollmentNbr = "";
	private String strPrimaryEmailID = "";
	private String strAlterEmailID = "";
	
	/** Retrieve the Alternate Email ID
	 * @return Returns the strAlterEmailID.
	 */
	public String getAlterEmailID() {
		return strAlterEmailID;
	}//end of getAlterEmailID()

	/** Sets the Alternate Email ID
	 * @param strAlterEmailID The strAlterEmailID to set.
	 */
	public void setAlterEmailID(String strAlterEmailID) {
		this.strAlterEmailID = strAlterEmailID;
	}//end of setAlterEmailID(String strAlterEmailID)

	/** Retrieve the CurrentPwd
	 * @return Returns the strCurrentPwd.
	 */
	public String getCurrentPwd() {
		return strCurrentPwd;
	}//end of getCurrentPwd()

	/** Sets the CurrentPwd
	 * @param strCurrentPwd The strCurrentPwd to set.
	 */
	public void setCurrentPwd(String strCurrentPwd) {
		this.strCurrentPwd = strCurrentPwd;
	}//end of setCurrentPwd(String strCurrentPwd)

	/** Retrieve the EmpName
	 * @return Returns the strEmpName.
	 */
	public String getEmpName() {
		return strEmpName;
	}//end of getEmpName()

	/** Sets the EmpName
	 * @param strEmpName The strEmpName to set.
	 */
	public void setEmpName(String strEmpName) {
		this.strEmpName = strEmpName;
	}//end of setEmpName(String strEmpName)

	/** Retrieve the EmpNbr
	 * @return Returns the strEmpNbr.
	 */
	public String getEmpNbr() {
		return strEmpNbr;
	}//end of getEmpNbr()

	/** Sets the EmpNbr
	 * @param strEmpNbr The strEmpNbr to set.
	 */
	public void setEmpNbr(String strEmpNbr) {
		this.strEmpNbr = strEmpNbr;
	}//end of setEmpNbr(String strEmpNbr)

	/** Retrieve the EnrollmentNbr
	 * @return Returns the strEnrollmentNbr.
	 */
	public String getEnrollmentNbr() {
		return strEnrollmentNbr;
	}//end of getEnrollmentNbr()

	/** Sets the EnrollmentNbr
	 * @param strEnrollmentNbr The strEnrollmentNbr to set.
	 */
	public void setEnrollmentNbr(String strEnrollmentNbr) {
		this.strEnrollmentNbr = strEnrollmentNbr;
	}//end of setEnrollmentNbr(String strEnrollmentNbr)

	/** Retrieve the PrimaryEmailID
	 * @return Returns the strPrimaryEmailID.
	 */
	public String getPrimaryEmailID() {
		return strPrimaryEmailID;
	}//end of getPrimaryEmailID()

	/** Sets the PrimaryEmailID
	 * @param strPrimaryEmailID The strPrimaryEmailID to set.
	 */
	public void setPrimaryEmailID(String strPrimaryEmailID) {
		this.strPrimaryEmailID = strPrimaryEmailID;
	}//end of setPrimaryEmailID(String strPrimaryEmailID)

	/** Retrieve the User ID
	 * @return Returns the strUserID.
	 */
	public String getUserID() {
		return strUserID;
	}//end of getUserID()

	/** Sets the User ID
	 * @param strUserID The strUserID to set.
	 */
	public void setUserID(String strUserID) {
		this.strUserID = strUserID;
	}//end of setUserID(String strUserID)

	/** Retrieve the Confirm Password
	 * @return Returns the strConfirmPassword.
	 */
	public String getConfirmPassword() {
		return strConfirmPassword;
	}//end of getConfirmPassword()
	
	/** Sets the Confirm Password
	 * @param strConfirmPassword The strConfirmPassword to set.
	 */
	public void setConfirmPassword(String strConfirmPassword) {
		this.strConfirmPassword = strConfirmPassword;
	}//end of setConfirmPassword(String strConfirmPassword)
	
	/** Retrieve the New Password
	 * @return Returns the strNewPassword.
	 */
	public String getNewPassword() {
		return strNewPassword;
	}//end of getNewPassword()
	
	/** Sets the New Password
	 * @param strNewPassword The strNewPassword to set.
	 */
	public void setNewPassword(String strNewPassword) {
		this.strNewPassword = strNewPassword;
	}//end of setNewPassword(String strNewPassword)
	
	/** Retrieve the Old Password
	 * @return Returns the strOldPassword.
	 */
	public String getOldPassword() {
		return strOldPassword;
	}//end of getOldPassword()
	
	/** Sets the Old Password
	 * @param strOldPassword The strOldPassword to set.
	 */
	public void setOldPassword(String strOldPassword) {
		this.strOldPassword = strOldPassword;
	}//end of setOldPassword(String strOldPassword)
}//end of PasswordVO
