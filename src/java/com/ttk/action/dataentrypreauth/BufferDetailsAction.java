/**
* @ (#) BufferDetailsAction.java Jul 3, 2006
* Project       : TTK HealthCare Services
* File          : BufferDetailsAction.java
* Author        : Chandrasekaran J
* Company       : Span Systems Corporation
* Date Created  : Jul 3, 2006

* @author       : Chandrasekaran J
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.dataentrypreauth;

import java.math.BigDecimal;
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
import com.ttk.business.preauth.PreAuthSupportManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.BufferVO;
import com.ttk.dto.preauth.BufferDetailVO;
import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for viewing the buffer information in pre-auth and claims flow.
 * This class also provides option for saving buffer information.
 */


public class BufferDetailsAction extends TTKAction
{
	private static Logger log = Logger.getLogger(BufferDetailsAction.class);

	//Setting the ActionForward
	private static final String strBufferDetails="bufferdetails";
	private static final String strClaimsBufferDetails="claimsbufferdetails";
	private static final String strClaimsSupportDoc="claimssupportdoc";
	private static final String strSupportDoc="supportdoc";

	//string for comparision
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";

	//Exception Message Identifier
	private static final String strBufferError="support";

	/**
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try{
			setLinks(request);
			log.debug("Inside BufferDetails Action doAdd");
			BufferDetailVO bufferDetailVO =new BufferDetailVO();
			String strForward="";
			String strClaimantname="";
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			String strEnrollmentID="";
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end fo else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			
							
			//Changes as per KOC 1216B change array length to 8
			Object[] objArrayResult = new Object[8];
			//Changes as per KOC 1216B
			if(strLink.equals("Claims"))
			{
				objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
										ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getActiveLink(request));
			}//end of if(strLink.equals("Claims"))
			else
			{
				objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
									PreAuthWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getActiveLink(request));
			}//end of else
			if(objArrayResult[0]!=null){
				bufferDetailVO.setAdminAuthTypeID(objArrayResult[0].toString());
			}//end of if(objArrayResult[0]!=null)
			if(objArrayResult[1]!=null){
				bufferDetailVO.setAvailBufferAmt((BigDecimal)objArrayResult[1]);
			}//end of if(objArrayResult[1]!=null)
			if(objArrayResult[2]!=null){
				bufferDetailVO.setBufferRemarks((String)objArrayResult[2]);				
			}//end of if(objArrayResult[2]!=null)
			//1216B ChangeRequest Modifications going to add
			if(objArrayResult[3]!=null){
				bufferDetailVO.setBufferMode(objArrayResult[3].toString());
			}//end of if(objArrayResult[3]!=null)
			if(objArrayResult[4]!=null){
			  bufferDetailVO.setHrInsurerBuffAmount((BigDecimal)objArrayResult[4]);
			 }//end of if(objArrayResult[4]!=null)
			if(objArrayResult[5]!=null){
				bufferDetailVO.setMemberBufferAmt((BigDecimal)objArrayResult[5]);
			}//end of if(objArrayResult[5]!=null)
			if(objArrayResult[6]!=null){
				bufferDetailVO.setUtilizedMemberBuffer((BigDecimal)objArrayResult[6]);
			}//end of if(objArrayResult[6]!=null)
			if(objArrayResult[7]!=null){
				bufferDetailVO.setBufferFamilyCap((BigDecimal)objArrayResult[7]);
			}//end of if(objArrayResult[7]!=null)
			
			
			//1216B ChangeRequest Modifications 				
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																							mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Add or Edit Buffer Details.
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
															HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doView");
			BufferDetailVO bufferDetailVO =null;
			BufferVO bufferVO=null;
			String strForward="";
			String strClaimantname="";
			String strEnrollmentID="";
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				bufferVO=(BufferVO)((TableData)request.getSession().getAttribute("tableData")).getData().get(
										Integer.parseInt(TTKCommon.checkNull(request.getParameter("rownum"))));
				if(strLink.equals("Claims"))
				{
					bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(bufferVO.getBufferDtlSeqID(),
																TTKCommon.getUserSeqId(request),"CLM");
				}//end of if(strLink.equals("Claims"))
				else
				{
					bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(bufferVO.getBufferDtlSeqID(),
																TTKCommon.getUserSeqId(request),"PAT");
				}//end of else
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																						mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Update the Buffer Details.
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
												HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doSave");
			BufferDetailVO bufferDetailVO =null;
			String strForward="";
			String strClaimantname="";
			String strEnrollmentID="";
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			Long lngBuffDetSeqId = null;
			bufferDetailVO=(BufferDetailVO)FormUtils.getFormValues(frmBufferDetails, this, mapping, request);
			if(bufferDetailVO.getStatusTypeID().equals("BAP"))
			{
				bufferDetailVO.setRejectedBy(null);
				bufferDetailVO.setRejectedDate(null);
				bufferDetailVO.setRejectedTime(null);
				bufferDetailVO.setRejectedDay(null);
			}//end of if(bufferDetailVO.getStatusTypeID().equals("BAP"))
			if(bufferDetailVO.getStatusTypeID().equals("BRJ"))
			{
				bufferDetailVO.setApprovedAmt(null);
				bufferDetailVO.setApprovedBy(null);
				bufferDetailVO.setApprovedDate(null);
				bufferDetailVO.setApprovedTime(null);
				bufferDetailVO.setApprovedDay(null);
			}//end of if(bufferDetailVO.getStatusTypeID().equals("BRJ"))
			if(strLink.equals(strClaims))
			{
				bufferDetailVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
				bufferDetailVO.setPreAuthSeqID(null);
				bufferDetailVO.setPolicySeqID(ClaimsWebBoardHelper.getPolicySeqId(request));
				bufferDetailVO.setMemberSeqID(ClaimsWebBoardHelper.getMemberSeqId(request));
			}//end of if(strLink.equals(strClaims))
			else
			{
				bufferDetailVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				bufferDetailVO.setClaimSeqID(null);
				bufferDetailVO.setPolicySeqID(PreAuthWebBoardHelper.getPolicySeqId(request));
				bufferDetailVO.setMemberSeqID(PreAuthWebBoardHelper.getMemberSeqId(request));
			}//end of else
			bufferDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			if(strLink.equals(strClaims))
			{
				lngBuffDetSeqId=preAuthSupportManagerObject.saveBufferDetail(bufferDetailVO,"CLM");
			}//end of if(strLink.equals(strClaims))
			else
			{
				lngBuffDetSeqId=preAuthSupportManagerObject.saveBufferDetail(bufferDetailVO,"PAT");
			}//end of else
			if(lngBuffDetSeqId>0)
			{
				if(frmBufferDetails.get("bufferDtlSeqID")!=null && !frmBufferDetails.get("bufferDtlSeqID").equals(""))
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(lngUpdate>0)
			bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(lngBuffDetSeqId,
																	TTKCommon.getUserSeqId(request),"Preauthorization");
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																		mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Send the request when the user clicks SendRequest button.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSendRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doSendRequest");
			BufferDetailVO bufferDetailVO =null;
			String strForward="";
			String strClaimantname="";
			String strEnrollmentID="";
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			Long lngBuffDetSeqId = null;
			bufferDetailVO=(BufferDetailVO)FormUtils.getFormValues(frmBufferDetails, this, mapping, request);
			if(bufferDetailVO.getStatusTypeID().equals("BAP"))
			{
				bufferDetailVO.setRejectedBy(null);
				bufferDetailVO.setRejectedDate(null);
				bufferDetailVO.setRejectedTime(null);
				bufferDetailVO.setRejectedDay(null);
			}//end of if(bufferDetailVO.getStatusTypeID().equals("BAP"))
			if(bufferDetailVO.getStatusTypeID().equals("BRJ"))
			{
				bufferDetailVO.setApprovedAmt(null);
				bufferDetailVO.setApprovedBy(null);
				bufferDetailVO.setApprovedDate(null);
				bufferDetailVO.setApprovedTime(null);
				bufferDetailVO.setApprovedDay(null);
			}//end of if(bufferDetailVO.getStatusTypeID().equals("BRJ"))
			bufferDetailVO.setStatusTypeID("BSR");
			if(strLink.equals(strClaims))
			{
				bufferDetailVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
				bufferDetailVO.setPreAuthSeqID(null);
				bufferDetailVO.setPolicySeqID(ClaimsWebBoardHelper.getPolicySeqId(request));
				bufferDetailVO.setMemberSeqID(ClaimsWebBoardHelper.getMemberSeqId(request));
			}//end of if(strLink.equals(strClaims))
			else
			{
				bufferDetailVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				bufferDetailVO.setClaimSeqID(null);
				bufferDetailVO.setPolicySeqID(PreAuthWebBoardHelper.getPolicySeqId(request));
				bufferDetailVO.setMemberSeqID(PreAuthWebBoardHelper.getMemberSeqId(request));
			}//end of else
			bufferDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			if(strLink.equals(strClaims))
			{
				lngBuffDetSeqId=preAuthSupportManagerObject.saveBufferDetail(bufferDetailVO,"CLM");
			}//end of if(strLink.equals(strClaims))
			else
			{
				lngBuffDetSeqId=preAuthSupportManagerObject.saveBufferDetail(bufferDetailVO,"PAT");
			}//end of else
			if(lngBuffDetSeqId>0)
			{
				if(frmBufferDetails.get("bufferDtlSeqID")!=null && !frmBufferDetails.get("bufferDtlSeqID").equals(""))
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(lngUpdate>0)
			bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(lngBuffDetSeqId,
																	TTKCommon.getUserSeqId(request),"Preauthorization");
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																		mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doSendRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to Reset the Buffer Details.
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
											HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doReset");
			BufferDetailVO bufferDetailVO =null;
			String strForward="";
			String strClaimantname="";
			String strEnrollmentID="";
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			if(!(TTKCommon.checkNull(frmBufferDetails.getString("bufferDtlSeqID")).equals("")))
			{
				if(strLink.equals("Claims"))
				{
					bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(TTKCommon.getLong(
								frmBufferDetails.getString("bufferDtlSeqID")),TTKCommon.getUserSeqId(request),"CLM");
				}//end of if(strLink.equals("Claims"))
				else
				{
					bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(TTKCommon.getLong(
							frmBufferDetails.getString("bufferDtlSeqID")),TTKCommon.getUserSeqId(request),"PAT");
				}//end of else
			}//end of if(!(TTKCommon.checkNull(frmBufferDetails.getString("bufferDtlSeqID")).equals("")))
			else
			{
				bufferDetailVO=new BufferDetailVO();
				//Object[] objArrayResult = new Object[3];
			//	Modified as per KOC 1216B Change request
				Object[] objArrayResult = new Object[8];
          		  //	Modified as per KOC 1216B Change request 
				if(strLink.equals("Claims"))
				{
					objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
					ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getActiveLink(request));
				}//end of if(strLink.equals("Claims"))
				else
				{
					objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
					PreAuthWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getActiveLink(request));
				}//end of else
				if(objArrayResult[0]!=null){
					bufferDetailVO.setAdminAuthTypeID(objArrayResult[0].toString());
				}//end of if(objArrayResult[0]!=null)
				if(objArrayResult[1]!=null){
					bufferDetailVO.setAvailBufferAmt((BigDecimal)objArrayResult[1]);
				}//end of if(objArrayResult[1]!=null)
				if(objArrayResult[2]!=null){
					bufferDetailVO.setBufferRemarks((String)objArrayResult[2]);				
				}//end of if(objArrayResult[2]!=null)
				//1216B ChangeRequest Modifications going to add
				
				if(objArrayResult[3]!=null){
					bufferDetailVO.setBufferMode(objArrayResult[3].toString());
				}//end of if(objArrayResult[3]!=null)
				if(objArrayResult[4]!=null){
				  bufferDetailVO.setHrInsurerBuffAmount((BigDecimal)objArrayResult[4]);
				 }//end of if(objArrayResult[4]!=null)
				if(objArrayResult[5]!=null){
					bufferDetailVO.setMemberBufferAmt((BigDecimal)objArrayResult[5]);
				}//end of if(objArrayResult[5]!=null)
				if(objArrayResult[6]!=null){
					bufferDetailVO.setUtilizedMemberBuffer((BigDecimal)objArrayResult[6]);
				}//end of if(objArrayResult[6]!=null)
				if(objArrayResult[7]!=null){
					bufferDetailVO.setBufferFamilyCap((BigDecimal)objArrayResult[7]);
				}//end of if(objArrayResult[7]!=null)
					
				//1216B ChangeRequest Modifications 
			}//end of else
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																					mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
/*
 * 
 * method is utility method to make 0.00 when values are null from back end
 * 
 * 
 */
	
	 private void formValue(BufferDetailVO bufferDetailVO,DynaActionForm frmBufferDetails,HttpServletRequest request) throws TTKException{
		 
		/* if(bufferDetailVO.getRequestedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("requestedAmt","0.00");
	    	}//end of if(bufferDetailVO.getRequestedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
*/		 
		 if(bufferDetailVO.getMemberBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("memberBufferAmt","0.00");
	    	}//end of if(bufferDetailVO.getMemberBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
		 
		 if(bufferDetailVO.getUtilizedMemberBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("utilizedMemberBuffer","0.00");
	    	}//end of if(bufferDetailVO.getUtilizedMemberBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
		 
		 if(bufferDetailVO.getBufferFamilyCap().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("bufferFamilyCap","0.00");
	    	}//end of if(bufferDetailVO.getBufferFamilyCap().compareTo(TTKCommon.getBigDecimal("0"))==0)
		 
		 if(bufferDetailVO.getHrInsurerBuffAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("hrInsurerBuffAmount","0.00");
	    	}//end of if(bufferDetailVO.getHrInsurerBuffAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
		 
		 if(bufferDetailVO.getAvailBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("availBufferAmt","0.00");
	    	}//end of if(bufferDetailVO.getAvailBufferAmt.compareTo(TTKCommon.getBigDecimal("0"))==0)
		 
		/* if(bufferDetailVO.getApprovedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("availBufferAmt","0.00");
	    	}//end of if(bufferDetailVO.getAvailBufferAmt.compareTo(TTKCommon.getBigDecimal("0"))==0)
*/
		 }

	
/**
	 * This method is used to close
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
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doClose");
			String strSupport="";
			String strClaimantname="";
			String strEnrollmentID="";
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strSupport=strSupportDoc;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strSupport=strClaimsSupportDoc;
			}// end of else if(strLink.equals(strClaims))
			TableData tableData = TTKCommon.getTableData(request);
			if(tableData.getSearchData()!= null&& tableData.getSearchData().size()>0)
			{
				ArrayList alSupportDoc = preAuthSupportManagerObject.getSupportBufferList(tableData.getSearchData());
				tableData.setData(alSupportDoc, "Cancel");
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			return this.getForward(strSupport, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthSupportManager getPreAuthSupportManagerObject() throws TTKException
	{
		PreAuthSupportManager preAuthSupportManager = null;
		try
		{
			if(preAuthSupportManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthSupportManager = (PreAuthSupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthSupportManagerBean!com.ttk.business.preauth.PreAuthSupportManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBufferError);
		}//end of catch
		return preAuthSupportManager;
	}//end getPreAuthSupportManagerObject()
}//end of BufferDetailsAction