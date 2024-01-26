/**
 *   @ (#) BrokerDAOImpl.java Dec 29, 2015
 *   Project 	   : Dubai Project
 *   File          : BrokerDAOImpl.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Dec 29, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */

package com.ttk.dao.impl.broker;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.brokerlogin.BrokerVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.onlineforms.insuranceLogin.DependentVO;

public class OnlineBrokerDAOImpl implements BaseDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static Logger log = Logger.getLogger(OnlineBrokerDAOImpl.class );
	 private static final String strPolicyDetails = "{CALL ONLINE_EXTERNAL_INFO_PKG.GET_BROKER_POLICES(?,?)}";
	 private static final String strPolicyList = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_BRO_CORP_LIST(?,?,?,?,?,?,?,?,?)}";
	 private static final String strMemberList = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_BRO_CORP_MEM_LIST(?,?,?,?,?,?,?,?,?,?)}";
	 private static final String strSummaryViewDetails = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_BRO_CORP_SUMMARY(?,?,?,?,?,?)}";
	 private static final String strDetailedViewDetails = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_BRO_CORP_MEM_DETAILS(?,?,?,?,?)}";
	 private static final String strClaimDetails = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_BRO_CLAIM_DETAILS(?,?)}";
	 private static final String strPreauthDetails = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_BRO_PREAUTH_DETAILS(?,?)}";
	 private static final String strEndrsementDetails = "{CALL ONLINE_EXTERNAL_INFO_PKG.SELECT_BRO_CORP_END_DTLS(?,?,?)}";
	 private static final String strGetTob="SELECT CASE WHEN PPI.FILE_TYPE = 'SCP' THEN 'Scanned Copy' WHEN PPI.FILE_TYPE = 'PFM' THEN 'Proposal Form' END AS FILE_TYPE, PPI.FILE_NAME, CON.CONTACT_NAME, PPI.ADDED_DATE FROM APP.POLICY_PROP_DOC_INFO PPI JOIN APP.TPA_USER_CONTACTS CON ON(PPI.ADDED_BY = CON.CONTACT_SEQ_ID) WHERE POLICY_SEQ_ID = ? ORDER BY PPI.ADDED_DATE";
	 private static final String strGetLogDetails="SELECT ROWNUM AS SINO,USER_ID,CONTACT_NAME,LOGIN_DATE FROM(SELECT ROWNUM AS SINO,C.USER_ID,L.CONTACT_NAME,to_char(H.LOGIN_DATE,'DD/MM/YYYY HH:MI:SS AM') AS LOGIN_DATE FROM APP.TPA_USER_LOGIN_HISTORY H JOIN TPA_LOGIN_INFO C ON (H.USER_ID=C.USER_ID) JOIN TPA_USER_CONTACTS L ON (L.CONTACT_SEQ_ID=C.CONTACT_SEQ_ID) WHERE C.USER_ID=? ORDER BY H.LOGIN_DATE DESC) WHERE ROWNUM<6";
	 private static final String strCorporateList="SELECT R.GROUP_REG_SEQ_ID, R.GROUP_NAME FROM tpa_user_contacts a LEFT OUTER JOIN tpa_group_user_assoc b on ( b.contact_seq_id=a.contact_seq_id ) JOIN tpa_bro_info c on (a.ins_seq_id=c.ins_seq_id) LEFT OUTER JOIN tpa_enr_policy tep  ON (tep.broker_group_id = b.group_branch_seq_id) LEFT OUTER JOIN APP.TPA_GROUP_REGISTRATION R ON (TEP.GROUP_REG_SEQ_ID = R.GROUP_REG_SEQ_ID) WHERE tep.enrol_type_id = 'COR' AND C.INS_COMP_CODE_NUMBER = ?";
	 private static final String strPolicyNumberList="SELECT TEP.POLICY_NUMBER, TEP.POLICY_SEQ_ID FROM TPA_USER_CONTACTS A  LEFT OUTER JOIN TPA_GROUP_USER_ASSOC B ON ( B.CONTACT_SEQ_ID=A.CONTACT_SEQ_ID ) JOIN TPA_BRO_INFO C ON (A.INS_SEQ_ID=C.INS_SEQ_ID) LEFT OUTER JOIN TPA_ENR_POLICY TEP ON (TEP.BROKER_GROUP_ID = B.GROUP_BRANCH_SEQ_ID) WHERE TEP.GROUP_REG_SEQ_ID = ?";
	 private static final String strPolicyPeriodList			= "SELECT TEP.EFFECTIVE_FROM_DATE||'#'||TEP.EFFECTIVE_TO_DATE AS POLICY_DATE,TO_CHAR(TEP.EFFECTIVE_FROM_DATE,'YYYY')||'-'||TO_CHAR(TEP.EFFECTIVE_TO_DATE,'YYYY') AS POLICY_PERIOD FROM APP.TPA_ENR_POLICY TEP WHERE TEP.POLICY_SEQ_ID = ? "; 		
	 private static final String strINDPolicyNumberList="SELECT TEP.POLICY_NUMBER, TEP.POLICY_SEQ_ID FROM TPA_USER_CONTACTS A LEFT OUTER JOIN TPA_GROUP_USER_ASSOC B ON ( B.CONTACT_SEQ_ID=A.CONTACT_SEQ_ID ) JOIN TPA_BRO_INFO C ON (A.INS_SEQ_ID=C.INS_SEQ_ID) LEFT OUTER JOIN TPA_ENR_POLICY TEP ON (TEP.BROKER_GROUP_ID = B.GROUP_BRANCH_SEQ_ID) WHERE TEP.ENROL_TYPE_ID = 'IND' AND C.INS_COMP_CODE_NUMBER = ?";
     private static final String strClaimTat					= "{CALL ONLINE_EXTERNAL_INFO_PKG.CLAIM_TAT_BRO_RPT (?,?,?,?,?,?,?,?)}";
     private static final String strPreAuthTat					= "{CALL ONLINE_EXTERNAL_INFO_PKG.PREAUTH_TAT_BRO_RPT (?,?,?,?,?,?,?,?)}";

	 public String[] getPolicyDetails(String brokerID)throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs=null;
		String[] policyDetails=new String[4];
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPolicyDetails);

			cStmtObject.setString(1,brokerID);			
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			
			if(rs != null){
				if(rs.next()){
					policyDetails[0]=rs.getString("POLICY_COUNT");
					policyDetails[1]=rs.getString("LIVES");
					policyDetails[2]=rs.getString("GROSS_PREMIUM");
					policyDetails[3]=rs.getString("EARNED_PREMIUM");
				}//rs if
				}//rs if
			return policyDetails;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getPolicyDetails",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getPolicyDetails",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getPolicyDetails",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally		
	}//end of getPolicyDetails(String brokerID)
	 public ArrayList<BrokerVO> getLogDetails(String brokerID) throws TTKException {
			Connection conn = null;
			PreparedStatement cStmtObject=null;
			ResultSet rs=null;
			ArrayList<BrokerVO> alBrokerVO=new ArrayList<BrokerVO>();
			
			try{
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.PreparedStatement)conn.prepareStatement(strGetLogDetails);

				cStmtObject.setString(1,brokerID);			
				rs=cStmtObject.executeQuery();
				int sNO=1;
				if(rs != null){
					while(rs.next()){
						BrokerVO brokerVO=new BrokerVO();
						brokerVO.setSerialNO(sNO+"");
						brokerVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
						brokerVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
						brokerVO.setLoginDate(TTKCommon.checkNull(rs.getString("LOGIN_DATE")));
						alBrokerVO.add(brokerVO);
						sNO++;
					}//rs if
					}//rs if
				return alBrokerVO;
			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "broker");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "broker");
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
						log.error("Error while closing the Resultset in BrokerDAOImpl getPolicyDetails",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{

						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in BrokerDAOImpl getPolicyDetails",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in BrokerDAOImpl getPolicyDetails",sqlExp);
								throw new TTKException(sqlExp, "broker");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "broker");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally		
		
	 }
	public ArrayList<Object> getPolicyList(ArrayList<Object> searchList) throws TTKException{
	

			Connection conn = null;
			CallableStatement cStmtObject=null;
			ResultSet rs=null;			
			ArrayList<Object> policyList=new ArrayList<Object>();
			BrokerVO brokerVO=null;
			try{
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPolicyList);
				cStmtObject.setString(1,(String)searchList.get(0));
				cStmtObject.setString(2,(String)searchList.get(1));
				cStmtObject.setString(3,(String)searchList.get(2));
				cStmtObject.setString(4,(String)searchList.get(3));
				cStmtObject.setString(5,(String)searchList.get(4));
				cStmtObject.setString(6,(String)searchList.get(5));
				cStmtObject.setString(7,(String)searchList.get(6));
				cStmtObject.setString(8,(String)searchList.get(7));
				cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(9);
				
				if(rs != null){
					while(rs.next()){
						brokerVO=new BrokerVO();
						brokerVO.setCorporateName(rs.getString("GROUP_NAME"));
						brokerVO.setCorporateID(rs.getString("GROUP_ID"));
						brokerVO.setCorporateSeqID(rs.getLong("GROUP_REG_SEQ_ID"));
						brokerVO.setPolicyNumber(rs.getString("POLICY_NUMBER"));
						brokerVO.setPolicySeqID(rs.getLong("POLICY_SEQ_ID"));
						if(rs.getDate("EFFECTIVE_TO_DATE")!=null) brokerVO.setPolicyDate(rs.getDate("EFFECTIVE_TO_DATE").toString());//TTKCommon.convertDateAsString("dd-MM-YYYY", rs.getDate("EFFECTIVE_TO_DATE")));
						policyList.add(brokerVO);
					}//rs if
					}//rs if
				return policyList;
			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "broker");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "broker");
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
						log.error("Error while closing the Resultset in BrokerDAOImpl getPolicyList",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{

						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in BrokerDAOImpl getPolicyList",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in BrokerDAOImpl getPolicyList",sqlExp);
								throw new TTKException(sqlExp, "broker");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "broker");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
			}//getPolicyList
	
	public ArrayList<Object> getMemberList(ArrayList<Object> searchList) throws TTKException{
		

		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs=null;			
		ArrayList<Object> memberList=new ArrayList<Object>();
		BrokerVO brokerVO=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strMemberList);
			cStmtObject.setString(1,(String)searchList.get(0));
			cStmtObject.setString(2,(String)searchList.get(1));
			cStmtObject.setString(3,(String)searchList.get(2));
			cStmtObject.setString(4,(String)searchList.get(3));
			cStmtObject.setString(5,(String)searchList.get(4));
			cStmtObject.setString(6,(String)searchList.get(5));
			cStmtObject.setString(7,(String)searchList.get(6));
			cStmtObject.setString(8,(String)searchList.get(7));
			cStmtObject.setString(9,(String)searchList.get(8));
			cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			
			if(rs != null){
				while(rs.next()){
					brokerVO=new BrokerVO();
					brokerVO.setEnrolmentID(rs.getString("TPA_ENROLLMENT_ID"));
					brokerVO.setMemberSeqID(rs.getLong("MEMBER_SEQ_ID"));
					brokerVO.setMemberName(rs.getString("MEM_NAME"));
					brokerVO.setEmployeeName(rs.getString("EMP_NAME"));
					brokerVO.setEmployeeNO(rs.getString("EMPLOYEE_NO"));
					brokerVO.setPolicySeqID(rs.getLong("POLICY_GROUP_SEQ_ID"));
					memberList.add(brokerVO);
				}//rs if
				}//rs if
			return memberList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getMemberList",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getMemberList",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getMemberList",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		}//getMemberList
	public HashMap<String,String> getSummaryViewDetails(String brokerCode,String groupType,Long groupSeqID,String summaryMode) throws TTKException{
			Connection conn = null;
			CallableStatement cStmtObject=null;
			ResultSet rs=null;			
			HashMap<String,String> corporateSummaryMap=new HashMap<String,String>();
			try{
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSummaryViewDetails);
				cStmtObject.setString(1,brokerCode);
				cStmtObject.setString(2,groupType);
				cStmtObject.setLong(3,groupSeqID);
				cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
				cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
				cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
				cStmtObject.execute();
				if("PAT".equals(summaryMode)){

					rs = (java.sql.ResultSet)cStmtObject.getObject(4);
					if(rs != null){
						if(rs.next()){
							corporateSummaryMap.put("NO_OF_PREAUTH_RECEIVED", TTKCommon.checkNull(rs.getString("NO_OF_PREAUTH_RECEIVED")));
							corporateSummaryMap.put("REQUESTED_AMOUNT", TTKCommon.checkNull(rs.getString("REQUESTED_AMOUNT")));
							corporateSummaryMap.put("NO_OF_PREAUTH_APPROVED", TTKCommon.checkNull(rs.getString("NO_OF_PREAUTH_APPROVED")));
							corporateSummaryMap.put("APPROVED_AMOUNT", TTKCommon.checkNull(rs.getString("APPROVED_AMOUNT")));
							corporateSummaryMap.put("NO_OF_PREAUTH_DENIED", TTKCommon.checkNull(rs.getString("NO_OF_PREAUTH_DENIED")));
							corporateSummaryMap.put("DENIED_AMOUNT", TTKCommon.checkNull(rs.getString("DENIED_AMOUNT")));
							corporateSummaryMap.put("NO_OF_PREAUTH_SHORTFALL", TTKCommon.checkNull(rs.getString("NO_OF_PREAUTH_SHORTFALL")));
							corporateSummaryMap.put("SHORTFALL_AMOUNT", TTKCommon.checkNull(rs.getString("SHORTFALL_AMOUNT")));
							corporateSummaryMap.put("NO_OF_PRE_CUR_MON_RECEIVED", TTKCommon.checkNull(rs.getString("NO_OF_PRE_CUR_MON_RECEIVED")));
							corporateSummaryMap.put("CUR_MON_REQUESTED_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_REQUESTED_AMOUNT")));
							corporateSummaryMap.put("NO_OF_PRE_CUR_MON_APPROVED", TTKCommon.checkNull(rs.getString("NO_OF_PRE_CUR_MON_APPROVED")));
							corporateSummaryMap.put("CUR_MON_APPROVED_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_APPROVED_AMOUNT")));
							corporateSummaryMap.put("NO_OF_PRE_CUR_MON_DENIED", TTKCommon.checkNull(rs.getString("NO_OF_PRE_CUR_MON_DENIED")));
							corporateSummaryMap.put("CUR_MON_DENIED_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_DENIED_AMOUNT")));
							corporateSummaryMap.put("NO_OF_PRE_CUR_MON_SHORTFALL", TTKCommon.checkNull(rs.getString("NO_OF_PRE_CUR_MON_SHORTFALL")));
							corporateSummaryMap.put("CUR_MON_SHORTFALL_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_SHORTFALL_AMOUNT")));
						}//rs if
						}//rs if					
				}else if("ENR".equals(summaryMode)){
				rs = (java.sql.ResultSet)cStmtObject.getObject(5);				
				if(rs != null){
					if(rs.next()){
						
						corporateSummaryMap.put("NUMBER_OF_EMPLOYEES", TTKCommon.checkNull(rs.getString("NUMBER_OF_EMPLOYEES")));
						corporateSummaryMap.put("NUMBER_OF_DEPENDENTS", TTKCommon.checkNull(rs.getString("NUMBER_OF_DEPENDENTS")));
						corporateSummaryMap.put("NO_OF_EMPLOYEES_ADDED", TTKCommon.checkNull(rs.getString("NO_OF_EMPLOYEES_ADDED")));
						corporateSummaryMap.put("NO_OF_DEPENDANTS_ADDED", TTKCommon.checkNull(rs.getString("NO_OF_DEPENDANTS_ADDED")));
						corporateSummaryMap.put("NO_OF_EMPLOYEES_DELETED", TTKCommon.checkNull(rs.getString("NO_OF_EMPLOYEES_DELETED")));
						corporateSummaryMap.put("NO_OF_DEPENDANTS_DELETED", TTKCommon.checkNull(rs.getString("NO_OF_DEPENDANTS_DELETED")));
						corporateSummaryMap.put("NET_PREMIUM", TTKCommon.checkNull(rs.getString("NET_PREMIUM")));
						corporateSummaryMap.put("EARNED_PREMIUM", TTKCommon.checkNull(rs.getString("EARNED_PREMIUM")));
					}//rs if
					}//rs if
				}//if("ENR".equals(summaryMode)){
				else if("CLM".equals(summaryMode)){
					rs = (java.sql.ResultSet)cStmtObject.getObject(6);				
					if(rs != null){
						if(rs.next()){
					corporateSummaryMap.put("NO_OF_CLAIMS_REPORTED", TTKCommon.checkNull(rs.getString("NO_OF_CLAIMS_REPORTED")));
					corporateSummaryMap.put("REPORTED_AMOUNT", TTKCommon.checkNull(rs.getString("REPORTED_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLAIMS_PAID", TTKCommon.checkNull(rs.getString("NO_OF_CLAIMS_PAID")));
					corporateSummaryMap.put("PAID_AMOUNT", TTKCommon.checkNull(rs.getString("PAID_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLAIMS_OUTSTANDING", TTKCommon.checkNull(rs.getString("NO_OF_CLAIMS_OUTSTANDING")));
					corporateSummaryMap.put("OUTSTANDING_AMOUNT", TTKCommon.checkNull(rs.getString("OUTSTANDING_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLAIMS_APPROVED", TTKCommon.checkNull(rs.getString("NO_OF_CLAIMS_APPROVED")));
					corporateSummaryMap.put("APPROVED_AMOUNT", TTKCommon.checkNull(rs.getString("APPROVED_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLAIMS_DENIED", TTKCommon.checkNull(rs.getString("NO_OF_CLAIMS_DENIED")));
					corporateSummaryMap.put("DENIED_AMOUNT", TTKCommon.checkNull(rs.getString("DENIED_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLAIMS_SHORTFALL", TTKCommon.checkNull(rs.getString("NO_OF_CLAIMS_SHORTFALL")));
					corporateSummaryMap.put("SHORTFALL_AMOUNT", TTKCommon.checkNull(rs.getString("SHORTFALL_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLM_CUR_MON_RPTD", TTKCommon.checkNull(rs.getString("NO_OF_CLM_CUR_MON_RPTD")));
					corporateSummaryMap.put("CUR_MON_REPORTED_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_REPORTED_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLM_CUR_MON_PAID", TTKCommon.checkNull(rs.getString("NO_OF_CLM_CUR_MON_PAID")));
					corporateSummaryMap.put("CUR_MON_PAID_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_PAID_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLM_CUR_MON_OUTSTANDING", TTKCommon.checkNull(rs.getString("NO_OF_CLM_CUR_MON_OUTSTANDING")));
					corporateSummaryMap.put("CUR_MON_OUTSTANDING_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_OUTSTANDING_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLM_CUR_MON_APPROVED", TTKCommon.checkNull(rs.getString("NO_OF_CLM_CUR_MON_APPROVED")));
					corporateSummaryMap.put("CUR_MON_APPROVED_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_APPROVED_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLM_CUR_MON_DENIED", TTKCommon.checkNull(rs.getString("NO_OF_CLM_CUR_MON_DENIED")));
					corporateSummaryMap.put("CUR_MON_DENIED_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_DENIED_AMOUNT")));
					corporateSummaryMap.put("NO_OF_CLM_CUR_MON_SHORTFALL", TTKCommon.checkNull(rs.getString("NO_OF_CLM_CUR_MON_SHORTFALL")));
					corporateSummaryMap.put("CUR_MON_SHORTFALL_AMOUNT", TTKCommon.checkNull(rs.getString("CUR_MON_SHORTFALL_AMOUNT")));
						}
					}
				}
				return corporateSummaryMap;
			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "broker");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "broker");
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
						log.error("Error while closing the Resultset in BrokerDAOImpl getSummaryViewDetails",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{

						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in BrokerDAOImpl getSummaryViewDetails",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in BrokerDAOImpl getSummaryViewDetails",sqlExp);
								throw new TTKException(sqlExp, "broker");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "broker");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
			}//getSummaryViewDetails
	public HashMap<String,Object> getDetailedViewDetails(String enrolmentID,String summaryMode) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs=null;
		ResultSet rs1=null;
		
		HashMap<String,Object> corporateDetailedMap=new HashMap<String,Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDetailedViewDetails);
			cStmtObject.setString(1,enrolmentID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.execute();
			if("PRI".equals(summaryMode)){

				rs = (java.sql.ResultSet)cStmtObject.getObject(2);
				rs1 = (java.sql.ResultSet)cStmtObject.getObject(5);
				if(rs != null){
					if(rs.next()){
						corporateDetailedMap.put("MEM_NAME", TTKCommon.checkNull(rs.getString("MEM_NAME")));
						corporateDetailedMap.put("MEM_DOB", TTKCommon.checkNull(rs.getString("MEM_DOB")));
						corporateDetailedMap.put("MEM_AGE", TTKCommon.checkNull(rs.getString("MEM_AGE")));
						corporateDetailedMap.put("GENDER_GENERAL_TYPE_ID", TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
						corporateDetailedMap.put("MARITAL_STATUS_ID", TTKCommon.checkNull(rs.getString("MARITAL_STATUS_ID")));
						if(rs1!=null){
							ArrayList<DependentVO> alDependentVO=new ArrayList<DependentVO>();
							while(rs1.next()){
								DependentVO dependentVO=new DependentVO();
								dependentVO.setName(TTKCommon.checkNull(rs1.getString("DEPENDENT_NAME")));
								dependentVO.setGender(TTKCommon.checkNull(rs1.getString("DEPENDENT_GENDER")));
								dependentVO.setAge((rs1.getInt("DEPENDENT_AGE")));
								
								alDependentVO.add(dependentVO);
							}
							corporateDetailedMap.put("ALDEPENDANTS", alDependentVO);
						}
					}//rs if
					}//rs if					
			}else if("CLM".equals(summaryMode)){
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);				
			if(rs != null){
				ArrayList<BrokerVO> alBrokerVO=new ArrayList<BrokerVO>();
				while(rs.next()){
					BrokerVO brokerVO=new BrokerVO();
					brokerVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					brokerVO.setRelationShip(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
					brokerVO.setClaimNumber(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					brokerVO.setDateOfHospitalization(TTKCommon.checkNull(rs.getString("DATE_OF_HOSPITALIZATION")));
					brokerVO.setDateOfDischarge(TTKCommon.checkNull(rs.getString("DATE_OF_DISCHARGE")));
					brokerVO.setTotalDiscGrassAmt(TTKCommon.checkNull(rs.getString("TOT_DISC_GROSS_AMOUNT")));
					brokerVO.setTotalApprovedAmt(TTKCommon.checkNull(rs.getString("TOT_APPROVED_AMOUNT")));
					brokerVO.setStatus(TTKCommon.checkNull(rs.getString("CLM_STATUS_TYPE_ID")));
					alBrokerVO.add(brokerVO);
				}//rs while
				corporateDetailedMap.put("CLAIM_LIST", alBrokerVO);
				}//rs if
			}//if("CLM".equals(summaryMode)){
			else if("PAT".equals(summaryMode)){
				rs = (java.sql.ResultSet)cStmtObject.getObject(4);				
				if(rs != null){
					ArrayList<BrokerVO> alBrokerVO=new ArrayList<BrokerVO>();
					while(rs.next()){
						BrokerVO brokerVO=new BrokerVO();
						brokerVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
						brokerVO.setRelationShip(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
						brokerVO.setPreAuthNumber(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
						brokerVO.setAuthNumber(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
						brokerVO.setDateOfHospitalization(TTKCommon.checkNull(rs.getString("DATE_OF_HOSPITALIZATION")));
						brokerVO.setDateOfDischarge(TTKCommon.checkNull(rs.getString("DATE_OF_DISCHARGE")));
						brokerVO.setTotalDiscGrassAmt(TTKCommon.checkNull(rs.getString("TOT_DISC_GROSS_AMOUNT")));
						brokerVO.setTotalApprovedAmt(TTKCommon.checkNull(rs.getString("TOT_APPROVED_AMOUNT")));
						brokerVO.setStatus(TTKCommon.checkNull(rs.getString("PAT_STATUS_TYPE_ID")));
						alBrokerVO.add(brokerVO);
					}//rs while
					corporateDetailedMap.put("PREAUTH_LIST", alBrokerVO);
					}//rs if
				}
			return corporateDetailedMap;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getDetailedViewDetails",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getDetailedViewDetails",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getDetailedViewDetails",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		}//getDetailedViewDetails
	public HashMap<String,Object> getClaimDetails(String claimNO) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs=null;			
		HashMap<String,Object> corporateDetailedMap=new HashMap<String,Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimDetails);
			cStmtObject.setString(1,claimNO);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(2);
				if(rs != null){
					if(rs.next()){
						corporateDetailedMap.put("CLAIM_NUMBER", TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
						corporateDetailedMap.put("CLM_RECEIVED_DATE", TTKCommon.checkNull(rs.getString("CLM_RECEIVED_DATE")));
						corporateDetailedMap.put("HOSP_NAME", TTKCommon.checkNull(rs.getString("HOSP_NAME")));
						corporateDetailedMap.put("ENCOUNTER_PROVIDER_INVOICE_NO", TTKCommon.checkNull(rs.getString("ENCOUNTER_PROVIDER_INVOICE_NO")));
						corporateDetailedMap.put("START_DATE", TTKCommon.checkNull(rs.getString("START_DATE")));
						corporateDetailedMap.put("ENCOUNTER_DATE_TO", TTKCommon.checkNull(rs.getString("ENCOUNTER_DATE_TO")));
						corporateDetailedMap.put("CLAIM_PAYMENT_ID", TTKCommon.checkNull(rs.getString("CLAIM_PAYMENT_ID")));
						corporateDetailedMap.put("CLAIM_PAYMENT_DATE", TTKCommon.checkNull(rs.getString("CLAIM_PAYMENT_DATE")));
						corporateDetailedMap.put("DIAGNOSYS_CODE", TTKCommon.checkNull(rs.getString("DIAGNOSYS_CODE")));
						corporateDetailedMap.put("SHORT_DESC", TTKCommon.checkNull(rs.getString("SHORT_DESC")));
						corporateDetailedMap.put("TOT_DISC_GROSS_AMOUNT", TTKCommon.checkNull(rs.getString("TOT_DISC_GROSS_AMOUNT")));
						corporateDetailedMap.put("DEDUCTIBLE_AMOUNT", TTKCommon.checkNull(rs.getString("DEDUCTIBLE_AMOUNT")));
						corporateDetailedMap.put("COPAY_AMOUNT", TTKCommon.checkNull(rs.getString("COPAY_AMOUNT")));
						corporateDetailedMap.put("BENEFIT_AMOUNT", TTKCommon.checkNull(rs.getString("BENEFIT_AMOUNT")));
						corporateDetailedMap.put("CLAIM_RECIPIENT", TTKCommon.checkNull(rs.getString("CLAIM_RECIPIENT")));
						corporateDetailedMap.put("DESCRIPTION", TTKCommon.checkNull(rs.getString("DESCRIPTION")));
						corporateDetailedMap.put("AUTH_NUMBER", TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
						corporateDetailedMap.put("CLM_STATUS_TYPE_ID", TTKCommon.checkNull(rs.getString("CLM_STATUS_TYPE_ID")));
						corporateDetailedMap.put("CLAIM_STATUS_DESCRIPTION", TTKCommon.checkNull(rs.getString("CLAIM_STATUS_DESCRIPTION")));
					  corporateDetailedMap.put("PAYEE_NAME", TTKCommon.checkNull(rs.getString("PAYEE_NAME")));
					  corporateDetailedMap.put("CHEQUE_EFT_NUMBER", TTKCommon.checkNull(rs.getString("CHEQUE_EFT_NUMBER")));
					  corporateDetailedMap.put("CHEQUE_EFT_DATE", TTKCommon.checkNull(rs.getString("CHEQUE_EFT_DATE")));
			          corporateDetailedMap.put("CHEQUE_DISPATCH_DATE", TTKCommon.checkNull(rs.getString("CHEQUE_DISPATCH_DATE")));
			          corporateDetailedMap.put("NAME_OF_COURIER", TTKCommon.checkNull(rs.getString("NAME_OF_COURIER")));
					  corporateDetailedMap.put("CONSIGNMENT_NO", TTKCommon.checkNull(rs.getString("CONSIGNMENT_NO")));
				}//rs if
					}//rs if					
			return corporateDetailedMap;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getClaimDetails",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getClaimDetails",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getClaimDetails",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		}//getClaimDetails
	public HashMap<String,Object> getPreauthDetails(String preAuthNO) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs=null;			
		HashMap<String,Object> corporateDetailedMap=new HashMap<String,Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreauthDetails);
			cStmtObject.setString(1,preAuthNO);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(2);
				if(rs != null){
					if(rs.next()){
						corporateDetailedMap.put("AUTH_NUMBER",TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
						corporateDetailedMap.put("HOSP_NAME",TTKCommon.checkNull(rs.getString("HOSP_NAME")));
						corporateDetailedMap.put("ENCOUNTER_DATE_FROM",TTKCommon.checkNull(rs.getString("ENCOUNTER_DATE_FROM")));
						corporateDetailedMap.put("ENCOUNTER_DATE_TO",TTKCommon.checkNull(rs.getString("ENCOUNTER_DATE_TO")));
						corporateDetailedMap.put("START_DATE",TTKCommon.checkNull(rs.getString("START_DATE")));
						corporateDetailedMap.put("DIAGNOSYS_CODE",TTKCommon.checkNull(rs.getString("DIAGNOSYS_CODE")));
						corporateDetailedMap.put("SHORT_DESC",TTKCommon.checkNull(rs.getString("SHORT_DESC")));
						corporateDetailedMap.put("TOT_APPROVED_AMOUNT",TTKCommon.checkNull(rs.getString("TOT_APPROVED_AMOUNT")));
						corporateDetailedMap.put("DEDUCTIBLE_AMOUNT",TTKCommon.checkNull(rs.getString("DEDUCTIBLE_AMOUNT")));
						corporateDetailedMap.put("COPAY_AMOUNT",TTKCommon.checkNull(rs.getString("COPAY_AMOUNT")));
						corporateDetailedMap.put("BENEFIT_AMOUNT",TTKCommon.checkNull(rs.getString("BENEFIT_AMOUNT")));
						corporateDetailedMap.put("ACTIVITY_BENEFIT",TTKCommon.checkNull(rs.getString("ACTIVITY_BENEFIT")));
						corporateDetailedMap.put("ENCOUNTER_TYPE",TTKCommon.checkNull(rs.getString("ENCOUNTER_TYPE")));
						corporateDetailedMap.put("ACTIVITY_STATUS",TTKCommon.checkNull(rs.getString("ACTIVITY_STATUS")));
						corporateDetailedMap.put("ACTIVITY_STATUS_DESCRIPTION",TTKCommon.checkNull(rs.getString("ACTIVITY_STATUS_DESCRIPTION")));
						corporateDetailedMap.put("CLAIM_RECEIVED",TTKCommon.checkNull(rs.getString("CLAIM_RECEIVED")));
					}//rs if
					}//rs if					
			return corporateDetailedMap;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getPreauthDetails",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getPreauthDetails",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getPreauthDetails",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		}//getPreauthDetails
	public HashMap<String,Object> getTob(Long policySeqID) throws TTKException{
		Connection conn = null;
		PreparedStatement cStmtObject=null;
		ResultSet rs=null;			
		HashMap<String,Object> corporateDetailedMap=new HashMap<String,Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.PreparedStatement)conn.prepareStatement(strGetTob);
			cStmtObject.setLong(1,policySeqID);
			rs=cStmtObject.executeQuery();
				if(rs != null){
					ArrayList<BrokerVO> alBroVO=new ArrayList<>();
					while(rs.next()){
						BrokerVO brokerVO=new BrokerVO();
						brokerVO.setFileType(TTKCommon.checkNull(rs.getString("FILE_TYPE")));
						brokerVO.setFileName(TTKCommon.checkNull(rs.getString("FILE_NAME")));
						brokerVO.setContactName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
						brokerVO.setAddedDateAsString(TTKCommon.checkNull(rs.getString("ADDED_DATE")));
						alBroVO.add(brokerVO);
					}//rs if
					corporateDetailedMap.put("TOB_LIST", alBroVO);
					}//rs if					
			return corporateDetailedMap;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getTob",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getTob",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getTob",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		}//getTob
	public HashMap<String,Object> getEndorsements(String broCode,String enrolID) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs=null;			
		HashMap<String,Object> corporateDetailedMap=new HashMap<String,Object>();
		
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strEndrsementDetails);
			cStmtObject.setString(1,broCode);
			cStmtObject.setString(2,enrolID);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(3);
				if(rs != null){
					ArrayList<BrokerVO> alBroVO=new ArrayList<>();
					while(rs.next()){
						
						BrokerVO brokerVO=new BrokerVO();
						brokerVO.setPolicyNumber(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
						brokerVO.setEmployeeName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
						brokerVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
						brokerVO.setDateOfInception(TTKCommon.checkNull(rs.getString("DATE_OF_INCEPTION")));
						brokerVO.setDateOfExist(TTKCommon.checkNull(rs.getString("DATE_OF_EXIT")));
						brokerVO.setStatus(TTKCommon.checkNull(rs.getString("ACTION")));
						alBroVO.add(brokerVO);
					}//rs if
					corporateDetailedMap.put("ENDORSE_LIST", alBroVO);
					}//rs if					
			return corporateDetailedMap;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "broker");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "broker");
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
					log.error("Error while closing the Resultset in BrokerDAOImpl getEndorsement",sqlExp);
					throw new TTKException(sqlExp, "broker");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BrokerDAOImpl getEndorsement",sqlExp);
						throw new TTKException(sqlExp, "broker");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BrokerDAOImpl getEndorsement",sqlExp);
							throw new TTKException(sqlExp, "broker");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "broker");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		}//getEndorsement
	public ArrayList<CacheObject> getCorporateList(String brokerCode) throws TTKException{

    	Connection conn = null;
    	ArrayList<CacheObject>  alCorporateList	=	new ArrayList<>();
    	ResultSet rs = null;
    	CacheObject cacheObject	=	null;
    	 PreparedStatement pStmt = null;
    	try{
	    	conn = ResourceManager.getConnection();
			 pStmt = conn.prepareStatement(strCorporateList);
             pStmt.setString(1,brokerCode);
             rs = pStmt.executeQuery();
             if(rs != null){
                 while (rs.next()) {
                	cacheObject = new CacheObject();
 					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("GROUP_REG_SEQ_ID")));
 					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
 					alCorporateList.add(cacheObject);
                }
            }
             
	    	return alCorporateList;
        }//end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally    
	}
	public ArrayList<CacheObject> getPolicyNumberList(Long corporateSeqID) throws TTKException{

    	Connection conn = null;
    	ArrayList<CacheObject> alCorporateList	=	new ArrayList<>();;
    	ResultSet rs = null;
    	CacheObject cacheObject	=	null;
    	 PreparedStatement pStmt = null;
    	try{
	    	conn = ResourceManager.getConnection();
			 pStmt = conn.prepareStatement(strPolicyNumberList);
             pStmt.setLong(1,corporateSeqID);
             rs = pStmt.executeQuery();
             if(rs != null){
            	
                 while (rs.next()) {
                	cacheObject = new CacheObject();
 					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("POLICY_SEQ_ID")));
 					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
 					alCorporateList.add(cacheObject);
                }
            }
             
	    	return alCorporateList;
        }//end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in group_name  getPolicyNumberList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in group_name  getPolicyNumberList()",sqlExp);
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
							log.error("Error while closing the Connection in group_name  getPolicyNumberList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally    
	}
	public ArrayList<CacheObject> getPolicyPeriodList(Long policySeqID) throws TTKException{

    	Connection conn = null;
    	ArrayList<CacheObject> alCorporateList	=	new ArrayList<>();;
    	ResultSet rs = null;
    	CacheObject cacheObject	=	null;
    	 PreparedStatement pStmt = null;
    	try{
	    	conn = ResourceManager.getConnection();
			 pStmt = conn.prepareStatement(strPolicyPeriodList);
             pStmt.setLong(1,policySeqID);
             rs = pStmt.executeQuery();
             if(rs != null){
            	
                 while (rs.next()) {
                	cacheObject = new CacheObject();
 					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("POLICY_DATE")));
 					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_PERIOD")));
 					alCorporateList.add(cacheObject);
                }
            }
             
	    	return alCorporateList;
        }//end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in group_name  getPolicyNumberList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in group_name  getPolicyNumberList()",sqlExp);
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
							log.error("Error while closing the Connection in group_name  getPolicyNumberList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally    
	}
	 public ArrayList<CacheObject> getINDPolicyNumberList(String brokerCode) throws TTKException{


	    	Connection conn = null;
	    	ArrayList<CacheObject> alCorporateList	=	new ArrayList<>();;
	    	ResultSet rs = null;
	    	CacheObject cacheObject	=	null;
	    	 PreparedStatement pStmt = null;
	    	try{
		    	conn = ResourceManager.getConnection();
				 pStmt = conn.prepareStatement(strINDPolicyNumberList);
	             pStmt.setString(1,brokerCode);
	             rs = pStmt.executeQuery();
	             if(rs != null){
	            	
	                 while (rs.next()) {
	                	cacheObject = new CacheObject();
	 					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("POLICY_SEQ_ID")));
	 					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
	 					alCorporateList.add(cacheObject);
	                }
	            }
	             
		    	return alCorporateList;
	        }//end of try
	    	catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "onlineforms");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "onlineforms");
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
						log.error("Error while closing the Resultset in group_name  getINDPolicyNumberList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt != null) pStmt.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in group_name  getINDPolicyNumberList()",sqlExp);
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
								log.error("Error while closing the Connection in group_name  getINDPolicyNumberList()",sqlExp);
								throw new TTKException(sqlExp, "enrollment");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "enrollment");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					pStmt = null;
					conn = null;
				}//end of finally
			}//end of finally    
			 
	 }
	 
	 
	 
		public ArrayList<String[]> getClaimTatData(ArrayList alInputParams) throws TTKException {
          	Connection conn = null;
          	ArrayList<String[]> alPolicyList	=	null;
          	ResultSet rs = null;
          	CallableStatement cStmtObject=null;
          	String[] clmData	=	null;
          	try{
          		conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimTat);
                cStmtObject.setString(1,(String)alInputParams.get(0));
                cStmtObject.setString(2,((String)alInputParams.get(1)));
                cStmtObject.setString(3,(String)alInputParams.get(2));
                //cStmtObject.setString(3,"TPA033");
                cStmtObject.setString(4,((String)alInputParams.get(3)));
                cStmtObject.setString(5,(String)alInputParams.get(4));
                cStmtObject.setString(6,((String)alInputParams.get(5)));
                cStmtObject.setString(7,((String)alInputParams.get(6)));
                cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(8);
                   if(rs != null){
                   	alPolicyList	=	new ArrayList<>();
                       while (rs.next()) {
                    	clmData	=	new String[10];
                    	clmData[0]	=	(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
                    	clmData[1]	=	rs.getString("COMPLETED_DATE_M");
                    	clmData[2]	=	rs.getString("COMPLETED_DATE_D");
                    	clmData[3]	=	rs.getString("COMPLETED_DATE_Y");
                    	clmData[4]	=	rs.getString("CLM_RECEIVED_DATE_M");
                    	clmData[5]	=	rs.getString("CLM_RECEIVED_DATE_D");
                    	clmData[6]	=	rs.getString("CLM_RECEIVED_DATE_Y");
       					alPolicyList.add(clmData);
                      }
                  }
      	    	return alPolicyList;
                 
              }//end of try
          	catch (SQLException sqlExp)
              {
                  throw new TTKException(sqlExp, "onlineforms");
              }//end of catch (SQLException sqlExp)
              catch (Exception exp)
              {
                  throw new TTKException(exp, "onlineforms");
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
      					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
      					throw new TTKException(sqlExp, "enrollment");
      				}//end of catch (SQLException sqlExp)
      				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
      				{
      					try
      					{
      						if (cStmtObject != null) cStmtObject.close();
      					}//end of try
      					catch (SQLException sqlExp)
      					{
      						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
      							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
      							throw new TTKException(sqlExp, "enrollment");
      						}//end of catch (SQLException sqlExp)
      					}//end of finally Connection Close
      				}//end of finally Statement Close
      			}//end of try
      			catch (TTKException exp)
      			{
      				throw new TTKException(exp, "enrollment");
      			}//end of catch (TTKException exp)
      			finally // Control will reach here in anycase set null to the objects 
      			{
      				rs = null;
      				cStmtObject = null;
      				conn = null;
      			}//end of finally
      		}//end of finally
          }//ArrayList<CacheObject> getClaimTatData(ArrayList alInputParams)
      	
      	
      	
      	public ArrayList<String[]> getPreAuthTatData(ArrayList alInputParams) throws TTKException {
          	Connection conn = null;
          	ArrayList<String[]> alPolicyList	=	null;
          	ResultSet rs = null;
          	CallableStatement cStmtObject=null;
          	String[] clmData	=	null;
          	try{
          		conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthTat);
                cStmtObject.setString(1,(String)alInputParams.get(0));
                cStmtObject.setString(2,((String)alInputParams.get(1)));
                cStmtObject.setString(3,(String)alInputParams.get(2));
                //cStmtObject.setString(3,"TPA033");
                cStmtObject.setString(4,((String)alInputParams.get(3)));
                cStmtObject.setString(5,(String)alInputParams.get(4));
                cStmtObject.setString(6,((String)alInputParams.get(5)));
                cStmtObject.setString(7,((String)alInputParams.get(6)));
                cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(8);
                   if(rs != null){
                   	alPolicyList	=	new ArrayList<>();
                       while (rs.next()) {
                    	clmData	=	new String[15];
                    	clmData[0]	=	(TTKCommon.checkNull(rs.getString("BENEFIT_TYPE")));
                    	clmData[1]	=	rs.getString("COMPLETED_DATE_M");
                    	clmData[2]	=	rs.getString("COMPLETED_DATE_D");
                    	clmData[3]	=	rs.getString("COMPLETED_DATE_Y");
                    	clmData[4]	=	rs.getString("COMPLETED_DATE_H");
                    	clmData[5]	=	rs.getString("COMPLETED_DATE_MI");
                    	clmData[6]	=	rs.getString("CLM_RECEIVED_DATE_M");
                    	clmData[7]	=	rs.getString("CLM_RECEIVED_DATE_D");
                    	clmData[8]	=	rs.getString("CLM_RECEIVED_DATE_Y");
                    	clmData[9]	=	rs.getString("CLM_RECEIVED_DATE_H");
                    	clmData[10]	=	rs.getString("CLM_RECEIVED_DATE_MI");
       					alPolicyList.add(clmData);
                      }
                  }
      	    	return alPolicyList;
                 
              }//end of try
          	catch (SQLException sqlExp)
              {
                  throw new TTKException(sqlExp, "onlineforms");
              }//end of catch (SQLException sqlExp)
              catch (Exception exp)
              {
                  throw new TTKException(exp, "onlineforms");
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
      					log.error("Error while closing the Resultset in group_name  getCorporatesList()",sqlExp);
      					throw new TTKException(sqlExp, "enrollment");
      				}//end of catch (SQLException sqlExp)
      				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
      				{
      					try
      					{
      						if (cStmtObject != null) cStmtObject.close();
      					}//end of try
      					catch (SQLException sqlExp)
      					{
      						log.error("Error while closing the Statement in group_name  getCorporatesList()",sqlExp);
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
      							log.error("Error while closing the Connection in group_name  getCorporatesList()",sqlExp);
      							throw new TTKException(sqlExp, "enrollment");
      						}//end of catch (SQLException sqlExp)
      					}//end of finally Connection Close
      				}//end of finally Statement Close
      			}//end of try
      			catch (TTKException exp)
      			{
      				throw new TTKException(exp, "enrollment");
      			}//end of catch (TTKException exp)
      			finally // Control will reach here in anycase set null to the objects 
      			{
      				rs = null;
      				cStmtObject = null;
      				conn = null;
      			}//end of finally
      		}//end of finally
          }//ArrayList<CacheObject> getPreAuthTatData(ArrayList alInputParams)
	}
