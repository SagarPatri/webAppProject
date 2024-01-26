/**
 * @ (#) RuleManager.java Jul 7, 2006
 * Project 	     : TTK HealthCare Services
 * File          : RuleManager.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 7, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.administration;

import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.ejb.Local;

import org.dom4j.Document;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ClauseDiseaseVO;
import com.ttk.dto.administration.HMOGlobalVO;
import com.ttk.dto.administration.HmoInPatientVO;
import com.ttk.dto.administration.HmoOutPatientVO;
import com.ttk.dto.administration.InvestigationRuleVO;
import com.ttk.dto.administration.LevelConfigurationVO;
import com.ttk.dto.administration.PolicyClauseVO;
import com.ttk.dto.administration.RuleVO;

@Local

public interface RuleManager {

	/**
     * This method returns the RuleVO, which contains all the Rule Data Details
     * @param lngProdPolicySeqID long value which contains Product Policy Seq ID to get the Rule Data Details
     * @return RuleVO object which contains all the Rule Data Details
     * @exception throws TTKException
     */
	public ArrayList getProductRuleList(long lngProdPolicySeqID) throws TTKException;

	/**
     * This method returns the RuleVO, which contains all the Rule Data details
     * @param lngSeqID long value which contains ProductPolicySeqID/RuleSeqID/Policy Seq ID to get the Rule Data Details
     * @param strFlag String object which contains Flag - P / R / I or C
     * @return RuleVO object which contains all the Rule Data details
     * @exception throws TTKException
     */
	public RuleVO getProdPolicyRule(long lngSeqID,String strFlag) throws TTKException;

	/**
     * This method saves the Rule Data information
     * @param ruleVO RuleVO contains Rule Data information
     * @param strFlag String object which contains Flag - P / R / I or C
     * @return long value contains Seq ID
     * @exception throws TTKException
     */
	public long saveProdPolicyRule(RuleVO ruleVO,String strFlag) throws TTKException;

//	 added by Unni for rule engine processing
	public Document initiateCheck(Document doc) throws TTKException;
	// end of addition
	
	/**
     * This method returns the RuleVO, which contains all the Rule Data details
     * @param lngSeqID long value which contains PAT_GEN_DETAIL_SEQ_ID/CLAIM_SEq_ID to get the Rule Data Details
     * @param strFlag String object which contains Flag - PI / PC
     * @return RuleVO object which contains all the Rule Data details
     * @exception throws TTKException
     */
	public RuleVO getPAClaimsRule(long lngSeqID,String strFlag) throws TTKException;
	
	/**
     * This method returns the RuleVO, which contains all the Rule Data details
     * @param strFlag String object which contains Flag - P / C
     * @param lngSeqID long value which contains PAT_GEN_DETAIL_SEQ_ID/CLAIM_SEq_ID to get the Rule Data Details
     * @return Document object which contains Rule Data XML
     * @exception throws TTKException
     */
	public Document processRule(String strFlag,long lngSeqID) throws TTKException;
	
	/**
     * This method returns the ArrayList, which contains all the Product/Policy Clauses details
     * @param lngProdPolicySeqID long value which contains Seq ID to get the Product/Policy Clauses Details
     * @param strIdentifier String value which contains identifier - Clause/Coverage for fetching the information
     * @return ArrayList object which contains all the Product/Policy Clauses details
     * @exception throws TTKException
     */
	public ArrayList getProdPolicyClause(long lngProdPolicySeqID,String strIdentifier) throws TTKException;
	
	
	//added for hyundai buffer
	/**
     * This method returns the ArrayList, which contains all the Product/Policy Clauses details
     * @param lngProdPolicySeqID long value which contains Seq ID to get the Product/Policy Clauses Details
     * @param strIdentifier String value which contains identifier - Clause/Coverage for fetching the information
     * @return ArrayList object which contains all the Product/Policy Clauses details
     * @exception throws TTKException
     */
	public ArrayList getPolicyLevelConfiguration(long lngPolicySeqID,long lngUserSeqId) throws TTKException;
	
	/**
     * This method saves the Product/Policy Clauses information
     * @param ruleVO RuleVO contains Product/Policy Clauses information
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
	public int saveProdPolicyClause(PolicyClauseVO policyClauseVO) throws TTKException;
	
	//added for hyundai buffer 
	/**
     * This method saves the Product/Policy Clauses information
     * @param ruleVO RuleVO contains Product/Policy Clauses information
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
	public ArrayList savePolicyLevelConfiguration(LevelConfigurationVO levelConfigurationVO) throws TTKException;
	
	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @exception throws TTKException
	 */
	
	public int deletePolicyLevelConfiguration(ArrayList alDeleteList) throws TTKException;
	//end for hyundai buffer 
	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteProdPolicyClause(ArrayList alDeleteList) throws TTKException;
	
	/**
     * This method returns the ArrayList, which contains all the Daycare procedures
     * @param strDaycareGroupId String Daycare group id
     * @return ArrayList object which contains daycare procedures belonging to this group.
     * @exception throws TTKException
     */
	public ArrayList getDayCareProcedureList(String strDaycareGroupId) throws TTKException;
	/**
     * This method returns the ArrayList, which contains the Validation Errors
     * @param lngErrorCode Long Error Code
     * @return ArrayList object which contains the Validation Errors.
     * @exception throws TTKException
     */
	public ArrayList getValidationErrorList(Long lngErrorCode) throws TTKException;
	
	/**
     * This method returns the ArrayList, which contains all the Disease Details
     * @param alSearchCriteria arraylist which contains  all the clause related details
     * @return ArrayList object which contains all the Disease Details
     * @exception throws TTKException
     */
	public ArrayList<ClauseDiseaseVO> getDiseaseList(ArrayList<Object> alSearchCriteria) throws TTKException;
	
	/**
     * This method is used to Associate/Disassociate a disease to a clause
     * @param alDiseaseList ArrayList which contains disease/clause related information
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
	public int associateDiseases(ArrayList<Object> alDiseaseList) throws TTKException;
	//added for KOC-1310
	/**
     * This method returns the ArrayList, which contains all the Cancer ICD CODES
     * @param strCancerGroupId String cancer ICD group id
     * @return ArrayList object which contains Cancer ICD codes belonging to this group.
     * @exception throws TTKException
     */
	public ArrayList getCancerICDList(String strCancerGroupId) throws TTKException;
	//ended
	

	/**
     * This method returns the ArrayList, which contains all the Cancer ICD CODES
     * @param strCancerGroupId String cancer ICD group id
     * @return ArrayList object which contains Cancer ICD codes belonging to this group.
     * @exception throws TTKException
     */
	public int saveBenefitRules(RuleVO ruleVO) throws TTKException;
	
	/**
     * This method returns the LinkedHashMap, which contains benefit type and desc
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public LinkedHashMap<String, String> getBenefitTypes() throws TTKException;
	
	/**
     * This method returns the LinkedHashMap, which contains benefit type and desc
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public LinkedHashMap<String,Long> getConfBenefitTypes(String strProPolRulSeqID) throws TTKException;
	/**
     * This method returns the Document, which contains benefit xml
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public Document getConfBenefitXml(Long strBenefitRulSeqID) throws TTKException;
	
	/**
     * This method remove the configured benefits, which contains benefit xml
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public int removeBenefitXml(RuleVO ruleVO) throws TTKException;
	
	/**
     * This method returns the int, which contains benefit xml updation
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public int saveAndCompleteRules(String proPolRulSeqID,Long userSeqID) throws TTKException;
	
	/**
     * This method returns the int, which contains benefit xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int overideBenefitRules(String proPolRulSeqID,Long userSeqID) throws TTKException;
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addConsdDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	
	
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getConsdDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;

	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getInvestdDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getPhysioDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	

	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList getEncounterTypes(String strBenID) throws TTKException;
	
// getEncounterTypesSPCB added by govind
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList getEncounterTypesSPCB(String spType) throws TTKException;
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList getAltTmtTypes() throws TTKException;

	/**
     * This method returns the delete status
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int deleteConsdtls(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the delete status
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public InvestigationRuleVO getConsDtls(InvestigationRuleVO investigationRuleVO) throws TTKException;

	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addInvestDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addPhysioDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getPharmaDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addPharmaDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the Document, which contains benefit xml
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public Reader getMohProCopayDetails(Long strBenefitRulSeqID) throws TTKException;
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addCompanDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	

	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getCompanDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;

	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addAmbulanceDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getAmbulanceDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;

	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public String getProductAuthority(Long proPolSeqID) throws TTKException;

	
	
	// development by govind for Special Benefits
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getVaccinDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	

	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addVaccinDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getAltTmtDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addAltTmtDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the delete status
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getDEOTDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addDEOTDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	public ArrayList<InvestigationRuleVO> getEyeCareDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	public int addEyeCareDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
public ArrayList<InvestigationRuleVO> getGyncolgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	public int addGyncolgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
public ArrayList<InvestigationRuleVO> getMinrSrgryDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	public int addMinrSrgryDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
public ArrayList<InvestigationRuleVO> getNaslCrctionDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	public int addNaslCrctionDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
public ArrayList<InvestigationRuleVO> getOnclgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	public int addOnclgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
public ArrayList<InvestigationRuleVO> getOgnTrnspltRcptDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	public int addOgnTrnspltRcptDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
public ArrayList<InvestigationRuleVO> getPsychiatricDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	public int addPsychiatricDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
public ArrayList<InvestigationRuleVO> getRnlDlsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	public int addRnlDlsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	
	
	public int deleteSBdtls(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	// new changes
	

	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getSpecialBenefitsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addSpecialBenefitsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException;
	
	
	
	// new changes end

	// development by govind for HMO TOB
	public ArrayList<HMOGlobalVO> getActivePolicies(String prodPolicyRuleSeqID) throws TTKException;
	
	
}//end of RuleManager
