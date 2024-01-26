/**
 * @ (#) BatchDAOImpl.java Jan 31, 2006
 * Project       : TTK HealthCare Services
 * File          : BatchDAOImpl.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Jan 31, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.dao.impl.enrollment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.print.attribute.standard.MediaSize.Other;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.enrollment.BatchVO;
import com.ttk.dto.enrollment.CardBatchVO;

public class BatchDAOImpl implements BaseDAO, Serializable {
	
	private static Logger log = Logger.getLogger(BatchDAOImpl.class);
	
	//private static final String strBatchList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_BATCH_ENRL_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strGetBatch = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_BATCH(?,?)}";
//	private static final String strBatchList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_BATCH_ENRL_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strBatchList = "{CALL POLICY_ENROLLMENT_PKG_SELECT_BATCH_ENRL_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strGetBatch = "{CALL POLICY_ENROLLMENT_PKG.SELECT_BATCH(?,?)}";
	private static final String strGetBatch = "{CALL POLICY_ENROLLMENT_PKG_SELECT_BATCH(?,?)}";
	//private static final String strSaveBatch = "{CALL POLICY_ENROLLMENT_PKG.SAVE_BATCH_ENRL(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveBatch = "{CALL POLICY_ENROLLMENT_PKG_SAVE_BATCH_ENRL(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteBatch = "{CALL POLICY_ENROLLMENT_PKG_DELETE_GENERAL(?,?,?,?,?,?,?,?)}";
    private static final String strBatchComplete = "{CALL POLICY_ENROLLMENT_PKG_COMPLETE_BATCH(?,?,?,?)}";
    private static final String strSoftcopyBatchList = "{CALL ENROLLMENT_REPORTS_PKG.GET_BATCH_LIST(?,?,?,?,?,?,?,?)}";
    private static final String strCardBatchList = "{CALL ENROLLMENT_REPORTS_PKG.GET_CARD_BATCH_LIST(?,?,?,?,?,?,?,?)}";
	
	private static final int BATCH_NUMBER = 1;
	private static final int INS_SEQ_ID = 2;
	private static final int START_DATE = 3;
	private static final int END_DATE = 4;
	private static final int INS_COMP_CODE_NUMBER = 5;
	private static final int CLARIFY_GENERAL_TYPE_ID = 6;
	private static final int BATCH_STATUS = 7;
	private static final int UPLOAD_YN = 8;
	private static final int POLICY_NUMBER = 9;
	 private static final int BATCH_TYPE = 10;
    private static final int SORT_VAR = 11;
	private static final int SORT_ORDER = 12;
	private static final int START_NUM = 13;
	private static final int END_NUM = 14;
	private static final int USER_SEQ_ID = 15;
    private static final int CURSOR = 16;
	/**
	 * This method returns the Arraylist of BatchVO's  which contains the details of batch enrollment details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BatchVO object which contains the details of batch enrollment details
	 * @exception throws TTKException
	 */
	public ArrayList getBatchList(ArrayList alSearchCriteria) throws TTKException{
		Collection<Object> alResultList = new ArrayList<Object>();
		BatchVO batchVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBatchList);

			cStmtObject.setString(BATCH_NUMBER,(String)alSearchCriteria.get(0));		
			
			if(!"".equals(alSearchCriteria.get(1))){
				
				cStmtObject.setLong(INS_SEQ_ID,Long.parseLong(alSearchCriteria.get(1).toString()));
			}else{
				cStmtObject.setLong(INS_SEQ_ID,0);
			}
			cStmtObject.setString(START_DATE,(String)alSearchCriteria.get(2));
			cStmtObject.setString(END_DATE,(String)alSearchCriteria.get(3));
			cStmtObject.setString(INS_COMP_CODE_NUMBER,(String)alSearchCriteria.get(4));
			cStmtObject.setString(CLARIFY_GENERAL_TYPE_ID,(String)alSearchCriteria.get(6));
			cStmtObject.setString(BATCH_STATUS,(String)alSearchCriteria.get(7));		
			
			if(alSearchCriteria.get(8).equals("MNL")){
				cStmtObject.setString(UPLOAD_YN,"N");
			}//end of if(alSearchCriteria.get(8).equals("MNL"))
			else if(alSearchCriteria.get(8).equals("ELE")){
				cStmtObject.setString(UPLOAD_YN,"Y");
			}//end of if(alSearchCriteria.get(8).equals("ELE"))
			else{
				cStmtObject.setString(UPLOAD_YN,"O");
			}//end of else
			
			cStmtObject.setString(POLICY_NUMBER,(String)alSearchCriteria.get(9));
			
			cStmtObject.setString(BATCH_TYPE,(String)alSearchCriteria.get(10));
			
            cStmtObject.setString(SORT_VAR,(String)alSearchCriteria.get(11));
			cStmtObject.setString(SORT_ORDER,(String)alSearchCriteria.get(12));
			
	       if(!"".equals(alSearchCriteria.get(13))){
	
				cStmtObject.setLong(START_NUM,Long.parseLong(alSearchCriteria.get(13).toString()));
			}else{
				cStmtObject.setLong(START_NUM,0);
			}
	       if(!"".equals(alSearchCriteria.get(14))){
	    		
				cStmtObject.setLong(END_NUM,Long.parseLong(alSearchCriteria.get(14).toString()));
			}else{
				cStmtObject.setLong(END_NUM,0);
			}
			//cStmtObject.setLong(END_NUM,(Long)alSearchCriteria.get(14));
            cStmtObject.setLong(USER_SEQ_ID,(Long)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(CURSOR,Types.OTHER);
			cStmtObject.execute();   
			rs = (java.sql.ResultSet)cStmtObject.getObject(CURSOR);
			if(rs != null){
				while (rs.next()) {
					batchVO = new BatchVO();
					batchVO.setBatchSeqID(rs.getString("ENROL_BATCH_SEQ_ID")!=null? new Long(rs.getLong("ENROL_BATCH_SEQ_ID")):null);
					batchVO.setBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NUMBER")));
					batchVO.setRecdDate(rs.getString("RECEIVED_DATE")!=null ? new Date(rs.getTimestamp("RECEIVED_DATE").getTime()):null);
					batchVO.setNbrOfPolicies(TTKCommon.checkNull(rs.getString("NO_OF_POLICIES")));
					batchVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					batchVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					batchVO.setInsuranceSeqID(rs.getString("INS_SEQ_ID")!=null? new Long(rs.getLong("INS_SEQ_ID")):null);
					batchVO.setEntryCompleteYn(TTKCommon.checkNull(rs.getString("BATCH_ENTRY_COMPLETE_YN")));
					batchVO.setBatchCompletedYN(TTKCommon.checkNull(rs.getString("BATCH_COMPLETED_YN")));
					batchVO.setClarifyStatusDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					alResultList.add(batchVO);
				}//end of while(rs.next())
			}//end of if(rs != null) 
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "batch");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "batch");
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
					log.error("Error while closing the Resultset in BatchDAOImpl getBatchList()",sqlExp);
					throw new TTKException(sqlExp, "batch");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BatchDAOImpl getBatchList()",sqlExp);
						throw new TTKException(sqlExp, "batch");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BatchDAOImpl getBatchList()",sqlExp);
							throw new TTKException(sqlExp, "batch");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "batch");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// End of getBatchList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the batchVO which contains the Batch Enrollment Details
	 * @param lngBatchSeqID the batch sequence id for which  Batch Enrollment Details has to be fetched
	 * @return BatchVO batchVO which contains the Batch Enrollment Details
	 * @exception throws TTKException
	 */
	public BatchVO getBatch(Long lngBatchSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BatchVO batchVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBatch);
			cStmtObject.setLong(1,lngBatchSeqID);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();   
			conn.commit();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				batchVO = new BatchVO();
				while (rs.next()) {
					batchVO.setBatchSeqID(rs.getString("ENROL_BATCH_SEQ_ID")!=null ? new Long(rs.getString("ENROL_BATCH_SEQ_ID")):null);
					batchVO.setInsuranceSeqID(rs.getString("INS_SEQ_ID")!=null? new Long(rs.getLong("INS_SEQ_ID")):null);
					batchVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					batchVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					batchVO.setBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NUMBER")));
					
					batchVO.setLetterRefNbr(TTKCommon.checkNull(rs.getString("LETTER_REF_NO")));
					batchVO.setLetterDate(rs.getString("LETTER_DATE")!=null ? new Date(rs.getTimestamp("LETTER_DATE").getTime()):null);
					batchVO.setRecdDate(rs.getString("RECEIVED_DATE")!=null ? new Date(rs.getTimestamp("RECEIVED_DATE").getTime()):null);
					batchVO.setInwardCompleted(rs.getString("BATCH_ENTRY_COMP_DATE")!=null ? new Date(rs.getTimestamp("BATCH_ENTRY_COMP_DATE").getTime()):null);
					batchVO.setDataCompleted(rs.getString("BATCH_COMP_DATE")!=null ? new Date(rs.getTimestamp("BATCH_COMP_DATE").getTime()):null);
					batchVO.setRecdNbrPolicy(rs.getString("NUM_OF_POLICIES_RCVD")!=null ? new Integer(rs.getInt("NUM_OF_POLICIES_RCVD")):null);
					batchVO.setEnteredNbrPolicy(rs.getString("NUM_OF_POLICY_ENTERED")!=null ? new Integer(rs.getInt("NUM_OF_POLICY_ENTERED")):null);
					batchVO.setClarifyTypeID(TTKCommon.checkNull(rs.getString("CLARIFY_GENERAL_TYPE_ID")));
					batchVO.setClarifiedDate(rs.getString("CLARIFIED_DATE")!=null ? new Date(rs.getTimestamp("CLARIFIED_DATE").getTime()):null);
					batchVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					batchVO.setEntryMode(TTKCommon.checkNull(rs.getString("ENTRY_MODE")));
					batchVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID")!=null? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
					batchVO.setBatchType(TTKCommon.checkNull(rs.getString("BATCH_TYPE")));
				}//end of while(rs.next())
			}//end of if(rs != null) 
			return batchVO;		
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "batch");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "batch");
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
					log.error("Error while closing the Resultset in BatchDAOImpl getBatch()",sqlExp);
					throw new TTKException(sqlExp, "batch");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BatchDAOImpl getBatch()",sqlExp);
						throw new TTKException(sqlExp, "batch");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BatchDAOImpl getBatch()",sqlExp);
							throw new TTKException(sqlExp, "batch");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "batch");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBatch(Long lngBatchSeqID)
	
	/**
	 * This method saves the Batch Enrollment information
	 * @param batchVO the object which contains the Batch Enrollment Details which has to be  saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public long saveBatch(BatchVO batchVO) throws TTKException
	{
		long lResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject =conn.prepareCall(strSaveBatch);

			if (batchVO.getBatchSeqID()!=null){
				cStmtObject.setLong(1,batchVO.getBatchSeqID().longValue());//ENROL_BATCH_SEQ_ID
            }//end of if (batchVO.getBatchSeqID()!=null)
			else{ 
				cStmtObject.setLong(1,0);//ENROL_BATCH_SEQ_ID
            }//end of else
			
			if (batchVO.getInsuranceSeqID()!=null){
				cStmtObject.setLong(2,batchVO.getInsuranceSeqID());//INS_SEQ_ID
            }//end of if (batchVO.getInsuranceSeqID()!=null)
			else{
				cStmtObject.setLong(2,0);//INS_SEQ_ID
            }//end of else
			cStmtObject.setString(3,batchVO.getBatchNbr());//BATCH_NUMBER
			cStmtObject.setString(4,batchVO.getLetterRefNbr());//LETTER_REF_NO
			
			if(batchVO.getLetterDate() != null){
				cStmtObject.setTimestamp(5,new Timestamp(batchVO.getLetterDate().getTime()));//LETTER_DATE
            }//end of if(batchVO.getLetterDate() != null)
			else{
				cStmtObject.setTimestamp(5, null);//LETTER_DATE
            }//end of else
			
			if(batchVO.getRecdDate() != null){
				cStmtObject.setTimestamp(6,new Timestamp(batchVO.getRecdDate().getTime()));//RECEIVED_DATE
            }//end of if(batchVO.getRecdDate() != null)
			else{
				cStmtObject.setTimestamp(6, null);//RECEIVED_DATE
            }//end of else
			
			if (batchVO.getRecdNbrPolicy()!=null){
				cStmtObject.setInt(7,batchVO.getRecdNbrPolicy());//NUM_OF_POLICIES_RCVD
            }//end of if (batchVO.getRecdNbrPolicy()!=null)
			else{ 
				cStmtObject.setInt(7,0);//NUM_OF_POLICIES_RCVD
            }//end of else
			cStmtObject.setString(8, batchVO.getClarifyTypeID());//CLARIFY_GENERAL_TYPE_ID
			
			if(batchVO.getClarifiedDate() != null){
				cStmtObject.setTimestamp(9,new Timestamp(batchVO.getClarifiedDate().getTime()));//CLARIFIED_DATE
            }//end of if(batchVO.getClarifiedDate() != null)
			else{
				cStmtObject.setTimestamp(9, null);//CLARIFIED_DATE
            }//end of else
			cStmtObject.setString(10,batchVO.getRemarks());//REMARKS
			
			if(batchVO.getOfficeSeqID() != null){
				cStmtObject.setLong(11,batchVO.getOfficeSeqID().longValue());
			}//end of if(batchVO.getOfficeSeqID() != null)
			else{
				cStmtObject.setLong(11,0);
			}//end of else
			cStmtObject.setString(12,batchVO.getBatchType());//ADDED_BY
			cStmtObject.setLong(13,batchVO.getUpdatedBy().longValue());//ADDED_BY
			cStmtObject.registerOutParameter(1,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.registerOutParameter(3,Types.VARCHAR);//ROW_PROCESSED
			cStmtObject.registerOutParameter(14,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute(); 
		///	lResult = cStmtObject.getInt(1);//ENROL_BATCH_SEQ_ID
			lResult = cStmtObject.getLong(1);//ENROL_BATCH_SEQ_ID
			conn.commit();
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "batch");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "batch");
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
        			log.error("Error while closing the Statement in BatchDAOImpl saveBatch()",sqlExp);
        			throw new TTKException(sqlExp, "batch");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BatchDAOImpl saveBatch()",sqlExp);
        				throw new TTKException(sqlExp, "batch");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "batch");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lResult;
	}// End of saveBatch(BatchVO batchVO)
	
	/**
	 * This method deletes the batch Enrollment infomation for the given sequence id's
	 * @param alDeleteBatch the details of the batch sequence id's which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exceptionthrows TTKException
	 */
	public int deleteBatch(ArrayList alDeleteBatch) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteBatch);
			cStmtObject.setString(1, (String)alDeleteBatch.get(0));//FLAG BATCH
			cStmtObject.setString(2, (String)alDeleteBatch.get(1));//CONCATENATED STRING OF ENROL_BATCH_SEQ_IDS
			if (alDeleteBatch.get(2)!=null)
			{
				cStmtObject.setLong(3,(Long)alDeleteBatch.get(2));// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			}//end of if (alDeleteBatch.get(2)!=null)
			else
			{
				cStmtObject.setLong(3,0);// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			}// end of else
			cStmtObject.setString(4,alDeleteBatch.get(3)!=null ? (String)alDeleteBatch.get(3):null);//-- Mode can be 'ENM','END'
			cStmtObject.setString(5,alDeleteBatch.get(4)!=null ? (String)alDeleteBatch.get(4):null);//-- 'IP',IG,CP,NC
			cStmtObject.setLong(6,(Long)alDeleteBatch.get(5));//ADDED_BY
			cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.setString(8, null);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
			conn.commit();
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "batch");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "batch");
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
        			log.error("Error while closing the Statement in BatchDAOImpl deleteBatch()",sqlExp);
        			throw new TTKException(sqlExp, "batch");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BatchDAOImpl deleteBatch()",sqlExp);
        				throw new TTKException(sqlExp, "batch");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "batch");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//End of deleteBatch(ArrayList alDeleteBatch)
    
	/**
	 * This method saves the Batch policy information 
	 * @param lngEnrollBatchSeqID long value which contains the enroll batch seq id which has to be updated
     * @param lngUserSeqID long value for which user has logged in
     * @param strFlag String Object contains Flag for InwardComplete/DataEntryComplete - INW/BAT
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveBatchComplete(long lngEnrollBatchSeqID,long lngUserSeqID,String strFlag) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try {
            conn = ResourceManager.getConnection();
            conn.setAutoCommit(false);
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBatchComplete);
            cStmtObject.setLong(1,lngEnrollBatchSeqID);
            cStmtObject.setString(2,strFlag);
            cStmtObject.setLong(3,lngUserSeqID);
            cStmtObject.registerOutParameter(4, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(4);//ROWS_PROCESSED
            conn.commit();
        }//end of try
        catch (SQLException sqlExp) 
        {
            throw new TTKException(sqlExp, "batch");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp) 
        {
            throw new TTKException(exp, "batch");
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
        			log.error("Error while closing the Statement in BatchDAOImpl saveBatchComplete()",sqlExp);
        			throw new TTKException(sqlExp, "batch");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in BatchDAOImpl saveBatchComplete()",sqlExp);
        				throw new TTKException(sqlExp, "batch");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "batch");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of saveBatchComplete(long lngEnrollBatchSeqID,long lngUserSeqID,String strFlag)
	
	/**
	 * This method returns the Arraylist of BatchVO's  which contains the details of Softcopy Batch
	 * This method used in Reports->Enrollment->IOB Report
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of BatchVO object which contains the details of Softcopy Batch
	 * @exception throws TTKException
	 */
	public ArrayList getSoftcopyBatchList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		BatchVO batchVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSoftcopyBatchList);
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
				while (rs.next()) {
					batchVO = new BatchVO();
					batchVO.setBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NUMBER")));
					batchVO.setRecdDate(rs.getString("BATCH_DATE")!=null ? new Date(rs.getTimestamp("BATCH_DATE").getTime()):null);
					alResultList.add(batchVO);
				}//end of while(rs.next())
			}//end of if(rs != null) 
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "batch");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "batch");
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
					log.error("Error while closing the Resultset in BatchDAOImpl getSoftcopyBatchList()",sqlExp);
					throw new TTKException(sqlExp, "batch");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BatchDAOImpl getSoftcopyBatchList()",sqlExp);
						throw new TTKException(sqlExp, "batch");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BatchDAOImpl getSoftcopyBatchList()",sqlExp);
							throw new TTKException(sqlExp, "batch");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "batch");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getSoftcopyBatchList(ArrayList alSearchCriteria)
	
	/**
	 * This method returns the Arraylist of CardBatchVO's  which contains the details of ID Card Printing Batch
	 * This method used in Support->Customized List->HDFC Certificate Generation
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of CardBatchVO object which contains the details of ID Card Printing Batch
	 * @exception throws TTKException
	 */
	public ArrayList getCardBatchList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		CardBatchVO cardBatchVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCardBatchList);
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
				while (rs.next()) {
					cardBatchVO = new CardBatchVO();
					cardBatchVO.setBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NO")));
					cardBatchVO.setBatchDate(rs.getString("BATCH_DATE")!=null ? new Date(rs.getTimestamp("BATCH_DATE").getTime()):null);
					cardBatchVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					cardBatchVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
					cardBatchVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID") != null? new Long(rs.getLong("POLICY_GROUP_SEQ_ID")):null);
					alResultList.add(cardBatchVO);
				}//end of while(rs.next())
			}//end of if(rs != null) 
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "batch");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "batch");
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
					log.error("Error while closing the Resultset in BatchDAOImpl getCardBatchList()",sqlExp);
					throw new TTKException(sqlExp, "batch");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in BatchDAOImpl getCardBatchList()",sqlExp);
						throw new TTKException(sqlExp, "batch");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in BatchDAOImpl getCardBatchList()",sqlExp);
							throw new TTKException(sqlExp, "batch");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "batch");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getCardBatchList(ArrayList alSearchCriteria)
	
}// End of BatchDAOImpl
