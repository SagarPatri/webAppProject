/**
 *   @ (#) ChequeDAOImpl.java June 07, 2006
 *   Project      : TTK HealthCare Services
 *   File         : ChequeDAOImpl.java
 *   Author       : Balakrishna E
 *   Company      : Span Systems Corporation
 *   Date Created : June 07, 2006
 *
 *   @author       :  Balakrishna E
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dao.impl.finance;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;

import oracle.jdbc.driver.OracleTypes;

import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.ReportVO;
import com.ttk.dto.finance.BankAddressVO;
import com.ttk.dto.finance.ChequeDetailVO;
import com.ttk.dto.finance.ChequeVO;

import org.dom4j.Document;
import org.dom4j.Node;

import java.util.List;

public class ChequeDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(ChequeDAOImpl.class);
	
	private static final String strChequeList = "{CALL fin_app.CHEQUE_INFO_PKG_SELECT_CHQ_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strChequeDetail = "{CALL fin_app.CHEQUE_INFO_PKG_SELECT_CHQ_DETAIL(?,?,?,?,?)}";
	private static final String strSaveCheque = "{CALL fin_app.CHEQUE_INFO_PKG_CHQ_DETAILS_SAVE(?,?,?,?,?,?)}";
	private static final String strUpdateChequeInfo = "{CALL FIN_MAINTENENACE_PKG.CHEQUE_NUM_DATE_CHANGE(?,?,?,?,?,?,?,?)}";
	private static final String strSaveChequeXml ="{CALL fin_app.FLOAT_ACCOUNTS_PKG_chq_reissue_bulk_upload(?)}";
	private static final String strTotalClaimsCount = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_chq_re_issue_current_file_log(?,?)}";
	
//bikki
	private static final String strLogReport ="{CALL fin_app.FLOAT_ACCOUNTS_PKG_cheque_batch_log(?,?,?,?)}";
	private static final String strFloatLog ="{CALL fin_app.FLOAT_ACCOUNTS_PKG_fin_logs(?,?)}";
	
	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO'S object's which contains the details of the cheque
	 * @exception throws TTKException
	 */
	public ArrayList getChequeList(ArrayList alSearchCriteria) throws TTKException
	{	System.out.println("************* alCheque *****************************");
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeVO chequeVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strChequeList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			
			if((String)alSearchCriteria.get(7)==null || alSearchCriteria.get(7).equals("")){
				cStmtObject.setLong(8,0);
			}
			else{
				cStmtObject.setLong(8,(Long.parseLong(alSearchCriteria.get(7).toString())));
			}
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			if((String)alSearchCriteria.get(9)==null || alSearchCriteria.get(9).equals("")){
				cStmtObject.setLong(10,0);
			}
			else{
				cStmtObject.setLong(10, (Long.parseLong(alSearchCriteria.get(9).toString())));
			}
			cStmtObject.setString(11,(String)alSearchCriteria.get(10));
			cStmtObject.setString(12,(String)alSearchCriteria.get(11));
			cStmtObject.setString(13,(String)alSearchCriteria.get(12));
			cStmtObject.setString(14,(String)alSearchCriteria.get(15));
			cStmtObject.setString(15,(String)alSearchCriteria.get(16));
			if((String)alSearchCriteria.get(17)==null || alSearchCriteria.get(17).equals("")){
				cStmtObject.setLong(16,0);
			}
			else{
				cStmtObject.setLong(16,(Long.parseLong(alSearchCriteria.get(17).toString())));
			}
			if((String)alSearchCriteria.get(18)==null || alSearchCriteria.get(18).equals("")){
				cStmtObject.setLong(17,0);
			}
			else{
				cStmtObject.setLong(17,(Long.parseLong(alSearchCriteria.get(18).toString())));
			}
			cStmtObject.setLong(18,(Long)alSearchCriteria.get(13));
			cStmtObject.setString(19,(String)alSearchCriteria.get(14));
			cStmtObject.registerOutParameter(20,Types.OTHER);
			cStmtObject.execute();
			conn.commit();
			rs = (java.sql.ResultSet)cStmtObject.getObject(20);
			System.out.println("************* lets start *****************************");
			if(rs != null){
				while(rs.next()){
					chequeVO = new ChequeVO();
					System.out.println(" **************** records exist start *****************");
					if(rs.getString("CLAIMS_CHK_SEQ_ID") != null){
						chequeVO.setSeqID(new Long(rs.getString("CLAIMS_CHK_SEQ_ID")));
					}//end of if(rs.getString("CLAIMS_CHK_SEQ_ID") != null)

					chequeVO.setChequeNo(TTKCommon.checkNull(rs.getString("CHECK_NUM")));
					chequeVO.setFloatAcctNo(TTKCommon.checkNull(rs.getString("FLOAT_ACCOUNT_NUMBER")));
					chequeVO.setStatusDesc(TTKCommon.checkNull(rs.getString("CHECK_STATUS")));
					if(rs.getTimestamp("CHECK_DATE") != null){
						chequeVO.setChequeDate(new Date(rs.getTimestamp("CHECK_DATE").getTime()));
					}//end of if(rs.getTimestamp("CHECK_DATE") != null)

					chequeVO.setClaimSettNo(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NO")));
					chequeVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
					chequeVO.setInsCompName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					chequeVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					chequeVO.setPaymentSeqId(new Long(rs.getString("PAYMENT_SEQ_ID")));
					System.out.println(" **************** records exist end *****************");	

					alResultList.add(chequeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			System.out.println("dao size is : "+alResultList.size());
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		 {
			throw new TTKException(sqlExp, "cheque");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "cheque");
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
					log.error("Error while closing the Resultset in ChequeDAOImpl getChequeList()",sqlExp);
					throw new TTKException(sqlExp, "cheque");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ChequeDAOImpl getChequeList()",sqlExp);
						throw new TTKException(sqlExp, "cheque");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ChequeDAOImpl getChequeList()",sqlExp);
							throw new TTKException(sqlExp, "cheque");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "cheque");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getChequeList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ChequeDetailVO which contains the Cheque Detail information
	 * @param lngSeqID long value which contains cheque seq id to get the Cheque Detail information
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return ChequeDetailVO this contains the Cheque Detail information
	 * @exception throws TTKException
	 */
	public ChequeDetailVO getChequeDetail(Long lngSeqID, Long lngPaymentSeqId, Long lngFloatSeqID, Long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeDetailVO chequeDetailVO = null;
        BankAddressVO bankAddressVO = null;

        log.info("Inside getChequeDetail method");
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strChequeDetail);
			cStmtObject.setLong(1, lngSeqID.longValue());
			if(lngPaymentSeqId!=0)
			{
				cStmtObject.setLong(2, lngPaymentSeqId.longValue());
			}
			else
			{
				cStmtObject.setLong(2, 0);
			}
            if(lngFloatSeqID!=null)
            {
                cStmtObject.setLong(3, lngFloatSeqID.longValue());
            }//end of if(lngFloatSeqID!=null)
            else
            {
                cStmtObject.setLong(3, 0);
            }//end of else
			cStmtObject.setLong(4, lngUserSeqID.longValue());
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(5,Types.OTHER);
			cStmtObject.execute();
			conn.commit();
			rs = (java.sql.ResultSet)cStmtObject.getObject(5);
			if(rs != null){
				while(rs.next()){
					chequeDetailVO = new ChequeDetailVO();
					bankAddressVO = new BankAddressVO();
                    if(lngFloatSeqID==null)
                    {
    					chequeDetailVO.setChequeNo(TTKCommon.checkNull(rs.getString("CHECK_NUM")));
    					if(rs.getString("CLAIMS_CHK_SEQ_ID") !=null) {
    						chequeDetailVO.setSeqID(new Long(rs.getString("CLAIMS_CHK_SEQ_ID")));
    					}//end of if(rs.getString("CLAIMS_CHK_SEQ_ID") !=null)

                        if(rs.getString("CHECK_AMOUNT") != null) {
                            chequeDetailVO.setChequeAmt(rs.getString("CHECK_AMOUNT"));
                        }//end of if(rs.getString("CHECK_AMOUNT") != null)
                        chequeDetailVO.setFloatAcctNo(TTKCommon.checkNull(rs.getString("FLOAT_ACCOUNT_NUMBER")));
                        chequeDetailVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("CHECK_STATUS")));

                        if(TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSI"))
                        {
                        	 if(rs.getTimestamp("CHECK_DATE") !=null)
                        	 {
                                chequeDetailVO.setClearedDate(rs.getTimestamp("CHECK_DATE"));
                             }//end of if(rs.getTimestamp("CLEARED_DATE") !=null)
                        }//end of if(TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSI"))

                        if(TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSV")||TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSR"))
                        {
                        	if(rs.getTimestamp("VOID_DATE") !=null)
                       	 	{
                               chequeDetailVO.setClearedDate(rs.getTimestamp("VOID_DATE"));
                            }//end of if(rs.getTimestamp("CLEARED_DATE") !=null)
                        }//end of if(TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSV")||TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSR"))
                        if(TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSC"))
                        {
                        	 if(rs.getTimestamp("CLEARED_DATE") !=null)
                        	 {
                                chequeDetailVO.setClearedDate(rs.getTimestamp("CLEARED_DATE"));
                             }//end of if(rs.getTimestamp("CLEARED_DATE") !=null)
                        }//end of if(TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSC"))
 			//Changes as per STALE...................
                        if(TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSS"))
                        {
                        	 if(rs.getTimestamp("STALE_DATE") !=null)
                        	 {
                                chequeDetailVO.setClearedDate(rs.getTimestamp("STALE_DATE"));
                                
                             }//end of if(rs.getTimestamp("CLEARED_DATE") !=null)
                        }//end of if(TTKCommon.checkNull(rs.getString("CHECK_STATUS")).equalsIgnoreCase("CSS"))
                    }//end of if(lngFloatSeqID!=null)
                    else
                    {
                    	chequeDetailVO.setSeqID(lngSeqID);
                    }//end of else

                    chequeDetailVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
                    chequeDetailVO.setPolicyType(TTKCommon.checkNull(rs.getString("ENROL_DESCRIPTION")));
                    if(rs.getString("APPROVED_AMOUNT") != null) {
                    	chequeDetailVO.setApprovedAmtClm(rs.getString("APPROVED_AMOUNT"));
                    	
                       // chequeDetailVO.setClaimAmt(new BigDecimal(rs.getString("APPROVED_AMOUNT")));    setApprovedAmtClm
                    }//end of if(rs.getString("APPROVED_AMOUNT") != null)

					if(rs.getTimestamp("CHECK_DATE") != null){
						chequeDetailVO.setChequeDate(new Date(rs.getTimestamp("CHECK_DATE").getTime()));
					}//end of if(rs.getTimestamp("CHECK_DATE") != null)
		
					/* ADDED AS PER STALE CHANGE REQUEST
					 * 
					 *TTKCommon.getDate()if(rs.getTimestamp("CURRENT_DATE") != null) {
					chequeDetailVO.setCurentDate(new Date(rs.getTimestamp("CURRENT_DATE").getTime()));
				}//end of if(rs.getTimestamp("CURRENT_DATE") != null)
					*/ 
					
						chequeDetailVO.setCurrentDate(TTKCommon.getDate());
				
					chequeDetailVO.setInFavorOf(TTKCommon.checkNull(rs.getString("IN_FAVOUR_OF")));
					chequeDetailVO.setClaimSettNo(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NO")));

				    //added as per KOC 1175 Eft Change Request
					chequeDetailVO.setPaymentType(TTKCommon.checkNull(rs.getString("PAYMENT_METHOD")));
			        //added as per KOC 1175 Eft Change Request
					if(rs.getTimestamp("CLAIM_APRV_DATE") != null) {
						chequeDetailVO.setApprDate(new Date(rs.getTimestamp("CLAIM_APRV_DATE").getTime()));
					}//end of if(rs.getTimestamp("CLAIM_APRV_DATE") != null)
					
					chequeDetailVO.setConverSionRatePayment(TTKCommon.checkNull(rs.getString("chq_exc_rate")));
					chequeDetailVO.setConverSionRateClaim(TTKCommon.checkNull(rs.getString("claim_exc_rate")));
					
					chequeDetailVO.setEnrollID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					chequeDetailVO.setClaimantName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					chequeDetailVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					chequeDetailVO.setInsCompName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					chequeDetailVO.setInsCompCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					chequeDetailVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					chequeDetailVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					chequeDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("COMMENTS")));
                    bankAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS1")));
                    bankAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS2")));
                    bankAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS3")));
                    bankAddressVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY")));
                    bankAddressVO.setStateName(TTKCommon.checkNull(rs.getString("STATE")));
                    bankAddressVO.setCountryName(TTKCommon.checkNull(rs.getString("COUNTRY")));
                    bankAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("PINCODE")));
                    bankAddressVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
                    bankAddressVO.setOffPhone1(TTKCommon.checkNull(rs.getString("PHONE1")));
                    bankAddressVO.setOffPhone2(TTKCommon.checkNull(rs.getString("PHONE2")));
                    bankAddressVO.setHomePhone(TTKCommon.checkNull(rs.getString("HOME_PHONE")));
                    bankAddressVO.setMobile(TTKCommon.checkNull(rs.getString("MOB_NUM")));
                    bankAddressVO.setFax(TTKCommon.checkNull(rs.getString("FAX_NO")));
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    chequeDetailVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
                    chequeDetailVO.setBankAddressVO(bankAddressVO);
					}//end of while(rs.next())
			}//end of if(rs != null)
			return chequeDetailVO;
		}// end of try
		catch (SQLException sqlExp)
		 {
			throw new TTKException(sqlExp, "cheque");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "cheque");
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
					log.error("Error while closing the Resultset in ChequeDAOImpl getChequeDetail()",sqlExp);
					throw new TTKException(sqlExp, "cheque");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ChequeDAOImpl getChequeDetail()",sqlExp);
						throw new TTKException(sqlExp, "cheque");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ChequeDAOImpl getChequeDetail()",sqlExp);
							throw new TTKException(sqlExp, "cheque");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "cheque");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getChequeDetail(long lngSeqID, long lngUserSeqID)

	/**
	 * This method saves the Cheque Detail information
	 * @param chequeDetailVO the object which contains the details of the Cheque
	 * @return long value which contains Cheque Seq ID
	 * @exception throws TTKException
	 */
	public long saveCheque(ChequeDetailVO chequeDetailVO) throws TTKException
	{
		long lngChequeSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCheque);
			if(chequeDetailVO.getSeqID() != null){
				cStmtObject.setLong(1,(Long)chequeDetailVO.getSeqID());
			}//end of if(chequeDetailVO.getSeqID() != null)
			else {
				cStmtObject.setLong(1,0);
			}//end of else
			cStmtObject.setString(2,(String)chequeDetailVO.getStatusTypeID());
			cStmtObject.setTimestamp(3,(Date)chequeDetailVO.getClearedDate()!=null ? new Timestamp(chequeDetailVO.getClearedDate().getTime()):null);
			cStmtObject.setString(4,(String)chequeDetailVO.getRemarks());
			cStmtObject.setLong(5,(Long)chequeDetailVO.getUpdatedBy().longValue());
			cStmtObject.registerOutParameter(6,Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.execute();
			lngChequeSeqID = cStmtObject.getLong(1);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp)
		 {
			throw new TTKException(sqlExp, "cheque");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "cheque");
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
        			log.error("Error while closing the Statement in ChequeDAOImpl saveCheque()",sqlExp);
        			throw new TTKException(sqlExp, "cheque");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ChequeDAOImpl saveCheque()",sqlExp);
        				throw new TTKException(sqlExp, "cheque");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "cheque");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngChequeSeqID;
	}//end of saveCheque(ChequeDetailVO chequeDetailVO)
	
	/**
	 * This method update the Cheque Detail information from maintenance-finance.
	 * @param chequeVO the object which contains the details of the Cheque
	 * @return int value which contains result
	 * @exception throws TTKException
	 */
	public int updateChequeInfo(ChequeVO chequeVO) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUpdateChequeInfo);
			cStmtObject.setString(1,(String)chequeVO.getFloatAcctNo());
			cStmtObject.setString(2,(String)chequeVO.getClaimSettNo());
			cStmtObject.setString(3,(String)chequeVO.getRemarks());
			cStmtObject.setString(4,(String)chequeVO.getChequeNo());
			cStmtObject.setString(5,(String)chequeVO.getNewChequeNo());
			cStmtObject.setTimestamp(6,(Date)chequeVO.getChequeDate()!=null ? new Timestamp(chequeVO.getChequeDate().getTime()):null);
			cStmtObject.setLong(7,(Long)chequeVO.getUpdatedBy());
			cStmtObject.registerOutParameter(8,Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(8);
		}// end of try
		catch (SQLException sqlExp)
		 {
			throw new TTKException(sqlExp, "cheque");
		 }//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "cheque");
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
        			log.error("Error while closing the Statement in ChequeDAOImpl updateChequeInfo()",sqlExp);
        			throw new TTKException(sqlExp, "cheque");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in ChequeDAOImpl updateChequeInfo()",sqlExp);
        				throw new TTKException(sqlExp, "cheque");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "cheque");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of updateChequeInfo(ChequeVO chequeVO)
	
	//bikki
	public ArrayList getLogExcelUpload(String startDate, String endDate,String flag) throws TTKException {
		// TODO Auto-generated method stub
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ArrayList<ReportVO> alRresultList	=	null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strLogReport);
        	cStmtObject.setString(1, startDate);
        	cStmtObject.setString(2, endDate);
        	cStmtObject.setString(3, flag);
        	cStmtObject.registerOutParameter(4,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            conn.commit();
            
            ReportVO reportVO = null;
            
            if(rs != null){
            	alRresultList	=	new ArrayList<ReportVO>();
            	//GEETING META DATA FOR COLUMN HEADS
            	 ArrayList<String> alResult	=	null;
                 ResultSetMetaData metaData	=	rs.getMetaData();
                 int colmCount				=	metaData.getColumnCount();
                 if(colmCount>0)
                 	alResult = new ArrayList<String>();
                 for(int k=0;k<colmCount;k++)
                 {
                 	alResult.add(metaData.getColumnName(k+1));
                 }
                 reportVO	=	new ReportVO();
                 reportVO.setAlColumns(alResult);
                 alRresultList.add(reportVO);
            	
               //THIS BLOCK IS DATA FROM QUERY
            	while (rs.next()) {
            		alResult = new ArrayList<String>();
                	reportVO = new ReportVO();
                	
                	for(int l=1;l<=colmCount;l++)
                		alResult.add(rs.getString(l)==null?"":rs.getString(l));
                	reportVO.setAlColumns(alResult);
                    alRresultList.add(reportVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            
            return alRresultList;
        }
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "cheque");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "cheque");
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
        			log.error("Error while closing the Resultset getLogExcelUpload()",sqlExp);
        			throw new TTKException(sqlExp, "cheque");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if result set is not closed, control reaches here. Try closing the statement now.
        		{
        			try
        			{
        				if (cStmtObject != null) cStmtObject.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Statement getLogExcelUpload()",sqlExp);
        				throw new TTKException(sqlExp, "cheque");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection getLogExcelUpload()",sqlExp);
        					throw new TTKException(sqlExp, "cheque");
        				}//end of catch (SQLException sqlExp)
        			}//end of finally Connection Close
        		}//end of try
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "cheque");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs = null;
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
        }//end of finally
		
	}
	
	public Object ChequeUploadExcel(String batchNo,FileReader fileReader,int fileLength)throws TTKException {
        Connection con=null;        
        CallableStatement statement=null;
        CallableStatement statement2=null;
        boolean status=false;      
        FileWriter fileWriter=null;
        try{            
        	File file=new File(TTKPropertiesReader.getPropertyValue("dubaiLogFinDir"));
		    if(!file.exists())file.mkdirs();
		    fileWriter=new FileWriter(TTKPropertiesReader.getPropertyValue("dubaiLogFinDir")+batchNo+".txt");
		    //fileWriter=new FileWriter(TTKPropertiesReader.getPropertyValue("dubaiLogFinDir")+batchNo+".xls");
		    
		    fileWriter.write(batchNo);	
		   fileWriter.write("\n");
		    fileWriter.write("=============================================");
		    fileWriter.write("\n");
		   
		    
		    con = ResourceManager.getConnection();
        	  
		      statement=con.prepareCall(strSaveChequeXml);  
              
               SAXReader saxReader=new SAXReader(); 
               
               Document document=saxReader.read(fileReader);
               
               String batchStartTag="<batch>";
               
               String batchEndTag="</batch>";
               
               String filedetailNode=document.selectSingleNode("batch/filedetail").asXML();
               
               List<Node>   policyNodes=(List<Node>)document.selectNodes("batch/policy");
               StringBuilder builder;
               builder=new StringBuilder();
               builder.append(batchStartTag);
               builder.append(filedetailNode);
               
              for(Node policyNode:policyNodes){
            
                 builder.append(policyNode.asXML());
              }//for(Node policyNode:policyNodes){
              
              builder.append(batchEndTag);
              
              String onePolicyEntry=builder.toString();   
              SQLXML sqlxml = con.createSQLXML();
              sqlxml.setString(onePolicyEntry);
             
              statement.setSQLXML(1, sqlxml);
 			    
 			    status=statement.execute();
 			    con.commit();
              
            //getting logs
                statement2=con.prepareCall(strFloatLog); 
                statement2.setString(1, batchNo);
                statement2.registerOutParameter(2,Types.VARCHAR);//out parameter which gives the Log file
			    status=statement2.execute();
			    con.commit();
			    String  logData=statement2.getString(2);
			    if(logData==null|| "".equals(logData)) fileWriter.write("Some Problem Occurred Please Report To Adminstrator");
			    else {
			    	fileWriter.write(logData);
			    	
			    }
			    
			    fileWriter.write("\n");			   
			    fileWriter.flush();
			    if(fileWriter!=null)fileWriter.close();
			    
                }catch (TTKException sqlExp)
        		{
        			throw new TTKException(sqlExp, "cheque");
        		}//end of catch (SQLException sqlExp)
        		catch (Exception exp)
        		{
        			throw new TTKException(exp, "cheque");
        		}//end of catch (Exception exp)
        		finally
        		{
                	/* Nested Try Catch to ensure resource closure */
                	try // First try closing the Statement
                	{
                		try
                		{
                			if (statement != null) statement.close();
                			if (statement2 != null) statement2.close();
                		
                		}//end of try
                		catch (SQLException sqlExp)
                		{
                			log.error("Error while closing the Connection in ChequeDAOImpl ChequeUploadExcel()",sqlExp);
                			throw new TTKException(sqlExp, "cheque");
                		}//end of catch (SQLException sqlExp)
                		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
                		{
                			try
                			{
                				if(con != null) con.close();
                			}//end of try
                			catch (SQLException sqlExp)
                			{
                				log.error("Error while closing the Connection in ChequeDAOImpl ChequeUploadExcel()",sqlExp);
                				throw new TTKException(sqlExp, "cheque");
                			}//end of catch (SQLException sqlExp)
                		}//end of finally Connection Close
                	}//end of try
                	catch (TTKException exp)
                	{
                		throw new TTKException(exp, "cheque");
                	}//end of catch (TTKException exp)
                	finally // Control will reach here in anycase set null to the objects
                	{
                		statement = null;
                		statement2 = null;
                		con = null;
                		
                	}//end of finally
        		}//end of finally
        return status;
        	}

	public ArrayList<String[]> getTotalStatusCount(String batchNo) throws TTKException {
		
		    Connection conn = null;
	        ResultSet rs = null;
	        CallableStatement stmt = null;
	        ArrayList<String[]> al =new ArrayList<String[]>();
	        
	        try {
	        	
	        	 conn = ResourceManager.getConnection();
	        	     	 
	        	 stmt=(java.sql.CallableStatement)conn.prepareCall(strTotalClaimsCount);
	        	 stmt.setString(1,batchNo);
	        	 stmt.registerOutParameter(2,Types.OTHER);
	        	 stmt.execute();
	        	 rs=(ResultSet) stmt.getObject(2);
	        	 
	        	 if(rs != null){
                 while (rs.next()) {
                		if((!"".equals(TTKCommon.checkNull(rs.getString("status"))))  && (!"".equals(TTKCommon.checkNull(rs.getString("cnt")))))
                		al.add(new String[]{rs.getString("status"),rs.getString("cnt")}) ;
                 	 
                 }
             }
	        	        	
	        	 
			} //end try 
	        
	        
	        /*catch (SQLException sqlExp)
	        {
	              throw new TTKException(sqlExp, "cheque");
	        }//end of catch (SQLException sqlExp)
*/	        catch (Exception exp)
	        {
	              throw new TTKException(exp, "cheque");
	        }//end of catch (Exception exp)
	        finally
			{
	        	/* Nested Try Catch to ensure resource closure */ 
	        	try // First try closing the Statement
	        	{
	        		try
	        		{
	        			if (rs != null) rs.close();
	        			if (stmt != null) stmt.close();
	        		}//end of try
	        		catch (SQLException sqlExp)
	        		{
	        			log.error("Error while closing the Statement in ChequeDAOImpl getTotalStatusCount()",sqlExp);
	        			throw new TTKException(sqlExp, "cheque");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in ChequeDAOImpl getTotalStatusCount()",sqlExp);
	        				throw new TTKException(sqlExp, "cheque");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "cheque");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		stmt=null;
	        		rs=null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
		
		
	       return al;
	}
        
	
	
}//end of ChequeDAOImpl