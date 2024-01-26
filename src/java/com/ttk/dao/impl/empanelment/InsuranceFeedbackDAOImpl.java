/**
 * @ (#) InsuranceFeedbackDAOImpl.java Oct 25, 2005
 * Project      : TTK HealthCare Services
 * File         : InsuranceFeedbackDAOImpl.java
 * Author       : Ravindra
 * Company      : Span Systems Corporation
 * Date Created : Oct 25, 2005
 *
 * @author       :  Ravindra

 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.empanelment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.InsuranceFeedbackVO;
import com.ttk.dto.empanelment.BrokerFeedbackVO;

public class InsuranceFeedbackDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger
			.getLogger(InsuranceFeedbackDAOImpl.class);

	private static final int INS_FEEDBACK_SEQ_ID = 1;
	private static final int INS_SEQ_ID = 2;
	private static final int FEEDBACK_RECEIVED_DATE = 3;
	private static final int DESCRIPTION = 4;
	private static final int COMM_TYPE_ID = 5;
	private static final int USER_SEQ_ID = 6;
	private static final int ROW_PROCESSED = 7;

	private static final String strInsuranceFeedbackList = "SELECT * FROM(SELECT INS_FEEDBACK_SEQ_ID, FEEDBACK_RECEIVED_DATE,DESCRIPTION,COMM_TYPE_ID,DENSE_RANK() OVER (ORDER BY #)Q FROM APP.TPA_INS_FEEDBACK ";
	private static final String strBrokerFeedbackList = "SELECT * FROM(SELECT INS_FEEDBACK_SEQ_ID, FEEDBACK_RECEIVED_DATE,DESCRIPTION,COMM_TYPE_ID,DENSE_RANK() OVER (ORDER BY #)Q FROM APP.TPA_BRO_FEEDBACK ";
	private static final String strCompanyFeedback = "SELECT INS_FEEDBACK_SEQ_ID, FEEDBACK_RECEIVED_DATE,DESCRIPTION,COMM_TYPE_ID FROM APP.TPA_INS_FEEDBACK A WHERE A.INS_FEEDBACK_SEQ_ID = ?";
	private static final String strAddUpdateFeedback = "{CALL INSCOMP_EMPANEL_PKG_PR_INS_FEEDBACK_SAVE(?,?,?,?,?,?,?)}";
	private static final String strDeleteInsuranceProduct = "{CALL INSCOMP_EMPANEL_PKG_PR_INS_FEEDBACK_DELETE(?,?,?)}";
	// added for kocb broker login strAddUpdateBrokerFeedback
	private static final String strAddUpdateBrokerFeedback = "{CALL BROCOMP_EMPANEL_PKG_PR_INS_FEEDBACK_SAVE(?,?,?,?,?,?,?)}";
	private static final String strBrokerFeedback = "SELECT INS_FEEDBACK_SEQ_ID, FEEDBACK_RECEIVED_DATE,DESCRIPTION,COMM_TYPE_ID FROM APP.TPA_BRO_FEEDBACK A WHERE A.INS_FEEDBACK_SEQ_ID = ?";
	private static final String strDeleteBrokerProduct = "{CALL BROCOMP_EMPANEL_PKG_PR_INS_FEEDBACK_DELETE(?,?,?)}";

	/**
	 * This method returns the ArrayList, which contains the
	 * InsuranceFeedbackVO's which are populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceFeedbackVO object's which contains the
	 *         InsuranceCompany Feedback details
	 * @exception throws TTKException
	 */
	public ArrayList getCompanyFeedbackList(ArrayList alSearchObjects)
			throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strInsuranceFeedbackList;
		String strInsSeqId = "";
		String strFromDate = "";
		String strToDate = "";
		InsuranceFeedbackVO insuranceFeedbackVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		if (alSearchObjects != null && alSearchObjects.size() > 0) {
			strInsSeqId = ((SearchCriteria) alSearchObjects.get(0)).getValue();
			
			strFromDate=((SearchCriteria) alSearchObjects.get(1)).getValue();
			strToDate=((SearchCriteria) alSearchObjects.get(2)).getValue();
			
			strFromDate =(strFromDate==null||"".equals(strFromDate))?null:"'"+strFromDate+"'";
			strToDate =(strToDate==null||"".equals(strToDate))?null:"'"+strToDate+"'";
				
					
			strStaticQuery = TTKCommon.replaceInString(strStaticQuery, "?",
					strInsSeqId);
			/*
			 * sbfDynamicQuery =
			 * sbfDynamicQuery.append(" WHERE INS_SEQ_ID = "+strInsSeqId
			 * +" AND trunc(FEEDBACK_RECEIVED_DATE) BETWEEN nvl(to_date('"
			 * +strFromDate+
			 * "','dd/mm/yyyy'), to_date('01/01/2005','dd/mm/yyyy') ) AND  nvl(to_date('"
			 * +strToDate+"','dd/mm/yyyy'), SYSDATE)");
			 */
			if ( TTKCommon.checkNull(strFromDate).length()<1  ) {
				strFromDate=null;
			}
			if ( TTKCommon.checkNull(strToDate).length()<1  ) {
				strToDate=null;
			}
	/*			sbfDynamicQuery = sbfDynamicQuery
						.append(" WHERE INS_SEQ_ID = "
								+ strInsSeqId
								+ " AND DATE(FEEDBACK_RECEIVED_DATE) BETWEEN COALESCE ("
								+ strFromDate
								+ ", date('01/01/2005')) and COALESCE ("
								+ strToDate + ",current_date))aa");*/
				
			sbfDynamicQuery = sbfDynamicQuery
					.append(" WHERE INS_SEQ_ID = "
							+ strInsSeqId
							+ " AND DATE(FEEDBACK_RECEIVED_DATE) BETWEEN COALESCE (to_date("
							+ strFromDate
							+ ",'dd/mm/yyyy'),date('01/01/2005')) and COALESCE (to_date("
							+ strToDate +",'dd/mm/yyyy') ,current_date))aa");
		}// end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
			// build the Order By Clause
		strStaticQuery = TTKCommon
				.replaceInString(
						strStaticQuery,
						"#",
						(String) alSearchObjects.get(alSearchObjects.size() - 4)
								+ " "
								+ (String) alSearchObjects.get(alSearchObjects
										.size() - 3));
		// build the row numbers to be fetched
		sbfDynamicQuery = sbfDynamicQuery.append(" WHERE Q >= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 2)
				+ " AND Q <= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		//System.out.println("Insurance feedback....." + strStaticQuery);
		try {
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					insuranceFeedbackVO = new InsuranceFeedbackVO();
					if (rs.getString("INS_FEEDBACK_SEQ_ID") != null)
						insuranceFeedbackVO.setFeedbackID((rs
								.getLong("INS_FEEDBACK_SEQ_ID")));
					if (rs.getTimestamp("FEEDBACK_RECEIVED_DATE") != null) {
						insuranceFeedbackVO
								.setFeebackRecievedDate(new java.util.Date(rs
										.getTimestamp("FEEDBACK_RECEIVED_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("FEEDBACK_RECEIVED_DATE") !=
						// null)
					if (rs.getTimestamp("FEEDBACK_RECEIVED_DATE") != null) {
						insuranceFeedbackVO.setRecievedDate(new java.util.Date(
								rs.getTimestamp("FEEDBACK_RECEIVED_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("FEEDBACK_RECEIVED_DATE") !=
						// null)

					insuranceFeedbackVO.setDescription(TTKCommon.checkNull(rs
							.getString("DESCRIPTION")));
					insuranceFeedbackVO.setCommType(TTKCommon.checkNull(rs
							.getString("COMM_TYPE_ID")));
					resultList.add(insuranceFeedbackVO);
					conn.commit();
				}// End of while (rs.next())
			}// End of if(rs != null)
			return (ArrayList) resultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null)
						rs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceFeedbackDAOImpl getCompanyFeedbackList()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (pStmt != null)
							pStmt.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceFeedbackDAOImpl getCompanyFeedbackList()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null)
								conn.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceFeedbackDAOImpl getCompanyFeedbackList()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getCompanyFeedbackList(ArrayList alSearchObjects)

	/**
	 * This method returns the InsuranceFeedbackVO, which contains the
	 * InsuranceCompany Feedback details which are populated from the database
	 * 
	 * @param lngInsFeedbackSeqID
	 *            long value which contains Feedback Seq ID
	 * @return InsuranceFeedbackVO which contains the InsuranceCompany Feedback
	 *         details
	 * @exception throws TTKException
	 */
	public InsuranceFeedbackVO getCompanyFeedback(long lngInsFeedbackSeqID)
			throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		InsuranceFeedbackVO insuranceFeedbackVO = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strCompanyFeedback);
			pStmt.setLong(1, lngInsFeedbackSeqID);
			rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					insuranceFeedbackVO = new InsuranceFeedbackVO();
					if (rs.getString("INS_FEEDBACK_SEQ_ID") != null) {
						insuranceFeedbackVO.setFeedbackID((rs
								.getLong("INS_FEEDBACK_SEQ_ID")));
					}// end of if(rs.getString("INS_FEEDBACK_SEQ_ID") != null)
					if (rs.getTimestamp("FEEDBACK_RECEIVED_DATE") != null) {
						insuranceFeedbackVO
								.setFeebackRecievedDate(new java.util.Date(rs
										.getTimestamp("FEEDBACK_RECEIVED_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("FEEDBACK_RECEIVED_DATE") !=
						// null)
					if (rs.getTimestamp("FEEDBACK_RECEIVED_DATE") != null) {
						insuranceFeedbackVO.setRecievedDate(new java.util.Date(
								rs.getTimestamp("FEEDBACK_RECEIVED_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("FEEDBACK_RECEIVED_DATE") !=
						// null)

					insuranceFeedbackVO.setDescription(TTKCommon.checkNull(rs
							.getString("DESCRIPTION")));
					insuranceFeedbackVO.setCommType(TTKCommon.checkNull(rs
							.getString("COMM_TYPE_ID")));
				}// end of while(rs.next())
			}// end of if(rs != null)
			return insuranceFeedbackVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null)
						rs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceFeedbackDAOImpl getCompanyFeedback()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (pStmt != null)
							pStmt.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceFeedbackDAOImpl getCompanyFeedback()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null)
								conn.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceFeedbackDAOImpl getCompanyFeedback()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getCompanyFeedback(long lngInsFeedbackSeqID)

	/**
	 * This method adds or updates the insurance Feedback The method also calls
	 * other methods on DAO to insert/update the insurance details to the
	 * database
	 * 
	 * @param insuranceFeedbackVO
	 *            InsuranceFeedbackVO object which contains the
	 *            insuranceFeedback details to be added/updated
	 * @return long value, Insurance Feedback Sequence Id
	 * @exception throws TTKException
	 */
	public long addUpdateCompanyFeedback(InsuranceFeedbackVO insuranceFeedbackVO)
			throws TTKException {
		long lResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAddUpdateFeedback);
			if (insuranceFeedbackVO.getFeedbackID() == null) {
				cStmtObject.setLong(INS_FEEDBACK_SEQ_ID, 0);
			}// end of if(insuranceFeedbackVO.getFeedbackID() ==null )
			else {
				cStmtObject.setLong(INS_FEEDBACK_SEQ_ID, insuranceFeedbackVO
						.getFeedbackID().longValue());
			}// end of else
			cStmtObject.setLong(INS_SEQ_ID,
					insuranceFeedbackVO.getInsuranceSeqID());
			if (insuranceFeedbackVO.getRecievedDate() != null) {
				cStmtObject.setTimestamp(FEEDBACK_RECEIVED_DATE, new Timestamp(
						insuranceFeedbackVO.getRecievedDate().getTime()));
			}// end of if(insuranceFeedbackVO.getRecievedDate() != null)
			else{
				cStmtObject.setTimestamp(FEEDBACK_RECEIVED_DATE, null);
			}
			cStmtObject.setString(DESCRIPTION,
					insuranceFeedbackVO.getDescription());
			cStmtObject.setString(COMM_TYPE_ID,
					insuranceFeedbackVO.getCommType());
			cStmtObject
					.setLong(USER_SEQ_ID, insuranceFeedbackVO.getUpdatedBy());
			cStmtObject
					.registerOutParameter(INS_FEEDBACK_SEQ_ID, Types.BIGINT);
			cStmtObject.registerOutParameter(ROW_PROCESSED, Types.INTEGER);
			cStmtObject.execute();
			conn.commit();
			lResult = cStmtObject.getLong(INS_FEEDBACK_SEQ_ID);
			
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceFeedbackDAOImpl addUpdateCompanyFeedback()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null)
							conn.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceFeedbackDAOImpl addUpdateCompanyFeedback()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return lResult;
	}// end of addUpdateCompanyFeedback(InsuranceFeedbackVO insuranceFeedbackVO)

	/**
	 * This method delete's the Insurance Feedback records from the database.
	 * 
	 * @param strFeedbackSeqID
	 *            String object which contains the Feedback sequence id's to be
	 *            deleted
	 * @param lnguserSeqID
	 *            for which user has loggedin
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
	 * @exception throws TTKException
	 */
	public int deleteCompanyFeedback(String strFeedbackSeqID, long lnguserSeqID)
			throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strDeleteInsuranceProduct);
			cStmtObject.setString(1, strFeedbackSeqID);// string of sequence
														// id's which are
														// separated with | as
														// separator (Note:
														// String should also
														// end with | at the
														// end)
			cStmtObject.setLong(2, lnguserSeqID);// user sequence id
			cStmtObject.registerOutParameter(3, Types.INTEGER);// out parameter
																// which gives
																// the number of
																// records
																// deleted
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceFeedbackDAOImpl deleteCompanyFeedback()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null)
							conn.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceFeedbackDAOImpl deleteCompanyFeedback()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteCompanyFeedback(String strFeedbackSeqID,long lnguserSeqID)

	// added for broker login kocb
	/*
	 * public long addUpdateBrokerFeedback(BrokerFeedbackVO brokerFeedbackVO)
	 * throws TTKException { long lResult =0; Connection conn = null;
	 * CallableStatement cStmtObject=null; try{ conn =
	 * ResourceManager.getConnection(); cStmtObject =
	 * (java.sql.CallableStatement)conn.prepareCall(strAddUpdateBrokerFeedback);
	 * if(brokerFeedbackVO.getFeedbackID() ==null ) {
	 * cStmtObject.setLong(INS_FEEDBACK_SEQ_ID,0); }//end of
	 * if(insuranceFeedbackVO.getFeedbackID() ==null ) else {
	 * cStmtObject.setLong
	 * (INS_FEEDBACK_SEQ_ID,brokerFeedbackVO.getFeedbackID()); }//end of else
	 * cStmtObject.setLong(INS_SEQ_ID,brokerFeedbackVO.getInsuranceSeqID());
	 * if(brokerFeedbackVO.getRecievedDate() != null){
	 * cStmtObject.setTimestamp(FEEDBACK_RECEIVED_DATE,new
	 * Timestamp(brokerFeedbackVO.getRecievedDate().getTime())); }//end of
	 * if(insuranceFeedbackVO.getRecievedDate() != null)
	 * cStmtObject.setString(DESCRIPTION,brokerFeedbackVO.getDescription());
	 * cStmtObject.setString(COMM_TYPE_ID,brokerFeedbackVO.getCommType());
	 * cStmtObject.setLong(USER_SEQ_ID,brokerFeedbackVO.getUpdatedBy());
	 * cStmtObject.registerOutParameter(INS_FEEDBACK_SEQ_ID,Types.BIGINT);
	 * cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
	 * cStmtObject.execute(); lResult =
	 * cStmtObject.getLong(INS_FEEDBACK_SEQ_ID); }// end of try catch
	 * (SQLException sqlExp) { throw new TTKException(sqlExp, "insurance");
	 * }//end of catch (SQLException sqlExp) catch (Exception exp) { throw new
	 * TTKException(exp, "insurance"); }//end of catch (Exception exp) finally {
	 * /* Nested Try Catch to ensure resource closure
	 */
	/*
	 * try // First try closing the Statement { try { if (cStmtObject != null)
	 * cStmtObject.close(); }//end of try catch (SQLException sqlExp) {
	 * log.error(
	 * "Error while closing the Statement in InsuranceFeedbackDAOImpl addUpdateCompanyFeedback()"
	 * ,sqlExp); throw new TTKException(sqlExp, "insurance"); }//end of catch
	 * (SQLException sqlExp) finally // Even if statement is not closed, control
	 * reaches here. Try closing the connection now. { try { if(conn != null)
	 * conn.close(); }//end of try catch (SQLException sqlExp) { log.error(
	 * "Error while closing the Connection in InsuranceFeedbackDAOImpl addUpdateCompanyFeedback()"
	 * ,sqlExp); throw new TTKException(sqlExp, "insurance"); }//end of catch
	 * (SQLException sqlExp) }//end of finally Connection Close }//end of try
	 * catch (TTKException exp) { throw new TTKException(exp, "insurance");
	 * }//end of catch (TTKException exp) finally // Control will reach here in
	 * anycase set null to the objects { cStmtObject = null; conn = null; }//end
	 * of finally }//end of finally return lResult; }//end of
	 * addUpdateCompanyFeedback(InsuranceFeedbackVO insuranceFeedbackVO)
	 */
	public int deleteBrokerFeedback(String strFeedbackSeqID, long lnguserSeqID)
			throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strDeleteBrokerProduct);
			cStmtObject.setString(1, strFeedbackSeqID);// string of sequence
														// id's which are
														// separated with | as
														// separator (Note:
														// String should also
														// end with | at the
														// end)
			cStmtObject.setLong(2, lnguserSeqID);// user sequence id
			cStmtObject.registerOutParameter(3, Types.INTEGER);// out parameter
																// which gives
																// the number of
																// records
																// deleted
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceFeedbackDAOImpl deleteCompanyFeedback()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null)
							conn.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceFeedbackDAOImpl deleteCompanyFeedback()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteCompanyFeedback(String strFeedbackSeqID,long lnguserSeqID)

	public ArrayList getBrokerFeedbackList(ArrayList alSearchObjects)
			throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = "";

		strStaticQuery = strBrokerFeedbackList;
		String strInsSeqId = "";
		String strFromDate = "";
		String strToDate = "";
		InsuranceFeedbackVO insuranceFeedbackVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		if (alSearchObjects != null && alSearchObjects.size() > 0) {
			strInsSeqId = ((SearchCriteria) alSearchObjects.get(0)).getValue();
			
			strFromDate=((SearchCriteria) alSearchObjects.get(1)).getValue();
			strToDate=((SearchCriteria) alSearchObjects.get(2)).getValue();
			
			strFromDate =(strFromDate==null||"".equals(strFromDate))?null:"'"+strFromDate+"'";
			strToDate =(strToDate==null||"".equals(strToDate))?null:"'"+strToDate+"'";
				
					
			strStaticQuery = TTKCommon.replaceInString(strStaticQuery, "?",
					strInsSeqId);

			if ( TTKCommon.checkNull(strFromDate).length()<1  ) {
				strFromDate=null;
			}
			if ( TTKCommon.checkNull(strToDate).length()<1  ) {
				strToDate=null;
			}
		
			/*sbfDynamicQuery = sbfDynamicQuery
					.append(" WHERE INS_SEQ_ID = "
							+ strInsSeqId
							+ " AND DATE(FEEDBACK_RECEIVED_DATE) BETWEEN COALESCE(('"
							+ strFromDate
							+ "','dd/mm/yyyy'), date('01/01/2005','dd/mm/yyyy') ) AND  COALESCE(('"
							+ strToDate + "','dd/mm/yyyy'), current_date)");*/
			sbfDynamicQuery = sbfDynamicQuery
					.append(" WHERE INS_SEQ_ID = "
							+ strInsSeqId
							+ " AND DATE(FEEDBACK_RECEIVED_DATE) BETWEEN COALESCE (to_date("
							+ strFromDate
							+ ",'dd/mm/yyyy'),date('01/01/2005')) and COALESCE (to_date("
							+ strToDate +",'dd/mm/yyyy') ,current_date))aa");
		}// end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
			// build the Order By Clause
		strStaticQuery = TTKCommon
				.replaceInString(
						strStaticQuery,
						"#",
						(String) alSearchObjects.get(alSearchObjects.size() - 4)
								+ " "
								+ (String) alSearchObjects.get(alSearchObjects
										.size() - 3));
		// build the row numbers to be fetched
		sbfDynamicQuery = sbfDynamicQuery.append(" WHERE Q >= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 2)
				+ " AND Q <= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		log.info("Broker add  search::::::::::" + strStaticQuery);
		try {
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					insuranceFeedbackVO = new InsuranceFeedbackVO();
					if (rs.getString("INS_FEEDBACK_SEQ_ID") != null)
						insuranceFeedbackVO.setFeedbackID((rs
								.getLong("INS_FEEDBACK_SEQ_ID")));
					if (rs.getTimestamp("FEEDBACK_RECEIVED_DATE") != null) {
						insuranceFeedbackVO
								.setFeebackRecievedDate(new java.util.Date(rs
										.getTimestamp("FEEDBACK_RECEIVED_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("FEEDBACK_RECEIVED_DATE") !=
						// null)
					if (rs.getTimestamp("FEEDBACK_RECEIVED_DATE") != null) {
						insuranceFeedbackVO.setRecievedDate(new java.util.Date(
								rs.getTimestamp("FEEDBACK_RECEIVED_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("FEEDBACK_RECEIVED_DATE") !=
						// null)

					insuranceFeedbackVO.setDescription(TTKCommon.checkNull(rs
							.getString("DESCRIPTION")));
					insuranceFeedbackVO.setCommType(TTKCommon.checkNull(rs
							.getString("COMM_TYPE_ID")));
					resultList.add(insuranceFeedbackVO);
				}// End of while (rs.next())
			}// End of if(rs != null)
			return (ArrayList) resultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null)
						rs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceFeedbackDAOImpl getCompanyFeedbackList()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (pStmt != null)
							pStmt.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceFeedbackDAOImpl getCompanyFeedbackList()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null)
								conn.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceFeedbackDAOImpl getCompanyFeedbackList()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getCompanyFeedbackList(ArrayList alSearchObjects)

	public InsuranceFeedbackVO getBrokerFeedback(long lngInsFeedbackSeqID)
			throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		InsuranceFeedbackVO insuranceFeedbackVO = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strBrokerFeedback);
			pStmt.setLong(1, lngInsFeedbackSeqID);
			rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					insuranceFeedbackVO = new InsuranceFeedbackVO();
					if (rs.getString("INS_FEEDBACK_SEQ_ID") != null) {
						insuranceFeedbackVO.setFeedbackID((rs
								.getLong("INS_FEEDBACK_SEQ_ID")));
					}// end of if(rs.getString("INS_FEEDBACK_SEQ_ID") != null)
					if (rs.getTimestamp("FEEDBACK_RECEIVED_DATE") != null) {
						insuranceFeedbackVO
								.setFeebackRecievedDate(new java.util.Date(rs
										.getTimestamp("FEEDBACK_RECEIVED_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("FEEDBACK_RECEIVED_DATE") !=
						// null)
					if (rs.getTimestamp("FEEDBACK_RECEIVED_DATE") != null) {
						insuranceFeedbackVO.setRecievedDate(new java.util.Date(
								rs.getTimestamp("FEEDBACK_RECEIVED_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("FEEDBACK_RECEIVED_DATE") !=
						// null)

					insuranceFeedbackVO.setDescription(TTKCommon.checkNull(rs
							.getString("DESCRIPTION")));
					insuranceFeedbackVO.setCommType(TTKCommon.checkNull(rs
							.getString("COMM_TYPE_ID")));
				}// end of while(rs.next())
			}// end of if(rs != null)
			return insuranceFeedbackVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null)
						rs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceFeedbackDAOImpl getCompanyFeedback()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (pStmt != null)
							pStmt.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceFeedbackDAOImpl getCompanyFeedback()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null)
								conn.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceFeedbackDAOImpl getCompanyFeedback()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getCompanyFeedback(long lngInsFeedbackSeqID)
		// end added for kocb

	public long addUpdateBrokerFeedback(InsuranceFeedbackVO insuranceFeedbackVO)
			throws TTKException {
		long lResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAddUpdateBrokerFeedback);
			if (insuranceFeedbackVO.getFeedbackID() == null) {
				cStmtObject.setLong(INS_FEEDBACK_SEQ_ID, 0);
			}// end of if(insuranceFeedbackVO.getFeedbackID() ==null )
			else {
				cStmtObject.setLong(INS_FEEDBACK_SEQ_ID,
						insuranceFeedbackVO.getFeedbackID());
			}// end of else
			cStmtObject.setLong(INS_SEQ_ID,
					insuranceFeedbackVO.getInsuranceSeqID());
			if (insuranceFeedbackVO.getRecievedDate() != null) {
				cStmtObject.setTimestamp(FEEDBACK_RECEIVED_DATE, new Timestamp(
						insuranceFeedbackVO.getRecievedDate().getTime()));
			}// end of if(insuranceFeedbackVO.getRecievedDate() != null)
			cStmtObject.setString(DESCRIPTION,
					insuranceFeedbackVO.getDescription());
			cStmtObject.setString(COMM_TYPE_ID,
					insuranceFeedbackVO.getCommType());
			cStmtObject
					.setLong(USER_SEQ_ID, insuranceFeedbackVO.getUpdatedBy());
			cStmtObject.registerOutParameter(INS_FEEDBACK_SEQ_ID, Types.BIGINT);
			cStmtObject.registerOutParameter(ROW_PROCESSED, Types.INTEGER);
			cStmtObject.execute();
			lResult = cStmtObject.getLong(INS_FEEDBACK_SEQ_ID);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceFeedbackDAOImpl addUpdateCompanyFeedback()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null)
							conn.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceFeedbackDAOImpl addUpdateCompanyFeedback()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return lResult;
	}// end of addUpdateCompanyFeedback(InsuranceFeedbackVO insuranceFeedbackVO)

}// end of InsuranceFeedbackDAOImpl
