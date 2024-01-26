package com.ttk.action.enrollment;


import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.myprofile.PersonalDetailsAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.usermanagement.PasswordVO;


import formdef.plugin.util.FormUtils;

public class ChangePasswordAction extends TTKAction
{
	private static Logger log = Logger.getLogger(PersonalDetailsAction.class);    
	// Action mapping forwards.
	private static final String strChangePassword="changepassword";  
	private static final String strChangePwd="changepwd"; 
	private static final String strClosepassword="corpMember"; 
	private static final String strClosePwd="corpMemberInfo";
	//For sub link name
	private static final String strEnrollment="Enrollment";
	private static final String strAccountInfo="Account Info";
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
			log.debug("Inside the doDefault method of ChangePasswordAction");
			//setLinks(request);
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strForwardPath="";
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			if(strActiveLink.equals(strEnrollment))
			{
				String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
				this.setLinks(request,strSwitchType);
				strForwardPath=strChangePassword;
			}//end of if(strActiveLink.equals(strEnrollment))
			else if(strActiveLink.equals(strAccountInfo))
			{
				setLinks(request);
				strForwardPath=strChangePwd;
			}//end of else if(strActiveLink.equals(strAccountInfo))
			MemberManager memberManager=this.getMemberManager();
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
			passwordVO=memberManager.getEmployeePasswordInfo(lngPolicyGroupSeqID);
			frmChangePassword = (DynaActionForm)FormUtils.setFormValues("frmChangePassword",passwordVO,	this,mapping,request);
			frmChangePassword.set("PolicyGroupSeqID",lngPolicyGroupSeqID);
			request.getSession().setAttribute("frmChangePassword",frmChangePassword);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strChangePassword));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to change the Assign Users
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
			//setLinks(request);
			log.debug("Inside ChangePasswordAction doChangeUsers");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strForwardPath="";
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			if(strActiveLink.equals(strEnrollment))
			{
				String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
				this.setLinks(request,strSwitchType);
				strForwardPath=strChangePassword;
			}//end of if(strActiveLink.equals(strEnrollment))
			else if(strActiveLink.equals(strAccountInfo))
			{
				setLinks(request);
				strForwardPath=strChangePwd;
			}//end of else if(strActiveLink.equals(strAccountInfo))
			MemberManager memberManager=this.getMemberManager();
			DynaActionForm frmChangePassword= (DynaActionForm)form;
			PasswordVO passwordVO=new PasswordVO();
			Long lngPolicyGroupSeqID=null;
			lngPolicyGroupSeqID=(Long)frmChangePassword.get("PolicyGroupSeqID");
			passwordVO=(PasswordVO)FormUtils.getFormValues(frmChangePassword,this,mapping, request);
			int iCount=memberManager.resetEmployeePassword(lngPolicyGroupSeqID,TTKCommon.getUserSeqId(request));
			if(iCount>0)
			{
				passwordVO=memberManager.getEmployeePasswordInfo(lngPolicyGroupSeqID);
				frmChangePassword = (DynaActionForm)FormUtils.setFormValues("frmChangePassword",passwordVO,	this,mapping,request);
				frmChangePassword.set("PolicyGroupSeqID",lngPolicyGroupSeqID);
				request.setAttribute("updated","message.changedSuccessfully");
			}//end of if(iCount>0)
			request.getSession().setAttribute("frmChangePassword",frmChangePassword);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strChangePassword));
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
			log.debug("Inside the doClose method of ChangePasswordAction");
			String strActiveLink=TTKCommon.getActiveLink(request);
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");  
			String strForwardPath="";
			if(strActiveLink.equals(strEnrollment))
			{
				String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
				this.setLinks(request,strSwitchType);
				strForwardPath=strClosepassword;
			}//end of if(strActiveLink.equals(strEnrollment))
			else if(strActiveLink.equals(strAccountInfo))
			{
				setLinks(request);
				strForwardPath=strClosePwd;
			}//end ofelse if(strActiveLink.equals(strAccountInfo))
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClosepassword));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the MemberManager session object for invoking methods on it.
	 * @return MemberManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MemberManager getMemberManager() throws TTKException
	{
		MemberManager memberManager = null;
		try
		{
			if(memberManager == null)
			{
				InitialContext ctx = new InitialContext();
				memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "memberdetail");
		}//end of catch
		return memberManager;
	}//end getMemberManager()
}//end of ChangePasswordAction()
