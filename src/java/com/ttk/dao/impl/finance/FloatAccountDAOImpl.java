/**
 *   @ (#) FloatAccountDAOImpl.java June 17, 2006
 *   File         : FloatAccountDAOImpl.java
 *   Author       : Balakrishna E
 *   Company      : Span Systems Corporation
 *   Date Created : June 17, 2006
 *
 *   @author       :  Balakrishna E
 *   Modified by   
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dao.impl.finance;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;

import com.sun.rowset.CachedRowSetImpl;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.administration.ReportVO;
import com.ttk.dto.finance.AssocGroupVO;
import com.ttk.dto.finance.ChequeDetailVO;
import com.ttk.dto.finance.FloatAccountDetailVO;
import com.ttk.dto.finance.FloatAccountVO;
import com.ttk.dto.finance.InvoiceBatchVO;
import com.ttk.dto.finance.InvoiceVO;
import com.ttk.dto.finance.TransactionVO;
import com.ttk.dto.finance.ChequeVO;

public class FloatAccountDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(FloatAccountDAOImpl.class);

	private static final String strFloatAccountList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SELECT_FLOAT_ACC_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strFloatAccountDetail = "{CALL FLOAT_ACCOUNTS_PKG.SELECT_FLOAT_ACC_DETAIL(?,?,?)}";
	 private static final String strFloatAccountDetail = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SELECT_FLOAT_ACC_DETAIL(?,?,?,?)}";

	private static final String strSaveFloatAccount = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_FLOAT_ACC_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteFloatAccount = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_FLOAT_ACC_DELETE(?,?,?)}";
	private static final String strFloatAccountTransactionList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SELECT_FLOAT_ACC_TRN_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strFloatAccountTransactionDetail = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SELECT_FLOAT_ACC_TRN_DETAIL(?,?,?,?)}";
	private static final String strSaveFloatAccountTransaction = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_FLOAT_ACC_TRN_SAVE(?,?,?,?,?,?,?,?,?,?)}";
	//start changes for cr koc1103 and 1105
	private static final String strClaimList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SELECT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added 16 to 17
	//end changes for cr koc1103 and 1105
	private static final String strClaimDetail = "{CALL FLOAT_ACCOUNTS_PKG.SELECT_CLAIM_DETAIL(?,?,?,?)}";
	private static final String strSaveClaims = "{CALL FLOAT_ACCOUNTS_PKG.CHEQUE_PRINTING_DETAILS_SAVE(?,?,?,?,?)}";
	private static final String strReverseTransaction = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_FLOAT_ACC_TRAN_REVERSE(?,?,?,?)}";
	private static final String strPrintCheque = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_PRINT_CHECK(?,?,?,?,?,?,?)}";
	private static final String strPaymentAdviceList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SELECT_PAYMENT_ADVICE_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGenerateChequeAdvice = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_PRINT_BANK_ADVICE(?,?,?,?,?)}";
	private static final String strGenerateChequeAdviceXL = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_GENERATE_PAYMENT_ADVICE(?,?,?,?,?,?,?)}";
	private static final String strGenerateChequeAdviceXLDetails = "{CALL FLOAT_ACCOUNTS_PKG.GENERATE_PAYMENT_ADVICE_DTL(?,?,?)}";
	// Change added for BOA CR KOC1220
	private static final String strGenerateBOAXLDetails = "{CALL FLOAT_ACCOUNTS_PKG.GENERATE_BOA_PAYMENT_ADVICE(?,?,?)}";
	private static final String strViewPaymentAdviceList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_VIEW_PAYMENT_ADVICE(?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strIndividualInvoiceList = "{CALL FLOAT_ACCOUNTS_PKG.GENERATE_IND_INVOICE_LIST(?,?,?,?,?)}";
    private static final String strViewInvoiceList = "{CALL FLOAT_ACCOUNTS_PKG.SELECT_INVOICE_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strDeleteInvoice ="{CALL FLOAT_ACCOUNTS_PKG.INVOICES_DELETE(?,?,?)}";
    private static final String strViewInvoiceBatch = "{CALL FLOAT_ACCOUNTS_PKG.VIEW_INV_BATCH(?,?,?,?,?,?,?,?,?)}";
    private static final String strSetFundTransfer = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SET_FUND_TRANSFER(?,?,?,?,?,?)}";
    private static final String strBatchFileName = "{CALL FLOAT_ACCOUNTS_PKG.GET_BANK_ADVICE_SAVE_FILE_NAME(?,?)}";
  //start changes for cr koc1103 and 1105
    private static final String strBatchClaimList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SELECT_BATCH_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?)}";//added 10 to 11
  //end changes for cr koc1103 and 1105
  
    private static final String strFloatAssocGrp = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SELECT_FLOAT_GROUP_ASSOC(?,?)}";
    private static final String strSaveFloatAssocGrp = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SAVE_TPA_FLOAT_GROUP_ASSOC(?,?,?,?,?,?)}";
    private static final String strDeleteFloatAssocGrp = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_DELETE_TPA_FLOAT_GROUP_ASSOC(?,?)}";
    private static final String strOIPaymentAdvice = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_generate_oi_payment_advice(?,?,?,?,?,?,?)}";
  //start changes for cr koc1103 and 1105
    private static final String strEftPaymentAdviceList = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_SELECT_EFT_PAYMENT_ADVICE_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strgenerateMail="{CALL fin_app.batch_report_pkg_send_mediclaim_computation(?,?)}";
  //end changes for cr koc1103 and 1105
	private static final String strGenerateChequeAdviceENBDXL = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_PRINT_ENBD_CHECK(?,?,?,?,?,?,?,?)}";
	private static final String strGenerateChequeAdviceENBDConsXL = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_PRINT_ENBD_CHECK(?,?,?,?,?,?,?)}";
	private static final String strENBDcountAndAccNumber="select substr(b.account_number, -3) acc_number, e.cnt FROM fin_app.enbd_count e JOIN tpa_float_account A ON (e.float_seq_id = a.float_seq_id) JOIN tpa_bank_accounts B ON (A.bank_acc_seq_id = B.bank_acc_seq_id) where a.float_seq_id =?";
	private static final String strENBDcount="{CALL fin_app.FLOAT_ACCOUNTS_PKG_count_enbd_list(?,?)}"; 
	private static final String strLogDetailReport="{CALL fin_app.FLOAT_ACCOUNTS_PKG_batch_log(?,?,?,?)}";
	private static final String strGeneratePaymentAdviceByXLUpload = "{CALL fin_app.FLOAT_ACCOUNTS_PKG_payment_upload_details(?,?,?,?,?,?,?,?)}";
	private static final String strGenerateChequeAdviceENBDXLUpload="{CALL fin_app.FLOAT_ACCOUNTS_PKG_payment_advice_aupload_details(?,?,?,?,?,?,?,?,?)}";
	
	
    /**
	 * This method returns the ArrayList, which contains the FloatAccountVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of FloatAccountVO'S object's which contains the details of the Float Account
	 * @exception throws TTKException
	 */
	public ArrayList getFloatAccountList(ArrayList alSearchCriteria) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		FloatAccountVO floatAccountVO =null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strFloatAccountList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			if(alSearchCriteria.get(4)== null || alSearchCriteria.get(4).equals("")){
				cStmtObject.setLong(5,0);
			}//end of if(alSearchCriteria.get(4)!= null)
			else {
				cStmtObject.setLong(5,(Long)alSearchCriteria.get(4));
			}//end of else
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));

			if(alSearchCriteria.get(7) != null){
				cStmtObject.setLong(8,(Long)alSearchCriteria.get(7));
			}//end of if(alSearchCriteria.get(7) != null)
			else {
				cStmtObject.setLong(8,0);
			}//end of else

			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(9));
			//cStmtObject.setString(11,(String)alSearchCriteria.get(11));
			cStmtObject.setString(11,(String)alSearchCriteria.get(10));
		
			cStmtObject.setString(12,(String)alSearchCriteria.get(12));
			cStmtObject.setString(13,(String)alSearchCriteria.get(13));
			cStmtObject.setLong(14,(Long.parseLong(alSearchCriteria.get(14).toString())));
			cStmtObject.setLong(15,(Long.parseLong(alSearchCriteria.get(15).toString())));
			cStmtObject.setLong(16,(Long)alSearchCriteria.get(11));
			
			cStmtObject.registerOutParameter(17,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(17);

			if(rs != null){
				while(rs.next()){
					floatAccountVO = new FloatAccountVO();

					if(rs.getString("FLOAT_SEQ_ID") != null){
						floatAccountVO.setFloatAcctSeqID(new Long(rs.getString("FLOAT_SEQ_ID")));
						
					}//end of if(rs.getString(FLOAT_SEQ_ID) != null)
					floatAccountVO.setFloatNo(TTKCommon.checkNull(rs.getString("FLOAT_ACCOUNT_NUMBER")));
					floatAccountVO.setFloatAcctName(TTKCommon.checkNull(rs.getString("FLOAT_ACCOUNT_NAME")));
					floatAccountVO.setInsComp(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					floatAccountVO.setInsCompCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					floatAccountVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
					floatAccountVO.setStrProductAuthority(TTKCommon.checkNull(rs.getString("PAYER_AUTHORITY")));
					alResultList.add(floatAccountVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getFloatAccountList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getFloatAccountList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getFloatAccountList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getFloatAccountList(ArrayList alSearchCriteria)

	/**
	 * This method returns the FloatAccountDetailVO which contains the Float Account Detail information
	 * @param lngFloatAcctSeqID long value which contains FloatAccount seq id to get the Float Account Detail information
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return FloatAccountDetailVO this contains the Float Account Detail information
	 * @exception throws TTKException
	 */
	public FloatAccountDetailVO getFloatAccountDetail(long lngFloatAcctSeqID, long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		//FloatAccountDetailVO floatAccountDetailVO = null;
		FloatAccountDetailVO floatAccountDetailVO = new FloatAccountDetailVO();
		AssocGroupVO assocGroupVO = null;
		ArrayList<Object> alAssocGrpList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strFloatAccountDetail);
			cStmtObject.setLong(1,lngFloatAcctSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.registerOutParameter(4,Types.OTHER);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(3);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs1 != null){
				while(rs1.next()){
					//floatAccountDetailVO = new FloatAccountDetailVO();

					if(rs1.getString("FLOAT_SEQ_ID") != null){
						floatAccountDetailVO.setFloatAcctSeqID(new Long(rs1.getString("FLOAT_SEQ_ID")));
					}//end of if(rs1.getString("FLOAT_SEQ_ID" != null))
					floatAccountDetailVO.setTransactionYN(TTKCommon.checkNull(rs1.getString("v_result")));
					floatAccountDetailVO.setFloatType(TTKCommon.checkNull(rs1.getString("FLOAT_TYPE")));
					floatAccountDetailVO.setFloatNo(TTKCommon.checkNull(rs1.getString("FLOAT_ACCOUNT_NUMBER")));
					floatAccountDetailVO.setFloatAcctName(TTKCommon.checkNull(rs1.getString("FLOAT_ACCOUNT_NAME")));
					floatAccountDetailVO.setStatusDesc(TTKCommon.checkNull(rs1.getString("FLOAT_STATUS")));

					if(rs1.getTimestamp("EFFECTIVE_DATE")!= null){
						floatAccountDetailVO.setCreatedDate(new Date(rs1.getTimestamp("EFFECTIVE_DATE").getTime()));
					}//end of if(rs1.getTimestamp("EFFECTIVE_DATE")!= null)

					if(rs1.getTimestamp("EXPIRATION_DATE") != null){
						floatAccountDetailVO.setClosedDate(new Date(rs1.getTimestamp("EXPIRATION_DATE").getTime()));
					}//end of if(rs1.getTimestamp("EXPIRATION_DATE") != null)

					if(rs1.getString("BANK_ACC_SEQ_ID") != null){
						floatAccountDetailVO.setAccountSeqID(new Long(rs1.getLong("BANK_ACC_SEQ_ID")));
					}//end of if(rs1.getString("BANK_ACC_SEQ_ID") != null)

					floatAccountDetailVO.setAccountNO(TTKCommon.checkNull(rs1.getString("ACCOUNT_NUMBER")));
					floatAccountDetailVO.setAccountName(TTKCommon.checkNull(rs1.getString("ACCOUNT_NAME")));
					floatAccountDetailVO.setBranchName(TTKCommon.checkNull(rs1.getString("OFFICE_NAME")));
					floatAccountDetailVO.setBankName(TTKCommon.checkNull(rs1.getString("BANK_NAME")));

					if(rs1.getString("INS_SEQ_ID") != null){
						floatAccountDetailVO.setInsSeqID(new Long(rs1.getString("INS_SEQ_ID")));
					}//end of if(rs1.getString("INS_SEQ_ID") != null){
					
					floatAccountDetailVO.setInsComp(TTKCommon.checkNull(rs1.getString("INS_COMP_NAME")));
					floatAccountDetailVO.setInsCompCode(TTKCommon.checkNull(rs1.getString("INS_COMP_CODE_NUMBER")));

					if(rs1.getString("GROUP_REG_SEQ_ID") != null){
						floatAccountDetailVO.setGroupRegnSeqID(new Long(rs1.getString("GROUP_REG_SEQ_ID")));
					}//end of if(rs1.getLong("GROUP_REG_SEQ_ID") != null)

					//floatAccountDetailVO.setGroupID(TTKCommon.checkNull(rs1.getString("GROUP_ID")));
					//floatAccountDetailVO.setGroupName(TTKCommon.checkNull(rs1.getString("GROUP_NAME")));
					if(rs1.getString("ESTABLISHED_AMT") != null){
					floatAccountDetailVO.setEstablishAmt(new BigDecimal(rs1.getString("ESTABLISHED_AMT")));
					}//end of if(rs1.getString("ESTABLISHED_AMT") != null)
					if(rs1.getString("CURRENT_BALANCE") != null){
						floatAccountDetailVO.setCurrentBalance(new BigDecimal(rs1.getString("CURRENT_BALANCE")));
					}//end of if(rs1.getString("CURRENT_BALANCE") != null)
					floatAccountDetailVO.setRemarks(TTKCommon.checkNull(rs1.getString("COMMENTS")));
					floatAccountDetailVO.setOfficeTypeDesc(TTKCommon.checkNull(rs1.getString("OFFICE_TYPE")));
                    floatAccountDetailVO.setInsOfficeType(TTKCommon.checkNull(rs1.getString("INS_OFFICE_TYPE")));
                    floatAccountDetailVO.setInsTtkBranch(TTKCommon.checkNull(rs1.getString("INS_OFFICE_NAME")));
                   
                    floatAccountDetailVO.setProductTypeCode(TTKCommon.checkNull(rs1.getString("prod_general_type_id")));     
                 
                    floatAccountDetailVO.setSelectedProduct(TTKCommon.checkNull(rs1.getString("product_seq_ids")));
                   
                    floatAccountDetailVO.setAdminCategoryType(TTKCommon.checkNull(rs1.getString("policy_category")));
                    floatAccountDetailVO.setNetworkType(TTKCommon.checkNull(rs1.getString("provider_networks")));        
                    floatAccountDetailVO.setPolicyType(TTKCommon.checkNull(rs1.getString("policy_type")));   
                    floatAccountDetailVO.setEncounterType(TTKCommon.checkNull(rs1.getString("encounter_type"))); 
                    //AK8
                    floatAccountDetailVO.setPayerAuthority(TTKCommon.checkNull(rs1.getString("PAYER_AUTHORITY"))); 
                    floatAccountDetailVO.setDirectBillingYN(TTKCommon.checkNull(rs1.getString("submission_catogory")));
                    
				}//end of while(rs1.next())
			}//end of if(rs1 != null){
			if(rs2 != null){
				while(rs2.next()){
					assocGroupVO = new AssocGroupVO();
					if(rs2.getString("TPA_FLOAT_GROUP_ASSOC_SEQ") != null){
						assocGroupVO.setFloatGrpAssSeqID(new Long(rs2.getLong("TPA_FLOAT_GROUP_ASSOC_SEQ")));
					}//end of if(rs2.getString("TPA_FLOAT_GROUP_ASSOC_SEQ") != null)

					if(rs2.getString("GROUP_REG_SEQ_ID") != null){
						assocGroupVO.setGroupRegSeqID(new Long(rs2.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs2.getString("GROUP_REG_SEQ_ID") != null)

					assocGroupVO.setGroupID(TTKCommon.checkNull(rs2.getString("GROUP_ID")));
					assocGroupVO.setGroupName(TTKCommon.checkNull(rs2.getString("GROUP_NAME")));

					assocGroupVO.setPolicyNo(TTKCommon.checkNull(rs2.getString("POLICY_NO")));
					assocGroupVO.setPolicyCategory(TTKCommon.checkNull(rs2.getString("CLASSIFICATION")));
					alAssocGrpList.add(assocGroupVO);
				}//end of while(rs2.next())
				floatAccountDetailVO.setAssocGrpList(alAssocGrpList);
			}//end of if(rs2 != null)

			return floatAccountDetailVO;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getFloatAccountDetail()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (rs2 != null) rs2.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in FloatAccountDAOImpl getFloatAccountDetail()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null)	cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in FloatAccountDAOImpl getFloatAccountDetail()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in FloatAccountDAOImpl getFloatAccountDetail()",sqlExp);
								throw new TTKException(sqlExp, "floataccount");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of Resultset close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in any case set null to the objects
			{
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getFloatAccountDetail(long lngFloatAcctSeqID)

	/**
	 * This method saves the Float Account Detail information
	 * @param floatAccountDetailVO the object which contains the details of the Float account
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public long saveFloatAccount(FloatAccountDetailVO floatAccountDetailVO) throws TTKException
	{
		long lngFloatAcctSeqID =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveFloatAccount);
			if(floatAccountDetailVO.getFloatAcctSeqID() != null){
				cStmtObject.setLong(1,floatAccountDetailVO.getFloatAcctSeqID().longValue());
			}//end of if(floatAccountDetailVO.getFloatAcctSeqID() != null)
			else {
				cStmtObject.setLong(1,0);
			}//end of else

			cStmtObject.setString(2,floatAccountDetailVO.getFloatType());
			cStmtObject.setString(3,floatAccountDetailVO.getFloatAcctName());
			cStmtObject.setString(4,floatAccountDetailVO.getStatusDesc());

			if(floatAccountDetailVO.getCreatedDate() != null){
				cStmtObject.setTimestamp(5,new Timestamp(floatAccountDetailVO.getCreatedDate().getTime()));
			}//end of if(floatAccountDetailVO.getCreatedDate() != null)
			else {
				cStmtObject.setTimestamp(5,null);
			}//end of else

			if(floatAccountDetailVO.getClosedDate() != null){
				cStmtObject.setTimestamp(6,new Timestamp(floatAccountDetailVO.getClosedDate().getTime()));
			}//end of if(floatAccountDetailVO.getClosedDate() != null)
			else {
				cStmtObject.setTimestamp(6,null);
			}//end of else

			if(floatAccountDetailVO.getAccountSeqID() != null){
				cStmtObject.setLong(7,floatAccountDetailVO.getAccountSeqID().longValue());
			}//end of if(floatAccountDetailVO.getAccountSeqID() != null)
			else {
				cStmtObject.setLong(7,0);
			}//end of else

			
			if(floatAccountDetailVO.getInsSeqID() != null){
				cStmtObject.setLong(8,floatAccountDetailVO.getInsSeqID().longValue());
		
			}//end of if(floatAccountDetailVO.getInsSeqID() != null)
			else {
				cStmtObject.setLong(8,0);
			}//end of else

		
			if(floatAccountDetailVO.getGroupRegnSeqID() != null){
				cStmtObject.setLong(9,floatAccountDetailVO.getGroupRegnSeqID().longValue());
			}//end of if(floatAccountDetailVO.getGroupRegnSeqID() != null)
			else {
				cStmtObject.setLong(9,0);
			}//end of else

			if(floatAccountDetailVO.getEstablishAmt() != null){
				cStmtObject.setBigDecimal(10,floatAccountDetailVO.getEstablishAmt());
			}//end of if(floatAccountDetailVO.getEstablishAmt() != null)
			else{
				cStmtObject.setDouble(10,0.00);
			}//end of else

			cStmtObject.setString(11,floatAccountDetailVO.getRemarks());
			cStmtObject.setString(12,floatAccountDetailVO.getProductTypeCode());
	
	
			cStmtObject.setLong(13,new Long(floatAccountDetailVO.getUpdatedBy()).longValue());
						
			cStmtObject.setString(14,floatAccountDetailVO.getSelectedProduct());
			cStmtObject.setString(15,floatAccountDetailVO.getAdminCategoryType());
			cStmtObject.setString(16,floatAccountDetailVO.getNetworkType());
			cStmtObject.setString(17,floatAccountDetailVO.getEncounterType());
			cStmtObject.setString(18,floatAccountDetailVO.getPolicyType());
			
			cStmtObject.setString(19,floatAccountDetailVO.getDirectBillingYN());
			
			cStmtObject.registerOutParameter(20,Types.INTEGER);//ROW_PROCESSED
	
			cStmtObject.registerOutParameter(1,Types.BIGINT);//FLOAT_SEQ_ID
			cStmtObject.execute();
			lngFloatAcctSeqID = cStmtObject.getLong(1);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl saveFloatAccount()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl saveFloatAccount()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngFloatAcctSeqID;
	}//end of saveFloatAccount(FloatAccountDetailVO floatAccountDetailVO)

	/**
	 * This method deletes the Float Account Detail information from the database
	 * @param alDeleteList ArrayList object which contains seq id for Finance flow to delete the Float Account information
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteFloatAccount(ArrayList alDeleteList) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			if(alDeleteList != null && alDeleteList.size() > 0){
				conn = ResourceManager.getConnection();
				conn.setAutoCommit(false);
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteFloatAccount);
				cStmtObject.setString(1,(String)alDeleteList.get(0));
				cStmtObject.setLong(2,(Long)alDeleteList.get(1));
				cStmtObject.registerOutParameter(3,Types.INTEGER);
				cStmtObject.execute();
				iResult = cStmtObject.getInt(3);
				conn.commit();
			}//end of if(alDeleteList != null && alDeleteList.size() > 0)

		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl deleteFloatAccount()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl deleteFloatAccount()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteFloatAccount(ArrayList alDeleteList)

	/**
	 * This method returns the ArrayList, which contains the TransactionVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of TransactionVO's object's which contains the details of the Float Transaction
	 * @exception throws TTKException
	 */
	public ArrayList getFloatTransactionList(ArrayList alSearchCriteria) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		TransactionVO transactionVO =null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strFloatAccountTransactionList);
			cStmtObject.setLong(1,(Long.parseLong(alSearchCriteria.get(0).toString())));//Mandatory
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setLong(8,(Long.parseLong(alSearchCriteria.get(8).toString())));
			cStmtObject.setLong(9,(Long.parseLong(alSearchCriteria.get(9).toString())));
			cStmtObject.setLong(10,(Long.parseLong(alSearchCriteria.get(5).toString())));
			cStmtObject.registerOutParameter(2,Types.VARCHAR);
			cStmtObject.registerOutParameter(11,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			if(rs != null){
				while(rs.next()){
					transactionVO = new TransactionVO();

					if(rs.getString("FLOAT_TRN_SEQ_ID") != null){
						transactionVO.setTransSeqID(new Long(rs.getString("FLOAT_TRN_SEQ_ID")));
					}//end of if(rs.getString("FLOAT_TRN_SEQ_ID") != null)

					if(rs.getString("FLOAT_SEQ_ID") != null){
						transactionVO.setAccountSeqID(new Long(rs.getString("FLOAT_SEQ_ID")));
					}//end of if(rs.getString("FLOAT_SEQ_ID") != null)

					transactionVO.setTransNbr(TTKCommon.checkNull(rs.getString("FLOAT_TRN_NUMBER")));
					transactionVO.setTransTypeID(TTKCommon.checkNull(rs.getString("FLT_TRN_TYPE")));
					transactionVO.setTransTypeDesc(TTKCommon.checkNull(rs.getString("TYPE")));

					if(rs.getTimestamp("FLT_TRN_DATE") != null){
						transactionVO.setTransDate(new Date(rs.getTimestamp("FLT_TRN_DATE").getTime()));
					}//end of if(rs.getTimestamp("FLT_TRN_DATE") != null)

					if(rs.getString("FLT_TRN_AMOUNT") != null){
						transactionVO.setTransAmt(new BigDecimal(rs.getString("FLT_TRN_AMOUNT")));
					}//end of if(rs.getString("FLT_TRN_AMOUNT") != null)

					if(rs.getString("CURRENT_BALANCE") != null){
						transactionVO.setFloatBalance(new BigDecimal(rs.getString("CURRENT_BALANCE")));
					}//end of if(rs.getString("CURRENT_BALANCE") != null)

					if(rs.getString("REVERSE_YN").equals("Y")){
						transactionVO.setImageName("FreshIcon");
						transactionVO.setImageTitle("Reverse Entry");
					}//end of if(rs.getString("REVERSE_YN").equals("Y"))

					alResultList.add(transactionVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getFloatTransactionList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getFloatTransactionList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getFloatTransactionList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getFloatTransactionList(ArrayList alSearchCriteria)

	/**
	 * This method returns the TransactionVO which contains the Float transaction information
	 * @param lngFloatTransSeqID long value which contains float transaction seq id to get the Float Account Detail information
	 * @param lngFloatSeqID long value which contains float seq id to get the flaot account information
	 * @return TransactionVO this contains the Transaction information
	 * @exception throws TTKException
	 */
	public TransactionVO getFloatTransactionDetail(long lngFloatTransSeqID, long lngFloatSeqID, long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		TransactionVO transactionVO =null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strFloatAccountTransactionDetail);
			cStmtObject.setLong(1, lngFloatTransSeqID);
			cStmtObject.setLong(2, lngFloatSeqID);
			cStmtObject.setLong(3, lngUserSeqID);
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(4,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				while(rs.next()){
					transactionVO = new TransactionVO();

					if(rs.getString("FLOAT_TRN_SEQ_ID") != null){
						transactionVO.setTransSeqID(new Long(rs.getString("FLOAT_TRN_SEQ_ID")));
					}//end of if(rs.getString("FLOAT_TRN_SEQ_ID") != null)

					if(rs.getString("FLOAT_SEQ_ID") != null){
						transactionVO.setAccountSeqID(new Long(rs.getString("FLOAT_SEQ_ID")));
					}//end of if(rs.getString("FLOAT_TRN_SEQ_ID") != null)

					transactionVO.setTransNbr(TTKCommon.checkNull(rs.getString("FLOAT_TRN_NUMBER")));
					transactionVO.setTransTypeID(TTKCommon.checkNull(rs.getString("FLT_TRN_TYPE")));
					transactionVO.setTransDate(new Date(rs.getTimestamp("FLT_TRN_DATE").getTime()));
					transactionVO.setTransAmt(new BigDecimal(rs.getString("FLT_TRN_AMOUNT")));
					transactionVO.setRemarks(TTKCommon.checkNull(rs.getString("COMMENTS")));
					transactionVO.setFloatTypeID(TTKCommon.checkNull(rs.getString("FLOAT_TYPE")));
					transactionVO.setCurrency(TTKCommon.checkNull(rs.getString("CURRENCY_TYPE")));
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return transactionVO;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getFloatTransactionDetail()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getFloatTransactionDetail()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getFloatTransactionDetail()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getFloatTransactionDetail(long lngFloatTransSeqID, long lngFloatSeqID, long lngUserSeqID)

	/**
	 * This method saves the Float Transaction information
	 * @param transactionVO the object which contains the details of the transaction
	 * @return long value contains Transaction Seq ID
	 * @exception throws TTKException
	 */
	public long saveFloatTransaction(TransactionVO transactionVO) throws TTKException
	{
		int iResult = 0;
		long lngFloatTransSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveFloatAccountTransaction);
			if(transactionVO.getTransSeqID() != null){
				cStmtObject.setLong(1,transactionVO.getTransSeqID().longValue());
			}//end of if(transactionVO.getTransSeqID() != null)
			else {
				cStmtObject.setLong(1,0);
			}//end of else

			if(transactionVO.getAccountSeqID() != null){
				cStmtObject.setLong(2,transactionVO.getAccountSeqID().longValue());
			}//end of if(transactionVO.getAccountSeqID() != null)
			else {
				cStmtObject.setLong(2,0);
			}//end of else

			cStmtObject.setString(3,transactionVO.getTransTypeID());

			if(transactionVO.getTransDate() != null){
				cStmtObject.setTimestamp(4,new Timestamp(transactionVO.getTransDate().getTime()));
			}//end of if(transactionVO.getTransDate() != null)
			else {
				cStmtObject.setTimestamp(4,null);
			}//end of else

			cStmtObject.setBigDecimal(5,transactionVO.getTransAmt());
			cStmtObject.setString(6,transactionVO.getRemarks());
			cStmtObject.setLong(7,transactionVO.getUpdatedBy().longValue());
			cStmtObject.setString(8,transactionVO.getFloatTypeID());
			cStmtObject.setString(9,transactionVO.getCurrency());
			cStmtObject.registerOutParameter(10,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(1,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(10);
			log.debug("iResult value is :"+iResult);
			lngFloatTransSeqID = cStmtObject.getLong(1);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl saveFloatTransaction()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl saveFloatTransaction()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngFloatTransSeqID;
	}//end of saveFloatTransaction(TransactionVO transactionVO)

	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Claims
	 * @exception throws TTKException
	 */
	public ArrayList getClaimList(ArrayList alSearchCriteria) throws TTKException
	{
		
		Collection<Object> alResultList = new ArrayList<Object>();
		
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeVO chequeVO = null;
		String strSuppressLink[]={"7"};
		String strTDSYN = "";
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimList);
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			if(alSearchCriteria.get(1) != null){
				cStmtObject.setLong(2,(Long)alSearchCriteria.get(1));
			
			}//end of if(alSearchCriteria.get(1) != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			//start changes for cr koc1103 and 1105
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(9));
			cStmtObject.setString(11,(String)alSearchCriteria.get(10));
			cStmtObject.setString(12,(String)alSearchCriteria.get(14));
			cStmtObject.setString(13,(String)alSearchCriteria.get(15));
			cStmtObject.setLong(14,(Long.parseLong(alSearchCriteria.get(16).toString())));
			cStmtObject.setLong(15,(Long.parseLong(alSearchCriteria.get(17).toString())));
			cStmtObject.setString(16,(String)alSearchCriteria.get(13));
			cStmtObject.setLong(17,(Long.parseLong(alSearchCriteria.get(12).toString())));
			cStmtObject.setString(18,(String)alSearchCriteria.get(11));
			cStmtObject.registerOutParameter(19,Types.OTHER);
	
			//start changes for cr koc1103 and 1105
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(19);
			conn.commit();

			if(rs != null){
				while(rs.next()){

					chequeVO = new ChequeVO();
					if(rs.getString("PAYMENT_SEQ_ID") != null){
						chequeVO.setSeqID(new Long(rs.getString("PAYMENT_SEQ_ID")));
					}//end of if(rs.getString("PAYMENT_SEQ_ID") != null)

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

					if(rs.getString("CURRENT_BALANCE") != null){
						chequeVO.setAvblFloatBalance(new BigDecimal(rs.getString("CURRENT_BALANCE")));
					}//end of if(rs.getString("CURRENT_BALANCE") != null)

					chequeVO.setFileName(TTKCommon.checkNull(rs.getString("TEMPLATE_NAME")));
					chequeVO.setRemarks("");
					strTDSYN = TTKCommon.checkNull(rs.getString("TDS_PROCESS_YN"));
                    
					if(strTDSYN.equals("N")){
						//chequeVO.setSuppressLink(strSuppressLink);
					}//end of if(strTDSYN.equals("N"))
					chequeVO.setIncuredCurencyFormat(rs.getString("req_amt_currency_type"));
					if(rs.getString("CONVERTED_FINAL_APPROVED_AMT")!=null)
					chequeVO.setConvertedApprovedAmount(new BigDecimal(rs.getString("CONVERTED_FINAL_APPROVED_AMT")));
					else chequeVO.setConvertedApprovedAmount(new BigDecimal("0.0"));
					//chequeVO.setConvertedApprovedAmount(rs.getBigDecimal("CONVERTED_FINAL_APPROVED_AMT"));
					if(rs.getString("DHPO_MEMBER_ID") == null || rs.getString("DHPO_MEMBER_ID").equals(""))
						chequeVO.setDhpoMemberId("");
					else
					chequeVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					alResultList.add(chequeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getClaimList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getClaimList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ChequeVO which contains the Claims information
	 * @param lngClaimSeqID long value which contains claims seq id to get the Claims information
	 * @param lngFloatAcctSeqID long value which contains FloatAccount seq id to get the Float Account Detail information
	 * @return ChequeDetailVO this contains the Claims information
	 * @exception throws TTKException
	 */
	public ChequeDetailVO getClaimDetail(long lngClaimSeqID,long lngFloatAcctSeqID, long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeDetailVO chequeDetailVO = new ChequeDetailVO();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimDetail);
			cStmtObject.setLong(1,lngClaimSeqID);
			cStmtObject.setLong(2,lngFloatAcctSeqID);
			cStmtObject.setLong(3,lngUserSeqID);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();

			rs = (java.sql.ResultSet)cStmtObject.getObject(4);

			if(rs != null){
				while(rs.next()){
					chequeDetailVO.setClaimSettNo(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NO")));
					chequeDetailVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));

					if(rs.getTimestamp("CLAIM_APRV_DATE") != null){
						chequeDetailVO.setApprDate(new Date(rs.getTimestamp("CLAIM_APRV_DATE").getTime()));
					}//end of if(rs.getTimestamp("CLAIM_APRV_DATE") != null)

					if(rs.getString("APPROVED_AMOUNT") != null){
						chequeDetailVO.setClaimAmt(new BigDecimal(rs.getString("APPROVED_AMOUNT")));
					}//end of if(rs.getString("APPROVED_AMOUNT") != null)

					if(rs.getString("FLOAT_SEQ_ID") != null){
						chequeDetailVO.setAccountSeqID(new Long(rs.getString("FLOAT_SEQ_ID")));
					}//end of if(rs.getString(FLOAT_SEQ_ID) != null)

					chequeDetailVO.setEnrollID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					chequeDetailVO.setClaimantName(TTKCommon.checkNull(rs.getString("MEM_NAME")));

					if(rs.getString("POLICY_SEQ_ID") != null){
						chequeDetailVO.setPolicySeqID(new Long(rs.getString("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					chequeDetailVO.setInsCompName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					chequeDetailVO.setInsCompCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					chequeDetailVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					chequeDetailVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					chequeDetailVO.setInFavorOf(TTKCommon.checkNull(rs.getString("IN_FAVOUR_OF")));
					chequeDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
				}//end of while(rs.next())
			}//end of if(rs != null)
			return chequeDetailVO;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getClaimDetail()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getClaimDetail()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getClaimDetail()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimDetail(long lngClaimSeqID,long lngFloatAcctSeqID, long lngUserSeqID)

	/**
	 * This method saves the Claims information
	 * @param chequeDetailVO the object which contains the details of the Claim
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveClaims(ChequeDetailVO chequeDetailVO) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaims);
			if(chequeDetailVO.getSeqID()!= null){
				cStmtObject.setLong(1,chequeDetailVO.getSeqID());
			}//end of if(chequeDetailVO.getSeqID()!= null){
			else
			{
				cStmtObject.setLong(1,0);
			}//end of else
			cStmtObject.setString(2,chequeDetailVO.getClaimSettNo());
			cStmtObject.setString(3,chequeDetailVO.getRemarks());
			cStmtObject.setLong(4,chequeDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(5,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl saveClaims()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl saveClaims()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveClaims(ChequeDetailVO chequeDetailVO)

	/**
	 * This method generates the Cheque information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateCheque(ArrayList alChequeList) throws TTKException
	{
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CachedRowSet crs = null;
		TTKReportDataSource reportDataSource =null;
		try{
			log.info("DAO generateCheque start-->"+new Date());
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPrintCheque);
			log.info("1F-------"+(String)alChequeList.get(0));
			cStmtObject.setString(1,(String)alChequeList.get(0));

			if(alChequeList.get(1) != null){
				log.info("2F-------"+(Long)alChequeList.get(1));
				cStmtObject.setLong(2,(Long)alChequeList.get(1));
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else

			log.info("3F-------"+(String)alChequeList.get(2));
			cStmtObject.setString(3,(String)alChequeList.get(2));
			log.info("4F-------"+(String)alChequeList.get(3));
			cStmtObject.setString(4,(String)alChequeList.get(3));
			log.info("5F-------"+(Long)alChequeList.get(4));
			cStmtObject.setLong(5,(Long)alChequeList.get(4));
			cStmtObject.registerOutParameter(6,Types.INTEGER);
			cStmtObject.registerOutParameter(7,Types.OTHER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);
			log.debug("iResult value is :"+iResult);
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			conn.commit();
			crs = new CachedRowSetImpl();
			if(rs !=null)
			{
				reportDataSource = new TTKReportDataSource();
				crs.populate(rs);
				reportDataSource.setData(crs);
			}
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
					if (crs != null) crs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in FloatAccountDAOImpl generateCheque()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl generateCheque()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl generateCheque()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				crs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		log.info("DAO generateCheque end-->"+new Date());
		return reportDataSource;
	}//end of generateCheque(ArrayList alChequeList)

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
			cStmtObject.setLong(1,(Long)alReverseTransList.get(0));//FLOAT_ACC_SEQ_ID
			cStmtObject.setString(2, (String)alReverseTransList.get(1));//CONCATENATED STRING OF FLOAT_TRN_SEQ_ID
			cStmtObject.setLong(3,(Long)alReverseTransList.get(2));//ADDED_BY
			cStmtObject.registerOutParameter(4, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);//ROWS_PROCESSED
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl reverseTransaction()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl reverseTransaction()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//end of reverseTransaction(ArrayList alReverseTransList)

    /**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public ArrayList getPaymentAdviceList(ArrayList alSearchCriteria) throws TTKException {
		log.info(" DAO getPaymentAdviceList start-->"+new Date());
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeVO chequeVO = null;
		String strSuppressLink[]={"0"};
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPaymentAdviceList);
			
			
			if(alSearchCriteria.get(0)!=null)
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			else
				cStmtObject.setLong(1,0);

			/*if(alSearchCriteria.get(1) != null){
				cStmtObject.setLong(2,(Long)alSearchCriteria.get(1));
			}//end of if(alSearchCriteria.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else*/

			cStmtObject.setString(2,(String)alSearchCriteria.get(1));

			if(alSearchCriteria.get(2) != null){
				cStmtObject.setLong(3,(Long)alSearchCriteria.get(2));
			}//end of if(alSearchCriteria.get(2) != null)
			else{
				cStmtObject.setLong(3,0);
			}//end of else

            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(6));
			cStmtObject.setString(6,(String)alSearchCriteria.get(7));
			cStmtObject.setLong(7,(Long.parseLong(alSearchCriteria.get(8).toString())));
			cStmtObject.setLong(8,(Long.parseLong(alSearchCriteria.get(9).toString())));
			cStmtObject.setLong(9,(Long)alSearchCriteria.get(4));
			cStmtObject.setString(10,(String)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(11,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);

			if(rs != null){
				while(rs.next()){
					chequeVO = new ChequeVO();

					if(rs.getString("PAYMENT_SEQ_ID") != null){
						chequeVO.setSeqID(new Long(rs.getString("PAYMENT_SEQ_ID")));
					}//end of if(rs.getString("PAYMENT_SEQ_ID") != null)

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

					chequeVO.setAbbrevationCode(new String(rs.getString("ABBREVATION_CODE")));
					
					chequeVO.setSuppressLink(strSuppressLink);

					if(!(TTKCommon.checkNull(rs.getString("HO"))).equals("") && !(TTKCommon.checkNull(rs.getString("BO"))).equals("")){

						if((TTKCommon.checkNull(rs.getString("HO"))).equals("UTIBANK") ||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("CITIBANK")||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("ICICIBANK")||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("HDFCBANK") ||
						    // Change below of BOA for CR KOC1220
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("BOABANK")){
							if(TTKCommon.checkNull(rs.getString("HO")).equals("UTIBANK")){
								chequeVO.setBankName("UTI");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("UTIBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("ICICIBANK")){
								chequeVO.setBankName("ICICI");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("ICICIBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("HDFCBANK")){
								chequeVO.setBankName("HDFC");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("HDFCBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("BOABANK")){
								chequeVO.setBankName("BOA");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("BOABANK"))
							else{
								chequeVO.setBankName("CITI");
							}//end of else
						}//end of if((TTKCommon.checkNull(rs.getString("HO"))).equals("UTIBANK") || (TTKCommon.checkNull(rs.getString("HO"))).equals("CITIBANK")
							//|| (TTKCommon.checkNull(rs.getString("HO"))).equals("ICICIBANK")
							//|| (TTKCommon.checkNull(rs.getString("HO"))).equals("HDFCBANK"))
						else{
							chequeVO.setBankName("CITI");
						}//end of else
					}//end of if(!(TTKCommon.checkNull(rs.getString("HO"))).equals("") && !(TTKCommon.checkNull(rs.getString("BO"))).equals(""))
					else if(!(TTKCommon.checkNull(rs.getString("HO"))).equals("")){
						if((TTKCommon.checkNull(rs.getString("HO"))).equals("UTIBANK") ||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("CITIBANK") ||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("ICICIBANK") ||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("HDFCBANK") ||
						    (TTKCommon.checkNull(rs.getString("HO"))).equals("BOABANK")){
							if(TTKCommon.checkNull(rs.getString("HO")).equals("UTIBANK")){
								chequeVO.setBankName("UTI");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("UTIBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("ICICIBANK")){
								chequeVO.setBankName("ICICI");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("ICICIBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("HDFCBANK")){
								chequeVO.setBankName("HDFC");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("HDFCBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("BOABANK")){
								chequeVO.setBankName("BOA");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("BOABANK"))
							else{
								chequeVO.setBankName("CITI");
							}//end of else
						}//end of if((TTKCommon.checkNull(rs.getString("HO"))).equals("UTIBANK") || (TTKCommon.checkNull(rs.getString("HO"))).equals("ICICI"))
						else{
							chequeVO.setBankName("CITI");
						}//end of else
					}//end of else
					else{
						if((TTKCommon.checkNull(rs.getString("BO"))).equals("UTIBANK") ||
								(TTKCommon.checkNull(rs.getString("BO"))).equals("CITIBANK") ||
								(TTKCommon.checkNull(rs.getString("BO"))).equals("ICICIBANK") ||
								(TTKCommon.checkNull(rs.getString("BO"))).equals("HDFCBANK") ||
								(TTKCommon.checkNull(rs.getString("BO"))).equals("BOABANK")){
							if(TTKCommon.checkNull(rs.getString("BO")).equals("UTIBANK")){
								chequeVO.setBankName("UTI");
							}//end of if(TTKCommon.checkNull(rs.getString("BO")).equals("UTIBANK"))
							else if(TTKCommon.checkNull(rs.getString("BO")).equals("ICICIBANK")){
								chequeVO.setBankName("ICICI");
							}//end of if(TTKCommon.checkNull(rs.getString("BO")).equals("ICICIBANK"))
							else if(TTKCommon.checkNull(rs.getString("BO")).equals("HDFCBANK")){
								chequeVO.setBankName("HDFC");
							}//end of if(TTKCommon.checkNull(rs.getString("BO")).equals("UTIBANK"))
							else if(TTKCommon.checkNull(rs.getString("BO")).equals("BOABANK")){
								chequeVO.setBankName("BOA");
							}//end of if(TTKCommon.checkNull(rs.getString("BO")).equals("BOABANK"))
							else{
								chequeVO.setBankName("CITI");
							}//end of else
						}//end of if((TTKCommon.checkNull(rs.getString("BO"))).equals("UTIBANK") ||
									//(TTKCommon.checkNull(rs.getString("BO"))).equals("CITIBANK") ||
									//(TTKCommon.checkNull(rs.getString("BO"))).equals("ICICIBANK") ||
									//(TTKCommon.checkNull(rs.getString("BO"))).equals("HDFCBANK"))
						else{
							chequeVO.setBankName("CITI");
						}//end of else
					}//end of else
					chequeVO.setIncuredCurencyFormat(rs.getString("req_amt_currency_type"));
					chequeVO.setConvertedApprovedAmount(new BigDecimal(rs.getString("CONVERTED_FINAL_APPROVED_AMT")));
					if(rs.getString("DHPO_MEMBER_ID") == null || rs.getString("DHPO_MEMBER_ID").equals(""))
						chequeVO.setDhpoMemberId("");
					else
					chequeVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					alResultList.add(chequeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			log.info(" DAO getPaymentAdviceList end-->"+new Date());

			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getPaymentAdviceList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getPaymentAdviceList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getPaymentAdviceList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPaymentAdviceList(ArrayList alSearchCriteria)
	//start changes for cr koc1103 and 1105
	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public ArrayList getEftPaymentAdviceList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeVO chequeVO = null;
		String strSuppressLink[]={"0"};
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strEftPaymentAdviceList);
			
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));

			/*if(alSearchCriteria.get(1) != null){
				cStmtObject.setLong(2,(Long)alSearchCriteria.get(1));
			}//end of if(alSearchCriteria.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else*/
      
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setLong(3,0);
			/*if(alSearchCriteria.get(2) != null){
				cStmtObject.setLong(3,(Long)alSearchCriteria.get(2));
				
			}//end of if(alSearchCriteria.get(2) != null)
			else{
				cStmtObject.setLong(3,0);
			}//end of else
*/
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
          
			cStmtObject.setString(5,(String)alSearchCriteria.get(6));
			cStmtObject.setString(6,(String)alSearchCriteria.get(7));
			cStmtObject.setLong(7,(Long.parseLong(alSearchCriteria.get(8).toString())));
			cStmtObject.setLong(8,(Long.parseLong(alSearchCriteria.get(9).toString())));
			cStmtObject.setLong(9,(Long)alSearchCriteria.get(4));
			cStmtObject.setString(10,(String)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(11,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);

			if(rs != null){
				while(rs.next()){
					
					chequeVO = new ChequeVO();

					if(rs.getString("PAYMENT_SEQ_ID") != null){
						chequeVO.setSeqID(new Long(rs.getString("PAYMENT_SEQ_ID")));
					}//end of if(rs.getString("PAYMENT_SEQ_ID") != null)

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

					chequeVO.setAbbrevationCode(new String(rs.getString("ABBREVATION_CODE")));
					
					chequeVO.setSuppressLink(strSuppressLink);

					if(!(TTKCommon.checkNull(rs.getString("HO"))).equals("") && !(TTKCommon.checkNull(rs.getString("BO"))).equals("")){

						if((TTKCommon.checkNull(rs.getString("HO"))).equals("UTIBANK") ||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("CITIBANK")||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("ICICIBANK")||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("HDFCBANK") ||
						   // Change below of BOA for CR KOC1220
						    (TTKCommon.checkNull(rs.getString("HO"))).equals("BOABANK")){
							if(TTKCommon.checkNull(rs.getString("HO")).equals("UTIBANK")){
								chequeVO.setBankName("UTI");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("UTIBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("ICICIBANK")){
								chequeVO.setBankName("ICICI");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("ICICIBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("HDFCBANK")){
								chequeVO.setBankName("HDFC");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("HDFCBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("BOABANK")){
								chequeVO.setBankName("BOA");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("BOABANK"))
							else{
								chequeVO.setBankName("CITI");
							}//end of else
						}//end of if((TTKCommon.checkNull(rs.getString("HO"))).equals("UTIBANK") || (TTKCommon.checkNull(rs.getString("HO"))).equals("CITIBANK")
							//|| (TTKCommon.checkNull(rs.getString("HO"))).equals("ICICIBANK")
							//|| (TTKCommon.checkNull(rs.getString("HO"))).equals("HDFCBANK"))
						else{
							chequeVO.setBankName("CITI");
						}//end of else
					}//end of if(!(TTKCommon.checkNull(rs.getString("HO"))).equals("") && !(TTKCommon.checkNull(rs.getString("BO"))).equals(""))
					else if(!(TTKCommon.checkNull(rs.getString("HO"))).equals("")){
						if((TTKCommon.checkNull(rs.getString("HO"))).equals("UTIBANK") ||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("CITIBANK") ||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("ICICIBANK") ||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("HDFCBANK") ||
						   (TTKCommon.checkNull(rs.getString("HO"))).equals("BOABANK")){
							if(TTKCommon.checkNull(rs.getString("HO")).equals("UTIBANK")){
								chequeVO.setBankName("UTI");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("UTIBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("ICICIBANK")){
								chequeVO.setBankName("ICICI");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("ICICIBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("HDFCBANK")){
								chequeVO.setBankName("HDFC");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("HDFCBANK"))
							else if(TTKCommon.checkNull(rs.getString("HO")).equals("BOABANK")){
								chequeVO.setBankName("BOA");
							}//end of if(TTKCommon.checkNull(rs.getString("HO")).equals("BOABANK"))
							else{
								chequeVO.setBankName("CITI");
							}//end of else
						}//end of if((TTKCommon.checkNull(rs.getString("HO"))).equals("UTIBANK") || (TTKCommon.checkNull(rs.getString("HO"))).equals("ICICI"))
						else{
							chequeVO.setBankName("CITI");
						}//end of else
					}//end of else
					else{
						if((TTKCommon.checkNull(rs.getString("BO"))).equals("UTIBANK") ||
								(TTKCommon.checkNull(rs.getString("BO"))).equals("CITIBANK") ||
								(TTKCommon.checkNull(rs.getString("BO"))).equals("ICICIBANK") ||
								(TTKCommon.checkNull(rs.getString("BO"))).equals("HDFCBANK")||
								 (TTKCommon.checkNull(rs.getString("BO"))).equals("BOABANK")){
							if(TTKCommon.checkNull(rs.getString("BO")).equals("UTIBANK")){
								chequeVO.setBankName("UTI");
							}//end of if(TTKCommon.checkNull(rs.getString("BO")).equals("UTIBANK"))
							else if(TTKCommon.checkNull(rs.getString("BO")).equals("ICICIBANK")){
								chequeVO.setBankName("ICICI");
							}//end of if(TTKCommon.checkNull(rs.getString("BO")).equals("ICICIBANK"))
							else if(TTKCommon.checkNull(rs.getString("BO")).equals("HDFCBANK")){
								chequeVO.setBankName("HDFC");
							}//end of if(TTKCommon.checkNull(rs.getString("BO")).equals("UTIBANK"))
							else if(TTKCommon.checkNull(rs.getString("BO")).equals("BOABANK")){
								chequeVO.setBankName("BOA");
							}//end of if(TTKCommon.checkNull(rs.getString("BO")).equals("BOABANK"))
							else{
								chequeVO.setBankName("CITI");
							}//end of else
						}//end of if((TTKCommon.checkNull(rs.getString("BO"))).equals("UTIBANK") ||
									//(TTKCommon.checkNull(rs.getString("BO"))).equals("CITIBANK") ||
									//(TTKCommon.checkNull(rs.getString("BO"))).equals("ICICIBANK") ||
									//(TTKCommon.checkNull(rs.getString("BO"))).equals("HDFCBANK"))
						else{
							chequeVO.setBankName("CITI");
						}//end of else
					}//end of else
					chequeVO.setIncuredCurencyFormat(rs.getString("req_amt_currency_type"));
					if(!"".equals(TTKCommon.checkNull(rs.getString("CONVERTED_FINAL_APPROVED_AMT")))){
					chequeVO.setConvertedApprovedAmount(new BigDecimal(rs.getString("CONVERTED_FINAL_APPROVED_AMT")));
					}
					else{
						chequeVO.setConvertedApprovedAmount(new BigDecimal(0));
					}
					if(rs.getString("DHPO_MEMBER_ID") == null || rs.getString("DHPO_MEMBER_ID").equals(""))
						chequeVO.setDhpoMemberId("");
					else
					chequeVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					alResultList.add(chequeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getPaymentAdviceList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getPaymentAdviceList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getPaymentAdviceList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPaymentAdviceList(ArrayList alSearchCriteria)
	
	 /**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public void generateMail(String strConcatenatedSeqID,long lngUserSeqID) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeVO chequeVO = null;
		String strSuppressLink[]={"0"};
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strgenerateMail);
			cStmtObject.setString(1,strConcatenatedSeqID);

			cStmtObject.setLong(2, lngUserSeqID);
			
			
			cStmtObject.execute();
			//rs = (java.sql.ResultSet)cStmtObject.getObject(10);

			
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getPaymentAdviceList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getPaymentAdviceList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getPaymentAdviceList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPaymentAdviceList(ArrayList alSearchCriteria)
	
	//end changes for cr koc1103 and 1105
	/**
	 * This method generates the Cheque information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int generateChequeAdvice(ArrayList alChequeList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
    	try{
    		log.info("DAO generateChequeAdvice start-->"+new Date());
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateChequeAdvice);
			cStmtObject.setString(1,(String)alChequeList.get(0));//PAYMENT_SEQ_ID
			cStmtObject.setLong(2,(Long)alChequeList.get(1));//FLOAT_SEQ_ID
			cStmtObject.setString(3,(String)alChequeList.get(2));//PAYMENT_METHOD
			cStmtObject.setLong(4,(Long)alChequeList.get(3));//ADDED_BY
			cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);//ROWS_PROCESSED
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl generateChequeAdvice()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl generateChequeAdvice()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	log.info("DAO generateChequeAdvice start-->"+new Date());
		return iResult;
	}//end of generateChequeAdvice(ArrayList alChequeList)

	/**
	 * This method generates the Cheque Advice XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateChequeAdviceXL(ArrayList alChequeList) throws TTKException {
		log.info("DAO start generateChequeAdviceXL Date start"+new Date());
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		//CachedRowSet crs = null;
		OracleCachedRowSet crs = null;
        TTKReportDataSource reportDataSource =null;
		String strCitiBank = "CITI";
		String strICICIBank = "ICICI";
		String strHDFCBank = "HDFC";
		String strReportFormat = "Multi";
		// Change added for Axis Bank CR KOC1212
		String strAxis = "Axis Pay Pro Advice";
		// Change added BOA CR KOC1220
		String strBOABank = "BOA";
		Date dtDate = new Date();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateChequeAdviceXL);
			
			cStmtObject.setString(1,(String)alChequeList.get(0));

			if(alChequeList.get(1) != null){
				cStmtObject.setLong(2,(Long)alChequeList.get(1));
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(alChequeList.get(2).equals(strCitiBank)){
				cStmtObject.setString(3,"0");
			}//end of if(alChequeList.get(2).equals(strCitiBank))
			else if(alChequeList.get(2).equals(strICICIBank)){

				//cStmtObject.setString(3,"2");
				if(alChequeList.get(4).equals(strReportFormat)){
					cStmtObject.setString(3,"4");
				}//end of if(alChequeList.get(4).equals("Multi Location Format"))
				else{
					cStmtObject.setString(3,"2");
				}//end of else
			}//end of if(alChequeList.get(2).equals(strICICIBank))
			else if(alChequeList.get(2).equals(strHDFCBank)){
				cStmtObject.setString(3,"3");
			}//end of if(alChequeList.get(2).equals(strCitiBank))
			// Change added BOA CR KOC1220
			else if(alChequeList.get(2).equals(strBOABank)){
				cStmtObject.setString(3,"5");
			}//end of if(alChequeList.get(2).equals(strBOABank))
			// Change added for Axis Bank CR KOC1212
			else if(alChequeList.get(5).equals(strAxis))
			{
				cStmtObject.setString(3,"99");
			}// End change added for Axis Bank CR KOC1212
			else{
				cStmtObject.setString(3,"1");
			}//end of else

			//cStmtObject.setTimestamp(4,new Timestamp(dtDate.getTime()));
			//cStmtObject.setTimestamp(4,(Timestamp)alChequeList.get(3));//mm/dd/yyyy
			cStmtObject.setDate(4,new java.sql.Date(new java.util.Date().getTime()));//mm/dd/yyyy	
			cStmtObject.setLong(5,(Long)alChequeList.get(4));
			cStmtObject.registerOutParameter(6,Types.INTEGER);
			cStmtObject.registerOutParameter(7,Types.OTHER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);
			log.debug("iResult value is "+iResult);
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			//crs = new CachedRowSetImpl();
			crs = new OracleCachedRowSet();
			if(rs !=null)
			{
				reportDataSource = new TTKReportDataSource();
				crs.populate(rs);
				reportDataSource.setData(crs);
			}//end of if(rs !=null)
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl generateChequeAdviceXL()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl generateChequeAdviceXL()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl generateChequeAdviceXL()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		log.info("DAO generateChequeAdviceXL end-->"+new Date());
		return reportDataSource;
	}//end of generateChequeAdviceXL(ArrayList alChequeList)
	
	
	
	
	/**
	 * This method generates the Cheque Advice XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public HashMap<String, TTKReportDataSource> generateChequeAdviceENBDXL(ArrayList alChequeList) throws TTKException {
		log.info("DAO generateChequeAdviceENBDXL Date start-->"+new Date());
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		//CachedRowSet crs = null;
	     CachedRowSet crs = null;
		 CachedRowSet crs2 = null;
        TTKReportDataSource reportDataSource =null;
        HashMap hashMap	=	new HashMap();
		// Change added for Axis Bank CR KOC1212
		// Change added BOA CR KOC1220
		Date dtDate = new Date();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateChequeAdviceENBDXL);

			cStmtObject.setString(1,(String)alChequeList.get(0));
			
			if(alChequeList.get(1) != null){
				cStmtObject.setLong(2,(Long)alChequeList.get(1));
				
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else
			if(alChequeList.get(4) != null){
				cStmtObject.setLong(3,(Long)alChequeList.get(4));
	
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setLong(3,0);
			}//end of else
			cStmtObject.setTimestamp(4,(Timestamp)alChequeList.get(3));
			cStmtObject.setString(8,(String) alChequeList.get(6));
			cStmtObject.registerOutParameter(5,Types.INTEGER);
			cStmtObject.registerOutParameter(6,Types.OTHER);
			cStmtObject.registerOutParameter(7,Types.OTHER);
			
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);
			conn.commit();
			rs = (java.sql.ResultSet)cStmtObject.getObject(6);
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(7);
			
			
			
			//crs = new CachedRowSetImpl();
			crs = new CachedRowSetImpl();
			
			//Detailed report
			if(rs !=null)
			{
				
				reportDataSource = new TTKReportDataSource();
				
				
				crs.populate(rs);
				reportDataSource.setData(crs);
			}//end of if(rs !=null)
			hashMap.put("Detail", reportDataSource);
			
			
			//Consolidated Report
			crs2 = new CachedRowSetImpl();
			if(rs1 !=null)
			{
				reportDataSource = new TTKReportDataSource();
				
				crs2.populate(rs1);
				reportDataSource.setData(crs2);
				
				/*if(reportDataSource.getResultData()!=null&&reportDataSource.getResultData().next()){
					Clob bpdClob=reportDataSource.getResultData().getClob("BENEFICIARY_PAYMENT_DETAILS");
					
					if(bpdClob!=null){
						Reader reader=bpdClob.getCharacterStream();
						
						
						if(reader!=null){
							 int ch;	
	                         while ((ch = reader.read()) != -1) builder.append((char)ch);
						}
						
					}
					System.out.println("builder::"+builder);
					reportDataSource.getResultData().beforeFirst();
				}*/
				

			}//end of if(rs !=null)
		
		
			hashMap.put("Cons", reportDataSource);
			//hashMap.put("BENEFICIARY_PAYMENT_DETAILS", builder.toString());
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs1 != null) rs1.close();
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in FloatAccountDAOImpl generateChequeAdviceXL()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl generateChequeAdviceXL()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl generateChequeAdviceXL()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs1 = null;
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		log.info("DAO generateChequeAdviceENBDXL Date end-->"+new Date());
		return hashMap;
		//return reportDataSource;
	}//end of generateChequeAdviceENBDXL(ArrayList alChequeList)
	
	
	
	/**
	 * This method generates the Cheque Advice XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateChequeAdviceENBDConsXL(ArrayList alChequeList) throws TTKException {
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		//CachedRowSet crs = null;
		OracleCachedRowSet crs = null;
        TTKReportDataSource reportDataSource =null;
        HashMap<String, TTKReportDataSource> hashMap	=	new HashMap<String, TTKReportDataSource>();
		// Change added for Axis Bank CR KOC1212
		// Change added BOA CR KOC1220
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateChequeAdviceENBDConsXL);
			cStmtObject.setString(1,(String)alChequeList.get(0));
			if(alChequeList.get(1) != null){
				cStmtObject.setLong(2,(Long)alChequeList.get(1));
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			if(alChequeList.get(4) != null){
				cStmtObject.setLong(3,(Long)alChequeList.get(4));
			}//end of if(alChequeList.get(1) != null)                                                            
			else{
				cStmtObject.setString(3,null);
			}//end of else
			cStmtObject.setTimestamp(4,(Timestamp)alChequeList.get(3));
			cStmtObject.registerOutParameter(5,Types.INTEGER);
			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);
			
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			crs = new OracleCachedRowSet();
			if(rs !=null)
			{
				reportDataSource = new TTKReportDataSource();
				crs.populate(rs);
				reportDataSource.setData(crs);
			}//end of if(rs !=null)
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl generateChequeAdviceXL()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl generateChequeAdviceXL()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl generateChequeAdviceXL()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return hashMap.put("Cons", reportDataSource);
	//	return reportDataSource;
	}//end of generateChequeAdviceENBDConsXL(ArrayList alChequeList)

	/**
	 * This method generates the Cheque Advice XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateOICPaymentAdvice(ArrayList<Object> alChequeList) throws TTKException {
		log.info("DAO generateOICPaymentAdvice Date start-->"+new Date());
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		//CachedRowSet crs = null;
		OracleCachedRowSet crs = null;
        TTKReportDataSource reportDataSource =null;
        String strCitiBank = "CITI";
		String strICICIBank = "ICICI";
		String strHDFCBank = "HDFC";
		String strReportFormat = "Multi";
		Date dtDate = new Date();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strOIPaymentAdvice);
			
			
			cStmtObject.setString(1,(String)alChequeList.get(0));

			if(alChequeList.get(1) != null){
				cStmtObject.setLong(2,(Long)alChequeList.get(1));
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else

			if(alChequeList.get(2).equals(strCitiBank)){
				cStmtObject.setString(3,"0");
			}//end of if(alChequeList.get(2).equals(strCitiBank))
			else if(alChequeList.get(2).equals(strICICIBank)){
				cStmtObject.setString(3,"2");
			}//end of if(alChequeList.get(2).equals(strICICIBank))
			else if(alChequeList.get(2).equals(strHDFCBank)){
				cStmtObject.setString(3,"3");
			}//end of if(alChequeList.get(2).equals(strCitiBank))
			else{
				cStmtObject.setString(3,"1");
			}//end of else			

			//cStmtObject.setTimestamp(4,new Timestamp(dtDate.getTime()));
			cStmtObject.setTimestamp(4,(Timestamp)alChequeList.get(3));
			cStmtObject.setLong(5,(Long)alChequeList.get(4));
			cStmtObject.registerOutParameter(6,Types.INTEGER);
			cStmtObject.registerOutParameter(7,Types.OTHER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);
			log.info("iResult value is "+iResult);
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			//crs = new CachedRowSetImpl();
			crs = new OracleCachedRowSet();
			if(rs !=null)
			{
				reportDataSource = new TTKReportDataSource();
				crs.populate(rs);
				reportDataSource.setData(crs);
			}//end of if(rs !=null)
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl generateOICPaymentAdvice()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl generateOICPaymentAdvice()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl generateOICPaymentAdvice()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		log.info("DAO generateOICPaymentAdvice Date end-->"+new Date());
		return reportDataSource;
	}//end of generateOICPaymentAdvice(ArrayList alChequeList)

	/**
	 * This method generates the Cheque Advice XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateChequeAdviceXLDetails(ArrayList alChequeList) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CachedRowSet crs = null;
        TTKReportDataSource reportDataSource =null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateChequeAdviceXLDetails);
			cStmtObject.setString(1,(String)alChequeList.get(0));
			if(alChequeList.get(1) != null){
				cStmtObject.setLong(2,(Long)alChequeList.get(1));
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			crs = new CachedRowSetImpl();
			if(rs !=null)
			{
				reportDataSource = new TTKReportDataSource();
				crs.populate(rs);
				reportDataSource.setData(crs);
			}//end of if(rs !=null)
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl generateChequeAdviceXLDetails()",sqlExp);
					sqlExp.printStackTrace();
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl generateChequeAdviceXLDetails()",sqlExp);
						sqlExp.printStackTrace();
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl generateChequeAdviceXLDetails()",sqlExp);
							sqlExp.printStackTrace();
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (Exception exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return reportDataSource;
	}//end of generateChequeAdviceXLDetails(ArrayList alChequeList)
	
	
	// Change added for BOA CR KOC1220
	/**
	 * This method generates the Cheque Advice XL information
	 * @param alChequeList contains Seq ID's to Print the Cheque
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public TTKReportDataSource generateBOAXLDetails(ArrayList alChequeList) throws TTKException {
		System.out.println(" DAO getPaymentAdviceList start-->"+new Date());
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		//CachedRowSet crs = null;
		OracleCachedRowSet crs = null;
        TTKReportDataSource reportDataSource =null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateBOAXLDetails);			
			cStmtObject.setString(1,(String)alChequeList.get(0));
			if(alChequeList.get(1) != null){
				cStmtObject.setLong(2,(Long)alChequeList.get(1));
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
		//	crs = new CachedRowSetImpl();
			crs = new OracleCachedRowSet();
			if(rs !=null)
			{
				reportDataSource = new TTKReportDataSource();
				crs.populate(rs);
				reportDataSource.setData(crs);
			}//end of if(rs !=null)
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl generateBOAXLDetails()",sqlExp);
					sqlExp.printStackTrace();
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl generateBOAXLDetails()",sqlExp);
						sqlExp.printStackTrace();
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl generateBOAXLDetails()",sqlExp);
							sqlExp.printStackTrace();
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (Exception exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		System.out.println(" DAO getPaymentAdviceList end-->"+new Date());

		return reportDataSource;
	}//end of generateBOAXLDetails(ArrayList alChequeList)
	

	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Payment Advice
	 * @exception throws TTKException
	 */
	public ArrayList getViewPaymentAdviceList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeVO chequeVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strViewPaymentAdviceList);
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			if((String)alSearchCriteria.get(2)== null || alSearchCriteria.get(2).equals("")){
				cStmtObject.setLong(3,0);
			}
			else{
				cStmtObject.setLong(3,(Long.parseLong(alSearchCriteria.get(2).toString())));
			}
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setLong(8,(Long.parseLong(alSearchCriteria.get(8).toString())));
			cStmtObject.setLong(9,(Long.parseLong(alSearchCriteria.get(9).toString())));
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(2,Types.VARCHAR);
			cStmtObject.registerOutParameter(4,Types.VARCHAR);
			cStmtObject.registerOutParameter(11,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			conn.commit();

			if(rs != null){
				while(rs.next()){
					chequeVO = new ChequeVO();

					if(rs.getString("BATCH_SEQ_ID") != null){
						chequeVO.setSeqID(new Long(rs.getString("BATCH_SEQ_ID")));
					}//end of if(rs.getString("BATCH_SEQ_ID") != null)

					if(rs.getTimestamp("BATCH_DATE") != null){
						chequeVO.setBatchDate(new Date(rs.getTimestamp("BATCH_DATE").getTime()));
					}//end of if(rs.getTimestamp("BATCH_DATE") != null)

					chequeVO.setBatchNumber(TTKCommon.checkNull(rs.getString("BATCH_SEQ_ID")));
					chequeVO.setFileName(TTKCommon.checkNull(rs.getString("FILE_NAME")));
					
					if(rs.getString("BANK_NAME").equals("CITIBANK")){
						chequeVO.setBankName("CITI");
					}//end of if(rs.getString("BANK_NAME").equals("CITIBANK"))
					else if(rs.getString("BANK_NAME").equals("UTIBANK")){
						chequeVO.setBankName("UTI");
					}//end of if(rs.getString("BANK_NAME").equals("UTIBANK"))
					else if(rs.getString("BANK_NAME").equals("ICICIBANK")){
						chequeVO.setBankName("ICICI");
					}//end of if(rs.getString("BANK_NAME").equals("ICICIBANK"))
					else if(rs.getString("BANK_NAME").equals("HDFCBANK")){
						chequeVO.setBankName("HDFC");
					}//end of if(rs.getString("BANK_NAME").equals("HDFCBANK"))

					chequeVO.setAddedBy(new Long(rs.getLong("ADDED_BY")));
					alResultList.add(chequeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getViewPaymentAdviceList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getViewPaymentAdviceList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getViewPaymentAdviceList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getViewPaymentAdviceList(ArrayList alSearchCriteria)

     /**
     * This method returns the ArrayList, which contains the InviceVO's which are populated from the database
     * @param strPolicySeqID String which contains Policy Seq ID
     * @param strEnrollType String which contains Enrollment Type
     * @param lngAddedBy Long which contains Added User Seq ID
     * @return ArrayList of InviceVO's object's which contains the details of the Invice
     * @exception throws TTKException
     */
    public TTKReportDataSource getGenerateInvoiceList(String strPolicySeqID,String strEnrollType,Long lngAddedBy) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        TTKReportDataSource reportDataSource =null;
        CachedRowSet crs = null;
        Date dtDate = new Date();
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strIndividualInvoiceList);
            cStmtObject.setString(1,strPolicySeqID);
            cStmtObject.setString(2,strEnrollType);
            cStmtObject.setTimestamp(3,new Timestamp(dtDate.getTime()));
            cStmtObject.setLong(4,lngAddedBy);
            cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(5);
            crs = new CachedRowSetImpl();
            if(rs != null){
                reportDataSource = new TTKReportDataSource();
                crs.populate(rs);
                reportDataSource.setData(crs);
            }//end of if(rs != null)
            return reportDataSource;
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "floataccount");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getGenerateInvoiceList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getGenerateInvoiceList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getGenerateInvoiceList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getGenerateInvoiceList(String strPolicySeqID)

    /**
     * This method returns the ArrayList, which contains the InviceVO's which are populated from the database
     * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return ArrayList of InviceVO's object's which contains the details of the Invoice
     * @exception throws TTKException
     */
    public ArrayList getViewInvoiceList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        InvoiceVO invoice = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strViewInvoiceList);
            if((Long)alSearchCriteria.get(0)!=null)
            {
                cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            }//end of if((Long)alSearchCriteria.get(0)!=null)
            else
            {
                cStmtObject.setString(1,null);
            }//end of else
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));

            if(!TTKCommon.checkNull((String)alSearchCriteria.get(2)).equals(""))
            {
                cStmtObject.setLong(3,TTKCommon.getLong((String)alSearchCriteria.get(2)));
            }//end of if(!TTKCommon.checkNull((String)alSearchCriteria.get(2)).equals(""))
            else
            {
                cStmtObject.setString(3,null);
            }//end of else

            if(!TTKCommon.checkNull((String)alSearchCriteria.get(3)).equals(""))
            {
                cStmtObject.setLong(4,TTKCommon.getLong((String)alSearchCriteria.get(3)));
            }//end of if(!TTKCommon.checkNull((String)alSearchCriteria.get(3)).equals(""))
            else
            {
                cStmtObject.setString(4,null);
            }//end of else

            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
            cStmtObject.setLong(7,(Long)alSearchCriteria.get(6));
            cStmtObject.setString(8,(String)alSearchCriteria.get(7));
            cStmtObject.setString(9,(String)alSearchCriteria.get(8));
            cStmtObject.setString(10,(String)alSearchCriteria.get(9));
            cStmtObject.setString(11,(String)alSearchCriteria.get(10));
            cStmtObject.registerOutParameter(12,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(12);

            if(rs != null){
                alResultList = new ArrayList<Object>();
                while(rs.next()){
                    invoice = new InvoiceVO();
                    invoice.setSeqID(rs.getString("FIN_POLICY_SEQ_ID")!=null ? new Long(rs.getLong("FIN_POLICY_SEQ_ID")):null);
                    invoice.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    invoice.setProductName(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                    invoice.setTotalPremium(rs.getString("NET_PREMIUM")!=null ? new BigDecimal(rs.getString("NET_PREMIUM")):null);
                    invoice.setCommision(rs.getString("COMMISSION")!=null ? new BigDecimal(rs.getString("COMMISSION")):new BigDecimal(0));
                    invoice.setCommissionAmt(rs.getString("COMMISSION_AMT")!=null ? new BigDecimal(rs.getString("COMMISSION_AMT")):new BigDecimal(0));
                    alResultList.add(invoice);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "floataccount");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getViewInvoiceList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getViewInvoiceList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getViewInvoiceList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getViewInvoiceList(ArrayList alSearchCriteria)

    /**
     * This method returns the ArrayList, which contains the InvoiceBatchVO's which are populated from the database
     * @param alSearchCriteria ArrayList which contains Search Criteria
     * @return ArrayList of InvoiceBatchVO's object's which contains the details of the Invoice
     * @exception throws TTKException
     */
    public ArrayList getViewInvoiceBatch(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        InvoiceBatchVO invoiceBatchVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strViewInvoiceBatch);
            cStmtObject.setString(1,(String)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setLong(4,(Long)alSearchCriteria.get(3));
            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));
            cStmtObject.setString(8,(String)alSearchCriteria.get(7));
            cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(9);

            if(rs != null){
                alResultList = new ArrayList<Object>();
                while(rs.next()){
                    invoiceBatchVO = new InvoiceBatchVO();
                    invoiceBatchVO.setSeqID(rs.getString("BATCH_SEQ_ID")!=null ? new Long(rs.getLong("BATCH_SEQ_ID")):null);
                    invoiceBatchVO.setBatchDate(rs.getString("BATCH_DATE")!=null ? new Date(rs.getTimestamp("BATCH_DATE").getTime()):null);
                    invoiceBatchVO.setNbrPolicy(TTKCommon.checkNull(rs.getString("NO_OF_POLICIES")));
                    invoiceBatchVO.setBatchName(TTKCommon.checkNull(rs.getString("BATCH_NAME")));
                    alResultList.add(invoiceBatchVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "floataccount");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getViewInvoiceBatch()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getViewInvoiceBatch()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getViewInvoiceBatch()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getViewInvoiceBatch(ArrayList alSearchCriteria)

    /**
     * This method deletes the Invoice Detail information from the database
     * @param alDeleteList ArrayList object which contains seq id for Invoice flow to delete the Invoice information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteInvoice(ArrayList alDeleteList) throws TTKException
    {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            if(alDeleteList != null && alDeleteList.size() > 0){
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteInvoice);
                cStmtObject.setString(1,(String)alDeleteList.get(0));
                cStmtObject.setLong(2,(Long)alDeleteList.get(1));
                cStmtObject.registerOutParameter(3,Types.INTEGER);
                cStmtObject.execute();
                iResult = cStmtObject.getInt(3);
            }//end of if(alDeleteList != null && alDeleteList.size() > 0)

        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "floataccount");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl deleteInvoice()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl deleteInvoice()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of deleteInvoice(ArrayList alDeleteList)

    /**
	 * This method generates the EFT information
	 * @param alChequeList contains Seq ID's to generate EFT information
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int setFundTransfer(ArrayList alChequeList) throws TTKException{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
    	try{
    		log.info("DAO setFundTransfer start-->"+new Date());
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSetFundTransfer);
			cStmtObject.setString(1,(String)alChequeList.get(0));//PAYMENT_SEQ_ID
			cStmtObject.setLong(2,(Long)alChequeList.get(1));//FLOAT_SEQ_ID
			cStmtObject.setString(3,(String)alChequeList.get(2));//REMARKS
			cStmtObject.setString(4,(String)alChequeList.get(3));//PAYMENT_METHOD
			cStmtObject.setLong(5,(Long)alChequeList.get(4));//ADDED_BY
			cStmtObject.registerOutParameter(6, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(6);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl setFundTransfer()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl setFundTransfer()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	log.info("DAO setFundTransfer end-->"+new Date());
		return iResult;
	}//end of setFundTransfer(ArrayList alChequeList)

/**
	 * This method returns the String which contains the Batch file name to save the file with the Batch file name
	 * @param strBatchNo long value which contains Batch number to get the Batch file name
	 * @return String this contains the Batch file name
	 * @exception throws TTKException
	 */
	public ArrayList<CacheObject> getSelectedProducts(String insSeqId) throws TTKException
	{
	Connection conn = null;
		PreparedStatement statement=null;
		ArrayList<CacheObject> arrayList = new ArrayList<>();
		ResultSet rs=null;
		
    	try{
			conn = ResourceManager.getConnection();
			statement =conn.prepareStatement("SELECT P.PRODUCT_SEQ_ID,P.PRODUCT_NAME FROM app.TPA_INS_PRODUCT P WHERE P.INS_SEQ_ID :: text=?");
			statement.setString(1,insSeqId);
			 rs=statement.executeQuery();
		 if(rs != null){
					while(rs.next()){
						
						CacheObject SelectedProducts=new CacheObject();
						SelectedProducts.setCacheId(TTKCommon.checkNull(rs.getString("PRODUCT_SEQ_ID")));
						SelectedProducts.setCacheDesc(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
						arrayList.add(SelectedProducts);
					}//end of while(rs.next())
				}//end of if(rs != null)
			 
			}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getBatchClaimList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (statement != null)	statement.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getBatchClaimList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getBatchClaimList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				statement = null;
				conn = null;
			}//end of finally
		}//end of finally
		return arrayList;
   }//end of getBatchClaimList(ArrayList alSearchCriteria)
	
	
	
	/**
	 * This method returns the String which contains the Batch file name to save the file with the Batch file name
	 * @param strBatchNo long value which contains Batch number to get the Batch file name
	 * @return String this contains the Batch file name
	 * @exception throws TTKException
	 */
	public String getBatchFileName(String strBatchNo) throws TTKException
	{
		log.info("DAO getBatchFileName start-->"+new Date());
		String strResult = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
    	try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBatchFileName);
			
			
			cStmtObject.setString(1,(String)strBatchNo);
			cStmtObject.registerOutParameter(2,OracleTypes.VARCHAR);
			cStmtObject.execute();
			strResult =(String)cStmtObject.getObject(2);
    	}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl setFundTransfer()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl setFundTransfer()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	log.info("DAO getBatchFileName end-->"+new Date());
		return strResult;

	}//end of getBatchFileName(String strBatchNo)

	/**
	 * This method returns the ArrayList, which contains the ChequeVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of ChequeVO's object's which contains the details of the Claims
	 * @exception throws TTKException
	 */
	public ArrayList getBatchClaimList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ChequeVO chequeVO = null;
		String strSuppressLink[]={"0"};
		String strSuppressLink1[]={"0","7"};
		String strTDSYN = "", strCigna_YN="";//2nd String added for Cigna Mail-SMS
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBatchClaimList);
			//start changes for cr koc1103 and 1105
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
            cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setLong(8,(Long.parseLong(alSearchCriteria.get(8).toString())));
			cStmtObject.setLong(9,(Long.parseLong(alSearchCriteria.get(9).toString())));
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(11,Types.OTHER);
			//end changes for cr koc1103 and 1105
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			conn.commit();
			if(rs != null){
				while(rs.next()){

					chequeVO = new ChequeVO();
					if(rs.getString("PAYMENT_SEQ_ID") != null){
						chequeVO.setSeqID(new Long(rs.getString("PAYMENT_SEQ_ID")));
					}//end of if(rs.getString("PAYMENT_SEQ_ID") != null)

					chequeVO.setClaimSettNo(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NO")));
					chequeVO.setEnrollID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					chequeVO.setClaimantName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					chequeVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));

					if(rs.getTimestamp("CLAIM_APRV_DATE") != null){
						chequeVO.setApprDate(new Date(rs.getTimestamp("CLAIM_APRV_DATE").getTime()));
					}//end of if(rs.getTimestamp("CLAIM_APRV_DATE") != null)

					chequeVO.setInFavorOf(TTKCommon.checkNull(rs.getString("IN_FAVOUR_OF")));
					if(rs.getString("DHPO_MEMBER_ID") == null || rs.getString("DHPO_MEMBER_ID").equals(""))
						chequeVO.setDhpoMemberId("");
					else
					chequeVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					if(rs.getString("CHECK_NUM") != null){
						chequeVO.setChequeNo(TTKCommon.checkNull(rs.getString("CHECK_NUM")));
					}//end of if(rs.getString("CHECK_NUM") != null)

					strTDSYN = TTKCommon.checkNull(rs.getString("TDS_PROCESS_YN"));
					//added for Mail-SMS Template for Cigna
					//chequeVO.setCigna_YN(TTKCommon.checkNull(rs.getString("CIGNA_YN")));
					
					//ended

					if(strTDSYN.equals("N")){
						chequeVO.setSuppressLink(strSuppressLink1);
					}//end of if(strTDSYN.equals("N"))
					else{
						chequeVO.setSuppressLink(strSuppressLink);
					}//end of else
					alResultList.add(chequeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getBatchClaimList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getBatchClaimList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getBatchClaimList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBatchClaimList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ArrayList, which contains the AssocGroupVO's which are populated from the database.
	 * @param lngFloatSeqID Long which contains Search Criteria
	 * @return ArrayList of AssocGroupVO'S object's which contains the details of the Groups associated to Float Account
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getFloatAssocGrpList(Long lngFloatSeqID) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		AssocGroupVO assocGroupVO =null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strFloatAssocGrp);
			cStmtObject.setLong(1,lngFloatSeqID.longValue());
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);

			if(rs != null){
				while(rs.next()){
					assocGroupVO = new AssocGroupVO();

					if(rs.getString("TPA_FLOAT_GROUP_ASSOC_SEQ") != null){
						assocGroupVO.setFloatGrpAssSeqID(new Long(rs.getLong("TPA_FLOAT_GROUP_ASSOC_SEQ")));
					}//end of if(rs.getString("TPA_FLOAT_GROUP_ASSOC_SEQ") != null)

					assocGroupVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					assocGroupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					assocGroupVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NO")));
					assocGroupVO.setPolicyCategory(TTKCommon.checkNull(rs.getString("CLASSIFICATION")));
					log.info("TPA_FLOAT_GROUP_ASSOC_SEQ is : "+assocGroupVO.getFloatGrpAssSeqID());
					log.info("GROUP_ID is                  : "+assocGroupVO.getGroupID());
					log.info("GROUP_NAME is                : "+assocGroupVO.getGroupName());
					log.info("POLICY_NO for     intX       : "+assocGroupVO.getPolicyNo());

					alResultList.add(assocGroupVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList<Object>)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
					log.error("Error while closing the Resultset in FloatAccountDAOImpl getFloatAssocGrpList()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl getFloatAssocGrpList()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl getFloatAssocGrpList()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getFloatAssocGrpList(Long lngFloatSeqID)

	/**
	 * This method associates the Group information to Float account.
	 * @param assocGroupVO the object which contains the details of the Associate Group information
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @exception throws TTKException
	 */
	public int saveFloatAssocGrp(AssocGroupVO assocGroupVO) throws TTKException {
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveFloatAssocGrp);
			cStmtObject.setLong(1,assocGroupVO.getFloatSeqID().longValue());
			cStmtObject.setLong(2,assocGroupVO.getGroupRegSeqID().longValue());
			cStmtObject.setString(3,assocGroupVO.getPolicyNo());
			cStmtObject.setLong(4,assocGroupVO.getUpdatedBy().longValue());
			cStmtObject.setString(5,assocGroupVO.getPolicyCategory());  //Rishi
			cStmtObject.registerOutParameter(6,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);//ROWS_PROCESSED
			conn.commit();
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl saveFloatAssocGrp()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl saveFloatAssocGrp()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveFloatAssocGrp(AssocGroupVO assocGroupVO)

	/**
	 * This method deletes the Associated Group information from the database.
	 * @param lngFloatGrpAssocSeqID Long object which contains seq id for Finance flow to delete the Associate Group information
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteFloatAssocGrp(Long lngFloatGrpAssocSeqID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteFloatAssocGrp);
			cStmtObject.setLong(1,lngFloatGrpAssocSeqID.longValue());
			cStmtObject.registerOutParameter(2,Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(2);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Statement in FloatAccountDAOImpl deleteFloatAssocGrp()",sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in FloatAccountDAOImpl deleteFloatAssocGrp()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "floataccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteFloatAssocGrp(Long lngFloatGrpAssocSeqID)
	
	
	public long getENBDCountandAccNum(long floatSeqId,String fileName) throws TTKException {
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cStmtObject=null;
		long	iResult = 0;
		OracleCachedRowSet crs = null;
        TTKReportDataSource reportDataSource =null;
      
		try{
			conn = ResourceManager.getConnection();
			
			
			
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strENBDcount);
				cStmtObject.setLong(1,floatSeqId);
				cStmtObject.setString(2,fileName);
				//cStmtObject.registerOutParameter(3,Types.INTEGER);
				cStmtObject.execute();
				//iResult = cStmtObject.getInt(3);
				
				
		//	}
			
			/*else
			{
		
			if(floatSeqId!=0){
				System.out.println("input >>"+floatSeqId);
				pstmt.setLong(1,(Long)floatSeqId);
			}//end of if(alChequeList.get(1) != null)
			else{
				pstmt.setString(1,null);
			}//end of else
			*/
			
		// rs=pstmt.executeQuery();
			
		
			/*if(rs !=null)
			{
				while(rs.next())
				{

				iResult=rs.getLong("cnt");
				
				}
			}*///end of if(rs !=null)
			//}
				
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Resultset in FloatAccountDAOImpl getENBDCountandAccNum()",sqlExp);
        			throw new TTKException(sqlExp, "policy");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if result set is not closed, control reaches here. Try closing the statement now.
        		{
        			try
        			{
        				if (cStmtObject != null) cStmtObject.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Statement in FloatAccountDAOImpl getENBDCountandAccNum()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in FloatAccountDAOImpl getENBDCountandAccNum()",sqlExp);
        					throw new TTKException(sqlExp, "floataccount");
        				}//end of catch (SQLException sqlExp)
        			}//end of finally Connection Close
        		}//end of try
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tTkReports");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs = null;
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
        }//end of finally
		return iResult;
		//return reportDataSource;
	}//end of generateChequeAdviceENBDXL(ArrayList alChequeList)



	public ArrayList setPaymentUploadExcel(ArrayList<Object> alPrintCheque) throws TTKException {
		// TODO Auto-generated method stub
		Collection<Object> alResultList = new ArrayList<Object>();
		ChequeVO chequeVO = null;
		ResultSet rs = null;
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		String strSuppressLink[]={"7"};
		String strTDSYN = "";
    	try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGeneratePaymentAdviceByXLUpload);
			log.info("1------"+(String)alPrintCheque.get(0));
			cStmtObject.setString(1,(String)alPrintCheque.get(0));//CLAIM_SETTLEMENT_NUMBER
			log.info("2------"+(Long)alPrintCheque.get(1));
			cStmtObject.setLong(2,(Long)alPrintCheque.get(1));//FLOAT_SEQ_ID
			log.info("3------"+(String)alPrintCheque.get(2));
			cStmtObject.setString(3,(String)alPrintCheque.get(2));//CURRENCY TYPE
			log.info("4--------"+(String)alPrintCheque.get(3));
			cStmtObject.setString(4,(String)alPrintCheque.get(3));//PAYMENT METHOD
			log.info("5-------"+(String)alPrintCheque.get(4));
			cStmtObject.setString(5,(String)alPrintCheque.get(4));//claimtype
			if(alPrintCheque.get(5)== null || alPrintCheque.get(5).toString().equals("")){
				cStmtObject.setLong(6,0);
			}
			else{
				log.info("6---------"+alPrintCheque.get(5).toString());
				cStmtObject.setLong(6,(Long.parseLong(alPrintCheque.get(5).toString())));
			}
			cStmtObject.registerOutParameter(7, Types.INTEGER);
			cStmtObject.registerOutParameter(8,Types.OTHER);	
			
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
			rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            conn.commit();
			
			if(rs != null){
				while(rs.next()){

					chequeVO = new ChequeVO();
					if(rs.getString("PAYMENT_SEQ_ID") != null){
						chequeVO.setSeqID(new Long(rs.getString("PAYMENT_SEQ_ID")));
					}//end of if(rs.getString("PAYMENT_SEQ_ID") != null)

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

					if(rs.getString("CURRENT_BALANCE") != null){
						chequeVO.setAvblFloatBalance(new BigDecimal(rs.getString("CURRENT_BALANCE")));
					}//end of if(rs.getString("CURRENT_BALANCE") != null)

					chequeVO.setFileName(TTKCommon.checkNull(rs.getString("TEMPLATE_NAME")));
					chequeVO.setRemarks("");
					strTDSYN = TTKCommon.checkNull(rs.getString("TDS_PROCESS_YN"));
	
					if(strTDSYN.equals("N")){
						//chequeVO.setSuppressLink(strSuppressLink);
					}//end of if(strTDSYN.equals("N"))
					chequeVO.setIncuredCurencyFormat(rs.getString("req_amt_currency_type"));
					if(rs.getString("CONVERTED_FINAL_APPROVED_AMT")!=null)
					chequeVO.setConvertedApprovedAmount(new BigDecimal(rs.getString("CONVERTED_FINAL_APPROVED_AMT")));
					else chequeVO.setConvertedApprovedAmount(new BigDecimal("0.0"));
					if(rs.getString("DHPO_MEMBER_ID") == null || rs.getString("DHPO_MEMBER_ID").equals(""))
						chequeVO.setDhpoMemberId("");
					else
					chequeVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					//chequeVO.setConvertedApprovedAmount(rs.getBigDecimal("CONVERTED_FINAL_APPROVED_AMT"));
					alResultList.add(chequeVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Resultset in FloatAccountDAOImpl setPaymentUploadExcel()",sqlExp);
        			throw new TTKException(sqlExp, "policy");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if result set is not closed, control reaches here. Try closing the statement now.
        		{
        			try
        			{
        				if (cStmtObject != null) cStmtObject.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Statement in FloatAccountDAOImpl setPaymentUploadExcel()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in FloatAccountDAOImpl setPaymentUploadExcel()",sqlExp);
        					throw new TTKException(sqlExp, "floataccount");
        				}//end of catch (SQLException sqlExp)
        			}//end of finally Connection Close
        		}//end of try
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tTkReports");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs = null;
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
        }//end of finally
		
	}

	public ArrayList getLogDetailsExcelUpload(String startDate, String endDate,String flag) throws TTKException {
		// TODO Auto-generated method stub
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ArrayList<ReportVO> alRresultList	=	null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strLogDetailReport);
        	cStmtObject.setString(1, startDate);
        	cStmtObject.setString(2, endDate);
        	cStmtObject.setString(3, flag);
        	cStmtObject.registerOutParameter(4,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            
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
            throw new TTKException(sqlExp, "floataccount");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Resultset in FloatAccountDAOImpl getLogDetailsExcelUpload()",sqlExp);
        			throw new TTKException(sqlExp, "policy");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if result set is not closed, control reaches here. Try closing the statement now.
        		{
        			try
        			{
        				if (cStmtObject != null) cStmtObject.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Statement in FloatAccountDAOImpl getLogDetailsExcelUpload()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in FloatAccountDAOImpl getLogDetailsExcelUpload()",sqlExp);
        					throw new TTKException(sqlExp, "floataccount");
        				}//end of catch (SQLException sqlExp)
        			}//end of finally Connection Close
        		}//end of try
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tTkReports");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs = null;
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
        }//end of finally
		
	}

	public HashMap<String, TTKReportDataSource> generateChequeAdviceUploadENBDXL(ArrayList<Object> alGenerateXL) throws TTKException
	{
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		//CachedRowSet crs = null;
		CachedRowSet crs = null;
        TTKReportDataSource reportDataSource =null;
        HashMap hashMap	=	new HashMap();
		// Change added for Axis Bank CR KOC1212
		// Change added BOA CR KOC1220
		Date dtDate = new Date();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGenerateChequeAdviceENBDXLUpload);
			log.info("1--------"+(String)alGenerateXL.get(0));
			cStmtObject.setString(1,(String)alGenerateXL.get(0));
			
			if(alGenerateXL.get(1) != null){
				log.info("2--------"+(Long)alGenerateXL.get(1));
				cStmtObject.setLong(2,(Long)alGenerateXL.get(1));
				
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else
			if(alGenerateXL.get(2) != null){
				log.info("3--------"+(Long)alGenerateXL.get(2));
				cStmtObject.setLong(3,(Long)alGenerateXL.get(2));
	
			}//end of if(alChequeList.get(1) != null)
			else{
				cStmtObject.setLong(3,0);
			}//end of else
			log.info("4--------"+(Timestamp)alGenerateXL.get(3));
			cStmtObject.setTimestamp(4,(Timestamp)alGenerateXL.get(3));
			log.info("5--------"+(String)alGenerateXL.get(5));
			cStmtObject.setString(5,(String)alGenerateXL.get(5));
			log.info("9--------"+(String)alGenerateXL.get(4));
			cStmtObject.setString(9,(String) alGenerateXL.get(4));
			cStmtObject.registerOutParameter(6,Types.INTEGER);
			cStmtObject.registerOutParameter(7,Types.OTHER);
			cStmtObject.registerOutParameter(8,Types.OTHER);
			
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);
			rs = (java.sql.ResultSet)cStmtObject.getObject(7);
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(8);
			conn.commit();
			crs = new CachedRowSetImpl();
			if(rs !=null)
			{
				reportDataSource = new TTKReportDataSource();
				crs.populate(rs);
				reportDataSource.setData(crs);
			}//end of if(rs !=null)
			hashMap.put("Detail", reportDataSource);
			
			
			//Consolidated Report
			crs = new CachedRowSetImpl();
			if(rs1 !=null)
			{
				reportDataSource = new TTKReportDataSource();
				
				crs.populate(rs1);
				reportDataSource.setData(crs);
				
			}//end of if(rs !=null)
		
			hashMap.put("Cons", reportDataSource);
		
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "floataccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "floataccount");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in FloatAccountDAOImpl generateChequeAdviceUploadENBDXL()",sqlExp);
					throw new TTKException(sqlExp, "floataccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in FloatAccountDAOImpl generateChequeAdviceUploadENBDXL()",sqlExp);
						throw new TTKException(sqlExp, "floataccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in FloatAccountDAOImpl generateChequeAdviceUploadENBDXL()",sqlExp);
							throw new TTKException(sqlExp, "floataccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "floataccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs1 = null;
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return hashMap;
		//return reportDataSource;
	}

	public ArrayList generateChequeAdviceUploadENBDXL(String startDate,String endDate) throws TTKException
	{
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ArrayList<ReportVO> alRresultList	=	null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strLogDetailReport);
        	cStmtObject.setString(1, startDate);
        	cStmtObject.setString(2, endDate);
        	cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            
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
            
            return (ArrayList)alRresultList;
        }
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "floataccount");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "floataccount");
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
        			log.error("Error while closing the Resultset in FloatAccountDAOImpl getLogDetailsExcelUpload()",sqlExp);
        			throw new TTKException(sqlExp, "policy");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if result set is not closed, control reaches here. Try closing the statement now.
        		{
        			try
        			{
        				if (cStmtObject != null) cStmtObject.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Statement in FloatAccountDAOImpl getLogDetailsExcelUpload()",sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in FloatAccountDAOImpl getLogDetailsExcelUpload()",sqlExp);
        					throw new TTKException(sqlExp, "floataccount");
        				}//end of catch (SQLException sqlExp)
        			}//end of finally Connection Close
        		}//end of try
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "tTkReports");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		rs = null;
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
        }//end of finally
		
	}


	

	

	/*public static void main(String a[]) throws Exception {
		FloatAccountDAOImpl floatAcctDAO = new FloatAccountDAOImpl();
		
		//Date dtDate = new Date();
		//Timestamp dtTimestamp = new Timestamp(dtDate.getTime());
		
		
		
		
		
		
		
		

		//ArrayList<Object> alAssocGrpList = new ArrayList<Object>();
		//AssocGroupVO assocGroupVO = new AssocGroupVO();
		//FloatAccountDetailVO floatAccountDetailVO = floatAcctDAO.getFloatAccountDetail(482, 56503);
		//alAssocGrpList = floatAccountDetailVO.getAssocGrpList();
		//if(alAssocGrpList !=null){
			//for(int i=0;i<alAssocGrpList.size();i++){
				//assocGroupVO = (AssocGroupVO)alAssocGrpList.get(i);
				
				
				
				
			//}//end of for
		//}//end of if(alAssocGrpList !=null || alAssocGrpList.size()<0)

		//floatAcctDAO.getFloatAssocGrpList(new Long(482));
		//AssocGroupVO assocGroupVO = new AssocGroupVO();
		//assocGroupVO.setFloatSeqID(new Long(482));
		//assocGroupVO.setGroupRegSeqID(new Long(2101));
		//assocGroupVO.setUpdatedBy(new Long(56503));
		//floatAcctDAO.saveFloatAssocGrp(assocGroupVO);

		//floatAcctDAO.deleteFloatAssocGrp(new Long(3));

	}//end of main
*/}//end of FloatAccountDAOImpl
	
