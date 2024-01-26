package com.ttk.action.onlineforms;

/**
	 * @ (#) ChangeCashlessFBAction
	 * Project       : TTK HealthCare Services
	 * File          : ChangeCashlessFBAction.java
	 * Author        : Manohar
	 * Company       : RCS
	 * Date Created  : 09th April,2012
	 *
	 * @author       : 
	 * Modified by   :
	 * Modified date :
	 * Reason        :
	 */



import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import com.ttk.action.TTKAction;
import com.ttk.business.onlineforms.OnlineFeedbackManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.FeedbackCashlessVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import formdef.plugin.util.FormUtils;

/**
 * 
 * This class is used to view the Cashless Feedback Form. Added For CR KOC1168 Feedback Form
 */
public class ChangeCashlessFBAction extends TTKAction {
	
	private static Logger log = Logger.getLogger(ChangeCashlessFBAction.class );
	
	private static final String strCashlessFBForm="cashlessfbform";
	private static final String strCashlessFBFormClose="cashlessfbformclose";
	// Exception Message Identifier
	private static final String strFeedbackFormError = "feedbackformCashless";
	
	/**
	 * This method is used to add the feedback record.
	 * Finally it forwards to the web login home page screen
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
			
			log.debug("Inside the doSave method of ChangeCashlessFBAction");
			// Variable Initialization
			String ans="";
			String remarks="";
			String remarks1="";
			long addedby=0;
			
			DynaActionForm frmChangeCashlessFBForm =(DynaActionForm)form;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
															getAttribute("UserSecurityProfile");
			
			FeedbackCashlessVO feedbackCashlessVO=new FeedbackCashlessVO();
			
			ArrayList<Object> fbdetails=new ArrayList<Object>();
			
			OnlineFeedbackManager feedbackManagerObject=this.getFeedbackManagerObject();
			fbdetails.add(null); // 1st param feedback_seq_id
			
			Long lngPlySeqId=userSecurityProfile.getPolicySeqID();	
			if(lngPlySeqId==null)
				fbdetails.add(null);
			else
				fbdetails.add(lngPlySeqId); // 2nd param policy_seq_id
			
			Long lngPlyGroupSeqId=userSecurityProfile.getPolicyGrpSeqID();
			if(lngPlyGroupSeqId==null)
				fbdetails.add(null);
			else
				fbdetails.add(lngPlyGroupSeqId); //3rd param policy_group_seq_id   
			
			ArrayList qustlist=(ArrayList)frmChangeCashlessFBForm.get("alQuestionList");
			feedbackCashlessVO.setAlQuestionList(qustlist);
			
			ArrayList arrqustlist=feedbackCashlessVO.getAlQuestionList();
			StringBuffer sb=new StringBuffer();
			int chk=0;
			for(int i=0;i<arrqustlist.size();i++)
			{	
				try
				{
					chk=Integer.parseInt(arrqustlist.get(i).toString());
					if(chk>0)
					{
						ans=sb.append("|"+arrqustlist.get(i)).toString();		
					}
				}
				catch(NumberFormatException exp)
				{
					chk=0;
					ans=sb.append("|"+chk).toString();	
				}
						
			}
			ans=ans.concat("|");
			fbdetails.add(ans); // 4th param answers 
					
			remarks=frmChangeCashlessFBForm.getString("remarks");
			if(remarks==null)
				fbdetails.add(null);
			else
				fbdetails.add(remarks); // 5th param remarks1  
			
			remarks1=frmChangeCashlessFBForm.getString("remarks1");
			if(remarks1==null)
				fbdetails.add(null);
			else
				fbdetails.add(remarks1); // 6th param remarks2  
			
			Date sAdmissionDate=TTKCommon.getUtilDate((String)frmChangeCashlessFBForm.get("sAdmissionDate"));
			if(sAdmissionDate==null)
				fbdetails.add(null);
			else
				fbdetails.add(sAdmissionDate.getTime()); // 7th param admission_date
			
			if(userSecurityProfile.getAddedBy()==null)
				fbdetails.add(null);   
			else
			{
				addedby=userSecurityProfile.getAddedBy();
				fbdetails.add(addedby);   // 8th param added_by   
			}
			           
			fbdetails.add(null);         // 9th param rows_processed        
			
			Long lngSeqID=feedbackManagerObject.saveCashlessFeedbackDetails(fbdetails);	
			if(lngSeqID>0)
			{	
				log.debug("Inside after feedback given calling the doDefault method of ChangeCashlessFBAction");
				userSecurityProfile=(UserSecurityProfile)request.getSession().
				getAttribute("UserSecurityProfile");	
				request.setAttribute("updated","message.saved");
			//	request.setAttribute("caption","feedback.cashlesscaption");
				String groupid=userSecurityProfile.getGroupID();
				String claimType="CNH";	
				feedbackManagerObject=this.getFeedbackManagerObject();
				FeedbackCashlessVO feedbackcashVO =new FeedbackCashlessVO();
				ArrayList alQuestionList = null;
				alQuestionList = feedbackManagerObject.getQuestionCashlessList(groupid,claimType);
				frmChangeCashlessFBForm = (DynaActionForm)FormUtils.setFormValues("frmChangeCashlessFBForm",														   
						feedbackcashVO, this, mapping, request);			
				frmChangeCashlessFBForm.set("alQuestionList",alQuestionList);
				request.getSession().setAttribute("frmChangeCashlessFBForm",frmChangeCashlessFBForm);				
				return mapping.findForward(strCashlessFBForm);
			}
			return mapping.findForward(strCashlessFBFormClose);
		}//end of try
		
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strFeedbackFormError));
		}//end of catch(Exception exp)
	
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to for bringing the questions and answers for the feedback form Cashless
	 * 
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 */
	
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDefault method of ChangeCashlessFBAction");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
		//	request.setAttribute("caption","feedback.cashlesscaption");
			String groupid=userSecurityProfile.getGroupID();
			String claimType="";	
			if(request.getParameter("claimType").contains("CNH"))
			{
				claimType="CNH";
			}
			OnlineFeedbackManager feedbackManagerObject=this.getFeedbackManagerObject();
			FeedbackCashlessVO feedbackcashVO =new FeedbackCashlessVO();
			ArrayList alQuestionList = null;
			alQuestionList = feedbackManagerObject.getQuestionCashlessList(groupid,claimType);
			DynaActionForm frmChangeCashlessFBForm = (DynaActionForm)FormUtils.setFormValues("frmChangeCashlessFBForm",														   
					feedbackcashVO, this, mapping, request);
			frmChangeCashlessFBForm.set("alQuestionList",alQuestionList);
			request.getSession().setAttribute("frmChangeCashlessFBForm",frmChangeCashlessFBForm);
			return mapping.findForward(strCashlessFBForm);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strFeedbackFormError));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to close the form and return to online homepage screen
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
			
			log.debug("Inside doClose of ChangeCashlessFBAction");
			return mapping.findForward(strCashlessFBFormClose);
		}//end of try
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strFeedbackFormError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the FeedbackManager session object for invoking methods on it.
	 * @return FeedbackManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private OnlineFeedbackManager getFeedbackManagerObject() throws TTKException
	{
		OnlineFeedbackManager feedbackManager = null;
		try
		{
			if(feedbackManager == null)
			{
				InitialContext ctx = new InitialContext();
				feedbackManager = (OnlineFeedbackManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineFeedbackManagerBean!com.ttk.business.onlineforms.OnlineFeedbackManager");
			}//end of if(feedbackManager == null)
		}//end of try
		
		catch(Exception exp)
		{
			throw new TTKException(exp,strFeedbackFormError);
		}//end of catch
		return feedbackManager;
	}//end getFeedbackManagerObject()
	
	
}//end of class ChangeCashlessFBAction
