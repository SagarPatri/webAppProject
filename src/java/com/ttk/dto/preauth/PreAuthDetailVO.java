/** 
 *  @ (#)PreAuthDetailVO.java Apr 10, 2006
 *  Project       : TTK HealthCare Services
 *  File          : PreAuthDetailVO.java
 *  Author        : Arun K N
 *  Company       : Span Systems Corporation
 *  Date Created  : Apr 10, 2006
 * 
 *  @author       :  Arun K N
 *  Modified by   :  
 *  Modified date :  
 *  Reason        :  
 *   
 */
package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.ttk.common.TTKCommon;
import com.ttk.dto.claims.ClaimDetailVO;


public class PreAuthDetailVO extends PreAuthVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ipCopayLimitYN="";
	private String requestedAmountcurrencyType;
   // private BigDecimal convertedAmount;
	private String convertedAmount;
    private String conversionRate=null;
    private String strHospitalizedTypeID="";
    private BigDecimal bdPrevApprovedAmount = null;
    private BigDecimal bdClaimRequestAmount = null;
    //private BigDecimal bdRequestAmount = null;
    private Date dtAdmissionDate = null;
    private String strAdmissionTime="";
    private String strAdmissionDay="";
    private String receiveDate = null;//receivedDate
    private String receiveTime="";
    private String receiveDay="";
    private Date activityStartDate = null;
    private Date dtClmAdmissionDate = null;
    private String strReceivedTime="";
    private String strReceivedDay="";   
    private String strClmAdmissionTime="";        
    private String strDoctorName="";
    private String strPreAuthRecvTypeID="";
    private String strInvestigationReqdYN="";
    private String strHospitalPhone="";    
    private Integer intReviewCount = null;
    private Long lngEventSeqID = null;
    private Integer intRequiredReviewCnt = null;
    private String strReview = "";
    private String strCompletedYN = "";
    private String strProcessCompletedYN = "";
    private String strDataDiscPresentYN = "";
    private String strCloseProximityYN="";
    private String strProximityStatusTypeID = "";
    private Date dtProximityDate = null;
    private String strPreAuthEnhancedYN = "";
    private String strConflictTypeID = "";
    private PreAuthHospitalVO preAuthHospitalVO = null;
    private ClaimantVO claimantVO = null;
    private AdditionalDetailVO additionalDetailVO = null;
    private ClaimDetailVO claimDetailVO = null;
    private DiagnosisDetailsVO diagnosisDetailsVO=null;
    private ActivityDetailsVO activityDetailsVO=null;
   private Long parentPreAuthSeqID=null;
   private Long activityDtlSeqId=null;
    private String authNum="";
    private String clinicianStatus="";
    private String claimSubmissionType;
    private String  primaryAilment="";
    private String ailmentDescription="";
    private Long icdCodeSeqId;
    private Long diagSeqId;
    private String icdCode="";
    private String claimFrom="";
    private String finalRemarks="";
    private String denialCode="";
    private String claimNo="";
    private String  provRegAuthority="";
    private String finalAprAmt="";
   // private String presentingComplaints;
    private String  vatTrnCode="";
    
	private String strEventName = "";
    private String strDiscPresentYN = "";
    private BigDecimal bdApprovedAmt = null;
    private String strProcessingBranch = "";
    private String strPreauthTypeDesc = "";
    private String dischargeDate = null;
	private String strDischargeTime = "";
	private String strDischargeDay = "";
	private Long lngHospAssocSeqID = null;
	private ArrayList alPrevClaimList = null;
	private ArrayList alPrevHospList = null;
	private HashMap hmPrevHospDetails = new HashMap();
	private Long lngPrevHospClaimSeqID = null;
	private String strPrevClaimNbr = "";
	private String strCodingReviewYN = "";
	private String strEmailID ="";
	private String strShowCodingOverrideYN = "";
	private String strEnrolChangeMsg = "";
	private String strShowReAssignIDYN="";
		//added for CR KOC-Decoupling
	private String strGenComplYN = "";
	//added for Critical Illness KOC-1273 
	private String strShowCriticalMsg = "";
	private String strUser = "";
	//ended
    private String strInsUnfreezeButtonYN="";//KOC 1274A  
    private String strInsDecisionyn="";//Bajaj Enhan
    private String strBufferNoteyn="";//added for hyundai buffer
    private String strBufferRestrictyn="";//added for hyundai buffer
    
    private String treatmentTypeID="";
    private String memberId="";
    private String patientName="";
    private Integer memberAge;
    private String emirateId="";
    private String encounterTypeId="";
    private String encounterFacilityId="";
    private String idPayer="";
    private String payerId="";
    private Long insSeqId;
    private String payerName="";
    private String providerName="";
    private String providerId="";
    private Long providerSeqId;
    private String providerDetails="";
    private String presentingComplaints="";
    private Long policySeqId;
    private String strRemarks="";
    private String patientGender;
    private String clinicianName;
    private String encounterStartTypeId;
    private String encounterEndTypeId;
    private String medicalOpinionRemarks;
    private String clinicianId;
    private String systemOfMedicine;
    private String accidentRelatedCase;
    private String preauthViewMode;
    private String networkProviderType;
    private String providerCountry;
    private String providerEmirate;
    private String providerArea;
    private String providerAddress;
    private String providerPobox; 
    private String providerOfficePhNo;
    private String providerOfficeFaxNo;
    private Long shortFallSeqId;
    private String letterPath="";
    private String preauthCompleteStatus;
    private String claimCompleteStatus;
    private String corporateName="";
    private String policyNumber="";
    private String benefitType="";
    private Integer gravida;
    private Integer para;
    private Integer live;
    private Integer abortion;
    private String  requestedAmountCurrency;
    private String  memberIDValidYN="";
    private String  policyType="";
    private String  duplicatePreauthAlert="";
    private String  duplicateClaimAlert="";
    private String  eligibleNetworks="";
    private String  oralstatus="";
    private String workRelatedInjury;
    private String alcoholIntoxication;
    private String roadTrafficAccident;
    private Date dateOfInjury;
    private Date billDate;
    private String patientType;
    private String additionaDeductionRemarks;
  
 private BigDecimal grossAmount;
 private BigDecimal   discountAmount;
 private BigDecimal   discountGrossAmount;
 private BigDecimal   patShareAmount;
 private BigDecimal   netAmount;
 private BigDecimal   approvedAmount;
 private BigDecimal  allowedAmount;
 private BigDecimal disAllowedAmount;
 private BigDecimal requestedAmount;
 private BigDecimal availableSuminsured;
 private BigDecimal finalApprovedAmount;
 
 private String preAuthNoStatus;
 private String validateIcdCodeYN;
 private String hiddenMemberID;
 private String hiddenHospitalID;
 
 private Long parentClaimSeqID;
 private BigDecimal availableSumInsured;
 private BigDecimal sumInsured;
 private String policyStartDate;
 private String policyEndDate;
 private String nationality;
 private String overrideRemarks;
 private String provAuthority;
 private String vipYorN;
 private String diagnosis="";
 private String revisedDiagnosis="";
 private String services="";
 private String revisedServices="";
 private BigDecimal oralApprovedAmount;
 private BigDecimal oralRevisedApprovedAmount;
 private String tempdiagnosis=null;
 private String tempservices=null;
 private BigDecimal tempAprAmt;
 private String strHospitalId ="";
 private String enablericopar;
 private String enableucr;
 private String takafulClaimRefNo;
 private String takafulYN;
 
 
 private String dhpoUploadStatus;
 private String clmMemInceptionDate;
 private String preMemInceptionDt;
 private String memCoveredAlert;
 
 
 private int memberCount;
 private int eventCount;
 
 private String providerFecilType;
 private String providerType;
 private String consultationType;
 
 private int count_flag;
 private int count_flag2;
 private String sEnrollmentId = "";
 private String memActiveStatus="";
 //private BigDecimal convertedFinalApprovedAmount;
 private String convertedFinalApprovedAmount;
 private String exitdate;
 private String allowOverrideYN;

 private String clinicianWarning;

private String currencyType;

private String completed_date;

private String preMemExitDt;

private String clmMemExitDate;
private DentalOrthoVO dentalOrthoVO;

private String patient_id; 
private String proStatusErrMsg;

private String memberWeight;
private String infoType;
private String infoCode;
private String patientdays;
private String memberDOB;
private String memberwtflag;
//private String policyStartDate;

private String providerSpecifiedPayerId;
private String providerSpecifiedPayerName;
private String overrideAllowYN;
private String copayLimitReachedActivityYN;

private String dhpoUploadedDate="";
private String eclaimDuplicateWrongAlert;
private String autodenialyn;
private String preCronTypeID;
private String preCronTypeYN;
private String productAuthority;
private String preOneMedicalCondition;
private String sum_exc_flag;
private String req_exc_flag;
private String processorInternalRemarks;
private String disableMemberId;
private String modeFlag;
private String cntFlag;
private int obsCountFlag;
private int observationFlag;
private String irdrgAltMsg ;
private BigDecimal proRequestedAmount;
private String claimSeqId;

private String presntComplaints;
private int durPresentAilment;
private String pastHistoryRelevant;
private int durPastHistRelevant;

private String claimVatAED;
private String vatAddedReqAmnt;
private String appliedVatAEDPER;
private String vatFlag;

private String dispDhpoBtn;



private ArrayList<DiagnosisDetailsVO> diagnosisList;
private ArrayList<ActivityDetailsVO> activityList;
private ArrayList<String[]> shortfallList;
private String prePedDescription;
private String preMaterinityDescription;
private String clmPedDescription;
private String clmMaterinityDescription;
private String mobileIsdCode;
private String mobileNbr;

private BigDecimal utilizeSuminsured;

public BigDecimal getUtilizeSuminsured() {
	return utilizeSuminsured;
}
public void setUtilizeSuminsured(BigDecimal utilizeSuminsured) {
	this.utilizeSuminsured = utilizeSuminsured;
}
public String getReq_exc_flag() {
	return req_exc_flag;
}
public void setReq_exc_flag(String req_exc_flag) {
	this.req_exc_flag = req_exc_flag;
}
public String getSum_exc_flag() {
	return sum_exc_flag;
}
public void setSum_exc_flag(String sum_exc_flag) {
	this.sum_exc_flag = sum_exc_flag;
}





public void setProductAuthority(String productAuthority) {
	this.productAuthority = productAuthority;
}
public String getProductAuthority() {
	return productAuthority;
}
public void setPreCronTypeYN(String preCronTypeYN) {
	this.preCronTypeYN = preCronTypeYN;
}
public String getPreCronTypeYN() {
	return preCronTypeYN;
}

private String claimOvrFlag;
private String negativeAmtFlg;


private String referenceNo;
private String documentStatus; 
private String policySuspeMsg;

private String processType = "";
private Integer preAuthCount;
private Integer clmCount;
private String paymentTo = "";
private String partnerName = "";

private BigDecimal preAuthIncurredAmt;
private String preAuthIncurredCurrencyType;

private String flagForCheckEnhancement;
private String invoiceno;
private String ailmentDuration;
private String ailmentDurationFlag;
private String relPresentIllness;
private String relevantFindings;
public String getPartnerName() {
	return partnerName;
}
public void setPartnerName(String partnerName) {
	this.partnerName = partnerName;
}
public String getProcessType() {
	return processType;
}
public void setProcessType(String processType) {
	this.processType = processType;
}
public DentalOrthoVO getDentalOrthoVO() {
	return dentalOrthoVO;
}
public void setDentalOrthoVO(DentalOrthoVO dentalOrthoVO) {
	this.dentalOrthoVO = dentalOrthoVO;
}

public void setPreCronTypeID(String preCronTypeID) {
	this.preCronTypeID = preCronTypeID;
}
public String getPreCronTypeID() {
	return preCronTypeID;
}

public String getMemberwtflag() {
	return memberwtflag;
}
public void setMemberwtflag(String memberwtflag) {
	this.memberwtflag = memberwtflag;
}
public String getMemberDOB() {
	return memberDOB;
}
public void setMemberDOB(String memberDOB) {
	this.memberDOB = memberDOB;
}


public String getPatientdays() {
	return patientdays;
}
public void setPatientdays(String patientdays) {
	this.patientdays = patientdays;
}
 
public String getMemberWeight() {
	return memberWeight;
}
public void setMemberWeight(String memberWeight) {
	this.memberWeight = memberWeight;
}
public String getInfoType() {
	return infoType;
}
public void setInfoType(String infoType) {
	this.infoType = infoType;
}
public String getInfoCode() {
	return infoCode;
}
public void setInfoCode(String infoCode) {
	this.infoCode = infoCode;
}
public void setConsultationType(String consultationType) {
	this.consultationType = consultationType;
}

public String getConsultationType() {
	return consultationType;
}

public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

 public String getMemActiveStatus() {
	return memActiveStatus;
}
public void setMemActiveStatus(String memActiveStatus) {
	this.memActiveStatus = memActiveStatus;
}
 public String getProviderFecilType() {
	return providerFecilType;
}
public void setProviderFecilType(String providerFecilType) {
	this.providerFecilType = providerFecilType;
}
public String getProviderType() {
	return providerType;
}
public void setProviderType(String providerType) {
	this.providerType = providerType;
}

 
 
 public void setAllowOverrideYN(String allowOverrideYN) {
	this.allowOverrideYN = allowOverrideYN;
}
 public String getAllowOverrideYN() {
	return allowOverrideYN;
}
 
 public void setDhpoUploadStatus(String dhpoUploadStatus) {
	this.dhpoUploadStatus = dhpoUploadStatus;
}
 

 

 public String getDhpoUploadStatus() {
	return dhpoUploadStatus;
}
 
 
 
public void setMemCoveredAlert(String memCoveredAlert) {
	this.memCoveredAlert = memCoveredAlert;
}
 public String getMemCoveredAlert() {
	return memCoveredAlert;
}
 
	public String getInsDecisionyn() {
		return strInsDecisionyn;
	}

	public void setInsDecisionyn(String strInsDecisionyn) {
		this.strInsDecisionyn = strInsDecisionyn;
	}

	public void setInsUnfreezeButtonYN(String insUnfreezeButtonYN) {
		strInsUnfreezeButtonYN = insUnfreezeButtonYN;
	}

	public String getInsUnfreezeButtonYN() {
		return strInsUnfreezeButtonYN;
	}
	//koc for griavance
	private String strSeniorCitizenYN = "";
	public void setSeniorCitizenYN(String strSeniorCitizenYN) {
		this.strSeniorCitizenYN = strSeniorCitizenYN;
	}

	public String getSeniorCitizenYN() {
		return strSeniorCitizenYN;
	}
	//koc for griavance
	/** Retrieve the EnrolChangeMsg
	 * @return Returns the strEnrolChangeMsg.
	 */
	public String getEnrolChangeMsg() {
		return strEnrolChangeMsg;
	}//end of getEnrolChangeMsg()

	/** Sets the EnrolChangeMsg
	 * @param strEnrolChangeMsg The strEnrolChangeMsg to set.
	 */
	public void setEnrolChangeMsg(String strEnrolChangeMsg) {
		this.strEnrolChangeMsg = strEnrolChangeMsg;
	}//end of setEnrolChangeMsg(String strEnrolChangeMsg)

	/** Retrieve the ShowCodingOverrideYN
	 * @return Returns the strShowCodingOverrideYN.
	 */
	public String getShowCodingOverrideYN() {
		return strShowCodingOverrideYN;
	}//end of getShowCodingOverrideYN()

	/** Sets the ShowCodingOverrideYN
	 * @param strShowCodingOverrideYN The strShowCodingOverrideYN to set.
	 */
	public void setShowCodingOverrideYN(String strShowCodingOverrideYN) {
		this.strShowCodingOverrideYN = strShowCodingOverrideYN;
	}//end of setShowCodingOverrideYN(String strShowCodingOverrideYN)

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

	/** Retrieve the PrevClaimNbr
	 * @return Returns the strPrevClaimNbr.
	 */
	public String getPrevClaimNbr() {
		return strPrevClaimNbr;
	}//end of getPrevClaimNbr()

	/** Sets the PrevClaimNbr
	 * @param strPrevClaimNbr The strPrevClaimNbr to set.
	 */
	public void setPrevClaimNbr(String strPrevClaimNbr) {
		this.strPrevClaimNbr = strPrevClaimNbr;
	}//end of setPrevClaimNbr(String strPrevClaimNbr)

	/** Retrieve the Claim Admission Date
	 * @return Returns the dtClmAdmissionDate.
	 */
	public Date getClmAdmissionDate() {
		return dtClmAdmissionDate;
	}//end of getClmAdmissionDate()
	
	/** Retrieve the Claim Admission Date
	 * @return Returns the dtClmAdmissionDate.
	 */
	public String getClmHospAdmissionDate() {
		return TTKCommon.getFormattedDate(dtClmAdmissionDate);
	}//end of getClmHospAdmissionDate()

	/** Sets the Claim Admission Date
	 * @param dtClmAdmissionDate The dtClmAdmissionDate to set.
	 */
	public void setClmAdmissionDate(Date dtClmAdmissionDate) {
		this.dtClmAdmissionDate = dtClmAdmissionDate;
	}//end of setClmAdmissionDate(Date dtClmAdmissionDate)

	/** Retrieve the Claim Admission Time
	 * @return Returns the strClmAdmissionTime.
	 */
	public String getClmAdmissionTime() {
		return strClmAdmissionTime;
	}//end of getClmAdmissionTime()

	/** Sets the Claim Admission Time
	 * @param strClmAdmissionTime The strClmAdmissionTime to set.
	 */
	public void setClmAdmissionTime(String strClmAdmissionTime) {
		this.strClmAdmissionTime = strClmAdmissionTime;
	}//end of setClmAdmissionTime(String strClmAdmissionTime)

	/** Retrieve the PrevHospClaimSeqID
	 * @return Returns the lngPrevHospClaimSeqID.
	 */
	public Long getPrevHospClaimSeqID() {
		return lngPrevHospClaimSeqID;
	}//end of getPrevHospClaimSeqID()

	/** Sets the PrevHospClaimSeqID
	 * @param lngPrevHospClaimSeqID The lngPrevHospClaimSeqID to set.
	 */
	public void setPrevHospClaimSeqID(Long lngPrevHospClaimSeqID) {
		this.lngPrevHospClaimSeqID = lngPrevHospClaimSeqID;
	}//end of setPrevHospClaimSeqID(Long lngPrevHospClaimSeqID)

	/** Retrieve the PrevHospDetails
	 * @return Returns the hmPrevHospDetails.
	 */
	public HashMap getPrevHospDetails() {
		return hmPrevHospDetails;
	}//end of getPrevHospDetails()

	/** Sets the PrevHospDetails
	 * @param hmPrevHospDetails The hmPrevHospDetails to set.
	 */
	public void setPrevHospDetails(HashMap hmPrevHospDetails) {
		this.hmPrevHospDetails = hmPrevHospDetails;
	}//end of setPrevHospDetails(HashMap hmPrevHospDetails)

	/** Retrieve the PrevHospList
	 * @return Returns the alPrevHospList.
	 */
	public ArrayList getPrevHospList() {
		return alPrevHospList;
	}//end of getPrevHospList()

	/** Sets the PrevHospList
	 * @param alPrevHospList The alPrevHospList to set.
	 */
	public void setPrevHospList(ArrayList alPrevHospList) {
		this.alPrevHospList = alPrevHospList;
	}//end of setPrevHospList(ArrayList alPrevHospList)

	/** Retrieve the PrevClaimList
	 * @return Returns the alPrevClaimList.
	 */
	public ArrayList getPrevClaimList() {
		return alPrevClaimList;
	}//end of getPrevClaimList()

	/** Sets the PrevClaimList
	 * @param alPrevClaimList The alPrevClaimList to set.
	 */
	public void setPrevClaimList(ArrayList alPrevClaimList) {
		this.alPrevClaimList = alPrevClaimList;
	}//end of setPrevClaimList(ArrayList alPrevClaimList)

	/** Retrieve the Hospital Associate SeqID
	 * @return Returns the lngHospAssocSeqID.
	 */
	public Long getHospAssocSeqID() {
		return lngHospAssocSeqID;
	}//end of getHospAssocSeqID()
	
	/** Sets the Hospital Associate SeqID
	 * @param lngHospAssocSeqID The lngHospAssocSeqID to set.
	 */
	public void setHospAssocSeqID(Long lngHospAssocSeqID) {
		this.lngHospAssocSeqID = lngHospAssocSeqID;
	}//end of setHospAssocSeqID(Long lngHospAssocSeqID)
	
	/** Retrieve the Discharge Date
	 * @return Returns the dtDischargeDate.
	 */
	
	
	/** Retrieve the Discharge Date
	 * @return Returns the dtDischargeDate.
	 */
	
	/** Sets the Discharge Date
	 * @param dtDischargeDate The dtDischargeDate to set.
	 */
	
	/** Retrieve the Discharge Day
	 * @return Returns the strDischargeDay.
	 */
	public String getDischargeDay() {
		return strDischargeDay;
	}//end of getDischargeDay()

	/** Sets the Discharge Day
	 * @param strDischargeDay The strDischargeDay to set.
	 */
	public void setDischargeDay(String strDischargeDay) {
		this.strDischargeDay = strDischargeDay;
	}//end of setDischargeDay(String strDischargeDay)

	/** Retrieve the Discharge Time
	 * @return Returns the strDischargeTime.
	 */
	public String getDischargeTime() {
		return strDischargeTime;
	}//end of getDischargeTime()

	/** Sets the Discharge Time
	 * @param strDischargeTime The strDischargeTime to set.
	 */
	public void setDischargeTime(String strDischargeTime) {
		this.strDischargeTime = strDischargeTime;
	}//end of setDischargeTime(String strDischargeTime)
	
    /** Retrieve the PreauthTypeDesc
	 * @return Returns the strPreauthTypeDesc.
	 */
	public String getPreauthTypeDesc() {
		return strPreauthTypeDesc;
	}//end of getPreauthTypeDesc()

	/** Sets the PreauthTypeDesc
	 * @param strPreauthTypeDesc The strPreauthTypeDesc to set.
	 */
	public void setPreauthTypeDesc(String strPreauthTypeDesc) {
		this.strPreauthTypeDesc = strPreauthTypeDesc;
	}//end of setPreauthTypeDesc(String strPreauthTypeDesc)

	/** Retrieve the ProcessingBranch
	 * @return Returns the strProcessingBranch.
	 */
	public String getProcessingBranch() {
		return strProcessingBranch;
	}//end of getProcessingBranch()

	/** Sets the ProcessingBranch
	 * @param strProcessingBranch The strProcessingBranch to set.
	 */
	public void setProcessingBranch(String strProcessingBranch) {
		this.strProcessingBranch = strProcessingBranch;
	}//end of setProcessingBranch(String strProcessingBranch)

	/** Retrieve the ApprovedAmt
	 * @return Returns the bdApprovedAmt.
	 */
	public BigDecimal getApprovedAmt() {
		return bdApprovedAmt;
	}//end of getApprovedAmt()

	/** Sets the ApprovedAmt
	 * @param bdApprovedAmt The bdApprovedAmt to set.
	 */
	public void setApprovedAmt(BigDecimal bdApprovedAmt) {
		this.bdApprovedAmt = bdApprovedAmt;
	}//end of setApprovedAmt(BigDecimal bdApprovedAmt)

	/** Retrieve the ClaimDetailVO
	 * @return Returns the claimDetailVO.
	 */
	public ClaimDetailVO getClaimDetailVO() {
		return claimDetailVO;
	}//end of getClaimDetailVO()

	/** Sets the ClaimDetailVO
	 * @param claimDetailVO The claimDetailVO to set.
	 */
	public void setClaimDetailVO(ClaimDetailVO claimDetailVO) {
		this.claimDetailVO = claimDetailVO;
	}//end of setClaimDetailVO(ClaimDetailVO claimDetailVO)

	/** Retrieve the Discrepancy Present YN
	 * @return Returns the strDiscPresentYN.
	 */
	public String getDiscPresentYN() {
		return strDiscPresentYN;
	}//end of getDiscPresentYN()

	/** Sets the Discrepancy Present YN
	 * @param strDiscPresentYN The strDiscPresentYN to set.
	 */
	public void setDiscPresentYN(String strDiscPresentYN) {
		this.strDiscPresentYN = strDiscPresentYN;
	}//end of setDiscPresentYN(String strDiscPresentYN)

	/** Retrieve the Event Name
	 * @return Returns the strEventName.
	 */
	public String getEventName() {
		return strEventName;
	}//end of getEventName()

	/** Sets the Event Name
	 * @param strEventName The strEventName to set.
	 */
	public void setEventName(String strEventName) {
		this.strEventName = strEventName;
	}//end of setEventName(String strEventName)

	/** Retrieve the Process Completed YN
	 * @return Returns the strProcessCompletedYN.
	 */
	public String getProcessCompletedYN() {
		return strProcessCompletedYN;
	}//end of 

	/** Sets the Process Completed YN
	 * @param strProcessCompletedYN The strProcessCompletedYN to set.
	 */
	public void setProcessCompletedYN(String strProcessCompletedYN) {
		this.strProcessCompletedYN = strProcessCompletedYN;
	}//end of setProcessCompletedYN(String strProcessCompletedYN)

	/** Retrieve the Conflict Type ID
	 * @return Returns the strConflictTypeID.
	 */
	public String getConflictTypeID() {
		return strConflictTypeID;
	}//end of getConflictTypeID()

	/** Sets the Conflict Type ID
	 * @param strConflictTypeID The strConflictTypeID to set.
	 */
	public void setConflictTypeID(String strConflictTypeID) {
		this.strConflictTypeID = strConflictTypeID;
	}//end of setConflictTypeID(String strConflictTypeID)

	/** Retrieve the Completed YN
	 * @return Returns the strCompletedYN.
	 */
	public String getCompletedYN() {
		return strCompletedYN;
	}//end of getCompletedYN()

	/** Sets the Completed YN
	 * @param strCompletedYN The strCompletedYN to set.
	 */
	public void setCompletedYN(String strCompletedYN) {
		this.strCompletedYN = strCompletedYN;
	}//end of setCompletedYN(String strCompletedYN)

	/** Retrieve the PreAuth Enhanced YN
	 * @return Returns the strPreAuthEnhancedYN.
	 */
	public String getPreAuthEnhancedYN() {
		return strPreAuthEnhancedYN;
	}//end of getPreAuthEnhancedYN()

	/** Sets the PreAuth Enhanced YN
	 * @param strPreAuthEnhancedYN The strPreAuthEnhancedYN to set.
	 */
	public void setPreAuthEnhancedYN(String strPreAuthEnhancedYN) {
		this.strPreAuthEnhancedYN = strPreAuthEnhancedYN;
	}//end of setPreAuthEnhancedYN(String strPreAuthEnhancedYN)

	/** Retrieve the Data Discrepany Present YN
	 * @return Returns the strDataDiscPresentYN.
	 */
	public String getDataDiscPresentYN() {
		return strDataDiscPresentYN;
	}//end of getDataDiscPresentYN()

	/** Sets the Data Discrepancy Present YN
	 * @param strDataDiscPresentYN The strDataDiscPresentYN to set.
	 */
	public void setDataDiscPresentYN(String strDataDiscPresentYN) {
		this.strDataDiscPresentYN = strDataDiscPresentYN;
	}//end of setDataDiscPresentYN(String strDataDiscPresentYN)

	/**
     * Retrieve the Close Proximity indicator
     * @return  strCloseProximityYN String
     */
    public String getCloseProximityYN() {
        return strCloseProximityYN;
    }//end of getCloseProximityYN()
    
    /**
     * Sets the Close Proximity indicator
     * @param  strCloseProximityYN String  
     */
    public void setCloseProximityYN(String strCloseProximityYN) {
        this.strCloseProximityYN = strCloseProximityYN;
    }//end of setCloseProximityYN(String strCloseProximityYN)
    
    /** Retrieve the AdditionalDetailVO
	 * @return Returns the additionalDetailVO.
	 */
	public AdditionalDetailVO getAdditionalDetailVO() {
		return additionalDetailVO;
	}//end of getAdditionalDetailVO()

	/** Sets the AdditionalDetailVO
	 * @param additionalDetailVO The additionalDetailVO to set.
	 */
	public void setAdditionalDetailVO(AdditionalDetailVO additionalDetailVO) {
		this.additionalDetailVO = additionalDetailVO;
	}//end of setAdditionalDetailVO(AdditionalDetailVO additionalDetailVO)
    
    /** Retrieve the Proximity Date
     * @return Returns the dtProximityDate.
     */
    public Date getProximityDate() {
        return dtProximityDate;
    }//end of getProximityDate()
    
    /** Sets the Proximity Date
     * @param dtProximityDate The dtProximityDate to set.
     */
    public void setProximityDate(Date dtProximityDate) {
        this.dtProximityDate = dtProximityDate;
    }//end of setProximityDate(Date dtProximityDate)
    
    /** Retrieve the Proximity Status Type ID
     * @return Returns the strProximityStatusTypeID.
     */
    public String getProximityStatusTypeID() {
        return strProximityStatusTypeID;
    }//end of getProximityStatusTypeID()
    
    /** Sets the Proximity Status Type ID
     * @param strProximityStatusTypeID The strProximityStatusTypeID to set.
     */
    public void setProximityStatusTypeID(String strProximityStatusTypeID) {
        this.strProximityStatusTypeID = strProximityStatusTypeID;
    }//end of setProximityStatusTypeID(String strProximityStatusTypeID)
    
    /** Retrieve the Review
     * @return Returns the strReview.
     */
    public String getReview() {
        return strReview;
    }//end of getReview()
    
    /** Sets the Review
     * @param strReview The strReview to set.
     */
    public void setReview(String strReview) {
        this.strReview = strReview;
    }//end of setReview(String strReview)
    
    /** Retrieve the Required Review Count
     * @return Returns the intRequiredReviewCnt.
     */
    public Integer getRequiredReviewCnt() {
        return intRequiredReviewCnt;
    }//end of getRequiredReviewCnt()
    
    /** Sets the Required Review Count
     * @param intRequiredReviewCnt The intRequiredReviewCnt to set.
     */
    public void setRequiredReviewCnt(Integer intRequiredReviewCnt) {
        this.intRequiredReviewCnt = intRequiredReviewCnt;
    }//end of setRequiredReviewCnt(Integer intRequiredReviewCnt)
    
    /** Retrieve the Review Count
     * @return Returns the intReviewCount.
     */
    public Integer getReviewCount() {
        return intReviewCount;
    }//end of getReviewCount()
    
    /** Sets the Review Count
     * @param intReviewCount The intReviewCount to set.
     */
    public void setReviewCount(Integer intReviewCount) {
        this.intReviewCount = intReviewCount;
    }//end of setReviewCount(Integer intReviewCount)
    
    /** Retriev the Event Seq ID
     * @return Returns the lngEventSeqID.
     */
    public Long getEventSeqID() {
        return lngEventSeqID;
    }//end of getEventSeqID()
    
    /** Sets the Event Seq ID
     * @param lngEventSeqID The lngEventSeqID to set.
     */
    public void setEventSeqID(Long lngEventSeqID) {
        this.lngEventSeqID = lngEventSeqID;
    }//end of setEventSeqID(Long lngEventSeqID)
    
    /**
     * Retrieve the Received Time
     * @return  strReceivedTime String
     */
    public String getReceivedTime() {
        return strReceivedTime;
    }//end of getReceivedTime()
    
    /**
     * Sets the Received Time
     * @param  strReceivedTime String  
     */
    public void setReceivedTime(String strReceivedTime) {
        this.strReceivedTime = strReceivedTime;
    }//end of setReceivedTime(String strReceivedTime)
    
    /**
     * Retrieve the Received Day
     * @return  strReceivedDay String
     */
    public String getReceivedDay() {
        return strReceivedDay;
    }//end of getReceivedDay()
    
    /**
     * Sets the Received Day
     * @param  strReceivedDay String  
     */
    public void setReceivedDay(String strReceivedDay) {
        this.strReceivedDay = strReceivedDay;
    }//end of setReceivedDay(String strReceivedDay)
    
    /**
     * Retrieve the Admission Day
     * @return  strAdmissionDay String
     */
    public String getAdmissionDay() {
        return strAdmissionDay;
    }//end of getAdmissionDay()
    
    /**
     * Sets the Admission Day
     * @param  strAdmissionDay String  
     */
    public void setAdmissionDay(String strAdmissionDay) {
        this.strAdmissionDay = strAdmissionDay;
    }//end of setAdmissionDay(String strAdmissionDay) 
    
    /**
     * Retrieve the Admission Time
     * @return  strAdmissionTime String
     */
    public String getAdmissionTime() {
        return strAdmissionTime;
    }//end of getAdmissionTime() 
    
    /**
     * Sets the Admission Time
     * @param  strAdmissionTime String  
     */
    public void setAdmissionTime(String strAdmissionTime) {
        this.strAdmissionTime = strAdmissionTime;
    }//end of setAdmissionTime(String strAdmissionTime)
    
    /**
     * Retrieve the Previous Approved Amount
     * @return  bdPrevApprovedAmount BigDecimal
     */
    public BigDecimal getPrevApprovedAmount() {
        return bdPrevApprovedAmount;
    }//end of getPrevApprovedAmount()
    
    /**
     * Sets the Previous Approved Amount
     * @param  bdPrevApprovedAmount BigDecimal  
     */
    public void setPrevApprovedAmount(BigDecimal bdPrevApprovedAmount) {
        this.bdPrevApprovedAmount = bdPrevApprovedAmount;
    }//end of setPrevApprovedAmount(BigDecimal bdPrevApprovedAmount)
    
    
    
    /**
     * Retrieve  claimantVO object which contains members information
     * @return  claimantVO ClaimantVO
     */
    public ClaimantVO getClaimantVO() {
        return claimantVO;
    }//end of getClaimantVO()
    
    /**
     * Sets the claimantVO object which contains members information
     * @param  claimantVO ClaimantVO  
     */
    public void setClaimantVO(ClaimantVO claimantVO) {
        this.claimantVO = claimantVO;
    }//end of setClaimantVO(ClaimantVO claimantVO)
    
    /**
     * Retrieve the Admission Date
     * @return  dtAdmissionDate Date
     */
    public Date getAdmissionDate() {
        return dtAdmissionDate;
    }//end of getAdmissionDate()
    
    /**
     * Retrieve the Admission Date
     * @return  dtAdmissionDate Date
     */
    public String getHospitalAdmissionDate() {
        return TTKCommon.getFormattedDate(dtAdmissionDate);
    }//end of getHospitalAdmissionDate()
    
    /**
     * Retrieve the Admission Date
     * @return  dtAdmissionDate Date
     */
    public String getAuthAdmissionDate() {
        return TTKCommon.getFormattedDateHour(dtAdmissionDate);
    }//end of getAuthAdmissionDate()
    
    /**
     * Sets the Admission Date
     * @param  dtAdmissionDate Date  
     */
    public void setAdmissionDate(Date dtAdmissionDate) {
        this.dtAdmissionDate = dtAdmissionDate;
    }//end of setAdmissionDate(Date dtAdmissionDate)
    
    /**
     * Retrieve the preAuthHospitalVO object Which contains 
     * hospital information for which member admitted.
     * @return  preAuthHospitalVO PreAuthHospitalVO
     */
    public PreAuthHospitalVO getPreAuthHospitalVO() {
        return preAuthHospitalVO;
    }//end of getPreAuthHospitalVO()
    
    /**
     * Sets the the preAuthHospitalVO object Which contains 
     * hospital information for which member admitted.
     * @param  preAuthHospitalVO PreAuthHospitalVO  
     */
    public void setPreAuthHospitalVO(PreAuthHospitalVO preAuthHospitalVO) {
        this.preAuthHospitalVO = preAuthHospitalVO;
    }//end of setPreAuthHospitalVO(PreAuthHospitalVO preAuthHospitalVO)
    
    /**
     * Retrieve the Doctor's Name
     * @return  strDoctorName String
     */
    public String getDoctorName() {
        return strDoctorName;
    }//end of getDoctorName()
    
    /**
     * Sets the Doctor's Name
     * @param  strDoctorName String  
     */
    public void setDoctorName(String strDoctorName) {
        this.strDoctorName = strDoctorName;
    }//end of setDoctorName(String strDoctorName)
    
    /**
     * Retrieve the Hospitalized Type ID
     * @return  strHospitalizedTypeID String
     */
    public String getHospitalizedTypeID() {
        return strHospitalizedTypeID;
    }//end of getHospitalizedTypeID()
    
    /**
     * Sets the Hospitalized Type ID
     * @param  strHospitalizedTypeID String  
     */
    public void setHospitalizedTypeID(String strHospitalizedTypeID) {
        this.strHospitalizedTypeID = strHospitalizedTypeID;
    }//end of setHospitalizedTypeID(String strHospitalizedTypeID)
    
    /**
     * Retrieve the InvestigationReqdYN
     * @return  strInvestigationReqdYN String
     */
    public String getInvestigationReqdYN() {
        return strInvestigationReqdYN;
    }//end of getInvestigationReqdYN()
    /**
     * Sets the InvestigationReqdYN
     * @param  strInvestigationReqdYN String  
     */
    public void setInvestigationReqdYN(String strInvestigationReqdYN) {
        this.strInvestigationReqdYN = strInvestigationReqdYN;
    }//end of setInvestigationReqdYN(String strInvestigationReqdYN)
    
    /**
     * Retrieve the Phone NO
     * @return  strHospitalPhone String
     */
    public String getHospitalPhone() {
        return strHospitalPhone;
    }//end of getHospitalPhone()
    
    /**
     * Sets the Phone NO
     * @param  strHospitalPhone String  
     */
    public void setHospitalPhone(String strHospitalPhone) {
        this.strHospitalPhone = strHospitalPhone;
    }//end of setHospitalPhone(String strHospitalPhone)
    
    /**
     * Retrieve the PreAuthReceived Type ID
     * @return  strPreAuthRecvTypeID String
     */
    public String getPreAuthRecvTypeID() {
        return strPreAuthRecvTypeID;
    }//end of getPreAuthRecvTypeID()
    
    /**
     * Sets the PreAuthReceived Type ID
     * @param  strPreAuthRecvTypeID String  
     */
    public void setPreAuthRecvTypeID(String strPreAuthRecvTypeID) {
        this.strPreAuthRecvTypeID = strPreAuthRecvTypeID;
    }//end of setPreAuthRecvTypeID(String strPreAuthRecvTypeID)
    
    /**
     * Retrieve the Remarks
     * @return  strRemarks String
     */
    public String getRemarks() {
        return strRemarks;
    }//end of getRemarks()
    
    /**
     * Sets the Remarks
     * @param  strRemarks String  
     */
    public void setRemarks(String strRemarks) {
        this.strRemarks = strRemarks;
    }//end of setRemarks(String strRemarks)

	/** Retrieve the ClaimRequestAmount
	 * @return Returns the bdClaimRequestAmount.
	 */
	public BigDecimal getClaimRequestAmount() {
		return bdClaimRequestAmount;
	}//end of getClaimRequestAmount()

	/** Sets the ClaimRequestAmount
	 * @param bdClaimRequestAmount The bdClaimRequestAmount to set.
	 */
	public void setClaimRequestAmount(BigDecimal bdClaimRequestAmount) {
		this.bdClaimRequestAmount = bdClaimRequestAmount;
	}//end of setClaimRequestAmount(BigDecimal bdClaimRequestAmount)

	/** Retrieve the CodingReviewYN
	 * @return Returns the strCodingReviewYN.
	 */
	public String getCodingReviewYN() {
		return strCodingReviewYN;
	}//end of getCodingReviewYN()

	/** Sets the CodingReviewYN
	 * @param strCodingReviewYN The strCodingReviewYN to set.
	 */
	public void setStrCodingReviewYN(String strCodingReviewYN) {
		this.strCodingReviewYN = strCodingReviewYN;
	}//end of setStrCodingReviewYN(String strCodingReviewYN)
	/** Retrieve the ShowReAssignIDYN
     * @return Returns the strShowReAssignIDYN.
     */
    public String getShowReAssignIDYN() {
        return strShowReAssignIDYN;
    }//end of getShowReAssignIDYN()

    /** Sets the ShowReAssignIDYN
     * @param strShowReAssignIDYN The strShowReAssignIDYN to set.
     */
    public void setShowReAssignIDYN(String strShowReAssignIDYN) {
        this.strShowReAssignIDYN = strShowReAssignIDYN;
    }//end of setShowReAssignIDYN(String strShowReAssignIDYN)
	//added for Document Viewer KOC-1267
	public String getUser() {
		return strUser;
	}

	public void setUser(String strUser) {
		this.strUser = strUser;
	}
	//Ended for Document Viewer
	public void setShowCriticalMsg(String strShowCriticalMsg) {
		this.strShowCriticalMsg = strShowCriticalMsg;
	}

	public String getShowCriticalMsg() {
		return strShowCriticalMsg;
	}

	public String getBufferNoteyn() {
		return strBufferNoteyn;
	}

	public void setBufferNoteyn(String strBufferNoteyn) {
		this.strBufferNoteyn = strBufferNoteyn;
	}

	public String getBufferRestrictyn() {
		return strBufferRestrictyn;
	}

	public void setBufferRestrictyn(String strBufferRestrictyn) {
		this.strBufferRestrictyn = strBufferRestrictyn;
	}
	//added for CR KOC-Decoupling
	public void setGenComplYN(String strGenComplYN) {
		this.strGenComplYN = strGenComplYN;
	}

	public String getGenComplYN() {
		return strGenComplYN;
	}
	//ended

	public String getTreatmentTypeID() {
		return treatmentTypeID;
	}

	public void setTreatmentTypeID(String treatmentTypeID) {
		this.treatmentTypeID = treatmentTypeID;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Integer getMemberAge() {
		return memberAge;
	}

	public void setMemberAge(Integer memberAge) {
		this.memberAge = memberAge;
	}

	public String getEmirateId() {
		return emirateId;
	}

	public void setEmirateId(String emirateId) {
		this.emirateId = emirateId;
	}

	public String getEncounterTypeId() {
		return encounterTypeId;
	}

	public void setEncounterTypeId(String encounterTypeId) {
		this.encounterTypeId = encounterTypeId;
	}

	public String getEncounterFacilityId() {
		return encounterFacilityId;
	}

	public void setEncounterFacilityId(String encounterFacilityId) {
		this.encounterFacilityId = encounterFacilityId;
	}

	public String getIdPayer() {
		return idPayer;
	}

	public void setIdPayer(String idPayer) {
		this.idPayer = idPayer;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderDetails() {
		return providerDetails;
	}

	public void setProviderDetails(String providerDetails) {
		this.providerDetails = providerDetails;
	}

	public String getPresentingComplaints() {
		return presentingComplaints;
	}

	public void setPresentingComplaints(String presentingComplaints) {
		this.presentingComplaints = presentingComplaints;
	}

	public Long getProviderSeqId() {
		return providerSeqId;
	}

	public void setProviderSeqId(Long providerSeqId) {
		this.providerSeqId = providerSeqId;
	}

	public Long getInsSeqId() {
		return insSeqId;
	}

	public void setInsSeqId(Long insSeqId) {
		this.insSeqId = insSeqId;
	}

	public Long getPolicySeqId() {
		return policySeqId;
	}

	public void setPolicySeqId(Long policySeqId) {
		this.policySeqId = policySeqId;
	}

	public DiagnosisDetailsVO getDiagnosisDetailsVO() {
		return diagnosisDetailsVO;
	}

	public void setDiagnosisDetailsVO(DiagnosisDetailsVO diagnosisDetailsVO) {
		this.diagnosisDetailsVO = diagnosisDetailsVO;
	}

	public ActivityDetailsVO getActivityDetailsVO() {
		return activityDetailsVO;
	}

	public void setActivityDetailsVO(ActivityDetailsVO activityDetailsVO) {
		this.activityDetailsVO = activityDetailsVO;
	}

	public Date getActivityStartDate() {
		return activityStartDate;
	}

	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getReceiveDay() {
		return receiveDay;
	}

	public void setReceiveDay(String receiveDay) {
		this.receiveDay = receiveDay;
	}

	public String getAuthNum() {
		return authNum;
	}

	public void setAuthNum(String authNum) {
		this.authNum = authNum;
	}

	public String getIcdCode() {
		return icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	public Long getIcdCodeSeqId() {
		return icdCodeSeqId;
	}

	public void setIcdCodeSeqId(Long icdCodeSeqId) {
		this.icdCodeSeqId = icdCodeSeqId;
	}

	public String getAilmentDescription() {
		return ailmentDescription;
	}

	public void setAilmentDescription(String ailmentDescription) {
		this.ailmentDescription = ailmentDescription;
	}

	public String getPrimaryAilment() {
		return primaryAilment;
	}

	public void setPrimaryAilment(String primaryAilment) {
		this.primaryAilment = primaryAilment;
	}

	public Long getParentPreAuthSeqID() {
		return parentPreAuthSeqID;
	}

	public void setParentPreAuthSeqID(Long parentPreAuthSeqID) {
		this.parentPreAuthSeqID = parentPreAuthSeqID;
	}

	public Long getDiagSeqId() {
		return diagSeqId;
	}

	public void setDiagSeqId(Long diagSeqId) {
		this.diagSeqId = diagSeqId;
	}

	public BigDecimal getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getDiscountGrossAmount() {
		return discountGrossAmount;
	}

	public void setDiscountGrossAmount(BigDecimal discountGrossAmount) {
		this.discountGrossAmount = discountGrossAmount;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public BigDecimal getPatShareAmount() {
		return patShareAmount;
	}

	public void setPatShareAmount(BigDecimal patShareAmount) {
		this.patShareAmount = patShareAmount;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}


	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public String getClinicianName() {
		return clinicianName;
	}

	public void setClinicianName(String clinicianName) {
		this.clinicianName = clinicianName;
	}

	public String getEncounterStartTypeId() {
		return encounterStartTypeId;
	}

	public void setEncounterStartTypeId(String encounterStartTypeId) {
		this.encounterStartTypeId = encounterStartTypeId;
	}

	public String getEncounterEndTypeId() {
		return encounterEndTypeId;
	}

	public void setEncounterEndTypeId(String encounterEndTypeId) {
		this.encounterEndTypeId = encounterEndTypeId;
	}

	public BigDecimal getAllowedAmount() {
		return allowedAmount;
	}

	public void setAllowedAmount(BigDecimal allowedAmount) {
		this.allowedAmount = allowedAmount;
	}

	public String getMedicalOpinionRemarks() {
		return medicalOpinionRemarks;
	}

	public void setMedicalOpinionRemarks(String medicalOpinionRemarks) {
		this.medicalOpinionRemarks = medicalOpinionRemarks;
	}

	public String getClinicianId() {
		return clinicianId;
	}

	public void setClinicianId(String clinicianId) {
		this.clinicianId = clinicianId;
	}

	public String getSystemOfMedicine() {
		return systemOfMedicine;
	}

	public void setSystemOfMedicine(String systemOfMedicine) {
		this.systemOfMedicine = systemOfMedicine;
	}

	public String getAccidentRelatedCase() {
		return accidentRelatedCase;
	}

	public void setAccidentRelatedCase(String accidentRelatedCase) {
		this.accidentRelatedCase = accidentRelatedCase;
	}

	public String getPreauthViewMode() {
		
		return preauthViewMode==null?"false":preauthViewMode;
	}

	public void setPreauthViewMode(String preauthViewMode) {
		this.preauthViewMode = preauthViewMode;
	}
	public String getProviderPobox() {
		return providerPobox;
	}

	public void setProviderPobox(String providerPobox) {
		this.providerPobox = providerPobox;
	}

	public String getProviderAddress() {
		return providerAddress;
	}

	public void setProviderAddress(String providerAddress) {
		this.providerAddress = providerAddress;
	}

	public String getProviderArea() {
		return providerArea;
	}

	public void setProviderArea(String providerArea) {
		this.providerArea = providerArea;
	}

	public String getProviderEmirate() {
		return providerEmirate;
	}

	public void setProviderEmirate(String providerEmirate) {
		this.providerEmirate = providerEmirate;
	}

	public String getProviderCountry() {
		return providerCountry;
	}

	public void setProviderCountry(String providerCountry) {
		this.providerCountry = providerCountry;
	}

	public String getNetworkProviderType() {
		return networkProviderType;
	}

	public void setNetworkProviderType(String networkProviderType) {
		this.networkProviderType = networkProviderType;
	}

	public Long getActivityDtlSeqId() {
		return activityDtlSeqId;
	}

	public void setActivityDtlSeqId(Long activityDtlSeqId) {
		this.activityDtlSeqId = activityDtlSeqId;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(String dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public Long getShortFallSeqId() {
		return shortFallSeqId;
	}

	public void setShortFallSeqId(Long shortFallSeqId) {
		this.shortFallSeqId = shortFallSeqId;
	}

	public String getLetterPath() {
		return letterPath;
	}

	public void setLetterPath(String letterPath) {
		this.letterPath = letterPath;
	}

	public String getClinicianStatus() {
		return clinicianStatus;
	}

	public void setClinicianStatus(String clinicianStatus) {
		this.clinicianStatus = clinicianStatus;
	}

	public String getPreauthCompleteStatus() {
		return preauthCompleteStatus;
	}

	public void setPreauthCompleteStatus(String preauthCompleteStatus) {
		this.preauthCompleteStatus = preauthCompleteStatus;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getBenefitType() {
		return benefitType;
	}

	public void setBenefitType(String benefitType) {
		this.benefitType = benefitType;
	}

	public Integer getGravida() {
		return gravida;
	}

	public void setGravida(Integer gravida) {
		this.gravida = gravida;
	}

	public Integer getAbortion() {
		return abortion;
	}

	public void setAbortion(Integer abortion) {
		this.abortion = abortion;
	}

	public Integer getLive() {
		return live;
	}

	public void setLive(Integer live) {
		this.live = live;
	}

	public Integer getPara() {
		return para;
	}

	public void setPara(Integer para) {
		this.para = para;
	}

	public String getPreAuthNoStatus() {
		return preAuthNoStatus;
	}

	public void setPreAuthNoStatus(String preAuthNoStatus) {
		this.preAuthNoStatus = preAuthNoStatus;
	}

	public String getRequestedAmountCurrency() {
		return requestedAmountCurrency;
	}

	public void setRequestedAmountCurrency(String requestedAmountCurrency) {
		this.requestedAmountCurrency = requestedAmountCurrency;
	}

	public Long getParentClaimSeqID() {
		return parentClaimSeqID;
	}

	public void setParentClaimSeqID(Long parentClaimSeqID) {
		this.parentClaimSeqID = parentClaimSeqID;
	}

	public BigDecimal getAvailableSuminsured() {
		return availableSuminsured;
	}

	public void setAvailableSuminsured(BigDecimal availableSuminsured) {
		this.availableSuminsured = availableSuminsured;
	}

	public String getProviderOfficePhNo() {
		return providerOfficePhNo;
	}

	public void setProviderOfficePhNo(String providerOfficePhNo) {
		this.providerOfficePhNo = providerOfficePhNo;
	}

	public String getProviderOfficeFaxNo() {
		return providerOfficeFaxNo;
	}

	public void setProviderOfficeFaxNo(String providerOfficeFaxNo) {
		this.providerOfficeFaxNo = providerOfficeFaxNo;
	}

	public String getValidateIcdCodeYN() {
		return validateIcdCodeYN;
	}

	public void setValidateIcdCodeYN(String validateIcdCodeYN) {
		this.validateIcdCodeYN = validateIcdCodeYN;
	}

	public BigDecimal getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(BigDecimal sumInsured) {
		this.sumInsured = sumInsured;
	}

	public BigDecimal getAvailableSumInsured() {
		return availableSumInsured;
	}

	public void setAvailableSumInsured(BigDecimal availableSumInsured) {
		this.availableSumInsured = availableSumInsured;
	}

	public String getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(String policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public String getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(String policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public BigDecimal getDisAllowedAmount() {
		return disAllowedAmount;
	}

	public void setDisAllowedAmount(BigDecimal disAllowedAmount) {
		this.disAllowedAmount = disAllowedAmount;
	}

	public String getMemberIDValidYN() {
		return memberIDValidYN;
	}

	public void setMemberIDValidYN(String memberIDValidYN) {
		this.memberIDValidYN = memberIDValidYN;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getHiddenMemberID() {
		return hiddenMemberID;
	}

	public void setHiddenMemberID(String hiddenMemberID) {
		this.hiddenMemberID = hiddenMemberID;
	}

	public String getHiddenHospitalID() {
		return hiddenHospitalID;
	}

	public void setHiddenHospitalID(String hiddenHospitalID) {
		this.hiddenHospitalID = hiddenHospitalID;
	}

	public String getClaimCompleteStatus() {
		return claimCompleteStatus;
	}

	public void setClaimCompleteStatus(String claimCompleteStatus) {
		this.claimCompleteStatus = claimCompleteStatus;
	}

	public String getClaimSubmissionType() {
		return claimSubmissionType;
	}

	public void setClaimSubmissionType(String claimSubmissionType) {
		this.claimSubmissionType = claimSubmissionType;
	}

	public String getDuplicatePreauthAlert() {
		return duplicatePreauthAlert;
	}

	public void setDuplicatePreauthAlert(String duplicatePreauthAlert) {
		this.duplicatePreauthAlert = duplicatePreauthAlert;
	}

	public BigDecimal getFinalApprovedAmount() {
		return finalApprovedAmount;
	}

	public void setFinalApprovedAmount(BigDecimal finalApprovedAmount) {
		this.finalApprovedAmount = finalApprovedAmount;
	}

	public String getClaimFrom() {
		return claimFrom;
	}

	public void setClaimFrom(String claimFrom) {
		this.claimFrom = claimFrom;
	}

	public String getFinalRemarks() {
		return finalRemarks;
	}

	public void setFinalRemarks(String finalRemarks) {
		this.finalRemarks = finalRemarks;
	}

	public String getDuplicateClaimAlert() {
		return duplicateClaimAlert;
	}

	public void setDuplicateClaimAlert(String duplicateClaimAlert) {
		this.duplicateClaimAlert = duplicateClaimAlert;
	}

	public String getOverrideRemarks() {
		return overrideRemarks;
	}

	public void setOverrideRemarks(String overrideRemarks) {
		this.overrideRemarks = overrideRemarks;
	}

	public String getProvAuthority() {
		return provAuthority;
	}

	public void setProvAuthority(String provAuthority) {
		this.provAuthority = provAuthority;
	}

	public String getEligibleNetworks() {
		return eligibleNetworks;
	}

	public void setEligibleNetworks(String eligibleNetworks) {
		this.eligibleNetworks = eligibleNetworks;
	}

	public String getVipYorN() {
		return vipYorN;
	}

	public void setVipYorN(String vipYorN) {
		this.vipYorN = vipYorN;
	}
	

	public String getClmMemInceptionDate() {
		return clmMemInceptionDate;
	}

	public void setClmMemInceptionDate(String clmMemInceptionDate) {
		this.clmMemInceptionDate = clmMemInceptionDate;
	}

	public String getTakafulClaimRefNo() {
		return takafulClaimRefNo;
	}

	public void setTakafulClaimRefNo(String takafulClaimRefNo) {
		this.takafulClaimRefNo = takafulClaimRefNo;
	}

	public String getTakafulYN() {
		return takafulYN;
	}

	public void setTakafulYN(String takafulYN) {
		this.takafulYN = takafulYN;
	}

	
	

	public String getOralstatus() {
		return oralstatus;
	}

	public void setOralstatus(String oralstatus) {
		this.oralstatus = oralstatus;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getRevisedDiagnosis() {
		return revisedDiagnosis;
	}

	public void setRevisedDiagnosis(String revisedDiagnosis) {
		this.revisedDiagnosis = revisedDiagnosis;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getRevisedServices() {
		return revisedServices;
	}

	public void setRevisedServices(String revisedServices) {
		this.revisedServices = revisedServices;
	}

	public BigDecimal getOralApprovedAmount() {
		return oralApprovedAmount;
	}

	public void setOralApprovedAmount(BigDecimal oralApprovedAmount) {
		this.oralApprovedAmount = oralApprovedAmount;
	}

	public BigDecimal getOralRevisedApprovedAmount() {
		return oralRevisedApprovedAmount;
	}

	public void setOralRevisedApprovedAmount(BigDecimal oralRevisedApprovedAmount) {
		this.oralRevisedApprovedAmount = oralRevisedApprovedAmount;
	}

	public String getPreMemInceptionDt() {
		return preMemInceptionDt;
	}

	public void setPreMemInceptionDt(String preMemInceptionDt) {
		this.preMemInceptionDt = preMemInceptionDt;
	}

	public String getTempdiagnosis() {
		return tempdiagnosis;
	}

	public void setTempdiagnosis(String tempdiagnosis) {
		this.tempdiagnosis = tempdiagnosis;
	}

	public String getTempservices() {
		return tempservices;
	}

	public void setTempservices(String tempservices) {
		this.tempservices = tempservices;
	}

	public BigDecimal getTempAprAmt() {
		return tempAprAmt;
	}

	public void setTempAprAmt(BigDecimal tempAprAmt) {
		this.tempAprAmt = tempAprAmt;
	}

	public String getStrHospitalId() {
		return strHospitalId;
	}

	public void setStrHospitalId(String strHospitalId) {
		this.strHospitalId = strHospitalId;
	}

	

	

	public String getEnablericopar() {
		return enablericopar;
	}

	public void setEnablericopar(String enablericopar) {
		this.enablericopar = enablericopar;
	}

	public String getEnableucr() {
		return enableucr;
	}

	public void setEnableucr(String enableucr) {
		this.enableucr = enableucr;
	}
	
	

	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	
	public String getClinicianWarning() {
		return clinicianWarning;
	}
	public void setClinicianWarning(String clinicianWarning) {
		this.clinicianWarning = clinicianWarning;
	}
	public String getDenialCode() { 
		return denialCode;
	}
	public void setDenialCode(String denialCode) { 
		this.denialCode = denialCode;
	}
	

	public String getExitdate() {
		return exitdate;
	}
	public void setExitdate(String exitdate) {
		this.exitdate = exitdate;
	}
	
	
	/*public BigDecimal getConvertedAmount() {
		return convertedAmount;
	}
	public void setConvertedAmount(BigDecimal convertedAmount) {
		this.convertedAmount = convertedAmount;
	}*/
	public String getConversionRate() {
		return conversionRate;
	}
	public void setConversionRate(String conversionRate) {
		this.conversionRate = conversionRate;
	}
	public String getRequestedAmountcurrencyType() {
		return requestedAmountcurrencyType;
	}
	public void setRequestedAmountcurrencyType(
			String requestedAmountcurrencyType) {
		this.requestedAmountcurrencyType = requestedAmountcurrencyType;
	}
	/*public BigDecimal getConvertedFinalApprovedAmount() {
		return convertedFinalApprovedAmount;
	}
	public void setConvertedFinalApprovedAmount(
			BigDecimal bigDecimal) {
		this.convertedFinalApprovedAmount = bigDecimal;
	}*/
	public String getFinalAprAmt() {
		return finalAprAmt;
	}
	public void setFinalAprAmt(String finalAprAmt) {
		this.finalAprAmt = finalAprAmt;
	}
	public String getIpCopayLimitYN() {
		return ipCopayLimitYN;
	}
	public void setIpCopayLimitYN(String ipCopayLimitYN) {
		this.ipCopayLimitYN = ipCopayLimitYN;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public int getCount_flag() {
		return count_flag;
	}
	public void setCount_flag(int count_flag) {
		this.count_flag = count_flag;
	}
	
	
	public int getEventCount() {
		return eventCount;
	}
	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}
	public String getsEnrollmentId() {
		return sEnrollmentId;
	}
	public void setsEnrollmentId(String sEnrollmentId) {
		this.sEnrollmentId = sEnrollmentId;
	}
	public String getCompleted_date() {
		return completed_date;
	}
	public void setCompleted_date(String completed_date) {
		this.completed_date = completed_date;
	}
	public String getPreMemExitDt() {
		return preMemExitDt;
	}
	public void setPreMemExitDt(String preMemExitDt) {
		this.preMemExitDt = preMemExitDt;
	}
	public String getClmMemExitDate() {
		return clmMemExitDate;
	}
	public void setClmMemExitDate(String clmMemExitDate) {
		this.clmMemExitDate = clmMemExitDate;
	}
	
	
	public String getProvRegAuthority() {
		return provRegAuthority;
	}
	public void setProvRegAuthority(String provRegAuthority) {
		this.provRegAuthority = provRegAuthority;
	}
	//SETTER AND GETTER OF PATIENT_ID
	public String getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}
	
	
	
	public void setProStatusErrMsg(String proStatusErrMsg) {
		this.proStatusErrMsg = proStatusErrMsg;
	}
	public String getProStatusErrMsg() {
		return proStatusErrMsg;
	}
	public String getProviderSpecifiedPayerId() {
		return providerSpecifiedPayerId;
	}
	public void setProviderSpecifiedPayerId(String providerSpecifiedPayerId) {
		this.providerSpecifiedPayerId = providerSpecifiedPayerId;
	}
	public String getProviderSpecifiedPayerName() {
		return providerSpecifiedPayerName;
	}
	public void setProviderSpecifiedPayerName(String providerSpecifiedPayerName) {
		this.providerSpecifiedPayerName = providerSpecifiedPayerName;
	}
	public String getConvertedAmount() {
		return convertedAmount;
	}
	public void setConvertedAmount(String convertedAmount) {
		this.convertedAmount = convertedAmount;
	}
	public String getConvertedFinalApprovedAmount() {
		return convertedFinalApprovedAmount;
	}
	public void setConvertedFinalApprovedAmount(
			String convertedFinalApprovedAmount) {
		this.convertedFinalApprovedAmount = convertedFinalApprovedAmount;
	}
	public int getCount_flag2() {
		return count_flag2;
	}
	public void setCount_flag2(int count_flag2) {
		this.count_flag2 = count_flag2;
	}
	public String getOverrideAllowYN() {
		return overrideAllowYN;
	}
	public void setOverrideAllowYN(String overrideAllowYN) {
		this.overrideAllowYN = overrideAllowYN;
	}
		public String getDhpoUploadedDate() {
		return dhpoUploadedDate;
	}
	public void setDhpoUploadedDate(String dhpoUploadedDate) {
		this.dhpoUploadedDate = dhpoUploadedDate;
	}
	public String getEclaimDuplicateWrongAlert() {
		return eclaimDuplicateWrongAlert;
	}
	public void setEclaimDuplicateWrongAlert(String eclaimDuplicateWrongAlert) {
		this.eclaimDuplicateWrongAlert = eclaimDuplicateWrongAlert;
	}
	public String getAutodenialyn() {
		return autodenialyn;
	}
	public void setAutodenialyn(String autodenialyn) {
		this.autodenialyn = autodenialyn;
	}
	public String getClaimOvrFlag() {
		return claimOvrFlag;
	}
	public void setClaimOvrFlag(String claimOvrFlag) {
		this.claimOvrFlag = claimOvrFlag;
	}
	public String getNegativeAmtFlg() {
		return negativeAmtFlg;
	}
	public void setNegativeAmtFlg(String negativeAmtFlg) {
		this.negativeAmtFlg = negativeAmtFlg;
	}
	
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	public String getCopayLimitReachedActivityYN() {
		return copayLimitReachedActivityYN;
	}
	public void setCopayLimitReachedActivityYN(
			String copayLimitReachedActivityYN) {
		this.copayLimitReachedActivityYN = copayLimitReachedActivityYN;
	}
	public String getPreOneMedicalCondition() {
		return preOneMedicalCondition;
	}
	public void setPreOneMedicalCondition(String preOneMedicalCondition) {
		this.preOneMedicalCondition = preOneMedicalCondition;
	}
	public String getVatTrnCode() {
		return vatTrnCode;
	}
	public void setVatTrnCode(String vatTrnCode) {
		this.vatTrnCode = vatTrnCode;
	}
	
	
	public Integer getPreAuthCount() {
		return preAuthCount;
	}
	public void setPreAuthCount(Integer preAuthCount) {
		this.preAuthCount = preAuthCount;
	}
	public Integer getClmCount() {
		return clmCount;
	}
	public void setClmCount(Integer clmCount) {
		this.clmCount = clmCount;
	}
	public String getPaymentTo() {
		return paymentTo;
	}
	public void setPaymentTo(String paymentTo) {
		this.paymentTo = paymentTo;
	}
	public BigDecimal getPreAuthIncurredAmt() {
		return preAuthIncurredAmt;
	}
	public void setPreAuthIncurredAmt(BigDecimal preAuthIncurredAmt) {
		this.preAuthIncurredAmt = preAuthIncurredAmt;
	}
	public String getPreAuthIncurredCurrencyType() {
		return preAuthIncurredCurrencyType;
	}
	public void setPreAuthIncurredCurrencyType(
			String preAuthIncurredCurrencyType) {
		this.preAuthIncurredCurrencyType = preAuthIncurredCurrencyType;
	}
	public String getIrdrgAltMsg() {
		return irdrgAltMsg;
	}
	public void setIrdrgAltMsg(String irdrgAltMsg) {
		this.irdrgAltMsg = irdrgAltMsg;
	}
	public String getClaimSeqId() {
		return claimSeqId;
	}
	public void setClaimSeqId(String claimSeqId) {
		this.claimSeqId = claimSeqId;
	}	
	
	
	
	
	
	
	
	public String getProcessorInternalRemarks() {
		return processorInternalRemarks;
	}
	public void setProcessorInternalRemarks(String processorInternalRemarks) {
		this.processorInternalRemarks = processorInternalRemarks;
	}
	public String getDisableMemberId() {
		return disableMemberId;
	}
	public void setDisableMemberId(String disableMemberId) {
		this.disableMemberId = disableMemberId;
	}
	public String getModeFlag() {
		return modeFlag;
	}
	public void setModeFlag(String modeFlag) {
		this.modeFlag = modeFlag;
	}
	public String getCntFlag() {
		return cntFlag;
	}
	public void setCntFlag(String cntFlag) {
		this.cntFlag = cntFlag;
	}
	public int getObsCountFlag() {
		return obsCountFlag;
	}
	public void setObsCountFlag(int obsCountFlag) {
		this.obsCountFlag = obsCountFlag;
	}
	public int getObservationFlag() {
		return observationFlag;
	}
	public void setObservationFlag(int observationFlag) {
		this.observationFlag = observationFlag;
	}
	public String getClaimVatAED() {
		return claimVatAED;
	}
	public void setClaimVatAED(String claimVatAED) {
		this.claimVatAED = claimVatAED;
	}
	public String getVatAddedReqAmnt() {
		return vatAddedReqAmnt;
	}
	public void setVatAddedReqAmnt(String vatAddedReqAmnt) {
		this.vatAddedReqAmnt = vatAddedReqAmnt;
	}
	
	public String getPolicySuspeMsg() {
		return policySuspeMsg;
	}
	public void setPolicySuspeMsg(String policySuspeMsg) {
		this.policySuspeMsg = policySuspeMsg;
	}
	public String getFlagForCheckEnhancement() {
		return flagForCheckEnhancement;
	}
	public void setFlagForCheckEnhancement(String flagForCheckEnhancement) {
		this.flagForCheckEnhancement = flagForCheckEnhancement;
	}
	public String getInvoiceno() {
		return invoiceno;
	}
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	public BigDecimal getProRequestedAmount() {
		return proRequestedAmount;
	}
	public void setProRequestedAmount(BigDecimal proRequestedAmount) {
		this.proRequestedAmount = proRequestedAmount;
	}
	public String getPresntComplaints() {
		return presntComplaints;
	}
	public void setPresntComplaints(String presntComplaints) {
		this.presntComplaints = presntComplaints;
	}
	public int getDurPresentAilment() {
		return durPresentAilment;
	}
	public void setDurPresentAilment(int durPresentAilment) {
		this.durPresentAilment = durPresentAilment;
	}
	public String getPastHistoryRelevant() {
		return pastHistoryRelevant;
	}
	public void setPastHistoryRelevant(String pastHistoryRelevant) {
		this.pastHistoryRelevant = pastHistoryRelevant;
	}
	public int getDurPastHistRelevant() {
		return durPastHistRelevant;
	}
	public void setDurPastHistRelevant(int durPastHistRelevant) {
		this.durPastHistRelevant = durPastHistRelevant;
	}
	public String getAppliedVatAEDPER() {
		return appliedVatAEDPER;
	}
	public void setAppliedVatAEDPER(String appliedVatAEDPER) {
		this.appliedVatAEDPER = appliedVatAEDPER;
	}
	public String getVatFlag() {
		return vatFlag;
	}
	public void setVatFlag(String vatFlag) {
		this.vatFlag = vatFlag;
	}
	public ArrayList<DiagnosisDetailsVO> getDiagnosisList() {
		return diagnosisList;
	}
	public void setDiagnosisList(ArrayList<DiagnosisDetailsVO> diagnosisList) {
		this.diagnosisList = diagnosisList;
	}
	public ArrayList<ActivityDetailsVO> getActivityList() {
		return activityList;
	}
	public void setActivityList(ArrayList<ActivityDetailsVO> activityList) {
		this.activityList = activityList;
	}
	public ArrayList<String[]> getShortfallList() {
		return shortfallList;
	}
	public void setShortfallList(ArrayList<String[]> shortfallList) {
		this.shortfallList = shortfallList;
	}
	public String getDispDhpoBtn() {
		return dispDhpoBtn;
	}
	public void setDispDhpoBtn(String dispDhpoBtn) {
		this.dispDhpoBtn = dispDhpoBtn;
	}
	public String getPrePedDescription() {
		return prePedDescription;
	}
	public void setPrePedDescription(String prePedDescription) {
		this.prePedDescription = prePedDescription;
	}
	public String getPreMaterinityDescription() {
		return preMaterinityDescription;
	}
	public void setPreMaterinityDescription(String preMaterinityDescription) {
		this.preMaterinityDescription = preMaterinityDescription;
	}
	public String getClmPedDescription() {
		return clmPedDescription;
	}
	public void setClmPedDescription(String clmPedDescription) {
		this.clmPedDescription = clmPedDescription;
	}
	public String getClmMaterinityDescription() {
		return clmMaterinityDescription;
	}
	public void setClmMaterinityDescription(String clmMaterinityDescription) {
		this.clmMaterinityDescription = clmMaterinityDescription;
	}
	public String getWorkRelatedInjury() {
		return workRelatedInjury;
	}
	public void setWorkRelatedInjury(String workRelatedInjury) {
		this.workRelatedInjury = workRelatedInjury;
	}
	public String getAlcoholIntoxication() {
		return alcoholIntoxication;
	}
	public void setAlcoholIntoxication(String alcoholIntoxication) {
		this.alcoholIntoxication = alcoholIntoxication;
	}
	public String getRoadTrafficAccident() {
		return roadTrafficAccident;
	}
	public void setRoadTrafficAccident(String roadTrafficAccident) {
		this.roadTrafficAccident = roadTrafficAccident;
	}
	public String getPatientType() {
		return patientType;
	}
	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}
	public String getAdditionaDeductionRemarks() {
		return additionaDeductionRemarks;
	}
	public void setAdditionaDeductionRemarks(String additionaDeductionRemarks) {
		this.additionaDeductionRemarks = additionaDeductionRemarks;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Date getDateOfInjury() {
		return dateOfInjury;
	}
	public void setDateOfInjury(Date dateOfInjury) {
		this.dateOfInjury = dateOfInjury;
	}
	public String getAilmentDuration() {
		return ailmentDuration;
	}
	public void setAilmentDuration(String ailmentDuration) {
		this.ailmentDuration = ailmentDuration;
	}
	public String getAilmentDurationFlag() {
		return ailmentDurationFlag;
	}
	public void setAilmentDurationFlag(String ailmentDurationFlag) {
		this.ailmentDurationFlag = ailmentDurationFlag;
	}
	public String getRelPresentIllness() {
		return relPresentIllness;
	}
	public void setRelPresentIllness(String relPresentIllness) {
		this.relPresentIllness = relPresentIllness;
	}
	public String getRelevantFindings() {
		return relevantFindings;
	}
	public void setRelevantFindings(String relevantFindings) {
		this.relevantFindings = relevantFindings;
	}
	public String getMobileIsdCode() {
		return mobileIsdCode;
	}
	public void setMobileIsdCode(String mobileIsdCode) {
		this.mobileIsdCode = mobileIsdCode;
	}
	public String getMobileNbr() {
		return mobileNbr;
	}
	public void setMobileNbr(String mobileNbr) {
		this.mobileNbr = mobileNbr;
	}
	
}//end of PreAuthDetailVO