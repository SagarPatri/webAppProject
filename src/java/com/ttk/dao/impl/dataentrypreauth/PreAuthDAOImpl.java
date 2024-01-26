/**
 *  @ (#) PreAuthDAOImpl.java April 10, 2006
 *   Project 	   : TTK HealthCare Services
 *   File          : PreAuthDAOImpl.java
 *   Author        : RamaKrishna K M
 *   Company       : Span Systems Corporation
 *   Date Created  : April 10, 2006
 *
 *   @author       :  RamaKrishna K M
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 */

package com.ttk.dao.impl.dataentrypreauth;

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
import java.sql.PreparedStatement;
import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;


import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.preauth.AdditionalDetailVO;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.BalanceCopayDeductionVO;//added as per KOC 1142 and 1140 Change Request and 1165
import com.ttk.dto.preauth.BalanceSIInfoVO;
import com.ttk.dto.preauth.ClaimantNewVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.DiscrepancyVO;
import com.ttk.dto.preauth.EnhancementVO;
import com.ttk.dto.preauth.MemberBufferVO;
import com.ttk.dto.preauth.PreAuthAssignmentVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.RestorationPreauthClaimVO;
import com.ttk.dto.preauth.SIBreakupInfoVO;
import com.ttk.dto.preauth.SIInfoVO;
import com.ttk.dto.preauth.StopPreauthClaimVO;
import com.ttk.dto.security.GroupVO;

public class PreAuthDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(PreAuthDAOImpl.class );
	private static final String strClaimInsIntimate = "{CALL CLAIMS_PKG_DATA_ENTRY.CLM_INS_INTIMATE(?,?,?,?)}";
	private static final String strPatInsIntimate = "{CALL PRE_AUTH_PKG_DATA_ENTRY.PAT_INS_INTIMATE(?,?,?,?)}";
	
	//private static final String strPreAuthList = "{CALL PRE_AUTH_SQL_PKG.SELECT_PRE_AUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strPreAuthorization = "{CALL PRE_AUTH_SQL_PKG.SELECT_PRE_AUTH(?,?,?,?,?,?)}";
	private static final String strPreAuthList = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_PRE_AUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPreAuthorization = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_PRE_AUTH(?,?,?,?,?,?)}";
	private static final String strSavePreAuthorization = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SAVE_PREAUTH(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strOverridePreauth = "{CALL PRE_AUTH_PKG_DATA_ENTRY.OVERRIDE_PREAUTH(?,?,?,?,?,?,?,?)}";
	private static final String strSaveReview = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SET_REVIEW(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveAssignUsers = "{CALL PRE_AUTH_PKG.SAVE_ASSIGN_USERS(?,?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strGetAssignTo = "{CALL PRE_AUTH_SQL_PKG.SELECT_ASSIGN_TO(?,?,?,?)}";
	private static final String strGetAssignTo = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_ASSIGN_TO(?,?,?,?)}";
	private static final String strPAAssignUserList = "{CALL PRE_AUTH_PKG_DATA_ENTRY.MANUAL_ASSIGN_PREAUTH(?,?,?,?)}";
	private static final String strClaimAssignUserList = "{CALL CLAIMS_PKG_DATA_ENTRY.MANUAL_ASSIGN_PREAUTH(?,?,?,?)}";
	//private static final String strEnrollmentList = "{CALL PRE_AUTH_SQL_PKG.SELECT_ENROLLMENT_ID_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strEnhancementList = "{CALL PRE_AUTH_SQL_PKG.SELECT_ENHANCEMENT_LIST(?,?,?,?,?)}";
	//private static final String strAuthorizationDetails = "{CALL PRE_AUTH_SQL_PKG.SELECT_AUTHORIZATION(?,?,?,?,?)}";
	//private static final String strSettlementDetails = "{CALL CLAIMS_SQL_PKG.SELECT_SETTLEMENT(?,?,?)}";
	private static final String strEnrollmentList = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_ENROLLMENT_ID_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectMemberList = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_MEMBER_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectEnrollmentID = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_ENROLLMENT_ID(?,?)}";

	private static final String strEnhancementList = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_ENHANCEMENT_LIST(?,?,?,?,?)}";
	private static final String strAuthorizationDetails = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_AUTHORIZATION(?,?,?,?,?)}";
	private static final String strSettlementDetails = "{CALL CLAIMS_PKG_DATA_ENTRY.SELECT_SETTLEMENT(?,?,?)}";
		//Modification as per KOC 1216b Change request
	//private static final String strSaveAuthorization = "{CALL PRE_AUTH_PKG.SAVE_AUTHORIZATION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveAuthorization = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SAVE_AUTHORIZATION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added 3 parameters as per 1140 and 1142 and 1065
	private static final String strSaveSettlement = "{CALL CLAIMS_PKG_DATA_ENTRY.SAVE_SETTLEMENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added 3 parameters as per 1140 and 1142 and 1065
	private static final String strDeletePATGeneral = "{CALL PRE_AUTH_PKG_DATA_ENTRY.DELETE_PAT_GENERAL(?,?,?,?,?,?)}";
	//private static final String strGetDiscrepancy = "{CALL PRE_AUTH_SQL_PKG.SELECT_DISCREPANCY_INFO(?,?,?,?)}";
	private static final String strGetDiscrepancy = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SELECT_DISCREPANCY_INFO(?,?,?,?)}";
	private static final String strResolveDiscrepancy = "{CALL PRE_AUTH_PKG_DATA_ENTRY.SAVE_DISCREPANCY(";
	private static final String strReturnToCoding = "{CALL CODING_PKG_DATA_ENTRY.RETURN_TO_CODING(?,?,?,?,?,?,?,?,?)}";
	
	//added for CR KOC-Decoupling
	private static final String strClaimsRevert = "{CALL CLAIMS_PKG_DATA_ENTRY.claims_revert(?,?,?)}";
	
	
	private static final String strReAssignEnrID= "{CALL PRE_AUTH_PKG_DATA_ENTRY.RE_ASSIGN_ENRL_ID(?,?,?,?,?,?)}";
	//Modified  as per KOC 1216 B change Request 
	//private static final String strBalanceSIInfo= "{CALL PRE_AUTH_PKG.SELECT_CLAIMANT_BALANCE(?,?,?,?,?,?)}";
	private static final String strBalanceSIInfo= "{CALL PRE_AUTH_PKG.SELECT_CLAIMANT_BALANCE(?,?,?,?,?,?,?,?)}";//changes for koc1289_1272
	//Modified as per KOC 1216 B change Request 
	private static final String strPreAuthCopayAdvice="{CALL PRE_AUTH_PKG_DATA_ENTRY.COPAY_ADVICED(?,?,?,?,?,?)}";//Changes As Per KOC 1140 && 1142 
	private static final String strClaimsCopayAdvice="{CALL CLAIMS_PKG_DATA_ENTRY.COPAY_ADVICED(?,?,?,?,?,?)}";	//Changes As Per KOC 1140 && 1142

	private static final int PAT_ENROLL_DETAIL_SEQ_ID = 1;
	private static final int MEMBER_SEQ_ID = 2;
	private static final int TPA_ENROLLMENT_ID = 3;
	private static final int POLICY_NUMBER = 4;
	private static final int POLICY_SEQ_ID = 5;
	private static final int INS_SEQ_ID = 6;
	private static final int GENDER_GENERAL_TYPE_ID = 7;
	private static final int CLAIMANT_NAME = 8;
	private static final int MEM_AGE = 9;
	private static final int DATE_OF_INCEPTION = 10;
	private static final int DATE_OF_EXIT = 11;
	private static final int MEM_TOTAL_SUM_INSURED = 12;
	private static final int CATEGORY_GENERAL_TYPE_ID = 13;
	private static final int INSURED_NAME = 14;
	private static final int PHONE_1 = 15;
	private static final int POLICY_EFFECTIVE_FROM = 16;
	private static final int POLICY_EFFECTIVE_TO = 17;
	private static final int PRODUCT_SEQ_ID = 18;
	private static final int INS_STATUS_GENERAL_TYPE_ID = 19;
	private static final int ENROL_TYPE_ID = 20;
	private static final int POLICY_SUB_GENERAL_TYPE_ID = 21;
	private static final int GROUP_REG_SEQ_ID = 22;
	private static final int DATE_OF_HOSPITALIZATION = 23;
	private static final int TPA_OFFICE_SEQ_ID = 24;
	private static final int HOSP_SEQ_ID = 25;
	private static final int PAT_STATUS_GENERAL_TYPE_ID = 26;
	private static final int PAT_GEN_DETAIL_SEQ_ID = 27;
	private static final int PAT_GENERAL_TYPE_ID =28;
	private static final int PAT_PRIORITY_GENERAL_TYPE_ID = 29;
	private static final int PAT_RECEIVED_DATE =30;
	private static final int PREV_APPROVED_AMOUNT = 31;
	private static final int PAT_REQUESTED_AMOUNT = 32;
	private static final int TREATING_DR_NAME = 33;
	private static final int PAT_RCVD_THRU_GENERAL_TYPE_ID = 34;
	private static final int PHONE_NO_IN_HOSPITALISATION = 35;
	private static final int DISCREPANCY_PRESENT_YN = 36;
	private static final int PARENT_GEN_DETAIL_SEQ_ID = 37;
	private static final int REMARKS = 38;
	private static final int AVA_SUM_INSURED = 39;
	private static final int AVA_CUM_BONUS = 40;
	private static final int ADDITIONAL_DTL_SEQ_ID = 41;
	private static final int RELSHIP_TYPE_ID = 42;
	private static final int EMPLOYEE_NO = 43;
	private static final int EMPLOYEE_NAME = 44;
	private static final int DATE_OF_JOINING = 45;
	private static final int INFO_RECEIVED_DATE = 46;
	private static final int COMM_TYPE_ID = 47;
	private static final int INFO_RCVD_GENERAL_TYPE_ID = 48;
	private static final int REFERENCE_NO = 49;
	private static final int CONTACT_PERSON = 50;
	private static final int ADDITIONAL_REMARKS = 51;
	private static final int AVA_BUFFER_AMOUNT = 52;
	private static final int BUFF_DTL_SEQ_ID = 53;
	private static final int BUFFER_ALLOWED_YN = 54;
	private static final int ADMIN_AUTH_GENERAL_TYPE_ID = 55;
	private static final int COMPLETED_YN = 56;
	private static final int PRE_AUTH_DMS_REFERENCE_ID = 57;
	/*private static final int SELECTION_TYPE = 58;
	private static final int USER_SEQ_ID = 59;
	private static final int ROW_PROCESSED = 60;*/
	private static final int EMAIL_ID = 58;
	private static final int INS_SCHEME = 59;
	private static final int CERTIFICATE_NO	= 60;
	private static final int INS_CUSTOMER_CODE = 61;
	private static final int SELECTION_TYPE = 62;
	private static final int USER_SEQ_ID = 63;
	private static final int ROW_PROCESSED = 64;

	/**
	 * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
	 * @exception throws TTKException
	 */
	public ArrayList getPreAuthList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthVO preAuthVO = null;
		String strSuppressLink[]={"1","7","8","9"};
		String strGroupID="";
		String strPolicyGrpID ="";
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthList);
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
			cStmtObject.setString(17,(String)alSearchCriteria.get(18));
			cStmtObject.setString(18,(String)alSearchCriteria.get(19));
			cStmtObject.setString(19,(String)alSearchCriteria.get(20));
			 cStmtObject.setString(20,(String)alSearchCriteria.get(21));
			cStmtObject.setString(21,(String)alSearchCriteria.get(22));
			cStmtObject.setString(22,(String)alSearchCriteria.get(23));
			cStmtObject.setString(23,(String)alSearchCriteria.get(24));
			//cStmtObject.setString(24,(String)alSearchCriteria.get(25));
			cStmtObject.setLong(24,(Long)alSearchCriteria.get(16));
			cStmtObject.registerOutParameter(25,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(25);
			
			if(rs != null){
				while(rs.next()){
					preAuthVO = new PreAuthVO();

					if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
						preAuthVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)

					if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						preAuthVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

					preAuthVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
					preAuthVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					preAuthVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					preAuthVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					preAuthVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));

					if(rs.getString("PAT_RECEIVED_DATE") != null){
						preAuthVO.setReceivedDate(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime()));
					}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)

					preAuthVO.setPriorityTypeID("");
					preAuthVO.setEnhancedYN(TTKCommon.checkNull(rs.getString("PAT_ENHANCED_YN")));
					preAuthVO.setDMSRefID(TTKCommon.checkNull(rs.getString("PRE_AUTH_DMS_REFERENCE_ID")));

					if(rs.getString("BUFFER_ALLOWED_YN") != null){
						preAuthVO.setBufferAllowedYN(TTKCommon.checkNull(rs.getString("BUFFER_ALLOWED_YN")));
					}//end of if(rs.getString("BUFFER_ALLOWED_YN") != null)
					else{
						preAuthVO.setBufferAllowedYN("N");
					}//end of else

					if(rs.getString("POLICY_SEQ_ID") != null){
						preAuthVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					if(rs.getString("INS_SEQ_ID") != null){
						preAuthVO.setInsuranceSeqID(new Long(rs.getLong("INS_SEQ_ID")));
					}//end of if(rs.getString("INS_SEQ_ID") != null)

					if(rs.getString("MEMBER_SEQ_ID") != null){
						preAuthVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if(rs.getString("PARENT_GEN_DETAIL_SEQ_ID") != null){
						preAuthVO.setParentGenDtlSeqID(new Long(rs.getLong("PARENT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PARENT_GEN_DETAIL_SEQ_ID") != null)

					if(rs.getString("ASSIGN_USERS_SEQ_ID") != null){
						preAuthVO.setAssignUserSeqID(new Long(rs.getLong("ASSIGN_USERS_SEQ_ID")));
					}//end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)

					if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("HIG")){
						preAuthVO.setPriorityImageName("HighPriorityIcon");
						preAuthVO.setPriorityImageTitle("High Priority");
					}//end of if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("HIG"))

					if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("LOW")){
						preAuthVO.setPriorityImageName("LowPriorityIcon");
						preAuthVO.setPriorityImageTitle("Low Priority");
					}//end of if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("LOW"))

					preAuthVO.setEnhanceIconYN(TTKCommon.checkNull(rs.getString("ENHANCE_ICON_YN")));

					if(rs.getString("ENHANCE_ICON_YN").equals("Y")){
						preAuthVO.setPreAuthImageName("EnhancedIcon");
						preAuthVO.setPreAuthImageTitle("Enhance Pre-Auth");
					}//end of if(rs.getString("ENHANCE_ICON_YN").equals("Y"))

					preAuthVO.setShortfallYN(TTKCommon.checkNull(rs.getString("SHRTFALL_YN")));
					if(rs.getString("SHRTFALL_YN").equals("Y")){
						preAuthVO.setShortfallImageName("shortfall");
						preAuthVO.setShortfallImageTitle("Shortfall Received");
					}//end of if(rs.getString("SHRTFALL_YN").equals("Y"))

					preAuthVO.setReadyToProcessYN(TTKCommon.checkNull(rs.getString("READY_TO_PROCESS_YN")));

					if(rs.getString("TPA_OFFICE_SEQ_ID") != null){
						preAuthVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					}//end of if(rs.getString("TPA_OFFICE_SEQ_ID") != null)

					preAuthVO.setShowBandYN(TTKCommon.checkNull(rs.getString("SHOW_BAND_YN")));
					preAuthVO.setCoding_review_yn(TTKCommon.checkNull(rs.getString("coding_review_yn")));

					if(rs.getString("REJECT_COMPLETE_YN").equals("Y")){
						preAuthVO.setRejImageName("DeleteIcon");
						preAuthVO.setRejImageTitle("Preauth Rejected");
					}//end of if(rs.getString("REJECT_COMPLETE_YN").equals("Y"))

					ArrayList alUserGroup = (ArrayList)alSearchCriteria.get(17);
					strGroupID = TTKCommon.checkNull(rs.getString("GROUP_BRANCH_SEQ_ID"));
					boolean bSuppress = true;
					if(alUserGroup != null){
						for(int iGroupNodeCnt=0;iGroupNodeCnt<alUserGroup.size();iGroupNodeCnt++)
						{
							strPolicyGrpID=String.valueOf(((GroupVO)alUserGroup.get(iGroupNodeCnt)).getGroupSeqID());
							if(strGroupID.equals(strPolicyGrpID))
							{
								bSuppress = false;
								break;
							}//if(policyVO.getGroupID().equals(((GroupVO)alUserGroup.get(iGroupNodeCnt)).getGroupSeqID()))
						}//end of for(int iGroupNodeCnt=0;iGroupNodeCnt<alGroupNodes.size();iGroupNodeCnt++)
					}//end of if(alUserGroup != null)

					if(!strGroupID.equals("") && bSuppress)
						preAuthVO.setSuppressLink(strSuppressLink);
					alResultList.add(preAuthVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getPreAuthList()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getPreAuthList()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getPreAuthList()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPreAuthList(ArrayList alSearchCriteria)

	/**
	 * This method returns the PreAuthDetailVO, which contains all the Pre-Authorization details
	 * @param preauthVO Object which contains seq id's to get the Pre-Authorization Details
	 * @param strSelectionType String object which contains Selection Type - 'PAT' or 'ICO'
	 * @return PreAuthDetailVO object which contains all the Pre-Authorization details
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO getPreAuthDetail(PreAuthVO preauthVO,String strSelectionType) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthDetailVO preAuthDetailVO = null;
		ClaimantVO claimantVO = null;
		AdditionalDetailVO additionalDetailVO = null;
		PreAuthHospitalVO preAuthHospitalVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthorization);

			if(preauthVO.getMemberSeqID() != null){
				cStmtObject.setLong(1,preauthVO.getMemberSeqID());
			}//end of if(preauthVO.getMemberSeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else

			if(preauthVO.getPreAuthSeqID() != null){
				cStmtObject.setLong(2,preauthVO.getPreAuthSeqID());
			}//end of if(preauthVO.getPreAuthSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(preauthVO.getEnrollDtlSeqID() != null){
				cStmtObject.setLong(3,preauthVO.getEnrollDtlSeqID());
			}//end of if(preauthVO.getEnrollDtlSeqID() != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			cStmtObject.setLong(4,preauthVO.getUpdatedBy());
			cStmtObject.setString(5,strSelectionType);
			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(6);
			if(rs != null){
				while(rs.next()){
					preAuthDetailVO = new PreAuthDetailVO();
					claimantVO = new ClaimantVO();
					additionalDetailVO = new AdditionalDetailVO();
					preAuthHospitalVO = new PreAuthHospitalVO();

					if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
						preAuthDetailVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)

					if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						preAuthDetailVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

					preAuthDetailVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
					preAuthDetailVO.setPreAuthTypeID(TTKCommon.checkNull(rs.getString("PAT_GENERAL_TYPE_ID")));

					if(rs.getString("PAT_RECEIVED_DATE") != null){
						preAuthDetailVO.setReceivedDate(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime()));
						preAuthDetailVO.setReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[1]:"");
						preAuthDetailVO.setReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)

					if(rs.getString("DATE_OF_HOSPITALIZATION") != null){
						preAuthDetailVO.setAdmissionDate(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime()));
						preAuthDetailVO.setAdmissionTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ")[1]:"");
						preAuthDetailVO.setAdmissionDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("DATE_OF_HOSPITALIZATION") != null)

					if(rs.getString("PREV_APPROVED_AMOUNT") != null){
						preAuthDetailVO.setPrevApprovedAmount(new BigDecimal(rs.getString("PREV_APPROVED_AMOUNT")));
					}//end of if(rs.getString("PREV_APPROVED_AMOUNT") != null)

					/*if(rs.getString("PAT_REQUESTED_AMOUNT") != null){
						preAuthDetailVO.setRequestAmount(new BigDecimal(rs.getString("PAT_REQUESTED_AMOUNT")));
					}//end of if(rs.getString("PAT_REQUESTED_AMOUNT") != null)
*/
					preAuthDetailVO.setDoctorName(TTKCommon.checkNull(rs.getString("TREATING_DR_NAME")));
					preAuthDetailVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("PAT_STATUS_GENERAL_TYPE_ID")));
					preAuthDetailVO.setPriorityTypeID(TTKCommon.checkNull(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID")));

					if(rs.getString("TPA_OFFICE_SEQ_ID") != null){
						preAuthDetailVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					}//end of if(rs.getString("TPA_OFFICE_SEQ_ID") != null)

					preAuthDetailVO.setPreAuthRecvTypeID(TTKCommon.checkNull(rs.getString("PAT_RCVD_THRU_GENERAL_TYPE_ID")));
					preAuthDetailVO.setHospitalPhone(TTKCommon.checkNull(rs.getString("PHONE_NO_IN_HOSPITALISATION")));
					preAuthDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					preAuthDetailVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					preAuthDetailVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					preAuthDetailVO.setPreAuthEnhancedYN(TTKCommon.checkNull(rs.getString("PAT_ENHANCED_YN")));

					if(rs.getString("PARENT_GEN_DETAIL_SEQ_ID") != null){
						preAuthDetailVO.setParentGenDtlSeqID(new Long(rs.getLong("PARENT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PARENT_GEN_DETAIL_SEQ_ID") != null)

					if(rs.getString("EVENT_SEQ_ID") != null){
						preAuthDetailVO.setEventSeqID(new Long(rs.getLong("EVENT_SEQ_ID")));
					}//end of if(rs.getString("EVENT_SEQ_ID") != null)

					if(rs.getString("REQUIRED_REVIEW_COUNT") != null){
						preAuthDetailVO.setRequiredReviewCnt(new Integer(rs.getInt("REQUIRED_REVIEW_COUNT")));
					}//end of if(rs.getString("REQUIRED_REVIEW_COUNT") != null)

					if(rs.getString("REVIEW_COUNT") != null){
						preAuthDetailVO.setReviewCount(new Integer(rs.getInt("REVIEW_COUNT")));
					}//end of if(rs.getString("REVIEW_COUNT") != null)

					preAuthDetailVO.setReview(TTKCommon.checkNull(rs.getString("REVIEW")));
					preAuthDetailVO.setEventName(TTKCommon.checkNull(rs.getString("EVENT_NAME")));
					preAuthDetailVO.setCompletedYN(TTKCommon.checkNull(rs.getString("COMPLETED_YN")));
					//preAuthDetailVO.setProcessCompletedYN(TTKCommon.checkNull(rs.getString("PROCESS_COMPLETED_YN")));
					preAuthDetailVO.setAdminAuthTypeID(TTKCommon.checkNull(rs.getString("ADMIN_AUTH_GENERAL_TYPE_ID")));
					preAuthDetailVO.setDMSRefID(TTKCommon.checkNull(rs.getString("PRE_AUTH_DMS_REFERENCE_ID")));
					preAuthDetailVO.setDiscPresentYN(TTKCommon.checkNull(rs.getString("DISCREPANCY_PRESENT_YN")));
					preAuthDetailVO.setEnrolChangeMsg(TTKCommon.checkNull(rs.getString("ENROLLMENT_CHANGE_MSG")));
					preAuthDetailVO.setShowReAssignIDYN(TTKCommon.checkNull(rs.getString("SHOW_REASSIGN_ID_YN")));

					if(rs.getString("MEMBER_SEQ_ID") != null){
						claimantVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
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

					claimantVO.setCategoryTypeID(TTKCommon.checkNull(rs.getString("CATEGORY_GENERAL_TYPE_ID")));
					claimantVO.setCategoryDesc(TTKCommon.checkNull(rs.getString("CATEGORY")));
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

					if(rs.getString("PRODUCT_SEQ_ID") != null){
						claimantVO.setProductSeqID(new Long(rs.getLong("PRODUCT_SEQ_ID")));
					}//end of if(rs.getString("PRODUCT_SEQ_ID") != null)

					claimantVO.setPolicySubTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
					claimantVO.setPolicyHolderNameDisc(TTKCommon.checkNull(rs.getString("DIS_POLICY_HOLDER_YN")));
					claimantVO.setTermStatusID(TTKCommon.checkNull(rs.getString("INS_STATUS_GENERAL_TYPE_ID")));
					claimantVO.setPhone(TTKCommon.checkNull(rs.getString("PHONE")));
					claimantVO.setClaimantPhoneNbr(TTKCommon.checkNull(rs.getString("PHONE")));
					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						claimantVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));

					claimantVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					//changes on dec 7th & 8th  in PreauthIMPL.java 

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
					/*else if(rs.getString("VIP_YN").equalsIgnoreCase("N") && rs.getString("VIP_YN").equalsIgnoreCase("") )
						{
							claimantVO.setVipYN("N");
						}*/


					//changes on dec 7th in PreauthIMPL.java 

					if(rs.getString("INS_SEQ_ID") != null){
						claimantVO.setInsSeqID(new Long(rs.getLong("INS_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					claimantVO.setCompanyCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					claimantVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					claimantVO.setInsCustCode(TTKCommon.checkNull(rs.getString("INS_CUSTOMER_CODE")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs.getString("INS_SCHEME")));

					if(rs.getString("ADDITIONAL_DTL_SEQ_ID") != null){
						additionalDetailVO.setAdditionalDtlSeqID(new Long(rs.getLong("ADDITIONAL_DTL_SEQ_ID")));
					}//end of if(rs.getString("ADDITIONAL_DTL_SEQ_ID") != null)

					additionalDetailVO.setRelationshipTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
					additionalDetailVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
					additionalDetailVO.setEmployeeName(TTKCommon.checkNull(rs.getString("EMPLOYEE_NAME")));

					if(rs.getString("DATE_OF_JOINING") != null){
						additionalDetailVO.setJoiningDate(new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
					}//end of if(rs.getString("DATE_OF_JOINING") != null)

					if(rs.getString("INFO_RECEIVED_DATE") != null){
						additionalDetailVO.setAddReceivedDate(new Date(rs.getTimestamp("INFO_RECEIVED_DATE").getTime()));
						additionalDetailVO.setAddReceivedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INFO_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INFO_RECEIVED_DATE").getTime())).split(" ")[1]:"");
						additionalDetailVO.setAddReceivedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INFO_RECEIVED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INFO_RECEIVED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("INFO_RECEIVED_DATE") != null)

					additionalDetailVO.setSourceTypeID(TTKCommon.checkNull(rs.getString("COMM_TYPE_ID")));
					additionalDetailVO.setReceivedFromType(TTKCommon.checkNull(rs.getString("INFO_RCVD_GENERAL_TYPE_ID")));
					additionalDetailVO.setReferenceNbr(TTKCommon.checkNull(rs.getString("REFERENCE_NO")));
					additionalDetailVO.setContactPerson(TTKCommon.checkNull(rs.getString("CONTACT_PERSON")));
					additionalDetailVO.setAdditionalRemarks(TTKCommon.checkNull(rs.getString("ADDITIONAL_REMARKS")));
					additionalDetailVO.setCertificateNo(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
					additionalDetailVO.setInsScheme(TTKCommon.checkNull(rs.getString("INS_SCHEME")));

					if(rs.getString("HOSP_SEQ_ID") != null){
						preAuthHospitalVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
					}//end of if(rs.getString("HOSP_SEQ_ID") != null)

					preAuthHospitalVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					preAuthHospitalVO.setEmplNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));

					if(rs.getString("FROM_DATE") != null){
						preAuthHospitalVO.setStartDate(new Date(rs.getTimestamp("FROM_DATE").getTime()));
					}//end of if(rs.getString("FROM_DATE") != null)

					if(rs.getString("TO_DATE") != null){
						preAuthHospitalVO.setEndDate(new Date(rs.getTimestamp("TO_DATE").getTime()));
					}//end of if(rs.getString("TO_DATE") != null)

					preAuthHospitalVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
					preAuthHospitalVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
					preAuthHospitalVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
					preAuthHospitalVO.setPincode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
					preAuthHospitalVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY_DESCRIPTION")));
					preAuthHospitalVO.setStateName(TTKCommon.checkNull(rs.getString("STATE_NAME")));
					preAuthHospitalVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					preAuthHospitalVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
					preAuthHospitalVO.setFaxNbr(TTKCommon.checkNull(rs.getString("OFFICE_FAX_NO")));
					preAuthHospitalVO.setEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
					preAuthHospitalVO.setHospRemarks(TTKCommon.checkNull(rs.getString("HOSP_REMARKS")));
					preAuthHospitalVO.setHospStatus(TTKCommon.checkNull(rs.getString("HOSP_STATUS")));
					preAuthHospitalVO.setRating(TTKCommon.checkNull(rs.getString("RATING")));
					preAuthHospitalVO.setEmpanelStatusTypeID(TTKCommon.checkNull(rs.getString("EMPANEL_TYPE_ID")));
					preAuthHospitalVO.setHospServiceTaxRegnNbr(TTKCommon.checkNull(rs.getString("SERV_TAX_RGN_NUMBER")));
					if(preAuthHospitalVO.getRating() != null){
						if(preAuthHospitalVO.getRating().equals("G")){
							preAuthHospitalVO.setRatingImageName("GoldStar");
							preAuthHospitalVO.setRatingImageTitle("Gold Star");
						}//end of if(preAuthHospitalVO.getRating().equals("G"))

						if(preAuthHospitalVO.getRating().equals("R")){
							preAuthHospitalVO.setRatingImageName("BlueStar");
							preAuthHospitalVO.setRatingImageTitle("Blue Star (Regular)");
						}//end of if(preAuthHospitalVO.getRating().equals("R"))

						if(preAuthHospitalVO.getRating().equals("B")){
							preAuthHospitalVO.setRatingImageName("BlackStar");
							preAuthHospitalVO.setRatingImageTitle("Black Star");
						}//end of if(preAuthHospitalVO.getRating().equals("B"))
					}//end of if(preAuthHospitalVO.getRating() != null)

					preAuthDetailVO.setShowBandYN(TTKCommon.checkNull(rs.getString("SHOW_BAND_YN")));

					preAuthDetailVO.setShowCodingOverrideYN(TTKCommon.checkNull(rs.getString("SHOW_CODING_OVERRIDE")));

					preAuthDetailVO.setClaimantVO(claimantVO);
					preAuthDetailVO.setAdditionalDetailVO(additionalDetailVO);
					preAuthDetailVO.setPreAuthHospitalVO(preAuthHospitalVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return preAuthDetailVO;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getPreAuthDetail()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPreAuthDetail(long lngPreAuthSeqID,long lngUserSeqID,String strSelectionType)

	/**
	 * This method saves the Pre-Authorization information
	 * @param preAuthDetailVO PreAuthDetailVO contains Pre-Authorization information
	 * @param strSelectionType String object which contains Selection Type - 'PAT' or 'ICO'
	 * @return Object[] the values,of  MEMBER_SEQ_ID , PAT_GEN_DETAIL_SEQ_ID and PAT_ENROLL_DETAIL_SEQ_ID
	 * @exception throws TTKException
	 */
	public Object[] savePreAuth(PreAuthDetailVO preAuthDetailVO,String strSelectionType) throws TTKException {
		Object[] objArrayResult = new Object[3];
		long lngMemberSeqID = 0;
		long lngPreAuthSeqID = 0;
		long lngPATEnrollDtlSeqID = 0;
		AdditionalDetailVO additionalDetailVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			additionalDetailVO = preAuthDetailVO.getAdditionalDetailVO();
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePreAuthorization);

			if(preAuthDetailVO.getEnrollDtlSeqID() != null){
				cStmtObject.setLong(PAT_ENROLL_DETAIL_SEQ_ID,preAuthDetailVO.getEnrollDtlSeqID());
			}//end of if(preAuthDetailVO.getEnrollDtlSeqID() != null)
			else{
				cStmtObject.setLong(PAT_ENROLL_DETAIL_SEQ_ID,0);
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getMemberSeqID() != null){
				cStmtObject.setLong(MEMBER_SEQ_ID,preAuthDetailVO.getClaimantVO().getMemberSeqID());
			}//end of if(preAuthDetailVO.getClaimantVO().getMemberSeqID() != null)
			else{
				cStmtObject.setLong(MEMBER_SEQ_ID,0);
			}//end of else

			cStmtObject.setString(TPA_ENROLLMENT_ID,preAuthDetailVO.getClaimantVO().getEnrollmentID());
			cStmtObject.setString(POLICY_NUMBER,preAuthDetailVO.getClaimantVO().getPolicyNbr());

			if(preAuthDetailVO.getClaimantVO().getPolicySeqID() != null){
				cStmtObject.setLong(POLICY_SEQ_ID,preAuthDetailVO.getClaimantVO().getPolicySeqID());
			}//end of if(preAuthDetailVO.getClaimantVO().getPolicySeqID() != null)
			else{
				cStmtObject.setString(POLICY_SEQ_ID,null);
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getInsSeqID() != null){
				cStmtObject.setLong(INS_SEQ_ID,preAuthDetailVO.getClaimantVO().getInsSeqID());
			}//end of if(preAuthDetailVO.getClaimantVO().getInsSeqID() != null)
			else{
				cStmtObject.setString(INS_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(GENDER_GENERAL_TYPE_ID,preAuthDetailVO.getClaimantVO().getGenderTypeID());
			cStmtObject.setString(CLAIMANT_NAME,preAuthDetailVO.getClaimantVO().getName());

			if(preAuthDetailVO.getClaimantVO().getAge() != null){
				cStmtObject.setInt(MEM_AGE,preAuthDetailVO.getClaimantVO().getAge());
			}//end of if(preAuthDetailVO.getClaimantVO().getAge() != null)
			else{
				cStmtObject.setString(MEM_AGE,null);
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getDateOfInception() != null){
				cStmtObject.setTimestamp(DATE_OF_INCEPTION,new Timestamp(preAuthDetailVO.getClaimantVO().getDateOfInception().getTime()));
			}//end of if(preAuthDetailVO.getClaimantVO().getDateOfInception() != null)
			else{
				cStmtObject.setTimestamp(DATE_OF_INCEPTION, null);
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getDateOfExit() != null){
				cStmtObject.setTimestamp(DATE_OF_EXIT,new Timestamp(preAuthDetailVO.getClaimantVO().getDateOfExit().getTime()));
			}//end of if(preAuthDetailVO.getClaimantVO().getDateOfExit() != null)
			else{
				cStmtObject.setTimestamp(DATE_OF_EXIT, null);
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getTotalSumInsured() != null){
				cStmtObject.setBigDecimal(MEM_TOTAL_SUM_INSURED,preAuthDetailVO.getClaimantVO().getTotalSumInsured());
			}//end of if(preAuthDetailVO.getClaimantVO().getTotalSumInsured() != null)
			else{
				cStmtObject.setString(MEM_TOTAL_SUM_INSURED,null);
			}//end of else

			cStmtObject.setString(CATEGORY_GENERAL_TYPE_ID,preAuthDetailVO.getClaimantVO().getCategoryTypeID());
			cStmtObject.setString(INSURED_NAME,preAuthDetailVO.getClaimantVO().getPolicyHolderName());
			cStmtObject.setString(PHONE_1,preAuthDetailVO.getClaimantVO().getPhone());

			if(preAuthDetailVO.getClaimantVO().getStartDate() != null){
				cStmtObject.setTimestamp(POLICY_EFFECTIVE_FROM,new Timestamp(preAuthDetailVO.getClaimantVO().getStartDate().getTime()));
			}//end of if(preAuthDetailVO.getClaimantVO().getStartDate() != null)
			else{
				cStmtObject.setTimestamp(POLICY_EFFECTIVE_FROM, null);
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getEndDate() != null){
				cStmtObject.setTimestamp(POLICY_EFFECTIVE_TO,new Timestamp(preAuthDetailVO.getClaimantVO().getEndDate().getTime()));
			}//end of if(preAuthDetailVO.getClaimantVO().getEndDate() != null)
			else{
				cStmtObject.setTimestamp(POLICY_EFFECTIVE_TO, null);
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getProductSeqID() != null){
				cStmtObject.setLong(PRODUCT_SEQ_ID,preAuthDetailVO.getClaimantVO().getProductSeqID());
			}//end of if(preAuthDetailVO.getClaimantVO().getProductSeqID() != null)
			else{
				cStmtObject.setString(PRODUCT_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(INS_STATUS_GENERAL_TYPE_ID,preAuthDetailVO.getClaimantVO().getTermStatusID());
			cStmtObject.setString(ENROL_TYPE_ID,preAuthDetailVO.getClaimantVO().getPolicyTypeID());
			cStmtObject.setString(POLICY_SUB_GENERAL_TYPE_ID,preAuthDetailVO.getClaimantVO().getPolicySubTypeID());

			if(preAuthDetailVO.getClaimantVO().getGroupRegnSeqID() != null){
				cStmtObject.setLong(GROUP_REG_SEQ_ID,preAuthDetailVO.getClaimantVO().getGroupRegnSeqID());
			}//end of if(preAuthDetailVO.getClaimantVO().getGroupRegSeqID() != null)
			else{
				cStmtObject.setString(GROUP_REG_SEQ_ID,null);
			}//end of else

			if(preAuthDetailVO.getAdmissionDate() != null){

				cStmtObject.setTimestamp(DATE_OF_HOSPITALIZATION,new Timestamp(TTKCommon.getOracleDateWithTime(preAuthDetailVO.getHospitalAdmissionDate(),preAuthDetailVO.getAdmissionTime(),preAuthDetailVO.getAdmissionDay()).getTime()));

			}//end of if(preAuthDetailVO.getAdmissionDate() != null)
			else{
				cStmtObject.setTimestamp(DATE_OF_HOSPITALIZATION, null);
			}//end of else

			if(preAuthDetailVO.getOfficeSeqID() != null){
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,preAuthDetailVO.getOfficeSeqID());
			}//end of if(preAuthDetailVO.getOfficeSeqID() != null)
			else{
				cStmtObject.setString(TPA_OFFICE_SEQ_ID,null);
			}//end of else

			if(preAuthDetailVO.getPreAuthHospitalVO().getHospSeqId() != null){
				cStmtObject.setLong(HOSP_SEQ_ID,preAuthDetailVO.getPreAuthHospitalVO().getHospSeqId());
			}//end of if(preAuthDetailVO.getPreAuthHospitalVO().getHospSeqId() != null)
			else{
				cStmtObject.setString(HOSP_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(PAT_STATUS_GENERAL_TYPE_ID,preAuthDetailVO.getStatusTypeID());

			if(preAuthDetailVO.getPreAuthSeqID()!=null)
			{
				cStmtObject.setLong(PAT_GEN_DETAIL_SEQ_ID,preAuthDetailVO.getPreAuthSeqID());
			}else
			{
				cStmtObject.setLong(PAT_GEN_DETAIL_SEQ_ID,0);
			}

			cStmtObject.setString(PAT_GENERAL_TYPE_ID,preAuthDetailVO.getPreAuthTypeID());
			cStmtObject.setString(PAT_PRIORITY_GENERAL_TYPE_ID,preAuthDetailVO.getPriorityTypeID());

			if(preAuthDetailVO.getReceivedDate() != null){
				cStmtObject.setTimestamp(PAT_RECEIVED_DATE,new Timestamp(TTKCommon.getOracleDateWithTime(preAuthDetailVO.getPATReceivedDate(),preAuthDetailVO.getReceivedTime(),preAuthDetailVO.getReceivedDay()).getTime()));
			}//end of if(preAuthDetailVO.getReceivedDate() != null)
			else{
				cStmtObject.setTimestamp(PAT_RECEIVED_DATE, null);
			}//end of else

			if(preAuthDetailVO.getPrevApprovedAmount() != null){
				cStmtObject.setBigDecimal(PREV_APPROVED_AMOUNT,preAuthDetailVO.getPrevApprovedAmount());
			}//end of if(preAuthDetailVO.getPrevApprovedAmount() != null)
			else{
				cStmtObject.setString(PREV_APPROVED_AMOUNT,null);
			}//end of else

			/*if(preAuthDetailVO.getRequestAmount() != null){
				cStmtObject.setBigDecimal(PAT_REQUESTED_AMOUNT,preAuthDetailVO.getRequestAmount());
			}//end of if(preAuthDetailVO.getRequestAmount() != null)
			else{
				cStmtObject.setString(PAT_REQUESTED_AMOUNT,null);
			}//end of else
*/
			cStmtObject.setString(TREATING_DR_NAME,preAuthDetailVO.getDoctorName());
			cStmtObject.setString(PAT_RCVD_THRU_GENERAL_TYPE_ID,preAuthDetailVO.getPreAuthRecvTypeID());
			cStmtObject.setString(PHONE_NO_IN_HOSPITALISATION,preAuthDetailVO.getHospitalPhone());
			cStmtObject.setString(DISCREPANCY_PRESENT_YN,preAuthDetailVO.getDiscPresentYN());

			if(strSelectionType.equals("PAT")){
				if(preAuthDetailVO.getParentGenDtlSeqID() != null){
					cStmtObject.setLong(PARENT_GEN_DETAIL_SEQ_ID,preAuthDetailVO.getParentGenDtlSeqID());
				}//end of if(preAuthDetailVO.getParentGenDtlSeqID() != null)
				else{
					cStmtObject.setString(PARENT_GEN_DETAIL_SEQ_ID,null);
				}//end of else
			}//end of if(strSelectionType.equals("PAT"))

			if(strSelectionType.equals("ICO")){
				if(preAuthDetailVO.getPreAuthSeqID() != null){
					cStmtObject.setLong(PARENT_GEN_DETAIL_SEQ_ID,preAuthDetailVO.getPreAuthSeqID());
				}//end of if(preAuthDetailVO.getPreAuthSeqID() != null)
				else{
					cStmtObject.setString(PARENT_GEN_DETAIL_SEQ_ID,null);
				}//end of else
			}//end of if(strSelectionType.equals("ICO"))

			cStmtObject.setString(REMARKS,preAuthDetailVO.getRemarks());

			if(preAuthDetailVO.getClaimantVO().getAvailSumInsured() != null){
				cStmtObject.setBigDecimal(AVA_SUM_INSURED,preAuthDetailVO.getClaimantVO().getAvailSumInsured());
			}//end of if(preAuthDetailVO.getClaimantVO().getAvailSumInsured() != null)
			else{
				cStmtObject.setString(AVA_SUM_INSURED,null);
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getCumulativeBonus() != null){
				cStmtObject.setBigDecimal(AVA_CUM_BONUS,preAuthDetailVO.getClaimantVO().getCumulativeBonus());
			}//end of if(preAuthDetailVO.getClaimantVO().getCumulativeBonus() != null)
			else{
				cStmtObject.setString(AVA_CUM_BONUS,null);
			}//end of else

			if(additionalDetailVO != null){

				log.debug("Inside Additional Details not null");
				if(preAuthDetailVO.getAdditionalDetailVO().getAdditionalDtlSeqID() != null){
					cStmtObject.setLong(ADDITIONAL_DTL_SEQ_ID,preAuthDetailVO.getAdditionalDetailVO().getAdditionalDtlSeqID());
				}//end of if(preAuthDetailVO.getAdditionalDetailVO().getAdditionalDtlSeqID() != null)
				else{
					cStmtObject.setString(ADDITIONAL_DTL_SEQ_ID,null);
				}//end of else

				cStmtObject.setString(RELSHIP_TYPE_ID,preAuthDetailVO.getAdditionalDetailVO().getRelationshipTypeID());
				cStmtObject.setString(EMPLOYEE_NO,preAuthDetailVO.getAdditionalDetailVO().getEmployeeNbr());
				cStmtObject.setString(EMPLOYEE_NAME,preAuthDetailVO.getAdditionalDetailVO().getEmployeeName());

				if(preAuthDetailVO.getAdditionalDetailVO().getJoiningDate() != null){
					cStmtObject.setTimestamp(DATE_OF_JOINING,new Timestamp(preAuthDetailVO.getAdditionalDetailVO().getJoiningDate().getTime()));
				}//end of if(preAuthDetailVO.getAdditionalDetailVO().getJoiningDate() != null)
				else{
					cStmtObject.setTimestamp(DATE_OF_JOINING, null);
				}//end of else

				if(preAuthDetailVO.getAdditionalDetailVO().getAddReceivedDate() != null){
					cStmtObject.setTimestamp(INFO_RECEIVED_DATE,new Timestamp(TTKCommon.getOracleDateWithTime(preAuthDetailVO.getAdditionalDetailVO().getInfoReceivedDate(),preAuthDetailVO.getAdditionalDetailVO().getAddReceivedTime(),preAuthDetailVO.getAdditionalDetailVO().getAddReceivedDay()).getTime()));
				}//end of if(preAuthDetailVO.getAdditionalDetailVO().getAddReceivedDate() != null)
				else{
					cStmtObject.setTimestamp(INFO_RECEIVED_DATE, null);
				}//end of else

				cStmtObject.setString(COMM_TYPE_ID,preAuthDetailVO.getAdditionalDetailVO().getSourceTypeID());
				cStmtObject.setString(INFO_RCVD_GENERAL_TYPE_ID,preAuthDetailVO.getAdditionalDetailVO().getReceivedFromType());
				cStmtObject.setString(REFERENCE_NO,preAuthDetailVO.getAdditionalDetailVO().getReferenceNbr());
				cStmtObject.setString(CONTACT_PERSON,preAuthDetailVO.getAdditionalDetailVO().getContactPerson());
				cStmtObject.setString(ADDITIONAL_REMARKS,preAuthDetailVO.getAdditionalDetailVO().getAdditionalRemarks());

				if(preAuthDetailVO.getPreAuthTypeID().equalsIgnoreCase("MAN")){
					if(preAuthDetailVO.getClaimantVO().getPolicyTypeID().equalsIgnoreCase("NCR")){
						cStmtObject.setString(INS_SCHEME,preAuthDetailVO.getAdditionalDetailVO().getInsScheme());
						log.debug("Scheme name :"+preAuthDetailVO.getAdditionalDetailVO().getInsScheme());
						cStmtObject.setString(CERTIFICATE_NO,preAuthDetailVO.getAdditionalDetailVO().getCertificateNo());
						log.debug("CertificateNo :"+preAuthDetailVO.getAdditionalDetailVO().getCertificateNo());
					}//end of if(preAuthDetailVO.getClaimantVO().getPolicyTypeID().equalsIgnoreCase("NCR"))
					else{
						cStmtObject.setString(INS_SCHEME,null);
						cStmtObject.setString(CERTIFICATE_NO,null);
					}//end of else
				}//end of if(preAuthDetailVO.getPreAuthTypeID().equalsIgnoreCase("MAN"))
				else{
					if(preAuthDetailVO.getClaimantVO().getPolicyTypeID().equalsIgnoreCase("NCR")){
						cStmtObject.setString(INS_SCHEME,preAuthDetailVO.getClaimantVO().getInsScheme());
						log.debug("Scheme name :"+preAuthDetailVO.getClaimantVO().getInsScheme());
						cStmtObject.setString(CERTIFICATE_NO,preAuthDetailVO.getClaimantVO().getCertificateNo());
						log.debug("CertificateNo :"+preAuthDetailVO.getClaimantVO().getCertificateNo());
					}//end of if(preAuthDetailVO.getClaimantVO().getPolicyTypeID().equalsIgnoreCase("NCR"))
					else{
						cStmtObject.setString(INS_SCHEME,null);
						cStmtObject.setString(CERTIFICATE_NO,null);
					}//end of else
				}//end of else
			}//end of if(additionalDetailVO != null)
			else{

				log.debug("Inside Additional Details null");
				cStmtObject.setString(ADDITIONAL_DTL_SEQ_ID,null);
				cStmtObject.setString(RELSHIP_TYPE_ID,null);
				cStmtObject.setString(EMPLOYEE_NO,null);
				cStmtObject.setString(EMPLOYEE_NAME,null);
				cStmtObject.setTimestamp(DATE_OF_JOINING, null);
				cStmtObject.setTimestamp(INFO_RECEIVED_DATE, null);
				cStmtObject.setString(COMM_TYPE_ID,null);
				cStmtObject.setString(INFO_RCVD_GENERAL_TYPE_ID,null);
				cStmtObject.setString(REFERENCE_NO,null);
				cStmtObject.setString(CONTACT_PERSON,null);
				cStmtObject.setString(ADDITIONAL_REMARKS,null);
				cStmtObject.setString(INS_SCHEME,preAuthDetailVO.getClaimantVO().getInsScheme());
				log.debug("Scheme name :"+preAuthDetailVO.getClaimantVO().getInsScheme());
				cStmtObject.setString(CERTIFICATE_NO,preAuthDetailVO.getClaimantVO().getCertificateNo());
				log.debug("CertificateNo :"+preAuthDetailVO.getClaimantVO().getCertificateNo());
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getAvblBufferAmount() != null){
				cStmtObject.setBigDecimal(AVA_BUFFER_AMOUNT,preAuthDetailVO.getClaimantVO().getAvblBufferAmount());
			}//end of if(preAuthDetailVO.getClaimantVO().getAvblBufferAmount() != null)
			else{
				cStmtObject.setString(AVA_BUFFER_AMOUNT,null);
			}//end of else

			if(preAuthDetailVO.getClaimantVO().getBufferDtlSeqID() != null){
				cStmtObject.setLong(BUFF_DTL_SEQ_ID,preAuthDetailVO.getClaimantVO().getBufferDtlSeqID());
			}//end of if(preAuthDetailVO.getClaimantVO().getBufferDtlSeqID() != null)
			else{
				cStmtObject.setString(BUFF_DTL_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(BUFFER_ALLOWED_YN,preAuthDetailVO.getClaimantVO().getBufferAllowedYN());
			cStmtObject.setString(ADMIN_AUTH_GENERAL_TYPE_ID,preAuthDetailVO.getAdminAuthTypeID());
			cStmtObject.setString(COMPLETED_YN,preAuthDetailVO.getCompletedYN());
			cStmtObject.setString(PRE_AUTH_DMS_REFERENCE_ID,preAuthDetailVO.getDMSRefID());
			cStmtObject.setString(EMAIL_ID,preAuthDetailVO.getClaimantVO().getEmailID());

			cStmtObject.setString(INS_CUSTOMER_CODE,preAuthDetailVO.getClaimantVO().getInsCustCode());
			cStmtObject.setString(SELECTION_TYPE,strSelectionType);
			cStmtObject.setLong(USER_SEQ_ID,preAuthDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(MEMBER_SEQ_ID,Types.BIGINT);
			cStmtObject.registerOutParameter(PAT_GEN_DETAIL_SEQ_ID,Types.BIGINT);
			cStmtObject.registerOutParameter(PAT_ENROLL_DETAIL_SEQ_ID,Types.BIGINT);
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
			cStmtObject.execute();

			lngPreAuthSeqID = cStmtObject.getLong(PAT_GEN_DETAIL_SEQ_ID);
			lngMemberSeqID = cStmtObject.getLong(MEMBER_SEQ_ID);
			lngPATEnrollDtlSeqID = cStmtObject.getLong(PAT_ENROLL_DETAIL_SEQ_ID);
			objArrayResult[0] = new Long(lngMemberSeqID);
			objArrayResult[1] = new Long(lngPreAuthSeqID);
			objArrayResult[2] = new Long(lngPATEnrollDtlSeqID);
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
					log.error("Error while closing the Statement in PreAuthDAOImpl savePreAuth()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl savePreAuth()",sqlExp);
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
		return objArrayResult;
	}//end of savePreAuth(PreAuthDetailVO preAuthDetailVO,String strSelectionType)

	/**
	 * This method will allow to Override the Preauth information
	 * @param lngPATGenDetailSeqID PATGenDetailSeqID
	 * @param lngPATEnrollDtlSeqID PATEnrollDtlSeqID
	 * @param lngUserSeqID long value which contains Logged-in User
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO overridePreauth(long lngPATGenDetailSeqID,long lngPATEnrollDtlSeqID,long lngUserSeqID) throws TTKException {
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		PreAuthDetailVO preauthDetailVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strOverridePreauth);
			preauthDetailVO = new PreAuthDetailVO();
			cStmtObject.setLong(1,lngPATGenDetailSeqID);
			cStmtObject.setLong(2,lngPATEnrollDtlSeqID);
			cStmtObject.setLong(3,lngUserSeqID);
			cStmtObject.registerOutParameter(4,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(5,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(6,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(7,Types.VARCHAR);
			cStmtObject.registerOutParameter(8,Types.VARCHAR);
			cStmtObject.execute();

			lngEventSeqID = cStmtObject.getLong(4);
			intReviewCount = cStmtObject.getInt(5);
			intRequiredReviewCnt = cStmtObject.getInt(6);
			strEventName = cStmtObject.getString(7);
			strReview = cStmtObject.getString(8);

			preauthDetailVO.setEventSeqID(lngEventSeqID);
			preauthDetailVO.setReviewCount(intReviewCount);
			preauthDetailVO.setRequiredReviewCnt(intRequiredReviewCnt);
			preauthDetailVO.setEventName(strEventName);
			preauthDetailVO.setReview(strReview);
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
					log.error("Error while closing the Statement in PreAuthDAOImpl savePreAuth()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl savePreAuth()",sqlExp);
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
		return preauthDetailVO;
	}//end of overridePreauth(long lngPATGenDetailSeqID,long lngPATEnrollDtlSeqID,long lngUserSeqID)

	/**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @param strType String object which contains Type
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType) throws TTKException {

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

			if(preauthDetailVO.getPreAuthSeqID() != null){
				cStmtObject.setLong(1,preauthDetailVO.getPreAuthSeqID());
			}//end of if(preauthDetailVO.getPreAuthSeqID() != null)
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
					log.error("Error while closing the Statement in PreAuthDAOImpl saveReview()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl saveReview()",sqlExp);
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
		return preauthDetailVO;
	}//end of saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String strType)

	/**
	 * This method assigns the Pre-Authorization information to the corresponding Doctor
	 * @param preAuthAssignmentVO PreAuthAssignmentVO which contains Pre-Authorization information to assign the corresponding Doctor
	 * @return long value which contains Assign Users Seq ID
	 * @exception throws TTKException
	 */
	public long assignPreAuth(PreAuthAssignmentVO preAuthAssignmentVO) throws TTKException {
		long lngAssignUsersSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveAssignUsers);
			if(preAuthAssignmentVO.getAssignUserSeqID() != null){
				cStmtObject.setLong(1,preAuthAssignmentVO.getAssignUserSeqID());
			}//end of if(preAuthAssignmentVO.getAssignUserSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else

			if(preAuthAssignmentVO.getClaimSeqID() != null){
				cStmtObject.setLong(2,preAuthAssignmentVO.getClaimSeqID());
			}//end of if(preAuthAssignmentVO.getClaimSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(preAuthAssignmentVO.getPreAuthSeqID() != null){
				cStmtObject.setLong(3,preAuthAssignmentVO.getPreAuthSeqID());
			}//end of if(preAuthAssignmentVO.getPreAuthSeqID() != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			if(preAuthAssignmentVO.getOfficeSeqID() != null){
				cStmtObject.setLong(4,preAuthAssignmentVO.getOfficeSeqID());
			}//end of if(preAuthAssignmentVO.getOfficeSeqID() != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			if(preAuthAssignmentVO.getDoctor() != null){
				cStmtObject.setLong(5,preAuthAssignmentVO.getDoctor());
			}//end of if(preAuthAssignmentVO.getDoctor() != null)
			else{
				cStmtObject.setString(5,null);
			}//end of else

			cStmtObject.setString(6,null);
			cStmtObject.setString(7,preAuthAssignmentVO.getRemarks());
			cStmtObject.setString(8,"EXT");
			cStmtObject.setString(9,preAuthAssignmentVO.getSuperUserYN());
			cStmtObject.setLong(10,preAuthAssignmentVO.getUpdatedBy());
			cStmtObject.registerOutParameter(11,Types.INTEGER);
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.execute();
			lngAssignUsersSeqID = cStmtObject.getLong(1);//ASSIGN_USERS_SEQ_ID
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
					log.error("Error while closing the Statement in PreAuthDAOImpl assignPreAuth()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl assignPreAuth()",sqlExp);
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
		return lngAssignUsersSeqID;
	}//end of assignPreAuth(PreAuthAssignmentVO preAuthAssignmentVO)

	/**
	 * This method returns the PreAuthAssignmentVO  which contains Assignment details
	 * @param lngAssignUsersSeqID long value contains Assign User Seq ID
	 * @param lngUserSeqID long value contains Logged-in User Seq ID
	 * @param strMode contains PAT/CLM for identifying Pre-Authorization/Claims
	 * @return ArrayList of PreAuthAssignmentVO object which contains Assignment details
	 * @exception throws TTKException
	 */
	public PreAuthAssignmentVO getAssignTo(long lngAssignUsersSeqID,long lngUserSeqID,String strMode) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthAssignmentVO preauthAssignmentVO = null;
		ArrayList alAssignUserList = new ArrayList();
		ArrayList<Object> alUserList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetAssignTo);
			cStmtObject.setLong(1,lngAssignUsersSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.setString(3,strMode);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs=(java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				while(rs.next()){
					preauthAssignmentVO = new PreAuthAssignmentVO();

					if(rs.getString("ASSIGN_USERS_SEQ_ID") != null){
						preauthAssignmentVO.setAssignUserSeqID(new Long(rs.getLong("ASSIGN_USERS_SEQ_ID")));
					}//end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)

					if(rs.getString("SEQ_ID") != null){
						if(strMode.equals("PAT")){
							preauthAssignmentVO.setPreAuthSeqID(new Long(rs.getLong("SEQ_ID")));
						}//end of if(strMode.equals("PAT"))
						else{
							preauthAssignmentVO.setClaimSeqID(new Long(rs.getLong("SEQ_ID")));
						}//end of else
					}//end of if(rs.getString("SEQ_ID") != null)

					preauthAssignmentVO.setSelectedPreAuthNos(TTKCommon.checkNull(rs.getString("ID_NUMBER")));

					if(rs.getString("TPA_OFFICE_SEQ_ID") != null){
						preauthAssignmentVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					}//end of if(rs.getString("TPA_OFFICE_SEQ_ID") != null)

					if(rs.getString("ASSIGNED_TO_USER") != null){
						preauthAssignmentVO.setDoctor(new Long(rs.getLong("ASSIGNED_TO_USER")));
					}//end of if(rs.getString("ASSIGNED_TO_USER") != null)

					preauthAssignmentVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));

					if(rs.getString("POLICY_SEQ_ID") != null){
						preauthAssignmentVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					if(rs.getString("SEQ_ID") != null && rs.getString("TPA_OFFICE_SEQ_ID") != null ){
						alUserList.add(new Long(rs.getLong("SEQ_ID")));

						if(rs.getString("POLICY_SEQ_ID") != null){
							alUserList.add(new Long(rs.getLong("POLICY_SEQ_ID")));
						}//end of if(rs.getString("POLICY_SEQ_ID") != null)
						else{
							alUserList.add(null);
						}//end of else

						alUserList.add(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
						alAssignUserList = getAssignUserList(alUserList,strMode);
						preauthAssignmentVO.setAssignUserList(alAssignUserList);
					}//end of if

				}//end of while(rs.next())
			}//end of if(rs != null)
			return preauthAssignmentVO;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getAssignTo()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getAssignTo()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getAssignTo()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getAssignTo(long lngAssignUsersSeqID,long lngUserSeqID,String strMode)

	/**
	 * This method returns the ArrayList object, which contains all the Users for the Corresponding TTK Branch
	 * @param alAssignUserList ArrayList Object contains PreauthSeqID/ClaimSeqID,PolicySeqID and TTKBranch
	 * @param strMode contains PAT/CLM for identifying the Pre-Authorization/Claims flow
	 * @return ArrayList object which contains all the Users for the Corresponding TTK Branch
	 * @exception throws TTKException
	 */
	public ArrayList getAssignUserList(ArrayList alAssignUserList,String strMode) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ArrayList<Object> alUserList = new ArrayList<Object>();
		CacheObject cacheObject = null;
		try{
			conn = ResourceManager.getConnection();

			if(strMode.equals("PAT")){
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPAAssignUserList);
			}//end of if(strMode.equals("PAT"))
			else if(strMode.equals("CLM")){
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimAssignUserList);
			}//end of if(strMode.equals("CLM"))
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
			throw new TTKException(sqlExp, "preauth");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "preauth");
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
					log.error("Error while closing the Resultset in PreAuthDAOImpl getAssignUserList()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getAssignUserList()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getAssignUserList()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getAssignUserList(ArrayList alAssignUserList,String strMode)

	/**
	 * This method returns the Arraylist of ClaimantVO's  which contains Claimant details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ClaimantVO object which contains Claimant details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimantList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ClaimantVO claimantVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strEnrollmentList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(11));
			cStmtObject.setString(11,(String)alSearchCriteria.get(12));
			cStmtObject.setString(12,(String)alSearchCriteria.get(13));
			cStmtObject.setString(13,(String)alSearchCriteria.get(14));
			cStmtObject.setLong(14,(Long)alSearchCriteria.get(9));
			cStmtObject.setString(15,(String)alSearchCriteria.get(10));
			cStmtObject.registerOutParameter(16,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(16);
			if(rs != null){
				while(rs.next()){
					claimantVO = new ClaimantVO();

					if(rs.getString("MEMBER_SEQ_ID") != null){
						claimantVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
						claimantVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
					}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));

					if(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")).equals("COR")){
						claimantVO.setEmployeeName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
					}//end of if(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")).equals("COR"))

					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
					claimantVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
					claimantVO.setGenderDescription(TTKCommon.checkNull(rs.getString("GENDER")));

					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)

					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));

					if(rs.getString("INS_SEQ_ID") != null){
						claimantVO.setInsSeqID(new Long(rs.getLong("INS_SEQ_ID")));
					}//end of if(rs.getString("") != null)
					claimantVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					claimantVO.setCompanyCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));

					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						claimantVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					claimantVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));

					if(rs.getString("DATE_OF_INCEPTION") != null){
						claimantVO.setDateOfInception(new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()));
					}//end of if(rs.getString("DATE_OF_INCEPTION") != null)

					if(rs.getString("DATE_OF_EXIT") != null){
						claimantVO.setDateOfExit(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
					}//end of if(rs.getString("DATE_OF_EXIT") != null)

					if(rs.getString("MEM_TOT_SUM_INSURED") != null){
						claimantVO.setTotalSumInsured(new BigDecimal(rs.getString("MEM_TOT_SUM_INSURED")));
					}//end of if(rs.getString("MEM_TOT_SUM_INSURED") != null)

					if(rs.getString("AVAIL_SUM_INSURED") != null){
						claimantVO.setAvailSumInsured(new BigDecimal(rs.getString("AVAIL_SUM_INSURED")));
					}//end of if(rs.getString("AVAIL_SUM_INSURED") != null)

					claimantVO.setCategoryTypeID(TTKCommon.checkNull(rs.getString("CATEGORY_GENERAL_TYPE_ID")));
					claimantVO.setCategoryDesc(TTKCommon.checkNull(rs.getString("CATEGORY")));
					claimantVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));

					if(rs.getString("PRODUCT_SEQ_ID") != null){
						claimantVO.setProductSeqID(new Long(rs.getLong("PRODUCT_SEQ_ID")));
					}//end of if(rs.getString("PRODUCT_SEQ_ID") != null)

					claimantVO.setPolicySubTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
					claimantVO.setTermStatusID(TTKCommon.checkNull(rs.getString("INS_STATUS_GENERAL_TYPE_ID")));
					claimantVO.setPhone(TTKCommon.checkNull(rs.getString("PHONE")));

					if(rs.getString("EFFECTIVE_FROM_DATE") != null){
						claimantVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if(rs.getString("EFFECTIVE_TO_DATE") != null){
						claimantVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

					if(rs.getString("POLICY_SEQ_ID") != null){
						claimantVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					claimantVO.setBufferAllowedYN(TTKCommon.checkNull(rs.getString("BUFFER_ALLOWED_YN")));

					if(rs.getString("TOTAL_BUFFER_AMOUNT") != null){
						claimantVO.setTotBufferAmt(new BigDecimal(rs.getString("TOTAL_BUFFER_AMOUNT")));
					}//end of if(rs.getString("TOTAL_BUFFER_AMOUNT") != null)

					claimantVO.setSchemeCertNbr(TTKCommon.checkNull(rs.getString("SCHEME_OR_CERTFICATE")));

					if(rs.getString("INS_HEAD_OFFICE_SEQ_ID") != null){
						claimantVO.setInsHeadOffSeqID(new Long(rs.getLong("INS_HEAD_OFFICE_SEQ_ID")));
					}//end of if(rs.getString("INS_HEAD_OFFICE_SEQ_ID") != null)

					claimantVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
					claimantVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					claimantVO.setInsCustCode(TTKCommon.checkNull(rs.getString("INS_CUSTOMER_CODE")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs.getString("INS_SCHEME")));
					claimantVO.setCreditCardNbr(TTKCommon.checkNull(rs.getString("CREDITCARD_NO")));
					alResultList.add(claimantVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getClaimantList()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getClaimantList()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getClaimantList()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimantList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of ClaimantVO's  which contains Claimant details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of ClaimantVO object which contains Claimant details
	 * @exception throws TTKException
	 */
	public ArrayList getEnrollmentList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ClaimantNewVO claimantVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectMemberList);

			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			cStmtObject.setString(10,(String)alSearchCriteria.get(11));
			cStmtObject.setString(11,(String)alSearchCriteria.get(12));
			cStmtObject.setString(12,(String)alSearchCriteria.get(13));
			cStmtObject.setString(13,(String)alSearchCriteria.get(14));
			cStmtObject.setLong(14,(Long)alSearchCriteria.get(9));
			cStmtObject.setString(15,(String)alSearchCriteria.get(10));
			cStmtObject.registerOutParameter(16,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(16);
			if(rs != null){
				while(rs.next()){
					claimantVO = new ClaimantNewVO();

					if(rs.getString("MEMBER_SEQ_ID") != null){
						claimantVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));

					claimantVO.setGenderDescription(TTKCommon.checkNull(rs.getString("GENDER")));

					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)

					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));

					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						claimantVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));

					//					claimantVO.setInsScheme(TTKCommon.checkNull(rs.getString("SCHEME_OR_CERTFICATE")));
					claimantVO.setSchemeCertNbr(TTKCommon.checkNull(rs.getString("SCHEME_OR_CERTFICATE")));

					if(rs.getString("EFFECTIVE_FROM_DATE") != null){
						claimantVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if(rs.getString("EFFECTIVE_TO_DATE") != null){
						claimantVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

					alResultList.add(claimantVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getClaimantList()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getClaimantList()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getClaimantList()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimantList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of ClaimantVO's  which contains Claimant details
	 * @param lngMemSeqID String object which contains the search criteria
	 * @return ArrayList of ClaimantVO object which contains Claimant details
	 * @exception throws TTKException
	 */
	public ClaimantVO getSelectEnrollmentID(Long lngMemSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ClaimantVO claimantVO = null;
		try{
			conn = ResourceManager.getConnection();
			log.debug("lngMemSeqID is :"+lngMemSeqID);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectEnrollmentID);
			cStmtObject.setLong(1,lngMemSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			claimantVO = new ClaimantVO();
			if(rs != null){
				while(rs.next()){

					if(rs.getString("MEMBER_SEQ_ID") != null){
						claimantVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
						claimantVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
					}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));

					if(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")).equals("COR")){
						claimantVO.setEmployeeName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
					}//end of if(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")).equals("COR"))

					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
					claimantVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
					claimantVO.setGenderDescription(TTKCommon.checkNull(rs.getString("GENDER")));

					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)

					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));

					if(rs.getString("INS_SEQ_ID") != null){
						claimantVO.setInsSeqID(new Long(rs.getLong("INS_SEQ_ID")));
					}//end of if(rs.getString("") != null)
					claimantVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					claimantVO.setCompanyCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));

					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						claimantVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					claimantVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));

					//changes on 9th Dec for KOC1136
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
					//changes on 7th Dec for KOC1136
					//claimantVO.setVipYN(TTKCommon.checkNull(rs.getString("vipYN")));
					//claimantVO.setVipYN("Y");
					//changes on 7th Dec


					if(rs.getString("DATE_OF_INCEPTION") != null){
						claimantVO.setDateOfInception(new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()));
					}//end of if(rs.getString("DATE_OF_INCEPTION") != null)

					if(rs.getString("DATE_OF_EXIT") != null){
						claimantVO.setDateOfExit(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
					}//end of if(rs.getString("DATE_OF_EXIT") != null)

					if(rs.getString("MEM_TOT_SUM_INSURED") != null){
						claimantVO.setTotalSumInsured(new BigDecimal(rs.getString("MEM_TOT_SUM_INSURED")));
					}//end of if(rs.getString("MEM_TOT_SUM_INSURED") != null)

					if(rs.getString("AVAIL_SUM_INSURED") != null){
						claimantVO.setAvailSumInsured(new BigDecimal(rs.getString("AVAIL_SUM_INSURED")));
					}//end of if(rs.getString("AVAIL_SUM_INSURED") != null)

					claimantVO.setCategoryTypeID(TTKCommon.checkNull(rs.getString("CATEGORY_GENERAL_TYPE_ID")));
					claimantVO.setCategoryDesc(TTKCommon.checkNull(rs.getString("CATEGORY")));
					claimantVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));

					if(rs.getString("PRODUCT_SEQ_ID") != null){
						claimantVO.setProductSeqID(new Long(rs.getLong("PRODUCT_SEQ_ID")));
					}//end of if(rs.getString("PRODUCT_SEQ_ID") != null)

					claimantVO.setPolicySubTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
					claimantVO.setTermStatusID(TTKCommon.checkNull(rs.getString("INS_STATUS_GENERAL_TYPE_ID")));
					claimantVO.setPhone(TTKCommon.checkNull(rs.getString("PHONE")));

					if(rs.getString("EFFECTIVE_FROM_DATE") != null){
						claimantVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if(rs.getString("EFFECTIVE_TO_DATE") != null){
						claimantVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

					if(rs.getString("POLICY_SEQ_ID") != null){
						claimantVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					claimantVO.setBufferAllowedYN(TTKCommon.checkNull(rs.getString("BUFFER_ALLOWED_YN")));

					if(rs.getString("TOTAL_BUFFER_AMOUNT") != null){
						claimantVO.setTotBufferAmt(new BigDecimal(rs.getString("TOTAL_BUFFER_AMOUNT")));
					}//end of if(rs.getString("TOTAL_BUFFER_AMOUNT") != null)

					claimantVO.setSchemeCertNbr(TTKCommon.checkNull(rs.getString("SCHEME_OR_CERTFICATE")));

					if(rs.getString("INS_HEAD_OFFICE_SEQ_ID") != null){
						claimantVO.setInsHeadOffSeqID(new Long(rs.getLong("INS_HEAD_OFFICE_SEQ_ID")));
					}//end of if(rs.getString("INS_HEAD_OFFICE_SEQ_ID") != null)

					claimantVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
					claimantVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					claimantVO.setInsCustCode(TTKCommon.checkNull(rs.getString("INS_CUSTOMER_CODE")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs.getString("INS_SCHEME")));
					claimantVO.setCreditCardNbr(TTKCommon.checkNull(rs.getString("CREDITCARD_NO")));
					//					alResultList.add(claimantVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			//			return (ArrayList)alResultList;
			return claimantVO;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getClaimantList()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getClaimantList()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getClaimantList()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimantList(ArrayList alSearchCriteria)


	/**
	 * This method returns the Arraylist of EnhancementVO's  which contains SumInsured Enhancement details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of EnhancementVO object which contains SumInsured Enhancement details
	 * @exception throws TTKException
	 */
	public ArrayList getSumInsuredEnhancementList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		EnhancementVO enhancementVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strEnhancementList);
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(4));
			cStmtObject.setString(3,(String)alSearchCriteria.get(5));
			cStmtObject.setLong(4,(Long)alSearchCriteria.get(1));
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(5);
			if(rs != null){
				while(rs.next()){
					enhancementVO = new EnhancementVO();

					if(rs.getString("POLICY_SEQ_ID") != null){
						enhancementVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					enhancementVO.setPolicyNo(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));

					if(rs.getString("EFFECTIVE_FROM_DATE") != null){
						enhancementVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if(rs.getString("EFFECTIVE_TO_DATE") != null){
						enhancementVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
					}//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

					enhancementVO.setSumInsured(TTKCommon.checkNull(rs.getString("FLOATER_SUM_INSURED")));
					alResultList.add(enhancementVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getSumInsuredEnhancementList()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getSumInsuredEnhancementList()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getSumInsuredEnhancementList()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getSumInsuredEnhancementList(ArrayList alSearchCriteria)

	/**
	 * This method returns the AuthorizationVO, which contains Authorization Details
	 * @param alAuthList ArrayList object which contains MemberSeqID,PAT_GEN_DETAIL_SEQ_ID,PAT_ENROLL_DETAIL_SEQ_ID and USER_SEQ_ID to get the Authorization Details
	 * @param strIdentifier String which contains module identifer Pre-Authorization/Claims
	 * @return AuthorizationVO object which contains Authorization Details
	 * @exception throws TTKException
	 */
	public AuthorizationVO getAuthorizationDetail(ArrayList alAuthList,String strIdentifier) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		AuthorizationVO authorizationVO = null;
		try{
			conn = ResourceManager.getConnection();

			if(strIdentifier.equals("Pre-Authorization"))
			{
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAuthorizationDetails);
				if(alAuthList.get(0) != null){
					cStmtObject.setLong(1,(Long)alAuthList.get(0));//Mandatory
				}//end of if(alAuthList.get(0) != null)
				else{
					cStmtObject.setString(1,null);
				}//end of else

				cStmtObject.setLong(2,(Long)alAuthList.get(1));//Mandatory
				cStmtObject.setLong(3,(Long)alAuthList.get(2));//Mandatory
				cStmtObject.setLong(4,(Long)alAuthList.get(3));//Mandatory
				cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(5);
			}//end of if(strIdentifier.equals("Pre-Authorization"))
			else if(strIdentifier.equals("Claims"))
			{
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSettlementDetails);
				cStmtObject.setLong(1,(Long)alAuthList.get(1));//Mandatory
				cStmtObject.setLong(2,(Long)alAuthList.get(2));//Mandatory
				cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			}//end of else if(strIdentifier.equals("Claims"))

			if(rs != null){
				while(rs.next()){
					authorizationVO = new AuthorizationVO();

					if(rs.getString("ASSIGN_USERS_SEQ_ID") != null){
						authorizationVO.setAssignUserSeqID(new Long(rs.getLong("ASSIGN_USERS_SEQ_ID")));
					}//end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)

					if(rs.getString("AVA_SUM_INSURED") != null){
						authorizationVO.setAvailSumInsured(new BigDecimal(rs.getString("AVA_SUM_INSURED")));
					}//end of if(rs.getString("AVA_SUM_INSURED") != null)
					else{
						authorizationVO.setAvailSumInsured(new BigDecimal("0.00"));
					}//end of else

					if(rs.getString("AVA_CUM_BONUS") != null){
						authorizationVO.setAvailCumBonus(new BigDecimal(rs.getString("AVA_CUM_BONUS")));
					}//end of if(rs.getString("AVA_CUM_BONUS") != null)
					else{
						authorizationVO.setAvailCumBonus(new BigDecimal("0.00"));
					}//end of else

					authorizationVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("PAT_STATUS_GENERAL_TYPE_ID")));

					if(rs.getString("BUFF_DETAIL_SEQ_ID") != null){
						authorizationVO.setBufferDtlSeqID(new Long(rs.getLong("BUFF_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("BUFF_DETAIL_SEQ_ID") != null)

					authorizationVO.setAuthorizedBy(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					authorizationVO.setReasonTypeID(TTKCommon.checkNull(rs.getString("RSON_GENERAL_TYPE_ID")));
					authorizationVO.setAuthPermittedBy(TTKCommon.checkNull(rs.getString("PERMISSION_SOUGHT_FROM")));
					authorizationVO.setDiscPresentYN(TTKCommon.checkNull(rs.getString("DISCRIPENCY_PRESENET_YN")));

					if(rs.getString("COMPLETED_DATE") != null){
						authorizationVO.setAuthorizedDate(new Date(rs.getTimestamp("COMPLETED_DATE").getTime()));
						authorizationVO.setAuthorizedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("COMPLETED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("COMPLETED_DATE").getTime())).split(" ")[1]:"");
						authorizationVO.setAuthorizedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("COMPLETED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("COMPLETED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("COMPLETED_DATE") != null)

					if(rs.getString("BALANCE_SEQ_ID") != null){
						authorizationVO.setBalanceSeqID(new Long(rs.getLong("BALANCE_SEQ_ID")));
					}//end of if(rs.getString("BALANCE_SEQ_ID") != null)

					if(rs.getString("DISCOUNT_AMOUNT") != null){
						authorizationVO.setDiscountAmount(new BigDecimal(rs.getString("DISCOUNT_AMOUNT")));
					}//end of if(rs.getString("DISCOUNT_AMOUNT") != null)
					else{
						authorizationVO.setDiscountAmount(new BigDecimal("0.00"));
					}//end of else

					if(rs.getString("CO_PAYMENT_AMOUNT") != null){
						authorizationVO.setCopayAmount(new BigDecimal(rs.getString("CO_PAYMENT_AMOUNT")));
					}//end of if(rs.getString("CO_PAYMENT_AMOUNT") != null)
					else{
						authorizationVO.setCopayAmount(new BigDecimal("0.00"));
					}//end of else

					if(rs.getString("MEMBER_SEQ_ID") != null){
						authorizationVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if(rs.getString("POLICY_SEQ_ID") != null){
						authorizationVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					authorizationVO.setEnrolTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));

					if(rs.getString("DATE_OF_HOSPITALIZATION") != null){
						authorizationVO.setClaimAdmnDate(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime()));
					}//end of if(rs.getString("DATE_OF_HOSPITALIZATION") != null)					

					if(rs.getString("un_available_suminsured") != null){
						authorizationVO.setUnAvailableSuminsured(new Integer(rs.getInt("un_available_suminsured")));
					}//end of if(rs.getString("un_available_suminsured") != null)

					if(rs.getString("POLICY_GROUP_SEQ_ID") !=null){
						authorizationVO.setPolicyGrpSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
					}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") !=null)

					if(strIdentifier.equals("Pre-Authorization")){
						if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
							authorizationVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
						}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

						if(rs.getString("MEM_TOTAL_SUM_INSURED") != null){
							authorizationVO.setTotalSumInsured(new BigDecimal(rs.getString("MEM_TOTAL_SUM_INSURED")));
						}//end of if(rs.getString("MEM_TOTAL_SUM_INSURED") != null)
						else{
							authorizationVO.setTotalSumInsured(new BigDecimal("0.00"));
						}//end of else

						if(rs.getString("PREV_APPROVED_AMOUNT") != null){
							authorizationVO.setPrevApprovedAmount(new BigDecimal(rs.getString("PREV_APPROVED_AMOUNT")));
						}//end of if(rs.getString("PREV_APPROVED_AMOUNT") != null)
						else{
							authorizationVO.setPrevApprovedAmount(new BigDecimal("0.00"));
						}//end of else

						if(rs.getString("PAT_REQUESTED_AMOUNT") != null){
							authorizationVO.setRequestedAmount(new BigDecimal(rs.getString("PAT_REQUESTED_AMOUNT")));
						}//end of if(rs.getString("PAT_REQUESTED_AMOUNT") != null)
						else{
							authorizationVO.setRequestedAmount(new BigDecimal("0.00"));
						}//end of else

						if(rs.getString("BUFFER_APP_AMOUNT") != null){
							authorizationVO.setApprovedBufferAmount(new BigDecimal(rs.getString("BUFFER_APP_AMOUNT")));
						}//end of if(rs.getString("BUFFER_APP_AMOUNT") != null)
						else{
							authorizationVO.setApprovedBufferAmount(new BigDecimal("0.00"));
						}//end of else

						authorizationVO.setAuthorizationNo(TTKCommon.checkNull(rs.getString("AUTH_NUMBER")));
						authorizationVO.setRemarks(TTKCommon.checkNull(rs.getString("AUTHORIZATION_REMARKS")));

						if(rs.getString("TOTAL_APP_AMOUNT") != null){
							authorizationVO.setApprovedAmount(new BigDecimal(rs.getString("TOTAL_APP_AMOUNT")));
						}//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)
						else{
							authorizationVO.setApprovedAmount(new BigDecimal("0.00"));
						}//end of else

						if(rs.getString("MAX_ALLOWED_AMOUNT") != null){
							authorizationVO.setMaxAllowedAmt(new BigDecimal(rs.getString("MAX_ALLOWED_AMOUNT")));
						}//end of if(rs.getString("MAX_ALLOWED_AMOUNT") != null)
						else{
							authorizationVO.setMaxAllowedAmt(new BigDecimal("0.00"));
						}//end of else

						authorizationVO.setCompletedYN(TTKCommon.checkNull(rs.getString("COMPLETED_YN")));
						authorizationVO.setClauseRemarks(TTKCommon.checkNull(rs.getString("REJECTION_REMARKS")));
						authorizationVO.setAuthLtrTypeID(TTKCommon.checkNull(rs.getString("AUTH_LETTER_GENERAL_TYPE_ID")));
					/**Added as per KOC 1216B Changee request
						 *  
						 **/
						
						//1.	preauth_co_pay_buffer_amount, --display in Pre-Auth Details tab
						//2.	co_payment_buffer_amount     --display in  Claims Deductions tab

					/*	if(rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT") != null){
							authorizationVO.setPreCopayBufferamount(new BigDecimal(rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT")));
						}//end of if(rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT") != null)
						else
						{
							authorizationVO.setPreCopayBufferamount(new BigDecimal("0.00"));
						}//end of else
						*/	
						if(rs.getString("CO_PAYMENT_BUFFER_AMOUNT") != null){
							authorizationVO.setCopayBufferamount(new BigDecimal(rs.getString("CO_PAYMENT_BUFFER_AMOUNT")));
						}//end of if(rs.getString("CO_PAYMENT_BUFFER_AMOUNT") != null)
						else
						{
							authorizationVO.setCopayBufferamount(new BigDecimal("0.00"));
						}//end of else
							 //Start Modification As per Koc 1140(Sum Insured Restriction) 
							if(rs.getString("MEM_REST_SUM_INSURED") != null)
							{
							   if(rs.getString("MEM_REST_SUM_INSURED") != "" || rs.getString("MEM_REST_SUM_INSURED") != "0"){
							   authorizationVO.setMemberRestrictedSIAmt(new BigDecimal(rs.getString("MEM_REST_SUM_INSURED")));
							    }
							else{
								authorizationVO.setMemberRestrictedSIAmt(new BigDecimal("0.00"));
								}
						    }//end of if(rs.getString("MEM_REST_SUM_INSURED") != null)
						else{
							authorizationVO.setMemberRestrictedSIAmt(new BigDecimal("0.00"));
							}
						if(rs.getString("AVA_REST_SUM_INSURED") != null)
						{
							if(rs.getString("AVA_REST_SUM_INSURED") != "" || rs.getString("AVA_REST_SUM_INSURED") != "0"){
								authorizationVO.setAvaRestrictedSIAmt(new BigDecimal(rs.getString("AVA_REST_SUM_INSURED")));
								}//end of if(rs.getString("AVA_REST_SUM_INSURED") != null)
						    else{
							authorizationVO.setAvaRestrictedSIAmt(new BigDecimal("0.00"));
						   }//end of else(rs.getString("AVA_REST_SUM_INSURED") != null)
						 }
						else{
							authorizationVO.setAvaRestrictedSIAmt(new BigDecimal("0.00"));
					        }//end of else(rs.getString("AVA_REST_SUM_INSURED") != null
                          if(rs.getString("FAM_REST_YN") != null){
							authorizationVO.setFamRestrictedYN(rs.getString("FAM_REST_YN"));
						     }//end of if(rs.getString("FAM_REST_YN") != null)
						else
						{	
						authorizationVO.setFamRestrictedYN("N");
						}
						/* Added as per KOC 1216B Changee request*/
						if(rs.getString("PAT_INS_STATUS")!=null)
						{
							if(rs.getString("PAT_INS_STATUS").equalsIgnoreCase("INP"))
							{
								authorizationVO.setInsurerApprovedStatus("In-Progress");
							}
							else if(rs.getString("PAT_INS_STATUS").equalsIgnoreCase("REJ"))
							{
								authorizationVO.setInsurerApprovedStatus("Rejected");
							}
							else if(rs.getString("PAT_INS_STATUS").equalsIgnoreCase("APR"))
							{
								authorizationVO.setInsurerApprovedStatus("Approved");
							}
							else if(rs.getString("PAT_INS_STATUS").equalsIgnoreCase("REQ"))
							{
								authorizationVO.setInsurerApprovedStatus("Required Information");
							}
							else{
								authorizationVO.setInsurerApprovedStatus(TTKCommon.checkNull(rs.getString("PAT_INS_STATUS")));	
							}
						}
						authorizationVO.setInsremarks(TTKCommon.checkNull(rs.getString("PAT_INS_REMARKS")));
  
						 
   						}//end of if(strIdentifier.equals("Pre-Authorization"))

					else if(strIdentifier.equals("Claims")){

						if(rs.getString("CLAIM_SEQ_ID") != null){
							authorizationVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
						}//end of if(rs.getString("CLAIM_SEQ_ID") != null)

						if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null){
							authorizationVO.setClmEnrollDtlSeqID(new Long(rs.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
						}//end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null)

						if(rs.getString("BUFFER_HDR_SEQ_ID") != null){
							authorizationVO.setBufferHdrSeqID(new Long(rs.getLong("BUFFER_HDR_SEQ_ID")));
						}//end of if(rs.getString("BUFFER_HDR_SEQ_ID") != null)

						if(rs.getString("TOT_SUM_INSURED") != null){
							authorizationVO.setTotalSumInsured(new BigDecimal(rs.getString("TOT_SUM_INSURED")));
						}//end of if(rs.getString("MEM_TOTAL_SUM_INSURED") != null)
						else{
							authorizationVO.setTotalSumInsured(new BigDecimal("0.00"));
						}//end of else

						/*if(rs.getString("BUFFER_APP_AMOUNT") != null){
							authorizationVO.setApprovedBufferAmount(new BigDecimal(rs.getString("BUFFER_APP_AMOUNT")));
						}//end of if(rs.getString("BUFFER_APP_AMOUNT") != null)
						else{
							authorizationVO.setApprovedBufferAmount(new BigDecimal("0.00"));
						}//end of else
						 */
						if(rs.getString("PAT_APPROVED_AMOUNT") != null){
							authorizationVO.setPAApprovedAmt(new BigDecimal(rs.getString("PAT_APPROVED_AMOUNT")));
						}//end of if(rs.getString("PAT_APPROVED_AMOUNT") != null)
						else{
							authorizationVO.setPAApprovedAmt(new BigDecimal("0.00"));
						}//end of else

						if(rs.getString("REQUESTED_AMOUNT") != null){
							authorizationVO.setRequestedAmount(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
						}//end of if(rs.getString("PAT_REQUESTED_AMOUNT") != null)
						else{
							authorizationVO.setRequestedAmount(new BigDecimal("0.00"));
						}//end of else
						if(rs.getString("SERV_TAX_CALC_AMOUNT")!= null){
							authorizationVO.setTaxAmtPaid(new BigDecimal(rs.getString("SERV_TAX_CALC_AMOUNT")));
						}//end of if(rs.getString("SERV_TAX_CALC_AMOUNT")!= null)
						else{
							authorizationVO.setTaxAmtPaid(new BigDecimal("0.00"));
						}// end of else
						if(rs.getString("SER_TAX_REQ_AMT")!= null){
							authorizationVO.setRequestedTaxAmt(new BigDecimal(rs.getString("SER_TAX_REQ_AMT")));
						}//end of if(rs.getString("SERV_TAX_CALC_AMOUNT")!= null)
						else{
							authorizationVO.setRequestedTaxAmt(new BigDecimal("0.00"));
						}// end of else

						if(rs.getString("TOTAL_APP_AMOUNT") != null){
							authorizationVO.setApprovedAmount(new BigDecimal(rs.getString("TOTAL_APP_AMOUNT")));
						}//end of if(rs.getString("TOTAL_APP_AMOUNT") != null)

						else{
							authorizationVO.setApprovedAmount(new BigDecimal("0.00"));
						}//end of else

						if(rs.getString("MAX_ALLOWED_AMOUNT") != null){
							authorizationVO.setMaxAllowedAmt(new BigDecimal(rs.getString("MAX_ALLOWED_AMOUNT")));
						}//end of if(rs.getString("MAX_ALLOWED_AMOUNT") != null)
						else{
							authorizationVO.setMaxAllowedAmt(new BigDecimal("0.00"));
						}//end of else

						authorizationVO.setAuthorizationNo(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NUMBER")));

						if(rs.getString("CLAIM_APP_BUFFER_AMOUNT") != null){
							authorizationVO.setApprovedBufferAmount(new BigDecimal(rs.getString("CLAIM_APP_BUFFER_AMOUNT")));
						}//end of if(rs.getString("BUFFER_APP_AMOUNT") != null)
						else{
							authorizationVO.setApprovedBufferAmount(new BigDecimal("0.00"));
						}//end of else
						if(rs.getString("FINAL_APP_AMOUNT") != null){
							authorizationVO.setFinalApprovedAmt(new BigDecimal(rs.getString("FINAL_APP_AMOUNT")));
						}//end of if(rs.getString("FINAL_APP_AMOUNT") != null)
						else
						{
							authorizationVO.setFinalApprovedAmt(new BigDecimal("0.00"));
						}//end of else

						authorizationVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
						authorizationVO.setClaimSubTypeID(TTKCommon.checkNull(rs.getString("CLAIM_SUB_GENERAL_TYPE_ID")));
						authorizationVO.setCompletedYN(TTKCommon.checkNull(rs.getString("COMPLETED_YN")));
						authorizationVO.setCalcButDispYN(TTKCommon.checkNull(rs.getString("CALC_BUTTON_DISP_YN")));
						authorizationVO.setPressButManYN(TTKCommon.checkNull(rs.getString("PRESS_BUTTON_MAN_YN")));

						if(rs.getString("AVA_DOMICILARY_AMOUNT") != null){
							authorizationVO.setAvailDomTrtLimit(new BigDecimal(rs.getString("AVA_DOMICILARY_AMOUNT")));
						}//end of if(rs.getString("AVA_DOMICILARY_AMOUNT") != null)
						else{
							authorizationVO.setAvailDomTrtLimit(new BigDecimal("0.00"));
						}//end of else
						authorizationVO.setDVMessageYN(TTKCommon.checkNull(rs.getString("DV_MESSAGE_YN")));
						authorizationVO.setClaimTypeID(TTKCommon.checkNull(rs.getString("CLAIM_GENERAL_TYPE_ID")));

						if(rs.getString("PREAUTH_CO_PAYMENT_AMOUNT") != null){
							authorizationVO.setPACopayAmt(new BigDecimal(rs.getString("PREAUTH_CO_PAYMENT_AMOUNT")));
						}//end of if(rs.getString("PREAUTH_CO_PAYMENT_AMOUNT") != null)
						else{
							authorizationVO.setPACopayAmt(new BigDecimal("0.00"));
						}//end of else

						if(rs.getString("PREAUTH_DISCOUNT_AMOUNT") != null){
							authorizationVO.setPADiscountAmt(new BigDecimal(rs.getString("PREAUTH_DISCOUNT_AMOUNT")));
						}//end of if(rs.getString("PREAUTH_DISCOUNT_AMOUNT") != null)
						else{
							authorizationVO.setPADiscountAmt(new BigDecimal("0.00"));
						}//end of else

						if(rs.getString("DEPOSIT_AMOUNT") != null){
							authorizationVO.setDepositAmt(new BigDecimal(rs.getString("DEPOSIT_AMOUNT")));
						}//end of if(rs.getString("DEPOSIT_AMOUNT") != null)
						else{
							authorizationVO.setDepositAmt(new BigDecimal("0.00"));
						}//end of else
						/**Added as per KOC 1216B Changee request
						 *  
						 **/
						
						//1.	preauth_co_pay_buffer_amount, --display in Pre-Auth Details tab
						//2.	co_payment_buffer_amount     --display in  Claims Deductions tab

						if(rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT") != null){
							authorizationVO.setPreCopayBufferamount(new BigDecimal(rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT")));
						}//end of if(rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT") != null)
						else
						{
							authorizationVO.setPreCopayBufferamount(new BigDecimal("0.00"));
						}//end of else
						
						if(rs.getString("CO_PAYMENT_BUFFER_AMOUNT") != null){
							authorizationVO.setCopayBufferamount(new BigDecimal(rs.getString("CO_PAYMENT_BUFFER_AMOUNT")));
						}//end of if(rs.getString("CO_PAYMENT_BUFFER_AMOUNT") != null)
						else
						{
							authorizationVO.setCopayBufferamount(new BigDecimal("0.00"));
						}//end of else
						//Start Modification As per Koc 1140(Sum Insured Restriction) 
						if(rs.getString("MEM_REST_SUM_INSURED") != null)
							{
								if(rs.getString("MEM_REST_SUM_INSURED") != "" || rs.getString("MEM_REST_SUM_INSURED") != "0"){
						       	authorizationVO.setMemberRestrictedSIAmt(new BigDecimal(rs.getString("MEM_REST_SUM_INSURED")));
     						}
							else{
								authorizationVO.setMemberRestrictedSIAmt(new BigDecimal("0.00"));
							}

						}//end of if(rs.getString("MEM_REST_SUM_INSURED") != null)
						else{
							authorizationVO.setMemberRestrictedSIAmt(new BigDecimal("0.00"));
							}
					    if(rs.getString("AVA_REST_SUM_INSURED") != null)
						{
							if(rs.getString("AVA_REST_SUM_INSURED") != "" || rs.getString("AVA_REST_SUM_INSURED") != "0"){
								authorizationVO.setAvaRestrictedSIAmt(new BigDecimal(rs.getString("AVA_REST_SUM_INSURED")));
							}//end of if(rs.getString("AVA_REST_SUM_INSURED") != null)
					    	else{
							authorizationVO.setAvaRestrictedSIAmt(new BigDecimal("0.00"));
						   }//end of else(rs.getString("AVA_REST_SUM_INSURED") != null)
						}//	if(rs.getString("MEM_REST_SUM_INSURED") != null)?"":""
						else{
							authorizationVO.setAvaRestrictedSIAmt(new BigDecimal("0.00"));
						   }//end of else(rs.getString("AVA_REST_SUM_INSURED") != null)
                          if(rs.getString("FAM_REST_YN") != null){
							authorizationVO.setFamRestrictedYN(rs.getString("FAM_REST_YN"));
							}//end of if(rs.getString("FAM_REST_YN") != null)
						else
						{	authorizationVO.setFamRestrictedYN("N");
							}
						//end Modification As per Koc 1140(Sum Insured Restriction) 
						/** Added as per KOC 1216B Changee request
						 *  
						 **/
						// koc1216A
						authorizationVO.setAdditionalDomicialaryYN(TTKCommon.checkNull(rs.getString("additional_domicilary_yn")));	
						//KOC 1286 for OPD
						authorizationVO.setOPDAmountYN(TTKCommon.checkNull(rs.getString("OPD_BENEFITS_YN")));
						if(rs.getString("CLM_INS_STATUS")!=null)
						{
							if(rs.getString("CLM_INS_STATUS").equalsIgnoreCase("INP"))
							{
								authorizationVO.setInsurerApprovedStatus("In-Progress");
							}
							else if(rs.getString("CLM_INS_STATUS").equalsIgnoreCase("REJ"))
							{
								authorizationVO.setInsurerApprovedStatus("Rejected");
							}
							else if(rs.getString("CLM_INS_STATUS").equalsIgnoreCase("APR"))
							{
								authorizationVO.setInsurerApprovedStatus("Approved");
							}
							else if(rs.getString("CLM_INS_STATUS").equalsIgnoreCase("REQ"))
							{
								authorizationVO.setInsurerApprovedStatus("Required Information");
							}
							else{
								authorizationVO.setInsurerApprovedStatus(TTKCommon.checkNull(rs.getString("CLM_INS_STATUS")));	
							}
						}
						authorizationVO.setInsremarks(TTKCommon.checkNull(rs.getString("CLM_INS_REMARKS")));	
						
						//KOC 1286 for OPD						
					}//end of else if(strIdentifier.equals("Claims"))
				}//end of while(rs.next())
			}//end of if(rs != null)
			return authorizationVO;
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getAuthorizationDetail()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getAuthorizationDetail()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getAuthorizationDetail()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getAuthorizationDetail(ArrayList alAuthList)

	/**
	 * This method saves the Authorization information
	 * @param authorizationVO AuthorizationVO contains Authorization information
	 * @param strIdentifier Pre-Authorization/Claims
	 * @return long int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveAuthorization(AuthorizationVO authorizationVO,String strIdentifier) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();

			if(strIdentifier.equalsIgnoreCase("Pre-Authorization")){
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveAuthorization);
				if(authorizationVO.getPreAuthSeqID() != null){
					cStmtObject.setLong(1,authorizationVO.getPreAuthSeqID());
				}//end of if(authorizationVO.getPreAuthSeqID() != null)
				else{
					cStmtObject.setString(1,null);
				}//end of else

				if(authorizationVO.getEnrollDtlSeqID() != null){
					cStmtObject.setLong(2,authorizationVO.getEnrollDtlSeqID());
				}//end of if(authorizationVO.getEnrollDtlSeqID() != null)
				else{
					cStmtObject.setString(2,null);
				}//end of else

				cStmtObject.setString(3,authorizationVO.getAuthorizationNo());

				if(authorizationVO.getTotalSumInsured() != null){
					cStmtObject.setBigDecimal(4,authorizationVO.getTotalSumInsured());
				}//end of if(authorizationVO.getTotalSumInsured() != null)
				else{
					cStmtObject.setString(4,null);
				}//en of else

				cStmtObject.setString(5,authorizationVO.getStatusTypeID());
				cStmtObject.setString(6,authorizationVO.getReasonTypeID());
				cStmtObject.setString(7,authorizationVO.getRemarks());

				if(authorizationVO.getAssignUserSeqID() != null){
					cStmtObject.setLong(8,authorizationVO.getAssignUserSeqID());
				}//end of if(authorizationVO.getAssignUserSeqID() != null)
				else{
					cStmtObject.setString(8,null);
				}//end of else

				cStmtObject.setString(9,authorizationVO.getAuthPermittedBy());

				if(authorizationVO.getBalanceSeqID() != null){
					cStmtObject.setLong(10,authorizationVO.getBalanceSeqID());
				}//end of if(authorizationVO.getBalanceSeqID() != null)
				else{
					cStmtObject.setString(10,null);
				}//end of else

				if(authorizationVO.getApprovedAmount() != null){
					cStmtObject.setBigDecimal(11,authorizationVO.getApprovedAmount());
				}//end of if(authorizationVO.getApprovedAmount() != null)
				else{
					cStmtObject.setString(11,null);
				}//end of else

				if(authorizationVO.getDiscountAmount() != null){
					cStmtObject.setBigDecimal(12,authorizationVO.getDiscountAmount());
				}//end of if(authorizationVO.getDiscountAmount() != null)
				else{
					cStmtObject.setString(12,null);
				}//end of else

				if(authorizationVO.getCopayAmount() != null){
					cStmtObject.setBigDecimal(13,authorizationVO.getCopayAmount());
				}//end of if(authorizationVO.getCopayAmount() != null)
				else{
					cStmtObject.setString(13,null);
				}//end of else
				//ADDED AS PER KOC 1216B Change Request on 11 jan 2012
				
			  //  v_co_pay_buff_amount                IN  OUT pat_general_details.co_payment_buffer_amount%TYPE, -- new parameter
				if(authorizationVO.getCopayBufferamount() != null){
					cStmtObject.setBigDecimal(14,authorizationVO.getCopayBufferamount());
				}//end of if(authorizationVO.getCopayBufferamount() != null)
				else{
					cStmtObject.setString(14,null);
				}//end of else
				cStmtObject.setLong(15,authorizationVO.getUpdatedBy());
					//Start Modification as per KOC 1140(SUM INSURED RESTRICTION)
				if(authorizationVO.getMemberRestrictedSIAmt() != null){
				cStmtObject.setBigDecimal(16,authorizationVO.getMemberRestrictedSIAmt());
				}//end of if(authorizationVO.getMemberRestrictedSIAmt() != null)
				else{
				cStmtObject.setString(16,null);
				}//end of else getMemberRestrictedSIAmt
				 if(authorizationVO.getAvaRestrictedSIAmt() != null){
				cStmtObject.setBigDecimal(17,authorizationVO.getAvaRestrictedSIAmt());
				}//end of if(authorizationVO.getAvaRestrictedSIAmt() != null)
				else{
					cStmtObject.setString(17,null);
				}//end of else getAvaRestrictedSIAmt
        		if(authorizationVO.getFamRestrictedYN() != null){
				cStmtObject.setString(18,authorizationVO.getFamRestrictedYN());
				}//end of if(authorizationVOgetFamRestrictedYN() != null)
				  else{
						cStmtObject.setString(18,null);
					}//end of else getFamRestrictedYN
				//END Modification as per KOC 1140(SUM INSURED RESTRICTION)
        		if(authorizationVO.getBalanceDedAmount()!=null)
				{
                    cStmtObject.setBigDecimal(19,authorizationVO.getBalanceDedAmount());
				}
				else
				{
                    cStmtObject.setString(19,null);
                }
                //added for CR KOC Mail-SMS for Cigna
                cStmtObject.setString(20,authorizationVO.getMailNotifyYN());
                //added for Policy deductable - KOC-1277		
              	cStmtObject.registerOutParameter(21,Types.INTEGER);
				cStmtObject.execute();
				iResult = cStmtObject.getInt(21);
				/*cStmtObject.registerOutParameter(16,Types.INTEGER);
				cStmtObject.execute();
				iResult = cStmtObject.getInt(16);*/
			}//end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization"))

			else if(strIdentifier.equalsIgnoreCase("Claims")){

				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveSettlement);
				if(authorizationVO.getAssignUserSeqID() != null){
					cStmtObject.setLong(1,authorizationVO.getAssignUserSeqID());
				}//end of if(authorizationVO.getAssignUserSeqID() != null)
				else{
					cStmtObject.setString(1,null);
				}//end of else

				if(authorizationVO.getClaimSeqID() != null){
					cStmtObject.setLong(2,authorizationVO.getClaimSeqID());
				}//end of if(authorizationVO.getClaimSeqID() != null)
				else{
					cStmtObject.setString(2,null);
				}//end of else

				if(authorizationVO.getTotalSumInsured() != null){
					cStmtObject.setBigDecimal(3,authorizationVO.getTotalSumInsured());
				}//end of if(authorizationVO.getTotalSumInsured() != null)
				else{
					cStmtObject.setString(3,null);
				}//end of else

				cStmtObject.setString(4,authorizationVO.getRemarks());
				cStmtObject.setString(5,authorizationVO.getAuthorizationNo());
				cStmtObject.setString(6,authorizationVO.getStatusTypeID());
				cStmtObject.setString(7,authorizationVO.getAuthPermittedBy());

				if(authorizationVO.getBalanceSeqID() != null){
					cStmtObject.setLong(8,authorizationVO.getBalanceSeqID());
				}//end of if(authorizationVO.getBalanceSeqID() != null)
				else{
					cStmtObject.setString(8,null);
				}//end of else

				if(authorizationVO.getApprovedAmount() != null){
					cStmtObject.setBigDecimal(9,authorizationVO.getApprovedAmount());
				}//end of if(authorizationVO.getApprovedAmount() != null)
				else{
					cStmtObject.setString(9,null);
				}//end of else

				cStmtObject.setLong(10,authorizationVO.getUpdatedBy());
				cStmtObject.setString(11,authorizationVO.getReasonTypeID());

				if(authorizationVO.getDiscountAmount() != null){
					cStmtObject.setBigDecimal(12,authorizationVO.getDiscountAmount());
				}//end of if(authorizationVO.getDiscountAmount() != null)
				else{
					cStmtObject.setString(12,null);
				}//end of else

				if(authorizationVO.getCopayAmount() != null){
					cStmtObject.setBigDecimal(13,authorizationVO.getCopayAmount());
				}//end of if(authorizationVO.getCopayAmount() != null)
				else{
					cStmtObject.setString(13,null);
				}//end of else

				if(authorizationVO.getDepositAmt() != null){
					cStmtObject.setBigDecimal(14,authorizationVO.getDepositAmt());
				}//end of if(authorizationVO.getDepositAmt() != null)
				else{
					cStmtObject.setString(14,null);
				}//end of else
				if(authorizationVO.getTaxAmtPaid() != null){
					cStmtObject.setBigDecimal(15,authorizationVO.getTaxAmtPaid());
				}//end of if(authorizationVO.getTaxAmtPaid() != null)
				else 
				{
					cStmtObject.setString(15,null);
				}//end of else
				if(authorizationVO.getSerTaxCalPer() != null){
					cStmtObject.setBigDecimal(16,authorizationVO.getSerTaxCalPer());
				}//end of if(authorizationVO.getSerTaxCalPer() != null)
				else
				{
					cStmtObject.setString(16,null);
				}//end of else

				 //Added as per KOC 1216B Change Request in claims 
				
				if(authorizationVO.getCopayBufferamount() != null){
					cStmtObject.setBigDecimal(17,authorizationVO.getCopayBufferamount());
				}//end of if(authorizationVO.getCopayBufferamount() != null)
				else
				{
					cStmtObject.setString(17,null);
				}//end of else
				//Start Modification as per KOC 1140(SUM INSURED RESTRICTION)
				if(authorizationVO.getMemberRestrictedSIAmt() != null){
					cStmtObject.setBigDecimal(18,authorizationVO.getMemberRestrictedSIAmt());
					}//end of if(authorizationVO.getMemberRestrictedSIAmt() != null)
					else{
					cStmtObject.setString(18,null);
					}//end of else getMemberRestrictedSIAmt
				if(authorizationVO.getAvaRestrictedSIAmt() != null){
					cStmtObject.setBigDecimal(19,authorizationVO.getAvaRestrictedSIAmt());
				     }//end of if(authorizationVO.getAvaRestrictedSIAmt() != null)
				else{
					cStmtObject.setString(19,null);
				     }//end of else getAvaRestrictedSIAmt
				  if(authorizationVO.getFamRestrictedYN() != null){
					cStmtObject.setString(20,authorizationVO.getFamRestrictedYN());
					}//end of if(authorizationVOgetFamRestrictedYN() != null)
				  else{
					cStmtObject.setString(20,null);
					}//end of else getFamRestrictedYN
				 cStmtObject.registerOutParameter(21,Types.INTEGER);
				cStmtObject.execute();
				iResult = cStmtObject.getInt(21);
				//END Modification as per KOC 1140(SUM INSURED RESTRICTION)
				
				/*cStmtObject.registerOutParameter(18,Types.INTEGER);
				cStmtObject.execute();
				iResult = cStmtObject.getInt(18);*/
			}//end of else
			  //Added as per KOC 1216B Change Request in claims 
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
					log.error("Error while closing the Statement in PreAuthDAOImpl saveAuthorization()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl saveAuthorization()",sqlExp);
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
	}//end of saveAuthorization(AuthorizationVO authorizationVO)

	/**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deletePATGeneral(ArrayList alDeleteList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeletePATGeneral);
			cStmtObject.setString(1, (String)alDeleteList.get(0));//FLAG PAT/AIL/SFL/INV/PED/COU/BUFFER
			cStmtObject.setString(2, (String)alDeleteList.get(1));//CONCATENATED STRING OF  delete SEQ_IDS

			if(alDeleteList.get(2) != null){
				cStmtObject.setLong(3,(Long)alDeleteList.get(2));//Mandatory  PAT_ENROLL_DETAIL_SEQ_ID
			}//end of if(alDeleteList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			if(alDeleteList.get(3) != null){
				cStmtObject.setLong(4,(Long)alDeleteList.get(3));//Mandatory  PAT_GENERAL_DETAIL_SEQ_ID
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
					log.error("Error while closing the Statement in PreAuthDAOImpl deletePATGeneral()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl deletePATGeneral()",sqlExp);
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
	}//end of deletePATGeneral(ArrayList alDeleteList)

	/**
	 * This method returns the ArrayList, which contains Discrepancy Details
	 * @param alDiscrepancyList lwhich contains PAT_GEN_DETAIL_SEQ_ID,CLAIM_SEQ_ID,USER_SEQ_ID to get the Discrepancy Details
	 * @return ArrayList object which contains Discrepancy Details
	 * @exception throws TTKException
	 */
	public ArrayList getDiscrepancyInfo(ArrayList alDiscrepancyList) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		DiscrepancyVO discrepancyVO = null;
		ArrayList<Object> alDiscrepancyInfo = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDiscrepancy);

			if(alDiscrepancyList.get(0) != null){
				cStmtObject.setLong(1,(Long)alDiscrepancyList.get(0));
			}//end of if(alDiscrepancyList.get(0) != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else

			if(alDiscrepancyList.get(1) != null){
				cStmtObject.setLong(2,(Long)alDiscrepancyList.get(1));
			}//end of if(alDiscrepancyList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			cStmtObject.setLong(3,(Long)alDiscrepancyList.get(2));
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			alDiscrepancyInfo= new ArrayList<Object>();
			if(rs != null){
				while(rs.next()){
					discrepancyVO = new DiscrepancyVO();

					if(rs.getString("DISCREPANCY_SEQ_ID") != null){
						discrepancyVO.setDiscrepancySeqID(new Long(rs.getLong("DISCREPANCY_SEQ_ID")));
					}//end of if(rs.getString("DISCREPANCY_SEQ_ID") != null)

					discrepancyVO.setDiscrepancyTypeID(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));

					if(rs.getString("REMARKS") != null){
						discrepancyVO.setDiscrepancy(TTKCommon.checkNull(rs.getString("DISCREPANCY")).concat(" - ").concat(rs.getString("REMARKS")));
					}//end of if(rs.getString("REMARKS") != null)
					else{
						discrepancyVO.setDiscrepancy(TTKCommon.checkNull(rs.getString("DISCREPANCY")));
					}//end of else

					discrepancyVO.setResolvedYN(TTKCommon.checkNull(rs.getString("RESOLVED_YN")));
					discrepancyVO.setResolvableYN(TTKCommon.checkNull(rs.getString("RESOLVABLE_YN")));
					alDiscrepancyInfo.add(discrepancyVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
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
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getDiscrepancyInfo()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreAuthDAOImpl getDiscrepancyInfo()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getDiscrepancyInfo()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alDiscrepancyInfo;
	}//end of getDiscrepancyInfo(ArrayList alDiscrepancyList)

	/**
	 * This method resolves the Discrepancy information
	 * @param alDiscrepancyVO ArrayList contains Discrepancy information
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int resolveDiscrepancy(ArrayList alDiscrepancyVO) throws TTKException {
		int iResult = 1;
		StringBuffer sbfSQL = null;
		Statement stmt = null;
		DiscrepancyVO discrepancyVO = null;
		Connection conn = null;
		try{
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			stmt = (java.sql.Statement)conn.createStatement();
			if(alDiscrepancyVO != null){
				for(int i=0;i<alDiscrepancyVO.size();i++){
					sbfSQL = new StringBuffer();
					discrepancyVO = (DiscrepancyVO)alDiscrepancyVO.get(i);
					sbfSQL = sbfSQL.append("'"+discrepancyVO.getDiscrepancySeqID()+"',");
					sbfSQL = sbfSQL.append("'"+discrepancyVO.getResolvedYN()+"',");
					sbfSQL = sbfSQL.append("'"+discrepancyVO.getUpdatedBy()+"')}");
					stmt.addBatch(strResolveDiscrepancy+sbfSQL.toString());
				}//end of for
			}//end of if(alDiscrepancyVO != null)
			stmt.executeBatch();
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			try {
				conn.rollback();
				throw new TTKException(sqlExp, "preauth");
			} //end of try
			catch (SQLException sExp) {
				throw new TTKException(sExp, "preauth");
			}//end of catch (SQLException sExp)
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			try {
				conn.rollback();
				throw new TTKException(exp, "preauth");
			} //end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "preauth");
			}//end of catch (SQLException sqlExp)
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the Statement
			{
				try
				{
					if (stmt != null) stmt .close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthDAOImpl resolveDiscrepancy()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl resolveDiscrepancy()",sqlExp);
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
				stmt  = null;
				conn = null;
			}//end of finally
		}//end of finally
		return iResult;
	}//end of resolveDiscrepancy(ArrayList alDiscrepancyVO)

	/**
	 * This method will save the Review Information and Workflow will change to Coding
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode Pre-authorization/Claims - PAT/CLM
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO returnToCoding(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException {
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		String strCodingReviewYN = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;

		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strReturnToCoding);

			if(strMode.equals("PAT")){
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
			preauthDetailVO.setCoding_review_yn(strCodingReviewYN);
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
		return preauthDetailVO;
	}//end of returnToCoding(PreAuthDetailVO preauthDetailVO,String strMode)
	
	
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
	 * This method reassign the enrollment ID
	 * @param alReAssignEnrID the arraylist of details for which have to be reassigned
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int reAssignEnrID(ArrayList alReAssignEnrID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strReAssignEnrID);
			cStmtObject.setLong(1,(Long)alReAssignEnrID.get(0));//PAT_GEN_DETAIL_SEQ_ID
			cStmtObject.setLong(2,(Long)alReAssignEnrID.get(1));//PAT_ENROLL_DETAIL_SEQ_ID			
			cStmtObject.setString(3,(String)alReAssignEnrID.get(2));//PAT_STATUS_GENERAL_TYPE_ID 
			cStmtObject.setLong(4,(Long)alReAssignEnrID.get(3));//MEMBER_SEQ_ID              
			cStmtObject.setLong(5,(Long)alReAssignEnrID.get(4));//ADDED_BY
			cStmtObject.registerOutParameter(6, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);//ROWS_PROCESSED
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
	 * This method returns the SIInfoVO, which contains Balance SI Details.
	 * @param lngMemSeqID long value which contains member id to get the Balance SI Details
	 * @param lngPolicyGrpSeqID long value which contains Policy Group Seq ID
	 * @param lngBalanceSeqID long value which contains Balance Seq ID
	 * @return PreAuthMedicalVO object which contains Balance SI Details
	 * @exception throws TTKException
	 */
	public SIInfoVO getBalanceSIDetail(long lngMemSeqID,long lngPolicyGrpSeqID,long lngBalanceSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		//Added as per KOC 1216B change request
		ResultSet rs4 = null;
		ResultSet rs5 = null;////koc1289_1272
		MemberBufferVO memberBufferVO=null;
		//Added as per KOC 1216B change request
		SIInfoVO siInfoVO = new SIInfoVO();
	    BalanceSIInfoVO balanceSIInfoVO = null;
		SIBreakupInfoVO siBreakupInfoVO = null;
		StopPreauthClaimVO stopPreClmVO = null;  
        RestorationPreauthClaimVO restorationPreauthClaimVO = null;  
        ArrayList<Object> alSIBreakupList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBalanceSIInfo);

			cStmtObject.setLong(1,lngMemSeqID);
			cStmtObject.setLong(2,lngPolicyGrpSeqID);
			cStmtObject.setLong(3,lngBalanceSeqID);
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
			//Added as per kOC 1216 b Change request
			cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);//koc1289_1272
			//Added as per kOC 1216 b Change request
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(4);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(5);
			rs3 = (java.sql.ResultSet)cStmtObject.getObject(6);
			//Added as per kOC 1216 b Change request
			rs4 = (java.sql.ResultSet)cStmtObject.getObject(7);
			//Added as per kOC 1216 b Change request
            rs5 = (java.sql.ResultSet)cStmtObject.getObject(8);//koc1289_1272
			if(rs1 != null){
				while(rs1.next()){
					balanceSIInfoVO = new BalanceSIInfoVO();

					if(rs1.getString("SUM_INSURED") != null){
						balanceSIInfoVO.setTotalSumInsured(new BigDecimal(rs1.getString("SUM_INSURED")));
					}//end of if(rs1.getString("SUM_INSURED") != null)
					else{
						balanceSIInfoVO.setTotalSumInsured(new BigDecimal("0.00"));
					}//end of else

					if(rs1.getString("BONUS") != null){
						balanceSIInfoVO.setBonus(new BigDecimal(rs1.getString("BONUS")));
					}//end of if(rs1.getString("BONUS") != null)
					else{
						balanceSIInfoVO.setBonus(new BigDecimal("0.00"));
					}//end of else

					if(rs1.getString("BUFFER_AMOUNT") != null){
						balanceSIInfoVO.setBufferAmt(new BigDecimal(rs1.getString("BUFFER_AMOUNT")));
					}//end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else{
						balanceSIInfoVO.setBufferAmt(new BigDecimal("0.00"));
					}//end of else

					if(rs1.getString("UTILISED_SUM_INSURED") != null){
						balanceSIInfoVO.setUtilizedSumInsured(new BigDecimal(rs1.getString("UTILISED_SUM_INSURED")));
					}//end of if(rs1.getString("UTILISED_SUM_INSURED") != null)
					else{
						balanceSIInfoVO.setUtilizedSumInsured(new BigDecimal("0.00"));
					}//end of else

					if(rs1.getString("UTILISED_CUM_BONUS") != null){
						balanceSIInfoVO.setUtilizedBonus(new BigDecimal(rs1.getString("UTILISED_CUM_BONUS")));
					}//end of if(rs1.getString("UTILISED_CUM_BONUS") != null)
					else{
						balanceSIInfoVO.setUtilizedBonus(new BigDecimal("0.00"));
					}//end of else

					balanceSIInfoVO.setPolSubType(TTKCommon.checkNull(rs1.getString("POLICY_SUB_TYPE")));
					balanceSIInfoVO.setBufferAllocation(TTKCommon.checkNull(rs1.getString("BUFFER_ALLOCATION")));

					if(rs1.getString("UTILISED_BUFFER") != null){
						balanceSIInfoVO.setUtilizedBufferAmt(new BigDecimal(rs1.getString("UTILISED_BUFFER")));
					}//end of if(rs1.getString("UTILISED_BUFFER") != null)
					else{
						balanceSIInfoVO.setUtilizedBufferAmt(new BigDecimal("0.00"));
					}//end of else					
				}//end of while(rs1.next())
				siInfoVO.setBalSIInfoVO(balanceSIInfoVO);
			}//end of if(rs1 != null)

			if(rs2 != null){
				while(rs2.next()){
					siBreakupInfoVO = new SIBreakupInfoVO();

					siBreakupInfoVO.setRowNbr(TTKCommon.checkNull(rs2.getString("RNO")));

					if(rs2.getString("MEM_SUM_INSURED") != null){
						siBreakupInfoVO.setMemSumInsured(new BigDecimal(rs2.getString("MEM_SUM_INSURED")));
					}//end of if(rs2.getString("MEM_SUM_INSURED") != null)
					else{
						siBreakupInfoVO.setMemSumInsured(new BigDecimal("0.00"));
					}//end of else

					if(rs2.getString("MEM_BONUS_AMOUNT") != null){
						siBreakupInfoVO.setMemBonus(new BigDecimal(rs2.getString("MEM_BONUS_AMOUNT")));
					}//end of if(rs2.getString("MEM_BONUS_AMOUNT") != null)
					else{
						siBreakupInfoVO.setMemBonus(new BigDecimal("0.00"));
					}//end of else

					if(rs2.getString("EFFECTIVE_DATE") != null){
						siBreakupInfoVO.setSIEffDate(new Date(rs2.getTimestamp("EFFECTIVE_DATE").getTime()));
					}//end of if(rs2.getString("EFFECTIVE_DATE") != null)

					alSIBreakupList.add(siBreakupInfoVO);
				}//end of while(rs2.next())
				siInfoVO.setSIBreakupList(alSIBreakupList);
			}//end of if(rs2 != null)

			if(rs3 != null){
				while(rs3.next()){		
					stopPreClmVO = new StopPreauthClaimVO();
					stopPreClmVO.setStopPatClmYN(rs3.getString("STOP_PAT_CLM_PROCESS_YN"));
					if(rs3.getString("RECIEVED_AFTER") != null){
						stopPreClmVO.setPatClmRcvdAftr(new Date(rs3.getTimestamp("RECIEVED_AFTER").getTime()));
					}//end of if(rs3.getString("PAT_CLM_RECIEVED_AFTER") != null){
				}//end of while(rs2.next())				
				siInfoVO.setStopPreClmVO(stopPreClmVO);
			}//end of if(rs3 != null)
			//Added as per KOC 1216B Change Request
			if(rs4 != null){
				while(rs4.next()){		
					memberBufferVO = new MemberBufferVO();
					memberBufferVO.setMemberBufferYN(rs4.getString("MEMBER_BUFFER_YN"));//member_buffer_yn
					
					//Mem_Buffer_Alloc
					if(rs4.getString("MEM_BUFFER_ALLOC") != null){
						memberBufferVO.setBufferAmtMember(new BigDecimal(rs4.getString("MEM_BUFFER_ALLOC")));
					}//end of if(rs4.getString("MEM_BUFFER_ALLOC") != null)
					else{
						memberBufferVO.setBufferAmtMember(new BigDecimal("0.00"));
					}//end of else
					
					//used_buff_amount
					if(rs4.getString("USED_BUFF_AMOUNT") != null){
						memberBufferVO.setUtilizedBufferAmtMember(new BigDecimal(rs4.getString("USED_BUFF_AMOUNT")));
					}//end of if(rs4.getString("USED_BUFF_AMOUNT") != null)
					else{
						memberBufferVO.setUtilizedBufferAmtMember(new BigDecimal("0.00"));
					}//end of else
				}//end of while(rs4.next())				
				siInfoVO.setMemberBufferVO(memberBufferVO);
			}//end of if(rs4 != null)
			
			// added by rekha koc1289_1272
			if (rs5 == null ) {
                restorationPreauthClaimVO = new RestorationPreauthClaimVO();
			    restorationPreauthClaimVO.setRestorationYN("N");
			    restorationPreauthClaimVO.setRestSumInsured(new BigDecimal("0.00"));
			    log.info("RESTORATION_YN---------------------"+restorationPreauthClaimVO.getRestorationYN());
			    log.info("restoration---------------------"+restorationPreauthClaimVO.getRestSumInsured());
			    siInfoVO.setRestorationPreauthClaimVO(restorationPreauthClaimVO);
			} 
			else if(rs5 != null){
                if (rs5.next()){        
                restorationPreauthClaimVO = new RestorationPreauthClaimVO(); 
                if(rs5.getString("RESTORATION_YN") != null){
				restorationPreauthClaimVO.setRestorationYN(rs5.getString("RESTORATION_YN").trim());
				log.info("db---------------------"+rs5.getString("RESTORATION_YN"));
				}
				else{
					restorationPreauthClaimVO.setRestorationYN("N");
				log.info("RESTORATION_YN---------------------"+restorationPreauthClaimVO.getRestorationYN());
				}
				if(rs5.getString("MEM_SUM_INSURED") != null){
					
					restorationPreauthClaimVO.setRestSumInsured(new BigDecimal(rs5.getString("MEM_SUM_INSURED")));
				
					log.info("db2---------------------"+rs5.getString("MEM_SUM_INSURED"));
				}//end of if(rs5.getString("PAT_CLM_RECIEVED_AFTER") != null){
			}//end of while(rs5.next())	
				else
				{
                            restorationPreauthClaimVO = new RestorationPreauthClaimVO();
						    restorationPreauthClaimVO.setRestorationYN("N");
						    restorationPreauthClaimVO.setRestSumInsured(new BigDecimal("0.00"));
						    log.info("Null RESTORATION_YN---------------------"+restorationPreauthClaimVO.getRestorationYN());
						    log.info("Nullrestoration---------------------"+restorationPreauthClaimVO.getRestSumInsured());
                }
				
			siInfoVO.setRestorationPreauthClaimVO(restorationPreauthClaimVO);
			
		}//end of if(rs5 != null)
			// End added by rekha koc1289_1272
			//Added as per KOC 1216B Change Request	
			 
            
			//Added as per KOC 1216B Change Request			
			return siInfoVO;
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
			try // First try closing the fourth result set
			{ 
				try
				{
					if (rs5 != null) rs5.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the fourth Resultset in PreAuthDAOImpl getBalanceSIDetail()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally{
			
				try
				{
					if (rs4 != null) rs4.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the fourth Resultset in PreAuthDAOImpl getBalanceSIDetail()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if fourth result set is not closed, control reaches here. Try closing the third resultset now.
				{
					try{
						if (rs3 != null) rs3.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the third Resultset in PreAuthDAOImpl getBalanceSIDetail()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
					finally // Even if third result set is not closed, control reaches here. Try closing the second resultset now.
					{
						try{
							if (rs2 != null) rs2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Resultset in PreAuthDAOImpl getBalanceSIDetail()",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
						finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
						{
							try{
								if (rs1 != null) rs1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Second Resultset in PreAuthDAOImpl getBalanceSIDetail()",sqlExp);
								throw new TTKException(sqlExp, "preauth");
							}//end of catch (SQLException sqlExp)
							finally // Even if first result set is not closed, control reaches here. Try closing the statement now.
							{
								try
								{
									if (cStmtObject != null) cStmtObject.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Statement in PreAuthDAOImpl getBalanceSIDetail()",sqlExp);
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
										log.error("Error while closing the Connection in PreAuthDAOImpl getBalanceSIDetail()",sqlExp);
										throw new TTKException(sqlExp, "preauth");
									}//end of catch (SQLException sqlExp)
								}//end of finally Connection Close
							}//end of finally Statement Close
						} // end finally rs1
					} // end finally rs2
				}//end of finally rs3
			}//end of finally rs4
			}//end of try 
			
			catch (TTKException exp)
			{
				throw new TTKException(exp, "preauth");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{   rs5 = null;
				rs4 = null;
				rs3 = null;
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	

	}//end of getBalanceSIDetail(long lngMemSeqID,long lngPolicyGrpSeqID,long lngBalanceSeqID)
//Changes as Per Koc 1142  && KOC 1140Change Request
   /**
	 * This method returns the SIInfoVO, which contains CopayAdvicedDetails.
	  * @param lngPatGenDtlSeqID long value which contains member id to get the CopayAdvicedDetails
	 * @param lngMemSeqID long value which contains member id to get the CopayAdvicedDetails
	 * @param lngPolicyGrpSeqID long value which contains Policy Group Seq ID
	 * @param lngBalanceSeqID long value which contains Balance Seq ID
	 * @return BalanceCopayDeductionVO object which contains CopayAdvicedDetails
	 * @exception throws TTKException
	 */
	public BalanceCopayDeductionVO getcopayAdviced(String strIdentifier,long lngPatGenDtlSeqID,long lngMemSeqID,long lngPolicyGrpSeqID,long lngBalanceSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		
		SIInfoVO siInfoVO = new SIInfoVO();
		BalanceCopayDeductionVO balCopayDeducVO=null;
		try{
			conn = ResourceManager.getConnection();
			
			
			if(strIdentifier.equalsIgnoreCase("Pre-Authorization")){
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthCopayAdvice);
			cStmtObject.setLong(1,lngMemSeqID);
			cStmtObject.setLong(2,lngPolicyGrpSeqID);
			cStmtObject.setLong(3,lngBalanceSeqID);
			cStmtObject.setLong(4,lngPatGenDtlSeqID);
					
			cStmtObject.registerOutParameter(5, Types.DECIMAL);//ROWS_PROCESSED
			cStmtObject.registerOutParameter(6, Types.DECIMAL);//ROWS_PROCESSED
	
			cStmtObject.execute();
						
			
			 balCopayDeducVO=new BalanceCopayDeductionVO();
			
			 if(cStmtObject.getBigDecimal(5)!=null)
			 {
			balCopayDeducVO.setBdApprovedAmt(cStmtObject.getBigDecimal(5));
			 }
			 else {
			 balCopayDeducVO.setBdApprovedAmt(new BigDecimal("0.00"));
			 }
			
			 if(cStmtObject.getBigDecimal(6)!=null)
			 {
			balCopayDeducVO.setBdMaxCopayAmt(cStmtObject.getBigDecimal(6));
			 }
			 else {
				 balCopayDeducVO.setBdMaxCopayAmt(new BigDecimal("0.00"));
			 }
			}
			else if(strIdentifier.equalsIgnoreCase("Claims")){
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimsCopayAdvice);
			     cStmtObject.setLong(1,lngMemSeqID);
				cStmtObject.setLong(2,lngPolicyGrpSeqID);
				cStmtObject.setLong(3,lngBalanceSeqID);
				cStmtObject.setLong(4,lngPatGenDtlSeqID);
				cStmtObject.registerOutParameter(5, Types.DECIMAL);//ROWS_PROCESSED
				cStmtObject.registerOutParameter(6, Types.DECIMAL);//ROWS_PROCESSED
				cStmtObject.execute();
			    balCopayDeducVO=new BalanceCopayDeductionVO();
				 if(cStmtObject.getBigDecimal(5)!=null)
				 {
				balCopayDeducVO.setBdApprovedAmt(cStmtObject.getBigDecimal(5));
				 }
				 else {
				 balCopayDeducVO.setBdApprovedAmt(new BigDecimal("0.00"));
				 }
				
				 if(cStmtObject.getBigDecimal(6)!=null)
				 {
				balCopayDeducVO.setBdMaxCopayAmt(cStmtObject.getBigDecimal(6));
				 }
				 else {
					 balCopayDeducVO.setBdMaxCopayAmt(new BigDecimal("0.00"));
				 }
				 
			}
			//siInfoVO.setBalCopayDeducVO(balCopayDeducVO);
			return balCopayDeducVO;
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
        			log.error("Error while closing the Statement in PreAuthDAOImpl copayAdviced()",sqlExp);
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
        				log.error("Error while closing the Connection in PreAuthDAOImpl copayAdviced()",sqlExp);
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
	}//end of copayAdviced(long lngMemSeqID,long lngPolicyGrpSeqID,long lngBalanceSeqID)
   //Changes as Per Koc 1142 && 1140 Change Request and 1165
 /**bajaj
     * This method send  the intimation   to insurer 
     * @param authorizationVO AuthorizationVO contains Authorization information
     * @param strIdentifier Pre-Authorization/Claims
     * @return long int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int sendInsIntimate(AuthorizationVO authorizationVO,String strIdentifier) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			
			if(strIdentifier.equalsIgnoreCase("Pre-Authorization")){
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPatInsIntimate);
				if(authorizationVO.getPreAuthSeqID() != null){
					cStmtObject.setLong(1,authorizationVO.getPreAuthSeqID());
				}//end of if(authorizationVO.getPreAuthSeqID() != null)
				else{
					cStmtObject.setString(1,null);
				}//end of else

				if(authorizationVO.getStatusTypeID() != null){
					cStmtObject.setString(2,authorizationVO.getStatusTypeID());
				}//end of if(authorizationVO.getEnrollDtlSeqID() != null)
				else{
					cStmtObject.setString(2,null);
				}//end of else

				
				if(authorizationVO.getTotalSumInsured() != null){
					cStmtObject.setBigDecimal(3,authorizationVO.getTotalSumInsured());
				}//end of if(authorizationVO.getTotalSumInsured() != null)
				else{
					cStmtObject.setString(3,null);
				}//en of else
				cStmtObject.setString(4,"M");
				cStmtObject.execute();
				}//end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization"))
				
				else if(strIdentifier.equalsIgnoreCase("Claims")){
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimInsIntimate);
				if(authorizationVO.getClaimSeqID() != null){
					cStmtObject.setLong(1,authorizationVO.getClaimSeqID());
				}//end of if(authorizationVO.getClaimSeqID() != null)
				else{
					cStmtObject.setString(1,null);
				}//end of else
				if(authorizationVO.getStatusTypeID() != null){
					cStmtObject.setString(2,authorizationVO.getStatusTypeID());
				}//end of if(authorizationVO.getAssignUserSeqID() != null)
				else{
					cStmtObject.setString(2,null);
				}//end of else

				if(authorizationVO.getTotalSumInsured() != null){
					cStmtObject.setBigDecimal(3,authorizationVO.getTotalSumInsured());
				}//end of if(authorizationVO.getTotalSumInsured() != null)
				else{
					cStmtObject.setString(3,null);
				}//end of else
				cStmtObject.setString(4,"M");
				cStmtObject.execute();
				}//end of  else if(strIdentifier.equalsIgnoreCase("Claims"))
			
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
					log.error("Error while closing the Statement in PreAuthDAOImpl SendIntimation()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl SendIntimation()",sqlExp);
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
	}//end of SendInsIntimate(AuthorizationVO authorizationVO)


}//end of PreAuthDAOImpl