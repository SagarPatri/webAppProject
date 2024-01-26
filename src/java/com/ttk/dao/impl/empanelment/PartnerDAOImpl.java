/**
 * @ (#) HospitalDAOImpl.java Sep 29, 2005
 * Project      :
 * File         : HospitalDAOImpl.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Sep 29, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :  Ramakrishna K M
 * Modified date :  Sep 29, 2005
 * Reason        :  Modified getHospital() and addUpdateHospital() methods
 */

package com.ttk.dao.impl.empanelment;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.BaseVO;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.DocumentDetailVO;
import com.ttk.dto.empanelment.HaadTariffFactorHistoryVO;
import com.ttk.dto.empanelment.PartnerAuditVO;
import com.ttk.dto.empanelment.PartnerDetailVO;
import com.ttk.dto.empanelment.PartnerVO;
import com.ttk.dto.empanelment.NetworkTypeVO;
import com.ttk.dto.empanelment.PreRequisiteVO;
import com.ttk.dto.empanelment.TdsCertificateVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

public class PartnerDAOImpl implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(PartnerDAOImpl.class);
	
    private static final int PTNR_SEQ_ID = 1;
    private static final int ADDR_SEQ_ID = 2;
    private static final int DISCREPANCY_STATUS = 3;
    private static final int EMPANEL_NUMBER = 4;
	private static final int  EMPANELED_DATE  = 5 ;
    private static final int PARTNER_NAME = 6;
    private static final int PTNR_LICENC_NUMB = 7;
    private static final int trade_licenc_numb = 8;
    private static final int TRADE_LICENCE_NAME = 9;
    private static final int country_id = 10;
    private static final int CURRENCY = 11;
    private static final int OFFICE_NAME = 12;
    private static final int IPT_APPROVAL_LIMIT = 13;
    private static final int ADDRESS_1 = 14;
    private static final int ADDRESS_2 = 15;
    private static final int ADDRESS_3 = 16;
    private static final int City_Type_Id = 17;
    private static final int STATE_TYPE_ID = 18;
    private static final int PIN_CODE = 19;
    private static final int COUNTRY_ID = 20;
    private static final int STD_CODE = 21;
    private static final int ISD_CODE = 22;
    private static final int off_phone_no_1 = 23;
    private static final int office_fax_no = 24;//
    private static final int PRIMARY_EMAIL_ID = 25;
    private static final int pre_emp_mailid = 26;
    private static final int WEBSITE = 27;
 private static final int LOG_SEQ_ID = 28;
    private static final int MOD_REASON_TYPE_ID = 29;
    private static final int REFERENCE_DATE = 30;
    private static final int REFERENCE_NO = 31;
    private static final int REMARKS = 32;
    private static final int AGREE_ADMIN_FEE = 33;
    private static final int DISCOUNT = 34;
    private static final int START_DATE = 35;
    private static final int END_DATE = 36;
    private static final int added_by  = 37;
    private static final int USER_SEQ_ID  = 38;


    
    private static final int COMM_TYPE_ID = 2;
    private static final int EMPANEL_TYPE_ID = 6;
    private static final int TPA_OFFICE_SEQ_ID = 7;
    private static final int PROVIDER_GENERAL_TYPE_ID = 8;
    private static final int PROD_POLICY_SEQ_ID = 45;
    private static final int STATUS_GENERAL_TYPE_ID = 46;
    private static final int HOSP_REGIST_DATE  = 52;
    private static final int NOTIFICATION_GENERAL_TYPE_ID  = 53;
    private static final int TAN_NUMBER  = 54;
    private static final int HOSP_OWNER_GENERAL_TYPE_ID  = 55;
    private static final int TDS_SUBCAT_TYPE_ID  = 56;
    private static final int STOP_PREAUTH_YN = 57;
    private static final int STOP_CLAIM_YN = 58;
    private static final int SERV_TAX_RGN_NUMBER=59;
    private static final int GIPSA_PPNYN=60;  //	<!-- added for RoomRenttariff -->
    private static final int CN = 62;
    private static final int GN = 63;
    private static final int SRN = 64;
    private static final int RN = 65;
    private static final int PROVIDER_TYPE_ID = 66;
    private static final int PRIMARY_NETWORK_ID = 67;
    private static final int CLAIM_SUBMISSION = 69;
    private static final int PROVIDERYN		 = 70;
   // private static final int PROVGRPLIST	 = 71;
   // private static final int SECTORTYPE		 = 72;
   // private static final int SECTORNUMB	 = 73;
    private static final int PROVIDERREVIEW	 = 71;
    private static final int WN = 72;
    private static final int INDORGRP = 73;
    private static final int PROVGRPLISTID = 74;
    private static final int GROUPNAME = 75;
    private static final int GRPCONTACTPERSON = 76;
    private static final int GRPCONTACTNO = 77;
    private static final int GRPCONTACTEMAIL = 78;
    private static final int GRPCONtACTADDRESS = 79;
    private static final int ASSOCIATEPAYERS = 80;
    private static final int TRADELICENCENAME = 81;
    private static final int ROW_PROCESSED = 82;
    private static final int NETWORK_TYPES = 83;
    private static final int PREAPPROVAL_LIMIT = 84;
    

/*    private static final String strHospitalList = "SELECT * FROM ( SELECT HOSP_INFO.HOSP_SEQ_ID,EMPANEL_NUMBER,HOSP_NAME,EMP_STATUS.FROM_DATE,TLI.USER_ID,EMP_STATUS.TO_DATE,STATUS_CODE.EMPANEL_DESCRIPTION,OFF_INFO.OFFICE_NAME,DENSE_RANK() OVER (ORDER BY #, ROWNUM) Q FROM TPA_HOSP_INFO HOSP_INFO,TPA_HOSP_EMPANEL_STATUS EMP_STATUS,TPA_HOSP_EMPANEL_STATUS_CODE STATUS_CODE,TPA_OFFICE_INFO OFF_INFO,TPA_HOSP_GRADING GRAD,TPA_HOSP_EMPANEL_RSON_CODE RSON_CODE,TPA_HOSP_ADDRESS ADDR,DHA_PROVIDER_TYPE DPT,TPA_COUNTRY_CODE TCC,TPA_LOGIN_INFO TLI WHERE HOSP_INFO.HOSP_SEQ_ID=EMP_STATUS.HOSP_SEQ_ID AND EMP_STATUS.EMPANEL_STATUS_TYPE_ID=STATUS_CODE.EMPANEL_STATUS_TYPE_ID AND HOSP_INFO.TPA_OFFICE_SEQ_ID=OFF_INFO.TPA_OFFICE_SEQ_ID(+) AND ADDR.HOSP_SEQ_ID = HOSP_INFO.HOSP_SEQ_ID AND RSON_CODE.EMPANEL_RSON_TYPE_ID(+)=EMP_STATUS.EMPANEL_RSON_TYPE_ID AND HOSP_INFO.HOSP_SEQ_ID = GRAD.HOSP_SEQ_ID (+) AND HOSP_INFO.PROVIDER_TYPE_ID = DPT.PROVIDER_TYPE_ID(+) AND TLI.CONTACT_SEQ_ID = EMP_STATUS.ADDED_BY(+) AND ADDR.COUNTRY_ID = TCC.COUNTRY_ID AND EMP_STATUS.ACTIVE_YN = 'Y' " ;//WHERE HOSP_SEQ_ID = ? (HOSPITAL SEQUENCE IS REQUIRED AS A JOIN MANDATORILY)
    //private static final String strPreAuthHospitalList = "{call PRE_AUTH_SQL_PKG.SELECT_HOSPITAL_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strPreAuthHospitalList = "{call PRE_AUTH_PKG.SELECT_HOSPITAL_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added two parameter OPD_4_hosptial
    private static final String strEmpanelledHospitalList ="SELECT * FROM (SELECT TPA_HOSP_INFO.HOSP_SEQ_ID,TPA_HOSP_INFO.HOSP_NAME,TPA_HOSP_INFO.EMPANEL_NUMBER,TPA_OFFICE_INFO.OFFICE_NAME,TPA_OFFICE_INFO.TPA_OFFICE_SEQ_ID,TPA_CITY_CODE.CITY_DESCRIPTION,TPA_INS_PROD_HOSP_COPAY.COPAY_AMOUNT,TPA_INS_PROD_HOSP_COPAY.COPAY_PERC,DENSE_RANK() OVER (ORDER BY #,ROWNUM)Q FROM TPA_HOSP_INFO JOIN TPA_OFFICE_INFO ON (TPA_HOSP_INFO.TPA_OFFICE_SEQ_ID = TPA_OFFICE_INFO.TPA_OFFICE_SEQ_ID) JOIN TPA_HOSP_ADDRESS ON (TPA_HOSP_INFO.HOSP_SEQ_ID = TPA_HOSP_ADDRESS.HOSP_SEQ_ID) JOIN TPA_CITY_CODE ON (TPA_HOSP_ADDRESS.CITY_TYPE_ID = TPA_CITY_CODE.CITY_TYPE_ID) JOIN TPA_HOSP_EMPANEL_STATUS ON (TPA_HOSP_INFO.HOSP_SEQ_ID = TPA_HOSP_EMPANEL_STATUS.HOSP_SEQ_ID) LEFT OUTER JOIN TPA_INS_PROD_HOSP_COPAY ON (TPA_HOSP_INFO.HOSP_SEQ_ID = TPA_INS_PROD_HOSP_COPAY.HOSP_SEQ_ID AND TPA_INS_PROD_HOSP_COPAY.PROD_POLICY_SEQ_ID=?) WHERE TPA_HOSP_EMPANEL_STATUS.EMPANEL_STATUS_TYPE_ID='EMP'";
    private static final String strAssociatedExcludedListProduct = "SELECT * FROM (SELECT TPA_INS_ASSOC_PROD_HOSP.PROD_HOSP_SEQ_ID,TPA_HOSP_INFO.HOSP_SEQ_ID,HOSP_NAME,EMPANEL_NUMBER,OFFICE_NAME,TPA_HOSP_INFO.TPA_OFFICE_SEQ_ID,CITY_DESCRIPTION,NULL AS COPAY_AMOUNT,NULL AS COPAY_PERC,DENSE_RANK() OVER (ORDER BY #, ROWNUM)Q FROM TPA_HOSP_INFO, TPA_OFFICE_INFO, TPA_INS_ASSOC_PROD_HOSP, TPA_HOSP_ADDRESS, TPA_CITY_CODE WHERE TPA_HOSP_INFO.TPA_OFFICE_SEQ_ID = TPA_OFFICE_INFO.TPA_OFFICE_SEQ_ID AND TPA_HOSP_INFO.HOSP_SEQ_ID =TPA_INS_ASSOC_PROD_HOSP.HOSP_SEQ_ID AND TPA_HOSP_INFO.HOSP_SEQ_ID =TPA_HOSP_ADDRESS.HOSP_SEQ_ID AND TPA_HOSP_ADDRESS.CITY_TYPE_ID=TPA_CITY_CODE.CITY_TYPE_ID AND PROD_POLICY_SEQ_ID=? AND STATUS_GENERAL_TYPE_ID = '$' ";//WHERE PROD_POLICY_SEQ_ID = ?  and status general type id =$(PRODPOLICY sequence and status general type id are required as a join mandatorily)
     // private static final String strHospitalDetail = "SELECT A.HOSP_SEQ_ID,C.HOSP_GNRL_SEQ_ID,E.ADDR_SEQ_ID,A.EMPANEL_NUMBER,B.EMPANEL_STATUS_TYPE_ID,F.EMPANEL_DESCRIPTION,A.EMPANEL_TYPE_ID,A.TPA_REF_NUMBER,A.PROVIDER_GENERAL_TYPE_ID,G.OFFICE_NAME,A.TPA_OFFICE_SEQ_ID,A.TPA_REGIST_DATE,A.DOC_DISPATCH_DATE,C.MOU_TYPE_ID,C.MOU_RCVD_DATE,C.CREDIT_PERIOD,C.INTEREST,C.SIGNED_DATE,C.MOU_SENT_DATE,C.TARIFF_RCVD_DATE,C.HOSP_INFO_RCVD_DATE,A.HOSP_NAME,A.HOSP_TYPE_ID,A.HOSP_REGIST_NUMBER,A.REGIST_AUTHORITY,A.IRDA_NUMBER,A.PAN_NUMBER,A.TAN_NUMBER,A.HOSP_OWNER_GENERAL_TYPE_ID,A.TDS_SUBCAT_TYPE_ID,A.RATING,A.INT_EXT_APPLICATION_YN,E.ADDRESS_1,E.ADDRESS_2,E.ADDRESS_3,E.City_Type_Id,E.STATE_TYPE_ID,E.PIN_CODE,E.COUNTRY_ID,A.STD_CODE,E.LANDMARKS,A.PRIMARY_EMAIL_ID,A.COMM_TYPE_ID,A.OFF_PHONE_NO_1,A.OFF_PHONE_NO_2, A.OFFICE_FAX_NO,A.WEBSITE,A.INTERNET_CONNECT_YN,A.REMARKS,H.DISC_PRESENT_YN,C.HOSP_VERIFY_FORM_RCVD_DATE,C.REG_CRT_RCVD_DATE,A.HOSP_REGIST_DATE,A.NOTIFICATION_GENERAL_TYPE_ID,A.STOP_PREAUTH_YN,A.STOP_CLAIM_YN,A.SERV_TAX_RGN_NUMBER FROM ((((((( TPA_HOSP_INFO A LEFT OUTER JOIN TPA_HOSP_EMPANEL_STATUS B ON	A.HOSP_SEQ_ID = B.HOSP_SEQ_ID AND B.active_yn='Y') LEFT OUTER JOIN TPA_HOSP_GENERAL_DTL C ON C.empanel_seq_id =B.empanel_seq_id) LEFT OUTER JOIN TPA_HOSP_ADDRESS E ON A.HOSP_SEQ_ID=E.HOSP_SEQ_ID ) LEFT OUTER JOIN TPA_HOSP_EMPANEL_STATUS_CODE F ON B.EMPANEL_STATUS_TYPE_ID=F.EMPANEL_STATUS_TYPE_ID) LEFT OUTER JOIN TPA_OFFICE_INFO G ON A.TPA_OFFICE_SEQ_ID = G.TPA_OFFICE_SEQ_ID) LEFT OUTER JOIN TPA_HOSP_DISCREPANCY H ON A.HOSP_SEQ_ID = H. HOSP_SEQ_ID)) WHERE A.HOSP_SEQ_ID=?";
    //changes as per ENC and Decrpt KOC11ED  added( ttk_util_pkg.fn_decrypt(A.PRIMARY_EMAIL_ID) as PRIMARY_EMAIL_ID)
//	   private static final String strHospitalDetail = "SELECT A.HOSP_SEQ_ID,C.HOSP_GNRL_SEQ_ID,E.ADDR_SEQ_ID,A.EMPANEL_NUMBER,B.EMPANEL_STATUS_TYPE_ID,F.EMPANEL_DESCRIPTION,A.EMPANEL_TYPE_ID,A.TPA_REF_NUMBER,A.PROVIDER_GENERAL_TYPE_ID,G.OFFICE_NAME,A.TPA_OFFICE_SEQ_ID,A.TPA_REGIST_DATE,A.DOC_DISPATCH_DATE,C.MOU_TYPE_ID,C.MOU_RCVD_DATE,C.CREDIT_PERIOD,C.INTEREST,C.SIGNED_DATE,C.MOU_SENT_DATE,C.TARIFF_RCVD_DATE,C.HOSP_INFO_RCVD_DATE,A.HOSP_NAME,A.HOSP_TYPE_ID,A.HOSP_REGIST_NUMBER,A.REGIST_AUTHORITY,A.IRDA_NUMBER,A.PAN_NUMBER,A.TAN_NUMBER,A.HOSP_OWNER_GENERAL_TYPE_ID,A.TDS_SUBCAT_TYPE_ID,A.RATING,A.INT_EXT_APPLICATION_YN,E.ADDRESS_1,E.ADDRESS_2,E.ADDRESS_3,E.City_Type_Id,E.STATE_TYPE_ID,E.PIN_CODE,E.COUNTRY_ID,A.STD_CODE,E.LANDMARKS,ttk_util_pkg.fn_decrypt(A.PRIMARY_EMAIL_ID) as PRIMARY_EMAIL_ID ,A.COMM_TYPE_ID,A.OFF_PHONE_NO_1,A.OFF_PHONE_NO_2, A.OFFICE_FAX_NO,A.WEBSITE,A.INTERNET_CONNECT_YN,A.REMARKS,H.DISC_PRESENT_YN,C.HOSP_VERIFY_FORM_RCVD_DATE,C.REG_CRT_RCVD_DATE,A.HOSP_REGIST_DATE,A.NOTIFICATION_GENERAL_TYPE_ID,A.STOP_PREAUTH_YN,A.STOP_CLAIM_YN,A.SERV_TAX_RGN_NUMBER,A.GIPSA_PPNYN,A.CN_YN,A.GN_YN,A.SRN_YN,A.RN_YN,PROVIDER_TYPE_ID AS DHA_PROVIDER_TYPE,A.PRIMARY_NETWORK,ISD_CODE FROM ((((((( TPA_HOSP_INFO A LEFT OUTER JOIN TPA_HOSP_EMPANEL_STATUS B ON  A.HOSP_SEQ_ID = B.HOSP_SEQ_ID AND B.active_yn='Y') LEFT OUTER JOIN TPA_HOSP_GENERAL_DTL C ON C.empanel_seq_id =B.empanel_seq_id) LEFT OUTER JOIN TPA_HOSP_ADDRESS E ON A.HOSP_SEQ_ID=E.HOSP_SEQ_ID ) LEFT OUTER JOIN TPA_HOSP_EMPANEL_STATUS_CODE F ON B.EMPANEL_STATUS_TYPE_ID=F.EMPANEL_STATUS_TYPE_ID) LEFT OUTER JOIN TPA_OFFICE_INFO G ON A.TPA_OFFICE_SEQ_ID = G.TPA_OFFICE_SEQ_ID) LEFT OUTER JOIN TPA_HOSP_DISCREPANCY H ON A.HOSP_SEQ_ID = H. HOSP_SEQ_ID)) WHERE A.HOSP_SEQ_ID=?";
    private static final String strDeleteHospitalInfo 			= 	"{call HOSPITAL_EMPANEL_PKG.PR_HOSPITAL_INFO_DELETE(?,?)}";
    private static final String strHospitalDiscrepancyList 		= 	"SELECT dl.hosp_seq_id,dl.match_hosp_seq_id,h.HOSP_NAME,h.EMPANEL_NUMBER,o.OFFICE_NAME,sc.EMPANEL_DESCRIPTION from TPA_HOSP_DISCREPANCY_LIST dl,tpa_hosp_info h,tpa_office_info o,TPA_HOSP_EMPANEL_STATUS s,TPA_HOSP_EMPANEL_STATUS_CODE sc WHERE dl.hosp_seq_id = ? AND h.hosp_seq_id = dl.match_hosp_seq_id AND o.tpa_office_seq_id = h.tpa_office_seq_id AND dl.match_hosp_seq_id = s.hosp_seq_id AND s.empanel_status_type_id = sc.empanel_status_type_id";//WHERE HOSP_SEQ_ID = ? (Hospital sequence is required as a join mandatorily)
    private static final String strUpdateDiscrepancyInfo 		= 	"{call HOSPITAL_EMPANEL_PKG.PR_DISCREPANCY_INFO_SAVE (?,?,?,?)}";
    private static final String strDisAssociateHospitalInfo 	= 	"{CALL PRODUCT_ADMIN_PKG.PR_PRODUCT_ASSOCIATE_DELETE(?,?,?)}";
    private static final String strAssociateOrExcludeHospitalInfo = "{CALL PRODUCT_ADMIN_PKG.PR_PRODUCT_ASSOCIATE_SAVE(?,?,?,?,?)}";
    private static final String strSaveAssocHospCopay 			= 	"{CALL PRODUCT_ADMIN_PKG.SAVE_ASSOC_HOSP_COPAY(?,?,?,?,?,?)}";
    private static final String steDeleteAssocHospCopay 		=	 	"{CALL PRODUCT_ADMIN_PKG.DELETE_ASSOC_HOSP_COPAY(?,?,?)}";
    private static final String strAssocCertList				=	"{CALL TDS_PKG.SELECT_TDS_CERT_INFO(?,?,?,?)}";
    private static final String strSaveCertificateDocDetail		=	"{CALL TDS_PKG.SAVE_TDS_CERT_INFO(?,?,?,?,?,?,?,?)}";
    private static final String strCertInfodetails				=	"{CALL TDS_PKG.SELECT_TDS_CERT_INFO_DETAILS(?,?)}";
    private static final String strDeleteTdsCertificates		=	"{CALL TDS_PKG.DELETE_TDS_CERTIFICATE(?,?)}"; 
    //intX
    private static final String strSaveMouFils					=	"{CALL HOSPITAL_EMPANEL_PKG.SAVE_MOU_DOCS_INFO(?,?,?,?,?,?,?,?)}";
    private static final String strGetMouFils					=	"SELECT I.MOU_DOC_SEQ_ID,GC.DESCRIPTION,to_char(I.added_date,'MM/DD/YYYY HH24:MI:SS' ) as ADDED_DATE, UC.CONTACT_NAME,I.DOCS_PATH,I.HOSP_SEQ_ID,I.FILE_NAME FROM APP.MOU_DOCS_INFO I JOIN APP.TPA_GENERAL_CODE GC ON (I.DOCS_GENTYPE_ID = GC.GENERAL_TYPE_ID) JOIN APP.TPA_USER_CONTACTS UC ON(UC.CONTACT_SEQ_ID=I.ADDED_BY) WHERE I.HOSP_SEQ_ID=? ORDER BY I.MOU_DOC_SEQ_ID";   
    private static final String strGetLicenceNos				=	"select provider_id from app.dha_providers_master where trim(replace(provider_name,'''','~'))=? and provider_id=?";
    private static final String strSaveGenerateCredentialsPreRequisite	=	"{CALL CONTACT_PKG.save_provider_creds(?,?,?,?,?,?,?,?,?)}";
    private static final String strSendMailCredentialsPreRequisite	=	"{CALL GENERATE_MAIL_PKG.proc_generate_message(?,?,?,?,?,?,?)}";
    private static final String strProviderDashBoard 			=	"{CALL hospital_empanel_pkg.Dashboard_provider(?)}";    
    private static final String strTotalProvidersList 			=	"SELECT M.PROVIDER_ID,M.PROVIDER_NAME,M.PROVIDER_SECTOR,M.HEALTH_AUTHORITY FROM APP.DHA_PROVIDERS_MASTER M ORDER BY M. HEALTH_AUTHORITY";
    private static final String strGetLicenceNosForPreEmp		=	"select provider_id from app.dha_providers_master where trim(replace(provider_name,'''','~'))=? ";
    private static final String strTotalPayersList				=	"{ ? = call hospital_empanel_pkg.fn_asso_payeer_list() }";
    private static final String strGetLicenceNosById			=	"select (p.PROVIDER_name || '[' || provider_id || ']') as "
														    		+" provider_namewithId from app.dha_providers_master p where provider_id=? AND P.PROVIDER_ID NOT IN "
														    		+" (select p.provider_id from app.tpa_hosp_info thi join app.dha_providers_master p on "
														    		+" (thi.hosp_licenc_numb = p.provider_id))";

    private static final String strGetStdOrIsd					=	"SELECT ISD_CODE FROM APP.TPA_COUNTRY_CODE WHERE COUNTRY_ID = ?";
    private static final String strStateOnCountry				=	"SELECT ST.STATE_TYPE_ID,ST.STATE_NAME FROM APP.TPA_STATE_CODE ST WHERE ST.COUNTRY_ID = ? ORDER BY ST.SORT_NO";
    private static final String strNetworkTypeList				=	"SELECT TG.DESCRIPTION,TG.GENERAL_TYPE_ID,TG.SORT_NO,TG.NETWORK_TYPE_SEQ_ID   FROM APP.TPA_GENERAL_CODE TG  WHERE TG.HEADER_TYPE = 'PROVIDER_NETWORK' ORDER BY TG.SORT_NO";
    private static final String strSaveNetworkTypes				=	"{CALL HOSPITAL_EMPANEL_PKG.SAVE_NETWORK_TYPE (?,?,?,?,?)}";
    private static final String strModifyNetworkTypes			=	"{CALL HOSPITAL_EMPANEL_PKG.MODIFY_NETWORK_TYPE_MSTR (";
    private static final String strAssociatedNetworkList		=	"SELECT J.NETWORK_TYPE,J.NETWORK_YN,TG.SORT_NO FROM APP.TPA_HOSP_NETWORK J,APP.TPA_GENERAL_CODE TG WHERE TG.HEADER_TYPE = 'PROVIDER_NETWORK' AND J.NETWORK_TYPE=TG.GENERAL_TYPE_ID AND J.HOSP_SEQ_ID=? ORDER BY TG.SORT_NO ";
    private static final String strHistoryNetworkList			=	"SELECT NETWORK_NAME_OLD,NETWORK_CODE_OLD,NETWORK_NAME_NEW,NETWORK_CODE_NEW FROM APP.TPA_NETWORK_HISTORY ORDER BY ADDED_DATE DESC";
    //private static final String strGetHaadCategories			=	"SELECT G.ACTIVITY_TYPE,MAX(DECODE(T.FACTOR, 'BASE_RATE', T.VALUE)) AS BASE_RATE,MAX(DECODE(T.FACTOR, 'MARGIN', T.VALUE)) AS MARGIN,MAX(DECODE(T.FACTOR, 'GAP', T.VALUE)) AS GAP,MAX(DECODE(T.FACTOR, 'FACTOR', T.VALUE)) AS FACTOR   FROM APP.HAAD_AGGREED_FACTOR_BSPY T,APP.TPA_HAAD_CATEGORY_TYPE G   WHERE T.CATEGORY_TYPE_SEQ_ID = G.CATEGORY_TYPE_SEQ_ID GROUP BY G.ACTIVITY_TYPE";
    private static final String strGetHaadCategories			=	"SELECT CATEGORY_TYPE_SEQ_ID ID,ACTIVITY_TYPE NAME FROM APP.TPA_HAAD_CATEGORY_TYPE";
    private static final String strSaveHaadFactors				= "{CALL  HOSPITAL_EMPANEL_PKG.AGG_FACTORS_BULK_UPDATE(";
    private static final String strGetHaadColumnHEaders			= "SELECT COLUMN_NAME  FROM APP.HAAD_GENERL_CODE";
    private static final String strCheckHaadTariffExixts		=	"SELECT 1 FROM APP.HAAD_AGGREED_FACTOR_BSPY T WHERE T.HOSP_SEQ_ID=?";
    private static final String strgetPrimaryNetwork			=	"SELECT PRIMARY_NETWORK FROM APP.TPA_HOSP_INFO WHERE HOSP_SEQ_ID=?";
    private static final String strGetHaadExistingCategories	=	"SELECT G.ACTIVITY_TYPE, T.NETWORK_TYPE, T.ADDED_DATE, MAX(DECODE(T.FACTOR, 'BASE RATE', T.VALUE)) AS BASE_RATE, MAX(DECODE(T.FACTOR, 'MARGIN', T.VALUE)) AS MARGIN,"
    															+" MAX(DECODE(T.FACTOR, 'GAP', T.VALUE)) AS GAP, MAX(DECODE(T.FACTOR, 'FACTOR', T.VALUE)) AS FACTOR FROM APP.HAAD_AGGREED_FACTOR_BSPY T, APP.TPA_HAAD_CATEGORY_TYPE G WHERE T.CATEGORY_TYPE_SEQ_ID = G.CATEGORY_TYPE_SEQ_ID"
    															+" AND T.HOSP_SEQ_ID = 35971 AND T.NETWORK_TYPE = 'RN' AND T.HAAD_AGG_SEQ_ID IN (SELECT ADDED_DAT FROM (SELECT GG.ACTIVITY_TYPE, TT.NETWORK_TYPE, MAX(TT.HAAD_AGG_SEQ_ID) ADDED_DAT"
    															+" FROM APP.HAAD_AGGREED_FACTOR_BSPY TT, APP.TPA_HAAD_CATEGORY_TYPE   GG WHERE TT.CATEGORY_TYPE_SEQ_ID = GG.CATEGORY_TYPE_SEQ_ID AND TT.HOSP_SEQ_ID = ? AND TT.NETWORK_TYPE = ?"
    															+" GROUP BY GG.ACTIVITY_TYPE, TT.NETWORK_TYPE)) GROUP BY G.ACTIVITY_TYPE, T.NETWORK_TYPE, T.ADDED_DATE";
    
    
    private static final String strGetHaadExistingCategories	=	"SELECT G.ACTIVITY_TYPE,T.NETWORK_TYPE,MAX(DECODE(T.FACTOR, 'BASE RATE', T.VALUE)) AS BASE_RATE,MAX(DECODE(T.FACTOR, 'MARGIN', T.VALUE)) AS MARGIN,MAX(DECODE(T.FACTOR, 'GAP', T.VALUE)) AS GAP,"
    															+" MAX(DECODE(T.FACTOR, 'FACTOR', T.VALUE)) AS FACTOR FROM APP.HAAD_AGGREED_FACTOR_BSPY T, APP.TPA_HAAD_CATEGORY_TYPE G WHERE T.CATEGORY_TYPE_SEQ_ID = G.CATEGORY_TYPE_SEQ_ID"
    															+" AND T.HAAD_AGG_SEQ_ID IN ((SELECT ADDED_DAT FROM (SELECT GG.ACTIVITY_TYPE, TT.NETWORK_TYPE,TT.FACTOR, MAX(TT.HAAD_AGG_SEQ_ID) ADDED_DAT FROM APP.HAAD_AGGREED_FACTOR_BSPY TT,"
    															+" APP.TPA_HAAD_CATEGORY_TYPE   GG WHERE TT.CATEGORY_TYPE_SEQ_ID = GG.CATEGORY_TYPE_SEQ_ID AND TT.HOSP_SEQ_ID = ? AND TT.NETWORK_TYPE = ? GROUP BY GG.ACTIVITY_TYPE, TT.NETWORK_TYPE,TT.FACTOR)))"
    															+" GROUP BY G.ACTIVITY_TYPE,T.NETWORK_TYPE";
			
    private static final String strUpdateHaadFactors			= "{CALL HOSPITAL_EMPANEL_PKG.MODIFY_HAAD_AGGREED_FACTOR(?,?,?,?,?,?,?,?,?,?)}";
 

    private static final String strDeleteTarrifDetails          =    "{CALL HOSPITAL_EMPANEL_PKG .DELETE_TARRIF_DETAILS(?,?)}";
    private static final String strEligibleNetworks		= "SELECT TG.GENERAL_TYPE_ID,TG.DESCRIPTION FROM APP.TPA_HOSP_INFO HP JOIN APP.TPA_HOSP_NETWORK J ON (J.HOSP_SEQ_ID=HP.HOSP_SEQ_ID)JOIN  APP.TPA_GENERAL_CODE TG ON (TG.GENERAL_TYPE_ID=J.NETWORK_TYPE)"
				+" WHERE TG.HEADER_TYPE = 'PROVIDER_NETWORK'  AND J.NETWORK_YN='Y' AND HP.HOSP_SEQ_ID=?  ORDER BY TG.SORT_NO";

    private static final String strGetHaadHistory          =    "SELECT ROWNUM AS SNO ,C.* FROM (SELECT G.ACTIVITY_TYPE, T.FACTOR, T.VALUE, T.ADDED_DATE, C.CONTACT_NAME AS ADDED_BY, T.NETWORK_TYPE||'-'||JK.DESCRIPTION  NETWORK_TYPE "
    															+" FROM APP.HAAD_AGGREED_FACTOR_BSPY T, APP.TPA_HAAD_CATEGORY_TYPE G,APP.TPA_USER_CONTACTS C,APP.TPA_GENERAL_CODE JK WHERE T.CATEGORY_TYPE_SEQ_ID = G.CATEGORY_TYPE_SEQ_ID"
    															+" AND JK.GENERAL_TYPE_ID=T.NETWORK_TYPE AND C.CONTACT_SEQ_ID=T.ADDED_BY AND T.HOSP_SEQ_ID=? ORDER BY T.ADDED_DATE DESC) C ";

    

    private static final String strPreAuthUploads				=	"{CALL HOSPITAL_EMPANEL_PKG.SAVE_MOU_DOCS_INFO1(?,?,?,?,?,?,?,?,?)}";
    private static final String strGetPreAuthUploads			=	"{CALL HOSP_DIAGNOSYS_PKG. GET_FILE_DETAILS(?,?)}";
    */
    
    
    
    
	 private static final String strPartnerList="SELECT * FROM ( SELECT PATR_INFO.PTNR_SEQ_ID,UPPER(PATR_INFO.PARTNER_NAME) PARTNER_NAME,PATR_INFO.EMPANEL_NUMBER EMPANEL_NUMBER,PATR_INFO.EMPANELED_DATE  EMPANELED_DATE,STATUS_CODE.EMPANEL_DESCRIPTION ,DENSE_RANK() OVER (ORDER BY #) Q FROM APP.TPA_PARTNER_INFO PATR_INFO,APP.TPA_PARTNER_EMPANEL_STATUS EMP_STATUS,APP.TPA_PATNR_EMPANEL_STATUS_CODE STATUS_CODE WHERE PATR_INFO.PTNR_SEQ_ID=EMP_STATUS.PTNR_SEQ_ID AND EMP_STATUS.EMPANEL_STATUS_TYPE_ID=STATUS_CODE.EMPANEL_STATUS_TYPE_ID AND EMP_STATUS.EMPANEL_STATUS_TYPE_ID IN ('EMP','INP','ONH','DFC','TDE','EXP') AND EMP_STATUS.ACTIVE_YN ='Y'"; 
	 private static final String strPartnerDetail	 = "SELECT A.PTNR_SEQ_ID, A.PARTNER_NAME, A.EMPANEL_NUMBER, A.EMPANELED_DATE EMPANELED_DATE, A.PTNR_LICENC_NUMB, A.TRADE_LICENC_NUMB, A.TRADE_LICENCE_NAME, A.COUNTRY_ID COUNTRY_COVERED, A.CURRENCY_TYPE, A.OFFICE_NAME, A.IPT_APPROVAL_LIMIT, A.ISD_CODE, A.STD_CODE, A.OFF_PHONE_NO_1, A.OFFICE_FAX_NO, public.PGP_SYM_DECRYPT(A.PRE_EMP_MAILID::bytea, 'AES_KEY') PRE_EMP_MAILID, public.PGP_SYM_DECRYPT(A.PRIMARY_EMAIL_ID::bytea, 'AES_KEY') PRIMARY_EMAIL_ID, public.PGP_SYM_DECRYPT(A.PRE_EMP_MAILID::bytea, 'AES_KEY') PRE_EMP_MAILID, A.WEBSITE, case when A.AGREE_ADMIN_FEE=0 then null else A.AGREE_ADMIN_FEE end as AGREE_ADMIN_FEE , case when A.DISCOUNT=0 then null else A.DISCOUNT end as DISCOUNT, A.START_DATE, A.END_DATE, B.ADDR_SEQ_ID, B.ADDRESS_1, B.ADDRESS_2, B.ADDRESS_3, B.STATE_TYPE_ID, B.CITY_TYPE_ID, B.COUNTRY_ID  COUNTRY, B.PIN_CODE, C.EMPANEL_STATUS_TYPE_ID, D.EMPANEL_DESCRIPTION, E.DISC_PRESENT_YN  FROM  APP.TPA_PARTNER_INFO A JOIN APP.TPA_PARTNER_ADDRESS B ON (A.PTNR_SEQ_ID = B.PTNR_SEQ_ID) JOIN APP.TPA_PARTNER_EMPANEL_STATUS C ON (A.PTNR_SEQ_ID= C.PTNR_SEQ_ID) JOIN APP.TPA_PATNR_EMPANEL_STATUS_CODE D ON (C.EMPANEL_STATUS_TYPE_ID = D.EMPANEL_STATUS_TYPE_ID) JOIN APP.TPA_PARTNER_DISCREPANCY E  ON (A.PTNR_SEQ_ID=E.PTNR_SEQ_ID) WHERE A.PTNR_SEQ_ID = ? ";   
	 private static final String strStateTypeIdList 				= 	"SELECT STATE_TYPE_ID FROM APP.TPA_STATE_CODE";
	 private static final String strStateTypeIdListintX 			=	"SELECT SC.STATE_TYPE_ID, SC.COUNTRY_ID AS COUNTRY_ID,SC.STD_CODE,CC.ISD_CODE FROM APP.TPA_STATE_CODE SC JOIN APP.TPA_COUNTRY_CODE CC ON(SC.COUNTRY_ID=CC.COUNTRY_ID) WHERE STATE_TYPE_ID=?";
	 private static final String strCityTypeList 				= 	"SELECT CITY_TYPE_ID,CITY_DESCRIPTION FROM APP.TPA_CITY_CODE WHERE STATE_TYPE_ID = ?"; 
	 private static final String strAddUpdatePartnerInfo 	= "{CALL partner_empanell_pkg_PARTNER_INFO_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

   String pro1="{CALL partner_empanell_pkg.PARTNER_INFO_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    /**
     * This method returns the ArrayList, which contains the HospitalVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of HospitalVO object's which contains the hospital details
     * @exception throws TTKException
     */
    public ArrayList getPartnerList(ArrayList alSearchObjects) throws TTKException
    {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strPartnerList;
        String strIdentifier = "Partner";
        BaseVO bvo = null;
        Collection<Object> resultList = new ArrayList<Object>();
     
            
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            String strEmpaneledDate   = TTKCommon.checkNull(((SearchCriteria)alSearchObjects.get(0)).getValue());
            if (!strEmpaneledDate.equals(""))
            {
            	sbfDynamicQuery.append(" AND to_date(to_char(EMPANELED_DATE,'DD/MM/YYYY'),'dd/mm/yyyy')>= to_date(to_char(TO_DATE('"+strEmpaneledDate+"' ,'DD/MM/YYYY'),'DD/MM/YYYY'),'dd/mm/yyyy')");
            	// sbfDynamicQuery.append(" AND to_char(EMPANELED_DATE,'DD/MM/YYYY')>= to_char(TO_DATE('"+strEmpaneledDate+"','DD/MM/YYYY'),'DD/MM/YYYY')");
            }//end of if (!strEmpaneledDate.equals(""))
            for(int i=1; i < alSearchObjects.size()-4; i++)
            {
                if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
                {
                    sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
                }//end of if(!((SearchCriteria)alSearchCriteria.get(i)).getValue().equals(""))
            }//end of for()
        }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
        
        
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( ") A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        try{
            conn = ResourceManager.getConnection();
       //     System.out.println("Partner Search..."+strStaticQuery);
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    bvo = new PartnerVO();
                    populateBean(bvo,rs);
                    resultList.add(bvo);
                }//end of while
            }//end of if(rs != null)
            return (ArrayList)resultList;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "partner");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "partner");
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
					log.error("Error while closing the Resultset in PartnerDAOImpl getPartnerList()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PartnerDAOImpl getPartnerList()",sqlExp);
						throw new TTKException(sqlExp, "partner");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PartnerDAOImpl getPartnerList()",sqlExp);
							throw new TTKException(sqlExp, "partner");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "partner");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getHospitalList(ArrayList alSearchObjects)
    
    /**
     * This method populates the basebean with the information contained in the resultset object
     * @param baseVO BaseVO object which has to be populated
     * @param rs ResultSet object which has to be populated
     * @param strIdentifier String,which has to identify the request sender
     * @exception throws TTKException
     */
    private void populateBean(BaseVO BaseVO, ResultSet rs) throws SQLException {
        PartnerVO voBean = (PartnerVO) BaseVO;
        if(rs.getString("PTNR_SEQ_ID") != null){
        voBean.setPtnrSeqId(new Long(rs.getLong("PTNR_SEQ_ID")));
        }
        voBean.setPartnerName(TTKCommon.checkNull(rs.getString("PARTNER_NAME")));
        voBean.setEmplNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
       // voBean.setTtkBranch(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
  if(rs.getDate("EMPANELED_DATE") != null){
        	voBean.setEmpanelmentDate(rs.getDate("EMPANELED_DATE"));
        }//end of if(rs.getDate("EMPANELED_DATE") != null)            
        voBean.setPartnerStatus(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
    }//end of populateBean(BaseVO BaseVO, ResultSet rs,String strIdentifier)
    
   /* *//**
     * This method returns the HospitalDetailVO, which contains all the hospital details
     * @param lPtnrSeqId Long object which contains the hospital seq id
     * @return HospitalDetailVO object which contains all the hospital details
     * @exception throws TTKException
     */
    public PartnerDetailVO getPartnerDetail(Long lPtnrSeqId) throws TTKException {
    	PartnerDetailVO partnerDetailVO = null;
    	PartnerVO partnerVO=null;
        AddressVO addressVO = null;
        DocumentDetailVO documentDetailVO = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try
        {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strPartnerDetail);
            pStmt.setLong(1,lPtnrSeqId);
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                	partnerDetailVO = new PartnerDetailVO();
                    addressVO        = new AddressVO();
                    documentDetailVO = new DocumentDetailVO();
                    if(rs.getString("PTNR_SEQ_ID") != null){
                    	partnerDetailVO.setPtnrSeqId(new Long(rs.getLong("PTNR_SEQ_ID")));
                  //  	System.out.println("PTNR_SEQ_ID..."+partnerDetailVO.getPtnrSeqId());
                    }//end of if(rs.getString("PTNR_SEQ_ID") != null)
                    /*if(rs.getString("PTNR_GNRL_SEQ_ID") != null ){
                    	partnerDetailVO.setPtnrGnrlSeqId(new Long(rs.getLong("PTNR_GNRL_SEQ_ID")));//
                    }
                     */  
                    if(rs.getString("ADDR_SEQ_ID") != null){
                    addressVO.setAddrSeqId(new Long(rs.getLong("ADDR_SEQ_ID")));//
                    }//end of if(rs.getString("ADDR_SEQ_ID") != null)
                 
                    partnerDetailVO.setEmplNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
                    partnerDetailVO.setEmplStatusTypeId(TTKCommon.checkNull(rs.getString("EMPANEL_STATUS_TYPE_ID")));
                    partnerDetailVO.setStatus(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
                    partnerDetailVO.setTpaBranchCode(TTKCommon.checkNull(rs.getString("OFFICE_NAME"))); 
                    partnerDetailVO.setPartnerName(TTKCommon.checkNull(rs.getString("PARTNER_NAME")));
                    partnerDetailVO.setTradeLicenceName(TTKCommon.checkNull(rs.getString("TRADE_LICENCE_NAME")));
                    partnerDetailVO.setIrdaNumber(TTKCommon.checkNull(rs.getString("PTNR_LICENC_NUMB")));
                    partnerDetailVO.setPanNmbr(TTKCommon.checkNull(rs.getString("TRADE_LICENC_NUMB")));
                    partnerDetailVO.setCurrencyAccepted(TTKCommon.checkNull(rs.getString("CURRENCY_TYPE")));  
                    addressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
                    addressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
                    addressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
                    addressVO.setCityCode(TTKCommon.checkNull(rs.getString("City_Type_Id")));
                    addressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
                    addressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY")));
                   
                    if(rs.getDate("EMPANELED_DATE")!=null)
					{
						partnerDetailVO.setEmpanelmentDate(new java.util.Date(rs.getTimestamp("EMPANELED_DATE").getTime()));
					}//end of if(rs.getDate("EMPANELED_DATE")!=null)
                   
                    addressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
                    partnerDetailVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_COVERED")));
                    partnerDetailVO.setAddressVO(addressVO);
                    partnerDetailVO.setStdCode(TTKCommon.checkNull(rs.getString("STD_CODE")));
                    partnerDetailVO.setPrimaryEmailId(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
                    partnerDetailVO.setOfficePhone1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
                    partnerDetailVO.setFaxNbr(TTKCommon.checkNull(rs.getString("OFFICE_FAX_NO")));
                    partnerDetailVO.setWebsite(TTKCommon.checkNull(rs.getString("WEBSITE")));
                    partnerDetailVO.setIsdCode(TTKCommon.checkNull(rs.getString("ISD_CODE")));
                    partnerDetailVO.setPreEmpanelMailId(TTKCommon.checkNull(rs.getString("PRE_EMP_MAILID"))); 
                    
                    partnerDetailVO.setTTKBranchCode(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));                    
                     if(rs.getString("IPT_APPROVAL_LIMIT") != null)
                    {
                    	partnerDetailVO.setLimit(new Long(rs.getLong("IPT_APPROVAL_LIMIT")));//
                    }//end of  if(rs.getString("IPT_APPROVAL_LIMIT") != null)
                     
                     if(rs.getString("AGREE_ADMIN_FEE") != null)
                     {
                     	partnerDetailVO.setFee(new Long(rs.getLong("AGREE_ADMIN_FEE")));//
                     }//end of if(rs.getString("AGREE_ADMIN_FEE") != null)
                    
                     
                     if(rs.getString("DISCOUNT") != null)
                     {
                     	partnerDetailVO.setDiscount(new Long(rs.getLong("DISCOUNT")));//
                     }//end of if(rs.getString("DISCOUNT") != null)
                     
                     if(rs.getDate("START_DATE")!=null)
 					{
 						partnerDetailVO.setStartDate(new java.util.Date(rs.getTimestamp("START_DATE").getTime()));
 					}//end of if(rs.getDate("EMPANELED_DATE")!=null)
                     if(rs.getDate("END_DATE")!=null)
  					{
  						partnerDetailVO.setEndDate(new java.util.Date(rs.getTimestamp("END_DATE").getTime()));
  					}//end of if(rs.getDate("EMPANELED_DATE")!=null)
                  
                }//end of while(rs.next())
            }//end of if(rs != null)
            return partnerDetailVO;
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "partner");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "partner");
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
					log.error("Error while closing the Resultset in PartnerDAOImpl getPartnerDetail()",sqlExp);
					throw new TTKException(sqlExp, "partner");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in PartnerDAOImpl getPartnerDetail()",sqlExp);
						throw new TTKException(sqlExp, "partner");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in PartnerDAOImpl getPartnerDetail()",sqlExp);
							throw new TTKException(sqlExp, "partner");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "partner");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
    }//end of getHospitalDetail(Long lPtnrSeqId)

  /**
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
    public HashMap getCityInfo() throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt1 = null;
     	PreparedStatement pStmt2 = null;
     	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	CacheObject cacheObject = null;
    	HashMap<Object,Object> hmCityList = null;
    	ArrayList<Object> alCityList = null;
    	String strStateTypeId = "";
    	try{
    		conn = ResourceManager.getConnection();
    		pStmt1=conn.prepareStatement(strStateTypeIdList);
    		pStmt2 = conn.prepareStatement(strCityTypeList);
    		rs1= pStmt1.executeQuery();
    		if(rs1 != null){
    			while(rs1.next()){
    				if(hmCityList == null){
    					hmCityList=new HashMap<Object,Object>();
    				}//end of if(hmCityList == null)
    				strStateTypeId = TTKCommon.checkNull(rs1.getString("STATE_TYPE_ID"));
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
    		throw new TTKException(sqlExp, "partner");
    	}//end of catch (SQLException sqlExp)
    	catch (Exception exp)
    	{
    		throw new TTKException(exp, "partner");
    	}//end of catch (Exception exp)
    	finally
		{
			// Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Resultset in PartnerDAOImpl getCityInfo()",sqlExp);
					throw new TTKException(sqlExp, "partner");
				}//end of catch (SQLException sqlExp)
				finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
				{
					try
					{
						if (rs1 != null) rs1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the First Resultset in PartnerDAOImpl getCityInfo()",sqlExp);
						throw new TTKException(sqlExp, "partner");
					}//end of catch (SQLException sqlExp)
					finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
					{
						try
						{
							if(pStmt2 != null) pStmt2.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Second Statement in PartnerDAOImpl getCityInfo()",sqlExp);
							throw new TTKException(sqlExp, "partner");
						}//end of catch (SQLException sqlExp)
						finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
						{
							try
							{
								if(pStmt1 != null) pStmt1.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the First Statement in PartnerDAOImpl getCityInfo()",sqlExp);
								throw new TTKException(sqlExp, "partner");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
							{
								try{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in PartnerDAOImpl getCityInfo()",sqlExp);
									throw new TTKException(sqlExp, "partner");
								}//end of catch (SQLException sqlExp)
							}//end of finally
						}//end of finally
					}//end of finally 
				}//end of finally 
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "partner");
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
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
    public HashMap getCityInfo(String stateCode) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt1 = null;
     	PreparedStatement pStmt2 = null;
     	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	CacheObject cacheObject = null;
    	HashMap<Object,Object> hmCityList = null;
    	ArrayList<Object> alCityList = null;
    	String strStateTypeId = "";
    	String strCountryId	  =	"";
    	String strIsdCode	  =	"";
    	String strStdCode	  =	"";
    	try{
    		conn = ResourceManager.getConnection();
    		pStmt1=conn.prepareStatement(strStateTypeIdListintX);
    		pStmt2 = conn.prepareStatement(strCityTypeList);
    		pStmt1.setString(1,stateCode);
    		
    		rs1= pStmt1.executeQuery();
    		if(rs1 != null){
    			while(rs1.next()){
    				if(hmCityList == null){
    					hmCityList=new HashMap<Object,Object>();
    				}//end of if(hmCityList == null)
    				strStateTypeId 	= TTKCommon.checkNull(rs1.getString("STATE_TYPE_ID"));    				
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
    		throw new TTKException(sqlExp, "partner");
    	}//end of catch (SQLException sqlExp)
    	catch (Exception exp)
    	{
    		throw new TTKException(exp, "partner");
    	}//end of catch (Exception exp)
    	finally
    	{
    		// Nested Try Catch to ensure resource closure  
    		try // First try closing the result set
    		{
    			try
    			{
    				if (rs2 != null) rs2.close();
    			}//end of try
    			catch (SQLException sqlExp)
    			{
    				log.error("Error while closing the Second Resultset in PartnerDAOImpl getCityInfo()",sqlExp);
    				throw new TTKException(sqlExp, "partner");
    			}//end of catch (SQLException sqlExp)
    			finally // Even if second result set is not closed, control reaches here. Try closing the first resultset now.
    			{
    				try
    				{
    					if (rs1 != null) rs1.close();
    				}//end of try
    				catch (SQLException sqlExp)
    				{
    					log.error("Error while closing the First Resultset in PartnerDAOImpl getCityInfo()",sqlExp);
    					throw new TTKException(sqlExp, "partner");
    				}//end of catch (SQLException sqlExp)
    				finally // Even if First Resultset is not closed, control reaches here. Try closing the Second Statement now.
    				{
    					try
    					{
    						if(pStmt2 != null) pStmt2.close();
    					}//end of try
    					catch (SQLException sqlExp)
    					{
    						log.error("Error while closing the Second Statement in PartnerDAOImpl getCityInfo()",sqlExp);
    						throw new TTKException(sqlExp, "partner");
    					}//end of catch (SQLException sqlExp)
    					finally // Even if Second Statement is not closed, control reaches here. Try closing the First Statement now.
    					{
    						try
    						{
    							if(pStmt1 != null) pStmt1.close();
    						}//end of try
    						catch (SQLException sqlExp)
    						{
    							log.error("Error while closing the First Statement in PartnerDAOImpl getCityInfo()",sqlExp);
    							throw new TTKException(sqlExp, "partner");
    						}//end of catch (SQLException sqlExp)
    						finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
    						{
    							try{
    								if(conn != null) conn.close();
    							}//end of try
    							catch (SQLException sqlExp)
    							{
    								log.error("Error while closing the Connection in PartnerDAOImpl getCityInfo()",sqlExp);
    								throw new TTKException(sqlExp, "partner");
    							}//end of catch (SQLException sqlExp)
    						}//end of finally
    					}//end of finally
    				}//end of finally 
    			}//end of finally 
    		}//end of try
    		catch (TTKException exp)
    		{
    			throw new TTKException(exp, "partner");
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
}//end of getCityInfo(stateCode)

    /**
     * This method adds or updates the partner details
     * The method also calls other methods on DAO to insert/update the partner details to the database
     * @param partnerDetailVO PartnerDetailVO object which contains the hospital details to be added/updated
     * @return long value, Partner Seq Id
     * @exception throws TTKException
     */
    public long addUpdatePartner(PartnerDetailVO partnerDetailVO) throws TTKException {
        long lPtnrSeqId = 0;
        PartnerAuditVO partnerAuditVO = new PartnerAuditVO();
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            partnerAuditVO = partnerDetailVO.getPartnerAuditVO();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdatePartnerInfo);

            if(partnerDetailVO.getPtnrSeqId() != null)
            {
            	 cStmtObject.setLong(PTNR_SEQ_ID,partnerDetailVO.getPtnrSeqId());//PTNR_SEQ_ID
            }//end of  if(partnerDetailVO.getPtnrSeqId() != null)
            else
            {
                cStmtObject.setLong(PTNR_SEQ_ID,0);//PTNR_SEQ_ID
            }//end of else
          //  System.out.println("PTNR_SEQ_ID...."+partnerDetailVO.getPtnrSeqId());
            if(partnerDetailVO.getAddressVO().getAddrSeqId() != null)
            {
                cStmtObject.setLong(ADDR_SEQ_ID,partnerDetailVO.getAddressVO().getAddrSeqId());//ADDR_SEQ_ID
            }//end of if(partnerDetailVO.getAddressVO().getAddrSeqId() != null)
            else
            {
                cStmtObject.setLong(ADDR_SEQ_ID,0);//ADDR_SEQ_ID
            }//end of else
          //  System.out.println("ADDR_SEQ_ID...."+partnerDetailVO.getAddressVO().getAddrSeqId());
            cStmtObject.registerOutParameter(DISCREPANCY_STATUS,Types.VARCHAR);//DISCREPANCY_STATUS           
            cStmtObject.setString(EMPANEL_NUMBER, partnerDetailVO.getEmplNumber());//EMPANEL_NUMBER
			//cStmtObject.setString(EMPANELED_DATE,partnerDetailVO.getEmpanelmentDate());//EMPANELED_DATE
            if(partnerDetailVO.getEmpanelmentDate()!=null && !partnerDetailVO.getEmpanelmentDate().equals(""))
			{
				cStmtObject.setTimestamp(EMPANELED_DATE,(new Timestamp(partnerDetailVO.getEmpanelmentDate().getTime())));//EMPANELED_DATE
			}//end of if(insuranceDetailVO.getEmpanelmentDate()!=null && !insuranceDetailVO.getEmpanelmentDate().equals(""))
			else
			{
				cStmtObject.setTimestamp(EMPANELED_DATE,null);//EMPANELED_DATE
			}//end of else
			cStmtObject.setString(PARTNER_NAME, partnerDetailVO.getPartnerName());//PARTNER_NAME    
            cStmtObject.setString(PTNR_LICENC_NUMB, partnerDetailVO.getIrdaNumber());//IRDA_NUMBER   
            cStmtObject.setString(trade_licenc_numb, partnerDetailVO.getPanNmbr());//PAN_NUMBER
            cStmtObject.setString(TRADE_LICENCE_NAME, partnerDetailVO.getTradeLicenceName());
            cStmtObject.setInt(COUNTRY_ID, Integer.parseInt((partnerDetailVO.getAddressVO().getCountryCode())));// 
            cStmtObject.setString(CURRENCY, partnerDetailVO.getCurrencyAccepted());   
            cStmtObject.setString(OFFICE_NAME, partnerDetailVO.getTTKBranchCode());
            cStmtObject.setLong(IPT_APPROVAL_LIMIT,partnerDetailVO.getLimit());            
            cStmtObject.setString(ADDRESS_1, partnerDetailVO.getAddressVO().getAddress1());//ADDRESS_1
          //  System.out.println("ADDRESS_1...."+partnerDetailVO.getAddressVO().getAddress1());
            cStmtObject.setString(ADDRESS_2, partnerDetailVO.getAddressVO().getAddress2());//ADDRESS_2
          //  System.out.println("ADDRESS_2...."+partnerDetailVO.getAddressVO().getAddress2());
            cStmtObject.setString(ADDRESS_3, partnerDetailVO.getAddressVO().getAddress3());//ADDRESS_3
          //  System.out.println("ADDRESS_3...."+partnerDetailVO.getAddressVO().getAddress3());
            cStmtObject.setString(City_Type_Id, partnerDetailVO.getAddressVO().getCityCode());//City_Type_Id 
          //  System.out.println("City_Type_Id...."+partnerDetailVO.getAddressVO().getCityCode());
            cStmtObject.setString(STATE_TYPE_ID, partnerDetailVO.getAddressVO().getStateCode());//STATE_TYPE_ID
          //  System.out.println("STATE_TYPE_ID...."+partnerDetailVO.getAddressVO().getStateCode());
            cStmtObject.setLong(PIN_CODE, Long.parseLong(partnerDetailVO.getAddressVO().getPinCode()));//PIN_CODE
          //  System.out.println("PIN_CODE...."+partnerDetailVO.getAddressVO().getPinCode());
            cStmtObject.setInt(country_id, Integer.parseInt((partnerDetailVO.getCountryCode())));//COUNTRY_ID - changed to hospitalDetailVO to fetch country as wel on change state
            cStmtObject.setString(STD_CODE, partnerDetailVO.getStdCode());//STD_CODE
            cStmtObject.setString(ISD_CODE, partnerDetailVO.getIsdCode());//ISC_CODE
            cStmtObject.setString(off_phone_no_1, partnerDetailVO.getOfficePhone1());//OFFICE_PHONE1
            cStmtObject.setString(office_fax_no, partnerDetailVO.getFaxNbr());//OFFICE_FAX
            cStmtObject.setString(PRIMARY_EMAIL_ID, partnerDetailVO.getPrimaryEmailId());//PRIMARY_EMAIL_ID
            cStmtObject.setString(pre_emp_mailid, partnerDetailVO.getPreEmpanelMailId());
            cStmtObject.setString(WEBSITE, partnerDetailVO.getWebsite());//WEBSITE
          if(partnerAuditVO != null){
            	cStmtObject.setLong(LOG_SEQ_ID,0); //LOG_SEQ_ID
            	cStmtObject.setString(MOD_REASON_TYPE_ID,partnerAuditVO.getModReson());//MOD_REASON_TYPE_ID
            	if(partnerAuditVO.getRefDate() != null)
        		{
                	cStmtObject.setTimestamp(REFERENCE_DATE,new Timestamp(partnerAuditVO.getRefDate().getTime()));//REFERENCE_DATE
        		}//end of if(partnerAuditVO.getRefDate() != null)
                else
                {
                    cStmtObject.setTimestamp(REFERENCE_DATE, null);//REFERENCE_DATE
                }//end of else
        		cStmtObject.setString(REFERENCE_NO,partnerAuditVO.getRefNmbr());//REFERENCE_NO
        		cStmtObject.setString(REMARKS,partnerAuditVO.getRemarks());//REMARKS_LOG
            }// end of if(partnerAuditVO.getRefDate() != null)
            else{
            	cStmtObject.setLong(LOG_SEQ_ID,0);
            	cStmtObject.setString(MOD_REASON_TYPE_ID,null);
        		cStmtObject.setTimestamp(REFERENCE_DATE, null);
        		cStmtObject.setString(REFERENCE_NO,null);
        		cStmtObject.setString(REMARKS,null);
            }// end of else
          
          if(partnerDetailVO.getFee() != null)
          {
        	  cStmtObject.setLong(AGREE_ADMIN_FEE,partnerDetailVO.getFee());  
          }//end of  if(partnerDetailVO.getPtnrSeqId() != null)
          else
          {
              cStmtObject.setLong(AGREE_ADMIN_FEE,0);//PTNR_SEQ_ID
          }//end of else
          
          
          if(partnerDetailVO.getDiscount() != null)
          {
              cStmtObject.setInt(DISCOUNT,partnerDetailVO.getDiscount().intValue());   
          }//end of  if(partnerDetailVO.getPtnrSeqId() != null)
          else
          {
              cStmtObject.setInt(DISCOUNT,0);//PTNR_SEQ_ID
          }//end of else
          
              
            if(partnerDetailVO.getStartDate()!=null && !partnerDetailVO.getStartDate().equals(""))
			{
				cStmtObject.setTimestamp(START_DATE,(new Timestamp(partnerDetailVO.getStartDate().getTime())));//EMPANELED_DATE
			}//end of if(insuranceDetailVO.getEmpanelmentDate()!=null && !insuranceDetailVO.getEmpanelmentDate().equals(""))
			else
			{
				cStmtObject.setTimestamp(START_DATE,null);//EMPANELED_DATE
			}//end of else   
            
            if(partnerDetailVO.getEndDate()!=null && !partnerDetailVO.getEndDate().equals(""))
			{
				cStmtObject.setTimestamp(END_DATE,(new Timestamp(partnerDetailVO.getEndDate().getTime())));//EMPANELED_DATE
			}//end of if(insuranceDetailVO.getEmpanelmentDate()!=null && !insuranceDetailVO.getEmpanelmentDate().equals(""))
			else
			{
				cStmtObject.setTimestamp(END_DATE,null);//EMPANELED_DATE
			}//end of else                            
        	
            cStmtObject.setLong(added_by, partnerDetailVO.getUpdatedBy());//USER_SEQ_ID
            cStmtObject.registerOutParameter(PTNR_SEQ_ID,Types.BIGINT);
            cStmtObject.registerOutParameter(ADDR_SEQ_ID,Types.BIGINT);
            cStmtObject.registerOutParameter(LOG_SEQ_ID,Types.BIGINT);
            cStmtObject.registerOutParameter(USER_SEQ_ID,Types.BIGINT); 
            cStmtObject.execute();
            lPtnrSeqId = cStmtObject.getLong(PTNR_SEQ_ID);
            conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
        	 sqlExp.printStackTrace();
              throw new TTKException(sqlExp, "partner");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
        	exp.printStackTrace();
              throw new TTKException(exp, "partner");
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
        			log.error("Error while closing the Statement in PartnerDAOImpl addUpdatePartner()",sqlExp);
        			throw new TTKException(sqlExp, "partner");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in PartnerDAOImpl addUpdatePartner()",sqlExp);
        				throw new TTKException(sqlExp, "partner");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "partner");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return lPtnrSeqId;
    }//end of addUpdateHospital(HospitalDetailVO hospitalDetailVO)

    
    
/**
     * This method returns the Arraylist of PreAuthHospitalVO's  which contains State details
     * @param ArrayList object which contains the search criteria
     * @return 
     * @exception throws TTKException
     *//*
    public ArrayList getStateInfo(String countryId) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
    	ArrayList resultList	=	null;
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStateOnCountry);
            pStmt.setString(1, countryId);
            rs = pStmt.executeQuery();
            CacheObject cacheObject	=	null;
            if(rs != null){
               	resultList	=	new ArrayList();
                while (rs.next()) {
					cacheObject = new CacheObject();
					cacheObject.setCacheId(TTKCommon.checkNull(rs.getString("state_type_id")));
					cacheObject.setCacheDesc(TTKCommon.checkNull(rs.getString("state_name")));
					resultList.add(cacheObject);
				}//end of while
            }//end of if(rs != null)
            return (ArrayList)resultList;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl getStateInfo()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl getStateInfo()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl getStateInfo()",sqlExp);
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
    }
    */
    
    /**
     * This method returns the Arraylist of PreAuthHospitalVO's  which contains Hospital details
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of PreAuthHospitalVO object which contains Hospital details
     * @exception throws TTKException
     *//*
    public ArrayList getPreAuthHospitalList(ArrayList alSearchCriteria) throws TTKException {
    	Collection<Object> alResultList = new ArrayList<Object>();
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	ResultSet rs = null;
		PreAuthHospitalVO preAuthHospitalVO = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthHospitalList);
			cStmtObject.setString(1,(String)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.setString(4,(String)alSearchCriteria.get(3));
			cStmtObject.setString(5,(String)alSearchCriteria.get(4));
			cStmtObject.setString(6,(String)alSearchCriteria.get(6));
			cStmtObject.setString(7,(String)alSearchCriteria.get(7));
			cStmtObject.setString(8,(String)alSearchCriteria.get(8));
			cStmtObject.setString(9,(String)alSearchCriteria.get(9));
			cStmtObject.setString(10,(String)alSearchCriteria.get(10));//OPD_4_hosptial
			cStmtObject.setString(11,(String)alSearchCriteria.get(11));//OPD_4_hosptial
			cStmtObject.setLong(12,(Long)alSearchCriteria.get(5));
						
			cStmtObject.registerOutParameter(13,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(13);
			if(rs != null){
				while(rs.next()){
					preAuthHospitalVO = new PreAuthHospitalVO();

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
					preAuthHospitalVO.setCityDesc(TTKCommon.checkNull(rs.getString("CITY_DESCRIPTION")));
					preAuthHospitalVO.setStateName(TTKCommon.checkNull(rs.getString("STATE_NAME")));
					preAuthHospitalVO.setCountryName(TTKCommon.checkNull(rs.getString("COUNTRY_NAME")));
					preAuthHospitalVO.setPincode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
					preAuthHospitalVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					preAuthHospitalVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
					preAuthHospitalVO.setFaxNbr(TTKCommon.checkNull(rs.getString("OFFICE_FAX_NO")));
					preAuthHospitalVO.setEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
					preAuthHospitalVO.setHospRemarks(TTKCommon.checkNull(rs.getString("HOSP_REMARKS")));
					preAuthHospitalVO.setHospStatus(TTKCommon.checkNull(rs.getString("HOSP_STATUS")));
					preAuthHospitalVO.setHospServiceTaxRegnNbr(TTKCommon.checkNull(rs.getString("SERV_TAX_RGN_NUMBER")));
					preAuthHospitalVO.setEmpanelStatusTypeID(TTKCommon.checkNull(rs.getString("EMPANEL_STATUS_TYPE_ID")));
					preAuthHospitalVO.setRating(TTKCommon.checkNull(rs.getString("RATING")));
					

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

					alResultList.add(preAuthHospitalVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl getPreAuthHospitalList()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{

					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl getPreAuthHospitalList()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl getPreAuthHospitalList()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getPreAuthHospitalList(ArrayList alSearchCriteria)

    *//**
     * This method returns the ArrayList, which contains the HospitalVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of HospitalVO object's which contains the hospital empanelled details, which is used in Administration Products/Policies
     * @exception throws TTKException
     *//*
    public ArrayList getEmpanelledHospitalList(ArrayList alSearchObjects) throws TTKException {
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strEmpanelledHospitalList;
        String strIdentifier = "Empanel";
        BaseVO bvo = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Collection<Object> resultList = new ArrayList<Object>();
        String strProdPolicySeqId = "";
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
        	strProdPolicySeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
        	strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strProdPolicySeqId);
        	for(int i=1; i < alSearchObjects.size()-4; i++)
            {
                if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals("")){
                    if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
                    {
                        sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
                    }//end of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
                    else
                    {
                        sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
                    }//end of else
                }//end of if(!((SearchCriteria)alSearchCriteria.get(i)).getValue().equals(""))
            }//end of for(int i=1; i < alSearchObjects.size()-4; i++)
        }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
        log.info("Admn Hospital Dynamic Query is : "+strStaticQuery);
        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    bvo = new HospitalVO();
                    populateBean(bvo,rs,strIdentifier);
                    resultList.add(bvo);
                }//end of while
            }//end of if(rs != null)
            return (ArrayList)resultList;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl getEmpanelledHospitalList()",sqlExp);
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
						log.error("Error while closing the Statement in HospitalDAOImpl getEmpanelledHospitalList()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl getEmpanelledHospitalList()",sqlExp);
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
    }//end of getEmpanelledHospitalList(ArrayList alSearchObjects)

    *//**
     * This method returns the ArrayList, which contains the HospitalVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of HospitalVO object's which contains the hospital Associated/Excluded details,which is used in Administration Products/Policies
     * @exception throws TTKException
     *//*
    public ArrayList getAssociatedExcludedList(ArrayList alSearchObjects) throws TTKException {
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strAssociatedExcludedListProduct;
        String strProdPolicySeqId = "";
        String strStatusGeneralTypeId = "";
        String strIdentifier = "AssocExclude";
        BaseVO bvo = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Collection<Object> resultList = new ArrayList<Object>();
        if(alSearchObjects != null && alSearchObjects.size() > 0)
        {
            strProdPolicySeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
            strStatusGeneralTypeId = ((SearchCriteria)alSearchObjects.get(1)).getValue();

            strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strProdPolicySeqId);
            strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"$",strStatusGeneralTypeId);
            for(int i=2; i < alSearchObjects.size()-4; i++){
                if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals("")){
                    if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
                    {
                        sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
                    }//end of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
                    else
                    {
                        sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
                    }//end of else
                }//end of if(!((SearchCriteria)alSearchCriteria.get(i)).getValue().equals(""))
            }//end of for()
        }//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
        strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
        sbfDynamicQuery = sbfDynamicQuery .append( " )A WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();

        try{
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    bvo = new HospitalVO();
                    populateBean(bvo,rs,strIdentifier);
                    resultList.add(bvo);
                }//end of while
            }//end of if(rs != null)
            return (ArrayList)resultList;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl getAssociatedExcludedList()",sqlExp);
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
						log.error("Error while closing the Statement in HospitalDAOImpl getAssociatedExcludedList()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl getAssociatedExcludedList()",sqlExp);
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
    }//end of getAssociatedExcludedList(ArrayList alSearchObjects)

    */
/**
     * This method delete's the hospital records from the database.
     * @param alHospitalList ArrayList object which contains the hospital sequence id's to be deleted
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     *//*
    public int deleteHospital(ArrayList alHospitalList) throws TTKException {
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
        int iResult =0;
        try
        {
            if(alHospitalList != null && alHospitalList.size() > 0)
            {
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteHospitalInfo);
                cStmtObject.setString(1, (String)alHospitalList.get(0));//string of sequence id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.registerOutParameter(2, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(2);

            }//end of if(alHospitalList != null && alHospitalList.size() > 0)
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
        	 Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl deleteHospital()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl deleteHospital()",sqlExp);
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
        return iResult;
    }//end of deleteHospital(ArrayList alHospitalList)

    *//**
     * This method returns the ArrayList, which contains the Hospital Discrepancy List , which are populated from the database
     * @param hospitalVO HospitalVO object which contains the minimum details about the hospital discrepancy
     * @return ArrayList of HospitalDetailVO object's which contains the hospital discrepancy details
     * @exception throws TTKException
     *//*
    public ArrayList getHospitalDiscrepancyList(HospitalVO hospitalVO) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
    	ArrayList<Object> alDiscrepancy = null;
    	try
    	{
    		conn = ResourceManager.getConnection();

            pStmt = conn.prepareStatement(strHospitalDiscrepancyList);
            if(hospitalVO.getHospSeqId() != null)
            {
                pStmt.setLong(1,hospitalVO.getHospSeqId());
            }//end of if(hospitalVO.getHospSeqId() != null)
            else
            {
                pStmt.setString(1,null);
            }//end of else
            rs = pStmt.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    hospitalVO = new HospitalVO();
                    if(alDiscrepancy == null)
                    {
                        alDiscrepancy = new ArrayList<Object>();
                    }//end of if(alDiscrepancy == null)
                    populateDiscrepancy(hospitalVO, rs);
                    alDiscrepancy.add(hospitalVO);
    			}//end of while(rs.next())
            }//end of if(rs != null)
    		return alDiscrepancy;
    	}// end of try
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl getHospitalDiscrepancyList()",sqlExp);
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
						log.error("Error while closing the Statement in HospitalDAOImpl getHospitalDiscrepancyList()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl getHospitalDiscrepancyList()",sqlExp);
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
    } // end of getHospitalDiscrepancyList(HospitalVO hospitalVO)

    *//**
     * This method populates the basediscrepancy with the information contained in the resultset object
     * @param hospitalVO HospitalVO object which has to be populated
     * @param rs ResultSet object which has to be populated
     * @exception throws TTKException
     *//*
    private void populateDiscrepancy(HospitalVO hospitalVO, ResultSet rs) throws SQLException {


    	if(rs.getString("HOSP_SEQ_ID") != null)
    	{
            hospitalVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
    	}//end of if(rs.getString("HOSP_SEQ_ID") != null)
        hospitalVO.setEmplNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
        hospitalVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
        hospitalVO.setTtkBranch(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
        hospitalVO.setStatus(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
   }//end of populateDiscrepancy(HospitalVO hospitalVO, ResultSet rs)

    *//**
     * This method updates the hospital discrepancy details
     * The method also calls other methods on DAO to update the hospital discrepancy details to the database
     * @param strDiscrepancyType String object which specifies whether the discrepancy flag has to be updated or not
     * @param hospitalVO HospitalVO object which contains the minimum details about the hospital
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     *//*
    public int updateDiscrepancyInfo(String strDiscrepancyType, HospitalVO hospitalVO) throws TTKException
    {
    	int iResult=0;
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUpdateDiscrepancyInfo);

            if(hospitalVO.getHospSeqId()!= null)
            {
            	cStmtObject.setLong(1,hospitalVO.getHospSeqId());//HOSP_SEQ_ID
            }//end of if(hospitalVO.getHospSeqId()!= null)
            else
            {
            	cStmtObject.setLong(1,0);//HOSP_SEQ_ID
            }//end of else
            cStmtObject.setString(2,strDiscrepancyType);//Action
            cStmtObject.setLong(3 , hospitalVO.getUpdatedBy());//USER_ID
            cStmtObject.registerOutParameter(4,Types.INTEGER);//ROW_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(4);

    	}// end of try
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
        	 Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl updateDiscrepancyInfo()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl updateDiscrepancyInfo()",sqlExp);
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
        return iResult;
    } // end of updateDiscrepancyInfo(String strDiscrepancyType, HospitalVO hospitalVO)

    *//**
     * This method dis-associate's the hospital records from the database.
     * @param alHospitalList ArrayList object which contains the hospital product sequence id's, to be dis-associate hospital records
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     *//*
    public int disassociateHospital(ArrayList alHospitalList) throws TTKException {
        int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try
        {

            if(alHospitalList != null && alHospitalList.size() > 0)
            {
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDisAssociateHospitalInfo);
                cStmtObject.setString(1, (String)alHospitalList.get(0));//string of product hospital sequence id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setLong(2, (Long)alHospitalList.get(1));//user sequence id
                cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(3);
            }//end of if(alHospitalList != null && alHospitalList.size() > 0)
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
        	 Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl disassociateHospital()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl disassociateHospital()",sqlExp);
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
        return iResult;
    }//end of disassociateHospital(ArrayList alHospitalList)

    *//**
     * This method associate or excluded the hospital records to the database.
     * @param lProdPolicySeqId Long Product Policy Seq Id which contains the Hospital product Policy seq id
     * @param strStatusGeneralTypeId String which contains the status general type id for associate or exclude the hospital
     * @param alAssocExcludedList ArrayList object which contains the hospital sequence id's to be associated or excluded
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     *//*
    public int associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            if(alAssocExcludedList != null && alAssocExcludedList.size() > 0) {
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssociateOrExcludeHospitalInfo);
                cStmtObject.setString(1, (String)alAssocExcludedList.get(0));//string of hospital sequence id's which are separated with | as separator (Note: String should also end with | at the end)
                cStmtObject.setLong(2,lProdPolicySeqId);//PROD_POLICY_SEQ_ID
                cStmtObject.setString(3,strStatusGeneralTypeId);//STATUS_GENERAL_TYPE_ID
                cStmtObject.setLong(4, (Long)alAssocExcludedList.get(1));//user sequence id
                cStmtObject.registerOutParameter(5, Types.INTEGER);//out parameter which gives the number of records deleted
                cStmtObject.execute();
                iResult = cStmtObject.getInt(5);

            }//end of if(alAssocExcludedList != null && alAssocExcludedList.size() > 0)
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
        	 Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl associateExcludeHospital()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl associateExcludeHospital()",sqlExp);
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
        return iResult;
    }//end of associateExcludeHospital(Long lProdPolicySeqId,String strStatusGeneralTypeId,ArrayList alAssocExcludedList)
    
    *//**
	 * This method saves the Copay Information to the Hospital associated to Product/Policy or all the 
	 * Hospitals associated to Product/Policy 
	 * @param hospCopayVO HospitalCopayVO object which contains the Copay details to be saved
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 *//*
	public int saveAssocHospCopay(ArrayList alHospCopay) throws TTKException {
		int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveAssocHospCopay);
        	if(alHospCopay.get(0) == null){
        		cStmtObject.setString(1,null);
        	}//end of if(hospCopayVO.getProdHospSeqID() == null)
        	else{
        		cStmtObject.setString(1,(String)alHospCopay.get(0));//concatenated with | hospSeqID
        		log.info("concatenated hospSeqIds " + alHospCopay.get(0));
        	}//end of else
        	cStmtObject.setLong(2,(Long)alHospCopay.get(1));//prodPolicySeqID
        	log.info("prodPolicySeqID is " + alHospCopay.get(1));
        	if(alHospCopay.get(2) == null){
        		cStmtObject.setString(3,null);
        	}//end of if(hospCopayVO.getCopayAmt() == null)
        	else{
        		cStmtObject.setBigDecimal(3,(BigDecimal)alHospCopay.get(2));//copayAmt
        		log.info("copayAmt is " + alHospCopay.get(2) );
        	}//end of else
        	if(alHospCopay.get(3) == null){
        		cStmtObject.setString(4,null);
        	}//end of if(hospCopayVO.getCopayPerc()== null)
        	else{
        		cStmtObject.setBigDecimal(4,(BigDecimal)alHospCopay.get(3));//copayPerc()
        		log.info("copayPercentage is " + alHospCopay.get(3));
        	}//en of else
        	
        	cStmtObject.setLong(5,(Long)alHospCopay.get(4));//updated by
        	log.info("updated by is " + alHospCopay.get(4));
        	cStmtObject.registerOutParameter(6, Types.INTEGER);//out parameter which gives the number of records deleted
        	cStmtObject.execute();
        	iResult = cStmtObject.getInt(6);
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
        	 Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl saveAssocHospCopay()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl saveAssocHospCopay()",sqlExp);
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
        return iResult;
	}//end of saveAssocHospCopay(HospitalCopayVO hospCopayVO)
	
	*//**
	 * This method deletes the Copay Information to the selected Hospital(s) associated to Product/Policy 
	 *  
	 * @param alHospCopay ArrayList object which contains the Copay details to be deleted
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 *//*
	public int deleteAssocHospCopay(ArrayList alHospCopay) throws TTKException {
		int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
        	conn = ResourceManager.getConnection();
        	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(steDeleteAssocHospCopay);
        	
        	cStmtObject.setString(1,(String)alHospCopay.get(0));//hosp_seq_id (s)
        	cStmtObject.setLong(2,(Long)alHospCopay.get(1));//prodPolicySeqID
        	log.info("Hosp_Seq_Ids is    : "+alHospCopay.get(0));
        	log.info("prodPolicySeqID is : " +alHospCopay.get(1));
        	cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
        	cStmtObject.execute();
        	iResult = cStmtObject.getInt(3);
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
        	 Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl deleteAssocHospCopay()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl deleteAssocHospCopay()",sqlExp);
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
        return iResult;
	}//end of deleteAssocHospCopay(ArrayList alHospCopay)

    *//*
   
    *//**
     * This method returns the ArrayList, which contains the TdsCertificateVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of TdsCertificateVO's object's which contains the details of the TDS certificate Information
     * @exception throws TTKException
     *//*
	public ArrayList<Object> getAssocCertificateList(ArrayList<Object> alSearchCriteria) throws TTKException
	{
		TdsCertificateVO tdsCertificateVO = null;
    	Collection<Object> alResultList = new ArrayList<Object>();
	    Connection conn = null;
	    CallableStatement cStmtObject=null;
	    ResultSet rs = null;
	    String strFinancialYearTo=null,strFinancialYear=null;
	    Long lngFinancialYear=null,lngFinancialYearTo=null;
	    try{
	    	conn = ResourceManager.getConnection();
	    	//if(gateway.equals("tableDataMouCertificates"))
	    	//{
			//cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetMouFils);
	    	//}
	    	//else
	    	//{
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAssocCertList);
	    	//}
			cStmtObject.setLong(1,(Long)alSearchCriteria.get(0));
			cStmtObject.setString(2,(String)alSearchCriteria.get(1));
			cStmtObject.setString(3,(String)alSearchCriteria.get(2));
			cStmtObject.registerOutParameter(4,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(4);
			if(rs != null){
				while(rs.next()){
					tdsCertificateVO = new TdsCertificateVO();
					
	                if(rs.getString("CERT_SEQ_ID") != null){
	                	tdsCertificateVO.setCertSeqId(rs.getLong("CERT_SEQ_ID"));
	                }//end of if(rs.getString("CERT_SEQ_ID") != null)
	                
	                if(rs.getString("FINANCIAL_YEAR") != null){
	                	strFinancialYear=rs.getString("FINANCIAL_YEAR");
	                	lngFinancialYear=rs.getLong("FINANCIAL_YEAR");
	                	lngFinancialYearTo=lngFinancialYear+1;
	                	log.info("lngFinancialYearTo"+lngFinancialYearTo);
	                	strFinancialYearTo=lngFinancialYearTo.toString();
	                	tdsCertificateVO.setFinancialYear(lngFinancialYear);
	                	tdsCertificateVO.setFinancialYearTo(lngFinancialYearTo);
	                	tdsCertificateVO.setStrFinancialYear(strFinancialYear+"-"+strFinancialYearTo);
	                	log.info("lngFinancialYear"+lngFinancialYear+"lngFinancialYearTo"+lngFinancialYearTo+"strFinancialYearTo"+strFinancialYearTo+"strFinancialYear"+strFinancialYear);
	                }//end of if(rs.getString("FINANCIAL_YEAR") != null)
	                
	                tdsCertificateVO.setEmpanelNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
	                tdsCertificateVO.setDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
	                tdsCertificateVO.setCertPath(TTKCommon.checkNull(rs.getString("CERTI_PATH")));
	                alResultList.add(tdsCertificateVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
	    	return (ArrayList<Object>)alResultList;
	    }//end of try
	    catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "EmpHospital");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "EmpHospital");
		}//end of catch (Exception exp)
		finally
		{
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl getAssocCertificateList()",sqlExp);
					throw new TTKException(sqlExp, "EmpHospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl getAssocCertificateList()",sqlExp);
						throw new TTKException(sqlExp, "EmpHospital");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in HospitalDAOImpl getAssocCertificateList()",sqlExp);
							throw new TTKException(sqlExp, "EmpHospital");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "EmpHospital");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getAssocCertificateList(ArrayList alAssociatedCertificatesList)
    
	
	*//**
     * This method saves the Tds Certificate Details
     * @param tdsCertificateVO TdsCertificateVO object which contains the Tds Certificate Details
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     *//*
	public int saveAssocCertificateInfo(TdsCertificateVO tdsCertificateVO) throws TTKException
	{
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
		int iResult = 0;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveCertificateDocDetail);
			
			if(tdsCertificateVO.getCertSeqId() != null)
			{
				cStmtObject.setLong(1,tdsCertificateVO.getCertSeqId());
			}//end of if(tdsCertificateVO.getCertSeqId() != null)
			else
			{
				cStmtObject.setString(1,null);
			}//end of else	
			
			if(tdsCertificateVO.getHospSeqId()!=null)
			{
				cStmtObject.setLong(2,tdsCertificateVO.getHospSeqId());
			}//end of if(tdsCertificateVO.getHospSeqId()!=null)
			else
			{
				cStmtObject.setLong(2,0);
			}//end else
			
			cStmtObject.setString(3,tdsCertificateVO.getEmpanelNumber());
			cStmtObject.setString(4,tdsCertificateVO.getDescription());
			if(tdsCertificateVO.getFinancialYear()!=null)
			{
				cStmtObject.setLong(5,tdsCertificateVO.getFinancialYear());
			}//end of if(tdsCertificateVO.getFinancialYear()!=null)
			else
			{
				cStmtObject.setLong(5,0);
			}//end of else
			
			cStmtObject.setString(6,tdsCertificateVO.getCertPath());
			if(tdsCertificateVO.getUpdatedBy()!=null)
			{
				cStmtObject.setLong(7,tdsCertificateVO.getUpdatedBy());
			}//end of if(tdsCertificateVO.getUpdatedBy()!=null)
			else
			{
				cStmtObject.setLong(7,0);
			}//end of else
			
			cStmtObject.registerOutParameter(8,OracleTypes.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(8);
			log.info("iResult "+iResult );
			return iResult;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl saveAssocCertificateInfo(TdsCertificateVO tdsCertificateVO)",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl saveAssocCertificateInfo(TdsCertificateVO tdsCertificateVO)",sqlExp);
						throw new TTKException(sqlExp, "hospital");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
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
    }//end of saveAssocCertificateInfo(TdsCertificateVO tdsCertificateVO)
    
	 *//**
     * This method fetches the particular Tds Certificate Info as TdsCertificateVO
     * @param Long CertificationId
     * @return Tds Certificate details in the form of TdsCertificateVO
     * @exception throws TTKException
     *//*
	public TdsCertificateVO getCertiDetailInfo(long iCertSeqId) throws TTKException
	{
		Connection conn=null;
		CallableStatement cStmtObject=null;
		TdsCertificateVO tdsCertificateVO=null;
		ResultSet rs=null;
		Long lngFinancialYear=null,lngFinancialYearTo=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject=(java.sql.CallableStatement)conn.prepareCall(strCertInfodetails);
			cStmtObject.setLong(1,iCertSeqId);
			cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);
			cStmtObject.execute();
			rs=(java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null)
			{
				while(rs.next()){
					tdsCertificateVO=new TdsCertificateVO();
					if(rs.getString("CERT_SEQ_ID")!= null)
					{
						tdsCertificateVO.setCertSeqId(rs.getLong("CERT_SEQ_ID"));
					}//end of if(rs.getString("CERT_SEQ_ID")!= null)

					if(rs.getString("FINANCIAL_YEAR") != null)
					{
						lngFinancialYear=rs.getLong("FINANCIAL_YEAR");
						lngFinancialYearTo=lngFinancialYear+1;
						tdsCertificateVO.setFinancialYear(lngFinancialYear);
						tdsCertificateVO.setFinancialYearTo(lngFinancialYearTo);
					}//end of if(rs.getString("FINANCIAL_YEAR") != null)

					tdsCertificateVO.setEmpanelNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					tdsCertificateVO.setDescription(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					tdsCertificateVO.setCertPath(TTKCommon.checkNull(rs.getString("CERTI_PATH")));
				}//end of while(rs.next())
			}//end of if(rs != null)
			return tdsCertificateVO;
		}catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "hospital");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "hospital");
		}//end of catch (Exception exp)
		finally
		{
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (cStmtObject != null) cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl getCertiDetailInfo(long iCertSeqId)",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl getCertiDetailInfo(long iCertSeqId)",sqlExp);
						throw new TTKException(sqlExp, "hospital");
					}//end of catch (SQLException sqlExp)
				}//end of finally Connection Close
			}//end of finally Statement Close
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
		
    }//end of getCertiDetailInfo(long iCertSeqId)
		
	*//**
     * This method deletes the particulr TdsCertificate Info from the database,based on the certificate seq id's present in the arraylist
     * @param ArrayList alCertificateDelete
     * @return int It signifies the number of rows deleted in the database. 
     * @exception throws TTKException
     *//*
	public int deleteAssocCertificatesInfo(ArrayList<Object> alCertificateDelete,String gateway)throws TTKException
	{
		Connection conn=null;
		int iResult=0;
		CallableStatement cStmtObject=null;
		try{
			conn=ResourceManager.getConnection();
			if(gateway.equals("tableDataMouCertificates"))
			{
				
			cStmtObject=(java.sql.CallableStatement)conn.prepareCall(strDeleteTarrifDetails);
			
			}
			else
			{
			cStmtObject=(java.sql.CallableStatement)conn.prepareCall(strDeleteTdsCertificates);
			}
			cStmtObject.setString(1,(String)alCertificateDelete.get(0));
			cStmtObject.registerOutParameter(2,OracleTypes.INTEGER);
			cStmtObject.execute();
			iResult=cStmtObject.getInt(2);
		}//end of try
		catch(SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "hospital");
		}//end of catch(SQLException sqlExp)
		catch(Exception exp)
		{
			throw new TTKException(exp, "hospital");
		}//end of catch(Exception exp)
		finally
		{
        	 //Nested Try Catch to ensure resource closure  
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl deleteAssocCertificatesInfo()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl deleteAssocCertificatesInfo()",sqlExp);
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
        		System.out.println("closed");
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of deleteAssocCertificatesInfo(ArrayList<Object> alCertificateDelete)
	
	
	//intx File uploadds for MOU
    public int saveMouUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList,Long userSeqId,String hospSeqId,String origFileName,InputStream inputStream,int formFileSize) throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
    		//conn = ResourceManager.getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMouFils);

            cStmtObject.setLong(1,0);
            if(mouDocumentVO.getMouDocSeqID() != null){
            	cStmtObject.setLong(1,mouDocumentVO.getMouDocSeqID());
            }//end of if(mouDocumentVO.getMouDocSeqID() != null)
            else{
            	cStmtObject.setLong(1,0);
            }//end of else
          
            cStmtObject.setString(2, hospSeqId);//HosSeqID
            cStmtObject.setString(3,(String) alFileAUploadList.get(2));//DESC
            cStmtObject.setString(4,mouDocumentVO.getDocName());//Doc Path
            cStmtObject.setString(5,origFileName);//Doc Path
            
            cStmtObject.setLong(6,userSeqId);
            cStmtObject.setBinaryStream(7, inputStream,formFileSize);//FILE INPUT STREAM
            cStmtObject.registerOutParameter(8,Types.INTEGER);//ROW_PROCESSED
            
            cStmtObject.execute();
            
            iResult  = cStmtObject.getInt(8);
            
            
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
        	// Nested Try Catch to ensure resource closure
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl saveWebConfigLinkInfo()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl saveWebConfigLinkInfo()",sqlExp);
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
    	return iResult;
    }//
  //intx File uploadds for MOU
    public int savepreAuthUploads(MOUDocumentVO mouDocumentVO,ArrayList alFileAUploadList,Long userSeqId,String preAuthpSeqId,String origFileName,InputStream inputStream,int formFileSize,Long hospSeqId) throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
    	CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
    		//conn = ResourceManager.getTestConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPreAuthUploads);

            cStmtObject.setLong(1,0);
            cStmtObject.setString(2, preAuthpSeqId);//HosSeqID
            cStmtObject.setString(3,(String) alFileAUploadList.get(2));//DESC
            cStmtObject.setString(4,mouDocumentVO.getDocName());//Doc Path
            cStmtObject.setString(5,origFileName);//Doc Path
	
            cStmtObject.setLong(6,userSeqId);
            cStmtObject.setBinaryStream(7, inputStream,formFileSize);//FILE INPUT STREAM
            cStmtObject.setLong(8, hospSeqId);
            cStmtObject.registerOutParameter(9,Types.INTEGER);//ROW_PROCESSED
    
            cStmtObject.execute();
    
            iResult  = cStmtObject.getInt(9);
            
            
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
        	// Nested Try Catch to ensure resource closure
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (cStmtObject != null) cStmtObject.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl saveWebConfigLinkInfo()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl saveWebConfigLinkInfo()",sqlExp);
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
    	return iResult;
    }//
    
    
  //intx fetch files from table File uploadds for MOU
    public ArrayList<Object> getMouUploadsList(String hospSeqId) throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
    	PreparedStatement pStmt1 = null;
    	ArrayList alMouList	=	new ArrayList();
    	ResultSet rs1 = null;
    	MOUDocumentVO mouDocumentVO	=	null;
    	try{

	    	conn = ResourceManager.getConnection();
	    	pStmt1=conn.prepareStatement(strGetMouFils);
	    	pStmt1.setString(1, hospSeqId);
			rs1= pStmt1.executeQuery();
			
			if(rs1 != null){
				while(rs1.next()){
					
					mouDocumentVO = new MOUDocumentVO();
					mouDocumentVO.setDescription(TTKCommon.checkNull(rs1.getString("DESCRIPTION")));
	                mouDocumentVO.setMouDocPath(TTKCommon.checkNull(rs1.getString("DOCS_PATH")));
	                mouDocumentVO.setMouDocSeqID(((long) rs1.getLong("mou_doc_seq_id")));
	                mouDocumentVO.setFileName(TTKCommon.checkNull(rs1.getString("FILE_NAME")));
	                if(rs1.getString("ADDED_DATE") != null){
	                	mouDocumentVO.setDateTime(rs1.getString("ADDED_DATE"));
					}//end of if(rs.getString("ADDED_DATE") != null)
	                mouDocumentVO.setUserId(TTKCommon.checkNull(rs1.getString("CONTACT_NAME")));
	               // System.out.println("mouDocumentVO::"+mouDocumentVO.toString());
	                alMouList.add(mouDocumentVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
	    	//return (ArrayList<Object>)alMouList;
	    
    		
           
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
        	// Nested Try Catch to ensure resource closure
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (pStmt1 != null) pStmt1.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl saveWebConfigLinkInfo()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl saveWebConfigLinkInfo()",sqlExp);
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
        		pStmt1 = null;
        		conn = null;
        	}//end of finally
        	 return alMouList;
		}//end of finally
    }//
	
    
    public ArrayList<Object> getPreAuthUploadsList(String hospSeqId) throws TTKException {
    	int iResult = 0;
    	Connection conn = null;
    	PreparedStatement pStmt1 = null;
    	CallableStatement cStmtObject=null;
    	ArrayList alMouList	=	new ArrayList();
    	ResultSet rs1 = null;
    	MOUDocumentVO mouDocumentVO	=	null;
    	try{

	    	conn = ResourceManager.getConnection();
	    	cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetPreAuthUploads);
	    	cStmtObject.setString(1, hospSeqId);
	    	cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs1 != null){
				while(rs1.next()){
					
					mouDocumentVO = new MOUDocumentVO();
					mouDocumentVO.setDescription(TTKCommon.checkNull(rs1.getString("DESCRIPTION")));
	                mouDocumentVO.setMouDocPath(TTKCommon.checkNull(rs1.getString("DOCS_PATH")));
	                mouDocumentVO.setMouDocSeqID(((long) rs1.getLong("mou_doc_seq_id")));
	                mouDocumentVO.setFileName(TTKCommon.checkNull(rs1.getString("FILE_NAME")));
	                if(rs1.getString("ADDED_DATE") != null){
	                	mouDocumentVO.setDateTime(rs1.getString("ADDED_DATE"));
					}//end of if(rs.getString("ADDED_DATE") != null)
	                mouDocumentVO.setUserId(TTKCommon.checkNull(rs1.getString("CONTACT_NAME")));
	               // System.out.println("mouDocumentVO::"+mouDocumentVO.toString());
	                alMouList.add(mouDocumentVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
	    	//return (ArrayList<Object>)alMouList;
	    
    		
           
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
        	// Nested Try Catch to ensure resource closure
        	try // First try closing the Statement
        	{
        		try
        		{
        			if (pStmt1 != null) pStmt1.close();
        		}//end of try
        		catch (SQLException sqlExp)
        		{
        			log.error("Error while closing the Statement in HospitalDAOImpl saveWebConfigLinkInfo()",sqlExp);
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
        				log.error("Error while closing the Connection in HospitalDAOImpl saveWebConfigLinkInfo()",sqlExp);
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
        		pStmt1 = null;
        		conn = null;
        	}//end of finally
        	 return alMouList;
		}//end of finally
    }//
    
    *//**
     * This ,ethod to gert the Licence Numb - on autocomplete of name in Add Provider
     *//*
	public ArrayList getLicenceNumbers(String profId,String provName,String strIdentifier)throws TTKException{
		
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ArrayList<String> alLicense	=	null;
		try
		{

			conn = ResourceManager.getConnection();
			if("GetNameByLicence".equalsIgnoreCase(strIdentifier))
    		{
				pStmt = conn.prepareStatement(strGetLicenceNosById);
				profId	=	profId.replace("'", "~");
				pStmt.setString(1,profId);  //provName
    		}
			else
			{
				pStmt = conn.prepareStatement(strGetLicenceNos);
				profId	=	profId.replace("'", "~");
				pStmt.setString(1,provName);  //provName
				pStmt.setString(2,profId);  //profId
			}
			
			
			rs = pStmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					alLicense	=	new ArrayList<String>();
					if("GetNameByLicence".equalsIgnoreCase(strIdentifier))
		    		{
						alLicense.add(rs.getString("provider_namewithId"));
		    		}else{
		    			alLicense.add(rs.getString("provider_id"));
		    		}
				}
	            
	            }
			
			return alLicense;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl getLicenceNumbers()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl getLicenceNumbers()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl getLicenceNumbers()",sqlExp);
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
	}//end of getLicenceNumbers(ArrayList alProfessionals)
	
	
	*//**
     * This ,ethod to gert the Licence Numb - on autocomplete of name in Add Provider
     *//*
	public ArrayList getLicenceNoForPreEmp(String provName)throws TTKException{
		
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ArrayList<String> alLicense	=	null;
		try
		{

			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strGetLicenceNosForPreEmp);
			pStmt.setString(1,provName);  //provName
			
			rs = pStmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					alLicense	=	new ArrayList<String>();
					alLicense.add(rs.getString("provider_id"));
				}
	            
	            }
			
			return alLicense;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl getLicenceNumbers()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl getLicenceNumbers()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl getLicenceNumbers()",sqlExp);
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
	}//end of getLicenceNoForPreEmp(ArrayList alProfessionals)
	
	
	
	 *//**
     * 
     *//*
	public PreRequisiteVO generateCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId)throws TTKException{
		
		ResultSet rs = null;
		ArrayList<String> alCredentials	=	new ArrayList<String>();
		
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        int iContactSeqId =0;
        
        
		try
		{
			conn = ResourceManager.getConnection();
			PreRequisiteVO preRequisiteVO1	=	new PreRequisiteVO();
	        
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveGenerateCredentialsPreRequisite);
            cStmtObject.setInt(1,0); 								  //contactSeqId
            cStmtObject.setString(2,"HOS");  						  //Provider
            cStmtObject.setString(3,TTKCommon.checkNull(preRequisiteVO.getHospName()));    //Hospital name
            cStmtObject.setString(4,TTKCommon.checkNull(preRequisiteVO.getLicenceNo()));   //LicenceNo
            cStmtObject.setString(5,TTKCommon.checkNull(preRequisiteVO.getHospMail()));    //Hospital Mail
            cStmtObject.setString(6,strContactSeqId);  			      //Contact SeqId
            if(preRequisiteVO.getMobileNo()!=null)
            	cStmtObject.setLong(7,preRequisiteVO.getMobileNo());  	  //Mobile No
            else
            	cStmtObject.setString(7,null);  	  //Mobile No
            cStmtObject.registerOutParameter(8,OracleTypes.CURSOR);   //Out CURSOR
            cStmtObject.registerOutParameter(9, Types.INTEGER);		  //out parameter which gives the number of records deleted
            cStmtObject.execute();
            iContactSeqId = cStmtObject.getInt(9);
            rs = (java.sql.ResultSet)cStmtObject.getObject(8);
            if(rs != null){
				while(rs.next()){
					preRequisiteVO1.setHospName(TTKCommon.checkNull(rs.getString("CONTACT_NAME"))); 
					preRequisiteVO1.setLicenceNo(TTKCommon.checkNull(rs.getString("PROVIDER_ID"))); 
					preRequisiteVO1.setHospMail(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID"))); 
					preRequisiteVO1.setUserId(TTKCommon.checkNull(rs.getString("USER_ID"))); 
					preRequisiteVO1.setPassword(TTKCommon.checkNull(rs.getString("PASSWORD"))); 
					preRequisiteVO1.setContactSeqId(TTKCommon.checkNull(rs.getString("CONTACT_SEQ_ID")));
					preRequisiteVO1.setMobileNo((Long)TTKCommon.checkNull(rs.getLong("MOBILE_NO")));
				}
            
            }

			return preRequisiteVO1;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of generateCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO)
	
	
	 *//**
     * 
     *//*
	public int sendCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO,String strContactSeqId,String iContactSeqId)throws TTKException{
		
		ResultSet rs = null;
		ArrayList<String> alCredentials	=	new ArrayList<String>();
		
		Connection conn = null;
    	CallableStatement cStmtObject=null;
        
    	int iResult	=	0;
        
		try
		{
			conn = ResourceManager.getConnection();
			PreRequisiteVO preRequisiteVO1	=	new PreRequisiteVO();
			
	        
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSendMailCredentialsPreRequisite);
            cStmtObject.setString(1,"PROVIDER_USER_ID");			//PROVIDER_USER_ID
            cStmtObject.setString(2,iContactSeqId);					//iContactSeqId
            cStmtObject.setString(3,strContactSeqId);				//Added By
            cStmtObject.registerOutParameter(4,Types.INTEGER);		//LicenceNo
            cStmtObject.setString(5,"");							
            cStmtObject.setString(6,"");							
            cStmtObject.setString(7,"");							
            
            cStmtObject.execute();
            iResult = cStmtObject.getInt(4);
            if(iResult!=0)
            	System.out.println("Mail Not Sent.....");
            else
            	System.out.println("Mail Sent Successfully.....");

			return iResult;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of sendCredentialsforPreRequisite(PreRequisiteVO preRequisiteVO)
	
	*//**
     * 
     *//*
	public ArrayList<Object> getProviderDashBoard()throws TTKException{
		
		ResultSet rs = null;
		Connection conn = null;
    	CallableStatement cStmtObject=null;
		try
		{ 
			ArrayList<Object> alProvDashBoard	=	new ArrayList<Object>();
			PreRequisiteVO preRequisiteVO	=	null;
	        
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strProviderDashBoard);
            cStmtObject.registerOutParameter(1,OracleTypes.CURSOR);	//LicenceNo
            cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);
            
			if(rs != null){
				while(rs.next()){
					preRequisiteVO	=	new PreRequisiteVO();
					preRequisiteVO.setEmpanelEvent(TTKCommon.checkNull(rs.getString("EMPANELMENT_EVENTS").replace('_', ' ')));
					preRequisiteVO.setTotalProviders((Long) TTKCommon.checkNull(rs.getLong("TOTAL_PROVIDERS")));
					preRequisiteVO.setCompletedProviders((Long) TTKCommon.checkNull(rs.getLong("COMPLETED_PROVIDERS")));
					preRequisiteVO.setPendingProviders((Long) TTKCommon.checkNull(rs.getLong("PENDING_PROVIDERS")));
					alProvDashBoard.add(preRequisiteVO);
				}
			}
			return alProvDashBoard;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getProviderDashBoard()
	
	*//**
     * 
     *//*
	public ArrayList<Object> getTotalProviders()throws TTKException{
		
		ResultSet rs = null;
		Connection conn = null;
    	CallableStatement cStmtObject=null;
    	PreparedStatement pStmt	=	null;
		try
		{
			ArrayList<Object> alProvDashBoard	=	null;
			conn = ResourceManager.getConnection();
			PreRequisiteVO preRequisiteVO	=	null;
	        
			
			  pStmt = conn.prepareStatement(strTotalProvidersList);
	            rs = pStmt.executeQuery();
	            if(rs != null){
	                while (rs.next()) {
	                	preRequisiteVO	=	new PreRequisiteVO();
	                	alProvDashBoard	=	new ArrayList<Object>();
	                	preRequisiteVO.setHospName(TTKCommon.checkNull(rs.getString("PROVIDER_NAME")));
	                	alProvDashBoard.add(preRequisiteVO);
	                	//System.out.println("PROVIDER_NAME::"+rs.getString("PROVIDER_NAME"));
	                }
	                
	            }
            
			return alProvDashBoard;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getTotalProviders()
	
	
	*//**
     * 
     *//*
	public String getAllPayersList()throws TTKException{
		
		ResultSet rs = null;
		Connection conn = null;
    	CallableStatement cStmtObject=null;
		try
		{
				String strPayersList	=	"";
				conn = ResourceManager.getConnection();
				cStmtObject = conn.prepareCall(strTotalPayersList);
			  
			  cStmtObject.registerOutParameter(1,Types.VARCHAR);
			  cStmtObject.execute();
			  strPayersList	= cStmtObject.getString(1);
			return strPayersList;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getAllPayersList()
	
	
	
public int getIsdCode(String countryCode)throws TTKException{
		
		ResultSet rs = null;
		Connection conn = null;
    	PreparedStatement pStmt	=	null;
    	int iResult	=	0;
		try
		{
				conn = ResourceManager.getConnection();
				pStmt = conn.prepareStatement(strGetStdOrIsd);
				pStmt.setString(1, countryCode);
	            rs = pStmt.executeQuery();
	            if(rs != null){
	                if (rs.next()) {
	                	iResult	=	rs.getInt(1);
	                }
	                
	            }
            
			return iResult;
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
			 Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs != null) rs.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Resultset in HospitalDAOImpl getIsdCode()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in HospitalDAOImpl getIsdCode()",sqlExp);
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
							log.error("Error while closing the Connection in HospitalDAOImpl getIsdCode()",sqlExp);
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
	}//end of getIsdCode()
		


*//**
 * 
 *//*
public ArrayList<NetworkTypeVO> getNetworkTypeList()throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	CallableStatement cStmtObject=null;
	PreparedStatement pStmt	=	null;
	try
	{
		ArrayList<NetworkTypeVO> alNetworkTypes	=	null;
		conn = ResourceManager.getConnection();
		NetworkTypeVO networkTypeVO	=	null;
        
		
		  pStmt = conn.prepareStatement(strNetworkTypeList);
            rs = pStmt.executeQuery();
            if(rs != null){
            	alNetworkTypes	=	new ArrayList<NetworkTypeVO>();
                while (rs.next()) {
                	networkTypeVO	=	new NetworkTypeVO();
                	networkTypeVO.setNetworkCode(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));
                	networkTypeVO.setNetworkName(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
                	networkTypeVO.setNetworkOrder(TTKCommon.checkNull(rs.getString("SORT_NO")));
                	networkTypeVO.setSeqId(rs.getLong("NETWORK_TYPE_SEQ_ID"));
                	alNetworkTypes.add(networkTypeVO);
                }
                
            }
        
		return alNetworkTypes;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
}//end of getNetworkTypeList<NetworkTypeVO> getNetworkTypeList()


public int saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId)throws TTKException{
	
	ResultSet rs = null;
	ArrayList<String> alCredentials	=	new ArrayList<String>();
	
	Connection conn = null;
	CallableStatement cStmtObject=null;
    
	int iResult	=	0;
    
	try
	{
        conn = ResourceManager.getConnection();
        cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveNetworkTypes);
        cStmtObject.setString(1,networkTypeVO.getNetworkName());			
        cStmtObject.setString(2,networkTypeVO.getNetworkCode().toUpperCase());					
        cStmtObject.setString(3,networkTypeVO.getNetworkOrder());
        cStmtObject.setLong(4,userSeqId);											
        cStmtObject.registerOutParameter(5,Types.INTEGER);		
        
        cStmtObject.execute();
        iResult = cStmtObject.getInt(5);
		return iResult;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl saveNetworkType()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null)	cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl saveNetworkType()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl saveNetworkType()",sqlExp);
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
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
}//end of saveNetworkType(NetworkTypeVO networkTypeVO,Long userSeqId)




public int modifyNetworkType(String[] strResult,Long userSeqId)throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	CallableStatement cStmtObject=null;
	Statement stmt = null;
	int iResult	=	1;
	try
	{
        conn = ResourceManager.getConnection();
        conn.setAutoCommit(false);
        StringBuffer sbfSQL = null;
        stmt = (java.sql.Statement)conn.createStatement();
        if(strResult != null){
        	for(int i=0;i< strResult.length;i++){
        		sbfSQL = new StringBuffer();
        			sbfSQL = sbfSQL.append("'"+strResult[i]+"',");
        			sbfSQL = sbfSQL.append("'"+userSeqId+"')}");
        		stmt.addBatch(strModifyNetworkTypes+sbfSQL.toString());
        		//System.out.println("qry::"+strModifyNetworkTypes+sbfSQL.toString());
    		}//end of for
        	stmt.executeBatch();
        	conn.commit();
        }//end of if(alConfigMemberList != null)
    
        return iResult;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl modifyNetworkType()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null)	cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl modifyNetworkType()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl modifyNetworkType()",sqlExp);
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
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
}//end of modifyNetworkType(String strResult,Long userSeqId)


*//**
 * 
 *//*
public ArrayList<String[]> getHospitalNetworkTypes(Long lngHospSeqId)throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pStmt	=	null;
	try
	{
		ArrayList<String[]> alAssNetworkTypes	=	null;
		conn = ResourceManager.getConnection();
        String[] hospNets	=	null;
		
		  pStmt = conn.prepareStatement(strAssociatedNetworkList);
		  pStmt.setLong(1, lngHospSeqId);
            rs = pStmt.executeQuery();
            if(rs != null){
            	alAssNetworkTypes	=	new ArrayList<String[]>();
                while (rs.next()) {
                	hospNets	=		new String[2];
                	hospNets[0]	=	rs.getString("NETWORK_TYPE");
                    hospNets[1]	=	rs.getString("NETWORK_YN");
                    alAssNetworkTypes.add(hospNets);
                }
                
            }
        
		return alAssNetworkTypes;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
}//end of ArrayList<String[]> getHospitalNetworkTypes()




*//**
 * 
 *//*
public ArrayList<NetworkTypeVO> getNetworkHistory()throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pStmt	=	null;
	try
	{
		ArrayList<NetworkTypeVO> alAssNetworkHistory	=	null;
		conn = ResourceManager.getConnection();
		NetworkTypeVO historyNetsVO	=	null;
		
		  pStmt = conn.prepareStatement(strHistoryNetworkList);
            rs = pStmt.executeQuery();
            if(rs != null){
            	alAssNetworkHistory	=	new ArrayList<NetworkTypeVO>();
                while (rs.next()) {
                	historyNetsVO	=		new NetworkTypeVO();
                	historyNetsVO.setNetworkNameOld(rs.getString(1));
                	historyNetsVO.setNetworkCodeOld(rs.getString(2));
                	historyNetsVO.setNetworkNameNew(rs.getString(3));
                	historyNetsVO.setNetworkCodeNew(rs.getString(4));
                	alAssNetworkHistory.add(historyNetsVO);
                }
                
            }
        
		return alAssNetworkHistory;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
}//end of ArrayList<String[]> getNetworkHistory()





public ArrayList<Object> getHaadEditHeaders(String hospSeqId)throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pStmt	=	null;
	try
	{ 
		ArrayList<Object> alHaadCategories	=	new ArrayList<Object>();
		String[] cache	=	null;
        conn = ResourceManager.getConnection(); 
        pStmt = conn.prepareStatement(strGetHaadCategories);
        rs = pStmt.executeQuery();
        
		if(rs != null){
			while(rs.next()){
				cache	=	new String[2];
				cache[0]	=	(TTKCommon.checkNull(rs.getString("ID")));
				cache[1]	=	(TTKCommon.checkNull(rs.getString("NAME")));
				alHaadCategories.add(cache);
				hospitalDetailVO.setGroupName(rs.getString("ACTIVITY_TYPE"));
				hospitalDetailVO.setFactor(rs.getString("FACTOR"));
				hospitalDetailVO.setBaseRate(rs.getString("BASE_RATE"));
				hospitalDetailVO.setGap(rs.getString("GAP"));
				hospitalDetailVO.setMargin(rs.getString("MARGIN"));
				alHaadCategories.add(hospitalDetailVO);
			}
		}
		
		return alHaadCategories;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
}//end of getHaadCategories()


public String getHaadCategories(String hospSeqId)throws TTKException{
	
	ResultSet rs = null;
	ResultSet rs1 = null;
	Connection conn = null;
	Connection conn1 = null;
	PreparedStatement pStmt	=	null;
	PreparedStatement pStmt1	=	null;
	String primaryNetwork	=	null;;
	try
	{ 
		ArrayList<Object> alHaadCategories	=	new ArrayList<Object>();
		String[] cache	=	null;
		HospitalDetailVO hospitalDetailVO	=	null; 
       // conn = ResourceManager.getConnection(); 
        conn1 = ResourceManager.getConnection();
       // pStmt = conn.prepareStatement(strGetHaadCategories);
        rs = pStmt.executeQuery();
        
		if(rs != null){
			while(rs.next()){
				cache	=	new String[2];
				cache[0]	=	(TTKCommon.checkNull(rs.getString("ID")));
				cache[1]	=	(TTKCommon.checkNull(rs.getString("NAME")));
				alHaadCategories.add(cache);
				hospitalDetailVO.setGroupName(rs.getString("ACTIVITY_TYPE"));
				hospitalDetailVO.setFactor(rs.getString("FACTOR"));
				hospitalDetailVO.setBaseRate(rs.getString("BASE_RATE"));
				hospitalDetailVO.setGap(rs.getString("GAP"));
				hospitalDetailVO.setMargin(rs.getString("MARGIN"));
				alHaadCategories.add(hospitalDetailVO);
			}
		}
		
		//pStmt1 = conn1.prepareStatement(strCheckHaadTariffExixts);THIS IS COMMENTED BECAUSE WE ARE USING THIS TO CHECK DATA EXISTS OR NOT
		pStmt1 = conn1.prepareStatement(strgetPrimaryNetwork);
        pStmt1.setString(1,hospSeqId);
		rs1=pStmt1.executeQuery();
		if(rs1 != null){
			if(rs1.next()){
				primaryNetwork	=	rs1.getString("PRIMARY_NETWORK");
				//alHaadCategories.add(rs1.getString("PRIMARY_NETWORK"));
			}
				
				 * THIS IS COMMENTED BECAUSE WE ARE USING THIS TO CHECK DATA EXISTS OR NOT
				 * 
				 * if(rs1.next()){
					alHaadCategories.add(new String("EXISTS"));
				}else{
					alHaadCategories.add(new String("NOT EXISTS"));
				}
		}
        
		return primaryNetwork;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
				if (rs1 != null) rs1.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
					if (pStmt1 != null)	pStmt1.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
					throw new TTKException(sqlExp, "hospital");
				}//end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches here. Try closing the connection now.
				{
					try
					{
						if(conn != null) conn.close();
						if(conn1 != null) conn1.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
}//end of getHaadCategories()

public ArrayList<Object> getHaadCategories(String hospSeqId,String temp,String primaryNetwork)throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pStmt	=	null;
	try
	{ 
		ArrayList<Object> alHaadCategories	=	new ArrayList<Object>();
		String[] cache	=	null;
		HospitalDetailVO hospitalDetailVO	=	null; 
        conn = ResourceManager.getConnection();
        pStmt = conn.prepareStatement(strGetHaadExistingCategories);
        pStmt.setString(1, hospSeqId);
        pStmt.setString(2, primaryNetwork);
        rs = pStmt.executeQuery();
        
		if(rs != null){
			while(rs.next()){
				hospitalDetailVO	=	new HospitalDetailVO();
				hospitalDetailVO.setGroupName(rs.getString("ACTIVITY_TYPE"));
				hospitalDetailVO.setFactor(rs.getString("FACTOR"));
				hospitalDetailVO.setBaseRate(rs.getString("BASE_RATE"));
				hospitalDetailVO.setGap(rs.getString("GAP"));
				hospitalDetailVO.setMargin(rs.getString("MARGIN"));
				alHaadCategories.add(hospitalDetailVO);
			}
		}
        
		return alHaadCategories;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
}//end of getHaadCategories()

public ArrayList<Object> getHaadColumnHEaders()throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pStmt	=	null;
	try
	{ 
		ArrayList<Object> alHaadCategories	=	new ArrayList<Object>();
        conn = ResourceManager.getConnection();
        pStmt = conn.prepareStatement(strGetHaadColumnHEaders);
        rs = pStmt.executeQuery();
        
		if(rs != null){
			while(rs.next()){
				alHaadCategories.add(rs.getString("COLUMN_NAME"));
			}
		}
		return alHaadCategories;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
}//end of getHaadColumnHEaders()



public int saveHaadCategories(ArrayList<String> alColumnHeaders,String[] cacheId,String[] factor,String[] baseRate,String[] gap,String[] margin,
  		String hospSeqId,Long userSeqId,String networkType,String haadTarrifStartDt,String haadTarrifEndDt)throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	Statement stmt = null;
	int iResult	=	0;
	try
	{
		conn = ResourceManager.getConnection();
		conn.setAutoCommit(false);
		stmt = (java.sql.Statement)conn.createStatement();
		StringBuffer sbfSQL;
		
		if(cacheId != null){
			String temp		=	"";
			//for(int i=0;i<alColumnHeaders.size();i++){
				//temp	=	alColumnHeaders.get(i);
				
				for(int k=0;k<cacheId.length;k++){
					sbfSQL = new StringBuffer();
					sbfSQL = sbfSQL.append("'"+hospSeqId+"',");
					sbfSQL = sbfSQL.append("'"+alColumnHeaders.get(0)+"',");
					sbfSQL = sbfSQL.append("'"+factor[k]+"',");
					sbfSQL = sbfSQL.append("'"+haadTarrifStartDt+"',");
					sbfSQL = sbfSQL.append("'"+haadTarrifEndDt+"',");
					sbfSQL = sbfSQL.append("'"+networkType+"',");
					sbfSQL = sbfSQL.append("'"+cacheId[k]+"',");
					sbfSQL = sbfSQL.append("'"+userSeqId+"')}");
					stmt.addBatch(strSaveHaadFactors+sbfSQL.toString());
				}for(int k=0;k<cacheId.length;k++){
					sbfSQL = new StringBuffer();
					sbfSQL = sbfSQL.append("'"+hospSeqId+"',");
					sbfSQL = sbfSQL.append("'"+alColumnHeaders.get(1)+"',");
					sbfSQL = sbfSQL.append("'"+baseRate[k]+"',");
					sbfSQL = sbfSQL.append("'"+haadTarrifStartDt+"',");
					sbfSQL = sbfSQL.append("'"+haadTarrifEndDt+"',");
					sbfSQL = sbfSQL.append("'"+networkType+"',");
					sbfSQL = sbfSQL.append("'"+cacheId[k]+"',");
					sbfSQL = sbfSQL.append("'"+userSeqId+"')}");
					stmt.addBatch(strSaveHaadFactors+sbfSQL.toString());
				}
				for(int k=0;k<cacheId.length;k++){
					sbfSQL = new StringBuffer();
					sbfSQL = sbfSQL.append("'"+hospSeqId+"',");
					sbfSQL = sbfSQL.append("'"+alColumnHeaders.get(2)+"',");
					sbfSQL = sbfSQL.append("'"+gap[k]+"',");
					sbfSQL = sbfSQL.append("'"+haadTarrifStartDt+"',");
					sbfSQL = sbfSQL.append("'"+haadTarrifEndDt+"',");
					sbfSQL = sbfSQL.append("'"+networkType+"',");
					sbfSQL = sbfSQL.append("'"+cacheId[k]+"',");
					sbfSQL = sbfSQL.append("'"+userSeqId+"')}");
					stmt.addBatch(strSaveHaadFactors+sbfSQL.toString());
				}for(int k=0;k<cacheId.length;k++){
					sbfSQL = new StringBuffer();
					sbfSQL = sbfSQL.append("'"+hospSeqId+"',");
					sbfSQL = sbfSQL.append("'"+alColumnHeaders.get(3)+"',");
					sbfSQL = sbfSQL.append("'"+margin[k]+"',");
					sbfSQL = sbfSQL.append("'"+haadTarrifStartDt+"',");
					sbfSQL = sbfSQL.append("'"+haadTarrifEndDt+"',");
					sbfSQL = sbfSQL.append("'"+networkType+"',");
					sbfSQL = sbfSQL.append("'"+cacheId[k]+"',");
					sbfSQL = sbfSQL.append("'"+userSeqId+"')}");
					stmt.addBatch(strSaveHaadFactors+sbfSQL.toString());
					//System.out.println("sbfSQL::\n"+strSaveHaadFactors+sbfSQL.toString());

				}
				//stmt.addBatch(strSaveDiagnosisDetails+sbfSQL.toString());
			//}//end of for
		}//end of if(diagnosis != null)
		stmt.executeBatch();
		iResult	=	1;
		conn.commit();
		return iResult;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl saveNetworkType()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (stmt != null)	stmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl saveNetworkType()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl saveNetworkType()",sqlExp);
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
			stmt = null;
			conn = null;
		}//end of finally
	}//end of finally
}//end of saveHaadCategories(ArrayList<Object> alHaadCategories)



public int updateHaadCategories(String eligibleNetworks,String haadGroup,String haadfactor,String haadTarrifStartDt,String haadTarrifEndDt,String factorVal,String hosp_seq_id,Long userSeqId)throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	CallableStatement cStmtObject=null;
	int iResult	=	0;
    
	try
	{
        conn = ResourceManager.getConnection();
        cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUpdateHaadFactors);
        cStmtObject.setString(1,eligibleNetworks);			
        cStmtObject.setString(2,haadGroup);			
        cStmtObject.setString(3,haadfactor);		
        cStmtObject.setString(4,TTKCommon.getFormattedDate(haadTarrifStartDt));
        cStmtObject.setString(5,TTKCommon.getFormattedDate(haadTarrifEndDt));				
        cStmtObject.setString(6,factorVal);
        cStmtObject.setString(7,hosp_seq_id);
        cStmtObject.setLong(8,userSeqId);
        cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
        cStmtObject.registerOutParameter(10,Types.INTEGER);
        
        cStmtObject.execute();
        iResult = cStmtObject.getInt(10);
		return iResult;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl saveNetworkType()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (cStmtObject != null)	cStmtObject.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl saveNetworkType()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl saveNetworkType()",sqlExp);
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
			cStmtObject = null;
			conn = null;
		}//end of finally
	}//end of finally
}//end of updateHaadCategories(String haadGroup,String haadfactor,String factorVal,String hosp_seq_id))


public ArrayList<CacheObject> getEligibleNetworks(String hospSeqId)throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pStmt	=	null;
	try
	{ 
		ArrayList<CacheObject> alEligibleNetworks	=	new ArrayList<CacheObject>();
		CacheObject cache	=null;
		
        conn = ResourceManager.getConnection(); 
        pStmt = conn.prepareStatement(strEligibleNetworks);

        pStmt.setString(1, hospSeqId);
        
        rs = pStmt.executeQuery();
        
		if(rs != null){
			while(rs.next()){
				cache	=	new CacheObject();
				cache.setCacheId(TTKCommon.checkNull(rs.getString("GENERAL_TYPE_ID")));
				cache.setCacheDesc(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
				alEligibleNetworks.add(cache);
			}
		}
		
        
		return alEligibleNetworks;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
}//end of getEligibleNetworks()





public ArrayList<Object> getHaadFactorsHostory(String hospSeqId)throws TTKException{
	
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pStmt	=	null;
	try
	{ 
		ArrayList<Object> alHistoryOfTariffUpdates	=	new ArrayList<Object>();
        conn = ResourceManager.getConnection();
        pStmt = conn.prepareStatement(strGetHaadHistory);
        pStmt.setString(1, hospSeqId);
        rs = pStmt.executeQuery();
        HaadTariffFactorHistoryVO historyVO	=	null;
		if(rs != null){
			while(rs.next()){
				historyVO	=	new HaadTariffFactorHistoryVO();
				historyVO.setService(rs.getString("ACTIVITY_TYPE"));
				historyVO.setFactor(rs.getString("FACTOR"));
				historyVO.setNewValue(rs.getString("VALUE"));
				historyVO.setAddedDate(rs.getDate("ADDED_DATE"));
				historyVO.setUpdatedByName(rs.getString("ADDED_BY"));
				historyVO.setNetwork(rs.getString("NETWORK_TYPE"));
				
				alHistoryOfTariffUpdates.add(historyVO);
			}
		}
		return alHistoryOfTariffUpdates;
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
		 Nested Try Catch to ensure resource closure  
		try // First try closing the result set
		{
			try
			{
				if (rs != null) rs.close();
			}//end of try
			catch (SQLException sqlExp)
			{
				log.error("Error while closing the Resultset in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
				throw new TTKException(sqlExp, "hospital");
			}//end of catch (SQLException sqlExp)
			finally // Even if result set is not closed, control reaches here. Try closing the statement now.
			{
				try
				{
					if (pStmt != null)	pStmt.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Statement in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
						log.error("Error while closing the Connection in HospitalDAOImpl generateCredentialsforPreRequisite()",sqlExp);
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
}//end of getHaadFactorsHostory()
*/}//end of class HopitalDAOImpl