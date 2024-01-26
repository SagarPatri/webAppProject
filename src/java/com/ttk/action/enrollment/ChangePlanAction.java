//KOC 1270 for hospital cash benefit
/**
 * @ (#) ChangePlanAction.java Mar 8, 2006
 * Project 		: TTK HealthCare Services
 * File 		: ChangePlanAction.java
 * Author 		: 
 * Company 		:RCS TECHNOLOGIES
 * Date Created :
 *
 * @author 		: 
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
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.enrollment.MemberManagerBean;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.DomiciliaryVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.usermanagement.PasswordValVO;

import formdef.plugin.util.FormUtils;
/**
 * This class is used for displaying the ChangePlanIcon Limits .
 * This class also provides Addition and Updation of ChangePlanIcon .
 */

  public class ChangePlanAction extends TTKAction {
	private static Logger log = Logger.getLogger( ChangePlanAction.class );
	private static final String strChangePlan="ChangePlan";
	private static final String strIndPolicy="Individual Policy";
	private static final String strIndGrpPolicy="Ind. Policy as Group";
	private static final String strCorporatePolicy="Corporate Policy";	
	private static final String strNonCorporatePolicy="Non-Corporate Policy";
	//declare forward paths
	private static final String strIndchangeplan="indchangeplan";
	private static final String strGrpchangeplan="grpchangeplan";
	private static final String strCorpchangeplan="corchangeplan";
	private static final String strNonCorpchangeplan="noncorpchangeplan";
	
	/**
	 * This method is used to display the Domiciliary.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	
	public ActionForward doViewChangePlan(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doViewChangePlan method of DomiciliaryAction");
			//get the DynaActionForm instance
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");  
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			MemberManager memberManager=this.getMemberManagerObject();
			DynaActionForm frmChangePlan=(DynaActionForm)form;
			MemberVO memberVO=new MemberVO();//get the instance of formbean
			Long lngPolicyGrpSeqID=null;
		
			//get the policy group seqid from the treedata if you
			//are coming here by selecting the object from tree
			if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			{
				//initialize the form bean
				frmChangePlan.initialize(mapping);
				 memberVO=(MemberVO)treeData.getSelectedObject((String)request.getParameter("selectedroot"),null);
				lngPolicyGrpSeqID=memberVO.getPolicyGroupSeqID();
				frmChangePlan.set("policyGroupSeqID",lngPolicyGrpSeqID);
			}//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			else
			{
				//select the policy group Seq Id from the form bean
				lngPolicyGrpSeqID=(Long)frmChangePlan.get("policyGroupSeqID");
			}//end of else
				ArrayList alSearchCriteria =new ArrayList();
			
			 	alSearchCriteria.add(lngPolicyGrpSeqID);    //policy_Group_seq_id
			 	memberVO=(MemberVO)memberManager.getCashBenefitInfo(alSearchCriteria);
				frmChangePlan.initialize(mapping); //If data is not there, initialize the form bean
				frmChangePlan.set("policyGroupSeqID",lngPolicyGrpSeqID);
				request.getSession().setAttribute("lngPolicyGrpSeqID",lngPolicyGrpSeqID);
				frmChangePlan = (DynaActionForm)FormUtils.setFormValues("frmChangePlan",memberVO,this,mapping,request);
				request.setAttribute("frmChangePlan",frmChangePlan);
				frmChangePlan.set("hospCashBenefitsYN",memberVO.getHospCashBenefitsYN());
				frmChangePlan.set("convCashBenefitsYN",memberVO.getConvCashBenefitsYN());
				//added for koc 1278
				frmChangePlan.set("personalWaitingPeriodYN",memberVO.getPersonalWaitingPeriodYN());
				//added for koc 1278
				//added for KOC-1273
				frmChangePlan.set("criticalBenefitYN",memberVO.getCriticalBenefitYN());
			frmChangePlan.set("caption","ChangePlan / Hospital And Canvalescence Cash Benefit / PED / Personal Waiting Period - "+"["+WebBoardHelper.getPolicyNumber(request)+"]"); //load the caption to be displayed
			return this.getForward(strCorpchangeplan,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCorpchangeplan));
		}//end of catch(Exception exp)
	}//end of doViewDomiciliary(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
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
		log.debug("Inside doReset method of DomiciliaryAction");
		return doViewChangePlan(mapping,form,request,response);
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doSave method of UserGroupAction");
			//get the DynaActionForm instance
			DynaActionForm frmChangePlan = (DynaActionForm)form;
			MemberVO memberVO=new MemberVO();
			MemberManager memberManager=this.getMemberManagerObject();
			memberVO=(MemberVO)FormUtils.getFormValues(frmChangePlan, "frmChangePlan", this,mapping, request);
			Long lngPolicyGrpSeqID = null;
											
			//frmChangePlan.set("hospCashBenefitsYN", request.getParameter("hospCashBenefitsYN"));
			//frmChangePlan.set("convCashBenefitsYN", request.getParameter("convCashBenefitsYN"));
			request.getSession().getAttribute("lngPolicyGrpSeqID"); 
			
			lngPolicyGrpSeqID=(Long)frmChangePlan.get("policyGroupSeqID");
			String convCashBenefitsYN =request.getParameter("convCashBenefitsYN");
			String hospCashBenefitsYN =request.getParameter("hospCashBenefitsYN");
			//added for koc 1278
			String personalWaitingPeriodYN =request.getParameter("personalWaitingPeriodYN");
			//added for koc 1278

			if(hospCashBenefitsYN.equals("Y"))
			//if(request.getParameter("hospCashBenefitsYN").equals("Y"))
			{
				memberVO.setHospCashBenefitsYN("Y");
			}//end of if(request.getParameter("paAllowedYN")==null)
			else
			{
				memberVO.setHospCashBenefitsYN("N");
			}
			
			if(convCashBenefitsYN.equals("Y"))
			{
				memberVO.setConvCashBenefitsYN("Y");
			}//end of if(request.getParameter("claimAllowedYN")==null)
			else
			{
				memberVO.setConvCashBenefitsYN("N");
			}
			
			//added for koc 1278
			if(personalWaitingPeriodYN.equals("Y"))
			{
				memberVO.setPersonalWaitingPeriodYN("Y");
			}//end of if(request.getParameter("claimAllowedYN")==null)
			else
			{
				memberVO.setPersonalWaitingPeriodYN("N");
			}
			//added for koc 1278			
			
			int iCount= memberManager.saveMemberPlan(memberVO,lngPolicyGrpSeqID);
			if(iCount>0)
			{
				request.setAttribute("updated","message.saved");
				//frmChangePlan = (DynaActionForm)FormUtils.setFormValues("frmChangePlanEdit",memberVO,this,mapping,request);
			   // request.getSession().setAttribute("frmChangePlanEdit",frmChangePlan);
			}//end of  if(alChangePlanInfo!=null && alChangePlanInfo.size()>0)
			
			return this.getForward(strCorpchangeplan, mapping, request);
			//return (mapping.findForward("strChangePlan1"));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCorpchangeplan));
		}//end of catch(Exception exp)
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
			log.debug("Inside the doClose method of DomiciliaryAction");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");  
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			String strCloseChangePlan="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);

			if(strActiveSubLink.equals(strIndPolicy))
			{
				strCloseChangePlan="indMember";
			}//end of if(strActiveSubLink.equals(strIndPolicy))
			else if (strActiveSubLink.equals(strIndGrpPolicy))
			{
				strCloseChangePlan="indMember";
			}//end of if(strActiveSubLink.equals(strIndGrpPolicy))
			else if (strActiveSubLink.equals(strCorporatePolicy))
			{               
		    Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);
            String policy_status ="";
			MemberManagerBean memberManager = new MemberManagerBean();
			policy_status =    memberManager.getPolicyStatus(pol_seq_id);

			if(policy_status.trim().equals("FTS"))
				strCloseChangePlan="corpMember";
			else
				strCloseChangePlan="corpRenewMember";

			}//end of if(strActiveSubLink.equals(strCorpchangeplan))
			else if (strActiveSubLink.equals(strNonCorporatePolicy))
			{
				strCloseChangePlan="corpMember";
			}//end of if(strActiveSubLink.equals(strNonCorporatePolicy))

			return mapping.findForward(strCloseChangePlan);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strChangePlan));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the MemberManager session object for invoking methods on it.
	 * @return MemberManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MemberManager getMemberManagerObject() throws TTKException
	{
		MemberManager memberManager = null;
		try
		{
			if(memberManager == null)
			{
				InitialContext ctx = new InitialContext();
				memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strCorpchangeplan);
		}//end of catch
		return memberManager;
	}//end getMemberManagerObject() throws TTKException
}//end of Domiciliary Action