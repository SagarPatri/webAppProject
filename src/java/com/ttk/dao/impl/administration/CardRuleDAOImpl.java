/**
 * @ (#) CardRulesDAOImpl.java Nov 05, 2005
 * Project      : TTK HealthCare Services
 * File         : CardRulesDAOImpl
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Nov 05, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao.impl.administration;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.CardRuleVO;
import com.ttk.dto.common.CacheObject;

public class CardRuleDAOImpl implements BaseDAO,Serializable {
                
                private static Logger log = Logger.getLogger(CardRuleDAOImpl.class);
    
    private static final String strCardRulesInfo = "SELECT CARD_RULE_TYPE_ID CARD_RULE_TYPE,TPA_INS_CARD_RULE_CODE.DESCRIPTION,ANSWER_HEADER_TYPE,'' ENROL_TYPE_ID,'' GENERAL_TYPE_ID, 0 CARD_RULES_SEQ_ID,TPA_GENERAL_CODE.DESCRIPTION OPTIONS1, SHOW_REMARKS, '' SHORT_REMARKS FROM APP.TPA_INS_CARD_RULE_CODE,APP.TPA_GENERAL_CODE WHERE RULE_GROUP_GENERAL_TYPE_ID =GENERAL_TYPE_ID AND CARD_RULE_TYPE_ID !='GMI' and CARD_RULE_TYPE_ID NOT IN (SELECT CARD_RULE_TYPE_ID FROM APP.TPA_INS_CARD_RULES,APP.TPA_INS_PROD_POLICY,APP.TPA_INS_PRODUCT,APP.TPA_INS_INFO WHERE TPA_INS_CARD_RULES.PROD_POLICY_SEQ_ID = TPA_INS_PROD_POLICY.PROD_POLICY_SEQ_ID AND TPA_INS_PROD_POLICY.PRODUCT_SEQ_ID=TPA_INS_PRODUCT.PRODUCT_SEQ_ID AND TPA_INS_PRODUCT.INS_SEQ_ID=TPA_INS_INFO.INS_SEQ_ID AND ENROL_TYPE_ID =? AND TPA_INS_CARD_RULES.PROD_POLICY_SEQ_ID =? AND TPA_INS_CARD_RULES.INS_SEQ_ID =? and  CARD_RULE_TYPE_ID !='GMI' UNION SELECT CARD_RULE_TYPE_ID FROM APP.TPA_INS_CARD_RULES,APP.TPA_INS_PROD_POLICY,APP.TPA_ENR_POLICY,APP.TPA_INS_INFO WHERE TPA_INS_CARD_RULES.PROD_POLICY_SEQ_ID=TPA_INS_PROD_POLICY.PROD_POLICY_SEQ_ID AND TPA_INS_PROD_POLICY.POLICY_SEQ_ID=TPA_ENR_POLICY.POLICY_SEQ_ID AND TPA_ENR_POLICY.INS_SEQ_ID=TPA_INS_INFO.INS_SEQ_ID AND TPA_ENR_POLICY.ENROL_TYPE_ID =? AND TPA_INS_CARD_RULES.PROD_POLICY_SEQ_ID =? AND TPA_INS_CARD_RULES.INS_SEQ_ID =? ) UNION SELECT TPA_INS_CARD_RULE_CODE.CARD_RULE_TYPE_ID CARD_RULE_TYPE,TPA_INS_CARD_RULE_CODE.DESCRIPTION,ANSWER_HEADER_TYPE,ENROL_TYPE_ID,TPA_INS_CARD_RULES.GENERAL_TYPE_ID,CARD_RULES_SEQ_ID,GC.DESCRIPTION OPTIONS1, SHOW_REMARKS, SHORT_REMARKS FROM APP.TPA_INS_CARD_RULE_CODE,APP.TPA_GENERAL_CODE GC,APP.TPA_INS_PROD_POLICY,APP.TPA_INS_PRODUCT,APP.TPA_INS_INFO,APP.TPA_INS_CARD_RULES LEFT OUTER JOIN APP.TPA_GENERAL_CODE ON(TPA_INS_CARD_RULES.GENERAL_TYPE_ID=TPA_GENERAL_CODE.GENERAL_TYPE_ID) WHERE TPA_INS_CARD_RULES.CARD_RULE_TYPE_ID=TPA_INS_CARD_RULE_CODE.CARD_RULE_TYPE_ID AND TPA_INS_CARD_RULE_CODE.CARD_RULE_TYPE_ID !='GMI' AND TPA_INS_CARD_RULES.PROD_POLICY_SEQ_ID=TPA_INS_PROD_POLICY.PROD_POLICY_SEQ_ID AND TPA_INS_PROD_POLICY.PRODUCT_SEQ_ID=TPA_INS_PRODUCT.PRODUCT_SEQ_ID AND TPA_INS_PRODUCT.INS_SEQ_ID=TPA_INS_INFO.INS_SEQ_ID AND RULE_GROUP_GENERAL_TYPE_ID=GC.GENERAL_TYPE_ID AND ENROL_TYPE_ID =? AND TPA_INS_CARD_RULES.PROD_POLICY_SEQ_ID =? AND TPA_INS_CARD_RULES.INS_SEQ_ID =? UNION SELECT TPA_INS_CARD_RULE_CODE.CARD_RULE_TYPE_ID CARD_RULE_TYPE,TPA_INS_CARD_RULE_CODE.DESCRIPTION,ANSWER_HEADER_TYPE,TPA_ENR_POLICY.ENROL_TYPE_ID, TPA_INS_CARD_RULES.GENERAL_TYPE_ID,CARD_RULES_SEQ_ID,GC.DESCRIPTION OPTIONS1, SHOW_REMARKS, SHORT_REMARKS FROM APP.TPA_INS_CARD_RULE_CODE,APP.TPA_GENERAL_CODE GC, APP.TPA_INS_PROD_POLICY,APP.TPA_ENR_POLICY,APP.TPA_INS_INFO,app.TPA_INS_CARD_RULES LEFT OUTER JOIN APP.TPA_GENERAL_CODE ON(TPA_INS_CARD_RULES.GENERAL_TYPE_ID=TPA_GENERAL_CODE.GENERAL_TYPE_ID) WHERE TPA_INS_CARD_RULES.CARD_RULE_TYPE_ID=TPA_INS_CARD_RULE_CODE.CARD_RULE_TYPE_ID AND TPA_INS_CARD_RULES.PROD_POLICY_SEQ_ID=TPA_INS_PROD_POLICY.PROD_POLICY_SEQ_ID AND TPA_INS_PROD_POLICY.POLICY_SEQ_ID=TPA_ENR_POLICY.POLICY_SEQ_ID AND TPA_ENR_POLICY.INS_SEQ_ID=TPA_INS_INFO.INS_SEQ_ID AND RULE_GROUP_GENERAL_TYPE_ID=GC.GENERAL_TYPE_ID AND TPA_ENR_POLICY.ENROL_TYPE_ID =? AND TPA_INS_CARD_RULES.PROD_POLICY_SEQ_ID =?  AND TPA_INS_CARD_RULES.INS_SEQ_ID =? and GC.DESCRIPTION != 'Global_net_mem_id' and TPA_INS_CARD_RULE_CODE.DESCRIPTION != 'Global_net_mem_id' ORDER BY OPTIONS1 DESC, DESCRIPTION  ASC";
    private static final String strCardRulesAnswersInfo = "SELECT GENERAL_TYPE_ID,DESCRIPTION FROM APP.TPA_GENERAL_CODE WHERE HEADER_TYPE=? ORDER BY SORT_NO";
    private static final String strUpdateCardRuleInfo = "{CALL PRODUCT_ADMIN_PKG_PR_PRODUCT_CARDRULES_SAVE(?,?,?,?,?,?,?,?)}";
                                                                         
    /** This method returns the ArrayList Which contains the Card Rules details 
     * @param strEnrollmentTypeId for identifying Enrollment Type Id
     * @param lngProdPolicySeqId for Product Policy Seq Id
     * @param lngInsSeqID for Insurance Seq ID
     * @return ArrayList which contains the Card Rules details
     * @exception throws TTKException
     */
    public ArrayList getCardRule(String strEnrollmentTypeId, Long lngProdPolicySeqId,Long lngInsSeqID) throws TTKException {
        
                Connection conn = null;
                PreparedStatement pStmt1 = null;
                PreparedStatement pStmt2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ArrayList<Object> alCardRulesList = null;
        ArrayList<Object> alAnswerList = null ;
        CardRuleVO cardRuleVO;
        String strParentId = null;
        CacheObject cacheObject = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt1 = conn.prepareStatement(strCardRulesInfo);
            pStmt2 = conn.prepareStatement(strCardRulesAnswersInfo);
            pStmt1.setString(1,strEnrollmentTypeId);
            pStmt1.setLong(2,lngProdPolicySeqId);
            pStmt1.setLong(3,lngInsSeqID);
            pStmt1.setString(4,strEnrollmentTypeId);
            pStmt1.setLong(5,lngProdPolicySeqId);
            pStmt1.setLong(6,lngInsSeqID);
            pStmt1.setString(7,strEnrollmentTypeId);
            pStmt1.setLong(8,lngProdPolicySeqId);
            pStmt1.setLong(9,lngInsSeqID);
            pStmt1.setString(10,strEnrollmentTypeId);
            pStmt1.setLong(11,lngProdPolicySeqId);
            pStmt1.setLong(12,lngInsSeqID);
            rs1 = pStmt1.executeQuery();
            if (rs1!=null)
            {
                while(rs1.next()) {
                    if (alCardRulesList == null)
                    {
                        alCardRulesList = new ArrayList<Object>();
                    }//end of if (alCardRulesList == null)
                    cardRuleVO = new CardRuleVO();
                    cardRuleVO.setCardRuleTypeId(TTKCommon.checkNull(rs1.getString("CARD_RULE_TYPE")));
                    cardRuleVO.setDescription(TTKCommon.checkNull(rs1.getString("DESCRIPTION")));
                    strParentId = TTKCommon.checkNull(rs1.getString("ANSWER_HEADER_TYPE")); 
                    cardRuleVO.setAnsHeaderType(strParentId);
                    cardRuleVO.setEnrollTypeId(TTKCommon.checkNull(rs1.getString("ENROL_TYPE_ID")));
                    cardRuleVO.setGeneralTypeId(TTKCommon.checkNull(rs1.getString("GENERAL_TYPE_ID")));
                    cardRuleVO.setOptions(TTKCommon.checkNull(rs1.getString("OPTIONS1")));
                    cardRuleVO.setShowRemarks(TTKCommon.checkNull(rs1.getString("SHOW_REMARKS")));
                    cardRuleVO.setShortRemarks(TTKCommon.checkNull(rs1.getString("SHORT_REMARKS")));
                    if(rs1.getString("CARD_RULES_SEQ_ID") != null){
                                cardRuleVO.setCardRulesSeqId(new Long(rs1.getLong("CARD_RULES_SEQ_ID")));
                    }//end of if(rs1.getString("CARD_RULES_SEQ_ID") != null)
                        
                    pStmt2.setString(1,strParentId);
                    rs2=pStmt2.executeQuery();
                    alAnswerList = null;
                    if(rs2 != null){
                        while (rs2.next()){
                            if (alAnswerList == null)
                            {
                                alAnswerList = new ArrayList<Object>();
                            }//end of if (alAnswerList == null)
                            cacheObject = new CacheObject();
                            cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("GENERAL_TYPE_ID")));
                            cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("DESCRIPTION")));
                            alAnswerList.add(cacheObject);
                        }// end of inner while (rs1.next())
                    }//end of if(rs1 != null)
                    cardRuleVO.setGnrlTypeIdList(alAnswerList);
                    alCardRulesList.add(cardRuleVO);
                    if (rs2 != null){
                                 rs2.close(); 
                    }//end of if (rs2 != null)
                       rs2=null;
               } //end of outer while(rs.next())
            }//End of if (rs!=null)
            if (pStmt2 != null){
              pStmt2.close();
            }//end of if (pStmt2 != null)
            pStmt2 = null;
            return alCardRulesList;
        }//end of try
        catch (SQLException sqlExp) 
        {
            throw new TTKException(sqlExp, "cardrules");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp) 
        {
            throw new TTKException(exp, "cardrules");
        }//end of catch (Exception exp) 
        finally
                                {
                                                /* Nested Try Catch to ensure resource closure */ 
                                                try // First try closing the result set
                                                {
                                                                try
                                                                {
                                                                                if (rs2 != null) rs2.close();
                                                                }//end of try
                                                                catch (SQLException sqlExp)
                                                                {
                                                                                log.error("Error while closing the Second Resultset in CardRuleDAOImpl getCardRule()",sqlExp);
                                                                                throw new TTKException(sqlExp, "cardrules");
                                                                }//end of catch (SQLException sqlExp)
                                                                finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
                                                                {
                                                                                try
                                                                                {
                                                                                                if (rs1 != null) rs1.close();
                                                                                }//end of try
                                                                                catch (SQLException sqlExp)
                                                                                {
                                                                                                log.error("Error while closing the First Resultset in CardRuleDAOImpl getCardRule()",sqlExp);
                                                                                                throw new TTKException(sqlExp, "cardrules");
                                                                                }//end of catch (SQLException sqlExp)
                                                                                finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
                                                                                {
                                                                                                try
                                                                                                {
                                                                                                                if(pStmt2 != null) pStmt2.close();
                                                                                                }//end of try
                                                                                                catch (SQLException sqlExp)
                                                                                                {
                                                                                                                log.error("Error while closing the Second Statement in CardRuleDAOImpl getCardRule()",sqlExp);
                                                                                                                throw new TTKException(sqlExp, "cardrules");
                                                                                                }//end of catch (SQLException sqlExp)
                                                                                                finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
                                                                                                {
                                                                                                                try
                                                                                                                {
                                                                                                                                if(pStmt1 != null) pStmt1.close();
                                                                                                                }//end of try
                                                                                                                catch (SQLException sqlExp)
                                                                                                                {
                                                                                                                                log.error("Error while closing the FirstStatement in CardRuleDAOImpl getCardRule()",sqlExp);
                                                                                                                                throw new TTKException(sqlExp, "cardrules");
                                                                                                                }//end of catch (SQLException sqlExp)
                                                                                                                finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
                                                                                                                {
                                                                                                                                try{
                                                                                                                                                if(conn != null) conn.close();
                                                                                                                                }//end of try
                                                                                                                                catch (SQLException sqlExp)
                                                                                                                                {
                                                                                                                                                log.error("Error while closing the Connection in CardRuleDAOImpl getCardRule()",sqlExp);
                                                                                                                                                throw new TTKException(sqlExp, "cardrules");
                                                                                                                                }//end of catch (SQLException sqlExp)
                                                                                                                }//end of finally
                                                                                                }//end of finally
                                                                                }//end of finally 
                                                                }//end of finally 
                                                }//end of try
                                                catch (TTKException exp)
                                                {
                                                                throw new TTKException(exp, "hospital");
                                                }//end of catch (TTKException exp)
                                                finally // Control will reach here in anycase set null to the objects 
                                                {
                                                                rs2 = null;
                                                                rs1 = null;
                                                                pStmt2 = null;
                                                                pStmt1 = null;
                                                                conn = null;
                                                }//end of finally
                                }//end of finally
    }//end of getCardRule(String strEnrollmentTypeId, Long lProdPolicySeqId)
        
    /**
     * This method adds/updates the CardRuleVO which contains Card Rules details
     * @param cardRuleVO the details which has to be added or updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int updateCardRule(CardRuleVO cardRuleVO) throws TTKException{
        int iResult = 1;
        StringBuffer sbfSQL = null;
        Connection conn = null;
        CallableStatement callStmt = null;
        try{
            conn = ResourceManager.getConnection();            
            callStmt = conn.prepareCall(strUpdateCardRuleInfo);
            if(cardRuleVO.getCardRuleTypeIdList()!= null){
               
            	String[] cardRuleTypeIdList=cardRuleVO.getCardRuleTypeIdList();
            	Long[] cardRulesSeqIdList=cardRuleVO.getCardRulesSeqIdList();
            	Long prodPolicySeqId=cardRuleVO.getProdPolicySeqId();
            	String enrollTypeId=cardRuleVO.getEnrollTypeId();
            	String[] generalTypeIdList=cardRuleVO.getGeneralTypeIdList();
            	Long insSeqID=cardRuleVO.getInsSeqID();
            	String[] shortRemarksList=cardRuleVO.getShortRemarksList();
            	Long updatedBy=cardRuleVO.getUpdatedBy();
            	
            	for(int i=0;i<cardRuleTypeIdList.length;i++){
                   
                	if(cardRulesSeqIdList[i]==null)callStmt.setLong(1, 0);
                	else callStmt.setLong(1, cardRulesSeqIdList[i]);
                	
                	if(prodPolicySeqId==null)callStmt.setLong(2, 0);
                	else callStmt.setLong(2, prodPolicySeqId);
                	
                	callStmt.setString(3, enrollTypeId);
                	
                	if(!"".equals(TTKCommon.checkNull(cardRuleTypeIdList[i])))callStmt.setString(4, cardRuleTypeIdList[i]);
                	else callStmt.setString(4, null);
                	
                	if(!"".equals(TTKCommon.checkNull(generalTypeIdList[i])))callStmt.setString(5, generalTypeIdList[i]);
                	else callStmt.setString(5, null);
                	
                	if(insSeqID==null)callStmt.setLong(6, 0);
                	else callStmt.setLong(6, insSeqID);
                	
                	callStmt.setString(7, TTKCommon.replaceSingleQots(shortRemarksList[i]));
                	callStmt.setLong(8, updatedBy);
                	callStmt.addBatch();
                }//end of for
            }//end of if(cardRuleVO.getCardRuleTypeIdList()!= null)
            callStmt.executeBatch();
            conn.commit();
        }//end of try
        catch (SQLException sqlExp) 
        { 
           
                throw new TTKException(sqlExp, "cardrules");
           
        }//end of catch (SQLException sqlExp) 
        catch (Exception exp) 
        {
          
                throw new TTKException(exp, "cardrules");
           
        }//end of catch (Exception exp)
        finally
                                {
                /* Nested Try Catch to ensure resource closure */ 
                try // First try closing the Statement
                {
                                try
                                {
                                                if (callStmt != null) callStmt.close();
                                }//end of try
                                catch (SQLException sqlExp)
                                {
                                                log.error("Error while closing the Statement in CardRuleDAOImpl updateCardRule()",sqlExp);
                                                throw new TTKException(sqlExp, "cardrules");
                                }//end of catch (SQLException sqlExp)
                                finally // Even if statement is not closed, control reaches here. Try closing the connection now.
                                {
                                                try
                                                {
                                                                if(conn != null) conn.close();
                                                }//end of try
                                                catch (SQLException sqlExp)
                                                {
                                                                log.error("Error while closing the Connection in CardRuleDAOImpl updateCardRule()",sqlExp);
                                                                throw new TTKException(sqlExp, "cardrules");
                                                }//end of catch (SQLException sqlExp)
                                }//end of finally Connection Close
                }//end of try
                catch (TTKException exp)
                {
                                throw new TTKException(exp, "cardrules");
                }//end of catch (TTKException exp)
                finally // Control will reach here in anycase set null to the objects 
                {
                	callStmt = null;
                    conn = null;
                }//end of finally
                                }//end of finally
        return iResult;
    }//end of updateCardRule(CardRuleVO cardRuleVO)
    
    /**
     * This method adds/updates the CardRuleVO which contains Card Rules details
     * @param cardRuleVO the details which has to be added or updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int updateCardRuleBackup(CardRuleVO cardRuleVO) throws TTKException{
        int iResult = 1;
        StringBuffer sbfSQL = null;
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = ResourceManager.getConnection();            
            stmt = conn.createStatement();
            if(cardRuleVO.getCardRuleTypeIdList()!= null){
                for(int i=0;i<cardRuleVO.getCardRuleTypeIdList().length;i++){
                    sbfSQL = new StringBuffer();
                    sbfSQL = sbfSQL.append(""+cardRuleVO.getCardRulesSeqIdList()[i]+","); //CARD_RULES_SEQ_ID
                    sbfSQL = sbfSQL.append(""+cardRuleVO.getProdPolicySeqId()+","); //PROD_POLICY_SEQ_ID
                    sbfSQL = sbfSQL.append("'"+cardRuleVO.getEnrollTypeId()+"',"); //PROD_POLICY_SEQ_ID
                    if(cardRuleVO.getCardRuleTypeIdList()[i].equals(""))
                    {
                    	sbfSQL = sbfSQL.append(""+null+",");//CARD_RULE_TYPE_ID
                    }//end of if(cardRuleVO.getCardRuleTypeIdList()[i].equals(""))
                    else
                    {
                        sbfSQL = sbfSQL.append("'"+cardRuleVO.getCardRuleTypeIdList()[i]+"',"); //CARD_RULE_TYPE_ID
                    }//end of else
                    if(cardRuleVO.getGeneralTypeIdList()[i].equals(""))
                    {
                        sbfSQL = sbfSQL.append(""+null+",");//GENERAL_TYPE_ID
                    }//end of if(cardRuleVO.getGeneralTypeIdList()[i].equals(""))
                    else
                    {
                        sbfSQL = sbfSQL.append("'"+cardRuleVO.getGeneralTypeIdList()[i]+"',"); //GENERAL_TYPE_ID
                    }//end of else
                    sbfSQL = sbfSQL.append(""+cardRuleVO.getInsSeqID()+",");
                    sbfSQL = sbfSQL.append("'"+TTKCommon.replaceSingleQots(cardRuleVO.getShortRemarksList()[i])+"',");//SHORT_REMARKS
                    sbfSQL = sbfSQL.append(""+cardRuleVO.getUpdatedBy()+")}"); //USER_ID
                    stmt.addBatch(strUpdateCardRuleInfo+sbfSQL.toString());
                }//end of for
            }//end of if(cardRuleVO.getCardRuleTypeIdList()!= null)
            stmt.executeBatch();
            conn.commit();
        }//end of try
        catch (SQLException sqlExp) 
        { 
            try {
                conn.rollback();
                throw new TTKException(sqlExp, "cardrules");
            } //end of try
            catch (SQLException sExp) {
                throw new TTKException(sExp, "cardrules");   
            }//end of catch (SQLException sExp)
        }//end of catch (SQLException sqlExp) 
        catch (Exception exp) 
        {
            try {
                conn.rollback();
                throw new TTKException(exp, "cardrules");
            } //end of try
            catch (SQLException sqlExp) {
                throw new TTKException(sqlExp, "cardrules");
            }//end of catch (SQLException sqlExp)
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
                                                log.error("Error while closing the Statement in CardRuleDAOImpl updateCardRule()",sqlExp);
                                                throw new TTKException(sqlExp, "cardrules");
                                }//end of catch (SQLException sqlExp)
                                finally // Even if statement is not closed, control reaches here. Try closing the connection now.
                                {
                                                try
                                                {
                                                                if(conn != null) conn.close();
                                                }//end of try
                                                catch (SQLException sqlExp)
                                                {
                                                                log.error("Error while closing the Connection in CardRuleDAOImpl updateCardRule()",sqlExp);
                                                                throw new TTKException(sqlExp, "cardrules");
                                                }//end of catch (SQLException sqlExp)
                                }//end of finally Connection Close
                }//end of try
                catch (TTKException exp)
                {
                                throw new TTKException(exp, "cardrules");
                }//end of catch (TTKException exp)
                finally // Control will reach here in anycase set null to the objects 
                {
                                stmt = null;
                                conn = null;
                }//end of finally
                                }//end of finally
        return iResult;
    }//end of updateCardRule(CardRuleVO cardRuleVO)
}//end of CardRuleDAOImpl
