/**
 * @ (#) SecurityManagerBean.java 8th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : SecurityManagerBean.java
 * Author       : Nagaraj D V
 * Company      : Span Systems Corporation
 * Date Created : 8th Sep 2005
 *
 * @author       : Nagaraj D V
 * Modified by   : Arun K N
 * Modified date : Mar 25, 2006
 * Reason        : for getting UserSecurityProfile Object with the help of the Helper class
 *                 which will read the UserProfileDoc returned from the DAO.
 */

package com.ttk.business.common;

import java.util.ArrayList;

import com.ttk.business.common.helper.SecurityManagerHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.common.CommonDAOFactory;
import com.ttk.dao.impl.security.SecurityDAOFactory;
import com.ttk.dao.impl.security.SecurityDAOImpl;
import com.ttk.dao.impl.usermanagement.LoginDAOImpl;
import com.ttk.dto.security.GroupVO;
import com.ttk.dto.security.RoleVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.dto.usermanagement.UserVO;

import javax.ejb.*;

import org.dom4j.Document;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class SecurityManagerBean implements SecurityManager{

	private SecurityDAOImpl securityDAO = null;
	private CommonDAOFactory commonDAOFactory = null;
	private SecurityDAOFactory securityDAOFactory = null;

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getSecurityDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if(commonDAOFactory == null)
				commonDAOFactory = new CommonDAOFactory();
			if (securityDAOFactory == null)
				securityDAOFactory = new SecurityDAOFactory();
			if(strIdentifier.equals("loginuser"))
			{
				return commonDAOFactory.getDAO("loginuser");
			}//end of if(strIdentifier.equals("loginuser"))
			else if(strIdentifier.equals("security"))
			{
				return securityDAOFactory.getDAO("security");
			}//end of if(strIdentifier.equals("security"))
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//end getLoginDAO()

	/**
	 * This method returns the UserVO object which contains all the user details
	 * The method also calls other methods like authenticate(UserVO userVO) and authorise(UserVO userVO), for the logged in user during this process.
	 * @param userVO UserVO object which contains the minimum user details like user id and password to be authenticated against the LDAP server
	 * @return UserSecurityProfile object which contains all the user details along with their role and profile information
	 * @exception throws TTKException
	 */
	public UserSecurityProfile login(UserVO userVO) throws TTKException
	{
		return SecurityManagerHelper.getUserSecurityProfile(((LoginDAOImpl)this.getSecurityDAO("loginuser")).userValidation(userVO));
	}//end of login(UserVO userVO)

    /**
     * This method returns the USer Securityobject which contains all the user details
     * if he is a valid user.
     * @param userVO UserVO object which contains the minimum user details like user id and password to be authenticated against the LDAP server
     * @return UserSecurityProfile object which contains all the user details along with their role and profile information
     * @exception throws TTKException
     */
    public ArrayList<Object> externalLogin(UserVO userVO) throws TTKException
    {
    	ArrayList<Object> alResult= new ArrayList<Object>() ;
    	ArrayList<Object> alResultList= ((LoginDAOImpl)this.getSecurityDAO("loginuser")).externalUserValidation(userVO);
    	Document userProfileDoc=(Document) alResultList.get(0);
    	UserSecurityProfile userSecurityProfile= SecurityManagerHelper.getOnlineSecurityProfile(userVO,userProfileDoc);
    	alResult.add(userSecurityProfile); 
    	alResult.add(alResultList.get(1));
    	alResult.add(alResultList.get(2));//added as per kOC 1257 11PP
		alResult.add(alResultList.get(3));//added as per kOC 1257 11PP
		alResult.add(alResultList.get(4));//added as per kOC 1349
    	return alResult;
    }//end of externalLogin(UserVO userVO)

	/**
	 * This method returns the ArrayList, which contains the RoleVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of RoleVO object's which contains the User Role Details
	 * @exception throws TTKException
	 */
	public ArrayList getRoleList(ArrayList alSearchObjects) throws TTKException{
		securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.getRoleList(alSearchObjects);
	}// End of getRoleList(ArrayList alSearchObjects)

	/**
	 * This method returns the Role Details And Previliages Information
	 * @param lRoleSeqId the roleseqid for which the details is required
	 * @return RoleVO object which contains the Details of the Role Information and Previliages
	 * @throws TTKException
	 */
	public RoleVO getRole(long lRoleSeqId) throws TTKException
	{
		securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.getRole(lRoleSeqId);
	}// End of getRole(long lRoleSeqId)

	/**
	 * This method adds or updates the User Role details
	 * @param roleVO RoleVO object which contains the user role details to be added/updated
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public long saveRole(RoleVO roleVO) throws TTKException
	{
		securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.saveRole(roleVO);
	}// End of saveRole(RoleVO roleVO)

	/**
	 * This method deletes the roles from the Database
	 * @param strDeleteRoleSeqID String which contains the user role id's which has to be deleted
	 * @return int, no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteRole(String strDeleteRoleSeqID) throws TTKException
	{
		securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.deleteRole(strDeleteRoleSeqID);
	}// End of deleteRole(String strDeleteRoleSeqID)

	/**
	 * This method returns the ArrayList, which contains the GroupVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of GroupVO object's which contains the user group Information
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchObjects) throws TTKException
	{
		securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.getGroupList(alSearchObjects);
	}// End of getGroupList(ArrayList alSearchObjects)
	
	/**
	 * This method returns the GroupVO, which contains the Group details which are populated from the database
	 * @param lngGroupBranchSeqID long value which contains the Group Branch Seq ID
	 * @return GroupVO which contains the user group Information
	 * @exception throws TTKException
	 */
	public GroupVO getGroup(long lngGroupBranchSeqID) throws TTKException
	{
		securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.getGroup(lngGroupBranchSeqID);
	}//end of getGroup(long lngGroupBranchSeqID)

	/**
	 * This method adds or updates the User Group details
	 * @param groupVO GroupVO object which contains the user groups details to be added/updated
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public long saveGroup(GroupVO groupVO) throws TTKException
	{
		securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.saveGroup(groupVO);
	}// End of addUpdateGroup(GroupVO groupVO)

	/**
	 * This method deletes the user groups from the Database
	 * @param strGroupBranchSeqID String which contains the GROUP_BRANCH_SEQ_ID'S which has to be deleted
	 * @return int, no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteGroup(String strGroupBranchSeqID) throws TTKException
	{
		securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.deleteGroup(strGroupBranchSeqID);
	}// End of deleteGroup(ArrayList alGroupList)
	
	/**
     * This method returns the UserSecurityProfile object which contains all the user details
     * if he is a valid User.
     * @param strCustomerCode Customer Code
     * @return UserSecurityProfile object which contains all the user details along with their role and profile information
     * @exception throws TTKException
     */
    public UserSecurityProfile ipruLogin(UserVO userVO) throws TTKException {
    	Document userProfileDoc=((LoginDAOImpl)this.getSecurityDAO("loginuser")).ipruLoginValidation(userVO);
    	return SecurityManagerHelper.getIpruSecurityProfile(userVO,userProfileDoc);
    }//end of ipruLogin(String strCustomerCode)

	//Changes Added for Password Policy CR KOC 1235
    /**
	 * This method update the user login ip address to null from the Database
	 * @param strUserID String which contains the User_ID'S which has to be updated
	 * @return String, successful execution of the task
	 * @exception throws TTKException
	 */
    public int userLoginIPAddress(String UserID) throws TTKException
    {
    	securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.userLoginIPAddress(UserID);
    }
    /**
	 * This method update the user login ip address to null from the Database
	 * @param strUserID String which contains the User_ID'S which has to be updated
	 * @return String, successful execution of the task
	 * @exception throws TTKException
	 */
    public String userLogout(UserVO userVO) throws TTKException
    {
    	securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.userLogout(userVO);
    }
    
    /**
	 * This method update the user login ip address to null from the Database
	 * @param strUserID String which contains the User_ID'S which has to be updated
	 * @return String, successful execution of the task
	 * @exception throws TTKException
	 */
    public int userSessionLogoutIPAddress(String SessionLogoutIPAddress) throws TTKException
    {
    	securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.userSessionLogoutIPAddress(SessionLogoutIPAddress);
    }
	//End changes for Password Policy CR KOC 1235
    //changes for  KOC 1349
    public int userLoginRandomNo(String UserID,String RandomNo) throws TTKException
    {
    	securityDAO = (SecurityDAOImpl)this.getSecurityDAO("security");
		return securityDAO.userLoginRandomNo(UserID,RandomNo);
    }
    

}//end of class SecurityManagerBean