/**
 *   @ (#) PreAuthManager.java April 10, 2006
 *   Project 	   : TTK HealthCare Services
 *   File          : PreAuthManager.java
 *   Author        : RamaKrishna K M
 *   Company       : Span Systems Corporation
 *   Date Created  : April 10, 2006
 *
 *   @author       :  RamaKrishna K M
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */

package com.ttk.business.preauth;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Local;

import org.dom4j.Document;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.claims.AdditionalHospitalDetailVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.BalanceCopayDeductionVO;//added as per kOC 1140 and 1142 (1165)
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.ClinicianDetailsVO;
import com.ttk.dto.preauth.DentalOrthoVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.ICDPCSVO;
import com.ttk.dto.preauth.InsOverrideConfVO;
import com.ttk.dto.preauth.ObservationDetailsVO;
import com.ttk.dto.preauth.PreAuthAilmentVO;
import com.ttk.dto.preauth.PreAuthAssignmentVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthMedicalVO;
import com.ttk.dto.preauth.PreAuthTariffVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.SIInfoVO;
import com.ttk.dto.preauth.ShortfallVO;

@Local

public interface PreAuthManager {

    /**
     * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
     * @exception throws TTKException
     */
    public ArrayList getActivityCodeList(ArrayList alSearchCriteria) throws TTKException;
    public ArrayList getPreAuthEnhancementList(ArrayList alSearchCriteria) throws TTKException;
    public ActivityDetailsVO getActivityCodeTariff(String activityTypeId,String activityCode,String preAuthSeqID,String activityStartDate,String authType,String overrideYN,String condDenialCode) throws TTKException;
    public ArrayList getDiagnosisCodeList(ArrayList alSearchCriteria) throws TTKException;
    public ArrayList getProviderList(ArrayList alSearchCriteria) throws TTKException;
    public ArrayList getClinicianList(ArrayList alSearchCriteria) throws TTKException;
    public ArrayList getProviderClinicianList(ArrayList alSearchCriteria) throws TTKException;
    public ClinicianDetailsVO getProviderClinicianDetails(String clinicianId,Long hospSeqId) throws TTKException;
    
    
    /**
     * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthList(ArrayList alSearchCriteria) throws TTKException;

    /**
     * This method returns the HashMap of String,String  which contains Network Types details
     * @return HashMap of String object which contains Network Types details
     * @exception throws TTKException
     */
    public HashMap<String,String> getNetworkTypeList() throws TTKException;
    /**
     * This method returns the PreAuthDetailVO, which contains all the Pre-Authorization details
     * @param preauthVO Object which contains seq id's to get the Pre-Authorization Details
     * @param strSelectionType String object which contains Selection Type - 'PAT' or 'ICO'
     * @return PreAuthDetailVO object which contains all the Pre-Authorization details
     * @exception throws TTKException
     */
    
    public PreAuthDetailVO getPreAuthDetails(Long preAuthSeqId) throws TTKException;
    
    public ArrayList<String[]> getDiagnosisDetails(Long preAuthSeqId) throws TTKException;
    public DiagnosisDetailsVO getDiagnosis(DiagnosisDetailsVO diagnosisDetailsVO) throws TTKException;
    public DiagnosisDetailsVO getIcdCodeDetails(String icdcode,long preauthSeqId,String authType) throws TTKException;
    public Object[] saveObservationDetails(ObservationDetailsVO observationDetailsVO) throws TTKException;
    public ObservationDetailsVO getObservDetail(Long observSeqId) throws TTKException;
    public Object[] getObservTypeDetails(String observType) throws TTKException;
    public long deleteObservDetails(Long preauthSeqId,String listOfobsvrSeqIds,String mode) throws TTKException;
    public ArrayList<String[]> getAllObservDetails(Long activityDtlSeqId) throws TTKException;
    public Document getPreAuthHistory(Long lngSeqId) throws TTKException ;
    public Document getClaimHistory(Long lngSeqId) throws TTKException ;
    public ActivityDetailsVO getActivityDetails(Long activityDtlSeqId) throws TTKException;
    public int deleteShortfallsDetails(String[] shortfallsDeatils) throws TTKException;
    public Object[] deleteDiagnosisDetails(DiagnosisDetailsVO diagnosisDetailsVO) throws TTKException;
    public Object[] deleteActivityDetails(long preauthSeqId,long activityDtlSeqId,String authType) throws TTKException;
    public BigDecimal[] getCalculatedPreauthAmount(Long preAuthSeqId,Long hospitalSeqID) throws TTKException;
    public Object[] saveAndCompletePreauth(PreAuthDetailVO  preAuthDetailVO) throws TTKException;
    public Map<String,String> getOtpresult(String memberId,Long adedBy,String otpNumber)throws Exception;
    public Map<String,String> getEncounterTypes(String benefitId)throws Exception;
    
    /**
     * This method returns the ShortfallVO, which contains all the Pre-Authorization/Claims Shortfall Details
     * @param alShortfallList object contains SeqID,Preauth/ClaimSeqID to fetch the Pre-Authorization/Claims Shortfall Details
     * @return ShortfallVO object which contains all the Pre-Authorization Shortfall/Claims Details
     * @exception throws TTKException
     */
    public ArrayList<String[]> getPreauthHistoryList(Long memberSeqID,String authType,String preauthorclaimNumber) throws TTKException;
  
    
    /**
     * This method returns the ShortfallVO, which contains all the Pre-Authorization/Claims Shortfall Details
     * @param alShortfallList object contains SeqID,Preauth/ClaimSeqID to fetch the Pre-Authorization/Claims Shortfall Details
     * @return ShortfallVO object which contains all the Pre-Authorization Shortfall/Claims Details
     * @exception throws TTKException
     */
    public ShortfallVO getShortfallDetail(ArrayList alShortfallList) throws TTKException;
    /**
     * This method saves the Pre-Authorization Shortfall information
     * @param shortfallVO ShortfallVO contains Pre-Authorization Shortfall information
     * @param strSelectionType
     * @return long value which contains Shortfall Seq ID
     * @exception throws TTKException
     */
    public long saveShortfall(ShortfallVO shortfallVO,String strSelectionType) throws TTKException;
    /**
     * This method saves the Pre-Authorization information
     * @param preAuthDetailVO PreAuthDetailVO contains Pre-Authorization information
     * @param strSelectionType String object which contains Selection Type - 'PAT' or 'ICO'
     * @return Object[] the values,of  MEMBER_SEQ_ID , PAT_GEN_DETAIL_SEQ_ID and PAT_ENROLL_DETAIL_SEQ_ID
     * @exception throws TTKException
     */
    public Object[] savePreAuth(PreAuthDetailVO preAuthDetailVO) throws TTKException;
    public Object[] saveDiagnosisDetails(DiagnosisDetailsVO diagnosisDetailsVO) throws TTKException;    
    public Object[] saveActivityDetails(ActivityDetailsVO activityDetailsVO) throws TTKException;    
    public String[] addPreauthDetails(PreAuthDetailVO preAuthDetailVO,String uploadedBy) throws TTKException;
   
   /**
  	 * This method will allow to Save the Preauth Activity Details information
  	 * @param Map which contains Activity Details
  	 * @return String[] which contains effected rows Information
  	 * @exception throws TTKException
  	 */
    /**
	 * This method will allow to Override the Preauth information
	 * @param lngPATGenDetailSeqID PATGenDetailSeqID
	 * @param lngPATEnrollDtlSeqID PATEnrollDtlSeqID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO overridePreauth(long lngPATGenDetailSeqID,long lngPATEnrollDtlSeqID,long lngUserSeqID) throws TTKException;

    /**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @param strType String object which contains Type
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType) throws TTKException;

    /**
     * This method returns the ArrayList object, which contains all the Users for the Corresponding TTK Branch
     * @param alAssignUserList ArrayList Object contains PreauthSeqID/ClaimSeqID,PolicySeqID and TTKBranch 
     * @param strMode contains PAT/CLM for identifying the Pre-Authorization/Claims flow 
     * @return ArrayList object which contains all the Users for the Corresponding TTK Branch
     * @exception throws TTKException
     */
    public ArrayList getAssignUserList(ArrayList alAssignUserList,String strMode) throws TTKException;

    /**
     * This method assigns the Pre-Authorization information to the corresponding Doctor
     * @param preAuthAssignmentVO PreAuthAssignmentVO which contains Pre-Authorization information to assign the corresponding Doctor
     * @return long value which contains Assign Users Seq ID
     * @exception throws TTKException
     */
    public long assignPreAuth(PreAuthAssignmentVO preAuthAssignmentVO) throws TTKException;

    /**
     * This method returns the PreAuthAssignmentVO  which contains Assignment details
     * @param lngAssignUsersSeqID long value contains Assign User Seq ID
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @param strMode contains PAT/CLM for identifying Pre-Authorization/Claims 
     * @return ArrayList of PreAuthAssignmentVO object which contains Assignment details
     * @exception throws TTKException
     */
    public PreAuthAssignmentVO getAssignTo(long lngAssignUsersSeqID,long lngUserSeqID,String strMode) throws TTKException;

    /**
     * This method returns the Arraylist of ClaimantVO's  which contains Claimant details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimantVO object which contains Claimant details
     * @exception throws TTKException
     */
    public ArrayList getClaimantList(ArrayList alSearchCriteria) throws TTKException;

    /**
     * This method returns the Arraylist of EnhancementVO's  which contains SumInsured Enhancement details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of EnhancementVO object which contains SumInsured Enhancement details
     * @exception throws TTKException
     */
    public ArrayList getSumInsuredEnhancementList(ArrayList alSearchCriteria) throws TTKException;

    /**
     * This method returns the PreAuthMedicalVO, which contains all the Pre-Authorization Medical Details
     * @param lngPATGenDetailSeqID long value which contains pre-authseq id to get the Pre-Authorization Medical Details
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthMedicalVO object which contains all the Pre-Authorization Medical details
     * @exception throws TTKException
     */
    public PreAuthMedicalVO getMedicalDetail(long lngPATGenDetailSeqID,long lngUserSeqID) throws TTKException;

    /**
     * This method returns the PreAuthMedicalVO, which contains all the Claims Medical Details
     * @param lngClaimSeqID long value which contains claimseq id to get the Claims Medical Details
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthMedicalVO object which contains all the Claims Medical details
     * @exception throws TTKException
     */
    public PreAuthMedicalVO getClaimMedicalDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException;
    
    /**
     * This method returns the String Object, which contains Doctor Opinion
     * @param lngClaimSeqID long value which contains claimseq id to get the Doctor Opinion Information
     * @return String Object, which contains Doctor Opinion
     * @exception throws TTKException
     */
    public String getDoctorOpinion(long lngClaimSeqID) throws TTKException;
    
    /**
     * This method saves the Doctor Opinion Information
     * @param lngClaimSeqID long value which contains claimseq id to save the Doctor Opinion Information
     * @param strDoctorOpinion String Object which contains Doctor Opinion Information
     * @param lngUserSeqID long value which contains Logged-in User
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveDoctorOpinion(long lngClaimSeqID,String strDoctorOpinion,long lngUserSeqID) throws TTKException;

    /**
     * This method returns the AdditionalHospitalDetailVO, which contains all the Claim Hospital Additional Details
     * @param lngClaimHospAssocSeqID long value which contains claim hosp assoc seq id to get the Claim Hospital Additional Details
     * @param lngUserSeqID long value which contains Logged-in User
     * @return AdditionalHospitalDetailVO object which contains all the Claim Hospital Additional details
     * @exception throws TTKException
     */
    public AdditionalHospitalDetailVO getAdditionalHospitalDetail(long lngClaimHospAssocSeqID,long lngUserSeqID) throws TTKException;

    /**
     * This method saves the Claim Hospital Additional information
     * @param addHospitalDetailVO AdditionalHospitalDetailVO contains Claim Hospital Additional
     * @return long value which contains Add Hospital Dtl Seq ID
     * @exception throws TTKException
     */
    public long saveAdditionalHospitalDetail(AdditionalHospitalDetailVO addHospitalDetailVO) throws TTKException;
	//added for KOC-Decoupling
    /**
     * This method returns the ArrayList, which contains Diagnosis list Details
     * @param alDiagnosisList which contains PAT_GEN_DETAIL_SEQ_ID,CLAIM_SEQ_ID to get the Diagnosis Details
     * @return ArrayList object which contains Diagnosis Details
     * @exception throws TTKException
     */
    public ArrayList getDiagnosisList(ArrayList alDiagnosisList) throws TTKException;
    
    /**This Method returns PreAuthAilmentVO, which contains all the Ailment Details
     * @param lngDiagSeqId value which contains Diagnosis Seq Id
     * @exception throws TTKException
     */
    public PreAuthAilmentVO getDiagnosisDetail(long lngDiagSeqId)throws TTKException;    
    
    /**
     * This method returns the PreAuthMedicalVO, which contains all the Pre-Authorization Ailment Details
     * @param lngSeqID long value which contains pre-authseq id/ClaimSEQID to get the Pre-Authorization/Claims Ailment Details
     * @param lngUserSeqID long value which contains Logged-in User seq id
     * @param strMode String Object contains mode for identifying Pre-Authorization/Claims - PAT/CLM
     * @return PreAuthAilmentVO object which contains all the Pre-Authorization/Claims Ailment and Associated Illness details
     * @exception throws TTKException
     */
    public PreAuthAilmentVO getAilmentDetail(long lngSeqID,long lngUserSeqID,String strMode) throws TTKException;

    /**
     * This method saves the Pre-Authorization Ailment information
     * @param preAuthAilmentVO PreAuthAilmentVO contains Pre-Authorization Ailment information and Associated Illness information
     * @return long value which contains Ailment Seq ID
     * @exception throws TTKException
     */
    public long saveAilment(PreAuthAilmentVO preAuthAilmentVO) throws TTKException;
	/**Added for KOC-Decoupling
     * This method saves the diagnosis Details
     * @param preAuthAilmentVo PreAuthAilmentVO contains Ailment information
     * @return long value which contains Diagnosis Seq ID
     * @Exception throws TTKException
     * 
     */
    public long saveDiagnosis(PreAuthAilmentVO preAuthAilmentVO) throws TTKException;    
    
    /**
     * This method returns the ICDPCSVO, which contains all the ICD Package Details
     * @param lngIcdPcsSeqID long value which contains seq id to get the ICD/PCS Details
     * @return ICDPCSVO object which contains all the ICD/PCS Details
     * @exception throws TTKException
     */
    public ICDPCSVO getICDPCSDetail(long lngIcdPcsSeqID,long lngUserSeqID) throws TTKException;

    /**
     * This method saves the Pre-Authorization ICD Package information
     * @param icdPCSVO ICDPCSVO contains ICD Package information
     * @param strMode String contains Mode for Pre-Authorization/Claims as - PAT / CLM
     * @return long value which contains ICDPCS Seq ID
     * @exception throws TTKException
     */
    public long saveICDPackage(ICDPCSVO icdPCSVO,String strMode) throws TTKException;

    /**
     * This method deletes the ICD Package information from the database
     * @param alPreAuth arraylist which the the details of the Pre-Authorization has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteICDPackage(ArrayList alICDPackage) throws TTKException;
	/**added for KOC-Decoupling
     * This method deletes the selected diagnosis information from the database  
     * @param DiagnosisSeqId which the details of the diagnosis to be deleted
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
    public int deleteDiagnosis(long deleteSeqId) throws TTKException;
    
    /**
     * This method returns the AuthorizationVO, which contains Authorization Details
     * @param alAuthList ArrayList object which contains MemberSeqID,PAT_GEN_DETAIL_SEQ_ID,PAT_ENROLL_DETAIL_SEQ_ID and USER_SEQ_ID to get the Authorization Details
     * @param strIdentifier String which contains module identifer Pre-Authorization/Claims
     * @return AuthorizationVO object which contains Authorization Details
     * @exception throws TTKException
     */
    public AuthorizationVO getAuthorizationDetail(ArrayList alAuthList,String strIdentifier) throws TTKException;

    /**
     * This method saves the Authorization information
     * @param authorizationVO AuthorizationVO contains Authorization information
     * @param strIdentifier Pre-Authorization/Claims
     * @return long int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveAuthorization(AuthorizationVO authorizationVO,String strIdentifier) throws TTKException;

   /**bajaj
     * This method send  the intimation   to insurer 
     * @param authorizationVO AuthorizationVO contains Authorization information
     * @param strIdentifier Pre-Authorization/Claims
     * @return long int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int sendInsIntimate(AuthorizationVO authorizationVO,String strIdentifier) throws TTKException;


   /**
     * This method returns the PreAuthTariffVO, which contains Tariff Details
     * @param alTariffList ArrayList contains PAT_GEN_DETAIL_SEQ_ID,Enroll Dtl Seq ID,logged-in User Seq ID and Policy Seq ID
     * @return PreAuthTariffVO object which contains Tariff Details
     * @exception throws TTKException
     */
    public PreAuthTariffVO getTariffDetail(ArrayList alTariffList) throws TTKException;
    
    /**
     * This method returns the PreAuthTariffVO, which contains Tariff Details
     * @param alTariffList ArrayList contains PAT_GEN_DETAIL_SEQ_ID,Enroll Dtl Seq ID,logged-in User Seq ID and Policy Seq ID
     * @return PreAuthTariffVO object which contains Tariff Details
     * @exception throws TTKException
     */
    public PreAuthTariffVO getPATariffDetail(ArrayList alTariffList) throws TTKException;

    /**
     * This method saves the Tariff information
     * @param preAuthTariffVO PreAuthTariffVO contains Tariff information
     * @return long value contains Tariff Header Seq ID
     * @exception throws TTKException
     */
    public long saveTariffDetail(PreAuthTariffVO preAuthTariffVO) throws TTKException;

    /**
     * This method Calculates the Tariff information
     * @param lngPreauthSeqID contains PAT_GEN_DETAIL_SEQ_ID
     * @param lngUserSeqID long value which contains Logged-in User seq id
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int calculateTariff(long lngPreauthSeqID,long lngUserSeqID) throws TTKException;

    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
    
    
    
	public int deletePATGeneral(ArrayList alDeleteList) throws TTKException;

	/**
     * This method returns the ArrayList, which contains Discrepancy Details
     * @param alDiscrepancyList lwhich contains PAT_GEN_DETAIL_SEQ_ID,CLAIM_SEQ_ID,USER_SEQ_ID to get the Discrepancy Details
     * @return ArrayList object which contains Discrepancy Details
     * @exception throws TTKException
     */
	public ArrayList getDiscrepancyInfo(ArrayList alDiscrepancyList) throws TTKException;

	/**
     * This method resolves the Discrepancy information
     * @param alDiscrepancyVO ArrayList contains Discrepancy information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int resolveDiscrepancy(ArrayList alDiscrepancyVO) throws TTKException;
	
	/**
     * This method returns the ArrayList, which contains AccountHeads
     * @param lngPreauthSeqID which contains PAT_GEN_DETAIL_SEQ_ID to get the AccountHeads
     * @return ArrayList object which contains AccountHeads Details
     * @exception throws TTKException
     */
	public ArrayList getAccountHeadInfo(long lngPreauthSeqID) throws TTKException;
	
	/**
     * This method returns the HashMap, which contains AccountHeads
     * @param lngPreauthSeqID which contains PAT_GEN_DETAIL_SEQ_ID to get the AccountHeads
     * @return HashMap object which contains AccountHeads Details
     * @exception throws TTKException
     */
	public HashMap getAccountHeadInfoList(long lngPreauthSeqID) throws TTKException;
	
	/**
     * This method saves the Tariff information
     * @param lngPreauthSeqID contains PAT_GEN_DETAIL_SEQ_ID
     * @param strSelectedSeqID contains Concatenated Selected Seq ID's
     * @param lngUserSeqID long value which contains Logged-in User seq id
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveSelectedAccountHeads(long lngPreauthSeqID,String strSelectedSeqID,long lngUserSeqID) throws TTKException;
	
	/**
     * This method returns the PreAuthTariffVO, which contains Tariff Details
     * @param alTariffList ArrayList contains PAT_GEN_DETAIL_SEQ_ID,Enroll Dtl Seq ID,logged-in User Seq ID and Policy Seq ID
     * @return PreAuthTariffVO object which contains Tariff Details
     * @exception throws TTKException
     */
    public PreAuthTariffVO getAccountHeadTariffDetail(ArrayList alTariffList) throws TTKException;
    
    /**
     * This method saves the Tariff information
     * @param preAuthTariffVO PreAuthTariffVO contains Tariff information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveAccountHeadTariffDetail(PreAuthTariffVO preAuthTariffVO) throws TTKException;
    
    /**
	 * This method will save the Review Information and Workflow will change to Coding
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO returnToCoding(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException;
	
    /**
	 * This method reassign the enrollment ID
	 * @param alReAssignEnrID the arraylist of details for which have to be reassigned
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int reAssignEnrID(ArrayList alReAssignEnrID) throws TTKException;
	
	/**
     * This method returns the Arraylist of ClaimantVO's  which contains Claimant details
     * @param lngMemSeqID String object which contains the search criteria
     * @return ArrayList of ClaimantVO object which contains Claimant details
     * @exception throws TTKException
     */
    public ClaimantVO getSelectEnrollmentID(Long lngMemSeqID) throws TTKException; 
    
    /**
     * This method returns the Arraylist of ClaimantVO's  which contains Claimant details.
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimantVO object which contains Claimant details
     * @exception throws TTKException
     */
    public ArrayList getEnrollmentList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method returns the SIInfoVO, which contains Balance SI Details.
     * @param lngMemSeqID long value which contains member id to get the Balance SI Details
     * @param lngPolicyGrpSeqID long value which contains Policy Group Seq ID
     * @param lngBalanceSeqID long value which contains Balance Seq ID
     * @return PreAuthMedicalVO object which contains Balance SI Details
     * @exception throws TTKException
     */
    public SIInfoVO getBalanceSIDetail(long lngMemSeqID,long lngPolicyGrpSeqID,long lngBalanceSeqID) throws TTKException;
	
	/**	//Changes As per KOC 1142 ChangeRequest(Copay restriction)
     * This method returns the BalanceCopayDeductionVO, which contains Copay Adviced SI Details.
     * @param strIdentifier specifies module
     * @param lngPatGenDtlSeqID long value which contains Preauthorization general detail Seq id
     * @param lngMemSeqID long value which contains Member Seq ID
     * @param lngPolicyGrpSeqID long value which contains Policy Group SeQ Id
     * @param lngBalanceSeqID long value which contains BAlance Seq ID
     * @return PreAuthMedicalVO object which contains Balance SI Details
     * @exception throws TTKException
     */
   public BalanceCopayDeductionVO getcopayAdviced(String strIdentifier,long lngPatGenDtlSeqID,long lngMemSeqID,long lngPolicyGrpSeqID,long lngBalanceSeqID) throws TTKException;	//Changes As per KOC 1142 ChangeRequest(Copay restriction)


	/**1274A
    * This method saves the File uploaded for unfreeze preauth status
    * @param ArrayList alFileAUploadList contains Save information
    * returns long value
    * @exception throws TTKException
    */
	public long saveUpoadedFilename(ArrayList alFileAUploadList) throws TTKException;
	
	/**1274A
    * This method saves the File uploaded for unfreeze preauth status
    * @param ArrayList alUnfreezeList contains Save information
    * returns long value
    * @exception throws TTKException
    */
   public int accountUnfreeze(ArrayList alUnfreezeList) throws TTKException;
   
   public int accountinsOverrideConf(ArrayList alUnfreezeList) throws TTKException;
	
   public InsOverrideConfVO getConfigDetails(long lngProdPolicySeqId,String Mode) throws TTKException;

  public int saveOverrideConfigDetails(InsOverrideConfVO insOverrideConfVO)throws TTKException;

  /**1274A
   * This method saves the File uploaded for unfreeze preauth status
   * @param ArrayList alUnfreezeList contains Save information
   * returns long value
   * @exception throws TTKException
   */
  public HashMap<String,String> getProviders() throws TTKException;

    
  
  public long overridPreAuthDetails(String preAuthSeqID,String overrideRemarks,Long userSeqID) throws TTKException;

      /**
       * This method get the DHPO upload preauth details  
       * @param PreAuthSeqID
       * returns DhpoWebServiceVO 
       * @exception throws TTKException
       */
   	public DhpoWebServiceVO getDhpoPreauthDetails(String preauthSeqID) throws TTKException;
   	
   	/**
     * This method get the DHPO upload preauth log details  
     * @param  DhpoWebServiceVO DHPO upload preauth log details  
     * returns row proccessed 
     * @exception throws TTKException
     */
 	public long saveDhpoPreauthLogDetails(DhpoWebServiceVO dhpoWebServiceVO) throws TTKException;
 	
 	
 	/**
     * This method get the DHPO upload preauth details  
     * @param PreAuthSeqID
     * returns DhpoWebServiceVO 
     * @exception throws TTKException
     */
 	public DhpoWebServiceVO getDhpoPreauthLogDetails(String preauthSeqID) throws TTKException;	
 	
   	
   public HashMap<String, String> getProviders(String type)throws TTKException;
   
   public ArrayList<MOUDocumentVO> getProviderDocs(String preauthSeqID)throws TTKException;
   
   
   public HashMap<String, String> getPreauthDiagDetails() throws TTKException;



  	public Document getXmlTOSendCEED(Long clmAuthSeqId) throws TTKException;
      
  	public Document getPATXmlTOSendCEED(Long preAuthSeqID) throws TTKException;
  	
  	
	public int getCountOralPreAuth() throws TTKException;
  	

  	public ArrayList<Object> getBenefitDetails(ArrayList<Object> alSearchCriteria) throws TTKException;
	public ArrayList getMemberList(ArrayList<Object> searchData) throws TTKException;
	public PreAuthDetailVO getMemberDetails(String memberId) throws TTKException;
	public ArrayList getProviderMemberList(ArrayList<Object> searchData,Long lngHospSeqId)throws TTKException;
	public Document getPATXmlForViewDocument(Long preAuthSeqID) throws TTKException;
	
	 public Object[] getPartnerPreAuthDetails(Long preAuthSeqId) throws TTKException;
	     
	    public String saveDentalDetails(DentalOrthoVO dentalOrthoVO,String PatOrClm) throws TTKException;
	    
	    public BigDecimal[] getCalculatedPreauthAmount(Long preAuthSeqId,Long hospitalSeqID, Long userSeqId) throws TTKException;

	    
	    public Object[] deleteActivityDetails(long preauthSeqId,long activityDtlSeqId,String authType,long userSeqId) throws TTKException;
	    
	    public InputStream getShortfallDBFile(String shortfallSeqID)throws TTKException;
	    
	    /**
	     * This method returns the Arraylist of PreAuthVO's  which contains Partner Pre-Authorization details
	     * @param alSearchCriteria ArrayList object which contains the search criteria
	     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
	     * @exception throws TTKException
	     */
	    public ArrayList getPartnerPreAuthList(ArrayList alSearchCriteria) throws TTKException;
		
	    public String[][]  getMemberRuleData(String strBenefitType,String memberSeqID)throws TTKException;

	    public String[][]  getCustomerForBenifits(Long lngMemberseqid,String benifitType) throws TTKException;
		
	    public ArrayList getPolicyDocsUploads(long policyseqid)throws TTKException;
	    
	    public int deleteAuthorizationDetails(long claimSeqID) throws TTKException;
	    public int ceedResponseDetails(String Mode,Long claimSeqID ,Long activitySeqId,String res) throws TTKException;
	    public ArrayList<MOUDocumentVO> getPreauthDocsUploads(String preAuthSeqID) throws TTKException;
	    
	    public ArrayList<Object> getDenialDescriptionsList(String payerAuthority) throws TTKException;		// for denial desc
	    
	 // for preauth docs upload    
	    public int savePreauthDocsUploads(ArrayList alFileAUploadList,Long userSeqId,String preAuthSeqID,String origFileName,java.io.InputStream inputStream,int formFileSize) throws TTKException;
	    public int delPreauthUploadedFile(String moudocseqid, String deleteRemarks, Long userSeqId)throws TTKException;
	    public long getPreAuthSeqID(String authNumber) throws TTKException;
	    public Object[] getProviderObservTypeDetails(String observType) throws TTKException;
	    
	    public ArrayList<String[]> getProviderAllObservDetails(Long activityDtlSeqId) throws TTKException;
	    public ObservationDetailsVO getProObservDetails(Long observSeqId) throws TTKException;
	    public Object[] saveProviderObservationDetails(ObservationDetailsVO observationDetailsVO) throws TTKException;
	    public long deleteProObservDetails(Long preauthSeqId,String listOfobsvrSeqIds) throws TTKException;
	    public Object[] saveProviderObserDetails(ObservationDetailsVO observationDetailsVO) throws TTKException;
	    public Object[] getEpreauthDocs(Long preAuthSeqID,Long activityDtlSeqId) throws TTKException;
}//end of PreAuthManager