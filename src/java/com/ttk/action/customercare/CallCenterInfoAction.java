/**
 * @ (#) CallCenterInfoAction.java July 28, 2006
 * Project      : TTK HealthCare Services
 * File         : CallCenterInfoAction.java
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
import com.ttk.business.customercare.CallCenterManager;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.OfficeVO;
import com.ttk.dto.customercare.EnquiryDetailVO;
import com.ttk.dto.empanelment.InsuranceVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of View Details regarding calls.
 */

public class CallCenterInfoAction extends TTKAction
{
    private static Logger log = Logger.getLogger(CallCenterInfoAction.class);

    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";

    //Action mapping forwards.
    private static final String strCallCenterSearch="centerinfo";
    private static final String strCallCenterDetails="callcenterdetails";
    private static final String strEnquiryDetail="enquirydetail";

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
    		log.debug("Inside CallCenterInfoAction doDefault");
    		TableData  enquiryListData =(TableData)request.getSession().getAttribute("enquiryListData");
    		DynaActionForm frmCallCenterInfo= (DynaActionForm) form;
    		enquiryListData = new TableData();          //create new table data object
    		frmCallCenterInfo.set("sType","HOS");
    		enquiryListData.createTableInfo("PreAuthHospitalTable",new ArrayList());
    		request.getSession().setAttribute("enquiryListData",enquiryListData);
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
    public ActionForward doChangeSearchType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    														HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterInfoAction doChangeSearchType");
    		String strType ="";
    		DynaActionForm frmCallCenterInfo= (DynaActionForm) form;
    		strType=(String)frmCallCenterInfo.get("sType");
    		TableData  enquiryListData =(TableData)request.getSession().getAttribute("enquiryListData");
    		frmCallCenterInfo.initialize(mapping);  //initialize the form
            enquiryListData = new TableData();
            if(strType.equals("HOS"))
            {
                enquiryListData.createTableInfo("PreAuthHospitalTable",null);
            }//end of if(strType.equals("HOS"))
            else if(strType.equals("INS"))
            {
                enquiryListData.createTableInfo("InsuranceTable",null);
            }//end of else if(strType.equals("INS"))
            else if(strType.equals("TTK"))
            {
                enquiryListData.createTableInfo("TTKOfficeTable",null);
            }//end of else if(strType.equals("TTK"))
            request.getSession().setAttribute("enquiryListData",enquiryListData);
            frmCallCenterInfo.set("sType",strType);
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
    }//end of doChangeSearchType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    		setLinks(request);
    		log.debug("Inside CallCenterInfoAction doForward");
    		TableData  enquiryListData =(TableData)request.getSession().getAttribute("enquiryListData");
    		String strType ="";
    		DynaActionForm frmCallCenterInfo= (DynaActionForm) form;
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		PolicyManager policyManagerObject=this.getPolicyManagerObject();
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		strType=(String)frmCallCenterInfo.get("sType");
    		enquiryListData.modifySearchData(strForward);//modify the search data
    		ArrayList alCallSearch = null;
    		if(strType.equals("TTK"))
    		{
    			alCallSearch=callCenterManagerObject.getTTKOfficeList(enquiryListData.getSearchData());
    		}//end of if(strType.equals("TTK"))
    		else if(strType.equals("INS"))
    		{
    			alCallSearch=policyManagerObject.getInsuranceCompanyList(enquiryListData.getSearchData());
    		}//end of else if(strType.equals("INS"))
    		else
    		{
    			alCallSearch=hospitalObject.getPreAuthHospitalList(enquiryListData.getSearchData());
    		}//end of else
    		enquiryListData.setData(alCallSearch, strForward);
    		request.getSession().setAttribute("enquiryListData",enquiryListData);
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
    		log.debug("Inside CallCenterInfoAction doBackward");
    		TableData  enquiryListData =(TableData)request.getSession().getAttribute("enquiryListData");
    		String strType ="";
    		DynaActionForm frmCallCenterInfo= (DynaActionForm) form;
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		PolicyManager policyManagerObject=this.getPolicyManagerObject();
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		strType=(String)frmCallCenterInfo.get("sType");
    		enquiryListData.modifySearchData(strBackward);//modify the search data
    		ArrayList alCallSearch = null;
    		if(strType.equals("TTK"))
    		{
    			alCallSearch=callCenterManagerObject.getTTKOfficeList(enquiryListData.getSearchData());
    		}//end of if(strType.equals("TTK"))
    		else if(strType.equals("INS"))
    		{
    			alCallSearch=policyManagerObject.getInsuranceCompanyList(enquiryListData.getSearchData());
    		}//end of else if(strType.equals("INS"))
    		else
    		{
    			alCallSearch=hospitalObject.getPreAuthHospitalList(enquiryListData.getSearchData());
    		}//end of else
    		enquiryListData.setData(alCallSearch, strBackward);
    		request.getSession().setAttribute("enquiryListData",enquiryListData);
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
    		log.debug("Inside CallCenterInfoAction doSearch");
    		TableData  enquiryListData =(TableData)request.getSession().getAttribute("enquiryListData");
    		String strType ="";
    		DynaActionForm frmCallCenterInfo= (DynaActionForm) form;
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		PolicyManager policyManagerObject=this.getPolicyManagerObject();
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		strType=(String)frmCallCenterInfo.get("sType");
    		//clear the dynaform if visiting from left links for the first time
    		//else get the dynaform data from session

    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		//if the page number or sort id is clicked
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				enquiryListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(
    																request.getParameter("pageId"))));
    				return this.getForward(strCallCenterSearch, mapping, request);
    			}///end of if(!strPageID.equals(""))
    			else
    			{
    				enquiryListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				enquiryListData.modifySearchData("sort");//modify the search data
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			//create the required grid table
    			if(strType.equals("HOS"))
    			{
    				enquiryListData.createTableInfo("PreAuthHospitalTable",null);
    			}//end of if(strType.equals("HOS"))
    			else if(strType.equals("INS"))
    			{
    				enquiryListData.createTableInfo("InsuranceTable",null);
    			}//end of else if(strType.equals("INS"))
    			else if(strType.equals("TTK"))
    			{
    				enquiryListData.createTableInfo("TTKOfficeTable",null);
    			}//end of else if(strType.equals("TTK"))
    			enquiryListData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			enquiryListData.modifySearchData("search");
    		}//end of else

    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alCallSearch=null;

    		if(strType.equals("TTK"))
    		{
    			alCallSearch=callCenterManagerObject.getTTKOfficeList(enquiryListData.getSearchData());
    		}//end of if(strType.equals("TTK"))
    		else if(strType.equals("INS"))
    		{
    			alCallSearch=policyManagerObject.getInsuranceCompanyList(enquiryListData.getSearchData());
    		}//end of else if(strType.equals("INS"))
    		else
    		{
    			alCallSearch=hospitalObject.getPreAuthHospitalList(enquiryListData.getSearchData());
    		}//end of else
    		enquiryListData.setData(alCallSearch, "search");
    		//set the table data object to session
    		request.getSession().setAttribute("enquiryListData",enquiryListData);
    		frmCallCenterInfo.set("sType",strType);
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
    public ActionForward doEnquiryDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    																HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterInfoAction doEnquiryDetail");
    		DynaActionForm frmEnquiryDetail= (DynaActionForm)form;
    		String strType ="";
    		DynaActionForm frmCallCenterInfo= (DynaActionForm) form;
    		PreAuthHospitalVO preAuthHospitalVO = null;
    		TableData  enquiryListData =(TableData)request.getSession().getAttribute("enquiryListData");
    		EnquiryDetailVO enquiryDetailVO=null;
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		InsuranceVO insuranceVO= null;
    		OfficeVO officeVO =null;
    		strType=(String)frmCallCenterInfo.get("sType");

    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			if(strType.equals("HOS"))           //getting the Hospital Details
    			{
    				preAuthHospitalVO = (PreAuthHospitalVO)enquiryListData.getRowInfo(Integer.parseInt(
    																				request.getParameter("rownum")));
    				enquiryDetailVO=callCenterManagerObject.getEnquiryDetail("HOS",preAuthHospitalVO.getHospSeqId());
    				frmEnquiryDetail = (DynaActionForm)FormUtils.setFormValues("frmEnquiryDetail",enquiryDetailVO,
    																					this, mapping, request);
    			}//end of  if(strType.equals("HOS"))
    			else if(strType.equals("INS"))      //getting the Insurance Details
    			{
    				insuranceVO = (InsuranceVO)enquiryListData.getRowInfo(Integer.parseInt(
    																				request.getParameter("rownum")));
    				enquiryDetailVO=callCenterManagerObject.getEnquiryDetail(strType,insuranceVO.getInsuranceSeqID());
    				frmEnquiryDetail = (DynaActionForm)FormUtils.setFormValues("frmEnquiryDetail",enquiryDetailVO,
    																			this, mapping, request);
    			}//end of else if(strType.equals("INS"))
    			else if(strType.equals("TTK"))      //getting theVidal Health TPAOffice Details
    			{
    				officeVO = (OfficeVO)enquiryListData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
    				enquiryDetailVO=callCenterManagerObject.getEnquiryDetail(strType,officeVO.getOfficeSeqId());
    				frmEnquiryDetail = (DynaActionForm)FormUtils.setFormValues("frmEnquiryDetail",enquiryDetailVO,
    																this, mapping, request);
    			}//end of else if(strType.equals("TTK"))
    			request.setAttribute("frmEnquiryDetail",frmEnquiryDetail);
    		}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		return this.getForward(strEnquiryDetail,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doEnquiryDetailMode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    //HttpServletResponse response)

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
    		log.debug("Inside CallCenterInfoAction doClose");
    		TableData  enquiryListData =(TableData)request.getSession().getAttribute("enquiryListData");
    		String strType ="";
    		DynaActionForm frmCallCenterInfo= (DynaActionForm) form;
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		PolicyManager policyManagerObject=this.getPolicyManagerObject();
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		strType=(String)frmCallCenterInfo.get("sType");
    		if(enquiryListData.getSearchData().size()>1)
    		{
    			ArrayList alCallSearch = null;
    			if(strType.equals("TTK"))
    			{
    				alCallSearch=callCenterManagerObject.getTTKOfficeList(enquiryListData.getSearchData());
    			}//end of if(strType.equals("TTK"))
    			else if(strType.equals("INS"))
    			{
    				alCallSearch=policyManagerObject.getInsuranceCompanyList(enquiryListData.getSearchData());
    			}//end of else if(strType.equals("INS"))
    			else
    			{
    				alCallSearch=hospitalObject.getPreAuthHospitalList(enquiryListData.getSearchData());
    			}//end of else
    			enquiryListData.setData(alCallSearch, "search");
    			//set the table data object to session
    			request.getSession().setAttribute("enquiryListData",enquiryListData);
    		}//end of if(enquiryListData.getSearchData().size()>1)
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
    }//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doCallInfoClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    																	HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterInfoAction doCallInfoClose");
    		return this.getForward(strCallCenterDetails,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strCallCenter));
    	}//end of catch(Exception exp)
    }//end of doCallInfoClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    	log.debug("inside CallCenterSearchAction popularSearchCriteria");
    	//build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        String strType = (String)request.getParameter("sType");
        if(strType.equals("HOS"))
        {
            alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.get("sEmpanelmentNo")));
            alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.get("sHospitalName")));
            alSearchParams.add((String)frmCallCenterList.get("stateCode"));
            alSearchParams.add((String)frmCallCenterList.get("cityCode"));
            alSearchParams.add(null);
            alSearchParams.add(TTKCommon.getUserSeqId(request));
            String GroupID=(String)request.getSession().getAttribute("groupId");//OPD_4_hosptial
    		String paymentType=(String)request.getSession().getAttribute("paymentType");//OPD_4_hosptial	
            alSearchParams.add(GroupID);//OPD_4_hosptial
    		alSearchParams.add(paymentType);//OPD_4_hosptial
        }//end of if(strType.equals("HOS"))
        else if(strType.equals("INS"))
        {
            alSearchParams.add(((String)frmCallCenterList.get("sInsuranceSeqID")));
            alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCallCenterList.get("sCompanyCode")));
            alSearchParams.add((String)frmCallCenterList.get("sTTKBranchCode"));
        }//end of else if(strType.equals("INS"))
        else
        {
            alSearchParams.add(TTKCommon.getUserSeqId(request));
        }//end of else
        
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
            throw new TTKException(exp,strCallCenter);
        }//end of catch
        return callCenterManager;
    }//end getCallCenterManagerObject()

    /**
     * Returns the PolicyManager session object for invoking methods on it.
     * @return PolicyManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private PolicyManager getPolicyManagerObject() throws TTKException
    {
        PolicyManager policyManager = null;
        try
        {
            if(policyManager == null)
            {
                InitialContext ctx = new InitialContext();
                policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
            }//end of if(PolicyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "callcenterview");
        }//end of catch
        return policyManager;
    }//end getPolicyManagerObject()

    /**
     * Returns the HospitalManager session object for invoking methods on it.
     * @return HospitalManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private HospitalManager getHospitalManagerObject() throws TTKException
    {
        HospitalManager hospManager = null;
        try
        {
            if(hospManager == null)
            {
                InitialContext ctx = new InitialContext();
                hospManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
            }//end if(hospManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "hospitalsearch");
        }//end of catch
        return hospManager;
    }//end getHospitalManagerObject()
}// end of CallCenterSearchAction