/**
 * @ (#) AssignAction.java Oct 07, 2006
 * Project      : TTK HealthCare Services
 * File         : AssignAction.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : Oct 07, 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.customercare;

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
import com.ttk.business.customercare.CallCenterManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.customercare.CallCenterAssignmentVO;
import com.ttk.dto.customercare.CallCenterVO;
import formdef.plugin.util.FormUtils;

/**
 * This class is used for assigning the user for the calls.
 *
 */

public class AssignAction extends TTKAction
{
    private static Logger log = Logger.getLogger(AssignAction.class);

    //Action mapping forwards.
    private static final String strCallCenterAssignTo="callcenterassignto";
    private static final String strCallCenterSearch="callcentersearch";

	//Exception Message Identifier
    private static final String strUser="user";
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
    public ActionForward doAssignTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    											HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside AssignAction doAssignTo");
    		CallCenterVO callCenterVO=null;
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		CallCenterAssignmentVO callCenterAssignmentVO=null;
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		DynaActionForm frmAssignTo= (DynaActionForm) form;
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			callCenterVO = (CallCenterVO)callcentertableData.getRowInfo(Integer.parseInt(
    																request.getParameter("rownum")));
    			if(callCenterVO.getClarifySeqID() != null)
    			{
    				callCenterAssignmentVO=callCenterManagerObject.getAssignTo(callCenterVO.getClarifySeqID(),
    																				TTKCommon.getUserSeqId(request));
    			}//end of if(callCenterVO.getClarifySeqID() != null)
    			else
    			{
    				frmAssignTo.initialize(mapping);
    				callCenterAssignmentVO=new CallCenterAssignmentVO();
    				callCenterAssignmentVO.setLogNumber(callCenterVO.getLogNumber());
    				callCenterAssignmentVO.setLogSeqID(callCenterVO.getLogSeqID());
    			}//end of else
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		frmAssignTo=(DynaActionForm)FormUtils.setFormValues("frmAssignTo", callCenterAssignmentVO, this,
    																				mapping, request);
    		request.setAttribute("frmAssignTo",frmAssignTo);
    		return mapping.findForward(strCallCenterAssignTo);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)
    }//end of doAssignTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside AssignAction doSave");
    		CallCenterAssignmentVO callCenterAssignmentVO=null;
    		DynaActionForm frmAssignTo= (DynaActionForm) form;
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		callCenterAssignmentVO=(CallCenterAssignmentVO)FormUtils.getFormValues(frmAssignTo,this, mapping, request);
    		callCenterAssignmentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
    		Long lngResult = callCenterManagerObject.assignDepartment(callCenterAssignmentVO);
    		if(lngResult >0)
    		{
    			if(callCenterAssignmentVO.getClarifySeqID()!=null)
    			{
    				request.setAttribute("updated","message.savedSuccessfully");
    			}//end of if(callCenterVO.getClarifySeqID()!=null)
    			else
    			{
    				request.setAttribute("updated","message.addedSuccessfully");
    				callCenterAssignmentVO = new CallCenterAssignmentVO();
    				callCenterAssignmentVO.setLogNumber((String)frmAssignTo.get("logNumber"));
    				callCenterAssignmentVO.setLogSeqID(TTKCommon.getLong((String)frmAssignTo.get("logSeqID")));
    				frmAssignTo.initialize(mapping);
    			}//end of else
    		}//end of if(lngResult >0)
    		callCenterAssignmentVO=callCenterManagerObject.getAssignTo(lngResult,TTKCommon.getUserSeqId(request));
    		frmAssignTo=(DynaActionForm)FormUtils.setFormValues("frmAssignTo", callCenterAssignmentVO, this,
    																mapping, request);
    		request.setAttribute("frmAssignTo",frmAssignTo);
    		return mapping.findForward(strCallCenterAssignTo);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
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
    		setLinks(request);
    		log.debug("Inside AssignAction doReset");
    		DynaActionForm frmAssignTo= (DynaActionForm) form;
    		CallCenterAssignmentVO callCenterAssignmentVO=null;
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		if(frmAssignTo.get("clarifySeqID")!=null && !frmAssignTo.get("clarifySeqID").equals(""))
    		{
    			callCenterAssignmentVO=callCenterManagerObject.getAssignTo(TTKCommon.getLong((String)
    											frmAssignTo.get("clarifySeqID")),TTKCommon.getUserSeqId(request));
    		}//end of if(frmAssignTo.get("clarifySeqID")!=null && !frmAssignTo.get("clarifySeqID").equals(""))
    		else
    		{
    			callCenterAssignmentVO = new CallCenterAssignmentVO();
    			callCenterAssignmentVO.setLogNumber((String)frmAssignTo.get("logNumber"));
    			callCenterAssignmentVO.setLogSeqID(TTKCommon.getLong((String)frmAssignTo.get("logSeqID")));
    			frmAssignTo.initialize(mapping);
    		}//end of else
    		frmAssignTo= (DynaActionForm)FormUtils.setFormValues("frmAssignTo", callCenterAssignmentVO, this,
    																mapping, request);
    		request.setAttribute("frmAssignTo",frmAssignTo);
    		return mapping.findForward(strCallCenterAssignTo);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    															HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside AssignAction doClose");
    		TableData  callcentertableData =(TableData)request.getSession().getAttribute("callcentertableData");
    		CallCenterManager callCenterManagerObject=this.getCallCenterManagerObject();
    		if(callcentertableData.getSearchData().size()>1)
    		{
    			ArrayList alCallSearch = callCenterManagerObject.getCallList(callcentertableData.getSearchData());
    			callcentertableData.setData(alCallSearch, "search");
    			//set the table data object to session
    			request.getSession().setAttribute("callcentertableData",callcentertableData);
    		}//end of if(callcentertableData.getSearchData().size()>1)
    		return this.getForward(strCallCenterSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * Returns the CallCenterManager session object for invoking methods on it.
     * @return CallCenterManager session object which can be used for method invokation
     * @exception throws TTKException
     */

    private CallCenterManager getCallCenterManagerObject() throws TTKException
    {
    	CallCenterManager callCenterManager = null;
        try
        {
            if(callCenterManager == null)
            {
                InitialContext ctx = new InitialContext();
                callCenterManager = (CallCenterManager) ctx.lookup("java:global/TTKServices/business.ejb3/CallCenterManagerBean!com.ttk.business.customercare.CallCenterManager");
            }//end of if(callCenterManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "callcenter");
        }//end of catch
        return callCenterManager;
    }//end getCallCenterManagerObject()
}// end of AssignAction