
/** 
 *  @ (#)CashlessDetailVO.java Apr 10, 2006
 *  Project       : TTK HealthCare Services
 *  File          : CashlessDetailVO.java
 *  Author        : Arun K N
 *  Company       : Span Systems Corporation
 *  Date Created  : Apr 10, 2006
 * 
 *  @author       :  Arun K N
 *  Modified by   :  
 *  Modified date :  
 *  Reason        :  
 */
package com.ttk.dto.preauth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.ttk.dto.common.CacheObject;
import com.ttk.dto.empanelment.LaboratoryServicesVO;
import com.ttk.dto.onlineforms.providerLogin.ServiceDetailsVO;


public class CashlessDetailVO extends CashlessVO {


	private static final long serialVersionUID = 1L;


    private int strHospSeqId			=	0;
    private int strDiagEnrolSeqId		=	0;
    private int strTestSeqId			=	0;
    private int strDiagSeqId			=	0;
	private String strTestName			=	"";
	private double bcdRate 				= 0;
	private double bcdDiscount 			= 0;
	private double bcdDiscountRate		= 0;
	private BigDecimal bdAgreedRate 	= null;
	private BigDecimal bdEnteredRate	= null;
	private Long lngMobileNo 			= null;
	private int isdCode 				= 	0;
	private String strBenifitType		="	";//added for Benifit Type
	private String strModeType			=	"";//added for  Mode
	private Date memDob					=	null;
	//intX
	private String strBpSystolic		=	null;
	private String strBpDiastolic		=	null;
	private String strTemprature		=	null;
	private String strPulse				=	null;
	private String strRespiration		=	null;
	private String strHeight			=	null;
	private String strWeight			=	null;
	private String strEnrollId			=	"";
	private String reasonForRejection	=	"";
	
	private String strMedicalRecordNo	=	null;
	private String strClinicianName		=	null;
	private String strClinicianId		=	null;
	private String strSpeciality		=	null;
	private String strConsultation		=	null;
	private String strSystemOfMedicine	=	null;
	private String strAccidentYN		=	null;
	private String strFirNo				=	null;
	private String strProcedureType		=	null;
	private String strSurgeryType		=	null;
	private String strSinceWhen			=	null;
	private String strPlanOfTreatment	=	null;
	private String strTreatmentPlan		=	null;
	private String strEstimation		=	null;
	private String strDetailsOfTreatment=	null;
	private String strMedicalFindings	=	null;
	private String strEmergencyYN		=	null;
	private String strDocType			=	null;
	private String strInvoiceNo			=	null;
	private String strFileName			=	null;
	private String strConsumableDesc	=	null;
	private String IntimationNo			=	null;
	private Long IntimationSeqId		=	null;
	private Date TreatmentDate			=	null;
	private Date dischargeDate			=	null;
	private String presentingComplaints	=	null;
	private String pastHistory			=	null;
	private String ailmentDuration		=	null;
	private String ailmentDurationText	=	null;
	private String encounterType		=	null;
	private String treatmentType		=	null;
	private String illnessDuration		=	null;
	private String illnessDurationText	=	null;
	private String preAuthRefNo			=	null;
	private String toothNo			=	null;
	private	FormFile file=null;
	private DiagnosisDetailsVO diagnosisDetailsVO	=	null;
	private ActivityDetailsVO activityDetailsVO		=	null;
	private DrugDetailsVO drugDetailsVO				=	null;
	private ServiceDetailsVO serviceDetailsVO;

	private String eligibility1	=	null;
	private String deductible	=	null;
	private String emirateID	=	null;
	//for validation of Networks
	private String[] networksArray	=	null;
	private Map<String,String> assNetworksArray	=	null;
	private String areaOfCoverageCode;
	private String eligibilityBenefit;
	private String eligibilityEncounter;
	private String policyStDt;
	private String policyEnDt;
	private long prodPolicySeqId;
	private String benifitTypeCode		=	null;
	private Double estimatedCost = Double.valueOf(0);
	private String providerName;
	private String country;
	private String currency;
	private String lineOfTreatment;
	private String genratedReferenceId;
	private String ptrEncounterType;
	private String authorizationNo			=	null;
	private Date dateOfLMP;
	private String natureOfConception;
	private String modeofdelivery;
	private DentalOrthoVO dentalOrthoVO;
	//for partner login.
    private long partnerGenSeqId;
    private ArrayList<CacheObject> encounterTypes	=	null;
    private String showEmpDetail =	"";
    
	private String policyNo;

	private String description;

	private String currencyType;
	
	private String loginType;

	
	private String eventReferenceNo;
	
	
	private String eligibilitySedId;
	
	private BigDecimal proReqAmt 	= null;
	private String dhpoMemberId;
	
	
	public String getEventReferenceNo() {
		return eventReferenceNo;
	}

	public void setEventReferenceNo(String eventReferenceNo) {
		this.eventReferenceNo = eventReferenceNo;
	}
	
	
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
	
    public ServiceDetailsVO getServiceDetailsVO() {
		return serviceDetailsVO;
	}

	public void setServiceDetailsVO(ServiceDetailsVO serviceDetailsVO) {
		this.serviceDetailsVO = serviceDetailsVO;
	}

	public String getToothNo() {
		return toothNo;
	}

	public void setToothNo(String toothNo) {
		this.toothNo = toothNo;
	}
 
    public String getShowEmpDetail() {
  		return showEmpDetail;
  	}


  	public void setShowEmpDetail(String showEmpDetail) {
  		this.showEmpDetail = showEmpDetail;
  	}

	public ArrayList<CacheObject> getEncounterTypes() {
		return encounterTypes;
	}

	public void setEncounterTypes(ArrayList<CacheObject> encounterTypes) {
		this.encounterTypes = encounterTypes;
	}
	
	public long getPartnerGenSeqId() {
		return partnerGenSeqId;
	}

	public void setPartnerGenSeqId(long partnerGenSeqId) {
		this.partnerGenSeqId = partnerGenSeqId;
	}
	
	
	public DentalOrthoVO getDentalOrthoVO() {
		return dentalOrthoVO;
	}

	public void setDentalOrthoVO(DentalOrthoVO dentalOrthoVO) {
		this.dentalOrthoVO = dentalOrthoVO;
	}
	
	public String getModeofdelivery() {
		return modeofdelivery;
	}

	public void setModeofdelivery(String modeofdelivery) {
		this.modeofdelivery = modeofdelivery;
	}
	
	public String getNatureOfConception() {
		return natureOfConception;
	}

	public void setNatureOfConception(String natureOfConception) {
		this.natureOfConception = natureOfConception;
	}
	
	
	public Date getDateOfLMP() {
		return dateOfLMP;
	}

	public void setDateOfLMP(Date dateOfLMP) {
		this.dateOfLMP = dateOfLMP;
	}

	
	public String getAuthorizationNo() {
		return authorizationNo;
	}

	public void setAuthorizationNo(String authorizationNo) {
		this.authorizationNo = authorizationNo;
	}
	
	
	
	public String getPtrEncounterType() {
		return ptrEncounterType;
	}

	public void setPtrEncounterType(String ptrEncounterType) {
		this.ptrEncounterType = ptrEncounterType;
	}
	
	
	public String getGenratedReferenceId() {
		return genratedReferenceId;
	}

	public void setGenratedReferenceId(String genratedReferenceId) {
		this.genratedReferenceId = genratedReferenceId;
	}

	
	public String getLineOfTreatment() {
		return lineOfTreatment;
	}

	public void setLineOfTreatment(String lineOfTreatment) {
		this.lineOfTreatment = lineOfTreatment;
	}
	
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	
	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	
	
	public double getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	
	
	public String getBenifitTypeCode() {
		return benifitTypeCode;
	}

	public void setBenifitTypeCode(String benifitTypeCode) {
		this.benifitTypeCode = benifitTypeCode;
	}

	
	public long getProdPolicySeqId() {
		return prodPolicySeqId;
	}

	public void setProdPolicySeqId(long prodPolicySeqId) {
		this.prodPolicySeqId = prodPolicySeqId;
	}	
	
	
	public String getPolicyEnDt() {
		return policyEnDt;
	}

	public void setPolicyEnDt(String policyEnDt) {
		this.policyEnDt = policyEnDt;
	}
	public String getPolicyStDt() {
		return policyStDt;
	}

	public void setPolicyStDt(String policyStDt) {
		this.policyStDt = policyStDt;
	}
	
	public String getEligibilityEncounter() {
		return eligibilityEncounter;
	}

	public void setEligibilityEncounter(String eligibilityEncounter) {
		this.eligibilityEncounter = eligibilityEncounter;
	}
	
	
	public String getEligibilityBenefit() {
		return eligibilityBenefit;
	}

	public void setEligibilityBenefit(String eligibilityBenefit) {
		this.eligibilityBenefit = eligibilityBenefit;
	}

	
	public String getAreaOfCoverageCode() {
		return areaOfCoverageCode;
	}

	public void setAreaOfCoverageCode(String areaOfCoverageCode) {
		this.areaOfCoverageCode = areaOfCoverageCode;
	}
	
	public String getInvoiceNo() {
		return strInvoiceNo;
	}

	public void setInvoiceNo(String strInvoiceNo) {
		this.strInvoiceNo = strInvoiceNo;
	}
	private LaboratoryServicesVO laboratoryServicesObj =null;
	
	public String getDocType() {
		return strDocType;
	}

	public void setDocType(String strDocType) {
		this.strDocType = strDocType;
	}
	
	public void setLaboratoryServicesVO(LaboratoryServicesVO laboratoryServicesObj)
	{
		this.laboratoryServicesObj=laboratoryServicesObj;
	}
	
	
	public LaboratoryServicesVO getLaboratoryServicesVO()
	{
		return laboratoryServicesObj;
	}
	
	public String getEmergencyYN() {
		return strEmergencyYN;
	}

	public void setEmergencyYN(String strEmergencyYN) {
		this.strEmergencyYN = strEmergencyYN;
	}

	public String getAccidentYN() {
		return strAccidentYN;
	}

	public void setAccidentYN(String strAccidentYN) {
		this.strAccidentYN = strAccidentYN;
	}

	public String getMedicalRecordNo() {
		return strMedicalRecordNo;
	}

	public void setMedicalRecordNo(String strMedicalRecordNo) {
		this.strMedicalRecordNo = strMedicalRecordNo;
	}

	public String getClinicianName() {
		return strClinicianName;
	}

	public void setClinicianName(String strClinicianName) {
		this.strClinicianName = strClinicianName;
	}

	public String getClinicianId() {
		return strClinicianId;
	}

	public void setClinicianId(String strClinicianId) {
		this.strClinicianId = strClinicianId;
	}

	public String getSpeciality() {
		return strSpeciality;
	}

	public void setSpeciality(String strSpeciality) {
		this.strSpeciality = strSpeciality;
	}

	public String getConsultation() {
		return strConsultation;
	}

	public void setConsultation(String strConsultation) {
		this.strConsultation = strConsultation;
	}

	public String getSystemOfMedicine() {
		return strSystemOfMedicine;
	}

	public void setSystemOfMedicine(String strSystemOfMedicine) {
		this.strSystemOfMedicine = strSystemOfMedicine;
	}

	public String getFirNo() {
		return strFirNo;
	}

	public void setFirNo(String strFirNo) {
		this.strFirNo = strFirNo;
	}

	public String getProcedureType() {
		return strProcedureType;
	}

	public void setProcedureType(String strProcedureType) {
		this.strProcedureType = strProcedureType;
	}

	public String getSurgeryType() {
		return strSurgeryType;
	}

	public void setSurgeryType(String strSurgeryType) {
		this.strSurgeryType = strSurgeryType;
	}

	public String getSinceWhen() {
		return strSinceWhen;
	}

	public void setSinceWhen(String strSinceWhen) {
		this.strSinceWhen = strSinceWhen;
	}

	public String getPlanOfTreatment() {
		return strPlanOfTreatment;
	}

	public void setPlanOfTreatment(String strPlanOfTreatment) {
		this.strPlanOfTreatment = strPlanOfTreatment;
	}

	public String getTreatmentPlan() {
		return strTreatmentPlan;
	}

	public void setTreatmentPlan(String strTreatmentPlan) {
		this.strTreatmentPlan = strTreatmentPlan;
	}

	public String getEstimation() {
		return strEstimation;
	}

	public void setEstimation(String strEstimation) {
		this.strEstimation = strEstimation;
	}

	public String getDetailsOfTreatment() {
		return strDetailsOfTreatment;
	}

	public void setDetailsOfTreatment(String strDetailsOfTreatment) {
		this.strDetailsOfTreatment = strDetailsOfTreatment;
	}

	public String getMedicalFindings() {
		return strMedicalFindings;
	}

	public void setMedicalFindings(String strMedicalFindings) {
		this.strMedicalFindings = strMedicalFindings;
	}


	
	public String getModeType() {
		return strModeType;
	}

	public void setModeType(String strModeType) {
		this.strModeType = strModeType;
	}

	
	
	public String getHeight() {
		return strHeight;
	}

	public void setHeight(String strHeight) {
		this.strHeight = strHeight;
	}

	public String getWeight() {
		return strWeight;
	}

	public void setWeight(String strWeight) {
		this.strWeight = strWeight;
	}
	
	public String getEnrollId() {
		return strEnrollId;
	}

	public void setEnrollId(String strEnrollId) {
		this.strEnrollId = strEnrollId;
	}
	//---- member Vitals getters------
	public String getBpSystolic() {
		return strBpSystolic;
	}

	public void setBpSystolic(String strBpSystolic) {
		this.strBpSystolic = strBpSystolic;
	}

	public String getBpDiastolic() {
		return strBpDiastolic;
	}

	public void setBpDiastolic(String strBpDiastolic) {
		this.strBpDiastolic = strBpDiastolic;
	}

	public String getTemprature() {
		return strTemprature;
	}

	public void setTemprature(String strTemprature) {
		this.strTemprature = strTemprature;
	}

	public String getPulse() {
		return strPulse;
	}

	public void setPulse(String strPulse) {
		this.strPulse = strPulse;
	}

	public String getRespiration() {
		return strRespiration;
	}

	public void setRespiration(String strRespiration) {
		this.strRespiration = strRespiration;
	}
	//---- member Vitals getters------
	
	public Long getMobileNo() {
		return lngMobileNo;
	}

	public void setMobileNo(Long lngMobileNo) {
		this.lngMobileNo = lngMobileNo;
	}
	 public String getBenifitType() {
		return strBenifitType;
	}

		public void setBenifitType(String strBenifitType) {
		this.strBenifitType = strBenifitType;
	}
		
	public int getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(int isdCode) {
		this.isdCode = isdCode;
	}
	
	public BigDecimal getEnteredRate() {
        return bdEnteredRate;
    }//end of getPrevApprovedAmount()
    
    
    public void setEnteredRate(BigDecimal bdEnteredRate) {
        this.bdEnteredRate = bdEnteredRate;
    }//
    
    public BigDecimal getAgreedRate() {
        return bdAgreedRate;
    }//end of getPrevApprovedAmount()
    
    
    public void setAgreedRate(BigDecimal bdAgreedRate) {
        this.bdAgreedRate = bdAgreedRate;
    }//
    public int getHospSeqId() {
		return strHospSeqId;
	}
    
	public void setHospSeqId(int strHospSeqId) {
		this.strHospSeqId = strHospSeqId;
	}

	public int getDiagSeqId() {
		return strDiagSeqId;
	}

	public void setDiagSeqId(int strDiagSeqId) {
		this.strDiagSeqId = strDiagSeqId;
	}
	
	public int getTestSeqId() {
		return strTestSeqId;
	}

	public void setTestSeqId(int strTestSeqId) {
		this.strTestSeqId = strTestSeqId;
	}
	
	public int getDiagEnrolSeqId() {
		return strDiagEnrolSeqId;
	}

	public void setDiagEnrolSeqId(int strDiagEnrolSeqId) {
		this.strDiagEnrolSeqId = strDiagEnrolSeqId;
	}

	public String getTestName() {
		return strTestName;
	}

	public void setTestName(String strTestName) {
		this.strTestName = strTestName;
	}
	
	public double getRate() {
		return bcdRate;
	}

	public void setRate(double d) {
		this.bcdRate = d;
	}

	public double getDiscount() {
		return bcdDiscount;
	}

	public void setDiscount(double bcdDiscount) {
		this.bcdDiscount = bcdDiscount;
	}
	
	public double getDiscountRate() {
		return bcdDiscountRate;
	}

	public void setDiscountRate(double bcdDiscountRate) {
		this.bcdDiscountRate = bcdDiscountRate;
	}
    //selffund ends
    
	
	//Member details for Cashless
	private String strMemberName	=	null;
	private Long   lngAge			=	null;
	private String strGender		=	null;
	private String strPayer			=	null;
	private String preApprLimit		=	null;
	
	private String strEligibility	=	null;
	private String strDeductable	=	null;
	private String strCoPay			=	null;
	private String strApplProcudure	=	null;
	private String strExclusions	=	null;
	private String strTob			=	null;
	private String strInsurredName	=	null;
	private String strPharmaceutical = null;
	private String strIPOPServices = null;
	private String sumInsured = null;
	private String avalableSumInsured = null;
	private String strCountryCovered = null;
	private String errorType;
	private String eMailId	 			= null;
	
	public String geteMailId() {
		return eMailId;
	}

	public void seteMailId(String eMailId) {
		this.eMailId = eMailId;
	}
	
	
	
	
	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	
	public String getCountryCovered() {
		return strCountryCovered;
	}

	public void setCountryCovered(String strCountryCovered) {
		this.strCountryCovered = strCountryCovered;
	}
	
	
	public String getAvalableSumInsured() {
		return avalableSumInsured;
	}

	public void setAvalableSumInsured(String avalableSumInsured) {
		this.avalableSumInsured = avalableSumInsured;
	}
	
	
	public String getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}
	
	
	public String getIPOPServices() {
		return strIPOPServices;
	}

	public void setIPOPServices(String strIPOPServices) {
		this.strIPOPServices = strIPOPServices;
	}
	
	public String getPharmaceutical() {
		return strPharmaceutical;
	}

	public void setPharmaceutical(String strPharmaceutical) {
		this.strPharmaceutical = strPharmaceutical;
	}
	
	
	
	public String getInsurredName() {
		return strInsurredName;
	}

	public void setInsurredName(String strInsurredName) {
		this.strInsurredName = strInsurredName;
	}
	public String getMemberName() {
		return strMemberName;
	}

	public void setMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}

	public Long getAge() {
		return lngAge;
	}

	public void setAge(Long lngAge) {
		this.lngAge = lngAge;
	}

	public String getGender() {
		return strGender;
	}

	public void setGender(String strGender) {
		this.strGender = strGender;
	}

	public String getPayer() {
		return strPayer;
	}

	public void setPayer(String strPayer) {
		this.strPayer = strPayer;
	}

	public String getEligibility() {
		return strEligibility;
	}

	public void setEligibility(String strEligibility) {
		this.strEligibility = strEligibility;
	}

	public String getDeductable() {
		return strDeductable;
	}

	public void setDeductable(String strDeductable) {
		this.strDeductable = strDeductable;
	}

	public String getCoPay() {
		return strCoPay;
	}

	public void setCoPay(String strCoPay) {
		this.strCoPay = strCoPay;
	}

	public String getApplProcudure() {
		return strApplProcudure;
	}

	public void setApplProcudure(String strApplProcudure) {
		this.strApplProcudure = strApplProcudure;
	}

	public String getExclusions() {
		return strExclusions;
	}

	public void setExclusions(String strExclusions) {
		this.strExclusions = strExclusions;
	}

	public String getTob() {
		return strTob;
	}

	public void setTob(String strTob) {
		this.strTob = strTob;
	}

	/**
	 * @return the preApprLimit
	 */
	public String getPreApprLimit() {
		return preApprLimit;
	}

	/**
	 * @param preApprLimit the preApprLimit to set
	 */
	public void setPreApprLimit(String preApprLimit) {
		this.preApprLimit = preApprLimit;
	}

	/**
	 * @return the reasonForRejection
	 */
	public String getReasonForRejection() {
		return reasonForRejection;
	}

	/**
	 * @param reasonForRejection the reasonForRejection to set
	 */
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

	/**
	 * @return the memDob
	 */
	public Date getMemDob() {
		return memDob;
	}

	/**
	 * @param memDob the memDob to set
	 */
	public void setMemDob(Date memDob) {
		this.memDob = memDob;
	}

	/**
	 * @return the presentingComplaints
	 */
	public String getPresentingComplaints() {
		return presentingComplaints;
	}

	/**
	 * @param presentingComplaints the presentingComplaints to set
	 */
	public void setPresentingComplaints(String presentingComplaints) {
		this.presentingComplaints = presentingComplaints;
	}

	/**
	 * @return the pastHistory
	 */
	public String getPastHistory() {
		return pastHistory;
	}

	/**
	 * @param pastHistory the pastHistory to set
	 */
	public void setPastHistory(String pastHistory) {
		this.pastHistory = pastHistory;
	}

	/**
	 * @return the ailmentDuration
	 */
	public String getAilmentDuration() {
		return ailmentDuration;
	}

	/**
	 * @param ailmentDuration the ailmentDuration to set
	 */
	public void setAilmentDuration(String ailmentDuration) {
		this.ailmentDuration = ailmentDuration;
	}

	/**
	 * @return the ailmentDurationText
	 */
	public String getAilmentDurationText() {
		return ailmentDurationText;
	}

	/**
	 * @param ailmentDurationText the ailmentDurationText to set
	 */
	public void setAilmentDurationText(String ailmentDurationText) {
		this.ailmentDurationText = ailmentDurationText;
	}

	/**
	 * @return the encounterType
	 */
	public String getEncounterType() {
		return encounterType;
	}

	/**
	 * @param encounterType the encounterType to set
	 */
	public void setEncounterType(String encounterType) {
		this.encounterType = encounterType;
	}

	/**
	 * @return the treatmentType
	 */
	public String getTreatmentType() {
		return treatmentType;
	}

	/**
	 * @param treatmentType the treatmentType to set
	 */
	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	/**
	 * @return the illnessDuration
	 */
	public String getIllnessDuration() {
		return illnessDuration;
	}

	/**
	 * @param illnessDuration the illnessDuration to set
	 */
	public void setIllnessDuration(String illnessDuration) {
		this.illnessDuration = illnessDuration;
	}

	/**
	 * @return the illnessDurationText
	 */
	public String getIllnessDurationText() {
		return illnessDurationText;
	}

	/**
	 * @param illnessDurationText the illnessDurationText to set
	 */
	public void setIllnessDurationText(String illnessDurationText) {
		this.illnessDurationText = illnessDurationText;
	}

	/**
	 * @return the diagnosisDetailsVO
	 */
	public DiagnosisDetailsVO getDiagnosisDetailsVO() {
		return diagnosisDetailsVO;
	}

	/**
	 * @param diagnosisDetailsVO the diagnosisDetailsVO to set
	 */
	public void setDiagnosisDetailsVO(DiagnosisDetailsVO diagnosisDetailsVO) {
		this.diagnosisDetailsVO = diagnosisDetailsVO;
	}

	/**
	 * @return the dischargeDate
	 */
	public Date getDischargeDate() {
		return dischargeDate;
	}

	/**
	 * @param dischargeDate the dischargeDate to set
	 */
	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	/**
	 * @return the activityDetailsVO
	 */
	public ActivityDetailsVO getActivityDetailsVO() {
		return activityDetailsVO;
	}

	/**
	 * @param activityDetailsVO the activityDetailsVO to set
	 */
	public void setActivityDetailsVO(ActivityDetailsVO activityDetailsVO) {
		this.activityDetailsVO = activityDetailsVO;
	}

	/**
	 * @return the drugDetailsVO
	 */
	public DrugDetailsVO getDrugDetailsVO() {
		return drugDetailsVO;
	}

	/**
	 * @param drugDetailsVO the drugDetailsVO to set
	 */
	public void setDrugDetailsVO(DrugDetailsVO drugDetailsVO) {
		this.drugDetailsVO = drugDetailsVO;
	}

	/**
	 * @return the networksArray
	 */
	public String[] getNetworksArray() {
		return networksArray;
	}

	/**
	 * @param networksArray the networksArray to set
	 */
	public void setNetworksArray(String[] networksArray) {
		this.networksArray = networksArray;
	}

	/**
	 * @return the assNetworksArray
	 */
	public Map<String,String> getAssNetworksArray() {
		return assNetworksArray;
	}

	/**
	 * @param assNetworksArray the assNetworksArray to set
	 */
	public void setAssNetworksArray(Map<String,String> assNetworksArray) {
		this.assNetworksArray = assNetworksArray;
	}

	public String getPreAuthRefNo() {
		return preAuthRefNo;
	}

	public void setPreAuthRefNo(String preAuthRefNo) {
		this.preAuthRefNo = preAuthRefNo;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}




	public String getDeductible() {
		return deductible;
	}

	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}

	public String getEligibility1() {
		return eligibility1;
	}

	public void setEligibility1(String eligibility1) {
		this.eligibility1 = eligibility1;
	}

	public String getEmirateID() {
		return emirateID;
	}

	public void setEmirateID(String emirateID) {
		this.emirateID = emirateID;
	}

	public Date getTreatmentDate() {
		return TreatmentDate;
	}

	public void setTreatmentDate(Date treatmentDate) {
		TreatmentDate = treatmentDate;
	}

	public Long getIntimationSeqId() {
		return IntimationSeqId;
	}

	public void setIntimationSeqId(Long intimationSeqId) {
		IntimationSeqId = intimationSeqId;
	}

	public String getIntimationNo() {
		return IntimationNo;
	}

	public void setIntimationNo(String intimationNo) {
		IntimationNo = intimationNo;
	}

	public String getConsumableDesc() {
		return strConsumableDesc;
	}

	public void setConsumableDesc(String strConsumableDesc) {
		this.strConsumableDesc = strConsumableDesc;
	}

	public String getFileName() {
		return strFileName;
	}

	public void setFileName(String strFileName) {
		this.strFileName = strFileName;
	}
	//selffund

	public String getEligibilitySedId() {
		return eligibilitySedId;
	}

	public void setEligibilitySedId(String eligibilitySedId) {
		this.eligibilitySedId = eligibilitySedId;
	}

	public BigDecimal getProReqAmt() {
		return proReqAmt;
	}

	public void setProReqAmt(BigDecimal proReqAmt) {
		this.proReqAmt = proReqAmt;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getDhpoMemberId() {
		return dhpoMemberId;
	}

	public void setDhpoMemberId(String dhpoMemberId) {
		this.dhpoMemberId = dhpoMemberId;
	}

	

	
}//end of CashlessDetailVO