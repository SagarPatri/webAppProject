/**
 * @ (#) DebitNoteDAOImpl.java Sep 11, 2007
 * Project 	     : TTK HealthCare Services
 * File          : DebitNoteDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 11, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.finance;

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
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.finance.ChequeVO;
import com.ttk.dto.finance.ClaimDepositVO;
import com.ttk.dto.finance.DebitNoteDepositVO;
import com.ttk.dto.finance.DebitNoteVO;
import com.ttk.dto.finance.ReportsVO;

public class DebitNoteDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(DebitNoteDAOImpl.class);
	
	private static final String strDebitNoteList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_GET_DEBIT_NOTE_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteDebitNote = "{CALL FLOAT_ACCOUNTS_PKG.DELETE_DEBIT_NOTES(?,?,?)}";
	private static final String strGetDebitNoteDetail = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_GET_DEBIT_NOTE_DETAILS(?,?,?)}";
	private static final String strSaveDebitNoteDetail = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_DEBIT_NOTE_SAVE(?,?,?,?,?,?,?,?,?)}";
	// Added one parameter for debit note CR KOC1163
	private static final String strGetDebitNoteClaimsList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_GET_DEBIT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strAssociateClaims = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SAVE_DEBIT_NOTE_CLAIMS_ASSOC(?,?,?,?,?)}";
	private static final String strGetFloatType = "SELECT A.FLOAT_TYPE FROM fin_app.TPA_FLOAT_ACCOUNT A WHERE A.FLOAT_SEQ_ID=?";
	private static final String strGetDebitNoteDepositList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_GET_DEBIT_NOTE_DEPOSIT_DETAILS(?,?,?)}";
	private static final String strSaveDebitNoteDepositDetail = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SAVE_DEBIT_NOTE_DEPOSIT_DTL(?,?,?,?,?)}";
	private static final String strGetClaimsDepositList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_GET_CLAIMS_DEPOSIT_DETAILS(?,?,?)}";
	private static final String strSaveClaimDepositDetail = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SAVE_CLAIMS_DEPOSIT_DTL(?,?,?,?,?)}";
	private static final String strGetBatchList = "{CALL BATCH_REPORT_PKG.GET_BATCH_LIST(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strAssociateClaimsExcelUpload = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_upload_debit_note_clm_assoc(?,?,?,?,?)}";
	
	private static final int FLOAT_SEQ_ID = 1;
	private static final int DEBIT_SEQ_ID = 2;
	private static final int DEBIT_DATE = 3;
	private static final int DEBIT_STATUS_GENERAL_TYPE_ID = 4;
	private static final int DEBIT_AMOUNT = 5;
	private static final int REMARKS = 6;
	private static final int USER_SEQ_ID = 7;
	private static final int FINAL_DATE = 8;
	private static final int ROW_PROCESSED = 9;
	
	/**
	 * This method returns the ArrayList, which contains the DebitNoteVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of DebitNoteVO's object's which contains the details of the Debit Note
	 * @exception throws TTKException
	 */
	public ArrayList getDebitNoteList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		DebitNoteVO debitNoteVO =null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDebitNoteList);
			
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setLong(9,(Long.parseLong(alSearchCriteria.get(8).toString())));
			cStmtObject.setLong(10,(Long.parseLong(alSearchCriteria.get(9).toString())));
			cStmtObject.registerOutParameter(11,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			if(rs != null){
				while(rs.next()){
					debitNoteVO = new DebitNoteVO();
					
					if(rs.getString("DEBIT_SEQ_ID") != null){
						debitNoteVO.setDebitNoteSeqID(new Long(rs.getLong("DEBIT_SEQ_ID")));
					}//end of if(rs.getString("DEBIT_SEQ_ID") != null)
					
					debitNoteVO.setDebitNoteNbr(TTKCommon.checkNull(rs.getString("DEBIT_NOTE_NUMBER")));
					
					if(rs.getTimestamp("DEBIT_DATE")!= null){
						debitNoteVO.setDebitNoteDate(new Date(rs.getTimestamp("DEBIT_DATE").getTime()));
					}//end of if(rs.getTimestamp("DRAFT_DATE")!= null)
					
					if(rs.getTimestamp("FINAL_DATE")!= null){
						debitNoteVO.setFinalDate(new Date(rs.getTimestamp("FINAL_DATE").getTime()));
					}//end of if(rs.getTimestamp("DRAFT_DATE")!= null)
					
					debitNoteVO.setDebitNoteTypeID(TTKCommon.checkNull(rs.getString("DEBIT_STATUS_GENERAL_TYPE_ID")));
					debitNoteVO.setDebitNoteDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					
					if(rs.getString("DEBIT_AMOUNT") != null){
						debitNoteVO.setDebitNoteAmt(new BigDecimal(rs.getString("DEBIT_AMOUNT")));
					}//end of if(rs.getString("DEBIT_AMOUNT") != null)
					else{
						debitNoteVO.setDebitNoteAmt(new BigDecimal("0"));
					}//end of else
					
					alResultList.add(debitNoteVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
					log.error("Error while closing the Resultset in DebitNoteDAOImpl getDebitNoteList()",sqlExp);
					throw new TTKException(sqlExp, "debitNote");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in DebitNoteDAOImpl getDebitNoteList()",sqlExp);
						throw new TTKException(sqlExp, "debitNote");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in DebitNoteDAOImpl getDebitNoteList()",sqlExp);
							throw new TTKException(sqlExp, "debitNote");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "debitNote");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getDebitNoteList(ArrayList alSearchCriteria)
	
	/**
	 * This method deltes the Debit Note Deposit information
	 * @param strDebitSeqId String Object contains Pipe concatenated Debit Seq ID's
	 * @param lngUserSeqID long value contains User Seq ID
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteDebitNote(String strDebitSeqId,long lngUserSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteDebitNote);
			
			cStmtObject.setString(1,strDebitSeqId);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
        			log.error("Error while closing the Statement in DebitNoteDAOImpl deleteDebitNote()",sqlExp);
        			throw new TTKException(sqlExp, "debitNote");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in DebitNoteDAOImpl deleteDebitNote()",sqlExp);
        				throw new TTKException(sqlExp, "debitNote");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "debitNote");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteDebitNote(String strDebitSeqId,long lngUserSeqID)
	
	/**
	 * This method returns the DebitNoteVO which contains the Debit Note details
	 * @param lngDebitNoteSeqID long value which contains Debit Note seq id to get the Debit Note details
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return DebitNoteVO this contains the Debit Note details
	 * @exception throws TTKException
	 */
	public DebitNoteVO getDebitNoteDetail(long lngDebitNoteSeqID,long lngUserSeqID) throws TTKException {
	    Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		DebitNoteVO debitNoteVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDebitNoteDetail);
			cStmtObject.setLong(1,lngDebitNoteSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			
			if(rs != null){
				while(rs.next()) {
					debitNoteVO = new DebitNoteVO();
					
					if(rs.getString("DEBIT_SEQ_ID") != null){
						debitNoteVO.setDebitNoteSeqID(new Long(rs.getLong("DEBIT_SEQ_ID")));
					}//end of if(rs.getString("DEBIT_SEQ_ID") != null)
					//Changes for Debit Note CR KOC1163 
					if(rs.getString("FLOAT_SEQ_ID") != null){
						debitNoteVO.setFloatSeqID(new Long(rs.getLong("FLOAT_SEQ_ID")));
					}//end of if(rs.getString("FLOAT_SEQ_ID") != null)
					// End changes for Debit Note CR KOC1163 
					
					debitNoteVO.setDebitNoteNbr(TTKCommon.checkNull(rs.getString("DEBIT_NOTE_NUMBER")));
					
					if(rs.getTimestamp("DEBIT_DATE")!= null){
						debitNoteVO.setDebitNoteDate(new Date(rs.getTimestamp("DEBIT_DATE").getTime()));
				      }//end of if(rs.getTimestamp("DRAFT_DATE")!= null)
					
					
					if(rs.getTimestamp("FINAL_DATE")!= null){
						debitNoteVO.setFinalDate(new Date(rs.getTimestamp("FINAL_DATE").getTime()));
				     }//end of if(rs.getTimestamp("FINAL_DATE")!= null)
					
					debitNoteVO.setDebitNoteTypeID(TTKCommon.checkNull(rs.getString("DEBIT_STATUS_GENERAL_TYPE_ID")));
					
					if(rs.getString("DEBIT_AMOUNT") != null){
						debitNoteVO.setDebitNoteAmt(new BigDecimal(rs.getString("DEBIT_AMOUNT")));
					}//end of if(rs.getString("DEBIT_AMOUNT") != null)
					
					debitNoteVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
					log.error("Error while closing the Resultset in DebitNoteDAOImpl getDebitNoteDetail()",sqlExp);
					throw new TTKException(sqlExp, "debitNote");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in DebitNoteDAOImpl getDebitNoteDetail()",sqlExp);
						throw new TTKException(sqlExp, "debitNote");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in DebitNoteDAOImpl getDebitNoteDetail()",sqlExp);
							throw new TTKException(sqlExp, "debitNote");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "debitNote");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return debitNoteVO;
	}//end of getDebitNoteDetail(long lngDebitNoteSeqID,long ingUserSeqID)
	
	/**
	 * This method saves the Debit Note details
	 * @param DebitNoteVO the object which contains Debit Note details
	 * @return long contains Debit Note Seq ID
	 * @exception throws TTKException
	 */
	public long saveDebitNoteDetail(DebitNoteVO debitNoteVO) throws TTKException {
		long lngDebitSeqID =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDebitNoteDetail);
			
			cStmtObject.setLong(FLOAT_SEQ_ID,debitNoteVO.getFloatSeqID().longValue());
			
			if(debitNoteVO.getDebitNoteSeqID() != null){
				cStmtObject.setLong(DEBIT_SEQ_ID,debitNoteVO.getDebitNoteSeqID().longValue());
			}//end of if(debitNoteVO.getDebitNoteSeqID() != null)
			else {
				cStmtObject.setLong(DEBIT_SEQ_ID,0);
			}//end of else
			
			if(debitNoteVO.getDebitNoteDate()  != null){
				cStmtObject.setTimestamp(DEBIT_DATE,new Timestamp(debitNoteVO.getDebitNoteDate().getTime()));
			}//end of if(debitNoteVO.getDebitNoteDate())
			else {
				cStmtObject.setTimestamp(DEBIT_DATE,null);
			}//end of else
			
			if(debitNoteVO.getFinalDate() != null){
			    cStmtObject.setTimestamp(FINAL_DATE,new Timestamp(debitNoteVO.getFinalDate().getTime()));
			}//end of if(debitNoteVO.getDtDraftDate())
			else {
				cStmtObject.setTimestamp(FINAL_DATE,null);
			}//end of else
			
			cStmtObject.setString(DEBIT_STATUS_GENERAL_TYPE_ID,debitNoteVO.getDebitNoteTypeID());
			
			if(debitNoteVO.getDebitNoteAmt() != null){
				cStmtObject.setBigDecimal(DEBIT_AMOUNT,debitNoteVO.getDebitNoteAmt());
			}//end of if(debitNoteVO.getDebitNoteAmt() != null)
			else{
				cStmtObject.setDouble(DEBIT_AMOUNT,0.0);
			}//end of else
			
			cStmtObject.setString(REMARKS,debitNoteVO.getRemarks());
			cStmtObject.setLong(USER_SEQ_ID,debitNoteVO.getUpdatedBy().longValue());
			cStmtObject.registerOutParameter(DEBIT_SEQ_ID,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			lngDebitSeqID = cStmtObject.getLong(DEBIT_SEQ_ID);
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
        			log.error("Error while closing the Statement in DebitNoteDAOImpl saveDebitNoteDetail()",sqlExp);
        			throw new TTKException(sqlExp, "debitNote");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in DebitNoteDAOImpl saveDebitNoteDetail()",sqlExp);
        				throw new TTKException(sqlExp, "debitNote");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "debitNote");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngDebitSeqID;
	}//end of saveDebitNoteDetail(DebitNoteVO debitNoteVO)
	
	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Claims
	 * @exception throws TTKException
	 */
	public ArrayList getDebitNoteClaimList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		ChequeVO chequeVO =null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDebitNoteClaimsList);
			
			cStmtObject.setLong(1,(Long.parseLong(alSearchCriteria.get(0).toString())));
			cStmtObject.setLong(2,(Long.parseLong(alSearchCriteria.get(1).toString())));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			// Changes added for debit note CR KOC1163
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(9));
			cStmtObject.setString(11,(String)alSearchCriteria.get(10));
			cStmtObject.setString(12,(String)alSearchCriteria.get(11));
			cStmtObject.setLong(13,(Long.parseLong(alSearchCriteria.get(12).toString())));
			cStmtObject.setLong(14,(Long.parseLong(alSearchCriteria.get(13).toString())));
			cStmtObject.registerOutParameter(15,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(15);
			
			if(rs != null){
				while(rs.next()){
					chequeVO = new ChequeVO();
					
					if(rs.getString("CLAIM_SEQ_ID") != null){
						chequeVO.setSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)
					
					chequeVO.setClaimSettNo(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NO")));
					chequeVO.setEnrollID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					chequeVO.setClaimantName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					chequeVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));

					if(rs.getTimestamp("CLAIM_APRV_DATE") != null){
						chequeVO.setApprDate(new Date(rs.getTimestamp("CLAIM_APRV_DATE").getTime()));
					}//end of if(rs.getTimestamp("CLAIM_APRV_DATE") != null)

					chequeVO.setInFavorOf(TTKCommon.checkNull(rs.getString("IN_FAVOUR_OF")));

					if(rs.getString("APPROVED_AMOUNT") != null){
						chequeVO.setClaimAmt(new BigDecimal(rs.getString("APPROVED_AMOUNT")));
					}//end of if(rs.getString("APPROVED_AMOUNT") != null)
					
					alResultList.add(chequeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
					log.error("Error while closing the Resultset in DebitNoteDAOImpl getDebitNoteClaimList()",sqlExp);
					throw new TTKException(sqlExp, "debitNote");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in DebitNoteDAOImpl getDebitNoteClaimList()",sqlExp);
						throw new TTKException(sqlExp, "debitNote");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in DebitNoteDAOImpl getDebitNoteClaimList()",sqlExp);
							throw new TTKException(sqlExp, "debitNote");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "debitNote");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getDebitNoteClaimList(ArrayList alSearchCriteria)
	
	/**
	 * This method Associates Claim(s) to Debit Note
	 * @param alClaimsList the object which contains the details of the Claims
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int associateClaims(ArrayList alClaimsList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateClaims);
			
			if(alClaimsList != null){
				cStmtObject.setLong(1,(Long)alClaimsList.get(0));
			    cStmtObject.setString(2,(String)alClaimsList.get(1));
				cStmtObject.setString(3,(String)alClaimsList.get(2));
				cStmtObject.setLong(4,(Long)alClaimsList.get(3));
				cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
				cStmtObject.execute();
				iResult = cStmtObject.getInt(5);//ROWS_PROCESSED
				conn.commit();
			}//end of if(alClaimsList != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
        			log.error("Error while closing the Statement in DebitNoteDAOImpl associateClaims()",sqlExp);
        			throw new TTKException(sqlExp, "debitNote");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in DebitNoteDAOImpl associateClaims()",sqlExp);
        				throw new TTKException(sqlExp, "debitNote");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "debitNote");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of associateClaims(ArrayList alClaimsList)
	
	/**
	 * This method returns the String which contains the Float Type ID for displaying the Transaction Type
	 * @param lngFloatSeqID long value which contains Float seq id to get the Float Type ID
	 * @return String this contains the Float Type ID
	 * @exception throws TTKException
	 */
	public String getFloatType(long lngFloatSeqID) throws TTKException {
		String strFloatTypeID = "";
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
    		pStmt=conn.prepareStatement(strGetFloatType);
    		
    		pStmt.setLong(1,lngFloatSeqID);
    		rs= pStmt.executeQuery();
    		if(rs != null){
    			while(rs.next()){
    				strFloatTypeID = TTKCommon.checkNull(rs.getString("FLOAT_TYPE"));
    			}//end of while(rs.next())
    		}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
					log.error("Error while closing the Resultset in DebitNoteDAOImpl getFloatType()",sqlExp);
					throw new TTKException(sqlExp, "debitNote");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in DebitNoteDAOImpl getFloatType()",sqlExp);
						throw new TTKException(sqlExp, "debitNote");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in DebitNoteDAOImpl getFloatType()",sqlExp);
							throw new TTKException(sqlExp, "debitNote");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "debitNote");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strFloatTypeID;
	}//end of getFloatType(long lngFloatSeqID)
	
	/**
	 * This method returns the ArrayList, which contains the DebitNoteDepositVO's which are populated from the database
	 * @param lngFloatSeqID long value which contains Float seq id to get the Debit Note details
	 * @param lngFloatTransSeqID long value which contains Float Transaction seq id to get the Debit Note details
	 * @return ArrayList of DebitNoteDepositVO's object's which contains the details of the Debit Note
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getDebitNoteDepositList(long lngFloatSeqID,long lngFloatTransSeqID) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        DebitNoteDepositVO debitNoteDepositVO = null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDebitNoteDepositList);
            
            cStmtObject.setLong(1,lngFloatSeqID);
            cStmtObject.setLong(2,lngFloatTransSeqID);
            cStmtObject.registerOutParameter(3,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            if(rs != null){
            	while(rs.next()){
            		debitNoteDepositVO = new DebitNoteDepositVO();
                	
                	if(rs.getString("DEBIT_SEQ_ID") != null){
                		debitNoteDepositVO.setDebitNoteSeqID(new Long(rs.getLong("DEBIT_SEQ_ID")));
                	}//end of if(rs.getString("DEBIT_SEQ_ID") != null)
                	
                	debitNoteDepositVO.setDebitNoteNbr(TTKCommon.checkNull(rs.getString("DEBIT_NOTE_NUMBER")));
                	
                	if(rs.getString("DEBIT_AMOUNT") != null){
                		debitNoteDepositVO.setDebitNoteAmt(new BigDecimal(rs.getString("DEBIT_AMOUNT")));
                	}//end of if(rs.getString("DEBIT_AMOUNT") != null)
                	else{
                		debitNoteDepositVO.setDebitNoteAmt(new BigDecimal("0"));
                	}//end of else
                	
                	if(rs.getString("TOTAL_DEP_AMT") != null){
                		debitNoteDepositVO.setTotalDepositAmt(new BigDecimal(rs.getString("TOTAL_DEP_AMT")));
                	}//end of if(rs.getString("TOTAL_DEP_AMT") != null)
                	else{
                		debitNoteDepositVO.setTotalDepositAmt(new BigDecimal("0"));
                	}//end of else
                	
                	if(rs.getString("TRANS_DEP_AMT") != null){
                		debitNoteDepositVO.setDepositedAmt(new BigDecimal(rs.getString("TRANS_DEP_AMT")));
                	}//end of if(rs.getString("TRANS_DEP_AMT") != null)
                	else{
                		debitNoteDepositVO.setDepositedAmt(new BigDecimal("0"));
                	}//end of else
                	
                	if(rs.getString("AMT_PENDING") != null){
                		debitNoteDepositVO.setPendingAmt(new BigDecimal(rs.getString("AMT_PENDING")));
                	}//end of if(rs.getString("AMT_PENDING") != null)
                	else{
                		debitNoteDepositVO.setPendingAmt(new BigDecimal("0"));
                	}//end of else
                	
                	if(rs.getString("DEBIT_NOTE_TRANS_SEQ_ID") != null){
                		debitNoteDepositVO.setDebitNoteTransSeqID(new Long(rs.getLong("DEBIT_NOTE_TRANS_SEQ_ID")));
                	}//end of if(rs.getString("DEBIT_NOTE_TRANS_SEQ_ID") != null)
                	
                	debitNoteDepositVO.setTranCompleteYN(TTKCommon.checkNull(rs.getString("TRAN_COMPLETE_YN")));
                	
                	alResultList.add(debitNoteDepositVO);
            	}//end of while(rs.next())
            }//end of if(rs != null)
        	return (ArrayList<Object>)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "debitNote");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "debitNote");
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
					log.error("Error while closing the Resultset in DebitNoteDAOImpl getDebitNoteDepositList()",sqlExp);
					throw new TTKException(sqlExp, "debitNote");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in DebitNoteDAOImpl getDebitNoteDepositList()",sqlExp);
						throw new TTKException(sqlExp, "debitNote");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in DebitNoteDAOImpl getDebitNoteDepositList()",sqlExp);
							throw new TTKException(sqlExp, "debitNote");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "debitNote");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getDebitNoteDepositList(long lngFloatSeqID,long lngFloatTransSeqID)
	
	/**
	 * This method saves the Debit Note Deposit information
	 * @param alDepositList ArrayList contains Debit Note Deposit information
	 * @param lngFloatTransSeqID long value contains Float Trans Seq ID
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveDebitNoteDepositDetail(ArrayList alDepositList,long lngFloatTransSeqID) throws TTKException {
		int iResult = 1;
        Connection conn = null;
        CallableStatement cstmObj = null;
        DebitNoteDepositVO debitNoteDepositVO = null;
        try{
        	conn = ResourceManager.getConnection();
        	cstmObj = conn.prepareCall(strSaveDebitNoteDepositDetail);
            if(alDepositList != null){
            	for(int i=0;i<alDepositList.size();i++){
            		debitNoteDepositVO = (DebitNoteDepositVO)alDepositList.get(i);
            		cstmObj.setLong(1, lngFloatTransSeqID);
            		cstmObj.setLong(2, debitNoteDepositVO.getDebitNoteSeqID());
            		
            		if(debitNoteDepositVO.getCurrentAmt() == null){
            			cstmObj.setDouble(3, 0.00);
        			}//end of if(premiumVO.getTotalSumInsured() == null)
        			else{
        				cstmObj.setDouble(3, debitNoteDepositVO.getCurrentAmt().doubleValue());
        			}//end of else
            		cstmObj.setString(4, debitNoteDepositVO.getTransTypeID());
            		cstmObj.setLong(5, debitNoteDepositVO.getUpdatedBy());
            		cstmObj.addBatch();
        		}//end of for
            	cstmObj.executeBatch();
            	conn.commit();
            }//end of if(alDepositList != null)
        }//end of try
        catch (SQLException sqlExp) 
        { 
            try {
                conn.rollback();
                throw new TTKException(sqlExp, "debitNote");
            } //end of try
            catch (SQLException sExp) {
                throw new TTKException(sExp, "debitNote");   
            }//end of catch (SQLException sExp)
        }//end of catch (SQLException sqlExp) 
        catch (Exception exp) 
        {
            try {
                conn.rollback();
                throw new TTKException(exp, "debitNote");
            } //end of try
            catch (SQLException sqlExp) {
                throw new TTKException(sqlExp, "debitNote");
            }//end of catch (SQLException sqlExp)
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cstmObj != null) cstmObj.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in DebitNoteDAOImpl saveDebitNoteDepositDetail()",sqlExp);
        			throw new TTKException(sqlExp, "debitNote");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in DebitNoteDAOImpl saveDebitNoteDepositDetail()",sqlExp);
        				throw new TTKException(sqlExp, "debitNote");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "debitNote");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cstmObj = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveDebitNoteDepositDetail(ArrayList alDepositList,long lngFloatTransSeqID)
	
	/**
	 * This method returns the ArrayList, which contains the ClaimDepositVO's which are populated from the database
	 * @param lngDebitSeqID long value which contains Debit seq id to get the Claim Deposit details
	 * @param lngFloatTransSeqID long value which contains Float Transaction seq id to get the Claim Deposit details
	 * @return ArrayList of ClaimDepositVO's object's which contains the details of the Claim Deposit
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getClaimDepositList(long lngDebitSeqID,long lngFloatTransSeqID) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ClaimDepositVO claimDepositVO = null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimsDepositList);
            
            cStmtObject.setLong(1,lngDebitSeqID);
            cStmtObject.setLong(2,lngFloatTransSeqID);
            cStmtObject.registerOutParameter(3,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            if(rs != null){
            	while(rs.next()){
            		claimDepositVO = new ClaimDepositVO();
            		
            		if(rs.getString("CLAIM_SEQ_ID") != null){
            			claimDepositVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
            		}//end of if(rs.getString("CLAIM_SEQ_ID") != null)
            		
            		claimDepositVO.setClaimSettleNbr(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NO")));
            		
            		if(rs.getString("APPROVED_AMOUNT") != null){
            			claimDepositVO.setApprovedAmt(new BigDecimal(rs.getString("APPROVED_AMOUNT")));
                	}//end of if(rs.getString("APPROVED_AMOUNT") != null)
            		else{
            			claimDepositVO.setApprovedAmt(new BigDecimal("0"));
            		}//end of else
            		
            		if(rs.getString("TOTAL_DEP_AMT") != null){
            			claimDepositVO.setTotalAmtPaid(new BigDecimal(rs.getString("TOTAL_DEP_AMT")));
                	}//end of if(rs.getString("TOTAL_DEP_AMT") != null)
            		else{
            			claimDepositVO.setTotalAmtPaid(new BigDecimal("0"));
            		}//end of else
            		
            		if(rs.getString("TRANS_DEP_AMT") != null){
            			claimDepositVO.setTransAmtPaid(new BigDecimal(rs.getString("TRANS_DEP_AMT")));
                	}//end of if(rs.getString("TRANS_DEP_AMT") != null)
            		else{
            			claimDepositVO.setTransAmtPaid(new BigDecimal("0"));
            		}//end of else
            		
            		if(rs.getString("AMT_PENDING") != null){
            			claimDepositVO.setPendingAmt(new BigDecimal(rs.getString("AMT_PENDING")));
                	}//end of if(rs.getString("AMT_PENDING") != null)
            		else{
            			claimDepositVO.setPendingAmt(new BigDecimal("0"));
            		}//end of else
            		
            		claimDepositVO.setTranCompleteYN(TTKCommon.checkNull(rs.getString("TRAN_COMPLETE_YN")));
            		
            		alResultList.add(claimDepositVO);
            	}//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList<Object>)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "debitNote");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "debitNote");
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
					log.error("Error while closing the Resultset in DebitNoteDAOImpl getClaimDepositList()",sqlExp);
					throw new TTKException(sqlExp, "debitNote");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in DebitNoteDAOImpl getClaimDepositList()",sqlExp);
						throw new TTKException(sqlExp, "debitNote");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in DebitNoteDAOImpl getClaimDepositList()",sqlExp);
							throw new TTKException(sqlExp, "debitNote");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "debitNote");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimDepositList(long lngDebitSeqID,long lngFloatTransSeqID)
	
	/**
	 * This method saves the Claim(s) Deposit information
	 * @param alClaimDepositList ArrayList contains Claim(s) Deposit information
	 * @param lngDebitNoteTransSeqID Debit Note Trans Seq ID
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveClaimDepositDetail(ArrayList alClaimDepositList,long lngDebitNoteTransSeqID) throws TTKException {
		int iResult = 1;
        Connection conn = null;
        CallableStatement cStmt = null;
        ClaimDepositVO claimDepositVO = null;
        try{
        	conn = ResourceManager.getConnection();
        	cStmt = conn.prepareCall(strSaveClaimDepositDetail);
            if(alClaimDepositList != null){
            	for(int i=0;i<alClaimDepositList.size();i++){
            		claimDepositVO = (ClaimDepositVO)alClaimDepositList.get(i);
            		
            		cStmt.setLong(1, lngDebitNoteTransSeqID);
            		cStmt.setLong(2, claimDepositVO.getClaimSeqID());
            		
            		if(claimDepositVO.getCurrentAmt() == null){
            			cStmt.setDouble(3, 0.00);
        			}//end of if(premiumVO.getTotalSumInsured() == null)
        			else{
        				cStmt.setDouble(3, claimDepositVO.getCurrentAmt().doubleValue());
        			}//end of else
            		cStmt.setString(4, claimDepositVO.getTransTypeID());
            		cStmt.setLong(5, claimDepositVO.getUpdatedBy());
            		cStmt.addBatch();
        		}//end of for
            	cStmt.executeBatch();
            	conn.commit();
            }//end of if(alDepositList != null)
        }//end of try
        catch (SQLException sqlExp) 
        { 
            try {
                conn.rollback();
                throw new TTKException(sqlExp, "debitNote");
            } //end of try
            catch (SQLException sExp) {
                throw new TTKException(sExp, "debitNote");   
            }//end of catch (SQLException sExp)
        }//end of catch (SQLException sqlExp) 
        catch (Exception exp) 
        {
            try {
                conn.rollback();
                throw new TTKException(exp, "debitNote");
            } //end of try
            catch (SQLException sqlExp) {
                throw new TTKException(sqlExp, "debitNote");
            }//end of catch (SQLException sqlExp)
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmt != null) cStmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in DebitNoteDAOImpl saveDebitNoteDepositDetail()",sqlExp);
        			throw new TTKException(sqlExp, "debitNote");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in DebitNoteDAOImpl saveDebitNoteDepositDetail()",sqlExp);
        				throw new TTKException(sqlExp, "debitNote");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "debitNote");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveClaimDepositDetail(ArrayList alClaimDepositList,long lngDebitNoteTransSeqID)
	
	/**
	 * This method returns the ArrayList, which contains the ReportVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ReportVO's object's which contains the details of the Batch
	 * @exception throws TTKException
	 */
	public ArrayList getBatchList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		ReportsVO reportsVO =null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBatchList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			if(rs != null){
				while(rs.next()){
					reportsVO = new ReportsVO();
					reportsVO.setFloatAccNo(TTKCommon.checkNull(rs.getString("FLOAT_ACCT_NUM")));
					reportsVO.setBatchNo(TTKCommon.checkNull(rs.getString("BATCH_NUMBER")));					
					if(rs.getTimestamp("BATCH_DATE")!= null){
						reportsVO.setBatchDate(new Date(rs.getTimestamp("BATCH_DATE").getTime()));
					}//end of if(rs.getTimestamp("BATCH_DATE")!= null)
					reportsVO.setFileName(TTKCommon.checkNull(rs.getString("TEMPLATE_NAME")));
					alResultList.add(reportsVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
					log.error("Error while closing the Resultset in DebitNoteDAOImpl getDebitNoteList()",sqlExp);
					throw new TTKException(sqlExp, "debitNote");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in DebitNoteDAOImpl getDebitNoteList()",sqlExp);
						throw new TTKException(sqlExp, "debitNote");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in DebitNoteDAOImpl getDebitNoteList()",sqlExp);
							throw new TTKException(sqlExp, "debitNote");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "debitNote");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getDebitNoteList(ArrayList alSearchCriteria)

	public int associateClaimsExcelUpload(ArrayList<Object> alClaimsList)throws TTKException {
		// TODO Auto-generated method stub
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateClaimsExcelUpload);
			
			if(alClaimsList != null){
				cStmtObject.setLong(1,(Long)alClaimsList.get(0));//Debit Seq id
			    cStmtObject.setString(2,(String)alClaimsList.get(1));//claim settlement number
				cStmtObject.setString(3,(String)alClaimsList.get(2)); //Associate and unAssociate DBA - Associated DBU - Unassociated
				cStmtObject.setLong(4,(Long)alClaimsList.get(3)); //Added By
				cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
				cStmtObject.execute();
				iResult = cStmtObject.getInt(5);//ROWS_PROCESSED
				conn.commit();
			}//end of if(alClaimsList != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "debitNote");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "debitNote");
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
        			log.error("Error while closing the Statement in DebitNoteDAOImpl associateClaimsExcelUpload()",sqlExp);
        			throw new TTKException(sqlExp, "debitNote");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in DebitNoteDAOImpl associateClaimsExcelUpload()",sqlExp);
        				throw new TTKException(sqlExp, "debitNote");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "debitNote");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}
	
	
	/*public static void main(String a[]) throws Exception {
		
	
		DebitNoteDAOImpl debitNoteDAO = new DebitNoteDAOImpl();
		ArrayList al = new ArrayList();
		al.add("FL-ACC-621");
		al.add("");
		al.add("01/03/2007");
		al.add("");
		al.add("batch_number");
		al.add("ASC");
		al.add("1");
		al.add("10");
		ArrayList rsl = debitNoteDAO.getBatchList(al);
		
		for(int i=0; i<=rsl.size(); i++)
		{
			ReportsVO rvo = new ReportsVO();
			rvo = (ReportsVO)rsl.get(i);
			if(rvo != null)
			{
				
				
				
			}
		}*/
		/*debitNoteVO.setFloatSeqID(new Long(681));
		debitNoteVO.setDebitNoteDate(new Date("11/09/2007"));
		debitNoteVO.setDebitNoteTypeID("DDT");
		debitNoteVO.setDebitNoteAmt(new BigDecimal("35000"));
		debitNoteVO.setRemarks("TEST2");
		debitNoteVO.setUpdatedBy(new Long(56503));
		debitNoteDAO.saveDebitNoteDetail(debitNoteVO);*/
		//debitNoteDAO.getDebitNoteDetail(new Long(2),new Long(56503));
		/*ArrayList alSearchCriteria = new ArrayList();
		alSearchCriteria.add(new Long(681));
		alSearchCriteria.add("DDT");
		alSearchCriteria.add("");
		alSearchCriteria.add("");
		alSearchCriteria.add("");
		alSearchCriteria.add("debit_note_number");
		alSearchCriteria.add("ASC");
		alSearchCriteria.add("1");
		alSearchCriteria.add("10");
		debitNoteDAO.getDebitNoteList(alSearchCriteria);*/
		/*ArrayList alSearchCriteria = new ArrayList();
		alSearchCriteria.add(new Long(681));
		alSearchCriteria.add(new Long(2));
		alSearchCriteria.add("");
		alSearchCriteria.add("");
		alSearchCriteria.add("");
		alSearchCriteria.add("");
		alSearchCriteria.add("IND");
		alSearchCriteria.add("");
		alSearchCriteria.add("DBU");
		alSearchCriteria.add("claim_settlement_no");
		alSearchCriteria.add("ASC");
		alSearchCriteria.add("1");
		alSearchCriteria.add("10");
		debitNoteDAO.getDebitNoteClaimList(alSearchCriteria);*/
		
		/*ArrayList alClaimsList = new ArrayList();
		alClaimsList.add(new Long(1));
		alClaimsList.add("|128343|");
		alClaimsList.add("DBU");
		alClaimsList.add(new Long(56503));
		debitNoteDAO.associateClaims(alClaimsList);*/
		
		//debitNoteDAO.getDebitNoteDepositList(new Long(681),new Long(801));
		//debitNoteDAO.getClaimDepositList(new Long(1),new Long(801));
		//debitNoteDAO.getFloatType(new Long(681));
//	}//end of main
	

}//end of DebitNoteDAOImpl
