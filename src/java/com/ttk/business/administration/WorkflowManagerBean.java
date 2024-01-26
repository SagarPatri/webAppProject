/**
 * @ (#) WorkflowManager.java Dec 22, 2005
 * Project      : TTK HealthCare Services
 * File         : WorkflowManager
 * Author       : Balakrishna
 * Company      : Span Systems Corporation
 * Date Created : Dec 22, 2005
 *
 * @author       :  Balakrishna
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.business.administration;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.impl.administration.AdministrationDAOFactory;
import com.ttk.dao.impl.administration.WorkflowDAOImpl;
import com.ttk.dto.administration.EventVO;
import javax.ejb.*;
@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class WorkflowManagerBean implements WorkflowManager {

	private AdministrationDAOFactory administrationDAOFactory = null;
	private WorkflowDAOImpl workflowDAO=null;

	 /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */

	private BaseDAO getAdministrationDAO(String strIdentifier) throws TTKException
    {
        try
        {
            if(administrationDAOFactory == null)
                administrationDAOFactory = new AdministrationDAOFactory();
            if(strIdentifier!=null)
            {
              return administrationDAOFactory.getDAO(strIdentifier);
            }//end of if(strIdentifier!=null)
            else
            	return null;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error."+strIdentifier+".dao");
        }//end of catch(Exception exp)
    }//end of getAdministrationDAO(String strIdentifier)

	/**
     * This method returns the ArrayList
     * @exception throws TTKException
     * @return ArrayList of Workflow object's which contains the workflow Details
     */
	public ArrayList getWorkflowList() throws TTKException {
		workflowDAO = (WorkflowDAOImpl)this.getAdministrationDAO("workflow");
        return workflowDAO.getWorkflowList();
	}//end of getWorkflowList()

	/**
     * This method returns the EventVO, which contains all the details about the event
     * @param lngEventSeqID the eventSequnceID for which the event Details has to be fetched
     * @return EventVO object which contains all the details about the Event
     * @exception throws TTKException
     */
	public EventVO getWorkflowEvent(Long lngEventSeqID) throws TTKException {
		workflowDAO = (WorkflowDAOImpl)this.getAdministrationDAO("workflow");
        return workflowDAO.getWorkflowEvent(lngEventSeqID);
	}//end of getWorkflowEvent(Long lngEventSeqID)

	/**
     * This method updates the EventVO which contains the details of the Workflow Events
     * @param EventVO the details of Workflow Events which has to be updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
	public int saveWorkflowEvent(EventVO eventVO) throws TTKException {
		workflowDAO = (WorkflowDAOImpl)this.getAdministrationDAO("workflow");
	    return workflowDAO.saveWorkflowEvent(eventVO);
	}//end of saveWorkflowEvent(EventVO eventVO)

}//end of WorkflowManagerBean
