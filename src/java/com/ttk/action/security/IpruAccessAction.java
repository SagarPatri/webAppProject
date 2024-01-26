/**
 * @ (#) IpruAccessAction.java Nov 13th, 2008
 * Project      : TTK HealthCare Services
 * File         : IpruAccessAction.java
 * Author       : Ramakrishna K M
 * Company      : Span Systems Corporation
 * Date Created : Nov 13th, 2008
 *
 * @author       :  Ramakrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.security;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class IpruAccessAction extends TTKAction{

    private static Logger log = Logger.getLogger( IpruAccessAction.class );

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
            log.info("Inside IpruAccessAction doLogin");
            UserSecurityProfile userSecurityProfile = null; //Contains the User Information
            String strLink = "";
            UserVO userVO = new UserVO();
            DynaActionForm frmIpruAccess = (DynaActionForm)form;
            String strPolicyNbr=(String)frmIpruAccess.get("policyNbr");
            String strFromUrl = (String)frmIpruAccess.get("fromUrl");
            String strIpruUrl = "www.iciciprulife.com";
            log.info("strPolicyNbr is : "+strPolicyNbr);
            log.info("strFromUrl is   : "+strFromUrl);
            
            if(TTKCommon.checkNull(strFromUrl).equals("") || !TTKCommon.checkNull(strFromUrl).equalsIgnoreCase(strIpruUrl)){
            	TTKException expTTK = new TTKException();
				expTTK.setMessage("error.ipru.fromurlrequired");
				throw expTTK;
            }//end of if(TTKCommon.checkNull(strFromUrl).equals(""))
            else{
            	userVO.setCustomerCode(TTKCommon.checkNull(strPolicyNbr));
                userVO.setLoginType("IPR");
                //load the user security profile object
                userSecurityProfile = (UserSecurityProfile)this.getSecurityManagerObject().ipruLogin(userVO);
                if(userSecurityProfile != null && userSecurityProfile.getSecurityProfile()!=null)
                {
                    
                    userSecurityProfile.setLoginType("IPR");
                    userSecurityProfile.setCustomerCode(TTKCommon.checkNull(strPolicyNbr));
                    request.getSession().setAttribute("UserSecurityProfile",userSecurityProfile);//set the User security profile object to session
                    setOnlineLinks(request);  //call the setlinks to set default links when user logins
                    strLink = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveLink()+"."+((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveSubLink()+"."+((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().getActiveTab();

                    log.info("Returning to : "+strLink);
                    this.loadToolBar(request, strLink);
                    request.setAttribute("OpenPage",mapping.findForward(strLink).getPath());
                    //return to the appropriate screen
                    return (mapping.findForward("ipruaccesslogin"));
                }//end if(this.getSecurityManagerObject().login(userVO) != null)
                else
                {
                    log.info("Returning to failure !!!!!!");
                    return (mapping.findForward("ipruaccesslogin"));
                }//end of else
            }//end of else
        }//end of try
        catch(TTKException expTTK)
        {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage(expTTK.getMessage());    //getMessage will return like "user.create.error"
            log.info("Error Key: "+expTTK.getMessage());
            actionMessages.add("global.error",actionMessage);
            saveErrors(request,actionMessages);
            //return this.processExceptions(request, mapping, expTTK);
            return mapping.findForward("ipruaccesslogin");
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processIpruExceptions(request, mapping, new TTKException(exp,"ipruaccesslogin"));
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
            log.info("Inside IpruAccessAction doLogoff");
            if(request.getSession().getAttribute("UserSecurityProfile")!=null)
            {
                //UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
                //log.info("Invalidating the session......"+userSecurityProfile.getPolicyNo());
                if(request.getSession(false)!=null)  //invalidate the session
                {
                	request.getSession().setAttribute("UserSecurityProfile",null);
                	request.getSession().invalidate();
                }//end of if(request.getSession(false)!=null)
            }//end of if(request.getSession().getAttribute("UserSecurityProfile")!=null)
            return null;
        }//end of try
        catch(Exception exp)
        {
            return this.processIpruExceptions(request, mapping, new TTKException(exp,"ipruaccesslogin"));
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
                log.info("Inside getSecurityManagerObject Vidal Health TPA>>>>>>>>>>>>>>>>>>>>>>> " + securityManager);
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

}//end of IpruAccessAction
