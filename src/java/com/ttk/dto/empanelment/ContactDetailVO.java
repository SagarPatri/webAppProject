/**
 * @ (#) ContactDetailVO.java Sep 19, 2005
 * Project       : TTK HealthCare Services
 * File          : ContactDetailVO.java
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

import java.util.ArrayList;

/**
 * This VO contains all details of a  contact for add/edit
 */
public class ContactDetailVO extends ContactVO{
	
//	String fields declared private
	private String strPrimaryEmailId="";
	private String strSecondaryEmailId="";
	private String strDesgTypeId="";
	private String strDrRegistNmbr="";
	private String strDrQualification="";
	private String strSpecTypeId="";
	private String strResidentDrYn="";
	private ArrayList alContactPhones=null;/* This is an ArrayList of DTOContactPhone objects. */
	private String strPhoneDetails;
	private String strDeletePhoneId = "";
	private String strPrefixId = "";
	private String strUserAccessYn = "";
	private Long lRoleSeqId = null;
	private String strUserId = "";
	private String strUserGeneralId = "";
	private Long lHospSeqId = null;
	private Long lInsSeqId = null;
	private Long lTpaOffSeqId = null;
	private Long lGroupSeqId = null;
	private Long lEmployeeNO = null;
	private String strDeptId = "";
	private String strRoleName = "";
	private String strHospActiveYn = "";
	private Long lEmpanelSeqId = null;
	private String strOfficeCode = "";
	private String strOfficeName = "";
	private String strAbbrevationCode = "";
	private String strInsCompName = "";
	private String strHospName = ""; 
    private Long lEmpanelNumber = null;
    private Long lGrpRegSeqId = null;
    
    /*
     * Sets the  phone details
     * @param strPhoneDetails String[] contact phone
     */
    public void setPhoneDetails(String strPhoneDetails)
    {
        this.strPhoneDetails=strPhoneDetails;
    }//end of setPhoneDetails(String[] strPhoneDetails)
        
    /*
     * Retrieve the  phone details
     * @return strPhoneDetails String[] 
     */
    public String getPhoneDetails()
    {
        return strPhoneDetails;
    }//end of getPhoneDetails()
	
	/*
	 * Sets the primary email id
	 * @param strPrimaryEmailId String primary email id
	 */
	public void setPrimaryEmailId(String strPrimaryEmailId)
	{
		this.strPrimaryEmailId=strPrimaryEmailId;
	}//end of setPrimaryEmailId(String strPrimaryEmailId)
	
	/*
	 * Retrieve the primary email id
	 * @return strPrimaryEmailId String primary email id
	 */
	public String getPrimaryEmailId()
	{
		return strPrimaryEmailId;
	}//end of getPrimaryEmailId()
		
	/*
	 * Sets the secondary emailid
	 * @param strSecondaryEmailId String secondary emailid
	 */
	public void setSecondaryEmailId(String strSecondaryEmailId)
	{
		this.strSecondaryEmailId=strSecondaryEmailId;
	}//end of setSecondaryEmailId(String strSecondaryEmailId)
	
	/*
	 * Retrieve the secondary email id
	 * @return strSecondaryEmailId String secondary email id
	 * 
	 */
	public String getSecondaryEmailId()
	{
		return strSecondaryEmailId;	
	}//end of getSecondaryEmailId()
			
	/*
	 * Sets the doctor qualification
	 * @param strDrQulification String doctor qulification 
	 */
	public void setDrQualification(String strDrQualification)
	{
		this.strDrQualification=strDrQualification;
	}// end of setDrQulification(String strDrQulification)
	
	/*
	 *Retrieve the doctor qualification
	 *@return  strDrQulification String doctor qualification
	 */
	public String getDrQualification()
	{
		return strDrQualification;
	}//end of getDrQulification()
	
	/*
	 * Sets the doctor registration number
	 * @param strDrRegistNmbr String doctor registration number
	 */
	public void setDrRegistNmbr(String strDrRegistNmbr)
	{
		this.strDrRegistNmbr=strDrRegistNmbr;
	}//end of setDrRegistNmbr(String strDrRegistNmbr)
	
	/*
	 * Retrieve the doctor registration number
	 * @return strDrRegistNmbr String doctor registration number
	 */
	public String getDrRegistNmbr()
	{
		return strDrRegistNmbr;
	}//end of getDrRegistNmbr()
	
	/*
	 * Sets the resident doctor y/n
	 * @param strResidentDrYn String resident doctor y/n 
	 */
	public void setResidentDrYn(String strResidentDrYn)
	{
		this.strResidentDrYn=strResidentDrYn;
	}//end of setResidentDrYn(String strResidentDrYn)
	
	/*
	 * Retrieve the resident doctor y/n
	 * @return strResidentDrYn String resident doctor y/n
	 */
	public String getResidentDrYn()
	{
		return strResidentDrYn;
	}//end of getResidentDrYn()
	
	/*
	 * Sets the designation type id
	 * @param strDesgTypeId String designation type id
	 */
	public void setDesgTypeId(String strDesgTypeId)
	{
		this.strDesgTypeId=strDesgTypeId;
	}//end of setDesgTypeId(String strDesgTypeId)
	
	/*
	 * Retrieve the designation type id
	 * @return strDesgTypeId String designation type id  
	 */
	public String getDesgTypeId()
	{
		return strDesgTypeId;
	}//end of getDesgTypeId()
	
	/*
	 * Sets the specialization type id
	 * @param strSpecTypeId String specialization type id
	 */
	public void setSpecTypeId(String strSpecTypeId)
	{
		this.strSpecTypeId=strSpecTypeId;
	}//end of setSpecTypeId(String strSpecTypeId)
	
	/*
	 * Retrieve the specialization type id
	 * @return strSpecTypeId String specialization type id 
	 */
	public String getSpecTypeId()
	{
		return strSpecTypeId;
	}//end of getSpecTypeId()		
	
	/*
	 * Sets the contact phones
	 * @param alContactPhones ArrayList contact phones
	 */
	public void setContactPhonesVO(ArrayList alContactPhones)
	{
		this.alContactPhones=alContactPhones;
	}//end of setContactPhonesVO(ArrayList alContactPhones)
	
	/*
	 * Retrieve the contact phones
	 * @return alContactPhones ArrayList contact phones
	 */
	public final ArrayList getContactPhonesVO()
	{
		return alContactPhones;
	}//end of getContactPhonesVO()

	/*
	 * Sets the Delete contact phone Id
	 * @param strDeletePhoneId String delete phone id
	 */
	public void setDeletePhoneId(String strDeletePhoneId)
	{
		this.strDeletePhoneId=strDeletePhoneId;
	}//end of setDeletePhoneId(String strDeletePhoneId)
		
	/*
	 * Retrieve the Delete phone id
	 * @return strDeletePhoneId String delete phone id
	 */
	public String getDeletePhoneId()
	{
		return strDeletePhoneId;
	}//end of 	getDeletePhoneId()

	/** This method returns the Prefix for the Contact person
	 * @return Returns the strPrefixId.
	 */
	public String getPrefixId() {
		return strPrefixId;
	}// End of getPrefixId()

	/** This method sets the Prefix for the Contact Person
	 * @param strPrefixGeneralTypeId The strPrefixId to set.
	 */
	public void setPrefixId(String strPrefixId) {
		this.strPrefixId = strPrefixId;
	}// End of setPrefixId(String strPrefixId)

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

	/** This method returns the User's ID
	 * @return Returns the strUserId.
	 */
	public String getUserId() {
		return strUserId;
	}// End of getUserId()

	/** This method sets the User's  ID
	 * @param strUserGeneralTypeId The strUserId to set.
	 */
	public void setUserId(String strUserId) {
		this.strUserId = strUserId;
	}// End of setUserId(String strUserId)

	/** This method returns the User Gweneral Type ID
	 * @return Returns the strUserGeneralId.
	 */
	public String getUserGeneralId() {
		return strUserGeneralId;
	}// End of getUserGeneralId()

	/** This method sets the User General Type ID
	 * @param strUserGeneralId The strUserGeneralId to set.
	 */
	public void setUserGeneralId(String strUserGeneralId) {
		this.strUserGeneralId = strUserGeneralId;
	}// End of setUserGeneralId(String strUserGeneralId)
    
	/** This method returns the Hospital Sequence ID
	 * @return Returns the lHospSeqId.
	 */
	public Long getHospSeqId() {
		return lHospSeqId;
	}// End of getHospSeqId()

	/** This method sets the Hospital Sequence ID
	 * @param hospSeqId The lHospSeqId to set.
	 */
	public void setHospSeqId(Long hospSeqId) {
		lHospSeqId = hospSeqId;
	}// End of setHospSeqId(Long hospSeqId)

	/** This method returns the Insurance Sequence ID
	 * @return Returns the lInsSeqId.
	 */
	public Long getInsSeqId() {
		return lInsSeqId;
	}// End of getInsSeqId() 

	/** This method sets the Insurance Sequence ID
	 * @param insSeqId The lInsSeqId to set.
	 */
	public void setInsSeqId(Long insSeqId) {
		lInsSeqId = insSeqId;
	}//End of setInsSeqId(Long insSeqId)

	/** This method returns the TPA Office Sequence Id
	 * @return Returns the lTpaOffSeqId.
	 */
	public Long getTpaOffSeqId() {
		return lTpaOffSeqId;
	}// End of getTpaOffSeqId()

	/** This method sets the TPA Office Sequence Id
	 * @param tpaOffSeqId The lTpaOffSeqId to set.
	 */
	public void setTpaOffSeqId(Long tpaOffSeqId) {
		lTpaOffSeqId = tpaOffSeqId;
	}// End of setTpaOffSeqId(Long tpaOffSeqId) 
	
	/** This method returns the Group Sequence Id
	 * @return Returns the lGroupSeqId.
	 */
	public Long getGroupSeqId() {
		return lGroupSeqId;
	}// End of getGroupSeqId()

	/** This method sets the Group Sequence Id
	 * @param groupSeqId The lGroupSeqId to set.
	 */
	public void setGroupSeqId(Long groupSeqId) {
		lGroupSeqId = groupSeqId;
	}// End of setGroupSeqId(Long groupSeqId)

	/** This method returns the Employee Number
	 * @return Returns the lEmployeeNO.
	 */
	public Long getEmployeeNO() {
		return lEmployeeNO;
	}// End of getEmployeeNO()

	/** This method sets the Employee Number
	 * @param employeeNO The lEmployeeNO to set.
	 */
	public void setEmployeeNO(Long employeeNO) {
		lEmployeeNO = employeeNO;
	}// End of setEmployeeNO(Long employeeNO)

	/** This method returns the General Type ID
	 * @return Returns the strDeptId.
	 */
	public String getDeptId() {
		return strDeptId;
	}// End of getDeptId()

	/** This method sets the Department General Type ID
	 * @param strDeptId The strDeptId to set.
	 */
	public void setDeptId(String strDeptId) {
		this.strDeptId = strDeptId;
	}// End of setDeptId(String strDeptId)

	/** This method returns the Role name
	 * @return Returns the strRoleName.
	 */
	public String getRoleName() {
		return strRoleName;
	}//End of getRoleName()

	/** This method sets the Role Name
	 * @param strRoleName The strRoleName to set.
	 */
	public void setRoleName(String strRoleName) {
		this.strRoleName = strRoleName;
	}// End of setRoleName(String strRoleName)

	/** This method returns the Hospital Contact Active or Not
	 * @return Returns the strHospActiveYn.
	 */
	public String getHospActiveYn() {
		return strHospActiveYn;
	}//End of  getHospActiveYn()

	/** This method sets the Hospital Contact Active or Not
	 * @param strHospActiveYn The strHospActiveYn to set.
	 */
	public void setHospActiveYn(String strHospActiveYn) {
		this.strHospActiveYn = strHospActiveYn;
	} // End of setHospActiveYn(String strHospActiveYn)

	/** This method returns the Empanel Sequence ID
	 * @return Returns the lEmpanelSeqId.
	 */
	public Long getEmpanelSeqId() {
		return lEmpanelSeqId;
	}// End of getEmpanelSeqId()

	/** This method sets the Empanel Sequence ID
	 * @param empanelSeqId The lEmpanelSeqId to set.
	 */
	public void setEmpanelSeqId(Long empanelSeqId) {
		lEmpanelSeqId = empanelSeqId;
	}//End of setEmpanelSeqId(Long empanelSeqId)

	/** This method returns the Office Code
	 * @return Returns the strOfficeCode.
	 */
	public String getStrOfficeCode() {
		return strOfficeCode;
	}// End of getStrOfficeCode()

	/** This method sets the Office Code
	 * @param strOfficeCode The strOfficeCode to set.
	 */
	public void setOfficeCode(String strOfficeCode) {
		this.strOfficeCode = strOfficeCode;
	}// End of setOfficeCode(String strOfficeCode)

	/** This method returns the office name
	 * @return Returns the strOfficeName.
	 */
	public String getOfficeName() {
		return strOfficeName;
	}// End of getOfficeName()

	/** This method sets the Office name
	 * @param strOfficeName The strOfficeName to set.
	 */
	public void setOfficeName(String strOfficeName) {
		this.strOfficeName = strOfficeName;
	}// End of setOfficeName(String strOfficeName)

	/** This method returns the Abbrevation code
	 * @return Returns the strAbbrevationCode.
	 */
	public String getAbbrevationCode() {
		return strAbbrevationCode;
	}// End of getAbbrevationCode()

	/** This method sets the Abbrevation code
	 * @param strAbbrevationCode The strAbbrevationCode to set.
	 */
	public void setAbbrevationCode(String strAbbrevationCode) {
		this.strAbbrevationCode = strAbbrevationCode;
	}// End of setAbbrevationCode(String strAbbrevationCode) 

	/** This method returns the Insurance Company Name
	 * @return Returns the strInsCompName.
	 */
	public String getInsCompName() {
		return strInsCompName;
	}// End of  getInsCompName()

	/** This method sets the Insurance Company name 
	 * @param strInsCompName The strInsCompName to set.
	 */
	public void setInsCompName(String strInsCompName) {
		this.strInsCompName = strInsCompName;
	}// End of setInsCompName(String strInsCompName)

	/** This method returns the Hospital name
	 * @return Returns the strHospName.
	 */
	public String getHospName() {
		return strHospName;
	} // End of getHospName()

	/** This method sets the Hospital name
	 * @param strHospName The strHospName to set.
	 */
	public void setHospName(String strHospName) {
		this.strHospName = strHospName;
	}//End of setHospName(String strHospName)

	/** This method returns the Role Sequence ID
	 * @return Returns the lRoleSeqId.
	 */
	public Long getRoleSeqId() {
		return lRoleSeqId;
	}// End of getRoleSeqId()

	/** This method sets the Role Sequence ID
	 * @param roleSeqId The lRoleSeqId to set.
	 */
	public void setRoleSeqId(Long roleSeqId) {
		this.lRoleSeqId = roleSeqId;
	}// End of setRoleSeqId(Long roleSeqId)

	/** This method returns the Empanelment Number
	 * @return Returns the lEmpanelNumber.
	 */
	public Long getEmpanelNumber() {
		return lEmpanelNumber;
	}// End of getEmpanelNumber()

	/** This method sets the Empanelment Numebr
	 * @param empanelNumber The lEmpanelNumber to set.
	 */
	public void setEmpanelNumber(Long empanelNumber) {
		lEmpanelNumber = empanelNumber;
	}// End of setEmpanelNumber(Long empanelNumber)

	/** This method returns the Group Registration Sequence ID
	 * @return Returns the lGrpRegSeqId.
	 */
	public Long getGrpRegSeqId() {
		return lGrpRegSeqId;
	}// End of getGrpRegSeqId()

	/** This method sets the Group Registration Sequence ID
	 * @param grpRegSeqId The lGrpRegSeqId to set.
	 */
	public void setGrpRegSeqId(Long grpRegSeqId) {
		lGrpRegSeqId = grpRegSeqId;
	}// End of setGrpRegSeqId(Long grpRegSeqId)
	
}// End of ContactDetailVO
