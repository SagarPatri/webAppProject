/**
 * @ (#) RuleManagerBean.java Jul 7, 2006
 * Project 	     : TTK HealthCare Services
 * File          : RuleManagerBean.java
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

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import org.dom4j.Document;

import com.ttk.business.preauth.ruleengine.RuleEngine;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.administration.AdministrationDAOFactory;
import com.ttk.dao.impl.administration.RuleDAOImpl;
import com.ttk.dto.administration.ClauseDiseaseVO;
import com.ttk.dto.administration.HMOGlobalVO;
import com.ttk.dto.administration.HmoInPatientVO;
import com.ttk.dto.administration.HmoOutPatientVO;
import com.ttk.dto.administration.InvestigationRuleVO;
import com.ttk.dto.administration.LevelConfigurationVO;
import com.ttk.dto.administration.PolicyClauseVO;
import com.ttk.dto.administration.RuleVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class RuleManagerBean implements RuleManager{

	private AdministrationDAOFactory administrationDAOFactory = null;
	private RuleDAOImpl ruleDAO = null;

	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    private BaseDAO getRuleDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if (administrationDAOFactory == null)
            	administrationDAOFactory = new AdministrationDAOFactory();
            if(strIdentifier!=null)
            {
                return administrationDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else
                return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//End of getRuleDAO(String strIdentifier)

	/**
     * This method returns the RuleVO, which contains all the Rule Data Details
     * @param lngProdPolicySeqID long value which contains Product Policy Seq ID to get the Rule Data Details
     * @return RuleVO object which contains all the Rule Data Details
     * @exception throws TTKException
     */
	public ArrayList getProductRuleList(long lngProdPolicySeqID) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getProductRuleList(lngProdPolicySeqID);
	}//end of getProductRuleList(long lngProdPolicySeqID)

	/**
     * This method returns the RuleVO, which contains all the Rule Data details
     * @param lngSeqID long value which contains ProductPolicySeqID/RuleSeqID/Policy Seq ID to get the Rule Data Details
     * @param strFlag String object which contains Flag - P / R / I or C
     * @return RuleVO object which contains all the Rule Data details
     * @exception throws TTKException
     */
	public RuleVO getProdPolicyRule(long lngSeqID,String strFlag) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getProdPolicyRule(lngSeqID,strFlag);
	}//end of getProdPolicyRule(long lngSeqID,String strFlag)

	/**
     * This method saves the Rule Data information
     * @param ruleVO RuleVO contains Rule Data information
     * @param strFlag String object which contains Flag - P / R / I or C
     * @return long value contains Seq ID
     * @exception throws TTKException
     */
	public long saveProdPolicyRule(RuleVO ruleVO,String strFlag) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.saveProdPolicyRule(ruleVO,strFlag);
	}//end of saveProdPolicyRule(RuleVO ruleVO,String strFlag)

	  public Document initiateCheck(Document doc) throws TTKException
	   {
		   RuleEngine ruleEngine = new RuleEngine();
		   return ruleEngine.applyClause(doc);
	   }

	/**
	 * This method returns the RuleVO, which contains all the Rule Data details
	 * @param lngSeqID long value which contains PAT_GEN_DETAIL_SEQ_ID/CLAIM_SEq_ID to get the Rule Data Details
	 * @param strFlag String object which contains Flag - PI / PC
	 * @return RuleVO object which contains all the Rule Data details
	 * @exception throws TTKException
	 */
	public RuleVO getPAClaimsRule(long lngSeqID,String strFlag) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getPAClaimsRule(lngSeqID,strFlag);
	}//end of getPAClaimsRule(long lngSeqID,String strFlag)

	/**
     * This method returns the RuleVO, which contains all the Rule Data details
     * @param strFlag String object which contains Flag - P / C
     * @param lngSeqID long value which contains PAT_GEN_DETAIL_SEQ_ID/CLAIM_SEq_ID to get the Rule Data Details
     * @return Document object which contains Rule Data XML
     * @exception throws TTKException
     */
	public Document processRule(String strFlag,long lngSeqID) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		Document doc=ruleDAO.processRule(strFlag,lngSeqID);
		Document processedDoc=null;
		if(doc !=null)
		{
			RuleEngine ruleEngine = new RuleEngine();
			processedDoc=ruleEngine.applyClause(doc);
		}
		return processedDoc;
	}//end of processRule(String strFlag,long lngSeqID)
	
	/**
     * This method returns the ArrayList, which contains all the Product/Policy Clauses details
     * @param lngProdPolicySeqID long value which contains Seq ID to get the Product/Policy Clauses Details
     * @param strIdentifier String value which contains identifier - Clause/Coverage for fetching the information
     * @return ArrayList object which contains all the Product/Policy Clauses details
     * @exception throws TTKException
     */
	public ArrayList getProdPolicyClause(long lngProdPolicySeqID,String strIdentifier) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getProdPolicyClause(lngProdPolicySeqID,strIdentifier);
	}//end of getProdPolicyClause(long lngProdPolicySeqID,String strIdentifier)
	
	/**
     * This method saves the Product/Policy Clauses information
     * @param ruleVO RuleVO contains Product/Policy Clauses information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int saveProdPolicyClause(PolicyClauseVO policyClauseVO) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.saveProdPolicyClause(policyClauseVO);
	}//end of saveProdPolicyClause(PolicyClauseVO policyClauseVO)
	
	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteProdPolicyClause(ArrayList alDeleteList) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.deleteProdPolicyClause(alDeleteList);
	}//end of deleteProdPolicyClause(ArrayList alDeleteList)
	
	
	//added for hyundai buffer
	
	/**
     * This method returns the ArrayList, which contains all the Product/Policy Clauses details
     * @param lngProdPolicySeqID long value which contains Seq ID to get the Product/Policy Clauses Details
     * @param strIdentifier String value which contains identifier - Clause/Coverage for fetching the information
     * @return ArrayList object which contains all the Product/Policy Clauses details
     * @exception throws TTKException
     */
	public ArrayList getPolicyLevelConfiguration(long lngPolicySeqID,long lngUserSeqId) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getPolicyLevelConfiguration(lngPolicySeqID,lngUserSeqId);
	}//end of getProdPolicyClause(long lngProdPolicySeqID,String strIdentifier)
	
	/**
     * This method saves the Product/Policy Clauses information
     * @param ruleVO RuleVO contains Product/Policy Clauses information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public ArrayList savePolicyLevelConfiguration(LevelConfigurationVO levelConfigurationVO) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.savePolicyLevelConfiguration(levelConfigurationVO);
	}//end of saveProdPolicyClause(PolicyClauseVO policyClauseVO)
	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deletePolicyLevelConfiguration(ArrayList alDeleteList) throws TTKException {
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.deletePolicyLevelConfiguration(alDeleteList);
	}//end of deleteProdPolicyClause(ArrayList alDeleteList)
	
	/**
     * This method returns the ArrayList, which contains the Daycare procedures belonging to given group
     * @param strDaycareGroupId String Daycare group id
     * @return ArrayList object which contains daycare procedures belonging to this group.
     * @exception throws TTKException
     */
	public ArrayList getDayCareProcedureList(String strDaycareGroupId) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getDayCareProcedureList(strDaycareGroupId);
	}//end of getDayCareProcedureList(String strDaycareGroupId)
	
	//added for KOC-1310
	/**
     * This method returns the ArrayList, which contains all the Cancer ICD CODES
     * @param strCancerGroupId String cancer ICD group id
     * @return ArrayList object which contains Cancer ICD codes belonging to this group.
     * @exception throws TTKException
     */
	public ArrayList getCancerICDList(String strCancerGroupId) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getCancerICDList(strCancerGroupId);		
	}//ended
	
	/**
     * This method returns the ArrayList, which contains the Validation Errors
     * @param lngErrorCode Long Error Code
     * @return ArrayList object which contains the Validation Errors.
     * @exception throws TTKException
     */
	public ArrayList getValidationErrorList(Long lngErrorCode) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getValidationErrorList(lngErrorCode);
	}//end of getValidationErrorList(Long lngErrorCode)
	
	/**
     * This method returns the ArrayList, which contains all the Disease Details
     * @param alSearchCriteria arraylist which contains  all the clause related details
     * @return ArrayList object which contains all the Disease Details
     * @exception throws TTKException
     */
	public ArrayList<ClauseDiseaseVO> getDiseaseList(ArrayList<Object> alSearchCriteria) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getDiseaseList(alSearchCriteria);
	}//end of getDiseaseList(ArrayList alDiseaseList)
	
	/**
     * This method is used to Associate/Disassociate a disease to a clause
     * @param alDiseaseList ArrayList which contains disease/clause related information
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
	public int associateDiseases(ArrayList<Object> alDiseaseList) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.associateDiseases(alDiseaseList);
	}//end of associateDiseases(ArrayList alDiseaseList)
	
	/**
     * This method is used to Associate/Disassociate a disease to a clause
     * @param alDiseaseList ArrayList which contains disease/clause related information
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
	public int saveBenefitRules(RuleVO ruleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.saveBenefitRules(ruleVO);
	}
	/**
     * This method returns the HashMap, which contains benefit type and desc
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public LinkedHashMap<String, String> getBenefitTypes() throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getBenefitTypes();
	}
	
	/**
     * This method returns the HashMap, which contains benefit type and desc
     * @return LinkedHashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public LinkedHashMap<String,Long> getConfBenefitTypes(String strProPolRuleSeqID) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getConfBenefitTypes(strProPolRuleSeqID);
	}
	/**
     * This method returns the Document, which contains benefit xml
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public Document getConfBenefitXml(Long strBenefitRulSeqID) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getConfBenefitXml(strBenefitRulSeqID);
	}
	
	/**
     * This method remove the configured benefits, which contains benefit xml
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public int removeBenefitXml(RuleVO ruleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.removeBenefitXml(ruleVO);
	}
	
	/**
     * This method returns the int, which contains benefit xml updation
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public int saveAndCompleteRules(String proPolRulSeqID,Long userSeqID) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.saveAndCompleteRules(proPolRulSeqID,userSeqID);
	}
	
	public int overideBenefitRules(String proPolRulSeqID,Long userSeqID) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.overideBenefitRules(proPolRulSeqID,userSeqID);
	}
	
	/**
     * This method returns the int, which contains investigationRuleVO  updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addConsdDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addConsdDetails(investigationRuleVO);
	}

	public ArrayList<InvestigationRuleVO> getConsdDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getConsdDetails(investigationRuleVO);
	}
	
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getInvestdDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getInvestdDetails(investigationRuleVO);
	
	}
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getPhysioDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getPhysioDetails(investigationRuleVO);
	}
	public ArrayList getEncounterTypes(String strBenID) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getEncounterTypes(strBenID);
	}

	public int deleteConsdtls(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.deleteConsdtls(investigationRuleVO);
	}
	

	
	
	/**
     * This method returns the delete status
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public InvestigationRuleVO getConsDtls(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getConsDtls(investigationRuleVO);
	
	}
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addInvestDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addInvestDetails(investigationRuleVO);
	
	}
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addPhysioDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addPhysioDetails(investigationRuleVO);
	}
	
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getPharmaDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getPharmaDetails(investigationRuleVO);
	}
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addPharmaDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addPharmaDetails(investigationRuleVO);
	}
	
	/**
     * This method returns the Document, which contains benefit xml
     * @return HashMap object which contains benefit type and desc
     * @exception throws TTKException
     */
	public Reader getMohProCopayDetails(Long strBenefitRulSeqID) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getMohProCopayDetails(strBenefitRulSeqID);
	}
	
	
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addCompanDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addCompanDetails(investigationRuleVO);
	}
	
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getCompanDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getCompanDetails(investigationRuleVO);
	}
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addAmbulanceDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addAmbulanceDetails(investigationRuleVO);
	}
	/**
     * This method returns the AL InvestigationRuleVO which contains investigationRuleVO
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public ArrayList<InvestigationRuleVO> getAmbulanceDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getAmbulanceDetails(investigationRuleVO);
	}
	/**
     * This method returns the int, which contains investigationRuleVO xml updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public String getProductAuthority(Long proPolSeqID) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getProductAuthority(proPolSeqID);
	}
	
	// development by govind for Special Benefits
	public int addVaccinDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addVaccinDetails(investigationRuleVO);
	}
	
	
	
	public ArrayList<InvestigationRuleVO> getVaccinDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getVaccinDetails(investigationRuleVO);
	}
	
	public ArrayList<InvestigationRuleVO> getAltTmtDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getAltTmtDetails(investigationRuleVO);
	}
	
	/**
     * This method returns the int, which contains investigationRuleVO  updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addAltTmtDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addAltTmtDetails(investigationRuleVO);
	}
	
	
	public ArrayList<InvestigationRuleVO> getDEOTDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getDEOTDetails(investigationRuleVO);
	}
	
	public int addDEOTDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addDEOTDetails(investigationRuleVO);
	}
	
//	new
	
	public ArrayList<InvestigationRuleVO> getEyeCareDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getEyeCareDetails(investigationRuleVO);
	}
	
	public int addEyeCareDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addEyeCareDetails(investigationRuleVO);
	}
	
	public ArrayList<InvestigationRuleVO> getGyncolgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getGyncolgyDetails(investigationRuleVO);
	}
	
	public int addGyncolgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addGyncolgyDetails(investigationRuleVO);
	}
	
	public ArrayList<InvestigationRuleVO> getMinrSrgryDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getMinrSrgryDetails(investigationRuleVO);
	}
	
	public int addMinrSrgryDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addMinrSrgryDetails(investigationRuleVO);
	}
	
	public ArrayList<InvestigationRuleVO> getNaslCrctionDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getNaslCrctionDetails(investigationRuleVO);
	}
	
	public int addNaslCrctionDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addNaslCrctionDetails(investigationRuleVO);
	}
	
	public ArrayList<InvestigationRuleVO> getOnclgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getOnclgyDetails(investigationRuleVO);
	}
	
	public int addOnclgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addOnclgyDetails(investigationRuleVO);
	}
	
	public ArrayList<InvestigationRuleVO> getOgnTrnspltRcptDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getOgnTrnspltRcptDetails(investigationRuleVO);
	}
	
	public int addOgnTrnspltRcptDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addOgnTrnspltRcptDetails(investigationRuleVO);
	}
	
	public ArrayList<InvestigationRuleVO> getPsychiatricDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getPsychiatricDetails(investigationRuleVO);
	}
	
	public int addPsychiatricDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addPsychiatricDetails(investigationRuleVO);
	}
	
	public ArrayList<InvestigationRuleVO> getRnlDlsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getRnlDlsDetails(investigationRuleVO);
	}
	
	public int addRnlDlsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addRnlDlsDetails(investigationRuleVO);
	}
	
	// new
	
	public int deleteSBdtls(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.deleteSBdtls(investigationRuleVO);
	}
	
	
	

	public ArrayList getEncounterTypesSPCB(String spType) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getEncounterTypesSPCB(spType);
	}
	
	public ArrayList getAltTmtTypes() throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getAltTmtTypes();
	}
	
	// new changes start
	
	public ArrayList<InvestigationRuleVO> getSpecialBenefitsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getSpecialBenefitsDetails(investigationRuleVO);
	}
	
	/**
     * This method returns the int, which contains investigationRuleVO  updation
     * @return int  which contains benefit type and desc
     * @exception throws TTKException
     */
	public int addSpecialBenefitsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.addSpecialBenefitsDetails(investigationRuleVO);
	}
	
	
	// new changes end
	
	// development by govind for Special Benefits 

	
	public ArrayList<HMOGlobalVO> getActivePolicies(String prodPolicyRuleSeqID) throws TTKException{

		ruleDAO = (RuleDAOImpl)this.getRuleDAO("prodpolicyrule");
		return ruleDAO.getActivePolicies(prodPolicyRuleSeqID);
	}
}//end of RuleManagerBean
