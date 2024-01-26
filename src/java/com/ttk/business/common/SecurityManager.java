/**
 * @ (#) SecurityManager.java 8th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : SecurityManager.java
 * Author       : Nagaraj D V
 * Company      : Span Systems Corporation
 * Date Created : 8th Sep 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.common;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.security.GroupVO;
import com.ttk.dto.security.RoleVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.dto.usermanagement.UserVO;
import javax.ejb.*;

@Local
public interface SecurityManager{

	/**
	 * This method returns the UserVO object which contains all the user details
	 * The method also calls other methods like authenticate(UserVO userVO) and authorise(UserVO userVO), for the logged in user during this process.
	 * @param userVO UserVO object which contains the minimum user details like user id and password to be authenticated against the LDAP server
	 * @return UserSecurityProfile object which contains all the user details along with their role and profile information
	 * @exception throws TTKException
	 */
	public UserSecurityProfile login(UserVO userVO) throws TTKException;

    /**
     * This method returns the UserSecurityProfile object which contains all the user details
     * if he is a valid User.
     * @param userVO UserVO object which contains the minimum user details like user id and password to be authenticated against the LDAP server
     * @return UserSecurityProfile object which contains all the user details along with their role and profile information
     * @exception throws TTKException
     */
    public ArrayList<Object> externalLogin(UserVO userVO) throws TTKException;
    
    /**
     * This method returns the UserSecurityProfile object which contains all the user details
     * if he is a valid User.
     * @param strCustomerCode Customer Code
     * @return UserSecurityProfile object which contains all the user details along with their role and profile information
     * @exception throws TTKException
     */
    public UserSecurityProfile ipruLogin(UserVO userVO) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the RoleVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of RoleVO object's which contains the User Role Details
	 * @exception throws TTKException
	 */
	public ArrayList getRoleList(ArrayList alSearchObjects) throws TTKException;

	/**
	 * This method returns the Role Details And Previliages Information
	 * @param lRoleSeqId the roleseqid for which the details is required
	 * @return RoleVO object which contains the Details of the Role Information and Previliages
	 * @throws TTKException
	 */
	public RoleVO getRole(long lRoleSeqId) throws TTKException;

	/**
	 * This method adds or updates the User Role details
	 * @param roleVO RoleVO object which contains the user role details to be added/updated
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public long saveRole(RoleVO roleVO) throws TTKException;

	/**
	 * This method deletes the roles from the Database
	 * @param strDeleteRoleSeqID String which contains the user role id's which has to be deleted
	 * @return int, no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteRole(String strDeleteRoleSeqID) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the GroupVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of GroupVO object's which contains the user group Information
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns the GroupVO, which contains the Group details which are populated from the database
	 * @param lngGroupBranchSeqID long value which contains the Group Branch Seq ID
	 * @return GroupVO which contains the user group Information
	 * @exception throws TTKException
	 */
	public GroupVO getGroup(long lngGroupBranchSeqID) throws TTKException;

	/**
	 * This method adds or updates the User Group details
	 * @param groupVO GroupVO object which contains the user groups details to be added/updated
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public long saveGroup(GroupVO groupVO) throws TTKException;

	/**
	 * This method deletes the user groups from the Database
	 * @param strGroupBranchSeqID String which contains the GROUP_BRANCH_SEQ_ID'S which has to be deleted
	 * @return int, no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteGroup(String strGroupBranchSeqID) throws TTKException;

	//Changes Added for Password Policy CR KOC 1235
	/**
	 * This method update the user login ip address to null from the Database
	 * @param strUserID String which contains the User_ID'S which has to be updated
	 * @return String, successful execution of the task
	 * @exception throws TTKException
	 */
	public int userLoginIPAddress(String UserID) throws TTKException;
	
	/**
	 * This method update the user login ip address to null from the Database
	 * @param UserVO contains strUserID String which contains the User_ID'S which has to be updated
	 * @return String, successful execution of the task
	 * @exception throws TTKException
	 */
	public String userLogout(UserVO userVO) throws TTKException;
	
	
	/**
	 * This method update the user login ip address to null from the Database
	 * @param strUserID String which contains the User_ID'S which has to be updated
	 * @return String, successful execution of the task
	 * @exception throws TTKException
	 */
	public int userSessionLogoutIPAddress(String SessionLogoutIPAddress) throws TTKException;
	//End changes for Password Policy CR KOC 1235
	
	//Changes Added for  KOC 1349
	public int userLoginRandomNo(String UserID,String RandomNo) throws TTKException;
}//end of class SecurityManager