/**
 * @ (#) CriticalICDListAction.java
 * Project       : TTK HealthCare Services
 * File          : ProcedureListAction.java
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2007
 *
 * @author       : Balaji C R B
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
import com.ttk.dto.maintenance.CriticalGroupVO;
import com.ttk.dto.administration.CriticalProcedureDetailVO;
import com.ttk.dto.administration.CriticalICDDetailVO;

/**
* This class is used for Searching the Prcedure Code and Name.
*/

public class CriticalICDListAction extends TTKAction
{
	
	//forwards links
	private static final String strCriticalICDItem="criticalicditem";
	private static final String strCriticalICDlist="criticalicdlist";
	private static final String strCriticalGrouplist="criticalgrouplist";
	private static final String strForward = "Forward";
	private static final String strBackward ="Backward";
	
	//Exception Message Identifier
	private static final String strCriticalICDExp="criticalicditem";
	private static final String strcriticalgroup="criticalgroup";
	
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
			log.debug("Inside the doDefault method of CriticalICDListAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("ProcedureTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			return mapping.findForward(strCriticalICDlist);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCriticalICDExp));
		}//end of catch(Exception exp)
		
	}
	/**
	 * This method is used to navigate to critical procedure list screen
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	
	public ActionForward doViewCriticalICDList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			  HttpServletResponse response) throws Exception{

		try{
			
			log.debug("Inside the doViewCriticalICDList method of CriticalICDListAction");
			setLinks(request);
			DynaActionForm frmSearchProcedure = (DynaActionForm)form;
			//reset the form data
			frmSearchProcedure.initialize(mapping);
			//get the session bean from the bean pool for each excecuting thread
			TableData tableData = TTKCommon.getTableData(request);
			//create a new Product object
			CriticalGroupVO criticalGroupVO = new CriticalGroupVO();
			StringBuffer strCaption = new StringBuffer();
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				criticalGroupVO = (CriticalGroupVO)tableData.getRowInfo(Integer.parseInt((String)(request).getParameter("rownum")));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			strCaption.append("List of Procedures - [ ");
			strCaption.append(criticalGroupVO.getGroupName()+" ]");
			frmSearchProcedure.set("sGroupID",criticalGroupVO.getGroupID());	
			frmSearchProcedure.set("caption",strCaption.toString());
			request.getSession().setAttribute("groupTableData",tableData);
			return mapping.findForward(strCriticalICDItem);
			
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCriticalICDExp));
		}//end of catch(Exception exp)
	}
	
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
		
		try
		{
			log.debug(" Inside doClose method of CriticalICDListAction");
			setLinks(request);
			MaintenanceManager maintenanceManagerObject = this.getMaintenanceManagerObject();
			TableData tableData = (TableData)request.getSession().getAttribute("groupTableData");
			//if((Object)request.getSession().getAttribute("searchparam") != null)
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				//refresh the data in cancel mode, in order to get the new records if any.
				ArrayList alCriticalGroup = maintenanceManagerObject.getCriticalGroupList(tableData.getSearchData());
				tableData.setData(alCriticalGroup);
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
				
			}
			return this.getForward(strCriticalGrouplist, mapping, request);			
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strcriticalgroup));
		}//end of catch(Exception exp)
		
	}
	
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
		
		try
		{
			log.debug("Inside the doSearch method of CriticalICDListAction");
			setLinks(request);
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();						
			TableData tableData = TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
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
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return this.getForward(strCriticalICDlist, mapping, request);	
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("ProcedureTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form, request));
				tableData.modifySearchData("search");
			}//end of else   
			
			//get the Procedure here for search
			
			ArrayList alCriticalICDItem= maintenanceManagerObject.getCriticalICDList(tableData.getSearchData());
			
			
			tableData.setData(alCriticalICDItem, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strCriticalICDlist, mapping, request);				
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCriticalICDExp));
		}//end of catch(Exception exp)
		
	}
	
	
	/**
     * This method is used to Associate or Exclude the ICD
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    
	 public ActionForward doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
		 
		 try{
	    		log.debug("Inside doAssociate of CriticalICDListAction");
	    		setLinks(request);
	    		DynaActionForm frmSearchProcedure = (DynaActionForm)form;
	    		String strSeqIds = "";
	    		TableData tableData = TTKCommon.getTableData(request);
	    		MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();
	    		strSeqIds = this.getConcatenatedSeqIDAssociate(request, (TableData)request.getSession().getAttribute("tableData"));
	    		log.info("strSeqIds--:"+strSeqIds);
	    		ArrayList<Object> alCriticalICDList = new ArrayList<Object>();
	    		alCriticalICDList.add((String)frmSearchProcedure.get("sGroupID"));
	    		alCriticalICDList.add(strSeqIds);
	    		alCriticalICDList.add(TTKCommon.getUserSeqId(request));
	        	        	
	        	int intResult = maintenanceManagerObject.associateCriticalICD(alCriticalICDList);
	        	
	        	ArrayList alCriticalICDItem= maintenanceManagerObject.getCriticalICDList(tableData.getSearchData());
	        	
	        	 if(alCriticalICDItem.size() == 0 || intResult == tableData.getData().size())
	       	    {
	       	    	tableData.modifySearchData("Delete");
	                 int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
	                 					 getSearchData().size()-2));
	                 if(iStartRowCount > 0)
	                  {
	                	 alCriticalICDItem = maintenanceManagerObject.getCriticalICDList(tableData.getSearchData());
	                  }//end of if(iStartRowCount > 0)
	              }//end if(alContact.size() == 0 || iCount == tableData.getData().size())
	             tableData.setData(alCriticalICDItem, "Delete");
	             request.getSession().setAttribute("tableData",tableData);
	             return this.getForward(strCriticalICDlist, mapping, request);
		 }
		 catch(TTKException expTTK)
	     {
	    		return this.processExceptions(request, mapping, expTTK);
	     }//end of catch(TTKException expTTK)
	     catch(Exception exp)
		 {
				return this.processExceptions(request, mapping, new TTKException(exp,strCriticalICDExp));
		 }//end of catch(Exception exp)
		 
		 
	 }
	 /**Returns the String of concatenated string of 
		 * @param HttpServletRequest
		 * @param TableData
		 * @return String
		 */
		private String getConcatenatedSeqIDAssociate(HttpServletRequest request,TableData tableData) {
			StringBuffer sbfConcatenatedSeqId=new StringBuffer("");
			String strChOpt[]=request.getParameterValues("chkopt");
			if((strChOpt!=null)&& strChOpt.length!=0)
			{
				for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
				{
					
					if(strChOpt[iCounter]!=null)
					{	
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((CriticalICDDetailVO)tableData.getRowInfo(Integer.
								parseInt(strChOpt[iCounter]))).getProcedureID())).append("|").append(String.valueOf(((CriticalICDDetailVO)tableData.getRowInfo(Integer.
										parseInt(strChOpt[iCounter]))).getProcedureCode()));
						
					} // end of if(strChOpt[iCounter]!=null)
				} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)	
				sbfConcatenatedSeqId.append("|");
			} // end of if((strChOpt!=null)&& strChOpt.length!=0)
			
			//log.info("sbfConcatenatedSeqId.toString() :" + sbfConcatenatedSeqId.toString());
			return sbfConcatenatedSeqId.toString();
		} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
		   
	
	/**
	 * Returns the ArrayList which contains the populated search criteria elements.
	 * @param frmSearchProcedure DynaActionForm will contains the values of corresponding fields.
	 * @param request HttpServletRequest object which contains the search parameter that is built.
	 * @return alSearchParams ArrayList.
	 */	
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frm,HttpServletRequest request)
	{	
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		DynaActionForm frmSearchProcedure = (DynaActionForm)frm;
		alSearchParams.add((String)frmSearchProcedure.get("sGroupID"));
		alSearchParams.add(TTKCommon.replaceSingleQots ((String)frmSearchProcedure.get("sProcedureCode")));
		alSearchParams.add((String)frmSearchProcedure.get("sProcedureName"));
		alSearchParams.add((String)frmSearchProcedure.get("sDayCareProduct"));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchTariffItem,HttpServletRequest request)
	
	
	/**
     * This method is used to Associate or Exclude the ICD
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
	
	 public ActionForward doExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
		 
		 try{
			 
			 	log.debug("Inside doExclude of CriticalICDListAction");
			 	setLinks(request);
			 	DynaActionForm frmSearchProcedure = (DynaActionForm)form;
			 	String strSeqIds = "";
			 	TableData tableData = TTKCommon.getTableData(request);
			 	MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();
	    		
			 	strSeqIds = this.getConcatenatedSeqIDExclude(request, (TableData)request.getSession().getAttribute("tableData"));
			 	
			 	ArrayList<Object> alCriticalICDList = new ArrayList<Object>();
			 	alCriticalICDList.add((String)frmSearchProcedure.get("sGroupID"));
			 	alCriticalICDList.add(strSeqIds);
			 	alCriticalICDList.add(TTKCommon.getUserSeqId(request));
	        	
	        	
	        	int intResult = maintenanceManagerObject.disAssociateCriticalICD(alCriticalICDList);
	        	
	        	ArrayList alCriticalICDItem= maintenanceManagerObject.getCriticalICDList(tableData.getSearchData());
	            if(alCriticalICDItem.size() == 0 || intResult == tableData.getData().size())
	      	    {
	      	    	tableData.modifySearchData("Delete");
	                int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
	                					 getSearchData().size()-2));
	                if(iStartRowCount > 0)
	                 {
	                	alCriticalICDItem = maintenanceManagerObject.getCriticalICDList(tableData.getSearchData());
	                 }//end of if(iStartRowCount > 0)
	             }//end if(alContact.size() == 0 || iCount == tableData.getData().size())
	            tableData.setData(alCriticalICDItem, "Delete");
	            request.getSession().setAttribute("tableData",tableData);
	            return this.getForward(strCriticalICDlist, mapping, request);
			 
		 }
		catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCriticalICDExp));
		}//end of catch(Exception exp)
		 
	 }
	  
	
	 /**Returns the String of concatenated string of 
		 * @param HttpServletRequest
		 * @param TableData
		 * @return String
		 */
		private String getConcatenatedSeqIDExclude(HttpServletRequest request,TableData tableData) {
			StringBuffer sbfConcatenatedSeqId=new StringBuffer("");
			String strChOpt[]=request.getParameterValues("chkopt");
			if((strChOpt!=null)&& strChOpt.length!=0)
			{
				for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
				{
					
					if(strChOpt[iCounter]!=null)
					{	
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((CriticalICDDetailVO)tableData.getRowInfo(Integer.
								parseInt(strChOpt[iCounter]))).getProcedureID()));	
						
					} // end of if(strChOpt[iCounter]!=null)
				} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)	
				sbfConcatenatedSeqId.append("|");
			} // end of if((strChOpt!=null)&& strChOpt.length!=0)
			
			//log.info("ex sbfConcatenatedSeqId.toString() :" + sbfConcatenatedSeqId.toString());
			return sbfConcatenatedSeqId.toString();
		} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
			
	
		
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
				log.debug("Inside the doForward method of CriticalICDListAction");
				setLinks(request);
				MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();	
				TableData tableData = TTKCommon.getTableData(request);
				//modify the search data
				tableData.modifySearchData(strForward);
				
				ArrayList alCriticalICDItem= maintenanceManagerObject.getCriticalICDList(tableData.getSearchData());
				
				tableData.setData(alCriticalICDItem,strForward );//set the table data
				request.getSession().setAttribute("tableData",tableData);
				//finally return to the grid screen
				return this.getForward(strCriticalICDlist, mapping, request);
			
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strCriticalICDExp));
			}//end of catch(Exception exp)
			
						
		}

			
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
					log.debug("Inside the doBackward method of CriticalICDListAction");
					setLinks(request);
					MaintenanceManager MaintenanceManagerObject =this.getMaintenanceManagerObject();	
					TableData tableData = TTKCommon.getTableData(request);
					//modify the search data
					tableData.modifySearchData(strBackward);
					ArrayList alCriticalICDItem= MaintenanceManagerObject.getCriticalICDList(tableData.getSearchData());
					tableData.setData(alCriticalICDItem, strBackward);//set the table data
					request.getSession().setAttribute("tableData",tableData);
					//finally return to the grid screen
					return this.getForward(strCriticalICDlist, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
			return this.processExceptions(request, mapping, new TTKException(exp,strCriticalICDExp));
			}//end of catch(Exception exp)
		}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
		
		
	/**
	 * Returns the TariffManager  session object for invoking methods on it.
	 * @return TariffManager session object which can be used for method invokation 
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
			}//end of if(user == null)
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, "criticalicditem");
		}//end of catch(Exception exp) 
		return maintenanceManager;
	}//end getMaintenanceManagerObject()
	

}
