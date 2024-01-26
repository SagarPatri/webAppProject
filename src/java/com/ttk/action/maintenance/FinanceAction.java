/**
 * @ (#) FinanceAction
 * Project       : TTK HealthCare Services
 * File          : FinanceAction.java
 * Author        : Navin Kumar R
 * Company       : Span Systems Corporation
 * Date Created  : 11th November,2009
 *
 * @author       : 
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.maintenance;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.finance.ChequeManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.ChequeVO;

import formdef.plugin.util.FormUtils;

public class FinanceAction  extends TTKAction{
	
	private static final Logger log = Logger.getLogger(EnrollmentAction.class );

	//Action mapping forwards.
	private static final String strFinmainchequeinfo="finmainchequeinfo";			
		
	/**
	 * This method is used to change the cheque information of a claim.
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
									HttpServletResponse response) throws TTKException
	{
		log.debug("Inside the doDefault mehtod of FinanceAction");
		try{
			setLinks(request);			
			DynaActionForm frmFinMaintance = (DynaActionForm)form;
			frmFinMaintance.initialize(mapping);
			return this.getForward(strFinmainchequeinfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strFinmainchequeinfo));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to save the changes to the finance.
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
									HttpServletResponse response) throws Exception
	{
		log.debug("Inside the doSave method of FinanceAction");
		try{						
			DynaActionForm frmFinMaintance = (DynaActionForm)form;
			ChequeVO chequeVO = null;
			ChequeManager chequeObject=this.getChequeManagerObject();
			int iResult;
			chequeVO = (ChequeVO)FormUtils.getFormValues(frmFinMaintance, this, mapping, request);
			chequeVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			iResult = chequeObject.updateChequeInfo(chequeVO); 
			if(iResult>0)
			{
				request.setAttribute("updated","message.saved");
				frmFinMaintance.initialize(mapping);
			}//end of if(iResult>0)
			return this.getForward(strFinmainchequeinfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strFinmainchequeinfo));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
     * Returns the ChequeManager session object for invoking methods on it.
     * @return ChequeManager session object which can be used for method invocation
     * @exception throws TTKException
     */
    private ChequeManager getChequeManagerObject() throws TTKException
    {
        ChequeManager chequeManager = null;
        try
        {
            if(chequeManager == null)
            {
                InitialContext ctx = new InitialContext();
                chequeManager = (ChequeManager) ctx.lookup("java:global/TTKServices/business.ejb3/ChequeManagerBean!com.ttk.business.finance.ChequeManager");
            }//end if(chequeManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strFinmainchequeinfo);
        }//end of catch
        return chequeManager;
    }//end of getChequeManagerObject()
}//end of FinanceAction
