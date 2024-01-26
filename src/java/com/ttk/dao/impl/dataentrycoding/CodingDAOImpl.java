/**
 * @ (#) CodingDAOImpl.java Aug 27, 2007
 * Project 	     : TTK HealthCare Services
 * File          : CodingDAOImpl.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Aug 27, 2007
 *
 * @author       :  
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *                  
 */

package com.ttk.dao.impl.dataentrycoding;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.ttk.dto.claims.ClaimDetailVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.enrollment.PEDVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.ICDPCSVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthVO;

public class CodingDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(CodingDAOImpl.class );
	private static final String strPreAuthList = "{CALL CODING_PKG.SELECT_CODING_PREAUTH_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPreAuthCodeCleanList = "{CALL CODE_CLEANUP_PKG.SELECT_PREAUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPreAuthorization = "{CALL CODING_PKG.SELECT_CODING_PREAUTH(?,?,?)}";
	private static final String strGetCodeCleanupPreAuth = "{CALL CODE_CLEANUP_PKG.SELECT_PREAUTH(?,?,?)}";
	private static final String strGetClaimList = "{CALL CODING_PKG_DATA_ENTRY.SELECT_CODING_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	
	//added for KOC-decoupling
	private static final String strDiagnosisList = "SELECT D.DIAGNOSYS_SEQ_ID,D.DIAGNOSYS_NAME FROM TPA_DIAGNOSYS_DETAILS D WHERE D.CLAIM_SEQ_ID=?";
	                                            

	private static final String strGetClaimCodeCleanList = "{CALL CODE_CLEANUP_PKG.SELECT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetClaimDetail = "{CALL CODING_PKG_DATA_ENTRY.SELECT_CODING_CLAIM(?,?,?)}";
	private static final String strGetCodeCleanupClaimDetail = "{CALL CODE_CLEANUP_PKG.SELECT_CLAIM(?,?,?)}";
	private static final String strGetAilmentList = "{CALL CODING_PKG_DATA_ENTRY.SELECT_AILMENT_LIST(?,?,?)}";
	private static final String strGetCodeCleanupAilmentList = "{CALL CODE_CLEANUP_PKG.SELECT_AILMENT_LIST(?,?,?)}";
	private static final String strGetExactICDCode = "{CALL CODING_PKG.GET_EXACT_ICD(?,?,?)}";
	private static final String strGetExactProcedure = "{CALL CODING_PKG.GET_EXACT_PROCEDURE(?,?,?)}";
	private static final String strSavePreauthReview = "{CALL CODE_CLEANUP_PKG.PAT_SET_REVIEW(?,?,?,?,?,?,?,?)}";
	private static final String strSaveClaimReview = "{CALL CODE_CLEANUP_PKG.claim_set_review(?,?,?,?,?,?,?,?)}";
	private static final String strSaveICDPackage = "{CALL CODE_CLEANUP_PKG.SAVE_ICD_PCS_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteCodecleanupICD = "{CALL CODE_CLEANUP_PKG.DELETE_CLEANUP_ICD(?,?,?)}";
	private static final String strSaveCodingPED = "{CALL CODING_PKG.SAVE_CODING_PED(?,?,?,?,?,?,?,?,?)}";
	
	//added for CR KOC-Decoupling
	private static final String strDataEntryPromote = "{CALL CLAIMS_PKG_DATA_ENTRY.claims_promote(?,?,?)}";	
	private static final String strDataEntryRevert = "{CALL CLAIMS_PKG_DATA_ENTRY.claims_revert(?,?,?)}";
	//ended
	
	
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
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));			
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(11,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			
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
					
					preAuthVO.setDMSRefID(TTKCommon.checkNull(rs.getString("PRE_AUTH_DMS_REFERENCE_ID")));
					if(rs.getString("MEMBER_SEQ_ID") != null){
						preAuthVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
					
					if(rs.getString("assigned_yn").equals("Y")){
						preAuthVO.setAssignedImageName("AlreadyAssignedIcon");
						preAuthVO.setAssignedImageTitle("Assigned");
						preAuthVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
						
						if(rs.getString("ASSIGN_USERS_SEQ_ID") != null){
							preAuthVO.setAssignUserSeqID(new Long(rs.getLong("ASSIGN_USERS_SEQ_ID")));
						}//end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)	
					}//end of if(rs.getString("assigned_yn").equals("N"))
					else if(rs.getString("assigned_yn").equals("N")){
						preAuthVO.setAssignedTo("");
						preAuthVO.setAssignUserSeqID(null);
					}//end of else
					
					if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("HIG")){
						preAuthVO.setPriorityImageName("HighPriorityIcon");
						preAuthVO.setPriorityImageTitle("High Priority");
					}//end of if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("HIG"))

					if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("LOW")){
						preAuthVO.setPriorityImageName("LowPriorityIcon");
						preAuthVO.setPriorityImageTitle("Low Priority");
					}//end of if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("LOW"))
					
					if(rs.getString("TPA_OFFICE_SEQ_ID") != null){
						preAuthVO.setOfficeSeqID(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					}//end of if(rs.getString("TPA_OFFICE_SEQ_ID") != null)
					
                    alResultList.add(preAuthVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Resultset in CodingDAOImpl getPreAuthList()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getPreAuthList()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getPreAuthList()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
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
     * This method returns the Arraylist of PreAuthVO's  which contains Pre-Authorization details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Pre-Authorization details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthCodeCleanList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthVO preAuthVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthCodeCleanList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0)); //PRE_AUTH_NUMBER
			cStmtObject.setString(2,(String)alSearchCriteria.get(1)); //TPA_ENROLLMENT_ID
			cStmtObject.setString(3,(String)alSearchCriteria.get(2)); //TPA_OFFICE_SEQ_ID
			cStmtObject.setString(4,(String)alSearchCriteria.get(3)); //EVENT_SEQ_ID
			cStmtObject.setString(5,(String)alSearchCriteria.get(4)); //RECEIVED_AFTER
			cStmtObject.setString(6,(String)alSearchCriteria.get(5)); //RECEIVED_BEFORE
			cStmtObject.setString(7,(String)alSearchCriteria.get(7)); //SORT_VAR
			cStmtObject.setString(8,(String)alSearchCriteria.get(8)); //SORT_ORDER
			cStmtObject.setString(9,(String)alSearchCriteria.get(9)); //START_NUM
			cStmtObject.setString(10,(String)alSearchCriteria.get(10)); //END_NUM
			cStmtObject.setLong(11,(Long)alSearchCriteria.get(6));      //ADDED_BY
			cStmtObject.registerOutParameter(12,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(12);
			
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
					preAuthVO.setDMSRefID(TTKCommon.checkNull(rs.getString("PRE_AUTH_DMS_REFERENCE_ID")));
					if(rs.getString("MEMBER_SEQ_ID") != null){
						preAuthVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
					if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("HIG")){
						preAuthVO.setPriorityImageName("HighPriorityIcon");
						preAuthVO.setPriorityImageTitle("High Priority");
					}//end of if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("HIG"))

					if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("LOW")){
						preAuthVO.setPriorityImageName("LowPriorityIcon");
						preAuthVO.setPriorityImageTitle("Low Priority");
					}//end of if(rs.getString("PAT_PRIORITY_GENERAL_TYPE_ID").equals("LOW"))
                    alResultList.add(preAuthVO);
				}//end of while(rs.next())
			}//end of if(rs != null)			
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Resultset in CodingDAOImpl getPreAuthCodeCleanList()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getPreAuthCodeCleanList()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getPreAuthCodeCleanList()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPreAuthCodeCleanList(ArrayList alSearchCriteria)
    
    /**
     * This method returns the PreAuthDetailVO, which contains all the Pre-Authorization details
     * @param lngPATEnrollDtlSeqID PATEnrollDtlSeqID
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Pre-Authorization details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getPreAuthDetail(long lngPATEnrollDtlSeqID,long lngUserSeqID) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	PreAuthDetailVO preAuthDetailVO = null;
    	ClaimantVO claimantVO = null;
    	
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthorization);

			cStmtObject.setLong(1,lngPATEnrollDtlSeqID);			
			cStmtObject.setLong(2,lngUserSeqID);			
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					preAuthDetailVO = new PreAuthDetailVO();
					claimantVO = new ClaimantVO();
					if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
						preAuthDetailVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)
					if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						preAuthDetailVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)
					preAuthDetailVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
					preAuthDetailVO.setPreauthTypeDesc(TTKCommon.checkNull(rs.getString("preauth_type")));
					if(rs.getString("PAT_RECEIVED_DATE") != null){
						preAuthDetailVO.setReceivedDate(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime()));				
					}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)
					if(rs.getString("DATE_OF_HOSPITALIZATION") != null){
						preAuthDetailVO.setAdmissionDate(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime()));				
					}//end of if(rs.getString("DATE_OF_HOSPITALIZATION") != null)
					if(rs.getString("PREV_APPROVED_AMOUNT") != null){
						preAuthDetailVO.setPrevApprovedAmount(new BigDecimal(rs.getString("PREV_APPROVED_AMOUNT")));
					}//end of if(rs.getString("PREV_APPROVED_AMOUNT") != null)
					/*if(rs.getString("PAT_REQUESTED_AMOUNT") != null){
						preAuthDetailVO.setRequestAmount(new BigDecimal(rs.getString("PAT_REQUESTED_AMOUNT")));
					}//end of if(rs.getString("PAT_REQUESTED_AMOUNT") != null)					
*/					preAuthDetailVO.setDoctorName(TTKCommon.checkNull(rs.getString("TREATING_DR_NAME")));
					preAuthDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					preAuthDetailVO.setHospitalPhone(TTKCommon.checkNull(rs.getString("PHONE_NO_IN_HOSPITALISATION")));
					preAuthDetailVO.setOfficeName(TTKCommon.checkNull(rs.getString("TTK_BRANCH")));
					
					if(rs.getString("ASSIGNED_YN").equals("Y")){
						preAuthDetailVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
						preAuthDetailVO.setProcessingBranch(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					}//end of if(rs.getString("ASSIGNED_YN").equals("Y"))
					else if(rs.getString("ASSIGNED_YN").equals("N")){
						preAuthDetailVO.setAssignedTo("");
						preAuthDetailVO.setProcessingBranch("");
					}//end of else
					
					preAuthDetailVO.setPreAuthRecvTypeID(TTKCommon.checkNull(rs.getString("PAT_RCVD_THRU_GENERAL_TYPE_ID")));
					preAuthDetailVO.setPriorityTypeID(TTKCommon.checkNull(rs.getString("priority")));					
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
					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)
					//claimantVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
					if(rs.getString("DATE_OF_INCEPTION") != null){
						claimantVO.setDateOfInception(new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()));
					}//end of if(rs.getString("DATE_OF_INCEPTION") != null)
					if(rs.getString("DATE_OF_EXIT") != null){
						claimantVO.setDateOfExit(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
					}//end of if(rs.getString("DATE_OF_EXIT") != null)
					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					claimantVO.setGenderDescription(TTKCommon.checkNull(rs.getString("gender")));
					claimantVO.setCategoryDesc(TTKCommon.checkNull(rs.getString("CATEGORY")));	
					preAuthDetailVO.setClaimantVO(claimantVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return preAuthDetailVO;
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Resultset in CodingDAOImpl getPreAuthDetail()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getPreAuthDetail()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getPreAuthDetail()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
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
     * This method returns the PreAuthDetailVO, which contains all the Pre-Authorization details
     * @param lngPATEnrollDtlSeqID PATEnrollDtlSeqID
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Pre-Authorization details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getCodeCleanupPreAuthDetail(long lngPATEnrollDtlSeqID,long lngUserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	PreAuthDetailVO preAuthDetailVO = null;
    	ClaimantVO claimantVO = null;
    	
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetCodeCleanupPreAuth);

			cStmtObject.setLong(1,lngPATEnrollDtlSeqID);			
			cStmtObject.setLong(2,lngUserSeqID);			
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					preAuthDetailVO = new PreAuthDetailVO();
					claimantVO = new ClaimantVO();
					if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null){
						preAuthDetailVO.setEnrollDtlSeqID(new Long(rs.getLong("PAT_ENROLL_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_ENROLL_DETAIL_SEQ_ID") != null)
					if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null){
						preAuthDetailVO.setPreAuthSeqID(new Long(rs.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}//end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)
					preAuthDetailVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
					preAuthDetailVO.setPreauthTypeDesc(TTKCommon.checkNull(rs.getString("preauth_type")));
					if(rs.getString("PAT_RECEIVED_DATE") != null){
						preAuthDetailVO.setReceivedDate(new Date(rs.getTimestamp("PAT_RECEIVED_DATE").getTime()));				
					}//end of if(rs.getString("PAT_RECEIVED_DATE") != null)
					if(rs.getString("DATE_OF_HOSPITALIZATION") != null){
						preAuthDetailVO.setAdmissionDate(new Date(rs.getTimestamp("DATE_OF_HOSPITALIZATION").getTime()));				
					}//end of if(rs.getString("DATE_OF_HOSPITALIZATION") != null)
					if(rs.getString("PREV_APPROVED_AMOUNT") != null){
						preAuthDetailVO.setPrevApprovedAmount(new BigDecimal(rs.getString("PREV_APPROVED_AMOUNT")));
					}//end of if(rs.getString("PREV_APPROVED_AMOUNT") != null)
					/*if(rs.getString("PAT_REQUESTED_AMOUNT") != null){
						preAuthDetailVO.setRequestAmount(new BigDecimal(rs.getString("PAT_REQUESTED_AMOUNT")));
					}//end of if(rs.getString("PAT_REQUESTED_AMOUNT") != null)					
*/					preAuthDetailVO.setDoctorName(TTKCommon.checkNull(rs.getString("TREATING_DR_NAME")));
					preAuthDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					preAuthDetailVO.setHospitalPhone(TTKCommon.checkNull(rs.getString("PHONE_NO_IN_HOSPITALISATION")));
					preAuthDetailVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					preAuthDetailVO.setOfficeName(TTKCommon.checkNull(rs.getString("TTK_BRANCH")));
					preAuthDetailVO.setProcessingBranch(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					preAuthDetailVO.setPreAuthRecvTypeID(TTKCommon.checkNull(rs.getString("PAT_RCVD_THRU_GENERAL_TYPE_ID")));
					preAuthDetailVO.setPriorityTypeID(TTKCommon.checkNull(rs.getString("priority")));					
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
					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)
					//claimantVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
					if(rs.getString("DATE_OF_INCEPTION") != null){
						claimantVO.setDateOfInception(new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()));
					}//end of if(rs.getString("DATE_OF_INCEPTION") != null)
					if(rs.getString("DATE_OF_EXIT") != null){
						claimantVO.setDateOfExit(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
					}//end of if(rs.getString("DATE_OF_EXIT") != null)
					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					claimantVO.setGenderDescription(TTKCommon.checkNull(rs.getString("gender")));
					claimantVO.setCategoryDesc(TTKCommon.checkNull(rs.getString("CATEGORY")));	
					preAuthDetailVO.setClaimantVO(claimantVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return preAuthDetailVO;
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Resultset in CodingDAOImpl getCodeCleanupPreAuthDetail()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getCodeCleanupPreAuthDetail()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getCodeCleanupPreAuthDetail()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getCodeCleanupPreAuthDetail(long lngPATEnrollDtlSeqID,long lngUserSeqID)
    
    /**
	 * This method returns the Arraylist of PreAuthVO's  which contains the details of Claim details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains the details of Claim
	 * @exception throws TTKException
	 */
	public ArrayList getClaimList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;		
		CallableStatement cStmtObject=null;
		PreAuthVO preauthVO = null;
		ResultSet rs = null;		
        try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(11,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			if(rs != null){
				while (rs.next()) {
					 preauthVO = new PreAuthVO();
	                    if(rs.getString("CLAIM_SEQ_ID") != null){
	                        preauthVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
	                    }//end of if(rs.getString("CLAIM_SEQ_ID") != null)
	                    preauthVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
	                    preauthVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));	                    
	                    preauthVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
	                    preauthVO.setClaimantName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));	                    
	                    
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
	
	                    if(rs.getString("MEMBER_SEQ_ID") != null){
	                    	preauthVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
						}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
	                    
	                    if(rs.getString("ASSIGNED_YN").equals("Y")){
	                    	preauthVO.setAssignedImageName("AlreadyAssignedIcon");
	                    	preauthVO.setAssignedImageTitle("Assigned");
	                    	preauthVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
	                    	
	                    	if(rs.getString("ASSIGN_USERS_SEQ_ID") != null){
		                        preauthVO.setAssignUserSeqID(new Long(rs.getLong("ASSIGN_USERS_SEQ_ID")));
		                    }//end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)
						}//end of if(rs.getString("ASSIGNED_YN").equals("N"))
	                    else if(rs.getString("ASSIGNED_YN").equals("N")){
	                    	preauthVO.setAssignedTo("");
	                    	preauthVO.setAssignUserSeqID(null);
	                    }//end of else	                    
	                    preauthVO.setAmmendmentYN(TTKCommon.checkNull(rs.getString("AMMENDMENT_YN")));	                    
	                    //added for CR KOC-Decoupling
	                    preauthVO.setStatus(TTKCommon.checkNull(rs.getString("Status")));	                    
	                    alResultList.add(preauthVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Resultset in CodingDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getClaimList()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getClaimList()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimList(ArrayList alSearchCriteria)
	
	
	//added for KOC-Decoupling
	public ArrayList getDiagnosisList(Long claimSeqId) throws TTKException
	{
		Connection conn = null;
		PreparedStatement pStmt = null;
		ArrayList<Object> alDiagnosis = new ArrayList<Object>();
		ResultSet rs = null;
		CacheObject cacheObject = null;
		try
		{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strDiagnosisList);
			pStmt.setLong(1,claimSeqId);
			rs = pStmt.executeQuery();
			if(rs!= null)
			{
				while(rs.next())
				{
					cacheObject = new CacheObject();
					cacheObject.setCacheId(rs.getString("DIAGNOSYS_SEQ_ID"));// DIAGNOSYS_TYPE
					cacheObject.setCacheDesc(rs.getString("DIAGNOSYS_NAME"));
					alDiagnosis.add(cacheObject);
				}
			}
			return alDiagnosis;			
		}
		catch (SQLException sqlExp) 
        {
            throw new TTKException(sqlExp, "coding");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp) 
        {
            throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Resultset in CodingDAOImpl getDiagnosisList()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getDiagnosisList()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getDiagnosisList()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
   	}
	
	//ended
	
	/**
     * This method returns the Arraylist of PreAuthVO's  which contains Claim details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthVO object which contains Claim details
     * @exception throws TTKException
     */
    public ArrayList getClaimCodeCleanList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;		
		CallableStatement cStmtObject=null;
		PreAuthVO preauthVO = null;
		ResultSet rs = null;		
        try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimCodeCleanList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));  //CLAIM_NUMBER
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));  //TPA_ENROLLMENT_ID
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));  //TPA_OFFICE_SEQ_ID
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));  //EVENT_SEQ_ID
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));  //RECEIVED_DATE
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));  //RECEIVED_DATE
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));  //SORT_VAR
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));  //SORT_ORDER
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));  //START_NUM
			cStmtObject.setString(10,(String)alSearchCriteria.get(10)); //END_NUM
			cStmtObject.setLong(11,(Long)alSearchCriteria.get(6));//ADDED_BY
			cStmtObject.registerOutParameter(12,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(12);
			if(rs != null){
				while (rs.next()) {
					 preauthVO = new PreAuthVO();
	                    if(rs.getString("CLAIM_SEQ_ID") != null){
	                        preauthVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
	                    }//end of if(rs.getString("CLAIM_SEQ_ID") != null)
	                    preauthVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
	                    preauthVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));	                    
	                    preauthVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
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
	
	                    if(rs.getString("MEMBER_SEQ_ID") != null){
	                    	preauthVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
						}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
	                    
	                    preauthVO.setAmmendmentYN(TTKCommon.checkNull(rs.getString("AMMENDMENT_YN")));
	                    alResultList.add(preauthVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Resultset in CodingDAOImpl getClaimCodeCleanList()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getClaimCodeCleanList()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getClaimCodeCleanList()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getClaimCodeCleanList(ArrayList alSearchCriteria)
	
	/**
     * This method returns the PreAuthDetailVO, which contains all the Claim details
     * @param alClaimList contains Claim seq id,Member Seq ID to get the Claim Details
     * @return PreAuthDetailVO object which contains all the Claim details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getClaimDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	PreAuthDetailVO preauthDetailVO = null;    	
    	ClaimantVO claimantVO = null;
    	ClaimDetailVO claimDetailVO = null;    	
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimDetail);
			cStmtObject.setLong(1,lngClaimSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					preauthDetailVO = new PreAuthDetailVO();
					claimantVO = new ClaimantVO();
					claimDetailVO = new ClaimDetailVO();

					if(rs.getString("CLAIM_SEQ_ID") != null){
						preauthDetailVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)
					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					claimantVO.setGenderDescription(TTKCommon.checkNull(rs.getString("GENDER")));
					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)					
					if(rs.getString("DATE_OF_INCEPTION") != null){
						claimantVO.setDateOfInception(new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()));
					}//end of if(rs.getString("DATE_OF_INCEPTION") != null)
					if(rs.getString("DATE_OF_EXIT") != null){
						claimantVO.setDateOfExit(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
					}//end of if(rs.getString("DATE_OF_EXIT") != null)
					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
					claimantVO.setEmployeeName(TTKCommon.checkNull(rs.getString("EMPLOYEE_NAME")));
					claimantVO.setRelationDesc(TTKCommon.checkNull(rs.getString("RELATIONSHIP")));					
					claimantVO.setClaimantPhoneNbr(TTKCommon.checkNull(rs.getString("CLAIMANT_PHONE_NUMBER")));					
					preauthDetailVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					claimDetailVO.setClaimFileNbr(TTKCommon.checkNull(rs.getString("CLAIM_FILE_NUMBER")));
					claimDetailVO.setRequestTypeDesc(TTKCommon.checkNull(rs.getString("REQUEST_TYPE")));
					preauthDetailVO.setPrevClaimNbr(TTKCommon.checkNull(rs.getString("PREV_CLAIM_NUMBER")));
					claimDetailVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
					claimDetailVO.setClaimSubTypeID(TTKCommon.checkNull(rs.getString("SUB_TYPE")));					
					if(rs.getString("INTIMATION_DATE") != null){
						claimDetailVO.setIntimationDate(new Date(rs.getTimestamp("INTIMATION_DATE").getTime()));
					}//end of if(rs.getString("INTIMATION_DATE") != null)
					claimDetailVO.setModeTypeID(TTKCommon.checkNull(rs.getString("MODE_GENERAL_TYPE_ID")));
					claimDetailVO.setModeTypeDesc(TTKCommon.checkNull(rs.getString("MODE_TYPE")));
					if(rs.getString("RCVD_DATE") != null){
						claimDetailVO.setClaimReceivedDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
					}//end of if(rs.getString("RCVD_DATE") != null)
					if(rs.getString("REQUESTED_AMOUNT") != null){
						preauthDetailVO.setClaimRequestAmount(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
					}//end of if(rs.getString("REQUESTED_AMOUNT") != null)
					preauthDetailVO.setDoctorName(TTKCommon.checkNull(rs.getString("TREATING_DR_NAME")));
					claimDetailVO.setInPatientNbr(TTKCommon.checkNull(rs.getString("IN_PATIENT_NO")));
					preauthDetailVO.setOfficeName(TTKCommon.checkNull(rs.getString("TTK_BRANCH")));
					claimDetailVO.setSourceDesc(TTKCommon.checkNull(rs.getString("SOURCE_TYPE")));
					
					if(rs.getString("ASSIGNED_YN").equals("Y")){
						preauthDetailVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
						preauthDetailVO.setProcessingBranch(TTKCommon.checkNull(rs.getString("PROCESSING_BRANCH")));
					}//end of if(rs.getString("ASSIGNED_YN").equals("Y"))
					else if(rs.getString("ASSIGNED_YN").equals("N")){
						preauthDetailVO.setAssignedTo("");
						preauthDetailVO.setProcessingBranch("");
					}//end of else
					
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
					claimDetailVO.setClaimRemarks(TTKCommon.checkNull(rs.getString("CLAIMS_REMARKS")));
					preauthDetailVO.setClaimDetailVO(claimDetailVO);
					preauthDetailVO.setClaimantVO(claimantVO);	
					
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
    	catch (SQLException sqlExp)
		{
    		sqlExp.printStackTrace();
    		throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Resultset in CodingDAOImpl getClaimDetail()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getClaimDetail()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getClaimDetail()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
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
     * This method returns the PreAuthDetailVO, which contains all the Claim details
     * @param alClaimList contains Claim seq id
     * @param lngUserSeqID long value which contains Logged-in User
     * @return PreAuthDetailVO object which contains all the Claim details
     * @exception throws TTKException
     */
    public PreAuthDetailVO getCodeCleanupClaimDetail(long lngClaimSeqID,long lngUserSeqID) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	PreAuthDetailVO preauthDetailVO = null;    	
    	ClaimantVO claimantVO = null;
    	ClaimDetailVO claimDetailVO = null;    	
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetCodeCleanupClaimDetail);
			cStmtObject.setLong(1,lngClaimSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					preauthDetailVO = new PreAuthDetailVO();
					claimantVO = new ClaimantVO();
					claimDetailVO = new ClaimDetailVO();

					if(rs.getString("CLAIM_SEQ_ID") != null){
						preauthDetailVO.setClaimSeqID(new Long(rs.getLong("CLAIM_SEQ_ID")));
					}//end of if(rs.getString("CLAIM_SEQ_ID") != null)
					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					claimantVO.setGenderDescription(TTKCommon.checkNull(rs.getString("GENDER")));
					if(rs.getString("MEM_AGE") != null){
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)					
					if(rs.getString("DATE_OF_INCEPTION") != null){
						claimantVO.setDateOfInception(new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()));
					}//end of if(rs.getString("DATE_OF_INCEPTION") != null)
					if(rs.getString("DATE_OF_EXIT") != null){
						claimantVO.setDateOfExit(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
					}//end of if(rs.getString("DATE_OF_EXIT") != null)
					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
					claimantVO.setEmployeeName(TTKCommon.checkNull(rs.getString("EMPLOYEE_NAME")));
					claimantVO.setRelationDesc(TTKCommon.checkNull(rs.getString("RELATIONSHIP")));					
					claimantVO.setClaimantPhoneNbr(TTKCommon.checkNull(rs.getString("CLAIMANT_PHONE_NUMBER")));					
					preauthDetailVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					claimDetailVO.setClaimFileNbr(TTKCommon.checkNull(rs.getString("CLAIM_FILE_NUMBER")));
					claimDetailVO.setRequestTypeDesc(TTKCommon.checkNull(rs.getString("REQUEST_TYPE")));
					preauthDetailVO.setPrevClaimNbr(TTKCommon.checkNull(rs.getString("PREV_CLAIM_NUMBER")));
					claimDetailVO.setClaimTypeDesc(TTKCommon.checkNull(rs.getString("CLAIM_TYPE")));
					claimDetailVO.setClaimSubTypeID(TTKCommon.checkNull(rs.getString("SUB_TYPE")));					
					if(rs.getString("INTIMATION_DATE") != null){
						claimDetailVO.setIntimationDate(new Date(rs.getTimestamp("INTIMATION_DATE").getTime()));
					}//end of if(rs.getString("INTIMATION_DATE") != null)
					claimDetailVO.setModeTypeID(TTKCommon.checkNull(rs.getString("MODE_GENERAL_TYPE_ID")));
					claimDetailVO.setModeTypeDesc(TTKCommon.checkNull(rs.getString("MODE_TYPE")));
					if(rs.getString("RCVD_DATE") != null){
						claimDetailVO.setClaimReceivedDate(new Date(rs.getTimestamp("RCVD_DATE").getTime()));
					}//end of if(rs.getString("RCVD_DATE") != null)
					if(rs.getString("REQUESTED_AMOUNT") != null){
						preauthDetailVO.setClaimRequestAmount(new BigDecimal(rs.getString("REQUESTED_AMOUNT")));
					}//end of if(rs.getString("REQUESTED_AMOUNT") != null)
					preauthDetailVO.setDoctorName(TTKCommon.checkNull(rs.getString("TREATING_DR_NAME")));
					claimDetailVO.setInPatientNbr(TTKCommon.checkNull(rs.getString("IN_PATIENT_NO")));
					preauthDetailVO.setOfficeName(TTKCommon.checkNull(rs.getString("TTK_BRANCH")));
					claimDetailVO.setSourceDesc(TTKCommon.checkNull(rs.getString("SOURCE_TYPE")));
					preauthDetailVO.setAssignedTo(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					preauthDetailVO.setProcessingBranch(TTKCommon.checkNull(rs.getString("PROCESSING_BRANCH")));	
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
					claimDetailVO.setClaimRemarks(TTKCommon.checkNull(rs.getString("CLAIMS_REMARKS")));
					preauthDetailVO.setClaimDetailVO(claimDetailVO);
					preauthDetailVO.setClaimantVO(claimantVO);	
					
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
    	catch (SQLException sqlExp)
		{
    		sqlExp.printStackTrace();
    		throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Resultset in CodingDAOImpl getCodeCleanupClaimDetail()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getCodeCleanupClaimDetail()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getCodeCleanupClaimDetail()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return preauthDetailVO;
    }//end of getCodeCleanupClaimDetail(long lngClaimSeqID,long lngUserSeqID)
    
    /**
	 * This method returns the ArrayList, which contains the ICDPCSVO's which are populated from the database
	 * @param alAilmentList ArrayList object which contains preauth Seq ID/Claim Seq ID
	 * @return ArrayList of ICDPCSVO'S object's which contains Ailment Details
	 * @exception throws TTKException
	 */
	public ArrayList getAilmentList(ArrayList alAilmentList) throws TTKException {
		ArrayList<Object> alICDpackageList = new ArrayList<Object>();
		ICDPCSVO icdPCSVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetAilmentList);
            
            if(alAilmentList.get(0) != null){
            	cStmtObject.setLong(1,(Long)alAilmentList.get(0));
            }//end of if(alAilmentList.get(0) != null)
            else{
            	cStmtObject.setString(1,null);
            }//end of else
            
            if(alAilmentList.get(1) != null){
            	cStmtObject.setLong(2,(Long)alAilmentList.get(1));
            }//end of if(alAilmentList.get(1) != null)
            else{
            	cStmtObject.setString(2,null);
            }//end of else
            
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            if(rs != null){
            	while(rs.next()){
            		icdPCSVO = new ICDPCSVO();
            		
            		if(rs.getString("ICD_PCS_SEQ_ID") != null){
            			icdPCSVO.setPEDSeqID(new Long(rs.getLong("ICD_PCS_SEQ_ID")));
            		}//end of if(rs.getString("ICD_PCS_SEQ_ID") != null)
            		
            		if(rs.getString("PED_CODE_ID") != null){
            			icdPCSVO.setPEDCodeID(new Long(rs.getLong("PED_CODE_ID")));
            		}//end of if(rs.getString("PED_CODE_ID") != null)
            		
            		icdPCSVO.setDescription(TTKCommon.checkNull(rs.getString("PED_DESCRIPTION")));
            		icdPCSVO.setICDCode(TTKCommon.checkNull(rs.getString("ICD_CODE")));
					icdPCSVO.setProcCodes(TTKCommon.checkNull(rs.getString("PROC_CODES")));
					//added for KOC-Decoupling
					icdPCSVO.setDiagnosisType(TTKCommon.checkNull(rs.getString("DIAGNOSYS_NAME")));
					icdPCSVO.setDiagnType(TTKCommon.checkNull(rs.getString("DIAG_TYPE")));
					
					
					icdPCSVO.setDeleteName("DeleteIcon");
					icdPCSVO.setDeleteTitle("Delete");
					//added for CR KOC-Decoupling
					icdPCSVO.setCodCompleteYN(TTKCommon.checkNull(rs.getString("COD_COMPLETE_YN")));
					alICDpackageList.add(icdPCSVO);
            	}//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "coding");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "coding");
        }//end of catch (Exception exp)
        finally
		{
			 //Nested Try Catch to ensure resource closure 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in CodingDAOImpl getAilmentList()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getAilmentList()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getAilmentList()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alICDpackageList;
	}//end of getAilmentList(ArrayList alAilmentList)
	
	
	//saveDataEntryPromote
	/*
	 * Saves Promote status
	 * 
	 */
	public long saveDataEntryPromote(long lngClaimsSeqId,long userSeqId) throws TTKException
	{
		
		Connection conn = null;
		CallableStatement cStmtObject=null;
        ResultSet rs = null;
        long rowProcessed = 0;
        
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDataEntryPromote);
            cStmtObject.setLong(1,lngClaimsSeqId);
            cStmtObject.setString(2,"C");
            cStmtObject.registerOutParameter(3,Types.INTEGER);
            
            cStmtObject.execute();            
            rowProcessed =  cStmtObject.getInt(3);	
        }
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "coding");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "coding");
        }//end of catch (Exception exp)
        finally
		{
			 //Nested Try Catch to ensure resource closure 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in CodingDAOImpl saveDataEntryPromote()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl saveDataEntryPromote()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl saveDataEntryPromote()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return rowProcessed;
	}
	
	//added for CR KOC-Decoupling
	/*
	 * Saves Promote status
	 * 
	 */
	public long saveDataEntryRevert(long lngClaimsSeqId,long userSeqId) throws TTKException
	{
		
		Connection conn = null;
		CallableStatement cStmtObject=null;
        ResultSet rs = null;
        long rowProcessed = 0;
        
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDataEntryRevert);
            cStmtObject.setLong(1,lngClaimsSeqId);
            cStmtObject.setString(2,"C");
            cStmtObject.registerOutParameter(3,Types.INTEGER);
            
            cStmtObject.execute();            
            rowProcessed =  cStmtObject.getInt(3);	
        }
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "coding");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "coding");
        }//end of catch (Exception exp)
        finally
		{
			 //Nested Try Catch to ensure resource closure 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in CodingDAOImpl saveDataEntryRevert()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl saveDataEntryRevert()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl saveDataEntryRevert()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return rowProcessed;
	}	
	//ended
    
	/**
	 * This method returns the ArrayList, which contains the ICDPCSVO's which are populated from the database
	 * @param alAilmentList ArrayList object which contains preauth Seq ID/Claim Seq ID
	 * @return ArrayList of ICDPCSVO'S object's which contains Ailment Details
	 * @exception throws TTKException
	 */
	public ArrayList getCodeCleanupAilmentList(ArrayList alAilmentList) throws TTKException {
		ArrayList<Object> alICDpackageList = new ArrayList<Object>();
		ICDPCSVO icdPCSVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetCodeCleanupAilmentList);
            
            if(alAilmentList.get(0) != null){
            	cStmtObject.setLong(1,(Long)alAilmentList.get(0));
            }//end of if(alAilmentList.get(0) != null)
            else{
            	cStmtObject.setString(1,null);
            }//end of else
            
            if(alAilmentList.get(1) != null){
            	cStmtObject.setLong(2,(Long)alAilmentList.get(1));
            }//end of if(alAilmentList.get(1) != null)
            else{
            	cStmtObject.setString(2,null);
            }//end of else
            
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            if(rs != null){
            	while(rs.next()){
            		icdPCSVO = new ICDPCSVO();
            		
            		if(rs.getString("ICD_PCS_SEQ_ID") != null){
            			icdPCSVO.setPEDSeqID(new Long(rs.getLong("ICD_PCS_SEQ_ID")));
            		}//end of if(rs.getString("ICD_PCS_SEQ_ID") != null)
            		
            		if(rs.getString("PED_CODE_ID") != null){
            			icdPCSVO.setPEDCodeID(new Long(rs.getLong("PED_CODE_ID")));
            		}//end of if(rs.getString("PED_CODE_ID") != null)
            		
            		icdPCSVO.setDescription(TTKCommon.checkNull(rs.getString("PED_DESCRIPTION")));
            		icdPCSVO.setICDCode(TTKCommon.checkNull(rs.getString("ICD_CODE")));
					icdPCSVO.setProcCodes(TTKCommon.checkNull(rs.getString("PROC_CODES")));
					
					if(rs.getString("ALLOW_DELETE_YN").equals("Y")){
						icdPCSVO.setDeleteName("DeleteIcon");
						icdPCSVO.setDeleteTitle("Delete");
					}//end of if(rs.getString("ALLOW_DELETE_YN").equals("Y"))
					
					alICDpackageList.add(icdPCSVO);
            	}//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "coding");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "coding");
        }//end of catch (Exception exp)
        finally
		{
			 //Nested Try Catch to ensure resource closure 
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in CodingDAOImpl getAilmentList()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CodingDAOImpl getAilmentList()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CodingDAOImpl getAilmentList()",sqlExp);
							throw new TTKException(sqlExp, "coding");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alICDpackageList;
	}//end of getCodeCleanupAilmentList(ArrayList alAilmentList)
	
	/**
     * This method gets the ICD Code information
     * @param strICDCode String object which contains ICD Details
     * @return Object[] the values ,of PEDCodeId,PED Description
     * @exception throws TTKException
     */
	public Object[] getExactICD(String strICDCode) throws TTKException {
		Object[] objArrayResult = new Object[2];
		Connection conn = null;
    	CallableStatement cStmtObject=null;
    	long lngPEDCodeID = 0;
    	String strPEDDescription = "";
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetExactICDCode);
			
			cStmtObject.setString(1,strICDCode);
			cStmtObject.registerOutParameter(2,Types.VARCHAR);
			cStmtObject.registerOutParameter(3,Types.BIGINT);
			cStmtObject.execute();
			strPEDDescription = cStmtObject.getString(2);
			lngPEDCodeID = cStmtObject.getLong(3);
			objArrayResult[0] = new String(strPEDDescription);
			objArrayResult[1] = new Long(lngPEDCodeID);
		}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
        			log.error("Error while closing the Statement in CodingDAOImpl getExactICD()",sqlExp);
        			throw new TTKException(sqlExp, "coding");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CodingDAOImpl getExactICD()",sqlExp);
        				throw new TTKException(sqlExp, "coding");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "coding");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return objArrayResult;
	}//end of getExactICD(String strICDCode)
	
	/**
     * This method gets the Procedure information
     * @param strProcCode String object which contains Procedure Code
     * @return Object[] the values ,of Procedure Seq ID,Procedure Description
     * @exception throws TTKException
     */
	public Object[] getExactProcedure(String strProcCode) throws TTKException {
		Object[] objArrayResult = new Object[2];
		Connection conn = null;
    	CallableStatement cStmtObject=null;
    	long lngProcSeqID = 0;
    	String strProcDescription = "";
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetExactProcedure);
			
			cStmtObject.setString(1,strProcCode);
			cStmtObject.registerOutParameter(2,Types.VARCHAR);
			cStmtObject.registerOutParameter(3,Types.BIGINT);
			cStmtObject.execute();
			strProcDescription = cStmtObject.getString(2);
			lngProcSeqID = cStmtObject.getLong(3);
			objArrayResult[0] = new String(strProcDescription);
			objArrayResult[1] = new Long(lngProcSeqID);
		}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
        			log.error("Error while closing the Statement in CodingDAOImpl getExactProcedure()",sqlExp);
        			throw new TTKException(sqlExp, "coding");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CodingDAOImpl getExactProcedure()",sqlExp);
        				throw new TTKException(sqlExp, "coding");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "coding");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return objArrayResult;
	}//end of getExactProcedure(String strProcCode)
	
	/**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode - CCU
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO savePreauthReview(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException {
    	String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePreauthReview);
			
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
			cStmtObject.setLong(6,preauthDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(2,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(3,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(4,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(7,Types.VARCHAR);//EVENT_NAME
			cStmtObject.registerOutParameter(8,Types.VARCHAR);//REVIEW
			cStmtObject.execute();

			lngEventSeqID = cStmtObject.getLong(2);
			intReviewCount = cStmtObject.getInt(3);
			intRequiredReviewCnt = cStmtObject.getInt(4);
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
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
        			log.error("Error while closing the Statement in CodingDAOImpl savePreauthReview()",sqlExp);
        			throw new TTKException(sqlExp, "coding");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CodingDAOImpl savePreauthReview()",sqlExp);
        				throw new TTKException(sqlExp, "coding");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "coding");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return preauthDetailVO;
    }//end of savePreauthReview(PreAuthDetailVO preauthDetailVO,String strMode)
    
    /**
	 * This method saves the Review information
	 * @param preauthDetailVO the object which contains the Review Details which has to be  saved
	 * @param strMode String object which contains Mode - CCU
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
    public PreAuthDetailVO saveClaimReview(PreAuthDetailVO preauthDetailVO,String strMode) throws TTKException {
    	String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveClaimReview);
			
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
			cStmtObject.setLong(6,preauthDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(2,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(3,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(4,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(7,Types.VARCHAR);//EVENT_NAME
			cStmtObject.registerOutParameter(8,Types.VARCHAR);//REVIEW
			cStmtObject.execute();

			lngEventSeqID = cStmtObject.getLong(2);
			intReviewCount = cStmtObject.getInt(3);
			intRequiredReviewCnt = cStmtObject.getInt(4);
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
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
        			log.error("Error while closing the Statement in CodingDAOImpl saveClaimReview()",sqlExp);
        			throw new TTKException(sqlExp, "coding");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CodingDAOImpl saveClaimReview()",sqlExp);
        				throw new TTKException(sqlExp, "coding");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "coding");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return preauthDetailVO;
    }//end of saveClaimReview(PreAuthDetailVO preauthDetailVO,String strMode)
    
    /**
	 * This method saves the Pre-Authorization ICD Package information
	 * @param icdPCSVO ICDPCSVO contains ICD Package information
	 * @param strMode String contains Mode for Pre-Authorization/Claims as - PAT / CLM
	 * @return long value which contains ICDPCS Seq ID
	 * @exception throws TTKException
	 */
	public long saveICDPackage(ICDPCSVO icdPCSVO,String strMode) throws TTKException {
		long lngICDPCSSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveICDPackage);
			
			if(icdPCSVO.getPEDSeqID() != null){
				cStmtObject.setLong(1,icdPCSVO.getPEDSeqID());
			}//end of if(icdPCSVO.getPEDSeqID() != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else
			
			if(icdPCSVO.getPreAuthClaimSeqID() != null){
				cStmtObject.setLong(2,icdPCSVO.getPreAuthClaimSeqID());//Mandatory
			}//end of if(icdPCSVO.getPreAuthSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else
			
			if(icdPCSVO.getPEDCodeID() != null){
				if(icdPCSVO.getPEDCodeID() < 0){
					cStmtObject.setString(3,null);
				}//end of if(pedVO.getPEDCodeID() != null)
				else{
					cStmtObject.setLong(3,icdPCSVO.getPEDCodeID());
				}//end of else
			}//end of if(icdPCSVO.getPEDCodeID() != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else
			
			cStmtObject.setString(4,icdPCSVO.getOtherDesc());
			cStmtObject.setString(5,icdPCSVO.getICDCode());
			cStmtObject.setString(6,icdPCSVO.getPrimaryAilmentYN());
			cStmtObject.setString(7,icdPCSVO.getHospitalTypeID());
			cStmtObject.setString(8,icdPCSVO.getTreatmentPlanTypeID());
			
			if(icdPCSVO.getFrequencyVisit() != null){
				cStmtObject.setInt(9,icdPCSVO.getFrequencyVisit());
			}//end of if(icdPCSVO.getFrequencyVisit() != null)
			else{
				cStmtObject.setString(9,null);
			}//end of else
			
			cStmtObject.setString(10,icdPCSVO.getFrequencyVisitType());
			
			if(icdPCSVO.getNoofVisits() != null){
				cStmtObject.setInt(11,icdPCSVO.getNoofVisits());
			}//end of if(icdPCSVO.getNoofVisits() != null)
			else{
				cStmtObject.setString(11,null);
			}//end of else
			
			cStmtObject.setString(12,icdPCSVO.getTariffItemVO().getTariffItemType());
			
			if(icdPCSVO.getTariffItemVO().getTariffItemId() != null){
				cStmtObject.setLong(13,icdPCSVO.getTariffItemVO().getTariffItemId());
			}//end of if(icdPCSVO.getTariffItemVO().getTariffItemId() != null)
			else{
				cStmtObject.setString(13,null);
			}//end of else
			
			cStmtObject.setString(14,icdPCSVO.getTariffItemVO().getAssociateProcedure());
			cStmtObject.setString(15,icdPCSVO.getTariffItemVO().getDeleteProcedure());
			cStmtObject.setString(16,strMode);
			cStmtObject.setLong(17,icdPCSVO.getUpdatedBy());//Mandatory
			
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(18,Types.INTEGER);
			cStmtObject.execute();
			lngICDPCSSeqID = cStmtObject.getLong(1);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "coding");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "coding");
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
					log.error("Error while closing the Statement in CodingDAOImpl saveICDPackage()",sqlExp);
					throw new TTKException(sqlExp, "coding");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in CodingDAOImpl saveICDPackage()",sqlExp);
						throw new TTKException(sqlExp, "coding");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "coding");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lngICDPCSSeqID;
	}//end of saveICDPackage(ICDPCSVO icdPCSVO,String strMode)
	
	/**
     * This method Deletes the ICD details entered in the CodeCleanup flow.
     * @param alICDList ArrayList object which contains the icdpcs sequence id's, to delete icd records
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int deleteCleanupICD(ArrayList alICDList) throws TTKException {
    	int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	 if(alICDList != null && alICDList.size() > 0)
             {
                 conn = ResourceManager.getConnection();
                 cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteCodecleanupICD);
                 
                 cStmtObject.setString(1, (String)alICDList.get(0));//string of icdpcs sequence id's which are separated with | as separator (Note: String should also end with | at the end)
                 cStmtObject.setLong(2, (Long)alICDList.get(1));//user sequence id
                 cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
                 cStmtObject.execute();
                 iResult = cStmtObject.getInt(3);
             }//end of if(alICDList != null && alICDList.size() > 0)
        }//end of try
        catch (SQLException sqlExp)
        {
              throw new TTKException(sqlExp, "coding");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
              throw new TTKException(exp, "coding");
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
        			log.error("Error while closing the Statement in CodingDAOImpl deleteCleanupICD()",sqlExp);
        			throw new TTKException(sqlExp, "coding");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CodingDAOImpl deleteCleanupICD()",sqlExp);
        				throw new TTKException(sqlExp, "coding");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "coding");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of deleteCleanupICD(ArrayList alICDList)
    
    /**
	 * This method saves the PED details
	 * @param pedVO the object which contains the details of the PED
	 * @param lngSeqID long value in Preauthorization flow, Webboard Seq ID as PreauthSeqID and in Claims Flow ClaimSeqID
	 * @param strMode Object which contains Mode - Preauth/Claims flow PAT/CLM
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveCodingPED(PEDVO pedVO,long lngSeqID,String strMode) throws TTKException {
		int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCodingPED);
        	
        	if(pedVO.getSeqID() != null){
                cStmtObject.setLong(1,pedVO.getSeqID());
            }//end of if(pedVO.getSeqID() != null)
            else{
                cStmtObject.setLong(1,0);
            }//end of else

        	cStmtObject.setLong(2,lngSeqID);

        	if(pedVO.getMemberSeqID() != null){
                cStmtObject.setLong(3,pedVO.getMemberSeqID());
            }//end of if(pedVO.getMemberSeqID() != null)
            else{
                cStmtObject.setString(3,null);
            }//end of else

        	if(pedVO.getPEDCodeID() < 0){
                cStmtObject.setString(4,null);
            }//end of if(pedVO.getPEDCodeID() != null)
            else{
                cStmtObject.setLong(4,pedVO.getPEDCodeID());
            }//end of else

        	cStmtObject.setString(5,pedVO.getICDCode());
            cStmtObject.setString(6,strMode);
            cStmtObject.setString(7,pedVO.getPEDType());
            cStmtObject.setLong(8,pedVO.getUpdatedBy());//ADDED_BY
            cStmtObject.registerOutParameter(9, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(9);//ROWS_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "coding");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "coding");
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
        			log.error("Error while closing the Statement in CodingDAOImpl saveCodingPED()",sqlExp);
        			throw new TTKException(sqlExp, "coding");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CodingDAOImpl saveCodingPED()",sqlExp);
        				throw new TTKException(sqlExp, "coding");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "coding");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
	}//end of saveCodingPED(PEDVO pedVO,long lngSeqID,String strMode)
    
}//end of CodingDAOImpl
