/**
 * @ (#) ClmReqAmtChangesAction.java
 * Project       : TTK HealthCare Services
 * File          : ClmReqAmtChangesAction.java
 * Author        : Balakrishna Erram
 * Company       : Span Systems Corporation
 * Date Created  : 9th August,2010
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
import com.ttk.dto.maintenance.ClaimListVO;


import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching policy and changing its sub type 
 */
public class ClmReqAmtChngDtlsAction extends TTKAction{
		
		 private static final Logger log = Logger.getLogger( ClmReqAmtChngDtlsAction.class );
	     //	  Action mapping forwards.
		 //private static final String strClaimsList="claimslist";
		 private static final String strClaimsDetails="claimsdetails";
		 private static final String strClaimsReqScreen="claimsreqscreen";		 
		 
		 //	  Exception Message Identifier
		 private static final String strMaintenanceClaims="maintenanceclaims";
		   
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
	    public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    							   HttpServletResponse response) throws TTKException{
	    	try{
	    		setLinks(request);
	    		DynaActionForm frmClaimReqAmtDetails=(DynaActionForm)form;
	    		log.info("Inside ClmReqAmtChngDtlsAction doSave");
	    		MaintenanceManager maintenanceManager = this.getMaintenanceManagerObject();
	    		ArrayList<Object> alNewClaimAmt = new ArrayList<Object>();
	    		ClaimListVO claimListVO=(ClaimListVO)FormUtils.getFormValues(frmClaimReqAmtDetails, "frmClaimReqAmtDetails",
																					this, mapping, request);
	    		alNewClaimAmt.add(claimListVO.getClaimSeqID());
	    		alNewClaimAmt.add(claimListVO.getNewClmReqAmt());
	    		alNewClaimAmt.add(claimListVO.getRemarks());
	    		alNewClaimAmt.add(TTKCommon.getUserSeqId(request));
	    		claimListVO.setAddedBy(TTKCommon.getUserSeqId(request));
	    		
	    		int intResult = maintenanceManager.saveClaimReqAmt(claimListVO);
	    		log.info("intResult : +"+intResult );
	    		if(intResult > 0)
	    		{   
	    			request.setAttribute("updated","message.savedSuccessfully");
	    			request.setAttribute("flag", "disable");
	    		}//end of if(intResult > 0)
	    		
	    		frmClaimReqAmtDetails = (DynaActionForm)FormUtils.setFormValues("frmClaimReqAmtDetails",
						claimListVO, this, mapping, request);
	    		request.getSession().setAttribute("frmClaimReqAmtDetails",frmClaimReqAmtDetails);
	    		return this.getForward(strClaimsDetails, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processExceptions(request, mapping, expTTK);
	    	}//end of catch(TTKException expTTK)
	    	catch(Exception exp)
	    	{
	    		return this.processExceptions(request, mapping, new TTKException(exp, strMaintenanceClaims));
	    	}//end of catch(Exception exp)
	    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
	    /**
		 * This method is used to Reset the values.
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
																		HttpServletResponse response) throws Exception {
			try
			{
				setLinks(request);
				return mapping.findForward(strClaimsDetails);
			}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processExceptions(request, mapping, expTTK);
	    	}//end of catch(TTKException expTTK)
	    	catch(Exception exp)
	    	{
	    		return this.processExceptions(request, mapping, new TTKException(exp, strMaintenanceClaims));
	    	}//end of catch(Exception exp)
	    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		
		
	    /**
		 * This method is used to Close the existing screen and goes back to the previous screen.
		 * Finally it forwards to the appropriate view based on the specified forward mappings
		 *
		 * @param mapping The ActionMapping used to select this instance
		 * @param form The optional ActionForm bean for this request (if any)
		 * @param request The HTTP request we are processing
		 * @param response The HTTP response we are creating
		 * @return ActionForward Where the control will be forwarded, after this request is processed
		 * @throws Exception if any error occurs
		 */
		public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception {
			try
			{
				setLinks(request);
				log.info("Inside ClmReqAmtChngDtlsAction doClose");
				return this.getForward(strClaimsReqScreen, mapping, request);
			}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processExceptions(request, mapping, expTTK);
	    	}//end of catch(TTKException expTTK)
	    	catch(Exception exp)
	    	{
	    		return this.processExceptions(request, mapping, new TTKException(exp, strMaintenanceClaims));
	    	}//end of catch(Exception exp)
		}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
					//HttpServletResponse response)
		
		
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
				throw new TTKException(exp, strMaintenanceClaims);
			}//end of catch
			return maintenanceManager;
		}//end getMaintenanceManagerObject()
}//end of ClmReqAmtChngDtlsAction