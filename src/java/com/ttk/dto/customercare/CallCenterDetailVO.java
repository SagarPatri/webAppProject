/**
 * @ (#) CallCenterDetailVO.java Aug 21, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CallCenterDetailVO.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Aug 21, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dto.customercare;

import java.util.ArrayList;
import java.util.Date;//shortfall phase1

import com.ttk.common.TTKCommon;//shortfall phase1
import com.ttk.dto.claims.ClaimIntimationVO;

public class CallCenterDetailVO extends CallCenterVO{
	

	private String strCallerTypeID = "";
	private String strCallerTypeDesc = "";
	private String strSourceTypeID = "";
	private String strSourceTypeDesc = "";
	private String strLogTypeID = "";
	private Long lngGroupRegnSeqID = null;
	private Long lngHospSeqID = null;
	private Long lngInsSeqID = null;
	private String strOfficePhoneNbr = "";
	private String strHomePhoneNbr = "";
	private String strMobileNumber = "";
	private String strEmailID = "";
	private String strCallContent = "";
	private String strReasonTypeID = "";
	private String strReasonTypeDesc = "";
	private String strSubReasonTypeID = "";
	private String strSubReasonDesc = "";
	private String strCategoryTypeID = "";
	private String strSubCategoryTypeID = "";
	private String strSubCategoryDesc = "";
	private String strFraudYN = "";
	private String strCallAnswerTypeID = "";
	private String strAnswerDesc = "";
	private String strCallRemarks = "";
	private String strLoggedByTypeID = "";
	private String strLoggedBy = "";
	private String strCallRelToTypeID = "";
	private String strCallRelToDesc = "";
	private String strLogClosedBy = "";
	private String strLogRecordTime = "";
	private String strLogRecordDay = "";
	private String strPriorityTypeID = "";
	private String strPriorityDesc = "";
	private String strStatusTypeID = "";
	private ClaimIntimationVO claimIntimationVO = null;
	private String strLinkIdentifier = ""; // Identifying Add/Edit Mode
	private Long lngContactSeqID = null;
	private String strUserTypeID = "";
	private ArrayList alCallCenterListVO = null;
	private String strChildCallContent = "";
	private String strChildMobileNbr = "";
	private String strInsCustCode =""; //ins_customer_code
    private String strCertificateNo =""; //certificate_no
    private String strInsScheme =""; //ins_scheme
    private String strCreditCardNbr = "";
    private String strCallType		= "";//intx
    private String strEscalationYN	= "";//intx
    
    public String getEscalationYN() {
		return strEscalationYN;
	}

	public void setEscalationYN(String strEscalationYN) {
		this.strEscalationYN = strEscalationYN;
	}
	
    public String getCallType() {
		return strCallType;
	}

	public void setCallType(String strCallType) {
		this.strCallType = strCallType;
	}

  //KOC FOR Grievance
    private String strGenderDescription="";
    private Integer intAge=null;
    
	private String strSeniorCitizenYN = "";
	private Date dtIntimationDate= null; //shortfall phase1
	
	
	//shortfall phase1
	public Date getIntimationDate() {
        return dtIntimationDate;
	}// End of getRecievedDate()
	
	
	public void setIntimationDate(Date dtIntimationDate) {
		this.dtIntimationDate = dtIntimationDate;
	}// End of setRecievedDate(Date dtRecievedDate)
	
	//shortfall phase1
	public void setSeniorCitizenYN(String strSeniorCitizenYN) {
		this.strSeniorCitizenYN = strSeniorCitizenYN;
	}

	public String getSeniorCitizenYN() {
		return strSeniorCitizenYN;
	}
	
    
    public void setAge(Integer intAge) {
		this.intAge = intAge;
	}

	public Integer getAge() {
		return intAge;
	}

	public void setGenderDescription(String strGenderDescription) {
		this.strGenderDescription = strGenderDescription;
	}

	public String getGenderDescription() {
		return strGenderDescription;
	}
	//KOC FOR Grievance
	/** Retrieve the CreditCardNbr
	 * @return Returns the strCreditCardNbr.
	 */
	public String getCreditCardNbr() {
		return strCreditCardNbr;
	}//end of getCreditCardNbr()

	/** Sets the CreditCardNbr
	 * @param strCreditCardNbr The strCreditCardNbr to set.
	 */
	public void setCreditCardNbr(String strCreditCardNbr) {
		this.strCreditCardNbr = strCreditCardNbr;
	}//end of setCreditCardNbr(String strCreditCardNbr)
    
    /** Retrive the CertificateNo
	 * @return Returns the strCertificateNo.
	 */
	public String getCertificateNo() {
		return strCertificateNo;
	}//end of getCertificateNo()

	/** Sets the CertificateNo
	 * @param strCertificateNo The strCertificateNo to set.
	 */
	public void setCertificateNo(String strCertificateNo) {
		this.strCertificateNo = strCertificateNo;
	}//end of setCertificateNo(String strCertificateNo)

	/** Retrive the InsCustCode
	 * @return Returns the strInsCustCode.
	 */
	public String getInsCustCode() {
		return strInsCustCode;
	}//end of getInsCustCode()

	/** Sets the InsCustCode
	 * @param strInsCustCode The strInsCustCode to set.
	 */
	public void setInsCustCode(String strInsCustCode) {
		this.strInsCustCode = strInsCustCode;
	}//end of setInsCustCode(String strInsCustCode)

	/** Retrive the InsScheme
	 * @return Returns the strInsScheme.
	 */
	public String getInsScheme() {
		return strInsScheme;
	}//end of getInsScheme()

	/** Sets the InsScheme
	 * @param strInsScheme The strInsScheme to set.
	 */
	public void setInsScheme(String strInsScheme) {
		this.strInsScheme = strInsScheme;
	}//end of setInsScheme(String strInsScheme)
	
	/** Retrieve the CallContent
	 * @return Returns the strChildCallContent.
	 */
	public String getChildCallContent() {
		return strChildCallContent;
	}//end of getChildCallContent()

	/** Sets the CallContent
	 * @param strChildCallContent The strChildCallContent to set.
	 */
	public void setChildCallContent(String strChildCallContent) {
		this.strChildCallContent = strChildCallContent;
	}//end of setChildCallContent(String strChildCallContent)

	/** Retrieve the MobileNbr
	 * @return Returns the strChildMobileNbr.
	 */
	public String getChildMobileNbr() {
		return strChildMobileNbr;
	}//end of getChildMobileNbr()

	/** Sets the MobileNbr
	 * @param strChildMobileNbr The strChildMobileNbr to set.
	 */
	public void setChildMobileNbr(String strChildMobileNbr) {
		this.strChildMobileNbr = strChildMobileNbr;
	}//end of setChildMobileNbr(String strChildMobileNbr)

	/** Retrieve the CallCenterListVO
	 * @return Returns the alCallCenterListVO.
	 */
	public ArrayList getCallCenterListVO() {
		return alCallCenterListVO;
	}//end of getCallCenterListVO()

	/** Sets the CallCenterListVO
	 * @param alCallCenterListVO The alCallCenterListVO to set.
	 */
	public void setCallCenterListVO(ArrayList alCallCenterListVO) {
		this.alCallCenterListVO = alCallCenterListVO;
	}//end of setCallCenterListVO(ArrayList alCallCenterListVO)

	/** Retrieve the UserTypeID
	 * @return Returns the strUserTypeID.
	 */
	public String getUserTypeID() {
		return strUserTypeID;
	}//end of getUserTypeID()

	/** Sets the UserTypeID
	 * @param strUserTypeID The strUserTypeID to set.
	 */
	public void setUserTypeID(String strUserTypeID) {
		this.strUserTypeID = strUserTypeID;
	}//end of setUserTypeID(String strUserTypeID)

	/** Retrieve the Answer Description
	 * @return Returns the strAnswerDesc.
	 */
	public String getAnswerDesc() {
		return strAnswerDesc;
	}//end of getAnswerDesc()

	/** Sets the Answer Description
	 * @param strAnswerDesc The strAnswerDesc to set.
	 */
	public void setAnswerDesc(String strAnswerDesc) {
		this.strAnswerDesc = strAnswerDesc;
	}//end of setAnswerDesc(String strAnswerDesc)

	/** Retrieve the Caller Type Description
	 * @return Returns the strCallerTypeDesc.
	 */
	public String getCallerTypeDesc() {
		return strCallerTypeDesc;
	}//end of getCallerTypeDesc()

	/** Sets the Caller Type Description
	 * @param strCallerTypeDesc The strCallerTypeDesc to set.
	 */
	public void setCallerTypeDesc(String strCallerTypeDesc) {
		this.strCallerTypeDesc = strCallerTypeDesc;
	}//end of setCallerTypeDesc(String strCallerTypeDesc)

	/** Retrieve the Priority Description
	 * @return Returns the strPriorityDesc.
	 */
	public String getPriorityDesc() {
		return strPriorityDesc;
	}//end of getPriorityDesc()

	/** Sets the Priority Description
	 * @param strPriorityDesc The strPriorityDesc to set.
	 */
	public void setPriorityDesc(String strPriorityDesc) {
		this.strPriorityDesc = strPriorityDesc;
	}//end of setPriorityDesc(String strPriorityDesc)

	/** Retrieve the Reason Type Description
	 * @return Returns the strReasonTypeDesc.
	 */
	public String getReasonTypeDesc() {
		return strReasonTypeDesc;
	}//end of getReasonTypeDesc()

	/** Sets the Reason Type Description
	 * @param strReasonTypeDesc The strReasonTypeDesc to set.
	 */
	public void setReasonTypeDesc(String strReasonTypeDesc) {
		this.strReasonTypeDesc = strReasonTypeDesc;
	}//end of setReasonTypeDesc(String strReasonTypeDesc)

	/** Retrieve the Source Type Description
	 * @return Returns the strSourceTypeDesc.
	 */
	public String getSourceTypeDesc() {
		return strSourceTypeDesc;
	}//end of getSourceTypeDesc()

	/** Sets the Source Type Description
	 * @param strSourceTypeDesc The strSourceTypeDesc to set.
	 */
	public void setSourceTypeDesc(String strSourceTypeDesc) {
		this.strSourceTypeDesc = strSourceTypeDesc;
	}//end of setSourceTypeDesc(String strSourceTypeDesc)

	/** Retrieve the Sub Category Description
	 * @return Returns the strSubCategoryDesc.
	 */
	public String getSubCategoryDesc() {
		return strSubCategoryDesc;
	}//end of getSubCategoryDesc()

	/** Sets the Sub Category Description
	 * @param strSubCategoryDesc The strSubCategoryDesc to set.
	 */
	public void setSubCategoryDesc(String strSubCategoryDesc) {
		this.strSubCategoryDesc = strSubCategoryDesc;
	}//end of setSubCategoryDesc(String strSubCategoryDesc)

	/** Retrieve the Sub Reason Description
	 * @return Returns the strSubReasonDesc.
	 */
	public String getSubReasonDesc() {
		return strSubReasonDesc;
	}//end of getSubReasonDesc()

	/** Sets the Sub Reason Description
	 * @param strSubReasonDesc The strSubReasonDesc to set.
	 */
	public void setSubReasonDesc(String strSubReasonDesc) {
		this.strSubReasonDesc = strSubReasonDesc;
	}//end of setSubReasonDesc(String strSubReasonDesc)

	/** Retrieve the ContactSeqID
	 * @return Returns the lngContactSeqID.
	 */
	public Long getContactSeqID() {
		return lngContactSeqID;
	}//end of getContactSeqID()

	/** Sets the ContactSeqID
	 * @param lngContactSeqID The lngContactSeqID to set.
	 */
	public void setContactSeqID(Long lngContactSeqID) {
		this.lngContactSeqID = lngContactSeqID;
	}//end of setContactSeqID(Long lngContactSeqID)

	/** Retrieve the LinkIdentifier
	 * @return Returns the strLinkIdentifier.
	 */
	public String getLinkIdentifier() {
		return strLinkIdentifier;
	}//end of getLinkIdentifier()

	/** Sets the LinkIdentifier
	 * @param strLinkIdentifier The strLinkIdentifier to set.
	 */
	public void setLinkIdentifier(String strLinkIdentifier) {
		this.strLinkIdentifier = strLinkIdentifier;
	}//end of setLinkIdentifier(String strLinkIdentifier)

	/** Retrieve the ClaimIntimationVO
	 * @return Returns the claimIntimationVO.
	 */
	public ClaimIntimationVO getClaimIntimationVO() {
		return claimIntimationVO;
	}//end of getClaimIntimationVO()

	/** Sets the ClaimIntimationVO
	 * @param claimIntimationVO The claimIntimationVO to set.
	 */
	public void setClaimIntimationVO(ClaimIntimationVO claimIntimationVO) {
		this.claimIntimationVO = claimIntimationVO;
	}//end of setClaimIntimationVO(ClaimIntimationVO claimIntimationVO)

	/** Retrieve the LoggedBy
	 * @return Returns the strLoggedBy.
	 */
	public String getLoggedBy() {
		return strLoggedBy;
	}//end of getLoggedBy()
	
	/** Sets the LoggedBy
	 * @param strLoggedBy The strLoggedBy to set.
	 */
	public void setLoggedBy(String strLoggedBy) {
		this.strLoggedBy = strLoggedBy;
	}//end of setLoggedBy(String strLoggedBy)
	
	/** Retrieve the CallRelToTypeID
	 * @return Returns the strCallRelToTypeID.
	 */
	public String getCallRelToTypeID() {
		return strCallRelToTypeID;
	}//end of getCallRelToTypeID()

	/** Sets the CallRelToTypeID
	 * @param strCallRelToTypeID The strCallRelToTypeID to set.
	 */
	public void setCallRelToTypeID(String strCallRelToTypeID) {
		this.strCallRelToTypeID = strCallRelToTypeID;
	}//end of setCallRelToTypeID(String strCallRelToTypeID)

	/** Retrieve the GroupRegnSeqID
	 * @return Returns the lngGroupRegnSeqID.
	 */
	public Long getGroupRegnSeqID() {
		return lngGroupRegnSeqID;
	}//end of getGroupRegnSeqID()

	/** Sets the GroupRegnSeqID
	 * @param lngGroupRegnSeqID The lngGroupRegnSeqID to set.
	 */
	public void setGroupRegnSeqID(Long lngGroupRegnSeqID) {
		this.lngGroupRegnSeqID = lngGroupRegnSeqID;
	}//end of setGroupRegnSeqID(Long lngGroupRegnSeqID)

	/** Retrieve the Hospital Seq ID
	 * @return Returns the lngHospSeqID.
	 */
	public Long getHospSeqID() {
		return lngHospSeqID;
	}//end of getHospSeqID()

	/** Sets the Hospital Seq ID
	 * @param lngHospSeqID The lngHospSeqID to set.
	 */
	public void setHospSeqID(Long lngHospSeqID) {
		this.lngHospSeqID = lngHospSeqID;
	}//end of setHospSeqID(Long lngHospSeqID)

	/** Retrieve the Insurance Seq ID
	 * @return Returns the lngInsSeqID.
	 */
	public Long getInsSeqID() {
		return lngInsSeqID;
	}//end of getInsSeqID()

	/** Sets the Insurance Seq ID
	 * @param lngInsSeqID The lngInsSeqID to set.
	 */
	public void setInsSeqID(Long lngInsSeqID) {
		this.lngInsSeqID = lngInsSeqID;
	}//end of setInsSeqID(Long lngInsSeqID)

	/** Retrieve the CallAnswerTypeID
	 * @return Returns the strCallAnswerTypeID.
	 */
	public String getCallAnswerTypeID() {
		return strCallAnswerTypeID;
	}//end of getCallAnswerTypeID()

	/** Sets the CallAnswerTypeID
	 * @param strCallAnswerTypeID The strCallAnswerTypeID to set.
	 */
	public void setCallAnswerTypeID(String strCallAnswerTypeID) {
		this.strCallAnswerTypeID = strCallAnswerTypeID;
	}//end of setCallAnswerTypeID(String strCallAnswerTypeID)

	/** Retrieve the CallContent
	 * @return Returns the strCallContent.
	 */
	public String getCallContent() {
		return strCallContent;
	}//end of getCallContent()

	/** Sets the CallContent
	 * @param strCallContent The strCallContent to set.
	 */
	public void setCallContent(String strCallContent) {
		this.strCallContent = strCallContent;
	}//end of setCallContent(String strCallContent)

	/** Retrieve the CallerTypeID
	 * @return Returns the strCallerTypeID.
	 */
	public String getCallerTypeID() {
		return strCallerTypeID;
	}//end of getCallerTypeID()

	/** Sets the CallerTypeID
	 * @param strCallerTypeID The strCallerTypeID to set.
	 */
	public void setCallerTypeID(String strCallerTypeID) {
		this.strCallerTypeID = strCallerTypeID;
	}//end of setCallerTypeID(String strCallerTypeID)

	/** Retrieve the CallRemarks
	 * @return Returns the strCallRemarks.
	 */
	public String getCallRemarks() {
		return strCallRemarks;
	}//end of getCallRemarks()

	/** Sets the CallRemarks
	 * @param strCallRemarks The strCallRemarks to set.
	 */
	public void setCallRemarks(String strCallRemarks) {
		this.strCallRemarks = strCallRemarks;
	}//end of setCallRemarks(String strCallRemarks)

	/** Retrieve the CategoryTypeID
	 * @return Returns the strCategoryTypeID.
	 */
	public String getCategoryTypeID() {
		return strCategoryTypeID;
	}//end of getCategoryTypeID()

	/** Sets the CategoryTypeID
	 * @param strCategoryTypeID The strCategoryTypeID to set.
	 */
	public void setCategoryTypeID(String strCategoryTypeID) {
		this.strCategoryTypeID = strCategoryTypeID;
	}//end of setCategoryTypeID(String strCategoryTypeID)

	/** Retrieve the EmailID
	 * @return Returns the strEmailID.
	 */
	public String getEmailID() {
		return strEmailID;
	}//end of getEmailID()

	/** Sets the EmailID
	 * @param strEmailID The strEmailID to set.
	 */
	public void setEmailID(String strEmailID) {
		this.strEmailID = strEmailID;
	}//end of setEmailID(String strEmailID)

	/** Retrieve the FraudYN
	 * @return Returns the strFraudYN.
	 */
	public String getFraudYN() {
		return strFraudYN;
	}//end of getFraudYN()

	/** Sets the FraudYN
	 * @param strFraudYN The strFraudYN to set.
	 */
	public void setFraudYN(String strFraudYN) {
		this.strFraudYN = strFraudYN;
	}//end of setFraudYN(String strFraudYN)

	/** Retrieve the HomePhoneNbr
	 * @return Returns the strHomePhoneNbr.
	 */
	public String getHomePhoneNbr() {
		return strHomePhoneNbr;
	}//end of getHomePhoneNbr()

	/** Sets the HomePhoneNbr
	 * @param strHomePhoneNbr The strHomePhoneNbr to set.
	 */
	public void setHomePhoneNbr(String strHomePhoneNbr) {
		this.strHomePhoneNbr = strHomePhoneNbr;
	}//end of setHomePhoneNbr(String strHomePhoneNbr)

	/** Retrieve the LogClosedBy
	 * @return Returns the strLogClosedBy.
	 */
	public String getLogClosedBy() {
		return strLogClosedBy;
	}//end of getLogClosedBy()

	/** Sets the LogClosedBy
	 * @param strLogClosedBy The strLogClosedBy to set.
	 */
	public void setLogClosedBy(String strLogClosedBy) {
		this.strLogClosedBy = strLogClosedBy;
	}//end of setLogClosedBy(String strLogClosedBy)

	/** Retrieve the LoggedByTypeID
	 * @return Returns the strLoggedByTypeID.
	 */
	public String getLoggedByTypeID() {
		return strLoggedByTypeID;
	}//end of getLoggedByTypeID()

	/** Sets the LoggedByTypeID
	 * @param strLoggedByTypeID The strLoggedByTypeID to set.
	 */
	public void setLoggedByTypeID(String strLoggedByTypeID) {
		this.strLoggedByTypeID = strLoggedByTypeID;
	}//end of setLoggedByTypeID(String strLoggedByTypeID)

	/** Retrieve the LogRecordDay
	 * @return Returns the strLogRecordDay.
	 */
	public String getLogRecordDay() {
		return strLogRecordDay;
	}//end of getLogRecordDay()

	/** Sets the LogRecordDay
	 * @param strLogRecordDay The strLogRecordDay to set.
	 */
	public void setLogRecordDay(String strLogRecordDay) {
		this.strLogRecordDay = strLogRecordDay;
	}//end of setLogRecordDay(String strLogRecordDay)

	/** Retrieve the LogRecordTime
	 * @return Returns the strLogRecordTime.
	 */
	public String getLogRecordTime() {
		return strLogRecordTime;
	}//end of getLogRecordTime()

	/** Sets the LogRecordTime
	 * @param strLogRecordTime The strLogRecordTime to set.
	 */
	public void setLogRecordTime(String strLogRecordTime) {
		this.strLogRecordTime = strLogRecordTime;
	}//end of setLogRecordTime(String strLogRecordTime)

	/** Retrieve the LogTypeID
	 * @return Returns the strLogTypeID.
	 */
	public String getLogTypeID() {
		return strLogTypeID;
	}//end of getLogTypeID()

	/** Sets the LogTypeID
	 * @param strLogTypeID The strLogTypeID to set.
	 */
	public void setLogTypeID(String strLogTypeID) {
		this.strLogTypeID = strLogTypeID;
	}//end of setLogTypeID(String strLogTypeID)

	/** Retrieve the MobileNumber
	 * @return Returns the strMobileNumber.
	 */
	public String getMobileNumber() {
		return strMobileNumber;
	}//end of getMobileNumber()

	/** Sets the MobileNumber
	 * @param strMobileNumber The strMobileNumber to set.
	 */
	public void setMobileNumber(String strMobileNumber) {
		this.strMobileNumber = strMobileNumber;
	}//end of setMobileNumber(String strMobileNumber)

	/** Retrieve the OfficePhoneNbr
	 * @return Returns the strOfficePhoneNbr.
	 */
	public String getOfficePhoneNbr() {
		return strOfficePhoneNbr;
	}//end of getOfficePhoneNbr()

	/** Sets the OfficePhoneNbr
	 * @param strOfficePhoneNbr The strOfficePhoneNbr to set.
	 */
	public void setOfficePhoneNbr(String strOfficePhoneNbr) {
		this.strOfficePhoneNbr = strOfficePhoneNbr;
	}//end of setOfficePhoneNbr(String strOfficePhoneNbr)

	/** Retrieve the PriorityTypeID
	 * @return Returns the strPriorityTypeID.
	 */
	public String getPriorityTypeID() {
		return strPriorityTypeID;
	}//end of getPriorityTypeID()

	/** Sets the PriorityTypeID
	 * @param strPriorityTypeID The strPriorityTypeID to set.
	 */
	public void setPriorityTypeID(String strPriorityTypeID) {
		this.strPriorityTypeID = strPriorityTypeID;
	}//end of setPriorityTypeID(String strPriorityTypeID)

	/** Retrieve the ReasonTypeID
	 * @return Returns the strReasonTypeID.
	 */
	public String getReasonTypeID() {
		return strReasonTypeID;
	}//end of getReasonTypeID()

	/** Sets the ReasonTypeID
	 * @param strReasonTypeID The strReasonTypeID to set.
	 */
	public void setReasonTypeID(String strReasonTypeID) {
		this.strReasonTypeID = strReasonTypeID;
	}//end of setReasonTypeID(String strReasonTypeID)

	/** Retrieve the SourceTypeID
	 * @return Returns the strSourceTypeID.
	 */
	public String getSourceTypeID() {
		return strSourceTypeID;
	}//end of getSourceTypeID()

	/** Sets the SourceTypeID
	 * @param strSourceTypeID The strSourceTypeID to set.
	 */
	public void setSourceTypeID(String strSourceTypeID) {
		this.strSourceTypeID = strSourceTypeID;
	}//end of setSourceTypeID(String strSourceTypeID)

	/** Retrieve the StatusTypeID
	 * @return Returns the strStatusTypeID.
	 */
	public String getStatusTypeID() {
		return strStatusTypeID;
	}//end of getStatusTypeID()

	/** Sets the StatusTypeID
	 * @param strStatusTypeID The strStatusTypeID to set.
	 */
	public void setStatusTypeID(String strStatusTypeID) {
		this.strStatusTypeID = strStatusTypeID;
	}//end of setStatusTypeID(String strStatusTypeID)

	/** Retrieve the SubCategoryTypeID
	 * @return Returns the strSubCategoryTypeID.
	 */
	public String getSubCategoryTypeID() {
		return strSubCategoryTypeID;
	}//end of getSubCategoryTypeID()

	/** Sets the SubCategoryTypeID
	 * @param strSubCategoryTypeID The strSubCategoryTypeID to set.
	 */
	public void setSubCategoryTypeID(String strSubCategoryTypeID) {
		this.strSubCategoryTypeID = strSubCategoryTypeID;
	}//end of setSubCategoryTypeID(String strSubCategoryTypeID)

	/** Retrieve the SubReasonTypeID
	 * @return Returns the strSubReasonTypeID.
	 */
	public String getSubReasonTypeID() {
		return strSubReasonTypeID;
	}//end of getSubReasonTypeID()

	/** Sets the SubReasonTypeID
	 * @param strSubReasonTypeID The strSubReasonTypeID to set.
	 */
	public void setSubReasonTypeID(String strSubReasonTypeID) {
		this.strSubReasonTypeID = strSubReasonTypeID;
	}//end of setSubReasonTypeID(String strSubReasonTypeID)

	/** Retrieve the Call Related To Description
	 * @return Returns the strCallRelToDesc.
	 */
	public String getCallRelToDesc() {
		return strCallRelToDesc;
	}//end of getCallRelToDesc()

	/** Sets the Call Related To Description
	 * @param strCallRelToDesc The strCallRelToDesc to set.
	 */
	public void setCallRelToDesc(String strCallRelToDesc) {
		this.strCallRelToDesc = strCallRelToDesc;
	}//end of setCallRelToDesc(String strCallRelToDesc)
	
}//end of CallCenterDetailVO
