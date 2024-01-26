
/**
 * @ (#) CopayConfigurationAction.java Jun 23, 2011
 * Project       : TTK HealthCare Services
 * File          : CopayConfigurationAction.java
 * Author        : ManiKanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : Jun 23, 2011
 *
 * @author       :  ManiKanta Kumar G G
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ConfigCopayVO;

import formdef.plugin.util.FormUtils;


public class CopayConfigurationAction extends TTKAction  {
	
	//private static final Logger log = Logger.getLogger( CopayConfigurationAction.class );
	
	private static final String strProductConfigCopay="prodconfigcopay";
	private static final String strPolicyConfigCopay="policyconfigcopay";
	
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
					setLinks(request);
					ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
					DynaActionForm frmConfigCopay = (DynaActionForm)form;
					String strActiveSubLink=TTKCommon.getActiveSubLink(request);
					ConfigCopayVO configCopayVO = null;
					String strForwardPath=null;
					if("Products".equals(strActiveSubLink)){
						strForwardPath=strProductConfigCopay;
					}//end of if("Products".equals(strActiveSubLink))
					else {
						strForwardPath=strPolicyConfigCopay;
					}//end of else
					Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
					configCopayVO = productpolicyObject.getConfigCopayAmt(lngProdPolicySeqId);
					frmConfigCopay = (DynaActionForm)FormUtils.setFormValues("frmConfigCopay",configCopayVO,
						     this,mapping,request);
					frmConfigCopay.set("caption",buildCaption(request));
					request.getSession().setAttribute("frmConfigCopay",frmConfigCopay);
					return this.getForward(strForwardPath, mapping, request);
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
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try
		{
			setLinks(request);		
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			DynaActionForm frmConfigCopay = (DynaActionForm)form;
			ConfigCopayVO configCopayVO  = new ConfigCopayVO();
			frmConfigCopay.set("caption",buildCaption(request));
			configCopayVO =(ConfigCopayVO)FormUtils.getFormValues(frmConfigCopay, "frmConfigCopay",this, mapping, request);
			  String regionMandatory=(String)frmConfigCopay.getString("insuredRegion");//1284
			//Modification as per koc 1142
			if(frmConfigCopay.getString("copaypercentageYN").equalsIgnoreCase("N"))
			{
				configCopayVO=null;
				configCopayVO=new ConfigCopayVO();
				//configCopayVO.setCopaypercentageYN("N");
				//configCopayVO.setCopaypercentage("");
                        configCopayVO.setInsuredRegion(regionMandatory);
			}
            
			configCopayVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			configCopayVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = productpolicyObject.saveConfigCopayAmt(configCopayVO);
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
					Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
					configCopayVO = productpolicyObject.getConfigCopayAmt(lngProdPolicySeqId);
					frmConfigCopay = (DynaActionForm)FormUtils.setFormValues("frmConfigCopay",configCopayVO,
						     this,mapping,request);
			}//end of if(iResult>0)
			frmConfigCopay.set("caption",buildCaption(request));
			request.getSession().setAttribute("frmConfigCopay",frmConfigCopay);
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductConfigCopay;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyConfigCopay;
			}//end of else
			return this.getForward(strForwardPath, mapping, request);
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
			setLinks(request);
			String strForward="close";
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			request.getSession().removeAttribute("frmConfigCopay");
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
			throw new TTKException(exp, "Configuration");
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()

}//end of CopayConfigurationAction
