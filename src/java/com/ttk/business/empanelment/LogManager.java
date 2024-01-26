/**
 * @ (#) LogManager.java Sep 15, 2005
 * Project      : 
 * File         : LogManager.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Sep 15, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.business.empanelment;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.empanelment.LogVO;
import javax.ejb.*;

@Local

public interface LogManager {
    
    /**
     * This method adds the log details   
     * The method also calls another method on DAO to insert the log details to the database 
     * @param logVO LogVO object which contains the log details to be added
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addLog(LogVO logVO) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the LogVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of LogVO object's which contains the log details
     * @exception throws TTKException
     */
    public ArrayList getLogList(ArrayList alSearchObjects) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the LogVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of LogVO'S object's which contains Log details 
     * @exception throws TTKException
     */
    public ArrayList getPolicyLogList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method adds the log details   
     * The method also calls another method on DAO to insert the log details to the database 
     * @param logVO LogVO object which contains the log details to be added
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addPolicyLog(LogVO logVO) throws TTKException;
    
    /**
     * This method returns the ArrayList, which contains the LogVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of LogVO'S object's which contains Pre-Authorization Log details 
     * @exception throws TTKException
     */
    public ArrayList getPreAuthLogList(ArrayList alSearchCriteria) throws TTKException;
    
    /**
     * This method adds the log details   
     * The method also calls another method on DAO to insert the Pre-Authorization Log details to the database 
     * @param logVO LogVO object which contains the Pre-Authorization Log details to be added
     * @param strIdentifier object which contains Identifier - Coding/Pre-Authorization/Claims
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addPreAuthLog(LogVO logVO,String strIdentifier) throws TTKException;
    
    public ArrayList getPartnerLogList(ArrayList alSearchObjects) throws TTKException;
    
    public int addPartnerLog(LogVO logVO) throws TTKException;

}//end of LogManager
