/**
 * @ (#) UserManagerBean.java Dec 27, 2005
 * Project 	     : TTK HealthCare Services
 * File          : UserManagerBean.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Dec 27, 2005
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.usermanagement;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.usermanagement.UserDAOImpl;
import com.ttk.dao.impl.usermanagement.UserManagementDAOFactory;
import com.ttk.dto.onlineforms.OnlineChangePasswordVO;
import com.ttk.dto.usermanagement.PasswordVO;
import com.ttk.dto.usermanagement.ContactVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;

import javax.ejb.*;

//Changes Added for Password Policy CR KOC 1235
import com.ttk.dto.usermanagement.PasswordValVO;
import com.ttk.dto.usermanagement.UserListVO;
//End changes for Password Policy CR KOC 1235
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class UserManagerBean implements UserManager{

	private UserManagementDAOFactory userManagementDAOFactory = null;
	private UserDAOImpl userDAO = null;

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getUserDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if(userManagementDAOFactory == null){
				userManagementDAOFactory = new UserManagementDAOFactory();
            }//end of if(userManagementDAOFactory == null)
			if(strIdentifier!=null)
			{
				return userManagementDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else{
				return null;
            }//end of else
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//end getUserDAO(String strIdentifier)

	/**
	 * This method returns the ArrayList, which contains the UserListVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @param strIdentifier for Identifying UserManagement/Finance Flow - In UserManagement empty string and in Finance Flow - Finance
	 * @return ArrayList of UserListVO object's which contains the user details
	 * @exception throws TTKException
	 */
	public ArrayList getUserList(ArrayList alSearchObjects,String strIdentifier) throws TTKException {
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.getUserList(alSearchObjects,strIdentifier);
	}//end of getUserList(ArrayList alSearchObjects,String strIdentifier)

	/**
	 * This method activate or inactivate the user records to the database.
	 * @param strContactSeqID String object which contains the conatct sequence id's to be activated or inactivated
	 * @param strIdentifier String object which contains the Active or Inactive flag to be activated or inactivated
	 * @return int value, greater than zero indicates successful execution of the task
	 * @exception throws TTKException
	 */
	public int setUserStatus(String strContactSeqID, String strIdentifier) throws TTKException {
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.setUserStatus(strContactSeqID,strIdentifier);
	}//end of setUserStatus(String strContactSeqID, String strIdentifier)

	/**
	 * This method returns the ArrayList, which contains the GroupVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of GroupVO object's which contains the user group Information
	 * @exception throws TTKException
	 */
	public ArrayList groupRegistrationUserList(ArrayList alSearchObjects) throws TTKException {
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.groupRegistrationUserList(alSearchObjects);
	}//end of groupRegistrationUserList(ArrayList alSearchObjects)

	/**
     * This method associate the User's to the group
     * @param strContactSeqID, String which contains the contact seq id's to be associated
     * @param lngGroupBranchSeqID long value which contains Group Branch Sequence ID
     * @param lngUserSeqID long value which contains User Sequence ID
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @throws TTKException
     */
    public int updateGroupRegistration(String strContactSeqID,long lngGroupBranchSeqID,long lngUserSeqID) throws TTKException {
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.updateGroupRegistration(strContactSeqID,lngGroupBranchSeqID,lngUserSeqID);
	}//end of updateGroupRegistration(String strContactSeqID,long lngGroupBranchSeqID,long lngUserSeqID)

	/**
	 * This method deletes the users from the  associated users list
	 * @param strGroupUserSeqID String which contains the user Group User seq id's which has to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int removeUsers(String strGroupUserSeqID) throws TTKException {
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.removeUsers(strGroupUserSeqID);
	}//end of removeUsers(String strGroupUserSeqID)

	/**
	 * This method returns the ContactVO which contains the details of UserContact
	 * @param lngContactSeqID the contact sequence if which the Contact information is to be fetched
	 * @return ContactVO which contains the information of UserContact
	 * @exception throws TTKException
	 */
	public ContactVO getContact(Long lngContactSeqID) throws TTKException
	{
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.getContact(lngContactSeqID);
	}// End of getContact(Long lngContactSeqID)
	
	/**
	 * This method returns the ContactVO which contains the details of UserContact
	 * @param lngContactSeqID the contact sequence if which the Contact information is to be fetched
	 * @return ContactVO which contains the information of UserContact
	 * @exception throws TTKException
	 */
	public ContactVO getProfessionalContact(Long lngContactSeqID) throws TTKException
	{
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.getProfessionalContact(lngContactSeqID);
	}// End of getContact(Long lngContactSeqID)

	/**
	 * This method updates the user Contact information
	 * @param contactVO the contactVO which contains the information to be added or updated
	 * @param strIdentifier String which contains the Identifier for Saving Contacts
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @throws TTKException
	 */
	public long saveContact(ContactVO contactVO,String strIdentifier) throws TTKException
	{
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return  userDAO.saveContact(contactVO,strIdentifier);
	}// End of saveContact(ContactVO contactVO,String strIdentifier)
	
	/**
	 * This method updates the professionals Contact information
	 * @param contactVO the contactVO which contains the information to be added or updated
	 * @param strIdentifier String which contains the Identifier for Saving Contacts
	 * @return long value, greater than zero indicates sucessfull execution of the task
	 * @throws TTKException
	 */
	public long saveProfessionals(ContactVO contactVO,String strIdentifier) throws TTKException
	{
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return  userDAO.saveProfessionals(contactVO,strIdentifier);
	}// End of saveContact(ContactVO contactVO,String strIdentifier)
	
	//denial process
	public long saveDenMail(Long lngContactSeqID,PersonalInfoVO personalInfoVO) throws TTKException
	{
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return  userDAO.saveDenMail(lngContactSeqID,personalInfoVO);
	}// End of saveContact(ContactVO contactVO,String strIdentifier)
	//denial process
	/**
     * This method saves the Personal Information
     * @param personalInfoVO PersonalInfoVO Object which contains Personal Details
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	/*public int savePersonalDetails(PersonalInfoVO personalInfoVO) throws TTKException {
		profileDAO = (ProfileDAOImpl)this.getUserDAO("profile");
		return profileDAO.savePersonalDetails(personalInfoVO);
	}//end of savePersonalDetails(PersonalInfoVO personalInfoVO)
	*/

	/**
     * This method saves the Password Information
     * @param passwordVO PasswordVO which contains Password Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changePassword(PasswordVO passwordVO) throws TTKException {
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.changePassword(passwordVO);
	}//end of changePassword(PasswordVO passwordVO)

	/**
	 * This method returns the ArrayList, which contains the AuthorisedVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of AuthorisedVO object's which contains the authorised signatory details
	 * @exception throws TTKException
	 */
	public ArrayList getSignatoryList(ArrayList alSearchObjects) throws TTKException {
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.getSignatoryList(alSearchObjects);
	}//end of getSignatoryList(ArrayList alSearchObjects)

    /**
     * This method deletes the requested information from the database
     * @param alDeleteList the arraylist of details of which has to be deleted
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int deleteSignatory(ArrayList alDeleteList) throws TTKException
    {
        userDAO = (UserDAOImpl)this.getUserDAO("user");
        return userDAO.deleteSignatory(alDeleteList);
    }

    /**
     * This method changes the password for the particular user-id.
     * @param lngContactSeqID the Long for which the password will be reseted.
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
    public int resetPassword(Long lngContactSeqID,long lngUserSeqID) throws TTKException
    {
    	userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.resetPassword(lngContactSeqID,lngUserSeqID);
    }
    
    /**
     * This method saves the Password Information
     * @param onlineChangePasswordVO OnlineChangePasswordVO which contains Password Information
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	public int changeOnlinePassword(OnlineChangePasswordVO onlineChangePasswordVO) throws TTKException {
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.changeOnlinePassword(onlineChangePasswordVO);
	}//end of changeOnlinePassword(OnlineChangePasswordVO onlineChangePasswordVO)
    
	//Changes Added for Password Policy CR KOC 1235 and mofdified as per 1257
	/**
	 * This method returns the UserListVO which contains the details of Password Information
	 * @param passwordValVO the contact sequence if which the Password Information is to be fetched
	 * @return passwordValVO which contains the information of Password Information
	 * @exception throws TTKException
	 */
	public PasswordValVO getPasswordConfigInfo(String identifier) throws TTKException
	{
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.getPasswordConfigInfo(identifier);
	}// End of getPasswordConfigInfo(String identifier)
	
	public ArrayList getStdIsd(Long hospSeqId,String strIdentifier) throws TTKException
	{
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.getStdIsd(hospSeqId,strIdentifier);
	}// End of getPasswordConfigInfo(String identifier)
	
	
	
	
	
	
	
	
	
	public ArrayList getStdIsd1(Long ptnrSeqId,String strIdentifier) throws TTKException
	{
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.getStdIsd1(ptnrSeqId,strIdentifier);
	}// End of getPasswordConfigInfo(String identifier)
	
	/**
     * This method saves the Password History Configuration information
     * @param savePasswordConfigInfo the object which contains the Password History Configuration Info which has to be  saved
     * @return int value, greater than zero indicates successful execution of the task
     * @exception throws TTKException
     */
    
	public int savePasswordConfigInfo(PasswordValVO passwordValVO) throws TTKException {
		userDAO = (UserDAOImpl)this.getUserDAO("user");
		return userDAO.savePasswordConfigInfo(passwordValVO);
	}//end of savePasswordConfigInfo(PasswordConfigInfo passwordConfigInfo)
    
	/**
     * This method changes the password for the particular user-id.
     * @param lngContactSeqID the Long for which the password will be reseted.
     * @return int the value which returns greater than zero for succcesssful execution of the task
     * @exception throws TTKException
     */
	 public int resetPasswordAll(long lngUserSeqID) throws TTKException
	    {
	    	userDAO = (UserDAOImpl)this.getUserDAO("user");
			return userDAO.resetPasswordAll(lngUserSeqID);
	    }
	//End changes for Password Policy CR KOC 1235
	 
		
}//end of UserManagerBean
