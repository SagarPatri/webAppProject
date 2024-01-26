
/**
 * @ (#) ShortfallDetailsConfigurationAction.java Dec 05, 2012
 * Project       : TTK HealthCare Services
 * File          : ShortfallDetailsConfigurationAction.java
 * Author        : Manohar
 * Company       : RCS
 * Date Created  : Dec 05, 2012
 * 
 * @author       :  Manohar
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */


/**
 * 
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
import com.ttk.dto.administration.ShortfallDaysConfigVO;

import formdef.plugin.util.FormUtils;


public class ShortfallDetailsConfigurationAction extends TTKAction  {
	
	//for setting modes
	private static Logger log = Logger.getLogger(ShortfallDetailsConfigurationAction.class);
	
	//forwards links
	private static final String strProductConfigShortfall="productconfigshortfall";
	private static final String strPolicyConfigShortfall="policyconfigshortfall";
	
	//close forwards links
	private static final String strClosepolicyconfigshortfall="closeproductconfigshortfall";
	private static final String strCloseproductconfigshortfall="closepolicyconfigshortfall";
	
	//Exception Message Identifier
	private static final String strShortfallDaysConfig="shortfalldaysconfiguration";
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
					log.debug("Inside doDefault of ShortfallDetailsConfigurationAction");
					setLinks(request);
					ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
					DynaActionForm frmClaimShortfallDaysConfig = (DynaActionForm)form;
					String strActiveSubLink=TTKCommon.getActiveSubLink(request);
					ShortfallDaysConfigVO shortfallDaysConfigVO =new ShortfallDaysConfigVO();
					String strForwardPath=null;
					if("Products".equals(strActiveSubLink)){
						strForwardPath=strProductConfigShortfall;
					}//end of if("Products".equals(strActiveSubLink))
					else if("Policies".equals(strActiveSubLink)) {
						strForwardPath=strPolicyConfigShortfall;
					}//end of else
			//		Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
					shortfallDaysConfigVO=productpolicyObject.getShortfallDaysConfig(TTKCommon.getWebBoardId(request));
					
					if(shortfallDaysConfigVO != null )
					{
						frmClaimShortfallDaysConfig = (DynaActionForm)FormUtils.setFormValues("frmClaimShortfallDaysConfig",shortfallDaysConfigVO,this, mapping, request);
					}	
					else
					{
						frmClaimShortfallDaysConfig.initialize(mapping);
					}//end of else
					frmClaimShortfallDaysConfig.set("caption",buildCaption(request));
					request.getSession().setAttribute("frmClaimShortfallDaysConfig",frmClaimShortfallDaysConfig);
					return this.getForward(strForwardPath, mapping, request);
				}
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,strShortfallDaysConfig));
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
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try
		{
			log.debug("Inside doSave of ShortfallDetailsConfigurationAction");
			setLinks(request);		
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath="policyconfigshortfall";
			DynaActionForm frmClaimShortfallDaysConfig = (DynaActionForm)form;
			
			ShortfallDaysConfigVO shortfallDaysConfigVO =new ShortfallDaysConfigVO();
			shortfallDaysConfigVO =(ShortfallDaysConfigVO)FormUtils.getFormValues(frmClaimShortfallDaysConfig, "frmClaimShortfallDaysConfig",this, mapping, request);
			shortfallDaysConfigVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			shortfallDaysConfigVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			//frmConfigShortfallDetails.set("caption",buildCaption(request));
		//	configCopayVO =(ConfigCopayVO)FormUtils.getFormValues(frmConfigShortfallDetails, "frmConfigShortfallDetails",this, mapping, request);
		//	configCopayVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
		//	configCopayVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			long iResult = productpolicyObject.saveShortfallDaysConfig(shortfallDaysConfigVO);
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
					shortfallDaysConfigVO=productpolicyObject.getShortfallDaysConfig(TTKCommon.getWebBoardId(request));
					frmClaimShortfallDaysConfig = (DynaActionForm)FormUtils.setFormValues("frmClaimShortfallDaysConfig", shortfallDaysConfigVO,
							this, mapping, request);
			}//end of if(iResult>0)
			frmClaimShortfallDaysConfig.set("caption",buildCaption(request));
			request.getSession().setAttribute("frmClaimShortfallDaysConfig",frmClaimShortfallDaysConfig);
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductConfigShortfall;
			}//end of if("Products".equals(strActiveSubLink))
			else if("Policies".equals(strActiveSubLink)) {
				strForwardPath=strPolicyConfigShortfall;
			}//end of else
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strShortfallDaysConfig));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)
	
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
		log.debug("Inside doReset of ShortfallDetailsConfigurationAction");
		setLinks(request);
		return doDefault(mapping, form, request, response);
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	
	/**
	 * This method is used to navigate to previous screen when close  button is clicked.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try
		{
			log.debug("Inside doClose of ShortfallDetailsConfigurationAction");
			setLinks(request);
			String strForward="close";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if("Products".equals(strActiveSubLink)){
				strForward=strCloseproductconfigshortfall;
			}//end of if("Products".equals(strActiveSubLink))
			else if("Policies".equals(strActiveSubLink)) {
				strForward=strClosepolicyconfigshortfall;
			}//end of else
			request.getSession().removeAttribute("frmClaimShortfallDaysConfig");
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strShortfallDaysConfig));
		}//end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
		if(strActiveSubLink.equals("Products"))
		{
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("Configuration - [").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append(" [").append(frmProductList.get("productName")).append("]");			
		}//end of if(strActiveSubLink.equals("Products"))
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
			throw new TTKException(exp,strShortfallDaysConfig);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()

}//end of ShortfallDetailsConfigurationAction
