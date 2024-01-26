/**
 * @ (#) SecurityDAOImpl.java Dec 27, 2005
 * Project       : TTK HealthCare Services
 * File          : SecurityDAOImpl.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Dec 27, 2005
 * @author       : Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.dao.impl.security;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.security.GroupVO;
import com.ttk.dto.security.RoleVO;
import com.ttk.dto.usermanagement.UserVO;

public class SecurityDAOImpl implements Serializable, BaseDAO {

    private static Logger log = Logger.getLogger(SecurityDAOImpl.class );
	private static final String strRoleLIist  = "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY A.#) Q FROM  (SELECT A.ROLE_SEQ_ID ,A.ROLE_NAME , A.ROLE_DESC , B.DESCRIPTION FROM app.TPA_ROLES_CODE A JOIN app.TPA_GENERAL_CODE B ON (A.USER_GENERAL_TYPE_ID = B.GENERAL_TYPE_ID) WHERE ROLE_NAME NOT IN ( 'ADMIN','EXTERNAL')";
	private static final String strGetRole = "SELECT  ROLE_SEQ_ID,ROLE_NAME,ROLE_DESC,PRIVILEGE,USER_GENERAL_TYPE_ID FROM app.TPA_ROLES_CODE A WHERE A.ROLE_SEQ_ID = ?";
	private static final String strSaveRole = "{CALL CONTACT_PKG_SAVE_ROLES(?,?,?,?,?,?,?)}";
	private static final String strDeleteRole = "{CALL CONTACT_PKG_DELETE_ROLES(?,?)}";
	
	//KOC Cigna_insurance_resriction
	private static final String strGroupsList = "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY A.#) Q FROM (SELECT B.GROUP_SEQ_ID,A.GROUP_BRANCH_SEQ_ID,C.OFFICE_CODE,B.GROUP_NAME,B.GROUP_DESCRIPTION,C.TPA_OFFICE_SEQ_ID,C.OFFICE_NAME,a.user_type,B.GROUP_TYPE FROM app.TPA_GROUP_BRANCH A JOIN app.TPA_GROUPS B ON (A.GROUP_SEQ_ID = B.GROUP_SEQ_ID) JOIN app.TPA_OFFICE_INFO C ON (A.TPA_OFFICE_SEQ_ID = C.TPA_OFFICE_SEQ_ID)";
	//KOC Cigna_insurance_resriction
	//private static final String strGroupsList = "SELECT * FROM (SELECT A.*,DENSE_RANK() OVER(ORDER BY #,ROWNUM) Q FROM (SELECT B.GROUP_SEQ_ID,A.GROUP_BRANCH_SEQ_ID,C.OFFICE_CODE,B.GROUP_NAME,B.GROUP_DESCRIPTION,C.TPA_OFFICE_SEQ_ID,C.OFFICE_NAME,B.group_type FROM TPA_GROUP_BRANCH A JOIN TPA_GROUPS B ON (A.GROUP_SEQ_ID = B.GROUP_SEQ_ID) JOIN TPA_OFFICE_INFO C ON (A.TPA_OFFICE_SEQ_ID = C.TPA_OFFICE_SEQ_ID)";
	
	private static final String strSelectGroup = "SELECT B.GROUP_SEQ_ID,A.GROUP_BRANCH_SEQ_ID,C.OFFICE_CODE,B.GROUP_NAME,B.GROUP_DESCRIPTION,C.TPA_OFFICE_SEQ_ID,B.group_type FROM TPA_GROUP_BRANCH A JOIN TPA_GROUPS B ON (A.GROUP_SEQ_ID = B.GROUP_SEQ_ID) JOIN TPA_OFFICE_INFO C ON (A.TPA_OFFICE_SEQ_ID = C.TPA_OFFICE_SEQ_ID) WHERE A.GROUP_BRANCH_SEQ_ID=?";
	private static final String strSaveGroup = "{CALL CONTACT_PKG_SAVE_GROUP_BRANCH(?,?,?,?,?,?,?,?,?,?)}";//KOC Cigna_insurance_resriction one parameter
	private static final String strDeleteGroup = "{CALL CONTACT_PKG_DELETE_GROUPS(?,?)}";
	//Changes Added for Password Policy CR KOC 1235
	private static final String strUpdateLoginIPAddress = "{CALL AUTHENTICATION_PKG_LOGOUT(?,?,?)}";
	private static final String strUserUpdateLoginIPAddress = "{CALL AUTHENTICATION_PKG_USER_LOGOUT(?,?,?,?,?)}";
	
	//End changes for Password Policy CR KOC 1235
	//Changes Added for KOC 1349
	private static final String strUpdateRandomNo = "{CALL authentication_pkg_online_logoff(?,?,?)}";
	//End changes for  CR KOC 1349
	private static final int ROLE_SEQ_ID  = 1;
	private static final int ROLE_NAME = 2;
	private static final int ROLE_DESC = 3;
	private static final int PRIVILEGE = 4;
	private static final int USER_GENERAL_TYPE_ID = 5;
	private static final int ADDED_BY = 6;
	private static final int ROW_PROCESSED = 7;

	/**
	 * This method returns the ArrayList, which contains the RoleVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of RoleVO object's which contains the User Role Details
	 * @exception throws TTKException
	 */
	public ArrayList getRoleList(ArrayList alSearchObjects) throws TTKException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String strStaticQuery = strRoleLIist;
		StringBuffer sbfDynamicQuery = new StringBuffer();
		RoleVO roleVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			for(int i=0; i < alSearchObjects.size()-4; i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
				{
					if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					{
						sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
					}//End of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
					else
					{
						sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
					}//End of else
					//}// end of else
				}// End of if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for()
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		//build the Order By Clause
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		//build the row numbers to be fetched
		sbfDynamicQuery.append( " )A )al WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		try{
			conn = ResourceManager.getConnection();
			stmt = conn.prepareStatement(strStaticQuery);
			rs = stmt.executeQuery();
			if (rs!=null) {
				while (rs.next())
				{
					roleVO = new RoleVO();
					roleVO.setRoleSeqID(rs.getString("ROLE_SEQ_ID")!= null ? new Long(rs.getLong("ROLE_SEQ_ID")):null);
					roleVO.setRoleName(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
					roleVO.setRoleDesc(TTKCommon.checkNull(rs.getString("ROLE_DESC")));
					roleVO.setUserType(TTKCommon.checkNull(rs.getString("DESCRIPTION")));
					resultList.add(roleVO);
				}// End of while (rs.next())
			}// End of if (rs!=null)
			return (ArrayList) resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "security");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "security");
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
					log.error("Error while closing the Resultset in SecurityDAOImpl getRoleList()",sqlExp);
					throw new TTKException(sqlExp, "security");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in SecurityDAOImpl getRoleList()",sqlExp);
						throw new TTKException(sqlExp, "security");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in SecurityDAOImpl getRoleList()",sqlExp);
							throw new TTKException(sqlExp, "security");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "security");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally	
	}//End of getRoleList(ArrayList alSearchObjects)

	/**
	 * This method returns the Role Details And Previliages Information
	 * @param lngRoleSeqID the roleseqid for which the details is required
	 * @return RoleVO object which contains the Details of the Role Information and Previliages
	 * @throws TTKException
	 */
	public RoleVO getRole(long lngRoleSeqID) throws TTKException
	{
		Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

		try
		{
			RoleVO roleVO = new RoleVO();
			conn = ResourceManager.getConnection();
			stmt = conn.prepareCall(strGetRole);
			stmt.setLong(1,lngRoleSeqID);
			rs =  stmt.executeQuery();
			if (rs!=null)
			{
				while (rs.next()) {
					roleVO.setRoleSeqID(rs.getString("ROLE_SEQ_ID")!=null ? new Long(rs.getString("ROLE_SEQ_ID")): null);
					roleVO.setRoleName(TTKCommon.checkNull(rs.getString("ROLE_NAME")));
					roleVO.setRoleDesc(TTKCommon.checkNull(rs.getString("ROLE_DESC")));
					roleVO.setUserType(TTKCommon.checkNull(rs.getString("USER_GENERAL_TYPE_ID")));
					if(rs.getSQLXML("PRIVILEGE") != null) {
	                        SAXReader saxReader=new SAXReader();
	                        roleVO.setPrivilege( saxReader.read( rs.getSQLXML("PRIVILEGE").getCharacterStream()));
					  }// End of if(rs.getOPAQUE("PRIVILEGE")
				}//end of while
			}//end of if (rs!=null)
			return roleVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "security");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "security");
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
					log.error("Error while closing the Resultset in SecurityDAOImpl getRole()",sqlExp);
					throw new TTKException(sqlExp, "security");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in SecurityDAOImpl getRole()",sqlExp);
						throw new TTKException(sqlExp, "security");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in SecurityDAOImpl getRole()",sqlExp);
							throw new TTKException(sqlExp, "security");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "security");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally		
	}// End of getRole(long lngRoleSeqID)

	/**
	 * This method adds or updates the User Role details
	 * @param roleVO RoleVO object which contains the user role details to be added/updated
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public long saveRole(RoleVO roleVO) throws TTKException
	{
		long lResult = 0;
		Connection conn = null;
		CallableStatement cStmtObject = null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = conn.prepareCall(strSaveRole);
			cStmtObject.setLong(ROLE_SEQ_ID, roleVO.getRoleSeqID() != null ? roleVO.getRoleSeqID():0);
			cStmtObject.setString(ROLE_NAME,roleVO.getRoleName());
			cStmtObject.setString(ROLE_DESC,roleVO.getRoleDesc());
			
			SQLXML xmlVal = conn.createSQLXML();
			xmlVal.setString(roleVO.getPrivilege().asXML());			
			cStmtObject.setSQLXML(PRIVILEGE, xmlVal);
			
			cStmtObject.setString(USER_GENERAL_TYPE_ID,roleVO.getUserType()); // User General Type Id
			cStmtObject.setLong(ADDED_BY,roleVO.getUpdatedBy());
			cStmtObject.registerOutParameter(ROW_PROCESSED,Types.SMALLINT);//ROW_PROCESSED
			cStmtObject.registerOutParameter(ROLE_SEQ_ID,Types.BIGINT);//ROW_PROCESSED
			cStmtObject.execute();
			lResult=cStmtObject.getShort(ROW_PROCESSED);
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "security");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "security");
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
        			log.error("Error while closing the Statement in SecurityDAOImpl saveRole()",sqlExp);
        			throw new TTKException(sqlExp, "security");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in SecurityDAOImpl saveRole()",sqlExp);
        				throw new TTKException(sqlExp, "security");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "security");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return  lResult;
	}// End of  saveRole(RoleVO roleVO)

	/**
	 * This method deletes the roles from the Database
	 * @param strDeleteRoleSeqID String which contains the user role id's which has to be deleted
	 * @return int, no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteRole(String strDeleteRoleSeqID) throws TTKException
	{
		int iResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteRole);
			cStmtObject.setString(1,strDeleteRoleSeqID); //Concatenated String of ROLE_SEQ_ID s
			cStmtObject.registerOutParameter(2,Types.SMALLINT);//ROW_PROCESSED
			cStmtObject.execute();
			iResult = cStmtObject.getShort(2);
			conn.commit();
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "security");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "security");
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
        			log.error("Error while closing the Statement in SecurityDAOImpl deleteRole()",sqlExp);
        			throw new TTKException(sqlExp, "security");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in SecurityDAOImpl deleteRole()",sqlExp);
        				throw new TTKException(sqlExp, "security");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "security");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return  iResult;
	}// End of deleteRole(ArrayList arrayList)

	/**
	 * This method returns the ArrayList, which contains the GroupVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of GroupVO object's which contains the user group Information
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchObjects) throws TTKException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String strStaticQuery = strGroupsList;
		StringBuffer sbfDynamicQuery = new StringBuffer();
		GroupVO groupVO = null;
		Collection<Object> resultList = new ArrayList<Object>();
		if(alSearchObjects != null && alSearchObjects.size() > 0)
		{
			for(int i=0; i < alSearchObjects.size()-4; i++)
			{
				if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals("")){
					if(sbfDynamicQuery.toString().equals(""))
					{
						if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
						{
							sbfDynamicQuery.append(" WHERE "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
						}//End of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
						else
						{
							sbfDynamicQuery.append(" WHERE UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
						}//End of else
					}// end of  end of 	if(sbfDynamicQuery.toString().equals(""))
					else {
						if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
						{
							sbfDynamicQuery.append(" AND "+((SearchCriteria)alSearchObjects.get(i)).getName()+" = '"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"' ");
						}//End of if(((SearchCriteria)alSearchObjects.get(i)).getOperator().equalsIgnoreCase("equals"))
						else
						{
							sbfDynamicQuery.append(" AND UPPER("+ ((SearchCriteria)alSearchObjects.get(i)).getName()+") LIKE UPPER('"+((SearchCriteria)alSearchObjects.get(i)).getValue()+"%')");
						}//End of else
					}// end of else
				 }//end of 	if(!((SearchCriteria)alSearchObjects.get(i)).getValue().equals(""))
			}//end of for(int i=0; i < alSearchObjects.size(); i++)
		}//end of if(alSearchCriteria != null && alSearchCriteria.size() > 0)
		//build the Order By Clause
		strStaticQuery = TTKCommon.replaceInString(strStaticQuery,"#", (String)alSearchObjects.get(alSearchObjects.size()-4)+" "+(String)alSearchObjects.get(alSearchObjects.size()-3));
		//build the row numbers to be fetched
		sbfDynamicQuery.append( " )A ) al WHERE Q >= "+(String)alSearchObjects.get(alSearchObjects.size()-2)+ " AND Q <= "+(String)alSearchObjects.get(alSearchObjects.size()-1));
		strStaticQuery = strStaticQuery + sbfDynamicQuery.toString();
		try{
			conn = ResourceManager.getConnection();
			stmt = conn.prepareStatement(strStaticQuery);
			rs = stmt.executeQuery();
            StringBuffer strGrpName=new StringBuffer();
			if (rs!=null) {
				while (rs.next())
				{
					groupVO = new GroupVO();
                    strGrpName.setLength(0);
					groupVO.setGroupSeqID(rs.getString("GROUP_SEQ_ID")!=null ? new Long(rs.getLong("GROUP_SEQ_ID")):null);
					groupVO.setGroupBranchSeqID(rs.getString("GROUP_BRANCH_SEQ_ID")!=null ? new Long(rs.getLong("GROUP_BRANCH_SEQ_ID")):null);
					groupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
                    strGrpName.append(TTKCommon.checkNull(rs.getString("GROUP_NAME"))).append("-").append(TTKCommon.checkNull(rs.getString("OFFICE_CODE")));
                    groupVO.setDisplayGroupName(strGrpName.toString());
					groupVO.setGroupDesc(TTKCommon.checkNull(rs.getString("GROUP_DESCRIPTION")));
					groupVO.setUserTypeID(TTKCommon.checkNull(rs.getString("GROUP_TYPE")));//GROUP TYPE- KOC BROKER LOGIN
					groupVO.setuserRestriction(TTKCommon.checkNull(rs.getString("user_type")));//KOC Cigna_insurance_resriction
					groupVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID") != null ? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
					groupVO.setOfficeName(TTKCommon.checkNull(rs.getString("OFFICE_NAME")));
					resultList.add(groupVO);
				}// End of while (rs.next())
			}// End of if (rs!=null)
			return (ArrayList) resultList;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "security");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "security");
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
					log.error("Error while closing the Resultset in SecurityDAOImpl getGroupList()",sqlExp);
					throw new TTKException(sqlExp, "security");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (stmt != null) stmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in SecurityDAOImpl getGroupList()",sqlExp);
						throw new TTKException(sqlExp, "security");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in SecurityDAOImpl getGroupList()",sqlExp);
							throw new TTKException(sqlExp, "callcenter");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "security");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				stmt = null;
				conn = null;
			}//end of finally
		}//end of finally	
	}//End of getGroupList(ArrayList alSearchObjects)
	
	/**
	 * This method returns the GroupVO, which contains the Group details which are populated from the database
	 * @param lngGroupBranchSeqID long value which contains the Group Branch Seq ID
	 * @return GroupVO which contains the user group Information
	 * @exception throws TTKException
	 */
	public GroupVO getGroup(long lngGroupBranchSeqID) throws TTKException{
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		GroupVO groupVO = null;
		try{
			conn = ResourceManager.getConnection();
			pStmt = conn.prepareStatement(strSelectGroup);
			pStmt.setLong(1,lngGroupBranchSeqID);
			rs = pStmt.executeQuery();
			if(rs != null){
				while(rs.next()){
					groupVO = new GroupVO();
					groupVO.setGroupSeqID(rs.getString("GROUP_SEQ_ID")!=null ? new Long(rs.getLong("GROUP_SEQ_ID")):null);
					groupVO.setGroupBranchSeqID(rs.getString("GROUP_BRANCH_SEQ_ID")!=null ? new Long(rs.getLong("GROUP_BRANCH_SEQ_ID")):null);
					groupVO.setGroupName(TTKCommon.checkNull(rs.getString("GROUP_NAME")));
					groupVO.setGroupDesc(TTKCommon.checkNull(rs.getString("GROUP_DESCRIPTION")));
					groupVO.setOfficeSeqID(rs.getString("TPA_OFFICE_SEQ_ID") != null ? new Long(rs.getLong("TPA_OFFICE_SEQ_ID")):null);
					groupVO.setUserTypeID(TTKCommon.checkNull(rs.getString("GROUP_TYPE")));
				}//end of while(rs.next())
			}//end of if(rs != null)
			return groupVO;
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "security");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "security");
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
					log.error("Error while closing the Resultset in SecurityDAOImpl getGroup()",sqlExp);
					throw new TTKException(sqlExp, "security");
				}//end of catch (SQLException sqlExp)
				finally // Even if result set is not closed, control reaches here. Try closing the statement now.
				{
					try
					{
						if (pStmt != null) pStmt.close();
					}//end of try
					catch (SQLException sqlExp)
					{
						log.error("Error while closing the Statement in SecurityDAOImpl getGroup()",sqlExp);
						throw new TTKException(sqlExp, "security");
					}//end of catch (SQLException sqlExp)
					finally // Even if statement is not closed, control reaches here. Try closing the connection now.
					{
						try
						{
							if(conn != null) conn.close();
						}//end of try
						catch (SQLException sqlExp)
						{
							log.error("Error while closing the Connection in SecurityDAOImpl getGroup()",sqlExp);
							throw new TTKException(sqlExp, "callcenter");
						}//end of catch (SQLException sqlExp)
					}//end of finally Connection Close
				}//end of finally Statement Close
			}//end of try
			catch (TTKException exp)
			{
				throw new TTKException(exp, "security");
			}//end of catch (TTKException exp)
			finally // Control will reach here in anycase set null to the objects 
			{
				rs = null;
				pStmt = null;
				conn = null;
			}//end of finally
		}//end of finally
	}//end of getGroup(long lngGroupBranchSeqID)

	/**
	 * This method adds or updates the User Group details
	 * @param groupVO GroupVO object which contains the user groups details to be added/updated
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public long saveGroup(GroupVO groupVO) throws TTKException
	{
		long lResult =0;
		Connection conn = null;
		CallableStatement cStmtObject=null;
		try{
			conn = ResourceManager.getConnection();
			cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strSaveGroup);
			cStmtObject.setLong(1,groupVO.getGroupSeqID()!= null ? groupVO.getGroupSeqID():0); //GROUP_SEQ_ID
			cStmtObject.setLong(2,groupVO.getGroupBranchSeqID()!= null ?groupVO.getGroupBranchSeqID():0); //GROUP_BRANCH_SEQ_ID
			cStmtObject.setString(3,groupVO.getGroupName());  //GROUP_NAME
			cStmtObject.setString(4,groupVO.getGroupDesc()); //GROUP_DESCRIPTION
			if(groupVO.getOfficeSeqID() != null)
			{
				cStmtObject.setLong(5,groupVO.getOfficeSeqID()); //TPA_OFFICE_SEQ_ID
			}//End of if(groupVO.getOfficeSeqID() != null)
			else
			{
				cStmtObject.setString(5,null);  //TPA_OFFICE_SEQ_ID
			}// End  of else
			cStmtObject.setString(6,groupVO.getUserTypeID());//GROUP_TYPE
			cStmtObject.setLong(7,new Long(1)); //GROUP_TYPE
			cStmtObject.setLong(8,groupVO.getUpdatedBy()); //USER_SEQ_ID
			cStmtObject.setString(9,groupVO.getuserRestriction());//user_type //KOC Cigna_insurance_resriction
			cStmtObject.registerOutParameter(10,Types.SMALLINT); //ROW_PROCESSED
			cStmtObject.registerOutParameter(1,Types.BIGINT); //GROUP_SEQ_ID
			cStmtObject.execute();
			lResult = cStmtObject.getLong(1);
			
			conn.commit();//
		}//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "security");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "security");
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
        			log.error("Error while closing the Statement in SecurityDAOImpl saveGroup()",sqlExp);
        			throw new TTKException(sqlExp, "security");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in SecurityDAOImpl saveGroup()",sqlExp);
        				throw new TTKException(sqlExp, "security");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "security");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return  lResult;
	}//end of saveGroup(GroupVO groupVO)

	/**
     * This method deletes the user groups from the Database
     * @param strGroupBranchSeqID String which contains the GROUP_BRANCH_SEQ_ID'S which has to be deleted
     * @return int, no of rows effected
     * @exception throws TTKException
     */
	public int deleteGroup(String strGroupBranchSeqID) throws TTKException
	{
        int iResult =0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
                conn = ResourceManager.getConnection();
                cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strDeleteGroup);
                cStmtObject.setString(1,strGroupBranchSeqID); //Concatenated String of GROUP_BRANCH_SEQ_ID'S
                cStmtObject.registerOutParameter(2,Types.SMALLINT);//ROW_PROCESSED
                cStmtObject.execute();
                iResult = cStmtObject.getShort(2);
                conn.commit();

        }//end of try
		catch (SQLException sqlExp)
		{
			throw new TTKException(sqlExp, "security");
		}//end of catch (SQLException sqlExp)
		catch (Exception exp)
		{
			throw new TTKException(exp, "security");
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
        			log.error("Error while closing the Statement in SecurityDAOImpl deleteGroup()",sqlExp);
        			throw new TTKException(sqlExp, "security");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in SecurityDAOImpl deleteGroup()",sqlExp);
        				throw new TTKException(sqlExp, "security");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "security");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
		return  iResult;
	}// End of deleteGroup(String strGroupBranchSeqID)

	//Changes Added for Password Policy CR KOC 1235
	/**
	 * This method update the user login ip address to null from the Database
	 * @param strUserID String which contains the User_ID'S which has to be updated
	 * @return String, successful execution of the task
	 * @exception throws TTKException
	 */
	public int userLoginIPAddress(String UserID) throws TTKException
	{
		int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUpdateLoginIPAddress);
            cStmtObject.setString(1,UserID);//ADDED_BY
            cStmtObject.setString(2,null);//ADDED_BY
            cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
            conn.commit();
        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "login");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "login");
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
        			log.error("Error while closing the Connection in SecurityDAOImpl userLoginIPAddress()",sqlExp);
        			throw new TTKException(sqlExp, "login");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in SecurityDAOImpl userLoginIPAddress()",sqlExp);
        				throw new TTKException(sqlExp, "login");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "login");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of userLoginIPAddress()
	//Changes Added for Password Policy CR KOC 1235
		/**
		 * This method update the user login ip address to null from the Database
		 * @param strUserID String which contains the User_ID'S which has to be updated
		 * @return String, successful execution of the task
		 * @exception throws TTKException
		 */
		public String userLogout(UserVO userVO) throws TTKException
		{
			String result = "";
	        Connection conn = null;
	        CallableStatement cStmtObject=null;
	        try{
	            conn = ResourceManager.getConnection();
	            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUserUpdateLoginIPAddress);
	            cStmtObject.setString(1,userVO.getUSER_ID());
	            cStmtObject.setString(2,userVO.getPassword());
	            cStmtObject.setString(3,userVO.getHostIPAddress());
	            cStmtObject.registerOutParameter(4, Types.VARCHAR);
	            cStmtObject.registerOutParameter(5, Types.INTEGER);//ROWS_PROCESSED
	            cStmtObject.execute();
	            result = cStmtObject.getString(4);
	            conn.commit();
	        }//end of try
	        catch (SQLException sqlExp)
	        {
	            throw new TTKException(sqlExp, "login");
	        }//end of catch (SQLException sqlExp)
	        catch (Exception exp)
	        {
	            throw new TTKException(exp, "login");
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
	        			log.error("Error while closing the Connection in SecurityDAOImpl userLogout()",sqlExp);
	        			throw new TTKException(sqlExp, "login");
	        		}//end of catch (SQLException sqlExp)
	        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
	        		{
	        			try
	        			{
	        				if(conn != null) conn.close();
	        			}//end of try
	        			catch (SQLException sqlExp)
	        			{
	        				log.error("Error while closing the Connection in SecurityDAOImpl userLogout()",sqlExp);
	        				throw new TTKException(sqlExp, "login");
	        			}//end of catch (SQLException sqlExp)
	        		}//end of finally Connection Close
	        	}//end of try
	        	catch (TTKException exp)
	        	{
	        		throw new TTKException(exp, "login");
	        	}//end of catch (TTKException exp)
	        	finally // Control will reach here in anycase set null to the objects 
	        	{
	        		cStmtObject = null;
	        		conn = null;
	        	}//end of finally
			}//end of finally
	        return result;
	    }//end of userLogout()
	/**
	 * This method update the user login ip address to null from the Database
	 * @param strUserID String which contains the User_ID'S which has to be updated
	 * @return String, successful execution of the task
	 * @exception throws TTKException
	 */
	public int userSessionLogoutIPAddress(String SessionLogoutIPAddress) throws TTKException
	{
		int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUpdateLoginIPAddress);
            cStmtObject.setString(1,null);//ADDED_BY
            cStmtObject.setString(2,SessionLogoutIPAddress);//ADDED_BY
            cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
            

        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "login");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "login");
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
        			log.error("Error while closing the Connection in SecurityDAOImpl userSessionLogoutIPAddress()",sqlExp);
        			throw new TTKException(sqlExp, "login");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in SecurityDAOImpl userSessionLogoutIPAddress()",sqlExp);
        				throw new TTKException(sqlExp, "login");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "login");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of userSessionLogoutIPAddress()
	//End changes for Password Policy CR KOC 1235
	
	
	
	//Changes Added for Password Policy CR KOC 1349
	/**
	 * This method update the user login ip address to null from the Database
	 * @param strUserID String which contains the User_ID'S which has to be updated
	 * @return String, successful execution of the task
	 * @exception throws TTKException
	 */
	//added for koc 1349
	
	public int userLoginRandomNo(String UserID,String RandomNo) throws TTKException
	{
		int iResult = 0;
        Connection conn = null;
        CallableStatement cStmtObject=null;
        try{
            conn = ResourceManager.getConnection();
            cStmtObject = (java.sql.CallableStatement)conn.prepareCall(strUpdateRandomNo);
            cStmtObject.setString(1,UserID);//ADDED_BY
            cStmtObject.setString(2,RandomNo);//ADDED_BY
            cStmtObject.registerOutParameter(3, Types.INTEGER);//ROWS_PROCESSED
            cStmtObject.execute();
            conn.commit();
            iResult = cStmtObject.getInt(3);//ROWS_PROCESSED
            

        }//end of try
        catch (SQLException sqlExp)
        {
            throw new TTKException(sqlExp, "login");
        }//end of catch (SQLException sqlExp)
        catch (Exception exp)
        {
            throw new TTKException(exp, "login");
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
        			log.error("Error while closing the Connection in SecurityDAOImpl userLoginIPAddress()",sqlExp);
        			throw new TTKException(sqlExp, "login");
        		}//end of catch (SQLException sqlExp)
        		finally // Even if statement is not closed, control reaches here. Try closing the connection now.
        		{
        			try
        			{
        				if(conn != null) conn.close();
        			}//end of try
        			catch (SQLException sqlExp)
        			{
        				log.error("Error while closing the Connection in SecurityDAOImpl userLoginIPAddress()",sqlExp);
        				throw new TTKException(sqlExp, "login");
        			}//end of catch (SQLException sqlExp)
        		}//end of finally Connection Close
        	}//end of try
        	catch (TTKException exp)
        	{
        		throw new TTKException(exp, "login");
        	}//end of catch (TTKException exp)
        	finally // Control will reach here in anycase set null to the objects 
        	{
        		cStmtObject = null;
        		conn = null;
        	}//end of finally
		}//end of finally
        return iResult;
    }//end of userLoginIPAddress()
	//end for koc 1349
}// End of SecurityDAOImpl
