/**
 * @ (#) ChangePolicyPrdAction
 * Project       : TTK HealthCare Services
 * File          : ChangePolicyPrdAction.java
 * Author        : Navin Kumar R
 * Company       : Span Systems Corporation
 * Date Created  : 21st August,2009
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
 * This class is used to change the policy period. 
 */
public class ChangePolicyPrdAction extends TTKAction{

	private static final Logger log = Logger.getLogger(ChangePolicyPrdAction.class);
	
    //Action mapping forwards.
	private static final String strViewChangePolicyPrd="viewChangePolicyPrd";
	private static final String strChangeOffice="changeoffice";
	
	//Exception Message Identifier
	private static final String strChangePolicyPrd="changePolicyPrd";
	
	/**
     * This method is used to change the period of a policy.
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
    		log.debug("Inside the doDefault method of ChangePolicyPrdAction");
    		DynaActionForm frmChangePolicyPrd = (DynaActionForm)form;
    		frmChangePolicyPrd.initialize(mapping);//reset the form data
    		request.getSession().removeAttribute("frmChangeDOBO");
    		request.getSession().setAttribute("frmChangePolicyPrd",frmChangePolicyPrd);
    		return this.getForward(strViewChangePolicyPrd, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChangePolicyPrd));
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
			log.debug("Inside the doChangeOffice method of ChangePolicyPrdAction");
			setLinks(request);
			return mapping.findForward(strChangeOffice);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strChangePolicyPrd));
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
			log.debug("Inside the doSave method of ChangePolicyPrdAction");
			ArrayList<Object> arUpdateList = new ArrayList<Object>();
			DynaActionForm frmChangePolicyPrd = (DynaActionForm)form;
			arUpdateList.add(TTKCommon.checkNull(frmChangePolicyPrd.get("prodPolicyNumber").toString()));
			Long lngSeqId = Long.valueOf(TTKCommon.checkNull(frmChangePolicyPrd.get("insuranceSeqID").toString()));
			arUpdateList.add(lngSeqId);
			arUpdateList.add(TTKCommon.checkNull(frmChangePolicyPrd.get("startDate").toString()));
			arUpdateList.add(TTKCommon.checkNull(frmChangePolicyPrd.get("endDate").toString()));
			MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();			
			int iUpdateCount=maintenanceManagerObject.updatePolicyPeriod(arUpdateList);
			if(iUpdateCount>0)
			{	
				request.setAttribute("updated","message.Maintenance.enrollment.policyperiod");
				frmChangePolicyPrd.initialize(mapping);//reset the form data
				request.getSession().setAttribute("frmChangePolicyPrd",frmChangePolicyPrd);
			}//end of if(iUpdateCount>0)			
			return this.getForward(strViewChangePolicyPrd, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strChangePolicyPrd));
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
			throw new TTKException(exp, strChangePolicyPrd);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()
}//end of ChangePolicyPrdAction