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

package com.ttk.action.partner;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Holder;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.onlineforms.OnlinePreAuthManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.business.webservice.dhpo.ValidateTransactions;
import com.ttk.business.webservice.dhpo.ValidateTransactionsSoap;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.claims.ClaimDetailVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.AdditionalDetailVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.DentalOrthoVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.ObservationDetailsVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;


/**
 * This class is reused for adding pre-auth/claims in pre-auth and claims flow.
 */
@SuppressWarnings("unchecked")
public class PartnerPreAuthGeneralAction extends TTKAction {

	private static Logger log = Logger.getLogger(PartnerPreAuthGeneralAction.class);

	private static final String strPreAuthDetail = "PreAuthDetails";
	private static final String strPreauthdetail = "preauthdetail";


	private static final String strPre_Authorization = "Pre-Authorization";
	private static final String strClaims = "Claims";
	private static final String strAuthorizationError = "authorizations";

	private static final String strForward = "Forward";
	private static final String strBackward = "Backward";
	private static final String strActivitydetails = "activitydetails";
	private static final String strDiagnosisCodeList = "diagnosisSearchList";
	private static final String strPreAuthViewHistory = "preauthViewHistory";
	private static final String strClaimViewHistory = "claimViewHistory";
	private static final String strPreauthHistoryList = "preauthHistoryList";

	

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
	public ActionForward doOralView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction dooralView ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			
			String s=(String) session.getAttribute("preAuthSeqID");
			
			Long preauthno=Long.valueOf((String) session.getAttribute("preAuthSeqID"));
			
			//Toolbar toolBar = (Toolbar) session.getAttribute("toolbar");
			PreAuthManager preAuthObject = null;
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthVO preauthVO = null;
			StringBuffer strCaption = new StringBuffer();
			String strDetail = "preauthdetail";
			preAuthObject = this.getPreAuthManagerObject();
           
			strCaption.append(" Edit");
			// check if user trying to hit the tab directly with out selecting
			// the pre-auth
			/*if (PreAuthWebBoardHelper.checkWebBoardId(request) == null) {
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.PreAuthorization.required");
				session.setAttribute("preauthDiagnosis", null);
				session.setAttribute("preauthActivities", null);
				session.setAttribute("preauthShortfalls", null);
				frmPreAuthGeneral.initialize(mapping);
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				throw expTTK;
			}// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
*/
			preauthVO = new PreAuthVO();
			//preauthVO.setMemberSeqID(PreAuthWebBoardHelper.getMemberSeqId(request));
			//preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			//preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
			//preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			// call the business layer to get the Pre-Auth detail
			// preauthVO.setPreAuthSeqID(Long.valueOf(request.getParameter("preauthno").toString()));
			
				
			preAuthDetailVO = preAuthObject.getPreAuthDetails(preauthno);
			
			preAuthDetailVO.setProviderId(preAuthDetailVO.getStrHospitalId());

			strCaption.append(" [ "
					+ PreAuthWebBoardHelper.getClaimantName(request) + " ]");
			if (PreAuthWebBoardHelper.getEnrollmentId(request) != null
					&& (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(
							request).trim())))
				strCaption.append(
						" [ " + PreAuthWebBoardHelper.getEnrollmentId(request)
								+ " ]").append(
						" [ " + preAuthDetailVO.getPreAuthNoStatus() + " ] ");

			frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
					"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
					request);
			if ("N".equals(frmPreAuthGeneral.getString("networkProviderType"))) {
				session.setAttribute("providerStates", TTKCommon
						.getStates(frmPreAuthGeneral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmPreAuthGeneral
								.getString("providerEmirate")));
			}
			session.setAttribute("encounterTypes", preAuthObject
					.getEncounterTypes(frmPreAuthGeneral
							.getString("benefitType")));
			// check for conflict value
			/*toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& preAuthDetailVO.getDiscPresentYN().equals("Y"));*/
			frmPreAuthGeneral.set("caption", strCaption.toString());
			// added for CR KOC-1273
			
			// keep the frmPreAuthGeneral in session scope
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
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
	
	
	
	public ActionForward doView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			setLinks(request);
			log.debug("Inside partnerPreAuthGeneralAction doView ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			Toolbar toolBar = (Toolbar) session.getAttribute("toolbar");
			PreAuthManager preAuthObject = null;
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthVO preauthVO = null;
			StringBuffer strCaption = new StringBuffer();
			String strDetail = "preauthdetail";
			preAuthObject = this.getPreAuthManagerObject();

			strCaption.append(" Edit");
			// check if user trying to hit the tab directly with out selecting
			// the pre-auth
			if (PreAuthWebBoardHelper.checkWebBoardId(request) == null) {
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.PreAuthorization.required");
				session.setAttribute("preauthDiagnosis", null);
				session.setAttribute("preauthActivities", null);
				session.setAttribute("preauthShortfalls", null);
				frmPreAuthGeneral.initialize(mapping);
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				throw expTTK;
			}// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			preauthVO = new PreAuthVO();
			
			/*preauthVO.setMemberSeqID(PreAuthWebBoardHelper
					.getMemberSeqId(request));*/
			preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper
					.getPreAuthSeqId(request));
			/*preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper
					.getEnrollmentDetailId(request));*/
			preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			// call the business layer to get the Pre-Auth detail
			
			
			Object[] preauthAllresult = preAuthObject.getPartnerPreAuthDetails(preauthVO.getPreAuthSeqID());
			
			preAuthDetailVO = (PreAuthDetailVO) preauthAllresult[0];
			ArrayList<DiagnosisDetailsVO> diagnosis = (ArrayList<DiagnosisDetailsVO>) preauthAllresult[1];
			ArrayList<ActivityDetailsVO> activities = (ArrayList<ActivityDetailsVO>) preauthAllresult[2];
			ArrayList<String[]> shortfalls = (ArrayList<String[]>) preauthAllresult[3];
			session.setAttribute("preauthDiagnosis", diagnosis);
			session.setAttribute("preauthActivities", activities);
			session.setAttribute("preauthShortfalls", shortfalls);

			strCaption.append(" [ "
					+ PreAuthWebBoardHelper.getClaimantName(request) + " ]");
			if (PreAuthWebBoardHelper.getEnrollmentId(request) != null
					&& (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(
							request).trim())))
				strCaption.append(
						" [ " + PreAuthWebBoardHelper.getEnrollmentId(request)
								+ " ]");

			/*frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
					"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
					request);*/
			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			if ("N".equals(frmPreAuthGeneral.getString("networkProviderType"))) {
				session.setAttribute("providerStates", TTKCommon
						.getStates(frmPreAuthGeneral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmPreAuthGeneral
								.getString("providerEmirate")));
			}
			session.setAttribute("encounterTypes", preAuthObject
					.getEncounterTypes(frmPreAuthGeneral
							.getString("benefitType")));
			// check for conflict value
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& preAuthDetailVO.getDiscPresentYN().equals("Y"));
			frmPreAuthGeneral.set("caption", strCaption.toString());
			
			// added for CR KOC-1273

			// keep the frmPreAuthGeneral in session scope
			
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			session.setAttribute("preAuthDetailVO", preAuthDetailVO);
			/*System.out.println("in gen action preAuthDetailVO.getRevisedDiagnosis()===="+preAuthDetailVO.getRevisedDiagnosis());
			if(preAuthDetailVO.getRevisedDiagnosis()!=null)
			{
				request.setAttribute("styledisplay", "display");
			}*/
			request.setAttribute("flag", "true");
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
	public ActionForward generatePreauthLetter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			String authNum = frmPreAuthGeneral.getString("authNum");
			String preauthStatus = frmPreAuthGeneral.getString("preauthStatus");
			String preAuthNo	=	frmPreAuthGeneral.getString("preAuthNo"); 

			JasperReport mainJasperReport = null;
			JasperReport diagnosisJasperReport = null;
			JasperReport activityJasperReport = null;
			JasperPrint mainJasperPrint = null;
			String parameter = "";
			
			String mainJrxmlfile = "generalreports/PreAuthApprovalOrDenialLetter.jrxml";
			String diagnosisJrxmlfile = "generalreports/DiagnosisDoc.jrxml";
			String activityJrxmlfile = "generalreports/ActivitiesDoc.jrxml";
			
			TTKReportDataSource mainTtkReportDataSource = null;
			TTKReportDataSource diagnosisTtkReportDataSource = null;
			TTKReportDataSource activityTtkReportDataSource = null;

			ByteArrayOutputStream boas = new ByteArrayOutputStream();

			String strPdfFile = TTKPropertiesReader
					.getPropertyValue("authorizationrptdir") + preAuthNo + ".pdf"; 
			JasperReport emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			  // mainJasperReport = JasperCompileManager.compileReport(mainJrxmlfile);
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			
			parameter = "|" + preAuthSeqID + "|" + preauthStatus + "|PAT|";
			mainTtkReportDataSource = new TTKReportDataSource("PreauthLetterFormat", parameter);
			diagnosisTtkReportDataSource = new TTKReportDataSource("DiagnosisDetails", parameter);
			activityTtkReportDataSource = new TTKReportDataSource("ActivityDetails", parameter);

			

			ResultSet main_report_RS = mainTtkReportDataSource.getResultData();

			mainJasperReport = JasperCompileManager	.compileReport(mainJrxmlfile);
			diagnosisJasperReport = JasperCompileManager.compileReport(diagnosisJrxmlfile);
			activityJasperReport = JasperCompileManager.compileReport(activityJrxmlfile);

			
			
			hashMap.put("diagnosisDataSource", diagnosisTtkReportDataSource);
			hashMap.put("diagnosis", diagnosisJasperReport);
			hashMap.put("activityDataSource", activityTtkReportDataSource);
			hashMap.put("activity", activityJasperReport);
			
			
			
			// JasperFillManager.fillReport(activityJasperReport, hashMap,
			// activityTtkReportDataSource);

			if (main_report_RS == null & !main_report_RS.next()) {
				
				mainJasperPrint = JasperFillManager.fillReport(emptyReport,hashMap, new JREmptyDataSource());
			
			} else {
				String authorisedBy=main_report_RS.getString("AUTHORISED_BY");
				
				hashMap.put("authorisedBy", authorisedBy);
				
				main_report_RS.beforeFirst();
				mainJasperPrint = JasperFillManager.fillReport(mainJasperReport, hashMap, mainTtkReportDataSource);
			}// end of else
			
			JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
			JasperExportManager.exportReportToPdfFile(mainJasperPrint,strPdfFile);
			frmPreAuthGeneral.set("letterPath", strPdfFile);
			
			
			
			request.setAttribute("boas", boas);
			return mapping.findForward("reportdisplay");// This forward goes to
														// the in web.xml file
														// BinaryServlet
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doSaveShortFallDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Send the Authorization Details generated Approval
	 * or Rejected letters to recipient. Finally it forwards to the appropriate
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
	public ActionForward sendPreAuthLetter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("inside AuthorizationDetailsAction sendPreAuthLetter");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preauthStatus = frmPreAuthGeneral.getString("preauthStatus");
			String authNum = frmPreAuthGeneral.getString("authNum");
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			String preAuthNo	=	frmPreAuthGeneral.getString("preAuthNo");
			String strIdentifier = "";
			CommunicationManager commManagerObject = this
					.getCommunicationManagerObject();

			if ("REJ".equals(preauthStatus)) {
				strIdentifier = "PREAUTH_REJECTED";
			} else if ("APR".equals(preauthStatus)) {
				strIdentifier = "PREAUTH_APPROVED";
			}

			// CommunicationOptionVO communicationOptionVO = null;
			Long lngUserID = TTKCommon.getUserSeqId(request);
			String strAuthpdf = TTKPropertiesReader
					.getPropertyValue("authorizationrptdir") + preAuthNo + ".pdf";
			// String[] strCommArray = {"SMS","EMAIL","FAX"};
			File file = new File(strAuthpdf);

			if (file.exists()) {

				commManagerObject.sendAuthorization(
						new Long(preAuthSeqID).longValue(), strIdentifier,
						lngUserID);
				request.setAttribute("successMsg", "Letter Sent Successfully");
			}// end of if(file.exists())
			else {
				request.setAttribute("errorMsg",
						"Please Generate The Letter Before Sending.");
				/*
				 * TTKException expTTK = new TTKException();
				 * expTTK.setMessage("error.authpdf"); throw expTTK;
				 */
			}// end of else
				// StringBuffer strCaption= new StringBuffer();

			return this.getForward("preauthdetail", mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of sendPreAuthLetter(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * Returns the CommunicationManager session object for invoking methods on
	 * it.
	 * 
	 * @return CommunicationManager session object which can be used for method
	 *         invocation
	 * @exception throws TTKException
	 */
	private CommunicationManager getCommunicationManagerObject()
			throws TTKException {
		CommunicationManager communicationManager = null;
		try {
			if (communicationManager == null) {
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strAuthorizationError);
		}// end of catch
		return communicationManager;
	}// end of getCommunicationManagerObject()

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
	public ActionForward viewHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction viewHistory ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
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
				strDetail = "claimsdetail";
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
			frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues("frmPreAuthGeneral", preAuthDetailVO, this, mapping,request);
			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils.setFormValues("frmPreAuthHospital",preAuthDetailVO.getPreAuthHospitalVO(), this, mapping,request));
			frmPreAuthGeneral.set("claimantDetailsVO", FormUtils.setFormValues("frmClaimantDetails", preAuthDetailVO.getClaimantVO(),this, mapping, request));
			frmPreAuthGeneral.set("additionalDetailVO", FormUtils.setFormValues("frmAdditionalDetail",preAuthDetailVO.getAdditionalDetailVO(), this,mapping, request));
			claimDetailVO = new ClaimDetailVO();
			frmPreAuthGeneral.set("claimDetailVO", FormUtils.setFormValues("frmClaimDetail", claimDetailVO, this, mapping, request));
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
			ActionForward actionForward = this.getForward(strDetail, mapping,
					request);
			return actionForward;
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of viewHistory(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doAdd ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			StringBuffer strCaption = new StringBuffer();
			String strDetail = "preauthdetail";
			strCaption.append("Add");
			PreAuthDetailVO preAuthDetailVO	=	new PreAuthDetailVO();
			preAuthDetailVO.setDentalOrthoVO(new DentalOrthoVO());
			frmPreAuthGeneral.initialize(mapping);
			
			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			
			frmPreAuthGeneral.set("networkProviderType", "Y");
			frmPreAuthGeneral.set("caption", strCaption.toString());
			frmPreAuthGeneral.set("preAuthNoStatus", "FRESH");
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthGeneral",
					frmPreAuthGeneral);
			request.getSession().setAttribute("preauthDiagnosis", null);
			request.getSession().setAttribute("preauthActivities", null);
			request.getSession().setAttribute("preauthShortfalls", null);
			// this.documentViewer(request,preAuthDetailVO);
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
	
	public ActionForward doNewPreApprovalAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doNewPreApprovalAdd ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			Toolbar toolBar = (Toolbar) session.getAttribute("toolbar");
			StringBuffer strCaption = new StringBuffer();
			String strDetail = "PreAuthDetails";
			strCaption.append("Add");
			PreAuthDetailVO preAuthDetailVO = null;
			if(request.getSession().getAttribute("preAuthDetailVO")!=null){
			preAuthDetailVO	=	(PreAuthDetailVO) request.getSession().getAttribute("preAuthDetailVO");
		    this.initializeValues(preAuthDetailVO);
			}
			else
			preAuthDetailVO = new PreAuthDetailVO();
			
			
			
			/*preAuthDetailVO.setDentalOrthoVO(new DentalOrthoVO());*/
			frmPreAuthGeneral.initialize(mapping);
			
			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			
			frmPreAuthGeneral.set("networkProviderType", "N");
			
			
			session.setAttribute("providerStates", TTKCommon
					.getStates(frmPreAuthGeneral
							.getString("providerCountry")));
			session.setAttribute("providerAreas", TTKCommon
					.getAreas(frmPreAuthGeneral
							.getString("providerEmirate")));
			
			frmPreAuthGeneral.set("caption", strCaption.toString());
			frmPreAuthGeneral.set("preAuthNoStatus", "FRESH");
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthGeneral",
					frmPreAuthGeneral);
			// this.documentViewer(request,preAuthDetailVO);
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
	 * 
	 * 
	 */
	public ActionForward validationOTP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PreAuthManager preAuthObject = this.getPreAuthManagerObject();
		String otpNumber = request.getParameter("otpNumber");
		otpNumber = otpNumber == null ? "" : otpNumber.trim();
		String memberId = request.getParameter("mid");
		memberId = memberId == null ? "" : memberId.trim();
		Map<String, String> holdResult;

		try {

			holdResult = preAuthObject.getOtpresult(memberId,
					TTKCommon.getUserSeqId(request), otpNumber);

		} catch (Exception e) {
			holdResult = null;
			e.printStackTrace();
		}

		request.setAttribute("holdResult", holdResult);
		return mapping.findForward("preauthOtpValidation");
	}

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
			log.debug("Inside PreAuthGenealAction doSave ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			String strDetail = "preauthdetail";
			StringBuffer strCaption = new StringBuffer();
			HttpSession session = request.getSession();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			// Toolbar toolBar =
			// (Toolbar)request.getSession().getAttribute("toolbar");

			preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
					frmPreAuthGeneral, this, mapping, request);
		
		
			/*
			 * SimpleDateFormat simpleDateFormat=new
			 * SimpleDateFormat("dd/MM/yyyy"); long
			 * preAuthReceivedDate=TTKCommon
			 * .getOracleDateWithTime(preAuthDetailVO
			 * .getReceiveDate(),preAuthDetailVO
			 * .getReceiveTime(),preAuthDetailVO.getReceiveDay()).getTime();
			 * long
			 * preAuthStartDate=TTKCommon.getOracleDateWithTime(simpleDateFormat
			 * .format(preAuthDetailVO.getAdmissionDate()),preAuthDetailVO.
			 * getAdmissionTime(),preAuthDetailVO.getAdmissionDay()).getTime();
			 * long
			 * preAuthEndDate=TTKCommon.getOracleDateWithTime(preAuthDetailVO
			 * .getDischargeDate
			 * (),preAuthDetailVO.getDischargeTime(),preAuthDetailVO
			 * .getDischargeDay()).getTime(); long currentDate=new
			 * Date().getTime(); String
			 * preAuthNoStatus=preAuthDetailVO.getPreAuthNoStatus(); String
			 * errorMsg=""; if("FRESH".equals(preAuthNoStatus)){
			 * if(preAuthReceivedDate>currentDate)
			 * errorMsg="Pre-Auth Reccived Should Not Be A Future Date"; else
			 * if((preAuthReceivedDate>preAuthStartDate))errorMsg=
			 * "Pre-Auth Received Date Should Not Be Greater Than Pre-Auth Start Date"
			 * ; else if(preAuthStartDate>preAuthEndDate)errorMsg=
			 * "Pre-Auth Start Date Should Not Greater Than Pre-Auth End Date";
			 * //else if(preAuthStartDate<currentDate)errorMsg=
			 * "Pre-Auth Start Date Should Not Be Less Than Current Date";
			 * }if("ENHANCEMENT".equals(preAuthNoStatus)){
			 * if(preAuthReceivedDate>currentDate)
			 * errorMsg="Pre-Auth Reccived Should Not Be A Future Date"; else
			 * if(preAuthReceivedDate>preAuthEndDate)errorMsg=
			 * "Pre-Auth Received Date Should Not Be Greater Than Pre-Auth End Date"
			 * ; else if(preAuthReceivedDate<preAuthStartDate)errorMsg=
			 * "Pre-Auth Received Date Should Not Be Less Than Pre-Auth Start Date"
			 * ; else if(preAuthEndDate<currentDate)errorMsg=
			 * "Pre-Auth End Date Has Been Passed"; } if(errorMsg.length()>1){
			 * request.setAttribute("errorMsg", errorMsg); return
			 * this.getForward(strDetail, mapping, request); }
			 */
			String preAuthNo = preAuthDetailVO.getPreAuthNo();
			Long preAuthSeqID = preAuthDetailVO.getPreAuthSeqID();
			Object[] objArrayResult = new Object[3];
			if ( preAuthNo== null || "".equals(preAuthNo)
					|| preAuthNo.trim().length() < 1 || preAuthSeqID == null
					|| preAuthSeqID == 0) {
				preAuthDetailVO.setAddedBy((TTKCommon.getUserSeqId(request)));
				objArrayResult = preAuthObject.savePreAuth(preAuthDetailVO);
				request.setAttribute("successMsg",
						"Pre-Auth Details Added Successfully");
			}// end of if(preAuthDetailVO.getPreAuthSeqID()!=null)
			else {
				preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				objArrayResult = preAuthObject.savePreAuth(preAuthDetailVO);
				request.setAttribute("successMsg",
						"Pre-Auth Details Updated Successfully");
			}// end of else
             session.setAttribute("oralTab",null);
			Long preAuthSeqId = (Long) objArrayResult[0];
			if (preAuthSeqId != null) {
				
				//-------------------Save the Dental related data--------------------
				ActionForm dentalForm=(ActionForm)frmPreAuthGeneral.get("dentalOrthoVO");
				DentalOrthoVO dentalOrthoVO=(DentalOrthoVO)FormUtils.getFormValues(dentalForm,"frmPreAuthDental",this,mapping,request);
				dentalOrthoVO.setPreAuthSeqid(preAuthSeqId);
				String lVal	=	preAuthObject.saveDentalDetails(dentalOrthoVO,"PAT");
	            //---------------------------------------------------------
				
				
				// call the business layer to get the Pre-Auth detail
				preAuthDetailVO = preAuthObject.getPreAuthDetails(preAuthSeqId);
		
				frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
				
				strCaption.append(" Edit");
				strCaption.append(" [ "
						+ frmPreAuthGeneral.getString("patientName") + " ]");
				strCaption.append(
						" [ " + frmPreAuthGeneral.getString("memberId") + " ]")
						.append(" [ " + preAuthDetailVO.getPreAuthNoStatus()
								+ " ] ");
				frmPreAuthGeneral.set("caption", strCaption.toString());

				session.setAttribute("encounterTypes", preAuthObject
						.getEncounterTypes(frmPreAuthGeneral
								.getString("benefitType")));
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				this.addToWebBoard(preAuthDetailVO, request, "PAT");
			}// end of if(preAuthDetailVO.getPreAuthSeqID()!=null)

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

	public ActionForward addDiagnosisDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction addDiagnosisDetails ");
			PreAuthDetailVO preAuthDetailVO = null;
			DiagnosisDetailsVO diagnosisDetailsVO = null;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String successMsg;
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			
			String preAuthNo = (String) frmPreAuthGeneral
					.getString("preAuthNo");
			String preAuthSeqID = (String) frmPreAuthGeneral
					.getString("preAuthSeqID");
			HttpSession session = request.getSession();

			String icdCode = (String) frmPreAuthGeneral.getString("icdCode");
			String icdCodeSeqId = (String) frmPreAuthGeneral
					.getString("icdCodeSeqId");
			String ailmentDescription = (String) frmPreAuthGeneral
					.getString("ailmentDescription");
			String presentingComplaints = (String) frmPreAuthGeneral
					.getString("presentingComplaints");
			String authType = (String) frmPreAuthGeneral.getString("authType");

			String primaryAilment = request.getParameter("primaryAilment");
			String diagSeqId = (String) frmPreAuthGeneral.get("diagSeqId");
			//Added for PED
			Integer durationOfAlment = Integer.parseInt((String)frmPreAuthGeneral.get("durAilment"));
			String durationFlag = (String)frmPreAuthGeneral.get("durationFlag");
			
			
			Long longDiagSeqId = (diagSeqId == null || diagSeqId.length() < 1) ? null
					: new Long(diagSeqId);
			diagnosisDetailsVO = new DiagnosisDetailsVO();
			diagnosisDetailsVO.setDiagSeqId(longDiagSeqId);
			diagnosisDetailsVO.setAuthType(authType);
			diagnosisDetailsVO.setPreAuthNo(preAuthNo);
			diagnosisDetailsVO.setPreAuthSeqID(new Long(preAuthSeqID));
			diagnosisDetailsVO.setIcdCode(icdCode);
			diagnosisDetailsVO.setAilmentDescription(ailmentDescription);
			diagnosisDetailsVO.setPresentingComplaints(presentingComplaints);
			diagnosisDetailsVO.setPrimaryAilment(primaryAilment);
			diagnosisDetailsVO.setIcdCodeSeqId(new Long(icdCodeSeqId));
			diagnosisDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
			diagnosisDetailsVO.setDurAilment(durationOfAlment);
			diagnosisDetailsVO.setDurationFlag(durationFlag);

			successMsg = (diagSeqId == null || diagSeqId.length() < 1) ? "Diagnosis Details Added Successfully"
					: "Diagnosis Details Updated Successfully";

			preAuthObject.saveDiagnosisDetails(diagnosisDetailsVO);

			preAuthDetailVO = preAuthObject.getPreAuthDetails(diagnosisDetailsVO.getPreAuthSeqID());

			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			request.setAttribute("focusObj", request.getParameter("focusObj"));
			
			frmPreAuthGeneral.set("icdCode", "");
			frmPreAuthGeneral.set("icdCodeSeqId", "");
			frmPreAuthGeneral.set("ailmentDescription", "");
			frmPreAuthGeneral.set("primaryAilment", "");
			frmPreAuthGeneral.set("diagSeqId", "");
			session.setAttribute("encounterTypes", preAuthObject
					.getEncounterTypes(frmPreAuthGeneral
							.getString("benefitType")));
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);

			request.setAttribute("successMsg", successMsg);

			return this.getForward(strPreauthdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of addDiagnosisDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward deleteDiagnosisDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction deleteDiagnosis ");
			PreAuthDetailVO preAuthDetailVO = null;
			DiagnosisDetailsVO diagnosisDetailsVO = null;
			HttpSession session = request.getSession();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preAuthSeqID = (String) frmPreAuthGeneral.get("preAuthSeqID");

			diagnosisDetailsVO = ((ArrayList<DiagnosisDetailsVO>) session.getAttribute("preauthDiagnosis")).get(new Integer(request
					.getParameter("rownum")).intValue());
			diagnosisDetailsVO.setAuthType("PAT");
			diagnosisDetailsVO.setPreAuthSeqID(new Long(preAuthSeqID));
			diagnosisDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));

			preAuthObject.deleteDiagnosisDetails(diagnosisDetailsVO);
			request.setAttribute("successMsg",
					"Diagnosis Details Deleted Successfully");

			preAuthDetailVO = preAuthObject.getPreAuthDetails(diagnosisDetailsVO.getPreAuthSeqID());

			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			request.setAttribute("focusObj", request.getParameter("focusObj"));

			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);

			return this.getForward(strPreauthdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of deleteDiagnosisDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward deleteShortfallsDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction deleteShortfallsDetails ");
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preAuthSeqID = (String) frmPreAuthGeneral.get("preAuthSeqID");
			String shortFallSeqId = (String) frmPreAuthGeneral.get("shortFallSeqId");

			int updateRows = preAuthObject.deleteShortfallsDetails(new String[] { preAuthSeqID,shortFallSeqId, "PAT" });
			if (updateRows > 0)
				request.setAttribute("successMsg","Shortfalls Details Deleted Successfully");

			preAuthDetailVO = preAuthObject.getPreAuthDetails(new Long(preAuthSeqID));

			
			/*frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues("frmPreAuthGeneral", preAuthDetailVO, this, mapping,request);*/
			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			request.setAttribute("focusObj", request.getParameter("focusObj"));
			// this.documentViewer(request,preAuthDetailVO);
			return this.getForward(strPreauthdetail, mapping, request);// mapping.findForward("preauthdetail");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of deleteShortfallsDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward setProviderMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction setProviderMode ");

			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String networkProviderType = (String) frmPreAuthGeneral
					.get("networkProviderType");
			HttpSession session = request.getSession();
			if ("N".equals(networkProviderType)) {
				frmPreAuthGeneral.set("networkProviderType", "N");
			} else {
				frmPreAuthGeneral.set("providerAddress", "");
				frmPreAuthGeneral.set("providerPobox", "");
				frmPreAuthGeneral.set("providerArea", "");
				frmPreAuthGeneral.set("providerEmirate", "");
				frmPreAuthGeneral.set("providerCountry", "");
				frmPreAuthGeneral.set("networkProviderType", "Y");
			}
			frmPreAuthGeneral.set("providerName", "");
			frmPreAuthGeneral.set("providerId", "");
			frmPreAuthGeneral.set("providerSeqId", "");
			frmPreAuthGeneral.set("clinicianName", "");
			frmPreAuthGeneral.set("clinicianId", "");
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			return mapping.findForward("preauthdetail"); // this.getForward(strDetail,
															// mapping,
															// request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of setProviderMode(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward editDiagnosisDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction editDiagnosis ");
			HttpSession session = request.getSession();
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;

			DiagnosisDetailsVO diagnosisDetailsVO2 = ((ArrayList<DiagnosisDetailsVO>) session
					.getAttribute("preauthDiagnosis")).get(new Integer(request
					.getParameter("rownum")).intValue());

			frmPreAuthGeneral.set("primaryAilment",
					diagnosisDetailsVO2.getPrimaryAilment());
			frmPreAuthGeneral.set("ailmentDescription",
					diagnosisDetailsVO2.getAilmentDescription());
			frmPreAuthGeneral.set("icdCode", diagnosisDetailsVO2.getIcdCode());
			frmPreAuthGeneral.set("icdCodeSeqId", diagnosisDetailsVO2
					.getIcdCodeSeqId().toString());
			frmPreAuthGeneral.set("diagSeqId", diagnosisDetailsVO2
					.getDiagSeqId().toString());
			request.setAttribute("focusObj", request.getParameter("focusObj"));
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);

			return this.getForward(strPreauthdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of editDiagnosisDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward activityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction addActivityDetails ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preAuthNo = (String) frmPreAuthGeneral.get("preAuthNo");
			String preAuthSeqID = (String) frmPreAuthGeneral
					.get("preAuthSeqID");
			String admissionDate = (String) frmPreAuthGeneral
					.get("admissionDate");
			String encounterTypeId = (String) frmPreAuthGeneral
					.get("encounterTypeId");
			// this.getForward(strDetail, mapping, request);
			String strDetail = "preauthdetail";
			if (preAuthNo == null || preAuthNo.length() < 1
					|| preAuthSeqID == null || preAuthSeqID.length() < 1) {
				request.setAttribute("errorMsg",
						"Please Enter And Save PreApproval Details");
				return this.getForward(strDetail, mapping, request);
			} else {
				request.setAttribute("preAuthNo", preAuthNo);
				request.setAttribute("preAuthSeqID", preAuthSeqID);
				request.setAttribute("admissionDate", admissionDate);
				request.setAttribute("encounterTypeId", encounterTypeId);
				return mapping.findForward(strActivitydetails);
			}
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of AddActivityDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)


	/**
	 * This method is used to search the data with the given search criteria.
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
	public ActionForward diagnosisCodeSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthAction diagnosisCodeSearch");
			setLinks(request);
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			HttpSession session = request.getSession();
			TableData diagnosisCodeListData = null;
			if (session.getAttribute("diagnosisCodeListData") != null) {
				diagnosisCodeListData = (TableData) session
						.getAttribute("diagnosisCodeListData");
			}// end of if((request.getSession()).getAttribute("icdListData") !=
				// null)
			else {
				diagnosisCodeListData = new TableData();
			}// end of else

			String strPageID = TTKCommon.checkNull(request
					.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request
					.getParameter("sortId"));
			// if the page number or sort id is clicked
			if (!strPageID.equals("") || !strSortID.equals("")) {
				if (strPageID.equals("")) {
					diagnosisCodeListData.setSortData(TTKCommon
							.checkNull(request.getParameter("sortId")));
					diagnosisCodeListData.modifySearchData("sort");// modify the
																	// search
																	// data
				}// /end of if(!strPageID.equals(""))
				else {
					log.debug("PageId   :"
							+ TTKCommon.checkNull(request
									.getParameter("pageId")));
					diagnosisCodeListData.setCurrentPage(Integer
							.parseInt(TTKCommon.checkNull(request
									.getParameter("pageId"))));
					return this.getForward(strDiagnosisCodeList, mapping,
							request);
				}// end of else
			}// end of if(!strPageID.equals("") || !strSortID.equals(""))
			else {
				diagnosisCodeListData.createTableInfo("DiagnosisCodeListTable",
						null);
				diagnosisCodeListData.setSearchData(this
						.populateDiagnosisCodeSearchCriteria(
								(DynaActionForm) form, request));
				diagnosisCodeListData.modifySearchData("search");
			}// end of else

			ArrayList alDiagnosisCodeList = null;
			alDiagnosisCodeList = preAuthObject
					.getDiagnosisCodeList(diagnosisCodeListData.getSearchData());
			diagnosisCodeListData.setData(alDiagnosisCodeList, "search");
			// set the table data object to session
			session.setAttribute("diagnosisCodeListData", diagnosisCodeListData);
			return this.getForward(strDiagnosisCodeList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strDiagnosisCodeList));
		}// end of catch(Exception exp)
	}// end of diagnosisCodeSearch(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * this method will add search criteria fields and values to the arraylist
	 * and will return it
	 * 
	 * @param frmPreAuthList
	 *            formbean which contains the search fields
	 * @param request
	 *            HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateDiagnosisCodeSearchCriteria(
			DynaActionForm frmPreAuthGeneral, HttpServletRequest request) {
		// build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon
				.replaceSingleQots((String) frmPreAuthGeneral
						.getString("icdCode")));
		alSearchParams.add(TTKCommon
				.replaceSingleQots((String) frmPreAuthGeneral
						.getString("ailmentDescription")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}// end of populateDiagnosisCodeSearchCriteria(DynaActionForm
		// frmProductList,HttpServletRequest request)

	public ActionForward doSelectDiagnosisCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doSelectDiagnosisCode ");
			HttpSession session = request.getSession();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			TableData diagnosisCodeListData = (TableData) session
					.getAttribute("diagnosisCodeListData");

			if (!(TTKCommon.checkNull(request.getParameter("rownum"))
					.equals(""))) {
				DiagnosisDetailsVO diagnosisDetailsVO = (DiagnosisDetailsVO) diagnosisCodeListData
						.getRowInfo(Integer.parseInt((String) request
								.getParameter("rownum")));

				DynaActionForm frmPreAuthGeneral = (DynaActionForm) session
						.getAttribute("frmPreAuthGeneral");
				String preAuthSeqID = frmPreAuthGeneral
						.getString("preAuthSeqID");
				String diagSeqId = frmPreAuthGeneral.getString("diagSeqId");

				DiagnosisDetailsVO diagnosisDetailsVO2 = preAuthObject
						.getIcdCodeDetails(diagnosisDetailsVO.getIcdCode(),
								new Long(preAuthSeqID), "PAT");
				String primary = diagnosisDetailsVO2.getPrimaryAilment();
				if (diagSeqId == null || diagSeqId.length() < 1) {
					if (primary == null || "".equals(primary)
							|| "YES".equals(primary))
						frmPreAuthGeneral.set("primaryAilment", "");
					else
						frmPreAuthGeneral.set("primaryAilment", "Y");
				}
				frmPreAuthGeneral.set("icdCode",
						diagnosisDetailsVO.getIcdCode());
				frmPreAuthGeneral.set("icdCodeSeqId", diagnosisDetailsVO
						.getIcdCodeSeqId().toString());
				frmPreAuthGeneral.set("ailmentDescription",
						diagnosisDetailsVO.getAilmentDescription());
				
				request.setAttribute("focusObj", request.getParameter("focusObj"));
				
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			}// end of
				// if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return this.getForward(strPreAuthDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strDiagnosisCodeList));
		}// end of catch(Exception exp)
	}// end of doSelectDiagnosisCode(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the next set of records with the given search
	 * criteria. Finally it forwards to the appropriate view based on the
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
	public ActionForward doDiagnosisCodeForward(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthAction doDiagnosisCodeForward");
			setLinks(request);
			TableData diagnosisCodeListData = (TableData) request.getSession()
					.getAttribute("diagnosisCodeListData");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			diagnosisCodeListData.modifySearchData(strForward);// modify the
																// search data
			ArrayList alDiagnosisList = preAuthObject
					.getDiagnosisCodeList(diagnosisCodeListData.getSearchData());
			diagnosisCodeListData.setData(alDiagnosisList, strForward);// set
																		// the
																		// table
																		// data
			request.getSession().setAttribute("diagnosisCodeListData",
					diagnosisCodeListData); // set the table data object to
											// session
			return this.getForward(strDiagnosisCodeList, mapping, request); // finally
																			// return
																			// to
																			// the
																			// grid
																			// screen
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strDiagnosisCodeList));
		}// end of catch(Exception exp)
	}// end of doDiagnosisCodeForward(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the next set of records with the given search
	 * criteria. Finally it forwards to the appropriate view based on the
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
	public ActionForward doDiagnosisCodeBackward(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthAction doDiagnosisCodeBackward");
			setLinks(request);
			TableData diagnosisCodeListData = (TableData) request.getSession()
					.getAttribute("diagnosisCodeListData");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			diagnosisCodeListData.modifySearchData(strBackward);// modify the
																// search data
			ArrayList alDiagnosisList = preAuthObject
					.getDiagnosisCodeList(diagnosisCodeListData.getSearchData());
			diagnosisCodeListData.setData(alDiagnosisList, strBackward);// set
																		// the
																		// table
																		// data
			request.getSession().setAttribute("diagnosisCodeListData",
					diagnosisCodeListData); // set the table data object to
											// session
			return this.getForward(strDiagnosisCodeList, mapping, request); // finally
																			// return
																			// to
																			// the
																			// grid
																			// screen
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strDiagnosisCodeList));
		}// end of catch(Exception exp)
	}// end of doDiagnosisCodeBackward(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doViewEnhancementPreauth(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doViewEnhancementPreauth ");
			HttpSession session = request.getSession();

			Long preAuthSeqID = (Long) session
					.getAttribute("enhancementPreAuthSeqID");
			if (preAuthSeqID == null || preAuthSeqID == 0) {
				// TTKException expTTK = new TTKException();
				// expTTK.setMessage("error.PreAuthorization.required");
				session.setAttribute("preauthDiagnosis", null);
				session.setAttribute("preauthActivities", null);
				session.setAttribute("preauthShortfalls", null);

				DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
				frmPreAuthGeneral.initialize(mapping);

				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				request.setAttribute("errorMsg",
						"Please Enter And Save PreApproval Details");
				return this.getForward(strPreAuthDetail, mapping, request);
				// throw expTTK;
			}// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			frmPreAuthGeneral.initialize(mapping);
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			StringBuffer strCaption = new StringBuffer();

			PreAuthDetailVO preAuthDetailVO = preAuthObject.getPreAuthDetails(preAuthSeqID);


			preAuthDetailVO.setParentPreAuthSeqID(preAuthSeqID);
			preAuthDetailVO.setPreAuthSeqID(null);
			preAuthDetailVO.setPreAuthNoStatus("ENHANCEMENT");
			preAuthDetailVO.setEnhancedYN("ENH");

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			frmPreAuthGeneral.set("receiveDate", "");
			frmPreAuthGeneral.set("receiveTime", "");
			frmPreAuthGeneral.set("receiveDay", "AM");
			strCaption.append(" Edit");
			strCaption.append(" [ "
					+ frmPreAuthGeneral.getString("patientName") + " ]");
			strCaption.append(" [ " + frmPreAuthGeneral.getString("memberId")
					+ " ]");
			strCaption.append(" [ Enhancement ]");
			frmPreAuthGeneral.set("caption", strCaption.toString());
			// this.addToWebBoard(preAuthDetailVO,request,"PAT");
			if ("N".equals(frmPreAuthGeneral.getString("networkProviderType"))) {
				session.setAttribute("providerStates", TTKCommon
						.getStates(frmPreAuthGeneral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmPreAuthGeneral
								.getString("providerEmirate")));
			}
			session.setAttribute("encounterTypes", preAuthObject
					.getEncounterTypes(frmPreAuthGeneral
							.getString("benefitType")));
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			return this.getForward(strPreAuthDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of doViewEnhancementPreauth(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward deleteActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction deleteActivityDetails ");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preAuthSeqID = (String) frmPreAuthGeneral
					.get("preAuthSeqID");
			String authType = frmPreAuthGeneral.getString("authType");
			String activityDtlSeqId = (String) frmPreAuthGeneral
					.get("activityDtlSeqId");

			preAuthObject.deleteActivityDetails(new Long(preAuthSeqID)
			.longValue(), new Long(activityDtlSeqId).longValue(),
			authType, TTKCommon.getUserSeqId(request));
			request.setAttribute("successMsg",
					"Activity Details Deleted Successfully");

			PreAuthDetailVO preAuthDetailVO = preAuthObject.getPreAuthDetails(new Long(preAuthSeqID));


			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			request.setAttribute("focusObj", request.getParameter("focusObj"));
			
			request.getSession().setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);

			return this.getForward(strPreauthdetail, mapping, request);// mapping.findForward("preauthdetail");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of deleteActivityDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward calculatePreauthAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction calculatePreauthAmount ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preAuthSeqID = (String) frmPreAuthGeneral
					.get("preAuthSeqID");
			String hospitalSeqID = (String) frmPreAuthGeneral
					.get("providerSeqId");
			hospitalSeqID = hospitalSeqID == null || hospitalSeqID.length() < 1 ? "0"
					: hospitalSeqID;
			StringBuffer strCaption = new StringBuffer();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			BigDecimal[] calculateAmounts = preAuthObject
					.getCalculatedPreauthAmount(new Long(preAuthSeqID),
							new Long(hospitalSeqID),TTKCommon.getUserSeqId(request));
			

			PreAuthDetailVO preAuthDetailVO  = preAuthObject.getPreAuthDetails(new Long(preAuthSeqID));

			frmPreAuthGeneral =setFormValues(preAuthDetailVO, mapping, request);
			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			strCaption.append(" Edit");
			strCaption.append(" [ "
					+ frmPreAuthGeneral.getString("patientName") + " ]");
			strCaption.append(" [ " + frmPreAuthGeneral.getString("memberId")
					+ " ]");
			frmPreAuthGeneral.set("caption", strCaption.toString());

			
			request.setAttribute("successMsg", "Record updated successfully!");
			request.getSession().setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
			
			request.setAttribute("focusObj", request.getParameter("focusObj"));
			
			return this.getForward(strPreAuthDetail, mapping, request);

		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of calculatePreauthAmount(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward saveAndCompletePreauth(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			
			setLinks(request);
			log.debug("Inside PreAuthGenealAction saveAndCompletePreauth");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preauthStatus="";
			String pstatus  = request.getParameter("pstatus");
			if(pstatus != null  && pstatus.length()!=0)
			{
				preauthStatus = pstatus;
			}
			else
			{	
				preauthStatus = frmPreAuthGeneral.getString("preauthStatus");
			}	
					
			String approvedAmount = frmPreAuthGeneral.getString("approvedAmount");
			approvedAmount = (approvedAmount == null || approvedAmount.length() < 1) ? "0" 	: approvedAmount;
			if ("APR".equals(preauthStatus)	&& (new Double(approvedAmount).doubleValue() <= 0)) {

				request.setAttribute("errorMsg","Approved Amount Should Be Greater Than Zero");
				return this.getForward(strPreAuthDetail, mapping, request);
			}
			
			PreAuthDetailVO preAuthDetailVO = new PreAuthDetailVO();
        	preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(frmPreAuthGeneral, this, mapping, request);
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			StringBuffer strCaption = new StringBuffer();
		
			String denailcode   = request.getParameter("denailcode");					
	        if(denailcode!=null  && denailcode.length()!=0 )
	        {
	        	preAuthDetailVO.setDenialCode(denailcode);
			}

			preAuthDetailVO.setPreauthStatus(preauthStatus);
				
			preAuthDetailVO.setAddedBy(TTKCommon.getUserSeqId(request));
			preAuthObject.saveAndCompletePreauth(preAuthDetailVO);
				
						
			PreAuthDetailVO preAuthDetailVO2 = preAuthObject.getPreAuthDetails(preAuthDetailVO.getPreAuthSeqID());

			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			strCaption.append(" Edit");
			strCaption.append(" [ "	+ frmPreAuthGeneral.getString("patientName") + " ]");
			strCaption.append(" [ " + frmPreAuthGeneral.getString("memberId")+ " ]");
			frmPreAuthGeneral.set("caption", strCaption.toString());
			request.getSession().setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);

			request.setAttribute("successMsg","Preauth completed successfully!");
			return this.getForward(strPreAuthDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of saveAndCompletePreauth(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward editActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction editActivityDetails ");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String activityDtlSeqId = (String) request
					.getAttribute("activityDtlSeqId");
			ActivityDetailsVO activityDetailsVO = preAuthObject
					.getActivityDetails(activityDtlSeqId == null ? 0 : Long
							.parseLong(activityDtlSeqId));
			frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
					"frmActivityDetails", activityDetailsVO, this, mapping,
					request);

			request.getSession().setAttribute("frmActivityDetails",
					frmActivityDetails);
			return mapping.findForward(strActivitydetails);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// editActivityDetails()

	public ActionForward overrideActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction overrideActivityDetails ");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String activityDtlSeqId = (String) request
					.getAttribute("activityDtlSeqId");
			String networkProviderType = (String) request
					.getAttribute("networkProviderType");
			String override = (String) request.getAttribute("override");

			ActivityDetailsVO activityDetailsVO = preAuthObject
					.getActivityDetails(activityDtlSeqId == null ? 0 : Long
							.parseLong(activityDtlSeqId));

			LinkedHashMap<String, String> activityDenialList = new LinkedHashMap<String, String>();
			// splitting the denial codes and denial desc in case override
			if ("Y".equals(activityDetailsVO.getOverrideYN())) {

				String denialCode = activityDetailsVO.getDenialCode();
				String denialCodeDesc = activityDetailsVO
						.getDenialDescription();

				if (denialCode != null && denialCodeDesc != null) {
					String denialCodes[] = denialCode.split("[;]");
					String denialCodeDescs[] = denialCodeDesc.split("[;]");

					if (denialCodes.length == denialCodeDescs.length) {
						for (int i = 0; i < denialCodes.length; i++) {
							activityDenialList.put(denialCodes[i].trim(),
									denialCodeDescs[i]);
						}
					}
				}
				activityDetailsVO.setDenialCode(null);
				activityDetailsVO.setDenialDescription(null);
			}

			frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
					"frmActivityDetails", activityDetailsVO, this, mapping,
					request);
			frmActivityDetails.set("overrideYN", override);
			frmActivityDetails.set("networkProviderType", networkProviderType);
			request.getSession().setAttribute("activityDenialList",
					activityDenialList);
			request.getSession().setAttribute("frmActivityDetails",
					frmActivityDetails);
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// viewActivityDetails()

	public ActionForward viewActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction viewActivityDetails ");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String activityDtlSeqId = (String) request
					.getAttribute("activityDtlSeqId");
			String networkProviderType = (String) request
					.getAttribute("networkProviderType");

			ActivityDetailsVO activityDetailsVO = preAuthObject
					.getActivityDetails(activityDtlSeqId == null ? 0 : Long
							.parseLong(activityDtlSeqId));

			LinkedHashMap<String, String> activityDenialList = new LinkedHashMap<String, String>();
			// splitting the denial codes and denial desc in case override
			if ("Y".equals(activityDetailsVO.getOverrideYN())) {

				String denialCode = activityDetailsVO.getDenialCode();
				String denialCodeDesc = activityDetailsVO
						.getDenialDescription();

				if (denialCode != null && denialCodeDesc != null) {
					String denialCodes[] = denialCode.split("[;]");
					String denialCodeDescs[] = denialCodeDesc.split("[;]");

					if (denialCodes.length == denialCodeDescs.length) {
						for (int i = 0; i < denialCodes.length; i++) {
							activityDenialList.put(denialCodes[i].trim(),
									denialCodeDescs[i]);
						}
					}
				}
				activityDetailsVO.setDenialCode(null);
				activityDetailsVO.setDenialDescription(null);
			}
			frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
					"frmActivityDetails", activityDetailsVO, this, mapping,
					request);

			frmActivityDetails.set("networkProviderType", networkProviderType);
			request.getSession().setAttribute("activityDenialList",
					activityDenialList);
			request.getSession().setAttribute("frmActivityDetails",
					frmActivityDetails);

			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// viewActivityDetails()

	public ActionForward saveActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction saveActivityDetails");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			String successMsg;
			ActivityDetailsVO activityDetailsVO = new ActivityDetailsVO();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			activityDetailsVO = (ActivityDetailsVO) FormUtils.getFormValues(
					frmActivityDetails, "frmActivityDetails", this, mapping,
					request);
			activityDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
			String amountAllowed = request.getParameter("amountAllowed");
			String overrideYN = request.getParameter("overrideYN");
			amountAllowed = (amountAllowed == null || "".equals(amountAllowed)) ? "N": amountAllowed;overrideYN = (overrideYN == null || "".equals(overrideYN)) ? "N": overrideYN;

			activityDetailsVO.setAmountAllowed(amountAllowed);
			activityDetailsVO.setOverrideYN(overrideYN);
			if ("Y".equals(overrideYN)) {
				activityDetailsVO.setDenialCode(null);
				activityDetailsVO.setDenialDescription(null);

				LinkedHashMap<String, String> activityDenialList = (LinkedHashMap<String, String>) request
						.getSession().getAttribute("activityDenialList");
				if (activityDenialList != null && activityDenialList.size() > 0) {
					StringBuilder dcsBuilder = new StringBuilder();
					StringBuilder dcdsBuilder = new StringBuilder();

					Set<Entry<String, String>> set = activityDenialList
							.entrySet();
					for (Entry<String, String> entr : set) {
						dcsBuilder.append(entr.getKey().trim());
						dcsBuilder.append(";");
						dcdsBuilder.append(entr.getValue().trim());
						dcdsBuilder.append(";");
					}
					activityDetailsVO.setDenialCode(dcsBuilder.toString());
					activityDetailsVO.setDenialDescription(dcdsBuilder.toString());
				}
			}

			// saving the activity details
			preAuthObject.saveActivityDetails(activityDetailsVO);

			successMsg = (activityDetailsVO.getActivityDtlSeqId() == null || activityDetailsVO.getActivityDtlSeqId() == 0) ? "Activity Details Added Successffully": "Activity Details Updated Successffully";

			String preAuthNo = frmActivityDetails.getString("preAuthNo");
			String preAuthSeqID = frmActivityDetails.getString("preAuthSeqID");
			String activityStartDate = frmActivityDetails.getString("activityStartDate");
			String clinicianId = frmActivityDetails.getString("clinicianId");
			String clinicianName = frmActivityDetails.getString("clinicianName");
			String networkProviderType = frmActivityDetails	.getString("networkProviderType");
			// String overrideYN=frmActivityDetails.getString("overrideYN");
			// String authType=frmActivityDetails.getString("authType");

			frmActivityDetails.initialize(mapping);

			frmActivityDetails.set("preAuthNo", preAuthNo);
			frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
			frmActivityDetails.set("clinicianId", clinicianId);
			frmActivityDetails.set("clinicianName", clinicianName);
			frmActivityDetails.set("networkProviderType", networkProviderType);
			// frmActivityDetails.set("overrideYN", overrideYN);
			// frmActivityDetails.set("authType", authType);
			// frmActivityDetails.set("activityDtlSeqId", activityDtlSeqId);

			frmActivityDetails.set("activityStartDate", activityStartDate);// 1,2--NON-edit
			frmActivityDetails.set("amountAllowed", "Y");// Amount Allowed			// default Checked
			request.getSession().setAttribute("frmActivityDetails",		frmActivityDetails);
			request.getSession().setAttribute("activityDenialList", null);
			request.setAttribute("successMsg", successMsg);
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivitydetails));
		}// end of catch(Exception exp)
	}// end of saveActivityDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward resetActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction resetActivityDetails");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			String preAuthNo = frmActivityDetails.getString("preAuthNo");
			String preAuthSeqID = frmActivityDetails.getString("preAuthSeqID");
			String activityStartDate = frmActivityDetails
					.getString("activityStartDate");
			String clinicianId = frmActivityDetails.getString("clinicianId");
			String clinicianName = frmActivityDetails
					.getString("clinicianName");
			String networkProviderType = frmActivityDetails
					.getString("networkProviderType");
			String overrideYN = frmActivityDetails.getString("overrideYN");
			// String
			// activityDtlSeqId=frmActivityDetails.getString("activityDtlSeqId");
			frmActivityDetails.initialize(mapping);
			frmActivityDetails.set("preAuthNo", preAuthNo);
			frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
			// frmActivityDetails.set("activityDtlSeqId", activityDtlSeqId);
			frmActivityDetails.set("activityStartDate", activityStartDate);// 1,2--NON-edit
			frmActivityDetails.set("clinicianId", clinicianId);
			frmActivityDetails.set("networkProviderType", networkProviderType);
			frmActivityDetails.set("clinicianName", clinicianName);
			frmActivityDetails.set("amountAllowed", "Y");// Amount Allowed
															// default Checked
			frmActivityDetails.set("overrideYN", overrideYN);
			request.getSession().setAttribute("frmActivityDetails",
					frmActivityDetails);

			return mapping.findForward(strActivitydetails);// this.getForward("preauthdetail",
															// mapping,
															// request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivitydetails));
		}// end of catch(Exception exp)
	}// end of resetActivityDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward addActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction activityDetails ");
			log.info("Inside PreAuthGenealAction activityDetails");
			HttpSession session = request.getSession();
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) session
					.getAttribute("frmPreAuthGeneral");
			String preAuthNo = frmPreAuthGeneral.getString("preAuthNo");
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			String admissionDate = frmPreAuthGeneral.getString("admissionDate");
			String encounterTypeId = frmPreAuthGeneral
					.getString("encounterTypeId");
			String clinicianId = frmPreAuthGeneral.getString("clinicianId");
			String clinicianName = frmPreAuthGeneral.getString("clinicianName");
			String networkProviderType = frmPreAuthGeneral
					.getString("networkProviderType");

			boolean dateStatus = false;
			if ("1".equals(encounterTypeId) || "2".equals(encounterTypeId))
				dateStatus = true;

			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			frmActivityDetails.initialize(mapping);
			frmActivityDetails.set("preAuthNo", preAuthNo);
			frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
			frmActivityDetails.set("clinicianId", clinicianId);
			frmActivityDetails.set("clinicianName", clinicianName);
			frmActivityDetails.set("networkProviderType", networkProviderType);
			frmActivityDetails.set("activityStartDate", admissionDate);// 1,2--NON-edit
			frmActivityDetails.set("amountAllowed", "Y");// Amount Allowed
															// default Checked
			
			request.setAttribute("focusObj", request.getParameter("focusObj"));
			
			session.setAttribute("dateStatus", dateStatus);
			session.setAttribute("frmActivityDetails", frmActivityDetails);
			return mapping.findForward(strActivitydetails);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of addActivityDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doChangeServiceType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doChangeServiceType ");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;	
			
			String preAuthNo = frmActivityDetails.getString("preAuthNo");
			String preAuthSeqID = frmActivityDetails.getString("preAuthSeqID");
			String activityStartDate = frmActivityDetails.getString("activityStartDate");
			String clinicianId = frmActivityDetails.getString("clinicianId");
			String clinicianName = frmActivityDetails.getString("clinicianName");
			String networkProviderType = frmActivityDetails.getString("networkProviderType");
			String activityDtlSeqId = frmActivityDetails.getString("activityDtlSeqId");
			String activityServiceType = frmActivityDetails.getString("activityServiceType");
			
			frmActivityDetails.initialize(mapping);
			
			frmActivityDetails.set("activityDtlSeqId", activityDtlSeqId);
			frmActivityDetails.set("activityServiceType", activityServiceType);
			frmActivityDetails.set("preAuthNo", preAuthNo);
			frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
			frmActivityDetails.set("clinicianId", clinicianId);
			frmActivityDetails.set("clinicianName", clinicianName);
			frmActivityDetails.set("networkProviderType", networkProviderType);
			frmActivityDetails.set("activityStartDate", activityStartDate);// 1,2--NON-edit
			frmActivityDetails.set("amountAllowed", "Y");// Amount Allowed			
			
			request.getSession().setAttribute("frmActivityDetails",frmActivityDetails);
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivitydetails));
		}// end of catch(Exception exp)
	}// viewActivityDetails()
	
	
	public ActionForward addDenialDesc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction addDenialDesc");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			String denialCode = request.getParameter("denialCode");
			denialCode = (denialCode == null) ? "" : denialCode;
			String denialDesc = request.getParameter("denialDescription");
			denialDesc = (denialDesc == null) ? "" : denialDesc;
			HttpSession session = request.getSession();
			LinkedHashMap<String, String> activityDenialList = (LinkedHashMap<String, String>) session
					.getAttribute("activityDenialList");
			if (activityDenialList == null) {
				activityDenialList = new LinkedHashMap<String, String>();
			}
			activityDenialList.put(denialCode, denialDesc);
			frmActivityDetails.set("denialCode", "");
			frmActivityDetails.set("denialDescription", "");
			session.setAttribute("frmActivityDetails", frmActivityDetails);
			session.setAttribute("activityDenialList", activityDenialList);
			request.setAttribute("successMsg", "Added Successfully");
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivitydetails));
		}// end of catch(Exception exp)
	}// end of addDenialDesc(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward deleteDenialDesc(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction deleteDenialDesc");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			String denialCode = request.getParameter("denialCode");

			HttpSession session = request.getSession();
			LinkedHashMap<String, String> activityDenialList = (LinkedHashMap<String, String>) session
					.getAttribute("activityDenialList");
			if (activityDenialList != null) {
				activityDenialList.remove(denialCode);
				session.setAttribute("activityDenialList", activityDenialList);
			}
			request.setAttribute("successMsg", "Deleted Successfully");
			frmActivityDetails.set("denialCode", "");
			frmActivityDetails.set("denialDescription", "");
			session.setAttribute("frmActivityDetails", frmActivityDetails);
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivitydetails));
		}// end of catch(Exception exp)
	}// end of deleteDenialDesc(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward observWindow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction addObserv ");
			String authSeqID = (String) request.getParameter("pstatussi");
			String activityDtlSeqId = (String) request
					.getParameter("activityDtlSeqId");
			String authType = (String) request.getParameter("authType");
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			frmObservDetails.initialize(mapping);
			frmObservDetails.set("activityDtlSeqId", activityDtlSeqId);
			frmObservDetails.set("authType", authType);
			if ("PAT".equals(authType))
				frmObservDetails.set("preAuthSeqID", authSeqID);
			else if ("CLM".equals(authType))
				frmObservDetails.set("claimSeqID", authSeqID);
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ArrayList<String[]> observations = preAuthObject
					.getAllObservDetails(new Long(activityDtlSeqId));
			request.getSession().setAttribute("observations", observations);
			request.getSession().setAttribute("frmObservDetails",
					frmObservDetails);
			return mapping.findForward("addObservations");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of addObserv(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward editObserDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction editObserDetails ");
			String observSeqId = (String) request.getParameter("observSeqId");
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			String preAuthSeqID = frmObservDetails.getString("preAuthSeqID");
			String claimSeqID = frmObservDetails.getString("claimSeqID");
			String authType = frmObservDetails.getString("authType");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ObservationDetailsVO observationDetailsVO = preAuthObject
					.getObservDetail(new Long(observSeqId));
			if ("PAT".equals(authType))
				observationDetailsVO.setPreAuthSeqID(new Long(preAuthSeqID));
			else if ("CLM".equals(authType))
				observationDetailsVO.setClaimSeqID(new Long(claimSeqID));
			observationDetailsVO.setAuthType(authType);
			frmObservDetails = (DynaActionForm) FormUtils.setFormValues(
					"frmObservDetails", observationDetailsVO, this, mapping,
					request);
			Object[] observTypeDetails = preAuthObject
					.getObservTypeDetails(observationDetailsVO.getObservType());
			HttpSession session = request.getSession();
			session.setAttribute("observCodes", observTypeDetails[0]);
			session.setAttribute("observValueTypes", observTypeDetails[1]);
			session.setAttribute("frmObservDetails", frmObservDetails);
			return mapping.findForward("addObservations");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of editObserDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward getObservTypeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction getObservTypeDetails ");
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String observType = frmObservDetails.getString("observType");
			Object[] observTypeDetails = preAuthObject
					.getObservTypeDetails(observType);
			request.getSession().setAttribute("observCodes",
					observTypeDetails[0]);
			request.getSession().setAttribute("observValueTypes",
					observTypeDetails[1]);
			return mapping.findForward("addObservations");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "addObservations"));
		}// end of catch(Exception exp)

	}// end of getObservTypeDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward deleteObservDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction deleteObservDetails ");
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			String authType = frmObservDetails.getString("authType");
			String authSeqID = "0";
			if ("PAT".equals(authType))
				authSeqID = frmObservDetails.getString("preAuthSeqID");
			else if ("CLM".equals(authType))
				authSeqID = frmObservDetails.getString("claimSeqID");
			String activityDtlSeqId = frmObservDetails
					.getString("activityDtlSeqId");

			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			String[] obsvrSeqIds = request.getParameterValues("chkopt");
			String listOfobsvrSeqIds = "|";// listOfobsvrSeqIds
			for (String seqId : obsvrSeqIds)
				listOfobsvrSeqIds += seqId + "|";

			preAuthObject.deleteObservDetails(new Long(authSeqID),
					listOfobsvrSeqIds, authType);

			ArrayList<String[]> observations = preAuthObject
					.getAllObservDetails(new Long(activityDtlSeqId));

			request.setAttribute("successMsg",
					"Observation Details Deleted Successfully");
			request.getSession().setAttribute("observations", observations);
			return mapping.findForward("addObservations");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of deleteObservDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward saveObserDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);

			log.debug("Inside PreAuthGenealAction addObserv ");
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			ObservationDetailsVO observationDetailsVO = new ObservationDetailsVO();
			String successMsg;
			observationDetailsVO = (ObservationDetailsVO) FormUtils
					.getFormValues(frmObservDetails, this, mapping, request);
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			if (observationDetailsVO.getObservSeqId() == null
					|| observationDetailsVO.getObservSeqId() == 0)
				successMsg = "Observation Details Added Successfully";
			else
				successMsg = "Observation Details Updated Successfully";
			observationDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
			Object results[] = preAuthObject
					.saveObservationDetails(observationDetailsVO);
			request.getSession().setAttribute("observations", results[2]);
			request.setAttribute("successMsg", successMsg);

			String activityDtlSeqId = frmObservDetails
					.getString("activityDtlSeqId");
			String authType = frmObservDetails.getString("authType");
			String preAuthSeqID = frmObservDetails.getString("preAuthSeqID");
			String claimSeqID = frmObservDetails.getString("claimSeqID");
			frmObservDetails.initialize(mapping);
			frmObservDetails.set("activityDtlSeqId", activityDtlSeqId);
			frmObservDetails.set("preAuthSeqID", preAuthSeqID);
			frmObservDetails.set("claimSeqID", claimSeqID);
			frmObservDetails.set("authType", authType);
			request.getSession().setAttribute("frmObservDetails",
					frmObservDetails);
			return mapping.findForward("saveObservDetails");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of addObserv(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward viewObservsHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction viewObservsHistory ");
			String activityDtlSeqId = (String) request
					.getParameter("activityDtlSeqId");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ArrayList<String[]> observations = preAuthObject
					.getAllObservDetails(new Long(activityDtlSeqId));
			request.getSession().setAttribute("observationDetails",
					observations);
			return mapping.findForward("viewObservationDetails");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreauthHistoryList));
		}// end of catch(Exception exp)

	}// end of viewObservsHistory(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * setMaternityMode
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setMaternityMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doGeneral");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			
			request.setAttribute("focusObj", request.getParameter("focusObj"));
			
			frmPreAuthGeneral.set("gravida", "");
			frmPreAuthGeneral.set("para", "");
			frmPreAuthGeneral.set("live", "");
			frmPreAuthGeneral.set("abortion", "");
			frmPreAuthGeneral.set("latMenstraulaPeriod", "");
			request.getSession().setAttribute(
					"encounterTypes",
					preAuthObject.getEncounterTypes(frmPreAuthGeneral
							.getString("benefitType")));
			request.getSession().setAttribute("frmPreAuthGeneral",
					frmPreAuthGeneral);
			return mapping.findForward(strPreAuthDetail);
		}// end of try
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of doGeneral(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * doGeneral forward the based on reforward parameter value
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doGeneral(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doGeneral");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;

			String path = "Pre-Authorization.Processing.System Preauth Approval";
			HttpSession session = request.getSession();
			String reforward = request.getParameter("reforward");
			if ("providerSearch".equalsIgnoreCase(reforward)) {
				TableData providerListData = new TableData(); // create new
																// table data
																// object
				providerListData.createTableInfo("ProviderListTable",
						new ArrayList());
				session.setAttribute("providerListData", providerListData);// create
																			// the
																			// required
																			// grid
																			// table
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				path = "providerSearchList";
				return mapping.findForward(path);
			} else if ("memberSearch".equalsIgnoreCase(reforward)) {
				
				TableData memberListData = new TableData(); // create new
																// table data
																// object
				memberListData.createTableInfo("MemberListTable",
						new ArrayList());
				session.setAttribute("memberListData", memberListData);// create
																			// the
																			// required
																			// grid
																			// table
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				path = "memberSearchList";
				return mapping.findForward(path);
			} else if ("clinicianSearch".equalsIgnoreCase(reforward)) {
				TableData clinicianListData = new TableData(); // create new
																// table data
																// object
				clinicianListData.createTableInfo("ClinicianListTable",
						new ArrayList());
				session.setAttribute("clinicianListData", clinicianListData);// create
																				// the
																				// required
																				// grid
																				// table
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				session.setAttribute("forwardMode", "");
				path = "clinicianSearchList";
				return mapping.findForward(path);
			} else if ("diagnosisSearch".equalsIgnoreCase(reforward)) {
				
				request.setAttribute("focusObj", request.getParameter("focusObj"));
				
				frmPreAuthGeneral.set("icdCode", "");
				frmPreAuthGeneral.set("icdCodeSeqId", "");
				frmPreAuthGeneral.set("ailmentDescription", "");
				// frmPreAuthGeneral.set("primaryAilment","");

				TableData diagnosisCodeListData = new TableData(); // create new
																	// table
																	// data
																	// object
				diagnosisCodeListData.createTableInfo("DiagnosisCodeListTable",
						new ArrayList());
				session.setAttribute("diagnosisCodeListData",
						diagnosisCodeListData);// create the required grid table
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				path = "diagnosisSearchList";
				return mapping.findForward(path);
			} else if ("addActivityDetails".equalsIgnoreCase(reforward)) {
				path = "Pre-Authorization.Processing.System Preauth Approval.ActivityDetails";
				
				request.setAttribute("focusObj", request.getParameter("focusObj"));
				
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				return mapping.findForward(path);
			} else if ("activityClinicianSearch".equalsIgnoreCase(reforward)) {
				DynaActionForm frmActivityDetails = (DynaActionForm) form;
				TableData clinicianListData = new TableData(); // create new
																// table data
																// object
				clinicianListData.createTableInfo("ClinicianListTable",
						new ArrayList());
				session.setAttribute("clinicianListData", clinicianListData);// create
																				// the
																				// required
																				// grid
																				// table
				session.setAttribute("forwardMode", "activityClinicianSearch");
				session.setAttribute("frmActivityDetails", frmActivityDetails);
				path = "clinicianSearchList";
				return mapping.findForward(path);
			} else if ("activitySearchList".equalsIgnoreCase(reforward)) {
				DynaActionForm frmActivityDetails = (DynaActionForm) form;
				TableData activityCodeListData = new TableData(); // create new
																	// table
																	// data
																	// object
				activityCodeListData.createTableInfo("ActivityCodeListTable",
						new ArrayList());
				session.setAttribute("activityCodeListData",
						activityCodeListData);// create the required grid table
				session.setAttribute("frmActivityDetails", frmActivityDetails);
				PreAuthManager preAuthObject = this.getPreAuthManagerObject();
				HashMap<String, String> networkTypes = new HashMap<>();//preAuthObject.getNetworkTypeList();
				networkTypes.put("test", "test");
				session.setAttribute("networkTypes", networkTypes);
				path = "activitySearchList";
				return mapping.findForward(path);
			} else if ("viewActivityDetails".equals(reforward)) {
				request.setAttribute("activityDtlSeqId",
						frmPreAuthGeneral.getString("activityDtlSeqId"));
				request.setAttribute("networkProviderType",
						frmPreAuthGeneral.getString("networkProviderType"));
				path = "viewActivityDetails";
				return mapping.findForward(path);
			}
			if ("overrideActivityDetails".equals(reforward)) {
				request.setAttribute("activityDtlSeqId",
						frmPreAuthGeneral.getString("activityDtlSeqId"));
				request.setAttribute("networkProviderType",
						frmPreAuthGeneral.getString("networkProviderType"));
				request.setAttribute("override",
						request.getParameter("override"));
				path = "overrideActivityDetails";
			} else if ("preauthshortfalls".equalsIgnoreCase(reforward)) {
				// path="Pre-Authorization.Processing.General.PreauthShortfall";
				path = "preauthShortFalls";
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				session.setAttribute("closeShortfalls", "goGeneral");
				return mapping.findForward(path);
			} else if ("viewShortfalls".equalsIgnoreCase(reforward)) {

				String preAuthSeqID = frmPreAuthGeneral
						.getString("preAuthSeqID");
				String shortFallSeqId = frmPreAuthGeneral
						.getString("shortFallSeqId");
				ShortfallVO shortfallVO = new ShortfallVO();
				shortfallVO.setPreAuthSeqID(new Long(preAuthSeqID));
				shortfallVO.setShortfallSeqID(new Long(shortFallSeqId));
				session.setAttribute("searchPreAuthShortfallVO", shortfallVO);
				session.setAttribute("closeShortfalls", "goGeneral");
				path = "viewShortfalls";
				return mapping.findForward(path);

			} else if ("goShortfallSearch".equalsIgnoreCase(reforward)) {
				path = "Pre-Authorization.Shortfalls.Search";
				request.setAttribute("invoked", null);
			} else if ("close".equalsIgnoreCase(reforward)) {
				DynaActionForm dynForm = (DynaActionForm) form;
				String preAuthSeqID = dynForm.getString("preAuthSeqID");
				request.setAttribute("preAuthSeqID", preAuthSeqID);
				request.setAttribute("invoked", null);
				path = "Pre-Authorization.Processing.System Preauth Approval.GetAllPreAuthDetails";
				
				request.setAttribute("focusObj", request.getParameter("focusObj"));
				
			} else if ("closeHistoryDetails".equalsIgnoreCase(reforward)) {
				path = "preauthHistoryList";
				return mapping.findForward(path);
			}
			String preAuthNo = frmPreAuthGeneral.getString("preAuthNo");
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			request.setAttribute("preAuthNo", preAuthNo);
			request.setAttribute("preAuthSeqID", preAuthSeqID);
			return mapping.findForward(path);
		}// end of try
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "preauthdetail"));
		}// end of catch(Exception exp)

	}// end of doGeneral(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * doClose forward
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doClose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doClose");
			String path = "Pre-Authorization.Processing.General.GetAllPreAuthDetails";
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) request
					.getSession().getAttribute("frmPreAuthGeneral");
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			String preAuthNo = frmPreAuthGeneral.getString("preAuthNo");
			request.setAttribute("preAuthNo", preAuthNo);
			request.setAttribute("preAuthSeqID", preAuthSeqID);
			request.setAttribute("focusObj", request.getParameter("focusObj"));
			return mapping.findForward(path);
		}// end of try
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "preauthdetail"));
		}// end of catch(Exception exp)

	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	public ActionForward closeActivities(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction closeActivities ");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			// frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
			String preAuthNo = frmActivityDetails.get("preAuthNo") == null ? ""
					: frmActivityDetails.get("preAuthNo").toString();
			String preAuthSeqID = frmActivityDetails.get("preAuthSeqID") == null ? "0"
					: frmActivityDetails.get("preAuthSeqID").toString();
			request.setAttribute("preAuthNo", preAuthNo);
			request.setAttribute("preAuthSeqID", preAuthSeqID);
			return mapping.findForward("preauthdetail");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of closeActivities(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)
	
	//Ramana
	public ActionForward deleteMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction deleteMember ");
			HttpSession session=request.getSession();
			DynaActionForm frmPreAuthGeneral= (DynaActionForm) form;
			frmPreAuthGeneral.set("memberSeqID", "");
			frmPreAuthGeneral.set("memberId","");
			frmPreAuthGeneral.set("patientName", "");
			frmPreAuthGeneral.set("memberAge","");
			frmPreAuthGeneral.set("emirateId", "");
			frmPreAuthGeneral.set("payerName", "");
			frmPreAuthGeneral.set("payerId", "");
			frmPreAuthGeneral.set("insSeqId","");
			frmPreAuthGeneral.set("policySeqId", "");
			frmPreAuthGeneral.set("patientGender", "");
			frmPreAuthGeneral.set("policyNumber", "");
			frmPreAuthGeneral.set("corporateName","");
			frmPreAuthGeneral.set("policyStartDate", "");
			frmPreAuthGeneral.set("policyEndDate", "");
			frmPreAuthGeneral.set("nationality", "");
			frmPreAuthGeneral.set("sumInsured","");
			frmPreAuthGeneral.set("availableSumInsured","");
			frmPreAuthGeneral.set("vipYorN","");
			frmPreAuthGeneral.set("preMemInceptionDt","");
			frmPreAuthGeneral.set("productName","");
			frmPreAuthGeneral.set("eligibleNetworks","");
			frmPreAuthGeneral.set("payerAuthority","");
			request.setAttribute("successMsg", "Member Details Deleted Successfully");
			session.setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
			
			return mapping.findForward("preauthdetail");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}
	

	public ActionForward getAllPreauthDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction getAllPreauthDetails ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			// frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
			// String preAuthNo=(String)request.getAttribute("preAuthNo");
			Object objPreAuthSeqID = request.getAttribute("preAuthSeqID");
			String preAuthSeqID = objPreAuthSeqID == null ? "0"
					: objPreAuthSeqID.toString();
			StringBuffer strCaption = new StringBuffer();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			HttpSession session = request.getSession();
			frmPreAuthGeneral.initialize(mapping);

			PreAuthDetailVO preAuthDetailVO = preAuthObject.getPreAuthDetails(new Long(preAuthSeqID));

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
			frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			strCaption.append(" Edit");
			strCaption.append(" [ "
					+ frmPreAuthGeneral.getString("patientName") + " ]");
			strCaption.append(
					" [ " + frmPreAuthGeneral.getString("memberId") + " ]")
					.append(" [ " + preAuthDetailVO.getPreAuthNoStatus()
							+ " ] ");
			frmPreAuthGeneral.set("caption", strCaption.toString());
			this.addToWebBoard(preAuthDetailVO, request, "PAT");

			if ("N".equals(frmPreAuthGeneral.getString("networkProviderType"))) {
				session.setAttribute("providerStates", TTKCommon
						.getStates(frmPreAuthGeneral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmPreAuthGeneral
								.getString("providerEmirate")));
			}
			session.setAttribute("encounterTypes", preAuthObject
					.getEncounterTypes(frmPreAuthGeneral
							.getString("benefitType")));
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			return this.getForward(strPreAuthDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}// end of getAllPreauthDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward historyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction historyList ");
			HttpSession session = request.getSession();
			DynaActionForm frmHistoryList = (DynaActionForm) form;
			PreAuthManager preAuthObject = null;
			preAuthObject = this.getPreAuthManagerObject();
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) session
					.getAttribute("frmPreAuthGeneral");

			if ("Y".equals(request.getParameter("Entry")))
				frmHistoryList.set("historyMode", "PAT");

			if (frmPreAuthGeneral == null
					|| frmPreAuthGeneral.getString("memberSeqID") == null
					|| frmPreAuthGeneral.getString("memberSeqID").length() < 1) {
				session.setAttribute("preauthHistoryList", null);
				request.setAttribute("errorMsg", "Select Pre-Approval Details");
				return this.getForward(strPreauthHistoryList, mapping, request);
			}

			// call the business layer to get the Pre-Auth detail
			// frmHistoryList.set("preAuthSeqID",
			// frmPreAuthGeneral.getString("preAuthSeqID"));
			ArrayList<String[]> authorizationList = preAuthObject
					.getPreauthHistoryList(
							new Long(frmPreAuthGeneral.getString("memberSeqID")),
							frmHistoryList.getString("historyMode"),null);

			session.setAttribute("preauthHistoryList", authorizationList);
			session.setAttribute("frmHistoryList", frmHistoryList);
			return this.getForward(strPreauthHistoryList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreauthHistoryList));
		}// end of catch(Exception exp)
	}// end of historyList(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doViewHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doViewHistory");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			DynaActionForm frmHistoryList = (DynaActionForm) form;
			// check if user trying to hit the tab directly with out selecting
			// the hospital
			Document historyDoc = null;
			String authSeqID = request.getParameter("authSeqID");
			authSeqID = (authSeqID == null || authSeqID.length() < 1) ? "0"
					: authSeqID;
			String historyMode = frmHistoryList.getString("historyMode");
			request.getSession().setAttribute("WorkFlow", "Preauth");
			if ("PAT".equals(historyMode)) {
				historyDoc = preAuthObject
						.getPreAuthHistory(new Long(authSeqID));
				request.setAttribute("preAuthHistoryDoc", historyDoc);
				return this.getForward(strPreAuthViewHistory, mapping, request);
			} else if ("CLM".equals(historyMode)) {
				historyDoc = preAuthObject.getClaimHistory(new Long(authSeqID));
				request.setAttribute("claimHistoryDoc", historyDoc);
				return this.getForward(strClaimViewHistory, mapping, request);
			}
			return this.getForward(strPreAuthViewHistory, mapping, request);// it's
																			// not
																			// reach
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreauthHistoryList));
		}// end of catch(Exception exp)
	}// end of doViewHistory(ActionMapping mapping,ActionForm
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
	public ActionForward uploadToDhpo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside the uploadToDhpo method of PreAuthGeneralAction");
			setLinks(request);	
			
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			
			String userID="";
			String password="";
			
			if("GnTips".equals(TTKPropertiesReader.getPropertyValue("WebserviceServerMode"))){
				userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.userID");
			    password=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.password");
			}else{
				userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.userID");
			    password=TTKPropertiesReader.getPropertyValue("DHPOWebService.password");
			}
			
			
			DhpoWebServiceVO dhpoWebServiceVO=preAuthObject.getDhpoPreauthDetails(preAuthSeqID);
			
           if(dhpoWebServiceVO.getFileContent()!=null){
        	   
          
			//DHPO Webservice code
        	   

ValidateTransactionsSoap transactionsSoap=getDhpoStubObj();	
Holder<Integer> uploadTransactionsResult=new Holder<>();
Holder<String> errorMessage=new Holder<>();
Holder<byte[]> errorReport=new Holder<>();



transactionsSoap.uploadTransaction(userID,password, dhpoWebServiceVO.getFileContent(), dhpoWebServiceVO.getFileName(), uploadTransactionsResult, errorMessage, errorReport);

String preAuthUploadStatus="N";
			if(uploadTransactionsResult.value==1||uploadTransactionsResult.value==0){				
				request.setAttribute("updated", "PreApproval details uploaded successfully");	
				preAuthUploadStatus="Y";
			}else{
				request.setAttribute("errorMsg", "PreApproval details not uploaded please check logs");
			}
			DhpoWebServiceVO logDetails=new DhpoWebServiceVO();
			
			logDetails.setPreAuthSeqID(preAuthSeqID);
			logDetails.setTransactionResult(uploadTransactionsResult.value);
			logDetails.setFileName(dhpoWebServiceVO.getFileName());
			logDetails.setErrorMessage(errorMessage.value);
			logDetails.setErrorReport(errorReport.value);
			logDetails.setPreAuthUploadStatus(preAuthUploadStatus);
			
			preAuthObject.saveDhpoPreauthLogDetails(logDetails);
			
			frmPreAuthGeneral.set("dhpoUploadStatus", preAuthUploadStatus);
			
			
  }// if(dhpoWebServiceVO.getFileContent()!=null){
           
           request.getSession().setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
			return this.getForward(strPreAuthDetail, mapping, request);
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
	public ActionForward viewDhpoLogs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside the viewDhpoLogs method of PreAuthGeneralAction");
			setLinks(request);	
			
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");		
			
			DhpoWebServiceVO logDetails=preAuthObject.getDhpoPreauthLogDetails(preAuthSeqID);
			
			
			 Reader logReader=(logDetails.getXmlFileReader()==null?new StringReader("No Logs"):logDetails.getXmlFileReader());
			
              String fileName=logDetails.getFileName()==null?"No.xml":logDetails.getFileName();
            			    	
					  response.setContentType("application/txt");
				      response.setHeader("Content-Disposition", "attachment;filename="+fileName+".txt");
				                                                       
				      
				      PrintWriter pw= response.getWriter();
		                    int ch;
		                    
		                          while ((ch = logReader.read()) != -1){
		                        	  pw.write((char)ch);	                          
		                          }
		                          
		                          pw.write("\n");			                          
		                          pw.write(TTKCommon.checkNull(logDetails.getErrorMessage()));	
		                          
		                          logReader.close();
		                          pw.flush();      
		                          pw.close(); 
				      
		            
		           
		        return null;		 
	
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)
	}// end of viewDhpoLogs(ActionMapping mapping,ActionForm
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
	public ActionForward doViewProviderDocs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside the doViewProviderDocs method of BatchPolicyAction");
			setLinks(request);
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) request	.getSession().getAttribute("frmPreAuthGeneral");
			
			String preAuthSeqID=frmPreAuthGeneral.getString("preAuthSeqID");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ArrayList<MOUDocumentVO> providerUploadedDocs=preAuthObject.getProviderDocs(preAuthSeqID);
			request.getSession().setAttribute("providerUploadedDocs",providerUploadedDocs);
			
			return mapping.findForward("providerUploadedDocsView");
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
	
	
	
	public ActionForward  doViewUploadDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException {
	    
		 ByteArrayOutputStream baos=null;
	    OutputStream sos = null;
	    FileInputStream fis = null; 
	    BufferedInputStream bis =null;
	  try{   
			
			String strFile	=	request.getParameter("filePath");
			 OnlinePreAuthManager onlinePreAuthManager = this.getOnlineAccessManagerObject();
			 InputStream iStream	=	onlinePreAuthManager.getProviderUploadedDocsDBFile(strFile);
	           bis = new BufferedInputStream(iStream);
	           baos=new ByteArrayOutputStream();
	           response.setContentType("application/pdf");
	           int ch;
	                 while ((ch = bis.read()) != -1) baos.write(ch);
	                 sos = response.getOutputStream();
	                 baos.writeTo(sos);  
	                 baos.flush();      
	                 sos.flush(); 
				      
		            }catch(Exception exp)
		            	{
		            		return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		            	}//end of catch(Exception exp)
		          finally{
		                   try{
		                         if(baos!=null)baos.close();                                           
		                         if(sos!=null)sos.close();
		                         if(bis!=null)bis.close();
		                         if(fis!=null)fis.close();
		                         }catch(Exception exception){
		                         log.error(exception.getMessage(), exception);
		                         }                     
		                 }
	       return null;		 
	}
	
	
	/*
	 * Reading and Displaying File Stored in DB
	 */
	
	 public ActionForward viewShortfalldoc(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
		    
			 ByteArrayOutputStream baos=null;
		    OutputStream sos = null;
		    FileInputStream fis = null; 
		    BufferedInputStream bis =null;
		  try{
				
			  
			  	PreAuthManager preAuthObject = this.getPreAuthManagerObject();
				String shortfallSeqID = request.getParameter("shortfallSeqID");		
				
				InputStream iStream	=	preAuthObject.getShortfallDBFile(shortfallSeqID);
				
               bis = new BufferedInputStream(iStream);
               baos=new ByteArrayOutputStream();
	             response.setContentType("application/pdf");
               int ch;
                     while ((ch = bis.read()) != -1) baos.write(ch);
                     sos = response.getOutputStream();
                     baos.writeTo(sos);  
                     baos.flush();      
                     sos.flush(); 
				      
		            }catch(Exception exp)
		            	{
		            		return this.processExceptions(request, mapping, new TTKException(exp,strPreAuthDetail));
		            	}//end of catch(Exception exp)
		        
		          finally{
		                   try{
		                         if(baos!=null)baos.close();                                           
		                         if(sos!=null)sos.close();
		                         if(bis!=null)bis.close();
		                         if(fis!=null)fis.close();
		                         }catch(Exception exception){
		                         log.error(exception.getMessage(), exception);
		                         }                     
		                 }
		       return null;		 
		}
	 
	 
	 /**
		 * Returns the PreAuthManager session object for invoking methods on it.
		 * 
		 * @return PreAuthManager session object which can be used for method
		 *         invokation
		 * @exception throws TTKException
		 */
	 private OnlinePreAuthManager getOnlineAccessManagerObject() throws TTKException
	    {
	    	OnlinePreAuthManager onlinePreAuthManager= null;
	        try
	        {
	            if(onlinePreAuthManager == null)
	            {
	                InitialContext ctx = new InitialContext();
	                //onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
	                onlinePreAuthManager = (OnlinePreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlinePreAuthManagerBean!com.ttk.business.onlineforms.OnlinePreAuthManager");
	            }//end if
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, strPreAuthDetail);
	        }//end of catch
	        return onlinePreAuthManager;
	    }//end of getOnlineAccessManagerObject()
	 
	
	/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * 
	 * @return PreAuthManager session object which can be used for method
	 *         invokation
	 * @exception throws TTKException
	 */
	private PreAuthManager getPreAuthManagerObject() throws TTKException {
		PreAuthManager preAuthManager = null;
		try {
			if (preAuthManager == null) {
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strPreAuthDetail);
		}// end of catch
		return preAuthManager;
	}// end getPreAuthManagerObject()

	

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
					+ TTKCommon.checkNull(preAuthDetailVO.getDMSRefID()));
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

	public HashMap<String, String> getPreauthDiagDetails() throws TTKException
	{
		
		PreAuthManager preAuthObject = this.getPreAuthManagerObject();
		
		return preAuthObject.getPreauthDiagDetails();
		
	}

	
	private DynaActionForm setFormValues(PreAuthDetailVO preAuthDetailVO,ActionMapping mapping,HttpServletRequest request){
		DynaActionForm frmPreAuthGeneral=(DynaActionForm) FormUtils.setFormValues("frmPreAuthGeneral", preAuthDetailVO, this, mapping,request);
		frmPreAuthGeneral.set("diagnosisList", preAuthDetailVO.getDiagnosisList());
		frmPreAuthGeneral.set("activityList", preAuthDetailVO.getActivityList());
		frmPreAuthGeneral.set("shortfallList", preAuthDetailVO.getShortfallList());
		return frmPreAuthGeneral;
		
	}
	 private void initializeValues(PreAuthDetailVO preAuthDetailVO) throws TTKException {
			preAuthDetailVO.setCorporateName(preAuthDetailVO.getGroupName());
			preAuthDetailVO.setPayerAuthority(preAuthDetailVO.getAuthorityType());
			preAuthDetailVO.setPayerId(preAuthDetailVO.getMemberId());
			preAuthDetailVO.setPayerName(preAuthDetailVO.getInsuredName());
			preAuthDetailVO.setNationality(preAuthDetailVO.getDescription());
			preAuthDetailVO.setEligibleNetworks(preAuthDetailVO.getProductCatTypeId());
			preAuthDetailVO.setPolicyStartDate(preAuthDetailVO.getEffectiveFromDate());
			preAuthDetailVO.setPolicyEndDate(preAuthDetailVO.getEffectiveToDate());
			preAuthDetailVO.setPreMemInceptionDt(preAuthDetailVO.getPolicyIssueDate());
			preAuthDetailVO.setAdmissionDate(TTKCommon.getUtilDate(preAuthDetailVO.getHospitalzationDate()));
	 }
	 
	 
	private ValidateTransactionsSoap getDhpoStubObj(){
		if(transactionsSoap==null){
			ValidateTransactions transactions=new ValidateTransactions();
			 transactionsSoap=transactions.getValidateTransactionsSoap();
		}
   return transactionsSoap;
	}
private ValidateTransactionsSoap 	transactionsSoap=null;



}// end of PreAuthGeneralAction
