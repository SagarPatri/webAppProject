/**
 * @ (#) InsuranceDAOImpl Oct 21, 2005
 * Project      : TTK HealthCare Services
 * File         : InsuranceCompanyDAOImpl.java
 * Author       : Ravindra
 * Company      : Span Systems Corporation
 * Date Created : Oct 21, 2005
 *
 * @author       :  Ravindra

 * Modified by   :  RamaKrishna K M
 * Modified date :  Dec 13, 2005
 * Reason        :  Modification in log statements
 */

package com.ttk.dao.impl.empanelment;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.InsuranceDetailVO;
import com.ttk.dto.empanelment.InsuranceVO;
//added for CR KOC Mail-SMS Notification for Cigna
import com.ttk.dto.empanelment.MailNotificationVO;
import com.ttk.dto.insurancepricing.AgeMasterVO;
import com.ttk.dto.insurancepricing.AreaOfCoverVO;
import com.ttk.dto.insurancepricing.InsPricingVO;
import com.ttk.dto.insurancepricing.MasterFactorVO;
import com.ttk.dto.insurancepricing.PolicyConfigVO;
import com.ttk.dto.insurancepricing.SwPolicyConfigVO;
import com.ttk.dto.insurancepricing.SwPricingSummaryVO;

public class InsuranceDAOImpl  implements BaseDAO, Serializable{

	private static Logger log = Logger.getLogger(InsuranceDAOImpl.class);
	
	private static final int  INS_SEQ_ID = 1 ;
	private static final int  INS_ADDR_SEQ_ID = 2 ;
	private static final int  TPA_OFFICE_SEQ_ID = 3 ;
	private static final int  INS_SECTOR_GENERAL_TYPE_ID = 4;
	private static final int  INS_OFFICE_GENERAL_TYPE_ID = 5;
	private static final int  INS_COMP_NAME = 6 ;
	private static final int  ABBREVATION_CODE = 7;
	private static final int INS_COMP_CODE_NUMBER = 8;
	private static final int EMPANELED_DATE = 9;
	private static final int DISB_TYPE_ID = 10;
	private static final int FREQUENCY_TYPE_ID = 11;
	private static final int FLOAT_REPLENISHMENT_PERIOD = 12;
	private static final int ACTIVE_YN = 13;
	private static final int INS_PARENT_SEQ_ID = 14;
	private static final int ADDRESS_1 = 15;
	private static final int ADDRESS_2 = 16;
	private static final int ADDRESS_3 = 17;
	private static final int CITY_TYPE_ID = 18;
	private static final int STATE_TYPE_ID = 19;
	private static final int PIN_CODE = 20;
	private static final int COUNTRY_ID = 21;
	private static final int LANDMARKS = 22;
	private static final int INS_OFF_SEQ_ID = 23;
	private static final int VALID_FROM = 24;
	private static final int VALID_TO = 25;
	private static final int USER_SEQ_ID = 26;

	// Shortfall CR KOC1179
	private static final int INS_COMPANY_EMAILID = 27;

	// added for CR KOC Mail-SMS Notification for Cigna.

	private static final int NOTIFY_TYPE_ID = 28;
	private static final int NOTIFY_EMAIL_ID = 29;
	private static final int CC_EMAIL_ID = 30;
	// ended
	private static final int user_group = 31;// KOC Cigna_insurance_resriction

	private static final int AUTHORITY_STANANDARD = 32;
	private static final int ISD_CODE = 33;
	private static final int STD_CODE = 34;
	private static final int PHONE1 = 35;
	private static final int PHONE2 = 36;
	private static final int INSCATEGORY = 37;// test nag
	private static final int ROW_PROCESSED = 38;

	private static final int INS_COMP_AUTHORITY_CODE_NUMBER = 39;
	private static final int INS_VAT_TRN = 40;
	private static final int INS_VAT = 41;
	private static final int PRODUCT_IDS_LIST = 42;
	private static final int POLICY_GAP_DAYS = 43;
	private static final int POLICY_GRACE_DAYS = 44;

	// private static final String
	// strInsuranceList="SELECT * FROM (SELECT INS_SEQ_ID, INS_COMP_NAME, EMPANELED_DATE, ACTIVE_YN, INS_COMP_CODE_NUMBER, DENSE_RANK() OVER (ORDER BY #, ROWNUM) Q FROM TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' "
	// ;
	private static final String strInsuranceList = "SELECT * FROM (SELECT INS_SEQ_ID, INS_COMP_NAME, EMPANELED_DATE, ACTIVE_YN, INS_COMP_CODE_NUMBER,PAYER_AUTHORITY,DENSE_RANK() OVER (ORDER BY #) Q FROM app.TPA_INS_INFO WHERE INS_OFFICE_GENERAL_TYPE_ID = 'IHO' ";
	// private static final String
	// strInsurancelDetail="SELECT INS_OFFICE_GENERAL_TYPE_ID,TPA_INS_INFO.INS_PARENT_SEQ_ID,TPA_INS_INFO.TPA_OFFICE_SEQ_ID,A.INS_COMP_NAME,ACTIVE_YN,INS_COMP_CODE_NUMBER,A.ABBREVATION_CODE,A.INS_SECTOR_GENERAL_TYPE_ID,EMPANELED_DATE,DISB_TYPE_ID,FLOAT_REPLENISHMENT_PERIOD ,FREQUENCY_TYPE_ID,ADDR_SEQ_ID,ADDRESS_1,ADDRESS_2,ADDRESS_3,STATE_TYPE_ID,CITY_TYPE_ID, PIN_CODE,EMAIL_ID, COUNTRY_ID,A.DESCRIPTION, TPA_INS_INFO.NOTIFY_TYPE_ID, TPA_INS_INFO.TO_MAIL_ID, TPA_INS_INFO.CC_MAIL_ID,TPA_INS_INFO.USER_GROUP,TPA_INS_INFO.PAYER_AUTHORITY,TPA_ADDRESS.ISD_CODE,TPA_ADDRESS.STD_CODE,A.PI_NONPI_INSURER as PI_NONPI_INSURER,TPA_ADDRESS.OFFICE_PHONE1,TPA_ADDRESS.OFFICE_PHONE2,TPA_INS_INFO.INS_COMP_COMMON_AUTH_CODE FROM TPA_INS_INFO, TPA_ADDRESS,(SELECT INS_SEQ_ID,INS_COMP_NAME, ABBREVATION_CODE, INS_SECTOR_GENERAL_TYPE_ID,DESCRIPTION,TPA_INS_INFO.PI_NONPI_INSURER FROM TPA_INS_INFO,TPA_GENERAL_CODE  WHERE INS_PARENT_SEQ_ID IS NULL AND TPA_INS_INFO.INS_SECTOR_GENERAL_TYPE_ID = GENERAL_TYPE_ID AND INS_SEQ_ID = ?)A WHERE TPA_INS_INFO.INS_SEQ_ID = TPA_ADDRESS.INS_SEQ_ID AND TPA_INS_INFO.INS_SEQ_ID = ?";
	// old
	private static final String strInsurancelDetail ="SELECT INS_OFFICE_GENERAL_TYPE_ID,TPA_INS_INFO.INS_PARENT_SEQ_ID,TPA_INS_INFO.TPA_OFFICE_SEQ_ID,A.INS_COMP_NAME,ACTIVE_YN,INS_COMP_CODE_NUMBER,A.ABBREVATION_CODE,A.INS_SECTOR_GENERAL_TYPE_ID,EMPANELED_DATE,DISB_TYPE_ID,FLOAT_REPLENISHMENT_PERIOD ,FREQUENCY_TYPE_ID,ADDR_SEQ_ID,ADDRESS_1,ADDRESS_2,ADDRESS_3,STATE_TYPE_ID,CITY_TYPE_ID,PIN_CODE,EMAIL_ID,COUNTRY_ID,DESCRIPTION,TPA_INS_INFO.Vat_Trn_Code,TPA_INS_INFO.NOTIFY_TYPE_ID,TPA_INS_INFO.TO_MAIL_ID,TPA_INS_INFO.CC_MAIL_ID,TPA_INS_INFO.USER_GROUP,TPA_INS_INFO.PAYER_AUTHORITY,TPA_ADDRESS.ISD_CODE,TPA_ADDRESS.STD_CODE,A.PI_NONPI_INSURER as PI_NONPI_INSURER,TPA_ADDRESS.OFFICE_PHONE1,TPA_ADDRESS.OFFICE_PHONE2,TPA_INS_INFO.INS_COMP_COMMON_AUTH_CODE,TPA_INS_INFO.Vat_Trn_Code as Vat_Trn_Code,TPA_INS_INFO.Vat_Percent  as Vat_Percent,allowed_gap, to_char(gap_date,'DD/MM/YYYY') gap_date, grace_period, to_char(grace_date,'DD/MM/YYYY') grace_date FROM APP.TPA_INS_INFO, APP.TPA_ADDRESS,(SELECT a.INS_SEQ_ID,a.INS_COMP_NAME, a.ABBREVATION_CODE, a.INS_SECTOR_GENERAL_TYPE_ID,DESCRIPTION,a.PI_NONPI_INSURER FROM APP.TPA_INS_INFO a,APP.TPA_GENERAL_CODE WHERE INS_PARENT_SEQ_ID IS NULL AND a.INS_SECTOR_GENERAL_TYPE_ID =GENERAL_TYPE_ID AND a.INS_SEQ_ID =? ) A WHERE TPA_INS_INFO.INS_SEQ_ID = TPA_ADDRESS.INS_SEQ_ID AND TPA_INS_INFO.INS_SEQ_ID =?";
	private static final String strAddUpdateInsuranceInfo = "{CALL INSCOMP_EMPANEL_PKG_PR_INS_INFO_SAVE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strDeleteInsuranceInfo = "{CALL INSCOMP_EMPANEL_PKG_PR_INS_INFO_DELETE(?,?)}";
	// private static final String strGetCompanyDetails =
	// "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER (ORDER BY INS_COMP_NAME,ROWNUM) Q FROM(SELECT INS_SEQ_ID, TPA_OFFICE_SEQ_ID,B.DESCRIPTION, INS_PARENT_SEQ_ID,A.INS_SECTOR_GENERAL_TYPE_ID,A.ABBREVATION_CODE, A.INS_COMP_NAME,CASE WHEN A.INS_OFFICE_GENERAL_TYPE_ID != 'IHO' THEN A.INS_COMP_NAME ||'-'|| A.INS_COMP_CODE_NUMBER ELSE A.INS_COMP_NAME END COMPANY,A.INS_COMP_CODE_NUMBER, INS_OFFICE_GENERAL_TYPE_ID FROM (SELECT  INS_SEQ_ID, TPA_OFFICE_SEQ_ID, INS_PARENT_SEQ_ID,INS_SECTOR_GENERAL_TYPE_ID,INS_COMP_CODE_NUMBER,ABBREVATION_CODE, INS_OFFICE_GENERAL_TYPE_ID ,INS_COMP_NAME FROM TPA_INS_INFO START WITH INS_SEQ_ID = ? ";
	// Shortfall CR KOC1179 added EMAIL_ID
	private static final String strGetCompanyDetails = "SELECT * FROM (SELECT ff.*,DENSE_RANK() OVER (ORDER BY ff.INS_COMP_NAME) Q FROM( SELECT dd.INS_SEQ_ID, dd.TPA_OFFICE_SEQ_ID ,b.DESCRIPTION ,dd.INS_PARENT_SEQ_ID,dd.INS_SECTOR_GENERAL_TYPE_ID,dd.ABBREVATION_CODE, dd.INS_COMP_NAME, CASE WHEN dd.INS_OFFICE_GENERAL_TYPE_ID != 'IHO' THEN dd.INS_COMP_NAME ||'-'|| dd.INS_COMP_CODE_NUMBER ELSE dd.INS_COMP_NAME END COMPANY,dd.INS_COMP_CODE_NUMBER, dd.INS_OFFICE_GENERAL_TYPE_ID,dd.EMAIL_ID FROM (WITH RECURSIVE A AS (SELECT  T1.INS_SEQ_ID, T1.TPA_OFFICE_SEQ_ID,    T1.INS_SECTOR_GENERAL_TYPE_ID,T1.INS_COMP_CODE_NUMBER, T1.INS_PARENT_SEQ_ID,T1.ABBREVATION_CODE, T1.INS_OFFICE_GENERAL_TYPE_ID ,T1.INS_HEAD_OFFICE_SEQ_ID, T1.INS_COMP_NAME,T1.EMAIL_ID FROM APP.TPA_INS_INFO T1 WHERE T1.INS_SEQ_ID= ?),  b as (    SELECT  T2.INS_SEQ_ID, T2.TPA_OFFICE_SEQ_ID,    T2.INS_SECTOR_GENERAL_TYPE_ID,T2.INS_COMP_CODE_NUMBER, T2.INS_PARENT_SEQ_ID,T2.ABBREVATION_CODE, T2.INS_OFFICE_GENERAL_TYPE_ID ,T2.INS_HEAD_OFFICE_SEQ_ID,T2.INS_COMP_NAME,T2.EMAIL_ID FROM APP.TPA_INS_INFO T2 join a on a.INS_SEQ_ID =t2.INS_PARENT_SEQ_ID   ),     c as (    SELECT  T3.INS_SEQ_ID, T3.TPA_OFFICE_SEQ_ID,    T3.INS_SECTOR_GENERAL_TYPE_ID,T3.INS_COMP_CODE_NUMBER, T3.INS_PARENT_SEQ_ID,T3.ABBREVATION_CODE, T3.INS_OFFICE_GENERAL_TYPE_ID ,T3.INS_HEAD_OFFICE_SEQ_ID, T3.INS_COMP_NAME,T3.EMAIL_ID FROM APP.TPA_INS_INFO T3 join b on t3.INS_PARENT_SEQ_ID=b.INS_SEQ_ID) select * from a union all select * from b union all select * from c)dd";
	// Shortfall CR KOC1179 getting EMAIL_ID
	private static final String strSavePricingList = "{CALL kvg_group_profile.save_group_profile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSelectPricingList = "{CALL kvg_group_profile.select_group_profile(?,?)}";
	private static final String strProfileIncomeList = "select id,val,grp from TPA_GRP_MASTER_CODES ORDER by grp asc";
	private static final String strSaveIncomeProfile = "{CALL kvg_group_profile.save_group_miltiple_list(?,?,?,?,?,?)}";
	private static final String strProfileIncomeListValue = "{CALL kvg_group_profile.select_group_miltiple_list(?,?)}";
	private static final String strInsuranceProfileList = "{CALL kvg_group_profile.select_group_profile_list(?,?)}";
	private static final String strSaveBenefitCoverage = "{CALL online_policy_copy_issue.save_benifit_coverage(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strGetBenefitCoverage = "{CALL online_policy_copy_issue.select_benifit_coverage(?,?,?)}";

	private static final String strSaveGenerateQuote = "{CALL Premium_calculation.p_calculate_premium(?,?,?,?,?,?,?,?,?,?)}";

	private static final String strSelectGenerateQuote = "{CALL Premium_calculation.select_calculate_premium (?,?,?,?)}";

	// bikki // gv pri_tool_pkg_dev.save_input_screen_1(dev) CALL
	// pri_tool_pkg_dev.select_input_screen_2(dev)
	private static final String strswSavePricingList = "{CALL app.pri_tool_pkg_save_input_screen_1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strswSelectPricingList = "{CALL app.pri_tool_pkg_select_input_screen_1(?,?)}";
	private static final String strSwInsuranceProfileList = "{CALL app.pri_tool_pkg_search_pric(?,?,?,?,?,?,?,?)}";
	private static final String strswProfileIncomeListValue = "{CALL app.pri_tool_pkg_select_input_screen_2(?,?,?,?,?)}";
	private static final String strswSaveIncomeProfile = "{CALL app.pri_tool_pkg_save_input_screen_2(?,?,?,?,?)}";
	private static final String strVatProductList = "{CALL INSCOMP_EMPANEL_PKG_sync_prod_list(?,?,?)}";
	// govind
	private static final String STR_VALIDATION_INPUT_SCREEN1 = "{CALL app.pri_tool_pkg_VALIDATION_INPUT_SCREEN1(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveVatProductList = "{CALL INSCOMP_EMPANEL_PKG.prod_sync(?,?,?,?)}";

	private static final String strSelectFactorMasterList = "{CALL app.pri_tool_pkg_select_factor(?)}"; // pri_tool_pkg.select_factor(v_rule_name
																									// OUT
																									// v_rule_name);

	private static final String strSaveFactorMaster = "{CALL app.pri_tool_pkg_save_factor(?,?,?,?)}";

	private static final String strSaveTrendFactorMaster = "{CALL app.pri_tool_pkg_SAVE_PRI_MASTER_DATA(?,?,?,?)}";
	private static final String strSelectTrendFactorMaster = "{CALL app.pri_tool_pkg_SELECT_PRI_MASTER_DATA(?,?)}"; // demographicData

	private static final String strSelectDemographicData = "{CALL app.PRI_TOOL_PKG_SELECT_RISK_PREMIUM_WORKING(?,?,?,?)}";

	private static final String strSaveDemographicData = "{CALL app.PRI_TOOL_PKG_SAVE_PROJ_WRKG_CLAIMS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	private static final String strSelectGrossPremiumData = "{CALL app.pri_tool_pkg_select_final_prem(?,?,?,?,?,?)}";

	private static final String strSaveGrossPremiumData = "{CALL app.pri_tool_pkg_save_final_prem(?,?,?,?,?,?,?,?,?,?)}"; // 9
																														// strSaveFactorAOC

	private static final String strSaveTable2Data = "{CALL app.PRI_TOOL_PKG_SAVE_PRIC_PER_MEM_CLM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveTable3Data = "{CALL app.PRI_TOOL_PKG_SAVE_ANTISELCTION_VOLATILITY(?,?,?,?,?,?,?,?,?)}";

	private static final String strFetchDetails = "{CALL app.pri_tool_pkg_fetch_current_data(?,?,?,?,?,?)}";

	private static final String strSelectFactorAOC = "{CALL app.pri_tool_pkg_select_factor_aoc(?)}";

	private static final String strSaveFactorAOC = "{CALL app.pri_tool_pkg_save_factor_aoc(?,?,?,?)}";
	private static final String strSavememberXml = "{CALL app.pri_tool_pkg_upload_census(?,?,?,?)}";

	// private static final String
	// strInsurancelDetail="SELECT INS_OFFICE_GENERAL_TYPE_ID,TPA_INS_INFO.INS_PARENT_SEQ_ID,TPA_INS_INFO.TPA_OFFICE_SEQ_ID,A.INS_COMP_NAME,ACTIVE_YN,INS_COMP_CODE_NUMBER,A.ABBREVATION_CODE,A.INS_SECTOR_GENERAL_TYPE_ID,EMPANELED_DATE,DISB_TYPE_ID,FLOAT_REPLENISHMENT_PERIOD ,FREQUENCY_TYPE_ID,ADDR_SEQ_ID,ADDRESS_1,ADDRESS_2,ADDRESS_3,STATE_TYPE_ID,CITY_TYPE_ID, PIN_CODE,EMAIL_ID, COUNTRY_ID,A.DESCRIPTION, TPA_INS_INFO.NOTIFY_TYPE_ID, TPA_INS_INFO.TO_MAIL_ID, TPA_INS_INFO.CC_MAIL_ID,TPA_INS_INFO.USER_GROUP,TPA_INS_INFO.PAYER_AUTHORITY,TPA_ADDRESS.ISD_CODE,TPA_ADDRESS.STD_CODE,TPA_ADDRESS.OFFICE_PHONE1,TPA_ADDRESS.OFFICE_PHONE2,PI_NONPI_INSURER FROM TPA_INS_INFO, TPA_ADDRESS,(SELECT INS_SEQ_ID,INS_COMP_NAME, ABBREVATION_CODE, INS_SECTOR_GENERAL_TYPE_ID,DESCRIPTION FROM TPA_INS_INFO,TPA_GENERAL_CODE  WHERE INS_PARENT_SEQ_ID IS NULL AND TPA_INS_INFO.INS_SECTOR_GENERAL_TYPE_ID = GENERAL_TYPE_ID AND INS_SEQ_ID = ?)A WHERE TPA_INS_INFO.INS_SEQ_ID = TPA_ADDRESS.INS_SEQ_ID AND TPA_INS_INFO.INS_SEQ_ID = ?";
	// //test Nag

	/**
	 * This method returns the ArrayList, which contains the list of
	 * InsuranceCompany which are populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of InsuranceCompany object's which contains the
	 *         InsuranceCompany details
	 * @exception throws TTKException
	 */
	public ArrayList getInsuranceCompanyList(ArrayList alSearchObjects)
			throws TTKException {
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strInsuranceList;
		Collection<Object> resultList = new ArrayList<Object>();
		InsuranceVO insuranceVO = null;
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		if (alSearchObjects != null && alSearchObjects.size() > 0) {
			String strEmpaneledDate = TTKCommon
					.checkNull(((SearchCriteria) alSearchObjects.get(0))
							.getValue());
			if (!strEmpaneledDate.equals("")) {
				sbfDynamicQuery.append(" AND to_date(to_char(EMPANELED_DATE,'DD/MM/YYYY'),'dd/mm/yyyy')>= to_date(to_char(TO_DATE('"+strEmpaneledDate+"' ,'DD/MM/YYYY'),'DD/MM/YYYY'),'dd/mm/yyyy')");
				/*sbfDynamicQuery.append(" AND to_char(EMPANELED_DATE,'DD/MM/YYYY')>= to_char(TO_DATE('"
						+ strEmpaneledDate + "','DD/MM/YYYY'),'DD/MM/YYYY')");*/
			}// end of if (!strEmpaneledDate.equals(""))
			for (int i = 1; i < alSearchObjects.size() - 4; i++) {
				if (!((SearchCriteria) alSearchObjects.get(i)).getValue()
						.equals("")) {
					sbfDynamicQuery.append(" AND UPPER("
							+ ((SearchCriteria) alSearchObjects.get(i))
									.getName()
							+ ") LIKE UPPER('"
							+ ((SearchCriteria) alSearchObjects.get(i))
									.getValue() + "%')");
				}// end of
					// if(!((SearchCriteria)alSearchCriteria.get(i)).getValue().equals(""))
			}// end of for()
		}// end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		strStaticQuery = TTKCommon
				.replaceInString(
						strStaticQuery,
						"#",
						(String) alSearchObjects.get(alSearchObjects.size() - 4)
								+ " "
								+ (String) alSearchObjects.get(alSearchObjects
										.size() - 3));
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
					insuranceVO = new InsuranceVO();

					if (rs.getString("INS_SEQ_ID") != null) {
						insuranceVO.setInsuranceSeqID(new Long(rs
								.getLong("INS_SEQ_ID")));
					}// end of if(rs.getString("INS_SEQ_ID") != null)

					insuranceVO.setCompanyName(TTKCommon.checkNull(rs
							.getString("INS_COMP_NAME")));

					if (rs.getDate("EMPANELED_DATE") != null) {
						insuranceVO.setEmpanelmentDate(rs
								.getDate("EMPANELED_DATE"));
					}// end of if(rs.getDate("EMPANELED_DATE") != null)

					if (rs.getString("INS_COMP_CODE_NUMBER") != null) {
						insuranceVO.setCompanyCodeNbr(rs
								.getString("INS_COMP_CODE_NUMBER"));
					}// end of if(rs.getDate("EMPANELED_DATE") != null)

					if (rs.getString("PAYER_AUTHORITY") != null) {
						insuranceVO.setStrAuthority(rs
								.getString("PAYER_AUTHORITY"));
					}// end of if(rs.getPAYER_AUTHORITY("PAYER_AUTHORITY") !=
						// null)

					insuranceVO.setCompanyStatus(TTKCommon.checkNull(rs
							.getString("ACTIVE_YN")));
					insuranceVO
							.setActiveInactive(TTKCommon.checkNull(
									rs.getString("ACTIVE_YN"))
									.equalsIgnoreCase("Y") ? "Active"
									: "Inactive");
					resultList.add(insuranceVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			return (ArrayList) resultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceComapanyList()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceComapanyList()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
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
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceComapanyList()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getInsuranceComapanyList(ArrayList alSearchObjects)

	/**
	 * This method returns the AraayList of InsuranceVO which contains the
	 * details of the HeadOffice and regional office and branch offices and
	 * regionael offices
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
	 * @return AraayList of InsuranceVO which contains the details of the
	 *         HeadOffice and regional office and branch offices and regionael
	 *         offices
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getCompanyDetails(ArrayList alSearchObjects)
			throws TTKException {
		String strStaticQuery = strGetCompanyDetails;
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strParentOfficeSeqID = (String) alSearchObjects.get(0);// INS_SEQ_ID
																		// OF
																		// THE
																		// PARENT
																		// OFFICE
		String strOfficeType = (String) alSearchObjects.get(1); // 'IHO' FOR
																// HEAD OFFICE
																// 'IRO' FOR
																// REGIONAL
																// OFFICES 'IDO'
																// FOR
																// DIVISIONAL
																// OFFICES 'IBO'
																// FOR BRANCH
																// OFFICES.
		String strStartRow = (String) alSearchObjects.get(2); // START ROW IN
																// THE CASES
																// PAGINATION
																// NOT REQUIRED
																// -- REPLACE
																// THE VALUES OF
																// Q WITH Q
																// ITSELF
		String strEndRow = (String) alSearchObjects.get(3); // START ROW IN THE
															// CASES PAGINATION
															// NOT REQUIRED --
															// REPLACE THE
															// VALUES OF Q WITH
															// Q ITSELF
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery, "?",
				strParentOfficeSeqID);
		sbfDynamicQuery
		/*.append(" CONNECT BY PRIOR INS_SEQ_ID = INS_PARENT_SEQ_ID ) A JOIN TPA_GENERAL_CODE B ON (A.INS_SECTOR_GENERAL_TYPE_ID = B.GENERAL_TYPE_ID) WHERE INS_OFFICE_GENERAL_TYPE_ID =")
				.append("'" + strOfficeType + "'").append(")A) WHERE Q >= ")
				.append(strStartRow).append("  AND Q <=").append(strEndRow);*/
		
		.append(" JOIN app.TPA_GENERAL_CODE B ON (dd.INS_SECTOR_GENERAL_TYPE_ID = B.GENERAL_TYPE_ID))ff WHERE ff.INS_OFFICE_GENERAL_TYPE_ID =")
		.append("'" + strOfficeType + "'").append(")AA ");
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		InsuranceVO insuranceVO = null;
		ArrayList<Object> alInsuranceList = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if (rs != null) {
				
				alInsuranceList = new ArrayList<Object>();
				while (rs.next()) {
					insuranceVO = new InsuranceVO();
					insuranceVO
							.setInsuranceSeqID(rs.getString("INS_SEQ_ID") != null ? new Long(
									rs.getLong("INS_SEQ_ID")) : null);
					insuranceVO.setTTKBranchCode(rs
							.getString("TPA_OFFICE_SEQ_ID") != null ? new Long(
							rs.getString("TPA_OFFICE_SEQ_ID")) : null);
					insuranceVO.setSectorTypeDesc(TTKCommon.checkNull(rs
							.getString("DESCRIPTION")));
					insuranceVO.setParentSeqID(rs
							.getString("INS_PARENT_SEQ_ID") != null ? new Long(
							rs.getString("INS_PARENT_SEQ_ID")) : null);
					insuranceVO.setSectorTypeCode(TTKCommon.checkNull(rs
							.getString("INS_SECTOR_GENERAL_TYPE_ID")));
					insuranceVO.setCompanyAbbreviation(TTKCommon.checkNull(rs
							.getString("ABBREVATION_CODE")));
					insuranceVO.setCompanyName(TTKCommon.checkNull(rs
							.getString("INS_COMP_NAME")));
					insuranceVO.setCompanyCodeNbr(TTKCommon.checkNull(rs
							.getString("INS_COMP_CODE_NUMBER")));
					insuranceVO.setOfficeType(TTKCommon.checkNull(rs
							.getString("INS_OFFICE_GENERAL_TYPE_ID")));
					insuranceVO.setBranchName(TTKCommon.checkNull(rs
							.getString("COMPANY")));
					alInsuranceList.add(insuranceVO);
				}// end of while(rs.next())
			}// End of if(rs!=null)
			return alInsuranceList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getCompanyDetails()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getCompanyDetails()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
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
									"Error while closing the Connection in InsuranceDAOImpl getCompanyDetails()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// End of getCompanyDetails(ArrayList alSearchObjects)

	/**
	 * This method returns the InsuranceDetailVO, which contains all the
	 * insurancecompany details
	 * 
	 * @param lInsuranceParentId
	 *            is Insurance Company Parent Sequence ID
	 * @param lInsuranceCompanyID
	 *            is insurance company ID whih is of long type
	 * @return InsuranceDetailVO object which contains all the insuranceComapany
	 *         details
	 * @exception throws TTKException
	 */
	public InsuranceDetailVO getInsuranceCompanyDetail(Long lInsuranceParentId,
			Long lInsuranceCompanyID) throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		InsuranceDetailVO insuranceDetailVO = new InsuranceDetailVO();
		AddressVO addressVO = new AddressVO();
		// added for Mail-SMS Template for Cigna
		MailNotificationVO mailnotificationVO = new MailNotificationVO();
		try {

			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strInsurancelDetail);
			pStmt.setLong(1, lInsuranceParentId); // head office ins_seq_id
			pStmt.setLong(2, lInsuranceCompanyID); // ins_seq_id of the office
			rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					insuranceDetailVO.setInsuranceSeqID(lInsuranceCompanyID);
					insuranceDetailVO.setOfficeType(TTKCommon.checkNull(rs
							.getString("INS_OFFICE_GENERAL_TYPE_ID")));
					if (rs.getString("INS_PARENT_SEQ_ID") != null) {
						insuranceDetailVO.setParentInsSeqID(new Long(rs
								.getLong("INS_PARENT_SEQ_ID")));
					}// end of if (rs.getString("INS_PARENT_SEQ_ID")!=null)
					if (rs.getString("TPA_OFFICE_SEQ_ID") != null) {
						insuranceDetailVO.setTTKBranchCode(new Long(rs
								.getLong("TPA_OFFICE_SEQ_ID")));
					}// end of if (rs.getString("TPA_OFFICE_SEQ_ID")!= null)
					insuranceDetailVO.setCompanyName(TTKCommon.checkNull(rs
							.getString("INS_COMP_NAME")));
					insuranceDetailVO.setCompanyStatus(TTKCommon.checkNull(rs
							.getString("ACTIVE_YN")));
					insuranceDetailVO.setCompanyCode(TTKCommon.checkNull(rs
							.getString("INS_COMP_CODE_NUMBER")));
					insuranceDetailVO.setCompanyAbbreviation(TTKCommon
							.checkNull(rs.getString("ABBREVATION_CODE")));
					// Shortfall CR KOC1179
					insuranceDetailVO.setCompanyEmailID(TTKCommon.checkNull(rs
							.getString("EMAIL_ID")));
					// End Shortfall CR KOC1179
					insuranceDetailVO.setSectorTypeCode(TTKCommon.checkNull(rs
							.getString("INS_SECTOR_GENERAL_TYPE_ID")));
					insuranceDetailVO.setSectorTypeDesc(TTKCommon.checkNull(rs
							.getString("DESCRIPTION")));
					if (rs.getDate("EMPANELED_DATE") != null) {
						insuranceDetailVO
								.setEmpanelmentDate(new java.util.Date(rs
										.getTimestamp("EMPANELED_DATE")
										.getTime()));
					}// end of if(rs.getDate("EMPANELED_DATE")!=null)
					insuranceDetailVO.setFundDisbursalCode(TTKCommon
							.checkNull(rs.getString("DISB_TYPE_ID")));
					if (rs.getString("FLOAT_REPLENISHMENT_PERIOD") != null) {
						insuranceDetailVO.setReplenishmentPeriod(new Long(rs
								.getLong("FLOAT_REPLENISHMENT_PERIOD")));
					}// end of if
						// (rs.getString("FLOAT_REPLENISHMENT_PERIOD")!=null)
					insuranceDetailVO.setFrequencyCode(TTKCommon.checkNull(rs
							.getString("FREQUENCY_TYPE_ID")));
					if (rs.getString("ADDR_SEQ_ID") != null) {
						addressVO.setAddrSeqId(new Long(rs
								.getLong("ADDR_SEQ_ID")));
					}// end of if(rs.getString("ADDR_SEQ_ID") != null)
					insuranceDetailVO.setAuthStandard(rs
							.getString("PAYER_AUTHORITY"));
					addressVO.setAddress1(TTKCommon.checkNull(rs
							.getString("ADDRESS_1")));
					addressVO.setAddress2(TTKCommon.checkNull(rs
							.getString("ADDRESS_2")));
					addressVO.setAddress3(TTKCommon.checkNull(rs
							.getString("ADDRESS_3")));
					addressVO.setStateCode(TTKCommon.checkNull(rs
							.getString("STATE_TYPE_ID")));
					addressVO.setCityCode(TTKCommon.checkNull(rs
							.getString("CITY_TYPE_ID")));
					addressVO.setPinCode(TTKCommon.checkNull(rs
							.getString("PIN_CODE")));
					addressVO.setCountryCode(TTKCommon.checkNull(rs
							.getString("COUNTRY_ID")));
					addressVO.setIsdCode((Integer) TTKCommon.checkNull(rs
							.getInt("ISD_CODE")));
					addressVO.setStdCode((Integer) TTKCommon.checkNull(rs
							.getInt("STD_CODE")));
					addressVO.setPhoneNo1(TTKCommon.checkNull(rs
							.getString("OFFICE_PHONE1")));
					addressVO.setPhoneNo2(TTKCommon.checkNull(rs
							.getString("OFFICE_PHONE2")));
					// added for CR KOC Mail-SMS Notification for Cigna
					mailnotificationVO.setNotiTypeId(TTKCommon.checkNull(rs
							.getString("NOTIFY_TYPE_ID")));
					mailnotificationVO.setNotiEmailId(TTKCommon.checkNull(rs
							.getString("TO_MAIL_ID")));
					mailnotificationVO.setCcEmailId(TTKCommon.checkNull(rs
							.getString("CC_MAIL_ID")));
					insuranceDetailVO.setuserRestrictionGroup(TTKCommon
							.checkNull(rs.getString("user_group")));// KOC
																	// Cigna_insurance_resriction

					insuranceDetailVO.setInsCategory(TTKCommon.checkNull(rs
							.getString("PI_NONPI_INSURER")));
					insuranceDetailVO.setCommonAuthorityCode(TTKCommon
							.checkNull(rs
									.getString("INS_COMP_COMMON_AUTH_CODE")));
					insuranceDetailVO.setVatTrnCode(TTKCommon.checkNull(rs
							.getString("Vat_Trn_Code")));// VAT TRN code
					insuranceDetailVO
							.setVatPercent(rs.getDouble("Vat_Percent"));// VAT %
					insuranceDetailVO.setMailnotification(mailnotificationVO);
					// ended
					insuranceDetailVO.setPolicygracePeriod(TTKCommon
							.checkNull(rs.getString("grace_period")));
			        insuranceDetailVO.setEffectivefromdateforpolicygracePeriod(TTKCommon.checkNull(rs.getString("grace_date")));
					insuranceDetailVO
							.setEffectivefromdateforpolicygracePeriod(TTKCommon
									.checkNull(rs.getString("grace_date")));
					insuranceDetailVO.setPolicygapPeriod(TTKCommon.checkNull(rs
							.getString("allowed_gap")));
					insuranceDetailVO
							.setEffectivefromdateforpolicygapPeriod(TTKCommon
									.checkNull(rs.getString("gap_date")));
					insuranceDetailVO.setAddress(addressVO);
					conn.commit();
				}// end of while(rs.next())
			}// End of if(rs!=null)
			return insuranceDetailVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
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
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getInsuranceCompanyDetail(Long lInsuranceCompanyID)

	/**
	 * This method adds or updates the insurance details The method also calls
	 * other methods on DAO to insert/update the insurance details to the
	 * database
	 * 
	 * @param insuranceCompanyVO
	 *            InsuranceDetailVO object which contains the hospital details
	 *            to be added/updated
	 * @return long value, Insurance Seq Id
	 * @exception throws TTKException
	 */
	public long addUpdateInsuranceCompany(InsuranceDetailVO insuranceDetailVO)
			throws TTKException {
		long lResult = 0;
		AddressVO addressVO = null;
		MailNotificationVO mailnotificationVO = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			addressVO = insuranceDetailVO.getAddress();
			// added for CR KOC Mail-SMS Notification
			mailnotificationVO = insuranceDetailVO.getMailnotification();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAddUpdateInsuranceInfo);
			if (insuranceDetailVO.getInsuranceSeqID() == null) {
				cStmtObject.setLong(INS_SEQ_ID, 0);// INS_SEQ_ID
			}// end of if(insuranceDetailVO.getInsuranceSeqID() == null)
			else {
				cStmtObject.setLong(INS_SEQ_ID,
						insuranceDetailVO.getInsuranceSeqID());// INS_SEQ_ID
			}// end of else
			if (addressVO.getAddrSeqId() != null) {
				cStmtObject.setLong(INS_ADDR_SEQ_ID, addressVO.getAddrSeqId());// INS_ADDR_SEQ_ID
			}// end of if(addressVO.getAddrSeqId()!=null)
			else {
				cStmtObject.setLong(INS_ADDR_SEQ_ID, 0);
			}// end of else
			if (insuranceDetailVO.getTTKBranchCode() != null) {
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,
						insuranceDetailVO.getTTKBranchCode());// TPA_OFFICE_SEQ_ID
			}// end of if (insuranceDetailVO.getTTKBranchCode()!= null)
			else {
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID, 0);// TPA_OFFICE_SEQ_ID
			}// end of else
			cStmtObject.setString(INS_SECTOR_GENERAL_TYPE_ID,
					TTKCommon.checkNull(insuranceDetailVO.getSectorTypeCode()));// INS_SECTOR_GENERAL_TYPE_ID
			cStmtObject.setString(INS_OFFICE_GENERAL_TYPE_ID,
					TTKCommon.checkNull(insuranceDetailVO.getOfficeType()));// INS_OFFICE_GENERAL_TYPE_ID
			cStmtObject.setString(INS_COMP_NAME,
					TTKCommon.checkNull(insuranceDetailVO.getCompanyName()));// INS_COMP_NAME
			cStmtObject.setString(ABBREVATION_CODE, TTKCommon
					.checkNull(insuranceDetailVO.getCompanyAbbreviation()));// ABBREVATION_CODE
			cStmtObject.setString(INS_COMP_CODE_NUMBER,
					TTKCommon.checkNull(insuranceDetailVO.getCompanyCode()));// INS_COMP_CODE_NUMBER
			// Shortfall CR KOC1179
			cStmtObject.setString(INS_COMPANY_EMAILID,
					TTKCommon.checkNull(insuranceDetailVO.getCompanyEmailID()));// INS_COMPANY_EMAILID
			// End Shortfall CR KOC1179
			if (insuranceDetailVO.getEmpanelmentDate() != null
					&& !insuranceDetailVO.getEmpanelmentDate().equals("")) {
				cStmtObject.setTimestamp(EMPANELED_DATE, (new Timestamp(
						insuranceDetailVO.getEmpanelmentDate().getTime())));// EMPANELED_DATE
			}// end of if(insuranceDetailVO.getEmpanelmentDate()!=null &&
				// !insuranceDetailVO.getEmpanelmentDate().equals(""))
			else {
				cStmtObject.setTimestamp(EMPANELED_DATE, null);// EMPANELED_DATE
			}// end of else
			cStmtObject.setString(FREQUENCY_TYPE_ID,
					TTKCommon.checkNull(insuranceDetailVO.getFrequencyCode()));// FREQ_TYPE_ID
			cStmtObject.setString(DISB_TYPE_ID, TTKCommon
					.checkNull(insuranceDetailVO.getFundDisbursalCode()));// DISB_TYPE_ID
			if (insuranceDetailVO.getReplenishmentPeriod() == null) {
				cStmtObject.setInt(FLOAT_REPLENISHMENT_PERIOD, 0);// FLOAT_REPLENISHMENT_PERIOD
			}// end of if (insuranceDetailVO.getReplenishmentPeriod()== null)
			else {
				cStmtObject.setInt(FLOAT_REPLENISHMENT_PERIOD,
						insuranceDetailVO.getReplenishmentPeriod().intValue());
			}// end of else
			cStmtObject.setString(ACTIVE_YN,
					TTKCommon.checkNull(insuranceDetailVO.getCompanyStatus()));// ACTIVE_YN
			if (insuranceDetailVO.getParentInsSeqID() != null) {
				cStmtObject.setLong(INS_PARENT_SEQ_ID,
						(insuranceDetailVO.getParentInsSeqID().intValue()));// INS_PARENT_SEQ_ID
			}// end of if(insuranceDetailVO.getParentInsSeqID()!=null)
			else {
				cStmtObject.setLong(INS_PARENT_SEQ_ID, 0);// INS_PARENT_SEQ_ID
			}// end of else
			cStmtObject.setString(ADDRESS_1,
					TTKCommon.checkNull(addressVO.getAddress1()));// ADDRESS_1
			cStmtObject.setString(ADDRESS_2,
					TTKCommon.checkNull(addressVO.getAddress2()));// ADDRESS_2
			cStmtObject.setString(ADDRESS_3,
					TTKCommon.checkNull(addressVO.getAddress3()));// ADDRESS_3
			cStmtObject.setString(CITY_TYPE_ID,
					TTKCommon.checkNull(addressVO.getCityCode()));// CITY_TYPE_ID
			cStmtObject.setString(STATE_TYPE_ID,
					TTKCommon.checkNull(addressVO.getStateCode()));// STATE_TYPE_ID
			if("".equals(TTKCommon.checkNull(addressVO.getPinCode())))
				cStmtObject.setLong(PIN_CODE,0);
			 else cStmtObject.setLong(PIN_CODE,Long.parseLong(addressVO.getPinCode()));// PIN_CODE
			
			if("".equals(TTKCommon.checkNull(addressVO.getCountryCode())))
				cStmtObject.setLong(COUNTRY_ID,0);
			 else cStmtObject.setLong(COUNTRY_ID,Long.parseLong(addressVO.getCountryCode()));// COUNTRY_ID	
			cStmtObject.setString(LANDMARKS, null);// LANDMARKS
			// added for CR KOC Mail-SMS Notification
			cStmtObject.setString(NOTIFY_EMAIL_ID,
					mailnotificationVO.getNotiEmailId());
			if (mailnotificationVO.getNotiTypeId() != null) {
				cStmtObject.setString(NOTIFY_TYPE_ID,
						mailnotificationVO.getNotiTypeId());
			} else {
				cStmtObject.setString(NOTIFY_TYPE_ID, null);
			}
			cStmtObject.setString(CC_EMAIL_ID,
					mailnotificationVO.getCcEmailId());

			if (insuranceDetailVO.getOfficeSequenceID() != null) {
				cStmtObject.setLong(INS_OFF_SEQ_ID,
						insuranceDetailVO.getOfficeSequenceID());// INS_OFF_SEQ_ID
			}// end of if (insuranceDetailVO.getOfficeSequenceID()!=null)
			else {
				cStmtObject.setLong(INS_OFF_SEQ_ID, 0);
			}// end of else
			if (insuranceDetailVO.getStartDate() != null
					&& !insuranceDetailVO.getStartDate().equals("")) {
				cStmtObject.setTimestamp(VALID_FROM, new Timestamp(
						insuranceDetailVO.getStartDate().getTime()));// VALID_FROM
			}// end of if(insuranceDetailVO.getStartDate()!=null &&
				// !insuranceDetailVO.getStartDate().equals(""))
			else {
				cStmtObject.setTimestamp(VALID_FROM, null);
			}// end of else
			if (insuranceDetailVO.getEndDate() != null
					&& !insuranceDetailVO.getEndDate().equals("")) {
				cStmtObject.setTimestamp(VALID_TO, new Timestamp(
						insuranceDetailVO.getEndDate().getTime()));// VALID_TO
			}// end of if(insuranceDetailVO.getEndDate()!=null &&
				// !insuranceDetailVO.getEndDate().equals(""))
			else {
				cStmtObject.setTimestamp(VALID_TO, null);
			}// end of else
			cStmtObject.setInt(USER_SEQ_ID, insuranceDetailVO.getUpdatedBy().intValue()); // USER_SEQ_ID
			
			if("".equals(TTKCommon.checkNull(insuranceDetailVO.getuserRestrictionGroup())))
				cStmtObject.setLong(user_group,0);
			 else cStmtObject.setLong(user_group,Long.parseLong(insuranceDetailVO.getuserRestrictionGroup()));
																				// Cigna_insurance_resriction
			cStmtObject.setString(AUTHORITY_STANANDARD,
					TTKCommon.checkNull(insuranceDetailVO.getAuthStandard()));// AUTHORITY
																				// STANDARD
			cStmtObject.setLong(ISD_CODE,
					(Integer) TTKCommon.checkNull(addressVO.getIsdCode()));// ISD_CODE
			cStmtObject.setLong(STD_CODE,
					(Integer) TTKCommon.checkNull(addressVO.getStdCode()));// STD_CODE
			cStmtObject.setLong(PHONE1,
					TTKCommon.getLong(addressVO.getPhoneNo1()));// PHONE1

			if ("".equals(TTKCommon.checkNull(addressVO.getPhoneNo2()))||addressVO.getPhoneNo2().equals("Phone No2")
					|| addressVO.getPhoneNo2().equals(" "))
				cStmtObject.setLong(PHONE2, 0);// PHONE2
			else
				cStmtObject.setLong(PHONE2,
						Long.parseLong(addressVO.getPhoneNo2()));// PHONE2
			// cStmtObject.setLong(PHONE2,TTKCommon.getLong(TTKCommon.checkNull(addressVO.getPhoneNo2())));//
			// PHONE2
			cStmtObject.setString(INSCATEGORY,
					TTKCommon.checkNull(insuranceDetailVO.getInsCategory()));// test
																				// INS
																				// CATEGORY)

			// cStmtObject.registerOutParameter(ROW_PROCESSED,Types.INTEGER);//ROW_PROCESSED
		//cStmtObject.setLong(ROW_PROCESSED, 0);
			cStmtObject.registerOutParameter(ROW_PROCESSED, Types.BIGINT);// ROW_PROCESSED
				
			cStmtObject.registerOutParameter(INS_SEQ_ID, Types.BIGINT);
			cStmtObject.registerOutParameter(INS_ADDR_SEQ_ID, Types.BIGINT);
			cStmtObject.registerOutParameter(INS_OFF_SEQ_ID, Types.BIGINT);
			
			cStmtObject.setString(INS_COMP_AUTHORITY_CODE_NUMBER, TTKCommon
					.checkNull(insuranceDetailVO.getCommonAuthorityCode()));// INS_COMP_AUTHORITY_CODE_NUMBER

			cStmtObject.setString(INS_VAT_TRN,
					TTKCommon.checkNull(insuranceDetailVO.getVatTrnCode()));
			cStmtObject.setDouble(INS_VAT, insuranceDetailVO.getVatPercent());
			cStmtObject.setString(PRODUCT_IDS_LIST,
					insuranceDetailVO.getProductIdList());
			if((insuranceDetailVO.getPolicygapPeriod()).equals("")){
				cStmtObject.setLong(POLICY_GAP_DAYS,
						Long.parseLong("0")); 
			}
			else{
				cStmtObject.setLong(POLICY_GAP_DAYS,
						Long.parseLong((insuranceDetailVO.getPolicygapPeriod()))); 
			}
			  
			if((insuranceDetailVO.getPolicygapPeriod()).equals("")){
				cStmtObject.setLong(POLICY_GRACE_DAYS,
						Long.parseLong("0"));  
			}
			else{
				cStmtObject.setLong(POLICY_GRACE_DAYS,
						Long.parseLong((insuranceDetailVO.getPolicygracePeriod())));  
			}
			
			  
			cStmtObject.execute();
		
			lResult = cStmtObject.getLong(INS_SEQ_ID);
			conn.commit();
			/*cStmtObject.setString(POLICY_GAP_DAYS,insuranceDetailVO.getPolicygapPeriod());
			cStmtObject.setString(POLICY_GRACE_DAYS,insuranceDetailVO.getPolicygracePeriod());*/
			
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Statement in InsuranceDAOImpl addUpdateInsuranceCompany()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Connection in InsuranceDAOImpl addUpdateInsuranceCompany()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return lResult;
	}// end of addUpdateInsuranceCompany(InsuranceDetailVO insuranceDetailVO)
		// throws TTKException

	/**
	 * This method delete's the insurance records from the database.
	 * 
	 * @param alInsuranceCompanyList
	 *            ArrayList object which contains the insurance sequence id's to
	 *            be deleted
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
	 * @exception throws TTKException
	 */
	public int deleteInsuranceCompany(String strInsSeqID) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strDeleteInsuranceInfo);
			cStmtObject.setString(1, strInsSeqID);// string of sequence id's
													// which are separated with
	             									// | as separator (Note:
													// String should also end
													// with | at the end)
			cStmtObject.registerOutParameter(2, Types.INTEGER);// out parameter
																// which gives
																// the number of
																// records
																// deleted
			cStmtObject.execute();
			iResult = cStmtObject.getInt(2);
			conn.commit();
			
			/*conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strRemoveUsers);
			cStmtObject.setString(1,strGroupUserSeqID); //Concatenated String of GROUP_USER_SEQ_ID'S
			cStmtObject.registerOutParameter(2,Types.SMALLINT);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getShort(2);
			conn.commit();*/
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Statement in InsuranceDAOImpl deleteInsuranceCompany()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Connection in InsuranceDAOImpl deleteInsuranceCompany()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteInsuranceCompany(String strInsSeqID)

	public Long savePricingList(InsPricingVO insPricingVO) throws TTKException {
		Long iResult = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSavePricingList);

			if (insPricingVO.getGroupProfileSeqID() != null) {
				cStmtObject.setLong(1, insPricingVO.getGroupProfileSeqID());
			} else {
				cStmtObject.setLong(1, 0);
			}// end of else
			cStmtObject.setString(2, insPricingVO.getCurrency());
			cStmtObject.setString(3, insPricingVO.getAverageAge());
			cStmtObject.setString(4, insPricingVO.getFamilyCoverage());
			cStmtObject.setString(5, insPricingVO.getGroupName());
			cStmtObject.setString(6, insPricingVO.getNoofEmployees());
			cStmtObject.setString(7, insPricingVO.getEmployeeGender());
			cStmtObject.setString(8, insPricingVO.getAdditionalPremium());
			cStmtObject.setString(9, insPricingVO.getGlobalCoverge());
			cStmtObject.setString(10, insPricingVO.getCountry());
			cStmtObject.setString(11, insPricingVO.getNameOfInsurer());
			cStmtObject.setString(12, insPricingVO.getNameOfTPA());
			cStmtObject.setString(13, insPricingVO.getPlanName());
			cStmtObject.setString(14, insPricingVO.getEligibility());
			cStmtObject.setString(15, insPricingVO.getTakafulQuote());
			cStmtObject.setString(16, insPricingVO.getAreaOfCover());
			cStmtObject.setLong(17, insPricingVO.getAddedBy());
			cStmtObject.registerOutParameter(1, Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getLong(1);

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Statement in InsuranceDAOImpl deleteInsuranceCompany()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl deleteInsuranceCompany()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteInsuranceCompany(String strInsSeqID)

	public InsPricingVO selectPricingList(Long lpricingSeqId)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		InsPricingVO insPricingVO = new InsPricingVO();
		AddressVO addressVO = new AddressVO();

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSelectPricingList);
			cStmtObject.setLong(1, lpricingSeqId);
			cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(2);
			if (rs != null) {
				while (rs.next()) {
					insPricingVO.setGroupProfileSeqID(lpricingSeqId);
					insPricingVO.setCurrency(TTKCommon.checkNull(rs
							.getString("CURRENCY_SEQ_ID")));
					insPricingVO.setAverageAge(TTKCommon.checkNull(rs
							.getString("AVG_AGE_SEQ_ID")));
					insPricingVO.setFamilyCoverage(TTKCommon.checkNull(rs
							.getString("FMLY_COVG_TYPE_ID")));
					insPricingVO.setGroupName(TTKCommon.checkNull(rs
							.getString("GROUP_NAME")));
					insPricingVO.setNoofEmployees(TTKCommon.checkNull(rs
							.getString("NO_OF_EMP_ID")));
					insPricingVO.setEmployeeGender(TTKCommon.checkNull(rs
							.getString("GEN_BRK_MASTR_ID")));
					insPricingVO.setAdditionalPremium(TTKCommon.checkNull(rs
							.getString("ADD_PREMIUM")));
					insPricingVO.setGlobalCoverge(rs
							.getString("GLOB_COVG_TYP_ID"));
					insPricingVO.setCountry(rs.getString("COUNTRY_TYP_ID"));

					insPricingVO.setNameOfInsurer(TTKCommon.checkNull(rs
							.getString("INSURER_TYPE_ID")));
					insPricingVO.setNameOfTPA(TTKCommon.checkNull(rs
							.getString("TPA_ID")));
					insPricingVO.setPlanName(TTKCommon.checkNull(rs
							.getString("PLAN_ID")));
					insPricingVO.setEligibility(rs.getString("ELIGIBLE_ID"));
					insPricingVO.setTakafulQuote(rs.getString("TAKAFUL_YN"));
					insPricingVO.setAreaOfCover(rs.getString("AREA_COVERAGE"));

				}// end of while(rs.next())
			}// End of if(rs!=null)
			return insPricingVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null) {
						rs.close();

					}
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (cStmtObject != null) {
							cStmtObject.close();
						}

					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	public ArrayList getProfileIncomeList(Long lpricingSeqId)
			throws TTKException {

		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		InsPricingVO insPricingVO = null;

		try {

			conn = ResourceManager.getConnection();

			pStmt = conn.prepareStatement(strProfileIncomeList);

			rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					insPricingVO = new InsPricingVO();

					insPricingVO.setProfileID1(TTKCommon.checkNull(rs
							.getString("id")));
					insPricingVO.setProfileValue1(TTKCommon.checkNull(rs
							.getString("val")));
					insPricingVO.setProfileGroup1(TTKCommon.checkNull(rs
							.getString("grp")));

					alResultList.add(insPricingVO);

				}
			}
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}

	public int saveIncomeProfile(InsPricingVO insPricingVO) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		String ProfileID = "";
		String ProfileValue = "";
		String ProfileGroup = "";
		String ProfilePercentage = "";
		Long TransProfileSeqID = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveIncomeProfile);

			if (insPricingVO.getGroupProfileSeqID() != null) {
				for (int i = 0; i < insPricingVO.getProfileID().length; i++) {
					if (!insPricingVO.getProfileID()[i].equals(""))// to
					{
						ProfileID = insPricingVO.getProfileID()[i];
					} else {
						ProfileID = null;
					}

					if (!insPricingVO.getProfileValue().equals("")) {
						ProfileValue = insPricingVO.getProfileValue()[i];
					} else {
						ProfileValue = null;
					}
					if (!insPricingVO.getProfileGroup().equals("")) {
						ProfileGroup = insPricingVO.getProfileGroup()[i];
					} else {
						ProfileGroup = null;
					}
					if (!insPricingVO.getProfilePercentage().equals("")) {
						ProfilePercentage = insPricingVO.getProfilePercentage()[i];
					} else {
						ProfilePercentage = null;
					}
					if (!insPricingVO.getTransProfileSeqID().equals("")) {
						TransProfileSeqID = insPricingVO.getTransProfileSeqID()[i];
					} else {
						TransProfileSeqID = null;
					}

					if (TransProfileSeqID != null) {
						cStmtObject.setLong(1, TransProfileSeqID);
					} else {
						cStmtObject.setLong(1, 0);
					}
					cStmtObject.setLong(2, insPricingVO.getGroupProfileSeqID());
					cStmtObject.setString(3, ProfileID);
					cStmtObject.setString(4, ProfileValue);
					cStmtObject.setString(5, ProfileGroup);
					cStmtObject.setString(6, ProfilePercentage);

					cStmtObject.addBatch();
				}
			}
			cStmtObject.executeBatch();

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "maintenance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "maintenance");
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
							"Error while closing the Statement in MaintenanceDAOImpl saveGroupSpecificDesc()",
							sqlExp);
					throw new TTKException(sqlExp, "maintenance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in MaintenanceDAOImpl saveGroupSpecificDesc()",
								sqlExp);
						throw new TTKException(sqlExp, "maintenance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "maintenance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of saveGroupSpecificDesc(CriticalICDDetailVO criticalICDDetailVO)

	public ArrayList getProfileIncomeListvalue(Long lpricingSeqId)
			throws TTKException {

		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		Collection<Object> resultList = new ArrayList<Object>();
		InsPricingVO insPricingVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strProfileIncomeListValue);
			cStmtObject.setLong(1, lpricingSeqId);
			cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(2);
			if (rs != null) {
				while (rs.next()) {
					insPricingVO = new InsPricingVO();

					insPricingVO.setProfileID1(TTKCommon.checkNull(rs
							.getString("id")));
					insPricingVO.setProfileValue1(TTKCommon.checkNull(rs
							.getString("val")));
					insPricingVO.setProfileGroup1(TTKCommon.checkNull(rs
							.getString("grp")));
					insPricingVO.setProfileGroupValue1(TTKCommon.checkNull(rs
							.getString("grp_show")));
					insPricingVO.setProfilePercentage1(TTKCommon.checkNull(rs
							.getString("perc")));
					insPricingVO.setTransProfileSeqID1(rs
							.getLong("tpa_grp_prof_tran_id"));
					insPricingVO.setGroupProfileSeqID(rs
							.getLong("grp_prof_seq_id"));

					alResultList.add(insPricingVO);

				}
			}
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	public ArrayList getInsuranceProfileList(ArrayList alSearchObjects)
			throws TTKException {
		StringBuffer sbfDynamicQuery = new StringBuffer();
		Connection conn = null;
		PreparedStatement pStmt = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		String strStaticQuery = "";

		String t;
		Collection<Object> alResultList = new ArrayList<Object>();
		InsPricingVO insPricingVO = null;

		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strInsuranceProfileList);
			cStmtObject.setString(1, (String) alSearchObjects.get(0));

			cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(2);
			if (rs != null) {
				while (rs.next()) {
					insPricingVO = new InsPricingVO();
					if (rs.getString("GRP_PROF_SEQ_ID") != null) {
						insPricingVO.setGroupProfileSeqID(new Long(rs
								.getLong("GRP_PROF_SEQ_ID")));
					}
					insPricingVO.setGroupName(TTKCommon.checkNull(rs
							.getString("GROUP_NAME")));
					alResultList.add(insPricingVO);
				}// end of if(strIdentifier.equalsIgnoreCase("Administration"))

			}// End of while (rs.next())

			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	public int savePlanDesignConfig(PolicyConfigVO policyConfigVO)
			throws TTKException {
		// TODO Auto-generated method stub
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveBenefitCoverage);

			if (policyConfigVO.getGroupProfileSeqID() != null) {
				cStmtObject.setLong(1, policyConfigVO.getGroupProfileSeqID());
			}// end of if(custConfigMsgVO.getProdPolicySeqID() != null)
			else {
				cStmtObject.setLong(1, 0);
			}// end of else.

			/* GROUP LEVEL DESCRIPTION */

			if (policyConfigVO.getGroupLevelYN() != null) {
				cStmtObject.setString(2, policyConfigVO.getGroupLevelYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(2, null);
			}// end of else

			if (policyConfigVO.getInitialWatiingPeriod() != null) {
				cStmtObject.setBigDecimal(3,
						policyConfigVO.getInitialWatiingPeriod());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setBigDecimal(3, null);
			}// end of else

			if (policyConfigVO.getNonNetworkRemCopayGroup() != null) {
				cStmtObject.setString(4,
						policyConfigVO.getNonNetworkRemCopayGroup());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(4, null);
			}// end of else

			if (policyConfigVO.getElectiveOutsideCover() != null) {
				cStmtObject.setString(5,
						policyConfigVO.getElectiveOutsideCover());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(5, null);
			}// end of else

			if (policyConfigVO.getElectiveOutsideCoverDays() != null) {
				cStmtObject.setBigDecimal(6,
						policyConfigVO.getElectiveOutsideCoverDays());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(6, 0);
			}// end of else

			if (policyConfigVO.getTransportaionOverseasLimit() != null) {
				cStmtObject.setBigDecimal(7,
						policyConfigVO.getTransportaionOverseasLimit());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(7, 0);
			}// end of else

			if (policyConfigVO.getRepatriationRemainsLimit() != null) {
				cStmtObject.setBigDecimal(8,
						policyConfigVO.getRepatriationRemainsLimit());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(8, 0);
			}// end of else

			if (policyConfigVO.getSpecialistConsultationReferal() != null) {
				cStmtObject.setString(9,
						policyConfigVO.getSpecialistConsultationReferal());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(9, null);
			}// end of else

			if (policyConfigVO.getEmergencyEvalAML() != null) {
				cStmtObject.setBigDecimal(10,
						policyConfigVO.getEmergencyEvalAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(10, 0);
			}// end of else

			if (policyConfigVO.getInternationalMedicalAssis() != null) {
				cStmtObject.setString(11,
						policyConfigVO.getInternationalMedicalAssis());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(11, null);
			}// end of else

			if (policyConfigVO.getSpecialistConsultationReferalGroup() != null) {
				cStmtObject.setString(12,
						policyConfigVO.getSpecialistConsultationReferalGroup());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(12, null);
			}// end of else

			if (policyConfigVO.getPerLifeAML() != null) {
				cStmtObject.setBigDecimal(13, policyConfigVO.getPerLifeAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(13, 0);
			}// end of else

			if (policyConfigVO.getOutpatientCoverage() != null) {
				cStmtObject.setString(14,
						policyConfigVO.getOutpatientCoverage());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(14, null);
			}// end of else

			if (policyConfigVO.getMaternityCopayGroup() != null) {
				cStmtObject.setString(15,
						policyConfigVO.getMaternityCopayGroup());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(15, null);
			}// end of else

			if (policyConfigVO.getGroundTransportaionPerc() != null) {
				cStmtObject.setString(16,
						policyConfigVO.getGroundTransportaionPerc());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setString(16, null);
			}// end of else

			if (policyConfigVO.getGroundTransportaionNumeric() != null) {
				cStmtObject.setBigDecimal(17,
						policyConfigVO.getGroundTransportaionNumeric());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setInt(17, 0);
			}// end of else

			if (policyConfigVO.getInsPatYN() != null) {
				cStmtObject.setString(18, policyConfigVO.getInsPatYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(18, null);
			}// end of else

			if (policyConfigVO.getAnnualMaxLimit() != null) {
				cStmtObject.setBigDecimal(19,
						policyConfigVO.getAnnualMaxLimit());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(19, 0);
			}// end of else

			if (policyConfigVO.getRoomType() != null) {
				cStmtObject.setString(20, policyConfigVO.getRoomType());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(20, null);
			}// end of else

			if (policyConfigVO.getCopay() != null) {
				cStmtObject.setString(21, policyConfigVO.getCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(21, null);
			}// end of else

			if (policyConfigVO.getCompanionBenefit() != null) {
				cStmtObject.setString(22, policyConfigVO.getCompanionBenefit());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(22, null);
			}// end of else

			if (policyConfigVO.getCompanionBenefitAMl() != null) {
				cStmtObject.setBigDecimal(23,
						policyConfigVO.getCompanionBenefitAMl());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(23, 0);
			}

			if (policyConfigVO.getInpatientCashBenefit() != null) {
				cStmtObject.setString(24,
						policyConfigVO.getInpatientCashBenefit());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(24, null);
			}// end of else

			if (policyConfigVO.getCashBenefitAML() != null) {
				cStmtObject.setBigDecimal(25,
						policyConfigVO.getCashBenefitAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(25, 0);
			}
			if (policyConfigVO.getEmergencyDental() != null) {
				cStmtObject.setBigDecimal(26,
						policyConfigVO.getEmergencyDental());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(26, 0);
			}
			if (policyConfigVO.getEmergencyMaternity() != null) {
				cStmtObject.setBigDecimal(27,
						policyConfigVO.getEmergencyMaternity());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(27, 0);
			}
			if (policyConfigVO.getAmbulance() != null) {
				cStmtObject.setString(28, policyConfigVO.getAmbulance());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(28, null);
			}// end of else

			if (policyConfigVO.getInpatientICU() != null) {
				cStmtObject.setString(29, policyConfigVO.getInpatientICU());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(29, null);
			}// end of else

			if (policyConfigVO.getPharmacyYN() != null) {
				cStmtObject.setString(30, policyConfigVO.getPharmacyYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(30, null);
			}// end of else

			if (policyConfigVO.getcopaypharmacy() != null) {
				cStmtObject.setString(31, policyConfigVO.getcopaypharmacy());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(31, null);
			}// end of else

			if (policyConfigVO.getAmlPharmacy() != null) {
				cStmtObject.setBigDecimal(32, policyConfigVO.getAmlPharmacy());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(32, 0);
			}

			if (policyConfigVO.getChronicAML() != null) {
				cStmtObject.setBigDecimal(33, policyConfigVO.getChronicAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(33, 0);
			}

			if (policyConfigVO.getChronicPharmacyCopayPerc() != null) {
				cStmtObject.setString(34,
						policyConfigVO.getChronicPharmacyCopayPerc());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(34, null);
			}// end of else

			if (policyConfigVO.getChronicPharmacyCopayNum() != null) {
				cStmtObject.setBigDecimal(35,
						policyConfigVO.getChronicPharmacyCopayNum());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(35, 0);
			}

			if (policyConfigVO.getPreauthLimitVIP() != null) {
				cStmtObject.setBigDecimal(36,
						policyConfigVO.getPreauthLimitVIP());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(36, 0);
			}

			if (policyConfigVO.getNonNetworkRemCopay() != null) {
				cStmtObject.setString(37,
						policyConfigVO.getNonNetworkRemCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(37, null);
			}// end of else

			if (policyConfigVO.getPreauthLimitNonVIP() != null) {
				cStmtObject.setBigDecimal(38,
						policyConfigVO.getPreauthLimitNonVIP());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(38, 0);
			}
			if (policyConfigVO.getLabdiagYN() != null) {
				cStmtObject.setString(39, policyConfigVO.getLabdiagYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(39, null);
			}// end of else

			if (policyConfigVO.getCopayLab() != null) {
				cStmtObject.setString(40, policyConfigVO.getCopayLab());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(40, null);
			}// end of else

			if (policyConfigVO.getNonNetworkRemCopayLab() != null) {
				cStmtObject.setString(41,
						policyConfigVO.getNonNetworkRemCopayLab());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(41, null);
			}// end of else

			if (policyConfigVO.getOncologyTest() != null) {
				cStmtObject.setString(42, policyConfigVO.getOncologyTest());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(42, null);
			}// end of else

			if (policyConfigVO.getOncologyTestAML() != null) {
				cStmtObject.setBigDecimal(43,
						policyConfigVO.getOncologyTestAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(43, 0);
			}

			if (policyConfigVO.getPreventiveScreeningDiabetics() != null) {
				cStmtObject.setString(44,
						policyConfigVO.getPreventiveScreeningDiabetics());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(44, null);
			}// end of else

			if (policyConfigVO.getPreventiveScreenDiabeticsAnnual() != null) {
				cStmtObject.setString(45,
						policyConfigVO.getPreventiveScreenDiabeticsAnnual());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(45, null);
			}// end of else

			if (policyConfigVO.getPreventiveScreeningDiabeticsAge() != null) {
				cStmtObject.setString(46,
						policyConfigVO.getPreventiveScreeningDiabeticsAge());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(46, null);
			}// end of else

			if (policyConfigVO.getOpConsultYN() != null) {
				cStmtObject.setString(47, policyConfigVO.getOpConsultYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(47, null);
			}// end of else

			if (policyConfigVO.getConsultationAML() != null) {
				cStmtObject.setBigDecimal(48,
						policyConfigVO.getConsultationAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(48, 0);
			}

			if (policyConfigVO.getGpConsultationList() != null) {
				cStmtObject.setString(49,
						policyConfigVO.getGpConsultationList());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(49, null);
			}// end of else

			if (policyConfigVO.getGpConsultationNum() != null) {
				cStmtObject.setBigDecimal(50,
						policyConfigVO.getGpConsultationNum());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(50, 0);
			}

			if (policyConfigVO.getSpecConsultationList() != null) {
				cStmtObject.setString(51,
						policyConfigVO.getSpecConsultationList());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(51, null);
			}// end of else

			if (policyConfigVO.getSpecConsultationNum() != null) {
				cStmtObject.setBigDecimal(52,
						policyConfigVO.getSpecConsultationNum());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(52, 0);
			}

			if (policyConfigVO.getPhysiotherapySession() != null) {
				cStmtObject.setBigDecimal(53,
						policyConfigVO.getPhysiotherapySession());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(53, 0);
			}

			if (policyConfigVO.getPhysiotherapyAMLLimit() != null) {
				cStmtObject.setBigDecimal(54,
						policyConfigVO.getPhysiotherapyAMLLimit());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(54, 0);
			}

			if (policyConfigVO.getNoOfmaternityConsults() != null) {
				cStmtObject.setBigDecimal(55,
						policyConfigVO.getNoOfmaternityConsults());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(55, 0);
			}

			if (policyConfigVO.getMaternityConsultations() != null) {
				cStmtObject.setString(56,
						policyConfigVO.getMaternityConsultations());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(56, null);
			}// end of else

			if (policyConfigVO.getMaternityConsultationsNum() != null) {
				cStmtObject.setBigDecimal(57,
						policyConfigVO.getMaternityConsultationsNum());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(57, 0);
			}

			if (policyConfigVO.getChronicDiseaseConsults() != null) {
				cStmtObject.setBigDecimal(58,
						policyConfigVO.getChronicDiseaseConsults());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(58, 0);
			}

			if (policyConfigVO.getChronicDiseaseAML() != null) {
				cStmtObject.setBigDecimal(59,
						policyConfigVO.getChronicDiseaseAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(59, 0);
			}

			if (policyConfigVO.getChronicDiseaseCopay() != null) {
				cStmtObject.setString(60,
						policyConfigVO.getChronicDiseaseCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(60, null);
			}// end of else

			if (policyConfigVO.getChronicDiseaseDeductible() != null) {
				cStmtObject.setBigDecimal(61,
						policyConfigVO.getChronicDiseaseDeductible());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(61, 0);
			}

			if (policyConfigVO.getMaternityYN() != null) {
				cStmtObject.setString(62, policyConfigVO.getMaternityYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(62, null);
			}// end of else

			if (policyConfigVO.getNormalDeliveryAML() != null) {
				cStmtObject.setBigDecimal(63,
						policyConfigVO.getNormalDeliveryAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(63, 0);
			}

			if (policyConfigVO.getMaternityCsectionAML() != null) {
				cStmtObject.setBigDecimal(64,
						policyConfigVO.getMaternityCsectionAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(64, 0);
			}

			if (policyConfigVO.getDayCoverage() != null) {
				cStmtObject.setString(65, policyConfigVO.getDayCoverage());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(65, null);
			}// end of else

			if (policyConfigVO.getDayCoverageVaccination() != null) {
				cStmtObject.setString(66,
						policyConfigVO.getDayCoverageVaccination());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(66, null);
			}// end of else

			if (policyConfigVO.getMaternityComplicationAML() != null) {
				cStmtObject.setBigDecimal(67,
						policyConfigVO.getMaternityComplicationAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(67, 0);
			}

			if (policyConfigVO.getDentalYN() != null) {
				cStmtObject.setString(68, policyConfigVO.getDentalYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(68, null);
			}// end of else

			if (policyConfigVO.getDentalAML() != null) {
				cStmtObject.setBigDecimal(69, policyConfigVO.getDentalAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(69, 0);
			}

			if (policyConfigVO.getDentalCopay() != null) {
				cStmtObject.setString(70, policyConfigVO.getDentalCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(70, null);
			}// end of else

			if (policyConfigVO.getDentalDeductible() != null) {
				cStmtObject.setBigDecimal(71,
						policyConfigVO.getDentalDeductible());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(71, 0);
			}

			if (policyConfigVO.getCleaningAML() != null) {
				cStmtObject.setBigDecimal(72, policyConfigVO.getCleaningAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(72, 0);
			}

			if (policyConfigVO.getOrthodonticsAML() != null) {
				cStmtObject.setBigDecimal(73,
						policyConfigVO.getOrthodonticsAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(73, 0);
			}

			if (policyConfigVO.getDentalNonNetworkRem() != null) {
				cStmtObject.setString(74,
						policyConfigVO.getDentalNonNetworkRem());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(74, null);
			}// end of else

			if (policyConfigVO.getChronicYN() != null) {
				cStmtObject.setString(75, policyConfigVO.getChronicYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(75, null);
			}// end of else

			if (policyConfigVO.getChronicAMLNum() != null) {
				cStmtObject
						.setBigDecimal(76, policyConfigVO.getChronicAMLNum());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(76, 0);
			}

			if (policyConfigVO.getPharmacyAML() != null) {
				cStmtObject.setBigDecimal(77, policyConfigVO.getPharmacyAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(77, 0);
			}

			if (policyConfigVO.getPharmacyAMLCopay() != null) {
				cStmtObject.setString(78, policyConfigVO.getPharmacyAMLCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(78, null);
			}// end of else

			if (policyConfigVO.getPharmacyAMLAmount() != null) {
				cStmtObject.setBigDecimal(79,
						policyConfigVO.getPharmacyAMLAmount());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(79, 0);
			}

			if (policyConfigVO.getChronicLabDiag() != null) {
				cStmtObject.setBigDecimal(80,
						policyConfigVO.getChronicLabDiag());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(80, 0);
			}

			if (policyConfigVO.getChronicLabDiagCopay() != null) {
				cStmtObject.setString(81,
						policyConfigVO.getChronicLabDiagCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(81, null);
			}// end of else

			if (policyConfigVO.getChronicLabDiagAmount() != null) {
				cStmtObject.setBigDecimal(82,
						policyConfigVO.getChronicLabDiagAmount());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(82, 0);
			}

			if (policyConfigVO.getChronicConsultationAML() != null) {
				cStmtObject.setBigDecimal(83,
						policyConfigVO.getChronicConsultationAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(83, 0);
			}

			if (policyConfigVO.getChronicConsultationCopay() != null) {
				cStmtObject.setString(84,
						policyConfigVO.getChronicConsultationCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(84, null);
			}// end of else

			if (policyConfigVO.getChronicConsultation() != null) {
				cStmtObject.setBigDecimal(85,
						policyConfigVO.getChronicConsultation());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(85, 0);
			}

			if (policyConfigVO.getPedYN() != null) {
				cStmtObject.setString(86, policyConfigVO.getPedYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(86, null);
			}// end of else

			if (policyConfigVO.getCoveredPED() != null) {
				cStmtObject.setString(87, policyConfigVO.getCoveredPED());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(87, null);
			}// end of else

			if (policyConfigVO.getPedAML() != null) {
				cStmtObject.setBigDecimal(88, policyConfigVO.getPedAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(88, 0);
			}

			if (policyConfigVO.getPedCopayDeductible() != null) {
				cStmtObject.setString(89,
						policyConfigVO.getPedCopayDeductible());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(89, null);
			}// end of else

			if (policyConfigVO.getPedDeductible() != null) {
				cStmtObject
						.setBigDecimal(90, policyConfigVO.getPedDeductible());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(90, 0);
			}

			if (policyConfigVO.getPsychiatryYN() != null) {
				cStmtObject.setString(91, policyConfigVO.getPsychiatryYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(91, null);
			}// end of else

			if (policyConfigVO.getInpatientAML() != null) {
				cStmtObject.setBigDecimal(92, policyConfigVO.getInpatientAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(92, 0);
			}

			if (policyConfigVO.getNoofInpatientDays() != null) {
				cStmtObject.setBigDecimal(93,
						policyConfigVO.getNoofInpatientDays());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(93, 0);
			}

			if (policyConfigVO.getOutpatientAML() != null) {
				cStmtObject
						.setBigDecimal(94, policyConfigVO.getOutpatientAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(94, 0);
			}

			if (policyConfigVO.getNoofOutpatientConsul() != null) {
				cStmtObject.setBigDecimal(95,
						policyConfigVO.getNoofOutpatientConsul());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(95, 0);
			}

			if (policyConfigVO.getPsychiatryCopayDeduc() != null) {
				cStmtObject.setString(96,
						policyConfigVO.getPsychiatryCopayDeduc());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(96, null);
			}// end of else

			if (policyConfigVO.getPsychiatryDeductible() != null) {
				cStmtObject.setBigDecimal(97,
						policyConfigVO.getPsychiatryDeductible());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(97, 0);
			}

			if (policyConfigVO.getOthersYN() != null) {
				cStmtObject.setString(98, policyConfigVO.getOthersYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(98, null);
			}// end of else

			if (policyConfigVO.getSystemOfMedicine() != null) {
				cStmtObject.setString(99, policyConfigVO.getSystemOfMedicine());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(99, null);
			}// end of else

			if (policyConfigVO.getAlternativeAML() != null) {
				cStmtObject.setBigDecimal(100,
						policyConfigVO.getAlternativeAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(100, 0);
			}

			if (policyConfigVO.getOpticalYN() != null) {
				cStmtObject.setString(101, policyConfigVO.getOpticalYN());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(101, null);
			}// end of else

			if (policyConfigVO.getOpticalCopay() != null) {
				cStmtObject.setString(102, policyConfigVO.getOpticalCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(102, null);
			}// end of else

			if (policyConfigVO.getFrameContactLensAML() != null) {
				cStmtObject.setBigDecimal(103,
						policyConfigVO.getFrameContactLensAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(103, 0);
			}

			if (policyConfigVO.getOpticalConsulCopay() != null) {
				cStmtObject.setString(104,
						policyConfigVO.getOpticalConsulCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(104, null);
			}// end of else

			if (policyConfigVO.getOpticalConsulAmount() != null) {
				cStmtObject.setBigDecimal(105,
						policyConfigVO.getOpticalConsulAmount());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(105, 0);
			}

			if (policyConfigVO.getOpticalNonNetworkRem() != null) {
				cStmtObject.setString(106,
						policyConfigVO.getOpticalNonNetworkRem());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(106, null);
			}// end of else

			if (policyConfigVO.getMaternityCopayGroupNumeric() != null) {
				cStmtObject.setBigDecimal(107,
						policyConfigVO.getMaternityCopayGroupNumeric());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(107, 0);
			}

			if (policyConfigVO.getInpatientcoverage() != null) {
				cStmtObject.setString(108,
						policyConfigVO.getInpatientcoverage());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(108, null);
			}

			if (policyConfigVO.getInpatientICUAML() != null) {
				cStmtObject.setBigDecimal(109,
						policyConfigVO.getInpatientICUAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(109, 0);
			}
			if (policyConfigVO.getLabsAndDiagnosticsAML() != null) {
				cStmtObject.setBigDecimal(110,
						policyConfigVO.getLabsAndDiagnosticsAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(110, 0);
			}

			if (policyConfigVO.getOpConsultationCopayList() != null) {
				cStmtObject.setBigDecimal(111,
						policyConfigVO.getOpConsultationCopayList());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setInt(111, 0);
			}// end of else

			if (policyConfigVO.getOpConsultationCopayListNum() != null) {
				cStmtObject.setBigDecimal(112,
						policyConfigVO.getOpConsultationCopayListNum());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(112, 0);
			}

			if (policyConfigVO.getEmergencyMaternityAML() != null) {
				cStmtObject.setBigDecimal(113,
						policyConfigVO.getEmergencyMaternityAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(113, 0);
			}

			if (policyConfigVO.getMaternityAnteNatalTests() != null) {
				cStmtObject.setString(114,
						policyConfigVO.getMaternityAnteNatalTests());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(114, null);
			}// end of else

			if (policyConfigVO.getEmergencyDentalAML() != null) {
				cStmtObject.setBigDecimal(115,
						policyConfigVO.getEmergencyDentalAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(115, 0);
			}
			if (policyConfigVO.getOpticalAML() != null) {
				cStmtObject.setBigDecimal(116, policyConfigVO.getOpticalAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(116, 0);
			}
			if (policyConfigVO.getEmergencyOpticalAML() != null) {
				cStmtObject.setBigDecimal(117,
						policyConfigVO.getEmergencyOpticalAML());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(117, 0);
			}
			if (policyConfigVO.getChronicCoverage() != null) {
				cStmtObject.setString(118, policyConfigVO.getChronicCoverage());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setString(118, null);
			}
			if (policyConfigVO.getChronicCopayDeductibleCopay() != null) {
				cStmtObject.setBigDecimal(119,
						policyConfigVO.getChronicCopayDeductibleCopay());
			}// end of if(bufferDetailVO.getBufferAmt() != null)
			else {
				cStmtObject.setInt(119, 0);
			}

			if (policyConfigVO.getChronicCopayDeductibleAmount() != null) {
				cStmtObject.setBigDecimal(120,
						policyConfigVO.getChronicCopayDeductibleAmount());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setInt(120, 0);
			}

			if (policyConfigVO.getChineseMedicine() != null) {
				cStmtObject.setString(121, policyConfigVO.getChineseMedicine());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setString(121, null);
			}

			if (policyConfigVO.getOsteopathyMedicine() != null) {
				cStmtObject.setString(122,
						policyConfigVO.getOsteopathyMedicine());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setString(122, null);
			}
			if (policyConfigVO.getHomeopathyMedicine() != null) {
				cStmtObject.setString(123,
						policyConfigVO.getHomeopathyMedicine());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setString(123, null);
			}

			if (policyConfigVO.getAyurvedaMedicine() != null) {
				cStmtObject
						.setString(124, policyConfigVO.getAyurvedaMedicine());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setString(124, null);
			}
			if (policyConfigVO.getAccupunctureMedicine() != null) {
				cStmtObject.setString(125,
						policyConfigVO.getAccupunctureMedicine());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setString(125, null);
			}
			if (policyConfigVO.getNaturopathyMedicine() != null) {
				cStmtObject.setString(126,
						policyConfigVO.getNaturopathyMedicine());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setString(126, null);
			}

			if (policyConfigVO.getUnaniMedicine() != null) {
				cStmtObject.setString(127, policyConfigVO.getUnaniMedicine());
			}// end of if(claimInwardDetailVO.getRequestedAmt() != null)
			else {
				cStmtObject.setString(127, null);
			}

			cStmtObject.registerOutParameter(128, Types.INTEGER);
			cStmtObject.execute();
			iResult = cStmtObject.getInt(128);

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
							"Error while closing the Statement in PolicyDAOImpl saveConfigInsuranceApprove()",
							sqlExp);
					throw new TTKException(sqlExp, "policy");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in PolicyDAOImpl saveConfigInsuranceApprove()",
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
	}// end of saveConfigInsuranceApprove

	/**
	 * bajaj This method returns the InsuranceApproveVO which contains Copay
	 * Amount configuration Details
	 * 
	 * @param long lngProdPolicySeqId
	 * @return InsuranceApproveVO which contains Domicilary configuration
	 *         Details i.e Flag Enr or POL || Product
	 * @exception throws TTKException
	 */

	public PolicyConfigVO getPlanDesignConfigData(long webBoardId)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		String strResult = "";
		PolicyConfigVO policyConfigVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strGetBenefitCoverage);
			cStmtObject.setLong(1, webBoardId);
			cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(3, OracleTypes.VARCHAR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(2);

			strResult = (String) cStmtObject.getObject(3);
			policyConfigVO = new PolicyConfigVO();

			if (rs != null) {
				while (rs.next()) {
					// policyConfigVO = new PolicyConfigVO();

					if (rs.getString("grp_prof_seq_id") != null) {
						policyConfigVO.setGroupProfileSeqID(rs
								.getLong("grp_prof_seq_id"));
					}

					// policyConfigVO.setBenefitTypeID(rs.getString("BENEFIT_TYPE_ID"));

					if (rs.getString("BENEFIT_TYPE_ID").equals("GRP")) {

						policyConfigVO.setGroupLevelYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));
						// policyConfigVO.setInitialWatiingPeriod(new
						// BigDecimal(rs.getString("initial_wat_period_days")));

						if (rs.getString("initial_wat_period_days") != null) {
							policyConfigVO
									.setInitialWatiingPeriod(new BigDecimal(
											rs.getString("initial_wat_period_days")));
						} else {
							policyConfigVO
									.setInitialWatiingPeriod(new BigDecimal("0"));
						}
						policyConfigVO
								.setNonNetworkRemCopayGroup(TTKCommon.checkNull(rs
										.getString("non_nwk_reimb_copay_perc")));
						policyConfigVO
								.setElectiveOutsideCover(TTKCommon.checkNull(rs
										.getString("ELECTIVE_AREA_COVER")));

						if (rs.getString("ELECTIVE_AREA_COVER_DAYS") != null) {
							policyConfigVO
									.setElectiveOutsideCoverDays(new BigDecimal(
											rs.getString("ELECTIVE_AREA_COVER_DAYS")));
						} else {
							policyConfigVO
									.setElectiveOutsideCoverDays(new BigDecimal(
											"0"));
						}

						if (rs.getString("transp_over_limit") != null) {
							policyConfigVO
									.setTransportaionOverseasLimit(new BigDecimal(
											rs.getString("transp_over_limit")));
						} else {
							policyConfigVO
									.setTransportaionOverseasLimit(new BigDecimal(
											"0"));
						}

						if (rs.getString("repatr_remain_limit") != null) {
							policyConfigVO
									.setRepatriationRemainsLimit(new BigDecimal(
											rs.getString("repatr_remain_limit")));
						} else {
							policyConfigVO
									.setRepatriationRemainsLimit(new BigDecimal(
											"0"));
						}

						policyConfigVO
								.setSpecialistConsultationReferal(TTKCommon.checkNull(rs
										.getString("work_rel_accident_cov")));
						if (rs.getString("emerg_eval_aml") != null) {
							policyConfigVO.setEmergencyEvalAML(new BigDecimal(
									rs.getString("emerg_eval_aml")));
						} else {
							policyConfigVO.setEmergencyEvalAML(new BigDecimal(
									"0"));
						}
						policyConfigVO
								.setInternationalMedicalAssis(TTKCommon.checkNull(rs
										.getString("inter_med_assistance")));
						policyConfigVO
								.setSpecialistConsultationReferalGroup(TTKCommon.checkNull(rs
										.getString("spec_conslt_by_referal")));

						if (rs.getString("PER_LIFE_AML") != null) {
							policyConfigVO.setPerLifeAML(new BigDecimal(rs
									.getString("PER_LIFE_AML")));
						} else {
							policyConfigVO.setPerLifeAML(new BigDecimal("0"));
						}

						policyConfigVO.setOutpatientCoverage(TTKCommon
								.checkNull(rs.getString("GRP_OUT_PAT_COVER")));
						policyConfigVO.setMaternityCopayGroup(TTKCommon
								.checkNull(rs.getString("GRP_MAT_COPAY_PERC")));
						policyConfigVO
								.setGroundTransportaionPerc(TTKCommon.checkNull(rs
										.getString("GRP_GRND_TRANS_UAE_COPAY_PERC")));

						if (rs.getString("GRP_GRND_TRANS_UAE_COPAY_AMNT") != null) {
							policyConfigVO
									.setGroundTransportaionNumeric(new BigDecimal(
											rs.getString("GRP_GRND_TRANS_UAE_COPAY_AMNT")));
						} else {
							policyConfigVO
									.setGroundTransportaionNumeric(new BigDecimal(
											"0"));
						}

						if (rs.getString("grp_mat_copay_amount") != null) {
							policyConfigVO
									.setMaternityCopayGroupNumeric(new BigDecimal(
											rs.getString("grp_mat_copay_amount")));
						} else {
							policyConfigVO
									.setMaternityCopayGroupNumeric(new BigDecimal(
											"0"));
						}

					}

					/* INPATIENT */
					if (rs.getString("BENEFIT_TYPE_ID").equals("IPT")) {

						policyConfigVO.setInsPatYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));

						if (rs.getString("annual_max_limit") != null) {
							policyConfigVO.setAnnualMaxLimit(new BigDecimal(rs
									.getString("annual_max_limit")));
						} else {
							policyConfigVO
									.setAnnualMaxLimit(new BigDecimal("0"));
						}

						policyConfigVO.setRoomType(TTKCommon.checkNull(rs
								.getString("room_type")));
						policyConfigVO.setCopay(TTKCommon.checkNull(rs
								.getString("copay_perc")));
						policyConfigVO.setCompanionBenefit(TTKCommon
								.checkNull(rs.getString("comp_benifit")));

						if (rs.getString("COMP_BENIFIT_AML_PER_NIGHT") != null) {
							policyConfigVO
									.setCompanionBenefitAMl(new BigDecimal(
											rs.getString("COMP_BENIFIT_AML_PER_NIGHT")));
						} else {
							policyConfigVO
									.setCompanionBenefitAMl(new BigDecimal("0"));
						}

						policyConfigVO.setInpatientCashBenefit(TTKCommon
								.checkNull(rs.getString("cash_benifit")));

						if (rs.getString("cash_benifit_aml") != null) {
							policyConfigVO.setCashBenefitAML(new BigDecimal(rs
									.getString("cash_benifit_aml")));
						} else {
							policyConfigVO
									.setCashBenefitAML(new BigDecimal("0"));
						}

						if (rs.getString("emerg_dental") != null) {
							policyConfigVO.setEmergencyDental(new BigDecimal(rs
									.getString("emerg_dental")));
						} else {
							policyConfigVO.setEmergencyDental(new BigDecimal(
									"0"));
						}

						if (rs.getString("emerg_maternity") != null) {
							policyConfigVO
									.setEmergencyMaternity(new BigDecimal(rs
											.getString("emerg_maternity")));
						} else {
							policyConfigVO
									.setEmergencyMaternity(new BigDecimal("0"));
						}

						policyConfigVO.setAmbulance(TTKCommon.checkNull(rs
								.getString("ambulance")));
						policyConfigVO.setInpatientICU(TTKCommon.checkNull(rs
								.getString("icu")));
						policyConfigVO.setInpatientcoverage(TTKCommon
								.checkNull(rs.getString("inpatient_coverage")));
						if (rs.getString("inpatient_icu_aml") != null) {
							policyConfigVO.setInpatientICUAML(new BigDecimal(rs
									.getString("inpatient_icu_aml")));
						} else {
							policyConfigVO.setInpatientICUAML(new BigDecimal(
									"0"));
						}

					}

					/* PHARMACY */
					if (rs.getString("BENEFIT_TYPE_ID").equals("PHM")) {

						policyConfigVO.setPharmacyYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));
						policyConfigVO.setcopaypharmacy(TTKCommon.checkNull(rs
								.getString("copay_perc")));

						if (rs.getString("annual_max_limit") != null) {
							policyConfigVO.setAmlPharmacy(new BigDecimal(rs
									.getString("annual_max_limit")));
						} else {
							policyConfigVO.setAmlPharmacy(new BigDecimal("0"));
						}

						if (rs.getString("chronic_aml") != null) {
							policyConfigVO.setChronicAML(new BigDecimal(rs
									.getString("chronic_aml")));
						} else {
							policyConfigVO.setChronicAML(new BigDecimal("0"));
						}
						policyConfigVO.setChronicPharmacyCopayPerc(TTKCommon
								.checkNull(rs
										.getString("CHRONIC_PHM_COPAY_PERC")));

						if (rs.getString("CHRONIC_PHM_COPAY_AMOUNT") != null) {
							policyConfigVO
									.setChronicPharmacyCopayNum(new BigDecimal(
											rs.getString("CHRONIC_PHM_COPAY_AMOUNT")));
						} else {
							policyConfigVO
									.setChronicPharmacyCopayNum(new BigDecimal(
											"0"));
						}

						if (rs.getString("pre_auth_limit_vip") != null) {
							policyConfigVO.setPreauthLimitVIP(new BigDecimal(rs
									.getString("pre_auth_limit_vip")));
						} else {
							policyConfigVO.setPreauthLimitVIP(new BigDecimal(
									"0"));
						}

						policyConfigVO
								.setNonNetworkRemCopay(TTKCommon.checkNull(rs
										.getString("non_nwk_reimb_copay_perc")));

						if (rs.getString("pre_auth_limit_non_vip") != null) {
							policyConfigVO
									.setPreauthLimitNonVIP(new BigDecimal(
											rs.getString("pre_auth_limit_non_vip")));
						} else {
							policyConfigVO
									.setPreauthLimitNonVIP(new BigDecimal("0"));
						}

					}

					/* LAB AND DIAGNOSTICS */
					if (rs.getString("BENEFIT_TYPE_ID").equals("LAB")) {

						policyConfigVO.setLabdiagYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));
						policyConfigVO.setCopayLab(TTKCommon.checkNull(rs
								.getString("copay_perc")));
						policyConfigVO
								.setNonNetworkRemCopayLab(TTKCommon.checkNull(rs
										.getString("non_nwk_reimb_copay_perc")));
						policyConfigVO.setOncologyTest(TTKCommon.checkNull(rs
								.getString("oncology_tests")));

						if (rs.getString("oncology_tests_aml") != null) {
							policyConfigVO.setOncologyTestAML(new BigDecimal(rs
									.getString("oncology_tests_aml")));
						} else {
							policyConfigVO.setOncologyTestAML(new BigDecimal(
									"0"));
						}

						policyConfigVO
								.setPreventiveScreeningDiabetics(TTKCommon.checkNull(rs
										.getString("prev_diabetic_cov")));
						policyConfigVO
								.setPreventiveScreenDiabeticsAnnual(TTKCommon.checkNull(rs
										.getString("prev_diabetic_cov_days")));
						policyConfigVO
								.setPreventiveScreeningDiabeticsAge(TTKCommon.checkNull(rs
										.getString("prev_diabetic_age_limit")));
						if (rs.getString("annual_max_limit") != null) {
							policyConfigVO
									.setLabsAndDiagnosticsAML(new BigDecimal(rs
											.getString("annual_max_limit")));
						} else {
							policyConfigVO
									.setLabsAndDiagnosticsAML(new BigDecimal(
											"0"));
						}

					}

					/* OP CONSULTATIONS */
					if (rs.getString("BENEFIT_TYPE_ID").equals("CON")) {

						policyConfigVO.setOpConsultYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));
						if (rs.getString("annual_max_limit") != null) {
							policyConfigVO.setConsultationAML(new BigDecimal(rs
									.getString("annual_max_limit")));
						} else {
							policyConfigVO.setConsultationAML(new BigDecimal(
									"0"));
						}

						policyConfigVO.setGpConsultationList(TTKCommon
								.checkNull(rs
										.getString("gp_consult_copay_perc")));

						if (rs.getString("gp_consult_copay_amount") != null) {
							policyConfigVO.setGpConsultationNum(new BigDecimal(
									rs.getString("gp_consult_copay_amount")));
						} else {
							policyConfigVO.setGpConsultationNum(new BigDecimal(
									"0"));
						}

						policyConfigVO.setSpecConsultationList(TTKCommon
								.checkNull(rs
										.getString("spec_consult_copay_perc")));

						if (rs.getString("spec_consult_copay_amount") != null) {
							policyConfigVO
									.setSpecConsultationNum(new BigDecimal(
											rs.getString("spec_consult_copay_amount")));
						} else {
							policyConfigVO
									.setSpecConsultationNum(new BigDecimal("0"));
						}

						if (rs.getString("phy_conslt_no_of_session") != null) {
							policyConfigVO
									.setPhysiotherapySession(new BigDecimal(
											rs.getString("phy_conslt_no_of_session")));
						} else {
							policyConfigVO
									.setPhysiotherapySession(new BigDecimal("0"));
						}

						if (rs.getString("phy_conslt_aml_limit") != null) {
							policyConfigVO
									.setPhysiotherapyAMLLimit(new BigDecimal(rs
											.getString("phy_conslt_aml_limit")));
						} else {
							policyConfigVO
									.setPhysiotherapyAMLLimit(new BigDecimal(
											"0"));
						}

						if (rs.getString("chronic_dise_no_of_consult") != null) {
							policyConfigVO
									.setChronicDiseaseConsults(new BigDecimal(
											rs.getString("chronic_dise_no_of_consult")));
						} else {
							policyConfigVO
									.setChronicDiseaseConsults(new BigDecimal(
											"0"));
						}

						if (rs.getString("chronic_dise_aml") != null) {
							policyConfigVO.setChronicDiseaseAML(new BigDecimal(
									rs.getString("chronic_dise_aml")));
						} else {
							policyConfigVO.setChronicDiseaseAML(new BigDecimal(
									"0"));
						}

						policyConfigVO.setChronicDiseaseCopay(TTKCommon
								.checkNull(rs
										.getString("chronic_dise_copay_perc")));

						if (rs.getString("chronic_dise_copay_amount") != null) {
							policyConfigVO
									.setChronicDiseaseDeductible(new BigDecimal(
											rs.getString("chronic_dise_copay_amount")));
						} else {
							policyConfigVO
									.setChronicDiseaseDeductible(new BigDecimal(
											"0"));
						}

						// policyConfigVO.setOpConsultationCopayList(TTKCommon.checkNull(rs.getString("consultation_copay_perc")));
						if (rs.getString("consultation_copay_perc") != null) {
							policyConfigVO
									.setOpConsultationCopayList(new BigDecimal(
											rs.getString("consultation_copay_perc")));
						} else {
							policyConfigVO
									.setOpConsultationCopayList(new BigDecimal(
											"0"));
						}

						if (rs.getString("consultation_copay_amount") != null) {
							policyConfigVO
									.setOpConsultationCopayListNum(new BigDecimal(
											rs.getString("consultation_copay_amount")));
						} else {
							policyConfigVO
									.setOpConsultationCopayListNum(new BigDecimal(
											"0"));
						}

					}

					/* MATERNITY */
					if (rs.getString("BENEFIT_TYPE_ID").equals("MAT")) {

						policyConfigVO.setMaternityYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));

						if (rs.getString("nor_delivery_aml") != null) {
							policyConfigVO.setNormalDeliveryAML(new BigDecimal(
									rs.getString("nor_delivery_aml")));
						} else {
							policyConfigVO.setNormalDeliveryAML(new BigDecimal(
									"0"));
						}

						if (rs.getString("c_section_aml") != null) {
							policyConfigVO
									.setMaternityCsectionAML(new BigDecimal(rs
											.getLong("c_section_aml")));
						} else {
							policyConfigVO
									.setMaternityCsectionAML(new BigDecimal("0"));
						}

						policyConfigVO.setDayCoverage(TTKCommon.checkNull(rs
								.getString("day_1_coverage")));
						policyConfigVO
								.setDayCoverageVaccination(TTKCommon.checkNull(rs
										.getString("MAT_DAY_1_VACC_COVER")));

						if (rs.getString("mat_compl_aml") != null) {
							policyConfigVO
									.setMaternityComplicationAML(new BigDecimal(
											rs.getString("mat_compl_aml")));
						} else {
							policyConfigVO
									.setMaternityComplicationAML(new BigDecimal(
											"0"));
						}
						if (rs.getString("mat_emer_maternity_aml") != null) {
							policyConfigVO
									.setEmergencyMaternityAML(new BigDecimal(
											rs.getString("mat_emer_maternity_aml")));
						} else {
							policyConfigVO
									.setEmergencyMaternityAML(new BigDecimal(
											"0"));
						}
						policyConfigVO
								.setMaternityAnteNatalTests(TTKCommon.checkNull(rs
										.getString("maternity_antenatal_tests")));

						if (rs.getString("mat_conslt_no_of_consult") != null) {
							policyConfigVO
									.setNoOfmaternityConsults(new BigDecimal(
											rs.getString("mat_conslt_no_of_consult")));
						} else {
							policyConfigVO
									.setNoOfmaternityConsults(new BigDecimal(
											"0"));
						}

						policyConfigVO.setMaternityConsultations(TTKCommon
								.checkNull(rs
										.getString("mat_consult_copay_perc")));

						if (rs.getString("mat_consult_copay_amount") != null) {
							policyConfigVO
									.setMaternityConsultationsNum(new BigDecimal(
											rs.getString("mat_consult_copay_amount")));
						} else {
							policyConfigVO
									.setMaternityConsultationsNum(new BigDecimal(
											"0"));
						}

					}

					/* DENTAL */

					if (rs.getString("BENEFIT_TYPE_ID").equals("DNT")) {

						policyConfigVO.setDentalYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));

						if (rs.getString("annual_max_limit") != null) {
							policyConfigVO.setDentalAML(new BigDecimal(rs
									.getString("annual_max_limit")));
						} else {
							policyConfigVO.setDentalAML(new BigDecimal("0"));
						}

						policyConfigVO.setDentalCopay(TTKCommon.checkNull(rs
								.getString("copay_perc")));

						if (rs.getString("copay_amount") != null) {
							policyConfigVO.setDentalDeductible(new BigDecimal(
									rs.getString("copay_amount")));
						} else {
							policyConfigVO.setDentalDeductible(new BigDecimal(
									"0"));
						}

						if (rs.getString("cleaning_aml") != null) {
							policyConfigVO.setCleaningAML(new BigDecimal(rs
									.getString("cleaning_aml")));
						} else {
							policyConfigVO.setCleaningAML(new BigDecimal("0"));
						}

						if (rs.getString("oth_aml") != null) {
							policyConfigVO.setOrthodonticsAML(new BigDecimal(rs
									.getString("oth_aml")));
						} else {
							policyConfigVO.setOrthodonticsAML(new BigDecimal(
									"0"));
						}

						policyConfigVO.setDentalNonNetworkRem(TTKCommon
								.checkNull(rs.getString("non_nwk_remb")));

						if (rs.getString("emergency_dental_aml") != null) {
							policyConfigVO
									.setEmergencyDentalAML(new BigDecimal(rs
											.getString("emergency_dental_aml")));
						} else {
							policyConfigVO
									.setEmergencyDentalAML(new BigDecimal("0"));
						}

					}

					/* CHRONIC */
					if (rs.getString("BENEFIT_TYPE_ID").equals("CHO")) {
						policyConfigVO.setChronicYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));

						if (rs.getString("annual_max_limit") != null) {
							policyConfigVO.setChronicAMLNum(new BigDecimal(rs
									.getString("annual_max_limit")));
						} else {
							policyConfigVO
									.setChronicAMLNum(new BigDecimal("0"));
						}

						if (rs.getString("pharmacy_aml") != null) {
							policyConfigVO.setPharmacyAML(new BigDecimal(rs
									.getString("pharmacy_aml")));
						} else {
							policyConfigVO.setPharmacyAML(new BigDecimal("0"));
						}

						policyConfigVO
								.setPharmacyAMLCopay(TTKCommon.checkNull(rs
										.getString("pharmacy_copay_perc")));

						if (rs.getString("pharmacy_copay_amount") != null) {
							policyConfigVO.setPharmacyAMLAmount(new BigDecimal(
									rs.getString("pharmacy_copay_amount")));
						} else {
							policyConfigVO.setPharmacyAMLAmount(new BigDecimal(
									"0"));
						}

						if (rs.getString("lab_diagnostic") != null) {
							policyConfigVO.setChronicLabDiag(new BigDecimal(rs
									.getString("lab_diagnostic")));
						} else {
							policyConfigVO
									.setChronicLabDiag(new BigDecimal("0"));
						}

						policyConfigVO
								.setChronicLabDiagCopay(TTKCommon.checkNull(rs
										.getString("lab_diagnostic_copay_perc")));

						if (rs.getString("lab_diagnostic_copay_amount") != null) {
							policyConfigVO
									.setChronicLabDiagAmount(new BigDecimal(
											rs.getString("lab_diagnostic_copay_amount")));
						} else {
							policyConfigVO
									.setChronicLabDiagAmount(new BigDecimal("0"));
						}

						if (rs.getString("consultation") != null) {
							policyConfigVO
									.setChronicConsultationAML(new BigDecimal(
											rs.getString("consultation")));
						} else {
							policyConfigVO
									.setChronicConsultationAML(new BigDecimal(
											"0"));
						}

						policyConfigVO.setChronicConsultationCopay(TTKCommon
								.checkNull(rs
										.getString("consultation_copay_perc")));

						if (rs.getString("consultation_copay_amount") != null) {
							policyConfigVO
									.setChronicConsultation(new BigDecimal(
											rs.getString("consultation_copay_amount")));
						} else {
							policyConfigVO
									.setChronicConsultation(new BigDecimal("0"));
						}

						policyConfigVO.setChronicCoverage(TTKCommon
								.checkNull(rs.getString("chronic_coverage")));
						// policyConfigVO.setChronicCopayDeductibleCopay(TTKCommon.checkNull(rs.getString("copay_perc")));
						if (rs.getString("copay_amount") != null) {
							policyConfigVO
									.setChronicCopayDeductibleCopay(new BigDecimal(
											rs.getString("copay_perc")));
						} else {
							policyConfigVO
									.setChronicCopayDeductibleCopay(new BigDecimal(
											"0"));
						}

						if (rs.getString("copay_amount") != null) {
							policyConfigVO
									.setChronicCopayDeductibleAmount(new BigDecimal(
											rs.getString("copay_amount")));
						} else {
							policyConfigVO
									.setChronicCopayDeductibleAmount(new BigDecimal(
											"0"));
						}

					}

					/* PED */
					if (rs.getString("BENEFIT_TYPE_ID").equals("PED")) {

						policyConfigVO.setPedYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));
						policyConfigVO.setCoveredPED(TTKCommon.checkNull(rs
								.getString("ped_covered")));

						if (rs.getString("annual_max_limit") != null) {
							policyConfigVO.setPedAML(new BigDecimal(rs
									.getString("annual_max_limit")));
						} else {
							policyConfigVO.setPedAML(new BigDecimal("0"));
						}

						policyConfigVO.setPedCopayDeductible(TTKCommon
								.checkNull(rs.getString("copay_perc")));

						if (rs.getString("copay_amount") != null) {
							policyConfigVO.setPedDeductible(new BigDecimal(rs
									.getString("copay_amount")));
						} else {
							policyConfigVO
									.setPedDeductible(new BigDecimal("0"));
						}

					}

					/* PSHYCHIATRY */
					if (rs.getString("BENEFIT_TYPE_ID").equals("PSY")) {
						policyConfigVO.setPsychiatryYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));

						if (rs.getString("ip_aml") != null) {
							policyConfigVO.setInpatientAML(new BigDecimal(rs
									.getString("ip_aml")));
						} else {
							policyConfigVO.setInpatientAML(new BigDecimal("0"));
						}

						if (rs.getString("ip_days") != null) {
							policyConfigVO.setNoofInpatientDays(new BigDecimal(
									rs.getString("ip_days")));
						} else {
							policyConfigVO.setNoofInpatientDays(new BigDecimal(
									"0"));
						}

						if (rs.getString("op_aml") != null) {
							policyConfigVO.setOutpatientAML(new BigDecimal(rs
									.getString("op_aml")));
						} else {
							policyConfigVO
									.setOutpatientAML(new BigDecimal("0"));
						}

						if (rs.getString("op_no_of_consult") != null) {
							policyConfigVO
									.setNoofOutpatientConsul(new BigDecimal(rs
											.getString("op_no_of_consult")));
						} else {
							policyConfigVO
									.setNoofOutpatientConsul(new BigDecimal("0"));
						}

						policyConfigVO.setPsychiatryCopayDeduc(TTKCommon
								.checkNull(rs.getString("copay_perc")));

						if (rs.getString("copay_amount") != null) {
							policyConfigVO
									.setPsychiatryDeductible(new BigDecimal(rs
											.getString("copay_amount")));
						} else {
							policyConfigVO
									.setPsychiatryDeductible(new BigDecimal("0"));
						}

					}
					/* ALTERNATIVE MEDICINES */
					if (rs.getString("BENEFIT_TYPE_ID").equals("ALT")) {

						policyConfigVO.setOthersYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));
						policyConfigVO
								.setSystemOfMedicine(TTKCommon.checkNull(rs
										.getString("ALTR_MED_CHIROPCTRY")));
						policyConfigVO.setOsteopathyMedicine(TTKCommon
								.checkNull(rs.getString("ALTR_MED_OSTEPTHY")));
						policyConfigVO
								.setHomeopathyMedicine(TTKCommon.checkNull(rs
										.getString("ALTR_MED_HOMEOPATHY")));
						policyConfigVO.setAyurvedaMedicine(TTKCommon
								.checkNull(rs.getString("ALTR_MED_AYRVED")));
						policyConfigVO
								.setAccupunctureMedicine(TTKCommon.checkNull(rs
										.getString("ALTR_MED_ACCUPUNTURE")));
						policyConfigVO
								.setNaturopathyMedicine(TTKCommon.checkNull(rs
										.getString("ALTR_MED_NATUROPTHY")));
						policyConfigVO.setUnaniMedicine(TTKCommon.checkNull(rs
								.getString("ALTR_MED_UNANI")));
						policyConfigVO.setChineseMedicine(TTKCommon
								.checkNull(rs.getString("ALTR_MED_CHINSE")));

						if (rs.getString("annual_max_limit") != null) {
							policyConfigVO.setAlternativeAML(new BigDecimal(rs
									.getString("annual_max_limit")));
						} else {
							policyConfigVO
									.setAlternativeAML(new BigDecimal("0"));
						}

					}

					/* OPTICAL */
					if (rs.getString("BENEFIT_TYPE_ID").equals("OPT")) {

						policyConfigVO.setOpticalYN(TTKCommon.checkNull(rs
								.getString("covered_yn")));
						policyConfigVO.setOpticalCopay(TTKCommon.checkNull(rs
								.getString("copay_perc")));

						if (rs.getString("frm_contact_lense_aml") != null) {
							policyConfigVO
									.setFrameContactLensAML(new BigDecimal(rs
											.getString("frm_contact_lense_aml")));
						} else {
							policyConfigVO
									.setFrameContactLensAML(new BigDecimal("0"));
						}

						policyConfigVO.setOpticalConsulCopay(TTKCommon
								.checkNull(rs
										.getString("consultation_copay_perc")));

						if (rs.getString("consultation_copay_amount") != null) {
							policyConfigVO
									.setOpticalConsulAmount(new BigDecimal(
											rs.getString("consultation_copay_amount")));
						} else {
							policyConfigVO
									.setOpticalConsulAmount(new BigDecimal("0"));
						}

						policyConfigVO.setOpticalNonNetworkRem(TTKCommon
								.checkNull(rs.getString("non_nwk_remb")));
						if (rs.getString("annual_max_limit") != null) {
							policyConfigVO.setOpticalAML(new BigDecimal(rs
									.getString("annual_max_limit")));
						} else {
							policyConfigVO.setOpticalAML(new BigDecimal("0"));
						}
						if (rs.getString("emergency_optical_aml") != null) {
							policyConfigVO
									.setEmergencyOpticalAML(new BigDecimal(rs
											.getString("emergency_optical_aml")));
						} else {
							policyConfigVO
									.setEmergencyOpticalAML(new BigDecimal("0"));
						}

					}

					// denial process
				}// end of while(rs.next())
			}// end of (rs != null)
			policyConfigVO.setShowflag(strResult);

			return policyConfigVO; // TODO Auto-generated method stub
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
							"Error while closing the Resultset in PolicyDAOImpl getPlanDesignConfigData()",
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
								"Error while closing the Statement in PolicyDAOImpl getPlanDesignConfigData()",
								sqlExp);
						throw new TTKException(sqlExp, "policy");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in PolicyDAOImpl getPlanDesignConfigData()",
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
	}// end of getDomicilaryConfig(long lngProdPolicySeqId)

	public PolicyConfigVO saveGenerateQuote(PolicyConfigVO policyConfigVO)
			throws TTKException {
		Long iResult = null;
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveGenerateQuote);

			if (policyConfigVO.getGroupProfileSeqID() != null) {
				cStmtObject.setLong(1, policyConfigVO.getGroupProfileSeqID());
			} else {
				cStmtObject.setLong(1, 0);
			}// end of else
			cStmtObject.setBigDecimal(2, policyConfigVO.getAdminCost());
			cStmtObject
					.setBigDecimal(3, policyConfigVO.getManagementExpenses());
			cStmtObject.setBigDecimal(4, policyConfigVO.getCommission());
			cStmtObject.setBigDecimal(5, policyConfigVO.getCostOfCapital());
			cStmtObject.setBigDecimal(6, policyConfigVO.getProfitMargin());
			cStmtObject.setString(9, policyConfigVO.getReinSwitch());
			cStmtObject.setString(10, policyConfigVO.getSignatory());
			cStmtObject.registerOutParameter(7, OracleTypes.VARCHAR);
			cStmtObject.registerOutParameter(8, OracleTypes.VARCHAR);
			// cStmtObject.registerOutParameter(9,OracleTypes.CURSOR);
			cStmtObject.execute();
			String finalamount = (String) cStmtObject.getString(7);
			policyConfigVO.setFinalAmount(finalamount);
			policyConfigVO.setGenerateflag(cStmtObject.getString(8));

			return policyConfigVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Statement in InsuranceDAOImpl deleteInsuranceCompany()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Connection in InsuranceDAOImpl deleteInsuranceCompany()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally

	}// end of deleteInsuranceCompany(String strInsSeqID)

	public Object[] selectGenerateQuote(long webBoardId) throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		// PolicyConfigVO policyConfigVO =null;
		PolicyConfigVO policyConfigVO = new PolicyConfigVO();
		Object[] premiumProposal = new Object[3];
		ArrayList<PolicyConfigVO> PremiumFirst = new ArrayList<PolicyConfigVO>();
		ArrayList<PolicyConfigVO> PremiumSecond = new ArrayList<PolicyConfigVO>();

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSelectGenerateQuote);
			cStmtObject.setLong(1, webBoardId);
			cStmtObject.registerOutParameter(2, OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(3, OracleTypes.CURSOR);
			cStmtObject.registerOutParameter(4, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(2);
			rs1 = (java.sql.ResultSet) cStmtObject.getObject(4);
			rs2 = (java.sql.ResultSet) cStmtObject.getObject(3);
			if (rs != null) {

				while (rs.next()) {

					String ageQuote = rs.getString("age_band") == null ? ""
							: rs.getString("age_band");
					String censusQuote = rs.getString("mid_emp") == null ? ""
							: rs.getString("mid_emp");
					String premiumQuote = rs.getString("final_amt") == null ? ""
							: rs.getString("final_amt");
					String totalPremiumQuote = rs.getString("Premium") == null ? ""
							: rs.getString("Premium");
					String memberType = rs.getString("member_type") == null ? ""
							: rs.getString("member_type");

					PremiumFirst.add(new PolicyConfigVO(ageQuote, censusQuote,
							premiumQuote, totalPremiumQuote, memberType));

				}// end of while(rs.next())
			}// End of if(rs!=null)
			if (rs1 != null) {

				while (rs1.next()) {

					policyConfigVO.setGroupProfileSeqID(webBoardId);
					policyConfigVO.setAdminCost(TTKCommon.getBigDecimal(rs1
							.getString("admin_perc")));
					policyConfigVO.setManagementExpenses(TTKCommon
							.getBigDecimal(rs1.getString("mangmnt_perc")));
					policyConfigVO.setCommission(TTKCommon.getBigDecimal(rs1
							.getString("comission_perc")));
					policyConfigVO.setCostOfCapital(TTKCommon.getBigDecimal(rs1
							.getString("cost_cap_perc")));
					policyConfigVO.setProfitMargin(TTKCommon.getBigDecimal(rs1
							.getString("profit_margin")));
					policyConfigVO.setReinSwitch(TTKCommon.checkNull(rs1
							.getString("rein_swtch")));
					policyConfigVO.setLessFourthousand(TTKCommon.checkNull(rs1
							.getString("less_4000")));
					policyConfigVO.setMoreFourthousand(TTKCommon.checkNull(rs1
							.getString("more_4000")));
					policyConfigVO.setSignatory(TTKCommon.checkNull(rs1
							.getString("sign_name_id")));

				}// end of while(rs.next())
			}// End of if(rs!=null)

			if (rs2 != null) {
				while (rs2.next()) {

					String ageQuoteFamily = rs2.getString("age_band") == null ? ""
							: rs2.getString("age_band");
					String censusQuoteFamily = rs2.getString("mid_emp") == null ? ""
							: rs2.getString("mid_emp");
					String premiumQuoteFamily = rs2.getString("final_amt") == null ? ""
							: rs2.getString("final_amt");
					String totalPremiumQuoteFamily = rs2.getString("Premium") == null ? ""
							: rs2.getString("Premium");
					String memberTypeFamily = rs2.getString("member_type") == null ? ""
							: rs2.getString("member_type");
					String generateflag = "y";

					PremiumSecond.add(new PolicyConfigVO(ageQuoteFamily,
							censusQuoteFamily, premiumQuoteFamily,
							totalPremiumQuoteFamily, memberTypeFamily,
							generateflag));

				}// end of while(rs.next())
			}// End of if(rs!=null)

			premiumProposal[0] = policyConfigVO;
			premiumProposal[1] = PremiumFirst;
			premiumProposal[2] = PremiumSecond;

			return premiumProposal;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null) {
						rs.close();
					}
					if (rs1 != null) {
						rs1.close();
					}
					if (rs2 != null) {
						rs2.close();

					}
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (cStmtObject != null) {
							cStmtObject.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				rs1 = null;
				rs2 = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}

	// bikki
	public ArrayList swSavePricingList(InsPricingVO insPricingVO)
			throws TTKException {
		Long iResult = null;
		ArrayList al = new ArrayList();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strswSavePricingList);

			if (insPricingVO.getGroupProfileSeqID() != null) {
				cStmtObject.setLong(1, insPricingVO.getGroupProfileSeqID());
			} else {
				cStmtObject.setBigDecimal(1, new BigDecimal(0));
			}// end of else
			cStmtObject.setString(2, insPricingVO.getPricingRefno());
			cStmtObject.setString(3, insPricingVO.getRenewalYN());
			cStmtObject.setString(4, insPricingVO.getClientCode());
			cStmtObject.setString(5, insPricingVO.getClientName());

			cStmtObject.setString(6, insPricingVO.getPreviousPolicyNo());

			if (insPricingVO.getCoverStartDate() != null) {
				cStmtObject.setTimestamp(7, new Timestamp(insPricingVO
						.getCoverStartDate().getTime()));// LETTER_DATE
			}// end of if(batchVO.getLetterDate() != null)
			else {
				cStmtObject.setTimestamp(7, null);// LETTER_DATE
			}// end of else
			if (insPricingVO.getCoverEndDate() != null) {
				cStmtObject.setTimestamp(8, new Timestamp(insPricingVO
						.getCoverEndDate().getTime()));// LETTER_DATE
			}// end of if(batchVO.getLetterDate() != null)
			else {
				cStmtObject.setTimestamp(8, null);// LETTER_DATE
			}// end of else
			if(insPricingVO.getTotalCovedLives() == null || insPricingVO.getTotalCovedLives().equals("")){
				cStmtObject.setLong(9, 0);
			}
			else{
				cStmtObject.setLong(9, Long.parseLong(insPricingVO.getTotalCovedLives().toString()));
			}
			

			cStmtObject.setString(10,
					insPricingVO.getAdministrativeServiceType());
			cStmtObject.setString(11, insPricingVO.getInsuranceCompanyName());
			cStmtObject.setString(12, insPricingVO.getIncomeGroup());
			//System.out.println("Save-----Previous_Product_Name==========="+insPricingVO.getPreviousProductName());
			cStmtObject.setString(13, insPricingVO.getPreviousProductName());
			cStmtObject.setString(14, insPricingVO.getInpatientBenefit());
			cStmtObject.setString(15, insPricingVO.getOutpatientBenefit());
			cStmtObject.setString(16, insPricingVO.getMaxBenifitList());
			cStmtObject.setString(17, insPricingVO.getMaxBeneLimitOth());

			cStmtObject.setString(18, insPricingVO.getNetworkList());
			cStmtObject.setString(19, insPricingVO.getInpatientAreaOfCover());
			cStmtObject.setString(20,
					insPricingVO.getInpatientAreaOfCoverVariations());

			cStmtObject.setString(21, insPricingVO.getLoadingForInpatient());
			System.out
					.println("Loading for inpatient area of cover variations=====> "
							+ insPricingVO.getLoadingForInpatient());

			cStmtObject.setString(22, insPricingVO.getCommentsInpatient());

			cStmtObject.setString(23, insPricingVO.getRoomType1());

			cStmtObject.setString(24, insPricingVO.getInpatientCopay());
			cStmtObject.setString(25, insPricingVO.getMaxChildAgeLimit());
			cStmtObject.setString(26, insPricingVO.getCompanionCharges());

			cStmtObject.setString(27, insPricingVO.getOutpatientAreaOfCover());
			cStmtObject.setString(28,
					insPricingVO.getOutpatientAreaOfCoverVariations());
			cStmtObject.setString(29, insPricingVO.getLoadingForOutpatient());
			cStmtObject.setString(30, insPricingVO.getCommentsOutpatient());

			cStmtObject.setString(31,
					insPricingVO.getAdditionalHospitalCoverage());
			cStmtObject.setString(32, insPricingVO.getHospitalNameOth());
			cStmtObject.setString(33, insPricingVO.getLoadingForAddHospital());
			cStmtObject.setString(34, insPricingVO.getConsultationCD());
			cStmtObject.setString(35, insPricingVO.getDrugsLimit());

			cStmtObject.setString(36, insPricingVO.getDrugsCopay());
			cStmtObject.setString(37, insPricingVO.getDrugsLimitOth());

			cStmtObject.setString(38, insPricingVO.getLaboratoryCD());
			cStmtObject.setString(39, insPricingVO.getPhysiotherapyLimit());
			cStmtObject.setString(40, insPricingVO.getPhysiotherapyCopay());
			cStmtObject.setString(41, insPricingVO.getDinpatientCopay());
			cStmtObject.setString(42, insPricingVO.getCommentsDirect());
			cStmtObject.setString(43, insPricingVO.getMaternityInpatient());
			cStmtObject.setString(44, insPricingVO.getMaternityOutpatient());

			if (!("").equals(insPricingVO.getTotalLivesMaternity())) {
				cStmtObject
						.setString(45, insPricingVO.getTotalLivesMaternity());
			} else {
				cStmtObject.setString(45, null);
			}

			cStmtObject.setString(46, insPricingVO.getMaternityPricing());
			cStmtObject.setString(47, insPricingVO.getNormalDeliveryLimit());
			cStmtObject.setString(48, insPricingVO.getNormalDeliveryLimitOth());
			cStmtObject.setString(49, insPricingVO.getCSectionLimit());
			cStmtObject.setString(50, insPricingVO.getCSectionLimitOth());

			cStmtObject
					.setString(51, insPricingVO.getMaternityInpatientCopay());
			cStmtObject.setString(52, insPricingVO.getComments());
			cStmtObject.setString(53,
					insPricingVO.getAntiSelectionLoadInpatient());
			cStmtObject.setString(54, insPricingVO.getCommentsAntiInpatient());
			cStmtObject.setString(55,
					insPricingVO.getAntiSelectionLoadOutpatient());
			cStmtObject.setString(56, insPricingVO.getCommentsAntiOutpatient());
			cStmtObject.setString(57, insPricingVO.getTrendFactor());

			// pdf
			cStmtObject.setString(58, insPricingVO.getAttachmentname1());
			byte[] iStream1 = insPricingVO.getSourceAttchments1().getFileData();
			cStmtObject.setBytes(59, iStream1);

			cStmtObject.setString(60, insPricingVO.getAttachmentname2());
			byte[] iStream2 = insPricingVO.getSourceAttchments2().getFileData();
			cStmtObject.setBytes(61, iStream2);

			cStmtObject.setString(62, insPricingVO.getAttachmentname3());
			byte[] iStream3 = insPricingVO.getSourceAttchments3().getFileData();
			cStmtObject.setBytes(63, iStream3);

			cStmtObject.setString(64, insPricingVO.getAttachmentname4());
			byte[] iStream4 = insPricingVO.getSourceAttchments4().getFileData();
			cStmtObject.setBytes(65, iStream4);

			cStmtObject.setString(66, insPricingVO.getAttachmentname5());
			byte[] iStream5 = insPricingVO.getSourceAttchments5().getFileData();
			cStmtObject.setBytes(67, iStream5);

			cStmtObject.setString(68,
					insPricingVO.getMaternityOutpatientVisits());
			cStmtObject.setString(69,
					insPricingVO.getMaternityOutpatientCopay());

			cStmtObject
					.setString(70, insPricingVO.getPremiumRefundApplicable());

			cStmtObject.setString(71, insPricingVO.getLogicForPremiumRefund());
			cStmtObject.setString(72, insPricingVO.getAuthorityType());

			cStmtObject.setString(73, insPricingVO.getExistCompProd1());
			cStmtObject.setString(74, insPricingVO.getExistCompProd2());
			cStmtObject.setString(75, insPricingVO.getPortfloLevel());
			cStmtObject.registerOutParameter(76, Types.INTEGER);
			cStmtObject.registerOutParameter(77, Types.VARCHAR);
			cStmtObject.registerOutParameter(78, Types.VARCHAR);
			cStmtObject.setString(79, insPricingVO.getHosComments());
			cStmtObject
					.setString(80, insPricingVO.getInsuranceCompanyNameOth());
			cStmtObject.setLong(81, insPricingVO.getAddedBy());

			cStmtObject.registerOutParameter(1, Types.NUMERIC);
			cStmtObject.registerOutParameter(2, Types.VARCHAR);
			cStmtObject.registerOutParameter(20, Types.VARCHAR);
			cStmtObject.registerOutParameter(21, Types.VARCHAR);
			cStmtObject.registerOutParameter(22, Types.VARCHAR);
			cStmtObject.registerOutParameter(25, Types.VARCHAR);
			cStmtObject.registerOutParameter(26, Types.VARCHAR);
			cStmtObject.registerOutParameter(27, Types.VARCHAR);
			cStmtObject.registerOutParameter(28, Types.VARCHAR);
			cStmtObject.registerOutParameter(29, Types.VARCHAR);
			cStmtObject.registerOutParameter(30, Types.VARCHAR);
			cStmtObject.registerOutParameter(31, Types.VARCHAR);
			cStmtObject.registerOutParameter(33, Types.VARCHAR);
			cStmtObject.registerOutParameter(41, Types.VARCHAR);
			cStmtObject.registerOutParameter(42, Types.VARCHAR);
			cStmtObject.registerOutParameter(43, Types.VARCHAR);
			cStmtObject.registerOutParameter(44, Types.VARCHAR);
			cStmtObject.registerOutParameter(79, Types.VARCHAR);
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getBigDecimal(1).longValue();

			String errorCode = cStmtObject.getString(77);
			String errorMsg = cStmtObject.getString(78);
			al.add(iResult);
			al.add(errorCode);
			al.add(errorMsg);

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			// Nested Try Catch to ensure resource closure
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl deleteInsuranceCompany()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl deleteInsuranceCompany()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return al;
	}

	public String[] callDbValidation(InsPricingVO insPricingVO)
			throws TTKException // Long lpricingSeqId
	{
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(STR_VALIDATION_INPUT_SCREEN1);
			if (insPricingVO.getGroupProfileSeqID() == null) {
				cStmtObject.setBigDecimal(1, new BigDecimal(0));

			} else {
				cStmtObject.setLong(1, insPricingVO.getGroupProfileSeqID());
			}

			cStmtObject.setString(2, insPricingVO.getClientCode());
			cStmtObject.setString(3, insPricingVO.getPreviousPolicyNo());
			cStmtObject.setDate(4, new Date(insPricingVO.getCoverStartDate()
					.getTime()));
			cStmtObject.setDate(5, new Date(insPricingVO.getCoverEndDate()
					.getTime()));
			cStmtObject.setString(6, insPricingVO.getIncomeGroup());
			cStmtObject.setString(7, insPricingVO.getPreviousProductName());
			cStmtObject.setString(8, insPricingVO.getExistCompProd1());
			cStmtObject.setString(9, insPricingVO.getExistCompProd2());
			cStmtObject.setString(10, insPricingVO.getPortfloLevel());

			cStmtObject.registerOutParameter(11, Types.VARCHAR);
			cStmtObject.registerOutParameter(12, Types.VARCHAR);
			cStmtObject.registerOutParameter(13, Types.VARCHAR);
			cStmtObject.registerOutParameter(1, Types.NUMERIC);
			cStmtObject.execute();
			conn.commit();

			String[] valStr = new String[3];
			valStr[0] = (String) cStmtObject.getObject(11);
			valStr[1] = (String) cStmtObject.getObject(12);
			valStr[2] = (String) cStmtObject.getObject(13);
			log.info("valStr[0]::" + valStr[0] + "  valStr[1]::" + valStr[1]
					+ "  valStr[2]::" + valStr[2]);

			return valStr;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null) {
						rs.close();

					}
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (cStmtObject != null) {
							cStmtObject.close();
							log.info("connection is closed selectPricingList");
						}

					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
								log.info("connection is closed selectPricingList");
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	public InsPricingVO swSelectPricingList(Long lpricingSeqId)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		InsPricingVO insPricingVO = new InsPricingVO();
		InputStream iStream1 = new ByteArrayInputStream(
				new String("").getBytes());
		InputStream iStream2 = new ByteArrayInputStream(
				new String("").getBytes());
		InputStream iStream3 = new ByteArrayInputStream(
				new String("").getBytes());
		InputStream iStream4 = new ByteArrayInputStream(
				new String("").getBytes());
		InputStream iStream5 = new ByteArrayInputStream(
				new String("").getBytes());

		byte[] blobAsBytes = null;
		Blob blob1 = null;
		Blob blob2 = null;
		Blob blob3 = null;
		Blob blob4 = null;
		Blob blob5 = null;

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strswSelectPricingList);
			cStmtObject.setLong(1, lpricingSeqId);
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(2);
			if (rs != null) {
				while (rs.next()) {
					insPricingVO.setGroupProfileSeqID(lpricingSeqId);
					insPricingVO.setRenewalYN(TTKCommon.checkNull(rs
							.getString("RENEWAL_YN")));
					insPricingVO.setPricingRefno(TTKCommon.checkNull(rs
							.getString("REF_NO")));
					insPricingVO.setClientCode(TTKCommon.checkNull(rs
							.getString("CLIENT_CODE")));
					insPricingVO.setClientName(TTKCommon.checkNull(rs
							.getString("CLIENT_NAME")));
					insPricingVO.setPreviousPolicyNo(TTKCommon.checkNull(rs
							.getString("PREV_POLICY_NO")));

					insPricingVO.setCoverStartDate(rs
							.getString("COVRG_START_DATE") != null ? new Date(
							rs.getTimestamp("COVRG_START_DATE").getTime())
							: null);
					insPricingVO
							.setCoverEndDate(rs.getString("COVRG_END_DATE") != null ? new Date(
									rs.getTimestamp("COVRG_END_DATE").getTime())
									: null);
					// insPricingVO.setCoverStartDate(new
					// Date(rs.getTimestamp("COVRG_START_DATE").getTime()));
					// insPricingVO.setCoverEndDate(new
					// Date(rs.getTimestamp("COVRG_END_DATE").getTime()));
					insPricingVO.setTotalCovedLives(TTKCommon.checkNull(rs
							.getString("TOT_COV_LIVES")));

					insPricingVO.setAdministrativeServiceType(TTKCommon
							.checkNull(rs.getString("ADMIN_SERV_TYP")));

					insPricingVO.setInsuranceCompanyName(TTKCommon.checkNull(rs
							.getString("INS_COMP_NAME")));

					insPricingVO.setIncomeGroup(TTKCommon.checkNull(rs
							.getString("INCOME_GRP")));
					//System.out.println("Previous_Product_Name==========="+rs.getString("PREV_PROD_NUMBER"));
					insPricingVO.setPreviousProductName(TTKCommon.checkNull(rs
							.getString("PREV_PROD_NUMBER")));
					insPricingVO.setInpatientBenefit(TTKCommon.checkNull(rs
							.getString("IP_COVR")));
					insPricingVO.setOutpatientBenefit(TTKCommon.checkNull(rs
							.getString("OP_COVR")));
					insPricingVO.setMaxBenifitList(TTKCommon.checkNull(rs
							.getString("MAX_BEN_LIMIT")));
					insPricingVO.setMaxBeneLimitOth(TTKCommon.checkNull(rs
							.getString("MAX_BEN_LIMIT_OTH")));
					insPricingVO.setNetworkList(TTKCommon.checkNull(rs
							.getString("NETWORK_TYPE")));
					insPricingVO.setInpatientAreaOfCover(TTKCommon.checkNull(rs
							.getString("INPAT_AREA_COV")));
					insPricingVO.setInpatientAreaOfCoverVariations(TTKCommon
							.checkNull(rs.getString("INPAT_AREA_COV_VAR")));
					insPricingVO.setLoadingForInpatient(TTKCommon.checkNull(rs
							.getString("PERC_INPAT_AREA_COV_VAR")));
					insPricingVO.setCommentsInpatient(TTKCommon.checkNull(rs
							.getString("INPAT_AREA_COV_VAR_COMM")));
					/*
					 * insPricingVO.setRoomType(TTKCommon.checkNull(rs.getString(
					 * "ROOM_TYP")));
					 */
					insPricingVO.setRoomType1(TTKCommon.checkNull(rs
							.getString("ROOM_TYP")));
					insPricingVO.setInpatientCopay(TTKCommon.checkNull(rs
							.getString("INPAT_COPAY")));
					insPricingVO.setMaxChildAgeLimit(TTKCommon.checkNull(rs
							.getString("MAX_CHILD_AGELMT_COMP")));
					insPricingVO.setCompanionCharges(TTKCommon.checkNull(rs
							.getString("COMP_CHGS_PER_DAY")));
					insPricingVO.setOutpatientAreaOfCover(TTKCommon
							.checkNull(rs.getString("OP_AREA_COV")));
					insPricingVO.setOutpatientAreaOfCoverVariations(TTKCommon
							.checkNull(rs.getString("OP_AREA_COV_VAR")));
					insPricingVO.setLoadingForOutpatient(TTKCommon.checkNull(rs
							.getString("VARIATIONS_OP")));
					insPricingVO.setCommentsOutpatient(TTKCommon.checkNull(rs
							.getString("COMMENTS_OP")));
					insPricingVO.setAdditionalHospitalCoverage(TTKCommon
							.checkNull(rs.getString("ADD_HOSP_COV")));
					insPricingVO.setHospitalNameOth(TTKCommon.checkNull(rs
							.getString("HOSP_NM_OTH")));
					insPricingVO.setLoadingForAddHospital(TTKCommon
							.checkNull(rs.getString("PERC_ADD_HOS_COV")));
					insPricingVO.setConsultationCD(TTKCommon.checkNull(rs
							.getString("CONS_COPAY_DED")));
					insPricingVO.setDrugsLimit(TTKCommon.checkNull(rs
							.getString("DRUGS_LMT")));
					insPricingVO.setDrugsCopay(TTKCommon.checkNull(rs
							.getString("DRUGS_COPAY")));
					insPricingVO.setLaboratoryCD(TTKCommon.checkNull(rs
							.getString("LAB_COPAY_DED")));
					insPricingVO.setPhysiotherapyLimit(TTKCommon.checkNull(rs
							.getString("PHYSIO_LMT")));
					insPricingVO.setPhysiotherapyCopay(TTKCommon.checkNull(rs
							.getString("PHYSIO_COPAY")));
					insPricingVO.setDinpatientCopay(TTKCommon.checkNull(rs
							.getString("DIR_SP_ACESS_OP")));
					insPricingVO.setCommentsDirect(TTKCommon.checkNull(rs
							.getString("DIR_SP_ACESS_COMM")));
					insPricingVO.setMaternityInpatient(rs.getString("MAT_IP"));
					insPricingVO.setMaternityOutpatient(rs.getString("MAT_OP"));
					/*
					 * insPricingVO.setEligibleForMaternity(TTKCommon.checkNull(rs
					 * .getString("TOT_COV_LV_MAT")));
					 */
					insPricingVO.setTotalLivesMaternity(TTKCommon.checkNull(rs
							.getString("TOT_COV_LV_MAT")));

					insPricingVO.setMaternityPricing(TTKCommon.checkNull(rs
							.getString("MAT_IP_OP_PRIC")));
					insPricingVO.setNormalDeliveryLimit(TTKCommon.checkNull(rs
							.getString("NOR_DEL_LMT")));
					insPricingVO.setNormalDeliveryLimitOth(TTKCommon
							.checkNull(rs.getString("NOR_DEL_LMT_OTH")));
					insPricingVO.setCSectionLimit(TTKCommon.checkNull(rs
							.getString("C_SEC_LMT")));
					insPricingVO.setCSectionLimitOth(TTKCommon.checkNull(rs
							.getString("C_SEC_LMT_OTH")));
					insPricingVO.setMaternityInpatientCopay(TTKCommon
							.checkNull(rs.getString("MAT_IP_COPAY")));
					insPricingVO.setComments(TTKCommon.checkNull(rs
							.getString("OTH_KEY_COV")));
					insPricingVO.setAntiSelectionLoadInpatient(TTKCommon
							.checkNull(rs.getString("ANTI_SEL_LOAD_IP")));
					insPricingVO.setCommentsAntiInpatient(TTKCommon
							.checkNull(rs.getString("ANTI_SEL_LOAD_IP_COMM")));
					insPricingVO.setAntiSelectionLoadOutpatient(TTKCommon
							.checkNull(rs.getString("ANTI_SEL_LOAD_OP")));
					insPricingVO.setCommentsAntiOutpatient(TTKCommon
							.checkNull(rs.getString("ANTI_SEL_LOAD_OP_COMM")));
					insPricingVO.setTrendFactor(TTKCommon.checkNull(rs
							.getString("TREND")));
					insPricingVO.setPremiumRefundApplicable(TTKCommon
							.checkNull(rs.getString("PRO_RATA_PREM_YN")));
					insPricingVO.setLogicForPremiumRefund(TTKCommon
							.checkNull(rs.getString("PRO_RATA_PREM_LOG")));
					insPricingVO.setMaternityOutpatientVisits(TTKCommon
							.checkNull(rs.getString("MAT_OP_VISTS")));
					insPricingVO.setMaternityOutpatientCopay(TTKCommon
							.checkNull(rs.getString("MAT_OP_COPAY")));
					insPricingVO.setDrugsLimitOth(TTKCommon.checkNull(rs
							.getString("DRUGS_LMT_OTH")));
					insPricingVO.setAuthorityType(TTKCommon.checkNull(rs
							.getString("AUTH_TYPE")));
					insPricingVO.setExistCompProd1(TTKCommon.checkNull(rs
							.getString("prd1")));
					insPricingVO.setExistCompProd2(TTKCommon.checkNull(rs
							.getString("prd2")));
					insPricingVO.setPortfloLevel(TTKCommon.checkNull(rs
							.getString("PRI_PORT_POL_LEVEL")));
					insPricingVO.setHosComments(TTKCommon.checkNull(rs
							.getString("ADD_HOSP_COMM")));
					insPricingVO.setInsuranceCompanyNameOth(TTKCommon
							.checkNull(rs.getString("INS_COMP_OTH")));

					blob1 = rs.getBlob("SOURCE_DOC1");
					if (blob1 != null) {
						iStream1 = blob1.getBinaryStream();
						insPricingVO.setInputstreamdoc1(iStream1);
					}
					blob2 = rs.getBlob("SOURCE_DOC2");
					if (blob2 != null) {
						iStream2 = blob2.getBinaryStream();
						insPricingVO.setInputstreamdoc2(iStream2);
					}

					blob3 = rs.getBlob("SOURCE_DOC3");
					if (blob3 != null) {
						iStream3 = blob3.getBinaryStream();
						insPricingVO.setInputstreamdoc3(iStream3);
					}

					blob4 = rs.getBlob("SOURCE_DOC4");
					if (blob4 != null) {
						iStream4 = blob4.getBinaryStream();
						insPricingVO.setInputstreamdoc4(iStream4);
					}

					blob5 = rs.getBlob("SOURCE_DOC5");
					if (blob5 != null) {
						iStream5 = blob5.getBinaryStream();
						insPricingVO.setInputstreamdoc5(iStream5);
					}

					insPricingVO.setAttachmentname1(TTKCommon.checkNull(rs
							.getString("SOURCE_PATH1")));
					insPricingVO.setAttachmentname2(TTKCommon.checkNull(rs
							.getString("SOURCE_PATH2")));
					insPricingVO.setAttachmentname3(TTKCommon.checkNull(rs
							.getString("SOURCE_PATH3")));
					insPricingVO.setAttachmentname4(TTKCommon.checkNull(rs
							.getString("SOURCE_PATH4")));
					insPricingVO.setAttachmentname5(TTKCommon.checkNull(rs
							.getString("SOURCE_PATH5")));

				}// end of while(rs.next())
			}// End of if(rs!=null)
			return insPricingVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null) {
						rs.close();

					}
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceDAOImpl swSelectPricingList()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (cStmtObject != null) {
							cStmtObject.close();
						}

					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceDAOImpl swSelectPricingList()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl swSelectPricingList()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	// search
	public ArrayList getSwInsuranceProfileList(ArrayList alSearchObjects)
			throws TTKException {
		StringBuffer sbfDynamicQuery = new StringBuffer();
		Connection conn = null;
		PreparedStatement pStmt = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		String strStaticQuery = "";

		String t;
		Collection<Object> alResultList = new ArrayList<Object>();
		InsPricingVO insPricingVO = null;

		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSwInsuranceProfileList);

			cStmtObject.setString(1, (String) alSearchObjects.get(0));
			cStmtObject.setString(2, (String) alSearchObjects.get(1));
			cStmtObject.setString(3, (String) alSearchObjects.get(2));
			cStmtObject.setString(4, (String) alSearchObjects.get(3));
			cStmtObject.setString(5, (String) alSearchObjects.get(4));
			cStmtObject.setBigDecimal(6, new BigDecimal(alSearchObjects.get(5).toString()));
			cStmtObject.setBigDecimal(7, new BigDecimal(alSearchObjects.get(6).toString()));

			cStmtObject.registerOutParameter(8, Types.OTHER);
			cStmtObject.registerOutParameter(3, Types.VARCHAR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(8);
			if (rs != null) {
				while (rs.next()) {
					insPricingVO = new InsPricingVO();
					insPricingVO.setClientCode(TTKCommon.checkNull(rs
							.getString("client_code")));
					insPricingVO.setPricingRefno(TTKCommon.checkNull(rs
							.getString("ref_no")));
					insPricingVO.setPreviousPolicyNo(TTKCommon.checkNull(rs
							.getString("prev_policy_no")));
					if (rs.getString("pri_seq_id") != null) {
						insPricingVO.setGroupProfileSeqID(new Long(rs
								.getLong("pri_seq_id")));
					}
					alResultList.add(insPricingVO);
				}// end of if(strIdentifier.equalsIgnoreCase("Administration"))

			}// End of while (rs.next())

			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getSwInsuranceProfileList()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getSwInsuranceProfileList()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getSwInsuranceProfileList()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	public Object[] getBenefitvalueAfter(Long lpricingSeqId)
			throws TTKException {

		HashMap<String, ArrayList<AgeMasterVO>> tablesDataHM = new HashMap<>();
		HashMap<String, String> tablesNamesHM = new HashMap<>();
		ArrayList<AgeMasterVO> table1 = new ArrayList<>();
		ArrayList<AgeMasterVO> table2 = new ArrayList<>();
		ArrayList<AgeMasterVO> table3 = new ArrayList<>();
		ArrayList<AgeMasterVO> table4 = new ArrayList<>();
		Object[] tableObjects = new Object[2];

		int table1totalLives = 0;
		int table2totalLives = 0;
		int table3totalLives = 0;
		int table4totalLives = 0;

		String table1HeaderName = null;
		String table2HeaderName = null;
		String table3HeaderName = null;
		String table4HeaderName = null;

		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strswProfileIncomeListValue);
			cStmtObject.setLong(1, lpricingSeqId);
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.registerOutParameter(3, Types.OTHER);
			cStmtObject.registerOutParameter(4, Types.OTHER);
			cStmtObject.registerOutParameter(5, Types.OTHER);
			cStmtObject.execute();
			rs1 = (java.sql.ResultSet) cStmtObject.getObject(2);
			rs2 = (java.sql.ResultSet) cStmtObject.getObject(3);
			rs3 = (java.sql.ResultSet) cStmtObject.getObject(4);
			rs4 = (java.sql.ResultSet) cStmtObject.getObject(5);

			if (rs1 != null) {
				AgeMasterVO ageMasterVO = null;
				while (rs1.next()) {
					ageMasterVO = new AgeMasterVO();
					ageMasterVO.setColumn1(rs1.getString("inp2_seq_id"));
					ageMasterVO.setColumn2(rs1.getString("col1"));
					ageMasterVO.setColumn3(rs1.getString("col2"));
					ageMasterVO.setColumn4(rs1.getString("val"));
					ageMasterVO.setOvrlPofloDist(rs1
							.getString("OVRL_PORTPOL_DISTRIB"));
					table1totalLives += rs1.getInt("val");

					table1.add(ageMasterVO);

					if (table1HeaderName == null)
						table1HeaderName = rs1.getString("table_name");
				}
			}

			if (rs2 != null) {
				AgeMasterVO ageMasterVO = null;
				while (rs2.next()) {
					tablesNamesHM.put(rs2.getString("VID"),
							rs2.getString("table_name"));

					ageMasterVO = new AgeMasterVO();
					ageMasterVO.setColumn1(rs2.getString("inp2_seq_id"));
					ageMasterVO.setColumn2(rs2.getString("col1"));
					ageMasterVO.setColumn3(rs2.getString("col2"));
					ageMasterVO.setColumn4(rs2.getString("val"));
					ageMasterVO.setOvrlPofloDist(rs2
							.getString("OVRL_PORTPOL_DISTRIB"));
					table2totalLives += rs2.getInt("val");
					table2.add(ageMasterVO);
					if (table2HeaderName == null)
						table2HeaderName = rs2.getString("table_name");
				}
			}

			if (rs3 != null) {
				AgeMasterVO ageMasterVO = null;
				while (rs3.next()) {

					ageMasterVO = new AgeMasterVO();
					ageMasterVO.setColumn1(rs3.getString("inp2_seq_id"));
					ageMasterVO.setColumn5(rs3.getString("col2"));
					ageMasterVO.setColumn4(rs3.getString("val"));
					ageMasterVO.setOvrlPofloDist(rs3
							.getString("OVRL_PORTPOL_DISTRIB"));
					table3totalLives += rs3.getInt("val");
					ageMasterVO.setIncmFlag(rs3.getString("INCME_GRP_FLG"));
					table3.add(ageMasterVO);
					if (table3HeaderName == null)
						table3HeaderName = rs3.getString("table_name");
				}
			}

			if (rs4 != null) {
				AgeMasterVO ageMasterVO = null;
				while (rs4.next()) {

					ageMasterVO = new AgeMasterVO();
					ageMasterVO.setColumn1(rs4.getString("inp2_seq_id"));

					// ageMasterVO.setColumn1(rs4.getString("pri_seq_id"));
					ageMasterVO.setColumn6(rs4.getString("col2"));
					ageMasterVO.setColumn4(rs4.getString("val"));
					table4totalLives += rs4.getInt("val");
					ageMasterVO.setOvrlPofloDist(rs4
							.getString("OVRL_PORTPOL_DISTRIB"));
					table4.add(ageMasterVO);
					if (table4HeaderName == null)
						table4HeaderName = rs4.getString("table_name");
				}
			}
			// table-1
			tablesDataHM.put("table1Data", table1);
			tablesNamesHM.put("table1HeaderName", table1HeaderName);
			tablesNamesHM.put("table1totalLives", table1totalLives + "");
			// table-2
			tablesDataHM.put("table2Data", table2);
			tablesNamesHM.put("table2HeaderName", table2HeaderName);
			tablesNamesHM.put("table2totalLives", table2totalLives + "");
			// table-3
			tablesDataHM.put("table3Data", table3);
			tablesNamesHM.put("table3HeaderName", table3HeaderName);
			tablesNamesHM.put("table3totalLives", table3totalLives + "");
			// table-4
			tablesDataHM.put("table4Data", table4);
			tablesNamesHM.put("table4HeaderName", table4HeaderName);
			tablesNamesHM.put("table4totalLives", table4totalLives + "");

			tableObjects[0] = tablesNamesHM;
			tableObjects[1] = tablesDataHM;

			return tableObjects;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs1 != null)
						rs1.close();
					if (rs2 != null)
						rs2.close();
					if (rs3 != null)
						rs3.close();
					if (rs4 != null)
						rs4.close();

				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceDAOImpl getBenefitvalueAfter()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getBenefitvalueAfter()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getBenefitvalueAfter()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{

				rs4 = null;
				rs3 = null;
				rs2 = null;
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}

	// prem
	public Long swSaveIncomeProfile(HashMap<String, Object> screen2Data)
			throws TTKException {
		Long iResult = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strswSaveIncomeProfile);
			String strGroupSeqID = screen2Data.get("groupProfileSeqID").toString();
			Long userSeqId = (Long) screen2Data.get("userSeqId");
			ArrayList<AgeMasterVO> table1Data = (ArrayList<AgeMasterVO>) screen2Data.get("table1Data");

			for (AgeMasterVO ageMasterVO : table1Data) {
				cStmtObject.setBigDecimal(1,  new BigDecimal(ageMasterVO.getColumn1()));
				cStmtObject.setBigDecimal(2, new BigDecimal(strGroupSeqID));
				if(ageMasterVO.getColumn4() == null || ageMasterVO.getColumn4().equals("")){
					cStmtObject.setDouble(3, 0.0);
				}
				else{
					cStmtObject.setDouble(3, Double.parseDouble(ageMasterVO.getColumn4()));
				}
				cStmtObject.setLong(4, userSeqId);

				cStmtObject.registerOutParameter(5, Types.INTEGER);
				cStmtObject.execute();
				conn.commit();
			}

			ArrayList<AgeMasterVO> table2Data = (ArrayList<AgeMasterVO>) screen2Data.get("table2Data");

			for (AgeMasterVO ageMasterVO : table2Data) {
				cStmtObject.setBigDecimal(1, new BigDecimal(ageMasterVO.getColumn1()));
				cStmtObject.setBigDecimal(2, new BigDecimal(strGroupSeqID));
				if(ageMasterVO.getColumn4() == null || ageMasterVO.getColumn4().equals("")){
					cStmtObject.setDouble(3, 0.0);
				}
				else{
					cStmtObject.setDouble(3, Double.parseDouble(ageMasterVO.getColumn4()));
				}
				cStmtObject.setLong(4, userSeqId);

				cStmtObject.registerOutParameter(5, Types.INTEGER);
				cStmtObject.execute();
				conn.commit();
			}

			ArrayList<AgeMasterVO> table3Data = (ArrayList<AgeMasterVO>) screen2Data.get("table3Data");

			for (AgeMasterVO ageMasterVO : table3Data) {
				cStmtObject.setBigDecimal(1, new BigDecimal(ageMasterVO.getColumn1()));
				cStmtObject.setBigDecimal(2, new BigDecimal(strGroupSeqID));
				if(ageMasterVO.getColumn4()== null || ageMasterVO.getColumn4().equals("")){
					cStmtObject.setDouble(3, new Double(0));
				}
				else{
				cStmtObject.setDouble(3, new Double(ageMasterVO.getColumn4()));
				}
				cStmtObject.setLong(4, userSeqId);

				cStmtObject.registerOutParameter(5, Types.INTEGER);
				cStmtObject.execute();
				conn.commit();
			}

			ArrayList<AgeMasterVO> table4Data = (ArrayList<AgeMasterVO>) screen2Data.get("table4Data");

			for (AgeMasterVO ageMasterVO : table4Data) {
				cStmtObject.setBigDecimal(1, new BigDecimal(ageMasterVO.getColumn1()));
				cStmtObject.setBigDecimal(2, new BigDecimal(strGroupSeqID));
				if(ageMasterVO.getColumn4() == null || ageMasterVO.getColumn4().equals("")){
					cStmtObject.setDouble(3, 0.0);
				}
				else{
					cStmtObject.setDouble(3, Double.parseDouble(ageMasterVO.getColumn4()));
				}
				cStmtObject.setLong(4, userSeqId);

				cStmtObject.registerOutParameter(5, Types.INTEGER);
				cStmtObject.execute();
				conn.commit();
			}

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			// Nested Try Catch to ensure resource closure
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl deleteInsuranceCompany()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl deleteInsuranceCompany()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}

	// end prem
	// end bikki

	// govind

	// govind start
	public ArrayList<com.ttk.dto.insurancepricing.MasterFactorVO> selectFactorMasterList()
			throws TTKException // Long lpricingSeqId
	{
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSelectFactorMasterList);
			// cStmtObject.setLong(1,lpricingSeqId);
			cStmtObject.registerOutParameter(1, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(1);
			ArrayList<MasterFactorVO> factorList = null;
			if (rs != null) {
				factorList = new ArrayList<MasterFactorVO>();
				while (rs.next()) {
					MasterFactorVO factorVO = new MasterFactorVO();
					factorVO.setFactId(TTKCommon.checkNull(rs
							.getString("FACT_ID")));
					factorVO.setBenefitRule(TTKCommon.checkNull(rs
							.getString("BENEFIT_RULE")));
					factorVO.setBenefitDesc(TTKCommon.checkNull(rs
							.getString("BENEFIT_DESC")));
					factorVO.setGender(TTKCommon.checkNull(rs
							.getString("GENDER")));
					factorVO.setDescription(TTKCommon.checkNull(rs
							.getString("DESCRIPTN")));
					factorVO.setFactor(TTKCommon.checkNull(rs
							.getString("FACTOR")));
					factorVO.setAverageAge(TTKCommon.checkNull(rs
							.getString("AVERAGE_AGE")));

					if ("R".equals(rs.getString("COLOR"))) {

						factorVO.setRowColor("RedRowClass");

					} else if ("B".equals(rs.getString("COLOR"))) {

						factorVO.setRowColor("BlueRowClass");

					}

					factorList.add(factorVO);

				}// end of while(rs.next())
			}// End of if(rs!=null)
			return factorList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null) {
						rs.close();

					}
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (cStmtObject != null) {
							cStmtObject.close();
						}

					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	public Long saveFactorMaster(ArrayList<MasterFactorVO> factorList)
			throws TTKException {
		Long iResult = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveFactorMaster);

			for (MasterFactorVO masterFactorVO : factorList) {

				cStmtObject.setDouble(1, new Double(masterFactorVO.getFactor()));
				cStmtObject.setString(2, masterFactorVO.getFactId());
				if(masterFactorVO.getAverageAge() == null || masterFactorVO.getAverageAge().equals("")){
					cStmtObject.setLong(3, 0);
				}
				else{
					cStmtObject.setBigDecimal(3, new BigDecimal(masterFactorVO.getAverageAge()));
				}
				
				cStmtObject.registerOutParameter(4, Types.INTEGER);
				cStmtObject.execute();
				conn.commit();
				iResult = (long)cStmtObject.getInt(4);

			}

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			// /nested ry Catch to ensure resource closure
			try // First try closing the Statement

			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl saveFactorMaster()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
							log.info("connection is closed saveFactorMaster");
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl saveFactorMaster()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteInsuranceCompany(String strInsSeqID)

	// govind end

	public int savePriceTrendMaster(MasterFactorVO masterFactorVO)
			throws TTKException {

		Connection conn = null;
		CallableStatement cStmtObject = null;
		int result = 0;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveTrendFactorMaster);
			cStmtObject.setBigDecimal(1, new BigDecimal(0));
			if (masterFactorVO.getAvgExpStartDate() != null) {
				cStmtObject.setTimestamp(2, new Timestamp(masterFactorVO
						.getAvgExpStartDate().getTime()));
			}
			if (masterFactorVO.getAvgExpendDate() != null) {
				cStmtObject.setTimestamp(3, new Timestamp(masterFactorVO
						.getAvgExpendDate().getTime()));
			}
			cStmtObject.registerOutParameter(4, Types.INTEGER);
			cStmtObject.registerOutParameter(1, Types.NUMERIC);

			cStmtObject.execute();
			conn.commit();

			result = cStmtObject.getInt(4);

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Statement in InsuranceDAOImpl savePriceTrendMaster()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl savePriceTrendMaster()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of finally Statement Close
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally

		return result;
	}// end of public int savePriceTrendMaster(MasterFactorVO masterFactorVO)
		// throws TTKException

	public MasterFactorVO getPriceTrendMaster(MasterFactorVO masterFactorVO)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;

		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSelectTrendFactorMaster);
			cStmtObject.setBigDecimal(1, new BigDecimal(0));
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();

			rs = (java.sql.ResultSet) cStmtObject.getObject(2);

			if (rs != null) {
				while (rs.next()) {
					masterFactorVO = new MasterFactorVO();
					if (rs.getString("AVA_EXP_START_DATE") != null) {
						masterFactorVO.setAvgExpStartDate(new Date(rs
								.getTimestamp("AVA_EXP_START_DATE").getTime()));
					}
					if (rs.getString("AVA_EXP_END_DATE") != null) {
						masterFactorVO.setAvgExpendDate(new Date(rs
								.getTimestamp("AVA_EXP_END_DATE").getTime()));
					}
				}// End of while (rs.next())

			}
			return masterFactorVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	public Object[] getdemographicData(InsPricingVO insPricingVO)
			throws TTKException {

		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		SwPolicyConfigVO swPolicyConfigVO = null;
		ArrayList<SwPolicyConfigVO> pastPolycies = new ArrayList<>();
		ArrayList<SwPolicyConfigVO> productsList = new ArrayList<>();
		HashMap<String, String> hmPropData = new HashMap<>();
		ArrayList<SwPolicyConfigVO> pastPolycies2 = new ArrayList<>();
		ArrayList<SwPolicyConfigVO> productsList2 = new ArrayList<>();
		ArrayList<HashMap<String, String>> tableList3 = new ArrayList<>();
		Object[] results = new Object[9];
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn.prepareCall(strSelectDemographicData);
			cStmtObject.setBigDecimal(1, new BigDecimal(insPricingVO.getGroupProfileSeqID()));
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.registerOutParameter(3, Types.OTHER);
			cStmtObject.registerOutParameter(4, Types.OTHER);
			cStmtObject.execute();
			conn.commit();

			rs1 = (java.sql.ResultSet) cStmtObject.getObject(2);
			rs2 = (java.sql.ResultSet) cStmtObject.getObject(3);
			rs3 = (java.sql.ResultSet) cStmtObject.getObject(4);
			if (rs1 != null) {
				while (rs1.next()) {
					if ("PROPOSED".equals(rs1.getString("REC_TYPE"))) {

						java.sql.ResultSetMetaData rsMD = rs1.getMetaData();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yyyy");

						int columncount = rsMD.getColumnCount();
						for (int i = 1; i <= columncount; i++) {
							if ("DATE".equals(rsMD.getColumnTypeName(i))) {

								hmPropData.put(
										rsMD.getColumnName(i),
										(rs1.getString(i) == null ? ""
												: dateFormat.format(rs1
														.getDate(i))));
							} else {
								hmPropData.put(rsMD.getColumnName(i).toUpperCase(),
										TTKCommon.checkNull(rs1.getString(i)));
							}
						}
					} else {
						swPolicyConfigVO = new SwPolicyConfigVO();
						swPolicyConfigVO.setLngGroupProfileSeqID(insPricingVO.getGroupProfileSeqID());
//						swPolicyConfigVO.setAddedBy(insPricingVO.getAddedBy());
						if (rs1.getString("Prev_pol") != null) {
							swPolicyConfigVO.setDemoPolicyNo(TTKCommon
									.checkNull(rs1.getString("Prev_pol")));
						}
						if (rs1.getString("COV_SEQ_ID") == null || rs1.getString("COV_SEQ_ID").equals("")) {
							swPolicyConfigVO.setDemoCovSeqId(new Long(0));
						}else{
							swPolicyConfigVO.setDemoCovSeqId(new Long(rs1
									.getLong("COV_SEQ_ID")));
						}
						if (rs1.getString("PRI_SEQ_ID") != null) {
							swPolicyConfigVO.setDemoPricSeqId(new Long(rs1
									.getString("PRI_SEQ_ID")));
						}

						if (rs1.getString("REC_TYPE") != null) {
							swPolicyConfigVO.setDemoRecType(TTKCommon
									.checkNull(rs1.getString("REC_TYPE")));
						}
						if (rs1.getString("TOP") != null) {
							swPolicyConfigVO.setDemoTop(TTKCommon.checkNull(rs1
									.getString("TOP")));
						}
						if (rs1.getString("CORP_ID") != null) {
							swPolicyConfigVO.setDemoCorpId(TTKCommon
									.checkNull(rs1.getString("CORP_ID")));
						}
						if (rs1.getString("CORP_NAME") != null) {
							swPolicyConfigVO.setDemoCorpNm(TTKCommon
									.checkNull(rs1.getString("CORP_NAME")));
						}
						if (rs1.getString("COV_START_DATE") == null || rs1.getString("COV_START_DATE").equals("")) {
							swPolicyConfigVO.setCovrgSrartDate("");
						}
						else{
							String stringDate = "";
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"dd/MM/yyyy");
							stringDate = dateFormat.format(rs1
									.getDate("COV_START_DATE"));
							swPolicyConfigVO.setCovrgSrartDate(TTKCommon
									.checkNull(stringDate));
						}
						if (rs1.getString("COV_END_DATE") == null || rs1.getString("COV_END_DATE").equals("")) {
							swPolicyConfigVO.setCovrgEndDate("");
						}
						else{
							String stringDate = "";
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"dd/MM/yyyy");
							stringDate = dateFormat.format(rs1
									.getDate("COV_END_DATE"));
							swPolicyConfigVO.setCovrgEndDate(TTKCommon
									.checkNull(stringDate));
						}
						if (rs1.getString("DURATION") != null) {
							swPolicyConfigVO
									.setDemoPolicyDurationPerMonth(TTKCommon
											.checkNull(rs1
													.getString("DURATION")));
						}
						if (rs1.getString("PRODUCT_NAME") != null) {
							swPolicyConfigVO.setDemoprodNm(TTKCommon
									.checkNull(rs1.getString("PRODUCT_NAME")));
						}
						swPolicyConfigVO.setDemoAuthType(TTKCommon
								.checkNull(rs1.getString("AUTHOR_TYPE")));

						if (rs1.getString("INS_COMPANY_NAME") != null) {
							swPolicyConfigVO.setDemoInsCompNm(TTKCommon
									.checkNull(rs1
											.getString("INS_COMPANY_NAME")));
						}

						if (rs1.getString("AS_TYPE") != null) {
							swPolicyConfigVO.setDemoAsType(TTKCommon
									.checkNull(rs1.getString("AS_TYPE")));
						}

						if (rs1.getString("MAT_LIVES") != null) {
							swPolicyConfigVO.setDemoMatLives((rs1
									.getString("MAT_LIVES")));
						}

						if (rs1.getString("MAT_AVG_AGE") == null || rs1.getString("MAT_AVG_AGE").equals("")) {
							swPolicyConfigVO.setDemoMatAvgAge("");
						}
						else{
							swPolicyConfigVO.setDemoMatAvgAge(TTKCommon.checkNull(rs1.getString("MAT_AVG_AGE")));
						}
						if (rs1.getString("NATIONALITY") != null) {
							swPolicyConfigVO.setDemoNationality(TTKCommon
									.checkNull(rs1.getString("NATIONALITY")));
						}
						if (rs1.getString("INCOME_DIST") != null) {
							swPolicyConfigVO.setDemoIncomeDist(TTKCommon
									.checkNull(rs1.getString("INCOME_DIST")));
						}
						if (rs1.getString("NETWORK") != null) {
							swPolicyConfigVO.setDemoNetwork(TTKCommon
									.checkNull(rs1.getString("NETWORK")));
						}
						if (rs1.getString("MBL") != null) {
							swPolicyConfigVO
									.setDemoMaximumBenfitLimit(TTKCommon
											.checkNull(rs1.getString("MBL")));
						}
						if (rs1.getString("OP_AOC") != null) {
							swPolicyConfigVO.setOpAreaCov(TTKCommon
									.checkNull(rs1.getString("OP_AOC")));
						}
						if (rs1.getString("DRUGS_LIMIT") != null) {
							swPolicyConfigVO.setDemoDrugsLimit(TTKCommon
									.checkNull(rs1.getString("DRUGS_LIMIT")));
						}

						if (rs1.getString("DRUGS_COPAY") != null) {
							swPolicyConfigVO.setDemoDrugsCopay(TTKCommon
									.checkNull(rs1.getString("DRUGS_COPAY")));
						}
						if (rs1.getString("LAB_COPAY_DEDUCTIBLE") != null) {
							swPolicyConfigVO
									.setDemoLabCopayDudct(TTKCommon.checkNull(rs1
											.getString("LAB_COPAY_DEDUCTIBLE")));
						}
						if (rs1.getString("CONSULT_DEDUCT") != null) {
							swPolicyConfigVO
									.setDemoConsltDudct(TTKCommon.checkNull(rs1
											.getString("CONSULT_DEDUCT")));
						}
						if (rs1.getString("PHYSIO_LIMIT") != null) {
							swPolicyConfigVO.setDemoPhysioLimit(TTKCommon
									.checkNull(rs1.getString("PHYSIO_LIMIT")));
						}
						if (rs1.getString("PHYSIO_COPAY") != null) {
							swPolicyConfigVO.setDemoPhysioCopay(TTKCommon
									.checkNull(rs1.getString("PHYSIO_COPAY")));
						}
						if (rs1.getString("IP_AOC") != null) {
							swPolicyConfigVO.setDemoIpAoc(TTKCommon
									.checkNull(rs1.getString("IP_AOC")));
						}
						if (rs1.getString("ROOM_TYPE") != null) {
							swPolicyConfigVO.setDemoRoomType(TTKCommon
									.checkNull(rs1.getString("ROOM_TYPE")));
						}
						if (rs1.getString("CHILD_AGE_LIMIT") != null) {
							swPolicyConfigVO
									.setDemoChildAgeLimit(TTKCommon.checkNull(rs1
											.getString("CHILD_AGE_LIMIT")));
						}
						if (rs1.getString("COMPANY_PER_DAY_LIMIT") != null) {
							swPolicyConfigVO
									.setDemoCompPerDayLimit(TTKCommon.checkNull(rs1
											.getString("COMPANY_PER_DAY_LIMIT")));
						}
						if (rs1.getString("IP_COPAY") != null) {
							swPolicyConfigVO.setDemoIpCopay(TTKCommon.checkNull(rs1.getString("IP_COPAY"))); // string
						}
						if (rs1.getString("MAT_OP_VISITS") != null) {
							swPolicyConfigVO.setDemoMatOpVisits(TTKCommon
									.checkNull(rs1.getString("MAT_OP_VISITS")));
						}
						if (rs1.getString("NORMAL_DELI_LIMIT") != null) {
							swPolicyConfigVO.setDemoNrmlDeliLimit(TTKCommon
									.checkNull(rs1
											.getString("NORMAL_DELI_LIMIT")));
						}
						if (rs1.getString("C_SEC_LIMIT") != null) {
							swPolicyConfigVO.setDemoCSecLimit(TTKCommon
									.checkNull(rs1.getString("C_SEC_LIMIT")));
						}
						if (rs1.getString("MAT_OP_COPAY") != null) {
							swPolicyConfigVO.setDemoMatOpCopay(TTKCommon
									.checkNull(rs1.getString("MAT_OP_COPAY")));
						}
						if (rs1.getString("MAT_IP_COPAY") != null) {
							swPolicyConfigVO.setDemoMatIpCopay(TTKCommon
									.checkNull(rs1.getString("MAT_IP_COPAY")));
						}
						if (rs1.getString("LIVES") != null) {
							swPolicyConfigVO.setDemoNoOfLives(TTKCommon
									.checkNull(rs1.getString("LIVES")));
						}
						if (rs1.getString("AVG_AGE") == null || rs1.getString("AVG_AGE").equals("")) {
							swPolicyConfigVO.setDemoAverageAge("");
						}
						else{
							swPolicyConfigVO.setDemoAverageAge(TTKCommon.checkNull(rs1.getString("AVG_AGE")));
						}
						if (rs1.getString("SHOW_BOX") != null) {
							swPolicyConfigVO.setShowBox(TTKCommon.checkNull(rs1
									.getString("SHOW_BOX")));
						}

						swPolicyConfigVO.setIpCoverage(TTKCommon.checkNull(rs1
								.getString("IP_COV")));
						swPolicyConfigVO.setOpCoverage(TTKCommon.checkNull(rs1
								.getString("OP_COV")));
						swPolicyConfigVO.setMatIpCoverage(TTKCommon
								.checkNull(rs1.getString("MAT_IP_COV")));
						swPolicyConfigVO.setMatOpCoverage(TTKCommon
								.checkNull(rs1.getString("MAT_OP_COV")));
						swPolicyConfigVO.setPolNoToolTip(TTKCommon.checkNull(rs1.getString("POL_TOOL_TIP")));
						swPolicyConfigVO.setOpAocToolTip(TTKCommon.checkNull(rs1.getString("OPAOC_TOOL_TIP")));
						swPolicyConfigVO.setIpAocToolTip(TTKCommon.checkNull(rs1.getString("IPAOC_TOOL_TIP")));
						
						if ("PRODUCT".equals(TTKCommon.checkNull(rs1.getString("REC_TYPE")))) {
							swPolicyConfigVO.setProductDate(TTKCommon
									.checkNull(rs1.getString("PROD_DATE")));

						}
						if ("CURRENT".equals(TTKCommon.checkNull(rs1.getString("REC_TYPE")))) {
							results[0] = swPolicyConfigVO;
						} else if ("PRODUCT".equals(rs1.getString("REC_TYPE"))) {
							productsList.add(swPolicyConfigVO);
						} else {
							pastPolycies.add(swPolicyConfigVO);
						}
					}// else
				}// End of while (rs.next())

			}

			results[1] = hmPropData;
			results[2] = productsList;
			results[3] = pastPolycies;

			if (rs2 != null) {
				while (rs2.next()) {

					swPolicyConfigVO = new SwPolicyConfigVO();
					swPolicyConfigVO.setLngGroupProfileSeqID(insPricingVO
							.getGroupProfileSeqID());
					//swPolicyConfigVO.setAddedBy(insPricingVO.getAddedBy());
					if(rs2.getString("POL_TOOL_TIP") == null || rs2.getString("POL_TOOL_TIP").equals("")){
						swPolicyConfigVO.setPolNoToolTip("");
					}
					else{
						swPolicyConfigVO.setPolNoToolTip(TTKCommon.checkNull(rs2
								.getString("POL_TOOL_TIP")));
					}
					if (rs2.getString("Prev_pol") == null || rs2.getString("Prev_pol").equals("")) {
						swPolicyConfigVO.setDemoPolicyNo("");
					}
					else{
						swPolicyConfigVO.setDemoPolicyNo(TTKCommon
								.checkNull(rs2.getString("Prev_pol")));
					}
					if (rs2.getString("COV_SEQ_ID") == null || rs2.getString("COV_SEQ_ID").equals("")) {
						swPolicyConfigVO.setDemoCovSeqId(new Long(0));
					}
					else{
						swPolicyConfigVO.setDemoCovSeqId(new Long(rs2
								.getLong("COV_SEQ_ID")));
					}
					if (rs2.getString("PRI_SEQ_ID") == null || rs2.getString("PRI_SEQ_ID").equals("")) {
						swPolicyConfigVO.setDemoPricSeqId(new Long(0));
					}
					else{
						swPolicyConfigVO.setDemoPricSeqId(new Long(rs2
								.getString("PRI_SEQ_ID")));
					}
					if (rs2.getString("REC_TYPE") == null || rs2.getString("REC_TYPE").equals("")) {
						swPolicyConfigVO.setDemoRecType("");
					}
					else{
						swPolicyConfigVO.setDemoRecType(TTKCommon.checkNull(rs2
								.getString("REC_TYPE")));
					}

					if (rs2.getString("TOP") == null || rs2.getString("TOP").equals("")) {
						swPolicyConfigVO.setDemoTop("");
					}
					else{
						swPolicyConfigVO.setDemoTop(TTKCommon.checkNull(rs2
								.getString("TOP")));
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd/MM/yyyy");
					if (rs2.getString("COV_START_DATE") == null || rs2.getString("COV_START_DATE").equals("")) {

						swPolicyConfigVO.setCovrgSrartDate("");
					}
					else{
						swPolicyConfigVO.setCovrgSrartDate(dateFormat
								.format(rs2.getDate("COV_START_DATE")));
					}
					if (rs2.getString("COV_END_DATE") == null || rs2.getString("COV_END_DATE").equals("")) {
						swPolicyConfigVO.setCovrgEndDate("");
					}
					else{
						swPolicyConfigVO.setCovrgEndDate(dateFormat.format(rs2
								.getDate("COV_END_DATE")));
					}

					if (rs2.getString("COST_IP") == null || rs2.getString("COST_IP").equals("")) {
						swPolicyConfigVO
								.setDemoCostIp(rs2.getString("COST_IP"));
					}
					else{
						swPolicyConfigVO.setDemoCostIp(rs2.getString("COST_IP"));
					}

					if (rs2.getString("COST_OP") == null || rs2.getString("COST_OP").equals("")) {
						swPolicyConfigVO
								.setDemoCostOp("");
					}
					else{
						swPolicyConfigVO
						.setDemoCostOp(rs2.getString("COST_OP"));
					}

					if (rs2.getString("COST_MAT_IP") == null || rs2.getString("COST_MAT_IP").equals("")) {
						swPolicyConfigVO.setDemoCostMatIp("");
					}
					else{
						swPolicyConfigVO.setDemoCostMatIp(rs2
								.getString("COST_MAT_IP"));
					}

					if (rs2.getString("COST_MAT_OP") == null || rs2.getString("COST_MAT_OP").equals("")) {
						swPolicyConfigVO.setDemoCostMatOp("");
					}
					else{
						swPolicyConfigVO.setDemoCostMatOp(rs2
								.getString("COST_MAT_OP"));
					}
					if (rs2.getString("CRED_IP") == null || rs2.getString("CRED_IP").equals("")) {
						swPolicyConfigVO
								.setDemoCredIp("");
					}
					else{
						swPolicyConfigVO
						.setDemoCredIp(rs2.getString("CRED_IP"));
					}

					if (rs2.getString("CRED_OP") == null || rs2.getString("CRED_OP").equals("")) {
						swPolicyConfigVO
								.setDemoCredOp("");
					}
					else{
						swPolicyConfigVO
						.setDemoCredOp(rs2.getString("CRED_OP"));
					}

					if (rs2.getString("CRED_MAT_IP") == null || rs2.getString("CRED_MAT_IP").equals("")) {
						swPolicyConfigVO.setDemoCredMatIp("");
					}
					else{
						swPolicyConfigVO.setDemoCredMatIp(rs2
								.getString("CRED_MAT_IP"));
					}

					if (rs2.getString("CRED_MAT_OP") == null || rs2.getString("CRED_MAT_OP").equals("")) {
						swPolicyConfigVO.setDemoCredMatOp("");
					}
					else{
						swPolicyConfigVO.setDemoCredMatOp(rs2
								.getString("CRED_MAT_OP") + "");
					}

					if (rs2.getString("SHOW_BOX") == null || rs2.getString("SHOW_BOX").equals("")) {
						swPolicyConfigVO.setShowBox("");
					}
					else{
						swPolicyConfigVO.setShowBox(TTKCommon.checkNull(rs2
								.getString("SHOW_BOX")));
					}
					if (rs2.getString("DISABLE_IP") == null || rs2.getString("DISABLE_IP").equals("")) {
						swPolicyConfigVO.setIpDisableYN("");
					}
					else{
						swPolicyConfigVO.setIpDisableYN(rs2
								.getString("DISABLE_IP"));
					}
					if (rs2.getString("DISABLE_OP") == null || rs2.getString("DISABLE_OP").equals("")) {
						swPolicyConfigVO.setOpDisableYN("");
					}
					else{
						swPolicyConfigVO.setOpDisableYN(rs2
								.getString("DISABLE_OP"));
					}
					if (rs2.getString("DISABLE_MAT_IP") == null || rs2.getString("DISABLE_MAT_IP").equals("")) {
						swPolicyConfigVO.setMatIpDisableYN("");
					}
					else{
						swPolicyConfigVO.setMatIpDisableYN(rs2
								.getString("DISABLE_MAT_IP"));
					}
					if (rs2.getString("DISABLE_MAT_OP") == null || rs2.getString("DISABLE_MAT_OP").equals("")) {
						swPolicyConfigVO.setMatOpDisableYN("");
					}
					else{
						swPolicyConfigVO.setMatOpDisableYN(rs2
								.getString("DISABLE_MAT_OP"));
					}

					if ("PRODUCT".equals(rs2.getString("REC_TYPE"))) {
						swPolicyConfigVO.setProductDate(TTKCommon.checkNull(rs2
								.getString("PROD_DATE")));

					}
					if ("PROPOSED".equals(rs2.getString("REC_TYPE"))) {
						results[5] = swPolicyConfigVO;
					} else if ("CURRENT".equals(rs2.getString("REC_TYPE"))) {
						results[4] = swPolicyConfigVO;
					} else if ("PRODUCT".equals(rs2.getString("REC_TYPE"))) {
						productsList2.add(swPolicyConfigVO);
					} else {
						pastPolycies2.add(swPolicyConfigVO);
					}

				}
			}
			results[6] = productsList2;
			results[7] = pastPolycies2;

			if (rs3 != null) {

				java.sql.ResultSetMetaData rsMD = rs3.getMetaData();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				int columncount = rsMD.getColumnCount();
				while (rs3.next()) {
					HashMap<String, String> table3 = new HashMap<>();

					for (int i = 1; i <= columncount; i++) {
						if ("DATE".equals(rsMD.getColumnTypeName(i))) {

							table3.put(
									rsMD.getColumnName(i),
									(rs3.getString(i) == null ? "" : dateFormat
											.format(rs3.getDate(i))));
						} else {
							table3.put(rsMD.getColumnName(i).toUpperCase(),
									TTKCommon.checkNull(rs3.getString(i)));
						}
					} // for(int i=1;i<=columncount;i++){
					tableList3.add(table3);
				}// while(rs3.next()){

			}// if(rs3 != null){
			results[8] = tableList3;
			return results;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs1 = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally

	}

	public Long saveDemographicData(SwPolicyConfigVO configVO)
			throws TTKException {
		Long iResult = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveDemographicData);
			if (configVO.getDemoCovSeqId() == null || configVO.getDemoCovSeqId().equals("")) {
				cStmtObject.setBigDecimal(1,new BigDecimal(0));
			} else {
				
				cStmtObject.setLong(1, configVO.getDemoCovSeqId());
			}

			if (configVO.getDemoPricSeqId() != null) {

				cStmtObject.setLong(2, configVO.getDemoPricSeqId());

			} else {
				cStmtObject.setBigDecimal(2, new BigDecimal(0));
			}

			if (configVO.getDemoCorpId() == null || configVO.getDemoCorpId().equals("")) {
				cStmtObject.setString(3,"");
			} else {
				
				cStmtObject.setString(3, configVO.getDemoCorpId());
			}
			
			cStmtObject.setString(4, configVO.getDemoCorpNm());
			cStmtObject.setString(5, configVO.getDemoPolicyNo());
			cStmtObject.setString(6, configVO.getDemoprodNm());
			cStmtObject.setString(7, "");

			if (configVO.getCovrgSrartDate() != null) {
				cStmtObject.setDate(8, TTKCommon.convertStringSqlDate(configVO
						.getCovrgSrartDate()));
			} else {
				cStmtObject.setString(8, "");
			}
			if (configVO.getCovrgEndDate() != null) {
				cStmtObject.setDate(9, TTKCommon.convertStringSqlDate(configVO
						.getCovrgEndDate()));// LETTER_DATE
			} else {
				cStmtObject.setString(9, "");
			}
			cStmtObject.setString(10, configVO.getDemoAuthType());
			cStmtObject.setString(11, configVO.getDemoInsCompNm());
			cStmtObject.setString(12, configVO.getDemoAsType());
			cStmtObject.setString(13, configVO.getDemoPolicyDurationPerMonth());

			if (configVO.getDemoNoOfLives() == null)
				cStmtObject.setString(14, "");
			else
				cStmtObject.setString(14, configVO.getDemoNoOfLives());

			if (configVO.getDemoAverageAge() == null)
				cStmtObject.setString(15, "");
			else
				cStmtObject.setString(15, configVO.getDemoAverageAge());

			if (configVO.getDemoMatLives() == null)
				cStmtObject.setString(16, "");
			else
				cStmtObject.setString(16, configVO.getDemoMatLives());

			if (configVO.getDemoMatAvgAge() == null)
				cStmtObject.setString(17, "");
			else
				cStmtObject.setString(17, configVO.getDemoMatAvgAge());

			cStmtObject.setString(18, configVO.getDemoNationality());
			cStmtObject.setString(19, configVO.getDemoIncomeDist());
			cStmtObject.setString(20, configVO.getDemoMaximumBenfitLimit());
			cStmtObject.setString(21, configVO.getDemoNetwork());
			cStmtObject.setString(22, configVO.getOpAreaCov());
			cStmtObject.setString(23, configVO.getDemoDrugsLimit());
			cStmtObject.setString(24, configVO.getDemoDrugsCopay());
			cStmtObject.setString(25, configVO.getDemoLabCopayDudct());
			cStmtObject.setString(26, configVO.getDemoConsltDudct());
			cStmtObject.setString(27, configVO.getDemoPhysioLimit());
			cStmtObject.setString(28, configVO.getDemoPhysioCopay());
			cStmtObject.setString(29, configVO.getDemoIpAoc());
			cStmtObject.setString(30, configVO.getDemoRoomType());
			cStmtObject.setString(31, configVO.getDemoChildAgeLimit());
			cStmtObject.setString(32, configVO.getDemoCompPerDayLimit());
			cStmtObject.setString(33, configVO.getDemoIpCopay());
			cStmtObject.setString(34, configVO.getDemoMatOpVisits());
			cStmtObject.setString(35, configVO.getDemoNrmlDeliLimit());
			cStmtObject.setString(36, configVO.getDemoCSecLimit());
			cStmtObject.setString(37, configVO.getDemoMatOpCopay());
			cStmtObject.setString(38, configVO.getDemoMatIpCopay());
			cStmtObject.setString(39, configVO.getShowBox());
			cStmtObject.setString(40, configVO.getDemoRecType());
			cStmtObject.setString(41, configVO.getIpCoverage());
			cStmtObject.setString(42, configVO.getOpCoverage());
			cStmtObject.setString(43, configVO.getMatIpCoverage());
			cStmtObject.setString(44, configVO.getMatOpCoverage());

			cStmtObject.registerOutParameter(45, Types.INTEGER);
			cStmtObject.registerOutParameter(1, Types.NUMERIC);
			cStmtObject.registerOutParameter(35, Types.VARCHAR);
			cStmtObject.registerOutParameter(36, Types.VARCHAR);
			cStmtObject.registerOutParameter(39, Types.VARCHAR);

			cStmtObject.execute();
			conn.commit();
			iResult = (long)cStmtObject.getInt(45);

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			// /nested ry Catch to ensure resource closure
			try // First try closing the Statement

			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl saveDemographicData()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl saveDemographicData()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteInsuranceCompany(String strInsSeqID)

	public HashMap getGrossPremiumData(long GroupProfileSeqID)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		String showMsg = "N";
		MasterFactorVO masterFactorVO = null;
		SwPricingSummaryVO summaryVO = null;
		HashMap map = new HashMap();
		ArrayList listCursor1 = new ArrayList();
		ArrayList listCursor2 = new ArrayList();
		ArrayList listCursor3 = new ArrayList();
		ArrayList listCursor4 = new ArrayList();
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSelectGrossPremiumData);
			cStmtObject.setLong(1, GroupProfileSeqID);
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.registerOutParameter(3, Types.OTHER);
			cStmtObject.registerOutParameter(4, Types.OTHER);
			cStmtObject.registerOutParameter(5, Types.OTHER);
			cStmtObject.registerOutParameter(6, Types.VARCHAR);
			cStmtObject.execute();

			rs1 = (java.sql.ResultSet) cStmtObject.getObject(2);
			rs2 = (java.sql.ResultSet) cStmtObject.getObject(3);
			rs3 = (java.sql.ResultSet) cStmtObject.getObject(4);
			rs4 = (java.sql.ResultSet) cStmtObject.getObject(5);
			String comments = cStmtObject.getString(6);

			if (rs1 != null) {
				while (rs1.next()) {
					summaryVO = new SwPricingSummaryVO();

					if (rs1.getString("RISK_SEQ_ID") != null) {
						summaryVO.setRiskSeqId(TTKCommon.checkNull(rs1
								.getString("RISK_SEQ_ID")));
					}

					if (rs1.getString("PRI_SEQ_ID") != null) {
						summaryVO.setPricSeqId(TTKCommon.checkNull(rs1
								.getString("PRI_SEQ_ID")));
					}

					if (rs1.getString("GENERAL_TYPE_ID") != null) {
						summaryVO.setGeneralTypeId(TTKCommon.checkNull(rs1
								.getString("GENERAL_TYPE_ID")));
					}

					if (rs1.getString("RISK_DESC") != null) {
						summaryVO.setRiscDescription(TTKCommon.checkNull(rs1
								.getString("RISK_DESC")));
					}

					if (rs1.getString("RISK_LOAD") != null) {
						summaryVO.setRiskLoad(rs1.getFloat("RISK_LOAD"));
					}

					if (rs1.getString("DETAIL") != null) {
						summaryVO.setDetail(TTKCommon.checkNull(rs1
								.getString("DETAIL")));
					}

					if (rs1.getString("RISK_DESC_TYP") != null) {
						summaryVO.setRiscDescriptionType(TTKCommon
								.checkNull(rs1.getString("RISK_DESC_TYP")));
					}

					listCursor1.add(summaryVO);

				}// End of while (rs.next())

			}

			if (rs2 != null) {
				while (rs2.next()) {
					summaryVO = new SwPricingSummaryVO();

					if (rs2.getString("BENEFIT") != null) {
						summaryVO.setBenefit(TTKCommon.checkNull(rs2
								.getString("BENEFIT")));
					}

					if (rs2.getString("RISK_PREM_AED") != null) {
						summaryVO.setRiskPremAED(TTKCommon.checkNull(rs2
								.getString("RISK_PREM_AED")));
					}

					if (rs2.getString("SHOW_MSG") != null) {
						summaryVO.setShowMsg(TTKCommon.checkNull(rs2
								.getString("SHOW_MSG")));

					}

					log.info("SHOW_MSG:" + summaryVO.getShowMsg());

					/*
					 * if("N".equalsIgnoreCase(rs2.getString("SHOW_MSG"))){
					 * summaryVO.setShowMsg("Y"); }
					 */
					listCursor2.add(summaryVO);
				}// End of while (rs.next())

			}

			if (rs3 != null) {
				while (rs3.next()) {
					summaryVO = new SwPricingSummaryVO();

					if (rs3.getString("RISK_SEQ_ID") != null) {
						summaryVO.setRiskSeqId(TTKCommon.checkNull(rs3
								.getString("RISK_SEQ_ID")));
					}

					if (rs3.getString("PRI_SEQ_ID") != null) {
						summaryVO.setPricSeqId(TTKCommon.checkNull(rs3
								.getString("PRI_SEQ_ID")));
					}

					if (rs3.getString("GENERAL_TYPE_ID") != null) {
						summaryVO.setGeneralTypeId(TTKCommon.checkNull(rs3
								.getString("GENERAL_TYPE_ID")));
					}

					if (rs3.getString("RISK_DESC") != null) {
						summaryVO.setRiscDescription(TTKCommon.checkNull(rs3
								.getString("RISK_DESC")));
					}

					if (rs3.getString("RISK_LOAD") != null) {
						summaryVO.setGrossRiskLoad(rs3.getFloat("RISK_LOAD"));
					}

					if (rs3.getString("DETAIL") != null) {
						summaryVO.setDetail(TTKCommon.checkNull(rs3
								.getString("DETAIL")));
					}

					if (rs3.getString("RISK_DESC_TYP") != null) {
						summaryVO.setRiscDescriptionType(TTKCommon
								.checkNull(rs3.getString("RISK_DESC_TYP")));
					}

					listCursor3.add(summaryVO);
				}// End of while (rs.next())

			}

			if (rs4 != null) {
				while (rs4.next()) {
					summaryVO = new SwPricingSummaryVO();

					if (rs4.getString("BENEFIT") != null) {
						summaryVO.setBenefit(TTKCommon.checkNull(rs4
								.getString("BENEFIT")));
					}

					if (rs4.getString("RISK_PREM_AED") != null) {
						summaryVO.setRiskPremAED(TTKCommon.checkNull(rs4
								.getString("RISK_PREM_AED")));
					}

					listCursor4.add(summaryVO);
				}// End of while (rs.next())

			}

			map.put("cursor1", listCursor1);
			map.put("cursor2", listCursor2);
			map.put("cursor3", listCursor3);
			map.put("cursor4", listCursor4);
			map.put("comments", comments);
			return map;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs1 != null)
						rs1.close();
					if (rs2 != null)
						rs2.close();
					if (rs3 != null)
						rs3.close();
					if (rs4 != null)
						rs4.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceDAOImpl getGrossPremiumData()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getGrossPremiumData()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getGrossPremiumData()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs1 = null;
				rs2 = null;
				rs3 = null;
				rs4 = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally

	}

	public Long saveRiskPremiumData(ArrayList updatedDataList, String flag)
			throws TTKException {
		Long iResult = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveGrossPremiumData);

			for (int i = 0; i < updatedDataList.size(); i++) {
				SwPricingSummaryVO configVO = (SwPricingSummaryVO) updatedDataList
						.get(i);

				if (configVO.getRiskSeqId() != null) {
					cStmtObject.setBigDecimal(1, new BigDecimal(configVO.getRiskSeqId()));
				}

				if (configVO.getPricSeqId() != null) {

					cStmtObject.setBigDecimal(2, new BigDecimal(configVO.getPricSeqId()));

				}

				cStmtObject.setString(3, configVO.getGeneralTypeId());
				cStmtObject.setString(4, configVO.getRiscDescription());

				if ("cursor1".equalsIgnoreCase(flag)) {
					cStmtObject.setFloat(5, configVO.getRiskLoad());
				} else {
					cStmtObject.setFloat(5, configVO.getGrossRiskLoad());
				}

				cStmtObject.setString(6, configVO.getDetail());
				cStmtObject.setString(7, configVO.getRiscDescriptionType());
				cStmtObject.setString(8, configVO.getComments());
				cStmtObject.setLong(9, configVO.getAddedBy());

				log.info("getAddedBy:" + configVO.getAddedBy());

				cStmtObject.registerOutParameter(10, Types.INTEGER);
				cStmtObject.registerOutParameter(1, Types.NUMERIC);
				cStmtObject.execute();
				conn.commit();
				iResult = (long)cStmtObject.getInt(10);

			}

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			// /nested ry Catch to ensure resource closure
			try // First try closing the Statement

			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl saveDemographicData()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl saveDemographicData()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteInsuranceCompany(String strInsSeqID)

	public InsPricingVO swFetchScreen1(InsPricingVO insPricingVO)
			throws TTKException {
		Connection conn = null;
		// Long lpricingSeqId = 1l;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;

		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strFetchDetails);

			if (insPricingVO.getPortfloLevel() != null) {
				cStmtObject.setString(1, insPricingVO.getPortfloLevel());
			}
			if (insPricingVO.getPreviousPolicyNo() != null) {
				cStmtObject.setString(2, insPricingVO.getPreviousPolicyNo());
			}

			if (insPricingVO.getPreviousProductName() != null) {
				cStmtObject.setString(3, insPricingVO.getPreviousProductName());
			}

			if (insPricingVO.getIncomeGroup() != null) {
				cStmtObject.setString(4, insPricingVO.getIncomeGroup());
			}

			// cStmtObject.setLong(5,lpricingSeqId);//bk
			if (insPricingVO.getGroupProfileSeqID() != null) {
				cStmtObject.setLong(5, insPricingVO.getGroupProfileSeqID());
			}

			cStmtObject.registerOutParameter(6, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(6);
			if (rs != null) {
				while (rs.next()) {

					/*
					 * insPricingVO.setClientCode(TTKCommon.checkNull(rs.getString
					 * ("CLIENT_CODE")));
					 * insPricingVO.setClientName(TTKCommon.checkNull
					 * (rs.getString("CLIENT_NAME")));
					 * 
					 * insPricingVO.setCoverStartDate(rs.getString(
					 * "COVRG_START_DATE")!=null ? new
					 * Date(rs.getTimestamp("COVRG_START_DATE"
					 * ).getTime()):null);
					 * insPricingVO.setCoverEndDate(rs.getString
					 * ("COVRG_END_DATE")!=null ? new
					 * Date(rs.getTimestamp("COVRG_END_DATE").getTime()):null);
					 * 
					 * insPricingVO.setTotalCovedLives(TTKCommon.checkNull(rs.
					 * getString("TOT_COV_LIVES")));
					 * 
					 * insPricingVO.setAdministrativeServiceType(TTKCommon.checkNull
					 * (rs.getString("ADMIN_SERV_TYP")));
					 * 
					 * 
					 * 
					 * insPricingVO.setInsuranceCompanyName(TTKCommon.checkNull(rs
					 * .getString("INS_COMP_NAME")));
					 */

					insPricingVO.setInpatientBenefit(TTKCommon.checkNull(rs
							.getString("IP_COVR")));
					insPricingVO.setOutpatientBenefit(TTKCommon.checkNull(rs
							.getString("OP_COVR")));
					insPricingVO.setMaxBenifitList(TTKCommon.checkNull(rs
							.getString("MAX_BEN_LIMIT")));
					insPricingVO.setNetworkList(TTKCommon.checkNull(rs
							.getString("NETWORK_TYPE")));
					insPricingVO.setInpatientAreaOfCover(TTKCommon.checkNull(rs
							.getString("INPAT_AREA_COV")));

					insPricingVO.setRoomType1(TTKCommon.checkNull(rs
							.getString("ROOM_TYP")));
					insPricingVO.setInpatientCopay(TTKCommon.checkNull(rs
							.getString("INPAT_COPAY")));
					insPricingVO.setMaxChildAgeLimit(TTKCommon.checkNull(rs
							.getString("MAX_CHILD_AGELMT_COMP")));
					insPricingVO.setCompanionCharges(TTKCommon.checkNull(rs
							.getString("COMP_CHGS_PER_DAY")));
					insPricingVO.setOutpatientAreaOfCover(TTKCommon
							.checkNull(rs.getString("OP_AREA_COV")));

					insPricingVO.setConsultationCD(TTKCommon.checkNull(rs
							.getString("CONS_COPAY_DED")));
					insPricingVO.setDrugsLimit(TTKCommon.checkNull(rs
							.getString("DRUGS_LMT")));
					insPricingVO.setDrugsCopay(TTKCommon.checkNull(rs
							.getString("DRUGS_COPAY")));
					insPricingVO.setLaboratoryCD(TTKCommon.checkNull(rs
							.getString("LAB_COPAY_DED")));
					insPricingVO.setPhysiotherapyLimit(TTKCommon.checkNull(rs
							.getString("PHYSIO_LMT")));
					insPricingVO.setPhysiotherapyCopay(TTKCommon.checkNull(rs
							.getString("PHYSIO_COPAY")));

					insPricingVO.setMaternityInpatient(rs.getString("MAT_IP"));
					insPricingVO.setMaternityOutpatient(rs.getString("MAT_OP"));
					insPricingVO.setTotalLivesMaternity(TTKCommon.checkNull(rs
							.getString("TOT_COV_LV_MAT")));

					insPricingVO.setNormalDeliveryLimit(TTKCommon.checkNull(rs
							.getString("NOR_DEL_LMT")));
					insPricingVO.setCSectionLimit(TTKCommon.checkNull(rs
							.getString("C_SEC_LMT")));
					insPricingVO.setMaternityInpatientCopay(TTKCommon
							.checkNull(rs.getString("MAT_IP_COPAY")));
					insPricingVO.setMaternityOutpatientVisits(TTKCommon
							.checkNull(rs.getString("MAT_OP_VISTS")));
					insPricingVO.setMaternityOutpatientCopay(TTKCommon
							.checkNull(rs.getString("MAT_OP_COPAY")));
					insPricingVO.setAuthorityType(TTKCommon.checkNull(rs
							.getString("AUTH_TYPE")));

				}// end of while(rs.next())
			}// End of if(rs!=null)
			return insPricingVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the result set
			{
				try {
					if (rs != null) {
						rs.close();

					}
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the statement now.
				{
					try {
						if (cStmtObject != null) {
							cStmtObject.close();
						}

					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	public Long saveTable2Data(ArrayList<SwPolicyConfigVO> alTable2Data)
			throws TTKException {

		Long iResult = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			for (SwPolicyConfigVO configVO : alTable2Data) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strSaveTable2Data);

				if (configVO.getDemoCovSeqId() == null || configVO.getDemoCovSeqId().equals("")) {
					cStmtObject.setBigDecimal(1,new BigDecimal(0));
				} else {
					cStmtObject.setBigDecimal(1,new BigDecimal(configVO.getDemoCovSeqId()));
				}

				if (configVO.getDemoPricSeqId() == null || configVO.getDemoPricSeqId().equals("")) {

					cStmtObject.setBigDecimal(2,new BigDecimal(0));
				} 
				else {
					cStmtObject.setBigDecimal(2, new BigDecimal(configVO.getDemoPricSeqId()));
				}

				if (configVO.getCovrgSrartDate() == null || configVO.getCovrgSrartDate().equals("")) {
					cStmtObject.setTimestamp(3, null);
				} else {
					cStmtObject.setDate(3, TTKCommon.convertStringSqlDate(configVO.getCovrgSrartDate()));
				}
				if (configVO.getCovrgEndDate() == null || configVO.getCovrgEndDate().equals("")) {
					cStmtObject.setTimestamp(4, null);
				} else {
					cStmtObject.setDate(4, TTKCommon.convertStringSqlDate(configVO.getCovrgEndDate()));// LETTER_DATE
				}

				if(configVO.getDemoCostIp() == null || configVO.getDemoCostIp().equals("")){
					cStmtObject.setDouble(5, 0.0);
				}
				else{
					cStmtObject.setDouble(5, new Double(configVO.getDemoCostIp()));
				}
				
				if(configVO.getDemoCostOp() == null || configVO.getDemoCostOp().equals("")){
					cStmtObject.setDouble(6, 0.0);
				}
				else{
					cStmtObject.setDouble(6, new Double(configVO.getDemoCostOp()));
				}
				if(configVO.getDemoCostMatIp() == null || configVO.getDemoCostMatIp().equals("")){
					cStmtObject.setDouble(7, 0.0);
				}
				else{
					cStmtObject.setDouble(7, new Double(configVO.getDemoCostMatIp()));
				}
				if(configVO.getDemoCostMatOp() == null || configVO.getDemoCostMatOp().equals("")){
					cStmtObject.setDouble(8, 0.0);
				}
				else{
					cStmtObject.setDouble(8, new Double(configVO.getDemoCostMatOp()));
				}
				
				if(configVO.getDemoCredIp() == null || configVO.getDemoCredIp().equals("")){
					cStmtObject.setDouble(9, 0.0);
				}
				else{
					cStmtObject.setDouble(9, new Double(configVO.getDemoCredIp()));
				}
				
				if(configVO.getDemoCredOp() == null || configVO.getDemoCredOp().equals("")){
					cStmtObject.setDouble(10, 0.0);
				}
				else{
					cStmtObject.setDouble(10, new Double(configVO.getDemoCredOp()));
				}
				
				if(configVO.getDemoCredMatIp() == null || configVO.getDemoCredMatIp().equals("")){
					cStmtObject.setDouble(11, 0.0);
				}
				else{
					cStmtObject.setDouble(11, new Double(configVO.getDemoCredMatIp()));
				}
				
				if(configVO.getDemoCredMatOp() == null || configVO.getDemoCredMatOp().equals("")){
					cStmtObject.setDouble(12, 0.0);
				}
				else{
					cStmtObject.setDouble(12, new Double(configVO.getDemoCredMatOp()));
				}
				cStmtObject.setString(13, configVO.getShowBox());
				cStmtObject.setString(14, configVO.getDemoRecType());
				cStmtObject.registerOutParameter(15, Types.INTEGER);
				cStmtObject.registerOutParameter(1, Types.NUMERIC);
				cStmtObject.registerOutParameter(13, Types.VARCHAR);
				cStmtObject.execute();
				conn.commit();
				iResult = (long)cStmtObject.getInt(15);
				if (cStmtObject != null)
					cStmtObject.close();
			}// for(SwPolicyConfigVO configVO:alTable2Data){

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			// /nested ry Catch to ensure resource closure
			try // First try closing the Statement

			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl saveTable2Data()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl saveTable2Data()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;

	}

	public Long saveTable3Data(ArrayList<HashMap<String, String>> alHmTable3Data)
			throws TTKException {

		Long iResult = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			for (HashMap<String, String> hmValues : alHmTable3Data) {
				cStmtObject = (java.sql.CallableStatement) conn
						.prepareCall(strSaveTable3Data);

				cStmtObject.setBigDecimal(1, new BigDecimal(hmValues.get("PRI_SEQ_ID")));
				cStmtObject.setString(2, hmValues.get("PW_NAME"));
				cStmtObject.setString(3, hmValues.get("PW_CODE"));
				if(hmValues.get("PW_IP") == null || hmValues.get("PW_IP").equals("")){
					cStmtObject.setDouble(4, 0.0);
				}
				else{
				cStmtObject.setDouble(4, new Double(hmValues.get("PW_IP")));
				}
                if(hmValues.get("PW_OP") == null || hmValues.get("PW_OP").equals("")){
                	cStmtObject.setDouble(5, 0.0);
				}
				else{
				cStmtObject.setDouble(5, new Double(hmValues.get("PW_OP")));
				}
                if(hmValues.get("PW_MAT_IP") == null || hmValues.get("PW_MAT_IP").equals("")){
                	cStmtObject.setDouble(6, 0.0);
				}
				else{
				cStmtObject.setDouble(6, new Double(hmValues.get("PW_MAT_IP")));
				}
                if(hmValues.get("PW_MAT_OP") == null || hmValues.get("PW_MAT_OP").equals("")){
                	cStmtObject.setDouble(7,0.0);
				}
				else{
				cStmtObject.setDouble(7, new Double(hmValues.get("PW_MAT_OP")));
				}
				cStmtObject.setString(8, hmValues.get("COMM"));
				cStmtObject.registerOutParameter(9, Types.INTEGER);
				cStmtObject.registerOutParameter(1, Types.NUMERIC);
				cStmtObject.registerOutParameter(4, Types.DOUBLE);
				cStmtObject.registerOutParameter(5, Types.DOUBLE);
				cStmtObject.registerOutParameter(6, Types.DOUBLE);
				cStmtObject.registerOutParameter(7, Types.DOUBLE);
				cStmtObject.execute();
				conn.commit();
				iResult = (long)cStmtObject.getInt(9);
				if (cStmtObject != null)
					cStmtObject.close();
			}// for(SwPolicyConfigVO configVO:alTable2Data){

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			// /nested ry Catch to ensure resource closure
			try // First try closing the Statement

			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl saveTable3Data()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl saveTable3Data()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;

	}

	public ArrayList<AreaOfCoverVO> getPriceOpAOC() throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		AreaOfCoverVO areaOfCoverVO = null;
		ArrayList<AreaOfCoverVO> opAocList = new ArrayList<AreaOfCoverVO>();

		try {

			conn = ResourceManager.getConnection();

			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSelectFactorAOC);
			cStmtObject.registerOutParameter(1, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(1);

			if (rs != null) {
				while (rs.next()) {
					areaOfCoverVO = new AreaOfCoverVO();
					if (rs.getString("FACT_ID") != null) {
						areaOfCoverVO.setFactId(rs.getString("FACT_ID"));
						log.info("fact_id" + rs.getString("FACT_ID"));
					}
					if (rs.getString("DESCRIPTN") != null) {
						areaOfCoverVO.setDescription(rs.getString("DESCRIPTN"));
						log.info("DESC " + rs.getString("DESCRIPTN"));
					}

					if (rs.getString("HIDE_VAL") != null) {
						areaOfCoverVO.setNetwork(rs.getString("HIDE_VAL"));
						log.info("Network" + rs.getString("HIDE_VAL"));
					}

					opAocList.add(areaOfCoverVO);
				}// End of while (rs.next())

			}
			return opAocList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
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
							"Error while closing the Resultset in InsuranceDAOImpl getInsuranceCompanyDetail()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Statement in InsuranceDAOImpl getInsuranceCompanyDetail()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches
							// here. Try closing the connection now.
					{
						try {
							if (conn != null) {
								conn.close();
							}
						}// end of try
						catch (SQLException sqlExp) {
							log.error(
									"Error while closing the Connection in InsuranceDAOImpl getInsuranceCompanyDetail()",
									sqlExp);
							throw new TTKException(sqlExp, "insurance");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
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

	public Long savePriceOpAOC(ArrayList<AreaOfCoverVO> opAocList)
			throws TTKException {
		log.info("into savePriceOpAOC");
		Long iResult = null;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveFactorAOC);

			for (AreaOfCoverVO areaOfCoverVO : opAocList) {

				cStmtObject.setDouble(1, new Double(areaOfCoverVO.getDescription()));
				log.info("Description:" + areaOfCoverVO.getDescription());
				cStmtObject.setString(2, areaOfCoverVO.getFactId());
				log.info("FactId:" + areaOfCoverVO.getFactId());
				cStmtObject.setString(3, areaOfCoverVO.getNetwork());
				log.info("Network:" + areaOfCoverVO.getNetwork());
				cStmtObject.registerOutParameter(4, Types.INTEGER);
				cStmtObject.execute();
				conn.commit();
				iResult = (long)cStmtObject.getInt(4);

			}

		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			// /nested ry Catch to ensure resource closure
			try // First try closing the Statement

			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl saveFactorMaster()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (conn != null) {
							conn.close();
							log.info("connection is closed saveFactorMaster");
						}
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in InsuranceDAOImpl saveFactorMaster()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteInsuranceCompany(String strInsSeqID)

	public String PricingUploadExcel(String batchNo, FileReader fileReader,
			int fileLength, Long pricingseqid) throws TTKException {
		Connection con = null;
		CallableStatement statement = null;
		CallableStatement statement2 = null;
		String status = "";
		try {

			con = ResourceManager.getConnection();

			statement = con.prepareCall(strSavememberXml);

			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(fileReader);

			String filedetailNode = document.selectSingleNode("batch").asXML();

			StringBuilder builder;
			builder = new StringBuilder();
			builder.append(filedetailNode);

			String oneMemberEntry = builder.toString();

			SQLXML sqlXML = null;
			if (oneMemberEntry != null) {
				sqlXML = con.createSQLXML();
				sqlXML.setString(oneMemberEntry);		
			}
			statement.setLong(1, pricingseqid);
			System.out.println("SQLXML---------"+sqlXML.getString());
			statement.setSQLXML(2, sqlXML);
			statement.registerOutParameter(3, Types.VARCHAR);// success/error
																// message
			statement.registerOutParameter(4, Types.NUMERIC);
			statement.execute();
			con.commit();
			status = statement.getString(3);

		} catch (TTKException sqlExp) {
			throw new TTKException(sqlExp, "cheque");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "cheque");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (statement != null)
						statement.close();
					if (statement2 != null)
						statement2.close();

				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Connection in ChequeDAOImpl ChequeUploadExcel()",
							sqlExp);
					throw new TTKException(sqlExp, "cheque");
				}// end of catch (SQLException sqlExp)
				finally // Even if statement is not closed, control reaches
						// here. Try closing the connection now.
				{
					try {
						if (con != null)
							con.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Connection in ChequeDAOImpl ChequeUploadExcel()",
								sqlExp);
						throw new TTKException(sqlExp, "cheque");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "cheque");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				statement = null;
				statement2 = null;
				con = null;

			}// end of finally
		}// end of finally
		return status;
	}

	public ArrayList getVatProductList(InsuranceDetailVO insDetailVO)
			throws TTKException {

		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		InsuranceDetailVO insObjVO = null;

		try {
			conn = ResourceManager.getConnection();

			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strVatProductList);

			cStmtObject.setString(1, insDetailVO.getCompanyCode());
			
			if (insDetailVO.getInsuranceSeqID()!=null){
				cStmtObject.setLong(2, insDetailVO.getInsuranceSeqID());// ins seqid
			}//end of if (insDetailVO.getInsuranceSeqID()!=null)
			else{
				cStmtObject.setLong(2,0);//ins seqid
			}//end of else
			
		//	cStmtObject.setLong(2, insDetailVO.getInsuranceSeqID());// ins seqid
														
			cStmtObject.registerOutParameter(3, Types.OTHER);

			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(3);

			if (rs != null) {
				while (rs.next()) {

					insObjVO = new InsuranceDetailVO();
					insObjVO.setProductSeqId(TTKCommon.checkNull(rs
							.getString("product_seq_id")));
					insObjVO.setProductName(TTKCommon.checkNull(rs
							.getString("Product_Name")));
					insObjVO.setProductCode(TTKCommon.checkNull(rs
							.getString("ins_product_code")));
					insObjVO.setProductType(TTKCommon.checkNull(rs
							.getString("Product_type")));
					insObjVO.setNetworkType(TTKCommon.checkNull(rs
							.getString("net_work")));
					insObjVO.setDescription(TTKCommon.checkNull(rs
							.getString("description")));
					insObjVO.setVatPercent(rs.getDouble("vat_percent"));

					insObjVO.setInsuranceSeqID(rs.getLong("ins_seq_id"));

					alResultList.add(insObjVO);
				}
			}
		} catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {
			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{

				try {
					if (rs != null) {
						rs.close();
						
					}
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Resultset in InsuranceDAOImpl getVatProductList()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
				}// end of catch (SQLException sqlExp)

				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl getVatProductList()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Connection in InsuranceDAOImpl getVatProductList()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return (ArrayList) alResultList;
	}

	public String saveVatProductList(String insSeqId, String productId,
			String refFlag) throws TTKException {

		ResultSet rs = null;
		String lResult = "";
		Connection conn = null;
		String mark_flag = "Y";
		CallableStatement cStmtObject = null;
		try {

			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveVatProductList);

			cStmtObject.setString(1, productId);// productId
			cStmtObject.setString(2, insSeqId);// insSeqId
			cStmtObject.setString(3, refFlag);// flag
			cStmtObject.registerOutParameter(4, OracleTypes.CURSOR);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(4);
			if (rs != null) {
				while (rs.next()) {

					lResult = TTKCommon.checkNull(rs.getString("mark_flag"));
				}
			}
		}

		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "insurance");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch (Exception exp)
		finally {

			try {
				if (rs != null) {
					rs.close();
				}
			}// end of try
			catch (SQLException sqlExp) {
				log.error(
						"Error while closing the Resultset in InsuranceDAOImpl saveVatProductList()",
						sqlExp);
				throw new TTKException(sqlExp, "insurance");
			}// end of catch (SQLException sqlExp)

			/* Nested Try Catch to ensure resource closure */
			try // First try closing the Statement
			{
				try {
					if (cStmtObject != null)
						cStmtObject.close();
				}// end of try
				catch (SQLException sqlExp) {
					log.error(
							"Error while closing the Statement in InsuranceDAOImpl saveVatProductList()",
							sqlExp);
					throw new TTKException(sqlExp, "insurance");
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
								"Error while closing the Connection in InsuranceDAOImpl saveVatProductList()",
								sqlExp);
						throw new TTKException(sqlExp, "insurance");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "insurance");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return lResult;
	}
}// end of InsuranceDAOImpl
