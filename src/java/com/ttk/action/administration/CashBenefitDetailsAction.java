//KOC 1270 for hospital cash benefit
/**
 * @ (#) CashBenefitDetailsAction.java   May 20, 2013
 * Project        : TTK HealthCare Services
 * File           : CashBenefitDetailsAction.java
 * Author         : 
 * Company        : RCS TECHNOLOGIES
 * Date Created   :
 *
 * @author        : 
 * Modified by    : 
 * Modified date  :
 * Reason         : 
 */

package com.ttk.action.administration;
/**
 * This class is used to add/update the Plan Details.
 */
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProdPolicyConfigManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
//import com.ttk.dto.administration.CashbenefitVO;
//import com.ttk.dto.administration.CashbenefitVO;
import com.ttk.dto.administration.CashbenefitVO;
import com.ttk.dto.administration.PlanDetailVO;
import com.ttk.dto.administration.PlanVO;
//import com.ttk.dto.administration.PlanDetailVO;
//import com.ttk.dto.administration.PlanVO;

import formdef.plugin.util.FormUtils;

public class CashBenefitDetailsAction extends TTKAction
{
	private static Logger log = Logger.getLogger( CashBenefitDetailsAction.class );

	//Action mapping forwards.
	private static final String strAddUpdatecashbenefit="addupdatecashbenefit";
	private static final String strAddUpdatePolicycashbenefit="addupdatepolicyCashBenefit";
	private static final String strCashBenefitClose="CashBenefitclose";

	//Exception Message Identifier
	private static final String strProduct="product";

	/**
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCashBenefitAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doCashBenefitAdd method of PlanDetailsAction");
			setLinks(request);
			DynaActionForm frmAddCash = (DynaActionForm)form;
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strAddUpdatecashbenefit;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strAddUpdatePolicycashbenefit;
			}//end of else
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption = sbfCaption.append("Add -").append(this.buildCaption(strActiveSubLink,request));
			frmAddCash.initialize(mapping);

			frmAddCash.set("caption",String.valueOf(sbfCaption));
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doPlanAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doSave method of CashBenefitDetailsAction");
			setLinks(request);
			DynaActionForm frmAddCash = (DynaActionForm)form;
			String strCaption = "";
			strCaption = (String)frmAddCash.get("caption");
			ProdPolicyConfigManager prodPolicyConfigManager=this.getProductPolicyConfigObject();
			CashbenefitVO cashbenefitVO=new CashbenefitVO();
			cashbenefitVO=(CashbenefitVO)FormUtils.getFormValues(frmAddCash, "frmAddCashBenefit",this,mapping, request);
			cashbenefitVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			
			if(strActiveSubLink.equals("Products"))
			{
				DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
				Long lngProdSeqId = TTKCommon.getLong(frmProductDetail.getString("prodSeqId"));
				cashbenefitVO.setProdSeqID(lngProdSeqId);
				strForwardPath=strAddUpdatecashbenefit;
			}//end of if(strActiveSubLink.equals("Products"))
			else if(strActiveSubLink.equals("Policies"))
			{
				DynaActionForm frmPoliciesDetail =(DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
				Long lngpolicySeqID = TTKCommon.getLong(frmPoliciesDetail.getString("policySeqID"));
				cashbenefitVO.setPolicySeqID(lngpolicySeqID);
				strForwardPath=strAddUpdatePolicycashbenefit;
			}//end of else if(strActiveSubLink.equals("Policies"))

			prodPolicyConfigManager.saveCashBenefitDetail(cashbenefitVO);
			
			
			
			if(frmAddCash.getString("insCashBenefitSeqID").equals(""))
			{
				
				
				
				frmAddCash.initialize(mapping);
				request.setAttribute("updated","message.saved");
			}//end of if(frmAddCashBenefit.getString("insCashBenefitSeqID").equals(""))
			else
			{
				request.setAttribute("updated","message.savedSuccessfully");
			}// end of else
			frmAddCash.set("caption",strCaption);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
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
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doReset method of CashBenefitDetailsAction");
			setLinks(request);
			DynaActionForm frmAddCash=(DynaActionForm)form;
			//DynaActionForm frmPlanList =(DynaActionForm)request.getSession().getAttribute("frmPlanList");
			String strinsCashBenefitSeqID = frmAddCash.getString("insCashBenefitSeqID");
			String strconvBenefit = frmAddCash.getString("convBenefit");
			String strPolicySeqID = frmAddCash.getString("policySeqID");
			String strProdSeqId = frmAddCash.getString("prodSeqId");
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			StringBuffer sbfCaption= new StringBuffer();
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strAddUpdatecashbenefit;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strAddUpdatePolicycashbenefit;
			}//end of else

			//get the session bean from the bean pool for each excecuting thread
			ProdPolicyConfigManager prodPolicyConfigManager=this.getProductPolicyConfigObject();
			//create a new PlanDetail object
			CashbenefitVO CashbenefitVO=new CashbenefitVO();
			Long lnginsCashBenefitSeqID = null;
			//String strconvBenefit="HCB";
			if(frmAddCash.getString("insCashBenefitSeqID").equals(""))
			{
				frmAddCash.initialize(mapping);
				sbfCaption = sbfCaption.append("Add -").append(this.buildCaption(strActiveSubLink,request));
			}//end of if(frmPlanDetail.getString("prodPlanSeqID").equals(""))
			else
			{
				lnginsCashBenefitSeqID=TTKCommon.getLong(frmAddCash.getString("insCashBenefitSeqID"));
				strconvBenefit=TTKCommon.getString(frmAddCash.getString("convBenefit"));
				//get the plan details from the Dao object
				CashbenefitVO	=	prodPolicyConfigManager.getCashBenefitDetail(lnginsCashBenefitSeqID,strconvBenefit);
				sbfCaption = sbfCaption.append("Edit -").append(this.buildCaption(strActiveSubLink,request));
				frmAddCash = (DynaActionForm)FormUtils.setFormValues("frmAddCashBenefit",CashbenefitVO, this, mapping, request);
			}//end of else
			//frmAddCashBenefit.set("caption",frmPlanList.get("caption"));
			frmAddCash.set("caption",sbfCaption.toString());
			frmAddCash.set("insCashBenefitSeqID", strinsCashBenefitSeqID);
			frmAddCash.set("convBenefit", "HCB");
			frmAddCash.set("policySeqID", strPolicySeqID);
			frmAddCash.set("prodSeqId", strProdSeqId);
			request.setAttribute("frmAddCashBenefit",frmAddCash);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doClose method of CashBenefitDetailsAction");
			setLinks(request);
			return mapping.findForward(strCashBenefitClose);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewPlanDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doViewPlanDetails method of PlanDetailsAction");
			setLinks(request);

			//get the session bean from the bean pool for each excecuting thread
			ProdPolicyConfigManager  productPolicyConfig=this.getProductPolicyConfigObject();
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			//Long lnginsCashBenefitSeqID=null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strAddUpdatecashbenefit;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strAddUpdatePolicycashbenefit;
			}//end of else
			StringBuffer sbfCaption= new StringBuffer();
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmAddCash =(DynaActionForm)form;
			//DynaActionForm frmPlanList =(DynaActionForm)request.getSession().getAttribute("frmPlanList");

			//create a new PlanDetail object
			CashbenefitVO cashbenefitVO=null;
			String strRowNum=request.getParameter("rownum");
			if(!(TTKCommon.checkNull(strRowNum).equals("")))
			{
				int iRowNum = TTKCommon.getInt(strRowNum);
				cashbenefitVO = (CashbenefitVO)tableData.getRowInfo(iRowNum);
				//get the product details from the Dao object
				
				
				
				cashbenefitVO=productPolicyConfig.getCashBenefitDetail(cashbenefitVO.getinsCashBenefitSeqID(),cashbenefitVO.getConvBenefit());
				//cashbenefitVO=productPolicyConfig.get.getCashBenefitDetail(cashbenefitVO.getConvBenefit());
				
				
				frmAddCash = (DynaValidatorForm)FormUtils.setFormValues("frmAddCashBenefit",cashbenefitVO, this, mapping, request);
				sbfCaption = sbfCaption.append("Edit -").append(this.buildCaption(strActiveSubLink,request));
				request.setAttribute("frmAddCashBenefit",frmAddCash);
			}//end if(!(TTKCommon.checkNull(strRowNum).equals("")))
			if(cashbenefitVO.getProdSeqID()!=null)
			{
				frmAddCash.set("prodSeqId", cashbenefitVO.getProdSeqID().toString());
			}//end of if(planDetailsVO.getProdSeqID()!=null)
			else if(cashbenefitVO.getPolicySeqID()!=null)
			{
				frmAddCash.set("policySeqID", cashbenefitVO.getPolicySeqID().toString());
			}//end of else if(planDetailsVO.getPolicySeqID()!=null)
			//frmAddCashBenefit.set("caption",frmPlanList.get("caption"));		
			frmAddCash.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("cashbenefitVO",cashbenefitVO);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doViewPlanDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	/**
	 * This method is prepares the Caption based on the flow and retunrs it
	 * @param form 
	 * @param strActiveSubLink current Active sublink
	 * @param request current HttpRequest
	 * @return String caption built
	 * @throws TTKException
	 */
	private String buildCaption(String strActiveSubLink,HttpServletRequest request)throws TTKException
	{
		StringBuffer sbfCaption=new StringBuffer();
		if(strActiveSubLink.equals("Products"))
		{
			DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append(" [ ").append(frmProductDetail.getString("companyName")).append(" ]");
			sbfCaption.append(" [ ").append(frmProductDetail.getString("productName")).append(" ]");
		}//end of if(strActiveSubLink.equals("Products"))
		else if(strActiveSubLink.equals("Policies"))
		{
			DynaActionForm frmPoliciesDetail =(DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			sbfCaption.append(" [ ").append(frmPoliciesDetail.getString("companyName")).append(" ]");
			sbfCaption.append(" [ ").append(frmPoliciesDetail.getString("policyNbr")).append(" ]");
		}//end of else if(strActiveSubLink.equals("Policies"))
		return sbfCaption.toString();
	}//end of buildCaption(String strActiveSubLink,HttpServletRequest request)

	/**
	 * Returns the ProdPolicyConfigManager session object for invoking methods on it.
	 * @return productPolicyConfigManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private ProdPolicyConfigManager getProductPolicyConfigObject() throws TTKException
	{
		ProdPolicyConfigManager productPolicyConfigManager = null;
		try
		{
			if(productPolicyConfigManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyConfigManager = (ProdPolicyConfigManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProdPolicyConfigManagerBean!com.ttk.business.administration.ProdPolicyConfigManager");
			}//end if(productPolicyConfigManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return productPolicyConfigManager;
	}//end of getProductPolicyConfigObject() throws TTKException
}//end of PlanDetailsAction
