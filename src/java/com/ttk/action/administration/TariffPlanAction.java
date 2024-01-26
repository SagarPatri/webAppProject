/**
 * @ (#) TariffPlanAction.java Sep 29, 2005
 * Project      : TTK HealthCare Services
 * File         : TariffPlansAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Sep 29, 2005
 *
 * @author       :  Arun K N
 * Modified by   :
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
import com.ttk.business.administration.TariffManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.RevisionPlanVO;
import com.ttk.dto.administration.TariffPlanVO;
import com.ttk.dto.common.SearchCriteria;


/**
 * This class is used for Searching the List of Plans.
 * This also provides deletion and updation of Plans.
 */
public class TariffPlanAction extends TTKAction
{

	private static Logger log = Logger.getLogger( TariffPlanAction.class );

	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";

	//forwards links
	private static final String strTariffplans="tariffplans";
	private static final String strEditTariffplans="edittariffplan";

	//Exception Message Identifier
	private static final String strTariffplansExp="tariffplan";

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
			log.debug("Inside the doDefault method of TariffplanAction");
			setLinks(request);
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("TariffPlanTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			//reset the form bean and search param
			((DynaActionForm)form).initialize(mapping);
			request.getSession().setAttribute("searchparam", null);
			return this.getForward(strTariffplans, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
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
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSearch method of TariffplanAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strTariffplans);
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
				tableData.createTableInfo("TariffPlanTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alTariffPlans= tariffObject.getTariffPlanList(tableData.getSearchData());
			tableData.setData(alTariffPlans, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strTariffplans, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
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
			log.debug("Inside the doDefault method of TariffplanAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//fetch the data from the data access layer and set the data to table object
			ArrayList alTariffPlans = null;
			alTariffPlans= tariffObject.getTariffPlanList(tableData.getSearchData());
			//set the table data
			tableData.setData(alTariffPlans, strBackward);
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strTariffplans, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
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
			log.debug("Inside the doDefault method of TariffplanAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//fetch the data from the data access layer and set the data to table object
			ArrayList alTariffPlans = null;
			alTariffPlans= tariffObject.getTariffPlanList(tableData.getSearchData());
			//set the table data
			tableData.setData(alTariffPlans, strForward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strTariffplans, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
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
	public ActionForward doViewTariffplan(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewTariffplan method of TariffplanAction");
			setLinks(request);
			TableData tableData =TTKCommon.getTableData(request);
			DynaActionForm frmTariffPlanDetail = (DynaActionForm)form;
			//if rownumber is found populate the form object
			if(!((String)(frmTariffPlanDetail).get("rownum")).equals(""))
			{
				TariffPlanVO tariffPlanVO=(TariffPlanVO)tableData.getRowInfo(Integer.parseInt(
													(String)frmTariffPlanDetail.get("rownum")));

                if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
                {
                    request.setAttribute("child","PlanPackage");
                }//end of if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
                else
                {
                    request.setAttribute("child","Plan Details");
                }//end of else
                
                if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
				{
					//prepare RevisePlanVO for default plan
					RevisionPlanVO revisePlanVO=new RevisionPlanVO();
					//Revise Plan Seq Id Zero for default plan
					revisePlanVO.setRevPlanSeqId(new Long(0));
					revisePlanVO.setTariffPlanID(tariffPlanVO.getTariffPlanID());
					revisePlanVO.setDefaultPlanYn(tariffPlanVO.getDefaultPlanYn());
					revisePlanVO.setTariffPlanName(tariffPlanVO.getTariffPlanName());
					//set the RevisePlanVO to session
					request.getSession().setAttribute("RevisionPlanVO",revisePlanVO);
					return mapping.findForward("planpackage");
				}//end of if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
				frmTariffPlanDetail.set("name",tariffPlanVO.getTariffPlanName());
				frmTariffPlanDetail.set("description",tariffPlanVO.getTariffPlanDesc());
				frmTariffPlanDetail.set("tariffPlanId",tariffPlanVO.getTariffPlanID().toString());
			}//end if(!((String)(tariffPlanForm).get("rownum")).equals(""))
			return this.getForward(strEditTariffplans, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doViewTariffplan(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doAdd method of TariffplanAction");
			setLinks(request);
			DynaActionForm frmTariffPlanDetail = (DynaActionForm)form;
			//initialize the formbean for add mode
			frmTariffPlanDetail.initialize(mapping);
			return this.getForward(strEditTariffplans, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside the doSave method of TariffplanAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			Long lTariffPlanId=null;
			DynaActionForm frmTariffPlanDetail = (DynaActionForm)form;
			TariffPlanVO tariffPlanVO=new TariffPlanVO();
			if((String)frmTariffPlanDetail.get("tariffPlanId")!=null && (String)frmTariffPlanDetail.
																			get("tariffPlanId")!="")
			{
				lTariffPlanId=new Long(Long.parseLong((String)frmTariffPlanDetail.get("tariffPlanId")));
				tariffPlanVO.setTariffPlanID(lTariffPlanId);
			}//end of if((String)tariffPlanForm.get("tariffPlanId")!=null && (String)tariffPlanForm.
				//get("tariffPlanId")!="")
			tariffPlanVO.setTariffPlanName((String)frmTariffPlanDetail.get("name"));
			tariffPlanVO.setTariffPlanDesc((String)frmTariffPlanDetail.get("description"));
			tariffPlanVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			long count=tariffObject.addUpdateTariffPlan(tariffPlanVO);
			if(lTariffPlanId!=null && count>0)
			{
				request.setAttribute("updated","message.savedSuccessfully");
			}//end of if(lTariffPlanId!=null && count>0)
			else if(lTariffPlanId==null)
			{
				frmTariffPlanDetail.initialize(mapping);
				request.setAttribute("updated","message.addedSuccessfully");
			}//end of else if
			return this.getForward(strEditTariffplans, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside the doDeleteList method of TariffplanAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			String strDeleteId = "";
			ArrayList<Object> alTariffPlanList=new ArrayList<Object>();
			//populate the delete string which contains user sequence id's to be deleted
			//if deleting from grid screen, get the populated "chkopt" values.
			strDeleteId = populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData"));
			//seq ids of the plans to be deleted
			alTariffPlanList.add("|"+strDeleteId+"|");
			//call the Dao method to delete the Tariff Plans
			int iCount=tariffObject.deleteTariffPlan(alTariffPlanList);
			//refresh the grid data
			ArrayList alTariffPlans = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the
			//current set of search criteria
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
												get(tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alTariffPlans= tariffObject.getTariffPlanList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alTariffPlans= tariffObject.getTariffPlanList(tableData.getSearchData());
			}//end of else
			tableData.setData(alTariffPlans, "Delete");
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strTariffplans, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
	public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDelete method of TariffplanAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			String strDeleteId = "";
			ArrayList<Object> alTariffPlanList=new ArrayList<Object>();
			//populate the delete string which contains user sequence id's to be deleted
			//if deleting from edit screen, get the rownum to be deleted.
			strDeleteId = ""+((TariffPlanVO)((TableData)request.getSession().getAttribute("tableData")).
					 getData().get(Integer.parseInt(request.getParameter("rownum")))).getTariffPlanID();
			//seq ids of the plans to be deleted
			alTariffPlanList.add("|"+strDeleteId+"|");
			//call the Dao method to delete the Tariff Plans
			int iCount=tariffObject.deleteTariffPlan(alTariffPlanList);
			//refresh the grid data
			ArrayList alTariffPlans = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the
			//current set of search criteria
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
											   get(tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alTariffPlans= tariffObject.getTariffPlanList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alTariffPlans= tariffObject.getTariffPlanList(tableData.getSearchData());
			}//end of else
			tableData.setData(alTariffPlans, "Delete");
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strTariffplans, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to navigate the plan to the next screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doTariffRevisePlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															  HttpServletResponse response) throws Exception{
		try{
			log.debug(" Inside doTariffRevisePlan method of TariffPlanAction");
			setLinks(request);
			TableData tableData =TTKCommon.getTableData(request);
			DynaActionForm frmTariffPlanList = (DynaActionForm)form;
			String strPath="";
			if(!((String)(frmTariffPlanList).get("rownum")).equals(""))
			{
				TariffPlanVO tariffPlanVO=(TariffPlanVO)tableData.getRowInfo(Integer.parseInt((String)(
																	frmTariffPlanList).get("rownum")));
				if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
                {
                    request.setAttribute("child","PlanPackage");
                }//end of if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
                else
                {
                    request.setAttribute("child","Revision History");
                }//end of else
                
                request.getSession().setAttribute("TariffPlanVO",tariffPlanVO);
				if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
				{
					strPath="planpackage";
					//prepare RevisePlanVO for default plan
					RevisionPlanVO revisePlanVO=new RevisionPlanVO();
					//Revise Plan Seq Id Zero for default plan
					revisePlanVO.setRevPlanSeqId(new Long(0));
					revisePlanVO.setTariffPlanID(tariffPlanVO.getTariffPlanID());
					revisePlanVO.setDefaultPlanYn(tariffPlanVO.getDefaultPlanYn());
					revisePlanVO.setTariffPlanName(tariffPlanVO.getTariffPlanName());
					//set the RevisePlanVO to session
					request.getSession().setAttribute("RevisionPlanVO",revisePlanVO);
				}//end of if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
				else
				{
					strPath="tariffreviseplan";
				}//end of else
			}//end of if(!((String)(tariffPlanForm).get("rownum")).equals(""))
			return mapping.findForward(strPath);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doTariffRevisePlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug(" Inside doClose method of TariffPlanAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
			//if((Object)request.getSession().getAttribute("searchparam") != null)
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				//refresh the data in cancel mode, in order to get the new records if any.
				ArrayList alTariffPlans= tariffObject.getTariffPlanList(tableData.getSearchData());
				tableData.setData(alTariffPlans);
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if
			//forward to the grid screen
			return this.getForward("tariffplans", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside the doReset method of TariffplanAction");
			setLinks(request);
			TableData tableData =TTKCommon.getTableData(request);
			DynaActionForm frmTariffPlanDetail = (DynaActionForm)form;
			//if rownumber is found populate the form object
			if(!((String)(frmTariffPlanDetail).get("rownum")).equals(""))
			{
				TariffPlanVO tariffPlanVO=(TariffPlanVO)tableData.getRowInfo(Integer.parseInt(
												  (String)frmTariffPlanDetail.get("rownum")));
				if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
				{
					//prepare RevisePlanVO for default plan
					RevisionPlanVO revisePlanVO=new RevisionPlanVO();
					//Revise Plan Seq Id Zero for default plan
					revisePlanVO.setRevPlanSeqId(new Long(0));
					revisePlanVO.setTariffPlanID(tariffPlanVO.getTariffPlanID());
					revisePlanVO.setDefaultPlanYn(tariffPlanVO.getDefaultPlanYn());
					revisePlanVO.setTariffPlanName(tariffPlanVO.getTariffPlanName());
					//set the RevisePlanVO to session
					request.getSession().setAttribute("RevisionPlanVO",revisePlanVO);
					return mapping.findForward("planpackage");
				}//end of if(tariffPlanVO.getDefaultPlanYn().equals("Y"))
				frmTariffPlanDetail.set("name",tariffPlanVO.getTariffPlanName());
				frmTariffPlanDetail.set("description",tariffPlanVO.getTariffPlanDesc());
				frmTariffPlanDetail.set("tariffPlanId",tariffPlanVO.getTariffPlanID().toString());
			}//end if(!((String)(tariffPlanForm).get("rownum")).equals(""))
			else
			{
				//initialize the formbean for add mode
				frmTariffPlanDetail.initialize(mapping);
			}//end of else
			return this.getForward("edittariffplan", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * Returns the TariffPlanManager session object for invoking methods on it.
	 * @return TariffPlanManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private TariffManager getTariffManagerObject() throws TTKException
	{
		TariffManager tariffObject = null;
		try
		{
			if(tariffObject == null)
			{
				InitialContext ctx = new InitialContext();
				tariffObject = (TariffManager) ctx.lookup("java:global/TTKServices/business.ejb3/TariffManagerBean!com.ttk.business.administration.TariffManager");
			}//end of if(tariffObject == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "tariffplan");
		}//end of catch
		return tariffObject;
	}//end getTariffManagerObject()

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmTariffPlanList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmTariffPlanList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("DEFAULT_PLAN_YN","'Y','N'"));
		alSearchParams.add(new SearchCriteria("PLAN_NAME",TTKCommon.replaceSingleQots(
											(String)frmTariffPlanList.get("sName"))));
		request.getSession().setAttribute("searchparam",alSearchParams);
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmTariffPlanList)

	/**
	 * Returns a string which contains the pipe separated Tariff Plan sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the pipe separated Tariff Plan sequence id's to be deleted
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for
			//the matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((TariffPlanVO)tableData.getData().
							get(Integer.parseInt(strChk[i]))).getTariffPlanID().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((TariffPlanVO)tableData.
								  getData().get(Integer.parseInt(strChk[i]))).getTariffPlanID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

}//end of TariffPlanAction.java
