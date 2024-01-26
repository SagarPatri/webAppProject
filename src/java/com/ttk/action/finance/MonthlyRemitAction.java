/**
 * @ (#) MonthlyRemitAction.java August 10, 2009
 * Project      : TTK HealthCare Services
 * File         : MonthlyRemitAction.java
 * Author       : Navin Kumar R
 * Company      : Span Systems Corporation
 * Date Created : August 10, 2009
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.finance;

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
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.TDSRemittanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.MonthlyRemittanceVO;
import com.ttk.dto.finance.MonthlyRemittanceDetailVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class MonthlyRemitAction extends TTKAction {

	private static final Logger log = Logger.getLogger(MonthlyRemitAction.class );

	//Modes.
	private static final String strPageBackward="Backward";
	private static final String strPageForward="Forward";

	//Action mapping forwards.
	private static final String strMonthlyRemitList="monthlyremitlist";
	private static final String strGenerateMonthlyRemit="generatemonthlyremit";

	//Exception Message Identifier
	private static final String strMonthlyRemit="monthlyremit";

	private static final String strMonthlyRemitTable="MonthlyRemitTable";
	private static final String strTableData="tableData";

	/**
	 * This method is used to initialize the search grid.
	 * Finally it forwards to the appropriate view based on the specified forward mappings.
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													 HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doDefault method of MonthlyRemitAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			strForward = strMonthlyRemitList;
			strTable = strMonthlyRemitTable;
			tableData.createTableInfo(strTable,new ArrayList<Object>());
			if(!TTKCommon.isAuthorized(request, "Monthly Remittance Details"))
			{
				((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(false);
			}//end of if(!TTKCommon.isAuthorized(request, "Monthly Remittance Details"))
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMonthlyRemit));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)

	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings.
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doSearch method of MonthlyRemitAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			strForward = strMonthlyRemitList;
			strTable = strMonthlyRemitTable;
			if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry")))){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			//get the session bean from the bean pool for each executing thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			//get the table data from session if exists
			TableData tableData = TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!"".equals(strPageID) || !"".equals(strSortID))
			{
				if(!"".equals(strPageID))
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

			if(!TTKCommon.isAuthorized(request, "Monthly Remittance Details"))
			{
				((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(false);
			}//end of if(!TTKCommon.isAuthorized(request, "Monthly Remittance Details"))

			ArrayList<Object> alMonthlyRemitList = new ArrayList<Object>();
			alMonthlyRemitList = tdsRemittanceManager.getMonthlyTransferList(tableData.getSearchData());
			tableData.setData(alMonthlyRemitList, "search");
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
			return this.processExceptions(request, mapping, new TTKException(exp,strMonthlyRemit));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							//HttpServletResponse response)

	/**
	 * This method will add search criteria fields and values to the arraylist and will return it.
	 * @param frmViewMonthlyRemit formbean which contains the search fields
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmViewMonthlyRemit)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewMonthlyRemit.get("sInsuranceCompanyID")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewMonthlyRemit.get("sYear")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewMonthlyRemit.get("sMonthID")));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

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
			log.debug("Inside the doBackward method of MonthlyRemitAction");
			setLinks(request);
			String strForward = "";
			strForward = strMonthlyRemitList;
			//get the session bean from the bean pool for each excecuting thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strPageBackward);
			ArrayList<Object> alMonthlyRemitList = tdsRemittanceManager.getMonthlyTransferList(tableData.getSearchData());
			tableData.setData(alMonthlyRemitList, strPageBackward);
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMonthlyRemit));
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
			log.debug("Inside the doForward method of MonthlyRemitAction");
			setLinks(request);
			String strMapForward = "";
			strMapForward = strMonthlyRemitList;
			//get the session bean from the bean pool for each excecuting thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strPageForward);
			ArrayList<Object> alMonthlyRemitList = tdsRemittanceManager.getMonthlyTransferList(tableData.getSearchData());
			tableData.setData(alMonthlyRemitList, strPageForward);
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strMapForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMonthlyRemit));
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
	public ActionForward doGenerateMonthlyRemit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doGenerateMonthlyRemit method of MonthlyRemitAction");
			setLinks(request);
			DynaActionForm frmMonthlyRemitDetail= (DynaActionForm)form;
			frmMonthlyRemitDetail.initialize(mapping);
			frmMonthlyRemitDetail= (DynaActionForm)FormUtils.setFormValues("frmMonthlyRemitDetail", new MonthlyRemittanceDetailVO(),
																					 this, mapping, request);
			request.getSession().setAttribute("frmMonthlyRemitDetail",frmMonthlyRemitDetail);
			return this.getForward(strGenerateMonthlyRemit, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMonthlyRemit));
		}//end of catch(Exception exp)
	}//end of doGenerateMonthlyRemit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							//HttpServletResponse response)

	/**
	 * This method is used to get the record details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
								HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			log.debug("Inside the doView method of MonthlyRemitAction");
			MonthlyRemittanceVO monthlyRemittanceVO=null;
			MonthlyRemittanceDetailVO monthlyRemittanceDetailVO=null;
			DynaActionForm frmMonthlyRemitDetail= (DynaActionForm)form;
			TableData tableData =TTKCommon.getTableData(request);
			monthlyRemittanceVO=(MonthlyRemittanceVO)tableData.getRowInfo(Integer.parseInt((String)
																			request.getParameter("rownum")));

			//get the session bean from the bean pool for each executing thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			// calling business layer to save the bank account detail
			monthlyRemittanceDetailVO = tdsRemittanceManager.getMonthlyRemitDetail(monthlyRemittanceVO.getMasterSeqID());
			monthlyRemittanceDetailVO.setMonthlyRemitModify(true);

			frmMonthlyRemitDetail= (DynaActionForm)FormUtils.setFormValues("frmMonthlyRemitDetail", monthlyRemittanceDetailVO,
					 																this, mapping, request);
			request.getSession().setAttribute("frmMonthlyRemitDetail",frmMonthlyRemitDetail);

			return this.getForward(strGenerateMonthlyRemit, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMonthlyRemit));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to update the record.
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
								HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			log.debug("Inside the doSave method of MonthlyRemitAction");
			MonthlyRemittanceDetailVO monthlyRemittanceDetailVO=null;
			DynaActionForm frmMonthlyRemitDetail= (DynaActionForm)form;

			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			int iYear = Integer.parseInt(TTKCommon.checkNull(frmMonthlyRemitDetail.get("year").toString()));
			int iMonth = Integer.parseInt(TTKCommon.checkNull(frmMonthlyRemitDetail.get("month").toString()));
			String[] sDate = new String[3];
			sDate = userSecurityProfile.getLoginDate().split("/");
			
			if(iYear>Integer.parseInt(sDate[2]))
			{
				TTKException expTTK = new TTKException();
                expTTK.setMessage("error.finance.tds.monthlyremittance.year");
                throw expTTK;
			}//end of if(iYear>Integer.parseInt(sDate[2]))
			if(iYear < 2009)
			{
				TTKException expTTK = new TTKException();
                expTTK.setMessage("error.finance.tds.monthlyremittance.validyear");
                throw expTTK;
			}//end of if(iYear < 2009)
			if(iYear>=Integer.parseInt(sDate[2]) && iMonth>=Integer.parseInt(sDate[1]))
			{
				TTKException expTTK = new TTKException();
                expTTK.setMessage("error.finance.tds.monthlyremittance.month");
                throw expTTK;
			}//end of if(iYear>=Integer.parseInt(sDate[2]) && iMonth>=Integer.parseInt(sDate[1]))

			//if check box is not selected, the same page is retained.
			if(!"on".equalsIgnoreCase(request.getParameter("monthlyRemitModify"))) {
				//return this.getForward(strGenerateMonthlyRemit, mapping, request);
				TTKException expTTK = new TTKException();
				 expTTK.setMessage("error.finance.tds.monthlyremittance.disclaimer");
				 throw expTTK;
			}//end of if(!"on".equalsIgnoreCase(request.getParameter("monthlyRemitModify")))

			//Update Details.
			monthlyRemittanceDetailVO = (MonthlyRemittanceDetailVO)FormUtils.getFormValues(frmMonthlyRemitDetail, this, mapping, request);
			monthlyRemittanceDetailVO.setUpdatedBy((Long)TTKCommon.getUserSeqId(request));

			//get the session bean from the bean pool for each executing thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			// calling business layer to save the bank account detail
			int iUpdate=tdsRemittanceManager.saveMonthlyRemittance(monthlyRemittanceDetailVO);

			//set the appropriate message
			if(iUpdate>0) 
			{
				request.setAttribute("updated","message.saved");
			}//end of if(iUpdate>0)
			else
			{
				request.setAttribute("exists","message.finance.tds.monthly");
			}//end of else
			return this.getForward(strGenerateMonthlyRemit, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMonthlyRemit));
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
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doReset method of MonthlyRemitAction");
			setLinks(request);
			DynaActionForm frmMonthlyRemitDetail=(DynaActionForm)form;
			frmMonthlyRemitDetail.initialize(mapping);
			frmMonthlyRemitDetail= (DynaActionForm)FormUtils.setFormValues("frmMonthlyRemitDetail", new MonthlyRemittanceDetailVO(),
					 																this, mapping, request);
			request.getSession().setAttribute("frmMonthlyRemitDetail",frmMonthlyRemitDetail);
			return this.getForward(strGenerateMonthlyRemit, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMonthlyRemit));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)

	/**
	 * This method is used to navigate to monthly remittance list screen.
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
													HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doClose method of MonthlyRemitAction");
			setLinks(request);
			return this.doDefault(mapping, form, request, response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMonthlyRemit));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							//HttpServletResponse response)

	/**
	 * Returns the TDSRemittanceManager session object for invoking methods on it.
	 * @return TDSRemittanceManager session object which can be used for method invokation
	 * @throws TTKException if any error occurs
	 */
	private TDSRemittanceManager getTDSRemittanceManagerObject() throws TTKException
	{
		TDSRemittanceManager tdsRemittanceManager = null;
		try
		{
			if(tdsRemittanceManager == null)
			{
				InitialContext ctx = new InitialContext();
				tdsRemittanceManager = (TDSRemittanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/TDSRemittanceManagerBean!com.ttk.business.finance.TDSRemittanceManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMonthlyRemit);
		}//end of catch
		return tdsRemittanceManager;
	}//end getTDSRemittanceManagerObject()

}//end of MonthlyRemitAction
