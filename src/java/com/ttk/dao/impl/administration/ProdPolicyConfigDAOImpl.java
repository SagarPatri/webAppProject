/**
 * @ (#) ProdPolicyConfigDAOImpl.java Jun 26, 2008
 * Project 	     : TTK HealthCare Services
 * File          : ProdPolicyConfigDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 26, 2008
 *
 * @author       :  RamaKrishna K M
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.AuthAcctheadVO;
import com.ttk.dto.administration.AuthGroupVO;
import com.ttk.dto.administration.ClauseDocumentVO;
import com.ttk.dto.administration.PlanDetailVO;
import com.ttk.dto.administration.PlanVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.administration.CashbenefitVO;

/**
 * @author ramakrishna_km
 *
 */
public class ProdPolicyConfigDAOImpl implements BaseDAO,Serializable{
	
	private static Logger log = Logger.getLogger(ProdPolicyConfigDAOImpl.class);
	
	private static final String strPlanList = "{CALL ADMINISTRATION_PKG.SELECT_PLAN_LIST(?,?,?,?,?,?,?,?)}";
	//KOC 1270 for hospital cash benefit
	private static final String strCashBenefitList = "{CALL ADMINISTRATION_PKG.select_cash_benefit_list(?,?,?,?,?,?,?,?,?)}";
	private static final String strCanvalescenceBenefitList = "{CALL ADMINISTRATION_PKG.select_cash_benefit_list(?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectCashBenefitDetail = "{CALL ADMINISTRATION_PKG.select_prod_cash_benefit(?,?,?)}";
	private static final String strSelectCanvalescenceBenefitDetail = "{CALL ADMINISTRATION_PKG.select_prod_cash_benefit(?,?,?)}";
	private static final String strSaveCashBenefitDetail = "{CALL ADMINISTRATION_PKG.save_product_cash_benefit(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveCanvalescenceBenefitDetail = "{CALL ADMINISTRATION_PKG.save_product_cash_benefit(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//KOC 1270 for hospital cash benefit
	private static final String strSelectPlanDetail = "{CALL ADMINISTRATION_PKG.SELECT_PROD_PLAN(?,?)}";
	private static final String strSavePlanDetail = "{CALL ADMINISTRATION_PKG.SAVE_PRODUCT_PLAN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//KOC-1273 FOR PRAVEEN CRITICAL BENEFIT
	private static final String strSelectCriticalBenefitDetail = "{CALL ADMINISTRATION_PKG.select_prod_cash_benefit(?,?,?)}";
	private static final String strCriticalBenefitList = "{CALL ADMINISTRATION_PKG.select_cash_benefit_list(?,?,?,?,?,?,?,?,?)}";
	
	private static final String strSaveCriticalBenefitDetail = "{CALL ADMINISTRATION_PKG.save_product_critical_benefit(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//KOC-1273 FOR PRAVEEN CRITICAL BENEFIT
		
	private static final String strAuthGroupList = "{CALL PRODUCT_ADMIN_PKG.GET_AUTH_GROUP_LIST(?,?,?,?,?,?,?)}";
	private static final String strSelectAuthGroupDetail = "{CALL PRODUCT_ADMIN_PKG.SELECT_AUTH_GROUP_INFO(?,?)}";
	private static final String strSaveAuthGroupDetail = "{CALL PRODUCT_ADMIN_PKG.SAVE_AUTH_GROUP_INFO(?,?,?,?,?,?)}";
	private static final String strGroupList = "SELECT A.AUTH_GRP_SEQ_ID,A.GROUP_NAME FROM TPA_INS_AUTHLTR_GROUP A WHERE A.PROD_POLICY_SEQ_ID=?";
	private static final String strAuthAcctheadList = "{CALL PRODUCT_ADMIN_PKG.SELECT_AUTH_ACCTHEAD_LIST(?,?)}";
	private static final String strSaveAuthAcctheadInfo = "{CALL PRODUCT_ADMIN_PKG.SAVE_AUTH_ACCTHEAD(?,?,?,?)}";
	private static final String strClauseDocList = "{CALL ADMINISTRATION_PKG.SELECT_CLAUSE_DOC_LIST(?,?,?,?,?,?,?)}";
	private static final String strClauseDocDetail = "{CALL ADMINISTRATION_PKG.SELECT_CLAUSE_DOC_DETAILS(?,?)}";
	private static final String strSaveClauseDocDetail = "{CALL ADMINISTRATION_PKG.SAVE_CLAUSE_DOC_DETAILS(?,?,?,?,?,?)}";
	private static final String strDeleteClauseDocInfo = "{CALL ADMINISTRATION_PKG.CLAUSE_DOC_DELETE(?,?,?)}";
	
	private static final int PROD_PLAN_SEQ_ID = 1;
	private static final int PROD_PLAN_NAME = 2;
	private static final int PLAN_AMOUNT = 3;
	private static final int PRODUCT_SEQ_ID = 4;
	private static final int SCHEME_ID = 5;
	private static final int PLAN_CODE = 6;
	private static final int GROUP_REG_SEQ_ID = 7;
	private static final int POLICY_SEQ_ID = 8;
	private static final int FROM_AGE = 9;
	private static final int TO_AGE = 10;
	private static final int PLAN_PREMIUM = 11;
	private static final int CASH_BENEFIT_AMT = 12;
	private static final int BENEFIT_ALLOWED_DAYS = 13;
	private static final int BENEFIT_GENERAL_TYPE_ID = 14;
	private static final int ADDED_BY = 15;
	private static final int ROWS_PROCESSED = 16;
	
	private static final long lngconvBenefit = 0;
	private static final long strconvBenefit = 0;
	private static final long strconv = 0;
	
	/**
     * This method returns the ArrayList, which contains the PlanVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of PlanVO'S object's which contains the details of the Plan Information
     * @exception throws TTKException
     */
	public ArrayList<Object> getPlanList(ArrayList<Object> alSearchObjects) throws TTKException {
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        Collection<Object> resultList = new ArrayList<Object>();
        PlanVO planVO = null;
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strPlanList);
			cStmtObject.setLong(1,(Long)alSearchObjects.get(0));//prod_plan_seq_id of Product/Policy
			cStmtObject.setString(2,(String)alSearchObjects.get(1));//Flag 'PRD' - products / 'POL'- Policy
			cStmtObject.setString(3,(String)alSearchObjects.get(3));
			cStmtObject.setString(4,(String)alSearchObjects.get(4));
			cStmtObject.setString(5,(String)alSearchObjects.get(5));
			cStmtObject.setString(6,(String)alSearchObjects.get(6));
			cStmtObject.setLong(7,(Long)alSearchObjects.get(2));//added_by
			cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(8);
			if(rs != null){
				while(rs.next()){
					planVO = new PlanVO();
					
					if(rs.getString("PROD_PLAN_SEQ_ID") != null){
						planVO.setProdPlanSeqID(new Long(rs.getLong("PROD_PLAN_SEQ_ID")));
					}//end of if(rs.getString("PROD_PLAN_SEQ_ID") != null)
					
					planVO.setProdPlanName(TTKCommon.checkNull(rs.getString("PROD_PLAN_NAME")));
					
					if(rs.getString("PLAN_AMOUNT") != null){
						planVO.setPlanAmount(new BigDecimal(rs.getString("PLAN_AMOUNT")));
					}//end of if(rs.getString("PLAN_AMOUNT") != null)
					
					planVO.setPlanCode(TTKCommon.checkNull(rs.getString("PLAN_CODE")));
					
					if(rs.getString("FROM_AGE") != null){
						planVO.setFromAge(new Integer(rs.getInt("FROM_AGE")));
					}//end of if(rs.getString("FROM_AGE") != null)
					
					if(rs.getString("TO_AGE") != null){
						planVO.setToAge(new Integer(rs.getInt("TO_AGE")));
					}//end of if(rs.getString("TO_AGE") != null)
					
					if(rs.getString("PLAN_PREMIUM") != null){
						planVO.setPlanPremium(new BigDecimal(rs.getString("PLAN_PREMIUM")));
					}//end of if(rs.getString("PLAN_PREMIUM") != null)
					
					resultList.add(planVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getPlanList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getPlanList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getPlanList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
	}//end of getPlanList(ArrayList<Object> alSearchObjects)
	
	//KOC 1270 for hospital cash benefit
	public ArrayList<Object> getCashBenefitList(ArrayList<Object> alSearchObjects) throws TTKException {
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        Collection<Object> resultList = new ArrayList<Object>();
        CashbenefitVO cashbenefitVO = null;
        try{

        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strCashBenefitList);
			
			cStmtObject.setLong(1,(Long)alSearchObjects.get(0));//prod_plan_seq_id of Product/Policy
			cStmtObject.setString(2,(String)alSearchObjects.get(1));//Flag 'PRD' - products / 'POL'- Policy
			cStmtObject.setString(3,(String)alSearchObjects.get(2));
			cStmtObject.setString(4,(String)alSearchObjects.get(4));
			cStmtObject.setString(5,(String)alSearchObjects.get(5));
			cStmtObject.setString(6,(String)alSearchObjects.get(6));
			cStmtObject.setString(7,(String)alSearchObjects.get(7));
			cStmtObject.setLong(8,(Long)alSearchObjects.get(3));//added_by
			cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);
			if(rs != null){
				while(rs.next()){
					cashbenefitVO = new CashbenefitVO();
					
					if(rs.getString("ins_cash_benefit_seq_id") != null){
						cashbenefitVO.setinsCashBenefitSeqID(new Long(rs.getLong("ins_cash_benefit_seq_id")));
					}//end of if(rs.getString("PROD_PLAN_SEQ_ID") != null)

					
					cashbenefitVO.setProdPlanName(TTKCommon.checkNull(rs.getString("cash_benefit_plan_name")));
					
					cashbenefitVO.setPlanCode(TTKCommon.checkNull(rs.getString("cash_benefit_plan_code")));
					
					if(rs.getString("from_age") != null){
						cashbenefitVO.setFromAge(new Integer(rs.getInt("from_age")));
					}//end of if(rs.getString("FROM_AGE") != null)
					
					if(rs.getString("to_age") != null){
						cashbenefitVO.setToAge(new Integer(rs.getInt("to_age")));
					}//end of if(rs.getString("TO_AGE") != null)
					
					resultList.add(cashbenefitVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getCashBenefitList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getCashBenefitList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getCashBenefitList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
	}//end of getCashBenefitList(ArrayList<Object> alSearchObjects)
	//KOC 1270 hospital cash benefit
	
	
	
	//KOC 1270 for hospital cash benefit
	public ArrayList<Object> getCanvalescenceBenefitList(ArrayList<Object> alSearchObjects) throws TTKException {
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        Collection<Object> resultList = new ArrayList<Object>();
        CashbenefitVO cashbenefitVO = null;
        try{

        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strCanvalescenceBenefitList);
			
			cStmtObject.setLong(1,(Long)alSearchObjects.get(0));//prod_plan_seq_id of Product/Policy
			cStmtObject.setString(2,(String)alSearchObjects.get(1));//Flag 'PRD' - products / 'POL'- Policy
			cStmtObject.setString(3,(String)alSearchObjects.get(2));
			cStmtObject.setString(4,(String)alSearchObjects.get(4));
			cStmtObject.setString(5,(String)alSearchObjects.get(5));
			cStmtObject.setString(6,(String)alSearchObjects.get(6));
			cStmtObject.setString(7,(String)alSearchObjects.get(7));
			cStmtObject.setLong(8,(Long)alSearchObjects.get(3));//added_by
			cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);
			if(rs != null){
				while(rs.next()){
					cashbenefitVO = new CashbenefitVO();
					
					if(rs.getString("ins_conv_benf_seq_id") != null){
						cashbenefitVO.setinsConvBenfSeqID(new Long(rs.getLong("ins_conv_benf_seq_id")));
					}//end of if(rs.getString("PROD_PLAN_SEQ_ID") != null)
					
					
					cashbenefitVO.setProdPlanConvName(TTKCommon.checkNull(rs.getString("conv_benf_plan_name")));
					
					cashbenefitVO.setPlanConvCode(TTKCommon.checkNull(rs.getString("conv_benf_plan_code")));
					
					if(rs.getString("from_age") != null){
						cashbenefitVO.setFromAge(new Integer(rs.getInt("from_age")));
					}//end of if(rs.getString("FROM_AGE") != null)
					
					if(rs.getString("to_age") != null){
						cashbenefitVO.setToAge(new Integer(rs.getInt("to_age")));
					}//end of if(rs.getString("TO_AGE") != null)
					
					if(rs.getString("conv_amt") != null){
						cashbenefitVO.setConveyance(new BigDecimal(rs.getString("conv_amt")));
					}//end of if(rs.getString("conv_amt") != null)
					
					resultList.add(cashbenefitVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getCashBenefitList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getCashBenefitList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getCashBenefitList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
	}//end of getCashBenefitList(ArrayList<Object> alSearchObjects)
	//KOC 1270 hospital cash benefit
	/**
     * This method returns the PlanDetailVO, which contains all the Plan details
     * @param lngProdPlanSeqID the productplanSequenceID for which the Plan Details has to be fetched
     * @return PlanDetailVO object which contains all the Plan details
     * @exception throws TTKException
     */
    public PlanDetailVO getPlanDetail(long lngProdPlanSeqID) throws TTKException{
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PlanDetailVO planDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectPlanDetail);
			cStmtObject.setLong(1,lngProdPlanSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					planDetailVO = new PlanDetailVO();
					
					if(rs.getString("PROD_PLAN_SEQ_ID") != null){
						planDetailVO.setProdPlanSeqID(new Long(rs.getLong("PROD_PLAN_SEQ_ID")));
					}//end of if(rs.getString("PROD_PLAN_SEQ_ID") != null)
					
					planDetailVO.setProdPlanName(TTKCommon.checkNull(rs.getString("PROD_PLAN_NAME")));
					
					if(rs.getString("PLAN_AMOUNT") != null){
						planDetailVO.setPlanAmount(new BigDecimal(rs.getString("PLAN_AMOUNT")));
					}//end of if(rs.getString("PLAN_AMOUNT") != null)
					
					if(rs.getString("PRODUCT_SEQ_ID") != null){
						planDetailVO.setProdSeqID(new Long(rs.getLong("PRODUCT_SEQ_ID")));
					}//end of if(rs.getString("PRODUCT_SEQ_ID") != null)
					
					planDetailVO.setSchemeID(TTKCommon.checkNull(rs.getString("SCHEME_ID")));
					planDetailVO.setPlanCode(TTKCommon.checkNull(rs.getString("PLAN_CODE")));
					
					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						planDetailVO.setGroupRegSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("GROUP_REG_SEQ_ID") != null)
					
					if(rs.getString("POLICY_SEQ_ID") != null){
						planDetailVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)
					
					if(rs.getString("FROM_AGE") != null){
						planDetailVO.setFromAge(new Integer(rs.getInt("FROM_AGE")));
					}//end of if(rs.getString("FROM_AGE") != null)
					
					if(rs.getString("TO_AGE") != null){
						planDetailVO.setToAge(new Integer(rs.getInt("TO_AGE")));
					}//end of if(rs.getString("TO_AGE") != null)
					
					if(rs.getString("PLAN_PREMIUM") != null){
						planDetailVO.setPlanPremium(new BigDecimal(rs.getString("PLAN_PREMIUM")));
					}//end of if(rs.getString("PLAN_PREMIUM") != null)
					
					if(rs.getString("CASH_BENEFIT_AMT") != null){
						planDetailVO.setCashBenefitAmt(new BigDecimal(rs.getString("CASH_BENEFIT_AMT")));
					}//end of if(rs.getString("CASH_BENEFIT_AMT") != null)
					
					if(rs.getString("BENEFIT_ALLOWED_DAYS") != null){
						planDetailVO.setBenefitAllowDays(new Integer(rs.getInt("BENEFIT_ALLOWED_DAYS")));
					}//end of if(rs.getString("BENEFIT_ALLOWED_DAYS") != null)
					
					planDetailVO.setBenefitTypeID(TTKCommon.checkNull(rs.getString("BENEFIT_GENERAL_TYPE_ID")));
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return planDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPlanDetail(long lngProdPolicySeqID)
    
	//KOC 1270 hospital cash benefit
    public CashbenefitVO getCashBenefitDetail(long lnginsCashBenefitSeqID,String strconvBenefit) throws TTKException{
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CashbenefitVO cashbenefitVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectCashBenefitDetail);
			cStmtObject.setLong(1,lnginsCashBenefitSeqID);
			cStmtObject.setString(2,strconvBenefit);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					cashbenefitVO = new CashbenefitVO();
					
					
					if(rs.getString("ins_cash_benefit_seq_id") != null){
						cashbenefitVO.setinsCashBenefitSeqID(new Long(rs.getLong("ins_cash_benefit_seq_id")));
					}//end of if(rs.getString("PROD_PLAN_SEQ_ID") != null)
					
					//cashbenefitVO.setConvBenefit(TTKCommon.checkNull(rs.getString("cash_benefit_flg")));
					
					
					cashbenefitVO.setProdPlanName(TTKCommon.checkNull(rs.getString("cash_benefit_plan_name")));
					
					
					
					if(rs.getString("product_seq_id") != null){
						cashbenefitVO.setProdSeqID(new Long(rs.getLong("product_seq_id")));
					}//end of if(rs.getString("PRODUCT_SEQ_ID") != null)
					
					cashbenefitVO.setSchemeID(TTKCommon.checkNull(rs.getString("scheme_id")));
					cashbenefitVO.setPlanCode(TTKCommon.checkNull(rs.getString("cash_benefit_plan_code")));
					
					if(rs.getString("group_reg_seq_id") != null){
						cashbenefitVO.setGroupRegSeqID(new Long(rs.getLong("group_reg_seq_id")));
					}//end of if(rs.getString("GROUP_REG_SEQ_ID") != null)
					
					if(rs.getString("policy_seq_id") != null){
						cashbenefitVO.setPolicySeqID(new Long(rs.getLong("policy_seq_id")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)
					
					if(rs.getString("from_age") != null){
						cashbenefitVO.setFromAge(new Integer(rs.getInt("from_age")));
					}//end of if(rs.getString("FROM_AGE") != null)
					
					if(rs.getString("to_age") != null){
						cashbenefitVO.setToAge(new Integer(rs.getInt("to_age")));
					}//end of if(rs.getString("TO_AGE") != null)
					
					if(rs.getString("ROOM_AMT") !=null){
						cashbenefitVO.setRoom(new BigDecimal(rs.getString("ROOM_AMT")));
					}
					
					if(rs.getString("ACC_AMT") !=null){
						cashbenefitVO.setAccident(new BigDecimal(rs.getString("ACC_AMT")));
					}
					
					if(rs.getString("ICU_AMT") !=null){
						cashbenefitVO.setIcu(new BigDecimal(rs.getString("ICU_AMT")));
					}
					
					if(rs.getString("CONV_AMT") !=null){
						cashbenefitVO.setConveyance(new BigDecimal(rs.getString("CONV_AMT")));
					}
					
					if(rs.getString("ROOM_DAYS_CLAIM") !=null){
						cashbenefitVO.setRoomDays(new Integer(rs.getString("ROOM_DAYS_CLAIM")));
					}
					
					if(rs.getString("ACC_DAYS_CLAIM") !=null){
						cashbenefitVO.setAccidentDays(new Integer(rs.getString("ACC_DAYS_CLAIM")));
					}
					
					if(rs.getString("ICU_DAYS_CLAIM") !=null){
						cashbenefitVO.setIcuDays(new Integer(rs.getString("ICU_DAYS_CLAIM")));
					}
					
					if(rs.getString("CONV_DAYS_CLAIM") !=null){
						cashbenefitVO.setConveyanceDays(new Integer(rs.getString("CONV_DAYS_CLAIM")));
					}
					
					if(rs.getString("ROOM_PER_SI") !=null){
						cashbenefitVO.setRoomPercentage(new Integer(rs.getString("ROOM_PER_SI")));
					}
					
					if(rs.getString("ACC_PER_SI") !=null){
						cashbenefitVO.setAccidentPercentage(new Integer(rs.getString("ACC_PER_SI")));
					}
					
					if(rs.getString("ICU_PER_SI") !=null){
						cashbenefitVO.setIcuPercentage(new Integer(rs.getString("ICU_PER_SI")));
					}
					
					if(rs.getString("CONV_PER_SI") !=null){
						cashbenefitVO.setConveyancePercentage(new Integer(rs.getString("CONV_PER_SI")));
					}
					
					if(rs.getString("POLICY_DAYS") !=null){
						cashbenefitVO.setPolicyDays(new Integer(rs.getString("POLICY_DAYS")));
					}
					//koc for 1270 hospital cash benefit*/
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return cashbenefitVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPlanDetail(long lngProdPolicySeqID)
    //KOC 1270 hospital cash benefit
    
  //KOC 1270 hospital cash benefit
    public CashbenefitVO getCanvalescenceBenefitDetail(long lnginsConvBenfSeqID,String strconv) throws TTKException{
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CashbenefitVO cashbenefitVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectCanvalescenceBenefitDetail);
			cStmtObject.setLong(1,lnginsConvBenfSeqID);
			cStmtObject.setString(2,strconv);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					cashbenefitVO = new CashbenefitVO();
					
					
					if(rs.getString("ins_conv_benf_seq_id") != null){
						cashbenefitVO.setinsConvBenfSeqID(new Long(rs.getLong("ins_conv_benf_seq_id")));
					}//end of if(rs.getString("PROD_PLAN_SEQ_ID") != null)					
					
					cashbenefitVO.setProdPlanConvName(TTKCommon.checkNull(rs.getString("conv_benf_plan_name")));
					
					
					
					if(rs.getString("product_seq_id") != null){
						cashbenefitVO.setProdSeqID(new Long(rs.getLong("product_seq_id")));
					}//end of if(rs.getString("PRODUCT_SEQ_ID") != null)
					
					cashbenefitVO.setSchemeID(TTKCommon.checkNull(rs.getString("scheme_id")));
					cashbenefitVO.setPlanConvCode(TTKCommon.checkNull(rs.getString("conv_benf_plan_code")));
					
					if(rs.getString("group_reg_seq_id") != null){
						cashbenefitVO.setGroupRegSeqID(new Long(rs.getLong("group_reg_seq_id")));
					}//end of if(rs.getString("GROUP_REG_SEQ_ID") != null)
					
					if(rs.getString("policy_seq_id") != null){
						cashbenefitVO.setPolicySeqID(new Long(rs.getLong("policy_seq_id")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)
					
					if(rs.getString("from_age") != null){
						cashbenefitVO.setFromAge(new Integer(rs.getInt("from_age")));
					}//end of if(rs.getString("FROM_AGE") != null)
					
					if(rs.getString("to_age") != null){
						cashbenefitVO.setToAge(new Integer(rs.getInt("to_age")));
					}//end of if(rs.getString("TO_AGE") != null)
					
					
					if(rs.getString("CONV_AMT") !=null){
						cashbenefitVO.setConveyance(new BigDecimal(rs.getString("CONV_AMT")));
					}
					
					
					if(rs.getString("CONV_DAYS_CLAIM") !=null){
						cashbenefitVO.setConveyanceDays(new Integer(rs.getString("CONV_DAYS_CLAIM")));
					}
					
					
					if(rs.getString("CONV_PER_SI") !=null){
						cashbenefitVO.setConveyancePercentage(new Integer(rs.getString("CONV_PER_SI")));
					}
					
					if(rs.getString("POLICY_DAYS") !=null){
						cashbenefitVO.setPolicyDays(new Integer(rs.getString("POLICY_DAYS")));
					}
					//koc for 1270 hospital cash benefit*/
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return cashbenefitVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPlanDetail(long lngProdPolicySeqID)
    //KOC 1270 hospital cash benefit
	//KOC-1273 FOR PRAVEEN CRITICAL BENEFIT
    public ArrayList<Object> getCriticalBenefitList(ArrayList<Object> alSearchObjects) throws TTKException {
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        Collection<Object> resultList = new ArrayList<Object>();
        CashbenefitVO cashbenefitVO = null;
        try{

        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strCriticalBenefitList);
			
			
			
			
			
			
			
			
			cStmtObject.setLong(1,(Long)alSearchObjects.get(0));//prod_plan_seq_id of Product/Policy
			cStmtObject.setString(2,(String)alSearchObjects.get(1));//Flag 'PRD' - products / 'POL'- Policy
			cStmtObject.setString(3,(String)alSearchObjects.get(2));
			cStmtObject.setString(4,"FRM_AGE");
			cStmtObject.setString(5,(String)alSearchObjects.get(5));
			cStmtObject.setString(6,(String)alSearchObjects.get(6));
			cStmtObject.setString(7,(String)alSearchObjects.get(7));
			cStmtObject.setLong(8,(Long)alSearchObjects.get(3));//added_by
			cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);
			if(rs != null){
				while(rs.next()){
					cashbenefitVO = new CashbenefitVO();
					
					if(rs.getString("critical_illness_config_seq_id") != null){
						cashbenefitVO.setinsCriticalBenefitSeqID(new Long(rs.getLong("critical_illness_config_seq_id")));
					}//end of if(rs.getString("PROD_PLAN_SEQ_ID") != null)

					
					if(rs.getString("FRM_AGE") != null){
						cashbenefitVO.setFromAge(new Integer(rs.getInt("FRM_AGE")));
					}//end of if(rs.getString("FROM_AGE") != null)
					
					if(rs.getString("to_age") != null){
						cashbenefitVO.setToAge(new Integer(rs.getInt("to_age")));
					}//end of if(rs.getString("TO_AGE") != null)
					
					resultList.add(cashbenefitVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getCashBenefitList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getCashBenefitList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getCashBenefitList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
	}//end of getCashBenefitList(ArrayList<Object> alSearchObjects)
    //added for KOC-1273
    public int saveCriticalBenefitDetail(CashbenefitVO cashbenefitVO) throws TTKException{
        int iResult = 0;
              Connection conn = null;
              CallableStatement cStmtObject=null;
              try
              {
                    conn = ResourceManager.getConnection();
              cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCriticalBenefitDetail);
              
              if(cashbenefitVO.getinsCriticalBenefitSeqID() != null){
                    cStmtObject.setLong(1,cashbenefitVO.getinsCriticalBenefitSeqID());
                    
              }//end of if(planDetailVO.getProdPlanSeqID() != null)
              else{
                    cStmtObject.setLong(1,0);
              }//end of else
              if(cashbenefitVO.getFromAge() != null){
                    cStmtObject.setInt(2,cashbenefitVO.getFromAge());
              }//end of if(planDetailVO.getFromAge() != null)
              else{
                    cStmtObject.setString(2,null);
              }//end of else        
              if(cashbenefitVO.getToAge() != null){
                   cStmtObject.setInt(3,cashbenefitVO.getToAge());
              }//end of if(planDetailVO.getToAge() != null)
              else{
                    cStmtObject.setString(3,null);
              }//end of else       
              cStmtObject.setString(4,null); // AGE_OPTR          
              if(cashbenefitVO.getCriticalBenefitAmount() != null){
                    cStmtObject.setBigDecimal(5,cashbenefitVO.getCriticalBenefitAmount()); //Amount /*cStmtObject.setInt(5,2000);
              }//end of if(planDetailVO.getCashBenefitAmt() != null)
              else{
                    cStmtObject.setString(5,null);
              }//end of else       
              if(cashbenefitVO.getSurvivalPeriod() != null){
                    cStmtObject.setInt(6,cashbenefitVO.getSurvivalPeriod()); //survival period //cStmtObject.setInt(6,90);
              }//end of if(planDetailVO.getToAge() != null)
              else{
                  cStmtObject.setString(6,null);

              }//end of else

              if(cashbenefitVO.getGroupRegSeqID() != null){
                    cStmtObject.setLong(7,cashbenefitVO.getGroupRegSeqID());
              }//end of if(planDetailVO.getGroupRegSeqID() != null)
              else{
                    cStmtObject.setString(7,null);
              }//end of else
              if(cashbenefitVO.getPolicySeqID() != null){
                    cStmtObject.setLong(8,cashbenefitVO.getPolicySeqID());
              }//end of if(planDetailVO.getPolicySeqID() != null)
              else{
                    cStmtObject.setString(8,null);
              }//end of else
              if(cashbenefitVO.getProdSeqID() != null){
                    cStmtObject.setLong(9,cashbenefitVO.getProdSeqID());
              }//end of if(planDetailVO.getProdSeqID() != null)
              else{
                    cStmtObject.setString(9,null);
              }//end of else
              cStmtObject.setString(10,cashbenefitVO.getCriticalTypeID()); // Critical Group  //cStmtObject.setString(10,"CTL");
              
              
              if(cashbenefitVO.getNoOfTimes() != null){
                    cStmtObject.setInt(11,cashbenefitVO.getNoOfTimes()); // No of Times //cStmtObject.setInt(11,1);
              }//end of if(planDetailVO.getFromAge() != null)
              else{
                    cStmtObject.setString(11,null);
              }//end of else
              
              if(cashbenefitVO.getSumInsuredPer() != null){
              	cStmtObject.setBigDecimal(12,cashbenefitVO.getSumInsuredPer()); //Sum Insured//cStmtObject.setInt(11,1); 
              }//end of if
              else{
              	cStmtObject.setString(12,null);
              }//end of else
              
              if(cashbenefitVO.getWaitingPeriod()!=null)
              {
            	  cStmtObject.setInt(13,cashbenefitVO.getWaitingPeriod()); //WaitingPeriod//cStmtObject.setInt(13,1);
              }
              else{
                cStmtObject.setString(13,null);
              }//end of else
               
              cStmtObject.registerOutParameter(14,Types.INTEGER);
              cStmtObject.execute();
              iResult = cStmtObject.getInt(14);
              }

              catch (SQLException sqlExp)
          {
              throw new TTKException(sqlExp, "prodpolicyconfig");
          }//end of catch (SQLException sqlExp)
          catch (Exception exp)
          {

              throw new TTKException(exp, "prodpolicyconfig");
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
                          log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl saveCriticalPlanDetail()",sqlExp);
                          throw new TTKException(sqlExp, "prodpolicyconfig");
                    }//end of catch (SQLException sqlExp)
                    finally // Even if statement is not closed, control reaches here. Try closing the connection now.
                    {
                          try
                          {
                                if(conn != null) conn.close();
                          }//end of try
                          catch (SQLException sqlExp)
                          {
                                log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl saveCriticalPlanDetail()",sqlExp);
                               throw new TTKException(sqlExp, "prodpolicyconfig");
                          }//end of catch (SQLException sqlExp)
                    }//end of finally Connection Close
              }//end of try
              catch (TTKException exp)
              {
                    throw new TTKException(exp, "prodpolicyconfig");
              }//end of catch (TTKException exp)
              finally // Control will reach here in anycase set null to the objects
              {
                    cStmtObject = null;
                   conn = null;
              }//end of finally
              }//end of finally
          return iResult;
      }
    //added for KOC-1273
    public CashbenefitVO getCriticalBenefitDetail(long lnginsCriticalBenefitSeqID, String strcriticalBenefit) throws TTKException{
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CashbenefitVO cashbenefitVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectCriticalBenefitDetail);
			cStmtObject.setLong(1,lnginsCriticalBenefitSeqID);
			cStmtObject.setString(2,strcriticalBenefit);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){

                while(rs.next()){
                	cashbenefitVO = new CashbenefitVO();
                      if(rs.getString("critical_illness_config_seq_id") != null){

                    	  cashbenefitVO.setinsCriticalBenefitSeqID(new Long(rs.getLong("critical_illness_config_seq_id")));

                            

                      }//end of if(rs.getString("PROD_PLAN_SEQ_ID") != null)

                      if(rs.getString("FRM_AGE") != null){

                            

                            cashbenefitVO.setFromAge(new Integer(rs.getInt("FRM_AGE")));

                      }//end of if(rs.getString("FROM_AGE") != null)

                      if(rs.getString("TO_AGE") != null){

                            

                            cashbenefitVO.setToAge(new Integer(rs.getInt("TO_AGE")));

                      }//end of if(rs.getString("TO_AGE") != null)

                      if(rs.getString("amount") != null){

                    	  cashbenefitVO.setCriticalBenefitAmount(new BigDecimal(rs.getString("amount")));

                      }//end of (rs.getString("amount") != null)
                      
                      if(rs.getString("sum_ins_per")!=null)
                      {
                    	  cashbenefitVO.setSumInsuredPer(new BigDecimal(rs.getString("sum_ins_per"))); // sumInsured Percentage
                      }
                      
                      if(rs.getString("surv_prd") != null){

                    	  cashbenefitVO.setSurvivalPeriod(new Integer(rs.getInt("surv_prd")));

                      }//if(rs.getString("surv_prd") != null){
                      if(rs.getString("waiting_period")!=null)
                      {
                    	  cashbenefitVO.setWaitingPeriod(new Integer(rs.getInt("waiting_period")));
                      }
                      cashbenefitVO.setCriticalTypeID(TTKCommon.checkNull(rs.getString("critical_grp")));

                      if(rs.getString("no_of_times") != null){

                    	  cashbenefitVO.setNoOfTimes(new Integer(rs.getInt("no_of_times")));

                      }//if(rs.getString("no_of_times") != null){

                      if(rs.getString("GROUP_REG_SEQ_ID") != null){

                    	  cashbenefitVO.setGroupRegSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));

                      }//end of if(rs.getString("GROUP_REG_SEQ_ID") != null)

                      if(rs.getString("POLICY_SEQ_ID") != null){

                    	  cashbenefitVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));

                      }//end of if(rs.getString("POLICY_SEQ_ID") != null)
                       if(rs.getString("PRODUCT_SEQ_ID") != null){

                    	   cashbenefitVO.setProdSeqID(new Long(rs.getLong("PRODUCT_SEQ_ID")));

                      }//end of if(rs.getString("PRODUCT_SEQ_ID") != null)

                }
          }
			return cashbenefitVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getPlanDetail()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPlanDetail(long lngProdPolicySeqID)
    
    //KOC FOR PRAVEEN CRITICAL BENEFIT
    
    /**
     * This method saves the Plan information
     * @param policyDetailVO the object which contains the Plan Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int savePlanDetail(PlanDetailVO planDetailVO) throws TTKException{
    	int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePlanDetail);
            
            if(planDetailVO.getProdPlanSeqID() != null){
            	cStmtObject.setLong(PROD_PLAN_SEQ_ID,planDetailVO.getProdPlanSeqID());
            }//end of if(planDetailVO.getProdPlanSeqID() != null)
            else{
            	cStmtObject.setLong(PROD_PLAN_SEQ_ID,0);
            }//end of else
            
            cStmtObject.setString(PROD_PLAN_NAME,planDetailVO.getProdPlanName());
            
            if(planDetailVO.getPlanAmount() != null){
            	cStmtObject.setBigDecimal(PLAN_AMOUNT,planDetailVO.getPlanAmount());
            }//end of if(planDetailVO.getPlanAmount() != null)
            else{
            	cStmtObject.setString(PLAN_AMOUNT,null);
            }//end of else
            
            if(planDetailVO.getProdSeqID() != null){
            	cStmtObject.setLong(PRODUCT_SEQ_ID,planDetailVO.getProdSeqID());
            }//end of if(planDetailVO.getProdSeqID() != null)
            else{
            	cStmtObject.setString(PRODUCT_SEQ_ID,null);
            }//end of else
            
            cStmtObject.setString(SCHEME_ID,planDetailVO.getSchemeID());
            cStmtObject.setString(PLAN_CODE,planDetailVO.getPlanCode());
            
            if(planDetailVO.getGroupRegSeqID() != null){
            	cStmtObject.setLong(GROUP_REG_SEQ_ID,planDetailVO.getGroupRegSeqID());
            }//end of if(planDetailVO.getGroupRegSeqID() != null)
            else{
            	cStmtObject.setString(GROUP_REG_SEQ_ID,null);
            }//end of else
            
            if(planDetailVO.getPolicySeqID() != null){
            	cStmtObject.setLong(POLICY_SEQ_ID,planDetailVO.getPolicySeqID());
            }//end of if(planDetailVO.getPolicySeqID() != null)
            else{
            	cStmtObject.setString(POLICY_SEQ_ID,null);
            }//end of else
            
            if(planDetailVO.getFromAge() != null){
            	cStmtObject.setInt(FROM_AGE,planDetailVO.getFromAge());
            }//end of if(planDetailVO.getFromAge() != null)
            else{
            	cStmtObject.setString(FROM_AGE,null);
            }//end of else
            
            if(planDetailVO.getToAge() != null){
            	cStmtObject.setInt(TO_AGE,planDetailVO.getToAge());
            }//end of if(planDetailVO.getToAge() != null)
            else{
            	cStmtObject.setString(TO_AGE,null);
            }//end of else
            
            if(planDetailVO.getPlanPremium() != null){
            	cStmtObject.setBigDecimal(PLAN_PREMIUM,planDetailVO.getPlanPremium());
            }//end of if(planDetailVO.getPlanPremium() != null)
            else{
            	cStmtObject.setString(PLAN_PREMIUM,null);
            }//end of else
            
            if(planDetailVO.getCashBenefitAmt() != null){
            	cStmtObject.setBigDecimal(CASH_BENEFIT_AMT,planDetailVO.getCashBenefitAmt());
            }//end of if(planDetailVO.getCashBenefitAmt() != null)
            else{
            	cStmtObject.setString(CASH_BENEFIT_AMT,null);
            }//end of else
            
            if(planDetailVO.getBenefitAllowDays() != null){
            	cStmtObject.setInt(BENEFIT_ALLOWED_DAYS,planDetailVO.getBenefitAllowDays());
            }//end of if(planDetailVO.getBenefitAllowDays() != null)
            else{
            	cStmtObject.setString(BENEFIT_ALLOWED_DAYS,null);
            }//end of else
            
            cStmtObject.setString(BENEFIT_GENERAL_TYPE_ID,planDetailVO.getBenefitTypeID());
            cStmtObject.setLong(ADDED_BY,planDetailVO.getUpdatedBy());
            cStmtObject.registerOutParameter(ROWS_PROCESSED,Types.INTEGER);
            cStmtObject.registerOutParameter(PROD_PLAN_SEQ_ID,Types.BIGINT);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(ROWS_PROCESSED); 
		}//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyconfig");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyconfig");
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
        			log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl savePlanDetail()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyconfig");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl savePlanDetail()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyconfig");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyconfig");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of savePlanDetail(PlanDetailVO planDetailVO)
    
	//KOC for 1270 hospital cash benefit
    public int saveCashBenefitDetail(CashbenefitVO CashbenefitVO) throws TTKException{
    	int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCashBenefitDetail);
            if(CashbenefitVO.getinsCashBenefitSeqID() != null){
            	cStmtObject.setLong(1,CashbenefitVO.getinsCashBenefitSeqID());//ins_cash_benefit_seq_id
            }//end of if(CashbenefitVO.getinsCashBenefitSeqID() != null)
            else{
            	cStmtObject.setLong(1, 0);
            }//end of else

            if((CashbenefitVO.getConvBenefit()!= "") )
    		{
    			cStmtObject.setString(2,CashbenefitVO.getConvBenefit());
    		}
    		else
    		{
    			cStmtObject.setString(2,"HCB");
    		}//end of else
           // cStmtObject.setString(2,CashbenefitVO.getConvBenefit());//cash_benefit_flg

            cStmtObject.setString(3,CashbenefitVO.getProdPlanName());//cash_benefit_plan_name
            
            
            if(CashbenefitVO.getProdSeqID() != null){
            	cStmtObject.setLong(4,CashbenefitVO.getProdSeqID());//PRODUCT_SEQ_ID
            }//end of if(planDetailVO.getProdSeqID() != null)
            else{
            	cStmtObject.setString(4,null);
            }//end of else
            
            cStmtObject.setString(5,CashbenefitVO.getSchemeID());//SCHEME_ID
            cStmtObject.setString(6,CashbenefitVO.getPlanCode());//cash_benefit_plan_code
            
            if(CashbenefitVO.getGroupRegSeqID() != null){
            	cStmtObject.setLong(7,CashbenefitVO.getGroupRegSeqID());//GROUP_REG_SEQ_ID
            }//end of if(planDetailVO.getGroupRegSeqID() != null)
            else{
            	cStmtObject.setString(7,null);
            }//end of else
            
            if(CashbenefitVO.getPolicySeqID() != null){
            	cStmtObject.setLong(8,CashbenefitVO.getPolicySeqID());//POLICY_SEQ_ID
            }//end of if(CashbenefitVO.getPolicySeqID() != null)
            else{
            	cStmtObject.setString(8,null);
            }//end of else
            
            if(CashbenefitVO.getFromAge() != null){
            	cStmtObject.setInt(9,CashbenefitVO.getFromAge());//FROM_AGE
            }//end of if(planDetailVO.getFromAge() != null)
            else{
            	cStmtObject.setString(9,null);
            }//end of else
            
            if(CashbenefitVO.getToAge() != null){
            	cStmtObject.setInt(10,CashbenefitVO.getToAge());//TO_AGE
            }//end of if(planDetailVO.getToAge() != null)
            else{
            	cStmtObject.setString(10,null);
            }//end of else
            
        
            if(CashbenefitVO.getRoom() != null){
            	cStmtObject.setBigDecimal(12,CashbenefitVO.getRoom());//ROOM_AMT
            }
            else{
            	cStmtObject.setString(12,null);
            }
            
            if(CashbenefitVO.getAccident() != null){
            	cStmtObject.setBigDecimal(13,CashbenefitVO.getAccident());//ACC_AMT
            }
            else{
            	cStmtObject.setString(13,null);
            }
            
            if(CashbenefitVO.getIcu() != null){
            	cStmtObject.setBigDecimal(14,CashbenefitVO.getIcu());//ICU_AMT
            }
            else{
            	cStmtObject.setString(14,null);
            }
            
            if(CashbenefitVO.getConveyance() != null){
            	cStmtObject.setBigDecimal(15,CashbenefitVO.getConveyance());//CONV_AMT
            }
            else{
            	cStmtObject.setString(15,null);
            }
            
            if(CashbenefitVO.getRoomDays() != null){
            	cStmtObject.setInt(16,CashbenefitVO.getRoomDays());//ROOM_DAYS_CLAIM
            }
            else{
            	cStmtObject.setString(16,null);
            }
            
            if(CashbenefitVO.getAccidentDays() != null){
            	cStmtObject.setInt(17,CashbenefitVO.getAccidentDays());//ACC_DAYS_CLAIM
            }
            else{
            	cStmtObject.setString(17,null);
            }
            
            if(CashbenefitVO.getIcuDays() != null){
            	cStmtObject.setInt(18,CashbenefitVO.getIcuDays());//ICU_DAYS_CLAIM
            }
            else{
            	cStmtObject.setString(18,null);
            }
            
            if(CashbenefitVO.getConveyanceDays() != null){
            	cStmtObject.setInt(19,CashbenefitVO.getConveyanceDays());//CONV_DAYS_CLAIM
            }
            else{
            	cStmtObject.setString(19,null);
            }
            
            
            if(CashbenefitVO.getRoomPercentage() != null){
            	cStmtObject.setInt(20,CashbenefitVO.getRoomPercentage());//ROOM_PER_SI
            }
            else{
            	cStmtObject.setString(20,null);//ROOM_PER_SI
            }
            
            if(CashbenefitVO.getAccidentPercentage() != null){
            	cStmtObject.setInt(21,CashbenefitVO.getAccidentPercentage());//ACC_PER_SI
            }
            else{
            	cStmtObject.setString(21,null);
            }
            
            if(CashbenefitVO.getIcuPercentage() != null){
            	cStmtObject.setInt(22,CashbenefitVO.getIcuPercentage());//ICU_PER_SI
            }
            else{
            	cStmtObject.setString(22,null);
            }
            
            if(CashbenefitVO.getConveyancePercentage() != null){
            	cStmtObject.setInt(23,CashbenefitVO.getConveyancePercentage());//CONV_PER_SI
            }
            else{
            	cStmtObject.setString(23,null);
            }
            
            if(CashbenefitVO.getPolicyDays() != null){
            	cStmtObject.setInt(24,CashbenefitVO.getPolicyDays());//POLICY_DAYS
            }
            else{
            	cStmtObject.setString(24,null);
            }
          //KOC for 1270 hospital cash benefit
            cStmtObject.setLong(11,CashbenefitVO.getUpdatedBy());//ADDED_BY
            cStmtObject.registerOutParameter(25,Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(25); 
		}//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyconfig");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyconfig");
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
        			log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl saveCashBenefitDetail()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyconfig");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl saveCashBenefitDetail()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyconfig");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyconfig");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of savePlanDetail(CashbenefitVO CashbenefitVO)
  //KOC 1270 for hospital cash benefit
  //KOC 1270 for hospital cash benefit
    public int saveCanvalescenceBenefitDetail(CashbenefitVO CashbenefitVO) throws TTKException{
    	int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCanvalescenceBenefitDetail);
            if(CashbenefitVO.getinsConvBenfSeqID() != null){
            	cStmtObject.setLong(1,CashbenefitVO.getinsConvBenfSeqID());//ins_conv_benf_seq_id
            }//end of if(CashbenefitVO.getinsCashBenefitSeqID() != null)
            else{
            	cStmtObject.setLong(1, 0);
            }//end of else
            if((CashbenefitVO.getconv() != "") )
    		{
            	
    			cStmtObject.setString(2,"CONV_BENEF");
    		}
    		else
    		{
    	
    			cStmtObject.setString(2,"CONV_BENEF");
    		}//end of else
            cStmtObject.setString(3,CashbenefitVO.getProdPlanConvName());//cash_benefit_plan_name
            
            if(CashbenefitVO.getProdSeqID() != null){
            	cStmtObject.setLong(4,CashbenefitVO.getProdSeqID());//PRODUCT_SEQ_ID
            }//end of if(planDetailVO.getProdSeqID() != null)
            else{
            	cStmtObject.setString(4,null);
            }//end of else
            
            cStmtObject.setString(5,CashbenefitVO.getSchemeID());//SCHEME_ID
            cStmtObject.setString(6,CashbenefitVO.getPlanConvCode());//cash_benefit_plan_code
            
            if(CashbenefitVO.getGroupRegSeqID() != null){
            	cStmtObject.setLong(7,CashbenefitVO.getGroupRegSeqID());//GROUP_REG_SEQ_ID
            }//end of if(planDetailVO.getGroupRegSeqID() != null)
            else{
            	cStmtObject.setString(7,null);
            }//end of else
            
            if(CashbenefitVO.getPolicySeqID() != null){
            	cStmtObject.setLong(8,CashbenefitVO.getPolicySeqID());//POLICY_SEQ_ID
            }//end of if(CashbenefitVO.getPolicySeqID() != null)
            else{
            	cStmtObject.setString(8,null);
            }//end of else
            
            if(CashbenefitVO.getFromAge() != null){
            	cStmtObject.setInt(9,CashbenefitVO.getFromAge());//FROM_AGE
            }//end of if(planDetailVO.getFromAge() != null)
            else{
            	cStmtObject.setString(9,null);
            }//end of else
            
            if(CashbenefitVO.getToAge() != null){
            	cStmtObject.setInt(10,CashbenefitVO.getToAge());//TO_AGE
            }//end of if(planDetailVO.getToAge() != null)
            else{
            	cStmtObject.setString(10,null);
            }//end of else
            
        
            if(CashbenefitVO.getRoom() != null){
            	cStmtObject.setBigDecimal(12,CashbenefitVO.getRoom());//ROOM_AMT
            }
            else{
            	cStmtObject.setString(12,null);
            }
            
            if(CashbenefitVO.getAccident() != null){
            	cStmtObject.setBigDecimal(13,CashbenefitVO.getAccident());//ACC_AMT
            }
            else{
            	cStmtObject.setString(13,null);
            }
            
            if(CashbenefitVO.getIcu() != null){
            	cStmtObject.setBigDecimal(14,CashbenefitVO.getIcu());//ICU_AMT
            }
            else{
            	cStmtObject.setString(14,null);
            }
            
            if(CashbenefitVO.getConveyance() != null){
            	cStmtObject.setBigDecimal(15,CashbenefitVO.getConveyance());//CONV_AMT
            }
            else{
            	cStmtObject.setString(15,null);
            }
            
            if(CashbenefitVO.getRoomDays() != null){
            	cStmtObject.setInt(16,CashbenefitVO.getRoomDays());//ROOM_DAYS_CLAIM
            }
            else{
            	cStmtObject.setString(16,null);
            }
            
            if(CashbenefitVO.getAccidentDays() != null){
            	cStmtObject.setInt(17,CashbenefitVO.getAccidentDays());//ACC_DAYS_CLAIM
            }
            else{
            	cStmtObject.setString(17,null);
            }
            
            if(CashbenefitVO.getIcuDays() != null){
            	cStmtObject.setInt(18,CashbenefitVO.getIcuDays());//ICU_DAYS_CLAIM
            }
            else{
            	cStmtObject.setString(18,null);
            }
            
            if(CashbenefitVO.getConveyanceDays() != null){
            	cStmtObject.setInt(19,CashbenefitVO.getConveyanceDays());//CONV_DAYS_CLAIM
            }
            else{
            	cStmtObject.setString(19,null);
            }
            
            
            if(CashbenefitVO.getRoomPercentage() != null){
            	cStmtObject.setInt(20,CashbenefitVO.getRoomPercentage());//ROOM_PER_SI
            }
            else{
            	cStmtObject.setString(20,null);//ROOM_PER_SI
            }
            
            if(CashbenefitVO.getAccidentPercentage() != null){
            	cStmtObject.setInt(21,CashbenefitVO.getAccidentPercentage());//ACC_PER_SI
            }
            else{
            	cStmtObject.setString(21,null);
            }
            
            if(CashbenefitVO.getIcuPercentage() != null){
            	cStmtObject.setInt(22,CashbenefitVO.getIcuPercentage());//ICU_PER_SI
            }
            else{
            	cStmtObject.setString(22,null);
            }
            
            if(CashbenefitVO.getConveyancePercentage() != null){
            	cStmtObject.setInt(23,CashbenefitVO.getConveyancePercentage());//CONV_PER_SI
            }
            else{
            	cStmtObject.setString(23,null);
            }
            
            if(CashbenefitVO.getPolicyDays() != null){
            	cStmtObject.setInt(24,CashbenefitVO.getPolicyDays());//POLICY_DAYS
            }
            else{
            	cStmtObject.setString(24,null);
            }
          //KOC for 1270 hospital cash benefit
            cStmtObject.setLong(11,CashbenefitVO.getUpdatedBy());//ADDED_BY
            cStmtObject.registerOutParameter(25,Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(25); 
		}//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyconfig");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyconfig");
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
        			log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl saveCashBenefitDetail()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyconfig");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl saveCashBenefitDetail()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyconfig");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyconfig");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of savePlanDetail(CashbenefitVO CashbenefitVO)
  //KOC 1270 for hospital cash benefit
    /**
     * This method returns the ArrayList, which contains the AuthGroupVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of AuthGroupVO'S object's which contains the details of the Auth Group Information
     * @exception throws TTKException
     */
    public ArrayList<Object> getAuthGroupList(ArrayList<Object> alSearchObjects) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        Collection<Object> resultList = new ArrayList<Object>();
        AuthGroupVO authGroupVO = null;
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strAuthGroupList);
			cStmtObject.setLong(1,(Long)alSearchObjects.get(0));//PROD_POLICY_SEQ_ID of Product
			cStmtObject.setString(2,(String)alSearchObjects.get(2));//sort_var
			cStmtObject.setString(3,(String)alSearchObjects.get(3));//sort_order
			cStmtObject.setString(4,(String)alSearchObjects.get(4));//start_num
			cStmtObject.setString(5,(String)alSearchObjects.get(5));//end_num
			cStmtObject.setLong(6,(Long)alSearchObjects.get(1));//added_by
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			if(rs != null){
				while(rs.next()){
					authGroupVO = new AuthGroupVO();
					
					if(rs.getString("AUTH_GRP_SEQ_ID") != null){
						authGroupVO.setAuthGroupSeqID(new Long(rs.getLong("AUTH_GRP_SEQ_ID")));
					}//end of if(rs.getString("AUTH_GRP_SEQ_ID") != null)
					
					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						authGroupVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
					
					authGroupVO.setAuthGrpName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					
					resultList.add(authGroupVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getAuthGroupList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getAuthGroupList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getAuthGroupList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList<Object>)resultList;
    }//end of getAuthGroupList(ArrayList<Object> alSearchObjects)
    
    /**
     * This method returns the AuthGroupVO, which contains Auth Group details
     * @param lngAuthGrpSeqID the Auth Group Seq ID for which the Auth Group Details has to be fetched
     * @return AuthGroupVO object which contains Auth Group details
     * @exception throws TTKException
     */
    public AuthGroupVO getAuthGroupDetail(long lngAuthGrpSeqID) throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		AuthGroupVO authGroupVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectAuthGroupDetail);
			cStmtObject.setLong(1,lngAuthGrpSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					authGroupVO = new AuthGroupVO();
					
					if(rs.getString("AUTH_GRP_SEQ_ID") != null){
						authGroupVO.setAuthGroupSeqID(new Long(rs.getLong("AUTH_GRP_SEQ_ID")));
					}//end of if(rs.getString("AUTH_GRP_SEQ_ID") != null)
					
					if(rs.getString("PROD_POLICY_SEQ_ID") != null){
						authGroupVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
					
					authGroupVO.setAuthGrpName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					authGroupVO.setAuthGroupDesc(TTKCommon.checkNull(rs.getString("GROUP_DESCRIPTION")));
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return authGroupVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getAuthGroupDetail()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getAuthGroupDetail()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getAuthGroupDetail()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getAuthGroupDetail(long lngAuthGrpSeqID)
    
    /**
     * This method saves the Auth Group information
     * @param authGroupVO the object which contains the Auth Group Details which has to be  saved
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveAuthGroup(AuthGroupVO authGroupVO) throws TTKException {
    	int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveAuthGroupDetail);
            
            if(authGroupVO.getAuthGroupSeqID() != null){
            	cStmtObject.setLong(1,authGroupVO.getAuthGroupSeqID());
            }//end of if(authGroupVO.getAuthGroupSeqID() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else
            
            cStmtObject.setLong(2,authGroupVO.getProdPolicySeqID());
            cStmtObject.setString(3,authGroupVO.getAuthGrpName());
            cStmtObject.setString(4,authGroupVO.getAuthGroupDesc());
            cStmtObject.setLong(5,authGroupVO.getUpdatedBy());
            cStmtObject.registerOutParameter(6,Types.INTEGER);
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(6); 
		}//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyconfig");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyconfig");
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
        			log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl saveAuthGroup()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyconfig");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl saveAuthGroup()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyconfig");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyconfig");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of saveAuthGroup(AuthGroupVO authGroupVO)
    
    /**
     * This method returns the Arraylist of Cache object which contains Group details for corresponding Product
     * @param lngProdPolicySeqID long value which contains Product Policy Seq ID
     * @return ArrayList of Cache object which contains Group details for corresponding Product
     * @exception throws TTKException
     */
    public ArrayList<Object> getGroupList(long lngProdPolicySeqID) throws TTKException {
    	Connection conn1 = null;
    	PreparedStatement pStmt = null;
        ResultSet rs = null;
        CacheObject cacheObject = null;
        ArrayList<Object> alGroupList = new ArrayList<Object>();
        try{
            conn1 = ResourceManager.getConnection();
            pStmt = conn1.prepareStatement(strGroupList);
            pStmt.setLong(1,lngProdPolicySeqID);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("AUTH_GRP_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
                    alGroupList.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alGroupList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyconfig");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getGroupList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getGroupList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn1 != null) conn1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getGroupList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				pStmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
    }//end of getGroupList(long lngProdPolicySeqID)
    
    /**
	 * This method returns the ArrayList, which contains Accounthead Information associated to Product
	 * @param lngProdPolicySeqID contains the ProdPolicySeqID
	 * @return ArrayList object which contains Accounthead Information associated to Product
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getAuthAcctheadList(long lngProdPolicySeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ArrayList<Object> alAcctheadInfo = null;
		AuthAcctheadVO authAcctheadVO = null;
		ArrayList<Object> alGroupList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAuthAcctheadList);
			
			cStmtObject.setLong(1,lngProdPolicySeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			alAcctheadInfo = new ArrayList<Object>();
			
			if(rs != null){
				while(rs.next()){
					authAcctheadVO = new AuthAcctheadVO();
					
					authAcctheadVO.setWardTypeID(TTKCommon.checkNull(rs.getString("WARD_TYPE_ID")));
					authAcctheadVO.setWardDesc(TTKCommon.checkNull(rs.getString("WARD_DESCRIPTION")));
					authAcctheadVO.setIncAcctheadYN(TTKCommon.checkNull(rs.getString("INCLUDE_ACCOUNT_HEAD_YN")));
					authAcctheadVO.setAuthGrpSeqID(TTKCommon.checkNull(rs.getString("AUTH_GRP_SEQ_ID")));
					authAcctheadVO.setShowAcctheadYN(TTKCommon.checkNull(rs.getString("SHOW_ACCOUNT_HEAD_YN")));
					alGroupList = getGroupList(lngProdPolicySeqID);
					authAcctheadVO.setGroupList(alGroupList);
					
					alAcctheadInfo.add(authAcctheadVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getAuthAcctheadList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getAuthAcctheadList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getAuthAcctheadList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alAcctheadInfo;
	}//end of getAuthAcctheadList(long lngProdPolicySeqID)
	
	/**
	 * This method saves the Accounthead Information associated to Product
	 * @param alAcctheadInfo contains concatenated string of |wardtypeid|includeacctheadyn|authgrpseqid|showacctheadyn|..| 
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveSelectedAcctheadInfo(ArrayList<Object> alAcctheadInfo) throws TTKException {
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveAuthAcctheadInfo);
			
			if(alAcctheadInfo != null){
				cStmtObject.setString(1,(String)alAcctheadInfo.get(0));
				cStmtObject.setLong(2,(Long)alAcctheadInfo.get(1));
				cStmtObject.setLong(3,(Long)alAcctheadInfo.get(2));
				cStmtObject.registerOutParameter(4,Types.INTEGER);
				cStmtObject.execute();
				iResult = cStmtObject.getInt(4);
			}//end of if(alAcctheadInfo != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl saveSelectedAcctheadInfo()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl saveSelectedAcctheadInfo()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of saveSelectedAcctheadInfo(ArrayList<Object> alAcctheadInfo)
	
	/**
     * This method returns the ArrayList, which contains the ClauseDocumentVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of ClauseDocumentVO'S object's which contains the details of the Clause Document Information
     * @exception throws TTKException
     */
    public ArrayList<Object> getClauseDocList(ArrayList<Object> alSearchCriteria) throws TTKException {
    	ClauseDocumentVO clauseDocumentVO = null;
    	Collection<Object> alResultList = new ArrayList<Object>();
	    Connection conn = null;
	    CallableStatement cStmtObject=null;
	    ResultSet rs = null;
	    try{
	    	
	    	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClauseDocList);
			
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(2));
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));
			cStmtObject.setString(4,(String)alSearchCriteria.get(4));
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));
			cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);//ROWS_PROCESSED
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			if(rs != null){
				while(rs.next()){
					clauseDocumentVO = new ClauseDocumentVO();
					
	                if(rs.getString("CLAUSES_DOC_SEQ_ID") != null){
	                	clauseDocumentVO.setClauseDocSeqID(new Long(rs.getLong("CLAUSES_DOC_SEQ_ID")));
	                }//end of if(rs.getString("CLAUSES_DOC_SEQ_ID") != null)
	                
	                if(rs.getString("PROD_POLICY_SEQ_ID") != null){
	                	clauseDocumentVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
	                }//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
	                
	                clauseDocumentVO.setDocName(TTKCommon.checkNull(rs.getString("DOC_NAME")));
	                clauseDocumentVO.setDocPath(TTKCommon.checkNull(rs.getString("DOC_PATH")));
	                
	                alResultList.add(clauseDocumentVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
	    	
	    	return (ArrayList<Object>)alResultList;
	    }//end of try
	    catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getClauseDocList()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getClauseDocList()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getClauseDocList()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClauseDocList(ArrayList<Object> alSearchCriteria)
    
    /**
	 * This method returns the ClauseDocumentVO, which contains Clause Document details
	 * @param lngClauseDocSeqID Clause Doc Seq ID
	 * @return ClauseDocumentVO object which contains Clause Document details
	 * @exception throws TTKException
	 */
    public ClauseDocumentVO getClauseDocInfo(long lngClauseDocSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	ClauseDocumentVO clauseDocumentVO = null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClauseDocDetail);
    		
    		cStmtObject.setLong(1,lngClauseDocSeqID);
    		cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			
			if(rs != null){
				while(rs.next()){
					clauseDocumentVO = new ClauseDocumentVO();
					
					if(rs.getString("CLAUSES_DOC_SEQ_ID") != null){
	                	clauseDocumentVO.setClauseDocSeqID(new Long(rs.getLong("CLAUSES_DOC_SEQ_ID")));
	                }//end of if(rs.getString("CLAUSES_DOC_SEQ_ID") != null)
	                
	                if(rs.getString("PROD_POLICY_SEQ_ID") != null){
	                	clauseDocumentVO.setProdPolicySeqID(new Long(rs.getLong("PROD_POLICY_SEQ_ID")));
	                }//end of if(rs.getString("PROD_POLICY_SEQ_ID") != null)
	                
	                clauseDocumentVO.setDocName(TTKCommon.checkNull(rs.getString("DOC_NAME")));
	                clauseDocumentVO.setDocPath(TTKCommon.checkNull(rs.getString("DOC_PATH")));
	            }//end of while(rs.next())
			}//end of if(rs != null)
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Resultset in ProdPolicyConfigDAOImpl getClauseDocInfo()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl getClauseDocInfo()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl getClauseDocInfo()",sqlExp);
							throw new TTKException(sqlExp, "prodpolicyconfig");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return clauseDocumentVO;
    }//end of getClauseDocInfo(long lngClauseDocSeqID)
    
    /**
     * This method saves the Clause Document details
     * @param clauseDocumentVO ClauseDocumentVO object which contains the Clause Document Details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    public int saveClauseDocInfo(ClauseDocumentVO clauseDocumentVO) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		int iResult = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClauseDocDetail);
			
			if(clauseDocumentVO.getClauseDocSeqID() != null){
				cStmtObject.setLong(1,clauseDocumentVO.getClauseDocSeqID());
			}//end of if(clauseDocumentVO.getClauseDocSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else
			
			cStmtObject.setLong(2,clauseDocumentVO.getProdPolicySeqID());
			cStmtObject.setString(3,clauseDocumentVO.getDocName());
			cStmtObject.setString(4,clauseDocumentVO.getDocPath());
			cStmtObject.setLong(5,clauseDocumentVO.getUpdatedBy());
			cStmtObject.registerOutParameter(6,OracleTypes.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);
			
			return iResult;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
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
					log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl saveClauseDocInfo()",sqlExp);
					throw new TTKException(sqlExp, "prodpolicyconfig");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl saveClauseDocInfo()",sqlExp);
						throw new TTKException(sqlExp, "prodpolicyconfig");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
			catch (TTKException exp)
			{
				throw new TTKException(exp, "prodpolicyconfig");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of saveClauseDocInfo(ClauseDocumentVO clauseDocumentVO)
    
    /**
     * This method deletes the Clause Document details from the database
     * @param alDeleteList which contains the id's of the Clause Document
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteClauseDocInfo(ArrayList<Object> alDeleteList) throws TTKException {
    	int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteClauseDocInfo);
			
			cStmtObject.setString(1, (String)alDeleteList.get(0));//Concatenated string of clauses_doc_seq_ids
			cStmtObject.setLong(2,(Long)alDeleteList.get(1));    //ADDED_BY
			cStmtObject.registerOutParameter(3, Types.INTEGER);  //ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);                     //ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "prodpolicyconfig");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "prodpolicyconfig");
		}//end of catch (Exception exp)
		finally
		{
        	 //Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in ProdPolicyConfigDAOImpl deleteClauseDocInfo()",sqlExp);
        			throw new TTKException(sqlExp, "prodpolicyconfig");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ProdPolicyConfigDAOImpl deleteClauseDocInfo()",sqlExp);
        				throw new TTKException(sqlExp, "prodpolicyconfig");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "prodpolicyconfig");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//end of deleteClauseDocInfo(ArrayList<Object> alDeleteList)
	
	public static void main(String a[]) throws Exception {
		ProdPolicyConfigDAOImpl prodPolicyConfigDAO = new ProdPolicyConfigDAOImpl();
		
		/*ArrayList<Object> alAcctheadInfo = new ArrayList<Object>();
		alAcctheadInfo.add("|ROO|Y|2|Y|CON|Y|1|Y|");
		alAcctheadInfo.add(new Long(320399));
		alAcctheadInfo.add(new Long(56503));
		prodPolicyConfigDAO.saveSelectedAcctheadInfo(alAcctheadInfo);*/
		//prodPolicyConfigDAO.getAuthAcctheadList(new Long(320399));
		
		/*ClauseDocumentVO clauseDocVO = new ClauseDocumentVO();
		clauseDocVO.setProdPolicySeqID(new Long(321802));
		clauseDocVO.setDocName("Preauth Document");
		clauseDocVO.setDocPath("preauth.doc");
		clauseDocVO.setUpdatedBy(new Long(56503));
		prodPolicyConfigDAO.saveClauseDocInfo(clauseDocVO);*/
		/*ArrayList<Object> alClauseDocInfo = new ArrayList<Object>();
		alClauseDocInfo.add(new Long(321802));
		alClauseDocInfo.add(new Long(56503));
		alClauseDocInfo.add("doc_name");
		alClauseDocInfo.add("ASC");
		alClauseDocInfo.add("1");
		alClauseDocInfo.add("10");
		prodPolicyConfigDAO.getClauseDocList(alClauseDocInfo);*/
		//prodPolicyConfigDAO.getClauseDocInfo(new Long(1));
		/*ArrayList<Object> alClauseDocInfo = new ArrayList<Object>();
		alClauseDocInfo.add("|2|");
		alClauseDocInfo.add(new Long(56503));
		prodPolicyConfigDAO.deleteClauseDocInfo(alClauseDocInfo);*/
	}//end of main

}//end of ProdPolicyConfigDAOImpl
