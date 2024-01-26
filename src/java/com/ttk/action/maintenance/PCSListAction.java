/**
 * @ (#) PCSListAction.java
 * Project      : TTK HealthCare Services
 * File         : PCSListAction.java
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : August 22, 2008
 *
 * @author       :Balaji C R B
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


import formdef.plugin.util.FormUtils;

public class PCSListAction extends TTKAction{
	private static Logger log = Logger.getLogger( PCSListAction.class );
	 //Modes.
	 private static final String strBackward="Backward";
	 private static final String strForward="Forward";
	 
	 private static final String strPCSlist="pcslist";
	 private static final String strPCSDetail="pcsdetail";
	 private static final String strSavePCSDetail="savepcsdetail";
	 private static final String strSearchpCS="searchpcs";
	
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
    		log.info("Inside PCSListAction doDefault");
    		DynaActionForm frmPCSList=(DynaActionForm)form;
    		String strTable = "";
			/*if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				frmPCSList.initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))*/
			//get the tbale data from session if exists
			TableData pcsListData =TTKCommon.getTableData(request);
			//create new table data object
			pcsListData = new TableData();
			//create the required grid table
			strTable = "PCSTable";
			pcsListData.createTableInfo(strTable,new ArrayList());
			request.getSession().setAttribute("tableData",pcsListData);
			frmPCSList.initialize(mapping);//reset the form data
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
    		log.debug("Inside PCSListAction doSearch");
    		TableData pcsListData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
    			((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				pcsListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strPCSlist));
    			}///end of if(!strPageID.equals(""))
    			else
    			{
    				pcsListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				pcsListData.modifySearchData("sort");//modify the search data
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			// create the required grid table
    			pcsListData.createTableInfo("PCSTable",null);
    			//fetch the data from the data access layer and set the data to table object
    			pcsListData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			pcsListData.modifySearchData("search");
    		}//end of else
    		ArrayList alPcsList = maintenanceManagerObject.getPcsList(pcsListData.getSearchData());
    		pcsListData.setData(alPcsList, "search");
    		request.getSession().setAttribute("tableData",pcsListData);
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
    		log.debug("Inside PCSListAction doForward");
    		TableData pcsListData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		pcsListData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alPcsList = maintenanceManagerObject.getPcsList(pcsListData.getSearchData());
    		pcsListData.setData(alPcsList, strForward);//set the table data
    		request.getSession().setAttribute("tableData",pcsListData);//set the table data object to session
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
    		log.debug("Inside PCSListAction doBackward");
    		TableData pcsListData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		pcsListData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alPcsList = maintenanceManagerObject.getPcsList(pcsListData.getSearchData());
    		pcsListData.setData(alPcsList, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",pcsListData);//set the table data object to session
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
     * This method initialize an empty screen for entering PCS Code details 
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
    			  log.info("Inside PCSListAction doAdd");
    			  setLinks(request);
    			  StringBuffer sbfCaption= new StringBuffer();
    			  sbfCaption.append(" Add");
    			  DynaActionForm frmAddPCSCode = (DynaActionForm)form;
    			  frmAddPCSCode.initialize(mapping);
    			  frmAddPCSCode.set("caption",sbfCaption.toString());
    			  request.getSession().setAttribute("frmAddPCSCode",frmAddPCSCode);
    			  return this.getForward(strPCSDetail, mapping, request);
    		}catch(TTKException expTTK){
    			return this.processExceptions(request, mapping, expTTK);
    		}catch(Exception exp)
    		{
    			return this.processExceptions(request, mapping, new TTKException(exp,"pcslist"));
    		}
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewPCS(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.info("Inside PCSListAction doViewPCS");
    		TableData pcsListData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceObject=this.getMaintenanceManagerObject();
    		DynaActionForm frmAddPCSCode = (DynaActionForm)form;
    		Long lngProcedureid = null;
    		ProcedureDetailVO procedureDetailVO = null;
    		StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append(" Edit");
    		if(!((String)(frmAddPCSCode).get("rownum")).equals(""))
			{
    			procedureDetailVO=(ProcedureDetailVO)pcsListData.getRowInfo(Integer.parseInt(
													(String)frmAddPCSCode.get("rownum")));
    			lngProcedureid = procedureDetailVO.getProcedureID();
				procedureDetailVO = maintenanceObject.getPcsInfo(lngProcedureid);
				frmAddPCSCode =(DynaActionForm)FormUtils.setFormValues("frmAddPCSCode", procedureDetailVO,this, mapping, request);
			}//end of if(!((String)(frmAddPCSCode).get("rownum")).equals("")) 
    		frmAddPCSCode.set("caption",sbfCaption.toString());
    		request.getSession().setAttribute("frmAddPCSCode", frmAddPCSCode);
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
    }//end of doViewPCS(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.info("Inside doSave method of PCSListAction");
    		MaintenanceManager maintenanceObject=this.getMaintenanceManagerObject();
    		DynaActionForm frmAddPCSCode =(DynaActionForm)form;
    		StringBuffer sbfCaption= new StringBuffer();
    		ProcedureDetailVO procedureDetailVO = new ProcedureDetailVO();
    		procedureDetailVO=(ProcedureDetailVO)FormUtils.getFormValues(frmAddPCSCode,this,mapping,request);
    		procedureDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    		int iCount=maintenanceObject.savePcsInfo(procedureDetailVO);	
    		if(iCount>0)
    		{
    			if(frmAddPCSCode.getString("procedureID")!=null && !frmAddPCSCode.getString("procedureID").equals("") )
    			{
    				sbfCaption.append(" Edit");
    				request.setAttribute("updated","message.savedSuccessfully");
    			}//end of if(!frmAddPCSCode.getString("procedureID").equals(""))
    			else
    			{
    				sbfCaption.append(" Add");
    				request.setAttribute("updated","message.addedSuccessfully");
    				frmAddPCSCode.initialize(mapping);
    			}//end of else
    			
    		}//end of if(iCount>0)
    		frmAddPCSCode.set("frmChanged", "");
    		frmAddPCSCode.set("caption",sbfCaption.toString());
    		request.getSession().setAttribute("frmAddPCSCode", frmAddPCSCode);
            return this.getForward(strSavePCSDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,"pcslist"));
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
    		log.info("Inside PCSListAction doReset");
    		setLinks(request);
    		ProcedureDetailVO procedureDetailVO = new ProcedureDetailVO();
    		DynaActionForm frmAddPCSCode = (DynaActionForm)form;
    		StringBuffer sbfCaption= new StringBuffer();
    		MaintenanceManager maintenanceObject=this.getMaintenanceManagerObject();
    		if(!frmAddPCSCode.getString("procedureID").equals("")&& frmAddPCSCode.getString("procedureID")!=null)
    		{
    			procedureDetailVO=maintenanceObject.getPcsInfo(TTKCommon.getLong(frmAddPCSCode.getString("procedureID")));
    			frmAddPCSCode =(DynaActionForm)FormUtils.setFormValues("frmAddPCSCode", procedureDetailVO,this, mapping, request);
    			sbfCaption.append(" Edit");
    		}//end of if(!frmAddPCSCode.getString("procedureID").equals("")&& frmAddPCSCode.getString("procedureID")!=null)
    		else
    		{
    			sbfCaption.append(" Add");
    			frmAddPCSCode.initialize(mapping);
    		}//end of else
    		frmAddPCSCode.set("caption",sbfCaption.toString());
    		request.getSession().setAttribute("frmAddPCSCode", frmAddPCSCode);
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
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	/**
     * This method is called on click of Select Group icon
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
    		log.debug("Inside PCSListAction doSelectPCS");
    		return mapping.findForward(strSearchpCS);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,"pcslist"));
    	}//end of catch(Exception exp)
    }//end of doSelectPCS(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     //HttpServletResponse response)
    
    /**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param  frmDayCareGroupList DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPCSList,HttpServletRequest request) throws TTKException
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPCSList.getString("sPCSCode")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPCSList.getString("sPCSName")));
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
}
