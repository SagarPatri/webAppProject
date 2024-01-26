package com.ttk.dao.impl.finance;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.jboss.jca.adapters.jdbc.jdk6.WrappedConnectionJDK6;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dao.impl.empanelment.HospitalDAOImpl;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.DocumentDetailVO;
import com.ttk.dto.empanelment.HospitalAuditVO;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.finance.BankAccountDetailVO;
import com.ttk.dto.finance.CustomerBankDetailsVO;
/**
 * This class is added for cr koc 1103
 * added eft
 */

    public class CustomerBankDetailsDAOImpl implements BaseDAO, Serializable {
    	
	private static Logger log = Logger.getLogger(CustomerBankDetailsDAOImpl.class);
   //private static final String strdestination_Bank ="SELECT DISTINCT A.BANK_NAME FROM APP.TPA_IFSC_CODE_DETAILS A ORDER BY A.BANK_NAME";
	private static final String strdestination_Bank ="select upper(bank_name) AS BANK_NAME from ( SELECT distinct A.BANK_NAME FROM APP.TPA_IFSC_CODE_DETAILS A ORDER BY A.BANK_NAME) B";
	private static final String strBankState ="SELECT DISTINCT A.STATE_TYPE_ID,A.STATE_NAME FROM APP.TPA_STATE_CODE A JOIN APP.TPA_ENR_BANK_NAME B ON(A.STATE_TYPE_ID=B.STATE_TYPE_ID)WHERE UPPER(B.BANK_NAME)=? ORDER BY A.STATE_NAME";
	private static final String strGetMouFils ="SELECT I.MOU_DOC_SEQ_ID,GC.DESCRIPTION,to_char(I.added_date,'MM/DD/YYYY HH24:MI:SS' ) as ADDED_DATE, UC.CONTACT_NAME,I.DOCS_PATH,I.HOSP_SEQ_ID,I.FILE_NAME FROM APP.MOU_DOCS_INFO I JOIN APP.TPA_GENERAL_CODE GC ON (I.DOCS_GENTYPE_ID = GC.GENERAL_TYPE_ID) JOIN APP.TPA_USER_CONTACTS UC ON(UC.CONTACT_SEQ_ID=I.ADDED_BY) WHERE I.HOSP_SEQ_ID=? ORDER BY I.MOU_DOC_SEQ_ID DESC";   
	private static final String strGetFinanefiles="SELECT I.MOU_DOC_SEQ_ID,GC.DESCRIPTION,to_char(I.added_date,'MM/DD/YYYY HH24:MI:SS' ) as ADDED_DATE,UC.CONTACT_NAME,I.DOCS_PATH,I.HOSP_SEQ_ID,I.FILE_NAME FROM APP.MOU_DOCS_INFO I JOIN APP.TPA_GENERAL_CODE GC ON (I.DOCS_GENTYPE_ID = GC.GENERAL_TYPE_ID)JOIN APP.TPA_USER_CONTACTS UC ON(UC.CONTACT_SEQ_ID=I.ADDED_BY) WHERE I.HOSP_SEQ_ID=? and (i.added_date)::date=(current_Date)ORDER BY I.MOU_DOC_SEQ_ID desc";
	//"SELECT STATE_TYPE_ID,STATE_NAME FROM APP.TPA_STATE_CODE WHERE STATE_TYPE_ID IN(SELECT DISTINCT SATE_TYPE_ID FROM APP.TPA_ENR_BANK_NAME WHERE BANK_NAME=?)";
	//private static final String strBankState = "SELECT DISTINCT STATE  FROM  APP.TPA_IFSC_CODE_DETAILS";
//	private static final String strBankCity ="SELECT  DISTINCT B.CITY_TYPE_ID,B.CITY_DESCRIPTION  FROM  APP.TPA_STATE_CODE A JOIN APP.TPA_CITY_CODE B ON(A.STATE_TYPE_ID=B.STATE_TYPE_ID) JOIN APP.TPA_IFSC_CODE_DETAILS C ON(B.CITY_TYPE_ID=C.CITY_TYPE_ID)WHERE C.STATE_TYPE_ID=? AND UPPER(C.BANK_NAME)=? ORDER BY B.CITY_DESCRIPTION"; 
	private static final String strBankCity="SELECT DISTINCT B.CITY_TYPE_ID,B.CITY_DESCRIPTION FROM APP.TPA_STATE_CODE A JOIN APP.TPA_CITY_CODE B ON(A.STATE_TYPE_ID=B.STATE_TYPE_ID) WHERE A.STATE_TYPE_ID=? ORDER BY B.CITY_DESCRIPTION ";

		//"SELECT CITY_TYPE_ID,CITY_DESCRIPTION FROM APP.TPA_CITY_CODE WHERE STATE_TYPE_ID=?";
	   // private static final String strBankBranch ="SELECT DISTINCT A.BRANCH_SEQ_ID,A.BRANCH_NAME,A.CITY_TYPE_ID,A.STATE_TYPE_ID FROM APP.TPA_BRANCH_CODE A join APP.TPA_IFSC_CODE_DETAILS B  on (b.BRANCH_SEQ_ID=a.branch_seq_id) WHERE  b.STATE_TYPE_ID=? AND b.CITY_TYPE_ID=? AND B.BANK_NAME =? ORDER BY A.BRANCH_NAME ";
	   private static final String strBankBranch ="SELECT DISTINCT A.BRANCH_SEQ_ID,intx.ttk_util_pkg_fn_decrypt(A.BRANCH_NAME) as BRANCH_NAME ,A.CITY_TYPE_ID,A.STATE_TYPE_ID FROM APP.TPA_BRANCH_CODE A join APP.TPA_IFSC_CODE_DETAILS B on (b.BRANCH_SEQ_ID=a.branch_seq_id) WHERE b.STATE_TYPE_ID=? AND b.CITY_TYPE_ID=? AND UPPER(B.BANK_NAME) =? ORDER BY BRANCH_NAME";
		//"SELECT  DISTINCT A.BRANCH_SEQ_ID,A.BRANCH_NAME FROM APP.TPA_BRANCH_CODE A join APP.TPA_IFSC_CODE_DETAILS B  on (b.BRANCH_CODE=a.branch_seq_id)WHERE  b.STATE_TYPE_ID=? AND b.CITY_TYPE_ID=? AND B.BANK_NAME =?";

	//"SELECT A.BRANCH_SEQ_ID,A.BRANCH_NAME FROM APP.TPA_BRANCH_CODE A ,APP.TPA_IFSC_CODE_DETAILS B WHERE A.CITY_TYPE_ID=B.CITY_TYPE_ID AND B.STATE_CODE=A.STATE_TYPE_ID AND A.STATE_TYPE_ID=? AND A.CITY_TYPE_ID=? AND B.BANK_NAME=?";
		//"SELECT BRANCH_SEQ_ID,BRANCH_NAME FROM APP.TPA_BRANCH_CODE WHERE STATE_TYPE_ID=? AND CITY_TYPE_ID=?";
	private static final String strIfscMicr ="SELECT B.IFSC_CODE,B.BANK_NAME,B.BRANCH_SEQ_ID,B.STATE_TYPE_ID FROM APP.TPA_IFSC_CODE_DETAILS B WHERE b.STATE_TYPE_ID=? AND b.CITY_TYPE_ID=? AND UPPER(B.BANK_NAME) =? AND BRANCH_SEQ_ID=?";
		//"SELECT IFSC_CODE FROM APP.TPA_IFSC_CODE_DETAILS  WHERE STATE_TYPE_ID=? AND BRANCH_CODE=? AND CITY_TYPE_ID=? AND BANK_NAME=?";
	private static final String strBankAcctSaveIfsc="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SAVE_MEM_BANK_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strMemBankAcctSaveIfsc="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SAVE_MEM_BANK_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//21 params
	private static final String strPolicyList="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SELECT_BANK_DETAILS_LIST(?,?,?,?,?,?,?,?)}";
    private static final String strBankAcct1="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SELECT_POLICY_BANK_DETAILS(?,?)}";
	private static final String strHospBankAcct1="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SELECT_HOSP_BANK_DETAILS(?,?)}";
	private static final String strBankAcct2="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SELECT_MEM_BANK_DETAILS(?,?)}";
	private static final String strHospBankAcctSaveIfsc="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SAVE_HOSP_BANK_DETAILS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//28 params, - Added 2 columns startdate and enddate for intX 
	//added eft
	//intX  
	private static final String strHospReviewAccoountValidationList ="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SLT_EMPL_UPD_HOSP_BNK_DTLS(?)}";
	private static final String strHospReviewList="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SELECT_INP_HOSP_LIST(?)}";
    private static final String strStateTypeIdListintX 	= "SELECT SC.STATE_TYPE_ID, SC.COUNTRY_ID AS COUNTRY_ID,SC.STD_CODE,CC.ISD_CODE FROM app.TPA_STATE_CODE SC JOIN app.TPA_COUNTRY_CODE CC ON(SC.COUNTRY_ID=CC.COUNTRY_ID) WHERE STATE_TYPE_ID=?";
    private static final String strCityTypeList = "SELECT CITY_TYPE_ID,CITY_DESCRIPTION FROM app.TPA_CITY_CODE WHERE STATE_TYPE_ID = ? ";
//	private static final String strAccountDetail = "SELECT C.HOSP_BANK_SEQ_ID,D.ADDR_SEQ_ID ADDR_SEQ_ID,F.ADDR_SEQ_ID POD_ADDR_SEQ_ID,C.HOSP_SEQ_ID,TTK_UTIL_PKG.FN_DECRYPT(C.BANK_NAME) BANK_NAME,TTK_UTIL_PKG.FN_DECRYPT(ACCOUNT_NUMBER) ACCOUNT_NUMBER,ACCOUNT_IN_NAME_OF,TTK_UTIL_PKG.FN_DECRYPT(BRANCH_NAME) AS BRANCH_NAME,D.ADDRESS_1 ADDRESS_1,D.ADDRESS_2 ADDRESS_2,D.ADDRESS_3 ADDRESS_3,D.CITY_TYPE_ID CITY_TYPE_ID,D.STATE_TYPE_ID STATE_TYPE_ID,D.PIN_CODE PIN_CODE,D.COUNTRY_ID COUNTRY_ID,C.MANAGEMENT_NAME,ISSUE_CHEQUES_TYPE_ID,EMPANEL_FEES_CHRG_YN,PAY_ORDER_TYPE_ID,PAY_ORDER_NUMBER,PAY_ORDER_AMOUNT,PAY_ORDER_RECEIVED_DATE,E.HOSP_GNRL_SEQ_ID,E.BANK_NAME POD_BANK_NAME,E.CHECK_ISSUED_DATE,F.ADDRESS_1 POD_ADDRESS_1,F.ADDRESS_2 POD_ADDRESS_2,F.ADDRESS_3 POD_ADDRESS_3,F.CITY_TYPE_ID POD_CITY_TYPE_ID,F.STATE_TYPE_ID POD_STATE_TYPE_ID,F.PIN_CODE POD_PIN_CODE,F.COUNTRY_ID POD_COUNTRY_ID,G.BANK_GUANT_SEQ_ID,G.EMPANEL_SEQ_ID,G.BANK_GUANT_REQ_YN,G.BANK_NAME BGUANT_BANK_NAME,G.AMOUNT,G.COMMENCEMENT_DATE,G.EXPIRY_DATE,A.TPA_REGIST_DATE,C.START_DATE,C.END_DATE,TTK_UTIL_PKG.FN_DECRYPT(C.BANK_IFSC) BANK_IFSC,A.EMPANEL_NUMBER,C.BANK_IFSC,C.BANK_MICR,C.REVIEW_YN,CASE WHEN C.REVIEW_YN = 'Y' THEN NVL(C.UPDATED_DATE, C.ADDED_DATE) END AS REVIEWED_DATE, CASE WHEN C.REVIEW_YN = 'Y' THEN H.CONTACT_NAME END AS REVIEWED_BY,A.HOSP_NAME FROM (TPA_HOSP_INFO A INNER JOIN TPA_HOSP_EMPANEL_STATUS B ON A.HOSP_SEQ_ID = B.HOSP_SEQ_ID AND B.ACTIVE_YN = 'Y') LEFT OUTER JOIN TPA_HOSP_ACCOUNT_DETAILS C ON A.HOSP_SEQ_ID = C.HOSP_SEQ_ID LEFT OUTER JOIN TPA_HOSP_ADDRESS D ON C.HOSP_BANK_SEQ_ID = D.HOSP_BANK_SEQ_ID LEFT OUTER JOIN TPA_HOSP_GENERAL_DTL E ON B.EMPANEL_SEQ_ID = E.EMPANEL_SEQ_ID LEFT OUTER JOIN TPA_HOSP_ADDRESS F ON E.HOSP_GNRL_SEQ_ID = F.HOSP_GNRL_SEQ_ID LEFT OUTER JOIN TPA_HOSP_BANK_GUARANTEE G ON B.EMPANEL_SEQ_ID = G.EMPANEL_SEQ_ID LEFT OUTER JOIN TPA_USER_CONTACTS H ON H.CONTACT_SEQ_ID = C.UPDATED_BY WHERE A.HOSP_SEQ_ID = ?";
    private static final String strAccountDetail = "SELECT C.HOSP_BANK_SEQ_ID,D.ADDR_SEQ_ID ADDR_SEQ_ID,F.ADDR_SEQ_ID POD_ADDR_SEQ_ID,C.HOSP_SEQ_ID,intx.ttk_util_pkg_fn_decrypt(C.BANK_NAME)as BANK_NAME,intx.ttk_util_pkg_fn_decrypt(ACCOUNT_NUMBER)as ACCOUNT_NUMBER123,ACCOUNT_IN_NAME_OF,intx.ttk_util_pkg_fn_decrypt(BRANCH_NAME)as BRANCH_NAME,ACCOUNT_IN_NAME_OF,D.ADDRESS_1 ADDRESS_1,D.ADDRESS_2 ADDRESS_2,D.ADDRESS_3 ADDRESS_3,D.CITY_TYPE_ID CITY_TYPE_ID,D.STATE_TYPE_ID STATE_TYPE_ID,D.PIN_CODE PIN_CODE,D.COUNTRY_ID COUNTRY_ID,C.MANAGEMENT_NAME,ISSUE_CHEQUES_TYPE_ID,EMPANEL_FEES_CHRG_YN,PAY_ORDER_TYPE_ID,PAY_ORDER_NUMBER,PAY_ORDER_AMOUNT,PAY_ORDER_RECEIVED_DATE,E.HOSP_GNRL_SEQ_ID,E.BANK_NAME POD_BANK_NAME,E.CHECK_ISSUED_DATE,F.ADDRESS_1 POD_ADDRESS_1,F.ADDRESS_2 POD_ADDRESS_2,F.ADDRESS_3 POD_ADDRESS_3,F.CITY_TYPE_ID POD_CITY_TYPE_ID,F.STATE_TYPE_ID POD_STATE_TYPE_ID,F.PIN_CODE POD_PIN_CODE,F.COUNTRY_ID POD_COUNTRY_ID,G.BANK_GUANT_SEQ_ID,G.EMPANEL_SEQ_ID,G.BANK_GUANT_REQ_YN,G.BANK_NAME BGUANT_BANK_NAME,G.AMOUNT,G.COMMENCEMENT_DATE,G.EXPIRY_DATE,A.TPA_REGIST_DATE,C.START_DATE,C.END_DATE,intx.ttk_util_pkg_fn_decrypt(C.BANK_IFSC) as BANK_IFSC,A.EMPANEL_NUMBER,intx.ttk_util_pkg_fn_decrypt(C.BANK_IFSC) as BANK_IFSC,intx.ttk_util_pkg_fn_decrypt(C.BANK_MICR) as BANK_MICR,C.REVIEW_YN,CASE WHEN C.REVIEW_YN = 'Y' THEN coalesce(C.UPDATED_DATE, C.ADDED_DATE) END AS REVIEWED_DATE,CASE WHEN C.REVIEW_YN = 'Y' THEN H.CONTACT_NAME END AS REVIEWED_BY,A.HOSP_NAME FROM (app.TPA_HOSP_INFO A INNER JOIN app.TPA_HOSP_EMPANEL_STATUS B ON A.HOSP_SEQ_ID = B.HOSP_SEQ_ID AND B.ACTIVE_YN = 'Y') LEFT OUTER JOIN app.TPA_HOSP_ACCOUNT_DETAILS C ON A.HOSP_SEQ_ID = C.HOSP_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_ADDRESS D ON C.HOSP_BANK_SEQ_ID = D.HOSP_BANK_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_GENERAL_DTL E ON B.EMPANEL_SEQ_ID = E.EMPANEL_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_ADDRESS F ON E.HOSP_GNRL_SEQ_ID = F.HOSP_GNRL_SEQ_ID LEFT OUTER JOIN app.TPA_HOSP_BANK_GUARANTEE G ON B.EMPANEL_SEQ_ID = G.EMPANEL_SEQ_ID LEFT OUTER JOIN app.TPA_USER_CONTACTS H ON H.CONTACT_SEQ_ID = C.UPDATED_BY WHERE A.HOSP_SEQ_ID = ?";
	private static final String strAddUpdateAccountInfo = "{CALL intx.HOSPITAL_EMPANEL_PKG_pr_account_info_update(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strUploadedFiles = "SELECT P.DOCS_PATH FROM APP.MOU_DOCS_INFO P WHERE P.DOCS_GENTYPE_ID='FIND' AND P.HOSP_SEQ_ID = ? ORDER BY P.ADDED_DATE DESC";
	private static final String strBankCode = "SELECT DISTINCT A.BANK_ID,A.BANK_NAME FROM APP.TPA_IFSC_CODE_DETAILS A WHERE A.BANK_NAME=? ORDER BY A.BANK_NAME";
	private static final String strSaveMouFils	=	"{CALL intx.HOSPITAL_EMPANEL_PKG_SAVE_MOU_DOCS_INFO(?,?,?,?,?,?,?,?)}";
	private static final String strPtnrReviewList="{CALL intx.IFSC_BANK_DETAILS_UPDATE_ENR_SELECT_INP_PTNR_LIST(?)}";
//  private static final String strPartnerAccountDetail = "SELECT C.PTNR_BANK_SEQ_ID, D.ADDR_SEQ_ID  ADDR_SEQ_ID, F.ADDR_SEQ_ID POD_ADDR_SEQ_ID, C.PTNR_SEQ_ID, TTK_UTIL_PKG.FN_DECRYPT(C.BANK_NAME) BANK_NAME, TTK_UTIL_PKG.FN_DECRYPT(ACCOUNT_NUMBER) ACCOUNT_NUMBER, ACCOUNT_IN_NAME_OF, TTK_UTIL_PKG.FN_DECRYPT(BRANCH_NAME) AS BRANCH_NAME, D.ADDRESS_1 ADDRESS_1, D.ADDRESS_2 ADDRESS_2 ,D.ADDRESS_3 ADDRESS_3, D.CITY_TYPE_ID CITY_TYPE_ID, D.STATE_TYPE_ID STATE_TYPE_ID, D.PIN_CODE PIN_CODE, D.COUNTRY_ID COUNTRY_ID, EMPANEL_FEES_CHRG_YN, PAY_ORDER_TYPE_ID, PAY_ORDER_NUMBER, PAY_ORDER_AMOUNT, PAY_ORDER_RECEIVED_DATE, E.PTNR_GNRL_SEQ_ID, E.BANK_NAME POD_BANK_NAME, E.CHECK_ISSUED_DATE, F.ADDRESS_1 POD_ADDRESS_1, F.ADDRESS_2 POD_ADDRESS_2, F.ADDRESS_3 POD_ADDRESS_3, F.CITY_TYPE_ID POD_CITY_TYPE_ID, F.STATE_TYPE_ID POD_STATE_TYPE_ID, F.PIN_CODE POD_PIN_CODE, F.COUNTRY_ID POD_COUNTRY_ID, G.BANK_GUANT_SEQ_ID, G.EMPANEL_SEQ_ID, G.BANK_GUANT_REQ_YN, G.BANK_NAME BGUANT_BANK_NAME, G.AMOUNT, G.COMMENCEMENT_DATE, G.EXPIRY_DATE, C.START_DATE, C.END_DATE, TTK_UTIL_PKG.FN_DECRYPT(C.BANK_IFSC) BANK_IFSC, A.EMPANEL_NUMBER, C.BANK_IFSC, TTK_UTIL_PKG.FN_DECRYPT(C.BANK_MICR) BANK_MICR, C.REVIEW_YN, CASE WHEN C.REVIEW_YN = 'Y' THEN NVL(C.UPDATED_DATE, C.ADDED_DATE) END AS REVIEWED_DATE, CASE WHEN C.REVIEW_YN = 'Y' THEN H.CONTACT_NAME END AS REVIEWED_BY, A.PARTNER_NAME  FROM TPA_PARTNER_INFO A INNER JOIN TPA_PARTNER_EMPANEL_STATUS B ON (A.PTNR_SEQ_ID = B.PTNR_SEQ_ID AND B.ACTIVE_YN = 'Y') LEFT OUTER JOIN  TPA_PARTNER_ACCOUNT_DETAILS C ON (A.PTNR_SEQ_ID = C.PTNR_SEQ_ID) LEFT OUTER JOIN TPA_PARTNER_ADDRESS D ON (C.PTNR_BANK_SEQ_ID = D.PTNR_BANK_SEQ_ID)  LEFT OUTER JOIN TPA_PARTNER_GENERAL_DTL E ON( B.EMPANEL_SEQ_ID = E.EMPANEL_SEQ_ID) LEFT OUTER JOIN TPA_PARTNER_ADDRESS F ON (E.PTNR_GNRL_SEQ_ID = F.PTNR_GNRL_SEQ_ID) LEFT OUTER JOIN TPA_PARTNER_BANK_GUARANTEE G ON (B.EMPANEL_SEQ_ID = G.EMPANEL_SEQ_ID) LEFT OUTER JOIN TPA_USER_CONTACTS H ON (H.CONTACT_SEQ_ID = C.UPDATED_BY) WHERE A.PTNR_SEQ_ID = ?";
	private static final String strPartnerAccountDetail = "SELECT C.PTNR_BANK_SEQ_ID, D.ADDR_SEQ_ID  ADDR_SEQ_ID, F.ADDR_SEQ_ID POD_ADDR_SEQ_ID, C.PTNR_SEQ_ID,intx.ttk_util_pkg_fn_decrypt(C.BANK_NAME)as BANK_NAME , intx.ttk_util_pkg_fn_decrypt(ACCOUNT_NUMBER)as ACCOUNT_NUMBER , ACCOUNT_IN_NAME_OF, intx.ttk_util_pkg_fn_decrypt(BRANCH_NAME)as BRANCH_NAME , D.ADDRESS_1 ADDRESS_1, D.ADDRESS_2 ADDRESS_2 ,D.ADDRESS_3 ADDRESS_3, D.CITY_TYPE_ID CITY_TYPE_ID, D.STATE_TYPE_ID STATE_TYPE_ID, D.PIN_CODE PIN_CODE, D.COUNTRY_ID COUNTRY_ID, EMPANEL_FEES_CHRG_YN, PAY_ORDER_TYPE_ID, PAY_ORDER_NUMBER, PAY_ORDER_AMOUNT, PAY_ORDER_RECEIVED_DATE, E.PTNR_GNRL_SEQ_ID, E.BANK_NAME POD_BANK_NAME, E.CHECK_ISSUED_DATE, F.ADDRESS_1 POD_ADDRESS_1, F.ADDRESS_2 POD_ADDRESS_2, F.ADDRESS_3 POD_ADDRESS_3, F.CITY_TYPE_ID POD_CITY_TYPE_ID, F.STATE_TYPE_ID POD_STATE_TYPE_ID, F.PIN_CODE POD_PIN_CODE, F.COUNTRY_ID POD_COUNTRY_ID, G.BANK_GUANT_SEQ_ID, G.EMPANEL_SEQ_ID, G.BANK_GUANT_REQ_YN, G.BANK_NAME BGUANT_BANK_NAME, G.AMOUNT, G.COMMENCEMENT_DATE, G.EXPIRY_DATE, C.START_DATE, C.END_DATE, C.BANK_IFSC as BANK_IFSC, C.BANK_MICR as BANK_MICR, C.REVIEW_YN, CASE WHEN C.REVIEW_YN = 'Y' THEN coalesce(C.UPDATED_DATE, C.ADDED_DATE) END AS REVIEWED_DATE, CASE WHEN C.REVIEW_YN = 'Y' THEN H.CONTACT_NAME END AS REVIEWED_BY, A.PARTNER_NAME  FROM app.TPA_PARTNER_INFO A INNER JOIN app.TPA_PARTNER_EMPANEL_STATUS B ON (A.PTNR_SEQ_ID = B.PTNR_SEQ_ID AND B.ACTIVE_YN = 'Y') LEFT OUTER JOIN app.TPA_PARTNER_ACCOUNT_DETAILS C ON (A.PTNR_SEQ_ID = C.PTNR_SEQ_ID) LEFT OUTER JOIN app.TPA_PARTNER_ADDRESS D ON (C.PTNR_BANK_SEQ_ID = D.PTNR_BANK_SEQ_ID) LEFT OUTER JOIN app.TPA_PARTNER_GENERAL_DTL E ON( B.EMPANEL_SEQ_ID = E.EMPANEL_SEQ_ID) LEFT OUTER JOIN app.TPA_PARTNER_ADDRESS F ON (E.PTNR_GNRL_SEQ_ID = F.PTNR_GNRL_SEQ_ID) LEFT OUTER JOIN app.TPA_PARTNER_BANK_GUARANTEE G ON (B.EMPANEL_SEQ_ID = G.EMPANEL_SEQ_ID) LEFT OUTER JOIN app.TPA_USER_CONTACTS H ON (H.CONTACT_SEQ_ID = C.UPDATED_BY) WHERE A.PTNR_SEQ_ID = ?"; 
	private static final String strAddUpdatePartnerAccountInfo = "{CALL intx.PARTNER_EMPANELL_PKG_PARTNER_ACCOUNT_INFO_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";


	 

	 /**
	 * This method returns the ArrayList, which contains the customerBankDetailsVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of customerBankDetailsVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
	public ArrayList getCustomerBankAccountList(ArrayList alSearchCriteria)throws TTKException
	{ 
		CustomerBankDetailsVO customerBankDetailsVO=null;
        Collection<Object> alResultList = new ArrayList<Object>();
		//BankAccountVO bankAccountVO = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		
		ResultSet rs = null;
		// String strSuppressLink[]={};
		try
		{
			//conn = ResourceManager.getConnection();
			//cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAccountList);//add the procedure call
			conn = ResourceManager.getConnection();
			cstmt = conn.prepareCall(strPolicyList);
			String strCond=TTKCommon.checkNull((String)alSearchCriteria.get(0));
			cstmt.setString(1,(String)alSearchCriteria.get(0));
			String[] strArr= (String[]) alSearchCriteria.get(2);
            Array sqlArray=conn.createArrayOf("text",strArr);
            cstmt.setArray(2,sqlArray ); 
			cstmt.setString(3,(String)alSearchCriteria.get(1));
			cstmt.setString(4,(String)alSearchCriteria.get(3));
			cstmt.setString(5,(String)alSearchCriteria.get(4));
			cstmt.setLong(6,Long.parseLong((String)alSearchCriteria.get(5).toString()));
			cstmt.setLong(7,Long.parseLong((String)alSearchCriteria.get(6).toString()));
			cstmt.registerOutParameter(8,Types.OTHER);
			cstmt.execute();
			rs = (java.sql.ResultSet)cstmt.getObject(8);

			if(rs != null)
			{
				while (rs.next())
					{
					customerBankDetailsVO=new CustomerBankDetailsVO();
					
					
					if(strCond.equals("CLAM") || strCond.equals("POLC"))
					{
									
					customerBankDetailsVO.setPolicyNumber(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
				    customerBankDetailsVO.setPolicyCategory(TTKCommon.checkNull(rs.getString("CLASSIFICATION")));
					customerBankDetailsVO.setInsureName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));
					customerBankDetailsVO.setOfficename(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					customerBankDetailsVO.setEnrollNmbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
					customerBankDetailsVO.setPolicySeqID(new Long(TTKCommon.checkNull(rs.getString("POLICY_SEQ_ID"))));
					customerBankDetailsVO.setGroupSeqID(new Long(TTKCommon.checkNull(rs.getString("POLICY_SEQ_ID"))));
					customerBankDetailsVO.setPolicyGroupSeqID(new Long(TTKCommon.checkNull(rs.getString("POLICY_GROUP_SEQ_ID"))));
					customerBankDetailsVO.setEnrType(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					customerBankDetailsVO.setPolicyType(TTKCommon.checkNull(rs.getString("POLICY_TYPE")));
					customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("BANK_ACCOUNT_NO")));//for  accont no display
					if(customerBankDetailsVO.getEnrType().equalsIgnoreCase("COR"))
					{
					customerBankDetailsVO.setCheckIssuedTo(TTKCommon.checkNull(rs.getString("TPA_CHEQUE_ISSUED_GENERAL_TYPE")));	
					
					}
					
					}
					if(strCond.equals("CLAM"))
					{
					customerBankDetailsVO.setClimentName(TTKCommon.checkNull(rs.getString("CLAIMANT_NAME")));
					customerBankDetailsVO.setClmSetmentno(TTKCommon.checkNull(rs.getString("CLAIM_SETTLEMENT_NUMBER")));
					customerBankDetailsVO.setClaimNo(TTKCommon.checkNull(rs.getString("CLAIM_NUMBER")));
					}
					if(strCond.equals("HOSP"))
					{
						log.info("HOSPITAL-ID(HOSP)-----------"+TTKCommon.checkNull(rs.getString("HOSP_SEQ_ID")));
					customerBankDetailsVO.setHospitalSeqId(TTKCommon.checkNull(rs.getString("HOSP_SEQ_ID")));
					customerBankDetailsVO.setHospitalEmnalNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					customerBankDetailsVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					customerBankDetailsVO.setOfficename(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					customerBankDetailsVO.setHospitalEmpnalDesc(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
					customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));//for hospital accont number display
					
					}
					//customerBankDetailsVO.setSuppressLink(strSuppressLink);
					alResultList.add(customerBankDetailsVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getBankAccountList()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cstmt != null)	cstmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getBankAccountList()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getBankAccountList()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cstmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankAccountList(ArrayList alSearchCriteria)
	//adedd eft
	
	
	/**
	 * This method returns the CustomerBankDetailsVO, which contains all the bank details
	 * @param lngBankSeqID  which contains seq id of Bank get the Bank Details
	 * @return CustomerBankDetailsVO object which contains the Bank details
	 * @exception throws TTKException
	 */
	
	public CustomerBankDetailsVO getPolicyBankAccountDetail(long lngBankSeqID,long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankAccountDetailVO bankAccountDetailVO = null;
		CustomerBankDetailsVO customerBankDetailsVO=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAcct1);
			cStmtObject.setLong(1,lngBankSeqID);
			//cStmtObject.setString(2,null);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				bankAccountDetailVO = new BankAccountDetailVO();
				customerBankDetailsVO=new CustomerBankDetailsVO();
				while (rs.next()) {
					customerBankDetailsVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getString("POLICY_SEQ_ID")):null);
					
					customerBankDetailsVO.setPolicyNumber(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					customerBankDetailsVO.setEnrType(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					customerBankDetailsVO.setBankname(TTKCommon.checkNull(rs.getString("BANK_NAME")));
					customerBankDetailsVO.setPolicyType(TTKCommon.checkNull(rs.getString("POLICY_TYPE")));
					customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("BANK_ACCOUNT_NO")));
					customerBankDetailsVO.setBankPhoneno(new String(TTKCommon.checkNull(rs.getString("BANK_PHONE_NO"))));
					customerBankDetailsVO.setBankBranch(TTKCommon.checkNull(rs.getString("BRANCH_SEQ_ID")));//cr
					customerBankDetailsVO.setBankAccName(TTKCommon.checkNull(rs.getString("ACCOUNT_NAME")));
					customerBankDetailsVO.setMicr(TTKCommon.checkNull(rs.getString("BANK_MICR")));
					customerBankDetailsVO.setIfsc(TTKCommon.checkNull(rs.getString("BANK_IFSC")));
					customerBankDetailsVO.setNeft(TTKCommon.checkNull(rs.getString("BANK_NEFT")));
					customerBankDetailsVO.setBankAccType(TTKCommon.checkNull(rs.getString("ACCOUNT_NAME")));//BANK ACCOUNT TYPE
					
					customerBankDetailsVO.setBankcity(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));//bankstate//cr
					
					customerBankDetailsVO.setBankState(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));//bankState//cr
					customerBankDetailsVO.setBankSeqId(TTKCommon.checkNull(rs.getString("BANK_SEQ_ID")));//bank seq id
					customerBankDetailsVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getString("TPA_OFFICE_SEQ_ID")):null);
					customerBankDetailsVO.setInsurenceSeqId(rs.getString("INS_SEQ_ID")!=null ? new Long(rs.getString("INS_SEQ_ID")):null);//insurence seq id
					
					//TTKCommon.checkNull(rs.getString("INS_SEQ_ID")));//insurence seq id
					//customerBankDetailsVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID")!=null ? new Long(rs.getString("POLICY_GROUP_SEQ_ID")):null);
					//customerBankDetailsVO.setEnrollNmbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
					
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return customerBankDetailsVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankAccountdetail(long lngBankSeqID,long lngUserSeqID)

	/**
	 * This method returns the CustomerBankDetailsVO, which contains all the bank details
	 * @param lngBankSeqID  which contains hospital seq id of Bank get the Bank Details
	 * @return CustomerBankDetailsVO object which contains the Bank details
	 * @exception throws TTKException
	 */
	
	public CustomerBankDetailsVO getHospBankAccountdetail1(long lngBankSeqID,long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankAccountDetailVO bankAccountDetailVO = null;
		CustomerBankDetailsVO customerBankDetailsVO=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHospBankAcct1);
			log.info("BANK_SEQ_ID------"+lngBankSeqID);
			cStmtObject.setLong(1,lngBankSeqID);
			//cStmtObject.setString(2,null);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				bankAccountDetailVO = new BankAccountDetailVO();
				customerBankDetailsVO=new CustomerBankDetailsVO();
				while (rs.next()) {
					log.info("HOSPITAL-ID-----------"+TTKCommon.checkNull(rs.getString("HOSP_SEQ_ID")));
					customerBankDetailsVO.setHospitalSeqId(TTKCommon.checkNull(rs.getString("HOSP_SEQ_ID")));
					customerBankDetailsVO.setHospitalEmnalNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					customerBankDetailsVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					//customerBankDetailsVO.setHospitalbankSeqId(TTKCommon.checkNull(rs.getString("HOSP_BANK_SEQ_ID")));
					customerBankDetailsVO.setHospAddresSeqID(TTKCommon.checkNull(rs.getString("ADDR_SEQ_ID")));
					customerBankDetailsVO.setBankname(TTKCommon.checkNull(rs.getString("BANK_NAME")));
					customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));
					customerBankDetailsVO.setHospitalAccountINameOf(TTKCommon.checkNull(rs.getString("ACCOUNT_IN_NAME_OF")));
					customerBankDetailsVO.setOfficename(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					customerBankDetailsVO.setHospitalEmpnalstatusTypeId(TTKCommon.checkNull(rs.getString("EMPANEL_STATUS_TYPE_ID")));
					customerBankDetailsVO.setHospitalEmpnalDesc(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
					customerBankDetailsVO.setMicr(TTKCommon.checkNull(rs.getString("BANK_MICR")));
					customerBankDetailsVO.setIfsc(TTKCommon.checkNull(rs.getString("BANK_IFSC")));
					customerBankDetailsVO.setNeft(TTKCommon.checkNull(rs.getString("BANK_NEFT")));
					customerBankDetailsVO.setBankcity(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));//bankstate//cr
					customerBankDetailsVO.setBankState(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));//bankState//cr
					customerBankDetailsVO.setBankBranch(TTKCommon.checkNull(rs.getString("BRANCH_SEQ_ID")));//cr
					
					customerBankDetailsVO.setBankAccType(TTKCommon.checkNull(rs.getString("ACCOUNT_TYPE")));//BANK ACCOUNT TYPE
					customerBankDetailsVO.setEmpanalDescription(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
					customerBankDetailsVO.setHospitalBankSeqId(new Long((rs.getString("HOSP_BANK_SEQ_ID")!=null ? new Long(rs.getString("HOSP_BANK_SEQ_ID")):0)));
					customerBankDetailsVO.setCountryId(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
					//AK
					customerBankDetailsVO.setCountryCode("123");
					customerBankDetailsVO.setState(TTKCommon.checkNull(rs.getString("STATE")));
					customerBankDetailsVO.setPinCode(TTKCommon.checkNull(rs.getString("PIN_CODE")));
					customerBankDetailsVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
					customerBankDetailsVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
					customerBankDetailsVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
					customerBankDetailsVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getString("TPA_OFFICE_SEQ_ID")):null);
					customerBankDetailsVO.setEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));//for fetching Hospital email id
					customerBankDetailsVO.setReviewedYN(TTKCommon.checkNull(rs.getString("REVIEW_YN")));//for Review Yes/No
					
					
					if(rs.getDate("START_DATE") != null){
						customerBankDetailsVO.setStartDate(rs.getDate("START_DATE"));
                    }//end of if(rs.getDate("START_DATE") != null)
					if(rs.getDate("END_DATE") != null){
						customerBankDetailsVO.setEndDate(rs.getDate("END_DATE"));
                    }//end of if(rs.getDate("END_DATE") != null)

					
					
					
				}//end of while(rs.next())
			}//end of if(rs != null)
			return customerBankDetailsVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankAccountdetail(long lngBankSeqID,long lngUserSeqID)
	
	/**
	 * This method returns the CustomerBankDetailsVO, which contains all the bank details
	 * @param lngBankSeqID  which contains membergroup seq id of Bank get the Bank Details
	 * @return CustomerBankDetailsVO object which contains the Bank details
	 * @exception throws TTKException
	 */
	public CustomerBankDetailsVO getMemberBankAccountdetail(long lngBankSeqID,long lngUserSeqID) throws TTKException
	{
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		BankAccountDetailVO bankAccountDetailVO = null;
		CustomerBankDetailsVO customerBankDetailsVO=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAcct2);
			cStmtObject.setLong(1,lngBankSeqID);
			//cStmtObject.setString(2,null);
			cStmtObject.registerOutParameter(2,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				bankAccountDetailVO = new BankAccountDetailVO();
				customerBankDetailsVO=new CustomerBankDetailsVO();
				while (rs.next()) {
					customerBankDetailsVO.setPolicySeqID(rs.getString("POLICY_SEQ_ID")!=null ? new Long(rs.getString("POLICY_SEQ_ID")):null);
					
					customerBankDetailsVO.setPolicyNumber(TTKCommon.checkNull(rs.getString("POLICY_NUMBER")));
					customerBankDetailsVO.setEnrType(TTKCommon.checkNull(rs.getString("ENROL_TYPE_ID")));
					customerBankDetailsVO.setPolicyType(TTKCommon.checkNull(rs.getString("POLICY_TYPE")));
					customerBankDetailsVO.setBankname(TTKCommon.checkNull(rs.getString("BANK_NAME")));
					
					customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("BANK_ACCOUNT_NO")));
					customerBankDetailsVO.setBankPhoneno(new String(TTKCommon.checkNull(rs.getString("BANK_PHONE_NO"))));
					
					customerBankDetailsVO.setBankBranch(TTKCommon.checkNull(rs.getString("BRANCH_SEQ_ID")));
					customerBankDetailsVO.setBankAccName(TTKCommon.checkNull(rs.getString("ACCOUNT_NAME")));
					customerBankDetailsVO.setBankAccType(TTKCommon.checkNull(rs.getString("ACCOUNT_NAME")));//BANK ACCOUNT TYPE
					customerBankDetailsVO.setMicr(TTKCommon.checkNull(rs.getString("BANK_MICR")));
					customerBankDetailsVO.setIfsc(TTKCommon.checkNull(rs.getString("BANK_IFSC")));
					customerBankDetailsVO.setNeft(TTKCommon.checkNull(rs.getString("BANK_NEFT")));
					customerBankDetailsVO.setBankSeqId(TTKCommon.checkNull(rs.getString("BANK_SEQ_ID")));
					
					customerBankDetailsVO.setBankcity(TTKCommon.checkNull(rs.getString("CITY_TYPE_ID")));//bankcity//cr
					customerBankDetailsVO.setBankState(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));//bankState//cr
					customerBankDetailsVO.setInsureName(TTKCommon.checkNull(rs.getString("INSURED_NAME")));//INSURED_NAME
					//customerBankDetailsVO.setBankdistict(TTKCommon.checkNull(rs.getString("BANK_DISTRICT")));
					customerBankDetailsVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID")!=null ? new Long(rs.getString("TPA_OFFICE_SEQ_ID")):null);
					customerBankDetailsVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID")!=null ? new Long(rs.getString("POLICY_GROUP_SEQ_ID")):null);
					customerBankDetailsVO.setEmailID(TTKCommon.checkNull(rs.getString("EMAIL_ID")));//for fetching member email id
					//customerBankDetailsVO.setPolicyGroupSeqID(rs.getString("POLICY_GROUP_SEQ_ID")!=null ? new Long(rs.getString("POLICY_GROUP_SEQ_ID")):null);
					customerBankDetailsVO.setEnrollNmbr(TTKCommon.checkNull(rs.getString("TPA_ENROLLMENT_NUMBER")));
									
				}//end of while(rs.next())
			}//end of if(rs != null)
			return customerBankDetailsVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankAccountdetail(long lngBankSeqID,long lngUserSeqID)
	//end eft
//added eft
	
	/**
	 * This method saves the Policy Bank Account information
	 * @param customerBankDetailsVO the object which contains the Bank details which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public long saveBankAccountIfsc(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
	{
		long lngBankSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {

			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strBankAcctSaveIfsc);
			cStmtObject.setString(1,"POLC");
			if(customerBankDetailsVO.getPolicySeqID() != null){
				cStmtObject.setLong(2,customerBankDetailsVO.getPolicySeqID().longValue());
			}//end of if(bankcustomerBankDetailsVO.getAccountSeqId() != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else
			cStmtObject.setString(3,customerBankDetailsVO.getEnrType());
			if(customerBankDetailsVO.getPolicyGroupSeqID()== null || customerBankDetailsVO.getPolicyGroupSeqID().equals(""))
			{
			cStmtObject.setLong(4,0);
			}
			else
			{
				cStmtObject.setLong(4,(Long.parseLong(customerBankDetailsVO.getPolicyGroupSeqID().toString())));
				
			}
			//cStmtObject.setLong((Long.parseLong(((String)(customerBankDetailsVO.getBankSeqId()!=null ? new Long(customerBankDetailsVO.getBankSeqId()):0)))));
			if(customerBankDetailsVO.getBankSeqId()==null || customerBankDetailsVO.getBankSeqId()=="")
			{
				cStmtObject.setLong(5,0);
			}
			else
			{
				cStmtObject.setLong(5,new Long(customerBankDetailsVO.getBankSeqId()));
			}
			
			cStmtObject.setString(6,customerBankDetailsVO.getBankname());
			

			cStmtObject.setString(7,customerBankDetailsVO.getBankAccno());
			
			cStmtObject.setString(8,customerBankDetailsVO.getBankPhoneno());
			if(customerBankDetailsVO.getBankBranch()==null || customerBankDetailsVO.getBankBranch()=="")
			{
				cStmtObject.setLong(9,0);
			}
			else
			{
				cStmtObject.setLong(9,(Long.parseLong(customerBankDetailsVO.getBankBranch().toString())));
			}
		//	cStmtObject.setLong(9,Long.parseLong(customerBankDetailsVO.getBankBranch()));
			cStmtObject.setString(10,customerBankDetailsVO.getMicr());
			cStmtObject.setString(11,customerBankDetailsVO.getNeft());
			cStmtObject.setString(12,customerBankDetailsVO.getIfsc());
			cStmtObject.setString(13,customerBankDetailsVO.getBankcity());
			cStmtObject.setString(14,customerBankDetailsVO.getBankAccType());
			
			cStmtObject.setString(15, customerBankDetailsVO.getBankState());//cr
			//LOGS
			if(!TTKCommon.checkNull(customerBankDetailsVO.getModReson()).equals(""))
			{
			cStmtObject.setString(16, customerBankDetailsVO.getModReson());
			
			}
			else
			{
				cStmtObject.setString(16, null);	
			}
			if(!TTKCommon.checkNull(customerBankDetailsVO.getRefNmbr()).equals(""))
			{
			cStmtObject.setString(17, customerBankDetailsVO.getRefNmbr());
			
			}
			else
			{
				cStmtObject.setString(17, null);
			}
			if(!TTKCommon.checkNull(customerBankDetailsVO.getRefDate()).equals(""))
			{
			cStmtObject.setTimestamp(18, new Timestamp(customerBankDetailsVO.getRefDate().getTime()));
			
			}
			else
			{
				cStmtObject.setTimestamp(18, null);
			}
			if(!TTKCommon.checkNull(customerBankDetailsVO.getRemarks()).equals(""))
			{
			cStmtObject.setString(19, customerBankDetailsVO.getRemarks());
			
			}
			else
			{
				cStmtObject.setString(19, null);
			}
			//end logs
			cStmtObject.setLong(20,customerBankDetailsVO.getUpdatedBy().longValue());//ADDED_BY
			cStmtObject.setLong(21,customerBankDetailsVO.getUpdatedBy().longValue());//update_BY
			//cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
			
			cStmtObject.registerOutParameter(2,Types.BIGINT);//POLICY_SEQ_ID ROW_PROCESSED
			cStmtObject.registerOutParameter(4,Types.BIGINT);
			cStmtObject.registerOutParameter(5,Types.BIGINT);
			
			cStmtObject.execute();
			lngBankSeqID = cStmtObject.getLong(2);//POLICY_SEQ_ID
            conn.commit();
			
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl saveBankAccount()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl saveBankAccount()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngBankSeqID;
	}//end of saveBankAccount(BankcustomerBankDetailsVO bankcustomerBankDetailsVO)
	
	
	/**
	 * This method saves the Member Bank Account information
	 * @param customerBankDetailsVO the object which contains the Bank details which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public long saveMemberBankAccountIfsc(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
	{
		long lngBankSeqID = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {

			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strMemBankAcctSaveIfsc);
			cStmtObject.setString(1,"MEM");
			if(customerBankDetailsVO.getPolicySeqID() != null){
				cStmtObject.setLong(2,customerBankDetailsVO.getPolicySeqID().longValue());
			}//end of if(bankcustomerBankDetailsVO.getAccountSeqId() != null)
			else{
				cStmtObject.setLong(2,0);
			}//end of else
			cStmtObject.setString(3,customerBankDetailsVO.getEnrType());
			if(TTKCommon.checkNull(customerBankDetailsVO.getPolicyGroupSeqID())!=null)
			{
			cStmtObject.setLong(4,customerBankDetailsVO.getPolicyGroupSeqID().longValue());
			
			}
			else
			{
				cStmtObject.setLong(4,0);
				
			}
			if(customerBankDetailsVO.getBankSeqId()== null || customerBankDetailsVO.getBankSeqId()=="" ){
				
				//cStmtObject.setLong(5,Long.parseLong(customerBankDetailsVO.getBankSeqId()));
				cStmtObject.setLong(5,0);
			}
			else{
				cStmtObject.setLong(5,Long.parseLong(customerBankDetailsVO.getBankSeqId()));
			}	
			
			cStmtObject.setString(6,customerBankDetailsVO.getBankname());
			

			cStmtObject.setString(7,customerBankDetailsVO.getBankAccno());
			
			cStmtObject.setString(8,customerBankDetailsVO.getBankPhoneno());
			cStmtObject.setLong(9,customerBankDetailsVO.getBankBranch()==null || customerBankDetailsVO.getBankBranch()==""?0:Long.parseLong(customerBankDetailsVO.getBankBranch().toString()));
			cStmtObject.setString(10,customerBankDetailsVO.getMicr());
			cStmtObject.setString(11,customerBankDetailsVO.getNeft());
			cStmtObject.setString(12,customerBankDetailsVO.getIfsc());
			cStmtObject.setString(13,customerBankDetailsVO.getBankcity());//destination bank state
			cStmtObject.setString(14,customerBankDetailsVO.getBankAccType());
			cStmtObject.setString(15, customerBankDetailsVO.getBankState());//cr
			//cStmtObject.setString(15, customerBankDetailsVO.getBankdistict());
			//for logs
			if(!TTKCommon.checkNull(customerBankDetailsVO.getModReson()).equals(""))
			{
			cStmtObject.setString(16, customerBankDetailsVO.getModReson());
			
			}
			else
			{
				cStmtObject.setString(16, null);	
			}
			if(!TTKCommon.checkNull(customerBankDetailsVO.getRefNmbr()).equals(""))
			{
			cStmtObject.setString(17, customerBankDetailsVO.getRefNmbr());
			
			}
			else
			{
				cStmtObject.setString(17, null);
			}
			if(!TTKCommon.checkNull(customerBankDetailsVO.getRefDate()).equals(""))
			{
			cStmtObject.setTimestamp(18, new Timestamp(customerBankDetailsVO.getRefDate().getTime()));
			
			}
			else
			{
				cStmtObject.setTimestamp(18, null);
			}
			if(!TTKCommon.checkNull(customerBankDetailsVO.getRemarks()).equals(""))
			{
			cStmtObject.setString(19, customerBankDetailsVO.getRemarks());
			
			}
			else
			{
				cStmtObject.setString(19, null);
			}
			//end logs
			//cStmtObject.setString(TDS_BANK_ACCOUNT_YN,bankcustomerBankDetailsVO.getTdsPurposeYN());
			cStmtObject.setLong(20,(customerBankDetailsVO.getUpdatedBy().longValue()));//ADDED_BY
			cStmtObject.setLong(21,(customerBankDetailsVO.getUpdatedBy().longValue()));//ADDED_BY
			//cStmtObject.setLong(17,customerBankDetailsVO.getUpdatedBy());//update_BY
			//cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
			
			cStmtObject.registerOutParameter(2,Types.BIGINT);//POLICY_SEQ_ID ROW_PROCESSED
			cStmtObject.registerOutParameter(4,Types.BIGINT);
			cStmtObject.registerOutParameter(5,Types.BIGINT);
			
			cStmtObject.execute();
			lngBankSeqID = cStmtObject.getLong(2);//POLICY_SEQ_ID
			conn.commit();

		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl saveBankAccount()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl saveBankAccount()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return lngBankSeqID;
	}//end of saveBankAccount(BankcustomerBankDetailsVO bankcustomerBankDetailsVO)
	
	
	/**
	 * This method saves the Hospiatl Bank Account information
	 * @param customerBankDetailsVO the object which contains the Bank details which has to be saved
	 * @return long the value which returns greater than zero for succcesssful execution of the task
	 * @exception throws TTKException
	 */

	public long saveHospBankAccountIfsc(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
	{
		long iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try {
			conn = ResourceManager.getConnection();
			conn.setAutoCommit(false);
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHospBankAcctSaveIfsc);
			if(customerBankDetailsVO.getHospitalBankSeqId() != null)
            {
				cStmtObject.setLong(1,customerBankDetailsVO.getHospitalBankSeqId().longValue());
            }//end of if(customerBankDetailsVO.getHospBankSeqId() != null)
            else
            {
                cStmtObject.setLong(1,0);
            }//end of else
			if(!TTKCommon.checkNull(customerBankDetailsVO.getHospAddresSeqID()).equals(""))
            {
                cStmtObject.setLong(2,new Long(customerBankDetailsVO.getHospAddresSeqID()).longValue());
            }//end of if(customerBankDetailsVO.getBankAddressVO().getAddrSeqId() != null)
            else
            {
                cStmtObject.setLong(2,0);
            }//end of else
            if(customerBankDetailsVO.getHospitalSeqId() != null)
            {
                cStmtObject.setLong(3,new Long(customerBankDetailsVO.getHospitalSeqId()).longValue());
            }//end of if(customerBankDetailsVO.getPayOrdBankAddressVO().getAddrSeqId() != null)
            else
            {
                cStmtObject.setLong(3,0);
            }//end of else
            cStmtObject.setString(4,customerBankDetailsVO.getBankname());
			cStmtObject.setString(5,customerBankDetailsVO.getBankAccno());
			cStmtObject.setString(6,customerBankDetailsVO.getHospitalAccountINameOf());
			cStmtObject.setString(7,customerBankDetailsVO.getBankBranch());
			cStmtObject.setString(8,customerBankDetailsVO.getMicr());
			cStmtObject.setString(9,customerBankDetailsVO.getNeft());
			cStmtObject.setString(10,customerBankDetailsVO.getIfsc());
			cStmtObject.setString(11,customerBankDetailsVO.getBankAccType());
			cStmtObject.setString(12,customerBankDetailsVO.getIssueChqToHosp());
			cStmtObject.setString(13,customerBankDetailsVO.getAddress1());
			cStmtObject.setString(14,customerBankDetailsVO.getAddress2());
			cStmtObject.setString(15,customerBankDetailsVO.getAddress3());
			cStmtObject.setString(16, customerBankDetailsVO.getBankState());//destination bank state
			cStmtObject.setString(17,customerBankDetailsVO.getBankcity());
/*			cStmtObject.setLong(18,Long.parseLong(customerBankDetailsVO.getBankBranch()));*/
			if(customerBankDetailsVO.getBankBranch()== null || customerBankDetailsVO.getBankBranch()==""){
				cStmtObject.setLong(18,0);
			}
			else{
				cStmtObject.setLong(18,Long.parseLong(customerBankDetailsVO.getBankBranch().toString()));
			}
			
/*			cStmtObject.setLong(19,Long.parseLong(customerBankDetailsVO.getPinCode()));*/
			if(customerBankDetailsVO.getPinCode()== null || customerBankDetailsVO.getPinCode()==""){
				cStmtObject.setLong(19,0);
			}
			else{
			cStmtObject.setLong(19,Long.parseLong(customerBankDetailsVO.getPinCode().toString()));
			}
			cStmtObject.setInt(20,Integer.parseInt(customerBankDetailsVO.getCountryCode()));
			cStmtObject.setLong(21,0);
			if(!TTKCommon.checkNull(customerBankDetailsVO.getModReson()).equals(""))
			{
			cStmtObject.setString(22, customerBankDetailsVO.getModReson());
			}
			else
			{
				cStmtObject.setString(22, null);
				
			}
			cStmtObject.setString(23,"Y");
			if(!TTKCommon.checkNull(customerBankDetailsVO.getRefDate()).equals(""))
			{
			cStmtObject.setTimestamp(24, new Timestamp(customerBankDetailsVO.getRefDate().getTime()));
			}
			else
			{
				cStmtObject.setTimestamp(24, null);
			}
			if(!TTKCommon.checkNull(customerBankDetailsVO.getRefNmbr()).equals(""))
			{
			cStmtObject.setString(25, customerBankDetailsVO.getRefNmbr());
			}
			else
			{
				cStmtObject.setString(25, null);
			}
			
			if(!TTKCommon.checkNull(customerBankDetailsVO.getRemarks()).equals(""))
			{
			cStmtObject.setString(26, customerBankDetailsVO.getRemarks());
			}
			else
			{
				cStmtObject.setString(26, null);
			}
			cStmtObject.setLong(27 , customerBankDetailsVO.getUpdatedBy().longValue());
			 //cStmtObject.registerOutParameter(28,Types.INTEGER);
           if(!TTKCommon.checkNull(customerBankDetailsVO.getStartDate()).equals(""))
			{
			cStmtObject.setTimestamp(28, new Timestamp(customerBankDetailsVO.getStartDate().getTime()));
			}else
			{
				cStmtObject.setTimestamp(28, null);
			}
			if(!TTKCommon.checkNull(customerBankDetailsVO.getEndDate()).equals(""))
			{
			cStmtObject.setTimestamp(29, new Timestamp(customerBankDetailsVO.getEndDate().getTime()));
			}
			else
			{
				cStmtObject.setTimestamp(29, null);
			}
			if("on".equalsIgnoreCase(customerBankDetailsVO.getReviewedYN()))//intX account review
				cStmtObject.setString(30,"Y");//intX
			else
				cStmtObject.setString(30,"N");//intX
			cStmtObject.registerOutParameter(31,Types.BIGINT);
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(2,Types.BIGINT);
			cStmtObject.registerOutParameter(3,Types.BIGINT);
			cStmtObject.registerOutParameter(21,Types.BIGINT);
			
            cStmtObject.execute();
           
            //iResult = cStmtObject.getLong(28);
          // iResult = cStmtObject.getInt(28);
            
            //iResult = cStmtObject.getBigDecimal(28).intValue();
            iResult = cStmtObject.getLong(1);
            long m = cStmtObject.getLong(31);
            conn.commit();
         }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
        			log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl saveBankAccount()",sqlExp);
        			throw new TTKException(sqlExp, "bankaccount");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl saveBankAccount()",sqlExp);
        				throw new TTKException(sqlExp, "bankaccount");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "bankaccount");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return iResult;
	}//end of saveBankAccount(BankcustomerBankDetailsVO bankcustomerBankDetailsVO)

	/**
	 * This method returns the HashMap,which contains the City Types associating the State
	 * @return HashMap containing City Types associating the State
	 * @exception throws TTKException
	 */
	public HashMap getbankStateInfo() throws TTKException
	{
		
		Connection conn = null;
    	PreparedStatement pStmt1 = null;
     	PreparedStatement pStmt2 = null;
     	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	CacheObject cacheObject = null;
    	HashMap<Object,Object> hmCityList = null;
    	ArrayList<Object> alCityList = null;
    	String strStateTypeId = "",strBankCode="";
    	try{
    		conn = ResourceManager.getConnection();
    		pStmt1=conn.prepareStatement(strdestination_Bank);
    		pStmt2 = conn.prepareStatement(strBankState);
    		rs1= pStmt1.executeQuery();
    		if(rs1 != null){
    			while(rs1.next()){
    				if(hmCityList == null){
    					hmCityList=new HashMap<Object,Object>();
    				}//end of if(hmCityList == null)
    				strStateTypeId = TTKCommon.checkNull(rs1.getString("BANK_NAME"));
    				pStmt2.setString(1,strStateTypeId);
    				rs2=pStmt2.executeQuery();
    				alCityList = null;
    				if(rs2 != null){
    					while(rs2.next()){
    						cacheObject = new CacheObject();
    						if(alCityList == null){
    							alCityList = new ArrayList<Object>();
    						}//end of if(alCityList == null)
    						cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("STATE_TYPE_ID")));
    						cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("STATE_NAME")));
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
					log.error("Error while closing the Second Resultset in HospitalDAOImpl getCityInfo()",sqlExp);
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
						log.error("Error while closing the First Resultset in HospitalDAOImpl getCityInfo()",sqlExp);
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
							log.error("Error while closing the Second Statement in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
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
								log.error("Error while closing the First Statement in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
								throw new TTKException(sqlExp, "hospital");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
							{
								try{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
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

	}
	
	
	/**
	 * This method returns the HashMap,which contains the Distict Types associating the State
	 * @return HashMap containing Distict Types associating the State And Bank Name
	 * @exception throws TTKException
	 */
	public HashMap getBankCityInfo(String bankState,String bankName) throws TTKException
	{
		
		Connection conn = null;
    	PreparedStatement pStmt1 = null;
     	PreparedStatement pStmt2 = null;
     	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	CacheObject cacheObject = null;
    	//HashMap<Object,Object> hmCityList = null;
    	HashMap<Object,Object> hmDistList = null;
    	ArrayList<Object> alDistList = null;
    	//ArrayList<Object> alCityList = null;
    	String strStateTypeId = "";
    	try{
    		conn = ResourceManager.getConnection();
    		pStmt1=conn.prepareStatement(strdestination_Bank);
    		pStmt2 = conn.prepareStatement(strBankCity);
    		//rs1= pStmt1.executeQuery();
    		/*if(rs1 != null){
    			while(rs1.next()){
    				if(hmDistList == null){
    					hmDistList=new HashMap<Object,Object>();
    				}//end of if(hmCityList == null)
*/    				//strStateTypeId = TTKCommon.checkNull(rs1.getString("BANK_NAME"));
    		  if(hmDistList == null){
				hmDistList=new HashMap<Object,Object>();
			   }//end of if(hmCityList == null)
    				pStmt2.setString(1,bankState);
    				//pStmt2.setString(2,bankName);
    				
    				rs2=pStmt2.executeQuery();
    				alDistList = null;
    				if(rs2 != null){
    					while(rs2.next()){
    						cacheObject = new CacheObject();
    						if(alDistList == null){
    							alDistList = new ArrayList<Object>();
    						}//end of if(alCityList == null)
    						cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("CITY_TYPE_ID")));
    						cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("CITY_DESCRIPTION")));
    						alDistList.add(cacheObject);
    					}//end of while(rs2.next())
    				}//end of if(rs2 != null)
    				hmDistList.put(bankState,alDistList);
    				if (rs2 != null){
        				rs2.close();
        			}//end of if (rs2 != null)
        			rs2 = null;
            	/*}//end of while(rs1.next())
    		}//end of if(rs1 != null)
*/    		if (pStmt2 != null){
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
			// Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Resultset in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
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
						log.error("Error while closing the First Resultset in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
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
							log.error("Error while closing the Second Statement in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
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
								log.error("Error while closing the First Statement in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
								throw new TTKException(sqlExp, "hospital");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
							{
								try{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in HospitalDAOImpl getCityInfo()",sqlExp);
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
    	return hmDistList;
	}
	 /**
	 * This method returns the HashMap,which contains the BankBranch Types associating the State,BankNmae,BankCity
	 * @return HashMap containing BankBranch Types associating the State And Bank Name
	 * @exception throws TTKException
	 */
	public HashMap getBankBranchtInfo(String bankState,String bankName,String bankDistict) throws TTKException
	{
		
		Connection conn = null;
    	PreparedStatement pStmt1 = null;
     	PreparedStatement pStmt2 = null;
     	ResultSet rs1 = null;
    	ResultSet rs2 = null;
    	CacheObject cacheObject = null;
    	//HashMap<Object,Object> hmCityList = null;
    	HashMap<Object,Object> hmBranchList = null;
    	ArrayList<Object> alBranchtList = null;
    	//ArrayList<Object> alCityList = null;
    	String strStateTypeId = "";
    	try{
    		conn = ResourceManager.getConnection();
    		pStmt1=conn.prepareStatement(strdestination_Bank);
    		pStmt2 = conn.prepareStatement(strBankBranch);
    		//rs1= pStmt1.executeQuery();
    		/*if(rs1 != null){
    			while(rs1.next()){
    				if(hmBranchList == null){
    					hmBranchList=new HashMap<Object,Object>();
    				}//end of if(hmCityList == null)
*/    			//	strStateTypeId = TTKCommon.checkNull(rs1.getString("BANK_NAME"));
    		   if(hmBranchList == null){
				hmBranchList=new HashMap<Object,Object>();
			     }//end of if(hmCityList == null)
    				pStmt2.setString(1,bankState);
    				pStmt2.setString(2,bankDistict);
    				pStmt2.setString(3,bankName);
    				rs2=pStmt2.executeQuery();
    				alBranchtList = null;
    				if(rs2 != null){
    					while(rs2.next()){
    						cacheObject = new CacheObject();
    						if(alBranchtList == null){
    							alBranchtList = new ArrayList<Object>();
    						}//end of if(alCityList == null)
    						cacheObject.setCacheId(TTKCommon.checkNull(rs2.getString("BRANCH_SEQ_ID")));
    						cacheObject.setCacheDesc(TTKCommon.checkNull(rs2.getString("BRANCH_NAME")));
    						alBranchtList.add(cacheObject);
    					}//end of while(rs2.next())
    				}//end of if(rs2 != null)
    				hmBranchList.put(bankDistict,alBranchtList);
    				if (rs2 != null){
        				rs2.close();
        			}//end of if (rs2 != null)
        			rs2 = null;
            	/*}//end of while(rs1.next())
    		}//end of if(rs1 != null)
*/    		if (pStmt2 != null){
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
			// Nested Try Catch to ensure resource closure  
			try // First try closing the result set
			{
				try
				{
					if (rs2 != null) rs2.close();
				}//end of try
				catch (SQLException sqlExp)
				{
					log.error("Error while closing the Second Resultset in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
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
						log.error("Error while closing the First Resultset in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
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
							log.error("Error while closing the Second Statement in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
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
								log.error("Error while closing the First Statement in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
								throw new TTKException(sqlExp, "hospital");
							}//end of catch (SQLException sqlExp)
							finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
							{
								try{
									if(conn != null) conn.close();
								}//end of try
								catch (SQLException sqlExp)
								{
									log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getCityInfo()",sqlExp);
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
    	return hmBranchList;

	}
	
	/**
	 * This method returns the HashMap,which contains the IFSC Types associating the State,BankNmae,BankCity,BankBranch
	 * @return CustomerBankDetailsVO which containes IFSC code
	 * @exception throws TTKException
	 */
	public CustomerBankDetailsVO getBankIfscInfo(String bankState,String bankName,String bankDistict,String bankBranch) throws TTKException
	{
		
		Connection conn = null;
		PreparedStatement pStmt=null;
		ResultSet rs = null;
		BankAccountDetailVO bankAccountDetailVO = null;
		CustomerBankDetailsVO customerBankDetailsVO=null;
		try{
			conn = ResourceManager.getConnection();
			pStmt=conn.prepareStatement(strIfscMicr);
			
			//pStmt.setString(1, bankName);
			pStmt.setString(1, bankState);
			pStmt.setString(2, bankDistict);
			pStmt.setString(3, bankName);
			pStmt.setString(4, bankBranch);
			
			
			rs= pStmt.executeQuery();
			if(rs != null){
				bankAccountDetailVO = new BankAccountDetailVO();
				customerBankDetailsVO=new CustomerBankDetailsVO();
				while (rs.next()) {
					
					//customerBankDetailsVO.setMicr(TTKCommon.checkNull(rs.getString("MICR_CODE")));
					customerBankDetailsVO.setIfsc(TTKCommon.checkNull(rs.getString("IFSC_CODE")));
				
				
									
				}//end of while(rs.next())
			}//end of if(rs != null)
			return customerBankDetailsVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null)	pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getBankAccountdetail()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getBankAccountdetail(long lngBankSeqID,long lngUserSeqID)
	
	
	
	
	
	/**
	 * This method returns the ArrayList, which contains the customerBankDetailsVO's which are populated from the database
	 * @param alSearchCriteria ArrayList which contains Search Criteria
	 * @return ArrayList of customerBankDetailsVO'S object's which contains the details of the Bank
	 * @exception throws TTKException
	 */
	public ArrayList getCustomerBankAccountReviewList(String flag)throws TTKException
	{ 
		CustomerBankDetailsVO customerBankDetailsVO=null;
		
	
		Collection<Object> alResultList = new ArrayList<Object>();
		//BankAccountVO bankAccountVO = null;
		Connection conn = null;
		OracleCallableStatement oCstmt = null;
		ResultSet rs = null;
		CallableStatement cStmtObject=null;
		// String strSuppressLink[]={};
		try
		{
			conn = ResourceManager.getConnection();
			
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHospReviewAccoountValidationList);
			
			
			cStmtObject.registerOutParameter(1,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);
			if(rs != null){
				while (rs.next())
					{
					customerBankDetailsVO=new CustomerBankDetailsVO();
					
					customerBankDetailsVO.setHospSeqID(new Long(TTKCommon.checkNull(rs.getString("HOSP_SEQ_ID"))));
					customerBankDetailsVO.setHospitalEmnalNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					customerBankDetailsVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					customerBankDetailsVO.setOfficename(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					customerBankDetailsVO.setHospitalEmpnalDesc(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
					customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));//for hospital accont number display
					customerBankDetailsVO.setBankState(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));//for hospital State number display
					customerBankDetailsVO.setBankname(TTKCommon.checkNull(rs.getString("Bank_Name")));//for Bank Name display
					customerBankDetailsVO.setiBanNo(TTKCommon.checkNull(rs.getString("IBAN_NO")));//for hospital IBAN_NO number display
					customerBankDetailsVO.setSwiftCode(TTKCommon.checkNull(rs.getString("Swift_Code")));//for hospital Swift_Code number display
					customerBankDetailsVO.setDhaId(TTKCommon.checkNull(rs.getString("HOSP_LICENC_NUMB")));//for hospital Swift_Code number display
					//customerBankDetailsVO.setSuppressLink(strSuppressLink);
					alResultList.add(customerBankDetailsVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getCustomerBankAccountReviewList()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getCustomerBankAccountReviewList()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getCustomerBankAccountReviewList()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getCustomerBankAccountReviewList()
	
	
	public ArrayList getCustomerBankAccountReviewList()throws TTKException
	{ 
		CustomerBankDetailsVO customerBankDetailsVO=null;
		
	
		Collection<Object> alResultList = new ArrayList<Object>();
		//BankAccountVO bankAccountVO = null;
		Connection conn = null;		
		ResultSet rs = null;
		CallableStatement cStmtObject=null;
		// String strSuppressLink[]={};
		try
		{
			conn = ResourceManager.getConnection();
			
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strHospReviewList);
			
			
			cStmtObject.registerOutParameter(1,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(1);
			if(rs != null){
				while (rs.next())
					{
					customerBankDetailsVO=new CustomerBankDetailsVO();
					
					customerBankDetailsVO.setHospSeqID(new Long(TTKCommon.checkNull(rs.getString("HOSP_SEQ_ID"))));
					customerBankDetailsVO.setHospitalEmnalNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					customerBankDetailsVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					customerBankDetailsVO.setOfficename(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					customerBankDetailsVO.setHospitalEmpnalDesc(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
					customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));//for hospital accont number display
					customerBankDetailsVO.setBankState(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));//for hospital State number display
					customerBankDetailsVO.setBankname(TTKCommon.checkNull(rs.getString("Bank_Name")));//for Bank Name display
					customerBankDetailsVO.setiBanNo(TTKCommon.checkNull(rs.getString("IBAN_NO")));//for hospital IBAN_NO number display
					customerBankDetailsVO.setSwiftCode(TTKCommon.checkNull(rs.getString("Swift_Code")));//for hospital Swift_Code number display
					customerBankDetailsVO.setDhaId(TTKCommon.checkNull(rs.getString("HOSP_LICENC_NUMB")));//for hospital Swift_Code number display
					//customerBankDetailsVO.setSuppressLink(strSuppressLink);
					alResultList.add(customerBankDetailsVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			
			return (ArrayList)alResultList;
		}// end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "bankaccount");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "bankaccount");
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
					log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getCustomerBankAccountReviewList()",sqlExp);
					throw new TTKException(sqlExp, "bankaccount");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getCustomerBankAccountReviewList()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getCustomerBankAccountReviewList()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "bankaccount");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getCustomerBankAccountReviewList()
	

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
	    				log.error("Error while closing the Second Resultset in HospitalDAOImpl getCityInfo()",sqlExp);
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
	    					log.error("Error while closing the First Resultset in HospitalDAOImpl getCityInfo()",sqlExp);
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
	    						log.error("Error while closing the Second Statement in HospitalDAOImpl getCityInfo()",sqlExp);
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
	    							log.error("Error while closing the First Statement in HospitalDAOImpl getCityInfo()",sqlExp);
	    							throw new TTKException(sqlExp, "hospital");
	    						}//end of catch (SQLException sqlExp)
	    						finally // Even if First Statement is not closed, control reaches here. Try closing the Connection now.
	    						{
	    							try{
	    								if(conn != null) conn.close();
	    							}//end of try
	    							catch (SQLException sqlExp)
	    							{
	    								log.error("Error while closing the Connection in HospitalDAOImpl getCityInfo()",sqlExp);
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
	}//end of getCityInfo(stateCode)
	    /**
	     * This method returns the HospitalDetailVO, which contains all the hospital details
	     * @param lHospSeqId Long object which contains the hospital seq id
	     * @return HospitalDetailVO object which contains all the hospital details
	     * @exception throws TTKException
	     */
	    public CustomerBankDetailsVO getHospitaBanklDetail(Long lHospSeqId) throws TTKException {
	    	Connection conn = null;
	    	PreparedStatement pStmt = null;
	    	ResultSet rs = null;
	        AddressVO bankAddressVO = null;
	        AddressVO podAddressVO = null;
	        CustomerBankDetailsVO customerBankDetailsVO	=	null;
	        try{
	            conn = ResourceManager.getConnection();

	            pStmt = conn.prepareStatement(strAccountDetail);
	            pStmt.setLong(1,lHospSeqId);
	            rs = pStmt.executeQuery();
	            if(rs != null){
	                while (rs.next()) {
	                	customerBankDetailsVO = new CustomerBankDetailsVO();
	                    bankAddressVO = new AddressVO();
	                    podAddressVO = new AddressVO();

	                    if(rs.getString("HOSP_BANK_SEQ_ID") != null)
	                    {
	                    	customerBankDetailsVO.setHospitalBankSeqId(new Long(rs.getLong("HOSP_BANK_SEQ_ID")));
	                    }//end of if(rs.getString("HOSP_BANK_SEQ_ID") != null)
	                    if(rs.getString("HOSP_SEQ_ID") != null)
	                    {
	                    	customerBankDetailsVO.setHospSeqID(new Long(rs.getLong("HOSP_SEQ_ID")));
	                    }//end of if(rs.getString("HOSP_SEQ_ID") != null)
	            
	                    customerBankDetailsVO.setBankname(TTKCommon.checkNull(rs.getString("BANK_NAME")));
	                    customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER123")));
	                    customerBankDetailsVO.setBankAccName(TTKCommon.checkNull(rs.getString("ACCOUNT_IN_NAME_OF")));
	                    customerBankDetailsVO.setBankBranch(TTKCommon.checkNull(rs.getString("BRANCH_NAME")));
	                    customerBankDetailsVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
	                    customerBankDetailsVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
	                    customerBankDetailsVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
	                    customerBankDetailsVO.setBankcity(TTKCommon.checkNull(rs.getString("City_Type_Id")));
	                    customerBankDetailsVO.setBankState(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
	                    customerBankDetailsVO.setPincode(TTKCommon.getInt((rs.getString("PIN_CODE"))));
	                    customerBankDetailsVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
	                   
	                    customerBankDetailsVO.setManagementName(TTKCommon.checkNull(rs.getString("MANAGEMENT_NAME")));
	                    customerBankDetailsVO.setIssueChqToHosp(TTKCommon.checkNull(rs.getString("ISSUE_CHEQUES_TYPE_ID")));
						customerBankDetailsVO.setHospAddresSeqID(TTKCommon.checkNull(rs.getString("ADDR_SEQ_ID")));
						customerBankDetailsVO.setHospitalBankSeqId(new Long(TTKCommon.checkNull(rs.getString("HOSP_BANK_SEQ_ID"))));
	                    //intX
	                    if(rs.getTimestamp("start_date") != null)
	                    {
	                    	customerBankDetailsVO.setStartDate(new java.util.Date(rs.getTimestamp("start_date").getTime()));
	                    }//end of if(rs.getTimestamp("start_date") != null)
	                    if(rs.getTimestamp("end_date") != null)
	                    {
	                    	customerBankDetailsVO.setEndDate(new java.util.Date(rs.getTimestamp("end_date").getTime()));
	                    }//end of if(rs.getTimestamp("end_date") != null)
	                    customerBankDetailsVO.setiBanNo(TTKCommon.checkNull(rs.getString("BANK_IFSC")));
	                    customerBankDetailsVO.setSwiftCode(TTKCommon.checkNull(rs.getString("BANK_MICR")));
	                   
	                    if(rs.getString("REVIEW_YN")!=null)
	                    	if(rs.getString("REVIEW_YN").equals("Y"))
	                    		customerBankDetailsVO.setReviewedYN("Y");
	                    	else
	                    		customerBankDetailsVO.setReviewedYN("N");
	                    
	                    customerBankDetailsVO.setHospitalName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
	                    
	                    /*if(rs.getTimestamp("REVIEWED_DATE") != null)
	                	{
	                    	customerBankDetailsVO.setReviewedDate(TTKCommon.checkNull(TTKCommon.getFormattedDate(new java.util.Date(rs.getTimestamp("REVIEWED_DATE").getTime()))));
	                	}
	                    customerBankDetailsVO.setReviewedBy(TTKCommon.checkNull(rs.getString("REVIEWED_BY")));*/
	                    
	                }// end of while
	            }//end of if(rs != null)
	            return customerBankDetailsVO;
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "account");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "account");
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
						log.error("Error while closing the Resultset in AccountDAOImpl getAccount()",sqlExp);
						throw new TTKException(sqlExp, "account");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt != null)	pStmt.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in AccountDAOImpl getAccount()",sqlExp);
							throw new TTKException(sqlExp, "account");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in AccountDAOImpl getAccount()",sqlExp);
								throw new TTKException(sqlExp, "account");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "account");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					pStmt = null;
					conn = null;
				}//end of finally
			}//end of finally
	    }//end of getHospitalDetail(Long lHospSeqId)
	    
	    
	    
	    /**
	     * This method adds or updates the account details
	     * The method also calls other methods on DAO to insert/update the account details to the database
	     * @param customerBankDetailsVO customerBankDetailsVO object which contains the account details to be added/updated
	     * @return int value, greater than zero indicates sucessfull execution of the task
	     * @exception throws TTKException
	     */
	    public int addUpdateReviewAccount(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
	    {
	      	Connection conn = null;
	    	CallableStatement cStmtObject=null;
	        int iResult =0;
	        HospitalAuditVO hospitalAuditVO = null;
	        
	        try{
	            conn = ResourceManager.getConnection();
	            conn.setAutoCommit(false);
	            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateAccountInfo);
                if(customerBankDetailsVO.getHospitalBankSeqId()!= null)
	            {
	                cStmtObject.setLong(1,customerBankDetailsVO.getHospitalBankSeqId().longValue());
	            }//end of if(customerBankDetailsVO.getHospBankSeqId() != null)
	            else
	            {
	                cStmtObject.setLong(1,0);
	            }//end of else
	            if(customerBankDetailsVO.getHospAddresSeqID() != null)
	            {
	                cStmtObject.setLong(2,Long.parseLong(customerBankDetailsVO.getHospAddresSeqID()));
	            }//end of if(customerBankDetailsVO.getBankAddressVO().getAddrSeqId() != null)
	            else
	            {
	                cStmtObject.setLong(2,0);
	            }//end of else
	           
	           
	            if(customerBankDetailsVO.getHospSeqID()!= null)
	            {
	                cStmtObject.setLong(3, customerBankDetailsVO.getHospSeqID().longValue());
	            }//end of if(customerBankDetailsVO.getHospSeqId()!= null)
	            else
	            {
	                cStmtObject.setLong(3,0);
	            }//end of else
	            cStmtObject.setString(4,customerBankDetailsVO.getBankname());
	            cStmtObject.setString(5, customerBankDetailsVO.getBankAccno());
	            cStmtObject.setString(6, customerBankDetailsVO.getBankAccName());
	            cStmtObject.setString(7, customerBankDetailsVO.getBankBranch());
	            cStmtObject.setString(8, customerBankDetailsVO.getAddress1());
	            cStmtObject.setString(9, customerBankDetailsVO.getAddress2());
	            cStmtObject.setString(10, customerBankDetailsVO.getAddress3());
	            cStmtObject.setString(11, customerBankDetailsVO.getBankcity());
	            cStmtObject.setString(12, customerBankDetailsVO.getBankState());
	            cStmtObject.setLong(13, customerBankDetailsVO.getPincode());
	            if(customerBankDetailsVO.getCountryCode()==null || customerBankDetailsVO.getCountryCode().equals("")){
	            	cStmtObject.setInt(14,0);
	            }
	            else{
	            cStmtObject.setInt(14, Integer.parseInt(customerBankDetailsVO.getCountryCode()));
	            }
	            cStmtObject.setString(15, customerBankDetailsVO.getManagementName());
	            cStmtObject.setString(16, customerBankDetailsVO.getIssueChqToHosp());
	            
	            cStmtObject.setLong(17, 0);
	            cStmtObject.setString(18,customerBankDetailsVO.getModReson());
	            if(customerBankDetailsVO.getRefDate() != null)
                {
                    cStmtObject.setTimestamp(19,new Timestamp(customerBankDetailsVO.getRefDate().getTime()));
                }//end of if(hospitalAuditVO.getRefDate() != null)
                else
                {
                    cStmtObject.setTimestamp(19, null);
                }//end of else
	            cStmtObject.setString(20,customerBankDetailsVO.getRefNmbr());
                cStmtObject.setString(21,customerBankDetailsVO.getRemarks());
                cStmtObject.setLong(22,customerBankDetailsVO.getAddedBy().longValue());
                if(!TTKCommon.checkNull(customerBankDetailsVO.getStartDate()).equals(""))
    			{
    			cStmtObject.setTimestamp(23, new Timestamp(customerBankDetailsVO.getStartDate().getTime()));
    			
    			}else
    			{
    				cStmtObject.setTimestamp(23, null);
    			}
    			if(!TTKCommon.checkNull(customerBankDetailsVO.getEndDate()).equals(""))
    			{
    			cStmtObject.setTimestamp(24, new Timestamp(customerBankDetailsVO.getEndDate().getTime()));
    			//cStmtObject.setDate(END_DATE, new java.sql.Date(accountDetailVO.getEndDate().getTime()));
    			}else
    			{
    				cStmtObject.setTimestamp(24, null);
    			}
    			cStmtObject.setString(25, customerBankDetailsVO.getiBanNo());
    			cStmtObject.setString(26, customerBankDetailsVO.getSwiftCode());
    			if("on".equals(customerBankDetailsVO.getReviewedYN()))
    				cStmtObject.setString(27, "Y");
    			else
    				cStmtObject.setString(27, "N");
    			
                cStmtObject.registerOutParameter(28,Types.INTEGER);
                cStmtObject.registerOutParameter(1, Types.BIGINT);
                cStmtObject.registerOutParameter(2, Types.BIGINT);
                cStmtObject.execute();
	            iResult = cStmtObject.getInt(28);
	            conn.commit();
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "account");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "account");
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
	        			log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl  addUpdateReviewAccount()",sqlExp);
	        			throw new TTKException(sqlExp, "account");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl  addUpdateReviewAccount()",sqlExp);
	        				throw new TTKException(sqlExp, "account");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "account");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
	        return iResult;
	    }//end of addUpdateReviewAccount(CustomerBankDetailsVO customerBankDetailsVO)
	    
	    
	    
	    
	    public int addUpdateRevisedReviewAccount(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
	    {
	    	Connection conn = null;
	    	CallableStatement cStmtObject=null;
	        int iResult =0;
	        HospitalAuditVO hospitalAuditVO = null;
	        
	        try{
	            conn = ResourceManager.getConnection();
	            conn.setAutoCommit(false);
	            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdateAccountInfo);
	            if(customerBankDetailsVO.getHospitalBankSeqId()!= null)
	            {
	                cStmtObject.setLong(1,customerBankDetailsVO.getHospitalBankSeqId().longValue());
	            }//end of if(customerBankDetailsVO.getHospBankSeqId() != null)
	            else
	            {
	                cStmtObject.setLong(1,0);
	            }//end of else
	            if(customerBankDetailsVO.getHospAddresSeqID() != null)
	            {
	                cStmtObject.setLong(2,(Long.parseLong(customerBankDetailsVO.getHospAddresSeqID())));
	            }//end of if(customerBankDetailsVO.getBankAddressVO().getAddrSeqId() != null)
	            else
	            {
	                cStmtObject.setLong(2,0);
	            }//end of else
	           
	           
	            if(customerBankDetailsVO.getHospSeqID()!= null)
	            {
	                cStmtObject.setLong(3, customerBankDetailsVO.getHospSeqID().longValue());
	            }//end of if(customerBankDetailsVO.getHospSeqId()!= null)
	            else
	            {
	                cStmtObject.setLong(3,0);
	            }//end of else
	            cStmtObject.setString(4,customerBankDetailsVO.getBankname());
	            cStmtObject.setString(5, customerBankDetailsVO.getBankAccno());
	            cStmtObject.setString(6, customerBankDetailsVO.getBankAccName());
	            cStmtObject.setString(7, customerBankDetailsVO.getBankBranch());
	            cStmtObject.setString(8, customerBankDetailsVO.getAddress1());
	            cStmtObject.setString(9, customerBankDetailsVO.getAddress2());
	            cStmtObject.setString(10, customerBankDetailsVO.getAddress3());
	            cStmtObject.setString(11, customerBankDetailsVO.getBankcity());
	            cStmtObject.setString(12, customerBankDetailsVO.getBankState());
	            cStmtObject.setLong(13, customerBankDetailsVO.getPincode());
	            cStmtObject.setInt(14,Integer.parseInt(customerBankDetailsVO.getCountryCode()));
	            cStmtObject.setString(15, customerBankDetailsVO.getManagementName());
	            cStmtObject.setString(16, customerBankDetailsVO.getIssueChqToHosp());
	            
	            cStmtObject.setLong(17, 0);
	            cStmtObject.setString(18,customerBankDetailsVO.getModReson());
	            if(customerBankDetailsVO.getRefDate() != null)
                {
                    cStmtObject.setTimestamp(19,new Timestamp(customerBankDetailsVO.getRefDate().getTime()));
                }//end of if(hospitalAuditVO.getRefDate() != null)
                else
                {
                    cStmtObject.setTimestamp(19, null);
                }//end of else
	            cStmtObject.setString(20,customerBankDetailsVO.getRefNmbr());
                cStmtObject.setString(21,customerBankDetailsVO.getRemarks());
                cStmtObject.setLong(22,customerBankDetailsVO.getAddedBy().longValue());
                if(!TTKCommon.checkNull(customerBankDetailsVO.getStartDate()).equals(""))
    			{
    			cStmtObject.setTimestamp(23, new Timestamp(customerBankDetailsVO.getStartDate().getTime()));
    			
    			}else
    			{
    				cStmtObject.setString(23, null);
    			}
    			if(!TTKCommon.checkNull(customerBankDetailsVO.getEndDate()).equals(""))
    			{
    			cStmtObject.setTimestamp(24, new Timestamp(customerBankDetailsVO.getEndDate().getTime()));
    			//cStmtObject.setDate(END_DATE, new java.sql.Date(accountDetailVO.getEndDate().getTime()));
    			}else
    			{
    				cStmtObject.setTimestamp(24, null);
    			}
    			cStmtObject.setString(25, customerBankDetailsVO.getiBanNo());
    			cStmtObject.setString(26, customerBankDetailsVO.getSwiftCode());
    			if("on".equals(customerBankDetailsVO.getReviewedYN()))
    				cStmtObject.setString(27, "Y");
    			else
    				cStmtObject.setString(27, "N");
    			
                cStmtObject.registerOutParameter(28,Types.INTEGER);
                cStmtObject.registerOutParameter(1, Types.BIGINT);
                cStmtObject.registerOutParameter(2, Types.BIGINT);
                cStmtObject.execute();
	            iResult = cStmtObject.getInt(28);
	            conn.commit();
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "account");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "account");
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
	        			log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl addUpdateRevisedReviewAccount()",sqlExp);
	        			throw new TTKException(sqlExp, "account");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl addUpdateRevisedReviewAccount()",sqlExp);
	        				throw new TTKException(sqlExp, "account");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "account");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
	        return iResult;
	    }//end of addUpdateReviewAccount(CustomerBankDetailsVO customerBankDetailsVO)
	    
	   
	    /**
	     * This method returns the HospitalDetailVO, which contains all the hospital details
	     * @param lHospSeqId Long object which contains the hospital seq id
	     * @return HospitalDetailVO object which contains all the hospital details
	     * @exception throws TTKException
	     */
	    public ArrayList<String> getFilesUploadedAtEmpnl(Long lHospSeqId) throws TTKException {
	    	Connection conn = null;
	    	PreparedStatement pStmt = null;
	    	ResultSet rs = null;
	    	ArrayList<String> alFilesUploaded	=	null;
	        try{
	            conn = ResourceManager.getConnection();
	            pStmt = conn.prepareStatement(strUploadedFiles);
	            pStmt.setLong(1,lHospSeqId);
	            rs = pStmt.executeQuery();
	            if(rs != null){
	            	alFilesUploaded	=	new ArrayList<String>();
	                while (rs.next()) {
	                	alFilesUploaded.add(rs.getString(1));
	                }// end of while
	            }//end of if(rs != null)
	            return alFilesUploaded;
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "account");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "account");
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
						log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getFilesUploadedAtEmpnl()",sqlExp);
						throw new TTKException(sqlExp, "account");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt != null)	pStmt.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getFilesUploadedAtEmpnl()",sqlExp);
							throw new TTKException(sqlExp, "account");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getFilesUploadedAtEmpnl()",sqlExp);
								throw new TTKException(sqlExp, "account");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "account");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					pStmt = null;
					conn = null;
				}//end of finally
			}//end of finally
	    }//end of getFilesUploadedAtEmpnl(Long lHospSeqId)
	    
	    
	    
	    
	    public String getBankCode(String bankName) throws TTKException {
	    	Connection conn = null;
	    	PreparedStatement pStmt = null;
	    	ResultSet rs = null;
	    	String bankCode=	null;
	        try{
	            conn = ResourceManager.getConnection();
	            pStmt = conn.prepareStatement(strBankCode);
	            pStmt.setString(1,bankName);
	            rs = pStmt.executeQuery();
	            if(rs != null){
	            	bankCode	=	new String();
	                if (rs.next()) {
	                	bankCode	=	rs.getString(1);
	                }// end of while
	            }//end of if(rs != null)
	            return bankCode;
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "account");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "account");
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
						log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getFilesUploadedAtEmpnl()",sqlExp);
						throw new TTKException(sqlExp, "account");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt != null)	pStmt.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getFilesUploadedAtEmpnl()",sqlExp);
							throw new TTKException(sqlExp, "account");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getFilesUploadedAtEmpnl()",sqlExp);
								throw new TTKException(sqlExp, "account");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "account");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					pStmt = null;
					conn = null;
				}//end of finally
			}//end of finally
	    }//end of getBankCode(String bankName)


		public int saveMouUploads(CustomerBankDetailsVO customerBankDetailsVO,ArrayList<Object> alFileAUploadList, Long userSeqId,String hosp_seq_id, String origFileName,InputStream inputStream, int formFileSize) throws TTKException {
			// TODO Auto-generated method stub
			int iResult = 0;
	    	Connection conn = null;
	    	CallableStatement cStmtObject=null;
	    	try{
	    		conn = ResourceManager.getConnection();
	    		//conn = ResourceManager.getTestConnection();
	            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveMouFils);

	            cStmtObject.setLong(1,0);
	           /* if(mouDocumentVO.getMouDocSeqID() != null){
	            	cStmtObject.setLong(1,mouDocumentVO.getMouDocSeqID());
	            }//end of if(mouDocumentVO.getMouDocSeqID() != null)
	            else{
	            	cStmtObject.setLong(1,0);
	            }//end of else
	*/          if(hosp_seq_id == null || hosp_seq_id.equals(""))
		        cStmtObject.setLong(2,0);
	            else
	            cStmtObject.setLong(2, new Long(hosp_seq_id));//HosSeqID
	            cStmtObject.setString(3,"FIND");//DESC
	            cStmtObject.setString(4,customerBankDetailsVO.getDocName());//Doc Path
	            cStmtObject.setString(5,origFileName);//Doc Path	        
	            cStmtObject.setLong(6,userSeqId);
	            cStmtObject.setBinaryStream(7, inputStream,formFileSize);//FILE INPUT STREAM
	            cStmtObject.registerOutParameter(8,Types.INTEGER);//ROW_PROCESSED
	            
	            cStmtObject.execute();
	            conn.commit();
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
	        			log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl saveMouUploads()",sqlExp);
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
	        				log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl saveMouUploads()",sqlExp);
	        				throw new TTKException(sqlExp, "bankaccount");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "bankaccount");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
	    	return iResult;
	            
		}


		public ArrayList<CustomerBankDetailsVO> getMouUploadsList(String hosp_seq_id) throws TTKException
		{
			// TODO Auto-generated method stub
			int iResult = 0;
	    	Connection conn = null;
	    	PreparedStatement pStmt1 = null;
	    	ArrayList<CustomerBankDetailsVO>alMouList	=	new ArrayList();
	    	ResultSet rs1 = null;
	    	CustomerBankDetailsVO customerBankDetailsVO	=	null;
	    	try{		    	
	    		conn = ResourceManager.getConnection();
		    	pStmt1=conn.prepareStatement(strGetFinanefiles);
		    	if(hosp_seq_id == null || hosp_seq_id.equals(""))
		    		pStmt1.setLong(1,0);
		    	else
		    	pStmt1.setLong(1, new Long(hosp_seq_id));
				rs1= pStmt1.executeQuery();
				
				if(rs1 != null){
					while(rs1.next())
					{			
						customerBankDetailsVO = new CustomerBankDetailsVO();
						customerBankDetailsVO.setDocPath(TTKCommon.checkNull(rs1.getString("DOCS_PATH")));
						customerBankDetailsVO.setFileName(TTKCommon.checkNull(rs1.getString("FILE_NAME")));
                        alMouList.add(customerBankDetailsVO);
					}//end of while(rs.next())
				}//end of if(rs != null)
		    	//return (ArrayList<Object>)alMouList;
		    
            }//end of try
	    	catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "bankaccount");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "bankaccount");
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
						log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getMouUploadsList()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt1 != null)	pStmt1.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getMouUploadsList()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getMouUploadsList()",sqlExp);
								throw new TTKException(sqlExp, "bankaccount");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "bankaccount");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs1 = null;
					pStmt1 = null;
					conn = null;
				}//end of finally
			}//end of finally
	    	 return alMouList;
		}
		
		
		/**
		 * This method returns the ArrayList, which contains the customerBankDetailsVO's which are populated from the database
		 * @param alSearchCriteria ArrayList which contains Search Criteria
		 * @return ArrayList of customerBankDetailsVO'S object's which contains the details of the Bank
		 * @exception throws TTKException
		 */
		public ArrayList getPartnerBankAccountReviewList()throws TTKException
		{ 
			CustomerBankDetailsVO customerBankDetailsVO=null;
			
		
			Collection<Object> alResultList = new ArrayList<Object>();
			//BankAccountVO bankAccountVO = null;
			Connection conn = null;
		//	OracleCallableStatement oCstmt = null;
			ResultSet rs = null;
			CallableStatement cStmtObject=null;
			// String strSuppressLink[]={};
			try
			{
				conn = ResourceManager.getConnection();
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strPtnrReviewList);
				cStmtObject.registerOutParameter(1,Types.OTHER);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(1);
				if(rs != null){
					while (rs.next())
						{
						customerBankDetailsVO=new CustomerBankDetailsVO();
						
						customerBankDetailsVO.setPtnrSeqID(new Long(TTKCommon.checkNull(rs.getString("PTNR_SEQ_ID"))));
						customerBankDetailsVO.setPartnerEmnalNumber(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
						customerBankDetailsVO.setPartnerName(TTKCommon.checkNull(rs.getString("PARTNER_NAME")));
						customerBankDetailsVO.setOfficename(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
						customerBankDetailsVO.setPartnerEmpnalDesc(TTKCommon.checkNull(rs.getString("EMPANEL_DESCRIPTION")));
						customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));//for hospital accont number display
						customerBankDetailsVO.setBankState(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));//for hospital State number display
						customerBankDetailsVO.setBankname(TTKCommon.checkNull(rs.getString("Bank_Name")));//for Bank Name display
						customerBankDetailsVO.setiBanNo(TTKCommon.checkNull(rs.getString("IBAN_NO")));//for hospital IBAN_NO number display
						customerBankDetailsVO.setSwiftCode(TTKCommon.checkNull(rs.getString("Swift_Code")));//for hospital Swift_Code number display
						customerBankDetailsVO.setDhaId(TTKCommon.checkNull(rs.getString("PTNR_LICENC_NUMB")));//for hospital Swift_Code number display
						//customerBankDetailsVO.setSuppressLink(strSuppressLink);
						alResultList.add(customerBankDetailsVO);
					}//end of while(rs.next())
				}//end of if(rs != null)
				
				return (ArrayList)alResultList;
			}// end of try
			catch (SQLException sqlExp)
			{
				throw new TTKException(sqlExp, "bankaccount");
			}//end of catch (SQLException sqlExp)
			catch (Exception exp)
			{
				throw new TTKException(exp, "bankaccount");
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
						log.error("Error while closing the Resultset in CustomerBankDetailsDAOImpl getPartnerBankAccountReviewList()",sqlExp);
						throw new TTKException(sqlExp, "bankaccount");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (cStmtObject != null)	cStmtObject.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in CustomerBankDetailsDAOImpl getPartnerBankAccountReviewList()",sqlExp);
							throw new TTKException(sqlExp, "bankaccount");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in CustomerBankDetailsDAOImpl getPartnerBankAccountReviewList()",sqlExp);
								throw new TTKException(sqlExp, "bankaccount");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "bankaccount");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					cStmtObject = null;
					conn = null;
				}//end of finally
			}//end of finally
		}//end of getCustomerBankAccountReviewList()
		
		  /**
	     * This method returns the HospitalDetailVO, which contains all the hospital details
	     * @param lHospSeqId Long object which contains the hospital seq id
	     * @return HospitalDetailVO object which contains all the hospital details
	     * @exception throws TTKException
	     */
	    public CustomerBankDetailsVO getPartnerBankDetail(Long lPtnrSeqId) throws TTKException {
	    	Connection conn = null;
	    	PreparedStatement pStmt = null;
	    	ResultSet rs = null;
	        AddressVO bankAddressVO = null;
	        AddressVO podAddressVO = null;
	        CustomerBankDetailsVO customerBankDetailsVO	=	null;
	        try{
	            conn = ResourceManager.getConnection();

	            pStmt = conn.prepareStatement(strPartnerAccountDetail);
	            pStmt.setLong(1,lPtnrSeqId);
	            rs = pStmt.executeQuery();
	            if(rs != null){
	                while (rs.next()) {
	                	customerBankDetailsVO = new CustomerBankDetailsVO();
	                    bankAddressVO = new AddressVO();
	                    podAddressVO = new AddressVO();

	                    if(rs.getString("PTNR_BANK_SEQ_ID") != null)
	                    {
	                    	customerBankDetailsVO.setPartnerBankSeqId(new Long(rs.getLong("PTNR_BANK_SEQ_ID")));
	                    }//end of if(rs.getString("PTNR_BANK_SEQ_ID") != null)
	                    if(rs.getString("PTNR_SEQ_ID") != null)
	                    {
	                    	customerBankDetailsVO.setPtnrSeqID(new Long(rs.getLong("PTNR_SEQ_ID")));
	                    }//end of if(rs.getString("HOSP_SEQ_ID") != null)
	                    customerBankDetailsVO.setBankname(TTKCommon.checkNull(rs.getString("BANK_NAME")));
	                    customerBankDetailsVO.setBankAccno(TTKCommon.checkNull(rs.getString("ACCOUNT_NUMBER")));
	                    customerBankDetailsVO.setBankAccName(TTKCommon.checkNull(rs.getString("ACCOUNT_IN_NAME_OF")));
	                    customerBankDetailsVO.setBankBranch(TTKCommon.checkNull(rs.getString("BRANCH_NAME")));
	                    customerBankDetailsVO.setAddress1(TTKCommon.checkNull(rs.getString("ADDRESS_1")));
	                    customerBankDetailsVO.setAddress2(TTKCommon.checkNull(rs.getString("ADDRESS_2")));
	                    customerBankDetailsVO.setAddress3(TTKCommon.checkNull(rs.getString("ADDRESS_3")));
	                    customerBankDetailsVO.setBankcity(TTKCommon.checkNull(rs.getString("City_Type_Id")));
	                    customerBankDetailsVO.setBankState(TTKCommon.checkNull(rs.getString("STATE_TYPE_ID")));
	                    customerBankDetailsVO.setPincode(TTKCommon.getInt((rs.getString("PIN_CODE"))));
	                    customerBankDetailsVO.setCountryCode(TTKCommon.checkNull(rs.getString("COUNTRY_ID")));
	             /*       customerBankDetailsVO.setManagementName(TTKCommon.checkNull(rs.getString("MANAGEMENT_NAME")));
	                    customerBankDetailsVO.setIssueChqToHosp(TTKCommon.checkNull(rs.getString("ISSUE_CHEQUES_TYPE_ID")));*/
						customerBankDetailsVO.setPtnrAddresSeqID(TTKCommon.checkNull(rs.getString("ADDR_SEQ_ID")));
						customerBankDetailsVO.setPartnerBankSeqId(new Long(TTKCommon.checkNull(rs.getString("PTNR_BANK_SEQ_ID"))));
	                    //intX
	                    if(rs.getTimestamp("start_date") != null)
	                    {
	                    	customerBankDetailsVO.setStartDate(new java.util.Date(rs.getTimestamp("start_date").getTime()));
	                    }//end of if(rs.getTimestamp("start_date") != null)
	                    if(rs.getTimestamp("end_date") != null)
	                    {
	                    	customerBankDetailsVO.setEndDate(new java.util.Date(rs.getTimestamp("end_date").getTime()));
	                    }//end of if(rs.getTimestamp("end_date") != null)
	                    customerBankDetailsVO.setiBanNo(TTKCommon.checkNull(rs.getString("BANK_IFSC")));
	                    customerBankDetailsVO.setSwiftCode(TTKCommon.checkNull(rs.getString("BANK_MICR")));
	                   
	                    if(rs.getString("REVIEW_YN")!=null)
	                    	if(rs.getString("REVIEW_YN").equals("Y"))
	                    		customerBankDetailsVO.setReviewedYN("Y");
	                    	else
	                    		customerBankDetailsVO.setReviewedYN("N");
	                    
	                    customerBankDetailsVO.setPartnerName(TTKCommon.checkNull(rs.getString("PARTNER_NAME")));
	                    
	                    /*if(rs.getTimestamp("REVIEWED_DATE") != null)
	                	{
	                    	customerBankDetailsVO.setReviewedDate(TTKCommon.checkNull(TTKCommon.getFormattedDate(new java.util.Date(rs.getTimestamp("REVIEWED_DATE").getTime()))));
	                	}
	                    customerBankDetailsVO.setReviewedBy(TTKCommon.checkNull(rs.getString("REVIEWED_BY")));*/
	                    
	                }// end of while
	            }//end of if(rs != null)
	            return customerBankDetailsVO;
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "account");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "account");
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
						log.error("Error while closing the Resultset in AccountDAOImpl getAccount()",sqlExp);
						throw new TTKException(sqlExp, "account");
					}//end of catch (SQLException sqlExp)
					finally // Even if result set is not closed, control reaches here. Try closing the statement now.
					{
						try
						{
							if (pStmt != null)	pStmt.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Statement in AccountDAOImpl getAccount()",sqlExp);
							throw new TTKException(sqlExp, "account");
						}//end of catch (SQLException sqlExp)
						finally // Even if statement is not closed, control reaches here. Try closing the connection now.
						{
							try
							{
								if(conn != null) conn.close();
							}//end of try
							catch (SQLException sqlExp)
							{
								log.error("Error while closing the Connection in AccountDAOImpl getAccount()",sqlExp);
								throw new TTKException(sqlExp, "account");
							}//end of catch (SQLException sqlExp)
						}//end of finally Connection Close
					}//end of finally Statement Close
				}//end of try
				catch (TTKException exp)
				{
					throw new TTKException(exp, "account");
				}//end of catch (TTKException exp)
				finally // Control will reach here in anycase set null to the objects 
				{
					rs = null;
					pStmt = null;
					conn = null;
				}//end of finally
			}//end of finally
	    }//end of getPartnerDetail(Long lPtnrSeqId)
	    
	    
	    public int addUpdatePartnerReviewAccount(CustomerBankDetailsVO customerBankDetailsVO) throws TTKException
	    {
	    	Connection conn = null;
	    	CallableStatement cStmtObject=null;
	        int iResult =0;
	        HospitalAuditVO hospitalAuditVO = null;
	        
	        try{
	            conn = ResourceManager.getConnection();
	            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strAddUpdatePartnerAccountInfo);
	            
	            if(customerBankDetailsVO.getPartnerBankSeqId()!= null)
	            {
	                cStmtObject.setLong(1,customerBankDetailsVO.getPartnerBankSeqId().longValue());
	            }//end of if(customerBankDetailsVO.getHospBankSeqId() != null)
	            else
	            {
	                cStmtObject.setLong(1,0);
	            }//end of else
	            if(customerBankDetailsVO.getPtnrAddresSeqID() != null)
	            {
	                cStmtObject.setLong(2,Long.parseLong(customerBankDetailsVO.getPtnrAddresSeqID()));
	            }//end of if(customerBankDetailsVO.getBankAddressVO().getAddrSeqId() != null)
	            else
	            {
	                cStmtObject.setLong(2,0);
	            }//end of else
	           
	           
	            if(customerBankDetailsVO.getPtnrSeqID()!= null)
	            {
	                cStmtObject.setLong(3, customerBankDetailsVO.getPtnrSeqID().longValue());
	            }//end of if(customerBankDetailsVO.getHospSeqId()!= null)
	            else
	            {
	                cStmtObject.setLong(3,0);
	            }//end of else
	            cStmtObject.setString(4,customerBankDetailsVO.getBankname());
	            cStmtObject.setString(5, customerBankDetailsVO.getBankAccno());
	            cStmtObject.setString(6, customerBankDetailsVO.getBankAccName());
	            cStmtObject.setString(7, customerBankDetailsVO.getBankBranch());
	            cStmtObject.setString(8, customerBankDetailsVO.getAddress1());
	            cStmtObject.setString(9, customerBankDetailsVO.getAddress2());
	            cStmtObject.setString(10, customerBankDetailsVO.getAddress3());
	            cStmtObject.setString(11, customerBankDetailsVO.getBankcity());
	            cStmtObject.setString(12, customerBankDetailsVO.getBankState());
	            cStmtObject.setLong(13, customerBankDetailsVO.getPincode());
	            cStmtObject.setInt(14,customerBankDetailsVO.getCountryCode()== null || customerBankDetailsVO.getCountryCode()=="" ? 0:Integer.parseInt(customerBankDetailsVO.getCountryCode()));
	          /*  cStmtObject.setString(15, customerBankDetailsVO.getManagementName());
	            cStmtObject.setString(16, customerBankDetailsVO.getIssueChqToHosp());*/
	            
	            cStmtObject.setLong(15, 0);
	            cStmtObject.setString(16,customerBankDetailsVO.getModReson());
	            if(customerBankDetailsVO.getRefDate() != null)
                {
                    cStmtObject.setTimestamp(17,new Timestamp(customerBankDetailsVO.getRefDate().getTime()));
                }//end of if(hospitalAuditVO.getRefDate() != null)
                else
                {
                    cStmtObject.setTimestamp(17, null);
                }//end of else
	            cStmtObject.setString(18,customerBankDetailsVO.getRefNmbr());
                cStmtObject.setString(19,customerBankDetailsVO.getRemarks());
                cStmtObject.setLong(20,customerBankDetailsVO.getAddedBy().longValue());
                if(!TTKCommon.checkNull(customerBankDetailsVO.getStartDate()).equals(""))
    			{
    			cStmtObject.setTimestamp(21, new Timestamp(customerBankDetailsVO.getStartDate().getTime()));
    			
    			}else
    			{
    				cStmtObject.setTimestamp(21, null);
    			}
    			if(!TTKCommon.checkNull(customerBankDetailsVO.getEndDate()).equals(""))
    			{
    			cStmtObject.setTimestamp(22, new Timestamp(customerBankDetailsVO.getEndDate().getTime()));
    			//cStmtObject.setDate(END_DATE, new java.sql.Date(accountDetailVO.getEndDate().getTime()));
    			}else
    			{
    				cStmtObject.setTimestamp(22, null);
    			}
    			cStmtObject.setString(23, customerBankDetailsVO.getiBanNo());
    			cStmtObject.setString(24, customerBankDetailsVO.getSwiftCode());
    			if("on".equals(customerBankDetailsVO.getReviewedYN()))
    				cStmtObject.setString(25, "Y");
    			else
    				cStmtObject.setString(25, "N");
    			
                cStmtObject.registerOutParameter(26,Types.INTEGER);
                cStmtObject.registerOutParameter(1, Types.BIGINT);
                cStmtObject.registerOutParameter(2, Types.BIGINT);
                cStmtObject.execute();
	            iResult = cStmtObject.getInt(26);
	            conn.commit();
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "account");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "account");
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
	        			log.error("Error while closing the Statement in AccountDAOImpl addUpdateAccount()",sqlExp);
	        			throw new TTKException(sqlExp, "account");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in AccountDAOImpl addUpdateAccount()",sqlExp);
	        				throw new TTKException(sqlExp, "account");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "account");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
	        return iResult;
	    }//end of addUpdateReviewAccount(CustomerBankDetailsVO customerBankDetailsVO)
	    
	    
	 
	    
}
