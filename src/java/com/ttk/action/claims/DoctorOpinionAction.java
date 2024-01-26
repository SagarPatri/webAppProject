/**
* @ (#) DoctorOpinionAction.java October 22nd, 2007
* Project       : TTK HealthCare Services
* File          : DoctorOpinionAction.java
* Author        : Krupa J
* Company       : Span Systems Corporation
* Date Created  : October 22nd, 2007

* @author       : Krupa J
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.claims;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

/**
 * This class is used for save the Doctor's Opinion
 */
public class DoctorOpinionAction extends TTKAction
{
	private static Logger log = Logger.getLogger(DoctorOpinionAction.class);
	
	//Action mapping forwards.
	private static final  String strDocOpinion="docopinion";
	private static final String strMedicalDetails ="medicaldetails";
	
	 //Exception Message Identifier
    private static final String strDocOpinionError="DoctorOpinion";
    
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
    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doView method of DoctorOpinionAction");
    		setLinks(request);
    		DynaActionForm frmDocOpinion =(DynaActionForm)form;
    		StringBuffer sbfCaption= new StringBuffer();
    		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
    		String strDoctorOpinion =preAuthObject.getDoctorOpinion(ClaimsWebBoardHelper.getClaimsSeqId(request));
    		sbfCaption.append("[ ").append(ClaimsWebBoardHelper.getClaimantName(request)).append(" ] [ ").
     		append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
    		if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
    		{
    			sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
    		}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
    		frmDocOpinion.set("docOpinion",strDoctorOpinion);
    		frmDocOpinion.set("caption",sbfCaption.toString());
    		return this.getForward(strDocOpinion, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDocOpinionError));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
    		log.debug("Inside the doDefault method of ClaimsAction");
    		setLinks(request);
    		DynaActionForm frmDocOpinion =(DynaActionForm)form;
    		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
    		
    		int iCount=0;
    		String strDoctorOpinion =frmDocOpinion.getString("docOpinion");
    		iCount=preAuthObject.saveDoctorOpinion(ClaimsWebBoardHelper.getClaimsSeqId(request),strDoctorOpinion,TTKCommon.getUserSeqId(request));
    		if(iCount>0)
    		{
    			request.setAttribute("updated","message.savedSuccessfully");
    		}//end of if(iCount>0)
    		
    		return this.getForward(strDocOpinion, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDocOpinionError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
	 * This method is used to forward to the parent screen
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
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doClose method of ClaimsAction");	
			setLinks(request);
			return mapping.findForward(strMedicalDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDocOpinionError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthManager getPreAuthManagerObject() throws TTKException
	{
		PreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strDocOpinionError);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()
}//end of DoctorOpinionAction
