/**
 * @ (#) SearchPCSAction.java
 * Project      : TTK HealthCare Services
 * File         : SearchPCSAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : August 26th, 2008
 *
 * @author       :Chandrasekaran J
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
import com.ttk.dto.administration.ProcedureDetailVO;




public class SearchPCSAction extends TTKAction
{
	private static Logger log = Logger.getLogger( SearchPCSAction.class );
	 //Modes.
	 private static final String strBackward="Backward";
	 private static final String strForward="Forward";
	 
	 private static final String strPCSlist="pcslist";
	 private static final String strPCSDetail="pcsdetail";
	 private static final String strClosePCSDetail="closepcsdetail";
	
	
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
   		log.info("Inside SearchPCSAction doDefault");
   		DynaActionForm frmSearchPCS=(DynaActionForm)form;
   		DynaActionForm frmAddPCSCode=(DynaActionForm)request.getSession().getAttribute("frmAddPCSCode");
   		StringBuffer sbfCaption = new StringBuffer();
   		if(frmAddPCSCode.getString("procedureCode")!= null &&!frmAddPCSCode.getString("procedureCode").equals(""))
   		{	
   			sbfCaption.append(" - [").append(frmAddPCSCode.getString("procedureCode")).append("]");
   		}// end of if(frmAddPCSCode.getString("masterProcCode")!= null &&!frmAddPCSCode.getString("masterProcCode").equals(""))
   		TableData pcsListTableData = null;
		//get the Table Data From the session 
        if(request.getSession().getAttribute("pcsListData")!=null) 
        {
        	pcsListTableData=(TableData)(request.getSession()).getAttribute("pcsListTableData");
        }//end of if(request.getSession().getAttribute("pcsListTableData")!=null)
        else
        {
        	pcsListTableData=new TableData();
        }//end of else
		//get the tbale data from session if exists
		pcsListTableData.createTableInfo("PCSTable",null);
		request.getSession().setAttribute("pcsListTableData",pcsListTableData);
		frmSearchPCS.initialize(mapping);//reset the form data
		frmSearchPCS.set("caption",sbfCaption.toString());
		return this.getForward(strPCSlist, mapping, request);           
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"pcslist"));
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
   		setLinks(request);
   		log.debug("Inside SearchPCSAction doSearch");
   		TableData pcsListTableData = null;
		//get the Table Data From the session 
        if(request.getSession().getAttribute("pcsListTableData")!=null) 
        {
        	pcsListTableData=(TableData)(request.getSession()).getAttribute("pcsListTableData");
        }//end of if(request.getSession().getAttribute("pcsListTableData")!=null)
        else
        {
        	pcsListTableData=new TableData();
        }//end of else
   		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
   		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
   		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
   		if(!strPageID.equals("") || !strSortID.equals(""))
   		{
   			if(!strPageID.equals(""))
   			{
   				pcsListTableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
   				return (mapping.findForward(strPCSlist));
   			}///end of if(!strPageID.equals(""))
   			else
   			{
   				pcsListTableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
   				pcsListTableData.modifySearchData("sort");//modify the search data
   			}//end of else
   		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
   		else
   		{
   			// create the required grid table
   			pcsListTableData.createTableInfo("PCSTable",null);
   			//fetch the data from the data access layer and set the data to table object
   			pcsListTableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
   			pcsListTableData.modifySearchData("search");
   		}//end of else
   		ArrayList alPcsList = maintenanceManagerObject.getPcsList(pcsListTableData.getSearchData());
   		pcsListTableData.setData(alPcsList, "search");
   		request.getSession().setAttribute("pcsListTableData",pcsListTableData);
   		//finally return to the grid screen
   		return this.getForward(strPCSlist, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
   	{
   		expTTK.printStackTrace();
   		return this.processExceptions(request, mapping, expTTK);
   	}//end of catch(TTKException expTTK)
   	catch(Exception exp)
   	{
   		exp.printStackTrace();
   		return this.processExceptions(request, mapping, new TTKException(exp, "pcslist"));
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
   		log.debug("Inside SearchPCSAction doForward");
   		
   		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
   		TableData pcsListTableData = null;
		//get the Table Data From the session 
        if(request.getSession().getAttribute("pcsListTableData")!=null) 
        {
        	pcsListTableData=(TableData)(request.getSession()).getAttribute("pcsListTableData");
        }//end of if(request.getSession().getAttribute("pcsListTableData")!=null)
        else
        {
        	pcsListTableData=new TableData();
        }//end of else
   		pcsListTableData.modifySearchData(strForward);//modify the search data
   		//fetch the data from the data access layer and set the data to table object
   		ArrayList alPcsList = maintenanceManagerObject.getPcsList(pcsListTableData.getSearchData());
   		pcsListTableData.setData(alPcsList, strForward);//set the table data
   		request.getSession().setAttribute("pcsListTableData",pcsListTableData);//set the table data object to session
   		return this.getForward(strPCSlist, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
   	{
   		return this.processExceptions(request, mapping, expTTK);
   	}//end of catch(TTKException expTTK)
   	catch(Exception exp)
   	{
   		return this.processExceptions(request, mapping, new TTKException(exp, "pcslist"));
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
   		log.debug("Inside SearchPCSAction doBackward");
   		TableData pcsListTableData = null;
		//get the Table Data From the session 
        if(request.getSession().getAttribute("pcsListTableData")!=null) 
        {
        	pcsListTableData=(TableData)(request.getSession()).getAttribute("pcsListTableData");
        }//end of if(request.getSession().getAttribute("pcsListTableData")!=null)
        else
        {
        	pcsListTableData=new TableData();
        }//end of else
   		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
   		pcsListTableData.modifySearchData(strBackward);//modify the search data
   		//fetch the data from the data access layer and set the data to table object
   		ArrayList alPcsList = maintenanceManagerObject.getPcsList(pcsListTableData.getSearchData());
   		pcsListTableData.setData(alPcsList, strBackward);//set the table data
   		request.getSession().setAttribute("pcsListTableData",pcsListTableData);//set the table data object to session
   		return this.getForward(strPCSlist, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
   	{
   		return this.processExceptions(request, mapping, expTTK);
   	}//end of catch(TTKException expTTK)
   	catch(Exception exp)
   	{
   		return this.processExceptions(request, mapping, new TTKException(exp,"pcslist"));
   	}//end of catch(Exception exp)
   }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
   
   /**
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
   public ActionForward doSelectPCS(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   								HttpServletResponse response) throws Exception{
   	try{
   		setLinks(request);
   		log.info("Inside SearchPCSAction doSelectPCS");
        DynaActionForm frmAddPCSCode=(DynaActionForm)request.getSession().getAttribute("frmAddPCSCode");
   		ProcedureDetailVO procedureDetailVO = null;
   		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		{
   			procedureDetailVO = (ProcedureDetailVO)((TableData)request.getSession().getAttribute("pcsListTableData")).
            getData().get(Integer.parseInt(request.getParameter("rownum")));
			frmAddPCSCode.set("masterProcCode",procedureDetailVO.getProcedureCode()) ;
			frmAddPCSCode.set("frmChanged","changed");
		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals(""))) 
   		return this.getForward(strPCSDetail, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
   	{
   		return this.processExceptions(request, mapping, expTTK);
   	}//end of catch(TTKException expTTK)
   	catch(Exception exp)
   	{
   		return this.processExceptions(request, mapping, new TTKException(exp,"pcslist"));
   	}//end of catch(Exception exp)
   }//end of doSelectPCS(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.info("Inside the doClose method of SearchPCSAction");
			setLinks(request);
			request.getSession().removeAttribute("frmSearchPCS");
            request.getSession().removeAttribute("pcsListTableData");
			return this.getForward(strClosePCSDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"pcslist"));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
   /**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param  frmDayCareGroupList DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchPCS,HttpServletRequest request) throws TTKException
   {
       //build the column names along with their values to be searched
       ArrayList<Object> alSearchParams = new ArrayList<Object>();
       alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchPCS.getString("sPCSCode")));
       alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchPCS.getString("sPCSName")));
       alSearchParams.add(TTKCommon.getUserSeqId(request));
       return alSearchParams;
   }//end of populateSearchCriteria(DynaActionForm frmFloatlist)
   /**
	 * Returns the MaintenanceManager session object for invoking methods on it.
	 * @return MaintenanceManager session object which can be used for method invocation
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
			throw new TTKException(exp, "ICDAction");
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject() 

}//end of SearchPCSAction class
