/**
 * @ (#) PreAuthGeneralAction.java May 10, 2006
 * Project       : TTK HealthCare Services
 * File          : PreAuthGeneralAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : May 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.dataentrypreauth;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.dataentryclaims.ParallelClaimManager;
import com.ttk.business.dataentrypreauth.ParallelPreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.claims.ClaimDetailVO;
import com.ttk.dto.claims.HospitalizationDetailVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.AdditionalDetailVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is reused for adding pre-auth/claims in pre-auth and claims flow.
 */

public class PreAuthGeneralAction extends TTKAction {

	private static Logger log = Logger.getLogger(PreAuthGeneralAction.class);

	private static final String strPreAuthDetail = "PreAuthDetails";

	// declare forward paths
	private static final String strEnrollmentlist = "dataentryenrollmentlist";
	private static final String strInsurancelist = "dataentryinsurancelist";
	private static final String strPolicylist = "policylist";
	private static final String strCorporatelist = "corporatelist";
	private static final String strHospitallist = "dataentryhospitallist";
	private static final String strSumenhancementlist = "sumenhancementlist";

	private static final String strPre_Authorization = "Pre-Authorization";
	private static final String strClaims = "DataEntryClaims";

	/**
	 * This method is used to navigate to detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doView ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			ParallelPreAuthManager preAuthObject = null;
			ParallelClaimManager claimObject = null;
			PreAuthDetailVO preAuthDetailVO = null;
			AdditionalDetailVO additionalDetailVO = null;
			PreAuthVO preauthVO = null;
			ClaimDetailVO claimDetailVO = null;
			StringBuffer strCaption = new StringBuffer();
			ArrayList alPrevClaimList = null;
			ArrayList<Object> alClaimList = null;
			String strActiveLink = TTKCommon.getActiveLink(request);
			String strFlowType = "";
			String strDetail = "";
			String strPreAuthFlow = "PRE";
			String strClaimFlow = "CLM";
			if (strActiveLink.equals(strPre_Authorization)) {
				strFlowType = strPreAuthFlow;
				strDetail = "preauthdetail";
				preAuthObject = this.getPreAuthManagerObject();
			}// end of if(strActiveLink.equals(strPre_Authorization))
			if (strActiveLink.equals(strClaims)) {
				strFlowType = strClaimFlow;
				strDetail = "dataentryclaimsdetail";
				claimObject = this.getClaimManagerObject();
			}// end of if(strActiveLink.equals(strClaims))
			strCaption.append(" Edit");
			if (strActiveLink.equals(strPre_Authorization))// if it is from
															// pre-auth flow
			{
				// check if user trying to hit the tab directly with out
				// selecting the pre-auth
				if (PreAuthWebBoardHelper.checkWebBoardId(request) == null) {
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.required");
					throw expTTK;
				}// end of
					// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				/*
				 * if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"
				 * )) { TTKException expTTK = new TTKException();
				 * frmPreAuthGeneral.set("display","display");
				 * expTTK.setMessage("error.PreAuthorization.codingflow"); throw
				 * expTTK; }//end of
				 * if(PreAuthWebBoardHelper.getCodingReviewYN(request
				 * ).equals("Y"))
				 */
				preauthVO = new PreAuthVO();
				preauthVO.setMemberSeqID(PreAuthWebBoardHelper
						.getMemberSeqId(request));
				preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper
						.getPreAuthSeqId(request));
				preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper
						.getEnrollmentDetailId(request));
				preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				// call the business layer to get the Pre-Auth detail
				preAuthDetailVO = preAuthObject.getPreAuthDetail(preauthVO,
						PreAuthWebBoardHelper.getPreAuthStatus(request));
				strFlowType = strPreAuthFlow;
				strCaption
						.append(" [ "
								+ PreAuthWebBoardHelper
										.getClaimantName(request) + " ]");
				if (PreAuthWebBoardHelper.getEnrollmentId(request) != null
						&& (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(
								request).trim())))
					strCaption.append(" [ "
							+ PreAuthWebBoardHelper.getEnrollmentId(request)
							+ " ]");
			}// end of if(strActiveLink.equals(strPre_Authorization))
			if (strActiveLink.equals(strClaims))// if it is from claims flow
			{
				// check if user trying to hit the tab directly with out
				// selecting the claim
				if (ClaimsWebBoardHelper.checkWebBoardId(request) == null) {
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}// end of
					// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				/*
				 * if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y")
				 * ) { TTKException expTTK = new TTKException();
				 * expTTK.setMessage("error.Claims.codingflow"); throw expTTK;
				 * }//end of
				 * if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals
				 * ("Y"))
				 */
				alClaimList = new ArrayList<Object>();
				alClaimList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alClaimList.add(ClaimsWebBoardHelper
						.getClmEnrollDetailSeqId(request));
				alClaimList.add(ClaimsWebBoardHelper.getMemberSeqId(request));
				alClaimList.add(TTKCommon.getUserSeqId(request));
				// call the business layer to get the Claim detail
				preAuthDetailVO = claimObject.getClaimDetail(alClaimList);
				if (preAuthDetailVO == null) {
					request.setAttribute("cacheId",
							"|" + ClaimsWebBoardHelper.getClaimsSeqId(request)
									+ "|");
					ClaimsWebBoardHelper.deleteWebBoardId(request, "SeqId");
					request.setAttribute("webboardinvoked", "true");
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}// end of if(preAuthDetailVO == null)
				if (preAuthDetailVO.getPrevClaimList() != null) {
					alPrevClaimList = preAuthDetailVO.getPrevClaimList();
				}// end of if(preAuthDetailVO.getPrevClaimList()!=null)
				strCaption.append(" [ "
						+ ClaimsWebBoardHelper.getClaimantName(request) + " ]");
				if (ClaimsWebBoardHelper.getEnrollmentId(request) != null
						&& (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(
								request).trim())))
					strCaption.append(" [ "
							+ ClaimsWebBoardHelper.getEnrollmentId(request)
							+ " ]");
			}// end of if(strActiveTab.equals(strClaims))
			if (!preAuthDetailVO.getPreAuthHospitalVO()
					.getEmpanelStatusTypeID().equals("")
					&& !preAuthDetailVO.getPreAuthHospitalVO()
							.getEmpanelStatusTypeID().equals("EMP")) {
				preAuthDetailVO.getPreAuthHospitalVO().setStatusDisYN("Y");
			}// end of
				// if(!preAuthDetailVO.getPreAuthHospitalVO().getEmpanelStatusTypeID().equals("EMP"))
			if (preAuthDetailVO.getEnrolChangeMsg() != null
					&& preAuthDetailVO.getEnrolChangeMsg() != "") {
				request.setAttribute("enrollmentChange",
						"message.enrollmentChange");
			}
			frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
					"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
					request);
			frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils.setFormValues(
					"frmPreAuthHospital",
					preAuthDetailVO.getPreAuthHospitalVO(), this, mapping,
					request));
			frmPreAuthGeneral.set("claimantDetailsVO", FormUtils.setFormValues(
					"frmClaimantDetails", preAuthDetailVO.getClaimantVO(),
					this, mapping, request));
			if (strActiveLink.equals(strPre_Authorization)) {
				frmPreAuthGeneral.set("additionalDetailVO", FormUtils
						.setFormValues("frmAdditionalDetail",
								preAuthDetailVO.getAdditionalDetailVO(), this,
								mapping, request));
				if (preAuthDetailVO.getPreAuthTypeID().equals("REG")) {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Regular");
				}// end of if(preAuthDetailVO.getPreAuthTypeID().equals("REG"))
				else if (preAuthDetailVO.getPreAuthTypeID().equals("MRG")) {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Manual-Regular");
				}// end of else
					// if(preAuthDetailVO.getPreAuthTypeID().equals("MRG")){
				else {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");
				}// end of else
				claimDetailVO = new ClaimDetailVO();
				frmPreAuthGeneral.set("claimDetailVO", FormUtils
						.setFormValues("frmClaimDetail", claimDetailVO, this,
								mapping, request));
			}// end of if(strActiveLink.equals(strPre_Authorization))
			if (strActiveLink.equals(strClaims)) {
				// to make claimsubtypeid as hospitalization when claim in NHCP
				if (preAuthDetailVO.getClaimDetailVO().getClaimTypeID()
						.equals("CNH")) {
					preAuthDetailVO.getClaimDetailVO().setClaimSubTypeID("CSH");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
					// Added as per KOC 1285 Change Request
				if (!preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
						.equalsIgnoreCase("CSD")) {
					preAuthDetailVO.getClaimDetailVO().setDomicilaryReason("");
					preAuthDetailVO.getClaimDetailVO().setDoctorCertificateYN(
							"");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
				if (preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
						.equalsIgnoreCase("CSD")) {
					if (preAuthDetailVO.getClaimDetailVO()
							.getDoctorCertificateYN().equalsIgnoreCase("Y"))
						request.setAttribute("doctorCertificateYN", "Y");
					else
						request.setAttribute("doctorCertificateYN", "N");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CSD"))
				// Added as per KOC 1285 Change Request

				frmPreAuthGeneral.set("claimDetailVO", FormUtils.setFormValues(
						"frmClaimDetail", preAuthDetailVO.getClaimDetailVO(),
						this, mapping, request));
				frmPreAuthGeneral.set("hmPrevHospList",
						preAuthDetailVO.getPrevHospDetails());
				additionalDetailVO = new AdditionalDetailVO();
				frmPreAuthGeneral.set("additionalDetailVO", FormUtils
						.setFormValues("frmAdditionalDetail",
								additionalDetailVO, this, mapping, request));
			}// end of if(strActiveLink.equals(strClaims))
				// check for conflict value
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& preAuthDetailVO.getDiscPresentYN().equals("Y"));
			frmPreAuthGeneral.set("flowType", strFlowType.toString());// set the
																		// value
																		// from
																		// which
																		// flow
																		// ur
																		// are.
			frmPreAuthGeneral.set("caption", strCaption.toString());
			frmPreAuthGeneral.set("alPrevClaimList", alPrevClaimList);
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthGeneral",
					frmPreAuthGeneral);
			this.documentViewer(request, preAuthDetailVO);
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to get the details of the selected record from
	 * web-board. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doChangeWebBoard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Inside PreAuthGenealAction doChangeWebBoard");
		return doView(mapping, form, request, response);
	}// end of doChangeWebBoard(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method will save the Review Information and Workflow will change to
	 * Coding Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doRevert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGeneralAction doRevert");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			AdditionalDetailVO additionalDetailVO = null;
			PreAuthVO preauthVO = null;
			ClaimDetailVO claimDetailVO = null;
			// ClaimantVO claimantVO=null;
			String strActiveLink = TTKCommon.getActiveLink(request);
			ParallelPreAuthManager preAuthObject = this
					.getPreAuthManagerObject();
			ParallelClaimManager claimObject = this.getClaimManagerObject();
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			StringBuffer strCaption = new StringBuffer();
			String strFlowType = "";
			String strPreAuthFlow = "PRE";
			String strClaimFlow = "CLM";
			ArrayList alPrevClaimList = null;
			ArrayList<Object> alClaimList = null;
			String strDetail = "";
			if (strActiveLink.equals(strPre_Authorization)) {
				strFlowType = strPreAuthFlow;
				strDetail = "preauthdetail";
			}// end of if(strActiveLink.equals(strPre_Authorization))
			if (strActiveLink.equals(strClaims)) {
				strFlowType = strClaimFlow;
				strDetail = "dataentryclaimsdetail";
			}// end of if(strActiveLink.equals(strClaims))
			if (strActiveLink.equals(strPre_Authorization)) {
				preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
						frmPreAuthGeneral, this, mapping, request);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
																				// Id
				// call the business method here for updating the pre-auth
				preAuthDetailVO = preAuthObject.returnToCoding(preAuthDetailVO,
						"PAT");
				// set the review information back to form
				frmPreAuthGeneral.set("eventSeqID", preAuthDetailVO
						.getEventSeqID().toString());
				frmPreAuthGeneral.set("reviewCount", preAuthDetailVO
						.getReviewCount().toString());
				frmPreAuthGeneral.set("requiredReviewCnt", preAuthDetailVO
						.getRequiredReviewCnt().toString());
				frmPreAuthGeneral.set("review", preAuthDetailVO.getReview()
						.toString());
				frmPreAuthGeneral.set("eventName", preAuthDetailVO
						.getEventName().toString());
				// check whether user is having the permession for next Event.
				boolean blnReviewPermession = checkReviewPermession(
						preAuthDetailVO.getEventSeqID(), request,
						strPre_Authorization);
				if (!blnReviewPermession)// there is no permession for next
											// level
				{
					request.setAttribute(
							"cacheId",
							"|"
									+ PreAuthWebBoardHelper
											.getPreAuthSeqId(request) + "|");
					PreAuthWebBoardHelper.deleteWebBoardId(request, "SeqId");
					request.setAttribute("webboardinvoked", "true");
					// After deleting if web board is null display the error
					// message.
					if (PreAuthWebBoardHelper.checkWebBoardId(request) == null) {
						TTKException expTTK = new TTKException();
						// this attribute is used in JSP to show the error
						// message.
						frmPreAuthGeneral.set("display", "display");
						expTTK.setMessage("error.PreAuthorization.required");
						throw expTTK;
					}// end of
						// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
					else {
						// Fetch the next pre-auth information which is the
						// web-board.
						preauthVO = new PreAuthVO();
						preauthVO.setMemberSeqID(PreAuthWebBoardHelper
								.getMemberSeqId(request));
						preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper
								.getPreAuthSeqId(request));
						preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper
								.getEnrollmentDetailId(request));
						preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
						// call the business layer to get the Pre-Auth detail
						preAuthDetailVO = preAuthObject
								.getPreAuthDetail(preauthVO,
										PreAuthWebBoardHelper
												.getPreAuthStatus(request));
						// check for conflict value
						toolBar.getConflictIcon().setVisible(
								toolBar.getConflictIcon().isVisible()
										&& preAuthDetailVO.getDiscPresentYN()
												.equals("Y"));
						frmPreAuthGeneral.initialize(mapping);
						if (!preAuthDetailVO.getPreAuthHospitalVO()
								.getEmpanelStatusTypeID().equals("")
								&& !preAuthDetailVO.getPreAuthHospitalVO()
										.getEmpanelStatusTypeID().equals("EMP")) {
							preAuthDetailVO.getPreAuthHospitalVO()
									.setStatusDisYN("Y");
						}// end of
							// if(!preAuthDetailVO.getPreAuthHospitalVO().getEmpanelStatusTypeID().equals("EMP"))
						frmPreAuthGeneral = (DynaActionForm) FormUtils
								.setFormValues("frmPreAuthGeneral",
										preAuthDetailVO, this, mapping, request);
						frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
								.setFormValues("frmPreAuthHospital",
										preAuthDetailVO.getPreAuthHospitalVO(),
										this, mapping, request));
						frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
								.setFormValues("frmClaimantDetails",
										preAuthDetailVO.getClaimantVO(), this,
										mapping, request));
						frmPreAuthGeneral
								.set("additionalDetailVO",
										FormUtils
												.setFormValues(
														"frmAdditionalDetail",
														preAuthDetailVO
																.getAdditionalDetailVO(),
														this, mapping, request));
						claimDetailVO = new ClaimDetailVO();
						frmPreAuthGeneral.set("claimDetailVO", FormUtils
								.setFormValues("frmClaimDetail", claimDetailVO,
										this, mapping, request));
						strCaption.append(" Edit");
						strCaption.append(" [ "
								+ PreAuthWebBoardHelper
										.getClaimantName(request) + " ]");
						if (PreAuthWebBoardHelper.getEnrollmentId(request) != null
								&& (!"".equals(PreAuthWebBoardHelper
										.getEnrollmentId(request).trim())))
							strCaption.append(" [ "
									+ PreAuthWebBoardHelper
											.getEnrollmentId(request) + " ]");
						frmPreAuthGeneral.set("caption", strCaption.toString());

						if (preAuthDetailVO.getPreAuthTypeID().equals("REG")) {
							frmPreAuthGeneral.set("preAuthTypeDesc", "Regular");
						}// end of
							// if(preAuthDetailVO.getPreAuthTypeID().equals("REG"))
						else if (preAuthDetailVO.getPreAuthTypeID().equals(
								"MRG")) {
							frmPreAuthGeneral.set("preAuthTypeDesc",
									"Manual-Regular");
						}// end of else
							// if(preAuthDetailVO.getPreAuthTypeID().equals("MRG")){
						else {
							frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");
						}// end of else
						frmPreAuthGeneral.set("flowType",
								strFlowType.toString());// set the value from
														// which flow ur are.
						// keep the frmPolicyDetails in session scope
						request.getSession().setAttribute("frmPreAuthGeneral",
								frmPreAuthGeneral);
						this.documentViewer(request, preAuthDetailVO);
					}// end of else
						// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				}// end of if(!blnReviewPermession)
				else {

					preauthVO = new PreAuthVO();
					preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper
							.getPreAuthSeqId(request));
					preauthVO.setEnrollmentID(PreAuthWebBoardHelper
							.getEnrollmentId(request));
					preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper
							.getEnrollmentDetailId(request));
					preauthVO.setPolicySeqID(PreAuthWebBoardHelper
							.getPolicySeqId(request));
					preauthVO.setMemberSeqID(PreAuthWebBoardHelper
							.getMemberSeqId(request));
					preauthVO.setClaimantName(PreAuthWebBoardHelper
							.getClaimantName(request));
					preauthVO.setBufferAllowedYN(PreAuthWebBoardHelper
							.getBufferAllowedYN(request));
					preauthVO.setShowBandYN(PreAuthWebBoardHelper
							.getShowBandYN(request));
					preauthVO.setCoding_review_yn(preAuthDetailVO
							.getCoding_review_yn());
					preauthVO.setPreAuthNo(PreAuthWebBoardHelper
							.getWebBoardDesc(request));
					this.addToWebBoard(preauthVO, request,
							PreAuthWebBoardHelper.getPreAuthStatus(request));
				}// end of else
				/*
				 * if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"
				 * )) {
				 * request.setAttribute("cacheId","|"+PreAuthWebBoardHelper.
				 * getPreAuthSeqId(request)+"|");
				 * PreAuthWebBoardHelper.deleteWebBoardId(request,"SeqId");
				 * request.setAttribute("webboardinvoked","true");
				 * frmPreAuthGeneral.set("display","display");
				 * if(PreAuthWebBoardHelper.checkWebBoardId(request)==null) {
				 * TTKException expTTK = new TTKException(); //this attribute is
				 * used in JSP to show the error message.
				 * expTTK.setMessage("error.PreAuthorization.required"); throw
				 * expTTK; }//end of
				 * if(PreAuthWebBoardHelper.checkWebBoardId(request)==null) else
				 * { TTKException expTTK = new TTKException();
				 * expTTK.setMessage("error.PreAuthorization.codingflow"); throw
				 * expTTK; }//end of else }//end of
				 * if(PreAuthWebBoardHelper.getCodingReviewYN
				 * (request).equals("Y"))
				 */
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveLink.equals(strClaims)) {
				log.info("Inside Claims flow");
				preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
						frmPreAuthGeneral, this, mapping, request);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
																				// Id
				preAuthDetailVO.setClaimSeqID(ClaimsWebBoardHelper
						.getClaimsSeqId(request));
				preAuthDetailVO = preAuthObject.returnToCoding(preAuthDetailVO,
						"CLM");
				// set the review information back to form
				frmPreAuthGeneral.set("eventSeqID", preAuthDetailVO
						.getEventSeqID().toString());
				frmPreAuthGeneral.set("reviewCount", preAuthDetailVO
						.getReviewCount().toString());
				frmPreAuthGeneral.set("requiredReviewCnt", preAuthDetailVO
						.getRequiredReviewCnt().toString());
				frmPreAuthGeneral.set("review", preAuthDetailVO.getReview()
						.toString());
				frmPreAuthGeneral.set("eventName",
						preAuthDetailVO.getEventName());
				// check whether user is having the permession for next Event.
				boolean blnReviewPermession = checkReviewPermession(
						preAuthDetailVO.getEventSeqID(), request, strClaims);
				if (!blnReviewPermession)// no permession is there
				{
					request.setAttribute("cacheId",
							"|" + ClaimsWebBoardHelper.getClaimsSeqId(request)
									+ "|");
					ClaimsWebBoardHelper.deleteWebBoardId(request, "SeqId");
					request.setAttribute("webboardinvoked", "true");
					if (ClaimsWebBoardHelper.checkWebBoardId(request) == null) {
						TTKException expTTK = new TTKException();
						expTTK.setMessage("error.Claims.required");
						// this attribute is used in JSP to show the error
						// message.
						frmPreAuthGeneral.set("display", "display");
						throw expTTK;
					}// end of
						// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
					else {
						// Fetch the next pre-auth information which is the
						// web-board.
						preauthVO = new PreAuthVO();
						alClaimList = new ArrayList<Object>();
						alClaimList.add(ClaimsWebBoardHelper
								.getClaimsSeqId(request));
						alClaimList.add(ClaimsWebBoardHelper
								.getClmEnrollDetailSeqId(request));
						alClaimList.add(ClaimsWebBoardHelper
								.getMemberSeqId(request));
						alClaimList.add(TTKCommon.getUserSeqId(request));
						// call the business layer to get the Claim detail
						preAuthDetailVO = claimObject
								.getClaimDetail(alClaimList);
						if (preAuthDetailVO.getPrevClaimList() != null) {
							alPrevClaimList = preAuthDetailVO
									.getPrevClaimList();
						}// end of if(preAuthDetailVO.getPrevClaimList()!=null)
						strCaption.append(" [ "
								+ ClaimsWebBoardHelper.getClaimantName(request)
								+ " ]");
						if (ClaimsWebBoardHelper.getEnrollmentId(request) != null
								&& (!"".equals(ClaimsWebBoardHelper
										.getEnrollmentId(request).trim()))) {
							strCaption.append(" [ "
									+ ClaimsWebBoardHelper
											.getEnrollmentId(request) + " ]");
						}// end of
							// if(ClaimsWebBoardHelper.getEnrollmentId(request)
							// != null &&
							// (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						frmPreAuthGeneral.initialize(mapping);
						if (!preAuthDetailVO.getPreAuthHospitalVO()
								.getEmpanelStatusTypeID().equals("")
								&& !preAuthDetailVO.getPreAuthHospitalVO()
										.getEmpanelStatusTypeID().equals("EMP")) {
							preAuthDetailVO.getPreAuthHospitalVO()
									.setStatusDisYN("Y");
						}// end of
							// if(!preAuthDetailVO.getPreAuthHospitalVO().getEmpanelStatusTypeID().equals("EMP"))
						frmPreAuthGeneral = (DynaActionForm) FormUtils
								.setFormValues("frmPreAuthGeneral",
										preAuthDetailVO, this, mapping, request);
						frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
								.setFormValues("frmPreAuthHospital",
										preAuthDetailVO.getPreAuthHospitalVO(),
										this, mapping, request));
						frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
								.setFormValues("frmClaimantDetails",
										preAuthDetailVO.getClaimantVO(), this,
										mapping, request));
						frmPreAuthGeneral.set("claimDetailVO", FormUtils
								.setFormValues("frmClaimDetail",
										preAuthDetailVO.getClaimDetailVO(),
										this, mapping, request));
						additionalDetailVO = new AdditionalDetailVO();
						frmPreAuthGeneral.set("additionalDetailVO", FormUtils
								.setFormValues("frmAdditionalDetail",
										additionalDetailVO, this, mapping,
										request));
						frmPreAuthGeneral.set("hmPrevHospList",
								preAuthDetailVO.getPrevHospDetails());
						toolBar.getConflictIcon().setVisible(
								toolBar.getConflictIcon().isVisible()
										&& preAuthDetailVO.getDiscPresentYN()
												.equals("Y"));
						frmPreAuthGeneral.set("flowType",
								strFlowType.toString());// set the value from
														// which flow ur are.
						frmPreAuthGeneral.set("caption", strCaption.toString());
						frmPreAuthGeneral.set("alPrevClaimList",
								alPrevClaimList);
						// keep the frmPolicyDetails in session scope
						request.getSession().setAttribute("frmPreAuthGeneral",
								frmPreAuthGeneral);
						this.documentViewer(request, preAuthDetailVO);
					}// end of else
						// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				}// end of if(!blnReviewPermession)
				else {
					preauthVO = new PreAuthVO();
					preauthVO.setClaimSeqID(ClaimsWebBoardHelper
							.getClaimsSeqId(request));
					preauthVO.setEnrollmentID(ClaimsWebBoardHelper
							.getEnrollmentId(request));
					preauthVO.setEnrollDtlSeqID(ClaimsWebBoardHelper
							.getEnrollDetailSeqId(request));
					preauthVO.setPolicySeqID(ClaimsWebBoardHelper
							.getPolicySeqId(request));
					preauthVO.setMemberSeqID(ClaimsWebBoardHelper
							.getMemberSeqId(request));
					preauthVO.setClaimantName(ClaimsWebBoardHelper
							.getClaimantName(request));
					preauthVO.setBufferAllowedYN(ClaimsWebBoardHelper
							.getBufferAllowedYN(request));
					preauthVO.setClmEnrollDtlSeqID(ClaimsWebBoardHelper
							.getClmEnrollDetailSeqId(request));
					preauthVO.setAmmendmentYN(ClaimsWebBoardHelper
							.getAmmendmentYN(request));
					preauthVO.setCoding_review_yn(preAuthDetailVO
							.getCoding_review_yn());
					preauthVO.setPreAuthNo(ClaimsWebBoardHelper
							.getWebBoardDesc(request));
					this.addToWebBoard(preauthVO, request, "");
				}// end of else
				/*
				 * if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y")
				 * ) { request.setAttribute("cacheId","|"+ClaimsWebBoardHelper.
				 * getClaimsSeqId(request)+"|");
				 * ClaimsWebBoardHelper.deleteWebBoardId(request);
				 * request.setAttribute("webboardinvoked","true");
				 * if(ClaimsWebBoardHelper.checkWebBoardId(request)==null) {
				 * TTKException expTTK = new TTKException();
				 * expTTK.setMessage("error.Claims.required"); //this attribute
				 * is used in JSP to show the error message.
				 * frmPreAuthGeneral.set("display","display"); throw expTTK;
				 * }//end of
				 * if(PreAuthWebBoardHelper.checkWebBoardId(request)==null) else
				 * { TTKException expTTK = new TTKException();
				 * frmPreAuthGeneral.set("display","display");
				 * expTTK.setMessage("error.Claims.codingflow"); throw expTTK;
				 * }//end of else }//end of
				 * if(ClaimsWebBoardHelper.getCodingReviewYN
				 * (request).equals("Y"))
				 */
			}// end of if(strActiveLink.equals(strClaims))
			if (preAuthDetailVO.getEnrolChangeMsg() != null
					&& preAuthDetailVO.getEnrolChangeMsg() != "") {
				request.setAttribute("enrollmentChange",
						"message.enrollmentChange");
			}
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& frmPreAuthGeneral.get("discPresentYN").equals(
									"Y"));
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of doChangeWebBoard(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to get the Review Information. Finally it forwards to
	 * the appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doReviewInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doReviewInfo ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			AdditionalDetailVO additionalDetailVO = null;
			PreAuthVO preauthVO = null;
			ClaimDetailVO claimDetailVO = null;
			// ClaimantVO claimantVO=null;
			String strActiveTab = TTKCommon.getActiveLink(request);
			ParallelPreAuthManager preAuthObject = null;
			ParallelClaimManager claimObject = null;
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			StringBuffer strCaption = new StringBuffer();
			String strFlowType = "";
			String strPreAuthFlow = "PRE";
			String strClaimFlow = "CLM";
			ArrayList alPrevClaimList = null;
			ArrayList<Object> alClaimList = null;
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strFlowType = strPreAuthFlow;
				strDetail = "preauthdetail";
				preAuthObject = this.getPreAuthManagerObject();
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strFlowType = strClaimFlow;
				strDetail = "dataentryclaimsdetail";
				claimObject = this.getClaimManagerObject();
			}// end of if(strActiveTab.equals(strClaims))
			if (strActiveTab.equals(strPre_Authorization)) {
				preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
						frmPreAuthGeneral, this, mapping, request);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
																				// Id
				// call the business method here for updating the pre-auth
				preAuthDetailVO = preAuthObject.saveReview(preAuthDetailVO,
						"PAT", "");
				// set the review information back to form
				frmPreAuthGeneral.set("eventSeqID", preAuthDetailVO
						.getEventSeqID().toString());
				frmPreAuthGeneral.set("reviewCount", preAuthDetailVO
						.getReviewCount().toString());
				frmPreAuthGeneral.set("requiredReviewCnt", preAuthDetailVO
						.getRequiredReviewCnt().toString());
				frmPreAuthGeneral.set("review", preAuthDetailVO.getReview()
						.toString());
				frmPreAuthGeneral.set("eventName", preAuthDetailVO
						.getEventName().toString());
				frmPreAuthGeneral.set("showCodingOverrideYN",
						preAuthDetailVO.getShowCodingOverrideYN());
				// If Workflow is completed set the flag as Y
				if (preAuthDetailVO.getEventName().contains("Complete")) {
					frmPreAuthGeneral.set("completedYN", "Y");
				}// end of
					// if(preAuthDetailVO.getEventName().contains("Complete"))
					// check whether user is having the permession for next
					// Event.
				boolean blnReviewPermession = checkReviewPermession(
						preAuthDetailVO.getEventSeqID(), request,
						strPre_Authorization);
				log.info("Coding review flag :"
						+ PreAuthWebBoardHelper.getCodingReviewYN(request));
				if (!blnReviewPermession)// there is no permession for next
											// level
				{
					request.setAttribute(
							"cacheId",
							"|"
									+ PreAuthWebBoardHelper
											.getPreAuthSeqId(request) + "|");
					PreAuthWebBoardHelper.deleteWebBoardId(request, "SeqId");
					request.setAttribute("webboardinvoked", "true");
					// After deleting if web board is null display the error
					// message.
					if (PreAuthWebBoardHelper.checkWebBoardId(request) == null) {
						TTKException expTTK = new TTKException();
						// this attribute is used in JSP to show the error
						// message.
						frmPreAuthGeneral.set("display", "display");
						expTTK.setMessage("error.PreAuthorization.required");
						throw expTTK;
					}// end of
						// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
					else {
						// Fetch the next pre-auth information which is the
						// web-board.
						preauthVO = new PreAuthVO();
						preauthVO.setMemberSeqID(PreAuthWebBoardHelper
								.getMemberSeqId(request));
						preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper
								.getPreAuthSeqId(request));
						preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper
								.getEnrollmentDetailId(request));
						preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
						// call the business layer to get the Pre-Auth detail
						preAuthDetailVO = preAuthObject
								.getPreAuthDetail(preauthVO,
										PreAuthWebBoardHelper
												.getPreAuthStatus(request));
						// check for conflict value
						toolBar.getConflictIcon().setVisible(
								toolBar.getConflictIcon().isVisible()
										&& preAuthDetailVO.getDiscPresentYN()
												.equals("Y"));
						frmPreAuthGeneral.initialize(mapping);
						if (!preAuthDetailVO.getPreAuthHospitalVO()
								.getEmpanelStatusTypeID().equals("")
								&& !preAuthDetailVO.getPreAuthHospitalVO()
										.getEmpanelStatusTypeID().equals("EMP")) {
							preAuthDetailVO.getPreAuthHospitalVO()
									.setStatusDisYN("Y");
						}// end of
							// if(!preAuthDetailVO.getPreAuthHospitalVO().getEmpanelStatusTypeID().equals("EMP"))
						frmPreAuthGeneral = (DynaActionForm) FormUtils
								.setFormValues("frmPreAuthGeneral",
										preAuthDetailVO, this, mapping, request);
						frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
								.setFormValues("frmPreAuthHospital",
										preAuthDetailVO.getPreAuthHospitalVO(),
										this, mapping, request));
						frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
								.setFormValues("frmClaimantDetails",
										preAuthDetailVO.getClaimantVO(), this,
										mapping, request));
						frmPreAuthGeneral
								.set("additionalDetailVO",
										FormUtils
												.setFormValues(
														"frmAdditionalDetail",
														preAuthDetailVO
																.getAdditionalDetailVO(),
														this, mapping, request));
						claimDetailVO = new ClaimDetailVO();
						frmPreAuthGeneral.set("claimDetailVO", FormUtils
								.setFormValues("frmClaimDetail", claimDetailVO,
										this, mapping, request));
						strCaption.append(" Edit");
						strCaption.append(" [ "
								+ PreAuthWebBoardHelper
										.getClaimantName(request) + " ]");
						if (PreAuthWebBoardHelper.getEnrollmentId(request) != null
								&& (!"".equals(PreAuthWebBoardHelper
										.getEnrollmentId(request).trim())))
							strCaption.append(" [ "
									+ PreAuthWebBoardHelper
											.getEnrollmentId(request) + " ]");
						frmPreAuthGeneral.set("caption", strCaption.toString());
						if (preAuthDetailVO.getPreAuthTypeID().equals("REG")) {
							frmPreAuthGeneral.set("preAuthTypeDesc", "Regular");
						}// end of
							// if(preAuthDetailVO.getPreAuthTypeID().equals("REG"))
						else if (preAuthDetailVO.getPreAuthTypeID().equals(
								"MRG")) {
							frmPreAuthGeneral.set("preAuthTypeDesc",
									"Manual-Regular");
						}// end of else
							// if(preAuthDetailVO.getPreAuthTypeID().equals("MRG"))
						else {
							frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");
						}// end of else
						frmPreAuthGeneral.set("flowType",
								strFlowType.toString());// set the value from
														// which flow ur are.
						// keep the frmPolicyDetails in session scope
						request.getSession().setAttribute("frmPreAuthGeneral",
								frmPreAuthGeneral);
						this.documentViewer(request, preAuthDetailVO);
					}// end of else
						// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				}// end of if(!blnReviewPermession)
				else {
					preauthVO = new PreAuthVO();
					preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper
							.getPreAuthSeqId(request));
					preauthVO.setEnrollmentID(PreAuthWebBoardHelper
							.getEnrollmentId(request));
					preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper
							.getEnrollmentDetailId(request));
					preauthVO.setPolicySeqID(PreAuthWebBoardHelper
							.getPolicySeqId(request));
					preauthVO.setMemberSeqID(PreAuthWebBoardHelper
							.getMemberSeqId(request));
					preauthVO.setClaimantName(PreAuthWebBoardHelper
							.getClaimantName(request));
					preauthVO.setBufferAllowedYN(PreAuthWebBoardHelper
							.getBufferAllowedYN(request));
					preauthVO.setShowBandYN(PreAuthWebBoardHelper
							.getShowBandYN(request));
					preauthVO.setCoding_review_yn(preAuthDetailVO
							.getCoding_review_yn());
					preauthVO.setPreAuthNo(PreAuthWebBoardHelper
							.getWebBoardDesc(request));
					this.addToWebBoard(preauthVO, request,
							PreAuthWebBoardHelper.getPreAuthStatus(request));
				}// end of else
				/*
				 * if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"
				 * )) {
				 * request.setAttribute("cacheId","|"+PreAuthWebBoardHelper.
				 * getPreAuthSeqId(request)+"|");
				 * PreAuthWebBoardHelper.deleteWebBoardId(request,"SeqId");
				 * request.setAttribute("webboardinvoked","true");
				 * frmPreAuthGeneral.set("display","display");
				 * if(PreAuthWebBoardHelper.checkWebBoardId(request)==null) {
				 * TTKException expTTK = new TTKException(); //this attribute is
				 * used in JSP to show the error message.
				 * expTTK.setMessage("error.PreAuthorization.required"); throw
				 * expTTK; }//end of
				 * if(PreAuthWebBoardHelper.checkWebBoardId(request)==null) else
				 * { TTKException expTTK = new TTKException();
				 * expTTK.setMessage("error.PreAuthorization.codingflow"); throw
				 * expTTK; }//end of else }//end of
				 * if(PreAuthWebBoardHelper.getCodingReviewYN
				 * (request).equals("Y"))
				 */
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
						frmPreAuthGeneral, this, mapping, request);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
																				// Id
				preAuthDetailVO = claimObject.saveReview(preAuthDetailVO,
						strClaimFlow, "");
				// set the review information back to form
				frmPreAuthGeneral.set("eventSeqID", preAuthDetailVO
						.getEventSeqID().toString());
				frmPreAuthGeneral.set("reviewCount", preAuthDetailVO
						.getReviewCount().toString());
				frmPreAuthGeneral.set("requiredReviewCnt", preAuthDetailVO
						.getRequiredReviewCnt().toString());
				frmPreAuthGeneral.set("review", preAuthDetailVO.getReview()
						.toString());
				frmPreAuthGeneral.set("eventName",
						preAuthDetailVO.getEventName());
				frmPreAuthGeneral.set("showCodingOverrideYN",
						preAuthDetailVO.getShowCodingOverrideYN());
				// If Workflow is completed set the flag as Y
				if (preAuthDetailVO.getEventName().contains("Complete")) {
					frmPreAuthGeneral.set("completedYN", "Y");
				}// end of
					// if(preAuthDetailVO.getEventName().contains("Complete"))

				// check whether user is having the permession for next Event.
				boolean blnReviewPermession = checkReviewPermession(
						preAuthDetailVO.getEventSeqID(), request, strClaims);
				if (!blnReviewPermession)// no permession is there
				{
					request.setAttribute("cacheId",
							"|" + ClaimsWebBoardHelper.getClaimsSeqId(request)
									+ "|");
					ClaimsWebBoardHelper.deleteWebBoardId(request, "SeqId");
					request.setAttribute("webboardinvoked", "true");
					if (ClaimsWebBoardHelper.checkWebBoardId(request) == null) {
						TTKException expTTK = new TTKException();
						expTTK.setMessage("error.Claims.required");
						// this attribute is used in JSP to show the error
						// message.
						frmPreAuthGeneral.set("display", "display");
						throw expTTK;
					}// end of
						// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
					else {
						// Fetch the next pre-auth information which is the
						// web-board.
						preauthVO = new PreAuthVO();
						alClaimList = new ArrayList<Object>();
						alClaimList.add(ClaimsWebBoardHelper
								.getClaimsSeqId(request));
						alClaimList.add(ClaimsWebBoardHelper
								.getClmEnrollDetailSeqId(request));
						alClaimList.add(ClaimsWebBoardHelper
								.getMemberSeqId(request));
						alClaimList.add(TTKCommon.getUserSeqId(request));
						// call the business layer to get the Claim detail
						preAuthDetailVO = claimObject
								.getClaimDetail(alClaimList);
						if (preAuthDetailVO.getPrevClaimList() != null) {
							alPrevClaimList = preAuthDetailVO
									.getPrevClaimList();
						}// end of if(preAuthDetailVO.getPrevClaimList()!=null)
						strCaption.append(" [ "
								+ ClaimsWebBoardHelper.getClaimantName(request)
								+ " ]");
						if (ClaimsWebBoardHelper.getEnrollmentId(request) != null
								&& (!"".equals(ClaimsWebBoardHelper
										.getEnrollmentId(request).trim())))
							strCaption.append(" [ "
									+ ClaimsWebBoardHelper
											.getEnrollmentId(request) + " ]");
						frmPreAuthGeneral.initialize(mapping);
						if (!preAuthDetailVO.getPreAuthHospitalVO()
								.getEmpanelStatusTypeID().equals("")
								&& !preAuthDetailVO.getPreAuthHospitalVO()
										.getEmpanelStatusTypeID().equals("EMP")) {
							preAuthDetailVO.getPreAuthHospitalVO()
									.setStatusDisYN("Y");
						}// end of
							// if(!preAuthDetailVO.getPreAuthHospitalVO().getEmpanelStatusTypeID().equals("EMP"))
						frmPreAuthGeneral = (DynaActionForm) FormUtils
								.setFormValues("frmPreAuthGeneral",
										preAuthDetailVO, this, mapping, request);
						frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
								.setFormValues("frmPreAuthHospital",
										preAuthDetailVO.getPreAuthHospitalVO(),
										this, mapping, request));
						frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
								.setFormValues("frmClaimantDetails",
										preAuthDetailVO.getClaimantVO(), this,
										mapping, request));
						frmPreAuthGeneral.set("claimDetailVO", FormUtils
								.setFormValues("frmClaimDetail",
										preAuthDetailVO.getClaimDetailVO(),
										this, mapping, request));
						additionalDetailVO = new AdditionalDetailVO();
						frmPreAuthGeneral.set("additionalDetailVO", FormUtils
								.setFormValues("frmAdditionalDetail",
										additionalDetailVO, this, mapping,
										request));
						frmPreAuthGeneral.set("hmPrevHospList",
								preAuthDetailVO.getPrevHospDetails());
						toolBar.getConflictIcon().setVisible(
								toolBar.getConflictIcon().isVisible()
										&& preAuthDetailVO.getDiscPresentYN()
												.equals("Y"));
						frmPreAuthGeneral.set("flowType",
								strFlowType.toString());// set the value from
														// which flow ur are.
						frmPreAuthGeneral.set("caption", strCaption.toString());
						frmPreAuthGeneral.set("alPrevClaimList",
								alPrevClaimList);
						// keep the frmPolicyDetails in session scope
						request.getSession().setAttribute("frmPreAuthGeneral",
								frmPreAuthGeneral);
						this.documentViewer(request, preAuthDetailVO);
					}// end of else
						// if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				}// end of if(!blnReviewPermession)
				else {
					preauthVO = new PreAuthVO();
					preauthVO.setClaimSeqID(ClaimsWebBoardHelper
							.getClaimsSeqId(request));
					preauthVO.setEnrollmentID(ClaimsWebBoardHelper
							.getEnrollmentId(request));
					preauthVO.setEnrollDtlSeqID(ClaimsWebBoardHelper
							.getEnrollDetailSeqId(request));
					preauthVO.setPolicySeqID(ClaimsWebBoardHelper
							.getPolicySeqId(request));
					preauthVO.setMemberSeqID(ClaimsWebBoardHelper
							.getMemberSeqId(request));
					preauthVO.setClaimantName(ClaimsWebBoardHelper
							.getClaimantName(request));
					preauthVO.setBufferAllowedYN(ClaimsWebBoardHelper
							.getBufferAllowedYN(request));
					preauthVO.setClmEnrollDtlSeqID(ClaimsWebBoardHelper
							.getClmEnrollDetailSeqId(request));
					preauthVO.setAmmendmentYN(ClaimsWebBoardHelper
							.getAmmendmentYN(request));
					preauthVO.setCoding_review_yn(preAuthDetailVO
							.getCoding_review_yn());
					preauthVO.setPreAuthNo(ClaimsWebBoardHelper
							.getWebBoardDesc(request));
					this.addToWebBoard(preauthVO, request, "");
				}// end of else
				/*
				 * if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y")
				 * ) { request.setAttribute("cacheId","|"+ClaimsWebBoardHelper.
				 * getClaimsSeqId(request)+"|");
				 * ClaimsWebBoardHelper.deleteWebBoardId(request);
				 * request.setAttribute("webboardinvoked","true");
				 * if(ClaimsWebBoardHelper.checkWebBoardId(request)==null) {
				 * TTKException expTTK = new TTKException();
				 * expTTK.setMessage("error.Claims.required"); //this attribute
				 * is used in JSP to show the error message.
				 * frmPreAuthGeneral.set("display","display"); throw expTTK;
				 * }//end of
				 * if(PreAuthWebBoardHelper.checkWebBoardId(request)==null) else
				 * { TTKException expTTK = new TTKException();
				 * frmPreAuthGeneral.set("display","display");
				 * expTTK.setMessage("error.Claims.codingflow"); throw expTTK;
				 * }//end of else }//end of
				 * if(ClaimsWebBoardHelper.getCodingReviewYN
				 * (request).equals("Y"))
				 */
			}// end of if(strActiveTab.equals(strClaims))
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& frmPreAuthGeneral.get("discPresentYN").equals(
									"Y"));
			if (preAuthDetailVO.getEnrolChangeMsg() != null
					&& preAuthDetailVO.getEnrolChangeMsg() != "") {
				request.setAttribute("enrollmentChange",
						"message.enrollmentChange");
			}// end of if
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doReviewInfo(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	// added for CR KOC-Decoupling -

	/**
	 * This method is used to get the Data Entry Review Information. Finally it
	 * forwards to the appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doDataEntryReviewInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doDataEntryReviewInfo ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			AdditionalDetailVO additionalDetailVO = null;
			PreAuthVO preauthVO = null;
			PreAuthHospitalVO preAuthHospitalVO = null;
			ClaimDetailVO claimDetailVO = null;
			ClaimantVO claimantVO = null;
			String strActiveTab = TTKCommon.getActiveLink(request);
			ParallelPreAuthManager preAuthObject = null;
			ParallelClaimManager claimObject = null;
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			StringBuffer strCaption = new StringBuffer();
			String strFlowType = "";
			String strPreAuthFlow = "PRE";
			String strClaimFlow = "CLM";
			ArrayList alPrevClaimList = null;
			ArrayList<Object> alClaimList = null;
			String strDetail = "";
			if (strActiveTab.equals(strClaims)) {
				strFlowType = strClaimFlow;
				strDetail = "dataentryclaimsdetail";
				claimObject = this.getClaimManagerObject();
			}// end of if(strActiveTab.equals(strClaims))
			if (strActiveTab.equals(strClaims)) {
				preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
						frmPreAuthGeneral, this, mapping, request);

				DynaActionForm frmPreAuthHospital = (DynaActionForm) frmPreAuthGeneral
						.get("preAuthHospitalVO");
				DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthGeneral
						.get("claimantDetailsVO");
				DynaActionForm frmClaimDetail = (DynaActionForm) frmPreAuthGeneral
						.get("claimDetailVO");

				if (frmClaimDetail.get("claimSubTypeID").toString()
						.equals("CSD")) {
					// Changes as per KOC 1285 Change Request
					if (request.getParameter("doctorCertificateYN") == null
							|| request.getParameter("doctorCertificateYN") == ""
							|| request.getParameter("doctorCertificateYN")
									.equalsIgnoreCase("N")) {
						frmClaimDetail.set("doctorCertificateYN", "N");
						// certificateYN="N";
					}// end of
						// if(request.getParameter("doctorCertificateYN")==null)
					else if (request.getParameter("doctorCertificateYN")
							.equalsIgnoreCase("Y")) {
						frmClaimDetail.set("doctorCertificateYN", "Y");
					}

					// Changes as per KOC 1285 Change Request
					preAuthHospitalVO = new PreAuthHospitalVO();
				}// end of
					// if(frmClaimDetail.get("claimSubTypeID").toString().equals("CSD"))
				else {
					// Changes as per KOC 1285 Change Request
					frmClaimDetail.set("doctorCertificateYN", "");
					frmClaimDetail.set("domicilaryReason", "");
					// Changes as per KOC 1285 Change Request
					preAuthHospitalVO = (PreAuthHospitalVO) FormUtils
							.getFormValues(frmPreAuthHospital,
									"frmPreAuthHospital", this, mapping,
									request);
				}// end of else
					// if(frmClaimDetail.get("claimSubTypeID").toString().equals("CSD"))

				claimantVO = (ClaimantVO) FormUtils.getFormValues(
						frmClaimantDetails, "frmClaimantDetails", this,
						mapping, request);
				claimDetailVO = (ClaimDetailVO) FormUtils.getFormValues(
						frmClaimDetail, "frmClaimDetail", this, mapping,
						request);
				preAuthDetailVO.setPreAuthHospitalVO(preAuthHospitalVO);
				preAuthDetailVO.setClaimantVO(claimantVO);
				preAuthDetailVO.setClaimDetailVO(claimDetailVO);

				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
																				// Id
				int iResult = claimObject.saveDataEntryReview(preAuthDetailVO,
						strClaimFlow, "");
				/*
				 * alClaimList=new ArrayList<Object>();
				 * alClaimList.add(ClaimsWebBoardHelper
				 * .getClaimsSeqId(request));
				 * alClaimList.add(ClaimsWebBoardHelper
				 * .getClmEnrollDetailSeqId(request));
				 * alClaimList.add(ClaimsWebBoardHelper
				 * .getMemberSeqId(request));
				 * alClaimList.add(TTKCommon.getUserSeqId(request));
				 */// preAuthDetailVO=claimObject.getClaimDetail(alClaimList);

				// if(iResult>0)
				// {

				request.setAttribute("updated", "message.savedSuccessfully");
				PreAuthVO preAuthVO = new PreAuthVO();
				preAuthVO.setClaimSeqID(preAuthDetailVO.getClaimSeqID());
				preAuthVO.setEnrollmentID(preAuthDetailVO.getClaimantVO()
						.getEnrollmentID());
				preAuthVO
						.setEnrollDtlSeqID(preAuthDetailVO.getEnrollDtlSeqID());
				preAuthVO.setPolicySeqID(preAuthDetailVO.getClaimantVO()
						.getPolicySeqID());
				preAuthVO.setMemberSeqID(preAuthDetailVO.getClaimantVO()
						.getMemberSeqID());
				preAuthVO.setClaimantName(preAuthDetailVO.getClaimantVO()
						.getName());
				if ((preAuthDetailVO.getClaimantVO().getBufferAllowedYN() != null)) {
					preAuthVO.setBufferAllowedYN(preAuthDetailVO
							.getClaimantVO().getBufferAllowedYN());
				}// end of
					// if((preAuthDetailVO.getClaimantVO().getBufferAllowedYN()
					// != null))
				preAuthVO.setAmmendmentYN(preAuthDetailVO.getAmmendmentYN());
				preAuthVO.setClmEnrollDtlSeqID(preAuthDetailVO
						.getClmEnrollDtlSeqID());
				preAuthVO.setPreAuthNo(preAuthDetailVO.getPreAuthNo());
				preAuthVO.setCoding_review_yn(preAuthDetailVO
						.getCoding_review_yn());
				this.addToWebBoard(preAuthVO, request, "");
				alClaimList = new ArrayList<Object>();
				alClaimList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alClaimList.add(ClaimsWebBoardHelper
						.getClmEnrollDetailSeqId(request));
				alClaimList.add(ClaimsWebBoardHelper.getMemberSeqId(request));
				alClaimList.add(TTKCommon.getUserSeqId(request));
				// call the business layer to get the Claim detail
				preAuthDetailVO = claimObject.getClaimDetail(alClaimList);
				if (preAuthDetailVO.getPrevClaimList() != null) {
					alPrevClaimList = preAuthDetailVO.getPrevClaimList();
				}// end of if(preAuthDetailVO.getPrevClaimList()!=null)
				if (!preAuthDetailVO.getPreAuthHospitalVO()
						.getEmpanelStatusTypeID().equals("")
						&& !preAuthDetailVO.getPreAuthHospitalVO()
								.getEmpanelStatusTypeID().equals("EMP")) {
					preAuthDetailVO.getPreAuthHospitalVO().setStatusDisYN("Y");
				}// end of if
					// to make claimsubtypeid as hospitalization when claim in
					// NHCP
				if (preAuthDetailVO.getClaimDetailVO().getClaimTypeID()
						.equals("CNH")) {
					preAuthDetailVO.getClaimDetailVO().setClaimSubTypeID("CSH");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
					// Added as per KOC 1285 Change Request
					// preAuthDetailVO.getClaimDetailVO().setDoctorCertificateYN(certificateYN);
				// System.out.println("Getting detail from DB DoctorCertificateYN"+preAuthDetailVO.getClaimDetailVO().getDoctorCertificateYN());
				if (!preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
						.equalsIgnoreCase("CSD")) {

					preAuthDetailVO.getClaimDetailVO().setDomicilaryReason("");
					preAuthDetailVO.getClaimDetailVO().setDoctorCertificateYN(
							"");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
				if (preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
						.equalsIgnoreCase("CSD")) {
					if (preAuthDetailVO.getClaimDetailVO()
							.getDoctorCertificateYN().equalsIgnoreCase("Y"))
						request.setAttribute("doctorCertificateYN", "Y");
					else
						request.setAttribute("doctorCertificateYN", "N");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CSD"))
				// Added as per KOC 1285 Change Request
				frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
						"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
						request);

				frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital",
								preAuthDetailVO.getPreAuthHospitalVO(), this,
								mapping, request));
				frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
						.setFormValues("frmClaimantDetails",
								preAuthDetailVO.getClaimantVO(), this, mapping,
								request));
				frmPreAuthGeneral.set("claimDetailVO", FormUtils.setFormValues(
						"frmClaimDetail", preAuthDetailVO.getClaimDetailVO(),
						this, mapping, request));
				additionalDetailVO = new AdditionalDetailVO();
				frmPreAuthGeneral.set("additionalDetailVO", FormUtils
						.setFormValues("frmAdditionalDetail",
								additionalDetailVO, this, mapping, request));
				frmPreAuthGeneral.set("hmPrevHospList",
						preAuthDetailVO.getPrevHospDetails());
				strCaption.append(" Edit");
				strCaption.append(" [ "
						+ ClaimsWebBoardHelper.getClaimantName(request) + " ]");
				if (ClaimsWebBoardHelper.getEnrollmentId(request) != null
						&& (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(
								request).trim())))
					strCaption.append(" [ "
							+ ClaimsWebBoardHelper.getEnrollmentId(request)
							+ " ]");
				toolBar.getConflictIcon().setVisible(
						toolBar.getConflictIcon().isVisible()
								&& preAuthDetailVO.getDiscPresentYN().equals(
										"Y"));
				frmPreAuthGeneral.set("flowType", strFlowType.toString());// set
																			// the
																			// value
																			// from
																			// which
																			// flow
																			// ur
																			// are.
				frmPreAuthGeneral.set("caption", strCaption.toString());
				frmPreAuthGeneral.set("alPrevClaimList", alPrevClaimList);
				// keep the frmPolicyDetails in session scope
				request.getSession().setAttribute("frmPreAuthGeneral",
						frmPreAuthGeneral);
				// }//end of IResult

				/*
				 * preauthVO=new PreAuthVO();
				 * preauthVO.setClaimSeqID(ClaimsWebBoardHelper
				 * .getClaimsSeqId(request));
				 * preauthVO.setEnrollmentID(ClaimsWebBoardHelper
				 * .getEnrollmentId(request));
				 * preauthVO.setEnrollDtlSeqID(ClaimsWebBoardHelper
				 * .getEnrollDetailSeqId(request));
				 * preauthVO.setPolicySeqID(ClaimsWebBoardHelper
				 * .getPolicySeqId(request));
				 * preauthVO.setMemberSeqID(ClaimsWebBoardHelper
				 * .getMemberSeqId(request));
				 * preauthVO.setClaimantName(ClaimsWebBoardHelper
				 * .getClaimantName(request));
				 * preauthVO.setBufferAllowedYN(ClaimsWebBoardHelper
				 * .getBufferAllowedYN(request));
				 * preauthVO.setClmEnrollDtlSeqID(
				 * ClaimsWebBoardHelper.getClmEnrollDetailSeqId(request));
				 * preauthVO
				 * .setAmmendmentYN(ClaimsWebBoardHelper.getAmmendmentYN
				 * (request)); //preauthVO.setCoding_review_yn(preAuthDetailVO.
				 * getCoding_review_yn());
				 * preauthVO.setPreAuthNo(ClaimsWebBoardHelper
				 * .getWebBoardDesc(request));
				 * this.addToWebBoard(preauthVO,request,"");
				 */
			}
			/*
			 * frmPreAuthGeneral=
			 * (DynaActionForm)FormUtils.setFormValues("frmPreAuthGeneral",
			 * preAuthDetailVO,this, mapping, request);
			 * frmPreAuthGeneral.set("genCompleteYN"
			 * ,preAuthDetailVO.getGenComplYN());
			 * request.getSession().setAttribute
			 * ("frmPreAuthGeneral",frmPreAuthGeneral);
			 */
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doReviewInfo(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	// ended

	// added for CR KOC-Decoupling
	/**
	 * This method will save the DataEntryReview Information Finally it forwards
	 * to the appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doDataEntryRevert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGeneralAction doDataEntryRevert");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			AdditionalDetailVO additionalDetailVO = null;
			PreAuthVO preauthVO = null;
			PreAuthHospitalVO preAuthHospitalVO = null;
			ClaimDetailVO claimDetailVO = null;
			ClaimantVO claimantVO = null;
			String strActiveTab = TTKCommon.getActiveLink(request);
			ParallelPreAuthManager preAuthObject = null;
			ParallelClaimManager claimObject = null;
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			StringBuffer strCaption = new StringBuffer();
			String strFlowType = "";
			String strPreAuthFlow = "PRE";
			String strClaimFlow = "CLM";
			ArrayList alPrevClaimList = null;
			ArrayList<Object> alClaimList = null;
			String strDetail = "";
			if (strActiveTab.equals(strClaims)) {
				strFlowType = strClaimFlow;
				strDetail = "dataentryclaimsdetail";
				claimObject = this.getClaimManagerObject();
			}// end of if(strActiveTab.equals(strClaims))
			if (strActiveTab.equals(strClaims)) {
				preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
						frmPreAuthGeneral, this, mapping, request);

				DynaActionForm frmPreAuthHospital = (DynaActionForm) frmPreAuthGeneral
						.get("preAuthHospitalVO");
				DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthGeneral
						.get("claimantDetailsVO");
				DynaActionForm frmClaimDetail = (DynaActionForm) frmPreAuthGeneral
						.get("claimDetailVO");

				if (frmClaimDetail.get("claimSubTypeID").toString()
						.equals("CSD")) {
					// Changes as per KOC 1285 Change Request
					if (request.getParameter("doctorCertificateYN") == null
							|| request.getParameter("doctorCertificateYN") == ""
							|| request.getParameter("doctorCertificateYN")
									.equalsIgnoreCase("N")) {
						frmClaimDetail.set("doctorCertificateYN", "N");
						// certificateYN="N";
					}// end of
						// if(request.getParameter("doctorCertificateYN")==null)
					else if (request.getParameter("doctorCertificateYN")
							.equalsIgnoreCase("Y")) {
						frmClaimDetail.set("doctorCertificateYN", "Y");
					}

					// Changes as per KOC 1285 Change Request
					preAuthHospitalVO = new PreAuthHospitalVO();
				}// end of
					// if(frmClaimDetail.get("claimSubTypeID").toString().equals("CSD"))
				else {
					// Changes as per KOC 1285 Change Request
					frmClaimDetail.set("doctorCertificateYN", "");
					frmClaimDetail.set("domicilaryReason", "");
					// Changes as per KOC 1285 Change Request
					preAuthHospitalVO = (PreAuthHospitalVO) FormUtils
							.getFormValues(frmPreAuthHospital,
									"frmPreAuthHospital", this, mapping,
									request);
				}// end of else
					// if(frmClaimDetail.get("claimSubTypeID").toString().equals("CSD"))

				claimantVO = (ClaimantVO) FormUtils.getFormValues(
						frmClaimantDetails, "frmClaimantDetails", this,
						mapping, request);
				claimDetailVO = (ClaimDetailVO) FormUtils.getFormValues(
						frmClaimDetail, "frmClaimDetail", this, mapping,
						request);
				preAuthDetailVO.setPreAuthHospitalVO(preAuthHospitalVO);
				preAuthDetailVO.setClaimantVO(claimantVO);
				preAuthDetailVO.setClaimDetailVO(claimDetailVO);

				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
																				// Id
				int iResult = claimObject.revertProcess(preAuthDetailVO, "CLM");
				/*
				 * alClaimList=new ArrayList<Object>();
				 * alClaimList.add(ClaimsWebBoardHelper
				 * .getClaimsSeqId(request));
				 * alClaimList.add(ClaimsWebBoardHelper
				 * .getClmEnrollDetailSeqId(request));
				 * alClaimList.add(ClaimsWebBoardHelper
				 * .getMemberSeqId(request));
				 * alClaimList.add(TTKCommon.getUserSeqId(request));
				 */// preAuthDetailVO=claimObject.getClaimDetail(alClaimList);

				// if(iResult>0)
				// {

				request.setAttribute("updated", "message.savedSuccessfully");
				PreAuthVO preAuthVO = new PreAuthVO();
				preAuthVO.setClaimSeqID(preAuthDetailVO.getClaimSeqID());
				preAuthVO.setEnrollmentID(preAuthDetailVO.getClaimantVO()
						.getEnrollmentID());
				preAuthVO
						.setEnrollDtlSeqID(preAuthDetailVO.getEnrollDtlSeqID());
				preAuthVO.setPolicySeqID(preAuthDetailVO.getClaimantVO()
						.getPolicySeqID());
				preAuthVO.setMemberSeqID(preAuthDetailVO.getClaimantVO()
						.getMemberSeqID());
				preAuthVO.setClaimantName(preAuthDetailVO.getClaimantVO()
						.getName());
				if ((preAuthDetailVO.getClaimantVO().getBufferAllowedYN() != null)) {
					preAuthVO.setBufferAllowedYN(preAuthDetailVO
							.getClaimantVO().getBufferAllowedYN());
				}// end of
					// if((preAuthDetailVO.getClaimantVO().getBufferAllowedYN()
					// != null))
				preAuthVO.setAmmendmentYN(preAuthDetailVO.getAmmendmentYN());
				preAuthVO.setClmEnrollDtlSeqID(preAuthDetailVO
						.getClmEnrollDtlSeqID());
				preAuthVO.setPreAuthNo(preAuthDetailVO.getPreAuthNo());
				preAuthVO.setCoding_review_yn(preAuthDetailVO
						.getCoding_review_yn());
				this.addToWebBoard(preAuthVO, request, "");
				alClaimList = new ArrayList<Object>();
				alClaimList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alClaimList.add(ClaimsWebBoardHelper
						.getClmEnrollDetailSeqId(request));
				alClaimList.add(ClaimsWebBoardHelper.getMemberSeqId(request));
				alClaimList.add(TTKCommon.getUserSeqId(request));
				// call the business layer to get the Claim detail
				preAuthDetailVO = claimObject.getClaimDetail(alClaimList);
				if (preAuthDetailVO.getPrevClaimList() != null) {
					alPrevClaimList = preAuthDetailVO.getPrevClaimList();
				}// end of if(preAuthDetailVO.getPrevClaimList()!=null)
				if (!preAuthDetailVO.getPreAuthHospitalVO()
						.getEmpanelStatusTypeID().equals("")
						&& !preAuthDetailVO.getPreAuthHospitalVO()
								.getEmpanelStatusTypeID().equals("EMP")) {
					preAuthDetailVO.getPreAuthHospitalVO().setStatusDisYN("Y");
				}// end of if
					// to make claimsubtypeid as hospitalization when claim in
					// NHCP
				if (preAuthDetailVO.getClaimDetailVO().getClaimTypeID()
						.equals("CNH")) {
					preAuthDetailVO.getClaimDetailVO().setClaimSubTypeID("CSH");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
					// Added as per KOC 1285 Change Request
					// preAuthDetailVO.getClaimDetailVO().setDoctorCertificateYN(certificateYN);
				if (!preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
						.equalsIgnoreCase("CSD")) {

					preAuthDetailVO.getClaimDetailVO().setDomicilaryReason("");
					preAuthDetailVO.getClaimDetailVO().setDoctorCertificateYN(
							"");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
				if (preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
						.equalsIgnoreCase("CSD")) {
					if (preAuthDetailVO.getClaimDetailVO()
							.getDoctorCertificateYN().equalsIgnoreCase("Y"))
						request.setAttribute("doctorCertificateYN", "Y");
					else
						request.setAttribute("doctorCertificateYN", "N");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CSD"))
				// Added as per KOC 1285 Change Request
				frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
						"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
						request);

				frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital",
								preAuthDetailVO.getPreAuthHospitalVO(), this,
								mapping, request));
				frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
						.setFormValues("frmClaimantDetails",
								preAuthDetailVO.getClaimantVO(), this, mapping,
								request));
				frmPreAuthGeneral.set("claimDetailVO", FormUtils.setFormValues(
						"frmClaimDetail", preAuthDetailVO.getClaimDetailVO(),
						this, mapping, request));
				additionalDetailVO = new AdditionalDetailVO();
				frmPreAuthGeneral.set("additionalDetailVO", FormUtils
						.setFormValues("frmAdditionalDetail",
								additionalDetailVO, this, mapping, request));
				frmPreAuthGeneral.set("hmPrevHospList",
						preAuthDetailVO.getPrevHospDetails());
				strCaption.append(" Edit");
				strCaption.append(" [ "
						+ ClaimsWebBoardHelper.getClaimantName(request) + " ]");
				if (ClaimsWebBoardHelper.getEnrollmentId(request) != null
						&& (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(
								request).trim())))
					strCaption.append(" [ "
							+ ClaimsWebBoardHelper.getEnrollmentId(request)
							+ " ]");
				toolBar.getConflictIcon().setVisible(
						toolBar.getConflictIcon().isVisible()
								&& preAuthDetailVO.getDiscPresentYN().equals(
										"Y"));
				frmPreAuthGeneral.set("flowType", strFlowType.toString());// set
																			// the
																			// value
																			// from
																			// which
																			// flow
																			// ur
																			// are.
				frmPreAuthGeneral.set("caption", strCaption.toString());
				frmPreAuthGeneral.set("alPrevClaimList", alPrevClaimList);
				request.getSession().setAttribute("frmPreAuthGeneral",
						frmPreAuthGeneral);

			}
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of doChangeWebBoard(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)
		// ended

	/**
	 * This method is called from the struts framework. This method is used to
	 * navigate to detail screen to add a record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doAdd ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile) request
					.getSession().getAttribute("UserSecurityProfile");
			ArrayList alPrevClaimList = null;
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthHospitalVO preAuthHospitalVO = null;
			AdditionalDetailVO additionalDetailVO = null;
			ClaimantVO claimantVO = null;
			ClaimDetailVO claimDetailVO = null;
			StringBuffer strCaption = new StringBuffer();
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strFlowType = "";
			String strDetail = "";
			String strPreAuthFlow = "PRE";
			String strClaimFlow = "CLM";
			if (strActiveTab.equals(strPre_Authorization)) {
				strFlowType = strPreAuthFlow;
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strFlowType = strClaimFlow;
				strDetail = "dataentryclaimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			preAuthDetailVO = new PreAuthDetailVO();
			preAuthHospitalVO = new PreAuthHospitalVO();
			additionalDetailVO = new AdditionalDetailVO();
			claimantVO = new ClaimantVO();
			preAuthDetailVO.setDiscPresentYN("N");
			preAuthDetailVO.setCompletedYN("N");
			preAuthDetailVO.setPreAuthTypeID("MAN");// from add mode pre-auth
													// type will be manual.
			preAuthDetailVO.setPreAuthRecvTypeID("FAX");// default is fax
			preAuthDetailVO.setPreAuthHospitalVO(preAuthHospitalVO);
			preAuthDetailVO.setAdditionalDetailVO(additionalDetailVO);
			preAuthDetailVO.setClaimantVO(claimantVO);
			preAuthDetailVO.setPriorityTypeID("MID");// from add mode priority
														// will be medium.
			frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
					"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
					request);
			frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils.setFormValues(
					"frmPreAuthHospital",
					preAuthDetailVO.getPreAuthHospitalVO(), this, mapping,
					request));
			frmPreAuthGeneral.set("claimantDetailsVO", FormUtils.setFormValues(
					"frmClaimantDetails", preAuthDetailVO.getClaimantVO(),
					this, mapping, request));
			frmPreAuthGeneral.set("additionalDetailVO", FormUtils
					.setFormValues("frmAdditionalDetail",
							preAuthDetailVO.getAdditionalDetailVO(), this,
							mapping, request));
			claimDetailVO = new ClaimDetailVO();
			frmPreAuthGeneral.set("claimDetailVO", FormUtils.setFormValues(
					"frmClaimDetail", claimDetailVO, this, mapping, request));
			frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");// to display as
																// manual in the
																// add mode.
			frmPreAuthGeneral.set("flowType", strFlowType.toString());// set the
																		// value
																		// from
																		// which
																		// flow
																		// ur
																		// are.
			strCaption.append("Add");
			if (userSecurityProfile.getBranchID() != null) {
				frmPreAuthGeneral.set("officeSeqID", userSecurityProfile
						.getBranchID().toString());
			}// end of if(userSecurityProfile.getBranchID()!=null)
			else {
				frmPreAuthGeneral.set("officeSeqID", "");
			}// end of else
			frmPreAuthGeneral.set("caption", strCaption.toString());
			frmPreAuthGeneral.set("alPrevClaimList", alPrevClaimList);
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthGeneral",
					frmPreAuthGeneral);
			this.documentViewer(request, preAuthDetailVO);
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to add/update the record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.info("Inside PreAuthGenealAction doSave ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthHospitalVO preAuthHospitalVO = null;
			AdditionalDetailVO additionalDetailVO = null;
			ClaimantVO claimantVO = null;
			PreAuthVO preauthVO = null;
			ClaimDetailVO claimDetailVO = null;
			HttpSession session=request.getSession();
			StringBuffer strCaption = new StringBuffer();
			ParallelPreAuthManager preAuthObject = null;
			ParallelClaimManager claimObject = null;
			ArrayList alPrevClaimList = null;
			ArrayList<Object> alClaimList = null;
			String strDetail = "";
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strFlowType = "";
			String strPreAuthFlow = "PRE";
			String strClaimFlow = "CLM";
			String certificateYN = "";// KOC1285
			if (strActiveTab.equals(strPre_Authorization)) {
				strFlowType = strPreAuthFlow;
				strDetail = "preauthdetail";
				preAuthObject = this.getPreAuthManagerObject();
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strFlowType = strClaimFlow;
				strDetail = "dataentryclaimsdetail";
				claimObject = this.getClaimManagerObject();
			}// end of if(strActiveTab.equals(strClaims))

			if (strActiveTab.equals(strPre_Authorization)) {
				preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
						frmPreAuthGeneral, this, mapping, request);
				DynaActionForm frmPreAuthHospital = (DynaActionForm) frmPreAuthGeneral
						.get("preAuthHospitalVO");
				DynaActionForm frmAdditionalDetail = (DynaActionForm) frmPreAuthGeneral
						.get("additionalDetailVO");
				DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthGeneral
						.get("claimantDetailsVO");
				preAuthHospitalVO = (PreAuthHospitalVO) FormUtils
						.getFormValues(frmPreAuthHospital,
								"frmPreAuthHospital", this, mapping, request);
				additionalDetailVO = (AdditionalDetailVO) FormUtils
						.getFormValues(frmAdditionalDetail,
								"frmAdditionalDetail", this, mapping, request);
				claimantVO = (ClaimantVO) FormUtils.getFormValues(
						frmClaimantDetails, "frmClaimantDetails", this,
						mapping, request);
				// log.info("Scheme :"+additionalDetailVO.getInsScheme());
				// log.info("CertificateNo :"+additionalDetailVO.getCertificateNo());
				preAuthDetailVO.setPreAuthHospitalVO(preAuthHospitalVO);
				preAuthDetailVO.setAdditionalDetailVO(additionalDetailVO);
				preAuthDetailVO.setClaimantVO(claimantVO);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
																				// Id
				// calling savePolicy through the DAO
				Object[] objArrayResult = new Object[3];
				if (preAuthDetailVO.getPreAuthSeqID() != null) {
					objArrayResult = preAuthObject.savePreAuth(preAuthDetailVO,
							PreAuthWebBoardHelper.getPreAuthStatus(request));
				}// end of if(preAuthDetailVO.getPreAuthSeqID()!=null)
				else {
					objArrayResult = preAuthObject.savePreAuth(preAuthDetailVO,
							"PAT");
				}// end of else
				preauthVO = new PreAuthVO();
				if (objArrayResult.length > 0) {
					preauthVO.setMemberSeqID((Long) objArrayResult[0]);
					preauthVO.setPreAuthSeqID((Long) objArrayResult[1]);
					preauthVO.setEnrollDtlSeqID((Long) objArrayResult[2]);
					preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				}// end of if(objArrayResult.length>0)
				if (preAuthDetailVO.getPreAuthSeqID() != null) {
					// call the business layer to get the Pre-Auth detail
					preAuthDetailVO = preAuthObject.getPreAuthDetail(preauthVO,
							PreAuthWebBoardHelper.getPreAuthStatus(request));
				}// end of if(preAuthDetailVO.getPreAuthSeqID()!=null)
				else {
					// call the business layer to get the Pre-Auth detail
					preAuthDetailVO = preAuthObject.getPreAuthDetail(preauthVO,
							"PAT");
				}// end of else if(preAuthDetailVO.getPreAuthSeqID()!=null)

				if (preAuthDetailVO.getClaimantVO().getEnrollmentID() != null) {
					preauthVO.setEnrollmentID(preAuthDetailVO.getClaimantVO()
							.getEnrollmentID());
				}// end of
					// if(preAuthDetailVO.getClaimantVO().getEnrollmentID()!=null)
				if (preAuthDetailVO.getClaimantVO().getPolicySeqID() != null) {
					preauthVO.setPolicySeqID(preAuthDetailVO.getClaimantVO()
							.getPolicySeqID());
				}// end of
					// if(preAuthDetailVO.getClaimantVO().getPolicySeqID()!=null)
				preauthVO.setPreAuthNo(preAuthDetailVO.getPreAuthNo());
				preauthVO.setClaimantName(preAuthDetailVO.getClaimantVO()
						.getName());
				preauthVO.setBufferAllowedYN(preAuthDetailVO.getClaimantVO()
						.getBufferAllowedYN());
				preauthVO.setShowBandYN(preAuthDetailVO.getShowBandYN());
				preauthVO.setCoding_review_yn(preAuthDetailVO
						.getCoding_review_yn());
				// set the appropriate message
				if (preauthVO.getPreAuthSeqID() != null
						&& preauthVO.getPreAuthSeqID() > 0) {
					if (frmPreAuthGeneral != null
							&& frmPreAuthGeneral.get("preAuthSeqID") == null) {
						request.setAttribute("updated",
								"message.addedSuccessfully");
						if (TTKCommon
								.checkNull(
										PreAuthWebBoardHelper
												.getPreAuthStatus(request))
								.equals("ICO")) {
							request.setAttribute(
									"cacheId",
									"|"
											+ PreAuthWebBoardHelper
													.getWebBoardDesc(request)
											+ "|");
							PreAuthWebBoardHelper.deleteWebBoardId(request,
									"Desc");
							request.setAttribute("webboardinvoked", "true");
						}// end of
							// if(TTKCommon.checkNull(PreAuthWebBoardHelper.getPreAuthStatus(request)).equals("ICO"))
						this.addToWebBoard(preauthVO, request, "PAT");
					}// end of if(policyDetailVO.getPolicySeqID()!=null)
					else // end of
							// if(frmPreAuthGeneral!=null&&!frmPreAuthGeneral.get("preAuthSeqID").equals(""))
					{
						request.setAttribute("updated",
								"message.savedSuccessfully");
						this.addToWebBoard(preauthVO, request,
								PreAuthWebBoardHelper.getPreAuthStatus(request));
					}// end of else
						// if(frmPreAuthGeneral!=null&&!frmPreAuthGeneral.get("preAuthSeqID").equals(""))
					session.setAttribute("oralTab",null);
					if (!preAuthDetailVO.getPreAuthHospitalVO()
							.getEmpanelStatusTypeID().equals("")
							&& !preAuthDetailVO.getPreAuthHospitalVO()
									.getEmpanelStatusTypeID().equals("EMP")) {
						preAuthDetailVO.getPreAuthHospitalVO().setStatusDisYN(
								"Y");
					}// end of if
					frmPreAuthGeneral = (DynaActionForm) FormUtils
							.setFormValues("frmPreAuthGeneral",
									preAuthDetailVO, this, mapping, request);
					frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
							.setFormValues("frmPreAuthHospital",
									preAuthDetailVO.getPreAuthHospitalVO(),
									this, mapping, request));
					frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
							.setFormValues("frmClaimantDetails",
									preAuthDetailVO.getClaimantVO(), this,
									mapping, request));
					frmPreAuthGeneral.set("additionalDetailVO", FormUtils
							.setFormValues("frmAdditionalDetail",
									preAuthDetailVO.getAdditionalDetailVO(),
									this, mapping, request));
					claimDetailVO = new ClaimDetailVO();
					frmPreAuthGeneral.set("claimDetailVO", FormUtils
							.setFormValues("frmClaimDetail", claimDetailVO,
									this, mapping, request));
					strCaption.append(" Edit");
					strCaption.append(" [ "
							+ PreAuthWebBoardHelper.getClaimantName(request)
							+ " ]");
					if (PreAuthWebBoardHelper.getEnrollmentId(request) != null
							&& (!"".equals(PreAuthWebBoardHelper
									.getEnrollmentId(request).trim())))
						strCaption.append(" [ "
								+ PreAuthWebBoardHelper
										.getEnrollmentId(request) + " ]");
					toolBar.getConflictIcon().setVisible(
							toolBar.getConflictIcon().isVisible()
									&& preAuthDetailVO.getDiscPresentYN()
											.equals("Y"));
					frmPreAuthGeneral.set("caption", strCaption.toString());
					if (preAuthDetailVO.getPreAuthTypeID().equals("REG")) {
						frmPreAuthGeneral.set("preAuthTypeDesc", "Regular");
					}// end of
						// if(preAuthDetailVO.getPreAuthTypeID().equals("REG"))
					else if (preAuthDetailVO.getPreAuthTypeID().equals("MRG")) {
						frmPreAuthGeneral.set("preAuthTypeDesc",
								"Manual-Regular");
					}// end of else
						// if(preAuthDetailVO.getPreAuthTypeID().equals("MRG")){
					else {
						frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");
					}// end of else
					frmPreAuthGeneral.set("alPrevClaimList", alPrevClaimList);
					frmPreAuthGeneral.set("flowType", strFlowType.toString());// set
																				// the
																				// value
																				// from
																				// which
																				// flow
																				// ur
																				// are.
					// keep the frmPolicyDetails in session scope
					request.getSession().setAttribute("frmPreAuthGeneral",
							frmPreAuthGeneral);
					this.documentViewer(request, preAuthDetailVO);
				}// end of
					// if(preauthVO.getPreAuthSeqID()!=null&&preauthVO.getPreAuthSeqID()>
					// 0)
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				log.info("Inside claims Tab ");
				preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
						frmPreAuthGeneral, this, mapping, request);
				DynaActionForm frmPreAuthHospital = (DynaActionForm) frmPreAuthGeneral
						.get("preAuthHospitalVO");
				DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthGeneral
						.get("claimantDetailsVO");
				DynaActionForm frmClaimDetail = (DynaActionForm) frmPreAuthGeneral
						.get("claimDetailVO");
				// if Claim Sub Type id Domocoliary than empty the hospital
				// information
				if (frmClaimDetail.get("claimSubTypeID").toString()
						.equals("CSD")) {
					// Changes as per KOC 1285 Change Request
					if (request.getParameter("doctorCertificateYN") == null
							|| request.getParameter("doctorCertificateYN") == ""
							|| request.getParameter("doctorCertificateYN")
									.equalsIgnoreCase("N")) {
						frmClaimDetail.set("doctorCertificateYN", "N");
						// certificateYN="N";
					}// end of
						// if(request.getParameter("doctorCertificateYN")==null)
					else if (request.getParameter("doctorCertificateYN")
							.equalsIgnoreCase("Y")) {
						frmClaimDetail.set("doctorCertificateYN", "Y");
					}

					// Changes as per KOC 1285 Change Request
					preAuthHospitalVO = new PreAuthHospitalVO();
				}// end of
					// if(frmClaimDetail.get("claimSubTypeID").toString().equals("CSD"))
				else {
					// Changes as per KOC 1285 Change Request
					frmClaimDetail.set("doctorCertificateYN", "");
					frmClaimDetail.set("domicilaryReason", "");
					// Changes as per KOC 1285 Change Request
					preAuthHospitalVO = (PreAuthHospitalVO) FormUtils
							.getFormValues(frmPreAuthHospital,
									"frmPreAuthHospital", this, mapping,
									request);
				}// end of else
					// if(frmClaimDetail.get("claimSubTypeID").toString().equals("CSD"))
				claimantVO = (ClaimantVO) FormUtils.getFormValues(
						frmClaimantDetails, "frmClaimantDetails", this,
						mapping, request);
				claimDetailVO = (ClaimDetailVO) FormUtils.getFormValues(
						frmClaimDetail, "frmClaimDetail", this, mapping,
						request);
				preAuthDetailVO.setPreAuthHospitalVO(preAuthHospitalVO);
				preAuthDetailVO.setClaimantVO(claimantVO);
				preAuthDetailVO.setClaimDetailVO(claimDetailVO);
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
																				// Id
				long iUpdate = claimObject.saveClaimDetail(preAuthDetailVO);
				if (iUpdate > 0) {
					log.info("Inside update>0 ");
					request.setAttribute("updated", "message.savedSuccessfully");
					PreAuthVO preAuthVO = new PreAuthVO();
					preAuthVO.setClaimSeqID(preAuthDetailVO.getClaimSeqID());
					preAuthVO.setEnrollmentID(preAuthDetailVO.getClaimantVO()
							.getEnrollmentID());
					preAuthVO.setEnrollDtlSeqID(preAuthDetailVO
							.getEnrollDtlSeqID());
					preAuthVO.setPolicySeqID(preAuthDetailVO.getClaimantVO()
							.getPolicySeqID());
					preAuthVO.setMemberSeqID(preAuthDetailVO.getClaimantVO()
							.getMemberSeqID());
					preAuthVO.setClaimantName(preAuthDetailVO.getClaimantVO()
							.getName());
					if ((preAuthDetailVO.getClaimantVO().getBufferAllowedYN() != null)) {
						preAuthVO.setBufferAllowedYN(preAuthDetailVO
								.getClaimantVO().getBufferAllowedYN());
					}// end of
						// if((preAuthDetailVO.getClaimantVO().getBufferAllowedYN()
						// != null))
					preAuthVO
							.setAmmendmentYN(preAuthDetailVO.getAmmendmentYN());
					preAuthVO.setClmEnrollDtlSeqID(preAuthDetailVO
							.getClmEnrollDtlSeqID());
					preAuthVO.setPreAuthNo(preAuthDetailVO.getPreAuthNo());
					preAuthVO.setCoding_review_yn(preAuthDetailVO
							.getCoding_review_yn());
					this.addToWebBoard(preAuthVO, request, "");
					alClaimList = new ArrayList<Object>();
					alClaimList.add(ClaimsWebBoardHelper
							.getClaimsSeqId(request));
					alClaimList.add(ClaimsWebBoardHelper
							.getClmEnrollDetailSeqId(request));
					alClaimList.add(ClaimsWebBoardHelper
							.getMemberSeqId(request));
					alClaimList.add(TTKCommon.getUserSeqId(request));
					// call the business layer to get the Claim detail
					preAuthDetailVO = claimObject.getClaimDetail(alClaimList);
					if (preAuthDetailVO.getPrevClaimList() != null) {
						alPrevClaimList = preAuthDetailVO.getPrevClaimList();
					}// end of if(preAuthDetailVO.getPrevClaimList()!=null)
					if (!preAuthDetailVO.getPreAuthHospitalVO()
							.getEmpanelStatusTypeID().equals("")
							&& !preAuthDetailVO.getPreAuthHospitalVO()
									.getEmpanelStatusTypeID().equals("EMP")) {
						preAuthDetailVO.getPreAuthHospitalVO().setStatusDisYN(
								"Y");
					}// end of if
						// to make claimsubtypeid as hospitalization when claim
						// in NHCP
					if (preAuthDetailVO.getClaimDetailVO().getClaimTypeID()
							.equals("CNH")) {
						preAuthDetailVO.getClaimDetailVO().setClaimSubTypeID(
								"CSH");
					}// end of
						// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
						// Added as per KOC 1285 Change Request
					if (!preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
							.equalsIgnoreCase("CSD")) {

						preAuthDetailVO.getClaimDetailVO().setDomicilaryReason(
								"");
						preAuthDetailVO.getClaimDetailVO()
								.setDoctorCertificateYN("");
					}// end of
						// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
					if (preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
							.equalsIgnoreCase("CSD")) {
						if (preAuthDetailVO.getClaimDetailVO()
								.getDoctorCertificateYN().equalsIgnoreCase("Y"))
							request.setAttribute("doctorCertificateYN", "Y");
						else
							request.setAttribute("doctorCertificateYN", "N");
					}// end of
						// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CSD"))
					// Added as per KOC 1285 Change Request
					frmPreAuthGeneral = (DynaActionForm) FormUtils
							.setFormValues("frmPreAuthGeneral",
									preAuthDetailVO, this, mapping, request);
					frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
							.setFormValues("frmPreAuthHospital",
									preAuthDetailVO.getPreAuthHospitalVO(),
									this, mapping, request));
					frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
							.setFormValues("frmClaimantDetails",
									preAuthDetailVO.getClaimantVO(), this,
									mapping, request));
					frmPreAuthGeneral.set("claimDetailVO", FormUtils
							.setFormValues("frmClaimDetail",
									preAuthDetailVO.getClaimDetailVO(), this,
									mapping, request));
					additionalDetailVO = new AdditionalDetailVO();
					frmPreAuthGeneral
							.set("additionalDetailVO", FormUtils.setFormValues(
									"frmAdditionalDetail", additionalDetailVO,
									this, mapping, request));
					frmPreAuthGeneral.set("hmPrevHospList",
							preAuthDetailVO.getPrevHospDetails());
					strCaption.append(" Edit");
					strCaption.append(" [ "
							+ ClaimsWebBoardHelper.getClaimantName(request)
							+ " ]");
					if (ClaimsWebBoardHelper.getEnrollmentId(request) != null
							&& (!"".equals(ClaimsWebBoardHelper
									.getEnrollmentId(request).trim())))
						strCaption.append(" [ "
								+ ClaimsWebBoardHelper.getEnrollmentId(request)
								+ " ]");
					toolBar.getConflictIcon().setVisible(
							toolBar.getConflictIcon().isVisible()
									&& preAuthDetailVO.getDiscPresentYN()
											.equals("Y"));
					frmPreAuthGeneral.set("flowType", strFlowType.toString());// set
																				// the
																				// value
																				// from
																				// which
																				// flow
																				// ur
																				// are.
					frmPreAuthGeneral.set("caption", strCaption.toString());
					frmPreAuthGeneral.set("alPrevClaimList", alPrevClaimList);
					// keep the frmPolicyDetails in session scope
					request.getSession().setAttribute("frmPreAuthGeneral",
							frmPreAuthGeneral);
					this.documentViewer(request, preAuthDetailVO);
				}// end of if(iUpdate > 0)
				log.info("Inside PreAuthGenealAction doSave-- "
						+ strFlowType.toString());
				frmPreAuthGeneral.set("flowType", strFlowType.toString());// set
																			// the
																			// value
																			// from
																			// which
																			// flow
																			// ur
																			// are.
			}// end of if(strActiveTab.equals(strClaims))
			if (preAuthDetailVO.getEnrolChangeMsg() != null
					&& preAuthDetailVO.getEnrolChangeMsg() != "") {
				request.setAttribute("enrollmentChange",
						"message.enrollmentChange");
			}// end of if
			log.info("Inside strDetail-- " + strDetail);
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to reload the screen when the reset button is
	 * pressed. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doReset ");
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile) request
					.getSession().getAttribute("UserSecurityProfile");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthHospitalVO preAuthHospitalVO = null;
			AdditionalDetailVO additionalDetailVO = null;
			ClaimantVO claimantVO = null;
			PreAuthVO preauthVO = null;
			ClaimDetailVO claimDetailVO = null;
			StringBuffer strCaption = new StringBuffer();
			ParallelPreAuthManager preAuthObject = null;
			ParallelClaimManager claimObject = null;
			ArrayList alPrevClaimList = null;
			ArrayList<Object> alClaimList = null;
			String strDetail = "";
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strFlowType = "";
			String strPreAuthFlow = "PRE";
			String strClaimFlow = "CLM";
			// String strForward = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strFlowType = strPreAuthFlow;
				strDetail = "preauthdetail";
				preAuthObject = this.getPreAuthManagerObject();
				if (frmPreAuthGeneral.get("preAuthSeqID") != null
						&& !frmPreAuthGeneral.get("preAuthSeqID").equals("")) {
					preauthVO = new PreAuthVO();
					preauthVO.setMemberSeqID(PreAuthWebBoardHelper
							.getMemberSeqId(request));
					preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper
							.getPreAuthSeqId(request));
					preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper
							.getEnrollmentDetailId(request));
					preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
					// call the business layer to get the Pre-Auth detail
					preAuthDetailVO = preAuthObject.getPreAuthDetail(preauthVO,
							PreAuthWebBoardHelper.getPreAuthStatus(request));
					strCaption.append(" Edit");
					strCaption.append(" [ "
							+ PreAuthWebBoardHelper.getClaimantName(request)
							+ " ]");
					if (PreAuthWebBoardHelper.getEnrollmentId(request) != null
							&& (!"".equals(PreAuthWebBoardHelper
									.getEnrollmentId(request).trim())))
						strCaption.append(" [ "
								+ PreAuthWebBoardHelper
										.getEnrollmentId(request) + " ]");
					if (!preAuthDetailVO.getPreAuthHospitalVO()
							.getEmpanelStatusTypeID().equals("")
							&& !preAuthDetailVO.getPreAuthHospitalVO()
									.getEmpanelStatusTypeID().equals("EMP")) {
						preAuthDetailVO.getPreAuthHospitalVO().setStatusDisYN(
								"Y");
					}// end of if
				}// end of if(frmPreAuthGeneral.get("preAuthSeqID")!=null &&
					// !frmPreAuthGeneral.get("preAuthSeqID").equals(""))
				else {
					preAuthDetailVO = new PreAuthDetailVO();
					preAuthHospitalVO = new PreAuthHospitalVO();
					additionalDetailVO = new AdditionalDetailVO();
					claimantVO = new ClaimantVO();
					preAuthDetailVO.setDiscPresentYN("N");
					preAuthDetailVO.setCompletedYN("N");// remove this value
					preAuthDetailVO.setPreAuthTypeID("MAN");// from add mode
															// pre-auth type
															// will be manual.
					preAuthDetailVO.setPreAuthHospitalVO(preAuthHospitalVO);
					preAuthDetailVO.setAdditionalDetailVO(additionalDetailVO);
					preAuthDetailVO.setClaimantVO(claimantVO);
					preAuthDetailVO.setPriorityTypeID("MID");// from add mode
																// priority will
																// be medium.
					preAuthDetailVO.setPreAuthRecvTypeID("FAX");// default is
																// fax
					strCaption.append(" Add ");
				}// end of else if(frmPreAuthGeneral.get("preAuthSeqID")!=null
					// &&
					// !frmPreAuthGeneral.get("preAuthSeqID").equals(""))
				frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
						"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
						request);
				frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital",
								preAuthDetailVO.getPreAuthHospitalVO(), this,
								mapping, request));
				frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
						.setFormValues("frmClaimantDetails",
								preAuthDetailVO.getClaimantVO(), this, mapping,
								request));
				frmPreAuthGeneral.set("additionalDetailVO", FormUtils
						.setFormValues("frmAdditionalDetail",
								preAuthDetailVO.getAdditionalDetailVO(), this,
								mapping, request));
				claimDetailVO = new ClaimDetailVO();
				frmPreAuthGeneral.set("claimDetailVO", FormUtils
						.setFormValues("frmClaimDetail", claimDetailVO, this,
								mapping, request));
				frmPreAuthGeneral.set("caption", strCaption.toString());
				if (userSecurityProfile.getBranchID() != null) {
					frmPreAuthGeneral.set("officeSeqID", userSecurityProfile
							.getBranchID().toString());
				}// end of if(userSecurityProfile.getBranchID()!=null)
				else {
					frmPreAuthGeneral.set("officeSeqID", "");
				}// end of else
				if (preAuthDetailVO.getPreAuthTypeID().equals("REG")) {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Regular");
				}// end of if(preAuthDetailVO.getPreAuthTypeID().equals("REG"))
				else if (preAuthDetailVO.getPreAuthTypeID().equals("MRG")) {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Manual-Regular");
				}// end of else
					// if(preAuthDetailVO.getPreAuthTypeID().equals("MRG"))
				else {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");
				}// end of else
				if (frmPreAuthGeneral.get("preAuthSeqID") == null) {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");// to
																		// display
																		// as
																		// manual
																		// in
																		// the
																		// add
																		// mode.
				}// end of if(frmPreAuthGeneral.get("preAuthSeqID")!=null &&
					// !frmPreAuthGeneral.get("preAuthSeqID").equals(""))
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				// strForward="claimsdetail";
				strFlowType = strClaimFlow;
				strDetail = "dataentryclaimsdetail";
				claimObject = this.getClaimManagerObject();
				alClaimList = new ArrayList<Object>();
				alClaimList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alClaimList.add(ClaimsWebBoardHelper
						.getClmEnrollDetailSeqId(request));
				alClaimList.add(ClaimsWebBoardHelper.getMemberSeqId(request));
				alClaimList.add(TTKCommon.getUserSeqId(request));
				// call the business layer to get the Claim detail
				preAuthDetailVO = claimObject.getClaimDetail(alClaimList);
				if (preAuthDetailVO.getPrevClaimList() != null) {
					alPrevClaimList = preAuthDetailVO.getPrevClaimList();
				}// end of if(preAuthDetailVO.getPrevClaimList()!=null)
				if (!preAuthDetailVO.getPreAuthHospitalVO()
						.getEmpanelStatusTypeID().equals("")
						&& !preAuthDetailVO.getPreAuthHospitalVO()
								.getEmpanelStatusTypeID().equals("EMP")) {
					preAuthDetailVO.getPreAuthHospitalVO().setStatusDisYN("Y");
				}// end of if
					// to make claimsubtypeid as hospitalization when claim in
					// NHCP
				if (preAuthDetailVO.getClaimDetailVO().getClaimTypeID()
						.equals("CNH")) {
					preAuthDetailVO.getClaimDetailVO().setClaimSubTypeID("CSH");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
					// Added as per KOC 1285 Change Request
				if (!preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
						.equalsIgnoreCase("CSD")) {
					// s
					preAuthDetailVO.getClaimDetailVO().setDomicilaryReason("");
					preAuthDetailVO.getClaimDetailVO().setDoctorCertificateYN(
							"");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CSD"))
				if (preAuthDetailVO.getClaimDetailVO().getClaimSubTypeID()
						.equalsIgnoreCase("CSD")) {
					if (preAuthDetailVO.getClaimDetailVO()
							.getDoctorCertificateYN().equalsIgnoreCase("Y"))
						request.setAttribute("doctorCertificateYN", "Y");
					else
						request.setAttribute("doctorCertificateYN", "N");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CSD"))
				// Added as per KOC 1285 Change Request

				frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
						"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
						request);
				frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital",
								preAuthDetailVO.getPreAuthHospitalVO(), this,
								mapping, request));
				frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
						.setFormValues("frmClaimantDetails",
								preAuthDetailVO.getClaimantVO(), this, mapping,
								request));
				frmPreAuthGeneral.set("claimDetailVO", FormUtils.setFormValues(
						"frmClaimDetail", preAuthDetailVO.getClaimDetailVO(),
						this, mapping, request));
				additionalDetailVO = new AdditionalDetailVO();
				frmPreAuthGeneral.set("additionalDetailVO", FormUtils
						.setFormValues("frmAdditionalDetail",
								additionalDetailVO, this, mapping, request));
				frmPreAuthGeneral.set("hmPrevHospList",
						preAuthDetailVO.getPrevHospDetails());
				strCaption.append(" Edit");
				strCaption.append(" [ "
						+ ClaimsWebBoardHelper.getClaimantName(request) + " ]");
				if (ClaimsWebBoardHelper.getEnrollmentId(request) != null
						&& (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(
								request).trim()))) {
					strCaption.append(" [ "
							+ ClaimsWebBoardHelper.getEnrollmentId(request)
							+ " ]");
				}// end of if(ClaimsWebBoardHelper.getEnrollmentId(request) !=
					// null &&
					// (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				frmPreAuthGeneral.set("caption", strCaption.toString());
			}// end of if(strActiveTab.equals(strClaims))
			if (preAuthDetailVO.getEnrolChangeMsg() != null
					&& preAuthDetailVO.getEnrolChangeMsg() != "") {
				request.setAttribute("enrollmentChange",
						"message.enrollmentChange");
			}// end of if(preAuthDetailVO.getEnrolChangeMsg()!=null &&
				// preAuthDetailVO.getEnrolChangeMsg()!="")
			frmPreAuthGeneral.set("flowType", strFlowType.toString());// set the
																		// value
																		// from
																		// which
																		// flow
																		// ur
																		// are.
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& preAuthDetailVO.getDiscPresentYN().equals("Y"));
			frmPreAuthGeneral.set("alPrevClaimList", alPrevClaimList);
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthGeneral",
					frmPreAuthGeneral);
			this.documentViewer(request, preAuthDetailVO);
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to Select Enrollment ID. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doSelectEnrollmentID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doSelectEnrollmentID ");
			setLinks(request);
			String strForward = "";
			strForward = strEnrollmentlist;
			return mapping.findForward(strForward);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doSelectEnrollmentID(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to navigate to previous screen when closed button is
	 * clicked. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doHospitalClose(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doHospitalClose ");
			setLinks(request);
			String strForward = "";
			strForward = "preauthdetail";
			return mapping.findForward(strForward);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doHospitalClose(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Delete Enrollment ID from the General Details
	 * screen. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doClearEnrollmentID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doClearEnrollmentID ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			ClaimantVO claimantVO = null;
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			ParallelClaimManager claimObject = null;
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "dataentryclaimsdetail";
				claimObject = this.getClaimManagerObject();
			}// end of if(strActiveTab.equals(strClaims))

			DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthGeneral
					.get("claimantDetailsVO");
			// if pre-auth is in process, then change pre-auth type to manual.
			if (frmPreAuthGeneral.get("completedYN").equals("N")) {
				// If it is not a Manual-Regular PreAuth made it as Manual
				if (!("MRG".equals(frmPreAuthGeneral.get("preAuthTypeID")))) {
					frmPreAuthGeneral.set("preAuthTypeID", "MAN");
					frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");
				}// end of
					// if(!("MRG".equals(frmPreAuthGeneral.get("preAuthTypeID"))))
				claimantVO = new ClaimantVO();
				frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
						.setFormValues("frmClaimantDetails", claimantVO, this,
								mapping, request));
			}// end of if(frmPreAuthGeneral.get("completedYN").equals("N"))
			else {
				frmClaimantDetails.set("enrollmentID", "");
			}// end of else
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& frmPreAuthGeneral.get("discPresentYN").equals(
									"Y"));
			if (((frmPreAuthGeneral.get("authNbr") != null && (!""
					.equals(frmPreAuthGeneral.get("authNbr")))) || (frmPreAuthGeneral
					.get("prevHospClaimSeqID") != null))
					&& strActiveTab.equals(strClaims)) {
				clearPreauthHospital(mapping, frmPreAuthGeneral, request,
						claimObject, "");
			}// end of if(((frmPreAuthGeneral.get("authNbr") != null &&
				// (!"".equals(frmPreAuthGeneral.get("authNbr")))) ||
				// (frmPreAuthGeneral.get("prevHospClaimSeqID") != null)) &&
				// strActiveTab.equals(strClaims))
			frmPreAuthGeneral.set("frmChanged", "changed");
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doClearEnrollmentID(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Select the Insurance Company. Finally it forwards
	 * to the appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doSelectInsurance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doSelectInsurance ");
			setLinks(request);
			String strForward = "";
			strForward = strInsurancelist;
			return mapping.findForward(strForward);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doSelectInsurance(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Clear the Insurance Company Name from the General
	 * Details screen. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doClearInsurance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doClearInsurance ");
			setLinks(request);
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "dataentryclaimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthGeneral
					.get("claimantDetailsVO");
			// if pre-auth is in process, then change pre-auth type to manual.
			if (frmPreAuthGeneral.get("completedYN").equals("N")) {
				frmClaimantDetails.set("policySeqID", "");
				frmClaimantDetails.set("policyNbr", "");
				frmClaimantDetails.set("policyTypeID", "");
				frmClaimantDetails.set("termStatusID", "");
				frmClaimantDetails.set("policySubTypeID", "");
				frmClaimantDetails.set("policyHolderName", "");
				frmClaimantDetails.set("phone", "");
				frmClaimantDetails.set("startDate", "");
				frmClaimantDetails.set("endDate", "");
				frmClaimantDetails.set("companyName", "");
				frmClaimantDetails.set("companyCode", "");
				frmClaimantDetails.set("insSeqID", "");
				frmClaimantDetails.set("groupRegnSeqID", "");
				frmClaimantDetails.set("productSeqID", "");
				frmClaimantDetails.set("groupID", "");
				frmClaimantDetails.set("groupName", "");
			}// end of if
			else {
				frmClaimantDetails.set("policySeqID", "");
				frmClaimantDetails.set("policyNbr", "");
			}// end of else
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& frmPreAuthGeneral.get("discPresentYN").equals(
									"Y"));
			frmPreAuthGeneral.set("frmChanged", "changed");
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doClearInsurance(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Select Policy. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doSelectPolicy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doSelectPolicy ");
			setLinks(request);
			String strForward = "";
			strForward = strPolicylist;
			return mapping.findForward(strForward);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doSelectPolicy(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Select Corporate. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doSelectCorporate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doSelectCorporate ");
			setLinks(request);
			String strForward = "";
			strForward = strCorporatelist;
			return mapping.findForward(strForward);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doSelectCorporate(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Select Hospital from the grid screen. Finally it
	 * forwards to the appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doSelectHospital(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doSelectHospital ");
			setLinks(request);
			return mapping.findForward(strHospitallist);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doSelectHospital(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Clear Hospital from the General Details screen.
	 * Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doClearHospital(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doClearHospital ");
			PreAuthHospitalVO preAuthHospitalVO = null;
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "dataentryclaimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			preAuthHospitalVO = new PreAuthHospitalVO();
			frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils.setFormValues(
					"frmPreAuthHospital", preAuthHospitalVO, this, mapping,
					request));
			frmPreAuthGeneral.set("frmChanged", "changed");
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doClearHospital(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Calculate the Enhancement Amount. Finally it
	 * forwards to the appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doEnhancementAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			String strForward = "";
			log.debug("Inside PreAuthGenealAction doEnhancementAmount ");
			strForward = strSumenhancementlist;
			return mapping.findForward(strForward);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doEnhancementAmount(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Select Authorization. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doSelectAuthorization(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			String strForward = "";
			log.debug("Inside PreAuthGenealAction doSelectAuthorization ");
			strForward = "dataentryauthorizationlist";
			return mapping.findForward(strForward);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doSelectAuthorization(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Clear Authorization. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doClearAuthorization(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doSelectAuthorization ");
			String strForward = "";
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			ParallelClaimManager claimObject = null;
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "dataentryclaimsdetail";
				claimObject = this.getClaimManagerObject();
			}// end of if(strActiveTab.equals(strClaims))
			strForward = strDetail;
			if (frmPreAuthGeneral.get("authNbr") != null
					&& (!"".equals(frmPreAuthGeneral.get("authNbr")))
					&& strActiveTab.equals(strClaims))
				clearPreauthHospital(mapping, frmPreAuthGeneral, request,
						claimObject, strPre_Authorization);
			return mapping.findForward(strForward);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doClearAuthorization(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to get the Previous Hospitalization Information.
	 * Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doPrevHospitalization(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doPrevHospitalization ");
			String strForward = "";
			String strActiveTab = TTKCommon.getActiveLink(request);
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthHospitalVO preAuthHospitalVO = null;
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "dataentryclaimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			strForward = strDetail;
			HashMap hmPrevHospList = (HashMap) frmPreAuthGeneral
					.get("hmPrevHospList");
			if (!frmPreAuthGeneral.get("prevHospClaimSeqID").equals("")) {
				HospitalizationDetailVO hospitalizationDetailVO = (HospitalizationDetailVO) hmPrevHospList
						.get(TTKCommon.getLong(frmPreAuthGeneral.get(
								"prevHospClaimSeqID").toString()));
				frmPreAuthGeneral.set("clmAdmissionDate", TTKCommon
						.getFormattedDate(hospitalizationDetailVO
								.getAdmissionDate()));
				frmPreAuthGeneral.set("admissionDay",
						hospitalizationDetailVO.getAdmissionDay());
				frmPreAuthGeneral.set("clmAdmissionTime",
						hospitalizationDetailVO.getAdmissionTime());
				frmPreAuthGeneral.set("dischargeDate", TTKCommon
						.getFormattedDate(hospitalizationDetailVO
								.getDischargeDate()));
				frmPreAuthGeneral.set("dischargeDay",
						hospitalizationDetailVO.getDischargeDay());
				frmPreAuthGeneral.set("dischargeTime",
						hospitalizationDetailVO.getDischargeTime());
				frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital",
								hospitalizationDetailVO.getPreauthHospitalVO(),
								this, mapping, request));
			}// end of
				// if(!frmPreAuthGeneral.get("prevHospClaimSeqID").equals(""))
			else {
				preAuthHospitalVO = new PreAuthHospitalVO();
				frmPreAuthGeneral.set("clmAdmissionDate", "");
				frmPreAuthGeneral.set("admissionDay", "");
				frmPreAuthGeneral.set("clmAdmissionTime", "");
				frmPreAuthGeneral.set("dischargeDate", "");
				frmPreAuthGeneral.set("dischargeDay", "");
				frmPreAuthGeneral.set("dischargeTime", "");
				frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital", preAuthHospitalVO,
								this, mapping, request));
			}// end of else
				// if(!frmPreAuthGeneral.get("prevHospClaimSeqID").equals(""))
			frmPreAuthGeneral.set("frmChanged", "changed");
			return mapping.findForward(strForward);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doPrevHospitalization(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to get the Discrepancies Information. Finally it
	 * forwards to the appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doDiscrepancies(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doDiscrepancies ");
			return mapping.findForward("discrepancy");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doDiscrepancies(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Override. Finally it forwards to the appropriate
	 * view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doOverride(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doOverride ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			ParallelPreAuthManager preAuthObject = null;
			ParallelClaimManager claimObject = null;
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
				preAuthObject = this.getPreAuthManagerObject();
				// call the bussiness layer to override the completed PreAuth
				preAuthDetailVO = preAuthObject.overridePreauth(
						PreAuthWebBoardHelper.getPreAuthSeqId(request),
						PreAuthWebBoardHelper.getEnrollmentDetailId(request),
						TTKCommon.getUserSeqId(request));
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "dataentryclaimsdetail";
				claimObject = this.getClaimManagerObject();
				// call the bussiness layer to override the completed Claims
				preAuthDetailVO = claimObject.overrideClaim(
						ClaimsWebBoardHelper.getClaimsSeqId(request),
						TTKCommon.getUserSeqId(request));
			}// end of if(strActiveTab.equals(strClaims))

			if (preAuthDetailVO != null) {
				// set the review information back to form
				frmPreAuthGeneral.set("eventSeqID", preAuthDetailVO
						.getEventSeqID().toString());
				frmPreAuthGeneral.set("reviewCount", preAuthDetailVO
						.getReviewCount().toString());
				frmPreAuthGeneral.set("requiredReviewCnt", preAuthDetailVO
						.getRequiredReviewCnt().toString());
				frmPreAuthGeneral.set("review", preAuthDetailVO.getReview()
						.toString());
				frmPreAuthGeneral.set("eventName", preAuthDetailVO
						.getEventName().toString());

				// If Workflow is overrided set flag as N
				if (!(preAuthDetailVO.getEventName().contains("Complete"))) {
					frmPreAuthGeneral.set("showCodingOverrideYN", "Y");
					frmPreAuthGeneral.set("completedYN", "N");
				}// end of if("Complete".equals(preAuthDetailVO.getEventName()))
			}// end of if(preAuthDetailVO!=null)
			return this.getForward(strDetail, mapping, request);
		}// end of try

		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doOverride(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Re-associate the policy information. Finally it
	 * forwards to the appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doReassociateEnrollID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.info("Inside PreAuthGenealAction doOverride ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			ParallelPreAuthManager preAuthObject = this
					.getPreAuthManagerObject();
			ParallelClaimManager claimManager = this.getClaimManagerObject();
			ArrayList<Object> alReAssignID = new ArrayList<Object>();
			String strActiveLink = TTKCommon.getActiveLink(request);
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthVO preauthVO = null;
			ClaimDetailVO claimDetailVO = null;
			StringBuffer strCaption = new StringBuffer();
			AdditionalDetailVO additionalDetailVO = null;
			String strClaimFlow = "CLM";
			String strFlowType = "";
			String strDetail = "";
			if (strActiveLink.equals(strPre_Authorization)) {
				alReAssignID
						.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alReAssignID.add(PreAuthWebBoardHelper
						.getEnrollmentDetailId(request));
				alReAssignID
						.add((String) frmPreAuthGeneral.get("statusTypeID"));
				alReAssignID.add(PreAuthWebBoardHelper.getMemberSeqId(request));
				alReAssignID.add(TTKCommon.getUserSeqId(request));
				strFlowType = "PRE";
				int iResult = preAuthObject.reAssignEnrID(alReAssignID);
				if (iResult > 0) {
					request.setAttribute("updated",
							"message.enrollmentInfoChange");
				}// end of if(iResult>0)
				strDetail = "preauthdetail";
				strCaption.append(" Edit");
				preauthVO = new PreAuthVO();
				preauthVO.setMemberSeqID(PreAuthWebBoardHelper
						.getMemberSeqId(request));
				preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper
						.getPreAuthSeqId(request));
				preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper
						.getEnrollmentDetailId(request));
				preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				// call the business layer to get the Pre-Auth detail
				preAuthDetailVO = preAuthObject.getPreAuthDetail(preauthVO,
						PreAuthWebBoardHelper.getPreAuthStatus(request));
				strCaption
						.append(" [ "
								+ PreAuthWebBoardHelper
										.getClaimantName(request) + " ]");
				if (PreAuthWebBoardHelper.getEnrollmentId(request) != null
						&& (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(
								request).trim())))
					strCaption.append(" [ "
							+ PreAuthWebBoardHelper.getEnrollmentId(request)
							+ " ]");

				if (!preAuthDetailVO.getPreAuthHospitalVO()
						.getEmpanelStatusTypeID().equals("")
						&& !preAuthDetailVO.getPreAuthHospitalVO()
								.getEmpanelStatusTypeID().equals("EMP")) {
					preAuthDetailVO.getPreAuthHospitalVO().setStatusDisYN("Y");
				}// end of
					// if(!preAuthDetailVO.getPreAuthHospitalVO().getEmpanelStatusTypeID().equals("EMP"))
				if (preAuthDetailVO.getEnrolChangeMsg() != null
						&& preAuthDetailVO.getEnrolChangeMsg() != "") {
					request.setAttribute("enrollmentChange",
							"message.enrollmentChange");
				}// end of if(preAuthDetailVO.getEnrolChangeMsg()!=null &&
					// preAuthDetailVO.getEnrolChangeMsg()!="")
				frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
						"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
						request);
				frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital",
								preAuthDetailVO.getPreAuthHospitalVO(), this,
								mapping, request));
				frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
						.setFormValues("frmClaimantDetails",
								preAuthDetailVO.getClaimantVO(), this, mapping,
								request));
				frmPreAuthGeneral.set("additionalDetailVO", FormUtils
						.setFormValues("frmAdditionalDetail",
								preAuthDetailVO.getAdditionalDetailVO(), this,
								mapping, request));
				if (preAuthDetailVO.getPreAuthTypeID().equals("REG")) {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Regular");
				}// end of if(preAuthDetailVO.getPreAuthTypeID().equals("REG"))
				else if (preAuthDetailVO.getPreAuthTypeID().equals("MRG")) {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Manual-Regular");
				}// end of else
					// if(preAuthDetailVO.getPreAuthTypeID().equals("MRG")){
				else {
					frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");
				}// end of else
				claimDetailVO = new ClaimDetailVO();
				frmPreAuthGeneral.set("claimDetailVO", FormUtils
						.setFormValues("frmClaimDetail", claimDetailVO, this,
								mapping, request));
				// check for conflict value

			} else if (strActiveLink.equals(strClaims)) {
				log.info("##########################################"
						+ frmPreAuthGeneral.get("statusTypeID"));
				alReAssignID.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alReAssignID
						.add((String) frmPreAuthGeneral.get("statusTypeID"));
				alReAssignID.add(ClaimsWebBoardHelper.getMemberSeqId(request));
				alReAssignID.add(TTKCommon.getUserSeqId(request));
				// ArrayList alPrevClaimList=new ArrayList();
				strDetail = "dataentryclaimsdetail";
				int iResult = claimManager.reAssignEnrID(alReAssignID);
				if (iResult > 0) {
					request.setAttribute("updated",
							"message.enrollmentInfoChange");
				}// end of if(iResult>0)
				strFlowType = strClaimFlow;
				strDetail = "dataentryclaimsdetail";
				claimManager = this.getClaimManagerObject();
				ArrayList<Object> alReAssignList = new ArrayList<Object>();
				alReAssignList
						.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alReAssignList.add(ClaimsWebBoardHelper
						.getClmEnrollDetailSeqId(request));
				alReAssignList
						.add(ClaimsWebBoardHelper.getMemberSeqId(request));
				alReAssignList.add(TTKCommon.getUserSeqId(request));
				// call the business layer to get the Claim detail
				preAuthDetailVO = claimManager.getClaimDetail(alReAssignList);
				if (preAuthDetailVO.getPrevClaimList() != null) {
					// alPrevClaimList =preAuthDetailVO.getPrevClaimList();
				}// end of if(preAuthDetailVO.getPrevClaimList()!=null)
				if (!preAuthDetailVO.getPreAuthHospitalVO()
						.getEmpanelStatusTypeID().equals("")
						&& !preAuthDetailVO.getPreAuthHospitalVO()
								.getEmpanelStatusTypeID().equals("EMP")) {
					preAuthDetailVO.getPreAuthHospitalVO().setStatusDisYN("Y");
				}// end of if
					// to make claimsubtypeid as hospitalization when claim in
					// NHCP
				if (preAuthDetailVO.getClaimDetailVO().getClaimTypeID()
						.equals("CNH")) {
					preAuthDetailVO.getClaimDetailVO().setClaimSubTypeID("CSH");
				}// end of
					// if(preAuthDetailVO.getClaimDetailVO().getClaimTypeID().equals("CNH"))
				frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
						"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
						request);
				frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital",
								preAuthDetailVO.getPreAuthHospitalVO(), this,
								mapping, request));
				frmPreAuthGeneral.set("claimantDetailsVO", FormUtils
						.setFormValues("frmClaimantDetails",
								preAuthDetailVO.getClaimantVO(), this, mapping,
								request));
				frmPreAuthGeneral.set("claimDetailVO", FormUtils.setFormValues(
						"frmClaimDetail", preAuthDetailVO.getClaimDetailVO(),
						this, mapping, request));
				additionalDetailVO = new AdditionalDetailVO();
				frmPreAuthGeneral.set("additionalDetailVO", FormUtils
						.setFormValues("frmAdditionalDetail",
								additionalDetailVO, this, mapping, request));
				frmPreAuthGeneral.set("hmPrevHospList",
						preAuthDetailVO.getPrevHospDetails());
				strCaption.append(" Edit");
				strCaption.append(" [ "
						+ ClaimsWebBoardHelper.getClaimantName(request) + " ]");
				if (ClaimsWebBoardHelper.getEnrollmentId(request) != null
						&& (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(
								request).trim()))) {
					strCaption.append(" [ "
							+ ClaimsWebBoardHelper.getEnrollmentId(request)
							+ " ]");
				}// end of if(ClaimsWebBoardHelper.getEnrollmentId(request) !=
					// null &&
					// (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					// frmPreAuthGeneral.set("caption",strCaption.toString());
			}// end of else if(strActiveLink.equals(strClaims))
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& preAuthDetailVO.getDiscPresentYN().equals("Y"));
			frmPreAuthGeneral.set("flowType", strFlowType.toString());// set the
																		// value
																		// from
																		// which
																		// flow
																		// ur
																		// are.
			frmPreAuthGeneral.set("caption", strCaption.toString());
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthGeneral",
					frmPreAuthGeneral);
			this.documentViewer(request, preAuthDetailVO);
			return this.getForward(strDetail, mapping, request);
		}// end of try

		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doReassociateEnrollID(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to set the form values on change of Policy Type.
	 * Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doChangePolicyType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside the doChangePolicyType method of BatchPolicyAction");
			setLinks(request);
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) request
					.getSession().getAttribute("frmPreAuthGeneral");
			DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthGeneral
					.get("claimantDetailsVO");
			DynaActionForm frmAdditionalDetail = (DynaActionForm) frmPreAuthGeneral
					.get("additionalDetailVO");
			String strDetail = "";
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strPolicyTypeID = (String) frmClaimantDetails
					.get("policyTypeID");

			if (strActiveTab.equals(strPre_Authorization)) {
				if (strPolicyTypeID.equals("COR")) {
					frmAdditionalDetail.set("insScheme", "");
					frmAdditionalDetail.set("certificateNo", "");
					frmClaimantDetails.set("policyHolderName", "");
				}// end of if(strPolicyTypeID.equals("COR"))
				if (strPolicyTypeID.equals("NCR")
						&& ((String) frmPreAuthGeneral.get("preAuthTypeID"))
								.equals("MAN")) {
					frmAdditionalDetail.set("employeeName", "");
					frmAdditionalDetail.set("joiningDate", "");
					frmAdditionalDetail.set("employeeNbr", "");
				}// end of if(strPolicyTypeID.equals("NCR")&&
					// ((String)frmPreAuthGeneral.get("preAuthTypeID")).equals("MAN"))
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				if (strPolicyTypeID.equals("COR")) {
					frmClaimantDetails.set("policyHolderName", "");
				}// end of if(strPolicyTypeID.equals("COR"))
				strDetail = "dataentryclaimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			frmClaimantDetails.set("groupRegnSeqID", "");
			frmClaimantDetails.set("groupID", "");
			frmClaimantDetails.set("groupName", "");
			frmPreAuthGeneral.set("frmChanged", "changed");
			request.getSession().setAttribute("frmPreAuthGeneral",
					frmPreAuthGeneral);
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doChangePolicyType(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the ParallelPreAuthManager session object for invoking methods on
	 * it.
	 * 
	 * @return ParallelPreAuthManager session object which can be used for
	 *         method invokation
	 * @exception throws TTKException
	 */
	private ParallelPreAuthManager getPreAuthManagerObject()
			throws TTKException {
		ParallelPreAuthManager preAuthManager = null;
		try {
			if (preAuthManager == null) {
				InitialContext ctx = new InitialContext();
				preAuthManager = (ParallelPreAuthManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/ParallelPreAuthManagerBean!com.ttk.business.dataentrypreauth.ParallelPreAuthManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strPreAuthDetail);
		}// end of catch
		return preAuthManager;
	}// end getPreAuthManagerObject()

	/**
	 * Returns the ParallelClaimManager session object for invoking methods on
	 * it.
	 * 
	 * @return ParallelClaimManager session object which can be used for method
	 *         invokation
	 * @exception throws TTKException
	 */
	private ParallelClaimManager getClaimManagerObject() throws TTKException {
		ParallelClaimManager claimManager = null;
		try {
			if (claimManager == null) {
				InitialContext ctx = new InitialContext();
				claimManager = (ParallelClaimManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/ParallelClaimManagerBean!com.ttk.business.dataentryclaims.ParallelClaimManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strPreAuthDetail);
		}// end of catch
		return claimManager;
	}// end getClaimManagerObject()

	/**
	 * This method for document viewer information
	 * 
	 * @param request
	 *            HttpServletRequest object which contains Pre-Authorization
	 *            information.
	 * @param preAuthDetailVO
	 *            PreAuthDetailVO object which contains Pre-Authorization
	 *            information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request,
			PreAuthDetailVO preAuthDetailVO) throws TTKException {
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink=" + TTKCommon.getActiveLink(request));
		if (TTKCommon.getActiveLink(request).equalsIgnoreCase(
				strPre_Authorization)) {
			alDocviewParams.add("pre_auth_number="
					+ TTKCommon.getWebBoardDesc(request));
			alDocviewParams.add("dms_reference_number="
					+ preAuthDetailVO.getDMSRefID());
		}// end of
			// if(TTKCommon.getActiveLink(request).equalsIgnoreCase(strPre_Authorization))
		else if (TTKCommon.getActiveLink(request).equalsIgnoreCase(strClaims)) {
			// alDocviewParams.add("claim_number="+TTKCommon.getWebBoardDesc(request));
			alDocviewParams
					.add("claimno=" + TTKCommon.getWebBoardDesc(request));
			alDocviewParams.add("dms_reference_number="
					+ preAuthDetailVO.getDMSRefID());
			// added for KOC-1267
			alDocviewParams.add("userid=" + preAuthDetailVO.getUser());
			alDocviewParams.add("roleid=INTERNAL");
		}// end of else

		if (request.getSession().getAttribute("toolbar") != null) {
			((Toolbar) request.getSession().getAttribute("toolbar"))
					.setDocViewParams(alDocviewParams);
		}// end of if(request.getSession().getAttribute("toolbar")!=null)
	}// end of documentViewer(HttpServletRequest request,PreAuthDetailVO
		// preAuthDetailVO)

	/**
	 * This method prepares the Weboard id for the selected Policy
	 * 
	 * @param preAuthDetailVO
	 *            preAuthVO for which webboard id to be prepared
	 * @param String
	 *            strIdentifier whether it is preauth or enhanced preauth
	 * @param String
	 *            strModule identifier Pre-Authorization/Claims
	 * @return Web board id for the passedVO
	 */
	private String prepareWebBoardId(PreAuthVO preAuthVO, String strIdentifier,
			String strModule) throws TTKException {
		StringBuffer sbfCacheId = new StringBuffer();
		if (strModule.equals(strPre_Authorization)) {
			sbfCacheId.append(preAuthVO.getPreAuthSeqID() != null ? String
					.valueOf(preAuthVO.getPreAuthSeqID()) : " ");
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getEnrollmentID())
							.equals("") ? " " : preAuthVO.getEnrollmentID());
			sbfCacheId.append("~#~").append(
					preAuthVO.getEnrollDtlSeqID() != null ? String
							.valueOf(preAuthVO.getEnrollDtlSeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getPolicySeqID() != null ? String
							.valueOf(preAuthVO.getPolicySeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getMemberSeqID() != null ? String
							.valueOf(preAuthVO.getMemberSeqID()) : " ");
			sbfCacheId.append("~#~").append(strIdentifier);
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getClaimantName())
							.equals("") ? " " : preAuthVO.getClaimantName());
			sbfCacheId.append("~#~").append(
					TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals(
							"") ? " " : preAuthVO.getBufferAllowedYN());
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getShowBandYN())
							.equals("") ? " " : preAuthVO.getShowBandYN());
			sbfCacheId
					.append("~#~")
					.append(TTKCommon
							.checkNull(preAuthVO.getCoding_review_yn()).equals(
									"") ? " " : preAuthVO.getCoding_review_yn());
		}// end of if(strModule.equals(strPre_Authorization))
		else if (strModule.equals(strClaims)) {
			sbfCacheId.append(preAuthVO.getClaimSeqID() != null ? String
					.valueOf(preAuthVO.getClaimSeqID()) : " ");
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getEnrollmentID())
							.equals("") ? " " : preAuthVO.getEnrollmentID());
			sbfCacheId.append("~#~").append(
					preAuthVO.getEnrollDtlSeqID() != null ? String
							.valueOf(preAuthVO.getEnrollDtlSeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getPolicySeqID() != null ? String
							.valueOf(preAuthVO.getPolicySeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getMemberSeqID() != null ? String
							.valueOf(preAuthVO.getMemberSeqID()) : " ");
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getClaimantName())
							.equals("") ? " " : preAuthVO.getClaimantName());
			sbfCacheId.append("~#~").append(
					TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals(
							"") ? " " : preAuthVO.getBufferAllowedYN());
			sbfCacheId.append("~#~").append(
					preAuthVO.getClmEnrollDtlSeqID() != null ? String
							.valueOf(preAuthVO.getClmEnrollDtlSeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getAmmendmentYN() != null ? String
							.valueOf(preAuthVO.getAmmendmentYN()) : " ");
			sbfCacheId
					.append("~#~")
					.append(TTKCommon
							.checkNull(preAuthVO.getCoding_review_yn()).equals(
									"") ? " " : preAuthVO.getCoding_review_yn());
		}// end of else
		return sbfCacheId.toString();
	}// end of prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws
		// TTKException

	/**
	 * Adds the selected item to the web board and makes it as the selected item
	 * in the web board
	 * 
	 * @param preauthVO
	 *            object which contains the information of the preauth * @param
	 *            String strIdentifier whether it is preauth or enhanced preauth
	 * @param request
	 *            HttpServletRequest
	 * @throws TTKException
	 *             if any runtime exception occures
	 */
	private void addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,
			String strIdentifier) throws TTKException {
		Toolbar toolbar = (Toolbar) request.getSession()
				.getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO, strIdentifier,
				TTKCommon.getActiveLink(request))); // set the cacheID
		cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());

		alCacheObject.add(cacheObject);
		// if the object(s) are added to the web board, set the current web
		// board id
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

		// webboardinvoked attribute will be set as true in request scope
		// to avoid the replacement of web board id with old value if it is
		// called twice in same request scope
		request.setAttribute("webboardinvoked", "true");
	}// end of addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest
		// request,String strIdentifier)throws TTKException

	/**
	 * clear's the preauth and hospital information when the preauth exsists and
	 * try to change the enrollment info
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional DynaActionForm bean for this request (if any)
	 * @param request
	 *            HttpServletRequest
	 * @param session
	 *            object which can be used for method invokation
	 * @throws TTKException
	 *             if any runtime exception occures
	 */
	private void clearPreauthHospital(ActionMapping mapping,
			DynaActionForm frmPreAuthGeneral, HttpServletRequest request,
			ParallelClaimManager claimObject, String strIdentity)
			throws TTKException {
		try {
			PreAuthHospitalVO preAuthHospitalVO = null;
			/*
			 * if(ClaimsWebBoardHelper.getEnrollDetailSeqId(request) != null){
			 * int iResult =
			 * claimObject.releasePreauth(ClaimsWebBoardHelper.getEnrollDetailSeqId
			 * (request)); }//end of
			 * if(ClaimsWebBoardHelper.getEnrollDetailSeqId(request) != null)
			 */frmPreAuthGeneral.set("enrollDtlSeqID", "");
			frmPreAuthGeneral.set("authNbr", "");
			frmPreAuthGeneral.set("prevHospClaimSeqID", "");
			frmPreAuthGeneral.set("receivedDate", "");
			frmPreAuthGeneral.set("receivedTime", "");
			frmPreAuthGeneral.set("receivedDay", "");
			frmPreAuthGeneral.set("approvedAmt", "");
			frmPreAuthGeneral.set("preauthTypeDesc", "");
			if (!strPre_Authorization.equals(strIdentity)) {
				frmPreAuthGeneral.set("hmPrevHospList", new HashMap());
				frmPreAuthGeneral.set("clmAdmissionDate", "");
				frmPreAuthGeneral.set("clmAdmissionTime", "");
				frmPreAuthGeneral.set("admissionDay", "");
				frmPreAuthGeneral.set("dischargeDate", "");
				frmPreAuthGeneral.set("dischargeTime", "");
				frmPreAuthGeneral.set("dischargeDay", "");
			}// end of if(!strPre_Authorization.equals(strIdentity))
			frmPreAuthGeneral.set("frmChanged", "changed");
			preAuthHospitalVO = new PreAuthHospitalVO();
			frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils.setFormValues(
					"frmPreAuthHospital", preAuthHospitalVO, this, mapping,
					request));
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strPreAuthDetail);
		}// end of catch
	}// end of clearPreauthHospital(ActionMapping mapping,DynaActionForm
		// frmPreAuthGeneral,HttpServletRequest request,ParallelClaimManager
		// claimObject)

	/**
	 * Methods checks whether user is having permession for the next Event.
	 * 
	 * @param lngEventSeqId
	 *            Long Event_Seq_Id.
	 * @param strSwitchType
	 *            String SwitchType.
	 * @param request
	 *            HttpServletRequest object.
	 * @return blnPermession boolean.
	 */
	private boolean checkReviewPermession(Long lngEventSeqId,
			HttpServletRequest request, String strActiveTab) {
		boolean blnPermession = false;
		WorkflowVO workFlowVO = null;
		ArrayList alEventId = null;
		// get the HashMap from UserSecurityProfile
		HashMap hmWorkFlow = ((UserSecurityProfile) request.getSession()
				.getAttribute("UserSecurityProfile")).getWorkFlowMap();
		if (strActiveTab.equals(strPre_Authorization)) {
			if (hmWorkFlow != null && hmWorkFlow.containsKey(new Long(3)))
				workFlowVO = (WorkflowVO) hmWorkFlow.get(new Long(3));// to get
																		// the
																		// work
																		// flow
																		// of
																		// pre-auth
		}// end of if(strActiveTab.equals(strPre_Authorization))
		if (strActiveTab.equals(strClaims)) {
			if (hmWorkFlow != null && hmWorkFlow.containsKey(new Long(4)))
				workFlowVO = (WorkflowVO) hmWorkFlow.get(new Long(4));// to get
																		// the
																		// work
																		// flow
																		// of
																		// claims
		}// end of if(strActiveTab.equals(strClaims))
			// get the arrayList which is having event information of the
			// particular user.
		if (workFlowVO != null) {
			alEventId = workFlowVO.getEventVO();
		}// end of if(workFlowVO!=null)
			// compare the current policy EventSeqId with the User permession.
		if (alEventId != null) {
			for (int i = 0; i < alEventId.size(); i++) {
				if (lngEventSeqId == ((EventVO) alEventId.get(i))
						.getEventSeqID()) {
					blnPermession = true;
					break;
				}// end of
					// if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
			}// end of for(int i=0;i<alEventId.size();i++)
		}// end of if(alEventId!=null)
		return blnPermession;
	}// end of checkReviewPermession(Long lngEventSeqId,HttpServletRequest
		// request,String strActiveTab)
}// end of PreAuthGeneralAction
