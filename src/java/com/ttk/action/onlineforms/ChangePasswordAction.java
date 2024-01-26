/**
 * @ (#) ChangePasswordAction.java Oct 11, 2010
 * Project      : TTK HealthCare Services
 * File         : ChangePasswordAction
 * Author       : Manikanta Kumar G G
 * Company      : Span Systems Corporation
 * Date Created : Oct 11, 2010
 *
 * @author       : Manikanta Kumar G G
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
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.OnlineChangePasswordVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;


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
            HttpServletResponse response) throws TTKException{
        try{
            //log.debug("Inside ChangePasswordAction doDefault");              
            String strGroupID = request.getParameter("GroupID");
            String strPolicyNo = request.getParameter("PolicyNo");
            String strUserID = request.getParameter("UserId");
            String strInsCompCode= request.getParameter("InsCompCode");
            String strLoginType = request.getParameter("LoginType");
            String strHrUserId =  request.getParameter("HRUserId");
            String strInsUserId = request.getParameter("InsUserId");
            String strHrGroupId= request.getParameter("HRGroupID");
            String strHosEmpaneId =  request.getParameter("HosEmpanelId"); //added as per HOSPITAL LOGIN
            String strHosUserId= request.getParameter("HosUserId"); //added as per HOSPITAL LOGIN
            
            String strPtrEmpaneId =  request.getParameter("PtrEmpanelId"); //added as per PARTNER LOGIN
            String strPtrUserId= request.getParameter("PtrUserId"); //added as per PARTNER LOGIN
            
            String strBroUserId= request.getParameter("BroUserId"); //homebroker
            DynaActionForm frmOnlineChangePassword = (DynaActionForm)form;
            frmOnlineChangePassword.set("groupId",strGroupID);
            frmOnlineChangePassword.set("corpPolicyNo",strPolicyNo);
            frmOnlineChangePassword.set("userId",strUserID);
            frmOnlineChangePassword.set("loginType", strLoginType.trim());
            frmOnlineChangePassword.set("insCompCode", strInsCompCode);
            frmOnlineChangePassword.set("hrUserId", strHrUserId);
            frmOnlineChangePassword.set("insUserId", strInsUserId);
            frmOnlineChangePassword.set("hrGroupId", strHrGroupId);
            frmOnlineChangePassword.set("hosEmpaneId",strHosEmpaneId); //added as per HOSPITAL LOGIN
            frmOnlineChangePassword.set("hosUserId",strHosUserId); //added as per HOSPITAL LOGIN
            frmOnlineChangePassword.set("broUserId", strBroUserId); //homebroker
            
            frmOnlineChangePassword.set("ptrEmpaneId",strPtrEmpaneId); //added as per PARTNER LOGIN
            frmOnlineChangePassword.set("ptrUserId",strPtrUserId); //added as per PARTNER LOGIN
            
            request.getSession().setAttribute("frmOnlineChangePassword", frmOnlineChangePassword);
            return mapping.findForward(strchangePassword);            
        }//end of try
        
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccesslogin"));
        }//end of catch(Exception exp)
    }//end of doLogin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		try{
			//log.debug("Inside the doChangePassword method of ChangePasswordAction");
			OnlineAccessManager onlineAccessManagerObject = this.getOnlineAccessManagerObject();
			UserManager userManagerObject=this.getUserManagerObject();
			int iResult = 0;
			String strForwardPath="changepwd";
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			String strPolicyNo =request.getParameter("corpPolicyNo");
			String strGroupID = request.getParameter("groupId");
			DynaActionForm frmOnlineChangePassword1 = (DynaActionForm) request.getSession().getAttribute("frmOnlineChangePassword");
			String strLoginType= (String) frmOnlineChangePassword1.get("loginType");
			DynaActionForm frmOnlineChangePassword = (DynaActionForm)form;
			OnlineChangePasswordVO onlineChangePasswordVO = new OnlineChangePasswordVO();
			onlineChangePasswordVO.setOldPassword(request.getParameter("oldPassword"));
			onlineChangePasswordVO.setNewPassword(request.getParameter("newPassword"));
			
			//Modification as per KOC 11PP 1257
			if(strLoginType.equals("OHR") || strLoginType.equals("IBMOHR"))
			{
				onlineChangePasswordVO.setUserID(request.getParameter("hrUserId"));
			}//end of if(strLoginType.equals("OHR") || strLoginType.equals("IBMOHR") )
			else if(strLoginType.equals("OIU"))
			{
				onlineChangePasswordVO.setUserID(request.getParameter("insUserId"));
			}//end of else if(strLoginType.equals("OIU"))
			
			//homebroker
			else if(strLoginType.equals("OBR"))
			{
				onlineChangePasswordVO.setUserID(request.getParameter("broUserId"));
			}//end of else if(strLoginType.equals("OBR"))
			//Modification as per KOC 11PP 1257
			if(strLoginType.equals("OCO")|| strLoginType.equals("IBMOCO"))
			{
				onlineChangePasswordVO.setUserID(request.getParameter("userId"));
				iResult=onlineAccessManagerObject.changeOnlinePassword(onlineChangePasswordVO,strPolicyNo,strGroupID);
			}//end of if(strLoginType.equals("OCO")|| strLoginType.equals("IBMOCO"))
			//homebroker
			else if(strLoginType.equals("OHR") || strLoginType.equals("OIU") || strLoginType.equals("IBMOHR") || strLoginType.equals("OBR"))
			{
				if(userSecurityProfile != null && userSecurityProfile.getSecurityProfile()!=null)
	            {
	            	Long lngContactSeqID = userSecurityProfile.getContactSeqID();
	            	onlineChangePasswordVO.setContactSeqID(lngContactSeqID);
	            	iResult=userManagerObject.changeOnlinePassword(onlineChangePasswordVO);
	            }//end of if(userSecurityProfile != null && userSecurityProfile.getSecurityProfile()!=null)
			}//end of else if(strLoginType.equals("OHR") || strLoginType.equals("OIU")|| strLoginType.equals("IBMOHR"))
			//added as per HOSPITAL LOGIN
			if(strLoginType.equals("HOS"))
			{
				if(userSecurityProfile != null && userSecurityProfile.getSecurityProfile()!=null)
	            {
			   	onlineChangePasswordVO.setContactSeqID(userSecurityProfile.getUSER_SEQ_ID());
	            }
				onlineChangePasswordVO.setUserID(request.getParameter("hosUserId"));
            	iResult=userManagerObject.changeOnlinePassword(onlineChangePasswordVO);

			}//end of if(strLoginType.equals("OHR") || strLoginType.equals("IBMOHR") )
			//added as per HOSPITAL LOGIN
			if(strLoginType.equals("PTR"))
			{
				if(userSecurityProfile != null && userSecurityProfile.getSecurityProfile()!=null)
	            {
			   	onlineChangePasswordVO.setContactSeqID(userSecurityProfile.getUSER_SEQ_ID());
	            }
				onlineChangePasswordVO.setUserID(request.getParameter("ptrUserId"));
            	iResult=userManagerObject.changeOnlinePassword(onlineChangePasswordVO);

			}//end of if(strLoginType.equals("OHR") || strLoginType.equals("IBMOHR") )
			//added as per PARTNER LOGIN
			if(iResult > 0)
			{
				if(strLoginType.equals("OHR"))				{
					request.setAttribute("updated","message.hrpwdchangedSuccessfully");
				}//end of if(strLoginType.equals("OHR"))
				if(strLoginType.equals("OCO"))				{
					request.setAttribute("updated","message.emppwdchangedSuccessfully");
				}//end of if(strLoginType.equals("OCO"))
				if(strLoginType.equals("OIU"))				{
					request.setAttribute("updated","message.iupwdchangedSuccessfully");
				}//end of if(strLoginType.equals("OIU"))
				
				//homebroker
				if(strLoginType.equals("OBR"))				{
					request.setAttribute("updated","message.brpwdchangedSuccessfully");
				}//end of if(strLoginType.equals("OBR"))
				//added as per KOC 1257 11PP
				if(strLoginType.equals("IBMOCO"))			{
					request.setAttribute("updated","message.ibmpwdchangedSuccessfully");
				}//end of if(strLoginType.equals("IBMOCO"))
					if(strLoginType.equals("IBMOHR"))		{
					request.setAttribute("updated","message.ibmhrpwdchangedSuccessfully");
				}//end of if(strLoginType.equals("IBMOHR"))
					if(strLoginType.equals("HOS"))				{
						request.setAttribute("updated","message.hospwdchangedSuccessfully");
					}//end of if(strLoginType.equals("OHR"))
					if(strLoginType.equals("PTR"))				{
						request.setAttribute("updated","message.ptrpwdchangedSuccessfully");
					}//end of if(strLoginType.equals("PTR"))
					
				//added as per KOC 1257 11PP

				frmOnlineChangePassword.set("loginType", strLoginType);
			    frmOnlineChangePassword.set("oldPassword","");
			    frmOnlineChangePassword.set("newPassword","");
			    frmOnlineChangePassword.set("confirmPassword","");
			}//end of if(iResult > 0)
			request.getSession().setAttribute("frmOnlineChangePassword", frmOnlineChangePassword);
			return mapping.findForward(strForwardPath);
		}//end of try
		catch(TTKException expTTK)
		{
			DynaActionForm frmOnlineChangePassword1 = (DynaActionForm) request.getSession().getAttribute("frmOnlineChangePassword");
			String strLoginType= (String) frmOnlineChangePassword1.get("loginType");
			DynaActionForm frmOnlineChangePassword = (DynaActionForm)form;
			frmOnlineChangePassword.set("loginType", strLoginType);
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccesslogin"));
		}//end of catch(Exception exp)
	}//end of doChangePassword(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			throw new TTKException(exp, "onlineaccesslogin");
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManagerObject()
	
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
			throw new TTKException(exp, "onlineaccesslogin");
		}//end of catch
		return userManager;
	}//end getUserManagerObject()
}//end of ChangePasswordAction
