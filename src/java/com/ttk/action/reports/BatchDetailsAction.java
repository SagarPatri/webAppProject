/**
 * @ (#) BatchDetailsAction.java   July 10, 2008
 * Project        : TTK HealthCare Services
 * File           : BatchDetailsAction.java
 * Author         : Sanjay.G.Nayaka
 * Company        : Span Systems Corporation
 * Date Created   : July 10, 2008
 *
 * @author        : Sanjay.G.Nayaka
 * Modified by    :
 * Modified date  :
 * Reason         :
 */

package com.ttk.action.reports;

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

import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.ReportsVO;

public class BatchDetailsAction extends TTKAction
{
	private static Logger log = Logger.getLogger(BatchDetailsAction.class);

	//Action Forwards
	private static final String strBatchList ="finbatchreportlist";
	private static final String strBatchDetail="batchdetail";
	private static final String strClaimsPending ="claimpendrptdetail";
	private static final String strClaimsReportDetail="claimsreportdetail";

	// Exception Message Identifier
	private static final String strReportExp = "report";

	private static final String strBackward="Backward";
	private static final String strForward="Forward";

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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.info("Inside the doDefault method of BatchDetailsAction");
			setLinks(request);
			String strTable = "";
			DynaActionForm frmBatchDetail =(DynaActionForm)form;
			frmBatchDetail.initialize(mapping);
			DynaActionForm frmReportList =(DynaActionForm) request.getSession().getAttribute("frmReportList");
			frmBatchDetail.set("reportName", frmReportList.getString("reportName"));
			if(!frmReportList.getString("batchNo").equals(""))
			{
				String strBatchNo = frmReportList.getString("batchNo");
				frmBatchDetail.set("batchNumber", strBatchNo);
			}// end of if(!frmReportList.getString("batchNo").equals(""))
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			strTable = "BatchDetailsTable";
			tableData.createTableInfo(strTable,new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			return (mapping.findForward(strBatchDetail));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specifiedforward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form the optional ActionForm bean for this request (if any)
	 * @param request the HTTP request we are processing
	 * @param response the HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSearch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		try
		{
			log.debug("Inside the doSearch method of BatchDetailsAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			strForward = strBatchDetail;
			strTable = "BatchDetailsTable";
			// get the session bean from the bean pool for each excecuting thread
			FloatAccountManager floatAccountObject = this.getFloatAccountManagerObject();
			// get the table data from session if exists
			TableData tableData = TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));

			//if the page number or sort id is clicked
			if (!strPageID.equals("") || !strSortID.equals(""))
			{
				if (!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strForward);
				}//end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");// modify the search data
				}// end of else
			}// end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				// create the required grid table
				tableData.createTableInfo(strTable, null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm) form, request));
				tableData.modifySearchData("search");
			}// end of else
			ArrayList alBatchList = new ArrayList();
			alBatchList = floatAccountObject.getBatchList(tableData.getSearchData());
			tableData.setData(alBatchList,"search");
			// set the table data object to session
			request.getSession().setAttribute("tableData", tableData);
			// finally return to the grid screen
			return this.getForward(strForward, mapping, request);
		}// end of try
		catch (TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doClose(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		try
		{
			setLinks(request);
			String strActiveLink=TTKCommon.getActiveLink(request);
            String strSubLinks=TTKCommon.getActiveSubLink(request);
			DynaActionForm frmReportList =(DynaActionForm) request.getSession().getAttribute("frmReportList");
			if(strActiveLink.equals("Maintenance"))
			{
				if(strSubLinks.equals("Finance"))
				{
					log.info("in doclose of batchdetails");
					return this.getForward("chequemaintainence", mapping, request);
				}
			}
			log.info("report name in close"+ frmReportList.get("reportName"));
			if(frmReportList.get("reportName").equals("Finance Batch Report"))
			{
				return this.getForward(strBatchList, mapping, request);
			}//end of if(frmReportList.get("reportName").equals("Finance Batch Report"))
			else if(frmReportList.get("reportName").equals("Claims Pending Report"))
			{
				return this.getForward(strClaimsPending, mapping, request);
			}//end of else if(frmReportList.get("reportName").equals("Claims Pending Report"))
			else if(frmReportList.get("reportName").equals("Claims Detail Report"))
			{
				return this.getForward(strClaimsReportDetail, mapping, request);
			}//end of else if(frmReportList.get("reportName").equals("Claims Detail Report"))
		}// end of try
		catch (TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
		}// end of catch(Exception exp)
		return null;
	}//end of doClose(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)

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
	public ActionForward doViewBatchNumber(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doViewBatchNumber method of BatchDetailsAction");
			setLinks(request);
			DynaActionForm frmReportList =(DynaActionForm) request.getSession().getAttribute("frmReportList");
			TableData tableData =TTKCommon.getTableData(request);
			String strRowNum=request.getParameter("rownum");
			String strActiveLink=TTKCommon.getActiveLink(request);
            String strSubLinks=TTKCommon.getActiveSubLink(request);

			if(!(TTKCommon.checkNull(strRowNum).equals("")))
			{
				int iRowNum = TTKCommon.getInt(strRowNum);
				ReportsVO  reportsVO = (ReportsVO)tableData.getRowInfo(iRowNum);

				if(strActiveLink.equals("Maintenance"))
				 {
						if(strSubLinks.equals("Finance"))
						{
							frmReportList.set("batchNo",reportsVO.getBatchNo());
							frmReportList.set("fileName", reportsVO.getFileName());
							request.getSession().setAttribute("frmReportList",frmReportList);
							return this.getForward("chequemaintainence", mapping, request);
						}//end of if(strSubLinks.equals("Finance"))
				}//end of  if(strActiveLink.equals("Maintenance"))
				if(frmReportList.get("reportName").equals("Finance Batch Report"))
				{
					frmReportList.set("batchNo",reportsVO.getBatchNo());
					request.getSession().setAttribute("frmReportList",frmReportList);
					return this.getForward(strBatchList, mapping, request);
				}//end of if(frmReportList.get("reportName").equals("Finance Batch Report"))
				else if(frmReportList.get("reportName").equals("Claims Pending Report"))
				{
					frmReportList.set("batchNo",reportsVO.getBatchNo());
					frmReportList.set("floatAccNo", reportsVO.getFloatAccNo());
					frmReportList.set("reportName", "Claims Pending Report");
					request.getSession().setAttribute("frmReportList",frmReportList);
					return this.getForward(strClaimsPending, mapping, request);
				}//end of else if(frmReportList.get("reportName").equals("Claims Pending Report"))
				else
				{
					frmReportList.set("batchNo",reportsVO.getBatchNo());
					frmReportList.set("floatAccNo", reportsVO.getFloatAccNo());
					frmReportList.set("reportName", "Claims Detail Report");
					request.getSession().setAttribute("frmReportList",frmReportList);
					return this.getForward(strClaimsReportDetail, mapping, request);
				}//end of else
			}//end of if(!(TTKCommon.checkNull(strRowNum).equals("")))
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.batchNo.required");
				throw expTTK;
			}//end of else
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doViewBatchNumber(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside the doBackward method of BatchDetails Action ");
			setLinks(request);
			String strForward = "";
			strForward = strBatchDetail;
			//get the session bean from the bean pool for each executing thread
			FloatAccountManager floatAccountObject = this.getFloatAccountManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);
			ArrayList alBatchList = new ArrayList();
			alBatchList = floatAccountObject.getBatchList(tableData.getSearchData());
			tableData.setData(alBatchList, strBackward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
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
			log.debug("Inside the doForward method of BatchDetailsAction ");
			setLinks(request);
			String strMapForward = "";
			strMapForward = strBatchDetail;
			//get the session bean from the bean pool for each executing thread
			FloatAccountManager floatAccountObject = this.getFloatAccountManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strForward);
			ArrayList alBatchList = new ArrayList();
			alBatchList = floatAccountObject.getBatchList(tableData.getSearchData());
			tableData.setData(alBatchList, strForward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strMapForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * Returns the FloatAccountManager session object for invoking methods on it
	 * @return FloatAccountManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getFloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if (floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}// end if(floatAccountManager == null)
		}// end of try
		catch (Exception exp)
		{
			throw new TTKException(exp, "report");
		}// end of catch(Exception exp)
		return floatAccountManager;
	}// end of getFloatAccountManagerObject()

	/**
	 * This method will add search criteria fields and values to the arraylist
	 * @param frmBatchDetail formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmBatchDetail, HttpServletRequest request)throws TTKException
	{
		// build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String) frmBatchDetail.get("floatAccNumber")));
		alSearchParams.add((String) frmBatchDetail.get("batchNumber"));
		alSearchParams.add((String) frmBatchDetail.get("startDate"));
		alSearchParams.add((String) frmBatchDetail.get("endDate"));
		if(frmBatchDetail.getString("reportName").equals("Claims Pending Report"))
		{
			alSearchParams.add("EFT");
		}//end of if(frmBatchDetail.getString("reportName").equals("Claims Pending Report"))
		else
		{
			alSearchParams.add("");
		}//end of else
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmBatchDetail,HttpServletRequest request)
}//end of BatchDetailsAction
