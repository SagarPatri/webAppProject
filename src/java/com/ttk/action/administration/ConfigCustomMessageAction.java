/**
 * @ (#) ConfigCustomMessageAction.java Oct 12, 2010
 * Project      : TTK HealthCare Services
 * File         : ConfigCustomMessageAction.java
 * Author       : Manikanta Kumar G G
 * Company      : Span Systems Corporation
 * Date Created : Oct 12, 2010
 *
 * @author       : Manikanta Kumar G G
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
import com.ttk.dto.administration.CustConfigMsgVO;

import formdef.plugin.util.FormUtils;

/**
 * @author manikanta_k
 *
 */
public class ConfigCustomMessageAction extends TTKAction {
	
	private static final Logger log = Logger.getLogger( ConfigCustomMessageAction.class );
	
	private static final  String strConfig="config";
	
	/**
	 * This method is used to forward to configuration screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					log.debug("Inside doDefault method of ConfigCustomMessageAction ");
					setLinks(request);
					ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
					DynaActionForm frmConfigMessage = (DynaActionForm)form;
					CustConfigMsgVO custConfigMsgVO = null;
					Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
					custConfigMsgVO = productpolicyObject.getCustMsgInfo(lngProdPolicySeqId);
					frmConfigMessage = (DynaActionForm)FormUtils.setFormValues("frmConfigMessage",custConfigMsgVO,
						     this,mapping,request);
					frmConfigMessage.set("caption",buildCaption(request));
					frmConfigMessage.set("configYN",custConfigMsgVO.getConfigYN());
					frmConfigMessage.set("config",custConfigMsgVO.getConfigYN());
					frmConfigMessage.set("message",custConfigMsgVO.getMessage());
					request.getSession().setAttribute("frmConfigMessage",frmConfigMessage);
					return this.getForward(strConfig, mapping, request);
				}
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
				}//end of catch(Exception exp)
		
			}//end of  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           // HttpServletResponse response)

	/**
	 * This method is used to add/update the record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try
		{
			log.debug("Inside doSave method of ConfigCustomMessageAction ");
			setLinks(request);		
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			DynaActionForm frmConfigMessage = (DynaActionForm)form;
			CustConfigMsgVO custConfigMsgVO = new CustConfigMsgVO();
			frmConfigMessage.set("caption",buildCaption(request));
			custConfigMsgVO =(CustConfigMsgVO)FormUtils.getFormValues(frmConfigMessage, "frmConfigMessage",this, mapping, request);
			if("Y".equals(TTKCommon.checkNull(frmConfigMessage.get("config"))))
			{
				custConfigMsgVO.setConfigYN("Y");
			}//end of if("Y".equals(TTKCommon.checkNull(frmConfigMessage.get("config"))))
			else
			{
				custConfigMsgVO.setConfigYN("N");
			}//end of else
			custConfigMsgVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			custConfigMsgVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = productpolicyObject.saveCustMsgInfo(custConfigMsgVO);
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
			}//end of if(iResult>0)
			frmConfigMessage.set("caption",buildCaption(request));
			request.getSession().setAttribute("frmConfigMessage",frmConfigMessage);
			return this.getForward(strConfig, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)
	
	public ActionForward doClose(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try
		{
			setLinks(request);
			log.debug("Inside  doClose method of ConfigCustomMessageAction");
			String strForward="close";
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			request.getSession().removeAttribute("frmConfigMessage");
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "Configuration"));
		}//end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
    {
			log.debug("Inside the doReset method of ConfigCustomMessageAction ");
			setLinks(request);
			return doDefault(mapping, form, request, response);
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	/**
	 * This method is to build the Caption 
	 * @param request current HttpRequest
	 * @param form 
	 * @return String caption built
	 * @throws TTKException
	 */
	private String buildCaption(HttpServletRequest request)throws Exception
	{
		String strCaption=null;
		StringBuffer sbfCaption= new StringBuffer();
		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
		if(strActiveSubLink.equals("Policies"))
		{
			DynaActionForm frmPolicies = (DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			sbfCaption.append("Configuration - [ ").append(frmPolicies.get("companyName")).append(" ] [ ").append(frmPolicies.get("policyNbr")).append(" ] ");
		}//end of else if(strActiveSubLink.equals("Policies"))
		strCaption=String.valueOf(sbfCaption);
		return strCaption;
	}//end of buildCaption(HttpServletRequest request)
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManager() throws TTKException
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
			throw new TTKException(exp, "Configuration");
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()
	
}//end of ConfigCustomMessageAction()
