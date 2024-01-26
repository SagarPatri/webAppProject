/**
 * @ (#) WorkflowAction.java Dec 20, 2005
 * Project      : TTK HealthCare Services
 * File         : WorkflowAction.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : Dec 20, 2005
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.administration;


import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.WorkflowManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.security.RoleVO;

import formdef.plugin.util.FormUtils;

/**
* This class is used for displaying the List of Workflow Events.
* This also provides  updation of Events.
*/
public class WorkflowAction extends TTKAction
{
	
	private static Logger log = Logger.getLogger( WorkflowAction.class );
	
	//Forward Paths
	private static final String strDefault="workflowlist";
	private static final String strEventDetails="eventdetails";
	private static final String strUpdateEventDetails="saveevent";
	
	//Exception Message Identifier
	private static final String strWorkflowExp="workflow";
	
	/**
	 * This method is used to get the list in the screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDefault method of WorkflowAction");
			setLinks(request);
			WorkflowManager workflowManagerObject=this.getWorkflowManagerObject();
			WorkflowVO workflowVO =new WorkflowVO();
			ArrayList alWorkflowList = null;
			alWorkflowList = workflowManagerObject.getWorkflowList();
			DynaActionForm frmWorkflow = (DynaActionForm)FormUtils.setFormValues("frmWorkflow",
														   workflowVO, this, mapping, request);
			frmWorkflow.set("alWorkflowList",alWorkflowList);
			request.setAttribute("frmWorkflow",frmWorkflow);
			return this.getForward(strDefault, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWorkflowExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to detail screen to view selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewWorkflow(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewWorkflow method of WorkflowAction");
			setLinks(request);
			WorkflowManager workflowManagerObject=this.getWorkflowManagerObject();
			EventVO eventVO=null;
			ArrayList associatedRoles = new ArrayList();
			ArrayList unAssociatedRoles = new ArrayList();
			eventVO = new EventVO();
			DynaActionForm frmGeneral = (DynaActionForm)form;
			frmGeneral.set("caption",request.getParameter("caption"));
			frmGeneral.set("eventSeqID",request.getParameter("eventSeqID"));
			eventVO = workflowManagerObject.getWorkflowEvent(TTKCommon.getLong(
										(String)frmGeneral.get("eventSeqID")));
			DynaActionForm frmEvent = (DynaActionForm)FormUtils.setFormValues("frmEvent",
														eventVO, this, mapping, request);
			frmEvent.set("caption",frmGeneral.get("caption"));
			if(frmGeneral.getString("caption").equals("Preauth Workflow") || frmGeneral.
										 getString("caption").equals("Claims Workflow"))
			{
				frmEvent.set("workflowName","display");
			}//end of if(frmGeneral.getString("caption").equals("Preauth Workflow") || frmGeneral.getString("caption").equals("Claims Workflow"))
			else
			{
				frmEvent.set("workflowName","");
			}//end of else
			associatedRoles = eventVO.getRole();
			unAssociatedRoles =	eventVO.getUnAssociatedRole();
			//set the ArrayList data to new instance when listofroles fetch's null value
			if(unAssociatedRoles==null)
			{
				unAssociatedRoles=new ArrayList();
			}//end of if(unAssociatedRoles==null)
			if(associatedRoles==null)
			{
				associatedRoles=new ArrayList();
			}//end of if(associatedRoles==null)
			frmEvent.set("associatedRoles",associatedRoles);
			frmEvent.set("unAssociatedRoles",unAssociatedRoles);
			request.setAttribute("frmEvent",frmEvent);
			return this.getForward(strEventDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWorkflowExp));
		}//end of catch(Exception exp)
	}//end of doViewWorkflow(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to add/update the record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of WorkflowAction");
			setLinks(request);
			WorkflowManager workflowManagerObject=this.getWorkflowManagerObject();
			EventVO eventVO=null;
			DynaActionForm generalForm=(DynaActionForm)form;
			eventVO=new EventVO();
			eventVO=(EventVO)FormUtils.getFormValues(generalForm, "frmEvent",this, mapping, request);
			//fetching roleID's from form to String array
			String strAssociatedRoles[] =(String[])generalForm.get("selectedRoles");
			log.debug("ASSOCIATED ROLES         "+strAssociatedRoles.length);
			if(strAssociatedRoles != null)
				for(int i=0; i < strAssociatedRoles.length; i++)
			generalForm.set("selectedRoles",strAssociatedRoles);
			eventVO.setEventSeqID(TTKCommon.getLong((String)generalForm.get("eventSeqID")));
			eventVO.setRole(populateRoleObjects(strAssociatedRoles));
			// User ID from session
			eventVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = workflowManagerObject.saveWorkflowEvent(eventVO);
			if(iResult!=0) {
				request.setAttribute("updated","message.saved");
				DynaActionForm frmgeneral = (DynaActionForm)form;
				eventVO = workflowManagerObject.getWorkflowEvent(TTKCommon.getLong(
											(String)frmgeneral.get("eventSeqID")));
				DynaActionForm frmEvent = (DynaActionForm)FormUtils.setFormValues("frmEvent",
															eventVO, this, mapping, request);
				ArrayList associatedRoles = eventVO.getRole();
				ArrayList unAssociatedRoles = eventVO.getUnAssociatedRole();
				associatedRoles = eventVO.getRole();
				unAssociatedRoles =	eventVO.getUnAssociatedRole();
				//set the ArrayList data to new instance when listofroles fetch's null value
				if(unAssociatedRoles==null)
				{
					unAssociatedRoles=new ArrayList();
				}//end of if(unAssociatedRoles==null)
				if(associatedRoles==null)
				{
					associatedRoles=new ArrayList();
				}//end of if(associatedRoles==null)
				frmEvent.set("caption",frmgeneral.get("caption"));
				if(frmgeneral.getString("caption").equals("Preauth Workflow") || frmgeneral.
												getString("caption").equals("Claims Workflow"))
				{
					frmEvent.set("workflowName","display");
				}//end of if(frmgeneral.getString("caption").equals("Preauth Workflow") || frmgeneral.getString("caption").equals("Claims Workflow"))
				else
				{
					frmEvent.set("workflowName","");
				}//end of else
				frmEvent.set("associatedRoles",associatedRoles);
				frmEvent.set("unAssociatedRoles",unAssociatedRoles);
				request.setAttribute("frmEvent",frmEvent);
			}//end of if(iResult!=0)
			return this.getForward(strUpdateEventDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWorkflowExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception{
		log.debug("Inside the doReset method of WorkflowAction");
		setLinks(request);
		return doViewWorkflow(mapping,form,request,response);
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the ArrayList roleVO object for invoking methods on it.
	 * @return ArrayList roleVO  object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ArrayList populateRoleObjects(String strAssociatedRoles[]) throws TTKException
	{
		ArrayList<Object> alAssociatedRoles = new ArrayList<Object>();
		try
		{
			for(int i=0; i<strAssociatedRoles.length; i++)
			{
				RoleVO roleVO = new RoleVO();
				roleVO.setRoleSeqID(TTKCommon.getLong(strAssociatedRoles[i]));
				alAssociatedRoles.add(roleVO);
			}//end of for 
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "workflow");
		}//end of catch
		return (alAssociatedRoles);
	}//end of populateRoleObjects(String strAssociatedRoles[])
	
	/**
	 * Returns the WorkflowManager session object for invoking methods on it.
	 * @return WorkflowManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private WorkflowManager getWorkflowManagerObject() throws TTKException
	{
		WorkflowManager workflowManager = null;
		try
		{
			if(workflowManager == null)
			{
				InitialContext ctx = new InitialContext();
				workflowManager = (WorkflowManager) ctx.lookup("java:global/TTKServices/business.ejb3/WorkflowManagerBean!com.ttk.business.administration.WorkflowManager");
			}//end of if(WorkflowManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "workflow");
		}//end of catch
		return workflowManager;
	}//end getWorkflowManagerObject()
	
}// end of WorkflowAction class
