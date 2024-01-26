/**
 * @ (#) GroupRegistrationManager.java Jan 11, 2006
 * Project       : TTK HealthCare Services
 * File          : GroupRegistrationManager.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Jan 11, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.business.empanelment;

import java.util.ArrayList;

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.GroupRegistrationVO;
import com.ttk.dto.empanelment.NotificationInfoVO;

@Local
public interface GroupRegistrationManager {
	/**
	 * This method returns the ArrayList, which contains the GroupRegistrationVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	 * @return ArrayList of GroupRegistrationVO object's which contains the Group Registration Details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchObjects) throws TTKException;
	
	/**
	 * This method returns  the ArrayList of GroupRegistrationVO's which contains Branch Group Registration's Details
	 * @param lngParentGroupSeqID Parent Group Sequence ID
	 * @return alResultList of GroupRegistrationVO which contains the Branch Group Registration's Details
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getBranchList(Long lngParentGroupSeqID) throws TTKException;
	
	/**
	 * This method returns the Group Registration Details Information
	 * @param lngGroupRegSeqId the group registration sequence id for which the details is required
	 * @return GroupRegistrationVO object which contains the Details of the Group Reistration 
	 * @throws TTKException 
	 */
	public GroupRegistrationVO getGroup(Long lngGroupRegSeqId) throws TTKException;
	
	/**
	 * This method adds or updates the Group Registration details   
	 * @param groupRegistrationVO GroupRegistrationVO object which contains the Group Registration details to be added/updated
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int saveGroup(GroupRegistrationVO groupRegistrationVO) throws TTKException;
	
	/**
	 * This method deletes the group registration from the Database
	 * @param lngGroupRegSeqId Long the user Groups Registration Sequence ID which has to be deleted
	 * @return int, no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteGroup(Long lngGroupRegSeqId) throws TTKException;
	
	/**
	 * This method deletes the group registration from the Database
	 * @param lngGroupRegSeqId Long the user Groups Registration Sequence ID which has to be deleted
	 * @return int, no of rows effected
	 * @exception throws TTKException
	 */
	public ArrayList selectAccountManager(ArrayList alSearchObjects) throws TTKException;
	
	/** This method returns the NotificationInfoVO Which contains the NotificationInfoVO details 
	 * @param lngGroupRegSeqID for Group Registration Seq Id
	 * @return NotificationInfoVO which contains the NotificationInfo details
	 * @exception throws TTKException
	 */
	public NotificationInfoVO getNotificationList(Long lngGroupRegSeqID) throws TTKException;
	
	/**
	 * This method adds or updates the Notification Info details
	 * @param notifyDetailVO NotifyDetailVO object which contains the Group Registration details to be added/updated
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int saveNotificationInfo(NotificationInfoVO notificationInfoVO) throws TTKException;
}//End of GroupRegistrationManager
