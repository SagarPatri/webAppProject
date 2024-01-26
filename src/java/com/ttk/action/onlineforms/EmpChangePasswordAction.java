/**
 * @ (#) EmpChangePasswordAction.java Feb 19th, 2008
 * Project       : TTK HealthCare Services
 * File          : EmpChangePasswordAction.java
 * Author        : Chandrasekaran J
 * Company       : Span Systems Corporation
 * Date Created  : Feb 19th, 2008
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
import com.ttk.action.tree.TreeData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.usermanagement.PasswordVO;

import formdef.plugin.util.FormUtils;

public class EmpChangePasswordAction extends TTKAction
{
	private static Logger log = Logger.getLogger(EmpChangePasswordAction.class);    
	// Action mapping forwards.
	private static final String strChangePassword="changepassword";  
	private static final String strOnlinePwd="onlinechangepwd";  
	private static final String strOnlineClosePwd="onlinemember";
	
	/**
	 * This method is used to get the information of the employee.
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
			log.debug("Inside the doDefault method of EmpChangePasswordAction");
			setOnlineLinks(request);
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			DynaActionForm frmChangePassword= (DynaActionForm)form;
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			PasswordVO passwordVO=new PasswordVO();
			Long lngPolicyGroupSeqID=null;
			//get the Policy group Seqid from the treedata if coming here by selecting the object from tree
			if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			{
				frmChangePassword.initialize(mapping); //initialize the form bean
				MemberVO memberVO=(MemberVO)treeData.getSelectedObject((String)request.
						getParameter("selectedroot"),null);
				lngPolicyGroupSeqID=memberVO.getPolicyGroupSeqID();
			}//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			passwordVO=onlineAccessObject.getEmployeePasswordInfo(lngPolicyGroupSeqID);
			frmChangePassword = (DynaActionForm)FormUtils.setFormValues("frmChangePassword",passwordVO,	this,mapping,request);
			frmChangePassword.set("PolicyGroupSeqID",lngPolicyGroupSeqID);
			request.getSession().setAttribute("frmChangePassword",frmChangePassword);
			return this.getForward(strOnlinePwd, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strChangePassword));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to change the employee password
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeUsers(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try
		{
			setOnlineLinks(request);
			log.debug("Inside EmpChangePasswordAction doChangeUsers");
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			DynaActionForm frmChangePassword= (DynaActionForm)form;
			PasswordVO passwordVO=new PasswordVO();
			Long lngPolicyGroupSeqID=null;
			String strPasswd = null;
			lngPolicyGroupSeqID=(Long)frmChangePassword.get("PolicyGroupSeqID");
			passwordVO=(PasswordVO)FormUtils.getFormValues(frmChangePassword,this,mapping, request);
			strPasswd =onlineAccessObject.resetEmployeePassword(lngPolicyGroupSeqID,TTKCommon.getUserSeqId(request));
			if(strPasswd!=null)
			{
				passwordVO.setCurrentPwd(strPasswd);
				frmChangePassword = (DynaActionForm)FormUtils.setFormValues("frmChangePassword",passwordVO,	this,mapping,request);
				request.setAttribute("updated","message.changedSuccessfully");
			}//end of if(iCount>0)
			request.getSession().setAttribute("frmChangePassword",frmChangePassword);
			return this.getForward(strOnlinePwd, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strChangePassword));
		}//end of catch(Exception exp)
	}// end of doChangeUsers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doClose method of EmpChangePasswordAction");
			setOnlineLinks(request);
			return this.getForward(strOnlineClosePwd, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strChangePassword));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the OnlineAccessManager session object for invoking methods on it.
	 * @return OnlineAccessManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineAccessManager() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
			}//end ofif(onlineAccessManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strChangePassword);
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()
}//end of EmpChangePasswordAction()
