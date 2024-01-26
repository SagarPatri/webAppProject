/**
 * @ (#) GroupRegistrationDAOImpl.java Dec 29, 2005
 * Project       : TTK HealthCare Services
 * File          : GroupRegistrationDAOImpl.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Dec 29, 2005
 * @author       : Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.empanelment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import javax.print.attribute.standard.MediaSize.Other;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.NotifyDetailVO;
import com.ttk.dto.empanelment.NotificationInfoVO;
import com.ttk.dto.empanelment.GroupRegistrationVO;
import com.ttk.dto.usermanagement.UserListVO;

public class GroupRegistrationDAOImpl implements Serializable, BaseDAO {

	private static Logger log = Logger
			.getLogger(GroupRegistrationDAOImpl.class);

	private static final String strGroupList = "SELECT * FROM( SELECT A.*, DENSE_RANK() OVER(ORDER BY PARENT_NAME ASC) Q FROM (SELECT GROUP_REG_SEQ_ID ,GROUP_NAME || ' - ' || GROUP_ID PARENT_NAME, GROUP_ID FROM APP.TPA_GROUP_REGISTRATION A WHERE COALESCE(PARENT_GROUP_SEQ_ID,0) = 0 ";
	private static final String strBranchList = "SELECT GROUP_REG_SEQ_ID, GROUP_NAME CHILD_NAME, OFFICE_NUMBER FROM APP.TPA_GROUP_REGISTRATION A WHERE PARENT_GROUP_SEQ_ID  = ?";
	// private static final String strGroupRegDetail =
	// "SELECT * FROM ( SELECT A.GROUP_REG_SEQ_ID ,A.GROUP_NAME ,A.GROUP_ID,A.GROUP_GENERAL_TYPE_ID,A.TPA_OFFICE_SEQ_ID,A.PARENT_GROUP_SEQ_ID,B.ADDR_SEQ_ID ,B.ADDRESS_1 ,B.ADDRESS_2 ,B.ADDRESS_3 , B.STATE_TYPE_ID, B.CITY_TYPE_ID ,B.COUNTRY_ID ,B.PIN_CODE ,B.LANDMARKS , NVL(A.LOCATION_CODE,A.GROUP_ID||'-'||NVL(A.OFFICE_NUMBER,'000')) AS LOCATION_CODE FROM TPA_GROUP_REGISTRATION A JOIN TPA_ADDRESS B ON (A.GROUP_REG_SEQ_ID = B.GROUP_REG_SEQ_ID )  WHERE A.GROUP_REG_SEQ_ID = ?)";
	// private static final String strGroupRegDetail =
	// "SELECT * FROM ( SELECT A.GROUP_REG_SEQ_ID ,A.GROUP_NAME ,A.GROUP_ID,A.GROUP_GENERAL_TYPE_ID,A.TPA_OFFICE_SEQ_ID,A.PARENT_GROUP_SEQ_ID,B.ADDR_SEQ_ID ,B.ADDRESS_1 ,B.ADDRESS_2 ,B.ADDRESS_3 , B.STATE_TYPE_ID, B.CITY_TYPE_ID ,B.COUNTRY_ID ,B.PIN_CODE ,B.LANDMARKS , NVL(A.LOCATION_CODE,A.GROUP_ID||'-'||NVL(A.OFFICE_NUMBER,'000')) AS LOCATION_CODE, A.ACC_MGR_CONTACT_SEQ_ID,C.CONTACT_NAME,A.NOTIFY_EMAIL_ID,A.CC_EMAIL_ID,A.HR_EMAIL_ID,A.NOTIFY_TYPE_ID,A.EMAIL_ID  FROM TPA_GROUP_REGISTRATION A JOIN TPA_ADDRESS B ON (A.GROUP_REG_SEQ_ID = B.GROUP_REG_SEQ_ID ) LEFT OUTER JOIN TPA_USER_CONTACTS C ON (A.ACC_MGR_CONTACT_SEQ_ID = C.CONTACT_SEQ_ID) WHERE A.GROUP_REG_SEQ_ID = ?)";//added
	// private static final String strSaveGroupReg =
	// "{CALL CONTACT_PKG.SAVE_GROUP_REG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//added
	private static final String strGroupRegDetail = "SELECT * FROM (SELECT A.GROUP_REG_SEQ_ID ,A.GROUP_NAME ,A.GROUP_ID,A.TRADELICENSE_NUMBER,A.GROUP_GENERAL_TYPE_ID,A.TPA_OFFICE_SEQ_ID,A.PARENT_GROUP_SEQ_ID,A.PREF_PROV_NTW_FLAG,B.ADDR_SEQ_ID ,B.ADDRESS_1 ,B.ADDRESS_2 ,B.ADDRESS_3 ,B.STATE_TYPE_ID, B.CITY_TYPE_ID ,B.COUNTRY_ID ,B.PIN_CODE ,B.LANDMARKS ,coalesce(A.LOCATION_CODE,A.GROUP_ID||'-'||coalesce(A.OFFICE_NUMBER,'000')) AS LOCATION_CODE,A.ACC_MGR_CONTACT_SEQ_ID,C.CONTACT_NAME,A.NOTIFY_EMAIL_ID,A.CC_EMAIL_ID,A.HR_EMAIL_ID,A.NOTIFY_TYPE_ID,A.EMAIL_ID,B.ISD_CODE,B.STD_CODE,B.OFFICE_PHONE1,B.OFFICE_PHONE2 FROM app.TPA_GROUP_REGISTRATION A JOIN app.TPA_ADDRESS B ON (A.GROUP_REG_SEQ_ID = B.GROUP_REG_SEQ_ID )LEFT OUTER JOIN app.TPA_USER_CONTACTS C ON (A.ACC_MGR_CONTACT_SEQ_ID = C.CONTACT_SEQ_ID) WHERE A.GROUP_REG_SEQ_ID = ?)aa";
	// added
	private static final String strSaveGroupReg = "{CALL CONTACT_PKG_SAVE_GROUP_REG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";// added
																																					// for
																																					// 1
																																					// parmater
																																					// for
																																					// koc1346
	private static final String strDeleteGroupReg = "{CALL CONTACT_PKG_DELETE_GROUP_REG(?,?)}";
	private static final String strAccountManager = "{CALL CONTACT_PKG_SELECT_ACCT_MNGR_LIST(?,?,?,?,?,?,?,?,?)}";
	private static final String strNotificationInfo = "{CALL CONTACT_PKG_SELECT_NOTIFICATION_LIST(?,?)}";
	private static final String strSaveNotificationInfo = "{CALL CONTACT_PKG_SAVE_NOTIFICATION_INFO(?,?,?,?)}";
	// added for CR Mail-SMS Notification for Signa.
	private static final String strMailSMSNotification = "{CALL CONTACT_PKG_select_notification_INS_list(?,?)}";
	private static final String strSaveMailNotificationInfo = "{CALL CONTACT_PKG_save_notification_ins_info(?,?,?,?)}";
	// intX
	private static final String strGetInsAbbreviation = "SELECT I.ABBREVATION_CODE FROM APP.TPA_INS_INFO I WHERE I.INS_SEQ_ID IN (SELECT MAX(INS_SEQ_ID) FROM APP.TPA_INS_INFO)";

	private static final int GROUP_REG_SEQ_ID = 1;
	private static final int TPA_OFFICE_SEQ_ID = 2;
	private static final int GROUP_GENERAL_TYPE_ID = 3;
	private static final int GROUP_NAME = 4;
	private static final int GROUP_ID = 5;
	private static final int TRADELICENSE_NUMBER = 6;
	private static final int PARENT_GROUP_SEQ_ID = 7;
	private static final int ADDR_SEQ_ID = 8;
	private static final int ADDRESS_1 = 9;
	private static final int ADDRESS_2 = 10;
	private static final int ADDRESS_3 = 11;
	private static final int STATE_TYPE_ID = 12;
	private static final int CITY_TYPE_ID = 13;
	private static final int COUNTRY_ID = 14;
	private static final int PIN_CODE = 15;
	private static final int LOCATION_CODE = 16;
	private static final int ACC_MGR_CONTACT_SEQ_ID = 17;
	private static final int NOTIFY_EMAIL_ID = 18;
	private static final int NOTIFY_TYPE_ID = 19;
	private static final int CC_EMAIL_ID = 20;
	private static final int EMAIL_ID = 21;
	private static final int HREMAIL_ID = 22;
	private static final int v_PREF_PROV_NTW_FLAG = 23; // added for KOC 1346
	private static final int UPDATEDBY = 24;

	private static final int ISD_CODE = 25;
	private static final int STD_CODE = 26;
	private static final int PHONE1 = 27;
	private static final int PHONE2 = 28;

	private static final int ROWS_PROCESSED = 29;

	/**
	 * This method returns the ArrayList, which contains the
	 * GroupRegistrationVO's which are populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
	 * @return ArrayList of GroupRegistrationVO object's which contains the
	 *         Group Registration Details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchObjects)
			throws TTKException {
		StringBuffer sbfDynamicQuery = new StringBuffer();
		String strStaticQuery = strGroupList;
		GroupRegistrationVO groupRegistrationVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		if (alSearchObjects != null && alSearchObjects.size() > 0) {
			for (int i = 0; i < alSearchObjects.size() - 2; i++) {
				if (!((SearchCriteria) alSearchObjects.get(i)).getValue()
						.equals("")) {
					if (((SearchCriteria) alSearchObjects.get(i)).getOperator()
							.equalsIgnoreCase("equals")) {
						sbfDynamicQuery.append(" AND "
								+ ((SearchCriteria) alSearchObjects.get(i))
										.getName()
								+ " = '"
								+ ((SearchCriteria) alSearchObjects.get(i))
										.getValue() + "' ");
					}// end of
						// if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					else {
						sbfDynamicQuery.append(" AND UPPER("
								+ ((SearchCriteria) alSearchObjects.get(i))
										.getName()
								+ ") LIKE UPPER('"
								+ ((SearchCriteria) alSearchObjects.get(i))
										.getValue() + "%')");
					}// end of else
				}// End of
					// if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}// end of for()
		}// end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		sbfDynamicQuery = sbfDynamicQuery.append(" )A) AA WHERE Q >= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 2)
				+ " AND Q <= "
				+ (String) alSearchObjects.get(alSearchObjects.size() - 1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
			rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					groupRegistrationVO = new GroupRegistrationVO();
					groupRegistrationVO.setGroupRegSeqID(rs
							.getString("GROUP_REG_SEQ_ID") != null ? new Long(
							rs.getLong("GROUP_REG_SEQ_ID")) : null);
					groupRegistrationVO.setGroupName(TTKCommon.checkNull(rs
							.getString("PARENT_NAME")));
					groupRegistrationVO.setGroupID(TTKCommon.checkNull(rs
							.getString("GROUP_ID")));
					resultList.add(groupRegistrationVO);
				}// End of while (rs.next())
			}// End of if (rs!=null)
			return (ArrayList) resultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Resultset in GroupRegistrationDAOImpl getGroupList()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
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
								"Error while closing the Statement in GroupRegistrationDAOImpl getGroupList()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
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
									"Error while closing the Connection in GroupRegistrationDAOImpl getGroupList()",
									sqlExp);
							throw new TTKException(sqlExp, "groupregistration");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// End of getGroupList(ArrayList alSearchObjects)

	/**
	 * This method returns the ArrayList of GroupRegistrationVO's which contains
	 * Branch Group Registration's Details
	 * 
	 * @param lngParentGroupSeqID
	 *            Parent Group Sequence ID
	 * @return alResultList of GroupRegistrationVO which contains the Branch
	 *         Group Registration's Details
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getBranchList(Long lngParentGroupSeqID)
			throws TTKException {
		GroupRegistrationVO groupRegistrationVO = null;
		ArrayList<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strBranchList);
			pStmt.setLong(1, lngParentGroupSeqID);
			rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					groupRegistrationVO = new GroupRegistrationVO();
					groupRegistrationVO.setGroupRegSeqID(rs
							.getString("GROUP_REG_SEQ_ID") != null ? new Long(
							rs.getLong("GROUP_REG_SEQ_ID")) : null);
					groupRegistrationVO.setGroupName(TTKCommon.checkNull(rs
							.getString("CHILD_NAME")));
					alResultList.add(groupRegistrationVO);
				}// End of while (rs.next())
			}// End of if (rs!=null)
			return alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Resultset in GroupRegistrationDAOImpl getBranchList()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
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
								"Error while closing the Statement in GroupRegistrationDAOImpl getBranchList()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
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
									"Error while closing the Connection in GroupRegistrationDAOImpl getBranchList()",
									sqlExp);
							throw new TTKException(sqlExp, "groupregistration");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// End of getBranchList(Long lngParentGroupSeqID)

	/**
	 * This method returns the Group Registration Details Information
	 * 
	 * @param lngGroupRegSeqId
	 *            the group registration sequence id for which the details is
	 *            required
	 * @return GroupRegistrationVO object which contains the Details of the
	 *         Group Reistration
	 * @throws TTKException
	 */
	public GroupRegistrationVO getGroup(Long lngGroupRegSeqId)
			throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		GroupRegistrationVO groupRegistrationVO = new GroupRegistrationVO();
		AddressVO addressVO = new AddressVO();
		try {
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strGroupRegDetail);
			pStmt.setLong(1, lngGroupRegSeqId);
			rs = pStmt.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					groupRegistrationVO.setGroupRegSeqID(rs
							.getString("GROUP_REG_SEQ_ID") != null ? new Long(
							rs.getLong("GROUP_REG_SEQ_ID")) : null);
					groupRegistrationVO.setGroupName(TTKCommon.checkNull(rs
							.getString("GROUP_NAME")));
					groupRegistrationVO.setGroupID(TTKCommon.checkNull(rs
							.getString("GROUP_ID")));
					groupRegistrationVO.setGroupGenTypeID(TTKCommon
							.checkNull(rs.getString("GROUP_GENERAL_TYPE_ID")));
					groupRegistrationVO.setOfficeSeqID(rs
							.getString("TPA_OFFICE_SEQ_ID") != null ? new Long(
							rs.getLong("TPA_OFFICE_SEQ_ID")) : null);
					groupRegistrationVO
							.setParentGroupSeqID(rs
									.getString("PARENT_GROUP_SEQ_ID") != null ? new Long(
									rs.getLong("PARENT_GROUP_SEQ_ID")) : null);
					groupRegistrationVO
							.setAccntManagerSeqID(rs
									.getString("ACC_MGR_CONTACT_SEQ_ID") != null ? new Long(
									rs.getLong("ACC_MGR_CONTACT_SEQ_ID"))
									: null);
					groupRegistrationVO.setAccntManagerName(TTKCommon
							.checkNull(rs.getString("CONTACT_NAME")));
					groupRegistrationVO.setNotiEmailId(TTKCommon.checkNull(rs
							.getString("NOTIFY_EMAIL_ID")));
					groupRegistrationVO.setCcEmailId(TTKCommon.checkNull(rs
							.getString("CC_EMAIL_ID")));
					groupRegistrationVO.setEmailId(TTKCommon.checkNull(rs
							.getString("EMAIL_ID")));
					groupRegistrationVO.setHrEmailId(TTKCommon.checkNull(rs
							.getString("HR_EMAIL_ID")));// added
					groupRegistrationVO.setPpnYN(TTKCommon.checkNull(rs
							.getString("PREF_PROV_NTW_FLAG")));// added for koc
																// 1346
					groupRegistrationVO.setNotiTypeId(TTKCommon.checkNull(rs
							.getString("NOTIFY_TYPE_ID")));
					addressVO
							.setAddrSeqId(rs.getString("ADDR_SEQ_ID") != null ? new Long(
									rs.getLong("ADDR_SEQ_ID")) : null);
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
					addressVO.setCountryCode(TTKCommon.checkNull(rs
							.getString("COUNTRY_ID")));
					addressVO.setPinCode(TTKCommon.checkNull(rs
							.getString("PIN_CODE")));

					addressVO.setIsdCode((Integer) TTKCommon.checkNull(rs
							.getInt("ISD_CODE")));
					addressVO.setStdCode((Integer) TTKCommon.checkNull(rs
							.getInt("STD_CODE")));
					addressVO.setPhoneNo1(TTKCommon.checkNull(rs
							.getString("OFFICE_PHONE1")));
					addressVO.setPhoneNo2(TTKCommon.checkNull(rs
							.getString("OFFICE_PHONE2")));
					groupRegistrationVO.setLocationCode(TTKCommon.checkNull(rs
							.getString("LOCATION_CODE")));

					groupRegistrationVO.setTradeLicenseNo(TTKCommon
							.checkNull(rs.getString("TRADELICENSE_NUMBER")));

					groupRegistrationVO.setAddress(addressVO);
				}// end of while(rs.next())
			}// End of if(rs!=null)
			return groupRegistrationVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Resultset in GroupRegistrationDAOImpl getGroup()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
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
								"Error while closing the Statement in GroupRegistrationDAOImpl getGroup()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
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
									"Error while closing the Connection in GroupRegistrationDAOImpl getGroup()",
									sqlExp);
							throw new TTKException(sqlExp, "groupregistration");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// End of getGroup(Long lngGroupRegSeqId)

	/**
	 * This method adds or updates the Group Registration details
	 * 
	 * @param groupRegistrationVO
	 *            GroupRegistrationVO object which contains the Group
	 *            Registration details to be added/updated
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
	 * @exception throws TTKException
	 */
	public int saveGroup(GroupRegistrationVO groupRegistrationVO)
			throws TTKException {

		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		AddressVO addressVO = groupRegistrationVO.getAddress();
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveGroupReg);
			cStmtObject
					.setLong(
							GROUP_REG_SEQ_ID,
							groupRegistrationVO.getGroupRegSeqID() != null ? groupRegistrationVO
									.getGroupRegSeqID() : 0);
		//	System.out.println("GROUP_REG_SEQ_ID..."+groupRegistrationVO.getGroupRegSeqID());
			if (groupRegistrationVO.getOfficeSeqID() != null) {
				cStmtObject.setLong(TPA_OFFICE_SEQ_ID,
						groupRegistrationVO.getOfficeSeqID());
			//	System.out.println("TPA_OFFICE_SEQ_ID..."+groupRegistrationVO.getOfficeSeqID());
			}// end of if (groupRegistrationVO.getOfficeSeqID()!=null)
			else {
				cStmtObject.setString(TPA_OFFICE_SEQ_ID, null);
			}// end of else
			cStmtObject.setString(GROUP_GENERAL_TYPE_ID,
					groupRegistrationVO.getGroupGenTypeID());
		//	System.out.println("GROUP_GENERAL_TYPE_ID    "+groupRegistrationVO.getGroupGenTypeID());
			cStmtObject.setString(GROUP_NAME,
					groupRegistrationVO.getGroupName());
			/*System.out.println("Group name..."
					+ groupRegistrationVO.getGroupName());*/
			cStmtObject.setString(GROUP_ID, groupRegistrationVO.getGroupID());
	/*	System.out.println("Group ID...."
					+ groupRegistrationVO.getGroupID());*/
			cStmtObject.setString(TRADELICENSE_NUMBER,
					groupRegistrationVO.getTradeLicenseNo());
//			System.out.println("TRADELICENSE_NUMBER    "+groupRegistrationVO.getTradeLicenseNo());

			if (groupRegistrationVO.getParentGroupSeqID() != null) {
				cStmtObject.setLong(PARENT_GROUP_SEQ_ID,
						groupRegistrationVO.getParentGroupSeqID());
			}// end of if (groupRegistrationVO.getParentGroupSeqID()!= null)
			
			else {
				cStmtObject.setLong(PARENT_GROUP_SEQ_ID, 0);
			}// end of else
	//		System.out.println("groupRegistrationVO.getParentGroupSeqID()  "+groupRegistrationVO.getParentGroupSeqID());
			cStmtObject.setLong(ADDR_SEQ_ID,
					addressVO.getAddrSeqId() != null ? addressVO.getAddrSeqId()
							: 0);
			cStmtObject.setString(ADDRESS_1, addressVO.getAddress1());
		//	System.out.println("ADDRESS_1  "+addressVO.getAddress1());
			cStmtObject.setString(ADDRESS_2, addressVO.getAddress2());
		//	System.out.println("ADDRESS_2"+addressVO.getAddress2());
			cStmtObject.setString(ADDRESS_3, addressVO.getAddress3());
		//	System.out.println("ADDRESS_3"+addressVO.getAddress3());
			cStmtObject.setString(STATE_TYPE_ID, addressVO.getStateCode());
		//	System.out.println("STATE_TYPE_ID"+addressVO.getStateCode());
			cStmtObject.setString(CITY_TYPE_ID, addressVO.getCityCode());
		//	System.out.println("CITY_TYPE_ID"+addressVO.getCityCode());
			cStmtObject.setLong(COUNTRY_ID,
					Long.parseLong(addressVO.getCountryCode()));
		//	System.out.println("COUNTRY_ID"+addressVO.getCountryCode());
			cStmtObject.setLong(PIN_CODE, Long.parseLong(addressVO.getPinCode()));
		//	System.out.println("PIN_CODE   "+addressVO.getPinCode());
			cStmtObject.setString(LOCATION_CODE,
					groupRegistrationVO.getLocationCode());
		//	System.out.println("LOCATION_CODE   "+groupRegistrationVO.getLocationCode());

			if (groupRegistrationVO.getAccntManagerSeqID() != null) {
				cStmtObject.setLong(ACC_MGR_CONTACT_SEQ_ID,
						groupRegistrationVO.getAccntManagerSeqID());
			}// end of if(groupRegistrationVO.getAccntManagerSeqID()!= null)
			else {
				cStmtObject.setLong(ACC_MGR_CONTACT_SEQ_ID, 0);
			}// end of else
	//		System.out.println("ACC_MGR_CONTACT_SEQ_ID  "+groupRegistrationVO.getAccntManagerSeqID());
			cStmtObject.setString(NOTIFY_EMAIL_ID,
					groupRegistrationVO.getNotiEmailId());
	//		System.out.println("NOTIFY_EMAIL_ID    "+groupRegistrationVO.getNotiEmailId());
			cStmtObject.setString(NOTIFY_TYPE_ID,
					groupRegistrationVO.getNotiTypeId());
	//		System.out.println("NOTIFY_TYPE_ID    "+groupRegistrationVO.getNotiTypeId());
			cStmtObject.setString(CC_EMAIL_ID,
					groupRegistrationVO.getCcEmailId());
	//		System.out.println("CC_EMAIL_ID   "+groupRegistrationVO.getCcEmailId());
			cStmtObject.setString(EMAIL_ID, groupRegistrationVO.getEmailId());
	//		System.out.println("EMAIL_ID   "+groupRegistrationVO.getEmailId());
			cStmtObject.setString(HREMAIL_ID,
					groupRegistrationVO.getHrEmailId());
	//		System.out.println("HREMAIL_ID   "+groupRegistrationVO.getHrEmailId());
			cStmtObject.setString(v_PREF_PROV_NTW_FLAG,
					groupRegistrationVO.getPpnYN());// added for koc 1346
	//		System.out.println("v_PREF_PROV_NTW_FLAG   "+groupRegistrationVO.getPpnYN());
			cStmtObject.setLong(UPDATEDBY, groupRegistrationVO.getUpdatedBy());
	//		System.out.println("UPDATEDBY   "+groupRegistrationVO.getUpdatedBy());
			cStmtObject.registerOutParameter(GROUP_REG_SEQ_ID, Types.BIGINT);
			cStmtObject.registerOutParameter(GROUP_ID, Types.VARCHAR);

			cStmtObject.setInt(ISD_CODE, addressVO.getIsdCode());
//			System.out.println("ISD_CODE  "+addressVO.getIsdCode());
			cStmtObject.setInt(STD_CODE, addressVO.getStdCode());
//			System.out.println("STD_CODE  "+ addressVO.getStdCode());
			cStmtObject.setLong(PHONE1, Long.parseLong(addressVO.getPhoneNo1()));
//			System.out.println("PHONE1  "+addressVO.getPhoneNo1());
		//	cStmtObject.setLong(PHONE2,Long.parseLong(addressVO.getPhoneNo2()));
				
			if(!"".equals(TTKCommon.checkNull(addressVO.getPhoneNo2()))){
				cStmtObject.setLong(PHONE2,Long.parseLong(addressVO.getPhoneNo2()));
	            }
			else{
				cStmtObject.setLong(28,0);
			}//end of else
	
//			System.out.println("PHONE2    "+addressVO.getPhoneNo2());
			cStmtObject.registerOutParameter(ROWS_PROCESSED, Types.INTEGER);
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(ROWS_PROCESSED);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Statement in GroupRegistrationDAOImpl addUpdateGroupRegistration()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
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
								"Error while closing the Connection in GroupRegistrationDAOImpl addUpdateGroupRegistration()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// End of addUpdateGroupRegistration(GroupVO groupVO)

	/**
	 * This method deletes the group registration from the Database
	 * 
	 * @param lngGroupRegSeqId
	 *            Long the user Groups Registration Sequence ID which has to be
	 *            deleted
	 * @return int, no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteGroup(Long lngGroupRegSeqId) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strDeleteGroupReg);
			cStmtObject.setLong(1, lngGroupRegSeqId);// GROUP REGISTRATION
														// SEQUENCE ID WHICH HAS
														// TO BE DELETED
			cStmtObject.registerOutParameter(2, Types.INTEGER);
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(2);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Statement in GroupRegistrationDAOImpl deleteGroupRegistration()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
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
								"Error while closing the Connection in GroupRegistrationDAOImpl deleteGroupRegistration()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// end of deleteGroupRegistration(long lGrpRegSeqId)

	/**
	 * This method returns the ArrayList, which contains the UserListVO's which
	 * are populated from the database
	 * 
	 * @param alSearchObjects
	 *            ArrayList object which contains the search criteria
	 * @param strIdentifier
	 *            for Identifying UserManagement/Finance Flow - In
	 *            UserManagement empty string and in Finance Flow - Finance
	 * @return ArrayList of UserListVO object's which contains the user details
	 * @exception throws TTKException
	 */
	public ArrayList selectAccountManager(ArrayList alSearchCriteria)
			throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		UserListVO userListVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strAccountManager);
			cStmtObject.setString(1, (String) alSearchCriteria.get(0));
			cStmtObject.setString(2, (String) alSearchCriteria.get(1));
			cStmtObject.setString(3, (String) alSearchCriteria.get(2));
			cStmtObject.setString(4, (String) alSearchCriteria.get(3));
			cStmtObject.setString(5, (String) alSearchCriteria.get(4));
			cStmtObject.setString(6, (String) alSearchCriteria.get(6));
			cStmtObject.setString(7, (String) alSearchCriteria.get(7));
			cStmtObject.setString(8, (String) alSearchCriteria.get(5));
			cStmtObject.registerOutParameter(9, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(9);
			if (rs != null) {
				while (rs.next()) {
					userListVO = new UserListVO();

					/*
					 * if (rs.getString("GROUP_USER_SEQ_ID")!=null){
					 * userListVO.setGroupUserSeqID(new
					 * Long(rs.getLong("GROUP_USER_SEQ_ID"))); }//end of if
					 * (rs.getString("GROUP_USER_SEQ_ID")!=null)
					 */
					userListVO.setUserID(TTKCommon.checkNull(rs
							.getString("USER_ID")));

					if (rs.getString("CONTACT_SEQ_ID") != null) {
						userListVO.setContactSeqId(new Long(rs
								.getLong("CONTACT_SEQ_ID")));
					}// end of if(rs.getString("CONTACT_SEQ_ID") != null)

					userListVO.setUserName(TTKCommon.checkNull(rs
							.getString("CONTACT_NAME")));
					userListVO.setEmployeeNbr(TTKCommon.checkNull(rs
							.getString("EMPLOYEE_NUMBER")));
					userListVO.setName(TTKCommon.checkNull(rs
							.getString("OFFICE_NAME")));
					userListVO.setRoleName(TTKCommon.checkNull(rs
							.getString("ROLE_NAME")));
					alResultList.add(userListVO);
				}// end of while(rs.next())
			}// end of if(rs != null)
			conn.commit();
			return (ArrayList) alResultList;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Resultset in GroupRegistrationDAOImpl selectAccountManager()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
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
								"Error while closing the Statement in GroupRegistrationDAOImpl selectAccountManager()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
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
									"Error while closing the Connection in GroupRegistrationDAOImpl selectAccountManager()",
									sqlExp);
							throw new TTKException(sqlExp, "groupregistration");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of selectAccountManager(ArrayList alSearchCriteria)

	/**
	 * This method returns the NotificationInfoVO Which contains the
	 * NotificationInfoVO details
	 * 
	 * @param lngGroupRegSeqID
	 *            for Group Registration Seq Id
	 * @return NotificationInfoVO which contains the NotificationInfo details
	 * @exception throws TTKException
	 */
	public NotificationInfoVO getNotificationList(Long lngGroupRegSeqID)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ArrayList<Object> alAssocNotifyList = null;
		ArrayList<Object> alUnAssocNotifyList = null;
		NotificationInfoVO notificationInfoVO = null;
		NotifyDetailVO notifyDetailVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strNotificationInfo);
			cStmtObject.setLong(1, lngGroupRegSeqID);
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(2);
			notificationInfoVO = new NotificationInfoVO();
			if (rs != null) {
				while (rs.next()) {
					if (alAssocNotifyList == null) {
						alAssocNotifyList = new ArrayList<Object>();
					}// end of if (alRoleList == null)
					if (alUnAssocNotifyList == null) {
						alUnAssocNotifyList = new ArrayList<Object>();
					}// end of if (alUnAssocRoleList == null)
					notifyDetailVO = new NotifyDetailVO();
					if (rs.getString("MSG_ID") != null) {
						notifyDetailVO.setMsgID(TTKCommon.checkNull(rs
								.getString("MSG_ID")));
					}// end of if(rsRole.getString("ROLE_SEQ_ID") != null)
					notifyDetailVO.setMsgName(TTKCommon.checkNull(rs
							.getString("MSG_NAME")));
					if (rs.getString("STATUS").equals("AS")) {
						alAssocNotifyList.add(notifyDetailVO);
						notificationInfoVO
								.setAssocNotifyList(alAssocNotifyList);
					}// end of if(rsRole.getString("STATUS").equals("AS"))
					else {
						alUnAssocNotifyList.add(notifyDetailVO);
						notificationInfoVO
								.setUnAssocNotifyList(alUnAssocNotifyList);
					}// end of else
				}// end of inner while
			} // end of if (rs!=null)
			return notificationInfoVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Resultset in WorkflowDAOImpl getNotificationList()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the second resultset now.
				{

					try {
						if (cStmtObject != null)
							cStmtObject.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in WorkflowDAOImpl getNotificationList()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
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
									"Error while closing the Connection in WorkflowDAOImpl getNotificationList()",
									sqlExp);
							throw new TTKException(sqlExp, "groupregistration");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of finally
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getNotificationList(Long lngGroupRegSeqID)

	// added for CR Mail-SMS Notification for cigna
	/**
	 * This method returns the NotificationInfoVO Which contains the
	 * NotificationInfoVO details
	 * 
	 * @param lngGroupRegSeqID
	 *            for Group Registration Seq Id
	 * @return NotificationInfoVO which contains the NotificationInfo details
	 * @exception throws TTKException
	 */
	public NotificationInfoVO getMailNotificationList(Long insuranceSeqID)
			throws TTKException {
		Connection conn = null;
		CallableStatement cStmtObject = null;
		ResultSet rs = null;
		ArrayList<Object> alAssocNotifyList = null;
		ArrayList<Object> alUnAssocNotifyList = null;
		NotificationInfoVO notificationInfoVO = null;
		NotifyDetailVO notifyDetailVO = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strMailSMSNotification);
			cStmtObject.setLong(1, insuranceSeqID.intValue());
			cStmtObject.registerOutParameter(2, Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet) cStmtObject.getObject(2);
			notificationInfoVO = new NotificationInfoVO();
			if (rs != null) {
				while (rs.next()) {
					if (alAssocNotifyList == null) {
						alAssocNotifyList = new ArrayList<Object>();
					}// end of if (alRoleList == null)
					if (alUnAssocNotifyList == null) {
						alUnAssocNotifyList = new ArrayList<Object>();
					}// end of if (alUnAssocRoleList == null)
					notifyDetailVO = new NotifyDetailVO();
					if (rs.getString("MSG_ID") != null) {
						notifyDetailVO.setMsgID(TTKCommon.checkNull(rs
								.getString("MSG_ID")));
					}// end of if(rsRole.getString("ROLE_SEQ_ID") != null)
					notifyDetailVO.setMsgName(TTKCommon.checkNull(rs
							.getString("MSG_NAME")));
					if (rs.getString("STATUS").equals("AS")) {
						alAssocNotifyList.add(notifyDetailVO);
						notificationInfoVO
								.setAssocNotifyList(alAssocNotifyList);
					}// end of if(rsRole.getString("STATUS").equals("AS"))
					else {
						alUnAssocNotifyList.add(notifyDetailVO);
						notificationInfoVO
								.setUnAssocNotifyList(alUnAssocNotifyList);
					}// end of else
				}// end of inner while
			} // end of if (rs!=null)
			conn.commit();
			return notificationInfoVO;
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Resultset in WorkflowDAOImpl getMailNotificationList()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
				}// end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches
						// here. Try closing the second resultset now.
				{

					try {
						if (cStmtObject != null)
							cStmtObject.close();
					}// end of try
					catch (SQLException sqlExp) {
						log.error(
								"Error while closing the Statement in WorkflowDAOImpl getMailNotificationList()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
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
									"Error while closing the Connection in WorkflowDAOImpl getMailNotificationList()",
									sqlExp);
							throw new TTKException(sqlExp, "groupregistration");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of finally
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// end of getMailNotificationList(Long insuranceSeqID)

	/**
	 * This method adds or updates the Notification Info details
	 * 
	 * @param notifyDetailVO
	 *            NotifyDetailVO object which contains the Group Registration
	 *            details to be added/updated
	 * @return int value, greater than zero indicates sucessfull execution of
	 *         the task
	 * @exception throws TTKException
	 */
	public int saveNotificationInfo(NotificationInfoVO notificationInfoVO)
			throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		StringBuffer sbfNotificationSeqId = new StringBuffer();
		try {
			ArrayList alAssNotificationList = new ArrayList();
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveNotificationInfo);
			cStmtObject.setLong(1, notificationInfoVO.getGroupRegSeqID()
					.longValue());
			/*System.out.println("notificationInfoVO.getGroupRegSeqID()..."
					+ notificationInfoVO.getGroupRegSeqID());*/
			alAssNotificationList = notificationInfoVO.getAssocNotifyList();
			if (alAssNotificationList != null
					&& (alAssNotificationList.size() >= 0)) {
				sbfNotificationSeqId.append("|");
				for (int i = 0; i < alAssNotificationList.size(); i++) {
					sbfNotificationSeqId
							.append(String
									.valueOf(((NotifyDetailVO) alAssNotificationList
											.get(i)).getMsgID())).append("|");
				}// end of for(int i=0; i<alAssNotificationList.size(); i++)
			}// end of if(alAssNotificationList!=null &&
				// (alAssNotificationList.size()>=0))
			cStmtObject.setString(2, sbfNotificationSeqId.toString());
			/*System.out.println("sbfNotificationSeqId.toString()    "
					+ sbfNotificationSeqId.toString());*/
			cStmtObject.setLong(3, notificationInfoVO.getUpdatedBy()
					.longValue());
		/*System.out.println("notificationInfoVO.getUpdatedBy()   "
					+ notificationInfoVO.getUpdatedBy());*/
			cStmtObject.registerOutParameter(4, Types.INTEGER);
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(4);
		}// end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Statement in GroupRegistrationDAOImpl saveNotificationInfo()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
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
								"Error while closing the Connection in GroupRegistrationDAOImpl saveNotificationInfo()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				cStmtObject = null;
				conn = null;
			}// end of finally
		}// end of finally
		return iResult;
	}// End of saveNotificationInfo(GroupVO groupVO)

	/**
	 * This method adds or updates the MailNotification Info details
	 * 
	 * @param notifyDetailVO
	 *            NotifyDetailVO object which contains the Group Registration
	 *            details to be added/updated
	 * @return int value, greater than zero indicates successfull execution of
	 *         the task
	 * @exception throws TTKException
	 */

	public int saveMailNotificationInfo(NotificationInfoVO notificationInfoVO)
			throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		StringBuffer sbfNotificationSeqId = new StringBuffer();
		try {
			ArrayList alAssNotificationList = new ArrayList();
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement) conn
					.prepareCall(strSaveMailNotificationInfo);
			cStmtObject.setLong(1, notificationInfoVO.getInsuranceSeqID());
			alAssNotificationList = notificationInfoVO.getAssocNotifyList();
			if (alAssNotificationList != null
					&& (alAssNotificationList.size() >= 0)) {
				sbfNotificationSeqId.append("|");
				for (int i = 0; i < alAssNotificationList.size(); i++) {
					sbfNotificationSeqId
							.append(String
									.valueOf(((NotifyDetailVO) alAssNotificationList
											.get(i)).getMsgID())).append("|");
				}// end of for(int i=0; i<alAssNotificationList.size(); i++)
			}// end of if(alAssNotificationList!=null &&
				// (alAssNotificationList.size()>=0))
			cStmtObject.setString(2, sbfNotificationSeqId.toString());
			cStmtObject.setLong(3, notificationInfoVO.getUpdatedBy());
			cStmtObject.registerOutParameter(4, Types.INTEGER);
			// System.out.println("first parameter  "+notificationInfoVO.getInsuranceSeqID()+"second"+sbfNotificationSeqId+" 3rd "+
			// notificationInfoVO.getUpdatedBy());
			cStmtObject.execute();
			iResult = cStmtObject.getInt(4);
			conn.commit();

		} catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Statement in GroupRegistrationDAOImpl saveMailNotificationInfo()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
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
								"Error while closing the Connection in GroupRegistrationDAOImpl saveMailNotificationInfo()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
					}// end of catch (SQLException sqlExp)
				}// end of finally Connection Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
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

	/**
	 * This method adds or updates the MailNotification Info details
	 * 
	 * @param notifyDetailVO
	 *            NotifyDetailVO object which contains the Group Registration
	 *            details to be added/updated
	 * @return int value, greater than zero indicates successfull execution of
	 *         the task
	 * @exception throws TTKException
	 */

	public String getMaxAbbrevationCode() throws TTKException {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		String insAbbr = null;
		try {
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strGetInsAbbreviation);
			rs = pStmt.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					insAbbr = TTKCommon.checkNull(rs
							.getString("ABBREVATION_CODE"));
				}// End of while (rs.next())
			}// End of if (rs!=null)
			return insAbbr;
		} catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "groupregistration");
		}// end of catch (SQLException sqlExp)
		catch (Exception exp) {
			throw new TTKException(exp, "groupregistration");
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
							"Error while closing the Resultset in GroupRegistrationDAOImpl getMaxAbbrevationCode()",
							sqlExp);
					throw new TTKException(sqlExp, "groupregistration");
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
								"Error while closing the Statement in GroupRegistrationDAOImpl getMaxAbbrevationCode()",
								sqlExp);
						throw new TTKException(sqlExp, "groupregistration");
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
									"Error while closing the Connection in GroupRegistrationDAOImpl getMaxAbbrevationCode()",
									sqlExp);
							throw new TTKException(sqlExp, "groupregistration");
						}// end of catch (SQLException sqlExp)
					}// end of finally Connection Close
				}// end of finally Statement Close
			}// end of try
			catch (TTKException exp) {
				throw new TTKException(exp, "groupregistration");
			}// end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the
					// objects
			{
				rs = null;
				pStmt = null;
				conn = null;
			}// end of finally
		}// end of finally
	}// End of getMaxAbbrevationCode()
	/*
	 * public static void main(String args[]) throws TTKException {
	 * GroupRegistrationDAOImpl groupRegistrationDAOImpl = new
	 * GroupRegistrationDAOImpl(); NotificationInfoVO notificationInfoVO = new
	 * NotificationInfoVO();
	 * 
	 * 
	 * String[] strNotifyList = new String[2]; strNotifyList[0] =
	 * "CLAIM_SHORTFALL"; strNotifyList[1] = "E_CARDS";
	 * notificationInfoVO.setGroupRegSeqID(new Long(263)); ArrayList al = new
	 * ArrayList(); if(strNotifyList != null){ for(int
	 * i=0;i<strNotifyList.length;i++){ NotifyDetailVO notifyDetailVO = new
	 * NotifyDetailVO(); notifyDetailVO.setMsgID(strNotifyList[i]);
	 * al.add(notifyDetailVO); } } notificationInfoVO.setAssocNotifyList(al);
	 * notificationInfoVO.setUpdatedBy(new Long(1)); int i =
	 * groupRegistrationDAOImpl.saveNotificationInfo(notificationInfoVO);
	 * 
	 * //groupRegistrationDAOImpl.getNotificationList(new Long(263));
	 * 
	 * 
	 * // for(int i=0; i<=obj.length; i++) // { // // }//end of for(int i=0;
	 * i<=obj.length; i++) }//end of main
	 */
}// End of GroupRegistrationDAOImpl
