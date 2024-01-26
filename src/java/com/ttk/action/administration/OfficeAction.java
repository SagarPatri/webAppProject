/**
 * @ (#) OfficeAction.java Dec 2nd, 2005
 * Project       : TTK HealthCare Services
 * File          : OfficeAction.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Dec 2nd, 2005
 * @author       : Bhaskar Sandra
 * Modified by   : Chandrasekaran J
 * Modified date : Mar 15th 2006
 * Reason        :
 * Modified by   : Lancy A
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
import com.ttk.dto.administration.OfficeVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.common.SearchCriteria;

/**
 * This class is reusable for searching the List of Offices  in Administration CardRules and Circulars.
 */
public class OfficeAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( OfficeAction.class ); 
	
	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	
	//string for forwarding
	private static final String strCardrules="cardrules";
	private static final String strCircular="circular";
	private static final String strDefineCardrules="definecardrules";
	private static final String strDefineCircular="definecirculars";
	
	//Exception Message Identifier
	private static final String strOfficeExp="officelist";
	
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
			log.debug("Inside the Default method of OfficeAction");
			setLinks(request);
			if(TTKCommon.getWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.product.required");
				throw expTTK;
			}//end of if
			String strOffice="";
			if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			{
				strOffice=strCardrules;
			}//end of if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			{
				strOffice=strCircular;
			}// end of else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			TableData tableData=null;
			tableData=TTKCommon.getTableData(request);
			tableData = new TableData();
			//get the current product by calling the database and add that to session
			ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
			request.getSession().setAttribute("productVO",productVO);
			//create the required grid table
			tableData.createTableInfo("OfficeListTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping); 
			getCaption((DynaActionForm)form,request);
			return this.getForward(strOffice, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOfficeExp));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
	/**
	 * This method is used to initialize the search grid when we change the record in web board.
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
		log.debug("Inside the doChangeWebBoard method of OfficeAction");
		return doDefault(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doSearch method of OfficeAction");
			setLinks(request);
			String strOffice="";
			if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			{
				strOffice=strCardrules;
			}//end of if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			{
				strOffice=strCircular;
			}// end of else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			long lngInsSeqID = 0;
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			TableData tableData=null;
			tableData=TTKCommon.getTableData(request);
			//get the current product by calling the database and add that to session
			ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
			lngInsSeqID = productVO.getInsSeqId();
			request.getSession().setAttribute("productVO",productVO);
			ArrayList alOfficeList=null;
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				((DynaActionForm)form).initialize(mapping);//reset the form data
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					//modify the search data
					tableData.modifySearchData("sort");					     
				}///end of if(!strPageID.equals(""))
				else
				{
//					Set the current page to be displayed
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
															    getParameter("pageId")))); 
					return mapping.findForward(strOffice);
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("OfficeListTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,lngInsSeqID));
				tableData.modifySearchData("search");
			}//end of else
			alOfficeList=productPolicyManagerObject.getOfficeCodeList(tableData.getSearchData());
			tableData.setData(alOfficeList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strOffice,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOfficeExp));
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
			log.debug("Inside the doBackwardt method of OfficeAction");
			setLinks(request);
			String strOffice="";
			if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			{
				strOffice=strCardrules;
			}//end of if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			{
				strOffice=strCircular;
			}// end of else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alOfficeList=productPolicyManagerObject.getOfficeCodeList(tableData.getSearchData());
			//set the table data
			tableData.setData(alOfficeList, strBackward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strOffice,mapping,request); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOfficeExp));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doForward method of OfficeAction");
			setLinks(request);
			String strOffice="";
			if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			{
				strOffice=strCardrules;
			}//end of if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			{
				strOffice=strCircular;
			}// end of else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			//modify the search data
			tableData.modifySearchData(strForward);
			ArrayList alOfficeList=productPolicyManagerObject.getOfficeCodeList(tableData.getSearchData());
			//set the table data
			tableData.setData(alOfficeList, strForward);
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strOffice,mapping,request); //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOfficeExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to navigate to another search screen .
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
			log.debug("Inside the doDefineCardRules method of OfficeAction");
			setLinks(request);
			DynaActionForm frmOffice = (DynaActionForm)form;
			String strDefineOffice="";
			if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			{
				strDefineOffice=strDefineCardrules;
			}//end of if(TTKCommon.getActiveTab(request).equals("Card Rules"))
			else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			{
				strDefineOffice=strDefineCircular;
			}// end of else if(TTKCommon.getActiveTab(request).equals("Circulars"))
			TableData tableData = TTKCommon.getTableData(request);
			if(!((String)request.getParameter("rownum")).equals(""))
			{
				OfficeVO officeVO=(OfficeVO)tableData.getRowInfo(Integer.parseInt((String)request.
																		 getParameter("rownum")));
				frmOffice.set("officeCode",officeVO.getOfficeCode());
				frmOffice.set("insSeqID",officeVO.getInsSeqId().toString());
				//frmOffice.set("productSeqID",officeVO.getProductSeqId().toString());
			}//end of if(!((String)request.getParameter("rownum")).equals(""))
			request.getSession().setAttribute( "frmOffice",frmOffice);
			return mapping.findForward(strDefineOffice);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOfficeExp));
		}//end of catch(Exception exp)
	}//end of doDefineCardRules(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)	
	
	/**
	 * This method is used to manipulate the caption .
	 *
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @throws Exception if any error occurs
	 */
	private void getCaption(DynaActionForm frmOffice,HttpServletRequest request)throws TTKException
	{
		try
		{
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
			//Building the caption
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("List of Offices - [").append(productVO.getCompanyName()).append("]");
			frmOffice.set("caption",String.valueOf(sbfCaption));
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "cardRules");
		}//end of catch
	}//end of getCaption(DynaActionForm frmOffice,HttpServletRequest request)
	
	/**
	 * Returns the ProductManager session object for invoking methods on it.
	 * @return ProductManager session object which can be used for method invokation
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
			throw new TTKException(exp, "cardRules");
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManagerObject()
	
	/**
	 * Return the ArrayList populated with Search criteria elements
	 * @param DynaActionForm
	 * @param HttpServletRequest
	 * @return ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmOfficeList,HttpServletRequest request,long lngInsSeqID)
	{
		ArrayList <Object> alSearchParams =new ArrayList<Object>();
		alSearchParams.add(String.valueOf(lngInsSeqID));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmOfficeList.get("sOfficeCode")));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmOfficeList,HttpServletRequest request)
	
}//end of OfficeListAction
