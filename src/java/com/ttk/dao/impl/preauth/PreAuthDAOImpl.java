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

package com.ttk.dao.impl.preauth;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ibm.icu.text.DateFormat;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.claims.BenefitDetailVo;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.AdditionalDetailVO;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.BalanceCopayDeductionVO;//added as per KOC 1142 and 1140 Change Request and 1165
import com.ttk.dto.preauth.BalanceSIInfoVO;
import com.ttk.dto.preauth.ClaimantNewVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.ClinicianDetailsVO;
import com.ttk.dto.preauth.DentalOrthoVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.DiscrepancyVO;
import com.ttk.dto.preauth.EnhancementVO;
import com.ttk.dto.preauth.InsOverrideConfVO;
import com.ttk.dto.preauth.MemberBufferVO;
import com.ttk.dto.preauth.ObservationDetailsVO;
import com.ttk.dto.preauth.PreAuthAssignmentVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.ProviderDetailsVO;
import com.ttk.dto.preauth.RestorationPreauthClaimVO;
import com.ttk.dto.preauth.SIBreakupInfoVO;
import com.ttk.dto.preauth.SIInfoVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.preauth.StopPreauthClaimVO;

public class PreAuthDAOImpl implements BaseDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PreAuthDAOImpl.class);

	private static final String strClaimInsIntimate = "{CALL CLAIMS_PKG.CLM_INS_INTIMATE(?,?,?,?,?)}";// 1274A
	private static final String strPatInsIntimate = "{CALL PRE_AUTH_PKG.PAT_INS_INTIMATE(?,?,?,?,?)}";// 1274A
	private static final String strPatClmFileUpload = "{CALL CLAIMS_APPROVAL_PKG.PAT_CLM_DOC_UPLOAD(?,?,?,?,?,?)}";// 1274A
	private static final String strUnFreezePatClm = "{CALL CLAIMS_APPROVAL_PKG.PAT_CLM_OVERRIDE(?,?,?,?)}";// 1274A
	private static final String strOverrideConf = "{CALL CLAIMS_APPROVAL_PKG.OVERRIDE_INS_INTIMATION(?,?,?,?,?,?,?)}";// bajaj
	private static final String strPartnerPreAuthList = "{CALL PARTNER_PREAUTH_ONLINE_PTR_PREAUTH_SUB_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPartnerPreAuthorization = "{CALL PARTNER_PREAUTH_ONLINE_PTR_PREAUTH_SUB_DTL(?,?)}";
	private static final String strSavePreAuthDentalDetails = "{CALL AUTHORIZATION_PKG.SAVE_ORTHODONTIC_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	//get_benifit_details
	private static final String strGetCustomerForBenifits	 	= "{CALL authorization_pkg.get_benifit_details(?,?,?)}";
	// private static final String strPreAuthList =
	// "{CALL PRE_AUTH_SQL_PKG.SELECT_PRE_AUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	// private static final String strPreAuthorization =
	// "{CALL PRE_AUTH_SQL_PKG.SELECT_PRE_AUTH(?,?,?,?,?,?)}";
	private static final String strPreAuthList = "{CALL PRE_AUTH_PKG_SELECT_PRE_AUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";// KOC
																																						// FOR
																																						// Grievance
																																						// cigna
																																						// 2
																																						// parameter
	private static final String strNetWorkTypes = "{CALL PRE_AUTH_PKG_SELECT_PRE_AUTH_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";// KOC
																																					// FOR
																																					// Grievance
																																					// cigna
																																					// 2
																																					// parameter
	private static final String strPreAuthEnhancementList = "{CALL AUTHORIZATION_PKG_SELECT_ENHANCE_PAT_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strActivityCodeList = "{CALL AUTHORIZATION_PKG_SELECT_ACTIVITY_LIST(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDiagnosisCodeList = "{CALL AUTHORIZATION_PKG_SELECT_ICD_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strProviderList = "{CALL AUTHORIZATION_PKG_SELECT_PROVIDER_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strClinicianList = "{CALL AUTHORIZATION_PKG_SELECT_CLINICIAN_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strProviderClinicianList = "{CALL HOSPITAL_PKG_SELECT_CLINICIAN_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strActivityCodeTariff = "{CALL AUTHORIZATION_PKG_SELECT_ACTIVITY_CODE(?,?,?,?,?,?,?,?,?)}";
	// private static final String strPreAuthorization =
	// "{CALL SELECT_PAT_AUTH(?,?)}";
	private static final String strPreAuthorization = "{CALL AUTHORIZATION_PKG_SELECT_PAT_AUTH_DETAILS(?,?,?,?,?)}";
	private static final String strPreAuthorizationHistoryList = "{CALL AUTHORIZATION_PKG_SELECT_PAT_CLM_HISTORY(?,?,?,?)}";
	private static final String strGetDiagnosisDetails = "{CALL SELECT_DIAGNOSYS_DETAILS(?,?)}";
	private static final String strGetIcdDetails = "{CALL AUTHORIZATION_PKG_SELECT_ICD(?,?,?,?)}";
	private static final String strGetDiagnosis = "{CALL AUTHORIZATION_PKG_SELECT_DIAGNOSYS_DETAILs(?,?,?,?)}";
	private static final String strSaveObservations = "{CALL AUTHORIZATION_PKG_SAVE_OBSERVATION_DETAILS(?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strShortfallDetails = "{CALL AUTHORIZATION_PKG_SELECT_SHORTFALL_DETAILS(?,?,?,?,?)}";
	private static final String strGetAllObservations = "{CALL AUTHORIZATION_PKG_SELECT_OBSERVATION_DETAILS(?,?)}";
	private static final String strGetObservation = "{CALL AUTHORIZATION_PKG_SELECT_OBSERVATION_DETAIL(?,?)}";
	private static final String strDeleteObservations = "{CALL AUTHORIZATION_PKG_DELETE_OBSERVATION_DETAILS(?,?,?,?)}";
	private static final String strSavePreAuthorization = "{CALL PRE_AUTH_PKG.SAVE_PREAUTH(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";// added
																																																						// no
	private static final String strAddPreAuthorizationDetails = "{CALL AUTHORIZATION_PKG_SAVE_PAT_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";// added
																																																			// no
	private static final String strAddDiagnosisDetails = "{CALL AUTHORIZATION_PKG_SAVE_DIAGNOSYS_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteDiagnosisDetails = "{CALL AUTHORIZATION_PKG_DELETE_DIAGNOSYS_DETAILS(?,?,?,?)}";
	private static final String strDeleteShortfallsDetails = "{CALL AUTHORIZATION_PKG_DELETE_SHORTFALLS(?,?,?,?)}";
	private static final String strDeleteActivityDetails = "{CALL AUTHORIZATION_PKG_DELETE_ACTIVITY_DETAILS(?,?,?,?)}";
	private static final String strSaveActivityDetails = "{CALL AUTHORIZATION_PKG_SAVE_ACTIVITY_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetActivityDetails = "{CALL AUTHORIZATION_PKG_SELECT_ACTIVITY_DETAILS(?,?)}";
	private static final String strGetCalculatePreauthAmount = "{CALL AUTHORIZATION_PKG_CALCULATE_AUTHORIZATION(?,?,?,?,?)}";
	private static final String strPreAuthAmountApproved = "{CALL AUTHORIZATION_PKG_SAVE_AUTHORIZATION(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strEncounterTypes = "{CALL AUTHORIZATION_PKG_SELECT_ENCOUNTER_TYPES(?,?)}";
	private static final String strOverridePreauth = "{CALL PRE_AUTH_PKG.OVERRIDE_PREAUTH(?,?,?,?,?,?,?,?)}";
	private static final String strSaveReview = "{CALL PRE_AUTH_PKG.SET_REVIEW(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveAssignUsers = "{CALL PRE_AUTH_PKG.SAVE_ASSIGN_USERS(?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveShortfall = "{CALL AUTHORIZATION_PKG_SAVE_SHORTFALL_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	// private static final String strGetAssignTo =
	// "{CALL PRE_AUTH_SQL_PKG.SELECT_ASSIGN_TO(?,?,?,?)}";
	private static final String strGetAssignTo = "{CALL PRE_AUTH_PKG.SELECT_ASSIGN_TO(?,?,?,?)}";
	private static final String strPAAssignUserList = "{CALL PRE_AUTH_PKG.MANUAL_ASSIGN_PREAUTH(?,?,?,?)}";
	private static final String strClaimAssignUserList = "{CALL CLAIMS_PKG.MANUAL_ASSIGN_PREAUTH(?,?,?,?)}";
	// private static final String strEnrollmentList =
	// "{CALL PRE_AUTH_SQL_PKG.SELECT_ENROLLMENT_ID_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	// private static final String strEnhancementList =
	// "{CALL PRE_AUTH_SQL_PKG.SELECT_ENHANCEMENT_LIST(?,?,?,?,?)}";
	// private static final String strAuthorizationDetails =
	// "{CALL PRE_AUTH_SQL_PKG.SELECT_AUTHORIZATION(?,?,?,?,?)}";
	// private static final String strSettlementDetails =
	// "{CALL CLAIMS_SQL_PKG.SELECT_SETTLEMENT(?,?,?)}";
	private static final String strEnrollmentList = "{CALL PRE_AUTH_PKG_SELECT_ENROLLMENT_ID_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectMemberList = "{CALL PRE_AUTH_PKG_SELECT_MEMBER_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectEnrollmentID = "{CALL PRE_AUTH_PKG_SELECT_ENROLLMENT_ID(?,?)}";

	private static final String strEnhancementList = "{CALL PRE_AUTH_PKG.SELECT_ENHANCEMENT_LIST(?,?,?,?,?)}";
	private static final String strAuthorizationDetails = "{CALL PRE_AUTH_PKG.SELECT_AUTHORIZATION(?,?,?,?,?)}";
	private static final String strSettlementDetails = "{CALL CLAIMS_PKG.SELECT_SETTLEMENT(?,?,?)}";
	// Modification as per KOC 1216b Change request
	// private static final String strSaveAuthorization =
	// "{CALL PRE_AUTH_PKG.SAVE_AUTHORIZATION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	// added 1 Parameter for High Deductable CR
	private static final String strSaveAuthorization = "{CALL PRE_AUTH_PKG.SAVE_AUTHORIZATION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";// added
																																			// 3
	private static final String strGetShortfallFile = "SELECT S.UPLOADED_FILE FROM SHORTFALL_DETAILS S WHERE S.DOCS_STATUS = 'Y' AND SHORTFALL_SEQ_ID=?";
																																			// parameters
																																			// as
	private static final String strPreAuthorizationHistoryList1 = "{CALL authorization_pkg.select_mem_pat_clm_history(?,?)}";//bikki
																																// per
																																			// 1140
																																			// and
																																			// 1142
																																			// and
																																			// 1065
	// added 1 Parameter for Policy Deductable - KOC-1277
	private static final String strSaveSettlement = "{CALL CLAIMS_PKG.SAVE_SETTLEMENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";// added
																																			// 3
																																			// parameters
																																			// as
																																			// per
																																			// 1140
																																			// and
																																			// 1142
																																			// and
																																			// 1065
	private static final String strDeletePATGeneral = "{CALL PRE_AUTH_PKG.DELETE_PAT_GENERAL(?,?,?,?,?,?)}";
	// private static final String strGetDiscrepancy =
	// "{CALL PRE_AUTH_SQL_PKG.SELECT_DISCREPANCY_INFO(?,?,?,?)}";
	private static final String strGetDiscrepancy = "{CALL PRE_AUTH_PKG.SELECT_DISCREPANCY_INFO(?,?,?,?)}";
	private static final String strResolveDiscrepancy = "{CALL PRE_AUTH_PKG.SAVE_DISCREPANCY(";
	private static final String strReturnToCoding = "{CALL CODING_PKG.RETURN_TO_CODING(?,?,?,?,?,?,?,?,?)}";
	private static final String strReAssignEnrID = "{CALL PRE_AUTH_PKG.RE_ASSIGN_ENRL_ID(?,?,?,?,?,?)}";
	// Modified as per KOC 1216 B change Request
	// private static final String strBalanceSIInfo=
	// "{CALL PRE_AUTH_PKG.SELECT_CLAIMANT_BALANCE(?,?,?,?,?,?)}";
	private static final String strBalanceSIInfo = "{CALL PRE_AUTH_PKG.SELECT_CLAIMANT_BALANCE(?,?,?,?,?,?,?,?)}";// changes
																													// for
																													// koc1289_1272
	// Modified as per KOC 1216 B change Request
	private static final String strPreAuthCopayAdvice = "{CALL PRE_AUTH_PKG.COPAY_ADVICED(?,?,?,?,?,?)}";// Changes
																											// As
																											// Per
																											// KOC
																											// 1140
																											// &&
																											// 1142
	private static final String strClaimsCopayAdvice = "{CALL CLAIMS_PKG.COPAY_ADVICED(?,?,?,?,?,?)}"; // Changes
																										// As
																										// Per
																										// KOC
																										// 1140
																										// &&
																										// 1142
	private static final String strGetInsIntimation = "{CALL CLAIMS_APPROVAL_PKG.SELECT_INS_INTIMATION(?,?,?)}"; // bajaj
																													// enhances

	private static final String strPreAuthHistoryList = "{CALL AUTHORIZATION_PKG_CREATE_PREAUTH_XML(?,?)}";
	private static final String strClaimHistoryList = "{CALL CLAIM_PKG_CREATE_CLAIM_XML(?,?)}";
	private static final String strProviderClinicianDetail = "SELECT P.CONTACT_SEQ_ID,P.PROFESSIONAL_ID,P.CONTACT_NAME,GC.GENERAL_TYPE_ID AS CONSULTAITON,CSM.SPECIALTY_ID AS SPECIALITY FROM TPA_HOSP_PROFESSIONALS"
			+ " P LEFT OUTER JOIN TPA_HOSP_INFO I     ON (I.HOSP_SEQ_ID = P.HOSP_SEQ_ID)   LEFT OUTER JOIN TPA_GENERAL_CODE GC ON"
			+ "(P.CONSULT_GEN_TYPE = GC.GENERAL_TYPE_ID)  LEFT OUTER JOIN APP.DHA_CLNSN_SPECIALTIES_MASTER CSM   ON (CSM.SPECIALTY_ID ="
			+ "P.SPECIALITY_ID) WHERE I.HOSP_SEQ_ID = ? AND P.PROFESSIONAL_ID = ? ";
	private static final String strGetProviderDocs = "SELECT I.MOU_DOC_SEQ_ID,GC.DESCRIPTION,to_char(I.added_date,'MM/DD/YYYY HH24:MI:SS' ) as ADDED_DATE, UC.CONTACT_NAME,I.DOCS_PATH,I.HOSP_SEQ_ID,I.FILE_NAME FROM APP.MOU_DOCS_INFO1 I LEFT JOIN APP.TPA_GENERAL_CODE GC ON (I.DOCS_GENTYPE_ID = GC.GENERAL_TYPE_ID) LEFT JOIN APP.TPA_USER_CONTACTS UC ON(UC.CONTACT_SEQ_ID=I.ADDED_BY) WHERE I.HOSP_SEQ_ID=? ORDER BY I.MOU_DOC_SEQ_ID";
	private static final String strGetXmlDataToCEED 	= "{CALL AUTHORIZATION_PKG_CLAIM_XML_CEED(?,?)}";
	private static final String strPATGetXmlDataToCEED 	= "{CALL AUTHORIZATION_PKG.PREAUTH_XML_CEED(?,?)}";
	private static final  String strOverridreAuthDetails = "{CALL AUTHORIZATION_PKG_OVERRIDE_PREAUTH(?,?,?,?)}";
    private static final String strGetdiagDetails = "select d.denial_code,d.denial_description from app.Tpa_Denial_Codes d order by d.denial_code";
	private static final String strGetProviders = "SELECT I.HOSP_SEQ_ID AS PROVIDER_SEQ_ID, I.HOSP_LICENC_NUMB AS PROVIDER_ID,I.HOSP_NAME AS PROVIDER_NAME FROM APP.TPA_HOSP_INFO I JOIN APP.TPA_HOSP_EMPANEL_STATUS S ON (I.HOSP_SEQ_ID=S.HOSP_SEQ_ID) WHERE S.EMPANEL_STATUS_TYPE_ID='EMP' ORDER BY I.HOSP_LICENC_NUMB";

	private static final String strGetDhpoPreauthUpload = "{CALL PAT_XML_LOAD_PKG_SELECT_UPLOAD_PAT_DTLS(?,?)}";
	private static final String strSaveDhpoPreauthlogDetails = "{CALL PAT_XML_LOAD_PKG_SAVE_UPLOAD_PAT_LOGS(?,?,?,?,?,?)}";
	private static final String strGetDhpoPreauthLogDetails = "{CALL PAT_XML_LOAD_PKG_SELECT_UPLOAD_PAT_LOGS(?,?)}";
	private static final String strCountOralPreAuth = "SELECT COUNT(1) AS ROWCOUNT FROM APP.PAT_AUTHORIZATION_DETAILS PAD WHERE PAD.ORAL_SYSTEM_STATUS=? AND PAD.SOURCE_TYPE_ID=?";
	private static final String strGetbenefitDetails = "{CALL claim_pkg_select_befefit_limit1(?,?,?,?,?,?,?,?)}";         //"{CALL CLAIM_PKG_SELECT_BEFEFIT_LIMIT(?,?,?,?,?,?,?,?)}";
	//private static final String strGetAvailableSumINsured ="select eb.sum_insured - eb.utilised_sum_insured as avi_balance from app.tpa_enr_policy p left join app.tpa_enr_policy_group pg on (p.policy_seq_id = pg.policy_seq_id) left join app.tpa_enr_policy_member pm on (pm.policy_group_seq_id = pg.policy_group_seq_id) left join app.tpa_enr_balance eb on (eb.policy_group_seq_id = pg.policy_group_seq_id) where pm.member_seq_id=37634720 and (pm.mem_general_type_id != 'PFL' AND pm.member_seq_id = eb.member_seq_id OR eb.member_seq_id IS NULL OR pm.member_seq_id IS NULL)"; 
	private static final String strallowedoverideYN="select case when trunc(pad.date_of_hospitalization) not BETWEEN trunc(tepm.date_of_inception) and trunc(tepm.date_of_exit) and tepm.status_general_type_id in ('POC') then  'N' ELSE 'Y' end  as ALLOW_ORVERIDE_YN "+
	"from clm_authorization_details pad left outer join  tpa_enr_policy_member tepm ON (pad.member_seq_id=tepm.member_seq_id) left outer join  tpa_enr_policy_group pg on (pg.policy_group_seq_id=tepm.policy_group_seq_id)"+
			"left outer join  tpa_enr_balance eb ON (pg.policy_group_seq_id = eb.policy_group_seq_id) where pad.claim_seq_id = ? and  (tepm.mem_general_type_id != 'PFL' AND tepm.member_seq_id = eb.member_seq_id OR eb.member_seq_id IS NULL OR tepm.member_seq_id IS NULL)";
	
	private static final String strMemberList="{CALL AUTHORIZATION_PKG_SELECT_MEMBER_LIST(?,?,?,?,?,?,?)}";
	
	private static final String strGetMemberDetails = "{CALL AUTHORIZATION_PKG.SELECT_MEMBER(?,?,?)}";
	private static final String strgetPartnersList ="SELECT P.PTNR_SEQ_ID,P.PARTNER_NAME FROM APP.TPA_PARTNER_INFO P JOIN APP.TPA_PARTNER_EMPANEL_STATUS E ON (P.PTNR_SEQ_ID=E.PTNR_SEQ_ID) WHERE E.EMPANEL_STATUS_TYPE_ID = 'EMP'";

	
	private static final String strProviderMemberList ="{CALL AUTHORIZATION_PKG_SELECT_PROVIDER_MEMBER_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strActivityViewDoc="{CALL AUTHORIZATION_PKG_SELECT_PAT_AUTH_DOCS(?,?)}";
	private static final String strMemberRuleDate="{CALL AUTHORIZATION_PKG_GET_BENIFIT_DETAILS(?,?,?)}";
	private static final String strPreAuthorizationMemberHistoryList = "{CALL AUTHORIZATION_PKG_SELECT_MEM_PAT_CLM_HISTORY(?,?)}";
    private static final String strGetPolicyDocs="SELECT PPI.PROP_DOC_SEQ_ID, CASE  PPI.FILE_TYPE WHEN 'CEN' THEN 'Census List' WHEN 'TOB' THEN 'TOB' WHEN 'POS' THEN 'UND to POS' WHEN 'TL' THEN 'Trade License' WHEN 'EC' THEN 'Establishment Card'WHEN 'CP' THEN 'Client Photos' WHEN 'RP' THEN 'Rename Photos' WHEN 'MAF' THEN 'MAF for Above 65yrs' WHEN 'CEMAIL' THEN 'Confirmation E-mail' WHEN 'OTH' THEN 'Others' END AS FILE_TYPE,PPI.FILE_DESC, PPI.FILE_NAME,PPI.FILE_DOC, to_char(PPI.ADDED_DATE, 'DD/MM/YYYY HH:MI:SS AM') ADDED_DATE, to_char(PPI.deleted_date, 'DD/MM/YYYY HH:MI:SS AM') DELETED_DATE, PPI.DELETED_REMARKS"+
             " FROM APP.POLICY_PROP_DOC_INFO PPI JOIN APP.TPA_USER_CONTACTS CON ON(PPI.ADDED_BY = CON.CONTACT_SEQ_ID)"+
             " WHERE POLICY_SEQ_ID = ?  AND PPI.FILE_TYPE IN ('CEMAIL','TOB','MAF','OTH') ORDER BY PPI.ADDED_DATE"; 
	private static final String strdeleteAuthorizationDetails = "{CALL CLAIM_PKG_DELINK_PRE_AUTH(?,?)}";
	
    private static final String StrCeedResponseDetails = "{CALL AUTHORIZATION_PKG_check_ceed_alert(?,?,?,?)}";
    private static final String str_DenialDescriptionsList="SELECT DENIAL_CODE,DENIAL_DESCRIPTION FROM APP.TPA_DENIAL_CODES WHERE AUTHORITY IS NULL OR AUTHORITY=? ORDER BY DENIAL_CODE";
    
   
    private static final String strGetPreauthDocs="SELECT MDI.MOU_DOC_SEQ_ID,MDI.HOSP_SEQ_ID,MDI.MODE_FLAG,CASE MDI.DOCS_GENTYPE_ID WHEN 'PRR' THEN 'Preauth Requests' WHEN 'ISR' THEN 'Investigation Reports' WHEN 'FBDS' THEN 'Final Bill and Discharge Summary' WHEN 'POTH' THEN 'Others' ELSE MDI.DOCS_GENTYPE_ID  END AS FILE_DESC, MDI.docs_path file_name,MDI.file_name file_name_val, to_char(MDI.ADDED_DATE, 'DD/MM/YYYY HH:MI:SS AM') ADDED_DATE ,CON.CONTACT_NAME, to_char(MDI.DELETED_DATE, 'DD/MM/YYYY HH:MI:SS AM') DELETED_DATE,MDI.DELETION_REMARKS,CON1.CONTACT_NAME DELETED_USER FROM APP.MOU_DOCS_INFO1 MDI JOIN APP.TPA_USER_CONTACTS CON ON(MDI.ADDED_BY = CON.CONTACT_SEQ_ID) LEFT JOIN APP.TPA_USER_CONTACTS CON1 ON (MDI.DELETED_BY = CON1.CONTACT_SEQ_ID) WHERE MDI.HOSP_SEQ_ID = ? ORDER BY MDI.ADDED_DATE";
    private static final String strUploadPreauthDocs="{CALL HOSPITAL_EMPANEL_PKG_SAVE_MOU_DOCS_INFO1(?,?,?,?,?,?,?,?)}";
    private static final String strDelPreauthUploadfile="{CALL HOSPITAL_EMPANEL_PKG_DEL_MOU_DOCS_INFO(?,?,?,?)}";
    
    private static final String strGetProviderAllObservations = "{CALL hospital_empanel_pkg.SELECT_OBSERVATION_DETAILS(?,?)}";
    private static final String strGetProviderObserv = "{CALL hospital_empanel_pkg.SELECT_OBSERVATION_DETAIL(?,?)}";
    private static final String strSaveProvoderObservations = "{CALL HOSP_DIAGNOSYS_PKG.SAVE_OBSERVATION_DETAILS(?,?,?,?,?,?,?,?,?,?)}";
    private static final String strDelProviderObservations = "{CALL HOSP_DIAGNOSYS_PKG.DELETE_OBSERVATION_DETAILS(?,?,?)}";
    private static final String strSaveProviderObservations = "{CALL hospital_empanel_pkg.SAVE_OBSERVATION_DETAILS(?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strGetAllProviderObservations = "{CALL HOSP_DIAGNOSYS_PKG.SELECT_OBSERVATION_DETAILS(?,?)}";
	private static final String strGetProviderObservation = "{CALL AUTHORIZATION_PKG.SELECT_OBSERVATION_DETAIL(?,?)}";
	private static final String strGetPreauthSeqId = "select pat_auth_seq_id from pat_authorization_details where auth_number=?";
	private static final String strGetEpreauthDocs = "{CALL hosp_diagnosys_pkg_pat_observ_file(?,?,?,?)}";
    
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
	private static final int PAT_GENERAL_TYPE_ID = 28;
	private static final int PAT_PRIORITY_GENERAL_TYPE_ID = 29;
	private static final int PAT_RECEIVED_DATE = 30;
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
	/*
	 * private static final int SELECTION_TYPE = 58; private static final int
	 * USER_SEQ_ID = 59; private static final int ROW_PROCESSED = 60;
	 */
	private static final int EMAIL_ID = 58;
	private static final int INS_SCHEME = 59;
	private static final int CERTIFICATE_NO	= 60;
	private static final int INS_CUSTOMER_CODE = 61;
	private static final int SELECTION_TYPE = 62;
	private static final int INSUR_REF_NUMBER = 63;
	private static final int USER_SEQ_ID = 64;
	private static final int ROW_PROCESSED = 65;

	/**
	 * This method returns the Arraylist of PreAuthVO's which contains
	 * Pre-Authorization details
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Pre-Authorization
	 *         details
	 * @exception throws TTKException
	 */
	public ArrayList getPreAuthList(ArrayList alSearchCriteria)
			throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		PreAuthVO preAuthVO = null;
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		try {
			
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
			cStmtObject.setString(23,(String)alSearchCriteria.get(28));			
			cStmtObject.setString(24,(String)alSearchCriteria.get(29));
			cStmtObject.setString(25,(String)alSearchCriteria.get(30));
			cStmtObject.setString(26,(String)alSearchCriteria.get(31));	
			cStmtObject.setLong(27,(Long)alSearchCriteria.get(16));	
			cStmtObject.setString(28,(String)alSearchCriteria.get(24));	
			cStmtObject.setString(29,(String)alSearchCriteria.get(25));
			cStmtObject.setString(30,(String)alSearchCriteria.get(26));
			cStmtObject.setString(31,(String)alSearchCriteria.get(27));
			cStmtObject.registerOutParameter(32,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(32);
			if (rs != null) {
				while (rs.next()) {
					preAuthVO = new PreAuthVO();
					preAuthVO.setPreAuthSeqID(rs.getLong("PAT_AUTH_SEQ_ID"));
					preAuthVO.setPreAuthNo(rs.getString("PRE_AUTH_NUMBER"));		
					preAuthVO.setReceivedDateAsString(rs
							.getString("PAT_RECEIVED_DATE_TIME"));
					preAuthVO.setClaimAdmnDate(rs.getDate("HOSPITALIZATION_DATE"));
					preAuthVO.setClaimDisrgDate(rs.getDate("DISCHARGE_DATE"));
					String priority = rs.getString("PRIORITY_GENERAL_TYPE_ID");
					if ("HIG".equals(priority)) {
						preAuthVO.setPriorityImageName("HighPriorityIcon");
						preAuthVO.setPriorityImageTitle("High Priority");
					} else if ("LOW".equals(priority)) {
						preAuthVO.setPriorityImageName("LowPriorityIcon");
						preAuthVO.setPriorityImageTitle("Low Priority");
					} else {
						preAuthVO.setPriorityImageName("MediumPriorityIcon");
						preAuthVO.setPriorityImageTitle("Medium Priority");
					}
					preAuthVO.setHospitalName(rs.getString("HOSP_NAME"));

					preAuthVO
							.setEnrollmentID(rs.getString("TPA_ENROLLMENT_ID"));

					preAuthVO.setStrAlternateID(TTKCommon.checkNull(rs
							.getString("TPA_ALTERNATE_ID"))); // database column
																// name

					preAuthVO.setClaimantName(rs.getString("MEM_NAME"));
					preAuthVO.setAssignedTo(rs.getString("CONTACT_NAME"));
					preAuthVO.setStatusTypeID(rs
							.getString("PAT_STATUS_TYPE_ID"));
					preAuthVO.setOralORsystemStatus(TTKCommon.checkNull(rs
							.getString("OralORsystemStatus")));
				
					preAuthVO.setModeOfPreauth(rs.getString("MODE_P"));
					
					preAuthVO.setsEmiratesID(TTKCommon.checkNull(rs.getString("emirate_id"))); 
					
					preAuthVO.setProcessType(rs.getString("submission_catogory")); 											
					preAuthVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					alResultList.add(preAuthVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getPreAuthList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getPreAuthList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getPreAuthList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getPreAuthList(ArrayList alSearchCriteria)

	 /**
	 * This method returns the HashMap of String,String which contains Network
	 * Types details
	 * 
     * @return HashMap of String object which contains Network Types details
     * @exception throws TTKException
     */
	public HashMap<String, String> getNetworkTypeList() throws TTKException {

		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		HashMap<String, String> networkTypes = new HashMap<>();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strNetWorkTypes);
			cStmtObject.registerOutParameter(1, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(1);
			if (rs != null) {
				while (rs.next()) {
					networkTypes.put(rs.getString(""), rs.getString(""));
				}// end of while(rs.next())
			}// end of if(rs != null)
			return networkTypes;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getNetworkTypeList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getNetworkTypeList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getNetworkTypeList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getNetworkTypeList()

	/**
	 * This method returns the Arraylist of PreAuthVO's which contains
	 * Pre-Authorization details
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Pre-Authorization
	 *         details
	 * @exception throws TTKException
	 */
	public ArrayList getPreAuthEnhancementList(ArrayList alSearchCriteria)
			throws TTKException {

		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		PreAuthVO preAuthVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strPreAuthEnhancementList);
			cStmtObject.setString(1, (String) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(1));
			cStmtObject.setString(3, (String) alSearchCriteria.get(2));
			cStmtObject.setString(4, (String) alSearchCriteria.get(3));
			cStmtObject.setString(5, (String) alSearchCriteria.get(4));
			cStmtObject.setString(6, (String) alSearchCriteria.get(5));
			cStmtObject.setString(7, (String) alSearchCriteria.get(7));
			cStmtObject.setString(8, (String) alSearchCriteria.get(8));
			cStmtObject.setString(9, (String) alSearchCriteria.get(9));
			cStmtObject.setString(10, (String) alSearchCriteria.get(10));
			cStmtObject.setLong(11, (Long) alSearchCriteria.get(6));
			cStmtObject.registerOutParameter(12, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(12);
			conn.commit();
			if (rs != null) {
				while (rs.next()) {
					preAuthVO = new PreAuthVO();
					preAuthVO.setPreAuthSeqID(rs.getLong("PAT_AUTH_SEQ_ID"));
					preAuthVO.setPreAuthNo(rs.getString("PRE_AUTH_NUMBER"));
					// preAuthVO.setReceivedDate(rs.getDate("PAT_RECEIVED_DATE"));
					preAuthVO.setReceivedDateAsString(rs
							.getString("PAT_RECEIVED_DATE"));

					preAuthVO.setClaimAdmnDate(rs
							.getDate("HOSPITALIZATION_DATE"));
					preAuthVO.setClaimDisrgDate(rs.getDate("DISCHARGE_DATE"));

					String priority = rs.getString("PRIORITY_GENERAL_TYPE_ID");
					if ("HIG".equals(priority)) {
						preAuthVO.setPriorityImageName("HighPriorityIcon");
						preAuthVO.setPriorityImageTitle("High Priority");
					} else if ("LOW".equals(priority)) {
						preAuthVO.setPriorityImageName("LowPriorityIcon");
						preAuthVO.setPriorityImageTitle("Low Priority");
					} else if ("MID".equals(priority)) {
						preAuthVO.setPriorityImageName("MediumPriorityIcon");
						preAuthVO.setPriorityImageTitle("Medium Priority");
					}

					preAuthVO.setHospitalName(rs.getString("HOSP_NAME"));
					preAuthVO
							.setEnrollmentID(rs.getString("TPA_ENROLLMENT_ID"));
					preAuthVO.setClaimantName(rs.getString("MEM_NAME"));
					preAuthVO.setAssignedTo(rs.getString("CONTACT_NAME"));
					preAuthVO.setStatusTypeID(rs
							.getString("PAT_STATUS_TYPE_ID"));
					alResultList.add(preAuthVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getPreAuthEnhancementList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getPreAuthEnhancementList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getPreAuthEnhancementList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getPreAuthEnhancementList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of PreAuthVO's which contains
	 * Pre-Authorization details
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Pre-Authorization
	 *         details
	 * @exception throws TTKException
	 */
	public ArrayList getActivityCodeList(ArrayList alSearchCriteria)
			throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ActivityDetailsVO activityDetailsVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strActivityCodeList);
			cStmtObject.setString(1, (String) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(1));
			cStmtObject.setString(3, (String) alSearchCriteria.get(2));
			cStmtObject.setString(4, (String) alSearchCriteria.get(3));
			cStmtObject.setString(5, (String) alSearchCriteria.get(4));
			cStmtObject.setString(6, (String) alSearchCriteria.get(7));
			cStmtObject.setString(7, (String) alSearchCriteria.get(8));
			cStmtObject.setString(8, (String) alSearchCriteria.get(9));
			cStmtObject.setString(9, (String) alSearchCriteria.get(10));
			cStmtObject.setLong(10, (Long) alSearchCriteria.get(5));
			cStmtObject.setString(11, (String) alSearchCriteria.get(6));
			cStmtObject.registerOutParameter(12, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(12);
			if (rs != null) {
				while (rs.next()) {
					activityDetailsVO = new ActivityDetailsVO();
					activityDetailsVO.setActivityCode(rs
							.getString("ACTIVITY_CODE"));
					activityDetailsVO.setActivityCodeDesc(rs
							.getString("ACTIVITY_DESCRIPTION"));
					activityDetailsVO.setsCategory(rs.getString("ACTIVITY_TYPE_SEQID"));
					
					if(rs.getString("activity_type_code")!=null){
					activityDetailsVO.setActivityTypeCode(rs.getString("activity_type_code"));
					}
					else{
						activityDetailsVO.setActivityTypeCode("");
					}
					
				
					alResultList.add(activityDetailsVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList; 
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getActivityCodeList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getActivityCodeList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getActivityCodeList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getActivityCodeList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of PreAuthVO's which contains
	 * Pre-Authorization details
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Pre-Authorization
	 *         details
	 * @exception throws TTKException
	 */
	public ArrayList getDiagnosisCodeList(ArrayList alSearchCriteria)
			throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		DiagnosisDetailsVO diagnosisDetailsVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strDiagnosisCodeList);
			cStmtObject.setString(1, (String) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(1));
			cStmtObject.setString(3, (String) alSearchCriteria.get(3));
			cStmtObject.setString(4, (String) alSearchCriteria.get(4));
			cStmtObject.setString(5, (String) alSearchCriteria.get(5));
			cStmtObject.setString(6, (String) alSearchCriteria.get(6));
			cStmtObject.setLong(7, (Long) alSearchCriteria.get(2));
			cStmtObject.registerOutParameter(8, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(8);
			if (rs != null) {
				while (rs.next()) {
					diagnosisDetailsVO = new DiagnosisDetailsVO();
					diagnosisDetailsVO.setIcdCode(rs.getString("ICD_CODE"));
					diagnosisDetailsVO.setAilmentDescription(rs
							.getString("LONG_DESC"));
					diagnosisDetailsVO.setIcdCodeSeqId(rs
							.getLong("ICD10_SEQ_ID"));
					alResultList.add(diagnosisDetailsVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getDiagnosisCodeList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getDiagnosisCodeList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getDiagnosisCodeList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getDiagnosisCodeList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of PreAuthVO's which contains
	 * Pre-Authorization details
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Pre-Authorization
	 *         details
	 * @exception throws TTKException
	 */
	public ArrayList getProviderList(ArrayList alSearchCriteria)
			throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ProviderDetailsVO providerDetailsVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strProviderList);
			cStmtObject.setString(1, (String) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(1));
			cStmtObject.setString(3, (String) alSearchCriteria.get(3));
			cStmtObject.setString(4, (String) alSearchCriteria.get(4));
			cStmtObject.setString(5, (String) alSearchCriteria.get(5));
			cStmtObject.setString(6, (String) alSearchCriteria.get(6));
			cStmtObject.setLong(7, (Long) alSearchCriteria.get(2));
			cStmtObject.registerOutParameter(8, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(8);
			if(rs != null){
				while(rs.next()){
					providerDetailsVO = new ProviderDetailsVO();					
					providerDetailsVO.setProviderSeqId(rs.getLong("HOSP_SEQ_ID"));
					providerDetailsVO.setProviderId(rs.getString("HOSP_LICENC_NUMB"));
					providerDetailsVO.setProviderName(rs.getString("HOSP_NAME"));
					providerDetailsVO.setProviderSpecificRemarks(rs.getString("PROVIDER_SPECIFIC_REMARKS"));
					providerDetailsVO.setProviderType(rs.getString("PROVIDER_TYPE_ID"));
					providerDetailsVO.setCurencyType(rs.getString("CURRENCY_ID"));
					providerDetailsVO.setProviderAuthority(rs.getString("AUTHORITY"));
					providerDetailsVO.setEmirate(rs.getString("state_name"));
					providerDetailsVO.setArea(rs.getString("city_name"));
					providerDetailsVO.setProviderPhone(rs.getString("off_phone_no_1"));
					providerDetailsVO.setVatTrnCode(rs.getString("vat_trn_numb"));
					//providerDetailsVO.setAuthority(rs.getString("AUTHORITY"));
					alResultList.add(providerDetailsVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getProviderList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getProviderList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getProviderList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getProviderList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of PreAuthVO's which contains
	 * Pre-Authorization details
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of PreAuthVO object which contains Pre-Authorization
	 *         details
	 * @exception throws TTKException
	 */
	public ArrayList getClinicianList(ArrayList alSearchCriteria)
			throws TTKException {

		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ClinicianDetailsVO clinicianDetailsVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strClinicianList);
			cStmtObject.setString(1, (String) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(1));
			cStmtObject.setString(3, (String) alSearchCriteria.get(2));
			cStmtObject.setString(4, (String) alSearchCriteria.get(3));
			cStmtObject.setString(5, (String) alSearchCriteria.get(6));
			cStmtObject.setString(6, (String) alSearchCriteria.get(7));
			cStmtObject.setString(7, (String) alSearchCriteria.get(8));
			cStmtObject.setString(8, (String) alSearchCriteria.get(9));
			cStmtObject.setLong(9, (Long) alSearchCriteria.get(4));
			cStmtObject.setString(10, (String) alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(11, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(11);
			if (rs != null) {
				while (rs.next()) {
					clinicianDetailsVO = new ClinicianDetailsVO();
					clinicianDetailsVO.setClinicianSeqId(rs.getLong("CONTACT_SEQ_ID"));
					clinicianDetailsVO.setClinicianId(rs.getString("PROFESSIONAL_ID"));
					clinicianDetailsVO.setClinicianName(rs.getString("CONTACT_NAME"));
					clinicianDetailsVO.setClinicianConsultation(rs.getString("CONSULT_GEN_TYPE"));
					alResultList.add(clinicianDetailsVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getClinicianList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getClinicianList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getClinicianList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getClinicianList(ArrayList alSearchCriteria)

	public ArrayList getProviderClinicianList(ArrayList alSearchCriteria)
			throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ClinicianDetailsVO clinicianDetailsVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strProviderClinicianList);
			cStmtObject.setString(1, (String) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(1));
			cStmtObject.setString(3, (String) alSearchCriteria.get(2));
			cStmtObject.setString(4, (String) alSearchCriteria.get(3));
			cStmtObject.setString(5, (String) alSearchCriteria.get(6));
			cStmtObject.setString(6, (String) alSearchCriteria.get(7));
			cStmtObject.setLong(7, (Long.parseLong(alSearchCriteria.get(8).toString())));
			cStmtObject.setLong(8, (Long.parseLong(alSearchCriteria.get(9).toString())));
			cStmtObject.setLong(9, (Long.parseLong(alSearchCriteria.get(4).toString())));
			cStmtObject.setLong(10, (Long.parseLong(alSearchCriteria.get(5).toString())));
			cStmtObject.registerOutParameter(1, Types.VARCHAR);
			cStmtObject.registerOutParameter(2, Types.VARCHAR);
			cStmtObject.registerOutParameter(3, Types.VARCHAR);
			cStmtObject.registerOutParameter(4, Types.VARCHAR);
			cStmtObject.registerOutParameter(5, Types.VARCHAR);
			cStmtObject.registerOutParameter(6, Types.VARCHAR);
			cStmtObject.registerOutParameter(7, Types.BIGINT);
			cStmtObject.registerOutParameter(8, Types.BIGINT);
			cStmtObject.registerOutParameter(11, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(11);
			if (rs != null) {
				while (rs.next()) {
					clinicianDetailsVO = new ClinicianDetailsVO();
					clinicianDetailsVO.setClinicianSeqId(rs
							.getLong("CONTACT_SEQ_ID"));
					clinicianDetailsVO.setClinicianId(rs
							.getString("PROFESSIONAL_ID"));
					clinicianDetailsVO.setClinicianName(rs
							.getString("CONTACT_NAME"));
					clinicianDetailsVO.setClinicianSpeciality(rs
							.getString("SPECIALITY"));
					clinicianDetailsVO.setClinicianConsultation(rs
							.getString("CONSULTAITON"));
					alResultList.add(clinicianDetailsVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getClinicianList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getClinicianList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getClinicianList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getProviderClinicianList(ArrayList alSearchCriteria)

	public ClinicianDetailsVO getProviderClinicianDetails(String clinicianId,
			Long hospSeqId) throws TTKException {
    	Connection conn = null;
    	ResultSet rs = null;
		ClinicianDetailsVO clinicianDetailsVO = null;
    	PreparedStatement pStmt 		= 	null;
        try {
            conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strProviderClinicianDetail);
            pStmt.setLong(1, hospSeqId);
            pStmt.setString(2, clinicianId);
            rs = pStmt.executeQuery();
			if (rs != null) {
				if (rs.next()) {
                	clinicianDetailsVO = new ClinicianDetailsVO();
					clinicianDetailsVO.setClinicianSeqId(rs
							.getLong("CONTACT_SEQ_ID"));
					clinicianDetailsVO.setClinicianId(rs
							.getString("PROFESSIONAL_ID"));
					clinicianDetailsVO.setClinicianName(rs
							.getString("CONTACT_NAME"));
					clinicianDetailsVO.setClinicianSpeciality(rs
							.getString("SPECIALITY"));
					clinicianDetailsVO.setClinicianConsultation(rs
							.getString("CONSULTAITON"));
				}// end of while(rs.next())
			}// end of if(rs != null)
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "onlineformsemp");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "onlineformsemp");
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
							"Error while closing the Resultset in PreAuthDAOImpl getPreAuthDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{

					try {
						if (pStmt != null)
							pStmt.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in PreAuthDAOImpl getPreAuthDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "hospital");
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
									"Error while closing the Connection in PreAuthDAOImpl getPreAuthDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "hospital");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "hospital");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
		return clinicianDetailsVO;
	}// end of getProviderClinicianDetails(String clinicianId,Long hospSeqId)

	/**
	 * This method returns the ActivityDetailsVO which contains
	 * ActivityDetailsVO details
	 * 
	 * @param activityDetailsVO
	 *            ActivityDetailsVO object which contains the Selected data
	 * @return This method returns the ActivityDetailsVO which contains
	 *         ActivityDetailsVO details
	 * @exception throws TTKException
	 */
	public ActivityDetailsVO getActivityCodeTariff(String sCategory,String activityCode,				
			String seqID, String activityStartDate, String authType,String overrideYN,String condDenialCode)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet resultSet = null;
		ResultSet tarrifrs = null;
		ActivityDetailsVO activityDetailsVO = new ActivityDetailsVO();
		activityDetailsVO.setDisplayMsg("NOTVALID");

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strActivityCodeTariff);
			cStmtObject.setString(1, seqID);
			cStmtObject.setString(2, authType);
			cStmtObject.setString(3, activityCode);
			cStmtObject.setDate(4, new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(activityStartDate).getTime()));
			cStmtObject.setString(5, sCategory);
			if("Y".equals(overrideYN))
			{
				cStmtObject.setString(6, overrideYN);
				cStmtObject.setString(7, condDenialCode);
			}
			else
			{
				cStmtObject.setString(6, null);
				cStmtObject.setString(7, null);	
			}
			cStmtObject.registerOutParameter(8, Types.OTHER);
			cStmtObject.registerOutParameter(9, Types.OTHER);
			cStmtObject.execute();
			resultSet = (ResultSet) cStmtObject.getObject(8);
			if (resultSet != null) {
				if (resultSet.next()) {
				 activityDetailsVO.setDisplayMsg("VALID");				 
				 activityDetailsVO.setIrdrgWarnMsg(resultSet.getString("IRDRG_ALERT"));
					activityDetailsVO.setActivityCodeSeqId(resultSet
							.getLong("ACTIVITY_SEQ_ID"));
					activityDetailsVO.setActivityCodeDesc(resultSet
							.getString("ACTIVITY_DESCRIPTION"));
					activityDetailsVO.setActivityTypeId(resultSet
							.getString("ACTIVITY_TYPE_ID"));
					activityDetailsVO.setActivityTariffStatus(resultSet
							.getString("ACTIVITY_TARIFF_STATUS"));
					
					if (resultSet.getString("TARIFF_YN") != null) { 
						activityDetailsVO.setTariffYN(resultSet
								.getString("TARIFF_YN"));
						
					}
					
				//	activityDetailsVO.setToothNoReqYN(resultSet.getString("TOOTH_REQ_YN"));//resultSet.getString("TOOTH_REQ_YN")
					tarrifrs = (ResultSet) cStmtObject.getObject(9);

					if (tarrifrs != null) {
						if (tarrifrs.next()) {

							if (tarrifrs.getBigDecimal("GROSS_AMOUNT") != null) 
							{
								activityDetailsVO.setGrossAmount(tarrifrs.getBigDecimal("GROSS_AMOUNT"));
								activityDetailsVO.setUnitPrice(tarrifrs.getBigDecimal("GROSS_AMOUNT"));
		                        }
						
							if (tarrifrs.getBigDecimal("DISC_AMOUNT") != null) {
								activityDetailsVO.setDiscount(tarrifrs
										.getBigDecimal("DISC_AMOUNT"));
				}
							if (tarrifrs.getBigDecimal("UNIT_DISCOUNT") != null) {
								activityDetailsVO.setDiscountPerUnit(tarrifrs
										.getBigDecimal("UNIT_DISCOUNT"));
				}
							if (tarrifrs.getBigDecimal("DISC_GROSS_AMOUNT") != null) {
								activityDetailsVO.setDiscountedGross(tarrifrs
										.getBigDecimal("DISC_GROSS_AMOUNT"));
								activityDetailsVO.setNetAmount(tarrifrs
										.getBigDecimal("DISC_GROSS_AMOUNT"));
				}
							if (tarrifrs.getString("BUNDLE_ID") != null) {
								activityDetailsVO.setBundleId(tarrifrs
										.getString("BUNDLE_ID"));
			}
							if (tarrifrs.getString("PACKAGE_ID") != null) {
								activityDetailsVO.setPackageId(tarrifrs
										.getString("PACKAGE_ID"));
			}
							if (tarrifrs.getString("INTERNAL_CODE") != null) {
								activityDetailsVO.setInternalCode(tarrifrs
										.getString("INTERNAL_CODE"));
		     }
		     }

		 }
					activityDetailsVO.setHaad_YN(TTKCommon.checkNull(resultSet.getString("haad_yn")));
					activityDetailsVO.setEdit_YN(TTKCommon.checkNull(resultSet.getString("edit_yn")));

					String editStatus = "N";
					if ("Y".equals(resultSet.getString("haad_yn"))
							&& "Y".equals(resultSet.getString("edit_yn"))) {
						editStatus = "Y";
					}
					if ("Y".equals(resultSet.getString("TARIFF_YN"))) {
						editStatus = "N";
					}
					if ("N".equals(resultSet.getString("TARIFF_YN"))) 
					{
						if ("Y".equals(resultSet.getString("haad_yn"))&& "Y".equals(resultSet.getString("edit_yn"))) 
						{
							editStatus = "N";
						}
			
			}
					activityDetailsVO.setsCategory(resultSet.getString("ACTIVITY_TYPE_SEQ_ID"));
		
					activityDetailsVO.setEditStatus(editStatus);
			 }
			}

			return activityDetailsVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (resultSet != null)
						resultSet.close();
					if (tarrifrs != null)
						tarrifrs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getActivityCodeTariff()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getActivityCodeTariff()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getActivityCodeTariff()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				resultSet = null;
				tarrifrs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getActivityCodeTariff(ArrayList alSearchCriteria)

	public ArrayList<String[]> getPreauthHistoryList(Long memberSeqID,
			String authType, String preauthorclaimNumber) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
	    ResultSet ars = null;
		ArrayList<String[]> authList = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strPreAuthorizationHistoryList);
			cStmtObject.setLong(1, memberSeqID);
			cStmtObject.setString(2, authType);
			if(preauthorclaimNumber!= null){
				cStmtObject.setString(3, preauthorclaimNumber);
			}else{
				cStmtObject.setString(3, null);	
			}
			cStmtObject.registerOutParameter(4, Types.OTHER);
			cStmtObject.execute();
			ars = (java.sql.ResultSet) cStmtObject.getObject(4);

			if (ars != null) {
				authList = new ArrayList<String[]>();
				while (ars.next()) {
					String authSeqId = "";
					String preAuthNo = "";
					String authNo = "";
					if ("PAT".equals(authType)) {
						authSeqId = ars.getLong("PAT_AUTH_SEQ_ID") == 0 ? ""
								: ars.getLong("PAT_AUTH_SEQ_ID") + "";
						preAuthNo = ars.getString("PRE_AUTH_NUMBER") == null ? ""
								: ars.getString("PRE_AUTH_NUMBER");
						authNo = ars.getString("AUTH_NUMBER") == null ? ""
								: ars.getString("AUTH_NUMBER");
					} else if ("CLM".equals(authType)) {
						authSeqId = ars.getLong("CLAIM_SEQ_ID") == 0 ? "" : ars
								.getLong("CLAIM_SEQ_ID") + "";
						preAuthNo = ars.getString("CLAIM_NUMBER") == null ? ""
								: ars.getString("CLAIM_NUMBER");
						authNo = ars.getString("settlement_number") == null ? ""
								: ars.getString("settlement_number");
					}
					
					String hospitalName=ars.getString("HOSP_NAME")==null?"":ars.getString("HOSP_NAME");
					String approvedAmt=ars.getBigDecimal("TOT_APPROVED_AMOUNT")==null?"":ars.getBigDecimal("TOT_APPROVED_AMOUNT").toString();
					String admissionDate=ars.getDate("ADMISSION_DATE")==null?"":ars.getDate("ADMISSION_DATE").toString();
					String status=ars.getString("PAT_STATUS")==null?"":ars.getString("PAT_STATUS");
					
					
					String approvedDate=ars.getString("approved_date")==null?"":ars.getString("approved_date");
					String paymentDate=ars.getString("payment_date")==null?"":ars.getString("payment_date");
					String chonologicalNo=ars.getString("chronic_number")==null?"":ars.getString("chronic_number");
					 
					String referenceNo=ars.getString("REFERENCE_NUM")==null?"":ars.getString("REFERENCE_NUM");

					authList.add(new String[]{authSeqId,preAuthNo,authNo,hospitalName,approvedAmt,admissionDate,status,approvedDate,paymentDate,chonologicalNo,referenceNo});			
				
				}
			}
			return authList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (ars != null)
						ars.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getPreauthHistoryList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getPreauthHistoryList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getPreauthHistoryList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ars = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getPreauthHistoryList(Long memberSeqID)
	

	public ArrayList<ArrayList<String[]>> getCustomerHistory(Long memberSeqID)throws TTKException
	{
	Connection conn = null;
	    CallableStatement cStmtObject = null;
	   ResultSet ars = null;
	    ArrayList<String[]> authListPAT = null;
	    ArrayList<String[]> authListCLM = null;
	    ArrayList<ArrayList<String[]>> al = new ArrayList<ArrayList<String[]>>();
	    try {
	        conn = ResourceManager.getConnection();
	        cStmtObject = (java.sql.CallableStatement) conn
	                .prepareCall(strPreAuthorizationHistoryList1);

	        cStmtObject.setLong(1, memberSeqID);
	       
	        //cStmtObject.setString(2, null);
	        cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
	        cStmtObject.execute();
	        ars = (java.sql.ResultSet) cStmtObject.getObject(2);

	        if (ars != null) {
	            authListPAT = new ArrayList<String[]>();
	            authListCLM =  new ArrayList<String[]>();
	            while (ars.next()) {
	                String authSeqId = "";
	                String preAuthNo = "";
	                String authNo = "";
	                String flag = ars.getString("flag");
	                if ("P".equals(flag)) {
	                    authSeqId = ars.getLong("PAT_AUTH_SEQ_ID") == 0 ? ""
	                            : ars.getLong("PAT_AUTH_SEQ_ID") + "";
	                    preAuthNo = ars.getString("PRE_AUTH_NUMBER") == null ? ""
	                            : ars.getString("PRE_AUTH_NUMBER");
	                    authNo = ars.getString("AUTH_NUMBER") == null ? ""
	                            : ars.getString("AUTH_NUMBER");
	                } if ("C".equals(flag)) {
	                    authSeqId = ars.getLong("CLAIM_SEQ_ID") == 0 ? "" : ars
	                            .getLong("CLAIM_SEQ_ID") + "";
	                    preAuthNo = ars.getString("CLAIM_NUMBER") == null ? ""
	                            : ars.getString("CLAIM_NUMBER");
	                    authNo = ars.getString("settlement_number") == null ? ""
	                            : ars.getString("settlement_number");
	                }
	               
	                String hospitalName=ars.getString("HOSP_NAME")==null?"":ars.getString("HOSP_NAME");
	                String approvedAmt=ars.getBigDecimal("TOT_APPROVED_AMOUNT")==null?"":ars.getBigDecimal("TOT_APPROVED_AMOUNT").toString();
	                String admissionDate=ars.getDate("ADMISSION_DATE")==null?"":ars.getDate("ADMISSION_DATE").toString();
	                String status=ars.getString("PAT_STATUS")==null?"":ars.getString("PAT_STATUS");
	               
	               
	                String approvedDate=ars.getString("approved_date")==null?"":ars.getString("approved_date");
	                String paymentDate=ars.getString("payment_date")==null?"":ars.getString("payment_date");
	               
	               
	               
	               
	                if ("P".equals(flag))
	                    authListPAT.add(new String[]{authSeqId,preAuthNo,authNo,hospitalName,approvedAmt,admissionDate,status,approvedDate,paymentDate,flag});           
	                else if("C".equals(flag))
	                    authListCLM.add(new String[]{authSeqId,preAuthNo,authNo,hospitalName,approvedAmt,admissionDate,status,approvedDate,paymentDate,flag});           
	               
	            }
	           
	        }
	        al.add(authListPAT);
	        al.add(authListCLM);
	        return al;
	    }// end of try
	    catch (SQLException sqlExp) {
	        throw new TTKException(sqlExp, "preauth");
	    }// end of catch (SQLException sqlExp)
	    catch (Exception exp) {
	        throw new TTKException(exp, "preauth");
	    }// end of catch (Exception exp)
	    finally {
	        /* Nested Try Catch to ensure resource closure */
	        try // First try closing the result set
	        {
	            try {
	                if (ars != null)
	                    ars.close();
	            }// end of try
	            catch (SQLException sqlExp) {
	                log.error(
	                        "Error while closing the Resultset in PreAuthDAOImpl getPreauthHistoryList()",
	                        sqlExp);
	                throw new TTKException(sqlExp, "preauth");
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
	                            "Error while closing the Statement in PreAuthDAOImpl getPreauthHistoryList()",
	                            sqlExp);
	                    throw new TTKException(sqlExp, "preauth");
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
	                                "Error while closing the Connection in PreAuthDAOImpl getPreauthHistoryList()",
	                                sqlExp);
	                        throw new TTKException(sqlExp, "preauth");
	                    }// end of catch (SQLException sqlExp)
	                }// end of finally Connection Close
	            }// end of finally Statement Close
	        }// end of try
	        catch (TTKException exp) {
	            throw new TTKException(exp, "preauth");
	        }// end of catch (TTKException exp)
	        finally // Control will reach here in anycase set null to the
	                // objects
	        {
	            ars = null;
	            cStmtObject = null;
	            conn = null;
	        }// end of finally
	    }// end of finally
	}// end of getPreauthHistoryList(Long memberSeqID)//
	
	
	
	 public String[][]getCustomerForBenifits(Long memberSeqID,String benifitType) throws TTKException {
		ClaimantVO claimantVO	=new ClaimantVO();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		//ResultSet rs	=	null;
		ResultSet rs1	=	null;
		String[][] tobBenefits	=	new String[7][21];  //7,12
		
		try{
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetCustomerForBenifits);
				cStmtObject.setLong(1,memberSeqID);
				cStmtObject.setString(2,benifitType);
				cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
				cStmtObject.execute();
				rs1 = (java.sql.ResultSet)cStmtObject.getObject(3);
				
				if(rs1 != null)
			{
					int i=0;
				while(rs1.next())
					
				{
					int j=0;
					
					if("DNTL".equals(benifitType)||"OPTC".equals(benifitType))
					{
					tobBenefits[0][0]	=	rs1.getString("total_limit");
					tobBenefits[0][1]	=	rs1.getString("ava_amount");
					tobBenefits[0][2]	=	rs1.getString("COPAY_DED");
					}
					else if("DAYC".equals(benifitType))
					{
						tobBenefits[0][0]	=	rs1.getString("COPAY");
					}
					else if("CHRO".equals(benifitType)||"PED".equals(benifitType))
					{
						tobBenefits[0][0]	=	rs1.getString("Waiting_period");
						tobBenefits[0][1]	=	rs1.getString("DATE_OF_INCEPTION");
					}
					
					else if("MTI".equals(benifitType))
					{
						//waiting Period 
						tobBenefits[0][0]	=	rs1.getString("waiting_period");
						
						//inpatient (with  emergency)
						tobBenefits[0][1]	=	rs1.getString("em_nrml_copay");
						tobBenefits[0][2]	=	rs1.getString("em_nrml_limit");
						tobBenefits[0][3]	=	rs1.getString("em_nrml_ava_limit");
						
						//inpatient (without emergency)
						tobBenefits[0][4]	=	rs1.getString("nrml_copay");
						tobBenefits[0][5]	=	rs1.getString("nrml_limit");
						tobBenefits[0][6]	=	rs1.getString("nrml_ava_limit");
						
						//inpatient LSCS(with  emergency)
						tobBenefits[0][7]	=	rs1.getString("em_lscs_copay");
						tobBenefits[0][8]	=	rs1.getString("em_lscs_limit");
						tobBenefits[0][9]	=	rs1.getString("em_lscs_ava_limit");
						
						////inpatient LSCS(without emergency)
						tobBenefits[0][10]	=	rs1.getString("lscs_copay");
						tobBenefits[0][11]	=	rs1.getString("lscs_limit");
						tobBenefits[0][12]	=	rs1.getString("lscs_ava_limit");
						
						//outpatient (with emergen)
						tobBenefits[0][13]	=	rs1.getString("ope_limit");
						tobBenefits[0][14]	=	rs1.getString("ope_copay");
						tobBenefits[0][15]	=	rs1.getString("ope_visits");
						
						////outpatient (without emergen emergen)
						tobBenefits[0][16]	=	rs1.getString("opw_limit");
						tobBenefits[0][17]	=	rs1.getString("opw_copay");
						tobBenefits[0][18]	=	rs1.getString("opw_visits");
						
						//Area of Coverage for I/P
						tobBenefits[0][19]	=	rs1.getString("mat_loc");
						
						//Hospital coverage
						tobBenefits[0][20]	=	rs1.getString("mat_pro");
					}
					else if("IPT".equals(benifitType)){
						
						if("CON".equals(rs1.getString("FLAG")) ||  "LAB".equals(rs1.getString("FLAG")) || "PATH".equals(rs1.getString("FLAG"))  ||"PHY".equals(rs1.getString("FLAG")) || "REGION".equals(rs1.getString("FLAG"))) 
						{
							tobBenefits[i][j]=rs1.getString("COPAY_DED");
							
							tobBenefits[i][j+1]=rs1.getString("clin_con_cop");
							
							tobBenefits[i][j+2]=rs1.getString("LAB");
							
							tobBenefits[i][j+3]=rs1.getString("PATH");
							
							tobBenefits[i][j+4]=rs1.getString("total_sessions");
						
							tobBenefits[i][j+5]=rs1.getString("PHY_COPAY");
							
							tobBenefits[i][j+6]=rs1.getString("ava_session");
						
							tobBenefits[i][j+7]=rs1.getString("area_cover");
							tobBenefits[i][j+8]=rs1.getString("pro_cover");
							
						
						}
						
					}//END OF "IPT"
					
					else if("OPTS".equals(benifitType)){
						
						if("CON_HOS".equals(rs1.getString("flag"))
								||"LAB".equals(rs1.getString("flag"))
								||"PATH".equals(rs1.getString("flag"))
								||"PHY".equals(rs1.getString("flag"))
								||"CON_NON".equals(rs1.getString("flag"))
								||"PRE".equals(rs1.getString("flag"))
								||"REGION".equals(rs1.getString("flag")))
						{
							tobBenefits[i][j]=rs1.getString("COPAY_DED");
							tobBenefits[i][j+1]=rs1.getString("clin_con_cop");
							
							tobBenefits[i][j+2]=rs1.getString("lab");
						
							tobBenefits[i][j+3]=rs1.getString("path");
							
							tobBenefits[i][j+4]=rs1.getString("total_sessions");
						
							tobBenefits[i][j+5]=rs1.getString("phy_copay");
							tobBenefits[i][j+6]=rs1.getString("ava_session");
							tobBenefits[i][j+7]=rs1.getString("pres_copay");
							tobBenefits[i][j+8]=rs1.getString("pres_ava_limit");
							tobBenefits[i][j+9]=rs1.getString("area_cover");
							tobBenefits[i][j+10]=rs1.getString("pro_cover");
						
						}
						
					}//END OF "OPTS"
					
					i++;
				}//end of while(rs1.next())
			}//end of if(rs1 != null)
				 
			
				//lResult++;
		}//end of try
		
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs1 != null)
						rs1.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl getCustomerForBenifits()()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getCustomerForBenifits()()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Statement in PreAuthDAOImpl getCustomerForBenifits()()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return tobBenefits;
	}// end of getCustomerForBenifits()
	

	public PreAuthDetailVO getPreAuthDetails(Long preAuthSeqID) throws TTKException{
		
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet prs = null;
		ResultSet drs = null;
		ResultSet ars = null;
		ResultSet shrs = null;
		PreAuthDetailVO preAuthDetailVO  = new PreAuthDetailVO();
		ArrayList<DiagnosisDetailsVO> diagnosis = new ArrayList<DiagnosisDetailsVO>();
		ArrayList<ActivityDetailsVO> activities = new ArrayList<ActivityDetailsVO>();
		ArrayList<String[]> shortfalls = null;
		try {
			
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strPreAuthorization);

			cStmtObject.setLong(1, preAuthSeqID);
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.registerOutParameter(3, Types.OTHER);
			cStmtObject.registerOutParameter(4, Types.OTHER);
			cStmtObject.registerOutParameter(5, Types.OTHER);
			cStmtObject.execute();
			prs = (java.sql.ResultSet) cStmtObject.getObject(2);// preauth Details
            drs = (java.sql.ResultSet) cStmtObject.getObject(3);// diagnosis Details
            ars = (java.sql.ResultSet) cStmtObject.getObject(4);// activity Details
            shrs = (java.sql.ResultSet) cStmtObject.getObject(5);// Shortfalls	
			if (prs != null) {
				if (prs.next()) {
						
					if(prs.getString("COPAY_LIMIT_REACHED_YN") !=null)preAuthDetailVO.setIpCopayLimitYN(prs.getString("COPAY_LIMIT_REACHED_YN"));
					
					preAuthDetailVO.setConsultationType(TTKCommon.checkNull(prs.getString("CONSULT_GEN_TYPE")));
					
					if (prs.getString("total_final_approved_amnt") != null)
						preAuthDetailVO.setFinalAprAmt(prs.getString("total_final_approved_amnt"));
						
						if(prs.getString("MEMBER_VALIDATION") !=null){
							preAuthDetailVO.setMemActiveStatus(prs.getString("MEMBER_VALIDATION"));
						}
					if (prs.getString("PRODUCT_NAME") != null)
						preAuthDetailVO.setProductName(prs.getString("PRODUCT_NAME"));
						
                   if(prs.getLong("PAT_AUTH_SEQ_ID") !=0)preAuthDetailVO.setPreAuthSeqID(prs.getLong("PAT_AUTH_SEQ_ID"));
					
                   preAuthDetailVO.setPreAuthNo(TTKCommon.checkNull(prs.getString("PRE_AUTH_NUMBER")));

					if(prs.getLong("PARENT_PAT_AUTH_SEQ_ID") !=0)preAuthDetailVO.setParentPreAuthSeqID(prs.getLong("PARENT_PAT_AUTH_SEQ_ID"));
					
					if(prs.getString("AUTH_NUMBER") !=null){
						preAuthDetailVO.setAuthNum(prs.getString("AUTH_NUMBER"));
					}//
					preAuthDetailVO.setPreAuthRecvTypeID(TTKCommon.checkNull(prs.getString("SOURCE_TYPE_ID")));
					preAuthDetailVO.setPreAuthRecvTypeID(TTKCommon.checkNull(prs.getString("SOURCE_TYPE_ID")));
					if (prs.getString("PAT_RECEIVED_DATE") != null) {
						preAuthDetailVO.setReceiveDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date(prs.getTimestamp("PAT_RECEIVED_DATE").getTime())));
						preAuthDetailVO.setReceiveTime(TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >= 3 ? TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[1]: "");
						preAuthDetailVO.setReceiveDay(TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ").length >= 3 ? TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("PAT_RECEIVED_DATE").getTime())).split(" ")[2]: "");
					}// end of if(prs.getString("PAT_RECEIVED_DATE") != null)
					if (prs.getString("HOSPITALIZATION_DATE") != null) {
						preAuthDetailVO.setAdmissionDate(new Date(prs.getTimestamp("HOSPITALIZATION_DATE").getTime()));
						preAuthDetailVO.setAdmissionTime(TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("HOSPITALIZATION_DATE").getTime())).split(" ").length >= 3 ? TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("HOSPITALIZATION_DATE").getTime())).split(" ")[1]: "");
						preAuthDetailVO.setAdmissionDay(TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("HOSPITALIZATION_DATE").getTime())).split(" ").length >= 3 ? TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("HOSPITALIZATION_DATE").getTime())).split(" ")[2]: "");
					}// end of if(prs.getString("DATE_OF_HOSPITALIZATION") !=
						// null)
					if (prs.getString("DISCHARGE_DATE") != null) {
						preAuthDetailVO.setDischargeDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date(prs.getTimestamp("DISCHARGE_DATE").getTime())));
						preAuthDetailVO.setDischargeTime(TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("DISCHARGE_DATE").getTime())).split(" ").length >= 3 ? TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("DISCHARGE_DATE").getTime())).split(" ")[1] : "");
						preAuthDetailVO.setDischargeDay(TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("DISCHARGE_DATE").getTime())).split(" ").length >= 3 ? TTKCommon.getFormattedDateHour(new Date(prs.getTimestamp("DISCHARGE_DATE").getTime())).split(" ")[2] : "");
					}// end of if(prs.getString("DISCHARGE_DATE") != null)

					if (prs.getString("TPA_ENROLLMENT_ID") != null) {
						preAuthDetailVO.setMemberId(prs.getString("TPA_ENROLLMENT_ID"));
					}// end of if(prs.getString("PAT_REQUESTED_AMOUNT") != null)
					if (prs.getLong("MEMBER_SEQ_ID") != 0) {
						preAuthDetailVO.setMemberSeqID(prs.getLong("MEMBER_SEQ_ID"));
					}// end of if(prs.getLong("MEMBER_SEQ_ID") != null)
					
					
					if (prs.getString("MEM_NAME") != null) {
						preAuthDetailVO.setPatientName(prs.getString("MEM_NAME"));
					}// end of if(prs.getString("MEM_NAME") != null)
					if (prs.getString("CLASSIFICATION") != null) {
						preAuthDetailVO.setPolicyCategory(prs.getString("CLASSIFICATION"));

					}// end of if(prs.getString("CLASSIFICATION") != null)

					if (prs.getString("COMPLETED_YN") != null) {
						preAuthDetailVO.setPreauthCompleteStatus(prs.getString("COMPLETED_YN"));
					}// end of if(prs.getString("COMPLETED_YN") != null)
					if (prs.getInt("MEM_AGE") != 0) {
						preAuthDetailVO.setMemberAge(prs.getInt("MEM_AGE"));
					}
					if (prs.getString("EMIRATE_ID") != null) {
						preAuthDetailVO.setEmirateId(prs.getString("EMIRATE_ID"));
					}
					//Member Payer ID
					if (prs.getString("PAYER_ID") != null) {
						preAuthDetailVO.setPayerId(prs.getString("PAYER_ID"));
					}
					if (prs.getLong("INS_SEQ_ID") != 0) {
					preAuthDetailVO.setInsSeqId(prs.getLong("INS_SEQ_ID"));
					}
					if (prs.getLong("POLICY_SEQ_ID") != 0) {
						preAuthDetailVO.setPolicySeqId(prs.getLong("POLICY_SEQ_ID"));
					}
					if (prs.getString("INS_COMP_NAME") != null) {
						preAuthDetailVO.setPayerName(prs.getString("INS_COMP_NAME"));
					}
					if (prs.getString("ENCOUNTER_TYPE_ID") != null) {
						preAuthDetailVO.setEncounterTypeId(prs.getString("ENCOUNTER_TYPE_ID"));
					}
					if (prs.getString("ENCOUNTER_FACILITY_ID") != null) {
						preAuthDetailVO.setEncounterFacilityId(prs.getString("ENCOUNTER_FACILITY_ID"));
					}
					if (prs.getString("ENCOUNTER_START_TYPE") != null) {
						preAuthDetailVO.setTreatmentTypeID(prs.getString("ENCOUNTER_START_TYPE"));
					}
					if(prs.getString("PROVIDER_ID") !=null){
						preAuthDetailVO.setProviderId(prs.getString("PROVIDER_ID"));
						
					}
					if(prs.getString("PROVIDER_TYPE_ID") !=null){
						preAuthDetailVO.setProviderType(prs.getString("PROVIDER_TYPE_ID"));
						
					}
					if(prs.getLong("HOSP_SEQ_ID") !=0){
						preAuthDetailVO.setProviderSeqId(prs.getLong("HOSP_SEQ_ID"));
						
					}					
					
					if(prs.getString("provider_authority") !=null){
						preAuthDetailVO.setProvAuthority(prs.getString("provider_authority"));
						
                    }
					preAuthDetailVO.setVatTrnCode(prs.getString("vat_trn_numb"));
				
					if (prs.getString("HOSP_NAME") != null) {
						preAuthDetailVO.setProviderName(prs.getString("HOSP_NAME"));
					}
					
					
					if (prs.getString("HOSP_ADDRESS") != null) {
						preAuthDetailVO.setProviderDetails(prs.getString("HOSP_ADDRESS"));
					}
					if (prs.getString("ENCOUNTER_START_TYPE") != null) {
						preAuthDetailVO.setEncounterStartTypeId(prs.getString("ENCOUNTER_START_TYPE"));
					}
					if (prs.getString("ENCOUNTER_END_TYPE") != null) {
						preAuthDetailVO.setEncounterEndTypeId(prs.getString("ENCOUNTER_END_TYPE"));
					}
					if (prs.getString("CLINICIAN_ID") != null) {
						preAuthDetailVO.setClinicianId(prs.getString("CLINICIAN_ID"));
					}
					if (prs.getString("CLINICIAN_NAME") != null) {
						preAuthDetailVO.setClinicianName(prs.getString("CLINICIAN_NAME"));
					}
					
					
					if (prs.getString("SYSTEM_OF_MEDICINE_TYPE_ID") != null) {
						preAuthDetailVO.setSystemOfMedicine(prs.getString("SYSTEM_OF_MEDICINE_TYPE_ID"));
					}
					if (prs.getString("ACCIDENT_RELATED_TYPE_ID") != null) {
						preAuthDetailVO.setAccidentRelatedCase(prs.getString("ACCIDENT_RELATED_TYPE_ID"));
					}
					if (prs.getString("GENDER") != null) {
						preAuthDetailVO.setPatientGender(prs.getString("GENDER"));
					}
					if (prs.getString("PRESENTING_COMPLAINTS") != null) {
						preAuthDetailVO.setPresentingComplaints(prs.getString("PRESENTING_COMPLAINTS"));
					}
					if (prs.getString("MEDICAL_OPINION_REMARKS") != null) {
						preAuthDetailVO.setMedicalOpinionRemarks(prs.getString("MEDICAL_OPINION_REMARKS"));
					}
					if (prs.getString("PAT_STATUS_TYPE_ID") != null) {
						preAuthDetailVO.setPreauthStatus(prs.getString("PAT_STATUS_TYPE_ID"));
					}
					if (prs.getString("COMPLETED_YN") != null) {
						String viewMode = prs.getString("COMPLETED_YN");
						preAuthDetailVO.setPreauthViewMode("Y".equals(viewMode) ? "true": "false");
					}
					if (prs.getString("PRIORITY_GENERAL_TYPE_ID") != null) {
						preAuthDetailVO.setPriorityTypeID(prs.getString("PRIORITY_GENERAL_TYPE_ID"));
					}
					if (prs.getString("NETWORK_YN") != null) {
						preAuthDetailVO.setNetworkProviderType(prs.getString("NETWORK_YN"));
					}
					if (prs.getString("CITY_TYPE_ID") != null) {
						preAuthDetailVO.setProviderArea(prs.getString("CITY_TYPE_ID"));
					}
					if (prs.getString("STATE_TYPE_ID") != null) {
						preAuthDetailVO.setProviderEmirate(prs.getString("STATE_TYPE_ID"));
					}
					if (prs.getString("COUNTRY_TYPE_ID") != null) {
						preAuthDetailVO.setProviderCountry(prs.getString("COUNTRY_TYPE_ID"));
					}
					if (prs.getString("PROVIDER_ADDRESS") != null) {
						preAuthDetailVO.setProviderAddress(prs.getString("PROVIDER_ADDRESS"));
					}
					if (prs.getString("PINCODE") != null) {
						preAuthDetailVO.setProviderPobox(prs.getString("PINCODE"));
						}

					if (prs.getBigDecimal("REQUESTED_AMOUNT") != null) {
						preAuthDetailVO.setRequestedAmount(prs.getBigDecimal("REQUESTED_AMOUNT"));
						preAuthDetailVO.setProRequestedAmount(prs.getBigDecimal("REQUESTED_AMOUNT"));
					}
									
					if (prs.getString("REMARKS") != null) {
						preAuthDetailVO.setRemarks(prs.getString("REMARKS"));
					}
					if (prs.getString("CLINICIAN_STATUS") != null) {
						preAuthDetailVO.setClinicianStatus(prs.getString("CLINICIAN_STATUS"));
					}
					if (prs.getBigDecimal("TOT_GROSS_AMOUNT") != null) {
						preAuthDetailVO.setGrossAmount(prs.getBigDecimal("TOT_GROSS_AMOUNT"));
					}
					if (prs.getBigDecimal("TOT_DISCOUNT_AMOUNT") != null) {
						preAuthDetailVO.setDiscountAmount(prs.getBigDecimal("TOT_DISCOUNT_AMOUNT"));
					}
					if (prs.getBigDecimal("TOT_DISC_GROSS_AMOUNT") != null) {
						preAuthDetailVO.setDiscountGrossAmount(prs.getBigDecimal("TOT_DISC_GROSS_AMOUNT"));
					}

					if (prs.getBigDecimal("TOT_PATIENT_SHARE_AMOUNT") != null) {
						preAuthDetailVO.setPatShareAmount(prs.getBigDecimal("TOT_PATIENT_SHARE_AMOUNT"));
					}
					if (prs.getBigDecimal("TOT_NET_AMOUNT") != null) {
						preAuthDetailVO.setNetAmount(prs.getBigDecimal("TOT_NET_AMOUNT"));
					}
					if (prs.getBigDecimal("TOT_ALLOWED_AMOUNT") != null) {
						preAuthDetailVO.setAllowedAmount(prs.getBigDecimal("TOT_ALLOWED_AMOUNT"));
					}
					if (prs.getBigDecimal("FINAL_APP_AMOUNT") != null) {
						preAuthDetailVO.setApprovedAmount(prs.getBigDecimal("FINAL_APP_AMOUNT"));
					}
					if (prs.getString("POLICY_NUMBER") != null) {
						preAuthDetailVO.setPolicyNumber(prs.getString("POLICY_NUMBER"));
					}
					if (prs.getString("CORP_NAME") != null) {
						preAuthDetailVO.setCorporateName(prs.getString("CORP_NAME"));
					}
					if (prs.getString("GRAVIDA") != null) {
						preAuthDetailVO.setGravida(prs.getInt("GRAVIDA"));
					}
					if (prs.getString("LIVE") != null) {
						preAuthDetailVO.setLive(prs.getInt("LIVE"));
					}
					if (prs.getString("PARA") != null) {
						preAuthDetailVO.setPara(prs.getInt("PARA"));
					}
					if (prs.getString("ABORTION") != null) {
						preAuthDetailVO.setAbortion(prs.getInt("ABORTION"));
					}
					if (prs.getString("BENIFIT_TYPES") != null) {
						preAuthDetailVO.setBenefitType(prs.getString("BENIFIT_TYPES"));
					}
					if (prs.getString("FRESH_YN") != null) {
						preAuthDetailVO.setPreAuthNoStatus(("Y".equals(prs.getString("FRESH_YN")) ? "FRESH": "ENHANCEMENT"));
					}
					if (prs.getString("CURRENCY_TYPE") != null) {
						preAuthDetailVO.setRequestedAmountCurrency(prs.getString("CURRENCY_TYPE"));
					}
					if (prs.getDate("POLICY_START_DATE") != null) {
						preAuthDetailVO.setPolicyStartDate(TTKCommon.convertDateAsString("dd/MM/yyyy",prs.getDate("POLICY_START_DATE")));
					}
					if (prs.getDate("POLICY_END_DATE") != null) {
						preAuthDetailVO.setPolicyEndDate(TTKCommon.convertDateAsString("dd/MM/yyyy",prs.getDate("POLICY_END_DATE")));
					}
					preAuthDetailVO.setNationality(TTKCommon.checkNull(prs.getString("NATIONALITY")));
					if (prs.getBigDecimal("SUM_INSURED") != null) {
						preAuthDetailVO.setSumInsured(prs.getBigDecimal("SUM_INSURED"));
					}
					if (prs.getBigDecimal("AVA_SUM_INSURED") != null) {
						preAuthDetailVO.setAvailableSumInsured(prs.getBigDecimal("AVA_SUM_INSURED"));
					}
					if (prs.getBigDecimal("DIS_ALLOWED_AMOUNT") != null) {
						preAuthDetailVO.setDisAllowedAmount(prs.getBigDecimal("DIS_ALLOWED_AMOUNT"));
					}
					if (prs.getString("ENROL_TYPE_ID") != null) {
						preAuthDetailVO.setPolicyType(prs.getString("ENROL_TYPE_ID"));
					}
					if (prs.getString("DUP_PAT") != null) {
						preAuthDetailVO.setDuplicatePreauthAlert(prs.getString("DUP_PAT"));
					}
					if (prs.getString("PRODUCT_NAME") != null) {
						preAuthDetailVO.setProductName(prs.getString("PRODUCT_NAME"));
					}
					
					if (prs.getString("PAYER_AUTHORITY") != null) {
						preAuthDetailVO.setPayerAuthority(prs.getString("PAYER_AUTHORITY"));

					}
					if (prs.getString("OVERRIDE_REMARKS") != null) {
						preAuthDetailVO.setOverrideRemarks(prs.getString("OVERRIDE_REMARKS"));
					}
					if (prs.getString("MEMBER_ADDED_DATE") != null) {
						preAuthDetailVO.setPreMemInceptionDt(prs.getString("MEMBER_ADDED_DATE"));
                    }
						preAuthDetailVO.setEligibleNetworks(prs.getString("ELIGIBLE_NETWORKS"));

						preAuthDetailVO.setAssignedTo(prs.getString("ASSIGNED_TO"));
					preAuthDetailVO.setVipYorN(prs.getString("VIP_YN"));
					
					preAuthDetailVO.setDiagnosis(prs.getString("Oraldiagnosis"));
					preAuthDetailVO.setServices(prs.getString("Oralservices"));
					preAuthDetailVO.setOralApprovedAmount(prs.getBigDecimal("OralaAprovedamount"));
					preAuthDetailVO.setRevisedDiagnosis(prs.getString("OralDiagnosisRevised"));
					preAuthDetailVO.setRevisedServices(prs.getString("OralServicesRevised"));
					preAuthDetailVO.setOralRevisedApprovedAmount(prs.getBigDecimal("OralaAprovedAmountRevised"));
					preAuthDetailVO.setOralORsystemStatus(prs.getString("OralORsystemStatus"));
					preAuthDetailVO.setStrHospitalId(prs.getString("hospital_id"));
					preAuthDetailVO.setProviderSpecificRemarks(prs.getString("PROVIDER_SPECIFIC_REMARKS"));	
					preAuthDetailVO.setDhpoUploadStatus(prs.getString("DHPO_UPLOAD_YN"));
					preAuthDetailVO.setAllowOverrideYN(prs.getString("ALLOW_OVERRIDE"));
					preAuthDetailVO.setProvAuthority(prs.getString("PROVIDER_AUTHORITY"));
					preAuthDetailVO.setCount_flag(prs.getInt("count_flag"));
					
					preAuthDetailVO.setCount_flag2(prs.getInt("count_flag2"));
					
					
					preAuthDetailVO.setMemberWeight(prs.getString("patient_weight"));
					preAuthDetailVO.setMemberDOB(prs.getString("mem_dob"));	
						
					if(prs.getString("mem_age_indays")!=null)
					{
						preAuthDetailVO.setPatientdays(prs.getString("mem_age_indays"));
					}
					
					if (prs.getString("approved_date") != null) {
						preAuthDetailVO.setCompleted_date(prs.getString("approved_date"));
					}
					if (prs.getString("pat_mem_exit_date") != null) 
					{
						preAuthDetailVO.setPreMemExitDt(prs.getString("pat_mem_exit_date"));
					}	
					
					String strProErrMsg=providerStatusValidation(preAuthDetailVO.getPreAuthRecvTypeID(),prs.getString("hosp_validity_flag"),prs.getString("emp_stat_count"));
					preAuthDetailVO.setProStatusErrMsg(strProErrMsg);

				
					//provider_payer_id
					  if(prs.getString("provider_payer_id")!=null){
					    	preAuthDetailVO.setProviderSpecifiedPayerId(prs.getString("provider_payer_id"));
					    }
					//provider_payer_name
					    if(prs.getString("provider_payer_name")!=null){
					    	preAuthDetailVO.setProviderSpecifiedPayerName(prs.getString("provider_payer_name"));
					    }
					
					preAuthDetailVO.setProductAuthority(prs.getString("PRODUCT_AUTHORITY"));
					preAuthDetailVO.setPreCronTypeID(prs.getString("CHRONIC_PRE_EXISTING"));
					preAuthDetailVO.setPreOneMedicalCondition(prs.getString("PER_ONE_MEDICAL_CONDITION"));
					    preAuthDetailVO.setOverrideAllowYN(prs.getString("override_allow_yn"));
					  
					if(prs.getString("DHPO_UPLOAD_DATE")!=null){
					    	preAuthDetailVO.setDhpoUploadedDate(prs.getString("DHPO_UPLOAD_DATE"));
					    }
					
					
					  preAuthDetailVO.setNegativeAmtFlg(TTKCommon.checkNull(prs.getString("negtive_null_amt_flag"))); 
						
					if(prs.getString("REFERENCE_NUM")!=null){
				    	preAuthDetailVO.setReferenceNo(prs.getString("REFERENCE_NUM"));
				    }
						
					if(prs.getString("document_status")!=null){
				    	preAuthDetailVO.setDocumentStatus(prs.getString("document_status"));
				    }
					
					if(prs.getString("sum_exc_flag")!=null)
						preAuthDetailVO.setSum_exc_flag(prs.getString("sum_exc_flag"));
						
				/* if(prs.getString("copay_limit_reached_yn1")!=null&&prs.getString("copay_limit_reached_yn1").length()>0) preAuthDetailVO.setCopayLimitReachedActivityYN(prs.getString("copay_limit_reached_yn1"));
						  if(prs.getString("mem_sus_msg")!=null){
						    	preAuthDetailVO.setPolicySuspeMsg(prs.getString("mem_sus_msg"));
						    }   */  
						   preAuthDetailVO.setProcessType(TTKCommon.checkNull(prs.getString("submission_catogory")));
						    preAuthDetailVO.setRequestedAmountcurrencyType(TTKCommon.checkNull(prs.getString("reqamtcurrencytype")));
							preAuthDetailVO.setCurrencyType(prs.getString("conv_currncy_type"));
							preAuthDetailVO.setConversionRate(prs.getString("conversionrate"));
							BigDecimal convrtedFinalAmt = prs.getBigDecimal("converted_fin_app_amt");
							String strConvrtedFinalAmt = String.valueOf(convrtedFinalAmt);
							 if(prs.getBigDecimal("converted_fin_app_amt") == null){
								preAuthDetailVO.setConvertedFinalApprovedAmount("");
						 	}
							else{
								preAuthDetailVO.setConvertedFinalApprovedAmount(strConvrtedFinalAmt);
							}
							preAuthDetailVO.setConvertedAmount(prs.getString("convertedamount"));
							preAuthDetailVO.setPartnerName(prs.getString("partnername"));
						  
							if(prs.getString("onl_pat_auth_seq_id")!=null)
								preAuthDetailVO.setPartnerPreAuthSeqId(prs.getLong("onl_pat_auth_seq_id"));
								
								if(prs.getString("onl_pre_auth_refno")!=null)
								preAuthDetailVO.setPreauthPartnerRefId(prs.getString("onl_pre_auth_refno"));
								
								  
								preAuthDetailVO.setProviderType(prs.getString("provider_sector_type"));
								preAuthDetailVO.setProviderFecilType(prs.getString("provider_facility_type_id"));
								preAuthDetailVO.setIrdrgAltMsg(TTKCommon.checkNull(prs.getString("irdrg_altmsg")));
								
				
						  preAuthDetailVO.setProcessorInternalRemarks(TTKCommon.checkNull(prs.getString("processor_remarks")));
					
					if(prs.getString("SOURCE_TYPE_ID")!=null)
					 {
						 if(("DHP".equals(prs.getString("SOURCE_TYPE_ID")) || "ONL1".equals(prs.getString("SOURCE_TYPE_ID"))) )
						  {
							 preAuthDetailVO.setModeFlag("DISABLE");
						  }
						 if(!("DHP".equals(prs.getString("SOURCE_TYPE_ID")) || "ONL1".equals(prs.getString("SOURCE_TYPE_ID"))))
						  {
							 preAuthDetailVO.setModeFlag("ENABLE");
						  }
							  
					 }
					
					if(prs.getString("count_flag")!=null)
					 {
						if(prs.getInt("count_flag") == 1)
						{	
							preAuthDetailVO.setCntFlag("DIS");
						}
					  if(prs.getInt("count_flag")  == 0 || preAuthDetailVO.getCount_flag() >1)
						{	
						  preAuthDetailVO.setCntFlag("ENA");
						}	
							  
					 }
					
					preAuthDetailVO.setFlagForCheckEnhancement(TTKCommon.checkNull(prs.getString("pat_enhanced_yn")));
					preAuthDetailVO.setInvoiceno(TTKCommon.checkNull(prs.getString("authorization_id")));
					preAuthDetailVO.setPresntComplaints(TTKCommon.checkNull(prs.getString("presenting_complaints")));
					preAuthDetailVO.setDurPresentAilment(prs.getInt("duration_above_illness"));
					preAuthDetailVO.setPastHistoryRelevant(TTKCommon.checkNull(prs.getString("past_history")));
					preAuthDetailVO.setDurPastHistRelevant(prs.getInt("since_when"));
					

				  preAuthDetailVO.setPreAuthCount(prs.getInt("number_of_pre_auth"));
				  preAuthDetailVO.setClmCount(prs.getInt("number_of_clm"));
					
				  if("DHP".equals(preAuthDetailVO.getPreAuthRecvTypeID())&&
						   "Y".equals(preAuthDetailVO.getPreauthCompleteStatus())&&
							!"Y".equals(preAuthDetailVO.getDhpoUploadStatus())&&
							("APR".equals(preAuthDetailVO.getPreauthStatus())||"REJ".equals(preAuthDetailVO.getPreauthStatus()))
									
						){
					  preAuthDetailVO.setDispDhpoBtn("Y");
								
							}
				  preAuthDetailVO.setPrePedDescription(TTKCommon.checkNull(prs.getString("ped_desc")));
				  preAuthDetailVO.setPreMaterinityDescription(TTKCommon.checkNull(prs.getString("maternity_desc")));
				  preAuthDetailVO.setWorkRelatedInjury(TTKCommon.checkNull(prs.getString("work_related_injury_yn")));
				   preAuthDetailVO.setAlcoholIntoxication(TTKCommon.checkNull(prs.getString("alcohol_drug_yn")));
				   preAuthDetailVO.setRoadTrafficAccident(TTKCommon.checkNull(prs.getString("RTA_YN")));
				   if(prs.getString("date_of_injury")!=null){
					   preAuthDetailVO.setDateOfInjury(new Date(prs.getTimestamp("date_of_injury").getTime()));
                  }
				   preAuthDetailVO.setPatientType(TTKCommon.checkNull(prs.getString("patient_type")));
				   preAuthDetailVO.setRelPresentIllness(TTKCommon.checkNull(prs.getString("details_of_past_history")));
				   preAuthDetailVO.setRelevantFindings(TTKCommon.checkNull(prs.getString("relevant_clinical_findings")));
				   if(!TTKCommon.checkNull(prs.getString("mobile_no")).equals("")){
					   String mobile = prs.getString("mobile_no");
					   String[] mobileNo = mobile.split("\\|");
					   if(mobileNo.length<=1){
						   preAuthDetailVO.setMobileNbr(mobileNo[0]);
					   }else{
					   for(int i=0; i<=mobileNo.length;){
						   preAuthDetailVO.setMobileIsdCode(mobileNo[i]);
						   preAuthDetailVO.setMobileNbr(mobileNo[++i]);  
						   break;
					   }
					   }
				   }else{
					   preAuthDetailVO.setMobileNbr("");   
					   preAuthDetailVO.setMobileIsdCode("");
				   }
				}//if (prs.next()) {
			}	//if (prs != null) {
		if(drs!=null){			
		while(drs.next()){	
			String diagCode=drs.getString("DIAGNOSYS_CODE")==null?"":drs.getString("DIAGNOSYS_CODE");
			String desc=drs.getString("ICD_DESCRIPTION")==null?"":drs.getString("ICD_DESCRIPTION");
			String primAil=drs.getString("PRIMARY_AILMENT_YN")==null?"":drs.getString("PRIMARY_AILMENT_YN");
			Long diagSeqId=drs.getLong("DIAG_SEQ_ID");
			Long icdCodeSeqId=drs.getLong("ICD_CODE_SEQ_ID");			
			 String infotype=drs.getString("dx_info_type")==null?"":drs.getString("dx_info_type");
	            String infocode = drs.getString("dx_info_code")==null?"":drs.getString("dx_info_code");
	            DiagnosisDetailsVO diagnosisDetailsVO=     new   DiagnosisDetailsVO(diagCode, desc,primAil,diagSeqId,icdCodeSeqId,infotype,infocode);
	            diagnosisDetailsVO.setPreCronTypeYN(drs.getString("CHRONIC_YN"));
	            diagnosisDetailsVO.setPreCronTypeID(drs.getString("CHRONIC_PRE_EXISTING"));
	            diagnosisDetailsVO.setPreOneMedicalCondition(drs.getString("PER_ONE_MEDICAL_CONDITION"));
	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	            if(drs.getString("first_diagnosed_date")!=null){
		            diagnosisDetailsVO.setFirstDiagnosedDate(dateFormat.format(new Date(drs.getTimestamp("first_diagnosed_date").getTime())));
	            }
	            diagnosis.add(diagnosisDetailsVO);
		   }//while(drs.next()){			
		}//if(drs!=null){
				
		if (ars != null) {
			int sNo = 0;
		while (ars.next()) {

			sNo++;
					String activityCode = ars.getString("CODE") == null ? "": ars.getString("CODE");
					
					String activityTypeCodeDesc = ars.getString("ACTIVITY_TYPE_CODE") == null ? "": ars.getString("ACTIVITY_TYPE_CODE");

					String modifiers = ars.getString("MODIFIER") == null ? "": ars.getString("MODIFIER");

					String unityType = ars.getString("UNIT_TYPE") == null ? "": ars.getString("UNIT_TYPE");

					Float quantity = ars.getFloat("QUANTITY");

					float approvedQuantity = ars.getFloat("APPROVED_QUANTITY");

					String startDate = ars.getDate("START_DATE") == null ? "": new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(ars.getDate("START_DATE").getTime()));

					BigDecimal grossAmt = ars.getBigDecimal("GROSS_AMOUNT") == null ? new BigDecimal(0) : ars.getBigDecimal("GROSS_AMOUNT");

					BigDecimal discount = ars.getBigDecimal("DISCOUNT_AMOUNT") == null ? new BigDecimal(0) : ars.getBigDecimal("DISCOUNT_AMOUNT");

					BigDecimal discountGross = ars.getBigDecimal("DISC_GROSS_AMOUNT") == null ? new BigDecimal(0) : ars.getBigDecimal("DISC_GROSS_AMOUNT");

					BigDecimal patientShare = ars.getBigDecimal("PATIENT_SHARE_AMOUNT") == null ? new BigDecimal(0) : ars.getBigDecimal("PATIENT_SHARE_AMOUNT");

					BigDecimal netAmt = ars.getBigDecimal("NET_AMOUNT") == null ? new BigDecimal(0) : ars.getBigDecimal("NET_AMOUNT");

					BigDecimal approAmt = ars.getBigDecimal("APPROVED_AMT") == null ? new BigDecimal(0) : ars.getBigDecimal("APPROVED_AMT");
					
					BigDecimal disAllowedAmt = ars.getBigDecimal("DIS_ALLOWED_AMOUNT") == null ? new BigDecimal(0) : ars.getBigDecimal("DIS_ALLOWED_AMOUNT");
					String denialDecs = ars.getString("DENIAL_DESC") == null ? "": ars.getString("DENIAL_DESC");// denial_desc

					String denialCodes = ars.getString("DENIAL_CODE") == null ? "": ars.getString("DENIAL_CODE");// denial_desc

					String activityRemarks = ars.getString("REMARKS") == null ? "": ars.getString("REMARKS");

					Long activitySeqId = ars.getLong("ACTIVITY_SEQ_ID");

					Long activityDtlSeqId = ars.getLong("ACTIVITY_DTL_SEQ_ID");

					String activityCodeDec = ars.getString("ACTIVITY_DESCRIPTION") == null ? "": ars.getString("ACTIVITY_DESCRIPTION");// denial_desc
			String preAuthNo	=	"";
					ActivityDetailsVO activityDetailsVO = new ActivityDetailsVO(
							sNo, activityCode,activityTypeCodeDesc,activityCodeDec, modifiers,
							unityType, startDate, activityRemarks,
							denialCodes, denialDecs, quantity,
							approvedQuantity, activitySeqId,
							activityDtlSeqId, grossAmt, discount,
							discountGross, patientShare, netAmt, approAmt,
							disAllowedAmt, preAuthNo);
					
					activityDetailsVO.setOverrideYN(ars.getString("OVERRIDE_YN"));
					activityDetailsVO.setOverrideRemarks(ars.getString("OVERRIDE_REMARKS"));
					activityDetailsVO.setActivityServiceCode(ars.getString("SERVICE_TYPE"));
                    activityDetailsVO.setOverrideAllowYN(ars.getString("OVERRIDE_ALLOWE_YN"));
                   
			
					if ("PKD".equals(ars.getString("SERVICE_TYPE"))) {
			            activityDetailsVO.setActivityServiceCode("Package");
						activityDetailsVO.setActivityServiceCodeDesc("Package");
					   } else {
						activityDetailsVO.setActivityServiceCode(TTKCommon.checkNull(ars.getString("SERVICE_CODE")));
						activityDetailsVO.setActivityServiceCodeDesc(TTKCommon.checkNull(ars.getString("SERVICE_DESCRIPTION")));
			         }
                     activityDetailsVO.setActivityId(TTKCommon.checkNull(ars.getString("ACTIVITY_ID")));
                     activityDetailsVO.setObservationFlag(ars.getString("OB_YN"));            
				    activityDetailsVO.setPreauthRequestAmt(ars.getBigDecimal("provider_net_amount") == null ? new BigDecimal(0) : ars.getBigDecimal("provider_net_amount"));    
                         
			activities.add(activityDetailsVO);
	}//while (ars.next()) {
	}//if (ars != null) {
    if (shrs != null) {
				shortfalls = new ArrayList<String[]>();
				while (shrs.next()) {
					
					String preauthSeqId = shrs.getLong("PAT_AUTH_SEQ_ID") == 0 ? "": shrs.getLong("PAT_AUTH_SEQ_ID") + "";
				String shortFallSeqId = shrs.getLong("SHORTFALL_SEQ_ID") == 0 ? "" : shrs.getLong("SHORTFALL_SEQ_ID") + "";
				String shortFallNo = shrs.getString("SHORTFALL_ID") == null ? "": shrs.getString("SHORTFALL_ID");
				String shortFallsType = shrs.getString("SHORTFALL_TYPE") == null ? "": shrs.getString("SHORTFALL_TYPE");
				String shortFallsStatus = shrs.getString("SRTFLL_STATUS_GENERAL_TYPE_ID") == null ? "": shrs.getString("SRTFLL_STATUS_GENERAL_TYPE_ID");
				String sendDate = shrs.getString("SRTFLL_SENT_DATE") == null ? "": shrs.getString("SRTFLL_SENT_DATE");
				shortfalls.add(new String[] { preauthSeqId,
						shortFallSeqId, shortFallNo, shortFallsType,
						shortFallsStatus, sendDate });
			}//while (shrs.next()) {
		}//if (shrs != null) {
				
				
				
				preAuthDetailVO.setDiagnosisList(diagnosis);
				preAuthDetailVO.setActivityList(activities);
				
				preAuthDetailVO.setShortfallList(shortfalls);

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (prs != null)
						prs.close();
					if (drs != null)
						drs.close();
					if (ars != null)
						ars.close();
					if (shrs != null)
						shrs.close();
					
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getPreAuthDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getPreAuthDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getPreAuthDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				prs = null;
				drs = null;
				ars = null;
				shrs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally

		return preAuthDetailVO;
	}// end of getPreAuthDetails(long lngPreAuthSeqID,long lngUserSeqID,String
		// strSelectionType)

	/**
	 * This method returns the ShortfallVO, which contains all the
	 * Pre-Authorization/Claims Shortfall Details
	 * 
	 * @param alShortfallList
	 *            object contains SeqID,Preauth/ClaimSeqID to fetch the
	 *            Pre-Authorization/Claims Shortfall Details
	 * @return ShortfallVO object which contains all the Pre-Authorization
	 *         Shortfall/Claims Details
     * @exception throws TTKException
     */
	public ShortfallVO getShortfallDetail(ArrayList alShortfallList)
			throws TTKException {
        ShortfallVO shortfallVO = null;
        Document doc = null;
        XMLType xml = null;
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
		try {
            conn = ResourceManager.getConnection();
			stmt =conn.prepareCall(strShortfallDetails);
			stmt.setLong(1, (Long) alShortfallList.get(0));

			if (alShortfallList.get(1) != null) {
				stmt.setLong(2, (Long) alShortfallList.get(1));
			}// end of if(alShortfallList.get(1) != null)
			else {
				stmt.setLong(2, 0);
			}// end of else

			if (alShortfallList.get(2) != null) {
				stmt.setLong(3, (Long) alShortfallList.get(2));
			}// end of if(alShortfallList.get(2) != null)
			else {
				stmt.setLong(3, 0);
			}// end of else

			stmt.setLong(4, (Long) alShortfallList.get(3));
			stmt.registerOutParameter(5, Types.OTHER);

            stmt.execute();
			rs = (ResultSet) stmt.getObject(5);
			conn.commit();

			if (rs != null) {
				while (rs.next()) {
                    shortfallVO = new ShortfallVO();

					if (rs.getString("SHORTFALL_SEQ_ID") != null) {
						shortfallVO.setShortfallSeqID(new Long(rs
								.getLong("SHORTFALL_SEQ_ID")));
					}// end of if(rs.getString("SHORTFALL_SEQ_ID") != null)
					shortfallVO.setShortfallNo(TTKCommon.checkNull(rs
							.getString("SHORTFALL_ID")));

					if (rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null) {
						shortfallVO.setPreAuthSeqID(new Long(rs
								.getLong("PAT_GEN_DETAIL_SEQ_ID")));
					}// end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null)

					shortfallVO.setShortfallTypeID(TTKCommon.checkNull(rs
							.getString("SRTFLL_GENERAL_TYPE_ID")));
					shortfallVO.setStatusTypeID(TTKCommon.checkNull(rs
							.getString("SRTFLL_STATUS_GENERAL_TYPE_ID")));

					/*
					 * if(rs.getString("SRTFLL_SENT_DATE") != null){
					 * shortfallVO.setSentDate(new
					 * java.util.Date(rs.getTimestamp
					 * ("SRTFLL_SENT_DATE").getTime()));
					 * shortfallVO.setSentTime(
					 * TTKCommon.getFormattedDateHour(new
					 * Date(rs.getTimestamp("SRTFLL_SENT_DATE"
					 * ).getTime())).split(" ").length >=3 ?
					 * TTKCommon.getFormattedDateHour(new
					 * Date(rs.getTimestamp("SRTFLL_SENT_DATE"
					 * ).getTime())).split(" ")[1]:"");
					 * shortfallVO.setSentDay(TTKCommon.getFormattedDateHour(new
					 * Date
					 * (rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split
					 * (" ").length >=3 ? TTKCommon.getFormattedDateHour(new
					 * Date
					 * (rs.getTimestamp("SRTFLL_SENT_DATE").getTime())).split
					 * (" ")[2]:""); }//end of
					 * if(rs.getString("SRTFLL_SENT_DATE") != null)
					 */
					if (rs.getString("SRTFLL_RECEIVED_DATE") != null) {
						shortfallVO
								.setReceivedDate(new java.util.Date(rs
										.getTimestamp("SRTFLL_RECEIVED_DATE")
										.getTime()));
					}// end of if(rs.getString("SRTFLL_RECEIVED_DATE") != null)

					if (rs.getString("SRTFLL_RECEIVED_DATE") != null) {
						shortfallVO.setReceivedDate(new Date(rs.getTimestamp(
								"SRTFLL_RECEIVED_DATE").getTime()));
						shortfallVO
								.setReceivedTime(TTKCommon
										.getFormattedDateHour(
												new Date(rs.getTimestamp(
														"SRTFLL_RECEIVED_DATE")
														.getTime())).split(" ").length >= 3 ? TTKCommon
										.getFormattedDateHour(
												new Date(rs.getTimestamp(
														"SRTFLL_RECEIVED_DATE")
														.getTime())).split(" ")[1]
										: "");
						shortfallVO
								.setReceivedDay(TTKCommon.getFormattedDateHour(
										new Date(rs.getTimestamp(
												"SRTFLL_RECEIVED_DATE")
												.getTime())).split(" ").length >= 3 ? TTKCommon
										.getFormattedDateHour(
												new Date(rs.getTimestamp(
														"SRTFLL_RECEIVED_DATE")
														.getTime())).split(" ")[2]
										: "");
					}// end of if(rs.getString("SRTFLL_RECEIVED_DATE") != null)

					shortfallVO.setRemarks(TTKCommon.checkNull(rs
							.getString("REMARKS")));
					shortfallVO.setReasonTypeID(TTKCommon.checkNull(rs
							.getString("SRTFLL_REASON_GENERAL_TYPE_ID")));
					// shortfallVO.setDMSRefID(TTKCommon.checkNull(rs.getString("PRE_AUTH_DMS_REFERENCE_ID")));

					/*
					 * if(rs.getString("SRTFLL_REMINDER_LOG_SEQ_ID") != null){
					 * shortfallVO.setReminderLogSeqID(new
					 * Long(rs.getLong("SRTFLL_REMINDER_LOG_SEQ_ID"))); }//end
					 * of if(rs.getString("SRTFLL_REMINDER_LOG_SEQ_ID") != null)
                    */
					if (rs.getString("REMINDER_COUNT") != null) {
						shortfallVO.setCorrespondenceCount(new Integer(rs
								.getInt("REMINDER_COUNT")));
					}// end of if(rs.getString("REMINDER_COUNT") != null)

					if (rs.getString("CORRESPONDENCE_DATE") != null) {
						shortfallVO
								.setCorrespondenceDate(new Date(rs
										.getTimestamp("CORRESPONDENCE_DATE")
										.getTime()));
						shortfallVO
								.setCorrespondenceTime(TTKCommon
										.getFormattedDateHour(
												new Date(rs.getTimestamp(
														"CORRESPONDENCE_DATE")
														.getTime())).split(" ").length >= 3 ? TTKCommon
										.getFormattedDateHour(
												new Date(rs.getTimestamp(
														"CORRESPONDENCE_DATE")
														.getTime())).split(" ")[1]
										: "");
						shortfallVO
								.setCorrespondenceDay(TTKCommon
										.getFormattedDateHour(
												new Date(rs.getTimestamp(
														"CORRESPONDENCE_DATE")
														.getTime())).split(" ").length >= 3 ? TTKCommon
										.getFormattedDateHour(
												new Date(rs.getTimestamp(
														"CORRESPONDENCE_DATE")
														.getTime())).split(" ")[2]
										: "");
					}// end of if(rs.getString("CORRESPONDENCE_DATE") != null)

					if (rs.getSQLXML("SHORTFALL_QUESTIONS") != null) {
						SAXReader saxReader=new SAXReader();
						
                        shortfallVO.setShortfallQuestions(saxReader.read(rs.getSQLXML("SHORTFALL_QUESTIONS").getCharacterStream()));
                  }// End of if(rs.getOPAQUE("SHORTFALL_QUESTIONS")

					shortfallVO.setEditYN(TTKCommon.checkNull(rs
							.getString("EDIT_YN")));
					shortfallVO.setShortfallDesc(TTKCommon.checkNull(rs
							.getString("SHORTFALL_TYPE")));
					// added for Mail-SMS Template for Cigna
					// shortfallVO.setCignaYN(TTKCommon.checkNull(rs.getString("CIGNA_YN")));
					// added new Implementation for Member claim check
					// shortfallVO.setMemberClaimYN(TTKCommon.checkNull(rs.getString("MEM_CLM")));
					// end
					// Shortfall CR KOC1179
					shortfallVO.setShortfallTemplateType(TTKCommon.checkNull(rs
							.getString("SHORTFALL_RAISED_FOR")));
					shortfallVO.setShortfallUnderClause(rs
							.getLong("CLAUSE_SEQ_ID"));
					shortfallVO.setClause(TTKCommon.checkNull(rs
							.getString("ADD_CLAUSE_NUMBER")));
					shortfallVO.setCurrentShortfallStatus(TTKCommon
							.checkNull(rs.getString("SHORTFAL_EMAIL_STATUS")));
                    shortfallVO.setPreAuthNo(rs.getString("PRE_AUTH_NUMBER"));
                    shortfallVO.setPreAuthSeqID(rs.getLong("PAT_AUTH_SEQ_ID"));
                    shortfallVO.setEnrollmentID(rs.getString("MEM_ID"));
                    shortfallVO.setClaimantName(rs.getString("MEM_NAME"));
                    shortfallVO.setRecievedStatus(rs.getString("RECIEVED_YN"));
                    // End Shortfall CR KOC1179
				}// end of while(rs.next())
			}// end of if(rs != null)
            return shortfallVO;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getShortfallDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{

					try {
						if (stmt != null)
							stmt.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in PreAuthDAOImpl getShortfallDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getShortfallDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				stmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getShortfallDetail(ArrayList alShortfallList)

    /**
     * This method saves the Pre-Authorization Shortfall information
	 * 
	 * @param shortfallVO
	 *            ShortfallVO contains Pre-Authorization Shortfall information
     * @param strSelectionType
     * @return long value which contains Shortfall Seq ID
     * @exception throws TTKException
     */
	public long saveShortfall(ShortfallVO shortfallVO, String strSelectionType)
			throws TTKException {
        long lngShortfallSeqID = 0;
        Connection conn = null;
        CallableStatement stmt = null;
		try {
            conn = ResourceManager.getConnection();
			stmt =conn.prepareCall(strSaveShortfall);

			if (shortfallVO.getShortfallSeqID() != null) {
				stmt.setLong(1, shortfallVO.getShortfallSeqID());
			}// end of if(shortfallVO.getShortfallSeqID() != null)
			else {
				stmt.setLong(1, 0);
			}// end of else

			if (shortfallVO.getPreAuthSeqID() != null) {
				stmt.setLong(2, shortfallVO.getPreAuthSeqID());
			}// end of if(shortfallVO.getPreAuthSeqID() != null)
			else {
				stmt.setLong(2, 0);
			}// end of else

			if (shortfallVO.getClaimSeqID() != null) {
				stmt.setLong(3, shortfallVO.getClaimSeqID());
			}// end of if(shortfallVO.getClaimSeqID() != null)
			else {
				stmt.setLong(3, 0);
			}// end of else

			stmt.setString(4, shortfallVO.getShortfallTypeID());
			stmt.setString(5, shortfallVO.getStatusTypeID());
			stmt.setString(6, shortfallVO.getReasonTypeID());
			stmt.setString(7, shortfallVO.getRemarks());
			if (shortfallVO.getReceivedDate() != null) {
				stmt.setTimestamp(8,new Timestamp(TTKCommon.getOracleDateWithTime(
								shortfallVO.getPATReceivedDate(),
								shortfallVO.getReceivedTime(),
								shortfallVO.getReceivedDay()).getTime()));
			}// end of if(shortfallVO.getReceivedDate() != null)
			else {
                stmt.setTimestamp(8, null);
			}// end of else
			SQLXML sqlXml=conn.createSQLXML();
			if (shortfallVO.getShortfallQuestions() != null) {
				sqlXml.setString(shortfallVO.getShortfallQuestions().asXML());
				stmt.setSQLXML(9, sqlXml);
			}// end of if(shortfallVO.getShortfallQuestions() != null)

			else stmt.setSQLXML(9, sqlXml);

			if (shortfallVO.getCorrespondenceDate() != null) {
				stmt.setTimestamp(10,new Timestamp(TTKCommon.getOracleDateWithTime(
								shortfallVO.getPATCorrespondenceDate(),
								shortfallVO.getCorrespondenceTime(),
								shortfallVO.getCorrespondenceDay()).getTime()));
			}// end of if(shortfallVO.getCorrespondenceDate() != null)
			else {
                stmt.setTimestamp(10, null);
			}// end of else

			stmt.setString(11, shortfallVO.getCorrespondenceYN());

			/*
			 * if(shortfallVO.getReminderLogSeqID() != null){
			 * stmt.setLong(12,shortfallVO.getReminderLogSeqID()); }//end of
			 * if(shortfallVO.getReminderLogSeqID() != null) else{
			 * stmt.setString(12,null); }//end of else
			 */
			stmt.setString(12, strSelectionType);

			if (shortfallVO.getActiveLink().equals("Claims")) {
				if (shortfallVO.getShortfallTemplateType().equals("")|| shortfallVO.getShortfallTemplateType() == null) {
					stmt.setString(13, "OLD");
				} else {
					stmt.setString(13, shortfallVO.getShortfallTemplateType());
            	}
				
			} else if (shortfallVO.getActiveLink().equals("Pre-Authorization")) {
				stmt.setString(13, "OLD");
               // stmt.setString(15,null);
				// .setString(16,"");
            }

			stmt.setLong(14, shortfallVO.getUpdatedBy());
			stmt.setString(15, shortfallVO.getRecievedStatus());
			stmt.registerOutParameter(1, Types.BIGINT);
			stmt.registerOutParameter(11, Types.VARCHAR);
			stmt.registerOutParameter(16, Types.INTEGER);
            stmt.execute();
            lngShortfallSeqID = stmt.getLong(1);
            conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (stmt != null)
						stmt.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl saveShortfall()",
							sqlExp);
        			throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl saveShortfall()",
								sqlExp);
        				throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		stmt = null;
        		conn = null;
			}// end of finally
		}// end of finally
        return lngShortfallSeqID;
	}// end of saveShortfall(ShortfallVO shortfallVO,String strSelectionType)

	public Object[] saveObservationDetails(ObservationDetailsVO observationDetailsVO) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet ors = null;
		ArrayList<String[]> observations = new ArrayList<String[]>();
		Object[] results = new Object[3];
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveObservations);
			if (observationDetailsVO.getObservSeqId() == null)
				cStmtObject.setLong(1, 0);
			else
				cStmtObject.setLong(1, observationDetailsVO.getObservSeqId());
			cStmtObject.setLong(2, observationDetailsVO.getActivityDtlSeqId());
			cStmtObject.setString(3, observationDetailsVO.getAuthType());
			if ("PAT".equals(observationDetailsVO.getAuthType()))
			cStmtObject.setLong(4, observationDetailsVO.getPreAuthSeqID());
			else if ("CLM".equals(observationDetailsVO.getAuthType()))
			cStmtObject.setLong(4, observationDetailsVO.getClaimSeqID());
			cStmtObject.setString(5, observationDetailsVO.getObservType());
			cStmtObject.setString(6, observationDetailsVO.getObservCode());
			cStmtObject.setString(7, observationDetailsVO.getObservValue());
			cStmtObject.setString(8, observationDetailsVO.getObservValueType());
			cStmtObject.setLong(9, observationDetailsVO.getAddedBy());
			cStmtObject.setString(10, observationDetailsVO.getObservRemarks());
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.registerOutParameter(11, Types.INTEGER);
			cStmtObject.execute();
			results[0] = cStmtObject.getLong(1);
			results[1] = cStmtObject.getInt(11);
			conn.commit();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetAllObservations);
			cStmtObject.setLong(1, observationDetailsVO.getActivityDtlSeqId());
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ
																// Details

			if (ors != null) {
				while (ors.next()) {
					String observSeqId = ors.getLong("OBSERVATION_SEQ_ID") == 0 ? ""
							: ors.getLong("OBSERVATION_SEQ_ID") + "";
					String activityDtlSeqId = ors
							.getLong("ACTIVITY_DTL_SEQ_ID") == 0 ? "" : ors
							.getLong("ACTIVITY_DTL_SEQ_ID") + "";
					String observTypeDesc = ors.getString("TYPE_DESC") == null ? ""
							: ors.getString("TYPE_DESC");
					String observCodeDesc = ors.getString("CODE_DESC") == null ? ""
							: ors.getString("CODE_DESC");
					String observValue = ors.getString("VALUE") == null ? ""
							: ors.getString("VALUE");
					String observValueTypeDesc = ors
							.getString("VALUE_TYPE_DESC") == null ? "" : ors
							.getString("VALUE_TYPE_DESC");
					String observRemarks = ors.getString("REMARKS") == null ? ""
							: ors.getString("REMARKS");
					observations.add(new String[] { observSeqId,
							activityDtlSeqId, observTypeDesc, observCodeDesc,
							observValue, observValueTypeDesc, observRemarks });
			}
			}
			results[2] = observations;
			return results;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (ors != null)
						ors.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl saveObservationDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl saveObservationDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl saveObservationDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ors = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of saveObservationDetails(long lngPreAuthSeqID,long
		// lngUserSeqID,String strSelectionType)

	public ObservationDetailsVO getObservDetail(Long observSeqId)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet ors = null;
		ObservationDetailsVO observationDetailsVO = new ObservationDetailsVO();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetObservation);
			cStmtObject.setLong(1, observSeqId);
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ Details

			if (ors != null) {
				while (ors.next()) {
					observationDetailsVO.setObservSeqId(ors
							.getLong("OBSERVATION_SEQ_ID"));
					observationDetailsVO.setActivityDtlSeqId(ors
							.getLong("ACTIVITY_DTL_SEQ_ID"));
				observationDetailsVO.setObservType(ors.getString("TYPE"));
				observationDetailsVO.setObservCode(ors.getString("CODE"));
					observationDetailsVO.setObservCodeDesc(ors
							.getString("CODE_DESC"));
				observationDetailsVO.setObservValue(ors.getString("VALUE"));
					observationDetailsVO.setObservValueType(ors
							.getString("VALUE_TYPE_ID"));
					observationDetailsVO.setObservValueTypeDesc(ors
							.getString("VALUE_TYPE_DESC"));
					observationDetailsVO.setObservRemarks(ors
							.getString("REMARKS"));
			}
			}

			return observationDetailsVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (ors != null)
						ors.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getObservDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getObservDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getObservDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ors = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getObservDetails(long lngPreAuthSeqID,long lngUserSeqID,String
		// strSelectionType)

	public Object[] getObservTypeDetails(String observType) throws TTKException {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		HashMap<String, String> observCodes = new HashMap<String, String>();
		HashMap<String, String> observValueTypes = new HashMap<String, String>();
		Object[] result = new Object[2];
		try {
			observType = (observType == null) ? "" : observType.trim();
		     conn = ResourceManager.getConnection();
			statement = conn
					.prepareStatement("SELECT S.OBSERVATION_CODE_ID,S.OBSERVATION_CODE FROM APP.TPA_OBSERVATION_VALUE_CODES S where s.observation_type_id='"
							+ observType + "'");
			resultSet = statement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next())
					observCodes.put(resultSet.getString(1),
							resultSet.getString(2));
		    }
			statement = conn
					.prepareStatement("select C.OBS_VALUE_TYPE_CODE_ID,C.VALUE_TYPE from app.TPA_OBSER_VALUE_TYPE_CODES C WHERE C.OBSERVATION_TYPE_ID='"
							+ observType + "'");
			resultSet = statement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next())
					observValueTypes.put(resultSet.getString(1),
							resultSet.getString(2));
			    }
			result[0] = observCodes;
			result[1] = observValueTypes;
			return result;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (resultSet != null)
						resultSet.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getObservTypeDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{

					try {
						if (statement != null)
							statement.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in PreAuthDAOImpl getObservTypeDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getObservTypeDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				resultSet = null;
				statement = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getObservTypeDetails(long lngPreAuthSeqID,long
		// lngUserSeqID,String strSelectionType)

	public long deleteObservDetails(Long peauthSeqId, String listOfobsvrSeqIds,
			String mode) throws TTKException {
			Connection conn = null;
		CallableStatement cStmtObject = null;
			long updatedRows;
		try {
				conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strDeleteObservations);
			cStmtObject.setString(1, listOfobsvrSeqIds);
			cStmtObject.setLong(2, peauthSeqId);
			cStmtObject.setString(3, mode);		
			cStmtObject.registerOutParameter(4, Types.INTEGER);
				cStmtObject.execute();
				updatedRows = cStmtObject.getInt(4);
				conn.commit();

				return updatedRows;
		}// end of try
		catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
				throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
				try // First try closing the result set
				{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl deleteObservDetails()",
							sqlExp);
							throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl deleteObservDetails()",
								sqlExp);
								throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
				{
					cStmtObject = null;
					conn = null;
			}// end of finally
		}// end of finally
	}// end of deleteObservDetails(Long peauthSeqId,String
		// listOfobsvrSeqIds,String mode)

	public ArrayList<String[]> getAllObservDetails(Long activityDtlSeqId1)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet ors = null;
		ArrayList<String[]> observations = new ArrayList<String[]>();

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetAllObservations);
			cStmtObject.setLong(1, activityDtlSeqId1);
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ
																// Details

			if (ors != null) {
				while (ors.next()) {
					String observSeqId = ors.getLong("OBSERVATION_SEQ_ID") == 0 ? ""
							: ors.getLong("OBSERVATION_SEQ_ID") + "";
					String activityDtlSeqId = ors
							.getLong("ACTIVITY_DTL_SEQ_ID") == 0 ? "" : ors
							.getLong("ACTIVITY_DTL_SEQ_ID") + "";
					String observTypeDesc = ors.getString("TYPE_DESC") == null ? ""
							: ors.getString("TYPE_DESC");
					String observCodeDesc = ors.getString("CODE_DESC") == null ? ""
							: ors.getString("CODE_DESC");
					String observValue = ors.getString("VALUE") == null ? ""
							: ors.getString("VALUE");
					String observValueTypeDesc = ors
							.getString("VALUE_TYPE_DESC") == null ? "" : ors
							.getString("VALUE_TYPE_DESC");
					String observRemarks = ors.getString("REMARKS") == null ? ""
							: ors.getString("REMARKS");
					observations.add(new String[] { observSeqId,
							activityDtlSeqId, observTypeDesc, observCodeDesc,
							observValue, observValueTypeDesc, observRemarks });
			}
			}

			return observations;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (ors != null)
						ors.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getAllObservDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getAllObservDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getAllObservDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ors = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getAllObservDetails(long lngPreAuthSeqID,long lngUserSeqID,String
		// strSelectionType)

	public ArrayList<String[]> getDiagnosisDetails(Long preAuthSeqId)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ArrayList<String[]> listOfDiagnosis = new ArrayList<String[]>();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strGetDiagnosisDetails);
			cStmtObject.setLong(1, preAuthSeqId);
			cStmtObject.setString(2, "PAT");
			cStmtObject.registerOutParameter(3, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(3);
			if (rs != null) {
				while (rs.next()) {
					String icdCode = rs.getString("DIAGNOSYS_CODE") == null ? ""
							: rs.getString("DIAGNOSYS_CODE");
					String icdDesc = rs.getString("ICD_DESCRIPTION") == null ? ""
							: rs.getString("ICD_DESCRIPTION");
					String prime = rs.getString("PRIMARY_AILMENT_YN") == null ? ""
							: rs.getString("PRIMARY_AILMENT_YN");
					String icdSeqId = rs.getLong("ICD_CODE_SEQ_ID") == 0 ? ""
							: rs.getLong("ICD_CODE_SEQ_ID") + "";
					listOfDiagnosis.add(new String[] { icdCode, icdDesc, prime,
							icdSeqId });
			}
			}
			return listOfDiagnosis;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getDiagnosisDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getDiagnosisDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getDiagnosisDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getDiagnosisDetails(long lngPreAuthSeqID)

	public DiagnosisDetailsVO getIcdCodeDetails(String icdCode, long seqId,
			String authType) throws TTKException {
			Connection conn = null;
		CallableStatement cStmtObject = null;
			ResultSet rs = null;
		DiagnosisDetailsVO diagnosisDetailsVO = new DiagnosisDetailsVO();
		try {
				conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strGetIcdDetails);
			cStmtObject.setString(1, icdCode);
			cStmtObject.setLong(2, seqId);
			cStmtObject.setString(3, authType);
			cStmtObject.registerOutParameter(4, Types.OTHER);
				cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(4);
			if (rs != null) {
				while (rs.next()) {
					diagnosisDetailsVO.setIcdCodeSeqId(rs
							.getLong("ICD_CODE_SEQ_ID"));
					diagnosisDetailsVO.setAilmentDescription(rs
							.getString("ICD_DESCRIPTION"));
					diagnosisDetailsVO.setPrimaryAilment(rs
							.getString("PRIMARY"));
					diagnosisDetailsVO.setPreCronTypeYN(rs.getString("CHRONIC_YN"));
				}
			}
				return diagnosisDetailsVO;
		}// end of try
		catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
				throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getIcdCodeDetails()",
							sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getIcdCodeDetails()",
								sqlExp);
							throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getIcdCodeDetails()",
									sqlExp);
								throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
					throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
				{
					rs = null;
					cStmtObject = null;
					conn = null;
			}// end of finally
		}// end of finally
	}// end of getDiagnosisDetails(String icdCode,long lngPreAuthSeqID)

	public DiagnosisDetailsVO getDiagnosis(DiagnosisDetailsVO diagnosisDetailsVO)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		DiagnosisDetailsVO diagnosisDetailsVO2 = new DiagnosisDetailsVO();
		ResultSet rs = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strGetDiagnosis);
			cStmtObject.setLong(1, diagnosisDetailsVO.getPreAuthSeqID());
			cStmtObject.setLong(2, diagnosisDetailsVO.getDiagSeqId());
			cStmtObject.setString(3, "PAT");
			cStmtObject.registerOutParameter(4, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(4);
			if (rs != null) {
				while (rs.next()) {
					String icdCode = rs.getString("DIAGNOSYS_CODE");
					String icdDesc = rs.getString("ICD_DESCRIPTION");
					String prime = rs.getString("PRIMARY_AILMENT_YN");
					Long icdSeqId = rs.getLong("ICD_CODE_SEQ_ID");
					Long diagSeqId = rs.getLong("diag_seq_id");
					diagnosisDetailsVO2 = new DiagnosisDetailsVO(prime,
							icdDesc, icdCode, icdSeqId, null, null, null,
							diagSeqId);
			}
			}
			return diagnosisDetailsVO2;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getDiagnosis()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getDiagnosis()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getDiagnosis()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getDiagnosis(long lngPreAuthSeqID,long lngUserSeqID,String
		// strSelectionType)

	/**
	 * This method saves the Pre-Authorization information
	 * 
	 * @param preAuthDetailVO
	 *            PreAuthDetailVO contains Pre-Authorization information
	 * @param strSelectionType
	 *            String object which contains Selection Type - 'PAT' or 'ICO'
	 * @return Object[] the values,of MEMBER_SEQ_ID , PAT_GEN_DETAIL_SEQ_ID and
	 *         PAT_ENROLL_DETAIL_SEQ_ID
	 * @exception throws TTKException
	 */
	public String[] addPreauthDetails(PreAuthDetailVO preAuthDetailVO,
			String uploadedBy) throws TTKException {
		String[] objArrayResult = new String[3];
		long lngMemberSeqID = 0;
		long lngPreAuthSeqID = 0;
		long lngPATEnrollDtlSeqID = 0;
		AdditionalDetailVO additionalDetailVO = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			additionalDetailVO = preAuthDetailVO.getAdditionalDetailVO();
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAddPreAuthorizationDetails);
			cStmtObject.registerOutParameter(1, Types.BIGINT);// cStmtObject.setString(,"");
			cStmtObject.setString(2, "");
			cStmtObject.setString(3, "");
			cStmtObject.setString(4, "");
			cStmtObject.setString(5, "");

			if (preAuthDetailVO.getEnrollDtlSeqID() != null) {
				cStmtObject.setLong(PAT_ENROLL_DETAIL_SEQ_ID,
						preAuthDetailVO.getEnrollDtlSeqID());
			}// end of if(preAuthDetailVO.getEnrollDtlSeqID() != null)
			else {
				cStmtObject.setLong(PAT_ENROLL_DETAIL_SEQ_ID, 0);
			}// end of else

			if (preAuthDetailVO.getClaimantVO().getMemberSeqID() != null) {
				cStmtObject.setLong(MEMBER_SEQ_ID, preAuthDetailVO
						.getClaimantVO().getMemberSeqID());
			}// end of if(preAuthDetailVO.getClaimantVO().getMemberSeqID() !=
				// null)
			else {
				cStmtObject.setLong(MEMBER_SEQ_ID, 0);
			}// end of else

			cStmtObject.setString(TPA_ENROLLMENT_ID, preAuthDetailVO
					.getClaimantVO().getEnrollmentID());
			cStmtObject.setString(POLICY_NUMBER, preAuthDetailVO
					.getClaimantVO().getPolicyNbr());

			if (preAuthDetailVO.getClaimantVO().getPolicySeqID() != null) {
				cStmtObject.setLong(POLICY_SEQ_ID, preAuthDetailVO
						.getClaimantVO().getPolicySeqID());
			}// end of if(preAuthDetailVO.getClaimantVO().getPolicySeqID() !=
				// null)
			else {
				cStmtObject.setString(POLICY_SEQ_ID, null);
			}// end of else

			if (preAuthDetailVO.getClaimantVO().getInsSeqID() != null) {
				cStmtObject.setLong(INS_SEQ_ID, preAuthDetailVO.getClaimantVO()
						.getInsSeqID());
			}// end of if(preAuthDetailVO.getClaimantVO().getInsSeqID() != null)
			else {
				cStmtObject.setString(INS_SEQ_ID, null);
			}// end of else

			cStmtObject.setString(GENDER_GENERAL_TYPE_ID, preAuthDetailVO
					.getClaimantVO().getGenderTypeID());
			cStmtObject.setString(CLAIMANT_NAME, preAuthDetailVO
					.getClaimantVO().getName());

			if (preAuthDetailVO.getClaimantVO().getAge() != null) {
				cStmtObject.setInt(MEM_AGE, preAuthDetailVO.getClaimantVO()
						.getAge());
			}// end of if(preAuthDetailVO.getClaimantVO().getAge() != null)
			else {
				cStmtObject.setString(MEM_AGE, null);
			}// end of else

			if (preAuthDetailVO.getClaimantVO().getDateOfInception() != null) {
				cStmtObject.setTimestamp(DATE_OF_INCEPTION, new Timestamp(
						preAuthDetailVO.getClaimantVO().getDateOfInception()
								.getTime()));
			}// end of if(preAuthDetailVO.getClaimantVO().getDateOfInception()
				// != null)
			else {
				cStmtObject.setTimestamp(DATE_OF_INCEPTION, null);
			}// end of else

			if (preAuthDetailVO.getClaimantVO().getDateOfExit() != null) {
				cStmtObject.setTimestamp(DATE_OF_EXIT, new Timestamp(
						preAuthDetailVO.getClaimantVO().getDateOfExit()
								.getTime()));
			}// end of if(preAuthDetailVO.getClaimantVO().getDateOfExit() !=
				// null)
			else {
				cStmtObject.setTimestamp(DATE_OF_EXIT, null);
			}// end of else

			if (preAuthDetailVO.getClaimantVO().getTotalSumInsured() != null) {
				cStmtObject.setBigDecimal(MEM_TOTAL_SUM_INSURED,
						preAuthDetailVO.getClaimantVO().getTotalSumInsured());
			}// end of if(preAuthDetailVO.getClaimantVO().getTotalSumInsured()
				// != null)
			else {
				cStmtObject.setString(MEM_TOTAL_SUM_INSURED, null);
			}// end of else

			cStmtObject.setString(CATEGORY_GENERAL_TYPE_ID, preAuthDetailVO
					.getClaimantVO().getCategoryTypeID());
			cStmtObject.setString(INSURED_NAME, preAuthDetailVO.getClaimantVO()
					.getPolicyHolderName());
			cStmtObject.setString(PHONE_1, preAuthDetailVO.getClaimantVO()
					.getPhone());

			if (preAuthDetailVO.getClaimantVO().getStartDate() != null) {
				cStmtObject.setTimestamp(POLICY_EFFECTIVE_FROM, new Timestamp(
						preAuthDetailVO.getClaimantVO().getStartDate()
								.getTime()));
			}// end of if(preAuthDetailVO.getClaimantVO().getStartDate() !=
				// null)
			else {
				cStmtObject.setTimestamp(POLICY_EFFECTIVE_FROM, null);
			}// end of else

			if (preAuthDetailVO.getClaimantVO().getEndDate() != null) {
				cStmtObject
						.setTimestamp(POLICY_EFFECTIVE_TO, new Timestamp(
								preAuthDetailVO.getClaimantVO().getEndDate()
										.getTime()));
			}// end of if(preAuthDetailVO.getClaimantVO().getEndDate() != null)
			else {
				cStmtObject.setTimestamp(POLICY_EFFECTIVE_TO, null);
			}// end of else

			if (preAuthDetailVO.getClaimantVO().getProductSeqID() != null) {
				cStmtObject.setLong(PRODUCT_SEQ_ID, preAuthDetailVO
						.getClaimantVO().getProductSeqID());
			}// end of if(preAuthDetailVO.getClaimantVO().getProductSeqID() !=
				// null)
			else {
				cStmtObject.setString(PRODUCT_SEQ_ID, null);
			}// end of else

			cStmtObject.setString(INS_STATUS_GENERAL_TYPE_ID, preAuthDetailVO
					.getClaimantVO().getTermStatusID());
			cStmtObject.setString(ENROL_TYPE_ID, preAuthDetailVO
					.getClaimantVO().getPolicyTypeID());
			cStmtObject.setString(POLICY_SUB_GENERAL_TYPE_ID, preAuthDetailVO
					.getClaimantVO().getPolicySubTypeID());

			if (preAuthDetailVO.getClaimantVO().getGroupRegnSeqID() != null) {
				cStmtObject.setLong(GROUP_REG_SEQ_ID, preAuthDetailVO
						.getClaimantVO().getGroupRegnSeqID());
			}// end of if(preAuthDetailVO.getClaimantVO().getGroupRegSeqID() !=
				// null)
			else {
				cStmtObject.setString(GROUP_REG_SEQ_ID, null);
			}// end of else

			if (preAuthDetailVO.getAdmissionDate() != null) {

				cStmtObject.setTimestamp(
						DATE_OF_HOSPITALIZATION,
						new Timestamp(TTKCommon.getOracleDateWithTime(
								preAuthDetailVO.getHospitalAdmissionDate(),
								preAuthDetailVO.getAdmissionTime(),
								preAuthDetailVO.getAdmissionDay()).getTime()));

			}// end of if(preAuthDetailVO.getAdmissionDate() != null)
			else {
				cStmtObject.setTimestamp(DATE_OF_HOSPITALIZATION, null);
			}// end of else

			if (preAuthDetailVO.getOfficeSeqID() != null) {
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,
						preAuthDetailVO.getOfficeSeqID());
			}// end of if(preAuthDetailVO.getOfficeSeqID() != null)
			else {
				cStmtObject.setString(TPA_OFFICE_SEQ_ID, null);
			}// end of else

			if (preAuthDetailVO.getPreAuthHospitalVO().getHospSeqId() != null) {
				cStmtObject.setLong(HOSP_SEQ_ID, preAuthDetailVO
						.getPreAuthHospitalVO().getHospSeqId());
			}// end of if(preAuthDetailVO.getPreAuthHospitalVO().getHospSeqId()
				// != null)
			else {
				cStmtObject.setString(HOSP_SEQ_ID, null);
			}// end of else

			cStmtObject.setString(PAT_STATUS_GENERAL_TYPE_ID,
					preAuthDetailVO.getStatusTypeID());

			if (preAuthDetailVO.getPreAuthSeqID() != null) {
				cStmtObject.setLong(PAT_GEN_DETAIL_SEQ_ID,
						preAuthDetailVO.getPreAuthSeqID());
			} else {
				cStmtObject.setLong(PAT_GEN_DETAIL_SEQ_ID, 0);
			}

			cStmtObject.setString(PAT_GENERAL_TYPE_ID,
					preAuthDetailVO.getPreAuthTypeID());
			cStmtObject.setString(PAT_PRIORITY_GENERAL_TYPE_ID,
					preAuthDetailVO.getPriorityTypeID());

			if (preAuthDetailVO.getReceivedDate() != null) {
				cStmtObject.setTimestamp(
						PAT_RECEIVED_DATE,
						new Timestamp(TTKCommon.getOracleDateWithTime(
								preAuthDetailVO.getPATReceivedDate(),
								preAuthDetailVO.getReceivedTime(),
								preAuthDetailVO.getReceivedDay()).getTime()));
			}// end of if(preAuthDetailVO.getReceivedDate() != null)
			else {
				cStmtObject.setTimestamp(PAT_RECEIVED_DATE, null);
			}// end of else

			if (preAuthDetailVO.getPrevApprovedAmount() != null) {
				cStmtObject.setBigDecimal(PREV_APPROVED_AMOUNT,
						preAuthDetailVO.getPrevApprovedAmount());
			}// end of if(preAuthDetailVO.getPrevApprovedAmount() != null)
			else {
				cStmtObject.setString(PREV_APPROVED_AMOUNT, null);
			}// end of else

			/*
			 * if(preAuthDetailVO.getRequestAmount() != null){
			 * cStmtObject.setBigDecimal
			 * (PAT_REQUESTED_AMOUNT,preAuthDetailVO.getRequestAmount()); }//end
			 * of if(preAuthDetailVO.getRequestAmount() != null) else{
			 * cStmtObject.setString(PAT_REQUESTED_AMOUNT,null); }//end of else
			 */
			cStmtObject.setString(TREATING_DR_NAME,
					preAuthDetailVO.getDoctorName());
			cStmtObject.setString(PAT_RCVD_THRU_GENERAL_TYPE_ID,
					preAuthDetailVO.getPreAuthRecvTypeID());
			cStmtObject.setString(PHONE_NO_IN_HOSPITALISATION,
					preAuthDetailVO.getHospitalPhone());
			cStmtObject.setString(DISCREPANCY_PRESENT_YN,
					preAuthDetailVO.getDiscPresentYN());
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl addPreauthDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl addPreauthDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return objArrayResult;
	}// end of addPreauthDetails(PreAuthDetailVO preAuthDetailVO,String
		// strSelectionType)

	public Object[] saveActivityDetails(ActivityDetailsVO activityDetailsVO)
			throws TTKException {
	Object[] objArrayResult = new Object[2];

	Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveActivityDetails);
			if (activityDetailsVO.getActivityDtlSeqId() == null)
				cStmtObject.setLong(1, 0);
			else
				cStmtObject.setLong(1, activityDetailsVO.getActivityDtlSeqId());
			if ("PAT".equals(activityDetailsVO.getAuthType())) {
				cStmtObject.setLong(2, activityDetailsVO.getPreAuthSeqID());
				cStmtObject.setLong(3, 0);
			} else if ("CLM".equals(activityDetailsVO.getAuthType())) {
				cStmtObject.setLong(2, 0);
				cStmtObject.setLong(3, activityDetailsVO.getClaimSeqID());
		}
		
		cStmtObject.setString(4,null);
		if(activityDetailsVO.getActivityCodeSeqId()==null)cStmtObject.setLong(5,0);
		else cStmtObject.setLong(5,activityDetailsVO.getActivityCodeSeqId());
		cStmtObject.setDate(6,new java.sql.Date(activityDetailsVO.getActivityStartDate().getTime()));
		cStmtObject.setString(7,activityDetailsVO.getActivityTypeId());
		cStmtObject.setString(8,activityDetailsVO.getActivityCode());
		cStmtObject.setString(9,activityDetailsVO.getUnitType());
		cStmtObject.setString(10,activityDetailsVO.getModifier());
		cStmtObject.setString(11,activityDetailsVO.getInternalCode());
		cStmtObject.setString(12,activityDetailsVO.getPackageId());//cStmtObject.setString(,);
		cStmtObject.setString(13,activityDetailsVO.getBundleId());
		
		if(activityDetailsVO.getQuantity()==null)cStmtObject.setDouble(14,0.0);
		else cStmtObject.setDouble(14,activityDetailsVO.getQuantity());
		
		cStmtObject.setDouble(15,activityDetailsVO.getGrossAmount().doubleValue());
		cStmtObject.setDouble(16,activityDetailsVO.getDiscount().doubleValue());
		cStmtObject.setDouble(17,activityDetailsVO.getDiscountedGross().doubleValue());
		cStmtObject.setDouble(18,activityDetailsVO.getPatientShare().doubleValue());
		cStmtObject.setDouble(19,activityDetailsVO.getCopay().doubleValue());
		cStmtObject.setDouble(20,activityDetailsVO.getCoinsurance().doubleValue());
		cStmtObject.setDouble(21,activityDetailsVO.getDeductible().doubleValue());
		cStmtObject.setDouble(22,activityDetailsVO.getOutOfPocket().doubleValue());
		cStmtObject.setDouble(23,activityDetailsVO.getNetAmount().doubleValue());
		
		if(activityDetailsVO.getAllowedAmount()==null)cStmtObject.setDouble(24,0.0);
		else cStmtObject.setDouble(24,activityDetailsVO.getAllowedAmount().doubleValue());
		if(activityDetailsVO.getApprovedAmount()==null)cStmtObject.setDouble(25,0.0);
		else cStmtObject.setDouble(25,activityDetailsVO.getApprovedAmount().doubleValue());
		
		cStmtObject.setString(26,activityDetailsVO.getAmountAllowed());
		cStmtObject.setString(27,activityDetailsVO.getDenialCode());

		cStmtObject.setString(28,activityDetailsVO.getActivityRemarks());
		cStmtObject.setLong(29,activityDetailsVO.getAddedBy());
		
		if(activityDetailsVO.getApprovedQuantity()==null)cStmtObject.setDouble(30,0.0);		
		else cStmtObject.setDouble(30,activityDetailsVO.getApprovedQuantity());
		
		cStmtObject.setDouble(31,activityDetailsVO.getUnitPrice().doubleValue());
		cStmtObject.setString(32,activityDetailsVO.getClinicianId());
		cStmtObject.setString(33,activityDetailsVO.getOverrideYN());
		cStmtObject.setString(34,activityDetailsVO.getOverrideRemarks());
		cStmtObject.setString(35,activityDetailsVO.getActivityStatus());
		
		if(activityDetailsVO.getMedicationDays()==null)cStmtObject.setLong(36,0);
		else cStmtObject.setLong(36,activityDetailsVO.getMedicationDays());
		
		if(!"".equals(activityDetailsVO.getPosology()))	cStmtObject.setLong(37,Long.parseLong(activityDetailsVO.getPosology()));
		else cStmtObject.setLong(37,0);
		
		cStmtObject.setDouble(38,activityDetailsVO.getProviderRequestedAmt().doubleValue());
		cStmtObject.setString(39,activityDetailsVO.getDenialDescription());
		cStmtObject.setString(40,activityDetailsVO.getActivityServiceType());
		cStmtObject.setString(41,activityDetailsVO.getActivityServiceCode());		
		cStmtObject.setDouble(42, activityDetailsVO.getNonNetworkCopay().doubleValue());
		cStmtObject.registerOutParameter(43,Types.INTEGER);
		cStmtObject.setDouble(44,activityDetailsVO.getAdditionalDisallowances().doubleValue());
		if(TTKCommon.checkNull(activityDetailsVO.getConvertedUnitPrice()).length()>0)
		cStmtObject.setDouble(45, Double.parseDouble(activityDetailsVO.getConvertedUnitPrice()));
		else cStmtObject.setDouble(45,0.0);
		
		 if(TTKCommon.checkNull(activityDetailsVO.getsCategory()).length()>0)
		cStmtObject.setLong(46, Long.parseLong(activityDetailsVO.getsCategory()));
         else cStmtObject.setLong(46, 0);
		
		if(activityDetailsVO.getVatPercent()!=null)
		cStmtObject.setDouble(47, activityDetailsVO.getVatPercent());
		else cStmtObject.setDouble(47, 0.0);
		
		if( activityDetailsVO.getVatAed()!=null)
		cStmtObject.setDouble(48, activityDetailsVO.getVatAed());
		else cStmtObject.setDouble(48, 0.0);
		
		if( activityDetailsVO.getVatAddedReqAmnt()!=null)
		cStmtObject.setDouble(49, activityDetailsVO.getVatAddedReqAmnt());
		else 
		cStmtObject.setDouble(49, 0.0);
		if(activityDetailsVO.getAppliedVatPercent()!=null)
		cStmtObject.setDouble(50, activityDetailsVO.getAppliedVatPercent());
		else cStmtObject.setDouble(50, 0.0);
		
		if(activityDetailsVO.getAppliedVatAed()!=null)
		cStmtObject.setDouble(51, activityDetailsVO.getAppliedVatAed());
		else  cStmtObject.setDouble(51, 0.0);		
		
		cStmtObject.setString(52, activityDetailsVO.getAppliedVatAEDPER());
		
		if(activityDetailsVO.getUcr()!=null)
		cStmtObject.setDouble(53,activityDetailsVO.getUcr().doubleValue());
		else 	cStmtObject.setDouble(53,0.0);
		
		cStmtObject.setString(54,activityDetailsVO.getDenialRemarks());
		cStmtObject.setString(55,activityDetailsVO.getVBCDescription());
		cStmtObject.registerOutParameter(1,Types.BIGINT);
		cStmtObject.execute();
			objArrayResult[0] = cStmtObject.getInt(43);
			objArrayResult[1]=cStmtObject.getLong(1);
			conn.commit();
			// return null;
		}// end of try
		catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
		throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
		try // First try closing the Statement
		{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl saveActivityDetails()",
							sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl saveActivityDetails()",
								sqlExp);
					throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
		{
			cStmtObject = null;
			conn = null;
			}// end of finally
		}// end of finally
	return objArrayResult;
}//end of saveActivityDetails(PreAuthDetailVO preAuthDetailVO,String strSelectionType)
	
	public ActivityDetailsVO getActivityDetails(Long activityDtlSeqId) throws TTKException {
		
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet ars = null;
		ActivityDetailsVO activityDetailsVO = new ActivityDetailsVO();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetActivityDetails);
			
			cStmtObject.setLong(1, activityDtlSeqId);
			// cStmtObject.setString(2,"PAT");
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			ars = (java.sql.ResultSet) cStmtObject.getObject(2);// activity
																// Details
			if (ars != null) {
				if (ars.next()) {
					 activityDetailsVO.setIrdrgWarnMsg(ars.getString("IRDRG_ALERT"));
					activityDetailsVO.setActivityId(ars
							.getString("ACTIVITY_ID"));
					activityDetailsVO.setActivityTypeId(ars
							.getString("ACTIVITY_TYPE"));
					activityDetailsVO.setPreAuthNo(ars
							.getString("PRE_AUTH_NUMBER"));
					activityDetailsVO.setPreAuthSeqID(ars
							.getLong("PAT_AUTH_SEQ_ID"));
				activityDetailsVO.setClaimNo(ars.getString("CLAIM_NUMBER"));
					activityDetailsVO
							.setClaimSeqID(ars.getLong("CLAIM_SEQ_ID"));
					activityDetailsVO.setActivityDtlSeqId(ars
							.getLong("ACTIVITY_DTL_SEQ_ID"));
					if (ars.getString("ACTIVITY_SEQ_ID") != null) {
						activityDetailsVO.setActivitySeqId(ars
								.getLong("ACTIVITY_SEQ_ID"));
						activityDetailsVO.setActivityCodeSeqId(ars
								.getLong("ACTIVITY_SEQ_ID"));
					} else {
					activityDetailsVO.setActivitySeqId(null);
					activityDetailsVO.setActivityCodeSeqId(null);
				}

					activityDetailsVO.setClinicianId(ars
							.getString("CLINICIAN_ID"));
					activityDetailsVO
							.setSerialNo(ars.getInt("S_NO") == 0 ? null : ars
									.getInt("S_NO"));
					activityDetailsVO.setActivityCode(ars
							.getString("ACTIVITY_CODE") == null ? "" : ars
							.getString("ACTIVITY_CODE"));
					activityDetailsVO.setActivityCodeDesc(ars
							.getString("ACTIVITY_DESCRIPTION") == null ? ""
							: ars.getString("ACTIVITY_DESCRIPTION"));
					activityDetailsVO.setCopay(ars
							.getBigDecimal("PROVIDER_COPAY"));
					activityDetailsVO.setModifier(ars.getString("MODIFIER"));
					activityDetailsVO.setCoinsurance(ars
							.getBigDecimal("CO_INS_AMOUNT"));
				activityDetailsVO.setUnitType(ars.getString("UNIT_TYPE"));
					activityDetailsVO.setDeductible(ars
							.getBigDecimal("DEDUCT_AMOUNT"));
				activityDetailsVO.setQuantity(ars.getFloat("QUANTITY"));
					activityDetailsVO.setApprovedQuantity(ars
							.getFloat("APPROVED_QUANTITY"));
					activityDetailsVO.setOutOfPocket(ars
							.getBigDecimal("OUT_OF_POCKET_AMOUNT"));
					activityDetailsVO.setActivityStartDate(ars
							.getDate("START_DATE"));// StartDate(ars.getDate("START_DATE")==null?"":new
													// SimpleDateFormat("dd/MM/yyyy").format(new
													// java.util.Date(ars.getDate("START_DATE").getTime())));
					activityDetailsVO.setPatientShare(ars
							.getBigDecimal("PATIENT_SHARE_AMOUNT"));
				activityDetailsVO.setUnitPrice(ars.getBigDecimal("UNIT_PRICE"));
					activityDetailsVO.setGrossAmount(ars
							.getBigDecimal("GROSS_AMOUNT"));
					activityDetailsVO.setNetAmount(ars
							.getBigDecimal("NET_AMOUNT"));
					activityDetailsVO.setApprovedAmount(ars
							.getBigDecimal("APPROVED_AMT"));
					activityDetailsVO.setAllowedAmount(ars
							.getBigDecimal("ALLOWED_AMOUNT"));
					activityDetailsVO.setDiscount(ars
							.getBigDecimal("DISCOUNT_AMOUNT"));
					activityDetailsVO.setDiscountPerUnit(ars
							.getBigDecimal("UNIT_DISCOUNT"));
					activityDetailsVO.setDiscountedGross(ars
							.getBigDecimal("DISC_GROSS_AMOUNT"));
				activityDetailsVO.setPackageId(ars.getString("PACKAGE_ID"));
				activityDetailsVO.setBundleId(ars.getString("BUNDLE_ID"));
				activityDetailsVO.setInternalCode(ars.getString("INTERNAL_CODE"));
				activityDetailsVO.setActivityRemarks(ars.getString("REMARKS"));
				activityDetailsVO.setDenialCode(ars.getString("DENIAL_CODE"));
				activityDetailsVO.setDenialDescription(ars.getString("DENIAL_DESC"));
				activityDetailsVO.setAmountAllowed(ars.getString("ALLOW_YN"));
				activityDetailsVO.setTariffYN(ars.getString("TARIFF_YN"));
					activityDetailsVO.setClinicianId(ars
							.getString("CLINICIAN_ID"));
					activityDetailsVO.setClinicianName(ars
							.getString("CLINICIAN_NAME"));
					activityDetailsVO.setRcopay(ars
							.getBigDecimal("COPAY_AMOUNT"));
					activityDetailsVO.setRoutOfPocket(ars
							.getBigDecimal("RULE_OUTOF_POCKET"));
					activityDetailsVO.setRcoinsurance(ars
							.getBigDecimal("RULE_COINSURENCE"));
					activityDetailsVO.setRdeductible(ars
							.getBigDecimal("RULE_DEDUCTIBLE"));
					activityDetailsVO.setRdisAllowedAmount(ars
							.getBigDecimal("DIS_ALLOWED_AMOUNT"));
					activityDetailsVO.setOverrideYN(ars
							.getString("OVERRIDE_YN"));
					activityDetailsVO.setOverrideRemarks(ars
							.getString("OVERRIDE_REMARKS"));
					activityDetailsVO.setActivityStatus(ars
							.getString("APPROVE_YN"));
					activityDetailsVO.setMedicationDays(ars
							.getInt("POSOLOGY_DURATION"));
				activityDetailsVO.setPosology(ars.getString("POSOLOGY"));
					activityDetailsVO.setProviderRequestedAmt(ars
							.getBigDecimal("provider_net_amount"));
					if (ars.getBigDecimal("ri_copar") != null) {
						activityDetailsVO.setRicopar(ars
								.getBigDecimal("ri_copar"));
				}
					if (ars.getBigDecimal("ucr") != null) {
				activityDetailsVO.setUcr(ars.getBigDecimal("ucr"));
				}

					activityDetailsVO.setActivityServiceType(ars
							.getString("SERVICE_TYPE"));
					activityDetailsVO.setActivityServiceCode(ars
							.getString("SERVICE_CODE"));
					activityDetailsVO.setDenialRemarks(ars
							.getString("denial_res"));
					activityDetailsVO.setNonNetworkCopay(ars
							.getBigDecimal("non_network_co_pay"));

					activityDetailsVO.setAdditionalDisallowances(ars
							.getBigDecimal("addtional_dis_amount"));

					activityDetailsVO.setHaad_YN(TTKCommon.checkNull(ars
							.getString("haad_yn")));
					activityDetailsVO.setEdit_YN(TTKCommon.checkNull(ars
							.getString("edit_yn")));

					String editStatus = "N";

					if ("Y".equals(ars.getString("haad_yn"))
							&& "Y".equals(ars.getString("edit_yn"))) {
						editStatus = "Y";
					}
					if ("Y".equals(ars.getString("TARIFF_YN"))) {
						editStatus = "N";
					}
				
					if ("N".equals(ars.getString("TARIFF_YN"))) {
						if ("Y".equals(ars.getString("haad_yn"))&& "Y".equals(ars.getString("edit_yn"))) {
							editStatus = "N";
						}
				
				
					}
					activityDetailsVO.setConvertedUnitPrice(ars.getString("converted_acitivty_amt"));
					activityDetailsVO.setEditStatus(editStatus);
                    activityDetailsVO.setProviderAreCopay(ars.getBigDecimal("LOCATION_COPAY"));

                    activityDetailsVO.setsCategory(ars.getString("CATEGORY_TYPE_SEQ_ID"));

                    activityDetailsVO.setConvertedProviderReqAmt(ars.getBigDecimal("converted_provider_net_amount")==null?new BigDecimal(0):ars.getBigDecimal("converted_provider_net_amount"));  
                    activityDetailsVO.setAppliedCopay(ars.getBigDecimal("APPLIED_COPAY"));
                    activityDetailsVO.setMohPatientShareErrMsg(ars.getString("hmo_copay_limit_reached"));
                    activityDetailsVO.setCondDenialCode(TTKCommon.checkNull(ars.getString("conditional_denial_code")));
                    
                    if(ars.getBigDecimal("irdrg_baserate") != null){
                    activityDetailsVO.setBaseRate(ars.getBigDecimal("irdrg_baserate"));
                    }
                    if(ars.getBigDecimal("irdrg_negot_factor") != null){
                    activityDetailsVO.setNegotiationFactor(ars.getBigDecimal("irdrg_negot_factor"));
                    }
                    if(ars.getBigDecimal("irdrg_relative_weight") != null){
                    	activityDetailsVO.setIrdrgRelativeWt(ars.getBigDecimal("irdrg_relative_weight").toString());
                    }
                                        	
                    
                    String paymentProcesType ;
                    if("Y".equals(ars.getString("irdrg_inlier_yn")))
                    	paymentProcesType = "Inlier Payment";
                    else
                    	paymentProcesType = "Outlier Payment";
                    activityDetailsVO.setIrdrgPymntProcess(paymentProcesType);
                    
                    activityDetailsVO.setIrdrgInlierFlagYN(TTKCommon.checkNull(ars.getString("irdrg_inlier_yn")));
                    
                    if(ars.getBigDecimal("irdrg_thresh_hold_limit") != null){
                    activityDetailsVO.setOutlierPmntThresholdLmt(ars.getBigDecimal("irdrg_thresh_hold_limit"));
                    }
                    
                    if(ars.getBigDecimal("irdrg_margin") != null){
                    activityDetailsVO.setMargin(ars.getBigDecimal("irdrg_margin"));
                    }
                    
                    if(ars.getBigDecimal("avg_activity_cost") != null){
                    activityDetailsVO.setAvgActivityCost(ars.getBigDecimal("avg_activity_cost"));
                    }
                    
                    activityDetailsVO.setIrdrgProcessYN(TTKCommon.checkNull(ars.getString("irdrg_process_yn")));
				}
                    //added for vat
                    activityDetailsVO.setVatPercent(ars.getDouble("vat_Percent"));
                    activityDetailsVO.setVatAed(ars.getDouble("VAT_AED"));
                    activityDetailsVO.setVatAddedReqAmnt(ars.getDouble("PROVIDER_REQ_AMT_VAT_ADDED"));
                    
                    
                    activityDetailsVO.setAppliedVatPercent(ars.getDouble("APPLIED_VAT_PER"));
                    activityDetailsVO.setAppliedVatAed(ars.getDouble("APPLIED_VAT_AED"));
                    activityDetailsVO.setVatAddedApprAmnt(ars.getDouble("APPR_AMT_VAT_ADDED"));
                    activityDetailsVO.setAppliedVatAEDPER(ars.getString("APPLIED_VAT_flag"));
                    activityDetailsVO.setVBCDescription(ars.getString("vbc_description"));
                    
			}
			return activityDetailsVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (ars != null)
						ars.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getActivityDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getActivityDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getActivityDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ars = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getActivityDetails(Long activtiDtlSeqId)

	public BigDecimal[] getCalculatedPreauthAmount(Long preauthSeqId,
			Long hospitalSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		BigDecimal[] calculateAmunts = new BigDecimal[7];
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetCalculatePreauthAmount);

			cStmtObject.setLong(1, preauthSeqId);
			cStmtObject.setLong(2, hospitalSeqID);
			cStmtObject.registerOutParameter(3, Types.DECIMAL);
			cStmtObject.registerOutParameter(4, Types.OTHER);
			cStmtObject.registerOutParameter(5, Types.BIGINT);
			cStmtObject.execute();
			conn.commit();
			// calculateAmunts[0]= cStmtObject.getBigDecimal(3);;
			rs = (java.sql.ResultSet) cStmtObject.getObject(4);

			if (rs != null) {
				while (rs.next()) {
					calculateAmunts[0] = rs.getBigDecimal("TOT_GROSS_AMOUNT");
					calculateAmunts[1] = rs
							.getBigDecimal("TOT_DISCOUNT_AMOUNT");
					calculateAmunts[2] = rs
							.getBigDecimal("TOT_DISC_GROSS_AMOUNT");
					calculateAmunts[3] = rs
							.getBigDecimal("TOT_PATIENT_SHARE_AMOUNT");
					calculateAmunts[4] = rs.getBigDecimal("TOT_NET_AMOUNT");
					calculateAmunts[5] = rs.getBigDecimal("TOT_ALLOWED_AMOUNT");
					calculateAmunts[6] = rs
							.getBigDecimal("TOT_APPROVED_AMOUNT");
			}
			}
			return calculateAmunts;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getCalculatedPreauthAmount()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getCalculatedPreauthAmount()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getCalculatedPreauthAmount()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getCalculatedPreauthAmount(Long preauthSeqId)

	public Object[] saveAndCompletePreauth(PreAuthDetailVO preAuthDetailVO)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		Object[] rowProcess = new Object[1];
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strPreAuthAmountApproved);
			cStmtObject.setLong(1, preAuthDetailVO.getPreAuthSeqID());
			if (preAuthDetailVO.getMemberSeqID() == null)cStmtObject.setLong(2, 0);
			else cStmtObject.setLong(2, preAuthDetailVO.getMemberSeqID());
			cStmtObject.setString(3, preAuthDetailVO.getAuthNum());
			cStmtObject.setTimestamp(4,new Timestamp(TTKCommon.getOracleDateWithTime(new SimpleDateFormat("dd/MM/yyyy").format(preAuthDetailVO.getAdmissionDate()),preAuthDetailVO.getAdmissionTime(),preAuthDetailVO.getAdmissionDay()).getTime()));
			cStmtObject.setBigDecimal(5, preAuthDetailVO.getApprovedAmount());
			cStmtObject.setString(6, preAuthDetailVO.getPreAuthRecvTypeID());
			cStmtObject.setString(7, preAuthDetailVO.getPreauthStatus());
			cStmtObject.setString(8, preAuthDetailVO.getMedicalOpinionRemarks());
			cStmtObject.setLong(9, preAuthDetailVO.getAddedBy());
			cStmtObject.registerOutParameter(10, Types.INTEGER);
			cStmtObject.setString(11, preAuthDetailVO.getDenialCode());
			cStmtObject.setString(12, preAuthDetailVO.getProcessorInternalRemarks());
			cStmtObject.registerOutParameter(3, Types.VARCHAR);
			cStmtObject.registerOutParameter(7, Types.VARCHAR);
			cStmtObject.execute();
			rowProcess[0] = cStmtObject.getInt(10);
			conn.commit();
			return rowProcess;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl saveAndCompletePreauth()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl saveAndCompletePreauth()",
								sqlExp);

						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl saveAndCompletePreauth()",
									sqlExp);

							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of saveAndCompletePreauth(Long preauthSeqId)

	public Map<String,String> getOtpResult(String memberId,Long addedBy,String otpNumber){
		Connection con=null;
		CallableStatement statement=null,statement2=null;
		ResultSet resultSet=null;
		Map<String,String> holdResult=new HashMap<String, String>();
	  try{	 
	     con=ResourceManager.getConnection();     
	     statement=con.prepareCall("{CALL AUTHORIZATION_PKG.SELECT_MEMBER(?,?,?)}");    
	     statement.setString(1,memberId);
	     statement.registerOutParameter(2,OracleTypes.CURSOR);
	     statement.registerOutParameter(3,OracleTypes.INTEGER);
		statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
		 int memcount= statement.getInt(3);
			if (resultSet == null || !resultSet.next()
					|| resultSet.getLong("MEMBER_SEQ_ID") == 0) {
				holdResult.put("MEMBER_SEQ_ID", null);
			} else {
				String memberSeqId = TTKCommon.checkNull(
						resultSet.getLong("MEMBER_SEQ_ID")).toString();
				holdResult.put("MEMBER_SEQ_ID",
						TTKCommon.checkNull(memberSeqId));
				holdResult.put("TPA_ENROLLMENT_ID", TTKCommon
						.checkNull(resultSet.getString("TPA_ENROLLMENT_ID")));
				holdResult.put("MEM_NAME",
						TTKCommon.checkNull(resultSet.getString("MEM_NAME")));
				holdResult.put("MEM_AGE",
						TTKCommon.checkNull(resultSet.getString("MEM_AGE")));
				holdResult.put("GENDER",
						TTKCommon.checkNull(resultSet.getString("GENDER")));
				holdResult.put("EMIRATE_ID",
						TTKCommon.checkNull(resultSet.getString("EMIRATE_ID")));
				holdResult.put("INS_SEQ_ID",
						TTKCommon.checkNull(resultSet.getLong("INS_SEQ_ID"))
								.toString());
				holdResult.put("INS_COMP_NAME", TTKCommon.checkNull(resultSet
						.getString("INS_COMP_NAME")));
				holdResult.put("PAYER_ID", TTKCommon.checkNull(TTKCommon
						.checkNull(resultSet.getString("PAYER_ID"))));
				holdResult.put("POLICY_SEQ_ID",
						TTKCommon.checkNull(resultSet.getLong("POLICY_SEQ_ID"))
								.toString());
				holdResult.put("POLICY_NUMBER", TTKCommon.checkNull(resultSet
						.getString("POLICY_NUMBER")));
				holdResult.put("CORPORATE_NAME", TTKCommon.checkNull(resultSet
						.getString("CORPORATE_NAME")));
				holdResult.put(
						"POLICY_START_DATE",
						TTKCommon.convertDateAsString("dd/MM/yyyy",
								resultSet.getDate("POLICY_START_DATE")));
				holdResult.put(
						"POLICY_END_DATE",
						TTKCommon.convertDateAsString("dd/MM/yyyy",
								resultSet.getDate("POLICY_END_DATE")));
				holdResult
						.put("NATIONALITY", TTKCommon.checkNull(resultSet
								.getString("NATIONALITY")));
				holdResult
						.put("SUM_INSURED", TTKCommon.checkNull(resultSet
								.getString("SUM_INSURED")));
				holdResult.put("AVA_SUM_INSURED", TTKCommon.checkNull(resultSet
						.getString("AVA_SUM_INSURED")));
				holdResult.put("PRODUCT_NAME", TTKCommon.checkNull(resultSet
						.getString("PRODUCT_NAME")));
				holdResult.put("PAYER_AUTHORITY", TTKCommon.checkNull(resultSet
						.getString("PAYER_AUTHORITY")));
				holdResult.put("VIP_YN",
						TTKCommon.checkNull(resultSet.getString("VIP_YN")));
				holdResult.put("CLASSIFICATION", TTKCommon.checkNull(resultSet
						.getString("CLASSIFICATION")));

				holdResult.put("CLM_MEMB_INCP_DT", TTKCommon
						.checkNull(resultSet.getString("clm_mem_insp_date")));

				statement2 = con
						.prepareCall("{CALL PAT_XML_LOAD_PKG.VALIDATE_MEM_OTP(?,?,?,?)}");
				statement2.setString(1, memberSeqId);
			 statement2.setString(2, otpNumber);
				statement2.setLong(3, addedBy);
				statement2.registerOutParameter(4, Types.VARCHAR);// possible
																	// values Y
																	// or N
			 statement2.execute();
				String validationStatus = statement2.getString(4);
				holdResult.put("validationStatus", validationStatus);
		   }
		} catch (Exception exception) {
			holdResult = null;
			log.error("Error  in PreAuthDAOImpl getOtpResult()", exception);
			exception.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (statement2 != null)
					statement2.close();
				if (con != null)
					con.close();
			} catch (Exception exception2) {
				log.error(
						"Error while closing the Connection/Statement in PreAuthDAOImpl getOtpResult()",
						exception2);
				exception2.printStackTrace();
		}
		}
		return holdResult;
	}// end of getCalculatedPreauthAmount(Long preauthSeqId)

	public Map<String, String> getEncounterTypes(String benefitId) {

		Connection con = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		Map<String, String> encounterTypes = new LinkedHashMap<String, String>();
		try {
			con = ResourceManager.getConnection();
			statement = con.prepareCall(strEncounterTypes);
			statement.setString(1, benefitId);
			statement.registerOutParameter(2, Types.OTHER);
		statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			if (resultSet != null) {
				while (resultSet.next())
					encounterTypes.put(resultSet.getString("ENCOUNTER_SEQ_ID"),
							resultSet.getString("DESCRIPTION"));
		   }
		} catch (Exception exception) {
			log.error("Error  in PreAuthDAOImpl getEncounterTypes()", exception);
			exception.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (con != null)
					con.close();
			} catch (Exception exception2) {
				log.error(
						"Error while closing the Connection/Statement in PreAuthDAOImpl getEncounterTypes()",
						exception2);
				exception2.printStackTrace();
		}
		}
		return encounterTypes;
	}// end of getEncounterTypes(Long preauthSeqId)

	public Object[] savePreAuth(PreAuthDetailVO preAuthDetailVO)
			throws TTKException {
	Object[] objArrayResult = new Object[3];
	Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddPreAuthorizationDetails);
		
		if (preAuthDetailVO.getPreAuthSeqID()!=null) {			
			cStmtObject.setLong(1, preAuthDetailVO.getPreAuthSeqID());
			
			}else{
				cStmtObject.setLong(1,0);
			}
	
			cStmtObject.setLong(2,preAuthDetailVO.getParentPreAuthSeqID()==null?0:preAuthDetailVO.getParentPreAuthSeqID());
			cStmtObject.setTimestamp(3,new Timestamp(TTKCommon.getOracleDateWithTime(preAuthDetailVO.getReceiveDate(),preAuthDetailVO.getReceiveTime(),preAuthDetailVO.getReceiveDay()).getTime()));
		  if(preAuthDetailVO.getDischargeDate()!=null)cStmtObject.setTimestamp(4,new Timestamp(TTKCommon.getOracleDateWithTime(preAuthDetailVO.getDischargeDate(),preAuthDetailVO.getDischargeTime(),preAuthDetailVO.getDischargeDay()).getTime()));
		  else   cStmtObject.setDate(4,null);
		   cStmtObject.setString(5,preAuthDetailVO.getPreAuthRecvTypeID());		 
		   cStmtObject.setTimestamp(6,new Timestamp(TTKCommon.getOracleDateWithTime(preAuthDetailVO.getHospitalAdmissionDate(),preAuthDetailVO.getAdmissionTime(),preAuthDetailVO.getAdmissionDay()).getTime()));
		  
		   if(preAuthDetailVO.getMemberSeqID()!=null){
			   cStmtObject.setLong(7,preAuthDetailVO.getMemberSeqID());
			 }else{
				 cStmtObject.setLong(7,0);
			 }

				cStmtObject.setString(8, preAuthDetailVO.getMemberId());
				cStmtObject.setString(9, preAuthDetailVO.getPreAuthNo());
				cStmtObject.setString(10, preAuthDetailVO.getAuthNum());
				cStmtObject.setString(11, preAuthDetailVO.getPatientName());

				if (preAuthDetailVO.getMemberAge() == null) {
					cStmtObject.setInt(12, 0);
				} else {
					cStmtObject.setInt(12, preAuthDetailVO.getMemberAge());
		   }

				if (preAuthDetailVO.getInsSeqId() != null) {
					cStmtObject.setLong(13, preAuthDetailVO.getInsSeqId());
				 } else {
					cStmtObject.setLong(13, 0);
		        }

				if (preAuthDetailVO.getProviderSeqId() != null) {
					cStmtObject.setLong(14, preAuthDetailVO.getProviderSeqId());
				 } else {
					cStmtObject.setLong(14, 0);
		        }

				if (preAuthDetailVO.getPolicySeqId() != null) {
					cStmtObject.setLong(15, preAuthDetailVO.getPolicySeqId());
				} else {
					cStmtObject.setLong(15, 0);
			   }
		   
		   cStmtObject.setString(16,preAuthDetailVO.getEmirateId());
		   cStmtObject.setByte(17,new Byte(preAuthDetailVO.getEncounterTypeId()));
		   cStmtObject.setString(18,preAuthDetailVO.getEncounterFacilityId());
		   
		   String encStartTypeId=preAuthDetailVO.getEncounterStartTypeId();
		   String encEndTypeId=preAuthDetailVO.getEncounterEndTypeId();
		   
		   if(encStartTypeId!=null&&!"".equals(encStartTypeId))
		   cStmtObject.setByte(19,new Byte(preAuthDetailVO.getEncounterStartTypeId()));
		   else cStmtObject.setByte(19,(byte)0);
		   
		   if(encEndTypeId!=null&&!"".equals(encEndTypeId))
		   cStmtObject.setByte(20,new Byte(preAuthDetailVO.getEncounterEndTypeId()));
		   else cStmtObject.setByte(20,(byte)0);
		   
		   cStmtObject.setString(21,preAuthDetailVO.getRemarks());
		   cStmtObject.setLong(22,preAuthDetailVO.getUpdatedBy());
		   cStmtObject.setString(23,preAuthDetailVO.getClinicianId());
		   cStmtObject.setString(24,preAuthDetailVO.getSystemOfMedicine());
		   cStmtObject.setString(25,preAuthDetailVO.getAccidentRelatedCase());
		   cStmtObject.setString(26,preAuthDetailVO.getPriorityTypeID());
		   cStmtObject.setString(27,preAuthDetailVO.getNetworkProviderType());
		   cStmtObject.setString(28,preAuthDetailVO.getProviderName());
		   cStmtObject.setString(29,preAuthDetailVO.getProviderId());
		   cStmtObject.setString(30,preAuthDetailVO.getProviderArea());
		   cStmtObject.setString(31,preAuthDetailVO.getProviderEmirate());
		   cStmtObject.setString(32,preAuthDetailVO.getProviderCountry());
		   cStmtObject.setString(33,preAuthDetailVO.getProviderAddress());
		   if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getProviderPobox())))
		   cStmtObject.setLong(34,new Long(preAuthDetailVO.getProviderPobox()));
		   else cStmtObject.setLong(34,0);
		   
		    if("DBL".equals(preAuthDetailVO.getProcessType()) || "".equals(preAuthDetailVO.getProcessType())){
				   cStmtObject.setDouble(35,preAuthDetailVO.getRequestedAmount().doubleValue());
			   }
			   else if("GBL".equals(preAuthDetailVO.getProcessType()))
			   {
				   cStmtObject.setDouble(35,preAuthDetailVO.getProRequestedAmount().doubleValue());
			 }else cStmtObject.setDouble(35,0.0);
		    
		   cStmtObject.setString(36,preAuthDetailVO.getBenefitType());
		   int gravida=preAuthDetailVO.getGravida()==null?0:preAuthDetailVO.getGravida();
		   int para=preAuthDetailVO.getPara()==null?0:preAuthDetailVO.getPara();
		   int live=preAuthDetailVO.getLive()==null?0:preAuthDetailVO.getLive();
		   int abortion=preAuthDetailVO.getAbortion()==null?0:preAuthDetailVO.getAbortion();
		   cStmtObject.setByte(37,(byte)gravida);
		   cStmtObject.setByte(38,(byte)para);
		   cStmtObject.setByte(39,(byte)live);
		   cStmtObject.setByte(40,(byte)abortion);	
		   cStmtObject.setString(41,preAuthDetailVO.getEnhancedYN());
		   
		   cStmtObject.setString(42,preAuthDetailVO.getRequestedAmountCurrency());
		   cStmtObject.setString(43,preAuthDetailVO.getClinicianName());
		   
		   if(preAuthDetailVO.getAvailableSumInsured()!=null)
		   cStmtObject.setDouble(44,preAuthDetailVO.getAvailableSumInsured().doubleValue());
		   else  cStmtObject.setDouble(44,0.0);
		   
			  
		    cStmtObject.setString(45,preAuthDetailVO.getDiagnosis());
			cStmtObject.setString(46,preAuthDetailVO.getServices());
			
			cStmtObject.setBigDecimal(47, preAuthDetailVO.getOralApprovedAmount());
			  
		   
		   cStmtObject.setString(48,preAuthDetailVO.getRevisedDiagnosis());
		   cStmtObject.setString(49,preAuthDetailVO.getRevisedServices());
		   cStmtObject.setBigDecimal(50, preAuthDetailVO.getOralRevisedApprovedAmount());
		   cStmtObject.setString(51,preAuthDetailVO.getOralORsystemStatus());
		   cStmtObject.setString(52,preAuthDetailVO.getConsultationType());
		   
		   if(TTKCommon.checkNull(preAuthDetailVO.getMemberWeight()).length()>0)
		   cStmtObject.setDouble(53,new Double(preAuthDetailVO.getMemberWeight()));
		   else  cStmtObject.setDouble(53,0.0);
		   
		   cStmtObject.setString(54,preAuthDetailVO.getReferenceNo());
		   cStmtObject.setString(55,preAuthDetailVO.getDocumentStatus());
		   cStmtObject.setString(56,preAuthDetailVO.getProcessType());
		   
		   cStmtObject.setString(57,preAuthDetailVO.getRequestedAmountcurrencyType());
		   
		   if(TTKCommon.checkNull(preAuthDetailVO.getConvertedAmount()).length()>0)
		   cStmtObject.setBigDecimal(58,new BigDecimal(preAuthDetailVO.getConvertedAmount()));
		   else cStmtObject.setBigDecimal(58,null);
		   
		   cStmtObject.setString(59,preAuthDetailVO.getCurrencyType());
		   cStmtObject.setString(60,preAuthDetailVO.getConversionRate());
		   cStmtObject.setString(61,preAuthDetailVO.getPartnerName());
			if(preAuthDetailVO.getPartnerPreAuthSeqId() != null)
				 cStmtObject.setLong(62,preAuthDetailVO.getPartnerPreAuthSeqId());
			 else
				 cStmtObject.setLong(62,0);
		 
		 
		 if(!(preAuthDetailVO.getPreauthPartnerRefId()==null||("").equals(preAuthDetailVO.getPreauthPartnerRefId().trim())))
			 cStmtObject.setString(63,preAuthDetailVO.getPreauthPartnerRefId());
		 else
			 cStmtObject.setString(63,null);
		 
		 
		 cStmtObject.setString(64,preAuthDetailVO.getProviderType());
		 cStmtObject.setString(65,preAuthDetailVO.getProviderFecilType());
		   
		   cStmtObject.registerOutParameter(66,Types.BIGINT);
		   cStmtObject.registerOutParameter(1,Types.BIGINT);
		   
		   cStmtObject.registerOutParameter(9,Types.VARCHAR);
		   cStmtObject.setString(67,null);
		   cStmtObject.setDouble(68,0);
		   cStmtObject.setString(69,null);
		   cStmtObject.setDouble(70,0);
		   cStmtObject.setString(71,null);
		   cStmtObject.setString(72,null);
		   cStmtObject.setString(73,null);
		   cStmtObject.setString(74,null);
		   cStmtObject.setString(75,null);
		   cStmtObject.setString(76,null);
		   
           if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getWorkRelatedInjury()))){
        	   cStmtObject.setString(77,preAuthDetailVO.getWorkRelatedInjury());
	 		               }
		   else{
		   cStmtObject.setString(77,"");
		   }
           
           if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getAlcoholIntoxication()))){
        	   cStmtObject.setString(78,preAuthDetailVO.getAlcoholIntoxication());
	 		               }
		   else{
		   cStmtObject.setString(78,"");
		   }
           
           if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getRoadTrafficAccident()))){
        	   cStmtObject.setString(79,preAuthDetailVO.getRoadTrafficAccident());
	 		               }
		   else{
		   cStmtObject.setString(79,"");
		   }
           if(preAuthDetailVO.getDateOfInjury() != null){
				cStmtObject.setTimestamp(80,new Timestamp(preAuthDetailVO.getDateOfInjury().getTime()));
			}
			else{
				cStmtObject.setTimestamp(80, null);
			}
           if(!"".equals(TTKCommon.checkNull(preAuthDetailVO.getPatientType()))){
        	   cStmtObject.setString(81,preAuthDetailVO.getPatientType());
	 		               }
		   else{
		   cStmtObject.setString(81,"");
		   }
		   cStmtObject.execute();
		   conn.commit();
			objArrayResult[0] = cStmtObject.getLong(1);
			objArrayResult[1] = cStmtObject.getString(9);
			objArrayResult[2] = cStmtObject.getLong(66);
	}//end of try
	catch (SQLException sqlExp)
	{
		throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
		throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
		try // First try closing the Statement
		{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl savePreAuth()",
							sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl savePreAuth()",
								sqlExp);
					throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
		{
			cStmtObject = null;
			conn = null;
			}// end of finally
		}// end of finally
	return objArrayResult;
	}// end of savePreAuth(PreAuthDetailVO preAuthDetailVO,String
		// strSelectionType)

	public Object[] saveDiagnosisDetails(DiagnosisDetailsVO diagnosisDetailsVO)
			throws TTKException {
	Object[] objArrayResult = new Object[2];
	Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strAddDiagnosisDetails);

			if (diagnosisDetailsVO.getDiagSeqId() == null)
				cStmtObject.setLong(1, 0);
			else
				{
				cStmtObject.setLong(1, diagnosisDetailsVO.getDiagSeqId());
				}
			if ("PAT".equals(diagnosisDetailsVO.getAuthType())) {
				cStmtObject.setLong(2, diagnosisDetailsVO.getPreAuthSeqID());
				cStmtObject.setLong(3, 0);
			} else if ("CLM".equals(diagnosisDetailsVO.getAuthType())) {
				cStmtObject.setLong(2, 0);
				cStmtObject.setLong(3, diagnosisDetailsVO.getClaimSeqID());
		   }
			cStmtObject.setLong(4, diagnosisDetailsVO.getIcdCodeSeqId());
			cStmtObject.setString(5, diagnosisDetailsVO.getIcdCode());
			if (diagnosisDetailsVO.getPrimaryAilment() == null
					|| diagnosisDetailsVO.getPrimaryAilment().length() < 1
					|| "n".equalsIgnoreCase(diagnosisDetailsVO.getPrimaryAilment()))
				cStmtObject.setString(6, "N");
			else
				cStmtObject.setString(6, "Y");
			cStmtObject.setString(7,diagnosisDetailsVO.getPresentingComplaints());
			cStmtObject.setLong(8, diagnosisDetailsVO.getAddedBy());
			cStmtObject.setString(9,diagnosisDetailsVO.getInfoType());
			cStmtObject.setString(10,diagnosisDetailsVO.getInfoCode());
			cStmtObject.setString(11,diagnosisDetailsVO.getPreCronTypeID());
			cStmtObject.setString(12,diagnosisDetailsVO.getPreOneMedicalCondition());
			cStmtObject.registerOutParameter(13, Types.INTEGER);
			cStmtObject.setInt(14,diagnosisDetailsVO.getDurAilment());
			cStmtObject.setString(15,diagnosisDetailsVO.getDurationFlag());
			cStmtObject.setString(16,diagnosisDetailsVO.getRelPresentIllness());
			cStmtObject.setString(17,diagnosisDetailsVO.getRelevantFindings());
			cStmtObject.execute();
			conn.commit();
		  objArrayResult[0] = cStmtObject.getInt(13);
		  conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
		throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
		try // First try closing the Statement
		{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl saveDiagnosisDetails()",
							sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl saveDiagnosisDetails()",
								sqlExp);
					throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
		{
			cStmtObject = null;
			conn = null;
			}// end of finally
		}// end of finally
	return objArrayResult;
	}// end of saveDiagnosisDetails(PreAuthDetailVO preAuthDetailVO,String
		// strSelectionType)

	public int deleteShortfallsDetails(String[] shortfallsDeatils)
			throws TTKException {
	int updatedRows;
	Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strDeleteShortfallsDetails);
			cStmtObject.setLong(1, new Long(shortfallsDeatils[0]));
			cStmtObject.setLong(2, new Long(shortfallsDeatils[1]));
			cStmtObject.setString(3, shortfallsDeatils[2]);
			cStmtObject.registerOutParameter(4, Types.INTEGER);
			cStmtObject.execute();
			updatedRows = cStmtObject.getInt(4);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
		throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
		try // First try closing the Statement
		{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl deleteShortfallsDetails()",
							sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl deleteShortfallsDetails()",
								sqlExp);
					throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
		{
			cStmtObject = null;
			conn = null;
			}// end of finally
		}// end of finally
	return updatedRows;
	}// end of deleteShortfallsDetails(PreAuthDetailVO preAuthDetailVO,String
		// strSelectionType)

	public Object[] deleteDiagnosisDetails(DiagnosisDetailsVO diagnosisDetailsVO)
			throws TTKException {
	Object[] objArrayResult = new Object[2];
	Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strDeleteDiagnosisDetails);
			if ("PAT".equals(diagnosisDetailsVO.getAuthType()))
				cStmtObject.setLong(1, diagnosisDetailsVO.getPreAuthSeqID());
			else if ("CLM".equals(diagnosisDetailsVO.getAuthType()))
				cStmtObject.setLong(1, diagnosisDetailsVO.getClaimSeqID());
			cStmtObject.setLong(2, diagnosisDetailsVO.getDiagSeqId());
			cStmtObject.setString(3, diagnosisDetailsVO.getAuthType());
			cStmtObject.registerOutParameter(4, Types.INTEGER);
			cStmtObject.execute();
			conn.commit();
			objArrayResult[0] = cStmtObject.getInt(4);
		}// end of try
		catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
		throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
		try // First try closing the Statement
		{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl deleteDiagnosisDetails()",
							sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl deleteDiagnosisDetails()",
								sqlExp);
					throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
		{
			cStmtObject = null;
			conn = null;
			}// end of finally
		}// end of finally
	return objArrayResult;
	}// end of deleteDiagnosisDetails(PreAuthDetailVO preAuthDetailVO,String
		// strSelectionType)

	public Object[] deleteActivityDetails(long preauthSeqId,long activityDtlSeqId, String authType) throws TTKException {
	Object[] objArrayResult = new Object[2];
	Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strDeleteActivityDetails);
			cStmtObject.setLong(1, preauthSeqId);
			cStmtObject.setLong(2, activityDtlSeqId);
			cStmtObject.setString(3, authType);
			cStmtObject.registerOutParameter(4, Types.INTEGER);
			cStmtObject.execute();
			conn.commit();
			objArrayResult[0] = cStmtObject.getInt(4);
		}// end of try
		catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
		throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
		try // First try closing the Statement
		{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl deleteActivityDetails()",
							sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl deleteActivityDetails()",
								sqlExp);
					throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
		{
			cStmtObject = null;
			conn = null;
			}// end of finally
		}// end of finally
	return objArrayResult;
	}// end of deleteActivityDetails()

	public Document getPreAuthHistory(Long lngSeqId) throws TTKException {
    Connection conn = null;
    CallableStatement cStmtObject = null;
    Document doc = null;
		try {
			conn = ResourceManager.getConnection();

			cStmtObject = conn.prepareCall(strPreAuthHistoryList);
			cStmtObject.setLong(1, lngSeqId);
			cStmtObject.registerOutParameter(2, Types.SQLXML);
            cStmtObject.execute();
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(cStmtObject.getSQLXML(2).getCharacterStream());

		}// end of try
		catch (SQLException sqlExp) {
        throw new TTKException(sqlExp, "history");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
        throw new TTKException(exp, "history");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
    	try // First try closing the Statement
    	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl getPreAuthHistory()",
							sqlExp);
    			throw new TTKException(sqlExp, "history");
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
								"Error while closing the Connection in PreAuthDAOImpl getPreAuthHistory()",
								sqlExp);
    				throw new TTKException(sqlExp, "history");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
    		throw new TTKException(exp, "history");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
    	{
    		cStmtObject = null;
    		conn = null;
			}// end of finally
		}// end of finally
    return doc;
	}// end of getPreAuthHistory(String strHistoryType, long lngSeqId, long
		// lngEnrollDtlSeqId)

	public Document getClaimHistory(Long lngSeqId) throws TTKException {
    Connection conn = null;
    CallableStatement cStmtObject = null;
    Document doc = null;
		try {
			conn = ResourceManager.getConnection();

			cStmtObject = conn.prepareCall(strClaimHistoryList);
			cStmtObject.setLong(1, lngSeqId);
			cStmtObject.registerOutParameter(2, Types.SQLXML);
            cStmtObject.execute();
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(cStmtObject.getSQLXML(2).getCharacterStream());

		}// end of try
		catch (SQLException sqlExp) {
        throw new TTKException(sqlExp, "history");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
        throw new TTKException(exp, "history");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
    	try // First try closing the Statement
    	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl getClaimHistory()",
							sqlExp);
    			throw new TTKException(sqlExp, "history");
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
								"Error while closing the Connection in PreAuthDAOImpl getClaimHistory()",
								sqlExp);
    				throw new TTKException(sqlExp, "history");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
    		throw new TTKException(exp, "history");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
    	{
    		cStmtObject = null;
    		conn = null;
			}// end of finally
		}// end of finally
    return doc;
	}// end of getClaimHistory(String strHistoryType, long lngSeqId, long
		// lngEnrollDtlSeqId)


	/**
	 * This method will allow to Override the Preauth information
	 * 
	 * @param lngPATGenDetailSeqID
	 *            PATGenDetailSeqID
	 * @param lngPATEnrollDtlSeqID
	 *            PATEnrollDtlSeqID
	 * @param lngUserSeqID
	 *            long value which contains Logged-in User
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO overridePreauth(long lngPATGenDetailSeqID,
			long lngPATEnrollDtlSeqID, long lngUserSeqID) throws TTKException {
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		PreAuthDetailVO preauthDetailVO = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strOverridePreauth);
			preauthDetailVO = new PreAuthDetailVO();
			cStmtObject.setLong(1, lngPATGenDetailSeqID);
			cStmtObject.setLong(2, lngPATEnrollDtlSeqID);
			cStmtObject.setLong(3, lngUserSeqID);
			cStmtObject.registerOutParameter(4, Types.BIGINT);// EVENT_SEQ_ID
			cStmtObject.registerOutParameter(5, Types.BIGINT);// REVIEW_COUNT
			cStmtObject.registerOutParameter(6, Types.BIGINT);// REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(7, Types.VARCHAR);
			cStmtObject.registerOutParameter(8, Types.VARCHAR);
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
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl savePreAuth()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl savePreAuth()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return preauthDetailVO;
	}// end of overridePreauth(long lngPATGenDetailSeqID,long
		// lngPATEnrollDtlSeqID,long lngUserSeqID)

	/**
	 * This method saves the Review information
	 * 
	 * @param preauthDetailVO
	 *            the object which contains the Review Details which has to be
	 *            saved
	 * @param strMode
	 *            String object which contains Mode Pre-authorization/Claims -
	 *            PAT/CLM
	 * @param strType
	 *            String object which contains Type
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO saveReview(PreAuthDetailVO preauthDetailVO,
			String strMode, String strType) throws TTKException {

		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		String strCodingReviewYN = "";
		String strShowCodingOverride = "";
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveReview);

			if (preauthDetailVO.getPreAuthSeqID() != null) {
				cStmtObject.setLong(1, preauthDetailVO.getPreAuthSeqID());
			}// end of if(preauthDetailVO.getPreAuthSeqID() != null)
			else {
				cStmtObject.setString(1, null);
			}// end of else

			if (preauthDetailVO.getEventSeqID() != null) {

				cStmtObject.setLong(2, preauthDetailVO.getEventSeqID());
			}// end of if(preauthDetailVO.getEventSeqID() != null)
			else {
				cStmtObject.setString(2, null);
			}// end of else

			if (preauthDetailVO.getReviewCount() != null) {

				cStmtObject.setInt(3, preauthDetailVO.getReviewCount());
			}// end of if(preauthDetailVO.getReviewCount() != null)
			else {
				cStmtObject.setString(3, null);
			}// end of else

			if (preauthDetailVO.getRequiredReviewCnt() != null) {

				cStmtObject.setLong(4, preauthDetailVO.getRequiredReviewCnt());
			}// end of if(preauthDetailVO.getRequiredReviewCnt() != null)
			else {
				cStmtObject.setString(4, null);
			}// end of else

			cStmtObject.setString(5, strMode);

			cStmtObject.setString(6, strType);
			cStmtObject.setLong(7, preauthDetailVO.getUpdatedBy());
			cStmtObject.registerOutParameter(2, Types.BIGINT);// EVENT_SEQ_ID
			cStmtObject.registerOutParameter(3, Types.BIGINT);// REVIEW_COUNT
			cStmtObject.registerOutParameter(4, Types.BIGINT);// REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(8, Types.VARCHAR);
			cStmtObject.registerOutParameter(9, Types.VARCHAR);
			cStmtObject.registerOutParameter(10, Types.VARCHAR);// CODING_REVIEW_YN
			cStmtObject.registerOutParameter(11, Types.VARCHAR);// SHOW_CODING_OVERRIDE
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

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl saveReview()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl saveReview()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return preauthDetailVO;
	}// end of saveReview(PreAuthDetailVO preauthDetailVO,String strMode,String
		// strType)

	/**
	 * This method assigns the Pre-Authorization information to the
	 * corresponding Doctor
	 * 
	 * @param preAuthAssignmentVO
	 *            PreAuthAssignmentVO which contains Pre-Authorization
	 *            information to assign the corresponding Doctor
	 * @return long value which contains Assign Users Seq ID
	 * @exception throws TTKException
	 */
	public long assignPreAuth(PreAuthAssignmentVO preAuthAssignmentVO)
			throws TTKException {
		long lngAssignUsersSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveAssignUsers);
			if (preAuthAssignmentVO.getAssignUserSeqID() != null) {
				cStmtObject
						.setLong(1, preAuthAssignmentVO.getAssignUserSeqID());
			}// end of if(preAuthAssignmentVO.getAssignUserSeqID() != null)
			else {
				cStmtObject.setLong(1, 0);
			}// end of else

			if (preAuthAssignmentVO.getClaimSeqID() != null) {
				cStmtObject.setLong(2, preAuthAssignmentVO.getClaimSeqID());
			}// end of if(preAuthAssignmentVO.getClaimSeqID() != null)
			else {
				cStmtObject.setString(2, null);
			}// end of else

			if (preAuthAssignmentVO.getPreAuthSeqID() != null) {
				cStmtObject.setLong(3, preAuthAssignmentVO.getPreAuthSeqID());
			}// end of if(preAuthAssignmentVO.getPreAuthSeqID() != null)
			else {
				cStmtObject.setString(3, null);
			}// end of else

			if (preAuthAssignmentVO.getOfficeSeqID() != null) {
				cStmtObject.setLong(4, preAuthAssignmentVO.getOfficeSeqID());
			}// end of if(preAuthAssignmentVO.getOfficeSeqID() != null)
			else {
				cStmtObject.setString(4, null);
			}// end of else

			if (preAuthAssignmentVO.getDoctor() != null) {
				cStmtObject.setLong(5, preAuthAssignmentVO.getDoctor());
			}// end of if(preAuthAssignmentVO.getDoctor() != null)
			else {
				cStmtObject.setString(5, null);
			}// end of else

			cStmtObject.setString(6, null);
			cStmtObject.setString(7, preAuthAssignmentVO.getRemarks());
			cStmtObject.setString(8, "EXT");
			cStmtObject.setString(9, preAuthAssignmentVO.getSuperUserYN());
			cStmtObject.setLong(10, preAuthAssignmentVO.getUpdatedBy());
			cStmtObject.registerOutParameter(11, Types.INTEGER);
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.execute();
			lngAssignUsersSeqID = cStmtObject.getLong(1);// ASSIGN_USERS_SEQ_ID
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl assignPreAuth()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl assignPreAuth()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return lngAssignUsersSeqID;
	}// end of assignPreAuth(PreAuthAssignmentVO preAuthAssignmentVO)

	/**
	 * This method returns the PreAuthAssignmentVO which contains Assignment
	 * details
	 * 
	 * @param lngAssignUsersSeqID
	 *            long value contains Assign User Seq ID
	 * @param lngUserSeqID
	 *            long value contains Logged-in User Seq ID
	 * @param strMode
	 *            contains PAT/CLM for identifying Pre-Authorization/Claims
	 * @return ArrayList of PreAuthAssignmentVO object which contains Assignment
	 *         details
	 * @exception throws TTKException
	 */
	public PreAuthAssignmentVO getAssignTo(long lngAssignUsersSeqID,
			long lngUserSeqID, String strMode) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		PreAuthAssignmentVO preauthAssignmentVO = null;
		ArrayList alAssignUserList = new ArrayList();
		ArrayList<Object> alUserList = new ArrayList<Object>();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strGetAssignTo);
			cStmtObject.setLong(1, lngAssignUsersSeqID);
			cStmtObject.setLong(2, lngUserSeqID);
			cStmtObject.setString(3, strMode);
			cStmtObject.registerOutParameter(4, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(4);
			if (rs != null) {
				while (rs.next()) {
					preauthAssignmentVO = new PreAuthAssignmentVO();

					if (rs.getString("ASSIGN_USERS_SEQ_ID") != null) {
						preauthAssignmentVO.setAssignUserSeqID(new Long(rs
								.getLong("ASSIGN_USERS_SEQ_ID")));
					}// end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)

					if (rs.getString("SEQ_ID") != null) {
						if (strMode.equals("PAT")) {
							preauthAssignmentVO.setPreAuthSeqID(new Long(rs
									.getLong("SEQ_ID")));
						}// end of if(strMode.equals("PAT"))
						else {
							preauthAssignmentVO.setClaimSeqID(new Long(rs
									.getLong("SEQ_ID")));
						}// end of else
					}// end of if(rs.getString("SEQ_ID") != null)

					preauthAssignmentVO.setSelectedPreAuthNos(TTKCommon
							.checkNull(rs.getString("ID_NUMBER")));

					if (rs.getString("TPA_OFFICE_SEQ_ID") != null) {
						preauthAssignmentVO.setOfficeSeqID(new Long(rs
								.getLong("TPA_OFFICE_SEQ_ID")));
					}// end of if(rs.getString("TPA_OFFICE_SEQ_ID") != null)

					if (rs.getString("ASSIGNED_TO_USER") != null) {
						preauthAssignmentVO.setDoctor(new Long(rs
								.getLong("ASSIGNED_TO_USER")));
					}// end of if(rs.getString("ASSIGNED_TO_USER") != null)

					preauthAssignmentVO.setRemarks(TTKCommon.checkNull(rs
							.getString("REMARKS")));

					if (rs.getString("POLICY_SEQ_ID") != null) {
						preauthAssignmentVO.setPolicySeqID(new Long(rs
								.getLong("POLICY_SEQ_ID")));
					}// end of if(rs.getString("POLICY_SEQ_ID") != null)

					if (rs.getString("SEQ_ID") != null
							&& rs.getString("TPA_OFFICE_SEQ_ID") != null) {
						alUserList.add(new Long(rs.getLong("SEQ_ID")));

						if (rs.getString("POLICY_SEQ_ID") != null) {
							alUserList
									.add(new Long(rs.getLong("POLICY_SEQ_ID")));
						}// end of if(rs.getString("POLICY_SEQ_ID") != null)
						else {
							alUserList.add(null);
						}// end of else

						alUserList
								.add(new Long(rs.getLong("TPA_OFFICE_SEQ_ID")));
						alAssignUserList = getAssignUserList(alUserList,
								strMode);
						preauthAssignmentVO.setAssignUserList(alAssignUserList);
					}// end of if

				}// end of while(rs.next())
			}// end of if(rs != null)
			return preauthAssignmentVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getAssignTo()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getAssignTo()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getAssignTo()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getAssignTo(long lngAssignUsersSeqID,long lngUserSeqID,String
		// strMode)

	/**
	 * This method returns the ArrayList object, which contains all the Users
	 * for the Corresponding TTK Branch
	 * 
	 * @param alAssignUserList
	 *            ArrayList Object contains PreauthSeqID/ClaimSeqID,PolicySeqID
	 *            and TTKBranch
	 * @param strMode
	 *            contains PAT/CLM for identifying the Pre-Authorization/Claims
	 *            flow
	 * @return ArrayList object which contains all the Users for the
	 *         Corresponding TTK Branch
	 * @exception throws TTKException
	 */
	public ArrayList getAssignUserList(ArrayList alAssignUserList,
			String strMode) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ArrayList<Object> alUserList = new ArrayList<Object>();
		CacheObject cacheObject = null;
		try {
			conn = ResourceManager.getConnection();

			if (strMode.equals("PAT")) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strPAAssignUserList);
			}// end of if(strMode.equals("PAT"))
			else if (strMode.equals("CLM")) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strClaimAssignUserList);
			}// end of if(strMode.equals("CLM"))
			cStmtObject.setLong(1, (Long) alAssignUserList.get(0));// Mandatory
			if (alAssignUserList.get(1) != null) {
				cStmtObject.setLong(2, (Long) alAssignUserList.get(1));
			}// end of if(alAssignUserList.get(1) != null)
			else {
				cStmtObject.setString(2, null);
			}// end of else

			cStmtObject.setLong(3, (Long) alAssignUserList.get(2));// Mandatory
			cStmtObject.registerOutParameter(4, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(4);
			if (rs != null) {
				while (rs.next()) {
					cacheObject = new CacheObject();
					cacheObject.setCacheId((rs.getString("CONTACT_SEQ_ID")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs
							.getString("CONTACT_NAME")));
					alUserList.add(cacheObject);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return alUserList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getAssignUserList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getAssignUserList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getAssignUserList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getAssignUserList(ArrayList alAssignUserList,String strMode)

	/**
	 * This method returns the Arraylist of ClaimantVO's which contains Claimant
	 * details
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of ClaimantVO object which contains Claimant details
	 * @exception throws TTKException
	 */
	public ArrayList getClaimantList(ArrayList alSearchCriteria)
			throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ClaimantVO claimantVO = null;
		try {
						
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strEnrollmentList);
			cStmtObject.setString(1, (String) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(1));
			cStmtObject.setString(3, (String) alSearchCriteria.get(2));
			cStmtObject.setString(4, (String) alSearchCriteria.get(3));
			cStmtObject.setString(5, (String) alSearchCriteria.get(4));
			cStmtObject.setString(6, (String) alSearchCriteria.get(5));
			cStmtObject.setString(7, (String) alSearchCriteria.get(6));
			cStmtObject.setString(8, (String) alSearchCriteria.get(7));
			cStmtObject.setString(9, (String) alSearchCriteria.get(8));
			
			cStmtObject.setString(10, (String) alSearchCriteria.get(12));
			cStmtObject.setString(11, (String) alSearchCriteria.get(13));
			cStmtObject.setString(12, (String) alSearchCriteria.get(14));
			cStmtObject.setString(13, (String) alSearchCriteria.get(15));
			cStmtObject.setLong(14, (Long) alSearchCriteria.get(9));
			cStmtObject.setString(15, (String) alSearchCriteria.get(10));
			cStmtObject.setString(16, (String) alSearchCriteria.get(11));
			cStmtObject.registerOutParameter(17, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(17);
			if (rs != null) {
				while (rs.next()) {
					claimantVO = new ClaimantVO();

					if (rs.getString("MEMBER_SEQ_ID") != null) {
						claimantVO.setMemberSeqID(new Long(rs
								.getLong("MEMBER_SEQ_ID")));
					}// end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if (rs.getString("POLICY_GROUP_SEQ_ID") != null) {
						claimantVO.setPolicyGroupSeqID(new Long(rs
								.getLong("POLICY_GROUP_SEQ_ID")));
					}// end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs
							.getString("TPA_ENROLLMENT_ID")));
					
					claimantVO.setsEmiratesID(TTKCommon.checkNull(rs.getString("emirate_id")));
					
					
					claimantVO.setName(TTKCommon.checkNull(rs
							.getString("MEM_NAME")));
					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs
							.getString("INSURED_NAME")));

					if (TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID"))
							.equals("COR")) {
						claimantVO.setEmployeeName(TTKCommon.checkNull(rs
								.getString("INSURED_NAME")));
					}// end of
						// if(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")).equals("COR"))

					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs
							.getString("EMPLOYEE_NO")));
					claimantVO.setGenderTypeID(TTKCommon.checkNull(rs
							.getString("GENDER_GENERAL_TYPE_ID")));
					claimantVO.setGenderDescription(TTKCommon.checkNull(rs
							.getString("GENDER")));

					if (rs.getString("MEM_AGE") != null) {
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}// end of if(rs.getString("MEM_AGE") != null)

					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs
							.getString("POLICY_NUMBER")));

					if (rs.getString("INS_SEQ_ID") != null) {
						claimantVO.setInsSeqID(new Long(rs
								.getLong("INS_SEQ_ID")));
					}// end of if(rs.getString("") != null)
					claimantVO.setCompanyName(TTKCommon.checkNull(rs
							.getString("INS_COMP_NAME")));
					claimantVO.setCompanyCode(TTKCommon.checkNull(rs
							.getString("INS_COMP_CODE_NUMBER")));

					if (rs.getString("GROUP_REG_SEQ_ID") != null) {
						claimantVO.setGroupRegnSeqID(new Long(rs
								.getLong("GROUP_REG_SEQ_ID")));
					}// end of if(rs.getString("") != null)

					claimantVO.setGroupName(TTKCommon.checkNull(rs
							.getString("GROUP_NAME")));
					claimantVO.setGroupID(TTKCommon.checkNull(rs
							.getString("GROUP_ID")));

					if (rs.getString("DATE_OF_INCEPTION") != null) {
						claimantVO.setDateOfInception(new Date(rs.getTimestamp(
								"DATE_OF_INCEPTION").getTime()));
					}// end of if(rs.getString("DATE_OF_INCEPTION") != null)

					if (rs.getString("DATE_OF_EXIT") != null) {
						claimantVO.setDateOfExit(new Date(rs.getTimestamp(
								"DATE_OF_EXIT").getTime()));
					}// end of if(rs.getString("DATE_OF_EXIT") != null)

					if (rs.getString("MEM_TOT_SUM_INSURED") != null) {
						claimantVO.setTotalSumInsured(new BigDecimal(rs
								.getString("MEM_TOT_SUM_INSURED")));
					}// end of if(rs.getString("MEM_TOT_SUM_INSURED") != null)

					if (rs.getString("AVAIL_SUM_INSURED") != null) {
						claimantVO.setAvailSumInsured(new BigDecimal(rs
								.getString("AVAIL_SUM_INSURED")));
					}// end of if(rs.getString("AVAIL_SUM_INSURED") != null)

					claimantVO.setCategoryTypeID(TTKCommon.checkNull(rs
							.getString("CATEGORY_GENERAL_TYPE_ID")));
					claimantVO.setCategoryDesc(TTKCommon.checkNull(rs
							.getString("CATEGORY")));
					claimantVO.setPolicyTypeID(TTKCommon.checkNull(rs
							.getString("ENROL_TYPE_ID")));

					if (rs.getString("PRODUCT_SEQ_ID") != null) {
						claimantVO.setProductSeqID(new Long(rs
								.getLong("PRODUCT_SEQ_ID")));
					}// end of if(rs.getString("PRODUCT_SEQ_ID") != null)

					claimantVO.setPolicySubTypeID(TTKCommon.checkNull(rs
							.getString("POLICY_SUB_GENERAL_TYPE_ID")));
					claimantVO.setTermStatusID(TTKCommon.checkNull(rs
							.getString("INS_STATUS_GENERAL_TYPE_ID")));
					claimantVO.setPhone(TTKCommon.checkNull(rs
							.getString("PHONE")));
					
					if (rs.getString("EFFECTIVE_FROM_DATE") != null) {
						claimantVO.setStartDate(new Date(rs.getTimestamp("EFFECTIVE_FROM_DATE").getTime()));
					}// end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if (rs.getString("EFFECTIVE_TO_DATE") != null) {
						claimantVO.setEndDate(new Date(rs.getTimestamp(
								"EFFECTIVE_TO_DATE").getTime()));
					}// end of if(rs.getString("EFFECTIVE_TO_DATE") != null)


					if(rs.getString("pol_start") != null)
					{
						claimantVO.setsDate(rs.getString("pol_start"));
						
					}
					if(rs.getString("pol_end") != null)
					{
						claimantVO.seteDate(rs.getString("pol_end"));
					
						
					}
					
					
					if (rs.getString("POLICY_SEQ_ID") != null) {
						claimantVO.setPolicySeqID(new Long(rs
								.getLong("POLICY_SEQ_ID")));
					}// end of if(rs.getString("POLICY_SEQ_ID") != null)

					claimantVO.setBufferAllowedYN(TTKCommon.checkNull(rs
							.getString("BUFFER_ALLOWED_YN")));

					if (rs.getString("TOTAL_BUFFER_AMOUNT") != null) {
						claimantVO.setTotBufferAmt(new BigDecimal(rs
								.getString("TOTAL_BUFFER_AMOUNT")));
					}// end of if(rs.getString("TOTAL_BUFFER_AMOUNT") != null)

					claimantVO.setSchemeCertNbr(TTKCommon.checkNull(rs
							.getString("SCHEME_OR_CERTFICATE")));

					if (rs.getString("INS_HEAD_OFFICE_SEQ_ID") != null) {
						claimantVO.setInsHeadOffSeqID(new Long(rs
								.getLong("INS_HEAD_OFFICE_SEQ_ID")));
					}// end of if(rs.getString("INS_HEAD_OFFICE_SEQ_ID") !=
						// null)

					claimantVO.setRelationTypeID(TTKCommon.checkNull(rs
							.getString("RELSHIP_TYPE_ID")));
					claimantVO.setEmailID(TTKCommon.checkNull(rs
							.getString("EMAIL_ID")));
					claimantVO.setInsCustCode(TTKCommon.checkNull(rs
							.getString("INS_CUSTOMER_CODE")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs
							.getString("CERTIFICATE_NO")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs
							.getString("INS_SCHEME")));
					claimantVO.setCreditCardNbr(TTKCommon.checkNull(rs
							.getString("CREDITCARD_NO")));
					

					claimantVO.setMemberActive(TTKCommon.checkNull(rs.getString("Member_Acitive")));
					claimantVO.setCorpGroupId(TTKCommon.checkNull(rs.getString("cor_group_id")));
					claimantVO.setCardNo(TTKCommon.checkNull(rs.getString("tpa_enrollment_id")));
					claimantVO.setNetworkType(TTKCommon.checkNull(rs.getString("member_network")));
					
					if(rs.getString("dob") != null)
					{
						claimantVO.setDob(rs.getString("dob"));
					}
					claimantVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					alResultList.add(claimantVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getClaimantList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getClaimantList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getClaimantList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getClaimantList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of ClaimantVO's which contains Claimant
	 * details
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of ClaimantVO object which contains Claimant details
	 * @exception throws TTKException
	 */
	public ArrayList getEnrollmentList(ArrayList alSearchCriteria)
			throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ClaimantNewVO claimantVO = null;
		try {
			
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSelectMemberList);
			    cStmtObject.setString(1, (String) alSearchCriteria.get(0));
	            cStmtObject.setString(2, (String) alSearchCriteria.get(1));
	            cStmtObject.setString(3, (String) alSearchCriteria.get(2));
	            cStmtObject.setString(4, (String) alSearchCriteria.get(3));
	            cStmtObject.setString(5, (String) alSearchCriteria.get(4));
	            cStmtObject.setString(6, (String) alSearchCriteria.get(5));
	            if(!"".equals(TTKCommon.checkNull(alSearchCriteria.get(6)))){
	            	 cStmtObject.setLong(7, Long.parseLong((String) alSearchCriteria.get(6)));
	            	}
	            else{
	            cStmtObject.setLong(7, 0);
	            }
	            cStmtObject.setString(8, (String) alSearchCriteria.get(7));
	            cStmtObject.setString(9, (String) alSearchCriteria.get(8));
	            cStmtObject.setString(10, (String) alSearchCriteria.get(12));
	             cStmtObject.setString(11, (String) alSearchCriteria.get(13));
	             if(!"".equals(TTKCommon.checkNull(alSearchCriteria.get(14)))){
	            	 cStmtObject.setLong(12, Long.parseLong((String)alSearchCriteria.get(14)));
	            	}
	             else{
	            cStmtObject.setLong(12, 0);
	             }
	             if(!"".equals(TTKCommon.checkNull(alSearchCriteria.get(15)))){
	            	 cStmtObject.setLong(13, Long.parseLong((String)alSearchCriteria.get(15)));
	            	}
	             else{
	            cStmtObject.setLong(13,0);
	             }
	            cStmtObject.setLong(14,   (Long) alSearchCriteria.get(9));
	            cStmtObject.setString(15, (String) alSearchCriteria.get(10));
	             cStmtObject.setString(16, (String) alSearchCriteria.get(11));
	            cStmtObject.registerOutParameter(17, Types.OTHER); 
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(17);
			if (rs != null) {
				while (rs.next()) {
					claimantVO = new ClaimantNewVO();

					if (rs.getString("MEMBER_SEQ_ID") != null) {
						claimantVO.setMemberSeqID(new Long(rs
								.getLong("MEMBER_SEQ_ID")));
					}// end of if(rs.getString("MEMBER_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs
							.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs
							.getString("MEM_NAME")));

					claimantVO.setGenderDescription(TTKCommon.checkNull(rs
							.getString("GENDER")));

					if (rs.getString("MEM_AGE") != null) {
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}// end of if(rs.getString("MEM_AGE") != null)

					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs
							.getString("POLICY_NUMBER")));

					if (rs.getString("GROUP_REG_SEQ_ID") != null) {
						claimantVO.setGroupRegnSeqID(new Long(rs
								.getLong("GROUP_REG_SEQ_ID")));
					}// end of if(rs.getString("") != null)

					claimantVO.setGroupName(TTKCommon.checkNull(rs
							.getString("GROUP_NAME")));

					//					claimantVO.setInsScheme(TTKCommon.checkNull(rs.getString("SCHEME_OR_CERTFICATE")));
					claimantVO.setSchemeCertNbr(TTKCommon.checkNull(rs
							.getString("SCHEME_OR_CERTFICATE")));

					if (rs.getString("EFFECTIVE_FROM_DATE") != null) {
						claimantVO.setStartDate(new Date(rs.getTimestamp(
								"EFFECTIVE_FROM_DATE").getTime()));
					}// end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if (rs.getString("EFFECTIVE_TO_DATE") != null) {
						claimantVO.setEndDate(new Date(rs.getTimestamp(
								"EFFECTIVE_TO_DATE").getTime()));
					}// end of if(rs.getString("EFFECTIVE_TO_DATE") != null)
					claimantVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					alResultList.add(claimantVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getClaimantList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getClaimantList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getClaimantList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getClaimantList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of ClaimantVO's which contains Claimant
	 * details
	 * 
	 * @param lngMemSeqID
	 *            String object which contains the search criteria
	 * @return ArrayList of ClaimantVO object which contains Claimant details
	 * @exception throws TTKException
	 */
	public ClaimantVO getSelectEnrollmentID(Long lngMemSeqID)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ClaimantVO claimantVO = null;
		try {
			conn = ResourceManager.getConnection();
			log.debug("lngMemSeqID is :" + lngMemSeqID);
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSelectEnrollmentID);
			cStmtObject.setLong(1, lngMemSeqID);
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(2);
			claimantVO = new ClaimantVO();
			if (rs != null) {
				while (rs.next()) {

					if (rs.getString("MEMBER_SEQ_ID") != null) {
						claimantVO.setMemberSeqID(new Long(rs
								.getLong("MEMBER_SEQ_ID")));
					}// end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if (rs.getString("POLICY_GROUP_SEQ_ID") != null) {
						claimantVO.setPolicyGroupSeqID(new Long(rs
								.getLong("POLICY_GROUP_SEQ_ID")));
					}// end of if(rs.getString("POLICY_GROUP_SEQ_ID") != null)

					claimantVO.setEnrollmentID(TTKCommon.checkNull(rs
							.getString("TPA_ENROLLMENT_ID")));
					claimantVO.setName(TTKCommon.checkNull(rs
							.getString("MEM_NAME")));
					claimantVO.setPolicyHolderName(TTKCommon.checkNull(rs
							.getString("INSURED_NAME")));

					if (TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID"))
							.equals("COR")) {
						claimantVO.setEmployeeName(TTKCommon.checkNull(rs
								.getString("INSURED_NAME")));
					}// end of
						// if(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")).equals("COR"))

					claimantVO.setEmployeeNbr(TTKCommon.checkNull(rs
							.getString("EMPLOYEE_NO")));
					claimantVO.setGenderTypeID(TTKCommon.checkNull(rs
							.getString("GENDER_GENERAL_TYPE_ID")));
					claimantVO.setGenderDescription(TTKCommon.checkNull(rs
							.getString("GENDER")));

					if (rs.getString("MEM_AGE") != null) {
						claimantVO.setAge(new Integer(rs.getString("MEM_AGE")));
					}// end of if(rs.getString("MEM_AGE") != null)

					claimantVO.setPolicyNbr(TTKCommon.checkNull(rs
							.getString("POLICY_NUMBER")));

					if (rs.getString("INS_SEQ_ID") != null) {
						claimantVO.setInsSeqID(new Long(rs
								.getLong("INS_SEQ_ID")));
					}// end of if(rs.getString("") != null)
					claimantVO.setCompanyName(TTKCommon.checkNull(rs
							.getString("INS_COMP_NAME")));
					claimantVO.setCompanyCode(TTKCommon.checkNull(rs
							.getString("INS_COMP_CODE_NUMBER")));

					if (rs.getString("GROUP_REG_SEQ_ID") != null) {
						claimantVO.setGroupRegnSeqID(new Long(rs
								.getLong("GROUP_REG_SEQ_ID")));
					}// end of if(rs.getString("") != null)

					claimantVO.setGroupName(TTKCommon.checkNull(rs
							.getString("GROUP_NAME")));
					claimantVO.setGroupID(TTKCommon.checkNull(rs
							.getString("GROUP_ID")));

					// changes on 9th Dec for KOC1136
					try {
						String VIP_YN = TTKCommon.checkNull(rs
								.getString("VIP_YN"));
						if (VIP_YN.equals("") || VIP_YN.equalsIgnoreCase("N")) {
							claimantVO.setVipYN("N");
						}
						if (VIP_YN.equalsIgnoreCase("Y")) {
							claimantVO.setVipYN("Y");
						}

					} catch (SQLException e) {
						// TODO: handle exception
						log.error("VIP_YN Column DOES NOT EXIST ");
					}
					// changes on 7th Dec for KOC1136
					// claimantVO.setVipYN(TTKCommon.checkNull(rs.getString("vipYN")));
					// claimantVO.setVipYN("Y");
					// changes on 7th Dec

					if (rs.getString("DATE_OF_INCEPTION") != null) {
						claimantVO.setDateOfInception(new Date(rs.getTimestamp(
								"DATE_OF_INCEPTION").getTime()));
					}// end of if(rs.getString("DATE_OF_INCEPTION") != null)

					if (rs.getString("DATE_OF_EXIT") != null) {
						claimantVO.setDateOfExit(new Date(rs.getTimestamp(
								"DATE_OF_EXIT").getTime()));
					}// end of if(rs.getString("DATE_OF_EXIT") != null)

					if (rs.getString("MEM_TOT_SUM_INSURED") != null) {
						claimantVO.setTotalSumInsured(new BigDecimal(rs
								.getString("MEM_TOT_SUM_INSURED")));
					}// end of if(rs.getString("MEM_TOT_SUM_INSURED") != null)

					if (rs.getString("AVAIL_SUM_INSURED") != null) {
						claimantVO.setAvailSumInsured(new BigDecimal(rs
								.getString("AVAIL_SUM_INSURED")));
					}// end of if(rs.getString("AVAIL_SUM_INSURED") != null)

					claimantVO.setCategoryTypeID(TTKCommon.checkNull(rs
							.getString("CATEGORY_GENERAL_TYPE_ID")));
					claimantVO.setCategoryDesc(TTKCommon.checkNull(rs
							.getString("CATEGORY")));
					claimantVO.setPolicyTypeID(TTKCommon.checkNull(rs
							.getString("ENROL_TYPE_ID")));

					if (rs.getString("PRODUCT_SEQ_ID") != null) {
						claimantVO.setProductSeqID(new Long(rs
								.getLong("PRODUCT_SEQ_ID")));
					}// end of if(rs.getString("PRODUCT_SEQ_ID") != null)

					claimantVO.setPolicySubTypeID(TTKCommon.checkNull(rs
							.getString("POLICY_SUB_GENERAL_TYPE_ID")));
					claimantVO.setTermStatusID(TTKCommon.checkNull(rs
							.getString("INS_STATUS_GENERAL_TYPE_ID")));
					claimantVO.setPhone(TTKCommon.checkNull(rs
							.getString("PHONE")));

					if (rs.getString("EFFECTIVE_FROM_DATE") != null) {
						claimantVO.setStartDate(new Date(rs.getTimestamp(
								"EFFECTIVE_FROM_DATE").getTime()));
					}// end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if (rs.getString("EFFECTIVE_TO_DATE") != null) {
						claimantVO.setEndDate(new Date(rs.getTimestamp(
								"EFFECTIVE_TO_DATE").getTime()));
					}// end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

					if (rs.getString("POLICY_SEQ_ID") != null) {
						claimantVO.setPolicySeqID(new Long(rs
								.getLong("POLICY_SEQ_ID")));
					}// end of if(rs.getString("POLICY_SEQ_ID") != null)

					claimantVO.setBufferAllowedYN(TTKCommon.checkNull(rs
							.getString("BUFFER_ALLOWED_YN")));

					if (rs.getString("TOTAL_BUFFER_AMOUNT") != null) {
						claimantVO.setTotBufferAmt(new BigDecimal(rs
								.getString("TOTAL_BUFFER_AMOUNT")));
					}// end of if(rs.getString("TOTAL_BUFFER_AMOUNT") != null)

					claimantVO.setSchemeCertNbr(TTKCommon.checkNull(rs
							.getString("SCHEME_OR_CERTFICATE")));

					if (rs.getString("INS_HEAD_OFFICE_SEQ_ID") != null) {
						claimantVO.setInsHeadOffSeqID(new Long(rs
								.getLong("INS_HEAD_OFFICE_SEQ_ID")));
					}// end of if(rs.getString("INS_HEAD_OFFICE_SEQ_ID") !=
						// null)

					claimantVO.setRelationTypeID(TTKCommon.checkNull(rs
							.getString("RELSHIP_TYPE_ID")));
					claimantVO.setEmailID(TTKCommon.checkNull(rs
							.getString("EMAIL_ID")));
					claimantVO.setInsCustCode(TTKCommon.checkNull(rs
							.getString("INS_CUSTOMER_CODE")));
					claimantVO.setCertificateNo(TTKCommon.checkNull(rs
							.getString("CERTIFICATE_NO")));
					claimantVO.setInsScheme(TTKCommon.checkNull(rs
							.getString("INS_SCHEME")));
					claimantVO.setCreditCardNbr(TTKCommon.checkNull(rs
							.getString("CREDITCARD_NO")));
					//					alResultList.add(claimantVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			//			return (ArrayList)alResultList;
			return claimantVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getClaimantList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getClaimantList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getClaimantList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getClaimantList(ArrayList alSearchCriteria)

	/**
	 * This method returns the Arraylist of EnhancementVO's which contains
	 * SumInsured Enhancement details
	 * 
	 * @param alSearchCriteria
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of EnhancementVO object which contains SumInsured
	 *         Enhancement details
	 * @exception throws TTKException
	 */
	public ArrayList getSumInsuredEnhancementList(ArrayList alSearchCriteria)
			throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		EnhancementVO enhancementVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strEnhancementList);
			cStmtObject.setLong(1, (Long) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(4));
			cStmtObject.setString(3, (String) alSearchCriteria.get(5));
			cStmtObject.setLong(4, (Long) alSearchCriteria.get(1));
			cStmtObject.registerOutParameter(5, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(5);
			if (rs != null) {
				while (rs.next()) {
					enhancementVO = new EnhancementVO();

					if (rs.getString("POLICY_SEQ_ID") != null) {
						enhancementVO.setPolicySeqID(new Long(rs
								.getLong("POLICY_SEQ_ID")));
					}// end of if(rs.getString("POLICY_SEQ_ID") != null)

					enhancementVO.setPolicyNo(TTKCommon.checkNull(rs
							.getString("POLICY_NUMBER")));

					if (rs.getString("EFFECTIVE_FROM_DATE") != null) {
						enhancementVO.setStartDate(new Date(rs.getTimestamp(
								"EFFECTIVE_FROM_DATE").getTime()));
					}// end of if(rs.getString("EFFECTIVE_FROM_DATE") != null)

					if (rs.getString("EFFECTIVE_TO_DATE") != null) {
						enhancementVO.setEndDate(new Date(rs.getTimestamp(
								"EFFECTIVE_TO_DATE").getTime()));
					}// end of if(rs.getString("EFFECTIVE_TO_DATE") != null)

					enhancementVO.setSumInsured(TTKCommon.checkNull(rs
							.getString("FLOATER_SUM_INSURED")));
					alResultList.add(enhancementVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getSumInsuredEnhancementList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getSumInsuredEnhancementList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getSumInsuredEnhancementList()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getSumInsuredEnhancementList(ArrayList alSearchCriteria)

	/**
	 * This method returns the AuthorizationVO, which contains Authorization
	 * Details
	 * 
	 * @param alAuthList
	 *            ArrayList object which contains
	 *            MemberSeqID,PAT_GEN_DETAIL_SEQ_ID,PAT_ENROLL_DETAIL_SEQ_ID and
	 *            USER_SEQ_ID to get the Authorization Details
	 * @param strIdentifier
	 *            String which contains module identifer
	 *            Pre-Authorization/Claims
	 * @return AuthorizationVO object which contains Authorization Details
	 * @exception throws TTKException
	 */
	public AuthorizationVO getAuthorizationDetail(ArrayList alAuthList,
			String strIdentifier) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		AuthorizationVO authorizationVO = null;
		try {
			conn = ResourceManager.getConnection();

			if (strIdentifier.equals("Pre-Authorization")) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strAuthorizationDetails);
				if (alAuthList.get(0) != null) {
					cStmtObject.setLong(1, (Long) alAuthList.get(0));// Mandatory
				}// end of if(alAuthList.get(0) != null)
				else {
					cStmtObject.setString(1, null);
				}// end of else

				cStmtObject.setLong(2, (Long) alAuthList.get(1));// Mandatory
				cStmtObject.setLong(3, (Long) alAuthList.get(2));// Mandatory
				cStmtObject.setLong(4, (Long) alAuthList.get(3));// Mandatory
				cStmtObject.registerOutParameter(5, OracleTypes.CURSOR);
				cStmtObject.execute();
				rs = (java.sql.ResultSet) cStmtObject.getObject(5);
			}// end of if(strIdentifier.equals("Pre-Authorization"))
			else if (strIdentifier.equals("Claims")) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strSettlementDetails);
				cStmtObject.setLong(1, (Long) alAuthList.get(1));// Mandatory
				cStmtObject.setLong(2, (Long) alAuthList.get(2));// Mandatory
				cStmtObject.registerOutParameter(3, OracleTypes.CURSOR);
				cStmtObject.execute();
				rs = (java.sql.ResultSet) cStmtObject.getObject(3);
			}// end of else if(strIdentifier.equals("Claims"))

			if (rs != null) {
				while (rs.next()) {
					authorizationVO = new AuthorizationVO();

					if (rs.getString("ASSIGN_USERS_SEQ_ID") != null) {
						authorizationVO.setAssignUserSeqID(new Long(rs
								.getLong("ASSIGN_USERS_SEQ_ID")));
					}// end of if(rs.getString("ASSIGN_USERS_SEQ_ID") != null)

					authorizationVO.setSeniorCitizenYN(TTKCommon.checkNull(rs
							.getString("senior_citizen_yn")));// koc for
																// griavance

					if (rs.getString("AVA_SUM_INSURED") != null) {
						authorizationVO.setAvailSumInsured(new BigDecimal(rs
								.getString("AVA_SUM_INSURED")));
					}// end of if(rs.getString("AVA_SUM_INSURED") != null)
					else {
						authorizationVO.setAvailSumInsured(new BigDecimal(
								"0.00"));
					}// end of else

					if (rs.getString("AVA_CUM_BONUS") != null) {
						authorizationVO.setAvailCumBonus(new BigDecimal(rs
								.getString("AVA_CUM_BONUS")));
					}// end of if(rs.getString("AVA_CUM_BONUS") != null)
					else {
						authorizationVO
								.setAvailCumBonus(new BigDecimal("0.00"));
					}// end of else

					authorizationVO.setStatusTypeID(TTKCommon.checkNull(rs
							.getString("PAT_STATUS_GENERAL_TYPE_ID")));

					if (rs.getString("BUFF_DETAIL_SEQ_ID") != null) {
						authorizationVO.setBufferDtlSeqID(new Long(rs
								.getLong("BUFF_DETAIL_SEQ_ID")));
					}// end of if(rs.getString("BUFF_DETAIL_SEQ_ID") != null)

					authorizationVO.setAuthorizedBy(TTKCommon.checkNull(rs
							.getString("CONTACT_NAME")));
					authorizationVO.setReasonTypeID(TTKCommon.checkNull(rs
							.getString("RSON_GENERAL_TYPE_ID")));
					authorizationVO.setAuthPermittedBy(TTKCommon.checkNull(rs
							.getString("PERMISSION_SOUGHT_FROM")));
					authorizationVO.setDiscPresentYN(TTKCommon.checkNull(rs
							.getString("DISCRIPENCY_PRESENET_YN")));

					if (rs.getString("COMPLETED_DATE") != null) {
						authorizationVO.setAuthorizedDate(new Date(rs
								.getTimestamp("COMPLETED_DATE").getTime()));
						authorizationVO.setAuthorizedTime(TTKCommon
								.getFormattedDateHour(
										new Date(rs.getTimestamp(
												"COMPLETED_DATE").getTime()))
								.split(" ").length >= 3 ? TTKCommon
								.getFormattedDateHour(
										new Date(rs.getTimestamp(
												"COMPLETED_DATE").getTime()))
								.split(" ")[1] : "");
						authorizationVO.setAuthorizedDay(TTKCommon
								.getFormattedDateHour(
										new Date(rs.getTimestamp(
												"COMPLETED_DATE").getTime()))
								.split(" ").length >= 3 ? TTKCommon
								.getFormattedDateHour(
										new Date(rs.getTimestamp(
												"COMPLETED_DATE").getTime()))
								.split(" ")[2] : "");
					}// end of if(rs.getString("COMPLETED_DATE") != null)

					if (rs.getString("BALANCE_SEQ_ID") != null) {
						authorizationVO.setBalanceSeqID(new Long(rs
								.getLong("BALANCE_SEQ_ID")));
					}// end of if(rs.getString("BALANCE_SEQ_ID") != null)

					if (rs.getString("DISCOUNT_AMOUNT") != null) {
						authorizationVO.setDiscountAmount(new BigDecimal(rs
								.getString("DISCOUNT_AMOUNT")));
					}// end of if(rs.getString("DISCOUNT_AMOUNT") != null)
					else {
						authorizationVO
								.setDiscountAmount(new BigDecimal("0.00"));
					}// end of else

					if (rs.getString("CO_PAYMENT_AMOUNT") != null) {
						authorizationVO.setCopayAmount(new BigDecimal(rs
								.getString("CO_PAYMENT_AMOUNT")));
					}// end of if(rs.getString("CO_PAYMENT_AMOUNT") != null)
					else {
						authorizationVO.setCopayAmount(new BigDecimal("0.00"));
					}// end of else

					if (rs.getString("MEMBER_SEQ_ID") != null) {
						authorizationVO.setMemberSeqID(new Long(rs
								.getLong("MEMBER_SEQ_ID")));
					}// end of if(rs.getString("MEMBER_SEQ_ID") != null)

					if (rs.getString("POLICY_SEQ_ID") != null) {
						authorizationVO.setPolicySeqID(new Long(rs
								.getLong("POLICY_SEQ_ID")));
					}// end of if(rs.getString("POLICY_SEQ_ID") != null)

					authorizationVO.setEnrolTypeID(TTKCommon.checkNull(rs
							.getString("ENROL_TYPE_ID")));

					if (rs.getString("DATE_OF_HOSPITALIZATION") != null) {
						authorizationVO.setClaimAdmnDate(new Date(rs
								.getTimestamp("DATE_OF_HOSPITALIZATION")
								.getTime()));
					}// end of if(rs.getString("DATE_OF_HOSPITALIZATION") !=
						// null)

					if (rs.getString("un_available_suminsured") != null) {
						authorizationVO.setUnAvailableSuminsured(new Integer(rs
								.getInt("un_available_suminsured")));
					}// end of if(rs.getString("un_available_suminsured") !=
						// null)

					if (rs.getString("POLICY_GROUP_SEQ_ID") != null) {
						authorizationVO.setPolicyGrpSeqID(new Long(rs
								.getLong("POLICY_GROUP_SEQ_ID")));
					}// end of if(rs.getString("POLICY_GROUP_SEQ_ID") !=null)

					if (strIdentifier.equals("Pre-Authorization")) {
						if (rs.getString("PAT_GEN_DETAIL_SEQ_ID") != null) {
							authorizationVO.setPreAuthSeqID(new Long(rs
									.getLong("PAT_GEN_DETAIL_SEQ_ID")));
						}// end of if(rs.getString("PAT_GEN_DETAIL_SEQ_ID") !=
							// null)

						if (rs.getString("MEM_TOTAL_SUM_INSURED") != null) {
							authorizationVO.setTotalSumInsured(new BigDecimal(
									rs.getString("MEM_TOTAL_SUM_INSURED")));
						}// end of if(rs.getString("MEM_TOTAL_SUM_INSURED") !=
							// null)
						else {
							authorizationVO.setTotalSumInsured(new BigDecimal(
									"0.00"));
						}// end of else

						if (rs.getString("PREV_APPROVED_AMOUNT") != null) {
							authorizationVO
									.setPrevApprovedAmount(new BigDecimal(rs
											.getString("PREV_APPROVED_AMOUNT")));
						}// end of if(rs.getString("PREV_APPROVED_AMOUNT") !=
							// null)
						else {
							authorizationVO
									.setPrevApprovedAmount(new BigDecimal(
											"0.00"));
						}// end of else

						if (rs.getString("PAT_REQUESTED_AMOUNT") != null) {
							authorizationVO.setRequestedAmount(new BigDecimal(
									rs.getString("PAT_REQUESTED_AMOUNT")));
						}// end of if(rs.getString("PAT_REQUESTED_AMOUNT") !=
							// null)
						else {
							authorizationVO.setRequestedAmount(new BigDecimal(
									"0.00"));
						}// end of else

						if (rs.getString("BUFFER_APP_AMOUNT") != null) {
							authorizationVO
									.setApprovedBufferAmount(new BigDecimal(rs
											.getString("BUFFER_APP_AMOUNT")));
						}// end of if(rs.getString("BUFFER_APP_AMOUNT") != null)
						else {
							authorizationVO
									.setApprovedBufferAmount(new BigDecimal(
											"0.00"));
						}// end of else

						authorizationVO.setAuthorizationNo(TTKCommon
								.checkNull(rs.getString("AUTH_NUMBER")));
						authorizationVO.setRemarks(TTKCommon.checkNull(rs
								.getString("AUTHORIZATION_REMARKS")));

						if (rs.getString("TOTAL_APP_AMOUNT") != null) {
							authorizationVO.setApprovedAmount(new BigDecimal(rs
									.getString("TOTAL_APP_AMOUNT")));
						}// end of if(rs.getString("TOTAL_APP_AMOUNT") != null)
						else {
							authorizationVO.setApprovedAmount(new BigDecimal(
									"0.00"));
						}// end of else

						if (rs.getString("MAX_ALLOWED_AMOUNT") != null) {
							authorizationVO.setMaxAllowedAmt(new BigDecimal(rs
									.getString("MAX_ALLOWED_AMOUNT")));
						}// end of if(rs.getString("MAX_ALLOWED_AMOUNT") !=
							// null)
						else {
							authorizationVO.setMaxAllowedAmt(new BigDecimal(
									"0.00"));
						}// end of else

						authorizationVO.setCompletedYN(TTKCommon.checkNull(rs
								.getString("COMPLETED_YN")));
						authorizationVO.setClauseRemarks(TTKCommon.checkNull(rs
								.getString("REJECTION_REMARKS")));
						authorizationVO.setAuthLtrTypeID(TTKCommon.checkNull(rs
								.getString("AUTH_LETTER_GENERAL_TYPE_ID")));
						/**
						 * Added as per KOC 1216B Changee request
						 *  
						 **/

						// 1. preauth_co_pay_buffer_amount, --display in
						// Pre-Auth Details tab
						// 2. co_payment_buffer_amount --display in Claims
						// Deductions tab

						/*
						 * if(rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT") !=
						 * null){ authorizationVO.setPreCopayBufferamount(new
						 * BigDecimal
						 * (rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT")));
						 * }//end of
						 * if(rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT") !=
						 * null) else {
						 * authorizationVO.setPreCopayBufferamount(new
						 * BigDecimal("0.00")); }//end of else
						 */
						if (rs.getString("CO_PAYMENT_BUFFER_AMOUNT") != null) {
							authorizationVO
									.setCopayBufferamount(new BigDecimal(
											rs.getString("CO_PAYMENT_BUFFER_AMOUNT")));
						}// end of if(rs.getString("CO_PAYMENT_BUFFER_AMOUNT")
							// != null)
						else {
							authorizationVO
									.setCopayBufferamount(new BigDecimal("0.00"));
						}// end of else
							// added for Policy Deductable - KOC-1277
						if (rs.getString("BAL_DEDUCTABLE_AMT") != null) {
							authorizationVO.setBalanceDedAmount(new BigDecimal(
									rs.getString("BAL_DEDUCTABLE_AMT")));
						} else {
							authorizationVO.setBalanceDedAmount(new BigDecimal(
									"0.00"));
						}
						// Start Modification As per Koc 1140(Sum Insured
						// Restriction)
						if (rs.getString("MEM_REST_SUM_INSURED") != null) {
							if (rs.getString("MEM_REST_SUM_INSURED") != ""
									|| rs.getString("MEM_REST_SUM_INSURED") != "0") {
								authorizationVO
										.setMemberRestrictedSIAmt(new BigDecimal(
												rs.getString("MEM_REST_SUM_INSURED")));
							} else {
								authorizationVO
										.setMemberRestrictedSIAmt(new BigDecimal(
												"0.00"));
						}
						}// end of if(rs.getString("MEM_REST_SUM_INSURED") !=
							// null)
						else {
							authorizationVO
									.setMemberRestrictedSIAmt(new BigDecimal(
											"0.00"));
							    }
						if (rs.getString("AVA_REST_SUM_INSURED") != null) {
							if (rs.getString("AVA_REST_SUM_INSURED") != ""
									|| rs.getString("AVA_REST_SUM_INSURED") != "0") {
								authorizationVO
										.setAvaRestrictedSIAmt(new BigDecimal(
												rs.getString("AVA_REST_SUM_INSURED")));
							}// end of if(rs.getString("AVA_REST_SUM_INSURED")
								// != null)
							else {
								authorizationVO
										.setAvaRestrictedSIAmt(new BigDecimal(
												"0.00"));
							}// end of else(rs.getString("AVA_REST_SUM_INSURED")
								// != null)
						} else {
							authorizationVO
									.setAvaRestrictedSIAmt(new BigDecimal(
											"0.00"));
						}// end of else(rs.getString("AVA_REST_SUM_INSURED") !=
							// null
						if (rs.getString("FAM_REST_YN") != null) {
							authorizationVO.setFamRestrictedYN(rs
									.getString("FAM_REST_YN"));
						}// end of if(rs.getString("FAM_REST_YN") != null)
						else {
						authorizationVO.setFamRestrictedYN("N");
						}
						/* Added as per KOC 1216B Changee request */
						if (rs.getString("PAT_INS_STATUS") != null) {
							if (rs.getString("PAT_INS_STATUS")
									.equalsIgnoreCase("INP")) {
								authorizationVO
										.setInsurerApprovedStatus("Awaiting Insurer Advice");// 1274A
							} else if (rs.getString("PAT_INS_STATUS")
									.equalsIgnoreCase("REJ")) {
								authorizationVO
										.setInsurerApprovedStatus("Revert received from Insurer");// denial
																									// process
							} else if (rs.getString("PAT_INS_STATUS")
									.equalsIgnoreCase("APR")) {
								authorizationVO
										.setInsurerApprovedStatus("Revert received from Insurer");// denial
																									// process
							} else if (rs.getString("PAT_INS_STATUS")
									.equalsIgnoreCase("REQ")) {
								authorizationVO
										.setInsurerApprovedStatus("Revert received from Insurer");
							} else {
								authorizationVO
										.setInsurerApprovedStatus(TTKCommon.checkNull(rs
												.getString("PAT_INS_STATUS")));
							}
							}
						authorizationVO.setInsremarks(TTKCommon.checkNull(rs
								.getString("PAT_INS_REMARKS")));
						// added for CR KOC - Mail-SMS Cigna
						authorizationVO.setMailNotifyYN(TTKCommon.checkNull(rs
								.getString("FINAL_APP_YN")));
						// added for Mail-SMS for Cigna
						authorizationVO.setCigna_Ins_Cust(TTKCommon
								.checkNull(rs.getString("Cigna_YN")));
						authorizationVO.setInsDecisionyn(TTKCommon.checkNull(rs
								.getString("INS_DECISION_YN")));// baja enhan
						authorizationVO.setDenialBanyn(TTKCommon.checkNull(rs
								.getString("DEN_BAN_YN")));// denial process
					}// end of if(strIdentifier.equals("Pre-Authorization"))

					else if (strIdentifier.equals("Claims")) {

						if (rs.getString("CLAIM_SEQ_ID") != null) {
							authorizationVO.setClaimSeqID(new Long(rs
									.getLong("CLAIM_SEQ_ID")));
						}// end of if(rs.getString("CLAIM_SEQ_ID") != null)

						if (rs.getString("CLM_ENROLL_DETAIL_SEQ_ID") != null) {
							authorizationVO.setClmEnrollDtlSeqID(new Long(rs
									.getLong("CLM_ENROLL_DETAIL_SEQ_ID")));
						}// end of if(rs.getString("CLM_ENROLL_DETAIL_SEQ_ID")
							// != null)

						if (rs.getString("BUFFER_HDR_SEQ_ID") != null) {
							authorizationVO.setBufferHdrSeqID(new Long(rs
									.getLong("BUFFER_HDR_SEQ_ID")));
						}// end of if(rs.getString("BUFFER_HDR_SEQ_ID") != null)

						if (rs.getString("TOT_SUM_INSURED") != null) {
							authorizationVO.setTotalSumInsured(new BigDecimal(
									rs.getString("TOT_SUM_INSURED")));
						}// end of if(rs.getString("MEM_TOTAL_SUM_INSURED") !=
							// null)
						else {
							authorizationVO.setTotalSumInsured(new BigDecimal(
									"0.00"));
						}// end of else

						/*
						 * if(rs.getString("BUFFER_APP_AMOUNT") != null){
						 * authorizationVO.setApprovedBufferAmount(new
						 * BigDecimal(rs.getString("BUFFER_APP_AMOUNT")));
						 * }//end of if(rs.getString("BUFFER_APP_AMOUNT") !=
						 * null) else{
						 * authorizationVO.setApprovedBufferAmount(new
						 * BigDecimal("0.00")); }//end of else
						 */
						if (rs.getString("PAT_APPROVED_AMOUNT") != null) {
							authorizationVO.setPAApprovedAmt(new BigDecimal(rs
									.getString("PAT_APPROVED_AMOUNT")));
						}// end of if(rs.getString("PAT_APPROVED_AMOUNT") !=
							// null)
						else {
							authorizationVO.setPAApprovedAmt(new BigDecimal(
									"0.00"));
						}// end of else

						if (rs.getString("REQUESTED_AMOUNT") != null) {
							authorizationVO.setRequestedAmount(new BigDecimal(
									rs.getString("REQUESTED_AMOUNT")));
						}// end of if(rs.getString("PAT_REQUESTED_AMOUNT") !=
							// null)
						else {
							authorizationVO.setRequestedAmount(new BigDecimal(
									"0.00"));
						}// end of else
						if (rs.getString("SERV_TAX_CALC_AMOUNT") != null) {
							authorizationVO.setTaxAmtPaid(new BigDecimal(rs
									.getString("SERV_TAX_CALC_AMOUNT")));
						}// end of if(rs.getString("SERV_TAX_CALC_AMOUNT")!=
							// null)
						else {
							authorizationVO
									.setTaxAmtPaid(new BigDecimal("0.00"));
						}// end of else
						if (rs.getString("SER_TAX_REQ_AMT") != null) {
							authorizationVO.setRequestedTaxAmt(new BigDecimal(
									rs.getString("SER_TAX_REQ_AMT")));
						}// end of if(rs.getString("SERV_TAX_CALC_AMOUNT")!=
							// null)
						else {
							authorizationVO.setRequestedTaxAmt(new BigDecimal(
									"0.00"));
						}// end of else

						if (rs.getString("TOTAL_APP_AMOUNT") != null) {
							authorizationVO.setApprovedAmount(new BigDecimal(rs
									.getString("TOTAL_APP_AMOUNT")));
						}// end of if(rs.getString("TOTAL_APP_AMOUNT") != null)

						else {
							authorizationVO.setApprovedAmount(new BigDecimal(
									"0.00"));
						}// end of else

						// added for KOC-1273
						authorizationVO
								.setClaimSubGeneralTypeId(TTKCommon.checkNull(rs
										.getString("claim_sub_general_type_id")));

						if (rs.getString("MAX_ALLOWED_AMOUNT") != null) {
							authorizationVO.setMaxAllowedAmt(new BigDecimal(rs
									.getString("MAX_ALLOWED_AMOUNT")));
						}// end of if(rs.getString("MAX_ALLOWED_AMOUNT") !=
							// null)
						else {
							authorizationVO.setMaxAllowedAmt(new BigDecimal(
									"0.00"));
						}// end of else

						authorizationVO.setAuthorizationNo(TTKCommon
								.checkNull(rs
										.getString("CLAIM_SETTLEMENT_NUMBER")));

						if (rs.getString("CLAIM_APP_BUFFER_AMOUNT") != null) {
							authorizationVO
									.setApprovedBufferAmount(new BigDecimal(
											rs.getString("CLAIM_APP_BUFFER_AMOUNT")));
						}// end of if(rs.getString("BUFFER_APP_AMOUNT") != null)
						else {
							authorizationVO
									.setApprovedBufferAmount(new BigDecimal(
											"0.00"));
						}// end of else
						if (rs.getString("FINAL_APP_AMOUNT") != null) {
							authorizationVO.setFinalApprovedAmt(new BigDecimal(
									rs.getString("FINAL_APP_AMOUNT")));
						}// end of if(rs.getString("FINAL_APP_AMOUNT") != null)
						else {
							authorizationVO.setFinalApprovedAmt(new BigDecimal(
									"0.00"));
						}// end of else

						authorizationVO.setRemarks(TTKCommon.checkNull(rs
								.getString("REMARKS")));
						authorizationVO
								.setClaimSubTypeID(TTKCommon.checkNull(rs
										.getString("CLAIM_SUB_GENERAL_TYPE_ID")));
						authorizationVO.setCompletedYN(TTKCommon.checkNull(rs
								.getString("COMPLETED_YN")));
						authorizationVO.setCalcButDispYN(TTKCommon.checkNull(rs
								.getString("CALC_BUTTON_DISP_YN")));
						authorizationVO.setPressButManYN(TTKCommon.checkNull(rs
								.getString("PRESS_BUTTON_MAN_YN")));
						// added for Mail-SMS for Cigna
						authorizationVO.setCigna_Ins_Cust(TTKCommon
								.checkNull(rs.getString("Cigna_YN")));

						if (rs.getString("AVA_DOMICILARY_AMOUNT") != null) {
							authorizationVO.setAvailDomTrtLimit(new BigDecimal(
									rs.getString("AVA_DOMICILARY_AMOUNT")));
						}// end of if(rs.getString("AVA_DOMICILARY_AMOUNT") !=
							// null)
						else {
							authorizationVO.setAvailDomTrtLimit(new BigDecimal(
									"0.00"));
						}// end of else

						// INVESTIGATION UAT
						if (rs.getString("INV_DISALLOWED_AMT") != null) {
							authorizationVO
									.setInvDisallowdedAmt(new BigDecimal(rs
											.getString("INV_DISALLOWED_AMT")));
						}// end of if(rs.getString("AVA_DOMICILARY_AMOUNT") !=
							// null)
						else {
							authorizationVO
									.setInvDisallowdedAmt(new BigDecimal("0.00"));
						}// end of else
							// INVESTIGATION UAT
						authorizationVO.setClmapramt(TTKCommon.checkNull(rs
								.getString("CLM_APR_AMT")));

						authorizationVO.setDVMessageYN(TTKCommon.checkNull(rs
								.getString("DV_MESSAGE_YN")));
						authorizationVO.setClaimTypeID(TTKCommon.checkNull(rs
								.getString("CLAIM_GENERAL_TYPE_ID")));

						if (rs.getString("PREAUTH_CO_PAYMENT_AMOUNT") != null) {
							authorizationVO.setPACopayAmt(new BigDecimal(rs
									.getString("PREAUTH_CO_PAYMENT_AMOUNT")));
						}// end of if(rs.getString("PREAUTH_CO_PAYMENT_AMOUNT")
							// != null)
						else {
							authorizationVO
									.setPACopayAmt(new BigDecimal("0.00"));
						}// end of else

						if (rs.getString("PREAUTH_DISCOUNT_AMOUNT") != null) {
							authorizationVO.setPADiscountAmt(new BigDecimal(rs
									.getString("PREAUTH_DISCOUNT_AMOUNT")));
						}// end of if(rs.getString("PREAUTH_DISCOUNT_AMOUNT") !=
							// null)
						else {
							authorizationVO.setPADiscountAmt(new BigDecimal(
									"0.00"));
						}// end of else

						if (rs.getString("DEPOSIT_AMOUNT") != null) {
							authorizationVO.setDepositAmt(new BigDecimal(rs
									.getString("DEPOSIT_AMOUNT")));
						}// end of if(rs.getString("DEPOSIT_AMOUNT") != null)
						else {
							authorizationVO
									.setDepositAmt(new BigDecimal("0.00"));
						}// end of else
						/**
						 * Added as per KOC 1216B Changee request
						 *  
						 **/

						// 1. preauth_co_pay_buffer_amount, --display in
						// Pre-Auth Details tab
						// 2. co_payment_buffer_amount --display in Claims
						// Deductions tab

						if (rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT") != null) {
							authorizationVO
									.setPreCopayBufferamount(new BigDecimal(
											rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT")));
						}// end of
							// if(rs.getString("PREAUTH_CO_PAY_BUFFER_AMOUNT")
							// != null)
						else {
							authorizationVO
									.setPreCopayBufferamount(new BigDecimal(
											"0.00"));
						}// end of else

						if (rs.getString("CO_PAYMENT_BUFFER_AMOUNT") != null) {
							authorizationVO
									.setCopayBufferamount(new BigDecimal(
											rs.getString("CO_PAYMENT_BUFFER_AMOUNT")));
						}// end of if(rs.getString("CO_PAYMENT_BUFFER_AMOUNT")
							// != null)
						else {
							authorizationVO
									.setCopayBufferamount(new BigDecimal("0.00"));
						}// end of else
							// Start Modification As per Koc 1140(Sum Insured
							// Restriction)
						if (rs.getString("MEM_REST_SUM_INSURED") != null) {
							if (rs.getString("MEM_REST_SUM_INSURED") != ""
									|| rs.getString("MEM_REST_SUM_INSURED") != "0") {
								authorizationVO
										.setMemberRestrictedSIAmt(new BigDecimal(
												rs.getString("MEM_REST_SUM_INSURED")));
							} else {
								authorizationVO
										.setMemberRestrictedSIAmt(new BigDecimal(
												"0.00"));
     						}

						}// end of if(rs.getString("MEM_REST_SUM_INSURED") !=
							// null)
						else {
							authorizationVO
									.setMemberRestrictedSIAmt(new BigDecimal(
											"0.00"));
							}
						if (rs.getString("AVA_REST_SUM_INSURED") != null) {
							if (rs.getString("AVA_REST_SUM_INSURED") != ""
									|| rs.getString("AVA_REST_SUM_INSURED") != "0") {
								authorizationVO
										.setAvaRestrictedSIAmt(new BigDecimal(
												rs.getString("AVA_REST_SUM_INSURED")));
							}// end of if(rs.getString("AVA_REST_SUM_INSURED")
								// != null)
							else {
								authorizationVO
										.setAvaRestrictedSIAmt(new BigDecimal(
												"0.00"));
							}// end of else(rs.getString("AVA_REST_SUM_INSURED")
								// != null)
						}// if(rs.getString("MEM_REST_SUM_INSURED") !=
							// null)?"":""
						else {
							authorizationVO
									.setAvaRestrictedSIAmt(new BigDecimal(
											"0.00"));
						}// end of else(rs.getString("AVA_REST_SUM_INSURED") !=
							// null)
						if (rs.getString("FAM_REST_YN") != null) {
							authorizationVO.setFamRestrictedYN(rs
									.getString("FAM_REST_YN"));
						}// end of if(rs.getString("FAM_REST_YN") != null)
						else {
							authorizationVO.setFamRestrictedYN("N");
							}
						// end Modification As per Koc 1140(Sum Insured
						// Restriction)
						/**
						 * Added as per KOC 1216B Changee request
						 *  
						 **/
						// koc1216A
						authorizationVO
								.setAdditionalDomicialaryYN(TTKCommon.checkNull(rs
										.getString("additional_domicilary_yn")));
						// added for Policy_deductable - KOC-1277
						if (rs.getString("BAL_DEDUCTABLE_AMT") != null) {
							authorizationVO.setBalanceDedAmount(new BigDecimal(
									rs.getString("BAL_DEDUCTABLE_AMT")));
						} else {
							authorizationVO.setBalanceDedAmount(new BigDecimal(
									"0.00"));
						}
						// added for Policy Deductable - KOC-1277.
						authorizationVO
								.setPolicy_deductable_yn(TTKCommon.checkNull(rs
										.getString("policy_deductable_yn")));
						authorizationVO.setPreauthDifference(TTKCommon
								.checkNull(rs.getString("pat_diff")));
						authorizationVO.setPreauthYN(TTKCommon.checkNull(rs
								.getString("pat_yn")));
						//
						// KOC 1286 for OPD
						authorizationVO.setOPDAmountYN(TTKCommon.checkNull(rs
								.getString("OPD_BENEFITS_YN")));
						if (rs.getString("CLM_INS_STATUS") != null) {
							if (rs.getString("CLM_INS_STATUS")
									.equalsIgnoreCase("INP")) {
								authorizationVO
										.setInsurerApprovedStatus("Awaiting Insurer Advice");// 1274A
							} else if (rs.getString("CLM_INS_STATUS")
									.equalsIgnoreCase("REJ")) {
								authorizationVO
										.setInsurerApprovedStatus("Revert received from Insurer");// denial
																									// process
							} else if (rs.getString("CLM_INS_STATUS")
									.equalsIgnoreCase("APR")) {
								authorizationVO
										.setInsurerApprovedStatus("Revert received from Insurer");// denial
																									// process
							} else if (rs.getString("CLM_INS_STATUS")
									.equalsIgnoreCase("REQ")) {
								authorizationVO
										.setInsurerApprovedStatus("Revert received from Insurer");
							} else {
								authorizationVO
										.setInsurerApprovedStatus(TTKCommon.checkNull(rs
												.getString("CLM_INS_STATUS")));
							}
							}
						authorizationVO.setInsremarks(TTKCommon.checkNull(rs
								.getString("CLM_INS_REMARKS")));
						// added for CR KOC - Mail-SMS Cigna
						authorizationVO.setMailNotifyYN(TTKCommon.checkNull(rs
								.getString("FINAL_APP_YN")));
						authorizationVO.setInsDecisionyn(TTKCommon.checkNull(rs
								.getString("INS_DECISION_YN")));// baja enhan
						authorizationVO.setDenialBanyn(TTKCommon.checkNull(rs
								.getString("DEN_BAN_YN")));// denial process
						// KOC 1286 for OPD
					}// end of else if(strIdentifier.equals("Claims"))
				}// end of while(rs.next())
			}// end of if(rs != null)
			return authorizationVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getAuthorizationDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getAuthorizationDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getAuthorizationDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getAuthorizationDetail(ArrayList alAuthList)

	/**
	 * This method saves the Authorization information
	 * 
	 * @param authorizationVO
	 *            AuthorizationVO contains Authorization information
	 * @param strIdentifier
	 *            Pre-Authorization/Claims
	 * @return long int the value which returns greater than zero for
	 *         succcesssful execution of the task
	 * @exception throws TTKException
	 */
	public int saveAuthorization(AuthorizationVO authorizationVO,
			String strIdentifier) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();

			if (strIdentifier.equalsIgnoreCase("Pre-Authorization")) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strSaveAuthorization);
				if (authorizationVO.getPreAuthSeqID() != null) {
					cStmtObject.setLong(1, authorizationVO.getPreAuthSeqID());
				}// end of if(authorizationVO.getPreAuthSeqID() != null)
				else {
					cStmtObject.setString(1, null);
				}// end of else

				if (authorizationVO.getEnrollDtlSeqID() != null) {
					cStmtObject.setLong(2, authorizationVO.getEnrollDtlSeqID());
				}// end of if(authorizationVO.getEnrollDtlSeqID() != null)
				else {
					cStmtObject.setString(2, null);
				}// end of else

				cStmtObject.setString(3, authorizationVO.getAuthorizationNo());

				if (authorizationVO.getTotalSumInsured() != null) {
					cStmtObject.setBigDecimal(4,
							authorizationVO.getTotalSumInsured());
				}// end of if(authorizationVO.getTotalSumInsured() != null)
				else {
					cStmtObject.setString(4, null);
				}// en of else

				cStmtObject.setString(5, authorizationVO.getStatusTypeID());
				cStmtObject.setString(6, authorizationVO.getReasonTypeID());
				cStmtObject.setString(7, authorizationVO.getRemarks());

				if (authorizationVO.getAssignUserSeqID() != null) {
					cStmtObject
							.setLong(8, authorizationVO.getAssignUserSeqID());
				}// end of if(authorizationVO.getAssignUserSeqID() != null)
				else {
					cStmtObject.setString(8, null);
				}// end of else

				cStmtObject.setString(9, authorizationVO.getAuthPermittedBy());

				if (authorizationVO.getBalanceSeqID() != null) {
					cStmtObject.setLong(10, authorizationVO.getBalanceSeqID());
				}// end of if(authorizationVO.getBalanceSeqID() != null)
				else {
					cStmtObject.setString(10, null);
				}// end of else

				if (authorizationVO.getApprovedAmount() != null) {
					cStmtObject.setBigDecimal(11,
							authorizationVO.getApprovedAmount());
				}// end of if(authorizationVO.getApprovedAmount() != null)
				else {
					cStmtObject.setString(11, null);
				}// end of else

				if (authorizationVO.getDiscountAmount() != null) {
					cStmtObject.setBigDecimal(12,
							authorizationVO.getDiscountAmount());
				}// end of if(authorizationVO.getDiscountAmount() != null)
				else {
					cStmtObject.setString(12, null);
				}// end of else

				if (authorizationVO.getCopayAmount() != null) {
					cStmtObject.setBigDecimal(13,
							authorizationVO.getCopayAmount());
				}// end of if(authorizationVO.getCopayAmount() != null)
				else {
					cStmtObject.setString(13, null);
				}// end of else
					// ADDED AS PER KOC 1216B Change Request on 11 jan 2012

				// v_co_pay_buff_amount IN OUT
				// pat_general_details.co_payment_buffer_amount%TYPE, -- new
				// parameter
				if (authorizationVO.getCopayBufferamount() != null) {
					cStmtObject.setBigDecimal(14,
							authorizationVO.getCopayBufferamount());
				}// end of if(authorizationVO.getCopayBufferamount() != null)
				else {
					cStmtObject.setString(14, null);
				}// end of else
				cStmtObject.setLong(15, authorizationVO.getUpdatedBy());

				// Start Modification as per KOC 1140(SUM INSURED RESTRICTION)
				if (authorizationVO.getMemberRestrictedSIAmt() != null) {
					cStmtObject.setBigDecimal(16,
							authorizationVO.getMemberRestrictedSIAmt());

				}// end of if(authorizationVO.getMemberRestrictedSIAmt() !=
					// null)
				else {
					cStmtObject.setString(16, null);
				}// end of else getMemberRestrictedSIAmt
				if (authorizationVO.getAvaRestrictedSIAmt() != null) {
					cStmtObject.setBigDecimal(17,
							authorizationVO.getAvaRestrictedSIAmt());

				}// end of if(authorizationVO.getAvaRestrictedSIAmt() != null)
				else {
					cStmtObject.setString(17, null);
				}// end of else getAvaRestrictedSIAmt
				if (authorizationVO.getFamRestrictedYN() != null) {
					cStmtObject.setString(18,
							authorizationVO.getFamRestrictedYN());

				}// end of if(authorizationVOgetFamRestrictedYN() != null)
				else {
					cStmtObject.setString(18, null);
				}// end of else getFamRestrictedYN
				// END Modification as per KOC 1140(SUM INSURED RESTRICTION)

				// added for Policy deductable - KOC-1277
				if (authorizationVO.getBalanceDedAmount() != null) {
					cStmtObject.setBigDecimal(19,
							authorizationVO.getBalanceDedAmount());
				} else {
					cStmtObject.setString(19, null);
				}
				// added for CR KOC Mail-SMS for Cigna

				cStmtObject.setString(20, authorizationVO.getMailNotifyYN());
				cStmtObject.registerOutParameter(21, Types.INTEGER);
				cStmtObject.execute();
                iResult = cStmtObject.getInt(21);
				/*
				 * cStmtObject.registerOutParameter(16,Types.INTEGER);
				 * cStmtObject.execute(); iResult = cStmtObject.getInt(16);
				 */
			}// end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization"))

			else if (strIdentifier.equalsIgnoreCase("Claims")) {

				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strSaveSettlement);
				if (authorizationVO.getAssignUserSeqID() != null) {
					cStmtObject
							.setLong(1, authorizationVO.getAssignUserSeqID());
				}// end of if(authorizationVO.getAssignUserSeqID() != null)
				else {
					cStmtObject.setString(1, null);
				}// end of else

				if (authorizationVO.getClaimSeqID() != null) {
					cStmtObject.setLong(2, authorizationVO.getClaimSeqID());
				}// end of if(authorizationVO.getClaimSeqID() != null)
				else {
					cStmtObject.setString(2, null);
				}// end of else

				if (authorizationVO.getTotalSumInsured() != null) {
					cStmtObject.setBigDecimal(3,
							authorizationVO.getTotalSumInsured());
				}// end of if(authorizationVO.getTotalSumInsured() != null)
				else {
					cStmtObject.setString(3, null);
				}// end of else

				cStmtObject.setString(4, authorizationVO.getRemarks());
				cStmtObject.setString(5, authorizationVO.getAuthorizationNo());
				cStmtObject.setString(6, authorizationVO.getStatusTypeID());
				cStmtObject.setString(7, authorizationVO.getAuthPermittedBy());

				if (authorizationVO.getBalanceSeqID() != null) {
					cStmtObject.setLong(8, authorizationVO.getBalanceSeqID());
				}// end of if(authorizationVO.getBalanceSeqID() != null)
				else {
					cStmtObject.setString(8, null);
				}// end of else

				if (authorizationVO.getApprovedAmount() != null) {
					cStmtObject.setBigDecimal(9,
							authorizationVO.getApprovedAmount());
				}// end of if(authorizationVO.getApprovedAmount() != null)
				else {
					cStmtObject.setString(9, null);
				}// end of else

				cStmtObject.setLong(10, authorizationVO.getUpdatedBy());
				cStmtObject.setString(11, authorizationVO.getReasonTypeID());

				if (authorizationVO.getDiscountAmount() != null) {
					cStmtObject.setBigDecimal(12,
							authorizationVO.getDiscountAmount());
				}// end of if(authorizationVO.getDiscountAmount() != null)
				else {
					cStmtObject.setString(12, null);
				}// end of else

				if (authorizationVO.getCopayAmount() != null) {
					cStmtObject.setBigDecimal(13,
							authorizationVO.getCopayAmount());
				}// end of if(authorizationVO.getCopayAmount() != null)
				else {
					cStmtObject.setString(13, null);
				}// end of else

				if (authorizationVO.getDepositAmt() != null) {
					cStmtObject.setBigDecimal(14,
							authorizationVO.getDepositAmt());
				}// end of if(authorizationVO.getDepositAmt() != null)
				else {
					cStmtObject.setString(14, null);
				}// end of else
				if (authorizationVO.getTaxAmtPaid() != null) {
					cStmtObject.setBigDecimal(15,
							authorizationVO.getTaxAmtPaid());
				}// end of if(authorizationVO.getTaxAmtPaid() != null)
				else {
					cStmtObject.setString(15, null);
				}// end of else
				if (authorizationVO.getSerTaxCalPer() != null) {
					cStmtObject.setBigDecimal(16,
							authorizationVO.getSerTaxCalPer());
				}// end of if(authorizationVO.getSerTaxCalPer() != null)
				else {
					cStmtObject.setString(16, null);
				}// end of else

				// Added as per KOC 1216B Change Request in claims

				if (authorizationVO.getCopayBufferamount() != null) {
					cStmtObject.setBigDecimal(17,
							authorizationVO.getCopayBufferamount());
				}// end of if(authorizationVO.getCopayBufferamount() != null)
				else {
					cStmtObject.setString(17, null);
				}// end of else

				// Start Modification as per KOC 1140(SUM INSURED RESTRICTION)
				if (authorizationVO.getMemberRestrictedSIAmt() != null) {
					cStmtObject.setBigDecimal(18,
							authorizationVO.getMemberRestrictedSIAmt());

				}// end of if(authorizationVO.getMemberRestrictedSIAmt() !=
					// null)
				else {
					cStmtObject.setString(18, null);
				}// end of else getMemberRestrictedSIAmt
				if (authorizationVO.getAvaRestrictedSIAmt() != null) {
					cStmtObject.setBigDecimal(19,
							authorizationVO.getAvaRestrictedSIAmt());
				}// end of if(authorizationVO.getAvaRestrictedSIAmt() != null)
				else {
					cStmtObject.setString(19, null);
				}// end of else getAvaRestrictedSIAmt
				if (authorizationVO.getFamRestrictedYN() != null) {
					cStmtObject.setString(20,
							authorizationVO.getFamRestrictedYN());
				}// end of if(authorizationVOgetFamRestrictedYN() != null)
				else {
					cStmtObject.setString(20, null);
				}// end of else getFamRestrictedYN

				// added for POlicy Deductable - KOC-1277
				if (authorizationVO.getBalanceDedAmount() != null) {
					cStmtObject.setBigDecimal(21,
							authorizationVO.getBalanceDedAmount());
				} else {
					cStmtObject.setString(21, null);
					}

				// added for Mail-SMS Template for Cigna

				cStmtObject.setString(22, authorizationVO.getMailNotifyYN());

				if (authorizationVO.getInvDisallowdedAmt() != null) {
					cStmtObject.setBigDecimal(23,
							authorizationVO.getInvDisallowdedAmt());
				}// end of if(authorizationVO.getAvaRestrictedSIAmt() != null)
				else {
					cStmtObject.setString(23, null);
				}// end of else getAvaRestrictedSIAmt

				cStmtObject.registerOutParameter(24, Types.INTEGER);

				cStmtObject.execute();
                iResult = cStmtObject.getInt(24);
				// END Modification as per KOC 1140(SUM INSURED RESTRICTION)

				/*
				 * cStmtObject.registerOutParameter(18,Types.INTEGER);
				 * cStmtObject.execute(); iResult = cStmtObject.getInt(18);
				 */
			}// end of else
				// Added as per KOC 1216B Change Request in claims
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl saveAuthorization()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl saveAuthorization()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of saveAuthorization(AuthorizationVO authorizationVO)

	/**
	 * This method deletes the requested information from the database
	 * 
	 * @param alDeleteList
	 *            the arraylist of details of which has to be deleted
	 * @return int the value which returns greater than zero for succcesssful
	 *         execution of the task
	 * @exception throws TTKException
	 */

	public int deletePATGeneral(ArrayList alDeleteList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strDeletePATGeneral);
			cStmtObject.setString(1, (String) alDeleteList.get(0));// FLAG
																	// PAT/AIL/SFL/INV/PED/COU/BUFFER
			cStmtObject.setString(2, (String) alDeleteList.get(1));// CONCATENATED
																	// STRING OF
																	// delete
																	// SEQ_IDS

			if (alDeleteList.get(2) != null) {
				cStmtObject.setLong(3, (Long) alDeleteList.get(2));// Mandatory
																	// PAT_ENROLL_DETAIL_SEQ_ID
			}// end of if(alDeleteList.get(2) != null)
			else {
				cStmtObject.setString(3, null);
			}// end of else

			if (alDeleteList.get(3) != null) {
				cStmtObject.setLong(4, (Long) alDeleteList.get(3));// Mandatory
																	// PAT_GENERAL_DETAIL_SEQ_ID
			}// end of if(alDeleteList.get(2) != null)
			else {
				cStmtObject.setString(4, null);
			}// end of else

			cStmtObject.setLong(5, (Long) alDeleteList.get(4));// ADDED_BY
			cStmtObject.registerOutParameter(6, Types.INTEGER);// ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);// ROWS_PROCESSED
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl deletePATGeneral()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl deletePATGeneral()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deletePATGeneral(ArrayList alDeleteList)

	/**
	 * This method returns the ArrayList, which contains Discrepancy Details
	 * 
	 * @param alDiscrepancyList
	 *            lwhich contains PAT_GEN_DETAIL_SEQ_ID,CLAIM_SEQ_ID,USER_SEQ_ID
	 *            to get the Discrepancy Details
	 * @return ArrayList object which contains Discrepancy Details
	 * @exception throws TTKException
	 */
	public ArrayList getDiscrepancyInfo(ArrayList alDiscrepancyList)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		DiscrepancyVO discrepancyVO = null;
		ArrayList<Object> alDiscrepancyInfo = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strGetDiscrepancy);

			if (alDiscrepancyList.get(0) != null) {
				cStmtObject.setLong(1, (Long) alDiscrepancyList.get(0));
			}// end of if(alDiscrepancyList.get(0) != null)
			else {
				cStmtObject.setString(1, null);
			}// end of else

			if (alDiscrepancyList.get(1) != null) {
				cStmtObject.setLong(2, (Long) alDiscrepancyList.get(1));
			}// end of if(alDiscrepancyList.get(1) != null)
			else {
				cStmtObject.setString(2, null);
			}// end of else

			cStmtObject.setLong(3, (Long) alDiscrepancyList.get(2));
			cStmtObject.registerOutParameter(4, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(4);
			alDiscrepancyInfo = new ArrayList<Object>();
			if (rs != null) {
				while (rs.next()) {
					discrepancyVO = new DiscrepancyVO();

					if (rs.getString("DISCREPANCY_SEQ_ID") != null) {
						discrepancyVO.setDiscrepancySeqID(new Long(rs
								.getLong("DISCREPANCY_SEQ_ID")));
					}// end of if(rs.getString("DISCREPANCY_SEQ_ID") != null)

					discrepancyVO.setDiscrepancyTypeID(TTKCommon.checkNull(rs
							.getString("GENERAL_TYPE_ID")));

					if (rs.getString("REMARKS") != null) {
						discrepancyVO.setDiscrepancy(TTKCommon
								.checkNull(rs.getString("DISCREPANCY"))
								.concat(" - ").concat(rs.getString("REMARKS")));
					}// end of if(rs.getString("REMARKS") != null)
					else {
						discrepancyVO.setDiscrepancy(TTKCommon.checkNull(rs
								.getString("DISCREPANCY")));
					}// end of else

					discrepancyVO.setResolvedYN(TTKCommon.checkNull(rs
							.getString("RESOLVED_YN")));
					discrepancyVO.setResolvableYN(TTKCommon.checkNull(rs
							.getString("RESOLVABLE_YN")));
					alDiscrepancyInfo.add(discrepancyVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
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
							"Error while closing the Resultset in PreAuthDAOImpl getDiscrepancyInfo()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getDiscrepancyInfo()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getDiscrepancyInfo()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return alDiscrepancyInfo;
	}// end of getDiscrepancyInfo(ArrayList alDiscrepancyList)

	/**
	 * This method resolves the Discrepancy information
	 * 
	 * @param alDiscrepancyVO
	 *            ArrayList contains Discrepancy information
	 * @return int the value which returns greater than zero for succcesssful
	 *         execution of the task
	 * @exception throws TTKException
	 */
	public int resolveDiscrepancy(ArrayList alDiscrepancyVO)
			throws TTKException {
		int iResult = 1;
		StringBuffer sbfSQL = null;
		Statement stmt = null;
		DiscrepancyVO discrepancyVO = null;
		Connection conn = null;
		try {
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			stmt = (java.sql.Statement) conn.createStatement();
			if (alDiscrepancyVO != null) {
				for (int i = 0; i < alDiscrepancyVO.size(); i++) {
					sbfSQL = new StringBuffer();
					discrepancyVO = (DiscrepancyVO) alDiscrepancyVO.get(i);
					sbfSQL = sbfSQL.append("'"
							+ discrepancyVO.getDiscrepancySeqID() + "',");
					sbfSQL = sbfSQL.append("'" + discrepancyVO.getResolvedYN()
							+ "',");
					sbfSQL = sbfSQL.append("'" + discrepancyVO.getUpdatedBy()
							+ "')}");
					stmt.addBatch(strResolveDiscrepancy + sbfSQL.toString());
				}// end of for
			}// end of if(alDiscrepancyVO != null)
			stmt.executeBatch();
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			try {
				conn.rollback();
				throw new TTKException(sqlExp, "preauth");
			} // end of try
			catch (SQLException sExp) {
				throw new TTKException(sExp, "preauth");
			}// end of catch (SQLException sExp)
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			try {
				conn.rollback();
				throw new TTKException(exp, "preauth");
			} // end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "preauth");
			}// end of catch (SQLException sqlExp)
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (stmt != null)
						stmt.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl resolveDiscrepancy()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl resolveDiscrepancy()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				stmt  = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of resolveDiscrepancy(ArrayList alDiscrepancyVO)

	/**
	 * This method will save the Review Information and Workflow will change to
	 * Coding
	 * 
	 * @param preauthDetailVO
	 *            the object which contains the Review Details which has to be
	 *            saved
	 * @param strMode
	 *            String object which contains Mode Pre-authorization/Claims -
	 *            PAT/CLM
	 * @return PreAuthDetailVO object which contains Review Information
	 * @exception throws TTKException
	 */
	public PreAuthDetailVO returnToCoding(PreAuthDetailVO preauthDetailVO,
			String strMode) throws TTKException {
		String strReview = "";
		Long lngEventSeqID = null;
		Integer intReviewCount = null;
		Integer intRequiredReviewCnt = null;
		String strEventName = "";
		String strCodingReviewYN = "";
		Connection conn = null;
		CallableStatement cStmtObject = null;

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strReturnToCoding);

			if (strMode.equals("PAT")) {
				if (preauthDetailVO.getPreAuthSeqID() != null) {
					cStmtObject.setLong(1, preauthDetailVO.getPreAuthSeqID());
				}// end of if(preauthDetailVO.getPreAuthSeqID() != null)
				else {
					cStmtObject.setString(1, null);
				}// end of else
			}// end of if(strMode.equals("PAT"))
			else {
				if (preauthDetailVO.getClaimSeqID() != null) {
					cStmtObject.setLong(1, preauthDetailVO.getClaimSeqID());
				}// end of if(preauthDetailVO.getClaimSeqID() != null)
				else {
					cStmtObject.setString(1, null);
				}// end of else
			}// end of else

			cStmtObject.setString(2, strMode);
			cStmtObject.setLong(3, preauthDetailVO.getUpdatedBy());

			if (preauthDetailVO.getEventSeqID() != null) {
				cStmtObject.setLong(4, preauthDetailVO.getEventSeqID());
			}// end of if(preauthDetailVO.getEventSeqID() != null)
			else {
				cStmtObject.setString(4, null);
			}// end of else

			if (preauthDetailVO.getReviewCount() != null) {
				cStmtObject.setInt(5, preauthDetailVO.getReviewCount());
			}// end of if(preauthDetailVO.getReviewCount() != null)
			else {
				cStmtObject.setString(5, null);
			}// end of else

			if (preauthDetailVO.getRequiredReviewCnt() != null) {
				cStmtObject.setLong(6, preauthDetailVO.getRequiredReviewCnt());
			}// end of if(preauthDetailVO.getRequiredReviewCnt() != null)
			else {
				cStmtObject.setString(6, null);
			}// end of else

			cStmtObject.registerOutParameter(4, Types.BIGINT);// EVENT_SEQ_ID
			cStmtObject.registerOutParameter(5, Types.BIGINT);// REVIEW_COUNT
			cStmtObject.registerOutParameter(6, Types.BIGINT);// REQUIRED_REVIEW_COUNT
			cStmtObject.registerOutParameter(7, Types.VARCHAR);// EVENT_NAME
			cStmtObject.registerOutParameter(8, Types.VARCHAR);// REVIEW
			cStmtObject.registerOutParameter(9, Types.VARCHAR);// CODING_REVIEW_YN
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
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl returnToCoding()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl returnToCoding()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return preauthDetailVO;
	}// end of returnToCoding(PreAuthDetailVO preauthDetailVO,String strMode)

	/**
	 * This method reassign the enrollment ID
	 * 
	 * @param alReAssignEnrID
	 *            the arraylist of details for which have to be reassigned
	 * @return int the value which returns greater than zero for succcesssful
	 *         execution of the task
	 * @exception throws TTKException
	 */
	public int reAssignEnrID(ArrayList alReAssignEnrID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strReAssignEnrID);
			cStmtObject.setLong(1, (Long) alReAssignEnrID.get(0));// PAT_GEN_DETAIL_SEQ_ID
			cStmtObject.setLong(2, (Long) alReAssignEnrID.get(1));// PAT_ENROLL_DETAIL_SEQ_ID
			cStmtObject.setString(3, (String) alReAssignEnrID.get(2));// PAT_STATUS_GENERAL_TYPE_ID
			cStmtObject.setLong(4, (Long) alReAssignEnrID.get(3));// MEMBER_SEQ_ID
			cStmtObject.setLong(5, (Long) alReAssignEnrID.get(4));// ADDED_BY
			cStmtObject.registerOutParameter(6, Types.INTEGER);// ROWS_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(6);// ROWS_PROCESSED
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl reAssignEnrID()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl reAssignEnrID()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of reAssignEnrID(ArrayList alReAssignEnrID)

	/**
	 * This method returns the SIInfoVO, which contains Balance SI Details.
	 * 
	 * @param lngMemSeqID
	 *            long value which contains member id to get the Balance SI
	 *            Details
	 * @param lngPolicyGrpSeqID
	 *            long value which contains Policy Group Seq ID
	 * @param lngBalanceSeqID
	 *            long value which contains Balance Seq ID
	 * @return PreAuthMedicalVO object which contains Balance SI Details
	 * @exception throws TTKException
	 */
	public SIInfoVO getBalanceSIDetail(long lngMemSeqID,
			long lngPolicyGrpSeqID, long lngBalanceSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		// Added as per KOC 1216B change request
		ResultSet rs4 = null;
		ResultSet rs5 = null;// //koc1289_1272
		MemberBufferVO memberBufferVO = null;
		// Added as per KOC 1216B change request
		SIInfoVO siInfoVO = new SIInfoVO();
	    BalanceSIInfoVO balanceSIInfoVO = null;
		SIBreakupInfoVO siBreakupInfoVO = null;
		StopPreauthClaimVO stopPreClmVO = null;
		RestorationPreauthClaimVO restorationPreauthClaimVO = null;
        ArrayList<Object> alSIBreakupList = new ArrayList<Object>();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strBalanceSIInfo);

			cStmtObject.setLong(1, lngMemSeqID);
			cStmtObject.setLong(2, lngPolicyGrpSeqID);
			cStmtObject.setLong(3, lngBalanceSeqID);
			cStmtObject.registerOutParameter(4, OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(5, OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(6, OracleTypes.CURSOR);
			// Added as per kOC 1216 b Change request
			cStmtObject.registerOutParameter(7, OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(8, OracleTypes.CURSOR);// koc1289_1272
			// Added as per kOC 1216 b Change request
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet) cStmtObject.getObject(4);
			rs2 = (java.sql.ResultSet) cStmtObject.getObject(5);
			rs3 = (java.sql.ResultSet) cStmtObject.getObject(6);
			// Added as per kOC 1216 b Change request
			rs4 = (java.sql.ResultSet) cStmtObject.getObject(7);
			// Added as per kOC 1216 b Change request
			rs5 = (java.sql.ResultSet) cStmtObject.getObject(8);// koc1289_1272
			if (rs1 != null) {
				while (rs1.next()) {
					balanceSIInfoVO = new BalanceSIInfoVO();

					if (rs1.getString("SUM_INSURED") != null) {
						balanceSIInfoVO.setTotalSumInsured(new BigDecimal(rs1
								.getString("SUM_INSURED")));
					}// end of if(rs1.getString("SUM_INSURED") != null)
					else {
						balanceSIInfoVO.setTotalSumInsured(new BigDecimal(
								"0.00"));
					}// end of else

					if (rs1.getString("BONUS") != null) {
						balanceSIInfoVO.setBonus(new BigDecimal(rs1
								.getString("BONUS")));
					}// end of if(rs1.getString("BONUS") != null)
					else {
						balanceSIInfoVO.setBonus(new BigDecimal("0.00"));
					}// end of else

					if (rs1.getString("BUFFER_AMOUNT") != null) {
						balanceSIInfoVO.setBufferAmt(new BigDecimal(rs1
								.getString("BUFFER_AMOUNT")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						balanceSIInfoVO.setBufferAmt(new BigDecimal("0.00"));
					}// end of else

					if (rs1.getString("UTILISED_SUM_INSURED") != null) {
						balanceSIInfoVO.setUtilizedSumInsured(new BigDecimal(
								rs1.getString("UTILISED_SUM_INSURED")));
					}// end of if(rs1.getString("UTILISED_SUM_INSURED") != null)
					else {
						balanceSIInfoVO.setUtilizedSumInsured(new BigDecimal(
								"0.00"));
					}// end of else

					if (rs1.getString("UTILISED_CUM_BONUS") != null) {
						balanceSIInfoVO.setUtilizedBonus(new BigDecimal(rs1
								.getString("UTILISED_CUM_BONUS")));
					}// end of if(rs1.getString("UTILISED_CUM_BONUS") != null)
					else {
						balanceSIInfoVO
								.setUtilizedBonus(new BigDecimal("0.00"));
					}// end of else

					balanceSIInfoVO.setPolSubType(TTKCommon.checkNull(rs1
							.getString("POLICY_SUB_TYPE")));
					balanceSIInfoVO.setBufferAllocation(TTKCommon.checkNull(rs1
							.getString("BUFFER_ALLOCATION")));

					// added for Policy Deductable - KOC-1277
					if (rs1.getString("Policy_deductable_amt") != null) {
						balanceSIInfoVO.setPolicyDeductableAmt(new BigDecimal(
								rs1.getString("POLICY_DEDUCTABLE_AMT")));
					}
					balanceSIInfoVO.setUtilizedPolicyDedAmt(new BigDecimal(rs1
							.getString("UTILISED_POLICY_DEDUCTABLE_AMT")));
					// ended

					// added for hyundai buffer cr

					// /set1
					if (rs1.getString("BUFFER_AMOUNT") != null) {
						balanceSIInfoVO.setNorCorpusBufAmt(new BigDecimal(rs1
								.getString("BUFFER_AMOUNT")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						balanceSIInfoVO.setNorCorpusBufAmt(new BigDecimal(
								"0.00"));
					}// end of else///set2

					if (rs1.getString("MED_BUFFER_AMOUNT") != null) {
						balanceSIInfoVO.setNorMedicalBufAmt(new BigDecimal(rs1
								.getString("MED_BUFFER_AMOUNT")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						balanceSIInfoVO.setNorMedicalBufAmt(new BigDecimal(
								"0.00"));
					}// end of else

					if (rs1.getString("CRITICAL_CORP_BUFF_AMOUNT") != null) {
						balanceSIInfoVO.setCriCorpusBufAmt(new BigDecimal(rs1
								.getString("CRITICAL_CORP_BUFF_AMOUNT")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						balanceSIInfoVO.setCriCorpusBufAmt(new BigDecimal(
								"0.00"));
					}// end of else

					if (rs1.getString("CRITICAL_MED_BUFF_AMOUNT") != null) {
						balanceSIInfoVO.setCriMedicalBufAmt(new BigDecimal(rs1
								.getString("CRITICAL_MED_BUFF_AMOUNT")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						balanceSIInfoVO.setCriMedicalBufAmt(new BigDecimal(
								"0.00"));
					}// end of else

					if (rs1.getString("CRITICAL_BUFF_AMOUNT") != null) {
						balanceSIInfoVO.setCriIllnessBufAmt(new BigDecimal(rs1
								.getString("CRITICAL_BUFF_AMOUNT")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						balanceSIInfoVO.setCriIllnessBufAmt(new BigDecimal(
								"0.00"));
					}// end of else

					// ////////////////////////////////

					if (rs1.getString("UTILISED_BUFFER") != null) {
						balanceSIInfoVO.setUtilizedBufferAmt(new BigDecimal(rs1
								.getString("UTILISED_BUFFER")));
					}// end of if(rs1.getString("UTILISED_BUFFER") != null)
					else {
						balanceSIInfoVO.setUtilizedBufferAmt(new BigDecimal(
								"0.00"));
					}// end of else

					if (rs1.getString("UTILISED_MED_BUFFER") != null) {
						balanceSIInfoVO
								.setUtilizedNorMedicalBufAmt(new BigDecimal(rs1
										.getString("UTILISED_MED_BUFFER")));
					}// end of if(rs1.getString("UTILISED_BUFFER") != null)
					else {
						balanceSIInfoVO
								.setUtilizedNorMedicalBufAmt(new BigDecimal(
										"0.00"));
					}// end of else

					if (rs1.getString("UTILISED_CRIT_BUFFER") != null) {
						balanceSIInfoVO
								.setUtilizedCriIllnessBufAmt(new BigDecimal(rs1
										.getString("UTILISED_CRIT_BUFFER")));
					}// end of if(rs1.getString("UTILISED_BUFFER") != null)
					else {
						balanceSIInfoVO
								.setUtilizedCriIllnessBufAmt(new BigDecimal(
										"0.00"));
					}// end of else

					// /set2

					// /set3

					if (rs1.getString("UTILISED_CRIT_CORP_BUFFER") != null) {
						balanceSIInfoVO
								.setUtilizedCriCorpusBufAmt(new BigDecimal(rs1
										.getString("UTILISED_CRIT_CORP_BUFFER")));
					}// end of if(rs1.getString("UTILISED_BUFFER") != null)
					else {
						balanceSIInfoVO
								.setUtilizedCriCorpusBufAmt(new BigDecimal(
										"0.00"));
					}// end of else

					// /set3
					// /set4

					if (rs1.getString("UTILISED_CRIT_MED_BUFFER") != null) {
						balanceSIInfoVO
								.setUtilizedCriMedicalBufAmt(new BigDecimal(rs1
										.getString("UTILISED_CRIT_MED_BUFFER")));
					}// end of if(rs1.getString("UTILISED_BUFFER") != null)
					else {
						balanceSIInfoVO
								.setUtilizedCriMedicalBufAmt(new BigDecimal(
										"0.00"));
					}// end of else

					// /set4
					// /set5

					// /set5
					// end added for hyundai buffer cr
				}// end of while(rs1.next())
				siInfoVO.setBalSIInfoVO(balanceSIInfoVO);
			}// end of if(rs1 != null)

			if (rs2 != null) {
				while (rs2.next()) {
					siBreakupInfoVO = new SIBreakupInfoVO();

					siBreakupInfoVO.setRowNbr(TTKCommon.checkNull(rs2
							.getString("RNO")));

					if (rs2.getString("MEM_SUM_INSURED") != null) {
						siBreakupInfoVO.setMemSumInsured(new BigDecimal(rs2
								.getString("MEM_SUM_INSURED")));
					}// end of if(rs2.getString("MEM_SUM_INSURED") != null)
					else {
						siBreakupInfoVO
								.setMemSumInsured(new BigDecimal("0.00"));
					}// end of else

					if (rs2.getString("MEM_BONUS_AMOUNT") != null) {
						siBreakupInfoVO.setMemBonus(new BigDecimal(rs2
								.getString("MEM_BONUS_AMOUNT")));
					}// end of if(rs2.getString("MEM_BONUS_AMOUNT") != null)
					else {
						siBreakupInfoVO.setMemBonus(new BigDecimal("0.00"));
					}// end of else

					if (rs2.getString("EFFECTIVE_DATE") != null) {
						siBreakupInfoVO.setSIEffDate(new Date(rs2.getTimestamp(
								"EFFECTIVE_DATE").getTime()));
					}// end of if(rs2.getString("EFFECTIVE_DATE") != null)

					alSIBreakupList.add(siBreakupInfoVO);
				}// end of while(rs2.next())
				siInfoVO.setSIBreakupList(alSIBreakupList);
			}// end of if(rs2 != null)

			if (rs3 != null) {
				while (rs3.next()) {
					stopPreClmVO = new StopPreauthClaimVO();
					stopPreClmVO.setStopPatClmYN(rs3
							.getString("STOP_PAT_CLM_PROCESS_YN"));
					if (rs3.getString("RECIEVED_AFTER") != null) {
						stopPreClmVO.setPatClmRcvdAftr(new Date(rs3
								.getTimestamp("RECIEVED_AFTER").getTime()));
					}// end of if(rs3.getString("PAT_CLM_RECIEVED_AFTER") !=
						// null){
				}// end of while(rs2.next())
				siInfoVO.setStopPreClmVO(stopPreClmVO);
			}// end of if(rs3 != null)
				// Added as per KOC 1216B Change Request
			if (rs4 != null) {
				while (rs4.next()) {
					memberBufferVO = new MemberBufferVO();
					memberBufferVO.setMemberBufferYN(rs4
							.getString("MEMBER_BUFFER_YN"));// member_buffer_yn

					// Mem_Buffer_Alloc
					if (rs4.getString("MEM_BUFFER_ALLOC") != null) {
						memberBufferVO.setBufferAmtMember(new BigDecimal(rs4
								.getString("MEM_BUFFER_ALLOC")));
					}// end of if(rs4.getString("MEM_BUFFER_ALLOC") != null)
					else {
						memberBufferVO
								.setBufferAmtMember(new BigDecimal("0.00"));
					}// end of else

					// added for hyundai buffer cr

					// /set2
					if (rs4.getString("MEM_MED_BUFFER_ALLOC") != null) {
						memberBufferVO.setNorMedicalBufAmt(new BigDecimal(rs4
								.getString("MEM_MED_BUFFER_ALLOC")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						memberBufferVO.setNorMedicalBufAmt(new BigDecimal(
								"0.00"));
					}// end of else

					log.info("getting form value"
							+ memberBufferVO.getNorMedicalBufAmt());
					if (rs4.getString("USED_MED_BUFF_AMOUNT") != null) {
						memberBufferVO
								.setUtilizedNorMedicalBufAmt(new BigDecimal(rs4
										.getString("USED_MED_BUFF_AMOUNT")));
					}// end of if(rs1.getString("UTILISED_BUFFER") != null)
					else {
						memberBufferVO
								.setUtilizedNorMedicalBufAmt(new BigDecimal(
										"0.00"));
					}// end of else

					// /set2

					// /set3
					if (rs4.getString("MEM_CRIT_CORP_BUFF_ALLOC") != null) {
						memberBufferVO.setCriCorpusBufAmt(new BigDecimal(rs4
								.getString("MEM_CRIT_CORP_BUFF_ALLOC")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						memberBufferVO
								.setCriCorpusBufAmt(new BigDecimal("0.00"));
					}// end of else
					if (rs4.getString("USED_CRIT_CORP_BUFF_AMOUNT") != null) {
						memberBufferVO
								.setUtilizedCriCorpusBufAmt(new BigDecimal(
										rs4.getString("USED_CRIT_CORP_BUFF_AMOUNT")));
					}// end of if(rs1.getString("UTILISED_BUFFER") != null)
					else {
						memberBufferVO
								.setUtilizedCriCorpusBufAmt(new BigDecimal(
										"0.00"));
					}// end of else

					// /set3
					// /set4
					if (rs4.getString("MEM_CRIT_MED_BUFF_ALLOC") != null) {
						memberBufferVO.setCriMedicalBufAmt(new BigDecimal(rs4
								.getString("MEM_CRIT_MED_BUFF_ALLOC")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						memberBufferVO.setCriMedicalBufAmt(new BigDecimal(
								"0.00"));
					}// end of else
					if (rs4.getString("USED_CRIT_MED_BUFF_AMOUNT") != null) {
						memberBufferVO
								.setUtilizedCriMedicalBufAmt(new BigDecimal(rs4
										.getString("USED_CRIT_MED_BUFF_AMOUNT")));
					}// end of if(rs1.getString("UTILISED_BUFFER") != null)
					else {
						memberBufferVO
								.setUtilizedCriMedicalBufAmt(new BigDecimal(
										"0.00"));
					}// end of else

					// /set4
					// /set5
					if (rs4.getString("MEM_CRIT_BUFF_ALLOC") != null) {
						memberBufferVO.setCriIllnessBufAmt(new BigDecimal(rs4
								.getString("MEM_CRIT_BUFF_ALLOC")));
					}// end of if(rs1.getString("BUFFER_AMOUNT") != null)
					else {
						memberBufferVO.setCriIllnessBufAmt(new BigDecimal(
								"0.00"));
					}// end of else
					if (rs4.getString("USED_CRIT_BUFF_AMOUNT") != null) {
						memberBufferVO
								.setUtilizedCriIllnessBufAmt(new BigDecimal(rs4
										.getString("USED_CRIT_BUFF_AMOUNT")));
					}// end of if(rs1.getString("UTILISED_BUFFER") != null)
					else {
						memberBufferVO
								.setUtilizedCriIllnessBufAmt(new BigDecimal(
										"0.00"));
					}// end of else

					// /set5
					// end added for hyundai buffer cr
					// used_buff_amount
					if (rs4.getString("USED_BUFF_AMOUNT") != null) {
						memberBufferVO
								.setUtilizedBufferAmtMember(new BigDecimal(rs4
										.getString("USED_BUFF_AMOUNT")));
					}// end of if(rs4.getString("USED_BUFF_AMOUNT") != null)
					else {
						memberBufferVO
								.setUtilizedBufferAmtMember(new BigDecimal(
										"0.00"));
					}// end of else
				}// end of while(rs4.next())
				siInfoVO.setMemberBufferVO(memberBufferVO);
			}// end of if(rs4 != null)

			// added by rekha koc1289_1272
			if (rs5 == null) {
                restorationPreauthClaimVO = new RestorationPreauthClaimVO();
			    restorationPreauthClaimVO.setRestorationYN("N");
				restorationPreauthClaimVO.setRestSumInsured(new BigDecimal(
						"0.00"));
				log.info("RESTORATION_YN---------------------"
						+ restorationPreauthClaimVO.getRestorationYN());
				log.info("restoration---------------------"
						+ restorationPreauthClaimVO.getRestSumInsured());
			    siInfoVO.setRestorationPreauthClaimVO(restorationPreauthClaimVO);
			} else if (rs5 != null) {
				if (rs5.next()) {
					restorationPreauthClaimVO = new RestorationPreauthClaimVO();
					if (rs5.getString("RESTORATION_YN") != null) {
						restorationPreauthClaimVO.setRestorationYN(rs5
								.getString("RESTORATION_YN").trim());

					} else {
					restorationPreauthClaimVO.setRestorationYN("N");

				}
					if (rs5.getString("MEM_SUM_INSURED") != null) {

						restorationPreauthClaimVO
								.setRestSumInsured(new BigDecimal(rs5
										.getString("MEM_SUM_INSURED")));
					}// end of if(rs5.getString("PAT_CLM_RECIEVED_AFTER") !=
						// null){
				}// end of while(rs5.next())
				else {
                            restorationPreauthClaimVO = new RestorationPreauthClaimVO();
						    restorationPreauthClaimVO.setRestorationYN("N");
					restorationPreauthClaimVO.setRestSumInsured(new BigDecimal(
							"0.00"));

                }

			siInfoVO.setRestorationPreauthClaimVO(restorationPreauthClaimVO);

			}// end of if(rs5 != null)
			// End added by rekha koc1289_1272
				// Added as per KOC 1216B Change Request

			// Added as per KOC 1216B Change Request
			return siInfoVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the fourth result set
				{
				try {
					if (rs5 != null)
						rs5.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the fourth Resultset in PreAuthDAOImpl getBalanceSIDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}// end of catch (SQLException sqlExp)
				finally {

					try {
						if (rs4 != null)
							rs4.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the fourth Resultset in PreAuthDAOImpl getBalanceSIDetail()",
								sqlExp);
					throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
					finally // Even if fourth result set is not closed, control
							// reaches here. Try closing the third resultset
							// now.
				{
						try {
							if (rs3 != null)
								rs3.close();
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the third Resultset in PreAuthDAOImpl getBalanceSIDetail()",
									sqlExp);
						throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
						finally // Even if third result set is not closed,
								// control reaches here. Try closing the second
								// resultset now.
					{
							try {
								if (rs2 != null)
									rs2.close();
							}// end of try
							catch (SQLException sqlExp) {
								log.error(
										"Error while closing the Second Resultset in PreAuthDAOImpl getBalanceSIDetail()",
										sqlExp);
							throw new TTKException(sqlExp, "preauth");
							}// end of catch (SQLException sqlExp)
							finally // Even if second result set is not closed,
									// control reaches here. Try closing the
									// first resultset now.
						{
								try {
									if (rs1 != null)
										rs1.close();
								}// end of try
								catch (SQLException sqlExp) {
									log.error(
											"Error while closing the Second Resultset in PreAuthDAOImpl getBalanceSIDetail()",
											sqlExp);
								throw new TTKException(sqlExp, "preauth");
								}// end of catch (SQLException sqlExp)
								finally // Even if first result set is not
										// closed, control reaches here. Try
										// closing the statement now.
							{
									try {
										if (cStmtObject != null)
											cStmtObject.close();
									}// end of try
									catch (SQLException sqlExp) {
										log.error(
												"Error while closing the Statement in PreAuthDAOImpl getBalanceSIDetail()",
												sqlExp);
										throw new TTKException(sqlExp,
												"preauth");
									}// end of catch (SQLException sqlExp)
									finally // Even if statement is not closed,
											// control reaches here. Try closing
											// the connection now.
								{
										try {
											if (conn != null)
												conn.close();
										}// end of try
										catch (SQLException sqlExp) {
											log.error(
													"Error while closing the Connection in PreAuthDAOImpl getBalanceSIDetail()",
													sqlExp);
											throw new TTKException(sqlExp,
													"preauth");
										}// end of catch (SQLException sqlExp)
									}// end of finally Connection Close
								}// end of finally Statement Close
						} // end finally rs1
					} // end finally rs2
					}// end of finally rs3
				}// end of finally rs4
			}// end of try

			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs5 = null;
				rs4 = null;
				rs3 = null;
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally

	}// end of getBalanceSIDetail(long lngMemSeqID,long lngPolicyGrpSeqID,long
		// lngBalanceSeqID)
		// Changes as Per Koc 1142 && KOC 1140Change Request

   /**
	 * This method returns the SIInfoVO, which contains CopayAdvicedDetails.
	 * 
	 * @param lngPatGenDtlSeqID
	 *            long value which contains member id to get the
	 *            CopayAdvicedDetails
	 * @param lngMemSeqID
	 *            long value which contains member id to get the
	 *            CopayAdvicedDetails
	 * @param lngPolicyGrpSeqID
	 *            long value which contains Policy Group Seq ID
	 * @param lngBalanceSeqID
	 *            long value which contains Balance Seq ID
	 * @return BalanceCopayDeductionVO object which contains CopayAdvicedDetails
	 * @exception throws TTKException
	 */
	public BalanceCopayDeductionVO getcopayAdviced(String strIdentifier,
			long lngPatGenDtlSeqID, long lngMemSeqID, long lngPolicyGrpSeqID,
			long lngBalanceSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;

		SIInfoVO siInfoVO = new SIInfoVO();
		BalanceCopayDeductionVO balCopayDeducVO = null;
		try {
			conn = ResourceManager.getConnection();

			if (strIdentifier.equalsIgnoreCase("Pre-Authorization")) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strPreAuthCopayAdvice);
				cStmtObject.setLong(1, lngMemSeqID);
				cStmtObject.setLong(2, lngPolicyGrpSeqID);
				cStmtObject.setLong(3, lngBalanceSeqID);
				cStmtObject.setLong(4, lngPatGenDtlSeqID);

				cStmtObject.registerOutParameter(5, Types.DECIMAL);// ROWS_PROCESSED
				cStmtObject.registerOutParameter(6, Types.DECIMAL);// ROWS_PROCESSED

			cStmtObject.execute();

				balCopayDeducVO = new BalanceCopayDeductionVO();

				if (cStmtObject.getBigDecimal(5) != null) {
					balCopayDeducVO.setBdApprovedAmt(cStmtObject
							.getBigDecimal(5));
				} else {
			 balCopayDeducVO.setBdApprovedAmt(new BigDecimal("0.00"));
			 }

				if (cStmtObject.getBigDecimal(6) != null) {
					balCopayDeducVO.setBdMaxCopayAmt(cStmtObject
							.getBigDecimal(6));
				} else {
				 balCopayDeducVO.setBdMaxCopayAmt(new BigDecimal("0.00"));
			 }
			} else if (strIdentifier.equalsIgnoreCase("Claims")) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strClaimsCopayAdvice);
				cStmtObject.setLong(1, lngMemSeqID);
				cStmtObject.setLong(2, lngPolicyGrpSeqID);
				cStmtObject.setLong(3, lngBalanceSeqID);
				cStmtObject.setLong(4, lngPatGenDtlSeqID);
				cStmtObject.registerOutParameter(5, Types.DECIMAL);// ROWS_PROCESSED
				cStmtObject.registerOutParameter(6, Types.DECIMAL);// ROWS_PROCESSED
				cStmtObject.execute();
				balCopayDeducVO = new BalanceCopayDeductionVO();
				if (cStmtObject.getBigDecimal(5) != null) {
					balCopayDeducVO.setBdApprovedAmt(cStmtObject
							.getBigDecimal(5));
				} else {
				 balCopayDeducVO.setBdApprovedAmt(new BigDecimal("0.00"));
				 }

				if (cStmtObject.getBigDecimal(6) != null) {
					balCopayDeducVO.setBdMaxCopayAmt(cStmtObject
							.getBigDecimal(6));
				} else {
					 balCopayDeducVO.setBdMaxCopayAmt(new BigDecimal("0.00"));
				 }

			}
			// siInfoVO.setBalCopayDeducVO(balCopayDeducVO);
			return balCopayDeducVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)

		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl copayAdviced()",
							sqlExp);
        			throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl copayAdviced()",
								sqlExp);
        				throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
	}// end of copayAdviced(long lngMemSeqID,long lngPolicyGrpSeqID,long
		// lngBalanceSeqID)
		// Changes as Per Koc 1142 && 1140 Change Request and 1165

	/**
	 * bajaj This method send the intimation to insurer
	 * 
	 * @param authorizationVO
	 *            AuthorizationVO contains Authorization information
	 * @param strIdentifier
	 *            Pre-Authorization/Claims
	 * @return long int the value which returns greater than zero for
	 *         succcesssful execution of the task
     * @exception throws TTKException
     */
	public int sendInsIntimate(AuthorizationVO authorizationVO,
			String strIdentifier) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();

			if (strIdentifier.equalsIgnoreCase("Pre-Authorization")) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strPatInsIntimate);
				if (authorizationVO.getPreAuthSeqID() != null) {
					cStmtObject.setLong(1, authorizationVO.getPreAuthSeqID());
				}// end of if(authorizationVO.getPreAuthSeqID() != null)
				else {
					cStmtObject.setString(1, null);
				}// end of else

				if (authorizationVO.getStatusTypeID() != null) {
					cStmtObject.setString(2, authorizationVO.getStatusTypeID());
				}// end of if(authorizationVO.getEnrollDtlSeqID() != null)
				else {
					cStmtObject.setString(2, null);
				}// end of else

				if (authorizationVO.getTotalSumInsured() != null) {
					cStmtObject.setBigDecimal(3,
							authorizationVO.getTotalSumInsured());
				}// end of if(authorizationVO.getTotalSumInsured() != null)
				else {
					cStmtObject.setString(3, null);
				}// en of else
				cStmtObject.setString(4, "M");
				cStmtObject.setLong(5, authorizationVO.getUpdatedBy());// 1274A
				cStmtObject.execute();
			}// end of if(strIdentifier.equalsIgnoreCase("Pre-Authorization"))

			else if (strIdentifier.equalsIgnoreCase("Claims")) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strClaimInsIntimate);
				if (authorizationVO.getClaimSeqID() != null) {
					cStmtObject.setLong(1, authorizationVO.getClaimSeqID());
				}// end of if(authorizationVO.getClaimSeqID() != null)
				else {
					cStmtObject.setString(1, null);
				}// end of else
				if (authorizationVO.getStatusTypeID() != null) {
					cStmtObject.setString(2, authorizationVO.getStatusTypeID());
				}// end of if(authorizationVO.getAssignUserSeqID() != null)
				else {
					cStmtObject.setString(2, null);
				}// end of else

				if (authorizationVO.getTotalSumInsured() != null) {
					cStmtObject.setBigDecimal(3,
							authorizationVO.getTotalSumInsured());
				}// end of if(authorizationVO.getTotalSumInsured() != null)
				else {
					cStmtObject.setString(3, null);
				}// end of else
				cStmtObject.setString(4, "M");
				cStmtObject.setLong(5, authorizationVO.getUpdatedBy());// 1274A
				cStmtObject.execute();
			}// end of else if(strIdentifier.equalsIgnoreCase("Claims"))

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl SendIntimation()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl SendIntimation()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of SendInsIntimate(AuthorizationVO authorizationVO)

	public long saveUpoadedFilename(ArrayList alFileAUploadList)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		// long batchSeqId=0;
		// long batchId=0;
		int rowsprocessed = 0;
		try {
		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strPatClmFileUpload);
			if (alFileAUploadList.get(0) != null) {
				cStmtObject.setLong(1, (Long) alFileAUploadList.get(0));
				cStmtObject.setString(2, "");

			} else if (alFileAUploadList.get(1) != null) {
				cStmtObject.setString(1, "");
				cStmtObject.setLong(2, (Long) alFileAUploadList.get(1));

			}
			cStmtObject.setString(3, (String) alFileAUploadList.get(3));// filename
			cStmtObject.setString(4, (String) alFileAUploadList.get(4));// remarks
			cStmtObject.setLong(5, (Long) alFileAUploadList.get(2));// addedby
			cStmtObject.registerOutParameter(6, Types.INTEGER);
			cStmtObject.execute();
			rowsprocessed = cStmtObject.getInt(6);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl saveUploadFileName()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl saveUploadFileName()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{

				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally

		return rowsprocessed;

	}

	/**
	 * 1274A This method saves the Pre-Authorization information
	 * 
	 * @param preAuthDetailVO
	 *            PreAuthDetailVO contains Pre-Authorization information
	 * @param strSelectionType
	 *            String object which contains Selection Type - 'PAT' or 'ICO'
	 * @return int the values,of MEMBER_SEQ_ID , PAT_GEN_DETAIL_SEQ_ID and
	 *         PAT_ENROLL_DETAIL_SEQ_ID
	 * @exception throws TTKException
	 */
	public int accountUnfreeze(ArrayList alUnfreezeList) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		long rowsprocessed = 0;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strUnFreezePatClm);
			if (alUnfreezeList.get(0) != null) {
				cStmtObject.setLong(1, (Long) alUnfreezeList.get(0));
				cStmtObject.setString(2, null);
			} else if (alUnfreezeList.get(1) != null) {
				cStmtObject.setString(1, null);
				cStmtObject.setLong(2, (Long) alUnfreezeList.get(1));
			}
			cStmtObject.setLong(3, (Long) alUnfreezeList.get(2));
			cStmtObject.registerOutParameter(4, Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {

			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl accountUnfreeze()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl accountUnfreeze()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of accountUnfreeze(AuthorizationVO authorizationVO)

	public int accountinsOverrideConf(ArrayList alUnfreezeList)
			throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		long rowsprocessed = 0;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strOverrideConf);
			if (alUnfreezeList.get(0) != null) {
				cStmtObject.setLong(1, (Long) alUnfreezeList.get(0));
				cStmtObject.setString(2, null);
			} else if (alUnfreezeList.get(1) != null) {
				cStmtObject.setString(1, null);
				cStmtObject.setLong(2, (Long) alUnfreezeList.get(1));
			}
			cStmtObject.setLong(3, (Long) alUnfreezeList.get(2));
			cStmtObject.registerOutParameter(4, Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {

			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl accountUnfreeze()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl accountUnfreeze()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of accountUnfreeze(AuthorizationVO authorizationVO)

	public InsOverrideConfVO getConfigDetails(long lngProdPolicySeqId,
			String Mode) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		InsOverrideConfVO insOverrideConfVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strGetInsIntimation);
			cStmtObject.setLong(1, lngProdPolicySeqId);
			cStmtObject.setString(2, Mode);
			cStmtObject.registerOutParameter(3, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(3);
			if (rs != null) {
				while (rs.next()) {
					insOverrideConfVO = new InsOverrideConfVO();

					if (rs.getString("INS_INTIMATION_REQ_YN") != null) {
						insOverrideConfVO.setInsDecYN(rs
								.getString("INS_INTIMATION_REQ_YN"));
					}
					if (rs.getString("INS_INT_RSON_TYPE_ID") != null) {
						insOverrideConfVO.setInsReqType(rs
								.getString("INS_INT_RSON_TYPE_ID"));
					}
					// Changes as per KOC 1142 Change Request
					if (rs.getString("INS_INT_OVERRIDEN_DATE") != null) {
						insOverrideConfVO.setOverriddenDate(rs
								.getString("INS_INT_OVERRIDEN_DATE"));
					}
					// Changes as per KOC 1142 Change Request
					if (rs.getString("INS_INT_OVERRIDE_REMARKS") != null) {
						insOverrideConfVO.setRemarks(rs
								.getString("INS_INT_OVERRIDE_REMARKS"));
					}

					/*
					 * if(rs.getString("INS_INT_OVERRIDEN_BY") != null){
					 * insOverrideConfVO
					 * .set(rs.getString("INS_INT_OVERRIDEN_BY")); }
					 */

					// Changes as per KOC 1142 Change Request
				}// end of while(rs.next())
			}// end of (rs != null)
			return insOverrideConfVO;
		}// end of try
		catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "policy");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
				throw new TTKException(exp, "policy");
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
							"Error while closing the Resultset in PreAuthDAOImpl getCustMsgInfo()",
							sqlExp);
						throw new TTKException(sqlExp, "policy");
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
								"Error while closing the Statement in PreAuthDAOImpl getCustMsgInfo()",
								sqlExp);
							throw new TTKException(sqlExp, "policy");
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
									"Error while closing the Connection in PreAuthDAOImpl getCustMsgInfo()",
									sqlExp);
								throw new TTKException(sqlExp, "policy");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
					throw new TTKException(exp, "policy");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
				{
					rs = null;
					cStmtObject = null;
					conn = null;
			}// end of finally
		}// end of finally
	}// end of getConfigCopayAmt(long lngProdPolicySeqId)

	public int saveOverrideConfigDetails(InsOverrideConfVO insOverrideConfVO)
			throws TTKException {
		int iResult = 0;
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
    		conn = ResourceManager.getConnection();

			// cStmtObject =
			// (java.sql.CallableStatement)conn.prepareCall(strSaveConfigCopayAmt);
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strOverrideConf);

            // log.debug("seq_id"+insOverrideConfVO.getPatClmSeqID());
            // log.info("getMode"+insOverrideConfVO.getMode());
            // log.info("getInsDecYN"+insOverrideConfVO.getInsDecYN());
			// log.info("getOverriddenDate"+
			// (insOverrideConfVO.getOverriddenDate()));
           //  log.info("getRemarks"+insOverrideConfVO.getRemarks());

			if (insOverrideConfVO.getPatClmSeqID() != null) {
				cStmtObject.setLong(1, insOverrideConfVO.getPatClmSeqID());
			}// end of if(custConfigMsgVO.getProdPolicySeqID() != null)
			else {
				cStmtObject.setLong(1, 0);
			}// end of else.
			if (insOverrideConfVO.getMode() != null) {

				cStmtObject.setString(2, insOverrideConfVO.getMode());

			}// end of if(bufferDetailVO.getBufferAmt() != null)
			/*
			 * else{ cStmtObject.setString(2,"N"); }//end of else
            */
			if (insOverrideConfVO.getInsDecYN() != null) {
				cStmtObject.setString(3, insOverrideConfVO.getInsDecYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(3, null);
			}// end of else

			if (insOverrideConfVO.getInsReqType() != null) {
				cStmtObject.setString(4, insOverrideConfVO.getInsReqType());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(4, null);
			}// end of else

			/*
			 * if(insOverrideConfVO.getOverriddenDate() != null){
			 * cStmtObject.setString(5,insOverrideConfVO.getOverriddenDate());
			 * }//end of if(bufferDetailVO.getBufferAmt() != null) else{
			 * cStmtObject.setString(5,null); }//end of else
			 */

			cStmtObject.setString(5, insOverrideConfVO.getRemarks());// 1284
																		// change
																		// request
			cStmtObject.setLong(6, insOverrideConfVO.getUpdatedBy());

            cStmtObject.registerOutParameter(7, Types.INTEGER);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(7);
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "policy");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "policy");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl getProviders()",
							sqlExp);
        			throw new TTKException(sqlExp, "policy");
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
								"Error while closing the Connection in PreAuthDAOImpl getProviders()",
								sqlExp);
        				throw new TTKException(sqlExp, "policy");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "policy");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
        return iResult;
	}// end of saveConfigCopayAmt(insOverrideConfVO insOverrideConfVO)

	public long overridPreAuthDetails(String preAuthSeqID,String overrideRemarks, Long userSeqID) throws TTKException {

		long rowUpd = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strOverridreAuthDetails);
			cStmtObject.setLong(1,Long.parseLong(preAuthSeqID) );
			cStmtObject.setLong(2, userSeqID);
			cStmtObject.setString(3, overrideRemarks);
			cStmtObject.registerOutParameter(4, Types.INTEGER);
			cStmtObject.execute();
			rowUpd = cStmtObject.getInt(4);
			conn.commit();
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "claim");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "claim");
		}// end of catch (Exception exp)
		finally {
        	/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl overridPreAuthDetails()",
							sqlExp);
        			throw new TTKException(sqlExp, "floataccount");
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
								"Error while closing the Connection in PreAuthDAOImpl overridPreAuthDetails()",
								sqlExp);
        				throw new TTKException(sqlExp, "floataccount");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "claim");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
		return rowUpd;
	}

	public HashMap<String, String> getProviders() throws TTKException {

	    	Connection conn = null;
		PreparedStatement cStmtObject = null;
		ResultSet rs=null;
		HashMap<String, String> providerList = new HashMap<>();
		try {
	    		conn = ResourceManager.getConnection();

	             cStmtObject = conn.prepareCall(strGetProviders);

			 rs = cStmtObject.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					providerList.put(
							rs.getString("PROVIDER_SEQ_ID") + "@"
									+ rs.getString("PROVIDER_ID"),
							rs.getString("PROVIDER_NAME"));
	            	}
	            }
		}// end of try
		catch (SQLException sqlExp) {
	            throw new TTKException(sqlExp, "policy");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
	            throw new TTKException(exp, "policy");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
	        	try // First try closing the Statement
	        	{
				try {
					if (rs != null)
						rs.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getDhpoPreauthDetails()",
							sqlExp);
						throw new TTKException(sqlExp, "policy");
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
							"Error while closing the Statement in PreAuthDAOImpl getProviders()",
							sqlExp);
	        			throw new TTKException(sqlExp, "policy");
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
								"Error while closing the Connection in PreAuthDAOImpl getProviders()",
								sqlExp);
	        				throw new TTKException(sqlExp, "policy");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
				}
			}// end of try
			catch (TTKException exp) {
	        		throw new TTKException(exp, "policy");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
	        	{
			        rs=null;
	        		cStmtObject = null;
	        		conn = null;
			}// end of finally
		}// end of finally
	        return providerList;

	}// getProviders

    /**
	 * This method get the DHPO upload preauth details
	 * 
     * @param PreAuthSeqID
	 *            returns DhpoWebServiceVO
     * @exception throws TTKException
     */
	public DhpoWebServiceVO getDhpoPreauthDetails(String preauthSeqID)
			throws TTKException {

		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		DhpoWebServiceVO webServiceVO = new DhpoWebServiceVO();
		;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetDhpoPreauthUpload);
			cStmtObject.setBigDecimal(1, new BigDecimal(preauthSeqID));
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			rs = (ResultSet) cStmtObject.getObject(2);
			if (rs != null) {
				if (rs.next()) {

					if (rs.getSQLXML("UPLOAD_FILE") != null) {
						webServiceVO.setFileContent(rs.getSQLXML("UPLOAD_FILE").getString().getBytes());
					}// End of if(rs.getOPAQUE("UPLOAD_FILE")
					 webServiceVO.setFileID(rs.getString("FILE_ID"));
					 webServiceVO.setFileName(rs.getString("FILE_NAME"));
					 webServiceVO.setDhpoLicenceNO(rs.getString("SENDER_ID"));
				}//end of while(rs.next())
			}//end of (rs != null)
			return webServiceVO;
		}// end of try
		catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "policy");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
				throw new TTKException(exp, "policy");
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
							"Error while closing the Resultset in PreAuthDAOImpl getDhpoPreauthDetails()",
							sqlExp);
						throw new TTKException(sqlExp, "policy");
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
								"Error while closing the Statement in PreAuthDAOImpl getDhpoPreauthDetails()",
								sqlExp);
							throw new TTKException(sqlExp, "policy");
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
									"Error while closing the Connection in PreAuthDAOImpl getDhpoPreauthDetails()",
									sqlExp);
								throw new TTKException(sqlExp, "policy");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
					throw new TTKException(exp, "policy");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
				{
					rs = null;
					cStmtObject = null;
					conn = null;
			}// end of finally
		}// end of finally

	}// getDhpoPreauthDetails(String preauthSeqID)

 	/**
	 * This method get the DHPO upload preauth log details
	 * 
	 * @param DhpoWebServiceVO
	 *            DHPO upload preauth log details returns row proccessed
     * @exception throws TTKException
     */
	public long saveDhpoPreauthLogDetails(DhpoWebServiceVO dhpoWebServiceVO)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveDhpoPreauthlogDetails);

			cStmtObject.setBigDecimal(1, new BigDecimal(dhpoWebServiceVO.getPreAuthSeqID()));

			cStmtObject.setString(2, dhpoWebServiceVO.getFileName());

			if (dhpoWebServiceVO.getTransactionResult() != null) {
				cStmtObject.setBigDecimal(3, new BigDecimal(dhpoWebServiceVO.getTransactionResult()));
			} else {
				cStmtObject.setBigDecimal(3, null);
			}

			cStmtObject.setString(4, dhpoWebServiceVO.getErrorMessage());

			if (dhpoWebServiceVO.getErrorReport() != null) {

				cStmtObject.setString(5,new String(dhpoWebServiceVO.getErrorReport()));

			} else {
				cStmtObject.setString(5, null);
			}
			cStmtObject.setString(6, dhpoWebServiceVO.getPreAuthUploadStatus());
			int efrw = cStmtObject.executeUpdate();
			conn.commit();
			return efrw;
		}// end of try
		catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "policy");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
				throw new TTKException(exp, "policy");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
				try // First try closing the result set
				{
				
				try {
						if (cStmtObject != null)
							cStmtObject.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in PreAuthDAOImpl saveDhpoPreauthLogDetails()",
								sqlExp);
							throw new TTKException(sqlExp, "policy");
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
									"Error while closing the Connection in PreAuthDAOImpl saveDhpoPreauthLogDetails()",
									sqlExp);
								throw new TTKException(sqlExp, "policy");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
					throw new TTKException(exp, "policy");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
				{
					cStmtObject = null;
					conn = null;
			}// end of finally
		}// end of finally

	}// saveDhpoPreauthLogDetails

 	/**
	 * This method get the DHPO upload preauth details
	 * 
     * @param PreAuthSeqID
	 *            returns DhpoWebServiceVO
     * @exception throws TTKException
     */
	public DhpoWebServiceVO getDhpoPreauthLogDetails(String preauthSeqID)
			throws TTKException {

		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		DhpoWebServiceVO webServiceVO = new DhpoWebServiceVO();
		;

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetDhpoPreauthLogDetails);
			cStmtObject.setBigDecimal(1, new BigDecimal(preauthSeqID));
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			rs = (ResultSet) cStmtObject.getObject(2);

			if (rs != null) {
				if (rs.next()) {
					//webServiceVO.setXmlFileReader(new StringReader(rs.getString("ERROR_REPORT")));
					webServiceVO.setErrorText(rs.getString("ERROR_REPORT"));					
					 webServiceVO.setFileName(rs.getString("FILE_NAME"));
					webServiceVO.setErrorMessage(rs.getString("ERROR_MESSAGE"));
				}// end of while(rs.next())
			}// end of (rs != null)
			return webServiceVO;
		}// end of try
		catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "policy");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
				throw new TTKException(exp, "policy");
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
							"Error while closing the Resultset in PreAuthDAOImpl getDhpoPreauthLogDetails()",
							sqlExp);
						throw new TTKException(sqlExp, "policy");
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
								"Error while closing the Statement in PreAuthDAOImpl getDhpoPreauthLogDetails()",
								sqlExp);
							throw new TTKException(sqlExp, "policy");
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
									"Error while closing the Connection in PreAuthDAOImpl getDhpoPreauthLogDetails()",
									sqlExp);
								throw new TTKException(sqlExp, "policy");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
					throw new TTKException(exp, "policy");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
				{
					rs = null;
					cStmtObject = null;
					conn = null;
			}// end of finally
		}// end of finally

	}// getDhpoPreauthLogDetails(String preauthSeqID)

	public HashMap<String, String> getProviders(String type)
			throws TTKException {

         Connection conn = null;
		PreparedStatement cStmtObject = null;
		ResultSet rs = null;
		HashMap<String, String> providerList = new HashMap<>();
     	String strGetDiagCodes = "SELECT ICD_CODE,SHORT_DESC FROM APP.TPA_ICD10_MASTER_DETAILS";

		try {
             conn = ResourceManager.getConnection();

			if ("activityCode".equals(type) || "activityDesc".equals(type))
            	 strGetDiagCodes	=	"SELECT MD.ACTIVITY_CODE ,MD.ACTIVITY_DESCRIPTION   FROM APP.TPA_ACTIVITY_MASTER_DETAILS MD WHERE MD.ACTIVITY_TYPE_SEQ_ID <> 5 ";

			if ("drugCode".equals(type) || "drugDesc".equals(type))
				strGetDiagCodes = "SELECT PM.ACTIVITY_CODE ,PM.ACTIVITY_DESCRIPTION,PM.GRANULAR_UNIT FROM APP.TPA_PHARMACY_MASTER_DETAILS PM";

              cStmtObject = conn.prepareCall(strGetDiagCodes);
			 rs = cStmtObject.executeQuery();
			if ("Code".equals(type)) {
				if (rs != null) {
					while (rs.next()) {
						providerList.put(rs.getString("ICD_CODE"),
								rs.getString("SHORT_DESC"));
	                 }
	             }
			} else if ("Desc".equals(type)) {
				if (rs != null) {
					while (rs.next()) {
						providerList.put(rs.getString("SHORT_DESC"),
								rs.getString("ICD_CODE"));
	                 }
	             }
			} else if ("activityCode".equals(type)) {
				if (rs != null) {
					while (rs.next()) {
						providerList.put(rs.getString("ACTIVITY_CODE"),
								rs.getString("ACTIVITY_DESCRIPTION"));
	                 }
	             }
			} else if ("activityDesc".equals(type)) {
				if (rs != null) {
					while (rs.next()) {
						providerList.put(rs.getString("ACTIVITY_DESCRIPTION"),
								rs.getString("ACTIVITY_CODE"));
	                 }
	             }
			} else if ("drugCode".equals(type)) {
				if (rs != null) {
					while (rs.next()) {
						providerList.put(rs.getString("ACTIVITY_CODE"),
								rs.getString("ACTIVITY_DESCRIPTION"));/* + "@@"
										+ rs.getString("GRANULAR_UNIT"));*/
        	}
	                 }
			} else if ("drugDesc".equals(type)) {
				if (rs != null) {
					while (rs.next()) {
						providerList.put(
								rs.getString("ACTIVITY_DESCRIPTION"),
								rs.getString("ACTIVITY_CODE"));/* + "@@"
										+ rs.getString("GRANULAR_UNIT"));*/
	             }
	                 }
	             }

		}// end of try
		catch (SQLException sqlExp) {
             throw new TTKException(sqlExp, "policy");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
             throw new TTKException(exp, "policy");
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
						"Error while closing the Resultset in PreAuthDAOImpl getProviders()",
						sqlExp);
					throw new TTKException(sqlExp, "policy");
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
							"Error while closing the Statement in PreAuthDAOImpl getProviders()",
							sqlExp);
                     throw new TTKException(sqlExp, "policy");
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
								"Error while closing the Connection in PreAuthDAOImpl getProviders()",
								sqlExp);
                         throw new TTKException(sqlExp, "policy");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
                 throw new TTKException(exp, "policy");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
             {
				rs = null;
                 cStmtObject = null;
                 conn = null;
			}// end of finally
		}// end of finally
         return providerList;

	}// getProviders

	public HashMap<String, String> getPreauthDiagDetails() throws TTKException {
		HashMap<String, String> hashMap = new HashMap<>();
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		conn = ResourceManager.getConnection();
		// strGetdiagDetails="select d.denial_code, d.denial_description
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(strGetdiagDetails);
			while (resultSet.next()) {

				hashMap.put(resultSet.getString("denial_code"),
						resultSet.getString("denial_description"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
			try {
				if (resultSet != null)
					resultSet.close();
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Resultset in PreAuthDAOImpl getPreauthDiagDetails()",
						sqlExp);
					throw new TTKException(sqlExp, "policy");
			}// end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches
					// here. Try closing the statement now.
				{
				try {
					if (statement != null)
						statement.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl getPreauthDiagDetails()",
							sqlExp);
						throw new TTKException(sqlExp, "policy");
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
								"Error while closing the Connection in PreAuthDAOImpl getPreauthDiagDetails()",
								sqlExp);
							throw new TTKException(sqlExp, "policy");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
		}// end of try
		catch (TTKException exp) {
				throw new TTKException(exp, "policy");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
			{
			resultSet = null;
				statement = null;
				conn = null;
		}// end of finally
	}// end of finally
		
		return hashMap;

	}

	public ArrayList<MOUDocumentVO> getProviderDocs(String preauthSeqID)
			throws TTKException {

		Connection conn = null;
		PreparedStatement cStmtObject = null;
		ResultSet rs = null;
		MOUDocumentVO mouDocumentVO = null;
		ArrayList<MOUDocumentVO> providerDocList = new ArrayList<>();
		try {
				conn = ResourceManager.getConnection();

		         cStmtObject = conn.prepareCall(strGetProviderDocs);

		         cStmtObject.setLong(1, Long.parseLong(preauthSeqID));
			rs = cStmtObject.executeQuery();

			if (rs != null) {
				while (rs.next()) {

							mouDocumentVO = new MOUDocumentVO();
					mouDocumentVO.setDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					mouDocumentVO.setMouDocPath(TTKCommon.checkNull(rs.getString("DOCS_PATH")));
					mouDocumentVO.setMouDocSeqID(((long) rs.getLong("mou_doc_seq_id")));
					mouDocumentVO.setFileName(TTKCommon.checkNull(rs.getString("FILE_NAME")));
					if (rs.getString("ADDED_DATE") != null) {
			                	mouDocumentVO.setDateTime(rs.getString("ADDED_DATE"));
					}// end of if(rs.getString("ADDED_DATE") != null)
					mouDocumentVO.setUserId(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
			               // System.out.println("mouDocumentVO::"+mouDocumentVO.toString());
			                providerDocList.add(mouDocumentVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
				// return (ArrayList<Object>)alMouList;

		}// end of try
		catch (SQLException sqlExp) {
		        throw new TTKException(sqlExp, "policy");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
		        throw new TTKException(exp, "policy");
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
						"Error while closing the Resultset in PreAuthDAOImpl getProviderDocs()",
						sqlExp);
					throw new TTKException(sqlExp, "policy");
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
							"Error while closing the Statement in PreAuthDAOImpl getProviderDocs()",
							sqlExp);
						throw new TTKException(sqlExp, "policy");
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
								"Error while closing the Connection in PreAuthDAOImpl getProviderDocs()",
								sqlExp);
							throw new TTKException(sqlExp, "policy");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
		}// end of try
		catch (TTKException exp) {
				throw new TTKException(exp, "policy");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
			{
			rs = null;
			cStmtObject = null;
			conn = null;
		}// end of finally
	}// end of finally
 return providerDocList;
	 }

	public Document getXmlTOSendCEED(Long clmAuthSeqId) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		Document doc = null;
		try {
			log.info("called getXmlTOSendCEED");
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strGetXmlDataToCEED);
					
			cStmtObject.setLong(1, clmAuthSeqId);
			cStmtObject.registerOutParameter(2, Types.SQLXML);// CLOB
			cStmtObject.execute();

			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(cStmtObject.getSQLXML(2).getCharacterStream());
			return doc;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)

		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl getXmlTOSendCEED()",
							sqlExp);
        			throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl getXmlTOSendCEED()",
								sqlExp);
        				throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
	}// end of getXmlTOSendCEED(ArrayList alFileAUploadList)
		// Changes as Per Koc 1142 && 1140 Change Request and 1165

	public Document getPATXmlTOSendCEED(Long preAuthSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		Document doc = null;
		try {
			
			conn = ResourceManager.getConnection();
			cStmtObject = (OracleCallableStatement) ((OracleConnection) ((WrappedConnectionJDK6) conn)
					.getUnderlyingConnection())
					.prepareCall(strPATGetXmlDataToCEED);
			cStmtObject.setLong(1, preAuthSeqID);
			cStmtObject.registerOutParameter(2, Types.CLOB);// CLOB
			cStmtObject.execute();

			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(cStmtObject.getCharacterStream(2));
            return doc;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)

		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl getPATXmlTOSendCEED()",
							sqlExp);
        			throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl getPATXmlTOSendCEED()",
								sqlExp);
        				throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
	}// end of getPATXmlTOSendCEED(Long preAuthSeqID)
		// Changes as Per Koc 1142 && 1140 Change Request and 1165

	public int getCountOralPreAuth() throws TTKException  {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement preparedstatement=null;
		ResultSet resultSet=null;
	   int count=0;
	  try{	 
	     con=ResourceManager.getConnection();     
	     preparedstatement=con.prepareStatement(strCountOralPreAuth);
	     
	     preparedstatement.setString(1,"ORAL");
	     preparedstatement.setString(2, "NTEL");
	     resultSet = preparedstatement.executeQuery();
		  if(resultSet.next())
		   count =  resultSet.getInt("ROWCOUNT");
		}catch(Exception exception){
			log.error("Error  in PreAuthDAOImpl getCountOralPreAuth()",exception);
			exception.printStackTrace();
		}
		finally{
			try{
				if(resultSet!=null)resultSet.close();
				if(preparedstatement!=null)preparedstatement.close();
			    if(con!=null)con.close();
			}catch(Exception exception2){
				log.error("Error while closing the Connection/PreparedStatement in PreAuthDAOImpl getCountOralPreAuth()",exception2);
				exception2.printStackTrace();}
		 }  
	 return count;	
	}


	public ArrayList<Object> getBenefitDetails(ArrayList<Object> alSearchCriteria) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		boolean recors=false;
		ArrayList<Object> alRes=new ArrayList<>();
		Collection<Object> alResultList = new ArrayList<Object>();
		try{
			conn = ResourceManager.getConnection();

			cStmtObject = conn.prepareCall(strGetbenefitDetails);
			if( alSearchCriteria.get(0)!=null)cStmtObject.setLong(1,new Long((String) alSearchCriteria.get(0)));
			else cStmtObject.setLong(1,new Long(0));
			if (alSearchCriteria.get(1)!=null)cStmtObject.setLong(2,new Long((String) alSearchCriteria.get(1)));
			else cStmtObject.setLong(2,new Long(0));
			if(alSearchCriteria.get(2)!=null)cStmtObject.setString(3, ((String) alSearchCriteria.get(2)).trim());
			else cStmtObject.setString(3, null);
			if(alSearchCriteria.get(3)!=null) cStmtObject.setLong(4,new Long((String) alSearchCriteria.get(3)));
			else cStmtObject.setLong(4,new Long(0));
			if(alSearchCriteria.get(4)!=null) cStmtObject.setString(5, ((String) alSearchCriteria.get(4)).trim()); //&& !((String)alSearchCriteria.get(4)).trim().equals("")) cStmtObject.setString(5 ,(String)alSearchCriteria.get(4));
			else cStmtObject.setString(5,null);
			cStmtObject.registerOutParameter(6,Types.OTHER);
			cStmtObject.registerOutParameter(7,Types.OTHER);
			cStmtObject.registerOutParameter(8,Types.OTHER);
			cStmtObject.execute();
			 rs = (java.sql.ResultSet)cStmtObject.getObject(6);
			 rs1 = (java.sql.ResultSet)cStmtObject.getObject(7);
			rs2 = (java.sql.ResultSet)cStmtObject.getObject(8);
			conn.commit();
			if(rs != null){
				while(rs.next()){
					recors=true;
					BenefitDetailVo benefitDetailVo=new BenefitDetailVo();
					benefitDetailVo.setBenefitName(TTKCommon.checkNull(rs.getString("BENEFIT_NAME")));
					benefitDetailVo.setSubBenefitName(TTKCommon.checkNull(rs.getString("SUB_BENEFIT_NAME")));
					benefitDetailVo.setStrConfigration(rs.getString("RULE_CONFIG"));
					benefitDetailVo.setLimit(rs.getString("COPAY_LIMIT"));
					benefitDetailVo.setCopay(rs.getString("COPAY"));
					benefitDetailVo.setDeductable(rs.getString("DEDUCTABLE"));   
					benefitDetailVo.setSession(rs.getString("SESSIONS"));
					benefitDetailVo.setMemWaitingPeriod(rs.getString("MEM_WAITING"));
					benefitDetailVo.setAmountUtilized(rs.getString("utlized_amount"));
					benefitDetailVo.setBalance(rs.getString("balance"));
					benefitDetailVo.setAntenatelScans(rs.getString("ANTENATEL_SCANS"));
					benefitDetailVo.setNoOfOPVisits(rs.getString("NO_OF_OP_VISITS"));
					benefitDetailVo.setLscsLimit(rs.getString("LSCS_LIMIT"));
					benefitDetailVo.setLscsCopay(rs.getString("LSCS_COPAY"));
					benefitDetailVo.setNormalDeleviryCopay(rs.getString("NORMAL_DELIVERY_COPAY"));
					benefitDetailVo.setNormalDeliveryLimit(rs.getString("NORMAL_DELIVERY_LIMIT"));
					benefitDetailVo.setTypeMode(rs.getString("TYPE_MODE"));
					alResultList.add(benefitDetailVo);
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			//if(!recors)	alResultList.add(new BenefitDetailVo());
			
			alRes.add(alResultList);
			
			recors=false;
			if(rs1 != null){
				while(rs1.next()){
					PreAuthDetailVO preAuthDetailVO = new PreAuthDetailVO();
					recors=true;
			alRes.add(rs1.getString("available_sum_insured"));
			alRes.add(rs1.getString("UTILISED_SUM_INSURED"));
				}
			}
			if(!recors) alRes.add(null);
			recors=false;
			if(rs2!=null){
				while(rs2.next()){
					recors=true;
			    //alRes.add(rs2.getString("OTHER_REMARKS"));
					
				}
				
			}
			 if(!recors) alRes.add(null);
			return alRes;
			
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
					if (rs1 != null) rs1.close();
					if (rs != null) rs.close();
				
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreAuthDAOImpl getBenefitDetails()",sqlExp);
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
						log.error("Error while closing the Statement in PreAuthDAOImpl getBenefitDetails()",sqlExp);
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
							log.error("Error while closing the Connection in PreAuthDAOImpl getBenefitDetails()",sqlExp);
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
				rs2 =null;
				rs1 =null;
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getClaimList(ArrayList alSearchCriteria)

	public ArrayList getMemberList(ArrayList<Object> alSearchCriteria)  throws TTKException{
			
		  Collection<Object> alResultList = new ArrayList<Object>();
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        ResultSet rs = null;
	        MemberDetailVO MemberDetailVO = null;
	        MemberAddressVO memberAddressVO = null;
	   try{
	        	
	            conn = ResourceManager.getConnection();
	            cStmtObject = conn.prepareCall(strMemberList);
	            
				cStmtObject.setString(1,(String)alSearchCriteria.get(0));
				cStmtObject.setString(2,(String)alSearchCriteria.get(2));
				cStmtObject.setString(3,(String)alSearchCriteria.get(3));
				cStmtObject.setString(4,(String)alSearchCriteria.get(4));
				cStmtObject.setString(5,(String)alSearchCriteria.get(5));
				cStmtObject.setLong(6,(Long)alSearchCriteria.get(1));
	cStmtObject.registerOutParameter(7,Types.OTHER);
	
	
	
	            cStmtObject.execute();
	            rs = (java.sql.ResultSet)cStmtObject.getObject(7);
	            if(rs != null){
	                while(rs.next()){
					MemberDetailVO = new MemberDetailVO();
					memberAddressVO = new MemberAddressVO();
					MemberDetailVO.setMemberSeqID(rs.getLong("MEMBER_SEQ_ID"));
					MemberDetailVO.setPolicySeqId(rs.getLong("POLICY_SEQ_ID"));
					MemberDetailVO.setMemberId(rs.getString("tpa_enrollment_id"));
					MemberDetailVO.setMemberActive(rs.getString("Member_Acitive"));
					
					MemberDetailVO.setPolicyNumber(TTKCommon.checkNull(rs.getString("policy_number")));
					MemberDetailVO.setStart_date(TTKCommon.checkNull(rs.getString("start_date")));
					MemberDetailVO.setEnd_date(TTKCommon.checkNull(rs.getString("end_date")));
					MemberDetailVO.setPatientName(rs.getString("MEM_NAME"));
					MemberDetailVO.setMemberAge(rs.getInt("MEM_AGE"));
					MemberDetailVO.setEmirateId(rs.getString("EMIRATE_ID"));
					MemberDetailVO.setPayerId((rs.getString("PAYER_ID")));
					MemberDetailVO.setPayerName(rs.getString("INS_COMP_NAME"));
					MemberDetailVO.setInsSeqId(rs.getLong("INS_SEQ_ID"));
					MemberDetailVO.setPatientGender(rs.getString("GENDER"));
					MemberDetailVO.setCorporateName(rs.getString("CORPORATE_NAME"));
					MemberDetailVO.setCorporateName1(rs.getString("CORPORATE_NAME1"));
					MemberDetailVO.setNationality(rs.getString("NATIONALITY"));
					MemberDetailVO.setSumInsured(rs.getBigDecimal("SUM_INSURED"));
					MemberDetailVO.setAvailableSumInsured(rs.getBigDecimal("AVA_SUM_INSURED"));
					MemberDetailVO.setProductName(rs.getString("PRODUCT_NAME"));
					MemberDetailVO.setProvAuthority(rs.getString("AUTHORITY"));
					MemberDetailVO.setEligibleNetworks(rs.getString("NETWORK_TYPE"));
					MemberDetailVO.setPreMemInceptionDt(rs.getString("MEMBER_ADDED_DATE"));
					MemberDetailVO.setCorporate_id(rs.getString("corporate_id"));
					MemberDetailVO.setVipYorN(rs.getString("VIP_YN"));
					MemberDetailVO.setMemberDOB(rs.getString("mem_dob"));
					MemberDetailVO.setMemberExitDate(rs.getString("mem_exit_date"));
					MemberDetailVO.setMember_network(TTKCommon.checkNull(rs.getString("member_network")));
					MemberDetailVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
					if(!TTKCommon.checkNull(rs.getString("mobile_no")).equals("")){
						   String mobile = rs.getString("mobile_no");
						   String[] mobileNo = mobile.split("\\|");
						   if(mobileNo.length<=1){
							   memberAddressVO.setMobileNbr(mobileNo[0]);
						   }else{
						   for(int i=0; i<=mobileNo.length;){
							   memberAddressVO.setMobileIsdCode(mobileNo[i]);
							   memberAddressVO.setMobileNbr(mobileNo[++i]);  
							   break;
						   }
						   }
					   }else{
						   memberAddressVO.setMobileNbr("");   
						   memberAddressVO.setMobileIsdCode("");
					   }
            		MemberDetailVO.setMemberAddressVO(memberAddressVO);
            		alResultList.add(MemberDetailVO);
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
	                    log.error("Error while closing the Resultset in PreAuthDAOImpl getMemberList()",sqlExp);
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
	                        log.error("Error while closing the Statement in PreAuthDAOImpl getMemberList()",sqlExp);
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
	                            log.error("Error while closing the Connection in PreAuthDAOImpl getMemberList()",sqlExp);
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
	}

	public PreAuthDetailVO getMemberDetails(String memberId) throws TTKException{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthDetailVO preAuthDetailVO = null;

		try{

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetMemberDetails);
			cStmtObject.setString(1,memberId);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(3,OracleTypes.INTEGER);
			cStmtObject.execute();
         
           
           rs=(ResultSet)cStmtObject.getObject(2);	
    	   

			if(rs != null){
				while(rs.next()){
					
					

					preAuthDetailVO = new PreAuthDetailVO();
					    preAuthDetailVO.setMemberSeqID(rs.getLong("MEMBER_SEQ_ID"));
					    preAuthDetailVO.setClaimantName(rs.getString("MEM_NAME"));
					    preAuthDetailVO.setMemberAge(rs.getInt("MEM_AGE"));
					    preAuthDetailVO.setEnrollmentID(TTKCommon.checkNull(rs.getString("EMIRATE_ID")));
					    preAuthDetailVO.setPayerId(TTKCommon.checkNull(rs.getString("PAYER_ID")));
					    preAuthDetailVO.setInsSeqId(rs.getLong("INS_SEQ_ID"));
					    preAuthDetailVO.setPolicySeqId(rs.getLong("POLICY_SEQ_ID"));
					    preAuthDetailVO.setPayerName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
					    preAuthDetailVO.setPatientGender(TTKCommon.checkNull(rs.getString("GENDER")));
					    preAuthDetailVO.setPolicyNumber(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					    preAuthDetailVO.setPolicyCategory(TTKCommon.checkNull(rs.getString("CLASSIFICATION")));
					    preAuthDetailVO.setCorporateName(TTKCommon.checkNull(rs.getString("CORPORATE_NAME")));
					    preAuthDetailVO.setPolicyStartDate(TTKCommon.convertDateAsString("dd/MM/yyyy",rs.getDate("POLICY_START_DATE")));
                        preAuthDetailVO.setPolicyEndDate(TTKCommon.convertDateAsString("dd/MM/yyyy",rs.getDate("POLICY_END_DATE")));
					    preAuthDetailVO.setNationality(TTKCommon.checkNull(rs.getString("NATIONALITY")));
					    preAuthDetailVO.setSumInsured(rs.getBigDecimal("SUM_INSURED"));
					    preAuthDetailVO.setAvailableSumInsured(rs.getBigDecimal("AVA_SUM_INSURED"));
					    preAuthDetailVO.setPolicyType(rs.getString("ENROL_TYPE_ID"));
					    preAuthDetailVO.setEmirateId(rs.getString("EMIRATE_ID"));

					    preAuthDetailVO.setProductName(rs.getString("PRODUCT_NAME"));
                        preAuthDetailVO.setPayerAuthority(rs.getString("PAYER_AUTHORITY"));
					    preAuthDetailVO.setVipYorN(rs.getString("VIP_YN"));

                        preAuthDetailVO.setClmMemInceptionDate(rs.getString("clm_mem_insp_date"));
				}//end of while(rs.next())
				
				
				
				
				
			}//end of if(rs != null)
            return preAuthDetailVO;
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
					log.error("Error while closing the Resultset in ClaimDAOImpl getMemberDetails()",sqlExp);
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
						log.error("Error while closing the Statement in ClaimDAOImpl getMemberDetails()",sqlExp);
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
							log.error("Error while closing the Connection in ClaimDAOImpl getMemberDetails()",sqlExp);
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
	}

	public ArrayList getProviderMemberList(ArrayList<Object> alSearchCriteria, Long lngHospSeqId) throws TTKException {
		
		  Collection<Object> alResultList = new ArrayList<Object>();
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        ResultSet rs = null;
	        MemberDetailVO memberDetailVO = null;
	        
	        try{
	            conn = ResourceManager.getConnection();
	            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strProviderMemberList);
	            
	           /* cStmtObject.setLong(1,lngHospSeqId);*/
	            
	            cStmtObject.setString(1,(String)alSearchCriteria.get(0));
	            cStmtObject.setString(2,(String)alSearchCriteria.get(1));
	            cStmtObject.setString(3,(String)alSearchCriteria.get(3));
	            cStmtObject.setString(4,(String)alSearchCriteria.get(4));
	            cStmtObject.setLong(5,(Long.parseLong(alSearchCriteria.get(5).toString())));
	            cStmtObject.setLong(6,(Long.parseLong(alSearchCriteria.get(6).toString())));
	            cStmtObject.setLong(7,(Long)alSearchCriteria.get(2));
	
	            cStmtObject.registerOutParameter(8,Types.OTHER);
	
	            cStmtObject.execute();
	            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
	            if(rs != null){
	                while(rs.next()){
	                	
	                	memberDetailVO = new MemberDetailVO();
	                	memberDetailVO.setMemberSeqID(rs.getLong("MEMBER_SEQ_ID"));
	                	memberDetailVO.setPolicySeqId(rs.getLong("POLICY_SEQ_ID"));
	                	memberDetailVO.setMemberId(rs.getString("tpa_enrollment_id"));
	                	memberDetailVO.setPolicyNumber(TTKCommon.checkNull(rs.getString("policy_number")));
	                	memberDetailVO.setStart_date(TTKCommon.checkNull(rs.getString("start_date")));
	                	memberDetailVO.setEnd_date(TTKCommon.checkNull(rs.getString("end_date")));
	                	memberDetailVO.setPatientName(rs.getString("MEM_NAME"));
	                	memberDetailVO.setMemberAge(rs.getInt("MEM_AGE"));
	                	memberDetailVO.setEmirateId(rs.getString("EMIRATE_ID"));
	                	memberDetailVO.setPayerId((rs.getString("PAYER_ID")));
	                	memberDetailVO.setPayerName(rs.getString("INS_COMP_NAME"));
	                	memberDetailVO.setInsSeqId(rs.getLong("INS_SEQ_ID"));
	                	memberDetailVO.setPatientGender(rs.getString("GENDER"));
	                	memberDetailVO.setCorporateName(rs.getString("CORPORATE_NAME"));
	                	memberDetailVO.setNationality(rs.getString("NATIONALITY"));
	                	memberDetailVO.setSumInsured(rs.getBigDecimal("SUM_INSURED"));
	                	memberDetailVO.setAvailableSumInsured(rs.getBigDecimal("AVA_SUM_INSURED"));
	                	memberDetailVO.setProductName(rs.getString("PRODUCT_NAME"));
	                	memberDetailVO.setProvAuthority(rs.getString("AUTHORITY"));
	                	memberDetailVO.setEligibleNetworks(rs.getString("NETWORK_TYPE"));
	                	memberDetailVO.setPreMemInceptionDt(rs.getString("MEM_INSP_DATE"));	
	                	memberDetailVO.setCorporate_id(rs.getString("corporate_id"));	
	                	memberDetailVO.setVipYorN(rs.getString("VIP_YN"));	
	                	memberDetailVO.setMember_network(TTKCommon.checkNull(rs.getString("member_network")));
	                	memberDetailVO.setDhpoMemberId(TTKCommon.checkNull(rs.getString("DHPO_MEMBER_ID")));
	
	                    alResultList.add(memberDetailVO);
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
	                    log.error("Error while closing the Resultset in PreAuthDAOImpl getMemberList()",sqlExp);
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
	                        log.error("Error while closing the Statement in PreAuthDAOImpl getMemberList()",sqlExp);
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
	                            log.error("Error while closing the Connection in PreAuthDAOImpl getMemberList()",sqlExp);
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
		
		
	}
			
	public Document getPATXmlForViewDocument(Long preAuthSeqID) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		Document doc = null;
		try {
			
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strActivityViewDoc);
			cStmtObject.setLong(1, preAuthSeqID);
			cStmtObject.registerOutParameter(2, Types.SQLXML);
			cStmtObject.execute();

			SAXReader saxReader = new SAXReader();			
			doc = saxReader.read(cStmtObject.getSQLXML(2).getCharacterStream());
            return doc;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)

		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl copayAdviced()",
							sqlExp);
        			throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl copayAdviced()",
								sqlExp);
        				throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
	}// end of getPATXmlTOSendCEED(Long preAuthSeqID)

	private String providerStatusValidation(String preAuthRecvTypeID,String hospLicenseCount,String empanelValidityFlag){

		String strErrMsg="";
		
		 if("DHP".equals(preAuthRecvTypeID)&&"N".equals(empanelValidityFlag)&&"N".equals(hospLicenseCount)){
				
			 strErrMsg="Provider is not empanelled";
			}		
		 else  if("DHP".equals(preAuthRecvTypeID)&&"N".equals(empanelValidityFlag)&&"Y".equals(hospLicenseCount)){
			 
			 strErrMsg="Provider is not empanelled";
				
			}		
		else if("DHP".equals(preAuthRecvTypeID)&&"N".equals(hospLicenseCount)&&"Y".equals(empanelValidityFlag)){
	
			strErrMsg="Invalid Provider ID";
			}		
		return strErrMsg;
	}
	
public ArrayList getPartnerPreAuthList(ArrayList alSearchCriteria) throws TTKException {
		
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		PreAuthVO preAuthVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPartnerPreAuthList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));
			
			if(!(alSearchCriteria.get(10)==null||alSearchCriteria.get(10).equals("")))
			cStmtObject.setLong(10,Long.parseLong((String)alSearchCriteria.get(10)));
			
			if(!(alSearchCriteria.get(11)==null||alSearchCriteria.get(11).equals("")))
				cStmtObject.setLong(11,Long.parseLong((String)alSearchCriteria.get(11)));
			
			cStmtObject.registerOutParameter(12,Types.OTHER);
			cStmtObject.execute();
			
			rs = (java.sql.ResultSet)cStmtObject.getObject(12);
			conn.commit();
			if(rs != null){
				while(rs.next()){
					preAuthVO = new PreAuthVO();	
					preAuthVO.setPreauthPartnerRefId(rs.getString("onl_pre_auth_refno"));
					preAuthVO.setStatus(rs.getString("onl_pat_status"));
					preAuthVO.setPartnerName(rs.getString("partner_name"));
					preAuthVO.setEnrollmentID(rs.getString("tpa_enrollment_id"));
					preAuthVO.setClaimantName(rs.getString("MEM_NAME"));
				
					if(rs.getString("added_date")!=null){
					preAuthVO.setReceivedDate(rs.getDate("added_date"));
					preAuthVO.setReceivedDateAsString(TTKCommon.getFormattedDate(preAuthVO.getReceivedDate()));
					}
					if(rs.getString("admission_date")!=null)
					preAuthVO.setClaimAdmnDate(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("admission_date")));
					if(rs.getString("discharge_date")!=null)
					preAuthVO.setClaimDisrgDate(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("discharge_date")));
					
					preAuthVO.setPreAuthSeqID(rs.getLong("onl_pat_auth_seq_id"));
					
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
	

public Object[] getPartnerPreAuthDetails(Long preAuthSeqID) throws TTKException{
	
	Connection conn = null;
	CallableStatement cStmtObject=null;
	ResultSet prs=null;
	PreAuthDetailVO preAuthDetailVO  = new PreAuthDetailVO();
	Object[] preauthAllResult=new Object[4];
	try{
		conn = ResourceManager.getConnection(); 
		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPartnerPreAuthorization);

		cStmtObject.setLong(1,preAuthSeqID);			
		cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
		cStmtObject.execute();
		prs = (java.sql.ResultSet)cStmtObject.getObject(2);//preauth Details
		
				
		if(prs != null){
			if(prs.next()){
					preAuthDetailVO.setPreauthPartnerRefId(TTKCommon.checkNull(prs.getString("onl_pre_auth_refno")));
					
					/*if(prs.getString("PAYER_AUTHORITY") !=null)preAuthDetailVO.setPayerAuthority(prs.getString("PAYER_AUTHORITY"));
					preAuthDetailVO.setPreAuthNo(TTKCommon.checkNull(prs.getString("PAYER_AUTHORITY")));	*/
					
               if(prs.getLong("ptnr_seq_id") !=0)preAuthDetailVO.setPartnerSeqId(prs.getLong("ptnr_seq_id"));
              
               if(prs.getLong("onl_pat_auth_seq_id") !=0)preAuthDetailVO.setPartnerPreAuthSeqId(prs.getLong("onl_pat_auth_seq_id"));
               
               if(prs.getString("TPA_ENROLLMENT_ID") != null){
					preAuthDetailVO.setMemberId(prs.getString("TPA_ENROLLMENT_ID"));
				}//end of if(prs.getString("PAT_REQUESTED_AMOUNT") != null)
               
				if(prs.getString("added_date") != null){
					preAuthDetailVO.setReceiveDate(prs.getString("added_date"));
				}
				if(prs.getString("submission_category")!= null){
					preAuthDetailVO.setSubmissionCategory(prs.getString("submission_category"));
				}
				if(prs.getString("EMIRATE_ID") !=null){
					preAuthDetailVO.setEmirateId(prs.getString("EMIRATE_ID"));
				}	
				if(prs.getString("MEM_NAME") != null){
					preAuthDetailVO.setPatientName(prs.getString("MEM_NAME"));
				}//end of if(prs.getString("MEM_NAME") != null)
				if(prs.getInt("MEM_AGE") != 0){
					preAuthDetailVO.setMemberAge(prs.getInt("MEM_AGE"));
				}
				if(prs.getString("gender_general_type_id") !=null){
					preAuthDetailVO.setPatientGender(prs.getString("gender_general_type_id"));
				}
				if(prs.getString("POLICY_NUMBER") !=null){
					preAuthDetailVO.setPolicyNumber(prs.getString("POLICY_NUMBER"));
				}
				if(prs.getString("group_name") !=null){
					preAuthDetailVO.setGroupName(prs.getString("group_name"));
				}
				if(prs.getString("PRODUCT_NAME") !=null)
					preAuthDetailVO.setProductName(prs.getString("PRODUCT_NAME"));
				if(prs.getString("product_cat_type_id") !=null)
					preAuthDetailVO.setProductCatTypeId(prs.getString("product_cat_type_id"));
				if(prs.getString("authority_type") !=null)
					preAuthDetailVO.setAuthorityType(prs.getString("authority_type"));
				if(prs.getString("insured_name") !=null)
					preAuthDetailVO.setInsuredName(prs.getString("insured_name"));
				if(prs.getString("effective_from_date") != null)
					preAuthDetailVO.setEffectiveFromDate(prs.getString("effective_from_date"));
				if(prs.getString("effective_to_date") != null)
						preAuthDetailVO.setEffectiveToDate(prs.getString("effective_to_date"));
				if(prs.getLong("sum_insured") != 0)
					preAuthDetailVO.setSumInsured(new BigDecimal(prs.getLong("sum_insured")));
				if(prs.getLong("available_sum_insured") != 0)
					preAuthDetailVO.setAvailableSumInsured(new BigDecimal(prs.getLong("available_sum_insured")));
				if(prs.getLong("estimated_amount") != 0)
					preAuthDetailVO.setRequestedAmount(new BigDecimal(prs.getLong("estimated_amount")));
				if(prs.getString("currency_id")!=null)
					preAuthDetailVO.setRequestedAmountcurrencyType(prs.getString("currency_id"));
			
				if(prs.getLong("country_id") != 0)
					preAuthDetailVO.setProviderCountry((""+prs.getLong("country_id")).trim());
				if(prs.getString("description") != null){
						preAuthDetailVO.setDescription(prs.getString("description"));
					}
					if(prs.getString("VIP") != null){
							preAuthDetailVO.setVipYorN(prs.getString("VIP"));
					}
						if(prs.getString("policy_issue_date") !=null){
							  preAuthDetailVO.setPolicyIssueDate(prs.getString("policy_issue_date"));
							}
						
							if(prs.getString("HOSP_NAME") !=null){
								preAuthDetailVO.setProviderName(prs.getString("HOSP_NAME"));
							}
							if(prs.getString("country_name") !=null){
								preAuthDetailVO.setCountryName(prs.getString("country_name"));
							}	
							if(prs.getString("BENIFIT_TYPE") !=null){
								preAuthDetailVO.setBenefitType(prs.getString("BENIFIT_TYPE"));
							}
							if(prs.getString("HOSPITALIZATION_DATE") != null){
								preAuthDetailVO.setHospitalzationDate(prs.getString("HOSPITALIZATION_DATE"));
							}
							if(prs.getString("DISCHARGE_DATE") != null){
								preAuthDetailVO.setDischargeDate(prs.getString("DISCHARGE_DATE"));
							}	
							
							if(prs.getString("partner_name") != null){
								preAuthDetailVO.setPartnerName(prs.getString("partner_name"));
							}
							
							if(prs.getString("preauth_number")!=null){
								preAuthDetailVO.setPreAuthSeqID(prs.getLong("preauth_number"));
							}
							
							if(prs.getString("PRE_AUTH_CAT")!=null){
								preAuthDetailVO.setProcessType(prs.getString("PRE_AUTH_CAT"));
							}
							
							if(prs.getString("MEMBER_SEQ_ID")!=null){
								preAuthDetailVO.setMemberSeqID(prs.getLong("MEMBER_SEQ_ID"));
							}
							
							if(prs.getString("INS_SEQ_ID")!=null){
								preAuthDetailVO.setInsSeqId(prs.getLong("INS_SEQ_ID"));
							}
							
							if(prs.getString("POLICY_SEQ_ID")!=null){
								preAuthDetailVO.setPolicySeqId(prs.getLong("POLICY_SEQ_ID"));
							}	
			
							if(prs.getString("ENCOUNTER_START_TYPE")!=null){
								preAuthDetailVO.setEncounterStartTypeId(prs.getString("ENCOUNTER_START_TYPE"));
							}
							if(prs.getString("source_type_id")!=null){
								preAuthDetailVO.setPreAuthRecvTypeID(TTKCommon.checkNull(prs.getString("source_type_id")));
							}
							
				}
		preauthAllResult[0]=preAuthDetailVO;
	}//end of 	if(prs != null)
		
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
				if (prs != null) prs.close();
				
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
			prs = null;
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
	
	return preauthAllResult;
}//end of getPreAuthDetails(long lngPreAuthSeqID,long lngUserSeqID,String strSelectionType)



public String saveDentalDetails(DentalOrthoVO dentalOrthoVO,String PatOrClm) throws TTKException{
	Connection conn = null;
	CallableStatement cStmtObject=null;
	OracleResultSet rs = null;	
	
	try{
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePreAuthDentalDetails);
	
		if(dentalOrthoVO.getDentoSeqid()!=null)
			cStmtObject.setLong(1,dentalOrthoVO.getDentoSeqid());
		else
			cStmtObject.setLong(1,0);
		cStmtObject.setLong(2,dentalOrthoVO.getPreAuthSeqid());
		cStmtObject.setString(3,PatOrClm);
		cStmtObject.setString(4,dentalOrthoVO.getDentoclass1());
		cStmtObject.setString(5,dentalOrthoVO.getDentoclass2());
		cStmtObject.setString(6,dentalOrthoVO.getDentoclass2Text());
		cStmtObject.setString(7,dentalOrthoVO.getDentoclass3());
		cStmtObject.setString(8,dentalOrthoVO.getSkeletalClass1());
		cStmtObject.setString(9,dentalOrthoVO.getSkeletalClass2());
		cStmtObject.setString(10,dentalOrthoVO.getSkeletalClass3());
		cStmtObject.setString(11,dentalOrthoVO.getOverJet());
		cStmtObject.setString(12,dentalOrthoVO.getReverseJet());
		cStmtObject.setString(13,dentalOrthoVO.getReverseJetYN());
		cStmtObject.setString(14,dentalOrthoVO.getCrossbiteAntrio());
		cStmtObject.setString(15,dentalOrthoVO.getCrossbitePosterior());
		cStmtObject.setString(16,dentalOrthoVO.getCrossbiteRetrucontract());
		cStmtObject.setString(17,dentalOrthoVO.getOpenbiteAntrio());
		cStmtObject.setString(18,dentalOrthoVO.getOpenbitePosterior());
		cStmtObject.setString(19,dentalOrthoVO.getOpenbiteLateral());
		cStmtObject.setString(20,dentalOrthoVO.getContactPointDisplacement());
		cStmtObject.setString(21,dentalOrthoVO.getOverBite());
		cStmtObject.setString(22,dentalOrthoVO.getOverbitePalatalYN());
		cStmtObject.setString(23,dentalOrthoVO.getOverbiteGingivalYN());
		cStmtObject.setString(24,dentalOrthoVO.getHypodontiaQuand1Teeth());
		cStmtObject.setString(25,dentalOrthoVO.getHypodontiaQuand2Teeth());
		cStmtObject.setString(26,dentalOrthoVO.getHypodontiaQuand3Teeth());
		cStmtObject.setString(27,dentalOrthoVO.getHypodontiaQuand4Teeth());
		cStmtObject.setString(28,dentalOrthoVO.getImpededTeethEruptionNo());
		cStmtObject.setString(29,dentalOrthoVO.getImpededTeethNo());
		cStmtObject.setString(30,dentalOrthoVO.getSubmergerdTeethNo());
		cStmtObject.setString(31,dentalOrthoVO.getSupernumeryTeethNo());
		cStmtObject.setString(32,dentalOrthoVO.getRetainedTeethNo());
		cStmtObject.setString(33,dentalOrthoVO.getEctopicTeethNo());
		cStmtObject.setString(34,dentalOrthoVO.getCranioFacialNo());
		cStmtObject.setString(35,dentalOrthoVO.getAestheticComp());
		cStmtObject.setString(36,dentalOrthoVO.getCrossbiteAntriomm());
		cStmtObject.setString(37,dentalOrthoVO.getCrossbitePosteriormm());
		cStmtObject.setString(38,dentalOrthoVO.getCrossbiteRetrucontractmm());
		cStmtObject.setString(39,dentalOrthoVO.getContactPointDisplacementmm());
		cStmtObject.setString(40,dentalOrthoVO.getDentoclass3Text());
		
		cStmtObject.registerOutParameter(41,Types.VARCHAR);
		cStmtObject.executeUpdate();
		String iotn = cStmtObject.getString(41);
		return iotn;
	 }//end of try
		catch (SQLException sqlExp) 
		{
			throw new TTKException(sqlExp, "policy");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp) 
		{
			throw new TTKException(exp, "policy");
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
					log.error("Error while closing the Resultset in PreAuthDAOImpl saveDentalDetails()",sqlExp);
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
						log.error("Error while closing the Statement in PreAuthDAOImpl saveDentalDetails()",sqlExp);
						throw new TTKException(sqlExp, "policy");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PreAuthDAOImpl saveDentalDetails()",sqlExp);
							throw new TTKException(sqlExp, "policy");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "policy");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally	
	
	}//long saveDentalDetails(DentalOrthoVO dentalOrthoVO


public BigDecimal[] getCalculatedPreauthAmount(Long preauthSeqId,Long hospitalSeqID, Long userSeqId) throws TTKException {
	Connection conn = null;
	CallableStatement cStmtObject=null;
	ResultSet rs = null;
	BigDecimal[] calculateAmunts=new BigDecimal[7]; 
	try{
		conn = ResourceManager.getConnection();
		cStmtObject = conn.prepareCall(strGetCalculatePreauthAmount);

		cStmtObject.setLong(1,preauthSeqId);
		cStmtObject.setLong(2,hospitalSeqID);
		cStmtObject.registerOutParameter(3,Types.DOUBLE);
		cStmtObject.registerOutParameter(4,Types.OTHER);
		cStmtObject.setLong(5,userSeqId);
		cStmtObject.execute();
		//calculateAmunts[0]= cStmtObject.getBigDecimal(3);;
		rs = (java.sql.ResultSet)cStmtObject.getObject(4);
		
	if(rs!=null){	
		while(rs.next()){
			calculateAmunts[0]=rs.getBigDecimal("TOT_GROSS_AMOUNT");
			calculateAmunts[1]=rs.getBigDecimal("TOT_DISCOUNT_AMOUNT");
			calculateAmunts[2]=rs.getBigDecimal("TOT_DISC_GROSS_AMOUNT");
			calculateAmunts[3]=rs.getBigDecimal("TOT_PATIENT_SHARE_AMOUNT");
			calculateAmunts[4]=rs.getBigDecimal("TOT_NET_AMOUNT");
			calculateAmunts[5]=rs.getBigDecimal("TOT_ALLOWED_AMOUNT");
			calculateAmunts[6]=rs.getBigDecimal("TOT_APPROVED_AMOUNT");
		}
		}
	    conn.commit();
		return calculateAmunts;
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
				log.error("Error while closing the Resultset in PreAuthDAOImpl getCalculatedPreauthAmount()",sqlExp);
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
					log.error("Error while closing the Statement in PreAuthDAOImpl getCalculatedPreauthAmount()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl getCalculatedPreauthAmount()",sqlExp);
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
}//end of getCalculatedPreauthAmount(Long preauthSeqId)

public Object[] deleteActivityDetails(long preauthSeqId,long activityDtlSeqId,String authType,long userSeqId) throws TTKException {
	Object[] objArrayResult = new Object[2];
	Connection conn = null;
	CallableStatement cStmtObject=null;
	try{		
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteActivityDetails);
		cStmtObject.setLong(1,preauthSeqId);
		   cStmtObject.setLong(2,activityDtlSeqId);
		   cStmtObject.setString(3,authType);
		   cStmtObject.setLong(4,userSeqId);
		   cStmtObject.registerOutParameter(5,Types.INTEGER);
		   cStmtObject.execute();			
		  objArrayResult[0] = cStmtObject.getInt(5);		
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
				log.error("Error while closing the Statement in PreAuthDAOImpl deleteActivityDetails()",sqlExp);
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
					log.error("Error while closing the Connection in PreAuthDAOImpl deleteActivityDetails()",sqlExp);
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
}//end of deleteActivityDetails()

public InputStream getShortfallDBFile(String shortfallSeqID)throws TTKException{
	
	Connection conn = null;
	PreparedStatement cStmtObject=null;
	ResultSet rs=null;
	Blob blob	=	null;
	InputStream iStream	=	null;
	try{
		conn = ResourceManager.getConnection();
                 
         cStmtObject = conn.prepareCall(strGetShortfallFile);
       
         cStmtObject.setString(1, shortfallSeqID);
			rs= cStmtObject.executeQuery();
			
			if(rs != null){
				if(rs.next())
					blob	=	rs.getBlob(1);
				iStream	=	blob.getBinaryStream();
			}//end of if(rs != null)
	    	//return (ArrayList<Object>)alMouList;
			
    }//end of try
    catch (SQLException sqlExp) 
    {
        throw new TTKException(sqlExp, "policy");
    }//end of catch (SQLException sqlExp)
    catch (Exception exp) 
    {
        throw new TTKException(exp, "policy");
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
				log.error("Error while closing the Resultset in getShortfallDBFile getDhpoPreauthDetails()",sqlExp);
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
					log.error("Error while closing the Statement in getShortfallDBFile getDhpoPreauthDetails()",sqlExp);
    			throw new TTKException(sqlExp, "policy");
    		}//end of catch (SQLException sqlExp)
    		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    		{
    			try
    			{
    				if(conn != null) conn.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
						log.error("Error while closing the Connection in getShortfallDBFile getDhpoPreauthDetails()",sqlExp);
    				throw new TTKException(sqlExp, "policy");
    			}//end of catch (SQLException sqlExp)
    		}//end of finally Connection Close
			}//end of finally Statement Close
    	}//end of try
    	catch (TTKException exp)
    	{
    		throw new TTKException(exp, "policy");
    	}//end of catch (TTKException exp)
    	finally // Control will reach here in anycase set null to the objects 
    	{
			rs = null;
    		cStmtObject = null;
    		conn = null;
    	}//end of finally
	}//end of finally	
    return iStream;
}

public ArrayList<ArrayList<String[]>> getPreauthHistoryListPreAuthClaim(Long memberSeqID)throws TTKException
{
Connection conn = null;
	CallableStatement cStmtObject = null;
   ResultSet ars = null;
	ArrayList<String[]> authListPAT = null;
	ArrayList<String[]> authListCLM = null;
	ArrayList<ArrayList<String[]>> al = new ArrayList<ArrayList<String[]>>();
	try {
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strPreAuthorizationMemberHistoryList);

		cStmtObject.setLong(1, memberSeqID);
		cStmtObject.registerOutParameter(2, Types.OTHER);
		cStmtObject.execute();
		ars = (java.sql.ResultSet) cStmtObject.getObject(2);

		if (ars != null) {
			authListPAT = new ArrayList<String[]>();
			authListCLM =  new ArrayList<String[]>();
			while (ars.next()) {
				String authSeqId = "";
				String preAuthNo = "";
				String authNo = "";
				String flag = ars.getString("flag");
				if ("P".equals(flag)) {
					authSeqId = ars.getLong("PAT_AUTH_SEQ_ID") == 0 ? ""
							: ars.getLong("PAT_AUTH_SEQ_ID") + "";
					preAuthNo = ars.getString("PRE_AUTH_NUMBER") == null ? ""
							: ars.getString("PRE_AUTH_NUMBER");
					authNo = ars.getString("AUTH_NUMBER") == null ? ""
							: ars.getString("AUTH_NUMBER");
				} if ("C".equals(flag)) {
					authSeqId = ars.getLong("CLAIM_SEQ_ID") == 0 ? "" : ars
							.getLong("CLAIM_SEQ_ID") + "";
					preAuthNo = ars.getString("CLAIM_NUMBER") == null ? ""
							: ars.getString("CLAIM_NUMBER");
					authNo = ars.getString("settlement_number") == null ? ""
							: ars.getString("settlement_number");
				}
				
				String hospitalName=ars.getString("HOSP_NAME")==null?"":ars.getString("HOSP_NAME");
				String approvedAmt=ars.getBigDecimal("TOT_APPROVED_AMOUNT")==null?"":ars.getBigDecimal("TOT_APPROVED_AMOUNT").toString();
				String admissionDate=ars.getDate("ADMISSION_DATE")==null?"":ars.getDate("ADMISSION_DATE").toString();
				String status=ars.getString("PAT_STATUS")==null?"":ars.getString("PAT_STATUS");
				
				
				String approvedDate=ars.getString("approved_date")==null?"":ars.getString("approved_date");
				String paymentDate=ars.getString("payment_date")==null?"":ars.getString("payment_date");
				
				
				
				
				if ("P".equals(flag))
					authListPAT.add(new String[]{authSeqId,preAuthNo,authNo,hospitalName,approvedAmt,admissionDate,status,approvedDate,paymentDate,flag});			
				else if("C".equals(flag))
					authListCLM.add(new String[]{authSeqId,preAuthNo,authNo,hospitalName,approvedAmt,admissionDate,status,approvedDate,paymentDate,flag});			
				
			}
			
		}
		al.add(authListPAT);
		al.add(authListCLM);
		return al;
	}// end of try
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "preauth");
	}// end of catch (Exception exp)
	finally {
		/* Nested Try Catch to ensure resource closure */
		try // First try closing the result set
		{
			try {
				if (ars != null)
					ars.close();
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Resultset in PreAuthDAOImpl getPreauthHistoryList()",
						sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
							"Error while closing the Statement in PreAuthDAOImpl getPreauthHistoryList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl getPreauthHistoryList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
		}// end of try
		catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
		{
			ars = null;
			cStmtObject = null;
			conn = null;
		}// end of finally
	}// end of finally
}// end of getPreauthHistoryList(Long memberSeqID)

public String[][] getMemberRuleData(String strBenefitType, String memberSeqID)throws TTKException
{
	// TODO Auto-generated method stub
	
	Connection conn = null;
	CallableStatement cStmtObject = null;
   ResultSet rs1 = null;
   String[][] tobBenefits	=	new String[7][11];
   try {
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strMemberRuleDate);

		cStmtObject.setBigDecimal(1, new BigDecimal(memberSeqID));
		cStmtObject.setString(2, strBenefitType);
		cStmtObject.registerOutParameter(3, Types.OTHER);
		
		cStmtObject.execute();
		
		
		rs1 = (java.sql.ResultSet)cStmtObject.getObject(3);
		if(rs1 != null)
		{
			int i =0;
			while(rs1.next())
			{
				int j=0;
				if("DNTL".equals(strBenefitType)  || "OPTC".equals(strBenefitType))
				{
					tobBenefits[0][0]	=	rs1.getString("COPAY_DED");
					tobBenefits[0][1]	=	rs1.getString("TOTAL_LIMIT");
					tobBenefits[0][2]	=	rs1.getString("AVA_AMOUNT");
				}
				else if("DAYC".equals(strBenefitType))
				{
					tobBenefits[0][0]	=	rs1.getString("COPAY");
				}
				else if("CHRO".equals(strBenefitType) || "PED".equals(strBenefitType))
				{
					tobBenefits[0][0]	=	rs1.getString("Waiting_period");
					tobBenefits[0][1]	=	rs1.getString("DATE_OF_INCEPTION");
				}
				else if("MTI".equals(strBenefitType))
				{
					//waiting Period 
					tobBenefits[0][0]	=	rs1.getString("waiting_period");
					
					//inpatient (Inpatient with  emergency)
					tobBenefits[0][1]	=	rs1.getString("em_nrml_copay");
					tobBenefits[0][2]	=	rs1.getString("em_nrml_limit");
					tobBenefits[0][3]	=	rs1.getString("em_nrml_ava_limit");
					
					//inpatient (Inpatient without emergency)
					tobBenefits[0][4]	=	rs1.getString("nrml_copay");
					tobBenefits[0][5]	=	rs1.getString("nrml_limit");
					tobBenefits[0][6]	=	rs1.getString("nrml_ava_limit");
					
					//inpatient LSCS(Inpatient with  emergency)
					tobBenefits[0][7]	=	rs1.getString("em_lscs_copay");
					tobBenefits[0][8]	=	rs1.getString("em_lscs_limit");
					tobBenefits[0][9]	=	rs1.getString("em_lscs_ava_limit");
					
					////inpatient LSCS(Inpatient without emergency)
					tobBenefits[0][10]	=	rs1.getString("lscs_copay");
					tobBenefits[1][0]	=	rs1.getString("lscs_limit");
					tobBenefits[1][1]	=	rs1.getString("lscs_ava_limit");
					
					//outpatient (with emergency)
					tobBenefits[1][2]	=	rs1.getString("ope_limit");
					tobBenefits[1][3]	=	rs1.getString("ope_copay");
					tobBenefits[1][4]	=	rs1.getString("ope_visits");
					
					////outpatient (without emergency)
					tobBenefits[1][5]	=	rs1.getString("opw_limit");
					tobBenefits[1][6]	=	rs1.getString("opw_copay");
					tobBenefits[1][7]	=	rs1.getString("opw_visits");
					
					//Area of Coverage for I/P
					tobBenefits[1][8]	=	rs1.getString("mat_loc");
					
					//Hospital coverage
					tobBenefits[1][9]	=	rs1.getString("mat_pro");              
				
				}
				else if("IPT".equals(strBenefitType))
				{   
					if("CON".equals(rs1.getString("FLAG")) ||  "LAB".equals(rs1.getString("FLAG")) || "PATH".equals(rs1.getString("FLAG"))  ||"PHY".equals(rs1.getString("FLAG")) || "REGION".equals(rs1.getString("FLAG"))) 
					{
						tobBenefits[i][j]	    =	rs1.getString("COPAY_DED");

						tobBenefits[i][j+1]	=	rs1.getString("clin_con_cop");

						tobBenefits[i][j+2]	=	rs1.getString("LAB");

						tobBenefits[i][j+3]	=	rs1.getString("PATH");

						tobBenefits[i][j+4]	=	rs1.getString("total_sessions");

						tobBenefits[i][j+5]	=	rs1.getString("PHY_COPAY");

						tobBenefits[i][j+6]	=	rs1.getString("ava_session");

						tobBenefits[i][j+7]	=	rs1.getString("area_cover");
                        
						tobBenefits[i][j+8]	=	rs1.getString("pro_cover");
						
					}


				}	
				else if("OPTS".equals(strBenefitType))
				{    
					if("CON_HOS".equals(rs1.getString("FLAG")) ||  "CON_NON".equals(rs1.getString("FLAG")) || "LAB".equals(rs1.getString("FLAG"))  ||"PATH".equals(rs1.getString("FLAG")) ||"PHY".equals(rs1.getString("FLAG")) || "PRE".equals(rs1.getString("FLAG")) || "REGION".equals(rs1.getString("FLAG"))) 
					{
						tobBenefits[i][j]	    =	rs1.getString("COPAY_DED");

						tobBenefits[i][j+1]	=	rs1.getString("clin_con_cop");

						tobBenefits[i][j+2]	=	rs1.getString("LAB");

						tobBenefits[i][j+3]	=	rs1.getString("PATH");

						tobBenefits[i][j+4]	=	rs1.getString("total_sessions");

						tobBenefits[i][j+5]	=	rs1.getString("PHY_COPAY");

						tobBenefits[i][j+6]	=	rs1.getString("ava_session");
						
						tobBenefits[i][j+7]	=	rs1.getString("PRES_COPAY");
						
						
						tobBenefits[i][j+8]	=	rs1.getString("PRES_AVA_LIMIT");

						tobBenefits[i][j+9]	=	rs1.getString("area_cover");
						
						tobBenefits[i][j+10]	=	rs1.getString("pro_cover");

					}


				}	

				i++;



			}//end of while(rs1.next())
		}//end of if(rs1 != null)

		
	}// end of try
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "preauth");
	}// end of catch (Exception exp)
	finally {
		/* Nested Try Catch to ensure resource closure */
		try // First try closing the result set
		{
			try {
				if (rs1 != null)
					rs1.close();
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Resultset in PreAuthDAOImpl getPreauthHistoryList()",
						sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
							"Error while closing the Statement in PreAuthDAOImpl getPreauthHistoryList()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl getPreauthHistoryList()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
		}// end of try
		catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
		{
			rs1 = null;
			cStmtObject = null;
			conn = null;
		}// end of finally
	}// end of finally
	return tobBenefits;
}

public ArrayList<MOUDocumentVO>getPolicyDocsUploads(long policy_seq_id) throws TTKException {
	Connection conn = null;
	PreparedStatement pStmt1 = null;
	ArrayList<MOUDocumentVO>alMouList	=	new ArrayList<MOUDocumentVO>();
	ResultSet rs1 = null;
	MOUDocumentVO mouDocumentVO	=	null;
	try{

		
    	conn = ResourceManager.getConnection();
    	pStmt1=conn.prepareStatement(strGetPolicyDocs);
    	pStmt1.setLong(1, policy_seq_id);
		rs1= pStmt1.executeQuery();
		if(rs1 != null){
			while(rs1.next())
			{
				String filename=TTKCommon.checkNull(rs1.getString("FILE_NAME"));
				String fileTypes=TTKCommon.checkNull(rs1.getString("FILE_TYPE"));
				String fileDescription=TTKCommon.checkNull(rs1.getString("FILE_DESC"));
				if(filename!=null && fileTypes!=null && filename.length()>0&&fileTypes.length()>0)
				{
				if(filename.substring(0,filename.length()-1).contains("@"))
				{
					String[] files=filename.split("@");
					String[] fileType=fileTypes.split("@");
					for (int i = 0; i < files.length; i++) 
					{
						mouDocumentVO = new MOUDocumentVO();
						mouDocumentVO.setDescription(fileDescription);
						if(i<fileType.length)mouDocumentVO.setFileType(fileType[i]);
						else mouDocumentVO.setFileType("");
		                mouDocumentVO.setFileName(files[i]);
		                if(rs1.getString("ADDED_DATE") != null)
		                {
		                	mouDocumentVO.setDateTime(rs1.getString("ADDED_DATE"));
						}//end of if(rs.getString("ADDED_DATE") != null)
		                if(rs1.getBinaryStream("FILE_DOC")!=null)
		                {
                             mouDocumentVO.setFileData(rs1.getBinaryStream("FILE_DOC"));
                        } 
		                mouDocumentVO.setMouDocSeqID(rs1.getLong("PROP_DOC_SEQ_ID"));
		             		alMouList.add(mouDocumentVO);
					}
					
					
				}
				else if (filename.contains("@")) 
				{
					mouDocumentVO = new MOUDocumentVO();
					mouDocumentVO.setDescription(fileDescription);
				    mouDocumentVO.setFileType(fileTypes.substring(0,fileTypes.length()-1));
	                mouDocumentVO.setFileName(filename.substring(0,filename.length()-1));
	                if(rs1.getString("ADDED_DATE") != null)
	                {
	                	mouDocumentVO.setDateTime(rs1.getString("ADDED_DATE"));
					}//end of if(rs.getString("ADDED_DATE") != null)
	                if(rs1.getBinaryStream("FILE_DOC")!=null)
	                {
                         mouDocumentVO.setFileData(rs1.getBinaryStream("FILE_DOC"));
                    } 
	                mouDocumentVO.setMouDocSeqID(rs1.getLong("PROP_DOC_SEQ_ID"));
	               alMouList.add(mouDocumentVO);
				}
				else
				{
				mouDocumentVO = new MOUDocumentVO();
			    mouDocumentVO.setDescription(fileDescription);
				mouDocumentVO.setFileType(fileTypes);
                mouDocumentVO.setFileName(filename);
                if(rs1.getString("ADDED_DATE") != null)
                {
                	mouDocumentVO.setDateTime(rs1.getString("ADDED_DATE"));
				}//end of if(rs.getString("ADDED_DATE") != null)
                if(rs1.getBinaryStream("FILE_DOC")!=null)
                {
                     mouDocumentVO.setFileData(rs1.getBinaryStream("FILE_DOC"));
                } 
                mouDocumentVO.setMouDocSeqID(rs1.getLong("PROP_DOC_SEQ_ID"));
                if(rs1.getString("DELETED_DATE") != null)
                {
                	mouDocumentVO.setDelDateTime(rs1.getString("DELETED_DATE"));
                	
                }
                if(rs1.getString("DELETED_REMARKS") != null)
                {
                	mouDocumentVO.setDelRemarks(rs1.getString("DELETED_REMARKS"));
                }
                
                alMouList.add(mouDocumentVO);
				}
				}
                  
			}//end of while(rs.next())
		}//end of if(rs != null)
    	
		return alMouList;
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
				if (rs1 != null) rs1.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in PreAuthDAOImpl getPolicySIInfo()",sqlExp);
				throw new TTKException(sqlExp, "enrollment");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt1 != null) pStmt1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthDAOImpl getPolicySIInfo()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl getPolicySIInfo()",sqlExp);
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
			rs1 = null;
			pStmt1 = null;
			conn = null;
		}//end of finally
	}//end of finally
}//
public int deleteAuthorizationDetails(long claimSeqID) throws TTKException {
	int count=0;
	Connection conn = null;
	CallableStatement cStmtObject = null;
	try {
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strdeleteAuthorizationDetails);
		cStmtObject.setLong(1, claimSeqID);
		cStmtObject.registerOutParameter(2, Types.BIGINT);
		cStmtObject.execute();
		count = (int) cStmtObject.getLong(2);
		conn.commit();
	}// end of try
	catch (SQLException sqlExp) {
	throw new TTKException(sqlExp, "preauth");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
	throw new TTKException(exp, "preauth");
	}// end of catch (Exception exp)
	finally {
		/* Nested Try Catch to ensure resource closure */
	try // First try closing the Statement
	{
			try {
				if (cStmtObject != null)
					cStmtObject.close();
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Statement in PreAuthDAOImpl deleteAuthorizationDetails()",
						sqlExp);
			throw new TTKException(sqlExp, "preauth");
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
							"Error while closing the Connection in PreAuthDAOImpl deleteAuthorizationDetails()",
							sqlExp);
				throw new TTKException(sqlExp, "preauth");
				}// end of catch (SQLException sqlExp)
			}// end of finally Connection Close
		}// end of try
		catch (TTKException exp) {
		throw new TTKException(exp, "preauth");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
	{
		cStmtObject = null;
		conn = null;
		}// end of finally
	}// end of finally
return count;
}
public int ceedResponseDetails(String Mode,Long claimSeqID ,Long activitySeqId,String res) throws TTKException {
	int count=0;
	Connection conn = null;
	CallableStatement cStmtObject = null;
	try {
			log.info("Called ceedResponseDetails ");
		    conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(StrCeedResponseDetails);
			cStmtObject.setString(1, Mode);
			cStmtObject.setLong(2, claimSeqID);
			cStmtObject.setLong(3, activitySeqId);
			cStmtObject.setString(4, res);
		
		//cStmtObject.registerOutParameter(2, Types.BIGINT);
		cStmtObject.execute();
		conn.commit();
		//count = (int) cStmtObject.getLong(2);
	}// end of try
	catch (SQLException sqlExp) {
	throw new TTKException(sqlExp, "preauth");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
	throw new TTKException(exp, "preauth");
	}// end of catch (Exception exp)
	finally {
		/* Nested Try Catch to ensure resource closure */
	try // First try closing the Statement
	{
			try {
				if (cStmtObject != null)
					cStmtObject.close();
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Statement in PreAuthDAOImpl ceedResponseDetails()",
						sqlExp);
			throw new TTKException(sqlExp, "preauth");
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
							"Error while closing the Connection in PreAuthDAOImpl ceedResponseDetails()",
							sqlExp);
				throw new TTKException(sqlExp, "preauth");
				}// end of catch (SQLException sqlExp)
			}// end of finally Connection Close
		}// end of try
		catch (TTKException exp) {
		throw new TTKException(exp, "preauth");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
	{
		cStmtObject = null;
		conn = null;
		}// end of finally
	}// end of finally
return count;
}

public ArrayList<MOUDocumentVO> getPreauthDocsUploads(String preAuthSeqID) throws TTKException {
	Connection conn = null;
	PreparedStatement pStmt1 = null;
	ArrayList<MOUDocumentVO>alMouList	=	new ArrayList<MOUDocumentVO>();
	ResultSet rs1 = null;
	MOUDocumentVO mouDocumentVO	=	null;
	try{
    	conn = ResourceManager.getConnection();
    	pStmt1=conn.prepareStatement(strGetPreauthDocs);
    	pStmt1.setLong(1, Long.valueOf(preAuthSeqID));
		rs1= pStmt1.executeQuery();
		if(rs1 != null){
			while(rs1.next())
			{
				String filename=TTKCommon.checkNull(rs1.getString("file_name"));
				String fileDescription=TTKCommon.checkNull(rs1.getString("FILE_DESC"));
				if(filename!=null&& filename.length()>0)
					{
						if(filename.substring(0,filename.length()-1).contains("@"))
							{
								String[] files=filename.split("@");
								for (int i = 0; i < files.length; i++) 
								{
									mouDocumentVO = new MOUDocumentVO();
									mouDocumentVO.setDescription(fileDescription);						
								    mouDocumentVO.setFileName(files[i]);								
					                
					                if(rs1.getString("ADDED_DATE") != null)
					                {
					                	mouDocumentVO.setDateTime(rs1.getString("ADDED_DATE"));			
									}
					                mouDocumentVO.setUserId(TTKCommon.checkNull(rs1.getString("CONTACT_NAME")));	
								    mouDocumentVO.setMouDocSeqID(rs1.getLong("MOU_DOC_SEQ_ID"));
								    mouDocumentVO.setDeletedDateTime(TTKCommon.checkNull(rs1.getString("DELETED_DATE")));	
						    		mouDocumentVO.setDeletedRemarks(TTKCommon.checkNull(rs1.getString("DELETION_REMARKS")));	
									mouDocumentVO.setDeletedUserId(TTKCommon.checkNull(rs1.getString("DELETED_USER"))); 		
									mouDocumentVO.setPreauthFileName(rs1.getString("file_name"));
									mouDocumentVO.setHospitalSeqId(rs1.getLong("HOSP_SEQ_ID"));
									mouDocumentVO.setPreaithFileNameVal(rs1.getString("file_name_val")); 
									mouDocumentVO.setModeFlag(rs1.getString("MODE_FLAG"));
									alMouList.add(mouDocumentVO);
								}
							}
						else if (filename.contains("@")) 
						{
							mouDocumentVO = new MOUDocumentVO();
							mouDocumentVO.setDescription(fileDescription);						
							mouDocumentVO.setFileName(filename.substring(0,filename.length()-1));		
		               		                
		                if(rs1.getString("ADDED_DATE") != null)
		                {
		                	mouDocumentVO.setDateTime(rs1.getString("ADDED_DATE"));					
						}
		                mouDocumentVO.setUserId(TTKCommon.checkNull(rs1.getString("CONTACT_NAME")));	
		                mouDocumentVO.setDeletedDateTime(TTKCommon.checkNull(rs1.getString("DELETED_DATE")));	
			    		mouDocumentVO.setDeletedRemarks(TTKCommon.checkNull(rs1.getString("DELETION_REMARKS")));	
						mouDocumentVO.setDeletedUserId(TTKCommon.checkNull(rs1.getString("DELETED_USER"))); 		
						mouDocumentVO.setMouDocSeqID(rs1.getLong("MOU_DOC_SEQ_ID"));
					    mouDocumentVO.setPreauthFileName(rs1.getString("file_name"));		
						mouDocumentVO.setHospitalSeqId(rs1.getLong("HOSP_SEQ_ID"));
						mouDocumentVO.setPreaithFileNameVal(rs1.getString("file_name_val"));
						mouDocumentVO.setModeFlag(rs1.getString("MODE_FLAG"));
						alMouList.add(mouDocumentVO);
						}
						else
						{
					mouDocumentVO = new MOUDocumentVO();
				    mouDocumentVO.setDescription(fileDescription);
	                mouDocumentVO.setFileName(filename);
	                if(rs1.getString("ADDED_DATE") != null)
	                {
	                	mouDocumentVO.setDateTime(rs1.getString("ADDED_DATE"));					
					}
	                mouDocumentVO.setUserId(TTKCommon.checkNull(rs1.getString("CONTACT_NAME")));	
	                mouDocumentVO.setDeletedDateTime(TTKCommon.checkNull(rs1.getString("DELETED_DATE")));	
		    		mouDocumentVO.setDeletedRemarks(TTKCommon.checkNull(rs1.getString("DELETION_REMARKS")));	
					mouDocumentVO.setDeletedUserId(TTKCommon.checkNull(rs1.getString("DELETED_USER"))); 		  
					mouDocumentVO.setMouDocSeqID(rs1.getLong("MOU_DOC_SEQ_ID"));
				    mouDocumentVO.setPreauthFileName(rs1.getString("file_name"));	
					mouDocumentVO.setHospitalSeqId(rs1.getLong("HOSP_SEQ_ID"));
					mouDocumentVO.setPreaithFileNameVal(rs1.getString("file_name_val")); 
					mouDocumentVO.setModeFlag(rs1.getString("MODE_FLAG"));
					alMouList.add(mouDocumentVO);
					}
					}
                
			}//end of while(rs.next())
		}//end of if(rs != null)
    	
		return alMouList;
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
				if (rs1 != null) rs1.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in PreAuthDAOImpl getPreauthDocsUploads()",sqlExp);
				throw new TTKException(sqlExp, "preauth");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt1 != null) pStmt1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in PreAuthDAOImpl getPreauthDocsUploads()",sqlExp);
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
						log.error("Error while closing the Connection in PreAuthDAOImpl getPreauthDocsUploads()",sqlExp);
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
			rs1 = null;
			pStmt1 = null;
			conn = null;
		}//end of finally
	}//end of finally
}

public int savePreauthDocsUploads(ArrayList alFileAUploadList,Long userSeqId,String preAuthSeqID,String origFileName,InputStream inputStream,int formFileSize) throws TTKException {
	int iResult = 0;
	Connection conn = null;
	CallableStatement cStmtObject=null;
	try{
		conn = ResourceManager.getConnection();
        cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUploadPreauthDocs);

        cStmtObject.setLong(1,0);									
        cStmtObject.setLong(2, Long.valueOf(preAuthSeqID));						
        cStmtObject.setString(3,(String) alFileAUploadList.get(2));	
        cStmtObject.setString(4,origFileName);						
        cStmtObject.setString(5,origFileName);	
        cStmtObject.setLong(6,userSeqId);
        cStmtObject.setBinaryStream(7, inputStream,formFileSize);	
        cStmtObject.registerOutParameter(8,Types.INTEGER);//ROW_PROCESSED
        cStmtObject.execute();
        iResult  = cStmtObject.getInt(8);
        conn.commit();
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
    	// Nested Try Catch to ensure resource closure
    	try // First try closing the Statement
    	{
    		try
    		{
    			if (cStmtObject != null) cStmtObject.close();
    		}//end of try
    		catch (SQLException sqlExp)
    		{
    			log.error("Error while closing the Statement in PreAuthDAOImpl savePreauthDocsUploads()",sqlExp);
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
    				log.error("Error while closing the Connection in PreAuthDAOImpl savePreauthDocsUploads()",sqlExp);
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
}

public int delPreauthUploadedFile(String moudocseqid, String deleteRemarks, Long userSeqId) throws TTKException {
	Connection conn = null;
	CallableStatement cStmtObject=null;
	ResultSet rs=null;
	int result=0;
	try{
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDelPreauthUploadfile);
		cStmtObject.setString(1,moudocseqid); 
		cStmtObject.setLong(2,userSeqId);				
		cStmtObject.setString(3,deleteRemarks);			
		cStmtObject.registerOutParameter(4,Types.INTEGER);
        cStmtObject.execute();
        result = cStmtObject.getInt(4);
        conn.commit();
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
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the rs in PreAuthDAOImpl delPreauthUploadedFile()",sqlExp);
				throw new TTKException(sqlExp, "preauth");
			}//end of catch (SQLException sqlExp)
			finally // Even if rs is not closed, control reaches here. Try closing the connection now.
			{
			
			try
			{
				if (cStmtObject != null) cStmtObject.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Statement in PreAuthDAOImpl delPreauthUploadedFile()",sqlExp);
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
					log.error("Error while closing the Connection in PreAuthDAOImpl delPreauthUploadedFile()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
			}//end of finally Connection Close
			}// end of finally Statement Close
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
	return result;
}

 
public ArrayList<String[]> getProviderAllObservDetails(Long activityDtlSeqId)
		throws TTKException {
	Connection conn = null;
	CallableStatement cStmtObject = null;
	ResultSet ors = null;
	ArrayList<String[]> observations = new ArrayList<String[]>();

	try {
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn
				.prepareCall(strGetProviderAllObservations);
		cStmtObject.setLong(1, activityDtlSeqId);			
		cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
		cStmtObject.execute();
		ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ
															// Details
		if (ors != null) {
			while (ors.next()) 
			{
				String observSeqId = ors.getLong("OBSERVATION_SEQ_ID") == 0 ? "": ors.getLong("OBSERVATION_SEQ_ID") + "";
				String activityDtlSeqId1 = ors.getLong("ACTIVITY_DTL_SEQ_ID") == 0 ? "" : ors.getLong("ACTIVITY_DTL_SEQ_ID") + "";
				String observTypeDesc = ors.getString("TYPE_DESC") == null ? ""	: ors.getString("TYPE_DESC");
				String observCodeDesc = ors.getString("CODE_DESC") == null ? ""	: ors.getString("CODE_DESC");
				String observValue = ors.getString("VALUE") == null ? "": ors.getString("VALUE");
				String observValueTypeDesc = ors.getString("VALUE_TYPE_DESC") == null ? "" : ors.getString("VALUE_TYPE_DESC");
				String observRemarks = ors.getString("REMARKS") == null ? "": ors.getString("REMARKS");
				observations.add(new String[] { observSeqId,activityDtlSeqId1, observTypeDesc, observCodeDesc,observValue, observValueTypeDesc, observRemarks });
				
			}
		}

		return observations;
	}// end of try
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "preauth");
	}// end of catch (Exception exp)
	finally {
		/* Nested Try Catch to ensure resource closure */
		try // First try closing the result set
		{
			try {
				if (ors != null)
					ors.close();
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Resultset in PreAuthDAOImpl getProviderAllObservDetails()",
						sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
							"Error while closing the Statement in PreAuthDAOImpl getProviderAllObservDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl getProviderAllObservDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
		}// end of try
		catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
		{
			ors = null;
			cStmtObject = null;
			conn = null;
		}// end of finally
	}// end of finally
}

public Object[] saveProviderObserDetails(ObservationDetailsVO observationDetailsVO) throws TTKException {
	Connection conn = null;
	CallableStatement cStmtObject = null;
	ResultSet ors = null;
	ArrayList<String[]> observations = new ArrayList<String[]>();
	Object[] results = new Object[3];
	try {
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveProviderObservations);
		if (observationDetailsVO.getObservSeqId() == null)
			cStmtObject.setLong(1, 0);
		else
			cStmtObject.setLong(1, observationDetailsVO.getObservSeqId());
		cStmtObject.setLong(2, observationDetailsVO.getActivityDtlSeqId());
			cStmtObject.setLong(4, observationDetailsVO.getPreAuthSeqID());
		cStmtObject.setString(5, observationDetailsVO.getObservType());
		cStmtObject.setString(6, observationDetailsVO.getObservCode());
		cStmtObject.setString(7, observationDetailsVO.getObservValue());
		cStmtObject.setString(8, observationDetailsVO.getObservValueType());
		cStmtObject.setLong(9, observationDetailsVO.getAddedBy());
		cStmtObject.setString(10, observationDetailsVO.getObservRemarks());
		cStmtObject.registerOutParameter(1, Types.BIGINT);
		cStmtObject.registerOutParameter(11, Types.BIGINT);
		cStmtObject.execute();
		results[0] = cStmtObject.getLong(1);
		results[1] = cStmtObject.getLong(11);

		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetAllObservations);
		cStmtObject.setLong(1, observationDetailsVO.getActivityDtlSeqId());
		cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
		cStmtObject.execute();
		ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ
															// Details
		if (ors != null) 
		{
			while (ors.next()) 
			{
				String observSeqId = ors.getLong("OBSERVATION_SEQ_ID") == 0 ? "": ors.getLong("OBSERVATION_SEQ_ID") + "";
				String activityDtlSeqId = ors.getLong("ACTIVITY_DTL_SEQ_ID") == 0 ? "" : ors.getLong("ACTIVITY_DTL_SEQ_ID") + "";
				String observTypeDesc = ors.getString("TYPE_DESC") == null ? ""	: ors.getString("TYPE_DESC");
				String observCodeDesc = ors.getString("CODE_DESC") == null ? ""	: ors.getString("CODE_DESC");
				String observValue = ors.getString("VALUE") == null ? "": ors.getString("VALUE");
				String observValueTypeDesc = ors.getString("VALUE_TYPE_DESC") == null ? "" : ors.getString("VALUE_TYPE_DESC");
				String observRemarks = ors.getString("REMARKS") == null ? "": ors.getString("REMARKS");
				observations.add(new String[] { observSeqId,activityDtlSeqId, observTypeDesc, observCodeDesc,observValue, observValueTypeDesc, observRemarks });
			}
		}
		results[2] = observations;
		return results;
	}// end of try
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "preauth");
	}// end of catch (Exception exp)
	finally {
		/* Nested Try Catch to ensure resource closure */
		try // First try closing the result set
		{
			try {
				if (ors != null)
					ors.close();
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Resultset in PreAuthDAOImpl saveProviderObserDetails()",
						sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
							"Error while closing the Statement in PreAuthDAOImpl saveProviderObserDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl saveProviderObserDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
		}// end of try
		catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
		{
			ors = null;
			cStmtObject = null;
			conn = null;
		}// end of finally
	}// end of finally
}

public ArrayList<String[]> getAllProviderObservDetails(Long activityDtlSeqId1)throws TTKException 
{
	Connection conn = null;
	CallableStatement cStmtObject = null;
	ResultSet ors = null;
	ArrayList<String[]> observations = new ArrayList<String[]>();

	try {
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetAllProviderObservations);
		cStmtObject.setLong(1, activityDtlSeqId1);
		cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
		cStmtObject.execute();
		ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ
															// Details
		if (ors != null) 
		{
			while (ors.next()) 
			{
				String observSeqId = ors.getLong("OBSERVATION_SEQ_ID") == 0 ? "": ors.getLong("OBSERVATION_SEQ_ID") + "";
				String activityDtlSeqId = ors.getLong("ACTIVITY_DTL_SEQ_ID") == 0 ? "" : ors.getLong("ACTIVITY_DTL_SEQ_ID") + "";
				String observTypeDesc = ors.getString("TYPE_DESC") == null ? "": ors.getString("TYPE_DESC");
				String observCodeDesc = ors.getString("CODE_DESC") == null ? ""	: ors.getString("CODE_DESC");
				String observValue = ors.getString("VALUE") == null ? "": ors.getString("VALUE");
				String observValueTypeDesc = ors.getString("VALUE_TYPE_DESC") == null ? "" : ors.getString("VALUE_TYPE_DESC");
				String observRemarks = ors.getString("REMARKS") == null ? "": ors.getString("REMARKS");
				observations.add(new String[] { observSeqId,activityDtlSeqId, observTypeDesc, observCodeDesc,observValue, observValueTypeDesc, observRemarks });
			}
		}
		return observations;
	}// end of try
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "preauth");
	}// end of catch (Exception exp)
	finally {
		/* Nested Try Catch to ensure resource closure */
		try // First try closing the result set
		{
			try {
				if (ors != null)
					ors.close();
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Resultset in PreAuthDAOImpl getAllProviderObservDetails()",
						sqlExp);
				throw new TTKException(sqlExp, "preauth");
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
							"Error while closing the Statement in PreAuthDAOImpl getAllProviderObservDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl getAllProviderObservDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
		}// end of try
		catch (TTKException exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (TTKException exp)
		finally // Control will reach here in anycase set null to the
				// objects
		{
			ors = null;
			cStmtObject = null;
			conn = null;
		}// end of finally
	}// end of finally
}
	

public ObservationDetailsVO getProviderObservDetail(Long observSeqId)throws TTKException 
{
	Connection conn = null;
	CallableStatement cStmtObject = null;
	ResultSet ors = null;
	ObservationDetailsVO observationDetailsVO = new ObservationDetailsVO();
	try {
		conn = ResourceManager.getConnection();
		cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetProviderObservation);
		cStmtObject.setLong(1, observSeqId);
		cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
		cStmtObject.execute();
		ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ
															// Details
		if (ors != null) 
		{
			while (ors.next()) 
			{
				observationDetailsVO.setObservSeqId(ors.getLong("OBSERVATION_SEQ_ID"));
				observationDetailsVO.setActivityDtlSeqId(ors.getLong("ACTIVITY_DTL_SEQ_ID"));
				observationDetailsVO.setObservType(ors.getString("TYPE"));
				observationDetailsVO.setObservCode(ors.getString("CODE"));
				observationDetailsVO.setObservCodeDesc(ors.getString("CODE_DESC"));
				observationDetailsVO.setObservValue(ors.getString("VALUE"));
				observationDetailsVO.setObservValueType(ors.getString("VALUE_TYPE_ID"));
				observationDetailsVO.setObservValueTypeDesc(ors.getString("VALUE_TYPE_DESC"));
				observationDetailsVO.setObservRemarks(ors.getString("REMARKS"));
			}
		}
		return observationDetailsVO;
	}// end of try
	catch (SQLException sqlExp) {
		throw new TTKException(sqlExp, "preauth");
	}// end of catch (SQLException sqlExp)
	catch (Exception exp) {
		throw new TTKException(exp, "preauth");
	}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (ors != null)
						ors.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getProviderObservDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getProviderObservDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getProviderObservDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ors = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}


public Object[] getProviderObservTypeDetails(String observType) throws TTKException 
			{
			Connection conn = null;
			PreparedStatement statement = null;
			PreparedStatement statement2 = null;

			ResultSet resultSet = null;
			ResultSet resultSet2 = null;
			HashMap<String, String> observCodes = new HashMap<String, String>();
			HashMap<String, String> observValueTypes = new HashMap<String, String>();
			Object[] result = new Object[2];
			try {
				observType = (observType == null) ? "" : observType.trim();
			    conn = ResourceManager.getConnection();
				statement = conn.prepareStatement("SELECT S.OBSERVATION_CODE_ID,S.OBSERVATION_CODE FROM APP.TPA_OBSERVATION_VALUE_CODES S where s.observation_type_id='"+observType+"'");
				resultSet = statement.executeQuery();
				if (resultSet != null) 
				{
					while (resultSet.next())
						observCodes.put(resultSet.getString(1),resultSet.getString(2));
			    }
				statement2 = conn.prepareStatement("select C.OBS_VALUE_TYPE_CODE_ID,C.VALUE_TYPE from app.TPA_OBSER_VALUE_TYPE_CODES C WHERE C.OBSERVATION_TYPE_ID='"+observType+"'");
				resultSet2 = statement2.executeQuery();
				if (resultSet2 != null) 
				{
					while (resultSet2.next())
						observValueTypes.put(resultSet2.getString(1),resultSet2.getString(2));
				}
				result[0] = observCodes;
				result[1] = observValueTypes;
				return result;
			}// end of try
			catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "preauth");
			}// end of catch (SQLException sqlExp)
			catch (Exception exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (Exception exp)
			finally {
				/* Nested Try Catch to ensure resource closure */
				try // First try closing the result set
				{
					try {
						if (resultSet != null)
							resultSet.close();
						try{
						if (resultSet2 != null)
							resultSet2.close();
						}catch (SQLException sqlExp) {
							log.error("Error while closing the Resultset in PreAuthDAOImpl getProviderObservTypeDetails()",sqlExp);
						throw new TTKException(sqlExp, "preauth");
						}finally // Even if result set is not closed, control reaches
					{
						try {
							if (statement != null)
								statement.close();
							if (statement2 != null)
							statement2.close();

						
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Statement in PreAuthDAOImpl getProviderObservTypeDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
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
										"Error while closing the Connection in PreAuthDAOImpl getProviderObservTypeDetails()",
										sqlExp);
								throw new TTKException(sqlExp, "preauth");
							}// end of catch (SQLException sqlExp)
						}// end of finally Connection Close
					}// end of finally Statement Close
				}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Resultset in PreAuthDAOImpl getProviderObservTypeDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
					
				}// end of try
				catch (TTKException exp) {
					throw new TTKException(exp, "preauth");
				}// end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the
						// objects
				{
					resultSet = null;
					resultSet2 = null;
					statement = null;
					statement2 = null;


					conn = null;
				}// end of finally
			}// end of finally
	}
	
	public ObservationDetailsVO getProviderObsTypeDetails(Long observSeqId)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet ors = null;
		ObservationDetailsVO observationDetailsVO = new ObservationDetailsVO();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strGetObservation);
			cStmtObject.setLong(1, observSeqId);
			cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
			cStmtObject.execute();
			ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ
																// Details

			if (ors != null) {
				while (ors.next()) {
					observationDetailsVO.setObservSeqId(ors
							.getLong("OBSERVATION_SEQ_ID"));
					observationDetailsVO.setActivityDtlSeqId(ors
							.getLong("ACTIVITY_DTL_SEQ_ID"));
				observationDetailsVO.setObservType(ors.getString("TYPE"));
				observationDetailsVO.setObservCode(ors.getString("CODE"));
					observationDetailsVO.setObservCodeDesc(ors
							.getString("CODE_DESC"));
				observationDetailsVO.setObservValue(ors.getString("VALUE"));
					observationDetailsVO.setObservValueType(ors
							.getString("VALUE_TYPE_ID"));
					observationDetailsVO.setObservValueTypeDesc(ors
							.getString("VALUE_TYPE_DESC"));
					observationDetailsVO.setObservRemarks(ors
							.getString("REMARKS"));
			}
			}

			return observationDetailsVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (ors != null)
						ors.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getProviderObsTypeDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getProviderObsTypeDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getProviderObsTypeDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ors = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getObservDetails(long lngPreAuthSeqID,long lngUserSeqID,String
		// strSelectionType)
	
	
	public ObservationDetailsVO getProObservDetails(Long observSeqId)throws TTKException 
	{
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet ors = null;
		ObservationDetailsVO observationDetailsVO = new ObservationDetailsVO();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetProviderObserv);
			cStmtObject.setLong(1, observSeqId);
			cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
			cStmtObject.execute();
			ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ
																// Details

			if (ors != null) {
				while (ors.next()) {
					observationDetailsVO.setObservSeqId(ors
							.getLong("OBSERVATION_SEQ_ID"));
					observationDetailsVO.setActivityDtlSeqId(ors
							.getLong("ACTIVITY_DTL_SEQ_ID"));
				observationDetailsVO.setObservType(ors.getString("TYPE"));
				observationDetailsVO.setObservCode(ors.getString("CODE"));
					observationDetailsVO.setObservCodeDesc(ors
							.getString("CODE_DESC"));
				observationDetailsVO.setObservValue(ors.getString("VALUE"));
					observationDetailsVO.setObservValueType(ors
							.getString("VALUE_TYPE_ID"));
					observationDetailsVO.setObservValueTypeDesc(ors
							.getString("VALUE_TYPE_DESC"));
					observationDetailsVO.setObservRemarks(ors
							.getString("REMARKS"));
			}
			}

			return observationDetailsVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (ors != null)
						ors.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl getProObservDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl getProObservDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl getProObservDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ors = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}
	
	public Object[] saveProviderObservationDetails(	ObservationDetailsVO observationDetailsVO) throws TTKException 
	{
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet ors = null;
		ArrayList<String[]> observations = new ArrayList<String[]>();
		Object[] results = new Object[3];
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSaveProvoderObservations);
			if (observationDetailsVO.getObservSeqId() == null)
				cStmtObject.setLong(1, 0);
			else
				cStmtObject.setLong(1, 	observationDetailsVO.getObservSeqId());
				cStmtObject.setLong(2, 	observationDetailsVO.getActivityDtlSeqId());
				cStmtObject.setLong(3, 	observationDetailsVO.getPreAuthSeqID());
				cStmtObject.setString(4, observationDetailsVO.getObservType());
				cStmtObject.setString(5, observationDetailsVO.getObservCode());
				cStmtObject.setString(6, observationDetailsVO.getObservValue());
				cStmtObject.setString(7, observationDetailsVO.getObservValueType());
				cStmtObject.setLong(8, 	observationDetailsVO.getAddedBy());
				cStmtObject.setString(9, observationDetailsVO.getObservRemarks());
				cStmtObject.registerOutParameter(1, Types.BIGINT);
				cStmtObject.registerOutParameter(10, Types.BIGINT);
				cStmtObject.execute();
				results[0] = cStmtObject.getLong(1);
				results[1] = cStmtObject.getLong(10);

				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetProviderAllObservations);
				cStmtObject.setLong(1, observationDetailsVO.getActivityDtlSeqId());
				cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
				cStmtObject.execute();
				ors = (java.sql.ResultSet) cStmtObject.getObject(2);// observ
																// Details

			if (ors != null) {
				while (ors.next()) {
					String observSeqId = ors.getLong("OBSERVATION_SEQ_ID") == 0 ? "": ors.getLong("OBSERVATION_SEQ_ID") + "";
					String activityDtlSeqId = ors.getLong("ACTIVITY_DTL_SEQ_ID") == 0 ? "" : ors.getLong("ACTIVITY_DTL_SEQ_ID") + "";    
				//	String activityDtlSeqId = "114061";
					String observTypeDesc = ors.getString("TYPE_DESC") == null ? ""	: ors.getString("TYPE_DESC");
					String observCodeDesc = ors.getString("CODE_DESC") == null ? ""	: ors.getString("CODE_DESC");
					String observValue = ors.getString("VALUE") == null ? ""		: ors.getString("VALUE");
					String observValueTypeDesc = ors.getString("VALUE_TYPE_DESC") == null ? "" : ors.getString("VALUE_TYPE_DESC");
					String observRemarks = ors.getString("REMARKS") == null ? "": ors.getString("REMARKS");
					
					observations.add(new String[] { observSeqId,activityDtlSeqId, observTypeDesc, observCodeDesc,
							observValue, observValueTypeDesc, observRemarks });
			}
			}
			results[2] = observations;
			return results;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (ors != null)
						ors.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in PreAuthDAOImpl saveProviderObservationDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Statement in PreAuthDAOImpl saveProviderObservationDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "preauth");
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
									"Error while closing the Connection in PreAuthDAOImpl saveProviderObservationDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "preauth");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				ors = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}
	
	public long deleteProObservDetails(Long peauthSeqId, String listOfobsvrSeqIds) throws TTKException {
			Connection conn = null;
		CallableStatement cStmtObject = null;
			long updatedRows;
		try {
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strDelProviderObservations);
				cStmtObject.setString(1, listOfobsvrSeqIds);
				cStmtObject.setLong(2, peauthSeqId);
				cStmtObject.registerOutParameter(3, Types.BIGINT);
				cStmtObject.execute();
				updatedRows = cStmtObject.getLong(3);

				return updatedRows;
		}// end of try
		catch (SQLException sqlExp) {
				throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
				throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
				try // First try closing the result set
				{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl deleteProObservDetails()",
							sqlExp);
							throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl deleteProObservDetails()",
								sqlExp);
								throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
				{
					cStmtObject = null;
					conn = null;
			}// end of finally
		}// end of finally
	}
	
	
	public long getPreAuthSeqID(String authNumber) throws TTKException 
	{
		long preAuthSeqID=0;
		Connection conn = null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		conn=ResourceManager.getConnection();
		try {
		    	conn=ResourceManager.getConnection();
		    	statement=conn.prepareStatement(strGetPreauthSeqId);		
		    	statement.setString(1,authNumber);
		    	resultSet=statement.executeQuery();
		    	while(resultSet.next())
		    	{
		    		preAuthSeqID = resultSet.getLong("pat_auth_seq_id");
		    	}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{
				if(resultSet!=null)resultSet.close();
				if(statement!=null)statement.close();
			}catch(SQLException sqlExc){
				sqlExc.printStackTrace();
			}
			
			try{
				if(conn!=null)conn.close();				
			}catch(SQLException sqlExc){
				sqlExc.printStackTrace();
			}
		}
		return preAuthSeqID;
	}	
	
	public Object[] getEpreauthDocs(Long preAuthSeqID,Long activityDtlSeqId) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		Document doc = null;
		String  act_id = "";
		Object[] results = new Object[2];
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strGetEpreauthDocs);		
			
			cStmtObject.setLong(1, preAuthSeqID);
			cStmtObject.setLong(2,activityDtlSeqId);
			cStmtObject.registerOutParameter(3, Types.SQLXML);	// db passing as xml
			cStmtObject.registerOutParameter(4, Types.VARCHAR);
			
			cStmtObject.execute();
			
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(cStmtObject.getSQLXML(3).getCharacterStream());
			String activityid = cStmtObject.getString(4);
			activityid = (activityid==null||"".equals(activityid))?"0":activityid;
			
			results[0] = activityid;
			results[1] = doc;
			
			return results;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "preauth");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "preauth");
		}// end of catch (Exception exp)

		finally {
			/* Nested Try Catch to ensure resource closure */
        	try // First try closing the Statement
        	{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in PreAuthDAOImpl getEpreauthDocs()",
							sqlExp);
        			throw new TTKException(sqlExp, "preauth");
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
								"Error while closing the Connection in PreAuthDAOImpl getEpreauthDocs()",
								sqlExp);
        				throw new TTKException(sqlExp, "preauth");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "preauth");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
	}
	
	public ArrayList<Object> getDenialDescriptionsList(String payerAuthority) throws TTKException
{
	Connection conn = null;
	PreparedStatement pStmt 		= 	null;
	ResultSet rs1=null;
	CacheObject cacheObject = null;
    ArrayList<CacheObject> denailDesc = new ArrayList<CacheObject>();
    ArrayList<Object> allList = new ArrayList<Object>();
	try {
		 conn = ResourceManager.getConnection();
		 pStmt = conn.prepareStatement(str_DenialDescriptionsList);
		 if("MOH".equals(payerAuthority) || "DHA".equals(payerAuthority))
			 pStmt.setString(1,"DHA");
		 else
			pStmt.setString(1,payerAuthority);
			  rs1 = pStmt.executeQuery();
			
		if(rs1!=null){
			while(rs1.next()){
				cacheObject = new CacheObject();
				cacheObject.setCacheId((rs1.getString("DENIAL_CODE")));
                cacheObject.setCacheDesc(TTKCommon.checkNull(rs1.getString("DENIAL_DESCRIPTION")));
                denailDesc.add(cacheObject);
			}
		}
		allList.add(denailDesc);
		return allList;
	} 
	 catch (SQLException sqlExp)
     {
         throw new TTKException(sqlExp, "preauth");
     }
	
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
					if (rs1 != null) rs1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in PreauthDAOImpl getDenialDescriptionsList()",sqlExp);
					throw new TTKException(sqlExp, "preauth");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PreauthDAOImpl getDenialDescriptionsList()",sqlExp);
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
							log.error("Error while closing the Connection in PreauthDAOImpl getDenialDescriptionsList()",sqlExp);
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
				rs1 = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
}
	
	
}//end of PreAuthDAOImpl