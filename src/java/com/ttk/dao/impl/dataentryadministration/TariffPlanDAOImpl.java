/**
 * @ (#)  TariffPlanDAOImpl.java Oct 3, 2005
 * Project      : TTKPROJECT
 * File         : TariffPlanDAOImpl.java
 * Author       : Suresh.M
 * Company      : Span Systems Corporation
 * Date Created : Oct 3, 2005
 *
 * @author       :  Suresh.M
 * Modified by   :  Ramakrishna K M
 * Modified date :  Oct 10, 2005
 * Reason        :  Adding getPlanDetailList() and getPlanHistoryList()
 *
 * Modified by   :  Nagaraj D V
 * Modified date :  Nov 05, 2005
 * Reason        :  To handle the where clause in administration and empanelment flow
 */

package com.ttk.dao.impl.dataentryadministration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.PlanPackageVO;
import com.ttk.dto.administration.RateVO;
import com.ttk.dto.administration.RevisionPlanVO;
import com.ttk.dto.administration.TariffPlanVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.InsuranceVO;

public class TariffPlanDAOImpl implements BaseDAO,Serializable{
	
	private static Logger log = Logger.getLogger(TariffPlanDAOImpl.class);
	
	private static final String strTariffPlanList = "SELECT * FROM(SELECT PLAN_SEQ_ID,PLAN_NAME,PLAN_DESCRIPTION,DEFAULT_PLAN_YN,dense_RANK() OVER (ORDER BY #, ROWNUM) Q FROM  TPA_HOSP_PLAN_CODE";//WHERE DEFAULT_PLAN_YN IN ('Y', 'N') in administration flow, WHERE DEFAULT_PLAN_YN IN ('N') in hospital flow
    private static final String strAddUpdateTariffPlanInfo = "{CALL HOSPITAL_ADMIN_TARIFF_PKG.PR_HOSPITAL_PLAN_SAVE(?,?,?,?,?)}";
    private static final String strDeleteTariffPlanInfo = "{CALL HOSPITAL_ADMIN_TARIFF_PKG.PR_HOSPITAL_PLAN_DELETE(?,?)}";
    private static final String strAddUpdateRatesInfo = "{CALL HOSPITAL_ADMIN_TARIFF_PKG.PR_MANAGE_RATES(";
    private static final String strCheckPackageInfo = "{CALL HOSPITAL_ADMIN_TARIFF_PKG.PR_MANAGE_RATES_CHECK_PKG(?,?,?,?)}";
    private static final String strTariffPkgNonPkgList = "{CALL HOSPITAL_ADMIN_TARIFF_PKG.PR_SELECT_TARIFF_ITEM(?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strRatesDetailInfo = "{CALL HOSPITAL_ADMIN_TARIFF_PKG.PR_SELECT_PKG_RATES(?,?,?,?,?)}";
    private static final String strCheckAvailability = "{CALL HOSPITAL_ADMIN_TARIFF_PKG.PR_CHECK_PKG_AVAILABILITY(?,?,?,?,?)}";
    private static final String strRevisedPlanList = "SELECT * FROM (SELECT A.*,dense_RANK() OVER (ORDER BY #, ROWNUM) Q FROM (SELECT from_period.revised_plan_seq_id,'Revision ' || revision_number Revision_Number,revision_number as revision_no, tpa_hosp_plan_code.Plan_Name,tpa_hosp_plan_code.PLAN_SEQ_ID, tpa_hosp_plan_code.DEFAULT_PLAN_YN, from_period.plan_from_date from_date,Lead ( from_period.plan_from_date, 1 ) OVER ( ORDER BY from_period.plan_from_date ) - 1 to_date FROM tpa_hosp_revised_plan from_period, tpa_hosp_plan_code WHERE from_period.plan_seq_id = tpa_hosp_plan_code.plan_seq_id AND from_period.prod_hosp_seq_id is null and from_period.plan_seq_id = ?)A ";
    private static final String strAddRevisedPlan = "{call HOSPITAL_ADMIN_TARIFF_PKG.PR_HOSPITAL_REVISE_PLAN_SAVE(?,?,?,?,?,?,?)}";
    private static final String strTariffDetailList = "SELECT * FROM(SELECT B.*,DENSE_RANK() OVER (ORDER BY #, ROWNUM) Q FROM ( (SELECT TPA_INS_ASSOC_PROD_HOSP.PROD_HOSP_SEQ_ID,-1 ASSOCIATED_SEQ_ID,TPA_INS_ASSOC_PROD_HOSP.GENERAL_TYPE_ID GENERAL_TYPE_ID,'TTK-(' || TPA_GENERAL_CODE.DESCRIPTION || ')' ASSOCIATED_TO,'-' PRODUCT_POLICY_NO FROM TPA_INS_ASSOC_PROD_HOSP,TPA_GENERAL_CODE WHERE ( TPA_INS_ASSOC_PROD_HOSP.GENERAL_TYPE_ID = TPA_GENERAL_CODE.GENERAL_TYPE_ID ) AND ( TPA_GENERAL_CODE.HEADER_TYPE = 'NHCP' ) AND ( TPA_INS_ASSOC_PROD_HOSP.HOSP_SEQ_ID = ? ) AND ( TPA_INS_ASSOC_PROD_HOSP.PROD_POLICY_SEQ_ID IS NULL ) UNION ALL SELECT TPA_INS_ASSOC_PROD_HOSP.PROD_HOSP_SEQ_ID,TPA_INS_INFO.INS_SEQ_ID ASSOCIATED_SEQ_ID,'' GENERAL_TYPE_ID,TPA_INS_INFO.INS_COMP_NAME ASSOCIATED_TO , TPA_INS_PRODUCT.PRODUCT_NAME FROM TPA_INS_ASSOC_PROD_HOSP,TPA_INS_INFO,TPA_INS_PRODUCT,TPA_INS_PROD_POLICY WHERE ( TPA_INS_PRODUCT.INS_SEQ_ID = TPA_INS_INFO.INS_SEQ_ID ) AND ( TPA_INS_PROD_POLICY.PRODUCT_SEQ_ID = TPA_INS_PRODUCT.PRODUCT_SEQ_ID ) AND ( TPA_INS_ASSOC_PROD_HOSP.PROD_POLICY_SEQ_ID = TPA_INS_PROD_POLICY.PROD_POLICY_SEQ_ID ) AND ( TPA_INS_ASSOC_PROD_HOSP.STATUS_GENERAL_TYPE_ID = 'ASL' ) AND ( TPA_INS_ASSOC_PROD_HOSP.HOSP_SEQ_ID = ?) UNION ALL SELECT TPA_INS_ASSOC_PROD_HOSP.PROD_HOSP_SEQ_ID,TPA_INS_INFO.INS_SEQ_ID ASSOCIATED_SEQ_ID,''GENERAL_TYPE_ID,TPA_INS_INFO.INS_COMP_NAME || '(' || TPA_INS_INFO.INS_COMP_CODE_NUMBER || ')', '-' PRODUCT_POLICY_NO FROM TPA_INS_ASSOC_PROD_HOSP,TPA_INS_INFO, TPA_ENR_POLICY,TPA_INS_PROD_POLICY WHERE ( TPA_ENR_POLICY.INS_SEQ_ID = TPA_INS_INFO.INS_SEQ_ID ) AND ( TPA_INS_PROD_POLICY.POLICY_SEQ_ID = TPA_ENR_POLICY.POLICY_SEQ_ID ) AND ( TPA_INS_ASSOC_PROD_HOSP.PROD_POLICY_SEQ_ID = TPA_INS_PROD_POLICY.PROD_POLICY_SEQ_ID ) AND ( TPA_INS_ASSOC_PROD_HOSP.STATUS_GENERAL_TYPE_ID = 'ASL' ) AND ( TPA_INS_ASSOC_PROD_HOSP.HOSP_SEQ_ID= ? ) ))B";
    private static final String strAssociatedInfo = "SELECT DISTINCT * FROM (SELECT -1 ASSOCIATED_SEQ_ID,'TTK' ASSOCIATED_TO FROM DUAL UNION ALL SELECT TPA_INS_INFO.INS_SEQ_ID ASSOCIATED_SEQ_ID,TPA_INS_INFO.INS_COMP_NAME ASSOCIATED_TO FROM TPA_INS_ASSOC_PROD_HOSP,TPA_INS_INFO,TPA_INS_PRODUCT,TPA_INS_PROD_POLICY WHERE ( TPA_INS_PRODUCT.INS_SEQ_ID = TPA_INS_INFO.INS_SEQ_ID ) AND ( TPA_INS_PROD_POLICY.PRODUCT_SEQ_ID = TPA_INS_PRODUCT.PRODUCT_SEQ_ID ) AND ( TPA_INS_ASSOC_PROD_HOSP.PROD_POLICY_SEQ_ID = TPA_INS_PROD_POLICY.PROD_POLICY_SEQ_ID ) AND ( TPA_INS_ASSOC_PROD_HOSP.STATUS_GENERAL_TYPE_ID = 'ASL' ) AND ( TPA_INS_ASSOC_PROD_HOSP.HOSP_SEQ_ID = ?) UNION ALL SELECT TPA_INS_INFO.INS_SEQ_ID ASSOCIATED_SEQ_ID, TPA_INS_INFO.INS_COMP_NAME || '(' || TPA_INS_INFO.INS_COMP_CODE_NUMBER || ')' FROM TPA_INS_ASSOC_PROD_HOSP, TPA_INS_INFO,TPA_ENR_POLICY, TPA_INS_PROD_POLICY WHERE ( TPA_ENR_POLICY.INS_SEQ_ID = TPA_INS_INFO.INS_SEQ_ID ) AND ( TPA_INS_PROD_POLICY.POLICY_SEQ_ID = TPA_ENR_POLICY.POLICY_SEQ_ID ) AND ( TPA_INS_ASSOC_PROD_HOSP.PROD_POLICY_SEQ_ID = TPA_INS_PROD_POLICY.PROD_POLICY_SEQ_ID ) AND ( TPA_INS_ASSOC_PROD_HOSP.STATUS_GENERAL_TYPE_ID = 'ASL' ) AND ( TPA_INS_ASSOC_PROD_HOSP.HOSP_SEQ_ID = ? ) ) ORDER BY ASSOCIATED_TO";
    private static final String strRevisionHistoryList = "SELECT * FROM (SELECT A.*,dense_RANK() OVER (ORDER BY # , ROWNUM) Q FROM (SELECT from_period.revised_plan_seq_id,'Revision ' || revision_number Revision_Number,revision_number as revision_no,tpa_hosp_plan_code.Plan_Name,tpa_hosp_plan_code.PLAN_SEQ_ID,from_period.plan_from_date from_date, from_period.DISCOUNT_OFFERED_TTK, Lead ( from_period.plan_from_date, 1 ) OVER ( ORDER BY from_period.plan_from_date ) - 1 to_date,from_period.PROD_HOSP_SEQ_ID FROM tpa_hosp_revised_plan from_period, tpa_hosp_plan_code    WHERE from_period.PROD_HOSP_SEQ_ID = ? AND   from_period.plan_seq_id = tpa_hosp_plan_code.plan_seq_id ) A ";

    private static final int PLAN_SEQ_ID = 1;
    private static final int PLAN_NAME = 2;
    private static final int PLAN_DESCRIPTION = 3;
    private static final int USER_SEQ_ID  = 4;
    private static final int ROW_PROCESSED = 5;

    /**
     * This method returns the ArrayList, which contains the TariffPlanVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of TariffPlanVO object's which contains the tariff plan details
     * @exception throws TTKException
     */
    public ArrayList getTariffPlanList(ArrayList alSearchObjects) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strTariffPlanList;
        Collection<Object> alResultList = new ArrayList<Object>();
        TariffPlanVO tariffPlanVO = null;
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            for(int i=0; i < alSearchObjects.size()-4; i++)
            {
                if(alSearchObjects != null && alSearchObjects.size() > 0)
                {
                    if(sbfDynamicQuery.toString().equals(""))
                    {
                        sbfDynamicQuery = sbfDynamicQuery.append(" WHERE "+((SearchCriteria)alSearchObjects.get(i)).getName()+" IN ("+((SearchCriteria)alSearchObjects.get(i)).getValue()+")");
                    }//end of if(sbfDynamicQuery.toString().equals(""))
                    else
                    {
                        sbfDynamicQuery = sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
                    }//end of else
                }//end of  if(alSearchObjects != null && alSearchObjects.size() > 0)
            }//end of for()
        }//end of if(alSearchObjects != null && alSearchObjects.size() > 0)
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));  
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        try
        {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    tariffPlanVO =new TariffPlanVO();
                    if(rs.getString("PLAN_SEQ_ID")!=null){
                    	tariffPlanVO.setTariffPlanID(new Long(rs.getLong("PLAN_SEQ_ID")));
                    }//end of if(rs.getString("PLAN_SEQ_ID")!=null)
                    tariffPlanVO.setTariffPlanName(TTKCommon.checkNull(rs.getString("PLAN_NAME")));
                    tariffPlanVO.setTariffPlanDesc(TTKCommon.checkNull(rs.getString("PLAN_DESCRIPTION")));
                    tariffPlanVO.setDefaultPlanYn(TTKCommon.checkNull(rs.getString("DEFAULT_PLAN_YN")));
                    if (TTKCommon.checkNull(rs.getString("DEFAULT_PLAN_YN")).equals("Y")){
                    	tariffPlanVO.setImageTitle("Edit Rates");
                    }//end of if (TTKCommon.checkNull(rs.getString("DEFAULT_PLAN_YN")).equals("Y"))
                    alResultList.add(tariffPlanVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList) alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
					log.error("Error while closing the Resultset in TariffPlanDAOImpl getTariffPlanList()",sqlExp);
					throw new TTKException(sqlExp, "tariffplan");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffPlanDAOImpl getTariffPlanList()",sqlExp);
						throw new TTKException(sqlExp, "tariffplan");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffPlanDAOImpl getTariffPlanList()",sqlExp);
							throw new TTKException(sqlExp, "tariffplan");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffplan");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getTariffPlanList(ArrayList alSearchObjects)

    /**
     * This method adds/updates the details  TariffPlanVO which contains tariff plan details
     * @param tariffPlanVO the details of tariff plan which has to be added or updated
     * @return long value, Plan Seq Id
     * @exception throws TTKException
     */
    public long addUpdateTariffPlan(TariffPlanVO tariffPlanVO) throws TTKException
    {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	long lResult = 0;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateTariffPlanInfo);
            if(tariffPlanVO.getTariffPlanID() == null){
            	cStmtObject.setLong(PLAN_SEQ_ID,0);
            }//end of if(tariffPlanVO.getTariffPlanID() == null)
            else{
            	cStmtObject.setLong(PLAN_SEQ_ID,tariffPlanVO.getTariffPlanID());
            }//end of else
                
            cStmtObject.setString(PLAN_NAME,tariffPlanVO.getTariffPlanName());
            cStmtObject.setString(PLAN_DESCRIPTION,tariffPlanVO.getTariffPlanDesc());
            cStmtObject.setLong(USER_SEQ_ID, tariffPlanVO.getUpdatedBy());
            cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
            cStmtObject.registerOutParameter(PLAN_SEQ_ID,Types.BIGINT);
            cStmtObject.execute();
            lResult = cStmtObject.getLong(PLAN_SEQ_ID);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
        			log.error("Error while closing the Statement in TariffPlanDAOImpl addUpdatePlan()",sqlExp);
        			throw new TTKException(sqlExp, "tariffplan");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TariffPlanDAOImpl addUpdatePlan()",sqlExp);
        				throw new TTKException(sqlExp, "tariffplan");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tariffplan");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lResult;
    }//end of addUpdatePlan(TariffPlanVO tariffPlanVO)

    /**
     * This method delete's the tariff plan's records from the database.
     * @param alTariffPlanList ArrayList object which contains the tariff plans id's to be deleted
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteTariffPlan(ArrayList alTariffPlanList) throws TTKException{
        int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try
        {
            if(alTariffPlanList != null && alTariffPlanList.size() > 0)
            {
                conn = ResourceManager.getConnection();
                cStmtObject = conn.prepareCall(strDeleteTariffPlanInfo);
                cStmtObject.setString(1, (String)alTariffPlanList.get(0));//string of sequence id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.registerOutParameter(2, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(2);
            }//end of if(alTariffPlanList != null && alTariffPlanList.size() > 0)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
        			log.error("Error while closing the Statement in TariffPlanDAOImpl deleteTariffPlan()",sqlExp);
        			throw new TTKException(sqlExp, "tariffplan");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TariffPlanDAOImpl deleteTariffPlan()",sqlExp);
        				throw new TTKException(sqlExp, "tariffplan");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tariffplan");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of deleteTariffPlan(ArrayList alTariffPlanList)

    /**
     * This method adds/updates the RateVO which contains room rates details
     * @param rateVO the details which has to be added or updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addUpdateRates(RateVO rateVO) throws TTKException {
        int iResult = 1;
        StringBuffer sbfSQL = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        Statement stmt = null;
        try{
            conn = ResourceManager.getConnection();
            conn.setAutoCommit(false);
            stmt = (java.sql.Statement)conn.createStatement();
            if(rateVO.getRates() != null){
                for(int i=0;i<rateVO.getPkgCostSeqIdList().length;i++){
                    sbfSQL = new StringBuffer();
                    sbfSQL = sbfSQL.append("'"+rateVO.getSelRevPlanSeqId()+"',");
                    sbfSQL = sbfSQL.append("'"+rateVO.getRevPlanSeqId()+"',");
                    sbfSQL = sbfSQL.append("'"+rateVO.getPkgCostSeqIdList()[i]+"',");
                    sbfSQL = sbfSQL.append("'"+rateVO.getPkgDetailSeqIdList()[i]+"',");

                    if(rateVO.getRates()[i].equals("")){
                    	sbfSQL = sbfSQL.append(""+null+",");
                    }//end of if(rateVO.getRates()[i].equals(""))
                    else{
                    	sbfSQL = sbfSQL.append(""+rateVO.getRates()[i]+",");
                    }//end of else

                    if(rateVO.getDiscountList() != null){
                    	if(rateVO.getDiscountList()[i].equals("")){
                        	sbfSQL = sbfSQL.append(""+null+",");//Discount On Services
                        }//end of if(rateVO.getDiscountList()[i].equals(""))
                        else{
                        	sbfSQL = sbfSQL.append(""+rateVO.getDiscountList()[i]+",");
                        }//end of else
                    }//end of if(rateVO.getDiscountList() != null)
                    else{
                    	sbfSQL = sbfSQL.append(""+null+",");//Discount On Services
                    }//end of else

                    sbfSQL = sbfSQL.append("'"+rateVO.getUpdatedBy()+"')}");
                    stmt.addBatch(strAddUpdateRatesInfo+sbfSQL.toString());
               }//end of for
            }//end of if(rateVO.getRates() != null)
            stmt.executeBatch();
            stmt.close();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCheckPackageInfo);
            if(rateVO.getSelRevPlanSeqId() != null){
            	cStmtObject.setLong(1,rateVO.getSelRevPlanSeqId());
            }//end of if(rateVO.getSelRevPlanSeqId() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else
                
            if(rateVO.getPkgSeqId() != null){
            	cStmtObject.setLong(2,rateVO.getPkgSeqId());
            }//end of if(rateVO.getPkgSeqId() != null)
            else{
            	cStmtObject.setLong(2,0);
            }//end of else
                
            cStmtObject.setString(3,rateVO.getAvblGnrlTypeId());
            cStmtObject.setLong(4,rateVO.getUpdatedBy());//USER_SEQ_ID
            cStmtObject.execute();
            cStmtObject.close();
            conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
            try {
                conn.rollback();
                throw new TTKException(sqlExp, "tariffplan");
            } //end of try
            catch (SQLException sExp) {
                throw new TTKException(sExp, "tariffplan");
            }//end of catch (SQLException sExp)
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            try {
                conn.rollback();
                throw new TTKException(exp, "tariffplan");
            } //end of try
            catch (SQLException sqlExp) {
                throw new TTKException(sqlExp, "tariffplan");
            }//end of catch (SQLException sqlExp)
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try{
        			if (stmt != null) stmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in TariffPlanDAOImpl addUpdateRates()",sqlExp);
        			throw new TTKException(sqlExp, "tariffplan");
        		}//end of catch (SQLException sqlExp)
        		finally{ // Even if statement is not closed, control reaches here. Try closing the Callabale Statement now.
        			try
            		{
            			if (cStmtObject != null) cStmtObject.close();
            		}//end of try
            		catch (SQLException sqlExp)
            		{
            			log.error("Error while closing the Statement in TariffPlanDAOImpl addUpdateRates()",sqlExp);
            			throw new TTKException(sqlExp, "tariffplan");
            		}//end of catch (SQLException sqlExp)
            		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
            		{
            			try
            			{
            				if(conn != null) conn.close();
            			}//end of try
            			catch (SQLException sqlExp)
            			{
            				log.error("Error while closing the Connection in TariffPlanDAOImpl addUpdateRates()",sqlExp);
            				throw new TTKException(sqlExp, "tariffplan");
            			}//end of catch (SQLException sqlExp)
            		}//end of finally Connection Close
        		}//end of finally
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tariffplan");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		stmt = null;
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
   }//end of addUpdateRates(RateVO rateVO)

    /**
     * This method returns the ArrayList, which contains the RevisionPlanVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of RevisionPlanVO object's which contains the tariff revision plan details
     * @exception throws TTKException
     */
    public ArrayList getRevisionPlanList(ArrayList alSearchObjects) throws TTKException
    {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strRevisedPlanList;
        RevisionPlanVO revisionPlanVO = null;
        Collection<Object> alResultList = new ArrayList<Object>();
        if(alSearchObjects != null && alSearchObjects.size()-4 > 0)
        {
            String strPlanSeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
            strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strPlanSeqId);
            String strFromDate   = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(1)).getValue());
            String strToDate = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(2)).getValue());
            sbfDynamicQuery = sbfDynamicQuery.append("  WHERE ( ( from_date >= nvl(to_date( '"+strFromDate+"','dd/mm/yyyy'),to_date('1/1/2002','dd/mm/yyyy') ) AND ( NVL(TO_DATE,nvl2(to_date('"+strToDate+"','dd/mm/yyyy'),to_date('"+strToDate+"','dd/mm/yyyy')+1,from_date)) <= nvl(to_date('"+strToDate+"','dd/mm/yyyy'), NVL(TO_DATE,FROM_DATE)))))");
        }//end of  if(alSearchObjects != null && alSearchObjects.size() > 0)
        //build the Order By Clause
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));  
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        try
        {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if (rs!=null)
            {
                while (rs.next()) {
                    revisionPlanVO = new RevisionPlanVO();
                    if(rs.getString("REVISED_PLAN_SEQ_ID")!=null){
                    	revisionPlanVO.setRevPlanSeqId(new Long(rs.getLong("REVISED_PLAN_SEQ_ID")));
                    }//end of if(rs.getString("REVISED_PLAN_SEQ_ID")!=null)s
                    revisionPlanVO.setRevisionName(TTKCommon.checkNull(rs.getString("REVISION_NUMBER")));
                    revisionPlanVO.setTariffPlanName(TTKCommon.checkNull(rs.getString("Plan_Name")));
                    if (rs.getString("PLAN_SEQ_ID") != null){
                    	revisionPlanVO.setTariffPlanID(new Long(rs.getLong("PLAN_SEQ_ID")));
                    }//end of if (rs.getString("PLAN_SEQ_ID") != null)
                    revisionPlanVO.setDefaultPlanYn(TTKCommon.checkNull(rs.getString("DEFAULT_PLAN_YN")));
                    if(rs.getTimestamp("FROM_DATE") != null){
                    	revisionPlanVO.setStartDate(new java.util.Date(rs.getTimestamp("FROM_DATE").getTime()));
                    }//end of if(rs.getTimestamp("FROM_DATE") != null)
                    if(rs.getTimestamp("TO_DATE") != null){
                    	revisionPlanVO.setEndDate(new java.util.Date(rs.getTimestamp("TO_DATE").getTime()));
                    }//end of if(rs.getTimestamp("TO_DATE") != null)
                    alResultList.add(revisionPlanVO);
                }// End of  while (rs.next()
            }// End of if (rs!=null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
					log.error("Error while closing the Resultset in TariffPlanDAOImpl getRevisionPlanList()",sqlExp);
					throw new TTKException(sqlExp, "tariffplan");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffPlanDAOImpl getRevisionPlanList()",sqlExp);
						throw new TTKException(sqlExp, "tariffplan");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffPlanDAOImpl getRevisionPlanList()",sqlExp);
							throw new TTKException(sqlExp, "tariffplan");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffplan");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//End of getRevisionPlanList(ArrayList alSearchObjects, String sortColumnName, String sortOrder, String startRow, String endRow)

    /**
     * This method returns the ArrayList, which contains the PlanPackageVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of PlanPackageVO object's which contains the tariff package details
     * @exception throws TTKException
     */
    public ArrayList getPackageList(ArrayList alSearchObjects) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        Collection<Object> alRresultList = new ArrayList<Object>();
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTariffPkgNonPkgList);
            cStmtObject.setString(1,(String)alSearchObjects.get(0));
            cStmtObject.setLong(2, (Long)alSearchObjects.get(1));
            if(alSearchObjects.get(2) != null){
            	cStmtObject.setLong(3, (Long)alSearchObjects.get(2));
            }//end of if(alSearchObjects.get(2) != null)
            else{
            	cStmtObject.setLong(3,0);
            }//end of else
                
            cStmtObject.setLong(4, (Long)alSearchObjects.get(3));
            cStmtObject.setString(5,(String)alSearchObjects.get(4));
            cStmtObject.setString(6,(String)alSearchObjects.get(5));
            cStmtObject.setString(7,(String)alSearchObjects.get(6));
            cStmtObject.setString(8,(String)alSearchObjects.get(7));
            cStmtObject.setString(9,(String)alSearchObjects.get(8));
            cStmtObject.setString(10,(String)alSearchObjects.get(9));
            cStmtObject.setString(11,(String)alSearchObjects.get(10));
            cStmtObject.registerOutParameter(12,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(12);
            if(rs != null){
                PlanPackageVO planPackageVO = null;
                while (rs.next()) {
                    planPackageVO = new PlanPackageVO();
                    if(rs.getString("REVISED_PLAN_SEQ_ID") != null){
                    	planPackageVO.setRevPlanSeqId(new Long(rs.getLong("REVISED_PLAN_SEQ_ID")));
                    }//end of if(rs.getString("REVISED_PLAN_SEQ_ID") != null)
                        
                    if(rs.getString("PKG_SEQ_ID") != null){
                    	planPackageVO.setPkgSeqId(new Long(rs.getLong("PKG_SEQ_ID")));
                    }//end of if(rs.getString("PKG_SEQ_ID") != null)
                        
                    planPackageVO.setName(TTKCommon.checkNull(rs.getString("NAME")));
                    planPackageVO.setType(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    planPackageVO.setMedicalPkgYN(TTKCommon.checkNull(rs.getString("MEDICAL_PACKAGE_YN")));
                    if(TTKCommon.checkNull(rs.getString("STATUS")).equals("MOD")){
                        planPackageVO.setModified(true);
                        planPackageVO.setImageTitle("Modified");
                        planPackageVO.setImageName("ModifiedIcon");
                    }// End of if(TTKCommon.checkNull(rs.getString("STATUS")).equals("MOD"))
                    alRresultList.add(planPackageVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alRresultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
					log.error("Error while closing the Resultset in TariffPlanDAOImpl getPackageList()",sqlExp);
					throw new TTKException(sqlExp, "tariffplan");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffPlanDAOImpl getPackageList()",sqlExp);
						throw new TTKException(sqlExp, "tariffplan");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffPlanDAOImpl getPackageList()",sqlExp);
							throw new TTKException(sqlExp, "tariffplan");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffplan");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPackageList(ArrayList alSearchObjects, String sortColumnName, String sortOrder, String startRow, String endRow)

    /**
     * This method returns the ArrayList object, which contains all the details about the Room Rates
     * @param lRevisedPlanSeqId long Revised Plan Seq Id
     * @param lPkgSeqId long Package Seq Id
     * @param strGeneralTypeId String  General Type Id for Package/Non Package
     * @param strWardTypeId String  Ward Type Id for Ward Charges, for packages ward type id is null
     * @return ArrayList object which contains all the details about the Room Rates
     * @exception throws TTKException
     */
    public ArrayList getRates(long lRevisedPlanSeqId,long lPkgSeqId,String strGeneralTypeId,String strWardTypeId) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        RateVO rateVO = null;
        ArrayList<Object> alRatesList = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strRatesDetailInfo);
            cStmtObject.setLong(1, lPkgSeqId);
            cStmtObject.setString(2, TTKCommon.checkNull(strGeneralTypeId));
            cStmtObject.setLong(3, lRevisedPlanSeqId);
            cStmtObject.setString(4, TTKCommon.checkNull(strWardTypeId));
            cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(5);
            alRatesList= new ArrayList<Object>();
            if(rs != null){
                while(rs.next()){
                    rateVO = new RateVO();
                    rateVO.setSelRevPlanSeqId(lRevisedPlanSeqId);
                    rateVO.setPkgSeqId(lPkgSeqId);
                    rateVO.setGnrlTypeId(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));
                    
                    if(rs.getString("REVISED_PLAN_SEQ_ID") != null){
                    	rateVO.setRevPlanSeqId(new Long(rs.getLong("REVISED_PLAN_SEQ_ID")));
                    }//end of if(rs.getString("REVISED_PLAN_SEQ_ID") != null)
                        
                    if(rs.getString("PKG_COST_SEQ_ID") != null){
                    	rateVO.setPkgCostSeqId(new Long(rs.getLong("PKG_COST_SEQ_ID")));
                    }//end of if(rs.getString("PKG_COST_SEQ_ID") != null)
                        
                    if(rs.getString("PKG_DETAIL_SEQ_ID") != null){
                    	rateVO.setPkgDetailSeqId(new Long(rs.getLong("PKG_DETAIL_SEQ_ID")));
                    }//end of if(rs.getString("PKG_DETAIL_SEQ_ID") != null)
                        
                    rateVO.setRoomDesc(TTKCommon.checkNull(rs.getString("ROOM_DESCRIPTION")));
                    if(rs.getString("COST") != null){
                    	rateVO.setRate(new BigDecimal(rs.getString("COST")));
                    }//end of if(rs.getString("COST") != null)
                        
                    if(rs.getString("DISCOUNT_ON_SERVICES") != null){
                    	rateVO.setDisctOnServices(new BigDecimal(rs.getString("DISCOUNT_ON_SERVICES")));
                    }//end of if(rs.getString("DISCOUNT_ON_SERVICES") != null)
                    else{
                    	rateVO.setDisctOnServices(new BigDecimal(0));
                    }//end of else
                        
                    alRatesList.add(rateVO);
                }//end of while(rs.next())
           }//end of if(rs != null)
            return alRatesList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
					log.error("Error while closing the Resultset in TariffPlanDAOImpl getRates()",sqlExp);
					throw new TTKException(sqlExp, "tariffplan");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffPlanDAOImpl getRates()",sqlExp);
						throw new TTKException(sqlExp, "tariffplan");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffPlanDAOImpl getRates()",sqlExp);
							throw new TTKException(sqlExp, "tariffplan");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffplan");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getRates(long lRevisedPlanSeqId,long lPkgSeqId,String strGeneralTypeId,String strWardTypeId)

    /**
     * This method will sets the package as available or not available in the Package records in the database
     * @param alPackageList ArrayList which contains the details of the packages, which are to be available or not available
     * @param strAvblGnrlTypeId String which contains the available general type id for available or notavailable the package
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int updateAvailabilityStatus(ArrayList alPackageList,String strAvblGnrlTypeId) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	int iResult = 0;
        try{
            if(alPackageList != null && alPackageList.size() > 0)
            {
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCheckAvailability);
                cStmtObject.setLong(1,(Long)alPackageList.get(0));//REVISED_PLAN_SEQ_ID
                cStmtObject.setString(2, TTKCommon.checkNull((String)alPackageList.get(1)));//string of package sequence id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setString(3,strAvblGnrlTypeId);//AVAILABLE_GENERAL_TYPE_ID
                cStmtObject.setLong(4, (Long)alPackageList.get(2));//user sequence id
                cStmtObject.registerOutParameter(5, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(5);
            }//end of if(alPackageList != null && alPackageList.size() > 0)
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "tariffplan");
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
        			log.error("Error while closing the Statement in TariffPlanDAOImpl updateAvailabilityStatus()",sqlExp);
        			throw new TTKException(sqlExp, "tariffplan");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TariffPlanDAOImpl updateAvailabilityStatus()",sqlExp);
        				throw new TTKException(sqlExp, "tariffplan");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tariffplan");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of updateAvailabilityStatus(ArrayList alPackageList,String strAvblGnrlTypeId)

    /**
     * This method returns the ArrayList, which contains the HospitalTariffPlanDetailVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of HospitalTariffPlanDetailVO object's which contains the hospital's tariff plan details
     * @exception throws TTKException
     */
    public ArrayList getHospitalTariffDetailList(ArrayList alSearchObjects) throws TTKException
    {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strTariffDetailList;
        Collection<Object> alResultList = new ArrayList<Object>();
        InsuranceVO  insuranceVO = null;
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            String strHospSeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
            strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?",strHospSeqId);
            for(int i=1; i < alSearchObjects.size()-4; i++)
            {
            	if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
                {
            		if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
                    {
                        if(sbfDynamicQuery.toString().equals("")){
                        	sbfDynamicQuery =sbfDynamicQuery.append( " WHERE "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
                        }//end of if
                        else{
                        	sbfDynamicQuery = sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
                        }//end of else
                    }// end of   if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
                    else
                    {
                        if(sbfDynamicQuery.toString().equals("")){
                        	sbfDynamicQuery = sbfDynamicQuery.append(" WHERE UPPER("+((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
                        }//end of if
                        else{
                        	sbfDynamicQuery = sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
                        }//end of else
                    }//end of else
            	}// end of  if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
            }//end of for()
        }//end of if(alSearchObjects != null && alSearchObjects.size() > 0)
        //build the Order By Clause
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        try
        {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if (rs!=null)
            {
                while (rs.next()) {
                    insuranceVO = new InsuranceVO();
                    if (rs.getString("PROD_HOSP_SEQ_ID")!=null){
                    	insuranceVO.setProdHospSeqId(new Long(rs.getLong("PROD_HOSP_SEQ_ID")));
                    }//end of if (rs.getString("PROD_HOSP_SEQ_ID")!=null)
                    if (rs.getString("ASSOCIATED_SEQ_ID")!=null){
                    	insuranceVO.setAssociatedSeqId(new Long(rs.getLong("ASSOCIATED_SEQ_ID")));
                    }//end of if (rs.getString("ASSOCIATED_SEQ_ID")!=null)
                    insuranceVO.setGenTypeID(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));
                    insuranceVO.setCompanyName(TTKCommon.checkNull(rs.getString("ASSOCIATED_TO")));
                    insuranceVO.setProdPolicyNumber(TTKCommon.checkNull(rs.getString("PRODUCT_POLICY_NO")));
                    alResultList.add(insuranceVO);
                }//end of while
            }//End of  if (rs!=null)
            return  (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
					log.error("Error while closing the Resultset in TariffPlanDAOImpl getHospitalTariffDetailList()",sqlExp);
					throw new TTKException(sqlExp, "tariffplan");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffPlanDAOImpl getHospitalTariffDetailList()",sqlExp);
						throw new TTKException(sqlExp, "tariffplan");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffPlanDAOImpl getHospitalTariffDetailList()",sqlExp);
							throw new TTKException(sqlExp, "tariffplan");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffplan");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//End of getHospitalTariffDetailList(ArrayList alSearchObjects, String sortColumnName, String sortOrder, String startRow, String endRow)

    /**
     * This method returns the ArrayList, which contains the RevisionPlanVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of RevisionPlanVO object's which contains the tariff revision plan details
     * @exception throws TTKException
     */
    public ArrayList getHospitalRevisionPlanList(ArrayList alSearchObjects) throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strRevisionHistoryList;
        Collection<Object> alResultList = new ArrayList<Object>();
        RevisionPlanVO revisionPlanVO = null;
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            String strProdHospSeqId = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(0)).getValue());
            String strStartDate = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(1)).getValue());
            String strEnddate = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(2)).getValue());
            strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strProdHospSeqId);
            sbfDynamicQuery = sbfDynamicQuery.append("  WHERE ( ( from_date >= nvl(to_date( '"+strStartDate+"','dd/mm/yyyy'),to_date('1/1/2002','dd/mm/yyyy') ) AND ( NVL(TO_DATE,nvl2(to_date('"+strEnddate+"','dd/mm/yyyy'),to_date('"+strEnddate+"','dd/mm/yyyy')+1,from_date)) <= nvl(to_date('"+strEnddate+"','dd/mm/yyyy'), NVL(TO_DATE,FROM_DATE)))))");
        }//end of if(alSearchObjects != null && alSearchObjects.size() > 0)
        //build the Order By Clause
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        try
        {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if (rs!=null)
            {
                while (rs.next()) {
                    revisionPlanVO = new RevisionPlanVO();
                    if (rs.getString("REVISED_PLAN_SEQ_ID")!=null){
                    	revisionPlanVO.setRevPlanSeqId(new Long(rs.getLong("REVISED_PLAN_SEQ_ID")));
                    }//end of if (rs.getString("REVISED_PLAN_SEQ_ID")!=null)
                        
                    revisionPlanVO.setRevisionName(TTKCommon.checkNull(rs.getString("REVISION_NUMBER")));
                    revisionPlanVO.setTariffPlanName(TTKCommon.checkNull(rs.getString("PLAN_NAME")));
                    
                    if (rs.getString("PLAN_SEQ_ID")!=null){
                    	revisionPlanVO.setTariffPlanID(new Long(rs.getLong("PLAN_SEQ_ID")));
                    }//end of if (rs.getString("PLAN_SEQ_ID")!=null)
                        
                    if (rs.getString("DISCOUNT_OFFERED_TTK")!=null){
                    	revisionPlanVO.setDiscountOffered(new BigDecimal(rs.getString("DISCOUNT_OFFERED_TTK")));
                    }//end of if (rs.getString("DISCOUNT_OFFERED_TTK")!=null)
                        
                    if(rs.getTimestamp("FROM_DATE") != null){
                    	revisionPlanVO.setStartDate(new java.util.Date(rs.getTimestamp("FROM_DATE").getTime()));
                    }//end of if(rs.getTimestamp("FROM_DATE") != null)
                        
                    if(rs.getTimestamp("TO_DATE") != null){
                    	revisionPlanVO.setEndDate(new java.util.Date(rs.getTimestamp("TO_DATE").getTime()));
                    }//end of if(rs.getTimestamp("TO_DATE") != null)
                        
                    if (rs.getString("PROD_HOSP_SEQ_ID")!= null){
                    	revisionPlanVO.setProdHospSeqId(new Long(rs.getLong("PROD_HOSP_SEQ_ID")));
                    }//end of if (rs.getString("PROD_HOSP_SEQ_ID")!= null)
                        
                    alResultList.add(revisionPlanVO);
                }//end of while
            }//end of if (rs!=null)
            return  (ArrayList) alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
					log.error("Error while closing the Resultset in TariffPlanDAOImpl getHospitalRevisionPlanList()",sqlExp);
					throw new TTKException(sqlExp, "tariffplan");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffPlanDAOImpl getHospitalRevisionPlanList()",sqlExp);
						throw new TTKException(sqlExp, "tariffplan");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffPlanDAOImpl getHospitalRevisionPlanList()",sqlExp);
							throw new TTKException(sqlExp, "tariffplan");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffplan");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getHospitalRevisionPlanList(ArrayList alSearchObjects, String sortColumnName, String sortOrder, String startRow, String endRow)

    /**
     * This method addsthe RevisionPlanVO which contains the details of the revision of the plan
     * @param revisionPlanVO the details which has to be added
     * @return Long value the revised plan sequence id which is added/updated
     * @exception throws TTKException
     */
    public Long addRevisionPlan(RevisionPlanVO revisionPlanVO) throws TTKException
    {
        Long lResult = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddRevisedPlan);
            if (revisionPlanVO.getRevPlanSeqId() != null){
            	cStmtObject.setLong(1,revisionPlanVO.getRevPlanSeqId());
            }//end of if (revisionPlanVO.getRevPlanSeqId() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else
                
            cStmtObject.setLong(2,revisionPlanVO.getTariffPlanID());
            
            if(revisionPlanVO.getProdHospSeqId() != null){
            	cStmtObject.setLong(3,revisionPlanVO.getProdHospSeqId());
            }//end of if(revisionPlanVO.getProdHospSeqId() != null)
            else{
            	cStmtObject.setString(3,null);
            }//end of else
                
            if (revisionPlanVO.getStartDate() != null && revisionPlanVO.getStartDate()!=""){
            	cStmtObject.setTimestamp(4,new Timestamp(TTKCommon.getUtilDate(revisionPlanVO.getStartDate()).getTime()));
            }//end of if (revisionPlanVO.getStartDate() != null && revisionPlanVO.getStartDate()!="")
            else{
            	cStmtObject.setTimestamp(4, null);
            }//end of else
                
            if(revisionPlanVO.getDiscountOffered()!=null){
            	cStmtObject.setBigDecimal(5,revisionPlanVO.getDiscountOffered());
            }//end of if(revisionPlanVO.getDiscountOffered()!=null)
            else{
            	cStmtObject.setString(5,null);
            }//end of else
                
            cStmtObject.setLong(6,revisionPlanVO.getUpdatedBy());
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.registerOutParameter(7,Types.INTEGER);
            cStmtObject.execute();
            lResult = cStmtObject.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
        			log.error("Error while closing the Statement in TariffPlanDAOImpl addRevisionPlan()",sqlExp);
        			throw new TTKException(sqlExp, "tariffplan");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TariffPlanDAOImpl addRevisionPlan()",sqlExp);
        				throw new TTKException(sqlExp, "tariffplan");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tariffplan");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lResult;
    }//end of addRevisionPlan(RevisionPlanVO revisionPlanVO)

    /**
     * This method returns the ArrayList object, which contains all the details about the Insurance Companies
     * @param lHospSeqId long Package Hospital Seq Id
     * @return ArrayList object which contains all the details about the Insurance Companies
     * @exception throws TTKException
     */
    public ArrayList getAssociatedCompanyList(long lngHospSeqId) throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        ArrayList<Object> alAssociatedTo = new ArrayList<Object>();
        CacheObject cacheObject = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt=conn.prepareStatement(strAssociatedInfo);
            pStmt.setLong(1,lngHospSeqId);
            pStmt.setLong(2,lngHospSeqId);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId((rs.getString("ASSOCIATED_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("ASSOCIATED_TO")));
                    alAssociatedTo.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alAssociatedTo;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tariffplan");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tariffplan");
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
					log.error("Error while closing the Resultset in TariffPlanDAOImpl getAssociatedCompanyList()",sqlExp);
					throw new TTKException(sqlExp, "tariffplan");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TariffPlanDAOImpl getAssociatedCompanyList()",sqlExp);
						throw new TTKException(sqlExp, "tariffplan");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TariffPlanDAOImpl getAssociatedCompanyList()",sqlExp);
							throw new TTKException(sqlExp, "tariffplan");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tariffplan");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getAssociatedCompanyList(long lngHospSeqId)
}//end of TariffPlanDAOImpl
