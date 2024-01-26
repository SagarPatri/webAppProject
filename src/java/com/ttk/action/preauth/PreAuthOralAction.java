package com.ttk.action.preauth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ibm.icu.text.SimpleDateFormat;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.claims.ClaimDetailVO;
import com.ttk.dto.claims.HospitalizationDetailVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.AdditionalDetailVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class PreAuthOralAction extends TTKAction
{



	private static Logger log = Logger.getLogger(PreAuthOralAction.class);

	private static final String strPreAuthDetail = "PreAuthDetails";
	private static final String strPreauthdetail = "preauthdetail";
	// declare forward paths
	private static final String strEnrollmentlist = "enrollmentlist";
	private static final String strInsurancelist = "insurancelist";
	private static final String strPolicylist = "policylist";
	private static final String strCorporatelist = "corporatelist";
	private static final String strHospitallist = "hospitallist";
	private static final String strSumenhancementlist = "sumenhancementlist";

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
	private static  HashMap<String,String> providerList = null;
	 

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
			log.debug("Inside PreAuthGenealAction doView ");
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			session.setAttribute("preAuthSeqID",request.getParameter("preAuthSeqID"));
			String strDetail = "preauthgeneraldetail";
			session.setAttribute("frmPreAuthoral", frmPreAuthoral);
			//this.documentViewer(request, preAuthDetailVO);
			return mapping.findForward(strDetail);
			//return this.getForward(strDetail, mapping, request);
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
	public ActionForward doViewOral(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try {
		
			setLinks(request);
		DynaActionForm frmPreAuthoral = (DynaActionForm) form;
		HttpSession session = request.getSession();
		Toolbar toolBar = (Toolbar) session.getAttribute("toolbar");
		PreAuthManager preAuthObject = null;
		PreAuthDetailVO preAuthDetailVO = null;
		PreAuthVO preauthVO = null;
		StringBuffer strCaption = new StringBuffer();
		String strDetail = "preauthdetail";
		preAuthObject = this.getPreAuthManagerObject();
		strCaption.append(" Edit");
		PreAuthVO preAuthVO=(PreAuthVO) request.getAttribute("preAuthVO");
		
		preAuthDetailVO = preAuthObject.getPreAuthDetails(preAuthVO.getPreAuthSeqID());
			frmPreAuthoral =setFormValues(preAuthDetailVO, mapping, request);
			if ("N".equals(frmPreAuthoral.getString("networkProviderType"))) {
				session.setAttribute("providerStates", TTKCommon
						.getStates(frmPreAuthoral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmPreAuthoral
								.getString("providerEmirate")));
			}
			if(preAuthDetailVO.getRevisedDiagnosis()!=null)
			{
				request.setAttribute("styledisplayDiagnosis", "display");
			}
			if(preAuthDetailVO.getRevisedServices()!=null)
			{
				request.setAttribute("styledisplayServices", "display");
			}
			if(preAuthDetailVO.getOralRevisedApprovedAmount().floatValue()!=0)
			{
				request.setAttribute("styledisplayAprAmt", "display");
			}
			request.setAttribute("flag", "true");
			session.setAttribute("encounterTypes", preAuthObject.getEncounterTypes(frmPreAuthoral.getString("benefitType")));
			// check for conflict value
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& preAuthDetailVO.getDiscPresentYN().equals("Y"));
			frmPreAuthoral.set("caption", strCaption.toString());
			// added for CR KOC-1273

			// keep the frmPreAuthoral in session scope
			session.setAttribute("frmPreAuthoral", frmPreAuthoral);
			this.documentViewer(request, preAuthDetailVO);
			return this.getForward(strDetail, mapping, request);
		} 
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}
		
		
		
	}
	
	public ActionForward doView(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doView ");
			log.info("Inside PreAuthGenealAction doView in oral action");
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
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
			// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			if("Y".equals(request.getAttribute("onPolicyDocClose"))){
				return this.getForward(strDetail, mapping, request);
			}
			 if(session.getAttribute("oralTab")!=null)
			 {
        	  
				 preAuthDetailVO=(PreAuthDetailVO) session.getAttribute("oralTab");
			preauthVO = new PreAuthVO();
          
			preauthVO.setMemberSeqID(preAuthDetailVO.getMemberSeqID());
			preauthVO.setPreAuthSeqID(preAuthDetailVO.getPreAuthSeqID());
			preauthVO.setEnrollDtlSeqID(preAuthDetailVO.getEnrollDtlSeqID());
			preauthVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			// call the business layer to get the Pre-Auth detail
			preAuthDetailVO = preAuthObject.getPreAuthDetails(preauthVO.getPreAuthSeqID());

			strCaption.append(" [ "
					+ PreAuthWebBoardHelper.getClaimantName(request) + " ]");
			if (PreAuthWebBoardHelper.getEnrollmentId(request) != null
					&& (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(
							request).trim())))
				strCaption.append(
						" [ " + PreAuthWebBoardHelper.getEnrollmentId(request)
								+ " ]").append(
						" [ " + preAuthDetailVO.getPreAuthNoStatus() + " ] ");
			

			frmPreAuthoral = setFormValues(preAuthDetailVO, mapping, request);
			
			if ("N".equals(frmPreAuthoral.getString("networkProviderType"))) {
				session.setAttribute("providerStates", TTKCommon
						.getStates(frmPreAuthoral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmPreAuthoral
								.getString("providerEmirate")));
			}
			session.setAttribute("encounterTypes", preAuthObject
					.getEncounterTypes(frmPreAuthoral
							.getString("benefitType")));
			// check for conflict value
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& preAuthDetailVO.getDiscPresentYN().equals("Y"));
			frmPreAuthoral.set("caption", strCaption.toString());
			 }
			 else
			 {
					session.setAttribute("preauthDiagnosis", null);
					session.setAttribute("preauthActivities", null);
					session.setAttribute("preauthShortfalls", null);
					request.getSession().setAttribute("enhancedCase","ORALPREAUTH");
					frmPreAuthoral.initialize(mapping);
					session.setAttribute("frmPreAuthoral", frmPreAuthoral);
					ActionMessages actionMessages = new ActionMessages();
					ActionMessage actionMessage = new ActionMessage("error.PreAuthorization.required");
			        actionMessages.add("global.error",actionMessage); 
			        saveErrors(request,actionMessages);
			        return mapping.findForward("failure");
			 }
			// added for CR KOC-1273

			// keep the frmPreAuthoral in session scope
			 if(preAuthDetailVO.getRevisedDiagnosis()!=null)
				{
					request.setAttribute("styledisplayDiagnosis", "display");
				}
				if(preAuthDetailVO.getRevisedServices()!=null)
				{
					request.setAttribute("styledisplayServices", "display");
				}
				if(preAuthDetailVO.getOralRevisedApprovedAmount().floatValue()!=0)
				{
					request.setAttribute("styledisplayAprAmt", "display");
				}
				request.setAttribute("flag", "true");
			session.setAttribute("frmPreAuthoral", frmPreAuthoral);
			request.getSession().setAttribute("enhancedCase","ORALPREAUTH");
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
	
	
	
	public ActionForward doProviderSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
StringBuilder builder=new StringBuilder();

		try {
			
			String providerName=request.getParameter("providerName");
		if(providerName!=null&&providerName.trim().length()>0){
			providerName=providerName.toUpperCase();
			PreAuthManager preAuthObject = null;
			
			if(providerList==null){
				preAuthObject = this.getPreAuthManagerObject();		
			 providerList=preAuthObject.getProviders();
			 Set<Entry<String, String>> entries =providerList.entrySet();
				for(Entry<String, String> entry:entries){
					if(entry.getValue().startsWith(providerName)){
						builder.append(entry.getKey());
						builder.append("#");
						builder.append(entry.getValue());						
						builder.append("|");
					}else if(entry.getValue().contains(providerName)){
						builder.append(entry.getKey());
						builder.append("#");
						builder.append(entry.getValue());						
						builder.append("|");
					}
				}
				
			}else{
				 Set<Entry<String, String>> entries =providerList.entrySet();
				for(Entry<String, String> entry:entries){
					if(entry.getValue().startsWith(providerName)){
						builder.append(entry.getKey());
						builder.append("#");
						builder.append(entry.getValue());						
						builder.append("|");
					}else if(entry.getValue().startsWith(providerName)){
						builder.append(entry.getKey());
						builder.append("#");
						builder.append(entry.getValue());						
						builder.append("|");
					}
				}
			}
		PrintWriter writer=response.getWriter();
		writer.write(builder.toString());		
		writer.flush();
		writer.close();
		
	        }//if(providerName!=null&&providerName.trim().length()>0){
		return null;
	        }catch(Exception e){
			return null;
		}
	}

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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			String preAuthSeqID = frmPreAuthoral.getString("preAuthSeqID");
			String authNum = frmPreAuthoral.getString("authNum");
			String preauthStatus = frmPreAuthoral.getString("preauthStatus");

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
					.getPropertyValue("authorizationrptdir") + authNum + ".pdf";
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
				main_report_RS.beforeFirst();
				mainJasperPrint = JasperFillManager.fillReport(mainJasperReport, hashMap, mainTtkReportDataSource);
			}// end of else
			JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
			JasperExportManager.exportReportToPdfFile(mainJasperPrint,
					strPdfFile);
			frmPreAuthoral.set("letterPath", strPdfFile);
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			String preauthStatus = frmPreAuthoral.getString("preauthStatus");
			String authNum = frmPreAuthoral.getString("authNum");
			String preAuthSeqID = frmPreAuthoral.getString("preAuthSeqID");
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
					.getPropertyValue("authorizationrptdir") + authNum + ".pdf";
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
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
			frmPreAuthoral = (DynaActionForm) FormUtils.setFormValues("frmPreAuthoral", preAuthDetailVO, this, mapping,request);
			frmPreAuthoral.set("preAuthHospitalVO", FormUtils.setFormValues("frmPreAuthHospital",preAuthDetailVO.getPreAuthHospitalVO(), this, mapping,request));
			frmPreAuthoral.set("claimantDetailsVO", FormUtils.setFormValues("frmClaimantDetails", preAuthDetailVO.getClaimantVO(),this, mapping, request));
			frmPreAuthoral.set("additionalDetailVO", FormUtils.setFormValues("frmAdditionalDetail",preAuthDetailVO.getAdditionalDetailVO(), this,mapping, request));
			claimDetailVO = new ClaimDetailVO();
			frmPreAuthoral.set("claimDetailVO", FormUtils.setFormValues("frmClaimDetail", claimDetailVO, this, mapping, request));
			frmPreAuthoral.set("preAuthTypeDesc", "Manual");// to display as
																// manual in the
																// add mode.
			frmPreAuthoral.set("flowType", strFlowType.toString());// set the
																		// value
																		// from
																		// which
																		// flow
																		// ur
																		// are.
			strCaption.append("Add");
			if (userSecurityProfile.getBranchID() != null) {
				frmPreAuthoral.set("officeSeqID", userSecurityProfile
						.getBranchID().toString());
			}// end of if(userSecurityProfile.getBranchID()!=null)
			else {
				frmPreAuthoral.set("officeSeqID", "");
			}// end of else
			frmPreAuthoral.set("caption", strCaption.toString());
			frmPreAuthoral.set("alPrevClaimList", alPrevClaimList);
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthoral",
					frmPreAuthoral);
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			StringBuffer strCaption = new StringBuffer();
			String strDetail = "preauthdetail";
			strCaption.append("Add");
			frmPreAuthoral.initialize(mapping);
			frmPreAuthoral.set("networkProviderType", "Y");
			frmPreAuthoral.set("caption", strCaption.toString());
			frmPreAuthoral.set("preAuthNoStatus", "FRESH");
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthoral",
					frmPreAuthoral);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date=new Date();
			frmPreAuthoral.set("receiveDate",dateFormat.format(date));
			SimpleDateFormat time = new SimpleDateFormat("hh:mm");
			frmPreAuthoral.set("receiveTime",time.format(date));
			SimpleDateFormat AMorPM = new SimpleDateFormat("a");
			frmPreAuthoral.set("receiveDay",AMorPM.format(date));
			request.getSession().setAttribute("preauthDiagnosis", null);
			request.getSession().setAttribute("preauthActivities", null);
			request.getSession().setAttribute("preauthShortfalls", null);
			request.getSession().setAttribute("enhancedCase","ORALPREAUTH");
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			String strDetail = "preauthdetail";
			StringBuffer strCaption = new StringBuffer();
			HttpSession session = request.getSession();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			// Toolbar toolBar =
			// (Toolbar)request.getSession().getAttribute("toolbar");

			preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(frmPreAuthoral, this, mapping, request);
			preAuthDetailVO.setOralORsystemStatus("ORAL");//added to make edit and link button display after saving details
			
			if(!(preAuthDetailVO.getTempdiagnosis()==null || preAuthDetailVO.getTempdiagnosis()=="" ||preAuthDetailVO.getTempdiagnosis().trim().length()==0) )
			{
				if(!preAuthDetailVO.getTempdiagnosis().equalsIgnoreCase(preAuthDetailVO.getRevisedDiagnosis()))
				{
					preAuthDetailVO.setDiagnosis(preAuthDetailVO.getTempdiagnosis());
				}
				
			}
			
			if(!(preAuthDetailVO.getTempservices()==null || preAuthDetailVO.getTempservices()=="" ||preAuthDetailVO.getTempservices().trim().length()==0) )
			{
				if(!preAuthDetailVO.getTempservices().equalsIgnoreCase(preAuthDetailVO.getRevisedServices()))
				{
					preAuthDetailVO.setServices(preAuthDetailVO.getTempservices());
				}
				
			}
			 
			if(!(preAuthDetailVO.getTempAprAmt()==null ||preAuthDetailVO.getTempAprAmt().floatValue()==0) )
			{
				float tempAprAmt=preAuthDetailVO.getTempAprAmt().floatValue();
				
				float revisedAprAmt=preAuthDetailVO.getOralRevisedApprovedAmount().floatValue();
				
				if(!(tempAprAmt==revisedAprAmt))
				{
					
					preAuthDetailVO.setOralApprovedAmount(new BigDecimal(tempAprAmt));
				}
				
			}
			
	
			
			String preAuthNo = preAuthDetailVO.getPreAuthNo();
			Long preAuthSeqID = preAuthDetailVO.getPreAuthSeqID();
			Object[] objArrayResult = new Object[3];
			if (preAuthNo == null || "".equals(preAuthNo)
					|| preAuthNo.trim().length() < 1 || preAuthSeqID == null
					|| preAuthSeqID == 0) {
				preAuthDetailVO.setUpdatedBy((TTKCommon.getUserSeqId(request)));
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
			
			Long preAuthSeqId = (Long) objArrayResult[0];
			request.setAttribute("flag", "true");
			if (preAuthSeqId != null) {
				// call the business layer to get the Pre-Auth detail
				preAuthDetailVO = preAuthObject.getPreAuthDetails(preAuthSeqId);
				
				 if(preAuthDetailVO.getRevisedDiagnosis()!=null)
					{
						request.setAttribute("styledisplayDiagnosis", "display");
					}
					if(preAuthDetailVO.getRevisedServices()!=null)
					{
						request.setAttribute("styledisplayServices", "display");
					}
					if(preAuthDetailVO.getOralRevisedApprovedAmount().floatValue()!=0)
					{
						request.setAttribute("styledisplayAprAmt", "display");
					}
					request.setAttribute("flag", "true");
				
				preAuthDetailVO.setProviderId(preAuthDetailVO.getStrHospitalId());
				
				
				
				session.setAttribute("oralTab",preAuthDetailVO);
				
				frmPreAuthoral = (DynaActionForm) FormUtils.setFormValues("frmPreAuthoral", preAuthDetailVO, this, mapping,request);
				
				strCaption.append(" Edit");
				strCaption.append(" [ "
						+ frmPreAuthoral.getString("patientName") + " ]");
				strCaption.append(
						" [ " + frmPreAuthoral.getString("memberId") + " ]")
						.append(" [ " + preAuthDetailVO.getPreAuthNoStatus()
								+ " ] ");
				frmPreAuthoral.set("caption", strCaption.toString());

				session.setAttribute("encounterTypes", preAuthObject.getEncounterTypes(frmPreAuthoral.getString("benefitType")));
				session.setAttribute("frmPreAuthoral", frmPreAuthoral);
							
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


	

	
	public ActionForward setProviderMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction setProviderMode ");

			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			String networkProviderType = (String) frmPreAuthoral
					.get("networkProviderType");
			HttpSession session = request.getSession();
			if ("N".equals(networkProviderType)) {
				frmPreAuthoral.set("networkProviderType", "N");
			} else {
				frmPreAuthoral.set("providerAddress", "");
				frmPreAuthoral.set("providerPobox", "");
				frmPreAuthoral.set("providerArea", "");
				frmPreAuthoral.set("providerEmirate", "");
				frmPreAuthoral.set("providerCountry", "");
				frmPreAuthoral.set("networkProviderType", "Y");
			}
			frmPreAuthoral.set("providerName", "");
			frmPreAuthoral.set("providerId", "");
			frmPreAuthoral.set("providerSeqId", "");
			frmPreAuthoral.set("clinicianName", "");
			frmPreAuthoral.set("clinicianId", "");
			session.setAttribute("frmPreAuthoral", frmPreAuthoral);
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;

			DiagnosisDetailsVO diagnosisDetailsVO2 = ((ArrayList<DiagnosisDetailsVO>) session
					.getAttribute("preauthDiagnosis")).get(new Integer(request
					.getParameter("rownum")).intValue());

			frmPreAuthoral.set("primaryAilment",
					diagnosisDetailsVO2.getPrimaryAilment());
			frmPreAuthoral.set("ailmentDescription",
					diagnosisDetailsVO2.getAilmentDescription());
			frmPreAuthoral.set("icdCode", diagnosisDetailsVO2.getIcdCode());
			frmPreAuthoral.set("icdCodeSeqId", diagnosisDetailsVO2
					.getIcdCodeSeqId().toString());
			frmPreAuthoral.set("diagSeqId", diagnosisDetailsVO2
					.getDiagSeqId().toString());

			session.setAttribute("frmPreAuthoral", frmPreAuthoral);

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

	

	

	public ActionForward saveAndCompletePreauth(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {

			setLinks(request);
			log.debug("Inside PreAuthGenealAction saveAndCompletePreauth");
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;

			String preauthStatus = frmPreAuthoral.getString("preauthStatus");
			String approvedAmount = frmPreAuthoral
					.getString("approvedAmount");
			approvedAmount = (approvedAmount == null || approvedAmount.length() < 1) ? "0"
					: approvedAmount;
			if ("APR".equals(preauthStatus)
					&& (new Double(approvedAmount).doubleValue() <= 0)) {

				request.setAttribute("errorMsg",
						"Approved Amount Should Be Greater Than Zero");
				return this.getForward(strPreAuthDetail, mapping, request);
			}

			PreAuthDetailVO preAuthDetailVO = new PreAuthDetailVO();
			preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
					frmPreAuthoral, this, mapping, request);
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			StringBuffer strCaption = new StringBuffer();

			// errorMsg

			preAuthDetailVO.setAddedBy(TTKCommon.getUserSeqId(request));
			preAuthObject.saveAndCompletePreauth(preAuthDetailVO);

			PreAuthDetailVO preAuthDetailVO2 = preAuthObject.getPreAuthDetails(preAuthDetailVO.getPreAuthSeqID());

			frmPreAuthoral = setFormValues(preAuthDetailVO2, mapping, request);
			strCaption.append(" Edit");
			strCaption.append(" [ "
					+ frmPreAuthoral.getString("patientName") + " ]");
			strCaption.append(" [ " + frmPreAuthoral.getString("memberId")
					+ " ]");
			frmPreAuthoral.set("caption", strCaption.toString());
			request.getSession().setAttribute("frmPreAuthoral",frmPreAuthoral);

			request.setAttribute("successMsg",
					"Preauth completed successfully!");
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

	

	public ActionForward setMaternityMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction doGeneral");
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			frmPreAuthoral.set("gravida", "");
			frmPreAuthoral.set("para", "");
			frmPreAuthoral.set("live", "");
			frmPreAuthoral.set("abortion", "");
			request.getSession().setAttribute("encounterTypes",preAuthObject.getEncounterTypes(frmPreAuthoral.getString("benefitType")));
		
			request.getSession().setAttribute("frmPreAuthoral",frmPreAuthoral);
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;

			String path = "Pre-Authorization.Processing.General";
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
				session.setAttribute("frmPreAuthoral", frmPreAuthoral);
				path = "providerSearchList";
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
				session.setAttribute("frmPreAuthoral", frmPreAuthoral);
				session.setAttribute("forwardMode", "");
				path = "clinicianSearchList";
				return mapping.findForward(path);
			} else if ("diagnosisSearch".equalsIgnoreCase(reforward)) {
				frmPreAuthoral.set("icdCode", "");
				frmPreAuthoral.set("icdCodeSeqId", "");
				frmPreAuthoral.set("ailmentDescription", "");
				// frmPreAuthoral.set("primaryAilment","");

				TableData diagnosisCodeListData = new TableData(); // create new
																	// table
																	// data
																	// object
				diagnosisCodeListData.createTableInfo("DiagnosisCodeListTable",
						new ArrayList());
				session.setAttribute("diagnosisCodeListData",
						diagnosisCodeListData);// create the required grid table
				session.setAttribute("frmPreAuthoral", frmPreAuthoral);
				path = "diagnosisSearchList";
				return mapping.findForward(path);
			} else if ("addActivityDetails".equalsIgnoreCase(reforward)) {
				path = "Pre-Authorization.Processing.General.ActivityDetails";
				session.setAttribute("frmPreAuthoral", frmPreAuthoral);
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
				HashMap<String, String> networkTypes = new HashMap<>();// preAuthObject.getNetworkTypeList();
				networkTypes.put("test", "test");
				session.setAttribute("networkTypes", networkTypes);
				path = "activitySearchList";
				return mapping.findForward(path);
			} else if ("viewActivityDetails".equals(reforward)) {
				request.setAttribute("activityDtlSeqId",
						frmPreAuthoral.getString("activityDtlSeqId"));
				request.setAttribute("networkProviderType",
						frmPreAuthoral.getString("networkProviderType"));
				path = "viewActivityDetails";
				return mapping.findForward(path);
			}
			if ("overrideActivityDetails".equals(reforward)) {
				request.setAttribute("activityDtlSeqId",
						frmPreAuthoral.getString("activityDtlSeqId"));
				request.setAttribute("networkProviderType",
						frmPreAuthoral.getString("networkProviderType"));
				request.setAttribute("override",
						request.getParameter("override"));
				path = "overrideActivityDetails";
			} else if ("preauthshortfalls".equalsIgnoreCase(reforward)) {
				// path="Pre-Authorization.Processing.General.PreauthShortfall";
				path = "preauthShortFalls";
				session.setAttribute("frmPreAuthoral", frmPreAuthoral);
				session.setAttribute("closeShortfalls", "goGeneral");
				return mapping.findForward(path);
			} else if ("viewShortfalls".equalsIgnoreCase(reforward)) {

				String preAuthSeqID = frmPreAuthoral
						.getString("preAuthSeqID");
				String shortFallSeqId = frmPreAuthoral
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
				path = "Pre-Authorization.Processing.General.GetAllPreAuthDetails";
			} else if ("closeHistoryDetails".equalsIgnoreCase(reforward)) {
				path = "preauthHistoryList";
				return mapping.findForward(path);
			}
			else if ("preauthdocUpload".equalsIgnoreCase(reforward)) { 
				
				String policyseqid = "";
				ArrayList alPolicyDocs = new ArrayList();

				PreAuthManager preAuthObject = this.getPreAuthManagerObject();
				policyseqid =request.getParameter("policySeqId");
				if (policyseqid.trim()!="" || policyseqid.trim().length()!=0)
				{
					
					Long policySeq_id= Long.valueOf(policyseqid);
					alPolicyDocs = preAuthObject.getPolicyDocsUploads(policySeq_id);
				}
				request.setAttribute("alPolicyDocs", alPolicyDocs);
				path = "preauthdocUpload";
				return mapping.findForward(path);
			}
			String preAuthNo = frmPreAuthoral.getString("preAuthNo");
			String preAuthSeqID = frmPreAuthoral.getString("preAuthSeqID");
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) request
					.getSession().getAttribute("frmPreAuthoral");
			String preAuthSeqID = frmPreAuthoral.getString("preAuthSeqID");
			String preAuthNo = frmPreAuthoral.getString("preAuthNo");
			request.setAttribute("preAuthNo", preAuthNo);
			request.setAttribute("preAuthSeqID", preAuthSeqID);
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

	public ActionForward getAllPreauthDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction getAllPreauthDetails ");
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			// frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
			// String preAuthNo=(String)request.getAttribute("preAuthNo");
			Object objPreAuthSeqID = request.getAttribute("preAuthSeqID");
			String preAuthSeqID = objPreAuthSeqID == null ? "0"
					: objPreAuthSeqID.toString();
			StringBuffer strCaption = new StringBuffer();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			HttpSession session = request.getSession();
			frmPreAuthoral.initialize(mapping);

			PreAuthDetailVO preAuthDetailVO = preAuthObject.getPreAuthDetails(new Long(preAuthSeqID));

			frmPreAuthoral = setFormValues(preAuthDetailVO, mapping, request);
			strCaption.append(" Edit");
			strCaption.append(" [ "
					+ frmPreAuthoral.getString("patientName") + " ]");
			strCaption.append(
					" [ " + frmPreAuthoral.getString("memberId") + " ]")
					.append(" [ " + preAuthDetailVO.getPreAuthNoStatus()
							+ " ] ");
			frmPreAuthoral.set("caption", strCaption.toString());
			this.addToWebBoard(preAuthDetailVO, request, "PAT");

			if ("N".equals(frmPreAuthoral.getString("networkProviderType"))) {
				session.setAttribute("providerStates", TTKCommon
						.getStates(frmPreAuthoral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmPreAuthoral
								.getString("providerEmirate")));
			}
			session.setAttribute("encounterTypes", preAuthObject
					.getEncounterTypes(frmPreAuthoral
							.getString("benefitType")));
			session.setAttribute("frmPreAuthoral", frmPreAuthoral);
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) session
					.getAttribute("frmPreAuthoral");

			if ("Y".equals(request.getParameter("Entry")))
				frmHistoryList.set("historyMode", "PAT");

			if (frmPreAuthoral == null
					|| frmPreAuthoral.getString("memberSeqID") == null
					|| frmPreAuthoral.getString("memberSeqID").length() < 1) {
				session.setAttribute("preauthHistoryList", null);
				request.setAttribute("errorMsg", "Select Pre-Auth Details");
				return this.getForward(strPreauthHistoryList, mapping, request);
			}

			// call the business layer to get the Pre-Auth detail
			// frmHistoryList.set("preAuthSeqID",
			// frmPreAuthoral.getString("preAuthSeqID"));
			ArrayList<String[]> authorizationList = preAuthObject
					.getPreauthHistoryList(
							new Long(frmPreAuthoral.getString("memberSeqID")),
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			ClaimantVO claimantVO = null;
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			ClaimManager claimObject = null;
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "claimsdetail";
				claimObject = this.getClaimManagerObject();
			}// end of if(strActiveTab.equals(strClaims))

			DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthoral
					.get("claimantDetailsVO");
			// if pre-auth is in process, then change pre-auth type to manual.
			if (frmPreAuthoral.get("completedYN").equals("N")) {
				// If it is not a Manual-Regular PreAuth made it as Manual
				if (!("MRG".equals(frmPreAuthoral.get("preAuthTypeID")))) {
					frmPreAuthoral.set("preAuthTypeID", "MAN");
					frmPreAuthoral.set("preAuthTypeDesc", "Manual");
				}// end of
					// if(!("MRG".equals(frmPreAuthoral.get("preAuthTypeID"))))
				claimantVO = new ClaimantVO();
				frmPreAuthoral.set("claimantDetailsVO", FormUtils
						.setFormValues("frmClaimantDetails", claimantVO, this,
								mapping, request));
			}// end of if(frmPreAuthoral.get("completedYN").equals("N"))
			else {
				frmClaimantDetails.set("enrollmentID", "");
			}// end of else
			toolBar.getConflictIcon().setVisible(
					toolBar.getConflictIcon().isVisible()
							&& frmPreAuthoral.get("discPresentYN").equals(
									"Y"));
			if (((frmPreAuthoral.get("authNbr") != null && (!""
					.equals(frmPreAuthoral.get("authNbr")))) || (frmPreAuthoral
					.get("prevHospClaimSeqID") != null))
					&& strActiveTab.equals(strClaims)) {
				clearPreauthHospital(mapping, frmPreAuthoral, request,
						claimObject, "");
			}// end of if(((frmPreAuthoral.get("authNbr") != null &&
				// (!"".equals(frmPreAuthoral.get("authNbr")))) ||
				// (frmPreAuthoral.get("prevHospClaimSeqID") != null)) &&
				// strActiveTab.equals(strClaims))
			frmPreAuthoral.set("frmChanged", "changed");
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			Toolbar toolBar = (Toolbar) request.getSession().getAttribute(
					"toolbar");
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "claimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthoral
					.get("claimantDetailsVO");
			// if pre-auth is in process, then change pre-auth type to manual.
			if (frmPreAuthoral.get("completedYN").equals("N")) {
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
							&& frmPreAuthoral.get("discPresentYN").equals(
									"Y"));
			frmPreAuthoral.set("frmChanged", "changed");
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
			// OPD_4_hosptial
			String paymentType = request.getParameter("paymentto");
			request.getSession().setAttribute("paymentType", paymentType);
			// OPD_4_hosptial
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "claimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			preAuthHospitalVO = new PreAuthHospitalVO();
			frmPreAuthoral.set("preAuthHospitalVO", FormUtils.setFormValues(
					"frmPreAuthHospital", preAuthHospitalVO, this, mapping,
					request));
			frmPreAuthoral.set("frmChanged", "changed");
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
			strForward = "authorizationlist";
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			ClaimManager claimObject = null;
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "claimsdetail";
				claimObject = this.getClaimManagerObject();
			}// end of if(strActiveTab.equals(strClaims))
			strForward = strDetail;
			if (frmPreAuthoral.get("authNbr") != null
					&& (!"".equals(frmPreAuthoral.get("authNbr")))
					&& strActiveTab.equals(strClaims))
				clearPreauthHospital(mapping, frmPreAuthoral, request,
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			PreAuthHospitalVO preAuthHospitalVO = null;
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "claimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			strForward = strDetail;
			HashMap hmPrevHospList = (HashMap) frmPreAuthoral
					.get("hmPrevHospList");
			if (!frmPreAuthoral.get("prevHospClaimSeqID").equals("")) {
				HospitalizationDetailVO hospitalizationDetailVO = (HospitalizationDetailVO) hmPrevHospList
						.get(TTKCommon.getLong(frmPreAuthoral.get(
								"prevHospClaimSeqID").toString()));
				frmPreAuthoral.set("clmAdmissionDate", TTKCommon
						.getFormattedDate(hospitalizationDetailVO
								.getAdmissionDate()));
				frmPreAuthoral.set("admissionDay",
						hospitalizationDetailVO.getAdmissionDay());
				frmPreAuthoral.set("clmAdmissionTime",
						hospitalizationDetailVO.getAdmissionTime());
				frmPreAuthoral.set("dischargeDate", TTKCommon
						.getFormattedDate(hospitalizationDetailVO
								.getDischargeDate()));
				frmPreAuthoral.set("dischargeDay",
						hospitalizationDetailVO.getDischargeDay());
				frmPreAuthoral.set("dischargeTime",
						hospitalizationDetailVO.getDischargeTime());
				frmPreAuthoral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital",
								hospitalizationDetailVO.getPreauthHospitalVO(),
								this, mapping, request));
			}// end of
				// if(!frmPreAuthoral.get("prevHospClaimSeqID").equals(""))
			else {
				preAuthHospitalVO = new PreAuthHospitalVO();
				frmPreAuthoral.set("clmAdmissionDate", "");
				frmPreAuthoral.set("admissionDay", "");
				frmPreAuthoral.set("clmAdmissionTime", "");
				frmPreAuthoral.set("dischargeDate", "");
				frmPreAuthoral.set("dischargeDay", "");
				frmPreAuthoral.set("dischargeTime", "");
				frmPreAuthoral.set("preAuthHospitalVO", FormUtils
						.setFormValues("frmPreAuthHospital", preAuthHospitalVO,
								this, mapping, request));
			}// end of else
				// if(!frmPreAuthoral.get("prevHospClaimSeqID").equals(""))
			frmPreAuthoral.set("frmChanged", "changed");
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthManager preAuthObject = null;
			ClaimManager claimObject = null;
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
				strDetail = "claimsdetail";
				claimObject = this.getClaimManagerObject();
				// call the bussiness layer to override the completed Claims
				preAuthDetailVO = claimObject.overrideClaim(
						ClaimsWebBoardHelper.getClaimsSeqId(request),
						TTKCommon.getUserSeqId(request));
			}// end of if(strActiveTab.equals(strClaims))

			if (preAuthDetailVO != null) {
				// set the review information back to form
				frmPreAuthoral.set("eventSeqID", preAuthDetailVO
						.getEventSeqID().toString());
				frmPreAuthoral.set("reviewCount", preAuthDetailVO
						.getReviewCount().toString());
				frmPreAuthoral.set("requiredReviewCnt", preAuthDetailVO
						.getRequiredReviewCnt().toString());
				frmPreAuthoral.set("review", preAuthDetailVO.getReview()
						.toString());
				frmPreAuthoral.set("eventName", preAuthDetailVO
						.getEventName().toString());

				// If Workflow is overrided set flag as N
				if (!(preAuthDetailVO.getEventName().contains("Complete"))) {
					frmPreAuthoral.set("showCodingOverrideYN", "Y");
					frmPreAuthoral.set("completedYN", "N");
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
			DynaActionForm frmPreAuthoral = (DynaActionForm) request
					.getSession().getAttribute("frmPreAuthoral");
			DynaActionForm frmClaimantDetails = (DynaActionForm) frmPreAuthoral
					.get("claimantDetailsVO");
			DynaActionForm frmAdditionalDetail = (DynaActionForm) frmPreAuthoral
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
						&& ((String) frmPreAuthoral.get("preAuthTypeID"))
								.equals("MAN")) {
					frmAdditionalDetail.set("employeeName", "");
					frmAdditionalDetail.set("joiningDate", "");
					frmAdditionalDetail.set("employeeNbr", "");
				}// end of if(strPolicyTypeID.equals("NCR")&&
					// ((String)frmPreAuthoral.get("preAuthTypeID")).equals("MAN"))
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				if (strPolicyTypeID.equals("COR")) {
					frmClaimantDetails.set("policyHolderName", "");
				}// end of if(strPolicyTypeID.equals("COR"))
				strDetail = "claimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			frmClaimantDetails.set("groupRegnSeqID", "");
			frmClaimantDetails.set("groupID", "");
			frmClaimantDetails.set("groupName", "");
			frmPreAuthoral.set("frmChanged", "changed");
			request.getSession().setAttribute("frmPreAuthoral",
					frmPreAuthoral);
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
	 * Returns the ClaimManager session object for invoking methods on it.
	 * 
	 * @return ClaimManager session object which can be used for method
	 *         invokation
	 * @exception throws TTKException
	 */
	private ClaimManager getClaimManagerObject() throws TTKException {
		ClaimManager claimManager = null;
		try {
			if (claimManager == null) {
				InitialContext ctx = new InitialContext();
				claimManager = (ClaimManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
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
	 * @param ClaimManager
	 *            session object which can be used for method invokation
	 * @throws TTKException
	 *             if any runtime exception occures
	 */
	private void clearPreauthHospital(ActionMapping mapping,
			DynaActionForm frmPreAuthoral, HttpServletRequest request,
			ClaimManager claimObject, String strIdentity) throws TTKException {
		try {
			PreAuthHospitalVO preAuthHospitalVO = null;
			/*
			 * if(ClaimsWebBoardHelper.getEnrollDetailSeqId(request) != null){
			 * int iResult =
			 * claimObject.releasePreauth(ClaimsWebBoardHelper.getEnrollDetailSeqId
			 * (request)); }//end of
			 * if(ClaimsWebBoardHelper.getEnrollDetailSeqId(request) != null)
			 */frmPreAuthoral.set("enrollDtlSeqID", "");
			frmPreAuthoral.set("authNbr", "");
			frmPreAuthoral.set("prevHospClaimSeqID", "");
			frmPreAuthoral.set("receivedDate", "");
			frmPreAuthoral.set("receivedTime", "");
			frmPreAuthoral.set("receivedDay", "");
			frmPreAuthoral.set("approvedAmt", "");
			frmPreAuthoral.set("preauthTypeDesc", "");
			if (!strPre_Authorization.equals(strIdentity)) {
				frmPreAuthoral.set("hmPrevHospList", new HashMap());
				frmPreAuthoral.set("clmAdmissionDate", "");
				frmPreAuthoral.set("clmAdmissionTime", "");
				frmPreAuthoral.set("admissionDay", "");
				frmPreAuthoral.set("dischargeDate", "");
				frmPreAuthoral.set("dischargeTime", "");
				frmPreAuthoral.set("dischargeDay", "");
			}// end of if(!strPre_Authorization.equals(strIdentity))
			frmPreAuthoral.set("frmChanged", "changed");
			preAuthHospitalVO = new PreAuthHospitalVO();
			frmPreAuthoral.set("preAuthHospitalVO", FormUtils.setFormValues(
					"frmPreAuthHospital", preAuthHospitalVO, this, mapping,
					request));
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strPreAuthDetail);
		}// end of catch
	}// end of clearPreauthHospital(ActionMapping mapping,DynaActionForm
		// frmPreAuthoral,HttpServletRequest request,ClaimManager
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
	private DynaActionForm setFormValues(PreAuthDetailVO preAuthDetailVO,ActionMapping mapping,HttpServletRequest request){
		DynaActionForm frmPreAuthoral=(DynaActionForm) FormUtils.setFormValues("frmPreAuthoral", preAuthDetailVO, this, mapping,request);
		frmPreAuthoral.set("diagnosisList", preAuthDetailVO.getDiagnosisList());
		frmPreAuthoral.set("activityList", preAuthDetailVO.getActivityList());
		frmPreAuthoral.set("shortfallList", preAuthDetailVO.getShortfallList());
		return frmPreAuthoral;
		
	}
	
}
