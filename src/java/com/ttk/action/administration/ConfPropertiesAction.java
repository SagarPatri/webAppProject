/**
* @ (#) ConfProperties.java Mar 21, 2006
* Project 		: TTK HealthCare Services
* File 			: ConfProperties.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created 	: Mar 21, 2006
*
* @author 		: Pradeep R
* Modified by 	: 
* Modified date : 
* Reason 		: 
*/

package com.ttk.action.administration;

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
import com.ttk.business.administration.TtkOfficeManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.TpaOfficeVO;
import com.ttk.dto.administration.TpaPropertiesVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for displaying the details of Configure Properties.
 */
public class ConfPropertiesAction extends TTKAction{
	
	private static Logger log = Logger.getLogger( ConfPropertiesAction.class );
	
	// Forward Path
	private static final String strClose="close";
	private static final String strConfproperties="confproperties";
	
	/**
	 * This method is used to navigate to  detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewProperties(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewProperties method of ConfPropertiesAction");
			setLinks(request);
			TtkOfficeManager  officeManager =this.getOfficeManager();
			TreeData treeData =(TreeData) request.getSession().getAttribute("treeData");
			//getting the refference of the tree form
			DynaActionForm frmTTKOffice=(DynaActionForm)request.getSession().getAttribute("frmTTKOffice");
			String strCaption="[ "+frmTTKOffice.getString("headOfficeName")+" ]";
			DynaActionForm frmConfProperties=(DynaActionForm)form;
			String strSelectedRoot=TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode=TTKCommon.checkNull(request.getParameter("selectednode"));
			String strHOSeqId=null;
			TpaPropertiesVO tpaPropertiesVO=null;
			if(strSelectedRoot.equals(""))
			{
				strHOSeqId=frmTTKOffice.getString("headOfficeSeqID");
				tpaPropertiesVO= officeManager.getConfigurationProperties(TTKCommon.getLong(strHOSeqId));
			}//end of if(strSelectedRoot.equals(""))
			else
			{
				//getting TpaOfficeVO from the tree then getting the officeseqid
				TpaOfficeVO tpaOfficeVO=((TpaOfficeVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode));
				Long lngOfficeSeqID=tpaOfficeVO.getOfficeSequenceID();
				strCaption="[ "+tpaOfficeVO.getOfficeName()+" ]";
				tpaPropertiesVO= officeManager.getConfigurationProperties(lngOfficeSeqID);
			}//end of else
			frmConfProperties = (DynaActionForm)FormUtils.setFormValues("frmConfProperties",tpaPropertiesVO, 
																					this, mapping, request);
			frmConfProperties.set("selectedRoot",strSelectedRoot);
			frmConfProperties.set("selectedNode",strSelectedNode);
			frmConfProperties.set("caption",strCaption);
			request.setAttribute("frmConfProperties",frmConfProperties);
			return this.getForward(strConfproperties, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strConfproperties));
		}//end of catch(Exception exp)
	}//end of doViewProperties(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doSave method of ConfPropertiesAction");
			setLinks(request);
			TtkOfficeManager  officeManager =this.getOfficeManager();
			DynaActionForm frmConfProperties=(DynaActionForm)form;
			TpaPropertiesVO tpaPropertiesVO=new TpaPropertiesVO();
			if(request.getParameter("paAllowedYN")==null)
			{
				frmConfProperties.set("paAllowedYN","N");
			}//end of if(request.getParameter("paAllowedYN")==null)
			if(request.getParameter("claimAllowedYN")==null)
			{
				frmConfProperties.set("claimAllowedYN","N");
			}//end of if(request.getParameter("claimAllowedYN")==null)
			if(request.getParameter("cardPrintAllowedYN")==null)
			{
				frmConfProperties.set("cardPrintAllowedYN","N");
			}//end of if(request.getParameter("cardPrintAllowedYN")==null)
			if(request.getParameter("enrolProcessYN")==null)
			{
				frmConfProperties.set("enrolProcessYN","N");
			}//end of iif(request.getParameter("enrolProcessYN")==null))
			tpaPropertiesVO= (TpaPropertiesVO)FormUtils.getFormValues(frmConfProperties,this,mapping,request);
			tpaPropertiesVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			long lngCount= officeManager.updateConfigurationProperties(tpaPropertiesVO);
			if(lngCount>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(lngCount>0)
			return this.getForward(strConfproperties, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strConfproperties));
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
			log.debug("Inside the doReset method of ConfPropertiesAction");
			setLinks(request);
			TtkOfficeManager  officeManager =this.getOfficeManager();
			TreeData treeData =(TreeData) request.getSession().getAttribute("treeData");
			//getting the refference of the tree form
			DynaActionForm frmTTKOffice=(DynaActionForm)request.getSession().getAttribute("frmTTKOffice");
			String strCaption="[ "+frmTTKOffice.getString("headOfficeName")+" ]";
			DynaActionForm frmConfProperties=(DynaActionForm)form;
			String strSelectedRoot=frmConfProperties.getString("selectedRoot");
			String strSelectedNode=frmConfProperties.getString("selectedNode");
			String strHOSeqId=null;
			TpaPropertiesVO tpaPropertiesVO=null;
			if(strSelectedRoot.equals(""))
			{
				strHOSeqId=frmTTKOffice.getString("headOfficeSeqID");
				tpaPropertiesVO= officeManager.getConfigurationProperties(TTKCommon.getLong(strHOSeqId));
			}//end of if(strSelectedRoot.equals(""))
			else
			{
				//getting TpaOfficeVO from the tree then getting the officeseqid
				TpaOfficeVO tpaOfficeVO=((TpaOfficeVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode));
				Long lngOfficeSeqID=tpaOfficeVO.getOfficeSequenceID();
				strCaption="[ "+tpaOfficeVO.getOfficeName()+" ]";
				tpaPropertiesVO= officeManager.getConfigurationProperties(lngOfficeSeqID);
			}//end of else
			frmConfProperties = (DynaActionForm)FormUtils.setFormValues("frmConfProperties",tpaPropertiesVO, this, mapping, request);
			frmConfProperties.set("selectedRoot",strSelectedRoot);
			frmConfProperties.set("selectedNode",strSelectedNode);
			frmConfProperties.set("caption",strCaption);
			request.setAttribute("frmConfProperties",frmConfProperties);
			return this.getForward(strConfproperties, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strConfproperties));
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												   HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doClose method of ConfPropertiesAction");
			setLinks(request);
			return (mapping.findForward(strClose)); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strConfproperties));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the TtkOfficeManager session object for invoking methods on it.
	 * @return TtkOfficeManager session object which can be used for method invokation 
	 * @exception throws TTKException  
	 */
	private TtkOfficeManager getOfficeManager() throws TTKException
	{
		TtkOfficeManager officeManager = null;
		try 
		{
			if(officeManager == null)
			{
				InitialContext ctx = new InitialContext();
				officeManager = (TtkOfficeManager) ctx.lookup("java:global/TTKServices/business.ejb3/TtkOfficeManagerBean!com.ttk.business.administration.TtkOfficeManager");
			}//end if
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, strConfproperties);
		}//end of catch
		return officeManager;
	}//end getOfficeManager()
	
}//end of ConfPropertiesAction class


