/**
 *   @ (#) TDSRemittanceDAOImpl.java August 6, 2009
 *   Project      : TTK HealthCare Services
 *   File         : TDSRemittanceDAOImpl.java
 *   Author       : Navin Kumar R
 *   Company      : Span Systems Corporation
 *   Date Created : August 6, 2009
 *
 *   @author       :
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dao.impl.finance;

import java.io.Serializable;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import oracle.jdbc.driver.OracleTypes;


import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.finance.AcknowledgmentInfoVO;
import com.ttk.dto.finance.CertificateVO;
import com.ttk.dto.finance.DailyTransferVO;
import com.ttk.dto.finance.MonthlyRemittanceVO;
import com.ttk.dto.finance.MonthlyRemittanceDetailVO;
import com.ttk.dto.finance.TDSHospitalInfoVO;

/** This is a DAO class used for Daily/Monthly Remittance or Acknowledgment Information.
 * @author ramakrishna_km
 *
 */
public class TDSRemittanceDAOImpl implements BaseDAO, Serializable {

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = -139880240236497084L;

	/**
	 * Calling Logger Class.
	 */
	private static Logger log = Logger.getLogger(TDSRemittanceDAOImpl.class);

	/**
	 * This variable is used in all the methods in the exception block.
	 */
	private static final String strTDSRemittance = "tdsremittance";

	private static final String strDailyTransferList = "{CALL TDS_PKG.SELECT_DAILY_REMITTANCE(?,?,?,?,?,?,?,?,?)}";
	private static final String strSetDailyTransfer = "{CALL TDS_PKG.SAVE_DAILY_REMITTANCE(?,?,?,?)}";
	private static final String strMonthlyRemittanceList = "{CALL TDS_PKG.SELECT_MONTHLY_REMIT_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strSaveMonthlyRemittance = "{CALL TDS_PKG.SAVE_MONTHLY_REMITTANCE(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetMonthlyRemitDetail = "{CALL TDS_PKG.SELECT_MONTHLY_REMIT(?,?)}";
	private static final String strAckInfoList = "{CALL TDS_PKG.SELECT_ACKNOWLEDGE_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strSaveAckInfo = "{CALL TDS_PKG.SAVE_TPA_TDS_ACKNOWLEDGEMENTS(?,?,?,?,?,?,?,?)}";
	private static final String strSelIncludExcludTdsClms="{CALL TDS_PKG.SELECT_INCLUD_EXCLUD_TDS_CLMS(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strInclExclTdsClaims="{CALL TDS_PKG.INCLUDE_EXCLUDE_TDS_CLAIMS(?,?,?,?)}";
	private static final String strSelectFinancialYear="{CALL TDS_PKG.SELECT_FINANCIAL_YEAR(?,?,?,?,?)}";
	private static final String strGenerateTdsBatch="{CALL TDS_PKG.GENERATE_TDS_BATCH_INDV(?,?,?,?,?,?,?)}";
	private static final String strTdsDeductedHospitals="{CALL TDS_PKG.SELECT_TDS_DEDUCTED_HOSP(?,?,?,?,?,?,?,?)}";
	private static final String strGenerateTdsBatchIndv="{CALL TDS_PKG.GENERATE_TDS_BATCH_INDV(?,?,?,?,?,?,?)}";
	private static final String strGenTdsCertScheduler="{CALL TDS_PKG.GENERATE_TDS_CERT_SCHEDULER(?)}";
	private static final String strGenTdsCertIndScheduler="{CALL TDS_PKG.GENERATE_TDS_CERT_IND_SCHED(?)}";
	private static final String strSaveDocGenInfo="{CALL TDS_PKG.SAVE_DOC_GENERATION_INFO(?)}";
	

	/**
	 * This method returns the ArrayList, which contains the DailyTransferVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of DailyTransferVO object's which contains the details of the DailyTransfer
	 * @throws TTKException
	 */
	public ArrayList<Object> getDailyTransferList(ArrayList<Object> alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		DailyTransferVO dailyTransferVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDailyTransferList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);

			if(rs != null){
				while(rs.next()){
					dailyTransferVO = new DailyTransferVO();

					if(rs.getString("TDS_REPORT_MASTER_SEQ_ID") != null){
						dailyTransferVO.setMasterSeqID(new Long(rs.getLong("TDS_REPORT_MASTER_SEQ_ID")));
					}//end of if(rs.getString(TDS_REPORT_MASTER_SEQ_ID) != null)

					dailyTransferVO.setClaimSettleNo(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NUMBER")));

					if(rs.getTimestamp("GENERATED_DATE") != null){
						dailyTransferVO.setChequeIssDate(new Date(rs.getTimestamp("GENERATED_DATE").getTime()));
					}//end of if(rs.getTimestamp("CHEQUE_DATE") != null)

					if(rs.getTimestamp("DAILY_REMIT_DATE") != null){
						dailyTransferVO.setDailyTransferDate(new Date(rs.getTimestamp("DAILY_REMIT_DATE").getTime()));
					}//end of if(rs.getTimestamp("DAILY_REMIT_DATE") != null)

					dailyTransferVO.setChequeNo(TTKCommon.checkNull(rs.getString("CHEQUE_NUMBER")));
					dailyTransferVO.setFloatAccountNo(TTKCommon.checkNull(rs.getString("FLOAT_ACCT_NUMBER")));
					dailyTransferVO.setInsCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));

					alResultList.add(dailyTransferVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<Object>)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
					log.error("Error while closing the Resultset in TDSRemittanceDAOImpl getDailyTransferList()",sqlExp);
					throw new TTKException(sqlExp, strTDSRemittance);
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSRemittanceDAOImpl getDailyTransferList()",sqlExp);
						throw new TTKException(sqlExp, strTDSRemittance);
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSRemittanceDAOImpl getDailyTransferList()",sqlExp);
							throw new TTKException(sqlExp, strTDSRemittance);
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, strTDSRemittance);
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getDailyTransferList(ArrayList<Object> alSearchCriteria)

	/**
	 * This method sets the Daily Transfer information from the database.
	 * @param alDailyTransferList ArrayList object which contains sequence id for Finance flow to do the Daily Transfer.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @exception throws TTKException.
	 */
	public int setDailyTransfer(ArrayList<Object> alDailyTransferList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			if(alDailyTransferList != null && !alDailyTransferList.isEmpty()){
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSetDailyTransfer);
				cStmtObject.setString(1,(String)alDailyTransferList.get(0));
				cStmtObject.setTimestamp(2, new Timestamp(TTKCommon.getUtilDate(alDailyTransferList.get(1).toString()).getTime()));
				cStmtObject.setLong(3,(Long)alDailyTransferList.get(2));
				cStmtObject.registerOutParameter(4,Types.INTEGER);
				cStmtObject.execute();
				iResult = cStmtObject.getInt(4);
			}//end of if(alDailyTransferList != null && alDailyTransferList.size() > 0)

		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
        			log.error("Error while closing the Statement in TDSRemittanceDAOImpl setDailyTransfer()",sqlExp);
        			throw new TTKException(sqlExp, strTDSRemittance);
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TDSRemittanceDAOImpl setDailyTransfer()",sqlExp);
        				throw new TTKException(sqlExp, strTDSRemittance);
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, strTDSRemittance);
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in any case set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of setDailyTransfer(ArrayList<Object> alDailyTransferList)

	/**
	 * This method returns the ArrayList, which contains the MonthlyRemittanceVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of MonthlyRemittanceVO object's which contains the details of the Monthly Remittance
	 * @throws TTKException
	 */
	public ArrayList<Object> getMonthlyTransferList(ArrayList<Object> alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		MonthlyRemittanceVO monthlyRemittanceVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strMonthlyRemittanceList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(8);

			if(rs != null){
				while(rs.next()){
					monthlyRemittanceVO = new MonthlyRemittanceVO();

					if(rs.getString("TDS_REPORT_MASTER_SEQ_ID") != null){
						monthlyRemittanceVO.setMasterSeqID(new Long(rs.getLong("TDS_REPORT_MASTER_SEQ_ID")));
					}//end of if(rs.getString(TDS_REPORT_MASTER_SEQ_ID) != null)

					monthlyRemittanceVO.setInsCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					monthlyRemittanceVO.setYear(TTKCommon.checkNull(rs.getString("MONTHLY_REMITT_YEAR")));
					monthlyRemittanceVO.setMonth(TTKCommon.checkNull(rs.getString("MONTHLY_REMITT_MONTH")));
					monthlyRemittanceVO.setChallanrefNbr(TTKCommon.checkNull(rs.getString("CHALLAN_REF_NUMBER")));

					alResultList.add(monthlyRemittanceVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<Object>)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
					log.error("Error while closing the Resultset in TDSRemittanceDAOImpl getMonthlyTransferList()",sqlExp);
					throw new TTKException(sqlExp, strTDSRemittance);
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSRemittanceDAOImpl getMonthlyTransferList()",sqlExp);
						throw new TTKException(sqlExp, strTDSRemittance);
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSRemittanceDAOImpl getMonthlyTransferList()",sqlExp);
							throw new TTKException(sqlExp, strTDSRemittance);
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, strTDSRemittance);
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getMonthlyTransferList(ArrayList<Object> alSearchCriteria)

	/**
	 * This method sets the Monthly Remittance information from the database.
	 * @param monthlyRemittanceDetailVO MonthlyRemittanceDetailVO object which contains information for Monthly Remittance.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @exception throws TTKException.
	 */
	public int saveMonthlyRemittance(MonthlyRemittanceDetailVO monthlyRemittanceDetailVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult=0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMonthlyRemittance);

			cStmtObject.setString(1,monthlyRemittanceDetailVO.getInsID());
			cStmtObject.setString(2,monthlyRemittanceDetailVO.getYear());
			cStmtObject.setString(3, monthlyRemittanceDetailVO.getMonth());

			if(monthlyRemittanceDetailVO.getRemittanceDate() != null){
				cStmtObject.setTimestamp(4,new Timestamp(monthlyRemittanceDetailVO.getRemittanceDate().getTime()));
			}//end of if(monthlyRemittanceDetailVO.getRemittanceDate() != null)
			else {
				cStmtObject.setTimestamp(4,null);
			}//end of else

			if(monthlyRemittanceDetailVO.getChallanDate() != null){
				cStmtObject.setTimestamp(5,new Timestamp(monthlyRemittanceDetailVO.getChallanDate().getTime()));
			}//end of if(monthlyRemittanceDetailVO.getChallanDate() != null)
			else {
				cStmtObject.setTimestamp(5,null);
			}//end of else

			cStmtObject.setString(6,monthlyRemittanceDetailVO.getChallanrefNbr());
			cStmtObject.setString(7, monthlyRemittanceDetailVO.getBankTransactionNo());
			cStmtObject.setString(8, monthlyRemittanceDetailVO.getChallanTransVouNbr());
			cStmtObject.setString(9, monthlyRemittanceDetailVO.getBsrCode());
			cStmtObject.setLong(10, monthlyRemittanceDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(11,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(11);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
        			log.error("Error while closing the Statement in TDSRemittanceDAOImpl saveMonthlyRemittance()",sqlExp);
        			throw new TTKException(sqlExp, strTDSRemittance);
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TDSRemittanceDAOImpl saveMonthlyRemittance()",sqlExp);
        				throw new TTKException(sqlExp, strTDSRemittance);
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, strTDSRemittance);
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveMonthlyRemittance(MonthlyRemittanceVO monthlyRemittanceVO)

	/**
	 * This method returns the MonthlyRemittanceDetailVO which contains the Monthly Remittance information.
	 * @param lngRepMasterSeqID long value which contains Report Master sequence id to get the Monthly Remittance information
	 * @return MonthlyRemittanceDetailVO this contains the Monthly Remittance information
	 * @throws TTKException
	 */
	public MonthlyRemittanceDetailVO getMonthlyRemitDetail(long lngRepMasterSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		MonthlyRemittanceDetailVO monthlyRemittanceDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetMonthlyRemitDetail);
			cStmtObject.setLong(1,lngRepMasterSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					monthlyRemittanceDetailVO = new MonthlyRemittanceDetailVO();

					if(rs.getString("TDS_REPORT_MASTER_SEQ_ID") != null){
						monthlyRemittanceDetailVO.setMasterSeqID(new Long(rs.getString("TDS_REPORT_MASTER_SEQ_ID")));
					}//end of if(rs.getString("TDS_REPORT_MASTER_SEQ_ID" != null))

					monthlyRemittanceDetailVO.setInsID(TTKCommon.checkNull(rs.getString("INS_COMP_ID")));
					monthlyRemittanceDetailVO.setYear(TTKCommon.checkNull(rs.getString("MONTHLY_REMITT_YEAR")));
					monthlyRemittanceDetailVO.setMonth(Integer.valueOf(TTKCommon.checkNull(rs.getString("MONTHLY_REMITT_MONTH"))).toString());

					if(rs.getTimestamp("MONTHLY_REMIT_DATE")!= null){
						monthlyRemittanceDetailVO.setRemittanceDate(new Date(rs.getTimestamp("MONTHLY_REMIT_DATE").getTime()));
					}//end of if(rs.getTimestamp("MONTHLY_REMIT_DATE")!= null)

					monthlyRemittanceDetailVO.setChallanrefNbr(TTKCommon.checkNull(rs.getString("CHALLAN_REF_NUMBER")));

					if(rs.getTimestamp("CHALLAN_DATE")!= null){
						monthlyRemittanceDetailVO.setChallanDate(new Date(rs.getTimestamp("CHALLAN_DATE").getTime()));
					}//end of if(rs.getTimestamp("CHALLAN_DATE")!= null)

					monthlyRemittanceDetailVO.setChallanTransVouNbr(TTKCommon.checkNull(rs.getString("CHALLAN_TRANSFER_VOU_NUMBER")));
					monthlyRemittanceDetailVO.setBsrCode(TTKCommon.checkNull(rs.getString("BSR_CODE")));
					monthlyRemittanceDetailVO.setBankTransactionNo(TTKCommon.checkNull(rs.getString("CHALLAN_TRANSACTION_NUMBER")));
				}//end of while(rs.next())
			}//end of if(rs != null){
			return monthlyRemittanceDetailVO;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
					log.error("Error while closing the Resultset in TDSRemittanceDAOImpl getMonthlyRemitDetail()",sqlExp);
					throw new TTKException(sqlExp, strTDSRemittance);
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSRemittanceDAOImpl getMonthlyRemitDetail()",sqlExp);
						throw new TTKException(sqlExp, strTDSRemittance);
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSRemittanceDAOImpl getMonthlyRemitDetail()",sqlExp);
							throw new TTKException(sqlExp, strTDSRemittance);
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, strTDSRemittance);
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getMonthlyRemitDetail(long lngRepMasterSeqID)

	/**
	 * This method returns the ArrayList, which contains the AcknowledgmentInfoVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of AcknowledgmentInfoVO object's which contains the details of the Acknowledgement Information
	 * @throws TTKException
	 */
	public ArrayList<Object> getAckInfoList(ArrayList<Object> alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		AcknowledgmentInfoVO ackInfoVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAckInfoList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(8);

			if(rs != null){
				while(rs.next()){
					ackInfoVO = new AcknowledgmentInfoVO();

					if(rs.getString("TPA_TDS_ACKNOWLEDGE_SEQ_ID") != null){
						ackInfoVO.setAckSeqID(new Long(rs.getLong("TPA_TDS_ACKNOWLEDGE_SEQ_ID")));
					}//end of if(rs.getString(TPA_TDS_ACKNOWLEDGE_SEQ_ID) != null)

					ackInfoVO.setInsID(TTKCommon.checkNull(rs.getString("ABBREVATION_CODE")));
					ackInfoVO.setInsCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					ackInfoVO.setFinancialYear(TTKCommon.checkNull(rs.getString("V_FINANCIAL_YEAR")));
					ackInfoVO.setQuarter(TTKCommon.checkNull(rs.getString("QUARTER")));
					ackInfoVO.setAckNbr(TTKCommon.checkNull(rs.getString("ACKNOWLEDGEMENT_NUMBER")));

					if(rs.getTimestamp("ACKNOWLEDGEMENT_DATE") != null){
						ackInfoVO.setAckDate(new Date(rs.getTimestamp("ACKNOWLEDGEMENT_DATE").getTime()));
					}//end of if(rs.getTimestamp("ACKNOWLEDGEMENT_DATE") != null)

					alResultList.add(ackInfoVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<Object>)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
					log.error("Error while closing the Resultset in TDSRemittanceDAOImpl getAckInfoList()",sqlExp);
					throw new TTKException(sqlExp, strTDSRemittance);
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSRemittanceDAOImpl getAckInfoList()",sqlExp);
						throw new TTKException(sqlExp, strTDSRemittance);
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSRemittanceDAOImpl getAckInfoList()",sqlExp);
							throw new TTKException(sqlExp, strTDSRemittance);
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, strTDSRemittance);
			}//end of catch (TTKException exp)
			finally // Control will reach here in any case set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getAckInfoList(ArrayList<Object> alSearchCriteria)

	/**
	 * This method sets the Monthly Remittance information from the database.
	 * @param ackInfoVO AcknowledgmentInfoVO object which contains information for Acknowledgement Information.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @throws TTKException.
	 */
	public int saveAckInfo(AcknowledgmentInfoVO ackInfoVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult=0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveAckInfo);

			if(ackInfoVO.getAckSeqID() == null){
				cStmtObject.setString(1,null);
			}//end of if(rs.getString(TPA_TDS_ACKNOWLEDGE_SEQ_ID) != null)
			else{
				cStmtObject.setLong(1,ackInfoVO.getAckSeqID());
			}//end of else

			cStmtObject.setString(2, ackInfoVO.getInsID());
			cStmtObject.setString(3, ackInfoVO.getFinancialYear());
			cStmtObject.setString(4, ackInfoVO.getQuarterTypeID());
			cStmtObject.setString(5, ackInfoVO.getAckNbr());

			if(ackInfoVO.getAckDate() != null){
				cStmtObject.setTimestamp(6,new Timestamp(ackInfoVO.getAckDate().getTime()));
			}//end of if(ackInfoVO.getAckDate() != null)
			else {
				cStmtObject.setTimestamp(6,null);
			}//end of else

			cStmtObject.setLong(7, ackInfoVO.getUpdatedBy());
			cStmtObject.registerOutParameter(8,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(8);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
        			log.error("Error while closing the Statement in TDSRemittanceDAOImpl saveAckInfo()",sqlExp);
        			throw new TTKException(sqlExp, strTDSRemittance);
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TDSRemittanceDAOImpl saveAckInfo()",sqlExp);
        				throw new TTKException(sqlExp, strTDSRemittance);
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, strTDSRemittance);
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in any case set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveAckInfo(AcknowledgmentInfoVO ackInfoVO)

	/**
	 * This method returns the ArrayList, which contains the DailyTransferVO's which are populated from the database.
	 * @param alInclExcluList ArrayList which contains Search Criteria
	 * @return ArrayList of DailyTransferVO object's which contains the details of the DailyTransfer
	 * @throws TTKException
	 */
	public ArrayList<Object> getIncludExcludTdsClms(ArrayList<Object> alInclExcluList) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		DailyTransferVO dailyTransferVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelIncludExcludTdsClms);
			cStmtObject.setString(1,(String)alInclExcluList.get(0));
			cStmtObject.setString(2,(String)alInclExcluList.get(1));
			cStmtObject.setString(3,(String)alInclExcluList.get(2));
			cStmtObject.setString(4,(String)alInclExcluList.get(3));
			cStmtObject.setString(5,(String)alInclExcluList.get(4));
			cStmtObject.setString(6,(String)alInclExcluList.get(5));
			cStmtObject.setString(7,(String)alInclExcluList.get(6));
			cStmtObject.setString(8,(String)alInclExcluList.get(7));
			cStmtObject.setString(9,(String)alInclExcluList.get(8));
			cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);

			if(rs != null){
				while(rs.next()){
					dailyTransferVO = new DailyTransferVO();

					if(rs.getString("TDS_REPORT_MASTER_SEQ_ID") != null){
						dailyTransferVO.setMasterSeqID(new Long(rs.getLong("TDS_REPORT_MASTER_SEQ_ID")));
					}//end of if(rs.getString(TDS_REPORT_MASTER_SEQ_ID) != null)

					dailyTransferVO.setClaimSettleNo(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NUMBER")));
					dailyTransferVO.setChequeNo(TTKCommon.checkNull(rs.getString("CHEQUE_NUMBER")));
					if(rs.getTimestamp("GENERATED_DATE") != null){
						dailyTransferVO.setChequeIssDate(new Date(rs.getTimestamp("GENERATED_DATE").getTime()));
					}//end of if(rs.getTimestamp("GENERATED_DATE") != null)

					if(rs.getTimestamp("DAILY_REMIT_DATE") != null){
						dailyTransferVO.setDailyTransferDate(new Date(rs.getTimestamp("DAILY_REMIT_DATE").getTime()));
					}//end of if(rs.getTimestamp("DAILY_REMIT_DATE") != null)
					dailyTransferVO.setInsCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					dailyTransferVO.setFloatAccountNo(TTKCommon.checkNull(rs.getString("FLOAT_ACCT_NUMBER")));

					alResultList.add(dailyTransferVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<Object>)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
					log.error("Error while closing the Resultset in TDSRemittanceDAOImpl getIncludExcludTdsClms()",sqlExp);
					throw new TTKException(sqlExp, strTDSRemittance);
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSRemittanceDAOImpl getIncludExcludTdsClms()",sqlExp);
						throw new TTKException(sqlExp, strTDSRemittance);
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSRemittanceDAOImpl getIncludExcludTdsClms()",sqlExp);
							throw new TTKException(sqlExp, strTDSRemittance);
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, strTDSRemittance);
			}//end of catch (TTKException exp)
			finally // Control will reach here in any case set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getIncludExcludTdsClms(ArrayList<Object> alInclExcluList)

	/**
	 * This method sets the Daily Transfer information from the database.
	 * @param alPreDailyTranList ArrayList object which contains sequence id for Finance flow to do the Daily Transfer.
	 * @return integer value which returns greater than zero for successful execution of the task.
	 * @exception throws TTKException.
	 */
	public int setInclExclTdsClaims(ArrayList<Object> alPreDailyTranList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			if(alPreDailyTranList != null && !alPreDailyTranList.isEmpty()){
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInclExclTdsClaims);
				cStmtObject.setString(1,(String)alPreDailyTranList.get(0));
				cStmtObject.setString(2,(String)alPreDailyTranList.get(1));
				cStmtObject.setLong(3,(Long)alPreDailyTranList.get(2));
				cStmtObject.registerOutParameter(4,Types.INTEGER);
				cStmtObject.execute();
				iResult = cStmtObject.getInt(4);
			}//end of if(alDailyTransferList != null && alDailyTransferList.size() > 0)
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
        			log.error("Error while closing the Statement in TDSRemittanceDAOImpl setInclExclTdsClaims()",sqlExp);
        			throw new TTKException(sqlExp, strTDSRemittance);
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TDSRemittanceDAOImpl setInclExclTdsClaims()",sqlExp);
        				throw new TTKException(sqlExp, strTDSRemittance);
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, strTDSRemittance);
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in any case set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of setInclExclTdsClaims(ArrayList<Object> alDailyTransferList)

	/**
	 * This method returns the ArrayList, which contains the DailyTransferVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of DailyTransferVO object's which contains the details of the DailyTransfer
	 * @throws TTKException
	 */
	public ArrayList<Object> selectFinYearList(ArrayList<Object> alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CertificateVO certificateVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectFinancialYear);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(5);
			if(rs != null){
				while(rs.next()){
					certificateVO = new CertificateVO();
					certificateVO.setBatchSeqID(new Long(rs.getLong("TDS_BATCH_SEQ_ID")));
					certificateVO.setFinanceYear(TTKCommon.checkNull(rs.getString("FINANCIAL_YEAR")));
					certificateVO.setProcessedYN(TTKCommon.checkNull(rs.getString("DOC_GENERATED_YN")));
					certificateVO.setTdsBatchQuarter(TTKCommon.checkNull(rs.getString("TDS_BATCH_QTR_GEN_TYPE_ID")));
					certificateVO.setTdsBatchQtrDesc(TTKCommon.checkNull(rs.getString("QUARTER_DESC")));
					alResultList.add(certificateVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<Object>)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
					log.error("Error while closing the Resultset in TDSRemittanceDAOImpl selectFinYearList()",sqlExp);
					throw new TTKException(sqlExp, strTDSRemittance);
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSRemittanceDAOImpl selectFinYearList()",sqlExp);
						throw new TTKException(sqlExp, strTDSRemittance);
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSRemittanceDAOImpl selectFinYearList()",sqlExp);
							throw new TTKException(sqlExp, strTDSRemittance);
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, strTDSRemittance);
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of selectFinYearList(ArrayList<Object> alSearchCriteria)

	/**
	 * This method returns the ArrayList, which contains the CertificateVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return integer value which returns greater than zero for successful execution of the task.
	 * @throws TTKException
	 */
	public int generateTdsBatch(ArrayList<Object> alSearchCriteria) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult=0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateTdsBatch);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			if(alSearchCriteria.get(2) !=null){
				cStmtObject.setLong(3,(Long)alSearchCriteria.get(2));
			}//end of if(alSearchCriteria.get(2) !=null)
			else{
				cStmtObject.setString(3, null);
			}//end of else
			cStmtObject.setLong(4,(Long)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(7,OracleTypes.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
        			log.error("Error while closing the Statement in TDSRemittanceDAOImpl generateTdsBatch()",sqlExp);
        			throw new TTKException(sqlExp, strTDSRemittance);
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TDSRemittanceDAOImpl generateTdsBatch()",sqlExp);
        				throw new TTKException(sqlExp, strTDSRemittance);
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, strTDSRemittance);
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in any case set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of selectFinancialYear(ArrayList<Object> alSearchCriteria)

	/**
	 * This method returns the ArrayList, which contains the TDSHospitalInfoVO's which are populated from the database.
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of TDSHospitalInfoVO object's which contains the details of the tdsDeductedHospitals Information
	 * @throws TTKException
	 */
	public ArrayList<Object> getTdsDeductedHospList(ArrayList<Object> alSearchCriteria) throws TTKException{

		 Collection<Object> alResultList = new ArrayList<Object>();
		 CallableStatement cStmtObject=null;
		 Connection conn = null;
		 ResultSet rs = null;
		 TDSHospitalInfoVO tdsHospitalInfo = null;
		 try{
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTdsDeductedHospitals);
				cStmtObject.setString(1,(String)alSearchCriteria.get(0));
				cStmtObject.setString(2,(String)alSearchCriteria.get(1));
				cStmtObject.setString(3,(String)alSearchCriteria.get(2));
				cStmtObject.setString(4,(String)alSearchCriteria.get(3));
				cStmtObject.setString(5,(String)alSearchCriteria.get(4));
				cStmtObject.setString(6,(String)alSearchCriteria.get(5));
				cStmtObject.setString(7,(String)alSearchCriteria.get(6));
				cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(8);
				if(rs != null){
					while(rs.next()){
						tdsHospitalInfo = new TDSHospitalInfoVO();
						tdsHospitalInfo.setEmplNumber(TTKCommon.checkNull(rs.getString("empanel_number")));
						tdsHospitalInfo.setHospitalName(TTKCommon.checkNull(rs.getString("hosp_name")));
						tdsHospitalInfo.setTtkBranch(TTKCommon.checkNull(rs.getString("Office_Name")));
						alResultList.add(tdsHospitalInfo);
					}//end of while(rs.next())
				}//end of if(rs != null)
				return (ArrayList<Object>)alResultList;
			}// end of try
		 catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, strTDSRemittance);
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, strTDSRemittance);
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
						log.error("Error while closing the Resultset in TDSRemittanceDAOImpl getTdsDeductedHosp()",sqlExp);
						throw new TTKException(sqlExp, strTDSRemittance);
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null)	cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in TDSRemittanceDAOImpl getTdsDeductedHosp()",sqlExp);
							throw new TTKException(sqlExp, strTDSRemittance);
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in TDSRemittanceDAOImpl getTdsDeductedHosp()",sqlExp);
								throw new TTKException(sqlExp, strTDSRemittance);
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, strTDSRemittance);
				}//end of catch (TTKException exp)
				finally // Control will reach here in any case set null to the objects
				{
					rs = null;
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
	}//end of getTdsDeductedHospList(ArrayList<Object> alSearchCriteria)


	/**
	 * This Method is used for certificate generation individually
	 * @param alGenerateList An arraylist Which Contains details of sequence id's which has to be Generated
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @throws TTKException
	 */
	public int generateTdsBatchInd(ArrayList<Object> alGenerateList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateTdsBatchIndv);
			cStmtObject.setString(1,(String)alGenerateList.get(0));
			cStmtObject.setString(2, (String)alGenerateList.get(1));
			cStmtObject.setString(3, (String)alGenerateList.get(2));
			cStmtObject.setLong(4, (Long)alGenerateList.get(3));
			cStmtObject.setString(5, (String)alGenerateList.get(4));
			cStmtObject.setString(6, (String)alGenerateList.get(5));
			cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
		 iResult=cStmtObject.getInt(7);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
        			log.error("Error while closing the Statement in TDSRemittanceDAOImpl generateTdsBatchInd()",sqlExp);
        			throw new TTKException(sqlExp, strTDSRemittance);
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TDSRemittanceDAOImpl generateTdsBatchInd()",sqlExp);
        				throw new TTKException(sqlExp, strTDSRemittance);
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp,strTDSRemittance);
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//End of generateTdsBatchInd(ArrayList alGenerateList)

	/**
	 * This method returns the ArrayList, which contains the list of Tds certificate generate schedule records.
	 * @return ArrayList of CertificateVO object's which contains the details of the GenTdsCertSchedList Information
	 * @throws TTKException
	 */
	public ArrayList<CertificateVO> getGenTdsCertSchedList() throws TTKException
	{
		Collection<CertificateVO> alResultList = new ArrayList<CertificateVO>();
		CallableStatement cStmtObject=null;
		Connection conn = null;
		ResultSet rs = null;
		CertificateVO certificateVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenTdsCertScheduler);
			cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);
			if(rs != null){
				while(rs.next()){
					certificateVO = new CertificateVO();
					certificateVO.setHospitalID(TTKCommon.checkNull(rs.getString("empanel_number")));
					certificateVO.setFinanceYear(TTKCommon.checkNull(rs.getString("financial_year")));
					certificateVO.setBatchSeqID((rs.getLong("tds_batch_seq_id")));
					certificateVO.setTdsBatchQtrDesc(TTKCommon.checkNull(rs.getString("qtr_desc")));
					certificateVO.setTdsBatchQuarter(TTKCommon.checkNull(rs.getString("qtr_gen_type_id")));
					alResultList.add(certificateVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<CertificateVO>)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
					log.error("Error while closing the Resultset in TDSRemittanceDAOImpl getGenTdsCertSchedList()",sqlExp);
					throw new TTKException(sqlExp, strTDSRemittance);
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSRemittanceDAOImpl getGenTdsCertSchedList()",sqlExp);
						throw new TTKException(sqlExp, strTDSRemittance);
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSRemittanceDAOImpl getGenTdsCertSchedList()",sqlExp);
							throw new TTKException(sqlExp, strTDSRemittance);
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, strTDSRemittance);
			}//end of catch (TTKException exp)
			finally // Control will reach here in any case set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getGenTdsCertSchedList()
	
	/**
	 * This method returns the ArrayList, which contains the list of Individual Tds certificate generate schedule records.
	 * @return ArrayList of CertificateVO object's which contains the details of the GenTdsCertSchedList Information
	 * @throws TTKException
	 */
	public ArrayList<CertificateVO> getGenTdsCertIndSchedList() throws TTKException
	{
		Collection<CertificateVO> alResultList = new ArrayList<CertificateVO>();
		CallableStatement cStmtObject=null;
		Connection conn = null;
		ResultSet rs = null;
		CertificateVO certificateVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenTdsCertIndScheduler);
			cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);
			if(rs != null){
				while(rs.next()){
					certificateVO = new CertificateVO();
					certificateVO.setHospitalID(TTKCommon.checkNull(rs.getString("empanel_number")));
					certificateVO.setFinanceYear(TTKCommon.checkNull(rs.getString("financial_year")));
					certificateVO.setBatchSeqID((rs.getLong("tds_batch_seq_id")));
					certificateVO.setTdsBatchQuarter(TTKCommon.checkNull(rs.getString("qtr_gen_type_id")));
					certificateVO.setTdsBatchQtrDesc(TTKCommon.checkNull(rs.getString("qtr_desc")));
					alResultList.add(certificateVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<CertificateVO>)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
					log.error("Error while closing the Resultset in TDSRemittanceDAOImpl getGenTdsCertIndSchedList()",sqlExp);
					throw new TTKException(sqlExp, strTDSRemittance);
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in TDSRemittanceDAOImpl getGenTdsCertIndSchedList()",sqlExp);
						throw new TTKException(sqlExp, strTDSRemittance);
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in TDSRemittanceDAOImpl getGenTdsCertIndSchedList()",sqlExp);
							throw new TTKException(sqlExp, strTDSRemittance);
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, strTDSRemittance);
			}//end of catch (TTKException exp)
			finally // Control will reach here in any case set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getGenTdsCertIndSchedList()
	
	/**
	 * This method update the Hospital Info doc generated field.
	 * @param lngBatchSeqID Long object which contains TDS Batch Seq ID.
	 * @throws TTKException.
	 */
	public void saveDocGenInfo(Long lngBatchSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDocGenInfo);

			if(lngBatchSeqID != null){
				cStmtObject.setLong(1,lngBatchSeqID);
			}//end of if(lngBatchSeqID != null)
			cStmtObject.execute();			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, strTDSRemittance);
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, strTDSRemittance);
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
        			log.error("Error while closing the Statement in TDSRemittanceDAOImpl saveAckInfo()",sqlExp);
        			throw new TTKException(sqlExp, strTDSRemittance);
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in TDSRemittanceDAOImpl saveAckInfo()",sqlExp);
        				throw new TTKException(sqlExp, strTDSRemittance);
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, strTDSRemittance);
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in any case set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally		
	}//end of saveDocGenInfo(AcknowledgmentInfoVO ackInfoVO)
	
}//end of TDSRemittanceDAOImpl
