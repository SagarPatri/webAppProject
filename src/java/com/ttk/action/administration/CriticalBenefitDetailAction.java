//KOC FOR PRAVEEN CRITICAL BENEFIT
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
import com.ttk.dto.administration.CashbenefitVO;
import com.ttk.dto.administration.PlanDetailVO;
import com.ttk.dto.administration.PlanVO;

import formdef.plugin.util.FormUtils;


public class CriticalBenefitDetailAction extends TTKAction
{
	private static Logger log = Logger.getLogger( CriticalBenefitDetailAction.class );

	//Action mapping forwards.
	private static final String strAddUpdateCriticalbenefit="addupdateCriticalbenefit";
	private static final String strAddUpdatePolicyCriticalbenefit="addupdatepolicyCriticalBenefit";
	private static final String strCriticalBenefitClose="CriticalBenefitclose";
	
	String productCriticalSurvivalPeriod = "";
	String policiesCriticalSurvivalPeriod = "";
	
	
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
	public ActionForward doCriticalBenefitAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doCashBenefitAdd method of PlanDetailsAction");
			setLinks(request);
			DynaActionForm frmAddCritical = (DynaActionForm)form;
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strAddUpdateCriticalbenefit;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strAddUpdatePolicyCriticalbenefit;
			}//end of else
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption = sbfCaption.append("Add -").append(this.buildCaption(strActiveSubLink,request));
			CashbenefitVO cashbenefitVO= new CashbenefitVO();
			if(strActiveSubLink.equals("Products"))
			{
				DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
				productCriticalSurvivalPeriod = (String)frmProductDetail.getString("survivalPeriod");
				log.info("productCriticalSurvivalPeriod:::>>>>>>>>>>>>"+productCriticalSurvivalPeriod);
				cashbenefitVO.setShowSurvivalPeriod(productCriticalSurvivalPeriod);
			}
			else if(strActiveSubLink.equals("Policies"))
			{
				DynaActionForm frmPoliciesDetail =(DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
				policiesCriticalSurvivalPeriod = (String)frmPoliciesDetail.getString("survivalPeriod");
				cashbenefitVO.setShowSurvivalPeriod(policiesCriticalSurvivalPeriod);
			}
			frmAddCritical.initialize(mapping);
			log.info("Add--inti--"+frmAddCritical);
			frmAddCritical = (DynaValidatorForm)FormUtils.setFormValues("frmAddCriticalPlan",cashbenefitVO, this, mapping, request);
			log.info("Add--After--inti--"+frmAddCritical);
			request.getSession().setAttribute("cashbenefitVO",cashbenefitVO);
			frmAddCritical.set("caption",String.valueOf(sbfCaption));
			if(frmAddCritical.getString("showSurvivalPeriod").contains("Y"))
			{
				
				frmAddCritical.set("showSurvivalPeriod","Y");
			}
			else 
			{
				
				frmAddCritical.set("showSurvivalPeriod","N");
			}
			
			request.setAttribute("frmAddCriticalPlan",frmAddCritical);
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
		DynaActionForm frmAddCritical = (DynaActionForm)form;
		String strCaption = "";
		strCaption = (String)frmAddCritical.get("caption");
		ProdPolicyConfigManager prodPolicyConfigManager=this.getProductPolicyConfigObject();
		CashbenefitVO cashbenefitVO=new CashbenefitVO();
		cashbenefitVO=(CashbenefitVO)FormUtils.getFormValues(frmAddCritical, "frmAddCriticalPlan",this,mapping, request);
		cashbenefitVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
		String strActiveSubLink = TTKCommon.getActiveSubLink(request);
		String strForwardPath=null;
		
		
		if(strActiveSubLink.equals("Products"))
		{
			DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			Long lngProdSeqId = TTKCommon.getLong(frmProductDetail.getString("prodSeqId"));
			productCriticalSurvivalPeriod = (String)frmProductDetail.getString("survivalPeriod");
			log.info("do Save:::>>>>>>>>>>>>"+productCriticalSurvivalPeriod);
			cashbenefitVO.setProdSeqID(lngProdSeqId);
			cashbenefitVO.setShowSurvivalPeriod(productCriticalSurvivalPeriod);
			strForwardPath=strAddUpdateCriticalbenefit;
		}//end of if(strActiveSubLink.equals("Products"))
		else if(strActiveSubLink.equals("Policies"))
		{
			DynaActionForm frmPoliciesDetail =(DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			Long lngpolicySeqID = TTKCommon.getLong(frmPoliciesDetail.getString("policySeqID"));
			cashbenefitVO.setPolicySeqID(lngpolicySeqID);
			policiesCriticalSurvivalPeriod = (String)frmPoliciesDetail.getString("survivalPeriod");
			cashbenefitVO.setShowSurvivalPeriod(productCriticalSurvivalPeriod);
			strForwardPath=strAddUpdatePolicyCriticalbenefit;
		}//end of else if(strActiveSubLink.equals("Policies"))

		//prodPolicyConfigManager.savePlanDetail(planDetailVO);
		if(cashbenefitVO.getShowSurvivalPeriod().contains("Y"))
		{
			String survivalPeriod = request.getParameter("survivalPeriod");
			if((survivalPeriod=="")||(survivalPeriod==null))
			{
				request.setAttribute("survivalperiodvalidation","message.survivalperiod");
				frmAddCritical.set("showSurvivalPeriod","Y");
			}
			else
			{
				prodPolicyConfigManager.saveCriticalBenefitDetail(cashbenefitVO);
				request.setAttribute("updated","message.saved");
			}
		}
		else
		{
			prodPolicyConfigManager.saveCriticalBenefitDetail(cashbenefitVO);
			request.setAttribute("updated","message.saved");
			
		}
		
	//	frmAddCritical.set("showSurvivalPeriod","Y");
		
		
		
		if(frmAddCritical.getString("insCriticalBenefitSeqID").equals(""))
		{
			frmAddCritical.initialize(mapping);
			
			//request.setAttribute("updated","message.saved");
		}//end of if(frmPlanDetails.getString("prodPlanSeqID").equals(""))
		
			
			frmAddCritical.set("showSurvivalPeriod",cashbenefitVO.getShowSurvivalPeriod());
			//request.setAttribute("updated","message.savedSuccessfully");
	
		frmAddCritical.set("caption",strCaption);
		
		request.getSession().setAttribute("frmAddCriticalPlan",frmAddCritical);
		

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
			DynaActionForm frmAddCritical=(DynaActionForm)form;
			//DynaActionForm frmPlanList =(DynaActionForm)request.getSession().getAttribute("frmPlanList");
			String strinsCriticalBenefitSeqID = frmAddCritical.getString("insCriticalBenefitSeqID");
			String strcriticalBenefit = frmAddCritical.getString("criticalBenefit");
			String strPolicySeqID = frmAddCritical.getString("policySeqID");
			String strProdSeqId = frmAddCritical.getString("prodSeqId");
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			StringBuffer sbfCaption= new StringBuffer();
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strAddUpdateCriticalbenefit;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strAddUpdatePolicyCriticalbenefit;
			}//end of else

			//get the session bean from the bean pool for each excecuting thread
			ProdPolicyConfigManager prodPolicyConfigManager=this.getProductPolicyConfigObject();
			//create a new PlanDetail object
			CashbenefitVO CashbenefitVO=new CashbenefitVO();
			Long lnginsCriticalBenefitSeqID = null;
			//String strcriticalBenefit="HCB";
			if(frmAddCritical.getString("insCriticalBenefitSeqID").equals(""))
			{
				frmAddCritical.initialize(mapping);
				sbfCaption = sbfCaption.append("Add -").append(this.buildCaption(strActiveSubLink,request));
			}//end of if(frmPlanDetail.getString("prodPlanSeqID").equals(""))
			else
			{
				lnginsCriticalBenefitSeqID=TTKCommon.getLong(frmAddCritical.getString("insCriticalBenefitSeqID"));
				strcriticalBenefit=TTKCommon.getString(frmAddCritical.getString("criticalBenefit"));
				//get the plan details from the Dao object
				CashbenefitVO	=	prodPolicyConfigManager.getCriticalBenefitDetail(lnginsCriticalBenefitSeqID,strcriticalBenefit);
				sbfCaption = sbfCaption.append("Edit -").append(this.buildCaption(strActiveSubLink,request));
				frmAddCritical = (DynaActionForm)FormUtils.setFormValues("frmAddCriticalPlan",CashbenefitVO, this, mapping, request);
			}//end of else
			//frmAddCriticalPlan.set("caption",frmPlanList.get("caption"));
			frmAddCritical.set("caption",sbfCaption.toString());
			frmAddCritical.set("insCriticalBenefitSeqID", strinsCriticalBenefitSeqID);
			frmAddCritical.set("criticalBenefit", "CRITICAL_BENEFIT");
			frmAddCritical.set("policySeqID", strPolicySeqID);
			frmAddCritical.set("prodSeqId", strProdSeqId);
			request.setAttribute("frmAddCriticalPlan",frmAddCritical);
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
			return mapping.findForward(strCriticalBenefitClose);
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
			//Long lnginsCriticalBenefitSeqID=null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strAddUpdateCriticalbenefit;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strAddUpdatePolicyCriticalbenefit;
			}//end of else
			StringBuffer sbfCaption= new StringBuffer();
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmAddCritical =(DynaActionForm)form;
			//DynaActionForm frmPlanList =(DynaActionForm)request.getSession().getAttribute("frmPlanList");

			//create a new PlanDetail object
			CashbenefitVO cashbenefitVO=null;
			String strRowNum=request.getParameter("rownum");
			if(strActiveSubLink.equals("Products"))
			{
				DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
				productCriticalSurvivalPeriod = (String)frmProductDetail.getString("survivalPeriod");
			}
			else if(strActiveSubLink.equals("Policies"))
			{
				DynaActionForm frmPoliciesDetail =(DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
				policiesCriticalSurvivalPeriod = (String)frmPoliciesDetail.getString("survivalPeriod");
			}
			if(!(TTKCommon.checkNull(strRowNum).equals("")))
			{
				int iRowNum = TTKCommon.getInt(strRowNum);
				cashbenefitVO = (CashbenefitVO)tableData.getRowInfo(iRowNum);
				//get the product details from the Dao object
				
				
				
				cashbenefitVO=productPolicyConfig.getCriticalBenefitDetail(cashbenefitVO.getinsCriticalBenefitSeqID(),cashbenefitVO.getcriticalBenefit());
				//cashbenefitVO=productPolicyConfig.get.getCriticalBenefitDetail(cashbenefitVO.getcriticalBenefit());
				
				
				
				if(strActiveSubLink.equals("Products"))
				{
					cashbenefitVO.setShowSurvivalPeriod(productCriticalSurvivalPeriod);
				}
				else if(strActiveSubLink.equals("Policies"))
				{
					cashbenefitVO.setShowSurvivalPeriod(policiesCriticalSurvivalPeriod);
				}
				//log.info("Check--:"+cashbenefitVO.getShowSurvivalPeriod());
				frmAddCritical = (DynaValidatorForm)FormUtils.setFormValues("frmAddCriticalPlan",cashbenefitVO, this, mapping, request);
				
				
				
				sbfCaption = sbfCaption.append("Edit -").append(this.buildCaption(strActiveSubLink,request));
				request.setAttribute("frmAddCriticalPlan",frmAddCritical);
			}//end if(!(TTKCommon.checkNull(strRowNum).equals("")))
			if(cashbenefitVO.getProdSeqID()!=null)
			{
				frmAddCritical.set("prodSeqId", cashbenefitVO.getProdSeqID().toString());
			}//end of if(planDetailsVO.getProdSeqID()!=null)
			else if(cashbenefitVO.getPolicySeqID()!=null)
			{
				frmAddCritical.set("policySeqID", cashbenefitVO.getPolicySeqID().toString());
			}//end of else if(planDetailsVO.getPolicySeqID()!=null)
			//frmAddCriticalPlan.set("caption",frmPlanList.get("caption"));		
			frmAddCritical.set("caption",sbfCaption.toString());
			
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
