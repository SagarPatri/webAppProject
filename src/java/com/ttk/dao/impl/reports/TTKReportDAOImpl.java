/**
 * @ (#) TTKReportDAO.java June 28, 2006
 * Project       : TTK HealthCare Services
 * File          : TTKReportDAO.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : June 28, 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.reports;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;
import oracle.jdbc.rowset.OracleCachedRowSet;
import oracle.xdb.XMLType;

import org.apache.log4j.Logger;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.sun.rowset.CachedRowSetImpl;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.ReportVO;
import com.ttk.dto.preauth.ProviderDetailsVO;

public class TTKReportDAOImpl implements BaseDAO, Serializable {
	
	private static Logger log = Logger.getLogger(TTKReportDAOImpl.class);
	private static String strAppRejePreAuthsList="CLAIMS_APPROVAL_PKG.GEN_PAT_APR_REJ_RPT";//1274A
	private static String strAppRejeClaimsList="CLAIMS_APPROVAL_PKG.GEN_CLM_APR_REJ_RPT";//1274A
//Added as per Hospital Login
	private static String strTAKAFUL_LETTER="PAT_CLM_REPORTS_PKG_TAKAFUL_PO_LETTER";
	
	private static String strPRICING_SUMMARY="app.PRI_TOOL_PKG_SELECT_PRICING_SUMMARY";//
	
	private static String strHospitalBillsPend="HOSPITAL_PKG.BILLS_PENDING_RPT";
	private static String strHospitalClmRegd="HOSPITAL_PKG.CLAIMS_DETAILED_RPT";
	private static String strHospitalOnlineSummaryRptClaims="HOSPITAL_PKG.CLAIMS_SUMMARY_RPT";
			
	private static String strInsurancePreAuthGenerateLetter="CLAIMS_APPROVAL_PKG.PAT_GENERATE_LETTER";
    private static String strPatGenPolicyHistory="CLAIMS_APPROVAL_PKG.PAT_GEN_POLICY_HIST";
    private static String strPatPreAuthHistory="CLAIMS_APPROVAL_PKG.PAT_PREV_HISTORY";
    private static String strPatBillDetails="CLAIMS_APPROVAL_PKG.PAT_BILL_DETAILS";
 
    private static String strInsClaimsGenerateLetter="CLAIMS_APPROVAL_PKG.CLM_GENERATE_LETTER";
    private static String  strClaimsPolicyHistory="CLAIMS_APPROVAL_PKG.CLM_GEN_POLICY_HIST";
    private static String strPrevClaimsHistory="CLAIMS_APPROVAL_PKG.CLM_PREV_HISTORY";
    private static String strClaimsBillHistory="CLAIMS_APPROVAL_PKG.CLM_BILL_DETAILS";
    private static String strClaimsIntimateSend="CLAIMS_APPROVAL_PKG.CLAIMS_INTIMATE_SEND_IAR";
    private static String strPreAuthIntimateSend="CLAIMS_APPROVAL_PKG.PAT_INTIMATE_SEND_IAR";
   // claims_intimate_send_ar
    
    //Mail-SMS for Cigna
    private static final String strCLAIM_CLOSURE_LTR_ADVISOR="PRE_CLM_REPORTS_PKG.CIGNA_CLOSURE_REMINDER"; 
    //added for mail-sms template for cign
    private static final String strCLAIM_SUB_APP_LTR_ADV = "PRE_CLM_REPORTS_PKG.cigna_get_bill_dtls";
        
    private static final String strCLAIM_REJECT_LTR_PO="PRE_CLM_REPORTS_PKG.CIGNA_CLAIM_REJECTION";
    private static final String strCLAIM_APP_LTR_ADV="PRE_CLM_REPORTS_PKG.Claim_settlement_ltr_Advisor";
    private static final String strCLAIM_SETTLEMENT_LTR_PO="PRE_CLM_REPORTS_PKG.cigna_claim_settlement";
    
   
    //Enrollment Reports
    private static String strPR_HOSP_DOC_RCVD_REPORT="HOSPITAL_EMPANEL_REPORTS_PKG_PR_HOSP_DOC_RCVD_REPORT";
    private static String strIMAGE_NOT_UPLOADED="ENROLLMENT_REPORTS_PKG.IMAGE_NOT_UPLOADED";
    private static String strPOLICIES_AWAITING_WORKFLOW="ENROLLMENT_REPORTS_PKG.POLICIES_AWAITING_WORKFLOW";
    private static String strCARDS_PRINTED="ENROLLMENT_REPORTS_PKG.CARDS_PRINTED";
    private static String strCARDS_NOT_DELIVERED="ENROLLMENT_REPORTS_PKG.CARDS_NOT_DELIVERED";
    private static String strGROUPS_RENEWAL="ENROLLMENT_REPORTS_PKG.GROUPS_RENEWAL";
    private static String strTIMETAKEN_TO_RECEIVE_POLICY="ENROLLMENT_REPORTS_PKG.TIMETAKEN_TO_RECEIVE_POLICY";
    private static String strDISPATCH_REPORT="ENROLLMENT_REPORTS_PKG.DISPATCH_REPORT";
    private static String strENDORSEMENTS="ENROLLMENT_REPORTS_PKG.ENDORSEMENTS";
    private static String strTIMETAKEN_TO_PROCESS_POLICY="ENROLLMENT_REPORTS_PKG.TIMETAKEN_TO_PROCESS_POLICY";
    private static String strDISPATCH_LIST="ENROLLMENT_REPORTS_PKG.DISPATCH_LIST";
    private static String strBOI_REPORT="ENROLLMENT_REPORTS_PKG.BOI_DETAIL_REPORT";
    private static String strIMPACT_REPORT = "ENDORSEMENT_REPORTS_PKG.ENDORSEMENT_IMPACT_REPORT"; 
    private static String strPREMIUM_CHANGE_REPORT = "ENDORSEMENT_REPORTS_PKG.PREMIUM_CHANGE_REPORT";
    private static String strEFT_COV_LETTER_ANNEXURE = "BATCH_REPORT_PKG.EFT_COV_LETTER_ANNEXURE";
    private static String strEFT_COVERING_LETTER = "BATCH_REPORT_PKG.EFT_COVERING_LETTER";
    private static String strGET_MEMBERS_ADDED_LIST = "ENROLLMENT_REPORTS_PKG.GET_MEMBERS_ADDED_LIST";
    private static String StrSOFTCOPYUPLOAD_ERROR_DETAILS="ENROLLMENT_REPORTS_PKG.SELECT_CORP_BATCH_ERROR_LOG";
    //Added for IBM.....9
    private static String strDELETION_CUTOFF ="APP.ENROLLMENT_REPORTS_PKG.DELETION_CUT_OFF_REPORT";//sri Krishna
    private static String strADDITION_CUTOFF="APP.ENROLLMENT_REPORTS_PKG.ADDITION_CUT_OFF_REPORT";
    private static String strNEW_IBM_DELETION_CUTOFF="APP.ENROLLMENT_REPORTS_PKG.IBM_DELETION_CUTOFF_NEW";
    //Ended.
    private static String strGENERATE_RENEWAL_MEMBERS="ADMINISTRATION_PKG_GENERATE_RENEWAL_MEMBERS";
    private static String strPREAUTH_LETTER_FORMAT="PAT_CLM_REPORTS_PKG_PRE_AUTH_GENERATE_LETTER";
    private static String strCLAIM_LETTER_FORMAT="PAT_CLM_REPORTS_PKG_CLAIM_COMPUTATION";
    private static String strDIAGNOSIS_DETAILS="PAT_CLM_REPORTS_PKG_GET_PAT_DIAGNOSIS";
    private static String strACTIVITY_DETAILS="PAT_CLM_REPORTS_PKG_GET_PAT_ACTIVITY";
    //SoftCopy Upload Reprots
    private static String strERROR_DETAILS="ENROLLMENT_REPORTS_PKG.ERROR_DETAILS";

    //Pre-auth Reports
    private static String strPRE_AUTH_RPT="PRE_CLM_REPORTS_PKG.PRE_AUTH_RPT ";
    private static String strMANUAL_PREATH_RPT="PRE_AUTH_PKG.MANUAL_PREATH_RPT";
    private static String strSHORTFALL_GENERATE_LETTER_preauth = "PAT_CLM_REPORTS_PKG_SHORTFALL_GENERATE_LETTER";
    private static String strPreAuthClmDtlRpt ="PRE_CLM_REPORTS_PKG.SELECT_PAT_CLM_REPORT";
    
    //Claims Report
    private static String stCLAIM_INTIMATIN_RPT="call_center_pkg_p_ins_sms_report";//KOC 1339 for mail
    private static String strMANUAL_CLAIM_RPT="CLAIMS_PKG.MANUAL_CLAIM_RPT";
    private static String strPROCESS_TIME_ANALYSIS="PRE_CLM_REPORTS_PKG.PROCESS_TIME_DETAIL";
    private static String strCLOSURE_LETTER="PRE_CLM_REPORTS_PKG.CLOSURE_LETTER";
    private static String strSHORTFALL_GENERATE_LETTER_claims = "PRE_CLM_REPORTS_PKG_SHORTFALL_LETTER_INFO";
    private static String strClaims_OUTSTANDING_LIABILITY = "PRE_CLM_REPORTS_PKG.CLM_DETAIL_RPT";
    private static String strRejectionLetter="PRE_CLM_REPORTS_PKG.REJECTION_LETTER_DATA";
    private static String strMedicalOpinionSheet="PRE_CLM_REPORTS_PKG.MEDICAL_OPINION_DATA";
	 // Shortfall CR KOC1179
    private static String strSHORTFALL_GENERATE_SHORTFALL_LETTER_CLAIMS = "PRE_CLM_REPORTS_PKG.GENERATE_SHORTFALL_LETTER";
    // Shortfall CR KOC1179
    
  //added for Mail-SMS for Cigna
    private static String strCIGNA_SHORTFALL_GENERATE_LETTER_claims = "PRE_CLM_REPORTS_PKG.CIGNA_SHORTFALL_REPORT";    
  //ADDED FOR Mail-SMS Template for Cigna
    private static String strGENERATE_CIGNA_SHORTFALL_SCHEDULER_REQUEST = "PRE_CLM_REPORTS_PKG.gen_cigna_shrtfal_req_ltr";
  //Added for Mail-SMS for Cigna
    private static String strCIGNA_SHORTFALL_GENERATE_SHORTFALL_REQUEST_LETTER_CLAIMS = "PRE_CLM_REPORTS_PKG.gen_cigna_shrtfal_req_ltr1"; 

    //ADDED FOR CIGNA CLAIMHISTORY REPORT
    private static String strCIGNA_SHORTFALL_CLAIM_HISTORY = "PRE_CLM_REPORTS_PKG.CIGNA_CLM_SHRTFL_HISTORY";
    //ENDED
  //added for Mail-SMS 
    private static String strCIGNA_SUBREPORT_SHORTFALL_GENERATE_SHORTFALL_REQUEST_LETTER_CLAIMS = "PRE_CLM_REPORTS_PKG_GET_SHORTFAL_DOCS";
  		
    
    private static String strSHORTFALL_GENERATE_SHORTFALL_REQUEST_LETTER_CLAIMS="PRE_CLM_REPORTS_PKG_GENERATE_SHRTFALL_REQUEST_LTR";
        
    // Shortfall CR KOC1179 Claim Shortfall Summary Report
    private static String strCLAIMS_SHORTFALL_SUMMARY_REPORT="PRE_CLM_REPORTS_PKG_GET_SHORTFAL_REPORT";
	 //Shortfall CR KOC1179
    private static String strGENERATE_SHORTFALL_SCHEDULER_REQUEST="PRE_CLM_REPORTS_PKG_GENERATE_SHRTFALL_REQUEST_LTR";
    

    //Finance Reprots
    private static String strFinanceBatch="BATCH_REPORT_PKG_BATCH_REPORT_LIST";
    private static String strCOVERING_LETTER="BATCH_REPORT_PKG_COVERING_LETTER";
    private static String strRECONCILATION_REPORT_TEST="BATCH_REPORT_PKG.RECONCILATION_REPORT";
    private static String strBANK_TRANSACTION_REPORT="BATCH_REPORT_PKG_BANK_TRANSACTION_REPORT";
    private static String strFLOAT_TRANSACTION_REPORT="BATCH_REPORT_PKG_FLOAT_TRANSACTION_REPORT";
    private static String strCLAIMS_DETAILS_REPORT="BATCH_REPORT_PKG_CLAIMS_DETAILS_REPORT";
    private static String strCITI_CLAIMS_DETAILS_REPORT="BATCH_REPORT_PKG_CITI_CLAIMS_DETAILS_REPORT";
    
    private static String strEFT_CLAIMS_DETAILS_REPORT="BATCH_REPORT_PKG.EFT_CLAIMS_DETAILS_REPORT";
    private static String strEFT_CLAIMS_PENDING_REPORT="BATCH_REPORT_PKG.EFT_CLAIMS_PENDING_REPORT";    
    private static String strMANUAL_BATCH_LIST ="fin_app.FLOAT_ACCOUNTS_PKG_MANUAL_BATCH_LIST";
    private static String strFLOAT_STATEMENT_REPORT ="BATCH_REPORT_PKG.FLOAT_STATEMENT_REPORT";
    private static String strCLAIMS_PENDING_REPORT ="fin_app.BATCH_REPORT_PKG_CLAIMS_PENDING_REPORT";
	 //Changes added for Debit Note CR KOC1163
  //  private static String strDEBITNOTE_IBM_DAKSH_REPORT ="intx.BATCH_REPORT_PKG_DEBIT_NOTE_FOR_IBM_DAKSH";
    // End changes added for Debit Note CR KOC1163
    private static String strTPA_COMMISSION_REPORT ="BATCH_REPORT_PKG_TPA_COMMISSION_REPORT";
  //  private static String strSEVENTY_SIX_COLUMN_REPORT ="intx.BATCH_REPORT_PKG_GET_76_COL_RPT_DATA";
    //private static String strTPA_COMISSION_REPORT_SHEETS="BATCH_REPORT_PKG.TPA_COMMISSION";
    private static String strTPA_COMISSION_REPORT_SHEETS="FLOAT_ACCOUNTS_PKG_TPA_COMMISSION";
    private static String strFUT_GEN_CLAIMS_DETAILS_REPORT="BATCH_REPORT_PKG_FUT_GEN_CLAIMS_DETAILS_REPORT";
    private static String strFIN_CLAIM_COMPUTATION="fin_app.BATCH_REPORT_PKG_CLAIM_COMPUTATION";
    private static String strFIN_CLAIM_COMPUTATION_LINEITEMS="fin_app.BATCH_REPORT_PKG_CLAIM_COMPUTATION_LINEITEMS";
    private static String strFIN_SELECT_PENDING_DREMIT_RPT="TDS_PKG_SELECT_PENDING_DREMIT_RPT";
    private static String strFIN_SELECT_SETTLED_DREMIT_RPT="TDS_PKG_SELECT_SETTLED_DREMIT_RPT";

    
    //Coding Reports
    private static String strPendingCases_Report="CODING_PKG_PENDING_CASE_REPORT";
    private static String strCodeCleanupStatusReport="CODE_CLEANUP_PKG_CODE_CLEANUP_STATUS_RPT";

    //Call Center Reports
    private static String strCARD_HISTORY="PRE_CLM_REPORTS_PKG_CARD_HISTORY";
    private static String strPRE_AUTH_HISTORY ="PRE_CLM_REPORTS_PKG_PRE_AUTH_HISTORY";
    private static String strCLAIM_HISTORY ="PRE_CLM_REPORTS_PKG_CLAIM_HISTORY";
    private static String strPOLICY_HISTORY ="PRE_CLM_REPORTS_PKG_POLICY_HISTORY";
    private static String strPOLICY_HISTORY_SUB="PRE_CLM_REPORTS_PKG_POLICY_SUMINSURED_BREAKUP_RPT";
    private static String strCALL_HISTORY_DETAIL ="PRE_CLM_REPORTS_PKG_CALL_HISTORY_DETAIL";
    private static String strCALL_HISTORY_LIST ="PRE_CLM_REPORTS_PKG_CALL_HISTORY_LIST";
    private static String strCALL_HISTORY_ASSOCIATE_DETAIL ="PRE_CLM_REPORTS_PKG_CALL_HISTORY_ASSOCIATE_DETAIL";
    private static String strDISALLOWED_BILL_LIST="PRE_CLM_REPORTS_PKG_CLM_DISALLOWED_AMT_RPT"; 
    private static String strCLAIM_NOTIFICATIONS_DETAIL ="PRE_CLM_REPORTS_PKG_CLAIM_NOTIFICATIONS_DETAIL";
    private static String strENDORSEMENT_LIST = "ACCOUNT_INFO_PKG_SELECT_ENDORSEMENT_LIST";
    private static String strGET_REJECTION_INFO = "ONLINE_ENROLMENT_PKG_GET_REJECTION_INFO";

    private static String strCOURIER_DETAILS="PRE_CLM_REPORTS_PKG_COURIER_DETAILS";
    private static String strPRINT_CHEQUE = "FLOAT_ACCOUNTS_PKG_PRINT_CHECK";
    
    private static String strPRE_AUTH_GENERATE_LETTER ="PRE_CLM_REPORTS_PKG_PRE_AUTH_GENERATE_LETTER";
    private static String strVOUCHER_LIST ="PRE_CLM_REPORTS_PKG_VOUCHER_LIST";
    private static String strCLAIM_COMPUTATION ="PRE_CLM_REPORTS_PKG_CLAIM_COMPUTATION";
    private static String strCLAIM_COMPUTATION_LINEITEMS ="PRE_CLM_REPORTS_PKG_CLAIM_COMPUTATION_LINEITEMS";
    private static String strPRINT_ACKNOWLEDGEMENT ="PRE_CLM_REPORTS_PKG_PRINT_ACKNOWLEDGEMENT";
    private static String strPRINT_ACK_CHECK_LIST ="PRE_CLM_REPORTS_PKG_PRINT_ACK_CHECK_LIST";
    private static String strPRE_AUTH_CITI_GENERATE_LETTER = "PRE_CLM_REPORTS_PKG_PRE_AUTH_CITI_GENERATE_LETTER";
    
    //private static String strCALLCENTERCARDPRINT ="PRE_CLM_REPORTS_PKG.CALLCENTERCARDPRINT";

    private static final String strGENERATE_CARD_LIST = "PRE_CLM_REPORTS_PKG.GENERATE_CARD_LIST";
    private static String strPRE_AUTH_HIST_SHORTFALL_LIST = "PRE_CLM_REPORTS_PKG.PRE_AUTH_HIST_SHORTFALL_LIST";
    private static String strPRE_AUTH_HISTORY_QUESTIONS = "PRE_CLM_REPORTS_PKG_PRE_AUTH_HISTORY_QUESTIONS";
    private static String strPRE_AUTH_HISTORY_INVEST_LIST = "PRE_CLM_REPORTS_PKG.PRE_AUTH_HIST_INVEST_LIST";
    private static String strINVESTIGATION_DETAIL = "PRE_CLM_REPORTS_PKG.INVESTIGATION_DETAIL";
    private static String strPRE_CLM_HIST_BUFFER_LIST = "PRE_CLM_REPORTS_PKG.PRE_CLM_HIST_BUFFER_LIST";
    private static String strPRE_CLM_HIST_BUFFER_DETAIL= "PRE_CLM_REPORTS_PKG.PRE_CLM_HIST_BUFFER_DETAIL";
    private static String strCALL_HOSPITAL_LIST = "PRE_CLM_REPORTS_PKG_CALL_HOSPITAL_LIST";
    
//  web reports
    private static String strWEBLOGIN_HOSPITAL_LIST= "PRE_CLM_REPORTS_PKG.WEBLOGIN_HOSPITAL_LIST";
    
    
    //For E-card reports
    private static String strSELECT_DATA_FOR_CARD_PRINT= "ECARD_PKG_SELECT_DATA_FOR_CARD_PRINT";
    
    private static String strSELECT_DATA_FOR_CARD_PRINTInd= "ECARD_PKG_SELECT_DATA_FOR_CARD_PRINT_MEM";
    
   
//	Fax Status Report
	private static final String strFAX_STATUS_RPT = "PRE_CLM_REPORTS_PKG.FAX_STATUS_RPT";
	private static final String strPROC_GEN_CALL_PENDING_REPORT = "{CALL SCHEDULE_PKG.PROC_GEN_CALL_PENDING_REPORT(?)}";
	private static final String strPROC_GEN_MRCLM_PENDING = "SCHEDULE_PKG.PROC_GEN_MRCLM_PENDING";
	private static final String strPROC_GEN_MRCLM_PENDING_SRTFLL = "SCHEDULE_PKG.PROC_GEN_MRCLM_PENDING_SRTFLL";
	
	private static final String strGET_CALLPENDING_BRANCH_REPORT = "SCHEDULE_PKG.GET_CALLPENDING_BRANCH_REPORT";

//for Online Reports
	private static final String strEMP_CRDN_RPT_FOR_HR = "ONLINE_REPORTS_PKG.EMP_CRDN_RPT_FOR_HR";
	private static final String strCLAIMS_REGISTERED = "ONLINE_REPORTS_PKG.CLAIMS_REGISTERED_RPT";
	private static final String strBILLS_PENDING = "ONLINE_REPORTS_PKG.CASHLESS_BILLS_PENDING_HR";
	private static final String strLIST_OF_EMP_AND_DEPENDENTS = "ONLINE_REPORTS_PKG.LIST_OF_EMP_AND_DEPENDENTS";
	private static final String strINS_PREAUTH_REPORT="ONLINE_REPORTS_PKG.INS_PREAUTH_REPORT";
	private static final String strINS_CLM_REGISTER_RPT="ONLINE_REPORTS_PKG.INS_CLM_REGISTER_RPT";
	private static final String strINS_CLM_REGISTER_DTL_RPT="ONLINE_REPORTS_PKG.INS_CLM_REGISTER_DTL_RPT";
	
	//On select IDs kocbroker
	private static final String strBRO_PREAUTH_REPORT="ONLINE_REPORTS_PKG.BRO_PREAUTH_REPORT";
	private static final String strBRO_CLM_REGISTER_RPT="ONLINE_REPORTS_PKG.BRO_CLM_REGISTER_RPT";
	private static final String strBRO_CLM_REGISTER_DTL_RPT="ONLINE_REPORTS_PKG.BRO_CLM_REGISTER_DTL_RPT";
	private static final String strBRO_PREAUTH_SUMMARY_RPT="ONLINE_REPORTS_PKG.PRE_AUTH_SUMMARY_RPT";
	private static final String strDashBoard_REPORT="ONLINE_REPORTS_PKG.DASHBOARD_RPT";
	private static final String strDashBoardSub_REPORT="ONLINE_REPORTS_PKG.dashboardsub_rpt";
	private static final String strBRO_Emp_Dep_REPORT="ONLINE_REPORTS_PKG.bro_list_of_emp_and_dependents";//added new for kocbroker
	
	private static final String strONLINE_CLM_INTIMATION="APP.CALL_CENTER_PKG_P_WEB_SMS_REPORT";//KOC 1339 for mail
	
	private static final String strCheque_Covering_Letter="fin_app.BATCH_REPORT_PKG_CHEQUE_COVERING_LETTER";
	private static final String strAddress_Label="fin_app.BATCH_REPORT_PKG_ADDRESS_LABEL";
	private static final String strGRP_SUMMARY_REPORT="ONLINE_REPORTS_PKG.SELECT_POLICY_PLAN_SUMMARY";
	private static final String strPREAUTH_GRP_REPORT="ONLINE_REPORTS_PKG.SELECT_PREAUTH_DETAIL";
	private static final String strONLINE_TAT_REPORT="ONLINE_REPORTS_PKG.SELECT_ONLINE_TAT_REPORT";
	//IBM Reports
	private static final String strIBMPREAUTH_REPORT="ENROLLMENT_REPORTS_PKG.IBM_PREAUTH_REPORT";
    private static final String strIBMCLAIM_REPORT="ENROLLMENT_REPORTS_PKG.IBM_CLAIM_REPORT";
    private static final String strIBMCHILDBORN_REPORT="ENROLLMENT_REPORTS_PKG.EMP_GIVEN_BIRTH_TO_CHILD";
    private static final String strIBMREPOTIN_REPORT="ENROLLMENT_REPORTS_PKG.IBM_REOPTIN_REPORT";
    private static final String strIBMRECON_REPORT="ENROLLMENT_REPORTS_PKG.MONTHLY_RECON_REPORT";
    private static final String strIBMDAILY_OPTOUT_REPORT="ENROLLMENT_REPORTS_PKG.OPTOUT_REPORT";
    private static final String strIBMDAILY_ADDITTIONAL_COVERAGE_REPORT="ENROLLMENT_REPORTS_PKG.ADDITTIONAL_COVERAGE_REPORT";
    private static final String strIBMDAILY_PARENTAL_COVERAGE_REPORT="ENROLLMENT_REPORTS_PKG.PARENTAL_COVERAGE_REPORT";
	
	//Added for KOC-1255
    private static final String strACCENTURE_DASHBOARD_MC_REPORT = "ONLINE_REPORTS_PKG.DASHBOARD_MC";
    private static final String strACCENTURE_DASHBOARD_CC_REPORT = "ONLINE_REPORTS_PKG.DASHBOARD_CC";
    private static final String strACCENTURE_DASHBOARD_SMC_REPORT = "ONLINE_REPORTS_PKG.DASHBOARD_SMC";
    private static final String strACCENTURE_DASHBOARD_SCC_REPORT = "ONLINE_REPORTS_PKG.DASHBOARD_SCC";
    private static final String strACCENTURE_DASHBOARD_PREAUTH_REPORT = "ONLINE_REPORTS_PKG.DASHBOARD_PREAUTH";
    private static final String strACCENTURE_DASHBOARD_CALLCENTER_REPORT = "ONLINE_REPORTS_PKG.DASHBOARD_CALLCENTER";
	//Ended For KOC-1255
    //citibank reports
	private static final String strSOFT_COPY_UPLOAD_REPORT="CITIBANK_REPORTS.SOFT_COPY_UPLOAD_REPORT";
	private static final String strSOFT_COPY_CANCELLATION_REPORT="CITIBANK_REPORTS.SOFT_COPY_CANCELLATION_REPORT";
	private static final String strNOT_CANCELLED_REPORT="CITIBANK_REPORTS.NOT_CANCELLED_REPORT";	
	
	//IOB Report
	private static final String strIOB_DETAIL_REPORT= "ENROLLMENT_REPORTS_PKG.IOB_DETAIL_REPORT";
	private static final String strHDFC_CERTIFICATE= "ENROLLMENT_REPORTS_PKG.HDFC_CERTIFICATE";
	private static final String strHDFC_CERTIFICATE_MEMBER= "ENROLLMENT_REPORTS_PKG.HDFC_CERTIFICATE_MEMBER";
	
	//Coverage Reports
	private static final String strCALL_COVERAGE_LIST = "PRE_CLM_REPORTS_PKG_CALL_COVERAGE_LIST";
	private static final String strCALL_CLAUSE_ASSOC_DOC_LIST = "PRE_CLM_REPORTS_PKG_CALL_CLAUSE_ASSOC_DOC_LIST";
	
	//TDS Daily Report
	private static final String strTDS_DAILY_RPT = "{CALL BATCH_REPORT_PKG.TDS_DAILY_RPT(?)}";
	private static final String strTDS_DETAIL_REPORT = "BATCH_REPORT_PKG.TDS_DETAIL_REPORT";
	private static final String strSELECT_EMP_WITHOUT_SUM_LIST = "ADMINISTRATION_PKG_SELECT_EMP_WITHOUT_SUM_LIST";
	private static final String strDAILY_TRANSFER_SUMMARY_REPORT = "BATCH_REPORT_PKG.DAILY_TRANSFER_SUMMARY_REPORT";
	private static final String strMONTHLY_REMIT_SUMARY_REPORT = "BATCH_REPORT_PKG.MONTHLY_REMIT_SUMARY_REPORT";
	private static final String strANNEXURE_I26Q_REPORT = "BATCH_REPORT_PKG.ANNEXURE_I26Q_REPORT";
	private static final String strCHALLAN_DETAILS_Q_REPORT = "BATCH_REPORT_PKG.CHALLAN_DETAILS_Q_REPORT";
		
	//TDS Hospital Report
	private static final String strHOSPITAL_TDS__RPT = "{CALL TDS_PKG.SELECT_HOSPITAL_TDS_RPT(?)}";
	//AUDIT PREAUTH CLAIM Provider Report
	/*private static final String strPROVIDER_NAME="select p.PROVIDER_NAME from tpa_hosp_info thi join app.dha_providers_master p on (thi.hosp_licenc_numb=p.provider_id) WHERE p.HEALTH_AUTHORITY =?"+
"and p.PROVIDER_NAME like ?";*/
/*	private static final String strPROVIDER_NAME="select p.PROVIDER_NAME from tpa_hosp_info thi join app.dha_providers_master p on (thi.hosp_licenc_numb=p.provider_id)"+
	"WHERE p.HEALTH_AUTHORITY =? and UPPER(p.PROVIDER_NAME) like ? OR LOWER(p.PROVIDER_NAME) like ?";*/
	
	private static final String strPROVIDER_NAME="select p.PROVIDER_NAME from app.tpa_hosp_info thi join app.dha_providers_master p on (thi.hosp_licenc_numb=p.provider_id)"+
	"WHERE (p.HEALTH_AUTHORITY =? AND UPPER(p.PROVIDER_NAME) like ?) OR (p.HEALTH_AUTHORITY =? AND LOWER(p.PROVIDER_NAME) like ?)";
	
	//Universal Sampo Pending Report - 29/01/2010
	private static final String strUNIVERSAL_SOMPO_PENDING_REPORT = "MIS_V2_FIN_REPORTS_PKG.UNIVERSAL_SOMPO_PENDING_REPORT";
	
	//Cheque printing in Maintanance module -04/05/2010
	private static final String strPrintChequeInfo=" BATCH_REPORT_PKG.PRINT_CHEQUE_INFO";
	
	//TDS Certificate Generation -11/05/2010
	private static final String strSelectTdsCert= "TDS_PKG.SELECT_TDS_CERTIFICATE";
	private static final String strAckQuarterInfo= "TDS_PKG.GET_ACK_QUARTER_INFO";
	private static final String strTdsAnneSummary= "TDS_PKG.TDS_ANNEXURE_SUMMARY";
	private static final String strAnneClmWiseBreakup= "TDS_PKG.ANNEXURE_CLAIM_WISE_BREAKUP";
	private static final String strTdsCovLetter= "TDS_PKG.SELECT_TDS_COV_LETTER";
	
	private static final String strJOB_STATUS_RPT ="SCHEDULE_PKG.SELECT_JOB_DETAIL";
	// Summary Report CR KOC1224
	private static final String strONLINE_SUMMARY_REPORT = "ONLINE_REPORTS_PKG.EMP_MEM_SUMMARY";
	//added for 2koc 
	private static final String strCourierDetails = "COURIER_PKG.GENERATE_COURIER_LETTER";
	//end added for 2koc 
	//KOC 1353 for payment report
	private static final String strONLINE_PAYMENT_INS_REPORT = "ONLINE_REPORTS_PKG.INS_PAYMENT_REPORT";
	//KOCPreauthreport 
	private static final String strINS_PREAUTH_CIGNA_REPORT = "ONLINE_REPORTS_PKG.INS_PREAUTH_CIGNA_REPORT";
	private static final String strINS_CLAIM_CIGNA_REPORT = "ONLINE_REPORTS_PKG.INS_CLAIM_CIGNA_REPORT";
	private static final String strINS_POLICY_CIGNA_REPORT = "ONLINE_REPORTS_PKG.INS_POLICY_CIGNA_REPORT";
	//KOC 1353 for payment report
	
	//SELF FUND
    private static String strPRE_AUTH_LETTER ="HOSP_DIAGNOSYS_PKG.gen_bill_report1";
    private static String strDiagonisTariffHistory ="HOSP_DIAGNOSYS_PKG.gen_bill_report";
	
    //intX
    private static String strCUSTOMERCALLBACKREPORT		= "SCHEDULE_PKG.PROC_GEN_CALL_PENDING_REPORT";
	private static final String strPREAUTHUTILIZATION 	= "{CALL online_reports_pkg.authutilazation_rpt(?,?,?,?,?)}";
	private static final String strPRPAUTHUTILIZATION = "{CALL PKG_MIS_REPORTS_MIS_REPORT_PREAUTH(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strCLRAUTHUTILIZATION = "{CALL PKG_MIS_REPORTS_MIS_REPORT_CUR(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strERPAUTHUTILIZATION = "{CALL PKG_MIS_REPORTS_MIS_REPORT_ENROLMNT(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strCHEQUEWISEREPORT		= "HOSPITAL_PKG_SELECT_CHKWISE_REPORT";
	private static final String strBATCHRECONCILIATION	= "HOSPITAL_PKG_SELECT_BATCH_RECON_SMRY_RPT";
	private static final String strBATCHINVOICE			= "HOSPITAL_PKG_SELECT_BATCH_INVOICE_RPT";
	private static final String strGENERATEPREAUTHLETTER= "HOSPITAL_PKG_SELECT_PREAUTH_DETAILS";
	private static final String strFINACNEDASHBOARD		= "HOSPITAL_PKG_DASHBOARD_ACCOUNT";
	private static final String strINVOICEWISEREPORT	= "HOSPITAL_PKG_SELECT_INVOICEWISE_REPORT";
	private static final String strOVERDUEREPORT		= "HOSPITAL_PKG_SELECT_OVERDUE_RPT";
	private static final String strONLINEPREAUTHFORM	= "HOSP_DIAGNOSYS_PKG_GET_HOSP_DETAILS";
	
	//insLoginReports
	private static final String strCLAIMUTILIZATIONREP   = "ONLINE_EXTERNAL_INFO_PKG.CLAIMUTILAZATION_RPT";
	private static final String strAUTHUTILIZATIONREP 	 = "ONLINE_EXTERNAL_INFO_PKG.AUTHUTILAZATION_RPT";
	private static final String strTATFORCLAIMS			 = "ONLINE_EXTERNAL_INFO_PKG.CLAIM_TAT_RPT1";
	private static final String strTATForPreAuth	     = "ONLINE_EXTERNAL_INFO_PKG.PREAUTH_TAT_RPT";
	private static final String strTRRForPreAuth		 = "ONLINE_EXTERNAL_INFO_PKG.TRR_Premium_rpt";
	private static final String strTATFSCORPOARTE		 = "ONLINE_EXTERNAL_INFO_PKG.Enrollment_COR_TAT_rpt";
	private static final String strTATFSINDIVIDUAL		 = "ONLINE_EXTERNAL_INFO_PKG.Enrollment_IND_TAT_rpt";


	private static final String strDETAILEDREPORT	  = "{CALL fin_app.BATCH_REPORT_PKG_CLAIMS_DETAILS_REPORT(?,?)}";
	private static final String strPENDINGREPORT	  = "{CALL fin_app.BATCH_REPORT_PKG_CLAIMS_PENDING_REPORT(?,?)}";
	private static final String strBANKHOSPITALREPORT = "{CALL fin_app.BATCH_REPORT_PKG_HOSP_BANK_DTL_REPORT(?,?)}";
	private static final String strBANKPOLICYREPORT	  = "{CALL fin_app.BATCH_REPORT_PKG_POLICY_BANK_DTL_REPORT(?,?)}";
	private static final String strREMITTANCEREPORT = "{CALL fin_app.BATCH_REPORT_PKG_GENERATE_RA_REPORT(?,?)}";
	private static final String strCLAIM_SELECT_REIM_CLAIM_LETTER="PAT_CLM_REPORTS_PKG_REIM_CLM_LETTE";
	private static final String  strMEMBER_DIAGNOSIS_DETAILS="PAT_CLM_REPORTS_PKG_get_pat_diagnosis";
	
	//private static final String strGENERATEPREAUTHLETTER= "HOSPITAL_PKG.SELECT_PREAUTH_DETAILS";
	//private static final String strFINACNEDASHBOARD		= "HOSPITAL_PKG.DASHBOARD_ACCOUNT";
	//private static final String strINVOICEWISEREPORT	= "HOSPITAL_PKG.SELECT_INVOICEWISE_REPORT";
	//private static final String strOVERDUEREPORT		= "HOSPITAL_PKG.SELECT_OVERDUE_RPT";
	//private static final String strONLINEPREAUTHFORM	= "HOSP_DIAGNOSYS_PKG.GET_HOSP_DETAILS";
	//private static final String strPREAUTH_REFERRAL_REP	= "PREAUTH_REFERRAL_LETTER_PKG.GENERATE_REFERRAL_LETTER";
	
	private static final String strBRO_CLAIMUTILIZATIONREP = "ONLINE_EXTERNAL_INFO_PKG.CLAIMUTILAZATION_BRO_RPT";
	private static final String strBRO_AUTHUTILIZATIONREP = "ONLINE_EXTERNAL_INFO_PKG.AUTHUTILAZATION_BRO_RPT";
	private static final String strBRO_TATFORCLAIMS = "ONLINE_EXTERNAL_INFO_PKG.CLAIM_TAT_BRO_RPT";
	//private static final String strCLAIM_SELECT_REIM_CLAIM_LETTER="PAT_CLM_REPORTS_PKG.REIM_CLM_LETTE";
	//private static final String  strMEMBER_DIAGNOSIS_DETAILS="PAT_CLM_REPORTS_PKG.get_pat_diagnosis";
	
	private static final String strBRO_TATFORPREAUTH = "ONLINE_EXTERNAL_INFO_PKG.PREAUTH_TAT_BRO_RPT";
	private static final String strBRO_TECHNICALRESULTREPORT = "ONLINE_EXTERNAL_INFO_PKG.TRR_PREMIUM_BRO_RPT";
	private static final String strBRO_TATFSCORPOARTE = "ONLINE_EXTERNAL_INFO_PKG.ENROLLMENT_COR_TAT_BRO_RPT";
	private static final String strBRO_TATFSINDIVIDUAL = "ONLINE_EXTERNAL_INFO_PKG.ENROLLMENT_IND_TAT_BRO_RPT";
	private static final String strBRO_HIPA = "ONLINE_EXTERNAL_INFO_PKG.HIPA";
	private static final String strRevenueInvoiceReport="{CALL fin_app.BATCH_REPORT_PKG_REVENUE_INVOICE_REPORT(?,?)}";
	private static final String strHEALTH_INSURANCE_QUOTE = "online_policy_copy_issue.generate_policy_quote";
	private static final String strBENEFIT_DESIGN_SECTION = "online_policy_copy_issue.get_benifit_details";
	private static final String strPREMIUM_RATES_SECTION = "premium_calculation_select_calculate_premium";
	
	private static final String strClaimSummary_Report = "PKG_MIS_REPORTS.providers_clm_reports";
	//private static final String strDETAILEDREPORT	  = "{CALL BATCH_REPORT_PKG.CLAIMS_DETAILS_REPORT(?,?)}";
	//private static final String strPENDINGREPORT	  = "{CALL BATCH_REPORT_PKG.CLAIMS_PENDING_REPORT(?,?)}";
	//private static final String strBANKHOSPITALREPORT = "{CALL BATCH_REPORT_PKG.HOSP_BANK_DTL_REPORT(?,?)}";
	//private static final String strBANKPOLICYREPORT	  = "{CALL BATCH_REPORT_PKG.POLICY_BANK_DTL_REPORT(?,?)}";
	
	private static final String strPREAUTH_REFERRAL_REP	= "PREAUTH_REFERRAL_LETTER_PKG_GENERATE_REFERRAL_LETTER";
	
	private static final String strClaimSummary_ProviderLOgin ="HOSPITAL_PKG_dwn_clm_reprts_in_prdr_login";
	
	
	private static final String strDebitNote_ClaimWise_Report = "fin_app.FLOAT_ACCOUNTS_PKG_draft_debit_clm__note_rpt";
	private static final String strDebitNote_ActivityWise_Report = "fin_app.FLOAT_ACCOUNTS_PKG_draft_debit_note_rpt";
	private static final String strASCANA_ClaimWise_Report = "fin_app.FLOAT_ACCOUNTS_PKG_ascn_draft_debit_clm__note_rpt";
	private static final String strASCANA_ActivityWise_Report = "fin_app.FLOAT_ACCOUNTS_PKG_draft_debit_note_rpt";
	//private static final String strClaimSummary_ProviderLOgin ="HOSPITAL_PKG.dwn_clm_reprts_in_prdr_login";
	
	private static final String strNLGICRI_NLGICRI_ClaimWise_Report="fin_app.FLOAT_ACCOUNTS_PKG_NLGIC_RI_REPORT";
	private static final String strProvider_Payment_Excel_Report = "generate_mail_pkg_clm_appr_rcpt_to_nhcp";
	
	private static final String strClaimAuditReport ="{CALL PKG_MIS_REPORTS_CLAIM_AUDIT_RPT(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strPreAuthAuditReport="{CALL PKG_MIS_REPORTS_PRE_AUTH_AUDIT_RPT(?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strUploadAuditResult="{CALL PKG_MIS_REPORTS_AUDIT_REPT_UPLOAD(?,?,?)}";
	private static final String strAuditUploadResultLogDetail="{CALL PKG_MIS_REPORTS_AUDIT_LOG_RPT(?,?,?,?)}";
	
	private static final String strExchangeRatesList = "{CALL PKG_MIS_REPORTS_SELECT_EXCHANGE_LIST(?,?,?,?,?,?,?,?)}";
	
	private static final String strExchangeRatesReportDownload = "PKG_MIS_REPORTS_DWNLD_EXCHANGE_REPORT";
	
	private static final String strMemberCheckEligibility = "PKG_MIS_REPORTS_MEM_ELIGCHECK_REPORT";
	
	private static final String strProductSyncReport = "product_admin_pkg_pol_syn_list";
	
	//private static final String strConnection ="Connection";
	//private static final String strEcardConnection ="EcardConnection";
	
    /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getReport(String strReportID,String strParameter) throws TTKException {
    	
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        CachedRowSet crs = null;
        String strProcedureName="";
        
        strProcedureName =getProcedureName(strReportID);
       
        if(strProcedureName==null)
        {
        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
        }//end of if(strProcedureName==null)
        try{
            String strCall = "{CALL "+strProcedureName.trim()+"(?,?)}";
           
            log.info("ReportID : "+strReportID);
            log.info("Parameters : "+strParameter);
            log.info("procedure	: "+strCall);
           
            crs = new CachedRowSetImpl(); 	
            conn = ResourceManager.getConnection();
        
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
     //     cStmtObject = conn.prepareCall(strCall,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cStmtObject.setString(1,strParameter);
            cStmtObject.registerOutParameter(2,Types.OTHER);
            
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(2);  
     	    if(rs !=null)
            {
             	crs.populate(rs);
            }//end of if(rs !=null)
            return crs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
        				}//end of catch (SQLException sqlExp)
        			}//end of finally Connection Close
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
    }//end of getReport(String strReportID,String strParameter)
    //Added for IBM....9.2 

	//ADDED BY PRAVEEN only for IBM Reports.
		public ResultSet getReport(String strReportID,String strParameter1,String strParameter2,String strParameter3) throws TTKException {
		    	Connection conn = null;
		    	CallableStatement cStmtObject=null;
		        ResultSet rs = null;
		        String strProcedureName="";
		        strProcedureName =getProcedureName(strReportID);
		        System.out.println("strProcedureName::"+strProcedureName);
		        if(strProcedureName==null)
		        {
		        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
		        }//end of if(strProcedureName==null)
		        try{
		            /*String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?)}";
		            crs = new OracleCachedRowSet();
		            conn = ResourceManager.getConnection();//getTestConnection();
		            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
		            cStmtObject.setString(1,strParameter1);
		            cStmtObject.setString(2,strParameter2);
		            cStmtObject.setString(3,strParameter3);

		            cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);*/
		        	
		        	String strCall ="";
                     if("HOSPITAL_PKG_SELECT_PREAUTH_DETAILS".equals(strProcedureName)){
                         strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?,?)}";
                    }else{
                         strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?)}";
                    }
                    conn = ResourceManager.getConnection();//getTestConnection();
                    cStmtObject = conn.prepareCall(strCall,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    
                //  HOSPITAL_PKG_SELECT_BATCH_RECON_SMRY_RPT    --- HOSPITAL_PKG_DASHBOARD_ACCOUNT  
                    if("HOSPITAL_PKG_SELECT_BATCH_RECON_SMRY_RPT".equals(strProcedureName)|| "PREAUTH_REFERRAL_LETTER_PKG_GENERATE_REFERRAL_LETTER".equals(strProcedureName)){
                    	cStmtObject.setLong(1,(Long.parseLong(strParameter1.toString())));
                    }
                    else{
                    System.out.println("strCall--------"+strCall);
                    cStmtObject.setString(1,strParameter1.toString());//
                    log.info("strParameter1.."+strParameter1);
                    }
                    if("HOSPITAL_PKG_SELECT_BATCH_RECON_SMRY_RPT".equals(strProcedureName) || "HOSPITAL_PKG_DASHBOARD_ACCOUNT".equals(strProcedureName) || "HOSP_DIAGNOSYS_PKG_GET_HOSP_DETAILS".equals(strProcedureName) || "PREAUTH_REFERRAL_LETTER_PKG_GENERATE_REFERRAL_LETTER".equals(strProcedureName)){ 
                    	log.info("strParameter2.."+strParameter2);
                    cStmtObject.setString(2,strParameter2.toString());
                    }//
                    else{
                    	log.info("strParameter2.."+strParameter2);
                    	cStmtObject.setLong(2,Long.parseLong(strParameter2.toString()));//
                    }
                    if("HOSP_DIAGNOSYS_PKG_GET_HOSP_DETAILS".equals(strProcedureName)|| "PREAUTH_REFERRAL_LETTER_PKG_GENERATE_REFERRAL_LETTER".equals(strProcedureName)){
                    	log.info("strParameter3.."+strParameter3);
                    	cStmtObject.setLong(3,Long.parseLong(strParameter3.toString()));//
                    }
                    else{
                    cStmtObject.setString(3,strParameter3.toString());//
                    log.info("strParameter3.."+strParameter3);
                    }

                    cStmtObject.registerOutParameter(4,Types.OTHER);
                    if("HOSPITAL_PKG_SELECT_PREAUTH_DETAILS".equals(strProcedureName)){
                    cStmtObject.registerOutParameter(5,Types.OTHER);
                    cStmtObject.registerOutParameter(6,Types.OTHER);
                    } 
		            cStmtObject.execute();
		            rs = (java.sql.ResultSet) cStmtObject.getObject(4);
		            conn.commit();
		            
		            return rs;
		        }//end of try
		        catch (SQLException sqlExp)
		        {
		            throw new TTKException(sqlExp, "tTkReports");
		        }//end of catch (SQLException sqlExp)
		        catch (Exception exp)
		        {
		            throw new TTKException(exp, "tTkReports");
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
		        				log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
		        				throw new TTKException(sqlExp, "tTkReports");
		        			}//end of catch (SQLException sqlExp)
		        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
		        			{
		        				try
		        				{
		        					if(conn != null) conn.close();
		        				}//end of try
		        				catch (SQLException sqlExp)
		        				{
		        					log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
		        					throw new TTKException(sqlExp, "tTkReports");
		        				}//end of catch (SQLException sqlExp)
		        			}//end of finally Connection Close
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
		    }//end of getReport(String strReportID,String strParameter)




	//Ended..
		
		//ADDED BY PRAVEEN only for IBM Reports.
		public ResultSet getReport(String strReportID,String strParameter1,String strParameter2,String strParameter3,String strParameter4) throws TTKException {

		    	Connection conn = null;
		    	CallableStatement cStmtObject=null;
		        ResultSet rs = null;
		        String strProcedureName="";
		        strProcedureName =getProcedureName(strReportID);
                             
		        if(strProcedureName==null)
		        {
		        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
		        }//end of if(strProcedureName==null)
		        CachedRowSet crs = null;
		        try{
		        	System.out.println("strProcedureName(Overdue)--------------"+strProcedureName);
		            String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?)}";
		            crs = new CachedRowSetImpl();
		            conn = ResourceManager.getConnection();//getTestConnection();
		            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
		            cStmtObject.setString(1,strParameter1);
		            cStmtObject.setString(2,strParameter2);
		            cStmtObject.setString(3,strParameter3);
		            cStmtObject.setString(4,strParameter4);

		            cStmtObject.registerOutParameter(5,Types.OTHER);
		            cStmtObject.execute();
		            rs = (java.sql.ResultSet) cStmtObject.getObject(5);
		            if(rs !=null)
		            {
		            	crs.populate(rs);
		            }//end of if(rs !=null)
		            return crs;
		        }//end of try
		        catch (SQLException sqlExp)
		        {
		            throw new TTKException(sqlExp, "tTkReports");
		        }//end of catch (SQLException sqlExp)
		        catch (Exception exp)
		        {
		            throw new TTKException(exp, "tTkReports");
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
		        			log.error("Error while closing the Resultset in TTKReportDAOImpl getPolicyList()",sqlExp);
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
		        				log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
		        				throw new TTKException(sqlExp, "tTkReports");
		        			}//end of catch (SQLException sqlExp)
		        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
		        			{
		        				try
		        				{
		        					if(conn != null) conn.close();
		        				}//end of try
		        				catch (SQLException sqlExp)
		        				{
		        					log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
		        					throw new TTKException(sqlExp, "tTkReports");
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
		    }//end of getReport(String strReportID,String strParameter)




	//Ended..
	
    /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getReport(String strReportID) throws TTKException
    {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        OracleCachedRowSet crs = null;
        try{
            crs = new OracleCachedRowSet();
            conn = ResourceManager.getConnection();
            //cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPROC_GEN_CALL_PENDING_REPORT);
            if(strReportID.equals("TDSSchedRpt"))
            {
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strTDS_DAILY_RPT);
            }//end of if(strReportID.equals("CallPenRpt"))
            else if(strReportID.equals("HospitalTDSRpt"))
            {
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHOSPITAL_TDS__RPT);
            }//end of else if(strReportID.equals("HospitalTDSRpt"))
            else if (strReportID.equals("PreAuthUtilization"))
    		{
    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPREAUTHUTILIZATION);
    		}//end of else if (strReportID.equals("CustCallBack"))//added as per intX preauth Utilization report 
            else
            {
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPROC_GEN_CALL_PENDING_REPORT);
            }//end of else
            
            cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(1);            
            if(rs !=null)
            {
             	crs.populate(rs);
            }//end of if(rs !=null)
            return crs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getPolicyList()",sqlExp);
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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
    }//end of getReport(String strReportID)
    
    /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getEcardReport(String strReportID,String strParameter) throws TTKException {
    	
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        String strProcedureName="";
        strProcedureName =getProcedureName(strReportID);        
        
        if(strProcedureName==null)
        {
        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
        }//end of if(strProcedureName==null)
    //    OracleCachedRowSet crs = null;
        CachedRowSet crs = null;
        try{
            String strCall = "{CALL "+strProcedureName.trim()+"(?,?)}";
            //log.debug("strCall is " + strCall);
          //  crs = new OracleCachedRowSet();
            crs = new CachedRowSetImpl();	
        //    conn = ResourceManager.getEcardConnection();
            conn = ResourceManager.getConnection();		
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
            cStmtObject.setString(1,strParameter);
         //   cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
            cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(2);
            if(rs !=null)
            {
             	crs.populate(rs);
            }//end of if(rs !=null)
            return crs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getEcardReport()",sqlExp);
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getEcardReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getEcardReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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
    }//end of getEcardReport(String strReportID,String strParameter)
    
    /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getIEcardReport(String strReportID,String strParameter,long strMemParameter) throws TTKException {
    	
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        String strProcedureName="";
        strProcedureName =getProcedureName(strReportID);        
        
        if(strProcedureName==null)
        {
        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
        }//end of if(strProcedureName==null)
        OracleCachedRowSet crs = null;
        try{
            String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?)}";
            //log.debug("strCall is " + strCall);
            crs = new OracleCachedRowSet();
            conn = ResourceManager.getEcardConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
            cStmtObject.setString(1,strParameter);
            cStmtObject.setLong(2,strMemParameter);
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(3);
            if(rs !=null)
            {
             	crs.populate(rs);
            }//end of if(rs !=null)
            return crs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getEcardReport()",sqlExp);
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getEcardReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getEcardReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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
    }//end of getEcardReport(String strReportID,String strParameter)
//Added as per KOC 1179 change Request
	    /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getReport(String strReportID,String strParameter,StringBuffer shotfallType) throws TTKException {
    	
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        String strProcedureName="";
        strProcedureName =getProcedureName(strReportID);
    	
        if(strProcedureName==null)
        {
        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
        }//end of if(strProcedureName==null)
        CachedRowSet crs = null;
        try{
        	System.out.println("strProcedureName========"+strProcedureName);
            String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?)}";
            crs = new CachedRowSetImpl();
            conn = ResourceManager.getConnection();//getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
            cStmtObject.setString(1,strParameter);
			 cStmtObject.setString(2,shotfallType.toString());
            cStmtObject.registerOutParameter(3,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(3);  
            if(rs !=null)
            {
            	crs.populate(rs);
            }//end of if(rs !=null)
            return crs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getPolicyList()",sqlExp);
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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
    }//end of getReport(String strReportID,String strParameter,StringBuffer shotfallType)

	    
    /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strProcedureName String which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getReport(String strReportID,String strParameter,int indexCursor) throws TTKException {
    	
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
    	String strProcedureName="";
    	String strCall = null;
    	strProcedureName =getProcedureName(strReportID);
    	if(strProcedureName==null)
    	{
    		throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
    	}//end of if(strProcedureName==null)
    	OracleCachedRowSet crs = null;
    	try{
    		if(strReportID.equals("EndrImpactReport") || strReportID.equals("EFTAcceAnne")){
    			strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?)}";
    		}//end of if(strReportID.equals("CallImpactReport"))
    		if(strReportID.equals("PremiumRates"))
    		{
    			strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?)}";
    		}
    		else {
    			strCall = "{CALL "+strProcedureName.trim()+"(?,?,?)}";
    		}//end of else
    		crs = new OracleCachedRowSet();
    		conn = ResourceManager.getConnection();//getTestConnection();
    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
    		cStmtObject.setString(1,strParameter);
    		cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
    		cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
    		if(strReportID.equals("EndrImpactReport") || strReportID.equals("EFTAcceAnne"))
    		{
    			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
    			cStmtObject.registerOutParameter(5,OracleTypes.CURSOR);
    		}//end of if(strReportID.equals("EndrImpactReport"))
    		
    		if(strReportID.equals("PremiumRates"))
    		{
    			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
    		}
    		cStmtObject.execute();
    		rs = (java.sql.ResultSet) cStmtObject.getObject(indexCursor);
    		if(rs !=null)
    		{
    			crs.populate(rs);
    		}//end of if(rs !=null)
    		return crs;
    	}//end of try
    	catch (SQLException sqlExp)
    	{
    		throw new TTKException(sqlExp, "tTkReports");
    	}//end of catch (SQLException sqlExp)
    	catch (Exception exp)
    	{
    		throw new TTKException(exp, "tTkReports");
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
    				log.error("Error while closing the Resultset in TTKReportDAOImpl getPolicyList()",sqlExp);
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
    					log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
    					throw new TTKException(sqlExp, "tTkReports");
    				}//end of catch (SQLException sqlExp)
    				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    				{
    					try
    					{
    						if(conn != null) conn.close();
    					}//end of try
    					catch (SQLException sqlExp)
    					{
    						log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
    						throw new TTKException(sqlExp, "tTkReports");
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
    }//end of getReport(String strReportID,String strParameter)
    
    /**
     * This method returns the ResultSet Array, which contains Reports data which are populated from the database
     * @param strReportID String which is a ReportID which has to be called to get data.
     * @param strParameter String which is a concatinated Parameters which has to be called to get data.
     * @param strNoOfCursors String which contains the No. Of Cursors.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */    
    public ResultSet[] getReport(String strReportID,String strParameter, String strNoOfCursors) throws TTKException {

    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs[] = null;
    	String strProcedureName="";
    	strProcedureName =getProcedureName(strReportID);
    	int intNoOfCursors = Integer.parseInt(strNoOfCursors);
    	
   
    	if(strProcedureName==null)
    	{
    		throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
    	}//end of if(strProcedureName==null)
    	OracleCachedRowSet crs[] = null;
    	CachedRowSet cachers[] = null;

    	try{
    		if(strReportID.equals("TPACommissionRpt"))
    		{
        		StringTokenizer strTok = new StringTokenizer(strParameter,"|");
        		String strArray[] = new String[4];
        		for(int i=0;i<4 && strTok.hasMoreTokens();i++) {
        	        strArray[i] = (String)strTok.nextElement();
        	     }//end of for(int i=0;i<4 && strTok.hasMoreTokens();i++)
        		
        		String strInvoiceSeqID = strArray[0];
        		String strStartDate = strArray[1];
        		String strEndDate = strArray[2];
        		Float flAddedBy = new Float(strArray[3]);
        		
        		String strCall = "{CALL "+strProcedureName.trim();
                String strParameters = "(?,?,?,?";
                for(int i=0;i<intNoOfCursors+1;i++){ //one more for OUT parameter for batch seq id 
                	strParameters += ",?";
                }//end of for(int i=0;i<intNoOfCursors+1;i++)
                strParameters += ")}";
                strCall += strParameters;
                crs = new OracleCachedRowSet[intNoOfCursors];
                rs = new ResultSet[intNoOfCursors];
                conn = ResourceManager.getConnection();//getTestConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
                cStmtObject.setString(1,strInvoiceSeqID);
                cStmtObject.setString(2,strStartDate);
                cStmtObject.setString(3,strEndDate);
                cStmtObject.setFloat(4,flAddedBy);
                for(int i=5;i<intNoOfCursors+5;i++){
                	cStmtObject.registerOutParameter(i,OracleTypes.CURSOR);
                }//end of for(int i=5;i<intNoOfCursors+5;i++)
                cStmtObject.registerOutParameter(17,OracleTypes.NUMBER);
                cStmtObject.execute();
                for(int index=0;index<intNoOfCursors;index++) {
                	rs[index]= (java.sql.ResultSet) cStmtObject.getObject(index+5);
                	crs[index] = new OracleCachedRowSet();
                	if(rs[index]!=null){
                		crs[index].populate(rs[index]);
                	}//end of if(rs[index]!=null){ 
                }//end of for(int index=0;index<intNoOfCursors;index++)
                return crs;
            }//end of if(strReportID.equals("TPACommissionRpt"))
    		else if(strReportID.equals("FinTPAComm"))
    		{
    			String strCall = "{CALL "+strProcedureName.trim();
    			String strParameters = "(?";
    			for(int i=0;i<intNoOfCursors;i++){ //one more for OUT parameter for batch seq id 
    				strParameters += ",?";
    			}//end of for(int i=0;i<intNoOfCursors+1;i++)
    			strParameters += ")}";
    			strCall += strParameters;
    			crs = new OracleCachedRowSet[intNoOfCursors];
    			rs = new ResultSet[intNoOfCursors];
    			conn = ResourceManager.getConnection();//getTestConnection();
    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
    			cStmtObject.setString(1,strParameter);
    			for(int i=2;i<intNoOfCursors+2;i++){
    				cStmtObject.registerOutParameter(i,OracleTypes.CURSOR);
    			}//end of for(int i=2;i<intNoOfCursors+2;i++)
    			cStmtObject.execute();
    			for(int index=0;index<intNoOfCursors;index++) {
    				rs[index]= (java.sql.ResultSet) cStmtObject.getObject(index+2);
    				crs[index] = new OracleCachedRowSet();
    				if(rs[index]!=null){
    					crs[index].populate(rs[index]);
    				}//end of if(rs[index]!=null)
    			}//end of for(int index=0;index<intNoOfCursors;index++)
    			return crs;    			
    		}//end of else if(strReportID.equals("FinTPAComm"))
			// Summary Report KOC1224
    		else if(strReportID.equals("OnlineSummaryRpt"))
        	{
        			String strCall = "{CALL "+strProcedureName.trim();
        			String strParameters = "(?";
        			for(int i=0;i<intNoOfCursors;i++){ //one more for OUT parameter for batch seq id 
        				strParameters += ",?";
        			}//end of for(int i=0;i<intNoOfCursors+1;i++)
        			strParameters += ")}";
        			
        			strCall += strParameters;
        			crs = new OracleCachedRowSet[intNoOfCursors];
        			rs = new ResultSet[intNoOfCursors];
        			conn = ResourceManager.getConnection();//getTestConnection();
        			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
        			cStmtObject.setString(1,strParameter);
        			for(int i=2;i<intNoOfCursors+2;i++){
        				
        				cStmtObject.registerOutParameter(i,OracleTypes.CURSOR);
        			}//end of for(int i=2;i<intNoOfCursors+2;i++)
        			cStmtObject.execute();
        			for(int index=0;index<intNoOfCursors;index++) {
        				rs[index]= (java.sql.ResultSet) cStmtObject.getObject(index+2);
        				crs[index] = new OracleCachedRowSet();
        				if(rs[index]!=null){
        					crs[index].populate(rs[index]);
        				}//end of if(rs[index]!=null)
        			}//end of for(int index=0;index<intNoOfCursors;index++)
        			return crs;    			
        		}//end of else if(strReportID.equals("OnlineSummaryReport"))
			 else if(strReportID.equalsIgnoreCase("claimintimation") || strReportID.equalsIgnoreCase("preauthintimation") )
        	{
        		String strCall = "{CALL "+strProcedureName.trim();
    			String strParameters = "(?";
    			for(int i=0;i<intNoOfCursors;i++){
    				strParameters += ",?";
    			}//end of for(int i=0;i<intNoOfCursors;i++)
    			strParameters += ")}";
    			strCall += strParameters;
    		
    			crs = new OracleCachedRowSet[intNoOfCursors];
    			rs = new ResultSet[intNoOfCursors];
    			conn = ResourceManager.getConnection();//getTestConnection();
    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
    			cStmtObject.setString(1,strParameter);
    			for(int i=2;i<intNoOfCursors+2;i++){
    				cStmtObject.registerOutParameter(i,OracleTypes.CURSOR);
    			}//end of for(int i=2;i<intNoOfCursors+2;i++)
    			cStmtObject.execute();
    			for(int index=0;index<intNoOfCursors;index++) {
    				rs[index]= (java.sql.ResultSet) cStmtObject.getObject(index+2);
    				crs[index] = new OracleCachedRowSet();
    				if(rs[index]!=null){
    					crs[index].populate(rs[index]);
    				}//end of if(rs[index]!=null)
    			}//end of for(int index=0;index<intNoOfCursors;index++)
    			
    			return crs;  
         	}// end of 	else if(strReportID.equalsIgnoreCase("Intimation"))
            
            else if(strReportID.equals("AddressingDocuments"))
        	{
    			   String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?,?,?,?)}";
        			crs = new OracleCachedRowSet[intNoOfCursors];
        			rs = new ResultSet[intNoOfCursors];
        			conn = ResourceManager.getConnection();//getTestConnection();
        			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
        			cStmtObject.setString(1,"AddressingDocuments");
        			cStmtObject.setString(2,"AddressingDocuments");
        			for(int i=3;i<intNoOfCursors+3;i++){
        				cStmtObject.registerOutParameter(i,OracleTypes.CURSOR);
        			}//end of for(int i=2;i<intNoOfCursors+2;i++)
        			cStmtObject.execute();
        			for(int index=0;index<intNoOfCursors;index++) {
        				rs[index]= (java.sql.ResultSet) cStmtObject.getObject(index+3);
        				crs[index] = new OracleCachedRowSet();
        				if(rs[index]!=null){
        					crs[index].populate(rs[index]);
        				}//end of if(rs[index]!=null)
        			}//end of for(int index=0;index<intNoOfCursors;index++)
        			return crs;    			
        		}//end of else if(strReportID.equals("AddressingDocuments"))
    		
    		//added for Cigna
    		
    		else if(strReportID.equals("CignaAddressingDocuments"))
         	{
     			   String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?)}";
         			crs = new OracleCachedRowSet[intNoOfCursors];
         			rs = new ResultSet[intNoOfCursors];
         			conn = ResourceManager.getConnection();//getTestConnection();
         			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
         			cStmtObject.setString(1,"CignaAddressingDocuments");
         			cStmtObject.setString(2,"CignaAddressingDocuments");
         			for(int i=3;i<intNoOfCursors+3;i++){
         				cStmtObject.registerOutParameter(i,OracleTypes.CURSOR);
         			}//end of for(int i=2;i<intNoOfCursors+2;i++)
         			cStmtObject.execute();
         			for(int index=0;index<intNoOfCursors;index++) {
         				rs[index]= (java.sql.ResultSet) cStmtObject.getObject(index+3);
         				crs[index] = new OracleCachedRowSet();
         				if(rs[index]!=null){
         					crs[index].populate(rs[index]);
         				}//end of if(rs[index]!=null)
         			}//end of for(int index=0;index<intNoOfCursors;index++)
         			return crs;    			
         		}//end of else if(strReportID.equals("CignaAddressingDocuments"))
    		 else if(strReportID.equals("mainInputPricing")) {

                             String strCall = "{CALL "+strProcedureName.trim();
                          
                             System.out.println("strCall="+strCall);
                                            
                                             String strParameters = "(?";

                                             for(int i=0;i<intNoOfCursors;i++){

                                                             strParameters += ",?";

                                             }//end of for(int i=0;i<intNoOfCursors;i++)

                                             strParameters += ")}";

                                             strCall += strParameters;

                                             cachers = new CachedRowSetImpl[intNoOfCursors];

                                             rs = new ResultSet[intNoOfCursors];

                                             conn = ResourceManager.getConnection();//getTestConnection();

                                             cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);

                                             cStmtObject.setString(1,strParameter);

                                             for(int i=2;i<intNoOfCursors+2;i++){

                                                             cStmtObject.registerOutParameter(i,Types.OTHER);

                                             }//end of for(int i=2;i<intNoOfCursors+2;i++)

                                             cStmtObject.execute();

                                             for(int index=0;index<intNoOfCursors;index++) {

                                                             rs[index]= (java.sql.ResultSet) cStmtObject.getObject(index+2);

                                                             cachers[index] = new CachedRowSetImpl();

                                                             if(rs[index]!=null){

                                                            	 cachers[index].populate(rs[index]);

                                                             }//end of if(rs[index]!=null)

                                             }//end of for(int index=0;index<intNoOfCursors;index++)
                                             return cachers; 

             }//end of else if(strReportID.equals("CignaAddressingDocuments"))
    		
    		else {
    			String strCall = "{CALL "+strProcedureName.trim();
    			String strParameters = "(?";
    			for(int i=0;i<intNoOfCursors;i++){
    				strParameters += ",?";
    			}//end of for(int i=0;i<intNoOfCursors;i++)
    			strParameters += ")}";
    			strCall += strParameters;
    			
    			crs = new OracleCachedRowSet[intNoOfCursors];
    			rs = new ResultSet[intNoOfCursors];
    			conn = ResourceManager.getConnection();//getTestConnection();
    			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
    			cStmtObject.setString(1,strParameter);
    			for(int i=2;i<intNoOfCursors+2;i++){
                 cStmtObject.registerOutParameter(i,OracleTypes.CURSOR);
    			}//end of for(int i=2;i<intNoOfCursors+2;i++)
    			cStmtObject.execute();
    			for(int index=0;index<intNoOfCursors;index++) {
    				rs[index]= (java.sql.ResultSet) cStmtObject.getObject(index+2);
    				crs[index] = new OracleCachedRowSet();
    				if(rs[index]!=null){
    					crs[index].populate(rs[index]);
    				}//end of if(rs[index]!=null)
    			}//end of for(int index=0;index<intNoOfCursors;index++)
    			return crs;
    		}//end of else
    	}//end of try        
    	catch (SQLException sqlExp)
    	{
    		throw new TTKException(sqlExp, "tTkReports");
    	}//end of catch (SQLException sqlExp)
    	catch (Exception exp)
    	{
    		throw new TTKException(exp, "tTkReports");
    	}//end of catch (Exception exp)
    	finally
    	{
    		/* Nested Try Catch to ensure resource closure */ 
    		try // First try closing the Statement
    		{
    			try
    			{
    				for(int i=0;i<intNoOfCursors;i++){  
    					if (rs != null) rs[i].close();
        			}//end of for(int i=0;i<intNoOfCursors+1;i++)    				
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Resultset in TTKReportDAOImpl getPolicyList()",sqlExp);
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
    					log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
    					throw new TTKException(sqlExp, "tTkReports");
    				}//end of catch (SQLException sqlExp)
    				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
    				{
    					try
    					{
    						if(conn != null) conn.close();
    					}//end of try
    					catch (SQLException sqlExp)
    					{
    						log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
    						throw new TTKException(sqlExp, "tTkReports");
    					}//end of catch (SQLException sqlExp)
    				}//end of finally Connection Close
    			}//end of try
    		}//end of tryc
    		catch (TTKException exp)
    		{
    			throw new TTKException(exp, "tTkReports");
    		}//end of catch (TTKException exp)
    		finally // Control will reach here in anycase set null to the objects 
    		{
    			for(int i=0;i<intNoOfCursors;i++){  
					rs[i] = null;
    			}//end of for(int i=0;i<intNoOfCursors+1;i++)
    			cStmtObject = null;
    			conn = null;
    		}//end of finally
    	}//end of finally
    }//end of getReport(String strReportID,String strParameter)
    
    /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param alFaxStatusList ArrayList which procedure name which has to be called to get data.
     * @param hMap HashMap which contains the parameter for the procedure.
     * @return ResultSet object's which contains the details of the Reports
     * @exception throws TTKException
     */
    public ResultSet getReport(ArrayList alFaxStatusList) throws TTKException {
    	
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        String strProcedureName="";
        String strReportID = (String)alFaxStatusList.get(0);
        strProcedureName =getProcedureName(strReportID);
        if(strProcedureName==null)
        {
        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
        }//end of if(strProcedureName==null)
        OracleCachedRowSet crs = null;
        try{
            String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?,?)}";
            crs = new OracleCachedRowSet();
            conn = ResourceManager.getConnection();//getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
            cStmtObject.setString(1,(String)alFaxStatusList.get(1));
            cStmtObject.setString(2,(String)alFaxStatusList.get(2));
            cStmtObject.setString(3,(String)alFaxStatusList.get(3));
            cStmtObject.setString(4,(String)alFaxStatusList.get(4));
            if(alFaxStatusList.get(5) != null)
            {
            	cStmtObject.setLong(5,(Long)alFaxStatusList.get(5));
            }//end of if(alFaxStatusList.get(5) != null)
            else
            {
            	cStmtObject.setString(5,null);
            }//end of else            
            cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(6);
            if(rs !=null)
            {
             	crs.populate(rs);
            }//end of if(rs !=null)
            return crs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getPolicyList()",sqlExp);
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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
    }//end of getReport(String strReportID,String strParameter)
    
     /**
     * This method returns the ResultSet, which contains Reports data which are populated from the database
     * @param strReportID String which procedure name which has to be called to get data.
     * @param alCallPendList ArrayList which contains the config param and primary mail list.
     * @exception throws TTKException
     */
    public ResultSet getReport(String strReportID, ArrayList alCallPendList) throws TTKException {
    	
    	System.out.println("strReportID::"+strReportID);
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        String strProcedureName="";
        strProcedureName =getProcedureName(strReportID);
        if(strProcedureName==null)
        {
        	throw new TTKException(new Exception("No procedure is found for this report !"), "tTkReports");
        }//end of if(strProcedureName==null)
        CachedRowSet crs = null;
        try{
        	
        	if(strReportID.equalsIgnoreCase("PatpolHistoryApprej") || strReportID.equalsIgnoreCase("ClmPolicyHistoryApprej"))
        	{
        		
        		   String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?)}";
        		   
                   crs = new OracleCachedRowSet();
                   conn = ResourceManager.getConnection();//getTestConnection();
                   cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
                   cStmtObject.setString(1,(String)alCallPendList.get(0));
                   cStmtObject.setString(2,((String)alCallPendList.get(1)));
                   cStmtObject.registerOutParameter(3,Types.OTHER);
                   cStmtObject.execute();
                   rs = (java.sql.ResultSet) cStmtObject.getObject(3);
                   if(rs !=null)
                   {
                    	crs.populate(rs);
                   }//end of if(rs !=null)
                   return crs;
        	}else if(strReportID.equalsIgnoreCase("ClaimUtilizationRep") || strReportID.equalsIgnoreCase("AuthUtilizationRep") || strReportID.equalsIgnoreCase("TATForClaims")
        			|| strReportID.equalsIgnoreCase("TATForPreAuth") ||  strReportID.equalsIgnoreCase("TATFSCORPOARTE"))//Insurance Login Reports
        	{
     		    String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?,?,?,?)}";
			crs = new OracleCachedRowSet();
                conn = ResourceManager.getConnection();//getTestConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
                cStmtObject.setString(1,(String)alCallPendList.get(0));
                cStmtObject.setString(2,((String)alCallPendList.get(1)));
                cStmtObject.setString(3,(String)alCallPendList.get(2));
                cStmtObject.setString(4,((String)alCallPendList.get(3)));
                cStmtObject.setString(5,(String)alCallPendList.get(4));
                cStmtObject.setString(6,((String)alCallPendList.get(5)));
                cStmtObject.setString(7,((String)alCallPendList.get(6)));
                cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(8);
                if(rs !=null)
                {
                 	crs.populate(rs);
                }//end of if(rs !=null)
                
        	}
			else if("BroClaimUtilizationRep".equals(strReportID)||"BroAuthUtilizationRep".equals(strReportID)
        			||"BroTATForClaims".equals(strReportID)||"BroTATForPreAuth".equals(strReportID)
        			||"BroTATFSCORPOARTE".equals(strReportID)){

     		   String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?,?,?,?)}";
     		   
                crs = new OracleCachedRowSet();
                conn = ResourceManager.getConnection();//getTestConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
                cStmtObject.setString(1,(String)alCallPendList.get(0));
                cStmtObject.setString(2,((String)alCallPendList.get(1)));
                cStmtObject.setString(3,(String)alCallPendList.get(2));
                cStmtObject.setString(4,((String)alCallPendList.get(3)));
                cStmtObject.setString(5,(String)alCallPendList.get(4));
                cStmtObject.setString(6,((String)alCallPendList.get(5)));
                cStmtObject.setString(7,((String)alCallPendList.get(6)));
                cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(8);
                if(rs !=null)
                {
                 	crs.populate(rs);
                }//end of if(rs !=null)
                return crs;
        	}
        	else if(strReportID.equalsIgnoreCase("batchInvoice"))
        	{
        		
        		   String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?)}";
        		   System.out.println("strCall==========="+strProcedureName.trim());
                   crs = new CachedRowSetImpl();
                   conn = ResourceManager.getConnection();//getTestConnection();
                   cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
                   cStmtObject.setString(1,(String)alCallPendList.get(0));
                   cStmtObject.setString(2,((String)alCallPendList.get(1)));
                   cStmtObject.registerOutParameter(3,Types.OTHER);
                   cStmtObject.execute();
                   rs = (java.sql.ResultSet) cStmtObject.getObject(3);
                   if(rs !=null)
                   {
                    	crs.populate(rs);
                   }//end of if(rs !=null)
                   return crs;
        	}else if("BroTechnicalResultReport".equals(strReportID)){

     		   String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?,?)}";
     		   
                crs = new OracleCachedRowSet();
                conn = ResourceManager.getConnection();//getTestConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
                cStmtObject.setString(1,(String)alCallPendList.get(0));
                cStmtObject.setString(2,(String)alCallPendList.get(1));
                cStmtObject.setString(3,(String)alCallPendList.get(2));
                cStmtObject.setString(4,(String)alCallPendList.get(3));
                cStmtObject.setString(5,(String)alCallPendList.get(7));
                cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(6);
                if(rs !=null)
                {
                 	crs.populate(rs);
                }//end of if(rs !=null)
                return crs;
        	}else if("BroTATFSINDIVIDUAL".equals(strReportID)){

      		   String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?,?,?)}";
      		   
                 crs = new OracleCachedRowSet();
                 conn = ResourceManager.getConnection();//getTestConnection();
                 cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
                 cStmtObject.setString(1,(String)alCallPendList.get(0));
                 cStmtObject.setString(2,(String)alCallPendList.get(1));
                 cStmtObject.setString(3,(String)alCallPendList.get(2));
                 cStmtObject.setString(4,(String)alCallPendList.get(4));
                 cStmtObject.setString(5,(String)alCallPendList.get(5));
                 cStmtObject.setString(6,(String)alCallPendList.get(6));
                 cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
                 cStmtObject.execute();
                 rs = (java.sql.ResultSet) cStmtObject.getObject(7);
                 if(rs !=null)
                 {
                  	crs.populate(rs);
                 }//end of if(rs !=null)
                 return crs;
         	} 
        	else if(strReportID.equalsIgnoreCase("TATFSINDIVIDUAL"))//Insurance Login Reports
        	{
     		    String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?,?,?)}";
                crs = new OracleCachedRowSet();
                conn = ResourceManager.getConnection();//getTestConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
                cStmtObject.setString(1,(String)alCallPendList.get(0));
                cStmtObject.setString(2,((String)alCallPendList.get(1)));
                cStmtObject.setString(3,(String)alCallPendList.get(2));
                cStmtObject.setString(4,(String)alCallPendList.get(4));
                cStmtObject.setString(5,((String)alCallPendList.get(5)));
                cStmtObject.setString(6,((String)alCallPendList.get(6)));
                cStmtObject.registerOutParameter(7,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(7);
                if(rs !=null)
                {
                 	crs.populate(rs);
                }//end of if(rs !=null)
                return crs;
        	}
        	else if(strReportID.equalsIgnoreCase("TechnicalResultReport"))//Insurance Login Reports
        	{
     		    String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?,?,?,?)}";
                crs = new OracleCachedRowSet();
                conn = ResourceManager.getConnection();//getTestConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
                cStmtObject.setString(1,(String)alCallPendList.get(0));
                cStmtObject.setString(2,((String)alCallPendList.get(1)));
                cStmtObject.setString(3,(String)alCallPendList.get(2));
                cStmtObject.setString(4,((String)alCallPendList.get(3)));
                cStmtObject.setString(5,(String)alCallPendList.get(4));
                cStmtObject.registerOutParameter(6,OracleTypes.CURSOR);
                cStmtObject.execute();
                rs = (java.sql.ResultSet) cStmtObject.getObject(6);
                if(rs !=null)
                {
                 	crs.populate(rs);
                }//end of if(rs !=null)
                return crs;
        	}
        	else
        		{
        		String strCall = "{CALL "+strProcedureName.trim()+"(?,?,?)}";
            crs = new OracleCachedRowSet();
            conn = ResourceManager.getConnection();//getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCall);
            cStmtObject.setString(1,(String)alCallPendList.get(0));
            cStmtObject.setString(2,((String)alCallPendList.get(1)));
            cStmtObject.registerOutParameter(3,OracleTypes.CURSOR);
            cStmtObject.execute();
            rs = (java.sql.ResultSet) cStmtObject.getObject(3);
            if(rs !=null)
            {
             	crs.populate(rs);
            }//end of if(rs !=null)
            return crs;
        		}
        	return crs;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getPolicyList()",sqlExp);
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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
    }//end of getReport(String strReportID,String strParameter)
    

    
public ArrayList getPreAuthUtilizationReport(String parameter,HashMap<String,String> hm) throws TTKException 
{ 	
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ArrayList<ReportVO> alRresultList	=	null;
        try{
        	log.info("DAO MIS REPORTS getPreauthUtilizationReport() start-->"+new Date());
            conn = ResourceManager.getConnection();
            if("ERP".equals(parameter)) 
            {	
             log.info("DAO ENROLLMENT MIS REPORT START-->"+new Date());        
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strERPAUTHUTILIZATION);  
            cStmtObject.setString(1, hm.get("strPayer"));		// authority
            cStmtObject.setString(2, hm.get("strCorps")); // group name
            cStmtObject.setString(3, hm.get("insCompanyCode"));//ERP
            cStmtObject.setString(4, hm.get("sAgentCode"));
            cStmtObject.setString(5, hm.get("sGroupPolicyNo"));
            cStmtObject.setString(6, hm.get("csStartDate"));
            cStmtObject.setString(7, hm.get("strSdate"));
            cStmtObject.setString(8, hm.get("strEdate"));
            cStmtObject.setString(9, hm.get("strProvider"));
            if("ERP".equals(parameter)) 
            	cStmtObject.setString(10, hm.get("eType"));
            else
            	cStmtObject.setString(10, hm.get("eType"));
            cStmtObject.setString(11, hm.get("sStatus"));//ERP
            cStmtObject.setString(12, hm.get("sType"));
            cStmtObject.registerOutParameter(13,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(13);
            
            if("CSM".equals(parameter) || "PRM".equals(parameter))
            {
            	 rs1 = (java.sql.ResultSet)cStmtObject.getObject(2);
            }
            	
            ReportVO reportVO = null;
            if(rs != null){
            	alRresultList	=	new ArrayList<ReportVO>();
            	//GEETING META-DATA FOR COLUMN HEADS
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
            	
            	
            	//START OF SECOND RESULT SET
            	if(rs1 != null){
            		//GEETING META DATA FOR COLUMN HEADS
                    metaData	=	rs1.getMetaData();
                    colmCount	=	metaData.getColumnCount();
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
            		while (rs1.next()) {
                		alResult = new ArrayList<String>();
                    	reportVO = new ReportVO();
                    	
                    	for(int l=1;l<colmCount;l++)
                    		alResult.add(rs1.getString(l)==null?"":rs1.getString(l));
                    	
                    	reportVO.setAlColumns(alResult);
                        alRresultList.add(reportVO);
                     log.info("ENROLLMENT MIS REPORT END-->"+new Date());
            		}//end of while(rs1.next())
            		
            	}//end of if(rs1 != null)
            }//end of if(rs != null)
            
           /* return (ArrayList)alRresultList;*/
            }
            
            else if("PRP".equals(parameter))
            {
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPRPAUTHUTILIZATION);  
            	cStmtObject.setString(1, hm.get("strPayer"));
                cStmtObject.setString(2, hm.get("strProvider"));
                cStmtObject.setString(3, hm.get("strCorps"));//ERP
                cStmtObject.setString(4, hm.get("insCompanyCode"));
                cStmtObject.setString(5, hm.get("sAgentCode"));//ERP
                if("ERP".equals(parameter)) 
                 	cStmtObject.setString(6, hm.get("eType"));
                else
                 	cStmtObject.setString(6, hm.get("eType"));
                cStmtObject.setString(7, hm.get("sStatus"));//ERP
                cStmtObject.setString(8, hm.get("sType"));
                cStmtObject.setString(9, hm.get("sGroupPolicyNo"));//ERP
                cStmtObject.setString(10, hm.get("csStartDate"));
                cStmtObject.setString(11, hm.get("strSdate"));//ERP
                cStmtObject.setString(12, hm.get("strEdate"));//ERP
                cStmtObject.registerOutParameter(13,Types.OTHER);
                cStmtObject.execute();
         
                rs = (java.sql.ResultSet)cStmtObject.getObject(13);
                 if("CSM".equals(parameter) || "PRM".equals(parameter))
                 {
                 	 rs1 = (java.sql.ResultSet)cStmtObject.getObject(2);
                 }
                 	
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
                 	
                 	
                 	//START OF SECOND RESULT SET
                 	if(rs1 != null){
                 		//GEETING META DATA FOR COLUMN HEADS
                         metaData	=	rs1.getMetaData();
                         colmCount	=	metaData.getColumnCount();
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
                 		while (rs1.next()) {
                     		alResult = new ArrayList<String>();
                         	reportVO = new ReportVO();
                         	
                         	for(int l=1;l<colmCount;l++)
                         		alResult.add(rs1.getString(l)==null?"":rs1.getString(l));
                         	
                         	reportVO.setAlColumns(alResult);
                             alRresultList.add(reportVO);
                         }//end of while(rs1.next())
                 		
                 	}//end of if(rs1 != null)
                 }//end of if(rs != null)
                 
                /* return (ArrayList)alRresultList;*/
        	}
            
            else if("CLR".equals(parameter))
            {
            	log.info("DAO CLAIM UTILIZATION REPORT START-->"+new Date());
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strCLRAUTHUTILIZATION); 
            	  
            	cStmtObject.setString(1, hm.get("strPayer"));
                cStmtObject.setString(2, hm.get("strProvider"));
            	cStmtObject.setString(3, hm.get("insCompanyCode"));
            	cStmtObject.setString(4, hm.get("strCorps"));
            	cStmtObject.setString(5, hm.get("sGroupPolicyNo"));
            	cStmtObject.setString(6, hm.get("clBenefitTypes"));
            	cStmtObject.setString(7, hm.get("clClaimSource"));
            	cStmtObject.setString(8, hm.get("clProviderCategory"));
            	cStmtObject.setString(9, hm.get("clBatchNo"));
            	cStmtObject.setString(10, hm.get("sType"));	// type of claim
            	cStmtObject.setString(11, hm.get("clPayStatus"));// claim status
            	cStmtObject.setString(12, hm.get("clClaimNo"));
            	cStmtObject.setString(13, hm.get("clRemAdvFname"));
            	cStmtObject.setString(14, hm.get("clPayTransRefNo"));
            	cStmtObject.setString(15, hm.get("sStatus"));// pay status
            	cStmtObject.setString(16, hm.get("clDtCriteria"));
            	cStmtObject.setString(17, hm.get("strSdate"));
                cStmtObject.setString(18, hm.get("strEdate"));
            	cStmtObject.registerOutParameter(19,Types.OTHER);
                cStmtObject.execute();
            
                 rs = (java.sql.ResultSet)cStmtObject.getObject(19);
                 
                 if("CSM".equals(parameter) || "PRM".equals(parameter))
                 {
                 	 rs1 = (java.sql.ResultSet)cStmtObject.getObject(2);
                 }
                 
                 	
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
                 	
                 	
                 	//START OF SECOND RESULT SET
                 	if(rs1 != null){
                 		//GEETING META DATA FOR COLUMN HEADS
                         metaData	=	rs1.getMetaData();
                         colmCount	=	metaData.getColumnCount();
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
                 		while (rs1.next()) {
                     		alResult = new ArrayList<String>();
                         	reportVO = new ReportVO();
                         	
                         	for(int l=1;l<colmCount;l++)
                         		alResult.add(rs1.getString(l)==null?"":rs1.getString(l));
                         	
                         	reportVO.setAlColumns(alResult);
                             alRresultList.add(reportVO);
                         }//end of while(rs1.next())
                 		
                 	}//end of if(rs1 != null)
                 }//end of if(rs != null)
                 
               
            }
            log.info("DAO CLAIM UTILIZATION REPORT END-->"+new Date());
        	log.info("DAO MIS REPORTS getPreauthUtilizationReport() End-->"+new Date());
          return (ArrayList)alRresultList;
        }
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getPolicyList()",sqlExp);
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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
		/*return alRresultList;*/
    }//end of getPreAuthUtilizationReport()
    

/*
 * MIS Finance Reports - Kishor kumar S H
 */

public ArrayList getDetailedReport(String parameter,String repType) throws TTKException {
       log.info("DAO getDetailedReport start-->"+new Date());
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ArrayList<ReportVO> alRresultList	=	null;
        try{
            conn = ResourceManager.getConnection();
            if("detail".equals(repType))
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDETAILEDREPORT);
            else if("pending".equals(repType))
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPENDINGREPORT);
            else if("hospital".equals(repType))
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBANKHOSPITALREPORT);
            else if("policy".equals(repType))
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBANKPOLICYREPORT);
            else if("revenue".equals(repType))
            	cStmtObject=conn.prepareCall(strRevenueInvoiceReport);
          
            else if("EMPANEL".equals(repType))
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strREMITTANCEREPORT);
            else if("FINANCE".equals(repType))
                 cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strREMITTANCEREPORT);
        	cStmtObject.setString(1, parameter);
        	cStmtObject.registerOutParameter(2,Types.OTHER);
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(2);
            
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
            log.info("DAO getDetailedReport end-->"+new Date());
            return (ArrayList)alRresultList;
        }
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getPolicyList()",sqlExp);
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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
    }//end of getDetailedReport(String parameter
    


	/**
	 * @param strReportID
	 * @return
	 */
	private String getProcedureName(String strReportID) {
		
		
		if("TakafulLetter".equals(strReportID))
		{
			return(strTAKAFUL_LETTER);
		}
		else if("mainInputPricing".equals(strReportID))
		{
			return(strPRICING_SUMMARY);
		}

		else if("ViewInputPricing".equals(strReportID))
		{
			return(strPRICING_SUMMARY);
		}

		
		else if(strReportID.equals("PremiumRates"))
		{
			return(strPREMIUM_RATES_SECTION);
		}
		else if(strReportID.equals("BenefitDesignSection"))
		{
			return(strBENEFIT_DESIGN_SECTION);
		}
		else if(strReportID.equals("HealthInsuranceQuote"))
		{
			return(strHEALTH_INSURANCE_QUOTE);
		}
		else if(strReportID.equals("BenefitDetails"))
		{
			return(strHEALTH_INSURANCE_QUOTE);
		}
		else if(strReportID.equals("GeneralTermsAndConditions"))
		{
			return(strHEALTH_INSURANCE_QUOTE);
		}
		else if(strReportID.equals("ClientApproval"))
		{
			return(strHEALTH_INSURANCE_QUOTE);
		}
		else if(strReportID.equals("PreauthLetterFormat"))
		{
			return(strPREAUTH_LETTER_FORMAT);
		}
		else if("ClaimLetterFormat".equals(strReportID))
		{
			return(strCLAIM_LETTER_FORMAT);
		}	
		else if(strReportID.equals("DiagnosisDetails"))
		{
			return(strDIAGNOSIS_DETAILS);
		}
		else if(strReportID.equals("ActivityDetails"))
		{
			return(strACTIVITY_DETAILS);
		}
		else if(strReportID.equals("GenRenMemXLs"))
		{
			return(strGENERATE_RENEWAL_MEMBERS);
		}
		else if(strReportID.equals("AccentureRpt"))
		{
			return(strGET_MEMBERS_ADDED_LIST);
		}else if(strReportID.equals("IBMDeletion"))
		{
			return(strDELETION_CUTOFF); // This procedure is to be changed for IBM
		}else if(strReportID.equals("IBMAdditionMaxRec"))
		{
			return(strADDITION_CUTOFF);
		}else if(strReportID.equals("NewIBMDeletion")){
			return(strNEW_IBM_DELETION_CUTOFF);
		}
		else if(strReportID.equals("IBMGrpPreAuthRpt"))
		{
			return strIBMPREAUTH_REPORT;
		}//else if(strReportID.equals("IBMGrpPreAuthRpt"))
		else if(strReportID.equals("IBMClaimRpt"))
		{
			return strIBMCLAIM_REPORT;
		}//else if(strReportID.equals("IBMClaimRpt"))
		else if(strReportID.equals("ChildBorn"))
		{
			return strIBMCHILDBORN_REPORT;
		}//else if(strReportID.equals("IBMClaimRpt"))
		else if(strReportID.equals("Reoptin"))
		{
			return strIBMREPOTIN_REPORT;
		}//else if(strReportID.equals("Reoptin"))
		else if(strReportID.equals("MontlyRecon"))
		{
			return strIBMRECON_REPORT;
		}//else if(strReportID.equals("MontlyRecon"))
		else if(strReportID.equals("DailyReport"))
		{
			return strIBMDAILY_OPTOUT_REPORT;
		}//else if(strReportID.equals("DailyReport"))
		else if(strReportID.equals("DailyAdditionalCoverage"))
		{
			return strIBMDAILY_ADDITTIONAL_COVERAGE_REPORT;
		}//else if(strReportID.equals("DailyReport"))
		else if(strReportID.equals("DailyParentalCoverage"))
		{
			return strIBMDAILY_PARENTAL_COVERAGE_REPORT;
		}//else if(strReportID.equals("DailyReport"))
		//Added for KOC-1255
		else if(strReportID.equals("Accenture_Report_MC"))
		{
			return strACCENTURE_DASHBOARD_MC_REPORT;
		}
		else if(strReportID.equals("Accenture_Report_CC"))
		{
			return strACCENTURE_DASHBOARD_CC_REPORT;
		}
		else if(strReportID.equals("Accenture_Report_SMC"))
		{
			return strACCENTURE_DASHBOARD_SMC_REPORT;
		}
		else if(strReportID.equals("Accenture_Report_SCC"))
		{
			return strACCENTURE_DASHBOARD_SCC_REPORT;
		}
		else if(strReportID.equals("Accenture_Report_PRE"))
		{
			return strACCENTURE_DASHBOARD_PREAUTH_REPORT;
		}
		else if(strReportID.equals("Accenture_Report_CALL"))
		{
			return strACCENTURE_DASHBOARD_CALLCENTER_REPORT;
		}	
		//Ended For KOC-1255
		//Ended.
		if(strReportID.equals("EnrDaiImg"))
		{
			return(strIMAGE_NOT_UPLOADED);
		}//end of if(strReportID.equals("EnrDaiImg"))
		if(strReportID.equals("EnrDaiPolAwtWork"))
		{
			return(strPOLICIES_AWAITING_WORKFLOW);
		}//end of if(strReportID.equals("EnrDaiPolAwtWork"))
		if(strReportID.equals("EnrDaiCardPrint"))
		{
			return(strCARDS_PRINTED);
		}//end of if(strReportID.equals("EnrDaiCardPrint"))
		if(strReportID.equals("EnrDaiCardsNotDelr"))
		{
			return(strCARDS_NOT_DELIVERED);
		}//end of if(strReportID.equals("EnrDaiCardsNotDelr"))
		if(strReportID.equals("EnrDaiGrpRen"))
		{
			return(strGROUPS_RENEWAL);
		}//end of if(strReportID.equals("EnrDaiGrpRen"))
		else if(strReportID.equals("EnrDaiTimeTakToRecvPol"))
		{
			return(strTIMETAKEN_TO_RECEIVE_POLICY);
		}//end of else if(strReportID.equals("EnrDaiTimeTakToRecvPol"))
		else if(strReportID.equals("EnrDaiDispRpt"))
		{
			return(strDISPATCH_REPORT);
		}//end of else if(strReportID.equals("EnrDaiDispRpt"))
		else if(strReportID.equals("EnrDaiEndors"))
		{
			return(strENDORSEMENTS);
		}//end of else if(strReportID.equals("EnrDaiEndors"))
		else if(strReportID.equals("EnrDaiPolProcTime"))
		{
			return(strTIMETAKEN_TO_PROCESS_POLICY);
		}//end of else if(strReportID.equals("EnrDaiPolProcTime"))
		else if(strReportID.equals("SupDispRpt"))
		{
			return(strDISPATCH_LIST);
		}//end of else if(strReportID.equals("SupDispRpt"))
		else if(strReportID.equals("SoftCopyUpload"))
		{
			return(strERROR_DETAILS);
		}//end of else if(strReportID.equals("SoftCopyUpload"))
		else if(strReportID.equals("SoftCopyUploadError"))
		{
			return(StrSOFTCOPYUPLOAD_ERROR_DETAILS);
		}//end of else if(strReportID.equals("SoftCopyUploadError"))
		else if(strReportID.equals("EnrDaiBOI"))
		{
			return(strBOI_REPORT);
		}//end of else if(strReportID.equals("EnrDaiBOI"))
		else if(strReportID.equals("EndrImpactReport"))
		{
			return(strIMPACT_REPORT);
		}//end of else if(strReportID.equals("EndrImpactReport"))
		else if(strReportID.equals("EFTAcceAnne"))
		{
			return(strEFT_COV_LETTER_ANNEXURE);
		}//end of else if(strReportID.equals("EFTAcceAnne"))
		else if(strReportID.equals("EFTFinBat"))
		{
			return(strEFT_COVERING_LETTER);
		}//end of else if(strReportID.equals("EFTAcceAnne"))
		else if(strReportID.equals("PremChangeRpt"))
		{
			return(strPREMIUM_CHANGE_REPORT);
		}//end of else if(strReportID.equals("PremChangeRpt"))
		else if(strReportID.equals("CodeRejClus"))
		{
			return(strGET_REJECTION_INFO);
		}//end of else if(strReportID.equals("CodeRejClus"))
		
		//pre-auth reports
		else if(strReportID.equals("PreAuthRpt"))
		{
			return(strPRE_AUTH_RPT);
		}//end of else if(strReportID.equals("PreAuthRpt"))
		else if(strReportID.equals("PreAuthManul"))
		{
			return(strMANUAL_PREATH_RPT);
		}//end of else if(strReportID.equals("PreAuthManul"))
		//claims reports
		else if(strReportID.equals("ClaimsManul"))
		{
			return(strMANUAL_CLAIM_RPT);
		}//end of else if(strReportID.equals("ClaimsManul"))
		
		//self fund
		else if(strReportID.equals("PreAuthDiagLetter"))
		{
			return(strPRE_AUTH_LETTER);
		}//end of else if(strReportID.equals("PreautViewHis"))
		
		else if(strReportID.equals("DiagonisTariffDetails"))
		{
			return(strDiagonisTariffHistory);
		}//end of else if(strReportID.equals("PreautViewHis"))
		else if(strReportID.equals("ProcessTimeAnalysis"))
		{
			return(strPROCESS_TIME_ANALYSIS);
		}//end of else if(strReportID.equals("ProcessTimeAnalysis"))
		//KOC 1339 for mail
		//KOC 1353 for payment report
		else if(strReportID.equals("INSPaymentReport"))
		{
			return(strONLINE_PAYMENT_INS_REPORT);
		}//end of else if(strReportID.equals("ClaimsIntimations"))
		else if(strReportID.equals("PreauthReport"))//KOCPreauthreport 
		{
			return(strINS_PREAUTH_CIGNA_REPORT);
		}//end of else if(strReportID.equals("PreauthReport"))
		else if(strReportID.equals("ClaimReport"))//KOCPreauthreport 
		{
			return(strINS_CLAIM_CIGNA_REPORT);
		}//end of else if(strReportID.equals("ClaimReport"))
		else if(strReportID.equals("PolicyReport"))//KOCPreauthreport 
		{
			return(strINS_POLICY_CIGNA_REPORT);
		}//end of else if(strReportID.equals("PolicyReport"))
		//KOC 1353 for payment report
		else if(strReportID.equals("ClaimsIntimation"))
		{
			return(stCLAIM_INTIMATIN_RPT);
		}//end of else if(strReportID.equals("ClaimsIntimation"))
		//KOC 1339 for mail
		else if(strReportID.equals("ClosureFormat"))
		{
			return (strCLOSURE_LETTER);
		}//end of else if(strReportID.equals("ClosureFormat"))
		//added for Mail-SMS for Cigna
		else if(strReportID.equals("CignaClaimClosureLtrAdvisor"))
		{
			return(strCLAIM_CLOSURE_LTR_ADVISOR);
		}	
		else if(strReportID.equals("CignaClaimSubAppvlLtrAdv"))
		{
		   return(strCLAIM_SUB_APP_LTR_ADV); 
		}		
		else if(strReportID.equals("CignaClaimRejectLtrPO"))
		{
			return(strCLAIM_REJECT_LTR_PO);
		}
		else if(strReportID.equals("CignaClaimAppvlLtrAdv"))
		{
			return(strCLAIM_APP_LTR_ADV); 
		}
		else if(strReportID.equals("ClaimSettlementLtrPO"))
		{
			return(strCLAIM_SETTLEMENT_LTR_PO); 
		}
		//ended
		else if(strReportID.equals("RejectionLetter"))
		{
			return (strRejectionLetter);
		}//end of else if(strReportID.equals("RejectionLetter"))
		else if(strReportID.equals("ClaimsOutstandingLiability"))
		{
			return(strClaims_OUTSTANDING_LIABILITY);
		}//end of else if(strReportID.equals("ClaimsOutstandingLiability"))
		else if(strReportID.equals("MedicalOpinion"))
		{
			return(strMedicalOpinionSheet);
		}//end of else if(strReportID.equals("MedicalOpinion"))
		else if(strReportID.equals("PreAuthClmDetail"))
		{
			return(strPreAuthClmDtlRpt);
		}//end of else if(strReportID.equals("PreAuthClmDetail"))
		
		//finance reports
		else if(strReportID.equals("FinDaiBat"))
		{
			return(strFinanceBatch);
		}//end of else if(strReportID.equals("FinDaiBat"))
		else if(strReportID.equals("FinDaiCovLetter"))
		{
			return(strCOVERING_LETTER);
		}//end of else if(strReportID.equals("FinDaiCovLetter"))
		else if(strReportID.equals("FinBanRec"))
		{
			return(strRECONCILATION_REPORT_TEST);
		}//end of else if(strReportID.equals("FinBanRec"))
		else if(strReportID.equals("FinBanRecBankTran"))
		{
			return(strBANK_TRANSACTION_REPORT);
		}//end of else if(strReportID.equals("FinBanRecBankTran"))
		else if(strReportID.equals("FinBanRecFloatTran"))
		{
			return(strFLOAT_TRANSACTION_REPORT);
		}//end of else if(strReportID.equals("FinBanRecFloatTran"))
		else if(strReportID.equals("FinDetRpt"))
		{
			return(strCLAIMS_DETAILS_REPORT);
		}//end of else if(strReportID.equals("FinDetRpt"))
		else if(strReportID.equals("CitiFinDetRpt"))
		{
			return(strCITI_CLAIMS_DETAILS_REPORT);
		}//end of else if(strReportID.equals("CitiFinDetRpt"))
		else if(strReportID.equals("FinDetEFTRpt"))
		{
			return(strEFT_CLAIMS_DETAILS_REPORT);
		}//end of else if(strReportID.equals("FinDetEFTRpt"))	
		else if(strReportID.equals("FinClmPenEFTRpt"))
		{
			return(strEFT_CLAIMS_PENDING_REPORT);
		}//end of else if(strReportID.equals("FinClmPenEFTRpt"))
		else if(strReportID.equals("FinBatChck"))
		{
			return(strMANUAL_BATCH_LIST);
		}//end of else if(strReportID.equals("FinBatChck"))
		else if(strReportID.equals("FltStmtRpt"))
		{
			return(strFLOAT_STATEMENT_REPORT);
		}//end of else if(strReportID.equals("FltStmtRpt"))
		else if(strReportID.equals("FinPenRpt"))
		{
			return(strCLAIMS_PENDING_REPORT);
		}//end of else if(strReportID.equals("FinPenRpt"))
		// Changes added for Debit Note CR KOC1163
		/*else if(strReportID.equals("IbmDakshRpt"))
		{
			return(strDEBITNOTE_IBM_DAKSH_REPORT);
		}//end of else if(strReportID.equals("FinPenRpt"))
*/		// End changes added for Debit Note CR KOC1163
		else if(strReportID.equals("FinTPAComm"))
		{
			return(strTPA_COMMISSION_REPORT);
		}//end of else if(strReportID.equals("FinTPAComm"))
		/*else if(strReportID.equals("76ColPend"))
		{
			return(strSEVENTY_SIX_COLUMN_REPORT);
		}//end of else if(strReportID.equals("76ColPend"))
*/		else if(strReportID.equals("TPACommissionRpt"))
		{
			return(strTPA_COMISSION_REPORT_SHEETS);
		}//end of else if(strReportID.equals("TPACommissionRpt"))
		else if(strReportID.equals("FurGenRpt"))
		{
			return(strFUT_GEN_CLAIMS_DETAILS_REPORT);
		}//end of else if(strReportID.equals("FurGenRpt"))
		else if(strReportID.equals("FinMediClaimCom"))
		{
			return(strFIN_CLAIM_COMPUTATION);
		}//end of else if(strReportID.equals("FinMediClaimCom"))
		else if(strReportID.equals("FinMediClaimComLineItems"))
		{
			return(strFIN_CLAIM_COMPUTATION_LINEITEMS);
		}//end of else if(strReportID.equals("FinMediClaimComLineItems"))
		else if(strReportID.equals("DailyTransferPending"))
		{
			return(strFIN_SELECT_PENDING_DREMIT_RPT);
		}//end of else if(strReportID.equals("DailyTransferPending"))
		else if(strReportID.equals("DailyTransferComplete"))
		{
			return(strFIN_SELECT_SETTLED_DREMIT_RPT);
		}//end of else if(strReportID.equals("DailyTransferComplete"))
		
		
		
		else if(strReportID.equals("MemberClaimLetterFormat"))
		{
			return(strCLAIM_SELECT_REIM_CLAIM_LETTER);
		}//end of else if(strReportID.equals("MemberClaimLetterFormat")
		else if(strReportID.equals("MemberDiagnosisDetails")){
			return(strMEMBER_DIAGNOSIS_DETAILS);
		}//end of else if(strReportID.equals("MemberActivityDetails")
	
		//Coding Reports
		else if(strReportID.equals("PenCaseRpt"))
		{
			return(strPendingCases_Report);
		}//end of else if(strReportID.equals("PenCaseRpt"))
		else if(strReportID.equals("CCSRpt"))
		{
			return(strCodeCleanupStatusReport);
		}//end of else if(strReportID.equals("CCSRpt"))
				
		//call Center Reports
		else if(strReportID.equals("CardViewHis"))
		{
			return(strCARD_HISTORY);
		}//end of else if(strReportID.equals("CardViewHis"))
		else if(strReportID.equals("PreautViewHis"))
		{
			return(strPRE_AUTH_HISTORY);
		}//end of else if(strReportID.equals("PreautViewHis"))
		else if(strReportID.equals("ClaimViewHis"))
		{
			return(strCLAIM_HISTORY);
		}//end of else if(strReportID.equals("ClaimViewHis"))
		else if(strReportID.equals("PolViewHis"))
		{
			return(strPOLICY_HISTORY);
		}//end of else if(strReportID.equals("PolViewHis"))
		else if(strReportID.equals("PolicyHistorySub"))
		{
			return(strPOLICY_HISTORY_SUB);
		}//end of else if(strReportID.equals("PolicyHistorySub"))
		else if(strReportID.equals("CallViewHis"))
		{
			return(strCALL_HISTORY_DETAIL);
		}//end of else if(strReportID.equals("CallViewHis"))
		else if(strReportID.equals("CallViewHisSub"))
		{
			return(strCALL_HISTORY_LIST);
		}//end of else if(strReportID.equals("CallViewHisSub"))
		else if(strReportID.equals("SupCardLb"))
		{
			return(strGENERATE_CARD_LIST);
		}//end of else if(strReportID.equals("SupCardLb"))
		else if(strReportID.equals("CallHisAssDet"))
		{
			return(strCALL_HISTORY_ASSOCIATE_DETAIL);
		}//end of else if(strReportID.equals("CallHisAssDet"))
		else if(strReportID.equals("CallClmNotDet"))
		{
			return(strCLAIM_NOTIFICATIONS_DETAIL);
		}//end of else if(strReportID.equals("CallClmNotDet"))
		else if(strReportID.equals("PreAuthHistoryList"))
		{
			return (strPRE_AUTH_HIST_SHORTFALL_LIST);
		}//end of else if(strReportID.equals("PreAuthHistoryList"))
		else if(strReportID.equals("DisallowedBillList"))
		{
			return (strDISALLOWED_BILL_LIST);
		}//end of else if(strReportID.equals("DisallowedBillList"))
		else if(strReportID.equals("DisallowedBillList1"))
		{
			return (strDISALLOWED_BILL_LIST);
		}//end of else if(strReportID.equals("DisallowedBillList1"))
		else if(strReportID.equals("ShortfallQuestions"))
		{
			return (strPRE_AUTH_HISTORY_QUESTIONS);
		}//end of else if(strReportID.equals("ShortfallQuestions"))
		else if(strReportID.equals("PreAuthHistoryInvestigationList"))
		{
			return (strPRE_AUTH_HISTORY_INVEST_LIST);
		}//end of else if(strReportID.equals("PreAuthHistoryInvestigationList"))
		else if(strReportID.equals("InvestigationDetails"))
		{
			return (strINVESTIGATION_DETAIL);
		}//end of else if(strReportID.equals("InvestigationDetails"))
		else if(strReportID.equals("ClaimHistoryList"))
		{
			return(strPRE_AUTH_HIST_SHORTFALL_LIST);
		}//end of else if(strReportID.equals("ClaimHistoryList"))
		else if(strReportID.equals("ClaimHistoryInvestigationList"))
		{
			return(strPRE_AUTH_HISTORY_INVEST_LIST);
		}//end of else if(strReportID.equals("ClaimHistoryInvestigationList"))
		else if(strReportID.equals("BufferList"))
		{
			return(strPRE_CLM_HIST_BUFFER_LIST);
		}//end of else if(strReportID.equals("BufferList"))
		else if(strReportID.equals("BufferDetails"))
		{
			return(strPRE_CLM_HIST_BUFFER_DETAIL);
		}//end of else if(strReportID.equals("BufferDetails"))
		else if(strReportID.equals("EndorsementList"))
		{
			return(strENDORSEMENT_LIST);
		}//end of else if(strReportID.equals("EndorsementList"))
			
		//other reports
		else if(strReportID.equals("CourierDtl"))
		{
			return(strCOURIER_DETAILS);
		}//end of else if(strReportID.equals("CourierDtl"))
		else if(strReportID.equals("PrintCheque"))
		{
			return(strPRINT_CHEQUE);
		}//end of else if(strReportID.equals("PrintCheque"))
		else if(strReportID.equals("Shortfall"))
		{
			return(strSHORTFALL_GENERATE_LETTER_claims);
		}//end of else if(strReportID.equals("Shortfall"))
		//Shortfall CR KOC1179
		else if(strReportID.equals("AddressingShortfall"))
		{
			return(strSHORTFALL_GENERATE_SHORTFALL_LETTER_CLAIMS);
		}//end of else if(strReportID.equals("AddressingShortfall"))
		
		//added for Mail-SMS for Cigna
		else if(strReportID.equals("CignaShortfall"))
		{
			return(strCIGNA_SHORTFALL_GENERATE_LETTER_claims);
		}//end of else if(strReportID.equals("Shortfall"))
		//added for cignaclaimHistoryreport
		else if(strReportID.equals("CignaClaimShortfall"))
		{
			return(strCIGNA_SHORTFALL_CLAIM_HISTORY);
		}
		//Shortfall CR KOC1179
		else if(strReportID.equals("AddressingShortfallRequests"))
		{
			return(strSHORTFALL_GENERATE_SHORTFALL_REQUEST_LETTER_CLAIMS);
		}//end of else if(strReportID.equals("AddressingShortfallRequests"))
		
		//added for Mail-SMS Cigna Template
		else if(strReportID.equals("CignaAddressingShortfallRequests"))
		{
			return (strCIGNA_SHORTFALL_GENERATE_SHORTFALL_REQUEST_LETTER_CLAIMS);
		}	
		// Shortfall CR KOC1179
		else if(strReportID.equals("AddressingDocuments"))
		{
			return(strGENERATE_SHORTFALL_SCHEDULER_REQUEST);
		}//end of else if(strReportID.equals("AddressingDocuments"))
		//added for Cigna-mail SMS 
		else if(strReportID.equals("CignaAddressingDocuments"))
		{
			return(strGENERATE_CIGNA_SHORTFALL_SCHEDULER_REQUEST);
		}//end of else if(strReportID.equals("AddressingDocuments"))
		
		else if(strReportID.equals("CignaSubAddressingShortfallRequests"))
		{
			return (strCIGNA_SUBREPORT_SHORTFALL_GENERATE_SHORTFALL_REQUEST_LETTER_CLAIMS);
		}
		
		// Shortfall CR KOC1179 Claim Shortfall Summary Report
		else if(strReportID.equals("ClaimShortfallSummary"))
		{
			
			return(strCLAIMS_SHORTFALL_SUMMARY_REPORT);
		}//end of else if(strReportID.equals("ClaimShortfallSummary"))
		else if(strReportID.equals("ShortfallIns"))
		{
			return(strSHORTFALL_GENERATE_LETTER_preauth);
		}//end of else if(strReportID.equals("ShortfallIns"))
		else if(strReportID.equals("ShortfallMid"))
		{
			return(strSHORTFALL_GENERATE_LETTER_preauth);
		}//end of else if(strReportID.equals("ShortfallMid"))
		else if(strReportID.equals("ShortfallINM"))
		{
			return(strSHORTFALL_GENERATE_LETTER_preauth);
		}//end of else if(strReportID.equals("ShortfallINM"))
		else if(strReportID.equals("AuthLetter"))
		{
			return(strPRE_AUTH_GENERATE_LETTER);
		}//end of else if(strReportID.equals("AuthLetter"))
		else if(strReportID.equals("CitiAuthLetter"))
		{
			return(strPRE_AUTH_CITI_GENERATE_LETTER);
		}//end of else if(strReportID.equals("CitiAuthLetter"))		
		else if(strReportID.equals("DischargeVoucher"))
		{
			return(strVOUCHER_LIST);
		}//end of else if(strReportID.equals("DischargeVoucher"))
		else if(strReportID.equals("MediClaimCom"))
		{
			return(strCLAIM_COMPUTATION);
		}//end of else if(strReportID.equals("MediClaimCom"))
		else if(strReportID.equals("MediClaimComLineItems"))
		{
			return(strCLAIM_COMPUTATION_LINEITEMS);
		}//end of else if(strReportID.equals("MediClaimComLineItems"))
		else if(strReportID.equals("ClaimsACK"))
		{
			return(strPRINT_ACKNOWLEDGEMENT);
		}//end of else if(strReportID.equals("ClaimsACK"))
		else if(strReportID.equals("ClaimsACKSub"))
		{
			return(strPRINT_ACK_CHECK_LIST);
		}//end of else if(strReportID.equals("ClaimsACKSub"))
		else if(strReportID.equals("CardPrint"))
		{
			return(strSELECT_DATA_FOR_CARD_PRINT);
		}//end of else if(strReportID.equals("CardPrint"))
		
		else if(strReportID.equals("CardPrint1"))
		{
			return(strSELECT_DATA_FOR_CARD_PRINTInd);
		}//end of else if(strReportID.equals("CardPrint"))
		
		
//		Online Reports
		else if(strReportID.equals("EmpCredential"))
		{
			return(strEMP_CRDN_RPT_FOR_HR);
		}//end of else if(strReportID.equals("EmpCredential"))
		else if(strReportID.equals("ClmRegd"))
		{
			return(strCLAIMS_REGISTERED);
		}//end of else if(strReportID.equals("ClmRegd"))
		else if(strReportID.equals("BiilsPend"))
		{
			return(strBILLS_PENDING);
		}//end of else if(strReportID.equals("BiilsPend"))
		else if(strReportID.equals("ListEmpDepPeriod"))
		{
			return(strLIST_OF_EMP_AND_DEPENDENTS);
		}//end of else if(strReportID.equals("ListEmpDepPeriod"))
		//Summary CR KOC1224
		else if(strReportID.equals("OnlineSummaryRpt"))
		{
			return(strONLINE_SUMMARY_REPORT);
		}//end of else if(strReportID.equals("OnlineSummaryRpt"))
		else if(strReportID.equals("ListEmpDepTill"))
		{
			return(strLIST_OF_EMP_AND_DEPENDENTS);
		}//end of else if(strReportID.equals("ListEmpDepTill"))
		else if(strReportID.equals("OnlinePreAuthRpt"))
		{
			return(strINS_PREAUTH_REPORT);
		}//end of else if(strReportID.equals("OnlinePreAuthRpt"))
		
		//added new for kocbroker
		
		else if(strReportID.equals("DashBoardRpt"))
		{
			return(strDashBoard_REPORT);
		}//end of else if(strReportID.equals("DashBoard"))
		else if(strReportID.equals("Dashboardsub"))
		{
			return(strDashBoardSub_REPORT);
		}//end of else if(strReportID.equals("DashBoard"))
		
	
		//On select IDs kocbroker
		else if(strReportID.equals("ReportOnlinePreAuthBroRpt"))
		{
			return(strBRO_PREAUTH_REPORT);
		}//end of else if(strReportID.equals("ReportOnlinePreAuthBroRpt"))
		else if(strReportID.equals("ReportPreAuthBroSummary"))
		{
			return(strBRO_PREAUTH_SUMMARY_RPT);
		}//end of else if(strReportID.equals("ReportPreAuthBroSummary"))
		else if(strReportID.equals("ReportClmRegBroSummary"))
		{
			return(strBRO_CLM_REGISTER_RPT);
		}//end of else if(strReportID.equals("ReportClmRegBroSummary"))	
		else if(strReportID.equals("ReportClmRegBroDetail"))
		{
			return(strBRO_CLM_REGISTER_DTL_RPT);
		}//end of else if(strReportID.equals("ReportClmRegBroDetail"))	
		else if(strReportID.equals("OnlinePreAuthBroRpt"))
		{
			return(strBRO_PREAUTH_REPORT);
		}//end of else if(strReportID.equals("OnlinePreAuthBroRpt"))
		else if(strReportID.equals("PreAuthBroSummary"))
		{
			return(strBRO_PREAUTH_SUMMARY_RPT);
		}//end of else if(strReportID.equals("PreAuthBroSummary"))
		else if(strReportID.equals("ClmRegBroSummary"))
		{
			return(strBRO_CLM_REGISTER_RPT);
		}//end of else if(strReportID.equals("ClmRegBroSummary"))
		else if(strReportID.equals("ClmRegBroDetail"))
		{
			return(strBRO_CLM_REGISTER_DTL_RPT);
		}//end of else if(strReportID.equals("ClmRegBroDetail"))	
		
		//added new for kocbroker
		else if(strReportID.equals("ListBroEmpDepPeriod"))
		{
			return(strBRO_Emp_Dep_REPORT);
		}//end of else if(strReportID.equals("ListBroEmpDepPeriod"))
		else if(strReportID.equals("ReportListBroEmpDepPeriod"))
		{
			return(strBRO_Emp_Dep_REPORT);
		}//end of else if(strReportID.equals("ReportListBroEmpDepPeriod"))
		
		//End of broker reports
		
		else if(strReportID.equals("ClmRegSummary"))
		{
			return(strINS_CLM_REGISTER_RPT);
		}//end of else if(strReportID.equals("ClmRegSummary"))		
		else if(strReportID.equals("ClmRegDetail"))
		{
			return(strINS_CLM_REGISTER_DTL_RPT);
		}//end of else if(strReportID.equals("ClmRegDetail"))
		//KOC 1339 for mail
		
		else if(strReportID.equals("ClaimsIntimations"))
		{
			return(strONLINE_CLM_INTIMATION);
		}//end of else if(strReportID.equals("ClaimsIntimations"))
		
		//KOC 1339 for mail
		else if(strReportID.equals("CheqCovrLett"))
		{
			return(strCheque_Covering_Letter);
		}//end of else if(strReportID.equals("CheqCovrLett"))
		else if(strReportID.equals("FinAddreLebel"))
		{
			return(strAddress_Label);
		}//end of else if(strReportID.equals("FinAddreLebel"))
		
//		web reports
		else if(strReportID.equals("GenWebHospXLs"))
		{
			return(strWEBLOGIN_HOSPITAL_LIST);
		}//end of else if(strReportID.equals("GenWebHospXLs"))
		else if(strReportID.equals("GenFaxStatRpt"))
		{
			return(strFAX_STATUS_RPT);
		}//end of else if(strReportID.equals("GenFaxStatRpt"))
		else if(strReportID.equals("CallPenRpt"))
		{
			return(strPROC_GEN_CALL_PENDING_REPORT);
		}//end of else if(strReportID.equals("CallPenRpt"))		
		else if(strReportID.equals("CallPenByBranRpt"))
		{
			return(strGET_CALLPENDING_BRANCH_REPORT);
		}//end of else if(strReportID.equals("CallPenByBranRpt"))
//		citi bank reports
		else if(strReportID.equals("CitiSoftCpyUpl"))
		{
			return(strSOFT_COPY_UPLOAD_REPORT);
		}//end of else if(strReportID.equals("CitiSoftCpyUpl"))		
		else if(strReportID.equals("CitiSoftCpyCncl"))
		{
			return(strSOFT_COPY_CANCELLATION_REPORT);
		}//end of else if(strReportID.equals("CitiSoftCpyCncl"))
		else if(strReportID.equals("CitiSoftCpyNtCncl"))
		{
			return(strNOT_CANCELLED_REPORT);
		}//end of else if(strReportID.equals("CitiSoftCpyNtCncl"))	
		//IOB Report
		else if(strReportID.equals("IOBBatRpt"))
		{
			return(strIOB_DETAIL_REPORT);
		}//end of else if(strReportID.equals("IOBBatRpt"))
		else if(strReportID.equals("HDFCCert"))
		{
			return(strHDFC_CERTIFICATE);
		}//end of else if(strReportID.equals("HDFCCert"))	
		else if(strReportID.equals("HDFCCertMem"))
		{
			return(strHDFC_CERTIFICATE_MEMBER);
		}//end of else if(strReportID.equals("HDFCCertMem"))
		else if(strReportID.equals("CallCoverList"))
		{
			return(strCALL_COVERAGE_LIST);
		}//end of else if(strReportID.equals("ClaAssDocList"))
		else if(strReportID.equals("ClaAssDocList"))
		{
			return(strCALL_CLAUSE_ASSOC_DOC_LIST);
		}//end of else if(strReportID.equals("ClaAssDocList"))
		else if(strReportID.equals("CallHosList"))
		{
			return(strCALL_HOSPITAL_LIST);
		}//end of else if(strReportID.equals("CallHosList"))
		else if(strReportID.equals("CallPendMRRpt"))
		{
			return(strPROC_GEN_MRCLM_PENDING);
		}//end of else if(strReportID.equals("CallPendMRRpt"))
		else if(strReportID.equals("CallPendMRShrtfallRpt"))
		{
			return(strPROC_GEN_MRCLM_PENDING_SRTFLL);
		}//end of else if(strReportID.equals("CallPendMRShrtfallRpt"))
		else if(strReportID.equals("TDSRemittanceRpt"))
		{
			return(strTDS_DETAIL_REPORT);
		}//end of else if(strReportID.equals("TDSRemittanceRpt"))	
		else if(strReportID.equals("FamiliesWOSI"))
		{
			return(strSELECT_EMP_WITHOUT_SUM_LIST);
		}//end of else if(strReportID.equals("FamiliesWOSI"))
		else if(strReportID.equals("MiscePreAuthsandClaims"))
		{
			return(strSELECT_EMP_WITHOUT_SUM_LIST);
		}//end of else if(strReportID.equals("FamiliesWOSI"))
		else if(strReportID.equals("MisceBufferUtil"))
		{
			return(strSELECT_EMP_WITHOUT_SUM_LIST);
		}//end of else if(strReportID.equals("FamiliesWOSI"))
		else if(strReportID.equals("MisceSIUtil"))
		{
			return(strSELECT_EMP_WITHOUT_SUM_LIST);
		}//end of else if(strReportID.equals("FamiliesWOSI"))
		else if(strReportID.equals("UniSampoPenRpt"))
		{
			return(strUNIVERSAL_SOMPO_PENDING_REPORT);
		}//end of else if(strReportID.equals("UniSampoPenRpt"))
		else if(strReportID.equals("DailyTansferRpt"))
		{
			return(strDAILY_TRANSFER_SUMMARY_REPORT);
		}//end of else if(strReportID.equals("DailyTansferRpt"))
		else if(strReportID.equals("MonthlyRemitRpt"))
		{
			return(strMONTHLY_REMIT_SUMARY_REPORT);
		}//end of else if(strReportID.equals("MonthlyRemitRpt"))
		else if(strReportID.equals("Annexure126qRpt"))
		{
			return(strANNEXURE_I26Q_REPORT);
		}//end of else if(strReportID.equals("Annexure126qRpt"))
		else if(strReportID.equals("ChallanDetailsRpt"))
		{
			return(strCHALLAN_DETAILS_Q_REPORT);
		}//end of else if(strReportID.equals("ChallanDetailsRpt"))
		else if(strReportID.equals("CheBatchNo"))
		{
			return(strPrintChequeInfo);
		}//end of else if(strReportID.equals("CheBatchNo"))
		else if(strReportID.equals("TDSCertGen"))
		{
			return(strSelectTdsCert);
		}//end of else if(strReportID.equals("TDSCertGen"))
		else if(strReportID.equals("AckQuaterInfo"))
		{
			return(strAckQuarterInfo);
		}//end of else if(strReportID.equals("AckQuaterInfo"))
		else if(strReportID.equals("TDSAnneSum"))
		{
			return(strTdsAnneSummary);
		}//end of else if(strReportID.equals("TDSAnneSum"))		
		else if(strReportID.equals("TDSAnneClmWise"))
		{
			return(strAnneClmWiseBreakup);
		}//end of else if(strReportID.equals("TDSAnneClmWise"))
		else if(strReportID.equals("TDSCovLett"))
		{
			return(strTdsCovLetter);
		}//end of else if(strReportID.equals("TDSCovLett"))
		/*else if(strReportID.equals("EnrDaiEndorse"))
		{
			return(strENDORSEMENTS);
		}//end of else if(strReportID.equals("EnrDaiEndorse"))
*/		else if(strReportID.equals("JobStatusRpt"))
		{
			return(strJOB_STATUS_RPT);
		}//end of else if(strReportID.equals(""))
		else if(strReportID.equals("GrpEnrollRpt"))
		{
			return strGRP_SUMMARY_REPORT;
		}//else if(strReportID.equals("GrpEnrollRpt"))
		else if(strReportID.equals("GrpPreAuthRpt"))
		{
			return strPREAUTH_GRP_REPORT;
		}//else if(strReportID.equals("GrpPreAuthRpt"))
		else if (strReportID.equals("GrpOnlineTATRpt"))
		{
			return strONLINE_TAT_REPORT;
		}//end of else if (strReportID.equals("GrpOnlineTATRpt"))
		//added as per Bajaj Change Request...........................................................
		else if (strReportID.equals("PatpolHistoryApprej"))
		{
			return strPatGenPolicyHistory ;
		}//end of else if (strReportID.equals("PatpolHistoryApprej"))
		
		else if (strReportID.equals("PatHistoryApprej"))
		{
			return strPatPreAuthHistory;
		}//end of else if (strReportID.equals("PatHistoryApprej"))
		
		else if (strReportID.equals("PatBillHistory"))
		{
			return strPatBillDetails;
		}//end of else if (strReportID.equals("PatBillHistory"))
		
		else if (strReportID.equals("PatApproveRejectForm"))
		{
			return strInsurancePreAuthGenerateLetter;
		}//end of else if (strReportID.equals("PatApproveRejectForm"))
		else if (strReportID.equals("ClaimApproveRejectForm"))
		{
			return strInsClaimsGenerateLetter;
		}//end of else if (strReportID.equals("ClaimApproveRejectForm"))
		else if (strReportID.equals("ClmPolicyHistoryApprej"))
		{
			return strClaimsPolicyHistory;
		}//end of else if (strReportID.equals("ClmPolicyHistoryApprej"))
		else if (strReportID.equals("ClmHistoryApprej"))
		{
			return strPrevClaimsHistory;
		}//end of else if (strReportID.equals("ClmHistoryApprej"))
		else if (strReportID.equals("ClmBillHistory"))
		{
			return strClaimsBillHistory;
		}//end of 		else if (strReportID.equals("ClmBillHistory"))
		else if (strReportID.equals("claimintimation"))
		{
			return strClaimsIntimateSend;
		}//end of else if (strReportID.equals("claimintimation"))
		else if (strReportID.equals("preauthintimation"))
		{
			return strPreAuthIntimateSend;
		}//end of else if (strReportID.equals("preauthintimation"))//end of bajaj
		
		//added for 2koc
		else if (strReportID.equals("InOutBound"))
		{
			return strCourierDetails;
		}//end of else if (strReportID.equals("preauthintimation"))
		
		//end added for 2koc
			else if (strReportID.equals("AppRejPreAuthList"))//1274A
		{
			return strAppRejePreAuthsList ;
		}//end of else if (strReportID.equals("AppRejPreAuthList"))
		else if (strReportID.equals("AppRejClaimsList"))
		{
			return strAppRejeClaimsList;
		}//end of else if (strReportID.equals("AppRejClaimsList"))//end of 1274A
		else if (strReportID.equals("HospitalBillsPend"))//added as per Hospital login
		{
			return strHospitalBillsPend;
		}//end of else if (strReportID.equals("HospitalBillsPend"))
		else if (strReportID.equals("HospitalClmRegd"))
		{
			return strHospitalClmRegd;
		}//end of else if (strReportID.equals("HospitalClmRegd"))
		else if (strReportID.equals("HospitalOnlineSummaryRpt"))
		{
			return strHospitalOnlineSummaryRptClaims;
		}//end of else if (strReportID.equals("HospitalOnlineSummaryRpt"))//added as per Hospital login 
		else if (strReportID.equals("CustCallBack"))
		{
			return strCUSTOMERCALLBACKREPORT;
		}//end of else if (strReportID.equals("CustCallBack"))//added as per Insurance Login
		else if (strReportID.equals("ClaimUtilizationRep"))
		{
			return strCLAIMUTILIZATIONREP;
		}//end of else if (strReportID.equals("CustCallBack"))//added as per Insurance Login
		else if (strReportID.equals("AuthUtilizationRep"))
		{
			return strAUTHUTILIZATIONREP;
		}//end of else if (strReportID.equals("CustCallBack"))//added as per Insurance Login
		else if (strReportID.equals("TATForClaims"))
		{
			return strTATFORCLAIMS;
		}//end of else if (strReportID.equals("CustCallBack"))//added as per Insurance Login
		else if (strReportID.equals("TATForPreAuth"))
		{
			return strTATForPreAuth;
		}//end of else if (strReportID.equals("CustCallBack"))//added as per Insurance Login
		 else if (strReportID.equals("chequeWiseReport"))
 		{
			 return strCHEQUEWISEREPORT;
 		}//end of else if (strReportID.equals("CustCallBack"))//added as per intX preauth Utilization report 
		 else if (strReportID.equals("batchReconciliation"))
 		{
			 return strBATCHRECONCILIATION;
 		}//end of else if (strReportID.equals("batchReconciliation"))//added as per intX provider Login 
		 else if (strReportID.equals("batchInvoice"))
 		{
			 return strBATCHINVOICE;
 		}//end of else if (strReportID.equals("batchReconciliation"))//added as per intX provider Login 
		else if (strReportID.equals("TechnicalResultReport"))
		{
			return strTRRForPreAuth;
		}//end of else if (strReportID.equals("CustCallBack"))//added as per Insurance Login
		else if (strReportID.equals("TATFSCORPOARTE"))
		{
			return strTATFSCORPOARTE;
		}//end of else if (strReportID.equals("CustCallBack"))//added as per Insurance Login
		
		else if (strReportID.equals("TATFSINDIVIDUAL"))
		{
			return strTATFSINDIVIDUAL;
		}//	if (strReportID.equals("TATFSINDIVIDUAL"))
		 else if (strReportID.equals("generatePreAuthLetter"))
		{
			 return strGENERATEPREAUTHLETTER;
		}//end of 
		else if (strReportID.equals("financeDashBoard"))//added as per intX provider Login else if (strReportID.equals("generatePreAuthLetter"))
		{
			 return strFINACNEDASHBOARD;
		}//end of else if (strReportID.equals("batchReconciliation"))//added as per intX provider Login 
		else if (strReportID.equals("InvoiceWiseReport"))//added as per intX provider Login else if (strReportID.equals("generatePreAuthLetter"))
		{
			 return strINVOICEWISEREPORT;
		}//end of else if (strReportID.equals("batchReconciliation"))//added as per intX provider Login 
		else if (strReportID.equals("overdueReport"))//added as per intX provider Login else if (strReportID.equals("generatePreAuthLetter"))
		{
			 return strOVERDUEREPORT;
		}//end of else if (strReportID.equals("batchReconciliation"))//added as per intX provider Login 
		else if (strReportID.equals("OnlinePreAuthForm"))//added as per intX provider Login else if (strReportID.equals("OnlinePreAuthForm"))
		{
			 return strONLINEPREAUTHFORM;
		}//end of else if (strReportID.equals("batchReconciliation"))//added as per intX provider Login 
		else  if (strReportID.equals("preAuthReferral"))
		{
			return(strPREAUTH_REFERRAL_REP);
		}//end of else		
		 
		else if("BroClaimUtilizationRep".equals(strReportID)){
			return strBRO_CLAIMUTILIZATIONREP;
		}		
		else if("BroAuthUtilizationRep".equals(strReportID)){
			return strBRO_AUTHUTILIZATIONREP;
		}		
		else if("BroTATForClaims".equals(strReportID)){
			return strBRO_TATFORCLAIMS;
		}
		else if("BroTATForPreAuth".equals(strReportID)){
			return strBRO_TATFORPREAUTH;
		}
		else if("BroTechnicalResultReport".equals(strReportID)){
			return strBRO_TECHNICALRESULTREPORT;
		}
		else if("BroTATFSCORPOARTE".equals(strReportID)){
			return strBRO_TATFSCORPOARTE;
		}
		else if("BroTATFSINDIVIDUAL".equals(strReportID)){
			return strBRO_TATFSINDIVIDUAL;
		}
		else if("BroHIPA".equals(strReportID)){
			return strBRO_HIPA;
		}		
		else  if (strReportID.equals("preAuthReferral"))
		{
			return(strPREAUTH_REFERRAL_REP);
		}
		else  if (strReportID.equals("claimSummaryRepprtParameter"))
		{
			return(strClaimSummary_Report);
		}	
		
		
		
		
		else  if (strReportID.equals("providerloginClaimSummary"))
		{
			return(strClaimSummary_ProviderLOgin);
		}
		
		else  if (strReportID.equals("DebitNote_ClaimWise"))
		{
			return(strDebitNote_ClaimWise_Report);
		}
		else  if (strReportID.equals("DebitNote_ActivityWise"))
		{
			return(strDebitNote_ActivityWise_Report);
		}
		
		else  if (strReportID.equals("ASCANA_ClaimWise"))
			{
				return(strASCANA_ClaimWise_Report);
			}
		else  if (strReportID.equals("ASCANA_ActivityWise"))
			{
				return(strASCANA_ActivityWise_Report);
			}
		else if(strReportID.equals("NLGICRI_ClaimWise"))
		{
			return(strNLGICRI_NLGICRI_ClaimWise_Report);
		}

		     else  if (strReportID.equals("providerloginClaimSummary"))
		    {
			return(strClaimSummary_ProviderLOgin);
		    }
		
		else if(strReportID.equals("ProvidePaymentRpt"))
		{
			return(strProvider_Payment_Excel_Report);
		}
		
		
		//end of else		
			
		
		else if(strReportID.equals("exchangRatesReport"))
		{
			return(strExchangeRatesReportDownload);
		}//end of else if
		
		else if(strReportID.equals("MemberCheckEligibilityParam"))
		{
			return(strMemberCheckEligibility);
		}//end of else if
		
		else if(strReportID.equals("ProdSyncLetter")){
			
			return strProductSyncReport;
		}
		
		
		
		
		else 
		{
			return(strPR_HOSP_DOC_RCVD_REPORT);
		}//end of else		
		
	}//end of getProcedureName(String strReportID)

	public ArrayList getProviderName(String regAuthority, String provName) throws TTKException {
		// TODO Auto-generated method stub
		ProviderDetailsVO providerDetailsVO = null;
		List<Object>li = new ArrayList<Object>();
		Connection conn = null;
    	PreparedStatement pstmt=null;
        ResultSet rs = null;
        String strQuery;
        strQuery =strPROVIDER_NAME;
         if(regAuthority.equals("ALL"))
         {
        	 int index = strQuery.indexOf("WHERE");
        	 strQuery=strQuery.substring(0, index);
        	 strQuery=strQuery+" WHERE (UPPER(p.PROVIDER_NAME) like ? OR LOWER(p.PROVIDER_NAME) like ?)";
         }
    	
    	
        if(strQuery==null)
        {
        	throw new TTKException(new Exception("No Query is found for this report !"), "tTkReports");
        }//end of if(strProcedureName==null)
        try{
            conn = ResourceManager.getConnection();//getTestConnection();
            pstmt = conn.prepareStatement(strQuery);
         
          
            if(regAuthority.equals("ALL"))
            {
            	  pstmt.setString(1, provName+"%");
            	  pstmt.setString(2, provName+"%");
            }
            else
            {
            pstmt.setString(1, regAuthority);
            pstmt.setString(2, provName+"%");
            pstmt.setString(3, regAuthority);
            pstmt.setString(4, provName+"%");
            }
            rs = pstmt.executeQuery();
            while(rs.next())
            {
            	providerDetailsVO = new ProviderDetailsVO();
            	providerDetailsVO.setProviderName(rs.getString("PROVIDER_NAME"));
              li.add(providerDetailsVO);
            }
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getProviderName()",sqlExp);
        			throw new TTKException(sqlExp, "policy");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if result set is not closed, control reaches here. Try closing the statement now.
        		{
        			try
        			{
        				if (pstmt != null) pstmt.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Statement in TTKReportDAOImpl getProviderName()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getProviderName()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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
        		pstmt = null;
        		conn = null;        		
        	}//end of finally
        	  return (ArrayList) li;
        }//end of finally
	}

	public ArrayList getClaimPreAuthAuditReport(Map<String, String> hm) throws TTKException
	{
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        ResultSet rs = null;
        ArrayList<ReportVO> alRresultList	=	null;
        try{
            conn = ResourceManager.getConnection();
            if("CLM".equals(hm.get("parameter"))) 
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strClaimAuditReport);
            if("PAT".equals(hm.get("parameter"))) 
            	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthAuditReport);  
      
            cStmtObject.setString(1, hm.get("sproviderName"));
            if(!"".equals(TTKCommon.checkNull(hm.get("sPayerName")))){
            cStmtObject.setLong(2,Long.parseLong(hm.get("sPayerName")));
            }
            else{
            	cStmtObject.setLong(2, 0);
            }
            if("CLM".equals(hm.get("parameter"))) 
            {
            cStmtObject.setString(3,  hm.get("ClaimNo"));//ERP
            cStmtObject.setString(4,  hm.get("ClaimSettlementNO"));
            }
            else   if("PAT".equals(hm.get("parameter"))) {
            	  cStmtObject.setString(3,  hm.get("PreAuthNo"));//ERP
                  cStmtObject.setString(4,  hm.get("AuthorizationNO"));
            }
            cStmtObject.setString(5, hm.get("sClaimantName"));//ERP
          
            	cStmtObject.setString(6, hm.get("EnrollmentId"));
          
            cStmtObject.setString(7, hm.get("sStatus"));//ERP
            cStmtObject.setString(8, hm.get("sPolicyNumber"));
            cStmtObject.setString(9, hm.get("sProductName"));//ERP
            cStmtObject.setString(10, hm.get("startdate"));
            cStmtObject.setString(11, hm.get("enddate"));//ERP
          
           
            cStmtObject.registerOutParameter(12,Types.OTHER);
            
            cStmtObject.execute();
            rs = (java.sql.ResultSet)cStmtObject.getObject(12);
        
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
            throw new TTKException(sqlExp, "tTkReports");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "tTkReports");
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
        			log.error("Error while closing the Resultset in TTKReportDAOImpl getClaimPreAuthAuditReport()",sqlExp);
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
        				log.error("Error while closing the Statement in TTKReportDAOImpl getClaimPreAuthAuditReport()",sqlExp);
        				throw new TTKException(sqlExp, "tTkReports");
        			}//end of catch (SQLException sqlExp)
        			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        			{
        				try
        				{
        					if(conn != null) conn.close();
        				}//end of try
        				catch (SQLException sqlExp)
        				{
        					log.error("Error while closing the Connection in TTKReportDAOImpl getClaimPreAuthAuditReport()",sqlExp);
        					throw new TTKException(sqlExp, "tTkReports");
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

	


	public int auditUploadResultClaimPreauth(String xml,String switchtype) throws TTKException {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement cStmtObject=null;
		Reader reader	=	null;
		int update=0;
		try{
            conn = ResourceManager.getConnection();
			cStmtObject=conn.prepareCall(strUploadAuditResult); 
			SQLXML poXML = null;
			if(xml != null)
			{
				poXML = conn.createSQLXML();
				poXML.setString(xml);
			}//end of if(mouDocument != null)
			cStmtObject.setObject(1, poXML);
			cStmtObject.setString(2, switchtype);
			cStmtObject.registerOutParameter(3,Types.NUMERIC);
			cStmtObject.execute();
			if(!"".equals(TTKCommon.checkNull(cStmtObject.getBigDecimal(3)))){
			update= cStmtObject.getBigDecimal(3).intValue();
			}
			
			conn.commit();
        	return update;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "tTkReports");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "tTkReports");
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
					log.error("Error while closing the Statement in TTKReportDAOImpl auditUploadResultClaimPreauth()",sqlExp);
					throw new TTKException(sqlExp, "tTkReports");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
					}//end of try

					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in TTKReportDAOImpl auditUploadResultClaimPreauth()",sqlExp);
						throw new TTKException(sqlExp, "tTkReports");
					}//end of catch (SQLException sqlExp)

					try{
						if(reader!=null)
							reader.close();
					}
					catch(IOException ioExp)
					{
						log.error("Error in Reader");
					}
					
				}//end of finally Connection Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "tTkReports");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}

	public ArrayList doViewAuditUploadTemplateErrorLog(String startDate,String endDate, String flag) throws TTKException
	{
		// TODO Auto-generated method stub
				Connection conn = null;
		    	CallableStatement cStmtObject=null;
		        ResultSet rs = null;
		        ArrayList<ReportVO> alRresultList	=	null;
		        try{
		            conn = ResourceManager.getConnection();
		            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAuditUploadResultLogDetail);
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
		            throw new TTKException(sqlExp, "tTkReports");
		        }//end of catch (SQLException sqlExp)
		        catch (Exception exp)
		        {
		            throw new TTKException(exp, "tTkReports");
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
		        			log.error("Error while closing the Resultset in TTKReportDAOImpl doViewAuditUploadTemplateErrorLog()",sqlExp);
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
		        				log.error("Error while closing the Statement in TTKReportDAOImpl doViewAuditUploadTemplateErrorLog()",sqlExp);
		        				throw new TTKException(sqlExp, "tTkReports");
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
		        					throw new TTKException(sqlExp, "tTkReports");
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

	public ArrayList getExchangeRatesList(ArrayList<Object> searchData) throws TTKException{
		
		ArrayList<Object> alResultList = new ArrayList<Object>();
		
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		 ResultSet rsCurrencyType=null;
	   ReportVO reportVO = null;
	   Statement stmt = null;
		 String currencyCode="";
	 try{
		 conn = ResourceManager.getConnection();
		 stmt = conn.createStatement();
		 String countrycode= (String) searchData.get(1);
		 
		 if("".equals(countrycode)){
			 currencyCode="";
		 }
		 else{
			 String currencyType="select tc.currency_id from app.tpa_currency_code tc where tc.country_id="+countrycode;
			 rsCurrencyType = stmt.executeQuery(currencyType);
			 while(rsCurrencyType.next()){
				  currencyCode = rsCurrencyType.getString("currency_id");
			 }
			 
		 }
		 
		 
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strExchangeRatesList);
			
			cStmtObject.setString(1,(String)searchData.get(0));//date
			cStmtObject.setString(2,currencyCode);//country
			cStmtObject.setString(3,(String)searchData.get(3));
			cStmtObject.setString(4,(String)searchData.get(4));
			if(!"".equals(TTKCommon.checkNull(searchData.get(5)))){
			cStmtObject.setLong(5,Long.parseLong((String)searchData.get(5)));
			}
			else{
				cStmtObject.setLong(5, 0);
			}
			if(!"".equals(TTKCommon.checkNull(searchData.get(6)))){
			cStmtObject.setLong(6,Long.parseLong((String)searchData.get(6)));
			}
			else{
				cStmtObject.setLong(6, 0);
			}
			if(!"".equals(TTKCommon.checkNull(searchData.get(2)))){
			cStmtObject.setLong(7,(Long) searchData.get(2));//addedby
			}
			else{
				cStmtObject.setLong(7,0);
			}
			cStmtObject.registerOutParameter(8,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(8);
			
			if(rs != null){
				while(rs.next()){
					reportVO = new ReportVO();
					
					reportVO.setCurrencyCode(TTKCommon.checkNull(rs.getString("currency_code")));
					reportVO.setCurrencyName(TTKCommon.checkNull(rs.getString("currency_name")));
					reportVO.setUnitsperAED(rs.getBigDecimal("units_per_aed"));//num
					reportVO.setaEDperUnit(rs.getBigDecimal("aed_per_unit"));//num
					reportVO.setConversionDate(TTKCommon.checkNull(rs.getString("conversion_date")));
					reportVO.setUploadedDate(TTKCommon.checkNull(rs.getString("uploaded_date")));
					
					alResultList.add(reportVO);
					
				}
			
			}
		 
			return (ArrayList)alResultList;
	 }
	  catch (Exception exp)
      {
          throw new TTKException(exp, "tTkReports");
      }//end of catch (Exception exp)
      finally
      {
      	
      	try // First try closing the Statement
      	{
      		try
      		{
      			if (rs != null) rs.close();
      			if (rsCurrencyType != null) rsCurrencyType.close();
      			
      		}//end of try
      		catch (SQLException sqlExp)
      		{
      			log.error("Error while closing the Resultset in TTKReportDAOImpl getExchangeRatesList()",sqlExp);
      			throw new TTKException(sqlExp, "policy");
      		}//end of catch (SQLException sqlExp)
      		finally // Even if result set is not closed, control reaches here. Try closing the statement now.
      		{
      			try
      			{
      				if (cStmtObject != null) cStmtObject.close();
      				if (stmt != null) stmt.close();
      			}//end of try
      			catch (SQLException sqlExp)
      			{
      				log.error("Error while closing the Statement in TTKReportDAOImpl getExchangeRatesList()",sqlExp);
      				throw new TTKException(sqlExp, "tTkReports");
      			}//end of catch (SQLException sqlExp)
      			finally // Even if statement is not closed, control reaches here. Try closing the connection now.
      			{
      				try
      				{
      					if(conn != null) conn.close();
      				}//end of try
      				catch (SQLException sqlExp)
      				{
      					log.error("Error while closing the Connection in FloatAccountDAOImpl getExchangeRatesList()",sqlExp);
      					throw new TTKException(sqlExp, "tTkReports");
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
      		rsCurrencyType=null;
      		stmt=null;
      	}//end of finally
      }//end of finally
	}
	
	
}//end of TTKReportDAO