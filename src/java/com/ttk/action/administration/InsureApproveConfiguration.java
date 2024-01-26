
// Bajaj Change request 1274A

/**
 * @ (#) InsureApproveConfiguration.java Jun 23, 2011
 * Project       : TTK HealthCare Services
 * File          : InsureApproveConfiguration.java
 * Author        : Satya Moganti
 * Company       : RCS 
 * Date Created  : Jun 23, 2011
 *
 * @author       :  Satya 
 * Modified date :  
 * Reason        :  Bajaj Insurance Approve Configuartion
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
import com.ttk.dto.administration.InsuranceApproveVO;

import formdef.plugin.util.FormUtils;


public class InsureApproveConfiguration extends TTKAction  {
	
	//private static final Logger log = Logger.getLogger( CopayConfigurationAction.class );
	
	private static final String strProductConfigInuranceApprove="prodconfiginuranceapprove";
	private static final String strPolicyConfigInuranceApprove="policyconfiginuranceapprove";
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
	public ActionForward  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
					DynaActionForm frmConfigInsuranceApprove = (DynaActionForm)form;
					String strActiveSubLink=TTKCommon.getActiveSubLink(request);
					InsuranceApproveVO insuranceApproveVO = null;
					String strForwardPath=null;
					if("Products".equals(strActiveSubLink)){
						strForwardPath=strProductConfigInuranceApprove;
					}//end of if("Products".equals(strActiveSubLink))
					else {
						strForwardPath=strPolicyConfigInuranceApprove;
					}//end of else
					Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
					insuranceApproveVO = productpolicyObject.getConfigInsuranceApproveData(lngProdPolicySeqId);
					/*if((insuranceApproveVO.getNotificationFlag()==null) && (insuranceApproveVO.getRejectionYN().equalsIgnoreCase("Y")))
					{		
						insuranceApproveVO.setNotificationFlag("ADOBE");
					}*/
					frmConfigInsuranceApprove = (DynaActionForm)FormUtils.setFormValues("frmConfigInsuranceApprove",insuranceApproveVO,this,mapping,request);
					frmConfigInsuranceApprove.set("caption",buildCaption(request));
					//frmConfigInsuranceApprove.set("insClmYN","Y");
					request.getSession().setAttribute("frmConfigInsuranceApprove",frmConfigInsuranceApprove);
					//return mapping.findForward(strForwardPath);
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
	public ActionForward  doConfigLinks(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					
					
					String strForwardPath="";
					if("PreApprovalLimit".equals(request.getParameter("linkMode"))){
						strForwardPath="preApprovalLimit";
					}else if("CapitationCategory".equals(request.getParameter("linkMode"))){
						strForwardPath="capitationCategory";
					}
					return mapping.findForward(strForwardPath);
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
	public ActionForward  preApprovalLimit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					String strActiveSubLink=TTKCommon.getActiveSubLink(request);
					String strForwardPath=null;
					if("Products".equals(strActiveSubLink)){
						strForwardPath="preApprovalLimitPDT";
					}//end of if("Products".equals(strActiveSubLink))
					else {
						strForwardPath="preApprovalLimitPLS";
					}//end of else
					ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
					DynaActionForm frmPreApprovalLimit = (DynaActionForm)form;					
					Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);					
					 Object[] objArry=productpolicyObject.getPreApprovedLimit(lngProdPolicySeqId);
					 frmPreApprovalLimit.set("preApprovalLimitYN", TTKCommon.checkNull(objArry[0]).toString());
					 frmPreApprovalLimit.set("preApprovalAmountVIP", TTKCommon.checkNull(objArry[1]).toString());
					 frmPreApprovalLimit.set("preApprovalAmountNONVIP", TTKCommon.checkNull(objArry[2]).toString());
					frmPreApprovalLimit.set("caption",buildCaption(request));
					request.getSession().setAttribute("frmPreApprovalLimit",frmPreApprovalLimit);
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
		
			}//end of  preApprovalLimit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           // HttpServletResponse response)
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
	public ActionForward  doSavePreApprovalLimit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					
					String strActiveSubLink=TTKCommon.getActiveSubLink(request);
					String strForwardPath=null;
					if("Products".equals(strActiveSubLink)){
						strForwardPath="preApprovalLimitPDT";
					}//end of if("Products".equals(strActiveSubLink))
					else {
						strForwardPath="preApprovalLimitPLS";
					}//end of else
					ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
					DynaActionForm frmPreApprovalLimit = (DynaActionForm)form;					
					Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
					Object[] objData=new Object[5];
					objData[0]=lngProdPolicySeqId;
					objData[1]=request.getParameter("preApprovalLimitYN");
					objData[2]=frmPreApprovalLimit.getString("preApprovalAmountVIP");
					objData[3]=frmPreApprovalLimit.getString("preApprovalAmountNONVIP");
					objData[4]=TTKCommon.getUserSeqId(request);
					
					 productpolicyObject.savePreApprovedLimit(objData);
					 request.setAttribute("updated","message.saved");
					 Object[] objArry=productpolicyObject.getPreApprovedLimit(lngProdPolicySeqId);
					 frmPreApprovalLimit.set("preApprovalLimitYN", TTKCommon.checkNull(objArry[0]).toString());
					 frmPreApprovalLimit.set("preApprovalAmountVIP", TTKCommon.checkNull(objArry[1]).toString());
					 frmPreApprovalLimit.set("preApprovalAmountNONVIP", TTKCommon.checkNull(objArry[2]).toString());
					
					frmPreApprovalLimit.set("caption",buildCaption(request));
					request.getSession().setAttribute("frmPreApprovalLimit",frmPreApprovalLimit);
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
		
			}//end of  doSavePreApprovalLimit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			DynaActionForm frmConfigInsuranceApprove = (DynaActionForm)form;
			InsuranceApproveVO insuranceApproveVO=null;
			String insPatYN=frmConfigInsuranceApprove.getString("insPatYN");
			String insClmYN=frmConfigInsuranceApprove.getString("insClmYN");
			
			frmConfigInsuranceApprove.set("caption",buildCaption(request));
		
		/*if((insPatYN.equals("Y")||(insClmYN.equals("Y"))))
		{
		frmConfigInsuranceApprove.set("rejectionYN","Y");
		}else 	if((insPatYN.equals("N")&&(insClmYN.equals("N"))))
		{
			frmConfigInsuranceApprove.set("rejectionYN","N");
		}*/
			insuranceApproveVO =(InsuranceApproveVO)FormUtils.getFormValues(frmConfigInsuranceApprove, "frmConfigInsuranceApprove",this, mapping, request);
			//1274 A latest changes for bajaj enhan1
			
			log.info(" rejectionYN        "+frmConfigInsuranceApprove.getString("rejectionYN"));
			log.info("allowedPatYN-------------"+frmConfigInsuranceApprove.getString("allowedPatYN"));
			log.info("insPatYN------------------"+frmConfigInsuranceApprove.getString("insPatYN"));
			log.info("insPatYN----------------"+frmConfigInsuranceApprove.getString("insPatYN"));
			log.info("insClmYN--------------------"+frmConfigInsuranceApprove.getString("insClmYN"));
			log.info("patOperator-------------------------"+frmConfigInsuranceApprove.getString("patOperator"));
			log.info("clmOperator-------------------"+frmConfigInsuranceApprove.getString("clmOperator"));
			
		/*	if(frmConfigInsuranceApprove.getString("allowedPatYN").equalsIgnoreCase("N"))	{
				insuranceApproveVO=null;
				insuranceApproveVO=new InsuranceApproveVO();
				insuranceApproveVO.setAllowedPatYN("N");
				insuranceApproveVO.setPatApproveAmountLimit(null);
				insuranceApproveVO.setPatOperator(null);
				insuranceApproveVO.setAllowedClmYN("N");
				insuranceApproveVO.setClmApproveAmountLimit(null);
				insuranceApproveVO.setClmOperator(null);
				
			}//end of if(frmConfigInsuranceApprove.getString("allowedYN").equalsIgnoreCase("N"))	
			if(frmConfigInsuranceApprove.getString("allowedClmYN").equalsIgnoreCase("N"))	{
				insuranceApproveVO=null;
				insuranceApproveVO=new InsuranceApproveVO();
				insuranceApproveVO.setAllowedPatYN("N");
				insuranceApproveVO.setPatApproveAmountLimit(null);
				insuranceApproveVO.setPatOperator(null);
				insuranceApproveVO.setAllowedClmYN("N");
				insuranceApproveVO.setClmApproveAmountLimit(null);
				insuranceApproveVO.setClmOperator(null);
				
			}//end of if(frmConfigInsuranceApprove.getString("allowedYN").equalsIgnoreCase("N"))	*/
					
		/*	if(frmConfigInsuranceApprove.getString("rejectionYN").equalsIgnoreCase("Y")){
					insuranceApproveVO.setRejectionYN("Y");
					insuranceApproveVO.setNotificationFlag(frmConfigInsuranceApprove.getString("notificationFlag"));
					insuranceApproveVO.setTimeInHrs(frmConfigInsuranceApprove.getString("timeInHrs"));
					insuranceApproveVO.setTimeInMins(frmConfigInsuranceApprove.getString("timeInMins"));

			}//end of if(frmConfigInsuranceApprove.getString("rejectionYN").equalsIgnoreCase("Y"))
			else if(frmConfigInsuranceApprove.getString("rejectionYN").equalsIgnoreCase("N"))
			{
				insuranceApproveVO.setRejectionYN("N");
			}//end of else if(frmConfigInsuranceApprove.getString("rejectionYN").equalsIgnoreCase("N"))
*/			//as per 1274 A latest changes
			insuranceApproveVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			insuranceApproveVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int  iResult = productpolicyObject.saveConfigInsuranceApprove(insuranceApproveVO,"FPR");
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
					insuranceApproveVO = productpolicyObject.getConfigInsuranceApproveData(TTKCommon.getWebBoardId(request));
					frmConfigInsuranceApprove = (DynaActionForm)FormUtils.setFormValues("frmConfigInsuranceApprove",insuranceApproveVO, this, mapping, request);
			}//end of if(iResult>0)
			frmConfigInsuranceApprove.set("caption",buildCaption(request));
			//frmConfigSumInsured.set("preAuthClaimsExistAlert",preAuthClaimsExistAlert);
			request.getSession().setAttribute("frmConfigInsuranceApprove",frmConfigInsuranceApprove);
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductConfigInuranceApprove;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyConfigInuranceApprove;
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
				request.getSession().removeAttribute("frmConfigInsuranceApprove");
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
		
		
		
/*
 * This method is used to navigate to previous screen when close  button is clicked.
 * Finally it forwards to the appropriate view based on the specified forward mappings
 */

public ActionForward  capitationCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws TTKException 
		{
			try
			{
		setLinks(request);
		ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
		DynaActionForm frmcapitationCategory = (DynaActionForm)form;					
		Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);					

		Object[] objArry=productpolicyObject.getCapitationCategory(lngProdPolicySeqId);
		frmcapitationCategory.set("capitationCategory", TTKCommon.checkNull(objArry[0]).toString());
		 
		frmcapitationCategory.set("caption",buildCaption(request));
		request.getSession().setAttribute("frmcapitationCategory",frmcapitationCategory);
		return this.getForward("capitationCategory", mapping, request);
		
	}
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
	}//end of catch(Exception exp)

}//end of  capitationCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   // HttpServletResponse response)
		

/*
 * Do Save Capitation Category
 */

public ActionForward  doSaveCapitationCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws TTKException 
		{
			try
			{
				setLinks(request);
				
				ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
				DynaActionForm frmcapitationCategory = (DynaActionForm)form;					
				Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
				Object[] objData=new Object[3];
				objData[0]=lngProdPolicySeqId;
				objData[1]=request.getParameter("capitationCategory");
				objData[2]=TTKCommon.getUserSeqId(request);
				
				 productpolicyObject.saveCapitationCategory(objData);
				 request.setAttribute("updated","message.saved");
				 Object[] objArry=productpolicyObject.getCapitationCategory(lngProdPolicySeqId);
				 frmcapitationCategory.set("capitationCategory", TTKCommon.checkNull(objArry[0]).toString());
				
				 frmcapitationCategory.set("caption",buildCaption(request));
				request.getSession().setAttribute("frmcapitationCategory",frmcapitationCategory);
				return this.getForward("capitationCategory", mapping, request);
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp)
	
		}//end of  doSaveCapitationCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           // HttpServletResponse response)
		
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
}//end of InsureApproveConfiguration
	
