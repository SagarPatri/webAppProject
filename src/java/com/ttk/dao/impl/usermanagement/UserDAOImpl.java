/**
 * @ (#) UserDAOImpl.java Dec 16, 2005
 * Project 	     : TTK HealthCare Services
 * File          : UserDAOImpl.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Dec 16, 2005
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.usermanagement;

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
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.ttk.common.TTKCommon;
import com.ttk.common.Test;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.finance.AuthorisedVO;
import com.ttk.dto.onlineforms.OnlineChangePasswordVO;
import com.ttk.dto.usermanagement.AdditionalInfoVO;
import com.ttk.dto.usermanagement.ContactVO;
import com.ttk.dto.usermanagement.PasswordVO;
//Changes Added for Password Policy CR KOC 1235
import com.ttk.dto.usermanagement.PasswordValVO;
//End changes for Password Policy CR KOC 1235
import com.ttk.dto.usermanagement.PersonalInfoVO;
import com.ttk.dto.usermanagement.UserAccessVO;
import com.ttk.dto.usermanagement.UserListVO;

public class UserDAOImpl implements BaseDAO, Serializable {
	 private static Logger log = Logger.getLogger(UserDAOImpl.class );
	private static final String strUserList = "{CALL CONTACT_PKG_SELECT_CONTACT_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	private static final String strFinanceUserList = "{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_USER_INFO_LIST(?,?,?,?,?,?,?,?)}";
	private static final String strActivateInactivateUserInfo = "{CALL CONTACT_PKG_INACTIVATE_USERS(?,?,?)}";
	private static final String strRemoveUsers = "{CALL CONTACT_PKG_DELETE_GRP_ASSOC_USER(?,?)}";
	private static final String strUpdateGroupRegistration = "{CALL CONTACT_PKG_SAVE_GRP_ASSOC_USER(?,?,?,?)}";
	private static final String strGroupRegistrationUserList = "SELECT * FROM ( SELECT A.*,DENSE_RANK() OVER(ORDER BY A.#) Q FROM (WITH assoc_users AS (SELECT  group_user_seq_id,group_branch_seq_id,contact_seq_id FROM  app.tpa_group_user_assoc WHERE group_branch_seq_id  = ?), ttk_user AS (SELECT contact_seq_id,tpa_office_seq_id,employee_number,contact_name FROM app.tpa_user_contacts WHERE user_general_type_id  in ('TTK','BRO')) SELECT A.group_user_seq_id, F.user_id,B.contact_seq_id,B.contact_name,B.employee_number,B.tpa_office_seq_id,C.office_name,E.role_name FROM  assoc_users A RIGHT OUTER JOIN ttk_user B ON (A.contact_seq_id = B.contact_seq_id ) LEFT OUTER JOIN app.tpa_office_info C ON (B.tpa_office_seq_id = C.tpa_office_seq_id) LEFT OUTER JOIN app.tpa_user_roles D ON (B.contact_seq_id = D.contact_seq_id) LEFT OUTER JOIN app.tpa_roles_code E ON (D.role_seq_id = E.role_seq_id) LEFT OUTER JOIN app.tpa_login_info F ON (B.contact_seq_id = F.contact_seq_id) WHERE (case when A.group_user_seq_id>0 then 'Y' else 'N' end ) ='$' ";//WHERE GROUP_BRANCH_SEQ_ID = ? AND NVL2(A.group_user_seq_id,'Y','N') ='$' (Group Branch sequence id and Associate type is required as a join mandatorily)
	private static final String strGetUserContact = "{CALL CONTACT_PKG_SELECT_CONTACT(?,?)}";
	//private static final String strSaveUserContact = "{CALL CONTACT_PKG.SAVE_CONTACTS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	  private static final String strSaveUserContact = "{CALL CONTACT_PKG_SAVE_CONTACTS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

	private static final String strSaveProfile = "{CALL CONTACT_PKG_SAVE_MYPROFILE(?,?,?,?,?,?,?,?,?)}";
	private static final String strSaveDenMail = "{CALL CONTACT_PKG_SAVE_DEN_MAIL(?,?,?,?,?)}";//denial process
	private static final String strSavePassword = "{CALL CONTACT_PKG_CHANGE_PASSWORD(?,?,?,?,?)}";
	private static final String strSignatoryList = "{CALL fin_app.BANK_ACCOUNTS_PKG_SELECT_AUTH_SIGN_LIST(?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strDeleteSignatory = "{CALL fin_app.BANK_ACCOUNTS_PKG_AUTH_SIGN_DELETE(?,?,?)}";
    private static final String strResetPassword="{CALL CONTACT_PKG_RESET_PASSWORD(?,?,?)}";

	//Changes Added for Password Policy CR KOC 1235
    private static final String strSavePasswordConfigInfo = "{CALL CONTACT_PKG_SAVE_PASSWORD_CONFIG(?,?,?,?,?,?)}";
    private static final String strGetPasswordConfigInfo = "SELECT PASSWORD_VALID_DAYS,ALERT_DAYS,WRONG_ATTEMPTS,ACN_EXPIRE_IN_DAYS FROM APP.PASSWORD_CONFIG WHERE CONFIG_SEQ_ID=?";//Modified as per Onlinepassword Policy
    private static final String strResetPasswordAll = "{CALL CONTACT_PKG_RESET_PWD_ALL(?,?)}";
    //End changes for Password Policy CR KOC 1235
//intx - Kishor
    private static final String strGetStdIsd = "SELECT I.STD_CODE,I.ISD_CODE FROM APP.TPA_HOSP_INFO I WHERE I.HOSP_SEQ_ID=?";
    private static final String strGetStdIsdForIns = "SELECT a.std_code,a.isd_code from app.tpa_ins_info i join app.tpa_address a on (i.ins_seq_id=a.ins_seq_id) where a.ins_seq_id=?";
    private static final String strGetStdIsdForBak = "SELECT STD_CODE,ISD_CODE from FIN_app.tpa_bank_master where bank_seq_id=?";
    private static final String strgetStdIsdForCorp="SELECT STD_CODE, ISD_CODE FROM (SELECT B.ISD_CODE,B.STD_CODE FROM APP.TPA_GROUP_REGISTRATION A JOIN APP.TPA_ADDRESS B ON (A.GROUP_REG_SEQ_ID = B.GROUP_REG_SEQ_ID) LEFT OUTER JOIN APP.TPA_USER_CONTACTS C ON (A.ACC_MGR_CONTACT_SEQ_ID = C.CONTACT_SEQ_ID)  WHERE A.GROUP_REG_SEQ_ID = ?)AB";
    private static final String strgetStdIsdForBroker="SELECT STD_CODE,ISD_CODE FROM APP.TPA_BRO_INFO,APP.TPA_BRO_ADDRESS WHERE APP.TPA_BRO_INFO.INS_SEQ_ID = APP.TPA_BRO_ADDRESS.INS_SEQ_ID AND APP.TPA_BRO_INFO.INS_SEQ_ID = ?";
    
    private static final String strGetProfessionalContact="{CALL intx.HOSPITAL_EMPANEL_PKG_SELECT_HOSP_PROFESSIONAL(?,?)}";
    private static final String strSaveProfessionalsContact="{CALL hospital_empanel_pkg_save_hosp_professionals(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
  //  private static final String strSaveProfessionalsContact="{CALL HOSPITAL_EMPANEL_PKG.save_hosp_professionals(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    private static final String strGetStdIsd1 = "SELECT I.STD_CODE,I.ISD_CODE FROM APP.TPA_PARTNER_INFO I WHERE I.PTNR_SEQ_ID=?";

    private static final int USER_GENERAL_TYPE_ID = 1;
	private static final int USER_ID =2;
	private static final int CONTACT_NAME = 3;
	private static final int ROLE_SEQ_ID = 4;
	private static final int HOSP_NAME = 5;
	private static final int EMPANEL_NUMBER = 6;
	private static final int CITY_TYPE_ID = 7;
	private static final int ACTIVE_YN = 8;
	private static final int INS_SEQ_ID = 9;
	private static final int INS_COMP_CODE_NUMBER = 10;
	private static final int TPA_OFFICE_SEQ_ID = 11;
	private static final int GROUP_NAME = 12;
	private static final int GROUP_ID = 13;
	private static final int SORT_COLUMN_NAME = 14;
	private static final int START_ROW = 15;
	private static final int END_ROW = 16;
	private static final int SORT_ORDER = 17;
	private static final int CURSOR = 18;

	/**
	 * This method returns the ArrayList, which contains the UserListVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @param strIdentifier for Identifying UserManagement/Finance Flow - In UserManagement empty string and in Finance Flow - Finance
	 * @return ArrayList of UserListVO object's which contains the user details
	 * @exception throws TTKException
	 */
	public ArrayList getUserList(ArrayList alSearchObjects,String strIdentifier) throws TTKException
	{
		Collection<Object> alResultList = new ArrayList<Object>();
        String strUserGnrlTypeId = "";
        Connection conn = null;
        CallableStatement cStmtObject=null;
        ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();

			if(strIdentifier.equalsIgnoreCase("Finance")){
				cStmtObject = conn.prepareCall(strFinanceUserList);

				cStmtObject.setString(1,(String)alSearchObjects.get(0));
				cStmtObject.setString(2,(String)alSearchObjects.get(1));
				cStmtObject.setString(3,(String)alSearchObjects.get(3));
				cStmtObject.setString(4,(String)alSearchObjects.get(4));
				cStmtObject.setLong(5,Long.parseLong((String) alSearchObjects.get(5)));
				cStmtObject.setLong(6,Long.parseLong((String) alSearchObjects.get(6)));
				cStmtObject.setLong(7,(Long)alSearchObjects.get(2));
				cStmtObject.registerOutParameter(1, Types.VARCHAR);
				cStmtObject.registerOutParameter(2, Types.VARCHAR);
				cStmtObject.registerOutParameter(8,Types.OTHER);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(8);
				if(rs != null){
					UserListVO userListVO = null;
					while(rs.next()){
						userListVO = new UserListVO();

						if(rs.getString("CONTACT_SEQ_ID") != null){
							userListVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
						}//end of if(rs.getString("CONTACT_SEQ_ID") != null)

						userListVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
						userListVO.setUserName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
						userListVO.setRoleName(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
						userListVO.setName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
						alResultList.add(userListVO);
					}//end of while(rs.next())
				}//end of if(rs != null)
				log.info("intx.BANK_ACCOUNTS_PKG_SELECT_USER_INFO_LIST");
			}//end of if(strIdentifier.equalsIgnoreCase("Finance"))
			else{

				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUserList);
				strUserGnrlTypeId = alSearchObjects.get(0).toString();
				cStmtObject.setString(USER_GENERAL_TYPE_ID,(String)alSearchObjects.get(0));//USER_GENERAL_TYPE_ID
				cStmtObject.setString(USER_ID, (String)alSearchObjects.get(1));//USER_ID
				cStmtObject.setString(CONTACT_NAME, (String)alSearchObjects.get(2));//CONTACT_NAME
				cStmtObject.setString(ROLE_SEQ_ID, (String)alSearchObjects.get(3));//ROLE_SEQ_ID
				cStmtObject.setString(HOSP_NAME, (String)alSearchObjects.get(4));//HOSP_NAME
				cStmtObject.setString(EMPANEL_NUMBER, (String)alSearchObjects.get(5));//EMPANEL_NUMBER
				cStmtObject.setString(CITY_TYPE_ID,(String)alSearchObjects.get(6));//CITY_TYPE_ID
				cStmtObject.setString(ACTIVE_YN,(String)alSearchObjects.get(7));//ACTIVE_YN
				cStmtObject.setString(INS_SEQ_ID,(String)alSearchObjects.get(8));//INS_COMP_NAME
				cStmtObject.setString(INS_COMP_CODE_NUMBER,(String)alSearchObjects.get(9));//INS_COMP_CODE_NUMBER
				cStmtObject.setString(TPA_OFFICE_SEQ_ID,(String)alSearchObjects.get(10));//TPA_OFFICE_SEQ_ID
				cStmtObject.setString(GROUP_NAME,(String)alSearchObjects.get(11));//GROUP_NAME
				cStmtObject.setString(GROUP_ID,(String)alSearchObjects.get(12));//GROUP_ID
				cStmtObject.setString(SORT_COLUMN_NAME,(String)alSearchObjects.get(13));
				cStmtObject.setString(START_ROW,(String)alSearchObjects.get(15));
				cStmtObject.setString(END_ROW ,(String)alSearchObjects.get(16));
				cStmtObject.setString(SORT_ORDER,(String)alSearchObjects.get(14));
				cStmtObject.registerOutParameter(CURSOR,Types.OTHER);
				cStmtObject.execute();
				rs = (java.sql.ResultSet)cStmtObject.getObject(CURSOR);
				if(rs != null){
					UserListVO userListVO = null;
					while (rs.next()) {
	                    userListVO = new UserListVO();

	                    userListVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));

	                    if(rs.getString("CONTACT_SEQ_ID") != null){
	                        userListVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
	                    }//end of if(rs.getString("CONTACT_SEQ_ID") != null)
						userListVO.setUserName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
						userListVO.setRoleName(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
						userListVO.setActive_Yn(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
						userListVO.setAccessYn(TTKCommon.checkNull(rs.getString("PROVIDE_ACCESS_USER_YN")));

						if(strUserGnrlTypeId.equalsIgnoreCase("TTK") || strUserGnrlTypeId.equalsIgnoreCase("CAL")||strUserGnrlTypeId.equalsIgnoreCase("DMC") ){
	                        userListVO.setName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
	                    }//end of if(strUserGnrlTypeId.equalsIgnoreCase("TTK") || strUserGnrlTypeId.equalsIgnoreCase("CAL"))

						if(strUserGnrlTypeId.equalsIgnoreCase("HOS")){
	                        userListVO.setName(TTKCommon.checkNull(rs.getString("HOSP_NAME")));
	                        userListVO.setCity(TTKCommon.checkNull(rs.getString("CITY")));
	                        userListVO.setEmpanelmentNo(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
						}//end of if(strUserGnrlTypeId.equalsIgnoreCase("HOS"))

	                    if(strUserGnrlTypeId.equalsIgnoreCase("INS")||strUserGnrlTypeId.equalsIgnoreCase("BRO")){
	                        userListVO.setName(TTKCommon.checkNull(rs.getString("INS_COMP_NAME")));
	                        userListVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_COMP_CODE_NUMBER")));
						}//end of if(strUserGnrlTypeId.equalsIgnoreCase("INS"))

	                    if(strUserGnrlTypeId.equalsIgnoreCase("COR")){
	                        userListVO.setName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
	                        userListVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
						}//end of if(strUserGnrlTypeId.equalsIgnoreCase("COR"))
						alResultList.add(userListVO);
					}//end of while(rs.next())
				}//end of if(rs != null)
				log.info("CONTACT_PKG_SELECT_CONTACT_LIST....");
			}//end of else
         conn.commit();
			return (ArrayList)alResultList;
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
					log.error("Error while closing the Resultset in UserDAOImpl getUserList()",sqlExp);
					throw new TTKException(sqlExp, "user");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null)	cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in UserDAOImpl getUserList()",sqlExp);
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
							log.error("Error while closing the Connection in UserDAOImpl getUserList()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}// End of getUserList(ArrayList alSearchObjects)

	/**
	 * This method activate or inactivate the user records to the database.
	 * @param strContactSeqID String object which contains the conatct sequence id's to be activated or inactivated
	 * @param strIdentifier String object which contains the Active or Inactive flag to be activated or inactivated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int setUserStatus(String strContactSeqID,String strIdentifier) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strActivateInactivateUserInfo);
			cStmtObject.setString(1, strContactSeqID);//string of contact sequence id's which are separated with | as separator (Note: String should also end with | at the end)
			cStmtObject.setString(2,strIdentifier);//ActiveYN
			cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
			cStmtObject.execute();
			iResult = cStmtObject.getInt(3);
			conn.commit();
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
        			log.error("Error while closing the Statement in UserDAOImpl setUserStatus()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl setUserStatus()",sqlExp);
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
	}//end of setUserStatus(String strContactSeqID,String strIdentifier)

	/**
	 * This method returns the ArrayList, which contains the GroupVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of GroupVO object's which contains the user group Information
	 * @exception throws TTKException
	 */
	public ArrayList groupRegistrationUserList(ArrayList alSearchObjects) throws TTKException
	{
		String strStaticQuery = strGroupRegistrationUserList;
		StringBuffer sbfDynamicQuery = new StringBuffer();
		UserListVO userListVO = null;
		String strGroupBranchSeqId = "";
		String strAssocType = "";
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Collection<Object> resultList = new ArrayList<Object>();
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
            strGroupBranchSeqId = ((SearchCriteria)alSearchObjects.get(0)).getValue();
			strAssocType = ((SearchCriteria)alSearchObjects.get(1)).getValue();

			strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"?", strGroupBranchSeqId);
			strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"$", strAssocType);
			for(int i=2; i < alSearchObjects.size()-4; i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals("")){
					if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals")){
                        sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
                    }//end of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
                    else {
                        sbfDynamicQuery.append(" AND "+ ((SearchCriteria)alSearchObjects.get(i)).getName()+" LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
                    }//end of else
				}//end of if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for()
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		//build the Order By Clause
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", TTKCommon.checkNull(((String)alSearchObjects.get(6)))+" "+TTKCommon.checkNull(((String)alSearchObjects.get(7))));
		//build the row numbers to be fetched
		sbfDynamicQuery.append( " )A ) al WHERE   Q >= "+TTKCommon.checkNull(((String)alSearchObjects.get(8)))+ " AND Q <= "+TTKCommon.checkNull(((String)alSearchObjects.get(9))));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		try{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strStaticQuery);
	
			rs = pStmt.executeQuery();
			if (rs!=null) {
				while (rs.next())
				{
					userListVO = new UserListVO();

                    if (rs.getString("GROUP_USER_SEQ_ID")!=null){
						userListVO.setGroupUserSeqID(new Long(rs.getLong("GROUP_USER_SEQ_ID")));
                    }//end of if (rs.getString("GROUP_USER_SEQ_ID")!=null)
					userListVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));

                    if(rs.getString("CONTACT_SEQ_ID") != null){
						userListVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
                    }//end of if(rs.getString("CONTACT_SEQ_ID") != null)

                    userListVO.setUserName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					userListVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NUMBER")));
					userListVO.setName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					userListVO.setRoleName(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
					resultList.add(userListVO);
				}// End of while (rs.next())
			}// End of if (rs!=null)
			return (ArrayList) resultList;
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
					log.error("Error while closing the Resultset in UserDAOImpl groupRegistrationUserList()",sqlExp);
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
						log.error("Error while closing the Statement in UserDAOImpl groupRegistrationUserList()",sqlExp);
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
							log.error("Error while closing the Connection in UserDAOImpl groupRegistrationUserList()",sqlExp);
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
	}//End of groupRegistrationUserList(ArrayList alSearchObjects)

	/**
	 * This method associate the User's to the group
	 * @param strContactSeqID, String which contains the contact seq id's to be associated
	 * @param lngGroupBranchSeqID long value which contains Group Branch Sequence ID
	 * @param lngUserSeqID long value which contains User Sequence ID
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @throws TTKException
	 */
	public int updateGroupRegistration(String strContactSeqID,long lngGroupBranchSeqID,long lngUserSeqID) throws TTKException
	{
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUpdateGroupRegistration);
			cStmtObject.setLong(1,lngGroupBranchSeqID);//GROUP_BRANCH_SEQ_ID
			cStmtObject.setString(2,strContactSeqID);//--concatenated string of contact_seq_id delimeted by '|'.
			cStmtObject.setLong(3,lngUserSeqID); // Added by
			cStmtObject.registerOutParameter(4,Types.SMALLINT);		
			cStmtObject.execute();
			iResult = cStmtObject.getShort(4);
			conn.commit();
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
        			log.error("Error while closing the Statement in UserDAOImpl updateGroupRegistration()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl updateGroupRegistration()",sqlExp);
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
	}// end of updateGroupRegistration(String strContactSeqID,long lngGroupSeqID,long lngUserSeqID)

	/**
	 * This method deletes the users from the  associated users list
	 * @param strGroupUserSeqID String which contains the Group User seq id's which has to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int removeUsers(String strGroupUserSeqID) throws TTKException
	{
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strRemoveUsers);
			cStmtObject.setString(1,strGroupUserSeqID); //Concatenated String of GROUP_USER_SEQ_ID'S
			cStmtObject.registerOutParameter(2,Types.SMALLINT);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getShort(2);
			conn.commit();
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
        			log.error("Error while closing the Statement in UserDAOImpl removeUsers()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl removeUsers()",sqlExp);
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
		return  iResult;
	}// End of removeUsers(String strGroupUserSeqID)

	/**
	 * This method returns the ContactVO which contains the details of UserContact
	 * @param lngContactSeqID the contact sequence if which the Contact information is to be fetched
	 * @return ContactVO which contains the information of UserContact
	 * @exception throws TTKException
	 */
	public ContactVO getContact(Long lngContactSeqID) throws TTKException
	{
		ContactVO contactVO = new ContactVO();
		UserAccessVO userAccessVO = new UserAccessVO();
		AdditionalInfoVO additionalInfoVO = new AdditionalInfoVO();
		PersonalInfoVO personalInfoVO = new PersonalInfoVO();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try {
	    	conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strGetUserContact);
			cStmtObject.setLong(1,lngContactSeqID);
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(2,Types.OTHER);// CONTACT DETAILS CURSOR
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);	
			if(rs != null){
				while (rs.next()) {				
					userAccessVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
					userAccessVO.setUserType(TTKCommon.checkNull(rs.getString("USER_GENERAL_TYPE_ID")));
					contactVO.setContactSeqID((rs.getString("CONTACT_SEQ_ID")!=null) ? new Long (rs.getLong("CONTACT_SEQ_ID")):null);
			    	contactVO.setHospitalSeqID((rs.getString("HOSP_SEQ_ID")!= null) ? new Long(rs.getLong("HOSP_SEQ_ID")): null);
					contactVO.setPartnerSeqID((rs.getString("PTNR_SEQ_ID")!= null) ? new Long(rs.getLong("PTNR_SEQ_ID")): null);
					contactVO.setInsuranceSeqID((rs.getString("INS_SEQ_ID")!=null) ? new Long(rs.getLong("INS_SEQ_ID")): null);
					additionalInfoVO.setOfficeSeqID((rs.getString("TPA_OFFICE_SEQ_ID")!=null) ? new Integer(rs.getInt("TPA_OFFICE_SEQ_ID")): null);
					contactVO.setGroupRegSeqID((rs.getString("GROUP_REG_SEQ_ID")!=null) ? new Long(rs.getLong("GROUP_REG_SEQ_ID")): null);
					contactVO.setBankSeqID((rs.getString("BANK_SEQ_ID")!=null) ? new Long (rs.getLong("BANK_SEQ_ID")):null);
					personalInfoVO.setPrefix(TTKCommon.checkNull(rs.getString("PREFIX_GENERAL_TYPE_ID")));
					personalInfoVO.setName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					personalInfoVO.setDesignationTypeID(TTKCommon.checkNull(rs.getString("DESIGNATION_TYPE_ID")));
					personalInfoVO.setDesignationDesc(TTKCommon.checkNull(rs.getString("DESIGNATION")));
					personalInfoVO.setDesignation(TTKCommon.checkNull(rs.getString("DESIGNATIONS")));			
					personalInfoVO.setPrimaryEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
					personalInfoVO.setSecondaryEmailID(TTKCommon.checkNull(rs.getString("SECONDARY_EMAIL_ID")));
					personalInfoVO.setActiveYN(TTKCommon.checkNull(rs.getString("USER_ACTIVE_YN")));
					userAccessVO.setAccessYN(TTKCommon.checkNull(rs.getString("PROVIDE_ACCESS_USER_YN")));
					additionalInfoVO.setContactTypeID(TTKCommon.checkNull(rs.getString("CONTACT_TYPE_ID")));
					additionalInfoVO.setSpecTypeID(TTKCommon.checkNull(rs.getString("SPEC_TYPE_ID")));
					additionalInfoVO.setRegistrationNbr(TTKCommon.checkNull(rs.getString("DR_REGIST_NMBR")));
					additionalInfoVO.setQualification(TTKCommon.checkNull(rs.getString("DR_QUALIF")));
					additionalInfoVO.setResidentYN(TTKCommon.checkNull(rs.getString("RESIDENT_DR_YN")));
					additionalInfoVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NUMBER")));
					personalInfoVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					personalInfoVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
					personalInfoVO.setResidencePhoneNbr(TTKCommon.checkNull(rs.getString("RES_PHONE_NO")));
					personalInfoVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
					personalInfoVO.setFaxNbr(TTKCommon.checkNull(rs.getString("FAX_NO")));
					additionalInfoVO.setDepartmentID(TTKCommon.checkNull(rs.getString("DEPT_GENERAL_TYPE_ID")));
					userAccessVO.setRoleID(rs.getString("ROLE_SEQ_ID")!= null ? new Long(rs.getLong("ROLE_SEQ_ID")):null);
					userAccessVO.setEmpanelNbr(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					userAccessVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_OFFICE_CODE")));
			    	userAccessVO.setOfficeName(rs.getString("INS_COMP_NAME")!=null ? TTKCommon.checkNull(rs.getString("INS_COMP_NAME")): TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					userAccessVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					userAccessVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					userAccessVO.setContactType(TTKCommon.checkNull(rs.getString("PTNR_USER_TYPE")));
					//Changes Added for Password Policy CR KOC 1235
					personalInfoVO.setAccn_locked_YN(TTKCommon.checkNull(rs.getString("ACCN_LOCKED_YN")));
					//End changes for Password Policy CR KOC 1235

					if(rs.getString("CONTACT_PA_LIMIT") != null){
						additionalInfoVO.setContactPALimit(new BigDecimal(rs.getString("CONTACT_PA_LIMIT")));
					}//end of if(rs.getString("CONTACT_PA_LIMIT") != null)

					if(rs.getString("CONTACT_CLAIM_LIMIT") != null){
						additionalInfoVO.setContactClaimLimit(new BigDecimal(rs.getString("CONTACT_CLAIM_LIMIT")));
					}//end of if(rs.getString("CONTACT_CLAIM_LIMIT") != null)
					personalInfoVO.setPreauthAppYN(TTKCommon.checkNull(rs.getString("pat_app_yn")));//denial process
					personalInfoVO.setPreauthRejYN(TTKCommon.checkNull(rs.getString("pat_rej_yn")));//denial process
					personalInfoVO.setClaimsAppYN(TTKCommon.checkNull(rs.getString("clm_app_yn")));//denial process
					personalInfoVO.setClaimsRejYN(TTKCommon.checkNull(rs.getString("clm_rej_yn")));//denial process
					personalInfoVO.setProfessionalId(TTKCommon.checkNull(rs.getString("hosp_prof_id")));
					personalInfoVO.setProfessionalAuthority(TTKCommon.checkNull(rs.getString("prof_authority")));
					personalInfoVO.setStartDate(rs.getString("start_date")!=null ? new Date(rs.getTimestamp("start_date").getTime()):null);
					personalInfoVO.setEndDate(rs.getString("end_date")!=null ? new Date(rs.getTimestamp("end_date").getTime()):null);
					//personalInfoVO.setStartDate(TTKCommon.checkNull(rs.getString("start_date")));
					//personalInfoVO.setEndDate(TTKCommon.checkNull(rs.getString("end_date")));
					additionalInfoVO.setSoftcopyAccessYN(TTKCommon.checkNull(rs.getString("SOFTCOPY_ACCESS_YN")));
					additionalInfoVO.setSoftcopyOtBranch(TTKCommon.checkNull(rs.getString("SOFTCOPY_OTHER_BRANCH_YN")));
					additionalInfoVO.setDateOfJoining(rs.getString("DATE_OF_JOINING")!=null ? new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()):null);
					additionalInfoVO.setDateOfResgn(rs.getString("DATE_OF_RESIGNATION")!=null ? new Date(rs.getTimestamp("DATE_OF_RESIGNATION").getTime()):null);
					personalInfoVO.setStdCode(TTKCommon.checkNull(rs.getString("STD_CODE")));
					personalInfoVO.setIsdCode(TTKCommon.checkNull(rs.getString("ISD_CODE")));
					personalInfoVO.setFile(TTKCommon.checkNull(rs.getString("PROF_FILE")));
					personalInfoVO.setFileName(TTKCommon.checkNull(rs.getString("PROF_FILE_NAME")));
					personalInfoVO.setConsultTypeCode(TTKCommon.checkNull(rs.getString("Consult_Gen_Type")));
					personalInfoVO.setNationalityTypeCode(TTKCommon.checkNull(rs.getString("Nationality_ID")));
					personalInfoVO.setGender(TTKCommon.checkNull(rs.getString("gender")));
					personalInfoVO.setAge(TTKCommon.checkNull(rs.getString("age")));
					personalInfoVO.setSpeciality(TTKCommon.checkNull(rs.getString("speciality")));
					contactVO.setAdditionalInfo(additionalInfoVO);
					contactVO.setPersonalInfo(personalInfoVO);
					contactVO.setUserAccessInfo(userAccessVO);
						userAccessVO = null;
					additionalInfoVO = null;
					personalInfoVO = null;
				} //end of  while(rs.next())
			}//end of if(rs != null)
			return contactVO;
		} //end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "user");
		} catch (Exception exp) {
			throw new TTKException(exp, "user");
		}
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
					log.error("Error while closing the Resultset in CallCenterDAOImpl getContact()",sqlExp);
					throw new TTKException(sqlExp, "user");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CallCenterDAOImpl getContact()",sqlExp);
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
							log.error("Error while closing the Connection in CallCenterDAOImpl getContact()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		
	}// End of getContact(Long lngContactSeqID)
	
	/**
	 * This method returns the ContactVO which contains the details of UserContact
	 * @param lngContactSeqID the contact sequence if which the Contact information is to be fetched
	 * @return ContactVO which contains the information of UserContact
	 * @exception throws TTKException
	 */
	public ContactVO getProfessionalContact(Long lngContactSeqID) throws TTKException
	{
		ContactVO contactVO = new ContactVO();
		UserAccessVO userAccessVO = new UserAccessVO();
		AdditionalInfoVO additionalInfoVO = new AdditionalInfoVO();
		PersonalInfoVO personalInfoVO = new PersonalInfoVO();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try {
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetProfessionalContact);
			cStmtObject.setLong(1,lngContactSeqID.longValue());
		//	cStmtObject.registerOutParameter(2,OracleTypes.CURSOR);// CONTACT DETAILS CURSOR
			cStmtObject.registerOutParameter(1,Types.BIGINT);
			cStmtObject.registerOutParameter(2,Types.OTHER);// CONTACT DETAILS CURSOR
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(2);
			if(rs != null){
				while (rs.next()) {
					userAccessVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
					userAccessVO.setUserType(TTKCommon.checkNull(rs.getString("USER_GENERAL_TYPE_ID")));
					contactVO.setContactSeqID((rs.getString("CONTACT_SEQ_ID")!=null) ? new Long (rs.getLong("CONTACT_SEQ_ID")):null);
					contactVO.setHospitalSeqID((rs.getString("HOSP_SEQ_ID")!= null) ? new Long(rs.getLong("HOSP_SEQ_ID")): null);
					contactVO.setInsuranceSeqID((rs.getString("INS_SEQ_ID")!=null) ? new Long(rs.getLong("INS_SEQ_ID")): null);
					additionalInfoVO.setOfficeSeqID((rs.getString("TPA_OFFICE_SEQ_ID")!=null) ? new Integer(rs.getInt("TPA_OFFICE_SEQ_ID")): null);
					contactVO.setGroupRegSeqID((rs.getString("GROUP_REG_SEQ_ID")!=null) ? new Long(rs.getLong("GROUP_REG_SEQ_ID")): null);
					contactVO.setBankSeqID((rs.getString("BANK_SEQ_ID")!=null) ? new Long (rs.getLong("BANK_SEQ_ID")):null);
					personalInfoVO.setPrefix(TTKCommon.checkNull(rs.getString("PREFIX_GENERAL_TYPE_ID")));
					personalInfoVO.setName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					personalInfoVO.setDesignationTypeID(TTKCommon.checkNull(rs.getString("DESIGNATION_TYPE_ID")));
					personalInfoVO.setDesignationDesc(TTKCommon.checkNull(rs.getString("DESIGNATION")));
					personalInfoVO.setPrimaryEmailID(TTKCommon.checkNull(rs.getString("PRIMARY_EMAIL_ID")));
					personalInfoVO.setSecondaryEmailID(TTKCommon.checkNull(rs.getString("SECONDARY_EMAIL_ID")));
					personalInfoVO.setActiveYN(TTKCommon.checkNull(rs.getString("ACTIVE_YN")));
					userAccessVO.setAccessYN(TTKCommon.checkNull(rs.getString("PROVIDE_ACCESS_USER_YN")));
					additionalInfoVO.setContactTypeID(TTKCommon.checkNull(rs.getString("CONTACT_TYPE_ID")));
					additionalInfoVO.setSpecTypeID(TTKCommon.checkNull(rs.getString("SPEC_TYPE_ID")));
					additionalInfoVO.setRegistrationNbr(TTKCommon.checkNull(rs.getString("DR_REGIST_NMBR")));
					additionalInfoVO.setQualification(TTKCommon.checkNull(rs.getString("DR_QUALIF")));
					additionalInfoVO.setResidentYN(TTKCommon.checkNull(rs.getString("RESIDENT_DR_YN")));
					additionalInfoVO.setEmployeeNbr(TTKCommon.checkNull(rs.getString("EMPLOYEE_NUMBER")));
					personalInfoVO.setPhoneNbr1(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_1")));
					personalInfoVO.setPhoneNbr2(TTKCommon.checkNull(rs.getString("OFF_PHONE_NO_2")));
					personalInfoVO.setResidencePhoneNbr(TTKCommon.checkNull(rs.getString("RES_PHONE_NO")));
					personalInfoVO.setMobileNbr(TTKCommon.checkNull(rs.getString("MOBILE_NO")));
					personalInfoVO.setFaxNbr(TTKCommon.checkNull(rs.getString("FAX_NO")));
					additionalInfoVO.setDepartmentID(TTKCommon.checkNull(rs.getString("DEPT_GENERAL_TYPE_ID")));
					userAccessVO.setRoleID(rs.getString("ROLE_SEQ_ID")!= null ? new Long(rs.getLong("ROLE_SEQ_ID")):null);
					userAccessVO.setEmpanelNbr(TTKCommon.checkNull(rs.getString("EMPANEL_NUMBER")));
					userAccessVO.setOfficeCode(TTKCommon.checkNull(rs.getString("INS_OFFICE_CODE")));
					userAccessVO.setOfficeName(rs.getString("INS_COMP_NAME")!=null ? TTKCommon.checkNull(rs.getString("INS_COMP_NAME")): TTKCommon.checkNull(rs.getString("HOSP_NAME")));
					userAccessVO.setGroupID(TTKCommon.checkNull(rs.getString("GROUP_ID")));
					userAccessVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));

					//Changes Added for Password Policy CR KOC 1235
					personalInfoVO.setAccn_locked_YN(TTKCommon.checkNull(rs.getString("ACCN_LOCKED_YN")));
					//End changes for Password Policy CR KOC 1235

					if(rs.getString("CONTACT_PA_LIMIT") != null){
						additionalInfoVO.setContactPALimit(new BigDecimal(rs.getString("CONTACT_PA_LIMIT")));
					}//end of if(rs.getString("CONTACT_PA_LIMIT") != null)

					if(rs.getString("CONTACT_CLAIM_LIMIT") != null){
						additionalInfoVO.setContactClaimLimit(new BigDecimal(rs.getString("CONTACT_CLAIM_LIMIT")));
					}//end of if(rs.getString("CONTACT_CLAIM_LIMIT") != null)
					personalInfoVO.setPreauthAppYN(TTKCommon.checkNull(rs.getString("pat_app_yn")));//denial process
					personalInfoVO.setPreauthRejYN(TTKCommon.checkNull(rs.getString("pat_rej_yn")));//denial process
					personalInfoVO.setClaimsAppYN(TTKCommon.checkNull(rs.getString("clm_app_yn")));//denial process
					personalInfoVO.setClaimsRejYN(TTKCommon.checkNull(rs.getString("clm_rej_yn")));//denial process
					personalInfoVO.setProfessionalId(TTKCommon.checkNull(rs.getString("hosp_prof_id")));
					personalInfoVO.setProfessionalAuthority(TTKCommon.checkNull(rs.getString("prof_authority")));
					personalInfoVO.setStartDate(rs.getString("start_date")!=null ? new Date(rs.getTimestamp("start_date").getTime()):null);
					personalInfoVO.setEndDate(rs.getString("end_date")!=null ? new Date(rs.getTimestamp("end_date").getTime()):null);
					//personalInfoVO.setStartDate(TTKCommon.checkNull(rs.getString("start_date")));
					//personalInfoVO.setEndDate(TTKCommon.checkNull(rs.getString("end_date")));
					additionalInfoVO.setSoftcopyAccessYN(TTKCommon.checkNull(rs.getString("SOFTCOPY_ACCESS_YN")));
					additionalInfoVO.setSoftcopyOtBranch(TTKCommon.checkNull(rs.getString("SOFTCOPY_OTHER_BRANCH_YN")));
					additionalInfoVO.setDateOfJoining(rs.getString("DATE_OF_JOINING")!=null ? new Date(rs.getTimestamp("DATE_OF_JOINING").getTime()):null);
					additionalInfoVO.setDateOfResgn(rs.getString("DATE_OF_RESIGNATION")!=null ? new Date(rs.getTimestamp("DATE_OF_RESIGNATION").getTime()):null);
					personalInfoVO.setStdCode(TTKCommon.checkNull(rs.getString("STD_CODE")));
					personalInfoVO.setIsdCode(TTKCommon.checkNull(rs.getString("ISD_CODE")));
					personalInfoVO.setFile(TTKCommon.checkNull(rs.getString("PROF_FILE")));
					personalInfoVO.setFileName(TTKCommon.checkNull(rs.getString("PROF_FILE_NAME")));
					personalInfoVO.setConsultTypeCode(TTKCommon.checkNull(rs.getString("Consult_Gen_Type")));
					personalInfoVO.setNationalityTypeCode(TTKCommon.checkNull(rs.getString("Nationality_ID")));
					personalInfoVO.setGender(TTKCommon.checkNull(rs.getString("gender")));
					personalInfoVO.setAge(TTKCommon.checkNull(rs.getString("age")));
					personalInfoVO.setSpeciality(TTKCommon.checkNull(rs.getString("speciality")));
					contactVO.setAdditionalInfo(additionalInfoVO);
					contactVO.setPersonalInfo(personalInfoVO);
					contactVO.setUserAccessInfo(userAccessVO);
					userAccessVO = null;
					additionalInfoVO = null;
					personalInfoVO = null;
				} //end of  while(rs.next())
			}//end of if(rs != null)
			return contactVO;
		} //end of try
		catch (SQLException sqlExp) {
			throw new TTKException(sqlExp, "user");
		} catch (Exception exp) {
			throw new TTKException(exp, "user");
		}
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
					log.error("Error while closing the Resultset in CallCenterDAOImpl getContact()",sqlExp);
					throw new TTKException(sqlExp, "user");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in CallCenterDAOImpl getContact()",sqlExp);
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
							log.error("Error while closing the Connection in CallCenterDAOImpl getContact()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
		
	}// End of getProfessionalContact(Long lngContactSeqID)

	/**
	 * This method updates the user Contact information
	 * @param contactVO the contactVO which contains the information to be added or updated
	 * @param strIdentifier String which contains the Identifier for Saving Contacts
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @throws TTKException
	 */
	//denial process
	
	public long saveDenMail(Long lngContactSeqID,PersonalInfoVO personalInfoVO) throws TTKException
	{
		long lResult=0;
		//PersonalInfoVO personalInfoVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		
		try{
			conn = ResourceManager.getConnection();
			
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveDenMail);
				if(lngContactSeqID != null){
					cStmtObject.setLong(1,lngContactSeqID);
				}//end of if(contactVO.getContactSeqID() != null)
				else{
					cStmtObject.setLong(1,0);
				}//end of else
				cStmtObject.setString(2,personalInfoVO.getPreauthAppYN());
				cStmtObject.setString(3,personalInfoVO.getPreauthRejYN());
				cStmtObject.setString(4,personalInfoVO.getClaimsAppYN());
				cStmtObject.setString(5,personalInfoVO.getClaimsRejYN());
				cStmtObject.execute();
              conn.commit();
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
        			log.error("Error while closing the Statement in UserDAOImpl saveContact()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl saveContact()",sqlExp);
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
	}// End of saveContact(ContactVO contactVO,String strIdentifier)
	
	//denial process
	
	
	public long saveContact(ContactVO contactVO,String strIdentifier) throws TTKException
	{
		long lResult=0;
		AdditionalInfoVO additionalInfoVO = null;
		UserAccessVO userAccessVO = null;
		PersonalInfoVO personalInfoVO = contactVO.getPersonalInfo();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		if(!strIdentifier.equalsIgnoreCase("MYPROFILE")){
			additionalInfoVO = contactVO.getAdditionalInfo();
			userAccessVO = contactVO.getUserAccessInfo();
		}//end of if(!strIdentifier.equalsIgnoreCase("MYPROFILE"))

		
		
		try{
			
			conn = ResourceManager.getConnection();
			
		
			if(!strIdentifier.equalsIgnoreCase("MYPROFILE")){
				
				cStmtObject = conn.prepareCall(strSaveUserContact);
				cStmtObject.setLong(1,contactVO.getContactSeqID() != null ? contactVO.getContactSeqID():0);//CONTACT_SEQ_ID
				if(userAccessVO != null){
					cStmtObject.setString(2,userAccessVO.getUserType()); //USER_GENERAL_TYPE_ID
				}//end of if(userAccessVO != null)
				else{
					cStmtObject.setString(2,""); //USER_GENERAL_TYPE_ID
				}//end of else

				if (contactVO.getHospitalSeqID()!=null){
					cStmtObject.setLong(3, contactVO.getHospitalSeqID()); //HOSP_SEQ_ID
				}//end of if (contactVO.getHospitalSeqID()!=null)
				else{
					cStmtObject.setLong(3,0l);//HOSP_SEQ_ID
				}//end of else
				if (contactVO.getInsuranceSeqID()!= null){
					cStmtObject.setLong(4, contactVO.getInsuranceSeqID()); //INS_SEQ_ID
				}//end of if (contactVO.getInsuranceSeqID()!= null)
				else{
					cStmtObject.setLong(4,0l);//INS_SEQ_ID
				}//end of else

				if(additionalInfoVO != null){
					if (additionalInfoVO.getOfficeSeqID()!=null ){
						cStmtObject.setLong(5,additionalInfoVO.getOfficeSeqID());//TPA_OFFICE_SEQ_ID
					}//end of if (additionalInfoVO.getOfficeSeqID()!=null )
					else{
						cStmtObject.setLong(5,0l);//TPA_OFFICE_SEQ_ID
					}//end of else
				}//end of if(additionalInfoVO != null)
				else{
					cStmtObject.setLong(5,0l);//TPA_OFFICE_SEQ_ID
				}//end of else

				if (contactVO.getGroupRegSeqID()!= null){
					cStmtObject.setLong(6,contactVO.getGroupRegSeqID());//GROUP_REG_SEQ_ID
				}//end of if (contactVO.getGroupRegSeqID()!= null)
				else{
					cStmtObject.setLong(6,0l);//GROUP_REG_SEQ_ID
				}//end of else
				cStmtObject.setString(7,personalInfoVO.getPrefix());//PREFIX_GENERAL_TYPE_ID
				cStmtObject.setString(8, personalInfoVO.getName());//CONTACT_NAME
				cStmtObject.setString(9, personalInfoVO.getDesignationTypeID());//DESIGNATION_TYPE_ID
				cStmtObject.setString(10, personalInfoVO.getPrimaryEmailID());//PRIMARY_EMAIL_ID
				cStmtObject.setString(11, personalInfoVO.getSecondaryEmailID());//SECONDARY_EMAIL_ID
				cStmtObject.setString(12,!TTKCommon.checkNull(personalInfoVO.getActiveYN()).equals("") ? personalInfoVO.getActiveYN():"N");//ACTIVE_YN

				if(userAccessVO != null){
					cStmtObject.setString(13,!TTKCommon.checkNull(userAccessVO.getAccessYN()).equals("") ? userAccessVO.getAccessYN():"N");//PROVIDE_ACCESS_USER_YN
				}//end of if(userAccessVO != null)
				else{
					cStmtObject.setString(13,"N");
				}//end of else

				if(additionalInfoVO != null){
					cStmtObject.setString(14, additionalInfoVO.getContactTypeID());//CONTACT_TYPE_ID
					cStmtObject.setString(15, additionalInfoVO.getSpecTypeID());//SPEC_TYPE_ID
					cStmtObject.setString(16,additionalInfoVO.getRegistrationNbr());//DR_REGIST_NMBR
					cStmtObject.setString(17, additionalInfoVO.getQualification());//DR_QUALIF
					cStmtObject.setString(18,additionalInfoVO.getResidentYN());//RESIDENT_DR_YN
					cStmtObject.setString(19, additionalInfoVO.getEmployeeNbr());//EMPLOYEE_NUMBER
					cStmtObject.setString(20,additionalInfoVO.getDepartmentID());//DEPT_GENERAL_TYPE_ID
				}//end of if(additionalInfoVO != null)
				else{
					cStmtObject.setString(14,null);//CONTACT_TYPE_ID
					cStmtObject.setString(15,null);//SPEC_TYPE_ID
					cStmtObject.setString(16,null);//DR_REGIST_NMBR
					cStmtObject.setString(17,null);//DR_QUALIF
					cStmtObject.setString(18,null);//RESIDENT_DR_YN
					cStmtObject.setString(19,null);//EMPLOYEE_NUMBER
					cStmtObject.setString(20,null);//DEPT_GENERAL_TYPE_ID
				}//end of else
				cStmtObject.setString(21,personalInfoVO.getResidencePhoneNbr());//RES_PHONE_NO
				cStmtObject.setString(22,personalInfoVO.getPhoneNbr1());//OFF_PHONE_NO_1
				cStmtObject.setString(23,personalInfoVO.getPhoneNbr2());//OFF_PHONE_NO_2
				cStmtObject.setString(24,personalInfoVO.getMobileNbr());//MOBILE_NO
				cStmtObject.setString(25,personalInfoVO.getFaxNbr());//FAX_NO

				if(additionalInfoVO != null){
					if(additionalInfoVO.getContactPALimit() != null){
						cStmtObject.setBigDecimal(26,additionalInfoVO.getContactPALimit());
					}//end of if(additionalInfoVO.getContactPALimit() != null)
					else{
						cStmtObject.setBigDecimal(26,null);
					}//end of else
					
					if(additionalInfoVO.getContactClaimLimit() != null){
						cStmtObject.setBigDecimal(27,additionalInfoVO.getContactClaimLimit());
					}//end of if(additionalInfoVO.getContactClaimLimit() != null)
					else{
						cStmtObject.setBigDecimal(27,null);
					}//end of else
				}//end of if(additionalInfoVO != null)
				else{
					cStmtObject.setString(26,null);
					cStmtObject.setString(27,null);
				}//end of else

				cStmtObject.setLong(28 ,contactVO.getUpdatedBy());//UPDATED_BY

				if(userAccessVO != null){
					cStmtObject.setString(29,userAccessVO.getUserID());//USER_ID
					if (userAccessVO.getRoleID()!=null){
						cStmtObject.setLong(30, userAccessVO.getRoleID());//ROLE_SEQ_ID
					}//end of if (userAccessVO.getRoleID()!=null)
					else{
						cStmtObject.setLong(30,0l);//ROLE_SEQ_ID
					}//end of else
				}//end of if(userAccessVO != null)
				else{
					cStmtObject.setString(29,null);
					cStmtObject.setLong(30,0l);//ROLE_SEQ_ID
				}//end of else

				if(contactVO.getBankSeqID() != null){
					cStmtObject.setLong(31,contactVO.getBankSeqID());
				}//end of if(contactVO.getBankSeqID() != null)
				else{
					cStmtObject.setLong(31,0l);//BANK_SEQ_ID
				}//end of else
				cStmtObject.setString(32,additionalInfoVO.getSoftcopyAccessYN());//SOFTCOPY_ACCESS_YN
				cStmtObject.setString(33,additionalInfoVO.getSoftcopyOtBranch());//SOFTCOPY_OTHER_BRANCH_YN
				if(additionalInfoVO.getDateOfJoining()!=null)
				{
				  cStmtObject.setTimestamp(34, new Timestamp(additionalInfoVO.getDateOfJoining().getTime()));
				}//end of if(additionalInfoVO.getDateOfJoining()!=null)
				else
				{
					cStmtObject.setTimestamp(34, null);
				}//end of else
				if(additionalInfoVO.getDateOfResgn()!=null)
				{
					cStmtObject.setTimestamp(35, new Timestamp(additionalInfoVO.getDateOfResgn().getTime()));
				}//end of if(additionalInfoVO.getDateOfResgn()!=null)
				else
				{
					cStmtObject.setTimestamp(35, null);
				}//end of else

				//Changes Added for Password Policy CR KOC 1235
				cStmtObject.setString(37,personalInfoVO.getAccn_locked_YN());	//LOCK_YN
				cStmtObject.setString(38,personalInfoVO.getProfessionalId());
				cStmtObject.setString(39,personalInfoVO.getProfessionalAuthority());
				if(personalInfoVO.getStartDate()==null)
				{	cStmtObject.setTimestamp(40, null);//start date
				}else{
				cStmtObject.setTimestamp(40, new Timestamp(personalInfoVO.getStartDate().getTime()));//start date
				}
				if(personalInfoVO.getEndDate()==null){
					cStmtObject.setTimestamp(41,null);//end date
				}else{
				cStmtObject.setTimestamp(41, new Timestamp(personalInfoVO.getEndDate().getTime()));//end date
				}
				cStmtObject.setString(42,personalInfoVO.getStdCode());//end date
				cStmtObject.setString(43,personalInfoVO.getIsdCode());//end date
				cStmtObject.setString(44,personalInfoVO.getFileName());//File Name Path
				cStmtObject.setString(45,personalInfoVO.getFile());//File Name
				cStmtObject.setString(46,personalInfoVO.getConsultTypeCode());//Consultation Type
				if(!"".equals(TTKCommon.checkNull(personalInfoVO.getNationalityTypeCode())))
				cStmtObject.setLong(47,new Long(personalInfoVO.getNationalityTypeCode()));//Nationality
				else 
				cStmtObject.setLong(47,0);//Nationality
				
				cStmtObject.setString(48, personalInfoVO.getGender());//GENDER	
				
				if(!"".equals(TTKCommon.checkNull(personalInfoVO.getAge())))
				cStmtObject.setShort(49, new Short(personalInfoVO.getAge()));
				else cStmtObject.setShort(49, new Short("0"));
				cStmtObject.setString(50, null);
				cStmtObject.setLong(51, 0l);
				cStmtObject.setString(52, personalInfoVO.getSpeciality());
				
				if (contactVO.getPartnerSeqID()!=null){
					cStmtObject.setLong(53, contactVO.getPartnerSeqID()); //HOSP_SEQ_ID
				}//end of if (contactVO.getHospitalSeqID()!=null)
				else{
					
					cStmtObject.setLong(53,0l);//PTNR_SEQ_ID
				}//end of else
				cStmtObject.setString(54, personalInfoVO.getDesignation());//DESIGNATION
				
				if((userAccessVO.getContactType()!=null)||userAccessVO.getContactType().trim().equals(""))
					cStmtObject.setString(55, userAccessVO.getContactType());//Type of contact for partner contacts.
				//End changes for Password Policy CR KOC 1235

				//End changes for Password Policy CR KOC 1235

				userAccessVO = null;
				personalInfoVO = null;
				additionalInfoVO = null;
				cStmtObject.registerOutParameter(1,Types.BIGINT);//CONTACT_SEQ_ID
				cStmtObject.registerOutParameter(29, Types.VARCHAR);//USER_ID
				cStmtObject.registerOutParameter(36,Types.SMALLINT);//ROW_PROCESSED
				cStmtObject.execute();
				lResult = cStmtObject.getLong(1);//CONTACT_SEQ_ID
			}//end of if(!strIdentifier.equalsIgnoreCase("MYPROFILE"))

			else {
				cStmtObject = conn.prepareCall(strSaveProfile);
				if(contactVO.getContactSeqID() != null){
					cStmtObject.setLong(1,contactVO.getContactSeqID());
				}//end of if(contactVO.getContactSeqID() != null)
				else{
					cStmtObject.setLong(1,0);
				}//end of else

				cStmtObject.setString(2, personalInfoVO.getPrimaryEmailID());//PRIMARY_EMAIL_ID
				cStmtObject.setString(3, personalInfoVO.getSecondaryEmailID());//SECONDARY_EMAIL_ID
				cStmtObject.setString(4,personalInfoVO.getResidencePhoneNbr());//RES_PHONE_NO
				cStmtObject.setString(5,personalInfoVO.getPhoneNbr1());//OFF_PHONE_NO_1
				cStmtObject.setString(6,personalInfoVO.getPhoneNbr2());//OFF_PHONE_NO_2
				cStmtObject.setString(7,personalInfoVO.getMobileNbr());//MOBILE_NO
				cStmtObject.setLong(8 ,contactVO.getUpdatedBy());//UPDATED_BY
				cStmtObject.registerOutParameter(1,Types.NUMERIC);//CONTACT_SEQ_ID
				cStmtObject.registerOutParameter(9,Types.INTEGER);//ROW_PROCESSED
				cStmtObject.execute();
				lResult = cStmtObject.getBigDecimal(1).longValue();//CONTACT_SEQ_ID
				
			}//end of else
			conn.commit();
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
        			log.error("Error while closing the Statement in UserDAOImpl saveContact()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl saveContact()",sqlExp);
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
	}// End of saveContact(ContactVO contactVO,String strIdentifier)
	
	
	public long saveProfessionals(ContactVO contactVO,String strIdentifier) throws TTKException
	{
		long lResult=0;
		AdditionalInfoVO additionalInfoVO = null;
		UserAccessVO userAccessVO = null;
		PersonalInfoVO personalInfoVO = contactVO.getPersonalInfo();
		Connection conn = null;
		CallableStatement cStmtObject=null;
		if(!strIdentifier.equalsIgnoreCase("MYPROFILE")){
			additionalInfoVO = contactVO.getAdditionalInfo();
			userAccessVO = contactVO.getUserAccessInfo();
		}//end of if(!strIdentifier.equalsIgnoreCase("MYPROFILE"))

		try{
			conn = ResourceManager.getConnection();
			if(!strIdentifier.equalsIgnoreCase("MYPROFILE")){
				
				cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveProfessionalsContact);
				cStmtObject.setLong(1,contactVO.getContactSeqID() != null ? contactVO.getContactSeqID().longValue():0);//CONTACT_SEQ_ID

				if(userAccessVO != null){
					cStmtObject.setString(2,userAccessVO.getUserType()); //USER_GENERAL_TYPE_ID
				}//end of if(userAccessVO != null)
				else{
					cStmtObject.setString(2,""); //USER_GENERAL_TYPE_ID
				}//end of else

				if (contactVO.getHospitalSeqID()!=null){
					cStmtObject.setLong(3, contactVO.getHospitalSeqID().longValue()); //HOSP_SEQ_ID
				}//end of if (contactVO.getHospitalSeqID()!=null)
				else{
					cStmtObject.setLong(3,0);//HOSP_SEQ_ID
				}//end of else
				
				cStmtObject.setString(4, personalInfoVO.getName());//CONTACT_NAME
				cStmtObject.setString(5, personalInfoVO.getProfessionalId());//DESIGNATION_TYPE_ID
				cStmtObject.setString(6, personalInfoVO.getProfessionalAuthority());//PRIMARY_EMAIL_ID
				cStmtObject.setString(7, personalInfoVO.getGender());//SECONDARY_EMAIL_ID
				//cStmtObject.setString(8, personalInfoVO.getAge());//ACTIVE_YN
				if(!"".equals(TTKCommon.checkNull(personalInfoVO.getAge()))){
				cStmtObject.setLong(8,Long.parseLong(personalInfoVO.getAge()));//ACTIVE_YN
				}else{
					cStmtObject.setLong(8,0);//ACTIVE_YN
				}
				cStmtObject.setString(9, personalInfoVO.getConsultTypeCode());//Consultation Type
				if(personalInfoVO.getStartDate()==null)
				{	cStmtObject.setTimestamp(10, null);//start date
				}else{
				cStmtObject.setTimestamp(10, new Timestamp(personalInfoVO.getStartDate().getTime()));//start date
				}
				if(personalInfoVO.getEndDate()==null){
					cStmtObject.setTimestamp(11,null);//end date
				}else{
				cStmtObject.setTimestamp(11, new Timestamp(personalInfoVO.getEndDate().getTime()));//end date
				}
				cStmtObject.setString(12, personalInfoVO.getPrimaryEmailID());//OFF_PHONE_NO_1
				cStmtObject.setString(13, personalInfoVO.getActiveYN());//OFF_PHONE_NO_2
				cStmtObject.setString(14, personalInfoVO.getIsdCode());//
				cStmtObject.setString(15, personalInfoVO.getStdCode());//
				cStmtObject.setString(16, personalInfoVO.getPhoneNbr1());//OFF_PHONE_NO_1
				cStmtObject.setString(17, personalInfoVO.getPhoneNbr2());//OFF_PHONE_NO_2
				cStmtObject.setString(18, personalInfoVO.getMobileNbr());//MOBILE_NO
				cStmtObject.setString(19, personalInfoVO.getFaxNbr());//FAX_NO
				//cStmtObject.setString(20, personalInfoVO.getNationalityTypeCode());//Nationality
				if(!"".equals(TTKCommon.checkNull(personalInfoVO.getNationalityTypeCode()))){
					cStmtObject.setLong(20, Long.parseLong(personalInfoVO.getNationalityTypeCode()));//Nationality
					}else{
						cStmtObject.setLong(20,0);//ACTIVE_YN
					}
			
				cStmtObject.setString(21, personalInfoVO.getSpeciality());
				cStmtObject.setString(22, personalInfoVO.getFile());//File Name
				cStmtObject.setString(23, personalInfoVO.getFileName());//File Name Path
				cStmtObject.setLong(24 ,contactVO.getUpdatedBy());//UPDATED_BY

				/*userAccessVO = null;
				personalInfoVO = null;
				additionalInfoVO = null;*/

				cStmtObject.registerOutParameter(1,Types.BIGINT);//CONTACT_SEQ_ID
				cStmtObject.registerOutParameter(25,Types.BIGINT);//ROW_PROCESSED
				
				cStmtObject.execute();
				lResult = cStmtObject.getLong(1);//CONTACT_SEQ_ID
			}//end of if(!strIdentifier.equalsIgnoreCase("MYPROFILE"))

			conn.commit();
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
        			log.error("Error while closing the Statement in UserDAOImpl saveContact()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl saveContact()",sqlExp);
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
	}// End of saveProfessionals(ContactVO contactVO,String strIdentifier)

	/**
     * This method saves the Password Information
     * @param passwordVO PasswordVO which contains Password Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changePassword(PasswordVO passwordVO) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePassword);
			
			if(passwordVO.getUpdatedBy() != null)
			{
                cStmtObject.setLong(1,passwordVO.getUpdatedBy());//CONTACT_SEQ_ID
			}//end of if(passwordVO.getUpdatedBy() != null)
            else
            {
                cStmtObject.setLong(1,0);//CONTACT_SEQ_ID
            }//end of else
			cStmtObject.setString(2,passwordVO.getUserID());
			cStmtObject.setString(3,passwordVO.getOldPassword());
			cStmtObject.setString(4,passwordVO.getNewPassword());
			cStmtObject.registerOutParameter(5,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getInt(5);
			conn.commit();
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
        			log.error("Error while closing the Statement in UserDAOImpl changePassword()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl changePassword()",sqlExp);
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
	}//end of changePassword(PasswordVO passwordVO)

	/**
	 * This method returns the ArrayList, which contains the AuthorisedVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of AuthorisedVO object's which contains the authorised signatory details
	 * @exception throws TTKException
	 */
	public ArrayList getSignatoryList(ArrayList alSearchObjects) throws TTKException {
		Collection<Object> alResultList = new ArrayList<Object>();
		AuthorisedVO authorisedVO = null;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		ResultSet rs = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSignatoryList);

			if(alSearchObjects.get(0) != null){
				cStmtObject.setLong(1,(Long)alSearchObjects.get(0));
			}//end of if(alSearchObjects.get(0) != null)
			else{
				cStmtObject.setLong(1,0);
			}//end of else

			cStmtObject.setString(2,(String)alSearchObjects.get(1));
			cStmtObject.setString(3,(String)alSearchObjects.get(2));

			if(alSearchObjects.get(3) != null){
				cStmtObject.setLong(4,(Long)alSearchObjects.get(3));
			}//end of if(alSearchObjects.get(3) != null)
			else{
				cStmtObject.setLong(4,0);
			}//end of else

			if(alSearchObjects.get(4) != null){
				cStmtObject.setLong(5,(Long)alSearchObjects.get(4));
			}//end of if(alSearchObjects.get(4) != null)
			else{
				cStmtObject.setLong(5,0);
			}//end of else

			cStmtObject.setString(6,(String)alSearchObjects.get(6));
			cStmtObject.setString(7,(String)alSearchObjects.get(7));
			cStmtObject.setLong(8,Long.parseLong((String) alSearchObjects.get(8)));
			cStmtObject.setLong(9,Long.parseLong((String) alSearchObjects.get(9)));
			cStmtObject.setLong(10,(Long)alSearchObjects.get(5));
			cStmtObject.registerOutParameter(11,Types.OTHER);
			cStmtObject.execute();
			rs = (java.sql.ResultSet)cStmtObject.getObject(11);
			if(rs != null){
				while(rs.next()){
					authorisedVO = new AuthorisedVO();

					if(rs.getString("AUTH_SEQ_ID") != null){
						authorisedVO.setAuthSeqID(new Long(rs.getLong("AUTH_SEQ_ID")));
						
					}//end of if(rs.getString("AUTH_SEQ_ID") != null)

					if(rs.getString("CONTACT_SEQ_ID") != null){
						authorisedVO.setContactSeqId(new Long(rs.getLong("CONTACT_SEQ_ID")));
					}//end of if(rs.getString("CONTACT_SEQ_ID") != null)

					if(rs.getString("BANK_ACC_SEQ_ID") != null){
						authorisedVO.setBankAcctSeqID(new Long(rs.getLong("BANK_ACC_SEQ_ID")));
					}//end of if(rs.getString("BANK_ACC_SEQ_ID") != null)

					authorisedVO.setUserID(TTKCommon.checkNull(rs.getString("USER_ID")));
					authorisedVO.setUserName(TTKCommon.checkNull(rs.getString("CONTACT_NAME")));
					authorisedVO.setRoleName(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
					authorisedVO.setName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));

					alResultList.add(authorisedVO);
				}//end of while(rs.next())
			}//end of if(rs != null)
			return (ArrayList)alResultList;
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
					log.error("Error while closing the Resultset in UserDAOImpl getSignatoryList()",sqlExp);
					throw new TTKException(sqlExp, "user");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (cStmtObject != null) cStmtObject.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in UserDAOImpl getSignatoryList()",sqlExp);
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
							log.error("Error while closing the Connection in UserDAOImpl getSignatoryList()",sqlExp);
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
				cStmtObject = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getSignatoryList(ArrayList alSearchObjects)

    /**
     * This method deletes the requested information from the database
     * @param alDeleteList the arraylist of details of which has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteSignatory(ArrayList alDeleteList) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteSignatory);
            cStmtObject.setString(1, (String)alDeleteList.get(0));//CONCATENATED STRING OF  delete SEQ_IDS
            cStmtObject.setLong(2,(Long)alDeleteList.get(1));//ADDED_BY
            cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            conn.commit();
            iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
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
        			log.error("Error while closing the Statement in UserDAOImpl deleteSignatory()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl deleteSignatory()",sqlExp);
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
    }//end of deleteSignatory(ArrayList alDeleteList)

    /**
     * This method changes the password for the particular user-id.
     * @param lngContactSeqID the Long for which the password will be reseted.
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int resetPassword(Long lngContactSeqID,long lngUserSeqID) throws TTKException {
        int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strResetPassword);
            cStmtObject.setLong(1,lngContactSeqID);
            cStmtObject.setLong(2,lngUserSeqID);//ADDED_BY
            cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
            conn.commit();
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
        			log.error("Error while closing the Statement in UserDAOImpl resetPassword()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl resetPassword()",sqlExp);
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
    }//end of resetPassword(Long lngContactSeqID,long lngUserSeqID)
    
    /**
     * This method saves the Password Information
     * @param onlineChangePasswordVO OnlineChangePasswordVO which contains Password Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changeOnlinePassword(OnlineChangePasswordVO onlineChangePasswordVO) throws TTKException {
		int iResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
    	try{
    		conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePassword);
			if(onlineChangePasswordVO.getContactSeqID() != null)
			{
                cStmtObject.setLong(1,onlineChangePasswordVO.getContactSeqID());//CONTACT_SEQ_ID
			}//end of if(passwordVO.getUpdatedBy() != null)
            else
            {
                cStmtObject.setLong(1,0);//CONTACT_SEQ_ID
            }//end of else
			cStmtObject.setString(2,onlineChangePasswordVO.getUserID());
			cStmtObject.setString(3,onlineChangePasswordVO.getOldPassword());
			cStmtObject.setString(4,onlineChangePasswordVO.getNewPassword());
			cStmtObject.registerOutParameter(5,Types.INTEGER);//ROW_PROCESSED
			cStmtObject.execute();
			conn.commit();
			iResult = cStmtObject.getInt(5);
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
        			log.error("Error while closing the Statement in UserDAOImpl changePassword()",sqlExp);
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
        				log.error("Error while closing the Connection in UserDAOImpl changePassword()",sqlExp);
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
	}//end of changeOnlinePassword(OnlineChangePasswordVO onlineChangePasswordVO)

	//Changes Added for Password Policy CR KOC 1235
	/**
     * This method saves the Password policy information
     * @param User Management Password Policy which contains Password Policy Information
     * @exception throws TTKException
     */
		public int savePasswordConfigInfo(PasswordValVO passwordValVO) throws TTKException  {
            

			int iResult = 0;
	    	Connection conn = null;
	    	CallableStatement cStmtObject=null;
	    	try{
	    		conn = ResourceManager.getConnection();
	    		cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSavePasswordConfigInfo);
	                  String lockDays="";//added as per KOC 1257 ONlineaPasswordPolicy
	    		if(passwordValVO.getIdentifier() !="")		{
	    		 lockDays =(passwordValVO.getIdentifier().equalsIgnoreCase("TTK"))?passwordValVO.getLockDays():"";
	    		}  
	            cStmtObject.setString(1,lockDays);
	          
	           // cStmtObject.setString(1,passwordValVO.getLockDays());
	         // 
	            cStmtObject.setString(2,passwordValVO.getPasswordValidityDays());
	        //  
	            cStmtObject.setString(3,passwordValVO.getWrongAttempts());
	        //  
	            cStmtObject.setString(4,passwordValVO.getPwdAlert());
	         // 
	              cStmtObject.setString(5,passwordValVO.getIdentifier());
	          
	          
	            cStmtObject.registerOutParameter(6, Types.INTEGER);//ROW_PROCESSED
	            cStmtObject.execute();
	            iResult = cStmtObject.getInt(6);
	            conn.commit();
	        	/*cStmtObject.registerOutParameter(3, Types.INTEGER);//out parameter which gives the number of records deleted
				cStmtObject.execute();
				iResult = cStmtObject.getInt(3);
	            */
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
	        	// Nested Try Catch to ensure resource closure  
	        	try // First try closing the Statement
	        	{
	        		try
	        		{
	        			if (cStmtObject != null) cStmtObject.close();
	        		}//end of try
	        		catch (SQLException sqlExp)
	        		{
	        			log.error("Error while closing the Statement in UserDAOImpl savePasswordConfigInfo()",sqlExp);
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
	        				log.error("Error while closing the Connection in UserDAOImpl savePasswordConfigInfo()",sqlExp);
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
	  	}//end of savePasswordConfigInfo(PasswordValVO passwordValVO)

	/**
	 * This method getting the Password policy information
	 * @param User Management Password Policy which contains Password Policy Information
	 * @exception throws TTKException
	 *///Modified as per KOC1257 11PP added String Identifier
	public PasswordValVO getPasswordConfigInfo(String identifier) throws TTKException {
			PasswordValVO passwordValVO = new PasswordValVO();	
			Connection conn = null;
	    	//CallableStatement cStmtObject=null;
                   PreparedStatement pstmtObject=null;
	    	ResultSet rs = null;
	    	try{
	    		conn = ResourceManager.getConnection();
				pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strGetPasswordConfigInfo);
				//cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strGetPasswordConfigInfo);	
				if(identifier.equalsIgnoreCase("TTK"))	    {
	    			  pstmtObject.setLong(1, 1);
	    	                 }
	    			 else if(identifier.equalsIgnoreCase("WHR")){
	    			  pstmtObject.setLong(1, 2);
	    			 }
	    			else if(identifier.equalsIgnoreCase("INS")) {
                     	          pstmtObject.setLong(1, 3);
	                         }
	    			//Modified Changes as per 11PP

				rs = pstmtObject.executeQuery();
				if(rs != null){
					while(rs.next()){
						passwordValVO.setPasswordValidityDays(TTKCommon.checkNull(rs.getString("PASSWORD_VALID_DAYS")));
							
						passwordValVO.setPwdAlert(TTKCommon.checkNull(rs.getString("ALERT_DAYS")));
							
						passwordValVO.setWrongAttempts(TTKCommon.checkNull(rs.getString("WRONG_ATTEMPTS")));
							
							if(identifier.equalsIgnoreCase("TTK")){
					  	passwordValVO.setLockDays(TTKCommon.checkNull(rs.getString("ACN_EXPIRE_IN_DAYS")));
					        
					   
							}
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
				try // First try closing the result set
				{
					try
					{
						if (rs != null) rs.close();
		        	}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Resultset in UserDAOImpl GetPasswordConfigInfo()",sqlExp);
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
							log.error("Error while closing the Statement in UserDAOImpl GetPasswordConfigInfo()",sqlExp);
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
								log.error("Error while closing the Connection in UserDAOImpl GetPasswordConfigInfo()",sqlExp);
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
			return passwordValVO;
		}//end of getPasswordConfigInfo()
	
	
	/**
	 * This method getting the Password policy information
	 * @param User Management Password Policy which contains Password Policy Information
	 * @exception throws TTKException
	 *///Modified as per KOC1257 11PP added String Identifier
	public ArrayList getStdIsd(Long hospSeqId,String strIdentifier) throws TTKException {
			Connection conn = null;
			PreparedStatement pstmtObject=null;
	    	ResultSet rs = null;
	    	ArrayList<String> alStdIsd	=	new ArrayList();
	    	
	    	try{
	    		conn = ResourceManager.getConnection();
	    		if("INS".equals(strIdentifier))
	    			pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strGetStdIsdForIns);
	    		else if("BAK".equals(strIdentifier))
	    			pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strGetStdIsdForBak);
	    		else if("COR".equals(strIdentifier))
	    		pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strgetStdIsdForCorp);
	    		else if("BRO".equals(strIdentifier))
	    			pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strgetStdIsdForBroker);
	    		else
	    			pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strGetStdIsd);
	    			  pstmtObject.setLong(1, hospSeqId);

				rs = pstmtObject.executeQuery();
				if(rs != null){
					while(rs.next()){
						alStdIsd.add(0, TTKCommon.checkNull(rs.getString(1)));
						alStdIsd.add(1, TTKCommon.checkNull(rs.getString(2)));
							}
					}//end of while(rs.next())
				conn.commit();
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
						log.error("Error while closing the Resultset in UserDAOImpl GetPasswordConfigInfo()",sqlExp);
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
							log.error("Error while closing the Statement in UserDAOImpl GetPasswordConfigInfo()",sqlExp);
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
								log.error("Error while closing the Connection in UserDAOImpl GetPasswordConfigInfo()",sqlExp);
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

	
	/**
	 * This method getting the Password policy information
	 * @param User Management Password Policy which contains Password Policy Information
	 * @exception throws TTKException
	 *///Modified as per KOC1257 11PP added String Identifier
	public ArrayList getStdIsd1(Long ptnrSeqId,String strIdentifier) throws TTKException {
			Connection conn = null;
			PreparedStatement pstmtObject=null;
	    	ResultSet rs = null;
	    	ArrayList<String> alStdIsd	=	new ArrayList();
	    	
	    	try{
	    		conn = ResourceManager.getConnection();
	    		if("INS".equals(strIdentifier))
	    			pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strGetStdIsdForIns);
	    		else if("BAK".equals(strIdentifier))
	    			pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strGetStdIsdForBak);
	    		else if("COR".equals(strIdentifier))
	    		pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strgetStdIsdForCorp);
	    		else if("BRO".equals(strIdentifier))
	    			pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strgetStdIsdForBroker);
	    		else
	    			pstmtObject= (java.sql.CallableStatement)conn.prepareCall(strGetStdIsd1);
	    //		System.out.println("Value..."+ptnrSeqId);
	    		 pstmtObject.setLong(1, ptnrSeqId);

				rs = pstmtObject.executeQuery();
				if(rs != null){
					while(rs.next()){
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
						log.error("Error while closing the Resultset in UserDAOImpl GetPasswordConfigInfo()",sqlExp);
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
							log.error("Error while closing the Statement in UserDAOImpl GetPasswordConfigInfo()",sqlExp);
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
								log.error("Error while closing the Connection in UserDAOImpl GetPasswordConfigInfo()",sqlExp);
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

	/**
	 * This method changes the password for the particular user-id.
	 * @param lngUserSeqID 
	 * @param lngContactSeqID the Long for which the password will be reseted.
	 * @return int the value which returns greater than zero for successful execution of the task
	 * @exception throws TTKException
	 */
	public int resetPasswordAll(long lngUserSeqID) throws TTKException {
	        int iResult = 0;
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        try{
	            conn = ResourceManager.getConnection();
	            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strResetPasswordAll);
	            cStmtObject.setLong(1,lngUserSeqID);//ADDED_BY
	            cStmtObject.setInt(2,0);//ADDED_BY
	            cStmtObject.registerOutParameter(2, Types.INTEGER);//ROWS_PROCESSED
	            cStmtObject.execute();
	            iResult = cStmtObject.getInt(2);//ROWS_PROCESSED
	            
	            conn.commit();
	
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
	        			log.error("Error while closing the Statement in UserDAOImpl resetPasswordAll()",sqlExp);
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
	        				log.error("Error while closing the Connection in UserDAOImpl resetPasswordAll()",sqlExp);
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
	    }//end of resetPasswordAll(long lngUserSeqID)
	//End changes for Password Policy CR KOC 1235
}//end of UserDAOImpl
