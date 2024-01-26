/**
 * @ (#) WorkflowManager.java Dec 20, 2005
 * Project      : TTK HealthCare Services
 * File         : WorkflowManager
 * Author       : Balakrishna
 * Company      : Span Systems Corporation
 * Date Created : Dec 20, 2005
 *
 * @author       :  Balakrishna 
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.business.administration;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.EventVO;
import javax.ejb.*;
@Local

public interface WorkflowManager {
	
	/**
     * This method returns the ArrayList of Workflow object's which contains the workflow Details
     * @exception throws TTKException
     * @return ArrayList of Workflow object's which contains the workflow Details
     */
	public ArrayList getWorkflowList() throws TTKException;
	
	/**
     * This method returns the EventVO, which contains all the details about the event
     * @param lngEventSeqID the eventSequnceID for which the event Details has to be fetched
     * @return EventVO object which contains all the details about the Event
     * @exception throws TTKException 
     */
	public EventVO getWorkflowEvent(Long lngEventSeqID) throws TTKException;
	
	/**
     * This method updates the EventVO which contains the details of the Workflow Events
     * @param EventVO the details of Workflow Events which has to be updated
     * @return int value, greater than zero indicates sucessfull execution of the task
     * @exception throws TTKException
     */
	public int saveWorkflowEvent(EventVO eventVO) throws TTKException;

}//end of WorkflowManager
