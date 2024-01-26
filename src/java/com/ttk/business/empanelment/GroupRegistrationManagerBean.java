/**
 * @ (#) GroupRegistrationManagerBean.java Jan 11, 2006
 * Project       : TTK HealthCare Services
 * File          : GroupRegistrationManagerBean.java
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

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.empanelment.EmpanelmentDAOFactory;
import com.ttk.dao.impl.empanelment.GroupRegistrationDAOImpl;
import com.ttk.dto.empanelment.GroupRegistrationVO;
import com.ttk.dto.empanelment.NotificationInfoVO;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class GroupRegistrationManagerBean implements GroupRegistrationManager {

	private EmpanelmentDAOFactory empanelmentDAOFactory = null;
	private GroupRegistrationDAOImpl groupRegistrationDAO = null;

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	private BaseDAO getEmpanelmentDAO(String strIdentifier) throws TTKException
	{
		try
		{
			if(empanelmentDAOFactory == null)
				empanelmentDAOFactory = new EmpanelmentDAOFactory();
			if(strIdentifier!=null)
			{
				return empanelmentDAOFactory.getDAO(strIdentifier);
			}//end of if(strIdentifier!=null)
			else
				return null;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error."+strIdentifier+".dao");
		}//end of catch(Exception exp)
	}//end of getEmpanelmentDAO(String strIdentifier)

	/**
	 * This method returns the ArrayList, which contains the GroupRegistrationVO's which are populated from the database
	 * @param alSearchObjects ArrayList object which contains the search criteria
	  * @return ArrayList of GroupRegistrationVO object's which contains the Group Registration Details
	 * @exception throws TTKException
	 */
	public ArrayList getGroupList(ArrayList alSearchObjects) throws TTKException {
		groupRegistrationDAO = (GroupRegistrationDAOImpl)this.getEmpanelmentDAO("groupregistration");
		return groupRegistrationDAO.getGroupList(alSearchObjects);
	}//End of getGroupList(ArrayList alSearchObjects)

	/**
	 * This method returns  the ArrayList of GroupRegistrationVO's which contains Branch Group Registration's Details
	 * @param lngParentGroupSeqID Parent Group Sequence ID
	 * @return alResultList of GroupRegistrationVO which contains the Branch Group Registration's Details
	 * @exception throws TTKException
	 */
	public ArrayList<Object> getBranchList(Long lngParentGroupSeqID) throws TTKException {
		groupRegistrationDAO = (GroupRegistrationDAOImpl)this.getEmpanelmentDAO("groupregistration");
		return groupRegistrationDAO.getBranchList(lngParentGroupSeqID);
	}//End of getBranchList(Long lngParentGroupSeqID)

	/**
	 * This method returns the Group Registration Details Information
	 * @param lngGroupRegSeqId the group registration sequence id for which the details is required
	 * @return GroupRegistrationVO object which contains the Details of the Group Reistration
	 * @throws TTKException
	 */
	public GroupRegistrationVO getGroup(Long lngGroupRegSeqId) throws TTKException {
		groupRegistrationDAO = (GroupRegistrationDAOImpl)this.getEmpanelmentDAO("groupregistration");
		return groupRegistrationDAO.getGroup(lngGroupRegSeqId);
	}//End of getGroup(Long lngGroupRegSeqId)

	/**
	 * This method adds or updates the Group Registration details
	 * @param groupRegistrationVO GroupRegistrationVO object which contains the Group Registration details to be added/updated
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int saveGroup(GroupRegistrationVO groupRegistrationVO) throws TTKException {
		groupRegistrationDAO = (GroupRegistrationDAOImpl)this.getEmpanelmentDAO("groupregistration");
		return groupRegistrationDAO.saveGroup(groupRegistrationVO);
	}// End of saveGroup(GroupRegistrationVO groupRegistrationVO)

	/**
	 * This method deletes the group registration from the Database
	 * @param lngGroupRegSeqId Long the user Groups Registration Sequence ID which has to be deleted
	 * @return int, no of rows effected
	 * @exception throws TTKException
	 */
	public int deleteGroup(Long lngGroupRegSeqId) throws TTKException {
		groupRegistrationDAO = (GroupRegistrationDAOImpl)this.getEmpanelmentDAO("groupregistration");
		return groupRegistrationDAO.deleteGroup(lngGroupRegSeqId);
	}// End of  deleteGroup(Long lngGroupRegSeqId)

	/**
	 * This method returns  the ArrayList of GroupRegistrationVO's which contains Branch Group Registration's Details
	 * @param lngParentGroupSeqID Parent Group Sequence ID
	 * @return alResultList of GroupRegistrationVO which contains the Branch Group Registration's Details
	 * @exception throws TTKException
	 */
	public ArrayList selectAccountManager(ArrayList alSearchObjects) throws TTKException {
		groupRegistrationDAO = (GroupRegistrationDAOImpl)this.getEmpanelmentDAO("groupregistration");
		return groupRegistrationDAO.selectAccountManager(alSearchObjects);
	}//End of getBranchList(Long lngParentGroupSeqID)
	
	/** This method returns the NotificationInfoVO Which contains the NotificationInfoVO details 
	 * @param lngGroupRegSeqID for Group Registration Seq Id
	 * @return NotificationInfoVO which contains the NotificationInfo details
	 * @exception throws TTKException
	 */
	public NotificationInfoVO getNotificationList(Long lngGroupRegSeqID) throws TTKException {
		groupRegistrationDAO = (GroupRegistrationDAOImpl)this.getEmpanelmentDAO("groupregistration");
		return groupRegistrationDAO.getNotificationList(lngGroupRegSeqID);
	}//end of getNotifInfoList(Long lngGroupRegSeqID)
	
	/**
	 * This method adds or updates the Notification Info details
	 * @param notifyDetailVO NotifyDetailVO object which contains the Group Registration details to be added/updated
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int saveNotificationInfo(NotificationInfoVO notificationInfoVO) throws TTKException {
		groupRegistrationDAO = (GroupRegistrationDAOImpl)this.getEmpanelmentDAO("groupregistration");
		return groupRegistrationDAO.saveNotificationInfo(notificationInfoVO);
	}//end of saveNotificationInfo(NotificationInfoVO notificationInfoVO)
}//End of GroupRegistrationManagerBean
