/**
 * @ (#) CardRulesAction.java Nov 08, 2005
 * Project       : TTK HealthCare Services
 * File          : CardRulesAction.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Nov 08, 2005
 * @author       : Bhaskar Sandra
 * Modified by   : Lancy A
 * Modified date :
 * Reason        :
 */

package com.ttk.action.administration;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.CardRuleVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.enrollment.PolicyDetailVO;

/**
 * This class is reusable for Defining the Card Rules  in Administration Products and Policies.
 */
public class CardRulesAction extends TTKAction {
	private static Logger log = Logger.getLogger(CardRulesAction.class);

	//Action mapping forwards.
	private static final String strProductCardRules="productcardrules";
	private static final String strPolicyCardRules="policycardrules";
	private static final String strCloseCardRules="closecardrules";

	//Exception Message Identifier
	private static final String strcardrulesExp="cardrules";

	/**
	 * This method is used to initialize the detail screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDefineCardRules(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doDefineCardRules of CardRulesAction");
			setLinks(request);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            if(TTKCommon.getWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                if(strActiveSubLink.equals("Products"))
                {
                    expTTK.setMessage("error.product.required");
                }//end of if(strActiveSubLink.equals("Products"))
                else if(strActiveSubLink.equals("Policies"))
                {
                	expTTK.setMessage("error.policy.required");
                }//end of else if(strActiveSubLink.equals("Policies"))
                throw expTTK;
            }//end of if(TTKCommon.getWebBoardId(request)==null)

			String strForwards="";
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			DynaActionForm frmCardRules = (DynaActionForm)form;
			DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
			StringBuffer sbfCaption= new StringBuffer();
			ProductVO productVO = null;
			PolicyDetailVO policyDetailVO = null;
			frmCardRules=(DynaActionForm)form;
			frmCardRules.initialize(mapping);
			//Building Caption
			if(strActiveSubLink.equals("Products"))
			{
				productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
				sbfCaption.append("Define Card Rules - [").append(productVO.getCompanyName()).
							 append("] [").append(formOffice.get("officeCode")).append("] [").
							 		   append(productVO.getProductName()).append("]");
				strForwards=strProductCardRules;
				frmCardRules.set("product","Y");
				frmCardRules.set("close","Y");
				request.getSession().setAttribute("cardRuleFor", "Products");
				request.getSession().removeAttribute("alCardRules");
			}//end of if(strActiveSubLink.equals("Products"))
			else if(strActiveSubLink.equals("Policies"))
			{
				policyDetailVO=productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId(request),
																		  TTKCommon.getUserSeqId(request));
				sbfCaption.append("Define Card Rules - [").append(policyDetailVO.getCompanyName()).append("]");
				strForwards=strPolicyCardRules;
				frmCardRules.set("product","N");
				frmCardRules.set("close","N");
				
				request.getSession().setAttribute("cardRuleFor", "Policies");
			}//end of else if(strActiveSubLink.equals("Policies"))

			frmCardRules.set("caption",String.valueOf(sbfCaption));
			if(strActiveSubLink.equals("Policies"))
			{
				String strEnrollTypeCode="COR";
				policyDetailVO=productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId(request),
						                                                  TTKCommon.getUserSeqId(request));
				ArrayList alCardRules=productPolicyManagerObject.getCardRule(strEnrollTypeCode,new Long(
								  TTKCommon.getWebBoardId(request)),policyDetailVO.getInsuranceSeqID());
				request.getSession().setAttribute("alCardRules",alCardRules);
			}//end of if(strActiveSubLink.equals("Policies"))
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strcardrulesExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
		log.debug("Inside doChangeWebBoard of CardRulesAction");
		return doDefineCardRules(mapping,form,request,response);
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
		String strForwards="";
		try{
			log.debug("Inside doSave of CardRulesAction");
			setLinks(request);
			
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			DynaActionForm frmCardRules = (DynaActionForm)form;
			DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
			PolicyDetailVO policyDetailVO = null;
			String strInsSeqID = "";
			if(formOffice != null){
				strInsSeqID = formOffice.get("insSeqID").toString();
			}//end of if(formOffice != null)
			String strEnrollTypeCode=null;
			ArrayList alCardRules = null;
			if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyCardRules;
				strEnrollTypeCode="COR";
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			else if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				if(frmCardRules.get("enrollTypeCode")!=null)
				{
					strEnrollTypeCode=(String)frmCardRules.get("enrollTypeCode");
				}//end of if(frmCardRules.get("enrollTypeCode")!=null)
				strForwards=strProductCardRules;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Products"))
			CardRuleVO cardRuleVO=new CardRuleVO();
			String strCardRulesSeqIdList[]=request.getParameterValues("cardRuleSeqId");
			Long lCardRulesSeqIdList[]=null;
			if(strCardRulesSeqIdList!=null)
			{
				lCardRulesSeqIdList=new Long[strCardRulesSeqIdList.length];
				for(int i=0;i<strCardRulesSeqIdList.length;i++)
				{
					lCardRulesSeqIdList[i]=Long.parseLong(strCardRulesSeqIdList[i]);
				}//end of for(int i=0;i<strCardRulesSeqIdList.length;i++)
			} //end of if(strCardRulesSeqIdList!=null)
			cardRuleVO.setEnrollTypeId(strEnrollTypeCode);
			cardRuleVO.setProdPolicySeqId(new Long(TTKCommon.getWebBoardId(request)));
			cardRuleVO.setCardRulesSeqIdList(lCardRulesSeqIdList);
			cardRuleVO.setCardRuleTypeIdList(request.getParameterValues("cardRuleTypeId"));
			cardRuleVO.setGeneralTypeIdList(request.getParameterValues("generalTypeId"));
			cardRuleVO.setShortRemarksList(request.getParameterValues("shortRemarks"));
			cardRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			if(TTKCommon.getActiveSubLink(request).equals("Products")){
				cardRuleVO.setInsSeqID(TTKCommon.getLong(strInsSeqID));
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			if(TTKCommon.getActiveSubLink(request).equals("Policies")){
				policyDetailVO=productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId(request),
																		  TTKCommon.getUserSeqId(request));
				cardRuleVO.setInsSeqID(policyDetailVO.getInsuranceSeqID());
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			 
			
			int iResult = productPolicyManagerObject.updateCardRule(cardRuleVO);
			
			if(iResult==1)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(iResult==1)
			if(TTKCommon.getActiveSubLink(request).equals("Products")){
				alCardRules=productPolicyManagerObject.getCardRule(strEnrollTypeCode,new Long(
									 TTKCommon.getWebBoardId(request)),new Long(strInsSeqID));
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies")){
				policyDetailVO=productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId(request),
																	      TTKCommon.getUserSeqId(request));
				alCardRules=productPolicyManagerObject.getCardRule(strEnrollTypeCode,new Long(
						TTKCommon.getWebBoardId(request)),policyDetailVO.getInsuranceSeqID());
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			request.getSession().setAttribute("alCardRules",alCardRules);
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strForwards));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to display the options when we change the values in the combobox.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doEnrollTypeChanged(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															   HttpServletResponse response) throws Exception{
		try{
			log.info("Inside doEnrollTypeChanged of CardRulesAction");
			setLinks(request);
			String strForwards="";
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			DynaActionForm frmCardRule=(DynaActionForm)form;
			DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
			String strInsSeqID = "";
			if(formOffice != null){
				strInsSeqID = formOffice.get("insSeqID").toString();
			}//end of if(formOffice != null)
			String strEnrollTypeCode=null;
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strProductCardRules;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyCardRules;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			if(frmCardRule.get("enrollTypeCode")!=null)
			{
				strEnrollTypeCode=(String)frmCardRule.get("enrollTypeCode");
			}//end of if(frmCardRule.get("enrollTypeCode")!=null)
			
			if(TTKCommon.getActiveSubLink(request).equals("Products")){
				
				ArrayList alCardRules=productPolicyManagerObject.getCardRule(strEnrollTypeCode,new Long(
						   TTKCommon.getWebBoardId(request)),new Long(strInsSeqID));
                request.getSession().setAttribute("alCardRules",alCardRules);
			}else{
			ArrayList alCardRules=productPolicyManagerObject.getCardRule(strEnrollTypeCode,new Long(
										   TTKCommon.getWebBoardId(request)),new Long(strInsSeqID));
			request.getSession().setAttribute("alCardRules",alCardRules);
			}
			
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strcardrulesExp));
		}//end of catch(Exception exp)
	}//end of doEnrollTypeChanged(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
			log.debug("Inside doClose of CardRulesAction");
			setLinks(request);
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			DynaActionForm frmCardRules=(DynaActionForm)form;
			TableData tableData=null;
			if(request.getSession().getAttribute("tableData")!=null)
			{
				//Getting TableData from the Session
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if(request.getSession().getAttribute("tableData")!=null)
			else
			{
				//Creating the new Instance of the TableData
				tableData=new TableData();
			}//end of else
			//refresh the data in cancel mode, in order to get the new records if any.
			ArrayList alOfficeList=productPolicyManagerObject.getOfficeCodeList(tableData.getSearchData());
			tableData.setData(alOfficeList);
			request.getSession().setAttribute("tableData",tableData);
			frmCardRules.initialize(mapping);
			return this.getForward(strCloseCardRules, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strcardrulesExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
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
			}//end if(productPolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "cardRules");
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManagerObject()
}// end of class CardRulesAction
