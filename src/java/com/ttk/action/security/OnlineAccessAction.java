/**
 * @ (#) OnlineAccessAction.java Jul 31, 2006
 * Project      : TTK HealthCare Services
 * File         : OnlineAccessAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 31, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.security;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.common.SecurityManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.IconObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.dto.usermanagement.UserVO;

/**
 *
 *
 */
public class OnlineAccessAction extends TTKAction{

    private static Logger log = Logger.getLogger( OnlineAccessAction.class );

    /**
     * This method is used check for the authorized user.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doLogin(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.info("Inside OnlineAccessAction doLogin");
            UserSecurityProfile userSecurityProfile = null; //Contains the User Information
            String strLink = "";
            UserVO userVO = new UserVO();
            DynaActionForm frmOnlineAccess = (DynaActionForm)form;
            String strLoginType=(String)frmOnlineAccess.get("loginType");
            ArrayList<Object>alUserList = new ArrayList<Object>() ;
            HttpSession session=request.getSession();
            String strPwdExpiryYN ="";
            String strFirstLogYN="";
            String   strPwdExpiryMsg="";//Modified as per KOC 1257 11PP
			String strLoginExpiryYN="";//Modified as per KOC 1257 11PP
                     // Long lngContactSeqID =null;
			String strRandomNo="";;//added as per KOC 1349
         
            if(strLoginType.equals("OIU"))
            {
            	userVO.setLoginType("OIU");
            	userVO.setGroupID((String)frmOnlineAccess.get("insCompCode"));
            	request.getSession().setAttribute("insCompCodeWebLogin", (String)frmOnlineAccess.get("insCompCode"));
                userVO.setUSER_ID((String)frmOnlineAccess.get("insUserId"));
                userVO.setPassword((String)frmOnlineAccess.get("insPassword"));
                
            }else if(strLoginType.equals("OBR")){
            	
          	  userVO.setLoginType("OBR");
              userVO.setGroupID((String)frmOnlineAccess.get("brokerCompCode"));
              userVO.setUSER_ID((String)frmOnlineAccess.get("broUserId"));
              userVO.setPassword((String)frmOnlineAccess.get("broPassword"));
          }//end of  else if(strLoginType.equals("OBR"))
          else if(strLoginType.equals("HOS")){
            	
        	  userVO.setLoginType("HOS");
            	// As we are not using in intX 
            	userVO.setGroupID((String)frmOnlineAccess.get("hosEmpaneId"));
            	userVO.setUSER_ID((String)frmOnlineAccess.get("hosUserId"));
                userVO.setPassword((String)frmOnlineAccess.get("hosPassword"));
                userVO.setGrpOrInd((String)frmOnlineAccess.get("hosProvType")); 
            }//end of  else if(strLoginType.equals("OCI"))
          else if(strLoginType.equals("PTR")){          
        	  userVO.setLoginType("PTR");
        	  	userVO.setGroupID((String)frmOnlineAccess.get("ptrEmpaneId"));
            	userVO.setUSER_ID((String)frmOnlineAccess.get("ptrUserId"));
                userVO.setPassword((String)frmOnlineAccess.get("ptrPassword"));
            }//end of  else if(strLoginType.equals("PTR"))
            //Hospital Login
            //load the user security profile object
            
            String ipAddress=request.getRemoteHost();			//Changes Added for kocbroker
			userVO.setHostIPAddress(ipAddress);	
		
			
            alUserList = (ArrayList<Object>)this.getSecurityManagerObject().externalLogin(userVO);
            userSecurityProfile = (UserSecurityProfile) alUserList.get(0);
            strPwdExpiryYN = (String)  TTKCommon.checkNull(alUserList.get(1));
            strFirstLogYN = userSecurityProfile.getFirstLoginYN();
            strPwdExpiryMsg = (String) TTKCommon.checkNull(alUserList.get(2));  //Added as petr KOC 11PP
			 strLoginExpiryYN=(String) TTKCommon.checkNull(alUserList.get(3));//Added as petr KOC 11PP
			 strRandomNo=(String) TTKCommon.checkNull(alUserList.get(4));//Added as petr KOC 1349
           
            frmOnlineAccess.set("pwdExpiryYN", strPwdExpiryYN);
            frmOnlineAccess.set("firstLoginYN", strFirstLogYN);
            frmOnlineAccess.set("pwdExpiryMsg", strPwdExpiryMsg);//Added as petr KOC 1257 11PP
			frmOnlineAccess.set("loginExpiryYN", strLoginExpiryYN);//Added as petr KOC 1257 11PP
			frmOnlineAccess.set("randomNo", strRandomNo);//Added as petr KOC 1349 11PP
			userSecurityProfile.setRandomNo((String)frmOnlineAccess.get("randomNo"));//Added as petr KOC 1349 11PP
           // }
            //Changes as per IBM change Request
           // frmOnlineAccess.set("contactSeqID", lngContactSeqID.toString());
            if(userSecurityProfile != null && userSecurityProfile.getSecurityProfile()!=null)
            {              
                
                if(strLoginType.equals("OIU"))
                {
                	userSecurityProfile.setLoginType("U");
                	userSecurityProfile.setGroupID((String)frmOnlineAccess.get("insCompCode"));
                	userSecurityProfile.setUSER_ID((String)frmOnlineAccess.get("insUserId"));
                }//end of  else if(strLoginType.equals("OIU"))                
                else if(strLoginType.equals("OBR"))
                {
                	userSecurityProfile.setLoginType("B");
                	userSecurityProfile.setUSER_ID((String)frmOnlineAccess.get("broUserId"));
                	request.getSession().setAttribute("loginType","OBR");
                	request.getSession().setAttribute("broUserId",frmOnlineAccess.get("broUserId"));
                	request.getSession().setAttribute("brokerCode",userSecurityProfile.getBrokerCode());
                }//end of  else if(strLoginType.equals("OIU"))                
                else if(strLoginType.equals("HOS"))
                {
                	userSecurityProfile.setLoginType("HOS");
                	//userSecurityProfile.setGroupID((String)frmOnlineAccess.get("hosEmpaneId"));
                	userSecurityProfile.setUSER_ID((String)frmOnlineAccess.get("hosUserId"));
                }//end of  else if(strLoginType.equals("HOS"))
              //added as per Hospital Login
                else if(strLoginType.equals("PTR"))
                {
                	userSecurityProfile.setLoginType("PTR");
                	userSecurityProfile.getSecurityProfile().setActiveLink("Partner Information");
                	userSecurityProfile.getSecurityProfile().setActiveSubLink("Home");
                	userSecurityProfile.getSecurityProfile().setActiveTab("Home");
                	userSecurityProfile.setUSER_ID((String)frmOnlineAccess.get("ptrUserId"));
                	session.setAttribute("ptnrUserID",frmOnlineAccess.get("ptrUserId"));
                	session.setAttribute("ptnrUserSeqID",userSecurityProfile.getUSER_SEQ_ID());
                	session.setAttribute("ptnrSeqID",userSecurityProfile.getPtnrSeqId() );
                	session.setAttribute("ptnrName", userSecurityProfile.getPartnerName());
                	session.setAttribute("ptnrEmpnNO", userSecurityProfile.getEmpanelNumber());
                	session.setAttribute("ptnrCountry", userSecurityProfile.getPartnerCountry());
                	session.setAttribute("ptnrEmirate", userSecurityProfile.getPartnerEmirate());
                	session.setAttribute("ptnrArea", userSecurityProfile.getPartnerArea());
                	session.setAttribute("ptnrAddress", userSecurityProfile.getPartnerAddress());
                	session.setAttribute("ptnrPhoneNO", userSecurityProfile.getPartnerPhone());
                	
                	if(userSecurityProfile.getSecurityProfile().isAuthorized("Online Information.Home.Home.PbmLoginAccess")){
                		session.setAttribute("PartnerLoginAccess","Y");
                	}else{
                		session.setAttribute("PartnerLoginAccess","N");
                	}
                }//end of  else if(strLoginType.equals("PTR"))
                request.getSession().setAttribute("UserSecurityProfile",userSecurityProfile);//set the User security profile object to session
                setOnlineLinks(request);  //call the setlinks to set default links when user logins
                strLink = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveLink()+"."+((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveSubLink()+"."+((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveTab();
           //load the tool bar to session
                this.loadToolBar(request, strLink);
                request.setAttribute("OpenPage",mapping.findForward(strLink).getPath());
                request.getSession().setAttribute("frmOnlineAccess", frmOnlineAccess);
                //return to the appropriate screen
                if("U".equals(userSecurityProfile.getLoginType()))
                	return mapping.findForward("insrelogin");
                else if("HOS".equals(userSecurityProfile.getLoginType()))
                	return mapping.findForward("hosrelogin");
                else
                	return (mapping.findForward("onlineaccesslogin"));
                
            }//end if(this.getSecurityManagerObject().login(userVO) != null)
            else
            {
                return (mapping.findForward("onlineaccesslogin"));
            }//end of else
        }//end of try
        catch(TTKException expTTK)
        {
        	if(expTTK.getMessage().equals("error.weblogin.policyno") ||(expTTK.getMessage().equals("error.policyno.required")))
        	{
        		request.setAttribute("tmp", "polerr");
        	}//end of if(expTTK.getMessage().equals("error.weblogin.policyno") ||(expTTK.getMessage().equals("error.policyno.required")))
        	if(expTTK.getMessage().equals("error.weblogin.enrollmentid") ||(expTTK.getMessage().equals("error.enrollmentId.required")))
        	{
        		request.setAttribute("tmp", "enrerr");
        	}//end of if(expTTK.getMessage().equals("error.weblogin.enrollmentid") ||(expTTK.getMessage().equals("error.enrollmentId.required")))
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage(expTTK.getMessage());//getMessage will return like "user.create.error"
            actionMessages.add("global.error",actionMessage);
            saveErrors(request,actionMessages);
            //return this.processExceptions(request, mapping, expTTK);
            return mapping.findForward("onlineaccesslogin");
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccesslogin"));
        }//end of catch(Exception exp)
    }//end of doLogin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to logoff from the application.
     * Finally it forwards to login page.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doLogoff(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
        try{
            log.info("Inside OnlineAccessAction doLogoff");
            
            UserSecurityProfile userSecurityProfile1	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
            
            if(userSecurityProfile1!=null){
            
            	String temp	=	userSecurityProfile1.getLoginType();
            	SecurityManager securityManagerObject = this.getSecurityManagerObject();	
            	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
            	int iResult =securityManagerObject.userLoginRandomNo(TTKCommon.getUserID(request),userSecurityProfile.getRandomNo());
            	log.info("Invalidating the session......"+userSecurityProfile.getPolicyNo());
                if(request.getSession(false)!=null)  //invalidate the session
                {
                	request.getSession().setAttribute("UserSecurityProfile",null);
                	request.getSession().invalidate();
                }//end of if(request.getSession(false)!=null) 
           
            if("U".equals(temp))return (mapping.findForward("insrelogin"));
			else if("HOS".equals(temp))
            	return mapping.findForward("hosrelogin");
            
            else if("B".equals(temp)){
            	request.setAttribute("LogoutSeccess","Your Successfully Logout!!!");
            	request.setAttribute("loginType", "OBR");
            	return (mapping.findForward("onlineaccesslogin"));
            }
            else if("PTR".equals(temp)){
           		request.setAttribute("LogoutSeccess","You are Successfully Logout!!!");
           		request.setAttribute("loginType", "PTR");
            	return mapping.findForward("ptrrelogin");
    		}
        }else {//if(userSecurityProfile1!=null)
        	 TTKException ttkException=new TTKException();
             ttkException.setMessage("error.onlinesession");
             throw ttkException;
        }//end of try
        return (mapping.findForward("onlineaccesslogin"));
        }
        catch(TTKException expTTK)
        {
        	if("OBR".equals(request.getParameter("loginType"))) return this.processBrokerExceptions(request, mapping, expTTK);
        	else  return this.processOnlineExceptions(request, mapping, new TTKException(expTTK,"onlineaccesslogin"));
           
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccesslogin"));
        }//end of catch(Exception exp)
    }//end of doLogoff(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * Returns the SecurityManager session object for invoking methods on it.
     * @return SecurityManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private SecurityManager getSecurityManagerObject() throws TTKException
    {
        SecurityManager securityManager = null;
        try
        {
            if(securityManager == null)
            {
                InitialContext ctx = new InitialContext();
                securityManager = (SecurityManager) ctx.lookup("java:global/TTKServices/business.ejb3/SecurityManagerBean!com.ttk.business.common.SecurityManager");	//changed for jboss upgradation
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "security");
        }//end of catch(Exception exp)
        return securityManager;
    }//end getSecurityManagerObject()

    /**
     * This method will load the tool bar to the session
     * @param request HttpServletRequest current request object
     * @param strLink
     */
    private void loadToolBar(HttpServletRequest request, String strLink)
    {
        Toolbar toolBar = null;
        IconObject conflictIcon = new IconObject();
        conflictIcon.setImageURL("ErrorIcon");
        IconObject docViewIcon = new IconObject();
        docViewIcon.setImageURL("DocViewIcon");

        toolBar = new Toolbar(conflictIcon, docViewIcon, strLink);
        request.getSession().setAttribute("toolbar", toolBar); //set the tool bar to the session
    }//end of loadToolBar(HttpServletRequest request)
}//end of OnlineAccessAction
