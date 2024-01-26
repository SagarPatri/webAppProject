/**
 * @ (#) PolicyDAOImpl.java Feb 1, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PolicyDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 1, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  Arun K N
 * Modified date :  May 03,2006
 * Reason        :  for handling the empty cache if insurance companies are not there and we are searching for
 *                  branches of it.
 */

package com.ttk.dao.impl.dataentryenrollment;

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
import java.util.HashMap;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;
//import org.jboss.resource.adapter.jdbc.WrappedConnection;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.enrollment.EndorsementVO;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.enrollment.PolicyGroupVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.dto.empanelment.InsuranceVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.security.GroupVO;


public class PolicyDAOImpl implements BaseDAO, Serializable {
	
	private static Logger log = Logger.getLogger(PolicyDAOImpl.class);
	
	//private static final String strPolicyList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_POLICY_LIST(?,?,?,?,?,?,?,?,?)}";
	//private static final String strPreAuthPolicyList = "{CALL PRE_AUTH_SQL_PKG.SELECT_POLICY_NO_LIST(?,?,?,?,?,?,?,?,?)}";
	//private static final String strPolicy = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_POLICY(?,?,?,?)}";
	private static final String strPolicyList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_POLICY_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strPreAuthPolicyList = "{CALL PRE_AUTH_PKG.SELECT_POLICY_NO_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strPolicy = "{CALL POLICY_ENROLLMENT_PKG.SELECT_POLICY(?,?,?,?)}";
	private static final String strProductList = "SELECT DISTINCT A.PRODUCT_SEQ_ID, A.PRODUCT_NAME FROM TPA_INS_PRODUCT A JOIN TPA_INS_ASSOC_OFF_PRODUCT B ON (A.PRODUCT_SEQ_ID= B.PRODUCT_SEQ_ID) WHERE B.INS_SEQ_ID=? AND B.ENROL_TYPE_ID=? ORDER BY UPPER(A.PRODUCT_NAME)";
	private static final String strInsProductList = "SELECT A.PRODUCT_SEQ_ID, A.PRODUCT_NAME FROM TPA_INS_PRODUCT A JOIN TPA_INS_PROD_POLICY B ON(A.product_seq_id=B.product_seq_id) JOIN TPA_INS_PROD_POLICY_RULES C ON(B.prod_policy_seq_id = C.prod_policy_seq_id) WHERE C.valid_to IS NULL AND A.ins_seq_id= ? ORDER BY UPPER(A.PRODUCT_NAME)";
	private static final String strEnrollTypeIdList = "SELECT ENROL_TYPE_ID FROM TPA_ENROLMENT_TYPE_CODE";
	private static final String strProductEnrollList = "SELECT DISTINCT A.PRODUCT_SEQ_ID, A.PRODUCT_NAME FROM TPA_INS_PRODUCT A JOIN TPA_INS_ASSOC_OFF_PRODUCT B ON (A.PRODUCT_SEQ_ID= B.Product_Seq_Id) WHERE B.INS_SEQ_ID=? AND B.ENROL_TYPE_ID = ? ORDER BY UPPER(A.PRODUCT_NAME)";
	//private static final String strInsuranceCompanyList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_INS_COMPS_LIST(?,?,?,?,?,?,?,?)}";
	//private static final String strGroupList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_CORPORATE_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strInsuranceCompanyList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_INS_COMPS_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strGroupList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_CORPORATE_LIST(?,?,?,?,?,?,?,?,?)}";
	// Changes done for CR KOC1170 added one parameter for saving policy holder code
     private static final String strSavePolicy = "{CALL POLICY_ENROLLMENT_PKG.SAVE_POLICY(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";  //7 parameters added for koc 1278 PED
	//private static final String strBatchPolicyList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_BATCH_POLICY_LIST(?,?,?,?,?,?,?,?)}";
	//private static final String strSelectBatchPolicy = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_BATCH_POLICY(?,?,?)}";
	private static final String strBatchPolicyList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_BATCH_POLICY_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strSelectBatchPolicy = "{CALL POLICY_ENROLLMENT_PKG.SELECT_BATCH_POLICY(?,?,?)}";
	private static final String strSaveBatchPolicy = "{CALL POLICY_ENROLLMENT_PKG.SAVE_BATCH_POLICY(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeletePolicy = "{CALL POLICY_ENROLLMENT_PKG.DELETE_GENERAL(?,?,?,?,?,?,?)}";
	private static final String strDeleteBatchPolicy = "{CALL POLICY_ENROLLMENT_PKG.DELETE_BATCH_POLICY(?,?,?)}";
	//private static final String strGetEndorsement = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_ENDORSEMENT(?,?)}";
	private static final String strGetEndorsement = "{CALL POLICY_ENROLLMENT_PKG.SELECT_ENDORSEMENT(?,?)}";
	private static final String strSaveEndorsement = "{CALL POLICY_ENROLLMENT_PKG_SAVE_ENDORSEMENTS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	//private static final String strOtherPolicyList = "{CALL POLICY_ENROLLMENT_SQL_PKG.COMPARE_POLICIES_LIST(?,?)}";
	private static final String strOtherPolicyList = "{CALL POLICY_ENROLLMENT_PKG.COMPARE_POLICIES_LIST(?,?)}";
	private static final String strComparePolicy = "{CALL POLICY_ENROLLMENT_PKG.COMPARE_POLICIES(?,?,?)}";
	private static final String strAssociatePolicy = "{CALL POLICY_ENROLLMENT_PKG.ASSOCIATE_POLICY(?,?,?,?)}";
	private static final String strSaveReview = "{CALL POLICY_ENROLLMENT_PKG.SET_REVIEW(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteGeneral = "{CALL POLICY_ENROLLMENT_PKG.DELETE_GENERAL(?,?,?,?,?,?,?)}";
    private static final String strDeleteEndorsement = "{CALL POLICY_ENROLLMENT_PKG.DELETE_GENERAL(?,?,?,?,?,?,?)}";
    private static final String strOnlinePolicyList = "{CALL ONLINE_ENROLMENT_PKG.SELECT_POLICY_LIST(?,?,?,?,?,?,?)}";
    private static final String strValidateEnrollment = "{CALL ENROLLMENT_VALIDATION_PKG.VALIDATE_ENROLLMENT(?,?,?)}";
    private static final String strCheckRuleDefined = "{CALL POLICY_ENROLLMENT_PKG.CHECK_RULE_DEFINED(?,?)}";
    private static final String strSelectSIDetails="{CALL POLICY_ENROLLMENT_PKG.SELECT_SI_DETAILS(?,?)}";
	
    private static final int POLICY_SEQ_ID = 1;
	private static final int ENROL_BATCH_SEQ_ID = 2;
	private static final int ENROL_TYPE_ID = 3;
	private static final int INS_SEQ_ID = 4;
	private static final int PRODUCT_SEQ_ID = 5;
	private static final int TPA_OFFICE_SEQ_ID = 6;
	private static final int POLICY_NUMBER = 7;
	private static final int RENEWAL_POLICY_NUMBER = 8;
	private static final int GROUP_REG_SEQ_ID = 9;
	private static final int POLICY_RCVD_DATE = 10;
	private static final int POLICY_SUB_GENERAL_TYPE_ID = 11;
	private static final int EMPLOYEE_COUNT = 12;
	private static final int TOTAL_MEMBER_COUNT = 13;
	private static final int POLICY_ISSUE_DATE = 14;
	private static final int EFFECTIVE_FROM_DATE = 15;
	private static final int EFFECTIVE_TO_DATE = 16;
	private static final int PROPOSAL_FORM_YN = 17;
	private static final int DECLARATION_DATE = 18;
    private static final int BANK_SEQ_ID = 19;
	private static final int BANK_ACCOUNT_NO = 20;
    private static final int BANK_NAME = 21;
    private static final int BANK_PHONE_NO = 22;
    private static final int BANK_BRANCH = 23;
    private static final int BANK_MICR = 24;
    private static final int POLICY_CONDONED_YN = 25;
	private static final int POLICY_AGENT_CODE = 26;
	private static final int INS_STATUS_GENERAL_TYPE_ID = 27;
	private static final int POLICY_STATUS_GENERAL_TYPE_ID = 28;
	private static final int TOTAL_SUM_INSURED = 29;
	private static final int TOTAL_NET_PREMIUM = 30;
	private static final int REMARKS = 31;
	private static final int PAN_NUMBER = 32;
    private static final int CLARIFY_GENERAL_TYPE_ID = 33;
    private static final int CLARIFIED_DATE = 34;
	private static final int INSERT_MODE = 35;
	private static final int DMS_REFERENCE_ID = 36;
	private static final int USER_SEQ_ID = 37;
	//added for koc 1278 PED
	private static final int OTH_TPA_INSURE = 38;
	private static final int OTH_TPA_POLICY_NO = 39;
	private static final int OTH_TPA_EFFECTIVE_FROM = 40;
	private static final int OTH_TPA_EFFECTIVE_TO = 41;
	private static final int OTH_TPA_SI = 42;
	private static final int OTH_TPA_DELAY_CONDONED = 43;
	private static final int OTH_TPA_PORTABILITY_APPROVED = 44;
	//added for koc 1278 PEd
	private static final int ADDREESS_1 = 45;
	private static final int ADDREESS_2 = 46;
	private static final int ADDREESS_3 = 47;
	private static final int STATE_TYPE_ID = 48;
	private static final int CITY_TYPE_ID = 49;
	private static final int PIN_CODE = 50;
	private static final int COUNTRY_ID = 51;
    private static final int EMAIL_ID = 52;
    private static final int RES_PHONE_NO = 53;
    private static final int OFF_PHONE_NO_1 = 54;
    private static final int OFF_PHONE_NO_2 = 55;
    private static final int MOBILE_NO = 56;
    private static final int FAX_NO = 57;
	private static final int POLICY_GROUP_SEQ_ID = 58;
	private static final int TPA_ENROLLMENT_NUMBER = 59;
	private static final int ENR_ADDRESS_SEQ_ID = 60;
	private static final int INSURED_NAME = 61;
	private static final int BENEFICIARY_NAME = 62;
	private static final int RELSHIP_TYPE_ID = 63;
	private static final int POLICY_MODE = 64;
	private static final int POLICY_TYPE = 65;
	private static final int ENDORSEMENT_SEQ_ID = 66;
	private static final int ACCOUNT_NAME = 67;
	private static final int INS_SCHEME = 68;
	private static final int PREVIOUS_SCHEME_NAME = 69;
	private static final int POLICY_REMARKS = 70;
	private static final int DOMICILARY_TYPE_ID = 71;
	private static final int PRODUCT_CHANGE_YN = 72;
	private static final int RENEW_USING_DIFF_BRANCH_YN = 73;
	private static final int ZONE_CODE = 74;
	private static final int DEV_OFFICER_CODE = 75;
	// Changes done for CR KOC1170
    private static final int POLICY_HOLDER_CODE = 76;
	//End change done for CR KOC1170
	//added by rekha for policy renewal
    private static final int ACTUAL_POLICY_START_DATE =77;
    private static final int ACTUAL_POLICY_END_DATE =78;
    private static final int ROW_PROCESSED = 79;
         

	/**
	 * This method returns the Arraylist of PolicyVO's  which contains the details of enrollment policy details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyVO object which contains the details of enrollment policy details
	 * @exception throws TTKException
	 */
	public ArrayList getPolicyList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		OracleCallableStatement oCstmt = null;
		PolicyVO policyVO = null;
		ResultSet rs = null;
		String strEnrolYN = "";
		String strIdentifier = "";
        String strSuppressLink[]={"0","1","2","6","8"}; //2(policy no.) for Endorsement flow
        String strGroupID="";
        String strPolicyGrpID ="";
        try{
			conn = ResourceManager.getConnection();
			oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPolicyList);
			strEnrolYN = alSearchCriteria.get(0).toString();
			strIdentifier = alSearchCriteria.get(1).toString();
			oCstmt.setString(1,strEnrolYN);
			oCstmt.setString(2, strIdentifier);
			oCstmt.setPlsqlIndexTable(3, alSearchCriteria.get(2), 10,10,OracleTypes.VARCHAR,250);
			ArrayList alUserGroup = (ArrayList)alSearchCriteria.get(4);
			oCstmt.setString(4,(String)alSearchCriteria.get(5));
			oCstmt.setString(5,(String)alSearchCriteria.get(6));
			oCstmt.setString(6 ,(String)alSearchCriteria.get(7));
			oCstmt.setString(7 ,(String)alSearchCriteria.get(8));
			oCstmt.setLong(8,(Long)alSearchCriteria.get(3));
			oCstmt.registerOutParameter(9,OracleTypes.CURSOR);
			oCstmt.execute();
			rs = (java.sql.ResultSet)oCstmt.getObject(9);
			if(rs != null){
				while (rs.next()) {
					policyVO = new PolicyVO();
					//common to all tabs
					if(rs.getString("POLICY_SEQ_ID") != null){
						policyVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)
					policyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					policyVO.setAgentCode(TTKCommon.checkNull(rs.getString("POLICY_AGENT_CODE")));

					if(rs.getString("ENROL_BATCH_SEQ_ID") != null){
						policyVO.setBatchSeqID(new Long(rs.getLong("ENROL_BATCH_SEQ_ID")));
					}//end of if(rs.getString("ENROL_BATCH_SEQ_ID") != null)
					policyVO.setBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NUMBER")));
					policyVO.setReview(TTKCommon.checkNull(rs.getString("REVIEW")));

					if((strIdentifier.equals("ING"))||(strIdentifier.equals("COR"))|| (strIdentifier.equals("NCR"))){
                        policyVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
                        policyVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					}//end of if((strIdentifier.equals("ING"))||(strIdentifier.equals("COR"))|| (strIdentifier.equals("NCR")))

					if(strEnrolYN.equals("Y")) {
						policyVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
						policyVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
						if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
							policyVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
						}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)
						policyVO.setTPAStatusTypeID(TTKCommon.checkNull(rs.getString("INS_STATUS_GENERAL_TYPE_ID")));
						policyVO.setCompletedYN(TTKCommon.checkNull(rs.getString("COMPLETED_YN")));

						if(rs.getString("INS_STATUS_GENERAL_TYPE_ID") != null){

							if(rs.getString("INS_STATUS_GENERAL_TYPE_ID").equals("POC")) {
								policyVO.setImageName("CancelledIcon");
								policyVO.setImageTitle("Cancelled Policy");
							}//end of if(rs.getString("TPA_STATUS_GENERAL_TYPE_ID") .equals("TPC"))

							if(rs.getString("INS_STATUS_GENERAL_TYPE_ID").equals("FTS")){
								policyVO.setImageName("FreshIcon");
								policyVO.setImageTitle("Fresh Policy");
							}//end of if(rs.getString("TPA_STATUS_GENERAL_TYPE_ID") .equals("TPF"))

							if(rs.getString("INS_STATUS_GENERAL_TYPE_ID").equals("RTS")) {
								policyVO.setImageName("RenewIcon");
								policyVO.setImageTitle("Renewal Policy");
							}//end of if(rs.getString("TPA_STATUS_GENERAL_TYPE_ID") .equals("TPR"))

						}//end of if(rs.getString("TPA_STATUS_GENERAL_TYPE_ID") != null)
					}//end of if(strEnrolYN.equals("Y"))

					if(strEnrolYN.equals("N")) {
						if(rs.getString("ENDORSEMENT_SEQ_ID") != null){
							policyVO.setEndorsementSeqID(new Long(rs.getLong("ENDORSEMENT_SEQ_ID")));
						}//end of if(rs.getString("ENDORSEMENT_SEQ_ID") != null)
						policyVO.setEndorsementNbr(TTKCommon.checkNull(rs.getString("ENDORSEMENT_NUMBER")));
						policyVO.setCustEndorsementNbr(TTKCommon.checkNull(rs.getString("CUST_ENDORSEMENT_NUMBER")));
						policyVO.setPolicyYN(TTKCommon.checkNull(rs.getString("POLICY_YN")));
					}//end of if(strEnrolYN.equals("N"))

					strGroupID = TTKCommon.checkNull(rs.getString("GROUP_BRANCH_SEQ_ID"));
					
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
                    
                    if(!strGroupID.equals("") && bSuppress)
                        policyVO.setSuppressLink(strSuppressLink);

                    alResultList.add(policyVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPolicyList(ArrayList alSearchCriteria)

	/**
     * This method returns the Arraylist of ClaimantVO  which contains Policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of ClaimantVO object which contains Policy details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthPolicyList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
		ClaimantVO claimantVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthPolicyList);
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

					if(rs.getString("POLICY_SEQ_ID") != null){
						claimantVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)

					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					claimantVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					claimantVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
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

					if(rs.getString("INS_SEQ_ID") != null){
						claimantVO.setInsSeqID(new Long(rs.getLong("INS_SEQ_ID")));
					}//end of if(rs.getString("") != null)

					claimantVO.setCompanyCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						claimantVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("") != null)
					claimantVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					claimantVO.setBufferAllowedYN(TTKCommon.checkNull(rs.getString("BUFFER_ALLOWED_YN")));

					if(rs.getString("TOTAL_BUFFER_AMOUNT") != null){
						claimantVO.setTotBufferAmt(new BigDecimal(rs.getString("TOTAL_BUFFER_AMOUNT")));
					}//end of if(rs.getString("TOTAL_BUFFER_AMOUNT") != null)

					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs.getString("INS_SCHEME")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
					alResultList.add(claimantVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getPreAuthPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getPreAuthPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getPreAuthPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPreAuthPolicyList(ArrayList alSearchCriteria)

	/**
	 * This method returns the PolicyDetailVO, which contains all the policy details
	 * @param alPolicy ArrayList which contains seq id for Enrollment or Endorsement flow to get the Policy Details
	 * @return PolicyDetailVO object which contains all the policy details
	 * @exception throws TTKException
	 */
	public PolicyDetailVO getPolicy(ArrayList alPolicy) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PolicyDetailVO policyDetailVO = null;
		MemberAddressVO memberAddressVO = null;
		ArrayList alProductList = new ArrayList();
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPolicy);
			cStmtObject.setLong(1,(Long)alPolicy.get(0));
			cStmtObject.setString(2,(String)alPolicy.get(1));
			cStmtObject.setString(3,(String)alPolicy.get(2));
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				policyDetailVO = new PolicyDetailVO();
				memberAddressVO = new MemberAddressVO();
				while (rs.next()) {
					policyDetailVO.setBatchSeqID(rs.getString("ENROL_BATCH_SEQ_ID")!=null ? new Long(rs.getLong("ENROL_BATCH_SEQ_ID")):null);
					policyDetailVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					policyDetailVO.setInsuranceSeqID(rs.getString("INS_SEQ_ID")!=null ? new Long(rs.getLong("INS_SEQ_ID")):null);
					policyDetailVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					policyDetailVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					policyDetailVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					policyDetailVO.setGroupRegnSeqID(rs.getString("GROUP_REG_SEQ_ID")!=null ? new Long(rs.getLong("GROUP_REG_SEQ_ID")):null);
					policyDetailVO.setEmployeeCnt(rs.getString("EMPLOYEE_COUNT")!=null ? new Long(rs.getLong("EMPLOYEE_COUNT")):null);
                    policyDetailVO.setMemberCnt(rs.getString("TOTAL_MEMBER_COUNT")!=null ? new Long(rs.getLong("TOTAL_MEMBER_COUNT")):null);
                    policyDetailVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					policyDetailVO.setPolicyHolderName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
					policyDetailVO.setPrevPolicyNbr(TTKCommon.checkNull(rs.getString("RENEWAL_POLICY_NUMBER")));
					policyDetailVO.setPANNbr(TTKCommon.checkNull(rs.getString("PAN_NUMBER")));
					policyDetailVO.setINSStatusTypeID(TTKCommon.checkNull(rs.getString("INS_STATUS_GENERAL_TYPE_ID")));
					policyDetailVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					policyDetailVO.setEnrollmentDesc(TTKCommon.checkNull(rs.getString("ENROL_DESCRIPTION")));
					policyDetailVO.setSubTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
					policyDetailVO.setStartDate(rs.getString("EFFECTIVE_FROM_DATE")!=null ? new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()):null);
					policyDetailVO.setEndDate(rs.getString("EFFECTIVE_TO_DATE")!=null ? new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()):null);
					policyDetailVO.setIssueDate(rs.getString("POLICY_ISSUE_DATE")!=null ? new Date(rs.getTimestamp("POLICY_ISSUE_DATE").getTime()):null);
					policyDetailVO.setDevOffCode(TTKCommon.checkNull(rs.getString("POLICY_AGENT_CODE")));
					policyDetailVO.setProposalFormYN(TTKCommon.checkNull(rs.getString("PROPOSAL_FORM_YN")));
					policyDetailVO.setDeclarationDate(rs.getString("DECLARATION_DATE")!=null ? new Date(rs.getTimestamp("DECLARATION_DATE").getTime()):null);
					policyDetailVO.setProductSeqID(rs.getString("PRODUCT_SEQ_ID")!=null ? new Long(rs.getLong("PRODUCT_SEQ_ID")):null);
					policyDetailVO.setPolicyStatusID(TTKCommon.checkNull(rs.getString("POLICY_STATUS_GENERAL_TYPE_ID")));
					policyDetailVO.setTPAStatusTypeID(TTKCommon.checkNull(rs.getString("TPA_STATUS_GENERAL_TYPE_ID")));
					policyDetailVO.setRecdDate(rs.getString("POLICY_RCVD_DATE")!=null ? new Date(rs.getTimestamp("POLICY_RCVD_DATE").getTime()):null);
					policyDetailVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
					policyDetailVO.setBankSeqID(rs.getString("BANK_SEQ_ID")!=null ? new Long(rs.getLong("BANK_SEQ_ID")):null);
                    policyDetailVO.setBankAccNbr(TTKCommon.checkNull(rs.getString("BANK_ACCOUNT_NO")));
                    policyDetailVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));
                    policyDetailVO.setBranch(TTKCommon.checkNull(rs.getString("BANK_BRANCH")));
                    policyDetailVO.setBankPhone(TTKCommon.checkNull(rs.getString("BANK_PHONE_NO")));
                    policyDetailVO.setMICRCode(TTKCommon.checkNull(rs.getString("BANK_MICR")));
                    policyDetailVO.setBankAccountName(TTKCommon.checkNull(rs.getString("ACCOUNT_NAME")));
					policyDetailVO.setCondonedYN(TTKCommon.checkNull(rs.getString("POLICY_CONDONED_YN")));
					policyDetailVO.setTotalSumInsured(rs.getString("TOTAL_SUM_INSURED")!=null ? new BigDecimal(rs.getString("TOTAL_SUM_INSURED")):null);
					policyDetailVO.setSumWording(TTKCommon.checkNull(rs.getString("SUM_WORDING")));
					policyDetailVO.setTotalPremium(rs.getString("TOTAL_NET_PREMIUM")!=null ? new BigDecimal(rs.getString("TOTAL_NET_PREMIUM")):null);
					policyDetailVO.setPremiumWording(TTKCommon.checkNull(rs.getString("PREMIUM_WORDING")));
					memberAddressVO.setAddrSeqId(rs.getString("ENR_ADDRESS_SEQ_ID")!=null ? new Long(rs.getLong("ENR_ADDRESS_SEQ_ID")):null);
					memberAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
					memberAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
					memberAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
					memberAddressVO.setCityCode(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));
					memberAddressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
					memberAddressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
					memberAddressVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY_DESCRIPTION")));
					memberAddressVO.setStateName(TTKCommon.checkNull(rs.getString("STATE_NAME")));
					memberAddressVO.setCountryName(TTKCommon.checkNull(rs.getString("COUNTRY_NAME")));
					memberAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
                    memberAddressVO.setHomePhoneNbr(TTKCommon.checkNull(rs.getString("RES_PHONE_NO")));
                    memberAddressVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
                    memberAddressVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
                    memberAddressVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
                    memberAddressVO.setFaxNbr(TTKCommon.checkNull(rs.getString("FAX_NO")));
                    memberAddressVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
					policyDetailVO.setAddress(memberAddressVO);
					policyDetailVO.setBeneficiaryName(TTKCommon.checkNull(rs.getString("BENEFICIARY_NAME")));
					policyDetailVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
					policyDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					policyDetailVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getLong("POLICY_SEQ_ID")):null);
					policyDetailVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID")!=null ? new Long(rs.getLong("POLICY_GROUP_SEQ_ID")):null);
					policyDetailVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
					policyDetailVO.setEventSeqID(rs.getString("EVENT_SEQ_ID")!=null ? new Long(rs.getLong("EVENT_SEQ_ID")):null);
					policyDetailVO.setRequiredReviewCnt(rs.getString("REQUIRED_REVIEW_COUNT")!=null ? new Integer(rs.getInt("REQUIRED_REVIEW_COUNT")):null);
					policyDetailVO.setReviewCount(rs.getString("REVIEW_COUNT")!=null ? new Integer(rs.getInt("REVIEW_COUNT")):null);
					policyDetailVO.setEventName(TTKCommon.checkNull(rs.getString("EVENT_NAME")));
					policyDetailVO.setCardPrintYN(TTKCommon.checkNull(rs.getString("CARD_PRN_YN")));
					policyDetailVO.setTemplateID(rs.getString("TEMPLATE_ID")!=null ? new Long(rs.getLong("TEMPLATE_ID")):null);
					policyDetailVO.setInsScheme(TTKCommon.checkNull(rs.getString("INS_SCHEME")));
					policyDetailVO.setInsertMode(TTKCommon.checkNull(rs.getString("INSERT_MODE")));
					policyDetailVO.setReview(TTKCommon.checkNull(rs.getString("REVIEW")));
                    policyDetailVO.setClarificationTypeID(TTKCommon.checkNull(rs.getString("CLARIFY_GENERAL_TYPE_ID")));
                    policyDetailVO.setClarifiedDate(rs.getString("CLARIFIED_DATE")!=null ? new Date(rs.getTimestamp("CLARIFIED_DATE").getTime()):null);
                    policyDetailVO.setDMSRefID(TTKCommon.checkNull(rs.getString("DMS_REFERENCE_ID")));
                    policyDetailVO.setTenure(rs.getString("TENURE")!=null ? new Long(rs.getLong("TENURE")):null);
                    policyDetailVO.setSchemeName(TTKCommon.checkNull(rs.getString("INS_SCHEME")));
                    policyDetailVO.setPrevSchemeName(TTKCommon.checkNull(rs.getString("PREVIOUS_SCHEME_NAME")));
                    policyDetailVO.setPolicyRemarks(TTKCommon.checkNull(rs.getString("POLICY_REMARKS")));
                    policyDetailVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
                    policyDetailVO.setCompletedYN(TTKCommon.checkNull(rs.getString("COMPLETED_YN")));
                    policyDetailVO.setDomicilaryTypeID(TTKCommon.checkNull(rs.getString("DOMICILARY_GENERAL_TYPE_ID")));
                    policyDetailVO.setInsuranceEndYN(TTKCommon.checkNull(rs.getString("INSURANCE_ENDORSEMENT_YN")));
                    policyDetailVO.setProductChangeYN(TTKCommon.checkNull(rs.getString("RENEW_USING_DIFF_PRODUCT_YN")));
                    policyDetailVO.setDOBOChangeYN(TTKCommon.checkNull(rs.getString("RENEW_USING_DIFF_BRANCH_YN")));
                    policyDetailVO.setZoneCode(TTKCommon.checkNull(rs.getString("ZONE_CODE")));
                    policyDetailVO.setAgentCode(TTKCommon.checkNull(rs.getString("DEV_OFFICER_CODE")));
					  // Changes done for CR KOC1170 
                    policyDetailVO.setPolicyHolderCode(TTKCommon.checkNull(rs.getString("POLICY_HOLDER_CODE")));
                	policyDetailVO.setStartDate1(rs.getString("ACTUAL_POLICY_START_DATE")!=null ? new Date(rs.getTimestamp("ACTUAL_POLICY_START_DATE").getTime()):null);
					policyDetailVO.setEndDate1(rs.getString("ACTUAL_POLICY_END_DATE")!=null ? new Date(rs.getTimestamp("ACTUAL_POLICY_END_DATE").getTime()):null);
                    
                    // End changes done for CR KOC1170 
                    if(rs.getString("INS_SEQ_ID") != null && !(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")).equals(""))){
                    	alProductList = (ArrayList)getProductList(rs.getLong("INS_SEQ_ID"),rs.getString("ENROL_TYPE_ID"));
                    	policyDetailVO.setProductList(alProductList);
                    }//end of if
                    //added for koc 1278 PED
                    policyDetailVO.setOtherTPAInsuredname(TTKCommon.checkNull(rs.getString("OTH_TPA_INSURE")));
                    policyDetailVO.setOtherTPAPolicyNr(TTKCommon.checkNull(rs.getString("OTH_TPA_POLICY_NO")));
                    policyDetailVO.setTPAStartDate(rs.getString("OTH_TPA_EFFECTIVE_FROM")!=null ? new Date(rs.getTimestamp("OTH_TPA_EFFECTIVE_FROM").getTime()):null);
                    policyDetailVO.setTPAEndDate(rs.getString("OTH_TPA_EFFECTIVE_TO")!=null ? new Date(rs.getTimestamp("OTH_TPA_EFFECTIVE_TO").getTime()):null);
                    policyDetailVO.setOtherTPASIValue(TTKCommon.checkNull(rs.getString("OTH_TPA_SI")));
                    policyDetailVO.setOtherTPADelayCondoned(TTKCommon.checkNull(rs.getString("OTH_TPA_DELAY_CONDONED")));
                    policyDetailVO.setOtherTPAPortApproved(TTKCommon.checkNull(rs.getString("OTH_TPA_PORTABILITY_APPROVED")));
                    //added for koc 1278 PEd
				}//end of while(rs.next())
			}//end of if(rs != null)
			return policyDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getPolicy()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getPolicy()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getPolicy()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPolicy(ArrayList alPolicy)

	/**
	 * This method returns the HashMap,which contains the ReasonId,Reason Description
	 *@return HashMap containing Reason Type Id and Reason Description
	 * @exception throws TTKException
	 */
    public HashMap getProductInfo(long lngInsSeqID) throws TTKException{
    	Connection conn = null;
    	PreparedStatement pStmt1 = null;
     	PreparedStatement pStmt2 = null;
    	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	CacheObject cacheObject = null;
    	HashMap<Object,Object> hmProductList = null;
    	ArrayList<Object> alProductList = null;
    	String strEnrolTypeId = "";
    	try{
    		conn = ResourceManager.getConnection();
    		pStmt1=conn.prepareStatement(strEnrollTypeIdList);
    		pStmt2 = conn.prepareStatement(strProductEnrollList);
    		rs1= pStmt1.executeQuery();
    		if (rs1!=null)
    		{
    			while(rs1.next()){
    				if(hmProductList == null)
    					hmProductList=new HashMap<Object,Object>();
    				strEnrolTypeId = TTKCommon.checkNull(rs1.getString("ENROL_TYPE_ID"));
    				pStmt2.setLong(1,lngInsSeqID);
    				pStmt2.setString(2,strEnrolTypeId);
    				rs2=pStmt2.executeQuery();
    				alProductList=null;
    				if (rs2!=null)
    				{
    					while(rs2.next()){
    						cacheObject = new CacheObject();
    						if(alProductList == null)
    							alProductList = new ArrayList<Object>();
    						cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("PRODUCT_SEQ_ID")));
    						cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("PRODUCT_NAME")));
    						alProductList.add(cacheObject);
    					}// end of inner while(rs2.next())
    				}//end of  inner if (rs2!=null)
    				hmProductList.put(strEnrolTypeId,alProductList);
    				if (rs2 != null){
                      rs2.close(); 
                    }//end of if (rs2 != null)
                    rs2=null;
               }//end of outer while(rs1.next())
    		}//end of outer  if (rs1!=null)
    		if (pStmt2 != null){
              pStmt2.close();
           }//end of if (pStmt2 != null)
    		pStmt2 = null;
    		return hmProductList;
    	}// end of try
    	catch (SQLException sqlExp)
    	{
    		throw new TTKException(sqlExp, "enrollment");
    	}//end of catch (SQLException sqlExp)
    	catch (Exception exp)
    	{
    		throw new TTKException(exp, "enrollment");
    	}//end of catch (Exception exp)
    	finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the second result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Resultset in PolicyDAOImpl getCategoryReasonInfo()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in PolicyDAOImpl getCategoryReasonInfo()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if first result set is not closed, control reaches here. Try closing the second statement now.
					{
						try
						{
							if (pStmt2 != null) pStmt2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the second Statement in PolicyDAOImpl getProductInfo()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
						finally // Even if second statement is not closed, control reaches here. Try closing the first statement now.
						{
							try{
								if (pStmt1 != null) pStmt1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the first Statement in PolicyDAOImpl getProductInfo()",sqlExp);
								throw new TTKException(sqlExp, "enrollment");
							}//end of catch (SQLException sqlExp)
							finally{ // Even if first statement is not closed, control reaches here. Try closing the connection now.
								try
								{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in PolicyDAOImpl getProductInfo()",sqlExp);
									throw new TTKException(sqlExp, "enrollment");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs2 = null;
	    		rs1 = null;
	    		pStmt2 = null;
	    		pStmt1 = null;
	    		conn = null;
			}//end of finally
		}//end of finally
    } // end of getProductInfo(long lngInsSeqID)

    /**
     * This method returns the ArrayList object, which contains all the details about the Products for the corresponding Insurance Company
     * @param lngInsSeqID long Insurance Seq ID
     * @param strPolicyType String contains Policy Type
     * @return ArrayList object which contains all the details about the Products for the corresponding Insurance Company
     * @exception throws TTKException
     */
    public ArrayList getProductList(long lngInsSeqID,String strPolicyType) throws TTKException {
    	Connection conn1 = null;
    	ResultSet rs = null;
    	PreparedStatement pStmt = null;
        ArrayList<Object> alproductList = new ArrayList<Object>();
        CacheObject cacheObject = null;
        try{
            conn1 = ResourceManager.getConnection();
            pStmt=conn1.prepareStatement(strProductList);
            pStmt.setLong(1,lngInsSeqID);
            pStmt.setString(2,strPolicyType);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId((rs.getString("PRODUCT_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                    alproductList.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alproductList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "enrollment");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getProductList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getProductList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn1 != null) conn1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getProductList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
    }//end of getProductList(long lngInsSeqID,String strPolicyType)
    
    /**
     * This method returns the ArrayList object, which contains all the details about the Products for the corresponding Insurance Company
     * @param lngInsSeqID long Insurance Seq ID
     * @return ArrayList object which contains all the details about the Products for the corresponding Insurance Company
     * @exception throws TTKException
     */
    public ArrayList getInsProductList(long lngInsSeqID) throws TTKException {
    	Connection conn1 = null;
    	ResultSet rs = null;
    	PreparedStatement pStmt = null;
        ArrayList<Object> alproductList = new ArrayList<Object>();
        CacheObject cacheObject = null;
        try{
            conn1 = ResourceManager.getConnection();
            pStmt=conn1.prepareStatement(strInsProductList);
            pStmt.setLong(1,lngInsSeqID);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next())
                {
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId((rs.getString("PRODUCT_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("PRODUCT_NAME")));
                    alproductList.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alproductList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "enrollment");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getProductList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getProductList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn1 != null) conn1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getProductList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
    }//end of getInsProductList(long lngInsSeqID)

	/**
	 * This method returns the Arraylist of InsuranceVO's  which contains insurance company details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceVO object which contains insurance company details
	 * @exception throws TTKException
	 */
	public ArrayList getInsuranceCompanyList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		InsuranceVO insuranceVO= null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInsuranceCompanyList);
			if(!TTKCommon.checkNull((String)alSearchCriteria.get(0)).equals(""))
			{
			    cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			}//end of if(!TTKCommon.checkNull((String)alSearchCriteria.get(0)).equals(""))
            else
            {
                cStmtObject.setString(1,null);
            }//end of else
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7 ,(String)alSearchCriteria.get(6));
			cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(8);
			if(rs != null){
				while (rs.next()) {
					insuranceVO = new InsuranceVO();
					if(rs.getString("INS_SEQ_ID") != null){
						insuranceVO.setInsuranceSeqID(new Long(rs.getLong("INS_SEQ_ID")));
					}//end of if(rs.getString("INS_SEQ_ID") != null)
					insuranceVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					insuranceVO.setCompanyCodeNbr(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					if(rs.getString("TPA_OFFICE_SEQ_ID") != null){
						insuranceVO.setTTKBranchCode(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
					}//end of if(rs.getString("TPA_OFFICE_SEQ_ID") != null)
					insuranceVO.setTTKBranch(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
                    insuranceVO.setOfficeType(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					alResultList.add(insuranceVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getInsuranceCompanyList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getInsuranceCompanyList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getInsuranceCompanyList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getInsuranceCompanyList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of PolicyGroupVO's  which contains Policy Corporate Group details
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PolicyGroupVO object which contains Policy Corporate Group details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PolicyGroupVO policyGroupVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGroupList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7 ,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8 ,(String)alSearchCriteria.get(7));
			cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);
			if(rs != null){
				while (rs.next()) {
					policyGroupVO = new PolicyGroupVO();
					if(rs.getString("GROUP_REG_SEQ_ID") != null){
						policyGroupVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
					}//end of if(rs.getString("GROUP_REG_SEQ_ID") != null)
					policyGroupVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					policyGroupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					policyGroupVO.setBranchName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					alResultList.add(policyGroupVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getGroupList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getGroupList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getGroupList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getGroupList(ArrayList alSearchCriteria)

	/**
	 * This method saves the Enrollment Policy information
	 * @param policyDetailVO the object which contains the Enrollment Policy Details which has to be  saved
	 * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
	 * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public long savePolicy(PolicyDetailVO policyDetailVO,String strPolicyMode,String strPolicyType) throws TTKException {
		long lngPolicySeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePolicy);
			
			log.info("DevOfficer Code is : "+policyDetailVO.getDevOffCode());
			if(policyDetailVO.getPolicySeqID() != null){
				cStmtObject.setLong(POLICY_SEQ_ID,policyDetailVO.getPolicySeqID());
			}//end of if(policyDetailVO.getPolicySeqID() != null)
			else{
				cStmtObject.setLong(POLICY_SEQ_ID,0);
			}//end of else
			if(policyDetailVO.getBatchSeqID() != null){
				cStmtObject.setLong(ENROL_BATCH_SEQ_ID,policyDetailVO.getBatchSeqID());
			}//end of if(policyDetailVO.getBatchSeqID() != null)
			else{
				cStmtObject.setString(ENROL_BATCH_SEQ_ID,null);
			}//end of else
			cStmtObject.setString(ENROL_TYPE_ID,policyDetailVO.getPolicyTypeID());
			if(policyDetailVO.getInsuranceSeqID() != null){
				cStmtObject.setLong(INS_SEQ_ID,policyDetailVO.getInsuranceSeqID());
			}//end of if(policyDetailVO.getInsuranceSeqID() != null)
			else{
				cStmtObject.setString(INS_SEQ_ID,null);
			}//end of else
			if(policyDetailVO.getProductSeqID() != null){
				cStmtObject.setLong(PRODUCT_SEQ_ID,policyDetailVO.getProductSeqID());
			}//end of if(policyDetailVO.getProductSeqID() != null)
			else{
				cStmtObject.setString(PRODUCT_SEQ_ID,null);
			}//end of else
			if(policyDetailVO.getOfficeSeqID() != null){
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,policyDetailVO.getOfficeSeqID());
			}//end of if(policyDetailVO.getOfficeSeqID() != null)
			else{
				cStmtObject.setString(TPA_OFFICE_SEQ_ID,null);
			}//end of else
			cStmtObject.setString(POLICY_NUMBER,policyDetailVO.getPolicyNbr());
			cStmtObject.setString(RENEWAL_POLICY_NUMBER,policyDetailVO.getPrevPolicyNbr());
			if(policyDetailVO.getGroupRegnSeqID() != null){
				cStmtObject.setLong(GROUP_REG_SEQ_ID,policyDetailVO.getGroupRegnSeqID());
			}//end of if(policyDetailVO.getGroupRegnSeqID() != null)
			else{
				cStmtObject.setString(GROUP_REG_SEQ_ID,null);
			}//end of else
			if(policyDetailVO.getRecdDate() != null){
				cStmtObject.setTimestamp(POLICY_RCVD_DATE,new Timestamp(policyDetailVO.getRecdDate().getTime()));
			}//end of if(policyDetailVO.getRecdDate() != null)
			else{
				cStmtObject.setTimestamp(POLICY_RCVD_DATE,null);
			}//end of else
			cStmtObject.setString(POLICY_SUB_GENERAL_TYPE_ID,policyDetailVO.getSubTypeID());
			if(policyDetailVO.getEmployeeCnt() != null){
				cStmtObject.setLong(EMPLOYEE_COUNT,policyDetailVO.getEmployeeCnt());
			}//end of if(policyDetailVO.getEmployeeCnt() != null)
			else{
				cStmtObject.setString(EMPLOYEE_COUNT,null);
			}//end of else
			if(policyDetailVO.getMemberCnt() != null){
				cStmtObject.setLong(TOTAL_MEMBER_COUNT,policyDetailVO.getMemberCnt());
			}//end of if(policyDetailVO.getMemberCnt() != null)
			else{
				cStmtObject.setString(TOTAL_MEMBER_COUNT,null);
			}//end of else
			if(policyDetailVO.getIssueDate() != null){
				cStmtObject.setTimestamp(POLICY_ISSUE_DATE,new Timestamp(policyDetailVO.getIssueDate().getTime()));
			}//end of if(policyDetailVO.getIssueDate() != null)
			else{
				cStmtObject.setTimestamp(POLICY_ISSUE_DATE,null);
			}//end of else
			if(policyDetailVO.getStartDate() != null){
				cStmtObject.setTimestamp(EFFECTIVE_FROM_DATE,new Timestamp(policyDetailVO.getStartDate().getTime()));
			}//end of if(policyDetailVO.getStartDate() != null)
			else{
				cStmtObject.setTimestamp(EFFECTIVE_FROM_DATE,null);
			}//end of else
			if(policyDetailVO.getEndDate() != null){
				cStmtObject.setTimestamp(EFFECTIVE_TO_DATE,new Timestamp(policyDetailVO.getEndDate().getTime()));
			}//end of if(policyDetailVO.getEndDate() != null)
			else{
				cStmtObject.setTimestamp(EFFECTIVE_TO_DATE,null);
			}//end of else
			cStmtObject.setString(PROPOSAL_FORM_YN,policyDetailVO.getProposalFormYN());
			if(policyDetailVO.getDeclarationDate() != null){
				cStmtObject.setTimestamp(DECLARATION_DATE,new Timestamp(policyDetailVO.getDeclarationDate().getTime()));
			}//end of if(policyDetailVO.getDeclarationDate() != null)
			else{
				cStmtObject.setTimestamp(DECLARATION_DATE,null);
			}//end of else
			if(policyDetailVO.getBankSeqID() != null){
                cStmtObject.setLong(BANK_SEQ_ID,policyDetailVO.getBankSeqID());
            }//end of if(policyDetailVO.getBankSeqID() != null)
            else{
                cStmtObject.setString(BANK_SEQ_ID,null);
            }//end of else
            cStmtObject.setString(BANK_ACCOUNT_NO,policyDetailVO.getBankAccNbr());
            cStmtObject.setString(BANK_NAME,policyDetailVO.getBankName());
            cStmtObject.setString(BANK_PHONE_NO,policyDetailVO.getBankPhone());
            cStmtObject.setString(BANK_BRANCH,policyDetailVO.getBranch());
            cStmtObject.setString(BANK_MICR,policyDetailVO.getMICRCode());
            cStmtObject.setString(POLICY_CONDONED_YN,policyDetailVO.getCondonedYN());
			cStmtObject.setString(POLICY_AGENT_CODE,policyDetailVO.getDevOffCode());
			
			cStmtObject.setString(INS_STATUS_GENERAL_TYPE_ID,policyDetailVO.getINSStatusTypeID());
			cStmtObject.setString(POLICY_STATUS_GENERAL_TYPE_ID,policyDetailVO.getPolicyStatusID());
			if(policyDetailVO.getTotalSumInsured() != null){
				cStmtObject.setBigDecimal(TOTAL_SUM_INSURED,policyDetailVO.getTotalSumInsured());
			}//end of if(policyDetailVO.getTotalSumInsured() != null)
			else{
				cStmtObject.setString(TOTAL_SUM_INSURED,null);
			}//end of else
			if(policyDetailVO.getTotalPremium() != null){
				cStmtObject.setBigDecimal(TOTAL_NET_PREMIUM,policyDetailVO.getTotalPremium());
			}//end of if(policyDetailVO.getTotalPremium() != null)
			else{
				cStmtObject.setString(TOTAL_NET_PREMIUM,null);
			}//end of else
			cStmtObject.setString(REMARKS,policyDetailVO.getRemarks());
			cStmtObject.setString(PAN_NUMBER,policyDetailVO.getPANNbr());
            cStmtObject.setString(CLARIFY_GENERAL_TYPE_ID,policyDetailVO.getClarificationTypeID());
            cStmtObject.setTimestamp(CLARIFIED_DATE,policyDetailVO.getClarifiedDate()!=null ? new Timestamp(policyDetailVO.getClarifiedDate().getTime()):null);
			cStmtObject.setString(INSERT_MODE,policyDetailVO.getInsertMode());
            cStmtObject.setString(DMS_REFERENCE_ID,policyDetailVO.getDMSRefID());
			cStmtObject.setLong(USER_SEQ_ID,policyDetailVO.getUpdatedBy());
			//added for koc 1278 PED
			cStmtObject.setString(OTH_TPA_INSURE,policyDetailVO.getOtherTPAInsuredname());
			cStmtObject.setString(OTH_TPA_POLICY_NO,policyDetailVO.getOtherTPAPolicyNr());
			if(policyDetailVO.getTPAStartDate() != null){
				cStmtObject.setTimestamp(OTH_TPA_EFFECTIVE_FROM,new Timestamp(policyDetailVO.getTPAStartDate().getTime()));
			}//end of if(policyDetailVO.getStartDate() != null)
			else{
				cStmtObject.setTimestamp(OTH_TPA_EFFECTIVE_FROM,null);
			}//end of else
			if(policyDetailVO.getTPAEndDate() != null){
				cStmtObject.setTimestamp(OTH_TPA_EFFECTIVE_TO,new Timestamp(policyDetailVO.getTPAEndDate().getTime()));
			}//end of if(policyDetailVO.getEndDate() != null)
			else{
				cStmtObject.setTimestamp(OTH_TPA_EFFECTIVE_TO,null);
			}//end of else
			cStmtObject.setString(OTH_TPA_SI,policyDetailVO.getOtherTPASIValue());
			cStmtObject.setString(OTH_TPA_DELAY_CONDONED,policyDetailVO.getOtherTPADelayCondoned());
			cStmtObject.setString(OTH_TPA_PORTABILITY_APPROVED,policyDetailVO.getOtherTPAPortApproved());
			//added for koc 1278 PED
			cStmtObject.setString(ADDREESS_1,policyDetailVO.getAddress().getAddress1());
			cStmtObject.setString(ADDREESS_2,policyDetailVO.getAddress().getAddress2());
			cStmtObject.setString(ADDREESS_3,policyDetailVO.getAddress().getAddress3());
			cStmtObject.setString(STATE_TYPE_ID,policyDetailVO.getAddress().getStateCode());
			cStmtObject.setString(CITY_TYPE_ID,policyDetailVO.getAddress().getCityCode());
			cStmtObject.setString(PIN_CODE,policyDetailVO.getAddress().getPinCode());
			cStmtObject.setString(COUNTRY_ID,policyDetailVO.getAddress().getCountryCode());
            cStmtObject.setString(EMAIL_ID,policyDetailVO.getAddress().getEmailID());//EMAIL_ID
            cStmtObject.setString(RES_PHONE_NO,policyDetailVO.getAddress().getHomePhoneNbr());//RES_PHONE_NO
            cStmtObject.setString(OFF_PHONE_NO_1,policyDetailVO.getAddress().getPhoneNbr1());//OFF_PHONE_NO_1
            cStmtObject.setString(OFF_PHONE_NO_2,policyDetailVO.getAddress().getPhoneNbr2());//OFF_PHONE_NO_2
            cStmtObject.setString(MOBILE_NO,policyDetailVO.getAddress().getMobileNbr());//MOBILE_NO
            cStmtObject.setString(FAX_NO,policyDetailVO.getAddress().getFaxNbr());//FAX_NO
			if(policyDetailVO.getPolicyGroupSeqID() != null){
				cStmtObject.setLong(POLICY_GROUP_SEQ_ID,policyDetailVO.getPolicyGroupSeqID());
			}//end of if(policyDetailVO.getPolicyGroupSeqID() != null)
			else{
				cStmtObject.setString(POLICY_GROUP_SEQ_ID,null);
			}//end of else
			cStmtObject.setString(TPA_ENROLLMENT_NUMBER,policyDetailVO.getEnrollmentNbr());
			if(policyDetailVO.getAddress().getAddrSeqId() != null){
				cStmtObject.setLong(ENR_ADDRESS_SEQ_ID,policyDetailVO.getAddress().getAddrSeqId());
			}//end of if(policyDetailVO.getAddress().getAddrSeqId() != null)
			else{
				cStmtObject.setString(ENR_ADDRESS_SEQ_ID,null);
			}//end of else
			cStmtObject.setString(INSURED_NAME,policyDetailVO.getPolicyHolderName());
			cStmtObject.setString(BENEFICIARY_NAME,policyDetailVO.getBeneficiaryName());
			cStmtObject.setString(RELSHIP_TYPE_ID,policyDetailVO.getRelationTypeID());
			cStmtObject.setString(POLICY_MODE,strPolicyMode);
			cStmtObject.setString(POLICY_TYPE,strPolicyType);

			if(policyDetailVO.getEndorsementSeqID() != null){
				cStmtObject.setLong(ENDORSEMENT_SEQ_ID,policyDetailVO.getEndorsementSeqID());
			}//end of if(policyDetailVO.getEndorsementSeqID() != null)
			else{
				cStmtObject.setString(ENDORSEMENT_SEQ_ID,null);
			}//end of else

			cStmtObject.setString(ACCOUNT_NAME,policyDetailVO.getBankAccountName());
			cStmtObject.setString(INS_SCHEME,policyDetailVO.getSchemeName());
			cStmtObject.setString(PREVIOUS_SCHEME_NAME,policyDetailVO.getPrevSchemeName());
			cStmtObject.setString(POLICY_REMARKS,policyDetailVO.getPolicyRemarks());
			cStmtObject.setString(DOMICILARY_TYPE_ID,policyDetailVO.getDomicilaryTypeID());
			if(policyDetailVO.getPrevPolicyNbr() != null || policyDetailVO.getPrevPolicyNbr() != "") {
				cStmtObject.setString(PRODUCT_CHANGE_YN,policyDetailVO.getProductChangeYN());
			}//end of if(policyDetailVO.getPrevPolicyNbr() != null || policyDetailVO.getPrevPolicyNbr() != "")
			else{
				cStmtObject.setString(PRODUCT_CHANGE_YN,null);
			}//end of else
			
			if(policyDetailVO.getPrevPolicyNbr() != null || policyDetailVO.getPrevPolicyNbr() != "") {
				cStmtObject.setString(RENEW_USING_DIFF_BRANCH_YN,policyDetailVO.getDOBOChangeYN());
			}//end of if(policyDetailVO.getPrevPolicyNbr() != null || policyDetailVO.getPrevPolicyNbr() != "")
			else{
				cStmtObject.setString(RENEW_USING_DIFF_BRANCH_YN,null);
			}//end of else
			//cStmtObject.setString(AGENT_CODE, policyDetailVO.getDevOffCode());
			
			cStmtObject.setString(ZONE_CODE, policyDetailVO.getZoneCode());
			cStmtObject.setString(DEV_OFFICER_CODE, policyDetailVO.getAgentCode());
			// Changes done for CR KOC1170
			cStmtObject.setString(POLICY_HOLDER_CODE,policyDetailVO.getPolicyHolderCode());
			// End change done for CR KOC1170
			//changes added for rekha for policy renewal
			if(policyDetailVO.getStartDate1() != null){
				cStmtObject.setTimestamp(ACTUAL_POLICY_START_DATE,new Timestamp(policyDetailVO.getStartDate1().getTime()));
			}//end of if(policyDetailVO.getStartDate() != null)
			else{
				cStmtObject.setTimestamp(ACTUAL_POLICY_START_DATE,null);
			}//end of else
			if(policyDetailVO.getEndDate1() != null){
				cStmtObject.setTimestamp(ACTUAL_POLICY_END_DATE,new Timestamp(policyDetailVO.getEndDate1().getTime()));
			}//end of if(policyDetailVO.getEndDate() != null)
			else{
				cStmtObject.setTimestamp(ACTUAL_POLICY_END_DATE,null);
			}//end of else
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(POLICY_SEQ_ID,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.execute();
			lngPolicySeqID = cStmtObject.getLong(POLICY_SEQ_ID);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl savePolicy()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl savePolicy()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngPolicySeqID;
	}//end of savePolicy(PolicyDetailVO policyDetailVO,String strPolicyMode,String strPolicyType)

	/**
	 * This method saves the Review information
	 * @param policyDetailVO the object which contains the Review Details which has to be  saved
	 * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
	 * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
	 * @return PolicyDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PolicyDetailVO saveReview(PolicyDetailVO policyDetailVO,String strPolicyMode,String strPolicyType) throws TTKException {
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName ="";
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveReview);
			if(policyDetailVO.getPolicySeqID() != null){
				cStmtObject.setLong(1,policyDetailVO.getPolicySeqID());
			}//end of if(policyDetailVO.getPolicySeqID() != null)
			else{
				cStmtObject.setString(1,null);
			}//end of else
			if(policyDetailVO.getEndorsementSeqID() != null){
				cStmtObject.setLong(2,policyDetailVO.getEndorsementSeqID());
			}//end of if(policyDetailVO.getEndorsementSeqID() != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(policyDetailVO.getEventSeqID() != null){
				cStmtObject.setLong(3,policyDetailVO.getEventSeqID());
			}//end of if(policyDetailVO.getEventSeqID() != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			if(policyDetailVO.getReviewCount() != null){
				cStmtObject.setInt(4,policyDetailVO.getReviewCount());
			}//end of if(policyDetailVO.getReviewCount() != null)
			else{
				cStmtObject.setString(4,null);
			}//end of else

			if(policyDetailVO.getRequiredReviewCnt() != null){
				cStmtObject.setInt(5,policyDetailVO.getRequiredReviewCnt());
			}//end of if(policyDetailVO.getRequiredReviewCnt() != null)
			else{
				cStmtObject.setString(5,null);
			}//end of else
			cStmtObject.setString(6,strPolicyMode);
			cStmtObject.setString(7,strPolicyType);
			cStmtObject.setLong(8,policyDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(3,Types.BIGINT);//EVENT_SEQ_ID
			cStmtObject.registerOutParameter(4,Types.BIGINT);//REVIEW_COUNT
			cStmtObject.registerOutParameter(5,Types.BIGINT);//REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(9,Types.VARCHAR);//EVENT_NAME
			cStmtObject.registerOutParameter(10,Types.VARCHAR);//REVIEW
			cStmtObject.execute();
			lngEventSeqID = cStmtObject.getLong(3);
			intReviewCount = cStmtObject.getInt(4);
			intRequiredReviewCnt = cStmtObject.getInt(5);
			strEventName = cStmtObject.getString(9);
			strReview = cStmtObject.getString(10);

			policyDetailVO.setEventSeqID(lngEventSeqID);
			policyDetailVO.setReviewCount(intReviewCount);
			policyDetailVO.setRequiredReviewCnt(intRequiredReviewCnt);
			policyDetailVO.setReview(strReview);
			policyDetailVO.setEventName(strEventName);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl saveReview()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl saveReview()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return policyDetailVO;
	}//end of saveReview(PolicyDetailVO policyDetailVO,String strPolicyMode,String strPolicyType)

    /**
     * This method saves the Review information
     * @param endorsementVO the object which contains the Review Details which has to be  saved
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return EndorsementVO object which contains Review Information
     * @exception throws TTKException
     */
    public EndorsementVO saveEndorsementReview(EndorsementVO endorsementVO,String strPolicyMode,String strPolicyType) throws TTKException {
        String strReview = "";
        Long lngEventSeqID = null;
        Integer intReviewCount = null;
        Integer intRequiredReviewCnt = null;
        String strEventName ="";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveReview);
            if(endorsementVO.getPolicySeqID() != null){
                cStmtObject.setLong(1,endorsementVO.getPolicySeqID());
            }//end of if(policyDetailVO.getPolicySeqID() != null)
            else{
                cStmtObject.setString(1,null);
            }//end of else
            if(endorsementVO.getEndorsementSeqID() != null){
                cStmtObject.setLong(2,endorsementVO.getEndorsementSeqID());
            }//end of if(policyDetailVO.getEndorsementSeqID() != null)
            else{
                cStmtObject.setString(2,null);
            }//end of else

            if(endorsementVO.getEventSeqID() != null){
                cStmtObject.setLong(3,endorsementVO.getEventSeqID());
            }//end of if(policyDetailVO.getEventSeqID() != null)
            else{
                cStmtObject.setString(3,null);
            }//end of else

            if(endorsementVO.getReviewCount() != null){
                cStmtObject.setInt(4,endorsementVO.getReviewCount());
            }//end of if(policyDetailVO.getReviewCount() != null)
            else{
                cStmtObject.setString(4,null);
            }//end of else

            if(endorsementVO.getRequiredReviewCnt() != null){
                cStmtObject.setInt(5,endorsementVO.getRequiredReviewCnt());
            }//end of if(policyDetailVO.getRequiredReviewCnt() != null)
            else{
                cStmtObject.setString(5,null);
            }//end of else
            cStmtObject.setString(6,strPolicyMode);
            cStmtObject.setString(7,strPolicyType);
            cStmtObject.setLong(8,endorsementVO.getUpdatedBy());
            cStmtObject.registerOutParameter(3,Types.BIGINT);//EVENT_SEQ_ID
            cStmtObject.registerOutParameter(4,Types.BIGINT);//REVIEW_COUNT
            cStmtObject.registerOutParameter(5,Types.BIGINT);//REQUIRED_REVIEW_COUNT
            cStmtObject.registerOutParameter(9,Types.VARCHAR);//EVENT_NAME
            cStmtObject.registerOutParameter(10,Types.VARCHAR);//REVIEW
            cStmtObject.execute();
            lngEventSeqID = cStmtObject.getLong(3);
            intReviewCount = cStmtObject.getInt(4);
            intRequiredReviewCnt = cStmtObject.getInt(5);
            strEventName = cStmtObject.getString(9);
            strReview = cStmtObject.getString(10);

            endorsementVO.setEventSeqID(lngEventSeqID);
            endorsementVO.setReviewCount(intReviewCount);
            endorsementVO.setRequiredReviewCnt(intRequiredReviewCnt);
            endorsementVO.setReview(strReview);
            endorsementVO.setEventName(strEventName);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "enrollment");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl saveEndorsementReview()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl saveEndorsementReview()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return endorsementVO;
    }//end of saveEndorsementReview(EndorsementVO endorsementVO,String strPolicyMode,String strPolicyType)

	/**
	 * This method returns the ArrayList of PolicyVO's, which contains all the policy details for given batch
	 * @param alSearchCriteria ArrayList which contains the collection of SearchCriteria objects
	 * @return ArrayList of PolicyVO's, which contains all the policy details for given batch
	 */
	public ArrayList getBatchPolicyList(ArrayList alSearchCriteria) throws TTKException {

		Collection<Object> alBatchPolicyList = new ArrayList<Object>();
		PolicyVO policyVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
        String strFlag = "";//'Y' add allowed, 'N' Add not allowed, E error
		try {
			conn=ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBatchPolicyList);
			if(alSearchCriteria != null && alSearchCriteria.size() > 0)
			{
				cStmtObject.setLong(1,Long.parseLong((String)alSearchCriteria.get(0))); // batch seq id
				cStmtObject.setString(2,(String)alSearchCriteria.get(2)); // sort col name
				cStmtObject.setString(3,(String)alSearchCriteria.get(3)); // sort order
				cStmtObject.setInt(4,Integer.parseInt((String)alSearchCriteria.get(4))); // start row nbr.
				cStmtObject.setInt(5,Integer.parseInt((String)alSearchCriteria.get(5))); // end row nbr.
				cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));//ADDED_BY
			}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
			cStmtObject.registerOutParameter(7,Types.VARCHAR);
            cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
			cStmtObject.execute();
			strFlag = cStmtObject.getString(7);
            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            if (rs!=null)
			{
            	if(rs.next())
            	{
	            	do
					{
	                    policyVO = new PolicyVO();
	                    policyVO.setFlag(strFlag);
	                    policyVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getLong("POLICY_SEQ_ID")):null);
						policyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
						policyVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
						policyVO.setEnrollmentDesc(TTKCommon.checkNull(rs.getString("ENROL_DESCRIPTION")));
						policyVO.setEndorsementNbr(TTKCommon.checkNull(rs.getString("CUST_ENDORSEMENT_NUMBER")));
						policyVO.setPrevPolicyNbr(TTKCommon.checkNull(rs.getString("RENEWAL_POLICY_NUMBER")));
						policyVO.setWorkflow(TTKCommon.checkNull(rs.getString("EVENT_NAME")));
						policyVO.setBatchEntryCompleteYN(TTKCommon.checkNull(rs.getString("BATCH_ENTRY_COMPLETE_YN")));
						policyVO.setBatchCompleteYN(TTKCommon.checkNull(rs.getString("BATCH_COMPLETED_YN")));
						policyVO.setEndorsementSeqID(rs.getString("ENDORSEMENT_SEQ_ID")!=null ? new Long(rs.getLong("ENDORSEMENT_SEQ_ID")):null);

						if(TTKCommon.checkNull(rs.getString("POLICY_NOT_LEGIBLE_YN")).equalsIgnoreCase("Y")){
							policyVO.setImageName("OriginalIcon");
							policyVO.setImageTitle("Not Legible");
						}//end of if(rs.getString("POLICY_NOT_LEGIBLE_YN").equalsIgnoreCase("Y"))
						alBatchPolicyList.add(policyVO);
					}while (rs.next());
            	}//end of if(rs.next())
	        	else
	            {
	            	alBatchPolicyList.add(strFlag);//strFlag contains the value to display the add policy button
	            }//end of else if(rs.next())
			}// End of if (rs!=null)
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getBatchPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getBatchPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getBatchPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList) alBatchPolicyList;
	}//end of getBatchPolicyList(ArrayList alSearchCriteria)

	/**
	 * This method returns the PolicyDetailVO which contains the details of batch policy
	 * @param lngPolicySeqID the Policy sequence id for which the details of policy are required
	 * @param lngEndorsementSeqID the Endorsment sequence if for which the details of policy are required
	 * @return PolicyDetailVO object which contains the details of batch policy
	 * @Exception throws TTKException
	 */
	public PolicyDetailVO getBatchPolicy(Long lngPolicySeqID,Long lngEndorsementSeqID,Long lngInsSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PolicyDetailVO policyDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectBatchPolicy);
			if (lngPolicySeqID != null){
				cStmtObject.setLong(1,lngPolicySeqID); //POLICY_SEQ_ID
			}//end of if (lngPolicySeqID != null)
			else {
				cStmtObject.setString(1,null); //POLICY_SEQ_ID
			}//end of else
			if (lngEndorsementSeqID !=null){
				cStmtObject.setLong(2,lngEndorsementSeqID); //ENDORSEMENT_SEQ_ID
			}//end of if (lngEndorsementSeqID !=null)
			else{
				cStmtObject.setString(2,null);//ENDORSEMENT_SEQ_ID
			}//end of else
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				policyDetailVO = new PolicyDetailVO();
				while (rs.next()) {
					policyDetailVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getLong("POLICY_SEQ_ID")):null);
					policyDetailVO.setBatchSeqID(rs.getString("ENROL_BATCH_SEQ_ID")!=null ? new Long(rs.getLong("ENROL_BATCH_SEQ_ID")):null);
					policyDetailVO.setProductSeqID(rs.getString("PRODUCT_SEQ_ID")!=null ? new Long(rs.getLong("PRODUCT_SEQ_ID")):null);
					policyDetailVO.setGroupRegnSeqID(rs.getString("GROUP_REG_SEQ_ID")!=null ? new Long(rs.getLong("GROUP_REG_SEQ_ID")):null);
					policyDetailVO.setInsuranceSeqID(rs.getString("INS_SEQ_ID")!=null ? new Long(rs.getLong("INS_SEQ_ID")):null);
					policyDetailVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					policyDetailVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					policyDetailVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					policyDetailVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					policyDetailVO.setPrevPolicyNbr(TTKCommon.checkNull(rs.getString("RENEWAL_POLICY_NUMBER")));
					policyDetailVO.setEndorsementNbr(TTKCommon.checkNull(rs.getString("CUST_ENDORSEMENT_NUMBER")));
					policyDetailVO.setStartDate(rs.getString("EFFECTIVE_FROM_DATE")!=null ? new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()):null);
					policyDetailVO.setEndDate(rs.getString("EFFECTIVE_TO_DATE")!=null ? new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()):null);
					policyDetailVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					policyDetailVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					policyDetailVO.setEndorseGenTypeID(TTKCommon.checkNull(rs.getString("ENDORSE_GENERAL_TYPE_ID")));
					policyDetailVO.setEndorsementSeqID(rs.getString("ENDORSEMENT_SEQ_ID")!=null ? new Long(rs.getLong("ENDORSEMENT_SEQ_ID")):null);
					policyDetailVO.setPhotoPresentYN(TTKCommon.checkNull(rs.getString("PHOTO_PRESENT_YN")));
					policyDetailVO.setPolicyNotLegibleYN(TTKCommon.checkNull(rs.getString("POLICY_NOT_LEGIBLE_YN")));
					policyDetailVO.setSchemeName(TTKCommon.checkNull(rs.getString("INS_SCHEME")));
					policyDetailVO.setPrevSchemeName(TTKCommon.checkNull(rs.getString("PREVIOUS_SCHEME_NAME")));
					policyDetailVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
					policyDetailVO.setProductChangeYN(TTKCommon.checkNull(rs.getString("RENEW_USING_DIFF_PRODUCT_YN")));
					policyDetailVO.setNoofPhotosRcvd(rs.getString("NUMBER_OF_PHOTOS_RCVD")!=null ? new Integer(rs.getInt("NUMBER_OF_PHOTOS_RCVD")):null);
					policyDetailVO.setDOBOChangeYN(TTKCommon.checkNull(rs.getString("RENEW_USING_DIFF_BRANCH_YN")));
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return policyDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getBatchPolicy()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getBatchPolicy()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getBatchPolicy()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//End of getBatchPolicy(Long lngPolicySeqID,Long lngEndorsementSeqID)

	/**
	 * This method deletes the policy information from the database
	 * @param strSeqID String concatenation of Endorsement and policy sequence id's for which the details has to be deleted
	 *@param lngUserSeqID long value which contains which user has logged in
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteBatchPolicy(String strSeqID,long lngUserSeqID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteBatchPolicy);
			cStmtObject.setString(1, strSeqID);//CONCATENATED STRING OF POLICY_SEQ_IDS
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl deleteBatchPolicy()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl deleteBatchPolicy()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteBatchPolicy(String strSeqID,long lngUserSeqID)

    /**
     * This method deletes the policy information from the database
     * @param alDeletePolicy arraylist which contains the details of policies
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deletePolicy(ArrayList alDeletePolicy) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeletePolicy);
			cStmtObject.setString(1, (String)alDeletePolicy.get(0));//FLAG
			cStmtObject.setString(2, (String)alDeletePolicy.get(1));//CONCATENATED STRING OF POLICY_SEQ_IDS
			if(alDeletePolicy.get(2)!= null)
			{
	 			cStmtObject.setLong(3,(Long)alDeletePolicy.get(2));// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			}//end of 	if(alDeletePolicy.get(2)!= null)
			else
			{
				cStmtObject.setString(3, null);// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			}// end of else

			cStmtObject.setString(4, (String)alDeletePolicy.get(3));//-- Mode can be 'ENM','END'
			cStmtObject.setString(5, (String)alDeletePolicy.get(4));//-- 'IP',IG,CP,NC
			cStmtObject.setLong(6,(Long)alDeletePolicy.get(5));//ADDED_BY
			cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl deletePolicy()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl deletePolicy()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// end of deletePolicy(ArrayList alDeletePolicy)

	/**
	 * This method saves the Batch policy information
	 * @param policyDetailVO the object which contains the policy details  which has to be updated
	 * @return int the value which returns zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveBatchPolicy(PolicyDetailVO policyDetailVO) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveBatchPolicy);
			cStmtObject.setLong(1,policyDetailVO.getPolicySeqID() != null ? policyDetailVO.getPolicySeqID():0); //POLICY_SEQ_ID
			cStmtObject.setLong(2,policyDetailVO.getBatchSeqID()); //ENROL_BATCH_SEQ_ID
			cStmtObject.setString(3,policyDetailVO.getPolicyTypeID()); //ENROL_TYPE_ID
			cStmtObject.setLong(4,policyDetailVO.getInsuranceSeqID());//INS_SEQ_ID
			cStmtObject.setLong(5,policyDetailVO.getProductSeqID());//PRODUCT_SEQ_ID
			cStmtObject.setString(6, policyDetailVO.getPolicyNbr());//POLICY_NUMBER
			cStmtObject.setString(7, policyDetailVO.getPrevPolicyNbr());//RENEWAL_POLICY_NUMBER
			cStmtObject.setString(8,policyDetailVO.getEndorseGenTypeID());//ENDORSE_GENERAL_TYPE_ID
			if (policyDetailVO.getGroupRegnSeqID()!=null){
				cStmtObject.setLong(9,policyDetailVO.getGroupRegnSeqID());//GROUP_REG_SEQ_ID
			}//end of if (policyDetailVO.getGroupRegnSeqID()!=null)
			else{
				cStmtObject.setString(9,null);//GROUP_REG_SEQ_ID
			}//end of else
			if (policyDetailVO.getEndorsementSeqID()!=null){
				cStmtObject.setLong(10,policyDetailVO.getEndorsementSeqID());//ENDORSEMENT_SEQ_ID
			}//end of if (policyDetailVO.getEndorsementSeqID()!=null)
			else{
				cStmtObject.setString(10,null);//ENDORSEMENT_SEQ_ID
			}//end of else
			cStmtObject.setString(11,policyDetailVO.getEndorsementNbr());//CUST_ENDORSEMENT_NUMBER
			cStmtObject.setString(12,policyDetailVO.getPhotoPresentYN());
			cStmtObject.setString(13,policyDetailVO.getPolicyNotLegibleYN());
			cStmtObject.setString(14,policyDetailVO.getSchemeName());
			cStmtObject.setString(15,policyDetailVO.getPrevSchemeName());

			if(policyDetailVO.getOfficeSeqID() != null){
				cStmtObject.setLong(16,policyDetailVO.getOfficeSeqID());
			}//end of if(policyDetailVO.getOfficeSeqID() != null)
			else{
				cStmtObject.setString(16,null);
			}//end of else

			cStmtObject.setLong(17,policyDetailVO.getUpdatedBy());//ADDED_BY
			if(policyDetailVO.getPrevPolicyNbr() != null || policyDetailVO.getPrevPolicyNbr() != "") {
				cStmtObject.setString(18,policyDetailVO.getProductChangeYN());
			}//end of if(policyDetailVO.getPrevPolicyNbr() != null || policyDetailVO.getPrevPolicyNbr() != "")
			else{
				cStmtObject.setString(18,null);
			}//end of else
			
			if(policyDetailVO.getNoofPhotosRcvd() != null){
				cStmtObject.setInt(19,policyDetailVO.getNoofPhotosRcvd());
			}//end of if(policyDetailVO.getNoofPhotosRcvd() != null)
			else{
				cStmtObject.setString(19,null);
			}//end of else
			
			cStmtObject.setString(20, policyDetailVO.getDOBOChangeYN());
			
			cStmtObject.registerOutParameter(21, Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(1, Types.BIGINT);//POLICY_SEQ_ID
			cStmtObject.execute();
			iResult = cStmtObject.getInt(21);//ROW_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl saveBatchPolicy()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl saveBatchPolicy()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// End of saveBatchPolicy(PolicyDetailVO policyDetailVO)

	/**
	 * This method returns the endorsement details of the policy
	 * @param lngEndorsementSeqID the Endorsement sequence id for which the Endorsement Details has to be fetched
	 * @return EndorsementVO the object which contains the Endorsement  details of policy
	 * @exception throws TTKException
	 */
	public EndorsementVO getEndorsement(Long lngEndorsementSeqID) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		EndorsementVO endorsementVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetEndorsement);
			if (lngEndorsementSeqID !=null)
				cStmtObject.setLong(1,lngEndorsementSeqID); //ENDORSEMENT_SEQ_ID
			else
				cStmtObject.setString(1,null);//ENDORSEMENT_SEQ_ID
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				endorsementVO = new EndorsementVO();
				while (rs.next()) {
					endorsementVO.setEnrollBatchSeqID(rs.getString("ENROL_BATCH_SEQ_ID")!=null ? new Long(rs.getLong("ENROL_BATCH_SEQ_ID")):null);
					endorsementVO.setEndorsementNbr(TTKCommon.checkNull(rs.getString("ENDORSEMENT_NUMBER")));
					endorsementVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getLong("POLICY_SEQ_ID")):null);
					endorsementVO.setEndorsementSeqID(rs.getString("ENDORSEMENT_SEQ_ID")!=null ? new Long(rs.getLong("ENDORSEMENT_SEQ_ID")):null);
					endorsementVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					endorsementVO.setCompanyName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					endorsementVO.setProductSeqID(rs.getString("PRODUCT_SEQ_ID")!=null ? new Long(rs.getLong("PRODUCT_SEQ_ID")):null);
					endorsementVO.setInsuranceSeqID(rs.getString("INS_SEQ_ID")!=null ? new Long(rs.getLong("INS_SEQ_ID")):null);
					endorsementVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					endorsementVO.setRecdDate(rs.getString("RECEIVED_DATE")!=null ? new Date(rs.getTimestamp("RECEIVED_DATE").getTime()):null);
					endorsementVO.setEffectiveDate(rs.getString("EFFECTIVE_DATE")!=null ? new Date(rs.getTimestamp("EFFECTIVE_DATE").getTime()):null);
					endorsementVO.setCustEndorsementNbr(TTKCommon.checkNull(rs.getString("CUST_ENDORSEMENT_NUMBER")));
					endorsementVO.setAddMemberCnt(rs.getString("ADD_NO_OF_MEMBERS")!=null ? new Integer(rs.getInt("ADD_NO_OF_MEMBERS")):null);
					endorsementVO.setAddPremium(rs.getString("ADD_PREMIUM")!=null ? new BigDecimal(rs.getString("ADD_PREMIUM")):null);
					endorsementVO.setAddPremiumInsured(rs.getString("ADD_SUM_INSURED")!=null ? new BigDecimal(rs.getString("ADD_SUM_INSURED")):null);
					endorsementVO.setAddMemberCntStatus(rs.getString("V_ADDED_MEMBERS")!=null ? new Integer(rs.getInt("V_ADDED_MEMBERS")):null);
					endorsementVO.setAddPremiumStatus(rs.getString("V_ADDED_PREMIUM")!=null ? new BigDecimal(rs.getString("V_ADDED_PREMIUM")):null);
					endorsementVO.setAddPremiumInsuredStatus(rs.getString("V_ADDED_SUM")!=null ? new BigDecimal(rs.getString("V_ADDED_SUM")):null);
					endorsementVO.setDeletedMemberCnt(rs.getString("DEL_NO_OF_MEMBERS")!=null ? new Integer(rs.getInt("DEL_NO_OF_MEMBERS")):null);
					endorsementVO.setDeductPremium(rs.getString("DEL_PREMIUM")!=null ? new BigDecimal(rs.getString("DEL_PREMIUM")):null);
					endorsementVO.setDeductSumInsured(rs.getString("DEL_SUM_INSURED")!=null ? new BigDecimal(rs.getString("DEL_SUM_INSURED")):null);
					endorsementVO.setDeletedMemberCntStatus(rs.getString("V_DELETED_MEMBERS")!=null ? new Integer(rs.getInt("V_DELETED_MEMBERS")):null);
					endorsementVO.setDeductPremiumStatus(rs.getString("V_DELETED_PREMIUM")!=null ? new BigDecimal(rs.getString("V_DELETED_PREMIUM")):null);
					endorsementVO.setDeductSumInsuredStatus(rs.getString("V_DELETED_SUM")!=null ? new BigDecimal(rs.getString("V_DELETED_SUM")):null);
					endorsementVO.setUpdateMemberCnt(rs.getString("MOD_NO_OF_MEMBERS")!=null ? new Integer(rs.getInt("MOD_NO_OF_MEMBERS")):null);
					endorsementVO.setUpdateMemberCntStatus(rs.getString("V_MODIFIED_MEMBERS")!=null ? new Integer(rs.getInt("V_MODIFIED_MEMBERS")):null);
					endorsementVO.setUpdatePremium(rs.getString("MOD_PREMIUM_RAISE")!=null ? new BigDecimal(rs.getString("MOD_PREMIUM_RAISE")):null);
					endorsementVO.setUpdatePremiumStatus(rs.getString("V_MOD_ADDED_PREMIUM")!=null ? new BigDecimal(rs.getString("V_MOD_ADDED_PREMIUM")):null);
					endorsementVO.setUpdatePremiumInsured(rs.getString("MOD_INSURED_RAISE")!=null ? new BigDecimal(rs.getString("MOD_INSURED_RAISE")):null);
					endorsementVO.setUpdatePremiumInsuredStatus(rs.getString("V_MOD_ADDED_SUM")!=null ? new BigDecimal(rs.getString("V_MOD_ADDED_SUM")):null);
					endorsementVO.setUpdateDeductPremium(rs.getString("MOD_PREMIUM_DEDUCT")!=null ? new BigDecimal(rs.getString("MOD_PREMIUM_DEDUCT")):null);
					endorsementVO.setUpdateDeductPremiumStatus(rs.getString("V_MOD_DEDUCTED_PREMIUM")!=null ? new BigDecimal(rs.getString("V_MOD_DEDUCTED_PREMIUM")):null);
					endorsementVO.setUpdateDeductSumInsured(rs.getString("MOD_INSURED_DEDUCT")!=null ? new BigDecimal(rs.getString("MOD_INSURED_DEDUCT")):null);
					endorsementVO.setUpdateDeductSumInsuredStatus(rs.getString("V_MOD_DEDUCTED_SUM")!=null ? new BigDecimal(rs.getString("V_MOD_DEDUCTED_SUM")):null);
					endorsementVO.setEndorsementCompletedYN(TTKCommon.checkNull(rs.getString("COMPLETED_YN")));
					endorsementVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
					endorsementVO.setReview(TTKCommon.checkNull(rs.getString("REVIEW")));
					endorsementVO.setEventSeqID(rs.getString("EVENT_SEQ_ID")!=null ? new Long(rs.getLong("EVENT_SEQ_ID")):null);
					endorsementVO.setReviewCount(rs.getString("REVIEW_COUNT")!=null ? new Integer(rs.getInt("REVIEW_COUNT")):null);
					endorsementVO.setRequiredReviewCnt(rs.getString("REQUIRED_REVIEW_COUNT")!= null ? new Integer(rs.getInt("REQUIRED_REVIEW_COUNT")):null);
					endorsementVO.setEventName(TTKCommon.checkNull(rs.getString("EVENT_NAME")));
					endorsementVO.setEndorseGenTypeID(TTKCommon.checkNull(rs.getString("ENDORSE_GENERAL_TYPE_ID")));
					endorsementVO.setEndorseTypeDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					endorsementVO.setAddEmployeeCnt(rs.getString("ADD_NO_OF_GROUPS")!=null ? new Integer(rs.getInt("ADD_NO_OF_GROUPS")):null);
					endorsementVO.setAddEmployeeCntStatus(rs.getString("V_ADDED_GROUPS")!=null ? new Integer(rs.getInt("V_ADDED_GROUPS")):null);
					endorsementVO.setDeletedEmployeeCnt(rs.getString("DEL_NO_OF_GROUPS")!=null ? new Integer(rs.getInt("DEL_NO_OF_GROUPS")):null);
					endorsementVO.setDeletedEmployeeCntStatus(rs.getString("V_DELETED_GROUPS")!=null ? new Integer(rs.getInt("V_DELETED_GROUPS")):null);
					endorsementVO.setPolicyCancelYN(TTKCommon.checkNull(rs.getString("DEL_POLICY_YN")));
					endorsementVO.setModPolicyYN(TTKCommon.checkNull(rs.getString("MOD_POLICY_YN")));
					endorsementVO.setPolicyTypeID(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					endorsementVO.setDMSRefID(TTKCommon.checkNull(rs.getString("DMS_REFERENCE_ID")));
					endorsementVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					endorsementVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					endorsementVO.setPolicyYN(TTKCommon.checkNull(rs.getString("POLICY_YN")));
					endorsementVO.setAddDomiciliary(rs.getString("ADD_DOMICILARY_AMOUNT")!=null ? new BigDecimal(rs.getString("ADD_DOMICILARY_AMOUNT")):null);
					endorsementVO.setAddDomiciliaryStatus(rs.getString("V_ADD_DOMICILARY_AMOUNT")!=null ? new BigDecimal(rs.getString("V_ADD_DOMICILARY_AMOUNT")):null);
					endorsementVO.setDeductDomiciliary(rs.getString("DEDUCT_DOMICILARY_AMOUNT")!=null ? new BigDecimal(rs.getString("DEDUCT_DOMICILARY_AMOUNT")):null);
					endorsementVO.setDeductDomiciliaryStatus(rs.getString("V_DEDUCT_DOMICILARY_AMOUNT")!=null ? new BigDecimal(rs.getString("V_DEDUCT_DOMICILARY_AMOUNT")):null);
					endorsementVO.setCancelDomiciliary(rs.getString("DEL_DOMICILARY_AMOUNT")!=null ? new BigDecimal(rs.getString("DEL_DOMICILARY_AMOUNT")):null);
					endorsementVO.setCancelDomiciliaryStatus(rs.getString("V_DEL_DOMICILARY_AMOUNT")!=null ? new BigDecimal(rs.getString("V_DEL_DOMICILARY_AMOUNT")):null);
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return endorsementVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getEndorsement()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getEndorsement()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getEndorsement()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// End of getEndorsement(Long lngEndorsementSeqID)

	/**
	 * This method saves saves/updates the endorsement details of the policy
	 * @param endorsementVO the endorsement details of the policy which has to be saved/updated
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveEndorsement(EndorsementVO endorsementVO) throws TTKException{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveEndorsement);
			
			cStmtObject.setLong(1,endorsementVO.getEndorsementSeqID() != null ? endorsementVO.getEndorsementSeqID():0); //ENDORSEMENT_SEQ_ID
			log.info("ENDORSEMENT_SEQ_ID.."+endorsementVO.getEndorsementSeqID());
			if (endorsementVO.getEnrollBatchSeqID()!= null){
				cStmtObject.setLong(2,endorsementVO.getEnrollBatchSeqID()); //ENROL_BATCH_SEQ_ID
			}//end of if (endorsementVO.getEnrollBatchSeqID()!= null)
			else{
				cStmtObject.setLong(2,0); //ENROL_BATCH_SEQ_ID
			}//end of else
			cStmtObject.setString(3,endorsementVO.getEndorsementNbr()); //ENDORSEMENT_NUMBER
			cStmtObject.setString(4,endorsementVO.getCustEndorsementNbr());//CUST_ENDORSEMENT_NUMBER
			cStmtObject.setString(5,endorsementVO.getEndorseGenTypeID());//ENDORSE_GENERAL_TYPE_ID
			cStmtObject.setString(6,endorsementVO.getPolicyNbr());//POLICY_NUMBER
			cStmtObject.setTimestamp(7,endorsementVO.getRecdDate()!=null ? new Timestamp(endorsementVO.getRecdDate().getTime()):null);//RECEIVED_DATE
			cStmtObject.setTimestamp(8,endorsementVO.getEffectiveDate()!=null ? new Timestamp(endorsementVO.getEffectiveDate().getTime()):null);//EFFECTIVE_DATE

			if (endorsementVO.getAddEmployeeCnt()!=null){
				cStmtObject.setInt(9,endorsementVO.getAddEmployeeCnt());//ADD_NO_OF_GROUPS
			}//end of if (endorsementVO.getAddEmployeeCnt()!=null)
			else {
				cStmtObject.setInt(9, 0);//ADD_NO_OF_GROUPS
			}//end of else
			if (endorsementVO.getAddMemberCnt()!=null){
				cStmtObject.setInt(10,endorsementVO.getAddMemberCnt());//ADD_NO_OF_MEMBERS
			}//end of if (endorsementVO.getAddMemberCnt()!=null)
			else {
				cStmtObject.setInt(10, 0);//ADD_NO_OF_MEMBERS
			}//end of else
			if (endorsementVO.getAddPremiumInsured()!=null){
				cStmtObject.setBigDecimal(11,endorsementVO.getAddPremiumInsured());//ADD_SUM_INSURED
			}//end of if (endorsementVO.getAddPremiumInsured()!=null)
			else{
				cStmtObject.setBigDecimal(11, null);//ADD_SUM_INSURED
			}//end of else
			if (endorsementVO.getAddPremium()!=null){
				cStmtObject.setBigDecimal(12,endorsementVO.getAddPremium());//ADD_PREMIUM
			}//end of if (endorsementVO.getAddPremium()!=null)
			else{
				cStmtObject.setBigDecimal(12, null);//ADD_PREMIUM
			}//end of else

			cStmtObject.setString(13,endorsementVO.getPolicyCancelYN());
			cStmtObject.setString(14,endorsementVO.getModPolicyYN());
			if (endorsementVO.getDeletedEmployeeCnt()!=null){
				cStmtObject.setInt(15,endorsementVO.getDeletedEmployeeCnt());//DEL_NO_OF_GROUPS
			}//end of if (endorsementVO.getDeletedMemberCnt()!=null)
			else{
				cStmtObject.setInt(15, 0);//DEL_NO_OF_GROUPS
			}//end of else

			if (endorsementVO.getDeletedMemberCnt()!=null){
				cStmtObject.setInt(16,endorsementVO.getDeletedMemberCnt());//DEL_NO_OF_MEMBERS
			}//end of if (endorsementVO.getDeletedMemberCnt()!=null)
			else{
				cStmtObject.setInt(16, 0);//DEL_NO_OF_MEMBERS
			}//end of else
			if (endorsementVO.getDeductSumInsured()!=null){
				cStmtObject.setBigDecimal(17,endorsementVO.getDeductSumInsured());//DEL_SUM_INSURED
			}//end of if (endorsementVO.getDeductSumInsured()!=null)
			else{
				cStmtObject.setBigDecimal(17, null);//DEL_SUM_INSURED
			}//end of else
			if (endorsementVO.getDeductPremium()!=null){
				cStmtObject.setBigDecimal(18,endorsementVO.getDeductPremium());//DEL_PREMIUM
			}//end of if (endorsementVO.getDeductPremium()!=null)
			else{
				cStmtObject.setBigDecimal(18, null);//DEL_SUM_INSURED
			}//end of else
			cStmtObject.setString(19,endorsementVO.getRemarks());//REMARKS
			cStmtObject.setLong(20, endorsementVO.getInsuranceSeqID());//INS_SEQ_ID
			cStmtObject.setLong(21, endorsementVO.getProductSeqID());//PRODUCT_SEQ_ID
			if (endorsementVO.getUpdateMemberCnt()!=null){
				cStmtObject.setInt(22,endorsementVO.getUpdateMemberCnt());//MOD_NO_OF_MEMBERS
			}//end of if (endorsementVO.getUpdateMemberCnt()!=null)
			else {
				cStmtObject.setInt(22, 0);//MOD_NO_OF_MEMBERS
			}//end of else
			if (endorsementVO.getUpdatePremium()!=null){
				cStmtObject.setBigDecimal(23,endorsementVO.getUpdatePremium());//MOD_PREMIUM_RAISE
			}//end of if (endorsementVO.getUpdatePremium()!=null)
			else{
				cStmtObject.setBigDecimal(23, null);//MOD_PREMIUM_RAISE
			}//end of else
			if (endorsementVO.getUpdatePremiumInsured()!=null){
				cStmtObject.setBigDecimal(24,endorsementVO.getUpdatePremiumInsured());//MOD_INSURED_RAISE
			}//end of if (endorsementVO.getUpdatePremiumInsured()!=null)
			else{
				cStmtObject.setBigDecimal(24, null);//MOD_INSURED_RAISE
			}//end of else
			if (endorsementVO.getUpdateDeductPremium()!=null){
				cStmtObject.setBigDecimal(25,endorsementVO.getUpdateDeductPremium());//MOD_PREMIUM_DEDUCT
			}//end of if (endorsementVO.getUpdateDeductPremium()!=null)
			else{
				cStmtObject.setBigDecimal(25, null);//MOD_PREMIUM_DEDUCT
			}//end of else
			if (endorsementVO.getUpdateDeductSumInsured()!=null){
				cStmtObject.setBigDecimal(26,endorsementVO.getUpdateDeductSumInsured());//MOD_INSURED_DEDUCT
			}//end of if (endorsementVO.getUpdateDeductSumInsured()!=null)
			else{
				cStmtObject.setBigDecimal(26, null);//MOD_INSURED_DEDUCT
			}//end of else
			
			if (endorsementVO.getAddDomiciliary()!=null){
				cStmtObject.setBigDecimal(27,endorsementVO.getAddDomiciliary());//ADD_DOMICILARY_AMOUNT
			}//end of if (endorsementVO.getAddDomiciliary()!=null)
			else{
				cStmtObject.setBigDecimal(27, null);//ADD_DOMICILARY_AMOUNT
			}//end of else
			
			if (endorsementVO.getDeductDomiciliary()!=null){
				cStmtObject.setBigDecimal(28,endorsementVO.getDeductDomiciliary());//DEDUCT_DOMICILARY_AMOUNT
			}//end of if (endorsementVO.getDeductDomiciliary()!=null)
			else{
				cStmtObject.setBigDecimal(28, null);//DEDUCT_DOMICILARY_AMOUNT
			}//end of else
			
			if (endorsementVO.getCancelDomiciliary()!=null){
				cStmtObject.setBigDecimal(29,endorsementVO.getCancelDomiciliary());//DEL_DOMICILARY_AMOUNT
			}//end of if (endorsementVO.getCancelDomiciliary()!=null)
			else{
				cStmtObject.setBigDecimal(29, null);//DEL_DOMICILARY_AMOUNT
			}//end of else
			
			cStmtObject.setLong(30, endorsementVO.getUpdatedBy());//ADDED_BY
			cStmtObject.setString(31,endorsementVO.getPolicyType() );//POLICY TYPE
			cStmtObject.registerOutParameter(32, Types.INTEGER);//ROW_PROCESSED
			cStmtObject.registerOutParameter(1, Types.BIGINT);//ENDORSEMENT_SEQ_ID
			cStmtObject.registerOutParameter(3, Types.CHAR);//ENDORSEMENT_SEQ_ID
			cStmtObject.execute();
			iResult = cStmtObject.getInt(32);//ROW_PROCESSED
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl saveEndorsement()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl saveEndorsement()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// End of saveEndorsement(EndorsementVO endorsementVO)

	/**
	 * This method returns the Arraylist of PolicyVO's  which contains Other Policies details
	 * @param lngMemberSeqID long value which contains the member sequence id
	 * @return ArrayList of PolicyVO object which contains Other Policies details
	 * @exception throws TTKException
	 */
	public ArrayList getOtherPolicyList(long lngMemberSeqID) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PolicyVO policyVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strOtherPolicyList);
			cStmtObject.setLong(1,lngMemberSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while (rs.next()) {
					policyVO = new PolicyVO();
					if(rs.getString("MEM_ASSOC_SEQ_ID") != null){
						policyVO.setMemAssocSeqID(new Long(rs.getLong("MEM_ASSOC_SEQ_ID")));
					}//end of if(rs.getString("MEM_ASSOC_SEQ_ID") != null)
					if(rs.getString("MEMBER_SEQ_ID") != null){
						policyVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
					if(rs.getString("MEM_MATCH_SEQ_ID") != null){
						policyVO.setMemMatchSeqID(new Long(rs.getLong("MEM_MATCH_SEQ_ID")));
					}//end of if(rs.getString("MEM_MATCH_SEQ_ID") != null)
					if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
						policyVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
					}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)
					policyVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					policyVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					policyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					policyVO.setAssociateYN(TTKCommon.checkNull(rs.getString("ASSOCIATE_YN")));
					if((TTKCommon.checkNull(rs.getString("ASSOCIATE_YN")).equalsIgnoreCase("Y"))){
						policyVO.setImageName("AssoicateIcon");
						policyVO.setImageTitle("Associated");
					}//end of if(rs.getString("ASSOCIATE_YN").equalsIgnoreCase("Y"))
					alResultList.add(policyVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getOtherPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getOtherPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getOtherPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getOtherPolicyList(long lngMemberSeqID)

	/**
	 * This method returns the ArrayList which contains compare policy details
	 * @param strMemberSeqID String object for comparing Policies
	 * @return ArrayList object which contains compare policy details
	 * @Exception throws TTKException
	 */
	public ArrayList comparePolicy(String strMemberSeqID) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs1 = null;
        ResultSet rs2 = null;
		MemberDetailVO memberDetailVO = null;
		MemberAddressVO memberAddressVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strComparePolicy);
			cStmtObject.setString(1,strMemberSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(2);
            rs2 = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs1 != null){
				while (rs1.next()){
					memberDetailVO = new MemberDetailVO();
					memberAddressVO = new MemberAddressVO();
					memberDetailVO.setName(TTKCommon.checkNull(rs1.getString("MEM_NAME")));
					memberDetailVO.setGenderTypeID(TTKCommon.checkNull(rs1.getString("GENDER")));
					if(rs1.getString("MEM_DOB") != null){
						memberDetailVO.setDOB(new java.util.Date(rs1.getTimestamp("MEM_DOB").getTime()));
					}//end of if(rs1.getString("MEM_DOB") != null)
					if(rs1.getString("MEM_AGE") != null){
						memberDetailVO.setAge(new Integer(rs1.getInt("MEM_AGE")));
					}//end of if(rs1.getString("MEM_AGE") != null)
                    memberDetailVO.setOccupation(TTKCommon.checkNull(rs1.getString("OCCUPATION")));
					if(rs1.getString("DATE_OF_INCEPTION") != null){
						memberDetailVO.setDateOfIncept(new java.util.Date(rs1.getTimestamp("DATE_OF_INCEPTION").getTime()));
					}//end of if(rs1.getString("DATE_OF_INCEPTION") != null)
					memberAddressVO.setAddress1(TTKCommon.checkNull(rs1.getString("ADDRESS_1")));
					memberAddressVO.setAddress2(TTKCommon.checkNull(rs1.getString("ADDRESS_2")));
					memberAddressVO.setAddress3(TTKCommon.checkNull(rs1.getString("ADDRESS_3")));
                    memberAddressVO.setCityDesc(TTKCommon.checkNull(rs1.getString("CITY_DESCRIPTION")));
                    memberAddressVO.setStateName(TTKCommon.checkNull(rs1.getString("STATE_NAME")));
					memberAddressVO.setPinCode(TTKCommon.checkNull(rs1.getString("PIN_CODE")));
                    memberAddressVO.setCountryName(TTKCommon.checkNull(rs1.getString("COUNTRY_NAME")));
					memberDetailVO.setMemberAddressVO(memberAddressVO);
					alResultList.add(memberDetailVO);
				}//end of while(rs1.next())
			}//end of if(rs1 != null)
            if(rs2 != null){
                while (rs2.next()){
                    memberDetailVO = new MemberDetailVO();
                    memberAddressVO = new MemberAddressVO();
                    memberDetailVO.setName(TTKCommon.checkNull(rs2.getString("MEM_NAME")));
                    memberDetailVO.setGenderTypeID(TTKCommon.checkNull(rs2.getString("GENDER")));
                    if(rs2.getString("MEM_DOB") != null){
                        memberDetailVO.setDOB(new java.util.Date(rs2.getTimestamp("MEM_DOB").getTime()));
                    }//end of if(rs2.getString("MEM_DOB") != null)
                    if(rs2.getString("MEM_AGE") != null){
                        memberDetailVO.setAge(new Integer(rs2.getInt("MEM_AGE")));
                    }//end of if(rs2.getString("MEM_AGE") != null)
                    memberDetailVO.setOccupation(TTKCommon.checkNull(rs2.getString("OCCUPATION")));
                    if(rs2.getString("DATE_OF_INCEPTION") != null){
                        memberDetailVO.setDateOfIncept(new java.util.Date(rs2.getTimestamp("DATE_OF_INCEPTION").getTime()));
                    }//end of if(rs2.getString("DATE_OF_INCEPTION") != null)
                    memberAddressVO.setAddress1(TTKCommon.checkNull(rs2.getString("ADDRESS_1")));
                    memberAddressVO.setAddress2(TTKCommon.checkNull(rs2.getString("ADDRESS_2")));
                    memberAddressVO.setAddress3(TTKCommon.checkNull(rs2.getString("ADDRESS_3")));
                    memberAddressVO.setCityDesc(TTKCommon.checkNull(rs2.getString("CITY_DESCRIPTION")));
                    memberAddressVO.setStateName(TTKCommon.checkNull(rs2.getString("STATE_NAME")));
                    memberAddressVO.setPinCode(TTKCommon.checkNull(rs2.getString("PIN_CODE")));
                    memberAddressVO.setCountryName(TTKCommon.checkNull(rs2.getString("COUNTRY_NAME")));
                    memberDetailVO.setMemberAddressVO(memberAddressVO);
                    alResultList.add(memberDetailVO);
                }//end of while(rs2.next())
            }//end of if(rs2 != null)
			return (ArrayList)alResultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the second result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the second Resultset in PolicyDAOImpl comparePolicy()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in PolicyDAOImpl comparePolicy()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally{ // Even if first result set is not closed, control reaches here. Try closing the statement now.
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in PolicyDAOImpl comparePolicy()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in PolicyDAOImpl comparePolicy()",sqlExp);
								throw new TTKException(sqlExp, "enrollment");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally
				}//end of finally 
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs1 = null;
	            rs2 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of comparePolicy(String strMemberSeqID)

	/**
     * This method associates the other policy infomation for the given sequence id's
     * @param lngMemAssocSeqID long value for which policy  to be associated
     * @param lngUserSeqID long value for which user has logged in
     * @param strFlag String which is for Associated/Un-Associated
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exceptionthrows TTKException
     */
    public int associatePolicy(long lngMemAssocSeqID,long lngUserSeqID,String strFlag) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociatePolicy);
			cStmtObject.setLong(1, lngMemAssocSeqID);
			cStmtObject.setLong(2, lngUserSeqID);
            cStmtObject.setString(3,strFlag);
			cStmtObject.registerOutParameter(4, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl associatePolicy()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl associatePolicy()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of associatePolicy(long lngMemAssocSeqID,long lngUserSeqID,String strFlag)

	/**
	 * This method deletes the policy information from the database
	 * @param alDeleteGeneral the details of policy sequence id's which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteGeneral(ArrayList alDeleteGeneral) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteGeneral);
			cStmtObject.setString(1, (String)alDeleteGeneral.get(0));//FLAG
			cStmtObject.setString(2, (String)alDeleteGeneral.get(1));//CONCATENATED STRING OF POLICY_SEQ_IDS
			cStmtObject.setLong(3,(Long)alDeleteGeneral.get(2));// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			cStmtObject.setString(4, (String)alDeleteGeneral.get(3));//-- Mode can be 'ENM','END'
			cStmtObject.setString(5, (String)alDeleteGeneral.get(4));//-- 'IP',IG,CP,NC
			cStmtObject.setLong(6,(Long)alDeleteGeneral.get(5));//ADDED_BY
			cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl deleteGeneral()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl deleteGeneral()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}// end of deleteGeneral(ArrayList alDeleteGeneral)

 	/**
	 * This method deletes the Endorsement information from the database
	 * @param alDeleteEndorsement the arraylist of endorsement details which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteEndorsement(ArrayList alDeleteEndorsement) throws TTKException
    {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteEndorsement);
            cStmtObject.setString(1, (String)alDeleteEndorsement.get(0));//FLAG ENDORSEMENT
			cStmtObject.setString(2, (String)alDeleteEndorsement.get(1));//CONCATENATED STRING OF POLICY_SEQ_IDS
			if (alDeleteEndorsement.get(2)!=null)
			{
			cStmtObject.setLong(3,(Long)alDeleteEndorsement.get(2));// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			}
			else
			{
				cStmtObject.setString(3,null);// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			}//else
			cStmtObject.setString(4, alDeleteEndorsement.get(3)!=null ? (String)alDeleteEndorsement.get(3):null);//-- Mode can be 'ENM','END'
			cStmtObject.setString(5, alDeleteEndorsement.get(3)!=null ? (String)alDeleteEndorsement.get(4):null);//-- 'IP',IG,CP,NC
			cStmtObject.setLong(6,(Long)alDeleteEndorsement.get(5));//ADDED_BY
			cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "enrollment");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl deleteEndorsement()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl deleteEndorsement()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }// end of deleteEndorsement(ArrayList alDeleteEndorsement)
	/**
     * This method returns the Arraylist of PolicyVO's  which contains the details of enrollment policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PolicyVO object which contains the details of enrollment policy details
     * @exception throws TTKException
     */
    public ArrayList getOnlinePolicyList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        OnlineAccessPolicyVO onlinePolicyVO = null;
        ResultSet rs = null;

        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strOnlinePolicyList);
            oCstmt.setString(1,(String)alSearchCriteria.get(0));
            oCstmt.setString(2,(String)alSearchCriteria.get(1));
            oCstmt.setString(3,(String)alSearchCriteria.get(2));
            oCstmt.setString(4,(String)alSearchCriteria.get(3));
            oCstmt.setString(5,(String)alSearchCriteria.get(4));

            if(alSearchCriteria.get(5)!=null)
            {
                oCstmt.setLong(6,(Long)alSearchCriteria.get(5));
            }//end of if(alSearchCriteria.get(5)!=null)
            else
            {
                oCstmt.setString(6,null);
            }//end of else
            oCstmt.registerOutParameter(7,OracleTypes.CURSOR);
            oCstmt.execute();
            rs = (java.sql.ResultSet)oCstmt.getObject(7);
            if(rs != null){
                while (rs.next()) {
                    onlinePolicyVO = new OnlineAccessPolicyVO();
                    if(rs.getString("POLICY_SEQ_ID") != null){
                        onlinePolicyVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_SEQ_ID") != null)
                    onlinePolicyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    if(rs.getString("EFFECTIVE_FROM_DATE") != null){
                        onlinePolicyVO.setFromDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)
                    if(rs.getString("EFFECTIVE_TO_DATE") != null){
                        onlinePolicyVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)
                    onlinePolicyVO.setEnrollmentType(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
                    onlinePolicyVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    onlinePolicyVO.setLoginType(TTKCommon.checkNull(rs.getString("policytype")));
                    onlinePolicyVO.setMemberAdditionPrivelegeYN(TTKCommon.checkNull(rs.getString("MEMBER_ADDTION_PRIVILEGE_YN")));
                    onlinePolicyVO.setOnlineAccessYN(TTKCommon.checkNull(rs.getString("ONLINE_ACCESS_PRESENT_YN")));
                    if(rs.getString("MODIFICATION_ALLOWED_DAYS") != null){
                    	onlinePolicyVO.setModificationAllowedDays(new Integer(rs.getInt("MODIFICATION_ALLOWED_DAYS")));
                    }//end of if(rs.getString("MODIFICATION_ALLOWED_DAYS") != null)
                    alResultList.add(onlinePolicyVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "enrollment");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getPolicyList()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getPolicyList()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getPolicyList()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPolicyList(ArrayList alSearchCriteria)

    /**
     * This method returns the RuleVO, which contains all the Rule Data details
     * @param strFlag String object which contains Flag - P / C
     * @param lngSeqID long value which contains PAT_GEN_DETAIL_SEQ_ID/CLAIM_SEq_ID to get the Rule Data Details
     * @return Document object which contains Rule Data XML
     * @exception throws TTKException
     */
    public Document validateEnrollment(String strFlag,long lngSeqID) throws TTKException {
        Document doc = null;
        XMLType xml = null;
        Connection conn = null;
        OracleCallableStatement stmt = null;
        try{
            conn = ResourceManager.getConnection();
            stmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strValidateEnrollment);
            stmt.setLong(1,lngSeqID);
            stmt.setString(2,strFlag);
            stmt.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
            stmt.execute();
            if(stmt.getOPAQUE(3)!=null)
            {
                xml = XMLType.createXML(stmt.getOPAQUE(3));
            }//end of if(stmt.getOPAQUE(3)!=null)
            DOMReader domReader = new DOMReader();
            doc = (xml != null) ? domReader.read(xml.getDOM()):null;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "prodpolicyrule");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "prodpolicyrule");
        }//end of catch (Exception exp)
        finally
		{
        	/* Nested Try Catch to ensure resource closure */ 
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (stmt != null) stmt.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in PolicyDAOImpl processRule()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl processRule()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of processRule(String strFlag,long lngSeqID)
    
    /**
     * This method checks wheteher Rule defined for Policy or not
     * @param lngPolicySeqID the policy sequence id
     * @return int the value which returns 0 or greater than 0 . If values is '0' rule is not defined and greater than 0 rule is defined
     * @exception throws TTKException
     */
    public int checkRuleDefined(long lngPolicySeqID) throws TTKException {
    	int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCheckRuleDefined);
			cStmtObject.setLong(1, lngPolicySeqID);//POLICY_SEQ_ID
			cStmtObject.registerOutParameter(2, Types.INTEGER);//OUT parameter
			cStmtObject.execute();
			iResult = cStmtObject.getInt(2);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
        			log.error("Error while closing the Statement in PolicyDAOImpl checkRuleDefined()",sqlExp);
        			throw new TTKException(sqlExp, "enrollment");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PolicyDAOImpl checkRuleDefined()",sqlExp);
        				throw new TTKException(sqlExp, "enrollment");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "enrollment");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//end of checkRuleDefined(long lngPolicySeqID)
    
    /**
	 * This method returns the PolicyDetailVO  which contains the details of Sum Insured Information
	 * @param lngPolicySeqID Long Value which contains the Policy SeqID
	 * @return  PolicyDetailVO object which contains the details of Sum Insured Information
	 * @exception throws TTKException
	 */
    public PolicyDetailVO getPolicySIInfo(Long lngPolicySeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PolicyDetailVO policyDetailVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectSIDetails);
			
			cStmtObject.setLong(1,lngPolicySeqID); //POLICY_SEQ_ID
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				policyDetailVO = new PolicyDetailVO();
				while (rs.next()) {
					policyDetailVO.setNoofFamiliesAdded(rs.getString("TOTAL_FAMILIES")!=null ? new Integer(rs.getInt("TOTAL_FAMILIES")):null);
					policyDetailVO.setActiveMembers(rs.getString("TOTAL_ACTIVE_MEMBERS")!=null ? new Integer(rs.getInt("TOTAL_ACTIVE_MEMBERS")):null);
					policyDetailVO.setCancelMembers(rs.getString("TOTAL_CANCEL_MEMBERS")!=null ? new Integer(rs.getInt("TOTAL_CANCEL_MEMBERS")):null);
					policyDetailVO.setAddedSIAmt(rs.getString("ADDED_FAMILY_SUM_INSURED")!=null ? new BigDecimal(rs.getString("ADDED_FAMILY_SUM_INSURED")):null);
					policyDetailVO.setAddedSIWording(TTKCommon.checkNull(rs.getString("FAMILY_SI_WORDING")));
				}//end of while(rs.next())
			}//end of if(rs != null)
			return policyDetailVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "enrollment");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "enrollment");
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
					log.error("Error while closing the Resultset in PolicyDAOImpl getPolicySIInfo()",sqlExp);
					throw new TTKException(sqlExp, "enrollment");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PolicyDAOImpl getPolicySIInfo()",sqlExp);
						throw new TTKException(sqlExp, "enrollment");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PolicyDAOImpl getPolicySIInfo()",sqlExp);
							throw new TTKException(sqlExp, "enrollment");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "enrollment");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPolicySIInfo(Long lngPolicySeqID)
}//end of PolicyDAOImpl
