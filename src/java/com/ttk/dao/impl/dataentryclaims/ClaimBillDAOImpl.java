/**
 * @ (#) ClaimBillDAOImpl.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimBillDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 15, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.dataentryclaims;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.claims.BillDetailVO;
import com.ttk.dto.claims.BillSummaryVO;
import com.ttk.dto.claims.ClaimBillDetailVO;
import com.ttk.dto.claims.ClaimBillVO;
import com.ttk.dto.claims.LineItemVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.TariffAilmentVO;
import com.ttk.dto.preauth.TariffProcedureVO;

public class ClaimBillDAOImpl implements BaseDAO, Serializable{
	
	private static Logger log = Logger.getLogger(ClaimBillDAOImpl.class);
	
	//private static final String strGetBillSummaryDetail = "{CALL CLAIMS_SQL_PKG.SELECT_BILL_SUMMARY(?,?,?,?,?)}";
	private static final String strGetBillSummaryDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_BILL_SUMMARY(?,?,?,?,?,?)}";
	private static final String strCheckClaim = "{CALL CLAIMS_PKG_DATA_ENTRY.CHECK_CLAIM(?,?)}";
	//private static final String strSaveBillSummaryDetail = "{CALL CLAIMS_PKG.SAVE_CLM_SUMMARY(";
	private static final String strAilmentCaps = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SAVE_AILMENT_CAPS(";
	//private static final String strGetBillList = "{CALL CLAIMS_SQL_PKG.CLAIMS_BILL_HEADER_LIST(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetBillList = "{CALL CLAIMS_PKG_DATA_ENTRY.CLAIMS_BILL_HEADER_LIST(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strIncludeOrExcludeBillInfo = "{CALL CLAIMS_PKG_DATA_ENTRY.EXCLUDE_BILLS(?,?,?,?,?,?)}";
	//private static final String strGetBillDetail = "{CALL CLAIMS_SQL_PKG.SELECT_BILL_HEADER(?,?,?)}";
	private static final String strGetBillDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_BILL_HEADER(?,?,?)}";	
	private static final String strSaveBillDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SAVE_CLM_BILL_HEADER(?,?,?,?,?,?,?,?,?,?)}";//added for donor expenses
	//private static final String strGetLineItemList = "{CALL CLAIMS_SQL_PKG.CLAIMS_BILL_DETAILS_LIST(?,?,?,?,?,?,?)}";
	private static final String strSaveLineItemDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SAVE_CLM_BILL_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added for maternity
	//added for KOC-1356
	private static final String strSaveLineItemDetailNext = "{CALL CLAIMS_PKG_DATA_ENTRY.select_bills_save_next(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//MODIFIED for CR KOC-Decoupling 
	private static final String strGetBillLineItemList = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_BILLS_LIST(?,?,?)}";
	private static final String strGetLineitemList = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_LINEITEMS_LIST(?,?)}";
	private static final String strGetLineItemList = "{CALL CLAIMS_PKG_DATA_ENTRY.CLAIMS_BILL_DETAILS_LIST(?,?,?,?,?,?,?)}";
	private static final String strGetLineitem = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_LINEITEM(?,?)}";
	private static final String strGetProcedureAmounts = "{CALL PRE_AUTH_PKG_DATA_ENTRY.GET_PROCEDURE_AMOUNTS(?,?)}";
	private static final String strSaveAccountHeadAilmentProc = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SAVE_AILMENT_PROC(";
	//added for CR KOC-Decoupling
	private static final String strSaveDataEntryBillsPromote = "{CALL CLAIMS_PKG_DATA_ENTRY.claims_promote(?,?,?)}";
	private static final String strSaveDataEntryBillsRevert = "{CALL CLAIMS_PKG_DATA_ENTRY.claims_revert(?,?,?)}";
	
	
	/**
	 * This method returns the BillSummaryVO, which contains Summary of Bill details
	 * @param lngClaimSeqID long value contains Claim seq id to get the Summary of Bill Details
	 * @param lngUserSeqID long value contains Logged-in User Seq ID
	 * @return BillSummaryVO object which contains Summary of Bill details
	 * @exception throws TTKException
	 */
	public BillSummaryVO getBillSummaryDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject1=null;
		CallableStatement cStmtObject2=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		int intRequestAmtMisMatch = 0;
		ArrayList<Object> alBillDetailsList = new ArrayList<Object>();
		ArrayList<Object> alAilmentList = new ArrayList<Object>();
		BillSummaryVO billSummaryVO = new BillSummaryVO();
		BillDetailVO billDetailVO = null;
		TariffAilmentVO tariffAilmentVO = null;
		TariffProcedureVO tariffProcedureVO=null;
		ArrayList<Object> alProcedureList=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject1 = (java.sql.CallableStatement)conn.prepareCall(strGetBillSummaryDetail);
			cStmtObject2 = (java.sql.CallableStatement)conn.prepareCall(strGetProcedureAmounts);
			
			cStmtObject1.setLong(1,lngClaimSeqID);
			cStmtObject1.setLong(2,lngUserSeqID);
			cStmtObject1.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject1.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject1.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject1.registerOutParameter(6, Types.INTEGER);
			cStmtObject1.execute();
			rs1 = (java.sql.ResultSet)cStmtObject1.getObject(3);
			rs2 = (java.sql.ResultSet)cStmtObject1.getObject(4);
			rs3 = (java.sql.ResultSet)cStmtObject1.getObject(5);
			
			intRequestAmtMisMatch=cStmtObject1.getInt(6);
			billSummaryVO.setReqAmtMisMatch(intRequestAmtMisMatch);
			
			if(rs1 != null){
				while(rs1.next()){
					billDetailVO = new BillDetailVO();
					
					if(rs1.getString("WARD_ACC_GROUP_SEQ_ID") != null){
						billDetailVO.setWardAccGroupSeqID(new Long(rs1.getLong("WARD_ACC_GROUP_SEQ_ID")));
					}//end of if(rs1.getString("WARD_ACC_GROUP_SEQ_ID") != null)
					
					billDetailVO.setAccGroupDesc(TTKCommon.checkNull(rs1.getString("ACC_GROUP_NAME")));
					billDetailVO.setWardTypeID(TTKCommon.checkNull(rs1.getString("WARD_TYPE_ID")));
					billDetailVO.setWardDesc(TTKCommon.checkNull(rs1.getString("WARD_DESCRIPTION")));
					billDetailVO.setClaimBillAmt(TTKCommon.checkNull(rs1.getString("BILL_AMOUNT")));
					billDetailVO.setApplyDiscountYN(TTKCommon.checkNull(rs1.getString("DISCOUNT_APPLY")));
					billDetailVO.setDiscountPercent(TTKCommon.checkNull(rs1.getString("DISCOUNT_PERCENT")));
					billDetailVO.setClaimNetAmount(TTKCommon.checkNull(rs1.getString("NET_AMOUNT")));
					billDetailVO.setClaimMaxAmount(TTKCommon.checkNull(rs1.getString("MAX_AMOUNT")));
					billDetailVO.setBillNotes(TTKCommon.checkNull(rs1.getString("NOTES")));
					alBillDetailsList.add(billDetailVO);
				}//end of while(rs.next())
				billSummaryVO.setBillDetailVOList(alBillDetailsList);
			}//end of if(rs1 != null)
			
			if(rs2 != null){
				while(rs2.next()){
					
					billSummaryVO.setPreHospitalization(TTKCommon.checkNull(rs2.getString("PreHospitalisation")));
					billSummaryVO.setHospitalization(TTKCommon.checkNull(rs2.getString("Hospitalisation")));
					billSummaryVO.setPostHospitalization(TTKCommon.checkNull(rs2.getString("PostHospitalisation")));
				}//end of while(rs.next())
			}//end of if(rs2 != null)
			
			if(rs3 != null){
				while(rs3.next()){
					tariffAilmentVO = new TariffAilmentVO();
					
					if(rs3.getString("AILMENT_CAPS_SEQ_ID") != null){
						tariffAilmentVO.setAilmentCapsSeqID(new Long(rs3.getLong("AILMENT_CAPS_SEQ_ID")));
					}//end of if(rs3.getString("AILMENT_CAPS_SEQ_ID") != null)
					
					if(rs3.getString("ICD_PCS_SEQ_ID") != null){
						tariffAilmentVO.setICDPCSSeqID(new Long(rs3.getLong("ICD_PCS_SEQ_ID")));
					}//end of if(rs3.getString("ICD_PCS_SEQ_ID") != null)
					
					tariffAilmentVO.setApprovedAilmentAmt(TTKCommon.checkNull(rs3.getString("APPROVED_AMOUNT")));
					tariffAilmentVO.setMaxAilmentAllowedAmt(TTKCommon.checkNull(rs3.getString("MAXIMUM_ALLOWED_AMOUNT")));
					tariffAilmentVO.setAilmentNotes(TTKCommon.checkNull(rs3.getString("NOTES")));
					
					if(rs3.getString("PRIMARY_AILMENT_YN")!=null){
						if(rs3.getString("PRIMARY_AILMENT_YN").equals("Y")){
							tariffAilmentVO.setDescription(TTKCommon.checkNull(rs3.getString("AILMENT_DESC")).concat("(P)"));
						}//end of if(rs3.getString("PRIMARY_AILMENT_YN").equals("Y"))
						else{
							tariffAilmentVO.setDescription(TTKCommon.checkNull(rs3.getString("AILMENT_DESC")));
						}//end of else
					}//end of if(rs3.getString("PRIMARY_AILMENT_YN")!=null)
					else{
						tariffAilmentVO.setDescription(TTKCommon.checkNull(rs3.getString("AILMENT_DESC")));
					}//end of else
					
					//get the procedure list for the current Ailment
					cStmtObject2.setLong(1,tariffAilmentVO.getICDPCSSeqID());
					cStmtObject2.registerOutParameter(2,OracleTypes.CURSOR);
					cStmtObject2.execute();
					rs4 = (java.sql.ResultSet)cStmtObject2.getObject(2);
					alProcedureList= null;
					
					if(rs4 != null){
						while (rs4.next()){
							if(alProcedureList == null){
								alProcedureList = new ArrayList<Object>();
							}//end of if(alLineItemList == null)
							tariffProcedureVO=new TariffProcedureVO();
							
							if(rs4.getString("PAT_PROC_SEQ_ID") != null){
								tariffProcedureVO.setPatProcSeqID(new Long(rs4.getLong("PAT_PROC_SEQ_ID")));
							}//end of if(rs3.getString("PAT_PROC_SEQ_ID") != null)
							
							if(rs4.getString("PROC_SEQ_ID") != null){
								tariffProcedureVO.setProcSeqID(new Long(rs4.getLong("PROC_SEQ_ID")));
							}//end of if(rs3.getString("PROC_SEQ_ID") != null)
							
							tariffProcedureVO.setProcDesc(TTKCommon.checkNull(rs4.getString("PROC_DESCRIPTION")));
							tariffProcedureVO.setProcedureAmt(TTKCommon.checkNull(rs4.getString("PROCEDURE_AMOUNT")));
							alProcedureList.add(tariffProcedureVO);
						}//end of while (rs4.next())
					}//end of if(rs4 != null)
					tariffAilmentVO.setProcedureList(alProcedureList);
					alAilmentList.add(tariffAilmentVO);
					if (rs4 != null){
						rs4.close();
					}//end of if (rs4 != null)
					rs4=null;
				}//end of while(rs3.next())
				billSummaryVO.setAilmentVOList(alAilmentList);
			}//end of if(rs3 != null)
			if (cStmtObject2 != null){
				cStmtObject2.close();
			}//end of if (cStmtObject2 != null)
			cStmtObject2 = null;
			return billSummaryVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the third result set
			{
				try
				{
					if (rs4 != null) rs4.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Fourth Resultset in ClaimBillDAOImpl getBillSummaryDetail()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally
				{
					try
					{
						if (rs3 != null) rs3.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Third Resultset in ClaimBillDAOImpl getBillSummaryDetail()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
					finally // Even if third result set is not closed, control reaches here. Try closing the second resultset now.
					{
						try{
							if (rs2 != null) rs2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Resultset in ClaimBillDAOImpl getBillSummaryDetail()",sqlExp);
							throw new TTKException(sqlExp, "bill");
						}//end of catch (SQLException sqlExp)
						finally{ // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
							try{
								if (rs1 != null) rs1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the First Resultset in ClaimBillDAOImpl getBillSummaryDetail()",sqlExp);
								throw new TTKException(sqlExp, "bill");
							}//end of catch (SQLException sqlExp)
							finally{ // Even if first result set is not closed, control reaches here. Try closing the statement now.
								try
								{
									if (cStmtObject1 != null) cStmtObject1.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the First Statement in ClaimBillDAOImpl getBillSummaryDetail()",sqlExp);
									throw new TTKException(sqlExp, "bill");
								}//end of catch (SQLException sqlExp)
								finally // Even if statement is not closed, control reaches here. Try closing the connection now.
								{
									try
									{
										if (cStmtObject2 != null) cStmtObject2.close();
									}//end of try
									catch (SQLException sqlExp)
									{
										log.error("Error while closing the Second Statement in ClaimBillDAOImpl getBillSummaryDetail()",sqlExp);
										throw new TTKException(sqlExp, "bill");
									}//end of catch (SQLException sqlExp)
									finally
									{
										try
										{
											if(conn != null) conn.close();
										}//end of try
										catch (SQLException sqlExp)
										{
											log.error("Error while closing the Connection in ClaimBillDAOImpl getBillSummaryDetail()",sqlExp);
											throw new TTKException(sqlExp, "bill");
										}//end of catch (SQLException sqlExp)
									}//end of finally
								}//end of finally Connection Close
							}//end of finally Statement Close
						}//end of finally
					}//end of finally
				}//end of try
			}//end of finally
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs4=null;
				rs3 = null;
				rs2 = null;
				rs1 = null;
				cStmtObject1 = null;
				cStmtObject2 = null;
				conn = null;
			}//end of finally
		}//end of finally
		
	}//end of getBillSummaryDetail(long lngClaimSeqID,long lngUserSeqID)
	
	/**
	 * This method saves the Summary of Bill information
	 * @param billSummaryVO object which contains the Summary of Bill Details which has to be  saved
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveBillSummaryDetail(BillSummaryVO billSummaryVO) throws TTKException {
		Connection conn = null;
		int iResult = 1;
		StringBuffer sbfSQL = null;
		StringBuffer sbfSQL2 = null;
		//ArrayList alBillDetailList = new ArrayList();
		ArrayList alAilmentCapsList = new ArrayList();
		ArrayList alProcedureList=null;
		CallableStatement cStmtObject=null; 
		//Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		//BillDetailVO billDetailVO = null;
		TariffAilmentVO tariffAilmentVO = null;
		TariffProcedureVO tariffProcedureVO=null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCheckClaim);
			
			cStmtObject.setLong(1,billSummaryVO.getClaimSeqID());
			cStmtObject.setLong(2,billSummaryVO.getUpdatedBy());
			cStmtObject.execute();
			//stmt1 = (java.sql.Statement)conn.createStatement();
			//alBillDetailList = billSummaryVO.getBillDetailVOList();
			
			/*if(alBillDetailList.size() >0){
				for(int i=0;i<alBillDetailList.size();i++){
					sbfSQL = new StringBuffer();
					billDetailVO = (BillDetailVO)alBillDetailList.get(i);
					sbfSQL = sbfSQL.append("'"+billSummaryVO.getClaimSeqID()+"',");//Mandatory
					sbfSQL = sbfSQL.append("'"+billDetailVO.getWardTypeID()+"',");//Mandatory
					sbfSQL = sbfSQL.append("'"+billDetailVO.getApplyDiscountYN()+"',");//Mandatory
					sbfSQL = sbfSQL.append("'"+billSummaryVO.getUpdatedBy()+"')}");
					stmt1.addBatch(strSaveBillSummaryDetail+sbfSQL.toString());
				}//end of for(int i=0;i<alBillSummaryList.size();i++)
			}//end of if(alBillSummaryList.size() >0)
			stmt1.executeBatch();*/
			
			stmt2 = (java.sql.Statement)conn.createStatement();
			stmt3 = (java.sql.Statement)conn.createStatement();
			alAilmentCapsList = billSummaryVO.getAilmentVOList();
			if(alAilmentCapsList != null){
				for(int i=0;i<alAilmentCapsList.size();i++){
					sbfSQL = new StringBuffer();
					tariffAilmentVO = (TariffAilmentVO)alAilmentCapsList.get(i);
					alProcedureList=tariffAilmentVO.getProcedureList();
					if(alProcedureList!=null)
					{
						for(int j=0;j<alProcedureList.size();j++)
						{
							sbfSQL2 = new StringBuffer();
							tariffProcedureVO=(TariffProcedureVO)alProcedureList.get(j);
							
							if(tariffProcedureVO.getPatProcSeqID() == null){
								sbfSQL2 = sbfSQL2.append(""+0+",");
							}//end of if(tariffProcedureVO.getPatProcSeqID() == null)
							else{
								sbfSQL2 = sbfSQL2.append("'"+tariffProcedureVO.getPatProcSeqID()+"',");//Mandatory
							}//end of else
							
							if(tariffProcedureVO.getProcedureAmt()== null){
								sbfSQL2 = sbfSQL2.append(""+null+",");
							}//end of if(tariffAilmentVO.getApprovedAmt()== null)
							else{
								sbfSQL2 = sbfSQL2.append("'"+tariffProcedureVO.getProcedureAmt()+"',");
							}//end of else
							
							sbfSQL2 = sbfSQL2.append("'"+billSummaryVO.getUpdatedBy()+"',");
							sbfSQL2 = sbfSQL2.append(""+null+")}");
							stmt3.addBatch(strSaveAccountHeadAilmentProc+sbfSQL2.toString());
						}//end of for(int j=0;j<alProcedureList.size();j++)
					}//end of if(alProcedureList!=null)
					if(tariffAilmentVO.getAilmentCapsSeqID() == null){
						sbfSQL = sbfSQL.append(""+null+",");//Mandatory in Edit Mode
					}//end of if(tariffAilmentVO.getAilmentCapsSeqID() == null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentCapsSeqID()+"',");//Mandatory in Edit Mode
					}//end of else
					
					sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getICDPCSSeqID()+"',");//Mandatory
					
					if(tariffAilmentVO.getAilmentMaxAllowedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffAilmentVO.getMaxAllowedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentMaxAllowedAmt()+"',");
					}//end of else
					
					if(tariffAilmentVO.getAilmentApprovedAmt()== null){
						sbfSQL = sbfSQL.append(""+null+",");
					}//end of if(tariffAilmentVO.getApprovedAmt()== null)
					else{
						sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentApprovedAmt()+"',");
					}//end of else
					
					sbfSQL = sbfSQL.append("'"+tariffAilmentVO.getAilmentNotes()+"',");
					sbfSQL = sbfSQL.append("'"+billSummaryVO.getUpdatedBy()+"')}");
					stmt2.addBatch(strAilmentCaps+sbfSQL.toString());
				}//end of for
			}//end of if(alAilmentCapsList != null)
			stmt3.executeBatch();
			stmt2.executeBatch();
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			try {
				conn.rollback();
				throw new TTKException(sqlExp, "bill");
			} //end of try
			catch (SQLException sExp) {
				throw new TTKException(sExp, "bill");
			}//end of catch (SQLException sExp)
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			try {
				conn.rollback();
				throw new TTKException(exp, "bill");
			} //end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "bill");
			}//end of catch (SQLException sqlExp)
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Third Statement
			{
				try
				{
					if (stmt3 != null) stmt3.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Third Statement in ClaimBillDAOImpl saveBillSummaryDetail()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if Third Statement is not closed, control reaches here. Try closing the Second Statement now.
				{
					try{
						if (stmt2 != null) stmt2.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Second Statement in ClaimBillDAOImpl saveBillSummaryDetail()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
					finally // Even if Second Statement is not closed, control reaches here. Try closing the first statement now.
					{
						
						try{
							if (cStmtObject != null) cStmtObject.close();
						}
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Callable Statement in ClaimBillDAOImpl saveBillSummaryDetail()",sqlExp);
							throw new TTKException(sqlExp, "bill");
						}//end of catch (SQLException sqlExp)
						finally{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in ClaimBillDAOImpl saveBillSummaryDetail()",sqlExp);
								throw new TTKException(sqlExp, "bill");
							}//end of catch (SQLException sqlExp)
						}//end of finally
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				stmt3 = null;
				stmt2 = null;
				//stmt1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of saveBillSummaryDetail(BillSummaryVO billSummaryVO)
	
	/**
	 * This method returns the Arraylist of ClaimBillVO's  which contains Claim Bill Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ClaimBillVO object which contains Claim Bill details
	 * @exception throws TTKException
	 */
	public ArrayList getBillList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ClaimBillVO claimBillVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBillList);
			if(alSearchCriteria.get(0) != null){
				cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			}//end of if(alSearchCriteria.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setLong(9,(Long)alSearchCriteria.get(4));
			cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			if(rs != null){
				while(rs.next()){
					claimBillVO = new ClaimBillVO();
					
					if(rs.getString("CLM_BILL_SEQ_ID") != null){
						claimBillVO.setBillSeqID(new Long(rs.getLong("CLM_BILL_SEQ_ID")));
					}//end of if(rs.getString("CLM_BILL_SEQ_ID") != null)
					
					claimBillVO.setBillNbr(TTKCommon.checkNull(rs.getString("BILL_NO")));
					
					if(rs.getString("BILL_DATE") != null){
						claimBillVO.setBillDate(new Date(rs.getTimestamp("BILL_DATE").getTime()));
					}//end of if(rs.getString("BILL_DATE") != null)
					
					if(rs.getString("BILL_AMOUNT") != null){
						claimBillVO.setBillAmount(new BigDecimal(rs.getString("BILL_AMOUNT")));
					}//end of if(rs.getString("BILL_AMOUNT") != null)
					else{
						claimBillVO.setBillAmount(new BigDecimal("0.00"));
					}//end of else
					
					if(rs.getString("ALLOWED_AMOUNT") != null){
						claimBillVO.setApprovedBillAmt(new BigDecimal(rs.getString("ALLOWED_AMOUNT")));
					}//end of if(rs.getString("ALLOWED_AMOUNT") != null)
					else{
						claimBillVO.setApprovedBillAmt(new BigDecimal("0.00"));
					}//end of else
					
					claimBillVO.setBillTypeDesc(TTKCommon.checkNull(rs.getString("BILL_TYPE")));
					claimBillVO.setAmmendmentYN(TTKCommon.checkNull(rs.getString("AMMENDMENT_YN")));
					claimBillVO.setBillIncludedYN(TTKCommon.checkNull(rs.getString("BILL_INCLUDED_YN")));
					alResultList.add(claimBillVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Resultset in ClaimBillDAOImpl getBillList()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimBillDAOImpl getBillList()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimBillDAOImpl getBillList()",sqlExp);
							throw new TTKException(sqlExp, "bill");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBillList(ArrayList alSearchCriteria)
	
	/**
	 * This method include or excluded the Bills records to the database.
	 * @param lngClaimSeqId Long Claim Seq Id which contains the Claim seq id
	 * @param strFlag String which contains the identifier for Include/Exclude the bill
	 * @param alIncludeExcludeList ArrayList object which contains the bill sequence id's to be included/excluded
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int includeExcludeBill(long lngClaimSeqId,String strFlag,ArrayList alIncludeExcludeList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			
			if(alIncludeExcludeList != null && alIncludeExcludeList.size() > 0) {
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strIncludeOrExcludeBillInfo);
				cStmtObject.setString(1, strFlag);//Identifier for Include/Exclude - INC/Include & EXC/Exclude
				cStmtObject.setString(2,(String)alIncludeExcludeList.get(0));//Concatenated String of Bill Seq ID's
				cStmtObject.setLong(3,lngClaimSeqId);//CLAIM_SEQ_ID
				cStmtObject.setString(4, (String)alIncludeExcludeList.get(1));//CALL_TYPE - APL
				cStmtObject.setLong(5,(Long)alIncludeExcludeList.get(2));//USER_SEQ_ID
				cStmtObject.registerOutParameter(6, Types.INTEGER);//out parameter which gives the number of records deleted
				cStmtObject.execute();
				iResult = cStmtObject.getInt(6);
			}//end of if(alIncludeExcludeList != null && alIncludeExcludeList.size() > 0)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Statement in ClaimBillDAOImpl includeExcludeBill()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimBillDAOImpl includeExcludeBill()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of includeExcludeBill(long lngClaimSeqId,String strFlag,ArrayList alIncludeExcludeList)
	
	/**
	 * This method returns the ClaimBillDetailVO, which contains all the Claim Bill details
	 * @param lngClaimBillSeqID long value contains seq id to get the Claim Bill Details
	 * @param lngUserSeqID long value contains Logged-in User Seq ID
	 * @return ClaimBillDetailVO object which contains all the Claim Bill details
	 * @exception throws TTKException
	 */
	public ClaimBillDetailVO getBillDetail(long lngClaimBillSeqID,long lngUserSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ClaimBillDetailVO claimBillDetailVO = null;
		LineItemVO lineItemVO=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBillDetail);
			cStmtObject.setLong(1,lngClaimBillSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			
			if(rs != null){
				while(rs.next()){
					claimBillDetailVO = new ClaimBillDetailVO();
					lineItemVO=new LineItemVO();
					
					if(rs.getString("CLM_BILL_SEQ_ID") != null){
						claimBillDetailVO.setBillSeqID(new Long(rs.getLong("CLM_BILL_SEQ_ID")));
					}//end of if(rs.getString("CLM_BILL_SEQ_ID") != null)
					
					claimBillDetailVO.setBillNbr(rs.getString("BILL_NO"));
					if(rs.getString("BILL_DATE") != null){
						claimBillDetailVO.setBillDate(new Date(rs.getTimestamp("BILL_DATE").getTime()));
					}//end of if(rs.getString("BILL_DATE") != null)
					claimBillDetailVO.setBillIssuedBy(TTKCommon.checkNull(rs.getString("BILL_ISSUED_BY")));
					claimBillDetailVO.setBillWithPrescription(rs.getString("BILLS_WITH_PRESCRIPTION_YN"));
					claimBillDetailVO.setBillTypeDesc(rs.getString("BILL_TYPE"));
					claimBillDetailVO.setCompletedYN(rs.getString("COMPLETED_YN"));
					if(rs.getString("REQUESTED_AMT") != null){
						lineItemVO.setRequestedAmt(new BigDecimal(rs.getString("REQUESTED_AMT")));
					}//end of if(rs.getString("REQUESTED_AMT") != null)
					if(rs.getString("DISALLOWED_AMOUNT") != null){
						lineItemVO.setDisAllowedAmt(new BigDecimal(rs.getString("DISALLOWED_AMOUNT")));
					}//end of if(rs.getString("DISALLOWED_AMOUNT") != null)
					if(rs.getString("ALLOWED_AMOUNT") != null){
						lineItemVO.setAllowedAmt(new BigDecimal(rs.getString("ALLOWED_AMOUNT")));
					}//end of if(rs.getString("ALLOWED_AMOUNT") != null)
					
					claimBillDetailVO.setBillIncludedYN(TTKCommon.checkNull(rs.getString("BILL_INCLUDED_YN")));
					claimBillDetailVO.setDonorExpYN(rs.getString("DONOR_BILL_YN"));
					claimBillDetailVO.setLineItemVO(lineItemVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Resultset in ClaimBillDAOImpl getBillDetail()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimBillDAOImpl getBillDetail()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimBillDAOImpl getBillDetail()",sqlExp);
							throw new TTKException(sqlExp, "bill");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return claimBillDetailVO;
	}//end of getBillDetail(long lngClaimBillSeqID,long lngUserSeqID)
	
	/**
	 * This method saves the Claim Bill information
	 * @param claimBillDetailVO the object which contains the Claim Bill Details which has to be saved
	 * @return long the value contains Claim Bill Seq ID
	 * @exception throws TTKException
	 */
	public long saveBillDetail(ClaimBillDetailVO claimBillDetailVO) throws TTKException {
		long lngBillSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveBillDetail);
			if(claimBillDetailVO.getBillSeqID() != null){
				cStmtObject.setLong(1,claimBillDetailVO.getBillSeqID());
			}//end of if(claimBillDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else
			
			if(claimBillDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(2,claimBillDetailVO.getClaimSeqID());
			}//end of if(claimBillDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			
			cStmtObject.setString(3,claimBillDetailVO.getBillNbr());
			
			if(claimBillDetailVO.getBillDate() != null){
				cStmtObject.setTimestamp(4,new Timestamp(claimBillDetailVO.getBillDate().getTime()));
			}//end of if(preAuthDetailVO.getClaimantVO().getDateOfInception() != null)
			else{
				cStmtObject.setTimestamp(4, null);
			}//end of else
			
			cStmtObject.setString(5,claimBillDetailVO.getBillIssuedBy());
			cStmtObject.setString(6,claimBillDetailVO.getBillWithPrescription());
			cStmtObject.setString(7,claimBillDetailVO.getBillIncludedYN());
			cStmtObject.setLong(8,claimBillDetailVO.getUpdatedBy());
			cStmtObject.setString(9,claimBillDetailVO.getDonorExpYN());//added for donor expenses
			cStmtObject.registerOutParameter(1,Types.BIGINT); //Bill_SEQ_ID
			cStmtObject.registerOutParameter(10,Types.INTEGER);
			cStmtObject.execute();
			lngBillSeqID = cStmtObject.getLong(1);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Statement in ClaimBillDAOImpl saveBillDetail()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimBillDAOImpl saveBillDetail()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngBillSeqID;
	}//end of saveBillDetail(ClaimBillDetailVO claimBillDetailVO)
	
	
	//added for CR KOC-Decoupling
	public int saveDataEntryBillsPromote(ClaimBillDetailVO claimBillDetailVO) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int rowProcessed = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDataEntryBillsPromote);
			
			if(claimBillDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(1,claimBillDetailVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			
			cStmtObject.setString(2,"B");
			
			cStmtObject.registerOutParameter(3,Types.INTEGER);
			
			cStmtObject.execute();
			
			rowProcessed =  cStmtObject.getInt(3);
			
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Statement in ClaimBillDAOImpl saveDataEntryBillsPromote()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimBillDAOImpl saveDataEntryBillsPromote()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return rowProcessed;
	}//end of saveDataEntryBillsPromote(ClaimBillDetailVO claimBillDetailVO)

	//added for CR KOC-Decoupling
	public int saveDataEntryBillsRevert(ClaimBillDetailVO claimBillDetailVO) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int rowProcessed = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDataEntryBillsRevert);
			
			if(claimBillDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(1,claimBillDetailVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			
			cStmtObject.setString(2,"B");
			
			cStmtObject.registerOutParameter(3,Types.INTEGER);
			
			cStmtObject.execute();
			
			rowProcessed =  cStmtObject.getInt(3);
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Statement in ClaimBillDAOImpl saveDataEntryBillsRevert()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimBillDAOImpl saveDataEntryBillsRevert()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return rowProcessed;
	}//end of saveDataEntryBillsRevert(ClaimBillDetailVO claimBillDetailVO)
	
	
	//ended
	
	
	
	/**
	 * This method returns the Arraylist of LineItemVO's  which contains Claim LineItem Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of LineItemVO object which contains Claim LineItem details
	 * @exception throws TTKException
	 */
	public ArrayList getLineItemList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		LineItemVO lineItemVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetLineItemList);
			if(alSearchCriteria.get(0) != null){
				cStmtObject.setLong(1,(Long)alSearchCriteria.get(0)); //Mandatory
			}//end of if(alSearchCriteria.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			
			cStmtObject.setString(2,(String)alSearchCriteria.get(2));
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));
			cStmtObject.setString(4,(String)alSearchCriteria.get(4));
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));
			cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			if(rs != null){
				while(rs.next()){
					lineItemVO = new LineItemVO();
					
					if(rs.getString("CLM_BILL_DTL_SEQ_ID") != null){
						lineItemVO.setLineItemSeqID(new Long(rs.getLong("CLM_BILL_DTL_SEQ_ID")));
					}//end of if(rs.getString("CLM_BILL_DTL_SEQ_ID") != null)
					
					lineItemVO.setLineItemNbr(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					lineItemVO.setAccountHeadDesc(TTKCommon.checkNull(rs.getString("WARD_DESCRIPTION")));
					
					if(rs.getString("REQUESTED_AMOUNT") != null){
						lineItemVO.setRequestedAmt(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
					}//end of if(rs.getString("REQUESTED_AMOUNT") != null)
					
					if(rs.getString("ALLOWED_AMOUNT") != null){
						lineItemVO.setAllowedAmt(new BigDecimal(rs.getString("ALLOWED_AMOUNT")));
					}//end of if(rs.getString("ALLOWED_AMOUNT") != null)
					else{
						lineItemVO.setAllowedAmt(new BigDecimal("0.00"));
					}//end of else
					
					lineItemVO.setAccountHeadTypeID(TTKCommon.checkNull(rs.getString("WARD_TYPE_ID")));
					lineItemVO.setRoomTypeID(TTKCommon.checkNull(rs.getString("ROOM_TYPE_ID")));
					lineItemVO.setvaccineTypeID(TTKCommon.checkNull(rs.getString("VACCINATION_TYPE_ID")));
					if(rs.getString("NUMBER_OF_DAYS") != null){
						lineItemVO.setNbrofDays(new Integer(rs.getInt("NUMBER_OF_DAYS")));
					}//end of if(rs.getString("NUMBER_OF_DAYS") != null)
					
					lineItemVO.setAllowYN(TTKCommon.checkNull(rs.getString("ALLOW_YN")));
					
					if(rs.getString("REJECTED_AMOUNT") != null){
						lineItemVO.setDisAllowedAmt(new BigDecimal(rs.getString("REJECTED_AMOUNT")));
					}//end of if(rs.getString("REJECTED_AMOUNT") != null)
					
					lineItemVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					alResultList.add(lineItemVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Resultset in ClaimBillDAOImpl getLineItemList()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimBillDAOImpl getLineItemList()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimBillDAOImpl getLineItemList()",sqlExp);
							throw new TTKException(sqlExp, "bill");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getLineItemList(ArrayList alSearchCriteria)
	
	/**
	 * This method saves the Claim LineItem information
	 * @param claimBillDetailVO the object which contains the Claim LineItem Details which has to be saved
	 * @return long the value contains Claim LineItem Seq ID
	 * @exception throws TTKException
	 */
	public long saveLineItemDetail(LineItemVO lineItemVO) throws TTKException {
		long lngLineItemSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveLineItemDetail);
			String strAccountHeadTypeID=lineItemVO.getAccountHeadTypeID();
			cStmtObject.setLong(1,lineItemVO.getLineItemSeqID()!=null ? lineItemVO.getLineItemSeqID():0);
			if(lineItemVO.getBillSeqID()!=null){
				cStmtObject.setLong(2,lineItemVO.getBillSeqID());
			}//end of if(lineItemVO.getBillSeqID()!=null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			cStmtObject.setString(3,lineItemVO.getLineItemNbr()); //Description
			
			cStmtObject.setString(4,strAccountHeadTypeID.substring(0,strAccountHeadTypeID.indexOf("#")));  //Ward Type
			cStmtObject.setString(5,lineItemVO.getRoomTypeID());
			cStmtObject.setInt(6,lineItemVO.getNbrofDays()!=null ? lineItemVO.getNbrofDays():0);
			
			if(lineItemVO.getRequestedAmt() != null){
				cStmtObject.setBigDecimal(7,lineItemVO.getRequestedAmt());
			}//end of if(lineItemVO.getRequestedAmt() != null)
			else{
				cStmtObject.setString(7,null);
			}//end of else
			cStmtObject.setString(8,lineItemVO.getAllowYN());
			if(lineItemVO.getAccountHeadTypeID().equals("STX#N"))
			{
				cStmtObject.setBigDecimal(9,new BigDecimal("0.00"));
			
			}//end of if(lineItemVO.getAccountHeadTypeID().equals("STX#N"))
			else if(lineItemVO.getAccountHeadTypeID()!="STX#N")
			{
				if(lineItemVO.getAllowedAmt() != null)
				{
					cStmtObject.setBigDecimal(9,lineItemVO.getAllowedAmt());
				}//end of if(lineItemVO.getAllowedAmt() != null)
			
				else
				{
				cStmtObject.setString(9,null);
				}//end of else
			}//end of else if(lineItemVO.getAccountHeadTypeID()!="STX#N")
			cStmtObject.setString(10,lineItemVO.getRemarks());
			cStmtObject.setLong(11,lineItemVO.getUpdatedBy());
			
			cStmtObject.setString(12,lineItemVO.getvaccineTypeID());//added for koc maternity
			cStmtObject.registerOutParameter(13,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(1,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.execute();
			lngLineItemSeqID = cStmtObject.getLong(1);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Statement in ClaimBillDAOImpl saveLineItemDetail()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimBillDAOImpl saveLineItemDetail()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngLineItemSeqID;
	}//end of saveLineItemDetail(LineItemVO lineItemVO)
	
	
	/**
	 * This method saves the Claim LineItem information
	 * @param claimBillDetailVO the object which contains the Claim LineItem Details which has to be saved
	 * @return long the value contains Claim LineItem Seq ID
	 * @exception throws TTKException
	 */
	public LineItemVO saveLineItemDetailNext(LineItemVO lineItemVO) throws TTKException {

		long lngLineItemSeqID = 0;
		//long lngLineItemSeqID1 = 0;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cStmtObject=null;
		//LineItemVO lineItemVO1 = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveLineItemDetailNext);
			String strAccountHeadTypeID=lineItemVO.getAccountHeadTypeID();
			
			cStmtObject.setLong(1,lineItemVO.getClaimSeqID()!=null ? lineItemVO.getClaimSeqID():0);
					
			cStmtObject.setLong(2,lineItemVO.getLineItemSeqID()!=null ? lineItemVO.getLineItemSeqID():0);
			
			
			if(lineItemVO.getBillSeqID()!=null){
				cStmtObject.setLong(3,lineItemVO.getBillSeqID());
			}//end of if(lineItemVO.getBillSeqID()!=null)
			else{
				cStmtObject.setString(3,null);
			}//end of else
			
			
			cStmtObject.setString(4,lineItemVO.getLineItemNbr()); //Description
			
			cStmtObject.setString(5,strAccountHeadTypeID.substring(0,strAccountHeadTypeID.indexOf("#")));  //Ward Type
			cStmtObject.setString(6,lineItemVO.getRoomTypeID());
			cStmtObject.setInt(7,lineItemVO.getNbrofDays()!=null ? lineItemVO.getNbrofDays():0);
			
			if(lineItemVO.getRequestedAmt() != null){
				cStmtObject.setBigDecimal(8,lineItemVO.getRequestedAmt());
			}//end of if(lineItemVO.getRequestedAmt() != null)
			else{
				cStmtObject.setString(8,null);
			}//end of else
			cStmtObject.setString(9,lineItemVO.getAllowYN());
			if(lineItemVO.getAccountHeadTypeID().equals("STX#N"))
			{
				cStmtObject.setBigDecimal(10,new BigDecimal("0.00"));
			
			}//end of if(lineItemVO.getAccountHeadTypeID().equals("STX#N"))
			else if(lineItemVO.getAccountHeadTypeID()!="STX#N")
			{
				if(lineItemVO.getAllowedAmt() != null)
				{
					cStmtObject.setBigDecimal(10,lineItemVO.getAllowedAmt());
				}//end of if(lineItemVO.getAllowedAmt() != null)
			
				else
				{
				cStmtObject.setString(10,null);
				}//end of else
			}//end of else if(lineItemVO.getAccountHeadTypeID()!="STX#N")
			cStmtObject.setString(11,lineItemVO.getRemarks());
			cStmtObject.setString(12,lineItemVO.getvaccineTypeID());//added for koc maternity
			cStmtObject.setLong(13,lineItemVO.getUpdatedBy());
			
			
			cStmtObject.registerOutParameter(2,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.registerOutParameter(14,OracleTypes.CURSOR);
			cStmtObject.execute();
			lngLineItemSeqID = cStmtObject.getLong(2);
			rs = (java.sql.ResultSet)cStmtObject.getObject(14);
			if(rs!=null)
			{
				while(rs.next())
				{
					//lineItemVO1 = new LineItemVO();
					if(rs.getString("CLM_BILL_SEQ_ID") != null){
						lineItemVO.setNextbillSeqID(new Long(rs.getLong("CLM_BILL_SEQ_ID")));
					}//end of if(rs.getString("CLM_BILL_DTL_SEQ_ID") != null)
					
					if(rs.getString("CLM_BILL_DTL_SEQ_ID") != null){
						lineItemVO.setNextLineItemSeqId(new Long(rs.getLong("CLM_BILL_DTL_SEQ_ID")));
					}//end of if(rs.getString("CLM_BILL_DTL_SEQ_ID") != null)
					lineItemVO.setNextbillNo(TTKCommon.checkNull(rs.getString("bill_no")));
									
			}
				lineItemVO.setLineItemSeqID(lngLineItemSeqID);
				
			}
			
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Statement in ClaimBillDAOImpl saveLineItemDetailNext()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in ClaimBillDAOImpl saveLineItemDetailNext()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		//return lineItemVO;
		return lineItemVO;
	}//end of saveLineItemDetailNext(LineItemVO lineItemVO)
	
	//ended*/

	/**
	 * This method returns the ArrayList, which contains all the Claim Bill details
	 * @param lngClaimSeqID long value contains seq id to get the Claim Bill Details
	 * @param lngUserSeqID long value contains Logged-in User Seq ID
	 * @return ArrayList object which contains all the Claim Bill details
	 * @exception throws TTKException
	 */
	public ArrayList getBillLineitemList(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
		ArrayList<Object> alBillLineitemList = null;
		Connection conn = null;
		CallableStatement cStmtObject1=null;
		CallableStatement cStmtObject2=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ClaimBillDetailVO claimBillDetailVO =null;
		LineItemVO lineItemVO = null;
		ArrayList<Object> alLineItemList = null;
		long lngBillSeqID = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject1 = (java.sql.CallableStatement)conn.prepareCall(strGetBillLineItemList);
			cStmtObject2 = (java.sql.CallableStatement)conn.prepareCall(strGetLineitemList);
			cStmtObject1.setLong(1,lngClaimSeqID);
			cStmtObject1.setLong(2,lngUserSeqID);
			cStmtObject1.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject1.execute();
			rs1 = (java.sql.ResultSet)cStmtObject1.getObject(3);
			if(rs1 != null){
				while(rs1.next()){
					if(alBillLineitemList == null){
						alBillLineitemList = new ArrayList<Object>();
					}//end of if(alBillLineitemList == null)
					claimBillDetailVO = new ClaimBillDetailVO();
					
					if(rs1.getString("CLM_BILL_SEQ_ID") != null){
						claimBillDetailVO.setBillSeqID(new Long(rs1.getLong("CLM_BILL_SEQ_ID")));
					}//end of if(rs1.getString("CLM_BILL_SEQ_ID") != null)
					
					claimBillDetailVO.setBillNbr(TTKCommon.checkNull(rs1.getString("BILL_NO")));
					
					if(rs1.getString("BILL_DATE") != null){
						claimBillDetailVO.setBillDate(new Date(rs1.getTimestamp("BILL_DATE").getTime()));
					}//end of if(rs1.getString("BILL_DATE") != null)
					claimBillDetailVO.setBillIssuedBy(TTKCommon.checkNull(rs1.getString("BILL_ISSUED_BY")));
					claimBillDetailVO.setBillWithPrescription(TTKCommon.checkNull(rs1.getString("BILLS_WITH_PRESCRIPTION_YN")));
					claimBillDetailVO.setBillTypeDesc(TTKCommon.checkNull(rs1.getString("BILL_TYPE")));
					claimBillDetailVO.setBillIncludedYN(TTKCommon.checkNull(rs1.getString("BILL_INCLUDED_YN")));
					claimBillDetailVO.setCompletedYN(TTKCommon.checkNull(rs1.getString("Completed_Yn")));
					claimBillDetailVO.setDonorExpYN(rs1.getString("DONOR_BILL_YN"));
					
					//added for CR KOC-Decoupling
					claimBillDetailVO.setBillsCompleteYN(TTKCommon.checkNull(rs1.getString("BILL_COMPLETE_YN")));
					
					lngBillSeqID = rs1.getLong("CLM_BILL_SEQ_ID");
					
					cStmtObject2.setLong(1,lngBillSeqID);
					cStmtObject2.registerOutParameter(2,OracleTypes.CURSOR);
					cStmtObject2.execute();
					rs2 = (java.sql.ResultSet)cStmtObject2.getObject(2);
					alLineItemList= null;
					
					if(rs2 != null){
						while (rs2.next()){
							if(alLineItemList == null){
								alLineItemList = new ArrayList<Object>();
							}//end of if(alLineItemList == null)
							lineItemVO=new LineItemVO();
							
							if(rs2.getString("CLM_BILL_DTL_SEQ_ID") != null){
								lineItemVO.setLineItemSeqID(new Long(rs2.getLong("CLM_BILL_DTL_SEQ_ID")));
							}//end of if(rs2.getString("CLM_BILL_DTL_SEQ_ID") != null)
							
							lineItemVO.setLineItemNbr(TTKCommon.checkNull(rs2.getString("DESCRIPTION")));
							lineItemVO.setAccountHeadDesc(TTKCommon.checkNull(rs2.getString("WARD_DESCRIPTION")));
							
							if(rs2.getString("REQUESTED_AMOUNT") != null){
								lineItemVO.setRequestedAmt(new BigDecimal(rs2.getString("REQUESTED_AMOUNT")));
							}//end of if(rs2.getString("REQUESTED_AMOUNT") != null)
							
							if(rs2.getString("ALLOWED_AMOUNT") != null){
								lineItemVO.setAllowedAmt(new BigDecimal(rs2.getString("ALLOWED_AMOUNT")));
							}//end of if(rs2.getString("ALLOWED_AMOUNT") != null)
							else{
								lineItemVO.setAllowedAmt(new BigDecimal("0.00"));
							}//end of else
							
							lineItemVO.setAccountHeadTypeID(TTKCommon.checkNull(rs2.getString("WARD_TYPE_ID")));
							lineItemVO.setRoomTypeID(TTKCommon.checkNull(rs2.getString("ROOM_TYPE_ID")));
							lineItemVO.setvaccineTypeID(TTKCommon.checkNull(rs2.getString("VACCINATION_TYPE_ID")));//added for maternity
							lineItemVO.setRoomTypeDesc(TTKCommon.checkNull(rs2.getString("ROOM_DESCRIPTION")));
							
							if(rs2.getString("NUMBER_OF_DAYS") != null){
								lineItemVO.setNbrofDays(new Integer(rs2.getInt("NUMBER_OF_DAYS")));
							}//end of if(rs2.getString("NUMBER_OF_DAYS") != null)
							
							lineItemVO.setAllowYN(TTKCommon.checkNull(rs2.getString("ALLOW_YN")));
							
							if(rs2.getString("REJECTED_AMOUNT") != null){
								lineItemVO.setDisAllowedAmt(new BigDecimal(rs2.getString("REJECTED_AMOUNT")));
							}//end of if(rs2.getString("REJECTED_AMOUNT") != null)
							else{
								lineItemVO.setDisAllowedAmt(new BigDecimal("0.00"));
							}//end of else
							
							lineItemVO.setRemarks(TTKCommon.checkNull(rs2.getString("REMARKS")));
							alLineItemList.add(lineItemVO);
						}//end of while (rs2.next())
					}//end of if(rs2 != null)
					claimBillDetailVO.setLineItemList(alLineItemList);
					alBillLineitemList.add(claimBillDetailVO);
					if (rs2 != null){
						rs2.close();
					}//end of if (rs2 != null)
					rs2=null;
				}//end of while(rs1.next())
				
				
			}//end of if(rs1 != null)
			if (cStmtObject2 != null){
				cStmtObject2.close();
			}//end of if (cStmtObject2 != null)
			cStmtObject2 = null;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Second Resultset in ClaimBillDAOImpl getBillLineitemList()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try
					{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in ClaimBillDAOImpl getBillLineitemList()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
					{
						try
						{
							if(cStmtObject2 != null) cStmtObject2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Statement in ClaimBillDAOImpl getBillLineitemList()",sqlExp);
							throw new TTKException(sqlExp, "bill");
						}//end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
						{
							try
							{
								if(cStmtObject1 != null) cStmtObject1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the FirstStatement in ClaimBillDAOImpl getBillLineitemList()",sqlExp);
								throw new TTKException(sqlExp, "bill");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
							{
								try{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in ClaimBillDAOImpl getBillLineitemList()",sqlExp);
									throw new TTKException(sqlExp, "bill");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs2 = null;
				rs1 = null;
				cStmtObject2 = null;
				cStmtObject1 = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alBillLineitemList;
	}//end of getBillLineitemList(long lngClaimSeqID,long lngUserSeqID)
	
	/**
	 * This method returns the LineItemVO, which contains all the Line Item details
	 * @param lngLineItemSeqID long value contains seq id to get the Line Item details
	 * @return LineItemVO object which contains all the Line Item details
	 * @exception throws TTKException
	 */
	
	public LineItemVO getLineItem(long lngLineItemSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		LineItemVO lineItemVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetLineitem);
			cStmtObject.setLong(1,lngLineItemSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			
			if(rs != null){
				while(rs.next()){
					lineItemVO = new LineItemVO();
					
					if(rs.getString("CLM_BILL_SEQ_ID") != null){
						lineItemVO.setBillSeqID(new Long(rs.getLong("CLM_BILL_SEQ_ID")));
					}//end of if(rs.getString("CLM_BILL_DTL_SEQ_ID") != null)
					
					if(rs.getString("CLM_BILL_DTL_SEQ_ID") != null){
						lineItemVO.setLineItemSeqID(new Long(rs.getLong("CLM_BILL_DTL_SEQ_ID")));
					}//end of if(rs.getString("CLM_BILL_DTL_SEQ_ID") != null)
					
					lineItemVO.setLineItemNbr(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					lineItemVO.setAccountHeadDesc(TTKCommon.checkNull(rs.getString("WARD_DESCRIPTION")));
					
					if(rs.getString("REQUESTED_AMOUNT") != null){
						lineItemVO.setRequestedAmt(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
					}//end of if(rs.getString("REQUESTED_AMOUNT") != null)
					
					if(rs.getString("ALLOWED_AMOUNT") != null){
						lineItemVO.setAllowedAmt(new BigDecimal(rs.getString("ALLOWED_AMOUNT")));
					}//end of if(rs.getString("ALLOWED_AMOUNT") != null)
					else{
						lineItemVO.setAllowedAmt(new BigDecimal("0.00"));
					}//end of else
					
					lineItemVO.setAccountHeadTypeID(TTKCommon.checkNull(rs.getString("WARD_TYPE_ID")));
					lineItemVO.setRoomTypeID(TTKCommon.checkNull(rs.getString("ROOM_TYPE_ID")));
					lineItemVO.setvaccineTypeID(TTKCommon.checkNull(rs.getString("VACCINATION_TYPE_ID")));//added for maternity
                   
					lineItemVO.setRoomTypeDesc(TTKCommon.checkNull(rs.getString("ROOM_DESCRIPTION")));
					
					if(rs.getString("NUMBER_OF_DAYS") != null){
						lineItemVO.setNbrofDays(new Integer(rs.getInt("NUMBER_OF_DAYS")));
					}//end of if(rs.getString("NUMBER_OF_DAYS") != null)
					
					if(rs.getString("REJECTED_AMOUNT") != null){
						lineItemVO.setDisAllowedAmt(new BigDecimal(rs.getString("REJECTED_AMOUNT")));
					}//end of if(rs.getString("REJECTED_AMOUNT") != null)
					
					lineItemVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
				}//end of while(rs.next())
			}//end of if(rs != null)
			return lineItemVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bill");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bill");
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
					log.error("Error while closing the Resultset in ClaimBillDAOImpl getLineItem()",sqlExp);
					throw new TTKException(sqlExp, "bill");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimBillDAOImpl getLineItem()",sqlExp);
						throw new TTKException(sqlExp, "bill");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimBillDAOImpl getLineItem()",sqlExp);
							throw new TTKException(sqlExp, "bill");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bill");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getLineItem(long lngClaimBillSeqID,long lngUserSeqID)
}//end of ClaimBillDAOImpl.java
