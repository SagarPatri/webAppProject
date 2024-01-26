/**
 * @ (#) ChangeDoBoAction
 * Project       : TTK HealthCare Services
 * File          : ChangeDoBoAction.java
 * Author        : Navin Kumar R
 * Company       : Span Systems Corporation
 * Date Created  : 20th August,2009
 *
 * @author       : 
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.maintenance;

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
import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

/**
 * This class is used to change the policy DO/BO. 
 */
public class ChangeDoBoAction extends TTKAction{

	private static final Logger log = Logger.getLogger(ChangeDoBoAction.class);
	
    //Action mapping forwards.
	private static final String strViewChangeDOBO="viewChangeDOBO";
	private static final String strChangeOffice="changeoffice";
	
	//Exception Message Identifier
	private static final String strChangeDoBo="changeDoBo";
	
	/**
     * This method is used to change the DO/BO of a policy.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws TTKException if any error occurs
     */
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							   HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside the doDefault method of ChangeDoBoAction");
    		DynaActionForm frmChangeDOBO = (DynaActionForm)form;
    		frmChangeDOBO.initialize(mapping);//reset the form data
    		request.getSession().removeAttribute("frmChangePolicyPrd");
    		request.getSession().setAttribute("frmChangeDOBO",frmChangeDOBO);
    		return this.getForward(strViewChangeDOBO, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChangeDoBo));
    	}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
	 * This method is used to navigate to the next screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doChangeOffice method of ChangeDoBoAction");
			setLinks(request);
			return mapping.findForward(strChangeOffice);			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strChangeDoBo));
		}//end of catch(Exception exp)
	}//end of doChangeOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to save the changes to the policy.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of ChangeDoBoAction");
			ArrayList<Object> arUpdateList = new ArrayList<Object>();
			DynaActionForm frmChangeDOBO = (DynaActionForm)form;
			arUpdateList.add(TTKCommon.checkNull(frmChangeDOBO.get("prodPolicyNumber").toString()));
			Long lngSeqId = Long.valueOf(TTKCommon.checkNull(frmChangeDOBO.get("insuranceSeqID").toString()));
			arUpdateList.add(lngSeqId);
			MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();			
			int iUpdateCount=maintenanceManagerObject.updatePolicyDOBO(arUpdateList);
			if(iUpdateCount>0)
			{	
				request.setAttribute("updated","message.Maintenance.enrollment.policyDO/BO");
				frmChangeDOBO.initialize(mapping);//reset the form data
				request.getSession().setAttribute("frmChangeDOBO",frmChangeDOBO);
			}//end of if(iUpdateCount>0)			
			return this.getForward(strViewChangeDOBO, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strChangeDoBo));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the MaintenanceManager session object for invoking methods on it.
	 * @return MaintenanceManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MaintenanceManager getMaintenanceManagerObject() throws TTKException
	{
		MaintenanceManager maintenanceManager = null;
		try
		{
			if(maintenanceManager == null)
			{
				InitialContext ctx = new InitialContext();
				maintenanceManager = (MaintenanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/MaintenanceManagerBean!com.ttk.business.maintenance.MaintenanceManager");
			}//end if(maintenanceManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strChangeDoBo);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()
}//end of ChangeDoBoAction
