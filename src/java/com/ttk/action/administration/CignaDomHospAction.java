


/**
 * @ (#) CIGNADOMHOSPACTION-1285 Jun 23, 2011
 * Project       : TTK HealthCare Services
 * File          : SumInsuredRestrictionAction.java
 * Author        : Satya Moganti
 * Company       : RCS 
 * Date Created  : Jun 23, 2011
 *
 * @author       :  Satya 
 * Modified date :  
 * Reason        :  KOC 1140(SumInsured restriction at policy level)
 */


/**
 * 
 */

package com.ttk.action.administration;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import com.ttk.dto.administration.DomConfigVO;



import formdef.plugin.util.FormUtils;


public class CignaDomHospAction extends TTKAction  {
	
	//private static final Logger log = Logger.getLogger( CopayConfigurationAction.class );
	
	private static final String strProductDomHospConfig="prodDomHospconfig";
	private static final String strPolicyDomHospConfig="policyDomHospconfig";
	private static final String strProductClose="productclose";
	private static final String strPolicyClose="policyclose";
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
					DynaActionForm frmCignaDomHosp = (DynaActionForm)form;
					String strActiveSubLink=TTKCommon.getActiveSubLink(request);
					DomConfigVO domConfigVO =null;
					String strForwardPath=null;
				
					if("Products".equals(strActiveSubLink)){
						strForwardPath=strProductDomHospConfig;
					}//end of if("Products".equals(strActiveSubLink))
					else {
						strForwardPath=strPolicyDomHospConfig;
					}//end of else
					Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
					domConfigVO = productpolicyObject.getDomicilaryConfig(lngProdPolicySeqId);
					frmCignaDomHosp = (DynaActionForm)FormUtils.setFormValues("frmCignaDomHosp",domConfigVO,   this,mapping,request);
				
					frmCignaDomHosp.set("caption",buildCaption(request));
		
					request.getSession().setAttribute("frmCignaDomHosp",frmCignaDomHosp);
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
			DynaActionForm frmCignaDomHosp = (DynaActionForm)form;
			
			DomConfigVO  domConfigVO  = new DomConfigVO();
			domConfigVO =(DomConfigVO)FormUtils.getFormValues(frmCignaDomHosp, "frmCignaDomHosp",this, mapping, request);
			
			
			String selectedType=(String)frmCignaDomHosp.get("selectedType");
		   //Long prodPolicySeqID=TTKCommon.getWebBoardId(request);
			 Long userId=TTKCommon.getUserSeqId(request);
			domConfigVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			domConfigVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
		  
			ArrayList allOutParam = productpolicyObject.saveDomicilaryConfig(domConfigVO);
			int iResult=((Integer)allOutParam.get(0)).intValue();
			String preAuthClaimsExistAlert=(String) allOutParam.get(1);
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
					domConfigVO = productpolicyObject.getDomicilaryConfig(TTKCommon.getWebBoardId(request));
					frmCignaDomHosp = (DynaActionForm)FormUtils.setFormValues("frmCignaDomHosp",domConfigVO, this, mapping, request);
			}//end of if(iResult>0)
			frmCignaDomHosp.set("caption",buildCaption(request));
			frmCignaDomHosp.set("preAuthClaimsExistAlert",preAuthClaimsExistAlert);
			request.getSession().setAttribute("frmCignaDomHosp",frmCignaDomHosp);
			
			
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductDomHospConfig;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyDomHospConfig;
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
		
	
	
	
	/*public ActionForward  doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
		String strForward="";
		if("Products".equals(strActiveSubLink)){
			strForward=strProductConfigSumInsured;
		}//end of if("Products".equals(strActiveSubLink))
		else {
			strForward=strPolicyConfigSumInsured;
		}//end of else;
		
		return this.getForward(strForward, mapping, request);
		
			}
	
	*//**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 *//*
		public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws TTKException
	    {
				setLinks(request);
				return doDefault(mapping, form, request, response);
	    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
		
		*//**
		 * This method is used to navigate to previous screen when close  button is clicked.
		 * Finally it forwards to the appropriate view based on the specified forward mappings
		 *
		 * @param mapping The ActionMapping used to select this instance
		 * @param form The optional ActionForm bean for this request (if any)
		 * @param request The HTTP request we are processing
		 * @param response The HTTP response we are creating
		 * @return ActionForward Where the control will be forwarded, after this request is processed
		 * @throws Exception if any error occurs
		 *//*
		public ActionForward doClose(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response) throws TTKException
		{
			try
			{
				setLinks(request);
				String strActiveSubLink=TTKCommon.getActiveSubLink(request);
				String strForward="";
				if("Products".equals(strActiveSubLink)){
					strForward=strProductClose;
				}//end of if("Products".equals(strActiveSubLink))
				else {
					strForward=strPolicyClose;
				}//end of else
				if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				{
					((DynaActionForm)form).initialize(mapping);//reset the form data
				}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				request.getSession().removeAttribute("frmConfigSumInsured");
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
		
		
		
		
	*//**
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
}
	
