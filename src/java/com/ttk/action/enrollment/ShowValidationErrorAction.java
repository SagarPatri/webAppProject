/**
 * @ (#) MemberAction.java Feb 2nd, 2006
 * Project 		: TTK HealthCare Services
 * File 		: MemberAction.java
 * Author 		: Krishna K H
 * Company 		: Span Systems Corporation
 * Date Created : Feb 2nd, 2006
 *
 * @author 		: Krishna K H
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.enrollment;

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
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.enrollment.MemberManagerBean;
import com.ttk.business.webservice.ValidationRuleManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;

/**
 * This class is reusable for showing the validation errors which is exist for the particular policices for
 * individual policy,individual policy as group,corporate policies and non corporate policies in enrollment
 * and endorsement flow.
 */

public class ShowValidationErrorAction extends TTKAction
{
	private static Logger log = Logger.getLogger( ShowValidationErrorAction.class );
	/*private static final String strEnrollment="ENM";
    private static final String strEndorsement="END";*/


    // For sub link name
	private static final String strIndividualPolicy="Individual Policy";
	private static final String strIndPolicyasGroup="Ind. Policy as Group";
	private static final String strCorporatePolicy="Corporate Policy";
	private static final String strNonCorporatePolicy="Non-Corporate Policy";
	private static final String strIndShowerrors="indshowerrors";
	private static final String strNcrShowerrors="ncrshowerrors";
	private static final String strCorShowerrors="corshowerrors";
	private static final String strIngShowerrors="ingshowerrors";
	private static final String strIndAddMember="indaddnewmember";
	private static final String strGrpAddMember="grpaddnewmember";
	private static final String strCorpAddMember="coraddnewmember";
	private static final String strCorpAddRenewMember="coraddnewrenewmember";
	private static final String strNonCorpAddMember="noncoraddnewmember";

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
			log.debug("Inside ShowValidationErrorAction doDefault");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
			String strErrorScreen=getErrorscreenForwardPath(request);
			String strPolicyNumber = WebBoardHelper.getPolicyNumber(request);
			DynaActionForm generalForm = (DynaActionForm)form;
			ArrayList<Object> alSearchParm = new ArrayList<Object>();
			alSearchParm.add(strPolicyNumber);
			alSearchParm.add("P");
			alSearchParm.add("ERROR_LOG_SEQ_ID");
			alSearchParm.add("ASC");
			alSearchParm.add(1);
			alSearchParm.add(100);
			ValidationRuleManager validationRuleManager = this.getValidationRuleManagerObject();
			ArrayList alErrors = validationRuleManager.selectRuleErrors(alSearchParm);

			generalForm.set("alErrors",alErrors);
			
			return this.getForward(strErrorScreen, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"showvalidationerror"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside ShowValidationErrorAction doClose");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
			String strAddMember=getAddmemberForwardPath(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strAddMembers  = null; 
			 
			   if(strActiveSubLink.trim().equals("Corporate Policy"))
	            {
	            	 String policy_status ="";
	            	 Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);
	            	 MemberManagerBean memberManager  = new MemberManagerBean();
	                 policy_status =    memberManager.getPolicyStatus(pol_seq_id);
	                 strAddMembers=getForwardPath(strActiveSubLink,policy_status);
	                 request.getSession().setAttribute("policy_status", policy_status);
                }
			   else{
				 strAddMembers=getAddmemberForwardPath(request);
			   }
	            return this.getForward(strAddMembers, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"showvalidationerror"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	/**
	 * This method returns the validation error forward path for next view based on the Flow
	 *
	 * @param request The HTTP request we are processing.
	 * @return strErrorScreen String forward path for the next view
	 */
	private String getErrorscreenForwardPath(HttpServletRequest request)throws TTKException
	{
		String strErrorScreen=null;
		try
		{
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(strActiveSubLink.equals(strIndividualPolicy))
			{
				strErrorScreen=strIndShowerrors;

			}//end of if(strActiveSubLink.equals(strIndividualPolicy))
			else if(strActiveSubLink.equals(strIndPolicyasGroup))
			{
				strErrorScreen=strIngShowerrors;

			}//end of if(strActiveSubLink.equals(strIndPolicyasGroup))
			else if(strActiveSubLink.equals(strCorporatePolicy))
			{
				strErrorScreen=strCorShowerrors;

			} //end of if(strActiveSubLink.equals(strCorporatePolicy))
			else if(strActiveSubLink.equals(strNonCorporatePolicy))
			{
				strErrorScreen=strNcrShowerrors;

			}//end of if(strActiveSubLink.equals(strNonCorporatePolicy))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "showvalidationerror");
		}//end of catch

		return strErrorScreen;
	}//end of getErrorscreenForwardPath(HttpServletRequest request)

	/**
	 * This method returns the add member screen forward path for next view based on the Flow
	 *
	 * @param request The HTTP request we are processing.
	 * @return strAddMember String forward path for the next view
	 */
	private String getAddmemberForwardPath(HttpServletRequest request)throws TTKException
	{
		String strAddMember=null;
		try
		{
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(strActiveSubLink.equals(strIndividualPolicy))
			{
				strAddMember=strIndAddMember;
			}//end of if(strActiveSubLink.equals(strIndividualPolicy))
			else if(strActiveSubLink.equals(strIndPolicyasGroup))
			{
				strAddMember=strGrpAddMember;
			}//end of if(strActiveSubLink.equals(strIndPolicyasGroup))
			else if(strActiveSubLink.equals(strCorporatePolicy))
			{
				strAddMember=strCorpAddMember;
			} //end of if(strActiveSubLink.equals(strCorporatePolicy))
			else if(strActiveSubLink.equals(strNonCorporatePolicy))
			{
				strAddMember=strNonCorpAddMember;
			}//end of if(strActiveSubLink.equals(strNonCorporatePolicy))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "showvalidationerror");
		}//end of catch

		return strAddMember;
	}//end of getAddmemberForwardPath(String strActiveSubLink)

	/**
	 * Returns the PolicyManager session object for invoking methods on it.
	 * @return policyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ValidationRuleManager getValidationRuleManagerObject() throws TTKException
	{
		ValidationRuleManager validationRuleManager = null;
		try
		{
			if(validationRuleManager == null)
			{
				InitialContext ctx = new InitialContext();
				validationRuleManager = (ValidationRuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/ValidationRuleManagerBean!com.ttk.business.webservice.ValidationRuleManager");
			}//end if(policyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "showvalidationerror");
		}//end of catch
		return validationRuleManager;
	}//end getValidationRuleManagerObject()
	
	
	 private String getForwardPath(String strActiveSubLink, String policy_status) {
			// TODO Auto-generated method stub
	    	String strForwardPath=null;
	    	if(strActiveSubLink.equals(strCorporatePolicy)  &&  policy_status.trim().equals("FTS"))
	        {
	            strForwardPath=strCorpAddMember;

	        } 
	    	//end of if(strActiveSubLink.equals(strCorporatePolicy))
	    	else if(strActiveSubLink.equals(strCorporatePolicy)  &&  policy_status.trim().equals("RTS"))
	        {
	    		
	            strForwardPath=strCorpAddRenewMember;
	           
	        } 
			return strForwardPath;
		}
}//end of class ShowValidationErrorAction