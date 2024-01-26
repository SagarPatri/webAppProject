/**
 * @ (#) CancelEnrollmentAction.java Jan 16, 2008
 * Project      : TTK HealthCare Services
 * File         : CancelEnrollmentAction.java
 * Author       : Yogesh Gowda S.C.
 * Company      : Span Systems Corporation
 * Date Created : Jan 16, 2008
 *
 * @author       :  Yogesh Gowda S.C.
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 * Modified by   :  
 */
package com.ttk.action.onlineforms;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.onlineforms.MemberCancelVO;

import formdef.plugin.util.FormUtils;

public class CancelEnrollmentAction extends TTKAction {
	
	//for forwarding
	private static final String strCancelEnroll="cancelenroll";
	private static final String strMemberDetails="closeAddEnrollment";
	//Exception Message Identifier
	private static final String strMember="groupdetail"; 
	private static final String strFailure="failure";
	private static Logger log = Logger.getLogger( CancelEnrollmentAction.class );
	
	/**
	 * This method is used to navigate to next screen when cancel icon is clicked.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doEnrollCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);    	 
			log.debug("inside doEnrollCancel method of CancelEnrollmentAction");
			DynaActionForm frmCancelEnroll = (DynaActionForm)form;
			MemberVO memberVO =null;
			TreeData treeData = TTKCommon.getTreeData(request);
			String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
			if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
			{
				memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode); 			 				 				 				
			}//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
			String strCaption="";
			OnlineAccessPolicyVO onlinePolicyVO =null;   		 
			onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
			strCaption="[ "+TTKCommon.checkNull(onlinePolicyVO.getPolicyNbr())+" ]"+"[ "+TTKCommon.checkNull(memberVO.getEnrollmentID())+" ]";
			ArrayList<Long> alSelCanParams = new ArrayList<Long>();
			alSelCanParams.add(memberVO.getPolicyGroupSeqID());
			alSelCanParams.add(memberVO.getMemberSeqID());
			MemberCancelVO memberCancelVO = onlineAccessManagerObject.selectCancellation(alSelCanParams);
			if(memberCancelVO==null)
			{
				memberCancelVO = new MemberCancelVO();
			}//end of if(memberCancelVO==null)
			memberCancelVO.setPolicyGroupSeqID(memberVO.getPolicyGroupSeqID());
			memberCancelVO.setMemberSeqID(memberVO.getMemberSeqID());
			memberCancelVO.setPolicySeqID(memberVO.getPolicySeqID());	
			memberCancelVO.setRelationTypeID(memberVO.getRelationTypeID());
			frmCancelEnroll = (DynaActionForm)FormUtils.setFormValues("frmCancelEnroll",memberCancelVO,this,mapping,request);
			frmCancelEnroll.set("caption",strCaption);
			request.setAttribute("frmCancelEnroll",frmCancelEnroll);
			return this.getForward(strCancelEnroll, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
		}//end of catch(Exception exp)
	}//end of method doEnrollCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	
	/**
	 * 
	 * This method is used to save the Enrollment Cancellation.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSaveEnrollCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);    	 
			log.debug("inside doSaveEnrollCancel method of CancelEnrollmentAction");
			DynaActionForm frmCancelEnroll = (DynaActionForm)form;
			MemberCancelVO memberCancelVO = (MemberCancelVO)FormUtils.getFormValues(frmCancelEnroll,this,mapping,request);
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
			Long lngPolicyGroupSeqID = memberCancelVO.getPolicyGroupSeqID();
			Long lngMemberSeqID = memberCancelVO.getMemberSeqID();
			memberCancelVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			if(memberCancelVO.getRelationTypeID().equals("NSF"))
			{
				ActionMessages actionMessages = new ActionMessages();
				ActionMessage actionMessage=new ActionMessage("onlineforms.cancel.self");
				actionMessages.add("global.error",actionMessage);
				saveErrors(request,actionMessages);
				return (mapping.findForward(strCancelEnroll));
			}//end of if(memberCancelVO.getRelationTypeID().equals("NSF"))
			
			int i = onlineAccessManagerObject.saveCancellation(memberCancelVO);
			if(i>0)
			{
				request.setAttribute("updated","message.saved");
				ArrayList<Long> alSelCanParams = new ArrayList<Long>();
				alSelCanParams.add(lngPolicyGroupSeqID);
				alSelCanParams.add(lngMemberSeqID);
				MemberCancelVO memberCancelVO1 = onlineAccessManagerObject.selectCancellation(alSelCanParams);
				if(memberCancelVO==null)
				{
					memberCancelVO = new MemberCancelVO();
				}//end of if(memberCancelVO==null)
				memberCancelVO1.setPolicyGroupSeqID(memberCancelVO.getPolicyGroupSeqID());
				memberCancelVO1.setMemberSeqID(memberCancelVO.getMemberSeqID());
				memberCancelVO1.setPolicySeqID(memberCancelVO.getPolicySeqID());
				DynaActionForm frmCancelEnroll1 = (DynaActionForm)FormUtils.setFormValues("frmCancelEnroll",memberCancelVO1,this,mapping,request);												
				frmCancelEnroll1.set("caption",frmCancelEnroll.get("caption"));
				request.setAttribute("frmCancelEnroll",frmCancelEnroll1);
			}//end of if(i>0)
			return this.getForward(strCancelEnroll, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
		}//end of catch(Exception exp)
	}//end of method doSaveEnrollCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	
	/**
	 * 
	 * This method is used to navigate to previous screen .
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCloseEnrollCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);    	 
			log.debug("inside doCloseEnrollCancel method of CancelEnrollmentAction");
			return mapping.findForward(strMemberDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
		}//end of catch(Exception exp)
	}//end of method doCloseEnrollCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	
	/**
	 * 
	 * This method is used to Reset the values.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doResetEnrollCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);    	 
			log.debug("inside doResetEnrollCancel method of CancelEnrollmentAction");   
			DynaActionForm frmCancelEnroll = (DynaActionForm)form;
			MemberCancelVO memberCancelVO = (MemberCancelVO)FormUtils.getFormValues(frmCancelEnroll,this,mapping,request);
			ArrayList<Long> alSelCanParams = new ArrayList<Long>();
			alSelCanParams.add(memberCancelVO.getPolicyGroupSeqID());
			alSelCanParams.add(memberCancelVO.getMemberSeqID());
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();     		    	 
			MemberCancelVO memberCancelVO1 = onlineAccessManagerObject.selectCancellation(alSelCanParams);
			if(memberCancelVO1==null)
			{
				memberCancelVO1 = new MemberCancelVO();
			}//end of if(memberCancelVO1==null)
			memberCancelVO1.setPolicyGroupSeqID(memberCancelVO.getPolicyGroupSeqID());
			memberCancelVO1.setMemberSeqID(memberCancelVO.getMemberSeqID());
			memberCancelVO1.setPolicySeqID(memberCancelVO.getPolicySeqID());
			DynaActionForm frmCancelEnroll1 = (DynaActionForm)FormUtils.setFormValues("frmCancelEnroll",memberCancelVO1,this,mapping,request);
			frmCancelEnroll1.set("caption",frmCancelEnroll.get("caption"));
			request.setAttribute("frmCancelEnroll",frmCancelEnroll1);
			return this.getForward(strCancelEnroll, mapping, request);    		 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
		}//end of catch(Exception exp)
	}//end of method doResetEnrollCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	
	/**
	 * Returns the onlineAccessManager session object for invoking methods on it.
	 * @return onlineAccessManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineAccessManagerObject() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
			}//end if(onlineAccessManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strFailure);
		}//end of catch
		return onlineAccessManager;
	}//end of getOnlineAccessManagerObject()
}//end of CancelEnrollmentAction()
