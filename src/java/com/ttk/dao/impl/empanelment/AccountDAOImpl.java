/**
 * @ (#) AccountDAOImpl.java Sep 29, 2005
 * Project      : TTK HealthCare Services
 * File         : AccountDAOImpl
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Sep 29, 2005
 *
 * @author       :  Ramakrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.empanelment;

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

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.AccountDetailVO;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.HospitalAuditVO;

public class AccountDAOImpl implements BaseDAO, Serializable {

	private static Logger log = Logger.getLogger(AccountDAOImpl.class);

	private static final int HOSP_BANK_SEQ_ID = 1;
	private static final int ADDR_SEQ_ID = 2;
	private static final int POD_ADDR_SEQ_ID = 3;
	private static final int HOSP_SEQ_ID = 4;
	private static final int BANK_NAME = 5;
	private static final int ACCOUNT_NUMBER = 6;
	private static final int ACCOUNT_IN_NAME_OF = 7;
	private static final int BRANCH_NAME = 8;
	private static final int ADDRESS_1 = 9;
	private static final int ADDRESS_2 = 10;
	private static final int ADDRESS_3 = 11;
	private static final int CITY_TYPE_ID = 12;
	private static final int STATE_TYPE_ID = 13;
	private static final int PIN_CODE = 14;
	private static final int COUNTRY_ID = 15;
	private static final int MANAGEMENT_NAME = 16;
	private static final int ISSUE_CHEQUES_TYPE_ID = 17;
	private static final int HOSP_GNRL_SEQ_ID = 18;
	private static final int EMPANEL_FEES_CHRG_YN = 19;
	private static final int PAY_ORDER_TYPE_ID = 20;
	private static final int PAY_ORDER_NUMBER = 21;
	private static final int PAY_ORDER_AMOUNT = 22;
	private static final int PAY_ORDER_RECEIVED_DATE = 23;
	private static final int POD_BANK_NAME = 24;
    private static final int CHECK_ISSUED_DATE = 25;
    private static final int BANK_GUANT_SEQ_ID = 26;
    private static final int EMPANEL_SEQ_ID = 27;
    private static final int BANK_GUANT_REQD_YN = 28;
	private static final int POD_ADDRESS_1 = 29;
	private static final int POD_ADDRESS_2 = 30;
	private static final int POD_ADDRESS_3 = 31;
	private static final int POD_CITY_TYPE_ID = 32;
	private static final int POD_STATE_TYPE_ID = 33;
	private static final int POD_PIN_CODE = 34;
	private static final int POD_COUNTRY_ID = 35;
    private static final int LOG_SEQ_ID = 36;
    private static final int MOD_REASON_TYPE_ID = 37;
    private static final int REFERENCE_DATE = 38;
    private static final int REFERENCE_NO = 39;
    private static final int REMARKS_LOG = 40;
	private static final int USER_SEQ_ID  = 41;

	private static final int START_DATE  = 42;
	private static final int END_DATE = 43;

	private static final int IBAN_NO = 44;
	private static final int SWIFT_CODE = 45;
	private static final int MODE_OF_PAYMENT = 46;

	private static final int ROW_PROCESSED = 47;
	// Modified Changes ENCRYPT and Decrypt KOC11ED
	// private static final String strAccountDetail =
	// "SELECT c.hosp_bank_seq_id,d.addr_seq_id addr_seq_id,F.addr_seq_id pod_addr_seq_id, c.hosp_seq_id,c.bank_name bank_name,account_number,account_in_name_of,branch_name,d.address_1 address_1,d.address_2 address_2,d.address_3 address_3,d.city_type_id city_type_id,d.state_type_id state_type_id,d.pin_code pin_code,d.country_id country_id,c.management_name,issue_cheques_type_id,empanel_fees_chrg_yn,pay_order_type_id,pay_order_number,pay_order_amount,pay_order_received_date,e.hosp_gnrl_seq_id,e.bank_name pod_bank_name,e.check_issued_date,f.address_1 pod_address_1,f.address_2 pod_address_2,f.address_3 pod_address_3,f.city_type_id pod_city_type_id,f.state_type_id pod_state_type_id,f.pin_code pod_pin_code,f.country_id pod_country_id,g.bank_guant_seq_id,g.empanel_seq_id,g.bank_guant_req_yn,g.bank_name BGUANT_BANK_NAME,g.amount,g.commencement_date,g.expiry_date,a.tpa_regist_date  FROM ( TPA_HOSP_INFO A INNER JOIN TPA_HOSP_EMPANEL_STATUS B ON A.Hosp_Seq_Id = B.Hosp_Seq_Id AND B.ACTIVE_YN = 'Y') LEFT OUTER JOIN TPA_HOSP_ACCOUNT_DETAILS C ON A.Hosp_Seq_Id = C.Hosp_Seq_Id LEFT OUTER JOIN TPA_HOSP_ADDRESS D ON C.HOSP_BANK_SEQ_ID = D.Hosp_Bank_Seq_Id LEFT OUTER JOIN TPA_HOSP_GENERAL_DTL E ON B.Empanel_Seq_Id = E.Empanel_Seq_Id LEFT OUTER JOIN TPA_HOSP_ADDRESS F ON E.HOSP_GNRL_SEQ_ID = F.Hosp_Gnrl_Seq_Id LEFT OUTER JOIN TPA_HOSP_BANK_GUARANTEE G ON B.Empanel_Seq_Id = G.EMPANEL_SEQ_ID WHERE A.HOSP_SEQ_ID = ?";
	//  private static final String strAccountDetail = "SELECT C.HOSP_BANK_SEQ_ID,D.ADDR_SEQ_ID ADDR_SEQ_ID,F.ADDR_SEQ_ID POD_ADDR_SEQ_ID,C.HOSP_SEQ_ID,TTK_UTIL_PKG.FN_DECRYPT(C.BANK_NAME) BANK_NAME,TTK_UTIL_PKG.FN_DECRYPT(ACCOUNT_NUMBER) ACCOUNT_NUMBER,ACCOUNT_IN_NAME_OF,TTK_UTIL_PKG.FN_DECRYPT(BRANCH_NAME) AS BRANCH_NAME,D.ADDRESS_1 ADDRESS_1,D.ADDRESS_2 ADDRESS_2,D.ADDRESS_3 ADDRESS_3,D.CITY_TYPE_ID CITY_TYPE_ID,D.STATE_TYPE_ID STATE_TYPE_ID,D.PIN_CODE PIN_CODE,D.COUNTRY_ID COUNTRY_ID,C.MANAGEMENT_NAME,ISSUE_CHEQUES_TYPE_ID,EMPANEL_FEES_CHRG_YN,PAY_ORDER_TYPE_ID,PAY_ORDER_NUMBER,PAY_ORDER_AMOUNT,PAY_ORDER_RECEIVED_DATE,E.HOSP_GNRL_SEQ_ID,E.BANK_NAME POD_BANK_NAME,E.CHECK_ISSUED_DATE,F.ADDRESS_1 POD_ADDRESS_1,F.ADDRESS_2 POD_ADDRESS_2,F.ADDRESS_3 POD_ADDRESS_3,F.CITY_TYPE_ID POD_CITY_TYPE_ID,F.STATE_TYPE_ID POD_STATE_TYPE_ID,F.PIN_CODE POD_PIN_CODE,F.COUNTRY_ID POD_COUNTRY_ID,G.BANK_GUANT_SEQ_ID,G.EMPANEL_SEQ_ID,G.BANK_GUANT_REQ_YN,G.BANK_NAME BGUANT_BANK_NAME,G.AMOUNT,G.COMMENCEMENT_DATE,G.EXPIRY_DATE,A.TPA_REGIST_DATE,C.START_DATE,C.END_DATE,TTK_UTIL_PKG.FN_DECRYPT(C.BANK_IFSC) BANK_IFSC,A.EMPANEL_NUMBER,C.BANK_IFSC,TTK_UTIL_PKG.FN_DECRYPT(C.BANK_MICR) BANK_MICR,C.REVIEW_YN,CASE WHEN C.REVIEW_YN = 'Y' THEN NVL(C.UPDATED_DATE, C.ADDED_DATE) END AS REVIEWED_DATE, CASE WHEN C.REVIEW_YN = 'Y' THEN H.CONTACT_NAME END AS REVIEWED_BY, C.MODE_OF_PAYMENT FROM (TPA_HOSP_INFO A INNER JOIN TPA_HOSP_EMPANEL_STATUS B ON A.HOSP_SEQ_ID = B.HOSP_SEQ_ID AND B.ACTIVE_YN = 'Y') LEFT OUTER JOIN TPA_HOSP_ACCOUNT_DETAILS C ON A.HOSP_SEQ_ID = C.HOSP_SEQ_ID LEFT OUTER JOIN TPA_HOSP_ADDRESS D ON C.HOSP_BANK_SEQ_ID = D.HOSP_BANK_SEQ_ID LEFT OUTER JOIN TPA_HOSP_GENERAL_DTL E ON B.EMPANEL_SEQ_ID = E.EMPANEL_SEQ_ID LEFT OUTER JOIN TPA_HOSP_ADDRESS F ON E.HOSP_GNRL_SEQ_ID = F.HOSP_GNRL_SEQ_ID LEFT OUTER JOIN TPA_HOSP_BANK_GUARANTEE G ON B.EMPANEL_SEQ_ID = G.EMPANEL_SEQ_ID LEFT OUTER JOIN TPA_USER_CONTACTS H ON H.CONTACT_SEQ_ID = C.UPDATED_BY WHERE A.HOSP_SEQ_ID = ?";
	//  private static final String strAccountDetail = "SELECT C.HOSP_BANK_SEQ_ID,D.ADDR_SEQ_ID ADDR_SEQ_ID,F.ADDR_SEQ_ID POD_ADDR_SEQ_ID,C.HOSP_SEQ_ID,public.PGP_SYM_DECRYPT(C.BANK_NAME::bytea , 'AES_KEY')::varchar AS BANK_NAME,public.PGP_SYM_DECRYPT(ACCOUNT_NUMBER::bytea , 'AES_KEY')::varchar AS ACCOUNT_NUMBER,ACCOUNT_IN_NAME_OF,public.PGP_SYM_DECRYPT(BRANCH_NAME::bytea , 'AES_KEY')::varchar AS BRANCH_NAME,D.ADDRESS_1 ADDRESS_1,D.ADDRESS_2 ADDRESS_2,D.ADDRESS_3 ADDRESS_3,D.CITY_TYPE_ID CITY_TYPE_ID,D.STATE_TYPE_ID STATE_TYPE_ID,D.PIN_CODE PIN_CODE,D.COUNTRY_ID COUNTRY_ID,C.MANAGEMENT_NAME,ISSUE_CHEQUES_TYPE_ID,EMPANEL_FEES_CHRG_YN,PAY_ORDER_TYPE_ID,PAY_ORDER_NUMBER,PAY_ORDER_AMOUNT,PAY_ORDER_RECEIVED_DATE,E.HOSP_GNRL_SEQ_ID,E.BANK_NAME POD_BANK_NAME,E.CHECK_ISSUED_DATE,F.ADDRESS_1 POD_ADDRESS_1,F.ADDRESS_2 POD_ADDRESS_2,F.ADDRESS_3 POD_ADDRESS_3,F.CITY_TYPE_ID POD_CITY_TYPE_ID,F.STATE_TYPE_ID POD_STATE_TYPE_ID,F.PIN_CODE POD_PIN_CODE,F.COUNTRY_ID POD_COUNTRY_ID,G.BANK_GUANT_SEQ_ID,G.EMPANEL_SEQ_ID,G.BANK_GUANT_REQ_YN,G.BANK_NAME BGUANT_BANK_NAME,G.AMOUNT,G.COMMENCEMENT_DATE,G.EXPIRY_DATE,A.TPA_REGIST_DATE,C.START_DATE,C.END_DATE,public.PGP_SYM_DECRYPT(C.BANK_IFSC::bytea , 'AES_KEY')::varchar BANK_IFSC,A.EMPANEL_NUMBER,C.BANK_IFSC,public.PGP_SYM_DECRYPT(C.BANK_MICR::bytea , 'AES_KEY')::varchar BANK_MICR,C.REVIEW_YN,CASE WHEN C.REVIEW_YN = 'Y' THEN COALESCE(C.UPDATED_DATE, C.ADDED_DATE) END AS REVIEWED_DATE, CASE WHEN C.REVIEW_YN = 'Y' THEN H.CONTACT_NAME END AS REVIEWED_BY, C.MODE_OF_PAYMENT FROM (app.TPA_HOSP_INFO A INNER JOIN app.TPA_HOSP_EMPANEL_STATUS B ON A.HOSP_SEQ_ID = B.HOSP_SEQ_ID AND B.ACTIVE_YN = 'Y') LEFT OUTER JOIN app.TPA_HOSP_ACCOUNT_DETAILS C ON A.HOSP_SEQ_ID = C.HOSP_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_ADDRESS D ON C.HOSP_BANK_SEQ_ID = D.HOSP_BANK_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_GENERAL_DTL E ON B.EMPANEL_SEQ_ID = E.EMPANEL_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_ADDRESS F ON E.HOSP_GNRL_SEQ_ID = F.HOSP_GNRL_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_BANK_GUARANTEE G ON B.EMPANEL_SEQ_ID = G.EMPANEL_SEQ_ID LEFT OUTER JOIN app.TPA_USER_CONTACTS H ON H.CONTACT_SEQ_ID = C.UPDATED_BY WHERE A.HOSP_SEQ_ID = ?";
	  private static final String strAccountDetail = "SELECT C.HOSP_BANK_SEQ_ID,D.ADDR_SEQ_ID ADDR_SEQ_ID,F.ADDR_SEQ_ID POD_ADDR_SEQ_ID,C.HOSP_SEQ_ID,intx.ttk_util_pkg_fn_decrypt(C.BANK_NAME)::varchar AS BANK_NAME,intx.ttk_util_pkg_fn_decrypt(ACCOUNT_NUMBER)::varchar AS ACCOUNT_NUMBER,ACCOUNT_IN_NAME_OF,intx.ttk_util_pkg_fn_decrypt(BRANCH_NAME)::varchar AS BRANCH_NAME,D.ADDRESS_1 ADDRESS_1,D.ADDRESS_2 ADDRESS_2,D.ADDRESS_3 ADDRESS_3,D.CITY_TYPE_ID CITY_TYPE_ID,D.STATE_TYPE_ID STATE_TYPE_ID,D.PIN_CODE PIN_CODE,D.COUNTRY_ID COUNTRY_ID,C.MANAGEMENT_NAME,ISSUE_CHEQUES_TYPE_ID,EMPANEL_FEES_CHRG_YN,PAY_ORDER_TYPE_ID,PAY_ORDER_NUMBER,PAY_ORDER_AMOUNT,PAY_ORDER_RECEIVED_DATE,E.HOSP_GNRL_SEQ_ID,E.BANK_NAME POD_BANK_NAME,E.CHECK_ISSUED_DATE,F.ADDRESS_1 POD_ADDRESS_1,F.ADDRESS_2 POD_ADDRESS_2,F.ADDRESS_3 POD_ADDRESS_3,F.CITY_TYPE_ID POD_CITY_TYPE_ID,F.STATE_TYPE_ID POD_STATE_TYPE_ID,F.PIN_CODE POD_PIN_CODE,F.COUNTRY_ID POD_COUNTRY_ID,G.BANK_GUANT_SEQ_ID,G.EMPANEL_SEQ_ID,G.BANK_GUANT_REQ_YN,G.BANK_NAME BGUANT_BANK_NAME,G.AMOUNT,G.COMMENCEMENT_DATE,G.EXPIRY_DATE,A.TPA_REGIST_DATE,C.START_DATE,C.END_DATE,intx.ttk_util_pkg_fn_decrypt(C.BANK_IFSC) BANK_IFSC,A.EMPANEL_NUMBER,intx.ttk_util_pkg_fn_decrypt(C.BANK_IFSC) as BANK_IFSC,intx.ttk_util_pkg_fn_decrypt(C.BANK_MICR) BANK_MICR,C.REVIEW_YN,CASE WHEN C.REVIEW_YN = 'Y' THEN COALESCE(C.UPDATED_DATE, C.ADDED_DATE) END AS REVIEWED_DATE, CASE WHEN C.REVIEW_YN = 'Y' THEN H.CONTACT_NAME END AS REVIEWED_BY, C.MODE_OF_PAYMENT FROM (app.TPA_HOSP_INFO A INNER JOIN app.TPA_HOSP_EMPANEL_STATUS B ON A.HOSP_SEQ_ID = B.HOSP_SEQ_ID AND B.ACTIVE_YN = 'Y') LEFT OUTER JOIN app.TPA_HOSP_ACCOUNT_DETAILS C ON A.HOSP_SEQ_ID = C.HOSP_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_ADDRESS D ON C.HOSP_BANK_SEQ_ID = D.HOSP_BANK_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_GENERAL_DTL E ON B.EMPANEL_SEQ_ID = E.EMPANEL_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_ADDRESS F ON E.HOSP_GNRL_SEQ_ID = F.HOSP_GNRL_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_BANK_GUARANTEE G ON B.EMPANEL_SEQ_ID = G.EMPANEL_SEQ_ID LEFT OUTER JOIN app.TPA_USER_CONTACTS H ON H.CONTACT_SEQ_ID = C.UPDATED_BY WHERE A.HOSP_SEQ_ID = ?";
	//private static final String strAddUpdateAccountInfo = "{CALL HOSPITAL_EMPANEL_PKG.PR_ACCOUNT_INFO_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strAddUpdateAccountInfo = "{CALL hospital_empanel_pkg_pr_account_info_save(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strEmpanelmentFeeHistoryList = "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER (ORDER BY #, ROWNUM) Q  FROM (SELECT GDTL.EMPANEL_SEQ_ID,GDTL.PAY_ORDER_AMOUNT AS AMOUNT,GDTL.BANK_NAME,ESTATUS.FROM_DATE,ESTATUS.TO_DATE,GDTL.EMPANEL_FEES_CHRG_YN FROM APP.TPA_HOSP_GENERAL_DTL GDTL,APP.TPA_HOSP_EMPANEL_STATUS ESTATUS WHERE ESTATUS.EMPANEL_SEQ_ID = GDTL.EMPANEL_SEQ_ID AND ESTATUS.ACTIVE_YN = 'N' AND GDTL.EMPANEL_FEES_CHRG_YN = 'Y' AND GDTL.HOSP_SEQ_ID = ? ) A";
    private static final String strBankGuaranteeHistoryList = "SELECT * FROM ( SELECT A.* ,DENSE_RANK() OVER (ORDER BY #, ROWNUM) Q  FROM ( SELECT BGUNT.BANK_GUANT_SEQ_ID,BGUNT.AMOUNT,BGUNT.BANK_NAME,ESTATUS.FROM_DATE,ESTATUS.TO_DATE,BGUNT.BANK_GUANT_REQ_YN FROM APP.TPA_HOSP_BANK_GUARANTEE BGUNT,APP.TPA_HOSP_EMPANEL_STATUS ESTATUS WHERE ESTATUS.EMPANEL_SEQ_ID = BGUNT.EMPANEL_SEQ_ID  AND BGUNT.BANK_GUANT_REQ_YN = 'Y' AND ESTATUS.HOSP_SEQ_ID = ? ) A";
    private static final String strEmpanelmentFeeHistoryDetail = "select empanel_fees_chrg_yn,podcode.description,pay_order_number,pay_order_amount,pay_order_received_date,bank_name,check_issued_date,poadd.address_1,poadd.address_2,poadd.address_3,statecode.state_name,citycode.city_description,poadd.pin_code,countrycode.country_name from tpa_hosp_general_dtl gdtl join tpa_hosp_address poadd on (gdtl.hosp_gnrl_seq_id = poadd.hosp_gnrl_seq_id) left outer join tpa_pay_order_code podcode on(gdtl.pay_order_type_id = podcode.pay_order_type_id) left outer join tpa_city_code citycode on (poadd.city_type_id = citycode.city_type_id) left outer join tpa_state_code statecode on (poadd.state_type_id = statecode.state_type_id) left outer join tpa_country_code countrycode on (poadd.country_id = countrycode.country_id) where gdtl.empanel_seq_id = ?";
    private static final String strBankGuaranteeHistoryDetail = "SELECT gunt.bank_guant_req_yn,gunt.bank_name,gunt.amount,gunt.commencement_date,gunt.expiry_date FROM tpa_hosp_bank_guarantee gunt  WHERE bank_guant_seq_id = ?";

    private static final String strAccountMultipleDetail = "SELECT intx.ttk_util_pkg_fn_decrypt(h.bank_name)::varchar bank_name,intx.ttk_util_pkg_fn_decrypt(h.account_number)::varchar account_number,intx.ttk_util_pkg_fn_decrypt(h.bank_ifsc)::varchar as ifsc_code,h.start_date,h.end_date FROM APP.Tpa_Hosp_Account_History h where h.hosp_seq_id=?";
  //  private static final String strAccountMultipleDetail = "SELECT ttk_util_pkg.fn_decrypt(h.bank_name) bank_name,ttk_util_pkg.fn_decrypt(h.account_number) account_number,ttk_util_pkg.fn_decrypt(h.bank_ifsc) as ifsc_code,h.start_date,h.end_date FROM APP.Tpa_Hosp_Account_History h where h.hosp_seq_id=?";

	private static final String strPartnerAccountVariousDetails = "SELECT public.PGP_SYM_DECRYPT(h.BANK_NAME::bytea, 'AES_KEY')bank_name, public.PGP_SYM_DECRYPT(h.account_number::bytea, 'AES_KEY') account_number, public.PGP_SYM_DECRYPT(h.bank_ifsc::bytea, 'AES_KEY') as ifsc_code ,h.start_date,h.end_date FROM APP.Tpa_Partner_Account_History h where h.Ptnr_Seq_Id= ?";

	private static final String strPartnerAccountDetail = "SELECT C.PTNR_BANK_SEQ_ID,D.ADDR_SEQ_ID ADDR_SEQ_ID, F.ADDR_SEQ_ID POD_ADDR_SEQ_ID,c.route_code, C.PTNR_SEQ_ID, public.PGP_SYM_DECRYPT(C.BANK_NAME::bytea ,'AES_KEY')BANK_NAME, public.PGP_SYM_DECRYPT(ACCOUNT_NUMBER::bytea, 'AES_KEY')ACCOUNT_NUMBER, ACCOUNT_IN_NAME_OF, public.PGP_SYM_DECRYPT(BRANCH_NAME::bytea, 'AES_KEY') AS BRANCH_NAME, D.ADDRESS_1 ADDRESS_1, D.ADDRESS_2 ADDRESS_2, D.ADDRESS_3 ADDRESS_3, D.CITY_TYPE_ID CITY_TYPE_ID, D.STATE_TYPE_ID STATE_TYPE_ID, D.PIN_CODE PIN_CODE, D.COUNTRY_ID COUNTRY_ID, EMPANEL_FEES_CHRG_YN, PAY_ORDER_TYPE_ID, PAY_ORDER_NUMBER, PAY_ORDER_AMOUNT,PAY_ORDER_RECEIVED_DATE, E.PTNR_GNRL_SEQ_ID, E.BANK_NAME POD_BANK_NAME, E.CHECK_ISSUED_DATE,F.ADDRESS_1 POD_ADDRESS_1, F.ADDRESS_2 POD_ADDRESS_2, F.ADDRESS_3 POD_ADDRESS_3, F.CITY_TYPE_ID POD_CITY_TYPE_ID, F.STATE_TYPE_ID POD_STATE_TYPE_ID, F.PIN_CODE POD_PIN_CODE,F.COUNTRY_ID POD_COUNTRY_ID, G.BANK_GUANT_SEQ_ID, G.EMPANEL_SEQ_ID, G.BANK_GUANT_REQ_YN,G.BANK_NAME BGUANT_BANK_NAME, G.AMOUNT, G.COMMENCEMENT_DATE, G.EXPIRY_DATE,C.START_DATE, C.END_DATE, C.BANK_IFSC BANK_IFSC, A.EMPANEL_NUMBER, C.BANK_IFSC, C.BANK_MICR BANK_MICR, C.REVIEW_YN, CASE WHEN C.REVIEW_YN = 'Y' THEN COALESCE(C.UPDATED_DATE, C.ADDED_DATE) END AS REVIEWED_DATE, CASE WHEN C.REVIEW_YN = 'Y' THEN H.CONTACT_NAME END AS REVIEWED_BY, C.MODE_OF_PAYMENT FROM (APP.TPA_PARTNER_INFO A INNER JOIN APP.TPA_PARTNER_EMPANEL_STATUS B ON A.PTNR_SEQ_ID = B.PTNR_SEQ_ID AND B.ACTIVE_YN = 'Y') LEFT OUTER JOIN APP.TPA_PARTNER_ACCOUNT_DETAILS C ON A.PTNR_SEQ_ID = C.PTNR_SEQ_ID LEFT OUTER JOIN APP.TPA_PARTNER_ADDRESS D ON C.PTNR_BANK_SEQ_ID = D.PTNR_BANK_SEQ_ID LEFT OUTER JOIN APP.TPA_PARTNER_GENERAL_DTL E ON B.EMPANEL_SEQ_ID = E.EMPANEL_SEQ_ID LEFT OUTER JOIN APP.TPA_PARTNER_ADDRESS F ON E.PTNR_GNRL_SEQ_ID = F.PTNR_GNRL_SEQ_ID LEFT OUTER JOIN APP.TPA_PARTNER_BANK_GUARANTEE G ON B.EMPANEL_SEQ_ID = G.EMPANEL_SEQ_ID LEFT OUTER JOIN APP.TPA_USER_CONTACTS H ON H.CONTACT_SEQ_ID = C.UPDATED_BY WHERE A.PTNR_SEQ_ID = ? ";

	private static final String strAddUpdatePartnerAccountInfo = "{CALL PARTNER_EMPANELL_PKG_PARTNER_ACCOUNT_INFO_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	/**
	 * This method returns the ArrayList, which contains the AccountDetailVO's
	 * which are populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of AccountDetailVO object's which contains the Account
	 *         Details of Bank Guarantee
     * @exception throws TTKException
     */
	public ArrayList getBankGuaranteeHistoryList(ArrayList alSearchObjects)
			throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strBankGuaranteeHistoryList;
		String strHospSeqId = "";
        String strFromDate   = "";
        String strToDate = "";
        AccountDetailVO accountDetailVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
		if (alSearchObjects != null && alSearchObjects.size() > 0) {
			strHospSeqId = ((SearchCriteria) alSearchObjects.get(0)).getValue();
			strFromDate = TTKCommon.checkNull(((SearchCriteria) alSearchObjects
					.get(1)).getValue());
			strToDate = TTKCommon.checkNull(((SearchCriteria) alSearchObjects
					.get(2)).getValue());
			strStaticQuery = TTKCommon.replaceInString(strStaticQuery, "?",
					strHospSeqId);
			sbfDynamicQuery = sbfDynamicQuery
					.append(" WHERE ( ( FROM_DATE >= NVL(TO_DATE( '"
							+ strFromDate
							+ "','DD/MM/YYYY'),TO_DATE('1/1/2005','DD/MM/YYYY') ) AND TO_DATE<= NVL(TO_DATE('"
							+ strToDate + "','DD/MM/YYYY'), TO_DATE)) )");
		}// end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
			// build the Order By Clause
		strStaticQuery = TTKCommon
				.replaceInString(
						strStaticQuery,
						"#",
						(String) alSearchObjects.get(alSearchObjects.size() - 4)
								+ " "
								+ (String) alSearchObjects.get(alSearchObjects
										.size() - 3));
		// build the row numbers to be fetched
		sbfDynamicQuery = sbfDynamicQuery.append(" )A WHERE Q >= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 2)
				+ " AND Q <= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();

		try {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
			if (rs != null) {
                while (rs.next()) {
                    accountDetailVO = new AccountDetailVO();

					if (rs.getString("BANK_GUANT_SEQ_ID") != null) {
						accountDetailVO.setBankGuantSeqId(new Long(rs
								.getString("BANK_GUANT_SEQ_ID")));
					}// end of if (rs.getString("BANK_GUANT_SEQ_ID")!=null)
					if (rs.getString("AMOUNT") != null) {
						accountDetailVO.setAmount(new BigDecimal(rs
								.getString("AMOUNT")));
					}// end of if(rs.getString("AMOUNT") != null)
					else {
                        accountDetailVO.setAmount(new BigDecimal("0.00"));
					}// end of else
					accountDetailVO.setBankGuantReqYN(TTKCommon.checkNull(rs
							.getString("BANK_GUANT_REQ_YN")));
					if (rs.getTimestamp("FROM_DATE") != null) {
						accountDetailVO.setStartDate(new java.util.Date(rs
								.getTimestamp("FROM_DATE").getTime()));
					}// end of if(rs.getTimestamp("FROM_DATE") != null)
					if (rs.getTimestamp("TO_DATE") != null) {
						accountDetailVO.setEndDate(new java.util.Date(rs
								.getTimestamp("TO_DATE").getTime()));
					}// end of if(rs.getTimestamp("TO_DATE") != null)
					accountDetailVO.setGuaranteeBankName(TTKCommon.checkNull(rs
							.getString("BANK_NAME")));
                    resultList.add(accountDetailVO);
                }// End of while (rs.next())
            }// End of if(rs != null)
			return (ArrayList) resultList;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "account");
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
							"Error while closing the Resultset in AccountDAOImpl getBankGuaranteeHistoryList()",
							sqlExp);
					throw new TTKException(sqlExp, "account");
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
								"Error while closing the Statement in AccountDAOImpl getBankGuaranteeHistoryList()",
								sqlExp);
						throw new TTKException(sqlExp, "account");
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
									"Error while closing the Connection in AccountDAOImpl getBankGuaranteeHistoryList()",
									sqlExp);
							throw new TTKException(sqlExp, "account");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// End of getBankGuaranteeHistoryList(ArrayList alSearchObjects)

    /**
	 * This method returns the ArrayList, which contains the AccountDetailVO's
	 * which are populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of AccountDetailVO object's which contains the Account
	 *         Details of Empanelment fee HistoryList
     * @exception throws TTKException
     */
	public ArrayList getEmpanelmentFeeHistoryList(ArrayList alSearchObjects)
			throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        StringBuffer sbfDynamicQuery = new StringBuffer();
        String strStaticQuery = strEmpanelmentFeeHistoryList;
		String strHospSeqId = "";
        String strFromDate   = "";
        String strToDate = "";
        AccountDetailVO accountDetailVO = null;
        Collection<Object> resultList = new ArrayList<Object>();
		if (alSearchObjects != null && alSearchObjects.size() > 0) {
			strHospSeqId = ((SearchCriteria) alSearchObjects.get(0)).getValue();
			strFromDate = TTKCommon.checkNull(((SearchCriteria) alSearchObjects
					.get(1)).getValue());
			strToDate = TTKCommon.checkNull(((SearchCriteria) alSearchObjects
					.get(2)).getValue());
			strStaticQuery = TTKCommon.replaceInString(strStaticQuery, "?",
					strHospSeqId);
			sbfDynamicQuery = sbfDynamicQuery
					.append(" WHERE ( ( FROM_DATE >= NVL(TO_DATE( '"
							+ strFromDate
							+ "','DD/MM/YYYY'),TO_DATE('1/1/2005','DD/MM/YYYY') ) AND TO_DATE<= NVL(TO_DATE('"
							+ strToDate + "','DD/MM/YYYY'), TO_DATE)) )");
		}// end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
			// build the Order By Clause
		strStaticQuery = TTKCommon
				.replaceInString(
						strStaticQuery,
						"#",
						(String) alSearchObjects.get(alSearchObjects.size() - 4)
								+ " "
								+ (String) alSearchObjects.get(alSearchObjects
										.size() - 3));
		// build the row numbers to be fetched
		sbfDynamicQuery = sbfDynamicQuery.append(" )A WHERE Q >= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 2)
				+ " AND Q <= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 1));
        strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();

		try {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strStaticQuery);
            rs = pStmt.executeQuery();
			if (rs != null) {
                while (rs.next()) {
                    accountDetailVO = new AccountDetailVO();
					if (rs.getString("EMPANEL_SEQ_ID") != null) {
						accountDetailVO.setEmplSeqId(new Long(rs
								.getLong("EMPANEL_SEQ_ID")));
					}// end of if (rs.getString("EMPANEL_SEQ_ID")!=null)
					if (rs.getString("AMOUNT") != null) {
						accountDetailVO.setAmount(new BigDecimal(rs
								.getString("AMOUNT")));
					}// end of if(rs.getString("AMOUNT") != null)
					else {
                        accountDetailVO.setAmount(new BigDecimal("0.00"));
					}// end of else
					accountDetailVO.setEmplFeeChrgYn(TTKCommon.checkNull(rs
							.getString("EMPANEL_FEES_CHRG_YN")));
					if (rs.getTimestamp("FROM_DATE") != null) {
						accountDetailVO.setStartDate(new java.util.Date(rs
								.getTimestamp("FROM_DATE").getTime()));
					}// end of if(rs.getTimestamp("FROM_DATE") != null)
					if (rs.getTimestamp("TO_DATE") != null) {
						accountDetailVO.setEndDate(new java.util.Date(rs
								.getTimestamp("TO_DATE").getTime()));
					}// end of if(rs.getTimestamp("TO_DATE") != null)
					accountDetailVO.setGuaranteeBankName(TTKCommon.checkNull(rs
							.getString("BANK_NAME")));
                    resultList.add(accountDetailVO);
                }// End of while (rs.next())
            }// End of if(rs != null)
			return (ArrayList) resultList;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "account");
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
							"Error while closing the Resultset in AccountDAOImpl getEmpanelmentFeeHistoryList()",
							sqlExp);
					throw new TTKException(sqlExp, "account");
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
								"Error while closing the Statement in AccountDAOImpl getEmpanelmentFeeHistoryList()",
								sqlExp);
						throw new TTKException(sqlExp, "account");
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
									"Error while closing the Connection in AccountDAOImpl getEmpanelmentFeeHistoryList()",
									sqlExp);
							throw new TTKException(sqlExp, "account");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// End of getEmpanelmentFeeHistoryList(ArrayList alSearchObjects)

    /**
	 * This method returns the AccountDetailVO, which contains all the general
	 * account History Details
	 * 
	 * @param lEmpanelSeqId
	 *            long which contains the Empanel Seq Id
	 * @return AccountDetailVO object which contains all the general account
	 *         History Details
     * @exception throws TTKException
     */
	public AccountDetailVO getEmpanelmentFeeDetails(long lEmpanelSeqId)
			throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        AccountDetailVO accountDetailVO = new AccountDetailVO();
        AddressVO podAddressVO = new AddressVO();
		try {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strEmpanelmentFeeHistoryDetail);
			pStmt.setLong(1, lEmpanelSeqId);
            rs = pStmt.executeQuery();
			if (rs != null) {
                while (rs.next()) {

					accountDetailVO.setEmplFeeChrgYn(TTKCommon.checkNull(rs
							.getString("EMPANEL_FEES_CHRG_YN")));
					accountDetailVO.setPayOrdDesc(TTKCommon.checkNull(rs
							.getString("DESCRIPTION")));
					accountDetailVO.setPayOrdNmbr(TTKCommon.checkNull(rs
							.getString("PAY_ORDER_NUMBER")));

					if (rs.getString("PAY_ORDER_AMOUNT") != null) {
						accountDetailVO.setAmount(new BigDecimal(rs
								.getString("PAY_ORDER_AMOUNT")));
					}// end of if(rs.getString("PAY_ORDER_AMOUNT") != null)
					else {
                        accountDetailVO.setAmount(new BigDecimal("0.00"));
					}// end of else

					if (rs.getTimestamp("PAY_ORDER_RECEIVED_DATE") != null) {
						accountDetailVO.setEndDate(new java.util.Date(rs
								.getTimestamp("PAY_ORDER_RECEIVED_DATE")
								.getTime()));
					}// end of if(rs.getTimestamp("PAY_ORDER_RECEIVED_DATE") !=
						// null)

					accountDetailVO.setBankName(TTKCommon.checkNull(rs
							.getString("BANK_NAME")));
					if (rs.getTimestamp("CHECK_ISSUED_DATE") != null) {
						accountDetailVO.setStartDate(new java.util.Date(rs
								.getTimestamp("CHECK_ISSUED_DATE").getTime()));
					}// end of if(rs.getTimestamp("CHECK_ISSUED_DATE") != null)

					podAddressVO.setAddress1(TTKCommon.checkNull(rs
							.getString("ADDRESS_1")));
					podAddressVO.setAddress2(TTKCommon.checkNull(rs
							.getString("ADDRESS_2")));
					podAddressVO.setAddress3(TTKCommon.checkNull(rs
							.getString("ADDRESS_3")));
					podAddressVO.setPinCode(TTKCommon.checkNull(rs
							.getString("PIN_CODE")));
					podAddressVO.setStateName(TTKCommon.checkNull(rs
							.getString("STATE_NAME")));
					podAddressVO.setCityDesc(TTKCommon.checkNull(rs
							.getString("CITY_DESCRIPTION")));
					podAddressVO.setCountryName(TTKCommon.checkNull(rs
							.getString("COUNTRY_NAME")));
                    accountDetailVO.setPayOrdBankAddressVO(podAddressVO);
                }// end of while
			}// end of if(rs != null)
            return accountDetailVO;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "account");
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
							"Error while closing the Resultset in AccountDAOImpl getEmpanelmentFeeDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "account");
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
								"Error while closing the Statement in AccountDAOImpl getEmpanelmentFeeDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "account");
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
									"Error while closing the Connection in AccountDAOImpl getEmpanelmentFeeDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "account");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getEmpanelmentFeeDetails(long lEmpanelSeqId)

    /**
	 * This method returns the AccountDetailVO, which contains all the general
	 * account History Details
	 * 
	 * @param lngGuaranteeSeqID
	 *            long which contains the Bank Guarantee Seq ID
	 * @return AccountDetailVO object which contains all the general account
	 *         History Details
     * @exception throws TTKException
     */
	public AccountDetailVO getBankGuaranteeDetails(long lngGuaranteeSeqID)
			throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        AccountDetailVO accountDetailVO = null;
		try {
            conn = ResourceManager.getConnection();
            pStmt = conn.prepareStatement(strBankGuaranteeHistoryDetail);
			pStmt.setLong(1, lngGuaranteeSeqID);
            rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
                    accountDetailVO = new AccountDetailVO();
					accountDetailVO.setBankGuantReqYN(TTKCommon.checkNull(rs
							.getString("BANK_GUANT_REQ_YN")));
					accountDetailVO.setGuaranteeBankName(TTKCommon.checkNull(rs
							.getString("BANK_NAME")));

					if (rs.getString("AMOUNT") != null) {
						accountDetailVO.setAmount(new BigDecimal(rs
								.getString("AMOUNT")));
					}// end of if(rs.getString("AMOUNT") != null)
					else {
                        accountDetailVO.setAmount(new BigDecimal("0.00"));
					}// end of else

					if (rs.getTimestamp("COMMENCEMENT_DATE") != null) {
						accountDetailVO
								.setGuaranteeCommDate(new java.util.Date(rs
										.getTimestamp("COMMENCEMENT_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("COMMENCEMENT_DATE") != null)

					if (rs.getTimestamp("EXPIRY_DATE") != null) {
						accountDetailVO
								.setGuaranteeExpiryDate(new java.util.Date(rs
										.getTimestamp("EXPIRY_DATE").getTime()));
					}// end of if(rs.getTimestamp("EXPIRY_DATE") != null)

				}// end of while(rs.next())
			}// end of if(rs != null)
            return accountDetailVO;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "account");
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
							"Error while closing the Resultset in AccountDAOImpl getBankGuaranteeDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "account");
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
								"Error while closing the Statement in AccountDAOImpl getBankGuaranteeDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "account");
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
									"Error while closing the Connection in AccountDAOImpl getBankGuaranteeDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "account");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getBankGuaranteeDetails(long lngGuaranteeSeqID)

    /**
	 * This method returns the AccountDetailVO, which contains all the hospital
	 * account details
	 * 
	 * @param lHospSeqId
	 *            long which contains the Hospital Seq Id
	 * @return AccountDetailVO object which contains all the hospital account
	 *         details
     * @exception throws TTKException
     */
	public AccountDetailVO getAccount(long lHospSeqId) throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        AccountDetailVO accountDetailVO = null;
        AddressVO bankAddressVO = null;
        AddressVO podAddressVO = null;
		try {
			conn = ResourceManager.getConnection();

            pStmt = conn.prepareStatement(strAccountDetail);
			pStmt.setLong(1, lHospSeqId);
            rs = pStmt.executeQuery();
			if (rs != null) {
                while (rs.next()) {
                    accountDetailVO = new AccountDetailVO();
                    bankAddressVO = new AddressVO();
                    podAddressVO = new AddressVO();

                    if(rs.getString("HOSP_BANK_SEQ_ID") != null)
                    {
                        accountDetailVO.setHospBankSeqId(new Long(rs.getLong("HOSP_BANK_SEQ_ID")));
                    }//end of if(rs.getString("HOSP_BANK_SEQ_ID") != null)
                    if(rs.getString("ADDR_SEQ_ID") != null)
                    {
                        bankAddressVO.setAddrSeqId(new Long(rs.getLong("ADDR_SEQ_ID")));
                    }//end of if(rs.getString("ADDR_SEQ_ID") != null)
                    if(rs.getString("POD_ADDR_SEQ_ID") != null)
                    {
                        podAddressVO.setAddrSeqId(new Long(rs.getLong("POD_ADDR_SEQ_ID")));
                    }//end of if(rs.getString("POD_ADDR_SEQ_ID") != null)
                    if(rs.getString("HOSP_SEQ_ID") != null)
                    {
                        accountDetailVO.setHospSeqId(new Long(rs.getLong("HOSP_SEQ_ID")));
                    }//end of if(rs.getString("HOSP_SEQ_ID") != null)
                    accountDetailVO.setBankName(TTKCommon.checkNull(rs.getString("BANK_NAME")));
                    accountDetailVO.setAccountNumber(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));
                    accountDetailVO.setActInNameOf(TTKCommon.checkNull(rs.getString("ACCOUNT_IN_NAME_OF")));
                    accountDetailVO.setBranchName(TTKCommon.checkNull(rs.getString("BRANCH_NAME")));
                    bankAddressVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
                    bankAddressVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
                    bankAddressVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
                    bankAddressVO.setCityCode(TTKCommon.checkNull(rs.getString("City_Type_Id")));
                    bankAddressVO.setStateCode(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));                    
                    bankAddressVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
                    bankAddressVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
                    accountDetailVO.setBankAddressVO(bankAddressVO);
					accountDetailVO.setManagementName(TTKCommon.checkNull(rs
							.getString("MANAGEMENT_NAME")));
					accountDetailVO.setIssueChqTo(TTKCommon.checkNull(rs
							.getString("ISSUE_CHEQUES_TYPE_ID")));
					if (rs.getString("HOSP_GNRL_SEQ_ID") != null) {
						accountDetailVO.setHospGnrlSeqId(new Long(rs
								.getLong("HOSP_GNRL_SEQ_ID")));
					}// end of if(rs.getString("HOSP_GNRL_SEQ_ID") != null)
					accountDetailVO.setEmplFeeChrgYn(TTKCommon.checkNull(rs
							.getString("EMPANEL_FEES_CHRG_YN")));
					accountDetailVO.setPayOrdType(TTKCommon.checkNull(rs
							.getString("PAY_ORDER_TYPE_ID")));
					accountDetailVO.setPayOrdNmbr(TTKCommon.checkNull(rs
							.getString("PAY_ORDER_NUMBER")));
					if (rs.getString("PAY_ORDER_AMOUNT") != null) {
						accountDetailVO.setPayOrdAmount(new BigDecimal(rs
								.getString("PAY_ORDER_AMOUNT")));
						accountDetailVO.setPayOrdAmountWord((String) rs
								.getString("PAY_ORDER_AMOUNT"));
					}// end of if(rs.getString("PAY_ORDER_AMOUNT") != null)
					if (rs.getTimestamp("PAY_ORDER_RECEIVED_DATE") != null) {
						accountDetailVO.setPayOrdRcvdDate(new java.util.Date(rs
								.getTimestamp("PAY_ORDER_RECEIVED_DATE")
								.getTime()));
					}// end of if(rs.getTimestamp("PAY_ORDER_RECEIVED_DATE") !=
						// null)
					accountDetailVO.setPayOrdBankName(TTKCommon.checkNull(rs
							.getString("POD_BANK_NAME")));
					if (rs.getTimestamp("CHECK_ISSUED_DATE") != null) {
						accountDetailVO.setChkIssuedDate(new java.util.Date(rs
								.getTimestamp("CHECK_ISSUED_DATE").getTime()));
					}// end of if(rs.getTimestamp("CHECK_ISSUED_DATE") != null)
					podAddressVO.setAddress1(TTKCommon.checkNull(rs
							.getString("POD_ADDRESS_1")));
					podAddressVO.setAddress2(TTKCommon.checkNull(rs
							.getString("POD_ADDRESS_2")));
					podAddressVO.setAddress3(TTKCommon.checkNull(rs
							.getString("POD_ADDRESS_3")));
					podAddressVO.setCityCode(TTKCommon.checkNull(rs
							.getString("POD_CITY_TYPE_ID")));
					podAddressVO.setStateCode(TTKCommon.checkNull(rs
							.getString("POD_STATE_TYPE_ID")));
					podAddressVO.setPinCode(TTKCommon.checkNull(rs
							.getString("POD_PIN_CODE")));
					podAddressVO.setCountryCode(TTKCommon.checkNull(rs
							.getString("POD_COUNTRY_ID")));
                    accountDetailVO.setPayOrdBankAddressVO(podAddressVO);
					if (rs.getString("BANK_GUANT_SEQ_ID") != null) {
						accountDetailVO.setBankGuantSeqId(new Long(rs
								.getLong("BANK_GUANT_SEQ_ID")));
					}// end of if(rs.getString("BANK_GUANT_SEQ_ID") != null)
					if (rs.getString("EMPANEL_SEQ_ID") != null) {
						accountDetailVO.setEmplSeqId(new Long(rs
								.getLong("EMPANEL_SEQ_ID")));
					}// end of if(rs.getString("EMPANEL_SEQ_ID") != null)
					accountDetailVO.setBankGuantReqYN(TTKCommon.checkNull(rs
							.getString("BANK_GUANT_REQ_YN")));
					accountDetailVO.setGuaranteeBankName(TTKCommon.checkNull(rs
							.getString("BGUANT_BANK_NAME")));
					if (rs.getString("AMOUNT") != null) {
						accountDetailVO.setGuaranteeAmount(new BigDecimal(rs
								.getString("AMOUNT")));
						accountDetailVO.setGuaranteeAmountWord((String) rs
								.getString("AMOUNT"));
					}// end of if(rs.getString("AMOUNT") != null)
					if (rs.getTimestamp("COMMENCEMENT_DATE") != null) {
						accountDetailVO
								.setGuaranteeCommDate(new java.util.Date(rs
										.getTimestamp("COMMENCEMENT_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("COMMENCEMENT_DATE") != null)
					if (rs.getTimestamp("EXPIRY_DATE") != null) {
						accountDetailVO
								.setGuaranteeExpiryDate(new java.util.Date(rs
										.getTimestamp("EXPIRY_DATE").getTime()));
					}// end of if(rs.getTimestamp("EXPIRY_DATE") != null)
					if (rs.getTimestamp("tpa_regist_date") != null) {
						accountDetailVO.setTpaRegdDate(new java.util.Date(rs
								.getTimestamp("tpa_regist_date").getTime()));
					}// end of if(rs.getTimestamp("tpa_regist_date") != null)

					// intX
					if (rs.getTimestamp("start_date") != null) {
						accountDetailVO.setStartDate(new java.util.Date(rs
								.getTimestamp("start_date").getTime()));
					}// end of if(rs.getTimestamp("start_date") != null)
					if (rs.getTimestamp("end_date") != null) {
						accountDetailVO.setEndDate(new java.util.Date(rs
								.getTimestamp("end_date").getTime()));
					}// end of if(rs.getTimestamp("end_date") != null)
					accountDetailVO.setBankIfsc(TTKCommon.checkNull(rs
							.getString("bank_ifsc")));
					accountDetailVO.setEmplNum(TTKCommon.checkNull(rs
							.getString("empanel_number")));
					accountDetailVO.setIbanNumber(TTKCommon.checkNull(rs
							.getString("BANK_IFSC")));
					accountDetailVO.setSwiftCode(TTKCommon.checkNull(rs
							.getString("BANK_MICR")));
					if (rs.getString("REVIEW_YN") != null)
						if (rs.getString("REVIEW_YN").equals("Y"))
                    		accountDetailVO.setReviewedYN("Yes");
                    	else
                    		accountDetailVO.setReviewedYN("No");
                    else
                    	accountDetailVO.setReviewedYN("No");

					if (rs.getTimestamp("REVIEWED_DATE") != null) {
						accountDetailVO.setReviewedDate(TTKCommon
								.checkNull(TTKCommon
										.getFormattedDate(new java.util.Date(rs
												.getTimestamp("REVIEWED_DATE")
												.getTime()))));
                	}
					accountDetailVO.setReviewedBy(TTKCommon.checkNull(rs
							.getString("REVIEWED_BY")));
					accountDetailVO.setModeOfPayment(TTKCommon.checkNull(rs
							.getString("MODE_OF_PAYMENT")));

                }// end of while
			}// end of if(rs != null)
            return accountDetailVO;
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "account");
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
							"Error while closing the Resultset in AccountDAOImpl getAccount()",
							sqlExp);
					throw new TTKException(sqlExp, "account");
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
								"Error while closing the Statement in AccountDAOImpl getAccount()",
								sqlExp);
						throw new TTKException(sqlExp, "account");
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
									"Error while closing the Connection in AccountDAOImpl getAccount()",
									sqlExp);
							throw new TTKException(sqlExp, "account");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getAccount(long lHospSeqId)

	public ArrayList getPartnerAccountIntX(long PartnerSeqId)
			throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        AccountDetailVO accountDetailVO = null;
        ArrayList<AccountDetailVO> alAccountInfo	=	new ArrayList<AccountDetailVO>();
		try {
			conn = ResourceManager.getConnection();

            pStmt = conn.prepareStatement(strPartnerAccountVariousDetails);
			pStmt.setLong(1, PartnerSeqId);
            rs = pStmt.executeQuery();
			if (rs != null) {
                while (rs.next()) {

                	 accountDetailVO	=	new AccountDetailVO();
                	 accountDetailVO.setBankName(rs.getString("bank_name"));
					accountDetailVO.setAccountNumber(rs
							.getString("account_number"));
                     accountDetailVO.setBankIfsc(rs.getString("ifsc_code"));
                     accountDetailVO.setStartDate(rs.getDate("start_date"));
                     accountDetailVO.setEndDate(rs.getDate("end_date"));
					if (rs.getDate("start_date") != null)
						accountDetailVO.setStartDatestr(TTKCommon
								.getFormattedDate(accountDetailVO
										.getStartDate()));
					if (rs.getDate("end_date") != null)
						accountDetailVO
								.setEndDatestr(TTKCommon
										.getFormattedDate(accountDetailVO
												.getEndDate()));
                     alAccountInfo.add(accountDetailVO);
                }// end of while
			}// end of if(rs != null)

			return alAccountInfo;// accountDetailVO
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "account");
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
							"Error while closing the Resultset in AccountDAOImpl getAccountIntX()",
							sqlExp);
					throw new TTKException(sqlExp, "account");
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
								"Error while closing the Statement in AccountDAOImpl getAccountIntX()",
								sqlExp);
						throw new TTKException(sqlExp, "account");
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
									"Error while closing the Connection in AccountDAOImpl getAccountIntX()",
									sqlExp);
							throw new TTKException(sqlExp, "account");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getAccount(long lHospSeqId)

	// ------------------------------------------------------------------------------------------
	public ArrayList<AccountDetailVO> getAccountIntX(long lHospSeqId)
			throws TTKException {
    	Connection conn = null;
    	PreparedStatement pStmt = null;
    	ResultSet rs = null;
        AccountDetailVO accountDetailVO = null;
        ArrayList<AccountDetailVO> alAccountInfo	=	new ArrayList<AccountDetailVO>();
		try {
            conn = ResourceManager.getConnection();

            pStmt = conn.prepareStatement(strAccountMultipleDetail);
			pStmt.setLong(1, lHospSeqId);
            rs = pStmt.executeQuery();
			if (rs != null) {
                while (rs.next()) {

                	 accountDetailVO	=	new AccountDetailVO();
                	 accountDetailVO.setBankName(rs.getString("bank_name"));
					accountDetailVO.setAccountNumber(rs
							.getString("account_number"));
                     accountDetailVO.setBankIfsc(rs.getString("ifsc_code"));
                     accountDetailVO.setStartDate(rs.getDate("start_date"));
                     accountDetailVO.setEndDate(rs.getDate("end_date"));
                     alAccountInfo.add(accountDetailVO);
                }// end of while
			}// end of if(rs != null)

			return alAccountInfo;// accountDetailVO
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "account");
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
							"Error while closing the Resultset in AccountDAOImpl getAccountIntX()",
							sqlExp);
					throw new TTKException(sqlExp, "account");
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
								"Error while closing the Statement in AccountDAOImpl getAccountIntX()",
								sqlExp);
						throw new TTKException(sqlExp, "account");
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
									"Error while closing the Connection in AccountDAOImpl getAccountIntX()",
									sqlExp);
							throw new TTKException(sqlExp, "account");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getAccountIntX(long lHospSeqId)
		// ------------------------------------------------------------------------------------------

    /**
	 * This method adds or updates the account details The method also calls
	 * other methods on DAO to insert/update the account details to the database
	 * 
	 * @param accountDetailVO
	 *            AccountDetailVO object which contains the account details to
	 *            be added/updated
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
     * @exception throws TTKException
     */
	public int addUpdateAccount(AccountDetailVO accountDetailVO)
			throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		int iResult = 0;
        HospitalAuditVO hospitalAuditVO = null;
		try {
			conn = ResourceManager.getConnection();
            hospitalAuditVO = accountDetailVO.getAuditDetailsVO();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateAccountInfo);
            if(accountDetailVO.getHospBankSeqId() != null)
            {
                cStmtObject.setLong(HOSP_BANK_SEQ_ID,accountDetailVO.getHospBankSeqId().longValue());
            }//end of if(accountDetailVO.getHospBankSeqId() != null)
            else
            {
                cStmtObject.setLong(HOSP_BANK_SEQ_ID,0);
            }//end of else
            if(accountDetailVO.getBankAddressVO().getAddrSeqId() != null)
            {
                cStmtObject.setLong(ADDR_SEQ_ID,accountDetailVO.getBankAddressVO().getAddrSeqId().longValue());
            }//end of if(accountDetailVO.getBankAddressVO().getAddrSeqId() != null)
            else
            {
                cStmtObject.setLong(ADDR_SEQ_ID,0);
            }//end of else
            if(accountDetailVO.getPayOrdBankAddressVO().getAddrSeqId() != null)
            {
                cStmtObject.setLong(POD_ADDR_SEQ_ID,accountDetailVO.getPayOrdBankAddressVO().getAddrSeqId().longValue());
            }//end of if(accountDetailVO.getPayOrdBankAddressVO().getAddrSeqId() != null)
            else
            {
                cStmtObject.setLong(POD_ADDR_SEQ_ID,0);
            }//end of else
            if(accountDetailVO.getHospSeqId()!= null)
            {
                cStmtObject.setLong(HOSP_SEQ_ID, accountDetailVO.getHospSeqId().longValue());
            }//end of if(accountDetailVO.getHospSeqId()!= null)
            else
            {
                cStmtObject.setLong(HOSP_SEQ_ID,0);
            }//end of else
            cStmtObject.setString(BANK_NAME, accountDetailVO.getBankName());
			cStmtObject.setString(ACCOUNT_NUMBER,
					accountDetailVO.getAccountNumber());
			cStmtObject.setString(ACCOUNT_IN_NAME_OF,
					accountDetailVO.getActInNameOf());
            cStmtObject.setString(BRANCH_NAME, accountDetailVO.getBranchName());
            cStmtObject.setString(ADDRESS_1, accountDetailVO.getBankAddressVO().getAddress1());
            cStmtObject.setString(ADDRESS_2, accountDetailVO.getBankAddressVO().getAddress2());
            cStmtObject.setString(ADDRESS_3, accountDetailVO.getBankAddressVO().getAddress3());
            cStmtObject.setString(CITY_TYPE_ID, accountDetailVO.getBankAddressVO().getCityCode());
            cStmtObject.setString(STATE_TYPE_ID, accountDetailVO.getBankAddressVO().getStateCode());
           // cStmtObject.setString(PIN_CODE, accountDetailVO.getBankAddressVO().getPinCode());
            if(!"".equals(TTKCommon.checkNull(accountDetailVO.getBankAddressVO().getPinCode()))){
            	cStmtObject.setLong(PIN_CODE,Long.parseLong(accountDetailVO.getBankAddressVO().getPinCode()));
            }else{
            	cStmtObject.setLong(PIN_CODE,0);
            }
            
          //  cStmtObject.setString(COUNTRY_ID, accountDetailVO.getBankAddressVO().getCountryCode());
            if(!"".equals(TTKCommon.checkNull( accountDetailVO.getBankAddressVO().getCountryCode()))){
            	cStmtObject.setInt(COUNTRY_ID,Integer.parseInt(accountDetailVO.getBankAddressVO().getCountryCode()));
            }else{
            	cStmtObject.setInt(COUNTRY_ID,0);
            }
            cStmtObject.setString(MANAGEMENT_NAME, accountDetailVO.getManagementName());
            cStmtObject.setString(ISSUE_CHEQUES_TYPE_ID, accountDetailVO.getIssueChqTo());
            if(accountDetailVO.getHospGnrlSeqId() != null)
            {
                cStmtObject.setLong(HOSP_GNRL_SEQ_ID, accountDetailVO.getHospGnrlSeqId().longValue());
            }//end of if(accountDetailVO.getHospGnrlSeqId() != null)
            else
            {
                cStmtObject.setString(HOSP_GNRL_SEQ_ID,null);
            }//end of else
            cStmtObject.setString(EMPANEL_FEES_CHRG_YN, accountDetailVO.getEmplFeeChrgYn());
            cStmtObject.setString(PAY_ORDER_TYPE_ID, accountDetailVO.getPayOrdType());
            cStmtObject.setString(PAY_ORDER_NUMBER, accountDetailVO.getPayOrdNmbr());
            if(accountDetailVO.getPayOrdAmount()!= null)
            {
              //  cStmtObject.setBigDecimal(PAY_ORDER_AMOUNT, accountDetailVO.getPayOrdAmount());
                cStmtObject.setDouble(PAY_ORDER_AMOUNT,accountDetailVO.getPayOrdAmount().doubleValue());
            }//end of if(accountDetailVO.getPayOrdAmount()!= null)
            else
            {
            	cStmtObject.setDouble(PAY_ORDER_AMOUNT,0);
               
            }//end of else
            if(accountDetailVO.getPayOrdRcvdDate() != null)
            {
                cStmtObject.setTimestamp(PAY_ORDER_RECEIVED_DATE,new Timestamp(accountDetailVO.getPayOrdRcvdDate().getTime()));
            }//end of if(accountDetailVO.getPayOrdRcvdDate() != null)
            else
            {
                cStmtObject.setTimestamp(PAY_ORDER_RECEIVED_DATE, null);
			}// end of else
			cStmtObject.setString(POD_BANK_NAME,
					accountDetailVO.getPayOrdBankName());
			if (accountDetailVO.getChkIssuedDate() != null) {
				cStmtObject.setTimestamp(CHECK_ISSUED_DATE, new Timestamp(
						accountDetailVO.getChkIssuedDate().getTime()));
			}// end of if(accountDetailVO.getChkIssuedDate() != null)
			else {
                cStmtObject.setTimestamp(CHECK_ISSUED_DATE, null);
            }//end of else
            if(accountDetailVO.getBankGuantSeqId() != null)
            {
                cStmtObject.setLong(BANK_GUANT_SEQ_ID, accountDetailVO.getBankGuantSeqId().longValue());
            }//end of if(accountDetailVO.getBankGuantSeqId() != null)
            else
            {
                cStmtObject.setLong(BANK_GUANT_SEQ_ID,0);
            }//end of else
            if(accountDetailVO.getEmplSeqId() != null)
            {
                cStmtObject.setLong(EMPANEL_SEQ_ID, accountDetailVO.getEmplSeqId().longValue());
            }//end of if(accountDetailVO.getEmplSeqId() != null)
            else
            {
                cStmtObject.setLong(EMPANEL_SEQ_ID,0);
            }//end of else
            cStmtObject.setString(BANK_GUANT_REQD_YN,accountDetailVO.getBankGuantReqYN());
            cStmtObject.setString(POD_ADDRESS_1, accountDetailVO.getPayOrdBankAddressVO().getAddress1());
            cStmtObject.setString(POD_ADDRESS_2, accountDetailVO.getPayOrdBankAddressVO().getAddress2());
            cStmtObject.setString(POD_ADDRESS_3, accountDetailVO.getPayOrdBankAddressVO().getAddress3());
            cStmtObject.setString(POD_CITY_TYPE_ID, accountDetailVO.getPayOrdBankAddressVO().getCityCode());
            cStmtObject.setString(POD_STATE_TYPE_ID, accountDetailVO.getPayOrdBankAddressVO().getStateCode());
          //  cStmtObject.setString(POD_PIN_CODE, accountDetailVO.getPayOrdBankAddressVO().getPinCode());
            
            if(!"".equals(TTKCommon.checkNull(accountDetailVO.getPayOrdBankAddressVO().getPinCode()))){
                cStmtObject.setLong(POD_PIN_CODE,Long.parseLong(accountDetailVO.getPayOrdBankAddressVO().getPinCode()));
            }else{
            	 cStmtObject.setLong(POD_PIN_CODE,0);
            }
        
            if(!"".equals(accountDetailVO.getPayOrdBankAddressVO().getCountryCode())){
            	  cStmtObject.setInt(POD_COUNTRY_ID,Integer.parseInt(accountDetailVO.getPayOrdBankAddressVO().getCountryCode()));	
            }else{
            	  cStmtObject.setInt(POD_COUNTRY_ID,0);
            }
          
            if(hospitalAuditVO != null){
                cStmtObject.setLong(LOG_SEQ_ID,0);
                cStmtObject.setString(MOD_REASON_TYPE_ID,hospitalAuditVO.getModReson());
                if(hospitalAuditVO.getRefDate() != null)
                {
                    cStmtObject.setTimestamp(REFERENCE_DATE,new Timestamp(hospitalAuditVO.getRefDate().getTime()));
                }//end of if(hospitalAuditVO.getRefDate() != null)
                else
                {
                    cStmtObject.setTimestamp(REFERENCE_DATE, null);
				}// end of else
				cStmtObject.setString(REFERENCE_NO,
						hospitalAuditVO.getRefNmbr());
				cStmtObject
						.setString(REMARKS_LOG, hospitalAuditVO.getRemarks());
            }// end of if(hospitalAuditVO != null)
			else {
				cStmtObject.setLong(LOG_SEQ_ID, 0);
				cStmtObject.setString(MOD_REASON_TYPE_ID, null);
                cStmtObject.setTimestamp(REFERENCE_DATE, null);
				cStmtObject.setString(REFERENCE_NO, null);
				cStmtObject.setString(REMARKS_LOG, null);
            }// end of else
            cStmtObject.setLong(USER_SEQ_ID ,accountDetailVO.getUpdatedBy().longValue());
            
            if(!TTKCommon.checkNull(accountDetailVO.getStartDate()).equals(""))
			{
			cStmtObject.setTimestamp(START_DATE, new Timestamp(accountDetailVO.getStartDate().getTime()));
			
			}else
			{
				cStmtObject.setString(START_DATE, null);
			}
			if (!TTKCommon.checkNull(accountDetailVO.getEndDate()).equals("")) {
				cStmtObject.setTimestamp(END_DATE, new Timestamp(
						accountDetailVO.getEndDate().getTime()));
				// cStmtObject.setDate(END_DATE, new
				// java.sql.Date(accountDetailVO.getEndDate().getTime()));
			} else {
				cStmtObject.setTimestamp(END_DATE, null);
			}

			cStmtObject.setString(IBAN_NO, accountDetailVO.getIbanNumber());
			cStmtObject.setString(SWIFT_CODE, accountDetailVO.getSwiftCode());
			cStmtObject.setString(MODE_OF_PAYMENT, accountDetailVO.getModeOfPayment());
			
           // cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
			
			//  cStmtObject.registerOutParameter(HOSP_BANK_SEQ_ID,Types.BIGINT);
			  cStmtObject.registerOutParameter(ADDR_SEQ_ID,Types.BIGINT);
			  cStmtObject.registerOutParameter(POD_ADDR_SEQ_ID,Types.BIGINT);			  
			  cStmtObject.registerOutParameter(LOG_SEQ_ID,Types.BIGINT);
			  cStmtObject.registerOutParameter(HOSP_BANK_SEQ_ID,Types.BIGINT);
			  
            cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);
            cStmtObject.execute();
            iResult = cStmtObject.getInt(ROW_PROCESSED);
            conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "account");
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
							"Error while closing the Statement in AccountDAOImpl addUpdateAccount()",
							sqlExp);
        			throw new TTKException(sqlExp, "account");
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
								"Error while closing the Connection in AccountDAOImpl addUpdateAccount()",
								sqlExp);
        				throw new TTKException(sqlExp, "account");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
        return iResult;
	}// end of addUpdateAccount(AccountDetailVO accountDetailVO)

	public AccountDetailVO getPartnerAccountDetails(long partnerSeqId)
			throws TTKException {

    	Connection conn = null;
       	PreparedStatement pStmt = null;
       	ResultSet rs = null;
           AccountDetailVO accountDetailVO = null;
           AddressVO bankAddressVO = null;
           AddressVO podAddressVO = null;
		try {
			conn = ResourceManager.getConnection();

               pStmt = conn.prepareStatement(strPartnerAccountDetail);
			pStmt.setLong(1, partnerSeqId);
			// System.out.println("PartnerAccountDetails..."+partnerSeqId);
               rs = pStmt.executeQuery();
			if (rs != null) {
                   while (rs.next()) {
                       accountDetailVO = new AccountDetailVO();
                       bankAddressVO = new AddressVO();
                       podAddressVO = new AddressVO();

					if (rs.getString("PTNR_BANK_SEQ_ID") != null) {
						accountDetailVO.setHospBankSeqId(new Long(rs
								.getLong("PTNR_BANK_SEQ_ID")));
					}// end of if(rs.getString("HOSP_BANK_SEQ_ID") != null)
					if (rs.getString("ADDR_SEQ_ID") != null) {
						bankAddressVO.setAddrSeqId(new Long(rs
								.getLong("ADDR_SEQ_ID")));
					}// end of if(rs.getString("ADDR_SEQ_ID") != null)
					if (rs.getString("POD_ADDR_SEQ_ID") != null) {
						podAddressVO.setAddrSeqId(new Long(rs
								.getLong("POD_ADDR_SEQ_ID")));
					}// end of if(rs.getString("POD_ADDR_SEQ_ID") != null)
					if (rs.getString("PTNR_SEQ_ID") != null) {
						accountDetailVO.setHospSeqId(new Long(rs
								.getLong("PTNR_SEQ_ID")));
					}// end of if(rs.getString("HOSP_SEQ_ID") != null)
					accountDetailVO.setBankName(TTKCommon.checkNull(rs
							.getString("BANK_NAME")));
					accountDetailVO.setAccountNumber(TTKCommon.checkNull(rs
							.getString("ACCOUNT_NUMBER")));
					accountDetailVO.setActInNameOf(TTKCommon.checkNull(rs
							.getString("ACCOUNT_IN_NAME_OF")));
					accountDetailVO.setBranchName(TTKCommon.checkNull(rs
							.getString("BRANCH_NAME")));
					bankAddressVO.setAddress1(TTKCommon.checkNull(rs
							.getString("ADDRESS_1")));
					bankAddressVO.setAddress2(TTKCommon.checkNull(rs
							.getString("ADDRESS_2")));
					bankAddressVO.setAddress3(TTKCommon.checkNull(rs
							.getString("ADDRESS_3")));
					bankAddressVO.setCityCode(TTKCommon.checkNull(rs
							.getString("City_Type_Id")));
					bankAddressVO.setStateCode(TTKCommon.checkNull(rs
							.getString("STATE_TYPE_ID")));
					bankAddressVO.setPinCode(TTKCommon.checkNull(rs
							.getString("PIN_CODE")));
					bankAddressVO.setCountryCode(TTKCommon.checkNull(rs
							.getString("COUNTRY_ID")));
                       accountDetailVO.setBankAddressVO(bankAddressVO);
					// accountDetailVO.setManagementName(TTKCommon.checkNull(rs.getString("MANAGEMENT_NAME")));
					// accountDetailVO.setIssueChqTo(TTKCommon.checkNull(rs.getString("ISSUE_CHEQUES_TYPE_ID")));
					if (rs.getString("PTNR_GNRL_SEQ_ID") != null) {
						accountDetailVO.setHospGnrlSeqId(new Long(rs
								.getLong("PTNR_GNRL_SEQ_ID")));
					}// end of if(rs.getString("HOSP_GNRL_SEQ_ID") != null)
					accountDetailVO.setEmplFeeChrgYn(TTKCommon.checkNull(rs
							.getString("EMPANEL_FEES_CHRG_YN")));
					accountDetailVO.setPayOrdType(TTKCommon.checkNull(rs
							.getString("PAY_ORDER_TYPE_ID")));
					accountDetailVO.setPayOrdNmbr(TTKCommon.checkNull(rs
							.getString("PAY_ORDER_NUMBER")));
					if (rs.getString("PAY_ORDER_AMOUNT") != null) {
						accountDetailVO.setPayOrdAmount(new BigDecimal(rs
								.getString("PAY_ORDER_AMOUNT")));
						accountDetailVO.setPayOrdAmountWord((String) rs
								.getString("PAY_ORDER_AMOUNT"));
					}// end of if(rs.getString("PAY_ORDER_AMOUNT") != null)
					if (rs.getTimestamp("PAY_ORDER_RECEIVED_DATE") != null) {
						accountDetailVO.setPayOrdRcvdDate(new java.util.Date(rs
								.getTimestamp("PAY_ORDER_RECEIVED_DATE")
								.getTime()));
					}// end of if(rs.getTimestamp("PAY_ORDER_RECEIVED_DATE") !=
						// null)
					accountDetailVO.setPayOrdBankName(TTKCommon.checkNull(rs
							.getString("POD_BANK_NAME")));
					if (rs.getTimestamp("CHECK_ISSUED_DATE") != null) {
						accountDetailVO.setChkIssuedDate(new java.util.Date(rs
								.getTimestamp("CHECK_ISSUED_DATE").getTime()));
					}// end of if(rs.getTimestamp("CHECK_ISSUED_DATE") != null)
					podAddressVO.setAddress1(TTKCommon.checkNull(rs
							.getString("POD_ADDRESS_1")));
					podAddressVO.setAddress2(TTKCommon.checkNull(rs
							.getString("POD_ADDRESS_2")));
					podAddressVO.setAddress3(TTKCommon.checkNull(rs
							.getString("POD_ADDRESS_3")));
					podAddressVO.setCityCode(TTKCommon.checkNull(rs
							.getString("POD_CITY_TYPE_ID")));
					podAddressVO.setStateCode(TTKCommon.checkNull(rs
							.getString("POD_STATE_TYPE_ID")));
					podAddressVO.setPinCode(TTKCommon.checkNull(rs
							.getString("POD_PIN_CODE")));
					podAddressVO.setCountryCode(TTKCommon.checkNull(rs
							.getString("POD_COUNTRY_ID")));
                       accountDetailVO.setPayOrdBankAddressVO(podAddressVO);
					if (rs.getString("BANK_GUANT_SEQ_ID") != null) {
						accountDetailVO.setBankGuantSeqId(new Long(rs
								.getLong("BANK_GUANT_SEQ_ID")));
					}// end of if(rs.getString("BANK_GUANT_SEQ_ID") != null)
					if (rs.getString("EMPANEL_SEQ_ID") != null) {
						accountDetailVO.setEmplSeqId(new Long(rs
								.getLong("EMPANEL_SEQ_ID")));
					}// end of if(rs.getString("EMPANEL_SEQ_ID") != null)
					accountDetailVO.setBankGuantReqYN(TTKCommon.checkNull(rs
							.getString("BANK_GUANT_REQ_YN")));
					accountDetailVO.setGuaranteeBankName(TTKCommon.checkNull(rs
							.getString("BGUANT_BANK_NAME")));
					if (rs.getString("AMOUNT") != null) {
						accountDetailVO.setGuaranteeAmount(new BigDecimal(rs
								.getString("AMOUNT")));
						accountDetailVO.setGuaranteeAmountWord((String) rs
								.getString("AMOUNT"));
					}// end of if(rs.getString("AMOUNT") != null)
					if (rs.getTimestamp("COMMENCEMENT_DATE") != null) {
						accountDetailVO
								.setGuaranteeCommDate(new java.util.Date(rs
										.getTimestamp("COMMENCEMENT_DATE")
										.getTime()));
					}// end of if(rs.getTimestamp("COMMENCEMENT_DATE") != null)
					if (rs.getTimestamp("EXPIRY_DATE") != null) {
						accountDetailVO
								.setGuaranteeExpiryDate(new java.util.Date(rs
										.getTimestamp("EXPIRY_DATE").getTime()));
					}// end of if(rs.getTimestamp("EXPIRY_DATE") != null)

					/*
					 * if(rs.getTimestamp("tpa_regist_date") != null) {
					 * accountDetailVO.setTpaRegdDate(new
					 * java.util.Date(rs.getTimestamp
					 * ("tpa_regist_date").getTime())); }//end of
					 * if(rs.getTimestamp("tpa_regist_date") != null)
                       */

					// intX
					if (rs.getTimestamp("start_date") != null) {
						accountDetailVO.setStartDate(new java.util.Date(rs
								.getTimestamp("start_date").getTime()));
					}// end of if(rs.getTimestamp("start_date") != null)
					if (rs.getTimestamp("end_date") != null) {
						accountDetailVO.setEndDate(new java.util.Date(rs
								.getTimestamp("end_date").getTime()));
					}// end of if(rs.getTimestamp("end_date") != null)
					accountDetailVO.setBankIfsc(TTKCommon.checkNull(rs
							.getString("bank_ifsc")));
					accountDetailVO.setEmplNum(TTKCommon.checkNull(rs
							.getString("empanel_number")));
					accountDetailVO.setIbanNumber(TTKCommon.checkNull(rs
							.getString("BANK_IFSC")));
					accountDetailVO.setSwiftCode(TTKCommon.checkNull(rs
							.getString("BANK_MICR")));
					if (rs.getString("REVIEW_YN") != null)
						if (rs.getString("REVIEW_YN").equals("Y"))
                       		accountDetailVO.setReviewedYN("Yes");
                       	else
                       		accountDetailVO.setReviewedYN("No");
                       else
                       	accountDetailVO.setReviewedYN("No");

					if (rs.getTimestamp("REVIEWED_DATE") != null) {
						accountDetailVO.setReviewedDate(TTKCommon
								.checkNull(TTKCommon
										.getFormattedDate(new java.util.Date(rs
												.getTimestamp("REVIEWED_DATE")
												.getTime()))));
                   	}

					accountDetailVO.setReviewedBy(TTKCommon.checkNull(rs
							.getString("REVIEWED_BY")));
					accountDetailVO.setModeOfPayment(TTKCommon.checkNull(rs
							.getString("MODE_OF_PAYMENT")));
					accountDetailVO.setRouteCode(TTKCommon.checkNull(rs
							.getString("route_code")));

                   }// end of while
			}// end of if(rs != null)
               return accountDetailVO;
		}// end of try
		catch (SQLException sqlExp) {
               throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
               throw new TTKException(exp, "account");
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
							"Error while closing the Resultset in AccountDAOImpl getAccount()",
							sqlExp);
   					throw new TTKException(sqlExp, "account");
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
								"Error while closing the Statement in AccountDAOImpl getAccount()",
								sqlExp);
   						throw new TTKException(sqlExp, "account");
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
									"Error while closing the Connection in AccountDAOImpl getAccount()",
									sqlExp);
   							throw new TTKException(sqlExp, "account");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
   				throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
   			{
   				rs = null;
   				pStmt = null;
   				conn = null;
			}// end of finally
		}// end of finally
	}// end of getAccount(long lHospSeqId)

	/**
	 * This method adds or updates the account details The method also calls
	 * other methods on DAO to insert/update the account details to the database
	 * 
	 * @param accountDetailVO
	 *            AccountDetailVO object which contains the account details to
	 *            be added/updated
	 * @return int value, greater than zero indicates successful execution of
	 *         the task
	 * @exception throws TTKException
	 */
	public int addUpdatePartnerAccount(AccountDetailVO accountDetailVO)
			throws TTKException {
    	Connection conn = null;
		CallableStatement cStmtObject = null;
		int iResult = 0;
        HospitalAuditVO hospitalAuditVO = null;
		try {
			conn = ResourceManager.getConnection();
            hospitalAuditVO = accountDetailVO.getAuditDetailsVO();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAddUpdatePartnerAccountInfo);
			/*
			 * if (accountDetailVO.getHospBankSeqId() != null) {
			 * cStmtObject.setLong(1, accountDetailVO.getHospBankSeqId()); }//
			 * end of if(accountDetailVO.getHospBankSeqId() != null) else {
			 * cStmtObject.setLong(1, 0); }// end of else
			 */if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getHospBankSeqId()))) {
				cStmtObject.setLong(1, accountDetailVO.getHospBankSeqId());
			} else {
				cStmtObject.setLong(1, 0);
			}// end of else
			/*
			 * if (accountDetailVO.getBankAddressVO().getAddrSeqId() != null) {
			 * cStmtObject.setLong(2, accountDetailVO.getBankAddressVO()
			 * .getAddrSeqId()); }// end of
			 * if(accountDetailVO.getBankAddressVO().getAddrSeqId() != // null)
			 */
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getBankAddressVO().getAddrSeqId()))) {
				cStmtObject.setLong(2, accountDetailVO.getBankAddressVO()
						.getAddrSeqId());
			} else {
				cStmtObject.setLong(2, 0);
			}// end of else
		//	System.out.println("v_addr_seq_id..."
			//		+ accountDetailVO.getBankAddressVO().getAddrSeqId());
			/*
			 * if (accountDetailVO.getPayOrdBankAddressVO().getAddrSeqId() !=
			 * null) { cStmtObject.setLong(3,
			 * accountDetailVO.getPayOrdBankAddressVO() .getAddrSeqId()); }//
			 * end of //
			 * if(accountDetailVO.getPayOrdBankAddressVO().getAddrSeqId() != //
			 * null)
			 */if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getPayOrdBankAddressVO().getAddrSeqId()))) {
				cStmtObject.setLong(3, accountDetailVO.getPayOrdBankAddressVO()
						.getAddrSeqId());
			} else {
				cStmtObject.setLong(3, 0);
			}// end of else
		//	System.out
		//			.println("accountDetailVO.getPayOrdBankAddressVO().getAddrSeqId()..."
		//					+ accountDetailVO.getPayOrdBankAddressVO()
			//						.getAddrSeqId());
			if (accountDetailVO.getHospSeqId() != null) {
                cStmtObject.setLong(4, accountDetailVO.getHospSeqId());
			}// end of if(accountDetailVO.getHospSeqId()!= null)
			else {
				cStmtObject.setLong(4, 0);
			}// end of else
		//	System.out.println("accountDetailVO.getHospSeqId()..."+accountDetailVO.getHospSeqId());
            cStmtObject.setString(5, accountDetailVO.getBankName());
		//	System.out.println("accountDetailVO.getBankName().."+accountDetailVO.getBankName());
            cStmtObject.setString(6, accountDetailVO.getAccountNumber());
		//	System.out.println("accountDetailVO.getAccountNumber().."+accountDetailVO.getAccountNumber());
			if (!"".equals(TTKCommon.checkNull(accountDetailVO.getActInNameOf()))) {
            cStmtObject.setString(7, accountDetailVO.getActInNameOf());
			} else {
				cStmtObject.setString(7, null);
			}
		//	System.out.println("accountDetailVO.getActInNameOf()..."
			//		+ accountDetailVO.getActInNameOf());
            cStmtObject.setString(8, accountDetailVO.getIbanNumber());
		//	System.out.println("accountDetailVO.getIbanNumber()..."+accountDetailVO.getIbanNumber());
			cStmtObject.setString(9, accountDetailVO.getSwiftCode());
		//	System.out.println("accountDetailVO.getSwiftCode()..."+accountDetailVO.getSwiftCode());
			cStmtObject.setString(10, accountDetailVO.getModeOfPayment());
		//	System.out.println("accountDetailVO.getModeOfPayment()..."+accountDetailVO.getModeOfPayment());
			cStmtObject.setString(11, accountDetailVO.getBranchName());
		//	System.out.println("accountDetailVO.getBranchName()..."+accountDetailVO.getBranchName());
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getBankAddressVO().getAddress1()))) {
			cStmtObject.setString(12, accountDetailVO.getBankAddressVO()
					.getAddress1());
			} else {
				cStmtObject.setString(12, null);
			}
		//	System.out.println("address_1..."
			//		+ accountDetailVO.getBankAddressVO().getAddress1());
		/*	cStmtObject.setString(13, accountDetailVO.getBankAddressVO()
					.getAddress2()); */
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getBankAddressVO().getAddress2()))) {
			cStmtObject.setString(13, accountDetailVO.getBankAddressVO()
					.getAddress2());
			} else {
				cStmtObject.setString(13, null);
			}
		//	System.out.println("address_2..."
				//	+ accountDetailVO.getBankAddressVO().getAddress2());
			/*cStmtObject.setString(14, accountDetailVO.getBankAddressVO()
					.getAddress3());*/
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getBankAddressVO().getAddress3()))) {
			cStmtObject.setString(14, accountDetailVO.getBankAddressVO()
					.getAddress3());
			} else {
				cStmtObject.setString(14, null);
			}
		//	System.out.println("address_3..."
			//		+ accountDetailVO.getBankAddressVO().getAddress3());
			/*cStmtObject.setString(15, accountDetailVO.getBankAddressVO()
					.getCityCode());*/
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getBankAddressVO().getCityCode()))) {
			cStmtObject.setString(15, accountDetailVO.getBankAddressVO()
					.getCityCode());
			} else {
				cStmtObject.setString(15, null);
			}
		//	System.out.println("getCityCode..."
				//	+ accountDetailVO.getBankAddressVO().getCityCode());
			/*cStmtObject.setString(16, accountDetailVO.getBankAddressVO()
					.getStateCode());*/
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getBankAddressVO().getAddress1()))) {
				cStmtObject.setString(16, accountDetailVO.getBankAddressVO()
						.getStateCode());
			} else {
				cStmtObject.setString(16, null);
			}
		//	System.out.println("getStateCode..."
			//		+ accountDetailVO.getBankAddressVO().getStateCode());
			/*
			 * cStmtObject.setLong(17, Long.parseLong(accountDetailVO
			 * .getBankAddressVO().getPinCode()));
			 */
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getBankAddressVO().getPinCode()))) {
				 cStmtObject.setLong(17, Long.parseLong(accountDetailVO
							.getBankAddressVO().getPinCode()));
			} else {
				cStmtObject.setLong(17, 0);
			}// end of else
		//	System.out.println("getPinCode..."
			//		+ accountDetailVO.getBankAddressVO().getPinCode());
			/*
			 * cStmtObject.setInt(18, Integer.parseInt(accountDetailVO
			 * .getBankAddressVO().getCountryCode()));
			 */
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getBankAddressVO().getCountryCode()))) {
				 cStmtObject.setInt(18, Integer.parseInt(accountDetailVO
						 .getBankAddressVO().getCountryCode()));
			} else {
				cStmtObject.setInt(18, 0);
			}// end of else
		//	System.out.println("getCountryCode..."
			//		+ accountDetailVO.getBankAddressVO().getCountryCode());
			// cStmtObject.setString(MANAGEMENT_NAME,
			// accountDetailVO.getManagementName());
			// cStmtObject.setString(ISSUE_CHEQUES_TYPE_ID,
			// accountDetailVO.getIssueChqTo());
			if (accountDetailVO.getHospGnrlSeqId() != null) {
                cStmtObject.setLong(19, accountDetailVO.getHospGnrlSeqId());
			}// end of if(accountDetailVO.getHospGnrlSeqId() != null)
			else {
				cStmtObject.setString(19, null);
			}// end of else
		//	System.out.println("accountDetailVO.getHospGnrlSeqId()..."+accountDetailVO.getHospGnrlSeqId());
            cStmtObject.setString(20, accountDetailVO.getEmplFeeChrgYn());
		//	System.out.println("accountDetailVO.getEmplFeeChrgYn()..."+accountDetailVO.getEmplFeeChrgYn());
		//	cStmtObject.setString(21, accountDetailVO.getPayOrdType());
			if (!"".equals(TTKCommon.checkNull(accountDetailVO.getPayOrdType()))) {
            cStmtObject.setString(21, accountDetailVO.getPayOrdType());
			} else {
				cStmtObject.setString(21, null);
			}
		//	System.out.println("accountDetailVO.getPayOrdType()..."+accountDetailVO.getPayOrdType());
            cStmtObject.setString(22, accountDetailVO.getPayOrdNmbr());
		//	System.out.println("accountDetailVO.getPayOrdNmbr()..."+accountDetailVO.getPayOrdNmbr());
			if (accountDetailVO.getPayOrdAmount() != null) {
				cStmtObject
						.setBigDecimal(23, accountDetailVO.getPayOrdAmount());
			}// end of if(accountDetailVO.getPayOrdAmount()!= null)
			else {
				cStmtObject.setBigDecimal(23, null);
			}// end of else
		//	System.out.println("accountDetailVO.getPayOrdAmount()..."+accountDetailVO.getPayOrdAmount());
			if (accountDetailVO.getPayOrdRcvdDate() != null) {
				cStmtObject.setTimestamp(24, new Timestamp(accountDetailVO
						.getPayOrdRcvdDate().getTime()));
			}// end of if(accountDetailVO.getPayOrdRcvdDate() != null)
			else {
                cStmtObject.setTimestamp(24, null);
			}// end of else
	//		System.out.println("accountDetailVO.getPayOrdRcvdDate().getTime()..."+accountDetailVO.getPayOrdRcvdDate().getTime());
            cStmtObject.setString(25, accountDetailVO.getPayOrdBankName());
//			System.out.println("accountDetailVO.getPayOrdBankName()..."+accountDetailVO.getPayOrdBankName());
			if (accountDetailVO.getChkIssuedDate() != null) {
				cStmtObject.setTimestamp(26, new Timestamp(accountDetailVO
						.getChkIssuedDate().getTime()));
			}// end of if(accountDetailVO.getChkIssuedDate() != null)
			else {
                cStmtObject.setTimestamp(26, null);
			}// end of else
	//		System.out.println("accountDetailVO..getPayOrdBankAddressVO().getChkIssuedDate()...."+accountDetailVO.getPayOrdBankAddressVO().getAddress1());
			cStmtObject.setString(27, accountDetailVO.getPayOrdBankAddressVO()
					.getAddress1());
	//		System.out.println("accountDetailVO..getPayOrdBankAddressVO().getAddress1()...."+accountDetailVO.getPayOrdBankAddressVO().getAddress1());
			cStmtObject.setString(28, accountDetailVO.getPayOrdBankAddressVO()
					.getAddress2());
//			System.out.println("accountDetailVO..getPayOrdBankAddressVO().getAddress2()...."+accountDetailVO.getPayOrdBankAddressVO().getAddress2());
			cStmtObject.setString(29, accountDetailVO.getPayOrdBankAddressVO()
					.getAddress3());
	//		System.out.println("accountDetailVO..getPayOrdBankAddressVO().getAddress3()...."+accountDetailVO.getPayOrdBankAddressVO().getAddress3());
			cStmtObject.setString(30, accountDetailVO.getPayOrdBankAddressVO()
					.getCityCode());
	//		System.out.println("accountDetailVO..getPayOrdBankAddressVO().getCityCode()...."+accountDetailVO.getPayOrdBankAddressVO().getCityCode());
			cStmtObject.setString(31, accountDetailVO.getPayOrdBankAddressVO()
					.getStateCode());
	//		System.out.println("accountDetailVO..getPayOrdBankAddressVO().getPinCode()...."+accountDetailVO.getPayOrdBankAddressVO().getPinCode());
			/*
			 * cStmtObject.setLong(32, Long.parseLong(accountDetailVO
			 * .getPayOrdBankAddressVO().getPinCode()));
			 */
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getBankAddressVO().getPinCode()))) {
				 cStmtObject.setLong(32, Long.parseLong(accountDetailVO
							.getBankAddressVO().getPinCode()));
			} else {
				cStmtObject.setLong(32, 0);
			}// end of else
//			System.out.println("accountDetailVO..getPayOrdBankAddressVO().getPinCode()...."+accountDetailVO.getPayOrdBankAddressVO().getPinCode());
			/*
			 * cStmtObject.setInt(33, Integer.parseInt(accountDetailVO
			 * .getPayOrdBankAddressVO().getCountryCode()));
			 */
			if (!"".equals(TTKCommon.checkNull(accountDetailVO
					.getPayOrdBankAddressVO().getCountryCode()))) {
				 cStmtObject.setInt(33, Integer.parseInt(accountDetailVO
							.getPayOrdBankAddressVO().getCountryCode()));
			} else {
				cStmtObject.setInt(33, 0);
			}// end of else
	//		System.out.println("accountDetailVO..getPayOrdBankAddressVO().getCountryCode()...."+accountDetailVO.getPayOrdBankAddressVO().getCountryCode());
			if (accountDetailVO.getBankGuantSeqId() != null) {
                cStmtObject.setLong(34, accountDetailVO.getBankGuantSeqId());
			}// end of if(accountDetailVO.getBankGuantSeqId() != null)
			else {
				cStmtObject.setLong(34, 0);
			}// end of else
		//	System.out.println("accountDetailVO.getBankGuantSeqId()...."+accountDetailVO.getBankGuantSeqId());
			if (accountDetailVO.getEmplSeqId() != null) {
                cStmtObject.setLong(35, accountDetailVO.getEmplSeqId());
			}// end of if(accountDetailVO.getEmplSeqId() != null)
			else {
				cStmtObject.setLong(35, 0);
			}// end of else
	//		System.out.println("accountDetailVO.getBankGuantReqYN()...."+accountDetailVO.getEmplSeqId());
			cStmtObject.setString(36, accountDetailVO.getBankGuantReqYN());
	//		System.out.println("accountDetailVO.getBankGuantReqYN()...."+accountDetailVO.getBankGuantReqYN());
			if (hospitalAuditVO != null) {
				cStmtObject.setLong(37, 0);
				cStmtObject.setString(38, hospitalAuditVO.getModReson());
				if (hospitalAuditVO.getRefDate() != null) {
					cStmtObject.setTimestamp(39, new Timestamp(hospitalAuditVO
							.getRefDate().getTime()));
				}// end of if(hospitalAuditVO.getRefDate() != null)
				else {
                    cStmtObject.setTimestamp(39, null);
				}// end of else
				cStmtObject.setString(40, hospitalAuditVO.getRefNmbr());
				cStmtObject.setString(41, hospitalAuditVO.getRemarks());
            }// end of if(hospitalAuditVO != null)
			else {
				cStmtObject.setLong(37, 0);
				cStmtObject.setString(38, null);
                cStmtObject.setTimestamp(39, null);
				cStmtObject.setString(40, null);
				cStmtObject.setString(41, null);
            }// end of else
		//	System.out.println("hospitalAuditVOhospitalAuditVO.getModReson()"+hospitalAuditVO.getModReson());
		//	System.out.println("hospitalAuditVO.getRefDate().getTime()"+hospitalAuditVO.getRefDate().getTime());
		//	System.out.println("hospitalAuditVO.getRefNmbr()"+hospitalAuditVO.getRefNmbr());
		//	System.out.println("hospitalAuditVO.getRemarks()"+hospitalAuditVO.getRemarks());
		//	cStmtObject.setLong(42, accountDetailVO.getUpdatedBy());
			if (!"".equals(TTKCommon.checkNull(accountDetailVO.getUpdatedBy()))) {
			cStmtObject.setLong(42, accountDetailVO.getUpdatedBy());
			} else {
				cStmtObject.setLong(42, 0);
			}
		//	System.out.println("accountDetailVO..getUpdatedBy()"+accountDetailVO.getUpdatedBy());
			if (!TTKCommon.checkNull(accountDetailVO.getStartDate()).equals("")) {
				cStmtObject.setTimestamp(43, new Timestamp(accountDetailVO
						.getStartDate().getTime()));

			} else {
				cStmtObject.setString(43, null);
			}
		//	System.out.println("accountDetailVO..getStartDate().getTime()...."+accountDetailVO.getStartDate().getTime());
			if (!TTKCommon.checkNull(accountDetailVO.getEndDate()).equals("")) {
				cStmtObject.setTimestamp(44, new Timestamp(accountDetailVO
						.getEndDate().getTime()));
				// cStmtObject.setDate(END_DATE, new
				// java.sql.Date(accountDetailVO.getEndDate().getTime()));
			} else {
				cStmtObject.setTimestamp(44, null);
			}
	//		System.out.println("accountDetailVO..getEndDate().getTime()...."+accountDetailVO.getEndDate().getTime());

			cStmtObject.registerOutParameter(45, Types.INTEGER);
			cStmtObject.registerOutParameter(1, Types.BIGINT);
			cStmtObject.registerOutParameter(2, Types.BIGINT);
			cStmtObject.registerOutParameter(3, Types.BIGINT);
			cStmtObject.registerOutParameter(37, Types.BIGINT);
           cStmtObject.setString(46, accountDetailVO.getRouteCode());
            cStmtObject.execute();
			conn.commit();
            iResult = cStmtObject.getInt(45);
		}// end of try
		catch (SQLException sqlExp) {
            throw new TTKException(sqlExp, "account");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
            throw new TTKException(exp, "account");
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
							"Error while closing the Statement in AccountDAOImpl addUpdateAccount()",
							sqlExp);
        			throw new TTKException(sqlExp, "account");
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
								"Error while closing the Connection in AccountDAOImpl addUpdateAccount()",
								sqlExp);
        				throw new TTKException(sqlExp, "account");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
        		throw new TTKException(exp, "account");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
        	{
        		cStmtObject = null;
        		conn = null;
			}// end of finally
		}// end of finally
        return iResult;
	}// end of addUpdateAccount(AccountDetailVO accountDetailVO)

}// end of AccountDAOImpl
