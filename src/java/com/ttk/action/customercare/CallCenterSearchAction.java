/**
 * @ (#) CallCenterSearchAction.java July 28, 2006
 * Project      : TTK HealthCare Services
 * File         : CallCenterSearchAction.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : July 28, 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.customercare;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.ttk.business.customercare.CallCenterManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.ClaimIntimationVO;
import com.ttk.dto.customercare.CallCenterDetailVO;
import com.ttk.dto.customercare.CallCenterVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of List of Calls.
 * This class also provides option for saving of Calls.
 *
 */

public class CallCenterSearchAction extends TTKAction	
{
	private static Logger log = Logger.getLogger(CallCenterSearchAction.class);

    //Modes.
	private static final String strBackward="Backward";
    private static final String strForward="Forward";

    //Action mapping forwards.
    private static final String strCallCenterSearch="callcentersearch";
    private static final String strCallCenterDetails="callcenterdetails";
    private static final String strInsuranceList="insurancelist";
    private static final String strEnrollmentList="enrollmentlist";
    private static final String strHospitalList="hospitallist";
    private static final String strCorporateList="corporatelist";
    private static final String strLogdetails="logdetails";
    private static final String strViewDetails="viewdetails";

    private static final String strSearch="Search";
    private static final String strCallDetails="Call Details";

    //Exception Message Identifier
    private static final String strCallCenter="callcenter";

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
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doDefault");
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		DynaActionForm frmCallCenterList= (DynaActionForm) form;
    		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
    										request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
    		request.getSession().removeAttribute("callCenterDetailVO");
    		//create new table data object
    		callcentertableData = new TableData();
    		//create the required grid table
    		callcentertableData.createTableInfo("CallCenterTable",new ArrayList());
    		request.getSession().setAttribute("callcentertableData",callcentertableData);
    		((DynaActionForm)form).initialize(mapping);		//reset the form data
    		frmCallCenterList.set("callertype","CTP");
    		if(frmCallCenterList.get("callertype").equals("CTP"))
    		{
    			((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("8%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setColumnWidth("11%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setColumnWidth("5%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    		}// end of if(frmCallCenterList.get("callertype").equals("CTP"))
    		request.getSession().setAttribute("searchparam", null);
    		frmCallCenterList.set("ttkBranch", strDefaultBranchID);
    		frmCallCenterList.set("sinceWhenCall","S30");
    		return this.getForward(strCallCenterSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doChangeLogType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    															HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doChangeLogType");
    		String strCallerType="";
    		DynaActionForm frmCallCenterList= (DynaActionForm) form;
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		strCallerType = (String)frmCallCenterList.get("callertype");
    		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
    		callcentertableData = new TableData();
    		//create the required grid table
    		callcentertableData.createTableInfo("CallCenterTable",new ArrayList());
    		request.getSession().setAttribute("callcentertableData",callcentertableData);
    		((DynaActionForm)form).initialize(mapping);		//reset the form data
    		frmCallCenterList.set("callertype",strCallerType);
    		if(frmCallCenterList.get("callertype").equals("CTP"))
    		{
    			((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("8%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setColumnWidth("11%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setColumnWidth("5%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    		}// end of if(frmCallCenterList.get("callertype").equals("CTP"))
    		else if(frmCallCenterList.get("callertype").equals("CTC"))
    		{
    			((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("13%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("13%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("14%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("10%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setColumnWidth("16%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    		}// end of else if(frmCallCenterList.get("callertype").equals("CTC"))
    		else if(frmCallCenterList.get("callertype").equals("CNC"))
    		{
    			((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("13%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("13%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("14%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("8%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setColumnWidth("18%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    		}// end of else if(frmCallCenterList.get("callertype").equals("CNC"))
    		else if(frmCallCenterList.get("callertype").equals("CIR"))
    		{
    			((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("20%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("20%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    		}// end of else if(frmCallCenterList.get("callertype").equals("CIR"))
    		else if(frmCallCenterList.get("callertype").equals("CIC"))
    		{
    			((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("13%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("13%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("12%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("14%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("8%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setColumnWidth("16%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setColumnWidth("14%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    		}// end of else if(frmCallCenterList.get("callertype").equals("CIC"))
    		else if((frmCallCenterList.get("callertype").equals("CTB")) ||
    														(frmCallCenterList.get("callertype").equals("CTA")))
    		{
    			((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setColumnWidth("15%");
    			((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    			((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    		}// end of else if((frmCallCenterList.get("callertype").equals("CTB")) ||
    			//(frmCallCenterList.get("callertype").equals("CTA")))
    		request.getSession().setAttribute("searchparam", null);
    		frmCallCenterList.set("ttkBranch", strDefaultBranchID);
    		frmCallCenterList.set("sinceWhenCall","S30");
    		return this.getForward(strCallCenterSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doChangeLogType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doSearch");
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		DynaActionForm frmCallCenterList= (DynaActionForm) form;
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		//clear the dynaform if visting from left links for the first time
    		//else get the dynaform data from session
    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		{
    			((DynaActionForm)form).initialize(mapping);//reset the form data
    		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		//if the page number or sort id is clicked
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				callcentertableData.setCurrentPage(Integer.parseInt(
    															TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strCallCenterSearch));
    			}///end of if(!strPageID.equals(""))
    			else
    			{
    				callcentertableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				callcentertableData.modifySearchData("sort");//modify the search data
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			callcentertableData.createTableInfo("CallCenterTable",null);
    			if(frmCallCenterList.get("callertype").equals("CTP"))
    			{
    				((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("11%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("11%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("11%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("13%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("8%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setColumnWidth("11%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setColumnWidth("5%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    			}// end of if(frmCallCenterList.get("callertype").equals("CTP"))
    			else if(frmCallCenterList.get("callertype").equals("CTC"))
    			{
    				((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("13%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("13%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("12%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("14%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("10%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setColumnWidth("16%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setColumnWidth("12%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    			}// end of else if(frmCallCenterList.get("callertype").equals("CTC"))
    			else if(frmCallCenterList.get("callertype").equals("CNC"))
    			{
    				((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("13%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("13%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("12%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("14%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("8%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setColumnWidth("18%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setColumnWidth("12%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    			}// end of else if(frmCallCenterList.get("callertype").equals("CNC"))
    			else if(frmCallCenterList.get("callertype").equals("CIR"))
    			{
    				((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("20%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("20%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    			}// end of else if(frmCallCenterList.get("callertype").equals("CIR"))
    			else if(frmCallCenterList.get("callertype").equals("CIC"))
    			{
    				((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("13%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("13%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("12%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("14%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("8%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setColumnWidth("16%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setColumnWidth("14%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setVisibility(false);
    			}// end of else if(frmCallCenterList.get("callertype").equals("CIC"))
    			else if((frmCallCenterList.get("callertype").equals("CTB")) ||
    															(frmCallCenterList.get("callertype").equals("CTA")))
    			{
    				((Column)((ArrayList)callcentertableData.getTitle()).get(0)).setColumnWidth("10%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(1)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(2)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(3)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(4)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(5)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(15)).setColumnWidth("15%");
    				((Column)((ArrayList)callcentertableData.getTitle()).get(6)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(7)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(8)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(9)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(10)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(11)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(12)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(13)).setVisibility(false);
    				((Column)((ArrayList)callcentertableData.getTitle()).get(14)).setVisibility(false);
    			}// end of else if((frmCallCenterList.get("callertype").equals("CTB")) ||
    				//(frmCallCenterList.get("callertype").equals("CTA")))
    			callcentertableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			callcentertableData.modifySearchData("search");
    		}//end of else
    		ArrayList alCallSearch= callCenterManagerObject.getCallList(callcentertableData.getSearchData());
    		callcentertableData.setData(alCallSearch, "search");
    		//set the table data object to session
    		request.getSession().setAttribute("callcentertableData",callcentertableData);
    		//finally return to the grid screen
    		return this.getForward(strCallCenterSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
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
    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    														HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doForward");
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		callcentertableData.modifySearchData(strForward);//modify the search data
    		ArrayList alCallSearch = callCenterManagerObject.getCallList(callcentertableData.getSearchData());
    		callcentertableData.setData(alCallSearch, strForward);
    		request.getSession().setAttribute("callcentertableData",callcentertableData);
    		return this.getForward(strCallCenterSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
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
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doBackward");
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		callcentertableData.modifySearchData(strBackward);//modify the search data
    		ArrayList alCallSearch = callCenterManagerObject.getCallList(callcentertableData.getSearchData());
    		callcentertableData.setData(alCallSearch, strBackward);
    		request.getSession().setAttribute("callcentertableData",callcentertableData);
    		return this.getForward(strCallCenterSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doLogDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    															HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doLogDetails");
    		CallCenterVO callCenterVO=null;
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			callCenterVO = (CallCenterVO)callcentertableData.getRowInfo(Integer.parseInt(
    																			request.getParameter("rownum")));
    			request.getSession().setAttribute("callCenterDetailVO",callCenterVO);
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		return mapping.findForward(strLogdetails);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doLogDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doViewCallDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doViewDetail");
    		return mapping.findForward(strViewDetails);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doViewCallDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//ttpServletResponse response)

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
    public ActionForward doChangeReason(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    											HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doChangeReason");
    		DynaActionForm frmCallCenterDetails= (DynaActionForm)form;
    		HashMap hmReasonList = null;
    		ArrayList alCallAnswer = new ArrayList();
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    														request.getSession().getAttribute("UserSecurityProfile");
    		hmReasonList = callCenterManagerObject.getReasonInfo();
    		alCallAnswer = (ArrayList)hmReasonList.get(frmCallCenterDetails.getString("reasonTypeID"));
    		if(frmCallCenterDetails.get("logSeqID")!=null && !frmCallCenterDetails.get("logSeqID").equals(""))
    		{
    			strCaption.append("Call Details  - ").append("Edit");
    		}//end of if
    		else
    		{
    			strCaption.append("Call Details  - ").append("Add");
    		}//end of else

    		frmCallCenterDetails.set("caption",String.valueOf(strCaption));
    		frmCallCenterDetails.set("alCallAnswer",alCallAnswer);
    		frmCallCenterDetails.set("savedYN","N");
    		frmCallCenterDetails.set("editableYN","Y");
    		frmCallCenterDetails.set("recordedBy",userSecurityProfile.getUserName());
    		frmCallCenterDetails.set("frmChanged","changed");
    		request.getSession().setAttribute("frmCallCenterDetails",frmCallCenterDetails);
    		return this.getForward(strCallCenterDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doChangeReason(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doChangeCategory(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    																HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doChangeReason");
    		DynaActionForm frmCallCenterDetails= (DynaActionForm)form;
    		HashMap hmCategoryReasonList = null;
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		ArrayList alCategoryReasonList = new ArrayList();
    		StringBuffer strCaption= new StringBuffer();
    		ArrayList alCallAnswer = new ArrayList();
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    										request.getSession().getAttribute("UserSecurityProfile");
    		hmCategoryReasonList = callCenterManagerObject.getCategoryReasonInfo();
    		alCategoryReasonList=(ArrayList)hmCategoryReasonList.get(frmCallCenterDetails.getString("categoryTypeID"));
    		if(frmCallCenterDetails.get("logSeqID")!=null && !frmCallCenterDetails.get("logSeqID").equals(""))
    		{
    			strCaption.append("Call Details  - ").append("Edit");
    		}//end of if(frmCallCenterDetails.get("logSeqID")!=null && !frmCallCenterDetails.get("logSeqID").equals(""))
    		else
    		{
    			strCaption.append("Call Details  - ").append("Add");
    		}//end of else

    		frmCallCenterDetails.set("caption",String.valueOf(strCaption));
    		frmCallCenterDetails.set("alCategoryReasonList",alCategoryReasonList);
    		frmCallCenterDetails.set("alCallAnswer",alCallAnswer);
    		frmCallCenterDetails.set("savedYN","N");
    		frmCallCenterDetails.set("editableYN","Y");
    		frmCallCenterDetails.set("recordedBy",userSecurityProfile.getUserName());
    		frmCallCenterDetails.set("frmChanged","changed");
    		request.getSession().setAttribute("frmCallCenterDetails",frmCallCenterDetails);
    		return this.getForward(strCallCenterDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doChangeReason(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doAdd");
    		DynaActionForm frmCallCenterDetails= (DynaActionForm)form;
    		CallCenterDetailVO callCenterDetailVO=null;
    		ClaimIntimationVO claimIntimationVO=null;
    		StringBuffer strCaption= new StringBuffer();
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    														request.getSession().getAttribute("UserSecurityProfile");
    		frmCallCenterDetails.initialize(mapping);
    		callCenterDetailVO = new CallCenterDetailVO();
    		claimIntimationVO = new ClaimIntimationVO();
    		callCenterDetailVO.setClaimIntimationVO(claimIntimationVO);
    		callCenterDetailVO.setLogTypeID("CLG");//set the default value
    		strCaption.append("Call Details  - ").append("Add");
    		callCenterDetailVO.setRecordedBy(userSecurityProfile.getUserName());
    		callCenterDetailVO.setOfficeName(userSecurityProfile.getBranchName());
    		callCenterDetailVO.setOfficeSeqID(userSecurityProfile.getBranchID());
    		frmCallCenterDetails= (DynaActionForm)FormUtils.setFormValues("frmCallCenterDetails", callCenterDetailVO,
    																		this, mapping, request);
    		frmCallCenterDetails.set("claimIntimationVO",FormUtils.setFormValues("frmClaimIntimation",
    												callCenterDetailVO.getClaimIntimationVO(),this,mapping,request));
    		frmCallCenterDetails.set("caption",String.valueOf(strCaption));
    		frmCallCenterDetails.set("savedYN","N");
    		frmCallCenterDetails.set("editableYN","Y");
    		frmCallCenterDetails.set("callerTypeID","CTP");
    		frmCallCenterDetails.set("logTypeID","LTR");
    		frmCallCenterDetails.set("subCategoryTypeID","SCE");
    		frmCallCenterDetails.set("priorityTypeID","MID");
    		if("TTK".equals(userSecurityProfile.getUserTypeId()))
    		{
    			frmCallCenterDetails.set("loggedByTypeID","LBT");
    		}//end of if("TTK".equals(userSecurityProfile.getUserTypeId()))
    		else if("CAL".equals(userSecurityProfile.getUserTypeId()))
    		{
    			frmCallCenterDetails.set("loggedByTypeID","LBC");
    		}//end of else if("CAL".equals(userSecurityProfile.getUserTypeId()))
    		request.getSession().setAttribute("frmCallCenterDetails",frmCallCenterDetails);
    		return this.getForward(strCallCenterDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
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
    public ActionForward doViewCallCenter(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doViewCallCenter");
    		DynaActionForm frmCallCenterDetails= (DynaActionForm)form;
    		CallCenterVO callCenterVO=null;
    		CallCenterDetailVO callCenterDetailVO=null;
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		String strSavedYN="";
    		HashMap hmCategoryReasonList = null;
    		ArrayList alCategoryReasonList = new ArrayList();
    		HashMap hmReasonList = null;
    		ArrayList alCallAnswer = new ArrayList();
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    													request.getSession().getAttribute("UserSecurityProfile");
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			callCenterVO = (CallCenterVO)callcentertableData.getRowInfo(
    														Integer.parseInt(request.getParameter("rownum")));
    			callCenterDetailVO = callCenterManagerObject.getCallDetail(callCenterVO.getLogSeqID(),
    																				TTKCommon.getUserSeqId(request));
    			strCaption.append("Call Details - ").append("Edit");
    			//To disable the save button once the status is closed
    			if(callCenterDetailVO.getStatusDesc().equals("Closed"))
    			{
    				strSavedYN="Y";
    			}//end of if(callCenterDetailVO.getStatusDesc().equals("Closed"))
    			else
    			{
    				strSavedYN="N";
    			}//end of else
    			
    			//getting escalationYN
    			request.getSession().setAttribute("callCenterDetailVO",callCenterVO);
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		else
    		{
    			TTKException expTTK = new TTKException();
    			expTTK.setMessage("error.calldetail.required");
    			throw expTTK;
    		}// end of else

    		//for change of Category Type
    		hmCategoryReasonList = callCenterManagerObject.getCategoryReasonInfo();
    		alCategoryReasonList=(ArrayList)hmCategoryReasonList.get(frmCallCenterDetails.getString("categoryTypeID"));
    		if(alCategoryReasonList == null){
    			alCategoryReasonList = new ArrayList();
    		}//end of if(alCategoryReasonList == null)
    		hmReasonList = callCenterManagerObject.getReasonInfo();
    		alCallAnswer = (ArrayList)hmReasonList.get(callCenterDetailVO.getReasonTypeID());
    		if(alCallAnswer == null){
    			alCallAnswer = new ArrayList();
    		}//end of if(alCallAnswer == null)
    		ArrayList alLogDetails = callCenterDetailVO.getCallCenterListVO();
    		callCenterDetailVO.setOfficeSeqID(userSecurityProfile.getBranchID());
    		log.info("##### OfficeSeqID ##### "+callCenterDetailVO.getOfficeSeqID());
    		frmCallCenterDetails= (DynaActionForm)FormUtils.setFormValues("frmCallCenterDetails", callCenterDetailVO,this, mapping, request);
    		
    		
    		
    		frmCallCenterDetails.set("claimIntimationVO",FormUtils.setFormValues("frmClaimIntimation",
    								callCenterDetailVO.getClaimIntimationVO(),this,mapping,request));
    		
    		log.info("@@@ OfficeSeqID @@@@  "+frmCallCenterDetails.get("officeSeqID"));
    		frmCallCenterDetails.set("officeSeqID",String.valueOf(userSecurityProfile.getBranchID()));
    		log.info("$$$ OfficeSeqID $$$$$  "+frmCallCenterDetails.get("officeSeqID"));
    		frmCallCenterDetails.set("caption",String.valueOf(strCaption));
    		frmCallCenterDetails.set("savedYN",strSavedYN);
    		frmCallCenterDetails.set("editableYN","N");
    		frmCallCenterDetails.set("alCategoryReasonList",alCategoryReasonList);
    		//frmCallCenterDetails.set("alSubReason",alSubReason);
    		frmCallCenterDetails.set("alCallAnswer",alCallAnswer);
    		frmCallCenterDetails.set("alLogDetails",alLogDetails);
    		frmCallCenterDetails.set("recordedBy",userSecurityProfile.getUserName());
    		//KOC 1286 for OPD    
    		
			if(callCenterDetailVO.getSeniorCitizenYN().equals("Y"))
			{
				
				//frmAuthorizationDetails.set("seniorCitizen","Please consider as a senior citizen");
				request.setAttribute("seniorCitizen","message.seniorCitizen");
			}
			//koc for griavance
    		request.getSession().setAttribute("frmCallCenterDetails",frmCallCenterDetails);
    		return this.getForward(strCallCenterDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doSave");
    		ArrayList alCategoryReasonList = new ArrayList();
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    													request.getSession().getAttribute("UserSecurityProfile");
    		ArrayList alCallAnswer = new ArrayList();
    		CallCenterDetailVO callCenterDetailVO=null;
    		ClaimIntimationVO claimIntimationVO=null;
    		String strUserType="";
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		String strSavedYN="";
    		DynaActionForm frmCallCenterDetails= (DynaActionForm)form;
    		alCategoryReasonList = (ArrayList)frmCallCenterDetails.get("alCategoryReasonList");
    		//alSubReason = (ArrayList)frmCallCenterDetails.get("alSubReason");
    		alCallAnswer = (ArrayList)frmCallCenterDetails.get("alCallAnswer");

    		if(request.getParameter("fraudYN") == null)// if fraudYN checkbox in not selected
    		{
    			frmCallCenterDetails.set("fraudYN","N");
    		}//end of if(request.getParameter("fraudYN") == null)
    		
    		if(request.getParameter("callbackYN") == null)// if callbackYN checkbox in not selected
    		{
    			frmCallCenterDetails.set("callbackYN","N");
    		}//end of if(request.getParameter("callbackYN") == null)
    		if(request.getParameter("escalationYN") == null)// if escalationYN checkbox in not selected
    		{
    			frmCallCenterDetails.set("callbackYN","N");
    		}//end of if(request.getParameter("escalationYN") == null)
    		
    		callCenterDetailVO = (CallCenterDetailVO)FormUtils.getFormValues(frmCallCenterDetails, this,
    																			mapping, request);
    		DynaActionForm frmClaimIntimation = (DynaActionForm) frmCallCenterDetails.get("claimIntimationVO");
    		claimIntimationVO=(ClaimIntimationVO)FormUtils.getFormValues(frmClaimIntimation, "frmClaimIntimation",this,
    																		mapping, request);

    		if(callCenterDetailVO.getLogTypeID().equals("LTC"))
    		{
    			callCenterDetailVO.setClaimIntimationVO(claimIntimationVO);
    		}//end of if(callCenterDetailVO.getLogTypeID().equals("LTC"))

    		if(callCenterDetailVO.getLogSeqID()!=null)
    		{
    			callCenterDetailVO.setLinkIdentifier("EDIT");
    		}//end of if(callCenterDetailVO.getLogSeqID()!=null)
    		else
    		{
    			callCenterDetailVO.setLinkIdentifier("ADD");
    		}//end of else
    		callCenterDetailVO.setUserTypeID(strUserType);
    		callCenterDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));	//User Id
    		Long lCount=callCenterManagerObject.saveCallDetail(callCenterDetailVO);
    		if(lCount>0)
    		{
    			if(callCenterDetailVO.getLogSeqID()!=null)
    			{
    				request.setAttribute("updated","message.savedSuccessfully");
    				strCaption.append("Call Details  - ").append("Edit");
    			}//end of if(callCenterDetailVO.getLogSeqID()!=null)
    			else
    			{
    				request.setAttribute("updated","message.addedSuccessfully");
    				strCaption.append("Call Details  - ").append("Add");
    				callCenterDetailVO = new CallCenterDetailVO();
    				frmCallCenterDetails.initialize(mapping);
    				strSavedYN="N";
    			}//end of else
    		}//end of if(lCount>0)
    		callCenterDetailVO = callCenterManagerObject.getCallDetail(lCount, TTKCommon.getUserSeqId(request));
    		//To disable the save button once the status is closed
    		if(callCenterDetailVO.getStatusDesc().equals("Closed"))
    		{
    			strSavedYN="Y";
    		}//end of if(callCenterDetailVO.getStatusDesc().equals("Closed"))
    		else
    		{
    			strSavedYN="N";
    		}//end of else

    		ArrayList alLogDetails = callCenterDetailVO.getCallCenterListVO();

    		if(!callCenterDetailVO.getLogTypeID().equals("LTC"))
    		{
    			callCenterDetailVO.setClaimIntimationVO(new ClaimIntimationVO());
    		}//end of if(callCenterDetailVO.getLogTypeID().equals("LTC"))

    		frmCallCenterDetails= (DynaActionForm)FormUtils.setFormValues("frmCallCenterDetails", callCenterDetailVO,
    																		this, mapping, request);
    		frmCallCenterDetails.set("claimIntimationVO",FormUtils.setFormValues("frmClaimIntimation",
    												callCenterDetailVO.getClaimIntimationVO(),this,mapping,request));
    		frmCallCenterDetails.set("caption",String.valueOf(strCaption));
    		frmCallCenterDetails.set("savedYN",strSavedYN);
    		frmCallCenterDetails.set("editableYN","N");
    		frmCallCenterDetails.set("alCategoryReasonList",alCategoryReasonList);
    		//frmCallCenterDetails.set("alSubReason",alSubReason);
    		frmCallCenterDetails.set("alCallAnswer",alCallAnswer);
    		frmCallCenterDetails.set("alLogDetails",alLogDetails);
    		frmCallCenterDetails.set("recordedBy",userSecurityProfile.getUserName());
    		request.getSession().setAttribute("callCenterDetailVO",callCenterDetailVO);
    		//KOC 1286 for OPD    
    		
			if(callCenterDetailVO.getSeniorCitizenYN().equals("Y"))
			{
				
				//frmAuthorizationDetails.set("seniorCitizen","Please consider as a senior citizen");
				request.setAttribute("seniorCitizen","message.seniorCitizen");
			}
			//koc for griavance
    		request.getSession().setAttribute("frmCallCenterDetails",frmCallCenterDetails);
    		
    	//	return doClose(mapping, form, request, response);
    		
    		
    				return this.getForward(strCallCenterDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
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
    									HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doReset");
    		DynaActionForm frmCallCenterDetails= (DynaActionForm)form;
    		StringBuffer strCaption= new StringBuffer();
    		CallCenterDetailVO callCenterDetailVO=null;
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		HashMap hmCategoryReasonList = null;
    		ArrayList alCategoryReasonList = new ArrayList();
    		HashMap hmReasonList = null;
    		ArrayList alCallAnswer = new ArrayList();
    		String strEditableYN="";
    		String strSavedYN="";
    		ClaimIntimationVO claimIntimationVO=null;
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    														request.getSession().getAttribute("UserSecurityProfile");
    		if(frmCallCenterDetails.get("logSeqID")!=null && !frmCallCenterDetails.get("logSeqID").equals(""))
    		{
    			strCaption.append("Call Details  - ").append("Edit");
    			callCenterDetailVO = callCenterManagerObject.getCallDetail(TTKCommon.getLong((String)
    									frmCallCenterDetails.get("logSeqID")), TTKCommon.getUserSeqId(request));

    			hmCategoryReasonList = callCenterManagerObject.getCategoryReasonInfo();
    			alCategoryReasonList = (ArrayList)hmCategoryReasonList.get(
    																frmCallCenterDetails.getString("categoryTypeID"));
    			if(alCategoryReasonList == null){
    				alCategoryReasonList = new ArrayList();
    			}//end of if(alCategoryReasonList == null)
    			hmReasonList = callCenterManagerObject.getReasonInfo();
    			alCallAnswer = (ArrayList)hmReasonList.get(callCenterDetailVO.getReasonTypeID());
    			if(alCallAnswer == null){
    				alCallAnswer = new ArrayList();
    			}//end of if(alCallAnswer == null)
    			strEditableYN="N";
    			//To disable the save button once the status is closed
    			if(callCenterDetailVO.getStatusDesc().equals("Closed"))
    			{
    				strSavedYN="Y";
    			}//end of if(callCenterDetailVO.getStatusDesc().equals("Closed"))
    			else
    			{
    				strSavedYN="N";
    			}//end of else
    		}//end of if(frmCallCenterDetails.get("logSeqID")!=null&&!frmCallCenterDetails.get("logSeqID").equals(""))
    		else
    		{
    			frmCallCenterDetails.initialize(mapping);
    			callCenterDetailVO = new CallCenterDetailVO();
    			claimIntimationVO = new ClaimIntimationVO();
    			callCenterDetailVO.setClaimIntimationVO(claimIntimationVO);
    			callCenterDetailVO.setLogTypeID("CLG");//set the default value
    			strCaption.append("Call Details  - ").append("Add");
    			strEditableYN="Y";
    			strSavedYN="N";
    			callCenterDetailVO.setRecordedBy(userSecurityProfile.getUserName());
    			callCenterDetailVO.setOfficeName(userSecurityProfile.getBranchName());
    			callCenterDetailVO.setOfficeSeqID(userSecurityProfile.getBranchID());
    		}//end of else
    		frmCallCenterDetails= (DynaActionForm)FormUtils.setFormValues("frmCallCenterDetails", callCenterDetailVO,
    															this, mapping, request);
    		frmCallCenterDetails.set("claimIntimationVO",FormUtils.setFormValues("frmClaimIntimation",
    												callCenterDetailVO.getClaimIntimationVO(),this,mapping,request));
    		ArrayList alLogDetails = callCenterDetailVO.getCallCenterListVO();
    		frmCallCenterDetails.set("caption",strCaption.toString());
    		frmCallCenterDetails.set("savedYN",strSavedYN);
    		frmCallCenterDetails.set("editableYN",strEditableYN);
    		frmCallCenterDetails.set("alCategoryReasonList",alCategoryReasonList);
    		//frmCallCenterDetails.set("alSubReason",alSubReason);
    		frmCallCenterDetails.set("alCallAnswer",alCallAnswer);
    		frmCallCenterDetails.set("alLogDetails",alLogDetails);
    		frmCallCenterDetails.set("recordedBy",userSecurityProfile.getUserName());
    		request.getSession().setAttribute("frmCallCenterDetails",frmCallCenterDetails);
    		return this.getForward(strCallCenterDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doClose");
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		if(callcentertableData.getSearchData().size()>1)
    		{
    			ArrayList alCallSearch = callCenterManagerObject.getCallList(callcentertableData.getSearchData());
    			callcentertableData.setData(alCallSearch, "search");
    			//set the table data object to session
    			request.getSession().setAttribute("callcentertableData",callcentertableData);
    		}//end of if(callcentertableData.getSearchData().size()>1)
    		request.getSession().removeAttribute("callCenterDetailVO");
    		return this.getForward(strCallCenterSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doSelectInsurance(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    											HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doSelectInsurance");
    		String strActiveTab=TTKCommon.getActiveTab(request);
    		if(strActiveTab.equals(strCallDetails))
    		{
    			DynaActionForm frmGeneral= (DynaActionForm)form;
    			String strLogTypeId = (String)frmGeneral.get("logTypeID");
    			//logTypeID != "LTC"
    			if(strLogTypeId.equalsIgnoreCase("LTC")){
    				frmGeneral.set("statusTypeID","SCL");
    			}//end of if(strLogTypeId.equalsIgnoreCase("LTC"))
    			frmGeneral.set("frmChanged","changed");
    		}//end of if(strActiveTab.equals(strCallDetails))
    		return mapping.findForward(strInsuranceList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doSelectInsurance(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doClearInsurance(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    														HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doClearInsurance");
    		DynaActionForm frmGeneral= (DynaActionForm)form;
    		String strActiveTab=TTKCommon.getActiveTab(request);
    		String strForwards="";
    		if(strActiveTab.equals(strSearch))
    		{
    			strForwards=strCallCenterSearch;
    		}//end of if(strActiveTab.equals(strSearch))
    		else if(strActiveTab.equals(strCallDetails))
    		{
    			strForwards=strCallCenterDetails;
    		}//end of else if(strActiveTab.equals(strCallDetails))
    		if(strActiveTab.equals(strSearch))
    		{
    			frmGeneral.set("id","");
    			frmGeneral.set("name","");
    			frmGeneral.set("seqID","");
    		}//end of if(strActiveTab.equals(strSearch))
    		else if(strActiveTab.equals(strCallDetails))
    		{
    			frmGeneral.set("insCompName","");
    			frmGeneral.set("insCompCodeNbr","");
    			frmGeneral.set("insSeqID","");
    			String strLogTypeId = (String)frmGeneral.get("logTypeID");
    			//logTypeID != "LTC"
    			if(strLogTypeId.equalsIgnoreCase("LTC")){
    				frmGeneral.set("statusTypeID","SCL");
    			}//end of if(strLogTypeId.equalsIgnoreCase("LTC"))
    			frmGeneral.set("frmChanged","changed");
    		}//end of else if(strActiveTab.equals(strCallDetails))
    		request.getSession().setAttribute("frmGeneral",frmGeneral);
    		return this.getForward(strForwards, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doClearInsurance(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doSelectEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doSelectEnrollment");
    		ClaimantVO claimantVO = null;
    		 request.getSession().removeAttribute("claimantVO");
    		String strActiveTab=TTKCommon.getActiveTab(request);
    		if(strActiveTab.equals(strCallDetails))
    		{
    			DynaActionForm frmGeneral= (DynaActionForm)form;
    			String strLogTypeId = (String)frmGeneral.get("logTypeID");
    			//logTypeID != "LTC"
    			if(strLogTypeId.equalsIgnoreCase("LTC")){
    				frmGeneral.set("statusTypeID","SCL");
    			}//end of if(strLogTypeId.equalsIgnoreCase("LTC"))
    			frmGeneral.set("frmChanged","changed");
    		}//end of if(strActiveTab.equals(strCallDetails))
    		request.getSession().setAttribute("closeFlag",request.getParameter("closeFlag"));
    		return mapping.findForward(strEnrollmentList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doSelectEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doClearEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    													HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doClearEnrollment");
    		DynaActionForm frmGeneral= (DynaActionForm)form;
    		String strActiveTab=TTKCommon.getActiveTab(request);
    		String strForwards="";
    		if(strActiveTab.equals(strSearch))
    		{
    			strForwards=strCallCenterSearch;
    		}//end of if(strActiveTab.equals(strSearch))
    		else if(strActiveTab.equals(strCallDetails))
    		{
    			strForwards=strCallCenterDetails;
    		}//end of else if(strActiveTab.equals(strCallDetails))
    		if(strActiveTab.equals(strSearch))
    		{
    			frmGeneral.set("id","");
    			frmGeneral.set("name","");
    			frmGeneral.set("seqID","");
    		}//end of if(strActiveTab.equals(strSearch))
    		else if(strActiveTab.equals(strCallDetails))
    		{
    			frmGeneral.set("policyNumber","");
    			frmGeneral.set("insScheme","");
    			frmGeneral.set("certificateNo","");
				frmGeneral.set("creditCardNbr","");
    			frmGeneral.set("insCustCode","");
    			frmGeneral.set("claimantName","");
    			frmGeneral.set("enrollmentID","");
    			frmGeneral.set("memberSeqID","");
    			frmGeneral.set("age","");//KOC FOR Grievance
    			String strLogTypeId = (String)frmGeneral.get("logTypeID");
    			//logTypeID != "LTC"
    			if(strLogTypeId.equalsIgnoreCase("LTC")){
    				frmGeneral.set("statusTypeID","SCL");
    			}//end of if(strLogTypeId.equalsIgnoreCase("LTC"))
    			frmGeneral.set("frmChanged","changed");
    		}//end of else if(strActiveTab.equals(strCallDetails))
    		request.getSession().setAttribute("frmGeneral",frmGeneral);
    		return this.getForward(strForwards, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doClearEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    											HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doSelectHospital");
    		String strActiveTab=TTKCommon.getActiveTab(request);
        	if(strActiveTab.equals(strCallDetails))
        	{
        		DynaActionForm frmGeneral= (DynaActionForm)form;
        		String strLogTypeId = (String)frmGeneral.get("logTypeID");
        		//logTypeID != "LTC"
        		if(strLogTypeId.equalsIgnoreCase("LTC")){
        			frmGeneral.set("statusTypeID","SCL");
        		}//end of if(strLogTypeId.equalsIgnoreCase("LTC"))
        		frmGeneral.set("frmChanged","changed");
        	}//end of if(strActiveTab.equals(strCallDetails))
        	return mapping.findForward(strHospitalList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doClearHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    														HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doClearHospital");
    		DynaActionForm frmGeneral= (DynaActionForm)form;
    		String strActiveTab=TTKCommon.getActiveTab(request);
    		String strForwards="";
    		if(strActiveTab.equals(strSearch))
    		{
    			strForwards=strCallCenterSearch;
    		}//end of if(strActiveTab.equals(strSearch))
    		else if(strActiveTab.equals(strCallDetails))
    		{
    			strForwards=strCallCenterDetails;
    		}//end of else if(strActiveTab.equals(strCallDetails))
    		if(strActiveTab.equals(strSearch))
    		{
    			frmGeneral.set("id","");
    			frmGeneral.set("name","");
    			frmGeneral.set("seqID","");
    		}//end of if(strActiveTab.equals(strSearch))
    		else if(strActiveTab.equals(strCallDetails))
    		{
    			frmGeneral.set("empanelmentNbr","");
    			frmGeneral.set("hospName","");
    			frmGeneral.set("hospSeqID","");
    			String strLogTypeId = (String)frmGeneral.get("logTypeID");
    			//logTypeID != "LTC"
    			if(strLogTypeId.equalsIgnoreCase("LTC")){
    				frmGeneral.set("statusTypeID","SCL");
    			}//end of if(strLogTypeId.equalsIgnoreCase("LTC"))
    			frmGeneral.set("frmChanged","changed");
    		}//end of else if(strActiveTab.equals(strCallDetails))
    		request.getSession().setAttribute("frmGeneral",frmGeneral);
    		return this.getForward(strForwards, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doClearHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
      //HttpServletResponse response)

    /**
     * This method is used to
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doSelectCorporate");
    		String strActiveTab=TTKCommon.getActiveTab(request);
    		if(strActiveTab.equals(strCallDetails))
    		{
    			DynaActionForm frmGeneral= (DynaActionForm)form;
    			String strLogTypeId = (String)frmGeneral.get("logTypeID");
    			//logTypeID != "LTC"
    			if(strLogTypeId.equalsIgnoreCase("LTC")){
    				frmGeneral.set("statusTypeID","SCL");
    			}//end of if(strLogTypeId.equalsIgnoreCase("LTC"))
    			frmGeneral.set("frmChanged","changed");
    		}//end of if(strActiveTab.equals(strCallDetails))
    		return mapping.findForward(strCorporateList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doSelectCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     //HttpServletResponse response)

    /**
     * This method is used to
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClearCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doClearCorporate");
    		DynaActionForm frmGeneral= (DynaActionForm)form;
    		String strActiveTab=TTKCommon.getActiveTab(request);
    		String strForwards="";
    		if(strActiveTab.equals(strSearch))
    		{
    			strForwards=strCallCenterSearch;
    		}//end of if(strActiveTab.equals(strSearch))
    		else if(strActiveTab.equals(strCallDetails))
    		{
    			strForwards=strCallCenterDetails;
    		}//end of else if(strActiveTab.equals(strCallDetails))
    		if(strActiveTab.equals(strSearch))
    		{
    			frmGeneral.set("id","");
    			frmGeneral.set("name","");
    			frmGeneral.set("seqID","");
    		}//end of if(strActiveTab.equals(strSearch))
    		else if(strActiveTab.equals(strCallDetails))
    		{
    			frmGeneral.set("groupName","");
    			frmGeneral.set("groupID","");
    			frmGeneral.set("groupRegnSeqID","");
    			String strLogTypeId = (String)frmGeneral.get("logTypeID");
    			//logTypeID != "LTC"
    			if(strLogTypeId.equalsIgnoreCase("LTC")){
    				frmGeneral.set("statusTypeID","SCL");
    			}//end of if(strLogTypeId.equalsIgnoreCase("LTC"))
    			frmGeneral.set("frmChanged","changed");
    		}//end of else if(strActiveTab.equals(strCallDetails))
    		request.getSession().setAttribute("frmGeneral",frmGeneral);
    		return this.getForward(strForwards, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doClearCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
      //HttpServletResponse response)

    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmCallCenterList formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     * @throws TTKException
     */

    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCallCenterList,HttpServletRequest request)
    																		throws TTKException
    {
    	//build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(frmCallCenterList.get("callertype"));

        if(frmCallCenterList.get("callertype").equals("CTP") || frmCallCenterList.get("callertype").equals("CTC") ||
        		frmCallCenterList.get("callertype").equals("CNC") || frmCallCenterList.get("callertype").equals("CIC"))
        {
        	alSearchParams.add(frmCallCenterList.get("seqID"));
            alSearchParams.add(null);
        }//end of if(frmCallCenterList.get("callertype").equals("CTP")|| frmCallCenterList.get("callertype").equals(
        //"CTC")||frmCallCenterList.get("callertype").equals("CNC")||frmCallCenterList.get("callertype").equals("CIC"))
        else if(frmCallCenterList.get("callertype").equals("CTB") || frmCallCenterList.get("callertype").equals("CTA"))
        {
        	alSearchParams.add(null);
            alSearchParams.add((TTKCommon.replaceSingleQots((String)frmCallCenterList.get("Name"))));
        }//end of else if(frmCallCenterList.get("callertype").equals("CTB") ||
        //frmCallCenterList.get("callertype").equals("CTA"))
        else
        {
        	alSearchParams.add(null);
            alSearchParams.add(null);
        }//end of else

        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.get("logNbr")));
        alSearchParams.add(frmCallCenterList.get("category"));
        alSearchParams.add(frmCallCenterList.get("ttkBranch"));
        alSearchParams.add(frmCallCenterList.get("status"));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.get("policyno")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.get("sSchemeName")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.get("sCertificateNumber")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.get("sCreditCardNumber")));
        alSearchParams.add(frmCallCenterList.get("sinceWhenCall"));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.getString("sPolicyLogNo")));//KOC FOR Grievance cigna
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.getString("sClaimIntLogNo")));//KOC FOR Grievance cigna
    	alSearchParams.add(frmCallCenterList.getString("callBack"));//added for intX
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        //alSearchParams.add(frmCallCenterList.get("endDate"));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmCallCenterList,HttpServletRequest request)

	/**
     * Returns the CallCenterManager session object for invoking methods on it.
     * @return CallCenterManager session object which can be used for method invokation
     * @exception throws TTKException
     */

    private CallCenterManager getCallCenterManagerObject() throws TTKException
    {
    	CallCenterManager callCenterManager = null;
        try
        {
            if(callCenterManager == null)
            {
                InitialContext ctx = new InitialContext();
                callCenterManager = (CallCenterManager) ctx.lookup("java:global/TTKServices/business.ejb3/CallCenterManagerBean!com.ttk.business.customercare.CallCenterManager");
            }//end of if(callCenterManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "callcenter");
        }//end of catch
        return callCenterManager;
    }//end getCallCenterManagerObject()
}// end of CallCenterSearchAction