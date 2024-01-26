/**
 * @ (#) OnlinePersonalDetailsAction.java Feb 20th, 2008
 * Project       : TTK HealthCare Services
 * File          : OnlinePersonalDetailsAction.java
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
import com.ttk.business.usermanagement.UserManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.usermanagement.ContactVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;

import formdef.plugin.util.FormUtils;

public class OnlinePersonalDetailsAction extends TTKAction
{
	private static Logger log = Logger.getLogger(OnlinePersonalDetailsAction.class);
	// Action mapping forwards.
	private static final String strLoginPersonalDetails="loginpersonaldetails";
	//Exception Message Identifier
	private static final String strUserExp="user";
	
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
			log.debug("Inside the doDefault method of OnlinePersonalDetailsAction");
			setOnlineLinks(request);
			PersonalInfoVO personalInfoVO = new PersonalInfoVO();
			ContactVO userContactVO=null;
			UserManager userManagerObject=this.getUserManagerObject();
			DynaActionForm formPersonalDetails = (DynaActionForm)form;
			userContactVO=userManagerObject.getContact(TTKCommon.getUserSeqId(request));            	
			personalInfoVO=userContactVO.getPersonalInfo();
			formPersonalDetails = (DynaActionForm)FormUtils.setFormValues(
														"frmPersonalDetails",personalInfoVO, this, mapping, request);
			request.setAttribute("frmPersonalDetails",formPersonalDetails);
			return this.getForward(strLoginPersonalDetails, mapping, request);
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
			log.debug("Inside the doSave method of OnlinePersonalDetailsAction");
			setOnlineLinks(request);
			PersonalInfoVO personalInfoVO = new PersonalInfoVO();
			ContactVO userContactVO=null;
			UserManager userManagerObject=this.getUserManagerObject();
			DynaActionForm formPersonalDetails = (DynaActionForm)form;
			userContactVO=new ContactVO();
			personalInfoVO=(PersonalInfoVO)FormUtils.getFormValues(
													formPersonalDetails, "frmPersonalDetails",this, mapping, request);
			userContactVO.setPersonalInfo(personalInfoVO); 
			userContactVO.setContactSeqID(TTKCommon.getUserSeqId(request));
			userContactVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
			long lResult=userManagerObject.saveContact(userContactVO, "MYPROFILE");
			if(lResult > 0)
			{
				request.setAttribute("updated","message.savedSuccessfully");
				userContactVO=userManagerObject.getContact(TTKCommon.getUserSeqId(request));     
				personalInfoVO=userContactVO.getPersonalInfo();
			}//end of if(lResult > 0)
			formPersonalDetails = (DynaActionForm)FormUtils.setFormValues(
														"frmPersonalDetails",personalInfoVO, this, mapping, request);
			request.setAttribute("frmPersonalDetails",formPersonalDetails);          					
			return this.getForward(strLoginPersonalDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strUserExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.debug("Inside the doReset method of OnlinePersonalDetailsAction");
		setOnlineLinks(request);
		return doDefault(mapping,form,request,response);
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			}//end of if(userManager == null)
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, strUserExp);
		}//end of catch
		return userManager;
	}//end getUserManagerObject()
}//end of OnlinePersonalDetailsAction
