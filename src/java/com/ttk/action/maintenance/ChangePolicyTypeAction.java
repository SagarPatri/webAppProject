/**
 * @ (#) ChangePolicyTypeAction
 * Project       : TTK HealthCare Services
 * File          : ChangePolicyTypeAction.java
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : 16th May,2008
 *
 * @author       : 
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.maintenance;

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
import com.ttk.business.maintenance.MaintenanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.maintenance.PolicyListVO;

/**
 * This class is used for searching policy and changing its sub type 
 */
public class ChangePolicyTypeAction extends TTKAction{
	
	 private static final Logger log = Logger.getLogger( ChangePolicyTypeAction.class );
     //	  Action mapping forwards.
	 private static final String strPolicyList="policylist";
	 //	  Exception Message Identifier
	 private static final String strPolicy="policy";
     //   Modes.
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
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							   HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside ChangePolicyTypeAction doDefault");
    		TableData  tableData =TTKCommon.getTableData(request);    		
    		//create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("PolicyTypeTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		((DynaActionForm)form).initialize(mapping);//reset the form data    		    	
    		return this.getForward(strPolicyList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strPolicy));
    	}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to search the data with the given search criteria.
     * Finally it forwards to the appropriate view based on the specified forward mappings
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
    		setLinks(request);
    		log.debug("Inside ChangePolicyTypeAction doSearch");
    		DynaActionForm frmChangePolicyType = (DynaActionForm)form;
    		//resetting sPolicySubTypeID 
    		frmChangePolicyType.set("sPolicySubTypeID","");
    		String strPolicyNumber = frmChangePolicyType.getString("sPolicyNumber");
    		TableData  tableData =TTKCommon.getTableData(request);
    		if(strPolicyNumber==null || strPolicyNumber.equals(""))
    		{
    			request.setAttribute("policynumberrequired","message.policynumberrequired");
    			tableData.setData(new ArrayList(), "search");
    			return this.getForward(strPolicyList, mapping, request);
    		}//end of if(strPolicyNumber.equals(null)|| strPolicyNumber.equals(""))
    		else 
    		{
    			request.setAttribute("policynumberrequired",null);
    		}//end of else
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));    		 		
    		if(strPageID.equals(""))
    		{
//    			 create the required grid table
    			tableData.createTableInfo("PolicyTypeTable",null);
    			//fetch the data from the data access layer and set the data to table object
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
    			tableData.modifySearchData("search");    			
    		}//end of if(!strPageID.equals(""))
    		else
    		{
    			tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    			return mapping.findForward(strPolicyList);
    		}//end of else
    		ArrayList alPolicyList = maintenanceManagerObject.getPolicyList(tableData.getSearchData());
    		tableData.setData(alPolicyList, "search");
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strPolicyList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
//    		expTTK.printStackTrace();
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
//    		exp.printStackTrace();
    		return this.processExceptions(request, mapping, new TTKException(exp, strPolicy));
    	}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * This method is used to change the selected policy's sub type from
     * Floater to Non-Floater
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeToNonFloater(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							  HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside ChangePolicyTypeAction doChangeToNonFloater");
    		TableData  tableData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		String[] strChk = request.getParameterValues("chkopt");
    		PolicyListVO objPolicyListVO = (PolicyListVO)tableData.getData().get(Integer.parseInt(strChk[0]));
    		request.setAttribute("policynumberrequired",null);
    		ArrayList<Object> alChangeToPnf = new ArrayList<Object>();
    		alChangeToPnf.add(objPolicyListVO.getPolicySeqID());
    		alChangeToPnf.add(TTKCommon.getUserSeqId(request));
    		
    		int  iResult = maintenanceManagerObject.changeToNonFloater(alChangeToPnf);
    		log.debug("iResult is :" + iResult);
    		if(iResult!=0){    			
    			request.setAttribute("selectedRecord","");
    			((DynaActionForm)form).set("sPolicySubTypeID","");
    			request.setAttribute("updated","message.policySubTypeChangedSuccessfully");
    		}//end of if(iResult!=0) 
    		//requerying with the same search criteria
    		ArrayList alPolicyList = maintenanceManagerObject.getPolicyList(tableData.getSearchData());
    		tableData.setData(alPolicyList, "search");
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strPolicyList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
//    		expTTK.printStackTrace();
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
//    		exp.printStackTrace();
    		return this.processExceptions(request, mapping, new TTKException(exp, strPolicy));
    	}//end of catch(Exception exp)
    }//end of doChangeToNonFloater(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to change the selected policy's sub type from
     * Non-Floater to Floater
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    
    public ActionForward doChangeToFloater(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside ChangePolicyTypeAction doChangeToFloater");
    		TableData  tableData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		String[] strChk = request.getParameterValues("chkopt");
    		PolicyListVO objPolicyListVO = (PolicyListVO)tableData.getData().get(Integer.parseInt(strChk[0]));
    		request.setAttribute("policynumberrequired",null);
    		ArrayList<Object> alChangeToPfl = new ArrayList<Object>();
    		alChangeToPfl.add(objPolicyListVO.getPolicySeqID());
    		alChangeToPfl.add(TTKCommon.getUserSeqId(request));
    		
    		int  iResult = maintenanceManagerObject.changeToFloater(alChangeToPfl);
    		log.debug("iResult is :" + iResult);
    		if(iResult!=0){    			
    			request.setAttribute("selectedRecord","");
    			((DynaActionForm)form).set("sPolicySubTypeID","");
    			request.setAttribute("updated","message.policySubTypeChangedSuccessfully");
    		}//end of if(iResult!=0) 		
    		//requerying with the same search criteria
    		ArrayList alPolicyList = maintenanceManagerObject.getPolicyList(tableData.getSearchData());
    		tableData.setData(alPolicyList, "search");
    		request.getSession().setAttribute("tableData",tableData);
//  		finally return to the grid screen
    		return this.getForward(strPolicyList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
//    		expTTK.printStackTrace();
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
//    		exp.printStackTrace();
    		return this.processExceptions(request, mapping, new TTKException(exp, strPolicy));
    	}//end of catch(Exception exp)
    }//end of doChangeToFloater(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    		setLinks(request);
    		log.debug("Inside ChangePolicyTypeAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alPolicyList = maintenanceManagerObject.getPolicyList(tableData.getSearchData());
    		tableData.setData(alPolicyList, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		return this.getForward(strPolicyList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strPolicy));
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
    								HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside ChangePolicyTypeAction doBackward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alPolicyList = maintenanceManagerObject.getPolicyList(tableData.getSearchData());
    		tableData.setData(alPolicyList, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		return this.getForward(strPolicy, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strPolicy));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to set form value with which the button either 'Change to Non-Floater'
     * or 'Change to Floater' should be enabled based upon the policy selected from the search screen
     * Finally it forwards to the appropriate view based on the specified forward mappings
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doEnableButton(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside ChangePolicyTypeAction doEnableButton");
    		TableData  tableData =TTKCommon.getTableData(request);
    		String[] strChk = request.getParameterValues("chkopt");
    		PolicyListVO objPolicyListVO = (PolicyListVO)tableData.getData().get(Integer.parseInt(strChk[0]));
    		request.setAttribute("selectedRecord",Integer.parseInt(strChk[0]));
    		if("PFL".equals(objPolicyListVO.getPolicySubGeneralTypeID()))
    				{
    				((DynaActionForm)form).set("sPolicySubTypeID","PFL");
    				//enable change to non floater
    				}
    		else if("PNF".equals(objPolicyListVO.getPolicySubGeneralTypeID()))
    		{
    			((DynaActionForm)form).set("sPolicySubTypeID","PNF");
    				//enable change to floater
    		}    		
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strPolicyList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
//    		expTTK.printStackTrace();
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
//    		exp.printStackTrace();
    		return this.processExceptions(request, mapping, new TTKException(exp, strPolicy));
    	}//end of catch(Exception exp)
    }//end of doEnableButton(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param  frmDayCareGroupList DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmChangePolicyType) throws TTKException
	{
		try
		{
			//build the column names along with their values to be searched
			ArrayList<Object> alSearchParams = new ArrayList<Object>();
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmChangePolicyType.getString("sPolicyNumber")));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmChangePolicyType.getString("sInsuranceCompany")));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmChangePolicyType.getString("sEnrollmentType")));        
			return alSearchParams;
		}catch(Exception exp)
		{
			throw new TTKException(exp, strPolicy);
		}//end of catch
	}//end of populateSearchCriteria(DynaActionForm frmFloatlist)
	
    /**
	 * Returns the MaintenanceManager session object for invoking methods on it.
	 * @return MaintenanceManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MaintenanceManager getMaintenanceManagerObject() throws TTKException
	{
		MaintenanceManager maintenanceManager = null;
		try
		{
			if(maintenanceManager == null)
			{
				InitialContext ctx = new InitialContext();
				maintenanceManager = (MaintenanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/MaintenanceManagerBean!com.ttk.business.maintenance.MaintenanceManager");
			}//end if(maintenanceManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPolicy);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()
 
}//end of class ChanePolicyTypeAction
