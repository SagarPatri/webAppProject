/**
 * @ (#) PreAuthManagerBean.java Apr 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 10, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.dataentrypreauth;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.dataentrypreauth.PreAuthDAOFactory;
import com.ttk.dao.impl.dataentrypreauth.PreAuthDAOImpl;
import com.ttk.dao.impl.dataentrypreauth.PreAuthMedicalDAOImpl;
import com.ttk.dto.claims.AdditionalHospitalDetailVO;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.BalanceCopayDeductionVO;//added as per KOC 1140 and 1142(1165)
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.ICDPCSVO;
import com.ttk.dto.preauth.PreAuthAilmentVO;
import com.ttk.dto.preauth.PreAuthAssignmentVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthMedicalVO;
import com.ttk.dto.preauth.PreAuthTariffVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.SIInfoVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ParallelPreAuthManagerBean implements ParallelPreAuthManager{

    private PreAuthDAOFactory preAuthDAOFactory = null;
    private PreAuthDAOImpl preAuthDAO = null;
    private PreAuthMedicalDAOImpl preAuthMedicalDAO = null;

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getPreAuthDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (preAuthDAOFactory == null)
                preAuthDAOFactory = new PreAuthDAOFactory();
            if(strIdentifier!=null)
            {
                return preAuthDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//End of getPreAuthDAO(String strIdentifier)

    /**
     * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthList(ArrayList alSearchCriteria) throws TTKException{
        preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
        return preAuthDAO.getPreAuthList(alSearchCriteria);
    }//end of getPreAuthList(ArrayList alSearchCriteria)

    /**
     * This method returns the PreAuthDetailVO, which contains all the Pre-Authorization details
     * @param preauthVO Object which contains seq id's to get the Pre-Authorization Details
     * @param strSelectionType String object which contains Selection Type - 'PAT' or 'ICO'
     * @return PreAuthDetailVO object which contains all the Pre-Authorization details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getPreAuthDetail(PreAuthVO preauthVO,String strSelectionType) throws TTKException{
         preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
         return preAuthDAO.getPreAuthDetail(preauthVO,strSelectionType);
    }//end of getPreAuthDetail(PreAuthVO preauthVO,String strSelectionType)

    /**
     * This method saves the Pre-Authorization information
     * @param preAuthDetailVO PreAuthDetailVO contains Pre-Authorization information
     * @param strSelectionType String object which contains Selection Type - 'PAT' or 'ICO'
     * @return Object[] the values,of  MEMBER_SEQ_ID , PAT_GEN_DETAIL_SEQ_ID and PAT_ENROLL_DETAIL_SEQ_ID
     * @exception throws TTKException
     */
    public Object[] savePreAuth(PreAuthDetailVO preAuthDetailVO,String strSelectionType) throws TTKException{
        preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
        return preAuthDAO.savePreAuth(preAuthDetailVO,strSelectionType);
    }//end of savePreAuth(PreAuthDetailVO preAuthDetailVO,String strSelectionType)

    /**
	 * This method will allow to Override the Preauth information
	 * @param lngPATGenDetailSeqID PATGenDetailSeqID
	 * @param lngPATEnrollDtlSeqID PATEnrollDtlSeqID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO overridePreauth(long lngPATGenDetailSeqID,long lngPATEnrollDtlSeqID,long lngUserSeqID) throws TTKException {
    	preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.overridePreauth(lngPATGenDetailSeqID,lngPATEnrollDtlSeqID,lngUserSeqID);
    }//end of overridePreauth(long lngPATGenDetailSeqID,long lngPATEnrollDtlSeqID,long lngUserSeqID)

    /**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @param strType String object which contains Type
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType) throws TTKException {
    	preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.saveReview(preauthDetailVO,strMode,strType);
    }//end of saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType)

    /**
     * This method returns the ArrayList object, which contains all the Users for the Corresponding TTK Branch
     * @param alAssignUserList ArrayList Object contains PreauthSeqID/ClaimSeqID,PolicySeqID and TTKBranch
     * @param strMode contains PAT/CLM for identifying the Pre-Authorization/Claims flow
     * @return ArrayList object which contains all the Users for the Corresponding TTK Branch
     * @exception throws TTKException
     */
    public ArrayList getAssignUserList(ArrayList alAssignUserList,String strMode) throws TTKException{
    	preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.getAssignUserList(alAssignUserList,strMode);
    }//end of getAssignUserList(ArrayList alAssignUserList,String strMode)

    /**
     * This method assigns the Pre-Authorization information to the corresponding Doctor
     * @param preAuthAssignmentVO PreAuthAssignmentVO which contains Pre-Authorization information to assign the corresponding Doctor
     * @return long value which contains Assign Users Seq ID
     * @exception throws TTKException
     */
    public long assignPreAuth(PreAuthAssignmentVO preAuthAssignmentVO) throws TTKException {
        preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
        return preAuthDAO.assignPreAuth(preAuthAssignmentVO);
    }//end of assignPreAuth(PreAuthAssignmentVO preAuthAssignmentVO)

    /**
     * This method returns the PreAuthAssignmentVO  which contains Assignment details
     * @param lngAssignUsersSeqID long value contains Assign User Seq ID
     * @param lngUserSeqID long value contains Logged-in User Seq ID
     * @param strMode contains PAT/CLM for identifying Pre-Authorization/Claims
     * @return ArrayList of PreAuthAssignmentVO object which contains Assignment details
     * @exception throws TTKException
     */
    public PreAuthAssignmentVO getAssignTo(long lngAssignUsersSeqID,long lngUserSeqID,String strMode) throws TTKException {
    	preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.getAssignTo(lngAssignUsersSeqID,lngUserSeqID,strMode);
    }//end of getAssignTo(long lngAssignUsersSeqID,long lngUserSeqID,String strMode)

    /**
     * This method returns the Arraylist of ClaimantVO's  which contains Claimant details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimantVO object which contains Claimant details
     * @exception throws TTKException
     */
    public ArrayList getClaimantList(ArrayList alSearchCriteria) throws TTKException {
        preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
        return preAuthDAO.getClaimantList(alSearchCriteria);
    }//end of getClaimantList(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of EnhancementVO's  which contains SumInsured Enhancement details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of EnhancementVO object which contains SumInsured Enhancement details
     * @exception throws TTKException
     */
    public ArrayList getSumInsuredEnhancementList(ArrayList alSearchCriteria) throws TTKException {
    	preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.getSumInsuredEnhancementList(alSearchCriteria);
    }//end of getSumInsuredEnhancementList(ArrayList alSearchCriteria)

    /**
     * This method returns the PreAuthMedicalVO, which contains all the Pre-Authorization Medical Details
     * @param lngPATGenDetailSeqID long value which contains pre-authseq id to get the Pre-Authorization Medical Details
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthMedicalVO object which contains all the Pre-Authorization Medical details
     * @exception throws TTKException
     */
    public PreAuthMedicalVO getMedicalDetail(long lngPATGenDetailSeqID,long lngUserSeqID) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
        return preAuthMedicalDAO.getMedicalDetail(lngPATGenDetailSeqID,lngUserSeqID);
     }//end of getMedicalDetail(long lngPATGenDetailSeqID,long lngUserSeqID)

    /**
     * This method returns the PreAuthMedicalVO, which contains all the Claims Medical Details
     * @param lngClaimSeqID long value which contains claimseq id to get the Claims Medical Details
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthMedicalVO object which contains all the Claims Medical details
     * @exception throws TTKException
     */
    public PreAuthMedicalVO getClaimMedicalDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException{
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.getClaimMedicalDetail(lngClaimSeqID,lngUserSeqID);
    }//end of getClaimMedicalDetail(long lngClaimSeqID,long lngUserSeqID)
    
    /**
     * This method returns the String Object, which contains Doctor Opinion
     * @param lngClaimSeqID long value which contains claimseq id to get the Doctor Opinion Information
     * @return String Object, which contains Doctor Opinion
     * @exception throws TTKException
     */
    public String getDoctorOpinion(long lngClaimSeqID) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.getDoctorOpinion(lngClaimSeqID);
    }//end of getDoctorOpinion(long lngClaimSeqID)
    
    /**
     * This method saves the Doctor Opinion Information
     * @param lngClaimSeqID long value which contains claimseq id to save the Doctor Opinion Information
     * @param strDoctorOpinion String Object which contains Doctor Opinion Information
     * @param lngUserSeqID long value which contains Logged-in User
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveDoctorOpinion(long lngClaimSeqID,String strDoctorOpinion,long lngUserSeqID) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.saveDoctorOpinion(lngClaimSeqID,strDoctorOpinion,lngUserSeqID);
    }//end of saveDoctorOpinion(long lngClaimSeqID,String strDoctorOpinion,long lngUserSeqID)

    /**
     * This method returns the AdditionalHospitalDetailVO, which contains all the Claim Hospital Additional Details
     * @param lngClaimHospAssocSeqID long value which contains claim hosp assoc seq id to get the Claim Hospital Additional Details
     * @param lngUserSeqID long value which contains Logged-in User
     * @return AdditionalHospitalDetailVO object which contains all the Claim Hospital Additional details
     * @exception throws TTKException
     */
    public AdditionalHospitalDetailVO getAdditionalHospitalDetail(long lngClaimHospAssocSeqID,long lngUserSeqID) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.getAdditionalHospitalDetail(lngClaimHospAssocSeqID,lngUserSeqID);
    }//end of getAdditionalHospitalDetail(long lngClaimHospAssocSeqID,long lngUserSeqID)

    /**
     * This method saves the Claim Hospital Additional information
     * @param addHospitalDetailVO AdditionalHospitalDetailVO contains Claim Hospital Additional
     * @return long value which contains Add Hospital Dtl Seq ID
     * @exception throws TTKException
     */
    public long saveAdditionalHospitalDetail(AdditionalHospitalDetailVO addHospitalDetailVO) throws TTKException{
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.saveAdditionalHospitalDetail(addHospitalDetailVO);
    }//end of saveAdditionalHospitalDetail(AdditionalHospitalDetailVO addHospitalDetailVO)

    //added for KOC-Decoupling
    /**
     * This method returns the ArrayList, which contains Diagnosis list Details
     * @param alDiagnosisList which contains PAT_GEN_DETAIL_SEQ_ID,CLAIM_SEQ_ID to get the Diagnosis Details
     * @return ArrayList object which contains Diagnosis Details
     * @exception throws TTKException
     */
    public ArrayList getDiagnosisList(ArrayList alDiagnosisList) throws TTKException
    {    	
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.getDiagnosisList(alDiagnosisList);
    }
    /**This Method returns PreAuthAilmentVO, which contains all the Ailment Details
     * @param lngDiagSeqId value which contains Diagnosis Seq Id
     * @exception throws TTKException
     */
    public PreAuthAilmentVO getDiagnosisDetail(long lngDiagSeqId) throws TTKException
    {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.getDiagnosisDetail(lngDiagSeqId);    	
    }
    
    /**
     * This method returns the PreAuthMedicalVO, which contains all the Pre-Authorization Ailment Details
     * @param lngSeqID long value which contains pre-authseq id/ClaimSEQID to get the Pre-Authorization/Claims Ailment Details
     * @param lngUserSeqID long value which contains Logged-in User seq id
     * @param strMode String Object contains mode for identifying Pre-Authorization/Claims - PAT/CLM
     * @return PreAuthAilmentVO object which contains all the Pre-Authorization/Claims Ailment and Associated Illness details
     * @exception throws TTKException
     */
    public PreAuthAilmentVO getAilmentDetail(long lngSeqID,long lngUserSeqID,String strMode) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
        return preAuthMedicalDAO.getAilmentDetail(lngSeqID,lngUserSeqID,strMode);
     }//end of getAilmentDetail(long lngSeqID,long lngUserSeqID,String strMode)

    /**
     * This method saves the Pre-Authorization Ailment information
     * @param preAuthAilmentVO PreAuthAilmentVO contains Pre-Authorization Ailment information and Associated Illness information
     * @return long value which contains Ailment Seq ID
     * @exception throws TTKException
     */
    public long saveAilment(PreAuthAilmentVO preAuthAilmentVO) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
        return preAuthMedicalDAO.saveAilment(preAuthAilmentVO);
    }//end of saveAilment(PreAuthAilmentVO preAuthAilmentVO)

    
    /**Added for KOC-Decoupling
     * This method saves the diagnosis Details
     * @param preAuthAilmentVo PreAuthAilmentVO contains Ailment information
     * @return long value which contains Diagnosis Seq ID
     * @Exception throws TTKException
     * 
     */
    public long saveDiagnosis(PreAuthAilmentVO preAuthAilmentVO)throws TTKException
    {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.saveDiagnosis(preAuthAilmentVO);
    }
    
    
    /**
     * This method saves the Promote Status Information for Pre-Authorization Ailment information
     * @param preAuthAilmentVO PreAuthAilmentVO contains Pre-Authorization Ailment information and Associated Illness information
     * @return long value which contains rows Processed
     * @exception throws TTKException
     */
    public long saveDataEntryPromote(PreAuthAilmentVO preAuthAilmentVO) throws TTKException
    {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
        return preAuthMedicalDAO.saveDataEntryPromote(preAuthAilmentVO);

    }
    
    /**
     * This method saves the Revert Status Information for Pre-Authorization Ailment information
     * @param preAuthAilmentVO PreAuthAilmentVO contains Pre-Authorization Ailment information and Associated Illness information
     * @return long value which contains rows Processed
     * @exception throws TTKException
     */
    public long saveDataEntryRevert(PreAuthAilmentVO preAuthAilmentVO) throws TTKException
    {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
        return preAuthMedicalDAO.saveDataEntryRevert(preAuthAilmentVO);

    }
    
    /**
     * This method returns the ICDPCSVO, which contains all the ICD Package Details
     * @param lngIcdPcsSeqID long value which contains seq id to get the ICD/PCS Details
     * @return ICDPCSVO object which contains all the ICD/PCS Details
     * @exception throws TTKException
     */
    public ICDPCSVO getICDPCSDetail(long lngIcdPcsSeqID,long lngUserSeqID) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
        return preAuthMedicalDAO.getICDPCSDetail(lngIcdPcsSeqID,lngUserSeqID);
    }//end of getICDPCSDetail(long lngPEDSeqID,long lngUserSeqID)

    /**
     * This method saves the Pre-Authorization ICD Package information
     * @param icdPCSVO ICDPCSVO contains ICD Package information
     * @param strMode String contains Mode for Pre-Authorization/Claims as - PAT / CLM
     * @return long value which contains ICDPCS Seq ID
     * @exception throws TTKException
     */
    public long saveICDPackage(ICDPCSVO icdPCSVO,String strMode) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
        return preAuthMedicalDAO.saveICDPackage(icdPCSVO,strMode);
    }//end of saveICDPackage(ICDPCSVO icdPCSVO,String strMode)

    /**
     * This method deletes the ICD Package information from the database
     * @param alICDPackage arraylist which the the details of the ICD Package has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteICDPackage(ArrayList alICDPackage) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
        return preAuthMedicalDAO.deleteICDPackage(alICDPackage);
    }//end of deleteICDPackage(ArrayList alICDPackage)

    /**added for KOC-Decoupling
     * This method deletes the selected diagnosis information from the database  
     * @param DiagnosisSeqId which the details of the diagnosis to be deleted
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
    public int deleteDiagnosis(long deleteSeqId)throws TTKException
    {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.deleteDiagnosis(deleteSeqId);
    }  
    
    /**
     * This method returns the AuthorizationVO, which contains Authorization Details
     * @param alAuthList ArrayList object which contains MemberSeqID,PAT_GEN_DETAIL_SEQ_ID,PAT_ENROLL_DETAIL_SEQ_ID and USER_SEQ_ID to get the Authorization Details
     * @param strIdentifier String which contains module identifer Pre-Authorization/Claims
     * @return AuthorizationVO object which contains Authorization Details
     * @exception throws TTKException
     */
    public AuthorizationVO getAuthorizationDetail(ArrayList alAuthList,String strIdentifier) throws TTKException {
         preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
         return preAuthDAO.getAuthorizationDetail(alAuthList,strIdentifier);
     }//end of getAuthorizationDetail(ArrayList alAuthList)

    /**
     * This method saves the Authorization information
     * @param authorizationVO AuthorizationVO contains Authorization information
     * @param strIdentifier Pre-Authorization/Claims
     * @return long int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveAuthorization(AuthorizationVO authorizationVO,String strIdentifier) throws TTKException {
        preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
        return preAuthDAO.saveAuthorization(authorizationVO,strIdentifier);
    }//end of saveAuthorization(AuthorizationVO authorizationVO,String strIdentifier)
  /**
   *bajaj
    * This method saves the intimation information
    * @param authorizationVO AuthorizationVO contains Authorization information
    * @param strIdentifier Pre-Authorization/Claims
    * @return long int the value which returns greater than zero for succcesssful execution of the task
    * @exception throws TTKException
    */
   public int sendInsIntimate(AuthorizationVO authorizationVO,String strIdentifier) throws TTKException {
       preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
       return preAuthDAO.sendInsIntimate(authorizationVO,strIdentifier);
   }//end of saveAuthorization(AuthorizationVO authorizationVO,String strIdentifier)

    /**
     * This method returns the PreAuthTariffVO, which contains Tariff Details
     * @param alTariffList ArrayList contains PAT_GEN_DETAIL_SEQ_ID,Enroll Dtl Seq ID,logged-in User Seq ID and Policy Seq ID
     * @return PreAuthTariffVO object which contains Tariff Details
     * @exception throws TTKException
     */
    public PreAuthTariffVO getTariffDetail(ArrayList alTariffList) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.getTariffDetail(alTariffList);
    }//end of getTariffDetail(ArrayList alTariffList)

    /**
     * This method returns the PreAuthTariffVO, which contains Tariff Details
     * @param alTariffList ArrayList contains PAT_GEN_DETAIL_SEQ_ID,Enroll Dtl Seq ID,logged-in User Seq ID and Policy Seq ID
     * @return PreAuthTariffVO object which contains Tariff Details
     * @exception throws TTKException
     */
    public PreAuthTariffVO getPATariffDetail(ArrayList alTariffList) throws TTKException{
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.getPATariffDetail(alTariffList);
    }//end of getPATariffDetail(ArrayList alTariffList)

    /**
     * This method saves the Tariff information
     * @param preAuthTariffVO PreAuthTariffVO contains Tariff information
     * @return long value contains Tariff Header Seq ID
     * @exception throws TTKException
     */
    public long saveTariffDetail(PreAuthTariffVO preAuthTariffVO) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.saveTariffDetail(preAuthTariffVO);
    }//end of saveTariffDetail(PreAuthTariffVO preAuthTariffVO)

    /**
     * This method Calculates the Tariff information
     * @param lngPreauthSeqID contains PAT_GEN_DETAIL_SEQ_ID
     * @param lngUserSeqID long value which contains Logged-in User seq id
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int calculateTariff(long lngPreauthSeqID,long lngUserSeqID) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.calculateTariff(lngPreauthSeqID,lngUserSeqID);
    }//end of calculateTariff(long lngPreauthSeqID,long lngUserSeqID)

    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deletePATGeneral(ArrayList alDeleteList) throws TTKException {
		preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
		return preAuthDAO.deletePATGeneral(alDeleteList);
	}//end of deletePATGeneral(ArrayList alDeleteList)

	/**
     * This method returns the ArrayList, which contains Discrepancy Details
     * @param alDiscrepancyList lwhich contains PAT_GEN_DETAIL_SEQ_ID,CLAIM_SEQ_ID,USER_SEQ_ID to get the Discrepancy Details
     * @return ArrayList object which contains Discrepancy Details
     * @exception throws TTKException
     */
	public ArrayList getDiscrepancyInfo(ArrayList alDiscrepancyList) throws TTKException{
		preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
		return preAuthDAO.getDiscrepancyInfo(alDiscrepancyList);
	}//end of getDiscrepancyInfo(ArrayList alDiscrepancyList)

	/**
     * This method resolves the Discrepancy information
     * @param alDiscrepancyVO ArrayList contains Discrepancy information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int resolveDiscrepancy(ArrayList alDiscrepancyVO) throws TTKException {
		preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
		return preAuthDAO.resolveDiscrepancy(alDiscrepancyVO);
	}//end of resolveDiscrepancy(ArrayList alDiscrepancyVO)
	
	/**
     * This method returns the ArrayList, which contains AccountHeads
     * @param lngPreauthSeqID which contains PAT_GEN_DETAIL_SEQ_ID to get the AccountHeads
     * @return ArrayList object which contains AccountHeads Details
     * @exception throws TTKException
     */
	public ArrayList getAccountHeadInfo(long lngPreauthSeqID) throws TTKException {
		preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
		return preAuthMedicalDAO.getAccountHeadInfo(lngPreauthSeqID);
	}//end of getAccountHeadInfo(long lngPreauthSeqID)
	
	/**
     * This method returns the HashMap, which contains AccountHeads
     * @param lngPreauthSeqID which contains PAT_GEN_DETAIL_SEQ_ID to get the AccountHeads
     * @return HashMap object which contains AccountHeads Details
     * @exception throws TTKException
     */
	public HashMap getAccountHeadInfoList(long lngPreauthSeqID) throws TTKException {
		preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
		return preAuthMedicalDAO.getAccountHeadInfoList(lngPreauthSeqID);
	}//end of getAccountHeadInfoList(long lngPreauthSeqID)
	
	/**
     * This method saves the Tariff information
     * @param lngPreauthSeqID contains PAT_GEN_DETAIL_SEQ_ID
     * @param strSelectedSeqID contains Concatenated Selected Seq ID's
     * @param lngUserSeqID long value which contains Logged-in User seq id
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveSelectedAccountHeads(long lngPreauthSeqID,String strSelectedSeqID,long lngUserSeqID) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.saveSelectedAccountHeads(lngPreauthSeqID,strSelectedSeqID,lngUserSeqID);
    }//end of saveSelectedAccountHeads(long lngPreauthSeqID,String strSelectedSeqID,long lngUserSeqID)
	
	/**
     * This method returns the PreAuthTariffVO, which contains Tariff Details
     * @param alTariffList ArrayList contains PAT_GEN_DETAIL_SEQ_ID,Enroll Dtl Seq ID,logged-in User Seq ID and Policy Seq ID
     * @return PreAuthTariffVO object which contains Tariff Details
     * @exception throws TTKException
     */
    public PreAuthTariffVO getAccountHeadTariffDetail(ArrayList alTariffList) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.getAccountHeadTariffDetail(alTariffList);
    }//end of getAccountHeadTariffDetail(ArrayList alTariffList)
    
    /**
     * This method saves the Tariff information
     * @param preAuthTariffVO PreAuthTariffVO contains Tariff information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveAccountHeadTariffDetail(PreAuthTariffVO preAuthTariffVO) throws TTKException {
    	preAuthMedicalDAO = (PreAuthMedicalDAOImpl)this.getPreAuthDAO("medical");
    	return preAuthMedicalDAO.saveAccountHeadTariffDetail(preAuthTariffVO);
    }//end of saveAccountHeadTariffDetail(PreAuthTariffVO preAuthTariffVO)
    
    /**
	 * This method will save the Review Information and Workflow will change to Coding
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO returnToCoding(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException {
    	preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.returnToCoding(preauthDetailVO,strMode);
    }//end of returnToCoding(PreAuthDetailVO preauthDetailVO,String strMode)
    
       
    /**
	 * This method reassign the enrollment ID
	 * @param alReAssignEnrID the arraylist of details for which have to be reassigned
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int reAssignEnrID(ArrayList alReAssignEnrID) throws TTKException 
	{
		preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.reAssignEnrID(alReAssignEnrID);
	}//end of reAssignEnrID(ArrayList alReAssignEnrID)
	
	/**
     * This method returns the Arraylist of ClaimantVO's  which contains Claimant details
     * @param lngMemSeqID String object which contains the search criteria
     * @return ArrayList of ClaimantVO object which contains Claimant details
     * @exception throws TTKException
     */
    public ClaimantVO getSelectEnrollmentID(Long lngMemSeqID) throws TTKException{
    	preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.getSelectEnrollmentID(lngMemSeqID);
    }//end of getSelectEnrollmentID(Long lngMemSeqID)
    
    /**
     * This method returns the Arraylist of ClaimantVO's  which contains Claimant details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimantVO object which contains Claimant details
     * @exception throws TTKException
     */
    public ArrayList getEnrollmentList(ArrayList alSearchCriteria) throws TTKException{
    	preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.getEnrollmentList(alSearchCriteria);
    }//end of getEnrollmentList(ArrayList alSearchCriteria) 
    
    /**
     * This method returns the SIInfoVO, which contains Balance SI Details.
     * @param lngMemSeqID long value which contains member id to get the Balance SI Details
     * @param lngPolicyGrpSeqID long value which contains Policy Group Seq ID
     * @param lngBalanceSeqID long value which contains Balance Seq ID
     * @return PreAuthMedicalVO object which contains Balance SI Details
     * @exception throws TTKException
     */
    public SIInfoVO getBalanceSIDetail(long lngMemSeqID,long lngPolicyGrpSeqID,long lngBalanceSeqID) throws TTKException {
    	preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
    	return preAuthDAO.getBalanceSIDetail(lngMemSeqID,lngPolicyGrpSeqID,lngBalanceSeqID);
    }//end of getBalanceSIDetail(long lngMemSeqID,long lngPolicyGrpSeqID,long lngBalanceSeqID)
	

    /**
     * This method returns the BalanceCopayDeductionVO, which contains Copay Adviced SI Details.
     * @param strIdentifier specifies module
     * @param lngPatGenDtlSeqID long value which contains Preauthorization general detail Seq id
     * @param lngMemSeqID long value which contains Member Seq ID
     * @param lngPolicyGrpSeqID long value which contains Policy Group SeQ Id
     * @param lngBalanceSeqID long value which contains BAlance Seq ID
     * @return PreAuthMedicalVO object which contains Balance SI Details
     * @exception throws TTKException
	 * //Changes As per KOC 1142(Copay restriction) ChangeRequest
     */
      public BalanceCopayDeductionVO getcopayAdviced(String strIdentifier,long lngPatGenDtlSeqID,long lngMemSeqID,long lngPolicyGrpSeqID,long lngBalanceSeqID) throws TTKException
      {
    	  preAuthDAO = (PreAuthDAOImpl)this.getPreAuthDAO("preauth");
      	return preAuthDAO.getcopayAdviced(strIdentifier,lngPatGenDtlSeqID,lngMemSeqID,lngPolicyGrpSeqID,lngBalanceSeqID);
      }
}//end of PreAuthManagerBean
