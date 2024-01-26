/**
 * @ (#) Cache.java 12th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : Cache.java
 * Author       : Nagaraj D V
 * Company      : Span Systems Corporation
 * Date Created : 12th Sep 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :  Ramakrishna K M
 * Modified date :  Sep 28, 2005
 * Reason        :  Added Some Cache Objects
 */

package com.ttk.common.security; 

import java.io.Serializable;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import com.ttk.business.common.CacheManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;

public class Cache implements Serializable{

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList alRegions = null;//for drop down 1284
	 private static ArrayList alRegionsCode = null;//for drop down 1284
     
/*
 * added as per KOC 1285 Change Request
 */
	private static ArrayList alDomHospReason = null; //to load from table DOM Hosp Reason 

     //Added ass per Shortfall KOC 1179 CR
     private static ArrayList alClauseType=null;
     private static ArrayList alClauseSubType=null;
     //Shortfall CR KOC1179
     private static ArrayList alShortfallStatusList = null; //for drop down
	
	/**
	 * This code is added for cr koc 1103
	 * added eft
	 */
	private static ArrayList alDestnationBank = null; //to load from table TPA_HOSP_GRADE_CODE
    private static ArrayList alCityList = null; //to load from table TPA_HOSP_GRADE_CODE
    private static ArrayList alGeneralAccountUpdate=null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralAccountType=null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPaymentMethod1 = null;//for drop down
	 private static ArrayList alPaymentMethod2 = null;//for drop down
	/**
	 *End koc 1103
	 * End eft
	 */

    //private static ArrayList alUserRoles = null; //to load from table TPA_ROLES_CODE
	  private static ArrayList alInsProductCode = null; //to load from table TPA_STATE_CODE
	private static ArrayList alGradeCode = null; //to load from table TPA_HOSP_GRADE_CODE
    private static ArrayList alCountryCode = null; //to load from table TPA_COUNTRY_CODE
    private static ArrayList alDistrictCode = null;//to load district code
    private static ArrayList alStateCode = null; //to load from table TPA_STATE_CODE
    private static ArrayList alCityCode = null; //to load from table TPA_CITY_CODE
    private static ArrayList alEmpanelStatusCode = null; //to load from table TPA_HOSP_EMPANEL_STATUS_CODE
    private static ArrayList alEmpanelTypeCode = null; //to load from table TPA_HOSP_EMPANEL_TYPE_CODE
    private static ArrayList alMouCode = null; //to load from table TPA_MOU_CODE
    private static ArrayList alCommCode = null; //to load from table TPA_COMM_CODE
    private static ArrayList alContactTypeCode = null; //to load from table TPA_HOSP_CONTACT_CODE
    private static ArrayList alDrSpecialityCode = null; //to load from table TPA_HOSP_DR_SPECIALITY_CODE
    private static ArrayList alLogTypeCode = null; //to load from table TPA_HOSP_LOG_CODE
    //private static ArrayList alAddressTypeCode = null; //to load from table APP_ADDRESS_TYPE_CODE
    //private static ArrayList alCommTypeCode = null; //to load from table APP_COMM_TYPE_CODE
    //private static ArrayList alUserTypeCode = null; //to load from table APP_USER_TYPE_CODE
    private static ArrayList alDesignationCode = null; //to load from table TPA_DESIGNATION_CODE
    private static ArrayList alEnrolTypeCode = null; //to load from table TPA_ENROL_TYPE_CODE
    private static ArrayList alAnswerCode = null; //to load from table TPA_HOSP_ANSWER_CODE
    private static ArrayList alCategoryCode = null; //to load from table TPA_HOSP_CATEGORY_CODE
    private static ArrayList alHospCode = null; //to load from table TPA_HOSP_CODE
    private static ArrayList alEmpanelRsonCode = null; //to load from table TPA_HOSP_EMPANEL_RSON_CODE
    private static ArrayList alLocationCode = null; //to load from table TPA_HOSP_LOCATION_CODE
    private static ArrayList alMedicalCode = null; //to load from table TPA_HOSP_MEDICAL_CODE
    private static ArrayList alPlanCode = null; //to load from table TPA_HOSP_PLAN_CODE
    private static ArrayList alProcedureCode = null; //to load from table TPA_HOSP_PROCEDURE_CODE
    private static ArrayList alQuestCode = null; //to load from table TPA_HOSP_QUEST_CODE
    private static ArrayList alRoomsCode = null; //to load from table TPA_HOSP_ROOMS_CODE
    //private static ArrayList alWardCode = null; //to load from table TPA_HOSP_WARD_CODE
    private static ArrayList alMedicalNonPkgWardCode = null;//to load from tables TPA_HOSP_TARIFF_WARD and TPA_HOSP_WARD_CODE
    private static ArrayList alNonMedicalNonPkgWardCode = null;//to load from tables TPA_HOSP_TARIFF_WARD and TPA_HOSP_WARD_CODE
    private static ArrayList alRelationshipCode = null; //to load from table TPA_RELATIONSHIP_CODE
    private static ArrayList alOfficeInfoCode = null; //to load from table TPA_OFFICE_INFO
    private static ArrayList alModeOfClaims = null; //to load from table TPA_OFFICE_INFO
    private static ArrayList alcurrencyList = null; 
    private static ArrayList alUserInfoCode = null; //to load from table TPA_OFFICE_INFO 
  //KOC Cigna_insurance_resriction
    private static ArrayList aluserRestriction = null; 
    private static ArrayList aluserRestrictionGroup = null; 
	//KOC Cigna_insurance_resriction
	
	private static ArrayList alPreAuthAprRejStatus = null;//to load from table TPA_GENERAL_CODE//1274 A
    private static ArrayList alClaimAprRejStatus = null;//to load from table TPA_GENERAL_CODE//1274A

    private static ArrayList alIssuesChequesCode = null; //toload from table TPA_HOSP_ISSUES_CHEQUES_CODE
    private static ArrayList alPayOrderCode = null; //to load from table TPA_PAY_ORDER_CODE
    private static ArrayList alModReasonCode = null; // to load from table TPA_HOSP_MOD_REASON_CODE
    private static ArrayList alGeneralCodeDecision = null; // to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodePlan = null; // to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodeProviderStatus = null;// to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodeAssociate = null;// to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodeAssociateHos= null; //Insurance_corporate_wise_hosp_network 
    private static ArrayList alGeneralCodeValidationStatus = null;// to load from table TPA_GENERAL_CODE
    private static ArrayList alProductCode = null; // To load from TPA_INS_PRODUCT
    private static ArrayList alEnrollmentTypeCode = null; // To Load TPA_ENROLMENT_TYPE_CODE
    private static ArrayList alGeneralCodeHonour = null;// to load from table TPA_GENERAL_CODE
    private static ArrayList alInsCompanyNames = null;//to load from table TPA_INS_INFO  alBroCompanyNames
    private static ArrayList alhealthAuthorities=null;
    private static ArrayList alBroCompanyNames = null;//to load from table TPA_INS_INFO
    private static ArrayList alGeneralCodeProductType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodeProductStatus = null;// to load from table TPA_GENERAL_CODE
   
    private static ArrayList alGeneralCodeAdminMouStatus = null;// to load from table TPA_GENERAL_CODE
    private static ArrayList alHospMouArticle = null;//to load from table TPA_HOSP_MOU_ARTICLE
    private static ArrayList alGeneralCodeHospMouStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodePackage = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodeSectorType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alInsFrequencyCode = null;//to load from table TPA_INS_FREQUENCY_CODE
    private static ArrayList alInsDisbursalCode = null;//to load from table TPA_INS_DISBURSAL_CODE
    private static ArrayList alEmpHistory = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodeUserStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodePrefixName = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodeTPAUsers = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alHosRolesCode = null;//to load from TPA_ROLES_CODE
    private static ArrayList alPtrRolesCode = null;//to load from TPA_ROLES_CODE
    private static ArrayList alInsRolesCode = null;//to load from TPA_ROLES_CODE
    private static ArrayList alTTKRolesCode = null;//to load from TPA_ROLES_CODE
    private static ArrayList alCalRolesCode = null;//to load from TPA_ROLES_CODE
    private static ArrayList alCorRolesCode = null;//to load from TPA_ROLES_CODE
    private static ArrayList alAgnRolesCode = null;//to load from TPA_ROLES_CODE
    private static ArrayList alDmcRolesCode = null;//to load from TPA_ROLES_CODE
    private static ArrayList alGeneralCodeAssociatedUsers = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodeGroupType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGenearlCodeDepartmentID = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alTTKDesignationCode = null; //to load from table TPA_DESIGNATION_CODE
    private static ArrayList alBAKDesignationCode = null; //to load from table TPA_DESIGNATION_CODE
    private static ArrayList alCALDesignationCode = null; //to load from table TPA_DESIGNATION_CODE
    private static ArrayList alHOSDesignationCode = null; //to load from table TPA_DESIGNATION_CODE
    private static ArrayList alINSDesignationCode = null; //to load from table TPA_DESIGNATION_CODE
    private static ArrayList alCORDesignationCode = null; //to load from table TPA_DESIGNATION_CODE
    private static ArrayList alDMCDesignationCode = null; //to load from table TPA_DESIGNATION_CODE
    private static ArrayList alGeneralCodeEnrollment = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alGeneralCodeGenderDetails = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alClarificationStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alInsTermStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPolicySubTypeNonFloater = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPolicySubTypeFloater = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPolicySubTypeFloaterNon = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPolicySubTypeFloaterRestrict = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPolicySubType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPolicyMedium = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alIssueCheque = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alOccupation = null;
    private static ArrayList alNationalities = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alMaritalStatuses=null;
    private static ArrayList alMemberCategory = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alEnrollmentWorkflow = null;//to load from TPA_EVENT
    private static ArrayList alEndorsementWorkflow = null;//to load from TPA_EVENT
    private static ArrayList alClaimsWorkflow = null;//to load from TPA_EVENT
    private static ArrayList alCodeCleanUpWorkflow = null;//to load from TPA_EVENT
    private static ArrayList alInsProductPlan = null;//to load from TPA_INS_PRODUCT_PLAN
    private static ArrayList alPEDCodeDescription = null;//to load from TPA_PED_CODE
    private static ArrayList alPolicyStatus = null;//to load from table TPA_GENERAL_CODE
    //added for donor
    private static ArrayList alDonorClaimStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPrintStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alEnrollmentIdType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alTtkOfficeType = null; // to load from TPA_GENERAL_CODE
    private static ArrayList alTtkOfficeName = null ;//to load from TPA_OFFICE_INFO
    private static ArrayList alEndorsementType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alAmount = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alAssignedTo = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alRecdSource = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alEncounterTypes = null;
    private static ArrayList alEncounterStartTypes = null;
    private static ArrayList alEncounterEndTypes = null;
    private static ArrayList alTreatmentTypes=null;
    private static ArrayList alPreAuthStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPreauthEnhStatus = null;
    private static ArrayList alYears=null; //to load years
    private static ArrayList alClaimStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alInvestStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alHospitalizationType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alHospitalizedFor = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alCloseProximity = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alPreAuthPriority = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alCoInsCompList = null;
    private static ArrayList alPreAuthType = null;
    private static ArrayList alInsComp = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alShortfallStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alShortfallType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alClaimShortfallType = null;//to load from table TPA_GENERAL_CODE
	// Chanes as per KOC 1179
    private static ArrayList alClaimShortfallTypeNew = null;//to load from table TPA_GENERAL_CODE 
    private static ArrayList alClaimShortfallTemplateType = null;//to load from table TPA_GENERAL_CODE 
    // Chanes as per KOC 1179
  //shortfall phase1
    private static ArrayList alClaimShortfallTemplateNetworkType = null;//to load from table TPA_GENERAL_CODE
  //shortfall phase1
    private static ArrayList alClaimShortfallUnderClause = null;//to load from table TPA_GENERAL_CODE 
    private static ArrayList alSupportDoc = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alSpecialty = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alTreatmentPlan = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alICDTreatmentPlan = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alLogType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alLogInfo = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList aldataEntryLogType = null;//to load from table TPA_GENERAL_CODE //ParallelAlert
    private static ArrayList aldataEntryLogInfo = null;//to load from table TPA_GENERAL_CODE  //ParallelAlert
    private static ArrayList alHistoryType = null;//to load from table TPA_GENERAL_CODE
	private static ArrayList alClaimHistoryType = null;//to load from table TPA_GENERAL_CODE 
    private static ArrayList alAccountHistoryType = null;//to load from table TPA_GENERAL_CODE koc 11 koc11   
    private static ArrayList alInvestigationAgency = null;//to load from table INVESTIGATION_AGENCY_DETAILS
    private static ArrayList alInvestReason = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alAuthReason = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alShortfallReason = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alDurationType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alInvestigationType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alRcvdFrom = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alQualityStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alAssignUsers = null;//to load from table TPA_USER_CONTACTS
    private static ArrayList alConflict = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alRelshipGender = null;//to load from table TPA_RELATIONSHIP_CODE
    private static ArrayList alAdminAuthority = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alAllocatedType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alBatchStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alCourierStatus = null;//to load from table TPA_GENERAL_CODE
    
    private static ArrayList alContentType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alCourierName = null;//to load from table COURIER_COMPANY
    private static ArrayList alCourierDocType = null;//to load from table COURIER_COMPANY//added for courier
    
    private static ArrayList alOfficeType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alBankHeadOffice = null;//to load from TPA_BANK_MASTER
    private static ArrayList alAcctStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alTransType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alDepositTransType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alFloatTransType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alFloatType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alClaimType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alChequeStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alStaleChequeStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alBroRolesCode = null;//to load from TPA_ROLES_CODEalBroRolesCode
    private static ArrayList alBRODesignationCode = null; //to load from table TPA_DESIGNATION_CODE 
    
    //added as per KOC 1175 Change Request 
    private static ArrayList alChequeStatusWithOutStale=null;//to load from TPA_GENERAL_CODE
    //added as per KOC 1175 Change Request 
 
    private static ArrayList alCourierType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alBufferMode = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alEntryMode = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alSupportDocBuffer = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alBufferStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alClaimsSupportDoc = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alClaimsSupportDocBuffer = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alVoucherStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alPaymentMethod = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alInwardStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alClaimDocumentType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alClaimSource = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alClaimMode = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alRequestType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alClaimSubType = null;//to load from TPA_GENERAL_CODE
  //OPD_4_hosptial
    private static ArrayList alPaymentType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alDenialDescType = null;//to load from DenialDescription 
    private static ArrayList alModifiersType  = null;
    private static ArrayList alUnitTypes  = null;
    private static ArrayList alHealthCheckType = null;//to load from TPA_GENERAL_CODE
    
    private static ArrayList alObservationTypes=null;
    private static ArrayList alObservationCodes=null;
    private static ArrayList alObservationValueTypes=null;
  //OPD_4_hosptial
    private static ArrayList alDocType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alDocReasonType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alApproveReason = null; //to load from TPA_GENERAL_CODE
    private static ArrayList alAccountHeadType = null;//to load from TPA_HOSP_WARD_CODE
    //added for maternity 1164
    private static ArrayList alVaccineType = null;//to load from TPA_HOSP_WARD_CODE //added for maternity
    private static ArrayList alDischargeCondition = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alOnlineAccess = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alSummaryType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alCallerType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alCallLogType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alCallReason = null;//to load from TPA_CALL_REASON_CODE
    private static ArrayList alCallSubReason = null;//to load from TPA_CALL_SUB_REASON_CODE
    private static ArrayList alCallCategory = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alCallSubCategory = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alCallAnswerCode = null;//to load from TPA_CALL_ANSWER_CODE
    private static ArrayList alCallLoggedBy = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alCallRelateTo = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alCallStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alRuleAccountHeadType = null;//to load from TPA_HOSP_WARD_CODE
    private static ArrayList alCourierSource = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alPolicyTemplates = null;//to load from TPA_POLICY_TEMPLATES
    private static ArrayList alCallSource = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alBankName = null;//to load from TPA_BANK_MASTER
    private static ArrayList alChequeTemplate = null;//to load from CHEQUE_TEMPLATE
    private static ArrayList alDayCareProcedures = null;//to load from TPA_DAY_CARE_PROCEDURE
    private static ArrayList alVoucherRequired = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alNHCPLetterType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alMRLetterType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alLiabilityStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alProductChangeYN = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alDebitAssociate = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alDebitType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alDebitTypeDraft = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alPcsMonth = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alDayCareGroup = null;
    private static ArrayList alCurrencyGroup = null;//to load from TPA_DAYCARE_GROUP
    private static ArrayList alNetworkGroup = null;
    private static ArrayList alProviderNetworkGroup = null;
    private static ArrayList alTreatementType = null;
    private static ArrayList alDeductablesPubNetwork = null;
    private static ArrayList alDeductablesPriNetwork = null;
    private static ArrayList alPatientGroup = null;
    private static ArrayList alGovHospitalGroup = null;
    private static ArrayList alMaternityGroup = null;
    private static ArrayList alDrugsGroup = null;
    private static ArrayList alServicesList = null;
    private static ArrayList alDayCareList = null;
    private static ArrayList alChronicCondition=null;
    private static ArrayList alDieticianNeutricianList = null;
    private static ArrayList alWomenHormonalList=null;
  //added for KOC-1310
    private static ArrayList alCancerICDList = null;//to load from TPA_ICDList
    private static ArrayList alCancerICDGroup = null;//to load from TPA_ICDGROUP   
    
    private static ArrayList alMaternityICDGroup = null;
    private static ArrayList alDisabilityType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alDaycareproduct = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alSystemOfmedicine = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alAilmentClaimType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alCaseType = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginLink = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginLinkReports = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginHRAddition = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginEmpAddition = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginDOISettings = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginSumSettings = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginCancellation = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginSoftcopyUpload = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginMemConfigY = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginMemConfigN = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alWebLoginSendMailGenTypeID = null;
    private static ArrayList alWebLoginMailGenTypeID = null;//for mail_general_type_id
    private static ArrayList alEmployeeStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alEmpPwdGeneration = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alRelWindowPeriod = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alIntimationStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alIntimationAccesss = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alCashBenefit = null;//to load from TPA_GENERAL_CODE
    
    //added for KOC-1273
    private static ArrayList alCriticalGroups = null;//to load from tpa_critical_group 
    
    private static ArrayList alCaseStatus = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alSinceWhenCoding = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alSinceWhenCallCenter = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alClaimsPending = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alIOBSelectBatch = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alNotificationAccess = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alOnlineAssReqRelated = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alOnlineAssistance = null;//to load from TPA_GENERAL_CODE
    //Added for IBM.....KOC-1216
	private static ArrayList alWebLoginOptinSettings = null;//to load from TPA_GENERAL_CODE
   	//Ended...

    //Fax status reprot
    private static ArrayList alFaxGenUsers  = null;//to load from TPA_GENERAL_CODE
    private static ArrayList alFaxStatus = null;//to load from TPA_GENERAL_CODE

    //Adding notificatio cache in Empanelment Group registration --15/05/2008
    private static ArrayList alNotifinfo = null;//to load from TPA_GENERAL_CODE

    //Adding cache for Customer care view log history --25/09/2008
    private static ArrayList alViewLogHistory = null;//to load from TPA_GENERAL_CODE

    //Adding cache for Online Assistance status --27/10/2008
    private static ArrayList alSupportOnlineQuery = null;//to load from TPA_GENERAL_CODE

    //Adding cache for TDS Hostpital Status, Category-- 27/07/2009
    private static ArrayList alHospOwnerType = null;
    private static ArrayList alTdsHospCategory = null;//to load from TDS_HOSP_CATEGORY

    //Adding cache for TDS Remittance Status -- 07/08/2009
    private static ArrayList alTdsRemittanceStatus = null;

    //Adding cache for TDS Insurance Info -- 11/08/2009
    private static ArrayList alTdsInsuranceInfo = null;
    private static ArrayList alTdsAckInfo = null;

    //Adding cache for TDS claims under TDS Configuration -- 22/02/2010
    private static ArrayList alTdsClaimsInclExcl = null;
    private static ArrayList alsurgeryType = null;
    private static ArrayList alOnlineRating = null;
    private static ArrayList alOnlineFeedbackType = null;
    private static ArrayList alOnlineFeedbackStatus = null;
    private static ArrayList alOnlineFeedbackResponse = null;
    private static ArrayList alOnlineAssistFeedbackType= null;
    private static ArrayList alRelationshipCombination=null;
    private static ArrayList alSurgeryTypeMandatory= null;
    private static ArrayList alProductNetworkTypes= null;
    private static ArrayList alTdsBatchQuarter=null;
    private static ArrayList alMaternityType=null;
    //added for koc 1278
    private static ArrayList alAilmentType = null;//to load from table TPA_GENERAL_CODE
    //added for koc 1278


    //physiotherapy cr 1320
    private static ArrayList alPhysioMsgStatus=null;
    //physiotherapy cr 1320
    private static ArrayList alImmuneType = null;//added for koc 1315
    private static ArrayList alWellchildType = null;//added for koc 1316
    private static ArrayList alRoutAdultType = null;//added for koc 1308
    //added for koc 1349
    private static ArrayList wellnessAccess = null;//to load from table TPA_GENERAL_CODE
//added for kocb broker login
    private static ArrayList designationBRO = null;//to load from table TPA_GENERAL_CODE
    //AK
    private static ArrayList userRoleBRO = null;//to load from table TPA_GENERAL_CODE
    
    //added for koc 1349
  //added for 2koc 
    private static ArrayList alCourierStatusDsp = null;
    private static ArrayList alPolicyListIndpol = null;
    //to load from table TPA_GENERAL_CODE
    //added for 2koc 
    //added for bajaj enhance
    private static ArrayList alremType = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList alrsonType = null;//to load from table TPA_GENERAL_CODE bajaj enhance
	 //Shortfall CR KOC1179
    private static ArrayList alInsApprovalStatus = null;
    private static ArrayList alLevelslist = null;//added for hyundai buffer
    private static ArrayList alSupBufferTypeID = null;//added for hyundai buffer
    private static ArrayList alSupClaimTypeID = null;//added for hyundai buffer
    private static ArrayList alBufferTypeList = null;//added for hyundai buffer
    private static ArrayList alNormalbufferTypeList = null;//added for hyundai buffer
    private static ArrayList alAuthorizationTypeList = null;//authorization type
    private static ArrayList alOutPatientEncounterTypes=null;
    private static ArrayList alServiceTypes=null;
    private static ArrayList alActivityTypes=null;
    private static ArrayList alProviderList=null;
    private static ArrayList alMemberClaimFromList=null;
    private static ArrayList alClaimsSubmissionTypeList=null;
    private static ArrayList alPreauthStatusList=null;
    private static ArrayList alRoomType=null;    
    private static ArrayList alSystemOfMedicines=null;
    private static ArrayList alAccidentRelatedCases=null;
    private static ArrayList alBenefitTypes=null;
    private static ArrayList alPenefitTypes=null;
    private static ArrayList alBenefitTypesCallCenter=null;
    private static ArrayList alTariffTypesList = null;//tariffTypesList 
    private static ArrayList alOutPatientCoverageGroup = null;
    private static ArrayList alRoomTypeInpatient = null;
    private static ArrayList alRedAuthority 		=	null;//added for projectX
    private static ArrayList alproviderType 		= 	null;//added for projectX
    private static ArrayList alprimaryNetwork 		= 	null;//added for projectX
    private static ArrayList aldescriptionCode		=	null;//added for projectX
    private static ArrayList alconsultationCode		=	null;//added for projectX
    private static ArrayList alnationalityCode		=	null;//added for projectX
    private static ArrayList alproviderGroup		=	null;//added for projectX
    private static ArrayList algroupProviderList	=	null;//added for projectX added for projectX, Empanlement Group Info
    private static ArrayList alproviderSectorList	=	null;//added for projectX
    private static ArrayList albenifitType			=	null;//added for projectX
    private static ArrayList alspecialityType		=	null;//added for projectX
    private static ArrayList almodeTypes			=	null;//added for projectX
    private static ArrayList alPayerCode			=	null;//added for projectX, Tariff for Hospital
    private static ArrayList alProviderrCode		=	null;//added for projectX, Tariff for Hospital
    private static ArrayList alNetworkType			=	null;//added for projectX, Tariff for Hospital
    private static ArrayList alcorpCode				=	null;//added for projectX, Tariff for Hospital
    private static ArrayList alpayerCodeSearch				=	null;//added for projectX, Tariff for Hospital
    private static ArrayList alPayerCodeSearch		=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alProviderrCodeSearch	=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alNetworkTypeSearch	=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alcorpCodeSearch		=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alserviceName			=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alProfessionalsNames	=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alNetworksForTariff	=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alProviderCodeAdminTariff	=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alIns = null;
    private static ArrayList alProviderNames		=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alMaternityEncounters	=	null;
    private static ArrayList alHaadGroup			=	null;
    private static ArrayList alHaadfactor			=	null;
    private static ArrayList alActivityServiceTypes	=	null;
    private static ArrayList alActivityServiceCodes	=	null;
    private static ArrayList alpgroupCountry	=	null;
    private static ArrayList alethnicProfile	=	null;
    private static ArrayList algroupCurrency	=	null;
    private static ArrayList alnoofEmployees	=	null;
    private static ArrayList alaverageAgeEmployees	=	null;
    private static ArrayList alemployeeGenderBreak	=	null;
    private static ArrayList alglobalCoverge	=	null;
    private static ArrayList alfamilyCoverage	=	null;
    private static ArrayList alnameOfInsurer	=	null;
    private static ArrayList alnameOfTPA	=	null;
    private static ArrayList aleligibility	=	null;
    private static ArrayList alplanName	=	null;
    private static ArrayList alareaOfCover	=	null;
    private static ArrayList alsystemOfMedicineGroup	=	null;
    
    private static ArrayList alProvDocsType			=	null;
    private static ArrayList alalspeciality		=	null;//added for projectX, Tariff for Tariff
    private static ArrayList alCapitationCategory		=	null;//added for projectX, Tariff for Tariff
    
    private static ArrayList allCurencyCode=null;
    private static ArrayList insurenceCompanyList=null;
    private static ArrayList allDataType=null;
    private static ArrayList alProductList=null;
    private static ArrayList allCorporateList=null;
 
    private static ArrayList  alGeneralCodeClaim=null;
    private static ArrayList allpreauthCategory=null;
    private static ArrayList alXMLDashboards=null;
    private static ArrayList alCategory = null;
    private static ArrayList haadCategory=null;
    private static ArrayList alIalStateValueList = null;
    private static ArrayList alGeneralCodeproductAuthority = null;
    private static ArrayList alGeoLocations = null;
    private static ArrayList alPartnerLogTypeCode = null;//to load from table TPA_HOSP_LOG_CODE
    private static ArrayList alPartnerPreauthStatus = null;
    private static ArrayList alpartnerName=null;
    private static ArrayList partnerEncounterTypes = null;
    private static ArrayList main4benifitTypes			=	null;
    private static ArrayList apbenifitTypes			=	null;
    
    
    private static ArrayList alCountryCurrencyCodes=null;
    private static ArrayList alIpClmsPmtMethod=null;
    
    private static ArrayList alSubmissionCatagory = null;
    private static ArrayList alPrebenefitTypes	=	null;
    private static ArrayList alpaymentTo = null;
    private static ArrayList alBatchProviderList = null;
    private static ArrayList almaxBenifitList=null;
    private static ArrayList alnetworkList=null;
    private static ArrayList alinpatientAreaOfCover=null;
    private static ArrayList alroomType=null;
    private static ArrayList almaxChildAgeLimit=null;
    private static ArrayList alcompanionCharges=null;
    private static ArrayList aloutpatientAreaOfCover=null;
    private static ArrayList aladditionalHospitalCoverage=null;
    private static ArrayList alconsultationCD=null;
    private static ArrayList aldrugsLimit=null;
    private static ArrayList aldrugsCopay=null;
    private static ArrayList alphysiotherapyLimit=null;
    private static ArrayList allaboratoryCD=null;
    private static ArrayList alphysiotherapyCopay=null;
    private static ArrayList alnormalDeliveryLimit=null;
    
    private static ArrayList alincomeGroup=null;
    private static ArrayList alCSectionLimit=null;
    private static ArrayList alAuditUserRoleList = null;
    
    private static ArrayList almaternityOutpatientCopay=null;
    private static ArrayList almaternityInpatientCopay=null;
    private static ArrayList almaternityOutpatientVisits=null;
    private static ArrayList aldinpatientCopay=null;
    private static ArrayList alinpatientCopay=null;
    private static ArrayList alpremiumRefundApplicable=null;
    private static ArrayList alinpatientBenefit=null;
    private static ArrayList aloutpatientBenefit=null;
    //b
    private static ArrayList alrenewalYN=null;
    private static ArrayList aladministrativeServiceType=null;
    private static ArrayList alauthorityType=null;
    private static ArrayList alinsuranceCompanyName=null;
    private static ArrayList almaternityPricing=null;
    private static ArrayList alexistCompProd1=null;
    private static ArrayList alexistCompProd2=null;
    
    private static ArrayList alModeofpreapprovalforEnhanced = null;
    
    private static ArrayList alpolicyStatusForProductSync = null;
    
    private static ArrayList alpolicyStatusForScheme=null;
    private static ArrayList alinsNetworkType=null;
    private static ArrayList alPartnersList=null;
    
    private static ArrayList residentailLocation=null;
    private static ArrayList alPayerlist = null;
    private static ArrayList alPrimaryNetworkCheckBox = null;
    private static ArrayList payerlistfortariff = null;
    private static ArrayList alnetworkTypeForAMT = null;
    private static ArrayList alnetworkTypeForNIA = null;
    private static ArrayList alnetworkTypeForOIC = null;
    private static ArrayList roomTypes=null;
    private static ArrayList roomType=null;
    
    private static ArrayList passportIssuedCountry = null;
    private static ArrayList alProductSubType=null; 
   
    private static ArrayList alGroup_accounts=null;
    private static ArrayList alMapped_codes=null;
    private static ArrayList alAdmissionType=null;
    
    private static ArrayList alApplicableInsCompany = null;
    
    // govind start
    public static ArrayList getDynamicCacheObject(String strIdentifier,String strDynParam) throws TTKException
	{
		return loadDynamicObjects(strIdentifier, strDynParam);
	}
    
	private static ArrayList loadDynamicObjects(String strIdentifier,String strDynParam) throws TTKException
    {
		
            return getCacheManagerObject().loadDynamicObjects(strIdentifier, strDynParam);
            
        
    }//end of loadObjects(String strIdentifier, ArrayList alCacheObjects)
    
	 // govind end
    
    public static ArrayList getCacheObject1(String strIdentifier,Long strIdentifier1) throws TTKException
	{
		      	
    	
		alClaimShortfallUnderClause=null;
    	 if(strIdentifier.equals("claimShortfallUnderClause"))
         {
         	alClaimShortfallUnderClause = loadObjects1(strIdentifier, alClaimShortfallUnderClause,strIdentifier1);
             return alClaimShortfallUnderClause;
         }//end of if(strIdentifier.equals("claimShortfallUnderClause"))
 	    else{
 			return null;
 	    }//end of else	
	}//end of getCacheObject1(String strIdentifier,Long strIdentifier1)
    
    

    
    
    //Tariff - blocking Network types based on base network - INTX
    public static ArrayList getCacheObject1(String strIdentifier,String strIdentifier1, HttpServletRequest request) throws TTKException
	{
		      	
    	
    	alNetworksForTariff=null;
    	alProviderCodeAdminTariff=null;
    	alIns=null;
    	 if(strIdentifier.equals("networkTypeForTariff"))
         {
         	alNetworksForTariff = loadObjects1(strIdentifier, alNetworksForTariff,strIdentifier1,request);
             return alNetworksForTariff;
         }//end of if(strIdentifier.equals("networkTypeForTariff"))
    	 else if(strIdentifier.equals("insuranceCompany111"))
         {
    		 alIns = loadObjects1(strIdentifier, alIns,strIdentifier1,request);
             return alIns;
         }//end of if(strIdentifier.equals("networkTypeForTariff"))
    	 
    	 else if(strIdentifier.equals("providerCodeAdminTariff"))
         {
         	alProviderCodeAdminTariff = loadObjects1(strIdentifier, alProviderCodeAdminTariff,strIdentifier1,request);
             return alProviderCodeAdminTariff;
         }//end of if(strIdentifier.equals("providerCodeAdminTariff"))
    	 
 	    else{
 			return null;
 	    }//end of else	
	}//end of getCacheObject1(String strIdentifier,Long strIdentifier1)
	
    /**
     * Returns the ArrayList object which contains the cache objects
     * @param strIdentifier String object which identifies the arraylist to be populated
     * @return ArrayList object which contains the cache objects
     * @exception throws TTKException
     */
	
	public static ArrayList getCacheObject(String strIdentifier) throws TTKException
	{
		if(strIdentifier.equals("mapped_codes"))
	     {
			alMapped_codes = loadObjects(strIdentifier, alMapped_codes);
	            return alMapped_codes;
	     }
		if(strIdentifier.equals("group_accounts"))
	     {
			alGroup_accounts = loadObjects(strIdentifier, alGroup_accounts);
	            return alGroup_accounts;
	     }
		
		if(strIdentifier.equals("auditUserRoleList"))
	     {
			alAuditUserRoleList = loadObjects(strIdentifier, alAuditUserRoleList);
	            return alAuditUserRoleList;
	     }
		
		if(strIdentifier.equals("claimBatchProviderList"))
	     {
			alBatchProviderList = loadObjects(strIdentifier, alBatchProviderList);
	            return alBatchProviderList;
	     }
		
		if(strIdentifier.equals("paymentTo"))
	     {
			alpaymentTo = loadObjects(strIdentifier, alpaymentTo);
	            return alpaymentTo;
	     }
		if(strIdentifier.equals("prebenefitTypes"))
        {
			alPrebenefitTypes = loadObjects(strIdentifier, alPrebenefitTypes);
            return alPrebenefitTypes;
        }//end of if(strIdentifier.equals("ailmentType"))
		if(strIdentifier.equals("submissionCatagory"))
        {
			alSubmissionCatagory = loadObjects(strIdentifier, alSubmissionCatagory);
            return alSubmissionCatagory;
        }//end of if(strIdentifier.equals("ailmentType"))
		
		if(strIdentifier.equals("spreauthCategory"))
	     {
			   allpreauthCategory = loadObjects(strIdentifier, allpreauthCategory);
	            return allpreauthCategory;
	     }
		if(strIdentifier.equals("insuranceCompanyName"))
		{
			
		alinsuranceCompanyName = loadObjects(strIdentifier, alinsuranceCompanyName);
        return alinsuranceCompanyName;
    }
		if(strIdentifier.equals("existCompProd1"))
	     {
			alexistCompProd1 = loadObjects(strIdentifier, alexistCompProd1);
	            return alexistCompProd1;
	     }
		if(strIdentifier.equals("existCompProd2"))
	     {
			alexistCompProd2 = loadObjects(strIdentifier, alexistCompProd2);
	            return alexistCompProd2;
	     }
		 if(strIdentifier.equals("claims"))
        {
            alGeneralCodeClaim = loadObjects(strIdentifier, alGeneralCodeClaim);
            return alGeneralCodeClaim;
        }//end of if(strIdentifier.equals("enrollment"))
		if(strIdentifier.equals("insurenceCompanyList"))
		{
			insurenceCompanyList=loadObjects(strIdentifier, insurenceCompanyList);
			return insurenceCompanyList;
		}
		
		
		
		if(strIdentifier.equals("CorporateList"))
	     {
			allCorporateList = loadObjects(strIdentifier, allCorporateList);
	            return allCorporateList;
	     }
		
		if(strIdentifier.equals("partnerName"))
	     {
			alpartnerName = loadObjects(strIdentifier, alpartnerName);
	            return alpartnerName;
	     }
		
		
		if(strIdentifier.equals("allCurencyCode"))
	     {
	        	allCurencyCode = loadObjects(strIdentifier, allCurencyCode);
	            return allCurencyCode;
	     }
		if(strIdentifier.equals("DataType"))
	     {
			allDataType = loadObjects(strIdentifier, allDataType);
	            return allDataType;
	     }
		
		 else if(strIdentifier.equals("providerType"))
	        {
			 alproviderType = loadObjects(strIdentifier, alproviderType);
	            return alproviderType;
	        }
		
		if(strIdentifier.equals("corporateList"))
	     {
			allCorporateList = loadObjects(strIdentifier, allCorporateList);
	            return allCorporateList;
	     }
		
		else if(strIdentifier.equals("benefitTypes"))
        {
			alBenefitTypes = loadObjects(strIdentifier, alBenefitTypes);
            return alBenefitTypes;
        }//end of if(strIdentifier.equals("benifitTypes"))
		else if(strIdentifier.equals("patientTypes"))
        {
			alPenefitTypes = loadObjects(strIdentifier, alPenefitTypes);
            return alPenefitTypes;
        }//end of if(strIdentifier.equals("benifitTypes"))
		
		else if(strIdentifier.equals("benefitTypescallcenter"))
        {
			alBenefitTypesCallCenter = loadObjects(strIdentifier, alBenefitTypesCallCenter);
            return alBenefitTypesCallCenter;
        }//end of if(strIdentifier.equals("benifitTypes"))
		
		else if(strIdentifier.equals("admissionType"))
        {
			alAdmissionType = loadObjects(strIdentifier, alAdmissionType);
            return alAdmissionType;
        }//end of if(strIdentifier.equals("benifitTypes"))
		
		
		
		if(strIdentifier.equals("outPatientCoverageGroup"))
        {
			alOutPatientCoverageGroup = loadObjects(strIdentifier, alOutPatientCoverageGroup);
            return alOutPatientCoverageGroup;
        }//end of if(strIdentifier.equals("ailmentType"))
		
		if(strIdentifier.equals("roomTypeInpatient"))
        {
			alRoomTypeInpatient = loadObjects(strIdentifier, alRoomTypeInpatient);
            return alRoomTypeInpatient;
        }//end of if(strIdentifier.equals("ailmentType"))
		if(strIdentifier.equals("authorization"))
        {
			alAuthorizationTypeList = loadObjects(strIdentifier, alAuthorizationTypeList);
            return alAuthorizationTypeList;
        }//end of if(strIdentifier.equals("ailmentType"))		

		else if(strIdentifier.equals("category"))
        {
			alCategory = loadObjects(strIdentifier, alCategory);
            return alCategory;
        }//end of if(strIdentifier.equals("category"))
		else if(strIdentifier.equals("haadCategory"))
        {
			haadCategory = loadObjects(strIdentifier, haadCategory);
            return haadCategory;
        }//end of if(strIdentifier.equals("category"))
		else if(strIdentifier.equals("OutPatientEncounterTypes"))
        {
			alOutPatientEncounterTypes = loadObjects(strIdentifier, alOutPatientEncounterTypes);
            return alOutPatientEncounterTypes;
        }//end of if(strIdentifier.equals("OutPatientEncounterTypes"))
		else if(strIdentifier.equals("serviceTypes"))
        {
			alServiceTypes = loadObjects(strIdentifier, alServiceTypes);
            return alServiceTypes;
        }//end of if(strIdentifier.equals("OutPatientEncounterTypes"))
		else if(strIdentifier.equals("activityTypes"))
        {
			alActivityTypes = loadObjects(strIdentifier, alActivityTypes);
            return alActivityTypes;
        }//end of if(strIdentifier.equals("OutPatientEncounterTypes"))
		else if(strIdentifier.equals("ProviderList"))
        {
			alProviderList = loadObjects(strIdentifier, alProviderList);
            return alProviderList;
        }//end of if(strIdentifier.equals("ProviderList"))
		else if(strIdentifier.equals("MemberClaimFromList"))
        {
			alMemberClaimFromList = loadObjects(strIdentifier, alMemberClaimFromList);
            return alMemberClaimFromList;
        }//end of if(strIdentifier.equals("MemberClaimFromList"))
		else if(strIdentifier.equals("claimsSubmissionTypes"))
        {
			alClaimsSubmissionTypeList = loadObjects(strIdentifier, alClaimsSubmissionTypeList);
            return alClaimsSubmissionTypeList;
        }//end of if(strIdentifier.equals("claimsSubmissionTypes"))
		else if(strIdentifier.equals("preauthStatusList"))
        {
			alPreauthStatusList = loadObjects(strIdentifier, alPreauthStatusList);
            return alPreauthStatusList;
        }//end of if(strIdentifier.equals("preauthStatusList"))
		else if(strIdentifier.equals("roomType"))
        {
			alRoomType = loadObjects(strIdentifier, alRoomType);
            return alRoomType;
        }//end of if(strIdentifier.equals("preauthStatusList"))
		else if(strIdentifier.equals("systemOfMedicines"))
        {
			alSystemOfMedicines = loadObjects(strIdentifier, alSystemOfMedicines);
            return alSystemOfMedicines;
        }//end of if(strIdentifier.equals("systemOfMedicines"))
		else if(strIdentifier.equals("accidentRelatedCases"))
        {
			alAccidentRelatedCases = loadObjects(strIdentifier, alAccidentRelatedCases);
            return alAccidentRelatedCases;
        }//end of if(strIdentifier.equals("accidentRelatedCases"))
		
		else if(strIdentifier.equals("tariffTypes"))
        {
			alTariffTypesList = loadObjects(strIdentifier, alTariffTypesList);
            return alTariffTypesList;
        }//end of if(strIdentifier.equals("tariffTypes"))
		else if(strIdentifier.equals("normalbufferTypeList"))
        {
			alNormalbufferTypeList = loadObjects(strIdentifier, alNormalbufferTypeList);
            return alNormalbufferTypeList;
        }//end of if(strIdentifier.equals("ailmentType"))
		if(strIdentifier.equals("bufferTypeList"))
        {
			alBufferTypeList = loadObjects(strIdentifier, alBufferTypeList);
            return alBufferTypeList;
        }//end of if(strIdentifier.equals("ailmentType"))
		if(strIdentifier.equals("supClaimTypeID"))
        {
			alSupClaimTypeID = loadObjects(strIdentifier, alSupClaimTypeID);
            return alSupClaimTypeID;
        }//end of if(strIdentifier.equals("ailmentType"))
		if(strIdentifier.equals("supBufferTypeID"))
        {
			alSupBufferTypeID = loadObjects(strIdentifier, alSupBufferTypeID);
            return alSupBufferTypeID;
        }//end of if(strIdentifier.equals("ailmentType"))
		if(strIdentifier.equals("levelslist"))
        {
			alLevelslist = loadObjects(strIdentifier, alLevelslist);
            return alLevelslist;
        }//end of if(strIdentifier.equals("ailmentType"))
		
		//added for bajaj enhance
        //bikki
		
		if(strIdentifier.equals("maxBenifitList"))
        {
			almaxBenifitList = loadObjects(strIdentifier, almaxBenifitList);
            return almaxBenifitList;
        }
		if(strIdentifier.equals("networkList"))
        {
			alnetworkList = loadObjects(strIdentifier, alnetworkList);
            return alnetworkList;
        }
		if(strIdentifier.equals("inpatientAreaOfCover"))
        {
			alinpatientAreaOfCover = loadObjects(strIdentifier, alinpatientAreaOfCover);
            return alinpatientAreaOfCover;
        }
		if(strIdentifier.equals("roomType1"))
        {
			alroomType = loadObjects(strIdentifier, alroomType);
            return alroomType;
        }
		if(strIdentifier.equals("maxChildAgeLimit"))
        {
			almaxChildAgeLimit = loadObjects(strIdentifier, almaxChildAgeLimit);
            return almaxChildAgeLimit;
        }
		if(strIdentifier.equals("companionCharges"))
        {
			alcompanionCharges = loadObjects(strIdentifier, alcompanionCharges);
            return alcompanionCharges;
        }
	
		if(strIdentifier.equals("outpatientAreaOfCover"))
        {
			aloutpatientAreaOfCover = loadObjects(strIdentifier, aloutpatientAreaOfCover);
            return aloutpatientAreaOfCover;
        }
		if(strIdentifier.equals("additionalHospitalCoverage"))
        {
			aladditionalHospitalCoverage = loadObjects(strIdentifier, aladditionalHospitalCoverage);
            return aladditionalHospitalCoverage;
        }
		if(strIdentifier.equals("consultationCD"))
        {
			alconsultationCD = loadObjects(strIdentifier, alconsultationCD);
            return alconsultationCD;
        }
		if(strIdentifier.equals("drugsLimit"))
        {
			aldrugsLimit = loadObjects(strIdentifier, aldrugsLimit);
            return aldrugsLimit;
        }
		if(strIdentifier.equals("drugsCopay"))
        {
			aldrugsCopay = loadObjects(strIdentifier, aldrugsCopay);
            return aldrugsCopay;
        }
		if(strIdentifier.equals("physiotherapyLimit"))
        {
			alphysiotherapyLimit = loadObjects(strIdentifier, alphysiotherapyLimit);
            return alphysiotherapyLimit;
        }
		if(strIdentifier.equals("laboratoryCD"))
        {
			allaboratoryCD = loadObjects(strIdentifier, allaboratoryCD);
            return allaboratoryCD;
        }
		if(strIdentifier.equals("physiotherapyCopay"))
        {
			alphysiotherapyCopay = loadObjects(strIdentifier, alphysiotherapyCopay);
            return alphysiotherapyCopay;
        }
		
		if(strIdentifier.equals("normalDeliveryLimit"))
        {
			alnormalDeliveryLimit = loadObjects(strIdentifier, alnormalDeliveryLimit);
            return alnormalDeliveryLimit;
        }
		
		
		if(strIdentifier.equals("incomeGroup"))
        {
			alincomeGroup = loadObjects(strIdentifier, alincomeGroup);
            return alincomeGroup;
        }
		if(strIdentifier.equals("CSectionLimit"))
        {
			alCSectionLimit = loadObjects(strIdentifier, alCSectionLimit);
            return alCSectionLimit;
        }
		
		if(strIdentifier.equals("maternityOutpatientCopay"))
        {
			almaternityOutpatientCopay = loadObjects(strIdentifier, almaternityOutpatientCopay);
            return almaternityOutpatientCopay;
        }
		if(strIdentifier.equals("maternityInpatientCopay"))
        {
			almaternityInpatientCopay = loadObjects(strIdentifier, almaternityInpatientCopay);
            return almaternityInpatientCopay;
        }
		if(strIdentifier.equals("maternityOutpatientVisits"))
        {
			almaternityOutpatientVisits = loadObjects(strIdentifier, almaternityOutpatientVisits);
            return almaternityOutpatientVisits;
        }
		if(strIdentifier.equals("dinpatientCopay"))
        {
			aldinpatientCopay = loadObjects(strIdentifier, aldinpatientCopay);
            return aldinpatientCopay;
        }
		if(strIdentifier.equals("inpatientCopay"))
        {
			alinpatientCopay = loadObjects(strIdentifier, alinpatientCopay);
            return alinpatientCopay;
        }
		if(strIdentifier.equals("premiumRefundApplicable"))
        {
			alpremiumRefundApplicable = loadObjects(strIdentifier, alpremiumRefundApplicable);
            return alpremiumRefundApplicable;
        }
		if(strIdentifier.equals("inpatientBenefit"))
        {
			alinpatientBenefit = loadObjects(strIdentifier, alinpatientBenefit);
            return alinpatientBenefit;
        }
		if(strIdentifier.equals("outpatientBenefit"))
        {
			aloutpatientBenefit = loadObjects(strIdentifier, aloutpatientBenefit);
            return aloutpatientBenefit;
        }
		//b
		if(strIdentifier.equals("renewalYN"))
        {
			alrenewalYN = loadObjects(strIdentifier, alrenewalYN);
            return alrenewalYN;
        }
		if(strIdentifier.equals("administrativeServiceType"))
        {
			aladministrativeServiceType = loadObjects(strIdentifier, aladministrativeServiceType);
            return aladministrativeServiceType;
        }
		if(strIdentifier.equals("authorityType"))
        {
			alauthorityType = loadObjects(strIdentifier, alauthorityType);
            return alauthorityType;
        }
		
	
		if(strIdentifier.equals("maternityPricing"))
        {
			almaternityPricing = loadObjects(strIdentifier, almaternityPricing);
            return almaternityPricing;
        }
//end bikki
		//added for koc 1349
        if(strIdentifier.equals("designationBRO"))
        {
        	designationBRO = loadObjects(strIdentifier, designationBRO);
            return designationBRO;
        }//end of if(strIdentifier.equals("ailmentType"))
		//added for koc 1349
        //AK
      //added for koc 1349
        if(strIdentifier.equals("userRoleBRO"))
        {
        	userRoleBRO = loadObjects(strIdentifier, userRoleBRO);
            return userRoleBRO;
        }//end of if(strIdentifier.equals("ailmentType"))
		//added for koc 1349
        
	//physiotherapy cr 1320
	    if(strIdentifier.equals("physioMsgStatus"))
        {
	    	alPhysioMsgStatus = loadObjects(strIdentifier, alPhysioMsgStatus);
            return alPhysioMsgStatus;
        }//end of if(strIdentifier.equals("GradeCode"))
	    //physiotherapy cr 1320
	//added for bajaj enhance
		
		
		if(strIdentifier.equals("insApprovalStatus"))
        {
			alInsApprovalStatus = loadObjects(strIdentifier, alInsApprovalStatus);
            return alInsApprovalStatus;
        }//end of if(strIdentifier.equals("ailmentType"))
		if(strIdentifier.equals("insReqType"))
        {
			alrsonType = loadObjects(strIdentifier, alrsonType);
            return alrsonType;
        }//end of if(strIdentifier.equals("ailmentType"))
		 if(strIdentifier.equals("remType"))
	        {
			 alremType = loadObjects(strIdentifier, alremType);
	            return alremType;
	        }//end of if(strIdentifier.equals("ailmentType"))
			//added for bajaj enhance
//added for 2koc 
        if(strIdentifier.equals("courierStatusDsp"))
        {
        	alCourierStatusDsp = loadObjects(strIdentifier, alCourierStatusDsp);
            return alCourierStatusDsp;
        }//end of if(strIdentifier.equals("ailmentType"))
        if(strIdentifier.equals("alPolicyListInd"))
        {
        	alPolicyListIndpol = loadObjects(strIdentifier, alPolicyListIndpol);
            return alPolicyListIndpol;
        }//end of if(strIdentifier.equals("ailmentType"))
		//added for 2koc 
		//added for koc 1349
        if(strIdentifier.equals("wellnessAccess"))
        {
        	wellnessAccess = loadObjects(strIdentifier, wellnessAccess);
            return wellnessAccess;
        }//end of if(strIdentifier.equals("ailmentType"))
		//added for koc 1349
		
		//added for koc 1278
        if(strIdentifier.equals("ailmentType"))
        {
        	alAilmentType = loadObjects(strIdentifier, alAilmentType);
            return alAilmentType;
        }//end of if(strIdentifier.equals("ailmentType"))
		//added for koc 1278
    	//added as per KOC 1285 
		 if(strIdentifier.equals("domHospReason"))
	        {
			 alDomHospReason = loadObjects(strIdentifier, alDomHospReason);
	            return alDomHospReason;
	        }//end of if(strIdentifier.equals("GradeCode"))
		//added as per KOC 1285 
	//Added as Per Shortfall  Shortfall CR KOC1179 Cr
		 if(strIdentifier.equals("clausefor"))
	        {
			 alClauseType = loadObjects(strIdentifier, alClauseType);
	            return alClauseType;
	        }//end of if(strIdentifier.equals("clausefor"))
		 if(strIdentifier.equals("clausesubtype"))
	        {
			 alClauseSubType = loadObjects(strIdentifier, alClauseSubType);
	            return alClauseSubType;
	        }//end of if(strIdentifier.equals("clausesubtype"))
		/*
		 * This code is added for cr koc 1103
		 * added eft
		 */
	    if(strIdentifier.equals("destnationbank"))
        {
	    	alDestnationBank = loadObjects(strIdentifier, alDestnationBank);
            return alDestnationBank;
        }//end of if(strIdentifier.equals("GradeCode"))

	    if(strIdentifier.equals("alCityList"))
        {
	    	alCityList = loadObjects(strIdentifier, alCityList);
            return alCityList;
            
        }//end of if(strIdentifier.equals("GradeCode"))
	    
		 else if(strIdentifier.equals("shortfallStatus"))
        {
        	alShortfallStatus = loadObjects(strIdentifier, alShortfallStatus);
            return alShortfallStatus;
        }//end of if(strIdentifier.equals("shortfallStatus"))
		// Shortfall CR KOC1179
	    
		else if(strIdentifier.equals("claimShortfallStatus"))
        {
	    	alShortfallStatusList = loadObjects(strIdentifier, alShortfallStatusList);
            return alShortfallStatusList;
        }//end of if(strIdentifier.equals("claimShortfallStatus"))
	    //End Shortfall CR KOC1179
	     else if(strIdentifier.equals("accountupdate"))
        {
        	alGeneralAccountUpdate = loadObjects(strIdentifier, alGeneralAccountUpdate);
            return alGeneralAccountUpdate;
        }//end of if(strIdentifier.equals("enrollment"))
        else if(strIdentifier.equals("accounttype"))
        {
        	alGeneralAccountType = loadObjects(strIdentifier, alGeneralAccountType);
            return alGeneralAccountType;
        }//end of if(strIdentifier.equals("enrollment"))
	    else if(strIdentifier.equals("paymentMethod1"))
        {
        	alPaymentMethod1 = loadObjects(strIdentifier, alPaymentMethod1);
            return alPaymentMethod1;
        }//end of if(strIdentifier.equals("paymentMethod"))
		 // Changes added for Debit Note CR KOC1163
	    else if(strIdentifier.equals("paymentMethod2"))
        {
        	alPaymentMethod2 = loadObjects(strIdentifier, alPaymentMethod2);
            return alPaymentMethod2;
        }//end of if(strIdentifier.equals("paymentMethod2"))
		
		else if(strIdentifier.equals("regioncode"))//Region Based Premium KOC 1284 Change Request
        {
	    	alRegions = loadObjects(strIdentifier, alRegions);
            return alRegions;
        }//end of if(strIdentifier.equals("regioncode"))
	    
	    else if(strIdentifier.equals("allregioncode"))//Region Based Premium KOC 1284 Change Request
        {
	    	alRegionsCode = loadObjects(strIdentifier, alRegionsCode);
            return alRegionsCode;
        }//end of if(strIdentifier.equals("allregioncode"))
	    		
	    //	End changes added for Debit Note CR KOC1163
	    /*
		 *End koc 1103
		 * End eft
		 */

		if(strIdentifier.equals("gradeCode"))
        {
            alGradeCode = loadObjects(strIdentifier, alGradeCode);
            return alGradeCode;
        }//end of if(strIdentifier.equals("GradeCode"))
        else if(strIdentifier.equals("countryCode") || strIdentifier.equals("payOrderCountryCode"))
        {
            alCountryCode = loadObjects(strIdentifier, alCountryCode);
            return alCountryCode;
        }//end of if(strIdentifier.equals("CountryCode"))       
        else if(strIdentifier.equals("districtCode"))
        {
        	alDistrictCode = loadObjects(strIdentifier, alDistrictCode);
            return alDistrictCode;
        }//end of if(strIdentifier.equals("CountryCode"))       
        else if(strIdentifier.equals("checkeStatus"))
        {
            alCountryCode = loadObjects(strIdentifier, alCountryCode);
            return alCountryCode;
        }//end of if(strIdentifier.equals("CountryCode"))
        else if(strIdentifier.equals("countryCode") || strIdentifier.equals("payOrderCountryCode"))
        {
            alCountryCode = loadObjects(strIdentifier, alCountryCode);
            return alCountryCode;
        }//end of if(strIdentifier.equals("CountryCode"))
        else if(strIdentifier.equals("stateCode") || strIdentifier.equals("payOrderStateCode"))
        {
            alStateCode = loadObjects(strIdentifier, alStateCode);
            return alStateCode;
        }//end of if(strIdentifier.equals("StateCode"))
		
        else if(strIdentifier.equals("productCode"))
        {
        	alInsProductCode = loadObjects(strIdentifier, alInsProductCode);
            return alInsProductCode;
        }
	
        else if(strIdentifier.equals("cityCode") || strIdentifier.equals("payOrderCityCode"))
        {
            alCityCode = loadObjects(strIdentifier, alCityCode);
            return alCityCode;
        }//end of if(strIdentifier.equals("CityCode"))
        else if(strIdentifier.equals("empanelStatusCode"))
        {
            alEmpanelStatusCode = loadObjects(strIdentifier, alEmpanelStatusCode);
            return alEmpanelStatusCode;
        }//end of if(strIdentifier.equals("EmpanelStatusCode"))
        else if(strIdentifier.equals("empanelTypeCode"))
        {
            alEmpanelTypeCode = loadObjects(strIdentifier, alEmpanelTypeCode);
            return alEmpanelTypeCode;
        }//end of if(strIdentifier.equals("EmpanelTypeCode"))
        else if(strIdentifier.equals("mouCode"))
        {
            alMouCode = loadObjects(strIdentifier, alMouCode);
            return alMouCode;
        }//end of if(strIdentifier.equals("MouCode"))
        else if(strIdentifier.equals("commCode"))
        {
            alCommCode = loadObjects(strIdentifier, alCommCode);
            return alCommCode;
        }//end of if(strIdentifier.equals("CommCode"))
        else if(strIdentifier.equals("contactTypeCode"))
        {
        	alContactTypeCode = loadObjects(strIdentifier, alContactTypeCode);
            return alContactTypeCode;
        }//end of if(strIdentifier.equals("contactTypeCode"))
        else if(strIdentifier.equals("regioncode"))//Region Based Premium KOC 1284 Change Request
        {
	    	alRegions = loadObjects(strIdentifier, alRegions);
            return alRegions;
        }//end of if(strIdentifier.equals("regioncode"))
	    
	    else if(strIdentifier.equals("allregioncode"))//Region Based Premium KOC 1284 Change Request
        {
	    	alRegionsCode = loadObjects(strIdentifier, alRegionsCode);
            return alRegionsCode;
        }//end of if(strIdentifier.equals("allregioncode"))
	       else if(strIdentifier.equals("drSpecialityCode"))
        {
        	alDrSpecialityCode = loadObjects(strIdentifier, alDrSpecialityCode);
            return alDrSpecialityCode;
        }//end of if(strIdentifier.equals("drSpecialityCode"))
        else if(strIdentifier.equals("logTypeCode"))
        {
        	alLogTypeCode = loadObjects(strIdentifier, alLogTypeCode);
            return alLogTypeCode;
        }//end of if(strIdentifier.equals("logCode"))
        else if(strIdentifier.equals("partnerLogTypeCode"))
        {
        	alPartnerLogTypeCode = loadObjects(strIdentifier, alPartnerLogTypeCode);
            return alPartnerLogTypeCode;
        }//end of if(strIdentifier.equals("logCode"))
        /*else if(strIdentifier.equals("addressTypeCode"))
        {
        	alAddressTypeCode = loadObjects(strIdentifier, alAddressTypeCode);
            return alAddressTypeCode;
        }//end of if(strIdentifier.equals("addressTypeCode"))
        else if(strIdentifier.equals("commTypeCode"))
        {
        	alCommTypeCode = loadObjects(strIdentifier, alCommTypeCode);
            return alCommTypeCode;
        }//end of if(strIdentifier.equals("commTypeCode"))
        else if(strIdentifier.equals("userTypeCode"))
        {
        	alUserTypeCode = loadObjects(strIdentifier, alUserTypeCode);
            return alUserTypeCode;
        }//end of if(strIdentifier.equals("userTypeCode")) */
        else if(strIdentifier.equals("designationTypeID"))
        {
        	alDesignationCode = loadObjects(strIdentifier, alDesignationCode);
            return alDesignationCode;
        }//end of if(strIdentifier.equals("designationTypeID"))
        else if(strIdentifier.equals("enrolTypeCode"))
        {
        	alEnrolTypeCode = loadObjects(strIdentifier, alEnrolTypeCode);
            return alEnrolTypeCode;
        }//end of if(strIdentifier.equals("enrolTypeCode"))
        else if(strIdentifier.equals("answerCode"))
        {
        	alAnswerCode = loadObjects(strIdentifier, alAnswerCode);
            return alAnswerCode;
        }//end of if(strIdentifier.equals("answerCode"))
        else if(strIdentifier.equals("categoryCode"))
        {
        	alCategoryCode = loadObjects(strIdentifier, alCategoryCode);
            return alCategoryCode;
        }//end of if(strIdentifier.equals("categoryCode"))
        else if(strIdentifier.equals("hospCode"))
        {
        	alHospCode = loadObjects(strIdentifier, alHospCode);
            return alHospCode;
        }//end of if(strIdentifier.equals("hospCode"))
        else if(strIdentifier.equals("empanelRsonCode"))
        {
        	alEmpanelRsonCode = loadObjects(strIdentifier, alEmpanelRsonCode);
            return alEmpanelRsonCode;
        }//end of if(strIdentifier.equals("empanelRsonCode"))
        else if(strIdentifier.equals("locationCode"))
        {
        	alLocationCode = loadObjects(strIdentifier, alLocationCode);
            return alLocationCode;
        }//end of if(strIdentifier.equals("locationCode"))
		//KOC Cigna_insurance_resriction 
        else if(strIdentifier.equals("userRestriction"))
        {
        	aluserRestriction = loadObjects(strIdentifier, aluserRestriction);
            return aluserRestriction;
        }//end of if(strIdentifier.equals("userRestriction"))
        else if(strIdentifier.equals("userRestrictionGroup"))
        {
        	aluserRestrictionGroup = loadObjects(strIdentifier, aluserRestrictionGroup);
            return aluserRestrictionGroup;
        }//end of if(strIdentifier.equals("userRestrictionGroup"))
		
		//KOC Cigna_insurance_resriction
        else if(strIdentifier.equals("medicalCode"))
        {
        	alMedicalCode = loadObjects(strIdentifier, alMedicalCode);
            return alMedicalCode;
        }//end of if(strIdentifier.equals("medicalCode"))
        else if(strIdentifier.equals("planCode"))
        {
        	alPlanCode = loadObjects(strIdentifier, alPlanCode);
            return alPlanCode;
        }//end of if(strIdentifier.equals("planCode"))
        else if(strIdentifier.equals("procedureCode"))
        {
        	alProcedureCode = loadObjects(strIdentifier, alProcedureCode);
            return alProcedureCode;
        }//end of if(strIdentifier.equals("procedureCode"))
        else if(strIdentifier.equals("questCode"))
        {
        	alQuestCode = loadObjects(strIdentifier, alQuestCode);
            return alQuestCode;
        }//end of if(strIdentifier.equals("questCode"))
        else if(strIdentifier.equals("roomsCode"))
        {
        	alRoomsCode = loadObjects(strIdentifier, alRoomsCode);
            return alRoomsCode;
        }//end of if(strIdentifier.equals("roomsCode"))
        else if(strIdentifier.equals("medicalWardCode"))
        {
            alMedicalNonPkgWardCode = loadObjects(strIdentifier, alMedicalNonPkgWardCode);
            return alMedicalNonPkgWardCode;
        }//end of if(strIdentifier.equals("medicalWardCode"))
        else if(strIdentifier.equals("nonMedicalWardCode"))
        {
            alNonMedicalNonPkgWardCode = loadObjects(strIdentifier, alNonMedicalNonPkgWardCode);
            return alNonMedicalNonPkgWardCode;
        }//end of if(strIdentifier.equals("nonMedicalWardCode"))
        else if(strIdentifier.equals("relationshipCode"))
        {
        	alRelationshipCode = loadObjects(strIdentifier, alRelationshipCode);
            return alRelationshipCode;
        }//end of if(strIdentifier.equals("relationshipCode"))
        else if(strIdentifier.equals("officeInfo"))
        {
        	alOfficeInfoCode = loadObjects(strIdentifier, alOfficeInfoCode);
            return alOfficeInfoCode;
        }//end of if(strIdentifier.equals("officeInfo"))
        else if(strIdentifier.equals("modeOfClaims"))
        {
        	alModeOfClaims = loadObjects(strIdentifier, alModeOfClaims);
            return alModeOfClaims;
        }//end of if(strIdentifier.equals("officeInfo"))
        else if(strIdentifier.equals("currencyList"))
        {
        	alcurrencyList = loadObjects(strIdentifier, alcurrencyList);
            return alcurrencyList;
        }//end of if(strIdentifier.equals("currencyList"))
        else if(strIdentifier.equals("userInfo"))
        {
        	alUserInfoCode = loadObjects(strIdentifier, alUserInfoCode);
            return alUserInfoCode;
        }//end of if(strIdentifier.equals("userInfo"))
        else if(strIdentifier.equals("chequeCode"))
        {
        	alIssuesChequesCode = loadObjects(strIdentifier,alIssuesChequesCode);
        	return alIssuesChequesCode;
        }//end of if(strIdentifier.equals("issuesChequesCode"))
		

        else if(strIdentifier.equals("alStateValueList"))
              {
              	alIalStateValueList = loadObjects(strIdentifier,alIalStateValueList);
              	return alIalStateValueList;
              }

        else if(strIdentifier.equals("payOrderType"))
        {
        	alPayOrderCode = loadObjects(strIdentifier,alPayOrderCode);
        	return alPayOrderCode;
        }//end of if(strIdentifier.equals("payOrderType"))
        else if(strIdentifier.equals("modReason"))
        {
        	alModReasonCode = loadObjects(strIdentifier,alModReasonCode);
        	return alModReasonCode;
        }//end of if(strIdentifier.equals("modReason"))
        else if(strIdentifier.equals("generalCodeDecision"))
        {
            alGeneralCodeDecision = loadObjects(strIdentifier,alGeneralCodeDecision);
            return alGeneralCodeDecision;
        }//end of if(strIdentifier.equals("generalCodeDecision"))
        else if(strIdentifier.equals("generalCodePlan"))
        {
            alGeneralCodePlan = loadObjects(strIdentifier,alGeneralCodePlan);
            return alGeneralCodePlan;
        }//end of if(strIdentifier.equals("generalCodePlan"))
        else if(strIdentifier.equals("providerStatus"))
        {
            alGeneralCodeProviderStatus = loadObjects(strIdentifier,alGeneralCodeProviderStatus);
            return alGeneralCodeProviderStatus;
        }//end of if(strIdentifier.equals("providerStatus"))
        else if(strIdentifier.equals("associateCode"))
        {
            alGeneralCodeAssociate = loadObjects(strIdentifier,alGeneralCodeAssociate);
            return alGeneralCodeAssociate;
        }//end of if(strIdentifier.equals("associateCode"))
        else if(strIdentifier.equals("associateHosCode"))
        {
            alGeneralCodeAssociateHos = loadObjects(strIdentifier,alGeneralCodeAssociateHos);
            return alGeneralCodeAssociateHos;
        }//end of if(strIdentifier.equals("associateCode"))
        else if(strIdentifier.equals("validationStatus"))
        {
            alGeneralCodeValidationStatus = loadObjects(strIdentifier,alGeneralCodeValidationStatus);
            return alGeneralCodeValidationStatus;
        }//end of if(strIdentifier.equals("validationStatus"))
        else if(strIdentifier.equals("enrollTypeCode"))
        {
        	alEnrollmentTypeCode = loadObjects(strIdentifier,alEnrollmentTypeCode);
            return alEnrollmentTypeCode;
        }//end of if(strIdentifier.equals("enrollTypeCode"))
        else if(strIdentifier.equals("productCode"))
        {
        	alProductCode = loadObjects(strIdentifier,alProductCode);
            return alProductCode;
        }//end of if(strIdentifier.equals("productCode"))
        else if(strIdentifier.equals("honourCode"))
        {
            alGeneralCodeHonour = loadObjects(strIdentifier,alGeneralCodeHonour);
            return alGeneralCodeHonour;
        }//end of if(strIdentifier.equals("honourCode"))
        else if(strIdentifier.equals("insuranceCompany"))
        {
            alInsCompanyNames = loadObjects(strIdentifier,alInsCompanyNames);
            return alInsCompanyNames;
        }//end of if(strIdentifier.equals("insuranceCompany"))
        else if(strIdentifier.equals("healthAuthorities"))
        {
            alhealthAuthorities = loadObjects(strIdentifier,alhealthAuthorities);
            return alhealthAuthorities;
        }//end of if(strIdentifier.equals("healthAuthorities")) 
		else if(strIdentifier.equals("brokerCompany"))
        {
            alBroCompanyNames = loadObjects(strIdentifier,alBroCompanyNames);
            return alBroCompanyNames;
        }//end of if(strIdentifier.equals("brokerCompany"))
        else if(strIdentifier.equals("BRO"))
        {
        	alBroRolesCode = loadObjects(strIdentifier,alBroRolesCode);
            return alBroRolesCode;
        }//end of if(strIdentifier.equals("BRO"))
        else if(strIdentifier.equals("productTypeCode"))
        {
            alGeneralCodeProductType = loadObjects(strIdentifier,alGeneralCodeProductType);
            return alGeneralCodeProductType;
        }//end of if(strIdentifier.equals("productTypeCode"))
        else if(strIdentifier.equals("productStatusCode"))
        {
            alGeneralCodeProductStatus = loadObjects(strIdentifier,alGeneralCodeProductStatus);
            return alGeneralCodeProductStatus;
        }//end of if(strIdentifier.equals("productStatusCode"))
		
		
		
        else if(strIdentifier.equals("mouAdminStatusCode"))
        {
            alGeneralCodeAdminMouStatus = loadObjects(strIdentifier,alGeneralCodeAdminMouStatus);
            return alGeneralCodeAdminMouStatus;
        }//end of if(strIdentifier.equals("mouAdminStatusCode"))
        else if(strIdentifier.equals("mouArticle"))
        {
            alHospMouArticle = loadObjects(strIdentifier,alHospMouArticle);
            return alHospMouArticle;
        }//end of if(strIdentifier.equals("mouArticle"))
        else if(strIdentifier.equals("mouHospStatusCode"))
        {
            alGeneralCodeHospMouStatus = loadObjects(strIdentifier,alGeneralCodeHospMouStatus);
            return alGeneralCodeHospMouStatus;
        }//end of if(strIdentifier.equals("mouHospStatusCode"))
        else if(strIdentifier.equals("packageCode"))
        {
            alGeneralCodePackage = loadObjects(strIdentifier,alGeneralCodePackage);
            return alGeneralCodePackage;
        }//end of if(strIdentifier.equals("packageCode"))
        else if(strIdentifier.equals("sectorTypeCode"))
        {
            alGeneralCodeSectorType = loadObjects(strIdentifier,alGeneralCodeSectorType);
            return alGeneralCodeSectorType;
        }//end of if(strIdentifier.equals("sectorTypeCode"))
        else if(strIdentifier.equals("fundDisbursalCode"))
        {
            alInsDisbursalCode = loadObjects(strIdentifier,alInsDisbursalCode);
            return alInsDisbursalCode;
        }//end of if(strIdentifier.equals("fundDisbursalCode"))
        else if(strIdentifier.equals("frequencyCode"))
        {
            alInsFrequencyCode = loadObjects(strIdentifier,alInsFrequencyCode);
            return alInsFrequencyCode;
        }//end of if(strIdentifier.equals("frequencyCode"))
        else if(strIdentifier.equals("empHistory"))
        {
            alEmpHistory = loadObjects(strIdentifier,alEmpHistory);
            return alEmpHistory;
        }//end of if(strIdentifier.equals("empHistory"))
        else if(strIdentifier.equals("userStatus"))
        {
            alGeneralCodeUserStatus = loadObjects(strIdentifier,alGeneralCodeUserStatus);
            return alGeneralCodeUserStatus;
        }//end of if(strIdentifier.equals("userStatus"))
        else if(strIdentifier.equals("prefix"))
        {
            alGeneralCodePrefixName = loadObjects(strIdentifier,alGeneralCodePrefixName);
            return alGeneralCodePrefixName;
        }//end of if(strIdentifier.equals("prefix"))
        else if(strIdentifier.equals("tpaUsers"))
        {
            alGeneralCodeTPAUsers = loadObjects(strIdentifier,alGeneralCodeTPAUsers);
            return alGeneralCodeTPAUsers;
        }//end of if(strIdentifier.equals("tpaUsers"))
        else if(strIdentifier.equals("HOS"))
        {
            alHosRolesCode = loadObjects(strIdentifier,alHosRolesCode);
            return alHosRolesCode;
        }//end of if(strIdentifier.equals("HOS"))
        else if(strIdentifier.equals("PTR"))
        {
        	
              alPtrRolesCode = loadObjects(strIdentifier,alPtrRolesCode);
              return alPtrRolesCode;
        }//end of if(strIdentifier.equals("PTR"))
        else if(strIdentifier.equals("INS"))
        {
            alInsRolesCode = loadObjects(strIdentifier,alInsRolesCode);
            return alInsRolesCode;
        }//end of if(strIdentifier.equals("INS"))
        else if(strIdentifier.equals("TTK"))
        {
            alTTKRolesCode = loadObjects(strIdentifier,alTTKRolesCode);
            return alTTKRolesCode;
        }//end of if(strIdentifier.equals("TTK"))
		
        else if(strIdentifier.equals("CAL"))
        {
            alCalRolesCode = loadObjects(strIdentifier,alCalRolesCode);
            return alCalRolesCode;
        }//end of if(strIdentifier.equals("CAL"))
        else if(strIdentifier.equals("COR"))
        {
            alCorRolesCode = loadObjects(strIdentifier,alCorRolesCode);
            return alCorRolesCode;
        }//end of if(strIdentifier.equals("COR"))
        else if(strIdentifier.equals("AGN"))
        {
            alAgnRolesCode = loadObjects(strIdentifier,alAgnRolesCode);
            return alAgnRolesCode;
        }//end of if(strIdentifier.equals("AGN"))
        else if(strIdentifier.equals("DMC"))
        {
            alDmcRolesCode = loadObjects(strIdentifier,alDmcRolesCode);
            return alDmcRolesCode;
        }//end of if(strIdentifier.equals("DMC"))
		
        else if(strIdentifier.equals("designationBRO"))
        {
        	alBRODesignationCode = loadObjects(strIdentifier, alBRODesignationCode);
            return alBRODesignationCode;
        }//end of if(strIdentifier.equals("designationTTK"))

        else if(strIdentifier.equals("associateUsers"))
        {
            alGeneralCodeAssociatedUsers = loadObjects(strIdentifier,alGeneralCodeAssociatedUsers);
            return alGeneralCodeAssociatedUsers;
        }//end of if(strIdentifier.equals("associateUsers"))
        else if(strIdentifier.equals("groupType"))
        {
            alGeneralCodeGroupType = loadObjects(strIdentifier,alGeneralCodeGroupType);
            return alGeneralCodeGroupType;
        }//end of if(strIdentifier.equals("groupType"))
        else if(strIdentifier.equals("departmentID"))
        {
            alGenearlCodeDepartmentID = loadObjects(strIdentifier,alGenearlCodeDepartmentID);
            return alGenearlCodeDepartmentID;
        }//end of if(strIdentifier.equals("departmentID"))
        else if(strIdentifier.equals("designationTTK"))
        {
            alTTKDesignationCode = loadObjects(strIdentifier, alTTKDesignationCode);
            return alTTKDesignationCode;
        }//end of if(strIdentifier.equals("designationTTK"))
        else if(strIdentifier.equals("designationBAK"))
        {
        	alBAKDesignationCode = loadObjects(strIdentifier, alBAKDesignationCode);
            return alBAKDesignationCode;
        }//end of if(strIdentifier.equals("designationBAK"))
        else if(strIdentifier.equals("designationCAL"))
        {
            alCALDesignationCode = loadObjects(strIdentifier, alCALDesignationCode);
            return alCALDesignationCode;
        }//end of if(strIdentifier.equals("designationCAL"))
        else if(strIdentifier.equals("designationHOS"))
        {
            alHOSDesignationCode = loadObjects(strIdentifier, alHOSDesignationCode);
            return alHOSDesignationCode;
        }//end of if(strIdentifier.equals("designationHOS"))
        else if(strIdentifier.equals("designationINS"))
        {
            alINSDesignationCode = loadObjects(strIdentifier, alINSDesignationCode);
            return alINSDesignationCode;
        }//end of if(strIdentifier.equals("designationINS"))
        else if(strIdentifier.equals("designationCOR"))
        {
            alCORDesignationCode = loadObjects(strIdentifier, alCORDesignationCode);
            return alCORDesignationCode;
        }//end of if(strIdentifier.equals("designationCOR"))
        else if(strIdentifier.equals("designationDMC"))
        {
            alDMCDesignationCode = loadObjects(strIdentifier, alDMCDesignationCode);
            return alDMCDesignationCode;
        }//end of if(strIdentifier.equals("designationDMC"))
        else if(strIdentifier.equals("enrollment"))
        {
            alGeneralCodeEnrollment = loadObjects(strIdentifier, alGeneralCodeEnrollment);
            return alGeneralCodeEnrollment;
        }//end of if(strIdentifier.equals("enrollment"))
        else if(strIdentifier.equals("gender"))
        {
            alGeneralCodeGenderDetails = loadObjects(strIdentifier, alGeneralCodeGenderDetails);
            return alGeneralCodeGenderDetails;
        }//end of if(strIdentifier.equals("gender"))
        else if(strIdentifier.equals("clarificationstatus"))
        {
        	alClarificationStatus = loadObjects(strIdentifier, alClarificationStatus);
            return alClarificationStatus;
        }//end of if(strIdentifier.equals("clarificationstatus"))
        else if(strIdentifier.equals("INSStatusTypeID"))
        {
            alInsTermStatus = loadObjects(strIdentifier, alInsTermStatus);
            return alInsTermStatus;
        }//end of if(strIdentifier.equals("INSStatusTypeID"))
        else if(strIdentifier.equals("subTypeID"))
        {
            alPolicySubType = loadObjects(strIdentifier, alPolicySubType);
            return alPolicySubType;
        }//end of if(strIdentifier.equals("subTypeID"))
        else if(strIdentifier.equals("policyMedium"))
        {
        	alPolicyMedium = loadObjects(strIdentifier, alPolicyMedium);
            return alPolicyMedium;
        }//end of if(strIdentifier.equals("policyMedium"))
        else if(strIdentifier.equals("nonFloater"))
        {
            alPolicySubTypeNonFloater = loadObjects(strIdentifier, alPolicySubTypeNonFloater);
            return alPolicySubTypeNonFloater;
        }//end of if(strIdentifier.equals("nonFloater"))
        else if(strIdentifier.equals("floater"))
        {
            alPolicySubTypeFloater = loadObjects(strIdentifier, alPolicySubTypeFloater);
            return alPolicySubTypeFloater;
        }//end of if(strIdentifier.equals("floater"))
        else if(strIdentifier.equals("floaterNonFloater"))
        {
            alPolicySubTypeFloaterNon = loadObjects(strIdentifier, alPolicySubTypeFloaterNon);
            return alPolicySubTypeFloaterNon;
        }//end of if(strIdentifier.equals("floaterNonFloater"))
        else if(strIdentifier.equals("floaterRestrict"))
        {
            alPolicySubTypeFloaterRestrict = loadObjects(strIdentifier, alPolicySubTypeFloaterRestrict);
            return alPolicySubTypeFloaterRestrict;
        }//end of if(strIdentifier.equals("floaterRestrict"))
        else if(strIdentifier.equals("issueCheque"))
        {
            alIssueCheque = loadObjects(strIdentifier, alIssueCheque);
            return alIssueCheque;
        }//end of if(strIdentifier.equals("issueCheque"))
        else if(strIdentifier.equals("occupation"))
        {
            alOccupation = loadObjects(strIdentifier, alOccupation);
            return alOccupation;
        }//end of if(strIdentifier.equals("occupation"))
        else if(strIdentifier.equals("nationalities"))
        {
            alNationalities = loadObjects(strIdentifier, alNationalities);
            return alNationalities;
        }//end of if(strIdentifier.equals("nationalities"))
        else if(strIdentifier.equals("maritalStatuses"))
        {
            alMaritalStatuses = loadObjects(strIdentifier, alMaritalStatuses);
            return alMaritalStatuses;
        }//end of if(strIdentifier.equals("maritalStatuses"))
        else if(strIdentifier.equals("memberCategory"))
        {
            alMemberCategory = loadObjects(strIdentifier, alMemberCategory);
            return alMemberCategory;
        }//end of if(strIdentifier.equals("memberCategory"))
        else if(strIdentifier.equals("enrollmentWorkflow"))
        {
            alEnrollmentWorkflow = loadObjects(strIdentifier, alEnrollmentWorkflow);
            return alEnrollmentWorkflow;
        }//end of if(strIdentifier.equals("enrollmentWorkflow"))
        else if(strIdentifier.equals("endorsementWorkflow"))
        {
            alEndorsementWorkflow = loadObjects(strIdentifier, alEndorsementWorkflow);
            return alEndorsementWorkflow;
        }//end of if(strIdentifier.equals("endorsementWorkflow"))
        else if(strIdentifier.equals("claimsWorkflow"))
        {
        	alClaimsWorkflow = loadObjects(strIdentifier, alClaimsWorkflow);
            return alClaimsWorkflow;
        }//end of if(strIdentifier.equals("claimsWorkflow"))
        else if(strIdentifier.equals("codecleanupWorkflow"))
        {
        	alCodeCleanUpWorkflow = loadObjects(strIdentifier, alCodeCleanUpWorkflow);
            return alCodeCleanUpWorkflow;
        }//end of if(strIdentifier.equals("codecleanupWorkflow"))
	    else if(strIdentifier.equals("productPlan"))
        {
            alInsProductPlan = loadObjects(strIdentifier, alInsProductPlan);
            return alInsProductPlan;
        }//end of if(strIdentifier.equals("productPlan"))
        else if(strIdentifier.equals("icdDescription"))
        {
            alPEDCodeDescription = loadObjects(strIdentifier, alPEDCodeDescription);
            return alPEDCodeDescription;
        }//end of if(strIdentifier.equals("icdDescription"))
        else if(strIdentifier.equals("policyStatus"))
        {
            alPolicyStatus = loadObjects(strIdentifier, alPolicyStatus);
            return alPolicyStatus;
        }//end of if(strIdentifier.equals("policyStatus"))
		//added for donor
        else if(strIdentifier.equals("donorClaimStatus"))
        {
            alDonorClaimStatus = loadObjects(strIdentifier, alDonorClaimStatus);
            return alDonorClaimStatus;
        }//end of if(strIdentifier.equals("policyStatus"))
		//added as per KOC 1274 A
		else if(strIdentifier.equals("preauthStatusAprRejFlow"))
        {
        	alPreAuthAprRejStatus = loadObjects(strIdentifier, alPreAuthAprRejStatus);
            return alPreAuthAprRejStatus;
        }//end of if(strIdentifier.equals("preauthStatusAprRejFlow"))
        else if(strIdentifier.equals("claimStatusAprRejFlow"))
        {
        	alClaimAprRejStatus = loadObjects(strIdentifier, alClaimAprRejStatus);
            return alClaimAprRejStatus;
        }//end of if(strIdentifier.equals("claimStatusAprRejFlow"))
	//added as per KOC 1274 A
        else if(strIdentifier.equals("printStatus"))
        {
            alPrintStatus = loadObjects(strIdentifier, alPrintStatus);
            return alPrintStatus;
        }//end of if(strIdentifier.equals("printStatus"))
        else if(strIdentifier.equals("genEnrollmentID"))
        {
            alEnrollmentIdType = loadObjects(strIdentifier, alEnrollmentIdType);
            return alEnrollmentIdType;
        }//end of if(strIdentifier.equals("genEnrollmentID"))
        else if(strIdentifier.equals("ttkofficetype"))
        {
        	alTtkOfficeType = loadObjects(strIdentifier, alTtkOfficeType);
            return alTtkOfficeType;
        }//end of if(strIdentifier.equals("ttkofficetype"))
        else if(strIdentifier.equals("ttkheadofficelist"))
        {
        	alTtkOfficeName = loadObjects(strIdentifier, alTtkOfficeName);
            return alTtkOfficeName;
        }//end of if(strIdentifier.equals("ttkheadofficelist"))
        else if(strIdentifier.equals("endorsementType"))
        {
            alEndorsementType = loadObjects(strIdentifier, alEndorsementType);
            return alEndorsementType;
        }//end of if(strIdentifier.equals("endorsementType"))
        else if(strIdentifier.equals("amount"))
        {
        	alAmount = loadObjects(strIdentifier, alAmount);
            return alAmount;
        }//end of if(strIdentifier.equals("amount"))
        else if(strIdentifier.equals("assignedTo"))
        {
        	alAssignedTo = loadObjects(strIdentifier, alAssignedTo);
            return alAssignedTo;
        }//end of if(strIdentifier.equals("assignedTo"))
        else if(strIdentifier.equals("source"))
        {
        	alRecdSource = loadObjects(strIdentifier, alRecdSource);
            return alRecdSource;
        }//end of if(strIdentifier.equals("source"))
        else if(strIdentifier.equals("encounterTypes"))
        {
        	alEncounterTypes = loadObjects(strIdentifier, alEncounterTypes);
            return alEncounterTypes;
        }//end of if(strIdentifier.equals("encounterTypes"))
        else if(strIdentifier.equals("encounterStartTypes"))
        {
        	alEncounterStartTypes = loadObjects(strIdentifier, alEncounterStartTypes);
            return alEncounterStartTypes;
        }//end of if(strIdentifier.equals("encounterTypes"))
        else if(strIdentifier.equals("encounterEndTypes"))
        {
        	alEncounterEndTypes = loadObjects(strIdentifier, alEncounterEndTypes);
            return alEncounterEndTypes;
        }//end of if(strIdentifier.equals("encounterTypes"))
        else if(strIdentifier.equals("treatmentTypes"))
        {
        	alTreatmentTypes = loadObjects(strIdentifier, alTreatmentTypes);
            return alTreatmentTypes;
        }//end of if(strIdentifier.equals("encounterTypes"))
        else if(strIdentifier.equals("preauthStatus"))
        {
        	alPreAuthStatus = loadObjects(strIdentifier, alPreAuthStatus);
            return alPreAuthStatus;
        }//end of if(strIdentifier.equals("preauthStatus"))
        else if(strIdentifier.equals("preauthEnhStatus"))
        {
        	alPreauthEnhStatus = loadObjects(strIdentifier, alPreauthEnhStatus);
            return alPreauthEnhStatus;
        }//end of if(strIdentifier.equals("preauthEnhStatus"))
        else if(strIdentifier.equals("newDeletion"))
        {
        	alYears = loadObjects(strIdentifier, alYears);
            return alYears;
        }//else if(strIdentifier.equals("newDeletion"))
		
        else if(strIdentifier.equals("investStatus"))
        {
        	alInvestStatus = loadObjects(strIdentifier, alInvestStatus);
            return alInvestStatus;
        }//end of if(strIdentifier.equals("investStatus"))
        else if(strIdentifier.equals("claimStatus"))
        {
        	alClaimStatus = loadObjects(strIdentifier, alClaimStatus);
            return alClaimStatus;
        }//end of if(strIdentifier.equals("claimStatus"))
        else if(strIdentifier.equals("partnerpreauthStatus"))
        {
        	alPartnerPreauthStatus = loadObjects(strIdentifier, alPartnerPreauthStatus);
            return alPartnerPreauthStatus;
        }// else if(strIdentifier.equals("partnerpreauthStatus"))	
		
        else if(strIdentifier.equals("hospitalizationType"))
        {
        	alHospitalizationType = loadObjects(strIdentifier, alHospitalizationType);
            return alHospitalizationType;
        }//end of if(strIdentifier.equals("hospitalizationType"))
        else if(strIdentifier.equals("hospitalizedFor"))
        {
        	alHospitalizedFor = loadObjects(strIdentifier, alHospitalizedFor);
            return alHospitalizedFor;
        }//end of if(strIdentifier.equals("hospitalizedFor"))
        else if(strIdentifier.equals("closeProximity"))
        {
        	alCloseProximity = loadObjects(strIdentifier, alCloseProximity);
            return alCloseProximity;
        }//end of if(strIdentifier.equals("closeProximity"))
        else if(strIdentifier.equals("preauthPriority"))
        {
        	alPreAuthPriority = loadObjects(strIdentifier, alPreAuthPriority);
            return alPreAuthPriority;
        }//end of if(strIdentifier.equals("preauthPriority"))
        else if(strIdentifier.equals("coInsCompNameList"))
        {
        	alCoInsCompList = loadObjects(strIdentifier, alCoInsCompList);
            return alCoInsCompList;
        }
        else if(strIdentifier.equals("preauthType"))
        {
        	alPreAuthType = loadObjects(strIdentifier, alPreAuthType);
            return alPreAuthType;
        }//end of if(strIdentifier.equals("preauthType"))
        else if(strIdentifier.equals("InsComp"))
        {
        	alInsComp = loadObjects(strIdentifier, alInsComp);
            return alInsComp;
        }//end of if(strIdentifier.equals("preauthType"))

        else if(strIdentifier.equals("productList"))
        {
        	alProductList = loadObjects(strIdentifier, alProductList);
            return alProductList;
        }
        
        else if(strIdentifier.equals("shortfallStatus"))
        {
        	alShortfallStatus = loadObjects(strIdentifier, alShortfallStatus);
            return alShortfallStatus;
        }//end of if(strIdentifier.equals("shortfallStatus"))
        else if(strIdentifier.equals("shortfallType"))
        {
        	alShortfallType = loadObjects(strIdentifier, alShortfallType);
            return alShortfallType;
        }//end of if(strIdentifier.equals("shortfallType"))
        else if(strIdentifier.equals("claimShortfallType"))
        {
        	alClaimShortfallType = loadObjects(strIdentifier, alClaimShortfallType);
            return alClaimShortfallType;
        }//end of if(strIdentifier.equals("claimShortfallType"))
		//Shortfall CR KOC1179
        else if(strIdentifier.equals("claimShortfallTypeNew"))
        {
        	alClaimShortfallTypeNew = loadObjects(strIdentifier, alClaimShortfallTypeNew);
            return alClaimShortfallTypeNew;
        }//end of if(strIdentifier.equals("claimShortfallType"))
		
		
        else if(strIdentifier.equals("claimShortfallTemplateType"))
        {
        	alClaimShortfallTemplateType = loadObjects(strIdentifier, alClaimShortfallTemplateType);
            return alClaimShortfallTemplateType;
        }//end of if(strIdentifier.equals("claimShortfallTemplateType"))
       // End shortfall CR KOC1179
		//shortfall phase1
		
        else if(strIdentifier.equals("claimShortfallTemplateNetworkType"))
        {
        	alClaimShortfallTemplateNetworkType = loadObjects(strIdentifier, alClaimShortfallTemplateNetworkType);
            return alClaimShortfallTemplateNetworkType;
        }//end of if(strIdentifier.equals("claimShortfallTemplateType"))
		
		
		//shortfall phase1
        else if(strIdentifier.equals("documentType"))
        {
        	alSupportDoc = loadObjects(strIdentifier, alSupportDoc);
            return alSupportDoc;
        }//end of if(strIdentifier.equals("documentType"))
        else if(strIdentifier.equals("specialty"))
        {
        	alSpecialty = loadObjects(strIdentifier, alSpecialty);
            return alSpecialty;
        }//end of if(strIdentifier.equals("specialty"))
        else if(strIdentifier.equals("treatmentPlan"))
        {
        	alTreatmentPlan = loadObjects(strIdentifier, alTreatmentPlan);
            return alTreatmentPlan;
        }//end of if(strIdentifier.equals("treatmentPlan"))
        else if(strIdentifier.equals("treatmentPlanICD"))
        {
        	alICDTreatmentPlan = loadObjects(strIdentifier, alICDTreatmentPlan);
            return alICDTreatmentPlan;
        }//end of if(strIdentifier.equals("treatmentPlanICD"))
        else if(strIdentifier.equals("logType"))
        {
        	alLogType = loadObjects(strIdentifier, alLogType);
            return alLogType;
        }//end of if(strIdentifier.equals("logType"))
        else if(strIdentifier.equals("logInfo"))
        {
        	alLogInfo = loadObjects(strIdentifier, alLogInfo);
            return alLogInfo;
        }//end of if(strIdentifier.equals("logInfo"))
        else if(strIdentifier.equals("dataEntrylogType"))           //ParallelAlert
        {
        	aldataEntryLogType = loadObjects(strIdentifier, aldataEntryLogType);
            return aldataEntryLogType;
        }//end of if(strIdentifier.equals("logType"))
        else if(strIdentifier.equals("dataEntrylogInfo"))
        {
        	aldataEntryLogInfo = loadObjects(strIdentifier, aldataEntryLogInfo);
            return aldataEntryLogInfo;
        }//end of if(strIdentifier.equals("logInfo"))
        else if(strIdentifier.equals("historyType"))
        {
        	alHistoryType = loadObjects(strIdentifier, alHistoryType);
            return alHistoryType;
        }//end of if(strIdentifier.equals("historyType"))
        else if(strIdentifier.equals("claimHistoryType"))
        {
        	alClaimHistoryType = loadObjects(strIdentifier, alClaimHistoryType);
            return alClaimHistoryType;
        }//end of if(strIdentifier.equals("claimHistoryType"))
		else if(strIdentifier.equals("accountHistoryType"))//koc11 koc 11
        {
        	alAccountHistoryType = loadObjects(strIdentifier, alAccountHistoryType);
            return alAccountHistoryType;
        }//end of if(strIdentifier.equals("claimHistoryType"))
	    else if(strIdentifier.equals("agencyType"))
        {
        	alInvestigationAgency = loadObjects(strIdentifier, alInvestigationAgency);
            return alInvestigationAgency;
        }//end of if(strIdentifier.equals("agencyType"))
        else if(strIdentifier.equals("investReason"))
        {
        	alInvestReason = loadObjects(strIdentifier, alInvestReason);
            return alInvestReason;
        }//end of if(strIdentifier.equals("investReason"))
        else if(strIdentifier.equals("authReason"))
        {
        	alAuthReason = loadObjects(strIdentifier, alAuthReason);
            return alAuthReason;
        }//end of if(strIdentifier.equals("authReason"))
        else if(strIdentifier.equals("shortfallReason"))
        {
        	alShortfallReason = loadObjects(strIdentifier, alShortfallReason);
            return alShortfallReason;
        }//end of if(strIdentifier.equals("shortfallReason"))
        else if(strIdentifier.equals("durationType"))
        {
        	alDurationType = loadObjects(strIdentifier, alDurationType);
            return alDurationType;
        }//end of if(strIdentifier.equals("durationType"))
        else if(strIdentifier.equals("investType"))
        {
        	alInvestigationType = loadObjects(strIdentifier, alInvestigationType);
            return alInvestigationType;
        }//end of if(strIdentifier.equals("investType"))
        else if(strIdentifier.equals("receivedFrom"))
        {
        	alRcvdFrom = loadObjects(strIdentifier, alRcvdFrom);
            return alRcvdFrom;
        }//end of if(strIdentifier.equals("receivedFrom"))
        else if(strIdentifier.equals("qualityStatus"))
        {
        	alQualityStatus = loadObjects(strIdentifier, alQualityStatus);
            return alQualityStatus;
        }//end of if(strIdentifier.equals("qualityStatus"))
        else if(strIdentifier.equals("assignUsers"))
        {
        	alAssignUsers = loadObjects(strIdentifier, alAssignUsers);
            return alAssignUsers;
        }//end of if(strIdentifier.equals("assignUsers"))
        else if(strIdentifier.equals("conflict"))
        {
        	alConflict = loadObjects(strIdentifier, alConflict);
            return alConflict;
        }//end of if(strIdentifier.equals("conflict"))
        else if(strIdentifier.equals("relshipGender"))
        {
        	alRelshipGender = loadObjects(strIdentifier, alRelshipGender);
            return alRelshipGender;
        }//end of if(strIdentifier.equals("relshipGender"))
        else if(strIdentifier.equals("adminAuthority"))
        {
        	alAdminAuthority = loadObjects(strIdentifier, alAdminAuthority);
            return alAdminAuthority;
        }//end of if(strIdentifier.equals("adminAuthority"))
        else if(strIdentifier.equals("allocatedType"))
        {
        	alAllocatedType = loadObjects(strIdentifier, alAllocatedType);
            return alAllocatedType;
        }//end of if(strIdentifier.equals("allocatedType"))
        else if(strIdentifier.equals("batchStatus"))
        {
        	alBatchStatus = loadObjects(strIdentifier, alBatchStatus);
            return alBatchStatus;
        }//end of if(strIdentifier.equals("batchStatus"))
        else if(strIdentifier.equals("courierStatus"))
        {
        	alCourierStatus = loadObjects(strIdentifier, alCourierStatus);
            return alCourierStatus;
        }//end of if(strIdentifier.equals("courierStatus"))
        else if(strIdentifier.equals("contentType"))
        {
        	alContentType = loadObjects(strIdentifier, alContentType);
            return alContentType;
        }//end of if(strIdentifier.equals("contentType"))
        else if(strIdentifier.equals("courierName"))
        {
        	alCourierName = loadObjects(strIdentifier, alCourierName);
            return alCourierName;
        }//end of if(strIdentifier.equals("contentType"))
		//added for courier
        else if(strIdentifier.equals("courierDocType"))
        {
        	alCourierDocType = loadObjects(strIdentifier, alCourierDocType);
            return alCourierDocType;
        }//end of if(strIdentifier.equals("contentType"))
		//added for courier
        else if(strIdentifier.equals("officeType"))
        {
        	alOfficeType = loadObjects(strIdentifier, alOfficeType);
            return alOfficeType;
        }//end of if(strIdentifier.equals("officeType"))
        else if(strIdentifier.equals("headOffice"))
        {
        	alBankHeadOffice = loadObjects(strIdentifier, alBankHeadOffice);
            return alBankHeadOffice;
        }//end of if(strIdentifier.equals("headOffice"))
        else if(strIdentifier.equals("acctStatus"))
        {
        	alAcctStatus = loadObjects(strIdentifier, alAcctStatus);
            return alAcctStatus;
        }//end of if(strIdentifier.equals("acctStatus"))
        else if(strIdentifier.equals("transType"))
        {
        	alTransType = loadObjects(strIdentifier, alTransType);
            return alTransType;
        }//end of if(strIdentifier.equals("transType"))
        else if(strIdentifier.equals("transTypeDeposit"))
        {
        	alDepositTransType = loadObjects(strIdentifier, alDepositTransType);
            return alDepositTransType;
        }//end of if(strIdentifier.equals("transTypeDeposit"))
	    else if(strIdentifier.equals("floatTransType"))
        {
        	alFloatTransType = loadObjects(strIdentifier, alFloatTransType);
            return alFloatTransType;
        }//end of if(strIdentifier.equals("floatTransType"))
        else if(strIdentifier.equals("floatType"))
        {
        	alFloatType = loadObjects(strIdentifier, alFloatType);
            return alFloatType;
        }//end of if(strIdentifier.equals("floatType"))
        else if(strIdentifier.equals("claimType"))
        {
        	alClaimType = loadObjects(strIdentifier, alClaimType);
            return alClaimType;
        }//end of if(strIdentifier.equals("claimType"))
        else if(strIdentifier.equals("chequeStatus"))
        {
        	alChequeStatus = loadObjects(strIdentifier, alChequeStatus);
            return alChequeStatus;
        }//end of if(strIdentifier.equals("chequeStatus"))
        else if(strIdentifier.equals("chequeStatusStale"))
        {
        	alStaleChequeStatus = loadObjects(strIdentifier, alStaleChequeStatus);
            return alStaleChequeStatus;
        }//end of if(strIdentifier.equals("chequeStatusStale"))
		

		//Added ass per kOC 1175 ChangeRequest
        else if(strIdentifier.equals("chequeStatuswithoutStale"))
	        {
        	alChequeStatusWithOutStale = loadObjects(strIdentifier, alChequeStatusWithOutStale);
	            return alChequeStatusWithOutStale;
	        }//end of if(strIdentifier.equals("GradeCode"))
		//Added ass per kOC 1175 ChangeRequest
		
		
	    else if(strIdentifier.equals("courierType"))
        {
        	alCourierType = loadObjects(strIdentifier, alCourierType);
            return alCourierType;
        }//end of if(strIdentifier.equals("courierType"))
        else if(strIdentifier.equals("bufferMode"))
        {
        	alBufferMode = loadObjects(strIdentifier, alBufferMode);
            return alBufferMode;
        }//end of if(strIdentifier.equals("bufferMode"))
        else if(strIdentifier.equals("entryMode"))
        {
        	alEntryMode = loadObjects(strIdentifier, alEntryMode);
            return alEntryMode;
        }//end of if(strIdentifier.equals("entryMode"))
        else if(strIdentifier.equals("supportBuffer"))
        {
        	alSupportDocBuffer = loadObjects(strIdentifier, alSupportDocBuffer);
            return alSupportDocBuffer;
        }//end of if(strIdentifier.equals("supportBuffer"))
        else if(strIdentifier.equals("bufferStatus"))
        {
        	alBufferStatus = loadObjects(strIdentifier, alBufferStatus);
            return alBufferStatus;
        }//end of if(strIdentifier.equals("bufferStatus"))
        else if(strIdentifier.equals("claimsSupportDoc"))
        {
        	alClaimsSupportDoc = loadObjects(strIdentifier, alClaimsSupportDoc);
            return alClaimsSupportDoc;
        }//end of if(strIdentifier.equals("claimsSupportDoc"))
        else if(strIdentifier.equals("claimsSupportBuffer"))
        {
        	alClaimsSupportDocBuffer = loadObjects(strIdentifier, alClaimsSupportDocBuffer);
            return alClaimsSupportDocBuffer;
        }//end of if(strIdentifier.equals("claimsSupportBuffer"))
        else if(strIdentifier.equals("voucherStatus"))
        {
        	alVoucherStatus = loadObjects(strIdentifier, alVoucherStatus);
            return alVoucherStatus;
        }//end of if(strIdentifier.equals("voucherStatus"))
        else if(strIdentifier.equals("paymentMethod"))
        {
        	alPaymentMethod = loadObjects(strIdentifier, alPaymentMethod);
            return alPaymentMethod;
        }//end of if(strIdentifier.equals("paymentMethod"))
        else if(strIdentifier.equals("inwardStatus"))
        {
        	alInwardStatus = loadObjects(strIdentifier, alInwardStatus);
            return alInwardStatus;
        }//end of if(strIdentifier.equals("inwardStatus"))
        else if(strIdentifier.equals("claimDocumentType"))
        {
        	alClaimDocumentType = loadObjects(strIdentifier, alClaimDocumentType);
            return alClaimDocumentType;
        }//end of if(strIdentifier.equals("claimDocumentType"))
        else if(strIdentifier.equals("claimSource"))
        {
        	alClaimSource = loadObjects(strIdentifier, alClaimSource);
            return alClaimSource;
        }//end of if(strIdentifier.equals("claimSource"))
        else if(strIdentifier.equals("claimMode"))
        {
        	alClaimMode = loadObjects(strIdentifier, alClaimMode);
            return alClaimMode;
        }//end of if(strIdentifier.equals("claimMode"))
        else if(strIdentifier.equals("requestType"))
        {
        	alRequestType = loadObjects(strIdentifier, alRequestType);
            return alRequestType;
        }//end of if(strIdentifier.equals("requestType"))
        else if(strIdentifier.equals("claimSubType"))
        {
        	alClaimSubType = loadObjects(strIdentifier, alClaimSubType);
            return alClaimSubType;
        }//end of if(strIdentifier.equals("claimSubType"))
		//OPD_4_hosptial
        else if(strIdentifier.equals("PaymentType"))
        {
        	alPaymentType = loadObjects(strIdentifier, alPaymentType);
            return alPaymentType;
        }//end of if(strIdentifier.equals("claimSubType"))
        else if(strIdentifier.equals("denialDescriptions"))
        {
        	alDenialDescType = loadObjects(strIdentifier, alDenialDescType);
            return alDenialDescType;
        }//end of if(strIdentifier.equals("denialDescriptions"))
        else if(strIdentifier.equals("modifiers"))
        {
        	alModifiersType = loadObjects(strIdentifier, alModifiersType);
            return alModifiersType;
        }//end of if(strIdentifier.equals("denialDescriptions"))
        else if(strIdentifier.equals("unitTypes"))
        {
        	alUnitTypes    = loadObjects(strIdentifier, alUnitTypes);
            return alUnitTypes;
        }//end of if(strIdentifier.equals("denialDescriptions"))
        else if(strIdentifier.equals("observationTypes"))
        {
        	alObservationTypes = loadObjects(strIdentifier, alObservationTypes);
            return alObservationTypes;
        }//end of if(strIdentifier.equals("observationTypes"))
        else if(strIdentifier.equals("observationCodes"))
        {
        	alObservationCodes = loadObjects(strIdentifier, alObservationCodes);
            return alObservationCodes;
        }//end of if(strIdentifier.equals("observationCodes"))
        else if(strIdentifier.equals("observationValueTypes"))
        {
        	alObservationValueTypes = loadObjects(strIdentifier, alObservationValueTypes);
            return alObservationValueTypes;
        }//end of if(strIdentifier.equals("observationValueTypes"))
		
        else if(strIdentifier.equals("healthCheckType"))
        {
        	alHealthCheckType = loadObjects(strIdentifier, alHealthCheckType);
            return alHealthCheckType;
        }//end of if(strIdentifier.equals("claimSubType"))
		
		//OPD_4_hosptial
        else if(strIdentifier.equals("docType"))
        {
        	alDocType = loadObjects(strIdentifier, alDocType);
            return alDocType;
        }//end of if(strIdentifier.equals("docType"))
        else if(strIdentifier.equals("docReasonType"))
        {
        	alDocReasonType = loadObjects(strIdentifier, alDocReasonType);
            return alDocReasonType;
        }//end of if(strIdentifier.equals("docReasonType"))
        else if(strIdentifier.equals("approveReason"))
        {
        	alApproveReason = loadObjects(strIdentifier, alApproveReason);
            return alApproveReason;
        }//end of if(strIdentifier.equals("approveReason"))
        else if(strIdentifier.equals("accountHead"))
        {
        	alAccountHeadType = loadObjects(strIdentifier, alAccountHeadType);
            return alAccountHeadType;
        }//end of if(strIdentifier.equals("accountHead"))
		//added for maternity 1164
        else if(strIdentifier.equals("vaccineType"))
        {
        	alVaccineType = loadObjects(strIdentifier, alVaccineType);
            return alVaccineType;
        }//end of if(strIdentifier.equals("accountHead"))
		// End added for maternity
        else if(strIdentifier.equals("dischargeCondition"))
        {
        	alDischargeCondition = loadObjects(strIdentifier, alDischargeCondition);
            return alDischargeCondition;
        }//end of if(strIdentifier.equals("dischargeCondition"))
        else if(strIdentifier.equals("onlineAccess"))
        {
        	alOnlineAccess = loadObjects(strIdentifier, alOnlineAccess);
            return alOnlineAccess;
        }//end of if(strIdentifier.equals("onlineAccess"))
        else if(strIdentifier.equals("summaryType"))
        {
        	alSummaryType = loadObjects(strIdentifier, alSummaryType);
            return alSummaryType;
        }//end of if(strIdentifier.equals("summaryType"))
        else if(strIdentifier.equals("callerType"))
        {
        	alCallerType = loadObjects(strIdentifier, alCallerType);
            return alCallerType;
        }//end of if(strIdentifier.equals("callerType"))
        else if(strIdentifier.equals("callLogType"))
        {
        	alCallLogType = loadObjects(strIdentifier, alCallLogType);
            return alCallLogType;
        }//end of if(strIdentifier.equals("callLogType"))
        else if(strIdentifier.equals("callReason"))
        {
        	alCallReason = loadObjects(strIdentifier, alCallReason);
            return alCallReason;
        }//end of if(strIdentifier.equals("callReason"))
        else if(strIdentifier.equals("callSubReason"))
        {
        	alCallSubReason = loadObjects(strIdentifier, alCallSubReason);
            return alCallSubReason;
        }//end of if(strIdentifier.equals("callSubReason"))
        else if(strIdentifier.equals("callCategory"))
        {
        	alCallCategory = loadObjects(strIdentifier, alCallCategory);
            return alCallCategory;
        }//end of if(strIdentifier.equals("callCategory"))
        else if(strIdentifier.equals("callSubCategory"))
        {
        	alCallSubCategory = loadObjects(strIdentifier, alCallSubCategory);
            return alCallSubCategory;
        }//end of if(strIdentifier.equals("callSubCategory"))
        else if(strIdentifier.equals("callAnswerCode"))
        {
        	alCallAnswerCode = loadObjects(strIdentifier, alCallAnswerCode);
            return alCallAnswerCode;
        }//end of if(strIdentifier.equals("callAnswerCode"))
        else if(strIdentifier.equals("callLoggedBY"))
        {
        	alCallLoggedBy = loadObjects(strIdentifier, alCallLoggedBy);
            return alCallLoggedBy;
        }//end of if(strIdentifier.equals("callLoggedBY"))
        else if(strIdentifier.equals("callRelateTo"))
        {
        	alCallRelateTo = loadObjects(strIdentifier, alCallRelateTo);
            return alCallRelateTo;
        }//end of if(strIdentifier.equals("callRelateTo"))
        else if(strIdentifier.equals("callStatus"))
        {
        	alCallStatus = loadObjects(strIdentifier, alCallStatus);
            return alCallStatus;
        }//end of if(strIdentifier.equals("callStatus"))
        else if(strIdentifier.equals("ruleAccountHead"))
        {
        	alRuleAccountHeadType = loadObjects(strIdentifier, alRuleAccountHeadType);
            return alRuleAccountHeadType;
        }//end of if(strIdentifier.equals("ruleAccountHead"))
        else if(strIdentifier.equals("courierSource"))
        {
        	alCourierSource = loadObjects(strIdentifier, alCourierSource);
            return alCourierSource;
        }//end of if(strIdentifier.equals("courierSource"))
        else if(strIdentifier.equals("template"))
        {
        	alPolicyTemplates = loadObjects(strIdentifier, alPolicyTemplates);
            return alPolicyTemplates;
        }//end of if(strIdentifier.equals("template"))
        else if(strIdentifier.equals("callSource"))
        {
        	alCallSource = loadObjects(strIdentifier, alCallSource);
            return alCallSource;
        }//end of if(strIdentifier.equals("callSource"))
        else if(strIdentifier.equals("bankName"))
        {
        	alBankName = loadObjects(strIdentifier, alBankName);
            return alBankName;
        }//end of if(strIdentifier.equals("bankName"))
        else if(strIdentifier.equals("chequeTemplate"))
        {
        	alChequeTemplate = loadObjects(strIdentifier, alChequeTemplate);
            return alChequeTemplate;
        }//end of if(strIdentifier.equals("chequeTemplate"))
        else if(strIdentifier.equals("dayCareProcedures"))
        {
        	alDayCareProcedures = loadObjects(strIdentifier, alDayCareProcedures);
            return alDayCareProcedures;
        }//end of if(strIdentifier.equals("dayCareProcedures"))
        else if(strIdentifier.equals("voucherRequired"))
        {
        	alVoucherRequired = loadObjects(strIdentifier, alVoucherRequired);
            return alVoucherRequired;
        }//end of if(strIdentifier.equals("voucherRequired"))
        else if(strIdentifier.equals("nhcpLetterType"))
        {
        	alNHCPLetterType = loadObjects(strIdentifier, alNHCPLetterType);
            return alNHCPLetterType;
        }//end of if(strIdentifier.equals("nhcpLetterType"))
        else if(strIdentifier.equals("mrLetterType"))
        {
        	alMRLetterType = loadObjects(strIdentifier, alMRLetterType);
            return alMRLetterType;
        }//end of if(strIdentifier.equals("mrLetterType"))
        else if(strIdentifier.equals("liabilityStatus"))
        {
        	alLiabilityStatus = loadObjects(strIdentifier, alLiabilityStatus);
            return alLiabilityStatus;
        }//end of if(strIdentifier.equals("liabilityStatus"))
        else if(strIdentifier.equals("productChange"))
        {
        	alProductChangeYN = loadObjects(strIdentifier, alProductChangeYN);
            return alProductChangeYN;
        }//end of if(strIdentifier.equals("productChange"))
        else if(strIdentifier.equals("debitAssoc"))
        {
        	alDebitAssociate = loadObjects(strIdentifier, alDebitAssociate);
            return alDebitAssociate;
        }//end of if(strIdentifier.equals("debitAssoc"))
        else if(strIdentifier.equals("debitType"))
        {
        	alDebitType = loadObjects(strIdentifier, alDebitType);
            return alDebitType;
        }//end of if(strIdentifier.equals("debitType"))
        else if(strIdentifier.equals("debitTypeDraft"))
        {
        	alDebitTypeDraft = loadObjects(strIdentifier, alDebitTypeDraft);
            return alDebitTypeDraft;
        }//end of if(strIdentifier.equals("debitTypeDraft"))
        else if(strIdentifier.equals("durationMonths"))
        {
        	alPcsMonth = loadObjects(strIdentifier, alPcsMonth);
            return alPcsMonth;
        }//end of if(strIdentifier.equals("durationMonths"))
        else if(strIdentifier.equals("daycareGroup"))
        {
        	alDayCareGroup = loadObjects(strIdentifier,alDayCareGroup);
        	return alDayCareGroup;
        }//end of if(strIdentifier.equals("daycareGroup"))
        else if(strIdentifier.equals("currencyGroup"))
        {
        	alCurrencyGroup = loadObjects(strIdentifier,alCurrencyGroup);
        	return alCurrencyGroup;
        }//end of if(strIdentifier.equals("daycareGroup"))
        else if(strIdentifier.equals("networkGroup"))
        {
        	alNetworkGroup = loadObjects(strIdentifier,alNetworkGroup);
        	return alNetworkGroup;
        }//end of if(strIdentifier.equals("daycareGroup"))
        else if(strIdentifier.equals("providerNetworkGroup"))
        {
        	alProviderNetworkGroup = loadObjects(strIdentifier,alProviderNetworkGroup);
        	return alProviderNetworkGroup;
        }//end of if(strIdentifier.equals("daycareGroup"))
        else if(strIdentifier.equals("treatementType"))
        {
        	alTreatementType = loadObjects(strIdentifier,alTreatementType);
        	return alTreatementType;
        }//end of if(strIdentifier.equals("treatementType"))
        else if(strIdentifier.equals("deductablesPubNetwork"))
        {
        	alDeductablesPubNetwork = loadObjects(strIdentifier,alDeductablesPubNetwork);
        	return alDeductablesPubNetwork;
        }//end of if(strIdentifier.equals("deductablesPubNetwork"))
        else if(strIdentifier.equals("deductablesPriNetwork"))
        {
        	alDeductablesPriNetwork = loadObjects(strIdentifier,alDeductablesPriNetwork);
        	return alDeductablesPriNetwork;
        }//end of if(strIdentifier.equals("deductablesPriNetwork"))
        else if(strIdentifier.equals("patientGroup"))
        {
        	alPatientGroup = loadObjects(strIdentifier,alPatientGroup);
        	return alPatientGroup;
        }//end of if(strIdentifier.equals("daycareGroup"))
        else if(strIdentifier.equals("govHospitalGroup"))
        {
        	alGovHospitalGroup = loadObjects(strIdentifier,alGovHospitalGroup);
        	return alGovHospitalGroup;
        }//end of if(strIdentifier.equals("daycareGroup"))maternityGroup
        else if(strIdentifier.equals("maternityGroup"))
        {
        	alMaternityGroup = loadObjects(strIdentifier,alMaternityGroup);
        	return alMaternityGroup;
        }//end of if(strIdentifier.equals("daycareGroup"))maternityGroup
        else if(strIdentifier.equals("drugsGroup"))
        {
        	alDrugsGroup = loadObjects(strIdentifier,alDrugsGroup);
        	return alDrugsGroup;
        }//end of if(strIdentifier.equals("daycareGroup"))maternityGroup
        else if(strIdentifier.equals("servicesList"))
        {
        	alServicesList = loadObjects(strIdentifier,alServicesList);
        	return alServicesList;
        }//end of if(strIdentifier.equals("daycareGroup"))maternityGroup
        else if(strIdentifier.equals("dayCareList"))
        {
        	alDayCareList = loadObjects(strIdentifier,alDayCareList);
        	return alDayCareList;
        }//end of if(strIdentifier.equals("daycareGroup"))maternityGroup
        else if(strIdentifier.equals("chronicCondition"))
        {
        	alChronicCondition = loadObjects(strIdentifier,alChronicCondition);
        	return alChronicCondition;
        }//end of if(strIdentifier.equals("chronicCondition"))maternityGroup
        else if(strIdentifier.equals("dieticianNeutricianList"))
        {
        	alDieticianNeutricianList = loadObjects(strIdentifier,alDieticianNeutricianList);
        	return alDieticianNeutricianList;
        }//end of if(strIdentifier.equals("daycareGroup"))womenHormonalList
        else if(strIdentifier.equals("womenHormonalList"))
        {
        	alWomenHormonalList = loadObjects(strIdentifier,alWomenHormonalList);
        	return alWomenHormonalList;
        }//end of if(strIdentifier.equals("daycareGroup"))
		
		//added for KOC-1310
        else if(strIdentifier.equals("cancerICDS"))
        {
        	alCancerICDList = loadObjects(strIdentifier,alCancerICDList);
        	return alCancerICDList;
        }
		//ended
		//added for KOC-1310
        else if(strIdentifier.equals("cancerGroup"))
        {
        	alCancerICDGroup = loadObjects(strIdentifier,alCancerICDGroup);
        	return alCancerICDGroup;        	
        }		
		//ended
		
        else if(strIdentifier.equals("maternityICDGroup"))
        {
        	alMaternityICDGroup = loadObjects(strIdentifier,alMaternityICDGroup);
        	return alMaternityICDGroup;
        }//end of if(strIdentifier.equals("maternityICDGroup"))
		
		
        else if(strIdentifier.equals("disabilityType"))
        {
        	alDisabilityType = loadObjects(strIdentifier,alDisabilityType);
        	return alDisabilityType;
        }//end of if(strIdentifier.equals("disabilityType"))
        else if(strIdentifier.equals("daycareProduct"))
        {
        	alDaycareproduct = loadObjects(strIdentifier,alDaycareproduct);
        	return alDaycareproduct;
        }//end of if(strIdentifier.equals("daycareProduct"))
        else if(strIdentifier.equals("medicineSystem"))
        {
        	alSystemOfmedicine = loadObjects(strIdentifier,alSystemOfmedicine);
        	return alSystemOfmedicine;
        }//end of if(strIdentifier.equals("medicineSystem"))
        else if(strIdentifier.equals("ailmentClaimType"))
        {
        	alAilmentClaimType = loadObjects(strIdentifier,alAilmentClaimType);
        	return alAilmentClaimType;
        }//end of if(strIdentifier.equals("ailmentClaimType"))
        else if(strIdentifier.equals("caseType"))
        {
        	alCaseType = loadObjects(strIdentifier,alCaseType);
        	return alCaseType;
        }//end of if(strIdentifier.equals("caseType"))
        else if(strIdentifier.equals("webloginLink"))
        {
        	alWebLoginLink = loadObjects(strIdentifier,alWebLoginLink);
        	return alWebLoginLink;
        }//end of if(strIdentifier.equals("webloginLink"))
        else if(strIdentifier.equals("webloginLinkReport"))
        {
        	alWebLoginLinkReports = loadObjects(strIdentifier,alWebLoginLinkReports);
        	return alWebLoginLinkReports;
        }//end of if(strIdentifier.equals("webloginLinkReport"))
        else if(strIdentifier.equals("webloginHRAddition"))
        {
        	alWebLoginHRAddition = loadObjects(strIdentifier,alWebLoginHRAddition);
        	return alWebLoginHRAddition;
        }//end of if(strIdentifier.equals("webloginHRAddition"))
        else if(strIdentifier.equals("webloginEmpAddition"))
        {
        	alWebLoginEmpAddition = loadObjects(strIdentifier,alWebLoginEmpAddition);
        	return alWebLoginEmpAddition;
        }//end of if(strIdentifier.equals("webloginEmpAddition"))
        else if(strIdentifier.equals("webloginDOI"))
        {
        	alWebLoginDOISettings = loadObjects(strIdentifier,alWebLoginDOISettings);
        	return alWebLoginDOISettings;
        }//end of if(strIdentifier.equals("webloginDOI"))
        else if(strIdentifier.equals("webloginAddSuminsured"))
        {
        	alWebLoginSumSettings = loadObjects(strIdentifier,alWebLoginSumSettings);
        	return alWebLoginSumSettings;
        }//end of if(strIdentifier.equals("webloginAddSuminsured"))
        //Added for IBM...KOC-1216
		else if(strIdentifier.equals("webloginOptin"))
		{
			alWebLoginOptinSettings = loadObjects(strIdentifier,alWebLoginOptinSettings);
			return alWebLoginOptinSettings;
		 }//end of if(strIdentifier.equals("webloginOptin"))

        //Ended..
        else if(strIdentifier.equals("webloginCancellation"))
        {
        	alWebLoginCancellation = loadObjects(strIdentifier,alWebLoginCancellation);
        	return alWebLoginCancellation;
        }//end of if(strIdentifier.equals("webloginCancellation"))
        else if(strIdentifier.equals("webloginSoftcopy"))
        {
        	alWebLoginSoftcopyUpload = loadObjects(strIdentifier,alWebLoginSoftcopyUpload);
        	return alWebLoginSoftcopyUpload;
        }//end of if(strIdentifier.equals("webloginSoftcopy"))
        else if(strIdentifier.equals("webloginSendMailGenTypeID"))
        {
        	alWebLoginSendMailGenTypeID = loadObjects(strIdentifier,alWebLoginSendMailGenTypeID);
        	return alWebLoginSendMailGenTypeID;
        }//end of if(strIdentifier.equals("webloginSendMailGenTypeID"))
        else if(strIdentifier.equals("webloginMailGenTypeID"))
        {
        	alWebLoginMailGenTypeID = loadObjects(strIdentifier,alWebLoginMailGenTypeID);
        	return alWebLoginMailGenTypeID;
        }//end of if(strIdentifier.equals("webloginSendMailGenTypeID"))
        else if(strIdentifier.equals("webloginMemConfigAdslY"))
        {
        	alWebLoginMemConfigY = loadObjects(strIdentifier,alWebLoginMemConfigY);
        	return alWebLoginMemConfigY;
        }//end of if(strIdentifier.equals("webloginMemConfigAdslY"))
        else if(strIdentifier.equals("webloginMemConfigAdslN"))
        {
        	alWebLoginMemConfigN = loadObjects(strIdentifier,alWebLoginMemConfigN);
        	return alWebLoginMemConfigN;
        }//end of if(strIdentifier.equals("webloginMemConfigAdslN"))
	    //Fax status report
        else if(strIdentifier.equals("faxGenUsers"))
        {
        	alFaxGenUsers = loadObjects(strIdentifier,alFaxGenUsers);
        	return alFaxGenUsers;
        }//end of if(strIdentifier.equals("faxGenUsers"))
        else if(strIdentifier.equals("faxStatus"))
        {
        	alFaxStatus = loadObjects(strIdentifier,alFaxStatus);
        	return alFaxStatus;
        }//end of if(strIdentifier.equals("faxStatus"))
        else if(strIdentifier.equals("empStatus"))
        {
        	alEmployeeStatus = loadObjects(strIdentifier,alEmployeeStatus);
        	return alEmployeeStatus;
        }//end of if(strIdentifier.equals("empStatus"))
        else if(strIdentifier.equals("empPwdGen"))
        {
        	alEmpPwdGeneration = loadObjects(strIdentifier,alEmpPwdGeneration);
        	return alEmpPwdGeneration;
        }//end of if(strIdentifier.equals("empPwdGen"))
        else if(strIdentifier.equals("relWindowPeriod"))
        {
        	alRelWindowPeriod = loadObjects(strIdentifier,alRelWindowPeriod);
        	return alRelWindowPeriod;
        }//end of if(strIdentifier.equals("relWindowPeriod"))
        else if(strIdentifier.equals("intimationStatus"))
        {
        	alIntimationStatus = loadObjects(strIdentifier,alIntimationStatus);
        	return alIntimationStatus;
        }//end of if(strIdentifier.equals("intimationStatus"))
        else if(strIdentifier.equals("intimationAccess"))
        {
        	alIntimationAccesss = loadObjects(strIdentifier,alIntimationAccesss);
        	return alIntimationAccesss;
        }//end of if(strIdentifier.equals("intimationAccess"))
        else if(strIdentifier.equals("notificinfo"))
        {
        	alNotifinfo = loadObjects(strIdentifier,alNotifinfo);
        	return alNotifinfo;
        }//end of if(strIdentifier.equals("notificinfo"))
        else if(strIdentifier.equals("viewloghistory"))
        {
        	alViewLogHistory = loadObjects(strIdentifier,alViewLogHistory);
        	return alViewLogHistory;
        }//end of if(strIdentifier.equals("viewloghistory"))
        else if(strIdentifier.equals("onlinequerystatus"))
        {
        	alSupportOnlineQuery = loadObjects(strIdentifier,alSupportOnlineQuery);
        	return alSupportOnlineQuery;
        }//end of if(strIdentifier.equals("onlinequerystatus"))
        else if(strIdentifier.equals("cashBenefit"))
        {
        	alCashBenefit = loadObjects(strIdentifier,alCashBenefit);
        	return alCashBenefit;
        }//end of if(strIdentifier.equals("cashBenefit"))
		
		//added for KOC-1273
        else if(strIdentifier.equals("criticalGroups"))
        {
        	
        	alCriticalGroups = loadObjects(strIdentifier,alCriticalGroups);
        	return alCriticalGroups;
        	
        }
		//ended
		
        else if(strIdentifier.equals("caseStatus"))
        {
        	alCaseStatus = loadObjects(strIdentifier,alCaseStatus);
        	return alCaseStatus;
        }//end of if(strIdentifier.equals("cashBenefit"))
        else if(strIdentifier.equals("sinceWhen"))
        {
        	alSinceWhenCoding = loadObjects(strIdentifier,alSinceWhenCoding);
        	return alSinceWhenCoding;
        }//end of if(strIdentifier.equals("sinceWhen"))
        else if(strIdentifier.equals("sinceWhenCall"))
        {
        	alSinceWhenCallCenter = loadObjects(strIdentifier,alSinceWhenCallCenter);
        	return alSinceWhenCallCenter;
        }//end of if(strIdentifier.equals("sinceWhenCall"))
        else if(strIdentifier.equals("claimspending"))
        {
        	alClaimsPending = loadObjects(strIdentifier,alClaimsPending);
        	return alClaimsPending;
        }//end of if(strIdentifier.equals("claimspending"))
        else if(strIdentifier.equals("iobSelectBatch"))
        {
        	alIOBSelectBatch = loadObjects(strIdentifier,alIOBSelectBatch);
        	return alIOBSelectBatch;
        }//end of if(strIdentifier.equals("iobSelectBatch"))
        else if(strIdentifier.equals("notificationAccess"))
        {
        	alNotificationAccess = loadObjects(strIdentifier,alNotificationAccess);
        	return alNotificationAccess;
        }//end of if(strIdentifier.equals("notificationAccess"))
        else if(strIdentifier.equals("reqRelated"))
        {
        	alOnlineAssReqRelated = loadObjects(strIdentifier,alOnlineAssReqRelated);
        	return alOnlineAssReqRelated;
        }//end of if(strIdentifier.equals("reqRelated"))
        else if(strIdentifier.equals("onlineAssistance"))
        {
        	alOnlineAssistance = loadObjects(strIdentifier,alOnlineAssistance);
        	return alOnlineAssistance;
        }//end of if(strIdentifier.equals("onlineAssistance"))
	    //TDS cache objects
        else if(strIdentifier.equals("hospOwnerType"))
        {
        	alHospOwnerType = loadObjects(strIdentifier,alHospOwnerType);
        	return alHospOwnerType;
        }//end of if(strIdentifier.equals("hospOwnerType"))
        else if(strIdentifier.equals("tdsHospCategory"))
        {
        	alTdsHospCategory = loadObjects(strIdentifier,alTdsHospCategory);
        	return alTdsHospCategory;
        }//end of if(strIdentifier.equals("tdsHospCategory"))
        else if(strIdentifier.equals("tdsRemittanceStatus"))
        {
        	alTdsRemittanceStatus = loadObjects(strIdentifier,alTdsRemittanceStatus);
        	return alTdsRemittanceStatus;
        }//end of if(strIdentifier.equals("tdsRemittanceStatus"))
        else if(strIdentifier.equals("tdsInsuranceInfo"))
        {
        	alTdsInsuranceInfo = loadObjects(strIdentifier,alTdsInsuranceInfo);
        	return alTdsInsuranceInfo;
        }//end of if(strIdentifier.equals("tdsInsuranceInfo"))
        else if(strIdentifier.equals("tdsAckInfo"))
        {
        	alTdsAckInfo = loadObjects(strIdentifier,alTdsAckInfo);
        	return alTdsAckInfo;
        }//end of if(strIdentifier.equals("tdsAckInfo"))
        else if(strIdentifier.equals("tdsClaimsInclExcl"))
        {
        	alTdsClaimsInclExcl = loadObjects(strIdentifier,alTdsClaimsInclExcl);
        	return alTdsClaimsInclExcl;
        }//end of if(strIdentifier.equals("TdsClaimsInclExcl"))
        else if(strIdentifier.equals("surgeryType"))
        {
        	alsurgeryType = loadObjects(strIdentifier, alsurgeryType);
            return alsurgeryType;
        }//end of else if(strIdentifier.equals("surgeryType"))
        else if(strIdentifier.equals("onlineRating"))
        {
        	alOnlineRating = loadObjects(strIdentifier, alOnlineRating);
            return alOnlineRating;
        }//end of else if(strIdentifier.equals("onlineRating"))
        else if(strIdentifier.equals("onlineFeedbackType"))
        {
        	alOnlineFeedbackType = loadObjects(strIdentifier, alOnlineFeedbackType);
            return alOnlineFeedbackType;
        }//end of else if(strIdentifier.equals("onlineFeedbackType"))
        else if(strIdentifier.equals("onlineFeedbackStatus"))
        {
        	alOnlineFeedbackStatus = loadObjects(strIdentifier, alOnlineFeedbackStatus);
            return alOnlineFeedbackStatus;
        }//end of else if(strIdentifier.equals("onlineFeedbackStatus"))
        else if(strIdentifier.equals("onlineFeedbackResponse"))
        {
        	alOnlineFeedbackResponse = loadObjects(strIdentifier, alOnlineFeedbackResponse);
            return alOnlineFeedbackResponse;
        }//end of else if(strIdentifier.equals("onlineFeedbackResponse"))
        else if(strIdentifier.equals("onlineassistFeedBackType"))
        {
        	alOnlineAssistFeedbackType = loadObjects(strIdentifier, alOnlineAssistFeedbackType);
        	return alOnlineAssistFeedbackType;
        }//end of else if(strIdentifier.equals("onlineassistFeedBackType"))
        else if(strIdentifier.equals("relationshipCombination"))
        {
        	alRelationshipCombination = loadObjects(strIdentifier, alRelationshipCombination);
        	return alRelationshipCombination;
        }//end of else if(strIdentifier.equals("relationshipCombination"))
        else if(strIdentifier.equals("surgerytypeMandatoryYN"))
        {
        	alSurgeryTypeMandatory = loadObjects(strIdentifier, alSurgeryTypeMandatory);
        	return alSurgeryTypeMandatory;
        }//end of else if(strIdentifier.equals("surgerytypeMandatoryYN"))
        else if(strIdentifier.equals("surgerytypeMandatoryYN"))
        {
        	alSurgeryTypeMandatory = loadObjects(strIdentifier, alSurgeryTypeMandatory);
        	return alSurgeryTypeMandatory;
        }//end of else if(strIdentifier.equals("surgerytypeMandatoryYN"))
        else if(strIdentifier.equals("productNetworkTypes"))
        {
        	alProductNetworkTypes = loadObjects(strIdentifier, alProductNetworkTypes);
        	return alProductNetworkTypes;
        }//end of else if(strIdentifier.equals("surgerytypeMandatoryYN"))
        else if(strIdentifier.equals("MaternityType"))
        {
        	alMaternityType = loadObjects(strIdentifier, alMaternityType);
        	return alMaternityType;
        }//end of else if(strIdentifier.equals("MaternityType"))
        else if(strIdentifier.equals("tdsbatchQuarter"))
        {
        	alTdsBatchQuarter = loadObjects(strIdentifier, alTdsBatchQuarter);
        	return alTdsBatchQuarter;
        }//end of else if(strIdentifier.equals("tdsbatchQuarter"))
		//added for koc1315
		 else if(strIdentifier.equals("immuneType"))
	        {
			  alImmuneType = loadObjects(strIdentifier, alImmuneType);
	            return alImmuneType;
	        }//end of if(strIdentifier.equals("accountHead"))
		//end added for koc1315
		//added for koc1316
		 else if(strIdentifier.equals("wellchildType"))
	        {
			 alWellchildType = loadObjects(strIdentifier, alWellchildType);
	            return alWellchildType;
	        }//end of if(strIdentifier.equals("accountHead"))
		//end added for koc1316
		//added for koc1308
		 else if(strIdentifier.equals("routAdultType"))
	        {
			 alRoutAdultType = loadObjects(strIdentifier, alRoutAdultType);
	            return alRoutAdultType;
	        }//end of if(strIdentifier.equals("accountHead"))
		//end added for koc1308
		
		 else if(strIdentifier.equals("regAuthority"))
	        {
			 alRedAuthority = loadObjects(strIdentifier, alRedAuthority);
	            return alRedAuthority;
	        }//end of if(strIdentifier.equals("accountHead"))
		 else if(strIdentifier.equals("providerType"))
	        {
			 alproviderType = loadObjects(strIdentifier, alproviderType);
	            return alproviderType;
	        }//end of if(strIdentifier.equals("accountHead"))
		 else if(strIdentifier.equals("primaryNetwork"))
	        {
			 alprimaryNetwork = loadObjects(strIdentifier, alprimaryNetwork);
	            return alprimaryNetwork;
	        }//end of if(strIdentifier.equals("primaryNetwork"))
		 else if(strIdentifier.equals("primaryNetworkCheckBox"))
	        {
			 alPrimaryNetworkCheckBox = loadObjects(strIdentifier, alPrimaryNetworkCheckBox);
	            return alPrimaryNetworkCheckBox;
	        }//end of if(strIdentifier.equals("primaryNetworkCheckBox"))
		 else if(strIdentifier.equals("descriptionCode"))
	        {
			 aldescriptionCode = loadObjects(strIdentifier, aldescriptionCode);
	            return aldescriptionCode;
	        }//end of if(strIdentifier.equals("descriptionCode"))
		 else if(strIdentifier.equals("consultTypeCode"))
	        {
			 alconsultationCode = loadObjects(strIdentifier, alconsultationCode);
	            return alconsultationCode;
	        }//end of if(strIdentifier.equals("alconsultationCode"))
		 else if(strIdentifier.equals("nationalityTypeCode"))
	        {
			 alnationalityCode = loadObjects(strIdentifier, alnationalityCode);
	            return alnationalityCode;
	        }//end of if(strIdentifier.equals("alconsultationCode"))
		 else if(strIdentifier.equals("providerGroup"))
	        {
			 alproviderGroup = loadObjects(strIdentifier, alproviderGroup);
	            return alproviderGroup;
	        }//end of if(strIdentifier.equals("alproviderGroup"))
		 else if(strIdentifier.equals("groupProviderList"))
	        {
			 algroupProviderList = loadObjects(strIdentifier, algroupProviderList);
	            return algroupProviderList;
	        }//end of if(strIdentifier.equals("algroupProviderList"))
		 else if(strIdentifier.equals("providerSectorList"))
	        {
			 alproviderSectorList = loadObjects(strIdentifier, alproviderSectorList);
	            return alproviderSectorList;
	        }//end of if(strIdentifier.equals("providerSectorList"))
		 else if(strIdentifier.equals("benifitTypes"))
	        {
			 albenifitType = loadObjects(strIdentifier, albenifitType);
	            return albenifitType;
	        }//end of if(strIdentifier.equals("providerSectorList"))
		
		 else if(strIdentifier.equals("pbenifitTypes"))
	        {
			 apbenifitTypes = loadObjects(strIdentifier, apbenifitTypes);
	            return apbenifitTypes;
	        }
		
		 else if(strIdentifier.equals("specialityType"))
	        {
			 alspecialityType = loadObjects(strIdentifier, alspecialityType);
	            return alspecialityType;
	        }//end of if(strIdentifier.equals("providerSectorList"))
		 else if(strIdentifier.equals("modeTypes"))
	        {
			 almodeTypes = loadObjects(strIdentifier, almodeTypes);
	            return almodeTypes;
	        }//end of if(strIdentifier.equals("providerSectorList"))
		
		 else if(strIdentifier.equals("payerCode"))
	        {
			 alPayerCode = loadObjects(strIdentifier, alPayerCode);
	            return alPayerCode;
	        }//end of if(strIdentifier.equals("payerCode"))
		 else if(strIdentifier.equals("providerCode"))
	        {
			 alProviderrCode = loadObjects(strIdentifier, alProviderrCode);
	            return alProviderrCode;
	        }//end of if(strIdentifier.equals("providerCode"))
		 else if(strIdentifier.equals("networkType"))
	        {
			 alNetworkType = loadObjects(strIdentifier, alNetworkType);
	            return alNetworkType;
	        }//end of if(strIdentifier.equals("networkType"))
		 else if(strIdentifier.equals("corpCode"))
	        {
			 alcorpCode = loadObjects(strIdentifier, alcorpCode);
	            return alcorpCode;
	        }//end of if(strIdentifier.equals("corpCode"))
		 else if(strIdentifier.equals("applicableInsCompany"))
	        {
			 alApplicableInsCompany = loadObjects(strIdentifier, alApplicableInsCompany);
	            return alApplicableInsCompany;
	        }//end of if(strIdentifier.equals("applicableInsCompany"))
		 else if(strIdentifier.equals("networkTypeForAMT"))
	        {
			 alnetworkTypeForAMT = loadObjects(strIdentifier, alnetworkTypeForAMT);
	            return alnetworkTypeForAMT;
	        }//end of if(strIdentifier.equals("networkTypeForAMT"))
		 else if(strIdentifier.equals("networkTypeForNIA"))
	        {
			 alnetworkTypeForNIA = loadObjects(strIdentifier, alnetworkTypeForNIA);
	            return alnetworkTypeForNIA;
	        }//end of if(strIdentifier.equals("networkTypeForNIA"))
		 else if(strIdentifier.equals("networkTypeForOIC"))
	        {
			 alnetworkTypeForOIC = loadObjects(strIdentifier, alnetworkTypeForOIC);
	            return alnetworkTypeForOIC;
	        }//end of if(strIdentifier.equals("networkTypeForOIC"))
		 else if(strIdentifier.equals("payerCodeSearch"))
	        {
			 alPayerCodeSearch = loadObjects(strIdentifier, alPayerCodeSearch);
	            return alPayerCodeSearch;
	        }//end of if(strIdentifier.equals("payerCode"))
		 else if(strIdentifier.equals("providerCodeSearch"))
	        {
			 alProviderrCodeSearch = loadObjects(strIdentifier, alProviderrCodeSearch);
	            return alProviderrCodeSearch;
	        }//end of if(strIdentifier.equals("providerCode"))
		 else if(strIdentifier.equals("networkTypeSearch"))
	        {
			 alNetworkTypeSearch = loadObjects(strIdentifier, alNetworkTypeSearch);
	            return alNetworkTypeSearch;
	        }//end of if(strIdentifier.equals("networkType"))
		 else if(strIdentifier.equals("corpCodeSearch"))
	        {
			 alcorpCodeSearch = loadObjects(strIdentifier, alcorpCodeSearch);
	            return alcorpCodeSearch;
	        }//end of if(strIdentifier.equals("corpCode"))
		 else if(strIdentifier.equals("serviceName"))
	        {
			 alserviceName = loadObjects(strIdentifier, alserviceName);
	            return alserviceName;
	        }//end of if(strIdentifier.equals("serviceName"))
		 else if(strIdentifier.equals("ProfessionalsNames"))
	        {
			 alProfessionalsNames = loadObjects(strIdentifier, alProfessionalsNames);
	            return alProfessionalsNames;
	        }//end of if(strIdentifier.equals("serviceName"))
		 else if(strIdentifier.equals("providerNames"))
	        {
			 alProviderNames = loadObjects(strIdentifier, alProviderNames);
	            return alProviderNames;
	        }//end of if(strIdentifier.equals("serviceName"))
		 else if(strIdentifier.equals("haadGroup"))
	        {
			 alHaadGroup = loadObjects(strIdentifier, alHaadGroup);
	            return alHaadGroup;
	        }//end of if(strIdentifier.equals("serviceName"))
		 else if(strIdentifier.equals("haadfactor"))
	        {
			 alHaadfactor = loadObjects(strIdentifier, alHaadfactor);
	            return alHaadfactor;
	        }//end of if(strIdentifier.equals("serviceName"))
		
		 else if(strIdentifier.equals("maternityEncounters"))
	        {
			 alMaternityEncounters = loadObjects(strIdentifier, alMaternityEncounters);
	            return alMaternityEncounters;
	        }//end of if(strIdentifier.equals("maternityEncounters"))
		 else if(strIdentifier.equals("activityServiceTypes"))
	        {
			 alActivityServiceTypes = loadObjects(strIdentifier, alActivityServiceTypes);
	            return alActivityServiceTypes;
	        }//end of if(strIdentifier.equals("serviceTypes"))
		 else if(strIdentifier.equals("activityServiceCodes"))
	        {
			 alActivityServiceCodes = loadObjects(strIdentifier, alActivityServiceCodes);
	            return alActivityServiceCodes;
	        }//end of if(strIdentifier.equals("alActivityServiceCodes"))		
		 else if(strIdentifier.equals("alspeciality"))
	        {
			 alalspeciality = loadObjects(strIdentifier, alalspeciality);
	            return alalspeciality;
	        }//end of if(strIdentifier.equals("serviceName"))
		
		  else if(strIdentifier.equals("groupCountry"))
	        {
	        	
	        	alpgroupCountry = loadObjects(strIdentifier,alpgroupCountry);
	        	 return alpgroupCountry;
	        }
	        else if(strIdentifier.equals("ethnicProfile"))
	        {
	        	
	        	alethnicProfile = loadObjects(strIdentifier,alethnicProfile);
	        	 return alethnicProfile;
	        }
	        else if(strIdentifier.equals("groupCurrency"))
	        {
	        	
	        	algroupCurrency = loadObjects(strIdentifier,algroupCurrency);
	        	 return algroupCurrency;
	        }
	        else if(strIdentifier.equals("noofEmployees"))
	        {
	        	
	        	alnoofEmployees = loadObjects(strIdentifier,alnoofEmployees);
	        	 return alnoofEmployees;
	        }
	        else if(strIdentifier.equals("averageAgeEmployees"))
	        {
	        	
	        	alaverageAgeEmployees = loadObjects(strIdentifier,alaverageAgeEmployees);
	        	 return alaverageAgeEmployees;
	        }
	        else if(strIdentifier.equals("employeeGenderBreak"))
	        {
	        	
	        	alemployeeGenderBreak = loadObjects(strIdentifier,alemployeeGenderBreak);
	        	 return alemployeeGenderBreak;
	        }
	        else if(strIdentifier.equals("globalCoverge"))
	        {
	        	
	        	alglobalCoverge = loadObjects(strIdentifier,alglobalCoverge);
	        	 return alglobalCoverge;
	        }
	        else if(strIdentifier.equals("familyCoverage"))
	        {
	        	
	        	alfamilyCoverage = loadObjects(strIdentifier,alfamilyCoverage);
	        	 return alfamilyCoverage;
	        }
	        else if(strIdentifier.equals("nameOfInsurer"))
	        {
	        	
	        	alnameOfInsurer = loadObjects(strIdentifier,alnameOfInsurer);
	        	 return alnameOfInsurer;
	        }
	        else if(strIdentifier.equals("nameOfTPA"))
	        {
	        	
	        	alnameOfTPA = loadObjects(strIdentifier,alnameOfTPA);
	        	 return alnameOfTPA;
	        }
	        else if(strIdentifier.equals("eligibility"))
	        {
	        	
	        	aleligibility = loadObjects(strIdentifier,aleligibility);
	        	 return aleligibility;
	        }
	        else if(strIdentifier.equals("planName"))
	        {
	        	
	        	alplanName = loadObjects(strIdentifier,alplanName);
	        	 return alplanName;
	        }
	        else if(strIdentifier.equals("areaOfCover"))
	        {
	        	
	        	alareaOfCover = loadObjects(strIdentifier,alareaOfCover);
	        	 return alareaOfCover;
	        }
	        else if(strIdentifier.equals("systemOfMedicineGroup"))
	        {
	        	
	        	alsystemOfMedicineGroup = loadObjects(strIdentifier,alsystemOfMedicineGroup);
	        	 return alsystemOfMedicineGroup;
	        }
		
		 else if(strIdentifier.equals("provDocsType"))
	        {
			 alProvDocsType = loadObjects(strIdentifier, alProvDocsType);
	            return alProvDocsType;
	        }//end of if(strIdentifier.equals("alActivityServiceCodes"))	
			else if(strIdentifier.equals("main4benifitTypes"))
	        {
	 			main4benifitTypes = loadObjects(strIdentifier, main4benifitTypes);
	            return main4benifitTypes;
	        }//end of if(strIdentifier.equals("main4benifitTypes"))
		 else if(strIdentifier.equals("partnerEncounterTypes"))
	        {
			  partnerEncounterTypes = loadObjects(strIdentifier, partnerEncounterTypes);
	            return partnerEncounterTypes;
	        }//end of if(strIdentifier.equals("partnerEncounterTypes"))
		
		 else if(strIdentifier.equals("capitationCategory"))
	        {
			 alCapitationCategory = loadObjects(strIdentifier, alCapitationCategory);
	            return alCapitationCategory;
	        }//end of if(strIdentifier.equals("alActivityServiceCodes"))	
		
		
		
		//Start AK
	       else if(strIdentifier.equals("productAuthority"))
	        {
	            alGeneralCodeproductAuthority = loadObjects(strIdentifier,alGeneralCodeproductAuthority);
	            return alGeneralCodeproductAuthority;
	        }//end of if(strIdentifier.equals("productAuthority"))
			//End AK
		 else if(strIdentifier.equals("XMLDashboards"))
	        {
			 alXMLDashboards = loadObjects(strIdentifier, alXMLDashboards);
	            return alXMLDashboards;
	        }//end of if(strIdentifier.equals("alActivityServiceCodes"))
		 else if(strIdentifier.equals("GeoLocations"))
	        {
			 alGeoLocations = loadObjects(strIdentifier, alGeoLocations);
	            return alGeoLocations;
	        }//end of if(strIdentifier.equals("GeoLocations"))
	
		
		
		 else if(strIdentifier.equals("countryCurrencyCode"))
	        {
			 alCountryCurrencyCodes = loadObjects(strIdentifier, alCountryCurrencyCodes);
	            return alCountryCurrencyCodes;
	        }//end of if(strIdentifier.equals("alActivityServiceCodes"))	
		
		 else if(strIdentifier.equals("modeofpreapprovalforEnhanced"))
	        {
	        	alModeofpreapprovalforEnhanced = loadObjects(strIdentifier, alModeofpreapprovalforEnhanced);
	            return alModeofpreapprovalforEnhanced;
	        }
		 else if(strIdentifier.equals("ipClmsPmtMethod"))
	        {
			 alIpClmsPmtMethod = loadObjects(strIdentifier, alIpClmsPmtMethod);
	            return alIpClmsPmtMethod;  
	        }
		 else if(strIdentifier.equals("policyStatusForProductSync")){
			 alpolicyStatusForProductSync = loadObjects(strIdentifier, alpolicyStatusForProductSync);
	            return alpolicyStatusForProductSync;
		 }
		
		 else if(strIdentifier.equals("policyStatusForScheme")){
			 alpolicyStatusForScheme = loadObjects(strIdentifier, alpolicyStatusForScheme);
	            return alpolicyStatusForScheme;
		 }
		 else if(strIdentifier.equals("insNetworkType")){
			 alinsNetworkType = loadObjects(strIdentifier, alinsNetworkType);
	            return alinsNetworkType;
		 }
		 else if(strIdentifier.equals("partnersList")){
			 alPartnersList = loadObjects(strIdentifier, alPartnersList);
	            return alPartnersList;
		 }
		
		 else if(strIdentifier.equals("residentailLocation") || strIdentifier.equals("workLocation"))
	        {
			 residentailLocation = loadObjects(strIdentifier, residentailLocation);
	            return residentailLocation;
	        }
		 else if(strIdentifier.equals("payerlist")){
			 alPayerlist = loadObjects(strIdentifier, alPayerlist);
	            return alPayerlist;
		 }
		 else if(strIdentifier.equals("payerlistfortariff")){
			 payerlistfortariff = loadObjects(strIdentifier, payerlistfortariff);
	            return payerlistfortariff;
		 }
		if(strIdentifier.equals("roomTypes") )
        {
		 roomTypes = loadObjects(strIdentifier, roomTypes);
            return roomTypes;
        }
		if(strIdentifier.equals("roomType") )
        {
		 roomType = loadObjects(strIdentifier, roomType);
            return roomType;
        }
		 else if(strIdentifier.equals("passportIssuedCountry")){
			 passportIssuedCountry = loadObjects(strIdentifier, passportIssuedCountry);
	            return passportIssuedCountry;
		 }
		 else if(strIdentifier.equals("productSubType"))
		 {
			 alProductSubType = loadObjects(strIdentifier, alProductSubType);
	         return alProductSubType;
		 }
		 else{
			return null;
	    }//end of else
    }//end of getCacheObject(String strIdentifier)
	
	public static ArrayList getCacheObjectIns(String strIdentifier, String auth) throws TTKException
	{
		
		if(strIdentifier.equals("insuranceCompanyName"))
		{
		alinsuranceCompanyName = loadObjectsIns(strIdentifier, alinsuranceCompanyName,auth);
        return alinsuranceCompanyName;
    } else{
		return null;
    }
	}

    public static void refresh(String strIdentifier) throws TTKException
    {
        if(strIdentifier.equals("insuranceCompany"))
        {
            alInsCompanyNames = null;
            alInsCompanyNames = loadObjects(strIdentifier,alInsCompanyNames);
        }//end of if(strIdentifier.equals("insuranceCompany"))
        if(strIdentifier.equals("brokerCompany"))
        {
            alBroCompanyNames = null;
            alBroCompanyNames = loadObjects(strIdentifier,alBroCompanyNames);
        }//end of if(strIdentifier.equals("brokerCompany"))
        if(strIdentifier.equals("officeInfo"))
        {
            alOfficeInfoCode = null;
            alOfficeInfoCode = loadObjects(strIdentifier,alOfficeInfoCode);
        }//end of if(strIdentifier.equals("insuranceCompany"))

        else if(strIdentifier.equals("productCode"))
        {
            alProductCode = null;
            alProductCode = loadObjects(strIdentifier,alProductCode);
        }//end of if(strIdentifier.equals("productCode"))

        else if(strIdentifier.equals("BRO"))
        {
        	alBroRolesCode = null;
        	alBroRolesCode = loadObjects(strIdentifier,alBroRolesCode);
        }//end of if(strIdentifier.equals("BRO"))
        
        else if(strIdentifier.equals("HOS"))
        {
            alHosRolesCode = null;
            alHosRolesCode = loadObjects(strIdentifier,alHosRolesCode);
        }//end of if(strIdentifier.equals("HOS"))
   
        else if(strIdentifier.equals("PTR"))
        {
        	  alPtrRolesCode = null;
              alPtrRolesCode = loadObjects(strIdentifier,alPtrRolesCode);
        }//end of if(strIdentifier.equals("PTR"))
        else if(strIdentifier.equals("INS"))
        {
            alInsRolesCode = null;
            alInsRolesCode = loadObjects(strIdentifier,alInsRolesCode);
        }//end of if(strIdentifier.equals("INS"))
        else if(strIdentifier.equals("TTK"))
        {
            alTTKRolesCode = null;
            alTTKRolesCode = loadObjects(strIdentifier,alTTKRolesCode);
        }//end of if(strIdentifier.equals("TTK"))
        else if(strIdentifier.equals("CAL"))
        {
            alCalRolesCode = null;
            alCalRolesCode = loadObjects(strIdentifier,alCalRolesCode);
        }//end of if(strIdentifier.equals("CAL"))
        else if(strIdentifier.equals("COR"))
        {
            alCorRolesCode = null;
            alCorRolesCode = loadObjects(strIdentifier,alCorRolesCode);
        }//end of if(strIdentifier.equals("COR"))
        else if(strIdentifier.equals("AGN"))
        {
            alAgnRolesCode = null;
            alAgnRolesCode = loadObjects(strIdentifier,alAgnRolesCode);
        }//end of if(strIdentifier.equals("AGN"))
        else if(strIdentifier.equals("DMC"))
        {
            alDmcRolesCode = null;
            alDmcRolesCode = loadObjects(strIdentifier,alDmcRolesCode);
        }//end of if(strIdentifier.equals("DMC"))
        else if(strIdentifier.equals("enrollmentWorkflow"))
        {
            alEnrollmentWorkflow = null;
            alEnrollmentWorkflow = loadObjects(strIdentifier,alEnrollmentWorkflow);
        }//end of if(strIdentifier.equals("enrollmentWorkflow"))
        else if(strIdentifier.equals("endorsementWorkflow"))
        {
            alEndorsementWorkflow = null;
            alEndorsementWorkflow = loadObjects(strIdentifier,alEndorsementWorkflow);
        }//end of if(strIdentifier.equals("endorsementWorkflow"))
        else if(strIdentifier.equals("claimsWorkflow"))
        {
        	alClaimsWorkflow = null;
        	alClaimsWorkflow = loadObjects(strIdentifier, alClaimsWorkflow);
        }//end of if(strIdentifier.equals("claimsWorkflow"))
        else if(strIdentifier.equals("icdDescription"))
        {
            alPEDCodeDescription = null;
            alPEDCodeDescription = loadObjects(strIdentifier,alPEDCodeDescription);
        }//end of if(strIdentifier.equals("icdDescription"))
        else if(strIdentifier.equals("ttkofficetype"))
        {
        	alTtkOfficeType = null;
        	alTtkOfficeType = loadObjects(strIdentifier,alTtkOfficeType);
        }//end of if(strIdentifier.equals("ttkofficetype"))
        else if(strIdentifier.equals("ttkheadofficelist"))
        {
        	alTtkOfficeName = null;
        	alTtkOfficeName = loadObjects(strIdentifier,alTtkOfficeName);
        }//end of if(strIdentifier.equals("ttkheadofficelist"))
        else if(strIdentifier.equals("assignUsers"))
        {
        	alAssignUsers = null;
        	alAssignUsers = loadObjects(strIdentifier,alAssignUsers);
        }//end of if(strIdentifier.equals("assignUsers"))
        else if(strIdentifier.equals("headOffice"))
        {
        	alBankHeadOffice = null;
        	alBankHeadOffice = loadObjects(strIdentifier,alBankHeadOffice);
        }//end of if(strIdentifier.equals("headOffice"))
        else if(strIdentifier.equals("courierName"))
        {
        	alCourierName = null;
        	alCourierName = loadObjects(strIdentifier,alCourierName);
        }//end of if(strIdentifier.equals("courierName"))
        else if(strIdentifier.equals("supportBuffer"))
        {
        	alSupportDocBuffer = null;
        	alSupportDocBuffer = loadObjects(strIdentifier,alSupportDocBuffer);
        }//end of if(strIdentifier.equals("supportBuffer"))
        else if(strIdentifier.equals("claimsSupportBuffer"))
        {
        	alClaimsSupportDocBuffer = null;
        	alClaimsSupportDocBuffer = loadObjects(strIdentifier,alClaimsSupportDocBuffer);
        }//end of if(strIdentifier.equals("claimsSupportBuffer"))
        else if(strIdentifier.equals("bankName"))
        {
        	alBankName = null;
        	alBankName = loadObjects(strIdentifier,alBankName);
        }//end of if(strIdentifier.equals("bankName"))
        else if(strIdentifier.equals("daycareGroup"))
        {
        	alDayCareGroup = null;
        	alDayCareGroup = loadObjects(strIdentifier,alDayCareGroup);
        }//end of if(strIdentifier.equals("daycareGroup"))

        //fax status report
        else if(strIdentifier.equals("faxGenUsers"))
        {
        	alFaxGenUsers = null;
        	alFaxGenUsers = loadObjects(strIdentifier,alFaxGenUsers );
        }//end of if(strIdentifier.equals("faxGenUsers"))
        else if(strIdentifier.equals("faxStatus"))
        {
        	alFaxStatus = null;
        	alFaxStatus = loadObjects(strIdentifier,alFaxStatus);
        }//end of if(strIdentifier.equals("faxStatus"))
        else if(strIdentifier.equals("notificinfo"))
        {
        	alNotifinfo = null;
        	alNotifinfo = loadObjects(strIdentifier,alNotifinfo);
        }//end of if(strIdentifier.equals("notificinfo"))
        else if(strIdentifier.equals("viewloghistory"))
        {
        	alViewLogHistory = null;
        	alViewLogHistory = loadObjects(strIdentifier,alViewLogHistory);
        }//end of if(strIdentifier.equals("viewloghistory"))
        else if(strIdentifier.equals("onlinequerystatus"))
        {
        	alSupportOnlineQuery = null;
        	alSupportOnlineQuery = loadObjects(strIdentifier,alSupportOnlineQuery);
        }//end of if(strIdentifier.equals("onlinequerystatus"))
        else if(strIdentifier.equals("tdsHospCategory"))
        {
        	alTdsHospCategory = null;
        	alTdsHospCategory = loadObjects(strIdentifier,alTdsHospCategory);
        }//end of if(strIdentifier.equals("tdsHospCategory"))
        else if(strIdentifier.equals("providerCodeSearch"))
        {
        	alProviderrCodeSearch = null;
        	alProviderrCodeSearch = loadObjects(strIdentifier,alProviderrCodeSearch);
        }//end of if(strIdentifier.equals("providerCodeSearch"))
        else if(strIdentifier.equals("providerCode"))
        {
        	alProductCode = null;
        	alProductCode = loadObjects(strIdentifier,alProductCode);
        }//end of if(strIdentifier.equals("providerCode"))
        else if(strIdentifier.equals("groupProviderList"))
        {
        	algroupProviderList = null;
        	algroupProviderList = loadObjects(strIdentifier,algroupProviderList);
        }//end of if(strIdentifier.equals("providerCode"))
        else if(strIdentifier.equals("corpCodeSearch"))
        {
        	alcorpCodeSearch = null;
        	alcorpCodeSearch = loadObjects(strIdentifier,alcorpCodeSearch);
        }//end of if(strIdentifier.equals("providerCode"))
        else if(strIdentifier.equals("corpCode"))
        {
        	alcorpCode = null;
        	alcorpCode = loadObjects(strIdentifier,alcorpCode);
        }//end of if(strIdentifier.equals("providerCode"))
        else if(strIdentifier.equals("payerCodeSearch"))
        {
        	alpayerCodeSearch = null;
        	alpayerCodeSearch = loadObjects(strIdentifier,alpayerCodeSearch);
        }//end of if(strIdentifier.equals("providerCode"))
        else if(strIdentifier.equals("payerCode"))
        {
        	alPayerCode = null;
        	alPayerCode = loadObjects(strIdentifier,alPayerCode);
        }//end of if(strIdentifier.equals("providerCode"))
        else if(strIdentifier.equals("ProviderList"))
        {
        	alProviderList = null;
        	alProviderList = loadObjects(strIdentifier,alProviderList);
        }//end of if(strIdentifier.equals("tdsHospCategory"))
        else if(strIdentifier.equals("primaryNetwork"))
        {
        	alprimaryNetwork = null;
        	alprimaryNetwork = loadObjects(strIdentifier,alprimaryNetwork);
        }//end of if(strIdentifier.equals("primaryNetwork"))
        
        
    }//end of public static void refresh(String strIdentifier)

    /**
     * Returns the ArrayList object which contains the cache objects
     * @param strIdentifier String object which identifies the array list to be populated
     * @param alCacheObjects ArrayList the array list to be populated with cache objects
     * @return ArrayList object which contains the cache objects
     * @exception throws TTKException
     */
	private static ArrayList loadObjects(String strIdentifier, ArrayList alCacheObjects) throws TTKException
    {
		if(alCacheObjects != null)
		{
		    return alCacheObjects;
		}//end of if(alCacheObjects != null)
        else
        {
            return getCacheManagerObject().loadObjects(strIdentifier);
        }//end of else
    }//end of loadObjects(String strIdentifier, ArrayList alCacheObjects)
	
	/**
     * Returns the ArrayList object which contains the cache objects
     * @param strIdentifier String object which identifies the array list to be populated
     * @param alCacheObjects ArrayList the array list to be populated with cache objects
     * @return ArrayList object which contains the cache objects
     * @exception throws TTKException
     */
	private static ArrayList loadObjectsIns(String strIdentifier, ArrayList alCacheObjects, String auth) throws TTKException
    {
		if(alCacheObjects != null)
		{
		    return alCacheObjects;
		}//end of if(alCacheObjects != null)
        else
        {
            return getCacheManagerObject().loadObjectsIns(strIdentifier,auth);
        }//end of else
    }//end of loadObjects(String strIdentifier, ArrayList alCacheObjects)
	 /**
    * Returns the ArrayList object which contains the cache objects
    * @param strIdentifier String object which identifies the array list to be populated
    * @param alCacheObjects ArrayList the array list to be populated with cache objects
    * @return ArrayList object which contains the cache objects
    * @exception throws TTKException
    */
	private static ArrayList loadObjects1(String strIdentifier, ArrayList alCacheObjects,Long strClaimSeqId) throws TTKException
   {
		if(alCacheObjects != null)
		{
		    return alCacheObjects;
		}//end of if(alCacheObjects != null)
       else
       {
           return getCacheManagerObject().loadObjects1(strIdentifier,strClaimSeqId);
       }//end of else
   }//end of loadObjects(String strIdentifier, ArrayList alCacheObjects)
	
	
	/**
	 * //Tariff - blocking Network types based on nase network -INTX
	    * Returns the ArrayList object which contains the cache objects
	    * @param strIdentifier String object which identifies the array list to be populated
	    * @param alCacheObjects ArrayList the array list to be populated with cache objects
	    * @return ArrayList object which contains the cache objects
	    * @exception throws TTKException
	    */
		private static ArrayList loadObjects1(String strIdentifier, ArrayList alCacheObjects,String strCondParam,HttpServletRequest request) throws TTKException
	   {
			if(alCacheObjects != null)
			{
			    return alCacheObjects;
			}//end of if(alCacheObjects != null)
	       else
	       {
	           return getCacheManagerObject().loadObjects1(strIdentifier,strCondParam,request);
	       }//end of else
	   }//end of loadObjects(String strIdentifier, ArrayList alCacheObjects)


    /**
     * Returns the CacheManager object for invoking methods on it.
     * @return CacheManager object which can be used for calling the methods on session bean
     * @exception throws TTKException
     */
	private static CacheManager getCacheManagerObject() throws TTKException
    {
        CacheManager cacheManager = null;
        try
        {
            if(cacheManager == null)
            {
                InitialContext ctx = new InitialContext();
                cacheManager = (CacheManager) ctx.lookup("java:global/TTKServices/business.ejb3/CacheManagerBean!com.ttk.business.common.CacheManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "cache");
        }//end of catch
        return cacheManager;
    }//end getCacheManagerObject()

}//end of class Cache
