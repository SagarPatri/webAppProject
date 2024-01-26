/**
 * @ (#) ProductSearchAction.java Nov 14, 2005
 * Project      : TTK HealthCare Services
 * File         : ProductSearchAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Nov 14, 2005
 *
 * @author       : Arun K N
 * Modified by   : Lancy A
 * Modified date : Mar 10, 2006
 * Reason        : Changes in coding standards
 */

package com.ttk.action.administration;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.administration.ProdPolicyLimitVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.common.Toolbar;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for Searching the List of Products.
 * This also provides deletion and updation of products.
 */
public class ProductSearchAction extends TTKAction {

	private static final Logger log = Logger.getLogger( ProductSearchAction.class );

	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";

	// Action mapping forwards.
	private static final String strProductlist="productlist";
	private static final String strProductGeneralInfo="productgeneralinfo";
	private static final String strClauses="clauses";
	private static final String strCoverage="coverage";
	private static final String strSync="prodsync";
	private static final String strConfig="configuration";
	private static final String strLevelConfiguration="levelConfiguration";
	//Exception Message Identifier
	private static final String strProduct="product";
	
	private static final String strTableData="tableData";
	private static final String strRownum="rownum";
	private static final String strProductVO="productVO";
	
	private static final String strNewProductRules="NewProductRules";
	
	/**
	 * This method is used to initialize the search grid.
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
			log.debug("Inside the doDefault method of ProductSearchAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			strForward = strProductlist;
			strTable = "ProductSearchTable";
			tableData.createTableInfo(strTable,new ArrayList());
			request.getSession().setAttribute(strTableData,tableData);
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)

	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSearch method of ProductSearchAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			strForward = strProductlist;
			strTable = "ProductSearchTable";
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//get the session bean from the bean pool for each excecuting thread
			final ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			//get the tbale data from session if exists
			final TableData tableData =TTKCommon.getTableData(request);
			final String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			final String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!("").equals(strPageID) || !("").equals(strSortID))
			{
				if(!("").equals(strPageID))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strForward);
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo(strTable,null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alProductList = new ArrayList();
			alProductList = productPolicyObject.getProductList(tableData.getSearchData());
			tableData.setData(alProductList, "search");
			//set the table data object to session
			request.getSession().setAttribute(strTableData,tableData);
			//finally return to the grid screen
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							//HttpServletResponse response)

	/**
	 * This method is used to get the previous set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doBackward method of ProductSearchAction");
			setLinks(request);
			String strForward = "";
			strForward = strProductlist;
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);
			ArrayList alProductList = productPolicyObject.getProductList(tableData.getSearchData());
			tableData.setData(alProductList, strBackward);
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest
			//request,HttpServletResponse response)

	/**
	 * This method is used to get the next set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of ProductSearchAction");
			setLinks(request);
			String strMapForward = "";
			strMapForward = strProductlist;
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strForward);
			ArrayList alProductList = productPolicyObject.getProductList(tableData.getSearchData());
			tableData.setData(alProductList, strForward);
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strMapForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)

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
	public ActionForward doViewProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewProducts method of ProductSearchAction");
			setLinks(request);
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			DynaValidatorForm frmProductDetail = (DynaValidatorForm)form;
			//create a new Product object
			ProductVO productVO = new ProductVO();
			if(!(TTKCommon.checkNull(request.getParameter(strRownum)).equals("")))
			{
				productVO = (ProductVO)tableData.getRowInfo(Integer.parseInt((String)(frmProductDetail).
																						get(strRownum)));
				//get the product details from the Dao object
				productVO=productPolicyObject.getProductDetails(productVO.getProdPolicySeqID());
				//add the selected item to the web board and make it as default selected
				this.addToWebBoard(productVO, request);
				//this.valueObjectToForm(productVO, frmProductDetail);				
			}//end if(!(TTKCommon.checkNull(request.getParameter(strRownum)).equals("")))
			else if(TTKCommon.getWebBoardId(request) != null)
			{
				//if web board id is found, set it as current web board id
				Long lProdPolicySeqId=TTKCommon.getWebBoardId(request);
				//get the product details from the Dao object
				productVO=productPolicyObject.getProductDetails(lProdPolicySeqId);
			}//end of else if(TTKCommon.getWebBoardId(request) != null)
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.product.required");
				throw expTTK;
			}//end of else
			frmProductDetail = (DynaValidatorForm)FormUtils.setFormValues("frmProductGeneralInfo",productVO, this, mapping, request);
			frmProductDetail.set("caption","Edit");
			frmProductDetail.set("stopPreAuth",productVO.getStopPreAuthsYN());
			frmProductDetail.set("stopClaim",productVO.getStopClaimsYN());	
			frmProductDetail.set("opdClaim",productVO.getopdClaimsYN());//KOC 1286 for OPD Benefit	
            //KOC 1270 for hospital cash benefit            
			frmProductDetail.set("cashBenefit",productVO.getCashBenefitsYN()); 
			frmProductDetail.set("convCashBenefit",productVO.getConvCashBenefitsYN()); 			
			//KOC 1270 for hospital cash benefit			
            //added for KOC-1273
			frmProductDetail.set("criticalBenefit",productVO.getCriticalBenefitYN());
			frmProductDetail.set("survivalPeriod",productVO.getSurvivalPeriodYN());
			
			ArrayList<Object> hmInsCmpnys1 = null;
			ArrayList<CacheObject> hmInsCmpnys = null;
			ArrayList<Object> hmInsCmpnysLst = null;
			ArrayList<CacheObject> hmInsCmpnysAll = null;
			
			Long numberinscodeg = productVO.getInsSeqId();
			String inscode = String.valueOf(numberinscodeg);
			String healthAuthority=productVO.getHealthAuthority();
			String authority="";
			
			
			if("DHA".equals(healthAuthority)){
				authority="('DHA','MOH')";
			}
			else if("MOH".equals(healthAuthority)){
				authority="('DHA','MOH')";
			}
			else{
				authority="'"+healthAuthority+"'";
			}
			hmInsCmpnysLst = productPolicyObject.getInsCompnyList(authority);
			hmInsCmpnysAll=(ArrayList<CacheObject>) hmInsCmpnysLst.get(0);
			ProdPolicyLimitVO inscmpvo= productPolicyObject.getInsCmpCode(numberinscodeg);
			hmInsCmpnys1 = productPolicyObject.getCardTemplates(inscode);
			 hmInsCmpnys=(ArrayList<CacheObject>) hmInsCmpnys1.get(0);
			 String tempName = (String) hmInsCmpnys1.get(1);
			 long ltempName= Long.parseLong(tempName);
			 frmProductDetail.set("cardTempList",hmInsCmpnys);
			 frmProductDetail.set("alInsuranceCompany",hmInsCmpnysAll);
			
				if((productVO.getTemplateID()!=null)&&(ltempName== productVO.getTemplateID()))	{
				
			 if("INS128".equals(inscmpvo.getIns_Cmp_code())){
				
				 
			 if(hmInsCmpnys1.get(1)!=null)  frmProductDetail.set("templateID",(String) hmInsCmpnys1.get(1));
		 }
		 else{
			 
			 frmProductDetail.set("templateID","");
		 }
			}
		  hmInsCmpnys=(ArrayList<CacheObject>) frmProductDetail.get("cardTempList");
			request.getSession().setAttribute("frmProductGeneralInfo",frmProductDetail);
			this.documentViewer(request,productVO);
			//add the product to session
			request.getSession().setAttribute(strProductVO,productVO);
			return this.getForward(strProductGeneralInfo, mapping, request);
			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doViewProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)

	/**
	 * This method is used to navigate to clause screen to view selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewProductClause(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doViewProductClause method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmProductList =(DynaActionForm)form;
			frmProductList.set("identifier", request.getParameter("identifier"));
			request.getSession().setAttribute("frmProductList",frmProductList);
			return mapping.findForward(strClauses);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doViewProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to clause screen to view selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewProductCoverage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doViewProductCoverage method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmProductList =(DynaActionForm)form;
			frmProductList.set("identifier", request.getParameter("identifier"));
			request.getSession().setAttribute("frmProductList",frmProductList);
			return mapping.findForward(strCoverage);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doViewProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to clause screen to view selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewProductSync(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewProductSync method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmProductList = (DynaActionForm)form;
			//get the session bean from the bean pool for each excecuting thread
			TableData tableData = TTKCommon.getTableData(request);
			//create a new Product object
			ProductVO productVO = new ProductVO();
			if(!(TTKCommon.checkNull(request.getParameter(strRownum)).equals("")))
			{
				productVO = (ProductVO)tableData.getRowInfo(Integer.parseInt((String)(request).getParameter(strRownum)));
				this.addToWebBoard(productVO, request);
				request.setAttribute("webboardinvoked","true");
			}//end of if(!(TTKCommon.checkNull(request.getParameter(strRownum)).equals("")))
			frmProductList.set("companyName",productVO.getCompanyName());
			frmProductList.set("productName",productVO.getProductName());
			return mapping.findForward(strSync);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doViewProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)

	/**
	 * This method is called from the struts framework.
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
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAdd method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmProductDetail = (DynaActionForm)form;
			frmProductDetail.initialize(mapping);
			frmProductDetail.set("caption","Add");
			frmProductDetail.set("refundFalg","N");
			return this.getForward(strProductGeneralInfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)

	
	
	public ActionForward changeInsCmp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		
		try{
			log.debug("Inside the changeInsCmp method of ProductSearchAction");
			setLinks(request);
			ArrayList<Object> hmInsCmpnys1 = null;
			ArrayList<CacheObject> hmInsCmpnys = null;
			DynaActionForm frmProductGeneralInfo=(DynaActionForm)form;
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			//ProdPolicyLimitVO inscmpvo = null;
			
			String inscode=TTKCommon.checkNull(request.getParameter("insSeqId"));
			if(!"".equals(inscode)){
			long linscode= Long.parseLong(inscode);
			
			ProdPolicyLimitVO inscmpvo= productPolicyObject.getInsCmpCode(linscode);
			
			hmInsCmpnys1 = productPolicyObject.getCardTemplates(inscode);
			 hmInsCmpnys=(ArrayList<CacheObject>) hmInsCmpnys1.get(0);
		
			
			 String vatpercent = productPolicyObject.getVatPercent(linscode);
			 
			 
			 if(inscode!=""){
				 frmProductGeneralInfo.set("vatPercent",vatpercent);
			 }
			 else{
				 frmProductGeneralInfo.set("vatPercent","");
			 }
			 
			 frmProductGeneralInfo.set("cardTempList",hmInsCmpnys);
			
				 if("INS128".equals(inscmpvo.getIns_Cmp_code())){
				 
				 if(hmInsCmpnys1.get(1)!=null)  frmProductGeneralInfo.set("templateID",(String) hmInsCmpnys1.get(1));
			 }
			 else{
				 frmProductGeneralInfo.set("templateID","");
			 }
			}//if(!"".equals(inscode)){
			else {
				 frmProductGeneralInfo.set("templateID","");
				 frmProductGeneralInfo.set("vatPercent","");
				 frmProductGeneralInfo.set("cardTempList",new ArrayList<>());
			}
			return this.getForward(strProductGeneralInfo, mapping, request);
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public ActionForward changeAuthority(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
	
		
		
		try {
			log.debug("Inside the changeAuthority method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmProductGeneralInfo=(DynaActionForm)form;
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			ArrayList<Object> hmInsCmpnys1 = null;
			ArrayList<CacheObject> hmInsCmpnys = null;
			String healthAuthority=request.getParameter("healthAuthority");
			 String authority="";
			if("DHA".equals(healthAuthority)){
				
				authority="('DHA','MOH')";
				
			}
			
			else if("MOH".equals(healthAuthority)){
				
				authority="('DHA','MOH')";
				
			}
			else{
				authority="'"+healthAuthority+"'";
			}
			
			hmInsCmpnys1 = productPolicyObject.getInsCompnyList(authority);
			hmInsCmpnys=(ArrayList<CacheObject>) hmInsCmpnys1.get(0);

			frmProductGeneralInfo.set("insFlag", "YES");
			frmProductGeneralInfo.set("alInsuranceCompany",hmInsCmpnys);
			
			return this.getForward(strProductGeneralInfo, mapping, request);
		} 
		
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}
		
		
	}
	
	
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
			log.debug("Inside the doSave method of ProductSearchAction");
			setLinks(request);
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			DynaActionForm frmProductDetail = (DynaActionForm)form;
			
			frmProductDetail.set("caption","Edit");
			Object[] objArrayResult = new Object[2];
			String strCompanyAbbreviation="";
			Long  lProdPolicySeqId=TTKCommon.getLong((String)frmProductDetail.get("prodPolicySeqID"));
			ProductVO productVO=new ProductVO();
			productVO=(ProductVO)FormUtils.getFormValues(frmProductDetail, "frmProductGeneralInfo",this,
																						mapping, request);
			
			productVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			//if conditions for stop pre-auth and claim
			if("Y".equals(TTKCommon.checkNull(frmProductDetail.get("stopPreAuth"))))
			{
				productVO.setStopPreAuthsYN("Y");
			}
			else
			{
				productVO.setStopPreAuthsYN("N");
			}
			if("Y".equals(TTKCommon.checkNull(frmProductDetail.get("stopClaim"))))
			{
				productVO.setStopClaimsYN("Y");
			}
			else
			{
				productVO.setStopClaimsYN("N");
			}
			
			//KOC 1286 for OPD Benefit
			
			if("Y".equals(TTKCommon.checkNull(frmProductDetail.get("opdClaim"))))
			{
				productVO.setopdClaimsYN("Y");
			}
			else
			{
				productVO.setopdClaimsYN("N");
			}
			//KOC 1286 for OPD Benefit
			
			
			//KOC 1270 for hospital cash benefit
			if("Y".equals(TTKCommon.checkNull(frmProductDetail.get("cashBenefit")))) 
			{
				
				productVO.setCashBenefitsYN("Y");
			}
			else
			{
				
				productVO.setCashBenefitsYN("N");
			}
			if("Y".equals(TTKCommon.checkNull(frmProductDetail.get("convCashBenefit")))) 
			{
				
				productVO.setConvCashBenefitsYN("Y");
			}
			else
			{
				
				productVO.setConvCashBenefitsYN("N");
			}
			//KOC 1270 for hospital cash benefit
			
			//added for KOC-1273
			if("Y".equals(TTKCommon.checkNull(frmProductDetail.get("criticalBenefit"))))
			{
				productVO.setCriticalBenefitYN("Y");
			}
			else
			{
				productVO.setCriticalBenefitYN("N");
			}
			if("Y".equals(TTKCommon.checkNull(frmProductDetail.get("survivalPeriod"))))
			{
				productVO.setSurvivalPeriodYN("Y");
			}else
			{
				productVO.setSurvivalPeriodYN("N");
			}
			//ended
			//call the dao method to add or update the product
			objArrayResult=productPolicyObject.addUpdateProduct(productVO);
			//get prodPolicySeqId and company Abbrevation Code from Object
			lProdPolicySeqId=(Long)objArrayResult[0];
			strCompanyAbbreviation=(String)objArrayResult[1];
			//set the appropriate message and add the product to webboard
			if(lProdPolicySeqId>0)
			{
				if(!frmProductDetail.get("prodPolicySeqID").equals(""))
				{
					String strProductName=productVO.getProductName();
					if(strProductName.length()>35){
						strProductName=strProductName.substring(0,35);
					}//end of if(strProductName.length()>35)						
					request.setAttribute("cacheId",lProdPolicySeqId.toString());
					request.setAttribute("cacheDesc",strProductName+" ("+strCompanyAbbreviation+")");
					TTKCommon.modifyWebBoardId(request);
					request.setAttribute("updated","message.savedSuccessfully");
					
					//for clearing the dropdown values
					frmProductDetail.set("productList",new ArrayList());
					frmProductDetail.set("insuranceSeqId",Long.valueOf(0));//new Long(0));
				}//end of if(!frmProductDetail.get("prodPolicySeqID").equals(""))
				else
				{
					productVO=productPolicyObject.getProductDetails(lProdPolicySeqId);
					frmProductDetail = (DynaValidatorForm)FormUtils.setFormValues("frmProductGeneralInfo",
																		productVO, this, mapping, request);
					frmProductDetail.set("caption","Edit");
					request.getSession().setAttribute("frmProductGeneralInfo",frmProductDetail);
					productVO.setCompanyAbbreviation(strCompanyAbbreviation);
					this.addToWebBoard(productVO,request);
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
				//refresh the Cache of products
				
				ArrayList<Object> hmInsCmpnys1 = null;
				ArrayList<Object> hmInsCmpnysLst = null;
				
				
				ArrayList<CacheObject> hmInsCmpnys = null;
				ArrayList<CacheObject> hmInsCmpnysAll = null;
				String inscode=request.getParameter("insSeqId");
				String healthAuthority=request.getParameter("healthAuthority");
				
				long linscode= Long.parseLong(inscode);
				ProdPolicyLimitVO inscmpvo= productPolicyObject.getInsCmpCode(linscode);
				hmInsCmpnys1 = productPolicyObject.getCardTemplates(inscode);
				 hmInsCmpnys=(ArrayList<CacheObject>) hmInsCmpnys1.get(0);
				 String tempName = (String) hmInsCmpnys1.get(1);
				 long ltempName= Long.parseLong(tempName);
				
				 String authority="";
					if("DHA".equals(healthAuthority)){
						authority="('DHA','MOH')";
					}
					else if("MOH".equals(healthAuthority)){
						authority="('DHA','MOH')";
					}
					else{
						authority="'"+healthAuthority+"'";
					}
					hmInsCmpnysLst = productPolicyObject.getInsCompnyList(authority);
					hmInsCmpnysAll=(ArrayList<CacheObject>) hmInsCmpnysLst.get(0);
				 
				 frmProductDetail.set("alInsuranceCompany",hmInsCmpnysAll);
				 frmProductDetail.set("cardTempList",hmInsCmpnys);
				if(productVO.getTemplateID()!=null  && productVO.getTemplateID().longValue()!=0L)
				{
				if(productVO.getTemplateID()!=null&&(productVO.getTemplateID())== ltempName)	{
				 if("INS128".equals(inscmpvo.getIns_Cmp_code())){
					 
					 if(hmInsCmpnys1.get(1)!=null)  frmProductDetail.set("templateID",(String) hmInsCmpnys1.get(1));
				 }
				 else{
					 frmProductDetail.set("templateID","");
				 }
				}
				}
				
				Cache.refresh("productCode");
				//add the product to session
				request.getSession().setAttribute(strProductVO,productVO);
				this.documentViewer(request,productVO);				
			}//end of if(lProdPolicySeqId>0)
			return this.getForward(strProductGeneralInfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
		try{
			log.debug("Inside the doChangeWebBoard method of ProductSearchAction");
			setLinks(request);
			//if web board id is found, set it as current web board id
			//TTKCommon.setWebBoardId(request);
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			Long lProdPolicySeqId=TTKCommon.getWebBoardId(request);
			
			//create a new Product object
			ProductVO productVO = new ProductVO();
			DynaActionForm frmProductDetail = (DynaActionForm)form;
		/*	frmProductDetail.initialize(mapping);*/
			//get the product details from the Dao object
			productVO=productPolicyObject.getProductDetails(lProdPolicySeqId);
			
			if(productVO != null){
			frmProductDetail = (DynaActionForm)FormUtils.setFormValues("frmProductGeneralInfo",productVO, this, mapping, request);
			ProdPolicyLimitVO inscmpvo=null;
			Long numberinscodeg = productVO.getInsSeqId();
			Long tempid = productVO.getTemplateID();
			String ltempid = String.valueOf(tempid);
			String inscode = null;
			String tempName = null;
			
			ArrayList<Object> hmInsCmpnys1 = null;
			ArrayList<CacheObject> hmInsCmpnys = null;
			//if(numberinscodeg!=null){
			 inscode = String.valueOf(numberinscodeg);
			
			 inscmpvo= productPolicyObject.getInsCmpCode(numberinscodeg);
			 hmInsCmpnys1 = productPolicyObject.getCardTemplates(inscode);
			 hmInsCmpnys=(ArrayList<CacheObject>) hmInsCmpnys1.get(0);
			 tempName = (String) hmInsCmpnys1.get(1);
			
			 frmProductDetail.set("cardTempList",hmInsCmpnys);
			 if(tempName==ltempid){
             if("INS128".equals(inscmpvo.getIns_Cmp_code())){
			 
			 if(hmInsCmpnys1.get(1)!=null)  frmProductDetail.set("templateID",tempName);
		      }
			}
			
			}
			
			

			
			frmProductDetail.set("caption","Edit");
			request.getSession().setAttribute("frmProductGeneralInfo",frmProductDetail);
			//add the product to session
			request.getSession().setAttribute(strProductVO,productVO);
			this.documentViewer(request,productVO);
			return this.getForward(strProductGeneralInfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doReset method of ProductSearchAction");
			setLinks(request);
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			DynaActionForm frmProductDetail=(DynaActionForm)form;
			ProductVO productVO=new ProductVO();
			if(!frmProductDetail.get("prodPolicySeqID").equals(""))
			{
				Long lProdPolicySeqId=TTKCommon.getWebBoardId(request);
				frmProductDetail.initialize(mapping);
				//get the product details from the Dao object
				productVO=productPolicyObject.getProductDetails(lProdPolicySeqId);
				frmProductDetail = (DynaActionForm)FormUtils.setFormValues("frmProductGeneralInfo",
																productVO, this, mapping, request);
				frmProductDetail.set("caption","Edit");
				request.getSession().setAttribute("frmProductGeneralInfo",frmProductDetail);
				//add the product to session
				request.getSession().setAttribute(strProductVO,productVO);
				this.documentViewer(request,productVO);
			}//end of if(!productGeneralForm.get("prodPolicySeqId").equals(""))
			else
			{
				frmProductDetail.initialize(mapping);
				frmProductDetail.set("caption","Add");
			}//end of else
			return this.getForward(strProductGeneralInfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
															HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug(" Inside doClose method of ProductSearchAction");
			setLinks(request);
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
			//if((Object)request.getSession().getAttribute("searchparam") != null)
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				//refresh the data in cancel mode, in order to get the new records if any.
				ArrayList alProductList = productPolicyObject.getProductList(tableData.getSearchData());
				tableData.setData(alProductList);
				request.getSession().setAttribute(strTableData,tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if
			return this.getForward("productlist", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strProduct));
		}//end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to copy the selected records to web-board.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														    HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doCopyToWebBoard method of ProductSearchAction");
			setLinks(request);
			this.populateWebBoard(request);
			return this.getForward(strProductlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of CopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)

	/**
	 * This method is used to load the product based on appropriate insurance company.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeInsurance(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														    HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doChangeInsurance method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmProductDetail=(DynaActionForm)form;
//			get the session bean from the bean pool for each excecuting thread
			PolicyManager getPolicyManagerObject=this.getPolicyManagerObject();
			ArrayList alProduct=getPolicyManagerObject.getInsProductList(TTKCommon.getLong(frmProductDetail.get("insuranceSeqId").toString()));
			frmProductDetail.set("productList",alProduct);
			return this.getForward(strProductGeneralInfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of CopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)

	/**
	 * This method is used to copy the product rule for the selected product.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCopyRules(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														    HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doCopyRules method of ProductSearchAction");
			setLinks(request);
			DynaActionForm frmProductDetail=(DynaActionForm)form;
//			get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			int iResult=productPolicyObject.copyProductRules(TTKCommon.getLong(frmProductDetail.get("productSeqID").toString()),TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
			if(iResult>0)
			{
				request.setAttribute("updated","message.addedSuccessfully");
			}//end of if(iResult>0)
			//for clearing the dropdown values
			frmProductDetail.set("productList",new ArrayList());
			frmProductDetail.set("insuranceSeqId",Long.valueOf(0));//new Long(0));
			return this.getForward(strProductGeneralInfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of CopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)

	/**
	 * This method is used to bring out the Configuration List screen
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */	
	public ActionForward doConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doConfiguration method of ProductSearchAction");
			setLinks(request);				
			StringBuffer sbfCaption= new StringBuffer();			
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("[").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append("[").append(frmProductList.get("productName")).append("]");			
			request.getSession().setAttribute("ConfigurationTitle", sbfCaption.toString());			
			return this.getForward(strConfig, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//	HttpServletResponse response)

	/**
	 * This method is used to delete the selected records from the list.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDeleteList method of ProductSearchAction");
			setLinks(request);
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			String[] strDeleteIds ={"",""};
			ArrayList<Object> alDeleteProducts = new ArrayList<Object>();
			//populate the delete string which contains the sequence id's to be deleted
			strDeleteIds = populateDeleteId(request, (TableData)request.getSession().getAttribute(strTableData));
			//prepare the parameters
			alDeleteProducts.add("|"+strDeleteIds[0]+"|");
			alDeleteProducts.add(TTKCommon.getUserSeqId(request));
			//delete the Product Details
			int iCount = productPolicyObject.deleteProduct(alDeleteProducts);
			if(iCount>0)
			{
				//delete the product's from the web board if any based on ProdPolicySeqIds
				request.setAttribute("cacheId",strDeleteIds[1]);
				TTKCommon.deleteWebBoardId(request);
			}//end of if(iCount>0)
			//refresh the grid with search data in session
			ArrayList alProductList = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the
			//current set of search criteria
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
																			getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alProductList = productPolicyObject.getProductList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alProductList = productPolicyObject.getProductList(tableData.getSearchData());
			}//end of else
			tableData.setData(alProductList, "Delete");
			request.getSession().setAttribute(strTableData,tableData);
			return mapping.findForward(strProductlist);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)

	
	/**
     * This method is used to getLicenceNumbers based on professional ID
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward getProductCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the getProductCode method of HospitalSearchAction");
            		setLinks(request);
            		ArrayList alProfessionals	=	null;
            		ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
            		String prodname	=	request.getParameter("prodname");
            		
            		alProfessionals= productPolicyObject.getProductCode(prodname);
            		PrintWriter out = response.getWriter();  
        	        response.setContentType("text/xml");  
        	        response.setHeader("Cache-Control", "no-cache");  
        	        response.setStatus(HttpServletResponse.SC_OK);  
        	        if(alProfessionals!=null)
        	        	if(alProfessionals.get(0)!=null || alProfessionals.get(0)!="")
        	        		out.write(alProfessionals.get(0)+"");  
        	        out.flush();  
            		
            		return null;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
        		}//end of catch(Exception exp)
            }//end of getProductCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return productPolicyManager;
	}//end getProductManagerObject()

	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PolicyManager getPolicyManagerObject() throws TTKException
	{
		PolicyManager policyManager = null;
		try
		{
			if(policyManager == null)
			{
				InitialContext ctx = new InitialContext();
				policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return policyManager;
	}//end getProductManagerObject()

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmProductList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmProductList)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		//alSearchParams.add(new SearchCriteria("DEFAULT_PLAN_YN","'Y','N'"));
		alSearchParams.add(new SearchCriteria("TPA_INS_PRODUCT.INS_SEQ_ID",(String)frmProductList.
															get("insuranceCompany"),"equals"));
		alSearchParams.add(new SearchCriteria("PRODUCT_NAME",TTKCommon.replaceSingleQots((String)frmProductList.
																					 get("sProductName"))));
		alSearchParams.add(new SearchCriteria("PROD_STATUS_GENERAL_TYPE_ID",(String)frmProductList.
															get("productStatusCode"),"equals"));
		alSearchParams.add(new SearchCriteria("INS_PRODUCT_CODE",(String)frmProductList.
				get("sProductCode"),"equals"));
		// AK
				alSearchParams.add(new SearchCriteria("AUTHORITY_TYPE",(String)frmProductList.
						get("productAuthority"),"equals"));
				//EAK
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

	/**
	 * Adds the selected item to the web board and makes it as the selected item in the web board
	 * @param  productVO ProductVO object which contains the information of the products
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void addToWebBoard(ProductVO productVO, HttpServletRequest request)
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		
		String strProductName=null;
		 strProductName=productVO.getProductName();
		if((strProductName!=null)&&(strProductName.length()>35))
		{
			strProductName=strProductName.substring(0,35);
		}//end of if(strProductName.length()>35)
	
		cacheObject.setCacheId(productVO.getProdPolicySeqID().toString());
		cacheObject.setCacheDesc(strProductName+" ("+productVO.getCompanyAbbreviation()+")");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);
		//if the object(s) are added to the web board, set the current web board id
		//if(toolbar.getWebBoard().addToWebBoardList(alCacheObject))
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
	}//end of addToWebBoard(HospitalVO hospitalVO, HttpServletRequest request)

	/**
	 * Populates the web board in the session with the selected items in the grid
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void populateWebBoard(HttpServletRequest request)
	{
		String[] strChk = request.getParameterValues("chkopt");
		TableData tableData = (TableData)request.getSession().getAttribute(strTableData);
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = null;
		ProductVO productVO=new ProductVO();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the
			//matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				String strProductName="";
				cacheObject = new CacheObject();
				productVO=(ProductVO)tableData.getData().get(Integer.parseInt(strChk[i]));
				strProductName=productVO.getProductName();
				if(strProductName.length()>35)
				{
					strProductName=strProductName.substring(0,35);
				}//end of if(strProductName.length()>35)
				cacheObject.setCacheId(productVO.getProdPolicySeqID().toString());
				cacheObject.setCacheDesc(strProductName +" ("+productVO.getCompanyAbbreviation()+")");
				alCacheObject.add(cacheObject);
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)

		if(toolbar != null){
			toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		}//end of if(toolbar != null) 
			
	}//end of populateWebBoard(HttpServletRequest request)

	/**
	 * Returns array of string which contains the pipe separated Product sequence id's and Product Policy Seq ids
	 *  to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String[] which contains the pipe separated Product sequence id's and pipe seperated ProdPolicySeq ids
	 *  to be deleted
	 */
	private String[] populateDeleteId(HttpServletRequest request, TableData tableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		String[] strDeleteIds = {"",""};
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching
			//check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						strDeleteIds[0] = String.valueOf(((ProductVO)tableData.getData().
										get(Integer.parseInt(strChk[i]))).getProdSeqId());
						strDeleteIds[1] = String.valueOf(((ProductVO)tableData.getData().
								 get(Integer.parseInt(strChk[i]))).getProdPolicySeqID());
					}//end of if(i==0)
					else
					{
						strDeleteIds[0] = strDeleteIds[0] +"|"+ ((ProductVO)tableData.getData().
											   get(Integer.parseInt(strChk[i]))).getProdSeqId();
						strDeleteIds[1] = strDeleteIds[1] +"|"+ ((ProductVO)tableData.getData().
										 get(Integer.parseInt(strChk[i]))).getProdPolicySeqID();
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		return strDeleteIds;
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)


	/**
	 * This menthod for document viewer information
	 * @param request HttpServletRequest object which contains hospital information.
	 * @param policyDetailVO PolicyDetailVO object which contains policy information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request, ProductVO productVO) throws TTKException
	{
//		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink="+TTKCommon.getActiveLink(request));
		alDocviewParams.add("product_seq_id="+productVO.getProdSeqId());
		if(request.getSession().getAttribute("toolbar")!=null)
		{
			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
		}//end of if(request.getSession().getAttribute("toolbar")!=null)
	}//end of documentViewer(HttpServletRequest request, ProductVO productVO)

	
	//added for hyundaibuffer by rekha 0n 20.06.2014
	
	/**
	 * This method is used to navigate to clause screen to view selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	
	
	
	
	
	
	
	
	
	public ActionForward modificationRemarks(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		
		try {
			ProductVO productVO=new ProductVO();
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			productVO.setActiveTabLink(strActiveSubLink);
			if("Products".equals(strActiveSubLink)){
				
				 DynaActionForm frmProductGeneralInfo=(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
				 String productseqid= TTKCommon.checkNull((String)frmProductGeneralInfo.get("prodSeqId"));
				// Long lProdPolicySeqId=TTKCommon.getWebBoardId(request);
				 
				 productVO.setProductSeqId(productseqid);
				 
			}
			
			else{
		           
		           DynaActionForm frmPoliciesEdit=(DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
					 String policySeqID= TTKCommon.checkNull((String)frmPoliciesEdit.get("policySeqID"));
					 productVO.setPolicySeqID(Long.parseLong(policySeqID));
			}
	           
	           ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
	           
	          ArrayList<String[]>	modificationRemarksList= productPolicyObject.getModificationRemarks(productVO);
	          
	          //request.getSession().setAttribute("modificationRemarksList", modificationRemarksList);	
	          request.setAttribute("modificationRemarksList", modificationRemarksList);
			
			 return mapping.findForward("modificationRemarks");
		} 
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
		
	}
	
	
	
	
	
		
	
	public ActionForward onCloseModifiedRemarks(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
				
		
		
		
		
		
		
		
		return null;	
		
	}
	
	
	
	
	
	
	
}//end of ProductSearchAction