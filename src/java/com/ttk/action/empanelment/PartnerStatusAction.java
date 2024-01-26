/**
 * @ (#) PartnerStatusAction.java Sep 27, 2005
 * Project      : TTK HealthCare Services
 * File         : PartnerStatusAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Sep 27, 2005
 *
 * @author       :  Arun K N
 * Modified by   : Raghavendra T M
 * Modified date : March 10,2006
 * Reason        : 
 */

package com.ttk.action.empanelment;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.business.empanelment.PartnerManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.empanelment.StatusVO;

import formdef.plugin.util.FormUtils;

/**
 * This action class is used to insert/update Partner status information
 */

public class PartnerStatusAction extends TTKAction{
	
	private static Logger log = Logger.getLogger( PartnerStatusAction.class );
	
	// Action mapping forwards
	private static final String strPartnerStatus="Partnerstatus";
	
	//Exception Message Identifier
    private static final String strPartnerStatusError="PartnerStatus";
	
	/**
     * This method is used to initialize the search grid.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doDefault method of PartnerStatusAction");
    		if(TTKCommon.getWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.Partner.required");
				throw expTTK;
			}//end of if
    		//get the session bean from the bean pool for each excecuting thread
    		PartnerManager PartnerObject=this.getPartnerManagerObject();
			HashMap hmReasonInfo = null;
			ArrayList alReasonInfo = null;
    		StatusVO statusVO=null;
    		//clear the dynaform if visiting from left links for the first time
    		//else get the dynaform data from session
    		
    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
    			((DynaValidatorForm)form).initialize(mapping);//reset the form data
    		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")
    		    		
    		//call the business layer to get the Status
    		statusVO=PartnerObject.getStatus(TTKCommon.getWebBoardId(request));
    		//set the form bean
    		DynaActionForm  frmPartnerStatus = (DynaActionForm)FormUtils.setFormValues("frmPartnerStatus",statusVO, this, mapping, request);
    		hmReasonInfo=(HashMap)statusVO.getReasonInfo();
    		alReasonInfo=(ArrayList)hmReasonInfo.get(frmPartnerStatus.get("emplStatusTypeId"));
    		//setting the ReasonInfo into the request
    		request.getSession().setAttribute("alReasonInfo",alReasonInfo);
    		request.getSession().setAttribute("reasonInfo",hmReasonInfo);
    		request.getSession().setAttribute("frmPartnerStatus",frmPartnerStatus);
    		//finally return to the grid screen
    		return this.getForward(strPartnerStatus, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPartnerStatusError));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to get the details of the selected record from web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	log.debug("Inside doChangeWebBoard method of PartnerStatusAction");
    	//ChangeWebBoard method will call doDefault() method internally.
    	return doDefault(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to load the sub status based on the selected status.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doStatusChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doStatusChange method of PartnerStatusAction");
    		HashMap hmReasonInfo = null;
			ArrayList alReasonInfo = null;
    		StatusVO statusVO=new StatusVO();
			DynaValidatorForm frmStatus=(DynaValidatorForm)request.getSession().getAttribute("frmPartnerStatus");
			DynaActionForm  frmPartnerStatus = (DynaActionForm)FormUtils.setFormValues("frmPartnerStatus",statusVO, this, mapping, request);
			//set the form bean
			frmPartnerStatus.set("empanelSeqId",(String)frmStatus.get("empanelSeqId"));
			frmPartnerStatus.set("emplStatusTypeId",frmStatus.get("emplStatusTypeId"));
			frmPartnerStatus.set("fromDate","");
			frmPartnerStatus.set("toDate","");
            frmPartnerStatus.set("frmChanged","changed");
            frmPartnerStatus.set("gradeTypeId",(String)frmStatus.get("gradeTypeId"));
			hmReasonInfo=(HashMap)request.getSession().getAttribute("reasonInfo");
			alReasonInfo=(ArrayList)hmReasonInfo.get(frmStatus.get("emplStatusTypeId"));
			//setting the ReasonInfo into the request
			request.getSession().setAttribute("alReasonInfo",alReasonInfo);
			request.getSession().setAttribute("reasonInfo",hmReasonInfo);
			request.getSession().setAttribute("frmPartnerStatus",frmPartnerStatus);
            return this.getForward(strPartnerStatus,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPartnerStatusError));
		}//end of catch(Exception exp)
    }//end of doStatusChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doSave method of PartnerStatusAction");
    		PartnerManager PartnerObject=this.getPartnerManagerObject();
			HashMap hmReasonInfo = null;
			ArrayList alReasonInfo = null;
    		DynaValidatorForm frmStatus=(DynaValidatorForm)request.getSession().getAttribute("frmPartnerStatus");
			StatusVO statusVO= null;
			statusVO=(StatusVO)FormUtils.getFormValues(frmStatus, "frmPartnerStatus",this, mapping, request);
			statusVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User Id
			if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("EMP")){
				statusVO.setSubStatusCode("EMP");
			}//end of if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("EMP"))
			else{
				statusVO.setSubStatusCode((String)frmStatus.get("subStatusCode"));
			}//end of else
			if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("EMP"))
			{
				statusVO.setFromDate(TTKCommon.getUtilDate((String)frmStatus.get("fromDate")));
				statusVO.setToDate(TTKCommon.getUtilDate((String)frmStatus.get("toDate")));
			}//end of if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("EMP"))
			
			else if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("DIS"))
			{
				statusVO.setFromDate(TTKCommon.getUtilDate((String)frmStatus.get("fromDate")));
				statusVO.setToDate(null);
			}//end of else if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("DIS"))
			
			else if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("CLS")) 
			{
				statusVO.setFromDate(TTKCommon.getUtilDate((String)frmStatus.get("fromDate")));
				statusVO.setToDate(null);
			}//end of else if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("CLS"))
			else
			{
				statusVO.setFromDate(null);
				statusVO.setToDate(null);
			}//end of else
			
			//call the business method here for updating the status
			int result=PartnerObject.updateStatus(statusVO);
			
			//Set the appropriate message
			if(result!=0)
			{
				//requery to get the new status information
				statusVO=PartnerObject.getStatus(TTKCommon.getWebBoardId(request));
				request.setAttribute("updated","message.savedSuccessfully");
			}//end of if(result!=0)
			//set the form bean
			DynaActionForm frmPartnerStatus = (DynaActionForm)FormUtils.setFormValues("frmPartnerStatus",statusVO, this, mapping, request);
			hmReasonInfo=(HashMap)request.getSession().getAttribute("reasonInfo");
			alReasonInfo=(ArrayList)hmReasonInfo.get(frmStatus.get("emplStatusTypeId"));
			request.getSession().setAttribute("alReasonInfo",alReasonInfo);
			request.getSession().setAttribute("reasonInfo",hmReasonInfo);
			request.getSession().setAttribute("frmPartnerStatus",frmPartnerStatus); 
			Cache.refresh("providerCodeSearch");
            Cache.refresh("providerCode");
			return this.getForward(strPartnerStatus, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPartnerStatusError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	/**
	 * Returns the PartnerManager session object for invoking methods on it.
	 * @return PartnerManager session object which can be used for method invokation 
	 * @exception throws TTKException  
	 */
	private PartnerManager getPartnerManagerObject() throws TTKException
	{
		PartnerManager PartnerManager = null;
		try 
		{
			if(PartnerManager == null)
			{
				InitialContext ctx = new InitialContext();
				PartnerManager = (PartnerManager) ctx.lookup("java:global/TTKServices/business.ejb3/PartnerManagerBean!com.ttk.business.empanelment.PartnerManager");
			}//end if
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, strPartnerStatusError);
		}//end of catch
		return PartnerManager;
	}//end getPartnerManagerObject()
}//end of PartnerStatusAction
