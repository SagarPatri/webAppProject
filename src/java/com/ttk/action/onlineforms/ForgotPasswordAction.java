package com.ttk.action.onlineforms;

import java.util.ArrayList;
import java.util.Date;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import common.Logger;

public class ForgotPasswordAction extends TTKAction{
	
	public static final Logger log = Logger.getLogger(ForgotPasswordAction.class); 
	public static final String strforgotPassword ="forgotPassword";
	public static final String strSaveFrgtPswd ="savefrgtPswd";
	
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
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.info("Inside ForgotPassword doDefault");
            String strGroupID = request.getParameter("GroupID");
            String strPolicyNo = request.getParameter("PolicyNo");
            String strUserID = request.getParameter("UserId");
            //Added by Rishi Sharma
            String ptrEmpaneId = request.getParameter("ptrEmpaneId");
            String ptrUserId = request.getParameter("ptrUserId");
            
            DynaActionForm frmForgotPassword = (DynaActionForm)form;
//Modification as per IBM CHNAGE REQUEST
            String strLoginType = request.getParameter("LoginType");
            frmForgotPassword.set("loginType",strLoginType);
            frmForgotPassword.set("groupId",strGroupID);
            frmForgotPassword.set("corpPolicyNo",strPolicyNo);
            frmForgotPassword.set("userId",strUserID);
            //Added by Rishi Sharma
            frmForgotPassword.set("ptrEmpaneId",ptrEmpaneId);
            frmForgotPassword.set("ptrUserId",ptrUserId);
            request.getSession().setAttribute("frmForgotPassword", frmForgotPassword);
            return mapping.findForward(strforgotPassword);            
        }//end of try
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccesslogin"));
        }//end of catch(Exception exp)
    }//end of doLogin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
	/**
     * This method is used Save the User Password.
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
            log.info("Inside ForgotPassword doSave");
            DynaActionForm frmForgotPassword = (DynaActionForm)form;
            OnlineAccessManager onlineAccessManager=this.getOnlineAccessManagerObject();
            String strGroupId = "";
            String strCorpPolicyNo = "";
            String strUserId = "";
//Added as per IBM CR
        	
        	 Date dtDateOfBirth=null;
        	Date dtDateOfJoining=null;
        	String strLoginType="";
        	
            String strPassWord="";//as per Hospital Login 
            ArrayList<Object> alForgotPass =null;
            
        	strLoginType=request.getParameter("loginType");
            frmForgotPassword.set("loginType",strLoginType);
        	//Added as per IBM CR

            if(strLoginType.equalsIgnoreCase("HOS"))   {
            	strGroupId = frmForgotPassword.getString("groupId");
            	strUserId = frmForgotPassword.getString("userId");
            	//Added as per IBM CR
            }
            else   if(strLoginType.equalsIgnoreCase("PTR"))   {
            	strGroupId = frmForgotPassword.getString("groupId");
            	strUserId = frmForgotPassword.getString("userId");
            	//Added as per IBM CR
            }
            else{

            log.info("Grrrrrrrrrrrid :"+frmForgotPassword.getString("groupId"));
            if(frmForgotPassword.getString("groupId")==null || frmForgotPassword.getString("groupId").equalsIgnoreCase("") )
            {
				strGroupId = request.getParameter("groupId");
            	strCorpPolicyNo = request.getParameter("corpPolicyNo");
            	strUserId = request.getParameter("userId");
//Added as per IBM CR
            		dtDateOfBirth=TTKCommon.getUtilDate(request.getParameter("dateOfBirth"));
            	dtDateOfJoining=TTKCommon.getUtilDate(request.getParameter("dateOfJoining"));
//Added as per IBM CR
            }//end of if(frmForgotPassword.getString("groupId")==null || frmForgotPassword.getString("groupId").equalsIgnoreCase(""))
            else
            {
            	strGroupId = frmForgotPassword.getString("groupId");
            	strCorpPolicyNo = frmForgotPassword.getString("corpPolicyNo");
            	strUserId = frmForgotPassword.getString("userId");
//Added as per IBM CR
            	dtDateOfBirth=TTKCommon.getUtilDate((String)frmForgotPassword.get("dateOfBirth"));
            	dtDateOfJoining=TTKCommon.getUtilDate((String)frmForgotPassword.get("dateOfJoining"));

            	//Added as per IBM CR
            }//end else       
}//end of else part of  if(strLoginType.equalsIgnoreCase("HOS")) 
                     
if(strLoginType.equalsIgnoreCase("HOS"))   {
            	alForgotPass=new ArrayList<Object>();
            	alForgotPass.add(strGroupId);
            	alForgotPass.add(strUserId);
             strPassWord = onlineAccessManager.saveHospitalForgotPassword(alForgotPass);
            }
else if(strLoginType.equalsIgnoreCase("PTR")){
	alForgotPass=new ArrayList<Object>();
	alForgotPass.add(strGroupId);
	alForgotPass.add(strUserId);
		strPassWord = onlineAccessManager.savePartnerForgotPassword(alForgotPass);
}
            else{   
             alForgotPass = new ArrayList<Object>();
            alForgotPass.add(strGroupId);
            alForgotPass.add(strCorpPolicyNo);
            alForgotPass.add(strUserId);
//Added as per IBM CR
      alForgotPass.add(dtDateOfBirth);
            alForgotPass.add(dtDateOfJoining);
            alForgotPass.add(strLoginType);//kocbroker
            onlineAccessManager = this.getOnlineAccessManagerObject();
             strPassWord = onlineAccessManager.saveForgotPassword(alForgotPass);
            log.info("strPassWord :"+strPassWord);
}
           if(TTKCommon.checkNull(strPassWord) !="")
            {
            	log.info("inside if block :"+strPassWord);
            	if(strLoginType.equalsIgnoreCase("IBMOCO") || strLoginType.equalsIgnoreCase("IBMOHR"))
            	{
            		request.setAttribute("forgotPassibm","message.forgotPasswordInfo");
            	}
            	else if(strLoginType.equalsIgnoreCase("HOS"))//kochos
            	{
            		request.setAttribute("forgotPassHos","message.forgotPasswordInfo");
            	}
                                
            	else if(strLoginType.equalsIgnoreCase("OBR"))//kocbroker
            	{
            		request.setAttribute("forgotPassbro","message.forgotPasswordInfo");
            	}else 
            	{
            		request.setAttribute("forgotPass","message.forgotPasswordInfo");
            	}
            }//end of if(strPassWord.equalsIgnoreCase(""))
            else{
            	log.info("inside else block :"+strPassWord);            	
            	request.setAttribute("display","message.forgotPasswordInfo");
            }//end of else
            log.info("Password send to your mail id");
            return mapping.findForward(strSaveFrgtPswd);
        }//end of try
        catch(TTKException expTTK)
        {
        	return this.processOnlineExceptions(request, mapping, new TTKException(expTTK,"onlineaccesslogin"));   	
        	
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
        	return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccesslogin"));
        }//end of catch(Exception exp)
    }//end of doLogin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * Returns the OnlineAccessManager session object for invoking methods on it.
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
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "product");
        }//end of catch
        return onlineAccessManager;
    }//end of getOnlineAccessManagerObject()

}//end of ForgotPasswordAction
