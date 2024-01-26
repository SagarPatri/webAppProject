/**
 * @ (#) UserVO.java Jul 29, 2005
 * Project      :
 * File         : UserVO.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Jul 29, 2005
 *
 * @author       : Nagaraj D V
 * Modified by   : Arun K N
 * Modified date : Mar 25, 2006
 * Reason        : for removing the unwanted fields from the class
 */

package com.ttk.dto.usermanagement;

import com.ttk.dto.BaseVO;
//Changes Added for Password Policy CR KOC 1235
import java.net.InetAddress;
//End changes for Password Policy CR KOC 1235

/**
 * This class contains setters and getters for the User info through which
 * Users id and password are submitted to DAO for validation
 *
 */
public class UserVO extends BaseVO{

	private String strUserId="";
	private String strPassword="";
    private String strPolicyNo="";
    private String strEnrollmentID="";
    private String strGroupID="";
    private String strLoginType="";
    private String strGroupName = "";
    private String strCertificateNbr= "";
    private String strCustomerCode = "";
    private String brokerCode = "";
	//Changes Added for Password Policy CR KOC 1235
	private String HostIPAddress = null;
	private String grpOrInd;
    
    /**
	 * @param ipAddress the hostIPAddress to set
	 */
	public void setHostIPAddress(String ipAddress) {
		HostIPAddress = ipAddress;
	}

	/**
	 * @return the hostIPAddress
	 */
	public String getHostIPAddress() {
		return HostIPAddress;
	}
	//End changes for Password Policy CR KOC 1235
  
    /** Retrieve the CustomerCode
	 * @return Returns the strCustomerCode.
	 */
	public String getCustomerCode() {
		return strCustomerCode;
	}//end of getCustomerCode()

	/** Sets the CustomerCode
	 * @param strCustomerCode The strCustomerCode to set.
	 */
	public void setCustomerCode(String strCustomerCode) {
		this.strCustomerCode = strCustomerCode;
	}//end of setCustomerCode(String strCustomerCode)

	/**
     * Retrieve the Certificate Number
     * @return  strCertificateNbr String
     */
    public String getCertificateNbr() {
        return strCertificateNbr;
    }//end of getCertificateNbr()

    /**
     * Sets the Certificate Number
     * @param  strCertificateNbr String
     */
    public void setCertificateNbr(String strCertificateNbr) {
        this.strCertificateNbr = strCertificateNbr;
    }//end of setCertificateNbr(String strCertificateNbr)
    
    /** Retrieve the GroupName
	 * @return Returns the strGroupName.
	 */
	public String getGroupName() {
		return strGroupName;
	}//end of getGroupName()

	/** Sets the GroupName
	 * @param strGroupName The strGroupName to set.
	 */
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}//end of setGroupName(String strGroupName)

	/**
     * Retrieve the Enrollment ID
     * @return  strEnrollmentID String
     */
    public String getEnrollmentID() {
        return strEnrollmentID;
    }//end of getEnrollmentID()

    /**
     * Sets the Enrollment ID
     * @param  strEnrollmentID String
     */
    public void setEnrollmentID(String strEnrollmentID) {
        this.strEnrollmentID = strEnrollmentID;
    }//end of setEnrollmentID(String strEnrollmentID)

    /**
     * Retrieve the Group ID
     * @return  strGroupID String
     */
    public String getGroupID() {
        return strGroupID;
    }//end of getGroupID()

    /**
     * Sets the Group ID
     * @param  strGroupID String
     */
    public void setGroupID(String strGroupID) {
        this.strGroupID = strGroupID;
    }//end of setGroupID(String strGroupID)

    /**
     * Retrieve the Login Type
     * @return  strLoginType String
     */
    public String getLoginType() {
        return strLoginType;
    }//end of getLoginType()

    /**
     * Sets the Login Type
     * @param  strLoginType String
     */
    public void setLoginType(String strLoginType) {
        this.strLoginType = strLoginType;
    }//end of setLoginType(String strLoginType)

    /**
     * Retrieve the Policy No
     * @return  strPolicyNo String
     */
    public String getPolicyNo() {
        return strPolicyNo;
    }//end of getPolicyNo()

    /**
     * Sets the Policy No
     * @param  strPolicyNo String
     */
    public void setPolicyNo(String strPolicyNo) {
        this.strPolicyNo = strPolicyNo;
    }//end of setPolicyNo(String strPolicyNo)

    /**
	 * Store the user id
	 * @param strUserId String the user id
	 */
	public void setUSER_ID(String strUserId)
	{
		this.strUserId = strUserId;
	}//end of setUSER_ID(String strUserId)

	/**
	 * Retrieve the user id
	 * @return strUserId String the user id
	 */
	public String getUSER_ID()
	{
		return strUserId;
	}//end of getUSER_ID()

	/**
	 * Sets the password
	 * @param strPassword String the password
	 */
	public void setPassword(String strPassword)
	{
		this.strPassword = strPassword;
	}//end of setPassword(String strPassword)

	/**
	 * Retrieve the password
	 * @return strPassword String the password
	 */
	public String getPassword()
	{
		return strPassword;
	}//end of getPassword()

	public String getBrokerCode() {
		return brokerCode;
	}
	public String getGrpOrInd() {
		return grpOrInd;
	}

	public void setBrokerCode(String brokerCode) {
		this.brokerCode = brokerCode;
	}


	public void setGrpOrInd(String grpOrInd) {
		this.grpOrInd = grpOrInd;
	}


}//end of UserVO.java
