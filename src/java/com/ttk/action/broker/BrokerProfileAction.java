/**
 * @ (#) BrokerProfileAction.java jan 07, 2016
 * Project      : TTK HealthCare Services
 * File         : BrokerProfileAction.java
 * Author       : Nagababu K
 * Company      : RCS
 * Date Created : jan 07, 2016
 *
 * @author       : Nagababu K
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.broker;

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
import com.ttk.dto.usermanagement.PasswordVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for Updating the Personal Details of the user.
 */
public class BrokerProfileAction extends TTKAction 
{
	private static Logger log = Logger.getLogger(BrokerProfileAction.class);
	// Action mapping forwards.
	private static final String strPersonalDetails="personaldetails";
	private static final String strChangePassword="changePassword";
	
	//Exception Message Identifier
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
			log.info("Inside the doDefault method of BrokerProfileAction");
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
				return this.getForward(strPersonalDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPersonalDetails));
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
			log.debug("Inside the doSave method of BrokerProfileAction");
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
				return this.getForward(strPersonalDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPersonalDetails));
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
		try{
			setOnlineLinks(request);
			log.debug("Inside the doReset method of BrokerProfileAction");
			setOnlineLinks(request);
			return doDefault(mapping,form,request,response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPersonalDetails));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doSaveChangePassward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSaveChangePassward method of BrokerProfileAction");
			setOnlineLinks(request);
			UserManager userManagerObject=this.getUserManagerObject();
			int iResult = 0;
			DynaActionForm frmPassword = (DynaActionForm)form;
			PasswordVO passwordVO = new PasswordVO();
			passwordVO = (PasswordVO) FormUtils.getFormValues(frmPassword, this, mapping, request);
			passwordVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			passwordVO.setUserID(TTKCommon.getUserID(request));
			iResult=userManagerObject.changePassword(passwordVO);
			if(iResult > 0)
			{
				request.setAttribute("updated","message.changedSuccessfully");
				frmPassword.initialize(mapping);
			}//end of if(iResult > 0)
			return this.getForward(strChangePassword, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strChangePassword));
		}//end of catch(Exception exp)
	}//end of doSaveChangePassward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doChangePassward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doChangePassward method of BrokerProfileAction");
			setOnlineLinks(request);
			DynaActionForm frmPassword = (DynaActionForm)form;
				frmPassword.initialize(mapping);
			return this.getForward(strChangePassword, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strChangePassword));
		}//end of catch(Exception exp)
	}//end of doChangePassward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
     * This method is used to initialize the search grid.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the goBack method of BrokerAction");
            setOnlineLinks(request);            
            DynaActionForm frmBroDashBoard =(DynaActionForm)form;
            frmBroDashBoard.initialize(mapping);     //reset the form data
            String strFarward="Broker.Home.DashBoard";
            return mapping.findForward(strFarward);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPersonalDetails));
        }//end of catch(Exception exp)
    }//end of goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
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
			throw new TTKException(exp, "user");
		}//end of catch
		return userManager;
	}//end getUserManagerObject()
}// end of BrokerProfileAction.java