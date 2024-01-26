/**
 * @ (#) AuthGroupListAction.java  Aug 12, 2008
 * Project        : Vidal Health TPA Services
 * File           : AuthGroupListAction.java
 * Author         : Sendhil Kumar V
 * Company        : Span Systems Corporation
 * Date Created   : Aug 12, 2008
 *
 * @author        : Sendhil Kumar V
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
 * This class is used to get the List of Auth Group for the product.
 * This also provides addition of Auth Group for products.
 */
public class AuthGroupListAction extends TTKAction
{
	private static Logger log = Logger.getLogger( AuthGroupListAction.class );

	//Action mapping forwards.
	private static final String strAuthGroupList="authgrouplist";
	private static final String strAuthGroupClose="authgroupclose";

	//Exception Message Identifier
	private static final String strProduct="product";

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
			log.debug("Inside the doDefault method of AuthGroupListAction");
			setLinks(request);
			String strTable = "";
			DynaActionForm frmAuthGroupList =(DynaActionForm)form;
			DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			StringBuffer sbfCaption=new StringBuffer();
			ArrayList alPlanList = new ArrayList();
			ProdPolicyConfigManager  productPolicyConfig= null;
			
			sbfCaption = sbfCaption.append(this.buildCaption(frmProductDetail));
			frmAuthGroupList.set("caption",sbfCaption.toString());
			
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			if(tableData==null)
			{
				//create new table data object
				tableData = new TableData();
			}//end of if(tableData==null) 	
			
			//create the required grid table
			strTable = "AuthGroupListTable";
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strAuthGroupList));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				tableData.createTableInfo(strTable,null);
				tableData.setSearchData(this.populateSearchCriteria(request));
				tableData.modifySearchData("search");
			}// end of else	
			
			productPolicyConfig = this.getProductPolicyConfigObject();
			alPlanList = productPolicyConfig.getAuthGroupList(tableData.getSearchData());
			tableData.setData(alPlanList,"search");
			request.getSession().setAttribute("tableData",tableData);
			request.setAttribute("frmAuthGroupList",frmAuthGroupList);
			return this.getForward(strAuthGroupList, mapping, request);
		} // end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}// end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 

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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doClose method of AuthGroupListAction");
			setLinks(request);
			return mapping.findForward(strAuthGroupClose);
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
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		Long lngProdPolicySeqId=TTKCommon.getWebBoardId(request);
		Long lngUserSeqId=TTKCommon.getUserSeqId(request);
		alSearchParams.add(new Long(lngProdPolicySeqId));
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
	private String buildCaption(DynaActionForm frmProductDetail)throws TTKException
	{
		StringBuffer sbfCaption=new StringBuffer();
		sbfCaption.append("[").append(frmProductDetail.getString("companyName")).append("]");
		sbfCaption.append("	[").append(frmProductDetail.getString("productName")).append("]");
		return sbfCaption.toString();
	}//end of buildCaption(DynaActionForm frmProductDetail)
}//end of AuthGroupListAction class
