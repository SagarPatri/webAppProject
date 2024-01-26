/**
 * @ (#) RolesManager.java 8th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : RolesManager.java
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
import java.util.Map;

import org.apache.struts.action.DynaActionForm;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.usermanagement.RoleVO; 
import javax.ejb.*;

@Local
public interface RolesManager{
	
	/**
	 * This method adds or updates the role details   
	 * @param roleVO RoleVO object which contains the role details to be added/updated
	 * @return int value, greater than zero indicates sucessfull execution of the task
	 * @exception throws TTKException
	 */
	public int addUpdateRole(RoleVO roleVO) throws TTKException;
	
	/**
	 * This method returns the ArrayList, which contains the RoleVO's which are populated from the database
	 * @param form DynaActionForm object which contains the search criteria
	 * @return ArrayList of RoleVO object's which contains information about the user
	 * @exception throws TTKException
	 */
	public ArrayList getRoles(DynaActionForm form) throws TTKException;
	
	/**
	 * This method delete's the specified role id's for the user
	 * @param strUserId String object which contains the user id
	 * @param strRoleId String object which contains the comma separated role id's to be deleted
	 * @return int value, greater than zero indicates sucessfull execution of the task 
	 * @exception throws TTKException
	 */
	public int deleteRoles(String strUserId, String strRoleId) throws TTKException;
	
	/**
	 * This method returns the Map object which contains all the role id's as key and the SecurityProfileXML object as their respective value's
	 * @return Map object, which contains all the role id's as key for their respective SecurityProfileXML object's
	 * @exception throws TTKException
	 */
	public Map getRolePrevileges() throws TTKException;
	
	/**
	 * This method updates the database with the information contained in the Map object which has the role id as key and the SecurityProfileXML object as its value
	 * @param mpUserRoles Map object, which contains all the role id's as key for the respective SecurityProfileXML object's
	 * @exception throws TTKException
	 */
	public void setRolePrevileges(Map mpUserRoles) throws TTKException;
	
	
}//end of class RolesManager