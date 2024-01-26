/**
 * @ (#) UserPasswordValidityConfig.java Oct 20, 2012
 * Project      : TTK HealthCare Services
 * File         : UserPasswordValidityConfig.java
 * Author       : 
 * Company      : 
 * Date Created : Oct 20, 2012
 *
 * @author       : 
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.usermanagement;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.usermanagement.UserManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.usermanagement.ContactVO;
import com.ttk.dto.usermanagement.PasswordValVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;
import com.ttk.dto.usermanagement.UserAccessVO;
import com.ttk.dto.usermanagement.UserListVO;

import formdef.plugin.util.FormUtils;

public class UserPasswordValidityConfig extends TTKAction{
	
	private static Logger log = Logger.getLogger( UserPasswordValidityConfig.class );

	//Action mapping forwards.
	private static final String strPasswordValConfig="pswdvalidityconf";
	private static final String strPasswordValConfigClose="pswdvalidityclose";
	
	//Exception Message Identifier
	private static final String strUserList="userlist";

	/**
	 * This method is used to get the information of the user.
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
			log.debug("Inside the doDefault method of UserPasswordValidityConfig");
			setLinks(request);
			DynaActionForm frmPasswordVal = (DynaActionForm)request.getSession().getAttribute("frmPasswordVal");
			PasswordValVO passwordValVO= null;
                        String identifier=request.getParameter("identifier");//added as per 1257 CR
			UserManager userManagerObject=this.getUserManagerObject();
			passwordValVO=(PasswordValVO)userManagerObject.getPasswordConfigInfo(identifier);
                        passwordValVO.setIdentifier(TTKCommon.checkNull(identifier));//added as per 1257 CR
	    		frmPasswordVal = (DynaActionForm)FormUtils.setFormValues("frmPasswordVal",passwordValVO, this, mapping, request);
			request.setAttribute("frmPasswordVal",frmPasswordVal);
			return this.getForward(strPasswordValConfig, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to delete the selected records from the list.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doConfigurePasswordValidity(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doConfiguration method of UserPasswordValidityConfig");
			setLinks(request);
			StringBuffer sbfCaption= new StringBuffer();
			@SuppressWarnings("unused")
			DynaActionForm frmPasswordVal = (DynaActionForm)request.getSession().getAttribute("frmPasswordVal");
			request.getSession().setAttribute("ConfigurationTitle", sbfCaption.toString());
			return this.getForward(strPasswordValConfig, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
		}//end of catch(Exception exp)
		}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			setLinks(request);	
			DynaActionForm frmPasswordVal = (DynaActionForm)form;
			PasswordValVO passwordValVO= new PasswordValVO();
			UserManager userManagerObject = this.getUserManagerObject();
			passwordValVO=(PasswordValVO)FormUtils.getFormValues(frmPasswordVal, "frmPasswordVal", this,mapping, request);
			int iResult = userManagerObject.savePasswordConfigInfo(passwordValVO);
			if(iResult>0)
			{
				request.setAttribute("updated","message.saved");
				frmPasswordVal = (DynaActionForm)FormUtils.setFormValues("frmPasswordVal",passwordValVO, this, mapping, request);
				request.setAttribute("frmPasswordVal",frmPasswordVal);
			}//end of if(iResult>0)			
			return this.getForward(strPasswordValConfig, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
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
	   try{
			log.debug("Inside the doReset method of UserPasswordValidityConfig");
			setLinks(request);
			return doDefault(mapping,form,request,response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPasswordValConfig));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * This method is used to navigate to previous screen when closed button is clicked.
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
			log.debug("Inside the doClose method of UserPasswordValidityConfig");
			setLinks(request);
			return mapping.findForward(strPasswordValConfigClose);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to reset the password for all TTK,CAll CENTER,DMC users.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @param strUserType 
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doResetPasswordAll(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ContactAction doResetPassword");
			setLinks(request);
			String strContactPath="";
			//strContactPath=getForwardPath(request);
			DynaActionForm frmPasswordVal=(DynaActionForm)form;
			UserManager userManagerObject = this.getUserManagerObject();
			//CommunicationManager communicationManager = this.getCommunicationManagerObject();
			//Long lContactSeqId=TTKCommon.getLong((String)frmPasswordVal.get("contactSeqID"));
			int iResult =userManagerObject.resetPasswordAll(TTKCommon.getUserSeqId(request));
			
			
			//communicationManager.sendMessage("EMAIL");
			if(iResult>0)
			{
				request.setAttribute("updated","message.resetpassword");
			}//end of if(iResult>0)
			return this.getForward(strPasswordValConfig, mapping, request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"contactAction"));
		}//end of catch(Exception exp)
	}//end of doResetPassword(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 * Returns the UserManager session object for invoking methods on it.
	 * @return UserManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private UserManager getUserManagerObject() throws TTKException
	{
		UserManager userManager = null;
		try
		{
			if(userManager == null)
			{
				InitialContext ctx = new InitialContext();
				userManager = (UserManager) ctx.lookup("java:global/TTKServices/business.ejb3/UserManagerBean!com.ttk.business.usermanagement.UserManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return userManager;
	}//end getUserManagerObject()

}//end of RenewalDaysConfAction
