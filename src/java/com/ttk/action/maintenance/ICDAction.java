/**
 * @ (#) ICDAction.java May 14, 2008
 * Project      : TTK HealthCare Services
 * File         : ICDAction.java
 * Author       : UNNI V MANA
 * Company      : Span Systems Corporation
 * Date Created : May 14, 2008
 *
 * @author       : UNNI V MANA
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.maintenance;



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
import com.ttk.dto.enrollment.PEDVO;
import com.ttk.dto.maintenance.ICDCodeVO;

import formdef.plugin.util.FormUtils;

public class ICDAction extends TTKAction{
	private static Logger log = Logger.getLogger( ICDAction.class );
	private static final String straddicdcodedetails="addicdcodedetails";
	private static final String stricdcode="saveicdcode";
	private static final String strEditICDscreen="editicdscreen";
	private static final String strMasterList="masterlist";
	private static final String strScreen="screen";
	private static final String strMasterIcdCode="masterIcdCode";
	private static final String strPedCode="PEDCODE";
	private static final String strMasterPedCode="MASTERPEDCODE";
	private static final String strOp="op";
	private static final String strAdd="add";
	private static final String strEdit="edit";
	
	
	/**
     * This method initialize an empty screen for entering ICD Code details 
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
    			  log.debug("Inside ICDAction doAdd");
    			  setLinks(request);
    			  if(strMasterList.equalsIgnoreCase(request.getParameter(strScreen)))
    			  {
    				  saveToken(request);
    				  TableData icdListData = null;
    				  if((request.getSession()).getAttribute("icdListData") != null)
    	 	          icdListData    	   = (TableData)(request.getSession()).getAttribute("icdListData");
    				  PEDVO pedVO=(PEDVO)icdListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
    				  ICDCodeVO icdCodeVO = this.getMaintenanceManagerObject().selectICD(pedVO.getPEDCodeID());
    				  request.getSession().setAttribute(strMasterPedCode, pedVO.getPEDCodeID());
    				  DynaActionForm frmAddICD= (DynaActionForm)request.getSession().getAttribute("frmAddICDCode");
    				  frmAddICD.set(strMasterIcdCode,  icdCodeVO.getIcdCode()); // this is for displaying icd code mastericd screen
    				  frmAddICD.set("frmChanged", "changed");
    			  }//if(strMasterList.equalsIgnoreCase(request.getParameter(strScreen)))
    			  else
    			  {
    				  request.getSession().setAttribute(strOp,"add"); // to identify the operation
    				  saveToken(request);
    				  ((DynaActionForm)form).initialize(mapping);
    				  request.getSession().setAttribute("frmAddICDCode",(DynaActionForm)form);
    			  }//end of else
    			  return this.getForward(straddicdcodedetails, mapping, request);
    		}catch(TTKException expTTK){
    			return this.processExceptions(request, mapping, expTTK);
    		}catch(Exception exp)
    		{
    			return this.processExceptions(request, mapping, new TTKException(exp,"ICDAction"));
    		}
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method initialize an empty screen for entering ICD Code details 
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
    			  log.debug("Inside ICDAction doEdit");
    			  setLinks(request);
    			  saveToken(request);
    			  ((DynaActionForm)form).initialize(mapping);
    			  TableData icdListData = null;
    			  if((request.getSession()).getAttribute("icdListData") != null)
 	              icdListData = (TableData)(request.getSession()).getAttribute("icdListData");
    			  PEDVO pedVO=(PEDVO)icdListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
    			  ICDCodeVO icdCodeVO = this.getMaintenanceManagerObject().selectICD(pedVO.getPEDCodeID());
      			  DynaActionForm frmAddICD = (DynaActionForm)form;
      			  frmAddICD =(DynaActionForm)FormUtils.setFormValues("frmAddICDCode", icdCodeVO,this, mapping, request);
      			  if(!strMasterList.equalsIgnoreCase(request.getParameter(strScreen))){
    				  request.getSession().setAttribute(strPedCode, icdCodeVO.getPedCode());
    			  }//if(!strMasterList.equalsIgnoreCase(request.getParameter(strScreen)))
      			  request.getSession().setAttribute("frmAddICDCode", frmAddICD);
    			  return this.getForward(strEditICDscreen, mapping, request);
    		}catch(TTKException expTTK){
    			return this.processExceptions(request, mapping, expTTK);
    		}catch(Exception exp)
    		{
    			return this.processExceptions(request, mapping, new TTKException(exp,"ICDAction"));
    		}
    }//end of doEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * This method used to save ICD Code details
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
    		try{
    			  log.debug("Inside ICDAction doSave");
    			  setLinks(request);
    			  DynaActionForm frmAddICD = (DynaActionForm) form;
    			  ICDCodeVO icdCodeVO=(ICDCodeVO)FormUtils.getFormValues(frmAddICD,this,mapping,request);
    			  MaintenanceManager maintenanceObject=this.getMaintenanceManagerObject();
    			  Long pedCode=(request.getSession().getAttribute(strPedCode)==null)?null:(Long)request.getSession().getAttribute(strPedCode);
    			  Long masterPedCode=(request.getSession().getAttribute(strMasterPedCode)==null)?null:(Long)request.getSession().getAttribute(strMasterPedCode);
    			  icdCodeVO.setMasterPedCode(masterPedCode);
    			  icdCodeVO.setPedCode(pedCode);
    			  icdCodeVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    			  long lResult = 0;
    			  String strOperation = (String) request.getSession().getAttribute(strOp);
    				  if(strAdd.equals(strOperation))
    				  {
    					  /*if(isTokenValid(request,true))
    					  {*/
    						  lResult = maintenanceObject.addUpdateICDDetails(icdCodeVO);
    						  ICDCodeVO icdCodeVOOld = this.getMaintenanceManagerObject().selectICD(lResult);
    						  request.getSession().setAttribute(strPedCode,icdCodeVOOld.getPedCode());
    						  request.getSession().setAttribute(strMasterPedCode,icdCodeVOOld.getMasterPedCode());
    						  frmAddICD =(DynaActionForm)FormUtils.setFormValues("frmAddICDCode", icdCodeVOOld,this, mapping, request);
    						  request.setAttribute("updated","message.addedSuccessfully");
    						  request.getSession().setAttribute(strOp,"edit");
    					 /* } //if(isTokenValid(request,true))
    					  else 
    					  {	  
    						  request.setAttribute("updated","duplicate.submission");
    					  }//end of else */	  
    				  }// if(strAdd.equals(strOperation))
    				  else if(strEdit.equals(strOperation) || strOperation==null)
    				  {
    					  lResult = maintenanceObject.addUpdateICDDetails(icdCodeVO);
    				      request.setAttribute("updated","message.savedSuccessfully");
    				  }//if(strEdit.equals(strOperation) || strOperation==null)
    				  frmAddICD.set("frmChanged", "");
    			  return this.getForward(stricdcode, mapping, request);
    		}catch(TTKException expTTK){
    			return this.processExceptions(request, mapping, expTTK);
    		}catch(Exception exp)
    		{
    			return this.processExceptions(request, mapping, new TTKException(exp,"ICDAction"));
    		}
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
    		log.debug("Inside ICDAction doClose");
    		setLinks(request);
         	return this.getForward(straddicdcodedetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"ICDAction"));
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
    		log.info("Inside ICDAction doReset");
    		setLinks(request);
    		MaintenanceManager maintenanceObject=this.getMaintenanceManagerObject();
    		ICDCodeVO icdCodeVO = new ICDCodeVO();
    		DynaActionForm frmICDCodeAdd=(DynaActionForm)form;
    		Long pedCode=(request.getSession().getAttribute(strPedCode)==null)?null:(Long)request.getSession().getAttribute(strPedCode);
    		if(pedCode !=null){
    			frmICDCodeAdd.initialize(mapping);
    			icdCodeVO=maintenanceObject.selectICD(pedCode);
    			frmICDCodeAdd = (DynaActionForm)FormUtils.setFormValues("frmAddICDCode",icdCodeVO, this, mapping, request);
    			request.getSession().setAttribute("frmAddICDCode", frmICDCodeAdd);
    		}// if(!frmICDCodeAdd.get("pedCode").equals(""))
    		else
    		{
    		((DynaActionForm)form).initialize(mapping);
    		} //end of else 
    		return this.getForward(straddicdcodedetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,"ICDAction"));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)    
    
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
