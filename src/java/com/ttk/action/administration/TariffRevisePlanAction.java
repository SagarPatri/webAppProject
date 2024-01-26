
/**
 * @ (#) TariffRevisePlanAction.java Oct 20, 2005
 * Project      : TTK HealthCare Services
 * File         : TariffRevisePlanAction.java
 * Author       : Bhaskar Sandra
 * Company      : Span Systems Corporation
 * Date Created : Oct 20, 2005
 *
 * @author       :  Bhaskar Sandra
 * Modified by   : Arun K N
 * Modified date : Oct 24, 2005
 * Reason        : for adding new modes revise and addRevise for revising the plan
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
* This class is used for Searching the Revision List.
* This also provides  updation of Revised Plan.
*/
public class TariffRevisePlanAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( TariffRevisePlanAction.class ); 
	
	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";    
	
	//forwards links
	private static final String strRevisePlanSearch="reviseplansearch";
	private static final String strPlanPackage="planpackage";
	private static final String strRevisePlan="reviseplan";
	
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
			log.debug("Inside the doDefault method of TariffRevisePlanAction");
			setLinks(request);
			TableData tableDataRevisePlan=null;
			// Check whether tableDataRevisePlan is null
			if(request.getSession().getAttribute("tableDataRevisePlan")!=null) 
			{
				tableDataRevisePlan= (TableData)(request.getSession()).getAttribute("tableDataRevisePlan");
			}//end of if(request.getSession().getAttribute("tableDataRevisePlan")!=null) 
			else
			{
				tableDataRevisePlan=new TableData();
			}//end of else
			//create new table data object
			tableDataRevisePlan = new TableData();
			//create the required grid table
			tableDataRevisePlan.createTableInfo("TariffRevisePlanTable",new ArrayList());
			request.getSession().setAttribute("tableDataRevisePlan",tableDataRevisePlan);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			return this.getForward(strRevisePlanSearch, mapping, request);
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
			log.debug("Inside the doSearch method of TariffRevisePlanAction");
			setLinks(request);
			//Get the sessin bean from the beand pool for each thread being excuted.
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData tableDataRevisePlan=null;
			// Check whether tableDataRevisePlan is null
			if(request.getSession().getAttribute("tableDataRevisePlan")!=null)
			{
				tableDataRevisePlan= (TableData)(request.getSession()).getAttribute("tableDataRevisePlan");
			}//end of if(request.getSession().getAttribute("tableDataRevisePlan")!=null)
			else
			{
				tableDataRevisePlan=new TableData();
			}//end of else
			TariffPlanVO tariffPlanVO=null;
			Long lTariffPlanId=null;
			if(request.getSession().getAttribute("TariffPlanVO")!=null) 
			{
				//get the object from session
				tariffPlanVO=(TariffPlanVO)request.getSession().getAttribute("TariffPlanVO");
				lTariffPlanId=tariffPlanVO.getTariffPlanID();
			}//end of if(request.getSession().getAttribute("TariffPlanVO")!=null) 
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{    
					tableDataRevisePlan.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
																		  getParameter("pageId"))));
					return mapping.findForward(strRevisePlanSearch);                        
				}///end of if(!strPageID.equals(""))
				else
				{
					tableDataRevisePlan.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableDataRevisePlan.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableDataRevisePlan.createTableInfo("TariffRevisePlanTable",null);
				tableDataRevisePlan.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,
																							lTariffPlanId));
				tableDataRevisePlan.modifySearchData("search");
			}//end of else
			ArrayList alTariffRevisePlan=tariffObject.getRevisionPlanList(tableDataRevisePlan.getSearchData());
			tableDataRevisePlan.setData(alTariffRevisePlan, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableDataRevisePlan",tableDataRevisePlan); 
			//finally return to the grid screen
			return this.getForward(strRevisePlanSearch, mapping, request);          
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doForward (ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of TariffRevisePlanAction");
			setLinks(request);
			//Get the sessin bean from the beand pool for each thread being excuted.
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData tableDataRevisePlan=null;
			// Check whether tableDataRevisePlan is null
			if(request.getSession().getAttribute("tableDataRevisePlan")!=null) 
			{
				tableDataRevisePlan= (TableData)(request.getSession()).getAttribute("tableDataRevisePlan");
			}//end of if(request.getSession().getAttribute("tableDataRevisePlan")!=null)
			else
			{
				tableDataRevisePlan=new TableData();
			}//end of else
			//fetch the data from the data access layer and set the data to table object
			ArrayList alTariffRevisePlan = null;  
			alTariffRevisePlan= tariffObject.getRevisionPlanList(tableDataRevisePlan.getSearchData());
			//set the table data
			tableDataRevisePlan.setData(alTariffRevisePlan, strForward);
			request.getSession().setAttribute("tableDataRevisePlan",tableDataRevisePlan);	
			return this.getForward(strRevisePlanSearch, mapping, request); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doBackward method of TariffRevisePlanAction");
			setLinks(request);
			//Get the sessin bean from the beand pool for each thread being excuted.
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData tableDataRevisePlan=null;
			// Check whether tableDataRevisePlan is null
			if(request.getSession().getAttribute("tableDataRevisePlan")!=null) 
			{
				tableDataRevisePlan= (TableData)(request.getSession()).getAttribute("tableDataRevisePlan");
			}//end of if(request.getSession().getAttribute("tableDataRevisePlan")!=null) 
			else
			{
				tableDataRevisePlan=new TableData();
			}//end of else
			//fetch the data from the data access layer and set the data to table object
			ArrayList alTariffRevisePlan = null;  
			alTariffRevisePlan= tariffObject.getRevisionPlanList(tableDataRevisePlan.getSearchData());
			tableDataRevisePlan.setData(alTariffRevisePlan, strBackward);
			request.getSession().setAttribute("tableDataRevisePlan",tableDataRevisePlan);	
			return this.getForward("reviseplansearch", mapping, request); 
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
	 * This method is used to navigate to next screen .
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doRevise(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doRevise method of TariffRevisePlanAction");
			setLinks(request);
			return this.getForward(strRevisePlan,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doRevise(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is called from the struts framework.
	 * This method is used to navigate to next screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doPlanPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doPlanPackage method of TariffRevisePlanAction");
			setLinks(request);
			TableData tableDataRevisePlan=null;
			// Check whether tableDataRevisePlan is null
			if(request.getSession().getAttribute("tableDataRevisePlan")!=null) 
			{
				tableDataRevisePlan= (TableData)(request.getSession()).getAttribute("tableDataRevisePlan");
			}//end of if(request.getSession().getAttribute("tableDataRevisePlan")!=null)
			else
			{
				tableDataRevisePlan=new TableData();  // Creating the new Instance of the TableData
			}//end of else
			DynaActionForm frmTariffReviseList = (DynaActionForm)form;
			if(!((String)(frmTariffReviseList).get("rownum")).equals(""))
			{
				RevisionPlanVO revisePlanVO=(RevisionPlanVO)tableDataRevisePlan.getRowInfo(Integer.parseInt((String)
																			  (frmTariffReviseList).get("rownum")));
				request.getSession().setAttribute("RevisionPlanVO",revisePlanVO);
			}//end of if(!((String)(revisePlanForm).get("rownum")).equals(""))
			return mapping.findForward(strPlanPackage);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffplansExp));
		}//end of catch(Exception exp)
	}//end of doPlanPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
		try{
			log.debug("Inside the doSave method of TariffRevisePlanAction");
			setLinks(request);
			//Get the sessin bean from the beand pool for each thread being excuted.
			TariffManager tariffObject=this.getTariffManagerObject();
			TariffPlanVO tariffPlanVO=null;
			Long lTariffPlanId=null;
			if(request.getSession().getAttribute("TariffPlanVO")!=null) 
			{
				//get the object from session
				tariffPlanVO=(TariffPlanVO)request.getSession().getAttribute("TariffPlanVO");
				lTariffPlanId=tariffPlanVO.getTariffPlanID();
			}//end of if(request.getSession().getAttribute("TariffPlanVO")!=null) 
			DynaActionForm frmTariffRevise=(DynaActionForm)form;
			//prepare the RevisionPlanVO object to revise the plan
			RevisionPlanVO revisePlanVO=new RevisionPlanVO();
			//get the minimum values from form bean
			revisePlanVO.setStartDate(TTKCommon.getUtilDate((String)frmTariffRevise.get("startDate")));
			revisePlanVO.setTariffPlanID(lTariffPlanId);
			revisePlanVO.setTariffPlanName(tariffPlanVO.getTariffPlanName());
			revisePlanVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			//call the business layer to add the revised plan
			Long count=tariffObject.addRevisionPlan(revisePlanVO);
			revisePlanVO.setRevPlanSeqId(count);
			//set the VO in the session
			request.getSession().setAttribute("RevisionPlanVO",revisePlanVO);
			return mapping.findForward(strPlanPackage);
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
			log.debug("Inside the doClose method of TariffRevisePlanAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData tableDataRevisePlan=null;
			if(request.getSession().getAttribute("tableDataRevisePlan")!=null) 
			{
				tableDataRevisePlan= (TableData)(request.getSession()).getAttribute("tableDataRevisePlan");
			}//end of if(request.getSession().getAttribute("tableDataRevisePlan")!=null)
			else
			{
				tableDataRevisePlan=new TableData();
			}//end of else
			//if((Object)request.getSession().getAttribute("searchparam") != null)
			if(tableDataRevisePlan.getSearchData() != null && tableDataRevisePlan.getSearchData().size() > 0)
			{ 
				//refresh the data in cancel mode, in order to get the new records if any.
				ArrayList alTariffRevisePlan = tariffObject.getRevisionPlanList(tableDataRevisePlan.getSearchData());
				tableDataRevisePlan.setData(alTariffRevisePlan);
				request.getSession().setAttribute("tableDataRevisePlan",tableDataRevisePlan);
				//reset the forward links after adding the records if any
				tableDataRevisePlan.setForwardNextLink();
			}//end of if((Object)request.getSession().getAttribute("searchparam") != null)
			//forward to the grid screen
			return this.getForward(strRevisePlanSearch, mapping, request);
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
	 * Returns the TariffManager session object for invoking methods on it.
	 * @return TariffManager session object which can be used for method invokation 
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
			}//end if
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, "tariffplan");
		}//end of catch
		return tariffObject;
	}//end of getTariffManagerObject()
	
	/**
	 * Return the ArrayList populated with Search criteria elements
	 * @param frmTariffRevisePlan DynaActionForm
	 * @param request HttpServletRequest
	 * @param lTariffPlanId Tariff Plan Id
	 * @return ArrayList ArrayList of populated Search criteria elements
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmTariffRevisePlan,HttpServletRequest request,
																						Long lTariffPlanId)
	{
		ArrayList <Object> alSearchParams =new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("PLAN_SEQ_ID",lTariffPlanId.toString()));
		alSearchParams.add(new SearchCriteria("PLAN_FROM_DATE",(String)frmTariffRevisePlan.get("sStartDate")));
		alSearchParams.add(new SearchCriteria("PLAN_TO_DATE",(String)frmTariffRevisePlan.get("sEndDate")));
		request.getSession().setAttribute("searchparam",alSearchParams);
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmTariffRevisePlan)
	
}//end of TariffRevisePlanAction
