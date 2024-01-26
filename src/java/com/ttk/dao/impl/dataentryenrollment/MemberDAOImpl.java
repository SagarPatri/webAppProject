/**
 * @ (#) MemberDAOImpl.java Feb 2, 2006
 * Project       : TTK HealthCare Services
 * File          : MemberDAOImpl.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Feb 2, 2006
 *
 * @author       :  Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.dataentryenrollment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.ttk.dao.impl.common.TestClient;
import oracle.jdbc.driver.OracleTypes;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.enrollment.DomiciliaryVO;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PEDVO;
import com.ttk.dto.enrollment.ICDCodeVO;
import com.ttk.dto.enrollment.PremiumInfoVO;
import com.ttk.dto.enrollment.SumInsuredVO;
import com.ttk.dto.usermanagement.PasswordVO;

public class MemberDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(MemberDAOImpl.class);

    TestClient testClient = null;

    //private static final String strMemberList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_MEMBER_LIST(?,?,?,?)}";
    private static final String strMemberList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_MEMBER_LIST(?,?,?,?)}";
    //private static final String strEnrollmentList ="{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_ENROLLMENT_LIST(?,?,?,?,?,?,?,?)}";
    private static final String strEnrollmentList ="{CALL POLICY_ENROLLMENT_PKG.SELECT_ENROLLMENT_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
    //private static final String strSaveMember = "{CALL POLICY_ENROLLMENT_PKG.SAVE_POLICY_MEMBER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveMember = "{CALL POLICY_ENROLLMENT_PKG.SAVE_POLICY_MEMBER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";////one extra parameter added for EmailID2-KOC-1216
    private static final String strGetRelationshipCode = "SELECT A.RELSHIP_TYPE_ID ||'#'||A.ASSOC_GENDER_REL RELSHIP,A.RELSHIP_DESCRIPTION FROM TPA_RELATIONSHIP_CODE A JOIN ( SELECT DISTINCT RELSHIP_TYPE_ID FROM TPA_INS_RELATIONSHIP_MAPPING WHERE ABBREVATION_CODE=? ) B ON (A.RELSHIP_TYPE_ID = B.RELSHIP_TYPE_ID) ORDER BY RELSHIP_DESCRIPTION";
    //Added for IBM Endorsement Age CR
    private static final String strGetMemberDetail = "select gr.date_of_joining,gr.date_of_marriage,pol.effective_from_date,gr.tpa_enrollment_number from app.tpa_enr_policy_group gr,app.tpa_enr_policy pol where pol.policy_seq_id = gr.policy_seq_id and gr.policy_group_seq_id=?";
    //Ended
    //private static final String strSelectMember = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_MEMBER(?,?,?,?)}";
    private static final String strSelectMember = "{CALL POLICY_ENROLLMENT_PKG.SELECT_MEMBER(?,?,?,?)}";
    private static final String strDeleteMember = "{CALL POLICY_ENROLLMENT_PKG.DELETE_MEMBER(?,?,?,?,?,?,?)}";
    private static final String strCancelMember = "{CALL POLICY_ENROLLMENT_PKG.CANCEL_MEMBER(?,?,?,?,?,?,?,?)}";
    /*private static final String strPEDList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_MEM_PED_LIST(?,?)}";
    private static final String strPreauthPEDList = "{CALL PRE_AUTH_SQL_PKG.SELECT_CLAIMANT_PED_LIST(?,?,?,?,?)}";
    private static final String strGetPED = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_MEM_PED(?,?)}";
    private static final String strGetPreauthPED = "{CALL PRE_AUTH_SQL_PKG.SELECT_CLAIMANT_PED(?,?,?)}";*/
    private static final String strPEDList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_MEM_PED_LIST(?,?)}";
    private static final String strPreauthPEDList = "{CALL PRE_AUTH_PKG.SELECT_CLAIMANT_PED_LIST(?,?,?,?,?)}";
    private static final String strGetPED = "{CALL POLICY_ENROLLMENT_PKG.SELECT_MEM_PED(?,?)}";
    private static final String strGetPreauthPED = "{CALL PRE_AUTH_PKG.SELECT_CLAIMANT_PED(?,?,?)}";
    private static final String strSavePED = "{CALL POLICY_ENROLLMENT_PKG.SAVE_MEM_PED(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";	//added last 3 parameter for koc 1278
    private static final String strSavePreauthPED = "{CALL PRE_AUTH_PKG.SAVE_CLAIMANT_PED(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strDeletePED = "{CALL POLICY_ENROLLMENT_PKG.DELETE_GENERAL(?,?,?,?,?,?,?)}";
    /*private static final String strGetICDList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_ICD_LIST(?,?,?,?,?,?,?,?)}";
    private static final String strSuspensionList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_MEM_SUSPEND_LIST(?,?,?,?,?,?)}";*/
    private static final String strGetICDList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_ICD_LIST(?,?,?,?,?,?,?,?)}";
    private static final String strSuspensionList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_MEM_SUSPEND_LIST(?,?,?,?,?,?)}";
    private static final String strDeleteSuspension = "{CALL POLICY_ENROLLMENT_PKG.DELETE_GENERAL(?,?,?,?,?,?,?)}";
    private static final String strSaveSuspension = "{CALL POLICY_ENROLLMENT_PKG.SAVE_MEM_SUSPEND_HIST(?,?,?,?,?,?,?,?,?,?)}";

    //private static final String strRenewMembersList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_RENEW_MEMBERS_LIST(?,?,?,?)}";
    private static final String strRenewMembersList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_RENEW_MEMBERS_LIST(?,?,?,?)}";
    private static final String strRenewMember = "{CALL POLICY_ENROLLMENT_PKG.RENEW_MEMBER(?,?,?,?,?,?,?,?)}";
    //private static final String strSaveEnrollment = "{CALL POLICY_ENROLLMENT_PKG.SAVE_POLICY_GROUP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveEnrollment = "{CALL POLICY_ENROLLMENT_PKG_SAVE_POLICY_GROUP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//one extra parameter added by Praveen for emailId2-KOC-1216
    //private static final String strGetEnrollment = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_ENROLLMENT(?,?,?,?)}";
    private static final String strGetEnrollment = "{CALL POLICY_ENROLLMENT_PKG.SELECT_ENROLLMENT(?,?,?,?)}";
    private static final String strLocation = "{CALL POLICY_ENROLLMENT_PKG.SELECT_CORPORATE_LOCATION(?,?)}";
    /*private static final String strPremiumList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_PREMIUM_LIST(?,?,?,?)}";
    private static final String strBonusList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_BONUS_LIST(?,?,?,?,?)}";
    private static final String strGetBonus = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_BONUS(?,?)}";
    private static final String strPlanList = "{CALL POLICY_ENROLLMENT_SQL_PKG.GET_PLAN_LIST(?,?)}";*/
    

    private static final String strPremiumList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_PREMIUM_LIST(?,?,?,?)}";
    private static final String strBonusList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_BONUS_LIST(?,?,?,?,?)}";
    private static final String strGetBonus = "{CALL POLICY_ENROLLMENT_PKG.SELECT_BONUS(?,?)}";
    //private static final String strPlanList = "{CALL POLICY_ENROLLMENT_PKG.GET_PLAN_LIST(?,?)}";
    private static final String strPlanList = "{CALL POLICY_ENROLLMENT_PKG.GET_PLAN_LIST(?,?,?,?,?)}";
    private static final String strSaveBonus = "{CALL POLICY_ENROLLMENT_PKG.SAVE_BONUS(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strDeletePremium = "{CALL POLICY_ENROLLMENT_PKG.DELETE_GENERAL(?,?,?,?,?,?,?)}";
    private static final String strSavePremium = "{CALL POLICY_ENROLLMENT_PKG.SAVE_PREMIUM(";
    private static final String strClearAmount = "{CALL POLICY_ENROLLMENT_PKG.CLEAR_AMOUNT(?,?,?,?,?,?,?)}";
 //   private static final String strSaveGroupPremium = "{CALL POLICY_ENROLLMENT_PKG.SAVE_GROUP_PREMIUM(?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveGroupPremium = "{CALL POLICY_ENROLLMENT_PKG.SAVE_GROUP_PREMIUM(?,?,?,?,?,?,?,?,?,?,?)}";//modified as per KOC 1284 Change request

    private static final String strApproveCardPrinting = "{CALL POLICY_ENROLLMENT_PKG.APPROVE_CARD_PRINT_ALL(?,?,?,?,?)}";
    private static final String strSaveDomiciliary = "{CALL POLICY_ENROLLMENT_PKG.SAVE_MEM_DOMICILARY(";
//    private static final String strSaveMemDomiciliary = "{CALL POLICY_ENROLLMENT_PKG.SAVE_MEM_DOMICILARY(?,?,?,?,?,?,?)}";
    //private static final String strGetDomiciliaryList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_DOMICILARY_LIST(?,?,?,?)}";
    private static final String strGetDomiciliaryList = "{CALL POLICY_ENROLLMENT_PKG.SELECT_DOMICILARY_LIST(?,?,?,?)}";
    private static final String strGetDescriptionList = "SELECT ICD_CODE,PED_DESCRIPTION FROM TPA_PED_CODE WHERE ped_code_id=?";
    private static final String strClearEnrollmentRules = "{CALL POLICY_ENROLLMENT_PKG.CLEAR_ENROLLMENT_RULES(?,?,?,?)}";
    private static final String strSelectEmpPasswordInfo = "{CALL POLICY_ENROLLMENT_PKG.SELECT_PASSWORD(?,?)}";
    private static final String strResetEmpPassword = "{CALL POLICY_ENROLLMENT_PKG.RESET_PASSWORD(?,?,?)}";
    //added for koc 1278 & KOC 1270 for hospital cash benefit
    private static final String strSaveMemberPlan = "{CALL POLICY_ENROLLMENT_PKG.SAVE_CASH_BENEFIT_EMPLOYEE(?,?,?,?,?,?,?)}";
    private static final String strSelectCashBenefit = "{CALL POLICY_ENROLLMENT_PKG.SELECT_CASH_BENEFIT(?,?)}";
    //added for koc 1278 & KOC 1270 for hospital cash benefit
    private static final int MEMBER_SEQ_ID = 1 ;
    private static final int POLICY_GROUP_SEQ_ID = 2 ;
    private static final int ENR_ADDRESS_SEQ_ID = 3 ;
    private static final int MEM_NAME = 4 ;
    private static final int GENDER_GENERAL_TYPE_ID = 5 ;
    private static final int RELSHIP_TYPE_ID = 6 ;
    private static final int MEM_DOB = 7 ;
    private static final int MEM_AGE = 8 ;
    private static final int OCCUPATION_GENERAL_TYPE_ID = 9 ;
    private static final int DATE_OF_INCEPTION = 10 ;
    private static final int DATE_OF_EXIT = 11 ;
    private static final int PHOTO_PRESENT_YN = 12 ;
    private static final int POLICY_EXPIRY_DATE = 13 ;
    private static final int CARD_PRN_COUNT = 14 ;
    private static final int PRINTCARD_YN = 15 ;
    //private static final int CERTIFICATE_NO = 16 ;
    private static final int INS_CUSTOMER_CODE = 16 ;
    private static final int CLARIFY_GENERAL_TYPE_ID = 17 ;
    private static final int CLARIFIED_DATE = 18 ;
    private static final int REMARKS = 19 ;
    private static final int STATUS_GENERAL_TYPE_ID=20;
    private static final int ADDED_BY = 21 ;
    private static final int ADDRESS_1 = 22 ;
    private static final int ADDRESS_2 = 23 ;
    private static final int ADDRESS_3 = 24 ;
    private static final int STATE_TYPE_ID = 25 ;
    private static final int CITY_TYPE_ID = 26 ;
    private static final int PIN_CODE = 27 ;
    private static final int COUNTRY_ID = 28 ;
    private static final int EMAIL_ID = 29 ;
    private static final int RES_PHONE_NO = 30 ;
    private static final int OFF_PHONE_NO_1 = 31 ;
    private static final int OFF_PHONE_NO_2 = 32 ;
    private static final int MOBILE_NO = 33 ;
    private static final int FAX_NO = 34 ;
    private static final int TPA_ENROLLMENT_NUMBER = 35;
    private static final int POLICY_MODE = 36 ;
    private static final int POLICY_TYPE = 37 ;
    private static final int SEQ_ID = 38 ; //To store Policy Seq ID in Enrollment flow and Endorsement Seq ID in Endorsement flow
    private static final int MEMBER_REMARKS = 39;
    private static final int STOP_PAT_CLM_PROCESS_YN = 40;
    private static final int RECEIVED_AFTER = 41;
    private static final int PAN_NUMBER = 42;
    private static final int DIABETES_COVER_YN =43;
    private static final int HYPERTENSION_COVER_YN =44;
    private static final int SERIAL_NUMBER =45;
    private static final int INSURED_CUST_CODE_BAGI=46;
    private static final int ROW_PROCESSED = 47;
    private static final int EMAIL_ID2 = 48;//added for EmailID2 - KOC-1216

    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alMember ArrayList which contains seq id for Enrollment or Endorsement flow to get the Policy Details
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getDependentList(ArrayList alMember) throws TTKException
    {
        Collection<Object> alBatchPolicyList = new ArrayList<Object>();
        MemberVO memberVO = new MemberVO();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strMemberList);
            cStmtObject.setLong(1,(Long)alMember.get(0));
            cStmtObject.setString(2,(String)alMember.get(1));
            cStmtObject.setString(3,(String)alMember.get(2));
            cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if (rs!=null)
            {
                while (rs.next())
                {
                    memberVO = new MemberVO();
                    memberVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    memberVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
                    memberVO.setName(TTKCommon.checkNull(rs.getString("MEMBER")));
                    memberVO.setMemberSeqID(rs.getString("MEMBER_SEQ_ID")!=null ? new Long(rs.getLong("MEMBER_SEQ_ID")):null);
                    memberVO.setDateOfBirth(rs.getString("MEM_DOB")!=null ? new Date(rs.getTimestamp("MEM_DOB").getTime()):null);
                    memberVO.setAge(rs.getString("MEM_AGE")!=null ? new Integer(rs.getInt("MEM_AGE")):null);
                    memberVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER")));
                    memberVO.setPolicyGroupSeqID((Long)alMember.get(0));
                    memberVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getLong("POLICY_SEQ_ID")):null);
                    memberVO.setCancelYN(TTKCommon.checkNull(rs.getString("CANCEL_YN")));
                    alBatchPolicyList.add(memberVO);
                }// End of while (rs.next())
            }// End of if (rs!=null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getDependentList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getDependentList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getDependentList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return (ArrayList) alBatchPolicyList;
    }//end of getDependentList(ArrayList alMember)

    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getMemberList(ArrayList alSearchCriteria) throws TTKException
    {
        Collection<Object> alBatchPolicyList = new ArrayList<Object>();
        MemberVO memberVO = new MemberVO();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strEnrollmentList);
            if(alSearchCriteria.get(0)!=null){
                cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            }//end of if(alSearchCriteria.get(0)!=null)
            else{
                cStmtObject.setString(1,null);
            }//end of else
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));
            cStmtObject.setString(8,(String)alSearchCriteria.get(7));
            cStmtObject.setString(9,(String)alSearchCriteria.get(8));

            if(alSearchCriteria.get(9)!=null){
                cStmtObject.setInt(10,new Integer((String)alSearchCriteria.get(9)));
            }//end of if(alSearchCriteria.get(9)!=null)
            else{
                cStmtObject.setString(10,null);
            }//end of else

            if(alSearchCriteria.get(10)!=null){
                cStmtObject.setInt(11,new Integer((String)alSearchCriteria.get(10)));
            }//end of if(alSearchCriteria.get(10)!=null)
            else{
                cStmtObject.setString(11,null);
            }//end of else
            cStmtObject.registerOutParameter(12,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(12);
            if (rs!=null)
            {
                while (rs.next())
                {
                    memberVO = new MemberVO();
                    memberVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID")!=null ? new Long(rs.getInt("POLICY_GROUP_SEQ_ID")):null);
                    memberVO.setName(TTKCommon.checkNull(rs.getString("ENROLLMENT")));
                    memberVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    memberVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getInt("POLICY_SEQ_ID")):null);
                    memberVO.setTPAStatusTypeID(TTKCommon.checkNull(rs.getString("TPA_STATUS_GENERAL_TYPE_ID")));

                    if(rs.getString("EFFECTIVE_TO_DATE") != null){
                    	memberVO.setPolicyEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)
                    if(rs.getString("EFFECTIVE_FROM_DATE") != null){
                        memberVO.setPolicyStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

                    if(rs.getString("EFFECTIVE_DATE") != null){
                    	memberVO.setEffectiveDate(new Date(rs.getTimestamp("EFFECTIVE_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_DATE") != null)

                    memberVO.setAbbrCode(TTKCommon.checkNull(rs.getString("ABBREVATION_CODE")));
                    memberVO.setDMSRefID(TTKCommon.checkNull(rs.getString("DMS_REFERENCE_ID")));
                    memberVO.setValidationStatus(TTKCommon.checkNull(rs.getString("VALIDATION_STATUS")));
                    memberVO.setEmpStatusTypeID(TTKCommon.checkNull(rs.getString("STATUS_GENERAL_TYPE_ID")));
                    alBatchPolicyList.add(memberVO);
                }// End of while (rs.next())
            }// End of if (rs!=null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getMemberList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getMemberList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getMemberList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return (ArrayList) alBatchPolicyList;
    }//end of getMemberList(ArrayList alSearchCriteria)

    /**
     * This method saves the Member information
     * @param memberDetailVO the object which contains the Member Details which has to be  saved
     * @param strPolicyMode the String object which contains the Policy Mode
     * @param strPolicType the String object which contains the Policy Type
     * @return int the value which returns zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public Long saveMember(MemberDetailVO memberDetailVO,String strPolicyMode,String strPolicyType) throws TTKException
    {
        Long lngResult = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            MemberAddressVO memberAddressVO =null;
            memberAddressVO = memberDetailVO.getMemberAddressVO();
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMember);
            cStmtObject.setLong(MEMBER_SEQ_ID,memberDetailVO.getMemberSeqID()!=null ? memberDetailVO.getMemberSeqID():0);
            if(memberDetailVO.getPolicyGroupSeqID()!=null){
                cStmtObject.setLong(POLICY_GROUP_SEQ_ID,memberDetailVO.getPolicyGroupSeqID());
            }//end of if(memberDetailVO.getPolicyGroupSeqID()!=null)
            else{
                cStmtObject.setString(POLICY_GROUP_SEQ_ID,null);
            }//end of else
            if(memberAddressVO.getAddrSeqId()!=null){
                cStmtObject.setLong(ENR_ADDRESS_SEQ_ID,memberAddressVO.getAddrSeqId());
            }//end of if(memberAddressVO.getAddrSeqId()!=null)
            else{
                cStmtObject.setString(ENR_ADDRESS_SEQ_ID,null);
            }//end of else
            cStmtObject.setString(MEM_NAME,memberDetailVO.getName());
            cStmtObject.setString(GENDER_GENERAL_TYPE_ID,memberDetailVO.getGenderTypeID());
            cStmtObject.setString(RELSHIP_TYPE_ID,memberDetailVO.getRelationTypeID());
            cStmtObject.setTimestamp(MEM_DOB,memberDetailVO.getDateOfBirth()!=null ? new Timestamp(memberDetailVO.getDateOfBirth().getTime()):null);
            if(memberDetailVO.getAge()!= null){
                cStmtObject.setInt(MEM_AGE,memberDetailVO.getAge());
            }//end of if(memberDetailVO.getAge()!= null)
            else{
                cStmtObject.setString(MEM_AGE,null);
            }//end of else
            cStmtObject.setString(OCCUPATION_GENERAL_TYPE_ID,memberDetailVO.getOccupationTypeID());
            cStmtObject.setTimestamp(DATE_OF_INCEPTION,memberDetailVO.getInceptionDate()!=null ? new Timestamp(memberDetailVO.getInceptionDate().getTime()):null);
            cStmtObject.setTimestamp(DATE_OF_EXIT,memberDetailVO.getExitDate()!=null ? new Timestamp(memberDetailVO.getExitDate().getTime()):null);
            cStmtObject.setString(PHOTO_PRESENT_YN,memberDetailVO.getPhotoPresentYN());
            cStmtObject.setTimestamp(POLICY_EXPIRY_DATE,memberDetailVO.getPolicyExpireDate()!=null ? new Timestamp(memberDetailVO.getPolicyExpireDate().getTime()):null);
            if(memberDetailVO.getCardPrintCnt()!= null ){
                cStmtObject.setInt(CARD_PRN_COUNT,memberDetailVO.getCardPrintCnt());
            }//end of if(memberDetailVO.getCardPrintCnt()!= null )
            else{
                cStmtObject.setString(CARD_PRN_COUNT,null);
            }//end of else
            cStmtObject.setString(PRINTCARD_YN,memberDetailVO.getCardPrintYN());
            cStmtObject.setString(INS_CUSTOMER_CODE,memberDetailVO.getCustomerCode());
            cStmtObject.setString(CLARIFY_GENERAL_TYPE_ID,memberDetailVO.getClarificationTypeID());
            cStmtObject.setTimestamp(CLARIFIED_DATE,memberDetailVO.getClarifiedDate()!=null ? new Timestamp(memberDetailVO.getClarifiedDate().getTime()):null);
            cStmtObject.setString(REMARKS,memberDetailVO.getRemarks());
            cStmtObject.setString(STATUS_GENERAL_TYPE_ID,memberDetailVO.getStatus());
            cStmtObject.setLong(ADDED_BY,memberDetailVO.getAddedBy()!=null ? memberDetailVO.getAddedBy():1);
            cStmtObject.setString(ADDRESS_1,memberAddressVO.getAddress1());
            cStmtObject.setString(ADDRESS_2,memberAddressVO.getAddress2());
            cStmtObject.setString(ADDRESS_3,memberAddressVO.getAddress3());
            cStmtObject.setString(STATE_TYPE_ID,memberAddressVO.getStateCode());
            cStmtObject.setString(CITY_TYPE_ID,memberAddressVO.getCityCode());

            if(memberAddressVO.getPinCode()!=null && !memberAddressVO.getPinCode().equals("")){
                cStmtObject.setInt(PIN_CODE,new Integer(memberAddressVO.getPinCode()));
            }//end of if(memberAddressVO.getPinCode()!=null && !memberAddressVO.getPinCode().equals(""))
            else{
                cStmtObject.setString(PIN_CODE,null);
            }//end of else
            cStmtObject.setString(COUNTRY_ID,memberAddressVO.getCountryCode());
            cStmtObject.setString(EMAIL_ID,memberAddressVO.getEmailID());
            cStmtObject.setString(RES_PHONE_NO,memberAddressVO.getHomePhoneNbr());
            cStmtObject.setString(OFF_PHONE_NO_1,memberAddressVO.getPhoneNbr1());
            cStmtObject.setString(OFF_PHONE_NO_2,memberAddressVO.getPhoneNbr2());
            cStmtObject.setString(MOBILE_NO,memberAddressVO.getMobileNbr());
            cStmtObject.setString(FAX_NO,memberAddressVO.getFaxNbr());
            cStmtObject.setString(TPA_ENROLLMENT_NUMBER,memberDetailVO.getEnrollmentNbr());
            cStmtObject.setString(POLICY_MODE,strPolicyMode);
            cStmtObject.setString(POLICY_TYPE,strPolicyType);

            if(memberDetailVO.getSeqID()!=null){
                cStmtObject.setLong(SEQ_ID,memberDetailVO.getSeqID());
            }//end of if(memberDetailVO.getSeqID()!=null)
            else{
                cStmtObject.setString(SEQ_ID,null);
            }//end of else
            cStmtObject.setString(MEMBER_REMARKS,memberDetailVO.getMemberRemarks());
            cStmtObject.setString(STOP_PAT_CLM_PROCESS_YN,memberDetailVO.getStopPatClmYN());
            cStmtObject.setTimestamp(RECEIVED_AFTER,memberDetailVO.getReceivedAfter()!=null ? new Timestamp(memberDetailVO.getReceivedAfter().getTime()):null);
            cStmtObject.setString(PAN_NUMBER,memberDetailVO.getPanNbr());
            cStmtObject.setString(DIABETES_COVER_YN, memberDetailVO.getDiabetesCoverYN());
            cStmtObject.setString(HYPERTENSION_COVER_YN, memberDetailVO.getHyperTensCoverYN());
            cStmtObject.setString(SERIAL_NUMBER, memberDetailVO.getSerialNumber());

           //START CR CHANGE
            cStmtObject.setString(INSURED_CUST_CODE_BAGI, memberDetailVO.getInsuredCode());
         // END CR CHANGE
			//added by Praveen
			cStmtObject.setString(EMAIL_ID2,memberAddressVO.getEmailID2());
			//Ended
            cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.registerOutParameter(MEMBER_SEQ_ID,Types.BIGINT);//ROW_PROCESSED
            cStmtObject.execute();
            lngResult = cStmtObject.getLong(MEMBER_SEQ_ID);

        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "members");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "members");
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
        			log.error("Error while closing the Statement in MemberDAOImpl saveMember()",sqlExp);
        			throw new TTKException(sqlExp, "members");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl saveMember()",sqlExp);
        				throw new TTKException(sqlExp, "members");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "members");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngResult;
   }//end of saveMember(MemberDetailVO memberDetailVO,String strPolicyMode,String strPolicType)
  
    /**
	 * This method returns the ArrayList which contains Relationship Code
	 * @param strAbbrCode String object which contains Insurance Company Abbrevation Code to fetch the Relationship Code
	 * @return ArrayList which contains Relationship Code
	 * @exception throws TTKException
	 */
	public ArrayList getRelationshipCode(String strAbbrCode) throws TTKException {
		Connection conn1 = null;
        ResultSet rs = null;
        PreparedStatement pStmt = null;
        CacheObject cacheObject = null;
        ArrayList<Object> alRelationshipCode = new ArrayList<Object>();
        try{
            conn1 = ResourceManager.getConnection();
            pStmt=conn1.prepareStatement(strGetRelationshipCode);
            pStmt.setString(1,strAbbrCode);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("RELSHIP")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("RELSHIP_DESCRIPTION")));
                    alRelationshipCode.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alRelationshipCode;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getRelationshipCode()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getRelationshipCode()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn1 != null) conn1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getRelationshipCode()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				pStmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
	}//end of getRelationshipCode(String strAbbrCode)

	//IBM Endorsement Age CR

		/**
		 * This method returns the ArrayList which contains MemberDetails
		 * @param PolicyGroupSeqID long object which contains groupId of Policies to fetch the MemberDetail
		 * @return ArrayList which contains MemberDetail
		 * @exception throws TTKException
		 */
	public MemberDetailVO getMemberDetail(long PolicyGroupSeqID) throws TTKException {
		Connection conn1 = null;
	    ResultSet rs = null;
	    PreparedStatement pStmt = null;
	    CacheObject cacheObject = null;
	    MemberDetailVO memberDetailVO = null;
	        try{
	            conn1 = ResourceManager.getConnection();
	            pStmt=conn1.prepareStatement(strGetMemberDetail);
	            pStmt.setLong(1,PolicyGroupSeqID);
	            rs = pStmt.executeQuery();
	            if(rs != null){
	                while(rs.next()){

	                	memberDetailVO = new MemberDetailVO();
	                	if(rs.getString("EFFECTIVE_FROM_DATE")!=null)
	            		{ memberDetailVO.setPolicyStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
	            		}
	                	if(rs.getString("DATE_OF_MARRIAGE")!=null)
	            		{ memberDetailVO.setDateOfMarriage(new Date(rs.getTimestamp("DATE_OF_MARRIAGE").getTime()));
	            		}
	            		if(rs.getString("DATE_OF_JOINING")!=null)
	            		{ memberDetailVO.setDateOfJoining(new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
	            		}
						memberDetailVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
	                 }//end of while(rs.next())
	            }//end of if(rs != null)
	            return memberDetailVO;
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "member");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "member");
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
						log.error("Error while closing the Resultset in MemberDAOImpl getMemberDetail()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt != null) pStmt.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in MemberDAOImpl getMemberDetail()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn1 != null) conn1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in MemberDAOImpl getMemberDetail()",sqlExp);
								throw new TTKException(sqlExp, "member");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "member");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects
				{
					rs = null;
					pStmt = null;
					conn1 = null;
				}//end of finally
			}//end of finally
	}//end of getMemberDetail(long PolicyGroupSeqID)

    /**
     * This method deletes the Member/Enrollment information from the database
     * @param alMember ArrayList object which contains seq id for Enrollment or Endorsement flow to delete the Member information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteMember(ArrayList alMember) throws TTKException
    {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try
        {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteMember);
            cStmtObject.setString(1, (String)alMember.get(0));
            cStmtObject.setString(2, (String)alMember.get(1));
            cStmtObject.setString(3, (String)alMember.get(2));
            cStmtObject.setString(4, (String)alMember.get(3));
            if(alMember.get(4)!=null){
                cStmtObject.setLong(5,(Long)alMember.get(4));
            }//end of if(alMember.get(4)!=null)
            else{
                cStmtObject.setString(5,null);
            }//end of else
            if(alMember.get(5)!= null ){
                cStmtObject.setLong(6,(Long)alMember.get(5));
            }//end of if(alMember.get(5)!= null )
            else{
                cStmtObject.setString(6,null);
            }//end of else
            cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl deleteMember()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl deleteMember()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
   }//end of deleteMember(ArrayList alMember)

    /**
     * This method cancel the Member/Enrollment information from the database
     * @param alMember ArrayList object which contains seq id for Enrollment or Endorsement flow to delete the Member information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int cancelMember(ArrayList alMember) throws TTKException
    {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try
        {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCancelMember);
            cStmtObject.setString(1, (String)alMember.get(0));
            cStmtObject.setString(2, (String)alMember.get(1));
            cStmtObject.setString(3, (String)alMember.get(2));
            cStmtObject.setString(4, (String)alMember.get(3));
            if(alMember.get(4)!=null){
                cStmtObject.setLong(5,(Long)alMember.get(4));
            }//end of if(alMember.get(4)!=null)
            else{
                cStmtObject.setString(5,null);
            }//end of else
            if(alMember.get(5)!=null){
                cStmtObject.setLong(6,(Long)alMember.get(5));
            }//end of if(alMember.get(5)!=null)
            else{
                cStmtObject.setString(6,null);
            }//end of else
            if(alMember.get(6)!= null ){
                cStmtObject.setLong(7,(Long)alMember.get(6));
            }//end of if(alMember.get(6)!= null )
            else{
                cStmtObject.setString(7,null);
            }//end of else
            cStmtObject.registerOutParameter(8, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(8);//ROWS_PROCESSED
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl cancelMember()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl cancelMember()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
   }//end of cancelMember(ArrayList alMember)

    /**
     * This method returns the MemberDetailVO which contains the Member information
     * @param alMember ArrayList which contains seq id for Enrollment or Endorsement flow to get the Member information
     * @return MemberDetailVO the contains the Member information
     * @exception throws TTKException
     */
    public MemberDetailVO getMember(ArrayList alMember) throws TTKException
    {
        MemberDetailVO memberDetailVO = null;
        MemberAddressVO memberAddressVO =null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectMember);
            cStmtObject.setLong(1,(Long)alMember.get(0));
            cStmtObject.setString(2,(String)alMember.get(1));
            cStmtObject.setString(3,(String)alMember.get(2));
            cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);//ROW_PROCESSED
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if (rs!=null)
            {
                while(rs.next())
                 {
                    memberDetailVO = new MemberDetailVO();
                    memberAddressVO = new MemberAddressVO();
                    if(rs.getString("MEMBER_SEQ_ID")!=null){
                        memberDetailVO.setMemberSeqID(new Long(rs.getInt("MEMBER_SEQ_ID")));
                    }//end of if(rs.getString("MEMBER_SEQ_ID")!=null)
                    if(rs.getString("POLICY_GROUP_SEQ_ID")!=null){
                        memberDetailVO.setPolicyGroupSeqID(new Long(rs.getInt("POLICY_GROUP_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_GROUP_SEQ_ID")!=null)
                    memberDetailVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
                    memberDetailVO.setRelationDesc(TTKCommon.checkNull(rs.getString("RELSHIP_DESCRIPTION")));
                    memberDetailVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                    memberDetailVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
                    memberDetailVO.setGenderDescription(TTKCommon.checkNull(rs.getString("GENDER")));
                    if(rs.getString("MEM_DOB")!=null){
                        memberDetailVO.setDateOfBirth(new Date(rs.getTimestamp("MEM_DOB").getTime()));
                    }//end of if(rs.getString("MEM_DOB")!=null)
                    if(rs.getString("MEM_AGE")!=null){
                        memberDetailVO.setAge(new Integer(rs.getInt("MEM_AGE")));
                    }//end of if(rs.getString("MEM_AGE")!=null)
                    memberDetailVO.setOccupation(TTKCommon.checkNull(rs.getString("OCCUPATION")));
                    memberDetailVO.setOccupationTypeID(TTKCommon.checkNull(rs.getString("OCCUPATION_GENERAL_TYPE_ID")));
                    
                    memberDetailVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
                    memberDetailVO.setCustomerID(TTKCommon.checkNull(rs.getString("TPA_CUSTOMER_ID")));
                    memberDetailVO.setStatus(TTKCommon.checkNull(rs.getString("STATUS_GENERAL_TYPE_ID")));
                    memberDetailVO.setCustomerCode(TTKCommon.checkNull(rs.getString("INS_CUSTOMER_CODE")));
                    memberDetailVO.setCategoryTypeID(TTKCommon.checkNull(rs.getString("CATEGORY")));
                    memberDetailVO.setInceptionDate(rs.getString("DATE_OF_INCEPTION")!=null ? new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()):null);
                    if(rs.getString("DATE_OF_EXIT")!=null){
                        memberDetailVO.setExitDate(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
                    }//end of if(rs.getString("DATE_OF_EXIT")!=null)
                    memberDetailVO.setMemberTypeID(TTKCommon.checkNull(rs.getString("MEM_GENERAL_TYPE_ID")));
                    memberDetailVO.setPhotoPresentYN(TTKCommon.checkNull(rs.getString("PHOTO_PRESENT_YN")));
                    memberDetailVO.setCardPrintYN(TTKCommon.checkNull(rs.getString("PRINTCARD_YN")));
                    memberDetailVO.setCardPrintCnt(rs.getString("CARD_PRN_COUNT")!=null ? new Integer(rs.getInt("CARD_PRN_COUNT")):null);
                    memberDetailVO.setRenewYN(TTKCommon.checkNull(rs.getString("RENEW_YN")));
                    memberDetailVO.setRenewCount(TTKCommon.checkNull(rs.getString("RENEW_COUNT"))!=null ? new Integer(rs.getInt("RENEW_COUNT")):null);
                    if(rs.getString("CARD_PRN_DATE")!=null){
                        memberDetailVO.setCardPrintDate(new Date(rs.getTimestamp("CARD_PRN_DATE").getTime()));
                    }//end of if(rs.getString("CARD_PRN_DATE")!=null)

                    if(rs.getString("DOC_DISPATCH_DATE")!=null){
                        memberDetailVO.setDocDispatchDate(new Date(rs.getTimestamp("DOC_DISPATCH_DATE").getTime()));
                    }//end of if(rs.getString("DOC_DISPATCH_DATE")!=null)

                    if(rs.getString("ENR_ADDRESS_SEQ_ID")!=null){
                        memberAddressVO.setAddrSeqId(new Long(rs.getString("ENR_ADDRESS_SEQ_ID")));
                    }//end of if(rs.getString("ENR_ADDRESS_SEQ_ID")!=null)
                    memberAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
                    memberAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
                    memberAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
                    memberAddressVO.setCityCode(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));
                    memberAddressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
                    memberAddressVO.setStateName(TTKCommon.checkNull(rs.getString("STATE_NAME")));
                    memberAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
                    memberAddressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
                    memberAddressVO.setCountryName(TTKCommon.checkNull(rs.getString("COUNTRY_NAME")));
                    memberAddressVO.setHomePhoneNbr(TTKCommon.checkNull(rs.getString("RES_PHONE_NO")));
                    memberAddressVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
                    memberAddressVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
                    memberAddressVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
                    memberAddressVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
                    memberAddressVO.setFaxNbr(TTKCommon.checkNull(rs.getString("FAX_NO")));
                    memberDetailVO.setClarificationTypeID(TTKCommon.checkNull(rs.getString("CLARIFY_GENERAL_TYPE_ID")));
                    memberDetailVO.setAssocGenderRel(TTKCommon.checkNull(rs.getString("ASSOC_GENDER_REL")));
                    memberDetailVO.setGenderYN(TTKCommon.checkNull(rs.getString("ASSOC_GENDER_REL")));
                    memberDetailVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));

                    if(rs.getString("CLARIFIED_DATE")!=null){
                        memberDetailVO.setClarifiedDate(new Date(rs.getTimestamp("CLARIFIED_DATE").getTime()));
                    }//end of if(rs.getString("CLARIFIED_DATE")!=null)
                    memberDetailVO.setRemarks(TTKCommon.checkNull(rs.getString("remarks")));
                    if(rs.getString("COVERED_FROM_DATE")!=null){
                        memberDetailVO.setStartDate(new Date(rs.getTimestamp("COVERED_FROM_DATE").getTime()));
                    }//end of if(rs.getString("COVERED_FROM_DATE")!=null)
                    if(rs.getString("COVERED_TO_DATE")!=null){
                        memberDetailVO.setEndDate(new Date(rs.getTimestamp("COVERED_TO_DATE").getTime()));
                    }//end of if(rs.getString("COVERED_TO_DATE")!=null)
                    memberDetailVO.setMemberRemarks(TTKCommon.checkNull(rs.getString("MEMBER_REMARKS")));
                    memberDetailVO.setCardBatchNbr(TTKCommon.checkNull(rs.getString("BATCH_NO")));
                    memberDetailVO.setCourierNbr(TTKCommon.checkNull(rs.getString("COURIER_NO")));
                    memberDetailVO.setMemberAddressVO(memberAddressVO);
                    memberDetailVO.setStopPatClmYN(TTKCommon.checkNull(rs.getString("STOP_PAT_CLM_PROCESS_YN")));
            		if(rs.getString("RECIEVED_AFTER") != null){
            			memberDetailVO.setReceivedAfter(new Date(rs.getTimestamp("RECIEVED_AFTER").getTime()));
            		}//end of if(rs.getString("RECIEVED_AFTER") != null)

            		memberDetailVO.setCustEndorseNbr(TTKCommon.checkNull(rs.getString("CUST_ENDORSEMENT_NUMBER")));
            		memberDetailVO.setPanNbr(TTKCommon.checkNull(rs.getString("PAN_NUMBER")));
            		memberDetailVO.setCardDesc(TTKCommon.checkNull(rs.getString("CARD_DESCRIPTION")));
            		memberDetailVO.setInsuredCode(TTKCommon.checkNull(rs.getString("INS_INSURED_CODE")));
            		memberDetailVO.setDiabetesCoverYN(TTKCommon.checkNull(rs.getString("DIABETES_COVER_YN")));
            		memberDetailVO.setHyperTensCoverYN(TTKCommon.checkNull(rs.getString("HYPERTENSION_COVER_YN")));
            		memberDetailVO.setSerialNumber(TTKCommon.checkNull(rs.getString("SERIAL_NUMBER")));
            		memberDetailVO.getMemberAddressVO().setEmailID2(TTKCommon.checkNull(rs.getString("EMAIL_ID1")));//added by Praveen for EmailId2 - KOC-1216
            		//Added for IBM AGE CR
					if(rs.getString("DATE_OF_MARRIAGE")!=null)
					{ memberDetailVO.setDateOfMarriage(new Date(rs.getTimestamp("DATE_OF_MARRIAGE").getTime()));
					}
					if(rs.getString("DATE_OF_JOINING")!=null)
					{ memberDetailVO.setDateOfJoining(new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
					}
					if(rs.getString("EFFECTIVE_FROM_DATE")!=null)
					{ memberDetailVO.setPolicyStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
					}
					//Ended IBM AGE CR

            	 }//end of while(rs.next())
            }// End of if (rs!=null)
          }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getMember()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getMember()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getMember()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return memberDetailVO;
    }//end of getMember(ArrayList alMember)

    /**
     * This method returns the ArrayList, which contains the PEDVO's which are populated from the database
     * @param lngMemberSeqID the member sequence id for which the PED details has to be fetched
     * @return ArrayList of PEDVO'S object's which contains PED details
     * @exception throws TTKException
     */
    public ArrayList getPEDList(Long lngMemberSeqID) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        PEDVO pedVO = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPEDList);
            cStmtObject.setLong(1, lngMemberSeqID);//MEMBER_SEQ_ID
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
                while (rs.next()) {
                    pedVO = new PEDVO();
                    pedVO.setPEDSeqID(rs.getString("MEM_PED_SEQ_ID")!= null ? new Long(rs.getLong("MEM_PED_SEQ_ID")):null);
                    pedVO.setMemberSeqID(rs.getString("MEMBER_SEQ_ID")!= null ? new Long(rs.getLong("MEMBER_SEQ_ID")):null);
                    pedVO.setPEDCodeID(rs.getString("PED_CODE_ID")!= null ? new Long(rs.getLong("PED_CODE_ID")):null);
                    pedVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                    pedVO.setDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    pedVO.setDuration(TTKCommon.checkNull(rs.getString("MEM_DURATION")));
                    pedVO.setICDCode(TTKCommon.checkNull(rs.getString("ICD_CODE")));
                    pedVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
                    pedVO.setDurationYrMonth(TTKCommon.checkNull(rs.getString("DURATION")));
                    //added for koc 1278
                    pedVO.setWaitingPeriod(TTKCommon.checkNull(rs.getString("PED_WAITING_PERIOD")));
                    pedVO.setPersonalWaitingPeriod(TTKCommon.checkNull(rs.getString("ENR_DURATION_GENERAL_TYPE_ID")));
                    pedVO.setAilmentTypeID(TTKCommon.checkNull(rs.getString("AILMENT_GENERAL_TYPE_ID")));
                    pedVO.setPersWtPeriod(TTKCommon.checkNull(rs.getString("PERS_WT_PERIOD")));
                    //added for koc 1278
                    alResultList.add(pedVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getPEDList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getPEDList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getPEDList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPEDList(Long lngMemberSeqID)

    /**
	 * This method returns the ArrayList, which contains the PEDVO's which are populated from the database
	 * @param lngMemberSeqID the member sequence id for which the PED details has to be fetched
	 * @param lngSeqID long value which contains Seq ID for getting the PED details -
	 * In Pre-Authorization flow PAT_GEN_DETAIL_SEQ_ID and in Claims flow CLAIM_SEQ_ID
	 * @param lngUserSeqID long value which contains logged-in user seq id
	 * @param strMode contains Mode for Identifying Pre-authorization / Claims flow - PAT/CLM
	 * @return ArrayList of PEDVO'S object's which contains PED details
	 * @exception throws TTKException
	 */
	public ArrayList getPreauthPEDList(Long lngMemberSeqID,long lngSeqID,long lngUserSeqID,String strMode) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
        PEDVO pedVO = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreauthPEDList);
            cStmtObject.setLong(1,lngSeqID);//PAT_GEN_DETAIL_SEQ_ID/CLAIM_SEQ_ID
            cStmtObject.setLong(2, lngMemberSeqID);//MEMBER_SEQ_ID
            cStmtObject.setLong(3,lngUserSeqID);
            cStmtObject.setString(4,strMode);
            cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(5);
            if(rs != null){
            	while(rs.next()){
            		pedVO = new PEDVO();

            		if(rs.getString("PED_SEQ_ID") != null){
            			pedVO.setSeqID(new Long(rs.getLong("PED_SEQ_ID")));
            		}//end of if(rs.getString("PED_SEQ_ID") != null)

                    pedVO.setPEDCodeID(rs.getString("PED_CODE_ID")!= null ? new Long(rs.getLong("PED_CODE_ID")):null);
                    pedVO.setDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    pedVO.setDuration(TTKCommon.checkNull(rs.getString("MEM_DURATION")));
                    pedVO.setICDCode(TTKCommon.checkNull(rs.getString("ICD_CODE")));
                    pedVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
                    pedVO.setPEDType(TTKCommon.checkNull(rs.getString("PED_TYPE")));
                    pedVO.setEditYN(TTKCommon.checkNull(rs.getString("EDIT_YN")));
                    pedVO.setDurationYrMonth(TTKCommon.checkNull(rs.getString("DURATION")));
                    //added for koc 1278
                    pedVO.setAilmentTypeID(TTKCommon.checkNull(rs.getString("AILMENT_GENERAL_TYPE_ID")));
                    pedVO.setPersWtPeriod(TTKCommon.checkNull(rs.getString("PERS_WT_PERIOD")));
                    //added for koc 1278
                    alResultList.add(pedVO);
            	}//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getPreauthPEDList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getPreauthPEDList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getPreauthPEDList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPreauthPEDList(Long lngMemberSeqID,long lngSeqID,long lngUserSeqID,String strMode)

	/**
     * This method returns the PEDVO which contains the PED information
     * @param lngSeqID long value which contains seq id to get the PED information
     * @param strIdentifier Object which contains Identifier for identifying the flow - Pre-Authorization/Enrollment
     * @param strShowSave which contains Insurance/TTK in Preauthorization and in Enrollment - " "
     * @return PEDVO the contains the PED information
     * @exception throws TTKException
     */
    public PEDVO getPED(long lngSeqID,String strIdentifier,String strShowSave) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        PEDVO pedVO = null;
        try{
            conn = ResourceManager.getConnection();
            if(strIdentifier.equalsIgnoreCase("Pre-Authorization")){
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetPreauthPED);
            	if(lngSeqID != 0)
            	{
            		cStmtObject.setLong(1,lngSeqID);
            	}else
            	{
            		cStmtObject.setString(1,"");
            	}//end of else

            	cStmtObject.setString(2,strShowSave);
                cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            }//end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization"))
            if(strIdentifier.equalsIgnoreCase("Coding")){
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetPreauthPED);
            	cStmtObject.setLong(1,lngSeqID);
            	cStmtObject.setString(2,strShowSave);
                cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            }//end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization"))

            if(strIdentifier.equalsIgnoreCase("Enrollment")){
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetPED);
            	if(lngSeqID != 0)
            	{
            		cStmtObject.setLong(1,lngSeqID);
            	}else
            	{
            		cStmtObject.setString(1,"");
            	}//end of else
            	cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            }//end of if(strIdentifier.equalsIgnoreCase("Enrollment"))

            if (rs!=null)
            {
                while(rs.next())
                 {
                    pedVO = new PEDVO();

                    pedVO.setMemberSeqID(rs.getString("MEMBER_SEQ_ID")!= null ? new Long(rs.getLong("MEMBER_SEQ_ID")):null);
                    pedVO.setPEDCodeID(rs.getString("PED_CODE_ID")!= null ? new Long(rs.getLong("PED_CODE_ID")):null);
                    pedVO.setDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    pedVO.setDuration(TTKCommon.checkNull(rs.getString("MEM_DURATION")));
                    pedVO.setICDCode(TTKCommon.checkNull(rs.getString("ICD_CODE")));
                    pedVO.setRemarks(TTKCommon.checkNull(rs.getString("REMARKS")));
                    //added for koc 1278
                    pedVO.setWaitingPeriod(TTKCommon.checkNull(rs.getString("PED_WAITING_PERIOD")));
                    pedVO.setPersonalWaitingPeriod(TTKCommon.checkNull(rs.getString("ENR_DURATION_GENERAL_TYPE_ID")));
                    pedVO.setAilmentTypeID(TTKCommon.checkNull(rs.getString("AILMENT_GENERAL_TYPE_ID")));
                    //added for koc 1278
                    if(rs.getString("DURATION_YEARS") != null){
                    	pedVO.setDurationYears(new Integer(rs.getInt("DURATION_YEARS")));
                    }//end of if(rs.getString("DURATION_YEARS") != null)

                    if(rs.getString("DURATION_MONTHS") != null){
                    	pedVO.setDurationMonths(new Integer(rs.getInt("DURATION_MONTHS")));
                    }//end of if(rs.getString("DURATION_MONTHS") != null)

                    if(strIdentifier.equalsIgnoreCase("Pre-Authorization")||strIdentifier.equalsIgnoreCase("Coding")){
                    	pedVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    }//end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization")||strIdentifier.equalsIgnoreCase("Coding"))

                    if(strIdentifier.equalsIgnoreCase("Pre-Authorization") && strShowSave.equalsIgnoreCase("Insurance")){
                    	pedVO.setPEDSeqID(rs.getString("MEM_PED_SEQ_ID")!= null ? new Long(rs.getLong("MEM_PED_SEQ_ID")):null);
                    }//end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization") && strShowSave.equalsIgnoreCase("Insurance"))

                    if(strIdentifier.equalsIgnoreCase("Pre-Authorization") && strShowSave.equalsIgnoreCase("TTK")){
                    	pedVO.setSeqID(rs.getString("CLAIMANT_PED_SEQ_ID")!= null ? new Long(rs.getLong("CLAIMANT_PED_SEQ_ID")):null);
                    }//end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization") && strShowSave.equalsIgnoreCase("TTK"))

                    if(strIdentifier.equalsIgnoreCase("Enrollment")){
                    	pedVO.setPEDSeqID(rs.getString("MEM_PED_SEQ_ID")!= null ? new Long(rs.getLong("MEM_PED_SEQ_ID")):null);
                    }//end of if(strIdentifier.equalsIgnoreCase("Enrollment"))

                    if(strIdentifier.equalsIgnoreCase("Coding")&& strShowSave.equalsIgnoreCase("Insurance")){
                    	pedVO.setSeqID(rs.getString("MEM_PED_SEQ_ID")!= null ? new Long(rs.getLong("MEM_PED_SEQ_ID")):null);
                    }//end of if(strIdentifier.equalsIgnoreCase("Coding")&& strShowSave.equalsIgnoreCase("Insurance"))

                    if(strIdentifier.equalsIgnoreCase("Coding") && strShowSave.equalsIgnoreCase("TTK")){
                    	pedVO.setSeqID(rs.getString("CLAIMANT_PED_SEQ_ID")!= null ? new Long(rs.getLong("CLAIMANT_PED_SEQ_ID")):null);
                    }//end of if(strIdentifier.equalsIgnoreCase("Coding") && strShowSave.equalsIgnoreCase("TTK"))
                 }//end of while(rs.next())
            }// End of if (rs!=null)
          }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getPED()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getPED()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getPED()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return pedVO;
    }//end of getPED(long lngSeqID,String strIdentifier,String strShowSave)

    /**
     * This method returns the String contains ICDCode
     * @param lngPEDCodeID long value which contains PEDCode ID
     * @return PEDVO contains the PED information
     * @exception throws TTKException
     */
    public PEDVO getDescriptionList(long lngPEDCodeID) throws TTKException {
        Connection conn1 = null;
        ResultSet rs = null;
        PreparedStatement pStmt = null;
        PEDVO pedVO = null;
        try{
        	conn1 = ResourceManager.getConnection();
            pStmt = conn1.prepareStatement(strGetDescriptionList);
            pStmt.setLong(1,lngPEDCodeID);
            rs = pStmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                	pedVO = new PEDVO();
                	pedVO.setDescription(TTKCommon.checkNull(rs.getString("PED_DESCRIPTION")));
                	pedVO.setICDCode(TTKCommon.checkNull(rs.getString("ICD_CODE")));
               }//end of while(rs.next())
            }//end of if(rs != null)
            return pedVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getDescriptionList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getDescriptionList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn1 != null) conn1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getDescriptionList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				pStmt = null;
				conn1 = null;
			}//end of finally
		}//end of finally
    }//end of getDescriptionList(long lngPEDCodeID)

    /**
	 * This method saves the PED details
	 * @param pedVO the object which contains the details of the PED
	 * @param lngSeqID long value in Enrollment Flow - PolicySeqID/EndorsementSeqID and in Preauthorization flow, Webboard Seq ID as PreauthSeqID and in Claims Flow ClaimSeqID
	 * @param strIdentifier Object which contains Identifier for identifying the flow - Pre-Authorization/Enrollment/Claims
	 * @param strMode Object which contains ENM/END for Enrollment Flow and in Preauth/Claims flow PAT/CLM
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int savePED(PEDVO pedVO,long lngSeqID,String strIdentifier,String strMode) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            if(strIdentifier.equals("Pre-Authorization")){
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePreauthPED);

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

            	cStmtObject.setString(5,pedVO.getDuration());
                cStmtObject.setString(6,pedVO.getOtherDesc());
                cStmtObject.setString(7,pedVO.getICDCode());
                cStmtObject.setString(8,pedVO.getRemarks());
                cStmtObject.setString(9,strMode);

                if(pedVO.getDurationYears() != null){
                	cStmtObject.setInt(10,pedVO.getDurationYears());
                }//end of if(pedVO.getDurationYears() != null)
                else{
                	cStmtObject.setString(10,null);
                }//end of else

                if(pedVO.getDurationMonths() != null){
                	cStmtObject.setInt(11,pedVO.getDurationMonths());
                }//end of if(pedVO.getDurationMonths() != null)
                else{
                	cStmtObject.setString(11,null);
                }//end of else

                cStmtObject.setLong(12,pedVO.getUpdatedBy());//ADDED_BY
                cStmtObject.setString(13,pedVO.getDescription());
                cStmtObject.registerOutParameter(14, Types.INTEGER);//ROWS_PROCESSED
                cStmtObject.execute();
                iResult = cStmtObject.getInt(14);//ROWS_PROCESSED
            }//end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization"))

            else{

            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePED);
            	if(pedVO.getPEDSeqID() != null){
                    cStmtObject.setLong(1,pedVO.getPEDSeqID());
                }//end of if(pedVO.getPEDSeqID() != null)
                else{
                    cStmtObject.setLong(1,0);
                }//end of else
                if(pedVO.getMemberSeqID() != null){
                    cStmtObject.setLong(2,pedVO.getMemberSeqID());
                }//end of if(pedVO.getMemberSeqID() != null)
                else{
                    cStmtObject.setString(2,null);
                }//end of else
                if(pedVO.getPEDCodeID() < 0){
                    cStmtObject.setString(3,null);
                }//end of if(pedVO.getPEDCodeID() != null)
                else{
                    cStmtObject.setLong(3,pedVO.getPEDCodeID());
                }//end of else
                cStmtObject.setString(4,pedVO.getDuration());
                cStmtObject.setString(5,pedVO.getOtherDesc());
                cStmtObject.setString(6,pedVO.getICDCode());
                cStmtObject.setString(7,pedVO.getRemarks());
                cStmtObject.setLong(8, pedVO.getUpdatedBy());//ADDED_BY
                cStmtObject.setString(9,pedVO.getDescription());
                cStmtObject.setLong(10,lngSeqID);

                if(pedVO.getDurationYears() != null){
                	cStmtObject.setInt(11,pedVO.getDurationYears());
                }//end of if(pedVO.getDurationYears() != null)
                else{
                	cStmtObject.setString(11,null);
                }//end of else

                if(pedVO.getDurationMonths() != null){
                	cStmtObject.setInt(12,pedVO.getDurationMonths());
                }//end of if(pedVO.getDurationMonths() != null)
                else{
                	cStmtObject.setString(12,null);
                }//end of else
                //added for koc 1278
                cStmtObject.setString(13,pedVO.getWaitingPeriod());
                cStmtObject.setString(14,pedVO.getPersonalWaitingPeriod());
                cStmtObject.setString(15,pedVO.getAilmentTypeID());
                //added for koc 1278
                cStmtObject.setString(16,strMode);
                cStmtObject.registerOutParameter(17, Types.INTEGER);//ROWS_PROCESSED
                cStmtObject.execute();
                iResult = cStmtObject.getInt(17);//ROWS_PROCESSED
            }//end of else
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl savePED()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl savePED()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of savePED(PEDVO pedVO,long lngSeqID,String strIdentifier,String strMode)

    /**
     * This method deletes the PED information from the database
     * @param alDeletePED arraylist which the the details of the PED has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deletePED(ArrayList alDeletePED ) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeletePED);
            cStmtObject.setString(1, (String)alDeletePED.get(0));//FLAG PED
			cStmtObject.setString(2, (String)alDeletePED.get(1));//CONCATENATED STRING OF MEM_PED_SEQ_ID'S
			cStmtObject.setLong(3,(Long)alDeletePED.get(2));// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			cStmtObject.setString(4, (String)alDeletePED.get(3));//-- Mode can be 'ENM','END'
			cStmtObject.setString(5, (String)alDeletePED.get(4));//-- 'IP',IG,CP,NC
			cStmtObject.setLong(6,(Long)alDeletePED.get(5));//ADDED_BY
			cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
		}//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl deletePED()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl deletePED()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of deletePED(ArrayList alDeletePED )

    /**
	 * This method returns the ArrayList, which contains the PEDVO's which are populated from the database
	 * @param alSearchCriteria ArrayList object which contains the search criteria
	 * @return ArrayList of PEDVO'S object's which contains ICD Details
	 * @exception throws TTKException
	 */
	public ArrayList getICDList(ArrayList alSearchCriteria) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		PEDVO pedVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetICDList);
            cStmtObject.setString(1,(String)alSearchCriteria.get(0));//ICD_CODE
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));//PED_DESCRIPTION
            cStmtObject.setString(3,(String)alSearchCriteria.get(3)); //ORDER BY COLUMN
            cStmtObject.setString(4,(String)alSearchCriteria.get(4)); //SORTING ORDER
            cStmtObject.setString(5,(String)alSearchCriteria.get(5)); //START ROW
            cStmtObject.setString(6,(String)alSearchCriteria.get(6)); //END ROW
            cStmtObject.setLong(7,(Long)alSearchCriteria.get(2));//UPDATED_BY
            cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            if(rs != null){
                while (rs.next()) {
                	pedVO = new PEDVO();

                	if(rs.getString("PED_CODE_ID") != null){
                		pedVO.setPEDCodeID(new Long(rs.getLong("PED_CODE_ID")));
                	}//end of if(rs.getString("PED_CODE_ID") != null)

                	pedVO.setICDCode(TTKCommon.checkNull(rs.getString("ICD_CODE")));
                	pedVO.setDescription(TTKCommon.checkNull(rs.getString("PED_DESCRIPTION")));
                	pedVO.setRuleAssociateYN(TTKCommon.checkNull(rs.getString("RULE_ASSOCIATE_YN")));
                	//pedVO.setPEDCount(new Integer(rs.getInt("PED_COUNT")));
                	alResultList.add(pedVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getICDList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getICDList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getICDList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getICDList(ArrayList alSearchCriteria)

    /**
     * This method returns the ArrayList, which contains the ICDCodeVO's which are populated from the database
     * @param strAilment String object,based on that String parameter ICD Code details to be fetched from the External Source
     * @return ArrayList of ICDCodeVO which contains ICD Code details
     * @exception throws TTKException
     */
    public ArrayList getICDCode(String strAilment) throws TTKException {
        ArrayList<Object> alResultList = new ArrayList<Object>();
    	ICDCodeVO icdCodeVO = null;
        String[][] strICDCodeList = null;
        try{

            testClient = new TestClient();
            strICDCodeList = testClient.getICD(strAilment);
            String[] temp = null;

            if(strICDCodeList != null){
            	if(strICDCodeList.length >0) {
            		for(int i=0;i<strICDCodeList.length;i++){

            			temp = strICDCodeList[i];
            			icdCodeVO = new ICDCodeVO();

            			icdCodeVO.setPercentage(temp[0]);
            			icdCodeVO.setDRG(temp[1]);
            			icdCodeVO.setDRG_D(temp[2]);
            			icdCodeVO.setDescription(temp[3]);
            			alResultList.add(icdCodeVO);
            		}//end of outer for
            	}//end of if(strICDCode.length >0)
            }//end of if(strICDCodeList != null)
            return alResultList;
        }//end of try
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
        }//end of catch (Exception exp)
    }//end of getICDCode()

    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of MemberVO'S object's which contains the details of the Member suspension details
     * @exception throws TTKException
     */
    public ArrayList getSuspensionList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        MemberVO memberVO = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSuspensionList);
            cStmtObject.setString(1,(String)alSearchCriteria.get(0));//MEMBER_SEQ_ID
            cStmtObject.setString(2,(String)alSearchCriteria.get(1)); //ORDER BY COLUMN
            cStmtObject.setString(3,(String)alSearchCriteria.get(2)); //SORTING ORDER
            cStmtObject.setString(4,(String)alSearchCriteria.get(3)); //START ROW
            cStmtObject.setString(5,(String)alSearchCriteria.get(4)); //END ROW
            cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(6);
            if(rs != null){
                while (rs.next()) {
                    memberVO = new MemberVO();
                    memberVO.setSupensionSeqID(rs.getString("MEM_SUSPEND_HIST_ID")!= null ? new Long(rs.getLong("MEM_SUSPEND_HIST_ID")):null);
                    memberVO.setMemberSeqID(rs.getString("MEMBER_SEQ_ID")!=null ? new Long(rs.getLong("MEMBER_SEQ_ID")):null);
                    memberVO.setStartDate(rs.getString("MEM_SUSPEND_FROM")!=null ? new Date(rs.getTimestamp("MEM_SUSPEND_FROM").getTime()):null);
                    memberVO.setEndDate(rs.getString("MEM_SUSPEND_TO")!=null ? new Date(rs.getTimestamp("MEM_SUSPEND_TO").getTime()):null);
                    alResultList.add(memberVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getSuspensionList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getSuspensionList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getSuspensionList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }// End of getSuspensionList(ArrayList alSearchCriteria)

    /**
     * This method saves the member suspension details
     * @param memberVO the object which contains the details of the member suspension
     * @param strPolicyMode the String object which contains the Policy Mode
     * @param strPolicType the String object which contains the Policy Type
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveSuspension(MemberVO memberVO,String strPolicyMode,String strPolicyType) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveSuspension);
            cStmtObject.setLong(1,memberVO.getSupensionSeqID()!=null ? memberVO.getSupensionSeqID():0);//MEM_SUSPEND_HIST_ID
            cStmtObject.setLong(2,memberVO.getMemberSeqID());//MEMBER_SEQ_ID
            cStmtObject.setLong(3,memberVO.getPolicyGroupSeqID());//POLICY_GROUP_SEQ_ID
            cStmtObject.setTimestamp(4,memberVO.getStartDate()!=null ? new Timestamp(memberVO.getStartDate().getTime()):null); //MEM_SUSPEND_FROM
            cStmtObject.setTimestamp(5,memberVO.getEndDate()!=null ? new Timestamp(memberVO.getEndDate().getTime()):null); //MEM_SUSPEND_TO
            if(memberVO.getEndorsementSeqID()!=null)
            {
                cStmtObject.setLong(6, memberVO.getEndorsementSeqID());//ENDORSEMENT_SEQ_ID
            }//end of if(memberVO.getEndorsementSeqID()!=null)
            else
            {
                cStmtObject.setString(6,null);
            }//end of else
            cStmtObject.setString(7, strPolicyMode);//POLICY_MODE
            cStmtObject.setString(8, strPolicyType);//POLICY_TYPE
            cStmtObject.setLong(9, memberVO.getUpdatedBy());//ADDED_BY
            cStmtObject.registerOutParameter(10, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(10);//ROWS_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl saveSuspension()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl saveSuspension()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }// End of saveSuspension(MemberVO memberVO,String strPolicyMode,String strPolicyType)

    /**
     * This method deletes the suspension member information from the database
     * @param alDeleteSuspension the details of the supension which has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteSuspension(ArrayList alDeleteSuspension) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteSuspension);
            cStmtObject.setString(1, (String)alDeleteSuspension.get(0));//FLAG SUSPEND
			cStmtObject.setString(2, (String)alDeleteSuspension.get(1));//CONCATENATED STRING OF MEM_SUSPEND_HIST_ID'S
			cStmtObject.setLong(3,(Long)alDeleteSuspension.get(2));// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			cStmtObject.setString(4, (String)alDeleteSuspension.get(3));//-- Mode can be 'ENM','END'
			cStmtObject.setString(5, (String)alDeleteSuspension.get(4));//-- 'IP',IG,CP,NC
			cStmtObject.setLong(6,(Long)alDeleteSuspension.get(5));//ADDED_BY
			cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl deleteSuspension()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl deleteSuspension()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//End of deleteSuspension(ArrayList alDeleteSuspension)

    /**
     * This method returns the renewal group members from the database
     * @param alSearchCriteria ArrayList object which contains policy group sequence id for which the renewal member details to be fetched
     * @return ArrayList which contains MemberVO objects which consists of renewal members
     * @exception throws TTKException
     */
    public ArrayList getRenewMemberList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        MemberVO memberVO = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strRenewMembersList);
            cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if (rs!=null)
            {
                while (rs.next())
                {
                    memberVO = new MemberVO();
                    memberVO.setMemberSeqID(rs.getString("MEMBER_SEQ_ID")!=null ? new Long(rs.getLong("MEMBER_SEQ_ID")):null);
                    memberVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
                    memberVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                    memberVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER")));
                    memberVO.setRelationDesc(TTKCommon.checkNull(rs.getString("RELSHIP_DESCRIPTION")));
                    memberVO.setAge(rs.getString("MEM_AGE")!=null ? new Integer(rs.getInt("MEM_AGE")):null);
                    if(rs.getString("MEM_DOB")!=null){
                    	memberVO.setDOB(new java.util.Date(rs.getTimestamp("MEM_DOB").getTime()));
                    }//end of if(rs.getString("MEM_DOB")!=null)

                    memberVO.setRenewalYn(TTKCommon.checkNull(rs.getString("RENEW_YN")));
                    alResultList.add(memberVO);
                }// End of while (rs.next())
            }// End of if (rs!=null)
            return (ArrayList) alResultList;
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getRenewMemberList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getRenewMemberList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getRenewMemberList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }// End of getRenewMemberList(ArrayList alSearchCriteria)

    /**
     * This method saves the renewal information to the groups
     * @param alRenew the ArrayList object which contains renewal information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveRenewals(ArrayList alRenew) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strRenewMember);
            cStmtObject.setString(1,(String)alRenew.get(0));//MEMBER_SEQ_ID
            cStmtObject.setLong(2,(Long)alRenew.get(1));//ADDED_BY
            cStmtObject.setLong(3,(Long)alRenew.get(2));//POLICY_GROUP_SEQ_ID
            cStmtObject.setLong(4,(Long)alRenew.get(3));//POLICY_SEQ_ID
            if(alRenew.get(4) != null){
            	cStmtObject.setLong(5,(Long)alRenew.get(4));//ENDORSEMENT_SEQ_ID
            }//end of if(alRenew.get(4) != null)
            else{
            	cStmtObject.setString(5,null);
            }//end of else
            cStmtObject.setString(6,(String)alRenew.get(5));//POLICY_MODE - ENM/END
            cStmtObject.setString(7,(String)alRenew.get(6));//POLICY_TYPE - IP/IG/CP/NC
            cStmtObject.registerOutParameter(8,Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(8);//ROWS_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl saveRenewals()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl saveRenewals()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }// End of saveRenewals(ArrayList alRenew)

    /**
     * This method saves the enrollment details
     * @param memberDetailVO the object which contains the details of the enrollment
     * @return lngPolicySeqID long Object, which contains the Policy Group Seq ID
     * @exception throws TTKException
     */
    public long saveEnrollment(MemberDetailVO memberDetailVO,String strPolicyMode,String strPolicyType) throws TTKException {
        long lngPolicyGroupSeqID = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveEnrollment);
            if(memberDetailVO.getPolicyGroupSeqID()!=null){
                cStmtObject.setLong(1,memberDetailVO.getPolicyGroupSeqID());
            }//end of if(memberDetailVO.getPolicyGroupSeqID()!=null)
            else{
                cStmtObject.setLong(1,0);
            }//end of else
            if(memberDetailVO.getPolicySeqID()!=null){
                cStmtObject.setLong(2,memberDetailVO.getPolicySeqID());
            }//end of if(memberDetailVO.getPolicySeqID()!=null)
            else{
                cStmtObject.setString(2,null);
            }//end of else
            cStmtObject.setString(3,memberDetailVO.getEnrollmentID());//TPA_ENROLLMENT_NUMBER
            cStmtObject.setString(4,memberDetailVO.getOrderNbr());//ORDER_NUMBER
            cStmtObject.setString(5,memberDetailVO.getEmployeeNbr());//EMPLOYEE_NO

            if(memberDetailVO.getMemberAddressVO().getAddrSeqId() != null){
                cStmtObject.setLong(6,memberDetailVO.getMemberAddressVO().getAddrSeqId());
            }//end of if(memberDetailVO.getMemberAddressVO().getAddrSeqId() != null)
            else{
                cStmtObject.setString(6,null);
            }//end of else

            cStmtObject.setString(7,memberDetailVO.getName());//INSURED_NAME
            cStmtObject.setString(8,memberDetailVO.getDepartment());//DEPARTMENT
            cStmtObject.setString(9,memberDetailVO.getDesignation());//DESIGNATION

            if(memberDetailVO.getStartDate()!=null){
                cStmtObject.setTimestamp(10,new Timestamp(memberDetailVO.getStartDate().getTime()));
            }//end of if(memberDetailVO.getStartDate()!=null)
            else{
                cStmtObject.setTimestamp(10,null);
            }//end of else

            if(memberDetailVO.getEndDate()!=null){
                cStmtObject.setTimestamp(11,new Timestamp(memberDetailVO.getEndDate().getTime()));
            }//end of memberDetailVO.getEndDate()!=null
            else{
                cStmtObject.setTimestamp(11,null);
            }//end of else

            cStmtObject.setString(12,memberDetailVO.getBeneficiaryname());//BENEFICIARY_NAME
            cStmtObject.setString(13,memberDetailVO.getRelationTypeID());//RELSHIP_TYPE_ID

            if(memberDetailVO.getGroupRegnSeqID()!=null){
                cStmtObject.setLong(14,memberDetailVO.getGroupRegnSeqID());
            }//end of if(memberDetailVO.getGroupRegnSeqID()!=null)
            else{
                cStmtObject.setString(14,null);
            }//end of else

            cStmtObject.setString(15,memberDetailVO.getProposalFormYN());//PROPOSAL_FORM_YN
            if(memberDetailVO.getDeclarationDate()!=null){
                cStmtObject.setTimestamp(16,new Timestamp(memberDetailVO.getDeclarationDate().getTime()));
            }//end of if(memberDetailVO.getDeclarationDate()!=null)
            else{
                cStmtObject.setTimestamp(16,null);
            }//end of else

            cStmtObject.setString(17,memberDetailVO.getCustomerNbr());//CUSTOMER_NO

            if(memberDetailVO.getBankSeqID()!=null){
                cStmtObject.setLong(18,memberDetailVO.getBankSeqID());
            }//end of if(memberDetailVO.getBankSeqID()!=null)
            else{
                cStmtObject.setString(18,null);
            }//end of else

            cStmtObject.setString(19,memberDetailVO.getBankAccNbr());
            cStmtObject.setString(20,memberDetailVO.getBankName());
            cStmtObject.setString(21,memberDetailVO.getBankPhone());
            cStmtObject.setString(22,memberDetailVO.getBranch());
            cStmtObject.setString(23,memberDetailVO.getMICRCode());
            cStmtObject.setLong(24,memberDetailVO.getUpdatedBy());//ADDED_BY
            cStmtObject.setString(25,memberDetailVO.getMemberAddressVO().getAddress1());//ADDRESS_1
            cStmtObject.setString(26,memberDetailVO.getMemberAddressVO().getAddress2());//ADDRESS_2
            cStmtObject.setString(27,memberDetailVO.getMemberAddressVO().getAddress3());//ADDRESS_3
            cStmtObject.setString(28,memberDetailVO.getMemberAddressVO().getStateCode());//STATE_TYPE_ID
            cStmtObject.setString(29,memberDetailVO.getMemberAddressVO().getCityCode());//CITY_TYPE_ID
            cStmtObject.setString(30,memberDetailVO.getMemberAddressVO().getPinCode());//PIN_CODE
            cStmtObject.setString(31,memberDetailVO.getMemberAddressVO().getCountryCode());//COUNTRY_ID
            cStmtObject.setString(32,memberDetailVO.getMemberAddressVO().getEmailID());//EMAIL_ID
            cStmtObject.setString(33,memberDetailVO.getMemberAddressVO().getHomePhoneNbr());//RES_PHONE_NO
            cStmtObject.setString(34,memberDetailVO.getMemberAddressVO().getPhoneNbr1());//OFF_PHONE_NO_1
            cStmtObject.setString(35,memberDetailVO.getMemberAddressVO().getPhoneNbr2());//OFF_PHONE_NO_2
            cStmtObject.setString(36,memberDetailVO.getMemberAddressVO().getMobileNbr());//MOBILE_NO
            cStmtObject.setString(37,memberDetailVO.getMemberAddressVO().getFaxNbr());//FAX_NO
            cStmtObject.setString(38,memberDetailVO.getCertificateNbr());
            cStmtObject.setString(39,memberDetailVO.getCreditCardNbr());
            cStmtObject.setString(40,"EXT");

            if(memberDetailVO.getEndorsementSeqID() != null){
                cStmtObject.setLong(41,memberDetailVO.getEndorsementSeqID());//ENDORSEMENT_SEQ_ID
            }//end of if(memberDetailVO.getEndorsementSeqID() != null)
            else{
                cStmtObject.setString(41,null);
            }//end of else

            cStmtObject.setString(42,memberDetailVO.getStopPatClmYN());
            if(memberDetailVO.getReceivedAfter()!=null){
                cStmtObject.setTimestamp(43,new Timestamp(memberDetailVO.getReceivedAfter().getTime()));
            }//end of if(memberDetailVO.getReceivedAfter()!=null)
            else{
                cStmtObject.setTimestamp(43,null);
            }//end of else

            cStmtObject.setString(44,memberDetailVO.getEmpStatusTypeID());

            if(memberDetailVO.getDateOfMarriage()!=null){
                cStmtObject.setTimestamp(45,new Timestamp(memberDetailVO.getDateOfMarriage().getTime()));
            }//end of if(memberDetailVO.getDateOfMarriage()!=null)
            else{
                cStmtObject.setTimestamp(45,null);
            }//end of else

            cStmtObject.setString(46,memberDetailVO.getPrevOrderNbr());
            cStmtObject.setString(47,strPolicyMode);
            cStmtObject.setString(48,strPolicyType);
            cStmtObject.setString(49,memberDetailVO.getFamilyNbr());
            cStmtObject.registerOutParameter(50,Types.INTEGER);//ROW_PROCESSED
            //added by Praveen for KOC-1216
			cStmtObject.setString(51,memberDetailVO.getMemberAddressVO().getEmailID2());//added for EmailId2
            cStmtObject.registerOutParameter(1,Types.BIGINT);//ROW_PROCESSED
            cStmtObject.execute();
            lngPolicyGroupSeqID = cStmtObject.getLong(1);//POLICY_GROUP_SEQ_ID
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl saveEnrollment()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl saveEnrollment()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngPolicyGroupSeqID;
    }//end of saveEnrollment(MemberDetailVO memberDetailVO,String strPolicyMode,String strPolicyType)

    /**
     * This method returns the MemberDetailVO, which contains the Enrollment details which are populated from the database
     * @param alEnrollment ArrayList which contains seq id for Enrollment or Endorsement flow to get the Enrollment information
     * @return MemberDetailVO object's which contains the details of the Enrollment
     * @exception throws TTKException
     */
    public MemberDetailVO getEnrollment(ArrayList alEnrollment) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        MemberDetailVO memberDetailVO = null;
        MemberAddressVO memberAddressVO = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetEnrollment);
            cStmtObject.setLong(1,(Long)alEnrollment.get(0));
            cStmtObject.setString(2,(String)alEnrollment.get(1));
            cStmtObject.setString(3,(String)alEnrollment.get(2));
            cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if(rs != null){
            	while(rs.next()){
            		memberDetailVO = new MemberDetailVO();
            		memberAddressVO = new MemberAddressVO();

            		if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
            			memberDetailVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
            		}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

            		if(rs.getString("POLICY_SEQ_ID") != null){
            			memberDetailVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
            		}//end of if(rs.getString("POLICY_SEQ_ID") != null)

            		if(rs.getString("ENR_ADDRESS_SEQ_ID") != null){
            			memberAddressVO.setAddrSeqId(new Long(rs.getLong("ENR_ADDRESS_SEQ_ID")));
            		}//end of if(rs.getString("ENR_ADDRESS_SEQ_ID") != null)

            		memberDetailVO.setOrderNbr(TTKCommon.checkNull(rs.getString("ORDER_NUMBER")));
            		memberDetailVO.setPrevOrderNbr(TTKCommon.checkNull(rs.getString("PREV_ORDER_NUMBER")));
            		memberDetailVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
            		memberDetailVO.setName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
            		memberDetailVO.setDepartment(TTKCommon.checkNull(rs.getString("DEPARTMENT")));
            		memberDetailVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATION")));

            		if(rs.getString("DATE_OF_JOINING") != null){
            			memberDetailVO.setStartDate(new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
            		}//end of if(rs.getString("DATE_OF_JOINING") != null)

            		if(rs.getString("DATE_OF_RESIGNATION") != null){
            			memberDetailVO.setEndDate(new Date(rs.getTimestamp("DATE_OF_RESIGNATION").getTime()));
            		}//end of if(rs.getString("DATE_OF_RESIGNATION") != null)

            		memberAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
            		memberAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
            		memberAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
            		memberAddressVO.setCityCode(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));
            		memberAddressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
            		memberAddressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
            		memberAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
            		memberAddressVO.setHomePhoneNbr(TTKCommon.checkNull(rs.getString("RES_PHONE_NO")));
            		memberAddressVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
            		memberAddressVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
            		memberAddressVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
            		memberAddressVO.setFaxNbr(TTKCommon.checkNull(rs.getString("FAX_NO")));
            		memberAddressVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
            		//Added by Praveen for EmailId2 - for KOC-1216
					memberAddressVO.setEmailID2(TTKCommon.checkNull(rs.getString("EMAIL_ID1")));//added for EmailId2
					//Ended
               		memberDetailVO.setMemberAddressVO(memberAddressVO);
            		memberDetailVO.setBeneficiaryname(TTKCommon.checkNull(rs.getString("BENEFICIARY_NAME")));
            		memberDetailVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
            		memberDetailVO.setCustomerNbr(TTKCommon.checkNull(rs.getString("CUSTOMER_NO")));
            		memberDetailVO.setProposalFormYN(TTKCommon.checkNull(rs.getString("PROPOSAL_FORM_YN")));
            		if(rs.getString("DECLARATION_DATE") != null){
            			memberDetailVO.setDeclarationDate(new Date(rs.getTimestamp("DECLARATION_DATE").getTime()));
            		}//end of if(rs.getString("DATE_OF_JOINING") != null)
            		if(rs.getString("BANK_SEQ_ID") != null){
            			memberDetailVO.setBankSeqID(new Long(rs.getLong("BANK_SEQ_ID")));
            		}//end of if(rs.getString("BANK_SEQ_ID") != null)

            		memberDetailVO.setCertificateNbr(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
            		memberDetailVO.setCreditCardNbr(TTKCommon.checkNull(rs.getString("CREDITCARD_NO")));
            		memberDetailVO.setBankAccNbr(TTKCommon.checkNull(rs.getString("BANK_ACCOUNT_NO")));
            		memberDetailVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));
            		memberDetailVO.setBranch(TTKCommon.checkNull(rs.getString("BANK_BRANCH")));
            		memberDetailVO.setBankPhone(TTKCommon.checkNull(rs.getString("BANK_PHONE_NO")));
            		memberDetailVO.setMICRCode(TTKCommon.checkNull(rs.getString("BANK_MICR")));
            		if(rs.getString("GROUP_REG_SEQ_ID") != null){
            			memberDetailVO.setGroupRegnSeqID(new Long(rs.getLong("GROUP_REG_SEQ_ID")));
            		}//end of if(rs.getString("GROUP_REG_SEQ_ID") != null)

            		memberDetailVO.setStopPatClmYN(TTKCommon.checkNull(rs.getString("STOP_PAT_CLM_PROCESS_YN")));
            		if(rs.getString("RECIEVED_AFTER") != null){
            			memberDetailVO.setReceivedAfter(new Date(rs.getTimestamp("RECIEVED_AFTER").getTime()));
            		}//end of if(rs.getString("RECIEVED_AFTER") != null)

            		memberDetailVO.setEmpStatusTypeID(TTKCommon.checkNull(rs.getString("STATUS_GENERAL_TYPE_ID")));
            		memberDetailVO.setEmpStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
            		memberDetailVO.setFamilyNbr(TTKCommon.checkNull(rs.getString("FAMILY_NUMBER")));
            		if(rs.getString("DATE_OF_MARRIAGE") != null){
            			memberDetailVO.setDateOfMarriage(new Date(rs.getTimestamp("DATE_OF_MARRIAGE").getTime()));
            		}//end of if(rs.getString("DATE_OF_MARRIAGE") != null)

            	}//end of while(rs.next())
            }//end of if(rs != null)
            return memberDetailVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getEnrollment()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getEnrollment()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getEnrollment()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getEnrollment(ArrayList alEnrollment)

    /**
     * This method returns the ArrayList, which contains Groups corresponding to Group Reg Seq ID
     * @param lngPolicySeqID It contains the Policy Seq ID
     * @return ArrayList object which contains Groups corresponding to Group Reg Seq ID
     * @exception throws TTKException
     */
    public ArrayList getLocation(long lngPolicySeqID) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> alGroup = new ArrayList<Object>();
        CacheObject cacheObject = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strLocation);
            cStmtObject.setLong(1,lngPolicySeqID);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId((rs.getString("GROUP_REG_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
                    alGroup.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alGroup;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getLocation()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getLocation()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getLocation()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getLocation(long lngPolicySeqID)

    /**
     * This method returns the Arraylist of PremiumInfoVO's  which contains Premium details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PremiumInfoVO object which contains Premium details
     * @exception throws TTKException
     */
    public ArrayList<Object> getPremiumList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        PremiumInfoVO premiumInfoVO= null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPremiumList);
            cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if(rs != null){
                while (rs.next()) {
                    premiumInfoVO = new PremiumInfoVO();
                    premiumInfoVO.setPolicySubTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
                    premiumInfoVO.setPolicySubTypeDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    if(rs.getString("MEMBER_SEQ_ID") != null){
                        premiumInfoVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
                    }//end of if(rs.getString("MEMBER_SEQ_ID") != null)
                    premiumInfoVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                    premiumInfoVO.setMemberPolicyTypeID(TTKCommon.checkNull(rs.getString("MEM_GENERAL_TYPE_ID")));
                    if(rs.getString("MEM_TOT_SUM_INSURED") != null){
                        premiumInfoVO.setTotalSumInsured(new BigDecimal(rs.getString("MEM_TOT_SUM_INSURED")));
                    }//end of if(rs.getString("MEM_TOT_SUM_INSURED") != null)
                    premiumInfoVO.setPremiumPaid(TTKCommon.checkNull(rs.getString("MEM_TOTAL_PREMIUM")));
                    if(rs.getString("MEM_TOT_BONUS") != null){
                        premiumInfoVO.setCumulativeBonusAmt(new BigDecimal(rs.getString("MEM_TOT_BONUS")));
                    }//end of if(rs.getString("MEM_TOT_BONUS") != null)
                    if(rs.getString("CALC_PREMIUM") != null){
                        premiumInfoVO.setCalcPremium(new BigDecimal(rs.getString("CALC_PREMIUM")));
                    }//end of if(rs.getString("CALC_PREMIUM") != null)

                    if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
                        premiumInfoVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

                    if(rs.getString("PRODUCT_SEQ_ID") != null){
                        premiumInfoVO.setProductSeqID(new Long(rs.getLong("PRODUCT_SEQ_ID")));
                    }//end of if(rs.getString("PRODUCT_SEQ_ID") != null)

                    if(rs.getString("POLICY_SEQ_ID") != null){
                        premiumInfoVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_SEQ_ID") != null)

                    if(rs.getString("FAMILY_TOT_SUM_INSURED") != null){
                        premiumInfoVO.setTotalFlySumInsured(new BigDecimal(rs.getString("FAMILY_TOT_SUM_INSURED")));
                    }//end of if(rs.getString("FAMILY_TOT_SUM_INSURED") != null)
                    premiumInfoVO.setTotalFamilyPremium(TTKCommon.checkNull(rs.getString("FAMILY_TOTAL_PREMIUM")));

                    if(rs.getString("FLOATER_SUM_INSURED") != null){
                        premiumInfoVO.setFloaterSumInsured(new BigDecimal(rs.getString("FLOATER_SUM_INSURED")));
                    }//end of if(rs.getString("FLOATER_SUM_INSURED") != null)

                    premiumInfoVO.setFloatPremium(TTKCommon.checkNull(rs.getString("FLOATER_PREMIUM")));
                    premiumInfoVO.setCancelYN(TTKCommon.checkNull(rs.getString("CANCEL_YN")));

                    if(rs.getString("EFFECTIVE_FROM_DATE") != null){
                    	premiumInfoVO.setPolicyStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)
                    //Added as per koC 1284 Change Request
                    if(rs.getString("REGION_ID") != null){
                        premiumInfoVO.setSelectregion(rs.getString("REGION_ID"));
                    }//end of if(rs.getString("REGION_ID") != null)
                    if(rs.getString("REGION_FLAG_YN") != null){
                        premiumInfoVO.setSelectregionYN(rs.getString("REGION_FLAG_YN"));
                    }//end of if(rs.getString("REGION_ID") != null)
                    //Added as per koC 1284 Change Request
                    alResultList.add(premiumInfoVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList<Object>)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getPremiumList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getPremiumList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getPremiumList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPremiumList(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of SumInsuredVO's  which contains Bonus details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SumInsuredVO object which contains bonus details
     * @exception throws TTKException
     */
    public ArrayList getBonusList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        SumInsuredVO sumInsuredVO= null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBonusList);
            if(alSearchCriteria.get(0) != null){
                cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            }//end of if(alSearchCriteria.get(0) != null)
            else{
                cStmtObject.setString(1,null);
            }//end of else
            if(alSearchCriteria.get(1) != null){
                cStmtObject.setLong(2,(Long)alSearchCriteria.get(1));
            }//end of if(alSearchCriteria.get(1) != null)
            else{
                cStmtObject.setString(2,null);
            }//end of else
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
            cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(5);
            if(rs != null){
                while(rs.next()){
                    sumInsuredVO = new SumInsuredVO();

                    if(rs.getString("MEM_INSURED_SEQ_ID") != null){
                        sumInsuredVO.setMemInsuredSeqID(new Long(rs.getLong("MEM_INSURED_SEQ_ID")));
                    }//end of if(rs.getString("MEM_INSURED_SEQ_ID") != null)

                    if(rs.getString("MEMBER_SEQ_ID") != null){
                        sumInsuredVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
                    }//end of if(rs.getString("MEMBER_SEQ_ID") != null)

                    if(rs.getString("POLICY_DATE") != null){
                        sumInsuredVO.setEffectDate(new java.util.Date(rs.getTimestamp("POLICY_DATE").getTime()));
                    }//end of if(rs.getString("POLICY_DATE") != null)

                    if(rs.getString("MEM_SUM_INSURED") != null){
                        sumInsuredVO.setMemSumInsured(new BigDecimal(rs.getString("MEM_SUM_INSURED")));
                    }//end of if(rs.getString("MEM_SUM_INSURED") != null)
                    else{
                        sumInsuredVO.setMemSumInsured(new BigDecimal(0.0));
                    }//end of else

                    if(rs.getString("MEM_BONUS_PERCENT") != null){
                        sumInsuredVO.setBonus(new Double(rs.getString("MEM_BONUS_PERCENT")));
                    }//end of if(rs.getString("MEM_BONUS_PERCENT") != null)
                    else{
                        sumInsuredVO.setBonus(new Double(0.0));
                    }//end of else

                    if(rs.getString("MEM_BONUS_AMOUNT") != null){
                        sumInsuredVO.setBonusAmt(new BigDecimal(rs.getString("MEM_BONUS_AMOUNT")));
                    }//end of if(rs.getString("MEM_BONUS_AMOUNT") != null)
                    else{
                        sumInsuredVO.setBonusAmt(new BigDecimal(0.0));
                    }//end of else

                    if(rs.getString("MEM_TOT_BONUS") != null){
                        sumInsuredVO.setCumulativeBonusAmt(new BigDecimal(rs.getString("MEM_TOT_BONUS")));
                    }//end of if(rs.getString("MEM_TOT_BONUS") != null)
                    else{
                        sumInsuredVO.setCumulativeBonusAmt(new BigDecimal(0.0));
                    }//end of else

                    if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
                        sumInsuredVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

                    alResultList.add(sumInsuredVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getBonusList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getBonusList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getBonusList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getBonusList(ArrayList alSearchCriteria)

    /**
     * This method saves the Bonus details
     * @param sumInsuredVO the object which contains the details of the Bonus
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return lngMemInsuredSeqID long Object, which contains the MEM_INSURED_SEQ_ID
     * @exception throws TTKException
     */
    public long saveBonus(SumInsuredVO sumInsuredVO,String strPolicyMode,String strPolicyType) throws TTKException{
        //int iResult = 0;
    	long lngMemInsuredSeqID = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveBonus);
            if(sumInsuredVO.getMemInsuredSeqID() != null){
                cStmtObject.setLong(1,sumInsuredVO.getMemInsuredSeqID());
            }//end of if(sumInsuredVO.getMemInsuredSeqID() != null)
            else{
                cStmtObject.setLong(1,0);
            }//end of else

            if(sumInsuredVO.getPolicyGroupSeqID() != null){
                cStmtObject.setLong(2,sumInsuredVO.getPolicyGroupSeqID());
            }//end of if(sumInsuredVO.getPolicyGroupSeqID() != null)
            else{
                cStmtObject.setString(2,null);
            }//end of else

            if(sumInsuredVO.getSeqID() != null){
                cStmtObject.setLong(3,sumInsuredVO.getSeqID()); //In Enrollment flow - Policy Seq ID and in Endorsement flow-Endorsement Seq ID
            }//end of if(sumInsuredVO.getSeqID() != null)
            else{
                cStmtObject.setString(3,null);
            }//end of else

            if(sumInsuredVO.getMemberSeqID() != null){
                cStmtObject.setLong(4,sumInsuredVO.getMemberSeqID());
            }//end of if(sumInsuredVO.getMemberSeqID() != null)
            else{
                cStmtObject.setString(4,null);
            }//end of else

            cStmtObject.setString(5,sumInsuredVO.getSumInsured());

            if(sumInsuredVO.getBonus() != null){
                cStmtObject.setDouble(6,sumInsuredVO.getBonus());
            }//end of if(sumInsuredVO.getBonus() != null)
            else{
                cStmtObject.setString(6,null);
            }//end of else

            if(sumInsuredVO.getBonusAmt() != null){
                cStmtObject.setBigDecimal(7,sumInsuredVO.getBonusAmt());
            }//end of if(sumInsuredVO.getBonusAmt() != null)
            else{
                cStmtObject.setString(7,null);
            }//end of else

            if(sumInsuredVO.getEffectiveDate() != null){
                cStmtObject.setTimestamp(8,new Timestamp(sumInsuredVO.getEffectiveDate().getTime()));
            }//end of if(sumInsuredVO.getEffectiveDate() != null)
            else{
                cStmtObject.setTimestamp(8,null);
            }//end of else
            cStmtObject.setString(9,strPolicyMode);
            cStmtObject.setString(10,strPolicyType);
            cStmtObject.setString(11,sumInsuredVO.getProductPlanSeqID());
            cStmtObject.setLong(12,sumInsuredVO.getUpdatedBy());
            cStmtObject.registerOutParameter(13, Types.INTEGER);//ROW_PROCESSED
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.execute();
            lngMemInsuredSeqID = cStmtObject.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl saveBonus()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl saveBonus()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngMemInsuredSeqID;
    }//end of saveBonus(SumInsuredVO sumInsuredVO,String strPolicyMode,String strPolicyType)

    /**
     * This method returns the SumInsuredVO, which contains the Bonus details which are populated from the database
     * @param lngMemInsuredSeqID Member Insured sequence ID for which the Bonus details has to be fetched
     * @param lngProductSeqID Product Seq ID for Fetching Plans for corresponding Product Seq ID
     * @return SumInsuredVO object's which contains Bonus Details
     * @exception throws TTKException
     */
    public SumInsuredVO getBonus(long lngMemInsuredSeqID,ArrayList<Object> alPlanListObj) throws TTKException{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
        SumInsuredVO sumInsuredVO = null;
        ArrayList alPlanList = new ArrayList();
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBonus);
            cStmtObject.setLong(1,lngMemInsuredSeqID);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
            	while(rs.next()){
            		sumInsuredVO = new SumInsuredVO();
            		if(rs.getString("MEM_INSURED_SEQ_ID") != null){
            			sumInsuredVO.setMemInsuredSeqID(new Long(rs.getLong("MEM_INSURED_SEQ_ID")));
            		}//end of if(rs.getString("MEM_INSURED_SEQ_ID") != null)
            		if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
            			sumInsuredVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
            		}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)
            		if(rs.getString("MEMBER_SEQ_ID") != null){
            			sumInsuredVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
            		}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

            		if(rs.getString("MEM_BONUS_PERCENT") != null){
            			sumInsuredVO.setBonus(new Double(rs.getString("MEM_BONUS_PERCENT")));
            		}//end of if(rs.getString("MEM_BONUS_PERCENT") != null)
            		if(rs.getString("MEM_BONUS_AMOUNT") != null){
            			sumInsuredVO.setBonusAmt(new BigDecimal(rs.getString("MEM_BONUS_AMOUNT")));
            		}//end of if(rs.getString("MEM_BONUS_AMOUNT") != null)
            		if(rs.getString("POLICY_DATE") != null){
            			sumInsuredVO.setEffectiveDate(new java.util.Date(rs.getTimestamp("POLICY_DATE").getTime()));
            		}//end of if(rs.getString("POLICY_DATE") != null)
            		sumInsuredVO.setType(TTKCommon.checkNull(rs.getString("FLAG")));
            		sumInsuredVO.setProductPlanSeqID(TTKCommon.checkNull(rs.getString("PROD_PLAN_SEQ_ID")));
            		sumInsuredVO.setSumInsured(TTKCommon.checkNull(rs.getString("MEM_SUM_INSURED")));
            		sumInsuredVO.setPlanTypeValue(TTKCommon.checkNull(rs.getString("PROD_PLAN_SEQ_ID")).concat("#").concat(TTKCommon.checkNull(rs.getString("MEM_SUM_INSURED"))));
            		alPlanList = getPlanList(alPlanListObj);
            		sumInsuredVO.setPlanList(alPlanList);
            	}//end of while(rs.next())
            }//end of if(rs != null)
            return sumInsuredVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getBonus()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getBonus()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getBonus()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getBonus(long lngMemInsuredSeqID,long lngProductSeqID)

    /**
     * This method returns the Arraylist of Cache object which contains Plan details for corresponding Product
     * @param lngProductSeqID long value which contains Product Seq ID
     * @return ArrayList of Cache object which contains Plan details for corresponding Product
     * @exception throws TTKException
     */
    public ArrayList getPlanList(ArrayList<Object> alPlanListObj) throws TTKException {
        Connection conn1 = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        CacheObject cacheObject = null;
        ArrayList<Object> alPlanList = new ArrayList<Object>();
        try{
            conn1 = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn1.prepareCall(strPlanList);
            cStmtObject.setLong(1,(Long)alPlanListObj.get(0));
            cStmtObject.setLong(2,(Long)alPlanListObj.get(1));
            cStmtObject.setLong(3,(Long)alPlanListObj.get(2));
            if(alPlanListObj.get(3) != null)
            {
            	cStmtObject.setLong(4,(Long)alPlanListObj.get(3));
            }else
            {
            	cStmtObject.setString(4,null);
            }
            cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(5);
            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("PLAN_LIST")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("PROD_PLAN_NAME")));
                    alPlanList.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alPlanList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getPlanList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getPlanList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn1 != null) conn1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getPlanList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn1 = null;
			}//end of finally
		}//end of finally
    }//end of getPlanList(long lngProductSeqID)

    /**
     * This method deletes the Premium information from the database
     * @param aldeletePremium which contains the details of the premium which has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deletePremium(ArrayList aldeletePremium) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeletePremium);
            cStmtObject.setString(1, (String)aldeletePremium.get(0));//FLAG
			cStmtObject.setString(2, (String)aldeletePremium.get(1));//CONCATENATED STRING OF MEM_INSURED_SEQ_IDS
			cStmtObject.setLong(3,(Long)aldeletePremium.get(2));// policy_seq_id in Enrollment Flow, Endorsement Seq id in Endorsement Flow.
			cStmtObject.setString(4, (String)aldeletePremium.get(3));//-- Mode can be 'ENM','END'
			cStmtObject.setString(5, (String)aldeletePremium.get(4));//-- 'IP',IG,CP,NC
			cStmtObject.setLong(6,(Long)aldeletePremium.get(5));//ADDED_BY
			cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl deletePremium()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl deletePremium()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of deletePremium(ArrayList aldeletePremium)

    /**
     * This method saves the Premium details
     * @param alPremium ArrayList which contains PremiumVO's
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @param strCheckedYN String object which contains Checkbox value for clearing the amount
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int savePremium(ArrayList alPremium,String strPolicyMode,String strPolicyType,String strCheckedYN) throws TTKException {
        int iResult = 0;
        StringBuffer sbfSQL = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        Statement stmt = null;
        PremiumInfoVO premiumVO = null;
        PremiumInfoVO premiumVO1 = null;
        Long lngPolicyGrpSeqID = null;
        BigDecimal bdFloaterSumInsured = null;
        BigDecimal bdFloaterPremium = null;
        BigDecimal bdTotalFlySumInsured = null;
        BigDecimal bdTotalFlyPremium = null;
        Long lngSeqID = null;
        Long lngAddedBy = null;
      //added asper KOC 1284 Change Reequest
        String selectregion="";
        try{
            conn = ResourceManager.getConnection();
            conn.setAutoCommit(false);
            stmt = (java.sql.Statement)conn.createStatement();
            if(alPremium.size() >0){

            	premiumVO1 = (PremiumInfoVO)alPremium.get(0);
    			lngPolicyGrpSeqID = premiumVO1.getPolicyGroupSeqID();
    			lngSeqID = premiumVO1.getSeqID();
                lngAddedBy = premiumVO1.getUpdatedBy();

            	if(strCheckedYN.equals("Y")){
                	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClearAmount);
                    if(lngPolicyGrpSeqID !=0){
                        cStmtObject.setLong(1,lngPolicyGrpSeqID);
                    }//end of if(lngPolicyGrpSeqID !=0)
                    else{
                        cStmtObject.setString(1,null);
                    }//end of else

                    if(strPolicyMode.equalsIgnoreCase("ENM")){

                    	if(lngSeqID != null){
                    		cStmtObject.setLong(2,lngSeqID);
                    	}//end of if(lngSeqID != null)
                    	else{
                    		cStmtObject.setString(2,null);
                    	}//end of else

                    	cStmtObject.setString(3,null);
                    }//end of if(strPolicyMode.equalsIgnoreCase("ENM"))

                    if(strPolicyMode.equalsIgnoreCase("END")){

                    	cStmtObject.setString(2,null);

                    	if(lngSeqID != null){
                    		cStmtObject.setLong(3,lngSeqID);
                    	}//end of if(lngSeqID != null)
                    	else{
                    		cStmtObject.setString(3,null);
                    	}//end of else
                    }//end of if(strPolicyMode.equalsIgnoreCase("ENM"))

                    cStmtObject.setString(4,strPolicyMode);
                    cStmtObject.setString(5,strPolicyType);
                    cStmtObject.setLong(6,lngAddedBy);
                    cStmtObject.registerOutParameter(7,Types.INTEGER);
                    cStmtObject.execute();
                    iResult = cStmtObject.getInt(7);
                    conn.commit();
                }//end of if(strCheckedYN.equals("Y"))

            	else {
            		for(int i=0;i<alPremium.size();i++){
            			sbfSQL = new StringBuffer();
            			premiumVO = (PremiumInfoVO)alPremium.get(i);

            			//Member Premium Related Log
            			bdTotalFlySumInsured = premiumVO1.getTotalFlySumInsured();
            			bdTotalFlyPremium = premiumVO1.getTotalFlyPremium();
            			bdFloaterSumInsured = premiumVO1.getFloaterSumInsured();
            			bdFloaterPremium = premiumVO1.getFloaterPremium();
            			selectregion=premiumVO1.getSelectregion();//added as per KOC 1284 Change request

            			//Group Premium Related Log
            			sbfSQL = sbfSQL.append("'"+premiumVO.getMemberSeqID()+"',");
            			sbfSQL = sbfSQL.append("'"+premiumVO.getMemberPolicyTypeID()+"',");

            			if(premiumVO.getTotalSumInsured() == null){
            				sbfSQL = sbfSQL.append(""+null+",");
            			}//end of if(premiumVO.getTotalSumInsured() == null)
            			else{
            				sbfSQL = sbfSQL.append("'"+premiumVO.getTotalSumInsured()+"',");
            			}//end of else

            			if(premiumVO.getTotalPremium()== null){
            				sbfSQL = sbfSQL.append(""+null+",");
            			}//end of if(premiumVO.getTotalPremium()== null)
            			else{
            				sbfSQL = sbfSQL.append("'"+premiumVO.getTotalPremium()+"',");
            			}//end of else

            			if(premiumVO.getCumulativeBonusAmt()== null){
            				sbfSQL = sbfSQL.append(""+null+",");
            			}//end of if(premiumVO.getCumulativeBonusAmt()== null)
            			else{
            				sbfSQL = sbfSQL.append("'"+premiumVO.getCumulativeBonusAmt()+"',");
            			}//end of else
            			sbfSQL = sbfSQL.append("'"+premiumVO.getSeqID()+"',");//PolicySeqID/EndorsementSeqID
            			sbfSQL = sbfSQL.append("'"+strPolicyMode+"',");
            			sbfSQL = sbfSQL.append("'"+strPolicyType+"',");
            			sbfSQL = sbfSQL.append("'"+premiumVO.getUpdatedBy()+"')}");
            			stmt.addBatch(strSavePremium+sbfSQL.toString());
            		}//end of for

            		stmt.executeBatch();
            		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveGroupPremium);
            		if(lngPolicyGrpSeqID !=0){
            			cStmtObject.setLong(1,lngPolicyGrpSeqID);
            		}//end of if(lngPolicyGrpSeqID !=0)
            		else{
            			cStmtObject.setString(1,null);
            		}//end of else

            		if(bdTotalFlySumInsured != null){
            			cStmtObject.setBigDecimal(2,bdTotalFlySumInsured);
            		}//end of if(bdFloaterSumInsured != null)
            		else{
            			cStmtObject.setString(2,null);
            		}//end of else

            		if(bdTotalFlyPremium != null){
            			cStmtObject.setBigDecimal(3,bdTotalFlyPremium);
            		}//end of if(bdTotalFlyPremium != null)
            		else{
            			cStmtObject.setString(3,null);
            		}//end of else

            		if(bdFloaterSumInsured != null){
            			cStmtObject.setBigDecimal(4,bdFloaterSumInsured);
            		}//end of if(bdFloaterSumInsured != null)
            		else{
            			cStmtObject.setString(4,null);
            		}//end of else

            		if(bdFloaterPremium != null){
            			cStmtObject.setBigDecimal(5,bdFloaterPremium);
            		}//end of if(bdFloaterPremium != null)
            		else{
            			cStmtObject.setString(5,null);
            		}//end of else

            		//added as per kOC 1284 Change request
            		if(bdFloaterPremium != null){
            			cStmtObject.setString(6,selectregion);
            		}//end of if(bdFloaterPremium != null)
            		else{
            			cStmtObject.setString(6,null);
            		}//end of else
        			
        			//added as per kOC 1284 Change Request
        		
            		if(lngSeqID != null){
            			cStmtObject.setLong(7,lngSeqID);
            		}//end of if(lngSeqID != null)
            		else{
            			cStmtObject.setString(7,null);
            		}//end of else
            		cStmtObject.setString(8,strPolicyMode);
            		cStmtObject.setString(9,strPolicyType);
            		cStmtObject.setLong(10,lngAddedBy);
            		cStmtObject.registerOutParameter(11,Types.INTEGER);
            		cStmtObject.execute();
            		iResult = cStmtObject.getInt(11);
            		conn.commit();
            	}//end of else
         	}//end of if(alPremium.size() >0)
        }//end of try
        catch (SQLException sqlExp)
        {
            try {
                conn.rollback();
                throw new TTKException(sqlExp, "member");
            } //end of try
            catch (SQLException sExp) {
                throw new TTKException(sExp, "member");
            }//end of catch (SQLException sExp)
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            try {
                conn.rollback();
                throw new TTKException(exp, "member");
            } //end of try
            catch (SQLException sqlExp) {
                throw new TTKException(sqlExp, "member");
            }//end of catch (SQLException sqlExp)
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
					log.error("Error while closing the Statement in MemberDAOImpl savePremium()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if Statement is not closed, control reaches here. Try closing the Callablestatement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl savePremium()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl savePremium()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				stmt = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return iResult;
    }//end of savePremium(ArrayList alPremium,String strPolicyMode,String strPolicyType)

    /**
     * This method approves the card printing information
     * @param alCardPrint ArrayList which contains Seq ID either PolicyGroupSeqID/PolicySeqID and Policy Type as IP/IG/CP/NC
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int approveCardPrinting(ArrayList alCardPrint) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strApproveCardPrinting);
            cStmtObject.setString(1,(String)alCardPrint.get(0));
            cStmtObject.setLong(2,(Long)alCardPrint.get(1));//For IP&IG - PolicyGroupSeqID and for CP&NC - PolicySeqID and for Icon in CP&NC-PolicyGroupSeqID and for Members CardPrinting icon - MemberSeqID
            cStmtObject.setString(3,(String)alCardPrint.get(2));//Flag POLICY/GROUP/MEMBER
            cStmtObject.setLong(4,(Long)alCardPrint.get(3));
            cStmtObject.registerOutParameter(5,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(5);//ROW_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl approveCardPrinting()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl approveCardPrinting()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of approveCardPrinting(ArrayList alCardPrint)

    /**
     * This method saves the Domiciliary Treatment Limit details
     * @param alDomiciliaryLimit ArrayList which contains DomiciliaryVO's
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveDomiciliary(ArrayList alDomiciliaryLimit,String strPolicyMode,String strPolicyType) throws TTKException {
        int iResult = 1;
        StringBuffer sbfSQL = null;
        Connection conn = null;
        Statement stmt = null;
        CallableStatement cStmtObject=null;
        DomiciliaryVO domiciliaryVO = null;
        DomiciliaryVO domiciliaryVO1 = null;
        try{
            conn = ResourceManager.getConnection();
            conn.setAutoCommit(false);
            stmt = (java.sql.Statement)conn.createStatement();
            //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMemDomiciliary);
            if(alDomiciliaryLimit.size() >0){
            	domiciliaryVO1 = (DomiciliaryVO)alDomiciliaryLimit.get(0);

            	/*if(domiciliaryVO1.getDomiciliaryTypeID().equalsIgnoreCase("PFL")){
            		if(domiciliaryVO1.getMemberSeqID() != null){
            			cStmtObject.setLong(1,domiciliaryVO1.getMemberSeqID());
            		}//end of if(domiciliaryVO1.getMemberSeqID() != null)
            		else{
            			cStmtObject.setString(1,null);
            		}//end of else

            		cStmtObject.setString(2,domiciliaryVO1.getDomiciliaryTypeID());
            		cStmtObject.setString(3,null);

            		cStmtObject.setString(4,null);

            		if(domiciliaryVO1.getFamilyLimit() != null){
        				cStmtObject.setBigDecimal(4,domiciliaryVO1.getFamilyLimit());
        			}//end of if(domiciliaryVO1.getFamilyLimit() != null)
        			else{
        				cStmtObject.setString(4,null);
        			}//end of else

            		cStmtObject.setString(5,strPolicyMode);
            		cStmtObject.setString(6,strPolicyType);
            		cStmtObject.setLong(7,domiciliaryVO1.getUpdatedBy());
            		cStmtObject.execute();

            	}//end of if(domiciliaryVO.getDomiciliaryTypeID().equalsIgnoreCase(""))
*/            	//else{
            		for(int i=0;i<alDomiciliaryLimit.size();i++){
                        sbfSQL = new StringBuffer();
                        domiciliaryVO = (DomiciliaryVO)alDomiciliaryLimit.get(i);
                        //Member Premium Related Log

                        if(domiciliaryVO.getMemberSeqID() != null){
                        	sbfSQL = sbfSQL.append("'"+domiciliaryVO.getMemberSeqID()+"',");
                        }//end of if(domiciliaryVO.getMemberSeqID() != null)
                        else{
                        	sbfSQL = sbfSQL.append(""+null+",");
                        }//end of else

                        sbfSQL = sbfSQL.append("'"+domiciliaryVO.getDomiciliaryTypeID()+"',");

                        if(domiciliaryVO.getDomiciliaryTypeID().equalsIgnoreCase("PNF")){
                        	if(domiciliaryVO.getLimit()== null){
                        		sbfSQL = sbfSQL.append(""+null+",");
                        	}//end of if(domiciliaryVO.getLimit()== null)
                        	else{
                        		sbfSQL = sbfSQL.append("'"+domiciliaryVO.getLimit()+"',");
                        	}//end of else
                        }//end of if(domiciliaryVO.getDomiciliaryTypeID().equalsIgnoreCase("PNF"))
                        else{
                        	sbfSQL = sbfSQL.append(""+null+",");
                        }//end of else

                        if(domiciliaryVO.getHospAmt()== null){
                            sbfSQL = sbfSQL.append(""+null+",");
                        }//end of if(domiciliaryVO.getHospAmt()== null)
                        else{
                            sbfSQL = sbfSQL.append("'"+domiciliaryVO.getHospAmt()+"',");
                        }//end of else

                        if(domiciliaryVO.getDomiciliaryTypeID().equalsIgnoreCase("PNF")){

                        	if(domiciliaryVO1.getFamilyLimit() == null){
                        		sbfSQL = sbfSQL.append(""+0+",");
                        	}//end of if(domiciliaryVO.getFamilyLimit() == null)
                        	else{
                        		sbfSQL = sbfSQL.append("'"+domiciliaryVO1.getFamilyLimit()+"',");
                        	}//end of else
                        }//end of if(domiciliaryVO.getDomiciliaryTypeID().equalsIgnoreCase("PNF"))
                        else{
                        	if(domiciliaryVO1.getFamilyLimit() != null){
                        		sbfSQL = sbfSQL.append("'"+domiciliaryVO1.getFamilyLimit()+"',");
                			}//end of if(domiciliaryVO1.getFamilyLimit() != null)
                			else{
                				sbfSQL = sbfSQL.append(""+null+",");
                			}//end of else
                        }//end of else

                        sbfSQL = sbfSQL.append("'"+strPolicyMode+"',");
                        sbfSQL = sbfSQL.append("'"+strPolicyType+"',");
                        sbfSQL = sbfSQL.append("'"+domiciliaryVO.getUpdatedBy()+"')}");
                        stmt.addBatch(strSaveDomiciliary+sbfSQL.toString());
                    }//end of for
            		stmt.executeBatch();
            	//}//end of else
            }//end of if(alDomiciliaryLimit.size() >0)
            conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
            try {
                conn.rollback();
                throw new TTKException(sqlExp, "member");
            } //end of try
            catch (SQLException sExp) {
                throw new TTKException(sExp, "member");
            }//end of catch (SQLException sExp)
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            try {
                conn.rollback();
                throw new TTKException(exp, "member");
            } //end of try
            catch (SQLException sqlExp) {
                throw new TTKException(sqlExp, "member");
            }//end of catch (SQLException sqlExp)
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
        			log.error("Error while closing the Statement in MemberDAOImpl saveDomiciliary()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the callable statement now.
        		{
        			try
            		{
            			if (cStmtObject != null) cStmtObject.close();
            		}//end of try
            		catch (SQLException sqlExp)
            		{
            			log.error("Error while closing the Callable Statement in MemberDAOImpl saveDomiciliary()",sqlExp);
            			throw new TTKException(sqlExp, "member");
            		}//end of catch (SQLException sqlExp)
            		finally{
            			try
            			{
            				if(conn != null) conn.close();
            			}//end of try
            			catch (SQLException sqlExp)
            			{
            				log.error("Error while closing the Connection in MemberDAOImpl saveDomiciliary()",sqlExp);
            				throw new TTKException(sqlExp, "member");
            			}//end of catch (SQLException sqlExp)
            		}//end of finally
        		}//end of finally
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		stmt = null;
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of saveDomiciliary(ArrayList alDomiciliaryLimit,String strPolicyMode,String strPolicyType)

    /**
     * This method returns the ArrayList of DomiciliaryVO's, which contains the Domiciliary Limit details which are populated from the database
     * @param alSearchCriteria ArrayList for which the Domiciliary Limit details has to be fetched
     * @return ArrayList of DomiciliaryVO object's which contains Domiciliary Limit Details
     * @exception throws TTKException
     */
    public ArrayList<Object> getDomiciliaryList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        DomiciliaryVO domiciliaryVO = null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetDomiciliaryList);
            cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if(rs != null){
                while(rs.next()){
                    domiciliaryVO = new DomiciliaryVO();
                    if(rs.getString("MEMBER_SEQ_ID") != null){
                        domiciliaryVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
                    }//end of if(rs.getString("MEMBER_SEQ_ID") != null)
                    domiciliaryVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                    domiciliaryVO.setDomiciliaryTypeID(TTKCommon.checkNull(rs.getString("DOMICILARY_GENERAL_TYPE_ID")));
                    domiciliaryVO.setDomiciliaryTypeDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    domiciliaryVO.setDomiciliaryLimit(TTKCommon.checkNull(rs.getString("DOMICILARY_LIMIT")));
                    domiciliaryVO.setOverallfamilyLimit(TTKCommon.checkNull(rs.getString("FAMILY_LIMIT")));
                    domiciliaryVO.setDomHospAmt(TTKCommon.checkNull(rs.getString("DOMICILARY_HOSP_LIMIT")));
                    alResultList.add(domiciliaryVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList<Object>)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getDomiciliaryList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getDomiciliaryList()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getDomiciliaryList()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getDomiciliaryList(ArrayList alSearchCriteria)

    /**
     * This method clears the Enrollment Rules
     * @param lngPolicySeqID long for clearing enrollment rules
     * @param strPolicyMode String object which contains Mode Enrollment/Endorsement
     * @param strPolicyType String object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int clearEnrollmentRules(long lngPolicySeqID,String strPolicyMode,String strPolicyType) throws TTKException{
    	int iResult=0;
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClearEnrollmentRules);
			cStmtObject.setLong(1,lngPolicySeqID);
			cStmtObject.setString(2,strPolicyMode);// ENM/END
			cStmtObject.setString(3,strPolicyType);// IP/IG/CP/NC
			cStmtObject.registerOutParameter(4,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "member");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl clearEnrollmentRules()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl clearEnrollmentRules()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
    }//end of clearEnrollmentRules(long lngPolicySeqID,String strPolicyMode,String strPolicyType)

    /**
	 * This method returns the PasswordVO which contains the details of Employee Password Info
	 * @param lngPolicyGrpSeqID the Policy Group sequence id
	 * @return PasswordVO object which contains the details of Employee Password Info
	 * @Exception throws TTKException
	 */
    public PasswordVO getEmployeePasswordInfo(long lngPolicyGrpSeqID) throws TTKException {
    	Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        PasswordVO passwordVO = null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectEmpPasswordInfo);
            cStmtObject.setLong(1,lngPolicyGrpSeqID);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
            	while(rs.next()){
            		passwordVO = new PasswordVO();
            		passwordVO.setEmpName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
            		passwordVO.setUserID(TTKCommon.checkNull(rs.getString("EMPLOYEE_USER_ID")));
            		passwordVO.setCurrentPwd(TTKCommon.checkNull(rs.getString("PASSWORD")));
            		passwordVO.setEmpNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
            		passwordVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
            		passwordVO.setPrimaryEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
            	}//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getEmployeePasswordInfo()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getEmployeePasswordInfo()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getEmployeePasswordInfo()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return passwordVO;
    }//end of getEmployeePasswordInfo(long lngPolicyGrpSeqID)

    /**
     * This method resets the Employee Password
     * @param lngPolicyGrpSeqID the policy group sequence id
     * @param lngUserSeqID logged-in user seq id
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID) throws TTKException {
    	int iResult=0;
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strResetEmpPassword);

			cStmtObject.setLong(1,lngPolicyGrpSeqID);
			cStmtObject.setLong(2,lngUserSeqID);
			cStmtObject.registerOutParameter(3,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "member");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "member");
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
        			log.error("Error while closing the Statement in MemberDAOImpl resetEmployeePassword()",sqlExp);
        			throw new TTKException(sqlExp, "member");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl resetEmployeePassword()",sqlExp);
        				throw new TTKException(sqlExp, "member");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "member");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
    }//end of resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID)
    
    //added fro koc 1278 & KOC 1270 for hospital cash benefit
    /**
     * This method saves the MemberPlan
     * @param memberVO the object which contains the details of the MemberPlan
     * @param lngPolicyGrpSeqID long object which contains Policy Type as Individual/Individual as Group/Corporate/NonCorporate
     * @return lngPolicyGrpSeqID long Object, which contains the PolicyGrpSeqID
     * @exception throws TTKException
     */

    public int saveMemberPlan(MemberVO memberVO,Long lngPolicyGrpSeqID)throws TTKException
    {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMemberPlan);       
            if(lngPolicyGrpSeqID != null){
                cStmtObject.setLong(1,lngPolicyGrpSeqID);
            }//end of if(memberDetailVO.getPolicyGroupSeqID()!=null)
            else{
                cStmtObject.setString(1,null);
            }//end of else
            cStmtObject.setString(2, memberVO.getHospCashBenefitsYN());
            cStmtObject.setString(3, memberVO.getConvCashBenefitsYN());
            //added for koc 1278
            cStmtObject.setString(4, memberVO.getPersonalWaitingPeriodYN());
            //added for koc 1278
			cStmtObject.setString(5, memberVO.getreductWaitingPeriodYN());
            cStmtObject.setString(6, memberVO.getSumInsuredBenefitYN());//added for policy restoration
            cStmtObject.registerOutParameter(7, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(7);//ROWS_PROCESSED
         }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "members");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "members");
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
        			log.error("Error while closing the Statement in MemberDAOImpl saveMember()",sqlExp);
        			throw new TTKException(sqlExp, "members");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in MemberDAOImpl saveMember()",sqlExp);
        				throw new TTKException(sqlExp, "members");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "members");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
   }//end of saveMember(MemberDetailVO memberDetailVO,String strPolicyMode,String strPolicType)
    
    /**
	 * This method returns the MemberVO which contains the details of Change Plan Info
	 * @param lngPolicyGrpSeqID the Policy Group sequence id
	 * @return MemberVO object which contains the details of Change Plan Info
	 * @Exception throws TTKException
	 */
    public MemberVO getCashBenefitInfo(ArrayList alSearchCriteria) throws TTKException {
    	Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        MemberVO memberVO =new MemberVO();
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectCashBenefit);
            cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
            	while(rs.next()){
            		memberVO = new MemberVO();
            		memberVO.setHospCashBenefitsYN(TTKCommon.checkNull(rs.getString("hosp_cash_benefit_yn")));
            		memberVO.setConvCashBenefitsYN(TTKCommon.checkNull(rs.getString("conv_cash_benefit_yn")));
            		//added for koc 1278
            		memberVO.setPersonalWaitingPeriodYN(TTKCommon.checkNull(rs.getString("PERS_WAT_PRD_YN")));
            		//added for koc 1278
            	}//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "member");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "member");
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
					log.error("Error while closing the Resultset in MemberDAOImpl getCashBenefitInfo()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getCashBenefitInfo()",sqlExp);
						throw new TTKException(sqlExp, "member");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MemberDAOImpl getCashBenefitInfo()",sqlExp);
							throw new TTKException(sqlExp, "member");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "member");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return memberVO;
    }//end of getEmployeePasswordInfo(long lngPolicyGrpSeqID)
    //added for koc 1278 & KOC 1270 for hospital cash benefit
}//end of MemberDAOImpl
