/**
 * @ (#) CriticalGroupsAction.java 22nd Oct 2007
 * Project       : TTK HealthCare Services
 * File          : CriticalGroupsAction.java
 * Author        : Yogesh S.C
 * Company       : Span Systems Corporation
 * Date Created  : 22nd Oct 2007
 *
 * @author       : Yogesh S.C
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
import com.ttk.dto.maintenance.CriticalGroupVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching the List of DayCare Groups.
 * This class also provides option for Addition of DayCare Groups.
 */
public class CriticalGroupsAction extends TTKAction{
	
	 private static Logger log = Logger.getLogger( CriticalGroupsAction.class );
     //	  Action mapping forwards.
	 private static final String strcriticalgrouplist="criticalgrouplist";
	 private static final String strcriticalgroup="criticalgroup";
	 private static final String streditcriticalgroups = "editcriticalgroups";
	 
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
    							   HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside DayCareGroupsAction doDefault");
    		TableData  tableData =TTKCommon.getTableData(request);    		
    		//create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("CriticalDayCareGroupsTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		((DynaActionForm)form).initialize(mapping);//reset the form data    		    	
    		return this.getForward(strcriticalgrouplist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strcriticalgroup));
    	}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to add the record.
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
    		log.debug("Inside CriticalGroupsAction doAdd");
    		//((DynaActionForm)form).initialize(mapping);//reset the form data 
    		return this.getForward(streditcriticalgroups, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strcriticalgroup));
    	}//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    	
    	log.debug("Inside CriticalGroupsAction doSearch");
    	TableData  tableData =TTKCommon.getTableData(request);
    	MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
			{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward(strcriticalgrouplist));
			}///end of if(!strPageID.equals(""))
			else
			{
				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData.modifySearchData("sort");//modify the search data
			}//end of else
		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else
		{
			// create the required grid table
			tableData.createTableInfo("CriticalDayCareGroupsTable",null);
			//fetch the data from the data access layer and set the data to table object
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
			tableData.modifySearchData("search");
		}//end of else
		ArrayList alCritical = maintenanceManagerObject.getCriticalGroupList(tableData.getSearchData());
		tableData.setData(alCritical, "search");
		request.getSession().setAttribute("tableData",tableData);
		//finally return to the grid screen
		return this.getForward(strcriticalgrouplist, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		expTTK.printStackTrace();
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		exp.printStackTrace();
		return this.processExceptions(request, mapping, new TTKException(exp, strcriticalgroup));
	}//end of catch(Exception exp)
}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside CriticalGroupsAction doSave");
    		MaintenanceManager maintenanceObject=this.getMaintenanceManagerObject();
			String strGroupId=null;
			DynaActionForm frmCriticalGroupDetails = (DynaActionForm)form;
			CriticalGroupVO criticalGroupVO=new CriticalGroupVO();
			if((String)frmCriticalGroupDetails.get("groupID")!=null && (String)frmCriticalGroupDetails.
																						get("groupID")!="")
			{
				strGroupId=(String)frmCriticalGroupDetails.get("groupID");
				criticalGroupVO.setGroupID(strGroupId);
			}//if((String)frmDayCareGroupAdd.get("groupId")!=null && (String)frmDayCareGroupAdd.
				//get("groupId")!="")
			
			criticalGroupVO=(CriticalGroupVO)FormUtils.getFormValues(frmCriticalGroupDetails,this,mapping,request);
			/*log.info("Group ID is  :"+daycareGroupVO.getGroupID());
			log.info("Group name is:"+daycareGroupVO.getGroupName());
			log.info("Group Des is :"+daycareGroupVO.getGroupDesc());*/
			/*daycareGroupVO.setGroupName((String)frmDayCareGroupDetails.get("groupName"));
			daycareGroupVO.setGroupDesc((String)frmDayCareGroupDetails.get("groupDesc"));
*/			criticalGroupVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			int count=maintenanceObject.saveCriticalGroupDetail(criticalGroupVO);
			
			if(strGroupId!=null && count>0)
			{
				request.setAttribute("updated","message.savedSuccessfully");
			}//end of if(strGroupId!=null && count>0)
			else if(strGroupId==null)
			{
				frmCriticalGroupDetails.initialize(mapping);
				request.setAttribute("updated","message.addedSuccessfully");
			}//end of else if
    		
    		return this.getForward(streditcriticalgroups, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strcriticalgroup));
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
    		setLinks(request);
    		log.debug("Inside CriticalGroupsAction doClose");
    		/*DynaActionForm frmDayCareGroupDetails = (DynaActionForm)form;
    		DaycareGroupVO daycareGroupVO = null;*/
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		TableData tableData = TTKCommon.getTableData(request);
    		if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				//refresh the data in cancel mode, in order to get the new records if any.
				ArrayList alCritical=  maintenanceManagerObject.getCriticalGroupList(tableData.getSearchData());
				tableData.setData(alCritical);
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if  
    		return this.getForward(strcriticalgrouplist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strcriticalgroup));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) 
    
    
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
    		log.info("Inside CriticalGroupsAction doReset");
    		//((DynaActionForm)form).initialize(mapping);
    		return doViewCriticalGroups(mapping, form, request, response);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strcriticalgroup));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)    
    
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
    public ActionForward doViewCriticalGroups(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CriticalGroupsAction doViewDaycareGroups");
    		TableData tableData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceObject=this.getMaintenanceManagerObject();
    		DynaActionForm frmCriticalGroupDetails = (DynaActionForm)form;
    		String strGroupID = "";
    		CriticalGroupVO criticalGroupVO = null;
    		Long lngUserSeqID = null;
    		if(!((String)(frmCriticalGroupDetails).get("rownum")).equals(""))
			{
    			criticalGroupVO=(CriticalGroupVO)tableData.getRowInfo(Integer.parseInt(
													(String)frmCriticalGroupDetails.get("rownum")));
				
				strGroupID = criticalGroupVO.getGroupID();
				lngUserSeqID = TTKCommon.getUserSeqId(request);
				
				criticalGroupVO = maintenanceObject.getCriticalGroupDetail(strGroupID, lngUserSeqID);
				frmCriticalGroupDetails =(DynaActionForm)FormUtils.setFormValues("frmCriticalGroupDetails", criticalGroupVO,this, mapping, request);
				request.setAttribute("frmCriticalGroupDetails", frmCriticalGroupDetails);
				/*frmDayCareGroupDetails.set("groupName",daycareGroupVO.getGroupName());
				frmDayCareGroupDetails.set("groupDesc",daycareGroupVO.getGroupDesc());
				frmDayCareGroupDetails.set("groupID",daycareGroupVO.getGroupID());*/
			}//end of if(!((String)(frmDayCareGroupAdd).get("rownum")).equals(""))    
    		else
    		{
    			((DynaActionForm)form).initialize(mapping);//reset the form data 
    		}//end of else
    		return this.getForward(streditcriticalgroups, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strcriticalgroup));
    	}//end of catch(Exception exp)
    }//end of doViewDaycareGroups(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
     
    
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
			log.debug("Inside CriticalGroupsAction doForward");
			TableData  tableData =TTKCommon.getTableData(request);
			MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alCriticalGroup = maintenanceManagerObject.getCriticalGroupList(tableData.getSearchData());
			tableData.setData(alCriticalGroup, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strcriticalgrouplist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strcriticalgroup));
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
    		log.debug("Inside CriticalGroupsAction doBackward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alCriticalGroup = maintenanceManagerObject.getCriticalGroupList(tableData.getSearchData());
    		tableData.setData(alCriticalGroup, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		return this.getForward(strcriticalgrouplist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strcriticalgroup));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
        
    
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
    //yet to be implemented
    public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		    HttpServletResponse response) throws Exception{
    	
    	try{
	    		log.debug("Inside the doDeleteList method of CriticalGroupsAction");
				setLinks(request);
				MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
				//get the tbale data from session if exists
				TableData tableData =TTKCommon.getTableData(request);
				String strDeleteId = "";
				
				ArrayList<Object> alCriticalGroupList=new ArrayList<Object>();
				//populate the delete string which contains user sequence id's to be deleted
				//if deleting from grid screen, get the populated "chkopt" values.
				strDeleteId = populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData"));
				log.info(strDeleteId);
				//seq ids of the Daycare Groups to be deleted
				alCriticalGroupList.add("|"+strDeleteId+"|");
				alCriticalGroupList.add(TTKCommon.getUserSeqId(request));			
				
				//call the Dao method to delete the Critical Groups
				int iCount=maintenanceManagerObject.deleteCriticalGroup(alCriticalGroupList);			
				
				//refresh the grid data
				ArrayList alCriticalGroups = null;
		
				//fetch the data from previous set of rowcounts, if all the records are deleted for the
				//current set of search criteria
				if(iCount == tableData.getData().size())
				{
					tableData.modifySearchData("Delete");//modify the search data
					int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
												get(tableData.getSearchData().size()-2));
					if(iStartRowCount > 0)
					{
						alCriticalGroups= maintenanceManagerObject.getCriticalGroupList(tableData.getSearchData());
					}//end of if(iStartRowCount > 0)
				}//end if(iCount == tableData.getData().size())
				
				else
				{
					alCriticalGroups = maintenanceManagerObject.getCriticalGroupList(tableData.getSearchData());
				}//end of else
				tableData.setData(alCriticalGroups, "Delete");
				request.getSession().setAttribute("tableData",tableData);
				return this.getForward(strcriticalgrouplist, mapping, request);
				
    	}
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strcriticalgroup));
		}//end of catch(Exception exp)
     	
    }
    /**
	 * Returns a string which contains the pipe separated critical Group sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the pipe separated critical Group sequence id's to be deleted
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
						sbfDeleteId.append(String.valueOf(((CriticalGroupVO)tableData.getData().
								get(Integer.parseInt(strChk[i]))).getGroupID()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((CriticalGroupVO)tableData.
								getData().get(Integer.parseInt(strChk[i]))).getGroupID()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
    
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
			throw new TTKException(exp, strcriticalgroup);
		}//end of catch
		return maintenanceManager;
	}//end getMaintenanceManagerObject()
	
	/**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param  frmDayCareGroupList DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCriticalGroupList,HttpServletRequest request) throws TTKException
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCriticalGroupList.getString("sGroupName")));
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmFloatlist)
	
	
}//end of class CriticalGroupsAction

	
