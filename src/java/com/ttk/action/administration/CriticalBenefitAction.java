//KOC FOR PRAVEEN CRITICAL BENEFIT
/**
 * @ (#) CashBenefitAction.java   MAY 20, 2013
 * Project        : TTK HealthCare Services
 * File           : CashBenefitAction.java
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
import com.ttk.business.administration.ProdPolicyConfigManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

/**
 * This class is used to get the List of Plan for the product.
 * This also provides addition of plan for products.
 */

public class CriticalBenefitAction extends TTKAction
{
	private static Logger log = Logger.getLogger( CriticalBenefitAction.class );

	//Action mapping forwards.
	private static final String strProductCriticalBenefitList="CriticalBenefitlist";
	private static final String strPolicyCriticalBenefitList="policyCriticalBenefitlist";
	private static final String strCriticalBenefitClose="CriticalBenefitclose";


	//Exception Message Identifier
	private static final String strProduct="product";
	
	private static final String strBackward="Backward";
	private static final String strForward="Forward";

	/**
	 * This method is used to get List of Plans for the Product.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		try
		{
			log.debug("Inside the doDefault method of CriticalBenefitAction");
			setLinks(request);
			String strTable = "";
			DynaActionForm frmCriticalBenefitList =(DynaActionForm)form;
			StringBuffer sbfCaption=new StringBuffer();
			ArrayList alPlanList = new ArrayList();
			ProdPolicyConfigManager  productPolicyConfig= null;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			sbfCaption = sbfCaption.append(this.buildCaption(strActiveSubLink,request));
			String strForwardPath=null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductCriticalBenefitList;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyCriticalBenefitList;
			}//end of else
			frmCriticalBenefitList.set("caption",sbfCaption.toString());
			
			TableData tableData =TTKCommon.getTableData(request);
			if(tableData==null){
				//create new table data object
				tableData = new TableData();
			}//end of if(tableData==null) 	
			//create the required grid table
			strTable = "CriticalPlanListTable";
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			//if the page number or sort id is clicked
			if(!("").equals(strPageID) || !("").equals(strSortID))
			{
				if(!("").equals(strPageID))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strForwardPath);
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
				tableData.setSearchData(this.populateSearchCriteria(request));
				tableData.modifySearchData("search");
			}//end of else
			productPolicyConfig = this.getProductPolicyConfigObject();
			alPlanList = productPolicyConfig.getCriticalBenefitList(tableData.getSearchData());
			tableData.setData(alPlanList,"search");
			request.getSession().setAttribute("tableData",tableData);
			request.setAttribute("frmCriticalBenefitList",frmCriticalBenefitList);
			return this.getForward(strForwardPath, mapping, request);
		} // end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	} // end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 

	
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
													  HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doBackward method of CashBenefitAction");
			setLinks(request);
			String strForwardPath=null;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			ProdPolicyConfigManager  productPolicyConfig= null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductCriticalBenefitList;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyCriticalBenefitList;
			}//end of else
			//get the session bean from the bean pool for each excecuting thread
			 productPolicyConfig=this.getProductPolicyConfigObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);
			ArrayList alPlanList = productPolicyConfig.getCriticalBenefitList(tableData.getSearchData());
			tableData.setData(alPlanList, strBackward);
			request.getSession().setAttribute("tableData",tableData);
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
													 HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doForward method of CashBenefitAction");
			setLinks(request);
			String strForwardPath=null;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			ProdPolicyConfigManager  productPolicyConfig= null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductCriticalBenefitList;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyCriticalBenefitList;
			}//end of else
			//get the session bean from the bean pool for each excecuting thread
			productPolicyConfig=this.getProductPolicyConfigObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strForward);
			ArrayList alPlanList = productPolicyConfig.getCriticalBenefitList(tableData.getSearchData());
			tableData.setData(alPlanList, strForward);
			request.getSession().setAttribute("tableData",tableData);
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
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try
		{
			log.debug("Inside the doClose method of CashBenefitAction");
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
			}//end of if(productPolicyConfigManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch(Exception exp)
		return productPolicyConfigManager;
	}//end of getProductPolicyConfigObject()

	/**
	 * This method will add search criteria fields and values to the arraylist and will return it
	 * @param frmProductList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request) throws TTKException
	{
		//build the column names along with their values to be searched
		String strFlag = null;
		String strFlag1 = null;
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		//ArrayList<Object> alSearchParams1 = new ArrayList<Object>();
		Long lnginsCriticalBenefitSeqID=TTKCommon.getWebBoardId(request);
		Long lngUserSeqId=TTKCommon.getUserSeqId(request);
		alSearchParams.add(new Long(lnginsCriticalBenefitSeqID));
		
		String strcriticalBenefit=TTKCommon.getCriticalBenefitID(request);
		
		if("Products".equals(TTKCommon.getActiveSubLink(request)))
		{
			strFlag="PRD";
		}//end of if("Products".equals(TTKCommon.getActiveSubLink(request)))
		else if("Policies".equals(TTKCommon.getActiveSubLink(request)))
		{
			strFlag="POL";
		}//end of if("Policies".equals(TTKCommon.getActiveSubLink(request)))
		
	
	   
		alSearchParams.add(strFlag) ;	
		alSearchParams.add(new String(strcriticalBenefit));
		alSearchParams.add(new Long(lngUserSeqId));
		return alSearchParams;
		
		
	}//end of populateSearchCriteria(HttpServletRequest request)

	/**
	 * This method  prepares the Caption based on the flow and retunrs it
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
			//Comments the Database hit for Title Display.
			/*DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("[").append(frmProductDetail.getString("companyName")).append("]");
			sbfCaption.append("	[").append(frmProductDetail.getString("productName")).append("]");*/
			
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("[").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append(" [").append(frmProductList.get("productName")).append("]");			
			
		}//end of if(strActiveSubLink.equals("Products"))
		else if(strActiveSubLink.equals("Policies"))
		{
			DynaActionForm frmPoliciesDetail =(DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			sbfCaption.append("[").append(frmPoliciesDetail.getString("companyName")).append("]");
			sbfCaption.append("	[").append(frmPoliciesDetail.getString("policyNbr")).append("]");
		}//end of else if(strActiveSubLink.equals("Policies"))
		return sbfCaption.toString();
	}//end of buildCaption(String strActiveSubLink,HttpServletRequest request)
}//end of class CashBenefitAction
