/**
 * @ (#) ClaimBatchDAOImpl.java July 7, 2015
 * Project 	     : ProjectX
 * File          : ClaimBatchDAOImpl.java
 * Author        : Nagababu K
 * Company       : RCS Technologies
 * Date Created  : July 7, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.claims;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.claims.BatchVO;

public class ClaimBatchDAOImpl implements BaseDAO, Serializable{
	
	private static final long serialVersionUID = -1143723325198093673L;

	private static Logger log = Logger.getLogger(ClaimBatchDAOImpl.class );
	
	private static final String strSaveClaimBatchDetails="{CALL CLAIM_PKG_SAVE_CLM_BATCH_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetClaimBatchDetails="{CALL CLAIM_PKG_SELECT_CLM_BATCH_DETAILS(?,?,?)}";
	private static final String strAddRequestedAmount="{CALL CLAIM_PKG_ADD_BATCH_CLMS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetClaimAmountDetails="{CALL CLAIM_PKG.SELECT_CLAIM(?,?)}";
	private static final String strGetClaimBatchList="{CALL CLAIM_PKG_SELECT_CLM_BATCH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteInvoiceNO="{CALL CLAIM_PKG_DELETE_BATCH_CLAIMS(?,?,?)}";
	private static final String strgetPartnersList ="SELECT P.PTNR_SEQ_ID,P.PARTNER_NAME FROM APP.TPA_PARTNER_INFO P JOIN APP.TPA_PARTNER_EMPANEL_STATUS E ON (P.PTNR_SEQ_ID=E.PTNR_SEQ_ID) WHERE E.EMPANEL_STATUS_TYPE_ID = 'EMP'";
   
    public long saveClaimBatchDetails(BatchVO batchVO) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	long batchSeqID;
    	try{
    		
    		conn = ResourceManager.getConnection();
    		cStmtObject = conn.prepareCall(strSaveClaimBatchDetails);
    		cStmtObject.setLong(1,batchVO.getBatchSeqID()==null?0:batchVO.getBatchSeqID());
    		cStmtObject.setString(2,batchVO.getBatchNO());
    		if("CNH".equals(batchVO.getClaimType())&&!"".equals(TTKCommon.checkNull(batchVO.getProviderID()))){
    			cStmtObject.setLong(3,Long.parseLong(batchVO.getProviderID()));
    			cStmtObject.setString(17,null);
    		}else{
    			cStmtObject.setLong(3,0);
    			cStmtObject.setString(17,null);
    		}
    		cStmtObject.setString(4,null);
    		cStmtObject.setTimestamp(5,new java.sql.Timestamp(TTKCommon.getOracleDateWithTime(batchVO.getBatchReceiveDate(),batchVO.getReceivedTime(),batchVO.getReceiveDay()).getTime()));
    		cStmtObject.setLong(6,batchVO.getNoOfClaimsReceived()==null?0:batchVO.getNoOfClaimsReceived());
    		cStmtObject.setDouble(7,batchVO.getTotalAmount().doubleValue());
    		cStmtObject.setLong(8,Long.parseLong(batchVO.getVidalBranchName()));
    		cStmtObject.setString(9,batchVO.getBatchStatus());
    		cStmtObject.setString(10,null);//cStmtObject.setString(10,batchVO.getBenefitType());
    		cStmtObject.setString(11,batchVO.getClaimType());
    		cStmtObject.setString(12,batchVO.getSubmissionType());
    		cStmtObject.setString(13,batchVO.getTotalAmountCurrency());
    		cStmtObject.setString(14,batchVO.getModeOfClaim());
    		cStmtObject.setString(15,batchVO.getOverrideYN());
    		cStmtObject.setString(16,batchVO.getNetWorkType());
    		cStmtObject.setString(18,batchVO.getClaimFrom());
    		cStmtObject.setLong(19,batchVO.getAddedBy());
    		cStmtObject.setString(20,batchVO.getProcessType());
    		cStmtObject.setString(21,batchVO.getPaymentTo());
    		cStmtObject.setString(22,batchVO.getPartnerName());
    		cStmtObject.setString(23,batchVO.getProviderLisenceNO());
    		cStmtObject.registerOutParameter(1,Types.BIGINT);
    		cStmtObject.registerOutParameter(2, Types.VARCHAR);
    		cStmtObject.registerOutParameter(23, Types.VARCHAR);
    		cStmtObject.execute();
    		batchSeqID = cStmtObject.getLong(1);
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
    				log.error("Error while closing the Connection in ClaimBatchDAOImpl saveClaimBatchDetails()",sqlExp);
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
    					log.error("Error while closing the Connection in ClaimBatchDAOImpl saveClaimBatchDetails()",sqlExp);
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
    	return batchSeqID;
    }//end of saveClaimBatchDetails(BatchVO)
    
    /**
     * This method Create teh Claim Cash Benefit Details
     * @param lngPrvClaimsNbr Long object which contains the previous claims number
     * @return Long lngAddedBy Long object which contains the user seq id
     * @exception throws TTKException
     */
    public long deleteInvoiceNO(String batchSeqID,String claimSeqID) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	long rowPro;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = conn.prepareCall(strDeleteInvoiceNO);
    		cStmtObject.setLong(1,Long.parseLong(claimSeqID));
    		cStmtObject.setLong(2,Long.parseLong(batchSeqID));
    		cStmtObject.registerOutParameter(3,Types.BIGINT);
    		cStmtObject.execute();
    		rowPro = cStmtObject.getLong(3);
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
    				log.error("Error while closing the Connection in ClaimBatchDAOImpl deleteInvoiceNO()",sqlExp);
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
    					log.error("Error while closing the Connection in ClaimBatchDAOImpl deleteInvoiceNO()",sqlExp);
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
    	return rowPro;
    }//end of deleteInvoiceNO(String batchSeqID,String claimSeqID)
    
    /**
     * This method Create teh Claim Cash Benefit Details
     * @param lngPrvClaimsNbr Long object which contains the previous claims number
     * @return Long lngAddedBy Long object which contains the user seq id
     * @exception throws TTKException
     */
    public long addClaimDetails(BatchVO batchVO) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	long batchSeqID;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddRequestedAmount);
    		cStmtObject.setLong(1,batchVO.getClaimSeqID()==null?0:batchVO.getClaimSeqID());
    		cStmtObject.setLong(2,batchVO.getBatchSeqID()==null?0:batchVO.getBatchSeqID());
    		 if(!"".equals(TTKCommon.checkNull(batchVO.getPreviousClaimNO()))){
    			 cStmtObject.setLong(3,Long.parseLong(batchVO.getPreviousClaimNO()));
    			             } 
    		 else{
    			 cStmtObject.setLong(3, 0);
    		 }
    		
    		cStmtObject.setTimestamp(4,new java.sql.Timestamp(TTKCommon.getOracleDateWithTime(batchVO.getBatchReceiveDate(),batchVO.getReceivedTime(),batchVO.getReceiveDay()).getTime()));
    		cStmtObject.setString(5,batchVO.getClaimType());
    		cStmtObject.setString(6,null);//cStmtObject.setString(6,batchVO.getBenefitType());
    		cStmtObject.setString(7,batchVO.getProviderInvoiceNO());
    		cStmtObject.setDouble(8,batchVO.getRequestedAmount().doubleValue());
    		cStmtObject.setDouble(9,batchVO.getAddedTotalAmount().doubleValue());
    		cStmtObject.setString(10,batchVO.getProviderID());
    		if(batchVO.getEnrollmentSeqID()==null)cStmtObject.setLong(11,0);
    		else cStmtObject.setLong(11,batchVO.getEnrollmentSeqID());
    		cStmtObject.setString(12,batchVO.getEnrollmentID());
    		cStmtObject.setString(13,batchVO.getResubmissionRemarks());
    		cStmtObject.setLong(14,batchVO.getAddedBy());
    		cStmtObject.registerOutParameter(1,Types.BIGINT);
    		cStmtObject.registerOutParameter(2, Types.BIGINT);
    		cStmtObject.registerOutParameter(3, Types.BIGINT);
    		cStmtObject.registerOutParameter(15,Types.BIGINT);
    		cStmtObject.execute();
    		batchSeqID = cStmtObject.getLong(2);
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
    				log.error("Error while closing the Connection in ClaimBatchDAOImpl addClaimDetails()",sqlExp);
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
    					log.error("Error while closing the Connection in ClaimBatchDAOImpl addClaimDetails()",sqlExp);
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
    	return batchSeqID;
    }//end of addClaimDetails(BatchVO)
    
    /**
     * This method Create teh Claim Cash Benefit Details
     * @param lngPrvClaimsNbr Long object which contains the previous claims number
     * @return Long lngAddedBy Long object which contains the user seq id
     * @exception throws TTKException
     */
    public BatchVO getClaimAmountDetails(String claimSeqID) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet 	rs=null;
    	BatchVO batchVO=new BatchVO();
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimAmountDetails);
    		cStmtObject.setString(1,claimSeqID);
    		cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
    		cStmtObject.execute();
    	rs =(ResultSet)cStmtObject.getObject(2);
    	if(rs!=null){
    		if(rs.next()){
    			batchVO.setRequestedAmount(rs.getBigDecimal("REQUESTED_AMOUNT"));
    			batchVO.setProviderInvoiceNO(rs.getString("INVOICE_NUMBER"));
    		}
    	}
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
					log.error("Error while closing the Resultset in ClaimBatchDAOImpl getClaimAmountDetails()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimBatchDAOImpl getClaimAmountDetails()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimBatchDAOImpl getClaimAmountDetails()",sqlExp);
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
    	return batchVO;
    }//end of getClaimAmountDetails(String claimSeqID)
    /**
     * This method returns the Arraylist of ClaimBenefitVO's  which contains Claim Benefit Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimBenefitVO object which contains Claim Benefit details
     * @exception throws TTKException
     */
    public Object[] getClaimBatchDetails(long batchSeqID) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet batchVORs = null;
    	ResultSet batchDeatilsRs = null;
    	Object[] batchDeatils=new Object[2];
    	BatchVO batchVO = new BatchVO();
    	ArrayList<BatchVO> listOfAmounts=new ArrayList<BatchVO>();
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimBatchDetails);			
			cStmtObject.setLong(1,batchSeqID);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();
			batchVORs = (java.sql.ResultSet)cStmtObject.getObject(2);
			batchDeatilsRs = (java.sql.ResultSet)cStmtObject.getObject(3);
			
			if(batchVORs != null){
				if(batchVORs.next()){
					
					batchVO.setBatchSeqID(batchVORs.getLong("CLM_BATCH_SEQ_ID"));
					batchVO.setBatchNO(batchVORs.getString("BATCH_NO"));
					batchVO.setProviderName(batchVORs.getString("PROVIDER_NAME"));
					batchVO.setProviderID(batchVORs.getString("hosp_seq_id"));
					batchVO.setProviderLisenceNO(batchVORs.getString("SENDER_ID"));
					batchVO.setNetWorkType(batchVORs.getString("NETWORK_YN"));
					batchVO.setNoOfClaimsReceived(batchVORs.getInt("RECORD_COUNT"));
					batchVO.setTotalAmount(batchVORs.getBigDecimal("BATCH_TOT_AMOUNT"));
					batchVO.setTotalAmountCurrency(batchVORs.getString("CURRENCY_TYPE"));
					batchVO.setVidalBranchName(batchVORs.getString("TPA_OFFICE_AEQ_ID"));
					batchVO.setBatchStatus(batchVORs.getString("BATCH_STATUS_TYPE"));
					batchVO.setBenefitType(batchVORs.getString("BENEFIT_TYPE"));
					batchVO.setClaimType(batchVORs.getString("CLM_TYPE_GEN_TYPE_ID"));
					batchVO.setSubmissionType(batchVORs.getString("SUBMISSION_TYPE_ID"));
					if(batchVORs.getString("RECEIVED_DATE") != null){
						batchVO.setBatchReceiveDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date(batchVORs.getTimestamp("RECEIVED_DATE").getTime())));
						batchVO.setReceivedTime(TTKCommon.getFormattedDateHour(new Date(batchVORs.getTimestamp("RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(batchVORs.getTimestamp("RECEIVED_DATE").getTime())).split(" ")[1]:"");
						batchVO.setReceiveDay(TTKCommon.getFormattedDateHour(new Date(batchVORs.getTimestamp("RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(batchVORs.getTimestamp("RECEIVED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(batchVORs.getString("PAT_RECEIVED_DATE") != null)
					batchVO.setModeOfClaim(batchVORs.getString("SOURCE_TYPE_ID"));
					batchVO.setCompletedYN(batchVORs.getString("COMPLETED_YN"));
					batchVO.setClaimFrom(batchVORs.getString("CLAIM_FROM"));
					batchVO.setProcessType(batchVORs.getString("submission_catogory"));
					batchVO.setPartnerName(batchVORs.getString("partnername"));
					batchVO.setPaymentTo(batchVORs.getString("payment_to"));
					batchVO.setEclaimOverrideYN(batchVORs.getString("eclm_override_yn"));
					
				}//end of if(batchVORs != null)
			}//end of if(batchVORs != null)
			if(batchDeatilsRs != null){
				while(batchDeatilsRs.next()){
					
					listOfAmounts.add(new BatchVO(batchDeatilsRs.getLong("CLAIM_SEQ_ID"),batchDeatilsRs.getString("INVOICE_NUMBER"),batchDeatilsRs.getString("CLAIM_NUMBER"),batchDeatilsRs.getBigDecimal("REQUESTED_AMOUNT"),batchDeatilsRs.getString("PARENT_CLAIM_NUMBER"),batchDeatilsRs.getLong("PARENT_CLAIM_SEQ_ID"),batchDeatilsRs.getString("TPA_ENROLLMENT_ID"),batchDeatilsRs.getLong("MEMBER_SEQ_ID"),batchDeatilsRs.getString("REMARKS")));
				}//end of if(batchDeatilsRs != null)
			}//end of if(batchDeatilsRs != null)
			batchDeatils[0]=batchVO;
			batchDeatils[1]=listOfAmounts;
    		return batchDeatils;
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
					if (batchVORs != null) batchVORs.close();
					if (batchDeatilsRs != null) batchDeatilsRs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimBatchDAOImpl getClaimBatchDetails()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimBatchDAOImpl getClaimBatchDetails()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimBatchDAOImpl getClaimBatchDetails()",sqlExp);
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
				batchVORs = null;
				batchDeatilsRs=null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimBatchDetails(long batchSeqID)
    /**
     * This method returns the Arraylist of ClaimBenefitVO's  which contains Claim Benefit Details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimBenefitVO object which contains Claim Benefit details
     * @exception throws TTKException
     */
    public ArrayList<Object> getClaimBatchList(ArrayList<Object> alSearchCriteria) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	Collection<Object> alResultList = new ArrayList<Object>();
    	BatchVO batchVO = null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strGetClaimBatchList);			
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));			
			cStmtObject.setString(9,(String)alSearchCriteria.get(10));
			cStmtObject.setString(10,(String)alSearchCriteria.get(11));
			cStmtObject.setLong(11,Long.parseLong((String)alSearchCriteria.get(12)));
			cStmtObject.setLong(12,Long.parseLong((String) alSearchCriteria.get(13)));
			cStmtObject.setLong(13,(Long)alSearchCriteria.get(8));
			cStmtObject.setString(14,(String)alSearchCriteria.get(9));
			cStmtObject.registerOutParameter(15,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(15);
			if(rs != null){
				while(rs.next()){
					batchVO = new  BatchVO();
					if(rs.getString("CLM_BATCH_SEQ_ID") != null){
						batchVO.setBatchSeqID(new Long(rs.getLong("CLM_BATCH_SEQ_ID")));
					}//end of if(rs.getString("CLM_BATCH_SEQ_ID") != null)
					batchVO.setBatchNO(TTKCommon.checkNull(rs.getString("BATCH_NO")));
					batchVO.setProviderName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					batchVO.setClaimType(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
					batchVO.setBatchReceiveDate(TTKCommon.convertDateAsString("dd-MM-yyyy", rs.getDate("RECEIVED_DATE")));
					batchVO.setSubmissionType(rs.getString("SUBMISSION_TYPE"));
					batchVO.setProcessType(rs.getString("submission_catogory"));
					alResultList.add(batchVO);
				}//end of if(rs != null)
			}//end of if(rs != null)
    		return (ArrayList<Object>)alResultList;
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
					log.error("Error while closing the Resultset in ClaimBenefitDAOImpl getClaimBatchList()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimBenefitDAOImpl getClaimBatchList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimBenefitDAOImpl getClaimBatchList()",sqlExp);
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
    }//end of getClaimBatchList(ArrayList alSearchCriteria)

	public HashMap<String,String> getPartnersList() throws TTKException {
		
		Connection conn = null;
		ResultSet rs = null;
		HashMap<String,String> partners=new HashMap<>();
		PreparedStatement pStmt 		= 	null;
        try {
            conn = ResourceManager.getConnection();
            pStmt=conn.prepareStatement(strgetPartnersList);
            rs = pStmt.executeQuery();
			if(rs != null){
				while(rs.next()){
					partners.put(rs.getString("PTNR_SEQ_ID"),rs.getString("PARTNER_NAME"));
				}//end of while(rs.next())
			}//end of if(rs != null)
			return partners;
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
					log.error("Error while closing the Resultset in ClaimBatchDAOImpl getPartnersList()",sqlExp);
					throw new TTKException(sqlExp, "batch");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimBatchDAOImpl getPartnersList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimBatchDAOImpl getPartnersList()",sqlExp);
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
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getNetworkTypeList()
	
}//end of ClaimBatchDAOImpl
