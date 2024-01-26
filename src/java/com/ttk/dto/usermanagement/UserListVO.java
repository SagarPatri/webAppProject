/**
 * @ (#) UserListVO.java Dec 27, 2005
 * Project 	     : TTK HealthCare Services
 * File          : UserListVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Dec 27, 2005
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.usermanagement;

import java.util.Date;

import com.ttk.dto.BaseVO;

public class UserListVO extends BaseVO{
    
    private Long lContactSeqID = null;
    private String strUserID = "";
    private String strUserName = "";//CONTACT_NAME
    private String strRoleName = "";
    private String strName = "";
    private String strCity = "";
    private String strEmpanelmentNo=""; //Empanel number
    private String strOfficeCode = "";
    private String strGroupID = "";
    private String strEmployeeNbr = "";
    private Long lGroupUserSeqID = null;//GROUP_USER_SEQ_ID
    private String strActive_Yn = "";
    private String strAccessYn = "";
    private Date LoginDate;//For Ins Login Date
    private String LoginTime;//For Ins Login Time
    /** This method returns the Contact Sequence Id
     * @return Returns the lContactSeqID.
     */
    public Long getContactSeqId() {
        return lContactSeqID;
    }//end of getContactSeqId()
    
    /** This method sets the Contact Sequence Id
     * @param lContactSeqID The lContactSeqID to set.
     */
    public void setContactSeqId(Long lContactSeqID) {
        this.lContactSeqID = lContactSeqID;
    }//end of setContactSeqId(Long lContactSeqID)
    
    /** This method returns the Empanel Number
     * @return Returns the strEmpanelmentNo.
     */
    public String getEmpanelmentNo() {
        return strEmpanelmentNo;
    }//end of getEmpanelmentNo()
    
    /** This method sets the Empanel Number
     * @param strEmpanelmentNo The strEmpanelmentNo to set.
     */
    public void setEmpanelmentNo(String strEmpanelmentNo) {
        this.strEmpanelmentNo = strEmpanelmentNo;
    }//end of setEmpanelmentNo(String strEmpanelmentNo)
    
    /** This method returns the Name 
     * @return Returns the strName.
     */
    public String getName() {
        return strName;
    }//end of getHospName()
    
    /** This method sets the Name
     * @param strName The strName to set.
     */
    public void setName(String strName) {
        this.strName = strName;
    }//end of setName(String strName)
    
    /** This method returns the Office Code
     * @return Returns the strOfficeCode.
     */
    public String getOfficeCode() {
        return strOfficeCode;
    }//end of getOfficeCode()
    
    /** This method sets the Office Code
     * @param strOfficeCode The strOfficeCode to set.
     */
    public void setOfficeCode(String strOfficeCode) {
        this.strOfficeCode = strOfficeCode;
    }//end of setOfficeCode(String strOfficeCode)
    
    /** This method returns the City Description
     * @return Returns the strCity.
     */
    public String getCity() {
        return strCity;
    }//end of getCity()
    
    /** This method sets the City Description
     * @param strCity The strCity to set.
     */
    public void setCity(String strCity) {
        this.strCity = strCity;
    }//end of setCity(String strCity) 
    
    /** This method returns the Group Id
     * @return Returns the strGroupID.
     */
    public String getGroupID() {
        return strGroupID;
    }//end of getGroupID()
    
    /** This method sets the Group Id
     * @param strGroupID The strGroupID to set.
     */
    public void setGroupID(String strGroupID) {
        this.strGroupID = strGroupID;
    }//end of setGroupID(String strGroupID)
    
    /** This method returns the user ID
     * @return Returns the strUserID.
     */
    public String getUserID() {
        return strUserID;
    }// End of getUserID()

    /** This method sets the user Id
     * @param strUserId The strUserID to set.
     */
    public void setUserID(String strUserID) {
        this.strUserID = strUserID;
    }// End of setUserID(String strUserID)

    /** This method returns the User Name
     * @return Returns the strUserName.
     */
    public String getUserName() {
        return strUserName;
    }//End of getUserName()

    /** This method sets the User Name
     * @param strUserName The strUserName to set.
     */
    public void setUserName(String strUserName) {
        this.strUserName = strUserName;
    }// End of setUserName(String strUserName)

    /** This method returns the ROLE NAME
     * @return Returns the strRoleName.
     */
    public String getRoleName() {
        return strRoleName;
    }// End of getRoleName()

    /** This method sets the ROLE NAME
     * @param strRoleName The strRoleName to set.
     */
    public void setRoleName(String strRoleName) {
        this.strRoleName = strRoleName;
    }// End of setRoleName(String strRoleName)

    /** This method returns the Employee Number
     * @return Returns the strEmployeeNbr.
     */
    public String getEmployeeNbr() {
        return strEmployeeNbr;
    }//end of getEmployeeNbr()
    
    /** This method sets the Employee Number
     * @param strEmployeeNbr The strEmployeeNbr to set.
     */
    public void setEmployeeNbr(String strEmployeeNbr) {
        this.strEmployeeNbr = strEmployeeNbr;
    }//end of setEmployeeNbr(String strEmployeeNbr)
    
    /** This method returns the Group User Sequence ID
     * @return Returns the lGroupUserSeqID.
     */
    public Long getGroupUserSeqID() {
        return lGroupUserSeqID;
    }//end of getGroupUserSeqID()
    
    /** This method sets the Group User Sequence ID
     * @param groupUserSeqID The lGroupUserSeqID to set.
     */
    public void setGroupUserSeqID(Long groupUserSeqID) {
        lGroupUserSeqID = groupUserSeqID;
    }//end of setGroupUserSeqID(Long groupUserSeqID)

	/** This method returns the User Active or Not
	 * @return Returns the strActive_Yn.
	 */
	public String getActive_Yn() {
		return strActive_Yn;
	}// End of getActive_Yn()

	/** This method sets the User Active or Not
	 * @param strActive_Yn The strActive_Yn to set.
	 */
	public void setActive_Yn(String strActive_Yn) {
		this.strActive_Yn = strActive_Yn;
	}// End of setActive_Yn(String strActive_Yn)

	/** This method returns the Provide user Access yes or no
	 * @return Returns the strAccessYn.
	 */
	public String getAccessYn() {
		return strAccessYn;
	}// End of getAccessYn()

	/** This method sets the Provide user Access yes or no
	 * @param strAccessYn The strAccessYn to set.
	 */
	public void setAccessYn(String strAccessYn) {
		this.strAccessYn = strAccessYn;
	}// End of setAccessYn(String strAccessYn)

	public Date getLoginDate() {
		return LoginDate;
	}

	public void setLoginDate(Date loginDate) {
		LoginDate = loginDate;
	}

	public String getLoginTime() {
		return LoginTime;
	}

	public void setLoginTime(String loginTime) {
		LoginTime = loginTime;
	}
	
}//end of UserListVO
