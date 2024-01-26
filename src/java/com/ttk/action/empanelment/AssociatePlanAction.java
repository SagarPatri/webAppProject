/**
 * @ (#) AssociatePlanAction.javaOct 22, 2005
 * Project      : TTK HealthCare Services
 * File         : AssociatePlanAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Oct 22, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.empanelment;

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


import com.ttk.dto.administration.TariffPlanVO;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.InsuranceVO;

/**
 * This class is used for searching the associated plans.
 */

public class AssociatePlanAction extends TTKAction
{
    private static Logger log = Logger.getLogger( AssociatePlanAction.class ); 
    
    //Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	
    //Action mapping forwards.
    private static final String strHospitalTariffSearchPlans="searchplans";
    private static final String strHospPlanPackage = "planpackage";
    
    //Exception Message Identifier
    private static final String strHospTariffSearchPlan="searchplan";
    
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
    		log.debug("Inside the doDefault method of AssociatePlanAction");
    		setLinks(request);
    		TableData tableDataAssociate=null;
            //get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociate")!=null){
            	tableDataAssociate= (TableData)(request.getSession()).getAttribute("tableDataAssociate");
            }//end of if(request.getSession().getAttribute("tableDataAssociate")!=null)
            else{
            	tableDataAssociate=new TableData();
            }//end of else
            //create new table data object
            tableDataAssociate = new TableData();
            //create the required grid table
            tableDataAssociate.createTableInfo("HospitalTariffPlanTable",new ArrayList());
            //Setting the tableData in Session
            request.getSession().setAttribute("tableDataAssociate",tableDataAssociate);
            ((DynaActionForm)form).initialize(mapping);//reset the form data
            //initialising the assoicatesearchparam as null
            request.getSession().setAttribute("assoicatesearchparam", null);
            return this.getForward(strHospitalTariffSearchPlans, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffSearchPlan));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doSearch method of AssociatePlanAction");
    		setLinks(request);
    		TariffManager tariffManagerObject=this.getTariffManagerObject();
    		String strGeneralCode="";
    		TableData tableDataAssociate=null;
    		//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociate")!=null){
            	tableDataAssociate= (TableData)(request.getSession()).getAttribute("tableDataAssociate");
            }//end of if(request.getSession().getAttribute("tableDataAssociate")!=null)
            else{
            	tableDataAssociate=new TableData();
            }//end of else
    		if(request.getSession().getAttribute("insuranceVO")!=null)
            {
                InsuranceVO insuranceVO=(InsuranceVO)request.getSession().getAttribute("insuranceVO");
                strGeneralCode=insuranceVO.getGenTypeID();
            }//end of if(request.getSession().getAttribute("insuranceVO")!=null) 
    		//if the page number is clicked, dispaly the appropriate page
            if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
            {
                tableDataAssociate.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
                		getParameter("pageId"))));
                return (mapping.findForward("searchplans"));
            }//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
            //clear the dynaform if visting from left links for the first time
            //else get the dynaform data from session
            if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
            {
                    ((DynaActionForm)form).initialize(mapping);//reset the form data
            }//end of  if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
            ArrayList alTariffPlanList=null;
            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{    
					tableDataAssociate.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
							getParameter("pageId"))));
					return mapping.findForward(strHospitalTariffSearchPlans);                        
				}///end of if(!strPageID.equals(""))
				else
				{
					tableDataAssociate.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableDataAssociate.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableDataAssociate.createTableInfo("HospitalTariffPlanTable",null);
				tableDataAssociate.setSearchData(this.populateSearchCriteria((DynaActionForm)form,
						request,strGeneralCode));
				tableDataAssociate.modifySearchData("search");
			}//end of else
			alTariffPlanList= tariffManagerObject.getTariffPlanList(tableDataAssociate.getSearchData());
			tableDataAssociate.setData(alTariffPlanList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableDataAssociate",tableDataAssociate);
            //finally return to the grid screen
            return (mapping.findForward(strHospitalTariffSearchPlans));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffSearchPlan));
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
    public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doBackward method of AssociatePlanAction");
    		setLinks(request);
    		TariffManager tariffManagerObject=this.getTariffManagerObject();
    		TableData tableDataAssociate=null;
    		//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociate")!=null){
            	tableDataAssociate= (TableData)(request.getSession()).getAttribute("tableDataAssociate");
            }//end of if(request.getSession().getAttribute("tableDataAssociate")!=null)
            else{
            	tableDataAssociate=new TableData();
            }//end of else
            //fetch the data from the data access layer and set the data to table object
            ArrayList alTariffPlanList = null;
            alTariffPlanList = tariffManagerObject.getTariffPlanList(tableDataAssociate.getSearchData());
            tableDataAssociate.setData(alTariffPlanList, strBackward);//set the table data
            request.getSession().setAttribute("tableDataAssociate",tableDataAssociate);
            return (mapping.findForward(strHospitalTariffSearchPlans));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffSearchPlan));
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
    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doForward method of AssociatePlanAction");
    		setLinks(request);
    		TariffManager tariffManagerObject=this.getTariffManagerObject();
    		TableData tableDataAssociate=null;
    		//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociate")!=null){
            	tableDataAssociate= (TableData)(request.getSession()).getAttribute("tableDataAssociate");
            }//end of if(request.getSession().getAttribute("tableDataAssociate")!=null)
            else{
            	tableDataAssociate=new TableData();
            }//end of else
            //fetch the data from the data access layer and set the data to table object
            ArrayList alTariffPlanList = null;
            alTariffPlanList = tariffManagerObject.getTariffPlanList(tableDataAssociate.getSearchData());
            tableDataAssociate.setData(alTariffPlanList, strForward);//set the table data
            request.getSession().setAttribute("tableDataAssociate",tableDataAssociate);
            return (mapping.findForward(strHospitalTariffSearchPlans));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffSearchPlan));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewPlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewPlan method of AssociatePlanAction");
    		setLinks(request);
    		TableData tableDataAssociate=null;
    		//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociate")!=null){
            	tableDataAssociate= (TableData)(request.getSession()).getAttribute("tableDataAssociate");
            }//end of if(request.getSession().getAttribute("tableDataAssociate")!=null)
            else{
            	tableDataAssociate=new TableData();
            }//end of else
    		if(!((String)request.getParameter("rownum")).equals(""))
            {
                TariffPlanVO tariffPlanVO=(TariffPlanVO)tableDataAssociate.getRowInfo(
                		Integer.parseInt((String)request.getParameter("rownum")));
                request.setAttribute("tariffPlanVO",tariffPlanVO);
            }//end of if(!((String)(revisePlanForm).get("rownum")).equals(""))
            return mapping.findForward(strHospPlanPackage);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffSearchPlan));
		}//end of catch(Exception exp)
    }//end of doViewPlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method builds all the search parameters to ArrayList and places them in session 
     * @param searchTariffPlanForm DynaActionForm
     * @param request HttpServletRequest
     * @param strGeneralCode General code to identify NHCP offered Rates and NHCP agreed rates
     * @return alSearchParams ArrayList
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm searchTariffPlanForm,HttpServletRequest request,
    		String strGeneralCode)
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        if(strGeneralCode.equals("ART"))
        {
            alSearchParams.add(new SearchCriteria("DEFAULT_PLAN_YN","'N'"));
        }//end of if(strGeneralCode.equals("ART"))
        else
        {
            alSearchParams.add(new SearchCriteria("DEFAULT_PLAN_YN","'T'"));
        }//end of else
        alSearchParams.add(new SearchCriteria("PLAN_NAME", (String)searchTariffPlanForm.get("planname")));
        request.getSession().setAttribute("assoicatesearchparam",alSearchParams);
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm searchTariffPlanForm,HttpServletRequest request,String strGeneralCode)
    
    /**
     * Returns the TariffManager session object for invoking methods on it.
     * @return TariffManager session object which can be used for method invokation 
     * @exception throws TTKException  
     */
    private TariffManager getTariffManagerObject() throws TTKException
    {
        TariffManager tariffManager = null;
        try 
        {
            if(tariffManager == null)
            {
                InitialContext ctx = new InitialContext();
                tariffManager = (TariffManager) ctx.lookup("java:global/TTKServices/business.ejb3/TariffManagerBean!com.ttk.business.administration.TariffManager");
            }//end if(tariffManager == null)
        }//end of try 
        catch(Exception exp) 
        {
            throw new TTKException(exp, "searchplan");
        }//end of catch
        return tariffManager;
    }//end getTariffManagerObject()
}// end of AssociatePlanAction class
