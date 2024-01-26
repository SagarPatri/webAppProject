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
import com.ttk.dto.maintenance.InvestigationGroupVO;

import formdef.plugin.util.FormUtils;

public class InvestigationGroupAction extends TTKAction{
	private static Logger log = Logger.getLogger( InvestigationGroupAction.class );
    //	  Action mapping forwards.
	 private static final String strdaycaregrouplist="daycaregrouplist";
	 private static final String streditdaycaregroups="editdaycaregroups";
    //	  Exception Message Identifier
	 private static final String strdaycaregroup="daycaregroup";
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
   		log.debug("Inside InvestigationGroupAction doDefault");
   		log.info("				---Inside InvestigationGroupAction doDefault"); //koc11 koc 11
   		TableData  tableData =TTKCommon.getTableData(request);    		
   		//create new table data object
   		tableData = new TableData();
   		//create the required grid table
   		tableData.createTableInfo("InvestigationGroupsTable",new ArrayList());
   		request.getSession().setAttribute("tableData",tableData);
   		((DynaActionForm)form).initialize(mapping);//reset the form data    		    	
   		return this.getForward(strdaycaregrouplist, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
   	{
   		return this.processExceptions(request, mapping, expTTK);
   	}//end of catch(TTKException expTTK)
   	catch(Exception exp)
   	{
   		return this.processExceptions(request, mapping, new TTKException(exp, strdaycaregroup));
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
   		log.debug("Inside InvestigationGroupAction doSearch");
   		log.info("				---Inside InvestigationGroupAction doDefault");
   		TableData  tableData =TTKCommon.getTableData(request);
   		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
   		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
   		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
   		if(!strPageID.equals("") || !strSortID.equals(""))
   		{
   			if(!strPageID.equals(""))
   			{
   				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
   				return (mapping.findForward(strdaycaregrouplist));
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
   			tableData.createTableInfo("InvestigationGroupsTable",null);
   			//fetch the data from the data access layer and set the data to table object
   			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
   			tableData.modifySearchData("search");
   		}//end of else
   		ArrayList alDayCare = maintenanceManagerObject.getInvGroupList(tableData.getSearchData());
   		tableData.setData(alDayCare, "search");
   		request.getSession().setAttribute("tableData",tableData);
   		//finally return to the grid screen
   		return this.getForward(strdaycaregrouplist, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
   	{
   		expTTK.printStackTrace();
   		return this.processExceptions(request, mapping, expTTK);
   	}//end of catch(TTKException expTTK)
   	catch(Exception exp)
   	{
   		exp.printStackTrace();
   		return this.processExceptions(request, mapping, new TTKException(exp, strdaycaregroup));
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
   		log.debug("Inside InvestigationGroupAction doForward");
   		TableData  tableData =TTKCommon.getTableData(request);
   		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
   		tableData.modifySearchData(strForward);//modify the search data
   		//fetch the data from the data access layer and set the data to table object
   		ArrayList alDayCare = maintenanceManagerObject.getInvGroupList(tableData.getSearchData());
   		tableData.setData(alDayCare, strForward);//set the table data
   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
   		return this.getForward(strdaycaregrouplist, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
   	{
   		return this.processExceptions(request, mapping, expTTK);
   	}//end of catch(TTKException expTTK)
   	catch(Exception exp)
   	{
   		return this.processExceptions(request, mapping, new TTKException(exp, strdaycaregroup));
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
   		log.debug("Inside InvestigationGroupAction doBackward");
   		TableData  tableData =TTKCommon.getTableData(request);
   		MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
   		tableData.modifySearchData(strBackward);//modify the search data
   		//fetch the data from the data access layer and set the data to table object
   		ArrayList alDayCare = maintenanceManagerObject.getInvGroupList(tableData.getSearchData());
   		tableData.setData(alDayCare, strBackward);//set the table data
   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
   		return this.getForward(strdaycaregrouplist, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
   	{
   		return this.processExceptions(request, mapping, expTTK);
   	}//end of catch(TTKException expTTK)
   	catch(Exception exp)
   	{
   		return this.processExceptions(request, mapping, new TTKException(exp,strdaycaregroup));
   	}//end of catch(Exception exp)
   }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			throw new TTKException(exp, strdaycaregroup);
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
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmDayCareGroupList,HttpServletRequest request) throws TTKException
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmDayCareGroupList.getString("sGroupName")));
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmFloatlist)
	
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvestigationGroupAction doAdd");
			log.info("				---Inside InvestigationGroupAction doAdd");
			//((DynaActionForm)form).initialize(mapping);//reset the form data 
			return this.getForward(streditdaycaregroups, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strdaycaregroup));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDeleteList method of InvestigationGroupsAction");
			setLinks(request);
			MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			String strDeleteId = "";
			ArrayList<Object> alDayCareGroupList=new ArrayList<Object>();
			//populate the delete string which contains user sequence id's to be deleted
			//if deleting from grid screen, get the populated "chkopt" values.
			strDeleteId = populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData"));
			//seq ids of the Daycare Groups to be deleted
			alDayCareGroupList.add("|"+strDeleteId+"|");
			alDayCareGroupList.add(TTKCommon.getUserSeqId(request));			
			//call the Dao method to delete the Daycare Groups
			int iCount=maintenanceManagerObject.deleteInvGroup(alDayCareGroupList);			
			//refresh the grid data
			ArrayList alDayCareGroups = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the
			//current set of search criteria
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
						get(tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alDayCareGroups= maintenanceManagerObject.getInvGroupList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alDayCareGroups= maintenanceManagerObject.getInvGroupList(tableData.getSearchData());
			}//end of else
			tableData.setData(alDayCareGroups, "Delete");
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strdaycaregrouplist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strdaycaregroup));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
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
						sbfDeleteId.append(String.valueOf(((InvestigationGroupVO)tableData.getData().
								get(Integer.parseInt(strChk[i]))).getGroupID()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((InvestigationGroupVO)tableData.
								getData().get(Integer.parseInt(strChk[i]))).getGroupID()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvestigationGroupsAction doSave");
			MaintenanceManager maintenanceObject=this.getMaintenanceManagerObject();
			String strGroupId=null;
			DynaActionForm frmInvestigationGroupDetails = (DynaActionForm)form;
			InvestigationGroupVO investigationGroupVO=new InvestigationGroupVO();
			if((String)frmInvestigationGroupDetails.get("groupID")!=null && (String)frmInvestigationGroupDetails.
					get("groupID")!="")
			{
				strGroupId=(String)frmInvestigationGroupDetails.get("groupID");
				investigationGroupVO.setGroupID(strGroupId);
			}//if((String)frmDayCareGroupAdd.get("groupId")!=null && (String)frmDayCareGroupAdd.
			//get("groupId")!="")
			
			investigationGroupVO=(InvestigationGroupVO)FormUtils.getFormValues(frmInvestigationGroupDetails,this,mapping,request);
			/*log.info("Group ID is  :"+daycareGroupVO.getGroupID());
				log.info("Group name is:"+daycareGroupVO.getGroupName());
				log.info("Group Des is :"+daycareGroupVO.getGroupDesc());*/
				/*daycareGroupVO.setGroupName((String)frmDayCareGroupDetails.get("groupName"));
				daycareGroupVO.setGroupDesc((String)frmDayCareGroupDetails.get("groupDesc"));
			 */			investigationGroupVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			 int count=maintenanceObject.saveInvGroupDetail(investigationGroupVO);

			 if(strGroupId!=null && count>0)
			 {
				 request.setAttribute("updated","message.savedSuccessfully");
			 }//end of if(strGroupId!=null && count>0)
			 else if(strGroupId==null)
			 {
				 frmInvestigationGroupDetails.initialize(mapping);
				 request.setAttribute("updated","message.addedSuccessfully");
			 }//end of else if

			 return this.getForward(streditdaycaregroups, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strdaycaregroup));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	public ActionForward doViewInvestigationGroups(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvestigationGroupsAction doViewInvestigationGroups");
			log.info("----------Inside InvestigationGroupsAction doViewInvestigationGroups"); //koc11 koc 11
			TableData tableData =TTKCommon.getTableData(request);
			MaintenanceManager maintenanceObject=this.getMaintenanceManagerObject();
			DynaActionForm frmInvestigationGroupDetails = (DynaActionForm)form;
			String strGroupID = "";
			InvestigationGroupVO investigationGroupVO = null;
			Long lngUserSeqID = null;
			if(!((String)(frmInvestigationGroupDetails).get("rownum")).equals(""))
			{	log.info("----------Inside InvestigationGroupsAction doViewInvestigationGroups---rownum"+tableData.getRowInfo(Integer.parseInt(
					(String)frmInvestigationGroupDetails.get("rownum"))));
				investigationGroupVO=(InvestigationGroupVO)tableData.getRowInfo(Integer.parseInt(
						(String)frmInvestigationGroupDetails.get("rownum")));
				log.info("----------strGroupID"+investigationGroupVO.getGroupID());
				strGroupID = investigationGroupVO.getGroupID();
				lngUserSeqID = TTKCommon.getUserSeqId(request);

				investigationGroupVO = maintenanceObject.getInvGroupDetail(strGroupID, lngUserSeqID);
				frmInvestigationGroupDetails =(DynaActionForm)FormUtils.setFormValues("frmInvestigationGroupDetails", investigationGroupVO,this, mapping, request);
				request.setAttribute("frmInvestigationGroupDetails", frmInvestigationGroupDetails);
				/*frmDayCareGroupDetails.set("groupName",daycareGroupVO.getGroupName());
frmDayCareGroupDetails.set("groupDesc",daycareGroupVO.getGroupDesc());
frmDayCareGroupDetails.set("groupID",daycareGroupVO.getGroupID());*/
			}//end of if(!((String)(frmDayCareGroupAdd).get("rownum")).equals(""))    
			else
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data 
			}//end of else
			return this.getForward(streditdaycaregroups, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strdaycaregroup));
		}//end of catch(Exception exp)
	}//end of doViewInvestigationGroups(-,-,-,-)
	
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.info("Inside InvestigationGroupsAction doReset");
			//((DynaActionForm)form).initialize(mapping);
			return doViewInvestigationGroups(mapping, form, request, response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strdaycaregroup));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvestigationGroupsAction doClose");
			/*DynaActionForm frmDayCareGroupDetails = (DynaActionForm)form;
DaycareGroupVO daycareGroupVO = null;*/
			MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				//refresh the data in cancel mode, in order to get the new records if any.
				ArrayList alDayCare=  maintenanceManagerObject.getInvGroupList(tableData.getSearchData());
				tableData.setData(alDayCare);
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if  
			return this.getForward(strdaycaregrouplist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strdaycaregroup));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) 
}//end of class InvestigationGroupAction