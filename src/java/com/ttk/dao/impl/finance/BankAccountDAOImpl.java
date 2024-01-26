/**
 * @ (#) BankAccountDAOImpl.java Jun 9, 2006
 * Project : TTK HealthCare Services
 * File : BankAccountDAOImpl.java
 * Author : Srikanth H M
 * Company : Span Systems Corporation
 * Date Created : Jun 9, 2006
 *
 * @author : Srikanth H M
 * Modified by :
 * Modified date :
 * Reason :
 */

package com.ttk.dao.impl.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.finance.AuthorisedVO;
import com.ttk.dto.finance.BankAccountDetailVO;
import com.ttk.dto.finance.BankAccountVO;
import com.ttk.dto.finance.BankGuaranteeVO;
import com.ttk.dto.finance.BankVO;
import com.ttk.dto.finance.ChequeVO;
import com.ttk.dto.finance.TransactionVO;

public class BankAccountDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(BankAccountDAOImpl.class);
	
	private static final String strBankList = "{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_BANK_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strBankAccountList = "{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_BANK_ACC_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strBankGuaranteeList = "{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_GUARANTEE_LIST(?,?,?,?,?,?,?)}";
	private static final String strBankAccountTranList="{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_BANK_ACC_TRAN_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strBankAcctChequeList="{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_CHQ_SERIES_LIST(?,?,?,?,?,?,?)}";
	private static final String strBankAcct="{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_BANK_ACC_DETAIL(?,?,?)}";
	private static final String strBankAccountTranDetail="{CALL fin_app.BANK_ACCOUNTS_PKG_BANK_ACC_TRN_DETAIL(?,?,?,?)}";
	private static final String strBankGuaranteeDetail="{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_GUARANTEE_DETAIL(?,?,?)}";
	private static final String strBankAcctSave="{CALL fin_app.BANK_ACCOUNTS_PKG_BANK_ACC_SAVE(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strBankAccountTranSave="{CALL fin_app.BANK_ACCOUNTS_PKG_BANK_ACC_TRN_SAVE(?,?,?,?,?,?,?,?,?)}";
	private static final String strBankGuaranteeSave="{CALL fin_app.BANK_ACCOUNTS_PKG_SAVE_GUARANTEE_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strBankAcctDelete="{CALL fin_app.BANK_ACCOUNTS_PKG_BANK_ACC_DELETE(?,?,?)}";
	private static final String strBankGuarDelete="{CALL fin_app.BANK_ACCOUNTS_PKG_DELETE_BANK_GUARANTEE_DETAILS(?,?,?)}";
	private static final String strChequeSeriesSave="{CALL fin_app.BANK_ACCOUNTS_PKG_SAVE_CHEQUE_SERIES(?,?,?,?,?,?)}";
	private static final String strChequeSeriesDelete="{CALL fin_app.BANK_ACCOUNTS_PKG_CHQ_SERIES_DELETE(?,?,?,?)}";
	private static final String strSignatorieDetail="{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_AUTH_SIGN_DETAIL(?,?,?,?)}";
	private static final String strSaveSignatory = "{CALL fin_app.BANK_ACCOUNTS_PKG_AUTH_USER_SAVE(?,?,?,?,?,?,?,?)}";
	private static final String strReverseTransaction = "{CALL fin_app.BANK_ACCOUNTS_PKG_BANK_ACC_TRAN_REVERSE(?,?,?,?)}";
	
	private static final String strBankGuaranteeHistoryDetail = "{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_GUARANTEE_HIS_DETAIL(?,?,?)}";

	private static final int BANK_ACC_SEQ_ID = 1;
	private static final int ACCOUNT_NUMBER = 2;
	private static final int ACCOUNT_STATUS = 3;
	private static final int ACCOUNT_NAME  = 4;
	private static final int TPA_OFFICE_SEQ_ID = 5;
	private static final int BANK_SEQ_ID = 6;
	private static final int COMMENTS=7;
	private static final int ENDDATE=8;
	private static final int TDS_BANK_ACCOUNT_YN=9;
	private static final int ADDED_BY=10;
	private static final int ACCOPENDATE=11;
	//private static final int ACCCLOSEDATE=12;
	private static final int ROW_PROCESSED=12;

	/**
	 * This method returns the ArrayList, which contains the BankVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of BankVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
	public ArrayList getBankList(ArrayList alSearchCriteria) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankVO bankVO =null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(4));
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));
			cStmtObject.setLong(6,Long.parseLong((String)alSearchCriteria.get(6).toString()));
			cStmtObject.setLong(7,Long.parseLong((String)alSearchCriteria.get(7).toString()));
			cStmtObject.setLong(8,(Long)alSearchCriteria.get(3));
			cStmtObject.registerOutParameter(1,Types.VARCHAR);
			cStmtObject.registerOutParameter(9,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);

			if(rs != null){
				while(rs.next()){
					bankVO= new BankVO();

					if(rs.getString("BANK_SEQ_ID")!=null)
					{
						bankVO.setBankSeqID(new Long(rs.getString("BANK_SEQ_ID")));
					}//end of if(rs.getString("")!=null)

					bankVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));
					bankVO.setOfficeTypeDesc(TTKCommon.checkNull(rs.getString("OFFICE_TYPE_DESC")));
					bankVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY")));
					alResultList.add(bankVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		 {
		 throw new TTKException(sqlExp, "bank");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bank");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getBankList()",sqlExp);
					throw new TTKException(sqlExp, "bank");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getBankList()",sqlExp);
						throw new TTKException(sqlExp, "bank");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getBankList()",sqlExp);
							throw new TTKException(sqlExp, "bank");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bank");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the ArrayList, which contains the BankAccountVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BankAccountVO'S object's which contains the details of the Bank Account
	 * @exception throws TTKException
	 */
	public ArrayList getBankAccountList(ArrayList alSearchCriteria)throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		BankAccountVO bankAccountVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAccountList);//add the procedure call
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			if(alSearchCriteria.get(5)!=null)
			{
				cStmtObject.setLong(6,(Long)alSearchCriteria.get(5));
			}//end of if(alSearchCriteria.get(5)!=null)
			else
			{
				cStmtObject.setLong(6,0);
			}//end of if(alSearchCriteria.get(5)!=null)
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setLong(9,Long.parseLong((String)alSearchCriteria.get(9).toString()));
			cStmtObject.setLong(10,Long.parseLong((String)alSearchCriteria.get(10).toString()));
			cStmtObject.setLong(11,(Long)alSearchCriteria.get(6));
			cStmtObject.registerOutParameter(12,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(12);

			if(rs != null)
			{
				while (rs.next())
				{
					bankAccountVO = new BankAccountVO();
					if(rs.getString("BANK_ACC_SEQ_ID") != null)
					{
						bankAccountVO.setAccountSeqID(new Long(rs.getLong("BANK_ACC_SEQ_ID")));//account seq id
					}//end of if(rs.getString("BANK_ACC_SEQ_ID") != null)
					bankAccountVO.setAccountNO(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));//acount no
					bankAccountVO.setAccountName(TTKCommon.checkNull(rs.getString("ACCOUNT_NAME")));//account name
					bankAccountVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));//Bank name
					bankAccountVO.setTtkBranch(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));//Bank name
					bankAccountVO.setOfficeTypeDesc(TTKCommon.checkNull(rs.getString("OFFICE_TYPE_DESC")));//Office Type
					bankAccountVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));//Status
					alResultList.add(bankAccountVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getBankAccountList()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getBankAccountList()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getBankAccountList()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankAccountList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ArrayList, which contains the TransactionVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of TransactionVO'S object's which contains the details of the Bank Account Transaction
	 * @exception throws TTKException
	 */

	public ArrayList getTransactionList(ArrayList alSearchCriteria) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		TransactionVO transactionVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAccountTranList);//add the procedure call
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));//v_bank_acc_seq_id
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));//v_bank_trn_number
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));//v_trn_type
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));//v_from_date
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));//v_to_date
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));//v_sort_var
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));//v_sort_order
			cStmtObject.setLong(8,Long.parseLong((String) alSearchCriteria.get(8)));//v_start_num
			cStmtObject.setLong(9,Long.parseLong((String)alSearchCriteria.get(9)));//v_end_num
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(5));//v_added_by

			cStmtObject.registerOutParameter(11,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			if(rs != null)
			{
				while (rs.next())
				{
					transactionVO = new TransactionVO();
					if(rs.getString("BANK_TRN_SEQ_ID") != null)
					{
						transactionVO.setTransSeqID(new Long(rs.getLong("BANK_TRN_SEQ_ID")));
					}//end of if(rs.getString("BANK_TRN_SEQ_ID") != null)
					transactionVO.setTransNbr(TTKCommon.checkNull(rs.getString("BANK_TRN_NUMBER")));
					transactionVO.setTransTypeDesc(TTKCommon.checkNull(rs.getString("TYPE")));
					transactionVO.setTransDate(rs.getString("TRN_DATE")!=null ? new Date(rs.getTimestamp("TRN_DATE").getTime()):null);
					if(rs.getString("TRN_AMOUNT") != null){
						transactionVO.setTransAmt(new BigDecimal(rs.getString("TRN_AMOUNT")));
					}//end of if(rs.getString("TRN_AMOUNT") != null)

					if(rs.getString("REVERSE_YN").equals("Y")){
						transactionVO.setImageName("FreshIcon");
						transactionVO.setImageTitle("Reverse Entry");
					}//end of if(rs.getString("REVERSE_YN").equals("Y"))

					if(rs.getString("bank_balance") != null){
						transactionVO.setFloatBalance(new BigDecimal(rs.getString("bank_balance")));
					}//end of if(rs.getString("CURRENT_BALANCE") != null)
					alResultList.add(transactionVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getTransactionList()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getTransactionList()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getTransactionList()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getTransactionList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ChequeVO'S object's which contains the details of the Cheque Series
	 * @exception throws TTKException
	 */

	public ArrayList getChequeSeriesList(ArrayList alSearchCriteria) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		ChequeVO chequeVO=null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strBankAcctChequeList);
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(2));
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));
			cStmtObject.setLong(4,Long.parseLong((String) alSearchCriteria.get(4)));
			cStmtObject.setLong(5,Long.parseLong((String) alSearchCriteria.get(5)));
			cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.registerOutParameter(7,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			if(rs != null)
			{
				while (rs.next())
				{
					chequeVO = new ChequeVO();
					if(rs.getString("BANK_ACC_SEQ_ID") != null)
					{
						chequeVO.setAccountSeqID(new Long(rs.getLong("BANK_ACC_SEQ_ID")));//trans seq id
					}//end of if(rs.getString("BANK_ACC_SEQ_ID") != null)
					if(rs.getString("CHECK_SEQ_ID") != null)
					{
						chequeVO.setSeqID(new Long(rs.getLong("CHECK_SEQ_ID")));//trans seq id
					}//end of if(rs.getString("CHECK_SEQ_ID") != null)
					chequeVO.setStartNo(TTKCommon.checkNull(rs.getString("CHK_START_NUM")));
					chequeVO.setEndNo(TTKCommon.checkNull(rs.getString("CHK_END_NUM")));
					alResultList.add(chequeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getChequeSeriesList()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getChequeSeriesList()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getChequeSeriesList()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getChequeSeriesList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ArrayList, which contains the BankGuaranteeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BankGuaranteeVO'S object's which contains the details of the Bank Guarantee
	 * @exception throws TTKException
	 */

	public ArrayList getBankGuaranteeList(ArrayList alSearchCriteria) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		BankGuaranteeVO bankGuaranteeVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try
		{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strBankGuaranteeList);//add the procedure call
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));//v_bank_acc_seq_id
			cStmtObject.setString(2,(String)alSearchCriteria.get(2));//v_sort_var
			cStmtObject.setString(3,(String)alSearchCriteria.get(3));//v_sort_order
			cStmtObject.setLong(4,Long.parseLong((String) alSearchCriteria.get(4)));//v_start_num
			cStmtObject.setLong(5,Long.parseLong((String) alSearchCriteria.get(5)));//v_end_num
			cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));//v_added_by
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.registerOutParameter(7,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);

			if(rs != null)
			{
				while (rs.next())
				{
					bankGuaranteeVO = new BankGuaranteeVO();
					if(rs.getString("BANK_GUARANTEE_SEQ_ID") != null)
					{
						bankGuaranteeVO.setGuaranteeSeqID(new Long(rs.getLong("BANK_GUARANTEE_SEQ_ID")));
					}//end of if(rs.getString("BANK_TRN_SEQ_ID") != null)

					if(rs.getString("BANK_GUARANTEED") != null){
						bankGuaranteeVO.setBanGuarAmt(new BigDecimal(rs.getString("BANK_GUARANTEED")));
					}//end of if(rs.getString("TRN_AMOUNT") != null)
					bankGuaranteeVO.setBankerName(TTKCommon.checkNull(rs.getString("BANKER_NAME")));
					bankGuaranteeVO.setFromDate(rs.getString("BANKER_FROM_DATE")!=null ? new Date(rs.getTimestamp("BANKER_FROM_DATE").getTime()):null);
					bankGuaranteeVO.setToDate(rs.getString("BANKER_TO_DATE")!=null ? new Date(rs.getTimestamp("BANKER_TO_DATE").getTime()):null);
					bankGuaranteeVO.setBgType(TTKCommon.checkNull(rs.getString("BANK_GUARANTEE_TYPE")));
					bankGuaranteeVO.setBgStatus(TTKCommon.checkNull(rs.getString("BANK_GUARANTEE_STATUS")));
					alResultList.add(bankGuaranteeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getBankGuaranteeList()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getBankGuaranteeList()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getBankGuaranteeList()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankGuaranteeList(ArrayList alSearchCriteria)

	/**
	 * This method returns the BankAccountDetailVO, which contains all the bank details
	 * @param lngBankAccSeqID  which contains seq id of Bank get the Bank Details
	 * @return BankAccountDetailVO object which contains the Bank details
	 * @exception throws TTKException
	 */

	public BankAccountDetailVO getBankAccountdetail(long lngBankSeqID,long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankAccountDetailVO bankAccountDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAcct);
			cStmtObject.setLong(1,lngBankSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				bankAccountDetailVO = new BankAccountDetailVO();
				while (rs.next()) {
					bankAccountDetailVO.setAccountSeqID(rs.getString("BANK_ACC_SEQ_ID")!=null ? new Long(rs.getString("BANK_ACC_SEQ_ID")):null);
					bankAccountDetailVO.setAccountName(TTKCommon.checkNull(rs.getString("ACCOUNT_NAME")));
					bankAccountDetailVO.setAccountNO(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));
					bankAccountDetailVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("ACCOUNT_STATUS")));
					bankAccountDetailVO.setAccountType(TTKCommon.checkNull(rs.getString("ACCOUNT_TYPE")));
					bankAccountDetailVO.setTransactionYN(TTKCommon.checkNull(rs.getString("V_RESULT")));
					bankAccountDetailVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getString("TPA_OFFICE_SEQ_ID")):null);	
					bankAccountDetailVO.setTdsPurposeYN(TTKCommon.checkNull(rs.getString("TDS_BANK_ACCOUNT_YN")));
					bankAccountDetailVO.setCreatedDate(rs.getString("ACC_START_DATE")!=null ? new Date(rs.getTimestamp("ACC_START_DATE").getTime()):null);
					bankAccountDetailVO.setClosedDate(rs.getString("acc_end_date")!=null ? new Date(rs.getTimestamp("acc_end_date").getTime()):null);
					bankAccountDetailVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));
					bankAccountDetailVO.setBankSeqID(rs.getString("BANK_SEQ_ID")!=null ? new Long(rs.getString("BANK_SEQ_ID")):null);
					bankAccountDetailVO.setOfficeTypeDesc(TTKCommon.checkNull(rs.getString("OFFICE_TYPE")));
					bankAccountDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("COMMENTS")));
					bankAccountDetailVO.setAccOpenDate(rs.getString("ACC_OPEN_DATE")!=null ? new Date(rs.getTimestamp("ACC_OPEN_DATE").getTime()):null);
					//bankAccountDetailVO.setAccCloseDate(rs.getString("ACC_CLOSED_DATE")!=null ? new Date(rs.getTimestamp("ACC_CLOSED_DATE").getTime()):null);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return bankAccountDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getBankAccountdetail()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getBankAccountdetail()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getBankAccountdetail()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankAccountdetail(long lngBankSeqID,long lngUserSeqID)

	/**
	 * This method saves the Bank Account information
	 * @param bankAccountDetailVO the object which contains the Bank details which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public long saveBankAccount(BankAccountDetailVO bankAccountDetailVO) throws TTKException
	{
		long lngBankSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAcctSave);
			if(bankAccountDetailVO.getAccountSeqID() != null){
				cStmtObject.setLong(BANK_ACC_SEQ_ID,bankAccountDetailVO.getAccountSeqID().longValue());
			}//end of if(bankAccountDetailVO.getAccountSeqId() != null)
			else{
				cStmtObject.setLong(BANK_ACC_SEQ_ID,0);
			}//end of else
			cStmtObject.setString(ACCOUNT_NUMBER,bankAccountDetailVO.getAccountNO());
			cStmtObject.setString(ACCOUNT_STATUS,bankAccountDetailVO.getStatusTypeID());
			cStmtObject.setString(ACCOUNT_NAME,bankAccountDetailVO.getAccountName());
			if(bankAccountDetailVO.getOfficeSeqID() != null){
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,bankAccountDetailVO.getOfficeSeqID().longValue());
			}//end of if(if(bankAccountDetailVO.getTtkBranch() != null)
			else{
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,0);
			}//end of else

			if(bankAccountDetailVO.getBankSeqID() != null){
				cStmtObject.setLong(BANK_SEQ_ID,bankAccountDetailVO.getBankSeqID().longValue());
			}//end of if(bankAccountDetailVO.getBankSeqID() != null)
			else{
				cStmtObject.setLong(BANK_SEQ_ID,0);
			}//end of else
			cStmtObject.setString(COMMENTS,bankAccountDetailVO.getRemarks());
			if(bankAccountDetailVO.getClosedDate() != null)
			{
				cStmtObject.setTimestamp(ENDDATE,new Timestamp(bankAccountDetailVO.getClosedDate().getTime()));
			}
			else
			{
				cStmtObject.setTimestamp(ENDDATE, null);
			}
			cStmtObject.setString(TDS_BANK_ACCOUNT_YN,bankAccountDetailVO.getTdsPurposeYN());
			cStmtObject.setLong(ADDED_BY,bankAccountDetailVO.getUpdatedBy().longValue());
			
			cStmtObject.setTimestamp(ACCOPENDATE,new Timestamp(bankAccountDetailVO.getAccOpenDate().getTime()));
			/*
			 * CLOSED DATE COLUMN IS ALREADY THERE IN DB AND APPLICATION
			 * if(bankAccountDetailVO.getAccCloseDate() != null)
			{
				cStmtObject.setTimestamp(ACCCLOSEDATE,new Timestamp(bankAccountDetailVO.getAccCloseDate().getTime()));
			}else
			{
				cStmtObject.setTimestamp(ACCCLOSEDATE, null);
			}*/
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(BANK_ACC_SEQ_ID,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.execute();
			lngBankSeqID = cStmtObject.getLong(BANK_ACC_SEQ_ID);
            conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl saveBankAccount()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl saveBankAccount()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngBankSeqID;
	}//end of saveBankAccount(BankAccountDetailVO bankAccountDetailVO)

	/**
	 * This method deletes the bank information from the database
	 * @param alBankAccount arraylist which contains the details of bank
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public int deleteBankAccount(ArrayList alBankAccount)  throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAcctDelete);
			cStmtObject.setString(1, (String)alBankAccount.get(0));//CONCATENATED STRING OF ENROL_BATCH_SEQ_IDS
			cStmtObject.setLong(2,(Long)alBankAccount.get(1));//ADDED_BY
			cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl deleteBankAccount()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl deleteBankAccount()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteBankAccount(ArrayList alBankAccount)

	/**
	 * This method deletes the Bank Guarantee Series information from the database
	 * @param alDeleteList arraylist which contains the details of Bank Guarantee
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public int deleteBankGuarantee(ArrayList alBankGuarantee)  throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strBankGuarDelete);
			cStmtObject.setString(1, (String)alBankGuarantee.get(0));//CONCATENATED STRING OF BANK_GUARANTEE_SEQ_ID
			cStmtObject.setLong(2,(Long)alBankGuarantee.get(1));//ADDED_BY
			cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl deleteBankGuarantee()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl deleteBankGuarantee()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteBankGuarantee(ArrayList alBankGuarantee)

	/**
	 * This method returns the AuthorisedVO, which contains the Authorised Signatories details
	 * @param lngAuthSeqID  which contains seq id of Authorised Signatories
	 * @param lngBank_acc_seq_id  which Bank_acc_seq_id seq id of Authorised Signatories
	 * @param lngUserSeqID  which contains UserSeqID of Authorised Signatories
	 * @return AuthorisedVO object which contains the Authorised Signatories details
	 * @exception throws TTKException
	 */
	public AuthorisedVO getSignatorieDetail(long lngAuth_seq_id,long lngBank_acc_seq_id,long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		AuthorisedVO authorisedVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strSignatorieDetail);
			cStmtObject.setLong(1,lngAuth_seq_id);
			cStmtObject.setLong(2,lngBank_acc_seq_id);
			cStmtObject.setLong(3,lngUserSeqID);
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.registerOutParameter(4,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				authorisedVO = new AuthorisedVO();
				while (rs.next()) {

					authorisedVO.setAuthSeqID(rs.getString("AUTH_SEQ_ID")!=null ? new Long(rs.getString("AUTH_SEQ_ID")):null);
					authorisedVO.setContactSeqId(rs.getString("CONTACT_SEQ_ID")!=null ? new Long(rs.getString("CONTACT_SEQ_ID")):null);
					authorisedVO.setBankAcctSeqID(rs.getString("BANK_ACC_SEQ_ID")!=null ? new Long(rs.getString("BANK_ACC_SEQ_ID")):null);
					authorisedVO.setUserName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					authorisedVO.setRoleName(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
					authorisedVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
					authorisedVO.setOfficeCode(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					authorisedVO.setFromDate(rs.getString("AUTH_FROM_DATE")!=null ? new Date(rs.getTimestamp("AUTH_FROM_DATE").getTime()):null);
					authorisedVO.setToDate(rs.getString("AUTH_TO_DATE")!=null ? new Date(rs.getTimestamp("AUTH_TO_DATE").getTime()):null);
					authorisedVO.setTransLimit(rs.getString("TRANS_LIMIT")!=null? new Double(rs.getDouble("TRANS_LIMIT")):null);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return authorisedVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getSignatorieDetail()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getSignatorieDetail()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getSignatorieDetail()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getSignatorieDetail(long lngAuth_seq_id,long lngBank_acc_seq_id,long lngUserSeqID)

	/**
	 * This method updates the Authorised Signatory information
	 * @param authorisedVO the AuthorisedVO which contains the information to be added or updated
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @throws TTKException
	 */
	public int saveSignatories(AuthorisedVO authorisedVO) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveSignatory);
			if(authorisedVO.getAuthSeqID() != null){
				cStmtObject.setLong(1,authorisedVO.getAuthSeqID().longValue());
			}//end of if(authorisedVO.getAuthSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else

			if(authorisedVO.getContactSeqId() != null){
				cStmtObject.setLong(2,authorisedVO.getContactSeqId().longValue());
			}//end of if(authorisedVO.getContactSeqId() != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else

			if(authorisedVO.getBankAcctSeqID() != null){
				cStmtObject.setLong(3,authorisedVO.getBankAcctSeqID().longValue());
			}//end of if(authorisedVO.getBankAcctSeqID() != null)
			else{
				cStmtObject.setLong(3,0);
			}//end of else

			if(authorisedVO.getFromDate() != null){
				cStmtObject.setTimestamp(4,new Timestamp(authorisedVO.getFromDate().getTime()));
			}//end of if(authorisedVO.getFromDate() != null)
			else{
				cStmtObject.setTimestamp(4,null);
			}//end of else

			if(authorisedVO.getToDate() != null){
				cStmtObject.setTimestamp(5,new Timestamp(authorisedVO.getToDate().getTime()));
			}//end of if(authorisedVO.getToDate() != null)
			else{
				cStmtObject.setTimestamp(5,null);
			}//end of else
			
			if(authorisedVO.getTransLimit() != null){
				cStmtObject.setString(6,authorisedVO.getTransLimit().toString());
			}//end of if(authorisedVO.getLimitAmount() != null)
			else{
				cStmtObject.setString(6,null);
			}//end of else
			
			cStmtObject.setLong(7,authorisedVO.getUpdatedBy().longValue());
			cStmtObject.registerOutParameter(8,Types.INTEGER);
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(8);
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl saveSignatories()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl saveSignatories()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveSignatories(AuthorisedVO authorisedVO)

	/**
	 * This method saves the Cheque Series information
	 * @param chequeVO the object which contains the Cheque Series details which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public long saveChequeSeries(ChequeVO chequeVO) throws TTKException
	{
		long lngChequeSeqId = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strChequeSeriesSave);
			if(chequeVO.getSeqID() != null){
				cStmtObject.setLong(1,chequeVO.getSeqID().longValue());
			}//end of if(chequeVO.getChequeSeqId() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else

			if(chequeVO.getAccountSeqID() != null){
				cStmtObject.setLong(2,chequeVO.getAccountSeqID().longValue());
			}//end of if(chequeVO.getBankAccSeqId() != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else
			cStmtObject.setLong(3,(Long.parseLong(chequeVO.getStartNo())));
			cStmtObject.setLong(4,(Long.parseLong(chequeVO.getEndNo())));
			cStmtObject.setLong(5,chequeVO.getUpdatedBy().longValue());
			cStmtObject.registerOutParameter(6,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(1,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.execute();
			lngChequeSeqId = cStmtObject.getLong(1);
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl saveChequeSeries()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl saveChequeSeries()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngChequeSeqId;
	}//end of saveChequeSeries(ChequeVO chequeVO)

	/**
	 * This method deletes the Cheque Series information from the database
	 * @param alDeleteList arraylist which contains the details of Cheque Series
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public int deleteChequeSeries(ArrayList alDeleteList) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strChequeSeriesDelete);
			cStmtObject.setString(1, (String)alDeleteList.get(0));
			cStmtObject.setLong(2,(Long)alDeleteList.get(1)); 
            cStmtObject.setLong(3,(Long)alDeleteList.get(2));    //ADDED_BY
			cStmtObject.registerOutParameter(4, Types.INTEGER);  //ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);                     //ROWS_PROCESSED
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl deleteChequeSeries()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl deleteChequeSeries()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteChequeSeries(ArrayList alDeleteList)

	/**
	 * This method returns the TransactionVO, which contains the Bank Account Transaction
	 * @param lngTransSeqID  which contains seq id of Transaction get the Bank Account Transaction Details
	 * @return TransactionVO object which contains the Bank Account Transaction Details
	 * @exception throws TTKException
	 */

	public TransactionVO getTransactionDetail(long bank_acct_seq_id,long lngTransSeqID,long lngUserSeqID )  throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		TransactionVO transactionVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strBankAccountTranDetail);
			cStmtObject.setLong(1,lngTransSeqID);
			cStmtObject.setLong(2,bank_acct_seq_id);
			cStmtObject.setLong(3,lngUserSeqID);
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.registerOutParameter(4,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				transactionVO = new TransactionVO();
				while (rs.next())
				{
					transactionVO.setTransSeqID(rs.getString("BANK_TRN_SEQ_ID")!=null ? new Long(rs.getString("BANK_TRN_SEQ_ID")):null);
					transactionVO.setAccountSeqID(rs.getString("BANK_ACC_SEQ_ID")!=null ? new Long(rs.getString("BANK_ACC_SEQ_ID")):null);
					transactionVO.setAccountNo(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));
					transactionVO.setAccountName(TTKCommon.checkNull(rs.getString("ACCOUNT_NAME")));
					transactionVO.setTransNbr(TTKCommon.checkNull(rs.getString("BANK_TRN_NUMBER")));
					transactionVO.setTransTypeID(TTKCommon.checkNull(rs.getString("TRN_TYPE")));
					transactionVO.setTransDate(rs.getString("TRN_DATE")!=null ? new Date(rs.getTimestamp("TRN_DATE").getTime()):null);
					transactionVO.setTransAmt(rs.getString("TRN_AMOUNT")!=null ? new BigDecimal(rs.getString("TRN_AMOUNT")):null);
					transactionVO.setRemarks(TTKCommon.checkNull(rs.getString("COMMENTS")));
					transactionVO.setCurrency(TTKCommon.checkNull(rs.getString("CURRENCY_TYPE")));
				}//end of while(rs.next())
			}//end of if(rs != null)
			return transactionVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getTransactionDetail()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getTransactionDetail()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getTransactionDetail()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getTransactionDetail(long bank_acct_seq_id,long lngTransSeqID,long lngUserSeqID )

	/**
	 * This method returns the BankGuaranteeVO, which contains the Bank Guarantee Details
	 * @param lngGuaranteeSeqID  which Guarantee seq id of BankGuarantee get the Bank Guarantee Details
	 * @return BankGuaranteeVO object which contains the Bank Guarantee Details
	 * @exception throws TTKException
	 */

	public BankGuaranteeVO getBankGuaranteeDetail(long lngGuaranteeSeqID,long lngUserSeqID)  throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankGuaranteeVO bankAccountDAO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankGuaranteeDetail);
			cStmtObject.setLong(1,lngGuaranteeSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				bankAccountDAO = new BankGuaranteeVO();
				while (rs.next())
				{
					bankAccountDAO.setGuaranteeSeqID(rs.getString("BANK_GUARANTEE_SEQ_ID")!=null ? new Long(rs.getString("BANK_GUARANTEE_SEQ_ID")):null);
					if(rs.getString("BANK_GUARANTEED") != null)
					{
						bankAccountDAO.setBanGuarAmt(new BigDecimal(rs.getString("BANK_GUARANTEED")));
					}//end of if(rs.getString("bank_guaranteed") != null)
					bankAccountDAO.setBankerName(TTKCommon.checkNull(rs.getString("BANKER_NAME")));
					bankAccountDAO.setFromDate(rs.getString("BANKER_FROM_DATE")!=null ? new Date(rs.getTimestamp("BANKER_FROM_DATE").getTime()):null);
					bankAccountDAO.setToDate(rs.getString("BANKER_TO_DATE")!=null ? new Date(rs.getTimestamp("BANKER_TO_DATE").getTime()):null);
					bankAccountDAO.setBgType(TTKCommon.checkNull(rs.getString("Bank_Guarantee_Type")));
					bankAccountDAO.setBankGuaranteeNo(TTKCommon.checkNull(rs.getString("Bank_Guarantee_No")));
					bankAccountDAO.setBgStatus(TTKCommon.checkNull(rs.getString("Bank_Guarantee_Status")));
					bankAccountDAO.setRemarks(TTKCommon.checkNull(rs.getString("Bank_Guarantee_Remark")));
					bankAccountDAO.setBgHandOverDate(rs.getString("Bank_Guarantee_Handover_Date")!=null ? new Date(rs.getTimestamp("Bank_Guarantee_Handover_Date").getTime()):null);
					bankAccountDAO.setBgReturnDate(rs.getString("Bank_Guarantee_Return_Date")!=null ? new Date(rs.getTimestamp("Bank_Guarantee_Return_Date").getTime()):null);
					bankAccountDAO.setIssueDate(rs.getString("Issue_DatE")!=null ? new Date(rs.getTimestamp("Issue_Date").getTime()):null);

					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return bankAccountDAO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getBankGuaranteeDetail()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getBankGuaranteeDetail()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getBankGuaranteeDetail()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getTransactionDetail(long bank_trn_seq_id,long lngTransSeqID,long lngUserSeqID )

	
	/**
	 * This method returns the BankGuaranteeVO, which contains the Bank Guarantee Details
	 * @param lngGuaranteeSeqID  which Guarantee seq id of BankGuarantee get the Bank Guarantee Details
	 * @return BankGuaranteeVO object which contains the Bank Guarantee Details
	 * @exception throws TTKException
	 */

	public ArrayList<BankGuaranteeVO> getBankRenewHistory(long lngGuaranteeSeqID,long lngUserSeqID)  throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankGuaranteeVO bankAccountDAO 	= null;
		ArrayList<BankGuaranteeVO> alListOfBGs		= null;	
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strBankGuaranteeHistoryDetail);
			cStmtObject.setLong(1,lngGuaranteeSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				alListOfBGs	=	new ArrayList<BankGuaranteeVO>();
				while (rs.next())
				{
					bankAccountDAO = new BankGuaranteeVO();
					bankAccountDAO.setGuaranteeSeqID(rs.getString("BANK_GUARANTEE_SEQ_ID")!=null ? new Long(rs.getString("BANK_GUARANTEE_SEQ_ID")):null);
					if(rs.getString("BANK_GUARANTEED") != null)
					{
						bankAccountDAO.setBanGuarAmt(new BigDecimal(rs.getString("BANK_GUARANTEED")));
					}//end of if(rs.getString("bank_guaranteed") != null)
					bankAccountDAO.setBankerName(TTKCommon.checkNull(rs.getString("BANKER_NAME")));
					bankAccountDAO.setFromDate(rs.getString("BANKER_FROM_DATE")!=null ? new Date(rs.getTimestamp("BANKER_FROM_DATE").getTime()):null);
					bankAccountDAO.setToDate(rs.getString("BANKER_TO_DATE")!=null ? new Date(rs.getTimestamp("BANKER_TO_DATE").getTime()):null);
					bankAccountDAO.setBgType(TTKCommon.checkNull(rs.getString("Bank_Guarantee_Type")));
					bankAccountDAO.setBankGuaranteeNo(TTKCommon.checkNull(rs.getString("Bank_Guarantee_No")));
					alListOfBGs.add(bankAccountDAO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alListOfBGs;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in BankAccountDAOImpl getBankGuaranteeDetail()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BankAccountDAOImpl getBankGuaranteeDetail()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BankAccountDAOImpl getBankGuaranteeDetail()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankRenewHistory(long bank_trn_seq_id,long lngTransSeqID,long lngUserSeqID )

	/**
	 * This method saves the Bank Account Transaction detail information
	 * @param transactionVO the object which contains theBank Account Transaction detail information which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public long saveTransaction(TransactionVO transactionVO) throws TTKException
	{
		long lngTranBankSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAccountTranSave);
			if(transactionVO.getTransSeqID() != null){
				cStmtObject.setLong(2,transactionVO.getTransSeqID().longValue());
			}//end of if(transactionVO.getTransSeqId() != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else

			if(transactionVO.getAccountSeqID() != null){
				cStmtObject.setLong(1,transactionVO.getAccountSeqID().longValue());
			}//end of if(transactionVO.getAccountSeqId() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else
			cStmtObject.setString(3,transactionVO.getTransTypeID());
			if(transactionVO.getTransDate() != null){
				cStmtObject.setTimestamp(4,new Timestamp(transactionVO.getTransDate().getTime()));
			}//end of if(transactionVO.getTransDate() != null)
			else{
				cStmtObject.setTimestamp(4, null);
			}//end of else

			if(transactionVO.getTransAmt() != null){
				cStmtObject.setDouble(5,transactionVO.getTransAmt().doubleValue());
			}//end of if(transactionVO.getTransAmt() != null)
			else{
				cStmtObject.setDouble(5,0.00);
			}//end of else
			cStmtObject.setString(6,transactionVO.getRemarks());
			cStmtObject.setString(7,transactionVO.getCurrency());
			
			cStmtObject.setLong(8,transactionVO.getUpdatedBy().longValue());
			cStmtObject.registerOutParameter(9,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(2,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.execute();
			lngTranBankSeqID = cStmtObject.getLong(2);
			conn.commit();
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl saveTransaction()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl saveTransaction()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngTranBankSeqID;
	}//end of saveTransaction(TransactionVO transactionVO)

	/**
	 * This method saves the Bank Guarantee information
	 * @param bankGuaranteeVO the object which contains the Bank Guarantee details which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public long saveBankGuarantee(BankGuaranteeVO bankGuaranteeVO) throws TTKException
	{
		long lngBankGuarSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankGuaranteeSave);
			if(bankGuaranteeVO.getGuaranteeSeqID() != null){
				cStmtObject.setLong(1,bankGuaranteeVO.getGuaranteeSeqID().longValue());
			}//end of if(transactionVO.getTransSeqId() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else

			if(bankGuaranteeVO.getAccountSeqID() != null){
				cStmtObject.setLong(2,bankGuaranteeVO.getAccountSeqID().longValue());
			}//end of if(transactionVO.getAccountSeqId() != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else

			if(bankGuaranteeVO.getBanGuarAmt() != null){
				cStmtObject.setDouble(3,bankGuaranteeVO.getBanGuarAmt().doubleValue());
			}//end of if(transactionVO.getTransAmt() != null)
			else{
				cStmtObject.setDouble(3,0.00);
			}//end of else
			cStmtObject.setString(4,bankGuaranteeVO.getBankerName());
			if(bankGuaranteeVO.getFromDate() != null){
				cStmtObject.setTimestamp(5,new Timestamp(bankGuaranteeVO.getFromDate().getTime()));
			}//end of if(transactionVO.getTransDate() != null)
			else{
				cStmtObject.setTimestamp(5, null);
			}//end of else
			if(bankGuaranteeVO.getToDate() != null){
				cStmtObject.setTimestamp(6,new Timestamp(bankGuaranteeVO.getToDate().getTime()));
			}//end of if(transactionVO.getTransDate() != null)
			else{
				cStmtObject.setTimestamp(6,null);
			}//end of else
			cStmtObject.setLong(7,bankGuaranteeVO.getUpdatedBy().longValue());
			//New Columns added for intX
			if(bankGuaranteeVO.getIssueDate() != null){
				cStmtObject.setTimestamp(8,new Timestamp(bankGuaranteeVO.getIssueDate().getTime()));
			}//ISSUE DATE
			else{
				cStmtObject.setTimestamp(8,null);
			}//end of else
			cStmtObject.setString(9,bankGuaranteeVO.getBankGuaranteeNo());
			cStmtObject.setString(10,bankGuaranteeVO.getBgType());
			cStmtObject.setString(11,bankGuaranteeVO.getBgStatus());
			cStmtObject.setString(12,bankGuaranteeVO.getRemarks());
			if(bankGuaranteeVO.getBgHandOverDate() != null){
				cStmtObject.setTimestamp(13,new Timestamp(bankGuaranteeVO.getBgHandOverDate().getTime()));
			}//getBgHandOverDate
			else{
				cStmtObject.setTimestamp(13,null);
			}//end of else
			if(bankGuaranteeVO.getBgReturnDate() != null){
				cStmtObject.setTimestamp(14,new Timestamp(bankGuaranteeVO.getBgReturnDate().getTime()));
			}//getBgReturnDate
			else{
				cStmtObject.setTimestamp(14,null);
			}//end of else

			cStmtObject.registerOutParameter(15,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(1,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.execute();
			lngBankGuarSeqID = cStmtObject.getLong(1);
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl saveBankGuarantee()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl saveBankGuarantee()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngBankGuarSeqID;
	}//end of saveBankGuarantee(BankGuaranteeVO bankGuaranteeVO)

	/**
     * This method saves the Reverse Transaction information
     * @param alReverseTransList arraylist which contains the details of Transaction Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int reverseTransaction(ArrayList alReverseTransList) throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strReverseTransaction);
			cStmtObject.setLong(1,(Long)alReverseTransList.get(0));//BANK_ACC_SEQ_ID
			cStmtObject.setString(2, (String)alReverseTransList.get(1));//CONCATENATED STRING OF BANK_TRN_SEQ_ID
			cStmtObject.setLong(3,(Long)alReverseTransList.get(2));//ADDED_BY
			cStmtObject.registerOutParameter(4, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(4);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl reverseTransaction()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl reverseTransaction()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//end of reverseTransaction(ArrayList alReverseTransList)
    
    
    public ArrayList<CacheObject> getCurrencyMaster() throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
    	PreparedStatement pStmt	=	null;
    	ArrayList<CacheObject> alCurrency	=	null;
    	ResultSet rs			=	null;
    	CacheObject cacheObject	=	null;
    	try{
    		conn	=	ResourceManager.getConnection();
			pStmt = conn.prepareStatement("SELECT TCC.CURRENCY_ID,TCC.CURRENCY_NAME FROM APP.TPA_CURRENCY_CODE TCC ORDER BY TCC.CURRENCY_NAME");
            rs = pStmt.executeQuery();
            if(rs!=null)
            {
            	alCurrency	=	new ArrayList<CacheObject>();
            	while(rs.next())
            	{
            		cacheObject	=	new CacheObject();
            		cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("CURRENCY_ID")));
            		cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("CURRENCY_ID")));
            		alCurrency.add(cacheObject);
            	}
            }
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in BankAccountDAOImpl getCurrencyMaster()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(pStmt != null) pStmt.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BankAccountDAOImpl getCurrencyMaster()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ClaimDAOImpl getCurrencyMaster()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs = null;
        		pStmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return alCurrency;
    }//end of getCurrencyMaster()
    
}//end of BankAccountDAOImpl