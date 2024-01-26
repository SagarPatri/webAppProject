/**
 * @ (#) ChangePswdAction.java April 20th, 2006
 * Project       : TTK HealthCare Services
 * File          : ChangePswdAction.java
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : April 20th, 2006
 * @author       : Krupa J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.action.myprofile;

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
import com.ttk.dto.usermanagement.PasswordVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for Updating the Password of the user.
 */
public class ChangePswdAction extends TTKAction	{
	private static Logger log = Logger.getLogger( ChangePswdAction.class );
	//forward path
	private static final String strChangePassword="changepassword";
	private static final String strInsChangePassword="inschangepassword";
	private static final String strProvChangePassword="provchangepassword";
	private static final String strPtnrChangePassword="ptnrchangepassword";
	//Exception Message Identifier
	private static final String strUserExp="user";
	
	/**
	 * This method is used to initialise the form.
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
			log.debug("Inside the doDefault method of ChangePasswordAction");
			setLinks(request);
			DynaActionForm frmPassword = (DynaActionForm)form;
			frmPassword.initialize(mapping);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			if("U".equals(userSecurityProfile.getLoginType()))
				return this.getForward(strInsChangePassword, mapping, request);
			if("HOS".equals(userSecurityProfile.getLoginType()))
				return this.getForward(strProvChangePassword, mapping, request);
			else if("PTR".equals(userSecurityProfile.getLoginType()))
				return this.getForward(strPtnrChangePassword, mapping, request);
			else
				return this.getForward(strChangePassword, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doSave method of ChangePasswordAction");
			setLinks(request);
			UserManager userManagerObject=this.getUserManagerObject();
			int iResult = 0;
			DynaActionForm frmPassword = (DynaActionForm)form;
			PasswordVO passwordVO = new PasswordVO();
			passwordVO = (PasswordVO) FormUtils.getFormValues(frmPassword, this, mapping, request);
			passwordVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			passwordVO.setUserID(TTKCommon.getUserID(request));
			iResult=userManagerObject.changePassword(passwordVO);
			if(iResult > 0)
			{
				request.setAttribute("updated","message.changedSuccessfully");
				frmPassword.initialize(mapping);
			}//end of if(iResult > 0)
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			if("HOS".equals(userSecurityProfile.getLoginType()))
				return this.getForward(strProvChangePassword, mapping, request);
			else if("PTR".equals(userSecurityProfile.getLoginType()))
				return this.getForward(strPtnrChangePassword, mapping, request);
			else
				return this.getForward(strChangePassword, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			throw new TTKException(exp, strUserExp);
		}//end of catch
		return userManager;
	}//end getUserManagerObject()
}//end of ChangePswdAction    
    
