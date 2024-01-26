/**
 * @ (#) PlanDetailsAction.java   June 30, 2008
 * Project        : TTK HealthCare Services
 * File           : PlanDetailsAction.java
 * Author         : Sendhil Kumar V
 * Company        : Span Systems Corporation
 * Date Created   : June 30, 2008
 *
 * @author        : Sendhil Kumar V
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
import com.ttk.dto.administration.PlanDetailVO;
import com.ttk.dto.administration.PlanVO;

import formdef.plugin.util.FormUtils;

public class PlanDetailsAction extends TTKAction
{
	private static Logger log = Logger.getLogger( PlanDetailsAction.class );

	//Action mapping forwards.
	private static final String strAddUpdatePlan="addupdateplan";
	private static final String strAddUpdatePolicyPlan="addupdatepolicyplan";
	private static final String strPlanClose="planclose";

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
	public ActionForward doPlanAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doPlanAdd method of PlanDetailsAction");
			setLinks(request);
			DynaActionForm frmPlanDetails = (DynaActionForm)form;
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strAddUpdatePlan;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strAddUpdatePolicyPlan;
			}//end of else
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption = sbfCaption.append("Add -").append(this.buildCaption(strActiveSubLink,request));
			frmPlanDetails.initialize(mapping);

			frmPlanDetails.set("caption",String.valueOf(sbfCaption));
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
			log.debug("Inside the doSave method of PlanDetailsAction");
			setLinks(request);
			DynaActionForm frmPlanDetails = (DynaActionForm)form;
			String strCaption = "";
			strCaption = (String)frmPlanDetails.get("caption");
			ProdPolicyConfigManager prodPolicyConfigManager=this.getProductPolicyConfigObject();
			PlanDetailVO planDetailVO=new PlanDetailVO();
			planDetailVO=(PlanDetailVO)FormUtils.getFormValues(frmPlanDetails, "frmAddPlan",this,mapping, request);
			planDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			
			if(strActiveSubLink.equals("Products"))
			{
				DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
				Long lngProdSeqId = TTKCommon.getLong(frmProductDetail.getString("prodSeqId"));
				planDetailVO.setProdSeqID(lngProdSeqId);
				strForwardPath=strAddUpdatePlan;
			}//end of if(strActiveSubLink.equals("Products"))
			else if(strActiveSubLink.equals("Policies"))
			{
				DynaActionForm frmPoliciesDetail =(DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
				Long lngpolicySeqID = TTKCommon.getLong(frmPoliciesDetail.getString("policySeqID"));
				planDetailVO.setPolicySeqID(lngpolicySeqID);
				strForwardPath=strAddUpdatePolicyPlan;
			}//end of else if(strActiveSubLink.equals("Policies"))

			prodPolicyConfigManager.savePlanDetail(planDetailVO);
			if(frmPlanDetails.getString("prodPlanSeqID").equals(""))
			{
				frmPlanDetails.initialize(mapping);
				request.setAttribute("updated","message.saved");
			}//end of if(frmPlanDetails.getString("prodPlanSeqID").equals(""))
			else
			{
				request.setAttribute("updated","message.savedSuccessfully");
			}// end of else
			frmPlanDetails.set("caption",strCaption);
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
			log.debug("Inside the doReset method of PlanDetailsAction");
			setLinks(request);
			DynaActionForm frmPlanDetails=(DynaActionForm)form;
			//DynaActionForm frmPlanList =(DynaActionForm)request.getSession().getAttribute("frmPlanList");
			String strProdPlanSeqID = frmPlanDetails.getString("prodPlanSeqID");
			String strPolicySeqID = frmPlanDetails.getString("policySeqID");
			String strProdSeqId = frmPlanDetails.getString("prodSeqId");
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			StringBuffer sbfCaption= new StringBuffer();
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strAddUpdatePlan;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strAddUpdatePolicyPlan;
			}//end of else

			//get the session bean from the bean pool for each excecuting thread
			ProdPolicyConfigManager prodPolicyConfigManager=this.getProductPolicyConfigObject();
			//create a new PlanDetail object
			PlanDetailVO planDetailsVO=new PlanDetailVO();
			Long lngProdPlanSeqId = null;
			if(frmPlanDetails.getString("prodPlanSeqID").equals(""))
			{
				frmPlanDetails.initialize(mapping);
				sbfCaption = sbfCaption.append("Add -").append(this.buildCaption(strActiveSubLink,request));
			}//end of if(frmPlanDetail.getString("prodPlanSeqID").equals(""))
			else
			{
				lngProdPlanSeqId=TTKCommon.getLong(frmPlanDetails.getString("prodPlanSeqID"));
				//get the plan details from the Dao object
				planDetailsVO	=	prodPolicyConfigManager.getPlanDetail(lngProdPlanSeqId);
				sbfCaption = sbfCaption.append("Edit -").append(this.buildCaption(strActiveSubLink,request));
				frmPlanDetails = (DynaActionForm)FormUtils.setFormValues("frmAddPlan",planDetailsVO, this, mapping, request);
			}//end of else
			//frmPlanDetails.set("caption",frmPlanList.get("caption"));
			frmPlanDetails.set("caption",sbfCaption.toString());
			frmPlanDetails.set("prodPlanSeqID", strProdPlanSeqID);
			frmPlanDetails.set("policySeqID", strPolicySeqID);
			frmPlanDetails.set("prodSeqId", strProdSeqId);
			request.setAttribute("frmAddPlan",frmPlanDetails);
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
			log.debug("Inside the doClose method of PlanDetailsAction");
			setLinks(request);
			return mapping.findForward(strPlanClose);
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
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strAddUpdatePlan;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strAddUpdatePolicyPlan;
			}//end of else
			StringBuffer sbfCaption= new StringBuffer();
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPlanDetails =(DynaActionForm)form;
			//DynaActionForm frmPlanList =(DynaActionForm)request.getSession().getAttribute("frmPlanList");

			//create a new PlanDetail object
			PlanDetailVO planDetailsVO=null;
			String strRowNum=request.getParameter("rownum");
			if(!(TTKCommon.checkNull(strRowNum).equals("")))
			{
				int iRowNum = TTKCommon.getInt(strRowNum);
				PlanVO planVO = (PlanVO)tableData.getRowInfo(iRowNum);
				//get the product details from the Dao object
				planDetailsVO=productPolicyConfig.getPlanDetail(planVO.getProdPlanSeqID());
				frmPlanDetails = (DynaValidatorForm)FormUtils.setFormValues("frmAddPlan",planDetailsVO, this, mapping, request);
				sbfCaption = sbfCaption.append("Edit -").append(this.buildCaption(strActiveSubLink,request));
				request.setAttribute("frmAddPlan",frmPlanDetails);
			}//end if(!(TTKCommon.checkNull(strRowNum).equals("")))
			if(planDetailsVO.getProdSeqID()!=null)
			{
				frmPlanDetails.set("prodSeqId", planDetailsVO.getProdSeqID().toString());
			}//end of if(planDetailsVO.getProdSeqID()!=null)
			else if(planDetailsVO.getPolicySeqID()!=null)
			{
				frmPlanDetails.set("policySeqID", planDetailsVO.getPolicySeqID().toString());
			}//end of else if(planDetailsVO.getPolicySeqID()!=null)
			//frmPlanDetails.set("caption",frmPlanList.get("caption"));		
			frmPlanDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("planDetailsVO",planDetailsVO);
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
