/**

* @ (#) CourierListAction.java Jul 26, 2006
* Project       : TTK HealthCare Services
* File          : CourierListAction.java
* Author        : Chandrasekaran J
* Company       : Span Systems Corporation
* Date Created  : Jul 26, 2006

* @author       : Chandrasekaran J
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.inwardentry;

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
import com.ttk.business.enrollment.CourierManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.CourierVO;

/**
 * This class is used for searching the List of Couriers.
 */
public class CourierListAction extends TTKAction
{
	
	private static Logger log = Logger.getLogger(CourierListAction.class );
	
	//declrations of modes
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	
	//Action mapping forwards
	private static final String strCourier="couriersearch";
	private static final String strClaimDetails="claimdetails";
	
	//Exception Message Identifier
	private static final String strClaimsExp="claims";
	
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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDefault method of CourierListAction");
			setLinks(request);
			TableData  tableDataCourier = (TableData)request.getSession().getAttribute("tableDataCourier");
//			create new table data object
			tableDataCourier = new TableData();
			//create the required grid table
			tableDataCourier.createTableInfo("CourierListTable",new ArrayList());
			request.getSession().setAttribute("tableDataCourier",tableDataCourier);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			return this.getForward(strCourier, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimsExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSearch method of CourierListAction");
			setLinks(request);
			TableData  tableDataCourier = (TableData)request.getSession().getAttribute("tableDataCourier");
			CourierManager courierManagerObject=this.getCourierManagerObject();
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableDataCourier.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strCourier));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableDataCourier.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableDataCourier.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableDataCourier.createTableInfo("CourierListTable",null);
				tableDataCourier.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableDataCourier.modifySearchData("search");
			}//end of else
			
			
			log.info("outside table");
			ArrayList alCourier= courierManagerObject.getInwardCourierList(tableDataCourier.getSearchData());
			tableDataCourier.setData(alCourier, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableDataCourier",tableDataCourier);
			//finally return to the grid screen
			return this.getForward(strCourier, mapping, request);        	}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimsExp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doBackward method of CourierListAction");
			setLinks(request);
			TableData  tableDataCourier = (TableData)request.getSession().getAttribute("tableDataCourier");
			CourierManager courierManagerObject=this.getCourierManagerObject();
			tableDataCourier.modifySearchData(strBackward);//modify the search data
			ArrayList alCourier= courierManagerObject.getInwardCourierList(tableDataCourier.getSearchData());
			tableDataCourier.setData(alCourier, strBackward);//set the table data
			request.getSession().setAttribute("tableDataCourier",tableDataCourier);//set the tableData object to session
			return this.getForward(strCourier, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimsExp));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of CourierListAction");
			setLinks(request);
			TableData  tableDataCourier = (TableData)request.getSession().getAttribute("tableDataCourier");
			CourierManager courierManagerObject=this.getCourierManagerObject();
			tableDataCourier.modifySearchData(strForward);//modify the search data
			ArrayList alCourier= courierManagerObject.getInwardCourierList(tableDataCourier.getSearchData());
			tableDataCourier.setData(alCourier, strForward);//set the table data
			request.getSession().setAttribute("tableDataCourier",tableDataCourier);//set the tableData object to session
			return this.getForward(strCourier, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimsExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to select the record in the screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectCourier(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSelectCourier method of CourierListAction");
			setLinks(request);
			CourierVO courierVO=null;
			DynaActionForm frmClaimDetails= null;
			TableData  tableDataCourier = (TableData)request.getSession().getAttribute("tableDataCourier");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				courierVO=(CourierVO)tableDataCourier.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
				frmClaimDetails=(DynaActionForm)request.getSession().getAttribute("frmClaimDetails");
				frmClaimDetails.set("courierNbr",courierVO.getCourierNbr());
				frmClaimDetails.set("courierSeqID",String.valueOf(courierVO.getCourierSeqID()));
				frmClaimDetails.set("frmChanged","changed");
			}//if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			request.getSession().removeAttribute("tableDataCourier");//remove the tableData from the session
			return this.getForward(strClaimDetails,mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimsExp));
		}//end of catch(Exception exp)
	}//end of doSelectCourier(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of CourierListAction");
			setLinks(request);
			return this.getForward(strClaimDetails, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimsExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the CourierManager session object for invoking methods on it.
	 * @return CourierManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private CourierManager getCourierManagerObject() throws TTKException
	{
		CourierManager courierManager = null;
		try
		{
			if(courierManager == null)
			{
				InitialContext ctx = new InitialContext();
				courierManager = (CourierManager) ctx.lookup("java:global/TTKServices/business.ejb3/CourierManagerBean!com.ttk.business.enrollment.CourierManager");
			}//end of if(courierManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "support");
		}//end of catch
		return courierManager;
	}//end getCourierManagerObject()
	
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmCourierList DynaActionForm
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCourierList,HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCourierList.get("sCourierNbr")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCourierList.get("sdocketPODNbr")));
		alSearchParams.add((String)frmCourierList.get("sCourierName"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCourierList.get("sStartDate")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCourierList.get("sEndDate")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmCourierList,HttpServletRequest request)
	
}//end of CourierListAction class
