/**
 * @ (#) PreauthDetailsAction.java September 30th,2007
 * Project       : TTK HealthCare Services
 * File          : PreauthDetailsAction.java
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : September 30th,2007
 *
 * @author       : Krupa J
 * Modified by   : Arun K.N
 * Modified date : Oct 4th 2007
 * Reason        : 
 */
package com.ttk.action.dataentrycoding;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.dataentryclaims.ParallelClaimManager;
import com.ttk.business.dataentrycoding.ParallelCodingManager;
import com.ttk.business.dataentrypreauth.ParallelPreAuthManager;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class PreauthDetailsAction extends TTKAction{
	
	private static Logger log = Logger.getLogger( PreauthDetailsAction.class );
	private static final String strPreAuthDetail="PreAuthDetails";
	private static final String strPreauth="PreAuth";
	private static final String strClaims="Claims";
	private static final String strCoding="DataEntryCoding";
	private static final String strCodeCleanup="Code CleanUp";
	
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
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try {
			setLinks(request);
			log.debug("Inside PreauthDetailsAction doView ");
			DynaActionForm frmPreauthDetails = (DynaActionForm) form;
			ParallelCodingManager codingManagerObject = null;
			PreAuthDetailVO preAuthDetailVO = null;
			StringBuffer strCaption = new StringBuffer();
			codingManagerObject = this.getCodingManagerObject();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strDetail="";
			/*if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strPreauth))
			{
				strDetail="preauthdetails";
			}//end of if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strPreauth))
			else*/ if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strClaims))
			{
				strDetail="dataentryclaimsdetail";
			}//end of else if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strClaims))
			/*else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strPreauth))
			{
				strDetail="preauthcleanupdetails";
			}//end of if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strPreauth))
			else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strClaims))
			{
				strDetail="claimscleanupdetail";
			}//end of else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strClaims))
			*/strCaption.append(" Edit");
			/*if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strPreauth))//if it is from Coding pre-auth flow
			{
				// check if user trying to hit the tab directly with out selecting
				// the pre-auth
				if (CodingWebBoardHelper.checkWebBoardId(request) == null) 
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.required");
					throw expTTK;
				}// end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)
				// call the business layer to get the Pre-Auth detail
				preAuthDetailVO = codingManagerObject.getPreAuthDetail(
						CodingWebBoardHelper.getPreAuthSeqId(request),
						TTKCommon.getUserSeqId(request));
				
				strCaption.append(" [ "+ CodingWebBoardHelper.getClaimantName(request) + " ]");
				
			}//end of if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strPreauth))
			else*/ if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strClaims))//if it is Coding from Claims flow
			{
				//check if user trying to hit the tab directly with out selecting
				// the pre-auth
				if (CodingClaimsWebBoardHelper.checkWebBoardId(request) == null) 
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}// end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)
				// call the business layer to get the Pre-Auth detail
				preAuthDetailVO = codingManagerObject.getClaimDetail(
						CodingClaimsWebBoardHelper.getClaimsSeqId(request),
						TTKCommon.getUserSeqId(request));
				
				strCaption.append(" [ "+ CodingClaimsWebBoardHelper.getClaimantName(request) + " ]");
			}//end of else if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strClaims))
			/*else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strPreauth))//if it is from Codecleanup pre-auth flow
			{
				// check if user trying to hit the tab directly with out selecting
				// the pre-auth
				if (CodingWebBoardHelper.checkWebBoardId(request) == null) 
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.required");
					throw expTTK;
				}// end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)
				// call the business layer to get the Pre-Auth detail
				preAuthDetailVO = codingManagerObject.getCodeCleanupPreAuthDetail(
						CodingWebBoardHelper.getPreAuthSeqId(request),
						TTKCommon.getUserSeqId(request));
				
				strCaption.append(" [ "+ CodingWebBoardHelper.getClaimantName(request) + " ]");
				
			}//end of else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strPreauth))
			else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strClaims))//if it is from Codecleanup Claims flow
			{
				//check if user trying to hit the tab directly with out selecting
				// the pre-auth
				if (CodingClaimsWebBoardHelper.checkWebBoardId(request) == null) 
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}// end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)
				// call the business layer to get the Pre-Auth detail
				preAuthDetailVO = codingManagerObject.getCodeCleanupClaimDetail(
						CodingClaimsWebBoardHelper.getClaimsSeqId(request),
						TTKCommon.getUserSeqId(request));
				
				strCaption.append(" [ "+ CodingClaimsWebBoardHelper.getClaimantName(request) + " ]");
			}//end of else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strClaims))

*/			frmPreauthDetails = (DynaActionForm) FormUtils.setFormValues("frmPreauthDetails", preAuthDetailVO, 
					this, mapping,request);
			frmPreauthDetails.set("claimantDetailsVO", FormUtils.setFormValues(
					"frmClaimantInfo", preAuthDetailVO.getClaimantVO(), this,mapping, request));
			
			if(strActiveSubLink.equals(strClaims))
			{
				frmPreauthDetails.set("claimDetailVO",FormUtils.setFormValues("frmClaimInfo",
						preAuthDetailVO.getClaimDetailVO(),this,mapping,request));
			}//end of if(strActiveTab.equals(strClaims))
			frmPreauthDetails.set("caption", strCaption.toString());
			request.getSession().setAttribute("frmPreauthDetails",frmPreauthDetails);
			return this.getForward(strDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreAuthDetail));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to get the details of the selected record from web-board.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		log.debug("Inside PreauthDetailsAction doChangeWebBoard");
		return doView(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	/**
	 * This method is used to get the Review Information.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReviewInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside PreauthDetailsAction doReviewInfo ");
			DynaActionForm frmPreauthDetails = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO=null;
			ParallelPreAuthManager preAuthObject=null;
			ParallelCodingManager codingManagerObject=null;
			ParallelClaimManager claimObject=null;
			StringBuffer strCaption=new StringBuffer();
			
			codingManagerObject = this.getCodingManagerObject();
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strDetail="";
			if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strPreauth))
			{
				strDetail="preauthdetails";
				preAuthObject=this.getPreAuthManagerObject();
				preAuthDetailVO = (PreAuthDetailVO)FormUtils.getFormValues(frmPreauthDetails, this, mapping, request);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
				//call the business method here for updating the pre-auth
				preAuthDetailVO=preAuthObject.saveReview(preAuthDetailVO,"PAT","");
				//set the review information back to form
				frmPreauthDetails.set("eventSeqID",preAuthDetailVO.getEventSeqID().toString());
				frmPreauthDetails.set("reviewCount",preAuthDetailVO.getReviewCount().toString());
				frmPreauthDetails.set("requiredReviewCnt",preAuthDetailVO.getRequiredReviewCnt().toString());
				frmPreauthDetails.set("review",preAuthDetailVO.getReview().toString());
				frmPreauthDetails.set("eventName",preAuthDetailVO.getEventName().toString());
				
				// check whether user is having the permession for next Event.
				boolean blnReviewPermession=checkReviewPermession(preAuthDetailVO.getEventSeqID(),
						request,strCoding,strPreauth);
				
				//There is no permession for next level
				if(!blnReviewPermession || !(preAuthDetailVO.getEventName().contains(strCoding)))
				{
					request.setAttribute("cacheId","|"+CodingWebBoardHelper.getPreAuthSeqId(request)+"|");
					CodingWebBoardHelper.deleteWebBoardId(request,"SeqId");
					request.setAttribute("webboardinvoked","true");
					
					//After deleting if web board is null display the error message.
					if(CodingWebBoardHelper.checkWebBoardId(request)==null)
					{
						TTKException expTTK = new TTKException();
						//This attribute is used in JSP to show the error message.
						frmPreauthDetails.set("display","display");
						expTTK.setMessage("error.PreAuthorization.required");
						throw expTTK;
					}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
					else
					{
						// call the business layer to get the Pre-Auth detail
						preAuthDetailVO=codingManagerObject.getPreAuthDetail(CodingWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request));
						//check for conflict value
						frmPreauthDetails.initialize(mapping);
						frmPreauthDetails= (DynaActionForm)FormUtils.setFormValues("frmPreauthDetails",
								preAuthDetailVO, this, mapping, request);
						frmPreauthDetails.set("claimantDetailsVO", FormUtils.setFormValues(
								"frmClaimantInfo", preAuthDetailVO.getClaimantVO(), this,mapping, request));
						strCaption.append(" Edit");
						strCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
						frmPreauthDetails.set("caption",strCaption.toString());
						/*if(preAuthDetailVO.getPreAuthTypeID().equals("REG")){
							frmPreauthDetails.set("preAuthTypeDesc","Regular");
						}//end of if(preAuthDetailVO.getPreAuthTypeID().equals("REG"))
						else if(preAuthDetailVO.getPreAuthTypeID().equals("MRG")){
							frmPreauthDetails.set("preAuthTypeDesc","Manual-Regular");
						}//end of else if(preAuthDetailVO.getPreAuthTypeID().equals("MRG"))
						else{
							frmPreauthDetails.set("preAuthTypeDesc","Manual");
						}//end of else*/
						//keep the frmPolicyDetails in session scope
						request.getSession().setAttribute("frmPreauthDetails",frmPreauthDetails);
					}//end of else if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				}//end of if(!blnReviewPermession || !(preAuthDetailVO.getEventName().contains(strCoding)))
			}//end of if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strPreauth))
			else if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strClaims))
			{
				strDetail="claimsdetail";
				claimObject=this.getClaimManagerObject();
				preAuthDetailVO = (PreAuthDetailVO)FormUtils.getFormValues(frmPreauthDetails, this, mapping, request);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
				preAuthDetailVO=claimObject.saveReview(preAuthDetailVO,"CLM","");
				//set the review information back to form
				frmPreauthDetails.set("eventSeqID",preAuthDetailVO.getEventSeqID().toString());
				frmPreauthDetails.set("reviewCount",preAuthDetailVO.getReviewCount().toString());
				frmPreauthDetails.set("requiredReviewCnt",preAuthDetailVO.getRequiredReviewCnt().toString());
				frmPreauthDetails.set("review",preAuthDetailVO.getReview().toString());
				frmPreauthDetails.set("eventName",preAuthDetailVO.getEventName());
				// check whether user is having the permession for next Event.
				boolean blnReviewPermession=checkReviewPermession(preAuthDetailVO.getEventSeqID(),request,strCoding,strClaims);
				
				//there is no permession for next level
				if(!blnReviewPermession || !(preAuthDetailVO.getEventName().contains(strCoding)))
				{
					request.setAttribute("cacheId","|"+CodingClaimsWebBoardHelper.getClaimsSeqId(request)+"|");
					CodingClaimsWebBoardHelper.deleteWebBoardId(request);
					request.setAttribute("webboardinvoked","true");
					//After deleting if web board is null display the error message.
					if(CodingClaimsWebBoardHelper.checkWebBoardId(request)==null)
					{
						TTKException expTTK = new TTKException();
						//this attribute is used in JSP to show the error message.
						frmPreauthDetails.set("display","display");
						expTTK.setMessage("error.Claims.required");
						throw expTTK;
					}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
					else
					{
						// call the business layer to get the Pre-Auth detail
						preAuthDetailVO=codingManagerObject.getClaimDetail(CodingClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
						//check for conflict value
						frmPreauthDetails.initialize(mapping);
						frmPreauthDetails= (DynaActionForm)FormUtils.setFormValues("frmPreauthDetails",
								preAuthDetailVO, this, mapping, request);
						frmPreauthDetails.set("claimantDetailsVO", FormUtils.setFormValues(
								"frmClaimantInfo", preAuthDetailVO.getClaimantVO(), this,mapping, request));
						frmPreauthDetails.set("claimDetailVO",FormUtils.setFormValues("frmClaimInfo",
								preAuthDetailVO.getClaimDetailVO(),this,mapping,request));
						strCaption.append(" Edit");
						strCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
						frmPreauthDetails.set("caption",strCaption.toString());
						//keep the frmPolicyDetails in session scope
						request.getSession().setAttribute("frmPreauthDetails",frmPreauthDetails);
					}//end of else if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				}//end of if(!blnReviewPermession || !(preAuthDetailVO.getEventName().contains(strCoding)))
			}//end of else if(strActiveLink.equals(strCoding)&& strActiveSubLink.equals(strClaims))
			else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strPreauth))
			{
				strDetail="preauthcleanupdetails";
				preAuthDetailVO = (PreAuthDetailVO)FormUtils.getFormValues(frmPreauthDetails, this, mapping, request);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
				//call the business method here for updating the pre-auth
				preAuthDetailVO=codingManagerObject.savePreauthReview(preAuthDetailVO,"CCU");
				//set the review information back to form
				frmPreauthDetails.set("eventSeqID",preAuthDetailVO.getEventSeqID().toString());
				frmPreauthDetails.set("reviewCount",preAuthDetailVO.getReviewCount().toString());
				frmPreauthDetails.set("requiredReviewCnt",preAuthDetailVO.getRequiredReviewCnt().toString());
				frmPreauthDetails.set("review",preAuthDetailVO.getReview().toString());
				frmPreauthDetails.set("eventName",preAuthDetailVO.getEventName().toString());
				// check whether user is having the permession for next Event.
				boolean blnReviewPermession=checkReviewPermession(preAuthDetailVO.getEventSeqID(),
						request,strCodeCleanup,strPreauth);
				//There is no permession for next level
				if(!blnReviewPermession)
				{
					request.setAttribute("cacheId","|"+CodingWebBoardHelper.getPreAuthSeqId(request)+"|");
					CodingWebBoardHelper.deleteWebBoardId(request,"SeqId");
					request.setAttribute("webboardinvoked","true");
					//After deleting if web board is null display the error message.
					if(CodingWebBoardHelper.checkWebBoardId(request)==null)
					{
						TTKException expTTK = new TTKException();
						//This attribute is used in JSP to show the error message.
						frmPreauthDetails.set("display","display");
						expTTK.setMessage("error.PreAuthorization.required");
						throw expTTK;
					}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
					else
					{
						// call the business layer to get the Pre-Auth detail
						preAuthDetailVO=codingManagerObject.getCodeCleanupPreAuthDetail(CodingWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request));
						//check for conflict value
						frmPreauthDetails.initialize(mapping);
						frmPreauthDetails= (DynaActionForm)FormUtils.setFormValues("frmPreauthDetails",
								preAuthDetailVO, this, mapping, request);
						frmPreauthDetails.set("claimantDetailsVO", FormUtils.setFormValues(
								"frmClaimantInfo", preAuthDetailVO.getClaimantVO(), this,mapping, request));
						strCaption.append(" Edit");
						strCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
						frmPreauthDetails.set("caption",strCaption.toString());
						
						//keep the frmPolicyDetails in session scope
						request.getSession().setAttribute("frmPreauthDetails",frmPreauthDetails);
					}//end of else if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				}//end of if(!blnReviewPermession || !(preAuthDetailVO.getEventName().contains(strCoding)))
			}//end of if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strPreauth))
			else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strClaims))
			{
				strDetail="claimscleanupdetail";
				preAuthDetailVO = (PreAuthDetailVO)FormUtils.getFormValues(frmPreauthDetails, this, mapping, request);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
				preAuthDetailVO=codingManagerObject.saveClaimReview(preAuthDetailVO,"CCU");
				//set the review information back to form
				frmPreauthDetails.set("eventSeqID",preAuthDetailVO.getEventSeqID().toString());
				frmPreauthDetails.set("reviewCount",preAuthDetailVO.getReviewCount().toString());
				frmPreauthDetails.set("requiredReviewCnt",preAuthDetailVO.getRequiredReviewCnt().toString());
				frmPreauthDetails.set("review",preAuthDetailVO.getReview().toString());
				frmPreauthDetails.set("eventName",preAuthDetailVO.getEventName());
				// check whether user is having the permession for next Event.
				boolean blnReviewPermession=checkReviewPermession(preAuthDetailVO.getEventSeqID(),request,strCodeCleanup,strClaims);
				//there is no permession for next level
				if(!blnReviewPermession)
				{
					request.setAttribute("cacheId","|"+CodingClaimsWebBoardHelper.getClaimsSeqId(request)+"|");
					CodingClaimsWebBoardHelper.deleteWebBoardId(request);
					request.setAttribute("webboardinvoked","true");
					//After deleting if web board is null display the error message.
					if(CodingClaimsWebBoardHelper.checkWebBoardId(request)==null)
					{
						TTKException expTTK = new TTKException();
						//this attribute is used in JSP to show the error message.
						frmPreauthDetails.set("display","display");
						expTTK.setMessage("error.Claims.required");
						throw expTTK;
					}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
					else
					{
						// call the business layer to get the Pre-Auth detail
						preAuthDetailVO=codingManagerObject.getCodeCleanupClaimDetail(CodingClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
						//check for conflict value
						frmPreauthDetails.initialize(mapping);
						frmPreauthDetails= (DynaActionForm)FormUtils.setFormValues("frmPreauthDetails",
								preAuthDetailVO, this, mapping, request);
						frmPreauthDetails.set("claimantDetailsVO", FormUtils.setFormValues(
								"frmClaimantInfo", preAuthDetailVO.getClaimantVO(), this,mapping, request));
						frmPreauthDetails.set("claimDetailVO",FormUtils.setFormValues("frmClaimInfo",
								preAuthDetailVO.getClaimDetailVO(),this,mapping,request));
						strCaption.append(" Edit");
						strCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
						frmPreauthDetails.set("caption",strCaption.toString());
						//keep the frmPolicyDetails in session scope
						request.getSession().setAttribute("frmPreauthDetails",frmPreauthDetails);
					}//end of else if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				}//end of if(!blnReviewPermession || !(preAuthDetailVO.getEventName().contains(strCoding)))
			}//end of else if(strActiveLink.equals(strCodeCleanup)&& strActiveSubLink.equals(strClaims))
			return this.getForward(strDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreAuthDetail));
		}//end of catch(Exception exp)
	}//end of doReviewInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	/**
	 * Returns the ParallelPreAuthManager session object for invoking methods on it.
	 * @return ParallelPreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ParallelCodingManager getCodingManagerObject() throws TTKException
	{
		ParallelCodingManager codingManager = null;
		try
		{
			if(codingManager == null)
			{
				InitialContext ctx = new InitialContext();
				codingManager = (ParallelCodingManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelCodingManagerBean!com.ttk.business.dataentrycoding.ParallelCodingManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPreAuthDetail);
		}//end of catch
		return codingManager;
	}//end getPreAuthManagerObject()
	/**
	 * Methods checks whether user is having permession for the next Event.
	 * @param lngEventSeqId Long Event_Seq_Id.
	 * @param strSwitchType String SwitchType.
	 * @param request HttpServletRequest object.
	 * @return blnPermession boolean.
	 */
	private boolean checkReviewPermession(Long lngEventSeqId,HttpServletRequest request,String strActiveLink,String strActiveTab)
	{
		boolean blnPermession=false;
		WorkflowVO workFlowVO=null;
		ArrayList alEventId=null;
		
		//get the HashMap from UserSecurityProfile
		HashMap hmWorkFlow=((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getWorkFlowMap();
		if(strActiveLink.equals(strCoding))
		{
			if(strActiveTab.equals(strPreauth))
			{
				if(hmWorkFlow!=null && hmWorkFlow.containsKey(new Long(3)))
				{
					workFlowVO=(WorkflowVO)hmWorkFlow.get(new Long(3));//to get the work flow of pre-auth
				}//end of if(hmWorkFlow!=null && hmWorkFlow.containsKey(new Long(3)))
			}//end of if(strActiveTab.equals(strPre_Authorization))
			if(strActiveTab.equals(strClaims))
			{
				if(hmWorkFlow!=null && hmWorkFlow.containsKey(new Long(4)))
				{
					workFlowVO=(WorkflowVO)hmWorkFlow.get(new Long(4));//to get the work flow of claims
				}//end of if(hmWorkFlow!=null && hmWorkFlow.containsKey(new Long(4)))
			}//end of if(strActiveTab.equals(strClaims))	
		}//end of if(strActiveLink.equals(strCoding))
		else if((strActiveLink.equals(strCodeCleanup)&& strActiveTab.equals(strPreauth))||(strActiveLink.equals(strCodeCleanup)&& strActiveTab.equals(strClaims)))
		{
			if(hmWorkFlow!=null && hmWorkFlow.containsKey(new Long(5)))
			{
				workFlowVO=(WorkflowVO)hmWorkFlow.get(new Long(5));
			}//end of if(hmWorkFlow!=null && hmWorkFlow.containsKey(new Long(5)))
		}//end of else if(strActiveLink.equals(strCodeCleanup))
		//get the arrayList which is having event information of the particular user.
		if(workFlowVO!=null)
		{
			alEventId=workFlowVO.getEventVO();
		}//end of if(workFlowVO!=null)
		//compare the current policy EventSeqId with the User permession.
		if(alEventId!=null)
		{
			for(int i=0;i<alEventId.size();i++)
			{
				if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
				{
					blnPermession=true;
					break;
				}//end of if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
			}//end of for(int i=0;i<alEventId.size();i++)
		}//end of if(alEventId!=null)
		return blnPermession;
	}//end of checkReviewPermession(Long lngEventSeqId,HttpServletRequest request,String strActiveTab)
	
	/**
	 * Returns the ParallelPreAuthManager session object for invoking methods on it.
	 * @return ParallelPreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ParallelPreAuthManager getPreAuthManagerObject() throws TTKException
	{
		ParallelPreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (ParallelPreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelPreAuthManagerBean!com.ttk.business.dataentrypreauth.ParallelPreAuthManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPreAuthDetail);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()
	
	/**
	 * Returns the ParallelClaimManager session object for invoking methods on it.
	 * @return ParallelClaimManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ParallelClaimManager getClaimManagerObject() throws TTKException
	{
		ParallelClaimManager claimManager = null;
		try
		{
			if(claimManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimManager = (ParallelClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelClaimManagerBean!com.ttk.business.dataentryclaims.ParallelClaimManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPreAuthDetail);
		}//end of catch
		return claimManager;
	}//end getClaimManagerObject()
}//end of PreauthDetailsAction class
