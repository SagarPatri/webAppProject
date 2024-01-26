/**
 * @ (#) LogManagerBean.java Sep 20, 2005
 * Project      :
 * File         : LogManagerBean.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : Sep 20, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.business.empanelment;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.administration.WebConfigDAOImpl;
import com.ttk.dao.impl.empanelment.EmpanelmentDAOFactory;
import com.ttk.dao.impl.empanelment.LogDAOImpl;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.empanelment.LogVO;

import javax.ejb.*;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class LogManagerBean implements LogManager{

	private EmpanelmentDAOFactory empanelmentDAOFactory = null;
    private LogDAOImpl logDAO = null;

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
    }//end getEmpanelmentDAO(String strIdentifier)


    /**
     * This method adds the log details
     * The method also calls another method on DAO to insert the log details to the database
     * @param logVO LogVO object which contains the log details to be added
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addLog(LogVO logVO) throws TTKException
    {
 	  	logDAO = (LogDAOImpl)this.getEmpanelmentDAO("log");
 	  	return logDAO.addLog(logVO);
    }//end of addLog(LogVO logVO)

    /**
     * This method returns the ArrayList, which contains the LogVO's which are populated from the database
     * @param alSearchObjects ArrayList object which contains the search criteria
     * @return ArrayList of LogVO object's which contains the log details
     * @exception throws TTKException
     */
    public ArrayList getLogList(ArrayList alSearchObjects) throws TTKException
    {
    	logDAO = (LogDAOImpl)this.getEmpanelmentDAO("log");
    	return logDAO.getLogList(alSearchObjects);
    }//end of getLogList(ArrayList alSearchObjects)

    /**
     * This method returns the ArrayList, which contains the LogVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of LogVO'S object's which contains Log details
     * @exception throws TTKException
     */
    public ArrayList getPolicyLogList(ArrayList alSearchCriteria) throws TTKException{
        logDAO = (LogDAOImpl)this.getEmpanelmentDAO("log");
        return logDAO.getPolicyLogList(alSearchCriteria);
    }//end of getPolicyLogList(ArrayList alSearchCriteria)

    /**
     * This method adds the log details
     * The method also calls another method on DAO to insert the log details to the database
     * @param logVO LogVO object which contains the log details to be added
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addPolicyLog(LogVO logVO) throws TTKException {
        logDAO = (LogDAOImpl)this.getEmpanelmentDAO("log");
        return logDAO.addPolicyLog(logVO);
    }//end of addPolicyLog(LogVO logVO)

    /**
     * This method returns the ArrayList, which contains the LogVO's which are populated from the database
     * @param alSearchCriteria ArrayList object which contains the search criteria
     * @return ArrayList of LogVO'S object's which contains Pre-Authorization Log details
     * @exception throws TTKException
     */
    public ArrayList getPreAuthLogList(ArrayList alSearchCriteria) throws TTKException {
        logDAO = (LogDAOImpl)this.getEmpanelmentDAO("log");
        return logDAO.getPreAuthLogList(alSearchCriteria);
    }//end of getPreAuthLogList(ArrayList alSearchCriteria)

    /**
     * This method adds the log details   
     * The method also calls another method on DAO to insert the Pre-Authorization Log details to the database 
     * @param logVO LogVO object which contains the Pre-Authorization Log details to be added
     * @param strIdentifier object which contains Identifier - Coding/Pre-Authorization/Claims
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
    public int addPreAuthLog(LogVO logVO,String strIdentifier) throws TTKException {
        logDAO = (LogDAOImpl)this.getEmpanelmentDAO("log");
        return logDAO.addPreAuthLog(logVO,strIdentifier);
    }//end of addPreAuthLog(LogVO logVO,String strIdentifier)
    
    
    public ArrayList getPartnerLogList(ArrayList alSearchObjects) throws TTKException
    {
    	logDAO = (LogDAOImpl)this.getEmpanelmentDAO("log");
    	return logDAO.getPartnerLogList(alSearchObjects);
    }//end of getLogList(ArrayList alSearchObjects)
    
    public int addPartnerLog(LogVO logVO) throws TTKException
    {
 	  	logDAO = (LogDAOImpl)this.getEmpanelmentDAO("log");
 	  	return logDAO.addPartnerLog(logVO);
    }//end of addLog(LogVO logVO)
    
    
}//end of LogManagerBean
