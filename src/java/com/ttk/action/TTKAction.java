/**
 * @ (#) TTKAction.java 12th Jul 2005
 * Project      :
 * File         : TTKAction.java
 * Author       : Nagaraj D V
 * Company      :
 * Date Created : 12th Jul 2005
 *
 * @author       : Nagaraj D V
 * Modified by   : Arun K N
 * Modified date : 18th May 2007
 * Reason        : For adding checkSourcePathPermission,checkCurrentActionPermission and checkTargetPathPermission
 *                 methods to check the security before setting the links.
 */

package com.ttk.action;

import javax.naming.InitialContext;
//End changes for Password Policy CR KOC 1235
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

//Changes Added for Password Policy CR KOC 1235
import com.ttk.business.common.SecurityManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This is the Base Action class for the Application.
 * This Class is called when links are modified in application.
 *
 * It has methods to set the Links, check the Source path permission, current action permission
 * and target path permission. If the User's role not has the respective permission
 * it will throw appropriate error message.
 *
 * All the Exceptions raised in the Application will be processed in this class.
 *
 */

public class TTKAction extends DispatchAction
{
    private static Logger log1 = Logger.getLogger( TTKAction.class );

    /**
     * This method is used set the appropriate links by calling setlinks method
     *
     * exceptions and finally it returns control based on the forward mapping.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception
            {
        try
        {
            StringBuffer sbfForwardPath=new StringBuffer();
            //call the setlinks method to set the appropriate link when link, sub link or tab is modified
            setLinks(request);
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
            //create the forward path for the next view.
            sbfForwardPath.append(userSecurityProfile.getSecurityProfile().getActiveLink()).append(".");
            sbfForwardPath.append(userSecurityProfile.getSecurityProfile().getActiveSubLink()).append(".");
            sbfForwardPath.append(userSecurityProfile.getSecurityProfile().getActiveTab());
          //  System.out.println("sbfForwardPath.toString()..."+sbfForwardPath.toString());
            return mapping.findForward(sbfForwardPath.toString());
        }//end of try
        catch(TTKException ttkExp)
        {
            return this.processExceptions(request, mapping, ttkExp);
        }//end of catch(TTKException ttkExp)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, ""));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used set the appropriate links by calling setlinks method
     *
     * exceptions and finally it returns control based on the forward mapping.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doOnlineDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception
            {
        try
        {
            log1.info("Inside doOnlineDefault method");
        	StringBuffer sbfForwardPath=new StringBuffer();
            //call the setlinks method to set the appropriate link when link, sub link or tab is modified
            setOnlineLinks(request);
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
                                        getAttribute("UserSecurityProfile");

            //create the forward path for the next view.
            sbfForwardPath.append(userSecurityProfile.getSecurityProfile().getActiveLink()).append(".");
            sbfForwardPath.append(userSecurityProfile.getSecurityProfile().getActiveSubLink()).append(".");
            sbfForwardPath.append(userSecurityProfile.getSecurityProfile().getActiveTab());
            return mapping.findForward(sbfForwardPath.toString());
        }//end of try
        catch(TTKException ttkExp)
        {
            return this.processOnlineExceptions(request, mapping, ttkExp);
        }//end of catch(TTKException ttkExp)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp, ""));
        }//end of catch(Exception exp)
    }//end of doOnlineDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used set the appropriate links by calling setlinks method
     *
     * exceptions and finally it returns control based on the forward mapping.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doIpruDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception
            {
        try
        {
            log1.info("Inside doIpruDefault method");
        	StringBuffer sbfForwardPath=new StringBuffer();
            //call the setlinks method to set the appropriate link when link, sub link or tab is modified
        	setIpruLinks(request);
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
                                        getAttribute("UserSecurityProfile");

            //create the forward path for the next view.
            sbfForwardPath.append(userSecurityProfile.getSecurityProfile().getActiveLink()).append(".");
            sbfForwardPath.append(userSecurityProfile.getSecurityProfile().getActiveSubLink()).append(".");
            sbfForwardPath.append(userSecurityProfile.getSecurityProfile().getActiveTab());
            return mapping.findForward(sbfForwardPath.toString());
        }//end of try
        catch(TTKException ttkExp)
        {
            return this.processIpruExceptions(request, mapping, ttkExp);
        }//end of catch(TTKException ttkExp)
        catch(Exception exp)
        {
            return this.processIpruExceptions(request, mapping, new TTKException(exp, ""));
        }//end of catch(Exception exp)
    }//end of doIpruDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method activates/deactivates the links on the security profile XML object based
     * on the information from request object
     * @param request HttpServletRequest object which contains information about the links
     * @exception throws TTKException
     */
    protected void setLinks(HttpServletRequest request) throws TTKException
    {
        log1.debug("Inside setLinks.....");

        String strLink = TTKCommon.checkNull(request.getParameter("leftlink"));
        String strSubLink = TTKCommon.checkNull(request.getParameter("sublink"));
        String strTab = TTKCommon.checkNull(request.getParameter("tab"));
   
        if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        {
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
                                                            getAttribute("UserSecurityProfile");
            //to avoid calling twice from direct link and as well as specific action class
            if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
            {
                if(userSecurityProfile.getSecurityProfile().getActiveLink().equals(""))
                {
                    userSecurityProfile.getSecurityProfile().setDefaultActiveLink();
                }//end of if(userSecurityProfile.getSecurityProfile().getActiveLink().equals(""))
                else
                {
                    checkSourcePathPermission(request);
                    checkCurrentActionPermission(request);
                    checkTargetPathPermission(request);
                    userSecurityProfile.getSecurityProfile().setLinks(strLink,strSubLink,strTab);
                }//end of else
                request.setAttribute("invoked", "true");        //to avoid setlinks method to be called twice
            }//end of if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
        }//end of if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        else
        {
            TTKException ttkException=new TTKException();
            ttkException.setMessage("error.session");
            throw ttkException;
        }//end of else

        //if toolbar is in session update the visibality information for the present links
        if(request.getSession().getAttribute("toolbar")!=null)
        {
            StringBuffer sbfLinkPath=new StringBuffer();
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
                                                    getAttribute("UserSecurityProfile");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveLink()).append(".");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveSubLink()).append(".");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveTab());

            ((Toolbar)request.getSession().getAttribute("toolbar")).updateVisibility(sbfLinkPath.toString());
        }//end of if(request.getSession().getAttribute("toolbar")!=null)
   }//end of setLinks(HttpServletRequest request)
    
    /**
     * This method activates/deactivates the links on the security profile XML object based
     * on the information from request object
     * @param request HttpServletRequest object which contains information about the links
     * @exception throws TTKException
     */
    protected void setOnlineLinks(HttpServletRequest request) throws TTKException
    {
        log1.debug("Inside setOnlineLinks.....");

        String strLink = TTKCommon.checkNull(request.getParameter("leftlink"));
        String strSubLink = TTKCommon.checkNull(request.getParameter("sublink"));
        String strTab = TTKCommon.checkNull(request.getParameter("tab"));
        log1.debug("Left Link....."+strLink);
        log1.debug("SubLink......."+strSubLink);
        log1.debug("Tab..........."+strTab);
        if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        {
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
                                                            getAttribute("UserSecurityProfile");
            //to avoid calling twice from direct link and as well as specific action class
            if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
            {
                if(userSecurityProfile.getSecurityProfile().getActiveLink().equals(""))
                {
                    userSecurityProfile.getSecurityProfile().setDefaultActiveLink();
                }//end of if(userSecurityProfile.getSecurityProfile().getActiveLink().equals(""))
                else
                {
                    checkSourcePathPermission(request);
                    checkCurrentActionPermission(request);
                    checkTargetPathPermission(request);
                    userSecurityProfile.getSecurityProfile().setLinks(strLink,strSubLink,strTab);
                }//end of else
                request.setAttribute("invoked", "true");        //to avoid setlinks method to be called twice
            }//end of if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
        }//end of if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        else
        {
            TTKException ttkException=new TTKException();
            ttkException.setMessage("error.onlinesession");
            throw ttkException;
        }//end of else

        //if toolbar is in session update the visibality information for the present links
        if(request.getSession().getAttribute("toolbar")!=null)
        {
            StringBuffer sbfLinkPath=new StringBuffer();
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
                                                    getAttribute("UserSecurityProfile");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveLink()).append(".");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveSubLink()).append(".");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveTab());
            ((Toolbar)request.getSession().getAttribute("toolbar")).updateVisibility(sbfLinkPath.toString());
        }//end of if(request.getSession().getAttribute("toolbar")!=null)
   }//end of setOnlineLinks(HttpServletRequest request)
    
    /**
     * This method activates/deactivates the links on the security profile XML object based
     * on the information from request object
     * @param request HttpServletRequest object which contains information about the links
     * @exception throws TTKException
     */
    protected void setIpruLinks(HttpServletRequest request) throws TTKException
    {
        log1.info("Inside setIpruLinks.....");

        String strLink = TTKCommon.checkNull(request.getParameter("leftlink"));
        String strSubLink = TTKCommon.checkNull(request.getParameter("sublink"));
        String strTab = TTKCommon.checkNull(request.getParameter("tab"));
        log1.debug("Left Link....."+strLink);
        log1.debug("SubLink ......"+strSubLink);
        log1.debug("Tab..........."+strTab);
        if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        {
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
                                                            getAttribute("UserSecurityProfile");
            //to avoid calling twice from direct link and as well as specific action class
            if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
            {
                if(userSecurityProfile.getSecurityProfile().getActiveLink().equals(""))
                {
                    userSecurityProfile.getSecurityProfile().setDefaultActiveLink();
                }//end of if(userSecurityProfile.getSecurityProfile().getActiveLink().equals(""))
                else
                {
                    checkSourcePathPermission(request);
                    checkCurrentActionPermission(request);
                    checkTargetPathPermission(request);
                    userSecurityProfile.getSecurityProfile().setLinks(strLink,strSubLink,strTab);
                }//end of else
                request.setAttribute("invoked", "true");        //to avoid setlinks method to be called twice
            }//end of if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
        }//end of if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        else
        {
            TTKException ttkException=new TTKException();
            ttkException.setMessage("error.iprusession");
            throw ttkException;
        }//end of else

        //if toolbar is in session update the visibality information for the present links
        if(request.getSession().getAttribute("toolbar")!=null)
        {
            StringBuffer sbfLinkPath=new StringBuffer();
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
                                                    getAttribute("UserSecurityProfile");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveLink()).append(".");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveSubLink()).append(".");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveTab());

            ((Toolbar)request.getSession().getAttribute("toolbar")).updateVisibility(sbfLinkPath.toString());
        }//end of if(request.getSession().getAttribute("toolbar")!=null)
   }//end of setIpruLinks(HttpServletRequest request)

    /**
     * This method activates/deactivates the links on the security profile XML object based
     * on the information from request object
     *
     * @param request HttpServletRequest object which contains information about the links
     * @param strSwitchType String  identifier of enrollment or endorsement flow
     * @exception throws TTKException
     */
    protected void setLinks(HttpServletRequest request,String strSwitchType) throws TTKException
    {
        log1.debug("Inside PolicyAction setLinks.....");
        String strLink = TTKCommon.checkNull(request.getParameter("leftlink"));
        String strSubLink = TTKCommon.checkNull(request.getParameter("sublink"));
        String strTab = TTKCommon.checkNull(request.getParameter("tab"));
        log1.debug("Left Link....."+strLink);
        log1.debug("SubLink ......"+strSubLink);
        log1.debug("Tab..........."+strTab);

        if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        {
            //to avoid calling twice from direct link and as well as specific action class
            if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
            {
                UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
                getAttribute("UserSecurityProfile");
                //to avoid calling twice from direct link and as well as specific action class
                if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
                {
                    if(userSecurityProfile.getSecurityProfile().getActiveLink().equals(""))
                    {
                        userSecurityProfile.getSecurityProfile().setDefaultActiveLink();
                    }//end of if(userSecurityProfile.getSecurityProfile().getActiveLink().equals(""))
                    else
                    {
                        checkSourcePathPermission(request);
                        checkCurrentActionPermission(request);
                        checkTargetPathPermission(request);
                        userSecurityProfile.getSecurityProfile().setLinks(strLink,strSubLink,strTab);
                    }//end of else
                }//end of if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
            }//end if(TTKCommon.checkNull((String)request.getAttribute("invoked")).equals(""))
        }//end of if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        else
        {
            TTKException ttkException=new TTKException();
            ttkException.setMessage("error.session");
            throw ttkException;
        }//end of else
//      if toolbar is in session update the visibality information for the present links
        if(request.getSession().getAttribute("toolbar")!=null)
        {
            StringBuffer sbfLinkPath=new StringBuffer();
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
            getAttribute("UserSecurityProfile");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveLink()).append(".");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveSubLink()).append(".");
            sbfLinkPath.append(strSwitchType).append(".");
            sbfLinkPath.append(userSecurityProfile.getSecurityProfile().getActiveTab());
            ((Toolbar)request.getSession().getAttribute("toolbar")).updateVisibility(sbfLinkPath.toString());
        }//end of if(request.getSession().getAttribute("toolbar")!=null)
    }//end of setLinks(HttpServletRequest request,String strSwitchType)

    /**
     * This method will process the Exceptions raised in the Application and registers appropriate
     * error messages.
     *
     * @param request HttpServletRequest object which contains information about the links
     * @exception throws TTKException
     */
    protected ActionForward processExceptions(HttpServletRequest request, ActionMapping mapping,
            TTKException ttkExp) throws TTKException
    {
        ActionForward forward = null;
		HttpServletResponse response=null;	//Changes Added for Password Policy CR KOC 1235
        ActionForm form=null;	//Changes Added for Password Policy CR KOC 1235
   
        
        try
        {
        	 ActionMessages actionMessages = new ActionMessages();
        	if("error.dynamic.massage".equals(ttkExp.getMessage())){ 
        		String errorMsg="";
        		String dynaErrorMsg=ttkExp.getDynamicErrorDesc();
        		if(dynaErrorMsg!=null){
        			String[] strArrError= dynaErrorMsg.split("[@]");
        			if(strArrError!=null&&strArrError.length>2) errorMsg=strArrError[1];
        		}
        		ActionMessage actionMessage=new ActionMessage(ttkExp.getMessage(),errorMsg);
        		actionMessages.add("global.error",actionMessage);   
        	}
        	else{
            //getMessage will return like "user.create.error"
            ActionMessage actionMessage = new ActionMessage(ttkExp.getMessage());
            actionMessages.add("global.error",actionMessage);           
        	}
        	 saveErrors(request,actionMessages);
            //if session related error is found, forward to the re-login screen
            if(ttkExp.getMessage().contains("session")){
            	if(request.getSession().getAttribute("UserSecurityProfile") != null){
            		request.getSession().setAttribute("UserSecurityProfile",null);
            	}//end of if(request.getSession().getAttribute("UserSecurityProfile") != null)
				else	//Changes Added for Password Policy CR KOC 1235
            	{
            		doLogoff(mapping,form,request,response);
            	}//End changes for Password Policy CR KOC 1235
            	forward = mapping.findForward("relogin");   //return to login screen
            }//end of if(ttkExp.getMessage().contains("session"))
            else if(mapping.getInput() != null)
            {
                //get the input information from the mapping if it exists
                forward = new ActionForward(mapping.getInput());
            }//end else if(mapping.getInput() != null)
            else
            {
                forward = mapping.findForward("failure");   //get the path information from the mapping
            }//end else
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error.process.exception");
        }//end of catch(Exception exp)
        return forward;
    }//end of processExceptions(HttpServletRequest request, ActionMapping mapping, TTKException ttkExp)
    
	//Changes Added for Password Policy CR KOC 1235
    public ActionForward doLogoff(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		  	String ipAddress=request.getRemoteHost();
    		  	SecurityManager securityManagerObject = this.getSecurityManagerObject();
          		int iResult =securityManagerObject.userSessionLogoutIPAddress(ipAddress);
          		
            return (mapping.findForward("relogin"));
    	}//end of try
    	catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"login"));
		}//end of catch(Exception exp)
    }//end of doLogoff(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    //End changes for Password Policy CR KOC 1235

    /**
     * This method will process the Exceptions raised in the Application and registers appropriate
     * error messages.
     *
     * @param request HttpServletRequest object which contains information about the links
     * @exception throws TTKException
     */
    protected ActionForward processOnlineExceptions(HttpServletRequest request, ActionMapping mapping,
            TTKException ttkExp) throws TTKException
    {
        ActionForward forward = null;
        log1.info("Inside processOnlineExceptions....");
        try
        {
            ActionMessages actionMessages = new ActionMessages();
            //getMessage will return like "user.create.error"
            ActionMessage actionMessage = new ActionMessage(ttkExp.getMessage());
            actionMessages.add("global.error",actionMessage);
            saveErrors(request,actionMessages);
            log1.info("Online Error Msg : "+ttkExp.getMessage());
            //if session related error is found, forward to the re-login screen
            if(ttkExp.getMessage().contains("onlinesession")){
            	request.setAttribute("SessionExpired", "SessionExpired");
            	if(request.getSession().getAttribute("UserSecurityProfile") != null){
            		request.getSession().setAttribute("UserSecurityProfile",null);
            	}//end of if(request.getSession().getAttribute("UserSecurityProfile") != null)
            	forward = mapping.findForward("reonlinelogin");   //return to login screen
            }//end of if(ttkExp.getMessage().contains("session"))
            else if(mapping.getInput() != null)
            {
                //get the input information from the mapping if it exists
                forward = new ActionForward(mapping.getInput());
            }//end else if(mapping.getInput() != null)
            else
            {
                forward = mapping.findForward("failure");   //get the path information from the mapping
            }//end else
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error.process.exception");
        }//end of catch(Exception exp)
        return forward;
    }//end of processOnlineExceptions(HttpServletRequest request, ActionMapping mapping, TTKException ttkExp)
    /**
     * This method will process the Exceptions raised in the Application and registers appropriate
     * error messages.
     *
     * @param request HttpServletRequest object which contains information about the links
     * @exception throws TTKException
     */
    protected ActionForward processBrokerExceptions(HttpServletRequest request, ActionMapping mapping,
            TTKException ttkExp) throws TTKException
    {
        ActionForward forward = null;
        log1.info("Inside processBrokerExceptions....");
        try
        {
            ActionMessages actionMessages = new ActionMessages();
            //getMessage will return like "user.create.error"
            ActionMessage actionMessage = new ActionMessage(ttkExp.getMessage());
            actionMessages.add("global.error",actionMessage);
            saveErrors(request,actionMessages);
            log1.info("Online Error Msg : "+ttkExp.getMessage());
            //if session related error is found, forward to the re-login screen
            if(ttkExp.getMessage().contains("onlinesession")){
            	request.setAttribute("SessionExpired", "OBR");
            	if(request.getSession().getAttribute("UserSecurityProfile") != null){
            		request.getSession().setAttribute("UserSecurityProfile",null);
            	}//end of if(request.getSession().getAttribute("UserSecurityProfile") != null)
            	forward = mapping.findForward("reonlinelogin");   //return to login screen
            }//end of if(ttkExp.getMessage().contains("session"))
            else if(mapping.getInput() != null)
            {
                //get the input information from the mapping if it exists
                forward = new ActionForward(mapping.getInput());
            }//end else if(mapping.getInput() != null)
            else
            {
                forward = mapping.findForward("failure");   //get the path information from the mapping
            }//end else
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error.process.exception");
        }//end of catch(Exception exp)
        return forward;
    }//end of processBrokerExceptions(HttpServletRequest request, ActionMapping mapping, TTKException ttkExp)
    
    /**
     * This method will process the Exceptions raised in the Application and registers appropriate
     * error messages.
     *
     * @param request HttpServletRequest object which contains information about the links
     * @exception throws TTKException
     */
    protected ActionForward processIpruExceptions(HttpServletRequest request, ActionMapping mapping,
            TTKException ttkExp) throws TTKException
    {
        ActionForward forward = null;
        log1.info("Inside processIpruExceptions....");
        try
        {

            ActionMessages actionMessages = new ActionMessages();
            //getMessage will return like "user.create.error"
            ActionMessage actionMessage = new ActionMessage(ttkExp.getMessage());
            actionMessages.add("global.error",actionMessage);
            saveErrors(request,actionMessages);
            log1.info("Online Error Msg : "+ttkExp.getMessage());
            //if session related error is found, forward to the re-login screen
            if(ttkExp.getMessage().contains("iprusession")){
            	if(request.getSession().getAttribute("UserSecurityProfile") != null){
            		request.getSession().setAttribute("UserSecurityProfile",null);
            	}//end of if(request.getSession().getAttribute("UserSecurityProfile") != null)
            	forward = mapping.findForward("reiprulogin");   //return to login screen
            }//end of if(ttkExp.getMessage().contains("session"))
            else if(mapping.getInput() != null)
            {
                //get the input information from the mapping if it exists
                forward = new ActionForward(mapping.getInput());
            }//end else if(mapping.getInput() != null)
            else
            {
                forward = mapping.findForward("failure");   //get the path information from the mapping
            }//end else
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "error.process.exception");
        }//end of catch(Exception exp)
        return forward;
    }//end of processIpruExceptions(HttpServletRequest request, ActionMapping mapping, TTKException ttkExp)

    /**
     * This method checks the permission for the request, against the ACL defined
     * in the security Profile of User.
     *
     * @param strACL String the requested ACL path
     * @param request HttpServletRequest the request object
     * @return boolean true/false, the access to the screen is to be denied or not
     */
    protected boolean isAuthorized(String strACL, HttpServletRequest request) throws TTKException
    {
        //check for the permission in the UserSecurityProfile object for the path
        if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        {
            return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).
                        getSecurityProfile().isAuthorized(strACL);
        }//end of  if(request.getSession().getAttribute("UserSecurityProfile")!=null)
        else
            return false;
    }//end of isAuthorized(String strPath, HttpServletRequest request)

    /**
     * This method returns the appropriate forward object after checking for previleges
     * If the request is unauthorized, an appropriate error message is populated
     *
     * @param strForwardName String the forward name
     * @param mapping ActionMapping the action mapping object
     * @param request HttpServletRequest the request object
     * @return ActionForward the success or failure path for the request
     */
    protected ActionForward getForward(String strForwardName,ActionMapping mapping,
            HttpServletRequest request) throws TTKException {
        if(isAuthorized(mapping.findForward(strForwardName).getPath(), request))
        {
            return mapping.findForward(strForwardName);
        }//end of if
        else
        {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("error.security.unauthorized");
            actionMessages.add("global.error",actionMessage);
            saveErrors(request,actionMessages);
            //return mapping.findForward("failure");
            TTKException ttkException=new TTKException();
            ttkException.setMessage("error.security.unauthorized");
            throw ttkException;
        }//end of else
    }//end of getForward(String forwardName, HttpServletRequest request)

    /**
     * This method will check whether user has the Permission for the Entry Level screen
     * if the flow has multiple level of children at tab level.
     *
     * @param request HttpServletRequest current request object
     * @throws TTKException if any error occures
     */
    protected void checkSourcePathPermission(HttpServletRequest request) throws TTKException
    {
        boolean blnSourcePathPermission=true;
        String strSourcePathACL=null;

        strSourcePathACL=TTKCommon.getActiveLink(request)+"."+TTKCommon.getActiveSubLink(request)
        +"."+TTKCommon.getActiveTab(request);

        if(!strSourcePathACL.equals(""))
        {
            blnSourcePathPermission=((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).
                                            getSecurityProfile().isAuthorized(strSourcePathACL);
        }//end of if(!strSourcePathACL.equals(""))

        if(blnSourcePathPermission==false)
        {
            log1.fatal("Permission::Serious security violation detected, Details :-"+
                    " User Id - "+((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getUSER_ID()+
                    " is trying to access from "+strSourcePathACL+", which is not allowed for his role");

            TTKException ttkException = new TTKException();
            ttkException.setMessage("error.security.unauthorized");
            throw ttkException;
        }//end of if(blnSourcePathPermission==false)
    }//end of checkSourcePathPermission(HttpServletRequest request)

    /**
     * This method will check the permission for specific actions like add,delete
     * and edit. If user's role not has the respective permission it will throw Exception
     * with the appropriate error message.
     *
     * @param request HttpServletRequest current request object
     * @throws TTKException if any exception occures
     */
    protected void checkCurrentActionPermission(HttpServletRequest request) throws TTKException
    {
        boolean blnActionPermission=true;
        String strMode=TTKCommon.checkNull(request.getParameter("mode"));
        if(strMode.contains("doAdd"))
        {
            blnActionPermission=TTKCommon.isAuthorized(request,"Add");
        }//end of if(strMode.contains("doAdd"))
        else if(strMode.contains("doDelete"))
        {
            blnActionPermission=TTKCommon.isAuthorized(request,"Delete");
        }//end of else if(strMode.contains("doDelete"))
        else if(strMode.contains("doCancel"))
        {
            blnActionPermission=TTKCommon.isAuthorized(request,"Cancel");
        }//end of eelse if(strMode.contains("doCancel"))
        else if(strMode.contains("doSave"))
        {
            blnActionPermission=TTKCommon.isAuthorized(request,"Edit");
        }//end of else if(strMode.contains("doSave"))
        if(blnActionPermission==false)
        {
            log1.fatal("Action Permission: Serious security violation detected, Details :-"+
                    " User Id - "+((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getUSER_ID()+
                    " is trying to access "+strMode+" method of "+ this.getClass()+", which is not allowed for his role");

            TTKException ttkException = new TTKException();
            ttkException.setMessage("error.security.permissiondenied");
            throw ttkException;
        }//end of if(blnActionPermission==false)
    }//end of checkSourceAction(HttpServletRequest request)

    /**
     * This method will check whether user has the Permission for the Target/Next View of the flow
     * If user not haves the respective permission it will throw Exception with the appropriate error message.
     * @param request HttpServletRequest current request object
     * @throws TTKException if any error occures
     */
    protected void checkTargetPathPermission(HttpServletRequest request) throws TTKException
    {
        boolean blnTargetPathPermission=true;
        String strLink = TTKCommon.checkNull(request.getParameter("leftlink"));
        String strSubLink = TTKCommon.checkNull(request.getParameter("sublink"));
        String strTab = TTKCommon.checkNull(request.getParameter("tab"));
        String strChild = "";

        if(request.getAttribute("child")!=null)
        {
            strChild=TTKCommon.checkNull((String)request.getAttribute("child"));
        }//end of if(request.getAttribute("child")!=null)
        else
        {
            strChild=TTKCommon.checkNull(request.getParameter("child"));
        }//end of else
        String strTargetACL=((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).
                                    getSecurityProfile().getACL(strLink,strSubLink,strTab,strChild);

        if(!strTargetACL.equals(""))
        {
            blnTargetPathPermission=((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).
                                                    getSecurityProfile().isAuthorized(strTargetACL);
        }//end of if(!strTargetACL.equals(""))

        if(blnTargetPathPermission==false)
        {
            log1.fatal("Target Permission: Serious security violation detected, Details :-"+
                    " User Id - "+((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getUSER_ID()+
                    " is trying to access "+strTargetACL+", which is not allowed for his role");
            request.getSession().setAttribute("dhpoFlagYN","N");
            TTKException ttkException = new TTKException();
            ttkException.setMessage("error.security.unauthorized");
            throw ttkException;
        }//end of if(blnTargetPathPermission==false)
    }//end of checkTargetAction(HttpServletRequest request)

	//Changes Added for Password Policy CR KOC 1235
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
  //End changes for Password Policy CR KOC 1235

}//end of class TTKAction

