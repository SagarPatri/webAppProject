/**
 * @ (#) UserManager.java 8th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : UserManager.java
 * Author       : Nagaraj D V
 * Company      : Span Systems Corporation
 * Date Created : 8th Sep 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.usermanagement;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.OnlineChangePasswordVO;
import com.ttk.dto.usermanagement.PasswordVO;
import com.ttk.dto.usermanagement.ContactVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;

import javax.ejb.*;

//Changes Added for Password Policy CR KOC 1235
import com.ttk.dto.usermanagement.PasswordValVO;
import com.ttk.dto.usermanagement.UserListVO;
//End changes for Password Policy CR KOC 1235


@Local

public interface UserManager{

	/**
	 * This method adds or updates the user details
	 * The method also calls other methods to insert/update the user details on the LDAP server
	 * @param userVO UserVO object which contains the user details to be added/updated
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	//public int addUpdateUser(UserVO userVO) throws TTKException;

	/**
	 * This method returns the array list of RolesVO object's for the user
	 * @param strUserId String object which contains the user id
	 * @return ArrayList of RolesVO object's which contains information about the user role
	 * @exception throws TTKException
	 */
	//public ArrayList getUserRoles(String strUserId) throws TTKException;   //It will be replace to Security Manager

	/**
	 * This method associates the user with the specified role id
	 * @param strUserId String object which contains the user id
	 * @param strRoleId String object which contains the role id
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	//public int associateRole(String strUserId, String strRoleId) throws TTKException; //It will be replace to Security Manager

	/**
	 * This method removes the user from the specified role id
	 * @param strUserId String object which contains the user id
	 * @param strRoleId String object which contains the role id
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	//public int removeUserRole(String strUserId, String strRoleId) throws TTKException; //It will be replace to Security Manager

	/**
	 * This method returns the ArrayList, which contains the UserListVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @param strIdentifier for Identifying UserManagement/Finance Flow - In UserManagement empty string and in Finance Flow - Finance
	 * @return ArrayList of UserListVO object's which contains the user details
	 * @exception throws TTKException
	 */
	public ArrayList getUserList(ArrayList alSearchObjects,String strIdentifier) throws TTKException;

	/**
	 * This method activate or inactivate the user records to the database.
	 * @param strContactSeqID String object which contains the conatct sequence id's to be activated or inactivated
	 * @param strIdentifier String object which contains the Active or Inactive flag to be activated or inactivated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int setUserStatus(String strContactSeqID,String strIdentifier) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the GroupVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of GroupVO object's which contains the user group Information
	 * @exception throws TTKException
	 */
	public ArrayList groupRegistrationUserList(ArrayList alSearchObjects) throws TTKException;

	/**
	 * This method associate the User's to the group
	 * @param strContactSeqID, String which contains the contact seq id's to be associated
	 * @param lngGroupBranchSeqID long value which contains Group Branch Sequence ID
     * @param lngUserSeqID long value which contains User Sequence ID
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @throws TTKException
	 */
	public int updateGroupRegistration(String strContactSeqID,long lngGroupBranchSeqID,long lngUserSeqID) throws TTKException;

	/**
	 * This method deletes the users from the  associated users list
	 * @param strGroupUserSeqID String which contains the user Group User seq id's which has to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int removeUsers(String strGroupUserSeqID) throws TTKException;

	/**
	 * This method returns the ContactVO which contains the details of UserContact
	 * @param lngContactSeqID the contact sequence if which the Contact information is to be fetched
	 * @return ContactVO which contains the information of UserContact
	 * @exception throws TTKException
	 */
	public ContactVO getContact(Long lngContactSeqID) throws TTKException;
	
	/**
	 * This method returns the ContactVO which contains the details of UserContact
	 * @param lngContactSeqID the contact sequence if which the Contact information is to be fetched
	 * @return ContactVO which contains the information of UserContact
	 * @exception throws TTKException
	 */
	public ContactVO getProfessionalContact(Long lngContactSeqID) throws TTKException;

	/**
	 * This method updates the user Contact information
	 * @param contactVO the contactVO which contains the information to be added or updated
	 * @param strIdentifier String which contains the Identifier for Saving Contacts
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @throws TTKException
	 */
	public long saveContact(ContactVO contactVO,String strIdentifier) throws TTKException;
	
	/**
	 * This method updates the user Contact information
	 * @param contactVO the contactVO which contains the information to be added or updated
	 * @param strIdentifier String which contains the Identifier for Saving Contacts
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @throws TTKException
	 */
	public long saveProfessionals(ContactVO contactVO,String strIdentifier) throws TTKException;
	
	
	
	
	//denial process
	public long saveDenMail(Long lngContactSeqID,PersonalInfoVO personalInfoVO) throws TTKException;
	//denial process
	/**
     * This method saves the Personal Information
     * @param personalInfoVO PersonalInfoVO Object which contains Personal Details
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	//public int savePersonalDetails(PersonalInfoVO personalInfoVO) throws TTKException;

	/**
     * This method saves the Password Information
     * @param passwordVO PasswordVO which contains Password Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changePassword(PasswordVO passwordVO) throws TTKException;

	/**
	 * This method returns the ArrayList, which contains the AuthorisedVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of AuthorisedVO object's which contains the authorised signatory details
	 * @exception throws TTKException
	 */
	public ArrayList getSignatoryList(ArrayList alSearchObjects) throws TTKException;

    /**
     * This method deletes the requested information from the database
     * @param alDeleteList the arraylist of details of which has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteSignatory(ArrayList alDeleteList) throws TTKException;

    /**
     * This method changes the password for the particular user-id.
     * @param lngContactSeqID the Long for which the password will be reseted.
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int resetPassword(Long lngContactSeqID,long lngUserSeqID) throws TTKException;
    
    /**
     * This method saves the Password Information
     * @param onlineChangePasswordVO OnlineChangePasswordVO which contains Password Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changeOnlinePassword(OnlineChangePasswordVO onlineChangePasswordVO) throws TTKException;


	//Changes Added for Password Policy CR KOC 1235
	/**
     * This method saves the Password History Configuration information
     * @param passwordConfigInfo the object which contains the Password History Configuration Info which has to be  saved
     * @exception throws TTKException
     */
    public int savePasswordConfigInfo(PasswordValVO passwordValVO) throws TTKException;

     /**
     * This method returns the Password History Configuration information
     * @param passwordConfigInfo the object which contains the Password History Configuration Info which has to be  saved
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
     //Modified as per KOC 1257 11pp
    public PasswordValVO getPasswordConfigInfo(String identifier) throws TTKException;   
    
    //intx
    
    public ArrayList getStdIsd(Long hospSeqId,String strIdentifier) throws TTKException;   
    
    
    public ArrayList getStdIsd1(Long ptnrSeqId,String strIdentifier) throws TTKException;   
    /**
     * This method is used to reset the password for all TTK,CAll CENTER,DMC users.
     * @param long1 
     * @exception throws TTKException
     */
  	public int resetPasswordAll(long lngUserSeqID) throws TTKException;
	//End changes for Password Policy CR KOC 1235

}//end of class UserManager