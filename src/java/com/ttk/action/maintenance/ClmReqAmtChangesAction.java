/**
 * @ (#) ClmReqAmtChangesAction.java
 * Project       : TTK HealthCare Services
 * File          : ClmReqAmtChangesAction.java
 * Author        : Balakrishna Erram
 * Company       : Span Systems Corporation
 * Date Created  : 9th August,2010
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
import com.ttk.dto.maintenance.ClaimListVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching policy and changing its sub type 
 */
public class ClmReqAmtChangesAction extends TTKAction{
	
	 private static final Logger log = Logger.getLogger( ClmReqAmtChangesAction.class );
     //	  Action mapping forwards.
	 private static final String strClaimsList="claimslist";
	 private static final String strClaimsReqAmt="claimsreqamt";
	 private static final String strClaimsDetails="claimsdetails";
	 private static final String strClaimsListClose="claimslistclose";
	 
	 //	  Exception Message Identifier
	 private static final String strMaintenanceClaims="maintenanceclaims";
	   
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
    		tableData.createTableInfo("ClmReqAmtTable",new ArrayList<Object>());
    		request.getSession().setAttribute("tableData",tableData);
    		((DynaActionForm)form).initialize(mapping);//reset the form data    		    	
    		return this.getForward(strClaimsList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strMaintenanceClaims));
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
    		log.info("Inside ClmReqAmtChangesAction doSearch");
    		DynaActionForm frmClaimReqAmt = (DynaActionForm)form;
    		String strClaimNumber = "";
    		if(request.getParameter("claimNumber") != null)
    		{
    			 strClaimNumber =request.getParameter("claimNumber");
    		}//end of if(request.getParameter("claimNumber") != null)
    		else
    		{
    			 strClaimNumber = frmClaimReqAmt.getString("sClaimNumber");
    		}//end of else
    		 
    		TableData  tableData =TTKCommon.getTableData(request);
    		
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));    		 		
    		if(strPageID.equals(""))
    		{
//    			 create the required grid table
    			tableData.createTableInfo("ClmReqAmtTable",null);
    			//fetch the data from the data access layer and set the data to table object
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,strClaimNumber));
    			tableData.modifySearchData("search");    			
    		}//end of if(!strPageID.equals(""))
    		else
    		{
    			tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    			return mapping.findForward(strClaimsReqAmt);
    		}//end of else
    		ArrayList<Object> alClaimList = maintenanceManagerObject.getClaimReqamt(strClaimNumber);
    		
    		tableData.setData(alClaimList, "search");
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strClaimsReqAmt, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strMaintenanceClaims));
    	}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
     
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
    public ActionForward doSelectClaimNo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							  HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		//((DynaActionForm)form).initialize(mapping);//reset the form data
    		log.info("Inside ClmReqAmtChangesAction doSelectClaimNo");
    		TableData tableData = TTKCommon.getTableData(request);
			//DynaActionForm frmClaimReqAmt=(DynaActionForm)form;
			ClaimListVO claimListVO=(ClaimListVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
			DynaActionForm  frmClaimReqAmtDetails = (DynaActionForm)FormUtils.setFormValues("frmClaimReqAmtDetails",
					claimListVO, this, mapping, request);
			request.getSession().setAttribute("frmClaimReqAmtDetails",frmClaimReqAmtDetails);
			
			return mapping.findForward(strClaimsDetails);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strMaintenanceClaims));
    	}//end of catch(Exception exp)
    }//end of doSelectClaimNo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  				//HttpServletResponse response)
    
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
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							  HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		//((DynaActionForm)form).initialize(mapping);//reset the form data
    		log.info("Inside ClmReqAmtChangesAction doClose");
    		
			return mapping.findForward(strClaimsListClose);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strMaintenanceClaims));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  				//HttpServletResponse response)
    
    /**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param  frmDayCareGroupList DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClaimReqAmt, String strClaimNumber) throws TTKException
	{
		try
		{
			//build the column names along with their values to be searched
			ArrayList<Object> alSearchParams = new ArrayList<Object>();
			alSearchParams.add(TTKCommon.replaceSingleQots(strClaimNumber));        
			return alSearchParams;
		}catch(Exception exp)
		{
			throw new TTKException(exp, strMaintenanceClaims);
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
			throw new TTKException(exp, strMaintenanceClaims);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()
 
}//end of class ChanePolicyTypeAction
