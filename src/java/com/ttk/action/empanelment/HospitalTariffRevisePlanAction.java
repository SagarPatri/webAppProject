/**
 * @ (#) HospitalTariffRevisePlanAction.java Oct 22, 2005
 * Project       : TTK HealthCare Services
 * File          : HospitalTariffRevisePlanAction.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2005
 * @author       :  Bhaskar Sandra
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
import com.ttk.dto.administration.RevisionPlanVO;
import com.ttk.dto.administration.TariffPlanVO;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.InsuranceVO;

/**
 * This class is used for searching the tariff revision history and also add/edit plan information.
 */


public class HospitalTariffRevisePlanAction extends TTKAction {
	
	//Getting Logger for this Class file
	private static Logger log = Logger.getLogger( HospitalTariffRevisePlanAction.class ); 
	
	//Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
	
	//Action mapping forwards.
    private static final String strHospitalTariffRevisePlan="hospitaltariffreviseplan";
    private static final String strHospRevisePlan = "reviseplan";
    private static final String strHospAddPlan = "addplans";
    private static final String strHospPlanPackage = "planpackage";
    
	
	//Exception Message Identifier
    private static final String strHospTariffRevisePlanError="hospitaltariffreviseplan";
    
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
    		log.debug("Inside the doDefault method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		if(TTKCommon.getWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.hospital.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request)==null)
    		TableData hospRevisePlanData=null;
			InsuranceVO insuranceVO=null;
			String strGeneralCode=null;
			if(request.getSession().getAttribute("insuranceVO")!=null) 
			{
				//get the insurance product seq id From the session
				insuranceVO=(InsuranceVO)request.getSession().getAttribute("insuranceVO");
				strGeneralCode=insuranceVO.getGenTypeID();
			}//end of if(request.getSession().getAttribute("insuranceVO")!=null)
			//Check whether tableDataRevisePlan is null
			if(request.getSession().getAttribute("hospRevisePlanData")!=null){ 
				//Getting TableData from the Session
				hospRevisePlanData= (TableData)(request.getSession()).getAttribute("hospRevisePlanData");  
			}//end of if	
			else{
				hospRevisePlanData=new TableData();  // Creating the new Instance of the TableData
			}//end of else
				
    		//create new table data object
			hospRevisePlanData = new TableData();
			//create the required grid table
			if(strGeneralCode.equals("ART")){
				hospRevisePlanData.createTableInfo("HospitalTariffRevisePlanTable",new ArrayList());
			}//end of if(strGeneralCode.equals("ART"))
			else{
				hospRevisePlanData.createTableInfo("HospTariffRevisePlanORTTable",new ArrayList());
			}//end of else
			request.getSession().setAttribute("hospRevisePlanData",hospRevisePlanData);
			//initialize the form bean
			((DynaActionForm)form).initialize(mapping);//reset the form data
            request.getSession().setAttribute("searchparam",null);
			return this.getForward(strHospitalTariffRevisePlan, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
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
    		log.debug("Inside the doSearch method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		String strGeneralCode=null;
    		InsuranceVO insuranceVO=null;
			Long lProdHospSeqId=null;
    		if(request.getSession().getAttribute("insuranceVO")!=null) 
			{
				//get the insurance product seq id From the session
				insuranceVO=(InsuranceVO)request.getSession().getAttribute("insuranceVO");
				lProdHospSeqId=insuranceVO.getProdHospSeqId();
				strGeneralCode=insuranceVO.getGenTypeID();
			}//end of if(request.getSession().getAttribute("insuranceVO")!=null) 
    		TableData hospRevisePlanData=null;
    		//Check whether tableDataRevisePlan is null
    		if(request.getSession().getAttribute("hospRevisePlanData")!=null){ 
    			//Getting TableData from the Session
				hospRevisePlanData= (TableData)(request.getSession()).getAttribute("hospRevisePlanData");  
    		}//end of if	
			else{
				hospRevisePlanData=new TableData();  // Creating the new Instance of the TableData
			}//end of else	
    		//Get the sessin bean from the beand pool for each thread being excuted.
			TariffManager tariffObject=this.getTariffManagerObject();
    		ArrayList alHospTariffRevisePlan=null;
    		// Are you visiting the page first time
    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){ 
				((DynaActionForm)form).initialize(mapping); // then reset the form data
    		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))	
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				hospRevisePlanData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(
    						request.getParameter("pageId")))); //Set the current page to be displayed
				    return mapping.findForward(strHospitalTariffRevisePlan);      // forward the request to Appropriate Action
    			}///end of if(!strPageID.equals(""))
    			else
    			{
    				hospRevisePlanData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				hospRevisePlanData.modifySearchData("sort");//modify the search data
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			// create the required grid table
    			if(strGeneralCode.equals("ART")){
    				//create Table for NHCP agreed rates
    				hospRevisePlanData.createTableInfo("HospitalTariffRevisePlanTable",null);
    			}//end of if(strGeneralCode.equals("ART"))
				else{
					//create Table for NHCP offered rates
					hospRevisePlanData.createTableInfo("HospTariffRevisePlanORTTable",null);
				}//end of else
				
    			//fetch the data from the data access layer and set the data to table object
    			hospRevisePlanData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,
    					lProdHospSeqId));
    			//by default revisions should be in descending order
    			hospRevisePlanData.setSortData("0001");
    			hospRevisePlanData.setSortOrder("DESC");
    			hospRevisePlanData.modifySearchData("search");
    		}//end of else
    		alHospTariffRevisePlan=tariffObject.getHospitalRevisionPlanList(hospRevisePlanData.getSearchData());
    		hospRevisePlanData.setData(alHospTariffRevisePlan, "search");
    		request.getSession().setAttribute("hospRevisePlanData",hospRevisePlanData);
    		//finally return to the grid screen
    		return this.getForward(strHospitalTariffRevisePlan, mapping, request);
		}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
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
    		log.debug("Inside the doBackward method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		//Get the sessin bean from the beand pool for each thread being excuted.
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData hospRevisePlanData=null;
			//Getting TableData from the Session
			//Check whether tableDataRevisePlan is null
    		if(request.getSession().getAttribute("hospRevisePlanData")!=null){ 
    			//Getting TableData from the Session
				hospRevisePlanData= (TableData)(request.getSession()).getAttribute("hospRevisePlanData");  
    		}//end of if	
			else{
				hospRevisePlanData=new TableData();  // Creating the new Instance of the TableData
			}//end of else	
    		hospRevisePlanData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alTariffRevisePlans = tariffObject.getHospitalRevisionPlanList(hospRevisePlanData.
    				getSearchData());
    		hospRevisePlanData.setData(alTariffRevisePlans, strBackward);//set the table data
    		//set the table data object to session
    		request.getSession().setAttribute("hospRevisePlanData",hospRevisePlanData);
    		request.getSession().removeAttribute("RevisionPlanVO");
    		TTKCommon.documentViewer(request);
    		return this.getForward(strHospitalTariffRevisePlan, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
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
    		log.debug("Inside the doForward method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		//Get the sessin bean from the beand pool for each thread being excuted.
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData hospRevisePlanData=null;
			//Check whether tableDataRevisePlan is null
    		if(request.getSession().getAttribute("hospRevisePlanData")!=null){
    			//Getting TableData from the Session
				hospRevisePlanData= (TableData)(request.getSession()).getAttribute("hospRevisePlanData");  
    		}//end of if	
			else{
				hospRevisePlanData=new TableData();  // Creating the new Instance of the TableData
			}//end of else	
    		hospRevisePlanData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alTariffRevisePlans = tariffObject.getHospitalRevisionPlanList(hospRevisePlanData.
    				getSearchData());
    		hospRevisePlanData.setData(alTariffRevisePlans, strForward);//set the table data

    		//set the table data object to session
    		request.getSession().setAttribute("hospRevisePlanData",hospRevisePlanData);
    		request.getSession().removeAttribute("RevisionPlanVO");
    		TTKCommon.documentViewer(request);
    		return this.getForward(strHospitalTariffRevisePlan, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to Detail screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewRevisePlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewRevisePlan method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		TableData hospRevisePlanData=null;
    		//Check whether tableDataRevisePlan is null
    		if(request.getSession().getAttribute("hospRevisePlanData")!=null){ 
    			//Getting TableData from the Session
				hospRevisePlanData= (TableData)(request.getSession()).getAttribute("hospRevisePlanData");  
    		}//end of if	
			else{
				hospRevisePlanData=new TableData();  // Creating the new Instance of the TableData
			}//end of else	
    		if(!((String)request.getParameter("rownum")).equals(""))
			{
				RevisionPlanVO revisePlanVO=(RevisionPlanVO)hospRevisePlanData.
											 getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
				request.getSession().setAttribute("RevisionPlanVO",revisePlanVO);
			}//end of if(!((String)request.getParameter("rownum")).equals(""))
			return mapping.findForward(strHospRevisePlan);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
		}//end of catch(Exception exp)
    }//end of doViewRevisePlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doAdd method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		DynaActionForm associatePlanForm = (DynaActionForm)form;
			associatePlanForm.initialize(mapping);
			associatePlanForm.set("caption","Add");
			request.getSession().removeAttribute("RevisionPlanVO");
			if(request.getParameter("reviseMode")!=null){
				request.getSession().setAttribute("reviseMode",request.getParameter("reviseMode"));
			}//end of if(request.getParameter("reviseMode")!=null)
			return this.getForward(strHospAddPlan, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
		}//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doViewPlan method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		DynaActionForm associatePlanForm = (DynaActionForm)form;
			associatePlanForm.set("caption","Edit");
			if(request.getParameter("reviseMode")!=null)
				request.getSession().setAttribute("reviseMode",request.getParameter("reviseMode"));
			RevisionPlanVO revisionplanVO;
			TariffPlanVO tariffPlanVO=null;
			if(request.getParameter("nameChanged")!=null){
				tariffPlanVO=(TariffPlanVO) request.getAttribute("tariffPlanVO");
			}//end of if(request.getParameter("nameChanged")!=null)
			//Get the RevisionPlanVO from the TableData
			revisionplanVO=(RevisionPlanVO)request.getSession().getAttribute("RevisionPlanVO"); 
			if((request.getParameter("nameChanged")!=null)&&(tariffPlanVO!=null))
			{   
				associatePlanForm.set("sPlanName",tariffPlanVO.getTariffPlanName());
				associatePlanForm.set("planseqid",tariffPlanVO.getTariffPlanID());
				if(revisionplanVO!=null)// checking if the revisionplanVO is null
				{
					//If Plan has Changed
					if(!tariffPlanVO.getTariffPlanID().equals(revisionplanVO.getTariffPlanID())){ 
						request.setAttribute("nameChanged","true");
					}//end of if
				}//end of if(revisionplanVO!=null)
			}//end of if((request.getParameter("nameChanged")!=null)&&(tariffPlanVO!=null))
			else if(revisionplanVO!=null)
			{
				associatePlanForm.set("sPlanName",revisionplanVO.getTariffPlanName());
				associatePlanForm.set("planseqid",revisionplanVO.getTariffPlanID());
			}// end of else if(revisionplanVO!=null)
			if(revisionplanVO!=null)  
			{
				associatePlanForm.set("planfromdate",revisionplanVO.getStartDate());
				associatePlanForm.set("plantodate",revisionplanVO.getEndDate());
				associatePlanForm.set("discounts",revisionplanVO.getDiscountOffered().toString());
				associatePlanForm.set("lRevPlanSeqId",revisionplanVO.getRevPlanSeqId());
			}//end of if(revisionplanVO!=null)
			if(request.getParameter("reviseMode")!=null)
				if(request.getParameter("reviseMode").equals("add"))
				{
					associatePlanForm.initialize(mapping);
					associatePlanForm.set("caption","Add");
				}//end of if(request.getParameter("reviseMode").equals("add"))
			if(!((String)request.getSession().getAttribute("reviseMode")).equals("edit")){
				associatePlanForm.set("planfromdate","");
			}//end of if(!((String)request.getSession().getAttribute("reviseMode")).equals("edit"))
			return this.getForward(strHospAddPlan, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
		}//end of catch(Exception exp)
    }//end of doViewPlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doSave method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		DynaActionForm associatePlanForm = (DynaActionForm)form;
			RevisionPlanVO revisionplanVO= new RevisionPlanVO();
			TariffManager tariffPlanObject1=this.getTariffManagerObject();
			// get InsuranceVO from the session if it exits
			InsuranceVO insurancevo=(InsuranceVO)request.getSession().getAttribute("insuranceVO");
			//get RevisionPlanVO from the session if it exits and populate the RevisionPlanVO
			// with the values of InsuranceVO
			if(request.getSession().getAttribute("RevisionPlanVO")!=null){
				revisionplanVO=(RevisionPlanVO)request.getSession().getAttribute("RevisionPlanVO");
			}//end of if(request.getSession().getAttribute("RevisionPlanVO")!=null)
			revisionplanVO.setProdHospSeqId(insurancevo.getProdHospSeqId());//read the value from session
			revisionplanVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User ID
			revisionplanVO.setStartDate(TTKCommon.getUtilDate((String)associatePlanForm.get("planfromdate")));
			revisionplanVO.setEndDate(TTKCommon.getUtilDate((String)associatePlanForm.get("plantodate")));
			revisionplanVO.setDiscountOffered(TTKCommon.getBigDecimal((String)associatePlanForm.get("discounts")));
			revisionplanVO.setTariffPlanID((Long)associatePlanForm.get("planseqid"));
			revisionplanVO.setRevPlanSeqId((Long)associatePlanForm.get("lRevPlanSeqId"));
			Long lRevPlanSeqId=(Long)associatePlanForm.get("lRevPlanSeqId");
			Long count=tariffPlanObject1.addRevisionPlan(revisionplanVO);
			request.setAttribute("updated","The record was saved successfully!");
			// Get the RevisionPlanVO from the session and set the modified Values
			revisionplanVO=(RevisionPlanVO)request.getSession().getAttribute("RevisionPlanVO"); 
			if(revisionplanVO==null){
				revisionplanVO=new RevisionPlanVO();
			}//end of if(revisionplanVO==null)
			revisionplanVO.setRevPlanSeqId(count);
			revisionplanVO.setTariffPlanID((Long)associatePlanForm.get("planseqid"));
			revisionplanVO.setTariffPlanName((String)associatePlanForm.get("sPlanName"));
			revisionplanVO.setStartDate(TTKCommon.getUtilDate((String)associatePlanForm.get("planfromdate")));
			revisionplanVO.setProdHospSeqId(insurancevo.getProdHospSeqId());
			request.getSession().setAttribute("RevisionPlanVO",revisionplanVO);
			// if we add the plan then send the result to Tariff Plan package screen
			// else if you edit the plan then send the result to Edit tariff plan screen.
			if(lRevPlanSeqId==null)
			{   
				return mapping.findForward(strHospPlanPackage);
			}// End of if
			return this.getForward(strHospAddPlan, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to detail screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewPlanPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewPlanPackage method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		TableData hospRevisePlanData=null;
    		//Check whether tableDataRevisePlan is null
    		if(request.getSession().getAttribute("hospRevisePlanData")!=null){ 
    			// Getting TableData from the Session
				hospRevisePlanData= (TableData)(request.getSession()).getAttribute("hospRevisePlanData");  
    		}//end of if	
			else{
				hospRevisePlanData=new TableData();  // Creating the new Instance of the TableData
			}//end of else	
    		if(!((String)request.getParameter("rownum")).equals(""))
			{
				RevisionPlanVO revisePlanVO=(RevisionPlanVO)hospRevisePlanData.getRowInfo(Integer.
											 parseInt((String)request.getParameter("rownum")));
				request.getSession().setAttribute("RevisionPlanVO",revisePlanVO);
			}//end of if(!((String)(revisePlanForm).get("rownum")).equals(""))
			return mapping.findForward(strHospPlanPackage);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
		}//end of catch(Exception exp)
    }//end of doViewPlanPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doClose method of HospitalTariffRevisePlanAction");
    		setLinks(request);
    		//Get the sessin bean from the beand pool for each thread being excuted.
			TariffManager tariffObject=this.getTariffManagerObject();
    		TableData hospRevisePlanData=null;
    		if(request.getSession().getAttribute("hospRevisePlanData")!=null){ // Check whether tableDataRevisePlan is null
    			//Getting TableData from the Session
				hospRevisePlanData= (TableData)(request.getSession()).getAttribute("hospRevisePlanData");  
    		}//end of if	
			else{
				hospRevisePlanData=new TableData();  // Creating the new Instance of the TableData
			}//end of else	
    		if((Object)request.getSession().getAttribute("searchparam") != null)
            {    
                //refresh the data in cancel mode, in order to get the new records if any.
                ArrayList alTariffRevisePlans = tariffObject.getHospitalRevisionPlanList(hospRevisePlanData.
                								getSearchData());
                hospRevisePlanData.setData(alTariffRevisePlans);
                request.getSession().setAttribute("hospRevisePlanData",hospRevisePlanData);
                //reset the forward links after adding the records if any
                hospRevisePlanData.setForwardNextLink();
            }//end of if((Object)request.getSession().getAttribute("searchparam") != null)
            return (mapping.findForward(strHospitalTariffRevisePlan));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffRevisePlanError));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
			}//end if
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, "tariffplan");
		}//end of catch
		return tariffManager;
	}//end getTariffManagerObject()
	/**
	 * Return the ArrayList populated with Search criteria elements and adds that to the session for the first time
	 * @param DynaActionForm
	 * @param request HttpServletRequest
	 * @param Long lProdHospSeqId
	 * @return ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmTariffRevisePlan,HttpServletRequest request,
			Long lProdHospSeqId)
	{
		ArrayList <Object> alSearchParams =new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("from_period.PROD_HOSP_SEQ_ID",lProdHospSeqId.toString()));
		alSearchParams.add(new SearchCriteria("PLAN_FROM_DATE",(String)frmTariffRevisePlan.get("sStartDate")));
		alSearchParams.add(new SearchCriteria("PLAN_TO_DATE",(String)frmTariffRevisePlan.get("sEndDate")));
		request.getSession().setAttribute("searchparam",alSearchParams);
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmTariffRevisePlan,HttpServletRequest request,Long lProdHospSeqId)
}//end of HospitalTariffRevisePlanAction
