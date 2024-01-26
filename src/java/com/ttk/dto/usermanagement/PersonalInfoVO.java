/**
 * @ (#) PersonalInfoVO.java Jan 10, 2006
 * Project       : TTK HealthCare Services
 * File          : PersonalInfoVO.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Jan 10, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dto.usermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.ttk.dto.onlineforms.insuranceLogin.DependentVO;

public class PersonalInfoVO implements Serializable {
	

	private String strName = "";
	private String strPrefix = "";
	private String strActiveYN = "Y";
	private String strDesignationTypeID = "";
	private String strDesignation = "";
	private String strDesignationDesc = "";
	private String strPrimaryEmailID = "";
	private String strSecondaryEmailID = "";
	private String strPhoneNbr1 = "";
	private String strPhoneNbr2 = "";
	private String strResidencePhoneNbr = "";
	private String strMobileNbr = "";
	private String strFaxNbr = "";
	private String strUserID = "";
	private String strPreauthAppYN = "";//denial process
	private String strPreauthRejYN = "";//denial process
	private String strClaimsAppYN = "";//denial process
	private String strClaimsRejYN = "";//denial process
	private Date dtStartDate=null;
	private Date dtEndDate= null;
	private String strProfessionalId = "";
	private String strProfessionalAuthority = "";
	
	//intx
	private String strStdCode			= 	null;
	private String strIsdCode			= 	null;
	private	String fileName				=	null;
	private String strFileName			=	null;
	private String strGender			=	null;
	private String strAge				=	null;
	private String strPrimaryMail		=	null;
	private Long   LContactSeqId		=	null;
	private String strSpeciality		= 	null;
	private String dob					= 	null;
	private String maritalStatus		= 	null;
	private String loginType = null;
	
	
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getDesignation() {
		return strDesignation;
	}
	public void setDesignation(String strDesignation) {
		this.strDesignation = strDesignation;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	//intX
	public String getSpeciality() {
		return strSpeciality;
	}
	public void setSpeciality(String strSpeciality) {
		this.strSpeciality = strSpeciality;
	}
	public Long getContactSeqId() {
		return LContactSeqId;
	}
	public void setContactSeqId(Long lContactSeqId) {
		LContactSeqId = lContactSeqId;
	}
	
	public String getGender() {
		return strGender;
	}
	public void setGender(String strGender) {
		this.strGender = strGender;
	}
	public String getAge() {
		return strAge;
	}
	public void setAge(String strAge) {
		this.strAge = strAge;
	}
	
	
	public String getStrPrimaryMail() {
		return strPrimaryMail;
	}
	public void setStrPrimaryMail(String strPrimaryMail) {
		this.strPrimaryMail = strPrimaryMail;
	}
	
	private String strConsultTypeCode	=	null;
	private String strNationalityTypeCode	=	null;
	
	public String getConsultTypeCode() {
		return strConsultTypeCode;
	}
	public void setConsultTypeCode(String strConsultTypeCode) {
		this.strConsultTypeCode = strConsultTypeCode;
	}
	public String getNationalityTypeCode() {
		return strNationalityTypeCode;
	}
	public void setNationalityTypeCode(String strNationalityTypeCode) {
		this.strNationalityTypeCode = strNationalityTypeCode;
	}
	
	
	public String getFileName() {
		return strFileName;
	}
	public void setFileName(String strFileName) {
		this.strFileName = strFileName;
	}
	/**
	 * @param fileName the file to set
	 */
	public void setFile(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the file
	 */
	public String getFile() {
		return fileName;
	}
	public String getStdCode() {
		return strStdCode;
	}
	public void setStdCode(String strStdCode) {
		this.strStdCode = strStdCode;
	}

	public String getIsdCode() {
		return strIsdCode;
	}
	public void setIsdCode(String strIsdCode) {
		this.strIsdCode = strIsdCode;
	}
	
	public String getProfessionalId() {
		return strProfessionalId;
	}

	public void setProfessionalId(String strProfessionalId) {
		this.strProfessionalId = strProfessionalId;
	}

	public String getProfessionalAuthority() {
		return strProfessionalAuthority;
	}

	public void setProfessionalAuthority(String strProfessionalAuthority) {
		this.strProfessionalAuthority = strProfessionalAuthority;
	}
	
	public Date getStartDate() {
		return dtStartDate;
	}

	public void setStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}

	public Date getEndDate() {
		return dtEndDate;
	}

	public void setEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}
	
	//denial process
	public String getPreauthAppYN() {
		return strPreauthAppYN;
	}

	public void setPreauthAppYN(String strPreauthAppYN) {
		this.strPreauthAppYN = strPreauthAppYN;
	}

	public String getPreauthRejYN() {
		return strPreauthRejYN;
	}

	public void setPreauthRejYN(String strPreauthRejYN) {
		this.strPreauthRejYN = strPreauthRejYN;
	}

	public String getClaimsAppYN() {
		return strClaimsAppYN;
	}

	public void setClaimsAppYN(String strClaimsAppYN) {
		this.strClaimsAppYN = strClaimsAppYN;
	}

	public String getClaimsRejYN() {
		return strClaimsRejYN;
	}

	public void setClaimsRejYN(String strClaimsRejYN) {
		this.strClaimsRejYN = strClaimsRejYN;
	}
	//denial process
	
	//Changes Added for Password Policy CR KOC 1235
	private String accn_locked_YN = "N";
	
	/**
	 * @param accn_locked_YN the accn_locked_YN to set
	 */
	public void setAccn_locked_YN(String accn_locked_YN) {
		this.accn_locked_YN = accn_locked_YN;
	}

	/**
	 * @return the accn_locked_YN
	 */
	public String getAccn_locked_YN() {
		return accn_locked_YN;
	}
	//End changes for Password Policy CR KOC 1235

	/** Retrieve the Designation Description
	 * @return Returns the strDesignationDesc.
	 */
	public String getDesignationDesc() {
		return strDesignationDesc;
	}//end of getDesignationDesc()

	/** Sets the Designation Description
	 * @param strDesignationDesc The strDesignationDesc to set.
	 */
	public void setDesignationDesc(String strDesignationDesc) {
		this.strDesignationDesc = strDesignationDesc;
	}//end of setDesignationDesc(String strDesignationDesc)

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

	/** This method returns the Active Yes or No
	 * @return Returns the strActiveYN.
	 */
	public String getActiveYN() {
		return strActiveYN;
	}//End of  getActiveYN()
	
	/** This method sets the Active Yes or NO
	 * @param strActiveYN The strActiveYN to set.
	 */
	public void setActiveYN(String strActiveYN) {
		this.strActiveYN = strActiveYN;
	}// End of setActiveYN(String strActiveYN)
	
	/** This method returns the Designation Type ID 
	 * @return Returns the strDesignationTypeID.
	 */
	public String getDesignationTypeID() {
		return strDesignationTypeID;
	}// End of getDesignationTypeID()
	
	/** This method sets the Designation Type ID
	 * @param strDesignationTypeID The strDesignationTypeID to set.
	 */
	public void setDesignationTypeID(String strDesignationTypeID) {
		this.strDesignationTypeID = strDesignationTypeID;
	}// End of setDesignationTypeID(String strDesignationTypeID)
	
	/** This method returns the Fax Number
	 * @return Returns the strFaxNbr.
	 */
	public String getFaxNbr() {
		return strFaxNbr;
	}// End of getFaxNbr()
	
	/** This method sets the Fax Number
	 * @param strFaxNbr The strFaxNbr to set.
	 */
	public void setFaxNbr(String strFaxNbr) {
		this.strFaxNbr = strFaxNbr;
	}// End of setFaxNbr(String strFaxNbr)
	
	/** This method returns the Mobile Number
	 * @return Returns the strMobileNbr.
	 */
	public String getMobileNbr() {
		return strMobileNbr;
	}// End of getMobileNbr()
	
	/** This method sets the Mobile Number
	 * @param strMobileNbr The strMobileNbr to set.
	 */
	public void setMobileNbr(String strMobileNbr) {
		this.strMobileNbr = strMobileNbr;
	}// End of setMobileNbr(String strMobileNbr)
	
	/** This method returns the Name 
	 * @return Returns the strName.
	 */
	public String getName() {
		return strName;
	}// End of getName()
	
	/** This method sets the Name
	 * @param strName The strName to set.
	 */
	public void setName(String strName) {
		this.strName = strName;
	}// End of setName(String strName)
	
	/** This method returns the Phone Number 1
	 * @return Returns the strPhoneNbr1.
	 */
	public String getPhoneNbr1() {
		return strPhoneNbr1;
	}// End of getPhoneNbr1()
	
	/** This method sets the Phone Number 2
	 * @param strPhoneNbr1 The strPhoneNbr1 to set.
	 */
	public void setPhoneNbr1(String strPhoneNbr1) {
		this.strPhoneNbr1 = strPhoneNbr1;
	}// End of setPhoneNbr1(String strPhoneNbr1)
	
	/** This method returns the Phone Number 2
	 * @return Returns the strPhoneNbr2.
	 */
	public String getPhoneNbr2() {
		return strPhoneNbr2;
	}// End of getPhoneNbr2()
	
	/** This method sets the phone Number 2
	 * @param strPhoneNbr2 The strPhoneNbr2 to set.
	 */
	public void setPhoneNbr2(String strPhoneNbr2) {
		this.strPhoneNbr2 = strPhoneNbr2;
	}// End of setPhoneNbr2(String strPhoneNbr2)
	
	/** This method returns the Prefix
	 * @return Returns the strPrefix.
	 */
	public String getPrefix() {
		return strPrefix;
	}// End of getPrefix()
	
	/** This method sets the Prefix 
	 * @param strPrefix The strPrefix to set.
	 */
	public void setPrefix(String strPrefix) {
		this.strPrefix = strPrefix;
	}// End of setPrefix(String strPrefix)
	
	/** This method returns the Primary Email ID 
	 * @return Returns the strPrimaryEmailID.
	 */
	public String getPrimaryEmailID() {
		return strPrimaryEmailID;
	}// End of getPrimaryEmailID()
	
	/** This method sets the Primary Email ID 
	 * @param strPrimaryEmailID The strPrimaryEmailID to set.
	 */
	public void setPrimaryEmailID(String strPrimaryEmailID) {
		this.strPrimaryEmailID = strPrimaryEmailID;
	}// End of setPrimaryEmailID(String strPrimaryEmailID)
	
	/** This method returns the Residence Phone Number
	 * @return Returns the strResidencePhoneNbr.
	 */
	public String getResidencePhoneNbr() {
		return strResidencePhoneNbr;
	}// End of getResidencePhoneNbr()
	
	/** This method sets the Residence Phone Number
	 * @param strResidencePhoneNbr The strResidencePhoneNbr to set.
	 */
	public void setResidencePhoneNbr(String strResidencePhoneNbr) {
		this.strResidencePhoneNbr = strResidencePhoneNbr;
	}// End of setResidencePhoneNbr(String strResidencePhoneNbr)
	
	/** This method returns the Secondary Email ID
	 * @return Returns the strSecondaryEmailID.
	 */
	public String getSecondaryEmailID() {
		return strSecondaryEmailID;
	}// End of getSecondaryEmailID()
	
	/** This method sets the Secondary Email ID 
	 * @param strSecondaryEmailID The strSecondaryEmailID to set.
	 */
	public void setSecondaryEmailID(String strSecondaryEmailID) {
		this.strSecondaryEmailID = strSecondaryEmailID;
	}// End of setSecondaryEmailID(String strSecondaryEmailID)
	
}// End of PersonalInfoVO
