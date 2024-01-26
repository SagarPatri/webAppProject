
/**
 * @ (#) ChangePasswordAction.java Feb 8, 2011
 * Project       : TTK HealthCare Services
 * File          : ChangePasswordAction.java
 * Author        : ManiKanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : Feb 8, 2011
 *
 * @author       :  ManiKanta Kumar G G
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */


/**
 * 
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
import com.ttk.dto.usermanagement.PasswordVO;


/**
 * @author manikanta_k
 *
 */
public class ChangePasswordAction extends TTKAction {
	
	public static final Logger log = Logger.getLogger(ChangePasswordAction.class); 
	public static final String strchangePassword ="changepassword";
	
	/**
     * This method is used to forward to change password screen.
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
            HttpServletResponse response) throws TTKException
    {
		try
		{
			 String strUserID = request.getParameter("UserId");
			 DynaActionForm frmChangepassword = (DynaActionForm)form;
			 frmChangepassword.set("userId",strUserID);
			 request.getSession().setAttribute("frmChangepassword", frmChangepassword);
			 return mapping.findForward(strchangePassword);  
		}//end of try
		catch(Exception exp){
			return this.processExceptions(request, mapping, new TTKException(exp,"login"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     // HttpServletResponse response)
	
	/**
	 * This method is used to change the password for the user.
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doChangePassword(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try
		{
			UserManager userManagerObject=this.getUserManagerObject();
			int iResult = 0;
			DynaActionForm frmChangepassword = (DynaActionForm)form;
			PasswordVO passwordVO = new PasswordVO();
			passwordVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			passwordVO.setUserID(request.getParameter("userId"));
			passwordVO.setOldPassword(request.getParameter("oldPassword"));
			passwordVO.setNewPassword(request.getParameter("newPassword"));
			iResult=userManagerObject.changePassword(passwordVO);
			if(iResult > 0)
			{
				request.setAttribute("updated","message.ttkpwdchangedSuccessfully");
				frmChangepassword.set("oldPassword","");
				frmChangepassword.set("newPassword","");
				frmChangepassword.set("confirmPassword","");
			}//end of if(iResult > 0)
			 return mapping.findForward(strchangePassword);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "login"));
		}//end of catch(Exception exp)
	}//end of doChangePassword(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	 //HttpServletResponse response)
	
	/**
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			request.getSession().removeAttribute("frmChangepassword");
			if(request.getSession().getAttribute("UserSecurityProfile")!=null)
            {
                if(request.getSession(false)!=null)  //invalidate the session
                {
                	request.getSession().setAttribute("UserSecurityProfile",null);
                	request.getSession().invalidate();
                }//end of if(request.getSession(false)!=null) 
            }//end of if(request.getSession().getAttribute("UserSecurityProfile")!=null)
			return mapping.findForward("close");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"login"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			throw new TTKException(exp, "login");
		}//end of catch
		return userManager;
	}//end getUserManagerObject()
}//end of ChangePasswordAction()
