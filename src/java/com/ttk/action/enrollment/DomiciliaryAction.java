/**
 * @ (#) DomiciliaryAction.java Mar 8, 2006
 * Project 		: TTK HealthCare Services
 * File 			: DomiciliaryAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created 	: Mar 8, 2006
 *
 * @author 		: Pradeep R
 * Modified by 	:
 * Modified date :
 * Reason 		:
 */

package com.ttk.action.enrollment;

import java.util.ArrayList;
import java.util.Arrays;

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
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.enrollment.MemberManagerBean;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.DomiciliaryVO;
import com.ttk.dto.enrollment.MemberVO;

/**
 * This class is used for displaying the Domiciliary Treatment Limits .
 * This class also provides Addition and Updation of Domiciliary Treatment.
 */

public class DomiciliaryAction extends TTKAction {
	private static Logger log = Logger.getLogger( DomiciliaryAction.class );
	private static final String strDomiciliary="domiciliary";
	private static final String strIndPolicy="Individual Policy";
	private static final String strIndGrpPolicy="Ind. Policy as Group";
	private static final String strCorporatePolicy="Corporate Policy";
	private static final String strNonCorporatePolicy="Non-Corporate Policy";
	//declare forward paths
	private static final String strIndDomiciliary="inddomiciliary";
	private static final String strGrpDomiciliary="grpdomiciliary";
	private static final String strCorpDomiciliary="cordomiciliary";
	private static final String strNonCorpDomiciliary="noncorpdomiciliary";
	
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
	public ActionForward doViewDomiciliary(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doViewDomiciliary method of DomiciliaryAction");
			//get the DynaActionForm instance
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");  
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			MemberManager memberManager=this.getMemberManagerObject();
			DynaActionForm frmDomiciliary=(DynaActionForm)form;     //get the instance of formbean
			//declaration of the local variables
			String strDomiciliaryInfo="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			Long lngPolicyGrpSeqID=null;
			String strSubLinkCode="";
			//get the policy group seqid from the treedata if you
			//are coming here by selecting the object from tree
			if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			{
				//initialize the form bean
				frmDomiciliary.initialize(mapping);
				MemberVO memberVO=(MemberVO)treeData.getSelectedObject(
						(String)request.getParameter("selectedroot"),null);
				lngPolicyGrpSeqID=memberVO.getPolicyGroupSeqID();
				//load the policy group seq id to form bean
				frmDomiciliary.set("policyGroupSeqID",lngPolicyGrpSeqID);
			}//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			else
			{
				//select the policy group Seq Id from the form bean
				lngPolicyGrpSeqID=(Long)frmDomiciliary.get("policyGroupSeqID");
			}//end of else
			if(strActiveSubLink.equals(strIndPolicy))
			{
				strSubLinkCode="IP";
				strDomiciliaryInfo=strIndDomiciliary;
			}//end of if (strActiveSubLink.equals(strIndPolicy))
			else if (strActiveSubLink.equals(strIndGrpPolicy))
			{
				strSubLinkCode="IG";
				strDomiciliaryInfo=strGrpDomiciliary;
				
			}//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
			else if (strActiveSubLink.equals(strCorporatePolicy))
			{
				strSubLinkCode="CP";
				strDomiciliaryInfo=strCorpDomiciliary;
			}//end of else if (strActiveSubLink.equals(strCorporatePolicy))
			else if (strActiveSubLink.equals(strNonCorporatePolicy))
			{
				strSubLinkCode="NC";
				strDomiciliaryInfo=strNonCorpDomiciliary;
			}//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
			ArrayList<Object> alSearchCriteria =new ArrayList<Object>();
			alSearchCriteria.add(lngPolicyGrpSeqID);    //policy_Group_seq_id
			alSearchCriteria.add(strSwitchType);    // identifier of Enrollment or Endorsement flow
			alSearchCriteria.add(strSubLinkCode);    //identifier of current sublink
			ArrayList<Object> alDomiciliaryInfo=memberManager.getDomiciliaryList(alSearchCriteria);
			//System.out.println("alDomiciliaryInfo..."+alDomiciliaryInfo);
			 if(alDomiciliaryInfo!=null && alDomiciliaryInfo.size()>0)
             {
             	DomiciliaryVO domiciliaryVO=(DomiciliaryVO)alDomiciliaryInfo.get(0);
             	
             	frmDomiciliary.set("domiciliaryInfo",(DomiciliaryVO[])alDomiciliaryInfo.toArray(new DomiciliaryVO[0]));
             	if(domiciliaryVO.getDomiciliaryTypeDesc()==null ||domiciliaryVO.getDomiciliaryTypeDesc().length()== 0)
             	{
             		frmDomiciliary.set("domiciliaryTypeDesc","");
             	}//end of if(domiciliaryVO.getDomiciliaryTypeDesc()==null ||domiciliaryVO.getDomiciliaryTypeDesc().length()== 0)
             	else
             	{
             		frmDomiciliary.set("domiciliaryTypeDesc",domiciliaryVO.getDomiciliaryTypeDesc());
             	}//end of else
             	if(domiciliaryVO.getDomiciliaryTypeDesc().equalsIgnoreCase("floater"))
             	{
             		frmDomiciliary.set("domiciliaryTypeID","PFL");
             	}//end of if(domiciliaryVO.getDomiciliaryTypeDesc().equalsIgnoreCase("floater"))
             	else if(domiciliaryVO.getDomiciliaryTypeDesc().equalsIgnoreCase("Non-Floater"))
             	{
             		frmDomiciliary.set("domiciliaryTypeID","PNF");
             	}//end of else if(domiciliaryVO.getDomiciliaryTypeDesc().equalsIgnoreCase("Non-Floater"))
             	else
             	{
             		frmDomiciliary.set("domiciliaryTypeID","");
             	}//end of else
             	frmDomiciliary.set("totalFlyLimit",domiciliaryVO.getOverallfamilyLimit());
				//added for Policy deductable - KOC-1277
             	frmDomiciliary.set("totalFlyDeductableLimit",domiciliaryVO.getOverallfamilyDeductableLimit());
             	//frmDomiciliary.set("totalFlyDeductablePercLimit",domiciliaryVO.getOverallfamilyDeductablePercLimit());
             	frmDomiciliary.set("policyDeductableTypeId",domiciliaryVO.getPolicyDeductableTypeId());
             	
             	frmDomiciliary.set("overallFamilyCheck",domiciliaryVO.getOverallFamilyCheckYN());
             	frmDomiciliary.set("memberDeductable",domiciliaryVO.getMemberDeductableYN());
             
            }//end of  if(alDomiciliaryInfo!=null && alDomiciliaryInfo.size()>0)
			else
			{
				frmDomiciliary.initialize(mapping); //If data is not there, initialize the form bean
				//load the policy group seq id to form bean
				frmDomiciliary.set("policyGroupSeqID",lngPolicyGrpSeqID); 
			}//end of else
			frmDomiciliary.set("caption","Domiciliary Treatment(OPD) / Hospitalization Limits - "+"["
					+WebBoardHelper.getPolicyNumber(request)+"]"); //load the caption to be displayed
			request.getSession().setAttribute("frmDomiciliary",frmDomiciliary);
			return this.getForward(strDomiciliaryInfo,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDomiciliary));
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
		return doViewDomiciliary(mapping,form,request,response);
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
	public ActionForward doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doSubmit method of UserGroupAction");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");  
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			DynaActionForm frmDomiciliary=(DynaActionForm)form; 
			ArrayList<Object> alDomiciliaryInfo = new ArrayList<Object>();
			DomiciliaryVO domiciliaryVO=null;
			String[] strMemberSeqIds=request.getParameterValues("memberSeqID");
			String[] strName=request.getParameterValues("name");
			String[] strDomiciliaryLimit=request.getParameterValues("domiciliaryLimit");
			String[] strHospitalizationLimit=request.getParameterValues("domHospAmt");
			String strTotalFlyLimit=request.getParameter("totalFlyLimit");
			//added for Policy deduction
			String[] strPolicyDeductLimit = request.getParameterValues("policyDeductableLimit");
			String[] strMemberDeductableYN = request.getParameterValues("memberDeductableYN");
			
			if(strMemberSeqIds!=null && strMemberSeqIds.length>0)
			{
				for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
				{
					domiciliaryVO=new DomiciliaryVO();
					domiciliaryVO.setMemberSeqID(TTKCommon.checkNull(
							strMemberSeqIds[iCount]).equals("")? null:new Long(strMemberSeqIds[iCount]));
					domiciliaryVO.setDomiciliaryTypeID((String)frmDomiciliary.get("domiciliaryTypeID"));
					domiciliaryVO.setName(TTKCommon.checkNull(strName[iCount]));
					domiciliaryVO.setDomiciliaryLimit(
							(strDomiciliaryLimit[iCount])==null?"":strDomiciliaryLimit[iCount]);
					domiciliaryVO.setDomHospAmt(
							(strHospitalizationLimit[iCount])==null?"":strHospitalizationLimit[iCount]);
						//added for Policy Deductable - KOC-1277
					domiciliaryVO.setPolicyDeductableLimit((strPolicyDeductLimit[iCount])==null?"":strPolicyDeductLimit[iCount]);
					domiciliaryVO.setMemberDeductableYN(TTKCommon.checkNull(strMemberDeductableYN[iCount]));
					if(iCount==0)
					{
						domiciliaryVO.setOverallfamilyLimit(strTotalFlyLimit);
					}//end of if(iCount==0)
					domiciliaryVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
					alDomiciliaryInfo.add(domiciliaryVO);
				}//end of for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
			}//end of if(strMemberSeqIds!=null && strMemberSeqIds.length>0)
			frmDomiciliary.set("domiciliaryInfo",(DomiciliaryVO[])alDomiciliaryInfo.toArray(new DomiciliaryVO[0]));
			return mapping.findForward("submitform");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDomiciliary));
		}//end of catch(Exception exp)
	}//end of doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside doSave method of UserGroupAction");
			//get the DynaActionForm instance
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");  
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			MemberManager memberManager=this.getMemberManagerObject();
			DynaActionForm frmDomiciliary=(DynaActionForm)form;     //get the instance of formbean
			//declaration of the local variables
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			Long lngPolicyGrpSeqID=null;
			String strSubLinkCode="";
			//get the policy group seqid from the treedata if you
			//are coming here by selecting the object from tree
			if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			{
				//initialize the form bean
				frmDomiciliary.initialize(mapping);
				MemberVO memberVO=(MemberVO)treeData.getSelectedObject(
						(String)request.getParameter("selectedroot"),null);
				lngPolicyGrpSeqID=memberVO.getPolicyGroupSeqID();
				//load the policy group seq id to form bean
				frmDomiciliary.set("policyGroupSeqID",lngPolicyGrpSeqID); 
			}//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
			else
			{//select the policy group Seq Id from the form bean
				lngPolicyGrpSeqID=(Long)frmDomiciliary.get("policyGroupSeqID"); 
			}//end of else
			if(strActiveSubLink.equals(strIndPolicy))
			{
				strSubLinkCode="IP";
			}//end of if(strActiveSubLink.equals(strIndPolicy))
			else if (strActiveSubLink.equals(strIndGrpPolicy))
			{
				strSubLinkCode="IG";
			}//end of if(strActiveSubLink.equals(strIndGrpPolicy))
			else if (strActiveSubLink.equals(strCorporatePolicy))
			{
				strSubLinkCode="CP";
			}//end of if(strActiveSubLink.equals(strCorporatePolicy))
			else if (strActiveSubLink.equals(strNonCorporatePolicy))
			{
				strSubLinkCode="NC";
			}// end of if(strActiveSubLink.equals(strNonCorporatePolicy))
			ArrayList<Object> alDomiciliaryInfo = new ArrayList<Object>();
			DomiciliaryVO domiciliaryVO=null;
			String[] strMemberSeqIds=request.getParameterValues("memberSeqID");
			String[] strName=request.getParameterValues("name");
			String[] strDomiciliaryLimit=request.getParameterValues("domiciliaryLimit");
			String[] strDomHospAmt=request.getParameterValues("domHospAmt");
			String strTotalFlyLimit=request.getParameter("totalFlyLimit");
			//added for Policy deduction
			String[] strPolicyDeductLimit = request.getParameterValues("policyDeductableLimit");
			
			String[] strMemberDeductableYN = request.getParameterValues("memberDeductableYN");
		
			String strTotalFlyDeductLimit = request.getParameter("totalFlyDeductableLimit");
			String strOverallFamilyCheckYN = request.getParameter("overallFamilyCheckYN");
			
			if(strMemberSeqIds!=null && strMemberSeqIds.length>0)
			{
				for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
				{
					domiciliaryVO=new DomiciliaryVO();
					domiciliaryVO.setMemberSeqID(TTKCommon.checkNull(
							strMemberSeqIds[iCount]).equals("")? null:new Long(strMemberSeqIds[iCount]));
					domiciliaryVO.setDomiciliaryTypeID((String)frmDomiciliary.get("domiciliaryTypeID"));
					domiciliaryVO.setName(TTKCommon.checkNull(strName[iCount]));
					domiciliaryVO.setLimit(TTKCommon.getBigDecimal(strDomiciliaryLimit[iCount]));
					domiciliaryVO.setHospAmt(TTKCommon.getBigDecimal(strDomHospAmt[iCount]));					
					if(iCount==0)
					{
						domiciliaryVO.setFamilyLimit(TTKCommon.getBigDecimal(strTotalFlyLimit));
					}//end of if(iCount==0)
					domiciliaryVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
					domiciliaryVO.setDedLimit(TTKCommon.getBigDecimal((strPolicyDeductLimit[iCount])==null?"":strPolicyDeductLimit[iCount]));
					
					domiciliaryVO.setMemberDeductableYN(TTKCommon.checkNull(strMemberDeductableYN[iCount]));
					domiciliaryVO.setOverallfamilyDeductableLimit(strTotalFlyDeductLimit);
					
					domiciliaryVO.setOverallFamilyCheckYN(strOverallFamilyCheckYN);
					alDomiciliaryInfo.add(domiciliaryVO);
				}//end of for(int iCount=0;iCount<strMemberSeqIds.length;iCount++)
			}//end of if(strMemberSeqIds!=null && strMemberSeqIds.length>0)
			int iCount= memberManager.saveDomiciliary(alDomiciliaryInfo,strSwitchType,strSubLinkCode);
			if(iCount>0)
			{
				ArrayList<Object> alSearchCriteria =new ArrayList<Object>();
				alSearchCriteria.add(lngPolicyGrpSeqID);    //policy_Group_seq_id
				alSearchCriteria.add(strSwitchType);    // identifier of Enrollment or Endorsement flow
				alSearchCriteria.add(strSubLinkCode);    //identifier of current sublink
				alDomiciliaryInfo=memberManager.getDomiciliaryList(alSearchCriteria);
				if(alDomiciliaryInfo!=null && alDomiciliaryInfo.size()>0)
				{
					domiciliaryVO=(DomiciliaryVO)alDomiciliaryInfo.get(0);
					frmDomiciliary.set("domiciliaryInfo",
							(DomiciliaryVO[])alDomiciliaryInfo.toArray(new DomiciliaryVO[0]));
					frmDomiciliary.set("domiciliaryTypeID",domiciliaryVO.getDomiciliaryTypeID());
					frmDomiciliary.set("domiciliaryTypeDesc",domiciliaryVO.getDomiciliaryTypeDesc());
					frmDomiciliary.set("totalFlyLimit",domiciliaryVO.getOverallfamilyLimit());
					//added for Policy Deductable - KOC-1277
					frmDomiciliary.set("totalFlyDeductableLimit",domiciliaryVO.getOverallfamilyDeductableLimit());
					
					
					//added for Policy Deductable - KOC-1277
					
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of  if(alDomiciliaryInfo!=null && alDomiciliaryInfo.size()>0)
			}//end of if(iCount>0)
			
			return (mapping.findForward("cordomiciliary"));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDomiciliary));
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
			String strCloseDomiciliary="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(strActiveSubLink.equals(strIndPolicy))
			{
				strCloseDomiciliary="indMember";
			}//end of if(strActiveSubLink.equals(strIndPolicy))
			else if (strActiveSubLink.equals(strIndGrpPolicy))
			{
				strCloseDomiciliary="indMember";
			}//end of if(strActiveSubLink.equals(strIndGrpPolicy))
			else if (strActiveSubLink.equals(strCorporatePolicy))
			{

				String policy_status ="";
				MemberManagerBean memberManager = new MemberManagerBean();
				Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);
				policy_status =    memberManager.getPolicyStatus(pol_seq_id);
			      if(policy_status.trim().equals("FTS"))
			      {
				  strCloseDomiciliary="corpMember";
			      }
			      else
			      {
			    	  strCloseDomiciliary="corpRenewMember"; 
			      }
			}//end of if(strActiveSubLink.equals(strCorporatePolicy))
			else if (strActiveSubLink.equals(strNonCorporatePolicy))
			{
				strCloseDomiciliary="corpMember";
			}//end of if(strActiveSubLink.equals(strNonCorporatePolicy))
			
			return mapping.findForward(strCloseDomiciliary);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDomiciliary));
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
			throw new TTKException(exp, strDomiciliary);
		}//end of catch
		return memberManager;
	}//end getMemberManagerObject() throws TTKException
}//end of Domiciliary Action


