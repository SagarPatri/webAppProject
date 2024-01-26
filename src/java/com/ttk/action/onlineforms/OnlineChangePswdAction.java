/**
 * @ (#) OnlineChangePswdAction.java Feb 20th, 2008
 * Project       : TTK HealthCare Services
 * File          : OnlineChangePswdAction.java
 * Author        : Chandrasekaran J
 * Company       : Span Systems Corporation
 * Date Created  : Feb 20th, 2008
 * @author       : Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.action.onlineforms;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.business.usermanagement.UserManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.usermanagement.PasswordVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class OnlineChangePswdAction extends TTKAction
{
	private static Logger log = Logger.getLogger( OnlineChangePswdAction.class );
	//Mode
	private static final String strOnlineForms="Online Information";
	private static final String strLoginProfile="Login Profile";
	//forward path
	private static final String strOnlineChangePassword="onlinechangepassword";
	private static final String strLoginPwd="loginchangepassword";
	//Exception Message Identifier
	private static final String strUserExp="user";
	private static final String strProvChangePassword="provchangepassword";
	private static final String strPtnrChangePassword="ptnrchangepassword";
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
			log.debug("Inside the doDefault method of OnlineChangePswdAction");
			setOnlineLinks(request);
			String strForwardPath="";
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strOnlineForms))
			{
				strForwardPath=strOnlineChangePassword;
			}//end of else if(strLink.equals(strOnlineForms))
			else if(strLink.equals(strLoginProfile))
			{
				strForwardPath=strLoginPwd;
			}//end of else if(strActiveLink.equals(strLoginProfile))
			DynaActionForm frmPassword = (DynaActionForm)form;
			frmPassword.initialize(mapping);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strUserExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to change the password for the user.
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangePassword(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doChangePassword method of OnlineChangePswdAction");
			setOnlineLinks(request);
			UserManager userManagerObject=this.getUserManagerObject();
			OnlineAccessManager onlineAccessMangaerObject = this.getOnlineAccessManagerObject();
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
			int iResult = 0;
			String strForwardPath="";
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strOnlineForms))
			{
				strForwardPath=strOnlineChangePassword;
			}//end of else if(strLink.equals(strOnlineForms))
			else if(strLink.equals(strLoginProfile))
			{
				strForwardPath=strLoginPwd;
			}//end of else if(strActiveLink.equals(strLoginProfile))
			DynaActionForm frmPassword = (DynaActionForm)form;
			PasswordVO passwordVO = new PasswordVO();
			passwordVO = (PasswordVO) FormUtils.getFormValues(frmPassword, this, mapping, request);
			passwordVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			if(userSecurityProfile.getLoginType().equals("E")){
				passwordVO.setUserID(userSecurityProfile.getUSER_ID());
				iResult=onlineAccessMangaerObject.changePassword(passwordVO,userSecurityProfile.getPolicyNo(),
						                                 userSecurityProfile.getGroupID());
			}//end of if(userSecurityProfile.getLoginType().equals("E"))
			if("HOS".equals(userSecurityProfile.getLoginType())){
				passwordVO.setUserID(userSecurityProfile.getUSER_ID());
				iResult=onlineAccessMangaerObject.changePassword(passwordVO,userSecurityProfile.getPolicyNo(),
						                                 userSecurityProfile.getGroupID());
				strForwardPath=strProvChangePassword;
			}
			else if("PTR".equals(userSecurityProfile.getLoginType())){
				passwordVO.setUserID(userSecurityProfile.getUSER_ID());
				iResult=onlineAccessMangaerObject.changePassword(passwordVO,userSecurityProfile.getPolicyNo(),
						                                 userSecurityProfile.getGroupID());
				strForwardPath=strPtnrChangePassword;
			}
			else{
				passwordVO.setUserID(TTKCommon.getUserID(request));
				iResult=userManagerObject.changePassword(passwordVO);
			}//end of else
			if(iResult > 0)
			{
				request.setAttribute("updated","message.changedSuccessfully");
				frmPassword.initialize(mapping);
			}//end of if(iResult > 0)
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strUserExp));
		}//end of catch(Exception exp)
	}//end of doChangePassword(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the userManager session object for invoking methods on it.
	 * @return userManager session object which can be used for method invokation 
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
			}//end if(userManager == null)
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, strUserExp);
		}//end of catch
		return userManager;
	}//end getUserManagerObject()
	
	/**
	 * Returns the onlineAccessManager session object for invoking methods on it.
	 * @return onlineAccessManager session object which can be used for method invokation 
	 * @exception throws TTKException  
	 */
	private OnlineAccessManager getOnlineAccessManagerObject() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try 
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
			}//end if(onlineAccessManager == null)
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, strUserExp);
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManagerObject()
}//end of OnlineChangePswdAction()
