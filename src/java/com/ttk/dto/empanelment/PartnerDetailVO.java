/**
 * @ (#) HospitalDetailVO.java Sep 19, 2005
 * Project       : TTK HealthCare Services
 * File          : HospitalDetailVO.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 19, 2005
 *
 * @author       : Srikanth H M
 * Modified by   : Ramakrishna K M
 * Modified date : Sep 29, 2005
 * Reason        : Added Some Methods
 * Modified by   : Ramakrishna K M
 * Modified date : Nov 11, 2005
 * Reason        : Added Some Methods
 */

package com.ttk.dto.empanelment;

import java.util.Date;

import com.ttk.dto.usermanagement.PersonalInfoVO;

public class PartnerDetailVO extends PartnerVO{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strEmpanelmentNO="";//Insurance_corporate_wise_hosp_network
    private String strCityCode ="";//Insurance_corporate_wise_hosp_network
    private String partnerName ="";//Insurance_corporate_wise_hosp_network
    private String TTKBranchCode ="";//Insurance_corporate_wise_hosp_network
    private String strassociateCode ="";//Insurance_corporate_wise_hosp_network
	private String strPartnerYN		=	null;
    private Long lPtnrGnrlSeqId=null;
    private Long lTpaOfficeSeqId=null;
    private Long lProdPolicySeqId = null;
   private PartnerAuditVO ptnrAuditObj=null;
    private String currencyAccepted ="";
 //   private String strAlkootLocation ="";
  //  private String empanelmentDate =null;
    private String strPreEmpanelmentMailID =""; 
    private Long limit=null;
    private Long fee=null;
    private Long Discount=null;
	private Date startDate=null;
    private Date  endDate=null;

    
	//String fields declared private	
	private Date dtTpaRegDate=null;
	private String strPayOrderNmbr="";
	private String strTpaBranchCode="";
	private Date dtDocDispDate=null;
	private Date dtDocDelDate=null;
	private DocumentDetailVO docDetailObj=null;
	private Date dtTariffRcvdDate=null;
	private Date dtInfoRcvdDate=null;
	private String strTypeCode="";
	private String strHospRegNmbr="";
	private String strRegAuthority="";
	private String strIrdaNumber="";
	private String strPanNmbr="";
	private String strTanNmbr="";
	private String strHospitalStatusID="";
	private String strCategoryID="";
	private String strRating="";
	private String strCommTypeCode="";
	private String strIntExtApp="";
	private AddressVO addressObj=null;
	private String strStdCode="";
	private String strLandmarks="";
	private String strPrimaryEmailId="";
	private String strWebsite="";
	private String strInternetConn="";
	private String strDiscrepancy="";
    private String strEmplStatusTypeId=null;
    private String strEmplTypeId="";
    private String strRemarks="";
    private String strProvStatus = "";
    private String strStausGnrlTypeId = "";
    private String strOfficePhone1 = "";
    private String strOfficePhone2 = "";
    private String strFaxNbr = "";
    private Date dtHospVerifyRcvdDate = null;
    private Date dtRegCRTRcvdDate = null;
    private Date dtHospRegDate=null;
    private String strNotificationTypeID = "";//NOTIFICATION_GENERAL_TYPE_ID
    private String strServiceTaxRegnNbr ="";
    private String strGipsaPpnYN = ""; //   <!-- added for RoomRenttariff -->
    
    
  //all 4 added for projectX
    private String strCNYN 				= 	"";
    private String strGNYN 				= 	"";
    private String strSRNYN 			= 	"";
    private String strRNYN 				= 	"";
    private String strWNYN 				= 	"";
    private String strRegAuthorityID	=	"";
    private String strProviderName 		= 	null;
	private String strProviderID		= 	null;
	private String strEmirate 			= 	null;
	private String strProviderTypeId 	= 	null;
	private String strprimaryNetworkID 	= 	null;
	private String strIsdCode			= 	null;
	private String strSpeciality		=	null;
	private String strTradeLicenceNo	=	null;
	private String strAuthLicenceNo		=	null;
	private PersonalInfoVO personalInfoObj	=	null;
	private String strClaimSubmission		=	null;
	private String strPreEmpanelMailId		=	null;
	private String strCountryCode			=	null;
	
	private String strProvGrpId			=	null;
	private String strProvGrpListId		=	null;
	private String strSectorType		=	null;
	private String strSectorNumb		=	null;
	private String strProviderReview	=	null;
	private String strPayerCodes		=	null;
	private String strIndORgrp			=	null;
	
	
	






	//for Group Getters
	private String strGroupName			=	null;
	private String grpContactPerson		=	null;
	private String grpContactNo			=	null;
	private String grpContactEmail		=	null;
	private String grpContactAddress	=	null;
	private String createNewGrp			=	null;
	private String strTradeLicenceName		=	null;
	
	private String factor;
	private String baseRate;
	private String gap;
	private String margin;
	private String eligibleNetworks;
    

	public Long getDiscount() {
		return Discount;
	}
	public void setDiscount(Long discount) {
		Discount = discount;
	}
	public Long getFee() {
		return fee;
	}
	public void setFee(Long fee) {
		this.fee = fee;
	}
/*	public String getEmpanelmentDate() {
		return empanelmentDate;
	}
	public void setEmpanelmentDate(String empanelmentDate) {
		this.empanelmentDate = empanelmentDate;
	}*/
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getTradeLicenceName() {
		return strTradeLicenceName;
	}
	public void setTradeLicenceName(String strTradeLicenceName) {
		this.strTradeLicenceName = strTradeLicenceName;
	}

	public String getPreEmpanelmentMailID() {
		return strPreEmpanelmentMailID;
	}

	public void setPreEmpanelmentMailID(String strPreEmpanelmentMailID) {
		this.strPreEmpanelmentMailID = strPreEmpanelmentMailID;
	}
	
	public String getTTKBranchCode() {
		return TTKBranchCode;
	}
	public void setTTKBranchCode(String tTKBranchCode) {
		TTKBranchCode = tTKBranchCode;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getCurrencyAccepted() {
		return currencyAccepted;
	}
	public void setCurrencyAccepted(String currencyAccepted) {
		this.currencyAccepted = currencyAccepted;
	}
	
	public Long getLimit() {
		return limit;
	}
	public void setLimit(Long limit) {
		this.limit = limit;
	}

	
	
	public String getPartnerYN() {
		return strPartnerYN;
	}
	public void setPartnerYN(String strPartnerYN) {
		this.strPartnerYN = strPartnerYN;
	}
    
    public void setPtnrGnrlSeqId(Long lPtnrGnrlSeqId)
    {
        this.lPtnrGnrlSeqId = lPtnrGnrlSeqId;
    }//end of setHospGnrlSeqId(Long lHospGnrlSeqId)

    public Long getPtnrGnrlSeqId()
    {
        return lPtnrGnrlSeqId;
    }//end of getHospGnrlSeqId()
    

	public PartnerAuditVO getPartnerAuditVO()
	{
		return ptnrAuditObj;
	}//end of getPartnerAuditVO()
    
	public void setPartnerAuditVO(PartnerAuditVO ptnrAuditObj)
	{
		this.ptnrAuditObj=ptnrAuditObj;
	}//end of setHospitalAuditVO(HospitalAuditVO hospAuditObj)
	
	

    
    

	
	public String getCreateNewGrp() {
		return createNewGrp;
	}
	public void setCreateNewGrp(String createNewGrp) {
		this.createNewGrp = createNewGrp;
	}
	public String getGrpContactPerson() {
		return grpContactPerson;
	}
	public void setGrpContactPerson(String grpContactPerson) {
		this.grpContactPerson = grpContactPerson;
	}
	public String getGrpContactNo() {
		return grpContactNo;
	}
	public void setGrpContactNo(String grpContactNo) {
		this.grpContactNo = grpContactNo;
	}
	public String getGrpContactEmail() {
		return grpContactEmail;
	}
	public void setGrpContactEmail(String grpContactEmail) {
		this.grpContactEmail = grpContactEmail;
	}
	public String getGrpContactAddress() {
		return grpContactAddress;
	}
	public void setGrpContactAddress(String grpContactAddress) {
		this.grpContactAddress = grpContactAddress;
	}
	public String getIndORgrp() {
		return strIndORgrp;
	}
	public void setIndORgrp(String strIndORgrp) {
		this.strIndORgrp = strIndORgrp;
	}
	public String getGroupName() {
		return strGroupName;
	}
	public void setGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}
	public String getPayerCodes() {
		return strPayerCodes;
	}
	public void setPayerCodes(String strPayerCodes) {
		this.strPayerCodes = strPayerCodes;
	}
	public String getWNYN() {
		return strWNYN;
	}
	public void setWNYN(String strWNYN) {
		this.strWNYN = strWNYN;
	}
	public String getProviderReview() {
		return strProviderReview;
	}
	public void setProviderReview(String strProviderReview) {
		this.strProviderReview = strProviderReview;
	}
	public String getSectorType() {
		return strSectorType;
	}
	public void setSectorType(String strSectorType) {
		this.strSectorType = strSectorType;
	}
	public String getSectorNumb() {
		return strSectorNumb;
	}
	public void setSectorNumb(String strSectorNumb) {
		this.strSectorNumb = strSectorNumb;
	}
	
	public String getProvGrpId() {
		return strProvGrpId;
	}
	public void setProvGrpId(String strProvGrpId) {
		this.strProvGrpId = strProvGrpId;
	}
	public String getProvGrpListId() {
		return strProvGrpListId;
	}
	public void setProvGrpListId(String strProvGrpListId) {
		this.strProvGrpListId = strProvGrpListId;
	}
	public String getPreEmpanelMailId() {
		return strPreEmpanelMailId;
	}
	public void setPreEmpanelMailId(String strPreEmpanelMailId) {
		this.strPreEmpanelMailId = strPreEmpanelMailId;
	}
	public String getClaimSubmission() {
		return strClaimSubmission;
	}
	public void setClaimSubmission(String strClaimSubmission) {
		this.strClaimSubmission = strClaimSubmission;
	}
	public String getAuthLicenceNo() {
		return strAuthLicenceNo;
	}
	public void setAuthLicenceNo(String strAuthLicenceNo) {
		this.strAuthLicenceNo = strAuthLicenceNo;
	}
	public String getTradeLicenceNo() {
		return strTradeLicenceNo;
	}
	public void setTradeLicenceNo(String strTradeLicenceNo) {
		this.strTradeLicenceNo = strTradeLicenceNo;
	}
	
	
	public String getCityCode() {
		return strCityCode;
	}

	public void setCityCode(String strCityCode) {
		this.strCityCode = strCityCode;
	}
	public String getEmpanelmentNO() {
		return strEmpanelmentNO;
	}

	public void setEmpanelmentNO(String strEmpanelmentNO) {
		this.strEmpanelmentNO = strEmpanelmentNO;
	}
	
	
	
	//Insurance_corporate_wise_hosp_network
    public String getassociateCode() {
		return strassociateCode;
	}

	public void setassociateCode(String strassociateCode) {
		this.strassociateCode = strassociateCode;
	}

	/**
     * Retrieve the Country Code
     * 
     * @return  strCountryCode String Country Code
     */
    public String getCountryCode() {
        return strCountryCode;
    }//end of getCountryCode()
    
    /**
     * Sets the Country Code
     * 
     * @param  strCountryCode String Country Code 
     */
    public void setCountryCode(String strCountryCode) {
       this.strCountryCode = strCountryCode;
    }//end of setCountryCode(String strCountryCode)
    
	
	private ContactVO contactVO	=	null;
	

	public ContactVO getContactVoObj() {
		return contactVO;
	}
	public void setContactVO(ContactVO contactVO) {
		this.contactVO = contactVO;
	}
	
	
	public PersonalInfoVO getPersonalInfoObj() {
		return personalInfoObj;
	}
	public void setPersonalInfoObj(PersonalInfoVO personalInfoObj) {
		this.personalInfoObj = personalInfoObj;
	}
	
	
	public String getSpeciality() {
		return strSpeciality;
	}
	public void setSpeciality(String strSpeciality) {
		this.strSpeciality = strSpeciality;
	}

	
	public String getIsdCode() {
		return strIsdCode;
	}
	public void setIsdCode(String strIsdCode) {
		this.strIsdCode = strIsdCode;
	}
	
	public String getPrimaryNetworkID() {
		return strprimaryNetworkID;
	}
	public void setPrimaryNetworkID(String strprimaryNetworkID) {
		this.strprimaryNetworkID = strprimaryNetworkID;
	}
	public String getProviderTypeId() {
		return strProviderTypeId;
	}
	public void setProviderTypeId(String strProviderTypeId) {
		this.strProviderTypeId = strProviderTypeId;
	}
	public String getProviderName() {
		return strProviderName;
	}
	public void setProviderName(String strProviderName) {
		this.strProviderName = strProviderName;
	}
	public String getProviderID() {
		return strProviderID;
	}
	public void setProviderID(String strProviderID) {
		this.strProviderID = strProviderID;
	}
	public String getEmirate() {
		return strEmirate;
	}
	public void setEmirate(String strEmirate) {
		this.strEmirate = strEmirate;
	}
	
    public String getRegAuthorityID() {
		return strRegAuthorityID;
	}

	public void setRegAuthorityID(String strRegAuthorityID) {
		this.strRegAuthorityID = strRegAuthorityID;
	}

	public String getCNYN() {
		return strCNYN;
	}

	public void setCNYN(String strCNYN) {
		this.strCNYN = strCNYN;
	}

	public String getGNYN() {
		return strGNYN;
	}

	public void setGNYN(String strGNYN) {
		this.strGNYN = strGNYN;
	}

	public String getSRNYN() {
		return strSRNYN;
	}

	public void setSRNYN(String strSRNYN) {
		this.strSRNYN = strSRNYN;
	}

	public String getRNYN() {
		return strRNYN;
	}

	public void setRNYN(String strRNYN) {
		this.strRNYN = strRNYN;
	}   
    
    public String getGipsaPpnYN() {
		return strGipsaPpnYN;
	}

	public void setGipsaPpnYN(String strGipsaPpnYN) {
		this.strGipsaPpnYN = strGipsaPpnYN;
	}
//<!-- end for RoomRenttariff -->
	/** Retrieve the NotificationTypeID
	 * @return Returns the strNotificationTypeID.
	 */
	public String getNotificationTypeID() {
		return strNotificationTypeID;
	}//end of getNotificationTypeID()

	/** Sets the NotificationTypeID
	 * @param strNotificationTypeID The strNotificationTypeID to set.
	 */
	public void setNotificationTypeID(String strNotificationTypeID) {
		this.strNotificationTypeID = strNotificationTypeID;
	}//end of setNotificationTypeID(String strNotificationTypeID)

	

	/** Retrieve the Hosp Verify Rcvd Date
	 * @return Returns the dtHospVerifyRcvdDate.
	 */
	public Date getHospVerifyRcvdDate() {
		return dtHospVerifyRcvdDate;
	}//end of getHospVerifyRcvdDate()

	/** Sets the Hosp Verify Rcvd Date
	 * @param dtHospVerifyRcvdDate The dtHospVerifyRcvdDate to set.
	 */
	public void setHospVerifyRcvdDate(Date dtHospVerifyRcvdDate) {
		this.dtHospVerifyRcvdDate = dtHospVerifyRcvdDate;
	}//end of setHospVerifyRcvdDate(Date dtHospVerifyRcvdDate)

	/** Retrieve the Reg CRT Rcvd Date
	 * @return Returns the dtRegCRTRcvdDate.
	 */
	public Date getRegCRTRcvdDate() {
		return dtRegCRTRcvdDate;
	}//end of getRegCRTRcvdDate()

	/** Sets the Reg CRT Rcvd Date
	 * @param dtRegCRTRcvdDate The dtRegCRTRcvdDate to set.
	 */
	public void setRegCRTRcvdDate(Date dtRegCRTRcvdDate) {
		this.dtRegCRTRcvdDate = dtRegCRTRcvdDate;
	}//end of setRegCRTRcvdDate(Date dtRegCRTRcvdDate)

	/*
	 * Set the discrepancy
	 * @param strDiscrepancy String discrepancy status
	 */
	public void setDiscrepancy(String strDiscrepancy)
	{
		this.strDiscrepancy=strDiscrepancy;
	}//end of setDiscrepancy(String strDiscrepancy)
	
	/*
	 * Retrieve the discrepancy
	 * @return strDiscrepancy String discrepancy status
	 */
	public String getDiscrepancy()
	{
		return strDiscrepancy;
	}//end of getDisparency()	
	
	/*
	 * Sets the tpa registration date
	 * @param dtTpaRegDate Date tpa registration date
	 */
	public void setTpaRegDate(Date dtTpaRegDate)
	{
		this.dtTpaRegDate=dtTpaRegDate;
	}//end of setTpaRegDate(Date dtTpaRegDate)
	
	/*
	 * Retrieve the tpa registration date
	 * @return dtTpaRegDate Date tpa registration date
	 */
	
	public Date getTpaRegDate()
	{
		return dtTpaRegDate;
	}//end of getTpaRegDate()
	
	/*
	 * Set the pay order number
	 * @param strPayOrderNmbr String pay order number
	 */
	public void setPayOrderNmbr(String strPayOrderNmbr)
	{
		this.strPayOrderNmbr=strPayOrderNmbr;
	}//end of setPayOrderNmbr(String strPayOrderNmbr)
	
	/*
	 * Retrieve the pay order number
	 * @return strPayOrderNmbr String pay order number 
	 */
	public String getPayOrderNmbr()
	{
		return strPayOrderNmbr;
	}//end of getPayOrderNmbr()
	
	/*
	 * Sets the tpa branch code 
	 * @param strTpaBranchCode String tpa branch code
	 */
	public void setTpaBranchCode(String strTpaBranchCode)
	{
		this.strTpaBranchCode=strTpaBranchCode;
	}//end of setTpaBranchCode(String strTpaBranchCode)
	
	/*
	 * Retrieve the tpa branch code
	 * @return strTpaBranchCode String tpa branch code 
	 */
	public String getTpaBranchCode()
	{
		return strTpaBranchCode;
	}//end of getTpaBranchCode()		
	
	/*
	 * Sets the document dispatch date
	 * @param dtDocDispDate Date document dispatch date
	 */
	public void setDocDispDate(Date dtDocDispDate)
	{
		this.dtDocDispDate=dtDocDispDate;
	}//end of setDocDispDate(Date dtDocDispDate)
	
	/*
	 * Retrieve the document dispatch date
	 * @return dtDocDispDate Date tariff received date
	 */
	public Date getDocDispDate()
	{
		return dtDocDispDate;
	}//end of getDocDispDate()
	
	/*
	 * Sets the document delivery date
	 * @param dtDocDelDate Date document delivery date
	 */
	public void setDocDelDate(Date dtDocDelDate)
	{
		this.dtDocDelDate=dtDocDelDate;
	}//end of setDocDelDate(Date dtDocDelDate)
	
	/*
	 * Retrieve the document delivery date
	 * @return dtDocDelDate Date document delivery date 
	 */
	public Date getDocDelDate()
	{
		return dtDocDelDate;
	}//end of getDocDelDate()
	
	/*
	 * Sets the hospital document detail
	 * @param docDetailObj DocumentDetailVO hospital document detail
	 */
	public void setDocumentDetailVO(DocumentDetailVO docDetailObj)
	{
		this.docDetailObj=docDetailObj;
	}//end of setDocumentDetailVO(DocumentDetailVO docDetailObj)
	
	/*
	 * Retrieve the hospital document details
	 * @return HospDocDetailObj DocumentDetailVO hospital document detail
	 */
	public DocumentDetailVO getDocumentDetailVO()
	{
		return docDetailObj;
	}//end of getDocumentDetailVO() 	
	
	
	
	/*
	 * Sets the tariff received date
	 * @param dtTariffRcvdDate Date tariff received date 
	 */
	public void setTariffRcvdDate(Date dtTariffRcvdDate)
	{
		this.dtTariffRcvdDate=dtTariffRcvdDate;
	}//end of setTariffRcvdDate(Date dtTariffRcvdDate)
	
	/*
	 * Retrieve the tariff received date
	 * @return dtTariffRcvdDate Date tariff received date  
	 */
	public Date getTariffRcvdDate()
	{
		return dtTariffRcvdDate;
	}//end of getTariffRcvdDate()
	
	/*
	 * Sets the information received date
	 * @param dtInfoRcvdDate Date information received date 
	 */
	public void setInfoRcvdDate(Date dtInfoRcvdDate)
	{
		this.dtInfoRcvdDate=dtInfoRcvdDate;
	}//end of setInfoRcvdDate(Date dtInfoRcvdDate)
	
	/*
	 * Retrieve the information received date
	 * @return dtInfoRcvdDate Date information received date 
	 */		
	public Date getInfoRcvdDate()
	{
		return dtInfoRcvdDate;
	}//end of  getInfoRcvdDate()
	
	/*
	 *Set the the type code
	 *@param strTypeCode String  type code 
	 */
	public void setTypeCode(String strTypeCode)
	{
		this.strTypeCode=strTypeCode;
	}//end of setTypeCode(String strTypeCode)
	
	/*
	 * Retrieve the type code
	 * @return strTypeCode String  type code
	 */
	public String getTypeCode()
	{
		return strTypeCode;
	}//end of getTypeCode()
	
	/*
	 * Sets the hospital registration number
	 * @param strHospRegNmbr String hospital registration number
	 */
	public void setHospRegNmbr(String strHospRegNmbr)
	{
		this.strHospRegNmbr=strHospRegNmbr;
	}//end of setHospRegNmbr(String strHospRegNmbr)
	
	/*
	 * Retrieve the hospital registration number
	 * @return strHospRegNmbr String hospital registration number
	 */
	public String getHospRegNmbr()
	{
		return strHospRegNmbr;
	}//end of getHospRegNmbr()
	
	/*
	 * Sets the registration authority
	 * @param strRegAuthority String registration authority
	 */
	public void setRegAuthority(String strRegAuthority)
	{
		this.strRegAuthority=strRegAuthority;
	}//end of setRegAuthority(String strRegAuthority)
	
	/*
	 * Retrieve the registration authority
	 * @return strRegAuthority String registration authoriy 
	 */
	public String getRegAuthority()
	{
		return strRegAuthority;
	}//end of getRegAuthority()
	
	/*
	 * Sets the irda number
	 * @param strIrdaNumber String irdanumber
	 */
	public void setIrdaNumber(String strIrdaNumber)
	{
		this.strIrdaNumber=strIrdaNumber;
	}//end of setIrdaNumber(String strIrdaNumber)
	
	/*
	 * Retrieve the irda number
	 * @return strIrdaNumber String irda number 
	 */
	public String getIrdaNumber()
	{
		return strIrdaNumber;
	}//end of getIrdaNumber()
	
	/*
	 * Sets the pan number 
	 * @param strPanNmbr String pan number
	 */
	public void setPanNmbr(String strPanNmbr)
	{
		this.strPanNmbr=strPanNmbr;
	}//end of setPanNmbr(String strPanNmbr)
	
	/*
	 * Retrieve the pan number
	 * @return strPanNmbr String pan number 
	 */
	public String getPanNmbr()
	{
		return strPanNmbr;
	}//end of getPanNmbr()
	
	/*
	 * Sets the TAN number 
	 * @param strTanNmbr String TAN number
	 */
	public void setTanNmbr(String strTanNmbr)
	{
		this.strTanNmbr=strTanNmbr;
	}//end of setTanNmbr(String strTanNmbr)
	
	/*
	 * Retrieve the TAN number
	 * @return strTanNmbr String TAN number 
	 */
	public String getTanNmbr()
	{
		return strTanNmbr;
	}//end of getTanNmbr()
	
	/*
	 * Sets the hospital status 
	 * @param strHospitalStatusID String hospital status
	 */
	public void setHospitalStatusID(String strHospitalStatusID)
	{
		this.strHospitalStatusID=strHospitalStatusID;
	}//end of setHospitalStatusID(String strHospitalStatusID)
	
	/*
	 * Retrieve the hospital status
	 * @return strHospitalStatusID String hospital status 
	 */
	public String getHospitalStatusID()
	{
		return strHospitalStatusID;
	}//end of getHospitalStatusID()
	
	/*
	 * Sets the category 
	 * @param strCategoryID String category
	 */
	public void setCategoryID(String strCategoryID)
	{
		this.strCategoryID=strCategoryID;
	}//end of setCategoryID(String strCategoryID)
	
	/*
	 * Retrieve the category
	 * @return strCategoryID String category 
	 */
	public String getCategoryID()
	{
		return strCategoryID;
	}//end of getCategoryID()
	
	/*
	 * Sets the rating
	 * @param strRating String rating
	 */
	public void setRating(String strRating)
	{
		this.strRating=strRating;
	}//end of setRating(String strRating)
	
	/*
	 *Retrieve the rating
	 *@return  strRating String rating
	 */
	public String getRating()
	{
		return strRating;
	}//end of getRating()
	
	/*
	 * Sets the communication type code
	 * @param strCommTypeCode String communication type code
	 */
	public void setCommTypeCode(String strCommTypeCode)
	{
		this.strCommTypeCode=strCommTypeCode;
	}//end of setCommTypeCode(String strCommTypeCode)
	
	/*
	 * Retrieve the communication type code
	 * @return strCommTypeCode String communication type code 
	 */
	public String getCommTypeCode()
	{
		return strCommTypeCode;
	}//end of getCommTypeCode()
	
	/*
	 * Sets the external application used by hospital
	 * @param  strIntExtApp  String external application used by hospital
	 */
	public void setIntExtApp(String strIntExtApp)
	{
		this.strIntExtApp=strIntExtApp;
	}//end of setIntExtApp(String strIntExtApp)
	
	/*
	 * Retrieve the external application used by hospital
	 * @param strIntExtApp String external application used by hospital
	 */
	public String getIntExtApp()
	{
		return strIntExtApp;
	}//end of getIntExtApp()	
	
	/*
	 * Sets the address information
	 * @param addressObj AddressVO address information
	 */
	public void setAddressVO(AddressVO addressObj)
	{
		this.addressObj=addressObj; 
	}//end of setAddressVO(AddressVO addressObj)
	
	/*
	 * Retrieve the address information 
	 * @return addressObj AddressVO address information
	 */
	public AddressVO getAddressVO()
	{
		return addressObj;
	}//end of getAddress()
		
	/*
	 * Sets the std code
	 * @param strStdCode String std code
	 */
	public void setStdCode(String strStdCode)
	{
		this.strStdCode=strStdCode;
	}//end of setStdCode(Long lStdCode)
	
	/*
	 * Retrieve the std code
	 * @return strStdCode String std code 
	 */
	public String getStdCode()
	{
		return strStdCode;
	}//end of getStdCode()	
	
	/*Sets the land mark
	 *@param strLandmarks String land mark 
	 */
	public void setLandmarks(String strLandmarks)
	{
		this.strLandmarks=strLandmarks;
	}//end of setLandmarks(String strLandmarks)
	
	/*
	 * Retrieve the land mark
	 * @return strLandmarks String land mark 
	 */
	public String getLandmarks()
	{
		return strLandmarks;
	}//end of getLandmarks()	
	
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
	 * Sets the website
	 * @param strWebsite String website
	 */
	public void setWebsite(String strWebsite)
	{
		this.strWebsite=strWebsite;
	}//end of setWebsite(String strWebsite)
	
	/*
	 * Retrieve the website
	 * @return strWebsite String website 
	 */
	public String getWebsite()
	{
		return strWebsite;
	}//end of getWebsite()
	
	/*
	 * Sets the internet connection y/n
	 * @param strInternetConn String internet connection y/n
	 */
	public void setInternetConnYn(String strInternetConn)
	{
		this.strInternetConn=strInternetConn;
	}//end of setInternetConnYn(String strInternetConn)
	
	/*
	 * Retrieve the internet connection y/n
	 * @return strInternetConn String internet connection y/n 
	 */
	public String getInternetConnYn()
	{
		return strInternetConn;
	}//end of getInternetConnYn()
	
	/**
     * Sets the Empanel status type id
     * 
     * @param strEmplStatusTypeId String 
     */
    public void setEmplStatusTypeId(String strEmplStatusTypeId)
    {
        this.strEmplStatusTypeId = strEmplStatusTypeId;
    }//end of setEmplStatusTypeId(String strEmplStatusTypeId)
    
    /**
     * Retreive Empanel status type id
     * 
     * @return strEmplStatusTypeId String 
     */
    public String getEmplStatusTypeId()
    {
        return strEmplStatusTypeId;
    }//end of getEmplStatusTypeId()
    
    /**
     * Sets the Empanel  type id     
     * @param strEmplTypeId String empanel type id
     */
    public void setEmplTypeId(String strEmplTypeId)
    {
        this.strEmplTypeId = strEmplTypeId;
    }//end of setEmplTypeId(String strEmplTypeId)
    
    /**
     * Retreive Empanel  type id     
     * @return strEmplTypeId String empanel type id 
     */
    public String getEmplTypeId()
    {
        return strEmplTypeId;
    }//end of getEmplTypeId()   
    
   

    /**
     * Sets the Tpa Office Seq id
     * 
     * @param lTpaOfficeSeqId Long 
     */
    public void setTpaOfficeSeqId(Long lTpaOfficeSeqId)
    {
        this.lTpaOfficeSeqId = lTpaOfficeSeqId;
    }//end of setTpaOfficeSeqId(Long lTpaOfficeSeqId)
    
    /**
     * Retreive Tpa Office Seq id
     * 
     * @return lTpaOfficeSeqId Long 
     */
    public Long getTpaOfficeSeqId()
    {
        return lTpaOfficeSeqId;
    }//end of getTpaOfficeSeqId()
    
    /**
	 * Retreive the remarks
	 * @return strRemarks String remarks.
	 */
	public String getRemarks() {
		return strRemarks;
	}//end of getRemarks()
	/**
	 * Sets the remarks
	 * @param strRemarks String remarks.
	 */
	public void setRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}//end of setRemarks(String strRemarks)
	
    /** Retrieve the Provider General Type Id
     * @return strProvStatus String Provider General Type Id
     */
    public String getProvStatus() {
        return strProvStatus;
    }//end of getProvStatus()
    
    /** Sets the Provider General Type Id
     * @param strProvStatus String Provider General Type Id
     */
    public void setProvStatus(String strProvStatus) {
        this.strProvStatus = strProvStatus;
    }//end of setProvStatus(String strProvStatus)
    
	/** Retrieve the Product Policy Seq Id
	 * @return Returns the lProdPolicySeqId.
	 */
	public Long getProdPolicySeqId() {
		return lProdPolicySeqId;
	}//end of getProdPolicySeqId()
	
	/** Sets the Product Policy Seq Id
	 * @param prodPolicySeqId The lProdPolicySeqId to set.
	 */
	public void setProdPolicySeqId(Long prodPolicySeqId) {
		this.lProdPolicySeqId = prodPolicySeqId;
	}//end of setProdPolicySeqId(Long prodPolicySeqId)
	
	/** Retrieve the Status General Type Id
	 * @return Returns the strStausGnrlTypeId.
	 */
	public String getStausGnrlTypeId() {
		return strStausGnrlTypeId;
	}//end of getStausGnrlTypeId()
	
	/** Sets the Status General Type Id
	 * @param strStausGnrlTypeId The strStausGnrlTypeId to set.
	 */
	public void setStausGnrlTypeId(String strStausGnrlTypeId) {
		this.strStausGnrlTypeId = strStausGnrlTypeId;
	}//end of setStausGnrlTypeId(String strStausGnrlTypeId)

	/** This method returns the Office Phone Number 1
	 * @return Returns the strOfficePhone1.
	 */
	public String getOfficePhone1() {
		return strOfficePhone1;
	}// end of getOfficePhone1()

	/** This method sets the Office Phone Number 1   
	 * @param strOfficePhone1 The strOfficePhone1 to set.
	 */
	public void setOfficePhone1(String strOfficePhone1) {
		this.strOfficePhone1 = strOfficePhone1;
	}//end of setOfficePhone1(String strOfficePhone1)

	/** This method returns the Office Phone Number 2 
	 * @return Returns the strOfficePhone2.
	 */
	public String getOfficePhone2() {
		return strOfficePhone2;
	}// End of getOfficePhone2()

	/** This method sets the Office Phone Number 2 
	 * @param strOfficePhone2 The strOfficePhone2 to set.
	 */
	public void setOfficePhone2(String strOfficePhone2) {
		this.strOfficePhone2 = strOfficePhone2;
	}// End of setOfficePhone2(String strOfficePhone2)

	/** This method returns the Fax Number
	 * @return Returns the strFaxNo.
	 */
	public String getFaxNbr() {
		return strFaxNbr;
	}//End of getFaxNo()

	/** This method sets the Fax Number 
	 * @param strFaxNo The strFaxNo to set.
	 */
	public void setFaxNbr(String strFaxNbr) {
		this.strFaxNbr = strFaxNbr;
	}// End of setFaxNbr(String strFaxNbr)
	
	/*
	 * Sets the hospital registration date
	 * @param dtHospRegDate Date hospital registration date
	 */
	public void setHospRegDate(Date dtHospRegDate)
	{
		this.dtHospRegDate=dtHospRegDate;
	}//end of setHospRegDate(Date dtHospRegDate)
	
	/*
	 * Retrieve the hospital registration date
	 * @return dtHospRegDate Date hospital registration date
	 */	
	public Date getHospRegDate()
	{
		return dtHospRegDate;
	}//end of getHospRegDate()
	
	/** This method returns the ServiceTaxRegnNbr
	 * @return the strServiceTaxRegnNbr
	 */
	public String getServiceTaxRegnNbr() {
		return strServiceTaxRegnNbr;
	}//end of getServiceTaxRegnNbr()

	/** This method sets the ServiceTaxRegnNbr 
	 * @param strServiceTaxRegnNbr the strServiceTaxRegnNbr to set
	 */
	public void setServiceTaxRegnNbr(String strServiceTaxRegnNbr) {
		this.strServiceTaxRegnNbr = strServiceTaxRegnNbr;
	}//end of setServiceTaxRegnNbr(String strServiceTaxRegnNbr)
	public String getFactor() {
		return factor;
	}
	public void setFactor(String factor) {
		this.factor = factor;
	}
	public String getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}
	public String getGap() {
		return gap;
	}
	public void setGap(String gap) {
		this.gap = gap;
	}
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}
	public String getEligibleNetworks() {
		return eligibleNetworks;
	}
	public void setEligibleNetworks(String eligibleNetworks) {
		this.eligibleNetworks = eligibleNetworks;
	}
	
}//end of HospitalDetailVO
