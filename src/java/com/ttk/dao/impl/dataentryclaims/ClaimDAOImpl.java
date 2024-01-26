/**
 * @ (#) ClaimDAOImpl.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimDAOImpl.java
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
import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.PolicyClauseVO;
import com.ttk.dto.claims.ClaimDetailVO;
import com.ttk.dto.claims.ClaimInwardDetailVO;
import com.ttk.dto.claims.ClaimInwardVO;
import com.ttk.dto.claims.ClauseVO;
import com.ttk.dto.claims.DocumentChecklistVO;
import com.ttk.dto.claims.HospitalizationDetailVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.security.GroupVO;

public class ClaimDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(ClaimDAOImpl.class );

	/*private static final String strGetClaimInwardList = "{CALL CLAIMS_SQL_PKG.SELECT_CLM_INWARD_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetClaimInwardDetail = "{CALL CLAIMS_SQL_PKG.SELECT_CLM_INWARD_DTL(?,?,?,?,?)}";
	private static final String strGetClaimDocumentList = "{CALL CLAIMS_SQL_PKG.SELECT_CLM_INWARD_DOCUMENT(?,?)}";*/
	private static final String strGetClaimInwardList = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_CLM_INWARD_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetClaimInwardDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_CLM_INWARD_DTL(?,?,?,?,?)}";
	private static final String strClaimShortfallList = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_CLM_SHORTFALL_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strInwardShortfallDetails = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_OPEN_SHORTFALL_LIST(?,?,?)}";
	private static final String strGetClaimDocumentList = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_CLM_INWARD_DOCUMENT(?,?)}";
	private static final String strSaveClaimInwardDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SAVE_CLM_INWARD_ENTRY(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveDocumentChecklist = "{CALL CLAIMS_PKG_DATA_ENTRY.SAVE_CLM_DOCUMENT(";
	/*private static final String strGetClaimList = "{CALL CLAIMS_SQL_PKG.SELECT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetClaimDetail = "{CALL CLAIMS_SQL_PKG.SELECT_CLAIM(?,?,?,?,?)}";*/
	//private static final String strGetClaimList = "{CALL CLAIMS_PKG.SELECT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//changed above Procedure Package
	private static final String strGetClaimList = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	
	//private static final String strGetClaimDetail = "{CALL CLAIMS_PKG.SELECT_CLAIM(?,?,?,?,?)}";
	//changed the above procedure package
	private static final String strGetClaimDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_CLAIM(?,?,?,?,?)}";
	
	
	
	private static final String strReleasePreauth = "{CALL CLAIMS_PKG_DATA_ENTRY.RELEASE_PREAUTH(?)}";
	//private static final String strSaveClaimDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SAVE_CLAIM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strSaveClaimDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SAVE_CLAIM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added as per KOC  1285
	
	//changed the above procedure package
	private static final String strSaveClaimDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SAVE_CLAIM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added as per KOC  1285
	
	
	
	
	/*private static final String strGetPreauthList = "{CALL CLAIMS_SQL_PKG.SELECT_PREAUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPrevClaimList = "{CALL CLAIMS_SQL_PKG.SELECT_PREV_CLAIM(?,?)}";*/
	private static final String strGetPreauthList = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_PREAUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPrevClaimList = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_PREV_CLAIM(?,?)}";
	private static final String strSaveReview = "{CALL CLAIMS_PKG_DATA_ENTRY.SET_REVIEW(?,?,?,?,?,?,?,?,?,?,?)}";
	
	
	//added for CR KOC-Decoupling
	private static final String strSaveDataEntryReview = "{CALL CLAIMS_PKG_DATA_ENTRY.claims_promote(?,?,?)}";
	
	//added for CR KOC-Decoupling
	private static final String strClaimsRevert = "{CALL CLAIMS_PKG_DATA_ENTRY.claims_revert(?,?,?)}";
	
	
	
	//private static final String strPrevHospList = "{CALL CLAIMS_SQL_PKG.SELECT_PREV_HOSPS(?,?,?)}";
	private static final String strPrevHospList = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_PREV_HOSPS(?,?,?,?)}";
	//private static final String strNHCPPrevHospList = "{CALL CLAIMS_PKG.SELECT_HOSP_NHCP(?,?)}";
	//private static final String strPrevHospDetails = "{CALL CLAIMS_SQL_PKG.SELECT_HOSP_ALL(?,?)}";
	private static final String strPrevHospDetails = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_HOSP_ALL(?,?)}";
	private static final String strDeleteClaimGeneral = "{CALL CLAIMS_PKG_DATA_ENTRY.DELETE_CLM_GENERAL(?,?,?,?,?,?)}";
	private static final String strAssignUserList = "{CALL CLAIMS_PKG_DATA_ENTRY.MANUAL_ASSIGN_PREAUTH(?,?,?,?)}";
	private static final String strOverrideClaim = "{CALL CLAIMS_PKG_DATA_ENTRY.OVERRIDE_CLAIMS(?,?,?,?,?,?,?)}";
	private static final String strGetClauseDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_REJECTION_CLAUSES(?,?,?,?,?,?,?)}";
	private static final String strSaveClauseDetail = "{CALL CLAIMS_PKG_DATA_ENTRY.SAVE_REJECTION_CLAUSES(?,?,?,?,?,?,?,?,?)}";
	private static final String strRejectionLetterList = "{CALL CLAIMS_PKG_DATA_ENTRY.GET_LETTER_TO(?,?,?)}";
	private static final String strReAssignEnrID = "{CALL CLAIMS_PKG_DATA_ENTRY.RE_ASSIGN_ENRL_ID(?,?,?,?,?)} ";
	private static final String strServTaxCal ="{CALL CLAIMS_PKG_DATA_ENTRY.CALC_SERVICE_TAX(?,?,?,?,?,?)}";

	private static final int CLAIMS_INWARD_SEQ_ID = 1;
	private static final int TPA_OFFICE_SEQ_ID = 2;
	private static final int BARCODE_NO = 3;
	private static final int DOCUMENT_GENERAL_TYPE_ID = 4;
	private static final int RCVD_DATE = 5;
	private static final int SOURCE_GENERAL_TYPE_ID = 6;
	private static final int CLAIM_GENERAL_TYPE_ID = 7;
	private static final int REQUESTED_AMOUNT = 8;
	private static final int MEMBER_SEQ_ID = 9;
	private static final int TPA_ENROLLMENT_ID = 10;
	private static final int CLAIMANT_NAME = 11;
	private static final int POLICY_SEQ_ID = 12;
	private static final int POLICY_NUMBER = 13;
	private static final int INWARD_INS_SEQ_ID = 14;
	private static final int CORPORATE_NAME = 15;
	private static final int POLICY_HOLDER_NAME = 16;
	private static final int EMPLOYEE_NO =17;
	private static final int EMPLOYEE_NAME = 18;
	private static final int INWARD_STATUS_GENERAL_TYPE_ID = 19;
	private static final int REMARKS = 20;
	private static final int COURIER_SEQ_ID = 21;
	private static final int PARENT_CLAIM_SEQ_ID = 22;
	private static final int CLAIM_NUMBER = 23;
	private static final int SHORTFALL_ID = 24;
	private static final int CLAIM_SEQ_ID = 25;
	private static final int CLM_ENROLL_DETAIL_SEQ_ID = 26;
	private static final int EMAIL_ID = 27;
	private static final int NOTIFICATION_PHONE_NUMBER = 28;
	private static final int INS_SCHEME = 29;
	private static final int CERTIFICATE_N0 = 30;
	private static final int INS_CUSTOMER_CODE = 31;
	private static final int USER_SEQ_ID = 32;
	private static final int ROW_PROCESSED = 33;

	/**
	 * This method returns the Arraylist of ClaimInwardVO's  which contains Claim Inward Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ClaimInwardVO object which contains Claim Inward details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimInwardList(ArrayList alSearchCriteria) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		Collection<Object> alResultList = new ArrayList<Object>();
		ClaimInwardVO claimInwardVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimInwardList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(10));
			cStmtObject.setString(11,(String)alSearchCriteria.get(11));
			cStmtObject.setString(12,(String)alSearchCriteria.get(12));
			cStmtObject.setString(13,(String)alSearchCriteria.get(13));
			cStmtObject.setString(14,(String)alSearchCriteria.get(14));
			cStmtObject.setString(15,(String)alSearchCriteria.get(15));
			cStmtObject.setLong(16,(Long)alSearchCriteria.get(9));
			cStmtObject.registerOutParameter(17,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(17);
			if(rs != null){
				while(rs.next()){
					claimInwardVO = new ClaimInwardVO();

					if(rs.getString("CLAIMS_INWARD_SEQ_ID") != null){
						claimInwardVO.setInwardSeqID(new Long(rs.getLong("CLAIMS_INWARD_SEQ_ID")));
					}//end of if(rs.getString("CLAIMS_INWARD_SEQ_ID") != null)

					claimInwardVO.setInwardNbr(TTKCommon.checkNull(rs.getString("CLAIMS_INWARD_NO")));

					if(rs.getString("RCVD_DATE") != null){
						claimInwardVO.setReceivedDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
					}//end of if(rs.getString("RCVD_DATE") != null)

					if(rs.getString("CLAIM_SEQ_ID") != null){
						claimInwardVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)

					if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
						claimInwardVO.setClmEnrollDtlSeqID(new Long(rs.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)
					claimInwardVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimInwardVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					claimInwardVO.setGroupName(TTKCommon.checkNull(rs.getString("CORPORATE_NAME")));
					claimInwardVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					claimInwardVO.setClaimTypeID(TTKCommon.checkNull(rs.getString("CLAIM_GENERAL_TYPE_ID")));
					claimInwardVO.setDocumentTypeID(TTKCommon.checkNull(rs.getString("DOCUMENT_GENERAL_TYPE_ID")));
					alResultList.add(claimInwardVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimInwardList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimInwardList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimInwardList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimInwardList(ArrayList alSearchCriteria)

	/**
	 * This method returns the ClaimInwardDetailVO, which contains all the Claim Inward details
	 * @param lngClaimInwardSeqID long value contains seq id to get the Claim Inward Details
	 * @param strClaimTypeID contains Claim Type
	 * @param lngUserSeqID long value contains Logged-in User Seq ID
	 * @return ClaimInwardDetailVO object which contains all the Claim Inward details
	 * @exception throws TTKException
	 */
	public ClaimInwardDetailVO getClaimInwardDetail(long lngClaimInwardSeqID,String strClaimTypeID,long lngUserSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ClaimInwardDetailVO claimInwardDetailVO = new ClaimInwardDetailVO();
		ClaimantVO claimantVO = null;
		ArrayList<Object> alDocumentList = new ArrayList<Object>();
		ArrayList alPrevClaimList = new ArrayList();
		ArrayList<Object> alShortfallList = new ArrayList<Object>();
		ArrayList alShortfallDetails = new ArrayList();
		DocumentChecklistVO documentChecklistVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimInwardDetail);
			cStmtObject.setLong(1,lngClaimInwardSeqID);
			cStmtObject.setString(2,strClaimTypeID);
			cStmtObject.setLong(3,lngUserSeqID);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(4);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(5);
			if(rs1 != null){
				while(rs1.next()){

					claimantVO = new ClaimantVO();

					if(rs1.getString("CLAIMS_INWARD_SEQ_ID") != null){
						claimInwardDetailVO.setInwardSeqID(new Long(rs1.getLong("CLAIMS_INWARD_SEQ_ID")));
					}//end of if(rs1.getString("CLAIMS_INWARD_SEQ_ID") != null)

					claimInwardDetailVO.setInwardNbr(TTKCommon.checkNull(rs1.getString("CLAIMS_INWARD_NO")));
					claimInwardDetailVO.setBarCodeNbr(TTKCommon.checkNull(rs1.getString("BARCODE_NO")));
					claimInwardDetailVO.setDocumentTypeID(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")));
					claimInwardDetailVO.setReceivedDate(rs1.getString("RCVD_DATE")!=null ? new Date(rs1.getTimestamp("RCVD_DATE").getTime()):null);
					claimInwardDetailVO.setSourceTypeID(TTKCommon.checkNull(rs1.getString("SOURCE_GENERAL_TYPE_ID")));

					if(rs1.getString("COURIER_SEQ_ID") != null){
						claimInwardDetailVO.setCourierSeqID(new Long(rs1.getLong("COURIER_SEQ_ID")));
					}//end of if(rs1.getString("COURIER_SEQ_ID") != null)

					claimInwardDetailVO.setClaimTypeID(TTKCommon.checkNull(rs1.getString("CLAIM_GENERAL_TYPE_ID")));

					if(rs1.getString("REQUESTED_AMOUNT") != null){
						claimInwardDetailVO.setRequestedAmt(new BigDecimal(rs1.getString("REQUESTED_AMOUNT")));
					}//end of if(rs1.getString("REQUESTED_AMOUNT") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs1.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs1.getString("CLAIMANT_NAME")));
					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs1.getString("POLICY_NUMBER")));

					if(rs1.getString("INWARD_INS_SEQ_ID") != null){
						claimantVO.setInsSeqID(new Long(rs1.getLong("INWARD_INS_SEQ_ID")));
					}//end of if(rs1.getString("INWARD_INS_SEQ_ID") != null)

					if(rs1.getString("TPA_OFFICE_SEQ_ID") != null){
						claimInwardDetailVO.setOfficeSeqID(new Long(rs1.getLong("TPA_OFFICE_SEQ_ID")));
					}//end of if(rs1.getString("TPA_OFFICE_SEQ_ID") != null)

					claimInwardDetailVO.setOfficeName(TTKCommon.checkNull(rs1.getString("OFFICE_NAME")));
					claimantVO.setGroupName(TTKCommon.checkNull(rs1.getString("CORPORATE_NAME")));
					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs1.getString("POLICY_HOLDER_NAME")));
					claimantVO.setEmployeeName(TTKCommon.checkNull(rs1.getString("EMPLOYEE_NAME")));
					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs1.getString("EMPLOYEE_NO")));
					claimantVO.setEmailID(TTKCommon.checkNull(rs1.getString("EMAIL_ID")));
					claimantVO.setNotifyPhoneNbr(TTKCommon.checkNull(rs1.getString("NOTIFICATION_PHONE_NUMBER")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs1.getString("INS_SCHEME")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs1.getString("CERTIFICATE_NO")));
					claimantVO.setInsCustCode(TTKCommon.checkNull(rs1.getString("INS_CUSTOMER_CODE")));

					if(rs1.getString("POLICY_SEQ_ID") != null){
						claimantVO.setPolicySeqID(new Long(rs1.getLong("POLICY_SEQ_ID")));
					}//end of if(rs1.getString("POLICY_SEQ_ID") != null)

					if(rs1.getString("MEMBER_SEQ_ID") != null){
						claimantVO.setMemberSeqID(new Long(rs1.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs1.getString("MEMBER_SEQ_ID") != null)

					if(rs1.getString("CLAIM_SEQ_ID") != null){
						claimInwardDetailVO.setClaimSeqID(new Long(rs1.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs1.getString("CLAIM_SEQ_ID") != null)

					if(rs1.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
						claimInwardDetailVO.setClmEnrollDtlSeqID(new Long(rs1.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs1.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)

					claimInwardDetailVO.setStatusTypeID(TTKCommon.checkNull(rs1.getString("INWARD_STATUS_GENERAL_TYPE_ID")));
					claimInwardDetailVO.setRemarks(TTKCommon.checkNull(rs1.getString("REMARKS")));

					if(rs1.getString("SHORTFALL_SEQ_ID") != null){
						claimInwardDetailVO.setShortfallSeqID(new Long(rs1.getLong("SHORTFALL_SEQ_ID")));
					}//end of if(rs1.getString("SHORTFALL_SEQ_ID") != null)

					claimInwardDetailVO.setShortfallID(TTKCommon.checkNull(rs1.getString("SHORTFALL_ID")));
					if(rs1.getString("SHORTFALL_CLAIM_SEQ_ID") != null){
						claimantVO.setClmSeqID(new Long(rs1.getLong("SHORTFALL_CLAIM_SEQ_ID")));
					}//end of if(rs1.getLong("SHORTFALL_CLAIM_SEQ_ID") != null)

					if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equals("DTS")){
						claimantVO.setClaimNbr(TTKCommon.checkNull(rs1.getString("CLAIM_NUMBER")));
					}//end of if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equals("DTS"))
					else{
						claimInwardDetailVO.setClaimNbr(TTKCommon.checkNull(rs1.getString("CLAIM_NUMBER")));
					}//end of else

					claimInwardDetailVO.setDocumentTypeDesc(TTKCommon.checkNull(rs1.getString("DOC_TYPE")));
					claimInwardDetailVO.setCourierNbr(TTKCommon.checkNull(rs1.getString("COURIER_ID")));
					claimInwardDetailVO.setCurrentClaimNbr(TTKCommon.checkNull(rs1.getString("CURRENT_CLAIM_NUMBER")));

					if(rs1.getString("PARENT_CLAIM_SEQ_ID") != null){
						claimInwardDetailVO.setParentClmSeqID(new Long(rs1.getLong("PARENT_CLAIM_SEQ_ID")));
					}//end of if(rs1.getString("PARENT_CLAIM_SEQ_ID") != null)

					if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equalsIgnoreCase("DTA")){
						alPrevClaimList = getPrevClaim(rs1.getLong("MEMBER_SEQ_ID"));
						claimInwardDetailVO.setPrevClaimList(alPrevClaimList);
					}//end of if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equalsIgnoreCase("DTA"))

					if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equalsIgnoreCase("DTS")){
						alShortfallList.add((Long)rs1.getLong("SHORTFALL_CLAIM_SEQ_ID"));
						alShortfallList.add(rs1.getString("SHORTFALL_ID"));
						alShortfallDetails = getInwardShortfallDetail(alShortfallList);
						claimInwardDetailVO.setShortfallVO(alShortfallDetails);
					}//end of if(TTKCommon.checkNull(rs1.getString("DOCUMENT_GENERAL_TYPE_ID")).equalsIgnoreCase("DTS"))

					claimInwardDetailVO.setClaimantVO(claimantVO);
				}//end of while(rs1.next())
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){
					documentChecklistVO = new DocumentChecklistVO();

					if(rs2.getString("DOCU_RCVD_SEQ_ID") != null){
						documentChecklistVO.setDocumentRcvdSeqID(new Long(rs2.getLong("DOCU_RCVD_SEQ_ID")));
					}//end of if(rs2.getString("DOCU_RCVD_SEQ_ID") != null)

					documentChecklistVO.setDocumentListType(TTKCommon.checkNull(rs2.getString("DOCU_LIST_TYPE_ID")));
					documentChecklistVO.setSheetsCount(TTKCommon.checkNull(rs2.getString("SHEETS_COUNT")));
					documentChecklistVO.setDocumentTypeID(TTKCommon.checkNull(rs2.getString("DOC_GENERAL_TYPE_ID")));
					documentChecklistVO.setReasonTypeID(TTKCommon.checkNull(rs2.getString("REASON_GENERAL_TYPE_ID")));
					documentChecklistVO.setRemarks(TTKCommon.checkNull(rs2.getString("REMARKS")));
					documentChecklistVO.setDocumentRcvdYN(TTKCommon.checkNull(rs2.getString("DOCUMENT_RCVD_YN")));
					documentChecklistVO.setDocumentName(TTKCommon.checkNull(rs2.getString("DOCU_NAME")));
					alDocumentList.add(documentChecklistVO);
				}//end of while(rs2.next())
				claimInwardDetailVO.setClaimDocumentVOList(alDocumentList);
			}//end of if(rs2 != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimInwardDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the second resultset now.
				{
					try{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in ClaimDAOImpl getClaimInwardDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in ClaimDAOImpl getClaimInwardDetail()",sqlExp);
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
								log.error("Error while closing the Connection in ClaimDAOImpl getClaimInwardDetail()",sqlExp);
								throw new TTKException(sqlExp, "claim");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return claimInwardDetailVO;
	}//end of getClaimInwardDetail(long lngClaimInwardSeqID,String strClaimTypeID,long lngUserSeqID)

	/**
	 * This method returns the Arraylist of ClaimantVO's which contains Claim Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ClaimantVO object which contains Claim details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimShortfallList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ClaimantVO claimantVO = null;
		//ShortfallVO shortfallVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimShortfallList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(4));
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setLong(8,(Long)alSearchCriteria.get(3));
			cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);
			if(rs != null){
				while(rs.next()){
					claimantVO = new ClaimantVO();
					if(rs.getString("CLAIM_SEQ_ID") != null){
						claimantVO.setClmSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)

					claimantVO.setClaimNbr(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));

					if(rs.getString("MEMBER_SEQ_ID") != null){
						claimantVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					claimantVO.setGenderDescription(TTKCommon.checkNull(rs.getString("GENDER")));

					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)

					alResultList.add(claimantVO);
				}//end of while(rs.next())
			}//end of if(rs != null)

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimShortfallList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimShortfallList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimShortfallList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
	}//end of getClaimShortfallList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of ShortfallVO's which contains Claim Shortfall Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ShortfallVO object which contains Claim Shortfall details
	 * @exception throws TTKException
	 */
	public ArrayList getInwardShortfallDetail(ArrayList alSearchCriteria) throws TTKException {
		ArrayList<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		//ShortfallVO shortfallVO = null;
		CacheObject cacheObject = null;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInwardShortfallDetails);

			if(alSearchCriteria.get(0) != null){
				cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			}//end of if(alSearchCriteria.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			//cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();

					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("SHORTFALL_SEQ_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("SHORTFALL_ID")));

					alResultList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getInwardShortfallDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getInwardShortfallDetail()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getInwardShortfallDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alResultList;
	}//end of getInwardShortfallDetail(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of DocumentChecklistVO's  which contains Claim Document Details
	 * @param strClaimTypeID contains Claim Type ID
	 * @return ArrayList of DocumentChecklistVO object which contains Claim Inward Document details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimDocumentList(String strClaimTypeID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Collection<Object> alResultList = new ArrayList<Object>();
		ResultSet rs = null;
		DocumentChecklistVO documentChecklistVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimDocumentList);
			cStmtObject.setString(1,strClaimTypeID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					documentChecklistVO = new DocumentChecklistVO();
					documentChecklistVO.setDocumentListType(TTKCommon.checkNull(rs.getString("DOCU_LIST_TYPE_ID")));
					documentChecklistVO.setDocumentName(TTKCommon.checkNull(rs.getString("DOCU_NAME")));
					alResultList.add(documentChecklistVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimDocumentList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimDocumentList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimDocumentList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimDocumentList(String strClaimTypeID)

	/**
	 * This method saves the Claim Inward information
	 * @param claimInwardDetailVO the object which contains the Claim Inward Details which has to be saved
	 * @return long the value contains Claim Inward Seq ID
	 * @exception throws TTKException
	 */
	public long saveClaimInwardDetail(ClaimInwardDetailVO claimInwardDetailVO) throws TTKException {
		long lngClaimInwardSeqID = 0;
		ClaimantVO claimantVO = null;
		StringBuffer sbfSQL = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Statement stmt = null;
		DocumentChecklistVO documentChecklistVO = null;
		ArrayList alDocumentChecklist  = new ArrayList();
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaimInwardDetail);
			stmt = (java.sql.Statement)conn.createStatement();
			claimantVO = claimInwardDetailVO.getClaimantVO();
			if(claimInwardDetailVO.getClaimDocumentVOList() != null){
				alDocumentChecklist = claimInwardDetailVO.getClaimDocumentVOList();
			}//end of if(claimInwardDetailVO.getClaimDocumentVOList() != null)

			if(claimInwardDetailVO.getInwardSeqID() != null){
				cStmtObject.setLong(CLAIMS_INWARD_SEQ_ID,claimInwardDetailVO.getInwardSeqID());
			}//end of if(claimInwardDetailVO.getInwardSeqID() != null)
			else{
				cStmtObject.setLong(CLAIMS_INWARD_SEQ_ID,0);
			}//end of else

			if(claimInwardDetailVO.getOfficeSeqID() != null){
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,claimInwardDetailVO.getOfficeSeqID());
			}//end of if(claimInwardDetailVO.getOfficeSeqID() != null)
			else{
				cStmtObject.setString(TPA_OFFICE_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(BARCODE_NO,claimInwardDetailVO.getBarCodeNbr());
			cStmtObject.setString(DOCUMENT_GENERAL_TYPE_ID,claimInwardDetailVO.getDocumentTypeID());

			if(claimInwardDetailVO.getReceivedDate() != null){
				cStmtObject.setTimestamp(RCVD_DATE,new Timestamp(claimInwardDetailVO.getReceivedDate().getTime()));//RCVD_DATE
			}//end of if(claimInwardDetailVO.getReceivedDate() != null)
			else{
				cStmtObject.setTimestamp(RCVD_DATE, null);//RCVD_DATE
			}//end of else
			cStmtObject.setString(SOURCE_GENERAL_TYPE_ID,claimInwardDetailVO.getSourceTypeID());
			cStmtObject.setString(CLAIM_GENERAL_TYPE_ID,claimInwardDetailVO.getClaimTypeID());

			if(claimInwardDetailVO.getRequestedAmt() != null){
				cStmtObject.setBigDecimal(REQUESTED_AMOUNT,claimInwardDetailVO.getRequestedAmt());
			}//end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else{
				cStmtObject.setString(REQUESTED_AMOUNT,null);
			}//end of else

			if(claimantVO.getMemberSeqID() != null){
				cStmtObject.setLong(MEMBER_SEQ_ID,claimantVO.getMemberSeqID());
			}//end of if(claimantVO.getMemberSeqID() != null)
			else{
				cStmtObject.setString(MEMBER_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(TPA_ENROLLMENT_ID,claimantVO.getEnrollmentID());
			cStmtObject.setString(CLAIMANT_NAME,claimantVO.getName());

			if(claimInwardDetailVO.getClaimantVO().getPolicySeqID() != null){
				cStmtObject.setLong(POLICY_SEQ_ID,claimantVO.getPolicySeqID());
			}//end of if(claimInwardDetailVO.getClaimantVO().getPolicySeqID() != null)
			else{
				cStmtObject.setString(POLICY_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(POLICY_NUMBER,claimantVO.getPolicyNbr());

			if(claimantVO.getInsSeqID() != null){
				cStmtObject.setLong(INWARD_INS_SEQ_ID,claimantVO.getInsSeqID());
			}//end of if(claimantVO.getInsSeqID() != null)
			else{
				cStmtObject.setString(INWARD_INS_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(CORPORATE_NAME,claimantVO.getGroupName());
			cStmtObject.setString(POLICY_HOLDER_NAME,claimantVO.getPolicyHolderName());
			cStmtObject.setString(EMPLOYEE_NO,claimantVO.getEmployeeNbr());
			cStmtObject.setString(EMPLOYEE_NAME,claimantVO.getEmployeeName());
			cStmtObject.setString(INWARD_STATUS_GENERAL_TYPE_ID,claimInwardDetailVO.getStatusTypeID());
			cStmtObject.setString(REMARKS,claimInwardDetailVO.getRemarks());

			if(claimInwardDetailVO.getCourierSeqID() != null){
				cStmtObject.setLong(COURIER_SEQ_ID,claimInwardDetailVO.getCourierSeqID());
			}//end of if(claimInwardDetailVO.getCourierSeqID() != null)
			else{
				cStmtObject.setString(COURIER_SEQ_ID,null);
			}//end of else

			if(claimInwardDetailVO.getParentClmSeqID() != null){
				cStmtObject.setLong(PARENT_CLAIM_SEQ_ID,claimInwardDetailVO.getParentClmSeqID());
			}//end of if(claimInwardDetailVO.getParentClmSeqID() != null)
			else{
				cStmtObject.setString(PARENT_CLAIM_SEQ_ID,null);
			}//end of else

			//cStmtObject.setString(CLAIM_NUMBER,claimInwardDetailVO.getClaimNbr());
			if(claimInwardDetailVO.getDocumentTypeID().equals("DTS")){
				cStmtObject.setString(CLAIM_NUMBER,claimantVO.getClaimNbr());
			}//end of if(claimInwardDetailVO.getDocumentTypeID().equals("DTS"))
			else{
				cStmtObject.setString(CLAIM_NUMBER,claimInwardDetailVO.getClaimNbr());
			}//end of else

			cStmtObject.setString(SHORTFALL_ID,claimInwardDetailVO.getShortfallID());

			if(claimInwardDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(CLAIM_SEQ_ID,claimInwardDetailVO.getClaimSeqID());
			}//end of if(claimInwardDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(CLAIM_SEQ_ID,null);
			}//end of else

			if(claimInwardDetailVO.getClmEnrollDtlSeqID() != null){
				cStmtObject.setLong(CLM_ENROLL_DETAIL_SEQ_ID,claimInwardDetailVO.getClmEnrollDtlSeqID());
			}//end of if(claimInwardDetailVO.getClmEnrollDtlSeqID() != null)
			else{
				cStmtObject.setString(CLM_ENROLL_DETAIL_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(EMAIL_ID,claimantVO.getEmailID());
			cStmtObject.setString(NOTIFICATION_PHONE_NUMBER,claimantVO.getNotifyPhoneNbr());
			cStmtObject.setString(INS_SCHEME,claimantVO.getInsScheme());
			cStmtObject.setString(CERTIFICATE_N0,claimantVO.getCertificateNo());
			cStmtObject.setString(INS_CUSTOMER_CODE,claimantVO.getInsCustCode());
			cStmtObject.setLong(USER_SEQ_ID,claimInwardDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
			cStmtObject.registerOutParameter(CLAIMS_INWARD_SEQ_ID,Types.BIGINT);
			cStmtObject.execute();
			lngClaimInwardSeqID = cStmtObject.getLong(CLAIMS_INWARD_SEQ_ID);

			if(alDocumentChecklist != null){
				for(int i=0;i<alDocumentChecklist.size();i++){
					sbfSQL = new StringBuffer();
					documentChecklistVO =(DocumentChecklistVO)alDocumentChecklist.get(i);

					if(documentChecklistVO.getDocumentRcvdSeqID() == null){
						sbfSQL = sbfSQL.append("0,");
					}//end of if(documentChecklistVO.getDocumentRcvdSeqID()== null)
					else{
						sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getDocumentRcvdSeqID()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getDocumentRcvdYN()).append("',");
					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getDocumentListType()).append("',");

					if(documentChecklistVO.getSheetsCount()== null){
						sbfSQL = sbfSQL.append("null,");
					}//end of if(documentChecklistVO.getSheetsCount()==null)
					else{
						sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getSheetsCount()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getDocumentTypeID()).append("',");
					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getReasonTypeID()).append("',");
					sbfSQL = sbfSQL.append("null,");
					sbfSQL = sbfSQL.append("").append(lngClaimInwardSeqID).append(",");
					sbfSQL = sbfSQL.append("'").append(documentChecklistVO.getRemarks()).append("',");
					sbfSQL = sbfSQL.append("'").append(claimInwardDetailVO.getUpdatedBy()).append("')}");
					stmt.addBatch(strSaveDocumentChecklist+sbfSQL.toString());
				}//end of for(int i=0;i<alDocumentChecklist.size();i++)
			}//end of if(alDocumentChecklist != null)
			stmt.executeBatch();
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			try {
				conn.rollback();
				throw new TTKException(sqlExp, "claim");
			} //end of try
			catch (SQLException sExp) {
				throw new TTKException(sExp, "claim");
			}//end of catch (SQLException sExp)
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			try {
				conn.rollback();
				throw new TTKException(exp, "claim");
			} //end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "claim");
			}//end of catch (SQLException sqlExp)
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try{
					if (stmt != null) stmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in ClaimDAOImpl saveClaimInwardDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally{ // Even if statement is not closed, control reaches here. Try closing the Callabale Statement now.
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl saveClaimInwardDetail()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl saveClaimInwardDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				stmt = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngClaimInwardSeqID;
	}//end of saveClaimInwardDetail(ClaimInwardDetailVO claimInwardDetailVO)

	/**
	 * This method returns the Arraylist of PreAuthVO's  which contains Claim Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Claim details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimList(ArrayList alSearchCriteria) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		Collection<Object> alResultList = new ArrayList<Object>();
		PreAuthVO preauthVO = null;
		String strGroupID="";
		String strPolicyGrpID ="";
		ArrayList alUserGroup = new ArrayList();
		String strSuppressLink[]={"0","7","8"};
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(9));
			cStmtObject.setString(11,(String)alSearchCriteria.get(10));
			cStmtObject.setString(12,(String)alSearchCriteria.get(11));
			cStmtObject.setString(13,(String)alSearchCriteria.get(12));
			cStmtObject.setString(14,(String)alSearchCriteria.get(13));
			cStmtObject.setString(15,(String)alSearchCriteria.get(14));
			cStmtObject.setString(16,(String)alSearchCriteria.get(15));
			cStmtObject.setString(17,(String)alSearchCriteria.get(16));
			cStmtObject.setString(18,(String)alSearchCriteria.get(17));
			cStmtObject.setString(19,(String)alSearchCriteria.get(20));
			cStmtObject.setString(20,(String)alSearchCriteria.get(21));
			cStmtObject.setString(21,(String)alSearchCriteria.get(22));
			cStmtObject.setString(22,(String)alSearchCriteria.get(23));//bajaj
			cStmtObject.setString(23,(String)alSearchCriteria.get(24));
			cStmtObject.setString(24,(String)alSearchCriteria.get(25));
			cStmtObject.setString(25,(String)alSearchCriteria.get(26));
			cStmtObject.setLong(26,(Long)alSearchCriteria.get(18));
			cStmtObject.registerOutParameter(27,OracleTypes.CURSOR);
			cStmtObject.execute();
			//get the user group list
			alUserGroup = (ArrayList)alSearchCriteria.get(19);
			rs = (java.sql.ResultSet)cStmtObject.getObject(27);//bajaj changes
			if(rs != null){
				while(rs.next()){
					preauthVO = new PreAuthVO();

					if(rs.getString("CLAIM_SEQ_ID") != null){
						preauthVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)

					preauthVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					preauthVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					preauthVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					preauthVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					preauthVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));

					if(rs.getString("DATE_OF_ADMISSION") != null){
						preauthVO.setClaimAdmnDate(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime()));
					}//end of if(rs.getString("DATE_OF_ADMISSION") != null)

					preauthVO.setAuthNbr(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));

					if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
						preauthVO.setClmEnrollDtlSeqID(new Long(rs.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)

					if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
						preauthVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)

					if(rs.getString("POLICY_SEQ_ID") != null){
						preauthVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					if(rs.getString("MEMBER_SEQ_ID") != null){
						preauthVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if(rs.getString("TPA_OFFICE_SEQ_ID") != null){
						preauthVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					}//end of if(rs.getString("TPA_OFFICE_SEQ_ID") != null)

					preauthVO.setBufferAllowedYN(TTKCommon.checkNull(rs.getString("BUFFER_ALLOWED_YN")));

					if(rs.getString("ASSIGN_USERS_SEQ_ID") != null){
						preauthVO.setAssignUserSeqID(new Long(rs.getLong("ASSIGN_USERS_SEQ_ID")));
					}//end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)
					preauthVO.setAmmendmentYN(TTKCommon.checkNull(rs.getString("AMMENDMENT_YN")));
					preauthVO.setCoding_review_yn(TTKCommon.checkNull(rs.getString("CODING_REVIEW_YN")));


					strGroupID = TTKCommon.checkNull(rs.getString("GROUP_BRANCH_SEQ_ID"));
					//Koc Decoupling					
					preauthVO.setStatus(TTKCommon.checkNull(rs.getString("Status")));					
					 //Koc Decoupling

					boolean bSuppress = true;
					if(alUserGroup != null){
						for(int iGroupNodeCnt=0;iGroupNodeCnt<alUserGroup.size();iGroupNodeCnt++)
						{
							strPolicyGrpID=String.valueOf(((GroupVO)alUserGroup.get(iGroupNodeCnt)).getGroupSeqID());
							if(strGroupID.equals(strPolicyGrpID))
							{
								//Policy belongs to some group don't suppress links
								bSuppress = false;
								break;
							}//if(policyVO.getGroupID().equals(((GroupVO)alUserGroup.get(iGroupNodeCnt)).getGroupSeqID()))
						}//end of for(int iGroupNodeCnt=0;iGroupNodeCnt<alGroupNodes.size();iGroupNodeCnt++)
					}//end of if(alUserGroup != null)

					if(!strGroupID.equals("") && bSuppress){
						preauthVO.setSuppressLink(strSuppressLink);
					}//end of if(!strGroupID.equals("") && bSuppress)

					alResultList.add(preauthVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
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
	 * This method returns the PreAuthDetailVO, which contains all the Claim details
	 * @param alClaimList contains Claim seq id,Member Seq ID to get the Claim Details
	 * @return PreAuthDetailVO object which contains all the Claim details
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO getClaimDetail(ArrayList alClaimList) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthDetailVO preauthDetailVO = null;
		PreAuthHospitalVO preauthHospitalVO = null;
		ClaimantVO claimantVO = null;
		ClaimDetailVO claimDetailVO = null;
		ArrayList alPrevClaimList = new ArrayList();
		HashMap hmPrevHospDetails = new HashMap();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimDetail);

			if(alClaimList.get(0) != null){

				cStmtObject.setLong(1,(Long)alClaimList.get(0));
			}//end of if(alClaimList.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else

			if(alClaimList.get(1) != null){
				cStmtObject.setLong(2,(Long)alClaimList.get(1));
			}//end of if(alClaimList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(alClaimList.get(2) != null){
				cStmtObject.setLong(3,(Long)alClaimList.get(2));
			}//end of if(alClaimList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else
			cStmtObject.setLong(4,(Long)alClaimList.get(3));
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(5);
			if(rs != null){
				while(rs.next()){
					preauthDetailVO = new PreAuthDetailVO();
					claimantVO = new ClaimantVO();
					preauthHospitalVO = new PreAuthHospitalVO();
					claimDetailVO = new ClaimDetailVO();

					if(rs.getString("CLAIM_SEQ_ID") != null){
						preauthDetailVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)

					if(rs.getString("PARENT_CLAIM_SEQ_ID") != null){
						preauthDetailVO.setClmParentSeqID(new Long(rs.getLong("PARENT_CLAIM_SEQ_ID")));
					}//end of if(rs.getString("PARENT_CLAIM_SEQ_ID") != null)

					preauthDetailVO.setPrevClaimNbr(TTKCommon.checkNull(rs.getString("PREV_CLAIM_NUMBER")));

					if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
						preauthDetailVO.setClmEnrollDtlSeqID(new Long(rs.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)

					if(rs.getString("CLAIMS_INWARD_SEQ_ID") != null){
						preauthDetailVO.setInwardSeqID(new Long(rs.getLong("CLAIMS_INWARD_SEQ_ID")));
					}//end of if(rs.getString("CLAIMS_INWARD_SEQ_ID") != null)

					if(rs.getString("MEMBER_SEQ_ID") != null){
						claimantVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));

					claimantVO.setName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					//changes on dec 9th koc1136
					try{
						String VIP_YN=TTKCommon.checkNull(rs.getString("VIP_YN"));
						if( VIP_YN.equals("") || VIP_YN.equalsIgnoreCase("N"))
						{
							claimantVO.setVipYN("N");
						}
						if(VIP_YN.equalsIgnoreCase("Y")) {
							claimantVO.setVipYN("Y");
						}
						
					}
					catch(SQLException e) {
						// TODO: handle exception
						log.error("VIP_YN Column DOES NOT EXIST ");
					}

					//changes on dec 9th koc1136
					



					claimantVO.setClaimantNameDisc(TTKCommon.checkNull(rs.getString("DIS_NAME_YN")));
					claimantVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
					claimantVO.setGenderDiscrepancy(TTKCommon.checkNull(rs.getString("DIS_GEND_YN")));

					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)

					claimantVO.setAgeDiscrepancy(TTKCommon.checkNull(rs.getString("DIS_AGE_YN")));

					if(rs.getString("DATE_OF_INCEPTION") != null){
						claimantVO.setDateOfInception(new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()));
					}//end of if(rs.getString("DATE_OF_INCEPTION") != null)

					if(rs.getString("DATE_OF_EXIT") != null){
						claimantVO.setDateOfExit(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
					}//end of if(rs.getString("DATE_OF_EXIT") != null)

					claimantVO.setSumEnhancedYN(TTKCommon.checkNull(rs.getString("V_SUM_ENHANCED_YN")));

					if(rs.getString("MEM_TOTAL_SUM_INSURED") != null){
						claimantVO.setTotalSumInsured(new BigDecimal(rs.getString("MEM_TOTAL_SUM_INSURED")));
					}//end of if(rs.getString("MEM_TOT_SUM_INSURED") != null)

					if(rs.getString("AVA_SUM_INSURED") != null){
						claimantVO.setAvailSumInsured(new BigDecimal(rs.getString("AVA_SUM_INSURED")));
					}//end of if(rs.getString("AVA_SUM_INSURED") != null)
					else{
						claimantVO.setAvailSumInsured(new BigDecimal("0.00"));
					}//end of else

					if(rs.getString("BUFF_DETAIL_SEQ_ID") != null){
						claimantVO.setBufferDtlSeqID(new Long(rs.getLong("BUFF_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("BUFF_DETAIL_SEQ_ID") != null)

					claimantVO.setBufferAllowedYN(TTKCommon.checkNull(rs.getString("BUFFER_ALLOWED_YN")));

					if(rs.getString("BUFFER_AVA_AMOUNT") != null){
						claimantVO.setAvblBufferAmount(new BigDecimal(rs.getString("BUFFER_AVA_AMOUNT")));
					}//end of if(rs.getString("BUFFER_AVA_AMOUNT") != null)
					else{
						claimantVO.setAvblBufferAmount(new BigDecimal("0.00"));
					}//end of else

					if(rs.getString("AVA_CUM_BONUS") != null){
						claimantVO.setCumulativeBonus(new BigDecimal(rs.getString("AVA_CUM_BONUS")));
					}//end of if(rs.getString("AVA_CUM_BONUS") != null)
					else{
						claimantVO.setCumulativeBonus(new BigDecimal("0.00"));
					}//end of else

					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
					claimantVO.setEmployeeName(TTKCommon.checkNull(rs.getString("EMPLOYEE_NAME")));
					claimantVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
					claimantVO.setClaimantPhoneNbr(TTKCommon.checkNull(rs.getString("CLAIMANT_PHONE_NUMBER")));
					claimantVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					claimantVO.setNotifyPhoneNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));

					preauthDetailVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					claimDetailVO.setClaimFileNbr(TTKCommon.checkNull(rs.getString("CLAIM_FILE_NUMBER")));
					claimDetailVO.setRequestTypeID(TTKCommon.checkNull(rs.getString("REQUEST_GENERAL_TYPE_ID")));
					claimDetailVO.setRequestTypeDesc(TTKCommon.checkNull(rs.getString("REQUEST_TYPE")));
					claimDetailVO.setReopenTypeID(TTKCommon.checkNull(rs.getString("RE_OPEN_TYPE")));
					claimDetailVO.setReopenTypeDesc(TTKCommon.checkNull(rs.getString("RE_OPEN_TYPE_DESCRIPTION")));
					claimDetailVO.setClaimTypeID(TTKCommon.checkNull(rs.getString("CLAIM_GENERAL_TYPE_ID")));
					claimDetailVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
					claimDetailVO.setClaimSubTypeID(TTKCommon.checkNull(rs.getString("CLAIM_SUB_GENERAL_TYPE_ID")));
					//added as per KOC 1285 Change Request
					claimDetailVO.setDomicilaryReason(TTKCommon.checkNull(rs.getString("DOM_REASON_GEN_TYPE_ID")));
					claimDetailVO.setDoctorCertificateYN(TTKCommon.checkNull(rs.getString("DOC_CERT_DOM_YN")));
					//added as per KOC 1285 Change Request
					if(rs.getString("REQUESTED_AMOUNT") != null){
						preauthDetailVO.setClaimRequestAmount(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
					}//end of if(rs.getString("REQUESTED_AMOUNT") != null)

					if(rs.getString("INTIMATION_DATE") != null){
						claimDetailVO.setIntimationDate(new Date(rs.getTimestamp("INTIMATION_DATE").getTime()));
					}//end of if(rs.getString("INTIMATION_DATE") != null)

					claimDetailVO.setModeTypeID(TTKCommon.checkNull(rs.getString("MODE_GENERAL_TYPE_ID")));
					claimDetailVO.setModeTypeDesc(TTKCommon.checkNull(rs.getString("MODE_TYPE")));

					if(rs.getString("RCVD_DATE") != null){
						claimDetailVO.setClaimReceivedDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
					}//end of if(rs.getString("RCVD_DATE") != null)
					claimDetailVO.setDoctorRegnNbr(TTKCommon.checkNull(rs.getString("DOCTOR_REGISTRATION_NO")));
					preauthDetailVO.setDoctorName(TTKCommon.checkNull(rs.getString("TREATING_DR_NAME")));
					claimDetailVO.setInPatientNbr(TTKCommon.checkNull(rs.getString("IN_PATIENT_NO")));
					preauthDetailVO.setOfficeName(TTKCommon.checkNull(rs.getString("TTK_BRANCH")));
					claimDetailVO.setSourceDesc(TTKCommon.checkNull(rs.getString("SOURCE_TYPE")));
					claimDetailVO.setClaimRemarks(TTKCommon.checkNull(rs.getString("CLAIMS_REMARKS")));

					if(rs.getString("ASSIGN_USERS_SEQ_ID") != null){
						preauthDetailVO.setAssignUserSeqID(new Long(rs.getLong("ASSIGN_USERS_SEQ_ID")));
					}//end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)

					preauthDetailVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					preauthDetailVO.setProcessingBranch(TTKCommon.checkNull(rs.getString("PROCESSING_BRANCH")));
					preauthDetailVO.setAuthNbr(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
					preauthDetailVO.setPreAuthTypeID(TTKCommon.checkNull(rs.getString("PAT_GENERAL_TYPE_ID")));
					preauthDetailVO.setPreauthTypeDesc(TTKCommon.checkNull(rs.getString("PREAUTH_TYPE")));

					if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
						preauthDetailVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)

					if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						preauthDetailVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

					if(rs.getString("PAT_RECEIVED_DATE") != null){
						preauthDetailVO.setReceivedDate(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime()));
						preauthDetailVO.setReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[1]:"");
						preauthDetailVO.setReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)

					if(rs.getString("TOTAL_APP_AMOUNT") != null){
						preauthDetailVO.setApprovedAmt(new BigDecimal(rs.getString("TOTAL_APP_AMOUNT")));
					}//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)

					if(rs.getString("DATE_OF_ADMISSION") != null){
						preauthDetailVO.setClmAdmissionDate(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime()));
						preauthDetailVO.setClmAdmissionTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ")[1]:"");
						preauthDetailVO.setAdmissionDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("DATE_OF_ADMISSION") != null)

					if(rs.getString("DATE_OF_DISCHARGE") != null){
						//preauthDetailVO.setDischargeDate(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime()));
						preauthDetailVO.setDischargeTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[1]:"");
						preauthDetailVO.setDischargeDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("DATE_OF_DISCHARGE") != null)

					preauthDetailVO.setEnrolChangeMsg(TTKCommon.checkNull(rs.getString("ENROLLMENT_CHANGE_MSG")));

					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					claimantVO.setPolicyNbrDisc(TTKCommon.checkNull(rs.getString("DIS_POLICY_NUM_YN")));

					if(rs.getString("POLICY_SEQ_ID") != null){
						claimantVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					claimantVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));

					if(rs.getString("POLICY_EFFECTIVE_FROM") != null){
						claimantVO.setStartDate(new Date(rs.getTimestamp("POLICY_EFFECTIVE_FROM").getTime()));
					}//end of if(rs.getString("POLICY_EFFECTIVE_FROM") != null)

					if(rs.getString("POLICY_EFFECTIVE_TO") != null){
						claimantVO.setEndDate(new Date(rs.getTimestamp("POLICY_EFFECTIVE_TO").getTime()));
					}//end of if(rs.getString("POLICY_EFFECTIVE_TO") != null)

					claimantVO.setPolicySubTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs.getString("POLICY_HOLDER_NAME")));
					claimantVO.setPolicyHolderNameDisc(TTKCommon.checkNull(rs.getString("DIS_POLICY_HOLDER_YN")));
					claimantVO.setTermStatusID(TTKCommon.checkNull(rs.getString("INS_STATUS_GENERAL_TYPE_ID")));
					claimantVO.setPhone(TTKCommon.checkNull(rs.getString("PHONE_1")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs.getString("INS_SCHEME")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
					claimantVO.setInsCustCode(TTKCommon.checkNull(rs.getString("INS_CUSTOMER_CODE")));

					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						claimantVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					claimantVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));

					if(rs.getString("INS_SEQ_ID") != null){
						claimantVO.setInsSeqID(new Long(rs.getLong("INS_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					claimantVO.setCompanyCode(TTKCommon.checkNull(rs.getString("COMPANY_CODE")));

					if(rs.getString("CLM_HOSP_ASSOC_SEQ_ID") != null){
						preauthDetailVO.setHospAssocSeqID(new Long(rs.getLong("CLM_HOSP_ASSOC_SEQ_ID")));
					}//end of if(rs.getString("CLM_HOSP_ASSOC_SEQ_ID") != null)

					if(rs.getString("HOSP_SEQ_ID") != null){
						preauthHospitalVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
					}//end of if(rs.getString("HOSP_SEQ_ID") != null)

					preauthHospitalVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					preauthHospitalVO.setEmplNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					preauthHospitalVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
					preauthHospitalVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
					preauthHospitalVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
					preauthHospitalVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY_NAME")));
					preauthHospitalVO.setStateName(TTKCommon.checkNull(rs.getString("STATE_NAME")));
					preauthHospitalVO.setPincode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
					preauthHospitalVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					preauthHospitalVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
					preauthHospitalVO.setFaxNbr(TTKCommon.checkNull(rs.getString("OFFICE_FAX_NO")));
					preauthHospitalVO.setEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
					preauthHospitalVO.setHospRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					preauthHospitalVO.setHospServiceTaxRegnNbr(TTKCommon.checkNull(rs.getString("SERV_TAX_RGN_NUMBER")));
					preauthHospitalVO.setHospStatus(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
					preauthHospitalVO.setEmpanelStatusTypeID(TTKCommon.checkNull(rs.getString("EMPANEL_TYPE_ID")));
					preauthHospitalVO.setRating(TTKCommon.checkNull(rs.getString("RATING")));

					if(preauthHospitalVO.getRating() != null){
						if(preauthHospitalVO.getRating().equals("G")){
							preauthHospitalVO.setRatingImageName("GoldStar");
							preauthHospitalVO.setRatingImageTitle("Gold Star");
						}//end of if(preAuthHospitalVO.getRating().equals("G"))

						if(preauthHospitalVO.getRating().equals("R")){
							preauthHospitalVO.setRatingImageName("BlueStar");
							preauthHospitalVO.setRatingImageTitle("Blue Star (Regular)");
						}//end of if(preAuthHospitalVO.getRating().equals("R"))

						if(preauthHospitalVO.getRating().equals("B")){
							preauthHospitalVO.setRatingImageName("BlackStar");
							preauthHospitalVO.setRatingImageTitle("Black Star");
						}//end of if(preAuthHospitalVO.getRating().equals("B"))
					}//end of if(preAuthHospitalVO.getRating() != null)

					if(TTKCommon.checkNull(rs.getString("REQUEST_GENERAL_TYPE_ID")).equalsIgnoreCase("DTA")){
						alPrevClaimList = getPrevClaim(rs.getLong("MEMBER_SEQ_ID"));
						preauthDetailVO.setPrevClaimList(alPrevClaimList);
					}//end of if(TTKCommon.checkNull(rs.getString("REQUEST_GENERAL_TYPE_ID")).equalsIgnoreCase("DTA"))

					if(rs.getString("CLAIM_GENERAL_TYPE_ID") != null && rs.getString("MEMBER_SEQ_ID") != null){
						hmPrevHospDetails = (HashMap)getPrevHospList(rs.getLong("MEMBER_SEQ_ID"),rs.getString("CLAIM_GENERAL_TYPE_ID"),rs.getLong("CLAIM_SEQ_ID"));
						preauthDetailVO.setPrevHospDetails(hmPrevHospDetails);
					}//end of if(rs.getString("CLAIM_GENERAL_TYPE_ID") != null && rs.getString("MEMBER_SEQ_ID") != null)

					if(rs.getString("EVENT_SEQ_ID") != null){
						preauthDetailVO.setEventSeqID(new Long(rs.getLong("EVENT_SEQ_ID")));
					}//end of if(rs.getString("EVENT_SEQ_ID") != null)

					if(rs.getString("REQUIRED_REVIEW_COUNT") != null){
						preauthDetailVO.setRequiredReviewCnt(new Integer(rs.getInt("REQUIRED_REVIEW_COUNT")));
					}//end of if(rs.getString("REQUIRED_REVIEW_COUNT") != null)

					if(rs.getString("REVIEW_COUNT") != null){
						preauthDetailVO.setReviewCount(new Integer(rs.getInt("REVIEW_COUNT")));
					}//end of if(rs.getString("REVIEW_COUNT") != null)

					preauthDetailVO.setReview(TTKCommon.checkNull(rs.getString("REVIEW")));
					preauthDetailVO.setEventName(TTKCommon.checkNull(rs.getString("EVENT_NAME")));
					preauthDetailVO.setCompletedYN(TTKCommon.checkNull(rs.getString("COMPLETED_YN")));

					if(rs.getString("PREV_HOSP_CLAIM_SEQ_ID") != null){
						preauthDetailVO.setPrevHospClaimSeqID(new Long(rs.getLong("PREV_HOSP_CLAIM_SEQ_ID")));
					}//end of if(rs.getString("PREV_HOSP_CLAIM_SEQ_ID") != null)

					preauthDetailVO.setDiscPresentYN(TTKCommon.checkNull(rs.getString("DISCREPANCY_PRESENT_YN")));
					preauthDetailVO.setAmmendmentYN(TTKCommon.checkNull(rs.getString("AMMENDMENT_YN")));
					preauthDetailVO.setDMSRefID(TTKCommon.checkNull(rs.getString("CLAIM_DMS_REFERENCE_ID")));
					preauthDetailVO.setShowCodingOverrideYN(TTKCommon.checkNull(rs.getString("SHOW_CODING_OVERRIDE")));
					preauthDetailVO.setShowReAssignIDYN(TTKCommon.checkNull(rs.getString("SHOW_REASSIGN_ID_YN")));
					preauthDetailVO.setUser(TTKCommon.checkNull(rs.getString("USER_ID")));//ADDED FOR DOCUMENT VIEWER - KOC1267
					
					//added for CR KOC-Decoupling
					preauthDetailVO.setGenComplYN(TTKCommon.checkNull(rs.getString("GEN_COMPLETE_YN"))); 
					preauthDetailVO.setClaimDetailVO(claimDetailVO);
					preauthDetailVO.setClaimantVO(claimantVO);
					preauthDetailVO.setPreAuthHospitalVO(preauthHospitalVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimDetail()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimDetail()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return preauthDetailVO;
	}//end of getClaimDetail(ArrayList alClaimList)

	/**
	 * This method will allow to Override the Claim information
	 * @param lngClaimSeqID ClaimSeqID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO overrideClaim(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		PreAuthDetailVO preauthDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strOverrideClaim);
			preauthDetailVO = new PreAuthDetailVO();
			cStmtObject.setLong(1,lngClaimSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(4,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(5,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(6,Types.VARCHAR);//EVENT_NAME
			cStmtObject.registerOutParameter(7,Types.VARCHAR);//REVIEW
			cStmtObject.execute();

			lngEventSeqID = cStmtObject.getLong(3);
			intReviewCount = cStmtObject.getInt(4);
			intRequiredReviewCnt = cStmtObject.getInt(5);
			strEventName = cStmtObject.getString(6);
			strReview = cStmtObject.getString(7);

			preauthDetailVO.setEventSeqID(lngEventSeqID);
			preauthDetailVO.setReviewCount(intReviewCount);
			preauthDetailVO.setRequiredReviewCnt(intRequiredReviewCnt);
			preauthDetailVO.setEventName(strEventName);
			preauthDetailVO.setReview(strReview);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl overrideClaim()",sqlExp);
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
						log.error("Error while closing the Connection in ClaimDAOImpl overrideClaim()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return preauthDetailVO;
	}//end of overrideClaim(long lngClaimSeqID,long lngUserSeqID)

	/**
	 * This method releases the Preauth Associated to NHCPClaim
	 * @param lngPATEnrollDtlSeqID PAT Enroll Dtl Seq ID
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int releasePreauth(long lngPATEnrollDtlSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int iResult = 1;
		try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strReleasePreauth);
			cStmtObject.setLong(1,lngPATEnrollDtlSeqID);
			cStmtObject.execute();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl releasePreauth()",sqlExp);
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
						log.error("Error while closing the Connection in ClaimDAOImpl releasePreauth()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of releasePreauth(long lngPATEnrollDtlSeqID)

	/**
	 * This method saves the Claim information
	 * @param preauthDetailVO the object which contains the Claim Details which has to be saved
	 * @return long the value contains Claim Seq ID
	 * @exception throws TTKException
	 */
	public long saveClaimDetail(PreAuthDetailVO preauthDetailVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		long lngClaimSeqID = 0;
		ClaimDetailVO claimDetailVO = null;
		ClaimantVO claimantVO = null;
		PreAuthHospitalVO preauthHospitalVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaimDetail);

			claimDetailVO = preauthDetailVO.getClaimDetailVO();
			claimantVO = preauthDetailVO.getClaimantVO();
			preauthHospitalVO = preauthDetailVO.getPreAuthHospitalVO();
			if(preauthDetailVO.getClmEnrollDtlSeqID() != null){
				cStmtObject.setLong(1,preauthDetailVO.getClmEnrollDtlSeqID());
			}//end of if(preauthDetailVO.getClmEnrollDtlSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else

			if(preauthDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(2,preauthDetailVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.setString(3,claimantVO.getGenderTypeID());

			if(claimantVO.getMemberSeqID() != null){
				cStmtObject.setLong(4,claimantVO.getMemberSeqID());
			}//end of if(claimantVO.getMemberSeqID() != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			cStmtObject.setString(5,claimantVO.getEnrollmentID());

			if(claimantVO.getPolicySeqID() != null){
				cStmtObject.setLong(6,claimantVO.getPolicySeqID());
			}//end of if(claimantVO.getPolicySeqID() != null)
			else{
				cStmtObject.setString(6,null);
			}//end of else

			cStmtObject.setString(7,claimantVO.getPolicyHolderName());
			cStmtObject.setString(8,claimantVO.getEmployeeNbr());
			cStmtObject.setString(9,claimantVO.getEmployeeName());

			if(claimantVO.getAge() != null){
				cStmtObject.setInt(10,claimantVO.getAge());
			}//end of if(claimantVO.getAge() != null)
			else{
				cStmtObject.setInt(10,0);
			}//end of else

			cStmtObject.setString(11,claimantVO.getName());

			if(claimantVO.getDateOfInception() != null){
				cStmtObject.setTimestamp(12,new Timestamp(claimantVO.getDateOfInception().getTime()));
			}//end of if(claimantVO.getDateOfInception() != null)
			else{
				cStmtObject.setTimestamp(12,null);
			}//end of else

			if(claimantVO.getDateOfExit() != null){
				cStmtObject.setTimestamp(13,new Timestamp(claimantVO.getDateOfExit().getTime()));
			}//end of if(claimantVO.getDateOfExit() != null)
			else{
				cStmtObject.setTimestamp(13,null);
			}//end of else

			cStmtObject.setString(14,claimantVO.getRelationTypeID());
			cStmtObject.setString(15,claimantVO.getPolicyNbr());
			cStmtObject.setString(16,claimantVO.getClaimantPhoneNbr());

			if(claimantVO.getTotalSumInsured() != null){
				cStmtObject.setBigDecimal(17,claimantVO.getTotalSumInsured());
			}//end of if(claimantVO.getTotalSumInsured() != null)
			else{
				cStmtObject.setString(17,null);
			}//end of else

			cStmtObject.setString(18,claimantVO.getPolicyTypeID());
			cStmtObject.setString(19,claimantVO.getPolicySubTypeID());
			cStmtObject.setString(20,claimantVO.getPhone());

			if(claimantVO.getStartDate() != null){
				cStmtObject.setTimestamp(21,new Timestamp(claimantVO.getStartDate().getTime()));
			}//end of if(claimantVO.getStartDate() != null)
			else{
				cStmtObject.setTimestamp(21,null);
			}//end of else

			if(claimantVO.getEndDate() != null){
				cStmtObject.setTimestamp(22,new Timestamp(claimantVO.getEndDate().getTime()));
			}//end of if(claimantVO.getEndDate() != null)
			else{
				cStmtObject.setTimestamp(22,null);
			}//end of else

			cStmtObject.setString(23,claimantVO.getTermStatusID());

			if(claimantVO.getInsSeqID() != null){
				cStmtObject.setLong(24,claimantVO.getInsSeqID());
			}//end of if(claimantVO.getInsSeqID() != null)
			else{
				cStmtObject.setString(24,null);
			}//end of else

			if(claimantVO.getGroupRegnSeqID() != null){
				cStmtObject.setLong(25,claimantVO.getGroupRegnSeqID());
			}//end of if(claimantVO.getGroupRegnSeqID() != null)
			else{
				cStmtObject.setString(25,null);
			}//end of else

			if(preauthDetailVO.getInwardSeqID() != null){
				cStmtObject.setLong(26,preauthDetailVO.getInwardSeqID());
			}//end of if(preauthDetailVO.getInwardSeqID() != null)
			else{
				cStmtObject.setString(26,null);
			}//end of else

			if(preauthDetailVO.getClaimRequestAmount() != null){
				cStmtObject.setBigDecimal(27,preauthDetailVO.getClaimRequestAmount());
			}//end of if(preauthDetailVO.getClaimRequestAmount() != null)
			else{
				cStmtObject.setString(27,null);
			}//end of else

			if(preauthDetailVO.getEnrollDtlSeqID() != null){
				cStmtObject.setLong(28,preauthDetailVO.getEnrollDtlSeqID());
			}//end of if(preauthDetailVO.getEnrollDtlSeqID() != null)
			else{
				cStmtObject.setString(28,null);
			}//end of else

			cStmtObject.setString(29,preauthDetailVO.getAuthNbr());
			cStmtObject.setString(30,claimDetailVO.getRequestTypeID());
			cStmtObject.setString(31,claimDetailVO.getClaimSubTypeID());
			if(claimDetailVO.getDomicilaryReason()!= ""){
				cStmtObject.setString(32,claimDetailVO.getDomicilaryReason());
			}//end of if(preauthDetailVO.getDomicilaryReason() != null)
			else{
				cStmtObject.setString(32,null);
			}//end of else
			
			if(claimDetailVO.getDoctorCertificateYN() != ""){
				cStmtObject.setString(33,claimDetailVO.getDoctorCertificateYN());
			}//end of if(preauthDetailVO.getDoctorCertificateYN() != null)
			else{
				cStmtObject.setString(33,null);
			}//end of else
			cStmtObject.setString(34,claimDetailVO.getModeTypeID());
			cStmtObject.setString(35,preauthDetailVO.getDoctorName());
			cStmtObject.setString(36,claimDetailVO.getInPatientNbr());
			cStmtObject.setString(37,claimDetailVO.getClaimRemarks());

			if(claimantVO.getAvailSumInsured() != null){
				cStmtObject.setBigDecimal(38,claimantVO.getAvailSumInsured());
			}//end of if(claimantVO.getAvailSumInsured() != null)
			else{
				cStmtObject.setString(38,null);
			}//end of else

			if(claimantVO.getCumulativeBonus() != null){
				cStmtObject.setBigDecimal(39,claimantVO.getCumulativeBonus());
			}//end of if(claimantVO.getCumulativeBonus() != null)
			else{
				cStmtObject.setString(39,null);
			}//end of else

			if(preauthDetailVO.getClmAdmissionDate() != null){
				cStmtObject.setTimestamp(40,new Timestamp(TTKCommon.getOracleDateWithTime(preauthDetailVO.getClmHospAdmissionDate(),preauthDetailVO.getClmAdmissionTime(),preauthDetailVO.getAdmissionDay()).getTime()));
			}//end of if(preAuthDetailVO.getAdmissionDate() != null)
			else{
				cStmtObject.setTimestamp(40, null);
			}//end of else

			if(preauthDetailVO.getDischargeDate() != null){
				//cStmtObject.setTimestamp(41,new Timestamp(TTKCommon.getOracleDateWithTime(preauthDetailVO.getClaimDischargeDate(),preauthDetailVO.getDischargeTime(),preauthDetailVO.getDischargeDay()).getTime()));
			}//end of if(preAuthDetailVO.getDischargeDate() != null)
			else{
				cStmtObject.setTimestamp(41, null);
			}//end of else

			if(preauthDetailVO.getClmParentSeqID() != null){
				cStmtObject.setLong(42,preauthDetailVO.getClmParentSeqID());
			}//end of if(preauthDetailVO.getClmParentSeqID() != null)
			else{
				cStmtObject.setString(42,null);
			}//end of else

			
			if(preauthDetailVO.getHospAssocSeqID() != null){
				cStmtObject.setLong(43,preauthDetailVO.getHospAssocSeqID());
			}//end of if(preauthDetailVO.getHospAssocSeqID() != null)
			else{
				cStmtObject.setString(43,null);
			}//end of else

			if(preauthHospitalVO.getHospSeqId() != null){
				cStmtObject.setLong(44,preauthHospitalVO.getHospSeqId());
			}//end of if(preauthHospitalVO.getHospSeqId() != null)
			else{
				cStmtObject.setString(44,null);
			}//end of else

			cStmtObject.setString(45,preauthHospitalVO.getEmplNumber());
			cStmtObject.setString(46,preauthHospitalVO.getHospitalName());
			cStmtObject.setString(47,preauthHospitalVO.getAddress1());
			cStmtObject.setString(48,preauthHospitalVO.getAddress2());
			cStmtObject.setString(49,preauthHospitalVO.getAddress3());
			cStmtObject.setString(50,preauthHospitalVO.getStateName());
			cStmtObject.setString(51,preauthHospitalVO.getCityDesc());
			cStmtObject.setString(52,preauthHospitalVO.getPincode());
			cStmtObject.setString(53,preauthHospitalVO.getPhoneNbr1());
			cStmtObject.setString(54,preauthHospitalVO.getPhoneNbr2());
			cStmtObject.setString(55,preauthHospitalVO.getFaxNbr());
			cStmtObject.setString(56,preauthHospitalVO.getHospRemarks());
			cStmtObject.setString(57, preauthHospitalVO.getHospServiceTaxRegnNbr());
			if(preauthDetailVO.getPrevHospClaimSeqID() != null){
				cStmtObject.setLong(58,preauthDetailVO.getPrevHospClaimSeqID());
			}//end of preauthDetailVO.getPrevHospClaimSeqID()
			else{
				cStmtObject.setString(58,null);
			}//end of else

			cStmtObject.setString(59,preauthDetailVO.getDMSRefID());

			if(preauthDetailVO.getApprovedAmt() != null){
				cStmtObject.setBigDecimal(60,preauthDetailVO.getApprovedAmt());
			}//end of if(preauthDetailVO.getApprovedAmt() != null)
			else{
				cStmtObject.setString(60,null);
			}//end of else

			cStmtObject.setString(61,claimDetailVO.getReopenTypeID());
			cStmtObject.setString(62,claimDetailVO.getDoctorRegnNbr());
			cStmtObject.setString(63,claimantVO.getEmailID());
			cStmtObject.setString(64,claimantVO.getNotifyPhoneNbr());
			cStmtObject.setString(65,claimantVO.getInsScheme());
			cStmtObject.setString(66,claimantVO.getCertificateNo());
			cStmtObject.setString(67,claimantVO.getInsCustCode());
			cStmtObject.setLong(68,preauthDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(69,Types.INTEGER);
			cStmtObject.registerOutParameter(2,Types.BIGINT);
			cStmtObject.execute();
			lngClaimSeqID = cStmtObject.getLong(2);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl saveClaimDetail()",sqlExp);
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
						log.error("Error while closing the Connection in ClaimDAOImpl saveClaimDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngClaimSeqID;
	}//end of saveClaimDetail(PreAuthDetailVO preauthDetailVO)

	/**
	 * This method returns the Arraylist of PreAuthDetailVO's which contains Preauthorization Details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthDetailVO object which contains Preauthorization details
	 * @exception throws TTKException
	 */
	public ArrayList getPreauthList(ArrayList alSearchCriteria) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		Collection<Object> alResultList = new ArrayList<Object>();
		PreAuthDetailVO preauthDetailVO = null;
		ClaimantVO claimantVO = null;
		PreAuthHospitalVO preauthHospitalVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetPreauthList);
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
			cStmtObject.setString(11,(String)alSearchCriteria.get(12));
			cStmtObject.setString(12,(String)alSearchCriteria.get(13));
			cStmtObject.setLong(13,(Long)alSearchCriteria.get(8));

			if(alSearchCriteria.get(9) != null){
				cStmtObject.setLong(15,(Long)alSearchCriteria.get(9));
			}//end of if(alSearchCriteria.get(9) != null)
			else{
				cStmtObject.setString(15,null);
			}//end of else

			cStmtObject.registerOutParameter(14,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(14);
			if(rs != null){
				while(rs.next()){
					preauthDetailVO = new PreAuthDetailVO();
					claimantVO = new ClaimantVO();
					preauthHospitalVO = new PreAuthHospitalVO();

					if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
						preauthDetailVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)

					claimantVO.setName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					preauthDetailVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					preauthDetailVO.setAuthNbr(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));

					if(rs.getString("DATE_OF_HOSPITALIZATION") != null){
						preauthDetailVO.setAdmissionDate(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime()));
						preauthDetailVO.setAdmissionTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ")[1]:"");
						preauthDetailVO.setAdmissionDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("DATE_OF_HOSPITALIZATION") != null)

					if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						preauthDetailVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

					preauthHospitalVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					preauthDetailVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));

					if(rs.getString("HOSP_SEQ_ID") != null){
						preauthHospitalVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
					}//end of if(rs.getString("HOSP_SEQ_ID") != null)

					preauthHospitalVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
					preauthHospitalVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
					preauthHospitalVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
					preauthHospitalVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY_DESCRIPTION")));
					preauthHospitalVO.setStateName(TTKCommon.checkNull(rs.getString("STATE_NAME")));
					preauthHospitalVO.setCountryName(TTKCommon.checkNull(rs.getString("COUNTRY_NAME")));
					preauthHospitalVO.setPincode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
					preauthHospitalVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					preauthHospitalVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
					preauthHospitalVO.setEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
					preauthHospitalVO.setHospStatus(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
					preauthHospitalVO.setRating(TTKCommon.checkNull(rs.getString("RATING")));
					preauthHospitalVO.setEmplNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					preauthHospitalVO.setHospServiceTaxRegnNbr(TTKCommon.checkNull(rs.getString("SERV_TAX_RGN_NUMBER")));
					if(preauthHospitalVO.getRating() != null){
						if(preauthHospitalVO.getRating().equals("G")){
							preauthHospitalVO.setRatingImageName("GoldStar");
							preauthHospitalVO.setRatingImageTitle("Gold Star");
						}//end of if(preauthHospitalVO.getRating().equals("G"))

						if(preauthHospitalVO.getRating().equals("R")){
							preauthHospitalVO.setRatingImageName("BlueStar");
							preauthHospitalVO.setRatingImageTitle("Blue Star (Regular)");
						}//end of if(preauthHospitalVO.getRating().equals("R"))

						if(preauthHospitalVO.getRating().equals("B")){
							preauthHospitalVO.setRatingImageName("BlackStar");
							preauthHospitalVO.setRatingImageTitle("Black Star");
						}//end of if(preauthHospitalVO.getRating().equals("B"))
					}//end of if(preauthHospitalVO.getRating() != null)

					preauthDetailVO.setPreauthTypeDesc(TTKCommon.checkNull(rs.getString("PAT_TYPE")));

					if(rs.getString("PAT_RECEIVED_DATE") != null){
						preauthDetailVO.setReceivedDate(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime()));
						preauthDetailVO.setReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[1]:"");
						preauthDetailVO.setReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)

					if(rs.getString("TOTAL_APP_AMOUNT") != null){
						preauthDetailVO.setApprovedAmt(new BigDecimal(rs.getString("TOTAL_APP_AMOUNT")));
					}//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)

					preauthDetailVO.setClaimantVO(claimantVO);
					preauthDetailVO.setPreAuthHospitalVO(preauthHospitalVO);
					alResultList.add(preauthDetailVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getPreauthList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getPreauthList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getPreauthList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPreauthList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of Cache Object's which contains Previous Claim Seq ID's
	 * @param lngMemberSeqID Member Seq ID
	 * @return ArrayList of Cache Object's Previous Claim Seq ID's
	 * @exception throws TTKException
	 */
	public ArrayList getPrevClaim(long lngMemberSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ArrayList<Object> alPrevClaimList = new ArrayList<Object>();
		CacheObject cacheObject = null;
		try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPrevClaimList);
			cStmtObject.setLong(1,lngMemberSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();
					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("CLAIM_SEQ_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					alPrevClaimList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getPrevClaim()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getPrevClaim()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getPrevClaim()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alPrevClaimList;
	}//end of getPrevClaim(long lngMemberSeqID)

	/**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode PAT
	 * @param strType String object which contains Type
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType) throws TTKException{
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		String strCodingReviewYN = "";
		String strShowCodingOverride = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveReview);
			if(preauthDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(1,preauthDetailVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else

			if(preauthDetailVO.getEventSeqID() != null){
				cStmtObject.setLong(2,preauthDetailVO.getEventSeqID());
			}//end of if(preauthDetailVO.getEventSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(preauthDetailVO.getReviewCount() != null){
				cStmtObject.setInt(3,preauthDetailVO.getReviewCount());
			}//end of if(preauthDetailVO.getReviewCount() != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			if(preauthDetailVO.getRequiredReviewCnt() != null){
				cStmtObject.setLong(4,preauthDetailVO.getRequiredReviewCnt());
			}//end of if(preauthDetailVO.getRequiredReviewCnt() != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			cStmtObject.setString(5,strMode);
			cStmtObject.setString(6,strType);
			cStmtObject.setLong(7,preauthDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(2,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(3,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(4,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(8,Types.VARCHAR);
			cStmtObject.registerOutParameter(9,Types.VARCHAR);
			cStmtObject.registerOutParameter(10,Types.VARCHAR);//CODING_REVIEW_YN
			cStmtObject.registerOutParameter(11,Types.VARCHAR);//SHOW_CODING_OVERRIDE
			cStmtObject.execute();

			lngEventSeqID = cStmtObject.getLong(2);
			intReviewCount = cStmtObject.getInt(3);
			intRequiredReviewCnt = cStmtObject.getInt(4);
			strEventName = cStmtObject.getString(8);
			strReview = cStmtObject.getString(9);
			strCodingReviewYN = cStmtObject.getString(10);
			strShowCodingOverride = cStmtObject.getString(11);

			preauthDetailVO.setEventSeqID(lngEventSeqID);
			preauthDetailVO.setReviewCount(intReviewCount);
			preauthDetailVO.setRequiredReviewCnt(intRequiredReviewCnt);
			preauthDetailVO.setEventName(strEventName);
			preauthDetailVO.setReview(strReview);
			preauthDetailVO.setCoding_review_yn(strCodingReviewYN);
			preauthDetailVO.setShowCodingOverrideYN(strShowCodingOverride);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl saveReview()",sqlExp);
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
						log.error("Error while closing the Connection in ClaimDAOImpl saveReview()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return preauthDetailVO;
	}//end of saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType)
	
	
	
	//added for CR KOC-Decoupling
	/**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode PAT
	 * @param strType String object which contains Type
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public int saveDataEntryReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType) throws TTKException{
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		String strCodingReviewYN = "";
		String strShowCodingOverride = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int rowProcessed = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDataEntryReview);
			
			if(preauthDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(1,preauthDetailVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			
			cStmtObject.setString(2,"G");
			
			cStmtObject.registerOutParameter(3,Types.INTEGER);
			
			cStmtObject.execute();
			
			
			rowProcessed =  cStmtObject.getInt(3);
			
			/*if(preauthDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(1,preauthDetailVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else

			if(preauthDetailVO.getEventSeqID() != null){
				cStmtObject.setLong(2,preauthDetailVO.getEventSeqID());
			}//end of if(preauthDetailVO.getEventSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(preauthDetailVO.getReviewCount() != null){
				cStmtObject.setInt(3,preauthDetailVO.getReviewCount());
			}//end of if(preauthDetailVO.getReviewCount() != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			if(preauthDetailVO.getRequiredReviewCnt() != null){
				cStmtObject.setLong(4,preauthDetailVO.getRequiredReviewCnt());
			}//end of if(preauthDetailVO.getRequiredReviewCnt() != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			cStmtObject.setString(5,strMode);
			cStmtObject.setString(6,strType);
			cStmtObject.setLong(7,preauthDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(2,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(3,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(4,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(8,Types.VARCHAR);
			cStmtObject.registerOutParameter(9,Types.VARCHAR);
			cStmtObject.registerOutParameter(10,Types.VARCHAR);//CODING_REVIEW_YN
			cStmtObject.registerOutParameter(11,Types.VARCHAR);//SHOW_CODING_OVERRIDE
			cStmtObject.execute();

			lngEventSeqID = cStmtObject.getLong(2);
			intReviewCount = cStmtObject.getInt(3);
			intRequiredReviewCnt = cStmtObject.getInt(4);
			strEventName = cStmtObject.getString(8);
			strReview = cStmtObject.getString(9);
			strCodingReviewYN = cStmtObject.getString(10);
			strShowCodingOverride = cStmtObject.getString(11);

			preauthDetailVO.setEventSeqID(lngEventSeqID);
			preauthDetailVO.setReviewCount(intReviewCount);
			preauthDetailVO.setRequiredReviewCnt(intRequiredReviewCnt);
			preauthDetailVO.setEventName(strEventName);
			preauthDetailVO.setReview(strReview);
			preauthDetailVO.setCoding_review_yn(strCodingReviewYN);
			preauthDetailVO.setShowCodingOverrideYN(strShowCodingOverride);*/
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl saveDataEntryReview()",sqlExp);
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
						log.error("Error while closing the Connection in ClaimDAOImpl saveDataEntryReview()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return rowProcessed;
	}//end of saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType)

	
	//ended
	
	
	/**added for CR KOC-Decoupling
	 * This method will save the Review Information and allows you to modify the detail and save
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public int revertProcess(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException {
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		String strCodingReviewYN = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		int rowProcessed = 0;	
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimsRevert);
			
			if(preauthDetailVO.getClaimSeqID() != null){
				cStmtObject.setLong(1,preauthDetailVO.getClaimSeqID());
			}//end of if(preauthDetailVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			
			cStmtObject.setString(2,"G");
			
			cStmtObject.registerOutParameter(3,Types.INTEGER);		
			
			cStmtObject.execute();
			rowProcessed =  cStmtObject.getInt(3);
			/*if(strMode.equals("PAT")){
				if(preauthDetailVO.getPreAuthSeqID() != null){
					cStmtObject.setLong(1,preauthDetailVO.getPreAuthSeqID());
				}//end of if(preauthDetailVO.getPreAuthSeqID() != null)
				else{
					cStmtObject.setString(1,null);
				}//end of else
			}//end of if(strMode.equals("PAT"))
			else{
				if(preauthDetailVO.getClaimSeqID() != null){
					cStmtObject.setLong(1,preauthDetailVO.getClaimSeqID());
				}//end of if(preauthDetailVO.getClaimSeqID() != null)
				else{
					cStmtObject.setString(1,null);
				}//end of else
			}//end of else

			cStmtObject.setString(2,strMode);
			cStmtObject.setLong(3,preauthDetailVO.getUpdatedBy());

			if(preauthDetailVO.getEventSeqID() != null){
				cStmtObject.setLong(4,preauthDetailVO.getEventSeqID());
			}//end of if(preauthDetailVO.getEventSeqID() != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			if(preauthDetailVO.getReviewCount() != null){
				cStmtObject.setInt(5,preauthDetailVO.getReviewCount());
			}//end of if(preauthDetailVO.getReviewCount() != null)
			else{
				cStmtObject.setString(5,null);
			}//end of else

			if(preauthDetailVO.getRequiredReviewCnt() != null){
				cStmtObject.setLong(6,preauthDetailVO.getRequiredReviewCnt());
			}//end of if(preauthDetailVO.getRequiredReviewCnt() != null)
			else{
				cStmtObject.setString(6,null);
			}//end of else

			cStmtObject.registerOutParameter(4,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(5,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(6,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(7,Types.VARCHAR);//EVENT_NAME
			cStmtObject.registerOutParameter(8,Types.VARCHAR);//REVIEW
			cStmtObject.registerOutParameter(9,Types.VARCHAR);//CODING_REVIEW_YN
			cStmtObject.execute();

			lngEventSeqID = cStmtObject.getLong(4);
			intReviewCount = cStmtObject.getInt(5);
			intRequiredReviewCnt = cStmtObject.getInt(6);
			strEventName = cStmtObject.getString(7);
			strReview = cStmtObject.getString(8);
			strCodingReviewYN = cStmtObject.getString(9);

			preauthDetailVO.setEventSeqID(lngEventSeqID);
			preauthDetailVO.setReviewCount(intReviewCount);
			preauthDetailVO.setRequiredReviewCnt(intRequiredReviewCnt);
			preauthDetailVO.setEventName(strEventName);
			preauthDetailVO.setReview(strReview);
			preauthDetailVO.setCoding_review_yn(strCodingReviewYN);*/
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "preauth");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "preauth");
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
					log.error("Error while closing the Statement in PreAuthDAOImpl returnToCoding()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthDAOImpl returnToCoding()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return rowProcessed;
	}//end of returnToCoding(PreAuthDetailVO preauthDetailVO,String strMode)
	
	//ended

	/**
	 * This method returns the HashMap contains ArrayList of HospitalizationVO's which contains Previous Hospitalization Details
	 * @param lngMemberSeqID long value contains Member Seq ID
	 * @param strMode contains CTM - > MR / CNH -> NHCP
	 * @param lngClaimSeqID long value contains Claim Seq ID
	 * @return ArrayList of CacheVO object which contains Previous Hospitalization details
	 * @exception throws TTKException
	 */
	public HashMap getPrevHospList(long lngMemberSeqID,String strMode,long lngClaimSeqID) throws TTKException {
		Connection conn1 = null;
		CallableStatement cStmt = null;
		CallableStatement cStmt1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		HashMap<Object,Object> hmPrevHospList = null;
		ArrayList<Object> alPrevHospList = null;
		long lngPrevHospClaimSeqID = 0;
		HospitalizationDetailVO hospitalizationDetailVO = null;
		PreAuthHospitalVO preauthHospitalVO = null;
		try{

			conn1 = ResourceManager.getConnection();
			cStmt = (java.sql.CallableStatement)conn1.prepareCall(strPrevHospList);
			cStmt1 = (java.sql.CallableStatement)conn1.prepareCall(strPrevHospDetails);

			cStmt.setString(1,strMode);
			cStmt.setLong(2,lngMemberSeqID);
			cStmt.registerOutParameter(3,OracleTypes.CURSOR);
			cStmt.setLong(4,lngClaimSeqID);
			cStmt.execute();
			rs = (java.sql.ResultSet)cStmt.getObject(3);
			if(rs != null){
				while(rs.next()){

					if(hmPrevHospList == null){
						hmPrevHospList=new HashMap<Object,Object>();
					}//end of if(hmPrevHospList == null)
						lngPrevHospClaimSeqID = rs.getLong("CLAIM_SEQ_ID");
					cStmt1.setLong(1,lngPrevHospClaimSeqID);
					cStmt1.registerOutParameter(2,OracleTypes.CURSOR);
					cStmt1.execute();
					rs1 = (java.sql.ResultSet)cStmt1.getObject(2);
					alPrevHospList = null;
					if(rs1 != null){
						while(rs1.next()){
							hospitalizationDetailVO = new HospitalizationDetailVO();
							preauthHospitalVO = new PreAuthHospitalVO();

							if(alPrevHospList == null){
								alPrevHospList = new ArrayList<Object>();
							}//end of if(alPrevHospList == null)

								if(rs1.getString("CLAIM_SEQ_ID") != null){
									hospitalizationDetailVO.setPrevHospClaimSeqID(new Long(rs1.getLong("CLAIM_SEQ_ID")));
								}//end of if(rs1.getString("CLAIM_SEQ_ID") != null)

								hospitalizationDetailVO.setPrevHospDesc(TTKCommon.checkNull(rs1.getString("PREV_HOSP")));

								if(rs1.getString("DATE_OF_ADMISSION") != null){
									hospitalizationDetailVO.setAdmissionDate(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime()));
									hospitalizationDetailVO.setAdmissionTime(TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ")[1]:"");
									hospitalizationDetailVO.setAdmissionDay(TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_ADMISSION").getTime())).split(" ")[2]:"");
								}//end of if(rs.getString("DATE_OF_ADMISSION") != null)

								if(rs1.getString("DATE_OF_DISCHARGE") != null){
									hospitalizationDetailVO.setDischargeDate(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime()));
									hospitalizationDetailVO.setDischargeTime(TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[1]:"");
									hospitalizationDetailVO.setDischargeDay(TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs1.getTimestamp("DATE_OF_DISCHARGE").getTime())).split(" ")[2]:"");
								}//end of if(rs.getString("DATE_OF_DISCHARGE") != null)

								if(rs1.getString("HOSP_SEQ_ID") != null){
									preauthHospitalVO.setHospSeqId(new Long(rs1.getLong("HOSP_SEQ_ID")));
								}//end of if(rs1.getString("HOSP_SEQ_ID") != null)

								preauthHospitalVO.setHospitalName(TTKCommon.checkNull(rs1.getString("HOSP_NAME")));
								preauthHospitalVO.setEmplNumber(TTKCommon.checkNull(rs1.getString("EMPANEL_NUMBER")));
								preauthHospitalVO.setAddress1(TTKCommon.checkNull(rs1.getString("ADDRESS_1")));
								preauthHospitalVO.setAddress2(TTKCommon.checkNull(rs1.getString("ADDRESS_2")));
								preauthHospitalVO.setAddress3(TTKCommon.checkNull(rs1.getString("ADDRESS_3")));
								preauthHospitalVO.setCityDesc(TTKCommon.checkNull(rs1.getString("CITY_DESCRIPTION")));
								preauthHospitalVO.setStateName(TTKCommon.checkNull(rs1.getString("STATE_NAME")));
								preauthHospitalVO.setPincode(TTKCommon.checkNull(rs1.getString("PIN_CODE")));
								preauthHospitalVO.setEmailID(TTKCommon.checkNull(rs1.getString("PRIMARY_EMAIL_ID")));
								preauthHospitalVO.setPhoneNbr1(TTKCommon.checkNull(rs1.getString("OFF_PHONE_NO_1")));
								preauthHospitalVO.setPhoneNbr2(TTKCommon.checkNull(rs1.getString("OFF_PHONE_NO_2")));
								preauthHospitalVO.setFaxNbr(TTKCommon.checkNull(rs1.getString("OFFICE_FAX_NO")));
								preauthHospitalVO.setHospStatus(TTKCommon.checkNull(rs1.getString("EMPANEL_DESCRIPTION")));
								preauthHospitalVO.setRating(TTKCommon.checkNull(rs1.getString("RATING")));

								if(preauthHospitalVO.getRating() != null){
									if(preauthHospitalVO.getRating().equals("G")){
										preauthHospitalVO.setRatingImageName("GoldStar");
										preauthHospitalVO.setRatingImageTitle("Gold Star");
									}//end of if(preAuthHospitalVO.getRating().equals("G"))

									if(preauthHospitalVO.getRating().equals("R")){
										preauthHospitalVO.setRatingImageName("BlueStar");
										preauthHospitalVO.setRatingImageTitle("Blue Star (Regular)");
									}//end of if(preAuthHospitalVO.getRating().equals("R"))

									if(preauthHospitalVO.getRating().equals("B")){
										preauthHospitalVO.setRatingImageName("BlackStar");
										preauthHospitalVO.setRatingImageTitle("Black Star");
									}//end of if(preAuthHospitalVO.getRating().equals("B"))
								}//end of if(preAuthHospitalVO.getRating() != null)

								hospitalizationDetailVO.setPreauthHospitalVO(preauthHospitalVO);
								hmPrevHospList.put(lngPrevHospClaimSeqID,hospitalizationDetailVO);
						}//end of inner while(rs1.next())
					}//end of inner if(rs1 != null)
					if (rs1 != null){
						rs1.close();
					}//end of if (rs1 != null)
					rs1 = null;
				}//end of outer while(rs.next())
			}//end of outer if(rs != null)
			if (cStmt1 != null){
				cStmt1.close();
			}//end of if (cStmt1 != null)
			cStmt1 = null;
			return hmPrevHospList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Second Resultset in ClaimDAOImpl getPrevHospList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try
					{
						if (rs != null) rs.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in ClaimDAOImpl getPrevHospList()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
					{
						try
						{
							if(cStmt1 != null) cStmt1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Statement in ClaimDAOImpl getPrevHospList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
						{
							try
							{
								if(cStmt != null) cStmt.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the FirstStatement in ClaimDAOImpl getPrevHospList()",sqlExp);
								throw new TTKException(sqlExp, "claim");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
							{
								try{
									if(conn1 != null) conn1.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in ClaimDAOImpl getPrevHospList()",sqlExp);
									throw new TTKException(sqlExp, "claim");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs1 = null;
				rs = null;
				cStmt1 = null;
				cStmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
	}//end of getPrevHospList(long lngMemberSeqID,String strMode)

	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteClaimGeneral(ArrayList alDeleteList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteClaimGeneral);
			cStmtObject.setString(1, (String)alDeleteList.get(0));//FLAG PAT/AIL/SFL/INV/PED/COU/BUFFER
			cStmtObject.setString(2, (String)alDeleteList.get(1));//CONCATENATED STRING OF  delete SEQ_IDS

			if(alDeleteList.get(2) != null){
				cStmtObject.setLong(3,(Long)alDeleteList.get(2));//Mandatory  CLM_ENROLL_DETAIL_SEQ_ID
			}//end of if(alDeleteList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			if(alDeleteList.get(3) != null){
				cStmtObject.setLong(4,(Long)alDeleteList.get(3));//Mandatory CLAIM_SEQ_ID
			}//end of if(alDeleteList.get(2) != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			cStmtObject.setLong(5,(Long)alDeleteList.get(4));//ADDED_BY
			cStmtObject.registerOutParameter(6, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl deleteClaimGeneral()",sqlExp);
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
						log.error("Error while closing the Connection in ClaimDAOImpl deleteClaimGeneral()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteClaimGeneral(ArrayList alDeleteList)

	/**
	 * This method returns the ArrayList object, which contains all the Users for the Corresponding TTK Branch
	 * @param alAssignUserList ArrayList Object contains ClaimSeqID,PolicySeqID and TTKBranch
	 * @return ArrayList object which contains all the Users for the Corresponding TTK Branch
	 * @exception throws TTKException
	 */
	public ArrayList getAssignUserList(ArrayList alAssignUserList) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ArrayList<Object> alUserList = new ArrayList<Object>();
		CacheObject cacheObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssignUserList);
			cStmtObject.setLong(1,(Long)alAssignUserList.get(0));//Mandatory
			if(alAssignUserList.get(1) != null){
				cStmtObject.setLong(2,(Long)alAssignUserList.get(1));
			}//end of if(alAssignUserList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.setLong(3,(Long)alAssignUserList.get(2));//Mandatory
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();
					cacheObject.setCacheId((rs.getString("CONTACT_SEQ_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					alUserList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alUserList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getPrevClaim()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getAssignUserList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getAssignUserList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getAssignUserList(ArrayList alAssignUserList)

	/**
	 * This method returns the ClauseVO, which contains all the Clause details
	 * @param alClauseList contains Claim seq id,Policy Seq ID,EnrolTypeID,AdmissionDate to get the Clause Details
	 * @return ClauseVO object which contains all the Clause details
	 * @exception throws TTKException
	 */
	public ClauseVO getClauseDetail(ArrayList alClauseList) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ClauseVO clauseVO = new ClauseVO();
		ArrayList<Object> alClauseDetail = new ArrayList<Object>();
		PolicyClauseVO policyClauseVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClauseDetail);

			if(alClauseList.get(0) != null){
				cStmtObject.setLong(1,(Long)alClauseList.get(0));
			}//end of if(alClauseList.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			if(alClauseList.get(1) != null){
				cStmtObject.setLong(2,(Long)alClauseList.get(1));
			}//end of if(alClauseList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			cStmtObject.setLong(3,(Long)alClauseList.get(2));
			cStmtObject.setString(4,(String)alClauseList.get(3));
			//cStmtObject.setString(4,(String)alClauseList.get(3));
			if(alClauseList.get(4) != null && alClauseList.get(4) != ""){

				cStmtObject.setTimestamp(5,new Timestamp(TTKCommon.getUtilDate(alClauseList.get(4).toString()).getTime()));
			}//end of if(alClauseList.get(4) != null)
			else{
				cStmtObject.setString(5,null);
			}//end of else

			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(6);

			if(alClauseList.get(0) != null){
				rs2 = (java.sql.ResultSet)cStmtObject.getObject(7);
			}//end of if(alClauseList.get(0) != null)

			if(rs1 != null){
				while(rs1.next()){
					policyClauseVO = new PolicyClauseVO();

					if(rs1.getString("CLAUSE_SEQ_ID") != null){
						policyClauseVO.setClauseSeqID(new Long(rs1.getLong("CLAUSE_SEQ_ID")));
					}//end of if(rs1.getString("CLAUSE_SEQ_ID") != null)

					policyClauseVO.setClauseNbr(TTKCommon.checkNull(rs1.getString("CLAUSE_NUMBER")));
					policyClauseVO.setClauseDesc(TTKCommon.checkNull(rs1.getString("CLAUSE_DESCRIPTION")));
					policyClauseVO.setSelectedYN(TTKCommon.checkNull(rs1.getString("SELECTED_YN")));

					if(rs1.getString("PROD_POLICY_SEQ_ID") != null){
						policyClauseVO.setProdPolicySeqID(new Long(rs1.getLong("PROD_POLICY_SEQ_ID")));
					}//end of if(rs1.getString("PROD_POLICY_RULE_SEQ_ID") != null)

					alClauseDetail.add(policyClauseVO);
				}//end of while(rs1.next())
				clauseVO.setPolicyClauseVO(alClauseDetail);
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){
					clauseVO.setRejHeaderInfo(TTKCommon.checkNull(rs2.getString("REJ_HEADER_INFO")));
					clauseVO.setRejFooterInfo(TTKCommon.checkNull(rs2.getString("REJ_FOOTER_INFO")));
					clauseVO.setLetterTypeID(TTKCommon.checkNull(rs2.getString("LTR_GENERAL_TYPE_ID")));
					clauseVO.setClaimTypeID(TTKCommon.checkNull(rs2.getString("CLAIM_GENERAL_TYPE_ID")));
					if(rs2.getString("INS_SEQ_ID") != null){
						clauseVO.setInsSeqID(new Long(rs2.getLong("INS_SEQ_ID")));
					}//end of if(rs2.getString("INS_SEQ_ID") != null)
				}//end of while(rs2.next())
			}//end of if(rs2 != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getClauseDetail()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the second resultset now.
				{
					try{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in ClaimDAOImpl getClauseDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in ClaimDAOImpl getClauseDetail()",sqlExp);
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
								log.error("Error while closing the Connection in ClaimDAOImpl getClauseDetail()",sqlExp);
								throw new TTKException(sqlExp, "claim");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return clauseVO;
	}//end of getClauseDetail(ArrayList alClauseList)

	/**
	 * This method saves the Clause Details
	 * @param alClauseList the arraylist which contains the Clause Details which has to be saved
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public String saveClauseDetail(ArrayList alClauseList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		String strClauses="";
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClauseDetail);

			if(alClauseList.get(0) != null){
				cStmtObject.setLong(1,(Long)alClauseList.get(0));
			}//end of if(alClauseList.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			if(alClauseList.get(1) != null){
				cStmtObject.setLong(2,(Long)alClauseList.get(1));
			}//end of if(alClauseList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			cStmtObject.setString(3,(String)alClauseList.get(2));
			cStmtObject.setString(4,(String)alClauseList.get(3));
			cStmtObject.setString(5,(String)alClauseList.get(4));
			cStmtObject.setString(6,(String)alClauseList.get(5));
			cStmtObject.setLong(7,(Long)alClauseList.get(6));
			cStmtObject.registerOutParameter(8, Types.VARCHAR);
			cStmtObject.registerOutParameter(9, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(9);//ROWS_PROCESSED
			log.debug("iResult value is :"+iResult);
			strClauses = cStmtObject.getString(8);//Clauses which are savd
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in ClaimDAOImpl saveClauseDetail()",sqlExp);
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
						log.error("Error while closing the Connection in ClaimDAOImpl saveClauseDetail()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strClauses;
	}//end of saveClauseDetail(ArrayList alClauseList)

	/**
	 * This method returns the ArrayList object, which contains list of Letters to be sent for Claims Rejection
	 * @param alLetterList ArrayList Object contains ClaimTypeID and Ins Seq ID
	 * @return ArrayList object which contains list of Letters to be sent for Claims Rejection
	 * @exception throws TTKException
	 */
	public ArrayList getRejectionLetterList(ArrayList alLetterList) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CacheObject cacheObject = null;
		ArrayList<Object> alRejectionLetterList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strRejectionLetterList);
			
			cStmtObject.setString(1,(String)alLetterList.get(0));

			if(alLetterList.get(1) != null && alLetterList.get(0).equals("CTM")){
				cStmtObject.setLong(2,(Long)alLetterList.get(1));
			}//end of if(alLetterList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					cacheObject = new CacheObject();
					
					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					alRejectionLetterList.add(cacheObject);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return alRejectionLetterList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getRejectionLetterList()",sqlExp);
					throw new TTKException(sqlExp, "claim");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getRejectionLetterList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getRejectionLetterList()",sqlExp);
							throw new TTKException(sqlExp, "claim");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getRejectionLetterList(ArrayList alLetterList)

	/**
	 * This method reassign the enrollment ID
	 * @param alReAssignEnrID the arraylist of details for which have to be reassigned
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int reAssignEnrID(ArrayList alReAssignEnrID) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strReAssignEnrID);
			cStmtObject.setLong(1,(Long)alReAssignEnrID.get(0));//CLAIM_SEQ_ID
			cStmtObject.setString(2,(String)alReAssignEnrID.get(1));//CLM_STATUS_GENERAL_TYPE_ID			
			cStmtObject.setLong(3,(Long)alReAssignEnrID.get(2));//MEMBER_SEQ_ID              
			cStmtObject.setLong(4,(Long)alReAssignEnrID.get(3));//ADDED_BY
			cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "preauth");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "preauth");
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
					log.error("Error while closing the Statement in PreAuthDAOImpl reAssignEnrID()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in PreAuthDAOImpl reAssignEnrID()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of reAssignEnrID(ArrayList alReAssignEnrID)


	/**
	 * This method Gets the service Tax amount to be paid
	 * @param authorizationVO AuthorizationVO contains Settlement(Authorization) information
	 * @return Object[] the values,of  SERV_TAX_CALC_AMOUNT , ApprovedAmount and SERV_TAX_CALC_PERCENTAGE
	 * @exception throws TTKException
	 */
	public Object[] getServTaxCal(AuthorizationVO authorizationVO) throws TTKException
	{

		Object[] objArrayResult = new Object[3];
		BigDecimal bdApprovedAmount = null;
		BigDecimal bdTaxAmtPaid = null;
		BigDecimal bdSerTaxCalPer = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strServTaxCal);
			if(authorizationVO.getClaimSeqID() != null)
			{
				cStmtObject.setLong(1, authorizationVO.getClaimSeqID());
			}//end of if(authorizationVO.getClaimSeqID() != null)
				else
				{
					cStmtObject.setLong(1,0);
				}//end of else
			if(authorizationVO.getCopayAmount() != null)
			{
				cStmtObject.setBigDecimal(2, authorizationVO.getCopayAmount());
			}//end of if(authorizationVO.getCopayAmount() != null)
			else
			{
				cStmtObject.setBigDecimal(2,new BigDecimal("0.00"));
			}//end of else
			if(authorizationVO.getDiscountAmount() != null)
			{
				cStmtObject.setBigDecimal(3,authorizationVO.getDiscountAmount());
			}//end of if(authorizationVO.getDiscountAmount() != null) 
			else
			{
				cStmtObject.setBigDecimal(3,new BigDecimal("0.00"));
			}//end of else
			/*if(authorizationVO.getTaxAmtPaid() != null)
            {
            	cStmtObject.setBigDecimal(4,authorizationVO.getTaxAmtPaid());
            }//end of if(authorizationVO.getTaxAmtPaid() != null)
            else
            {
            	cStmtObject.setBigDecimal(4,new BigDecimal("0.00"));
            }//end of else
			 */            cStmtObject.registerOutParameter(4,Types.BIGINT);
			 cStmtObject.registerOutParameter(5,Types.BIGINT);
			 cStmtObject.registerOutParameter(6,Types.BIGINT);
			 cStmtObject.execute();
			 bdTaxAmtPaid = (BigDecimal)cStmtObject.getBigDecimal(4);
			 bdApprovedAmount =(BigDecimal) cStmtObject.getBigDecimal(5);
			 bdSerTaxCalPer =(BigDecimal) cStmtObject.getBigDecimal(6);
			 objArrayResult[0] = (bdTaxAmtPaid);
			 objArrayResult[1] = (bdApprovedAmount);
			 objArrayResult[2] = (bdSerTaxCalPer);

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "claim");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "claim");
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
					log.error("Error while closing the Statement in Claim DAOImpl getServTaxCal()",sqlExp);
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
						log.error("Error while closing the Connection in ClaimDAOImpl getServTaxCal()",sqlExp);
						throw new TTKException(sqlExp, "claim");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "claim");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return objArrayResult;  
	}//end of getServTaxCal(AuthorizationVO authorizationVO)

	//    public static void main(String a[]) throws Exception{
	//ClaimDAOImpl claimDAO = new ClaimDAOImpl();
	/*ArrayList alShortfallList = new ArrayList();
    	ArrayList alShortfallDetail = new ArrayList();
    	CacheObject cacheObject = new CacheObject();
    	alShortfallList.add(new Long(126384));
    	alShortfallList.add("");
    	alShortfallDetail = claimDAO.getInwardShortfallDetail(alShortfallList);
    	if(alShortfallDetail != null){
    		for(int i=0;i<alShortfallDetail.size();i++){
    			cacheObject = (CacheObject)alShortfallDetail.get(i);
    		}
    	}*/
	//ArrayList alClauseList = new ArrayList();

	/*alClauseList.add(new Long(127831));
    	alClauseList.add(null);
    	alClauseList.add(new Long(323222));
    	alClauseList.add("IND");
    	//alClauseList.add("06/13/2007");
    	alClauseList.add("07/07/2007");
    	//alClauseList.add("07-JUL-2007");
	 */
	//claimDAO.getClauseDetail(alClauseList);

	/*ArrayList alClauseList = new ArrayList();
    	alClauseList.add(new Long(127623));
    	alClauseList.add("|1|");
    	alClauseList.add("test");
    	alClauseList.add("test");
    	alClauseList.add(new Long(56503));
    	claimDAO.saveClauseDetail(alClauseList);*/

	/*ArrayList<Object> alLetterList = new ArrayList<Object>();
    	alLetterList.add("CTM");
    	alLetterList.add(new Long(2));
    	claimDAO.getRejectionLetterList(alLetterList);*/
	//boolean bResultUpdate = claimDAO.releasePreauth(new Long(60121));
	//    }//end of main
}//end of ClaimDAOImpl
