/**
 * @ (#) NotificationListAction.java
 * Project       : TTK HealthCare Services
 * File          : NotificationListAction.java
 * Author        : Sendhil Kumar V
 * Company       : Span Systems Corporation
 * Date Created  : 28th Jul 2008
 *
 * @author       : Sendhil Kumar V
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
import com.ttk.dto.empanelment.NotifyDetailVO;

import formdef.plugin.util.FormUtils;

public class NotificationListAction extends TTKAction
{
	//for setting modes
	private static Logger log = Logger.getLogger( NotificationListAction.class );

	//forwards links
	private static final String strNotificationList="notificationlist";
	private static final String strNotifyDetails="notifydetails";

	//Exception Message Identifier
	private static final String strNotifyExp="notificationlist";

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
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doSearch method of NotificationListAction");
			setLinks(request);
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);

			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{    
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return this.getForward(strNotificationList, mapping, request);	
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
				tableData.createTableInfo("NotificationTable",null);
				tableData.modifySearchData("search");
			}//end of else
			//fetch the data from the data access layer and set the data to table object
			ArrayList alNotifyList= maintenanceManagerObject.getNotifyList(tableData.getSearchData());
			tableData.setData(alNotifyList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strNotificationList, mapping, request);	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strNotifyExp));
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
	public ActionForward doViewNotification(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try{
			log.info("Inside the doViewNotification method of NotificationListAction");
			setLinks(request);
			NotifyDetailVO notifyDetailVO = null;
			String strMsgID="";
			String strConfigParam="";
			String strCustConfigAllowed = "";
			String strSendEmailYN="";
			String strSendSmsYN = "";
			String strShowMultiEmail = "";
			DynaActionForm frmNotifyDetails = (DynaActionForm)form;
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();
			StringBuffer sbfCaption= new StringBuffer();
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				notifyDetailVO = (NotifyDetailVO)((TableData)request.getSession().getAttribute("tableData")).getData().get(Integer.parseInt(TTKCommon.checkNull(request.getParameter("rownum"))));
				strMsgID=notifyDetailVO.getMsgID();
				sbfCaption.append(" Edit [").append(notifyDetailVO.getMsgName()).append("]");
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals(""))) 
			notifyDetailVO=maintenanceManagerObject.getNotificationInfo(strMsgID);
			frmNotifyDetails = (DynaActionForm)FormUtils.setFormValues("frmNotifyDetails",notifyDetailVO,this,mapping,request);
			strConfigParam=notifyDetailVO.getConfigAllowedYN();
			strCustConfigAllowed = notifyDetailVO.getCustConfigAllowedYN();
			strSendEmailYN=notifyDetailVO.getSendEmailYN();
			strSendSmsYN=notifyDetailVO.getSendSmsYN();
			strShowMultiEmail=notifyDetailVO.getShowMultiLevelYN();
			frmNotifyDetails.set("showMultiLevelEmail",strShowMultiEmail);
			frmNotifyDetails.set("sendEmail",strSendEmailYN);
			frmNotifyDetails.set("sendSms",strSendSmsYN);
			frmNotifyDetails.set("caption",sbfCaption.toString());
			frmNotifyDetails.set("configParam",strConfigParam);
			frmNotifyDetails.set("custConfigAllowed",strCustConfigAllowed);
			request.getSession().setAttribute("frmNotifyDetails",frmNotifyDetails);
			return this.getForward(strNotifyDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strNotifyExp));
		}//end of catch(Exception exp)
	}//end of doViewNotification(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to update the record.
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
			log.debug("Inside the doSave method of NotificationListAction");
			setLinks(request);
			NotifyDetailVO notifyDetailVO = null;
			//Get the session bean from the beand pool for each thread being excuted.
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();
			StringBuffer sbfCaption= new StringBuffer();
			DynaActionForm frmNotifyDetails = (DynaActionForm)form;
			notifyDetailVO=new NotifyDetailVO();
			notifyDetailVO = (NotifyDetailVO)FormUtils.getFormValues(frmNotifyDetails, this, mapping, request);
			notifyDetailVO.setAddedBy(TTKCommon.getUserSeqId(request));
			String[] applicableInsCompany = frmNotifyDetails.getString("applicableInsCompany").split("@");
			if(applicableInsCompany.length==1){
				notifyDetailVO.setApplicableInsCheckCode(applicableInsCompany[0]);
				notifyDetailVO.setApplicableInsCheckName("");
			}else{
			if(applicableInsCompany.length==0){
				notifyDetailVO.setApplicableInsCheckCode("");
				notifyDetailVO.setApplicableInsCheckName("");
			}
			else{
			notifyDetailVO.setApplicableInsCheckCode(applicableInsCompany[0]);
			notifyDetailVO.setApplicableInsCheckName(applicableInsCompany[1]);
			}
			}
			int iUpdate = maintenanceManagerObject.saveNotificationInfo(notifyDetailVO);
			if(iUpdate > 0)
			{
				if(!frmNotifyDetails.get("msgID").equals(""))
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(!frmNotifyDetails.get("msgID").equals(""))
			}//end of if(iUpdate > 0) 
			sbfCaption.append(" Edit [").append(frmNotifyDetails.get("msgName")).append("]");
			frmNotifyDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmNotifyDetails",frmNotifyDetails);
			return this.getForward(strNotifyDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strNotifyExp));
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
			log.debug("Inside doReset of NotificationListAction");
			setLinks(request);
			String strConfigParam="";
			String strCustConfigAllowed="";
			String strSendEmailYN="";
			String strSendSmsYN = "";
			String strShowMultiEmail = "";
			DynaActionForm frmNotifyDetails =(DynaActionForm)form;
			NotifyDetailVO notifyDetailVO = new NotifyDetailVO();
			StringBuffer sbfCaption= new StringBuffer();
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();
			if(frmNotifyDetails.get("msgID")!=null && !frmNotifyDetails.get("msgID").equals(""))// on clicking the edit link
			{
				notifyDetailVO=maintenanceManagerObject.getNotificationInfo(frmNotifyDetails.getString("msgID"));
			}//if(frmNotifyDetails.get("msgID")!=null && !frmNotifyDetails.get("msgID").equals(""))
			frmNotifyDetails= (DynaActionForm)FormUtils.setFormValues("frmNotifyDetails", notifyDetailVO,this,mapping,request);
			sbfCaption.append(" Edit [").append(notifyDetailVO.getMsgName()).append("]");
			strConfigParam=notifyDetailVO.getConfigAllowedYN();
			strCustConfigAllowed = notifyDetailVO.getCustConfigAllowedYN();
			strSendEmailYN=notifyDetailVO.getSendEmailYN();
			strSendSmsYN=notifyDetailVO.getSendSmsYN();
			strShowMultiEmail=notifyDetailVO.getShowMultiLevelYN();
			frmNotifyDetails.set("showMultiLevelEmail",strShowMultiEmail);
			frmNotifyDetails.set("sendEmail",strSendEmailYN);
			frmNotifyDetails.set("sendSms",strSendSmsYN);
			frmNotifyDetails.set("configParam",strConfigParam);
			frmNotifyDetails.set("custConfigAllowed",strCustConfigAllowed);
			frmNotifyDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmNotifyDetails",frmNotifyDetails);
			return this.getForward(strNotifyDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strNotifyExp));
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
			log.debug("Inside the doClose method of NotificationListAction");
			setLinks(request);
			//Get the session bean from the beand pool for each thread being excuted.
			MaintenanceManager maintenanceManagerObject =this.getMaintenanceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				ArrayList alNotifyList= maintenanceManagerObject.getNotifyList(tableData.getSearchData());
				tableData.setData(alNotifyList);
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			return this.getForward(strNotificationList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strNotifyExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			throw new TTKException(exp, "notificationlist");
		}//end of catch(Exception exp) 
		return maintenanceManager;
	}//end of getMaintenanceManagerObject()
}//end of NotificationListAction class
