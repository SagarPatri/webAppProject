/**
 * @ (#) ContactVO.java Sep 19, 2005
 * Project       : TTK HealthCare Services
 * File          : ContactVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 19, 2005
 *
 * @author       : Srikanth H M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dto.empanelment; 

import java.util.Date;

import com.ttk.common.TTKCommon;
import com.ttk.dto.BaseVO;

/**
 * This VO contains all details of contact 
 */
public class ContactVO  extends BaseVO{
	


	//	String fields declared private.	
	private String strContactTypeId="";
	private String strContactDesc=""; 
	private String strContactName="";
	private Long lContactSeqId = null;
	private Long lContactParentId = null;
	private String strImageName ="Blank";//ImageName
	private String strImageTitle="";//User
	private String strActiveYn = "";
	private String strUserAccessYn = "";
	private String strDesignation	=	"";
	private String strPhone	=	"";
	private String strEmail	=	"";
	
	
	//Variables for License Uploads...
	private String strLicenseNumb	=	null;
	private String strDoctorName	=	null;
	private String strAuthType		=	null;
	private Date	dtEffectiveFrom	=	null;
	private Date	dtEffectiveTo	=	null;
	
	//AK
	private String userRoleBRO	=	"";
	private String userID	=	"";
	private String primaryEmailID	=	"";
	private String officePhoneNO	=	"";
	private String status	=	"";
	private String brokerseqid = "";
	private String userAssoDisAsso = "";
	
	public String getUserRoleBRO() {
		return userRoleBRO;
	}

	public void setUserRoleBRO(String userRoleBRO) {
		this.userRoleBRO = userRoleBRO;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPrimaryEmailID() {
		return primaryEmailID;
	}

	public void setPrimaryEmailID(String primaryEmailID) {
		this.primaryEmailID = primaryEmailID;
	}

	public String getOfficePhoneNO() {
		return officePhoneNO;
	}

	public void setOfficePhoneNO(String officePhoneNO) {
		this.officePhoneNO = officePhoneNO;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLicenseNumb() {
		return strLicenseNumb;
	}

	public void setLicenseNumb(String strLicenseNumb) {
		this.strLicenseNumb = strLicenseNumb;
	}

	public String getDoctorName() {
		return strDoctorName;
	}

	public void setDoctorName(String strDoctorName) {
		this.strDoctorName = strDoctorName;
	}

	public String getAuthType() {
		return strAuthType;
	}

	public void setAuthType(String strAuthType) {
		this.strAuthType = strAuthType;
	}

	public String getEffectiveFrom() {
		return TTKCommon.getFormattedDate(dtEffectiveFrom);
	}

	public void setEffectiveFrom(Date dtEffectiveFrom) {
		this.dtEffectiveFrom = dtEffectiveFrom;
	}

	public String getEffectiveTo() {
		return TTKCommon.getFormattedDate(dtEffectiveTo);
	}

	public void setEffectiveTo(Date dtEffectiveTo) {
		this.dtEffectiveTo = dtEffectiveTo;
	}

	
	
	public String getPhone() {
		return strPhone;
	}

	public void setPhone(String strPhone) {
		this.strPhone = strPhone;
	}

	public String getEmail() {
		return strEmail;
	}

	public void setEmail(String strEmail) {
		this.strEmail = strEmail;
	}
	
	public String getDesignation() {
		return strDesignation;
	}

	public void setDesignation(String strDesignation) {
		this.strDesignation = strDesignation;
	}
	
    /*
	 * Sets the contact type id
	 * @param strContactTypeId String contact type id
	 */
	public void setContactTypeId(String strContactTypeId)
	{
		this.strContactTypeId=strContactTypeId;
	}//end of setContactTypeId(String strContactTypeId)
	
	/*
	 * Retrieve the contact type id
	 * @return strContactTypeId String contact type id
	 */
	public String getContactTypeId()
	{
		return strContactTypeId;
	}//end of getContactTypeId()
	
	/*
	 * Sets the strContactDesc
	 * @param strContactDesc String strContactDesc	 
	 */
	public void setContactDesc(String strContactDesc)
	{
		this.strContactDesc=strContactDesc;
	}//end of setContactDesc(String strContactDesc)
	
	/*
	 * Retrieve the strContactDesc 
	 * @return strContactDesc String contact description
	 */
	public String getContactDesc()
	{
		return strContactDesc;
	}//end of getContactDesc()
	
	/*
	 * Sets the contact name
	 * @param strContactName String contact name
	 */
	public void setContactName(String strContactName)
	{
		this.strContactName=strContactName;
	}//end of setContactName(String strContactName)
	
	/*
	 * Retrieve the contact name
	 * @return strContactName String contact name
	 */
	public String getContactName()
	{
		return strContactName;
	}//end of getContactName()	
	
	/*
	 * Sets the  contact seq id
	 * @param lContactSeqId Long  contact seq id
	 */
	public void setContactSeqId(Long lContactSeqId)
	{
		this.lContactSeqId=lContactSeqId;
	}//end of setContactSeqId(Long lContactSeqId)
	
	/*
	 * Retrieve the  contact seq id
	 * @return lContactSeqId Long  contact seq id
	 */
	public Long getContactSeqId()
	{
		return this.lContactSeqId;
	}//end of getContactSeqId()
	
	/** This method returns the lContactParentId
	 * @return Returns the lContactParentId.
	 */
	public Long getContactParentId() {
		return this.lContactParentId;
	}// End of getContactParentId()
	
	/** This method sets the lContactParentId
	 * @param contactParentId The lContactParentId to set.
	 */
	public void setContactParentId(Long contactParentId) {
		this.lContactParentId = contactParentId;
	}// End of setContactParentId(Long contactParentId)

	/** This method returns the Image Icon Name
	 * @return Returns the strImageName.
	 */
	public String getImageName() {
		return strImageName;
	}// End of getImageName()

	/** This method sets the Image Icon Name 
	 * @param strImageName The strImageName to set.
	 */
	public void setImageName(String strImageName) {
		this.strImageName = strImageName;
	}// End of setImageName(String strImageName)

	/** This method returns the Image Title
	 * @return Returns the strImageTitle.
	 */
	public String getImageTitle() {
		return strImageTitle;
	}// End of getImageTitle()

	/** This method sets the Image Title
	 * @param strImageTitle The strImageTitle to set.
	 */
	public void setImageTitle(String strImageTitle) {
		this.strImageTitle = strImageTitle;
	}// End of setImageTitle(String strImageTitle)
	
	/** This method returns the Contact has been Active or Not
	 * @return Returns the strActiveYn.
	 */
	public String getActiveYn() {
		return strActiveYn;
	}// End of getActiveYn()

	/** This method sets the Contact has been Active or Not
	 * @param strActiveYn The strActiveYn to set.
	 */
	public void setActiveYn(String strActiveYn) {
		this.strActiveYn = strActiveYn;
	}// End of setActiveYn(String strActiveYn)
	
	/** This method returns the Contact Person has been provide user Access Yes Or No
	 * @return Returns the strUserAccessYn.
	 */
	public String getUserAccessYn() {
		return strUserAccessYn;
	}// End of getUserAccessYn()

	/** This method sets the Contact Person has been provide user Access Yes Or No
	 * @param strProvideUserAccessYn The strProvideUserAccessYn to set.
	 */
	public void setUserAccessYn(String strUserAccessYn) {
		this.strUserAccessYn = strUserAccessYn;
	}// End of setUserAccessYn(String strUserAccessYn)

	/**
	 * @return the brokerseqid
	 */
	public String getBrokerseqid() {
		return brokerseqid;
	}

	/**
	 * @param brokerseqid the brokerseqid to set
	 */
	public void setBrokerseqid(String brokerseqid) {
		this.brokerseqid = brokerseqid;
	}

	/**
	 * @return the userAssoDisAsso
	 */
	public String getUserAssoDisAsso() {
		return userAssoDisAsso;
	}

	/**
	 * @param userAssoDisAsso the userAssoDisAsso to set
	 */
	public void setUserAssoDisAsso(String userAssoDisAsso) {
		this.userAssoDisAsso = userAssoDisAsso;
	}

}// End of ContactVO