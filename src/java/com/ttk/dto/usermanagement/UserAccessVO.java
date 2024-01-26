/**
 * @ (#) UserAccessVO.java Jan 10, 2006
 * Project       : TTK HealthCare Services
 * File          : UserAccessVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Jan 10, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.usermanagement;

import com.ttk.dto.BaseVO;

public class UserAccessVO extends BaseVO {
	
	private String strUserType = "";
	private Long lngRoleID = null;
	private String strUserID = "";
	private String strAccessYN = "" ;
	private String strEmpanelNbr = "";
	private String strOfficeName = "";
	private String strOfficeCode = "";
	private String strGroupID = "";
	private String strGroupName = "";
	private Integer intOfficeSeqID = null;
	private String strContactType ="";
	private String strFingerPrint="";
	private byte[] strFingerPrintFile;
	
	
	public String getContactType() {
		return strContactType;
	}

	public void setContactType(String contactType) {
		this.strContactType = contactType;
	}
	
	
	public String getFingerPrint() {
		return strFingerPrint;
	}

	public void setFingerPrint(String strFingerPrint) {
		this.strFingerPrint = strFingerPrint;
	}

	
	public byte[] getFingerPrintFile() {
		return strFingerPrintFile;
	}

	public void setFingerPrintFile(byte[] strFingerPrintFile) {
		this.strFingerPrintFile = strFingerPrintFile;
	}
	/** This method returns the Access Yes or No
	 * @return Returns the strAccessYN.
	 */
	public String getAccessYN() {
		return strAccessYN;
	}// End of getAccessYN()
	
	/** This method sets the Access Yes or NO
	 * @param strAccessYN The strAccessYN to set.
	 */
	public void setAccessYN(String strAccessYN) {
		this.strAccessYN = strAccessYN;
	}// End of setAccessYN(String strAccessYN)
	
	/** This method returns the Role Sequence ID
	 * @return Returns the lngRoleID.
	 */
	public Long getRoleID() {
		return lngRoleID;
	}// End of getRoleID()
	
	/** This method sets the Role Sequence ID
	 * @param strRoleID The lngRoleID to set.
	 */
	public void setRoleID(Long lngRoleID) {
		this.lngRoleID = lngRoleID;
	}// setRoleID(Long lngRoleID)
	
	/** This method returns the User ID
	 * @return Returns the strUserID.
	 */
	public String getUserID() {
		return strUserID;
	}// End of getUserID()
	
	/** This method sets the User ID 
	 * @param strUserID The strUserID to set.
	 */
	public void setUserID(String strUserID) {
		this.strUserID = strUserID;
	}// End of setUserID(String strUserID)
	
	/** This method returns the User Type
	 * @return Returns the strUserType.
	 */
	public String getUserType() {
		return strUserType;
	}// End of getUserType()
	
	/** This method sets the User Type
	 * @param strUserType The strUserType to set.
	 */
	public void setUserType(String strUserType) {
		this.strUserType = strUserType;
	}// End of setUserType(String strUserType) 
	
	/** This method returns the Empanel Number
	 * @return Returns the strEmpanelNbr.
	 */
	public String getEmpanelNbr() {
		return strEmpanelNbr;
	}// end of getEmpanelNbr()
	
	/** This method sets the Empanel Number
	 * @param strEmpanelNbr The strEmpanelNbr to set.
	 */
	public void setEmpanelNbr(String strEmpanelNbr) {
		this.strEmpanelNbr = strEmpanelNbr;
	}//End of setEmpanelNbr(String strEmpanelNbr)
	
	/** This method returns the Office Name
	 * @return Returns the strOfficeName.
	 */
	public String getOfficeName() {
		return strOfficeName;
	}// End of getOfficeName()
	
	/** This method sets the Office Name
	 * @param strOfficeName The strOfficeName to set.
	 */
	public void setOfficeName(String strOfficeName) {
		this.strOfficeName = strOfficeName;
	}//End of setOfficeName(String strOfficeName)
	
	/** This method returns the Office Code
	 * @return Returns the strOfficeCode.
	 */
	public String getOfficeCode() {
		return strOfficeCode;
	}// End of getOfficeCode()
	
	/** This method sets the Office Code
	 * @param strOfficeCode The strOfficeCode to set.
	 */
	public void setOfficeCode(String strOfficeCode) {
		this.strOfficeCode = strOfficeCode;
	}// End of setOfficeCode(String strOfficeCode)

	/** This method returns the Group ID
	 * @return Returns the strGroupID.
	 */
	public String getGroupID() {
		return strGroupID;
	}// End of getGroupID()

	/** This method sets the Group ID
	 * @param strGroupID The strGroupID to set.
	 */
	public void setGroupID(String strGroupID) {
		this.strGroupID = strGroupID;
	}// End of setGroupID(String strGroupID)

	/** This method returns the Group Name
	 * @return Returns the strGroupName.
	 */
	public String getGroupName() {
		return strGroupName;
	}// End of getGroupName()

	/** This method sets the Group Name
	 * @param strGroupName The strGroupName to set.
	 */
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}// End of setGroupName(String strGroupName)
	
	
	//kocb
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
	
}// End of UserAccessVO
