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

package com.ttk.dao.impl.enrollment;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dao.impl.common.TestClient;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.HaadWebServiceVo;
import com.ttk.dto.enrollment.DHPOMemberUploadVO;
import com.ttk.dto.enrollment.DohLogfileVO;
import com.ttk.dto.enrollment.DomiciliaryVO;
import com.ttk.dto.enrollment.ICDCodeVO;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PEDVO;
import com.ttk.dto.enrollment.PremiumInfoVO;
import com.ttk.dto.enrollment.ReciverMailIdVO;
import com.ttk.dto.enrollment.ReciverMailList;
import com.ttk.dto.enrollment.SumInsuredVO;
import com.ttk.dto.usermanagement.PasswordVO;

public class MemberDAOImpl implements BaseDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(MemberDAOImpl.class);

    TestClient testClient = null;

    //private static final String strMemberList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_MEMBER_LIST(?,?,?,?)}";
    private static final String strMemberList = "{CALL POLICY_ENROLLMENT_PKG_SELECT_MEMBER_LIST(?,?,?,?)}";
    //private static final String strEnrollmentList ="{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_ENROLLMENT_LIST(?,?,?,?,?,?,?,?)}";
    private static final String strEnrollmentList ="{CALL POLICY_ENROLLMENT_PKG_SELECT_ENROLLMENT_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    //private static final String strSaveMember = "{CALL POLICY_ENROLLMENT_PKG.SAVE_POLICY_MEMBER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveMember = "{CALL POLICY_ENROLLMENT_PKG_SAVE_POLICY_MEMBER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; // added by vishwanath
    private static final String strSaveMember1 = "{CALL policy_enrollment_pkg_save_policy_member1(?,?,?,?,?,?,?,?,?,?)}";
    
    private static final String strGetRelationshipCode = "SELECT A.RELSHIP_TYPE_ID || '#' || A.ASSOC_GENDER_REL RELSHIP,A.RELSHIP_DESCRIPTION FROM APP.TPA_RELATIONSHIP_CODE A ORDER BY A.RELSHIP_DESCRIPTION";
    //Added for IBM Endorsement Age CR
    private static final String strGetMemberDetail = "select gr.date_of_joining,gr.date_of_marriage,pol.effective_from_date,gr.tpa_enrollment_number, COALESCE((DATE_PART('year', a.date_of_exit::date) - DATE_PART('year',  a.date_of_inception::date))+1,0)as mem_active_days, a.date_of_exit,a.date_of_inception, a.policy_group_seq_id, case when date(pol.effective_from_date) >='01-JAN-2018' then 'Y' else 'N' end as display_vat_yn from app.tpa_enr_policy_group gr left join app.tpa_enr_policy pol on (pol.policy_seq_id = gr.policy_seq_id) left join app.tpa_enr_policy_member a on (a.policy_group_seq_id = gr.policy_group_seq_id) where gr.policy_group_seq_id =? "; 
    //Ended
    private static final String strSelectMember = "{CALL POLICY_ENROLLMENT_PKG_SELECT_MEMBER(?,?,?,?)}";
    private static final String strDeleteMember = "{CALL POLICY_ENROLLMENT_PKG_DELETE_MEMBER(?,?,?,?,?,?,?,?,?)}";
    private static final String strCancelMember = "{CALL POLICY_ENROLLMENT_PKG_CANCEL_MEMBER(?,?,?,?,?,?,?,?,?)}";
    /*private static final String strPEDList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_MEM_PED_LIST(?,?)}";
    private static final String strPreauthPEDList = "{CALL PRE_AUTH_SQL_PKG.SELECT_CLAIMANT_PED_LIST(?,?,?,?,?)}";
    private static final String strGetPED = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_MEM_PED(?,?)}";
    private static final String strGetPreauthPED = "{CALL PRE_AUTH_SQL_PKG.SELECT_CLAIMANT_PED(?,?,?)}";*/
    private static final String strPEDList = "{CALL POLICY_ENROLLMENT_PKG_SELECT_MEM_PED_LIST(?,?)}";
    private static final String strPreauthPEDList = "{CALL PRE_AUTH_PKG.SELECT_CLAIMANT_PED_LIST(?,?,?,?,?)}";
    private static final String strGetPED = "{CALL POLICY_ENROLLMENT_PKG_SELECT_MEM_PED(?,?)}";
    private static final String strGetPreauthPED = "{CALL PRE_AUTH_PKG_SELECT_CLAIMANT_PED(?,?,?)}";
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
    private static final String strRenewMembersList = "{CALL POLICY_ENROLLMENT_PKG_SELECT_RENEW_MEMBERS_LIST(?,?,?,?)}";
    private static final String strRenewMember = "{CALL POLICY_ENROLLMENT_PKG_RENEW_MEMBER(?,?,?,?,?,?,?,?)}";
    //private static final String strSaveEnrollment = "{CALL POLICY_ENROLLMENT_PKG.SAVE_POLICY_GROUP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveEnrollment = "{CALL POLICY_ENROLLMENT_PKG_SAVE_POLICY_GROUP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//one extra parameter added by Praveen for emailId2-KOC-1216
    //private static final String strGetEnrollment = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_ENROLLMENT(?,?,?,?)}";
    private static final String strGetEnrollment = "{CALL POLICY_ENROLLMENT_PKG_SELECT_ENROLLMENT(?,?,?,?)}";
    private static final String strLocation = "{CALL POLICY_ENROLLMENT_PKG_SELECT_CORPORATE_LOCATION(?,?)}";
    /*private static final String strPremiumList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_PREMIUM_LIST(?,?,?,?)}";
    private static final String strBonusList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_BONUS_LIST(?,?,?,?,?)}";
    private static final String strGetBonus = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_BONUS(?,?)}";
    private static final String strPlanList = "{CALL POLICY_ENROLLMENT_SQL_PKG.GET_PLAN_LIST(?,?)}";*/
    

    private static final String strPremiumList = "{CALL POLICY_ENROLLMENT_PKG_SELECT_PREMIUM_LIST(?,?,?,?)}";
    private static final String strBonusList = "{CALL POLICY_ENROLLMENT_PKG_SELECT_BONUS_LIST(?,?,?,?,?)}";
    private static final String strGetBonus = "{CALL POLICY_ENROLLMENT_PKG_SELECT_BONUS(?,?)}";
    //private static final String strPlanList = "{CALL POLICY_ENROLLMENT_PKG.GET_PLAN_LIST(?,?)}";
    private static final String strPlanList = "{CALL POLICY_ENROLLMENT_PKG_GET_PLAN_LIST(?,?,?,?,?)}";
    private static final String strSaveBonus = "{CALL POLICY_ENROLLMENT_PKG_SAVE_BONUS(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strDeletePremium = "{CALL POLICY_ENROLLMENT_PKG_DELETE_GENERAL(?,?,?,?,?,?,?)}";
    private static final String strSavePremium = "{CALL POLICY_ENROLLMENT_PKG_SAVE_PREMIUM(?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strClearAmount = "{CALL POLICY_ENROLLMENT_PKG_CLEAR_AMOUNT(?,?,?,?,?,?,?)}";
 //   private static final String strSaveGroupPremium = "{CALL POLICY_ENROLLMENT_PKG.SAVE_GROUP_PREMIUM(?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveGroupPremium = "{CALL POLICY_ENROLLMENT_PKG_SAVE_GROUP_PREMIUM(?,?,?,?,?,?,?,?,?,?,?)}";//modified as per KOC 1284 Change request

    private static final String strApproveCardPrinting = "{CALL POLICY_ENROLLMENT_PKG_APPROVE_CARD_PRINT_ALL(?,?,?,?,?)}";
    private static final String strSaveDomiciliary = "{CALL POLICY_ENROLLMENT_PKG_SAVE_MEM_DOMICILARY(";
//    private static final String strSaveMemDomiciliary = "{CALL POLICY_ENROLLMENT_PKG.SAVE_MEM_DOMICILARY(?,?,?,?,?,?,?)}";
    //private static final String strGetDomiciliaryList = "{CALL POLICY_ENROLLMENT_SQL_PKG.SELECT_DOMICILARY_LIST(?,?,?,?)}";
    private static final String strGetDomiciliaryList = "{CALL POLICY_ENROLLMENT_PKG_SELECT_DOMICILARY_LIST(?,?,?,?)}";
    private static final String strGetDescriptionList = "SELECT ICD_CODE,PED_DESCRIPTION FROM TPA_PED_CODE WHERE ped_code_id=?";
    private static final String strClearEnrollmentRules = "{CALL POLICY_ENROLLMENT_PKG_CLEAR_ENROLLMENT_RULES(?,?,?,?)}";
    private static final String strSelectEmpPasswordInfo = "{CALL POLICY_ENROLLMENT_PKG_SELECT_PASSWORD(?,?)}";
    private static final String strResetEmpPassword = "{CALL POLICY_ENROLLMENT_PKG_RESET_PASSWORD(?,?,?)}";
    private static final String strRenewalPolicyStatus = "{CALL POLICY_ENROLLMENT_PKG_CHECK_REN_OR_FRS(?,?)}";
    //added for koc 1278 & KOC 1270 for hospital cash benefit
    private static final String strSaveMemberPlan = "{CALL POLICY_ENROLLMENT_PKG_SAVE_CASH_BENEFIT_EMPLOYEE(?,?,?,?,?,?,?,?)}";
    private static final String strSelectCashBenefit = "{CALL POLICY_ENROLLMENT_PKG_SELECT_CASH_BENEFIT(?,?)}";
    private static final String strgetMemberXML="{CALL POLICY_ENROLLMENT_PKG_DHPO_MEMBER_AUTO_UPLOAD_OLD(?,?,?,?)}";
    private static final String strUpdateDHPOsUplodedMembers="{CALL POLICY_ENROLLMENT_PKG_UPLOADED_MEMBER_INTO_DHPO(?,?)}";
    private static final String strDHPOLogDetails="{CALL policy_enrollment_pkg_mem_auto_uploaded_log(?,?,?,?,?,?,?)}";
    private static final String strSelectDHPOLogDetails="{CALL policy_enrollment_pkg_mem_auto_uploaded_log_select(?,?,?,?,?,?,?,?,?)}";
    private static final String strDHPOInsuranceCompanyDetail="select * from  app.tpa_ins_mem_auto_upload_ud";
    private static final String strDHPOInsuranceCompanyCredentials="select t.ins_user_name,t.ins_password from APP.TPA_INS_MEM_AUTO_UPLOAD_UD t where t.ins_comp_code_number=?";
    private static final String strgetMemberXMLDataForMailAttachment="{CALL policy_enrollment_pkg_taka_member_auto_upload(?,?,?,?,?)}";
    private static final String strSaveReciverMailID="{CALL policy_enrollment_pkg_save_ins_trg_mail_congfig(?,?,?,?,?,?,?,?,?,?,?)}"; 
    private static final String strGetReciverMailID="{CALL policy_enrollment_pkg_get_instrg_mail_congfig(?,?)}"; 
    private static final String strSearchCriteriaOnMailList="{CALL  policy_enrollment_pkg_select_instrg_mail_congfig(?,?,?,?,?,?,?,?,?,?)}";
    private static final String strDeleteReciverMailData="{CALL policy_enrollment_pkg_delete_instrg_mail_congfig(?,?)}";
    private static final String strupdateMailTrigger="{CALL policy_enrollment_pkg_update_mail_trigger(?,?,?,?)}";
    
    
    
  private static final String strAuditLogs = "{call POLICY_ENROLLMENT_PKG_select_audit_endos_group_list(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
  private static final String strAuditMemberLogs ="{call POLICY_ENROLLMENT_PKG_select_audit_endos_member_list(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
//  private static final String strNewMemberXML="{CALL Ram.test_multiple_mem_xml(?)}";  
  private static final String strnewUpdateMembers="{CALL POLICY_ENROLLMENT_PKG_UPDATE_DHPO_MEMBER_STATUS(?,?,?)}";
  private static final String strNewMemberXML="{CALL POLICY_ENROLLMENT_PKG_DHPO_MEMBER_AUTO_UPLOAD(?,?)}";  
    
    
    
  private static final String strgetMemberXMLDataForMailAttachmentNew ="{CALL policy_enrollment_pkg_taka_member_auto_upld_new(?,?,?,?,?,?)}";
  private static final String strgetOMANMemberData="{CALL INTX.OMAN_MEM_UPLOAD(?)}";
  private static final String strOmanLogDetails="{CALL policy_enrollment_pkg_mem_auto_uploaded_log(?,?,?)}";
  private static final String strOccupationCodeList="{CALL policy_enrollment_pkg_search_occupation(?,?,?)}";
    
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
    private static final int EMAIL_ID2 = 48;
    private static final int V_EMIRATE_ID= 49;//added for EmailID2 - KOC-1216
    private static final int V_PASSPORT_NUMBER=50;
    private static final int V_MARITAL_STATUS=51;
    private static final int V_NATIONALITY_ID=52;
    private static final int V_MEM_LAST_NAME=53;
    private static final int V_FAMILY_NAME=54;
    private static final int V_UID_NUMBER=55;
    private static final int V_RESIDENTIAL_LOC=56;
    private static final int V_WORK_LOC=57;
    private static final int V_VIP_YN=58;
    private static final int V_GLOBALNET_MEM_ID=59;
    // Capitation Policy
    //private static final int v_age_range = 60;
    private static final int v_min_age = 60;
    private static final int v_max_age = 61;
    private static final int v_medical_prem = 62;
    private static final int v_maternity_prem = 63;
    private static final int v_optical_prem = 64;
    private static final int v_dental_prem = 65;
    private static final int v_wellness_prem = 66;
    private static final int v_gross_prem = 67;
    private static final int v_ip_net_prem = 68;
    private static final int v_insurer_margin = 69;
    private static final int v_broker_margin = 70;
    private static final int v_tpa_margin = 71;
    private static final int v_reins_brk_margin = 72;
    private static final int v_other_margin = 73;
    private static final Integer v_net_prem = 74;
    private static final Integer v_actual_prem = 75;
//    private static final long policy_seq_id =  76;
    private static final int v_DHPO_Upload_Status = 77;
    private static final int member_birth_city = 78;
    
    private static final int v_ins_category_code = 79;
    private static final int MEMBER_ADDED_DATED =80;
    
    private static final int vat_percent = 81;
    private static final int vat_aed = 82;
    private static final int v_op_net_prem = 84;
    private static final int EDUCATION = 92;
    private static final int NATIONAL_ID_EXP_DATE = 93;
    private static final int FLAG = 94;
    private static final int PARENTAL_NATIONAL_CIVIL_ID = 95;
    
/*    private static final int pro_rata_ip_net_prem = 79;
    private static final int pro_rata_op_net_prem = 80;
    private static final int collected_premium = 83;
    private static final int pre_rata_ins_margin = 85;
    private static final int pre_rata_broker_margin = 86;
    private static final int pre_rata_broker_reIns_margin = 87;
    private static final int pre_rata_other_margin = 88;*/
    
    
    
    
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
            cStmtObject.registerOutParameter(4,Types.OTHER);
            cStmtObject.execute();
            conn.commit();
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
                    memberVO.setMemeligFlag(TTKCommon.checkNull(rs.getString("mem_elig_flag")));
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
                cStmtObject.setLong(1,0);
            }//end of else
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
       /*     if(!"".equals(TTKCommon.checkNull(alSearchCriteria.get(3)))){
            	cStmtObject.setString(4,(String)alSearchCriteria.get(3));
             }
            else{
            	cStmtObject.setSQLXML(4, null);
            }*/
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
            cStmtObject.setString(5,(String)alSearchCriteria.get(4));
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));
            cStmtObject.setString(8,(String)alSearchCriteria.get(7));
            cStmtObject.setString(9,(String)alSearchCriteria.get(8));

            if(alSearchCriteria.get(11)!=null){
                cStmtObject.setInt(10,new Integer((String)alSearchCriteria.get(11)));
            }//end of if(alSearchCriteria.get(9)!=null)
            else{
                cStmtObject.setInt(10,0);
            }//end of else

            if(alSearchCriteria.get(12)!=null){
                cStmtObject.setInt(11,new Integer((String)alSearchCriteria.get(12)));
            }//end of if(alSearchCriteria.get(10)!=null)
            else{
                cStmtObject.setInt(11,0);
            }//end of else
            
            
            cStmtObject.setString(12,(String)alSearchCriteria.get(9));
            cStmtObject.setString(13,(String)alSearchCriteria.get(10));
            
            
            cStmtObject.registerOutParameter(14,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(14);
            conn.commit();
            if (rs!=null)
            { 
                while (rs.next())
                {  
                    memberVO = new MemberVO();
                    memberVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID")!=null ? new Long(rs.getInt("POLICY_GROUP_SEQ_ID")):null);
                    memberVO.setName(TTKCommon.checkNull(rs.getString("ENROLLMENT")));
                //    memberVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    memberVO.setEnrollmentID(rs.getString("TPA_ENROLLMENT_NUMBER") != null ? new String(rs.getString("TPA_ENROLLMENT_NUMBER")) : null);
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
                    memberVO.setStrFirstName(TTKCommon.checkNull(rs.getString("insured_name")));
                    memberVO.setStrSecondName(TTKCommon.checkNull(rs.getString("insured_middle_name")));
                    memberVO.setStrFamilyName(TTKCommon.checkNull(rs.getString("insured_family_name")));
                    memberVO.setEventCompleteYN(TTKCommon.checkNull(rs.getString("EVENT_COMPLETE_YN")));
                    memberVO.setCapitaionFlag(TTKCommon.checkNull(rs.getString("CAPITATION_YN")));
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
            //System.out.println("MEMBER_SEQ_ID==="+memberDetailVO.getMemberSeqID());
            /*if(memberDetailVO.getMemberSeqID()!=null){
                cStmtObject.setLong(MEMBER_SEQ_ID,memberDetailVO.getMemberSeqID());
            }//end of if(memberDetailVO.getPolicyGroupSeqID()!=null)
            else{
                cStmtObject.setLong(MEMBER_SEQ_ID,0);
            }//end of else
*/            if(memberDetailVO.getPolicyGroupSeqID()!=null){
                cStmtObject.setLong(POLICY_GROUP_SEQ_ID,memberDetailVO.getPolicyGroupSeqID());
            }//end of if(memberDetailVO.getPolicyGroupSeqID()!=null)
            else{
                cStmtObject.setLong(POLICY_GROUP_SEQ_ID,0);
            }//end of else
            if(memberAddressVO.getAddrSeqId()!=null){
                cStmtObject.setLong(ENR_ADDRESS_SEQ_ID,memberAddressVO.getAddrSeqId());
            }//end of if(memberAddressVO.getAddrSeqId()!=null)
            else{
                cStmtObject.setLong(ENR_ADDRESS_SEQ_ID,0);
            }//end of else
            cStmtObject.setString(MEM_NAME,memberDetailVO.getName());
            cStmtObject.setString(GENDER_GENERAL_TYPE_ID,memberDetailVO.getGenderTypeID());
            cStmtObject.setString(RELSHIP_TYPE_ID,memberDetailVO.getRelationTypeID());
            cStmtObject.setTimestamp(MEM_DOB,memberDetailVO.getDateOfBirth()!=null ? new Timestamp(memberDetailVO.getDateOfBirth().getTime()):null);
            if(memberDetailVO.getAge()!= null){
                cStmtObject.setInt(MEM_AGE,memberDetailVO.getAge());
            }//end of if(memberDetailVO.getAge()!= null)
            else{
                cStmtObject.setInt(MEM_AGE,0);
            }//end of else
            cStmtObject.setString(EDUCATION, memberDetailVO.getEducation());
            cStmtObject.setTimestamp(NATIONAL_ID_EXP_DATE, memberDetailVO.getNationalidExpdate()!=null ? new Timestamp(memberDetailVO.getNationalidExpdate().getTime()):null);
            cStmtObject.setString(FLAG, memberDetailVO.getFlagYN());
            cStmtObject.setString(PARENTAL_NATIONAL_CIVIL_ID, memberDetailVO.getNationalCivilId()); 
            cStmtObject.setString(OCCUPATION_GENERAL_TYPE_ID,memberDetailVO.getOccupationTypeID());
            cStmtObject.setTimestamp(DATE_OF_INCEPTION,memberDetailVO.getInceptionDate()!=null ? new Timestamp(memberDetailVO.getInceptionDate().getTime()):null);
            cStmtObject.setTimestamp(DATE_OF_EXIT,memberDetailVO.getExitDate()!=null ? new Timestamp(memberDetailVO.getExitDate().getTime()):null);
            cStmtObject.setString(PHOTO_PRESENT_YN,memberDetailVO.getPhotoPresentYN());
            cStmtObject.setTimestamp(POLICY_EXPIRY_DATE,memberDetailVO.getPolicyExpireDate()!=null ? new Timestamp(memberDetailVO.getPolicyExpireDate().getTime()):null);
            if(memberDetailVO.getCardPrintCnt()!= null ){
                cStmtObject.setInt(CARD_PRN_COUNT,memberDetailVO.getCardPrintCnt());
            }//end of if(memberDetailVO.getCardPrintCnt()!= null )
            else{
                cStmtObject.setInt(CARD_PRN_COUNT,0);
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
                cStmtObject.setLong(PIN_CODE,new Long(memberAddressVO.getPinCode()));
            }//end of if(memberAddressVO.getPinCode()!=null && !memberAddressVO.getPinCode().equals(""))
            else{
                cStmtObject.setLong(PIN_CODE,0);
            }//end of else
          //  cStmtObject.setLong(COUNTRY_ID,Long.parseLong(memberAddressVO.getCountryCode()));
            if(memberAddressVO.getCountryCode()!=null && !memberAddressVO.getCountryCode().equals("")){
                cStmtObject.setLong(COUNTRY_ID,new Long(memberAddressVO.getCountryCode()));
            }//end of if(memberAddressVO.getPinCode()!=null && !memberAddressVO.getPinCode().equals(""))
            else{
                cStmtObject.setLong(COUNTRY_ID,0);
            }//end of else
            cStmtObject.setString(EMAIL_ID,memberAddressVO.getEmailID());
            cStmtObject.setString(RES_PHONE_NO,memberAddressVO.getHomeIsdCode()+"|"+memberAddressVO.getHomeStdCode()+"|"+memberAddressVO.getHomePhoneNbr());
            cStmtObject.setString(OFF_PHONE_NO_1,memberAddressVO.getOff1IsdCode()+"|"+memberAddressVO.getOff1StdCode()+"|"+memberAddressVO.getPhoneNbr1());
            //System.out.println("OFF_PHONE_NO_1"+memberAddressVO.getOff1IsdCode()+"|"+memberAddressVO.getOff1StdCode()+"|"+memberAddressVO.getPhoneNbr1());
            cStmtObject.setString(OFF_PHONE_NO_2,memberAddressVO.getOff2IsdCode()+"|"+memberAddressVO.getOff2StdCode()+"|"+memberAddressVO.getPhoneNbr2());
            //System.out.println("OFF_PHONE_NO_2==="+memberAddressVO.getOff2IsdCode()+"|"+memberAddressVO.getOff2StdCode()+"|"+memberAddressVO.getPhoneNbr2());
            cStmtObject.setString(MOBILE_NO,memberAddressVO.getMobileIsdCode()+"|"+memberAddressVO.getMobileNbr());
            //System.out.println("MOBILE_NO===="+memberAddressVO.getMobileIsdCode()+"|"+memberAddressVO.getMobileNbr());
            cStmtObject.setString(FAX_NO,memberAddressVO.getFaxNbr());
            cStmtObject.setString(TPA_ENROLLMENT_NUMBER,memberDetailVO.getEnrollmentNbr());
            cStmtObject.setString(POLICY_MODE,strPolicyMode);
            cStmtObject.setString(POLICY_TYPE,strPolicyType);

            if(memberDetailVO.getSeqID()!=null){
                cStmtObject.setLong(SEQ_ID,memberDetailVO.getSeqID());
            }//end of if(memberDetailVO.getSeqID()!=null)
            else{
                cStmtObject.setLong(SEQ_ID,0);
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
            cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
			
			cStmtObject.setString(EMAIL_ID2,memberAddressVO.getEmailID2());
			
			cStmtObject.setString(V_EMIRATE_ID,memberDetailVO.getEmirateId());
			cStmtObject.setString(V_PASSPORT_NUMBER,memberDetailVO.getPassportNumber());
			cStmtObject.setString(V_MARITAL_STATUS,memberDetailVO.getMaritalStatus());
			if(!"".equals(TTKCommon.checkNull(memberDetailVO.getNationality()))){
			cStmtObject.setLong(V_NATIONALITY_ID,Long.parseLong(memberDetailVO.getNationality()));
	         }
			else{
				cStmtObject.setLong(52,0);
			}//end of else
			//cStmtObject.setLong(V_NATIONALITY_ID,Long.parseLong(memberDetailVO.getNationality()));
			cStmtObject.setString(V_MEM_LAST_NAME,memberDetailVO.getSecondName());
			cStmtObject.setString(V_FAMILY_NAME,memberDetailVO.getFamilyName());
			cStmtObject.setString(V_UID_NUMBER,memberDetailVO.getuIDNumber());
			cStmtObject.setString(V_RESIDENTIAL_LOC,memberAddressVO.getResidentialLocation());			
			cStmtObject.setString(V_WORK_LOC,memberAddressVO.getWorkLocation());
			cStmtObject.setString(V_VIP_YN,memberDetailVO.getVipYN());
			cStmtObject.setString(V_GLOBALNET_MEM_ID,memberDetailVO.getGlobalNetMemberID());
		    
			
			// Capitation Policy  Started 
			if(memberDetailVO.getMinAge() != null){
				cStmtObject.setInt(v_min_age,memberDetailVO.getMinAge());
			}
			else{
				cStmtObject.setInt(v_min_age,0);
			}
		//	cStmtObject.setInt(v_min_age,memberDetailVO.getMinAge());
			
				if(memberDetailVO.getMaxAge() != null){
					cStmtObject.setInt(v_max_age,memberDetailVO.getMaxAge());
				}
				else{
					cStmtObject.setInt(v_max_age,0);
				}
							
				
	//		cStmtObject.setInt(v_max_age,memberDetailVO.getMaxAge());
			cStmtObject.setDouble(v_medical_prem,memberDetailVO.getMedicalPremium());
			cStmtObject.setDouble(v_maternity_prem,memberDetailVO.getMaternityPremium());
			cStmtObject.setDouble(v_optical_prem,memberDetailVO.getOpticalPremium());
			cStmtObject.setDouble(v_dental_prem,memberDetailVO.getDentalPremium());
			cStmtObject.setDouble(v_wellness_prem,memberDetailVO.getWellnessPremium());
			cStmtObject.setDouble(v_gross_prem,memberDetailVO.getGrossPremium());
			cStmtObject.setDouble(v_ip_net_prem,memberDetailVO.getIpNetPremium());
			cStmtObject.setDouble(v_insurer_margin,memberDetailVO.getInsurerMarginPER());
			cStmtObject.setDouble(v_broker_margin,memberDetailVO.getBrokerMarginPER());
			cStmtObject.setDouble(v_tpa_margin,memberDetailVO.getTapMarginPER());
			cStmtObject.setDouble(v_reins_brk_margin,memberDetailVO.getReInsBrkMarginPER());
			cStmtObject.setDouble(v_other_margin,memberDetailVO.getOtherMarginPER());
			
		
			if(memberDetailVO.getNetPremium()!=0.0)cStmtObject.setDouble(v_net_prem,memberDetailVO.getNetPremium());
			else cStmtObject.setDouble(v_net_prem,0);

		    if(memberDetailVO.getActualPremium()!=0.0) cStmtObject.setDouble(v_actual_prem,memberDetailVO.getActualPremium());
		    else  cStmtObject.setDouble(v_actual_prem,0);
		    
		    if(!"".equals(TTKCommon.checkNull(memberDetailVO.getPolicySeqID()))){
		    cStmtObject.setLong(76, memberDetailVO.getPolicySeqID());
	         }
			else{
				cStmtObject.setLong(76,0);
			}//end of else
	//	    cStmtObject.setLong(76, memberDetailVO.getPolicySeqID());
		    
		    cStmtObject.setString(v_DHPO_Upload_Status,memberDetailVO.getDhpoUploadFlag());
		    cStmtObject.setString(member_birth_city,memberDetailVO.getMemberBirthCity());
		    cStmtObject.setString(v_ins_category_code,memberDetailVO.getInsCategoryCode());
		    cStmtObject.setTimestamp(MEMBER_ADDED_DATED,memberDetailVO.getMemAddedDate()!=null ? new Timestamp(memberDetailVO.getMemAddedDate().getTime()):null);
           
            if(TTKCommon.checkNull(memberDetailVO.getMember_type_id()).length()>0)
            cStmtObject.setBigDecimal(81,new BigDecimal(memberDetailVO.getMember_type_id()));
            else cStmtObject.setBigDecimal(81,null);
            //cStmtObject.setString(82,memberDetailVO.getMem_full_name());
            cStmtObject.setString(82,null);/////
            cStmtObject.setString(83,memberDetailVO.getBirth_certificate_id());
            cStmtObject.setString(84,memberDetailVO.getGdrfa_file_number());
            cStmtObject.setString(85,memberDetailVO.getDhpoMemberId());
            
            if(TTKCommon.checkNull(memberDetailVO.getPolicySequence()).length()>0)
                cStmtObject.setBigDecimal(86,new BigDecimal(memberDetailVO.getPolicySequence()));
             else cStmtObject.setBigDecimal(86,null);
            
            if(TTKCommon.checkNull(memberDetailVO.getEntityType()).length()>0)
                cStmtObject.setLong(87,new Long(memberDetailVO.getEntityType()));
             else cStmtObject.setLong(87,0);
            
            cStmtObject.setString(88,memberDetailVO.getEntityId());
            cStmtObject.setString(89,memberDetailVO.getRelationTo());
            cStmtObject.setString(90, memberDetailVO.getPedDescription());
            cStmtObject.setString(91, memberDetailVO.getMaterinityDescription());
            
            cStmtObject.registerOutParameter(MEMBER_SEQ_ID,Types.BIGINT);//MEMBER_SEQ_ID
            cStmtObject.registerOutParameter(ENR_ADDRESS_SEQ_ID,Types.BIGINT);//ENR_ADDRESS_SEQ_ID
            cStmtObject.registerOutParameter(MEM_AGE,Types.INTEGER);//MEM_AGE
            cStmtObject.registerOutParameter(CARD_PRN_COUNT,Types.INTEGER);//CARD_PRN_COUNT
            cStmtObject.registerOutParameter(TPA_ENROLLMENT_NUMBER,Types.VARCHAR); //TPA_ENROLLMENT_NUMBER
            cStmtObject.registerOutParameter(V_EMIRATE_ID,Types.VARCHAR); //V_EMIRATE_ID
            
            cStmtObject.execute();
            conn.commit();
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
    
    public int saveMember1(MemberDetailVO memberDetailVO,Long lngMemSeqID,String strPolicyMode) throws TTKException
    {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMember1);
            cStmtObject.setLong(1,lngMemSeqID);
            cStmtObject.setString(2,memberDetailVO.getFlagV());
            cStmtObject.setTimestamp(3,memberDetailVO.getPassportExpiryDate()!=null ? new Timestamp(memberDetailVO.getPassportExpiryDate().getTime()):null);
           // cStmtObject.setTimestamp(3,new Timestamp(memberDetailVO.getPassportExpiryDate().getTime()));
            cStmtObject.setString(4,memberDetailVO.getPassportIssuedCountry());
            cStmtObject.setString(5,memberDetailVO.getVisaNumber());
            cStmtObject.setString(6,memberDetailVO.getSponsorType());
            cStmtObject.setString(7,memberDetailVO.getSponsorID());
            cStmtObject.setTimestamp(8,memberDetailVO.getResidentCardNoExpiryDate()!=null ? new Timestamp(memberDetailVO.getResidentCardNoExpiryDate().getTime()):null);
            //cStmtObject.setTimestamp(8,new Timestamp(memberDetailVO.getResidentCardNoExpiryDate().getTime()));
            cStmtObject.setString(9, strPolicyMode);
            cStmtObject.registerOutParameter(10,Types.NUMERIC);
            cStmtObject.execute();
            iResult = (int) cStmtObject.getBigDecimal(10).longValue();
            conn.commit();
            

            return iResult;

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
   }//end of saveMember1(MemberDetailVO memberDetailVO,String strPolicyMode,String strPolicType)
  
    
    
	public ArrayList getRelationshipCode(String strAbbrCode) throws TTKException {
		Connection conn1 = null;
        ResultSet rs = null;
        PreparedStatement pStmt = null;
        CacheObject cacheObject = null;
        ArrayList<Object> alRelationshipCode = new ArrayList<Object>();
        try{
            conn1 = ResourceManager.getConnection();
            pStmt=conn1.prepareStatement(strGetRelationshipCode);
            //pStmt.setString(1,strAbbrCode);
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
	            	    memberDetailVO.setMemberActivePeriod(rs.getInt("mem_active_days"));
						memberDetailVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
						
						memberDetailVO.setPremiumDatesFlag(TTKCommon.checkNull(rs.getString("display_vat_yn")));
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
    public List<String> deleteMember(ArrayList alMember) throws TTKException
    {
        List<String> iResult = new ArrayList<>();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try
        {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteMember);
   
            cStmtObject.setString(1, (String)alMember.get(0));
            cStmtObject.setString(2, (String)alMember.get(1));
            cStmtObject.setString(3, (String)alMember.get(2));
            cStmtObject.setInt(4, Integer.parseInt((String)alMember.get(3)));

            if(alMember.get(4)!=null){
                cStmtObject.setLong(5,(Long)alMember.get(4));
            }//end of if(alMember.get(4)!=null)
            else{
                cStmtObject.setLong(5,0);
            }//end of else

            if(alMember.get(5)!= null ){
                cStmtObject.setLong(6,(Long)alMember.get(5));
            }//end of if(alMember.get(5)!= null )
            else{
                cStmtObject.setLong(6,0);
            }//end of else

            if(alMember.get(6)!= null ){
                cStmtObject.setString(7,(String)alMember.get(6));
            }//end of if(alMember.get(5)!= null )
            else{
                cStmtObject.setString(7,null);
            }//end of else
            cStmtObject.registerOutParameter(8, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.registerOutParameter(9, Types.VARCHAR); // Delete Flag
            cStmtObject.execute();
            iResult.add(String.valueOf(cStmtObject.getInt(8))); //ROWS_PROCESSED
            iResult.add(String.valueOf(cStmtObject.getString(9)));
            conn.commit();
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
        if(alMember.get(3)!=null){
        	cStmtObject.setLong(4, Long.parseLong((String) alMember.get(3)));
        }//end of if(alMember.get(3)!=null)
        else{
            cStmtObject.setLong(4,0);
        }//end of else
        
        if(alMember.get(4)!=null){
            cStmtObject.setLong(5,(Long)alMember.get(4));
        }//end of if(alMember.get(4)!=null)
        else{
            cStmtObject.setLong(5,0);
        }//end of else
        if(alMember.get(5)!=null){
            cStmtObject.setLong(6,(Long)alMember.get(5));
        }//end of if(alMember.get(5)!=null)
        else{
            cStmtObject.setLong(6,0);
        }//end of else
        if(alMember.get(6)!= null ){
            cStmtObject.setLong(7,(Long)alMember.get(6));
        }//end of if(alMember.get(6)!= null )
        else{
            cStmtObject.setLong(7,0);
        }//end of else
            cStmtObject.registerOutParameter(8, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.setString(9, (String)alMember.get(7));
            
            cStmtObject.execute();
            iResult = cStmtObject.getInt(8);//ROWS_PROCESSED ID
            
            conn.commit();
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
    public MemberDetailVO getMember(ArrayList<?> alMember) throws TTKException
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
            cStmtObject.registerOutParameter(4,Types.OTHER);//ROW_PROCESSED
            cStmtObject.execute();
            conn.commit();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            /*ResultSetMetaData rsMetaData = rs.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();

            // get the column names; column indexes start from 1
            for (int i = 1; i < numberOfColumns + 1; i++) {
              String columnName = rsMetaData.getColumnName(i);
            //  System.out.println("Column "+i+"\t"+columnName);
              
            }*/

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
                    
                    memberDetailVO.setGlobalNetMemberID(TTKCommon.checkNull(rs.getString("GLOBAL_NET_MEMBER_ID")));
                   
            		
                    memberDetailVO.setGenderDescription(TTKCommon.checkNull(rs.getString("GENDER")));
                    if(rs.getString("MEM_DOB")!=null){
                        memberDetailVO.setDateOfBirth(new Date(rs.getTimestamp("MEM_DOB").getTime()));
                    }//end of if(rs.getString("MEM_DOB")!=null)
                    if(rs.getString("MEM_AGE")!=null){
                        memberDetailVO.setAge(new Integer(rs.getInt("MEM_AGE")));
                    }//end of if(rs.getString("MEM_AGE")!=null)
                    memberDetailVO.setEducation(TTKCommon.checkNull(rs.getString("education")));
                    memberDetailVO.setNationalidExpdate(rs.getString("national_id_expiry_date")!=null ? new Date(rs.getTimestamp("national_id_expiry_date").getTime()):null);
                    memberDetailVO.setFlagYN(TTKCommon.checkNull(rs.getString("flag")));
                    memberDetailVO.setNationalCivilId(TTKCommon.checkNull(rs.getString("parental_national_civil_id")));
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
                    memberDetailVO.setVipYN(rs.getString("VIP_YN"));
                    if(rs.getString("CARD_PRN_DATE")!=null){
                        memberDetailVO.setCardPrintDate(new Date(rs.getTimestamp("CARD_PRN_DATE").getTime()));
                    }//end of if(rs.getString("CARD_PRN_DATE")!=null)

                    if(rs.getString("DOC_DISPATCH_DATE")!=null){
                        memberDetailVO.setDocDispatchDate(new Date(rs.getTimestamp("DOC_DISPATCH_DATE").getTime()));
                    }//end of if(rs.getString("DOC_DISPATCH_DATE")!=null)

                    if(rs.getString("ENR_ADDRESS_SEQ_ID")!=null){
                        memberAddressVO.setAddrSeqId(new Long(rs.getString("ENR_ADDRESS_SEQ_ID")));
                    }//end of if(rs.getString("ENR_ADDRESS_SEQ_ID")!=null)
                    
                    memberAddressVO.setOff1IsdCode(TTKCommon.checkNull(rs.getString("OFF_PHONE_1_ISD")));
            		memberAddressVO.setOff1StdCode(TTKCommon.checkNull(rs.getString("OFF_PHONE_1_STD")));
            		memberAddressVO.setOff2IsdCode(TTKCommon.checkNull(rs.getString("OFF_PHONE_2_ISD")));
            		memberAddressVO.setOff2StdCode(TTKCommon.checkNull(rs.getString("OFF_PHONE_2_STD")));
            		memberAddressVO.setHomeIsdCode(TTKCommon.checkNull(rs.getString("RES_PHONE_ISD")));
            		memberAddressVO.setHomeStdCode(TTKCommon.checkNull(rs.getString("RES_PHONE_STD")));
            		
            		String ISD_CODE = TTKCommon.checkNull(rs.getString("MOBILE_ISD"));
            		if(ISD_CODE.contains("|")){
    	              String[] output = ISD_CODE.split("\\|");
    	                for (String Isd_Code : output) {
    	                	memberAddressVO.setMobileIsdCode(TTKCommon.checkNull(Isd_Code));
    	                } 
            		}else{
            		memberAddressVO.setMobileIsdCode(TTKCommon.checkNull(rs.getString("MOBILE_ISD"))); 
            		}
            		memberAddressVO.setResidentialLocation(TTKCommon.checkNull(rs.getString("RESIDENCIAL_LOC")));
            		memberAddressVO.setWorkLocation(TTKCommon.checkNull(rs.getString("WORK_LOC")));   
            		
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
                    String MOBILE_NO = TTKCommon.checkNull(rs.getString("MOBILE_NO"));
                    if(MOBILE_NO.contains("|")){
   	                String[] output1 = MOBILE_NO.split("\\|");
   	                for (String Mob_No : output1) {
   	                	memberAddressVO.setMobileNbr(TTKCommon.checkNull(Mob_No));
   	                	System.out.println("MOBILE_NO222...  "+rs.getString("MOBILE_NO"));
   	                }
                    }else{
                    memberAddressVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
                    }
                    memberAddressVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
                    memberAddressVO.setFaxNbr(TTKCommon.checkNull(rs.getString("FAX_NO")));
                    memberDetailVO.setClarificationTypeID(TTKCommon.checkNull(rs.getString("CLARIFY_GENERAL_TYPE_ID")));
                    memberDetailVO.setAssocGenderRel(TTKCommon.checkNull(rs.getString("ASSOC_GENDER_REL")));
                    memberDetailVO.setGenderYN(TTKCommon.checkNull(rs.getString("ASSOC_GENDER_REL")));
                    memberDetailVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS")));
                    
                    memberDetailVO.setMaritalStatus(TTKCommon.checkNull(rs.getString("marital_status_id")));
                    memberDetailVO.setEmirateId(TTKCommon.checkNull(rs.getString("emirate_id")));
                    memberDetailVO.setNationality(TTKCommon.checkNull(rs.getString("nationality_id")));
                    memberDetailVO.setPassportNumber(TTKCommon.checkNull(rs.getString("passport_number")));

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
            		memberDetailVO.setSecondName(TTKCommon.checkNull(rs.getString("MEM_LAST_NAME")));
            		memberDetailVO.setFamilyName(TTKCommon.checkNull(rs.getString("FAMILY_NAME")));
            		memberDetailVO.setuIDNumber(TTKCommon.checkNull(rs.getString("UID_NUMBER")));
            		
            		// capitation policy started
               	    memberDetailVO.setMinAge(rs.getInt("min_age"));
            	    memberDetailVO.setMaxAge(rs.getInt("max_age"));
            	    
            	
                	memberDetailVO.setMedicalPremium(rs.getDouble("medical_prem"));
               	    memberDetailVO.setMaternityPremium(rs.getDouble("maternity_prem"));
            		memberDetailVO.setOpticalPremium(rs.getDouble("optical_prem"));
            	    memberDetailVO.setDentalPremium(rs.getDouble("dental_prem"));
            		memberDetailVO.setWellnessPremium(rs.getDouble("wellness_prem"));
            		memberDetailVO.setIpNetPremium(rs.getDouble("ip_net_prem"));
            		memberDetailVO.setOpNetPremium(rs.getDouble("op_net_prem"));
             
            		//memberDetailVO.setInsurerMargin(rs.getDouble("insurer_margin"));
            		memberDetailVO.setInsurerMarginPER(rs.getDouble("insurer_margin"));
                   // memberDetailVO.setBrokerMargin(rs.getDouble("broker_margin"));
                    memberDetailVO.setBrokerMarginPER(rs.getDouble("broker_margin"));
                	//memberDetailVO.setTapMargin(rs.getDouble("tpa_margin"));
                	//memberDetailVO.setTapMarginPER(rs.getDouble("tpa_margin"));
            	  //  memberDetailVO.setReInsBrkMargin(rs.getDouble("reins_brk_margin"));
            	    memberDetailVO.setReInsBrkMarginPER(rs.getDouble("reins_brk_margin"));
                   // memberDetailVO.setOtherMargin(rs.getDouble("other_margin"));
                    memberDetailVO.setOtherMarginPER(rs.getDouble("other_margin"));
                  //	memberDetailVO.setNetPremium(rs.getDouble("net_prem"));
                  	memberDetailVO.setInsurerMarginAED(rs.getDouble("insurer_margin_aed"));
                    memberDetailVO.setBrokerMarginAED(rs.getDouble("broker_margin_aed"));
                	memberDetailVO.setTapMarginAED(rs.getDouble("tpa_margin_aed"));
            	    memberDetailVO.setReInsBrkMarginAED(rs.getDouble("reins_brk_margin_aed"));
                    memberDetailVO.setOtherMarginAED(rs.getDouble("other_margin_aed"));
                  
                	memberDetailVO.setGrossPremium(rs.getDouble("gross_prem"));
                	memberDetailVO.setActualPremium(rs.getDouble("ACTUAL_PREMIUM"));
                	memberDetailVO.setNetPremium(rs.getDouble("net_prorata_prem"));
                	memberDetailVO.setMemberActivePeriod(rs.getInt("MEM_ACTIVE_PERIOD"));
                  	
                	memberDetailVO.setVatPercent(rs.getDouble("vat_percent"));
                	memberDetailVO.setVatAed(rs.getDouble("vat_aed"));
                	memberDetailVO.setCollectedPremium(rs.getDouble("collected_premium"));
                  	
                	
                	memberDetailVO.setInsurerMarginAEDPER(rs.getString("insurer_margin_flag"));
                	memberDetailVO.setBrokerMarginAEDPER(rs.getString("broker_margin_flag"));
                	memberDetailVO.setReInsBrkMarginAEDPER(rs.getString("reins_brk_margin_flag"));
                	memberDetailVO.setOtherMarginAEDPER(rs.getString("other_margin_flag"));
                	
                	memberDetailVO.setProRataInsMargin(rs.getDouble("pro_insurer_margin"));
                	memberDetailVO.setProRataBrokerMargin(rs.getDouble("pro_broker_margin"));
                	memberDetailVO.setProRataReInsBrkMargin(rs.getDouble("pro_reins_brk_margin"));
                	memberDetailVO.setProRataOtherMargin(rs.getDouble("pro_other_margin"));
                	memberDetailVO.setProRataIpNetPremium(rs.getDouble("pro_ip_net_premium"));
                	memberDetailVO.setProRataOpNetPremium(rs.getDouble("pro_op_net_premium"));
                	
                	
                	if("PER".equals(rs.getString("insurer_margin_flag")))
                		memberDetailVO.setInsurerMargin(rs.getDouble("insurer_margin"));
    				
    				else
    					memberDetailVO.setInsurerMargin(rs.getDouble("insurer_margin_aed"));
    				
    				
    				if("PER".equals(rs.getString("broker_margin_flag")))
    					memberDetailVO.setBrokerMargin(rs.getDouble("broker_margin"));
    				
    				else
    					memberDetailVO.setBrokerMargin(rs.getDouble("broker_margin_aed"));
    				
    				
    				if("PER".equals(rs.getString("reins_brk_margin_flag")))
    					memberDetailVO.setReInsBrkMargin(rs.getDouble("reins_brk_margin"));
    				
    				else
    					memberDetailVO.setReInsBrkMargin(rs.getDouble("reins_brk_margin_aed"));
    				
    				
    				if("PER".equals(rs.getString("other_margin_flag")))
    					memberDetailVO.setOtherMargin(rs.getDouble("other_margin"));
    				
    				else
    					memberDetailVO.setOtherMargin(rs.getDouble("other_margin_aed"));
                	
                	
                	
                  // capitation policy started
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
					memberDetailVO.setDhpoUploadFlag(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_UPLOAD_FLAG")));
					memberDetailVO.setMemAddedDate(rs.getString("MEMBER_ADDED_DATE")!=null ? new Date(rs.getTimestamp("MEMBER_ADDED_DATE").getTime()):null);
					memberDetailVO.setLastMemberDataUpdatedDate(TTKCommon.checkNull(rs.getString("MODIFIED_DATE")));
					memberDetailVO.setLastMemberRegisterUploadedDate(TTKCommon.checkNull(rs.getString("UPLOADED_DATE_TO_PORTAL")));
					memberDetailVO.setLastMemberRegisterXmlMailTrigeredDate(TTKCommon.checkNull(rs.getString("MAIL_TRIGGER_DATE")));
					memberDetailVO.setAuthorityProductId(TTKCommon.checkNull(rs.getString("prod_auth_id")));
					memberDetailVO.setMemberInceptionEligibiltyUpdateRemarks(TTKCommon.checkNull(rs.getString("mem_rule_update_remarks")));
					
					memberDetailVO.setMemeligFlag(TTKCommon.checkNull(rs.getString("mem_elig_flag")));
					
					memberDetailVO.setInsCategoryCode(TTKCommon.checkNull(rs.getString("ins_category_code")));
					memberDetailVO.setMemberCancelRemarks(TTKCommon.checkNull(rs.getString("member_cancel_rmk")));
					
					memberDetailVO.setPremiumDatesFlag(TTKCommon.checkNull(rs.getString("display_vat_yn")));
					
					
					//memberDetailVO.setMemberBirthCity(TTKCommon.checkNull(rs.getString("mem_birth_city")));//member birth city
					memberDetailVO.setMemberDeletionRemarks(TTKCommon.checkNull(rs.getString("del_remarks")));
					memberDetailVO.setMemberCancelRemarks(TTKCommon.checkNull(rs.getString("member_cancel_rmk")));
					memberDetailVO.setMember_type_id(TTKCommon.checkNull(rs.getString("MEMBER_TYPE_ID")));
					memberDetailVO.setMem_full_name(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					memberDetailVO.setBirth_certificate_id(TTKCommon.checkNull(rs.getString("BIRTH_CERTIFICATE_ID")));
					memberDetailVO.setGdrfa_file_number(TTKCommon.checkNull(rs.getString("GDRFA_FILE_NUMBER")));
				//	memberDetailVO.setPolicySequence(TTKCommon.checkNull(rs.getString("POLICY_SEQUENCE")));
//					memberDetailVO.setTpa_fee(TTKCommon.checkNull(rs.getString("TPA_FEE")));
//					memberDetailVO.setTpa_fee_type(TTKCommon.checkNull(rs.getString("TPA_FEE_TYPE")));
					
					if(rs.getString("POLICY_SEQUENCE")!=null)
						memberDetailVO.setPolicySequence(TTKCommon.checkNull(rs.getString("POLICY_SEQUENCE")));
					
					else
						memberDetailVO.setPolicySequence("");
					
					
					if(rs.getString("DHPO_MEMBER_ID")!=null)
						memberDetailVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					
					else
						memberDetailVO.setDhpoMemberId("");
					
					memberDetailVO.setEntityType(TTKCommon.checkNull(rs.getString("ENTITY_TYPE")));
					memberDetailVO.setEntityId(TTKCommon.checkNull(rs.getString("ENTITY_ID")));
					memberDetailVO.setRelationTo(TTKCommon.checkNull(rs.getString("RELATION_TO")));
					memberDetailVO.setPedDescription(TTKCommon.checkNull(rs.getString("ped_desc")));
					memberDetailVO.setMaterinityDescription(TTKCommon.checkNull(rs.getString("maternity_desc")));
					memberDetailVO.setFlagV(TTKCommon.checkNull(rs.getString("flag_new")));
					if(rs.getString("passport_exp_date")!=null){
                        memberDetailVO.setPassportExpiryDate(new Date(rs.getTimestamp("passport_exp_date").getTime()));
                    }
					memberDetailVO.setPassportIssuedCountry(TTKCommon.checkNull(rs.getString("passport_issue_country")));
					memberDetailVO.setVisaNumber(TTKCommon.checkNull(rs.getString("visa_number")));
					memberDetailVO.setSponsorType(TTKCommon.checkNull(rs.getString("sponser_type")));
					memberDetailVO.setSponsorID(TTKCommon.checkNull(rs.getString("sponser_id")));
					

					if(rs.getString("resident_card_exp_date")!=null){
                        memberDetailVO.setResidentCardNoExpiryDate(new Date(rs.getTimestamp("resident_card_exp_date").getTime()));
                    }
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
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            conn.commit();
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
            		cStmtObject.setLong(1,0);
            	}//end of else
            	cStmtObject.registerOutParameter(2,Types.OTHER);
                cStmtObject.execute();
                conn.commit();
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
            cStmtObject.registerOutParameter(4,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            conn.commit();
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
            if(alRenew.get(1) != null){
            	cStmtObject.setLong(2,(Long)alRenew.get(1));//ADDED_BY
           }//end of if(alRenew.get(1) != null)
           else{
           	cStmtObject.setLong(2,0);
           }//end of else
            //  cStmtObject.setLong(2,(Long)alRenew.get(1));//ADDED_BY
            if(alRenew.get(2) != null){
            	 cStmtObject.setLong(3,(Long)alRenew.get(2));//POLICY_GROUP_SEQ_ID
            }//end of if(alRenew.get(2) != null)
            else{
            	cStmtObject.setLong(3,0);
            }//end of else
         //   cStmtObject.setLong(3,(Long)alRenew.get(2));//POLICY_GROUP_SEQ_ID
            if(alRenew.get(3) != null){
            	cStmtObject.setLong(4,(Long)alRenew.get(3));//POLICY_SEQ_ID
            }//end of if(alRenew.get(3) != null)
            else{
            	cStmtObject.setLong(4,0);
            }//end of else
          //  cStmtObject.setLong(4,(Long)alRenew.get(3));//POLICY_SEQ_ID
            if(alRenew.get(4) != null){
            	cStmtObject.setLong(5,(Long)alRenew.get(4));//ENDORSEMENT_SEQ_ID
            }//end of if(alRenew.get(4) != null)
            else{
            	cStmtObject.setLong(5,0);
            }//end of else
            cStmtObject.setString(6,(String)alRenew.get(5));//POLICY_MODE - ENM/END
            cStmtObject.setString(7,(String)alRenew.get(6));//POLICY_TYPE - IP/IG/CP/NC
            cStmtObject.registerOutParameter(8,Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(8);//ROWS_PROCESSED
            conn.commit();
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
            	//System.out.println("getPolicyGroupSeqID==="+memberDetailVO.getPolicyGroupSeqID());
                cStmtObject.setLong(1,memberDetailVO.getPolicyGroupSeqID());
            }//end of if(memberDetailVO.getPolicyGroupSeqID()!=null)
            else{
                cStmtObject.setLong(1,0);
            }//end of else
            if(memberDetailVO.getPolicySeqID()!=null){
                cStmtObject.setLong(2,memberDetailVO.getPolicySeqID());
            }//end of if(memberDetailVO.getPolicySeqID()!=null)
            else{
                cStmtObject.setLong(2,0);
            }//end of else
         //   cStmtObject.setString(3,memberDetailVO.getEnrollmentID());//TPA_ENROLLMENT_NUMBER
           
            if(memberDetailVO.getEnrollmentID() != null){
            	cStmtObject.setString(3,memberDetailVO.getEnrollmentID());//TPA_ENROLLMENT_NUMBER
			}//end of if(contactVO.getContactSeqID() != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else            
            
            cStmtObject.setString(4,memberDetailVO.getOrderNbr());//ORDER_NUMBER
            cStmtObject.setString(5,memberDetailVO.getEmployeeNbr());//EMPLOYEE_NO

            if(memberDetailVO.getMemberAddressVO().getAddrSeqId() != null){
                cStmtObject.setLong(6,memberDetailVO.getMemberAddressVO().getAddrSeqId());
            }//end of if(memberDetailVO.getMemberAddressVO().getAddrSeqId() != null)
            else{
                cStmtObject.setLong(6,0);
            }//end of else

            cStmtObject.setString(7,memberDetailVO.getName());//INSURED_NAME
            cStmtObject.setString(8,memberDetailVO.getDepartment());//DEPARTMENT
//            cStmtObject.setString(9,memberDetailVO.getDesignation());//DESIGNATION
            cStmtObject.setString(9,memberDetailVO.getOccuCodeDesc());
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
                cStmtObject.setLong(14,0);
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
                cStmtObject.setLong(18,0);
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
            if(!"".equals(TTKCommon.checkNull(memberDetailVO.getMemberAddressVO().getPinCode()))){
            	cStmtObject.setLong(30,Long.parseLong(memberDetailVO.getMemberAddressVO().getPinCode())); //PIN_CODE
             }
            else{
            	cStmtObject.setLong(30, 0);
            }
          //  cStmtObject.setLong(30,Long.parseLong(memberDetailVO.getMemberAddressVO().getPinCode()));//PIN_CODE
            cStmtObject.setLong(31,Long.parseLong(memberDetailVO.getMemberAddressVO().getCountryCode()));//COUNTRY_ID
            cStmtObject.setString(32,memberDetailVO.getMemberAddressVO().getEmailID());//EMAIL_ID
            cStmtObject.setString(33,memberDetailVO.getMemberAddressVO().getHomeIsdCode()+"|"+memberDetailVO.getMemberAddressVO().getHomeStdCode()+"|"+memberDetailVO.getMemberAddressVO().getHomePhoneNbr());//RES_PHONE_NO
            cStmtObject.setString(34,memberDetailVO.getMemberAddressVO().getOff1IsdCode()+"|"+memberDetailVO.getMemberAddressVO().getOff1StdCode()+"|"+memberDetailVO.getMemberAddressVO().getPhoneNbr1());//OFF_PHONE_NO_1
            cStmtObject.setString(35,memberDetailVO.getMemberAddressVO().getOff2IsdCode()+"|"+memberDetailVO.getMemberAddressVO().getOff2StdCode()+"|"+memberDetailVO.getMemberAddressVO().getPhoneNbr2());//OFF_PHONE_NO_2
          // System.out.println("ISD Code::::::"+memberDetailVO.getMemberAddressVO().getMobileIsdCode());
            if("ISD".equals(memberDetailVO.getMemberAddressVO().getMobileIsdCode())){
            	//System.out.println("If ISD===>"+memberDetailVO.getMemberAddressVO().getMobileNbr());
            cStmtObject.setString(36,memberDetailVO.getMemberAddressVO().getMobileNbr());//MOBILE_NO
            }
            else{
            	//System.out.println("else====>"+memberDetailVO.getMemberAddressVO().getMobileIsdCode()+"|"+memberDetailVO.getMemberAddressVO().getMobileNbr());
            cStmtObject.setString(36,memberDetailVO.getMemberAddressVO().getMobileIsdCode()+"|"+memberDetailVO.getMemberAddressVO().getMobileNbr());//MOBILE_NO
            } 
            
            cStmtObject.setString(37,memberDetailVO.getMemberAddressVO().getFaxNbr());//FAX_NO
            cStmtObject.setString(38,memberDetailVO.getCertificateNbr());
            cStmtObject.setString(39,memberDetailVO.getCreditCardNbr());
            cStmtObject.setString(40,"EXT");
    
            if(memberDetailVO.getEndorsementSeqID() != null){
                cStmtObject.setLong(41,memberDetailVO.getEndorsementSeqID());//ENDORSEMENT_SEQ_ID
            }//end of if(memberDetailVO.getEndorsementSeqID() != null)
            else{
                cStmtObject.setLong(41,0);
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
			cStmtObject.setString(52,memberDetailVO.getSalaryBand());
			cStmtObject.setString(53,memberDetailVO.getMemberAddressVO().getResidentialLocation());
			cStmtObject.setString(54,memberDetailVO.getMemberAddressVO().getWorkLocation());
			cStmtObject.setString(55,memberDetailVO.getCommissionBased());
			//cStmtObject.setString(56,memberDetailVO.getEmployeeSecondName());
	       // cStmtObject.setString(57,memberDetailVO.getEmployeeFamilyName());
			cStmtObject.setString(56,null);
		    cStmtObject.setString(57,null);
		    cStmtObject.setLong(58,0);
		    cStmtObject.setString(59,memberDetailVO.getContractNo());
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(2,Types.BIGINT);
			cStmtObject.registerOutParameter(3,Types.VARCHAR);
			cStmtObject.registerOutParameter(6,Types.BIGINT);
			cStmtObject.registerOutParameter(14,Types.BIGINT);
			cStmtObject.registerOutParameter(18,Types.BIGINT);
            cStmtObject.execute();
            lngPolicyGroupSeqID = cStmtObject.getLong(1);//POLICY_GROUP_SEQ_ID
            conn.commit();
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
    public MemberDetailVO getEnrollment(ArrayList<?> alEnrollment) throws TTKException {
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
            cStmtObject.registerOutParameter(4,Types.OTHER);
            cStmtObject.execute();
            conn.commit();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            if(rs != null){
            	while(rs.next()){
            		memberDetailVO = new MemberDetailVO();
            		memberAddressVO = new MemberAddressVO();

            		if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
            			memberDetailVO.setPolicyGroupSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
            		}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)
                    log.info("POLICY_GROUP_SEQ_ID..."+memberDetailVO.getPolicyGroupSeqID());
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
            		//memberDetailVO.setOccuCodeDesc(TTKCommon.checkNull(rs.getString("OCCUPATION")));
            		//memberDetailVO.setDesignation(TTKCommon.checkNull(rs.getString("occupation_code")));
            		memberDetailVO.setOccuCodeDesc(TTKCommon.checkNull(rs.getString("occupation_code")));
            		memberDetailVO.setSalaryBand(TTKCommon.checkNull(rs.getString("SALARY_BAND")));
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
            		
            		memberAddressVO.setOff1IsdCode(TTKCommon.checkNull(rs.getString("OFF_PHONE_1_ISD")));
            		memberAddressVO.setOff1StdCode(TTKCommon.checkNull(rs.getString("OFF_PHONE_1_STD")));
            		memberAddressVO.setOff2IsdCode(TTKCommon.checkNull(rs.getString("OFF_PHONE_2_ISD")));
            		memberAddressVO.setOff2StdCode(TTKCommon.checkNull(rs.getString("OFF_PHONE_2_STD")));
            		memberAddressVO.setHomeIsdCode(TTKCommon.checkNull(rs.getString("RES_PHONE_ISD")));
            		memberAddressVO.setHomeStdCode(TTKCommon.checkNull(rs.getString("RES_PHONE_STD")));
            		memberAddressVO.setMobileIsdCode(TTKCommon.checkNull(rs.getString("MOBILE_ISD")));            		
            		
            		memberAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
            		memberAddressVO.setHomePhoneNbr(TTKCommon.checkNull(rs.getString("RES_PHONE_NO")));
            		memberAddressVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
            		memberAddressVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
            		memberAddressVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
            		memberAddressVO.setFaxNbr(TTKCommon.checkNull(rs.getString("FAX_NO")));
            		memberAddressVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
            		//Added by Praveen for EmailId2 - for KOC-1216
					memberAddressVO.setEmailID2(TTKCommon.checkNull(rs.getString("EMAIL_ID1")));//added for EmailId2
					
					memberAddressVO.setResidentialLocation(TTKCommon.checkNull(rs.getString("RESIDENCIAL_LOC")));
					memberAddressVO.setWorkLocation(TTKCommon.checkNull(rs.getString("WORK_LOC")));
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
            		memberDetailVO.setCommissionBased(TTKCommon.checkNull(rs.getString("COMM_BASED_YN")));
            		memberDetailVO.setEmployeeSecondName(rs.getString("insured_middle_name"));
        			memberDetailVO.setEmployeeFamilyName(rs.getString("insured_family_name"));
            		if(rs.getString("DATE_OF_MARRIAGE") != null){
            			memberDetailVO.setDateOfMarriage(new Date(rs.getTimestamp("DATE_OF_MARRIAGE").getTime()));
            		}//end of if(rs.getString("DATE_OF_MARRIAGE") != null)
            		memberDetailVO.setDhpoUploadFlag(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_UPLOAD_FLAG")));
            		memberDetailVO.setMemberCancelRemarks(TTKCommon.checkNull(rs.getString("member_cancel_rmk")));
            		memberDetailVO.setContractNo(TTKCommon.checkNull(rs.getString("contract_no")));
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
    public ArrayList<Object> getLocation(long lngPolicySeqID) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> alGroup = new ArrayList<Object>();
        CacheObject cacheObject = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strLocation);
            cStmtObject.setLong(1,lngPolicySeqID);
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            conn.commit();
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
    public ArrayList<Object> getPremiumList(ArrayList<?> alSearchCriteria) throws TTKException {
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
            cStmtObject.registerOutParameter(4,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            conn.commit();
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
                    if(rs.getString("MEM_TOT_SUM_INSURED") != null)
                    {
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
                   // 
                    //Added as per koC 1284 Change Request
                    if(rs.getString("REGION_ID") != null){
                        premiumInfoVO.setSelectregion(rs.getString("REGION_ID"));
                    }//end of if(rs.getString("REGION_ID") != null)
                    if(rs.getString("REGION_FLAG_YN") != null){
                        premiumInfoVO.setSelectregionYN(rs.getString("REGION_FLAG_YN"));
                    }//end of if(rs.getString("REGION_ID") != null)
                    if(rs.getBigDecimal("GROSS_PREMIUM") != null){
                        premiumInfoVO.setGrossPremium(rs.getBigDecimal("GROSS_PREMIUM"));
                  
                    }
               
                    if(rs.getBigDecimal("PRO_RATE_PREMIUM") != null){
                        premiumInfoVO.setProRataPremium(rs.getBigDecimal("PRO_RATE_PREMIUM"));
                   
                       
                    }
          
                    if(rs.getBigDecimal("net_prorata_prem") != null){
                        premiumInfoVO.setNetPremium(rs.getBigDecimal("net_prorata_prem"));
                   
                    }
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
    public ArrayList<Object> getBonusList(ArrayList<?> alSearchCriteria) throws TTKException {
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
                cStmtObject.setLong(1,0);
            }//end of else
            if(alSearchCriteria.get(1) != null){
                cStmtObject.setLong(2,(Long)alSearchCriteria.get(1));
            }//end of if(alSearchCriteria.get(1) != null)
            else{
                cStmtObject.setLong(2,0);
            }//end of else
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));
            cStmtObject.registerOutParameter(5,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(5);
            conn.commit();
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
    public long saveBonus(SumInsuredVO sumInsuredVO,String strPolicyMode,String strPolicyType,String currency) throws TTKException{
        //int iResult = 0;
    	long lngMemInsuredSeqID = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveBonus);
            
            cStmtObject.setString(12,currency);
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
                cStmtObject.setLong(2,0);
            }//end of else

            if(sumInsuredVO.getSeqID() != null){
                cStmtObject.setLong(3,sumInsuredVO.getSeqID()); //In Enrollment flow - Policy Seq ID and in Endorsement flow-Endorsement Seq ID
            }//end of if(sumInsuredVO.getSeqID() != null)
            else{
                cStmtObject.setLong(3,0);
            }//end of else

            if(sumInsuredVO.getMemberSeqID() != null){
                cStmtObject.setLong(4,sumInsuredVO.getMemberSeqID());
            }//end of if(sumInsuredVO.getMemberSeqID() != null)
            else{
                cStmtObject.setLong(4,0);
            }//end of else
       	    cStmtObject.setDouble(5,Double.parseDouble(sumInsuredVO.getSumInsured()));
            if(sumInsuredVO.getBonus() != null){
                cStmtObject.setDouble(6,sumInsuredVO.getBonus());
            }//end of if(sumInsuredVO.getBonus() != null)
            else{
                cStmtObject.setDouble(6,0);
            }//end of else

            if(sumInsuredVO.getBonusAmt() != null){
                cStmtObject.setDouble(7,sumInsuredVO.getBonusAmt().doubleValue());
            }//end of if(sumInsuredVO.getBonusAmt() != null)
            else{
                cStmtObject.setDouble(7,0);
            }//end of else

            if(sumInsuredVO.getEffectiveDate() != null){
                cStmtObject.setTimestamp(8,new Timestamp(sumInsuredVO.getEffectiveDate().getTime()));
            }//end of if(sumInsuredVO.getEffectiveDate() != null)
            else{
                cStmtObject.setTimestamp(8,null);
            }//end of else
            cStmtObject.setString(9,strPolicyMode);
            
            if(strPolicyType != null){
            	cStmtObject.setString(10,strPolicyType.toString());
            }//end of if(sumInsuredVO.getBonusAmt() != null)
            else{
                cStmtObject.setString(10,null);
            }//end of else
            
            //cStmtObject.setString(10,strPolicyType);
            if(!"".equals(TTKCommon.checkNull(sumInsuredVO.getProductPlanSeqID()))){
            	 cStmtObject.setInt(11,Integer.parseInt(sumInsuredVO.getProductPlanSeqID()));
             }
            else{
                cStmtObject.setInt(11,0);
            }//end of else
 			
           // cStmtObject.setInt(11,Integer.parseInt(sumInsuredVO.getProductPlanSeqID()));
            cStmtObject.setString(12,currency);
            cStmtObject.setLong(13,sumInsuredVO.getUpdatedBy());
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.registerOutParameter(14,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            lngMemInsuredSeqID = cStmtObject.getLong(1);
            conn.commit();
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
        ArrayList<Object> alPlanList = new ArrayList<Object>();
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetBonus);
            cStmtObject.setLong(1,lngMemInsuredSeqID);
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            conn.commit();
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
    public ArrayList<Object> getPlanList(ArrayList<Object> alPlanListObj) throws TTKException {
        Connection conn1 = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        CacheObject cacheObject = null;
        ArrayList<Object> alPlanList = new ArrayList<Object>();
        Long sumInsured= null;
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
            	cStmtObject.setLong(4,0);
            }
            cStmtObject.registerOutParameter(5,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(5);
            conn1.commit();
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
    public int deletePremium(ArrayList<?> aldeletePremium) throws TTKException {
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
			conn.commit();
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
    public int savePremium(ArrayList<?> alPremium,String strPolicyMode,String strPolicyType,String strCheckedYN) throws TTKException {
        int iResult = 0;
        StringBuffer sbfSQL = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        CallableStatement cStmtObject2=null;
        CallableStatement cStmtObject3=null;
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
                        cStmtObject.setLong(1,0);
                    }//end of else

                    if(strPolicyMode.equalsIgnoreCase("ENM")){

                    	if(lngSeqID != null){
                    		cStmtObject.setLong(2,lngSeqID);
                    	}//end of if(lngSeqID != null)
                    	else{
                    		cStmtObject.setLong(2,0);
                    	}//end of else

                    	cStmtObject.setLong(3,0);
                    }//end of if(strPolicyMode.equalsIgnoreCase("ENM"))

                    if(strPolicyMode.equalsIgnoreCase("END")){

                    	cStmtObject.setLong(2,0);

                    	if(lngSeqID != null){
                    		cStmtObject.setLong(3,lngSeqID);
                    	}//end of if(lngSeqID != null)
                    	else{
                    		cStmtObject.setLong(3,0);
                    	}//end of else
                    }//end of if(strPolicyMode.equalsIgnoreCase("ENM"))

                    cStmtObject.setString(4,strPolicyMode);
                    cStmtObject.setString(5,strPolicyType);
                    cStmtObject.setInt(6,lngAddedBy.intValue());
                    cStmtObject.registerOutParameter(2,Types.BIGINT);
                    cStmtObject.registerOutParameter(7,Types.INTEGER);
                    cStmtObject.execute();
                    iResult = cStmtObject.getInt(7);
                }//end of if(strCheckedYN.equals("Y"))

            	else {
            		cStmtObject2 = (java.sql.CallableStatement)conn.prepareCall(strSavePremium);  	
            		
            		
            		for(int i=0;i<alPremium.size();i++){
            			
            		//	sbfSQL = new StringBuffer();
            			premiumVO = (PremiumInfoVO)alPremium.get(i);
            			
            			//Member Premium Related Log
            			bdTotalFlySumInsured = premiumVO1.getTotalFlySumInsured();
            			bdTotalFlyPremium = premiumVO1.getTotalFlyPremium();
            			bdFloaterSumInsured = premiumVO1.getFloaterSumInsured();
            			bdFloaterPremium = premiumVO1.getFloaterPremium();
            			selectregion=premiumVO1.getSelectregion();//added as per KOC 1284 Change request
            			
            			cStmtObject2.setLong(1, premiumVO.getMemberSeqID());
            			cStmtObject2.setString(2, premiumVO.getMemberPolicyTypeID());
            			if(premiumVO.getTotalSumInsured() != null){
            				cStmtObject2.setDouble(3, premiumVO.getTotalSumInsured().doubleValue());
            			}//end of if(premiumVO.getTotalSumInsured() == null)
            			else{
            				cStmtObject2.setDouble(3,0);
            			}//end of else
                        
            			if(premiumVO.getGrossPremium() != null){
            				cStmtObject2.setDouble(4, premiumVO.getGrossPremium().doubleValue());
            			}//end of if(premiumVO.getGrossPremium() == null)
            			else{
            				cStmtObject2.setDouble(4,0);
            			}//end of else
            			if(premiumVO.getCumulativeBonusAmt() != null){
            				cStmtObject2.setDouble(5, premiumVO.getCumulativeBonusAmt().doubleValue());
            			}//end of if(premiumVO.getCumulativeBonusAmt() == null)
            			else{
            				cStmtObject2.setDouble(5,0);
            			}//end of else
            			if(premiumVO.getProRataPremium() != null){
            				cStmtObject2.setDouble(6, Double.parseDouble(premiumVO.getProRataPremium().toString()));
            			}//end of if(premiumVO.getCumulativeBonusAmt()== null)
            			else{
            				cStmtObject2.setDouble(6,0);
            			}//end of else
            			if(premiumVO.getNetPremium() != null){
            				cStmtObject2.setDouble(7, premiumVO.getNetPremium().doubleValue());
            			}//end of if(premiumVO.getNetPremium()== null)
            			else{
            				cStmtObject2.setDouble(7,0);
            			}//end of else
            			cStmtObject2.setLong(8, premiumVO.getSeqID());
            			cStmtObject2.setString(9, strPolicyMode);
            			cStmtObject2.setString(10, strPolicyType);
            			cStmtObject2.setLong(11, premiumVO.getUpdatedBy());
            			cStmtObject2.execute();
            			
            			

            			/*//Group Premium Related Log
            			sbfSQL = sbfSQL.append("'"+premiumVO.getMemberSeqID()+"',");
            			sbfSQL = sbfSQL.append("'"+premiumVO.getMemberPolicyTypeID()+"',");

            			
            			if(premiumVO.getTotalSumInsured() == null){
            				sbfSQL = sbfSQL.append(""+null+",");
            			}//end of if(premiumVO.getTotalSumInsured() == null)
            			else{
            				sbfSQL = sbfSQL.append("'"+premiumVO.getTotalSumInsured()+"',");
            			}//end of else
                        
            			
            			if(premiumVO.getGrossPremium()== null){
            				sbfSQL = sbfSQL.append(""+null+",");
            			}//end of if(premiumVO.getTotalPremium()== null)
            			else{
            				sbfSQL = sbfSQL.append("'"+premiumVO.getGrossPremium()+"',");
            			}
            			
            		//	if(premiumVO.getTotalPremium()== null){
            		//		sbfSQL = sbfSQL.append(""+null+",");
            		//	}//end of if(premiumVO.getTotalPremium()== null)
            		//	else{
            		//		sbfSQL = sbfSQL.append("'"+premiumVO.getTotalPremium()+"',");
            		//	}//end of else comented for pro rata premium cr

            			
            			if(premiumVO.getCumulativeBonusAmt()== null){
            				sbfSQL = sbfSQL.append(""+null+",");
            			}//end of if(premiumVO.getCumulativeBonusAmt()== null)
            			else{
            				sbfSQL = sbfSQL.append("'"+premiumVO.getCumulativeBonusAmt()+"',");
            			}//end of else
            			
            			if(premiumVO.getProRataPremium()== null){
            				sbfSQL = sbfSQL.append(""+null+",");
            			}//end of if(premiumVO.getCumulativeBonusAmt()== null)
            			else{
            				sbfSQL = sbfSQL.append("'"+premiumVO.getProRataPremium()+"',");
            			}//end of else
            			
            			if(premiumVO.getNetPremium()== null){
            				sbfSQL = sbfSQL.append(""+null+",");
            			}//end of if(premiumVO.getCumulativeBonusAmt()== null)
            			else{
            				sbfSQL = sbfSQL.append("'"+premiumVO.getNetPremium()+"',");
            			}//end of else
            			
            			sbfSQL = sbfSQL.append("'"+premiumVO.getSeqID()+"',");//PolicySeqID/EndorsementSeqID
            			sbfSQL = sbfSQL.append("'"+strPolicyMode+"',");
            			sbfSQL = sbfSQL.append("'"+strPolicyType+"',");
            			sbfSQL = sbfSQL.append("'"+premiumVO.getUpdatedBy()+"')}");
            			stmt.addBatch(strSavePremium+sbfSQL.toString());*/
            		}//end of for

            		
            		
            		
            		cStmtObject3 = (java.sql.CallableStatement)conn.prepareCall(strSaveGroupPremium);
            		if(lngPolicyGrpSeqID !=0){
            			cStmtObject3.setLong(1,lngPolicyGrpSeqID);
            		}//end of if(lngPolicyGrpSeqID !=0)
            		else{
            			cStmtObject3.setLong(1,0);
            		}//end of else

            		if(bdTotalFlySumInsured != null){
            			cStmtObject3.setDouble(2,bdTotalFlySumInsured.doubleValue());
            		}//end of if(bdFloaterSumInsured != null)
            		else{
            			cStmtObject3.setDouble(2,0);
            		}//end of else

            		if(bdTotalFlyPremium != null){
            			cStmtObject3.setDouble(3,bdTotalFlyPremium.doubleValue());
            		}//end of if(bdTotalFlyPremium != null)
            		else{
            			cStmtObject3.setDouble(3,0);
            		}//end of else

            		if(bdFloaterSumInsured != null){
            			cStmtObject3.setDouble(4,bdFloaterSumInsured.doubleValue());
            		}//end of if(bdFloaterSumInsured != null)
            		else{
            			cStmtObject3.setDouble(4,0);
            		}//end of else

            		if(bdFloaterPremium != null){
            			cStmtObject3.setDouble(5,bdFloaterPremium.doubleValue());
            		}//end of if(bdFloaterPremium != null)
            		else{
            			cStmtObject3.setDouble(5,0);
            		}//end of else

            		//added as per kOC 1284 Change request
            		if(bdFloaterPremium != null){
            			cStmtObject3.setString(6,selectregion);
            		}//end of if(bdFloaterPremium != null)
            		else{
            			cStmtObject3.setString(6,null);
            		}//end of else
        			
        			//added as per kOC 1284 Change Request
        		
            		if(lngSeqID != null){
            			cStmtObject3.setLong(7,lngSeqID);
            		}//end of if(lngSeqID != null)
            		else{
            			cStmtObject3.setLong(7,0);
            		}//end of else
            		cStmtObject3.setString(8,strPolicyMode);
            		cStmtObject3.setString(9,strPolicyType);
            		cStmtObject3.setLong(10,lngAddedBy);
            		cStmtObject3.registerOutParameter(2,Types.DOUBLE);
            		cStmtObject3.registerOutParameter(3,Types.DOUBLE);
            		cStmtObject3.registerOutParameter(5,Types.DOUBLE);
            		cStmtObject3.registerOutParameter(11,Types.INTEGER);  //rows_processed
            		cStmtObject3.execute();
            		iResult = cStmtObject3.getInt(11);
            		
            	}//end of else
         	}//end of if(alPremium.size() >0)
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
					log.error("Error while closing the Statement in MemberDAOImpl savePremium()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if Statement is not closed, control reaches here. Try closing the Callablestatement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
						if (cStmtObject2 != null) cStmtObject2.close();
						if (cStmtObject3 != null) cStmtObject3.close();
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
				cStmtObject2=null;
				cStmtObject3=null;
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
            conn.commit();
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
						//added for Policy Deductable - KOC-1277  
                        if(domiciliaryVO.getDedLimit()==null)
                        {
                        	sbfSQL = sbfSQL.append("'"+0+"',");
                        }
                        else
                        {
                        	sbfSQL = sbfSQL.append("'"+domiciliaryVO.getDedLimit()+"',");
                        }
                        sbfSQL = sbfSQL.append("'"+domiciliaryVO.getMemberDeductableYN()+"',");
                        
                        if(domiciliaryVO.getOverallfamilyDeductableLimit()==null)
                        {
                        	sbfSQL = sbfSQL.append("'"+0+"',");
                        }
                        else
                        {
                        	sbfSQL = sbfSQL.append("'"+domiciliaryVO.getOverallfamilyDeductableLimit()+"',");
                        }
                        sbfSQL = sbfSQL.append("'"+domiciliaryVO.getOverallFamilyCheckYN()+"',");
                        //ended
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
            cStmtObject.registerOutParameter(4,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(4);
            conn.commit();
            if(rs != null){
                while(rs.next()){
                    domiciliaryVO = new DomiciliaryVO();
                    if(rs.getLong("MEMBER_SEQ_ID") != 0){
                        domiciliaryVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
                    }//end of if(rs.getString("MEMBER_SEQ_ID") != null)
                    domiciliaryVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                    domiciliaryVO.setDomiciliaryTypeID(TTKCommon.checkNull(rs.getString("DOMICILARY_GENERAL_TYPE_ID")));
                    domiciliaryVO.setDomiciliaryTypeDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                    domiciliaryVO.setDomiciliaryLimit(TTKCommon.checkNull(rs.getString("DOMICILARY_LIMIT")));
                    domiciliaryVO.setOverallfamilyLimit(TTKCommon.checkNull(rs.getString("FAMILY_LIMIT")));
                    domiciliaryVO.setDomHospAmt(TTKCommon.checkNull(rs.getString("DOMICILARY_HOSP_LIMIT")));
					 //added for Policy Deductable - KOC-1277

                    domiciliaryVO.setPolicyDeductableTypeId(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));//added for Flag (Floater/Non-Floater);
                    domiciliaryVO.setPolicyDeductableLimit(TTKCommon.checkNull(rs.getString("POLICY_DEDUCTABLE_AMT")));
                    
                    domiciliaryVO.setOverallfamilyDeductableLimit(TTKCommon.checkNull(rs.getString("FAMILY_AMT_LIMIT")));
                    
                    domiciliaryVO.setMemberDeductableYN(TTKCommon.checkNull(rs.getString("MEMBER_DEDUCT_YN")));
                    domiciliaryVO.setOverallFamilyCheckYN(TTKCommon.checkNull(rs.getString("FAMILY_DEDUCT_YN")));
                    
                    
                    
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
			conn.commit();
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
            cStmtObject.registerOutParameter(2,Types.OTHER);
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
			conn.commit();
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
                cStmtObject.setLong(1,0);
            }//end of else
            cStmtObject.setString(2, memberVO.getHospCashBenefitsYN());
            cStmtObject.setString(3, memberVO.getConvCashBenefitsYN());
            //added for koc 1278
            cStmtObject.setString(4, memberVO.getPersonalWaitingPeriodYN());
            //added for koc 1278
			cStmtObject.setString(5, memberVO.getreductWaitingPeriodYN());
            cStmtObject.setString(6, memberVO.getSumInsuredBenefitYN());//added for policy restoration
          //added for KOC-1273
	     	cStmtObject.setString(7, memberVO.getCriticalBenefitYN());	  	
            cStmtObject.registerOutParameter(8, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.registerOutParameter(1, Types.BIGINT);
            cStmtObject.execute();
            conn.commit();
            iResult = cStmtObject.getInt(8);//ROWS_PROCESSED
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
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
            	while(rs.next()){
            		memberVO = new MemberVO();
            		memberVO.setHospCashBenefitsYN(TTKCommon.checkNull(rs.getString("hosp_cash_benefit_yn")));
            		memberVO.setConvCashBenefitsYN(TTKCommon.checkNull(rs.getString("conv_cash_benefit_yn")));
            		//added for koc 1278
            		memberVO.setPersonalWaitingPeriodYN(TTKCommon.checkNull(rs.getString("PERS_WAT_PRD_YN")));
            		//added for KOC-1273
        			memberVO.setCriticalBenefitYN(TTKCommon.checkNull(rs.getString("critical_cash_benefit_yn")));
            		//added for koc 1278
                    memberVO.setreductWaitingPeriodYN(TTKCommon.checkNull(rs.getString("reduct_in_waiting_period_yn")));//KOC Reduction In Waiting Period
        		    memberVO.setSumInsuredBenefitYN(TTKCommon.checkNull(rs.getString("sum_insured_rest_benefit_yn")));//KOC 1270 policy restoration 
        		
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
    
    public  String getPolicyStatus(Long Policy_Seq_id) throws TTKException{
        long Policy_Seq_id1 = Policy_Seq_id.longValue(); 
    	int iResult=0;
    	String policy_status =null;
    	Connection conn = null;
    	ResultSet rs = null;
    	CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strRenewalPolicyStatus);
    		cStmtObject.setLong(1,Policy_Seq_id1);
    		cStmtObject.registerOutParameter(2,Types.OTHER);
    		cStmtObject.execute();
    		rs = (java.sql.ResultSet)cStmtObject.getObject(2);
    		conn.commit();
    		if(rs.next())
    		{
    		policy_status =rs.getString("INS_STATUS_GENERAL_TYPE_ID");
    		}
    	
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
    	return policy_status;
    }
    
    public Reader getMemberXML(ArrayList<String> allData) throws TTKException
    {
    	Connection conn=null;
    	CallableStatement cStmtObject = null;
    	Reader xmlFileReader=null;
             try 
             {
				conn=ResourceManager.getConnection();
				cStmtObject=conn.prepareCall(strgetMemberXML);
				cStmtObject.setString(1,allData.get(0));
				cStmtObject.setString(2,allData.get(1));
				cStmtObject.setString(3,allData.get(2));
				cStmtObject.registerOutParameter(4,Types.SQLXML);
				cStmtObject.execute();
				if(cStmtObject.getSQLXML(4)!=null)
				xmlFileReader=cStmtObject.getSQLXML(4).getCharacterStream();
				
				//xmlFileReader=rs.getCharacterStream(2);
				/*while(xmlFileReader.read()>0)
				{
					char j=(char)xmlFileReader.read();
					
					System.out.print(j);
					
				}*/
				
	           // xml = XMLType.createXML(cStmtObject.getOPAQUE(2));
	            //System.out.println("xml data=="+xml.getLength());
				return xmlFileReader;
			 }
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
        
		
    	
    }
    
    public List<HaadWebServiceVo> getMemberHaadXML(ArrayList<String> allData) throws TTKException
	{
		
		Connection conn = null;
		List<HaadWebServiceVo> 	uploadList  = new ArrayList<HaadWebServiceVo>();
		HaadWebServiceVo haadwebservicebj = null;
		OracleCallableStatement cStmtObject = null;

		OracleResultSet rs1=null; 
		try{
			conn = ResourceManager.getConnection();

			conn=ResourceManager.getConnection();
			cStmtObject=(OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strgetMemberXML);
			cStmtObject.setString(1,allData.get(0));
			cStmtObject.setString(2,allData.get(1));
			cStmtObject.setString(3,allData.get(2));
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			
			rs1 =  (OracleResultSet) cStmtObject.getObject(4);
			while( rs1.next()){

				haadwebservicebj = new HaadWebServiceVo();

				
				if(rs1.getString("FILE_SEQ_ID")!=null){

					haadwebservicebj.setFileSeqID(rs1.getLong("FILE_SEQ_ID"));
				}

				if(rs1.getString("FILE_NAME")!=null)
				{
					haadwebservicebj.setFileName(rs1.getString("FILE_NAME"));   
				}

				if(rs1.getCharacterStream("RA_FILE")!=null)
				{
					//dhpowebservicebj.setFileContent((rs1.getOPAQUE("RA_FILE").getBytes()));
					haadwebservicebj.setXmlFileReader(rs1.getCharacterStream("RA_FILE"));	
				}

				if(rs1.getString("SETTLEMENT_NUMBER")!=null)
				{
					haadwebservicebj.setClaimsettlementnumber(rs1.getString("SETTLEMENT_NUMBER"));  
				}

				if(rs1.getString("CLAIM_SEQ_ID")!=null)
				{
					haadwebservicebj.setClaimSeqId(rs1.getLong("CLAIM_SEQ_ID"));
				}

				if(rs1.getString("SENDER_ID")!=null)
				{
					haadwebservicebj.setSenderID(rs1.getString("SENDER_ID"));
				}
				
				
				uploadList.add(haadwebservicebj);
			}

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "communication");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "communication");
		}//end of catch (Exception exp)
		finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the Statement
			{
				try
				{
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MaintenanceDAOIMplImpl getRemittanceAdviceDHPOFileUpload()",sqlExp);
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
						log.error("Error while closing the Statement in MaintenanceDAOImpl getRemittanceAdviceDHPOFileUpload()",sqlExp);
						throw new TTKException(sqlExp, "communication");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{

							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in MaintenanceDAOImpl getRemittanceAdviceDHPOFileUpload()",sqlExp);
							throw new TTKException(sqlExp, "communication");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of try
			}
			catch (TTKException exp)
			{
				throw new TTKException(exp, "communication");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{ 
				rs1=null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally

		return uploadList;
	}

    
    
    public void DHPOUplodedMembers(StringBuilder uploadedMemberData) throws TTKException
    {
    	Connection conn=null;
    	CallableStatement cStmtObject = null;
         
             try 
             {
				conn=ResourceManager.getConnection();
				cStmtObject=conn.prepareCall(strUpdateDHPOsUplodedMembers);
				cStmtObject.setString(1,uploadedMemberData.toString());
				cStmtObject.registerOutParameter(2,Types.INTEGER);
				cStmtObject.execute();
				conn.commit();
			 }
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
             
    }
    
    public void DHPOLogFileNames(ArrayList<String> alinfo) throws TTKException
    {
    	Connection conn=null;
    	CallableStatement cStmtObject = null;
    	
             try 
             {
				conn=ResourceManager.getConnection();
				cStmtObject=conn.prepareCall(strDHPOLogDetails);
				cStmtObject.setLong(1,0);
				cStmtObject.setString(2,alinfo.get(0));
				cStmtObject.setString(3,alinfo.get(1));
				cStmtObject.setString(4,alinfo.get(2)); 
				cStmtObject.setString(5,alinfo.get(3));
				cStmtObject.setString(6,alinfo.get(4));
				cStmtObject.registerOutParameter(1,Types.BIGINT);
				cStmtObject.registerOutParameter(7,Types.INTEGER);
				cStmtObject.execute();
				conn.commit();
			 }
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
    }
    
    public ArrayList getMemberLogFileList(ArrayList alSearchCriteria) throws TTKException
    {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		Collection<Object> alResultList = new ArrayList<Object>();
	
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectDHPOLogDetails);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			/*cStmtObject.setLong(4,(Long)alSearchCriteria.get(3));*/
			cStmtObject.setString(4,(String)alSearchCriteria.get(4));
			cStmtObject.setString(5,"added_date");
			cStmtObject.setString(6,"DESC");
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
		/*	cStmtObject.setLong(19,(Long)alSearchCriteria.get(14));*/
			cStmtObject.registerOutParameter(9,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(9);
			if(rs != null){
				int SNO=0;
				while(rs.next()){
					SNO++;
					DHPOMemberUploadVO dhpoMemberUploadVO = new DHPOMemberUploadVO();
					dhpoMemberUploadVO.setSno(SNO);
					dhpoMemberUploadVO.setFileName(TTKCommon.checkNull(rs.getString("mem_upd_log_file_name")));
					dhpoMemberUploadVO.setInsurenceCompany(TTKCommon.checkNull(rs.getString("ins_comp_name")));
					dhpoMemberUploadVO.setInsurenceCompanyCode(TTKCommon.checkNull(rs.getString("ins_comp_code_number")));
					dhpoMemberUploadVO.setUploadedDate(rs.getString("added_date"));
					dhpoMemberUploadVO.setRegulatoryAuthority(rs.getString("authority"));
					alResultList.add(dhpoMemberUploadVO);
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
	
   	 
    }
    
    public ArrayList<String> getInsuranceCompanyCount() throws TTKException
    {
    	Connection conn=null;
    	PreparedStatement pStatement=null;
    	ArrayList<String> alResultList=new ArrayList<String>();
    	ResultSet rs = null;
    	try{
    		conn= ResourceManager.getConnection();
    		pStatement=conn.prepareStatement(strDHPOInsuranceCompanyDetail);
    		rs=pStatement.executeQuery();
    		
    		if(rs != null){
				while(rs.next()){
					alResultList.add(TTKCommon.checkNull(rs.getString("ins_comp_code_number")));
				}//end of while(rs.next())
    	}
    		return alResultList;
    	}
    	catch (Exception exp)
		{
			throw new TTKException(exp, "member");
		}//end of catch (Exception exp)
		finally
		{
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStatement != null) pStatement.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
				pStatement = null;
				conn = null;
			}//end of finally
		}//end of finally

    }

    public ArrayList getInsuranceCompanyCredentials(String insCode) throws TTKException
    {
    	Connection conn=null;
    	PreparedStatement pStatement=null;
    	ArrayList alResultList=new ArrayList<>();
    	ResultSet rs = null;
    	try{
    		conn= ResourceManager.getConnection();
    		pStatement=conn.prepareStatement(strDHPOInsuranceCompanyCredentials);
    		pStatement.setString(1,insCode);
    		rs=pStatement.executeQuery();
    		
    		if(rs != null){
				while(rs.next()){
					alResultList.add(TTKCommon.checkNull(rs.getString("ins_user_name")));
					alResultList.add(TTKCommon.checkNull(rs.getString("ins_password")));
				}//end of while(rs.next())
    	}
    		return alResultList;
    	}
    	catch (Exception exp)
		{
			throw new TTKException(exp, "member");
		}//end of catch (Exception exp)
		finally
		{
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStatement != null) pStatement.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
				pStatement = null;
				conn = null;
			}//end of finally
		}//end of finallU
    }
    
    public Reader getMemberXMlDataForMailAttacment(ArrayList<String> alldata) throws TTKException
    {
    	Connection conn=null;
    	CallableStatement cStmtObject = null;
    	Reader xmlFileReader=null;
    
             try 
             {
				conn=ResourceManager.getConnection();
				cStmtObject=conn.prepareCall(strgetMemberXMLDataForMailAttachment);
				cStmtObject.setString(1,alldata.get(0));
				cStmtObject.setString(2,alldata.get(1));
				cStmtObject.setString(3,alldata.get(2));
				cStmtObject.setString(4,alldata.get(3));
				cStmtObject.registerOutParameter(5,Types.SQLXML);
				//cStmtObject.execute();
				cStmtObject.execute();
				if(cStmtObject.getSQLXML(5)!=null)
				xmlFileReader=cStmtObject.getSQLXML(5).getCharacterStream();
				
				//xmlFileReader=rs.getCharacterStream(2);
				/*while(xmlFileReader.read()>0)
				{
					char j=(char)xmlFileReader.read();
					
					System.out.print(j);
					
				}*/
			
	           // xml = XMLType.createXML(cStmtObject.getOPAQUE(2));
	            //System.out.println("xml data=="+xml.getLength());
				conn.commit();
			 }
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
        
		return xmlFileReader;
    	
    }
    
    
    public ArrayList<Object> getReciverMailList(ArrayList<Object> alSearchCriteria) throws TTKException
    {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		Collection<Object> alResultList = new ArrayList<Object>();
	
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strSearchCriteriaOnMailList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			 cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			 cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));
			
			cStmtObject.registerOutParameter(10,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);
			if(rs != null){
				while(rs.next()){
					ReciverMailList reciverMailList = new ReciverMailList();
					reciverMailList.setReciverSeqID(rs.getLong("ins_tgr_mail_seq"));
					reciverMailList.setInsuranceCode(rs.getString("ins_comp_code_number"));
					reciverMailList.setInsuranceCompany(rs.getString("ins_comp_name"));
					reciverMailList.setName(rs.getString("name"));
					reciverMailList.setPrimaryEmailId(rs.getString("primary_mail_id"));
					reciverMailList.setOfficePhone(rs.getString("office_number"));
					reciverMailList.setRegulatoryAuthority(rs.getString("authority"));
					reciverMailList.setImageName("DeleteIcon");
					reciverMailList.setImageTitle("Delete");
					reciverMailList.setDeleteTitle("Delete");
					alResultList.add(reciverMailList);
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
	
   	 
    }   
    
    public Long saveReciverMailData(ReciverMailIdVO reciverMailIdVO) throws TTKException    {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
	
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveReciverMailID);
			if(reciverMailIdVO.getReciverSeqID()!=null) cStmtObject.setLong(1,reciverMailIdVO.getReciverSeqID());
			else cStmtObject.setLong(1,0);
			cStmtObject.setString(2,reciverMailIdVO.getOfficeCode());
			cStmtObject.setString(3,reciverMailIdVO.getReciverName());
			cStmtObject.setString(4,reciverMailIdVO.getDesignation());
			cStmtObject.setString(5,reciverMailIdVO.getPrimaryEmailID());
			cStmtObject.setString(6,reciverMailIdVO.getSecondaryEmailID());
			if(reciverMailIdVO.getReciverStatus().equals("on") || reciverMailIdVO.getReciverStatus().equals("Y")) cStmtObject.setString(7,"Y");
			else cStmtObject.setString(7,"N");
			if(TTKCommon.checkNull(reciverMailIdVO.getOfficePhone()).length()>0)
			cStmtObject.setLong(8,Long.parseLong(reciverMailIdVO.getOfficePhone()));
			else 
			cStmtObject.setLong(8,0);
			cStmtObject.setString(9,reciverMailIdVO.getRegulatoryAuthority());
			cStmtObject.setLong(10,reciverMailIdVO.getAddedBY());
			
			cStmtObject.registerOutParameter(1,Types.NUMERIC);
			cStmtObject.registerOutParameter(11,Types.INTEGER);
			cStmtObject.execute();
			
			conn.commit();
			
			return cStmtObject.getBigDecimal(1).longValue();
		
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
	
   	 
    }
    
    public ReciverMailIdVO getReciverMailData(Long insSeqId) throws TTKException
    {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ReciverMailIdVO reMailIdVO=new ReciverMailIdVO();
	
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetReciverMailID);
			cStmtObject.setLong(1,insSeqId);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while(rs.next()){
					reMailIdVO.setReciverSeqID(rs.getLong("ins_tgr_mail_seq"));  
					reMailIdVO.setOfficeCode(rs.getString("ins_comp_code_number"));
					reMailIdVO.setCompanyName(rs.getString("ins_comp_name"));
					reMailIdVO.setReciverName(rs.getString("name"));
					reMailIdVO.setPrimaryEmailID(rs.getString("primary_mail_id"));
					reMailIdVO.setSecondaryEmailID(rs.getString("alternate_mail_id"));
					reMailIdVO.setDesignation(rs.getString("designation"));
					reMailIdVO.setReciverStatus(rs.getString("active"));
					reMailIdVO.setOfficePhone(rs.getString("office_number"));
					reMailIdVO.setRegulatoryAuthority(rs.getString("authority"));
				}//end of while(rs.next())
			}//end of if(rs != null)
			
			return reMailIdVO;
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
	
   	 
    }
    
    
    public ArrayList<Object> getAllReciverMailList() throws TTKException
    {
    	Connection conn = null;
		PreparedStatement pStmtObject=null;
		ResultSet rs = null;
		Collection<Object> alResultList = new ArrayList<Object>();
	
		try{
			conn = ResourceManager.getConnection();
			pStmtObject=conn.prepareStatement("select *from TPA_INS_TRIGGER_MAIL_CONFIG ");
			rs=pStmtObject.executeQuery();
			if(rs != null){
				while(rs.next()){
					ReciverMailList reciverMailList = new ReciverMailList();
					reciverMailList.setReciverSeqID(rs.getLong("ins_tgr_mail_seq"));
					reciverMailList.setInsuranceCode(rs.getString("ins_comp_code_number"));
					reciverMailList.setInsuranceCompany("");
					reciverMailList.setName(rs.getString("name"));
					reciverMailList.setPrimaryEmailId(rs.getString("primary_mail_id"));
					reciverMailList.setOfficePhone(rs.getString("alternate_mail_id"));
					reciverMailList.setImageName("DeleteIcon");
					reciverMailList.setImageTitle("Delete");
					reciverMailList.setDeleteTitle("Delete");
					alResultList.add(reciverMailList);
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmtObject != null) pStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
				pStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	
   	 
    }
   
    
    
    public int deleteReciverMailList(Long seqID) throws TTKException
    {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
	
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteReciverMailData);
			cStmtObject.setLong(1,seqID);
			//cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(2,Types.INTEGER);
			cStmtObject.execute();
			conn.commit();
			return cStmtObject.getInt(2);
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
	
   	 
    }
    public void updateMAilTrigger(ArrayList<String> allData) throws TTKException
    {
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
	
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strupdateMailTrigger);
			cStmtObject.setString(1,allData.get(0));
			cStmtObject.setString(2,allData.get(4));
			cStmtObject.setString(3,allData.get(1));
			cStmtObject.setString(4,allData.get(2));
			cStmtObject.execute();
			conn.commit();
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
	
   	 
    }
    
    public ArrayList<String> getAuthorityInsCompCodeList() throws TTKException
    {
    	Connection conn = null;
		PreparedStatement cStmtObject=null;
		ResultSet rs = null;
	    ArrayList<String> alresult=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareStatement("SELECT DISTINCT C.AUTHORITY,C.INS_COMP_CODE_NUMBER FROM APP.TPA_INS_TRIGGER_MAIL_CONFIG C where C.ACTIVE=?");
			cStmtObject.setString(1,"Y");
			rs=cStmtObject.executeQuery();
			alresult=new ArrayList<String>();
			while(rs.next())
			{
				alresult.add(rs.getString("AUTHORITY")+"|"+rs.getString("INS_COMP_CODE_NUMBER"));
			}
		
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ClaimDAOImpl getClaimList()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimDAOImpl getClaimList()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getClaimList()",sqlExp);
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
	
   	 return alresult;
    }

    public String getMemberRelation(String strSeqID) throws TTKException {


		Connection conn = null;
		PreparedStatement statement=null;
		ResultSet rs = null;
		String relation="";
			
			String query;
			query="select rc.relship_description from app.tpa_enr_policy_member pm join app.tpa_relationship_code rc on(pm.relship_type_id=rc.relship_type_id) where pm.member_seq_id=?";
			
				try {
					conn=ResourceManager.getConnection();
					statement=conn.prepareStatement(query);
					statement.setLong(1, Long.parseLong(strSeqID));
					rs=statement.executeQuery();
				       while(rs.next()){
				    	   relation=rs.getString("relship_description");
				    	  }
			
			return relation;
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MemberDAOImpl getMemberRelation()",sqlExp);
					throw new TTKException(sqlExp, "member");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (statement != null) statement.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in MemberDAOImpl getMemberRelation()",sqlExp);
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
							log.error("Error while closing the Connection in MemberDAOImpl getMemberRelation()",sqlExp);
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
				statement = null;
				conn = null;
			}//end of finally
		}//end of finally
	}

	public ArrayList getAuditLogsList(ArrayList<Object> searchData,String strSeqID,String reforward) throws TTKException{

		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		Collection<Object> alResultList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();
			
			if("groupMember".equals(reforward)){
				
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAuditLogs);
			}
			else{
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAuditMemberLogs);
			}
			
			cStmtObject.setLong(1,Long.parseLong(strSeqID));
			
			 if(!"".equals(TTKCommon.checkNull(searchData.get(0)))){
				 cStmtObject.setString(2,(String)searchData.get(0));//Field Name
	            }
			 else{
					cStmtObject.setString(2,null);
				}//end of else
				
		//	cStmtObject.setString(2,(String)searchData.get(0));//Field Name
			cStmtObject.setString(3,(String)searchData.get(1));//modified data from
			cStmtObject.setString(4,(String)searchData.get(2));//modified data to
			cStmtObject.setString(5,(String)searchData.get(3));//User Name
			cStmtObject.setString(6,(String)searchData.get(4));//user role
	/*		 if(!"".equals(TTKCommon.checkNull((String)searchData.get(4)))){
				 cStmtObject.setLong(6,Long.parseLong((String)searchData.get(4)));//user role
	            }
			 else{
					 cStmtObject.setLong(6, 0);
		}*/
			cStmtObject.setString(7,(String)searchData.get(5));//cust endorsement num
			cStmtObject.setLong(8,(long)searchData.get(6));//added by
			//cStmtObject.setString(9,(String)searchData.get(7));   DESC
			cStmtObject.setString(9,"added_date");
			//cStmtObject.setString(10,(String)searchData.get(8));
			cStmtObject.setString(10,"DESC");
			cStmtObject.setString(11,(String)searchData.get(9));
			cStmtObject.setString(12,(String)searchData.get(10));
			cStmtObject.registerOutParameter(2,Types.VARCHAR);
			cStmtObject.registerOutParameter(13,Types.OTHER);
			cStmtObject.execute();
			
			rs = (java.sql.ResultSet)cStmtObject.getObject(13);
			conn.commit();
			if(rs != null){
				while(rs.next()){
					DohLogfileVO dohMemberUploadVO = new DohLogfileVO();
					
					dohMemberUploadVO.setFieldname(TTKCommon.checkNull(rs.getString("modified_fields")));
					//dohMemberUploadVO.setValueBeforeModification(TTKCommon.checkNull(rs.getString("actual_value")));   Font.BOLD
					
					StringBuilder finalString=new StringBuilder();
					String mfNames="";
					String avNames="";
					mfNames = rs.getString("modified_fields");
					//String fieldname= "<b>"+actualName+"</b>";
					avNames = rs.getString("actual_value");
					if(mfNames!=null&&avNames!=null){
						
					String[] mfNamesArr=mfNames.split(";");
					String[] avNamesArr=avNames.split(";");
					
					
					if(mfNamesArr.length==avNamesArr.length){
					for (int i=0;i<mfNamesArr.length;i++) {
					    
					    finalString.append(avNamesArr[i].replace("Field","<b>"+ mfNamesArr[i]+"</b>"));					  
					    
					}
					}
					}
					if(mfNames!=null&&avNames!=null){
					dohMemberUploadVO.setValueBeforeModification(finalString.toString());
					}
					else{
						dohMemberUploadVO.setValueBeforeModification(TTKCommon.checkNull(rs.getString("actual_value")));
					}
					
					dohMemberUploadVO.setDateOfModification(TTKCommon.checkNull(rs.getString("added_date")));
					
					dohMemberUploadVO.setCustEndorsementNo(TTKCommon.checkNull(rs.getString("cust_endorsement_number")));
					dohMemberUploadVO.setModifiedUserName(TTKCommon.checkNull(rs.getString("user_id")));
					dohMemberUploadVO.setModifiedUserRole(TTKCommon.checkNull(rs.getString("role_name")));
					
					alResultList.add(dohMemberUploadVO);
				
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
			
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in MemberDAOImpl getAuditLogsList()",sqlExp);
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
						log.error("Error while closing the Statement in MemberDAOImpl getAuditLogsList()",sqlExp);
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
							log.error("Error while closing the Connection in MemberDAOImpl getAuditLogsList()",sqlExp);
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
	}
	
public ArrayList<Object> getInsCompnyList(String authority)throws TTKException {
		
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
       ArrayList<CacheObject> alInsList = new ArrayList<CacheObject>();
       CacheObject cacheObject = null;
       ArrayList<Object> allList = new ArrayList<Object>();
     String  query = "SELECT INS_COMP_CODE_NUMBER, INS_COMP_NAME as INS_COMP_NAME ,INS_SEQ_ID FROM APP.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' and PAYER_AUTHORITY in "+authority+" ORDER BY INS_COMP_NAME";      
       try {
  			 
    	   con=ResourceManager.getConnection();
    		statement=con.prepareStatement(query);
    			// statement.setString(1, authority); 
    			  resultSet=statement.executeQuery();
    	   
    			  while(resultSet.next()){
    				  
    				  cacheObject = new CacheObject();
    				  cacheObject.setCacheId((resultSet.getString("INS_SEQ_ID")));
                      cacheObject.setCacheDesc(TTKCommon.checkNull(resultSet.getString("INS_COMP_NAME")));
                      alInsList.add(cacheObject);
    				  
    			  }
    	   
    			  allList.add(alInsList);
    	    		
    	    	  return allList;
	
}
       
       catch (SQLException sqlExp)
       {
           throw new TTKException(sqlExp, "product");
       }	
       catch (Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch (Exception exp)
   	
       finally
		{
			/* Nested Try Catch to ensure resource closure */ 
			try // First try closing the result set
			{
				try
				{
					if (resultSet != null) resultSet.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in ProductDAOImpl getInsCompnyList()",sqlExp);
					throw new TTKException(sqlExp, "product");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (statement != null) statement.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in ProductDAOImpl getInsCompnyList()",sqlExp);
						throw new TTKException(sqlExp, "product");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(con != null) con.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in ProductDAOImpl getInsCompnyList()",sqlExp);
							throw new TTKException(sqlExp, "product");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "product");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				resultSet = null;
				statement = null;
				con = null;
			}//end of finally
		}//end of finally
       
	}//end getInsCompnyList	

public ArrayList getMemberType() throws TTKException{
//    ResultSet rs = null;
    CacheObject cacheObject = null;
    ArrayList<Object> alMemberType = new ArrayList<Object>();
    try(Connection conn1=ResourceManager.getConnection();
    		PreparedStatement pStmt =conn1.prepareStatement("SELECT C.MEMBER_TYPE_ID,(C.MEM_TYPE_DESC||'('||C.MEMBER_TYPE_ID||')') AS MEM_TYPE_DESC FROM APP.TPA_MEMBER_TYPE_CODE C ORDER BY C.MEMBER_TYPE_ID");){
        //pStmt.setString(1,strAbbrCode);
        try(ResultSet rs = pStmt.executeQuery();){
        	if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("MEMBER_TYPE_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("MEM_TYPE_DESC")));
                    alMemberType.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
        }
        return alMemberType;
    }//end of try
    catch (SQLException sqlExp)
    {
        throw new TTKException(sqlExp, "member");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp)
    {
        throw new TTKException(exp, "member");
    }//end of catch (Exception exp)
	
}

public ArrayList getEntityAndFeeType(String type) throws TTKException {
//  ResultSet rs = null;
  CacheObject cacheObject = null;
  String selectQury=null;
  if("Entity".equals(type))
	  selectQury="SELECT TC.ENTITY_TYPE_CODE,TC.ENTITY_TYPE_DESC FROM APP.TPA_MEM_ENTITY_TYPE_CODE TC where tc.entity_type_code in (1,2) ORDER BY TC.ENTITY_TYPE_CODE";
  else
	  selectQury="SELECT T.TYPE_CODE,T.TYPE_DESC FROM APP.TPA_FEE_TYPE_CODE T ORDER BY T.TYPE_CODE";
  ArrayList<Object> alMemberType = new ArrayList<Object>();
  try(Connection conn1=ResourceManager.getConnection();
  		PreparedStatement pStmt =conn1.prepareStatement(selectQury);){
      //pStmt.setString(1,strAbbrCode);
      try(ResultSet rs = pStmt.executeQuery();){
      	if(rs != null){
              while(rs.next()){
                  cacheObject = new CacheObject();
                  if("Entity".equals(type)){
                	  cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("ENTITY_TYPE_CODE")));
                      cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("ENTITY_TYPE_DESC")));
                  }else{
                	  cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("TYPE_CODE")));
                      cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("TYPE_DESC")));
                  }
                  
                  alMemberType.add(cacheObject);
              }//end of while(rs.next())
          }//end of if(rs != null)
      }
      return alMemberType;
  }//end of try
  catch (SQLException sqlExp)
  {
      throw new TTKException(sqlExp, "member");
  }//end of catch (SQLException sqlExp)
  catch (Exception exp)
  {
      throw new TTKException(exp, "member");
  }//end of catch (Exception exp)
 
}


public List<Reader> getMemberXMlDataForMailAttacmentNew(ArrayList<String> alldata) throws TTKException{
	Connection conn=null;
	CallableStatement cStmtObject = null;
	//Reader xmlFileReader;
	List<Reader> readerList=new ArrayList<>(); 
	 ResultSet rs = null;
         try 
         {
			conn=ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strgetMemberXMLDataForMailAttachmentNew);
			cStmtObject.setString(1,alldata.get(0));
			cStmtObject.setString(2,alldata.get(1));
			cStmtObject.setString(3,alldata.get(2));
			cStmtObject.setString(4,alldata.get(3));
			cStmtObject.setString(5,alldata.get(4));
			cStmtObject.registerOutParameter(6,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(6);
			
			if(rs != null){
				while(rs.next()){
					
				   String mem_xml_seq_id = rs.getString("mem_xml_seq_id");
					Reader xmlFileReader = rs.getSQLXML("mem_xml").getCharacterStream();
					readerList.add(xmlFileReader);
					
				}
				}
			conn.commit();
		 }
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
    	try // First try closing the Statement
		{
			try
			{
				if (cStmtObject != null) cStmtObject.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Statement in MemberDAOImpl getMemberXMlDataForMailAttacmentNew()",sqlExp);
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
					log.error("Error while closing the Connection in MemberDAOImpl getMemberXMlDataForMailAttacmentNew()",sqlExp);
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
    
	return readerList;
	
}
public List< Object[]> getNewMemberXML(ArrayList<String> allData) throws TTKException{
	List< Object[]> resultData=new ArrayList<>();
         try(Connection connection=ResourceManager.getConnection();
        	 CallableStatement cStmtObject =connection.prepareCall(strNewMemberXML);){
        	cStmtObject.setString(1,allData.get(0));
 			cStmtObject.registerOutParameter(2,Types.OTHER);
 			cStmtObject.execute();
 			try(ResultSet resultSet=(ResultSet)cStmtObject.getObject(2)){
// 				int count=0;
 				while(resultSet.next()){
 					Object[] resultSetData=new Object[3];
 					/*if(count%2==0){
 						resultSetData[0]="UPDATE";
 					}else*/
 					resultSetData[0]=resultSet.getString("FLAG");
 					resultSetData[1]=resultSet.getSQLXML("MEM_XML").getCharacterStream();
 					resultSetData[2]=resultSet.getString("MEM_XML_SEQ_ID");
 					resultData.add(resultSetData);
// 					count++;
 				}
 			}
         }
        catch (SQLException sqlExp)
     	{
     		throw new TTKException(sqlExp, "member");
     	}//end of catch (SQLException sqlExp)
     	catch (Exception exp)
     	{
     		throw new TTKException(exp, "member");
     	}//end of catch (Exception exp)
         return resultData;
}

public void updateDhpoMembers(ArrayList list) throws TTKException{
    try(Connection connection=ResourceManager.getConnection();
    CallableStatement cStmtObject=connection.prepareCall(strnewUpdateMembers);){
       cStmtObject.setString(1,list.get(0).toString());
       cStmtObject.setString(2,list.get(1).toString());
	   cStmtObject.registerOutParameter(3,Types.INTEGER);
	   cStmtObject.execute();
	   connection.commit();
	}catch (SQLException sqlExp)
     	{
     		throw new TTKException(sqlExp, "member");
     	}//end of catch (SQLException sqlExp)
     	catch (Exception exp)
     	{
     		throw new TTKException(exp, "member");
     	}//end of catch (Exception exp)
}

public ArrayList selectOccupationCode(ArrayList alSearchCriteria)
		throws TTKException {
	Collection<Object> alResultList = new ArrayList<Object>();
	Connection conn = null;
	CallableStatement cStmtObject = null;
	ResultSet rs = null;
	MemberDetailVO memberDetaliVO = null;
	try {
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn
				.prepareCall(strOccupationCodeList);
		cStmtObject.setString(1, (String) alSearchCriteria.get(0));
		cStmtObject.setString(2, (String) alSearchCriteria.get(1));
		cStmtObject.registerOutParameter(3, Types.OTHER);
		cStmtObject.execute();
		rs = (java.sql.ResultSet) cStmtObject.getObject(3);
		if (rs != null) {
			while (rs.next()) {
				memberDetaliVO= new MemberDetailVO();
				
				memberDetaliVO.setOccupationCode(TTKCommon.checkNull(rs.getString("designation_type_id")));
				memberDetaliVO.setOccuCodeDesc(TTKCommon.checkNull(rs.getString("designation_description")));
				alResultList.add(memberDetaliVO);
			}// end of while(rs.next())
		}// end of if(rs != null)
		conn.commit();
		return (ArrayList) alResultList;
	}// end of try
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "occupationcode");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "occupationcode");
	}// end of catch (Exception exp)
	finally {
		/* Nested Try Catch to ensure resource closure */
		try // First try closing the result set
		{
			try {
				if (rs != null)
					rs.close();
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Resultset in MemberDAOImpl doSelectOccupationCode()",
						sqlExp);
				throw new TTKException(sqlExp, "occupationcode");
			}// end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches
					// here. Try closing the statement now.
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in MemberDAOImpl doSelectOccupationCode()",
							sqlExp);
					throw new TTKException(sqlExp, "occupationcode");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null)
							conn.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in MemberDAOImpl doSelectOccupationCode()",
								sqlExp);
						throw new TTKException(sqlExp, "occupationcode");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
		}// end of try
		catch (TTKException exp) {
			throw new TTKException(exp, "occupationcode");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
		{
			rs = null;
			cStmtObject = null;
			conn = null;
		}// end of finally
	}// end of finally
}
private static final String strGetSumInsured ="select case when c.enrol_type_id='IND' then round(tipp.prod_sum_insured,2)::numeric else round(c.suminsuredpermembercorp::numeric,2) end as  prod_sum_insured FROM app.tpa_enr_policy_member A JOIN app.tpa_enr_policy_group B ON (A.policy_group_seq_id  = B.policy_group_seq_id) JOIN app.tpa_enr_policy C ON (B.policy_seq_id = C.policy_seq_id) JOIN app.tpa_general_code d ON (c.policy_sub_general_type_id = d.general_type_id)     left outer join app.tpa_ins_product tipp on (tipp.product_seq_id=c.product_seq_id) WHERE c.policy_seq_id= ?";  
public ArrayList getSumInsured(Long lngPolicySeqID) throws TTKException
{
	Connection conn = null;
	PreparedStatement pStmtObject=null;
	ResultSet rs=null;
	ArrayList al = new ArrayList<>();
	Double sumInsured= null;
	try {
	    	conn=ResourceManager.getConnection();
	    	pStmtObject=conn.prepareStatement(strGetSumInsured);
	    	if(lngPolicySeqID != null)
	    		pStmtObject.setLong(1,lngPolicySeqID);
	    	else
	    		pStmtObject.setLong(1,0);
	    	rs=pStmtObject.executeQuery();
	    	while(rs.next())
	    	{
	    		sumInsured = rs.getDouble("prod_sum_insured");
	    		System.out.println("SumInsured from db ->"+sumInsured);
	    		if(sumInsured != null)
	    			al.add(sumInsured);
	    		else
	    			al.add(0);
	    	}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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
				log.error("Error while closing the Resultset in MemberDAOImpl getSumInsured()",sqlExp);
				throw new TTKException(sqlExp, "member");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmtObject != null) pStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in MemberDAOImpl getSumInsured()",sqlExp);
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
						log.error("Error while closing the Connection in MemberDAOImpl getSumInsured()",sqlExp);
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
			pStmtObject = null;
			conn = null;
		}//end of finally
    }
	return al;
  }	// end of getSumInsured()	
}//end of MemberDAOImpl
