/**
 * @ (#) NotifyDetailVO.java May 16, 2008
 * Project       : TTK HealthCare Services
 * File          : NotifyDetailVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : May 16, 2008
 * @author       : Balakrishna Erram
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.empanelment;

import com.ttk.dto.BaseVO;

public class NotifyDetailVO extends BaseVO{

	private Long lngGroupRegSeqID = null ;  
	private String strMsgID = null; 
	private String strMsgName = null;
	private String strEmailDesc =""; 
	private String strSMSDesc ="";
	private String strPrimaryMailID ="";
	private String strSecondaryMailID ="";
	private String strNotificationDesc ="";//notification description
	private String strMsgTitle ="";//message_title
	private Integer intConfigParam1 =null;//config_param_1
	private Integer intConfigParam2 =null;//config_param_2
	private Integer intConfigParam3 =null;//config_param_3
	private String strNotifCategory="";//notification_category
	private String strConfigAllowedYN = "";//param_config_allowed_yn
	private String strCustConfigAllowedYN = "";//customized_config_allowed_yn
	private String strSendEmailYN = "";//send_as_email_yn
	private String strSendSmsYN = "";//send-as_sms_yn
	private String strLevel2EmailID = "";//LEVEL2_EMAIL_LIST
	private String strLevel3EmailID = "";//LEVEL3_EMAIL_LIST
	private String strShowMultiLevelYN = "";//SHOW_MULTI_ESCAL_LEVEL_YN
	private String applicableInsCompany = "";
	private String applicableInsCompanyDesc = "";
	private String applicableInsCheckCode = "";
	private String applicableInsCheckName = "";
	
	/** Retrieve the ShowMultiLevelYN
	 * @return Returns the strShowMultiLevelYN.
	 */
	public String getShowMultiLevelYN() {
		return strShowMultiLevelYN;
	}//end of getShowMultiLevelYN()

	/** Sets the ShowMultiLevelYN
	 * @param strShowMultiLevelYN The strShowMultiLevelYN to set.
	 */
	public void setShowMultiLevelYN(String strShowMultiLevelYN) {
		this.strShowMultiLevelYN = strShowMultiLevelYN;
	}//end of setShowMultiLevelYN(String strShowMultiLevelYN)

	/** Retrieve the Level2EmailID
	 * @return Returns the strLevel2EmailID.
	 */
	public String getLevel2EmailID() {
		return strLevel2EmailID;
	}//end of getLevel2EmailID()

	/** Sets the Level2EmailID
	 * @param strLevel2EmailID The strLevel2EmailID to set.
	 */
	public void setLevel2EmailID(String strLevel2EmailID) {
		this.strLevel2EmailID = strLevel2EmailID;
	}//end of setLevel2EmailID(String strLevel2EmailID)

	/** Retrieve the Level3EmailID
	 * @return Returns the strLevel3EmailID.
	 */
	public String getLevel3EmailID() {
		return strLevel3EmailID;
	}//end of getLevel3EmailID()

	/** Sets the Level3EmailID
	 * @param strLevel3EmailID The strLevel3EmailID to set.
	 */
	public void setLevel3EmailID(String strLevel3EmailID) {
		this.strLevel3EmailID = strLevel3EmailID;
	}//end of setLevel3EmailID(String strLevel3EmailID)

	/** Retrieve the SendEmailYN
	 * @return Returns the strSendEmailYN.
	 */
	public String getSendEmailYN() {
		return strSendEmailYN;
	}//end of getSendEmailYN()

	/** Sets the SendEmailYN
	 * @param strSendEmailYN The strSendEmailYN to set.
	 */
	public void setSendEmailYN(String strSendEmailYN) {
		this.strSendEmailYN = strSendEmailYN;
	}//end of setSendEmailYN(String strSendEmailYN)

	/** Retrieve the SendSmsYN
	 * @return Returns the strSendSmsYN.
	 */
	public String getSendSmsYN() {
		return strSendSmsYN;
	}//end of getSendSmsYN()

	/** Sets the SendSmsYN
	 * @param strSendSmsYN The strSendSmsYN to set.
	 */
	public void setSendSmsYN(String strSendSmsYN) {
		this.strSendSmsYN = strSendSmsYN;
	}//end of setSendSmsYN(String strSendSmsYN)
	
	/** Retrieve the ConfigAllowedYN
	 * @return Returns the strConfigAllowedYN.
	 */
	public String getConfigAllowedYN() {
		return strConfigAllowedYN;
	}//end of getConfigAllowedYN()

	/** Sets the ConfigAllowedYN
	 * @param strConfigAllowedYN The strConfigAllowedYN to set.
	 */
	public void setConfigAllowedYN(String strConfigAllowedYN) {
		this.strConfigAllowedYN = strConfigAllowedYN;
	}//end of setConfigAllowedYN(String strConfigAllowedYN)

	/** Retrieve the CustConfigAllowedYN
	 * @return Returns the strCustConfigAllowedYN.
	 */
	public String getCustConfigAllowedYN() {
		return strCustConfigAllowedYN;
	}//end of getCustConfigAllowedYN()

	/** Sets the CustConfigAllowedYN
	 * @param strCustConfigAllowedYN The strCustConfigAllowedYN to set.
	 */
	public void setCustConfigAllowedYN(String strCustConfigAllowedYN) {
		this.strCustConfigAllowedYN = strCustConfigAllowedYN;
	}//end of setCustConfigAllowedYN(String strCustConfigAllowedYN)

	/** Retrieve the MsgName
	 * @return Returns the strMsgName.
	 */
	public String getMsgName() {
		return strMsgName;
	}//end of getMsgName()

	/** Sets the MsgName
	 * @param strMsgName The strMsgName to set.
	 */
	public void setMsgName(String strMsgName) {
		this.strMsgName = strMsgName;
	}//end of setMsgName(String strMsgName)

	/** Retrieve the Message ID
	 * @return Returns the strMsgID.
	 */
	public String getMsgID() {
		return strMsgID;
	}//end of getMsgID()

	/** Sets the Message ID
	 * @param strMsgID The strMsgID to set.
	 */
	public void setMsgID(String strMsgID) {
		this.strMsgID = strMsgID;
	}//end of setMsgID(String strMsgID)
	
	/** This method returns the Group Registration Sequence ID
	 * @return Returns the lngGroupRegSeqID.
	 */
	public Long getGroupRegSeqID() {
		return lngGroupRegSeqID;
	}// End of getGroupRegSeqID()
	
	/** This method sets the Group Registration Sequence ID
	 * @param lngGroupRegSeqID The lngGroupRegSeqID to set.
	 */
	public void setGroupRegSeqID(Long lngGroupRegSeqID) {
		this.lngGroupRegSeqID = lngGroupRegSeqID;
	}// End of setGroupRegSeqID(Long lngGroupRegSeqID)
	
	/** This method returns the Email Description 
	 * @return the strEmailDesc
	 */
	public String getEmailDesc() {
		return strEmailDesc;
	}//end of getEmailDesc

	/** This method sets the Email Description 
	 * @param strEmailDesc the strEmailDesc to set
	 */
	public void setEmailDesc(String strEmailDesc) {
		this.strEmailDesc = strEmailDesc;
	}//end of setEmailDesc(String strEmailDesc)

	/** This method returns the Primary Mail ID
	 * @return the strPrimaryMailID
	 */
	public String getPrimaryMailID() {
		return strPrimaryMailID;
	}//end of getPrimaryMailID()

	/** This method sets the Primary Mail ID
	 * @param strPrimaryMailID the strPrimaryMailID to set
	 */
	public void setPrimaryMailID(String strPrimaryMailID) {
		this.strPrimaryMailID = strPrimaryMailID;
	}//end of setPrimaryMailID(String strPrimaryMailID)

	/** This method returns the Secondary Mail ID
	 * @return the strSecondaryMailID
	 */
	public String getSecondaryMailID() {
		return strSecondaryMailID;
	}//end of getSecondaryMailID() 

	/** This method sets the Secondary Mail ID
	 * @param strSecondaryMailID the strSecondaryMailID to set
	 */
	public void setSecondaryMailID(String strSecondaryMailID) {
		this.strSecondaryMailID = strSecondaryMailID;
	}//end of setSecondaryMailID(String strSecondaryMailID) 

	/** This method returns the SMS Description
	 * @return the strSMSDesc
	 */
	public String getSMSDesc() {
		return strSMSDesc;
	}//end of getSMSDesc() 

	/** This method sets the SMS Description
	 * @param strSMSDesc the strSMSDesc to set
	 */
	public void setSMSDesc(String strSMSDesc) {
		this.strSMSDesc = strSMSDesc;
	}//end of setSMSDesc(String strSMSDesc)

	/** This method returns the Config Param 1
	 * @return Returns the intConfigParam1.
	 */
	public Integer getConfigParam1() {
		return intConfigParam1;
	}//end of getConfigParam1

	/** This method sets the Config Param 1
	 * @param intConfigParam1 The intConfigParam1 to set.
	 */
	public void setConfigParam1(Integer intConfigParam1) {
		this.intConfigParam1 = intConfigParam1;
	}//end of setConfigParam1(Integer intConfigParam1)

	/** This method returns the Config Param 2
	 * @return Returns the intConfigParam2.
	 */
	public Integer getConfigParam2() {
		return intConfigParam2;
	}//end of getConfigParam2()

	/** This method sets the Config Param 2
	 * @param intConfigParam2 The intConfigParam2 to set.
	 */
	public void setConfigParam2(Integer intConfigParam2) {
		this.intConfigParam2 = intConfigParam2;
	}//end of setConfigParam2(Integer intConfigParam2) 

	/** This method returns the Config Param 3
	 * @return Returns the intConfigParam3.
	 */
	public Integer getConfigParam3() {
		return intConfigParam3;
	}//end of getConfigParam3()

	/** This method sets the Config Param 3
	 * @param intConfigParam3 The intConfigParam3 to set.
	 */
	public void setConfigParam3(Integer intConfigParam3) {
		this.intConfigParam3 = intConfigParam3;
	}//end of setConfigParam3(Integer intConfigParam3)

	/** This method returns the Messge title
	 * @return Returns the strMsgTitle.
	 */
	public String getMsgTitle() {
		return strMsgTitle;
	}//end of getMsgTitle()

	/** This method sets the Messge title
	 * @param strMsgTitle The strMsgTitle to set.
	 */
	public void setMsgTitle(String strMsgTitle) {
		this.strMsgTitle = strMsgTitle;
	}//end of setMsgTitle(String strMsgTitle)

	/** This method returns the Notification Description
	 * @return Returns the strNotificationDesc.
	 */
	public String getNotificationDesc() {
		return strNotificationDesc;
	}//end of getNotificationDesc

	/** This method sets the Notification Description
	 * @param strNotificationDesc The strNotificationDesc to set.
	 */
	public void setNotificationDesc(String strNotificationDesc) {
		this.strNotificationDesc = strNotificationDesc;
	}//end of setNotificationDesc(String strNotificationDesc)

	/** This method returns the Notification Category
	 * @return Returns the strNotifCategory.
	 */
	public String getNotifCategory() {
		return strNotifCategory;
	}//end of getNotifCategory()

	/** This method sets the Notification Category
	 * @param strNotifCategory The strNotifCategory to set.
	 */
	public void setNotifCategory(String strNotifCategory) {
		this.strNotifCategory = strNotifCategory;
	}//end of setNotifCategory(String strNotifCategory)

	public String getApplicableInsCompany() {
		return applicableInsCompany;
	}

	public void setApplicableInsCompany(String applicableInsCompany) {
		this.applicableInsCompany = applicableInsCompany;
	}

	public String getApplicableInsCompanyDesc() {
		return applicableInsCompanyDesc;
	}

	public void setApplicableInsCompanyDesc(String applicableInsCompanyDesc) {
		this.applicableInsCompanyDesc = applicableInsCompanyDesc;
	}

	public String getApplicableInsCheckCode() {
		return applicableInsCheckCode;
	}

	public void setApplicableInsCheckCode(String applicableInsCheckCode) {
		this.applicableInsCheckCode = applicableInsCheckCode;
	}

	public String getApplicableInsCheckName() {
		return applicableInsCheckName;
	}

	public void setApplicableInsCheckName(String applicableInsCheckName) {
		this.applicableInsCheckName = applicableInsCheckName;
	}
	
}//end of NotifyDetailVO
