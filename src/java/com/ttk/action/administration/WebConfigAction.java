/**
 * @ (#) WebConfigAction.java Dec 24, 2007
 * Project      : TTK HealthCare Services
 * File         : WebConfigAction.java
 * Author       : Yogesh S.C
 * Company      : Span Systems Corporation
 * Date Created : Dec 24, 2007
 *
 * @author       : Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.administration;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.WebConfigInfoVO;
import com.ttk.dto.enrollment.PolicyDetailVO;

import formdef.plugin.util.FormUtils;

public class WebConfigAction extends TTKAction{

	private static Logger log = Logger.getLogger(WebConfigAction.class);

	//	Forwards
	private static final String strWebLogin = "weblogin";
	private static final String strWebConfigDetails="webconfigdetails";

	//Exception Message Identifier
	private static final String strWebConfig="webconfig";

	/**
	 * This method is used to display the configuration information.
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
			log.debug("Inside the doDefault method of WebConfigAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManagerObject();
			if(TTKCommon.getWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.policy.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request)==null)
			else{
				Long lngProductPolicySeqId=TTKCommon.getWebBoardId(request);
				//get the Policy details from the Dao object
				PolicyDetailVO policyDetailVO= productpolicyObject.getPolicyDetail(lngProductPolicySeqId,
						TTKCommon.getUserSeqId(request));
				request.getSession().setAttribute("policyDetailVO",policyDetailVO);
			}//end of else
			return this.getForward(strWebLogin, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWebConfig));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to forward to web login screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doWebLogin(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doWebLogin method of WebLoginAction");
			setLinks(request);
			//build the caption
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
			StringBuffer sbfCaption1= new StringBuffer();
			sbfCaption1.append("Web Login - ").append(sbfCaption);
			ProductPolicyManager productPolicyManager = this.getProductPolicyManagerObject();
			WebConfigInfoVO webConfigInfoVO = productPolicyManager.getWebConfigInfo(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
			if(webConfigInfoVO.getConfigSeqID()==null||webConfigInfoVO.getConfigSeqID().equals(""))
			{
				webConfigInfoVO.setSoftCopyGenTypeID("PSN");
				webConfigInfoVO.setAddSumInsuredGenTypeID("WSN");
				webConfigInfoVO.setGroupCntCancelGenTypeID("WCN");
				webConfigInfoVO.setOnlineAssTypeID("OAI");
				webConfigInfoVO.setRatingGeneralTypeID("ORN");
				//Changes as per KOC 1159 and 1160
				webConfigInfoVO.setRelshipCombintnTypeID("RCN");
				////Changes as per KOC 1159 and 1160
				//Added for IBM....KOC-1216
				webConfigInfoVO.setOptGenTypeID("WSN"); // Default it optout should be not allowed config-added by Praveen
				//Ended
				webConfigInfoVO.setWellnessAccessTypeID("WAI");// added for koc 1349

			}//end of if(webConfigInfoVO.getConfigSeqID()==null||webConfigInfoVO.getConfigSeqID().equals(""))
			webConfigInfoVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			DynaActionForm frmWebConfig = (DynaActionForm)FormUtils.setFormValues("frmWebConfig",
					webConfigInfoVO, this, mapping, request);
			if(webConfigInfoVO.getPolicySumInsured()!=null){
				frmWebConfig.set("policySumInsured",webConfigInfoVO.getPolicySumInsured().toString());
			}//end of if(webConfigInfoVO.getPolicySumInsured()!=null)
			frmWebConfig.set("caption",String.valueOf(sbfCaption1));
			request.setAttribute("frmWebConfig",frmWebConfig);
			return this.getForward(strWebConfigDetails, mapping, request);
		}catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWebConfig));
		}//end of catch(Exception exp)
	}//end of doWebLogin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception

	/**
	 * This method is used to get the details of the selected record from web-board.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.debug("Inside the doChangeWebBoard method of WebConfigAction");
		return doDefault(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

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
			setLinks(request);
			DynaActionForm frmWebConfig = (DynaActionForm)form;
			String strCaption = (String) frmWebConfig.get("caption");
			WebConfigInfoVO webConfigInfoVO = new WebConfigInfoVO();
			ProductPolicyManager productPolicyManager = this.getProductPolicyManagerObject();
			webConfigInfoVO =(WebConfigInfoVO)FormUtils.getFormValues(frmWebConfig, "frmWebConfig",this, mapping, request);
			Integer strPasswordValidity = webConfigInfoVO.getPasswordValidity();
			Integer strAlert = webConfigInfoVO.getAlert();
			Integer intLoginDtWindowPrd= webConfigInfoVO.getLogindtWindowPerd();
			Integer intWindowPeriod = webConfigInfoVO.getWindowPeriod();
			if(((frmWebConfig.get("windowPeriod") != null) && (frmWebConfig.get("windowPeriod") != "")) && ((frmWebConfig.get("logindtWindowPerd") !=null) && (frmWebConfig.get("logindtWindowPerd") !="")))
			{
				if(intLoginDtWindowPrd > intWindowPeriod)
				{
					TTKException expTTK = new TTKException();
	    			expTTK.setMessage("error.administration.policy.webconfig.logindtwindowperiod");
	    			throw expTTK;
				}//end of if(intLoginDtWindowPrd > intWindowPeriod)
			}//end of if((frmWebConfig.get("windowPeriod") != null) && (frmWebConfig.get("logindtWindowPerd") !=null))
			if(((frmWebConfig.get("passwordValidity") != null)&&  (frmWebConfig.get("passwordValidity") !=""))&& ((frmWebConfig.get("alert") != null)&&  (frmWebConfig.get("alert") !="")))
			{
			if(((strAlert > strPasswordValidity)))
				{
					TTKException expTTK = new TTKException();
	    			expTTK.setMessage("error.administration.policy.webconfig.alert");
	    			throw expTTK;
				}//end of if(((frmWebConfig.get("alert") != null)) && (frmWebConfig.get("alert") !="") && (!(strAlert <= (30*strPasswordValidity))))
			}//end of if((frmWebConfig.get("passwordValidity") != null)&&  (frmWebConfig.get("passwordValidity") !=""))
			if(((frmWebConfig.get("passwordValidity") !=""))&& ((frmWebConfig.get("alert") =="")))
			{
				TTKException expTTK = new TTKException();
    			expTTK.setMessage("error.administration.policy.webconfig.validity");
    			throw expTTK;
			}//end of if(((frmWebConfig.get("passwordValidity") !=""))&& ((frmWebConfig.get("alert") =="")))
			if((((frmWebConfig.get("passwordValidity") ==""))&& ((frmWebConfig.get("alert") !=""))))
			{
				TTKException expTTK = new TTKException();
    			expTTK.setMessage("error.administration.policy.webconfig.validity");
    			throw expTTK;
			}//end of if((((frmWebConfig.get("passwordValidity") ==""))&& ((frmWebConfig.get("alert") !=""))))
			webConfigInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = productPolicyManager.saveWebConfigInfo(webConfigInfoVO);
			if(iResult>0)
			{
				request.setAttribute("updated","message.saved");
				webConfigInfoVO = productPolicyManager.getWebConfigInfo(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
				frmWebConfig = (DynaActionForm)FormUtils.setFormValues("frmWebConfig",
						webConfigInfoVO, this, mapping, request);
				if(webConfigInfoVO.getPolicySumInsured()!=null){
					frmWebConfig.set("policySumInsured",webConfigInfoVO.getPolicySumInsured().toString());
				}//end of if(webConfigInfoVO.getPolicySumInsured()!=null)
				frmWebConfig.set("caption",strCaption);
				request.setAttribute("frmWebConfig",frmWebConfig);
			}//end of if(iResult>0)
			return this.getForward(strWebConfigDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWebConfig));
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
			log.debug("Inside the doReset method of WebConfigAction");
			setLinks(request);
			return doWebLogin(mapping,form,request,response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWebConfig));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside the doClose method of WebConfigAction");
			setLinks(request);
			return this.getForward(strWebLogin, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWebConfig));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return productPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end of if(productPolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strWebConfig);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManagerObject()
}//end of WebConfigAction()
