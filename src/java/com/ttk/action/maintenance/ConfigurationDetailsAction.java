/** @ (#) ConfigurationDetailsAction.java Jul 31 2008
 * Project     		: TTK Healthcare Services
 * File        		: ConfigurationDetailsAction.java
 * Author      		: Sendhil Kumar V	
 * Company     		: Span Systems Corporation
 * Date Created		: Jul 31 2008
 *
 * @author 		 	: Sendhil Kumar V
 * Modified by  	:
 * Modified date 	:
 * Reason        	:
 *
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
import com.ttk.dto.maintenance.CustomizeConfigVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class ConfigurationDetailsAction extends TTKAction
{
	//for setting modes
	private static Logger log = Logger.getLogger( ConfigurationDetailsAction.class );

	//for forwarding
	private static final String strConfigList="configlist";
	private static final String strConfigDetails="configdetails";
	private static final String strNotifyDetails="notifydetails";

	//  Exception Message Identifier
	private static final String strConfigExp="configinfo";
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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside doDefault method of ConfigurationDetailsAction");
			DynaActionForm frmConfigDetails= (DynaActionForm) form;
			DynaActionForm frmNotifyDetails= (DynaActionForm) request.getSession().getAttribute("frmNotifyDetails");

			TableData tableDataConfigDetails = null;
			ArrayList alConfigDetails=null;
			String strCaption = frmNotifyDetails.getString("msgName");
			StringBuffer sbfCaption= new StringBuffer();

			//Get the session bean from the beand pool for each thread being excuted.
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataConfigDetails")!=null)
			{
				tableDataConfigDetails=(TableData)(request.getSession()).getAttribute("tableDataConfigDetails");
			}//end of if(request.getSession().getAttribute("tableDataConfigDetails")!=null)
			else
			{
				tableDataConfigDetails=new TableData();
			}//end of else
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataConfigDetails.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataConfigDetails.modifySearchData("sort");//modify the search data
				strDefaultBranchID = frmConfigDetails.getString("officeSeqID");
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataConfigDetails.createTableInfo("ConfigurationTable",null);
				tableDataConfigDetails.setSearchData(this.populateSearchCriteria(frmNotifyDetails,request));
				tableDataConfigDetails.modifySearchData("search");
			}// end of else
			alConfigDetails=maintenanceManagerObject.getCustomConfigList(tableDataConfigDetails.getSearchData());
			tableDataConfigDetails.setData(alConfigDetails);
			
			request.getSession().setAttribute("tableDataConfigDetails",tableDataConfigDetails);
			sbfCaption.append(" [").append(strCaption).append("]");
			frmConfigDetails.set("caption",sbfCaption.toString());
			frmConfigDetails.set("officeSeqID",strDefaultBranchID);
			frmConfigDetails.set("msgID",(String)frmNotifyDetails.get("msgID"));
			request.getSession().setAttribute("frmConfigDetails",frmConfigDetails);
			return this.getForward(strConfigList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strConfigExp));
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
	public ActionForward doViewConfigDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside doViewConfigDetails method of ConfigurationDetailsAction");
			DynaActionForm frmConfigDetails=(DynaActionForm)form;
			CustomizeConfigVO customizeConfigVO=null;
			String strCaption = frmConfigDetails.getString("caption");
			//Get the session bean from the beand pool for each thread being excuted.
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();
			
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				customizeConfigVO=(CustomizeConfigVO)((TableData)request.getSession().getAttribute("tableDataConfigDetails")).getData().get(Integer.parseInt(request.getParameter("rownum")));
				log.info("customizeConfigVO.getCustConfigSeqID() "+ customizeConfigVO.getCustConfigSeqID());
				customizeConfigVO=maintenanceManagerObject.getCustomConfigInfo(customizeConfigVO.getCustConfigSeqID());
				frmConfigDetails = (DynaActionForm)FormUtils.setFormValues("frmConfigDetails",customizeConfigVO,this,mapping,request);
			}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				frmConfigDetails.initialize(mapping);
			}// end of else
			frmConfigDetails.set("caption",strCaption);
			request.getSession().setAttribute("frmConfigDetails",frmConfigDetails);
			return this.getForward(strConfigList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strConfigExp));
		}//end of catch(Exception exp)
	}//end of doViewConfigDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside doSave method of ConfigurationDetailsAction");
			DynaActionForm frmConfigDetails=(DynaActionForm)form;
			DynaActionForm frmNotifyDetails= (DynaActionForm) request.getSession().getAttribute("frmNotifyDetails");
			CustomizeConfigVO customizeConfigVO=null;
			ArrayList alConfigDetails=null;
			TableData tableDataConfigDetails = null;

			String strMsgID = frmNotifyDetails.getString("msgID");
			String strCaption = frmConfigDetails.getString("caption");
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			//Get the session bean from the beand pool for each thread being excuted.
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();

			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataConfigDetails")!=null) 
			{
				tableDataConfigDetails=(TableData)(request.getSession()).getAttribute("tableDataConfigDetails");
			}//end of if(request.getSession().getAttribute("tableDataConfigDetails")!=null) 
			else
			{
				tableDataConfigDetails=new TableData();
			}//end of else
			customizeConfigVO=(CustomizeConfigVO)FormUtils.getFormValues(frmConfigDetails, "frmConfigDetails",this,mapping, request);
			customizeConfigVO.setMsgID(strMsgID);
			customizeConfigVO.setAddedBy(TTKCommon.getUserSeqId(request));
			int inUpdate=maintenanceManagerObject.saveCustomConfigInfo(customizeConfigVO);
			if(inUpdate>0) 
			{
				request.setAttribute("updated","message.saved");
				frmConfigDetails.initialize(mapping);
				alConfigDetails=maintenanceManagerObject.getCustomConfigList(tableDataConfigDetails.getSearchData());
				tableDataConfigDetails.setData(alConfigDetails);
			}// end of if(iUpdate>0)
			frmConfigDetails.set("caption",strCaption);
			frmConfigDetails.set("officeSeqID",strDefaultBranchID);
			request.getSession().setAttribute("tableDataConfigDetails",tableDataConfigDetails);
			request.getSession().setAttribute("frmConfigDetails",frmConfigDetails);
			return this.getForward(strConfigDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strConfigExp));
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
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doReset method of ConfigurationDetailsAction");
			setLinks(request);
			DynaActionForm frmConfigDetails=(DynaActionForm)form;
			CustomizeConfigVO customizeConfigVO=null;
			String strCaption = frmConfigDetails.getString("caption");
			String strDefaultBranchID = "";
			//Get the session bean from the beand pool for each thread being excuted.
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();

			if(frmConfigDetails.get("custConfigSeqID")!= null && (!frmConfigDetails.get("custConfigSeqID").equals("")))
			{
				customizeConfigVO=maintenanceManagerObject.getCustomConfigInfo(TTKCommon.getLong(frmConfigDetails.getString("custConfigSeqID")));
				frmConfigDetails = (DynaActionForm)FormUtils.setFormValues("frmConfigDetails",customizeConfigVO,this,mapping,request);
				strDefaultBranchID = frmConfigDetails.getString("officeSeqID");
			}// end of if(frmConfigDetails.get("custConfigSeqID")!= null && (!frmConfigDetails.get("custConfigSeqID").equals("")))
			else if(frmConfigDetails.get("custConfigSeqID")== null || frmConfigDetails.get("custConfigSeqID").equals(""))
			{
				frmConfigDetails.initialize(mapping);
				strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			}// end of else if(frmConfigDetails.get("custConfigSeqID")== null || frmConfigDetails.get("custConfigSeqID").equals(""))
			frmConfigDetails.set("caption",strCaption);
			frmConfigDetails.set("officeSeqID",strDefaultBranchID);
			request.getSession().setAttribute("frmConfigDetails",frmConfigDetails);
			return this.getForward(strConfigList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strConfigExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside the doClose method of ConfigurationDetailsAction");
			setLinks(request);
			return this.getForward(strNotifyDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strConfigExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmProductList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmNotifyDetails,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add((String)frmNotifyDetails.get("msgID"));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)

	/**
	 * Returns the MaintenanceManager  session object for invoking methods on it.
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
			}//end of if(maintenanceManager == null)
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, "configinfo");
		}//end of catch(Exception exp) 
		return maintenanceManager;
	}//end of getMaintenanceManagerObject()
}//end of ConfigurationDetailsAction class
