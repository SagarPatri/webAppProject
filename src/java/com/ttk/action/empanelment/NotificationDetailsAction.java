/**
 * @ (#) NotificationDetailsAction.java May 15, 2008
 * Project      : TTK HealthCare Services
 * File         : NotificationDetailsAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : May 15, 2008
 *
 * @author       :  Chandrasekaran J
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 * Modified by   :  
 */
package com.ttk.action.empanelment;

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
import com.ttk.action.tree.TreeData;
import com.ttk.business.empanelment.GroupRegistrationManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.GroupRegistrationVO;
import com.ttk.dto.empanelment.NotificationInfoVO;
import com.ttk.dto.empanelment.NotifyDetailVO;

import formdef.plugin.util.FormUtils;

public class NotificationDetailsAction extends TTKAction
{
	private static Logger log = Logger.getLogger( NotificationDetailsAction.class );
	
	//Forward Paths
	private static final String strNotificationDetails="notificationdetails";
	private static final String strSaveNotificationDetails="savenotificationdetails";
	
	//Exception Message Identifier
	private static final String strNotificationExp="notification";
	
	/**
	 * This method is used to navigate to detail screen to view selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doView method of NotificationDetailsAction");
			setLinks(request);
			GroupRegistrationManager getGroupRegnManagerObject=this.getGroupRegnManagerObject();
			DynaActionForm frmNotifiDetails = (DynaActionForm)form;
			StringBuffer sbfCaption=new StringBuffer();
			NotificationInfoVO notificationInfoVO=null;
			Long lGroupRegSeqID=null;
			GroupRegistrationVO groupRegistrationVO= new GroupRegistrationVO();
			GroupRegistrationVO groupRegistrationParentVO= new GroupRegistrationVO(); 
			ArrayList alAssocNotifyList = new ArrayList();
			ArrayList alUnAssocNotifyList = new ArrayList();
			notificationInfoVO = new NotificationInfoVO();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
			String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
            String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
            String strGroupName="";
            if(!strSelectedNode.equals(""))
            {
            	groupRegistrationVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
            	lGroupRegSeqID=groupRegistrationVO.getGroupRegSeqID();
            	groupRegistrationParentVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,null);
            	strGroupName=groupRegistrationVO.getGroupName();
            	strGroupName=strGroupName+"-"+groupRegistrationParentVO.getGroupID();
            }//end of if(!strSelectedNode.equals(""))
            else
            {
            	groupRegistrationVO=(GroupRegistrationVO)treeData.getRootData().get(iSelectedRoot);	
            	groupRegistrationParentVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,null);
            	lGroupRegSeqID=groupRegistrationParentVO.getGroupRegSeqID();
            	strGroupName=groupRegistrationParentVO.getGroupName();
            }//end of else
            sbfCaption.append("[").append(strGroupName).append("]");
			notificationInfoVO = getGroupRegnManagerObject.getNotificationList(lGroupRegSeqID);
			frmNotifiDetails = (DynaActionForm)FormUtils.setFormValues("frmNotifiDetails",
					notificationInfoVO, this, mapping, request);
			alAssocNotifyList = notificationInfoVO.getAssocNotifyList();
			alUnAssocNotifyList =	notificationInfoVO.getUnAssocNotifyList();
			//set the ArrayList data to new instance when listofroles fetch's null value
			if(alUnAssocNotifyList==null)
			{
				alUnAssocNotifyList=new ArrayList();
			}//end of if(alUnAssocNotifyList==null)
			if(alAssocNotifyList==null)
			{
				alAssocNotifyList=new ArrayList();
			}//end of if(alAssocNotifyList==null)
			frmNotifiDetails.set("associateNotifyList",alAssocNotifyList);
			frmNotifiDetails.set("unAssociateNotifyList",alUnAssocNotifyList);
			frmNotifiDetails.set("caption",sbfCaption.toString());
			frmNotifiDetails.set("groupname",strGroupName);
			frmNotifiDetails.set("selectednode",lGroupRegSeqID.toString());
			frmNotifiDetails.set("selectedroot",lGroupRegSeqID.toString());
			request.getSession().setAttribute("frmNotifiDetails",frmNotifiDetails);
			return this.getForward(strNotificationDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strNotificationExp));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
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
			log.debug("Inside the doSave method of NotificationDetailsAction");
			setLinks(request);
			GroupRegistrationManager getGroupRegnManagerObject=this.getGroupRegnManagerObject();
			NotificationInfoVO notificationInfoVO=null;
			Long lGroupRegSeqID=null;
			StringBuffer sbfCaption=new StringBuffer();
			//GroupRegistrationVO groupRegistrationVO= new GroupRegistrationVO();
			//GroupRegistrationVO groupRegistrationParentVO= new GroupRegistrationVO(); 
			DynaActionForm frmNotifiDetails=(DynaActionForm)form;
			ArrayList alAssocNotifyList = new ArrayList();
			ArrayList alUnAssocNotifyList = new ArrayList();
			notificationInfoVO=new NotificationInfoVO();
			String strGroupName="";
			//TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			//int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
			//String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
            String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
            strGroupName=frmNotifiDetails.getString("groupname");
            if(!strSelectedNode.equals(""))
            {
            	//groupRegistrationVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
            	lGroupRegSeqID=TTKCommon.getLong(frmNotifiDetails.getString("selectednode"));
            }// end of if(!strSelectedNode.equals(""))
            else
            {
            	//groupRegistrationVO=(GroupRegistrationVO)treeData.getRootData().get(iSelectedRoot);	
            	//groupRegistrationParentVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,null);
            	lGroupRegSeqID=TTKCommon.getLong(frmNotifiDetails.getString("selectedroot"));
            }//end of else
            sbfCaption.append("[").append(strGroupName).append("]");
			notificationInfoVO=(NotificationInfoVO)FormUtils.getFormValues(frmNotifiDetails, "frmNotifiDetails",this, mapping, request);
			//fetching roleID's from form to String array
			String strAssociatedNotifyList[] =(String[])frmNotifiDetails.get("selectedNotifyList");
			if(strAssociatedNotifyList != null)
			{
				for(int i=0; i < strAssociatedNotifyList.length; i++)
				{
					frmNotifiDetails.set("selectedNotifyList",strAssociatedNotifyList);
				}//end of for(int i=0; i < strAssociatedNotifyList.length; i++)
			}//end of if(strAssociatedRoles != null)
			notificationInfoVO.setGroupRegSeqID(lGroupRegSeqID);
			notificationInfoVO.setAssocNotifyList(populateRoleObjects(strAssociatedNotifyList));
			// User ID from session
			notificationInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = getGroupRegnManagerObject.saveNotificationInfo(notificationInfoVO);
			if(iResult!=0) {
				request.setAttribute("updated","message.saved");
				notificationInfoVO = getGroupRegnManagerObject.getNotificationList(lGroupRegSeqID);
				frmNotifiDetails = (DynaActionForm)FormUtils.setFormValues("frmNotifiDetails",
						notificationInfoVO, this, mapping, request);
				
				alAssocNotifyList = notificationInfoVO.getAssocNotifyList();
				alUnAssocNotifyList =	notificationInfoVO.getUnAssocNotifyList();
				//set the ArrayList data to new instance when listofroles fetch's null value
				if(alUnAssocNotifyList==null)
				{
					alUnAssocNotifyList=new ArrayList();
				}//end of if(alUnAssocNotifyList==null)
				if(alAssocNotifyList==null)
				{
					alAssocNotifyList=new ArrayList();
				}//end of if(alAssocNotifyList==null)
			}//end of if(iResult!=0)
			frmNotifiDetails.set("caption",sbfCaption.toString());
			frmNotifiDetails.set("associateNotifyList",alAssocNotifyList);
			frmNotifiDetails.set("unAssociateNotifyList",alUnAssocNotifyList);
			frmNotifiDetails.set("selectedroot",lGroupRegSeqID.toString());
			frmNotifiDetails.set("selectednode",lGroupRegSeqID.toString());
			frmNotifiDetails.set("groupname",strGroupName);
			request.getSession().setAttribute("frmNotifiDetails",frmNotifiDetails);
			return this.getForward(strSaveNotificationDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strNotificationExp));
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
			log.debug("Inside the doReset method of WorkflowAction");
			setLinks(request);
			GroupRegistrationManager getGroupRegnManagerObject=this.getGroupRegnManagerObject();
			NotificationInfoVO notificationInfoVO=null;
			Long lGroupRegSeqID=null;
			StringBuffer sbfCaption=new StringBuffer();
			//GroupRegistrationVO groupRegistrationVO= new GroupRegistrationVO();
			//GroupRegistrationVO groupRegistrationParentVO= new GroupRegistrationVO(); 
			ArrayList alAssocNotifyList = new ArrayList();
			ArrayList alUnAssocNotifyList = new ArrayList();
			notificationInfoVO = new NotificationInfoVO();
			DynaActionForm frmNotifiDetails = (DynaActionForm)form;
			//TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			//int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
			//String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
            String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
			String strGroupName="";
			strGroupName=frmNotifiDetails.getString("groupname");
            if(!strSelectedNode.equals(""))
            {
            	//groupRegistrationVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
            	lGroupRegSeqID=TTKCommon.getLong(frmNotifiDetails.getString("selectednode"));
            }//end of if(!strSelectedNode.equals(""))
            else
            {
            	//groupRegistrationVO=(GroupRegistrationVO)treeData.getRootData().get(iSelectedRoot);	
            	//groupRegistrationParentVO=(GroupRegistrationVO)treeData.getSelectedObject(strSelectedRoot,null);
            	lGroupRegSeqID=TTKCommon.getLong(frmNotifiDetails.getString("selectedroot"));
            }//end of else
            sbfCaption.append("[").append(strGroupName).append("]");
			notificationInfoVO = getGroupRegnManagerObject.getNotificationList(lGroupRegSeqID);
			frmNotifiDetails = (DynaActionForm)FormUtils.setFormValues("frmNotifiDetails",
					notificationInfoVO, this, mapping, request);
			alAssocNotifyList = notificationInfoVO.getAssocNotifyList();
			alUnAssocNotifyList =	notificationInfoVO.getUnAssocNotifyList();
			//set the ArrayList data to new instance when listofroles fetch's null value
			if(alUnAssocNotifyList==null)
			{	
				alUnAssocNotifyList=new ArrayList();
			}//end of if(alUnAssocNotifyList==null)	
			if(alAssocNotifyList==null)
			{
				alAssocNotifyList=new ArrayList();
			}//end of if(alAssocNotifyList==null)
			frmNotifiDetails.set("associateNotifyList",alAssocNotifyList);
			frmNotifiDetails.set("unAssociateNotifyList",alUnAssocNotifyList);
			frmNotifiDetails.set("caption",sbfCaption.toString());
			frmNotifiDetails.set("selectedroot",lGroupRegSeqID.toString());
			frmNotifiDetails.set("selectednode",lGroupRegSeqID.toString());
			frmNotifiDetails.set("groupname",strGroupName);
			request.getSession().setAttribute("frmNotifiDetails",frmNotifiDetails);
			return this.getForward(strNotificationDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strNotificationExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	/**
	 * Returns the ArrayList roleVO object for invoking methods on it.
	 * @return ArrayList roleVO  object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ArrayList populateRoleObjects(String strAssociatedNotifyList[]) throws TTKException
	{
		ArrayList<Object> alAssocNotifyList = new ArrayList<Object>();
		try
		{
			for(int i=0; i<strAssociatedNotifyList.length; i++)
			{
				NotifyDetailVO notifyDetailVO = new NotifyDetailVO();
				notifyDetailVO.setMsgID(strAssociatedNotifyList[i]);
				alAssocNotifyList.add(notifyDetailVO);
			}//end of for 
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strNotificationExp);
		}//end of catch
		return (alAssocNotifyList);
	}//end of populateRoleObjects(String strAssociatedRoles[])
	
	
	/**
	 * Returns the UserManager session object for invoking methods on it.
	 * @return UserManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private GroupRegistrationManager getGroupRegnManagerObject() throws TTKException
	{
		GroupRegistrationManager groupRegnManager = null;
		try
		{
			if(groupRegnManager == null)
			{
				InitialContext ctx = new InitialContext();
				groupRegnManager = (GroupRegistrationManager) ctx.lookup("java:global/TTKServices/business.ejb3/GroupRegistrationManagerBean!com.ttk.business.empanelment.GroupRegistrationManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "groupdetail");
		}//end of catch
		return groupRegnManager;
	}//end getGroupRegistrationManagerObject()
}//end of NotificationDetailsAction()
