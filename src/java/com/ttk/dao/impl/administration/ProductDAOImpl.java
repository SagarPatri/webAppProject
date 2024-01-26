/**
 * @ (#)  ProductDaoImpl.java Nov 5, 2005
 * Project      : TTKPROJECT
 * File         : ProductDaoImpl.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 5, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.administration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.EscalationLimitVO;
import com.ttk.dto.administration.PremiumConfigurationVO;
import com.ttk.dto.administration.ProdPolicyLimitVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.administration.RuleSynchronizationVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.security.GroupVO;

public class ProductDAOImpl implements BaseDAO,Serializable{

	private static Logger log = Logger.getLogger(ProductDAOImpl.class);

	//private static final String strProductList="SELECT * FROM(SELECT TPA_INS_PRODUCT.PRODUCT_SEQ_ID, PROD_POLICY_SEQ_ID,PROD_GENERAL_TYPE_ID,PRODUCT_NAME,TPA_INS_PRODUCT.DESCRIPTION PROD_DESC,INS_COMP_NAME,TPA_GENERAL_CODE.DESCRIPTION STATUSDESC,TPA_INS_PRODUCT.INS_SEQ_ID,PROD_STATUS_GENERAL_TYPE_ID, TPA_INS_PRODUCT.INS_PRODUCT_CODE, ABBREVATION_CODE, DENSE_RANK() OVER (ORDER BY #, ROWNUM)Q FROM TPA_INS_PROD_POLICY,TPA_INS_PRODUCT, TPA_INS_INFO, TPA_GENERAL_CODE WHERE TPA_INS_PROD_POLICY.PRODUCT_SEQ_ID = TPA_INS_PRODUCT.PRODUCT_SEQ_ID AND TPA_INS_PRODUCT.INS_SEQ_ID = TPA_INS_INFO.INS_SEQ_ID AND TPA_INS_PRODUCT.PROD_STATUS_GENERAL_TYPE_ID = TPA_GENERAL_CODE.GENERAL_TYPE_ID";
	private static final String strProductList ="SELECT * FROM(SELECT TPA_INS_PRODUCT.PRODUCT_SEQ_ID, PROD_POLICY_SEQ_ID,PROD_GENERAL_TYPE_ID,PRODUCT_NAME,TPA_INS_PRODUCT.DESCRIPTION PROD_DESC,INS_COMP_NAME,TPA_GENERAL_CODE.DESCRIPTION STATUSDESC,TPA_INS_PRODUCT.INS_SEQ_ID,PROD_STATUS_GENERAL_TYPE_ID, TPA_INS_PRODUCT.INS_PRODUCT_CODE,ABBREVATION_CODE,TPA_INS_PRODUCT.AUTHORITY_TYPE,TPA_INS_INFO.INS_COMP_CODE_NUMBER, DENSE_RANK() OVER (ORDER BY #)Q FROM app.TPA_INS_PROD_POLICY,app.TPA_INS_PRODUCT,app.TPA_INS_INFO, app.TPA_GENERAL_CODE WHERE TPA_INS_PROD_POLICY.PRODUCT_SEQ_ID = TPA_INS_PRODUCT.PRODUCT_SEQ_ID AND TPA_INS_PRODUCT.INS_SEQ_ID = TPA_INS_INFO.INS_SEQ_ID AND TPA_INS_PRODUCT.PROD_STATUS_GENERAL_TYPE_ID = TPA_GENERAL_CODE.GENERAL_TYPE_ID";

	private static final String strSynchPolicyList = "{CALL PRODUCT_ADMIN_PKG_GET_SYNCH_POLICY_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSynchronizeRule = "{CALL PRODUCT_ADMIN_PKG_SYNCHRONIZE_RULE(?,?,?,?)}";//denial process one parameter added
	private static final String strSynchronizeRuleAll = "{CALL PRODUCT_ADMIN_PKG_SYNCHRONIZE_ALL_POLICY(?,?)}";
	private static final String straddupdateproduct="{CALL PRODUCT_ADMIN_PKG_PR_PRODUCT_SAVE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//KOC 1286 added one parameter // added as per koc 1274A
    private static final String strDeleteProduct="{CALL intx.PRODUCT_ADMIN_PKG_PR_PRODUCTS_DELETE(?,?,?)}";
    private static final String strGetProduct = "{CALL PRODUCT_ADMIN_PKG_GET_PRODUCT_DETAILS(?,?)}";//"SELECT B.PRODUCT_SEQ_ID, A.PROD_POLICY_SEQ_ID, B.PROD_GENERAL_TYPE_ID, B.PRODUCT_NAME, B.DESCRIPTION PROD_DESC, C.INS_COMP_NAME,C.ABBREVATION_CODE,D.DESCRIPTION STATUSDESC,B.INS_SEQ_ID, B.PROD_STATUS_GENERAL_TYPE_ID, B.CARD_CLEARANCE_DAYS, B.CLAIM_CLEARANCE_DAYS, B.PA_CLEARANCE_HOURS , A.TEMPLATE_ID, A.DV_REQD_GENERAL_TYPE_ID,B.INS_PRODUCT_CODE,A.TENURE,B.AUTH_LETTER_GENERAL_TYPE_ID,A.STOP_PREAUTH_YN,A.STOP_CLAIM_YN,B.SURGERY_GEN_TYPE_ID, nvl(b.CBH_SUMINS_YN,'N') as CBH_SUMINS_YN, nvl(b.CONV_SUMINS_YN,'N') as CONV_SUMINS_YN,A.OPD_BENEFITS_YN,PAT_ALLOWED_YN,CLM_ALLOWED_YN,PAT_TO_MAIL_ID,PAT_CC_MAIL_ID,CLM_TO_MAIL_ID,CLM_CC_MAIL_ID,NVL(B.Critical_Sumins_Yn,'N') as Critical_Sumins_Yn,NVL(B.SURVIVAL_PRD,'N') AS SURVIVAL_PERIOD_YN,b.product_cat_type_id,b.auth_product_code  FROM TPA_INS_PROD_POLICY A JOIN TPA_INS_PRODUCT B ON (A.PRODUCT_SEQ_ID = B.PRODUCT_SEQ_ID) JOIN TPA_INS_INFO C ON (B.INS_SEQ_ID = C.INS_SEQ_ID) JOIN TPA_GENERAL_CODE D ON (B.PROD_STATUS_GENERAL_TYPE_ID = D.GENERAL_TYPE_ID) WHERE A.PROD_POLICY_SEQ_ID=?"; //added B.Critical_Sumins_Yn this for KOC-1273 6 param for bajaj koc 1274A
    //added B.Critical_Sumins_Yn this for KOC-1273
	private static final String strSaveUserGroup = "{CALL CONTACT_PKG_SAVE_PRODUCT_GROUPS(?,?,?,?)}";
	private static final String strGetUserGroup ="SELECT * FROM ( WITH INS AS ( SELECT C.INS_COMP_NAME FROM TPA_INS_PROD_POLICY A JOIN TPA_INS_PRODUCT B ON (A.PRODUCT_SEQ_ID = B.PRODUCT_SEQ_ID AND A.PROD_POLICY_SEQ_ID = ?) JOIN TPA_INS_INFO C ON ( B.INS_SEQ_ID = C.INS_SEQ_ID )) SELECT A.GROUP_SEQ_ID , A.GROUP_NAME, C.GROUP_BRANCH_SEQ_ID, D.TPA_OFFICE_SEQ_ID, CASE WHEN F.TPA_OFFICE_GENERAL_TYPE_ID = 'THO' THEN 'TZO' ELSE D.TPA_OFFICE_GENERAL_TYPE_ID END TPA_OFFICE_GENERAL_TYPE_ID , D.OFFICE_NAME , E.INS_COMP_NAME , D.TPA_PARENT_SEQ_ID FROM TPA_GROUPS A JOIN TPA_INS_PROD_POLICY B ON (A.PROD_POLICY_SEQ_ID = B.PROD_POLICY_SEQ_ID AND B.PROD_POLICY_SEQ_ID = ?) JOIN TPA_GROUP_BRANCH C ON ( A.GROUP_SEQ_ID = C.GROUP_SEQ_ID ) RIGHT OUTER JOIN TPA_OFFICE_INFO D ON ( C.TPA_OFFICE_SEQ_ID = D.TPA_OFFICE_SEQ_ID ) CROSS JOIN INS E LEFT OUTER JOIN TPA_OFFICE_INFO F ON ( D.TPA_PARENT_SEQ_ID = F.TPA_OFFICE_SEQ_ID )) START WITH TPA_OFFICE_GENERAL_TYPE_ID = 'THO' CONNECT BY PRIOR tpa_office_seq_id = tpa_parent_seq_id";
	private static final String strGetUserGroupp="{CALL PRODUCT_ADMIN_USER_GROUP(?,?)}";
	private static final String strCopyProduct = "{CALL PRODUCT_ADMIN_PKG.PR_COPY_PRODUCT_RULES(?,?,?,?)}";
	private static final String strAssInsProduct = "{CALL PRODUCT_ADMIN_PKG_ASSOC_INS_PRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveProdPolicyLimit = "{CALL PRODUCT_ADMIN_PKG_SAVE_PROD_POLICY_LIMITS(?,?,?,?)}";
	private static final String strGetProdPolicyLimit = "{CALL PRODUCT_ADMIN_PKG_SELECT_PROD_POLICY_LIMITS(?,?)}";
	//added for bajaj
	private static final String strSaveProdPolicyEscalateLimit = "{CALL CLAIMS_APPROVAL_PKG.SAVE_PROD_POL_ESCALATION(?,?,?,?)}";
	private static final String strGetProdPolicyEscalateLimit = "{CALL CLAIMS_APPROVAL_PKG.SELECT_PROD_POL_ESCALATION(?,?,?)}";
    private static final String strSavePremiumConfiguration="{CALL PRODUCT_ADMIN_PKG_CONFIG_PREM_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strGetPremiumConfiguration="{CALL PRODUCT_ADMIN_PKG_GET_PREM_CONFIG_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveBenefitLimitSync="{CALL CLAIM_PKG_SAVE_BENEFI_LIMITS_SYN(?,?)}";
    private static final String strSaveBenefitLimitSyncAll="{CALL CLAIM_PKG_SAVE_BENEFI_LIMITS_SYN_ALL(?,?)}";
    private static final String strCardTempList = "{CALL PRODUCT_ADMIN_PKG_SELECT_GC_CARD_TEMPLATE(?,?,?)}";
    private static final String strCardTempList1 = "SELECT TEMPLATE_ID,TEMPLATE_NAME FROM TPA_POLICY_TEMPLATES ORDER BY UPPER(TEMPLATE_NAME)";
    private static final String strInsCmpCode = "SELECT INS_COMP_CODE_NUMBER FROM APP.TPA_INS_INFO WHERE INS_SEQ_ID=?";
   
    // private static final String strCapitationYNData = "SELECT p.rule_seq_id,rownum AS S_NO,tp.product_name as PRODUCT_NAME,p.min_age as MINIMUM_AGE,p.max_age as MAXIMUM_AGE,p.marital_status AS MARITAL_STATUS,p.gender AS GENDER_APPLICABLE,p.elegible_relation AS APPLICABLE_TO_RELATION,p.gross_prem as GROSS_PREMIUM,p.CAPITATION_YN as CAPITATION_YN,p.updated_remarks as updated_remarks, case when p.CAPITATION_YN='2' then 'OP'when p.CAPITATION_YN='3' then 'IP&OP' else null end  as CAPITATION_DESC,p.SALARY_BAND,p.AUTH_PRODUCT_CODE FROM intx.Tpa_Plan_Config p LEFT JOIN tpa_enr_policy po  ON (po.policy_seq_id = p.policy_seq_id) LEFT JOIN tpa_ins_product tp ON (tp.product_seq_id = po.product_seq_id)  WHERE p.rule_seq_id = ?";
    // private static final String strCapitationYNData = "SELECT p.rule_seq_id,rownum AS S_NO,tp.product_name as PRODUCT_NAME,p.min_age as MINIMUM_AGE,p.max_age as MAXIMUM_AGE,p.marital_status AS MARITAL_STATUS,p.gender AS GENDER_APPLICABLE,replace(p.elegible_relation,'ALL','NSF|YSP|NCH|NFR|YMO|OTH') AS APPLICABLE_TO_RELATION,p.gross_prem as GROSS_PREMIUM,p.CAPITATION_YN as CAPITATION_YN,p.updated_remarks as updated_remarks, case when p.CAPITATION_YN='2' then 'OP'when p.CAPITATION_YN='3' then 'IP&OP' else null end  as CAPITATION_DESC,p.SALARY_BAND,p.AUTH_PRODUCT_CODE FROM intx.Tpa_Plan_Config p LEFT JOIN tpa_enr_policy po  ON (po.policy_seq_id = p.policy_seq_id) LEFT JOIN tpa_ins_product tp ON (tp.product_seq_id = po.product_seq_id)  WHERE p.rule_seq_id = ?";
   
     private static final String strCapitationYNData = "SELECT p.rule_seq_id, row_number()over() AS S_NO, tp.product_name as PRODUCT_NAME, p.min_age as MINIMUM_AGE, p.max_age as MAXIMUM_AGE, p.marital_status AS MARITAL_STATUS, p.gender AS GENDER_APPLICABLE, replace(p.elegible_relation, 'ALL', 'NSF|YSP|NCH|NFR|YMO|OTH') AS APPLICABLE_TO_RELATION, p.gross_prem as GROSS_PREMIUM, p.CAPITATION_YN as CAPITATION_YN, p.updated_remarks as updated_remarks, case when p.CAPITATION_YN = '2' then 'OP' when p.CAPITATION_YN = '3' then 'IP&OP' else null end as CAPITATION_DESC, p.SALARY_BAND, p.AUTH_PRODUCT_CODE, p.medical_prem, p.maternity_prem, p.optical_prem, p.dental_prem, p.wellness_prem, p.ip_net_prem, p.insurer_margin, p.insurer_margin_aed insurer_margin_aed, p.insurer_margin_flag, p.broker_margin, p.broker_margin_aed broker_margin_aed, p.broker_margin_flag , p.tpa_margin, round(coalesce(p.gross_prem::integer, 0) * coalesce(p.tpa_margin::integer, 0) / 100, 2) as tpa_margin_aed, p.reins_brk_margin, p.reins_brk_margin_aed reins_brk_margin_aed, p.reins_brk_margin_flag, p.other_margin,p.other_margin_aed other_margin_aed, p.other_margin_flag, p.net_prem, p.op_net_prem, p.tpa_fee,p.tpa_fee_aed,p.tpa_fee_flag,p.tpa_fee_type FROM app.Tpa_Plan_Config p LEFT JOIN app.tpa_enr_policy po  ON (po.policy_seq_id = p.policy_seq_id) LEFT JOIN app.tpa_ins_product tp ON (tp.product_seq_id = po.product_seq_id) WHERE p.rule_seq_id = ?";
        
     
     private static final String strGetProductVatYN = "select p.vat_eclg_chck from app.tpa_ins_product p where p.product_seq_id=?";
     
     private static final String strGetPolicyVatYN = "select p.vat_eclg_chck from app.tpa_enr_policy p where p.policy_seq_id=?";
     
    // private static final String strSynchronizeconfiguration="{CALL PRODUCT_ADMIN_PKG.synk_configurations(?,?,?,?)}";
    private static final String strSynchronizeCardRules="{CALL PRODUCT_ADMIN_PKG_SYNK_CARD_RULES(?,?,?)}";
    private static final String strSynchronizeGenRules="{CALL PRODUCT_ADMIN_PKG_SYNK_GEN_RULES(?,?,?,?,?)}";
    
    private static final String strSynchronizeGenRules1="{CALL PRODUCT_ADMIN_PKG_SYNK_CARD_BENIFIT_RULES(?,?,?,?)}";
	private static final String strGetAuditPremiumConfiguration="{CALL PRODUCT_ADMIN_PKG_TPA_CONFIG_RULE_LOG(?,?,?,?,?)}";
    private static final String strDELETE_CONFIG_RULE = "{CALL PRODUCT_ADMIN_PKG_DELETE_CONFIG_RULE(?,?,?,?,?)}";
    
    private static final String strSaveVatConfiguratinYN = "{CALL PRODUCT_ADMIN_PKG_CHECK_VAT_ELGBLITY(?,?,?,?)}";
    
    
    private static final String updfatePremiumConfigDetails="{CALL PRODUCT_ADMIN_PKG_PREMIUM_DATE_UPDATION(?,?,?,?,?,?,?)}";
    private static final String  strEffectiveDatesList = "{CALL PRODUCT_ADMIN_PKG_GET_PREMIUM_SRCH_LIST(?,?,?,?,?)}";
    private static final String strDELETE_EFFECTIVE_PREMIUM_PERIOD ="{CALL PRODUCT_ADMIN_PKG_DELETE_INTRVL_WISE_PREMIUM(?,?,?,?,?)}";
    
    
    
    private static final String strSynchronizeVatRules ="{CALL PRODUCT_ADMIN_PKG_SYNC_VAT_SI_RULE(?,?)}";
    
    private static final String strPtoductModificationRemarks="SELECT ADDED_DATE ,OLD_VALUE,SUBSTR(NEW_VALUE,1,1) NEW_VALUE,SUBSTR(NEW_VALUE,3) MODIFIY_REM FROM APP.TPA_INS_PRODUCT_LOG WHERE PRODUCT_SEQ_ID=? AND UPPER(COLUMN_NAME) = 'PREMIUM_REFUND_YN' ORDER BY LOG_SEQ_ID";
    
    private static final String strPolicyModificationRemarks ="SELECT ADDED_DATE ,OLD_VALUE,SUBSTR(NEW_VALUE,1,1) NEW_VALUE,SUBSTR(NEW_VALUE,3) MODIFIY_REM FROM APP.TPA_POLICY_LOG_DTL WHERE POLICY_SEQ_ID=? AND UPPER(COLUMN_NAME) = 'PREMIUM_REFUND_YN' ORDER BY LOG_DTL_SEQ_ID";
    
    
	private static final int PROD_POLICY_SEQ_ID = 1;
	private static final int INS_SEQ_ID = 2;
	private static final int PRODUCT_TYPE_ID = 3;
	private static final int PRODUCT_NAME = 4;
	private static final int DESCRIPTION = 5;
	private static final int PROD_STATUS_GENERAL_TYPE_ID = 6;
	private static final int USER_SEQ_ID = 7;
	private static final int INSCOMP_ABBR_CODE = 8;
	private static final int CARD_CLEARANCE_DAYS = 9;
	private static final int CLAIM_CLEARANCE_DAYS = 10;
	private static final int PA_CLEARANCE_HOURS = 11;
	private static final int TEMPLATE_ID = 12;
	private static final int DISCHARGE_VOUCH_MANDATORY_YN = 13;
	private static final int INS_PRODUCT_CODE = 14;
	private static final int TENURE = 15;
	private static final int AUTH_LETTER_GENERAL_TYPE_ID = 16;
	private static final int STOP_PREAUTH_YN = 17;
	private static final int STOP_CLAIM_YN = 18;
	private static final int SURGERY_GNRL_TYPE_ID =19;
	private static final int OPD_BENEFITS_YN =20; //KOC 1286 FOR OPD
    private static final int CBH_SUMINS_YN = 21; // KOC 1270 for hospital cash benefit
    private static final int CONV_SUMINS_YN = 22; // KOC 1270 for hospital cash benefit
    //added for KOC-1273
    private static final int CRITICAL_ILLNESS_BENEFIT = 23;
    private static final int SURVIVAL_PERIOD = 24;
    private static final int Pat_Enable_YN = 25; // koc 1274A
    private static final int Clm_Enable_YN = 26; // koc 1274A
    private static final int Pat_Mail_To = 27; //   koc 1274A
    private static final int Pat_Mail_CC = 28; //   koc 1274A
    private static final int Clm_Mail_To = 29; //   koc 1274A
    private static final int Clm_Mail_CC = 30; //   koc 1274A
    private static final int PRODUCT_NETWORK_TYPE = 31;
    private static final int HEALTH_AUTHORITY = 32;
    private static final int CO_INS=33;
    private static final int DEDUCTIBLE=34;
    private static final int CLASS_ROOM_TYPE=35;                   
    private static final int PLAN_TYPE=36;                    
    private static final int MATERNITY_YN=37;                               
    private static final int MATERNITY_COPAY=38;      
    private static final int OPTICAL_YN=39;                     
    private static final int OPTICAL_COPAY=40;                             
    private static final int DENTAL_YN=41;                       
    private static final int DENTAL_COPAY=42;                              
    private static final int ELIGIBILITY=43;                                         
    private static final int IP_OP_SERVICES=44;                                             
    private static final int PHARMACEUTICALS=45;
    private static final int ROWS_PROCESSED = 46;
    private static final int V_AUTH_PRODUCT_CODE = 47;
    
    private static final int SMEproductYN = 48;
    private static final int vatPercent = 49;
    private static final int refundedYN = 50;
    private static final int refundedYNRemarks = 51;
    
    
	
	/**
     * This method returns the ArrayList, which contains the ProductVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ProductVO'S object's which contains the details of the insurance products
     * @exception throws TTKException
     */
    public ArrayList getProductList(ArrayList alSearchObjects) throws TTKException {
    	Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strProductList;
        ProductVO productVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            for(int i=0; i < alSearchObjects.size()-4; i++)
            {
                if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
                {
                    if (((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
                    {
                        sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
                    }//end of if (((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
                    else
                    {
                        sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
                    }//end of else
                }//end of if(!((SearchCriteria)alSearchCriteria.get(i)).getValue().equals(""))
            }//end of for()
        }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+",TPA_INS_PRODUCT.PRODUCT_SEQ_ID "+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    productVO = new ProductVO();
                    if (rs.getString("PRODUCT_SEQ_ID")!=null){
                        productVO.setProdSeqId(new Long(rs.getLong("PRODUCT_SEQ_ID")));
                    }//end of if (rs.getString("PRODUCT_SEQ_ID")!=null)
                    if (rs.getString("PROD_POLICY_SEQ_ID")!=null){
                        productVO.setProdPolicySeqID((rs.getLong("PROD_POLICY_SEQ_ID")));
                    }//end of if (rs.getString("PROD_POLICY_SEQ_ID")!=null)

                    productVO.setProdTypeId(TTKCommon.checkNull(rs.getString("PROD_GENERAL_TYPE_ID")));
                    productVO.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                    productVO.setProdDesc(TTKCommon.checkNull(rs.getString("PROD_DESC")));
                    productVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
                    productVO.setCompanyAbbreviation(TTKCommon.checkNull(rs.getString("ABBREVATION_CODE")));
                    productVO.setProductCode(TTKCommon.checkNull(rs.getString("INS_PRODUCT_CODE")));
                    productVO.setProductAuthority(TTKCommon.checkNull(rs.getString("AUTHORITY_TYPE")));
                    productVO.setInsuranceCompanyID(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));

                    if (rs.getString("INS_SEQ_ID") != null){
                        productVO.setInsSeqId(new Long(rs.getLong("INS_SEQ_ID")));
                    }//end of if (rs.getString("INS_SEQ_ID") != null)

                    productVO.setStatus(TTKCommon.checkNull(rs.getString("STATUSDESC")));
                    productVO.setProdStatTypeId(TTKCommon.checkNull(rs.getString("PROD_STATUS_GENERAL_TYPE_ID")));
                    resultList.add(productVO);
                }//end of while
            }//end of if(rs != null)
            return (ArrayList)resultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "product");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "product");
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
					log.error("Error while closing the Resultset in ProductDAOImpl getProductList()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getProductList()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getProductList()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// End of getProductList(ArrayList alSearchObjects)

    /**
     * This method returns the ArrayList, which contains the RuleSynchronizationVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of RuleSynchronizationVO'S object's which contains the details of the Policies to Synchronize the Rules
     * @exception throws TTKException
     */
    public ArrayList getSynchPolicyList(ArrayList alSearchObjects) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        Collection<Object> resultList = new ArrayList<Object>();
        RuleSynchronizationVO ruleSynchVO = null;
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject =  conn.prepareCall(strSynchPolicyList);

			cStmtObject.setLong(1,(Long)alSearchObjects.get(0));
			cStmtObject.setString(2,(String)alSearchObjects.get(1));
			cStmtObject.setString(3,(String)alSearchObjects.get(2));
			cStmtObject.setString(4,(String)alSearchObjects.get(3));
			cStmtObject.setString(5,(String)alSearchObjects.get(4));
			cStmtObject.setString(6,(String)alSearchObjects.get(7));
			cStmtObject.setString(7,(String)alSearchObjects.get(8));
			cStmtObject.setLong(8,Long.valueOf((String) (alSearchObjects.get(9))));
			cStmtObject.setLong(9,Long.valueOf((String) alSearchObjects.get(10)));
			cStmtObject.setLong(10,(Long)alSearchObjects.get(5));
			cStmtObject.setString(11, (String)alSearchObjects.get(6));
			cStmtObject.registerOutParameter(12,Types.OTHER);//OracleTypes.CURSOR
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(12);
			if(rs != null){
				while(rs.next()){
					ruleSynchVO = new RuleSynchronizationVO();

					if(rs.getString("POLICY_SEQ_ID") != null){
						ruleSynchVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						ruleSynchVO.setProdpolicySeqId(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)

					ruleSynchVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					ruleSynchVO.setPrevPolicyNbr(TTKCommon.checkNull(rs.getString("RENEWAL_POLICY_NUMBER")));
					ruleSynchVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					ruleSynchVO.setCorporateName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));

					if(rs.getString("EFFECTIVE_FROM_DATE") != null){
						ruleSynchVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if(rs.getString("EFFECTIVE_TO_DATE") != null){
						ruleSynchVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)
					
					if(rs.getString("RULE_SYNCH_DATE") != null){
						ruleSynchVO.setSynchDate(new Date(rs.getTimestamp("RULE_SYNCH_DATE").getTime()));
					}//end of if(rs.getString("RULE_SYNCH_DATE") != null)
					//denial process					
					if(rs.getString("DEN_SYNC_DATE") != null){
						ruleSynchVO.setInsurerSynchDate(new Date(rs.getTimestamp("DEN_SYNC_DATE").getTime()));
					}//end of if(rs.getString("RULE_SYNCH_DATE") != null)
					
					if(rs.getString("description") != null){
						ruleSynchVO.setPolicyStatusID(rs.getString("description"));
					}
					//denial process
					resultList.add(ruleSynchVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
					log.error("Error while closing the Resultset in ProductDAOImpl getSynchPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getSynchPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getSynchPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)resultList;
    }//end of getSynchPolicyList(ArrayList alSearchObjects)

    /**
     * This method synchronizes the rules of Products to Policies
     * @param strSeqID contains concatenated ProdpolicySeqID's of Policies
     * @param lngProdPolicySeqID contains productpolicySeqID of Product
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int synchronizeRule(String[] strSeqID,long lngProdPolicySeqID,String strDenialsyn) throws TTKException {
    	int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSynchronizeRule);
			cStmtObject.setString(1,"|"+strSeqID[0]+"|");//policy_prod_seq_ids
			cStmtObject.setLong(2,lngProdPolicySeqID); //prod_policy_seq_id
			cStmtObject.setString(3,strDenialsyn);//denial process
			cStmtObject.registerOutParameter(4,Types.BIGINT);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);
			if(cStmtObject!=null) cStmtObject.close();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveBenefitLimitSync);
			cStmtObject.setString(1,"|"+strSeqID[1]+"|");
			cStmtObject.registerOutParameter(2,Types.BIGINT);
			cStmtObject.execute();
			conn.commit();
		}//end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl synchronizeRule()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl synchronizeRule()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally//end of synchronizeRule(String strSeqID,long lngProdPolicySeqID)
    	return iResult;
    }
	/**
	 * This method adds/updates the ProductVO which contains insurance products details
	 * @param productVO the details of insurance product which has to be added or updated
	 * @return Object[] the values,of  Product Policy Sequence Id and InsAbberevation Code
	 * @exception throws TTKException
	 */
	public Object[] addUpdateProduct(ProductVO productVO) throws TTKException {
		Object[] objArrayResult = new Object[2];
		long lResult = 0;
		String strAbbrCode = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(straddupdateproduct);
			
			if (productVO.getProdPolicySeqID()!=null){
				cStmtObject.setLong(PROD_POLICY_SEQ_ID,productVO.getProdPolicySeqID());
				
				
			}//end of if (productVO.getProdPolicySeqID()!=null)
			else{
				cStmtObject.setLong(PROD_POLICY_SEQ_ID,0);
			}//end of else

			if (productVO.getInsSeqId()!=null){
				cStmtObject.setLong(INS_SEQ_ID,productVO.getInsSeqId());
			}//end of if (productVO.getInsSeqId()!=null)
			else{
				cStmtObject.setLong(INS_SEQ_ID,0);
			}//end of else

			cStmtObject.setString(PRODUCT_TYPE_ID,productVO.getProdTypeId());
			cStmtObject.setString(PRODUCT_NAME,productVO.getProductName());
			cStmtObject.setString(V_AUTH_PRODUCT_CODE,productVO.getAuthorityProductCode());
			cStmtObject.setString(DESCRIPTION,productVO.getProdDesc());
			cStmtObject.setLong(USER_SEQ_ID,productVO.getUpdatedBy());
			cStmtObject.setString(PROD_STATUS_GENERAL_TYPE_ID,productVO.getProdStatTypeId());
			cStmtObject.setString(PRODUCT_NETWORK_TYPE,productVO.getProductNetworkType());

			if (productVO.getCardClearanceDays()!=null){
				cStmtObject.setInt(CARD_CLEARANCE_DAYS,productVO.getCardClearanceDays().intValue());
			}//end of if (productVO.getCardClearanceDays()!=null)
			else{
				cStmtObject.setInt(CARD_CLEARANCE_DAYS,0);
			}//end of else

			if (productVO.getClaimClearanceDays()!=null){
				cStmtObject.setInt(CLAIM_CLEARANCE_DAYS,productVO.getClaimClearanceDays().intValue());
			}//end of if (productVO.getClaimClearanceDays()!=null)
			else{
				cStmtObject.setInt(CLAIM_CLEARANCE_DAYS,0);
			}//end of else

			if (productVO.getPaClearanceHours()!=null){
				cStmtObject.setInt(PA_CLEARANCE_HOURS,productVO.getPaClearanceHours().intValue());
			}//end of if (productVO.getPaClearanceHours()!=null)
			else{
				cStmtObject.setInt(PA_CLEARANCE_HOURS,0);
			}//end of else

			if(productVO.getTemplateID() != null){
				cStmtObject.setLong(TEMPLATE_ID,productVO.getTemplateID());
			}//end of if(productVO.getTemplateID() != null)
			else{
				cStmtObject.setLong(TEMPLATE_ID,0);
			}//end of else

			cStmtObject.setString(DISCHARGE_VOUCH_MANDATORY_YN,productVO.getDischargeVoucherMandatoryYN());
			cStmtObject.setString(INS_PRODUCT_CODE,productVO.getInsProductCode());
			
			if(productVO.getTenure() != null){
				cStmtObject.setInt(TENURE,productVO.getTenure());
				
			}//end of if(productVO.getTenure() != null)
			else{
				cStmtObject.setInt(TENURE,0);
			}//end of else
			
			cStmtObject.setString(AUTH_LETTER_GENERAL_TYPE_ID,productVO.getAuthLtrTypeID());
			cStmtObject.setString(STOP_PREAUTH_YN,productVO.getStopPreAuthsYN());
			cStmtObject.setString(STOP_CLAIM_YN,productVO.getStopClaimsYN());
			cStmtObject.setString(SURGERY_GNRL_TYPE_ID,productVO.getSurgeryMandtryID());
			cStmtObject.setString(OPD_BENEFITS_YN,productVO.getopdClaimsYN());//KOC 1286 for OPD Benefit
			cStmtObject.setString(CBH_SUMINS_YN,productVO.getCashBenefitsYN());  //KOC 1270 for hospital cash benefit
			cStmtObject.setString(CONV_SUMINS_YN,productVO.getConvCashBenefitsYN());  //KOC 1270 for hospital cash benefit
			//added for KOC-1273
			cStmtObject.setString(CRITICAL_ILLNESS_BENEFIT,productVO.getCriticalBenefitYN());
			cStmtObject.setString(SURVIVAL_PERIOD,productVO.getSurvivalPeriodYN());
			//ended
			
            
			 
            cStmtObject.setString(Pat_Enable_YN,productVO.getPatEnableYN());
            cStmtObject.setString(Clm_Enable_YN,productVO.getClmEnableYN());
            cStmtObject.setString(Pat_Mail_To,productVO.getPatMailTo());
            cStmtObject.setString(Pat_Mail_CC,productVO.getPatMailCC());
            cStmtObject.setString(Clm_Mail_To,productVO.getClmMailTo());
            cStmtObject.setString(Clm_Mail_CC,productVO.getClmMailCC());			
			cStmtObject.registerOutParameter(INSCOMP_ABBR_CODE,Types.VARCHAR );
			cStmtObject.registerOutParameter(PROD_POLICY_SEQ_ID,Types.BIGINT);
			 cStmtObject.setString(HEALTH_AUTHORITY,productVO.getHealthAuthority());
			 if("".equals(TTKCommon.checkNull(productVO.getCoInsurance()))){
			 cStmtObject.setLong(CO_INS,0);
			 }else {
				 cStmtObject.setLong(CO_INS,Integer.parseInt(productVO.getCoInsurance()));
			 }
			 //System.out.println("CoInsurance NO:"+productVO.getCoInsurance());
			 cStmtObject.setString(DEDUCTIBLE,productVO.getDeductable());
			 cStmtObject.setString(CLASS_ROOM_TYPE,productVO.getClassRoomType());
			 cStmtObject.setString(PLAN_TYPE,productVO.getPlanType());
			 cStmtObject.setString(MATERNITY_YN,productVO.getMaternityYN());
			 cStmtObject.setString(MATERNITY_COPAY,productVO.getMaternityCopay());
			 cStmtObject.setString(OPTICAL_YN,productVO.getOpticalYN());
			 cStmtObject.setString(OPTICAL_COPAY,productVO.getOpticalCopay());
			 cStmtObject.setString(DENTAL_YN,productVO.getDentalYN());
			 cStmtObject.setString(DENTAL_COPAY,productVO.getDentalCopay());
			 cStmtObject.setString(ELIGIBILITY,productVO.getEligibility());
			 cStmtObject.setString(IP_OP_SERVICES,productVO.getIpopServices());
			 cStmtObject.setString(PHARMACEUTICALS,productVO.getPharmaceutical());
			 cStmtObject.setString(SMEproductYN,productVO.getSMEproductYN());
			 cStmtObject.setDouble(vatPercent,productVO.getVatPercent());
			 
			 
			 
			 cStmtObject.setString(refundedYN,productVO.getRefundedYN());
			 cStmtObject.setString(refundedYNRemarks,productVO.getRefundedYNRemarks());
			 cStmtObject.setString(52,productVO.getPlanIpCoPay());
			 cStmtObject.setString(53,productVO.getPlanOpCoPay());
			 cStmtObject.setString(54,productVO.getPlanOpConsultationCoPay());
			 cStmtObject.setString(55,productVO.getPlanPharmacyCoPay());
			 cStmtObject.setString(56,productVO.getPlanDentalCoPay());
			 cStmtObject.setString(57,productVO.getPlanOpticalCoPay());
			 cStmtObject.setString(58,productVO.getPlanMaternityCoPay());
			 cStmtObject.setString(59,productVO.getProductSubtype());
			 if(productVO.getTotalSumInsured() != null)
				{
					cStmtObject.setDouble(60,productVO.getTotalSumInsured().doubleValue());
				}
				else{
					cStmtObject.setDouble(60,0);
				}
			 System.out.println("save :ProductSubtype ->"+productVO.getProductSubtype());
			 System.out.println("save :TotalSumInsured ->"+productVO.getTotalSumInsured().doubleValue());
			 
			 cStmtObject.registerOutParameter(ROWS_PROCESSED,Types.BIGINT);
			cStmtObject.execute();
			lResult = cStmtObject.getLong(PROD_POLICY_SEQ_ID);
			strAbbrCode = cStmtObject.getString(INSCOMP_ABBR_CODE);
			objArrayResult[0] = new Long(lResult);
			objArrayResult[1] = strAbbrCode;
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl addUpdateProduct()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl addUpdateProduct()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return objArrayResult;
	}// End of addUpdateProduct(ProductVO productVO)

	/**
	 * This method deletes concerned InsuranceProduct details from the database
	 * @param alProductList which contains the id's of the Insurance Product's
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int deleteProduct(ArrayList alProductList) throws TTKException{
		Long iResult = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strDeleteProduct);
			cStmtObject.setString(1,TTKCommon.checkNull((String)alProductList.get(0)));  //Concatenated Product Sequence IDs.
			cStmtObject.setLong(2, (Long)alProductList.get(1));//user sequence id
			cStmtObject.registerOutParameter(3, Types.BIGINT);//out parameter which gives the number of records deleted
			cStmtObject.execute();
			iResult = cStmtObject.getLong(3);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl deleteProduct()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl deleteProduct()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		
		
		return iResult.intValue();
	}// End of deleteProduct(ArrayList alProductList)

	/**
	 * This method returns the ProductVO, which contains all the product details
	 * @param lngProdPolicySeqID the productpolicySequenceID for which the product Details has to be fetched
	 * @return ProductVO object which contains all the Product details
	 * @exception throws TTKException
	 */
	public ProductVO getProductDetails(long lngProdPolicySeqID) throws TTKException
	{
		
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ProductVO productVO = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject =  conn.prepareCall(strGetProduct);
			cStmtObject.setLong(1,lngProdPolicySeqID);

			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);

			if(rs != null){
				
				while (rs.next()) {
					
					productVO = new ProductVO();

					if (rs.getString("PRODUCT_SEQ_ID")!=null){
						productVO.setProdSeqId(new Long(rs.getLong("PRODUCT_SEQ_ID")));
					}//end of if (rs.getString("PRODUCT_SEQ_ID")!=null)

					if (rs.getString("PROD_POLICY_SEQ_ID")!=null){
						productVO.setProdPolicySeqID((rs.getLong("PROD_POLICY_SEQ_ID")));
						
					}//end of if (rs.getString("PROD_POLICY_SEQ_ID")!=null)

					productVO.setProdTypeId(TTKCommon.checkNull(rs.getString("PROD_GENERAL_TYPE_ID")));
					productVO.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
					productVO.setAuthorityProductCode(TTKCommon.checkNull(rs.getString("auth_product_code")));
					productVO.setProdDesc(TTKCommon.checkNull(rs.getString("PROD_DESC")));
					productVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					productVO.setCompanyAbbreviation(TTKCommon.checkNull(rs.getString("ABBREVATION_CODE")));
					productVO.setProductNetworkType(TTKCommon.checkNull(rs.getString("product_cat_type_id")));

					if (rs.getString("INS_SEQ_ID") != null){
						productVO.setInsSeqId(new Long(rs.getLong("INS_SEQ_ID")));
						
					}//end of if (rs.getString("INS_SEQ_ID") != null)

					productVO.setStatus(TTKCommon.checkNull(rs.getString("STATUSDESC")));
					productVO.setProdStatTypeId(TTKCommon.checkNull(rs.getString("PROD_STATUS_GENERAL_TYPE_ID")));
					productVO.setCardClearanceDays(rs.getString("CARD_CLEARANCE_DAYS")!=null ? new Long(rs.getLong("CARD_CLEARANCE_DAYS")):null);
					productVO.setClaimClearanceDays(rs.getString("CLAIM_CLEARANCE_DAYS")!=null ? new Long(rs.getLong("CLAIM_CLEARANCE_DAYS")):null);
					productVO.setPaClearanceHours(rs.getString("PA_CLEARANCE_HOURS")!=null ? new Long(rs.getLong("PA_CLEARANCE_HOURS")):null);
					productVO.setDischargeVoucherMandatoryYN(TTKCommon.checkNull(rs.getString("DV_REQD_GENERAL_TYPE_ID")));
					if(rs.getString("TEMPLATE_ID") != null){
						productVO.setTemplateID(new Long(rs.getLong("TEMPLATE_ID")));
			       Long TEMPLATE_ID  =rs.getLong("TEMPLATE_ID")!=0L?rs.getLong("TEMPLATE_ID"):0L;
			         productVO.setTemplateID(new Long(TEMPLATE_ID));
					}//end of if(rs.getString("TEMPLATE_ID") != null)
					productVO.setInsProductCode(TTKCommon.checkNull(rs.getString("INS_PRODUCT_CODE")));
					
					if(rs.getString("TENURE") != null){
						productVO.setTenure(new Integer(rs.getInt("TENURE")));
					}//end of if(rs.getString("TENURE") != null)
					
					productVO.setAuthLtrTypeID(TTKCommon.checkNull(rs.getString("AUTH_LETTER_GENERAL_TYPE_ID")));
					productVO.setStopPreAuthsYN(TTKCommon.checkNull(rs.getString("STOP_PREAUTH_YN")));
					productVO.setStopClaimsYN(TTKCommon.checkNull(rs.getString("STOP_CLAIM_YN")));
					productVO.setSurgeryMandtryID(TTKCommon.checkNull(rs.getString("SURGERY_GEN_TYPE_ID")));
					productVO.setopdClaimsYN(TTKCommon.checkNull(rs.getString("OPD_BENEFITS_YN")));//KOC 1286 for OPD Benefit
					productVO.setCashBenefitsYN(TTKCommon.checkNull(rs.getString("CBH_SUMINS_YN"))); // KOC 1270 for hospital cash benefit
					productVO.setConvCashBenefitsYN(TTKCommon.checkNull(rs.getString("CONV_SUMINS_YN"))); // KOC 1270 for hospital cash benefit
					//added for KOC-1273
					productVO.setCriticalBenefitYN(TTKCommon.checkNull(rs.getString("Critical_Sumins_Yn")));
					productVO.setSurvivalPeriodYN(TTKCommon.checkNull(rs.getString("SURVIVAL_PERIOD_YN")));
					 productVO.setSurvivalPeriodYN(TTKCommon.checkNull(rs.getString("SURVIVAL_PERIOD_YN")));
                    //1274A
                    productVO.setPatEnableYN(TTKCommon.checkNull(rs.getString("PAT_ALLOWED_YN")));
					productVO.setPatMailTo(TTKCommon.checkNull(rs.getString("PAT_TO_MAIL_ID")));
					productVO.setPatMailCC(TTKCommon.checkNull(rs.getString("PAT_CC_MAIL_ID")));
					productVO.setClmEnableYN(TTKCommon.checkNull(rs.getString("CLM_ALLOWED_YN")));
					productVO.setClmMailTo(TTKCommon.checkNull(rs.getString("CLM_TO_MAIL_ID")));
                    productVO.setClmMailCC(TTKCommon.checkNull(rs.getString("CLM_CC_MAIL_ID")));
                    productVO.setHealthAuthority(TTKCommon.checkNull(rs.getString("AUTHORITY_TYPE")));
			
                    productVO.setCoInsurance( TTKCommon.checkNull(rs.getInt("CO_INS")).toString());
                    productVO.setDeductable(TTKCommon.checkNull(rs.getString("DEDUCTIBLE")));
                    productVO.setClassRoomType(TTKCommon.checkNull(rs.getString("CLASS")));
                    productVO.setPlanType(TTKCommon.checkNull(rs.getString("PLAN")));
                    productVO.setMaternityYN(TTKCommon.checkNull(rs.getString("MATERNITY_YN")));
                    productVO.setMaternityCopay(TTKCommon.checkNull(rs.getString("MATERNITY_COPAY")));
                    productVO.setOpticalYN(TTKCommon.checkNull(rs.getString("OPTICAL_YN")));
                    productVO.setOpticalCopay(TTKCommon.checkNull(rs.getString("OPTICAL_COPAY")));
                    productVO.setDentalYN(TTKCommon.checkNull(rs.getString("DENTAL_YN")));
                    productVO.setDentalCopay(TTKCommon.checkNull(rs.getString("DENTAL_COPAY")));
                    productVO.setEligibility(TTKCommon.checkNull(rs.getString("ELIGIBILITY")));
                    productVO.setIpopServices(TTKCommon.checkNull(rs.getString("IP_OP_SERVICES")));
                    productVO.setPharmaceutical(TTKCommon.checkNull(rs.getString("PHARMACEUTICALS")));
                   productVO.setSMEproductYN(TTKCommon.checkNull(rs.getString("sme_chck_flg")));
                    
                    productVO.setVatPercent(rs.getDouble("VAT_PERCENT"));
                    
                    productVO.setRefundedYN(rs.getString("PREMIUM_REFUND_YN"));
                    productVO.setRefundedYNRemarks(rs.getString("PREMIUM_REFUND_REMK"));
                    
                  if(("".equals(rs.getString("PREMIUM_REFUND_YN"))|| (rs.getString("PREMIUM_REFUND_YN")==null)))
                    	 productVO.setRefundFalg("N");
                    else
                    productVO.setRefundFalg("Y");
                  productVO.setPlanMaternityCoPay(TTKCommon.checkNull(rs.getString("PLAN_MAT_COPAY")));
                  productVO.setPlanIpCoPay(TTKCommon.checkNull(rs.getString("PLAN_IP_COPAY")));
                  productVO.setPlanOpticalCoPay(TTKCommon.checkNull(rs.getString("PLAN_OPTICALCOPAY")));
                  productVO.setPlanOpCoPay(TTKCommon.checkNull(rs.getString("PLAN_OP_COPAY")));
                  productVO.setPlanDentalCoPay(TTKCommon.checkNull(rs.getString("PLAN_DENTAL_COPAY")));
                  productVO.setPlanOpConsultationCoPay(TTKCommon.checkNull(rs.getString("PLAN_OP_CONSUL_COPAY")));
                  productVO.setPlanPharmacyCoPay(TTKCommon.checkNull(rs.getString("PLAN_PHARMACYCOPAY")));
                  productVO.setProductSubtype(TTKCommon.checkNull(rs.getString("prod_sub_type")));
                  productVO.setTotalSumInsured(rs.getString("prod_sum_insured")!=null ? new BigDecimal(rs.getString("prod_sum_insured")):null);
                  System.out.println("select prod_sub_type ->"+rs.getString("prod_sub_type"));
                  System.out.println("select TOTAL_SUM_INSURED ->"+rs.getString("prod_sum_insured"));
				}//end of while(rs.next())
			}//end of if(rs != null)
			conn.commit();
			return productVO;
		
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
					log.error("Error while closing the Resultset in ProductDAOImpl getProductDetails()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getProductDetails()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getProductDetails()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getProductDetails(long lngProdPolicySeqID)

    /**
	 * This method saves the associated groups information to the product in to the database
	 * @param lngProductPolicySeqID Product Policy Sequence ID for which the user groups are associated
	 * @param strOfficeSeqID the assciated office sequence ID's
	 * @param lngUserSeqID the user adding the information
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int saveUserGroup(Long lngProductPolicySeqID, String strOfficeSeqID,Long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult = 0;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveUserGroup);
			cStmtObject.setLong(1,lngProductPolicySeqID);
			cStmtObject.setString(2, strOfficeSeqID);//Pipe Concatenated UserGroup Office Sequence ID's
			cStmtObject.setLong(3, lngUserSeqID);//User Sequence ID
			cStmtObject.registerOutParameter(4, Types.INTEGER);//out parameter which gives the number of records deleted
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl saveUserGroup()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl saveUserGroup()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// End of saveUserGroup(Long lngProductSeqID, String strOfficeSeqID,Long lngUserSeqID)

	/**
	 * This method returns the ArrayList of GroupVO's which are populated from the database
	 * @param lngProductSeqID the Product Sequence ID for which the user groups associated to
	 * @return ArrayList Which contains the GroupVO's which are associated with the particular product sequence id
	 * @exception throws TTKException
	 */
	public ArrayList getUserGroup(Long lngProductSeqID) throws TTKException	{
		Connection conn = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;
		GroupVO groupVO = null;
		ArrayList<Object> alResultList = new ArrayList<Object>();
		try
		{
			conn = ResourceManager.getConnection();
			cStmt = conn.prepareCall(strGetUserGroupp);
			cStmt.setLong(1,lngProductSeqID);
			cStmt.registerOutParameter(2, Types.OTHER);
		    cStmt.execute();
			rs=(ResultSet) cStmt.getObject(2);
		    if(rs != null){
				while (rs.next()) {
					groupVO = new GroupVO();
					groupVO.setGroupSeqID(rs.getString("GROUP_SEQ_ID")!=null ? new Long(rs.getLong("GROUP_SEQ_ID")):null);
					groupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					groupVO.setGroupBranchSeqID(rs.getString("GROUP_BRANCH_SEQ_ID")!=null ? new Long(rs.getLong("GROUP_BRANCH_SEQ_ID")):null);
					groupVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
					groupVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					groupVO.setOfficeCode(TTKCommon.checkNull(rs.getString("TPA_OFFICE_GENERAL_TYPE_ID")));
					groupVO.setInsCompName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					alResultList.add(groupVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
					log.error("Error while closing the Resultset in ProductDAOImpl getUserGroup()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmt != null)	cStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getUserGroup()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getUserGroup()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// End of getUserGroup(Long lngProductSeqID)

	 /**
	 * This method saves the associated groups information to the product in to the database
	 * @param lngProductPolicySeqID Product Policy Sequence ID for which the user groups are associated
	 * @param strOfficeSeqID the assciated office sequence ID's
	 * @param lngUserSeqID the user adding the information
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int copyProductRules(Long lngProductSeqID,Long lngProdPolicySeqId,Long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult = 0;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strCopyProduct);
			cStmtObject.setLong(1,lngProductSeqID);
			cStmtObject.setLong(2, lngProdPolicySeqId);
			cStmtObject.setLong(3, lngUserSeqID);//User Sequence ID
			cStmtObject.registerOutParameter(4, Types.INTEGER);//out parameter which gives the number of records deleted
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl copyProductRules()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl copyProductRules()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// End of copyProductRules(Long lngProductSeqID, String strOfficeSeqID,Long lngUserSeqID)
	
	/**
     * This method saves the associated procedure information to the insurance company into the database
     * @param alAssValues ArrayList object which contains the form values
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int getAssociateExecute(ArrayList alAssValues) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	int iResult=0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject =  conn.prepareCall(strAssInsProduct);
			cStmtObject.setLong(1,(Long)alAssValues.get(0));  //Concatenated Policy Sequence IDs.
			cStmtObject.setString(2, TTKCommon.checkNull((String)alAssValues.get(1)));//head office flag
			if(!TTKCommon.checkNull((String)alAssValues.get(2)).equals(""))
			{
				//cStmtObject.setTimestamp(3, new Timestamp((Long)alAssValues.get(2)));//head office effective date
				cStmtObject.setTimestamp(3, new Timestamp(TTKCommon.getUtilDate((String)alAssValues.get(2)).getTime()));
				//cStmtObject.setTimestamp(3, new Timestamp((Long)alAssValues.get(2)));
			}//end of if(alAssValues.get(2) != null)
			else
			{
				cStmtObject.setTimestamp(3, null);
			}//end of else
			cStmtObject.setString(4, TTKCommon.checkNull((String)alAssValues.get(3)));//reginal office flag
			cStmtObject.setString(5, TTKCommon.checkNull((String)alAssValues.get(4)));//pipe concatenated list OF INS_SEQ_IDS
			if(!TTKCommon.checkNull((String)alAssValues.get(5)).equals(""))
			{
				//cStmtObject.setTimestamp(6, new Timestamp((Long)alAssValues.get(5)));//reginal office effective date
				cStmtObject.setTimestamp(6, new Timestamp(TTKCommon.getUtilDate((String)alAssValues.get(5)).getTime()));
				//cStmtObject.setTimestamp(6, new Timestamp((Long)alAssValues.get(5)));
			}//end of if(alAssValues.get(5) != null)
			else
			{
				cStmtObject.setTimestamp(6, null);
			}//end of else
			cStmtObject.setString(7, TTKCommon.checkNull((String)alAssValues.get(6)));//divisional office flag
			cStmtObject.setString(8, TTKCommon.checkNull((String)alAssValues.get(7)));//pipe concatenated list OF INS_SEQ_IDS
			if(!TTKCommon.checkNull((String)alAssValues.get(8)).equals(""))
			{
				//cStmtObject.setTimestamp(9, new Timestamp((Long)alAssValues.get(8)));//divisional office effective date
				cStmtObject.setTimestamp(9, new Timestamp(TTKCommon.getUtilDate((String)alAssValues.get(8)).getTime()));
			}//end of if(alAssValues.get(8) != null)
			else
			{
				cStmtObject.setTimestamp(9, null);
			}//end of else
			cStmtObject.setString(10, TTKCommon.checkNull((String)alAssValues.get(9)));//branch office flag
			cStmtObject.setString(11, TTKCommon.checkNull((String)alAssValues.get(10)));//pipe concatenated list OF INS_SEQ_IDS
			if(!TTKCommon.checkNull((String)alAssValues.get(11)).equals(""))
			{
				cStmtObject.setTimestamp(12, new Timestamp(TTKCommon.getUtilDate((String)alAssValues.get(11)).getTime()));
				//cStmtObject.setTimestamp(12, new Timestamp((Long)alAssValues.get(11)));//branch office effective date
			}//end of if(alAssValues.get(11) != null)
			else
			{
				cStmtObject.setTimestamp(12, null);
			}//end of if(alAssValues.get(11) != null)
			cStmtObject.setString(13, TTKCommon.checkNull((String)alAssValues.get(12)));//enroll types and commissions with pipe
			//cStmtObject.setLong(14, (Long)alAssValues.get(13));//updated by
			cStmtObject.setString(14,(String) alAssValues.get(13).toString());
			cStmtObject.registerOutParameter(15, Types.INTEGER);//out parameter which gives the number of records deleted
			cStmtObject.execute();
			iResult=cStmtObject.getInt(15);
			conn.commit();
		    
		}catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl saveUserGroup()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl saveUserGroup()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//public ArrayList getAssociateExecute(ArrayList alSearchObjects)
    
    /**
     * This method saves the Prod Policy Limit information for the corresponding Enrollment Type
     * @param alProdPolicyLimit ArrayList object which contains the form values
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int saveProdPolicyLimit(ArrayList alProdPolicyLimit) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        int iResult=0;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strSaveProdPolicyLimit);
			cStmtObject.setLong(1,(Long)alProdPolicyLimit.get(0));
			cStmtObject.setString(2,(String)alProdPolicyLimit.get(1));
			cStmtObject.setLong(3,(Long)alProdPolicyLimit.get(2));
			cStmtObject.registerOutParameter(4, Types.BIGINT);//out parameter which gives the number of records inserted/updated
			cStmtObject.execute();
			iResult = (int) cStmtObject.getLong(4);
			conn.commit();
		}//end of try
    	catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl saveProdPolicyLimit()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl saveProdPolicyLimit()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//end of saveProdPolicyLimit(ArrayList alProdPolicyLimit)
    
    /**
     * This method returns the ArrayList, which contains the ProdPolicyLimitVO's which are populated from the database
     * @param lngProdPolicySeqID long value which contains ProdPolicySeqID
     * @return ArrayList of ProdPolicyLimitVO'S object's which contains the Buffer details
     * @exception throws TTKException
     */
    public ArrayList<Object> getProdPolicyLimit(long lngProdPolicySeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        Collection<Object> resultList = new ArrayList<Object>();
        ProdPolicyLimitVO prodPolicyLimitVO = null;
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject =  conn.prepareCall(strGetProdPolicyLimit);
			
			cStmtObject.setLong(1,lngProdPolicySeqID);
			cStmtObject.registerOutParameter(2,Types.OTHER);//OracleTypes.CURSOR
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					prodPolicyLimitVO = new ProdPolicyLimitVO();
					if(rs.getString("INS_PROD_POLICY_LIMITS_SEQ_ID") != null){
						prodPolicyLimitVO.setLimitSeqID(new Long(rs.getLong("INS_PROD_POLICY_LIMITS_SEQ_ID")));
					}//end of if(rs.getString("INS_PROD_POLICY_LIMITS_SEQ_ID") != null)
					else{
						prodPolicyLimitVO.setLimitSeqID(null);
					}//end of else
					
					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						prodPolicyLimitVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
					
					prodPolicyLimitVO.setEnrolTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					prodPolicyLimitVO.setEnrolDesc(TTKCommon.checkNull(rs.getString("ENROL_DESCRIPTION")));
					prodPolicyLimitVO.setFlag(TTKCommon.checkNull(rs.getString("V_FLAG")));
					
					if(rs.getString("RENEWAL_INTERVAL_DAYS") != null){
						prodPolicyLimitVO.setRenewalDays(new Integer(rs.getInt("RENEWAL_INTERVAL_DAYS")));
					}//end of if(rs.getString("RENEWAL_INTERVAL_DAYS") != null)
					else{
						prodPolicyLimitVO.setRenewalDays(null);
					}//end of else
					resultList.add(prodPolicyLimitVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
					log.error("Error while closing the Resultset in ProductDAOImpl getProdPolicyLimit()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getProdPolicyLimit()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getProdPolicyLimit()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
    }//end of getProdPolicyLimit(long lngProdPolicySeqID)

    
    public ArrayList<Object> getCardTemplates(String inscode) throws TTKException{
		
    	Connection conn = null;
    	
    	 ProdPolicyLimitVO inscmpvo = null;
    	CallableStatement cStmtObject=null;
    	
    	String temName=null;ResultSet rs1=null;
    	CacheObject cacheObject = null;
        ArrayList<CacheObject> alCardTempList = new ArrayList<CacheObject>();
      
        ArrayList<Object> allList = new ArrayList<Object>();
    	try {
    		 conn = ResourceManager.getConnection();
    		 cStmtObject = conn.prepareCall(strCardTempList);
    			
    		 cStmtObject.setLong(1,inscode!="null"?Long.parseLong(inscode): new Long(0));
 			 cStmtObject.registerOutParameter(2,Types.VARCHAR);
 			 cStmtObject.registerOutParameter(3,Types.OTHER);
 			
 			 cStmtObject.execute();
 			
 			temName = cStmtObject.getString(2);
 			inscmpvo= new ProdPolicyLimitVO();
 			inscmpvo.setDefault_template(temName);
 			rs1 = (java.sql.ResultSet)cStmtObject.getObject(3);
 			
    		if(rs1!=null){
    			while(rs1.next()){
    				
    				cacheObject = new CacheObject();
    				
    				cacheObject.setCacheId((rs1.getString("TEMPLATE_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs1.getString("TEMPLATE_NAME")));
                    
                    alCardTempList.add(cacheObject);
    			}
    		}
    		allList.add(alCardTempList);
    		allList.add(temName);
                 
    		   conn.commit();
    			return allList;
    		
		} 
    	 catch (SQLException sqlExp)
         {
             throw new TTKException(sqlExp, "product");
         }
    	
    	catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
    	
    	 finally
 		{
 			/* Nested Try Catch to ensure resource closure */ 
 			try // First try closing the result set
 			{
 				try
 				{
 					if (rs1 != null) rs1.close();
 				}//end of try
 				catch (SQLException sqlExp)
 				{
 					log.error("Error while closing the Resultset in ProductDAOImpl getCardTemplates()",sqlExp);
 					throw new TTKException(sqlExp, "product");
 				}//end of catch (SQLException sqlExp)
 				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
 				{
 					try
 					{
 						if (cStmtObject != null) cStmtObject.close();
 					}//end of try
 					catch (SQLException sqlExp)
 					{
 						log.error("Error while closing the Statement in ProductDAOImpl getCardTemplates()",sqlExp);
 						throw new TTKException(sqlExp, "product");
 					}//end of catch (SQLException sqlExp)
 					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
 					{
 						try
 						{
 							if(conn != null) conn.close();
 						}//end of try
 						catch (SQLException sqlExp)
 						{
 							log.error("Error while closing the Connection in ProductDAOImpl getCardTemplates()",sqlExp);
 							throw new TTKException(sqlExp, "product");
 						}//end of catch (SQLException sqlExp)
 					}//end of finally Connection Close
 				}//end of finally Statement Close
 			}//end of try
 			catch (TTKException exp)
 			{
 				throw new TTKException(exp, "product");
 			}//end of catch (TTKException exp)
 			finally // Control will reach here in anycase set null to the objects 
 			{
 				rs1 = null;
 				cStmtObject = null;
 				conn = null;
 			}//end of finally
 		}//end of finally
  		
	}
    
public ProdPolicyLimitVO getInsCmpCode(long linscode) throws TTKException{
		
	Connection conn = null;
	PreparedStatement pStmt1 = null;
	ResultSet rs1 = null;
    ProdPolicyLimitVO inscmpvo = null;
    CallableStatement cStmtObject=null;
	try {
		
		 conn = ResourceManager.getConnection();
		
			pStmt1 = conn.prepareStatement(strInsCmpCode);
    		
		
			pStmt1.setLong(1,linscode);
			
		rs1=pStmt1.executeQuery();
		
		if(rs1!=null){
			while(rs1.next()){
				
				inscmpvo = new ProdPolicyLimitVO();
				
				inscmpvo.setIns_Cmp_code(rs1.getString("ins_comp_code_number"));
			}
		}
		 return inscmpvo;
		
		
	} 
	 catch (SQLException sqlExp)
     {
         throw new TTKException(sqlExp, "product");
     }
	
	catch (Exception exp)
	{
		throw new TTKException(exp, "product");
	}//end of catch (Exception exp)
	
	 finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getInsCmpCode()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt1 != null) pStmt1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getInsCmpCode()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getInsCmpCode()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs1 = null;
				pStmt1 = null;
				conn = null;
			}//end of finally
		}//end of finally
		
	}

    

	/*public static void main(String a[]) throws Exception {
		//ProductDAOImpl productDAO = new ProductDAOImpl();
		//getAssociateExecute
		
		ArrayList alSearchCriteria = new ArrayList();
		alSearchCriteria.add("9211");
		alSearchCriteria.add("|110|");
		alSearchCriteria.add(null);
		alSearchCriteria.add("");
		alSearchCriteria.add("");//5
		//alSearchCriteria.add(new Timestamp((Long)new Date().getTime()));
		alSearchCriteria.add(null);
		alSearchCriteria.add("");
		alSearchCriteria.add("");
		alSearchCriteria.add(null);
		alSearchCriteria.add("");//10
		alSearchCriteria.add("");
		alSearchCriteria.add(null);
		alSearchCriteria.add("|IND|7.7|");
		alSearchCriteria.add("1");
		int i = productDAO.getAssociateExecute(alSearchCriteria);
		
		productDAO.getSynchPolicyList(alSearchCriteria);
		String strSeqID = "|322561|";
		productDAO.synchronizeRule(strSeqID,new Long(320391));
		
		ArrayList<Object> alProdPolicyLimit = new ArrayList<Object>();
		alProdPolicyLimit.add(new Long(320391));
		//alProdPolicyLimit.add("||Y|IND|5||Y|ING|5||Y|COR|5|");
		alProdPolicyLimit.add("|1|Y|IND|6|2|Y|ING|7|3|N|COR|5|");
		alProdPolicyLimit.add(new Long(56503));
		productDAO.saveProdPolicyLimit(alProdPolicyLimit);
		
		//productDAO.getProdPolicyLimit(new Long(320391));
	}//end of main
*/
    
    
    //added for baja enhan
    public ArrayList<Object> getProdPolicyEscalateLimit(long lngProdPolicySeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        Collection<Object> resultList = new ArrayList<Object>();
        EscalationLimitVO escalationLimitVO = null;
        EscalationLimitVO escalationLimitVO1 = null;
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetProdPolicyEscalateLimit);
			
			cStmtObject.setLong(1,lngProdPolicySeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(3);
			
			if(rs != null){
				while(rs.next()){
                                    
					escalationLimitVO = new EscalationLimitVO();
					
					if(rs.getString("PROD_POL_ESC_SEQ_ID") != null){
						escalationLimitVO.setLimitSeqID(new Long(rs.getLong("PROD_POL_ESC_SEQ_ID")));
					}//end of if(rs.getString("INS_PROD_POLICY_LIMITS_SEQ_ID") != null)
					else{
						escalationLimitVO.setLimitSeqID(null);
					}//end of else
					
					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						escalationLimitVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
					
					escalationLimitVO.setPatClmTypeID(TTKCommon.checkNull(rs.getString("PAT_CLM_TYPE")));
                    escalationLimitVO.setFlag(TTKCommon.checkNull(rs.getString("FREQ_FLAG")));
					escalationLimitVO.setRemType(TTKCommon.checkNull(rs.getString("REMAINDER_TYPE")));
					if(rs.getString("REMAINDER_VALUE") != null){
						escalationLimitVO.setEscalateDays(new Integer(rs.getInt("REMAINDER_VALUE")));
					}//end of if(rs.getString("RENEWAL_INTERVAL_DAYS") != null)
					else{
						escalationLimitVO.setEscalateDays(null);
					}//end of else
					
					resultList.add(escalationLimitVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
			
		/*	if(rs1 != null){
				while(rs1.next()){
					escalationLimitVO1 = new EscalationLimitVO();
					
					if(rs1.getString("PROD_POL_ESC_SEQ_ID") != null){
						escalationLimitVO1.setLimitSeqID(new Long(rs1.getLong("PROD_POL_ESC_SEQ_ID")));
					}//end of if(rs.getString("INS_PROD_POLICY_LIMITS_SEQ_ID") != null)
					else{
						escalationLimitVO1.setLimitSeqID(null);
					}//end of else
					
					if(rs1.getString("PROD_POLICY_SEQ_ID") != null){
						escalationLimitVO1.setProdPolicySeqID(new Long(rs1.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
					
					escalationLimitVO1.setPatClmTypeID(TTKCommon.checkNull(rs1.getString("PAT_CLM_TYPE")));
                    escalationLimitVO1.setFlag(TTKCommon.checkNull(rs1.getString("FREQ_FLAG")));
					escalationLimitVO1.setRemType(TTKCommon.checkNull(rs1.getString("REMAINDER_TYPE")));
					if(rs1.getString("REMAINDER_VALUE") != null){
						escalationLimitVO1.setEscalateDays(new Integer(rs1.getInt("REMAINDER_VALUE")));
					}//end of if(rs.getString("RENEWAL_INTERVAL_DAYS") != null)
					else{
						escalationLimitVO1.setEscalateDays(null);
					}//end of else
					
					resultList.add(escalationLimitVO1);
				}//end of while(rs.next())
			}//end of if(rs != null)
*/			
		
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getProdPolicyLimit()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getProdPolicyLimit()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getProdPolicyLimit()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
    }//end of getProdPolicyLimit(long lngProdPolicySeqID)
    
    
    public ArrayList<Object> getProdPolicyEscalateLimitclm(long lngProdPolicySeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        Collection<Object> resultList = new ArrayList<Object>();
        EscalationLimitVO escalationLimitVO = null;
        EscalationLimitVO escalationLimitVO1 = null;
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetProdPolicyEscalateLimit);
			
			cStmtObject.setLong(1,lngProdPolicySeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(3);
		
		/*	if(rs != null){
				while(rs.next()){
					log.info("here");
					escalationLimitVO = new EscalationLimitVO();
					
					if(rs.getString("PROD_POL_ESC_SEQ_ID") != null){
						escalationLimitVO.setLimitSeqID(new Long(rs.getLong("PROD_POL_ESC_SEQ_ID")));
					}//end of if(rs.getString("INS_PROD_POLICY_LIMITS_SEQ_ID") != null)
					else{
						escalationLimitVO.setLimitSeqID(null);
					}//end of else
					
					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						escalationLimitVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
					
					escalationLimitVO.setPatClmTypeID(TTKCommon.checkNull(rs.getString("PAT_CLM_TYPE")));
                    escalationLimitVO.setFlag(TTKCommon.checkNull(rs.getString("FREQ_FLAG")));
					escalationLimitVO.setRemType(TTKCommon.checkNull(rs.getString("REMAINDER_TYPE")));
					if(rs.getString("REMAINDER_VALUE") != null){
						escalationLimitVO.setEscalateDays(new Integer(rs.getInt("REMAINDER_VALUE")));
					}//end of if(rs.getString("RENEWAL_INTERVAL_DAYS") != null)
					else{
						escalationLimitVO.setEscalateDays(null);
					}//end of else
					
					resultList.add(escalationLimitVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			*/
			
		if(rs1 != null){
				while(rs1.next()){
					
				
					escalationLimitVO1 = new EscalationLimitVO();
					
					if(rs1.getString("PROD_POL_ESC_SEQ_ID") != null){
						escalationLimitVO1.setLimitSeqID(new Long(rs1.getLong("PROD_POL_ESC_SEQ_ID")));
					}//end of if(rs.getString("INS_PROD_POLICY_LIMITS_SEQ_ID") != null)
					else{
						escalationLimitVO1.setLimitSeqID(null);
					}//end of else
					
					if(rs1.getString("PROD_POLICY_SEQ_ID") != null){
						escalationLimitVO1.setProdPolicySeqID(new Long(rs1.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
					
					escalationLimitVO1.setPatClmTypeID(TTKCommon.checkNull(rs1.getString("PAT_CLM_TYPE")));
                    escalationLimitVO1.setFlag(TTKCommon.checkNull(rs1.getString("FREQ_FLAG")));
					escalationLimitVO1.setRemType(TTKCommon.checkNull(rs1.getString("REMAINDER_TYPE")));
					if(rs1.getString("REMAINDER_VALUE") != null){
						escalationLimitVO1.setEscalateDays(new Integer(rs1.getInt("REMAINDER_VALUE")));
					}//end of if(rs.getString("RENEWAL_INTERVAL_DAYS") != null)
					else{
						escalationLimitVO1.setEscalateDays(null);
					}//end of else
					
					resultList.add(escalationLimitVO1);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
		
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getProdPolicyLimit()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getProdPolicyLimit()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getProdPolicyLimit()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
    }//end of getProdPolicyLimit(long lngProdPolicySeqID)
    public int saveProdPolicyEscalateLimit(ArrayList alProdPolicyEscalateLimit) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	int iResult=0;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveProdPolicyEscalateLimit);
		
			cStmtObject.setLong(1,(Long)alProdPolicyEscalateLimit.get(0));
			cStmtObject.setString(2,(String)alProdPolicyEscalateLimit.get(1));
			cStmtObject.setLong(3,(Long)alProdPolicyEscalateLimit.get(2));
			cStmtObject.registerOutParameter(4, Types.INTEGER);//out parameter which gives the number of records inserted/updated
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);
			conn.commit();
		}//end of try
    	catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl saveProdPolicyLimit()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl saveProdPolicyLimit()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//end of saveProdPolicyLimit(ArrayList alProdPolicyLimit)
    
    public long savePremiumConfiguration(ProdPolicyLimitVO prodPolicyLimitVO) throws TTKException
    {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	long iResult=0;
    	long ruleSeqid=0L;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strSavePremiumConfiguration);
			
			cStmtObject.setLong(1,prodPolicyLimitVO.getPremiumConfigSwrId()==null?0:prodPolicyLimitVO.getPremiumConfigSwrId());
			
		if(prodPolicyLimitVO.getProdPolicySeqID()!=null)
		{
			cStmtObject.setLong(2,prodPolicyLimitVO.getProdPolicySeqID());
		}
		else
		{
			//cStmtObject.setString(2,null);
			cStmtObject.setLong(2,0);
			
		}
		if(prodPolicyLimitVO.getPolicySeqId()!=null)
		{
			cStmtObject.setLong(3,prodPolicyLimitVO.getPolicySeqId());
			
		}
		else
		{
			//cStmtObject.setString(3,null);
			cStmtObject.setLong(3,0);
		}
			
		if(prodPolicyLimitVO.getMinAge()==null){
			cStmtObject.setLong(4,0);
		}else{
			 cStmtObject.setLong(4,prodPolicyLimitVO.getMinAge());
		}
		 
		if(prodPolicyLimitVO.getMaxAge()==null){
			cStmtObject.setLong(5,0);
		}else{
			 cStmtObject.setLong(5,prodPolicyLimitVO.getMaxAge());
		}
			cStmtObject.setString(6,prodPolicyLimitVO.getMaritalStatus());
			cStmtObject.setString(7,prodPolicyLimitVO.getGender());
			
			
		//	cStmtObject.setBigDecimal(8,prodPolicyLimitVO.getGrossPremium());
			
			
			/* if("1".equals(prodPolicyLimitVO.getCapitationYN())){
					
		         	cStmtObject.setBigDecimal(8,prodPolicyLimitVO.getGrossPremium());
			    	}
			    	else{
				 	
					cStmtObject.setDouble(8,prodPolicyLimitVO.getGrossPremium1());
			    	}*/
			 
			if("NSF|YSP|NCH|NFR|YMO|OTH".equals(prodPolicyLimitVO.getRelation()))
			cStmtObject.setString(8,"|ALL|");
			else 
			{
				if("|ALL|".equals(prodPolicyLimitVO.getRelation()))
					cStmtObject.setString(8,"|"+"ALL"+"|");
				else
					cStmtObject.setString(8,"|"+prodPolicyLimitVO.getRelation()+"|");
			}
			cStmtObject.setString(9,prodPolicyLimitVO.getMode());
			cStmtObject.setString(10,prodPolicyLimitVO.getEditInsertFlag());
			
			
			if("1".equals(prodPolicyLimitVO.getCapitationYN())){
				
				cStmtObject.setString(11,prodPolicyLimitVO.getCapitationYN());
			}
			else{
				
				cStmtObject.setString(11,prodPolicyLimitVO.getCapitationtype());
			}
			if(!"".equals(prodPolicyLimitVO.getSalaryBand()))
			{
			 cStmtObject.setString(12,"|"+prodPolicyLimitVO.getSalaryBand().trim()+"|");
			}
			else
			{
				 cStmtObject.setString(12,"");
			}
			cStmtObject.setString(13,prodPolicyLimitVO.getAuthorityProductId());
			cStmtObject.setLong(14,prodPolicyLimitVO.getAddedBy());
			cStmtObject.setString(15,prodPolicyLimitVO.getUpdateRemarks());
			/*if(prodPolicyLimitVO.getCapitationYN()!=null)
			{
			cStmtObject.setString(12,prodPolicyLimitVO.getCapitationYN());
			}*/
			
			
			//cStmtObject.setDouble(16,prodPolicyLimitVO.getMedicalPremium());
			cStmtObject.setBigDecimal(16,new BigDecimal(prodPolicyLimitVO.getMedicalPremium()));
			//cStmtObject.setDouble(17,prodPolicyLimitVO.getMaternityPremium());
			cStmtObject.setBigDecimal(17,new BigDecimal(prodPolicyLimitVO.getMaternityPremium()));
			cStmtObject.setBigDecimal(18,new BigDecimal(prodPolicyLimitVO.getOpticalPremium()));
			cStmtObject.setBigDecimal(19, new BigDecimal(prodPolicyLimitVO.getDentalPremium()));
			cStmtObject.setBigDecimal(20,new BigDecimal(prodPolicyLimitVO.getWellnessPremium()));
			//cStmtObject.setDouble(18,prodPolicyLimitVO.getGrossPremium1());
			cStmtObject.setBigDecimal(21,new BigDecimal(prodPolicyLimitVO.getIpNetPremium()));
		//	System.out.println("v_insurer_margin::"+memberDetailVO.getInsurerMargin());
			cStmtObject.setBigDecimal(22,new BigDecimal(prodPolicyLimitVO.getInsurerMarginPER()));
	
			cStmtObject.setBigDecimal(23,new BigDecimal(prodPolicyLimitVO.getBrokerMarginPER()));
		
			//cStmtObject.setDouble(22,prodPolicyLimitVO.getTapMarginPER());
			
			cStmtObject.setBigDecimal(24,new BigDecimal(prodPolicyLimitVO.getReInsBrkMarginPER()));
	
			cStmtObject.setBigDecimal(25,new BigDecimal(prodPolicyLimitVO.getOtherMarginPER()));
		
			if(prodPolicyLimitVO.getNetPremium()!=0.0)cStmtObject.setBigDecimal(26,new BigDecimal(prodPolicyLimitVO.getNetPremium()));
			else cStmtObject.setBigDecimal(26,null);
			
			cStmtObject.setBigDecimal(27,new BigDecimal(prodPolicyLimitVO.getOpNetPremium()));
			
	     if("1".equals(prodPolicyLimitVO.getCapitationYN())){
	    	     cStmtObject.setString(28,prodPolicyLimitVO.getAsoFromDate());
				 cStmtObject.setString(29,prodPolicyLimitVO.getAsoToDate());
	     }
			else{
				cStmtObject.setString(28,prodPolicyLimitVO.getAsPlusFromDate());
				cStmtObject.setString(29,prodPolicyLimitVO.getAsPlusToDate());
			}
	     
	      cStmtObject.setString(30,prodPolicyLimitVO.getForward());
	      
	     cStmtObject.setString(31,prodPolicyLimitVO.getInsurerMarginAEDPER());
	     cStmtObject.setBigDecimal(32,new BigDecimal(prodPolicyLimitVO.getInsurerMarginAED()));
	     cStmtObject.setString(33,prodPolicyLimitVO.getBrokerMarginAEDPER());
	     cStmtObject.setBigDecimal(34,new BigDecimal(prodPolicyLimitVO.getBrokerMarginAED()));
	     cStmtObject.setString(35,prodPolicyLimitVO.getReInsBrkMarginAEDPER());
	     cStmtObject.setBigDecimal(36,new BigDecimal(prodPolicyLimitVO.getReInsBrkMarginAED()));
	     cStmtObject.setString(37,prodPolicyLimitVO.getOtherMarginAEDPER());
	     cStmtObject.setBigDecimal(38,new BigDecimal(prodPolicyLimitVO.getOtherMarginAED()));
	     cStmtObject.setString(40, prodPolicyLimitVO.getTpaFee());
	     cStmtObject.setString(42, prodPolicyLimitVO.getTpaFeeAEDPER());
	     cStmtObject.setString(41, prodPolicyLimitVO.getTpaActualFee());
	     cStmtObject.setString(43, prodPolicyLimitVO.getTpaFeeType());
	 	
	       if("1".equals(prodPolicyLimitVO.getCapitationYN())){
	    	    if(prodPolicyLimitVO.getGrossPremium1()==0.0)
	         	cStmtObject.setDouble(39,0.0);
	    	    else  cStmtObject.setDouble(39,prodPolicyLimitVO.getGrossPremium1());
	       }
		   else{
		    		cStmtObject.setDouble(39, prodPolicyLimitVO.getGrossPremium1());
		    	}
	       if(TTKCommon.checkNull(prodPolicyLimitVO.getTpaFee()).length()>0)
	        cStmtObject.setBigDecimal(40, new BigDecimal(prodPolicyLimitVO.getTpaFee()));
	       else 
	    	 cStmtObject.setBigDecimal(40, null);
	       if(TTKCommon.checkNull(prodPolicyLimitVO.getTpaActualFee()).length()>0)
		        cStmtObject.setBigDecimal(41, new BigDecimal(prodPolicyLimitVO.getTpaActualFee()));
		       else 
		    	 cStmtObject.setBigDecimal(41, null);
		       
	        cStmtObject.setString(42, prodPolicyLimitVO.getTpaFeeAEDPER());
	        cStmtObject.setString(43, prodPolicyLimitVO.getTpaFeeType());
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(44,Types.NUMERIC);
			    		cStmtObject.execute();
			    		iResult = cStmtObject.getLong(1);
			    		ruleSeqid= cStmtObject.getBigDecimal(44).longValue();
    		conn.commit();
    		
		}//end of try
    	catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl saveProdPolicyLimit()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl saveProdPolicyLimit()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return ruleSeqid;
    	
    }

/*	public ArrayList getPremiumConfiguration(ProductVO productVO) throws TTKException {
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
      
        Collection<Object> resultList = new ArrayList<Object>();
        PremiumConfigurationVO premiumConfigurationVO = null;
       
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetPremiumConfiguration);
			
			cStmtObject.setLong(1,productVO.getProdPolicySeqID());
			cStmtObject.setLong(2,productVO.getProdSeqId());
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			
		
	
			
		if(rs != null){
				while(rs.next()){
					premiumConfigurationVO.setSno(rs.getInt(1));
					premiumConfigurationVO.setMinAge(rs.getInt(2));
					premiumConfigurationVO.setMinAge(rs.getInt(3));
					premiumConfigurationVO.setMaritalStatus(rs.getString(4));
					premiumConfigurationVO.setRelation(rs.getString(6));
					premiumConfigurationVO.setGrossPremium(rs.getBigDecimal(7));
					premiumConfigurationVO.setGender(rs.getString(5));
				
					
					
					resultList.add(premiumConfigurationVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			

        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
		finally
		{
			 Nested Try Catch to ensure resource closure 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getProdPolicyLimit()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getProdPolicyLimit()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getProdPolicyLimit()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
		
	}*/

    public ArrayList<Object> getPremiumConfiguration(ProductVO productVO) throws TTKException {
    	
    	log.info("inside ProductDAOImpl getPremiumConfiguration()");
		// TODO Auto-generated method stub
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rsAso = null;
        ResultSet rsAsplus = null;
        PremiumConfigurationVO premiumConfigurationVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject =  conn.prepareCall(strGetPremiumConfiguration);
			
			   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			    Date date = new Date();  
			
			
			if(productVO.getPremiumConfigSeqID()!=null){
				cStmtObject.setLong(1,productVO.getPremiumConfigSeqID());
				System.out.println("PremiumConfigSeqID ->"+productVO.getPremiumConfigSeqID());
			}
			else cStmtObject.setLong(1,0);
			if(productVO.getProdPolicySeqID()!=null){
				cStmtObject.setLong(2,productVO.getProdPolicySeqID());
			System.out.println("ProdPolicySeqID->"+productVO.getProdPolicySeqID());	
			}
			else
				cStmtObject.setLong(2,0);

			if(productVO.getProdSeqId()!=null) {
				cStmtObject.setLong(3,productVO.getProdSeqId());
			System.out.println("getProdSeqId->"+productVO.getProdSeqId());	
			}
			else cStmtObject.setLong(3,0);

			if(productVO.getPolicySeqID()!=null){
			
					 cStmtObject.setLong(4,productVO.getPolicySeqID());
			System.out.println("getPolicySeqID->"+productVO.getPolicySeqID());	
			}
			else
				 cStmtObject.setLong(4,0);
			if(productVO.getMode()!=null){
			cStmtObject.setString(5,productVO.getMode());
			System.out.println("getMode->"+productVO.getMode());	
}
			else cStmtObject.setString(5,null);
		    //cStmtObject.setLong(3,productVO.getPolicySeqID());*/
			
			
			if(productVO.getPremiumEffectivePeriodAso()!=null){
				cStmtObject.setString(6,productVO.getAsoFromDate());
				cStmtObject.setString(7,productVO.getAsoToDate());
				System.out.println("getAsoFromDate->"+productVO.getAsoFromDate());
				System.out.println("getAsoToDate->"+productVO.getAsoToDate());
			}
			else{
				
				cStmtObject.setString(6,null);
				cStmtObject.setString(7,null);
			}
			if(productVO.getPremiumEffectivePeriodAsPlus()!=null){
				cStmtObject.setString(8,productVO.getAsPlusFromDate());
				cStmtObject.setString(9,productVO.getAsPlusToDate());
				System.out.println("getAsPlusFromDate->"+productVO.getAsPlusFromDate());
				System.out.println("getAsPlusToDate->"+productVO.getAsPlusToDate());

				
			}
			else{
				cStmtObject.setString(8,null);
				cStmtObject.setString(9,null);
			}
			
			cStmtObject.registerOutParameter(10,Types.OTHER);
			cStmtObject.registerOutParameter(11,Types.OTHER);
			cStmtObject.registerOutParameter(12,Types.OTHER);
			cStmtObject.registerOutParameter(13,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(11);
			rsAso = (java.sql.ResultSet)cStmtObject.getObject(12);
			rsAsplus = (java.sql.ResultSet)cStmtObject.getObject(13);
		/*if(rs != null){
				while(rs.next()){
					System.out.println("--------------- rs data coming ----------------------------- ");
					premiumConfigurationVO=new PremiumConfigurationVO();
					premiumConfigurationVO.setPremiumConfigSeqId(rs.getLong(1));
					premiumConfigurationVO.setSno(rs.getInt(2));
					premiumConfigurationVO.setProductName(rs.getString(3));
					premiumConfigurationVO.setMinAge(rs.getInt(4));
					premiumConfigurationVO.setMaxAge(rs.getInt(5));
					premiumConfigurationVO.setMaritalStatus(rs.getString(6));
					premiumConfigurationVO.setRelation(rs.getString(8));
					//premiumConfigurationVO.setGrossPremium(rs.getBigDecimal(9));
					premiumConfigurationVO.setGender(rs.getString(7));
					premiumConfigurationVO.setCapitationtype(rs.getString("CAPITATION_DESC"));
					premiumConfigurationVO.setCapitationYN(rs.getString("CAPITATION_YN"));
					premiumConfigurationVO.setImageName("DeleteIcon");
					premiumConfigurationVO.setImageTitle("Dlete");
					premiumConfigurationVO.setDeleteTitle("Delete");
					premiumConfigurationVO.setAuditLog("View");

					premiumConfigurationVO.setMedicalPremium(rs.getDouble("medical_prem"));
					premiumConfigurationVO.setMaternityPremium(rs.getDouble("maternity_prem"));
					premiumConfigurationVO.setOpticalPremium(rs.getDouble("optical_prem"));
					premiumConfigurationVO.setDentalPremium(rs.getDouble("dental_prem"));
					premiumConfigurationVO.setWellnessPremium(rs.getDouble("wellness_prem"));
					
					if("1".equals(rs.getString("CAPITATION_YN"))){
						premiumConfigurationVO.setGrossPremium(rs.getBigDecimal(9));
						premiumConfigurationVO.setGrossPremium1(rs.getBigDecimal(9).doubleValue());
						premiumConfigurationVO.setAsoFromDate(rs.getString("srch_as_date1"));
						premiumConfigurationVO.setAsoToDate(rs.getString("srch_as_date2"));
					}
					else{
						premiumConfigurationVO.setGrossPremium1(rs.getBigDecimal(9).doubleValue());
					}
					
					premiumConfigurationVO.setSalaryBand(rs.getString("SALARY_BAND"));
					premiumConfigurationVO.setAuthorityProductId(rs.getString("AUTH_PRODUCT_CODE"));
					premiumConfigurationVO.setUpdatedRemarks(rs.getString("updated_remarks"));
					
					
					premiumConfigurationVO.setIpNetPremium(rs.getDouble("ip_net_prem"));
					premiumConfigurationVO.setInsurerMargin(rs.getDouble("insurer_margin"));
					premiumConfigurationVO.setInsurerMarginPER(rs.getDouble("insurer_margin"));
					premiumConfigurationVO.setBrokerMargin(rs.getDouble("broker_margin"));
					premiumConfigurationVO.setBrokerMarginPER(rs.getDouble("broker_margin"));
					//premiumConfigurationVO.setTapMargin(rs.getDouble("tpa_margin"));
					//premiumConfigurationVO.setTapMarginPER(rs.getDouble("tpa_margin"));
					premiumConfigurationVO.setReInsBrkMargin(rs.getDouble("reins_brk_margin"));
					premiumConfigurationVO.setReInsBrkMarginPER(rs.getDouble("reins_brk_margin"));
					premiumConfigurationVO.setOtherMargin(rs.getDouble("other_margin"));
					premiumConfigurationVO.setOtherMarginPER(rs.getDouble("other_margin"));
					premiumConfigurationVO.setNetPremium(rs.getDouble("net_prem"));
					premiumConfigurationVO.setInsurerMarginAED(rs.getDouble("ins_mr_aed"));
					premiumConfigurationVO.setBrokerMarginAED(rs.getDouble("brkr_mr_aed"));
					premiumConfigurationVO.setReInsBrkMarginAED(rs.getDouble("reins_brk_margin_aed"));
					premiumConfigurationVO.setOtherMarginAED(rs.getDouble("other_margin_aed"));
					premiumConfigurationVO.setOpNetPremium(rs.getDouble("op_net_prem"));
					
					premiumConfigurationVO.setInsurerMarginAEDPER(rs.getString("insurer_margin_flag"));
					premiumConfigurationVO.setBrokerMarginAEDPER(rs.getString("broker_margin_flag"));
					premiumConfigurationVO.setReInsBrkMarginAEDPER(rs.getString("reins_brk_margin_flag"));
					premiumConfigurationVO.setOtherMarginAEDPER(rs.getString("other_margin_flag"));
					
					if("PER".equals(rs.getString("insurer_margin_flag")))
						premiumConfigurationVO.setInsurerMargin(rs.getDouble("insurer_margin"));
					
					else
						premiumConfigurationVO.setInsurerMargin(rs.getDouble("ins_mr_aed"));
					
					
					if("PER".equals(rs.getString("broker_margin_flag")))
						premiumConfigurationVO.setBrokerMargin(rs.getDouble("broker_margin"));
					
					else
						premiumConfigurationVO.setBrokerMargin(rs.getDouble("brkr_mr_aed"));
					
					
					if("PER".equals(rs.getString("other_margin_flag")))
						premiumConfigurationVO.setOtherMargin(rs.getDouble("other_margin"));
					
					else
						premiumConfigurationVO.setOtherMargin(rs.getDouble("other_margin_aed"));
					premiumConfigurationVO.setTpaFee(TTKCommon.checkNull(rs.getString("TPA_FEE")));
					premiumConfigurationVO.setTpaFeeType(TTKCommon.checkNull(rs.getString("TPA_FEE_TYPE")));
					premiumConfigurationVO.setTpaFeeAEDPER(TTKCommon.checkNull(rs.getString("TPA_FEE_FLAG")));
					premiumConfigurationVO.setTpaActualFee(TTKCommon.checkNull(rs.getString("TPA_FEE_AED")));
					resultList.add(premiumConfigurationVO);
					
				}//end of while(rs.next())
			}*///end of if(rs != null)
		
		if(rs1 != null){
			while(rs1.next()){
				System.out.println("--------------- rs1 data coming ----------------------------- ");
				premiumConfigurationVO=new PremiumConfigurationVO();
				premiumConfigurationVO.setPremiumConfigSeqId(rs1.getLong(1));
				premiumConfigurationVO.setSno(rs1.getInt(2));
				premiumConfigurationVO.setProductName(rs1.getString(3));
				premiumConfigurationVO.setMinAge(rs1.getInt(4));
				premiumConfigurationVO.setMaxAge(rs1.getInt(5));
				premiumConfigurationVO.setMaritalStatus(rs1.getString(6));
				premiumConfigurationVO.setRelation(rs1.getString(8));
				//premiumConfigurationVO.setGrossPremium(rs.getBigDecimal(9));
				premiumConfigurationVO.setGender(rs1.getString(7));
				premiumConfigurationVO.setCapitationtype(rs1.getString("CAPITATION_DESC"));
				premiumConfigurationVO.setCapitationYN(rs1.getString("CAPITATION_YN"));
				premiumConfigurationVO.setImageName("DeleteIcon");
				premiumConfigurationVO.setImageTitle("Dlete");
				premiumConfigurationVO.setDeleteTitle("Delete");
				premiumConfigurationVO.setAuditLog("View");

				premiumConfigurationVO.setMedicalPremium(rs1.getDouble("medical_prem"));
				premiumConfigurationVO.setMaternityPremium(rs1.getDouble("maternity_prem"));
				premiumConfigurationVO.setOpticalPremium(rs1.getDouble("optical_prem"));
				premiumConfigurationVO.setDentalPremium(rs1.getDouble("dental_prem"));
				premiumConfigurationVO.setWellnessPremium(rs1.getDouble("wellness_prem"));
				
				if("1".equals(rs1.getString("CAPITATION_YN"))){
					premiumConfigurationVO.setGrossPremium(rs1.getBigDecimal(9));
					premiumConfigurationVO.setGrossPremium1(rs1.getBigDecimal(9).doubleValue());
					
				}
				else{
					premiumConfigurationVO.setGrossPremium(rs1.getBigDecimal(9));
					premiumConfigurationVO.setGrossPremium1(rs1.getBigDecimal(9).doubleValue());
				}
				
				premiumConfigurationVO.setSalaryBand(rs1.getString("SALARY_BAND"));
				premiumConfigurationVO.setAuthorityProductId(rs1.getString("AUTH_PRODUCT_CODE"));
				premiumConfigurationVO.setUpdatedRemarks(rs1.getString("updated_remarks"));
				
				
				premiumConfigurationVO.setIpNetPremium(rs1.getDouble("ip_net_prem"));
				premiumConfigurationVO.setInsurerMarginPER(rs1.getDouble("insurer_margin"));
				premiumConfigurationVO.setBrokerMarginPER(rs1.getDouble("broker_margin"));
				//premiumConfigurationVO.setTapMargin(rs.getDouble("tpa_margin"));
				//premiumConfigurationVO.setTapMarginPER(rs.getDouble("tpa_margin"));
				premiumConfigurationVO.setReInsBrkMarginPER(rs1.getDouble("reins_brk_margin"));
				premiumConfigurationVO.setOtherMarginPER(rs1.getDouble("other_margin"));
				premiumConfigurationVO.setNetPremium(rs1.getDouble("net_prem"));
				premiumConfigurationVO.setInsurerMarginAED(rs1.getDouble("ins_mr_aed"));
				premiumConfigurationVO.setBrokerMarginAED(rs1.getDouble("brkr_mr_aed"));
				premiumConfigurationVO.setReInsBrkMarginAED(rs1.getDouble("reins_brk_margin_aed"));
				premiumConfigurationVO.setOtherMarginAED(rs1.getDouble("other_margin_aed"));
				premiumConfigurationVO.setOpNetPremium(rs1.getDouble("op_net_prem"));
				
				premiumConfigurationVO.setInsurerMarginAEDPER(rs1.getString("insurer_margin_flag"));
				premiumConfigurationVO.setBrokerMarginAEDPER(rs1.getString("broker_margin_flag"));
				premiumConfigurationVO.setReInsBrkMarginAEDPER(rs1.getString("reins_brk_margin_flag"));
				premiumConfigurationVO.setOtherMarginAEDPER(rs1.getString("other_margin_flag"));
				
				if("PER".equals(rs1.getString("insurer_margin_flag")))
					premiumConfigurationVO.setInsurerMargin(rs1.getDouble("insurer_margin"));
				
				else
					premiumConfigurationVO.setInsurerMargin(rs1.getDouble("ins_mr_aed"));
				
				
				if("PER".equals(rs1.getString("broker_margin_flag")))
					premiumConfigurationVO.setBrokerMargin(rs1.getDouble("broker_margin"));
				
				else
					premiumConfigurationVO.setBrokerMargin(rs1.getDouble("brkr_mr_aed"));
				
				
				if("PER".equals(rs1.getString("reins_brk_margin_flag")))
					premiumConfigurationVO.setReInsBrkMargin(rs1.getDouble("reins_brk_margin"));
				
				else
					premiumConfigurationVO.setReInsBrkMargin(rs1.getDouble("reins_brk_margin_aed"));
				
				
				if("PER".equals(rs1.getString("other_margin_flag")))
					premiumConfigurationVO.setOtherMargin(rs1.getDouble("other_margin"));
				
				else
					premiumConfigurationVO.setOtherMargin(rs1.getDouble("other_margin_aed"));
				premiumConfigurationVO.setTpaFee(TTKCommon.checkNull(rs1.getString("TPA_FEE")));
				premiumConfigurationVO.setTpaFeeType(TTKCommon.checkNull(rs1.getString("TPA_FEE_TYPE")));
				premiumConfigurationVO.setTpaFeeAEDPER(TTKCommon.checkNull(rs1.getString("TPA_FEE_FLAG")));
				premiumConfigurationVO.setTpaActualFee(TTKCommon.checkNull(rs1.getString("TPA_FEE_AED")));
				resultList.add(premiumConfigurationVO);
			}//end of while(rs.next())
		}//end of if(rs != null)
		}//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
					if (rs1 != null) rs1.close();
					if (rsAso != null) rsAso.close();
					if (rsAsplus != null) rsAsplus.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getProdPolicyLimit()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getProdPolicyLimit()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getProdPolicyLimit()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{  
				rs = null;
				rs1 = null;
				rsAso = null;
				rsAsplus = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
		
	}

	public int deletePremiumConfiguration(ProductVO productVO) throws TTKException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		int res = 0;
		try
		{
			conn=ResourceManager.getConnection();
			if(productVO.getMode().equals("Policies"))
		     pstmt=conn.prepareStatement("DELETE FROM INTX.TPA_PLAN_CONFIG WHERE RULE_SEQ_ID=?");
			else if(productVO.getMode().equals("Products"))
				   pstmt=conn.prepareStatement("DELETE FROM INTX.TPA_PLAN_CONFIG WHERE RULE_SEQ_ID=?");
		     pstmt.setLong(1,productVO.getPremiumConfigSeqID());
		      res=pstmt.executeUpdate();
		      
		}
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, null);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, null);
		}//end of catch (Exception exp)
		finally
		{
			if(pstmt!=null)
				try 
			    {
					pstmt.close();
				} 
			catch (SQLException sqlExp) {
					// TODO Auto-generated catch block
					sqlExp.printStackTrace();
					throw new TTKException(sqlExp, null);
	
				}
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in ProductDAOImpl getProdPolicyLimit()",sqlExp);
					throw new TTKException(sqlExp,null);
				}//end of catch (SQLException sqlExp)
		}
			
	}
		return res;
	}

	public int synchronizeRuleAllSchemes(long lngProdPolicySeqID) throws TTKException {
		// TODO Auto-generated method stub
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSynchronizeRuleAll);
			cStmtObject.setLong(1,lngProdPolicySeqID);
			cStmtObject.registerOutParameter(2,Types.BIGINT);
			cStmtObject.execute();
			iResult = (int)cStmtObject.getLong(2);
			
			if(cStmtObject!=null) cStmtObject.close();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveBenefitLimitSyncAll);
			cStmtObject.setLong(1,lngProdPolicySeqID);
			cStmtObject.registerOutParameter(2,Types.BIGINT);
			cStmtObject.execute();
			conn.commit();
		}//end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl synchronizeRule()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl synchronizeRule()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
	}

	
	public ArrayList<Object> getCapitationYNData(String premiumConfigSeqId) throws TTKException {
		// TODO Auto-generated method stub
		Long premiumConfigSeqIdd=Long.parseLong(premiumConfigSeqId);
		Connection conn = null;
		 PreparedStatement preparedstmt=null;
        ResultSet rs = null;
        PremiumConfigurationVO premiumConfigurationVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
       
        try{
        	conn = ResourceManager.getConnection();
      
         preparedstmt = conn.prepareStatement(strCapitationYNData);
 		
 		
         preparedstmt.setLong(1,premiumConfigSeqIdd);
			
		rs=preparedstmt.executeQuery();
		
			
		
	
			
		if(rs != null){
				while(rs.next()){
					premiumConfigurationVO=new PremiumConfigurationVO();
					premiumConfigurationVO.setPremiumConfigSeqId(rs.getLong(1));
					premiumConfigurationVO.setSno(rs.getInt(2));
					premiumConfigurationVO.setProductName(rs.getString(3));
					premiumConfigurationVO.setMinAge(rs.getInt(4));
					premiumConfigurationVO.setMaxAge(rs.getInt(5));
					premiumConfigurationVO.setMaritalStatus(rs.getString(6));
					premiumConfigurationVO.setRelation(rs.getString(8));
					premiumConfigurationVO.setCapitationYN(rs.getString("CAPITATION_YN"));
					premiumConfigurationVO.setSalaryBand(rs.getString("SALARY_BAND"));
					premiumConfigurationVO.setAuthorityProductId(rs.getString("AUTH_PRODUCT_CODE"));
					premiumConfigurationVO.setCapitationtype(rs.getString("CAPITATION_DESC"));
					
					
					premiumConfigurationVO.setCapitationYN(rs.getString("CAPITATION_YN"));
					
					//if(TTKCommon.checkNull(rs.getString("CAPITATION_YN").equals("1")) != null)
					if((rs.getString("CAPITATION_YN"))!=null && (rs.getString("CAPITATION_YN").equals("1")) ){
					premiumConfigurationVO.setGrossPremium(rs.getBigDecimal(9));
					}
					else{
						premiumConfigurationVO.setGrossPremium1(rs.getBigDecimal(9).doubleValue());
					}
					
					
					premiumConfigurationVO.setGender(rs.getString(7));
					premiumConfigurationVO.setUpdatedRemarks(rs.getString("updated_remarks"));
					
					premiumConfigurationVO.setImageName("DeleteIcon");
					premiumConfigurationVO.setImageTitle("Dlete");
					premiumConfigurationVO.setDeleteTitle("Delete");
					
					
					
					premiumConfigurationVO.setMedicalPremium(rs.getDouble("medical_prem"));
					premiumConfigurationVO.setMaternityPremium(rs.getDouble("maternity_prem"));
					premiumConfigurationVO.setOpticalPremium(rs.getDouble("optical_prem"));
					premiumConfigurationVO.setDentalPremium(rs.getDouble("dental_prem"));
					premiumConfigurationVO.setWellnessPremium(rs.getDouble("wellness_prem"));
					premiumConfigurationVO.setIpNetPremium(rs.getDouble("ip_net_prem"));
					premiumConfigurationVO.setNetPremium(rs.getDouble("net_prem"));
					premiumConfigurationVO.setOpNetPremium(rs.getDouble("op_net_prem"));
					
					
					premiumConfigurationVO.setInsurerMarginPER(rs.getDouble("insurer_margin"));
					premiumConfigurationVO.setBrokerMarginPER(rs.getDouble("broker_margin"));
					//premiumConfigurationVO.setTapMargin(rs.getDouble("tpa_margin"));
					//premiumConfigurationVO.setTapMarginPER(rs.getDouble("tpa_margin"));
					premiumConfigurationVO.setReInsBrkMarginPER(rs.getDouble("reins_brk_margin"));
					premiumConfigurationVO.setOtherMarginPER(rs.getDouble("other_margin"));
					premiumConfigurationVO.setInsurerMarginAED(rs.getDouble("insurer_margin_aed"));
					premiumConfigurationVO.setBrokerMarginAED(rs.getDouble("broker_margin_aed"));
					premiumConfigurationVO.setTapMarginAED(rs.getDouble("tpa_margin_aed"));
					premiumConfigurationVO.setReInsBrkMarginAED(rs.getDouble("reins_brk_margin_aed"));
					premiumConfigurationVO.setOtherMarginAED(rs.getDouble("other_margin_aed"));
	            		
					premiumConfigurationVO.setInsurerMarginAEDPER(rs.getString("insurer_margin_flag"));
					premiumConfigurationVO.setBrokerMarginAEDPER(rs.getString("broker_margin_flag"));
					premiumConfigurationVO.setReInsBrkMarginAEDPER(rs.getString("reins_brk_margin_flag"));
					premiumConfigurationVO.setOtherMarginAEDPER(rs.getString("other_margin_flag"));
					
					if("PER".equals(rs.getString("insurer_margin_flag")))
						premiumConfigurationVO.setInsurerMargin(rs.getDouble("insurer_margin"));
					
					else
						premiumConfigurationVO.setInsurerMargin(rs.getDouble("insurer_margin_aed"));
					
					
					if("PER".equals(rs.getString("broker_margin_flag")))
						premiumConfigurationVO.setBrokerMargin(rs.getDouble("broker_margin"));
					
					else
						premiumConfigurationVO.setBrokerMargin(rs.getDouble("broker_margin_aed"));
					
					
					if("PER".equals(rs.getString("reins_brk_margin_flag")))
						premiumConfigurationVO.setReInsBrkMargin(rs.getDouble("reins_brk_margin"));
					
					else
						premiumConfigurationVO.setReInsBrkMargin(rs.getDouble("reins_brk_margin_aed"));
					
					
					if("PER".equals(rs.getString("other_margin_flag")))
						premiumConfigurationVO.setOtherMargin(rs.getDouble("other_margin"));
					
					else
						premiumConfigurationVO.setOtherMargin(rs.getDouble("other_margin_aed"));
					premiumConfigurationVO.setTpaFee(TTKCommon.checkNull(rs.getString("TPA_FEE")));
					premiumConfigurationVO.setTpaFeeType(TTKCommon.checkNull(rs.getString("TPA_FEE_TYPE")));
					premiumConfigurationVO.setTpaFeeAEDPER(TTKCommon.checkNull(rs.getString("TPA_FEE_FLAG")));
					premiumConfigurationVO.setTpaActualFee(TTKCommon.checkNull(rs.getString("TPA_FEE_AED")));
					resultList.add(premiumConfigurationVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			

        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
		finally
		{ 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getProdPolicyLimit()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (preparedstmt != null)	preparedstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getProdPolicyLimit()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getProdPolicyLimit()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				preparedstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
		
	}

	public int[] synchronizeRuleGenProdCardRule(String[] strSeqID,
			Long lngProdPolicySeqID, String strDenialsyn,  HashMap flagMap,
			Long addedby) throws TTKException {
		int iResult[] = new int[2];
		Connection conn = null;
		CallableStatement cStmtObject=null; CallableStatement cStmtObject2=null;
		CallableStatement cStmtObject3=null; CallableStatement cStmtObject4=null;
		CallableStatement cStmtObject5=null; CallableStatement cStmtObject6=null;
		try{
			conn = ResourceManager.getConnection();
			if(flagMap.get("PROD").equals("Y"))
			{
				
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSynchronizeRule);				// 1
				cStmtObject.setString(1,strSeqID[0]+"|");//
				cStmtObject.setLong(2,lngProdPolicySeqID);
				cStmtObject.setString(3,strDenialsyn);//denial process
				cStmtObject.registerOutParameter(4,Types.INTEGER);
				cStmtObject.execute();
				iResult[0] = cStmtObject.getInt(4);
		//		if(cStmtObject!=null) cStmtObject.close();
				cStmtObject2 = (java.sql.CallableStatement) conn.prepareCall(strSaveBenefitLimitSync);			// 2
				cStmtObject2.setString(1,strSeqID[1]+"|");
				cStmtObject2.registerOutParameter(2,Types.INTEGER);
				cStmtObject2.execute();
				
			}

			if(flagMap.get("CARD").equals("Y"))
			{
				
				
				cStmtObject3 = (java.sql.CallableStatement) conn.prepareCall(strSynchronizeCardRules);			// 3
				cStmtObject3.setString(1,strSeqID[0]+"|");//policy_prod_seq_ids		
				cStmtObject3.setLong(2,lngProdPolicySeqID); //prod_policy_seq_id
				cStmtObject3.setLong(3,addedby);
				cStmtObject3.execute();
				
				iResult[0]=1;
			}
			if(flagMap.get("GEN").equals("Y"))
			{
				
				
				cStmtObject4 = (java.sql.CallableStatement) conn.prepareCall(strSynchronizeGenRules);
				cStmtObject4.setString(1,strSeqID[1]+"|");//policy_se_ids
				cStmtObject4.setString(2,strSeqID[0]+"|");//policy_prod_seq_ids
				cStmtObject4.setLong(3,lngProdPolicySeqID); //prod_policy_seq_id
				cStmtObject4.setLong(4,addedby);
				cStmtObject4.registerOutParameter(5, Types.INTEGER);
				cStmtObject4.execute();
				
				int updateFlag = cStmtObject4.getInt(5);
				iResult[0]=1;
				iResult[1]= updateFlag;
			}
			if(flagMap.get("GEN BNF").equals("Y"))
			{
				cStmtObject5 = (java.sql.CallableStatement) conn.prepareCall(strSynchronizeGenRules1);
				cStmtObject5.setString(1,strSeqID[1]+"|");//policy_se_ids
				cStmtObject5.setString(2,strSeqID[0]+"|");//policy_prod_seq_ids
				cStmtObject5.setLong(3,lngProdPolicySeqID); //prod_policy_seq_id
				cStmtObject5.setLong(4,addedby);
				cStmtObject5.execute();
				iResult[0]=1;
			}
			
			
			
			if(flagMap.get("VAT").equals("Y"))
			{
				
				
				cStmtObject6 = (java.sql.CallableStatement) conn.prepareCall(strSynchronizeVatRules);		// 6
				
				cStmtObject6.setLong(1,lngProdPolicySeqID);//prod_seq_id
				cStmtObject6.setString(2,strSeqID[1]+"|");//policy_se_ids
				//cStmtObject.setLong(3,strSeqID[0]); //policy_prod_seq_ids
				//cStmtObject.setLong(4,addedby);
				cStmtObject6.execute();
				iResult[0]=1;
			}
			
			conn.commit();
			
			
		}//end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close(); 	 if (cStmtObject2 != null) cStmtObject2.close(); 
					if (cStmtObject3 != null) cStmtObject3.close();  if (cStmtObject4 != null) cStmtObject4.close(); 
					if (cStmtObject5 != null) cStmtObject5.close();  if (cStmtObject6 != null) cStmtObject6.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in ProductDAOImpl synchronizeRule()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					/*try
					{
						if (cStmtObject != null) cStmtObject.close(); 	 if (cStmtObject2 != null) cStmtObject2.close(); 
						if (cStmtObject3 != null) cStmtObject3.close();  if (cStmtObject4 != null) cStmtObject4.close(); 
						if (cStmtObject5 != null) cStmtObject5.close();  if (cStmtObject6 != null) cStmtObject6.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl synchronizeRule()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
							if (cStmtObject2 != null) cStmtObject2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in ProductDAOImpl synchronizeRule()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if (cStmtObject != null) cStmtObject.close();
								if (cStmtObject2 != null) cStmtObject2.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Statement in ProductDAOImpl synchronizeRule()",sqlExp);
								throw new TTKException(sqlExp, "product");
							}//end of catch (SQLException sqlExp)
							finally // Even if statement is not closed, control reaches here. Try closing the connection now.
							{*/
								try
								{
									if(conn != null) conn.close();
									
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in ProductDAOImpl synchronizeRule()",sqlExp);
									throw new TTKException(sqlExp, "product");
								}//end of catch (SQLException sqlExp)
								finally
								{
							}
						}
					
				
			}//end of try
	
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null; 	cStmtObject2 = null; 	cStmtObject3 = null;
				cStmtObject4 = null;	cStmtObject5 = null;	cStmtObject6 = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;

	}
	// End of synchronizeRuleGenProdCardRule method

	public ArrayList<Object> getInsCompnyList(String authority)throws TTKException {
		
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
       ArrayList<CacheObject> alInsList = new ArrayList<CacheObject>();
       CacheObject cacheObject = null;
       ArrayList<Object> allList = new ArrayList<Object>();
       String query=null;
       //String  query = "SELECT INS_COMP_CODE_NUMBER, INS_COMP_NAME as INS_COMP_NAME ,INS_SEQ_ID FROM app.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' and PAYER_AUTHORITY in ("+authority+") ORDER BY INS_COMP_NAME";
       if(authority.equals("'CMA'")||authority.equals("''")){
    	    
    	//   query = "SELECT INS_COMP_CODE_NUMBER, INS_COMP_NAME as INS_COMP_NAME ,INS_SEQ_ID FROM app.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' and PAYER_AUTHORITY in ("+authority+") ORDER BY INS_COMP_NAME";  
			query = "SELECT INS_COMP_CODE_NUMBER, INS_COMP_NAME as INS_COMP_NAME ,INS_SEQ_ID FROM app.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' and active_yn='Y' and PAYER_AUTHORITY in ("+authority+") ORDER BY INS_COMP_NAME"; 
	   }
       else{
    	     
    	     //query = "SELECT INS_COMP_CODE_NUMBER, INS_COMP_NAME as INS_COMP_NAME ,INS_SEQ_ID FROM app.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' and PAYER_AUTHORITY in ("+authority+") ORDER BY INS_COMP_NAME";
    	   query="SELECT INS_SEQ_ID,INS_COMP_NAME FROM app.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' and ins_Seq_id IN(16423,16960,16940)ORDER BY INS_COMP_NAME";
       }
     
       try {
  			 
    	   con=ResourceManager.getConnection();
    		statement=con.prepareStatement(query);
    			// statement.setString(1, authority); 
    			  resultSet=statement.executeQuery();
    			  while(resultSet.next()){
    				  
    				  cacheObject = new CacheObject();
    				  cacheObject.setCacheId((resultSet.getString("INS_SEQ_ID")));
                      cacheObject.setCacheDesc(TTKCommon.checkNull(resultSet.getString("INS_COMP_NAME")));
                      alInsList.add(cacheObject);
    				  
    			  }
    	   
    			  allList.add(alInsList);
    	    		
    	    	  return allList;
	
}
       
       catch (SQLException sqlExp)
       {
           throw new TTKException(sqlExp, "product");
       }	
       catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
   	
       finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (resultSet != null) resultSet.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getInsCompnyList()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (statement != null) statement.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getInsCompnyList()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(con != null) con.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getInsCompnyList()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				resultSet = null;
				statement = null;
				con = null;
			}//end of finally
		}//end of finally
       
       
       
	}

		public ArrayList getAuditPremiumConfiguration(ProductVO productVO)throws TTKException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Connection conn = null;
		    	CallableStatement cStmtObject=null;
		        ResultSet rs = null;
		        PremiumConfigurationVO premiumConfigurationVO = null;
		        Collection<Object> resultList = new ArrayList<Object>();
		       try{
		        	conn = ResourceManager.getConnection();
					cStmtObject =  conn.prepareCall(strGetAuditPremiumConfiguration);
				   	if(productVO.getPolicySeqID()!=null)
					{	
						cStmtObject.setLong(1,productVO.getPolicySeqID());
					}else
					{
						cStmtObject.setLong(1,0);
					}
					if(productVO.getProdSeqId()!=null) cStmtObject.setLong(2,productVO.getProdSeqId());
					else cStmtObject.setLong(2,0);
					
					if(productVO.getPremiumConfigSeqID()!=null)
						cStmtObject.setLong(3,productVO.getPremiumConfigSeqID());
					else cStmtObject.setLong(3,0);
					 if(productVO.getMode()!=null)
					cStmtObject.setString(4,productVO.getMode());
					else cStmtObject.setString(4,null);
				    //cStmtObject.setLong(3,productVO.getPolicySeqID());*/
					cStmtObject.registerOutParameter(5,Types.OTHER);
					cStmtObject.execute();
					rs = (java.sql.ResultSet)cStmtObject.getObject(5);
					
				if(rs != null){
						while(rs.next()){
							premiumConfigurationVO=new PremiumConfigurationVO();
							premiumConfigurationVO.setReverse_num(rs.getInt("rev_no"));;
							premiumConfigurationVO.setMinAge(rs.getInt("min_age"));
							premiumConfigurationVO.setMaxAge(rs.getInt("max_age"));
							premiumConfigurationVO.setMaritalStatus(rs.getString("marital_status"));
							premiumConfigurationVO.setRelation(rs.getString("applicable_to_relation"));
							premiumConfigurationVO.setGrossPremium(rs.getBigDecimal("gross_prem"));
							premiumConfigurationVO.setGender(rs.getString("gender"));
							premiumConfigurationVO.setSalaryBand(rs.getString("SALARY_BAND"));
							premiumConfigurationVO.setUpdatedDate(rs.getDate("updated_date"));
							if(rs.getString("updated_remarks")!=null)
							{
							premiumConfigurationVO.setUpdatedRemarks(rs.getString("updated_remarks"));
							}
							else
							{
								premiumConfigurationVO.setUpdatedRemarks("");
							}
							premiumConfigurationVO.setGrossPremium(rs.getBigDecimal(9));
							premiumConfigurationVO.setUpdtby(rs.getString("contact_name"));
							if(rs.getString("remarks")!=null)
							{
							premiumConfigurationVO.setRemarks(rs.getString("remarks"));
							}
							else
							{
								premiumConfigurationVO.setRemarks("");	
							}
							resultList.add(premiumConfigurationVO);
						}//end of while(rs.next())
					}//end of if(rs != null)
					

		        }//end of try
		        catch (SQLException sqlExp)
				{
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				catch (Exception exp)
				{
					throw new TTKException(exp, "product");
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
							log.error("Error while closing the Resultset in ProductDAOImpl getProdPolicyLimit()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
						finally // Even if result set is not closed, control reaches here. Try closing the statement now.
						{
							try
							{
								if (cStmtObject != null)	cStmtObject.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Statement in ProductDAOImpl getProdPolicyLimit()",sqlExp);
								throw new TTKException(sqlExp, "product");
							}//end of catch (SQLException sqlExp)
							finally // Even if statement is not closed, control reaches here. Try closing the connection now.
							{
								try
								{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in ProductDAOImpl getProdPolicyLimit()",sqlExp);
									throw new TTKException(sqlExp, "product");
								}//end of catch (SQLException sqlExp)
							}//end of finally Connection Close
						}//end of finally Statement Close
					}//end of try
					catch (TTKException exp)
					{
						throw new TTKException(exp, "product");
					}//end of catch (TTKException exp)
					finally // Control will reach here in anycase set null to the objects
					{
						rs = null;
						cStmtObject = null;
						conn = null;
					}//end of finally
				}//end of finally
				return (ArrayList<Object>)resultList;
				
	}
	


	/* Rishi Sharma
	 * These method used for delete Audit logs,member Rules and Auto checking of member inception rules
	 * 
	 */
	public int deleteAuditlogsConfiguration(ProductVO productVO) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		int res = 0;
		try
		{
			conn=ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strDELETE_CONFIG_RULE);
			cStmtObject.setLong(1, productVO.getPremiumConfigSeqID());
			if(productVO.getProdPolicySeqID()!=null)
				cStmtObject.setLong(2,productVO.getProdPolicySeqID());
			else 
				cStmtObject.setLong(2,0);
			if(productVO.getPolicySeqID()!=null)
				cStmtObject.setLong(3,productVO.getPolicySeqID());
			else
				cStmtObject.setLong(3,0);
			if(productVO.getMode()!=null)
				cStmtObject.setString(4,productVO.getMode());
			else cStmtObject.setString(4,null);
			cStmtObject.registerOutParameter(5,Types.BIGINT);
			cStmtObject.execute();
		    res = (int) cStmtObject.getLong(5);
		    conn.commit();
		}
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
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
					log.error("Error while closing the Resultset in ProductDAOImpl getSynchPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getSynchPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getSynchPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return res;
	}

	public String saveVatConfigYN(ProdPolicyLimitVO prodPolicyLimitVO)throws TTKException {

    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	int iResult;
    	String strResult="";
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveVatConfiguratinYN);
			
			cStmtObject.setString(1,prodPolicyLimitVO.getVatYN_flag());
			
			if("PRO".equals(prodPolicyLimitVO.getVatYN_flag())){
				
				cStmtObject.setLong(2,prodPolicyLimitVO.getProdSeqId());
			}
			else {
				
				cStmtObject.setLong(2,prodPolicyLimitVO.getPolicySeqId());
			}
		
			cStmtObject.setString(3,prodPolicyLimitVO.getVatYN());
			
			 cStmtObject.registerOutParameter(4,Types.BIGINT);
			
    		cStmtObject.execute();
    		
    		iResult = (int) cStmtObject.getLong(4);
    		strResult = prodPolicyLimitVO.getVatYN();
		    conn.commit();
    	}//end of try
    	catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "product");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "product");
		}// end of catch (Exception exp)
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
        			log.error("Error while closing the Statement in ProductDAOImpl saveVatConfigYN()",sqlExp);
        			throw new TTKException(sqlExp, "product");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl saveVatConfigYN()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "product");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return strResult;
    	
    
	}

	public Object[] getPremiumEffectiveDates(ProductVO productVO) throws TTKException {

		// TODO Auto-generated method stub
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rsAso = null;
        ResultSet rsAsplus = null;
        PremiumConfigurationVO premiumConfigurationVO = null;
        PremiumConfigurationVO asoVO = null;
        PremiumConfigurationVO asplusVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
        Collection<Object> asoResultList = new ArrayList<Object>();
        Collection<Object> asplusResultList = new ArrayList<Object>();
        //Collection<Object> allResultList = new ArrayList<Object>();
        Object[] allResultList=new Object[5];
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject =  conn.prepareCall(strGetPremiumConfiguration);
			
			if(productVO.getPremiumConfigSeqID()!=null)
				cStmtObject.setLong(1,productVO.getPremiumConfigSeqID());
			else cStmtObject.setLong(1,0);
			if(productVO.getProdPolicySeqID()!=null)
				cStmtObject.setLong(2,productVO.getProdPolicySeqID());
			else
				cStmtObject.setLong(2,0);
			if(productVO.getProdSeqId()!=null) cStmtObject.setLong(3,productVO.getProdSeqId());
			else cStmtObject.setLong(3,0);
			if(productVO.getPolicySeqID()!=null)
			
				 cStmtObject.setLong(4,productVO.getPolicySeqID());
			else
				 cStmtObject.setLong(4,0);
			if(productVO.getMode()!=null)
			cStmtObject.setString(5,productVO.getMode());
			else cStmtObject.setString(5,null);
			
			if(productVO.getPremiumEffectivePeriodAso()!=null){
				
				cStmtObject.setString(6,productVO.getAsoFromDate());
				cStmtObject.setString(7,productVO.getAsoToDate());
			}
			else{
				cStmtObject.setString(6,null);
				cStmtObject.setString(7,null);
			}
			if(productVO.getPremiumEffectivePeriodAsPlus()!=null){
				cStmtObject.setString(8,productVO.getAsPlusFromDate());
				cStmtObject.setString(9,productVO.getAsPlusToDate());
			}
			else{
				cStmtObject.setString(8,null);
				cStmtObject.setString(9,null);
			}
			
			
			
			cStmtObject.registerOutParameter(10,Types.OTHER);
			cStmtObject.registerOutParameter(11,Types.OTHER);
			cStmtObject.registerOutParameter(12,Types.OTHER);
			cStmtObject.registerOutParameter(13,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(11);
			rsAso = (java.sql.ResultSet)cStmtObject.getObject(12);
			rsAsplus = (java.sql.ResultSet)cStmtObject.getObject(13);
			
		if(rsAso != null){
			
			if(rsAso.next()){
				
				asoVO=new PremiumConfigurationVO();
				
				asoVO.setAsoFromDate(TTKCommon.checkNull(rsAso.getString("ASo_srch_date1")));
				asoVO.setAsoToDate(TTKCommon.checkNull(rsAso.getString("ASo_srch_date2")));
				
				asoResultList.add(asoVO);
			}
		}
		
		if(rsAsplus != null){
			if(rsAsplus.next()){
				asplusVO=new PremiumConfigurationVO();
				
				asplusVO.setAsPlusFromDate(TTKCommon.checkNull(rsAsplus.getString("ASpls_srch_date1")));
				asplusVO.setAsPlusToDate(TTKCommon.checkNull(rsAsplus.getString("ASpls_srch_date2")));
				
				asplusResultList.add(asplusVO);
			}
		}
	
		allResultList[0]=asoResultList;
		allResultList[1]=asplusResultList;
		
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rsAso != null) rsAso.close();
					if (rsAsplus != null) rsAsplus.close();
					if (rs != null) rs.close();
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getPremiumEffectiveDates()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getPremiumEffectiveDates()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getPremiumEffectiveDates()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				rs1 = null;
				rsAso = null;
				rsAsplus = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return allResultList;
		
	
	}

public ArrayList<Object> getEffectiveDatesList(ProductVO productVO) throws TTKException {
		
		CacheObject cacheObject = null;
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rsAso = null;
        ResultSet rsAsplus = null;
        ArrayList<CacheObject> alAsoList = new ArrayList<CacheObject>();
        ArrayList<CacheObject> alAsplusList = new ArrayList<CacheObject>();
        ArrayList<Object> allList = new ArrayList<Object>();
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject =  conn.prepareCall(strEffectiveDatesList);
		
		
			if(productVO.getProdPolicySeqID()!=null)
				cStmtObject.setLong(1,productVO.getProdPolicySeqID());
			else
				cStmtObject.setLong(1,0);
			if(productVO.getProdSeqId()!=null) cStmtObject.setLong(2,productVO.getProdSeqId());
			else cStmtObject.setLong(2,0);
			if(productVO.getPolicySeqID()!=null)
			
				 cStmtObject.setLong(3,productVO.getPolicySeqID());
			else
				 cStmtObject.setLong(3,0);
			
			cStmtObject.registerOutParameter(4,Types.OTHER);
			cStmtObject.registerOutParameter(5,Types.OTHER);
			cStmtObject.execute();
			rsAso = (java.sql.ResultSet)cStmtObject.getObject(4);
			rsAsplus = (java.sql.ResultSet)cStmtObject.getObject(5);
			
		if(rsAso != null){
			
			while(rsAso.next()){
			
				cacheObject = new CacheObject();
				ProdPolicyLimitVO prodlimitvo=new ProdPolicyLimitVO();
				cacheObject.setCacheId((rsAso.getString("srch_key")));
				if(!"".equals(TTKCommon.checkNull(rsAso.getString("srch_key")))){
				prodlimitvo.setFromdate(rsAso.getString("srch_key").substring(0,9));
				}
                cacheObject.setCacheDesc(TTKCommon.checkNull(rsAso.getString("srch_criteria")));
                
                alAsoList.add(cacheObject);
			
			}
		}
		if(rsAsplus != null){
			
			while(rsAsplus.next()){
			
				cacheObject = new CacheObject();
				
				cacheObject.setCacheId((rsAsplus.getString("srch_key")));
                cacheObject.setCacheDesc(TTKCommon.checkNull(rsAsplus.getString("srch_criteria")));
                alAsplusList.add(cacheObject);
			
			}
		}
			
			
		allList.add(alAsoList);	
		allList.add(alAsplusList);		
        }
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "product");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rsAso != null) rsAso.close();
					if (rsAsplus != null) rsAsplus.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getEffectiveDatesList()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getEffectiveDatesList()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getEffectiveDatesList()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rsAso = null;
				rsAsplus = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return allList;
		
	}

public long updatePremiumConfigDetails(ProdPolicyLimitVO prodPolicyLimitVO)throws TTKException {
	
	Connection conn = null;
	CallableStatement cStmtObject=null;
	long iResult=0;
	
	try{
		conn = ResourceManager.getConnection();
		cStmtObject =  conn.prepareCall(updfatePremiumConfigDetails);
		
		if(prodPolicyLimitVO.getProdPolicySeqID()!=null)
		{
			cStmtObject.setLong(1,prodPolicyLimitVO.getProdPolicySeqID());
		}
		else
		{
			cStmtObject.setLong(1,0);
		}
		if(prodPolicyLimitVO.getPolicySeqId()!=null)
		{
			cStmtObject.setLong(2,prodPolicyLimitVO.getPolicySeqId());
			
		}
		else
		{
			cStmtObject.setLong(2,0);
		}
		
		cStmtObject.setString(3,prodPolicyLimitVO.getCapitationYN());
		
		if("1".equals(prodPolicyLimitVO.getCapitationYN())){
			if(prodPolicyLimitVO.getNewAsoFromDate()!=null)
			{
				cStmtObject.setString(4,prodPolicyLimitVO.getNewAsoFromDate());
				
			}
			else
			{
				cStmtObject.setString(4,null);
			}
			
			if(prodPolicyLimitVO.getNewAsoToDate()!=null)
			{
				cStmtObject.setString(5,prodPolicyLimitVO.getNewAsoToDate());
				
			}
			else
			{
				cStmtObject.setString(5,null);
			}
		}
		else{
			
			if(prodPolicyLimitVO.getNewAsPlusFromDate()!=null)
			{
				cStmtObject.setString(4,prodPolicyLimitVO.getNewAsPlusFromDate());
				
			}
			else
			{
				cStmtObject.setString(4,null);
			}
			
			if(prodPolicyLimitVO.getNewAsPlusToDate()!=null)
			{
				cStmtObject.setString(5,prodPolicyLimitVO.getNewAsPlusToDate());
				
			}
			else
			{
				cStmtObject.setString(5,null);
			}
			
		}
		
		
		
		cStmtObject.setString(6,prodPolicyLimitVO.getAsoFromDate());
		
		
		
		
		
		cStmtObject.registerOutParameter(7,Types.BIGINT);
		cStmtObject.execute();
		iResult = cStmtObject.getLong(7);
		conn.commit();
	}
	
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "product");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "product");
	}// end of catch (Exception exp)
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
    			log.error("Error while closing the Statement in ProductDAOImpl updatePremiumConfigDetails()",sqlExp);
    			throw new TTKException(sqlExp, "product");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if(conn != null) conn.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Connection in ProductDAOImpl updatePremiumConfigDetails()",sqlExp);
    				throw new TTKException(sqlExp, "product");
    			}//end of catch (SQLException sqlExp)
    		}//end of finally Connection Close
    	}//end of try
    	catch (TTKException exp)
    	{
    		throw new TTKException(exp, "product");
    	}//end of catch (TTKException exp)
    	finally // Control will reach here in anycase set null to the objects
    	{
    		cStmtObject = null;
    		conn = null;
    	}//end of finally
	}//end of finally
	return iResult;
}



public int deletePremiumEffectiveConfiguration(ProductVO productVO) throws TTKException{
	Connection conn = null;
	CallableStatement cStmtObject = null;
	ResultSet rs = null;
	int res = 0;
	try
	{
		conn=ResourceManager.getConnection();
		cStmtObject = conn.prepareCall(strDELETE_EFFECTIVE_PREMIUM_PERIOD);
		
		cStmtObject.setLong(1,productVO.getProdPolicySeqID());
		cStmtObject.setString(2,productVO.getCapitationYN());
		
		if("1".equals(productVO.getCapitationYN())){
			
			cStmtObject.setString(3,productVO.getAsoFromDate());
			cStmtObject.setString(4,productVO.getAsoToDate());
		}
		else{
			
			cStmtObject.setString(3,productVO.getAsPlusFromDate());
			cStmtObject.setString(4,productVO.getAsPlusToDate());
		}
		
		
		cStmtObject.registerOutParameter(5,Types.BIGINT);
		cStmtObject.execute();
	    res = (int) cStmtObject.getLong(5);
	    conn.commit();
	}
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "product");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "product");
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
				log.error("Error while closing the Resultset in ProductDAOImpl deletePremiumEffectiveConfiguration()",sqlExp);
				throw new TTKException(sqlExp, "product");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null)	cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in ProductDAOImpl deletePremiumEffectiveConfiguration()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ProductDAOImpl deletePremiumEffectiveConfiguration()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			rs = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	return res;
}

public String getVatPercent(long linscode) throws TTKException{

	
	Connection conn = null;
	PreparedStatement cStmtObject=null;
	String iResult="";
	ResultSet rs=null;
	
	
	try{
		conn = ResourceManager.getConnection();
		//cStmtObject = (java.sql.PreparedStatement) conn.prepareCall(strGetVatPercent);
		String query = "select i.vat_percent from app.tpa_ins_info i where i.ins_seq_id=?";
		cStmtObject=conn.prepareStatement(query); 
		cStmtObject.setLong(1,linscode);
	
		
		rs=cStmtObject.executeQuery();
		
		if(rs.next()){
			
			iResult = rs.getString("vat_percent");
		}
		
		
		
		
	}//end of try
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "product");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "product");
	}// end of catch (Exception exp)
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
				log.error("Error while closing the Resultset in ProductDAOImpl getVatPercent()",sqlExp);
				throw new TTKException(sqlExp, "product");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null)	cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in ProductDAOImpl getVatPercent()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ProductDAOImpl getVatPercent()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			rs = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	return iResult;
}



public String getVatConfigDetail(ProdPolicyLimitVO prodPolicyLimitVO) throws TTKException{


	Connection conn = null;
	CallableStatement cStmtObject=null;
	String iResult="";
	ResultSet rs=null;
	try{
		conn = ResourceManager.getConnection();
		
		if("PRO".equals(prodPolicyLimitVO.getVatYN_flag())){
			
			cStmtObject = conn.prepareCall(strGetProductVatYN);
			cStmtObject.setLong(1,prodPolicyLimitVO.getProdSeqId());
		}
		else {
			
			cStmtObject = conn.prepareCall(strGetPolicyVatYN);
			cStmtObject.setLong(1,prodPolicyLimitVO.getPolicySeqId());
		}
	
		
		rs=cStmtObject.executeQuery();
		
		
      if(rs.next()){
			
    	  iResult = rs.getString("vat_eclg_chck");
		}
		
		
		
		
		
	}//end of try
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "product");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "product");
	}// end of catch (Exception exp)
	finally
	{
    	/* Nested Try Catch to ensure resource closure */
    	try // First try closing the Statement
    	{
    		try
    		{
    			if(rs != null) rs.close();
    		}//end of try
    		catch (SQLException sqlExp)
    		{
    			log.error("Error while closing the resultset in ProductDAOImpl getVatConfigDetail()",sqlExp);
    			throw new TTKException(sqlExp, "product");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if (cStmtObject != null) cStmtObject.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Statement in ProductDAOImpl getVatConfigDetail()",sqlExp);
    				throw new TTKException(sqlExp, "product");
    			}//end of catch (SQLException sqlExp)
    			
    			finally{
    				
    				try
        			{
    					if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProductDAOImpl getVatConfigDetail()",sqlExp);
        				throw new TTKException(sqlExp, "product");
        			}//end of catch (SQLException sqlExp)
    				
    			}
    			
    			
    		}//end of finally Connection Close
    		
    	}//end of try
    	catch (TTKException exp)
    	{
    		throw new TTKException(exp, "product");
    	}//end of catch (TTKException exp)
    	finally // Control will reach here in anycase set null to the objects
    	{
    		 rs=null;
    		cStmtObject = null;
    		conn = null;
    		
    	}//end of finally
	}//end of finally
	return iResult;
}

public ArrayList<String[]> getModificationRemarks(ProductVO productVO) throws TTKException {
	
	
	int iResult = 0;
	Connection conn = null;
	PreparedStatement pstm=null;
	ResultSet rs=null;
	ArrayList<String[]> historyDetails=new ArrayList<String[]>();
	
	
	try{
		conn = ResourceManager.getConnection();
		
		if("Products".equals(productVO.getActiveTabLink())){
			
			pstm = conn.prepareStatement(strPtoductModificationRemarks);
			pstm.setLong(1,Long.parseLong(productVO.getProductSeqId())); 
			
		}
		else{
			
			pstm = conn.prepareStatement(strPolicyModificationRemarks);
			pstm.setLong(1,productVO.getPolicySeqID()); 
		}
		
		rs=pstm.executeQuery();
		
		if(rs!=null){
			while(rs.next()){
				historyDetails.add(new String[]{
				TTKCommon.checkNull(rs.getString(1)),
				TTKCommon.checkNull(rs.getString(2)),
				TTKCommon.checkNull(rs.getString(3)),
				TTKCommon.checkNull(rs.getString(4))
				//rs.getDate(2)==null?"":new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(rs.getDate(2).getTime()))
				});
			}
		}
	return historyDetails;
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "Products");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "Products");
	}//end of catch (Exception exp)
	finally
	{
		/* Nested Try Catch to ensure resource closure */ 
		try // First try closing the Statement
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in PolicyDAOImpl getPolicyModifyHistory()",sqlExp);
				throw new TTKException(sqlExp, "enrollment");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
			{
				if (pstm != null) pstm.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Statement in PolicyDAOImpl getPolicyModifyHistory()",sqlExp);
				throw new TTKException(sqlExp, "enrollment");
			}//end of catch (SQLException sqlExp)
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in PolicyDAOImpl getPolicyModifyHistory()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
			}// end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs = null;
			pstm = null;
			conn = null;
		}//end of finally
	}//end of finally
}









}// End of class ProductDAOImpl
