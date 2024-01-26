/**
 * @ (#) OnlineAccessDAOImpl.java Jul 19, 2007
 * Project 	     : TTK HealthCare Services
 * File          : OnlineAccessDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 19, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.onlineforms;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.dom4j.Document;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;
import org.omg.CORBA.Request;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.hospital.ClaimDetailVO;
import com.ttk.dto.hospital.HospitalizationDetailVO;
import com.ttk.dto.administration.TDSCateRateDetailVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.displayOfBenefits.BenefitsDetailsVO;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.DocumentDetailVO;
import com.ttk.dto.empanelment.GradingServicesVO;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.empanelment.LaboratoryServicesVO;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.onlineforms.DependentDetailVO;
import com.ttk.dto.hospital.AdditionalDetailVO;
import com.ttk.dto.hospital.ClaimantVO;
import com.ttk.dto.hospital.HospPreAuthVO;
import com.ttk.dto.hospital.HospitalPreAuthDetailVO;
import com.ttk.dto.hospital.PreAuthHospitalVO;
import com.ttk.dto.onlineforms.InsFileUploadVO;
import com.ttk.dto.onlineforms.EmployeeFileUplodedVO;
import com.ttk.dto.onlineforms.MemberCancelVO;
import com.ttk.dto.onlineforms.MemberPermVO;
import com.ttk.dto.onlineforms.OnlineAssistanceDetailVO;
import com.ttk.dto.onlineforms.OnlineAssistanceVO;
import com.ttk.dto.onlineforms.OnlineChangePasswordVO;
import com.ttk.dto.onlineforms.OnlineHospitalVO;
import com.ttk.dto.onlineforms.OnlineInsPolicyVO;
import com.ttk.dto.onlineforms.OnlineIntimationVO;
import com.ttk.dto.onlineforms.OnlinePolicyInfoVO;
import com.ttk.dto.onlineforms.OnlineQueryVO;
import com.ttk.dto.onlineforms.SumInsuredVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.CashlessVO;
import com.ttk.dto.preauth.CashlessDetailVO;
import com.ttk.dto.usermanagement.AdditionalInfoVO;
import com.ttk.dto.usermanagement.ContactVO;
//import com.ttk.dto.preauth.PreAuthVO;
//import com.ttk.dto.security.GroupVO;
import com.ttk.dto.usermanagement.PasswordVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;
import com.ttk.dto.usermanagement.UserAccessVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.dto.hospital.HospitalDetailsVo;

import org.apache.log4j.Logger;
import org.bouncycastle.ocsp.Req;

public class OnlineAccessDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(OnlineAccessDAOImpl.class);
	private static final String strSaveFileUploadDetails="{CALL ONLINE_ENROLMENT_PKG.SAVE_UPLOAD_FILE(?,?,?,?,?,?,?,?,?,?,?,?)}";//koc1352
	private static final String strGetFileUploadList="{CALL ONLINE_ENROLMENT_PKG.SELECT_UPD_INFO(?,?,?,?,?,?)}";//koc 1352
	private static final String strOnlinePolicyList = "{CALL ONLINE_ENROLMENT_PKG.SELECT_POLICY_LIST(?,?,?,?,?,?,?)}";
	private static final String strOnlinePolicySearchList = "{CALL ONLINE_ENROLMENT_PKG.select_policy_search_list(?,?,?,?,?,?,?,?,?)}";
	private static final String strPolicyDetail = "{CALL ONLINE_ENROLMENT_PKG.CREATE_POLICY_DTL_XML(?,?,?)}";
	private static final String strMemberList = "{CALL ONLINE_ENROLMENT_PKG.SELECT_MEMBER_LIST(?,?)}";
	private static final String strEnrollmentList ="{CALL ONLINE_ENROLMENT_PKG.SELECT_ENROLLMENT_LIST(?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPreAuthHistoryList = "{CALL ONLINE_ENROLMENT_PKG.CREATE_PREAUTH_XML(?,?,?)}";
	private static final String strPolicyHistoryList = "{CALL ONLINE_ENROLMENT_PKG.CREATE_POLICY_XML(?,?)}";
	private static final String strClaimHistoryList = "{CALL ONLINE_ENROLMENT_PKG.CREATE_CLAIM_XML(?,?,?)}";
	private static final String strOnlineEnrollmentList = "{CALL ONLINE_ENROLMENT_PKG.GET_ONLINE_ENROLLMENT_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strChangePassword = "{CALL ONLINE_ENROLMENT_PKG.CHANGE_PASSWORD(?,?,?,?,?,?)}";
	// Change added for KOC1227I added one parameter
	//private static final String strOnlineAccessHome = "{CALL ONLINE_ENROLMENT_PKG.SELECT_WEBLOGIN_HOME_INFO(?,?,?,?,?,?,?,?,?)}";

	private static final String strOnlineAccessHome = "{CALL ONLINE_ENROLMENT_PKG_SELECT_WEBLOGIN_HOME_INFO(?,?,?,?,?,?,?,?,?,?)}";//added 1 parameter for IBM

	//newly added code -- CHANGED FOR KOC-1216
		//private static final String strSaveEnrollment = "{CALL ONLINE_ENROLMENT_PKG.SAVE_ENROLLMENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	//Added for IBM....4
		private static final String strSaveEnrollment = "{CALL ONLINE_ENROLMENT_PKG.SAVE_ENROLLMENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//2 extra parameter added 1 for Optin/out 2nd for emailId2 and 1 for email checkbox
	//Ended

		//private static final String strSelectEnrollment = "{CALL ONLINE_ENROLMENT_PKG.SELECT_ENROLLMENT(?,?,?)}";

		//Added for IBM
		private static final String strSelectEnrollment = "{CALL ONLINE_ENROLMENT_PKG.SELECT_ENROLLMENT(?,?,?,?,?,?)}"; // added 3 extra parameters V_FLAG for windowsperiod, V_PRE_CLAM_YN for preauth/claim existence,V_OPT_ALLOWED for Dynamic weblogin
	//Ended...
    // Change for KOC1227C ADDED ONE PARAMETER
	//start modified again as per KOC 1160 and 1159 on 20.NOV 2012
	//private static final String strSaveDependent = "{CALL ONLINE_ENROLMENT_PKG.SAVE_DEPENDENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveDependent = "{CALL ONLINE_ENROLMENT_PKG.SAVE_DEPENDENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"; //added 1 Parameter for IBM Declaration
	// end modified again as per KOC 1160 and 1159 on 20.NOV 2012
	private static final String strSelectDependent = "{CALL ONLINE_ENROLMENT_PKG.SELECT_DEPENDENT(?,?)}";
	private static final String strSelectDependentList = "{CALL ONLINE_ENROLMENT_PKG.SELECT_DEPENDENT_LIST(?,?,?)}";

	private static final String strSelectSumInsuredPlan = "{CALL ONLINE_ENROLMENT_PKG.SELECT_SUM_INSURED_PLAN(?,?,?,?,?)}";
	// Change added for KOC1184 prorata
	private static final String strSelectSumInsuredPlanInfo = "{CALL ONLINE_ENROLMENT_PKG.GET_SUM_INSURED_PLAN(?,?,?,?,?)}";
		// Change for KOC1227C ADDED ONE PARAMETER
	private static final String strSaveMemInsured = "{CALL ONLINE_ENROLMENT_PKG.SAVE_MEM_INSURED(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteAdditionalSum = "{CALL ONLINE_ENROLMENT_PKG.DELETE_ADDITIONAL_SUM(?,?,?,?,?)}";
	private static final String strSaveCancellation = "{CALL ONLINE_ENROLMENT_PKG.SAVE_CANCELLATION(?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectCancellation = "{CALL ONLINE_ENROLMENT_PKG.SELECT_CANCELLATION(?,?,?)}";
	private static final String strLocation = "{CALL ONLINE_ENROLMENT_PKG.SELECT_CORPORATE_LOCATION(?,?)}";

	private static final String strFieldInfo = "{CALL ONLINE_ENROLMENT_PKG.SELECT_FIELD_INFO(?,?,?)}";
	private static final String strDeleteGeneral = "{CALL ONLINE_ENROLMENT_PKG.DELETE_GENERAL(?,?,?,?,?)}";
	private static final String strSelectEmpPasswordInfo = "{CALL ONLINE_ENROLMENT_PKG.SELECT_PASSWORD(?,?)}";
    //private static final String strResetEmpPassword = "{CALL ONLINE_ENROLMENT_PKG.RESET_PASSWORD(?,?,?)}";
    private static final String strResetEmpPassword = "{CALL ? := ONLINE_ENROLMENT_PKG.RESET_PASSWORD(?,?)}";
    private static final String strPolicyNumber = "SELECT A.POLICY_SEQ_ID,A.POLICY_NUMBER FROM TPA_ENR_POLICY A JOIN TPA_GROUP_REGISTRATION B ON(A.GROUP_REG_SEQ_ID=B.GROUP_REG_SEQ_ID) WHERE B.GROUP_ID=? AND A.COMPLETED_YN='Y' AND A.DELETED_YN='N'";
    private static final String strPolicyNumberBro = "SELECT c.policy_seq_id,c.policy_number FROM tpa_login_info Aa JOIN tpa_user_contacts a ON (aa.contact_seq_id = a.contact_seq_id) LEFT OUTER JOIN app.tpa_group_user_assoc ua on ( ua.contact_seq_id=aa.contact_seq_id and ua.contact_seq_id=?) LEFT OUTER JOIN tpa_enr_policy c ON (c.BROKER_GROUP_ID = ua.group_branch_seq_id) JOIN tpa_group_registration B ON (c.group_reg_seq_id = b.group_reg_seq_id) LEFT OUTER JOIN weblogin_config d ON (c.policy_seq_id = d.policy_seq_id) WHERE (aa.user_id = ?) AND c.completed_yn ='Y' ORDER BY c.policy_seq_id DESC";

    //added for Accenture KOC-1255
    private static final String strSelectedPolicyNumber="select pol.policy_seq_id,pol.policy_number from app.tpa_group_registration reg,app.tpa_enr_policy pol where pol.group_reg_seq_id=reg.group_reg_seq_id and sysdate between pol.effective_from_date and pol.effective_to_date and reg.group_id=? AND POL.DELETED_YN='N' AND POL.COMPLETED_YN='Y'";

   //private static final String strPolicyNumberInfo = "SELECT A.POLICY_SEQ_ID,A.POLICY_NUMBER,A.EFFECTIVE_FROM_DATE,A.EFFECTIVE_TO_DATE,B.GROUP_NAME FROM TPA_ENR_POLICY A JOIN TPA_GROUP_REGISTRATION B ON(A.GROUP_REG_SEQ_ID=B.GROUP_REG_SEQ_ID) LEFT OUTER JOIN TPA_ENR_POLICY C ON (A.POLICY_SEQ_ID = C.PREV_POLICY_SEQ_ID) WHERE B.GROUP_ID=? AND A.ENROL_TYPE_ID='COR' AND C.POLICY_SEQ_ID IS NULL AND A.COMPLETED_YN='Y' AND A.DELETED_YN='N' ORDER BY B.GROUP_NAME ASC,EFFECTIVE_FROM_DATE DESC";
   //changed for cr KOC1129A
   //private static final String strPolicyNumberInfo = "SELECT A.POLICY_SEQ_ID,A.POLICY_NUMBER,A.EFFECTIVE_FROM_DATE,A.EFFECTIVE_TO_DATE,B.GROUP_NAME FROM TPA_ENR_POLICY A JOIN TPA_GROUP_REGISTRATION B ON(A.GROUP_REG_SEQ_ID=B.GROUP_REG_SEQ_ID) LEFT OUTER JOIN TPA_ENR_POLICY C ON (A.POLICY_SEQ_ID = C.PREV_POLICY_SEQ_ID) WHERE B.GROUP_ID=? AND A.ENROL_TYPE_ID='COR' AND A.COMPLETED_YN='Y' AND A.DELETED_YN='N' ORDER BY B.GROUP_NAME ASC,EFFECTIVE_FROM_DATE DESC";
   //as per Hospital Login 
	private static final String strHospitalForgotPassword ="{CALL HOSPITAL_PKG.FORGOT_PASSWORD(?,?,?)}";
    private static final String strPolicyNumberInfo = "SELECT A.POLICY_SEQ_ID,A.POLICY_NUMBER,A.EFFECTIVE_FROM_DATE,A.EFFECTIVE_TO_DATE,B.GROUP_NAME FROM TPA_ENR_POLICY A JOIN TPA_GROUP_REGISTRATION B ON (A.GROUP_REG_SEQ_ID = B.GROUP_REG_SEQ_ID) WHERE A.ENROL_TYPE_ID = 'COR' AND A.POLICY_STATUS_GENERAL_TYPE_ID != 'POC' AND A.DELETED_YN = 'N' AND A.COMPLETED_YN = 'Y' AND B.GROUP_ID = ? AND A.POLICY_SEQ_ID NOT IN (SELECT UNIQUE NVL(A.PREV_POLICY_SEQ_ID, 0) FROM TPA_ENR_POLICY A JOIN TPA_GROUP_REGISTRATION B ON (A.GROUP_REG_SEQ_ID = B.GROUP_REG_SEQ_ID) WHERE A.ENROL_TYPE_ID = 'COR' AND A.POLICY_STATUS_GENERAL_TYPE_ID != 'POC' AND A.DELETED_YN = 'N' AND A.COMPLETED_YN = 'Y' AND B.GROUP_ID = ?) "; //added by srikrishna and changed by ram for IBM
    private static final String strGetRelationshipCode = "{CALL ONLINE_ENROLMENT_PKG.SELECT_RELSHIP_LIST(?,?,?)}";
    private static final String strHospitalList = "{CALL ONLINE_ENROLMENT_PKG.SELECT_HOSPITAL_LIST(?,?,?,?,?,?,?,?,?,?)}";
    private static final String strPatIntimationList = "{CALL ONLINE_ENROLMENT_PKG.PAT_INTIMATION_LIST(?,?,?,?)}";
    private static final String strClmIntimationList = "{CALL ONLINE_ENROLMENT_PKG.CLM_INTIMATION_LIST(?,?,?,?)}";
    private static final String strPatIntimationSave = "{CALL ONLINE_ENROLMENT_PKG.PAT_INTIMATION_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strClaimIntimationSave = "{CALL ONLINE_ENROLMENT_PKG.CLM_INTIMATION_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strSaveIntimationHospital = "{CALL ONLINE_ENROLMENT_PKG.PAT_INTIMATION_HOSP_SAVE(";
    private static final String strGetPreauthIntimationDetail = "{CALL ONLINE_ENROLMENT_PKG.PAT_INTIMATION_DTL(?,?)}";
    private static final String strGetClaimIntimationDetail = "{CALL ONLINE_ENROLMENT_PKG.CLM_INTIMATION_DTL(?,?)}";
    private static final String strDeleteIntimation="{CALL ONLINE_ENROLMENT_PKG.INTIMATION_DELETION(?,?,?,?)}";
    private static final String strOnlineMember = "SELECT A.MEMBER_SEQ_ID,A.TPA_ENROLLMENT_ID || ' / ' ||A.MEM_NAME AS MEMBER_NAME FROM TPA_ENR_POLICY_MEMBER A WHERE A.POLICY_GROUP_SEQ_ID=? AND A.DELETED_YN='N'";
    private static final String strInsEnrollmentList = "{CALL ONLINE_ENROLMENT_PKG.INS_ENROLLMENT_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strBroEnrollmentList = "{CALL ONLINE_ENROLMENT_PKG.INS_ENROLLMENT_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strOnlineQueryList = "{CALL ONLINE_ENROLMENT_PKG.SELECT_ONLINE_QUERY_LIST(?,?)}";
    private static final String strOnlineQueryHeaderInfo = "{CALL ONLINE_ENROLMENT_PKG.SELECT_QUERY_HEADER(?,?,?,?)}";
    private static final String strOnlineQueryInfo = "{CALL ONLINE_ENROLMENT_PKG.SELECT_QUERY_DETAILS(?,?)}";
    private static final String strSaveOnlineQueryInfo = "{CALL ONLINE_ENROLMENT_PKG.SAVE_QUERY_HEADER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
//    private static final String strForgotPassword ="{CALL ONLINE_ENROLMENT_PKG.FORGOT_PASSWORD(?,?,?,?)}";
//Changed as per IBM CR
    //private static final String strForgotPassword ="{CALL ONLINE_ENROLMENT_PKG.FORGOT_PASSWORD(?,?,?,?)}";
    private static final String strForgotPassword ="{CALL ONLINE_ENROLMENT_PKG.FORGOT_PASSWORD(?,?,?,?,?,?)}";
    private static final String strForgotPasswordBroker ="{CALL ONLINE_ENROLMENT_PKG.FORGOT_PASSWORD_BROKER(?,?)}";
    //Changed as per IBM CR
  private static final String strEmpContactInfo ="{CALL ONLINE_ENROLMENT_PKG.SELECT_EMP_CONTACT_INFO(?,?,?,?)}";
   // private static final String strConnection ="Connection";
   // Change added for KOC1127B
   private static final String strACCPolicyNumberInfo = "SELECT A.POLICY_SEQ_ID,A.POLICY_NUMBER,A.EFFECTIVE_FROM_DATE,A.EFFECTIVE_TO_DATE,B.GROUP_NAME FROM TPA_ENR_POLICY A JOIN TPA_GROUP_REGISTRATION B ON (A.GROUP_REG_SEQ_ID=B.GROUP_REG_SEQ_ID) LEFT OUTER JOIN TPA_ENR_POLICY C ON (A.POLICY_SEQ_ID = C.PREV_POLICY_SEQ_ID) WHERE B.GROUP_ID= ? AND A.ENROL_TYPE_ID='COR' AND C.POLICY_SEQ_ID IS NULL AND A.COMPLETED_YN='Y' AND A.DELETED_YN='N' AND A.SUB_GROUP_ID='1' ORDER BY B.GROUP_NAME ASC,EFFECTIVE_FROM_DATE DESC";


   //ins file upload
   private static final String strInsCompFileUploadDetails = "{CALL ONLINE_ENROLMENT_PKG.SAVE_INS_COMP_FILE_UPLOAD(?,?,?,?,?,?,?)}";
   private static final String strGetInsCompFileUploadList = "{CALL ONLINE_ENROLMENT_PKG.SELECT_INS_COMP_FILE_INFO(?,?,?,?,?,?)}";
   //ins file upload
   
   //selffund
   private static final String strValidateEnrollID 		="{CALL HOSP_DIAGNOSYS_PKG_VALIDATE_ENROLLMENT_ID(?,?,?,?,?,?,?)}";
   private static final String strTestsForDC 			= "{CALL HOSP_DIAGNOSYS_PKG.SELECT_DIAGNOSYS_TEST_LIST(?,?)}";
   //private static final String strDiaglistInfo 		= "SELECT tdcd.diag_test_seq_id,tdcd.hosp_seq_id,tdcd.test_name,tdcd.rate,tdcd.discount,tdcd.discount FROM  app.tpa_diagnosys_center_dtls tdcd WHERE tdcd.diag_test_seq_id in (?)"; //added by kishor for selffund
   private static final String strDiaglistInfo 			= "{CALL HOSP_DIAGNOSYS_PKG.SELECT_DIAG_TEST(?,?)}"; //added by kishor for selffund
   private static final String strsaveDiagTestAmnts 	= "{CALL HOSP_DIAGNOSYS_PKG.SAVE_DIAGNOSYS_DETAILS(?,?,?,?,?,?,?,?,?)}"; //added by kishor for selffund
   private static final String strsSelectDiagTestDetails= "{CALL HOSP_DIAGNOSYS_PKG.SELECT_DIAGNOSYS_TEST_DETAILS (?,?)}"; //added by kishor for selffund
   private static final String strApproveDiagDetais 	= "{CALL HOSP_DIAGNOSYS_PKG.SUBMIT_DIAGNOSYS_DETAILS  (?,?,?,?,?,?,?,?,?,?,?)}"; //added by kishor for selffund
   private static final String strValidateOTP 			="{CALL HOSP_DIAGNOSYS_PKG.VALIDATE_OTP(?,?,?,?,?,?,?,?)}";
   private static final String strTotalAmtDetails 		="{CALL HOSP_DIAGNOSYS_PKG.SELECT_TOTAL_AMT_DETAILS(?,?)}";

   //intx
   private static final String strStateTypeIdList 		= "SELECT SC.STATE_TYPE_ID, SC.COUNTRY_ID AS COUNTRY_ID,SC.STD_CODE,CC.ISD_CODE FROM TPA_STATE_CODE SC JOIN TPA_COUNTRY_CODE CC ON(SC.COUNTRY_ID=CC.COUNTRY_ID) WHERE STATE_TYPE_ID=?";
   private static final String strCityTypeList 			= "SELECT CITY_TYPE_ID,CITY_DESCRIPTION FROM TPA_CITY_CODE WHERE STATE_TYPE_ID=?";
   private static final String strAddUpdateHospitalInfo = "{CALL hospital_empanel_pkg.SAVE_PROVIDER_REGISTER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
   private static final String strHospitalDetail 		= "SELECT A.HOSP_SEQ_ID,C.HOSP_GNRL_SEQ_ID,E.ADDR_SEQ_ID,A.EMPANEL_NUMBER,B.EMPANEL_STATUS_TYPE_ID,F.EMPANEL_DESCRIPTION,A.EMPANEL_TYPE_ID,A.HOSP_NAME,A.HOSP_TYPE_ID,A.REGIST_AUTHORITY,A.Hosp_Licenc_Numb,A.TRADE_LICENC_NUMB,E.ADDRESS_1,E.ADDRESS_2,E.ADDRESS_3,E.City_Type_Id,E.STATE_TYPE_ID,E.PIN_CODE,E.COUNTRY_ID,A.STD_CODE,a.isd_code,E.LANDMARKS,ttk_util_pkg.fn_decrypt(A.PRIMARY_EMAIL_ID) as PRIMARY_EMAIL_ID,A.OFF_PHONE_NO_1,A.OFF_PHONE_NO_2,A.OFFICE_FAX_NO,A.WEBSITE,a.PROVIDER_TYPE_ID AS DHA_PROVIDER_TYPE,A.PRIMARY_NETWORK FROM (((((((TPA_HOSP_INFO A LEFT OUTER JOIN TPA_HOSP_EMPANEL_STATUS B ON A.HOSP_SEQ_ID = B.HOSP_SEQ_ID AND B.active_yn = 'Y') LEFT OUTER JOIN TPA_HOSP_GENERAL_DTL C ON C.empanel_seq_id = B.empanel_seq_id) LEFT OUTER JOIN TPA_HOSP_ADDRESS E ON A.HOSP_SEQ_ID = E.HOSP_SEQ_ID) LEFT OUTER JOIN TPA_HOSP_EMPANEL_STATUS_CODE F ON B.EMPANEL_STATUS_TYPE_ID = F.EMPANEL_STATUS_TYPE_ID) LEFT OUTER JOIN TPA_OFFICE_INFO G ON A.TPA_OFFICE_SEQ_ID = G.TPA_OFFICE_SEQ_ID) LEFT OUTER JOIN TPA_HOSP_DISCREPANCY H ON A.HOSP_SEQ_ID = H.HOSP_SEQ_ID))  WHERE A.HOSP_LICENC_NUMB = ?";
   private static final String strOnlineProvDetails		=	"select m.HEALTH_AUTHORITY as authority,m.PROVIDER_name as provider,m.provider_id from app.dha_providers_master m where m.provider_id=?";
   private static final String strSaveUserContact		=	"{CALL CONTACT_PKG.SAVE_HOSP_CONTACTS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
   private static final String strgetUserContact		=	"SELECT c.contact_seq_id,c.user_general_type_id,c.hosp_seq_id,c.prefix_general_type_id,c.contact_name,c.active_yn,c.designation_type_id,c.primary_email_id,c.secondary_email_id,c.off_phone_no_1,c.mobile_no,c.std_code,c.isd_code,c.gender_general_type,c.age FROM app.tpa_user_contacts c WHERE c.hosp_seq_id=? and PROV_CONT_YN='Y'";
 //intx - Kishor
   private static final String strGetStdIsd				= "SELECT I.STD_CODE,I.ISD_CODE FROM APP.TPA_HOSP_INFO I WHERE I.HOSP_SEQ_ID=?";
   private static final String strGetMemberOnEnroll 	= "{CALL HOSP_DIAGNOSYS_PKG_GET_MEM_BENIFIT_DETAILS(?,?,?,?,?,?)}";
   private static final String strSaveMemberVitals 		= "{CALL HOSP_DIAGNOSYS_PKG.save_hosp_mem_vital_dtls(?,?,?,?,?,?,?,?,?,?,?)}";
//   private static final String strLabServices	 		= "SELECT MEDICAL_DETAIL.PRESCRIPTION_SEQ_ID,MEDICAL_CODE.MEDICAL_TYPE_ID,MEDICAL_CODE.DESCRIPTION,MEDICAL_CODE.MEDICAL_DESCRIPTION,CASE WHEN MEDICAL_CODE.HEADER_DESCRIPTION = 'LABORATORY' THEN NVL(MEDICAL_DETAIL.VALUE_1, '') ELSE NVL(MEDICAL_DETAIL.VALUE_1, 'N') END VALUE_1,CASE WHEN MEDICAL_CODE.HEADER_DESCRIPTION = 'LABORATORY' THEN  NVL(MEDICAL_DETAIL.VALUE_2, '') ELSE NVL(MEDICAL_DETAIL.VALUE_1, 'N') END VALUE_2 FROM (SELECT TO_NUMBER('') PRESCRIPTION_SEQ_ID,B.MEDICAL_TYPE_ID, A.DESCRIPTION,B.MEDICAL_DESCRIPTION,A.HEADER_DESCRIPTION,A.SORT_NO SORT_NO1,B.SORT_NO SORT_NO2,'N' VALUE_1,'N' VALUE_2 FROM TPA_HOSP_PRESCRIPTION_CODE A JOIN TPA_HOSP_PRESC_MEDICAL_CODE B ON (B.PRESCRIPTION_TYPE_ID = A.PRESCRIPTION_TYPE_ID) WHERE (A.HEADER_DESCRIPTION = 'LABORATORY')) MEDICAL_CODE,(SELECT PD.PRESCRIPTION_SEQ_ID,MC.MEDICAL_TYPE_ID,PC.DESCRIPTION,MC.MEDICAL_DESCRIPTION,PC.HEADER_DESCRIPTION,PC.SORT_NO SORT_NO1,MC.SORT_NO SORT_NO2,PD.VALUE_1,PD.VALUE_2 FROM TPA_HOSP_PRESCRIPTION_CODE PC JOIN TPA_HOSP_PRESC_MEDICAL_CODE MC ON (MC.PRESCRIPTION_TYPE_ID = PC.PRESCRIPTION_TYPE_ID) LEFT OUTER JOIN TPA_HOSP_PRESCRIPTION_DETAILS PD ON (PD.MEDICAL_TYPE_ID = MC.MEDICAL_TYPE_ID) WHERE (PC.HEADER_DESCRIPTION = ?) AND (PD.HOSP_SEQ_ID = ?)) MEDICAL_DETAIL WHERE MEDICAL_CODE.MEDICAL_TYPE_ID = MEDICAL_DETAIL.MEDICAL_TYPE_ID(+) ORDER BY MEDICAL_CODE.SORT_NO1 ASC, MEDICAL_CODE.SORT_NO2 ASC";
   private static final String strLabServices			= "select mc.activity_code,mc.service_type,mc.sub_service_type,mc.medical_description from APP.TPA_HOSP_PRESC_MED_CODE mc where service_type=?";
   private static final String strSaveDownloadHistory	= "{CALL HOSPITAL_PKG_PROV_RPT_DWNL_HISTORY(?,?,?,?,?,?)}";
   private static final String strGetMemberOnEnrollNew 	= "{CALL HOSP_DIAGNOSYS_PKG_GET_MEM_DETAILS(?,?)}";
   private static final String strGetTOBForBenefir	 	= "{CALL HOSP_DIAGNOSYS_PKG_GET_BENIFIT_COPAY_DEDU(?,?,?,?)}";
 //as per Partner Login 
 	private static final String strPartnerForgotPassword ="{CALL PARTNER_PKG.FORGOT_PASSWORD_PARTNER(?,?,?)}";
    private static final String strPartnerValidateEnrollId = "{CALL ONLINE_PARTNER_LOGIN.VALIDATE_ENROLLMENT_ID_MEM(?,?,?,?,?)}";
    private static final String strGetPartnerMemberOnEnroll = "{CALL ONLINE_PARTNER_LOGIN.GET_MEM_BENIFIT_DETAILS_PATR(?,?,?,?,?,?,?,?,?)}";
    private static final String strPartnerGetTOBForBenefir	 	= "{CALL ONLINE_PARTNER_LOGIN.GET_BENIFIT_COPAY_DEDU_PATR(?,?,?)}";
    private static final String strGetPolicyTobFile = "{CALL ONLINE_PARTNER_LOGIN.SELECT_POLICY_TOB_DOC(?,?)}";
    private static final String strGetbenefitDetails = "{CALL DISPLAY_OF_BENEFITS.SELECT_BENEFIT_LIMIT(?,?,?,?,?,?,?,?,?)}";

    private static final String strGetCheckEligibilityLog = "{CALL HOSP_DIAGNOSYS_PKG_save_check_eligiblelog(?,?,?,?,?)}";
    
    
    
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
                    onlinePolicyVO.setEmployeeAddYN(TTKCommon.checkNull(rs.getString("HR_EMPLOYEE_ADD_YN")));
                    if(rs.getString("MEMBER_SEQ_ID") != null){
                    	onlinePolicyVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
                    }//end of if(rs.getString("MEMBER_SEQ_ID") != null)
                    onlinePolicyVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
                    onlinePolicyVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
                    onlinePolicyVO.setPolicyStatusTypeID(TTKCommon.checkNull(rs.getString("POLICY_STATUS_GENERAL_TYPE_ID")));
                    onlinePolicyVO.setGroupCntCancelGenTypeID(TTKCommon.checkNull(rs.getString("GRP_CNT_CANCEL_GENERAL_TYPE_ID")));
                    if(alSearchCriteria.get(4).equals("B")){
                    onlinePolicyVO.setLocation(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));//kocbroker
                    onlinePolicyVO.setBroCompanyName(TTKCommon.checkNull(rs.getString("ins_comp_name")));//kocbroker
                    }
                    alResultList.add(onlinePolicyVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getOnlinePolicyList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getOnlinePolicyList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlinePolicyList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
		}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getOnlinePolicyList(ArrayList alSearchCriteria)





  //kocbroker
    public ArrayList getOnlinePolicySearchList(ArrayList alSearchCriteria) throws TTKException {
        Collection<Object> alResultList = new ArrayList<Object>();
        Connection conn = null;
        OracleCallableStatement oCstmt = null;
        OnlineAccessPolicyVO onlinePolicyVO = null;
        ResultSet rs = null;

        try{
            conn = ResourceManager.getConnection();
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strOnlinePolicySearchList);
            oCstmt.setString(1,(String)alSearchCriteria.get(0));
            oCstmt.setString(2,(String)alSearchCriteria.get(1));
            oCstmt.setString(3,(String)alSearchCriteria.get(2));
            oCstmt.setString(4,(String)alSearchCriteria.get(3));
            if(alSearchCriteria.get(4)!=null)
            {
                oCstmt.setLong(5,(Long)alSearchCriteria.get(4));
            }//end of if(alSearchCriteria.get(5)!=null)
            else
            {
                oCstmt.setString(5,null);
            }//end of else

            oCstmt.setString(6,(String)alSearchCriteria.get(5));//group Name

            oCstmt.setString(7,(String)alSearchCriteria.get(6));//START_NUM
            oCstmt.setString(8,(String)alSearchCriteria.get(7));//END_NUM

            oCstmt.registerOutParameter(9,OracleTypes.CURSOR);
            oCstmt.execute();
            rs = (java.sql.ResultSet)oCstmt.getObject(9);
            if(rs != null){
                while (rs.next()) {
                    onlinePolicyVO = new OnlineAccessPolicyVO();
                    if(rs.getString("POLICY_SEQ_ID") != null){
                        onlinePolicyVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_SEQ_ID") != null)
                    onlinePolicyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                      onlinePolicyVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
                    if(rs.getString("EFFECTIVE_FROM_DATE") != null){
                        onlinePolicyVO.setFromDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)
                    if(rs.getString("EFFECTIVE_TO_DATE") != null){
                        onlinePolicyVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

                    onlinePolicyVO.setEnrollmentType(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
                    onlinePolicyVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    onlinePolicyVO.setLoginType(TTKCommon.checkNull(rs.getString("policytype")));
                    onlinePolicyVO.setEmployeeAddYN(TTKCommon.checkNull(rs.getString("HR_EMPLOYEE_ADD_YN")));
                    if(rs.getString("MEMBER_SEQ_ID") != null){
                    	onlinePolicyVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
                    }//end of if(rs.getString("MEMBER_SEQ_ID") != null)
                    onlinePolicyVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
                    onlinePolicyVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
                    onlinePolicyVO.setPolicyStatusTypeID(TTKCommon.checkNull(rs.getString("POLICY_STATUS_GENERAL_TYPE_ID")));
                    onlinePolicyVO.setGroupCntCancelGenTypeID(TTKCommon.checkNull(rs.getString("GRP_CNT_CANCEL_GENERAL_TYPE_ID")));
                    onlinePolicyVO.setLocation(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));//kocbroker

                    alResultList.add(onlinePolicyVO);
               }//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList)alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getOnlinePolicyList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
				try
					{
						if (oCstmt != null) oCstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getOnlinePolicyList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlinePolicyList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
		}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				oCstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getOnlinePolicySearchList(ArrayList alSearchCriteria)



    /**Added for KOC-1255
     * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList getSelectedPolicyNumber(String strGroupID) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> alPolicyNbr = new ArrayList<Object>();
        CacheObject cacheObject = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt=conn.prepareStatement(strSelectedPolicyNumber);

            pStmt.setString(1,strGroupID);
            rs = pStmt.executeQuery();

            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();

                    cacheObject.setCacheId((rs.getString("POLICY_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    alPolicyNbr.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alPolicyNbr;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Connection in OnlineAccessDAOImpl getSelectedPolicyNumber()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getSelectedPolicyNumber()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getSelectedPolicyNumber()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getSelectedPolicyNumber(String strGroupID)




    /**
     * This method returns the XML Document of Policy Details which contains Policy Details
     * @param alPolicyList ArrayList object which contains PolicySeqID/MemberSeqID
     * @return Document of Policy Detail XML object
     * @exception throws TTKException
     */
    public Document getOnlinePolicyDetail(ArrayList alPolicyList) throws TTKException {
    	Connection conn = null;
        OracleCallableStatement cStmtObject = null;
        Document doc = null;
        XMLType xml = null;
        try
        {
            conn = ResourceManager.getConnection();
            if(alPolicyList != null){
            	if(alPolicyList.get(2) != null){
            		cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPolicyHistoryList);
                    cStmtObject.setLong(1,(Long)alPolicyList.get(2));//Member_Seq_ID
                    cStmtObject.registerOutParameter(2,OracleTypes.OPAQUE,"SYS.XMLTYPE");
                    cStmtObject.execute();
                    xml = XMLType.createXML(cStmtObject.getOPAQUE(2));
            	}//end of if(alPolicyList.get(1) != null)
            	else{
            		cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPolicyDetail);
                    cStmtObject.setLong(1,(Long)alPolicyList.get(0));//Policy_Seq_ID
                    cStmtObject.setString(2,(String)alPolicyList.get(1));//EMPLOYEE_NO
                    cStmtObject.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
                    cStmtObject.execute();
                    xml = XMLType.createXML(cStmtObject.getOPAQUE(3));
            	}//end of else
            }//end of if(alPolicyList != null)

            DOMReader domReader = new DOMReader();
            doc = xml != null ? domReader.read(xml.getDOM()):null;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl getOnlinePolicyDetail()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlinePolicyDetail()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of getOnlinePolicyDetail(ArrayList alPolicyList)

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
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
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
                    memberVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
                    alBatchPolicyList.add(memberVO);
                }// End of while (rs.next())
            }// End of if (rs!=null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getDependentList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getDependentList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getDependentList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
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
                cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));   //POLICY_SEQ_ID
            }//end of if(alSearchCriteria.get(0)!=null)
            else{
                cStmtObject.setString(1,null);
            }//end of else

            cStmtObject.setString(2,(String)alSearchCriteria.get(1)); //TPA_ENROLLMENT_NUMBER
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));//MEM_NAME
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));//DEPENDENT_NAME
            cStmtObject.setString(5,(String)alSearchCriteria.get(4));//EMPLOYEE_NO
            cStmtObject.setString(6,(String)alSearchCriteria.get(5));//ENM
            cStmtObject.setString(7,(String)alSearchCriteria.get(6));//CP
            cStmtObject.setString(8,(String)alSearchCriteria.get(7));//START_NUM
            cStmtObject.setString(9,(String)alSearchCriteria.get(8));//END_NUM
            cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(10);
            if (rs!=null)
            {
                while (rs.next())
                {
                    memberVO = new MemberVO();
                    memberVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID")!=null ? new Long(rs.getInt("POLICY_GROUP_SEQ_ID")):null);
                    memberVO.setName(TTKCommon.checkNull(rs.getString("ENROLLMENT")));
                    memberVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    memberVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    memberVO.setInsuredName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
                    memberVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getInt("POLICY_SEQ_ID")):null);
                    memberVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    memberVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
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
                    memberVO.setTemplateID(TTKCommon.checkNull(rs.getString("TEMPLATE_ID")));
                    memberVO.setTemplateName(TTKCommon.checkNull(rs.getString("TEMPLATE_NAME")));
                    memberVO.setEmpAddYN(TTKCommon.checkNull(rs.getString("HR_EMPLOYEE_ADD_YN")));
                    memberVO.setDependentAddYN(TTKCommon.checkNull(rs.getString("HR_DEPENDENT_ADD_YN")));
                    memberVO.setAllowCancYN(TTKCommon.checkNull(rs.getString("HR_ALLOW_CANCELLATION_YN")));
                    memberVO.setAllowModiYN(TTKCommon.checkNull(rs.getString("HR_ALLOW_MODIFICATION_YN")));
                    memberVO.setMemberTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
                    memberVO.setAllowAddSumYN(TTKCommon.checkNull(rs.getString("ALLOW_ADDITIONAL_SUM_YN")));
                    memberVO.setCancelYN(TTKCommon.checkNull(rs.getString("EMPLOYEE_CANCELLED_YN")));
                    memberVO.setExpiredYN(TTKCommon.checkNull(rs.getString("EXPIRED_YN")));
                    memberVO.setPolicyStatusTypeID(TTKCommon.checkNull(rs.getString("POLICY_STATUS_GENERAL_TYPE_ID")));
                    //added for IBM CR change
					if(rs.getString("DATE_OF_JOINING") != null){
					  memberVO.setDateOfJoining(new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
					}//end of if(rs.getString("DATE_OF_JOINING") != null)
					//ended
                    if(rs.getString("POLICY_DEFAULT_SUM_INSURED") != null){
                    	memberVO.setPolicySumInsured(new BigDecimal(rs.getString("POLICY_DEFAULT_SUM_INSURED")));
					}//end of if(rs.getString("POLICY_DEFAULT_SUM_INSURED") != null)

                    alBatchPolicyList.add(memberVO);
                }// End of while (rs.next())
            }// End of if (rs!=null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getMemberList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getMemberList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getMemberList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return (ArrayList<Object>) alBatchPolicyList;
    }//end of getMemberList(ArrayList alSearchCriteria)

    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, strEnrollmentID which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    public Document getHistory(String strHistoryType, Long lngSeqId, String strEnrollmentID)throws TTKException
    {
        Connection conn = null;
        OracleCallableStatement cStmtObject = null;
        Document doc = null;
        XMLType xml = null;

        try
        {
            conn = ResourceManager.getConnection();
            if(strHistoryType.equals("HPR"))
            {
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPreAuthHistoryList);
                cStmtObject.setLong(1,lngSeqId);//MEMBER_SEQ_ID
    			cStmtObject.setString(2,strEnrollmentID);// TPA_ENROLLMENT_ID
                cStmtObject.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
                cStmtObject.execute();
                xml = XMLType.createXML(cStmtObject.getOPAQUE(3));
            }
            else if(strHistoryType.equals("HCL"))
            {
            	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strClaimHistoryList);
                cStmtObject.setLong(1,lngSeqId);//MEM_SEQ_ID
                cStmtObject.setString(2,strEnrollmentID);// TPA_ENROLLMENT_ID
                cStmtObject.registerOutParameter(3,OracleTypes.OPAQUE,"SYS.XMLTYPE");
                cStmtObject.execute();
                xml = XMLType.createXML(cStmtObject.getOPAQUE(3));
            }//end of else if(strHistoryType.equals("HCL"))

            DOMReader domReader = new DOMReader();
            doc = xml != null ? domReader.read(xml.getDOM()):null;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl getHistory()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl getHistory()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return doc;
    }//end of getHistory(String strHistoryType, long lngSeqId, long lngEnrollDtlSeqId)

    /**
     * This method returns the ArrayList, which contains the MemberVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of MemberVO'S object's which contains the details of the Member
     * @exception throws TTKException
     */
    public ArrayList getOnlineEnrollmentList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alEnrollmentList = new ArrayList<Object>();
        MemberVO memberVO = new MemberVO();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strOnlineEnrollmentList);
            cStmtObject.setString(1,(String)alSearchCriteria.get(0));//user_id
            cStmtObject.setString(2,(String)alSearchCriteria.get(1));//policy_no
            cStmtObject.setString(3,(String)alSearchCriteria.get(2));//group_id
            cStmtObject.setString(4,(String)alSearchCriteria.get(3));//enrollment_id
            cStmtObject.setString(5,(String)alSearchCriteria.get(6));//certificate_no
            cStmtObject.setString(6,(String)alSearchCriteria.get(4));//login_type

            if(alSearchCriteria.get(5)!=null)
            {
            	cStmtObject.setLong(7,(Long)alSearchCriteria.get(5));//user_seq_id
            }//end of if(alSearchCriteria.get(5)!=null)
            else
            {
            	cStmtObject.setString(7,null);//user_seq_id
            }//end of else
            cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            if (rs!=null)
            {
                while (rs.next())
                {
                    memberVO = new MemberVO();
                    memberVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID")!=null ? new Long(rs.getInt("POLICY_GROUP_SEQ_ID")):null);
                    memberVO.setName(TTKCommon.checkNull(rs.getString("ENROLLMENT")));
                    memberVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
                    memberVO.setInsuredName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
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
                    memberVO.setTemplateID(TTKCommon.checkNull(rs.getString("TEMPLATE_ID")));
                    memberVO.setTemplateName(TTKCommon.checkNull(rs.getString("TEMPLATE_NAME")));
                    memberVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
                    memberVO.setMemberTypeID(TTKCommon.checkNull(rs.getString("POLICY_SUB_GENERAL_TYPE_ID")));
                    memberVO.setAllowAddSumYN(TTKCommon.checkNull(rs.getString("ALLOW_ADDITIONAL_SUM_YN")));
                    memberVO.setEmpAddYN(TTKCommon.checkNull(rs.getString("ALLOW_ADD_EMP_YN")));
                    memberVO.setCancelYN(TTKCommon.checkNull(rs.getString("EMPLOYEE_CANCELLED_YN")));
                    memberVO.setExpiredYN(TTKCommon.checkNull(rs.getString("EXPIRED_YN")));
                    memberVO.setPolicyStatusTypeID(TTKCommon.checkNull(rs.getString("POLICY_STATUS_GENERAL_TYPE_ID")));
                    memberVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    memberVO.setPolicyOpted(TTKCommon.checkNull(rs.getString("POLICY_OPTED")));
                    //added for IBM CR change
                    if(rs.getString("DATE_OF_JOINING") != null){
					  memberVO.setDateOfJoining(new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
					}//end of if(rs.getString("DATE_OF_JOINING") != null)
                    //ended
                    if(rs.getString("POLICY_DEFAULT_SUM_INSURED") != null){
                    	memberVO.setPolicySumInsured(new BigDecimal(rs.getString("POLICY_DEFAULT_SUM_INSURED")));
					}//end of if(rs.getString("POLICY_DEFAULT_SUM_INSURED") != null)
                    memberVO.setUploadYN(TTKCommon.checkNull(rs.getString("ENROLLMENT_UPD_YN")));
                    alEnrollmentList.add(memberVO);
                }// End of while (rs.next())
            }// End of if (rs!=null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getOnlineEnrollmentList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getOnlineEnrollmentList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlineEnrollmentList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return (ArrayList) alEnrollmentList;
    }//end of getOnlineEnrollmentList(ArrayList alSearchCriteria)

    /**
     * This method saves the Password Information
     * @param passwordVO PasswordVO which contains Password Information
     * @param strPolicyNbr String Object which contains Policy Number
     * @param strGroupID String Object which contains Group ID
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changePassword(PasswordVO passwordVO,String strPolicyNbr,String strGroupID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strChangePassword);
			cStmtObject.setString(1,strPolicyNbr);//POLICY_NUMBER
			cStmtObject.setString(2,strGroupID);//GROUP_ID
			cStmtObject.setString(3,passwordVO.getUserID());
			cStmtObject.setString(4,passwordVO.getOldPassword());
			cStmtObject.setString(5,passwordVO.getNewPassword());
			cStmtObject.registerOutParameter(6,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);
		}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl changePassword()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl changePassword()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
	}//end of changePassword(PasswordVO passwordVO,String strPolicyNbr,String strGroupID)

	/**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, lngEnrollDtlSeqId which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    public ArrayList<Object> getOnlineHomeDetails(String strGroupID, String strPolicyNbr, Long lngPolicySeqId, String strPolicyType,String strUserID)throws TTKException
    {
        Connection conn = null;
        CallableStatement cStmtObject = null;
        Document doc = null;
        SQLXML xml = null;
        ArrayList<Object> alResultList = new ArrayList<Object>() ;
        String strAlert ="";
        String strWindowPeriodAlert="";
        try
        {
            conn = ResourceManager.getConnection();
          //cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strOnlineAccessHome);
            cStmtObject = conn.prepareCall(strOnlineAccessHome);
        	cStmtObject.setString(1,strGroupID);
            cStmtObject.setString(2,strPolicyNbr);
            if(lngPolicySeqId !=0)
            {
            	cStmtObject.setLong(3,lngPolicySeqId);
            }//end of if(lngPolicySeqId !=0)
            else
            {
            	cStmtObject.setLong(3,0);
            }//end of else
			cStmtObject.setString(4,strPolicyType);
            cStmtObject.registerOutParameter(5,Types.SQLXML);
            cStmtObject.setString(6, strUserID);
            cStmtObject.registerOutParameter(7,Types.VARCHAR);
            cStmtObject.registerOutParameter(8, Types.VARCHAR);
            cStmtObject.registerOutParameter(2,Types.VARCHAR);
			 // Change added for KOCKOC1227I
            cStmtObject.registerOutParameter(9,Types.INTEGER);
            cStmtObject.registerOutParameter(10,Types.CHAR);//added for IBM
            cStmtObject.execute();
            xml = cStmtObject.getSQLXML(5);
            conn.commit();
            strAlert = cStmtObject.getString(7);
            strWindowPeriodAlert= cStmtObject.getString(8);
            SAXReader saxReader = new SAXReader();
            doc = xml != null ? saxReader.read(xml.getCharacterStream()):null;
            alResultList.add(doc);
            alResultList.add(strAlert);
            alResultList.add(strWindowPeriodAlert);
			// Change added for KOCKOC1227I
            alResultList.add(cStmtObject.getInt(9));
            alResultList.add(cStmtObject.getString(10));//added for IBM
            return alResultList;
        }//end of try
        catch (SQLException sqlExp)
        {
        	sqlExp.printStackTrace();
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlineHomeDetails()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlineHomeDetails()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally

    }//end of getOnlineHomeDetails(String strGroupID, String strPolicyNbr, Long lngPolicySeqId, String strPolicyType)

//intx
    /**
     * This method returns the XML Document of PreAuthHistory/PolicyHistory/ClaimsHistory which contains History Details
     * @param strHistoryType, lngSeqId, lngEnrollDtlSeqId which web board values
     * @return Document of PreAuthHistory/PolicyHistory/ClaimsHistory XML object
     * @exception throws TTKException
     */
    public ArrayList<Object> getOnlineProviderHomeDetails(String strUserID)throws TTKException
    {
        Connection conn = null;
        OracleCallableStatement cStmtObject = null;
       // CallableStatement cStmtObject = null;
        Document doc = null;
        XMLType xml = null;
        ArrayList<Object> alResultList = new ArrayList<Object>() ;
        String strAlert ="";
        String strWindowPeriodAlert="";
        ResultSet rs	=	null;
        PreparedStatement pStmt	=	null;
        try
        {
            conn = ResourceManager.getConnection();
            pStmt=conn.prepareStatement(strOnlineProvDetails);

            pStmt.setString(1,strUserID);
            rs = pStmt.executeQuery();

            if(rs != null){
                while(rs.next()){
                	alResultList.add(rs.getString("authority"));
                	alResultList.add(rs.getString("provider"));
                	alResultList.add(rs.getString("provider_id"));
                }//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
        	sqlExp.printStackTrace();
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlineProviderHomeDetails()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlineProviderHomeDetails()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
        	return alResultList;       
		}//end of finally

    }//end of getOnlineProviderHomeDetails String strPolicyType)

    
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
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Connection in OnlineAccessDAOImpl getLocation()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getLocation()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getLocation()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
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
     * This method saves the Enrollment Information
     * @param memberDetailVO memberDetailVO which contains the MemberDetail Information
     * @return lngPolicySeqID long Object, which contains the Policy Group Seq ID
     * @exception throws TTKException
     */
    public long saveEnrollment(MemberDetailVO memberDetailVO,String strUserType) throws TTKException
    {
        //int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        long lngPolicyGrpSeqID = 0;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveEnrollment);

            if(memberDetailVO.getPolicyGroupSeqID()!=null){
                cStmtObject.setLong(1,memberDetailVO.getPolicyGroupSeqID());   //POLICY_GROUP_SEQ_ID
            }//end of if(memberDetailVO.getPolicyGroupSeqID()!=null)
            else
        	{
        		cStmtObject.setLong(1, 0);
        	}//end  of else

            if(memberDetailVO.getPolicySeqID()!=null){
                cStmtObject.setLong(2,memberDetailVO.getPolicySeqID());   //POLICY_SEQ_ID
            }//end of if(memberDetailVO.getPolicySeqID()!=null)
            else{
                cStmtObject.setString(2,null);
            }//end of else

            cStmtObject.setString(3,memberDetailVO.getEmployeeNbr()); //EMPLOYEE_NO
            cStmtObject.setString(4,memberDetailVO.getInsuredName());//INSURED_NAME
            cStmtObject.setString(5,memberDetailVO.getEnrollmentNbr());//tpa_enrollment_number
            cStmtObject.setString(6,memberDetailVO.getDepartment());//department
            cStmtObject.setString(7,memberDetailVO.getDesignation());//designation
            cStmtObject.setTimestamp(8,memberDetailVO.getStartDate()!=null ? new Timestamp(memberDetailVO.getStartDate().getTime()):null);
            cStmtObject.setTimestamp(9,memberDetailVO.getEndDate()!=null ? new Timestamp(memberDetailVO.getEndDate().getTime()):null);//DATE_OF_RESIGNATION

            if(memberDetailVO.getGroupRegnSeqID()!=null){
            	cStmtObject.setLong(10,memberDetailVO.getGroupRegnSeqID());//group_reg_seq_id
            }//end of if(memberDetailVO.getGroupRegnSeqID()!=null)
            else{
                cStmtObject.setString(10,null);
            }//end of else

            if(memberDetailVO.getMemberAddressVO().getAddrSeqId() != null){
                cStmtObject.setLong(11,memberDetailVO.getMemberAddressVO().getAddrSeqId());
            }//end of if(memberDetailVO.getMemberAddressVO().getAddrSeqId() != null)
            else{
                cStmtObject.setString(11,null);
            }//end of else

            cStmtObject.setString(12,memberDetailVO.getMemberAddressVO().getAddress1());//address_1
            cStmtObject.setString(13,memberDetailVO.getMemberAddressVO().getAddress2());//address_2
            cStmtObject.setString(14,memberDetailVO.getMemberAddressVO().getAddress3());//address_3
            cStmtObject.setString(15,memberDetailVO.getMemberAddressVO().getStateCode());//state_type_id
            cStmtObject.setString(16,memberDetailVO.getMemberAddressVO().getCityCode());//city_type_id
            cStmtObject.setString(17,memberDetailVO.getMemberAddressVO().getCountryCode());//country_id
            cStmtObject.setString(18,memberDetailVO.getMemberAddressVO().getPinCode());//pin_code
            cStmtObject.setString(19,memberDetailVO.getMemberAddressVO().getEmailID());//email_id
            cStmtObject.setString(20,memberDetailVO.getMemberAddressVO().getPhoneNbr1());//off_phone_no_1
            cStmtObject.setString(21,memberDetailVO.getMemberAddressVO().getPhoneNbr2());//off_phone_no_2
            cStmtObject.setString(22,memberDetailVO.getMemberAddressVO().getHomePhoneNbr());//res_phone_no
            cStmtObject.setString(23,memberDetailVO.getMemberAddressVO().getMobileNbr());//mobile_no
            //cStmtObject.setString(23,memberDetailVO.getMemberAddressVO().getMobileNbr());//mobile_no
			cStmtObject.setString(23,memberDetailVO.getMobileNo());//overwriiten for weblogin validation -- mobile_no
            cStmtObject.setString(24,memberDetailVO.getMemberAddressVO().getFaxNbr());//fax_no

            if(memberDetailVO.getBankSeqID()!=null){
                cStmtObject.setLong(25,memberDetailVO.getBankSeqID());   //BANK_SEQ_ID
            }//end of if(memberDetailVO.getBankSeqID()!=null)
            else{
                cStmtObject.setString(25,null);
            }//end of else

            cStmtObject.setString(26,memberDetailVO.getBankAccNbr());//BANK_ACCOUNT_NO
            cStmtObject.setString(27,memberDetailVO.getBankName());//BANK_NAME
            cStmtObject.setString(28,memberDetailVO.getAccountName());//ACCOUNT_NAME
            cStmtObject.setString(29,memberDetailVO.getBankPhone());//BANK_PHONE_NO
            cStmtObject.setString(30,memberDetailVO.getBranch());//BANK_BRANCH
            cStmtObject.setString(31,memberDetailVO.getMICRCode());//BANK_MICR

            if(memberDetailVO.getDateOfMarriage()!=null){
                cStmtObject.setTimestamp(32,new Timestamp(memberDetailVO.getDateOfMarriage().getTime()));
            }//end of if(memberDetailVO.getDateOfMarriage()!=null)
            else{
                cStmtObject.setTimestamp(32,null);
            }//end of else

            cStmtObject.setString(33,strUserType);//flag
            cStmtObject.setString(34, memberDetailVO.getFamilyNbr());
            cStmtObject.registerOutParameter(35,Types.INTEGER);
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.setLong(36,memberDetailVO.getUpdatedBy());//
            //Added for IBM.....KOC-1216
			cStmtObject.setString(37,memberDetailVO.getStopOPtInYN());//Saving the checkbox value --added by Rekha 13.07.2012
			cStmtObject.setString(38,memberDetailVO.getEmailID2());
			cStmtObject.setString(39,memberDetailVO.getValidEmailPhYN());//remove after adding parameter EmailPh

            cStmtObject.execute();
			lngPolicyGrpSeqID = cStmtObject.getLong(1);
        }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineformsemp");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineformsemp");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveEnrollment()",sqlExp);
        			throw new TTKException(sqlExp, "onlineformsemp");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveEnrollment()",sqlExp);
        				throw new TTKException(sqlExp, "onlineformsemp");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineformsemp");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngPolicyGrpSeqID;
     } //end of saveEnrollment(MemberDetailVO memberDetailVO)


    /**
     * This method returns the MemberDetailVO which contains the Member Detail information
     * @param alEnrollList ArrayList object which contains Policy group Seq ID and Policy seq ID
     * @return MemberDetailVO the contains the Member Detail information
     * @exception throws TTKException
     */
    public MemberDetailVO selectEnrollment(ArrayList alEnrollList) throws TTKException
    {
        MemberDetailVO memberDetailVO = null;
        MemberAddressVO memberAddressVO =null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;

		//Added for IBM.....KOC-1216

		//Added for IBM.3.0
		String V_FLAG ="";//Identifier added  by Rekha on 13.07.2012 for windows period
		String V_PRE_CLAM_YN ="";//Identifier added  by Rekha on 13.07.2012 for preauth/claim
		String V_OPT_ALLOWED = "";//Identifier added by Praveen for adding weblogin config
		//Ended for IBM.3.0


        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectEnrollment);

            if(alEnrollList.get(0) != null)
            {
            	cStmtObject.setLong(1,(Long)alEnrollList.get(0)); //policy group seq id
            }//end of if(alEnrollList.get(0) != null)
            else
            {
            	cStmtObject.setString(1,null); //lngPolicySeqID
            }//end of else
            if(alEnrollList.get(1) != null)
            {
            	cStmtObject.setLong(2,(Long)alEnrollList.get(1));
            }//end of if(alEnrollList.get(1) != null)
            else
            {
            	cStmtObject.setString(2,null);
            }//end of else
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);//ROW_PROCESSED

            //Added for IBM.....KOC-1216
			cStmtObject.registerOutParameter(4,OracleTypes.VARCHAR);//added by Rekha 13.07.2012
			cStmtObject.registerOutParameter(5,OracleTypes.VARCHAR);//added by Rekha 13.07.2012
			cStmtObject.registerOutParameter(6,OracleTypes.VARCHAR);//added by Rekha 13.07.2012
			//Ended for IBM.....3.1
            cStmtObject.execute();
            //Added for IBM...3.2
            //Added for IBM...KOC-1216
			if(cStmtObject.getString(4)!=null)
			{
				V_FLAG = cStmtObject.getString(4).trim();//ROW_PROCESSED//added by Rekha 13.07.2012
			}
			if(cStmtObject.getString(5)!=null)
			{
				V_PRE_CLAM_YN = cStmtObject.getString(5).trim();//ROW_PROCESSED//added by Rekha 13.07.2012
			}
			if(cStmtObject.getString(6)!=null)
			{
				V_OPT_ALLOWED = cStmtObject.getString(6).trim();//added by Praveen for adding weblogin config
			}



            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            if (rs!=null)
            {
                while(rs.next())
                 {
                    memberDetailVO = new MemberDetailVO();
                    memberAddressVO = new MemberAddressVO();
                    if(rs.getString("POLICY_GROUP_SEQ_ID")!=null){
                        memberDetailVO.setPolicyGroupSeqID(new Long(rs.getInt("POLICY_GROUP_SEQ_ID")));
                    }//end of if(rs.getString("POLICY_GROUP_SEQ_ID")!=null)

                    if(rs.getString("POLICY_SEQ_ID")!=null){
                        memberDetailVO.setPolicySeqID(new Long(rs.getInt("POLICY_SEQ_ID")));
                    }//end of if(rs.getString("MEMBER_SEQ_ID")!=null)
                    memberDetailVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NO")));
                    memberDetailVO.setInsuredName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));

                    memberDetailVO.setEnrollmentNbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));

                    if(rs.getString("ENR_ADDRESS_SEQ_ID") != null){
            			memberAddressVO.setAddrSeqId(new Long(rs.getLong("ENR_ADDRESS_SEQ_ID")));
            		}//end of if(rs.getString("ENR_ADDRESS_SEQ_ID") != null)

                    memberDetailVO.setDepartment(TTKCommon.checkNull(rs.getString("DEPARTMENT")));
                    memberDetailVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATION")));

                    if(rs.getString("DATE_OF_JOINING") != null){
            			memberDetailVO.setStartDate(new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()));
            		}//end of if(rs.getString("DATE_OF_JOINING") != null)

            		if(rs.getString("DATE_OF_RESIGNATION") != null){
            			memberDetailVO.setEndDate(new Date(rs.getTimestamp("DATE_OF_RESIGNATION").getTime()));
            		}//end of if(rs.getString("DATE_OF_RESIGNATION") != null)


            		if(rs.getString("LOCATION") != null){
            			memberDetailVO.setGroupRegnSeqID(new Long(rs.getLong("LOCATION")));
            		}//end of if(rs.getString("LOCATION") != null)

            		if(rs.getString("BANK_SEQ_ID")!=null){
                        memberDetailVO.setBankSeqID(new Long(rs.getInt("BANK_SEQ_ID")));
                    }//end of if(rs.getString("BANK_SEQ_ID")!=null)

            		if(rs.getString("BANK_NAME")!=null){
                        memberDetailVO.setBankName(rs.getString("BANK_NAME"));
                    }//end of if(rs.getString("BANK_NAME")!=null)

            		if(rs.getString("ACCOUNT_NAME")!=null){
                        memberDetailVO.setAccountName(rs.getString("ACCOUNT_NAME"));
                    }//end of if(rs.getString("ACCOUNT_NAME")!=null)

            		if(rs.getString("BANK_ACCOUNT_NO")!=null){
                        memberDetailVO.setBankAccNbr(rs.getString("BANK_ACCOUNT_NO"));
                    }//end of if(rs.getString("BANK_ACCOUNT_NO")!=null)

            		if(rs.getString("BANK_BRANCH")!=null){
                        memberDetailVO.setBranch(rs.getString("BANK_BRANCH"));
                    }//end of if(rs.getString("BANK_BRANCH")!=null)

            		if(rs.getString("BANK_PHONE_NO")!=null){
                        memberDetailVO.setBankPhone(rs.getString("BANK_PHONE_NO"));
                    }//end of if(rs.getString("BANK_PHONE_NO")!=null)

            		if(rs.getString("BANK_MICR")!=null){
                        memberDetailVO.setMICRCode(rs.getString("BANK_MICR"));
                    }//end of if(rs.getString("BANK_MICR")!=null)

            		memberDetailVO.setEmpStatusTypeID(TTKCommon.checkNull(rs.getString("EMPLOYEE_STATUS")));
            		memberDetailVO.setCustomerNbr(TTKCommon.checkNull(rs.getString("CUSTOMER_NO")));
            		memberDetailVO.setCertificateNbr(TTKCommon.checkNull(rs.getString("CERTIFICATE_NO")));
            		memberDetailVO.setCreditCardNbr(TTKCommon.checkNull(rs.getString("CREDITCARD_NO")));
            		memberDetailVO.setOrderNbr(TTKCommon.checkNull(rs.getString("ORDER_NUMBER")));
            		memberDetailVO.setPrevOrderNbr(TTKCommon.checkNull(rs.getString("PREV_ORDER_NUMBER")));
                    memberDetailVO.setMarriageHideYN(TTKCommon.checkNull(rs.getString("hide_dom_yn")));//koc for griavance

            		if(rs.getString("DATE_OF_MARRIAGE") != null){
            			memberDetailVO.setDateOfMarriage(new Date(rs.getTimestamp("DATE_OF_MARRIAGE").getTime()));
            		}//end of if(rs.getString("DATE_OF_MARRIAGE") != null)

            		memberAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
            		memberAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
            		memberAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
            		memberAddressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
            		memberAddressVO.setCityCode(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));
            		memberAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
            		memberAddressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
            		memberAddressVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
            		//added new email field---- by Praveen
					memberAddressVO.setEmailID2(TTKCommon.checkNull(rs.getString("EMAILID2")));
					//ended.
					//remove the below on adding EmailPh
					memberDetailVO.setValidEmailPhYN(TTKCommon.checkNull(rs.getString("VALID_EMAIL_PH_YN")));
            		memberAddressVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
            		memberAddressVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
            		memberAddressVO.setHomePhoneNbr(TTKCommon.checkNull(rs.getString("RES_PHONE_NO")));
            		memberAddressVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
            		memberAddressVO.setFaxNbr(TTKCommon.checkNull(rs.getString("FAX_NO")));
            		memberDetailVO.setFamilyNbr(TTKCommon.checkNull(rs.getString("FAMILY_NUMBER")));
            		//ADDED FOR KOC-1216
            		if(rs.getString("POLICY_OPTED")!=null)
					{
						memberDetailVO.setStopOPtInYN(TTKCommon.checkNull(rs.getString("POLICY_OPTED")));//getting the opted value from database
					}

					memberDetailVO.setwindowsOPTYN(TTKCommon.checkNull(V_FLAG));////added by Rekha 13.07.2012
					memberDetailVO.setchkpreauthclaims(TTKCommon.checkNull(V_PRE_CLAM_YN));////added by Rekha 13.07.2012
					memberDetailVO.setV_OPT_ALLOWED(TTKCommon.checkNull(V_OPT_ALLOWED));//added by Praveen for weblogin optin
					//Ended for IBM..3.3

					//Ended IBM....3

            		memberDetailVO.setMemberAddressVO(memberAddressVO);
                 }//end of while
            }//end of if (rs!=null)
        }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Statement in OnlineAccessDAOImpl selectEnrollment()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl selectEnrollment()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl selectEnrollment()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
			return (MemberDetailVO) memberDetailVO ;
		} //end of selectEnrollment(ArrayList alEnrollList)

    /**
     * This method saves the Policy Dependent Information
     * @param dependentDetailVO DependentDetailVO's object which contains the Member dependent details Information
     * @return lngMemberSeqID long Object, which contains the Member Seq ID
     * @exception throws TTKException
     */
    public long saveDependent(DependentDetailVO dependentDetailVO) throws TTKException
    {
        //int iResult = 0;
        long lngMemberSeqID = 0;
//start modification as per KOC 1159 and 1160
        String combinationLimit=null;
        //end modification as per KOC 1159 and 1160
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try {
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDependent);
        	if(dependentDetailVO.getMemberSeqID() != null)
        	{
        		cStmtObject.setLong(1, dependentDetailVO.getMemberSeqID());
        	}//end of if(dependentDetailVO.getMemberSeqID() != null)
        	else
        	{
        		cStmtObject.setLong(1, 0);
        	}//end  of else

        	cStmtObject.setLong(2, dependentDetailVO.getPolicyGroupSeqID());
        	cStmtObject.setLong(3, dependentDetailVO.getPolicySeqID());
        	cStmtObject.setString(4, (String)dependentDetailVO.getName());
        	cStmtObject.setString(5, (String)dependentDetailVO.getGenderTypeID());
        	cStmtObject.setString(6, (String)dependentDetailVO.getRelationTypeID());

        	cStmtObject.setTimestamp(7,dependentDetailVO.getDateOfBirth()!=null ? new Timestamp(dependentDetailVO.getDateOfBirth().getTime()):null);

        	if(dependentDetailVO.getAge()!= null){
                cStmtObject.setInt(8,dependentDetailVO.getAge());
            }//end of if(dependentDetailVO.getAge()!= null)
            else{
                cStmtObject.setString(8,null);
            }//end of else
        	cStmtObject.setBigDecimal(9, (BigDecimal)dependentDetailVO.getTotalSumInsured());
        	cStmtObject.setString(10, (String)dependentDetailVO.getMemberTypeID());
        	cStmtObject.setLong(11, (Long)dependentDetailVO.getUpdatedBy());

        	if(dependentDetailVO.getProdPlanSeqID() == null || dependentDetailVO.getProdPlanSeqID().equals(0)){
        		cStmtObject.setString(13,null);
        	}//end of if(dependentDetailVO.getProdPlanSeqID() == null)
        	else{
        		cStmtObject.setLong(13,dependentDetailVO.getProdPlanSeqID());
        	}//end of else

        	if(dependentDetailVO.getAddPremium() == null || dependentDetailVO.getAddPremium().equals(0)){
        		cStmtObject.setString(14,null);
        	}//end of if(dependentDetailVO.getAddPremium() == null)
        	else{
        		cStmtObject.setBigDecimal(14, (BigDecimal)dependentDetailVO.getAddPremium());
        	}//end of else
			//  KOC1227C passing the premium deduction option selected
        	cStmtObject.setString(15,TTKCommon.checkNull(dependentDetailVO.getPremiumDeductionOption()));
        	//Added for IBM Declaration
	    	cStmtObject.setString(17,TTKCommon.checkNull(dependentDetailVO.getDeclaration()));
	       	cStmtObject.registerOutParameter(12,OracleTypes.INTEGER);
        	//modified as per KOC 1159 and 1160
        	cStmtObject.registerOutParameter(16,Types.VARCHAR);
        	//modified as per KOC 1159 and 1160
        	cStmtObject.registerOutParameter(1,Types.BIGINT);
        	cStmtObject.execute();
        	combinationLimit = (String)cStmtObject.getObject(16);
        	dependentDetailVO.setCombinationMembersLimit(combinationLimit);
        	lngMemberSeqID = cStmtObject.getLong(1);
    }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveDependent()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveDependent()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lngMemberSeqID;
    }//end of saveDependent(DependentDetailVO dependentDetailVO)

    /**
     * This method returns the DependentDetailVO which contains the Dependent Detail information
     * @param lngMemberSeqID Long which contains seq id for Enrollment flow to get the Member information
     * @param strLoginType String which contains Weblogin Login Type
     * @return DependentDetailVO the contains the Dependent list Detail information
     * @exception throws TTKException
     */
    public DependentDetailVO selectDependent(Long lngMemberSeqID,String strLoginType) throws TTKException
    {
    	DependentDetailVO dependentDetailVO = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectDependent);
            cStmtObject.setLong(1,lngMemberSeqID);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);//ROW_PROCESSED
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if (rs!=null)
            {
                while(rs.next())
                 {
                	dependentDetailVO = new DependentDetailVO();
                	dependentDetailVO.setMemberSeqID(rs.getString("MEMBER_SEQ_ID") !=null? rs.getLong("MEMBER_SEQ_ID"):null);	//member_seq_id
                	dependentDetailVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
                	//Added for IBM.....KOC-1216
					dependentDetailVO.setPolicy_Stop_YN(rs.getString("POLICY_OPTED"));
					//Ended

                	dependentDetailVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
                	dependentDetailVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
                	dependentDetailVO.setDateOfBirth(rs.getString("MEM_DOB")!=null ? new Date(rs.getTimestamp("MEM_DOB").getTime()):null);
                	if(rs.getString("MEM_AGE") != null){
                		dependentDetailVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)
                	dependentDetailVO.setMemberTypeID(TTKCommon.checkNull(rs.getString("MEM_GENERAL_TYPE_ID")));
                	if(rs.getString("MEM_TOT_SUM_INSURED") != null){
                		dependentDetailVO.setTotalSumInsured(new BigDecimal(rs.getString("MEM_TOT_SUM_INSURED")));
					}//end of if(rs.getString("MEM_TOT_SUM_INSURED") != null)

                	dependentDetailVO.setLoginType(strLoginType);
                	dependentDetailVO.setPreClaimExistYN(TTKCommon.checkNull(rs.getString("CLAIM_EXIST_YN")));

                	/*if(strLoginType.equals("H")){
                		dependentDetailVO.setAllowAddYN(TTKCommon.checkNull(rs.getString("ALLOW_ADD_HR_YN")));
                	}//end of if(strLoginType.equals("H"))
                	else if(strLoginType.equals("E")){
                		dependentDetailVO.setAllowAddYN(TTKCommon.checkNull(rs.getString("ALLOW_ADD_EMP_YN")));
                	}//end of if(strLoginType.equals("E"))
*/
                	dependentDetailVO.setAllowAddSumYN(TTKCommon.checkNull(rs.getString("ALLOW_ADDITIONAL_SUM_YN")));
                	dependentDetailVO.setAssocGenderRel(TTKCommon.checkNull(rs.getString("ASSOC_GENDER_REL")));
                	dependentDetailVO.setGenderYN(TTKCommon.checkNull(rs.getString("ASSOC_GENDER_REL")));
                	dependentDetailVO.setDeclaration(TTKCommon.checkNull(rs.getString("AGE_CONFIRM")));

                }//end of  while(rs.next())
            }//end of if (rs!=null)
        }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Statement in OnlineAccessDAOImpl selectDependent()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl selectDependent()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl selectDependent()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (DependentDetailVO) dependentDetailVO ;
     } //end of selectDependent(Long lngMemberSeqID,String strLoginType)

    /**
     * This method returns the ArrayList, which contains the SumInsuredVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SumInsuredVO's object's which contains the details of the SumInsured Information
     * @exception throws TTKException
     */
    public ArrayList getSumInsuredPlan(ArrayList alSumInsCriteria) throws TTKException {
    	Collection<Object> alSumInsList = new ArrayList<Object>();
    	SumInsuredVO sumInsuredVO = null;
    	Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectSumInsuredPlan);
            cStmtObject.setLong(1,(Long)alSumInsCriteria.get(0));
            cStmtObject.setLong(2,(Long)alSumInsCriteria.get(1));
            if(alSumInsCriteria.get(2)!=null){
            	cStmtObject.setLong(3,(Long)alSumInsCriteria.get(2));
            }//end of if(alSumInsCriteria.get(2)!=null)
            else {
            	cStmtObject.setString(3,null);
            }//end of else
            if(alSumInsCriteria.get(3)!=null){
            	cStmtObject.setString(4,(String)alSumInsCriteria.get(3));
            }//end of if(alSumInsCriteria.get(3)!=null)
            else {
            	cStmtObject.setString(4,null);
            }//end of else
            cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(5);
            if(rs != null)
            {
            	while(rs.next())
            	{
            		sumInsuredVO = new SumInsuredVO();
            		sumInsuredVO.setProdPlanSeqId(rs.getString("PROD_PLAN_SEQ_ID"));
            		sumInsuredVO.setProdPlanName(rs.getString("PROD_PLAN_NAME"));
            		sumInsuredVO.setPlanAmount(rs.getString("PLAN_AMOUNT"));
            		sumInsuredVO.setPlanPremium(rs.getString("PLAN_PREMIUM"));
					// Below change added for CR KOC1184
					sumInsuredVO.setProRata(TTKCommon.checkNull(rs.getString("PRORATA")));
            		sumInsuredVO.setFromAge(rs.getString("FROM_AGE")!=null ? new Integer(rs.getInt("FROM_AGE")):null);
            		sumInsuredVO.setToAge(rs.getString("TO_AGE")!=null ? new Integer(rs.getInt("TO_AGE")):null);
            		sumInsuredVO.setCombineAge(sumInsuredVO.getFromAge()+"-"+sumInsuredVO.getToAge());
            		sumInsuredVO.setSelectedYN(rs.getString("SELECTED_YN"));
					//Change added for KOC1227C
            		if(rs.getString("SELECTED_YN").equals("Y"))
            		{
            			sumInsuredVO.setPremiumDeductionOption(TTKCommon.checkNull(rs.getString("PREMIUM_DEDUCT_OPTION")));
            		}
            		alSumInsList.add(sumInsuredVO);
            	}//end of while(rs.next())
            }//end of if(rs != null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getSumInsuredPlan()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getSumInsuredPlan()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getSumInsuredPlan()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return (ArrayList) alSumInsList;
    }//end of getSumInsuredPlan(ArrayList alSumInsCriteria)

    /** This method is useful while adding a member
     * This method returns the ArrayList, which contains the SumInsuredVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of SumInsuredVO's object's which contains the details of the SumInsured Information
     * @exception throws TTKException
     */
    public ArrayList getSumInsuredPlanInfo(ArrayList alSumInsCriteria) throws TTKException {
    	Collection<Object> alSumInsList = new ArrayList<Object>();
    	SumInsuredVO sumInsuredVO = null;
    	Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try {
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectSumInsuredPlanInfo);
            cStmtObject.setLong(1,(Long)alSumInsCriteria.get(0));
            if(alSumInsCriteria.get(1)!=null){
            	cStmtObject.setString(2,(String)alSumInsCriteria.get(1));
            }//end of if(alSumInsCriteria.get(1)!=null)
            else {
            	cStmtObject.setString(2,null);
            }//end of else
            if(alSumInsCriteria.get(2)!=null){
            	cStmtObject.setLong(3,(Long)alSumInsCriteria.get(2));
            }//end of if(alSumInsCriteria.get(2)!=null)
            else {
            	cStmtObject.setString(3,null);
            }//end of else
			// prorata CR KOC1184
            if(alSumInsCriteria.get(3)!=null){
            	cStmtObject.setLong(4,(Long)alSumInsCriteria.get(3));
        	}//end of if(alSumInsCriteria.get(3)!=null)
            else {
            	cStmtObject.setString(4,null);
            }//end of else
            cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(5);
            if(rs != null)
            {
            	while(rs.next())
            	{
            		sumInsuredVO = new SumInsuredVO();
            		sumInsuredVO.setProdPlanSeqId(rs.getString("PROD_PLAN_SEQ_ID"));
            		sumInsuredVO.setProdPlanName(rs.getString("PROD_PLAN_NAME"));
            		sumInsuredVO.setPlanAmount(rs.getString("PLAN_AMOUNT"));
            		sumInsuredVO.setPlanPremium(rs.getString("PLAN_PREMIUM"));
            		sumInsuredVO.setFromAge(rs.getString("FROM_AGE")!=null ? new Integer(rs.getInt("FROM_AGE")):null);
            		sumInsuredVO.setToAge(rs.getString("TO_AGE")!=null ? new Integer(rs.getInt("TO_AGE")):null);
            		sumInsuredVO.setCombineAge(sumInsuredVO.getFromAge()+"-"+sumInsuredVO.getToAge());
            		sumInsuredVO.setSelectedYN(rs.getString("SELECTED_YN"));
					//Prorata CR KOC1184
            		sumInsuredVO.setProRata(TTKCommon.checkNull(rs.getString("PRORATA")));
            		alSumInsList.add(sumInsuredVO);
            	}//end of while(rs.next())
            }//end of if(rs != null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getSumInsuredPlanInfo()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getSumInsuredPlanInfo()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getSumInsuredPlanInfo()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return (ArrayList) alSumInsList;
    }//end of getSumInsuredPlanInfo(ArrayList alSumInsCriteria)

    /**
     * This method returns the MemberCancelVO which contains the Member information
     * @param alSelectList ArrayList Object which contains Policy Group seq id and member seq ID for Enrollment flow to get the Cancelled Member information
     * @return MemberCancelVO the contains the Member Cancellation Information
     * @exception throws TTKException
     */
    public MemberCancelVO selectCancellation(ArrayList alSelectList) throws TTKException
    {
        MemberCancelVO memberCancelVO = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectCancellation);
            cStmtObject.setLong(1,(Long)alSelectList.get(0));
            if(alSelectList.get(1) != null)
            {
            	cStmtObject.setLong(2,(Long)alSelectList.get(1));
            }else
            {
            	cStmtObject.setString(2,"");
            }
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);//ROW_PROCESSED
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(3);
            if (rs!=null)
            {
                while(rs.next())
                 {
                	memberCancelVO = new MemberCancelVO();
                	if(rs.getString("DATE_OF_EXIT")!=null){
                		memberCancelVO.setExitDate(new Date(rs.getTimestamp("DATE_OF_EXIT").getTime()));
                    }//end of if(rs.getString("DATE_OF_EXIT")!=null)

                	memberCancelVO.setStopPatClmProcessYN(TTKCommon.checkNull(rs.getString("STOP_PAT_CLM_PROCESS_YN")));
                	if(rs.getString("RECIEVED_AFTER")!=null){
                		memberCancelVO.setReceivedAfter(new Date(rs.getTimestamp("RECIEVED_AFTER").getTime()));
                    }//end of if(rs.getString("DATE_OF_EXIT")!=null)

                	if(rs.getString("EFFECTIVE_FROM_DATE")!=null){
                		memberCancelVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
                    }//end of if(rs.getString("DATE_OF_EXIT")!=null)

                	if(rs.getString("EFFECTIVE_TO_DATE")!=null){
                		memberCancelVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
                    }//end of if(rs.getString("DATE_OF_EXIT")!=null)

                	memberCancelVO.setFlag(TTKCommon.checkNull(rs.getString("V_FLAG")));
                	memberCancelVO.setAllowEditYN(TTKCommon.checkNull(rs.getString("ALLOW_EDIT_YN")));

                 }//end of while(rs.next()
            }//end of if (rs!=null)
        }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl selectCancellation()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl selectCancellation()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl selectCancellation()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (MemberCancelVO) memberCancelVO;
    }//end of selectCancellation(Long lngPolicyGroupSeqID, Long lngMemberSeqID)

    /**
     * This method Save the Cancellation Information
     * @param memberCancelVO MemberCancelVO's object which contains the MemberCancel Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int saveCancellation(MemberCancelVO memberCancelVO) throws TTKException
    {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try {
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCancellation);
        	cStmtObject.setLong(1, memberCancelVO.getPolicyGroupSeqID());

        	if(memberCancelVO.getMemberSeqID() != null){
        		cStmtObject.setLong(2, memberCancelVO.getMemberSeqID());
        	}//end of if(memberCancelVO.getMemberSeqID() != null)
        	else{
        		cStmtObject.setString(2,null);
        	}//end of else

        	cStmtObject.setLong(3, memberCancelVO.getPolicySeqID());
        	cStmtObject.setTimestamp(4, memberCancelVO.getExitDate()!=null ? new Timestamp(memberCancelVO.getExitDate().getTime()):null);
        	cStmtObject.setString(5, (String)memberCancelVO.getStopPatClmProcessYN());
        	cStmtObject.setTimestamp(6, memberCancelVO.getReceivedAfter()!=null ? new Timestamp(memberCancelVO.getReceivedAfter().getTime()):null);
        	cStmtObject.setString(7, (String)memberCancelVO.getFlag());
        	cStmtObject.setLong(8, (Long)memberCancelVO.getUpdatedBy());
        	cStmtObject.registerOutParameter(9,OracleTypes.INTEGER);
        	cStmtObject.execute();
        	iResult = cStmtObject.getInt(9);

        }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveCancellation()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveCancellation()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of saveCancellation(MemberCancelVO memberCancelVO)

    /**
     * This method returns the SumInsuredVO which contains the Sum Insured information
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return lngPolicyGroupSeqID long Object, which contains the Policy Group Seq ID
     * @exception throws TTKException
     */
    public long saveMemInsured(SumInsuredVO sumInsuredVO) throws TTKException
    {
        int iResult = 0;
        long lngPolicyGroupSeqID = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try {
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMemInsured);
        	if(sumInsuredVO.getPolicyGroupSeqID() != null)
        	{
        		cStmtObject.setLong(1, sumInsuredVO.getPolicyGroupSeqID());
        	}//end of if(sumInsuredVO.getPolicyGroupSeqID() != null)
        	else
        	{
        		cStmtObject.setString(1, null);
        	}//end of else
        	if(sumInsuredVO.getPolicySeqID() != null)
        	{
        		cStmtObject.setLong(2, sumInsuredVO.getPolicySeqID());
        	}//end of if(sumInsuredVO.getPolicySeqID() != null)
        	else
        	{
        		cStmtObject.setString(2, null);
        	}//end of else
            if(sumInsuredVO.getMemberSeqID() != null)
            {
            	cStmtObject.setLong(3, sumInsuredVO.getMemberSeqID());
            }//end of if(sumInsuredVO.getMemberSeqID() != null)
            else
            {
            	cStmtObject.setString(3, null);
            }//end of else

            cStmtObject.setString(4, sumInsuredVO.getPlanAmount());
            cStmtObject.setString(5, sumInsuredVO.getProdPlanSeqId());
            String EnrollmentId = sumInsuredVO.getEnrollmentID();
            //ADDED FOR KOC-1216
			if(EnrollmentId.contains("I310"))
			{
				cStmtObject.setString(6, sumInsuredVO.getPlanPremium());
			}
			else
			{
				// Below Change done for CR KOC1184 -- passing pro rata instead of annual premium
				// cStmtObject.setString(6, sumInsuredVO.getPlanPremium());
				cStmtObject.setString(6, sumInsuredVO.getProRata());
            }
			// Below Change done for CR KOC1184 -- passing pro rata instead of annual premium
            // cStmtObject.setString(6, sumInsuredVO.getPlanPremium());
			cStmtObject.setString(6, sumInsuredVO.getProRata());
            cStmtObject.setTimestamp(7,sumInsuredVO.getInceptionDate() != null ?  new Timestamp(sumInsuredVO.getInceptionDate().getTime()):null);
            if(sumInsuredVO.getUpdatedBy() != null)
            {
            	cStmtObject.setLong(8, sumInsuredVO.getUpdatedBy());
            }//end of if(sumInsuredVO.getUpdatedBy() != null)
            else
            {
            	cStmtObject.setString(8, null);
            }//end of else
            cStmtObject.setString(9, "EXT");
			// Change added for KOC1227C
			cStmtObject.setString(10,TTKCommon.checkNull(sumInsuredVO.getPremiumDeductionOption()));
            cStmtObject.registerOutParameter(11,OracleTypes.INTEGER);
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(11);
           // log.debug("iResult value is :"+iResult);
            lngPolicyGroupSeqID = cStmtObject.getLong(1);
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
        }//end of catch (Exception exp)
        finally
        {
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the result set
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveMemInsured()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveMemInsured()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of finally Statement Close
        	catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return lngPolicyGroupSeqID;
    }//end of saveMemInsured(SumInsuredVO sumInsuredVO)

    /**
     * This method clears the Additional Sum Insured Plan saved before
     * @param sumInsuredVO SumInsuredVO's object which contains the search criteria
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int clearAdditionalSumInsured(SumInsuredVO sumInsuredVO) throws TTKException
    {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try {
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteAdditionalSum);
        	if(sumInsuredVO.getMemberSeqID() != null)
            {
            	cStmtObject.setLong(1, sumInsuredVO.getMemberSeqID());
            }//end of if(sumInsuredVO.getMemberSeqID() != null)
            else
            {
            	cStmtObject.setString(1, null);
            }//end of else

        	if(sumInsuredVO.getPolicyGroupSeqID() != null)
        	{
        		cStmtObject.setLong(2, sumInsuredVO.getPolicyGroupSeqID());
        	}//end of if(sumInsuredVO.getPolicyGroupSeqID() != null)
        	else
        	{
        		cStmtObject.setString(2, null);
        	}//end of else
        	if(sumInsuredVO.getPolicySeqID() != null)
        	{
        		cStmtObject.setLong(3, sumInsuredVO.getPolicySeqID());
        	}//end of if(sumInsuredVO.getPolicySeqID() != null)
        	else
        	{
        		cStmtObject.setString(3, null);
        	}//end of else
        	if(sumInsuredVO.getUpdatedBy() != null)
        	{
        		cStmtObject.setLong(4, sumInsuredVO.getUpdatedBy());
        	}//end of if(sumInsuredVO.getPolicySeqID() != null)
        	else
        	{
        		cStmtObject.setString(4, null);
        	}//end of else
            cStmtObject.registerOutParameter(5,OracleTypes.INTEGER);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(5);
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
        }//end of catch (Exception exp)
        finally
        {
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the result set
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveMemInsured()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveMemInsured()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of finally Statement Close
        	catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return iResult;
    }//end of clearAdditionalSumInsured(SumInsuredVO sumInsuredVO)

    /**
     * This method returns the ArrayList, which contains the Policy dependent List which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of DependentDetailVO'S object's which contains the details of the Policy dependent
     * @exception throws TTKException
     */
    public ArrayList dependentList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alDependentList = new ArrayList<Object>();
    	DependentDetailVO dependentDetailVO = new DependentDetailVO();
    	Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        String strLoginType = "";
        String strSuppressLink[]={"0","9"};
        String strSuppressLink1[]={"8"};
        String strSuppressLink2[]={"0","9"};
        try {
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSelectDependentList);
        	strLoginType = (String)alSearchCriteria.get(2);
        	cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
        	cStmtObject.setLong(2,(Long)alSearchCriteria.get(1));
        	cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
        	cStmtObject.execute();
        	rs = (java.sql.ResultSet) cStmtObject.getObject(3);
        	if(rs != null)
        	{
        		while(rs.next())
        		{
        			dependentDetailVO = new DependentDetailVO();
        			dependentDetailVO.setMemberSeqID( rs.getString("MEMBER_SEQ_ID")!=null ? new Long(rs.getInt("MEMBER_SEQ_ID")):null);
        			//Added for IBM....KOC-1216
					dependentDetailVO.setPolicy_Stop_YN(rs.getString("POLICY_OPTED"));//overwritten on NOV82012	by Praveen// getting selected Checkbox value from database.
					//Ended.

        			dependentDetailVO.setName(TTKCommon.checkNull(rs.getString("MEM_NAME")));

        			if(rs.getString("MEM_DOB") != null){
        				dependentDetailVO.setDateOfBirth(new Date(rs.getTimestamp("MEM_DOB").getTime()));
					}//end of if(rs.getString("MEM_DOB") != null)
        			if(rs.getString("MEM_AGE") != null){
        				dependentDetailVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}//end of if(rs.getString("MEM_AGE") != null)

        			dependentDetailVO.setGenderTypeID(TTKCommon.checkNull(rs.getString("GENDER_GENERAL_TYPE_ID")));
        			dependentDetailVO.setGenderDescription(TTKCommon.checkNull(rs.getString("GENDER")));
        			dependentDetailVO.setRelationTypeID(TTKCommon.checkNull(rs.getString("RELSHIP_TYPE_ID")));
        			dependentDetailVO.setRelationDesc(TTKCommon.checkNull(rs.getString("RELATIONSHIP")));

        			dependentDetailVO.setMemberTypeID(TTKCommon.checkNull(rs.getString("MEM_GENERAL_TYPE_ID")));
        			dependentDetailVO.setMemberType(TTKCommon.checkNull(rs.getString("MEMBER_TYPE")));

        			if(rs.getString("MEM_TOT_SUM_INSURED") != null){
                    	dependentDetailVO.setTotalSumInsured(new BigDecimal(rs.getString("MEM_TOT_SUM_INSURED")));
                    }//end of if(rs.getString("MEM_TOT_SUM_INSURED") != null)
                    else{
                    	dependentDetailVO.setTotalSumInsured(new BigDecimal("0.00"));
                    }//end of else

                    dependentDetailVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));

                    if(rs.getString("DATE_OF_INCEPTION") != null){
                    	dependentDetailVO.setInceptionDate(new Date(rs.getTimestamp("DATE_OF_INCEPTION").getTime()));
                    }//end of if(rs.getString("DATE_OF_INCEPTION") != null)

                    dependentDetailVO.setLoginType(strLoginType);
                    dependentDetailVO.setCancelYN(TTKCommon.checkNull(rs.getString("CANCEL_YN")));
                    //dependentDetailVO.setExpiredYN(TTKCommon.checkNull(rs.getString("EXPIRED_YN")));
                    dependentDetailVO.setWithinLimitYN(TTKCommon.checkNull(rs.getString("WITHIN_LIMIT_YN")));

                    if(TTKCommon.checkNull(rs.getString("CANCEL_YN")).equals("Y") &&
                    			TTKCommon.checkNull(rs.getString("MEM_GENERAL_TYPE_ID")).equals("PFL")){
                    	dependentDetailVO.setSuppressLink(strSuppressLink);
                    }//end of if(TTKCommon.checkNull(rs.getString("CANCEL_YN")).equals("Y"))
                    else if(TTKCommon.checkNull(rs.getString("CANCEL_YN")).equals("Y") &&
                			TTKCommon.checkNull(rs.getString("MEM_GENERAL_TYPE_ID")).equals("PNF")){
                    	dependentDetailVO.setSuppressLink(strSuppressLink);
                    }//end of else

                    if(strLoginType.equals("H")){
                    	dependentDetailVO.setAllowAddYN(TTKCommon.checkNull(rs.getString("ALLOW_ADD_HR_YN")));
                    	if(TTKCommon.checkNull(rs.getString("ALLOW_ADD_HR_YN")).equals("N")){

                    		dependentDetailVO.setSuppressLink(strSuppressLink2);
                    	}//end of if(TTKCommon.checkNull(rs.getString("ALLOW_ADD_HR_YN")).equals("N"))
                    	else if(TTKCommon.checkNull(rs.getString("ALLOW_ADD_HR_YN")).equals("Y")){
                    		dependentDetailVO.setDeleteImageName("DeleteIcon");
                    		dependentDetailVO.setDeleteImageTitle("Delete Member");
                    		if(TTKCommon.checkNull(rs.getString("ALLOW_ADDITIONAL_SUM_YN")).equals("N") || TTKCommon.checkNull(rs.getString("MEM_GENERAL_TYPE_ID")).equals("PFL")){
                    			dependentDetailVO.setSuppressLink(strSuppressLink1);
                    		}//end of if(TTKCommon.checkNull(rs.getString("ALLOW_ADDITIONAL_SUM_YN")).equals("N"))
                    	}//end of else

                    	if(TTKCommon.checkNull(rs.getString("ALLOW_ADD_HR_YN")).equals("Y") &&
                       		 TTKCommon.checkNull(rs.getString("EXPIRED_YN")).equals("N") &&
                       		 TTKCommon.checkNull(rs.getString("CANCEL_YN")).equals("N")){
                       	dependentDetailVO.setExpiredYN("N");
                       }//end of if
                       else{
                       	dependentDetailVO.setExpiredYN("Y");
                       }//end of else

                    }//end of if(strLoginType.equals("H"))
                    else if(strLoginType.equals("E")){
                    	dependentDetailVO.setAllowAddYN(TTKCommon.checkNull(rs.getString("ALLOW_ADD_EMP_YN")));
                    	if(TTKCommon.checkNull(rs.getString("ALLOW_ADD_EMP_YN")).equals("N")){
                    		dependentDetailVO.setSuppressLink(strSuppressLink2);
                    		//dependentDetailVO.setSuppressLink(strSuppressLink1);
                    	}//end of if(TTKCommon.checkNull(rs.getString("ALLOW_ADD_EMP_YN")).equals("N"))
                    	else if(TTKCommon.checkNull(rs.getString("ALLOW_ADD_EMP_YN")).equals("Y")){
                    		//dependentDetailVO.setDeleteImageName("DeleteIcon");
                    		//dependentDetailVO.setDeleteImageTitle("Delete Member");
                    		if(TTKCommon.checkNull(rs.getString("ALLOW_ADDITIONAL_SUM_YN")).equals("N") || TTKCommon.checkNull(rs.getString("MEM_GENERAL_TYPE_ID")).equals("PFL")){
                    			dependentDetailVO.setSuppressLink(strSuppressLink1);
                    		}//end of if(TTKCommon.checkNull(rs.getString("ALLOW_ADDITIONAL_SUM_YN")).equals("N"))
                    	}//end of else

                    	if(TTKCommon.checkNull(rs.getString("ALLOW_ADD_EMP_YN")).equals("Y") &&
                          		 TTKCommon.checkNull(rs.getString("EXPIRED_YN")).equals("N") &&
                          		 TTKCommon.checkNull(rs.getString("CANCEL_YN")).equals("N")){
                          	dependentDetailVO.setExpiredYN("N");
                          }//end of if
                          else{
                          	dependentDetailVO.setExpiredYN("Y");
                          }//end of else

                       //only for IBM
                    	if(rs.getString("TPA_ENROLLMENT_ID").contains("I310"))
                    	{
                    		if(TTKCommon.checkNull(rs.getString("MEM_EDIT_YN")).equals("N")){
                        		for(int i=0;i<strSuppressLink2.length;i++)
                        		{

                        		}
                               		dependentDetailVO.setSuppressLink(strSuppressLink2);
                        		//dependentDetailVO.setSuppressLink(strSuppressLink1);
                        	}//end of if(TTKCommon.checkNull(rs.getString("ALLOW_ADD_EMP_YN")).equals("N"))

                    	}

                    }//end of if(strLoginType.equals("E"))

                    dependentDetailVO.setAllowAddSumYN(TTKCommon.checkNull(rs.getString("ALLOW_ADDITIONAL_SUM_YN")));
                    dependentDetailVO.setFamilySumIconYN(TTKCommon.checkNull(rs.getString("SHOW_FAMILY_SUM_ICON_YN")));
                    dependentDetailVO.setAllowDeleteYN(TTKCommon.checkNull(rs.getString("ALLOW_DELETE_YN")));
					if(TTKCommon.checkNull(rs.getString("ALLOW_DELETE_YN")).equals("Y")){
                    		dependentDetailVO.setDeleteImageName("DeleteIcon");
                    		dependentDetailVO.setDeleteImageTitle("Delete Member");
                    		 }
                    dependentDetailVO.setFloaterSumStatus(TTKCommon.checkNull(rs.getString("FLOATER_SUM_STATUS")));

                    if(rs.getString("FLOATER_SUM_INSURED") != null){
                    	dependentDetailVO.setFloaterSumInsured(new BigDecimal(rs.getString("FLOATER_SUM_INSURED")));
                    }//end of if(rs.getString("FLOATER_SUM_INSURED") != null)
                    if(strLoginType.equals("E"))
                    {
                      dependentDetailVO.setLoginWindowPeriodAlert(TTKCommon.checkNull(rs.getString("FIRST_LOGIN_WINDOW_ALERT")));
                    }//end of if(strLoginType.equals("E"))

                    //Added for IBM for Age CR
                    if(rs.getString("DATE_OF_MARRIAGE") != null){
					   	dependentDetailVO.setDateOfMarriage(new Date(rs.getTimestamp("DATE_OF_MARRIAGE").getTime()));
					}//end of if(rs.getString("DATE_OF_MARRIAGE") != null)

                    alDependentList.add(dependentDetailVO);
        			//memberDetailVO.setMem
        		}//end of while(rs.next())
        	}//end of if(rs != null)
        }// end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl dependentList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl dependentList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl dependentList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
        return (ArrayList) alDependentList;
    }//end of dependentList(ArrayList  alSearchCriteria)

    /**
     * This method returns the ArrayList, which contains Groups corresponding to Policy Seq ID
     * @param lngPolicySeqID long Object, which contains the Policy Seq ID
     * @return ArrayList of MemberPermVO'S object's which contains the details of the Member permissions
     * @exception throws TTKException
     */
    public ArrayList<Object> getFieldInfo(long lngPolicySeqID,long lngPolicyGrpSeqID) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> alDisplayField = new ArrayList<Object>();
        MemberPermVO memberPermVO = null;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strFieldInfo);
            cStmtObject.setLong(1,lngPolicySeqID);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.setLong(3,lngPolicyGrpSeqID);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
            	while(rs.next()){
            		memberPermVO = new MemberPermVO();
         			memberPermVO.setPolMemFieldTypeID(TTKCommon.checkNull(rs.getString("POL_MEM_FIELD_TYPE_ID")));
         			memberPermVO.setFieldName(TTKCommon.checkNull(rs.getString("FIELD_NAME")));
         			memberPermVO.setMandatoryYN(TTKCommon.checkNull(rs.getString("MANDATORY_YN")));
         			memberPermVO.setFieldStatus(TTKCommon.checkNull(rs.getString("FIELD_STATUS")));
         			memberPermVO.setAddSumIconYN(TTKCommon.checkNull(rs.getString("ADD_SUM_ICON_YN")));

         			alDisplayField.add(memberPermVO);
         		}//end of while(rs.next())
            }//end of if(rs != null)
            return (ArrayList<Object>) alDisplayField;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Connection in OnlineAccessDAOImpl getFieldInfo()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getFieldInfo()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getFieldInfo()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getFieldInfo(long lngPolicySeqID)

    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteGeneral(ArrayList alDeleteList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteGeneral);
			cStmtObject.setString(1, (String)alDeleteList.get(0));//FLAG - MEM

			if(alDeleteList.get(1) != null){
				cStmtObject.setLong(2,(Long)alDeleteList.get(1));//Mandatory If Flag is MEM - MEMBER_SEQ_ID
			}//end of if(alDeleteList.get(1) != null)
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(alDeleteList.get(2) != null){
				cStmtObject.setLong(3,(Long)alDeleteList.get(2));//Mandatory  POLICY_SEQ_ID
			}//end of if(alDeleteList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			cStmtObject.setLong(4,(Long)alDeleteList.get(3));//ADDED_BY
			cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);//ROWS_PROCESSED
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl deleteGeneral()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl deleteGeneral()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
	}//end of deleteGeneral(ArrayList alDeleteList)

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
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getEmployeePasswordInfo()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getEmployeePasswordInfo()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getEmployeePasswordInfo()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
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
   /* public int resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID) throws TTKException {
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
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
		}//end of catch (Exception exp)
		finally
		{
        	 Nested Try Catch to ensure resource closure
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl resetEmployeePassword()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl resetEmployeePassword()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
    }//end of resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID)
*/
    /**
     * This method resets the Employee Password
     * @param lngPolicyGrpSeqID the policy group sequence id
     * @param lngUserSeqID logged-in user seq id
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public String resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID) throws TTKException {
    	String strResult = null;
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strResetEmpPassword);
			cStmtObject.registerOutParameter(1,Types.VARCHAR);
			cStmtObject.setLong(2,lngPolicyGrpSeqID);
			cStmtObject.setLong(3,lngUserSeqID);
			cStmtObject.execute();
			strResult = cStmtObject.getString(1);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl resetEmployeePassword()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl resetEmployeePassword()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return strResult;
    }//end of resetEmployeePassword(long lngPolicyGrpSeqID,long lngUserSeqID)

    /**
     * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList getPolicyNumber(String strGroupID) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> alPolicyNbr = new ArrayList<Object>();
        CacheObject cacheObject = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt=conn.prepareStatement(strPolicyNumber);

            pStmt.setString(1,strGroupID);
            rs = pStmt.executeQuery();

            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();

                    cacheObject.setCacheId((rs.getString("POLICY_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    alPolicyNbr.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alPolicyNbr;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Connection in OnlineAccessDAOImpl getPolicyNumber()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getPolicyNumber()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getPolicyNumber()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPolicyNumber(String strGroupID)

  //kocBroker
    public ArrayList getPolicyNumberBro(long lngUserseqID,String strUserID) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> alPolicyNbr = new ArrayList<Object>();
        CacheObject cacheObject = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt=conn.prepareStatement(strPolicyNumberBro);

            pStmt.setLong(1,lngUserseqID);
            pStmt.setString(2,strUserID);
            rs = pStmt.executeQuery();

            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();

                    cacheObject.setCacheId((rs.getString("POLICY_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    alPolicyNbr.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alPolicyNbr;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Connection in OnlineAccessDAOImpl getPolicyNumber()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getPolicyNumber()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getPolicyNumber()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPolicyNumber(String strGroupID)

    /**
     * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public static ArrayList<Object> getPolicyNumberInfo(String strGroupID) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> alPolicyNbr = new ArrayList<Object>();
        Connection conn = null;
        PreparedStatement pStmt = null;
        OnlinePolicyInfoVO onlinePolicyInfoVO = null;
        try{
            conn = ResourceManager.getConnection();
           // pStmt=conn.prepareStatement(strPolicyNumberInfo);
            //Modification as per KOC12227B

            if(strGroupID.equalsIgnoreCase("A0912"))
            {
                   pStmt=conn.prepareStatement(strACCPolicyNumberInfo);
                   pStmt.setString(1,strGroupID);

            }
            else{
            pStmt=conn.prepareStatement(strPolicyNumberInfo);
            pStmt.setString(1,strGroupID);
            pStmt.setString(2,strGroupID);

            }
            //Modification as per KOC12227B

            rs = pStmt.executeQuery();

            if(rs != null){
                while(rs.next()){

                    onlinePolicyInfoVO = new OnlinePolicyInfoVO();
                    onlinePolicyInfoVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					if(strGroupID.equalsIgnoreCase("A0912"))
                    {
                        String dataStr=TTKCommon.checkNull(rs.getString("POLICY_NUMBER"));
                        int chk =dataStr.lastIndexOf("/");
                        if(chk != -1)
                        {
                            dataStr = dataStr.substring(0, chk);
                        }
                        onlinePolicyInfoVO.setPolicyNbr1(dataStr);
                    }
                    onlinePolicyInfoVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
                    onlinePolicyInfoVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));

                    if(rs.getString("EFFECTIVE_FROM_DATE") != null){
                    	onlinePolicyInfoVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)
                    if(rs.getString("EFFECTIVE_TO_DATE") != null){
                    	onlinePolicyInfoVO.setEndDate(new Date(rs.getTimestamp("EFFECTIVE_TO_DATE").getTime()));
                    }//end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

                    alPolicyNbr.add(onlinePolicyInfoVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alPolicyNbr;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Connection in OnlineAccessDAOImpl getPolicyNumber()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getPolicyNumber()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getPolicyNumber()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getPolicyNumberInfo(String strGroupID)

    /**
	 * This method returns the ArrayList which contains Relationship Code
	 * @param lngPolicySeqID Policy Seq ID
	 * @param strAbbrCode String object which contains Insurance Company Abbrevation Code to fetch the Relationship Code
	 * @return ArrayList which contains Relationship Code
	 * @exception throws TTKException
	 */
	public ArrayList getRelationshipCode(long lngPolicySeqID,String strAbbrCode) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		CacheObject cacheObject = null;
        ArrayList<Object> alRelationshipCode = new ArrayList<Object>();
        try{
        	conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetRelationshipCode);

			cStmtObject.setLong(1,lngPolicySeqID);
			cStmtObject.setString(2,strAbbrCode);
			cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();
                    cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("RELSHIP")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("RELSHIP_DESCRIPTION")));
                    alRelationshipCode.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getRelationshipCode()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getRelationshipCode()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getRelationshipCode()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alRelationshipCode;
	}//end of getRelationshipCode(long lngPolicySeqID,String strAbbrCode)

	/**
     * This method returns the Arraylist of OnlineHospitalVO's  which contains Hospital details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineHospitalVO object which contains Hospital details
     * @exception throws TTKException
     */
    public ArrayList getHospitalList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	OnlineHospitalVO onlineHospitalVO = null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHospitalList);

			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(5));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setLong(9,(Long)alSearchCriteria.get(4));
			cStmtObject.registerOutParameter(10,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(10);

			if(rs != null){
				while(rs.next()){
					onlineHospitalVO = new OnlineHospitalVO();

					if(rs.getString("HOSP_SEQ_ID") != null){
						onlineHospitalVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
					}//end of if(rs.getString("HOSP_SEQ_ID") != null)

					onlineHospitalVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					onlineHospitalVO.setAddress(TTKCommon.checkNull(rs.getString("ADDRESS")));
					onlineHospitalVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY_DESCRIPTION")));
					onlineHospitalVO.setStateName(TTKCommon.checkNull(rs.getString("STATE_NAME")));

					alResultList.add(onlineHospitalVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getHospitalList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getHospitalList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getHospitalList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
    }//end of getHospitalList(ArrayList alSearchCriteria)

    /**
     * This method returns the Arraylist of OnlineIntimationVO's  which contains Intimation details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineIntimationVO object which contains Intimation details
     * @exception throws TTKException
     */
    public ArrayList getIntimationList(ArrayList alSearchCriteria) throws TTKException {

    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	OnlineIntimationVO onlineIntimationVO = null;
    	String strSuppressLink[]={"6"};
    	try{
    		conn = ResourceManager.getConnection();

    		if(alSearchCriteria.get(0).equals("Pre-Auth")){
    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPatIntimationList);
    		}//end of if(alSearchCriteria.get(0).equals("Pre-Auth"))
    		else if(alSearchCriteria.get(0).equals("Claims")){
    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClmIntimationList);
    		}//end of if(alSearchCriteria.get(0).equals("Claims"))

    		cStmtObject.setLong(1,(Long)alSearchCriteria.get(1));
			cStmtObject.setString(2,(String)alSearchCriteria.get(4));
			cStmtObject.setString(3,(String)alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				while(rs.next()){
					onlineIntimationVO = new OnlineIntimationVO();
					if(rs.getString("PAT_INTIMATION_SEQ_ID") != null){
						onlineIntimationVO.setIntimationSeqID(new Long(rs.getLong("PAT_INTIMATION_SEQ_ID")));
					}//end of if(rs.getString("PAT_INTIMATION_SEQ_ID") != null)

					onlineIntimationVO.setIntimationNbr(TTKCommon.checkNull(rs.getString("PAT_INTIMATION_ID")));
					onlineIntimationVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					onlineIntimationVO.setMemName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					onlineIntimationVO.setGenderDesc(TTKCommon.checkNull(rs.getString("GENDER")));
					if(rs.getString("INTIMATION_GENERATED_DATE") != null){
						onlineIntimationVO.setIntGenDate(new Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime()));
					}//end of if(rs.getString("INTIMATION_GENERATED_DATE") != null)

					if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
						onlineIntimationVO.setPolicyGrpSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
					}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

					if(rs.getString("MEMBER_SEQ_ID") != null){
						onlineIntimationVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)
					onlineIntimationVO.setGenderDesc(TTKCommon.checkNull(rs.getString("GENDER")));
					onlineIntimationVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("STATUS_GENERAL_TYPE_ID")));
					onlineIntimationVO.setStatusDesc(TTKCommon.checkNull(rs.getString("STATUS_DESC")));

					if(!TTKCommon.checkNull(rs.getString("STATUS_GENERAL_TYPE_ID")).equals("PIY")){
						onlineIntimationVO.setSuppressLink(strSuppressLink);
					}//end of if(!TTKCommon.checkNull(rs.getString("STATUS_GENERAL_TYPE_ID")).equals("PIY"))

					alResultList.add(onlineIntimationVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getIntimationList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getIntimationList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getIntimationList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
    }//end of getIntimationList(ArrayList alSearchCriteria)

    /**
     * This method saves the Preauth Intimation Information
     * @param onlineIntimationVO OnlineIntimationVO which contains the online Intimation Information
     * @param strGroupID Group ID
     * @return lngIntimationSeqID long Object, which contains the Intimation Seq ID
     * @exception throws TTKException
     */
    public long savePreauthIntimation(OnlineIntimationVO onlineIntimationVO,String strGroupID) throws TTKException
    {
        Connection conn = null;
        CallableStatement cStmtObject=null;
        long lngPatIntimationSeqID = 0;
        StringBuffer sbfSQL = null;
        Statement stmt = null;
        OnlineHospitalVO onlineHospitalVO = null;
        try {
            conn = ResourceManager.getConnection();
            conn.setAutoCommit(false);
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPatIntimationSave);
            stmt = (java.sql.Statement)conn.createStatement();

            onlineHospitalVO = onlineIntimationVO.getOnlineHospitalVO();

            if(onlineIntimationVO.getIntimationSeqID()!=null){
                cStmtObject.setLong(1,onlineIntimationVO.getIntimationSeqID());   //PAT_INTIMATION_SEQ_ID
            }//end of if(onlineIntimationVO.getIntimationSeqID()!=null)
            else
        	{
        		cStmtObject.setLong(1, 0);
        	}//end  of else

            if(onlineIntimationVO.getPolicyGrpSeqID()!=null){
                cStmtObject.setLong(2,onlineIntimationVO.getPolicyGrpSeqID());   //POLICY_GROUP_SEQ_ID
            }//end of if(onlineIntimationVO.getPolicyGrpSeqID!=null)
            else{
                cStmtObject.setString(2,null);
            }//end of else

            if(onlineIntimationVO.getMemberSeqID()!=null){
                cStmtObject.setLong(3,onlineIntimationVO.getMemberSeqID());   //MEMBER_SEQ_ID
            }//end of if(onlineIntimationVO.getMemberSeqID!=null)
            else{
                cStmtObject.setString(3,null);
            }//end of else
            cStmtObject.setString(4,strGroupID);//GROUP_ID

            if(onlineIntimationVO.getExpectedDOA() != null){
				cStmtObject.setTimestamp(5,new Timestamp(onlineIntimationVO.getExpectedDOA().getTime()));
			}//end of if(onlineIntimationVO.getExpectedDOA() != null)
			else{
				cStmtObject.setTimestamp(5, null);
			}//end of else

            if(onlineIntimationVO.getExpectedDOD() != null){
				cStmtObject.setTimestamp(6,new Timestamp(onlineIntimationVO.getExpectedDOD().getTime()));
			}//end of if(onlineIntimationVO.getExpectedDOD() != null)
			else{
				cStmtObject.setTimestamp(6, null);
			}//end of else

            cStmtObject.setString(7,onlineIntimationVO.getAilmentDesc());//AILMENT_DESCRIPTION
            cStmtObject.setString(8,onlineIntimationVO.getProvisionalDiagnosis());//PROVISIONAL_DIAGNOSIS
            cStmtObject.setString(9,onlineIntimationVO.getPhoneNbr());//CLAIMANT_PHONE_NO
            cStmtObject.setString(10,onlineIntimationVO.getMobileNbr());//CLAIMANT_MOBILE_NO
            cStmtObject.setString(11,onlineIntimationVO.getEmailID());//CLAIMANT_EMAIL_ID
            cStmtObject.setString(12,onlineIntimationVO.getRemarks());//CLAIMANT_COMMENTS
            cStmtObject.setString(13,onlineIntimationVO.getSubmittedYN());//INTIMATION_SUBMITTED_YN
            cStmtObject.setLong(14,onlineIntimationVO.getUpdatedBy());//ADDED_BY
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.execute();
            lngPatIntimationSeqID = cStmtObject.getLong(1);

            if(onlineHospitalVO != null){
				if(onlineIntimationVO.getOnlineHospitalVO().getHospSeqId1() != null){

					sbfSQL = new StringBuffer();
					sbfSQL = sbfSQL.append("'").append(lngPatIntimationSeqID).append("',");

					if(onlineIntimationVO.getOnlineHospitalVO().getHospIntSeqID1() == null){
						sbfSQL = sbfSQL.append("0,");
					}//end of if(onlineIntimationVO.getOnlineHospitalVO().getHospIntSeqID() == null)
					else{
						sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getHospIntSeqID1()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getHospSeqId1()).append("',");
					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getDoctorName1()).append("',");
					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getRoomType1()).append("',");
					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getDoctorPhoneNbr1()).append("',");

					if(onlineIntimationVO.getOnlineHospitalVO().getEstimatedCost1()== null){
						sbfSQL = sbfSQL.append("null,");
					}//end of if(onlineIntimationVO.getOnlineHospitalVO().getEstimatedCost1()==null)
					else{
						sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getEstimatedCost1()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getUpdatedBy()).append("')}");
					stmt.addBatch(strSaveIntimationHospital+sbfSQL.toString());
				}//end of if(onlineHospitalVO.getHospSeqId1() != null)
				if(onlineIntimationVO.getOnlineHospitalVO().getHospSeqId2() != null){

					sbfSQL = new StringBuffer();
					sbfSQL = sbfSQL.append("'").append(lngPatIntimationSeqID).append("',");

					if(onlineIntimationVO.getOnlineHospitalVO().getHospIntSeqID2() == null){
						sbfSQL = sbfSQL.append("0,");
					}//end of if(onlineHospitalVO.getHospIntSeqID2() == null)
					else{
						sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getHospIntSeqID2()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getHospSeqId2()).append("',");
					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getDoctorName2()).append("',");
					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getRoomType2()).append("',");
					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getDoctorPhoneNbr2()).append("',");

					if(onlineIntimationVO.getOnlineHospitalVO().getEstimatedCost2()== null){
						sbfSQL = sbfSQL.append("null,");
					}//end of if(onlineHospitalVO.getEstimatedCost2()==null)
					else{
						sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getEstimatedCost2()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getUpdatedBy()).append("')}");
					stmt.addBatch(strSaveIntimationHospital+sbfSQL.toString());
				}//end of if(onlineHospitalVO.getHospSeqId2() != null)
				if(onlineIntimationVO.getOnlineHospitalVO().getHospSeqId3() != null){

					sbfSQL = new StringBuffer();
					sbfSQL = sbfSQL.append("'").append(lngPatIntimationSeqID).append("',");

					if(onlineIntimationVO.getOnlineHospitalVO().getHospIntSeqID3() == null){
						sbfSQL = sbfSQL.append("0,");
					}//end of if(onlineHospitalVO.getHospIntSeqID3() == null)
					else{
						sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getHospIntSeqID3()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getHospSeqId3()).append("',");
					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getDoctorName3()).append("',");
					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getRoomType3()).append("',");
					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getDoctorPhoneNbr3()).append("',");

					if(onlineIntimationVO.getOnlineHospitalVO().getEstimatedCost3()== null){
						sbfSQL = sbfSQL.append("null,");
					}//end of if(onlineIntimationVO.getOnlineHospitalVO().getEstimatedCost3()==null)
					else{
						sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getOnlineHospitalVO().getEstimatedCost3()).append("',");
					}//end of else

					sbfSQL = sbfSQL.append("'").append(onlineIntimationVO.getUpdatedBy()).append("')}");
					stmt.addBatch(strSaveIntimationHospital+sbfSQL.toString());
				}//end of if(onlineHospitalVO.getHospSeqId3() != null)
			}//end of if(onlineHospitalVO != null)

            stmt.executeBatch();
            conn.commit();
        }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl savePreauthIntimation()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{

        			try{
        				if (stmt != null) stmt.close();
        			}//end of try
        			catch (SQLException sqlExp)
            		{
            			log.error("Error while closing the Statement in OnlineAccessDAOImpl savePreauthIntimation()",sqlExp);
            			throw new TTKException(sqlExp, "onlineforms");
            		}//end of catch (SQLException sqlExp)
        			finally{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in OnlineAccessDAOImpl savePreauthIntimation()",sqlExp);
        					throw new TTKException(sqlExp, "onlineforms");
        				}//end of catch (SQLException sqlExp)
        			}//end of finally
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		stmt = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngPatIntimationSeqID;
     } //end of savePreauthIntimation(OnlineIntimationVO onlineIntimationVO)

    /**
     * This method saves the Claim Intimation Information
     * @param onlineIntimationVO OnlineIntimationVO which contains the online Intimation Information
     * @param strGroupID Group ID
     * @return lngIntimationSeqID long Object, which contains the Intimation Seq ID
     * @exception throws TTKException
     */
    public long saveClaimIntimation(OnlineIntimationVO onlineIntimationVO,String strGroupID) throws TTKException {
    	Connection conn = null;
        CallableStatement cStmtObject=null;
        long lngIntimationSeqID = 0;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimIntimationSave);

            if(onlineIntimationVO.getIntimationSeqID() != null){
            	cStmtObject.setLong(1,onlineIntimationVO.getIntimationSeqID());
            }//end of if(onlineIntimationVO.getIntimationSeqID() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else

            cStmtObject.setLong(2,onlineIntimationVO.getMemberSeqID());
            cStmtObject.setString(3,strGroupID);
            cStmtObject.setString(4,onlineIntimationVO.getPhoneNbr());
            cStmtObject.setString(5,onlineIntimationVO.getMobileNbr());
            cStmtObject.setString(6,onlineIntimationVO.getEmailID());

            if(onlineIntimationVO.getEstimatedCost() != null){
            	cStmtObject.setBigDecimal(7,onlineIntimationVO.getEstimatedCost());
            }//end of if(onlineIntimationVO.getEstimatedCost() != null)
            else{
            	cStmtObject.setString(7,null);
            }//end of else

            if(onlineIntimationVO.getExpectedDOA() != null){
            	cStmtObject.setTimestamp(8,new Timestamp(onlineIntimationVO.getExpectedDOA().getTime()));
		    }//end of if(onlineIntimationVO.getExpectedDOA() != null)
		    else{
		    	cStmtObject.setTimestamp(8, null);
		    }//end of else

            if(onlineIntimationVO.getExpectedDOD() != null){
            	cStmtObject.setTimestamp(9,new Timestamp(onlineIntimationVO.getExpectedDOD().getTime()));
		    }//end of if(onlineIntimationVO.getExpectedDOD() != null)
		    else{
		    	cStmtObject.setTimestamp(9, null);
		    }//end of else

            cStmtObject.setString(10,onlineIntimationVO.getAilmentDesc());
            cStmtObject.setString(11,onlineIntimationVO.getHospitalName());
            cStmtObject.setString(12,onlineIntimationVO.getHospAddress());
            cStmtObject.setString(13,onlineIntimationVO.getSubmittedYN());
            cStmtObject.setString(14,onlineIntimationVO.getProvisionalDiagnosis());
            cStmtObject.setLong(15,onlineIntimationVO.getUpdatedBy());
            cStmtObject.registerOutParameter(16,Types.INTEGER);
            cStmtObject.registerOutParameter(1,Types.BIGINT);
            cStmtObject.execute();
            lngIntimationSeqID = cStmtObject.getLong(1);
        }//end of try
        catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveClaimIntimation()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveClaimIntimation()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return lngIntimationSeqID;
    }//end of saveClaimIntimation(OnlineIntimationVO onlineIntimationVO,String strGroupID)

    /**
     * This method returns the OnlineIntimationVO which contains the Intimation information
     * @param lngIntimationSeqID Intimation Seq ID
     * @param strIdentifier Identifier - Pre-Auth/Claims
     * @return OnlineIntimationVO the contains the Intimation Information
     * @exception throws TTKException
     */
    public OnlineIntimationVO selectIntimation(long lngIntimationSeqID,String strIdentifier) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	OnlineIntimationVO onlineIntimationVO = null;
    	OnlineHospitalVO onlineHospitalVO = null;
    	try{
    		conn = ResourceManager.getConnection();

    		if(strIdentifier.equals("Pre-Auth")){
    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetPreauthIntimationDetail);
    		}//end of if(strIdentifier.equals("Pre-Auth"))
    		else if(strIdentifier.equals("Claims")){
    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetClaimIntimationDetail);
    		}//end of if(strIdentifier.equals("Claims"))

    		cStmtObject.setLong(1,lngIntimationSeqID);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);

            if(rs != null){
            	while(rs.next()){
            		onlineIntimationVO = new OnlineIntimationVO();

            		if(strIdentifier.equals("Pre-Auth")){
            			onlineHospitalVO = new OnlineHospitalVO();
            			if(rs.getString("PAT_INTIMATION_SEQ_ID") != null){
                			onlineIntimationVO.setIntimationSeqID(new Long(rs.getLong("PAT_INTIMATION_SEQ_ID")));
                		}//end of if(rs.getString("PAT_INTIMATION_SEQ_ID") != null)

                		onlineIntimationVO.setIntimationNbr(TTKCommon.checkNull(rs.getString("PAT_INTIMATION_ID")));

                		if(rs.getString("INTIMATION_GENERATED_DATE") != null){
                			onlineIntimationVO.setIntGenDate(new java.util.Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime()));
                			onlineIntimationVO.setIntGenTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime())).split(" ")[1]:"");
                			onlineIntimationVO.setIntGenDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime())).split(" ")[2]:"");
                        }//end of if(rs.getString("INTIMATION_GENERATED_DATE") != null)

                		if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
                			onlineIntimationVO.setPolicyGrpSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
                		}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

                		if(rs.getString("MEMBER_SEQ_ID") != null){
                			onlineIntimationVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
                		}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

                		onlineIntimationVO.setMemName(TTKCommon.checkNull(rs.getString("MEM_NAME")));

                		if(rs.getString("EXPECTED_DOA") != null){
                			onlineIntimationVO.setExpectedDOA(new java.util.Date(rs.getTimestamp("EXPECTED_DOA").getTime()));
                        }//end of if(rs.getString("EXPECTED_DOA") != null)

                		if(rs.getString("EXPECTED_DOD") != null){
                			onlineIntimationVO.setExpectedDOD(new java.util.Date(rs.getTimestamp("EXPECTED_DOD").getTime()));
                        }//end of if(rs.getString("EXPECTED_DOD") != null)

                		onlineIntimationVO.setAilmentDesc(TTKCommon.checkNull(rs.getString("AILMENT_DESCRIPTION")));
                		onlineIntimationVO.setProvisionalDiagnosis(TTKCommon.checkNull(rs.getString("PROVISIONAL_DIAGNOSIS")));
                		onlineIntimationVO.setPhoneNbr(TTKCommon.checkNull(rs.getString("CLAIMANT_PHONE_NO")));
                		onlineIntimationVO.setMobileNbr(TTKCommon.checkNull(rs.getString("CLAIMANT_MOBILE_NO")));
                		onlineIntimationVO.setEmailID(TTKCommon.checkNull(rs.getString("CLAIMANT_EMAIL_ID")));
                		onlineIntimationVO.setRemarks(TTKCommon.checkNull(rs.getString("CLAIMANT_COMMENTS")));
                		onlineIntimationVO.setSubmittedYN(TTKCommon.checkNull(rs.getString("INTIMATION_SUBMITTED_YN")));
                		onlineIntimationVO.setTTKRemarks(TTKCommon.checkNull(rs.getString("TPA_COMMENTS")));
                		onlineIntimationVO.setTTKNarrative(TTKCommon.checkNull(rs.getString("TPA_NARRATIVE")));

                		if(rs.getString("TPA_RESPONDED_DATE") != null){
                			onlineIntimationVO.setTTKRespondedDate(new java.util.Date(rs.getTimestamp("TPA_RESPONDED_DATE").getTime()));
                			onlineIntimationVO.setTTKRespondedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("TPA_RESPONDED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("TPA_RESPONDED_DATE").getTime())).split(" ")[1]:"");
                			onlineIntimationVO.setTTKRespondedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("TPA_RESPONDED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("TPA_RESPONDED_DATE").getTime())).split(" ")[2]:"");
                        }//end of if(rs.getString("TPA_RESPONDED_DATE") != null)

                		if(rs.getString("INT_HOSP_SEQ1") != null){
                			onlineHospitalVO.setHospIntSeqID1(new Long(rs.getLong("INT_HOSP_SEQ1")));
                		}//end of if(rs.getString("INT_HOSP_SEQ1") != null)

                		if(rs.getString("INT_HOSP_SEQ2") != null){
                			onlineHospitalVO.setHospIntSeqID2(new Long(rs.getLong("INT_HOSP_SEQ2")));
                		}//end of if(rs.getString("INT_HOSP_SEQ2") != null)

                		if(rs.getString("INT_HOSP_SEQ3") != null){
                			onlineHospitalVO.setHospIntSeqID3(new Long(rs.getLong("INT_HOSP_SEQ3")));
                		}//end of if(rs.getString("INT_HOSP_SEQ3") != null)

                		if(rs.getString("HOSP_SEQ_ID1") != null){
                			onlineHospitalVO.setHospSeqId1(new Long(rs.getLong("HOSP_SEQ_ID1")));
                		}//end of if(rs.getString("HOSP_SEQ_ID1") != null)

                		if(rs.getString("HOSP_SEQ_ID2") != null){
                			onlineHospitalVO.setHospSeqId2(new Long(rs.getLong("HOSP_SEQ_ID2")));
                		}//end of if(rs.getString("HOSP_SEQ_ID2") != null)

                		if(rs.getString("HOSP_SEQ_ID3") != null){
                			onlineHospitalVO.setHospSeqId3(new Long(rs.getLong("HOSP_SEQ_ID3")));
                		}//end of if(rs.getString("HOSP_SEQ_ID3") != null)

                		onlineHospitalVO.setHospitalName1(TTKCommon.checkNull(rs.getString("HOSP_NAME1")));
                		onlineHospitalVO.setHospitalName2(TTKCommon.checkNull(rs.getString("HOSP_NAME2")));
                		onlineHospitalVO.setHospitalName3(TTKCommon.checkNull(rs.getString("HOSP_NAME3")));
                		onlineHospitalVO.setAddress1(TTKCommon.checkNull(rs.getString("HOSP_ADDRESS1")));
                		onlineHospitalVO.setAddress2(TTKCommon.checkNull(rs.getString("HOSP_ADDRESS2")));
                		onlineHospitalVO.setAddress3(TTKCommon.checkNull(rs.getString("HOSP_ADDRESS3")));
                		onlineHospitalVO.setDoctorName1(TTKCommon.checkNull(rs.getString("DR_NAME1")));
                		onlineHospitalVO.setDoctorName2(TTKCommon.checkNull(rs.getString("DR_NAME2")));
                		onlineHospitalVO.setDoctorName3(TTKCommon.checkNull(rs.getString("DR_NAME3")));
                		onlineHospitalVO.setRoomType1(TTKCommon.checkNull(rs.getString("HOSP_ROOM_TYPE1")));
                		onlineHospitalVO.setRoomType2(TTKCommon.checkNull(rs.getString("HOSP_ROOM_TYPE2")));
                		onlineHospitalVO.setRoomType3(TTKCommon.checkNull(rs.getString("HOSP_ROOM_TYPE3")));
                		onlineHospitalVO.setDoctorPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_11")));
                		onlineHospitalVO.setDoctorPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_12")));
                		onlineHospitalVO.setDoctorPhoneNbr3(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_13")));

                		if(rs.getString("ESTIMATED_AMOUNT1") != null){
                			onlineHospitalVO.setEstimatedCost1(new BigDecimal(rs.getString("ESTIMATED_AMOUNT1")));
    					}//end of if(rs.getString("ESTIMATED_AMOUNT1") != null)

                		if(rs.getString("ESTIMATED_AMOUNT2") != null){
                			onlineHospitalVO.setEstimatedCost2(new BigDecimal(rs.getString("ESTIMATED_AMOUNT2")));
    					}//end of if(rs.getString("ESTIMATED_AMOUNT2") != null)

                		if(rs.getString("ESTIMATED_AMOUNT3") != null){
                			onlineHospitalVO.setEstimatedCost3(new BigDecimal(rs.getString("ESTIMATED_AMOUNT3")));
    					}//end of if(rs.getString("ESTIMATED_AMOUNT3") != null)

                		onlineIntimationVO.setOnlineHospitalVO(onlineHospitalVO);
            		}//end of if(strIdentifier.equals("Pre-Auth"))
            		else if(strIdentifier.equals("Claims")){
            			if(rs.getString("CALL_LOG_SEQ_ID") != null){
                			onlineIntimationVO.setIntimationSeqID(new Long(rs.getLong("CALL_LOG_SEQ_ID")));
                		}//end of if(rs.getString("CALL_LOG_SEQ_ID") != null)

                		onlineIntimationVO.setIntimationNbr(TTKCommon.checkNull(rs.getString("CLM_INTIMATION_ID")));

                		if(rs.getString("INTIMATION_GENERATED_DATE") != null){
                			onlineIntimationVO.setIntGenDate(new java.util.Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime()));
                			onlineIntimationVO.setIntGenTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime())).split(" ")[1]:"");
                			onlineIntimationVO.setIntGenDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("INTIMATION_GENERATED_DATE").getTime())).split(" ")[2]:"");
                        }//end of if(rs.getString("INTIMATION_GENERATED_DATE") != null)

                		if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
                			onlineIntimationVO.setPolicyGrpSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
                		}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

                		if(rs.getString("MEMBER_SEQ_ID") != null){
                			onlineIntimationVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
                		}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

                		onlineIntimationVO.setMemName(TTKCommon.checkNull(rs.getString("MEM_NAME")));

                		if(rs.getString("LIKELY_DATE_OF_HOSPITALISATION") != null){
                			onlineIntimationVO.setExpectedDOA(new java.util.Date(rs.getTimestamp("LIKELY_DATE_OF_HOSPITALISATION").getTime()));
                        }//end of if(rs.getString("LIKELY_DATE_OF_HOSPITALISATION") != null)

                		if(rs.getString("DATE_OF_DISCHARGE") != null){
                			onlineIntimationVO.setExpectedDOD(new java.util.Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime()));
                        }//end of if(rs.getString("DATE_OF_DISCHARGE") != null)

                		onlineIntimationVO.setAilmentDesc(TTKCommon.checkNull(rs.getString("AILMENT_DESCRIPTION")));
                		onlineIntimationVO.setProvisionalDiagnosis(TTKCommon.checkNull(rs.getString("PROVISIONAL_DIAGNOSIS")));
                		onlineIntimationVO.setPhoneNbr(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
                		onlineIntimationVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
                		onlineIntimationVO.setEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
                		onlineIntimationVO.setSubmittedYN(TTKCommon.checkNull(rs.getString("INTIMATION_SUBMITTED_YN")));
                		onlineIntimationVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSPITAL_NAME")));
                		onlineIntimationVO.setHospAddress(TTKCommon.checkNull(rs.getString("HOSPITAL_ADDRESS")));

                		if(rs.getString("ESTIMATED_AMOUNT") != null){
                			onlineIntimationVO.setEstimatedCost(new BigDecimal(rs.getString("ESTIMATED_AMOUNT")));
    					}//end of if(rs.getString("ESTIMATED_AMOUNT") != null)
            		}//end of if(strIdentifier.equals("Claims"))
            	}//end of while(rs.next())
            }//end of if(rs != null)

    	}//end of try
    	catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl selectIntimation()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl selectIntimation()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl selectIntimation()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return onlineIntimationVO;
    }//end of selectIntimation(long lngIntimationSeqID,String strIdentifier)

    /**
	 * This method deletes the requested information from the database
	 * @param alDeleteList the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int deleteIntimation(ArrayList alDeleteList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteIntimation);
			cStmtObject.setString(1, (String)alDeleteList.get(0));//FLAG - PAT-Preauth/CLM-Claims/HOS - Cancel Hospital

			if(alDeleteList.get(0).equals("PAT")|| alDeleteList.get(0).equals("CLM")){
				cStmtObject.setString(2,(String)alDeleteList.get(1));//Concateneated String of pat_intimation_seq_id/call_log_seq_id
			}//end of if(alDeleteList.get(0).equals("PAT")|| alDeleteList.get(0).equals("CLM"))
			else{
				cStmtObject.setString(2,null);
			}//end of else

			if(alDeleteList.get(2) != null){
				cStmtObject.setLong(3,(Long)alDeleteList.get(2));//hosp_int_seq_id
			}//end of if(alDeleteList.get(2) != null)
			else{
				cStmtObject.setString(3,null);
			}//end of else

			cStmtObject.registerOutParameter(4, Types.INTEGER);//ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);//ROWS_PROCESSED
		}//end of try
		catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl deleteIntimation()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl deleteIntimation()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
	}//end of deleteIntimation(ArrayList alDeleteList)

    /**
     * This method returns the ArrayList, which contains EnrollmentID&Member Name corresponding to Policy Group Seq ID
     * @param lngPolicyGroupSeqID It contains the Policy Group Seq ID
     * @return ArrayList object which contains EnrollmentID&Member Name corresponding to Policy Group Seq ID
     * @exception throws TTKException
     */
    public ArrayList getOnlineMember(long lngPolicyGroupSeqID) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> alPolicyNbr = new ArrayList<Object>();
        CacheObject cacheObject = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt=conn.prepareStatement(strOnlineMember);

            pStmt.setLong(1,lngPolicyGroupSeqID);
            rs = pStmt.executeQuery();

            if(rs != null){
                while(rs.next()){
                    cacheObject = new CacheObject();

                    cacheObject.setCacheId((rs.getString("MEMBER_SEQ_ID")));
                    cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("MEMBER_NAME")));
                    alPolicyNbr.add(cacheObject);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alPolicyNbr;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlineMember()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlineMember()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlineMember()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getOnlineMember(long lngPolicyGroupSeqID)

    /**
     * This method returns the Arraylist of OnlineInsPolicyVO's  which contains Policy details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of OnlineInsPolicyVO object which contains Policy details
     * @exception throws TTKException
     */
    public ArrayList getInsEnrollmentList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		OnlineInsPolicyVO onlineInsPolicyVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInsEnrollmentList);

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
			cStmtObject.registerOutParameter(12,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(12);

			if(rs != null){
				while(rs.next()){
					onlineInsPolicyVO = new OnlineInsPolicyVO();

					if(rs.getString("MEMBER_SEQ_ID") != null){
						onlineInsPolicyVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if(rs.getString("POLICY_SEQ_ID") != null){
						onlineInsPolicyVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)
					onlineInsPolicyVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					onlineInsPolicyVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					onlineInsPolicyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					onlineInsPolicyVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					onlineInsPolicyVO.setInsCompCodeNbr(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					onlineInsPolicyVO.setPolicyStatus(TTKCommon.checkNull(rs.getString("POLICY_STATUS")));
					if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
						onlineInsPolicyVO.setPolicyGrpSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
					}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)
					onlineInsPolicyVO.setTemplateName(TTKCommon.checkNull(rs.getString("TEMPLATE_NAME")));
					alResultList.add(onlineInsPolicyVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getInsEnrollmentList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getInsEnrollmentList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getInsEnrollmentList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
    }//end of getInsEnrollmentList(ArrayList alSearchCriteria)





    public ArrayList getBroEnrollmentList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		OnlineInsPolicyVO onlineInsPolicyVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBroEnrollmentList);

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
			cStmtObject.registerOutParameter(12,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(12);

			if(rs != null){
				while(rs.next()){
					onlineInsPolicyVO = new OnlineInsPolicyVO();

					if(rs.getString("MEMBER_SEQ_ID") != null){
						onlineInsPolicyVO.setMemberSeqID(new Long(rs.getLong("MEMBER_SEQ_ID")));
					}//end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if(rs.getString("POLICY_SEQ_ID") != null){
						onlineInsPolicyVO.setPolicySeqID(new Long(rs.getLong("POLICY_SEQ_ID")));
					}//end of if(rs.getString("POLICY_SEQ_ID") != null)
					onlineInsPolicyVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
					onlineInsPolicyVO.setMemberName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
					onlineInsPolicyVO.setPolicyNbr(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					onlineInsPolicyVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					onlineInsPolicyVO.setInsCompCodeNbr(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
					onlineInsPolicyVO.setPolicyStatus(TTKCommon.checkNull(rs.getString("POLICY_STATUS")));
					if(rs.getString("POLICY_GROUP_SEQ_ID") != null){
						onlineInsPolicyVO.setPolicyGrpSeqID(new Long(rs.getLong("POLICY_GROUP_SEQ_ID")));
					}//end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)
					onlineInsPolicyVO.setTemplateName(TTKCommon.checkNull(rs.getString("TEMPLATE_NAME")));
					alResultList.add(onlineInsPolicyVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getInsEnrollmentList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getInsEnrollmentList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getInsEnrollmentList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
    }//end of getBroEnrollmentList(ArrayList alSearchCriteria)


    /**
     * This method returns the Arraylist of OnlineAssistanceVO's  which contains Online Query details
     * @param lngPolicyGroupSeqID It contains the Policy Group Seq ID
     * @return ArrayList of OnlineAssistanceVO object which contains Online Query details
     * @exception throws TTKException
     */
    public ArrayList getOnlineQueryList(long lngPolicyGroupSeqID) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	OnlineAssistanceVO onlineAssistanceVO = null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strOnlineQueryList);
			cStmtObject.setLong(1,lngPolicyGroupSeqID);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);

			if(rs != null){
				while(rs.next()){
					onlineAssistanceVO = new OnlineAssistanceVO();

					if(rs.getString("QUERY_HEADER_SEQ_ID") != null){
						onlineAssistanceVO.setQueryHdrSeqId(Long.valueOf(rs.getLong("QUERY_HEADER_SEQ_ID")));
					}//end of if(rs.getString("QUERY_HEADER_SEQ_ID") != null)

					onlineAssistanceVO.setRequestID(TTKCommon.checkNull(rs.getString("REQUEST_ID")));

					if(rs.getString("LAST_SUBMITTED_DATE") != null){
						onlineAssistanceVO.setLatestReqDate(new Date(rs.getTimestamp("LAST_SUBMITTED_DATE").getTime()));
					}//end of if(rs.getString("LAST_SUBMITTED_DATE") != null)

					if(rs.getString("CLARIFIED_DATE") != null){
						onlineAssistanceVO.setRespondedDate(new Date(rs.getTimestamp("CLARIFIED_DATE").getTime()));
					}//end of if(rs.getString("CLARIFIED_DATE") != null)

					onlineAssistanceVO.setStatus(TTKCommon.checkNull(rs.getString("STATUS")));

					alResultList.add(onlineAssistanceVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getOnlineQueryList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getOnlineQueryList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getOnlineQueryList()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return (ArrayList)alResultList;
    }//end of getOnlineQueryList(long lngPolicyGroupSeqID)

    /**
     * This method returns the OnlineAssistanceDetailVO which contains the Online Query Header information
     * @param lngQueryHdrSeqID Query Header Seq ID
     * @param strIdentifier Identifier for WEB/SUP
     * @return OnlineAssistanceDetailVO the contains the Online Query Header Information
     * @exception throws TTKException
     */
    public OnlineAssistanceDetailVO getQueryHeaderInfo(long lngQueryHdrSeqID,String strIdentifier) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	ArrayList<Object> alQueryList = new ArrayList<Object>();
    	OnlineAssistanceDetailVO onlineAssistanceDetailVO = new OnlineAssistanceDetailVO();
    	OnlineQueryVO onlineQueryVO = null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strOnlineQueryHeaderInfo);
    		cStmtObject.setLong(1,lngQueryHdrSeqID);
    		cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
    		cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
    		cStmtObject.setString(4,strIdentifier);
    		cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(2);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(3);

			if(rs1!= null){
				while(rs1.next()){
					if(rs1.getString("QUERY_HEADER_SEQ_ID") != null) {
						onlineAssistanceDetailVO.setQueryHdrSeqId(Long.valueOf(rs1.getLong("QUERY_HEADER_SEQ_ID")));
					}//end of if(rs1.getString("QUERY_HEADER_SEQ_ID") != null)

					onlineAssistanceDetailVO.setPolicyGrpSeqID(Long.valueOf(rs1.getLong("POLICY_GROUP_SEQ_ID")));
					onlineAssistanceDetailVO.setPolicySeqID(Long.valueOf(rs1.getLong("POLICY_SEQ_ID")));
					onlineAssistanceDetailVO.setRequestID(TTKCommon.checkNull(rs1.getString("REQUEST_ID")));
					onlineAssistanceDetailVO.setEmailID(TTKCommon.checkNull(rs1.getString("EMAIL_ID")));
					onlineAssistanceDetailVO.setPhoneNbr(TTKCommon.checkNull(rs1.getString("PHONE_NO")));
					onlineAssistanceDetailVO.setMobileNbr(TTKCommon.checkNull(rs1.getString("MOBILE_NO")));
					onlineAssistanceDetailVO.setFeedbackAllowedYN(TTKCommon.checkNull(rs1.getString("ONLINE_RATING_GEN_TYPE_ID")));
				}//end of while(rs1.next())
			}//end of if(rs1!= null)

			if(rs2!= null){
				while(rs2.next()){
					onlineQueryVO = new OnlineQueryVO();

					if(rs2.getString("QUERY_DETAILS_SEQ_ID") != null){
						onlineQueryVO.setQueryDtlSeqID(Long.valueOf(rs2.getLong("QUERY_DETAILS_SEQ_ID")));
					}//end of if(rs2.getString("QUERY_DETAILS_SEQ_ID") != null)

					if(rs2.getString("SUBMITTED_DATE") != null){
						onlineQueryVO.setLatestReqDate(new Date(rs2.getTimestamp("SUBMITTED_DATE").getTime()));
						onlineQueryVO.setLatestReqTime(TTKCommon.getFormattedDateHour(new Date(rs2.getTimestamp("SUBMITTED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs2.getTimestamp("SUBMITTED_DATE").getTime())).split(" ")[1]:"");
						onlineQueryVO.setLatestReqDay(TTKCommon.getFormattedDateHour(new Date(rs2.getTimestamp("SUBMITTED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs2.getTimestamp("SUBMITTED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs2.getString("SUBMITTED_DATE") != null)

					onlineQueryVO.setStatus(TTKCommon.checkNull(rs2.getString("STATUS")));
					onlineQueryVO.setQueryDesc(TTKCommon.checkNull(rs2.getString("QUERY_PART")));
					onlineQueryVO.setFeedbackStatus(TTKCommon.checkNull(rs2.getString("FEEDBACK_STATUS")));
					onlineQueryVO.setFeedBackDesc(TTKCommon.checkNull(rs2.getString("FEEDBACK_QUERY_TYPE")));
					onlineQueryVO.setQueryFeedbackStatusID(TTKCommon.checkNull(rs2.getString("FEEDBACK_STATUS_GEN_TYPE_ID")));
					alQueryList.add(onlineQueryVO);
				}//end of while(rs2.next())
				onlineAssistanceDetailVO.setQueryVO(alQueryList);
			}//end of if(rs2!= null)
			rs2.close();
			rs1.close();
			cStmtObject.close();
			conn.close();
			return onlineAssistanceDetailVO;
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Second Resultset in CallCenterDAOImpl getQueryHeaderInfo()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in CallCenterDAOImpl getQueryHeaderInfo()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if first result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in CallCenterDAOImpl getQueryHeaderInfo()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in CallCenterDAOImpl getQueryHeaderInfo()",sqlExp);
								throw new TTKException(sqlExp, "onlineforms");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of finally
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getQueryHeaderInfo(long lngQueryHdrSeqID)

    /**
     * This method returns the OnlineQueryVO which contains the Online Query information
     * @param lngQueryDtlSeqID Query Detail Seq ID
     * @return OnlineQueryVO the contains the Online Query Information
     * @exception throws TTKException
     */
    public OnlineQueryVO getQueryInfo(long lngQueryDtlSeqID) throws TTKException {
    	Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        OnlineQueryVO onlineQueryVO = null;
        try{
        	conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strOnlineQueryInfo);
            cStmtObject.setLong(1,lngQueryDtlSeqID);
            cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            if(rs != null){
            	while(rs.next()){
            		onlineQueryVO = new OnlineQueryVO();

            		if(rs.getString("QUERY_DETAILS_SEQ_ID") != null){
            			onlineQueryVO.setQueryDtlSeqID(Long.valueOf(rs.getLong("QUERY_DETAILS_SEQ_ID")));
            		}//end of if(rs.getString("QUERY_DETAILS_SEQ_ID") != null)
            		onlineQueryVO.setQueryDesc(TTKCommon.checkNull(rs.getString("QUERY_DESC"))); //Question
            		if(rs.getString("SUBMITTED_DATE") != null){
						onlineQueryVO.setLatestReqDate(new Date(rs.getTimestamp("SUBMITTED_DATE").getTime()));
						onlineQueryVO.setLatestReqTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SUBMITTED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SUBMITTED_DATE").getTime())).split(" ")[1]:"");
						onlineQueryVO.setLatestReqDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SUBMITTED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("SUBMITTED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("SUBMITTED_DATE") != null)

            		onlineQueryVO.setTTKRemarks(TTKCommon.checkNull(rs.getString("CLARIFICATION_DESC")));

            		if(rs.getString("CLARIFIED_DATE") != null){
						onlineQueryVO.setRespondedDate(new Date(rs.getTimestamp("CLARIFIED_DATE").getTime()));
						onlineQueryVO.setRespondedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CLARIFIED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CLARIFIED_DATE").getTime())).split(" ")[1]:"");
						onlineQueryVO.setRespondedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CLARIFIED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("CLARIFIED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("CLARIFIED_DATE") != null)

            		onlineQueryVO.setOnlineEditYN(TTKCommon.checkNull(rs.getString("ONLINE_EDIT_YN")));

            		if(TTKCommon.checkNull(rs.getString("ONLINE_EDIT_YN")).equals("Y")){
            			onlineQueryVO.setSubmittedYN("N");
            		}//end of if(TTKCommon.checkNull(rs.getString("ONLINE_EDIT_YN")).equals("Y"))
            		else{
            			onlineQueryVO.setSubmittedYN("Y");
            		}//end of else
            		onlineQueryVO.setSupportEditYN(TTKCommon.checkNull(rs.getString("SUPPORT_EDIT_YN")));
            		onlineQueryVO.setQueryGnlTypeID(TTKCommon.checkNull(rs.getString("QUERY_GENERAL_TYPE_ID")));
            		onlineQueryVO.setQueryTypeDesc(TTKCommon.checkNull(rs.getString("QUERY_TYPE")));
            		onlineQueryVO.setStatus(TTKCommon.checkNull(rs.getString("STATUS")));
            		onlineQueryVO.setQueryFeedbackTypeID(TTKCommon.checkNull(rs.getString("EMP_FEEDBACK_GEN_TYPE_ID")));
            		onlineQueryVO.setFeedbackStatus(TTKCommon.checkNull(rs.getString("FEEDBACK_RESP_STATUS")));
            		onlineQueryVO.setFeedBackDesc(TTKCommon.checkNull(rs.getString("EMP_FEEDBACK_TYPE")));
            		onlineQueryVO.setTtkfeedBackRemarks(TTKCommon.checkNull(rs.getString("FEEDBACK_CLARIFICATION_DESC")));
            		onlineQueryVO.setFeedbackSupportEditYN(TTKCommon.checkNull(rs.getString("SUPPORT_FEEDBACK_EDIT_YN")));
            		onlineQueryVO.setFeedbackOnlineEditYN(TTKCommon.checkNull(rs.getString("FEEDBACK_ONLINE_EDIT_YN")));
            		if(rs.getString("FEEDBACK_CLARIFIED_DATE") != null){
						onlineQueryVO.setClarifiedDate(new Date(rs.getTimestamp("FEEDBACK_CLARIFIED_DATE").getTime()));
						onlineQueryVO.setClarifiedTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("FEEDBACK_CLARIFIED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("FEEDBACK_CLARIFIED_DATE").getTime())).split(" ")[1]:"");
						onlineQueryVO.setClarifiedDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("FEEDBACK_CLARIFIED_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("FEEDBACK_CLARIFIED_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("FEEDBACK_CLARIFIED_DATE") != null)
            		onlineQueryVO.setQueryRemarksDesc(TTKCommon.checkNull(rs.getString("EMP_FEEDBACK_REMARKS")));
            		if(rs.getString("EMP_FEEDBACK_SUBMIT_DATE") != null){
						onlineQueryVO.setFeedbackSubmitDate(new Date(rs.getTimestamp("EMP_FEEDBACK_SUBMIT_DATE").getTime()));
						onlineQueryVO.setFeedbackSubmitTime(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("EMP_FEEDBACK_SUBMIT_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("EMP_FEEDBACK_SUBMIT_DATE").getTime())).split(" ")[1]:"");
						onlineQueryVO.setFeedbackSubmitDay(TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("EMP_FEEDBACK_SUBMIT_DATE").getTime())).split(" ").length >=3 ? TTKCommon.getFormattedDateHour(new Date(rs.getTimestamp("EMP_FEEDBACK_SUBMIT_DATE").getTime())).split(" ")[2]:"");
					}//end of if(rs.getString("EMP_FEEDBACK_SUBMIT_DATE") != null)
            		if(TTKCommon.checkNull(rs.getString("FEEDBACK_ONLINE_EDIT_YN")).equals("Y")){
            			onlineQueryVO.setFeedBackSubmittedYN("N");
            		}//end of if(TTKCommon.checkNull(rs.getString("FEEDBACK_ONLINE_EDIT_YN")).equals("Y"))
            		else{
            			onlineQueryVO.setFeedBackSubmittedYN("Y");
            		}//end of else
            		if(rs.getString("FEEDBACK_CLARIFIED_DATE") != null){
            			onlineQueryVO.setTtkfeedBackSubmittedYN("Y");
            		}//end of if(TTKCommon.checkNull(rs.getString("FEEDBACK_ONLINE_EDIT_YN")).equals("Y"))
            		else{
            			onlineQueryVO.setTtkfeedBackSubmittedYN("N");
            		}//end of else
            		onlineQueryVO.setQueryFeedbackStatusID(TTKCommon.checkNull(rs.getString("FEEDBACK_STATUS_GEN_TYPE_ID")));
            		onlineQueryVO.setQueryFeedbackStatusDesc(TTKCommon.checkNull(rs.getString("FEEDBACK_STATUS")));
            	}//end of while(rs.next())
            }//end of if(rs != null)
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getQueryInfo()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getQueryInfo()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getQueryInfo()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
    	return onlineQueryVO;
    }//end of getQueryInfo(long lngQueryDtlSeqID)

    /**
     * This method saves the Online Query Header Information
     * @param onlineAssistanceDetailVO OnlineAssistanceDetailVO which contains the Online Query Header Information
     * @return lngQueryHdrSeqID long Object, which contains the Query Header Seq ID
     * @exception throws TTKException
     */
    public long saveQueryHeaderInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	long lngQueryHdrSeqID = 0;
    	OnlineQueryVO onlineQueryVO = null;
    	try{
    		conn = ResourceManager.getConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveOnlineQueryInfo);
    		onlineQueryVO = onlineAssistanceDetailVO.getOnlineQueryVO();

    		if(onlineAssistanceDetailVO.getQueryHdrSeqId() == null){
    			cStmtObject.setLong(1,0);
    		}//end of if(onlineAssistanceDetailVO.getQueryHdrSeqId() == null)
    		else{
    			cStmtObject.setLong(1,onlineAssistanceDetailVO.getQueryHdrSeqId());
    		}//end of else

    		if(onlineQueryVO.getQueryDtlSeqID() == null){
    			cStmtObject.setString(2,null);
    		}//end of if(onlineQueryVO.getQueryDtlSeqID() == null)
    		else{
    			cStmtObject.setLong(2,onlineQueryVO.getQueryDtlSeqID());
    		}//end of else

    		cStmtObject.setLong(3,onlineAssistanceDetailVO.getPolicyGrpSeqID());
    		cStmtObject.setLong(4,onlineAssistanceDetailVO.getPolicySeqID());
    		cStmtObject.setString(5,onlineAssistanceDetailVO.getEmailID());
    		cStmtObject.setString(6,onlineAssistanceDetailVO.getPhoneNbr());
    		cStmtObject.setString(7,onlineAssistanceDetailVO.getMobileNbr());
    		cStmtObject.setString(8,onlineQueryVO.getSubmittedYN());
    		cStmtObject.setString(9,onlineQueryVO.getQueryDesc());
    		cStmtObject.setString(10,onlineQueryVO.getQueryGnlTypeID());
    		cStmtObject.setLong(11,onlineAssistanceDetailVO.getUpdatedBy());
    		cStmtObject.setString(12,onlineQueryVO.getQueryFeedbackTypeID());
    		cStmtObject.setString(13,onlineQueryVO.getQueryRemarksDesc());
    		if((onlineQueryVO.getQueryFeedbackStatusID()!= "") )
    		{
    			cStmtObject.setString(14,onlineQueryVO.getQueryFeedbackStatusID());
    		}//end of if((onlineQueryVO.getQueryFeedbackStatusID()!= "") )
    		else
    		{
    			cStmtObject.setString(14,"FSO");
    		}//end of else
    		cStmtObject.setString(15,onlineQueryVO.getFeedBackSubmittedYN());
    		cStmtObject.registerOutParameter(16,Types.INTEGER);
    		cStmtObject.registerOutParameter(1,Types.BIGINT);
    		cStmtObject.execute();
    		lngQueryHdrSeqID = cStmtObject.getLong(1);
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveQueryHeaderInfo()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveQueryHeaderInfo()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return lngQueryHdrSeqID;
    }//end of saveQueryHeaderInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO)
/**

    /**
     * This method saves the existing password
     * @param alForgotPass ArrayList object which contains the Employee Login Information
     * @return String which returns the result string
     * @exception throws TTKException
     */
    public String saveForgotPassword(ArrayList<Object> alForgotPass) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	String strPassword="";
    	try{
    		conn = ResourceManager.getConnection();
    		 if(alForgotPass.get(5).equals("OBR")){//kocbroker
    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strForgotPasswordBroker);
    			cStmtObject.setString(1,(String)alForgotPass.get(2));
    			cStmtObject.registerOutParameter(2,Types.VARCHAR);
    			cStmtObject.execute();
        		strPassword = cStmtObject.getString(2);
    		}else{
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strForgotPassword);

    		cStmtObject.setString(1,(String)alForgotPass.get(0));
    		cStmtObject.setString(2,(String)alForgotPass.get(1));
    		cStmtObject.setString(3,(String)alForgotPass.get(2));
    		cStmtObject.registerOutParameter(4,Types.VARCHAR);
//Changes as Per IBM CR
		   cStmtObject.setTimestamp(5,(Date)alForgotPass.get(3)!=null ? new Timestamp(((Date)alForgotPass.get(3)).getTime()):null);
    	   //cStmtObject.setTimestamp(DATE_OF_INCEPTION,memberDetailVO.getInceptionDate()!=null ? new Timestamp(memberDetailVO.getInceptionDate().getTime()):null);
           cStmtObject.setTimestamp(6,(Date)alForgotPass.get(4)!=null ? new Timestamp(((Date)alForgotPass.get(4)).getTime()):null);
		   //Changes as Per IBM CR
    		cStmtObject.execute();
    		strPassword = cStmtObject.getString(4);
    		}//end else
    	}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveQueryHeaderInfo()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveQueryHeaderInfo()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return strPassword;
    }//end of saveQueryHeaderInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO)

    /**
     * This method saves the Password Information
     * @param passwordVO PasswordVO which contains Password Information
     * @param strPolicyNbr String Object which contains Policy Number
     * @param strGroupID String Object which contains Group ID
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changeOnlinePassword(OnlineChangePasswordVO onlineChangePasswordVO,String strPolicyNbr,String strGroupID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strChangePassword);
			cStmtObject.setString(1,strPolicyNbr);//POLICY_NUMBER
			cStmtObject.setString(2,strGroupID);//GROUP_ID
			cStmtObject.setString(3,onlineChangePasswordVO.getUserID());
			cStmtObject.setString(4,onlineChangePasswordVO.getOldPassword());
			cStmtObject.setString(5,onlineChangePasswordVO.getNewPassword());
			cStmtObject.registerOutParameter(6,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);
		}//end of try
    	catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
        			log.error("Error while closing the Statement in OnlineAccessDAOImpl changePassword()",sqlExp);
        			throw new TTKException(sqlExp, "onlineforms");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in OnlineAccessDAOImpl changePassword()",sqlExp);
        				throw new TTKException(sqlExp, "onlineforms");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "onlineforms");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
    	return iResult;
	}//end of changePassword(PasswordVO passwordVO,String strPolicyNbr,String strGroupID)


public ArrayList getHospPreAuthList(ArrayList alSearchCriteria)throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		HospPreAuthVO preAuthVO=null;
		ArrayList alResultList = new ArrayList();
		try{

			String getHospPreAuthList = "{CALL HOSPITAL_PKG.SELECT_PRE_AUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			conn=ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(getHospPreAuthList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));//preauthNumber
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));//Auth Number
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));//enroll id
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));//status
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));//policy number
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));//doa
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));//date of preauth
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));//v_start_date
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));//v_end_date
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(9));//seqid

			cStmtObject.setString(11,(String)alSearchCriteria.get(10));//Patient Name

			cStmtObject.setString(12,(String)alSearchCriteria.get(11));//sort var
			cStmtObject.setString(13,(String)alSearchCriteria.get(12));//sort order
			cStmtObject.setString(14,(String)alSearchCriteria.get(13));//start number
			cStmtObject.setString(15,(String)alSearchCriteria.get(14));//end number
			cStmtObject.registerOutParameter(16,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(16);
			if(rs != null)
			{
				while(rs.next())
				{
					preAuthVO = new HospPreAuthVO();

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
					preAuthVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("PAT_STATUS")));

					/*ArrayList alUserGroup = (ArrayList)alSearchCriteria.get(17);
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
					preAuthVO.setSuppressLink(strSuppressLink);*/
					alResultList.add(preAuthVO);

				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getHospPreAuthList",sqlExp);
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
						log.error("Error while closing the Statement inOnlineAccessDAOImpl getHospPreAuthList",sqlExp);
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
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getHospPreAuthList",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alResultList;
	}//end of  getHospPreAuthList(ArrayList alSearchCriteria)

public ArrayList getHospClaimsList(ArrayList alSearchCriteria)throws TTKException	{
			Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		HospPreAuthVO preauthVO=null;
		ArrayList alResultList = new ArrayList();
		try{
			
			String getHospPreAuthList = "{CALL HOSPITAL_PKG.SELECT_CLAIMS_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//ADDED 2 NEW COLUMNS
			conn=ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(getHospPreAuthList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));//claimNumber
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));//Auth Number
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));//enroll id
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));//status
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));//policy number
			cStmtObject.setString(6,(String)alSearchCriteria.get(5));//doa
			cStmtObject.setString(7,(String)alSearchCriteria.get(6));//date of Claim
			cStmtObject.setString(8,(String)alSearchCriteria.get(7));//v_start_date
			cStmtObject.setString(9,(String)alSearchCriteria.get(8));//v_end_date
			cStmtObject.setLong(10,(Long)alSearchCriteria.get(9));//seqid

			cStmtObject.setString(11,(String)alSearchCriteria.get(10));//Patient Name
			cStmtObject.setString(12,(String)alSearchCriteria.get(11));//Discharge date

			cStmtObject.setString(13,(String)alSearchCriteria.get(12));//sort var
			cStmtObject.setString(14,(String)alSearchCriteria.get(13));//sort order
			cStmtObject.setString(15,(String)alSearchCriteria.get(14));//start number
			cStmtObject.setString(16,(String)alSearchCriteria.get(15));//end number
			cStmtObject.registerOutParameter(17,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(17);
			if(rs != null){
				while(rs.next()){
					preauthVO = new HospPreAuthVO();

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

					//New Req
					if(rs.getString("DATE_OF_DISCHARGE") != null){
						preauthVO.setDischargeDate1(new Date(rs.getTimestamp("DATE_OF_DISCHARGE").getTime()));
					}//end of if(rs.getString("DATE_OF_DISCHARGE") != null)


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

					preauthVO.setStatusTypeID(TTKCommon.checkNull(rs.getString("CLM_STATUS")));





					/*	String strGroupID = TTKCommon.checkNull(rs.getString("GROUP_BRANCH_SEQ_ID"));
					 * boolean bSuppress = true;
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
					 */
					alResultList.add(preauthVO);

				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getHospClaimsList",sqlExp);
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
						log.error("Error while closing the Statement inOnlineAccessDAOImpl getHospClaimsList",sqlExp);
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
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getHospClaimsList",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return alResultList;
	}//end of  getHospClaimsList(ArrayList alSearchCriteria)


/**
	 * This method saves the existing password
	 * @param alForgotPass ArrayList object which contains the Employee Login Information
	 * @return String which returns the result string
	 * @exception throws TTKException
	 */
	public String saveHospitalForgotPassword(ArrayList<Object> alForgotPass) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		String strPassword="";
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHospitalForgotPassword);

			cStmtObject.setString(1,(String)alForgotPass.get(0));//v_empanel_id
			cStmtObject.setString(2,(String)alForgotPass.get(1));//v_userid
			
			
			cStmtObject.registerOutParameter(3,Types.VARCHAR);
			cStmtObject.execute();
			strPassword = cStmtObject.getString(3);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Statement in OnlineAccessDAOImpl saveQueryHeaderInfo()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl saveQueryHeaderInfo()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineforms");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return strPassword;
	}//end of saveQueryHeaderInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO)

	 /**
     * This method returns the OnlineIntimationVO which contains the member contact details
     * @param strEmpNbr Employee Number
     * @param lngPolicyGroupSeqID PolicyGroupSeqID
     * @param strPolicyNbr PolicyNumber
     * @return OnlineIntimationVO  contains the member contact details
     * @exception throws TTKException
     */
	  public OnlineIntimationVO getEmpContactInfo(String strEmpNbr,long lngPolicyGroupSeqID,String strPolicyNbr)throws TTKException {
		  Connection conn = null;
	      CallableStatement cStmtObject=null;
	      ResultSet rs = null;
	      OnlineIntimationVO onlineIntimationVO = null;
	      try{
	        	conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strEmpContactInfo);
				cStmtObject.setString(1,(String)strEmpNbr);
				cStmtObject.setLong(2,(Long)lngPolicyGroupSeqID);
				cStmtObject.setString(3,(String)strPolicyNbr);
				cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(4);
				if(rs != null)
				{
					while(rs.next())
					{
						onlineIntimationVO = new OnlineIntimationVO();
						onlineIntimationVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));
						onlineIntimationVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
						onlineIntimationVO.setPhoneNbr(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					}//end of while(rs.next())
				}//end of if(rs != null)
	       }//end of try
	       catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "onlineforms");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "onlineforms");
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
						log.error("Error while closing the Resultset in OnlineAccessDAOImpl getEmpContactInfo()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in OnlineAccessDAOImpl getEmpContactInfo()",sqlExp);
							throw new TTKException(sqlExp, "onlineforms");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in OnlineAccessDAOImpl getEmpContactInfo()",sqlExp);
								throw new TTKException(sqlExp, "onlineforms");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "onlineforms");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects
				{
					rs = null;
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
	         return onlineIntimationVO;
	  }//end of getEmpContactInfo()

//ins file upload
	  /**
	     * This method returns the Long which contains the result sucess or fail
	     * @param thst contains details file save
	     * * @exception throws TTKException
	     */
		public Long saveInsCompUploadDetails(ArrayList alFileDetails) throws TTKException
		{
			Connection conn = null;
			CallableStatement cStmtObject=null;
			long lResult = 0;

			try {
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strInsCompFileUploadDetails);
				cStmtObject.setBigDecimal(1, null);//v_enrol_upd_seq_id
				cStmtObject.setString(2,(String)alFileDetails.get(0));//filename
				cStmtObject.setString(3,(String)alFileDetails.get(1));//insurance comp code
				cStmtObject.setString(4,(String)alFileDetails.get(2));//updated by(login user id)
				cStmtObject.setString(5,(String)alFileDetails.get(3));//upload type
				cStmtObject.setLong(6,(Long)alFileDetails.get(4));//user seq id
				cStmtObject.registerOutParameter(7,Types.BIGINT);
				cStmtObject.execute();
				lResult = cStmtObject.getLong(7);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineformsemp");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineformsemp");
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
	    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveInsCompUploadDetails",sqlExp);
	    			throw new TTKException(sqlExp, "onlineformsemp");
	    		}//end of catch (SQLException sqlExp)
	    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	    		{
	    			try
	    			{
	    				if(conn != null) conn.close();
	    			}//end of try
	    			catch (SQLException sqlExp)
	    			{
	    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveInsCompUploadDetails",sqlExp);
	    				throw new TTKException(sqlExp, "onlineformsemp");
	    			}//end of catch (SQLException sqlExp)
	    		}//end of finally Connection Close
	    	}//end of try
	    	catch (TTKException exp)
	    	{
	    		throw new TTKException(exp, "onlineformsemp");
	    	}//end of catch (TTKException exp)
	    	finally // Control will reach here in anycase set null to the objects
	    	{
	    		cStmtObject = null;
	    		conn = null;
	    	}//end of finally
		}//end of finally
		return lResult ;
	 } //end of  saveInsCompUploadDetails(ArrayList alFileDetails)

	 /**
	  * This method returns the Long which contains the result sucess or fail
	  * @param thst contains get the file details
	  * * @exception throws TTKException
	  */
		public ArrayList getInsCompUploadFilesList(ArrayList alSearchCriteria)throws TTKException
		{
			Connection conn = null;
			CallableStatement cStmtObject=null;
			ResultSet rs = null;
			InsFileUploadVO insFileUploadVO=null;
			ArrayList insFilelist = new ArrayList();



			try{
				conn=ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetInsCompFileUploadList);
				cStmtObject.setString(1,(String)alSearchCriteria.get(0));//login user id
				cStmtObject.setString(2,(String)alSearchCriteria.get(1));
				cStmtObject.setString(3,(String)alSearchCriteria.get(2));
				cStmtObject.setString(3,(String)alSearchCriteria.get(2));
				cStmtObject.setString(4,(String)alSearchCriteria.get(3));
				cStmtObject.setString(5,(String)alSearchCriteria.get(4));
				cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(6);
			if(rs != null)
			{
				while(rs.next())
				{
					insFileUploadVO=new InsFileUploadVO();
					insFileUploadVO.setFileName(rs.getString("INS_FILENAME"));
					insFileUploadVO.setAddedDate(new Date(rs.getTimestamp("ADDED_DATE").getTime()));
					insFilelist.add(insFileUploadVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getInsCompUploadFilesList",sqlExp);
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
						log.error("Error while closing the Statement inOnlineAccessDAOImpl getInsCompUploadFilesList",sqlExp);
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
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getInsCompUploadFilesList",sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return insFilelist;
	}//end of  getInsCompUploadFilesList(ArrayList alSearchCriteria)t)
//ins file upload


  /**
     * This method returns the Long which contains the result sucess or fail
     * @param thst contains details file save
     * * @exception throws TTKException //1352
     */
	public Long saveEmployeeFileDetails(ArrayList alFileDetails) throws TTKException
	{
		  Connection conn = null;
	        CallableStatement cStmtObject=null;
	        long lResult = 0;
	        try {
	            conn = ResourceManager.getConnection();
	            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveFileUploadDetails);
				cStmtObject.setBigDecimal(1, null);//v_enrol_upd_seq_id
	            cStmtObject.setBigDecimal(2, TTKCommon.getBigDecimal((String)alFileDetails.get(5)));//v_member_seq_id
	            cStmtObject.setBigDecimal(3, TTKCommon.getBigDecimal((String)alFileDetails.get(4)));//v_policy_grp_seq_id
	            cStmtObject.setString(4, (String)alFileDetails.get(1));//v_remark
	            cStmtObject.setString(5, (String)alFileDetails.get(0));//v_vi_path
	            cStmtObject.setString(6, null);//v_dms_path
	            cStmtObject.setString(7, (String)alFileDetails.get(2));//v_doc_type
	            cStmtObject.setString(8, null);//v_inward_no
	            cStmtObject.setString(9,null);//v_status
	             cStmtObject.setLong(10,(Long)alFileDetails.get(3));//v_added_by
	             cStmtObject.setString(11,null);//uid
	             cStmtObject.registerOutParameter(11,Types.VARCHAR);
				cStmtObject.registerOutParameter(12,Types.BIGINT);//v_rows_processed
				cStmtObject.execute();
				String uid=cStmtObject.getString(11);
				lResult = cStmtObject.getLong(12);
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "onlineformsemp");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "onlineformsemp");
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
    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveEmployeeFileDetails",sqlExp);
    			throw new TTKException(sqlExp, "onlineformsemp");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if(conn != null) conn.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveEmployeeFileDetails",sqlExp);
    				throw new TTKException(sqlExp, "onlineformsemp");
    			}//end of catch (SQLException sqlExp)
    		}//end of finally Connection Close
    	}//end of try
    	catch (TTKException exp)
    	{
    		throw new TTKException(exp, "onlineformsemp");
    	}//end of catch (TTKException exp)
    	finally // Control will reach here in anycase set null to the objects
    	{
    		cStmtObject = null;
    		conn = null;
    	}//end of finally
	}//end of finally
	return lResult ;
 } //end of  saveEmployeeFileDetails(ArrayList alFileDetails)

 	 /**
     * This method returns the ArrayList which contains the employee File ListDetails
     * @param strEmpNbr Search Criteria
     * * @exception throws TTKException//1352
     */
	public ArrayList getEmployeeFilesList(ArrayList alSearchCriteria)throws TTKException
	{
	Connection conn = null;
	CallableStatement cStmtObject=null;

	ResultSet rs = null;
	EmployeeFileUplodedVO employeeFileUplodedVO=null;

	ArrayList hsFilelist = new ArrayList();
	try{
		conn=ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetFileUploadList);
		cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
		cStmtObject.setString(2,(String)alSearchCriteria.get(1));
		cStmtObject.setString(3,(String)alSearchCriteria.get(2));
		cStmtObject.setString(4,(String)alSearchCriteria.get(3));
		cStmtObject.setString(5,(String)alSearchCriteria.get(4));
		cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
		cStmtObject.execute();
		rs = (java.sql.ResultSet)cStmtObject.getObject(6);
		if(rs != null)
		{
			while(rs.next())
			{
				employeeFileUplodedVO=new EmployeeFileUplodedVO();
				employeeFileUplodedVO.setFileName(rs.getString("VI_FILENAME"));
				employeeFileUplodedVO.setRemarks(rs.getString("REMARKS"));
				employeeFileUplodedVO.setAddedDate(new Date(rs.getTimestamp("ADDED_DATE").getTime()));
				hsFilelist.add(employeeFileUplodedVO);
			}//end of while(rs.next())
		}//end of if(rs != null)
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "");
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
				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getEmployeeFilesList",sqlExp);
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
					log.error("Error while closing the Statement inOnlineAccessDAOImpl getEmployeeFilesList",sqlExp);
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
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getEmployeeFilesList",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			rs = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	return hsFilelist;
}//end of  getEmployeeFilesList(ArrayList alSearchCriteria)

	public Long saveHospitalFileUpload(ArrayList alFileDetails) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		long lResult = 0;

		try {
			conn = ResourceManager.getConnection();
			String strSaveHospitalFileUpload="{CALL HOSPITAL_PKG.PAT_CLM_FILE_UPLOAD(?,?,?,?,?,?,?,?)}";
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveHospitalFileUpload);

			cStmtObject.setString(1,(String)alFileDetails.get(0));//ENROLLMENTID
			cStmtObject.setString(2,(String)alFileDetails.get(1));//PolicyNumber
			cStmtObject.setString(3,(String)alFileDetails.get(2));//FILE NAME
			cStmtObject.setString(4,(String)alFileDetails.get(3));//MODE (PAT/CLM)
			cStmtObject.setString(5,(String)alFileDetails.get(4));//SUB MODE (BILLS/SHORTFALL/OTHERS)
			cStmtObject.setString(6,(String)alFileDetails.get(5));//REMARKS
			cStmtObject.setLong(7,(Long)alFileDetails.get(6));//HOSP SEQ ID

			cStmtObject.registerOutParameter(8,Types.BIGINT);
			cStmtObject.execute();
			lResult = cStmtObject.getLong(8);
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
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
					log.error("Error while closing the Statement in OnlineAccessDAOImpl saveHospitalFileUpload",sqlExp);
					throw new TTKException(sqlExp, "onlineformsemp");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl saveHospitalFileUpload",sqlExp);
						throw new TTKException(sqlExp, "onlineformsemp");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineformsemp");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		return lResult ;
	} //end of  saveHospitalFileUpload(ArrayList alFileDetails)

//newkochosp
	public ArrayList getHospDetails(String empanelId,String hospGrpOrInd) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ArrayList alResultList = new ArrayList();
		ResultSet rs = null;
		HospitalDetailsVo hospitalVO=null;
		try {

			conn = ResourceManager.getConnection();
			String strgetHospDetails	=	"{CALL HOSPITAL_PKG_SELECT_HOSP_DETAILS(?,?,?)}";
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strgetHospDetails);
	
			cStmtObject.setString(1,empanelId);//EMPANEL ID
			cStmtObject.setString(2,hospGrpOrInd);//Group Or Individual
			
			cStmtObject.registerOutParameter(3,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(3);
			if(rs != null){
				while(rs.next()){
					hospitalVO = new HospitalDetailsVo();

					hospitalVO.setHospName(TTKCommon.checkNull(rs.getString("Hosp_Name")));
					hospitalVO.setEmpanelNo(TTKCommon.checkNull(rs.getString("empanel_number")));
					hospitalVO.setState(TTKCommon.checkNull(rs.getString("state_name")));
					hospitalVO.setCity(TTKCommon.checkNull(rs.getString("city")));
					if(rs.getString("tpa_regist_date") != null){
						hospitalVO.setRegDate(new Date(rs.getTimestamp("tpa_regist_date").getTime()));
					}//end of if(rs.getString("DATE_OF_DISCHARGE") != null)

					hospitalVO.setAddress(TTKCommon.checkNull(rs.getString("address")));
					hospitalVO.setPinCode(TTKCommon.checkNull(rs.getString("pin_code")));
					hospitalVO.setCountry(TTKCommon.checkNull(rs.getString("country")));
					hospitalVO.setOffPhone(TTKCommon.checkNull(rs.getString("off_phone_no_1")));
                    hospitalVO.setNetWorkTypes(TTKCommon.checkNull(rs.getString("selected_networks")));
                    hospitalVO.setCurrentLicenceID(TTKCommon.checkNull(rs.getString("hosp_licenc_numb")));
					hospitalVO.setProvideRegulatoryAuthority(TTKCommon.checkNull(rs.getString("regist_authority")));
					alResultList.add(hospitalVO);
				}
			}



		}catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "onlineforms");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "onlineforms");
		}//end of catch (Exception exp)
		finally{

			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in OnlineAccessDAOImpl saveHospitalFileUpload",sqlExp);
					throw new TTKException(sqlExp, "onlineformsemp");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl saveHospitalFileUpload",sqlExp);
						throw new TTKException(sqlExp, "onlineformsemp");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "onlineformsemp");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally

		}
		return alResultList;
	}
	public Document getHospitalDashBoard(Long lngHospitalSeqid,String sFromDate,String sToDate,String strType) throws TTKException{

			Connection conn = null;
			CallableStatement cStmtObject=null;
			long lResult = 0;
			SQLXML xml = null;
			Document doc=null;

			try {
				conn = ResourceManager.getConnection();
				String strHospitalDashBoard="";

				if(strType.equalsIgnoreCase("Claims"))		{
					strHospitalDashBoard="{CALL HOSPITAL_PKG_DASH_BOARD_CLAIMS(?,?,?,?)}";
					cStmtObject = conn.prepareCall(strHospitalDashBoard);
					cStmtObject.setLong(1,lngHospitalSeqid.longValue());//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
					cStmtObject.setString(2, sFromDate);
					cStmtObject.setString(3, sToDate);
					cStmtObject.registerOutParameter(4,Types.SQLXML);
					cStmtObject.execute();
					xml = cStmtObject.getSQLXML(4);
				}//end of if(strType.equalsIgnoreCase("Claims"))

				else if(strType.equalsIgnoreCase("Preauth"))	{
					strHospitalDashBoard="{CALL HOSPITAL_PKG_DASH_BOARD_PREAUTH(?,?,?,?)}";
					cStmtObject = conn.prepareCall(strHospitalDashBoard);
					cStmtObject.setLong(1,lngHospitalSeqid.longValue());//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
					cStmtObject.setString(2, sFromDate);
					cStmtObject.setString(3, sToDate);
					cStmtObject.registerOutParameter(4,Types.SQLXML);
					cStmtObject.execute();
					xml = cStmtObject.getSQLXML(4);
				}//end of if(strType.equalsIgnoreCase("Claims"))
				SAXReader saxReader = new SAXReader();
				doc = xml != null ? saxReader.read(xml.getCharacterStream()):null;
				log.info("----XML-------"+doc.asXML());
			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "history");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "history");
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
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getHospitalDashBoard()",sqlExp);
						throw new TTKException(sqlExp, "history");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in OnlineAccessDAOImpl getHospitalDashBoard()",sqlExp);
							throw new TTKException(sqlExp, "history");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "history");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects
				{
					cStmtObject = null;
					conn = null;
				}//end of finally

			}//end of finally
			return doc;
		}//end of  getHospitalDashBoard(Long lngHospitalSeqid,String strType) throws TTKException
	
	
	 //selffund
    /**Added for KOC-1255
     * This method returns the MemberSeqID, to validate a Enrollment ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList getValidateEnrollId(String enrollmentId,Long lngHospitalSeqid,Long UserSeqid,String treatmentDate,Long memSeqId) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> alcacheObj = new ArrayList<Object>();
        CacheObject cacheObject = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        CallableStatement oCstmt = null;
        Long member_seq_id	=	null;
        Long Diag_Gen_Seq_Id	=	null;	
        Long iresult			=	null;
        
        String flag="";
       
        try{
            conn = ResourceManager.getConnection();
            //oCstmt=conn.prepareStatement(strValidateEnrollID);
            
		
		oCstmt = conn.prepareCall(strValidateEnrollID);
		if(!"".equals(TTKCommon.checkNull(enrollmentId))){
		oCstmt.setString(1,enrollmentId);
		}
		else{
	    oCstmt.setString(1,null);	
		}
		if(!"".equals(TTKCommon.checkNull(lngHospitalSeqid))){
		oCstmt.setLong(2,lngHospitalSeqid);
		}
		else{
		oCstmt.setLong(2,0);	
		}
		if(!"".equals(TTKCommon.checkNull(UserSeqid))){
		oCstmt.setLong(3,UserSeqid);
		}
		else{
		oCstmt.setLong(3,0);	
		}
		if(!"".equals(TTKCommon.checkNull(memSeqId))){
		oCstmt.setLong(4,memSeqId);
		}
		else{
		oCstmt.setLong(4,0);	
		}
		oCstmt.registerOutParameter(4,Types.NUMERIC);
		oCstmt.registerOutParameter(5,Types.NUMERIC);
		oCstmt.setString(6,treatmentDate);
		oCstmt.registerOutParameter(7,Types.BIGINT);
		
            
		oCstmt.execute();
		conn.commit();
		member_seq_id		=	(Long)oCstmt.getBigDecimal(4).longValueExact();
	//	Diag_Gen_Seq_Id		=	(Long)oCstmt.getBigDecimal(5).longValueExact();
		iresult				=	oCstmt.getLong(7);
		CashlessVO cacheObj	=	new CashlessVO();
		cacheObj.setMemberSeqID(member_seq_id);//tpa_enr_policy_member.member_seq_id
	//	cacheObj.setDiagGenSeqId(Diag_Gen_Seq_Id);//tpa_diagnosys_gen_dtls.Diag_Gen_Seq_Id
		alcacheObj.add(cacheObj);
		

//rs = (java.sql.ResultSet)oCstmt.getObject(6);
           if(iresult != 0){
            	         	 
            	flag = "true";
            }//end of if(rs != null)
            else{
            	flag = "false";
            }
           alcacheObj.add(flag);
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
    				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getValidateEnrollId()",sqlExp);
    				throw new TTKException(sqlExp, "onlineforms");
    			}//end of catch (SQLException sqlExp)
    			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
    			{
    				try
    				{
    					if (oCstmt != null) oCstmt.close();
    				}//end of try
    				catch (SQLException sqlExp)
    				{
    					log.error("Error while closing the Statement in OnlineAccessDAOImpl getValidateEnrollId()",sqlExp);
    					throw new TTKException(sqlExp, "onlineforms");
    				}//end of catch (SQLException sqlExp)
    				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    				{
    					try
    					{
    						if(conn != null) conn.close();
    					}//end of try
    					catch (SQLException sqlExp)
    					{
    						log.error("Error while closing the Connection in OnlineAccessDAOImpl getValidateEnrollId()",sqlExp);
    						throw new TTKException(sqlExp, "onlineforms");
    					}//end of catch (SQLException sqlExp)
    				}//end of finally Connection Close
    			}//end of finally Statement Close
    		}//end of try
    		catch (TTKException exp)
    		{
    			throw new TTKException(exp, "onlineforms");
    		}//end of catch (TTKException exp)
    		finally // Control will reach here in anycase set null to the objects
    		{
    			rs = null;
    			conn = null;
    		}//end of finally
    	}//end of finally
		return alcacheObj;
    }//end of getSelectedPolicyNumber(String strGroupID)

    
    
    public static ArrayList<Object> getValidateSearchOpt(Long diagSeqIdForSelfFund)throws TTKException {
        Collection<Object> alBatchPolicyList = new ArrayList<Object>();
        ArrayList al	=	new ArrayList();
        Connection conn = null;
        //CashlessVO	cv	=	new CashlessVO();
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs3 = null;
        PreparedStatement pStmt = null;
        StringBuffer sbfSQL = null;
        ArrayList alDiagnosysEnrolResult	=	new ArrayList();
        ArrayList alDiagnosysTotalResult	=	new ArrayList();
        
        try {
        	
        	
             conn = ResourceManager.getConnection();
         	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strsSelectDiagTestDetails);
             //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDiaglistInfo);
             cStmtObject.setLong(1,diagSeqIdForSelfFund);
             cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
             
             cStmtObject.execute();
             

             rs1 = (java.sql.ResultSet)cStmtObject.getObject(2);  
             
             if(rs1 != null){
            	 
                 while(rs1.next()){
                	 CashlessDetailVO c2	=	new CashlessDetailVO();
                    
                     c2.setDiagEnrolSeqId(rs1.getInt("DIAG_ENROLL_SEQ_ID"));
                     c2.setTestSeqId(rs1.getInt("DIAG_TEST_SEQ_ID"));
                     c2.setDiagGenSeqId(rs1.getLong("DIAG_GEN_SEQ_ID"));
                     c2.setEnteredRate(rs1.getBigDecimal("ENTERED_RATE"));
                     c2.setAgreedRate(rs1.getBigDecimal("AGREED_RATE"));
                     c2.setDiscount(rs1.getDouble("DISCOUNT_PERC"));
                     c2.setDiscountRate(rs1.getDouble("DISCOUNT_RATE"));
                     c2.setTestName(rs1.getString("test_name"));
                     
                     
                     alDiagnosysEnrolResult.add(c2);
                     
                     
                 }
                 
                 } 
             
             
        }// end of try
        
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
    				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getDependentList()",sqlExp);
    				throw new TTKException(sqlExp, "onlineforms");
    			}//end of catch (SQLException sqlExp)
    			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
    			{
    				try
    				{
    					if (cStmtObject != null) cStmtObject.close();
    				}//end of try
    				catch (SQLException sqlExp)
    				{
    					log.error("Error while closing the Statement in OnlineAccessDAOImpl getDependentList()",sqlExp);
    					throw new TTKException(sqlExp, "onlineforms");
    				}//end of catch (SQLException sqlExp)
    				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    				{
    					try
    					{
    						if(conn != null) conn.close();
    					}//end of try
    					catch (SQLException sqlExp)
    					{
    						log.error("Error while closing the Connection in OnlineAccessDAOImpl getDependentList()",sqlExp);
    						throw new TTKException(sqlExp, "onlineforms");
    					}//end of catch (SQLException sqlExp)
    				}//end of finally Connection Close
    			}//end of finally Statement Close
    		}//end of try
    		catch (TTKException exp)
    		{
    			throw new TTKException(exp, "onlineforms");
    		}//end of catch (TTKException exp)
    		finally // Control will reach here in anycase set null to the objects
    		{
    			rs = null;
    			cStmtObject = null;
    			conn = null;
    		}//end of finally
    	}//end of finally
       
        return alDiagnosysEnrolResult;
    }//end of getDiagDetails(String splitIds)
    
    public static ArrayList<Object> getDiagOptTestTotalAmnts(Long diagSeqIdForSelfFund)throws TTKException {
        Collection<Object> alBatchPolicyList = new ArrayList<Object>();
        ArrayList al	=	new ArrayList();
        Connection conn = null;
        //CashlessVO	cv	=	new CashlessVO();
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs3 = null;
        PreparedStatement pStmt = null;
        StringBuffer sbfSQL = null;
        ArrayList alDiagnosysEnrolResult	=	new ArrayList();
        ArrayList alDiagnosysTotalResult	=	new ArrayList();
        
        try {
        	
        	
             conn = ResourceManager.getConnection();
         	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strTotalAmtDetails);
             //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDiaglistInfo);
             cStmtObject.setLong(1,diagSeqIdForSelfFund);
             cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
             
             cStmtObject.execute();
             

             rs3 = (java.sql.ResultSet)cStmtObject.getObject(2);  
             
             if(rs3 != null){
            	 
                 while(rs3.next()){
                	
                    
                	 CashlessVO cv =	new CashlessVO();
                 	cv.setTotalEnteredRate(rs3.getBigDecimal("TOTAL_ENTERED_RATE"));
                 	cv.setTotalAgreedRate(rs3.getBigDecimal("TOTAL_AGREED_RATE"));
                 	cv.setTotalDiscRate(rs3.getBigDecimal("TOTAL_DISCOUNT_RATE"));
                 	cv.setTotalDiscPerc(rs3.getBigDecimal("TOTAL_DISCOUNT_PERC"));
                 	cv.setRejectedAmount(rs3.getBigDecimal("REJECTED_AMOUNT"));
                 	cv.setDcRemarks(rs3.getString("REMARKS"));
                 	cv.setAuthNumb(rs3.getString("auth_number"));
                 	cv.setPreAuthStatus(rs3.getString("preauth_status"));
                 	cv.setEnrollmentID(rs3.getString("tpa_enrollment_id"));
                 	cv.setStatus(rs3.getString("OTP_STATUS"));
                 	alDiagnosysTotalResult.add(cv);
                     
                     
                 }
                 
                 } 
             
             
        }// end of try
        
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
    				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getDependentList()",sqlExp);
    				throw new TTKException(sqlExp, "onlineforms");
    			}//end of catch (SQLException sqlExp)
    			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
    			{
    				try
    				{
    					if (cStmtObject != null) cStmtObject.close();
    				}//end of try
    				catch (SQLException sqlExp)
    				{
    					log.error("Error while closing the Statement in OnlineAccessDAOImpl getDependentList()",sqlExp);
    					throw new TTKException(sqlExp, "onlineforms");
    				}//end of catch (SQLException sqlExp)
    				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    				{
    					try
    					{
    						if(conn != null) conn.close();
    					}//end of try
    					catch (SQLException sqlExp)
    					{
    						log.error("Error while closing the Connection in OnlineAccessDAOImpl getDependentList()",sqlExp);
    						throw new TTKException(sqlExp, "onlineforms");
    					}//end of catch (SQLException sqlExp)
    				}//end of finally Connection Close
    			}//end of finally Statement Close
    		}//end of try
    		catch (TTKException exp)
    		{
    			throw new TTKException(exp, "onlineforms");
    		}//end of catch (TTKException exp)
    		finally // Control will reach here in anycase set null to the objects
    		{
    			rs = null;
    			cStmtObject = null;
    			conn = null;
    		}//end of finally
    	}//end of finally
        
        return alDiagnosysTotalResult;
    }//end of getDiagDetails(String splitIds)
    
    
    /**Added for KOC-1255
     * This method returns the MemberSeqID, to validate a Enrollment ID
     * @param strGroupID It contains the Group ID
     * @return ArrayList object which contains Policy No's corresponding toGroup ID
     * @exception throws TTKException
     */
    public ArrayList getValidateOTP(Long diagSeqIDForSelfFund,String otpcode,Long UserSeqid) throws TTKException {
    	ResultSet rs = null;
        ArrayList<Object> aloptValues = new ArrayList<Object>();
        CacheObject cacheObject = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        OracleCallableStatement oCstmt = null;
        Long member_seq_id	=	null;
        Long Diag_Gen_Seq_Id	=	null;	
        Long iresult			=	null;
        String flag	=	"";
        
        String outdatedYN	=	"";
        String blockedYN	=	"";
        String wrongYN		=	"";
        String validatedYN	=	"";
        
       
        try{
            conn = ResourceManager.getConnection();
		
            oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strValidateOTP);
            oCstmt.setLong(1,diagSeqIDForSelfFund);
		oCstmt.setString(2,otpcode);
		oCstmt.registerOutParameter(3,Types.CHAR);
		oCstmt.registerOutParameter(4,Types.CHAR);
		oCstmt.registerOutParameter(5,Types.CHAR);
		oCstmt.registerOutParameter(6,Types.CHAR);
		oCstmt.setLong(7,UserSeqid);
		oCstmt.registerOutParameter(8,Types.INTEGER);
		
		oCstmt.execute();
		
		outdatedYN	=	(String)oCstmt.getString(3).trim();
		blockedYN	=	(String)oCstmt.getString(4).trim();
		wrongYN		=	(String)oCstmt.getString(5).trim();
		validatedYN	=	(String)oCstmt.getString(6).trim();
		
		aloptValues.add(outdatedYN);//0 index
		aloptValues.add(blockedYN);//1 index
		aloptValues.add(wrongYN);//2 index
		aloptValues.add(validatedYN);//3 index

        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "onlineforms");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "onlineforms");
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
    				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getValidateEnrollId()",sqlExp);
    				throw new TTKException(sqlExp, "onlineforms");
    			}//end of catch (SQLException sqlExp)
    			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
    			{
    				try
    				{
    					if (oCstmt != null) oCstmt.close();
    				}//end of try
    				catch (SQLException sqlExp)
    				{
    					log.error("Error while closing the Statement in OnlineAccessDAOImpl getValidateEnrollId()",sqlExp);
    					throw new TTKException(sqlExp, "onlineforms");
    				}//end of catch (SQLException sqlExp)
    				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    				{
    					try
    					{
    						if(conn != null) conn.close();
    					}//end of try
    					catch (SQLException sqlExp)
    					{
    						log.error("Error while closing the Connection in OnlineAccessDAOImpl getValidateEnrollId()",sqlExp);
    						throw new TTKException(sqlExp, "onlineforms");
    					}//end of catch (SQLException sqlExp)
    				}//end of finally Connection Close
    			}//end of finally Statement Close
    		}//end of try
    		catch (TTKException exp)
    		{
    			throw new TTKException(exp, "onlineforms");
    		}//end of catch (TTKException exp)
    		finally // Control will reach here in anycase set null to the objects
    		{
    			rs = null;
    			conn = null;
    		}//end of finally
    	}//end of finally
		return aloptValues;
    }//end of getValidateOTP


/**
 * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
 * @param strGroupID It contains the Group ID
 * @return ArrayList object which contains Policy No's corresponding toGroup ID
 * @exception throws TTKException
 */
public static ArrayList<Object> getTestsForDC(String strGroupID) throws TTKException {
    Collection<Object> alBatchPolicyList = new ArrayList<Object>();
    ArrayList al	=	new ArrayList();
    Connection conn = null;
    CallableStatement cStmtObject=null;
    ResultSet rs = null;
    try {
    	//strTestsForDC  strMemberList
    	conn = ResourceManager.getConnection();
    	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strTestsForDC);
        //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTestsForDC);
        cStmtObject.setString(1,strGroupID);
        cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
        cStmtObject.execute();
        rs = (java.sql.ResultSet)cStmtObject.getObject(2);
        if (rs!=null)
        {
            while (rs.next())
            {
                CashlessDetailVO c1	=	new CashlessDetailVO();
                c1.setTestName(rs.getString("TEST_NAME"));
                c1.setDiagSeqId(new Integer(rs.getString("DIAG_TEST_SEQ_ID")));
                c1.setHospSeqId(new Integer(rs.getString("HOSP_SEQ_ID")));
                c1.setRate(new Double(rs.getString("RATE")));
                c1.setDiscount(new Double(rs.getString("DISCOUNT")));
                al.add(c1);
            }// End of while (rs.next())
        }// End of if (rs!=null)
                
    }// end of try
    
    catch (SQLException sqlExp)
    {
        throw new TTKException(sqlExp, "onlineforms");
    }//end of catch (SQLException sqlExp)
    
    catch (Exception exp)
    {
        throw new TTKException(exp, "onlineforms");
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
				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getDependentList()",sqlExp);
				throw new TTKException(sqlExp, "onlineforms");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in OnlineAccessDAOImpl getDependentList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getDependentList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "onlineforms");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			rs = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
    return al;
}//end of getTestsForDC(String strGroupID)


/**
 * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
 * @param strGroupID It contains the Group ID
 * @return ArrayList object which contains Policy No's corresponding toGroup ID
 * @exception throws TTKException
 */
//this is to fetch test names after selecting individual tests to enter req amount
public static ArrayList<Object> getDiagDetails(String splitIds) throws TTKException {
    Collection<Object> alBatchPolicyList = new ArrayList<Object>();
    ArrayList al	=	new ArrayList();
    Connection conn = null;
    CallableStatement cStmtObject=null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;

    try {
    	
    	conn = ResourceManager.getConnection();
    	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strDiaglistInfo);
        //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDiaglistInfo);
        cStmtObject.setString(1,splitIds);
        cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
        cStmtObject.execute();
        rs = (java.sql.ResultSet)cStmtObject.getObject(2);   
        
        if(rs != null){
            while(rs.next()){
                CashlessDetailVO c1	=	new CashlessDetailVO();

            	c1.setTestName(rs.getString("TEST_NAME"));
                c1.setDiagSeqId(new Integer(rs.getString("DIAG_TEST_SEQ_ID")));
                c1.setHospSeqId(new Integer(rs.getString("HOSP_SEQ_ID")));
                c1.setRate(new Double(rs.getString("RATE")));
                c1.setDiscount(new Double(rs.getString("DISCOUNT")));
            	al.add(c1);
            }
            
            }
               
    }// end of try
    
    catch (SQLException sqlExp)
    {
        throw new TTKException(sqlExp, "onlineforms");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp)
    {
        throw new TTKException(exp, "onlineforms");
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
				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getDependentList()",sqlExp);
				throw new TTKException(sqlExp, "onlineforms");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in OnlineAccessDAOImpl getDependentList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getDependentList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "onlineforms");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			rs = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
    return al;
}//end of getDiagDetails(String splitIds)


/**
 * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
 * @param strGroupID It contains the Group ID
 * @return ArrayList object which contains Policy No's corresponding toGroup ID
 * @exception throws TTKException
 */
public static ArrayList<Object> saveDiagTestAmnts(ArrayList alDiagDataListSession,String reqAmnts,Long memSeqIDForSelfFund, Long diagSeqIdForSelfFund,Long UserSeqid)throws TTKException {
    Collection<Object> alBatchPolicyList = new ArrayList<Object>();
    ArrayList al	=	new ArrayList();
    Connection conn = null;
    //CashlessVO	cv	=	new CashlessVO();
    CallableStatement cStmtObject=null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    ResultSet rs3 = null;
    PreparedStatement pStmt = null;
    StringBuffer sbfSQL = null;
    ArrayList alDiagnosysEnrolResult	=	new ArrayList();
    ArrayList alDiagnosysTotalResult	=	new ArrayList();
    try {
    	
    	
    	
    	//--------------
    	conn = ResourceManager.getConnection();
    	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strsaveDiagTestAmnts);
        
        String sReqAmntsArr[]=new String[50];
        if(reqAmnts!=null || reqAmnts!="null" || reqAmnts!="")
		{
			sReqAmntsArr	=	reqAmnts.split("\\|");
		}
    	CashlessDetailVO c1	=	null;
    	int iresult	=	0;
         for(int i=0;i<alDiagDataListSession.size();i++)
         {
        	 c1 = (CashlessDetailVO)alDiagDataListSession.get(i);
        	 cStmtObject.setLong(1,0);
        	 cStmtObject.setLong(2,c1.getDiagSeqId());
        	 cStmtObject.setLong(3,diagSeqIdForSelfFund);
        	 cStmtObject.setString(4,sReqAmntsArr[i+1]);
        	 cStmtObject.setDouble(5,c1.getRate());
        	 cStmtObject.setDouble(6,c1.getDiscount());
        	 cStmtObject.setDouble(7,0);
        	 cStmtObject.setLong(8,UserSeqid);
        	 cStmtObject.registerOutParameter(9,Types.BIGINT);//EVENT_SEQ_ID
        	 cStmtObject.execute();
        	 iresult	=	cStmtObject.getInt(9);
         }
         conn = ResourceManager.getConnection();
     	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strsSelectDiagTestDetails);
         //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDiaglistInfo);
         cStmtObject.setLong(1,diagSeqIdForSelfFund);
         cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
         
         cStmtObject.execute();
         

         rs1 = (java.sql.ResultSet)cStmtObject.getObject(2);  
         
         if(rs1 != null){
        	 
             while(rs1.next()){
            	 CashlessDetailVO c2	=	new CashlessDetailVO();
                
                 c2.setDiagEnrolSeqId(rs1.getInt("DIAG_ENROLL_SEQ_ID"));
                 c2.setTestSeqId(rs1.getInt("DIAG_TEST_SEQ_ID"));
                 c2.setDiagGenSeqId(rs1.getLong("DIAG_GEN_SEQ_ID"));
                 c2.setEnteredRate(rs1.getBigDecimal("ENTERED_RATE"));
                 c2.setAgreedRate(rs1.getBigDecimal("AGREED_RATE"));
                 c2.setDiscount(rs1.getDouble("DISCOUNT_PERC"));
                 c2.setDiscountRate(rs1.getDouble("DISCOUNT_RATE"));
                 c2.setTestName(rs1.getString("test_name"));
                 
                 alDiagnosysEnrolResult.add(c2);
                 
                 
             }
             
             } 
         
         
    }// end of try
    
    catch (SQLException sqlExp)
    {
        throw new TTKException(sqlExp, "onlineforms");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp)
    {
        throw new TTKException(exp, "onlineforms");
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
				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getDependentList()",sqlExp);
				throw new TTKException(sqlExp, "onlineforms");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in OnlineAccessDAOImpl getDependentList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getDependentList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "onlineforms");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			rs = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
    return alDiagnosysEnrolResult;
}//end of getDiagDetails(String splitIds)


/**
 * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
 * @param strGroupID It contains the Group ID
 * @return ArrayList object which contains Policy No's corresponding toGroup ID
 * @exception throws TTKException
 */
public static ArrayList<Object> getDiagTestTotalAmnts(Long diagSeqIdForSelfFund)throws TTKException {
    Collection<Object> alBatchPolicyList = new ArrayList<Object>();
    ArrayList al	=	new ArrayList();
    Connection conn = null;
    //CashlessVO	cv	=	new CashlessVO();
    CallableStatement cStmtObject=null;
    ResultSet rs3 = null;
    ResultSet rs1 = null;
    PreparedStatement pStmt = null;
    StringBuffer sbfSQL = null;
    ArrayList alDiagnosysTotalResult	=	new ArrayList();
    try {
    	
         conn = ResourceManager.getConnection();
     	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strTotalAmtDetails);
         //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDiaglistInfo);
         cStmtObject.setLong(1,diagSeqIdForSelfFund);
         cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
         cStmtObject.execute();
         rs3 = (java.sql.ResultSet)cStmtObject.getObject(2);  
     	
			if(rs3 != null){	
             while(rs3.next()){	
            		
            	CashlessVO cv =	new CashlessVO();
            	cv.setTotalEnteredRate(rs3.getBigDecimal("TOTAL_ENTERED_RATE"));
            	cv.setTotalAgreedRate(rs3.getBigDecimal("TOTAL_AGREED_RATE"));
            	cv.setTotalDiscRate(rs3.getBigDecimal("TOTAL_DISCOUNT_RATE"));
            	cv.setTotalDiscPerc(rs3.getBigDecimal("TOTAL_DISCOUNT_PERC"));
            	cv.setRejectedAmount(rs3.getBigDecimal("REJECTED_AMOUNT"));
            	cv.setDcRemarks(rs3.getString("REMARKS"));
            	cv.setAuthNumb(rs3.getString("auth_number"));
            	cv.setPreAuthStatus(rs3.getString("preauth_status"));
            	alDiagnosysTotalResult.add(cv);
             }
             
             } 
         
    }// end of try
    
    catch (SQLException sqlExp)
    {
        throw new TTKException(sqlExp, "onlineforms");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp)
    {
        throw new TTKException(exp, "onlineforms");
    }//end of catch (Exception exp)
    finally
	{
		/* Nested Try Catch to ensure resource closure */
		try // First try closing the result set
		{
			try
			{
				if (rs3 != null) rs3.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getDiagTestTotalAmnts()",sqlExp);
				throw new TTKException(sqlExp, "onlineforms");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in OnlineAccessDAOImpl getDiagTestTotalAmnts()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getDiagTestTotalAmnts()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "onlineforms");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			rs3 = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
    return alDiagnosysTotalResult;
}//end of getDiagTestTotalAmnts(String splitIds)


/**
 * This method returns the ArrayList, which contains Policy Numbers corresponding to Group ID
 * @param strGroupID It contains the Group ID
 * @return ArrayList object which contains Policy No's corresponding toGroup ID
 * @exception throws TTKException
 */
public static ArrayList<Object> SubmitApproveBills(Long memSeqIDForSelfFund, Long diagSeqIdForSelfFund,Long UserSeqid, Long lngHospitalSeqid)throws TTKException {
    Collection<Object> alBatchPolicyList = new ArrayList<Object>();
    ArrayList al	=	new ArrayList();
    Connection conn = null;
    //CashlessVO	cv	=	new CashlessVO();
    CallableStatement cStmtObject=null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    ResultSet rs3 =	null;
    PreparedStatement pStmt = null;
    StringBuffer sbfSQL = null;
    ArrayList alPreAuthNumb	=	new ArrayList();
   
    
    try {
    	
    	//-------------------------------
    	conn = ResourceManager.getConnection();
     	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strTotalAmtDetails);
         //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDiaglistInfo);
         cStmtObject.setLong(1,diagSeqIdForSelfFund);
         cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
         cStmtObject.execute();
         rs3 = (java.sql.ResultSet)cStmtObject.getObject(2);  
     	
			if(rs3 != null){	
             while(rs3.next()){	
    	
         conn = ResourceManager.getConnection();
     	cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strApproveDiagDetais);
         //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDiaglistInfo);
     	
     	  cStmtObject.setLong(1,diagSeqIdForSelfFund);
     	  cStmtObject.setLong(2,lngHospitalSeqid);
     	  cStmtObject.setLong(3,memSeqIDForSelfFund);
     	  cStmtObject.setBigDecimal(4,rs3.getBigDecimal("TOTAL_ENTERED_RATE"));
     	  cStmtObject.setBigDecimal(5,rs3.getBigDecimal("TOTAL_AGREED_RATE"));
     	  cStmtObject.setBigDecimal(6,rs3.getBigDecimal("TOTAL_DISCOUNT_PERC"));
     	  cStmtObject.setBigDecimal(7,rs3.getBigDecimal("TOTAL_DISCOUNT_RATE"));
      	  cStmtObject.setLong(8,UserSeqid);
       	  cStmtObject.registerOutParameter(9,Types.VARCHAR);
      	  cStmtObject.registerOutParameter(10,Types.VARCHAR);
      	  cStmtObject.registerOutParameter(11,Types.INTEGER);
     	  
      	  cStmtObject.execute();
          
      	 cStmtObject.getString(9);
      	 cStmtObject.getString(10);
      	 cStmtObject.getLong(11);
      	alPreAuthNumb.add(cStmtObject.getString(9));
      	 
             }
             
            } 
    }// end of try
    
    catch (SQLException sqlExp)
    {
        throw new TTKException(sqlExp, "onlineforms");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp)
    {
        throw new TTKException(exp, "onlineforms");
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
				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getDependentList()",sqlExp);
				throw new TTKException(sqlExp, "onlineforms");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in OnlineAccessDAOImpl getDependentList()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getDependentList()",sqlExp);
						throw new TTKException(sqlExp, "onlineforms");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "onlineforms");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			rs = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
    return alPreAuthNumb;
}//end of SubmitApproveBills(String splitIds)


public ArrayList getHospCashlessOptList(ArrayList alSearchCriteria)throws TTKException	{
	Connection conn = null;
	CallableStatement cStmtObject=null;
	ResultSet rs = null;
	CashlessVO preAuthVO=null;
	ArrayList alResultList = new ArrayList();
	try{

		String getHospPreAuthList = "{CALL HOSP_DIAGNOSYS_PKG.select_diag_mem_list(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		conn=ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(getHospPreAuthList);
		
		cStmtObject.setString(1,(String)alSearchCriteria.get(0));//CashlessNumber
		cStmtObject.setString(2,(String)alSearchCriteria.get(1));//Auth Number
		cStmtObject.setString(3,(String)alSearchCriteria.get(2));//Member Name
		cStmtObject.setString(4,(String)alSearchCriteria.get(3));//Enrollment Number
		cStmtObject.setString(5,(String)alSearchCriteria.get(4));//sOtpReq
		cStmtObject.setString(6,(String)alSearchCriteria.get(5));//sBillsPending
		cStmtObject.setString(7,(String)alSearchCriteria.get(6));//sStartDate
		cStmtObject.setString(8,(String)alSearchCriteria.get(7));//sEndDate
		cStmtObject.setLong(9,(Long)alSearchCriteria.get(8));//seqid

		cStmtObject.setString(10,(String)alSearchCriteria.get(9));//sort var
		cStmtObject.setString(11,(String)alSearchCriteria.get(10));//sort order
		cStmtObject.setString(12,(String)alSearchCriteria.get(11));//start number
		cStmtObject.setString(13,(String)alSearchCriteria.get(12));//end number
		cStmtObject.registerOutParameter(14,OracleTypes.CURSOR);
		cStmtObject.execute();
		rs = (java.sql.ResultSet)cStmtObject.getObject(14);
		if(rs != null){
			while(rs.next()){
				preAuthVO = new CashlessVO();

				preAuthVO.setPreAuthNo(TTKCommon.checkNull(rs.getString("PRE_AUTH_NUMBER")));
				preAuthVO.setClaimantName(TTKCommon.checkNull(rs.getString("MEM_NAME")));
				preAuthVO.setRefNo(TTKCommon.checkNull(rs.getString("REF_NUMBER")));
				preAuthVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_ID")));
				preAuthVO.setTotRate(TTKCommon.checkNull(rs.getString("TOT_BILL_REQ_AMOUNT")));
				preAuthVO.setDiagGenSeqId(new Long(TTKCommon.checkNull(rs.getString("DIAG_GEN_SEQ_ID"))));
				
				alResultList.add(preAuthVO);

			}//end of while(rs.next())
		}//end of if(rs != null)
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "");
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
				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getHospClaimsList",sqlExp);
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
					log.error("Error while closing the Statement inOnlineAccessDAOImpl getHospClaimsList",sqlExp);
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
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getHospClaimsList",sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			rs = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	return alResultList;
}//end of  getHospClaimsList(ArrayList alSearchCriteria)

/**
 * This method returns the HashMap,which contains the City Types associating the State
 * @return HashMap containing City Types associating the State
 * @exception throws TTKException
 */
public HashMap getCityInfo(String stateCode) throws TTKException {
	Connection conn = null;
	PreparedStatement pStmt1 = null;
 	PreparedStatement pStmt2 = null;
 	PreparedStatement pStmt3 = null;
 	ResultSet rs1 = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	CacheObject cacheObject = null;
	HashMap<Object,Object> hmCityList = null;
	ArrayList<Object> alCityList = null;
	String strStateTypeId = "";
	String strCountryId	  =	"";
	String strIsdCode	  =	"";
	String strStdCode	  =	"";
	try{
		conn = ResourceManager.getConnection();
		pStmt1=conn.prepareStatement(strStateTypeIdList);
		pStmt2 = conn.prepareStatement(strCityTypeList);
		pStmt1.setString(1,stateCode);
		
		rs1= pStmt1.executeQuery();
		if(rs1 != null){
			while(rs1.next()){
				if(hmCityList == null){
					hmCityList=new HashMap<Object,Object>();
				}//end of if(hmCityList == null)
				strStateTypeId = TTKCommon.checkNull(rs1.getString("STATE_TYPE_ID"));
				strCountryId	=	TTKCommon.checkNull(rs1.getString("COUNTRY_ID"));
				strIsdCode		=	TTKCommon.checkNull(rs1.getString("ISD_CODE"));
				strStdCode		=	TTKCommon.checkNull(rs1.getString("STD_CODE"));
				pStmt2.setString(1,strStateTypeId);
				rs2=pStmt2.executeQuery();
				
				alCityList = null;
				if(rs2 != null){
					while(rs2.next()){
						cacheObject = new CacheObject();
						if(alCityList == null){
							alCityList = new ArrayList<Object>();
						}//end of if(alCityList == null)
						cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("CITY_TYPE_ID")));
						cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("CITY_DESCRIPTION")));
						alCityList.add(cacheObject);
					}//end of while(rs2.next())
				}//end of if(rs2 != null)
				hmCityList.put(strStateTypeId,alCityList);
				hmCityList.put("CountryId",strCountryId);
				hmCityList.put("isdcode",strIsdCode);
				hmCityList.put("stdcode",strStdCode);
				
				if (rs2 != null){
    				rs2.close();
    			}//end of if (rs2 != null)
    			rs2 = null;
        	}//end of while(rs1.next())
		}//end of if(rs1 != null)
		if (pStmt2 != null){
			pStmt2.close();
		}//end of if (pStmt2 != null)
		pStmt2 = null;
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "hospital");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "hospital");
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
				log.error("Error while closing the Second Resultset in OnlineAccessDAOImpl getCityInfo()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
			{
				try
				{
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the First Resultset in OnlineAccessDAOImpl getCityInfo()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
				{
					try
					{
						if(pStmt2 != null) pStmt2.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Second Statement in OnlineAccessDAOImpl getCityInfo()",sqlExp);
						throw new TTKException(sqlExp, "hospital");
					}//end of catch (SQLException sqlExp)
					finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
					{
						try
						{
							if(pStmt1 != null) pStmt1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the First Statement in OnlineAccessDAOImpl getCityInfo()",sqlExp);
							throw new TTKException(sqlExp, "hospital");
						}//end of catch (SQLException sqlExp)
						finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
						{
							try{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in OnlineAccessDAOImpl getCityInfo()",sqlExp);
								throw new TTKException(sqlExp, "hospital");
							}//end of catch (SQLException sqlExp)
						}//end of finally
					}//end of finally
				}//end of finally 
			}//end of finally 
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "hospital");
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
	return hmCityList;
}//end of getCityInfo()

/**
 * This method adds or updates the hospital details
 * The method also calls other methods on DAO to insert/update the hospital details to the database
 * @param hospitalDetailVO HospitalDetailVO object which contains the hospital details to be added/updated
 * @return long value, Hospital Seq Id
 * @exception throws TTKException
 */
public long addUpdateHospital(HospitalDetailVO hospitalDetailVO,Long UserSeqId,Long lngHospSeqId,Long lAddSeqId) throws TTKException {
    long lHospSeqId = 0;
    Connection conn = null;
    CallableStatement cStmtObject=null;
    try{
        conn = ResourceManager.getConnection();
        cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateHospitalInfo);
        if(lngHospSeqId != null)
        {
            cStmtObject.setLong(1,lngHospSeqId);//HOSP_SEQ_ID
        }//end of if(hospitalDetailVO.getHospSeqId() != null)
        else
        {
            cStmtObject.setLong(1,0);//HOSP_SEQ_ID
        }//end of else
        if(hospitalDetailVO.getHospGnrlSeqId() != null)
        {
            cStmtObject.setLong(2,hospitalDetailVO.getHospGnrlSeqId());//HOSP_GNRL_SEQ_ID
        }//end of if(hospitalDetailVO.getHospGnrlSeqId() != null)
        else
        {
            cStmtObject.setLong(2,0);//HOSP_GNRL_SEQ_ID
        }//end of else
        if(lAddSeqId != null)
        {
            cStmtObject.setLong(3,lAddSeqId);//ADDR_SEQ_ID
        }//end of if(hospitalDetailVO.getAddressVO().getAddrSeqId() != null)
        else
        {
            cStmtObject.setLong(3,0);//ADDR_SEQ_ID
        }//end of else
        cStmtObject.setString(4,hospitalDetailVO.getDiscrepancyPresent());//DISCREPANCY_STATUS
        cStmtObject.setString(5,hospitalDetailVO.getHospitalName());//getHospitalName
        
        cStmtObject.setString(6, hospitalDetailVO.getSpeciality());//Speciality
        
        cStmtObject.setString(7, hospitalDetailVO.getTradeLicenceNo());//TRADE_LICENCE_NO
        cStmtObject.setString(8, hospitalDetailVO.getProviderTypeId());//ProviderTypeId
        cStmtObject.setString(9, hospitalDetailVO.getRegAuthority());//REGIST_AUTHORITY
        
        cStmtObject.setString(10, hospitalDetailVO.getAuthLicenceNo());//AuthLicenceNo
        
        
        cStmtObject.setString(11, hospitalDetailVO.getAddressVO().getAddress1());//ADDRESS_1
        cStmtObject.setString(12, hospitalDetailVO.getAddressVO().getAddress2());//ADDRESS_2
        cStmtObject.setString(13, hospitalDetailVO.getAddressVO().getAddress3());//ADDRESS_3
        cStmtObject.setString(14, hospitalDetailVO.getAddressVO().getCityCode());//City_Type_Id
        cStmtObject.setString(15, hospitalDetailVO.getAddressVO().getStateCode());//STATE_TYPE_ID
        cStmtObject.setString(16, hospitalDetailVO.getAddressVO().getPinCode());//PIN_CODE
        cStmtObject.setString(17, hospitalDetailVO.getAddressVO().getCountryCode());//COUNTRY_ID
        cStmtObject.setString(18, hospitalDetailVO.getIsdCode());//ISD_CODE
        
        cStmtObject.setString(19, hospitalDetailVO.getStdCode());//STD_CODE
        cStmtObject.setString(20, hospitalDetailVO.getOfficePhone1());//OFFICE_PHONE1
        cStmtObject.setString(21, hospitalDetailVO.getOfficePhone2());//OFFICE_PHONE2
        cStmtObject.setString(22, hospitalDetailVO.getFaxNbr());//OFFICE_FAX
        cStmtObject.setString(23, hospitalDetailVO.getLandmarks());//LANDMARKS
        cStmtObject.setString(24, hospitalDetailVO.getPrimaryEmailId());//PRIMARY_EMAIL_ID
        cStmtObject.setString(25, hospitalDetailVO.getWebsite());//WEBSITE
        

        cStmtObject.setLong(26, UserSeqId);//User_SEQ_ID
/*        cStmtObject.setInt(27, 0);//CONTCACT_SEQ_ID
        cStmtObject.setString(28, "HOS");//USER_GENERAL_TYPE_ID
        cStmtObject.setString(29, hospitalDetailVO.getPersonalInfoObj().getPrefix());//Prefix
        cStmtObject.setString(30, hospitalDetailVO.getPersonalInfoObj().getName());//CONTCACT_NAME
        cStmtObject.setString(31, hospitalDetailVO.getPersonalInfoObj().getDesignationTypeID());//getDesignationTypeID
        cStmtObject.setString(32, hospitalDetailVO.getPersonalInfoObj().getMobileNbr());//getMobileNbr
        cStmtObject.setString(33, hospitalDetailVO.getPersonalInfoObj().getPrimaryEmailID());//getPrimaryEmailID
        cStmtObject.setString(34, hospitalDetailVO.getPersonalInfoObj().getSecondaryEmailID());//getSecondaryEmailID
        cStmtObject.setString(35, "Y");//ACtiveYN
*/
        
        cStmtObject.registerOutParameter(27,Types.INTEGER);//ROW_PROCESSED
        cStmtObject.registerOutParameter(1,Types.BIGINT);
        cStmtObject.execute();
        lHospSeqId = cStmtObject.getLong(1);

    }//end of try
    catch (SQLException sqlExp)
    {
          throw new TTKException(sqlExp, "hospital");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp)
    {
          throw new TTKException(exp, "hospital");
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
    			log.error("Error while closing the Statement in OnlineAccessDAOImpl addUpdateHospital()",sqlExp);
    			throw new TTKException(sqlExp, "hospital");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if(conn != null) conn.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Connection in OnlineAccessDAOImpl addUpdateHospital()",sqlExp);
    				throw new TTKException(sqlExp, "hospital");
    			}//end of catch (SQLException sqlExp)
    		}//end of finally Connection Close
    	}//end of try
    	catch (TTKException exp)
    	{
    		throw new TTKException(exp, "hospital");
    	}//end of catch (TTKException exp)
    	finally // Control will reach here in anycase set null to the objects 
    	{
    		cStmtObject = null;
    		conn = null;
    	}//end of finally
	}//end of finally
    return lHospSeqId;
}//end of addUpdateHospital(HospitalDetailVO hospitalDetailVO)



/**
 * This method returns the HospitalDetailVO, which contains all the hospital details
 * @param lHospSeqId Long object which contains the hospital seq id
 * @return HospitalDetailVO object which contains all the hospital details
 * @exception throws TTKException
 */
public HospitalDetailVO getHospitalDetail(String USER_ID) throws TTKException {

    HospitalDetailVO hospitalDetailVO = null;
    AddressVO addressVO = null;
    DocumentDetailVO documentDetailVO = null;
    Connection conn = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    try
    {
        conn = ResourceManager.getConnection();

        pStmt = conn.prepareStatement(strHospitalDetail);
        pStmt.setString(1,USER_ID);
        rs = pStmt.executeQuery();
        if(rs != null){
            while (rs.next()) {
                hospitalDetailVO = new HospitalDetailVO();
                addressVO        = new AddressVO();
                documentDetailVO = new DocumentDetailVO();


                if(rs.getString("HOSP_SEQ_ID") != null){
                	hospitalDetailVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
                }//end of if(rs.getString("HOSP_SEQ_ID") != null)

                if(rs.getString("HOSP_GNRL_SEQ_ID") != null ){
                	hospitalDetailVO.setHospGnrlSeqId(new Long(rs.getLong("HOSP_GNRL_SEQ_ID")));
                }//end of if(rs.getString("HOSP_GNRL_SEQ_ID") != null )
                if(rs.getString("ADDR_SEQ_ID") != null){
                	addressVO.setAddrSeqId(new Long(rs.getLong("ADDR_SEQ_ID")));
                }//end of if(rs.getString("ADDR_SEQ_ID") != null)

                hospitalDetailVO.setEmplNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
                hospitalDetailVO.setEmplStatusTypeId(TTKCommon.checkNull(rs.getString("EMPANEL_STATUS_TYPE_ID")));
                hospitalDetailVO.setStatus(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
                hospitalDetailVO.setEmplTypeId(TTKCommon.checkNull(rs.getString("EMPANEL_TYPE_ID")));
               
                hospitalDetailVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
                hospitalDetailVO.setTypeCode(TTKCommon.checkNull(rs.getString("HOSP_TYPE_ID")));
                
                hospitalDetailVO.setRegAuthority(TTKCommon.checkNull(rs.getString("REGIST_AUTHORITY")));
                hospitalDetailVO.setAuthLicenceNo(TTKCommon.checkNull(rs.getString("HOSP_LICENC_NUMB")));
                hospitalDetailVO.setTradeLicenceNo(TTKCommon.checkNull(rs.getString("TRADE_LICENC_NUMB")));
                addressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
                addressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
                addressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
                addressVO.setCityCode(TTKCommon.checkNull(rs.getString("City_Type_Id")));
                addressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
                addressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
                addressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
                hospitalDetailVO.setAddressVO(addressVO);
                hospitalDetailVO.setStdCode(TTKCommon.checkNull(rs.getString("STD_CODE")));
                hospitalDetailVO.setLandmarks(TTKCommon.checkNull(rs.getString("LANDMARKS")));
                hospitalDetailVO.setPrimaryEmailId(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
                hospitalDetailVO.setOfficePhone1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
                hospitalDetailVO.setOfficePhone2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
                hospitalDetailVO.setFaxNbr(TTKCommon.checkNull(rs.getString("OFFICE_FAX_NO")));
                hospitalDetailVO.setWebsite(TTKCommon.checkNull(rs.getString("WEBSITE")));
                
              //projectX
                hospitalDetailVO.setProviderTypeId(TTKCommon.checkNull(rs.getString("DHA_PROVIDER_TYPE")));
                hospitalDetailVO.setPrimaryNetworkID(TTKCommon.checkNull(rs.getString("PRIMARY_NETWORK")));
                hospitalDetailVO.setIsdCode(TTKCommon.checkNull(rs.getString("ISD_CODE")));
                hospitalDetailVO.setSpeciality(TTKCommon.checkNull(rs.getString("HOSP_TYPE_ID")));
            }//end of while(rs.next())
        }//end of if(rs != null)
        return hospitalDetailVO;
    }//end of try
    catch (SQLException sqlExp)
    {
        throw new TTKException(sqlExp, "hospital");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp)
    {
        throw new TTKException(exp, "hospital");
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
				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getHospitalDetail()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{

				try
				{
					if (pStmt != null) pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in OnlineAccessDAOImpl getHospitalDetail()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl getHospitalDetail()",sqlExp);
						throw new TTKException(sqlExp, "hospital");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "hospital");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs = null;
			pStmt = null;
			conn = null;
		}//end of finally
	}//end of finally
}//end of getHospitalDetail(String USER_ID)


public long saveHospContacts(HospitalDetailVO hospitalDetailVO, Long hospseqId,Long UserSeqId) throws TTKException
{
	int lResult =	0;
	AdditionalInfoVO additionalInfoVO = null;
	ContactVO contactVO	=	null;
	Connection conn = null;
	CallableStatement cStmtObject=null;

	try{
		
		conn = ResourceManager.getConnection();
			
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveUserContact);
			if(hospitalDetailVO.getPersonalInfoObj().getContactSeqId()!=null)
			{
				cStmtObject.setLong( 1, hospitalDetailVO.getPersonalInfoObj().getContactSeqId());
			}else{
				cStmtObject.setLong( 1,0);
			}
			cStmtObject.setString( 2, "HOS");
			cStmtObject.setLong( 3, hospseqId);
			cStmtObject.setString( 4, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getPrefix()));
			cStmtObject.setString( 5, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getName()));
			cStmtObject.setString( 6, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getActiveYN()));
			cStmtObject.setString( 7, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getDesignationTypeID()));
			cStmtObject.setString( 8, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getPrimaryEmailID()));
			cStmtObject.setString( 9, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getSecondaryEmailID()));
			cStmtObject.setString( 10,TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getPhoneNbr1()));
			cStmtObject.setString( 11, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getMobileNbr()));
			
			cStmtObject.setLong( 12, UserSeqId);
			
			cStmtObject.setString( 13, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getStdCode()));
			cStmtObject.setString( 14,TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getIsdCode()));
			cStmtObject.setString( 15, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getGender()));
			cStmtObject.setString( 16, TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getAge()));
			cStmtObject.registerOutParameter(1,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			lResult = cStmtObject.getInt(1);//CONTACT_SEQ_ID
			//lResult++;
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "ORA-20869");
	}//end of catch (SQLException sqlExp)
	
	catch (Exception exp)
	{
		throw new TTKException(exp, "ORA-20869");
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
    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
    			throw new TTKException(sqlExp, "user");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if(conn != null) conn.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
    				throw new TTKException(sqlExp, "user");
    			}//end of catch (SQLException sqlExp)
    		}//end of finally Connection Close
    	}//end of try
    	catch (TTKException exp)
    	{
    		throw new TTKException(exp, "user");
    	}//end of catch (TTKException exp)
    	finally // Control will reach here in anycase set null to the objects 
    	{
    		cStmtObject = null;
    		conn = null;
    	}//end of finally
	}//end of finally
	return lResult;
}// End of saveHospContacts(HospitalDetailVO hospitalDetailVO, Long hospseqId,Long UserSeqId


public HospitalDetailVO getContact(Long ContactSeqID) throws TTKException
{
	HospitalDetailVO hospitalDetailVO = null;
    AddressVO addressVO = new AddressVO();
    PersonalInfoVO personalInfoVO = new PersonalInfoVO();
	Connection conn = null;
	ResultSet rs	=	null;
	PreparedStatement	pStmt	=	null;

	try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strgetUserContact);

            pStmt.setLong(1,ContactSeqID);
            rs = pStmt.executeQuery();

            if(rs != null){
                while(rs.next()){
                	hospitalDetailVO	=	 new HospitalDetailVO();
                	personalInfoVO.setContactSeqId(TTKCommon.getLong(TTKCommon.checkNull(rs.getString("contact_seq_id"))));
                	hospitalDetailVO.setHospSeqId(TTKCommon.getLong(TTKCommon.checkNull(rs.getString("hosp_seq_id"))));
                	personalInfoVO.setPrefix(TTKCommon.checkNull(rs.getString("prefix_general_type_id")));
                	personalInfoVO.setName(TTKCommon.checkNull(rs.getString("contact_name")));
                	personalInfoVO.setActiveYN(TTKCommon.checkNull(rs.getString("active_yn")));
                	personalInfoVO.setDesignationTypeID(TTKCommon.checkNull(rs.getString("designation_type_id")));
                	personalInfoVO.setPrimaryEmailID(TTKCommon.checkNull(rs.getString("primary_email_id")));
                	personalInfoVO.setSecondaryEmailID(TTKCommon.checkNull(rs.getString("secondary_email_id")));
                	personalInfoVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("off_phone_no_1")));
                	personalInfoVO.setMobileNbr(TTKCommon.checkNull(rs.getString("mobile_no")));
                	//personalInfoVO.setStdCode(TTKCommon.checkNull(rs.getString("std_code")));
                	//personalInfoVO.setIsdCode(TTKCommon.checkNull(rs.getString("isd_code")));
                	personalInfoVO.setGender(TTKCommon.checkNull(rs.getString("gender_general_type")));
                	personalInfoVO.setAge(TTKCommon.checkNull(rs.getString("age")));
                	hospitalDetailVO.setPersonalInfoObj(personalInfoVO);
                	
                }//end of while(rs.next())
            }//end of if(rs != null)

	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "user");
	}//end of catch (SQLException sqlExp)
	
	catch (Exception exp)
	{
		throw new TTKException(exp, "user");
	}//end of catch (Exception exp)
	finally
	{
    	/* Nested Try Catch to ensure resource closure */ 
    	try // First try closing the Statement
    	{
    		try
    		{
    			if (pStmt != null) pStmt.close();
    		}//end of try
    		catch (SQLException sqlExp)
    		{
    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
    			throw new TTKException(sqlExp, "user");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if(conn != null) conn.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
    				throw new TTKException(sqlExp, "user");
    			}//end of catch (SQLException sqlExp)
    		}//end of finally Connection Close
    	}//end of try
    	catch (TTKException exp)
    	{
    		throw new TTKException(exp, "user");
    	}//end of catch (TTKException exp)
    	finally // Control will reach here in anycase set null to the objects 
    	{
    		pStmt = null;
    		conn = null;
    	}//end of finally
	}//end of finally
	return hospitalDetailVO;
}// End of getContact(Long hospseqId)


public ArrayList getStdIsd(Long hospSeqId) throws TTKException {
	Connection conn = null;
	PreparedStatement pstmtObject=null;
	ResultSet rs = null;
	ArrayList<String> alStdIsd	=	new ArrayList();
	
	try{
		conn = ResourceManager.getConnection();
		pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strGetStdIsd);
			  pstmtObject.setLong(1, hospSeqId);
		rs = pstmtObject.executeQuery();
		if(rs != null){
			if(rs.next()){
				alStdIsd.add(0, TTKCommon.checkNull(rs.getString(1)));
				alStdIsd.add(1, TTKCommon.checkNull(rs.getString(2)));
					}
			}//end of while(rs.next())
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "user");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "user");
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
				log.error("Error while closing the Resultset in OnlineAccessDAOImpl GetPasswordConfigInfo()",sqlExp);
				throw new TTKException(sqlExp, "user");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pstmtObject != null) pstmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in OnlineAccessDAOImpl GetPasswordConfigInfo()",sqlExp);
					throw new TTKException(sqlExp, "user");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in OnlineAccessDAOImpl GetPasswordConfigInfo()",sqlExp);
						throw new TTKException(sqlExp, "user");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "user");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects 
		{
			rs = null;
			pstmtObject= null;
			conn = null;
		}//end of finally
	}//end of finally
	return alStdIsd;
}//end of getStdIsd()


/*
 * Method to get the Member details, Member Eligibility and other details from Enrollment iD
 */

	public CashlessDetailVO geMemberDetailsOnEnrollId(String EnrollId,String benifitType,Long lngHospitalSeqid,Long memSeqId) throws TTKException {
		CashlessDetailVO cashlessDetailVO	=new CashlessDetailVO();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs	=	null;
		ResultSet rs1	=	null;
		String[] netArry	=	new String[2];
		try{
			
			conn = ResourceManager.getConnection();
			
			
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetMemberOnEnroll);
				cStmtObject.setString(1,EnrollId);//EnrollId
				cStmtObject.setString(2,benifitType);//benifitType
				cStmtObject.setLong(3,lngHospitalSeqid);//benifitType
				cStmtObject.setLong(4,memSeqId);//memSeqId
				cStmtObject.registerOutParameter(5,Types.OTHER);
				cStmtObject.registerOutParameter(6,Types.OTHER);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(5);
				rs1 = (java.sql.ResultSet)cStmtObject.getObject(6);
			if(rs != null)
			{
				if(rs.next())
				{
					cashlessDetailVO=new CashlessDetailVO();
					cashlessDetailVO.setMemberName(rs.getString("MEM_NAME"));
					cashlessDetailVO.setAge(rs.getLong("MEM_AGE"));
					cashlessDetailVO.setGender(rs.getString("GENDER"));
					cashlessDetailVO.setPayer(rs.getString("PAYER_NAME"));
					cashlessDetailVO.setEligibility(rs.getString("ELIGIBILITY"));
					cashlessDetailVO.setDeductable(rs.getString("DEDUCTIBLE"));
					cashlessDetailVO.setCoPay(rs.getString("CO_PARTICIPATION"));
					//cashlessDetailVO.setApplProcudure(rs.getString("APPLICABLE_PROCEDURE"));
					cashlessDetailVO.setExclusions(rs.getString("EXCLUSIONS"));
					cashlessDetailVO.setInsurredName(rs.getString("INSURED_NAME"));
					cashlessDetailVO.setPreApprLimit(rs.getString("PRE_APPRVL_LIMIT"));
					cashlessDetailVO.setEnrollId(rs.getString("TPA_ENROLLMENT_ID"));
					cashlessDetailVO.setMemDob(rs.getDate("MEM_DOB"));
					cashlessDetailVO.setMemberSeqID(rs.getLong("MEMBER_SEQ_ID"));
					/*String temp	=	rs.getString("ELIG_DENIAL_REASON");
					if(temp!=null || temp!="")
						temp*/
					cashlessDetailVO.setReasonForRejection(rs.getString("ELIG_DENIAL_REASON"));
					
					
					netArry[0]		=	rs.getString("PRIMARY_NETWORK");//PRIMARY NETWORK;//"GN";//rs.getString("DEDUCTIBLE");//PRIMARY NETWORK
					netArry[1]		=	rs.getString("PRODUCTTYPE");//"SN";//rs.getString("DEDUCTIBLE");//POLICY NETWORK
					cashlessDetailVO.setNetworksArray(netArry);				
					cashlessDetailVO.setEmirateID(rs.getString("EMIRATE_ID"));
					
					
					
					cashlessDetailVO.setPolicyNum(TTKCommon.checkNull(rs.getString("policy_number")));
					cashlessDetailVO.setMember_network(TTKCommon.checkNull(rs.getString("member_network")));
					cashlessDetailVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("dhpo_member_id")));
				
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			
			if(rs1 != null)
			{
				Map<String, String> provNetworksMap	=	new LinkedHashMap<String, String>();
				while(rs1.next())
				{
					provNetworksMap.put(rs1.getString("NETWORK_TYPE"), rs1.getString("NETWORK_YN"));
					/*netArry[2]		=	rs1.getString("CN_YN");//"N";//rs.getString("DEDUCTIBLE");//CN
					netArry[3]		=	rs1.getString("GN_YN");//"Y";//rs.getString("DEDUCTIBLE");//GN
					netArry[4]		=	rs1.getString("SN_YN");//"Y";//rs.getString("DEDUCTIBLE");//SN
					netArry[5]		=	rs1.getString("BN_YN");//"N";//rs.getString("DEDUCTIBLE");//BN
					netArry[6]		=	rs1.getString("WN_YN");//"N";//rs.getString("DEDUCTIBLE");//WN
					*/
				}//end of while(rs.next())
				cashlessDetailVO.setAssNetworksArray(provNetworksMap);
			}//end of if(rs != null)
			
				//lResult++;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "user");
		}//end of catch (SQLException sqlExp)
		
		catch (Exception exp)
		{
			throw new TTKException(exp, "user");
		}//end of catch (Exception exp)
		
		
		finally
		{
	    	/* Nested Try Catch to ensure resource closure */ 
	    	try // First try closing the Statement
	    	{
	    		try{
	    			if(rs!=null) rs.close();
	    		}catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getLabServices()",sqlExp);
					throw new TTKException(sqlExp, "user");
				}//end of catch (SQLException sqlExp)
	    		try{
	    			if(rs1!=null) rs1.close();
	    		}catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getLabServices()",sqlExp);
					throw new TTKException(sqlExp, "user");
				}//end of catch (SQLException sqlExp)
	    		try
	    		{
	    			if (cStmtObject != null) cStmtObject.close();
	    		}//end of try
	    		catch (SQLException sqlExp)
	    		{
	    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
	    			throw new TTKException(sqlExp, "user");
	    		}//end of catch (SQLException sqlExp)
	    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	    		{
	    			try
	    			{
	    				if(conn != null) conn.close();
	    			}//end of try
	    			catch (SQLException sqlExp)
	    			{
	    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
	    				throw new TTKException(sqlExp, "user");
	    			}//end of catch (SQLException sqlExp)
	    		}//end of finally Connection Close
	    	}//end of try
	    	catch (TTKException exp)
	    	{
	    		throw new TTKException(exp, "user");
	    	}//end of catch (TTKException exp)
	    	finally // Control will reach here in anycase set null to the objects 
	    	{
	    		cStmtObject = null;
	    		conn = null;
	    	}//end of finally
		}//end of finally
		return cashlessDetailVO;
	}//end of geMemberDetailsOnEnrollId()
	
	public CashlessDetailVO geMemberDetailsOnEnrollIdNew(String EnrollId) throws TTKException {
		CashlessDetailVO cashlessDetailVO	=new CashlessDetailVO();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs	=	null;
		try{
			
			conn = ResourceManager.getConnection();
				
				
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetMemberOnEnrollNew);
				cStmtObject.setString(1,EnrollId);//EnrollId
				cStmtObject.registerOutParameter(2,Types.OTHER);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null)
			{
				if(rs.next())
				{
					cashlessDetailVO=new CashlessDetailVO();
					cashlessDetailVO.setMemberName(rs.getString("MEM_NAME"));
					cashlessDetailVO.setAge(rs.getLong("YEARS"));
					cashlessDetailVO.setGender(rs.getString("GENDER"));
					cashlessDetailVO.setEligibility(rs.getString("MONTHS"));
					cashlessDetailVO.setEnrollmentID(rs.getString("EMPLOYEE_NO"));
					cashlessDetailVO.setEnrollId(rs.getString("TPA_ENROLLMENT_ID"));
					cashlessDetailVO.setInsurredName(rs.getString("COMPANY_NAME"));
					cashlessDetailVO.setModeType(rs.getString("MOBILE_NO"));
					cashlessDetailVO.setMemDob(rs.getDate("MEM_DOB"));
					cashlessDetailVO.setPolicyNo(rs.getString("POLICY_NUMBER"));
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			
				//lResult++;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "user");
		}//end of catch (SQLException sqlExp)
		
		catch (Exception exp)
		{
			throw new TTKException(exp, "user");
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
	    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
	    			throw new TTKException(sqlExp, "user");
	    		}//end of catch (SQLException sqlExp)
	    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	    		{
	    			try
	    			{
	    				if(conn != null) conn.close();
	    			}//end of try
	    			catch (SQLException sqlExp)
	    			{
	    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
	    				throw new TTKException(sqlExp, "user");
	    			}//end of catch (SQLException sqlExp)
	    		}//end of finally Connection Close
	    	}//end of try
	    	catch (TTKException exp)
	    	{
	    		throw new TTKException(exp, "user");
	    	}//end of catch (TTKException exp)
	    	finally // Control will reach here in anycase set null to the objects 
	    	{
	    		cStmtObject = null;
	    		conn = null;
	    	}//end of finally
		}//end of finally
		return cashlessDetailVO;
	}//end of geMemberDetailsOnEnrollIdNew(String EnrollId
	
	/*
	 *Procedure to save the Member Vital Details  in Hospital cashless Module 
	 */
	public int saveMemberVitals(String EnrollId, CashlessDetailVO cashlessDetailVO, Long UserSeqId) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs	=	null;
		int iResult	=	0;
	
		try{
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMemberVitals);
        	
    		cStmtObject.setString(1,null);
        	cStmtObject.setString(2,EnrollId);
        	cStmtObject.setString(3,cashlessDetailVO.getBpSystolic());
        	cStmtObject.setString(4,cashlessDetailVO.getBpDiastolic());
        	cStmtObject.setString(5,cashlessDetailVO.getTemprature());
        	cStmtObject.setString(6,cashlessDetailVO.getPulse());
        	cStmtObject.setString(7,cashlessDetailVO.getRespiration());
        	cStmtObject.setString(8,cashlessDetailVO.getHeight());
        	cStmtObject.setString(9,cashlessDetailVO.getWeight());
        	cStmtObject.setLong(10,UserSeqId);
        	cStmtObject.registerOutParameter(11,Types.BIGINT);
        	
        	cStmtObject.execute();
        	iResult = cStmtObject.getInt(11);
    }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "user");
		}//end of catch (SQLException sqlExp)
		
		catch (Exception exp)
		{
			throw new TTKException(exp, "user");
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
	    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
	    			throw new TTKException(sqlExp, "user");
	    		}//end of catch (SQLException sqlExp)
	    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	    		{
	    			try
	    			{
	    				if(conn != null) conn.close();
	    			}//end of try
	    			catch (SQLException sqlExp)
	    			{
	    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
	    				throw new TTKException(sqlExp, "user");
	    			}//end of catch (SQLException sqlExp)
	    		}//end of finally Connection Close
	    	}//end of try
	    	catch (TTKException exp)
	    	{
	    		throw new TTKException(exp, "user");
	    	}//end of catch (TTKException exp)
	    	finally // Control will reach here in anycase set null to the objects 
	    	{
	    		cStmtObject = null;
	    		conn = null;
	    	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveMemberVitals().
	
	/*
	 *Procedure to save the Member Vital Details  in Hospital cashless Module 
	 */
	public ArrayList getLabServices(String type, Long HospSeqId) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
        ResultSet rs = null;
        LaboratoryServicesVO laboratoryServicesVO = null;
        ArrayList<Object> alServicesList = null;
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strLabServices);
            pStmt.setString(1,type);
            //pStmt.setLong(2,HospSeqId);
            rs = pStmt.executeQuery();
            alServicesList= new ArrayList<Object>();
            if(rs != null){
                while(rs.next()){
                	laboratoryServicesVO = new LaboratoryServicesVO();
                    /*if(rs.getString("PRESCRIPTION_SEQ_ID") != null)
                    {
                    	laboratoryServicesVO.setMedicalSeqId(new Long(rs.getLong("PRESCRIPTION_SEQ_ID")));
                    }//end of if(rs.getString("MEDICAL_SEQ_ID") != null)
                    */
                	laboratoryServicesVO.setMedicalTypeId(TTKCommon.checkNull(rs.getString("activity_code")));
                    laboratoryServicesVO.setGroupName(TTKCommon.checkNull(rs.getString("sub_service_type")));
                    laboratoryServicesVO.setQuestionDesc(TTKCommon.checkNull(rs.getString("medical_description")));
                    /*if((TTKCommon.checkNull(rs.getString("VALUE_2")) != null) && (!TTKCommon.checkNull(rs.getString("VALUE_2")).equals("")))
                    {
                    	laboratoryServicesVO.setAnswer2(TTKCommon.checkNull(rs.getString("VALUE_2")));
                    }//end of if((TTKCommon.checkNull(rs.getString("VALUE_2")) != null) && (!TTKCommon.checkNull(rs.getString("VALUE_2")).equals("")))
                    */alServicesList.add(laboratoryServicesVO);
                }//end of while(rs.next())
            }//end of if(rs != null)
            return alServicesList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "user");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "user");
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
					log.error("Error while closing the Resultset in OnlineAccessDAOImpl getLabServices()",sqlExp);
					throw new TTKException(sqlExp, "user");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getLabServices()",sqlExp);
						throw new TTKException(sqlExp, "user");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in OnlineAccessDAOImpl getLabServices()",sqlExp);
							throw new TTKException(sqlExp, "user");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "user");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
   }//end of getLabServices(String type, Long HospSeqId)
	
	
	/*
	 * TO save the Downloaded File  in FInance DashBoard -  Provider Login
	 */
	public int updateDownloadHistory(String EmpanelNumber, String destFileName, String status,Long userID,String FileData) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs	=	null;
		int iResult	=	0;
	
		try{
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDownloadHistory);
        	
    		cStmtObject.setString(1,EmpanelNumber);
        	cStmtObject.setString(2,destFileName);
        	cStmtObject.setString(3,status);
        	cStmtObject.setLong(4,userID);
        	cStmtObject.setString(6,FileData);
        	cStmtObject.registerOutParameter(5,Types.BIGINT);
        	
        	cStmtObject.execute();
        	conn.commit();
        	iResult = (int) cStmtObject.getLong(5);
    }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "user");
		}//end of catch (SQLException sqlExp)
		
		catch (Exception exp)
		{
			throw new TTKException(exp, "user");
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
	    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
	    			throw new TTKException(sqlExp, "user");
	    		}//end of catch (SQLException sqlExp)
	    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	    		{
	    			try
	    			{
	    				if(conn != null) conn.close();
	    			}//end of try
	    			catch (SQLException sqlExp)
	    			{
	    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
	    				throw new TTKException(sqlExp, "user");
	    			}//end of catch (SQLException sqlExp)
	    		}//end of finally Connection Close
	    	}//end of try
	    	catch (TTKException exp)
	    	{
	    		throw new TTKException(exp, "user");
	    	}//end of catch (TTKException exp)
	    	finally // Control will reach here in anycase set null to the objects 
	    	{
	    		cStmtObject = null;
	    		conn = null;
	    	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveMemberVitals().
	
	
	public String[] getTobForBenefits(String benifitType,String enrollId,Long memSeqId) throws TTKException {
		CashlessDetailVO cashlessDetailVO	=new CashlessDetailVO();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs	=	null;
		ResultSet rs1	=	null;
		String[] tobBenefits	=	new String[13];
		try{
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetTOBForBenefir);
				cStmtObject.setString(1,benifitType);//EnrollId
				cStmtObject.setString(2,enrollId);//benifitType
				cStmtObject.setLong(3,memSeqId);//memSeqId
				cStmtObject.registerOutParameter(4,Types.OTHER);
				cStmtObject.execute();
				rs1 = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs1 != null)
			{
				if(rs1.next())
				{
					tobBenefits[0]	=	rs1.getString("COPAY");
					tobBenefits[1]	=	rs1.getString("ELIGIBILITY");
					tobBenefits[2]	=	rs1.getString("DEDUCTIBLE");
					tobBenefits[3]	=	rs1.getString("CO_INS");
					tobBenefits[4]	=	rs1.getString("CLASS1");
					tobBenefits[5]	=	rs1.getString("MATERNITY_YN");
					tobBenefits[6]	=	rs1.getString("MATERNITY_COPAY");
					tobBenefits[7]	=	rs1.getString("OPTICAL_YN");
					tobBenefits[8]	=	rs1.getString("OPTICAL_COPAY");
					tobBenefits[9]	=	rs1.getString("DENTAL_YN");
					tobBenefits[10]	=	rs1.getString("DENTAL_COPAY");
					tobBenefits[11]	=	rs1.getString("IP_OP_SERVICES");
					tobBenefits[12]	=	rs1.getString("PHARMACEUTICALS");
				}//end of if(rs1.next())
			}//end of if(rs1 != null)
			
			
				//lResult++;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "user");
		}//end of catch (SQLException sqlExp)
		
		catch (Exception exp)
		{
			throw new TTKException(exp, "user");
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
	    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
	    			throw new TTKException(sqlExp, "user");
	    		}//end of catch (SQLException sqlExp)
	    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	    		{
	    			try
	    			{
	    				if(conn != null) conn.close();
	    			}//end of try
	    			catch (SQLException sqlExp)
	    			{
	    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
	    				throw new TTKException(sqlExp, "user");
	    			}//end of catch (SQLException sqlExp)
	    		}//end of finally Connection Close
	    	}//end of try
	    	catch (TTKException exp)
	    	{
	    		throw new TTKException(exp, "user");
	    	}//end of catch (TTKException exp)
	    	finally // Control will reach here in anycase set null to the objects 
	    	{
	    		cStmtObject = null;
	    		conn = null;
	    	}//end of finally
		}//end of finally
		return tobBenefits;
	}//end of String[] getTobForBenefits(String benifitType,String enrollId
	


public String[] getEligibilityStatus(String enrollId) throws TTKException {
	CashlessDetailVO cashlessDetailVO	=new CashlessDetailVO();
	Connection conn = null;
	PreparedStatement cStmtObject=null;
	ResultSet rs1	=	null;
	String[] cardNumYN=new String[2];
			
			cardNumYN[0] = "N";
	try{
			conn = ResourceManager.getConnection();
		    cStmtObject=conn.prepareStatement("select m.member_seq_id, decode(m.status_general_type_id,'POA','Y','N') eligible_yn,m.status_general_type_id from app.tpa_enr_policy_member m where replace(m.emirate_id,'-','') =?");	
			cStmtObject.setString(1,enrollId);
			rs1=cStmtObject.executeQuery();
			
		if(rs1 != null)
		{
			if(rs1.next())
			{
				cardNumYN[0] = rs1.getString("eligible_yn");
				cardNumYN[1] = rs1.getString("member_seq_id");
				
			}
		}//end of if(rs1 != null)
		
		
			//lResult++;
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "user");
	}//end of catch (SQLException sqlExp)
	
	catch (Exception exp)
	{
		throw new TTKException(exp, "user");
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
    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
    			throw new TTKException(sqlExp, "user");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if(conn != null) conn.close();
    				if(rs1!=null) rs1.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
    				throw new TTKException(sqlExp, "user");
    			}//end of catch (SQLException sqlExp)
    		}//end of finally Connection Close
    	}//end of try
    	catch (TTKException exp)
    	{
    		throw new TTKException(exp, "user");
    	}//end of catch (TTKException exp)
    	finally // Control will reach here in anycase set null to the objects 
    	{
    		cStmtObject = null;
    		conn = null;
    		rs1=null;
    	}//end of finally
	}//end of finally
	return cardNumYN;
}//end of String getEligibilityStatus(String enrollId)


/**
 * This method saves the existing password
 * @param alForgotPass ArrayList object which contains the Employee Login Information
 * @return String which returns the result string
 * @exception throws TTKException
 */
public String savePartnerForgotPassword(ArrayList<Object> alForgotPass) throws TTKException {
	Connection conn = null;
	CallableStatement cStmtObject=null;
	String strPassword="";
	try{
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPartnerForgotPassword);
		cStmtObject.setString(1,(String)alForgotPass.get(0));//v_empanel_id
		cStmtObject.setString(2,(String)alForgotPass.get(1));//v_userid
		
		
		cStmtObject.registerOutParameter(3,Types.VARCHAR);
		cStmtObject.execute();
		strPassword = cStmtObject.getString(3);
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "onlineforms");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "onlineforms");
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
				log.error("Error while closing the Statement in OnlineAccessDAOImpl saveQueryHeaderInfo()",sqlExp);
				throw new TTKException(sqlExp, "onlineforms");
			}//end of catch (SQLException sqlExp)
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in OnlineAccessDAOImpl saveQueryHeaderInfo()",sqlExp);
					throw new TTKException(sqlExp, "onlineforms");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "onlineforms");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	return strPassword;
}//end of saveQueryHeaderInfo(OnlineAssistanceDetailVO onlineAssistanceDetailVO)


public ArrayList getPartnerDetails(String empanelId) throws TTKException {
	Connection conn = null;
	CallableStatement cStmtObject=null;
	ArrayList alResultList = new ArrayList();
	ResultSet rs = null;
	HospitalDetailsVo partnerVO=null;
	try {


		conn = ResourceManager.getConnection();
		String strgetPartnerDetails	=	"{CALL PARTNER_PKG.SELECT_PARTNER_DETAILS(?,?)}";
		//cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strgetHospDetails);
		cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strgetPartnerDetails);

		cStmtObject.setString(1,empanelId);//EMPANEL ID
		
		cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
		cStmtObject.execute();
		rs = (java.sql.ResultSet)cStmtObject.getObject(2);
		if(rs != null){
			while(rs.next()){
				partnerVO = new HospitalDetailsVo();

				partnerVO.setHospName(TTKCommon.checkNull(rs.getString("partner_name")));
				partnerVO.setEmpanelNo(TTKCommon.checkNull(rs.getString("partner_id")));
				partnerVO.setState(TTKCommon.checkNull(rs.getString("area")));
				partnerVO.setCity(TTKCommon.checkNull(rs.getString("city")));

				partnerVO.setAddress(TTKCommon.checkNull(rs.getString("address")));
				partnerVO.setPinCode(TTKCommon.checkNull(rs.getString("pincode")));
				partnerVO.setCountry(TTKCommon.checkNull(rs.getString("country")));
				partnerVO.setOffPhone(TTKCommon.checkNull(rs.getString("Office_Phone")));
				partnerVO.setHospMailId(TTKCommon.checkNull(rs.getString("partner_email")));
				partnerVO.setApprovalLimit(TTKCommon.checkNull(rs.getString("Ipt_Approval_Limit")));
				alResultList.add(partnerVO);
			}
		}

	}catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "onlineforms");
	}//end of catch (SQLException sqlExp)
	catch (Exception exp)
	{
		throw new TTKException(exp, "onlineforms");
	}//end of catch (Exception exp)
	finally{

		/* Nested Try Catch to ensure resource closure */
		try // First try closing the Statement
		{
			try
			{
				if (cStmtObject != null) cStmtObject.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Statement in OnlineAccessDAOImpl saveHospitalFileUpload",sqlExp);
				throw new TTKException(sqlExp, "onlineformsemp");
			}//end of catch (SQLException sqlExp)
			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			{
				try
				{
					if(conn != null) conn.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Connection in OnlineAccessDAOImpl saveHospitalFileUpload",sqlExp);
					throw new TTKException(sqlExp, "onlineformsemp");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
		}//end of try
		catch (TTKException exp)
		{
			throw new TTKException(exp, "onlineformsemp");
		}//end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the objects
		{
			cStmtObject = null;
			conn = null;
		}//end of finally

	}
	return alResultList;
}


//Added For the partner Login

	public Document getPartnerDashBoard(Long lngPartnerSeqid,String sFromDate,String sToDate,String strType) throws TTKException{

		Connection conn = null;
		OracleCallableStatement cStmtObject=null;
		long lResult = 0;
		XMLType xml = null;
		Document doc=null;

		try {
			conn = ResourceManager.getConnection();
			String strPartnerDashBoard="";

			if(strType.equalsIgnoreCase("Claims"))		{
				strPartnerDashBoard="{CALL PARTNER_PKG.DASH_BOARD_CLAIMS(?,?,?,?)}";
				cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPartnerDashBoard);
				cStmtObject.setLong(1,lngPartnerSeqid);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
				cStmtObject.setString(2, sFromDate);
				cStmtObject.setString(3, sToDate);
				cStmtObject.registerOutParameter(4,OracleTypes.OPAQUE,"SYS.XMLTYPE");
				cStmtObject.execute();
				xml = XMLType.createXML(((OracleCallableStatement) cStmtObject).getOPAQUE(4));
			}//end of if(strType.equalsIgnoreCase("Claims"))

			else if(strType.equalsIgnoreCase("Preauth"))	{
				strPartnerDashBoard="{CALL PARTNER_PKG.DASH_BOARD_PREAUTH(?,?,?,?)}";
				cStmtObject = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPartnerDashBoard);				
				cStmtObject.setLong(1,lngPartnerSeqid);//ENH-> enhancement, HPR-> Pre-Authorization , POL->Policy , HCL-> Claims
				cStmtObject.setString(2, sFromDate);
				cStmtObject.setString(3, sToDate);
				cStmtObject.registerOutParameter(4,OracleTypes.OPAQUE,"SYS.XMLTYPE");
				cStmtObject.execute();
				xml = XMLType.createXML(((OracleCallableStatement) cStmtObject).getOPAQUE(4));
			}//end of if(strType.equalsIgnoreCase("Claims"))
			DOMReader domReader = new DOMReader();
			doc = xml != null ? domReader.read(xml.getDOM()):null;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "history");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "history");
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
					log.error("Error while closing the Statement in OnlineAccessDAOImpl getPartnerDashBoard()",sqlExp);
					throw new TTKException(sqlExp, "history");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in OnlineAccessDAOImpl getPartnerDashBoard()",sqlExp);
						throw new TTKException(sqlExp, "history");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "history");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects
			{
				cStmtObject = null;
				conn = null;
			}//end of finally

		}//end of finally
		return doc;
	}//end of  getPartnerDashBoard(Long lngPartnerSeqid,String strType) throws TTKException
	
	
	  public ArrayList getPartnerValidateEnrollId(String enrollmentId,Long UserSeqid,String treatmentDate) throws TTKException {
	    	ResultSet rs = null;
	        ArrayList<Object> alcacheObj = new ArrayList<Object>();
	        Connection conn = null;
	        PreparedStatement pStmt = null;
	        OracleCallableStatement oCstmt = null;
	        Long member_seq_id	=	null;
	        Long iresult			=	null;
	        
	        String flag="";
	       
	        try{
	            conn = ResourceManager.getConnection();
	            //oCstmt=conn.prepareStatement(strValidateEnrollID);
	            
			
			oCstmt = (OracleCallableStatement)((OracleConnection)((WrappedConnectionJDK6)conn).getUnderlyingConnection()).prepareCall(strPartnerValidateEnrollId);
			oCstmt.setString(1,enrollmentId);
			oCstmt.setLong(2,UserSeqid);
			oCstmt.setString(4,treatmentDate);
			oCstmt.registerOutParameter(3,Types.INTEGER);
			oCstmt.registerOutParameter(5,Types.INTEGER);
			
			oCstmt.execute();
			member_seq_id		=	oCstmt.getLong(3);;	
			iresult				=	oCstmt.getLong(5);
			CashlessVO cacheObj	=	new CashlessVO();
			
			cacheObj.setMemberSeqID(member_seq_id);//tpa_enr_policy_member.member_seq_id
			alcacheObj.add(cacheObj);
			

	//rs = (java.sql.ResultSet)oCstmt.getObject(6);
	           if(iresult != 0){
	            	         	 
	            	flag = "true";
	            }//end of if(rs != null)
	            else{
	            	flag = "false";
	            }
	           alcacheObj.add(flag);
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "onlineforms");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "onlineforms");
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
	    				log.error("Error while closing the Resultset in OnlineAccessDAOImpl getPartnerValidateEnrollId()",sqlExp);
	    				throw new TTKException(sqlExp, "onlineforms");
	    			}//end of catch (SQLException sqlExp)
	    			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
	    			{
	    				try
	    				{
	    					if (oCstmt != null) oCstmt.close();
	    				}//end of try
	    				catch (SQLException sqlExp)
	    				{
	    					log.error("Error while closing the Statement in OnlineAccessDAOImpl getPartnerValidateEnrollId()",sqlExp);
	    					throw new TTKException(sqlExp, "onlineforms");
	    				}//end of catch (SQLException sqlExp)
	    				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	    				{
	    					try
	    					{
	    						if(conn != null) conn.close();
	    					}//end of try
	    					catch (SQLException sqlExp)
	    					{
	    						log.error("Error while closing the Connection in OnlineAccessDAOImpl getPartnerValidateEnrollId()",sqlExp);
	    						throw new TTKException(sqlExp, "onlineforms");
	    					}//end of catch (SQLException sqlExp)
	    				}//end of finally Connection Close
	    			}//end of finally Statement Close
	    		}//end of try
	    		catch (TTKException exp)
	    		{
	    			throw new TTKException(exp, "onlineforms");
	    		}//end of catch (TTKException exp)
	    		finally // Control will reach here in anycase set null to the objects
	    		{
	    			rs = null;
	    			conn = null;
	    		}//end of finally
	    	}//end of finally
			return alcacheObj;
	    }//end of getSelectedPolicyNumber(String strGroupID)

	  
		public CashlessDetailVO getPartnerMemberDetailsOnEnrollId(String EnrollId,String benifitType,String encounterType,String dateOfDischarge,Double estimatedCost,String hospitalName,Long countryId, String currencyId) throws TTKException {
			CashlessDetailVO cashlessDetailVO	=new CashlessDetailVO();
			Connection conn = null;
			CallableStatement cStmtObject=null;
			ResultSet rs	=	null;
			String[] netArry	=	new String[2];
			try{
				
				conn = ResourceManager.getConnection();
					
					
					cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetPartnerMemberOnEnroll);
					cStmtObject.setString(1,EnrollId);//EnrollId
					cStmtObject.setString(2,benifitType);//benifitType
					cStmtObject.setString(3,encounterType);//encounterType
					cStmtObject.setString(4,dateOfDischarge);//dateOfDischarge
					cStmtObject.setDouble(5,estimatedCost);//estimatedCost
					cStmtObject.setString(6,hospitalName);//hospitalName
					cStmtObject.setLong(7,countryId);//countryId
					cStmtObject.setString(8,currencyId);//currencyId
					cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
					cStmtObject.execute();
					rs = (java.sql.ResultSet)cStmtObject.getObject(9);
				if(rs != null)
				{
					if(rs.next())
					{
				/*		cashlessDetailVO=new CashlessDetailVO();*/
						cashlessDetailVO.setMemberName(rs.getString("MEM_NAME"));
						cashlessDetailVO.setAge(rs.getLong("MEM_AGE"));
						cashlessDetailVO.setGender(rs.getString("GENDER"));
						cashlessDetailVO.setPayer(rs.getString("PAYER_NAME"));
						cashlessDetailVO.setAreaOfCoverageCode(rs.getString("AREA"));
						if(cashlessDetailVO.getAreaOfCoverageCode()==null||cashlessDetailVO.getAreaOfCoverageCode().trim().equals("NULL")||cashlessDetailVO.getAreaOfCoverageCode().trim().equals("")){
							cashlessDetailVO.setEligibilityBenefit(rs.getString("ELIGIBILITY_BENEFIT"));
							if(("YES".equals(cashlessDetailVO.getEligibilityBenefit()))||(("Y").equals(cashlessDetailVO.getEligibilityBenefit())))
							cashlessDetailVO.setEligibilityEncounter(rs.getString("ELIGIBILITY_ENCOUNTER"));
						}
						else{
							cashlessDetailVO.setEligibility(rs.getString("ELIGIBILITY"));
						}
						cashlessDetailVO.setDeductable(rs.getString("DEDUCTIBLE"));
						cashlessDetailVO.setCoPay(rs.getString("CO_PARTICIPATION"));
						//cashlessDetailVO.setApplProcudure(rs.getString("APPLICABLE_PROCEDURE"));
						cashlessDetailVO.setExclusions(rs.getString("EXCLUSIONS"));
						cashlessDetailVO.setInsurredName(rs.getString("INSURED_NAME"));
						cashlessDetailVO.setPreApprLimit(rs.getString("PRE_APPRVL_LIMIT"));
						cashlessDetailVO.setEnrollId(rs.getString("TPA_ENROLLMENT_ID"));
						cashlessDetailVO.setMemDob(rs.getDate("MEM_DOB"));
						cashlessDetailVO.setMemberSeqID(rs.getLong("MEMBER_SEQ_ID"));
						/*String temp	=	rs.getString("ELIG_DENIAL_REASON");
						if(temp!=null || temp!="")
							temp*/
						cashlessDetailVO.setReasonForRejection(rs.getString("ELIG_DENIAL_REASON"));
						//netArry[0]		=	rs.getString("PRIMARY_NETWORK");//PRIMARY NETWORK;//"GN";//rs.getString("DEDUCTIBLE");//PRIMARY NETWORK
						netArry[0]		=	rs.getString("PRODUCTTYPE");//"SN";//rs.getString("DEDUCTIBLE");//POLICY NETWORK
						cashlessDetailVO.setNetworksArray(netArry);				
						cashlessDetailVO.setEmirateID(rs.getString("EMIRATE_ID"));

						cashlessDetailVO.setPolicyNo(rs.getString("POLICY_NO"));
						cashlessDetailVO.setPolicyStDt(rs.getString("POLICY_ST_DT"));
						cashlessDetailVO.setPolicyEnDt(rs.getString("POLICY_EN_DT"));
						cashlessDetailVO.setProdPolicySeqId(rs.getLong("PROD_POLICY_RULE_SEQ_ID"));
						cashlessDetailVO.setPolicySeqID(rs.getLong("POLICY_SEQ_ID"));
					}//end of while(rs.next())
				}//end of if(rs != null)
			}//end of try
			catch (SQLException sqlExp)
			{
				sqlExp.printStackTrace();
				throw new TTKException(sqlExp, "user");
			}//end of catch (SQLException sqlExp)
			
			catch (Exception exp)
			{
				
				throw new TTKException(exp, "user");
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
		    			log.error("Error while closing the Statement in OnlineAccessDAOImpl getPartnerMemberDetailsOnEnrollId()",sqlExp);
		    			throw new TTKException(sqlExp, "user");
		    		}//end of catch (SQLException sqlExp)
		    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
		    		{
		    			try
		    			{
		    				if(conn != null) conn.close();
		    			}//end of try
		    			catch (SQLException sqlExp)
		    			{
		    				log.error("Error while closing the Connection in OnlineAccessDAOImpl getPartnerMemberDetailsOnEnrollId()",sqlExp);
		    				throw new TTKException(sqlExp, "user");
		    			}//end of catch (SQLException sqlExp)
		    			finally{
		    				try{
		    					if(rs!=null) rs.close();
		    				}
		    				catch(SQLException sqlExp){
			    				log.error("Error while closing the ResultSet in OnlineAccessDAOImpl getPartnerMemberDetailsOnEnrollId()",sqlExp);
		    				}
		    			}
		    		}//end of finally Connection Close
		    	}//end of try
		    	catch (TTKException exp)
		    	{
		    		throw new TTKException(exp, "user");
		    	}//end of catch (TTKException exp)
		    	finally // Control will reach here in anycase set null to the objects 
		    	{
		    		cStmtObject = null;
		    		conn = null;
		    	}//end of finally
			}//end of finally
			return cashlessDetailVO;
		}//end of geMemberDetailsOnEnrollId()
	
		
		public String[] getPartnerTobForBenefits(String benifitType,String enrollId) throws TTKException {
			CashlessDetailVO cashlessDetailVO	=new CashlessDetailVO();
			Connection conn = null;
			CallableStatement cStmtObject=null;
			ResultSet rs	=	null;
			ResultSet rs1	=	null;
			String[] tobBenefits	=	new String[18];
			try{
					conn = ResourceManager.getConnection();
					cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strPartnerGetTOBForBenefir);
					cStmtObject.setString(1,benifitType);//EnrollId
					cStmtObject.setString(2,enrollId);//benifitType
					cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
					cStmtObject.execute();
					rs1 = (java.sql.ResultSet)cStmtObject.getObject(3);
				if(rs1 != null)
				{
					if(rs1.next())
					{
						tobBenefits[0]	=	rs1.getString("COPAY");
						tobBenefits[1]	=	rs1.getString("ELIGIBILITY");
						tobBenefits[2]	=	rs1.getString("DEDUCTIBLE");
						tobBenefits[3]	=	rs1.getString("CO_INS");
						tobBenefits[4]	=	rs1.getString("CLASS");
						tobBenefits[5]	=	rs1.getString("PLAN");
						tobBenefits[6]	=	rs1.getString("MATERNITY_YN");
						tobBenefits[7]	=	rs1.getString("MATERNITY_COPAY");
						tobBenefits[8]	=	rs1.getString("OPTICAL_YN");
						tobBenefits[9]	=	rs1.getString("OPTICAL_COPAY");
						tobBenefits[10]	=	rs1.getString("DENTAL_YN");
						tobBenefits[11]	=	rs1.getString("DENTAL_COPAY");
						tobBenefits[12]	=	rs1.getString("IP_OP_SERVICES");
						tobBenefits[13]	=	rs1.getString("PHARMACEUTICALS");
						tobBenefits[14] = rs1.getString("SUM_INSURED");
						tobBenefits[15] = rs1.getString("UTILISED_SUM_INSURED");
						tobBenefits[16] = rs1.getString("AVAILABLE_SUM_INSURED");
						tobBenefits[17] = rs1.getString("COUNTRY COVERED");
					}//end of if(rs1.next())
				}//end of if(rs1 != null)
				
				
					//lResult++;
			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "user");
			}//end of catch (SQLException sqlExp)
			
			catch (Exception exp)
			{
				throw new TTKException(exp, "user");
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
		    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
		    			throw new TTKException(sqlExp, "user");
		    		}//end of catch (SQLException sqlExp)
		    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
		    		{
		    			try
		    			{
		    				if(conn != null) conn.close();
		    			}//end of try
		    			catch (SQLException sqlExp)
		    			{
		    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
		    				throw new TTKException(sqlExp, "user");
		    			}//end of catch (SQLException sqlExp)
		    		}//end of finally Connection Close
		    	}//end of try
		    	catch (TTKException exp)
		    	{
		    		throw new TTKException(exp, "user");
		    	}//end of catch (TTKException exp)
		    	finally // Control will reach here in anycase set null to the objects 
		    	{
		    		cStmtObject = null;
		    		conn = null;
		    	}//end of finally
			}//end of finally
			return tobBenefits;
		}//end of String[] getTobForBenefits(String benifitType,String enrollId

		/*
		 * Method to get the Member details, Member Eligibility and other details from Enrollment iD
		 */

			public CashlessDetailVO geMemberDetailsOnEnrollId(String EnrollId,String benifitType,Long lngHospitalSeqid) throws TTKException {
				CashlessDetailVO cashlessDetailVO	=new CashlessDetailVO();
				Connection conn = null;
				CallableStatement cStmtObject=null;
				ResultSet rs	=	null;
				ResultSet rs1	=	null;
				String[] netArry	=	new String[2];
				try{
					
					conn = ResourceManager.getConnection();
						
						
						cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetMemberOnEnroll);
						cStmtObject.setString(1,EnrollId);//EnrollId
						cStmtObject.setString(2,benifitType);//benifitType
						cStmtObject.setLong(3,lngHospitalSeqid);//benifitType
						cStmtObject.registerOutParameter(4,Types.OTHER);
						cStmtObject.registerOutParameter(5,Types.OTHER);
						cStmtObject.execute();
						rs = (java.sql.ResultSet)cStmtObject.getObject(4);
						rs1 = (java.sql.ResultSet)cStmtObject.getObject(5);
					if(rs != null)
					{
						if(rs.next())
						{
							cashlessDetailVO=new CashlessDetailVO();
							cashlessDetailVO.setMemberName(rs.getString("MEM_NAME"));
							cashlessDetailVO.setAge(rs.getLong("MEM_AGE"));
							cashlessDetailVO.setGender(rs.getString("GENDER"));
							cashlessDetailVO.setPayer(rs.getString("PAYER_NAME"));
							cashlessDetailVO.setEligibility(rs.getString("ELIGIBILITY"));
							cashlessDetailVO.setDeductable(rs.getString("DEDUCTIBLE"));
							cashlessDetailVO.setCoPay(rs.getString("CO_PARTICIPATION"));
							//cashlessDetailVO.setApplProcudure(rs.getString("APPLICABLE_PROCEDURE"));
							cashlessDetailVO.setExclusions(rs.getString("EXCLUSIONS"));
							cashlessDetailVO.setInsurredName(rs.getString("INSURED_NAME"));
							cashlessDetailVO.setPreApprLimit(rs.getString("PRE_APPRVL_LIMIT"));
							cashlessDetailVO.setEnrollId(rs.getString("TPA_ENROLLMENT_ID"));
							cashlessDetailVO.setMemDob(rs.getDate("MEM_DOB"));
							cashlessDetailVO.setMemberSeqID(rs.getLong("MEMBER_SEQ_ID"));
							/*String temp	=	rs.getString("ELIG_DENIAL_REASON");
							if(temp!=null || temp!="")
								temp*/
							cashlessDetailVO.setReasonForRejection(rs.getString("ELIG_DENIAL_REASON"));
							netArry[0]		=	rs.getString("PRIMARY_NETWORK");//PRIMARY NETWORK;//"GN";//rs.getString("DEDUCTIBLE");//PRIMARY NETWORK
							netArry[1]		=	rs.getString("PRODUCTTYPE");//"SN";//rs.getString("DEDUCTIBLE");//POLICY NETWORK
							cashlessDetailVO.setNetworksArray(netArry);				
							cashlessDetailVO.setEmirateID(rs.getString("EMIRATE_ID"));

							cashlessDetailVO.setPolicyNo(rs.getString("POLICY_NO"));
							cashlessDetailVO.setPolicyStDt(rs.getString("POLICY_ST_DT"));
							cashlessDetailVO.setPolicyEnDt(rs.getString("POLICY_EN_DT"));
						}//end of while(rs.next())
					}//end of if(rs != null)
					
					if(rs1 != null)
					{
						Map<String, String> provNetworksMap	=	new LinkedHashMap<String, String>();
						while(rs1.next())
						{
							provNetworksMap.put(rs1.getString("NETWORK_TYPE"), rs1.getString("NETWORK_YN"));
							/*netArry[2]		=	rs1.getString("CN_YN");//"N";//rs.getString("DEDUCTIBLE");//CN
							netArry[3]		=	rs1.getString("GN_YN");//"Y";//rs.getString("DEDUCTIBLE");//GN
							netArry[4]		=	rs1.getString("SN_YN");//"Y";//rs.getString("DEDUCTIBLE");//SN
							netArry[5]		=	rs1.getString("BN_YN");//"N";//rs.getString("DEDUCTIBLE");//BN
							netArry[6]		=	rs1.getString("WN_YN");//"N";//rs.getString("DEDUCTIBLE");//WN
							*/
						}//end of while(rs.next())
						cashlessDetailVO.setAssNetworksArray(provNetworksMap);
					}//end of if(rs != null)
					
						//lResult++;
				}//end of try
				catch (SQLException sqlExp)
				{
					throw new TTKException(sqlExp, "user");
				}//end of catch (SQLException sqlExp)
				
				catch (Exception exp)
				{
					throw new TTKException(exp, "user");
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
			    			log.error("Error while closing the Statement in OnlineAccessDAOImpl saveContact()",sqlExp);
			    			throw new TTKException(sqlExp, "user");
			    		}//end of catch (SQLException sqlExp)
			    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
			    		{
			    			try
			    			{
			    				if(conn != null) conn.close();
			    			}//end of try
			    			catch (SQLException sqlExp)
			    			{
			    				log.error("Error while closing the Connection in OnlineAccessDAOImpl saveContact()",sqlExp);
			    				throw new TTKException(sqlExp, "user");
			    			}//end of catch (SQLException sqlExp)
			    			finally{
			    				try{
			    					if(rs!=null) rs.close();
			    				}
			    				catch(SQLException sqlExp){
				    				log.error("Error while closing the ResultSet in OnlineAccessDAOImpl saveContact()",sqlExp);
			    				}try{
			    					if(rs1!=null) rs1.close();
			    				}
			    				catch(SQLException sqlExp){
				    				log.error("Error while closing the ResultSet1 in OnlineAccessDAOImpl saveContact()",sqlExp);
			    				}
			    			}
			    		}//end of finally Connection Close
			    	}//end of try
			    	catch (TTKException exp)
			    	{
			    		throw new TTKException(exp, "user");
			    	}//end of catch (TTKException exp)
			    	finally // Control will reach here in anycase set null to the objects 
			    	{
			    		cStmtObject = null;
			    		conn = null;
			    	}//end of finally
				}//end of finally
				return cashlessDetailVO;
			}//end of geMemberDetailsOnEnrollId()
			
			
			 public InputStream getPolicyTobFile(String seqId)throws TTKException{
					
					Connection conn = null;
					CallableStatement cStmtObject=null;
					ResultSet rs = null;
					Blob blob	=	null;
					InputStream iStream	=	new ByteArrayInputStream(new String("").getBytes());
					try{
						conn = ResourceManager.getConnection();
				                 
				         cStmtObject = conn.prepareCall(strGetPolicyTobFile);
				       
				        cStmtObject.setString(1, seqId);
						cStmtObject.registerOutParameter(2,Types.BLOB);
						cStmtObject.execute();
						
					//	rs = (java.sql.ResultSet)cStmtObject.getObject(2);

						
							//if(rs != null){
							//	if(rs.next()){
								 blob	=	(Blob) cStmtObject.getBlob(2);
								 iStream	=	blob.getBinaryStream();
								
								
								//}
							//	}//end of if(rs != null)
					    	//return (ArrayList<Object>)alMouList;
								 
				    }//end of try
				    catch (SQLException sqlExp) 
				    {
				        throw new TTKException(sqlExp, "onlineforms");
				    }//end of catch (SQLException sqlExp)
					
					catch (NullPointerException nullExp)
					{
						TTKException expTTK = new TTKException();
						expTTK.setMessage("error.policyTobFile.notFound");
						throw expTTK;
					}
					
					
				    catch (Exception exp) 
				    {
				        throw new TTKException(exp, "onlineforms");
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
				    			log.error("Error while closing the Statement in PolicyDAOImpl getProviderDocs()",sqlExp);
				    			throw new TTKException(sqlExp, "onlineforms");
				    		}//end of catch (SQLException sqlExp)
				    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				    		{
				    			try
				    			{
				    				if(conn != null) conn.close();
				    			}//end of try
				    			catch (SQLException sqlExp)
				    			{
				    				log.error("Error while closing the Connection in PolicyDAOImpl getProviderDocs()",sqlExp);
				    				throw new TTKException(sqlExp, "policy");
				    			}//end of catch (SQLException sqlExp)
				    		}//end of finally Connection Close
				    	}//end of try
				    	catch (TTKException exp)
				    	{
				    		throw new TTKException(exp, "policy");
				    	}//end of catch (TTKException exp)
				    	finally // Control will reach here in anycase set null to the objects 
				    	{
				    		cStmtObject = null;
				    		conn = null;
				    	}//end of finally
					}//end of finally
				    return iStream;
			 }
			 
			 
			 public ArrayList<Object> getBenefitDetails(ArrayList<Object> alSearchCriteria) throws TTKException {
					Connection conn = null;
					CallableStatement cStmtObject=null;
					ResultSet rs = null;
					ResultSet rs1=null;
					ResultSet rs2=null;
					ResultSet rs3=null;
					boolean recors=false;
					ArrayList<Object> alRes=new ArrayList<>();
					Collection<Object> alResultList = new ArrayList<Object>();
					try{
						conn = ResourceManager.getConnection();
						
						cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetbenefitDetails);
						if(alSearchCriteria.get(0)!= null)cStmtObject.setLong(1,new Long((String) alSearchCriteria.get(0)));
						else cStmtObject.setString(1, null);
						if (alSearchCriteria.get(1)!= null)cStmtObject.setLong(2,new Long((String) alSearchCriteria.get(1)));
						else cStmtObject.setString(2, null);
						if(alSearchCriteria.get(2)!= null)cStmtObject.setString(3, (String) alSearchCriteria.get(2));
						else cStmtObject.setString(3, null);
						if(alSearchCriteria.get(3)!= null) cStmtObject.setLong(4,new Long((String)alSearchCriteria.get(3)));
						else cStmtObject.setString(4, null);
						if(alSearchCriteria.get(4)!= null) cStmtObject.setString(5,(String)alSearchCriteria.get(4));
						else cStmtObject.setString(5, null);
						cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
						cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
						cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
						cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
						cStmtObject.execute();
						 rs = (java.sql.ResultSet)cStmtObject.getObject(6);
						 rs1 = (java.sql.ResultSet)cStmtObject.getObject(7);
						rs2 = (java.sql.ResultSet)cStmtObject.getObject(8);
						rs3 = (java.sql.ResultSet)cStmtObject.getObject(9);
						if(rs != null){
							while(rs.next()){
								recors=true;
								
								BenefitsDetailsVO benefitDetailVo=new BenefitsDetailsVO();
								benefitDetailVo.setBenefitName(TTKCommon.checkNull(rs.getString("BENEFIT_NAME")));
								benefitDetailVo.setSubBenefit(TTKCommon.checkNull(rs.getString("SUB_BENF_NAME")));
								benefitDetailVo.setStrConfigration(rs.getString("COVERAGE_PAY_VAL"));
								benefitDetailVo.setCoverage(rs.getString("COVERAGE"));
								benefitDetailVo.setLimit(rs.getString("LIMIT"));
								benefitDetailVo.setCopay(rs.getString("COPAY"));
								benefitDetailVo.setDeductible(rs.getString("DEDUCTABLE"));
								benefitDetailVo.setWaitingPeriod(rs.getString("MEM_WAITING"));
								benefitDetailVo.setSessionAllowed(rs.getString("SESSION_ALLOWED"));
								
								benefitDetailVo.setModeType(rs.getString("type_mode"));
								benefitDetailVo.setOtherRemarks(rs.getString("OTHER_REMARKS"));
								benefitDetailVo.setLimitUtilised(rs.getString("utilized_limit"));
								benefitDetailVo.setLimitAvailable(rs.getString("balance"));
								alResultList.add(benefitDetailVo);
							}//end of while(rs.next())
						}//end of if(rs != null)
						//if(!recors)	alResultList.add(new BenefitDetailVo());
						
						alRes.add(alResultList);
						recors=false;
						String sumInsured=null;
						if(rs2 != null){
							while(rs2.next()){
								recors=true;
								sumInsured = rs2.getString("sum_insured"); 
								alRes.add(rs2.getString("available_sum_insured"));
							}
						}
						
						if(rs1 != null){
							HashMap<String, String> map = new HashMap<String,String>();
							while(rs1.next()){
								map.put("enrollmentId", rs1.getString("tpa_enrollment_id"));
								map.put("policyIssueDate", rs1.getString("policy_issue_date"));
								map.put("policyNumber", rs1.getString("policy_number"));
								map.put("effectiveFromDate", rs1.getString("effective_from_date"));
								map.put("productCatTypeID", rs1.getString("PRODUCT_CAT_TYPE_ID"));
								map.put("effectiveToDate", rs1.getString("effective_to_date"));
								map.put("sumInsured", sumInsured);
							}
						alRes.add(map);	
						}
						if(!recors) alRes.add(null);
						recors=false;
						if(rs3!=null){
							while(rs3.next()){
								recors=true;
								alRes.add(rs3.getString("OVERALL_REMARKS"));
							}
							
						}
						 if(!recors) alRes.add(null);
						return alRes;
						
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
								if (rs1 != null) rs1.close();
								if (rs2 != null) rs2.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Resultset in OnlinePreAuthDAOImpl getBenefitDetails",sqlExp);
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
									log.error("Error while closing the Statement in OnlinePreAuthDAOImpl getBenefitDetails",sqlExp);
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
										log.error("Error while closing the Connection in OnlinePreAuthDAOImpl getBenefitDetails",sqlExp);
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
							rs1 =null;
							rs2 =null;
							cStmtObject = null;
							conn = null;
						}//end of finally
					}//end of finally
				}//end of getBenefitDetails(ArrayList alSearchCriteria)





			public long getCheckEligibilityLog(String enrollId,String benifitType, Long hospSeqId, Long memSeqId,Long user_SEQ_ID)throws TTKException  {


				Connection conn = null;
				CallableStatement cStmtObject=null;
				ResultSet rs	=	null;
				long iResult=0;
				long logSEqId=0;
				
				try{
					conn = ResourceManager.getConnection();
					cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetCheckEligibilityLog);
					cStmtObject.setLong(1,logSEqId);
					cStmtObject.setLong(2,hospSeqId);
					cStmtObject.setLong(3,memSeqId);
					cStmtObject.setString(4,benifitType);
					cStmtObject.setLong(5,user_SEQ_ID);
					cStmtObject.registerOutParameter(1,Types.BIGINT);//ROW_PROCESSED
					
		    		cStmtObject.execute();
		    		conn.commit();
		    		iResult = (Long)cStmtObject.getLong(1);
				
				
				}
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
							log.error("Error while closing the Resultset in OnlinePreAuthDAOImpl getCheckEligibilityLog",sqlExp);
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
								log.error("Error while closing the Statement in OnlinePreAuthDAOImpl getCheckEligibilityLog",sqlExp);
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
									log.error("Error while closing the Connection in OnlinePreAuthDAOImpl getCheckEligibilityLog",sqlExp);
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
				
				return iResult;
			}
			
			private static final String strTemplateName= "{CALL product_admin_pkg_select_gc_card_template_name(?,?)}";
  			public String getTemplateName(String strPolicyGroupSeqId) throws TTKException {
			String templateName="";
			Connection conn = null;
			CallableStatement cStmtObject=null;
			ResultSet rs = null;
			try {
				log.info(" -- INSIDE getTemplateName() start --- ");
				System.out.println("PolicyGroupSeqId ->"+strPolicyGroupSeqId);
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTemplateName);
				if(!"".equals(strPolicyGroupSeqId))
					cStmtObject.setLong(1,Long.parseLong(strPolicyGroupSeqId));
				else
					cStmtObject.setLong(1,0);
				cStmtObject.registerOutParameter(2,Types.OTHER);
	            cStmtObject.execute();
	            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
	            if(rs!=null)
	            {
	                while(rs.next())
	                {
	                	templateName =TTKCommon.checkNull(rs.getString("template_name"));
	                }
	            }
	            log.info("Dao:templateName form database is ->"+templateName);
	            return templateName;
				
			}//end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "onlineforms");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "onlineforms");
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
						log.error("Error while closing the Resultset in CallCenterDAOImpl getTemplateName()",sqlExp);
						throw new TTKException(sqlExp, "callcenter");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null) cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in CallCenterDAOImpl getTemplateName()",sqlExp);
							throw new TTKException(sqlExp, "callcenter");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in CallCenterDAOImpl getTemplateName()",sqlExp);
								throw new TTKException(sqlExp, "callcenter");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "callcenter");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
	}//end of getTemplateName(String strPolicyGroupSeqId) throws TTKException		

}//end of getEligibilityStatus
