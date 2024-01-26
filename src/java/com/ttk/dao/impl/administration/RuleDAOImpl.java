/**
 * @ (#) RuleDAOImpl.java Jul 7, 2006
 * Project 	     : TTK HealthCare Services
 * File          : RuleDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 7, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.administration;

import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.RuleXmlUtility;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.ClauseDiseaseVO;
import com.ttk.dto.administration.ErrorLogVO;
import com.ttk.dto.administration.HMOGlobalVO;
import com.ttk.dto.administration.InvestigationRuleVO;
import com.ttk.dto.administration.LevelConfigurationVO;
import com.ttk.dto.administration.PolicyClauseVO;
import com.ttk.dto.administration.RuleVO;
import com.ttk.dto.common.CacheObject;

public class RuleDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(RuleDAOImpl.class);

	private static final String strGetProductRuleList = "{CALL DEFINE_RULE_PKG_PROD_POLICY_RULES_LIST(?,?)}";
	private static final String strGetProdPolicyRule = "{CALL DEFINE_RULE_PKG_SELECT_PROD_POLICY_RULES(?,?,?)}";
//modification as per KOC 1099	
    private static final String strSaveProdPolicyRule = "{CALL DEFINE_RULE_PKG_SAVE_PROD_POLICY_RULES(?,?,?,?,?,?,?)}";
	private static final String strGetPAClaimsRule = "{CALL DEFINE_RULE_PKG.SELECT_PA_CLAIMS_RULES(?,?,?)}";
	private static final String strProcessRule = "{CALL PROD_POLICY_RULES_PKG.PROCESS_RULE(?,?,?)}";
	private static final String strGetProdPolicyClause = "{CALL ADMINISTRATION_PKG.SELECT_PROD_POLICY_CLAUSES(?,?,?)}";
	
	//Modified according to Shortfall Cr 1179
	//private static final String strSaveProdPolicyClause = "{CALL ADMINISTRATION_PKG.SAVE_PROD_POLICY_CLAUSES(?,?,?,?,?,?,?)}";
	private static final String strSaveProdPolicyClause = "{CALL ADMINISTRATION_PKG.SAVE_PROD_POLICY_CLAUSES(?,?,?,?,?,?,?,?,?)}";
	//added for hyundai buffer
	private static final String strDeleteLevelConfiguration = "{CALL ADMINISTRATION_PKG_DEL_BUFFER_LEVEL_LIMITS(?,?,?,?,?)}";
	private static final String strGetLevelConfiguration = "{CALL ADMINISTRATION_PKG_SELECT_BUFF_LEVEL_LIMITS_LIST(?,?,?)}";
	private static final String strSaveLevelConfiguration = "{CALL ADMINISTRATION_PKG_SAVE_BUFFER_LEVEL_LIMITS(?,?,?,?,?,?,?,?,?)}";//added for hyundai buffer
	private static final String strDeleteProdPolicyClause = "{CALL ADMINISTRATION_PKG_DELETE_CLAUSES(?,?,?)}";
	private static final String strDAYCARE_PROCEDURES="SELECT A.PROC_SEQ_ID,A.PROC_CODE ||' | '||SUBSTR(A.SHORT_DESCRIPTION,0,50) AS DESCRIPTION FROM TPA_HOSP_PROCEDURE_CODE A JOIN TPA_DAY_CARE_PROCEDURE B ON (A.PROC_SEQ_ID=B.PROC_SEQ_ID) WHERE B.DAY_CARE_GROUP_ID=?";
	private static final String strGetValidationErrorList="{CALL VALIDATION_FIELD_DATA_PKG.GET_VALIDATION_ERRORS(?,?)}";
	private static final String strGetDiseaseList="{CALL ADMINISTRATION_PKG.GET_DISEASE_CODES(?,?,?,?,?,?)}";
	private static final String strAssociateDiseases="{CALL ADMINISTRATION_PKG.CLAUSE_DISEASE_BINDING(?,?,?,?,?)}";
	
	//ADDED FOR koc-1310
	private static final String strcancericdprocedures = "SELECT A.PED_CODE_ID,A.ICD_CODE ||' | '||SUBSTR(A.PED_DESCRIPTION,0,50) AS DESCRIPTION FROM TPA_PED_CODE A JOIN TPA_CANCER_ICD_CODE B ON (A.PED_CODE_ID=B.PED_CODE_ID) WHERE B.DAY_CARE_GROUP_ID=? ORDER BY A.ICD_CODE";
	
	private static final String  strBenefitTypes="SELECT B.BENEFIT_TYPE_ID,B.BENEFIT_DESCRIPTION FROM  APP.TPA_RULES_BENEFIT_MASTER B ORDER BY B.SORT_NO";
	private static final String  strConfBenefitTypes="SELECT BENEFIT_ID,BENEFIT_RULES_SEQ_ID FROM app.TPA_BENEFIT_RULES_XML WHERE PROD_POLICY_RULE_SEQ_ID=?";//"{CALL DEFINE_RULE_PKG.GET_HMO_BENEFIT_XML(?,?)}";
	private static final String  strsavebenefitrules="{CALL DEFINE_RULE_PKG_SAVE_HMO_BENEFIT_XML(?,?,?,?,?,?)}";
	private static final String  strGetConfBenefitXml="SELECT BENEFIT_XML FROM app.TPA_BENEFIT_RULES_XML WHERE BENEFIT_RULES_SEQ_ID=?";
	private static final String  strgetprocopayconfxml="SELECT BENEFIT_COVE_AREA_XML FROM app.TPA_BENEFIT_RULES_XML WHERE BENEFIT_RULES_SEQ_ID=?";
	private static final String  strRemoveConfBenefitXml="{CALL DEFINE_RULE_PKG_REMOVE_HMO_RULES(?,?,?)}";//"DELETE FROM TPA_BENEFIT_RULES_XML WHERE BENEFIT_RULES_SEQ_ID=?";
	private static final String  strGetAllConfBenefitXml="SELECT BENEFIT_ID,BENEFIT_XML FROM app.TPA_BENEFIT_RULES_XML WHERE PROD_POLICY_RULE_SEQ_ID=?";
	
	private static final String  strSaveConditionRules="{CALL DEFINE_RULE_PKG_SAVE_HMO_RULES_XML(?,?,?,?,?)}";
	private static final String  strSetRuleStatus="UPDATE  app.TPA_INS_PROD_POLICY_RULES SET PROD_POLICY_RULE=? where PROD_POLICY_RULE_SEQ_ID =?";
	private static final String  strDeleteBenefitRules="DELETE FROM app.TPA_HMO_PROD_POLICY_RULES  WHERE PROD_POLICY_RULE_SEQ_ID=?";
	private static final String  strGetBenefitRuleSeqID="SELECT PROD_POLICY_RULE_SEQ_ID FROM app.TPA_INS_PROD_POLICY_RULES  WHERE PROD_POLICY_SEQ_ID=?";
	
	private static final String  strSaveConsltSPCB="{CALL PROD_POLICY_RULES_EXTRACT_PKG_SAVE_HMO_COVECOND(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; // added by govind
	private static final String  strSaveConslt="{CALL PROD_POLICY_RULES_EXTRACT_PKG_SAVE_HMO_COVECOND(?,?,?,?,?,?,?,?,?,?,?,?,?)}";  
	private static final String  strSaveCompanDtls="{CALL PROD_POLICY_RULES_EXTRACT_PKG_SAVE_HMO_COVECOND(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strEncounterTypes = "{CALL AUTHORIZATION_PKG_SELECT_ENCOUNTER_TYPES(?,?,?)}";
	// strEncounterTypesSPCB added by govind
	private static final String strEncounterTypesSPCB = "{CALL HMO_RULE_FIELD_PKG_GET_SPECIAL_BENEFIT_LIST(?,?)}";//"select jk.encounter_seq_id,(case when jk.benefit_gen_type_id='OPTS' then 'Out-Patinet' when jk.benefit_gen_type_id='OPTS' then 'Out-Patinet' when jk.benefit_gen_type_id='OPTS' then 'Out-Patinet' end)||' '|| jk.description as benefit from app.tpa_encounter_type_codes  jk where jk.header_type='ENCOUNTER_TYPE' and jk.encounter_seq_id in (1,2,3,4,5,6)";
	
	private static final String strGetConsltDetails = "SELECT C.* FROM app.HMO_COVE_CONDITIONS C WHERE C.PROD_POLICY_RULE_SEQ_ID=?:: numeric AND C.TYPE_GEN_ID=? AND C.BENEFIT_ID=?";
	
	private static final String strDeleteConsltDetails = "DELETE FROM APP.HMO_COVE_CONDITIONS  WHERE COVE_COND_SEQ_ID=?";
	
	private static final String strGetConfConsltDetails = "SELECT C.* FROM app.HMO_COVE_CONDITIONS C WHERE C.COVE_COND_SEQ_ID=?";

	private static final String strGetProAuthorityName = "select COALESCE(b.authority_type,'DHA') authority_type from app.tpa_ins_prod_policy a join app.tpa_ins_product b on (a.product_seq_id = b.product_seq_id) WHERE a.prod_policy_seq_id = ?";
	
	// added by govind for HMO TOB Cr to get active policies
	
	private static final String strGetActivePolicy ="select pr.prod_policy_rule_seq_id,ep.policy_seq_id,ep.policy_number,i.ins_comp_name, ep.classification "
			+ "policy_catagory,gr.group_id||'-'||lpad(coalesce(ep.sub_group_id,'000') :: varchar,3,'0')group_subgroup_id, gr.group_name,p.product_name,gc.description as Product_Network,ct.description as capitation_yn,"
			+ " ep.policy_status_general_type_id,tepm.total_sum_insured from app.tpa_enr_policy ep join  (select max(tepm.mem_tot_sum_insured) as total_sum_insured,tepg.policy_seq_id "
			+ "from app.tpa_enr_policy_member tepm join app.tpa_enr_policy_group tepg on tepm.policy_group_seq_id = tepg.policy_group_seq_id group by tepg.policy_seq_id) tepm"
			+ "  on ep.policy_seq_id = tepm.policy_seq_id join app.tpa_ins_info i on i.ins_seq_id = ep.ins_seq_id join app.tpa_ins_product p on (p.product_seq_id = ep.product_seq_id) "
			+ "join app.tpa_group_registration gr on gr.group_reg_seq_id = ep.group_reg_seq_id join app.tpa_ins_prod_policy pp on (ep.policy_seq_id = pp.policy_seq_id) join app.tpa_ins_prod_policy_rules pr "
			+ "on (pp.prod_policy_seq_id = pr.prod_policy_seq_id) left join app.tpa_general_code gc on(gc.general_type_id=p.product_cat_type_id and gc.header_type='PROVIDER_NETWORK') left join app.tpa_capitation_type_codes ct"
			+ "  on(ct.general_type_id=ep.capitation_yn) where pr.prod_policy_rule_seq_id=?::NUMERIC  and  p.authority_type ='MOH'";
	
	
	/**
     * This method returns the RuleVO, which contains all the Rule Data Details
     * @param lngProdPolicySeqID long value which contains Product Policy Seq ID to get the Rule Data Details
     * @return RuleVO object which contains all the Rule Data Details
     * @exception throws TTKException
     */
	public ArrayList getProductRuleList(long lngProdPolicySeqID) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		RuleVO ruleVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetProductRuleList);
			cStmtObject.setLong(1,lngProdPolicySeqID);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					ruleVO = new RuleVO();

					if(rs.getString("PROD_POLICY_RULE_SEQ_ID") != null){
						ruleVO.setProdPolicyRuleSeqID(new Long(rs.getLong("PROD_POLICY_RULE_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_RULE_SEQ_ID") != null)

					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						ruleVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)

					if(rs.getString("VALID_FROM") != null){
						ruleVO.setFromDate(new Date(rs.getTimestamp("VALID_FROM").getTime()));
					}//end of if(rs.getString("VALID_FROM") != null)

					if(rs.getString("VALID_TO") != null){
						ruleVO.setEndDate(new Date(rs.getTimestamp("VALID_TO").getTime()));
					}//end of if(rs.getString("VALID_FROM") != null)
					if(rs.getString("BNF_RULE_COMPT_YN") != null){
						ruleVO.setBenefitRuleCompletedYN(rs.getString("BNF_RULE_COMPT_YN"));
					}//end of if(rs.getString("BNF_RULE_COMPT_YN") != null)
			         
					conn.commit();
			 		alResultList.add(ruleVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getProductRuleList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getProductRuleList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getProductRuleList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getProductRuleList(long lngProdPolicySeqID)

	/**
     * This method returns the RuleVO, which contains all the Rule Data details
     * @param lngSeqID long value which contains ProductPolicySeqID/RuleSeqID/Policy Seq ID to get the Rule Data Details
     * @param strFlag String object which contains Flag - P / R / I or C
     * @return RuleVO object which contains all the Rule Data details
     * @exception throws TTKException
     */
	public RuleVO getProdPolicyRule(long lngSeqID,String strFlag) throws TTKException {
		Document doc = null;
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        RuleVO ruleVO = null;
        try{
        	conn = ResourceManager.getConnection();
			stmt = conn.prepareCall(strGetProdPolicyRule);
			stmt.setLong(1,lngSeqID);
			stmt.setString(2,strFlag);
			stmt.registerOutParameter(3,Types.OTHER);
			stmt.execute();
			rs =  (ResultSet) stmt.getObject(3);
			if(rs != null){
				while(rs.next()){
					ruleVO = new RuleVO();
					
		

					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						ruleVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)

					if(rs.getString("PROD_POLICY_RULE_SEQ_ID") != null){
						ruleVO.setProdPolicyRuleSeqID(new Long(rs.getLong("PROD_POLICY_RULE_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_RULE_SEQ_ID") != null)

					if(rs.getSQLXML("PROD_POLICY_RULE") != null) {
                        SAXReader saxReader=new SAXReader();
                        ruleVO.setRuleDocument(saxReader.read(rs.getSQLXML("PROD_POLICY_RULE").getCharacterStream()));
				    }// End of if(rs.getOPAQUE("PROD_POLICY_RULE")
					
					if(rs.getSQLXML("PROV_COPAY_RULES") != null) {
						 
                        ruleVO.setProviderCopayRulesReader(rs.getSQLXML("PROV_COPAY_RULES").getCharacterStream());
				    }// End of if(rs.getOPAQUE("PROV_COPAY_RULES")

					if(rs.getString("VALID_FROM") != null){
						ruleVO.setFromDate(new Date(rs.getTimestamp("VALID_FROM").getTime()));
					}//end of if(rs.getString("VALID_FROM") != null)

					if(rs.getString("VALID_TO") != null){
						ruleVO.setEndDate(new Date(rs.getTimestamp("VALID_TO").getTime()));
					}//end of if(rs.getString("VALID_FROM") != null)
					
					if(rs.getString("BNF_RULE_COMPT_YN") != null){
						ruleVO.setBenefitRuleCompletedYN(rs.getString("BNF_RULE_COMPT_YN"));
					}//end of if(rs.getString("BNF_RULE_COMPT_YN") != null)
					if(rs.getString("AUTHORITY_TYPE") != null){
						ruleVO.setProPolAuthority(rs.getString("AUTHORITY_TYPE"));
					}//end of if(rs.getString("BNF_RULE_COMPT_YN") != null)
				}//end of while(rs.next())
			}//end of if(rs != null)
			conn.commit();
			return ruleVO;
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getProdPolicyRule()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getProdPolicyRule()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getProdPolicyRule()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getProdPolicyRule(long lngSeqID,String strFlag)

	/**
     * This method saves the Rule Data information
     * @param ruleVO RuleVO contains Rule Data information
     * @param strFlag String object which contains Flag - P / R / I or C
     * @return long value contains Seq ID
     * @exception throws TTKException
     */
	public long saveProdPolicyRule(RuleVO ruleVO,String strFlag) throws TTKException {
	
      
    	Connection conn = null;
         /** TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
	        DateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:S");
			df.setTimeZone(tz); 
    	 * 
    	 */
    	TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
        DateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:S");

        df.setTimeZone(tz); 
		
		CallableStatement stmt = null;
		CallableStatement stmt1 = null;
		long lngSeqID = 0;
		try{
			conn = ResourceManager.getConnection();
    		stmt = conn.prepareCall(strSaveProdPolicyRule);
            if(ruleVO.getSeqID() != null){
    			stmt.setLong(1,ruleVO.getSeqID());//PROD_POLICY_RULE_SEQ_ID/PROD_POLICY_SEQ_ID/POLICY_SEQ_ID
    		}//end of if(ruleVO.getSeqID() != null)
    		else{
    			stmt.setLong(1,0l);
    		}//end of else

    		stmt.setString(2,strFlag);
    		SQLXML ruleXml=conn.createSQLXML();
    		if(ruleVO.getRuleDocument() != null){
    			
    			ruleXml.setString(ruleVO.getRuleDocument().asXML());
			}//end of if(ruleVO.getRuleDocument() != null)
    		else {
    			ruleXml.setString("<empty/>");
        		}
    		stmt.setSQLXML(3,ruleXml);

    		if(ruleVO.getFromDate() != null){
    			stmt.setTimestamp(4,new Timestamp(ruleVO.getFromDate().getTime()));
    		}//end of if(ruleVO.getFromDate() != null)
    		else{
    			stmt.setTimestamp(4,null);
    		}//end of else

    		stmt.setLong(5,ruleVO.getUpdatedBy());

    		if(ruleVO.getOverrideCompletedYN()!= null && ruleVO.getOverrideCompletedYN().equalsIgnoreCase("Y") ){
    			
    			stmt.setString(6,ruleVO.getOverrideCompletedYN());//OverrideCompletedYN Status Y OR N
    		}//end of if(ruleVO.getSeqID() != null)
    		else{
    			
    			stmt.setString(6,ruleVO.getOverrideCompletedYN());
    		}
    		SQLXML providerRuleXml=conn.createSQLXML();
    		if(ruleVO.getProviderCopayRulesWriter() != null){
    			providerRuleXml.setString(ruleVO.getProviderCopayRulesWriter() .toString());
    		}//end of if(ruleVO.getRuleDocument() != null)
    		else{
    			
    			providerRuleXml.setString("<ProviderRules></ProviderRules>");
    		}
    		stmt.setSQLXML(7,providerRuleXml);
    		
    		
    		stmt.registerOutParameter(1,Types.BIGINT);
    		stmt.execute();
    		lngSeqID = stmt.getLong(1); //claim_pkg.save_benefi_limits(
    		stmt1 = (java.sql.CallableStatement)conn.prepareCall("{CALL claim_pkg_save_benefi_limits(?,?)}");
    		if(ruleVO.getPolicySeqId()!=null)stmt1.setLong(1,ruleVO.getPolicySeqId());
    		else stmt1.setLong(1,0);
    		stmt1.registerOutParameter(2,Types.BIGINT);
    		stmt1.execute();
    		conn.commit();
    		
    	}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (stmt != null) stmt.close();
        			if (stmt1 != null) stmt1.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in RuleDAOImpl saveProdPolicyRule()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyrule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl saveProdPolicyRule()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyrule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyrule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		stmt1=null;
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngSeqID;
	}//end of saveProdPolicyRule(RuleVO ruleVO,String strFlag)

	/**
	 * This method returns the RuleVO, which contains all the Rule Data details
	 * @param lngSeqID long value which contains PAT_GEN_DETAIL_SEQ_ID/CLAIM_SEQ_ID to get the Rule Data Details
	 * @param strFlag String object which contains Flag - PR /CR
	 * @return RuleVO object which contains all the Rule Data details
	 * @exception throws TTKException
	 */
	public RuleVO getPAClaimsRule(long lngSeqID,String strFlag) throws TTKException {
		Document doc = null;
        XMLType xml = null;
        Connection conn = null;
        OracleCallableStatement stmt = null;
        OracleResultSet rs = null;
        RuleVO ruleVO = null;
TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
	        DateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:S");
			df.setTimeZone(tz);
        try{
        	conn = ResourceManager.getConnection();
log.info("Before executing RuleDAOImpl getPAClaimsRule Proc :"+ df.format(Calendar.getInstance(tz).getTime()));
			stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strGetPAClaimsRule);
log.info("After executing RuleDAOImpl getPAClaimsRule Proc :"+ df.format(Calendar.getInstance(tz).getTime()));			
stmt.setLong(1,lngSeqID);
			stmt.setString(2,strFlag);
			stmt.registerOutParameter(3,OracleTypes.CURSOR);
			stmt.execute();
			rs = (OracleResultSet)stmt.getObject(3);
			if(rs != null){
				while(rs.next()){
					ruleVO = new RuleVO();

					if(rs.getString("RULE_DATA_SEQ_ID") != null){
						ruleVO.setRuleDataSeqID(new Long(rs.getLong("RULE_DATA_SEQ_ID")));
					}//end of if(rs.getString("RULE_DATA_SEQ_ID") != null)
					if(rs.getString("RULE_EXECUTION_FLAG")!=null)
                    {
                        ruleVO.setRuleExecutionFlag(new Integer(rs.getInt("RULE_EXECUTION_FLAG")));
                    }//end of if(rs.getString("RULE_EXECUTION_FLAG")!=null)
					if(rs.getOPAQUE("RULE") != null) {
                        xml = XMLType.createXML(rs.getOPAQUE("RULE"));
                        DOMReader domReader = new DOMReader();
                        doc = xml != null ? domReader.read(xml.getDOM()):null;
                        ruleVO.setRuleDocument(doc);
				    }// End of if(rs.getOPAQUE("RULE_DATA")
				}//end of while(rs.next())
			}//end of if(rs != null)
	log.info("Before return ruleVo :"+ df.format(Calendar.getInstance(tz).getTime()));		
return ruleVO;
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getPAClaimsRule()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getPAClaimsRule()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getPAClaimsRule()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPAClaimsRule(long lngSeqID,String strFlag)

	/**
     * This method returns the RuleVO, which contains all the Rule Data details
     * @param strFlag String object which contains Flag - P / C
     * @param lngSeqID long value which contains PAT_GEN_DETAIL_SEQ_ID/CLAIM_SEq_ID to get the Rule Data Details
     * @return Document object which contains Rule Data XML
     * @exception throws TTKException
     */
	public Document processRule(String strFlag,long lngSeqID) throws TTKException {
		Document doc = null;
        XMLType xml = null;
        Connection conn = null;
        OracleCallableStatement stmt = null;
  /*
        import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;*/
		TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
	        DateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:S");
			df.setTimeZone(tz); 
        try{
        	conn = ResourceManager.getConnection();
	log.info("Before executing RuleDAOImpl processRule Proc :"+ df.format(Calendar.getInstance(tz).getTime()));
    		stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strProcessRule);
    		log.info("After executing RuleDAOImpl processRule Proc :"+ df.format(Calendar.getInstance(tz).getTime()));	
stmt.setString(1,strFlag);
    		stmt.setLong(2,lngSeqID);
    		stmt.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
    		stmt.execute();
    		xml = XMLType.createXML(stmt.getOPAQUE(3));
    		DOMReader domReader = new DOMReader();
            doc = xml != null ? domReader.read(xml.getDOM()):null;
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (stmt != null) stmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in RuleDAOImpl processRule()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyrule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl processRule()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyrule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyrule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
log.info("Befor returning Document :"+ df.format(Calendar.getInstance(tz).getTime()));
		return doc;
	}//end of processRule(String strFlag,long lngSeqID)
	
	/**
     * This method returns the ArrayList, which contains all the Product/Policy Clauses details
     * @param lngProdPolicySeqID long value which contains Seq ID to get the Product/Policy Clauses Details
     * @param strIdentifier String value which contains identifier - Clause/Coverage for fetching the information
     * @return ArrayList object which contains all the Product/Policy Clauses details
     * @exception throws TTKException
     */
	public ArrayList getProdPolicyClause(long lngProdPolicySeqID,String strIdentifier) throws TTKException {
		Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	PolicyClauseVO policyClauseVO = null;
    	Collection<Object> alResultList = new ArrayList<Object>();
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetProdPolicyClause);
			cStmtObject.setLong(1,lngProdPolicySeqID);
			cStmtObject.setString(2,strIdentifier);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					policyClauseVO = new PolicyClauseVO();
					
					if(rs.getString("CLAUSE_SEQ_ID") != null){
						policyClauseVO.setClauseSeqID(new Long(rs.getLong("CLAUSE_SEQ_ID")));
					}//end of if(rs.getString("CLAUSE_SEQ_ID") != null)
					
					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						policyClauseVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
					
					policyClauseVO.setClauseNbr(TTKCommon.checkNull(rs.getString("CLAUSE_NUMBER")));
					policyClauseVO.setClauseDesc(TTKCommon.checkNull(rs.getString("CLAUSE_DESCRIPTION")));
					policyClauseVO.setClauseYN(TTKCommon.checkNull(rs.getString("CLAUSE_YN")));
					policyClauseVO.setActiveYN(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
				    if(rs.getString("CLAUSE_YN").equalsIgnoreCase("Y")){
						if(TTKCommon.checkNull(rs.getString("CLAUSE_TYPE")).equalsIgnoreCase("") || rs.getString("CLAUSE_TYPE")!=null )					{
							if(rs.getString("CLAUSE_TYPE").equalsIgnoreCase("SHORTFALL")){
								policyClauseVO.setClauseForDesc(TTKCommon.checkNull(rs.getString("CLAUSE_TYPE")));
								policyClauseVO.setClauseFor("SRT");
								policyClauseVO.setClauseSubType(TTKCommon.checkNull(rs.getString("CLAUSE_SUB_TYPE")));
							}
							else{
								policyClauseVO.setClauseFor("OTH");
								policyClauseVO.setClauseForDesc(TTKCommon.checkNull(rs.getString("CLAUSE_TYPE")));
							}
						}

					}
					//Added as per Shortfall 1179 CR 
					alResultList.add(policyClauseVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getProdPolicyClause()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getProdPolicyClause()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getProdPolicyClause()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
	}//end of getProdPolicyClause(long lngProdPolicySeqID)
	
	/**
     * This method saves the Product/Policy Clauses information
     * @param ruleVO RuleVO contains Product/Policy Clauses information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int saveProdPolicyClause(PolicyClauseVO policyClauseVO) throws TTKException {
		int iResult = 0;
		Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveProdPolicyClause);
            
            if(policyClauseVO.getClauseSeqID() != null){
            	cStmtObject.setLong(1,policyClauseVO.getClauseSeqID());
            }//end of if(policyClauseVO.getClauseSeqID() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else
            
            cStmtObject.setLong(2,policyClauseVO.getProdPolicySeqID());
            cStmtObject.setString(3,policyClauseVO.getClauseYN());
            cStmtObject.setString(4,policyClauseVO.getClauseNbr());
            cStmtObject.setString(5,policyClauseVO.getClauseDesc());
            cStmtObject.setLong(6,policyClauseVO.getUpdatedBy());
             //CHANGES AA PER KOC1179 Shortfall Cr
			if(policyClauseVO.getClauseYN().equalsIgnoreCase("Y"))
			{
				cStmtObject.setString(7,policyClauseVO.getClauseFor());
				if(policyClauseVO.getClauseFor().equalsIgnoreCase("SRT")){
					cStmtObject.setString(8,policyClauseVO.getClauseSubType());
				}
				else{
					cStmtObject.setString(8,null);
				}
			}
			else{
				
				cStmtObject.setString(7,null);
				cStmtObject.setString(8,null);
			}
			cStmtObject.registerOutParameter(9,Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(9);
			//CHANGES AA PER KOC1179 Shortfall Cr
			conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyrule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyrule");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in RuleDAOImpl saveProdPolicyClause()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyrule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl saveProdPolicyClause()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyrule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyrule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveProdPolicyClause(PolicyClauseVO policyClauseVO)
	
	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteProdPolicyClause(ArrayList alDeleteList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			 conn = ResourceManager.getConnection();
	         cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteProdPolicyClause);
	         cStmtObject.setString(1,(String)alDeleteList.get(0));//Concatenated String of Clause Seq ID's
	         cStmtObject.setLong(2,(Long)alDeleteList.get(1));//UPDATED_BY
	         cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
			 cStmtObject.execute();
			 iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyrule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyrule");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in RuleDAOImpl deleteProdPolicyClause()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyrule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl deleteProdPolicyClause()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyrule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyrule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteProdPolicyClause(ArrayList alDeleteList)
	
	/**
     * This method returns the ArrayList, which contains the Daycare procedures belonging to given group
     * @param strDaycareGroupId String Daycare group id
     * @return ArrayList object which contains daycare procedures belonging to this group.
     * @exception throws TTKException
     */
	public ArrayList getDayCareProcedureList(String strDaycareGroupId) throws TTKException {
		Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
    	ArrayList<Object> alDaycareProcedure = null;
    	CacheObject cacheObject=null;
    	try
    	{
    		conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strDAYCARE_PROCEDURES);
                pStmt.setString(1,strDaycareGroupId);
            
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                	cacheObject = new CacheObject();
                    if(alDaycareProcedure == null)
                    {
                        alDaycareProcedure = new ArrayList<Object>();
                    }//end of if(alDaycareProcedure == null)
                    cacheObject.setCacheId(TTKCommon.checkNull(rs.getString(1)));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString(2)));
                    alDaycareProcedure.add(cacheObject);
    			}//end of while(rs.next())
            }//end of if(rs != null)
    		return alDaycareProcedure;
    	}// end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyrule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyrule");
        }//end of catch (Exception exp)
        finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getDayCareProcedureList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getDayCareProcedureList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getDayCareProcedureList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getDayCareProcedureList
	
	//added for KOC-1310
	/**
     * This method returns the ArrayList, which contains all the Cancer ICD CODES
     * @param strCancerGroupId String cancer ICD group id
     * @return ArrayList object which contains Cancer ICD codes belonging to this group.
     * @exception throws TTKException
     */
	public ArrayList getCancerICDList(String strCancerGroupId)throws TTKException {
		
		Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
    	ArrayList<Object> alCancerICDCode = null;
    	CacheObject cacheObject=null;
    	try
    	{
    		conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strcancericdprocedures);
            pStmt.setString(1,strCancerGroupId);
            
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                	cacheObject = new CacheObject();
                    if(alCancerICDCode == null)
                    {
                    	alCancerICDCode = new ArrayList<Object>();
                    }//end of if(alDaycareProcedure == null)
                    cacheObject.setCacheId(TTKCommon.checkNull(rs.getString(1)));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString(2)));
                    alCancerICDCode.add(cacheObject);
    			}//end of while(rs.next())
            }//end of if(rs != null)
    		return alCancerICDCode;
    	}// end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyrule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyrule");
        }//end of catch (Exception exp)
        finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getCancerICDList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getCancerICDList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getCancerICDList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}
	//ended
	
	
	/**
	 * This method returns List of business errors occured during the rule engine execution in Enrollment and softcopy upload
	 * @param lngErrorCode long value which contains Product Policy Seq ID to get the Rule Data Details
	 * @return alResultList ArrayList list of business errors
	 * @exception throws TTKException
	 */
	public ArrayList getValidationErrorList(long lngErrorCode) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ErrorLogVO errorLogVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetValidationErrorList);
			cStmtObject.setLong(1,lngErrorCode);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					errorLogVO = new ErrorLogVO();
					
					if(rs.getString("ERROR_CODE") != null){
						errorLogVO.setErrorCode(new Long(rs.getLong("ERROR_CODE")));
					}//end of if(rs.getString("ERROR_CODE") != null)
					
					if(rs.getString("POLICY_SEQ_ID") != null){
						errorLogVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)
					
					if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
						errorLogVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
					}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)
					
					if(rs.getString("MEMBER_SEQ_ID") != null){
						errorLogVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
					
					errorLogVO.setConditionID(TTKCommon.checkNull(rs.getString("CONDITION_ID")));
					errorLogVO.setOperator(TTKCommon.checkNull(rs.getString("OP")));
					errorLogVO.setOperatorType(TTKCommon.checkNull(rs.getString("OPTYPE")));
					errorLogVO.setMethodName(TTKCommon.checkNull(rs.getString("METHOD")));
					errorLogVO.setConfiguredValue(TTKCommon.checkNull(rs.getString("CONFIGURED_VALUE")));
					errorLogVO.setComputedValue(TTKCommon.checkNull(rs.getString("COMPUTED_VALUE")));
					errorLogVO.setUnit(TTKCommon.checkNull(rs.getString("UNIT")));
					
					// Added for constructing proper error message by Unni on 03-04-2008
					errorLogVO.setErrorMessage(rs.getString("MESSAGE"));
					// End of Adding
					
					//Fields related to softcopy upload
//					if(rs.getString("TPA_OFFICE_SEQ_ID") != null){
//						errorLogVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
//					}//end of if(rs.getString("ERROR_CODE") != null)
//					
//					if(rs.getString("INS_SEQ_ID") != null){
//						errorLogVO.setInsSeqID(new Long(rs.getLong("INS_SEQ_ID")));
//					}//end of if(rs.getString("ERROR_CODE") != null)
//					
//					errorLogVO.setAbbrevationCode(TTKCommon.checkNull(rs.getString("ABBREVATION_CODE")));
//					errorLogVO.setInsCompCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
//					errorLogVO.setBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NUMBER")));
//					errorLogVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
//					errorLogVO.setEndorsementNbr(TTKCommon.checkNull(rs.getString("CUST_ENDORSEMENT_NUMBER")));
//					errorLogVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
//					errorLogVO.setPolicyHolder(TTKCommon.checkNull(rs.getString("POLICY_HOLDER")));
//					errorLogVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
//					errorLogVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
					alResultList.add(errorLogVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getValidationErrorList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getValidationErrorList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getValidationErrorList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getValidationErrorList(long lngProdPolicySeqID)
	
	/**
     * This method returns the ArrayList, which contains all the Disease Details
     * @param alSearchCriteria arraylist which contains  all the clause related details
     * @return ArrayList object which contains all the Disease Details
     * @exception throws TTKException
     */
	public ArrayList<ClauseDiseaseVO> getDiseaseList(ArrayList<Object> alSearchCriteria) throws TTKException
	{
		log.debug ("Inside getdisease list method");
		ArrayList<ClauseDiseaseVO> alDiseaseList = new ArrayList<ClauseDiseaseVO>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ClauseDiseaseVO  clauseDiseaseVO = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDiseaseList);
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setLong(2,(Long)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5, (String)alSearchCriteria.get(4));
			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(6);
			if(rs != null)
			{
				while(rs.next())
				{
					clauseDiseaseVO = new ClauseDiseaseVO();
					if(rs.getString("INS_SPECIFIC_CODE_SEQ_ID") != null){
						clauseDiseaseVO.setInsSpecificSeqID(new Long(rs.getString("INS_SPECIFIC_CODE_SEQ_ID")));
					}//end of if(rs.getString("INS_SPECIFIC_CODE_SEQ_ID") != null)
					clauseDiseaseVO.setDiseaseCode(TTKCommon.checkNull(rs.getString("CODE")));
					clauseDiseaseVO.setDiseaseDesc(TTKCommon.checkNull(rs.getString("CODE_DESCRIPTION")));
					alDiseaseList.add(clauseDiseaseVO);
					
				}//end of while(rs.next())
			}//end of if(rs != null)
		return (ArrayList<ClauseDiseaseVO>) alDiseaseList;
	    }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getDiseaseList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getDiseaseList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getDiseaseList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getDiseaseList(ArrayList alSearchCriteria)
	
	/**
     * This method is used to Associate/Disassociate a disease to a clause
     * @param alDiseaseList ArrayList which contains disease/clause related information
     * @return int the value which returns greater than zero for successful execution of the task
     * @exception throws TTKException
     */
	public int associateDiseases(ArrayList<Object> alDiseaseList) throws TTKException
	{
		log.debug("Inside associateDiseases method in RuleDAOImpl ");
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateDiseases);
			
			if(alDiseaseList != null){
				cStmtObject.setLong(1,(Long)alDiseaseList.get(0));
				cStmtObject.setString(2,(String)alDiseaseList.get(1));
				cStmtObject.setString(3,(String)alDiseaseList.get(2));
				cStmtObject.setLong(4,(Long)alDiseaseList.get(3));
				cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
				cStmtObject.execute();
				iResult = cStmtObject.getInt(5);//ROWS_PROCESSED
			}//end of if(alDiseaseList != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in RuleDAOImpl associateDiseases()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyrule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl associateDiseases()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyrule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyrule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of associateDiseases(ArrayList alDiseaseList)
//added for hyundai buffer
	
	
	/**
     * This method saves the Product/Policy Clauses information
     * @param ruleVO RuleVO contains Product/Policy Clauses information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public ArrayList savePolicyLevelConfiguration(LevelConfigurationVO levelConfigurationVO) throws TTKException {
		int iResult = 0;
		Connection conn = null;
        CallableStatement cStmtObject=null;
        ArrayList allOutParam=new ArrayList();
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveLevelConfiguration);
            
            if(levelConfigurationVO.getBufferSeqId() != null){
            	cStmtObject.setLong(1,levelConfigurationVO.getBufferSeqId());
            }//end of if(policyClauseVO.getClauseSeqID() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else
              cStmtObject.setLong(2,levelConfigurationVO.getProdPolicySeqID());
              cStmtObject.setString(3,levelConfigurationVO.getBufferType().trim());
              cStmtObject.setString(4,levelConfigurationVO.getLevelType());
              cStmtObject.setBigDecimal(5,levelConfigurationVO.getLevelsLimit());
               cStmtObject.setString(6,levelConfigurationVO.getRemarks());
              cStmtObject.setLong(7,levelConfigurationVO.getUpdatedBy());
             //CHANGES AA PER KOC1179 Shortfall Cr
              cStmtObject.registerOutParameter(8,Types.VARCHAR);
			cStmtObject.registerOutParameter(9,Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(9);
			
			
			 String alertMsg = (cStmtObject.getString(8)!=null)?cStmtObject.getString(8):"";
	            if(!alertMsg.equalsIgnoreCase(""))
	            {
	            	allOutParam.add(0);
	            	allOutParam.add(alertMsg);
	            }
	            else
	            {
	            	allOutParam.add(1);
	            	allOutParam.add(alertMsg);
	            }
	            allOutParam.add(iResult);
			//CHANGES AA PER KOC1179 Shortfall Cr
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyrule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyrule");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in RuleDAOImpl saveProdPolicyClause()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyrule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl saveProdPolicyClause()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyrule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyrule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return allOutParam;
	}//end of saveProdPolicyClause(PolicyClauseVO policyClauseVO)
	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deletePolicyLevelConfiguration(ArrayList alDeleteList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			 conn = ResourceManager.getConnection();
	         cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteLevelConfiguration);
	         cStmtObject.setString(1,(String)alDeleteList.get(0));//Concatenated String of Clause Seq ID's
	         cStmtObject.setLong(2,(Long)alDeleteList.get(1));//UPDATED_BY
	         cStmtObject.setString(3,(String)alDeleteList.get(2));//UPDATED_BY
	         cStmtObject.setLong(4,(Long)alDeleteList.get(3));//UPDATED_BY
	         cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
			 cStmtObject.execute();
			 iResult = cStmtObject.getInt(5);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyrule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyrule");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in RuleDAOImpl deleteProdPolicyClause()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyrule");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in RuleDAOImpl deleteProdPolicyClause()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyrule");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyrule");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteProdPolicyClause(ArrayList alDeleteList)
	
	
	
	/**
     * This method returns the ArrayList, which contains all the Product/Policy Clauses details
     * @param lngProdPolicySeqID long value which contains Seq ID to get the Product/Policy Clauses Details
     * @param strIdentifier String value which contains identifier - Clause/Coverage for fetching the information
     * @return ArrayList object which contains all the Product/Policy Clauses details
     * @exception throws TTKException
     */
	public ArrayList getPolicyLevelConfiguration(long lngPolicySeqID,long lngUserSeqId) throws TTKException {
		Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	LevelConfigurationVO levelConfigurationVO = null;
    	Collection<Object> alResultList = new ArrayList<Object>();
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetLevelConfiguration);
			cStmtObject.setLong(1,lngPolicySeqID);
			//cStmtObject.setString(2,strIdentifier);
			cStmtObject.setLong(2,lngUserSeqId);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					levelConfigurationVO = new LevelConfigurationVO();
					
					if(rs.getString("BUFF_LEVEL_SEQ_ID") != null){
						levelConfigurationVO.setBufferSeqId(new Long(rs.getLong("BUFF_LEVEL_SEQ_ID")));
					}//end of if(rs.getString("CLAUSE_SEQ_ID") != null)
					
					/*if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						levelConfigurationVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
*/					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						levelConfigurationVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
                    
                   levelConfigurationVO.setBufferType(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID").trim()));//code
                   levelConfigurationVO.setGeneralType(TTKCommon.checkNull(rs.getString("BUFFER_TYPE").trim()));//description
                    levelConfigurationVO.setLevelType(TTKCommon.checkNull(rs.getString("LEVEL_TYPE").trim()));
                    levelConfigurationVO.setLevelDesc(TTKCommon.checkNull(rs.getString("LEVEL_DESC").trim()));
					levelConfigurationVO.setLevelsLimit((BigDecimal) TTKCommon.checkNull(rs.getBigDecimal("LEVEL_LIMIT")));
					levelConfigurationVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
				
					//Added as per Shortfall 1179 CR 
					alResultList.add(levelConfigurationVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getProdPolicyClause()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getProdPolicyClause()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getProdPolicyClause()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
	}//end of getProdPolicyClause(long lngProdPolicySeqID)
	public int saveBenefitRules(RuleVO ruleVO) throws TTKException{

		Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strsavebenefitrules);
			if(ruleVO.getBenefitRuleSeqID()==null)
				//cStmtObject.setString(1,null);
				cStmtObject.setLong(1,0);
				
			else cStmtObject.setLong(1,ruleVO.getBenefitRuleSeqID());
			cStmtObject.setLong(2,ruleVO.getProdPolicyRuleSeqID());
			cStmtObject.setString(3,ruleVO.getBenefitID());
			//XMLType ruleXML = XMLType.createXML((((WrappedConnectionJDK6)conn).getUnderlyingConnection()), ruleVO.getBenefitRuleXml());
			SQLXML ruleXML=conn.createSQLXML();
			if(ruleVO.getBenefitRuleXml()!=null){
				ruleXML.setString(ruleVO.getBenefitRuleXml().toString());
			}//end of if(ruleVO.getBenefitRuleXml()!=null)
			else{
				ruleXML.setString("<empty/>");
			}
			
			
			cStmtObject.setSQLXML(4, ruleXML);
			//XMLType copayRuleXMLType=null;
			SQLXML copayRuleXMLType=conn.createSQLXML();
			if(ruleVO.getProviderCopayRulesWriter() != null){
    			//copayRuleXMLType = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), ruleVO.getProviderCopayRulesWriter() .toString());
				copayRuleXMLType.setString(ruleVO.getProviderCopayRulesWriter() .toString());
				cStmtObject.setObject(5,copayRuleXMLType);
    		}//end of if(ruleVO.getRuleDocument() != null)
    		else{
    			
    			//copayRuleXMLType = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), "<ProviderRules></ProviderRules>");
    			copayRuleXMLType.setString("<ProviderRules></ProviderRules>");
    			cStmtObject.setSQLXML(5,copayRuleXMLType);
    		}
			//cStmtObject.setLong(6., ruleVO.getUpdatedBy());
			cStmtObject.setInt(6, ruleVO.getUpdatedBy().intValue());				
			cStmtObject.executeUpdate();
			conn.commit();
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl saveBenefitRules()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl saveBenefitRules()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{				
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return 1;
	
	}
	public LinkedHashMap<String, String> getBenefitTypes() throws TTKException{


		Connection conn = null;
    	PreparedStatement pStmt=null;
    	ResultSet rs = null;
    	LinkedHashMap<String, String> hmBenefitTypes=new LinkedHashMap<>();
    	try{
    		conn = ResourceManager.getConnection();
    		
			pStmt=conn.prepareStatement(strBenefitTypes);
			rs=pStmt.executeQuery();
			while(rs.next()){
				hmBenefitTypes.put(rs.getString("BENEFIT_TYPE_ID"), rs.getString("BENEFIT_DESCRIPTION"));
			}
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getBenefitTypes()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getBenefitTypes()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getBenefitTypes()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    	
		return hmBenefitTypes;	
	}
	
	public LinkedHashMap<String,Long> getConfBenefitTypes(String strProPolRuleSeqID) throws TTKException{

		Connection conn = null;
    	PreparedStatement pStmtObject=null;
    	ResultSet rs = null;
    	LinkedHashMap<String,Long> lhmConfBenefitTypes=new LinkedHashMap<>();
    	try{
    		conn = ResourceManager.getConnection();   
    		
    		pStmtObject = conn.prepareStatement(strConfBenefitTypes);
    		//pStmtObject.setString(1, strProPolRuleSeqID);
    		 pStmtObject.setInt(1,Integer.parseInt(strProPolRuleSeqID));    		
    		 rs = pStmtObject.executeQuery();
			while(rs.next()){
				lhmConfBenefitTypes.put(rs.getString("BENEFIT_ID"),rs.getLong("BENEFIT_RULES_SEQ_ID"));
			}
			
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					
					//if (prs != null) prs.close();
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getConfBenefitTypes()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
					//	if (policyStmtObject != null) policyStmtObject.close();
						if (pStmtObject != null) pStmtObject.close();
						
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getConfBenefitTypes()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getConfBenefitTypes()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	
		return lhmConfBenefitTypes;	
	
	}
	public Document getConfBenefitXml(Long strBenefitRulSeqID) throws TTKException{

		Connection conn = null;
    	PreparedStatement pStmtObject=null;
    	ResultSet rs = null;
    	Document document=null;
    	try{
    		conn = ResourceManager.getConnection();    		
			
    		pStmtObject = conn.prepareStatement(strGetConfBenefitXml);
    		pStmtObject.setLong(1,strBenefitRulSeqID);    		
			rs = pStmtObject.executeQuery();
			if(rs.next()){
				if(rs.getSQLXML("BENEFIT_XML") != null) {
					SAXReader saxReader=new SAXReader();
					document=saxReader.read(rs.getSQLXML("BENEFIT_XML").getCharacterStream());
			    }// End of if(rs.getSQLXML("BENEFIT_XML") != null)
			
			}
			
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in RuleDAOImpl getConfBenefitXml()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmtObject != null) pStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl getConfBenefitXml()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl getConfBenefitXml()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	
		return document;	
	}
	public int removeBenefitXml(RuleVO ruleVO) throws TTKException{

		Connection conn = null;
    	CallableStatement cStmtObject=null;
    	int updated=0;
    	try{
    		conn = ResourceManager.getConnection();    		
			
    		//pStmtObject = conn.prepareStatement(strRemoveConfBenefitXml);
    		cStmtObject = conn.prepareCall(strRemoveConfBenefitXml);
    		cStmtObject.setLong(1,ruleVO.getProdPolicyRuleSeqID());    		    
    		cStmtObject.setString(2,ruleVO.getBenefitID());
    		cStmtObject.setLong(3,ruleVO.getUpdatedBy());  
    		updated=cStmtObject.executeUpdate();
    		conn.commit();
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl removeBenefitXml()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl removeBenefitXml()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return updated;	
	
	}
	
	public int saveAndCompleteRules(String proPolRulSeqID,Long userSeqID) throws TTKException{

		Connection conn = null;
    	PreparedStatement pStmtObject=null;
    	ResultSet rs=null;
    	
    	CallableStatement cStmtObject=null;
    	int updated=0;
    	
    	try{
    		conn = ResourceManager.getConnection();    		
    		Document dynDoc = DocumentHelper.createDocument();
    	     Element dynCon = dynDoc.addElement("conditions");
    	     
    	     Document statDoc = DocumentHelper.createDocument();
    	     Element statCon = statDoc.addElement("conditions");
    	     
    		pStmtObject = conn.prepareStatement(strGetAllConfBenefitXml);
    		//pStmtObject.setString(1,proPolRulSeqID);    		
			  pStmtObject.setLong(1,Long.parseLong(proPolRulSeqID));
    		rs = pStmtObject.executeQuery();
			while(rs.next()){
			    
				   Document document=null;
				   if(rs.getSQLXML("BENEFIT_XML") != null) {
                       SAXReader saxReader=new SAXReader();
                       document=saxReader.read(rs.getSQLXML("BENEFIT_XML").getCharacterStream());
				    }// End of if(rs.getOPAQUE("BENEFIT_XML")

				   
				   Element dynBenefitType=dynCon.addElement("benefit");
	               dynBenefitType.addAttribute("gid", rs.getString("BENEFIT_ID"));
	               Element statBenefitType=statCon.addElement("benefit");
	               statBenefitType.addAttribute("gid", rs.getString("BENEFIT_ID"));	               
	               RuleXmlUtility.getConditionFunctions(rs.getString("BENEFIT_ID"),document,dynBenefitType,statBenefitType);
			
			}
			
			cStmtObject = conn.prepareCall(strSaveConditionRules);
			
			cStmtObject.setLong(1,0);
			cStmtObject.setLong(2, Long.parseLong(proPolRulSeqID));
			SQLXML sqlxmlStatic=conn.createSQLXML();
			sqlxmlStatic.setString(statDoc.asXML());
			SQLXML sqlxmlDynamic=conn.createSQLXML();
			sqlxmlDynamic.setString(dynDoc.asXML());
			cStmtObject.setObject(3, sqlxmlStatic);
			cStmtObject.setObject(4, sqlxmlDynamic);
			cStmtObject.setLong(5, userSeqID);
			updated=cStmtObject.executeUpdate();
			conn.commit();
			
			/*OutputFormat format = OutputFormat.createPrettyPrint();			
			XMLWriter writer = new XMLWriter(
					new FileWriter( "D:/benefitRules/dyna.xml" ),format
				);
				writer.write( dynDoc );
				writer.close();
				
				XMLWriter writers = new XMLWriter(
						new FileWriter( "D:/benefitRules/stat.xml" ),format
					);
					writers.write( statDoc );
					writers.close();*/
			
    	}//end of try
    	
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
					
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the rs in RuleDAOImpl saveAndComleteRules()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if rs is not closed, control reaches here. Try closing the connection now.
				{
				
				try
					{
						if (pStmtObject != null) pStmtObject.close();
						if (cStmtObject != null) cStmtObject.close();
						
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in RuleDAOImpl saveAndComleteRules()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in RuleDAOImpl saveAndComleteRules()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyrule");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
			}
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyrule");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs=null;
				pStmtObject = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	
		return updated;	
	}

public int overideBenefitRules(String proPolRulSeqID,Long userSeqID) throws TTKException{

	Connection conn = null;
	PreparedStatement pStmtObject=null;
	PreparedStatement dpStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection(); 
		conn.setAutoCommit(false);
		//XMLType emptyRuleXML = XMLType.createXML(((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()), "<empty/>");
		SQLXML emptyRuleXML=conn.createSQLXML();
		emptyRuleXML.setString("<empty/>");
		pStmtObject = conn.prepareStatement(strSetRuleStatus);
		pStmtObject.setObject(1, emptyRuleXML);
		//pStmtObject.setString(2,proPolRulSeqID);    		
	    pStmtObject.setLong(2, Long.parseLong(proPolRulSeqID));
		pStmtObject.executeUpdate();
	    
	    dpStmtObject = conn.prepareStatement(strDeleteBenefitRules);
	    //dpStmtObject.setString(1,proPolRulSeqID);    		
	     dpStmtObject.setLong(1,Long.parseLong( proPolRulSeqID));
	    updated= dpStmtObject.executeUpdate();
	   conn.commit();
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					if (dpStmtObject != null) dpStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl overideBenefitRules()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl overideBenefitRules()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			dpStmtObject=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	

}
/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addConsdDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{


	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();    		
		
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getConsltFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		//cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getInvestgationSeqID()))){
			cStmtObject.setLong(1, Long.parseLong(investigationRuleVO.getInvestgationSeqID()));
			            } 
		else{
			cStmtObject.setLong(1,0);	
		}
		//cStmtObject.setString(2, null);
		cStmtObject.setLong(2, 0);
		//cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getProPolRuleSeqID()))){
			cStmtObject.setLong(3, Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
			            } 
		else{
			cStmtObject.setLong(3,0);	
		}
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11, hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		updated=cStmtObject.executeUpdate();
		conn.commit();
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addConsdDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addConsdDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}


//addVaccinDetails added by govind
/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addVaccinDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{


	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();    		
		
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getVaccinFieldXml(conn, investigationRuleVO);
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.info("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addVaccinDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addVaccinDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}
public ArrayList getEncounterTypes(String benefitId) {
	

	Connection con = null;
	CallableStatement cStm = null;
	ResultSet resultSet = null;
	ArrayList alEnctTypes=new ArrayList<>();
	try {
		con = ResourceManager.getConnection();
		cStm = con.prepareCall(strEncounterTypes);
		cStm.setString(1, benefitId);
		cStm.registerOutParameter(2, Types.OTHER);
		if("IPT".equals(benefitId))
		cStm.setString(3, benefitId);
		else cStm.setString(3, null);
		cStm.execute();
		resultSet = (ResultSet) cStm.getObject(2);
		if (resultSet != null) {
			while (resultSet.next()){
				CacheObject cache=new CacheObject();
			cache.setCacheId(resultSet.getString("ENCOUNTER_SEQ_ID"));
			cache.setCacheDesc(resultSet.getString("DESCRIPTION"));
			alEnctTypes.add(cache);
			}
		}
	} catch (Exception exception) {
		log.error("Error  in PreAuthDAOImpl getEncounterTypes()", exception);
		exception.printStackTrace();
	} finally {
		try {
			if (resultSet != null)
				resultSet.close();
			if (cStm != null)
				cStm.close();
			if (con != null)
				con.close();
		} catch (Exception exception2) {
			log.error(
					"Error while closing the Connection/Statement in  getEncounterTypes()",
					exception2);
			exception2.printStackTrace();
	}
	}
	return alEnctTypes;
}// end of getEncounterTypes(Long preauthSeqId)

// getEncounterTypesSPCB added by govind
public ArrayList getEncounterTypesSPCB(String spType) {
	
	Connection con = null;
	CallableStatement cStm = null;
	ResultSet resultSet = null;
	ArrayList alEnctTypes=new ArrayList<>();
	try {
		con = ResourceManager.getConnection();
		cStm = con.prepareCall(strEncounterTypesSPCB);
		cStm.setString(1, spType);
		cStm.registerOutParameter(2, Types.OTHER);
		cStm.execute();
		resultSet = (ResultSet) cStm.getObject(2);
		if (resultSet != null) {
			while (resultSet.next()){
				CacheObject cache=new CacheObject();
			cache.setCacheId(resultSet.getString("ENCOUNTER_SEQ_ID"));
			cache.setCacheDesc(resultSet.getString(2));
			alEnctTypes.add(cache);
			}
		}
	} catch (Exception exception) {
		log.error("Error  in PreAuthDAOImpl getEncounterTypesSPCB()", exception);
		exception.printStackTrace();
	} finally {
		try {
			if (resultSet != null)
				resultSet.close();
			if (cStm != null)
				cStm.close();
			if (con != null)
				con.close();
		} catch (Exception exception2) {
			log.error(
					"Error while closing the Connection/Statement in  getEncounterTypesSPCB()",
					exception2);
			exception2.printStackTrace();
	}
	}
	return alEnctTypes;
}// end of getEncounterTypes(Long preauthSeqId)

// not used
public ArrayList getAltTmtTypes() {
	
	Connection con = null;
	CallableStatement cStm = null;
	ResultSet resultSet = null;
	ArrayList alEnctTypes=new ArrayList<>();
	try {
		con = ResourceManager.getConnection();
		cStm = con.prepareCall(strEncounterTypesSPCB);
		cStm.setString(1, "ATT");
		cStm.registerOutParameter(2, Types.OTHER);
		cStm.execute();
		resultSet = (ResultSet) cStm.getObject(2);
		if (resultSet != null) {
			while (resultSet.next()){
				CacheObject cache=new CacheObject();
			cache.setCacheId(resultSet.getString("ENCOUNTER_SEQ_ID"));
			cache.setCacheDesc(resultSet.getString(2));
			alEnctTypes.add(cache);
			}
		}
	} catch (Exception exception) {
		log.error("Error  in PreAuthDAOImpl getAltTmtTypes()", exception);
		exception.printStackTrace();
	} finally {
		try {
			if (resultSet != null)
				resultSet.close();
			if (cStm != null)
				cStm.close();
			if (con != null)
				con.close();
		} catch (Exception exception2) {
			log.error(
					"Error while closing the Connection/Statement in  getAltTmtTypes()",
					exception2);
			exception2.printStackTrace();
	}
	}
	return alEnctTypes;
}// end of getAltTmtTypes


public ArrayList<InvestigationRuleVO> getConsdDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		//pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setLong(1,Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getConsConfdtls(rs, investRuleVO);	
			
			
			SQLXML xmlType =(SQLXML)rs.getObject("STATIC_COND");					
              SAXReader saxreader=new SAXReader();
               Document  document = xmlType != null ? saxreader.read(xmlType.getCharacterStream()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getConsdDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getConsdDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getConsdDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}
public ArrayList<InvestigationRuleVO> getInvestdDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		//pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setLong(1,Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			RuleXmlUtility.getInvestConfDtls(rs, investRuleVO);	
			
			
			/*XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;*/
			SQLXML sqlxml=(SQLXML)rs.getObject("STATIC_COND");
		     SAXReader saxreader=new SAXReader();
		     Document document=sqlxml!=null ? saxreader.read(sqlxml.getCharacterStream()):null;
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getInvestdDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getInvestdDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getInvestdDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}

public ArrayList<InvestigationRuleVO> getPhysioDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
	   //pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setLong(1,Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getPhysioConfDtls(rs, investRuleVO);	
			
			
			SQLXML sqlxml =(SQLXML)rs.getObject("STATIC_COND");					
                SAXReader saxReader = new SAXReader();
               Document  document = sqlxml != null ? saxReader.read(sqlxml.getCharacterStream()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getInvestdDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getInvestdDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getInvestdDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}

// added by govind getVaccinDetails

public ArrayList<InvestigationRuleVO> getVaccinDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	
	log.debug("getVaccinDetails RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection(); 
	
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			RuleXmlUtility.getVaccinConfDtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getVaccinDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getVaccinDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getVaccinDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}
public int deleteConsdtls(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();    		
		
		pStmtObject = conn.prepareCall(strDeleteConsltDetails);
		
		pStmtObject.setLong(1,Long.parseLong(investigationRuleVO.getInvestgationSeqID()));
		
		 updated=pStmtObject.executeUpdate();
	    conn.commit();
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl deleteConsdtls()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl deleteConsdtls()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			pStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	

}

public InvestigationRuleVO getConsDtls(InvestigationRuleVO investigationRuleVO) throws TTKException{

	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
    try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConfConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getInvestgationSeqID());  
		rs = pStmtObject.executeQuery();
		if(rs.next()){
			
			RuleXmlUtility.getConsConfdtls(rs, investRuleVO);			
			
               
		}		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getConsdDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getConsdDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getConsdDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
    return investRuleVO;
}
public int addInvestDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();    		
		
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getInvestFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		
		//cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getInvestgationSeqID()))){
			cStmtObject.setLong(1, Long.parseLong(investigationRuleVO.getInvestgationSeqID()));
			            } 
		else{
			cStmtObject.setLong(1,0);	
		}
		
		//cStmtObject.setString(2, null);
		cStmtObject.setLong(2,0);
		//cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getProPolRuleSeqID()))){
			cStmtObject.setLong(3, Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
			            } 
		else{
			cStmtObject.setLong(3,0);	
		}
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10,hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setString(12,investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		updated=cStmtObject.executeUpdate();
		conn.commit();
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addInvestDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addInvestDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	

}

public int addPhysioDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();    		
		
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getPhysioFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		
		//cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getInvestgationSeqID()))){
			cStmtObject.setLong(1,Long.parseLong(investigationRuleVO.getInvestgationSeqID()));
			            }
		else{
			cStmtObject.setLong(1,0);
		}

		
		cStmtObject.setLong(2, 0);
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getProPolRuleSeqID()))){
			cStmtObject.setLong(3, Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
			            }
		else{
		cStmtObject.setLong(3, 0);
		}
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10,hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setString(12,investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		updated=cStmtObject.executeUpdate();
		conn.commit();
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addPhysioDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addPhysioDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	

}

public ArrayList<InvestigationRuleVO> getPharmaDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alPharma=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		//pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setLong(1,Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getPharmaConfDtls(rs, investRuleVO);	
			
			
			SQLXML sqlxml =(SQLXML)rs.getObject("STATIC_COND");					
                SAXReader saxReader = new SAXReader();
               Document  document = sqlxml != null ? saxReader.read(sqlxml.getCharacterStream()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alPharma.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getPharmaDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getPharmaDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getPharmaDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alPharma;	

}

public int addPharmaDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();    		
		
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getPharmaFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		
		//cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getInvestgationSeqID()))){
			cStmtObject.setLong(1, Long.parseLong(investigationRuleVO.getInvestgationSeqID()));
			            }
		else{
			cStmtObject.setLong(1, 0);
		}
		//cStmtObject.setString(2, null);
		cStmtObject.setLong(2, 0);
		//cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getProPolRuleSeqID()))){
			cStmtObject.setLong(3, Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
			            }
		else{
			cStmtObject.setLong(3, 0);
		}
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10,hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setString(12,investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		updated=cStmtObject.executeUpdate();
		conn.commit();
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addPharmaDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addPharmaDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	

}
public Reader getMohProCopayDetails(Long strBenefitRulSeqID) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
	ResultSet rs = null;
	Reader reader=null;
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strgetprocopayconfxml);
		pStmtObject.setLong(1,strBenefitRulSeqID);    		
		rs = pStmtObject.executeQuery();
		if(rs.next()){
			
			if(rs.getSQLXML("BENEFIT_COVE_AREA_XML") != null) {
				reader=rs.getSQLXML("BENEFIT_COVE_AREA_XML").getCharacterStream();
		    }// End of if(rs.getOPAQUE("BENEFIT_COVE_AREA_XML")
		
		}
		
		
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in RuleDAOImpl getMohProCopayDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmtObject != null) pStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getMohProCopayDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getMohProCopayDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs = null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return reader;	
}

public int addCompanDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();    		
		
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getCompanFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveCompanDtls);	
		
		//cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		/*if(investigationRuleVO.getInvestgationSeqID()!="" && investigationRuleVO.getInvestgationSeqID()!=null){
		    System.out.println(investigationRuleVO.getInvestgationSeqID()==""+"InvestigationSeqID"+investigationRuleVO.getInvestgationSeqID());
			cStmtObject.setLong(1, Long.parseLong(investigationRuleVO.getInvestgationSeqID()));
		}*/
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getInvestgationSeqID()))){
			cStmtObject.setLong(1, Long.parseLong(investigationRuleVO.getInvestgationSeqID()));
			            } 
		else{
			cStmtObject.setLong(1,0);	
		}
		cStmtObject.setLong(2, 0);
		cStmtObject.setLong(3, Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10,hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setString(12,investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		cStmtObject.setObject(14, hmConfXml.get("HospTypes"));
		updated=cStmtObject.executeUpdate();
		conn.commit();
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addCompanDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addCompanDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	

}//addCompanDetails

public ArrayList<InvestigationRuleVO> getCompanDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alPharma=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		 if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getProPolRuleSeqID()))){
			 pStmtObject.setLong(1,Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
			             } 
		 else{
		pStmtObject.setLong(1,0); 
		 }
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			RuleXmlUtility.getCompanConfdtls(rs, investRuleVO);	
			SQLXML sqlxml =(SQLXML)rs.getObject("STATIC_COND");					
               SAXReader saxReader=new SAXReader();
               Document  document = sqlxml != null ? saxReader.read(sqlxml.getCharacterStream()):null;		
               investRuleVO.setStaticDoc(document);               
               alPharma.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getCompanDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getCompanDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getCompanDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alPharma;	

}

public int addAmbulanceDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();    		
		
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getAmbulanceFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveCompanDtls);	
		
		//cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getInvestgationSeqID()))){
			cStmtObject.setLong(1, Long.parseLong(investigationRuleVO.getInvestgationSeqID()));
			            } 
		else{
			cStmtObject.setLong(1,0);
		}
		//cStmtObject.setString(2, null);
		cStmtObject.setLong(2,0);
		//cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getProPolRuleSeqID()))){
			cStmtObject.setLong(3, Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
			            }
		else{
			cStmtObject.setLong(3,0);
		}
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10,hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setString(12,investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		cStmtObject.setObject(14, hmConfXml.get("HospTypes"));
		updated=cStmtObject.executeUpdate();
		conn.commit();
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addAmbulanceDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addAmbulanceDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	

}//addAmbulanceDetails


public ArrayList<InvestigationRuleVO> getAmbulanceDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alPharma=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		//pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getProPolRuleSeqID()))){
			 pStmtObject.setLong(1,Long.parseLong(investigationRuleVO.getProPolRuleSeqID()));
			 } 
		 else{
		pStmtObject.setLong(1,0); 
		 }
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			RuleXmlUtility.getAmbulanceConfdtls(rs, investRuleVO);	
			//XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
			// DOMReader domReader = new DOMReader();  
			  SQLXML sqlxml=rs.getSQLXML("STATIC_COND");
			  SAXReader saxreader=new SAXReader(); 
               Document  document = sqlxml != null ? saxreader.read(sqlxml.getCharacterStream()):null;	
               investRuleVO.setStaticDoc(document);               
               alPharma.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getAmbulanceDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getCompanDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getCompanDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alPharma;	

}//getAmbulanceDetails
public String getProductAuthority(Long proPolSeqID) throws TTKException{
	
Connection conn = null;
PreparedStatement pStmtObject=null;
ResultSet rs=null;
String strAuthType="";
try{
	conn = ResourceManager.getConnection();    		
	pStmtObject = conn.prepareStatement(strGetProAuthorityName);
	pStmtObject.setLong(1,proPolSeqID);  
	rs = pStmtObject.executeQuery();
	if(rs!=null){
	if(rs.next()){
		strAuthType=rs.getString("authority_type");
	}
	}
	
}//end of try

catch (SQLException sqlExp)
{
	throw new TTKException(sqlExp, "prodpolicyrule");
}//end of catch (SQLException sqlExp)
catch (Exception exp)
{
	throw new TTKException(exp, "prodpolicyrule");
}//end of catch (Exception exp)
finally
{
	/* Nested Try Catch to ensure resource closure */ 
	try // First try closing the result set
	{
			
		try
		{
			if (rs != null) rs.close();
		}//end of try
		catch (SQLException sqlExp)
		{
			log.error("Error while closing the rs in RuleDAOImpl getProductAuthority()",sqlExp);
			throw new TTKException(sqlExp, "prodpolicyrule");
		}//end of catch (SQLException sqlExp)
		finally // Even if rs is not closed, control reaches here. Try closing the connection now.
		{
		
		try
			{
				if (pStmtObject != null) pStmtObject.close();
				
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Statement in RuleDAOImpl getProductAuthority()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in RuleDAOImpl getProductAuthority()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
	}
	}//end of try
	catch (TTKException exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (TTKException exp)
	finally // Control will reach here in anycase set null to the objects 
	{
		rs=null;
		pStmtObject = null;
		conn = null;
	}//end of finally
}//end of finally

return strAuthType;	
}//getProductAuthority

//development by govind for Special Benefits
public ArrayList<InvestigationRuleVO> getAltTmtDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getAltTmtDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getAltTmtConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getAltTmtDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getAltTmtDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getAltTmtDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}

/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addAltTmtDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addAltTmtDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getAltTmtFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addAltTmtDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addAltTmtDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}

public ArrayList<InvestigationRuleVO> getDEOTDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getDEOTDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getDEOTConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getDEOTDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getDEOTDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getDEOTDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addDEOTDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addDEOTDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getDEOTFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addDEOTDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addDEOTDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}

// new


public ArrayList<InvestigationRuleVO> getEyeCareDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getEyeCareDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getEyeCareConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getEyeCareDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getEyeCareDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getEyeCareDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addEyeCareDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addEyeCareDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getSpecialBenefitsFieldXml(conn, investigationRuleVO);
		cStmtObject = conn.prepareCall(strSaveConsltSPCB);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		
		cStmtObject.setObject(14, hmConfXml.get("HospTypes"));
		cStmtObject.setObject(15, hmConfXml.get("claimType"));
		cStmtObject.setObject(16,hmConfXml.get("networkType"));
		
		//hmConfXml={Countries=oracle.xdb.XMLType@1d9a8d9, networkType=oracle.xdb.XMLType@dc75e0, claimType=oracle.xdb.XMLType@1bed4f4, ProviderFaclityTypes=oracle.xdb.XMLType@18eed67, Emirates=oracle.xdb.XMLType@840c25, HospTypes=oracle.xdb.XMLType@13962d7, Encounters=oracle.xdb.XMLType@881702, StaticXml=oracle.xdb.XMLType@9eb7de, ClcnCopayDtl=oracle.xdb.XMLType@396193}

		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addEyeCareDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addEyeCareDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}


public ArrayList<InvestigationRuleVO> getGyncolgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getGyncolgyDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getGyncolgyConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getGyncolgyDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getGyncolgyDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getGyncolgyDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addGyncolgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addGyncolgyDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getGyncolgyFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addGyncolgyDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addGyncolgyDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}


public ArrayList<InvestigationRuleVO> getMinrSrgryDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getMinrSrgryDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getMinrSrgryConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getMinrSrgryDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getMinrSrgryDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getMinrSrgryDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addMinrSrgryDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addMinrSrgryDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getMinrSrgryFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addMinrSrgryDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addMinrSrgryDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}


public ArrayList<InvestigationRuleVO> getNaslCrctionDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getNaslCrctionDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getNaslCrctionConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getNaslCrctionDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getNaslCrctionDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getNaslCrctionDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addNaslCrctionDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addNaslCrctionDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getNaslCrctionFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addNaslCrctionDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addNaslCrctionDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}


public ArrayList<InvestigationRuleVO> getOnclgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getOnclgyDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getOnclgyConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getOnclgyDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getOnclgyDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getOnclgyDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addOnclgyDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addOnclgyDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getOnclgyFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addOnclgyDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addOnclgyDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}


public ArrayList<InvestigationRuleVO> getOgnTrnspltRcptDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getOgnTrnspltRcptDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getOgnTrnspltRcptConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getOgnTrnspltRcptDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getOgnTrnspltRcptDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getOgnTrnspltRcptDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addOgnTrnspltRcptDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addOgnTrnspltRcptDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getOgnTrnspltRcptFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addOgnTrnspltRcptDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addOgnTrnspltRcptDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}


public ArrayList<InvestigationRuleVO> getPsychiatricDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getDEOTDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getPsychiatricConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getPsychiatricDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getPsychiatricDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getPsychiatricDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addPsychiatricDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addPsychiatricDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getPsychiatricFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addPsychiatricDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addPsychiatricDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}


public ArrayList<InvestigationRuleVO> getRnlDlsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getRnlDlsDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection();    		
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			
			RuleXmlUtility.getRnlDlsConfdtls(rs, investRuleVO);	
			
			
			XMLType xmlType =(XMLType)rs.getObject("STATIC_COND");					
                DOMReader domReader = new DOMReader();
               Document  document = xmlType != null ? domReader.read(xmlType.getDOM()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getRnlDlsDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getRnlDlsDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getRnlDlsDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addRnlDlsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	log.debug("addRnlDlsDetails:");
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getRnlDlsFieldXml(conn, investigationRuleVO);
		
		cStmtObject = conn.prepareCall(strSaveConslt);
		cStmtObject.setString(1, investigationRuleVO.getInvestgationSeqID());
		cStmtObject.setString(2, null);
		cStmtObject.setString(3, investigationRuleVO.getProPolRuleSeqID());
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setObject(6, hmConfXml.get("StaticXml"));
		cStmtObject.setObject(7, hmConfXml.get("Encounters"));
		cStmtObject.setObject(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setObject(9, hmConfXml.get("Countries"));
		cStmtObject.setObject(10, hmConfXml.get("Emirates"));
		cStmtObject.setObject(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setObject(12, investigationRuleVO.getInvsType());
		cStmtObject.setLong(13, investigationRuleVO.getUpdatedBy());
		int i=cStmtObject.executeUpdate();
		log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addRnlDlsDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addRnlDlsDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}




// new


public int deleteSBdtls(InvestigationRuleVO investigationRuleVO) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();    		
		
		pStmtObject = conn.prepareCall(strDeleteConsltDetails);
		
		pStmtObject.setBigDecimal(1, new BigDecimal(investigationRuleVO.getInvestgationSeqID()));
		
		 updated=pStmtObject.executeUpdate();
	    
		 conn.commit();
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl deleteSBdtls()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl deleteSBdtls()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			pStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	

}

// new changes 



public ArrayList<InvestigationRuleVO> getSpecialBenefitsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{
	log.debug("in getSpecialBenefitsDetails of RuleDaoImpl");
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<InvestigationRuleVO> alInvest=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection(); 
		log.debug("InvsType:"+investigationRuleVO.getInvsType());
		pStmtObject = conn.prepareStatement(strGetConsltDetails);
		pStmtObject.setString(1,investigationRuleVO.getProPolRuleSeqID());  
		pStmtObject.setString(2,investigationRuleVO.getInvsType()); 
		pStmtObject.setString(3,investigationRuleVO.getBenefitID()); 
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			InvestigationRuleVO investRuleVO=new InvestigationRuleVO();
			investRuleVO.setInvestgationSeqID(rs.getString("COVE_COND_SEQ_ID"));
			investRuleVO.setInvsType(investigationRuleVO.getInvsType());
			RuleXmlUtility.getSpecialBenefitsConfdtls(rs, investRuleVO);	
			
			
			SQLXML sqlxml =(SQLXML)rs.getObject("STATIC_COND");					
                SAXReader saxReader = new SAXReader();
               Document  document = sqlxml != null ? saxReader.read(sqlxml.getCharacterStream()):null;
		
               investRuleVO.setStaticDoc(document);
               
               alInvest.add(investRuleVO);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getSpecialBenefitsDetails()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getSpecialBenefitsDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getSpecialBenefitsDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alInvest;	

}


/**
 * This method returns the int, which contains investigationRuleVO  updation
 * @return int  which contains benefit type and desc
 * @exception throws TTKException
 */
public int addSpecialBenefitsDetails(InvestigationRuleVO investigationRuleVO) throws TTKException{

	Connection conn = null;
	CallableStatement cStmtObject=null;
	int updated=0;
	try{
		conn = ResourceManager.getConnection();  
		HashMap<String, SQLXML> hmConfXml=RuleXmlUtility.getSpecialBenefitsFieldXml(conn, investigationRuleVO);
		cStmtObject = conn.prepareCall(strSaveConsltSPCB);
		if(!"".equals(TTKCommon.checkNull(investigationRuleVO.getInvestgationSeqID())))
		cStmtObject.setBigDecimal(1, new BigDecimal(investigationRuleVO.getInvestgationSeqID()));
		else cStmtObject.setBigDecimal(1, null);			
		cStmtObject.setBigDecimal(2, null);
		cStmtObject.setBigDecimal(3, new BigDecimal(investigationRuleVO.getProPolRuleSeqID()));
		cStmtObject.setString(4, investigationRuleVO.getBenefitID());
		cStmtObject.setString(5, investigationRuleVO.getActMasterCode());
		cStmtObject.setSQLXML(6, hmConfXml.get("StaticXml"));
		cStmtObject.setSQLXML(7, hmConfXml.get("Encounters"));
		cStmtObject.setSQLXML(8, hmConfXml.get("ProviderFaclityTypes"));
		cStmtObject.setSQLXML(9, hmConfXml.get("Countries"));
		cStmtObject.setSQLXML(10, hmConfXml.get("Emirates"));
		cStmtObject.setSQLXML(11,hmConfXml.get("ClcnCopayDtl"));
		cStmtObject.setString(12, investigationRuleVO.getInvsType());
		cStmtObject.setBigDecimal(13, new BigDecimal(investigationRuleVO.getUpdatedBy()));
		
		cStmtObject.setSQLXML(14, hmConfXml.get("HospTypes"));
		cStmtObject.setSQLXML(15, hmConfXml.get("claimType"));
		cStmtObject.setSQLXML(16,hmConfXml.get("networkType"));
		
		//hmConfXml={Countries=oracle.xdb.XMLType@1d9a8d9, networkType=oracle.xdb.XMLType@dc75e0, claimType=oracle.xdb.XMLType@1bed4f4, ProviderFaclityTypes=oracle.xdb.XMLType@18eed67, Emirates=oracle.xdb.XMLType@840c25, HospTypes=oracle.xdb.XMLType@13962d7, Encounters=oracle.xdb.XMLType@881702, StaticXml=oracle.xdb.XMLType@9eb7de, ClcnCopayDtl=oracle.xdb.XMLType@396193}

	      cStmtObject.execute();
	      conn.commit();
		//log.debug("i:"+i);
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
			try
				{
					if (cStmtObject != null) cStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl addSpecialBenefitsDetails()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl addSpecialBenefitsDetails()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			cStmtObject=null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return updated;	
}


// new changes


// added by govind getActivePolicies for HMO TOB CR

public ArrayList<HMOGlobalVO> getActivePolicies(String prodPolicyRuleSeqID) throws TTKException{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
    ResultSet rs=null;
    ArrayList<HMOGlobalVO> alActvPolicy=new ArrayList<>();
	try{
		conn = ResourceManager.getConnection(); 
		pStmtObject = conn.prepareStatement(strGetActivePolicy);
		pStmtObject.setString(1, prodPolicyRuleSeqID);
		rs = pStmtObject.executeQuery();
		while(rs.next()){
			HMOGlobalVO activPolVo=new HMOGlobalVO();
			activPolVo.setPolicyNo(rs.getString("prod_policy_rule_seq_id"));
			activPolVo.setInsuranceCompanyName(rs.getString("ins_comp_name"));
			activPolVo.setProductName(rs.getString("product_name"));
			activPolVo.setCorporateGroupID(rs.getString("group_subgroup_id"));
			activPolVo.setPolicyNm(rs.getString("policy_number"));
			
			activPolVo.setProductNetworkCategory(rs.getString("Product_Network"));
			activPolVo.setCorporateGroupName(rs.getString("group_name"));
			activPolVo.setPolicyAdmnstrtvSrvcType(rs.getString("capitation_yn"));
			activPolVo.setSumInsured(rs.getString("total_sum_insured"));
			
	/*		log.info("========================================================================");
			log.info("prod_policy_rule_seq_id:"+rs.getString("prod_policy_rule_seq_id"));
			log.info("group_subgroup_id:"+rs.getString("group_subgroup_id"));
			log.info("Product_Network:"+rs.getString("Product_Network"));
			log.info("group_name:"+rs.getString("group_name"));
			log.info("capitation_yn:"+rs.getString("capitation_yn"));
			log.info("capitation_yn from vo:"+activPolVo.getPolicyAdmnstrtvSrvcType());
			log.info("total_sum_insured:"+rs.getString("total_sum_insured"));
			log.info("========================================================================");*/

			alActvPolicy.add(activPolVo);
		}
		
		
	}//end of try
	
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "prodpolicyrule");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "prodpolicyrule");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the result set
		{
				
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in RuleDAOImpl getActivePolicies()",sqlExp);
				throw new TTKException(sqlExp, "prodpolicyrule");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
				{
					if (pStmtObject != null) pStmtObject.close();
					
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in RuleDAOImpl getActivePolicies()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyrule");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in RuleDAOImpl getActivePolicies()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyrule");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
		}
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "prodpolicyrule");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs=null;
			pStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return alActvPolicy;	

}


}//end of RuleDAOImpl
