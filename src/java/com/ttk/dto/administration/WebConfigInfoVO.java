/**
 * @ (#) WebConfigInfoVO.java Dec 20,2007
 * Project 	     : TTK HealthCare Services
 * File          : WebConfigInfoVO.java
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Dec 20,2007
 *
 * @author       :  Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dto.administration;

import java.math.BigDecimal;

import com.ttk.dto.BaseVO;

public class WebConfigInfoVO extends BaseVO
{

	private Long lngConfigSeqID = null;
	private Long lngProdPolicySeqID = null;
	private Long lngPolicySeqID=null;
	private String strAllowOnlineYN = "";
	private Integer intModTimeFrame = null;
	private String strEmpAddGeneralTypeID="";
	private String strGroupCntGeneralTypeID="";
	private String strPolicySubType = "";
	private BigDecimal bdPolicySumInsured=null;
	private String strOpdDomicillaryType="";
	private BigDecimal bdOpdSumInsured=null;
	private String strInceptionDateGenTypeID="";
	private String strAddSumInsuredGenTypeID="";
	private String strGroupCntCancelGenTypeID="";
	private String strSoftCopyGenTypeID="";
	private String strSendMailGenTypeID="";
	private String strMailGenTypeID="";
	private String strPwdGeneralTypeID = "";
	private Integer intWindowPeriod = null;
	private String strIntimationAccessTypeID = "";
	private String strNotiDetails = "";
	private Integer intReportFrom = null;
	private Integer intReportTo = null;
	private String strOnlineAssTypeID = "";
	private Integer intAlert=null;
	private Integer intPasswordValidity = null;
	private String strRatingGeneralTypeID="";
	private String strRelshipCombintnTypeID="";
	private Integer intLogindtWindowPerd= null;
	//Start Modification as per KOC 1159 (Aravind) Change request, added following fields (noOfMembers,intDelModTimeFrame)
    	private Integer  noOfMembers=null;
	private Integer intDelModTimeFrame = null;
//start Added as per 1257 11PP
	private Integer intWrongAttempts = null;
	//added for koc1349
	private String strWellnessAssTypeID = "";
	private String  noOf="";// koc note change
	
	public String getNoOf() {
		return noOf;
	}

	public void setNoOf(String noOf) {
		this.noOf = noOf;
	}
	/**
	 * @param wrongAttempts the wrongAttempts to set
	 */
	public void setWrongAttempts(Integer wrongAttempts) {
		this.intWrongAttempts = wrongAttempts;
	}

	/**
	 * @return the wrongAttempts
	 */
	public Integer getWrongAttempts() {
		return intWrongAttempts;
	}

	//end Added as per 1257 11PP

	public void setIntDelModTimeFrame(Integer intDelModTimeFrame) {
		this.intDelModTimeFrame = intDelModTimeFrame;
	}

	public Integer getIntDelModTimeFrame() {
		return intDelModTimeFrame;
	}

	public void setNoOfMembers(Integer noOfMembers) {
		this.noOfMembers = noOfMembers;
	}

	public Integer getNoOfMembers() {
		return noOfMembers;
	}
	//END  Modification as per KOC 1159 (Aravind) Change request

	//Added for KOC-1216
	private String strIbmPolPremium="";

	public String getIbmPolPremium()
	{
	  		return strIbmPolPremium;
	}
	public void setIbmPolPremium(String strIbmPolPremium)
	{
	   		this.strIbmPolPremium = strIbmPolPremium;
	}
		//Added for IBM.....27
	private String stroptGenTypeID = ""; //added by Praveen for webogin configuration
		//Ended

	/** Retrieve the logindtWindowPerd
	 * @return the intlogindtWindowPerd
	 */
	public Integer getLogindtWindowPerd() {
		return intLogindtWindowPerd;
	}//end of getLogindtWindowPerd()

	/** Sets the logindtWindowPerd
	 * @param intlogindtWindowPerd the intlogindtWindowPerd to set
	 */
	public void setLogindtWindowPerd(Integer intLogindtWindowPerd) {
		this.intLogindtWindowPerd = intLogindtWindowPerd;
	}//end of setLogindtWindowPerd(Integer intLogindtWindowPerd)

	/** Retrieve the StrRelshipCombintnTypeID
	 * @return the strRelshipCombintnTypeID
	 */
	public String getRelshipCombintnTypeID() {
		return strRelshipCombintnTypeID;
	}//end of getRelshipCombintnTypeID()

	/** Sets the StrRelshipCombintnTypeID
	 * @param strRelshipCombintnTypeID the strRelshipCombintnTypeID to set
	 */
	public void setRelshipCombintnTypeID(String strRelshipCombintnTypeID) {
		this.strRelshipCombintnTypeID = strRelshipCombintnTypeID;
	}//end of setRelshipCombintnTypeID(String strRelshipCombintnTypeID)

	/** Retrieve the RatingGeneralTypeID
	 * @return Returns the strRatingGeneralTypeID.
	 */
	public String getRatingGeneralTypeID() {
		return strRatingGeneralTypeID;
	}//end of getRatingGeneralTypeID()

	/** Sets the RatingGeneralTypeID
	 * @param strRatingGeneralTypeID The strRatingGeneralTypeID to set.
	 */
	public void setRatingGeneralTypeID(String strRatingGeneralTypeID) {
		this.strRatingGeneralTypeID = strRatingGeneralTypeID;
	}//end of setRatingGeneralTypeID(String strRatingGeneralTypeID)

	/** Retrieve the Online Assistance TypeID
	 * @return Returns the strOnlineAssTypeID.
	 */
	public String getOnlineAssTypeID() {
		return strOnlineAssTypeID;
	}//end of getOnlineAssTypeID()

	/** Sets the Online Assistance TypeID
	 * @param strOnlineAssTypeID The strOnlineAssTypeID to set.
	 */
	public void setOnlineAssTypeID(String strOnlineAssTypeID) {
		this.strOnlineAssTypeID = strOnlineAssTypeID;
	}//end of setOnlineAssTypeID(String strOnlineAssTypeID)

	/** Retrieve the ReportFrom
	 * @return Returns the intReportFrom.
	 */
	public Integer getReportFrom() {
		return intReportFrom;
	}//end of getReportFrom()

	/** Sets the ReportFrom
	 * @param intReportFrom The intReportFrom to set.
	 */
	public void setReportFrom(Integer intReportFrom) {
		this.intReportFrom = intReportFrom;
	}//end of setReportFrom(Integer intReportFrom)

	/** Retrieve the ReportTo
	 * @return Returns the intReportTo.
	 */
	public Integer getReportTo() {
		return intReportTo;
	}//end of getReportTo()

	/** Sets the ReportTo
	 * @param intReportTo The intReportTo to set.
	 */
	public void setReportTo(Integer intReportTo) {
		this.intReportTo = intReportTo;
	}//end of setReportTo(Integer intReportTo)

	/** Retrieve the IntimationAccessTypeID
	 * @return Returns the strIntimationAccessTypeID.
	 */
	public String getIntimationAccessTypeID() {
		return strIntimationAccessTypeID;
	}//end of getIntimationAccessTypeID()

	/** Sets the IntimationAccessTypeID
	 * @param strIntimationAccessTypeID The strIntimationAccessTypeID to set.
	 */
	public void setIntimationAccessTypeID(String strIntimationAccessTypeID) {
		this.strIntimationAccessTypeID = strIntimationAccessTypeID;
	}//end of setIntimationAccessTypeID(String strIntimationAccessTypeID)

	/** Retrieve the WindowPeriod
	 * @return Returns the intWindowPeriod.
	 */
	public Integer getWindowPeriod() {
		return intWindowPeriod;
	}//end of getWindowPeriod()

	/** Sets the WindowPeriod
	 * @param intWindowPeriod The intWindowPeriod to set.
	 */
	public void setWindowPeriod(Integer intWindowPeriod) {
		this.intWindowPeriod = intWindowPeriod;
	}//end of setWindowPeriod(Integer intWindowPeriod)

	/** Retrieve the PwdGeneralTypeID
	 * @return Returns the strPwdGeneralTypeID.
	 */
	public String getPwdGeneralTypeID() {
		return strPwdGeneralTypeID;
	}//end of getPwdGeneralTypeID()

	/** Sets the PwdGeneralTypeID
	 * @param strPwdGeneralTypeID The strPwdGeneralTypeID to set.
	 */
	public void setPwdGeneralTypeID(String strPwdGeneralTypeID) {
		this.strPwdGeneralTypeID = strPwdGeneralTypeID;
	}//end of setPwdGeneralTypeID(String strPwdGeneralTypeID)

	/** Retrieve the PolicySeqID
	 * @return Returns the lngPolicySeqID.
	 */
	public Long getPolicySeqID() {
		return lngPolicySeqID;
	}

	/** Sets the PolicySeqID
	 * @param lngPolicySeqID The lngPolicySeqID to set.
	 */
	public void setPolicySeqID(Long lngPolicySeqID) {
		this.lngPolicySeqID = lngPolicySeqID;
	}

	/** Retrieve the GroupCntCancelGenTypeID
	 * @return Returns the strGroupCntCancelGenTypeID.
	 */
	public String getGroupCntCancelGenTypeID() {
		return strGroupCntCancelGenTypeID;
	}

	/** Sets the GroupCntCancelGenTypeID
	 * @param strGroupCntCancelGenTypeID The strGroupCntCancelGenTypeID to set.
	 */
	public void setGroupCntCancelGenTypeID(String strGroupCntCancelGenTypeID) {
		this.strGroupCntCancelGenTypeID = strGroupCntCancelGenTypeID;
	}

	/** Retrieve the OpdSumInsured
	 * @return Returns the bdOpdSumInsured.
	 */
	public BigDecimal getOpdSumInsured() {
		return bdOpdSumInsured;
	}

	/** Sets the OpdSumInsured
	 * @param bdOpdSumInsured The bdOpdSumInsured to set.
	 */
	public void setOpdSumInsured(BigDecimal bdOpdSumInsured) {
		this.bdOpdSumInsured = bdOpdSumInsured;
	}

	/** Retrieve the AddSumInsuredGenTypeID
	 * @return Returns the strAddSumInsuredGenTypeID.
	 */
	public String getAddSumInsuredGenTypeID() {
		return strAddSumInsuredGenTypeID;
	}

	/** Sets the AddSumInsuredGenTypeID
	 * @param strAddSumInsuredGenTypeID The strAddSumInsuredGenTypeID to set.
	 */
	public void setAddSumInsuredGenTypeID(String strAddSumInsuredGenTypeID) {
		this.strAddSumInsuredGenTypeID = strAddSumInsuredGenTypeID;
	}

	/** Retrieve the InceptionDateGenTypeID
	 * @return Returns the strInceptionDateGenTypeID.
	 */
	public String getInceptionDateGenTypeID() {
		return strInceptionDateGenTypeID;
	}

	/** Sets the InceptionDateGenTypeID
	 * @param strInceptionDateGenTypeID The strInceptionDateGenTypeID to set.
	 */
	public void setInceptionDateGenTypeID(String strInceptionDateGenTypeID) {
		this.strInceptionDateGenTypeID = strInceptionDateGenTypeID;
	}

	/** Retrieve the SoftCopyGenTypeID
	 * @return Returns the strSoftCopyGenTypeID.
	 */
	public String getSoftCopyGenTypeID() {
		return strSoftCopyGenTypeID;
	}

	/** Sets the SoftCopyGenTypeID
	 * @param strSoftCopyGenTypeID The strSoftCopyGenTypeID to set.
	 */
	public void setSoftCopyGenTypeID(String strSoftCopyGenTypeID) {
		this.strSoftCopyGenTypeID = strSoftCopyGenTypeID;
	}

	/** Retrieve the OpdDomicillaryType
	 * @return Returns the strOpdDomicillaryType.
	 */
	public String getOpdDomicillaryType() {
		return strOpdDomicillaryType;
	}

	/** Sets the OpdDomicillaryType
	 * @param strOpdDomicillaryType The strOpdDomicillaryType to set.
	 */
	public void setOpdDomicillaryType(String strOpdDomicillaryType) {
		this.strOpdDomicillaryType = strOpdDomicillaryType;
	}

	/** Retrieve the PolicySumInsured
	 * @return Returns the bdPolicySumInsured.
	 */
	public BigDecimal getPolicySumInsured() {
		return bdPolicySumInsured;
	}//end of getPolicySumInsured()

	/** Sets the PolicySumInsured
	 * @param bdPolicySumInsured The bdPolicySumInsured to set.
	 */
	public void setPolicySumInsured(BigDecimal bdPolicySumInsured) {
		this.bdPolicySumInsured = bdPolicySumInsured;
	}//end of setPolicySumInsured()

	/** Retrieve the ConfigSeqID
	 * @return Returns the lngConfigSeqID.
	 */
	public Long getConfigSeqID() {
		return lngConfigSeqID;
	}//end of getConfigSeqID()

	/** Sets the ConfigSeqID
	 * @param lngConfigSeqID The lngConfigSeqID to set.
	 */
	public void setConfigSeqID(Long lngConfigSeqID) {
		this.lngConfigSeqID = lngConfigSeqID;
	}//end of setConfigSeqID(Long lngConfigSeqID)

	/** Retrieve the ProdPolicySeqID
	 * @return Returns the lngProdPolicySeqID.
	 */
	public Long getProdPolicySeqID() {
		return lngProdPolicySeqID;
	}//end of getProdPolicySeqID()

	/** Sets the ProdPolicySeqID
	 * @param lngProdPolicySeqID The lngProdPolicySeqID to set.
	 */
	public void setProdPolicySeqID(Long lngProdPolicySeqID) {
		this.lngProdPolicySeqID = lngProdPolicySeqID;
	}//end of setProdPolicySeqID(Long lngProdPolicySeqID)

	/** Retrive the Allow Online Enrollment YN
	 * @return the strAllowOnlineYN
	 */
	public String getAllowOnlineYN() {
		return strAllowOnlineYN;
	}//end of getAllowOnlineYN()

	/** Sets the Allow Online Enrollment YN
	 * @param strAllowOnlineYN the strAllowOnlineYN to set
	 */
	public void setAllowOnlineYN(String strAllowOnlineYN) {
		this.strAllowOnlineYN = strAllowOnlineYN;
	}//end of setAllowOnlineYN(String strAllowOnlineYN)

	/** Retrieve the Modification Timeframe
	 * @return Returns the intModTimeFrame.
	 */
	public Integer getModTimeFrame() {
		return intModTimeFrame;
	}//end of getModTimeFrame()

	/** Sets the Modification Timeframe
	 * @param intModTimeFrame The intModTimeFrame to set.
	 */
	public void setModTimeFrame(Integer intModTimeFrame) {
		this.intModTimeFrame = intModTimeFrame;
	}//end of setModTimeFrame(Integer intModTimeFrame)

	/** Retrieve the Employee Add General Type ID
	 * @return Returns the intModTimeFrame.
	 */
	public String getEmpAddGeneralTypeID() {
		return strEmpAddGeneralTypeID;
	}//end of getEmpAddGeneralTypeID()

	/** Sets the Employee Add General Type ID
	 * @param intModTimeFrame The intModTimeFrame to set.
	 */
	public void setEmpAddGeneralTypeID(String strEmpAddGeneralTypeID) {
		this.strEmpAddGeneralTypeID = strEmpAddGeneralTypeID;
	}//end of setEmpAddGeneralTypeID(String strEmpAddGeneralTypeID)

	/** Retrieve the Group Content General Type ID
	 * @return Returns the intModTimeFrame.
	 */
	public String getGroupCntGeneralTypeID() {
		return strGroupCntGeneralTypeID;
	}//end of getGroupCntGeneralTypeID()

	/** Sets the Group Content General Type ID
	 * @param intModTimeFrame The intModTimeFrame to set.
	 */
	public void setGroupCntGeneralTypeID(String strGroupCntGeneralTypeID) {
		this.strGroupCntGeneralTypeID = strGroupCntGeneralTypeID;
	}//end of setGroupCntGeneralTypeID(String strGroupCntGeneralTypeID)

	/** This method returns the Policy Sub Type
	 * @return Returns the strPolicySubType.
	 */
	public String getPolicySubType() {
		return strPolicySubType;
	}//End of getPolicySubType()

	/** This method sets the Policy Sub Tpe
	 * @param strPolicySubType The strPolicySubType to set.
	 */
	public void setPolicySubType(String strPolicySubType) {
		this.strPolicySubType = strPolicySubType;
	}//End of setPolicySubType(String strPolicySubType)

	/** Retrieve the the Send Mial General Type ID
	 * @return Returns the strSendMailGenTypeID.
	 */
	public String getSendMailGenTypeID() {
		return strSendMailGenTypeID;
	}//end of getSendMailGenTypeID()

	/** Sets the Send Mial General Type ID
	 * @param strSendMailGenTypeID The strSendMailGenTypeID to set.
	 */
	public void setSendMailGenTypeID(String strSendMailGenTypeID) {
		this.strSendMailGenTypeID = strSendMailGenTypeID;
	}//end of setSendMailGenTypeID(String strSendMailGenTypeID)

	/** Retrieve the Mail content General Type ID
	 * @return Returns the strMailGenTypeID.
	 */
	public String getMailGenTypeID() {
		return strMailGenTypeID;
	}//end of getMailGenTypeID()

	/** Sets the Mail content General Type ID
	 * @param strMailContentGenTypeID The strMailContentGenTypeID to set.
	 */
	public void setMailGenTypeID(String strMailGenTypeID) {
		this.strMailGenTypeID = strMailGenTypeID;
	}//end of setMailGenTypeID(String strMailGenTypeID)

	/** Retrieve the Notification Details
	 * @return Returns the strNotiDetails.
	 */
	public String getNotiDetails() {
		return strNotiDetails;
	}//end of getNotiDetails()

	/** Sets the Notification Details
	 * @param strNotiDetails The strNotiDetails to set.
	 */
	public void setNotiDetails(String strNotiDetails) {
		this.strNotiDetails = strNotiDetails;
	}//end of setNotiDetails(String strNotiDetails)

	/** Retrieve the Alert
	 * @return the intAlert
	 */
	public Integer getAlert() {
		return intAlert;
	}//end of getAlert()

	/** Sets the Alert
	 * @param intAlert the intAlert to set
	 */
	public void setAlert(Integer intAlert) {
		this.intAlert = intAlert;
	}//end of setAlert(Integer intAlert)

	/** Retrieve the PasswordValidity
	 * @return the intPasswordValidity
	 */
	public Integer getPasswordValidity() {
		return intPasswordValidity;
	}//end of getPasswordValidity()

	/** Sets the PasswordValidity
	 * @param intPasswordValidity the intPasswordValidity to set
	 */
	public void setPasswordValidity(Integer intPasswordValidity) {
		this.intPasswordValidity = intPasswordValidity;
	}//end of setPasswordValidity(Integer intPasswordValidity)

	//Added for IBM...koc-1216
		//Getter for weblogin configuration for optin/out

	public String getOptGenTypeID()
	{
		return stroptGenTypeID;
	}
	//Setter for weblogin configuration for optin/out
	public void setOptGenTypeID(String stroptGenTypeID)
	{
		this.stroptGenTypeID = stroptGenTypeID;
	}

	//Ended
	//added for koc 1349
	public String getWellnessAccessTypeID() {
		return strWellnessAssTypeID;
	}//end of getOnlineAssTypeID()

	/** Sets the Online Assistance TypeID
	 * @param strOnlineAssTypeID The strOnlineAssTypeID to set.
	 */
	public void setWellnessAccessTypeID(String strWellnessAssTypeID) {
		this.strWellnessAssTypeID = strWellnessAssTypeID;
	}//end of setOnlineAssTypeID(String strOnlineAssTypeID)
	//end added for koc 1349

}//end of WebConfigInfoVO
