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

package com.ttk.action.preauth;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLXML;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.dom4j.Document;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.Visitor;
import org.dom4j.io.SAXReader;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.CeedSoapSaajClient;
import com.ttk.business.claims.NamesapceChangingVisitor;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.business.webservice.dhpo.ValidateTransactions;
import com.ttk.business.webservice.dhpo.ValidateTransactionsSoap;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;
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
public class PreAuthGeneralAction extends TTKAction {

	private static Logger log = Logger.getLogger(PreAuthGeneralAction.class);

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
	private static final String strBenefitDetails = "benefitDetails";
	private static final String strViewDocumnet = "viewDocument";
	private static final String strPreauthHistoryPATCLM = "preauthHistorypatclm";
	

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
	
	
	public ActionForward doViewDocument(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setLinks(request);
	String activityId=request.getParameter("activityId");
	PreAuthManager preAuthObject = null;
	ByteArrayOutputStream bos=null;
	ServletOutputStream sos=null;
	preAuthObject = this.getPreAuthManagerObject();
	try {
		Document document=preAuthObject.getPATXmlForViewDocument(new Long(request.getParameter("preAuthSeqID")));
	bos=new ByteArrayOutputStream();
	if(document!=null&&activityId!=null&&document.selectSingleNode("//Activity[ID="+activityId+"]/Observation/Type")!=null&&document.selectSingleNode("//Activity[ID="+activityId+"]/Observation/Type").getText().equalsIgnoreCase("File")&&document.selectSingleNode("//Activity[ID="+activityId+"]/Observation/Value")!=null&&document.selectSingleNode("//Activity[ID="+activityId+"]/Observation/Value").getText()!=null)
	{
	byte[] attchedData = DatatypeConverter.parseBase64Binary(document.selectSingleNode("//Activity[ID="+activityId+"]/Observation/Value").getText());
	bos.write(attchedData); 
	}
	else
	{
		bos.write("<html><body><body><div style='color: red;' align='center'><h2>Sorry No Document found for this ActivityCode</h2></div></body></html>".getBytes());
	}
	response.setContentType("text/html");
    sos=response.getOutputStream();
    bos.writeTo(sos);
    bos.flush();
    sos.flush();
	//request.setAttribute("boas", bos);
	}
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(
				exp, strPreAuthDetail));
	}// end of catch(Exception exp)
	finally
	{
		if(bos!=null)bos.close();
		if(sos!=null)sos.close();
	}
				return null;
	
	}
	
	
	
	public ActionForward doOralView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction dooralView ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			
			Long preauthno=Long.valueOf((String) session.getAttribute("preAuthSeqID"));
			
			//Toolbar toolBar = (Toolbar) session.getAttribute("toolbar");
			PreAuthManager preAuthObject = null;
			PreAuthDetailVO preAuthDetailVO = null;
			StringBuffer strCaption = new StringBuffer();
			String strDetail = "preauthdetail";
			preAuthObject = this.getPreAuthManagerObject();
           
			strCaption.append(" Edit");
				
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

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
			
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
			log.debug("Inside PreAuthGenealAction doView ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			Toolbar toolBar = (Toolbar) session.getAttribute("toolbar");
			PreAuthManager preAuthObject = null;
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthVO preauthVO = null;
			StringBuffer strCaption = new StringBuffer();
			String strDetail = "preauthdetail";
			preAuthObject = this.getPreAuthManagerObject();

			strCaption.append("Edit");
			// check if user trying to hit the tab directly with out selecting
			// the pre-auth
			if (PreAuthWebBoardHelper.checkWebBoardId(request) == null) {
				session.setAttribute("preauthDiagnosis", null);
				session.setAttribute("preauthActivities", null);
				session.setAttribute("preauthShortfalls", null);
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				ActionMessages actionMessages = new ActionMessages();
				ActionMessage actionMessage = new ActionMessage("error.PreAuthorization.required");
		        actionMessages.add("global.error",actionMessage); 
		        saveErrors(request,actionMessages);
		        return mapping.findForward("failure");
				
			}// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)

			preauthVO = new PreAuthVO();
			preauthVO.setMemberSeqID(PreAuthWebBoardHelper.getMemberSeqId(request));
			preauthVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			preauthVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
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

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
			
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
			
			if(("DHP".equals(preAuthDetailVO.getPreAuthRecvTypeID()))&&((preAuthDetailVO.getCount_flag())>1)){
				request.setAttribute("errorMsg","Invalid Member Id: More than one member found for the searched Member Id. Please select any one member id from the search results.");
				frmPreAuthGeneral.set("memberSeqID","");
				//frmPreAuthGeneral.set("memberId","");
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
			}
					
				else if(("DHP".equals(preAuthDetailVO.getPreAuthRecvTypeID())) && preAuthDetailVO.getPreAuthNo()!=null && preAuthDetailVO.getPreAuthNo()!="" && preAuthDetailVO.getCount_flag()==0)
				{
					if(preAuthDetailVO.getCount_flag2()==1)
						
					{
						frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues("frmPreAuthGeneral", preAuthDetailVO, this, mapping,
								request);
					}
					if(preAuthDetailVO.getCount_flag2()==0)
					{
						request.setAttribute("errorMsg", "Invalid Member Id/Emirates Id: Member Id/Emirates Id  not found.");
						frmPreAuthGeneral.set("memberSeqID","");
						
						/*frmPreAuthGeneral.set("memberId","");*/
						frmPreAuthGeneral.set("patientName", "");
						frmPreAuthGeneral.set("memberAge","");
						/*frmPreAuthGeneral.set("emirateId", "");*/
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
						
						}
					if(preAuthDetailVO.getCount_flag2()>1)
					{
						request.setAttribute("errorMsg", "Invalid Emirates Id: More than one member found for the searched Emirates Id. Please select any one Emirates id from the search results");
						frmPreAuthGeneral.set("memberSeqID","");
						
						frmPreAuthGeneral.set("memberId","");
						frmPreAuthGeneral.set("patientName", "");
						frmPreAuthGeneral.set("memberAge","");
						/*frmPreAuthGeneral.set("emirateId", "");*/
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
						
					}
					
				}
				else if(preAuthDetailVO.getPreAuthNo()!=null && preAuthDetailVO.getPreAuthNo()!="" && preAuthDetailVO.getCount_flag()==0){
					request.setAttribute("errorMsg", "Invalid Member Id: Member Id not found.");
					frmPreAuthGeneral.set("memberSeqID","");
					
					//frmPreAuthGeneral.set("memberId","");
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
				}

				
				if(preAuthDetailVO.getMemberWeight()!=null)
				{
					if(memberAgeValidation(frmPreAuthGeneral))
					frmPreAuthGeneral.set("memberwtflag","Y");
				}
				
				//frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues("frmPreAuthGeneral", preAuthDetailVO, this, mapping,request);--srikanth
				
				if(preAuthDetailVO.getPreAuthNoStatus().equals("ENHANCEMENT")){
					frmPreAuthGeneral.set("modeFlagforENHACEMENTMEMERID", "MEMBERIDDISABLE");
					frmPreAuthGeneral.set("cntFlagforENHACEMENTMEMERID", "ENHDIS");
					
			}
				/*if(session.getAttribute("back") != null)
				{
					System.out.println("1111111111111");
					session.setAttribute("back", "back to claim general screen");
				}*/
				String isWebBoard="false";
				if(request.getAttribute("isWebBoard")!=null)
					isWebBoard=(String) request.getAttribute("isWebBoard");
				String validateFlag=null;
				if(request.getParameter("validateFlag")!=null)
					validateFlag=request.getParameter("validateFlag");
				if("false".equals(validateFlag)|| validateFlag==null){
					validateFlag="false";
				}else{
					validateFlag="true";
				}
				if(("ONL1".equals(preAuthDetailVO.getPreAuthRecvTypeID())||"DHP".equals(preAuthDetailVO.getPreAuthRecvTypeID()) )&& "false".equals(validateFlag) && "false".equals(isWebBoard)){
					session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
//					frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues("frmPreAuthGeneral", preAuthDetailVO, this, mapping, request);
					return mapping.findForward("validatePreAuth");
				}
				
				
//				String validateFlag=null;
//				if(request.getParameter("validateFlag")!=null)
//					validateFlag=request.getParameter("validateFlag");
//				if("false".equals(validateFlag)|| validateFlag==null){
//					validateFlag="false";
//				}else{
//					validateFlag="true";
//				}
//				if("ECL".equals(preAuthDetailVO.getModeOfClaim()) && "false".equals(validateFlag) && "false".equals(isWebBoard)){
//					frmClaimGeneral.initialize(mapping);
//					frmClaimGeneral = (DynaActionForm) FormUtils.setFormValues("frmClaimGeneral", preAuthDetailVO, this, mapping, request);
//					session.setAttribute("frmClaimGeneral", frmClaimGeneral);
//					return mapping.findForward("validatePreAuth");
//				}
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			request.setAttribute("flag", "true");
			session.setAttribute("enhancedCase", preAuthDetailVO.getPreAuthNoStatus());
			session.setAttribute("enhancementtab", "PREAUTHTAB");
			session.setAttribute("preauthStatus", preAuthDetailVO.getPreauthStatus());
		//	session.setAttribute("enhancementFlag", preAuthDetailVO.getPreAuthNoStatus());
			if(preAuthDetailVO.getFlagForCheckEnhancement().equals("R")){
			  request.setAttribute("flagForCheckEnhancement", "Wrong Enhancement Submission: Invalid Invoice/Authorization No.");
			}else{
				request.setAttribute("flagForCheckEnhancement", "");	
			}
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

	
	
	
	public ActionForward  setDateMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction setDateMode");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String encounterTypeId=frmPreAuthGeneral.getString("encounterTypeId");
	        frmPreAuthGeneral.set("encounterTypeId",encounterTypeId);
	        if(memberAgeValidation(frmPreAuthGeneral)){
	        	frmPreAuthGeneral.set("memberwtflag", "Y");
				
				ActionMessages actionMessages = new ActionMessages();
	            ActionMessage actionMessage = new ActionMessage("error.member.patient");
	            actionMessages.add("global.error",actionMessage);
	            saveErrors(request,actionMessages);
	            request.getSession().setAttribute("frmClaimGeneral",frmPreAuthGeneral);
	            return this.getForward("preauthdetail", mapping, request);
			}
			request.getSession().setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
	return mapping.findForward("preauthdetail");
		}// end of try
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "preauthdetail"));
		}// end of catch(Exception exp)

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
		request.setAttribute("isWebBoard", "true");
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
			request.setAttribute("JS_Focus_ID","saveAndCompleteBtnid");
			String preauthStatus = frmPreAuthGeneral.getString("preauthStatus");
			String authNum = frmPreAuthGeneral.getString("authNum");
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			String strIdentifier = "";
			CommunicationManager commManagerObject = this.getCommunicationManagerObject();

			if ("REJ".equals(preauthStatus)) {
				strIdentifier = "PREAUTH_REJECTED";
			} else if ("APR".equals(preauthStatus)) {
				strIdentifier = "PREAUTH_APPROVED";
			}

			// CommunicationOptionVO communicationOptionVO = null;
			Long lngUserID = TTKCommon.getUserSeqId(request);
			String strAuthpdf = TTKPropertiesReader.getPropertyValue("authorizationrptdir") + authNum + ".pdf";
			// String[] strCommArray = {"SMS","EMAIL","FAX"};
			File file = new File(strAuthpdf);

			if (file.exists()) {
				
				commManagerObject.sendAuthorization(new Long(preAuthSeqID).longValue(), strIdentifier,lngUserID);
				
				request.setAttribute("successMsg", "Letter Sent Successfully");
			}// end of if(file.exists())
			else {
				request.setAttribute("errorMsg","Please Generate The Letter Before Sending.");
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
			//frmPreAuthGeneral = setFormValues(preAuthDetailVO,mapping,request);
			frmPreAuthGeneral.set("processType", "GBL");
			frmPreAuthGeneral.set("networkProviderType", "Y");
			frmPreAuthGeneral.set("caption", strCaption.toString());
			frmPreAuthGeneral.set("preAuthNoStatus", "FRESH");
			frmPreAuthGeneral.set("modeFlag", "ENABLE");
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
			
			request.getSession().setAttribute("enhancedCase", "FRESH");
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

	public ActionForward getPatientAgeYearMonthDays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		try {
			log.debug("Inside PreAuthGenealAction getPatientAgeYearMonthDays");
			setLinks(request);
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			request.setAttribute("JS_Focus_ID","dischargeDate");
			HttpSession session = request.getSession();
			PreAuthDetailVO preAuthDetailVO = null;
			
			if(memberAgeValidation(frmPreAuthGeneral)){
				frmPreAuthGeneral.set("memberwtflag", "Y");
				
				ActionMessages actionMessages = new ActionMessages();
	            ActionMessage actionMessage = new ActionMessage("error.member.patient");
	            actionMessages.add("global.error",actionMessage);
	            saveErrors(request,actionMessages);
	    	
	    		request.getSession().setAttribute("frmPreAuthGeneral",
	    				frmPreAuthGeneral);
	    		return this.getForward("preauthdetail", mapping, request);
			}
			else if(memberAgeValidation(frmPreAuthGeneral)==false)
			{
				frmPreAuthGeneral.set("memberwtflag", "N");
			}
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			return this.getForward("preauthdetail", mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "preauthdetail"));
		}// end of catch(Exception exp)
	}// end of getPatientAgeYearMonthDays(ActionMapping mapping,ActionForm form,HttpServletRequest
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
			
			log.debug("Inside PreAuthGenealAction doSave ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			
			PreAuthDetailVO preAuthDetailVO = null;
			String strDetail = "preauthdetail";
			StringBuffer strCaption = new StringBuffer();
			String memberwarnig;
			HttpSession session = request.getSession();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			// Toolbar toolBar =
			// (Toolbar)request.getSession().getAttribute("toolbar");
			if(memberAgeValidation(frmPreAuthGeneral)==true){
				if("".equals(frmPreAuthGeneral.getString("memberWeight")))
				{
					frmPreAuthGeneral.set("memberwtflag", "Y");

					ActionMessages actionMessages = new ActionMessages();
					ActionMessage actionMessage = new ActionMessage("error.member.patient");
					actionMessages.add("global.error",actionMessage);
					saveErrors(request,actionMessages);
					session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
					return this.getForward(strDetail, mapping, request);
				}
			}
			else if(memberAgeValidation(frmPreAuthGeneral)==false)
			{
				frmPreAuthGeneral.set("memberwtflag", "N");
			}
			
			preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(frmPreAuthGeneral, this, mapping, request);
		
		
			String preAuthNo = preAuthDetailVO.getPreAuthNo();
			Long preAuthSeqID = preAuthDetailVO.getPreAuthSeqID();
			preAuthDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			Object[] objArrayResult = new Object[3];
			if (preAuthNo == null || "".equals(preAuthNo)|| preAuthNo.trim().length() < 1 || preAuthSeqID == null|| preAuthSeqID == 0) {
			/*if(preAuthSeqID == null || preAuthSeqID == 0){*/
				
				objArrayResult = preAuthObject.savePreAuth(preAuthDetailVO);
				request.setAttribute("successMsg",
						"Pre-Auth Details Added Successfully");
			}// end of if(preAuthDetailVO.getPreAuthSeqID()!=null)
			else {
				objArrayResult = preAuthObject.savePreAuth(preAuthDetailVO);
				request.setAttribute("successMsg","Pre-Auth Details Updated Successfully");
			}// end of else
			
             session.setAttribute("oralTab",null);
			Long preAuthSeqId = (Long) objArrayResult[0];
			if (preAuthSeqId != null) {
				// call the business layer to get the Pre-Auth detail
				preAuthDetailVO= preAuthObject.getPreAuthDetails(preAuthSeqId);
				
				 memberwarnig=preAuthDetailVO.getMemActiveStatus();
	             session.setAttribute("warningmessage", memberwarnig);
				
				 frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping,request);
		
				
				strCaption.append("Edit");
				strCaption.append(" [ "+ frmPreAuthGeneral.getString("patientName") + " ]");
				strCaption.append(" [ " + frmPreAuthGeneral.getString("memberId") + " ]").append(" [ " + preAuthDetailVO.getPreAuthNoStatus()+ " ] ");
				frmPreAuthGeneral.set("caption", strCaption.toString());

				session.setAttribute("encounterTypes", preAuthObject.getEncounterTypes(frmPreAuthGeneral.getString("benefitType")));
	
			
				if(frmPreAuthGeneral.getString("memberWeight")!=null)
				{
					if(memberAgeValidation(frmPreAuthGeneral))
					frmPreAuthGeneral.set("memberwtflag", "Y");
				}
				
				if(preAuthDetailVO.getPreAuthNoStatus().equals("ENHANCEMENT")){
					frmPreAuthGeneral.set("modeFlagforENHACEMENTMEMERID", "MEMBERIDDISABLE");
					frmPreAuthGeneral.set("cntFlagforENHACEMENTMEMERID", "ENHDIS");
				}
				

				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				session.setAttribute("preauthStatus", preAuthDetailVO.getPreauthStatus());
				session.setAttribute("enhancedCase", preAuthDetailVO.getPreAuthNoStatus());
				session.setAttribute("PreauthSeqId", preAuthDetailVO.getPreAuthSeqID());
				this.addToWebBoard(preAuthDetailVO, request, "PAT");
			}// end of if(preAuthDetailVO.getPreAuthSeqID()!=null)
			return this.getForward(strDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			System.out.println("expTTK::"+expTTK.getMessage());
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
			String benefitType = (String) frmPreAuthGeneral.getString("benefitType");
			String productAuthority = (String) frmPreAuthGeneral.getString("productAuthority");
			String preOneMedicalCondition=request.getParameter("preOneMedicalCondition");
			String primaryAilment = (String) frmPreAuthGeneral.getString("primaryAilment");
			if(("MOH".equals(productAuthority))&&("DAYC".equals(benefitType)||"IPT".equals(benefitType))&&(preOneMedicalCondition==null||"".equals(preOneMedicalCondition))&&"Y".equals(primaryAilment)){
			TTKException expTTK = new TTKException();
			expTTK.setMessage("error.per.one.medical.condition");
			throw expTTK;
			}
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

		//	String primaryAilment = request.getParameter("primaryAilment");
			String diagSeqId = (String) frmPreAuthGeneral.get("diagSeqId");
			String infotype = (String) frmPreAuthGeneral.get("infoType");
			String infoCode = (String) frmPreAuthGeneral.get("infoCode");
			String preCronTypeID = (String) frmPreAuthGeneral.get("preCronTypeID");
			int ailmentDuration =  Integer.parseInt( frmPreAuthGeneral.getString("ailmentDuration"));
			String ailmentDurationFlag = (String) frmPreAuthGeneral.get("ailmentDurationFlag");
			String relPresentIllness = (String) frmPreAuthGeneral.get("relPresentIllness");
			String relevantFindings = (String) frmPreAuthGeneral.get("relevantFindings");
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
			diagnosisDetailsVO.setInfoType(infotype);
			diagnosisDetailsVO.setInfoCode(infoCode);
			diagnosisDetailsVO.setDurAilment(ailmentDuration);
			diagnosisDetailsVO.setDurationFlag(ailmentDurationFlag);
			diagnosisDetailsVO.setRelPresentIllness(relPresentIllness);
			diagnosisDetailsVO.setRelevantFindings(relevantFindings);
			
			diagnosisDetailsVO.setPreCronTypeID(preCronTypeID);
			successMsg = (diagSeqId == null || diagSeqId.length() < 1) ? "Diagnosis Details Added Successfully"
					: "Diagnosis Details Updated Successfully";
			diagnosisDetailsVO.setPreOneMedicalCondition(preOneMedicalCondition);
			preAuthObject.saveDiagnosisDetails(diagnosisDetailsVO);
			
			preAuthDetailVO = preAuthObject.getPreAuthDetails(diagnosisDetailsVO.getPreAuthSeqID());

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping,request);

			frmPreAuthGeneral.set("icdCode", "");
			frmPreAuthGeneral.set("icdCodeSeqId", "");
			frmPreAuthGeneral.set("ailmentDescription", "");
			frmPreAuthGeneral.set("primaryAilment", "");
			frmPreAuthGeneral.set("diagSeqId", "");
			session.setAttribute("encounterTypes", preAuthObject.getEncounterTypes(frmPreAuthGeneral.getString("benefitType")));
	
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
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

			diagnosisDetailsVO = ((ArrayList<DiagnosisDetailsVO>)frmPreAuthGeneral.get("diagnosisList")).get(new Integer(request.getParameter("rownum")).intValue());
			diagnosisDetailsVO.setAuthType("PAT");
			diagnosisDetailsVO.setPreAuthSeqID(new Long(preAuthSeqID));
			diagnosisDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));

			preAuthObject.deleteDiagnosisDetails(diagnosisDetailsVO);
			request.setAttribute("successMsg","Diagnosis Details Deleted Successfully");

			preAuthDetailVO = preAuthObject.getPreAuthDetails(diagnosisDetailsVO.getPreAuthSeqID());

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
			
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
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
			
			frmPreAuthGeneral =setFormValues(preAuthDetailVO, mapping, request);
			
	
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
			request.getSession().setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
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
			
			DiagnosisDetailsVO diagnosisDetailsVO2 = ((ArrayList<DiagnosisDetailsVO>) frmPreAuthGeneral.get("diagnosisList")).get(new Integer(request.getParameter("rownum")).intValue());

			frmPreAuthGeneral.set("primaryAilment",diagnosisDetailsVO2.getPrimaryAilment());
			frmPreAuthGeneral.set("ailmentDescription",diagnosisDetailsVO2.getAilmentDescription());
			frmPreAuthGeneral.set("icdCode", diagnosisDetailsVO2.getIcdCode());
			frmPreAuthGeneral.set("icdCodeSeqId", diagnosisDetailsVO2.getIcdCodeSeqId().toString());
			frmPreAuthGeneral.set("diagSeqId", diagnosisDetailsVO2.getDiagSeqId().toString());
			frmPreAuthGeneral.set("infoCode",diagnosisDetailsVO2.getInfoCode().toString());
            frmPreAuthGeneral.set("infoType",diagnosisDetailsVO2.getInfoType().toString());
            frmPreAuthGeneral.set("preCronTypeYN",diagnosisDetailsVO2.getPreCronTypeYN());
            frmPreAuthGeneral.set("preCronTypeID",diagnosisDetailsVO2.getPreCronTypeID());
            frmPreAuthGeneral.set("preOneMedicalCondition",diagnosisDetailsVO2.getPreOneMedicalCondition());
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			request.setAttribute("JS_Focus_ID","actImageID");
		
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
						"Please Enter And Save Preauth Details");
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

	public ActionForward selectActivityCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction selectActivityCode ");
			HttpSession session = request.getSession();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String codeFlag = request.getParameter("codeFlag");
			if ("remember".equalsIgnoreCase(codeFlag)) {// executing when
														// entered activity code
														// and onblure event
				DynaActionForm frmActivityDetails = (DynaActionForm) form;
				String activityStartDate = (String) frmActivityDetails.get("activityStartDate");
				String preAuthSeqID = (String) frmActivityDetails.get("preAuthSeqID");
				String preAuthNo = (String) frmActivityDetails.get("preAuthNo");
				String activityCode = (String) frmActivityDetails.get("activityCode");
				String activityDtlSeqId = frmActivityDetails.getString("activityDtlSeqId");
				String clinicianId = frmActivityDetails.getString("clinicianId");
				String clinicianName = frmActivityDetails.getString("clinicianName");
				String authType = frmActivityDetails.getString("authType");
				String overrideYN = frmActivityDetails.getString("overrideYN");
				String networkProviderType = frmActivityDetails.getString("networkProviderType");
				String activityServiceType = frmActivityDetails.getString("activityServiceType");
				String activtyId = frmActivityDetails.getString("activityId");
				String sCategory = frmActivityDetails.getString("sCategory");
				String condDenialCode = frmActivityDetails.getString("condDenialCode");
				String maternityStatus = frmActivityDetails.getString("hmaternityStatus");
				String quantity = frmActivityDetails.getString("quantity");
				String payerAuthority = frmActivityDetails.getString("payerAuthority");
				String providerRequestedAmt = frmActivityDetails.getString("providerRequestedAmt");

				ArrayList<Object> denialDesc = null;
				ArrayList<CacheObject> denailDescription = null;
			
				denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
				denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
				
				ActivityDetailsVO activityDetailsVO = preAuthObject
						.getActivityCodeTariff(sCategory,activityCode, preAuthSeqID,
								activityStartDate, authType,overrideYN,condDenialCode);
				String activityTariffStatus = activityDetailsVO
						.getActivityTariffStatus();
				sCategory=activityDetailsVO.getsCategory();
				if (!(activityTariffStatus == null || activityTariffStatus
						.trim().length() < 1)) {
					request.setAttribute("activityTariffStatus",
							activityTariffStatus);
				}

				if ("VALID".equals(activityDetailsVO.getDisplayMsg()))
					request.setAttribute("successMsg", "Activity Code Valid");
				else
					request.setAttribute("errorMsg", "Activity Code Not Valid");
				
				
				frmActivityDetails.initialize(mapping);
				
				DynaActionForm frmPreAuthGeneral = (DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
				
				String preAuthRecvTypeID = frmPreAuthGeneral.getString("preAuthRecvTypeID");
				
				frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
						"frmActivityDetails", activityDetailsVO, this, mapping,
						request);
				frmActivityDetails.set("activityCode", activityCode);
				frmActivityDetails.set("amountAllowed", "Y");
				frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
				frmActivityDetails.set("activityStartDate", activityStartDate);
				frmActivityDetails.set("preAuthNo", preAuthNo);
				frmActivityDetails.set("clinicianId", clinicianId);
				frmActivityDetails.set("clinicianName", clinicianName);
				frmActivityDetails.set("activityDtlSeqId", activityDtlSeqId);
				frmActivityDetails.set("overrideYN", overrideYN);
				frmActivityDetails.set("networkProviderType",networkProviderType);
				frmActivityDetails.set("activityServiceType",activityServiceType);
				frmActivityDetails.set("activityId", activtyId);
				frmActivityDetails.set("sCategory", sCategory);
				String grossAmt = "";
				frmActivityDetails.set("hmaternityStatus", maternityStatus);
				
				Float unitPrice=(float) (activityDetailsVO.getUnitPrice()==null?0.0:activityDetailsVO.getUnitPrice().floatValue());
				Float unitDiscount=(float) (activityDetailsVO.getDiscountPerUnit()==null?0.0:activityDetailsVO.getDiscountPerUnit().floatValue());
				float grossAmount= 0;
				float discountAmt= 0;
				float discountedGrossAmt= 0;
				System.out.println("-------------------- calc start -------------------------");
				System.out.println("1 : unitPrice 	 (from db)		= "+ unitPrice);
				System.out.println("2 : unitDiscount (from db) 		= "+ unitDiscount);
				System.out.println("3 : quantity 	 (from form)	= "+ quantity);
				System.out.println("4 : provReqAmt 	 (from form)	= "+ providerRequestedAmt);
				
				String  qt="";
				if("DHP".equals(preAuthRecvTypeID) || "ONL1".equals(preAuthRecvTypeID))
				{
					frmActivityDetails.set("quantity", quantity);
					if("".equals(quantity) || quantity == null)
						qt = "0";	
					else
						qt = quantity ;
				
					float qty=Float.parseFloat(qt); 
					grossAmount = qty * unitPrice;
					discountAmt = qty * unitDiscount;
					discountedGrossAmt = grossAmount - discountAmt;
					
					String GrossAmt =String.valueOf(grossAmount);
					String discount =String.valueOf(discountAmt);
					String discountedGross =String.valueOf(discountedGrossAmt);
					
					frmActivityDetails.set("grossAmount",GrossAmt);
					if("0.0".equalsIgnoreCase(discount))
						frmActivityDetails.set("discount","");
					else
						frmActivityDetails.set("discount",discount);	
					frmActivityDetails.set("discountedGross",discountedGross);
					frmActivityDetails.set("netAmount",discountedGross);
					if(providerRequestedAmt == null || "".equals(providerRequestedAmt))
						frmActivityDetails.set("providerRequestedAmt","");
					else
						frmActivityDetails.set("providerRequestedAmt",providerRequestedAmt);	
				}
				System.out.println("-------------------- calc End -------------------------");
				frmActivityDetails.set("payerAuthority", payerAuthority);
				frmActivityDetails.set("denialCodeList",denailDescription);	
				session.setAttribute("frmActivityDetails", frmActivityDetails);
			} else if ("search".equals(codeFlag)) {// executing when searching
													// activity code selected
				DynaActionForm frmActivityDetails = (DynaActionForm) session
						.getAttribute("frmActivityDetails");

				String activityStartDate = (String) frmActivityDetails
						.get("activityStartDate");
				String preAuthSeqID = (String) frmActivityDetails
						.get("preAuthSeqID");
				String preAuthNo = (String) frmActivityDetails.get("preAuthNo");
				String activityDtlSeqId = frmActivityDetails
						.getString("activityDtlSeqId");
				String clinicianId = frmActivityDetails
						.getString("clinicianId");
				String clinicianName = frmActivityDetails
						.getString("clinicianName");
				String authType = frmActivityDetails.getString("authType");
				String overrideYN = frmActivityDetails.getString("overrideYN");
				String networkProviderType = frmActivityDetails.getString("networkProviderType");
				String activityServiceType = frmActivityDetails.getString("activityServiceType");
				String activtyId = frmActivityDetails.getString("activityId");
				String sCategory = frmActivityDetails.getString("sCategory");
				
				String maternityStatus = frmActivityDetails.getString("hmaternityStatus");
				String condDenialCode = frmActivityDetails.getString("condDenialCode");
				String payerAuthority = frmActivityDetails.getString("payerAuthority");
				
				TableData activityCodeListData = (TableData) session
						.getAttribute("activityCodeListData");
				ActivityDetailsVO activityDetailsVO = (ActivityDetailsVO) activityCodeListData
						.getRowInfo(Integer.parseInt((String) request
								.getAttribute("rownum")));

				String activityCode = activityDetailsVO.getActivityCode();
				sCategory=activityDetailsVO.getsCategory();
				activityDetailsVO = preAuthObject.getActivityCodeTariff(sCategory,activityCode, preAuthSeqID,activityStartDate, authType,overrideYN,condDenialCode);
				
				String activityTariffStatus = activityDetailsVO.getActivityTariffStatus();
				if (!(activityTariffStatus == null || activityTariffStatus.trim().length() < 1)) {
					request.setAttribute("activityTariffStatus",activityTariffStatus);
				}
				
				ArrayList<Object> denialDesc = null;
				ArrayList<CacheObject> denailDescription = null;
			
				
				denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
				denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
				 
				frmActivityDetails.initialize(mapping);

				frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
						"frmActivityDetails", activityDetailsVO, this, mapping,
						request);
				frmActivityDetails.set("activityCode", activityCode);
				frmActivityDetails.set("amountAllowed", "Y");
				frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
				frmActivityDetails.set("activityStartDate", activityStartDate);
				frmActivityDetails.set("preAuthNo", preAuthNo);
				frmActivityDetails.set("clinicianId", clinicianId);
				frmActivityDetails.set("clinicianName", clinicianName);
				frmActivityDetails.set("activityDtlSeqId", activityDtlSeqId);
				frmActivityDetails.set("overrideYN", overrideYN);
				frmActivityDetails.set("networkProviderType",networkProviderType);
				frmActivityDetails.set("activityServiceType",activityServiceType);
				frmActivityDetails.set("activityId", activtyId);
				frmActivityDetails.set("sCategory", sCategory);
				frmActivityDetails.set("denialCodeList",denailDescription);	
				frmActivityDetails.set("hmaternityStatus", maternityStatus);
				frmActivityDetails.set("payerAuthority", payerAuthority);
				session.setAttribute("frmActivityDetails", frmActivityDetails);
			}

			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivitydetails));
		}// end of catch(Exception exp)
	}// end of selectActivityCode(ActionMapping mapping,ActionForm
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
			TableData diagnosisCodeListData = (TableData) session.getAttribute("diagnosisCodeListData");

			if (!(TTKCommon.checkNull(request.getParameter("rownum")).equals(""))) {
				DiagnosisDetailsVO diagnosisDetailsVO = (DiagnosisDetailsVO) diagnosisCodeListData.getRowInfo(Integer.parseInt((String) request.getParameter("rownum")));

				DynaActionForm frmPreAuthGeneral = (DynaActionForm) session.getAttribute("frmPreAuthGeneral");
				request.setAttribute("JS_Focus_ID","addDiagnosisBtn");
				String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
				String diagSeqId = frmPreAuthGeneral.getString("diagSeqId");

				DiagnosisDetailsVO diagnosisDetailsVO2 = preAuthObject.getIcdCodeDetails(diagnosisDetailsVO.getIcdCode(),new Long(preAuthSeqID), "PAT");
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
				frmPreAuthGeneral.set("preCronTypeYN",
						diagnosisDetailsVO2.getPreCronTypeYN());
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
			//	 TTKException expTTK = new TTKException();
			//	 expTTK.setMessage("error.PreAuthorization.required");
				session.setAttribute("preauthDiagnosis", null);
				session.setAttribute("preauthActivities", null);
				session.setAttribute("preauthShortfalls", null);

				DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
				frmPreAuthGeneral.initialize(mapping);

				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				request.setAttribute("errorMsg",
						"Please Enter And Save Preauth Details");
				return this.getForward(strPreAuthDetail, mapping, request);
			 //   throw expTTK;
			}// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			frmPreAuthGeneral.initialize(mapping);
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			StringBuffer strCaption = new StringBuffer();

			PreAuthDetailVO preAuthDetailVO = preAuthObject.getPreAuthDetails(preAuthSeqID);

			preAuthDetailVO.setParentPreAuthSeqID(preAuthSeqID);
			preAuthDetailVO.setPreAuthSeqID(null);
		//	preAuthDetailVO.setPreAuthNoStatus("ENHANCEMENT");
			preAuthDetailVO.setEnhancedYN("ENH");

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
          
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
			
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
			
			
				frmPreAuthGeneral.set("modeFlagforENHACEMENTMEMERID", "MEMBERIDDISABLE");
				frmPreAuthGeneral.set("cntFlagforENHACEMENTMEMERID", "ENHDIS");
			
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			session.setAttribute("enhancedCase",   "ENHANCEMENT");
			session.setAttribute("enhancementtab", "ENHANCEMENTTAB");
			frmPreAuthGeneral.set("preAuthNoStatus", "ENHANCEMENT");
			session.setAttribute("preauthStatus", preAuthDetailVO.getPreauthStatus());
		//	session.setAttribute("enhancementFlag", "ENHANCEMENT");
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
			String preAuthSeqID = (String) frmPreAuthGeneral.get("preAuthSeqID");
			String authType = frmPreAuthGeneral.getString("authType");
			String activityDtlSeqId = (String) frmPreAuthGeneral.get("activityDtlSeqId");

			
			preAuthObject.deleteActivityDetails(new Long(preAuthSeqID).longValue(), new Long(activityDtlSeqId).longValue(),authType);
			request.setAttribute("successMsg","Activity Details Deleted Successfully");

			PreAuthDetailVO preAuthDetailVO = preAuthObject.getPreAuthDetails(new Long(preAuthSeqID));


			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);

		
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
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
			String preAuthSeqID = (String) frmPreAuthGeneral.get("preAuthSeqID");
			String hospitalSeqID = (String) frmPreAuthGeneral.get("providerSeqId");
			
			hospitalSeqID = hospitalSeqID == null || hospitalSeqID.length() < 1 ? "0": hospitalSeqID;
			StringBuffer strCaption = new StringBuffer();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

		    preAuthObject.getCalculatedPreauthAmount(new Long(preAuthSeqID),new Long(hospitalSeqID),TTKCommon.getUserSeqId(request));

			PreAuthDetailVO preAuthDetailVO = preAuthObject.getPreAuthDetails(new Long(preAuthSeqID));

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
			strCaption.append(" Edit");
			strCaption.append(" [ "
					+ frmPreAuthGeneral.getString("patientName") + " ]");
			strCaption.append(" [ " + frmPreAuthGeneral.getString("memberId")
					+ " ]");
			
			frmPreAuthGeneral.set("caption", strCaption.toString());
			request.setAttribute("successMsg", "Record updated successfully!");
			
				
			if(("DHP".equals(preAuthDetailVO.getPreAuthRecvTypeID()))&&((preAuthDetailVO.getCount_flag())>1)){
				
				request.setAttribute("errorMsg","Invalid Member Id: More than one member found for the searched Member Id. Please select any one member id from the search results.");
				frmPreAuthGeneral.set("memberSeqID","");
				//frmPreAuthGeneral.set("memberId","");
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
			}
			
					
				else if(("DHP".equals(preAuthDetailVO.getPreAuthRecvTypeID())) && preAuthDetailVO.getPreAuthNo()!=null && preAuthDetailVO.getPreAuthNo()!="" && preAuthDetailVO.getCount_flag()==0)
				{
					
					if(preAuthDetailVO.getCount_flag2()==1)
						
					{
						
						frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues("frmPreAuthGeneral", preAuthDetailVO, this, mapping,
								request);
					}
					if(preAuthDetailVO.getCount_flag2()==0)
					{
						
						request.setAttribute("errorMsg", "Invalid Member Id/Emirates Id: Member Id/Emirates Id  not found.");
						frmPreAuthGeneral.set("memberSeqID","");
						
						/*frmPreAuthGeneral.set("memberId","");*/
						frmPreAuthGeneral.set("patientName", "");
						frmPreAuthGeneral.set("memberAge","");
						/*frmPreAuthGeneral.set("emirateId", "");*/
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
						
					}
					if(preAuthDetailVO.getCount_flag2()>1)
					{
						
						request.setAttribute("errorMsg", "Invalid Emirates Id: More than one member found for the searched Emirates Id. Please select any one Emirates id from the search results");
						frmPreAuthGeneral.set("memberSeqID","");
						
						frmPreAuthGeneral.set("memberId","");
						frmPreAuthGeneral.set("patientName", "");
						frmPreAuthGeneral.set("memberAge","");
						/*frmPreAuthGeneral.set("emirateId", "");*/
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
						
					}
					
				}
				else if(preAuthDetailVO.getPreAuthNo()!=null && preAuthDetailVO.getPreAuthNo()!="" && preAuthDetailVO.getCount_flag()==0){
					
					
					request.setAttribute("errorMsg", "Invalid Member ID: Member ID not found.");
					frmPreAuthGeneral.set("memberSeqID","");
					
					//frmPreAuthGeneral.set("memberId","");
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
				}

	
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
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
	}// end of calculatePreauthAmount(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward saveAndCompletePreauth(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			
			setLinks(request);
			log.debug("Inside PreAuthGenealAction saveAndCompletePreauth");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			request.setAttribute("JS_Focus_ID","saveAndCompleteBtnid");
			String preauthStatus="";
			String memberwarnig="";
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
		
			request.setAttribute("successMsg","Preauth completed successfully!");
			  if("DHP".equals(preAuthDetailVO.getPreAuthRecvTypeID())&&
						!"Y".equals(preAuthDetailVO.getDhpoUploadStatus())&&
						("APR".equals(preAuthDetailVO.getPreauthStatus())||"REJ".equals(preAuthDetailVO.getPreauthStatus()))
								
					){
				//calling dhpo preauth upload details
					dhpoPreauthUpload(frmPreAuthGeneral.getString("preAuthSeqID"),request);			
						}
			
				
			PreAuthDetailVO preAuthDetailVO2 = preAuthObject.getPreAuthDetails(new Long(frmPreAuthGeneral.getString("preAuthSeqID")));
			
			
			frmPreAuthGeneral = setFormValues(preAuthDetailVO2, mapping, request);
			strCaption.append(" Edit");
			strCaption.append(" [ "	+ frmPreAuthGeneral.getString("patientName") + " ]");
			strCaption.append(" [ " + frmPreAuthGeneral.getString("memberId")+ " ]");
			frmPreAuthGeneral.set("caption", strCaption.toString());
	
			if(preAuthDetailVO2.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
			
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
			String payerAuthority =  (String) request.getAttribute("payerAuthority");
			
			ArrayList<Object> denialDesc = null;
			ArrayList<CacheObject> denailDescription = null;
			
			denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
			denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
			
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
			String getCondDenialCode = activityDetailsVO.getCondDenialCode();
			request.getSession().setAttribute("activityDenialList",
					activityDenialList);
			frmActivityDetails.set("denialCodeList",denailDescription);	
			frmActivityDetails.set("payerAuthority", payerAuthority);
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
	
	
	
	public ActionForward overridPreAuthDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGeneralAction overridPreAuthDetails");

			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			request.setAttribute("JS_Focus_ID","saveAndCompleteBtnid");
			String overrideRemarks = frmPreAuthGeneral.getString("overrideRemarks");
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			StringBuffer strCaption = new StringBuffer();
			long rowUpd = preAuthObject.overridPreAuthDetails(preAuthSeqID,overrideRemarks, TTKCommon.getUserSeqId(request));

			if (rowUpd >= 1)
				request.setAttribute("successMsg","PreAuth Details Override Successfully");

			PreAuthDetailVO preAuthDetailVO2 = preAuthObject.getPreAuthDetails(new Long(frmPreAuthGeneral.getString("preAuthSeqID")));
			
		
			frmPreAuthGeneral = setFormValues(preAuthDetailVO2, mapping, request);
			strCaption.append(" Edit");
			strCaption.append(" [ "	+ frmPreAuthGeneral.getString("patientName") + " ]");
			strCaption.append(" [ " + frmPreAuthGeneral.getString("memberId")+ " ]");
			frmPreAuthGeneral.set("caption", strCaption.toString());
			frmPreAuthGeneral.set("overrideRemarks", overrideRemarks);
	
			if(preAuthDetailVO2.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
			
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
	}// end of doOverride(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)



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
			
			String maternityStatus =  (String) request.getAttribute("hmaternityStatus");
			String payerAuthority =  (String) request.getAttribute("payerAuthority");
			
			ArrayList<Object> denialDesc = null;
			ArrayList<CacheObject> denailDescription = null;
			
			denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
			denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
		
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

			 frmActivityDetails.set("denialCodeList",denailDescription);
			 
			frmActivityDetails.set("hmaternityStatus", maternityStatus);
			frmActivityDetails.set("networkProviderType", networkProviderType);
			frmActivityDetails.set("payerAuthority", payerAuthority);
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

			activityDetailsVO = (ActivityDetailsVO) FormUtils.getFormValues(frmActivityDetails, "frmActivityDetails", this, mapping,request);
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
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String preAuthNo = frmPreAuthGeneral.getString("preAuthNo");
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			String admissionDate = frmPreAuthGeneral.getString("admissionDate");
			
			String clinicianId = frmPreAuthGeneral.getString("clinicianId");
			String clinicianName = frmPreAuthGeneral.getString("clinicianName");
			String networkProviderType = frmPreAuthGeneral.getString("networkProviderType");
			
			String encounterTypeId = frmPreAuthGeneral.getString("encounterTypeId");
			String benefitType = frmPreAuthGeneral.getString("benefitType");
			String payerAuthority = frmPreAuthGeneral.getString("payerAuthority");
		
			ArrayList<Object> denialDesc = null;
			ArrayList<CacheObject> denailDescription = null;
			
			denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
			denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
			 
			String maternityStatus="";
			
			if("MTI".equals(benefitType)){
				
				if("3".equals(encounterTypeId)||"4".equals(encounterTypeId)){
					
					maternityStatus="IPTMTI";
					
				}
			}
			
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
			frmActivityDetails.set("hmaternityStatus", maternityStatus);	
			session.setAttribute("hmaternityStatus", maternityStatus);												
			session.setAttribute("dateStatus", dateStatus);
			frmActivityDetails.set("denialCodeList",denailDescription);
			frmActivityDetails.set("payerAuthority", payerAuthority);
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
			
			String hmaternityStatus = frmActivityDetails.getString("hmaternityStatus");
			
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
			frmActivityDetails.set("hmaternityStatus", hmaternityStatus);
			
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
			String modeType = (String) request.getParameter("modeType");			
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			frmObservDetails.initialize(mapping);
			frmObservDetails.set("activityDtlSeqId", activityDtlSeqId);
			frmObservDetails.set("authType", authType);
			frmObservDetails.set("preAuthRecvTypeID", modeType);
			if ("PAT".equals(authType))
				frmObservDetails.set("preAuthSeqID", authSeqID);
			else if ("CLM".equals(authType))
				frmObservDetails.set("claimSeqID", authSeqID);
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ArrayList<String[]> observations = preAuthObject
					.getAllObservDetails(new Long(activityDtlSeqId));
			
			request.getSession().setAttribute("observations", observations);
			
			if("ECL".equals(modeType) || "DHP".equals(modeType) ||"ONL1".equalsIgnoreCase(modeType))
				frmObservDetails.set("modeFlag", "DISABLE");
			else
				frmObservDetails.set("modeFlag", "ENABLE");
			
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
			String modeFlag = frmObservDetails.getString("modeFlag");
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
			frmObservDetails.set("modeFlag", modeFlag);
			request.setAttribute("successMsg",
					"Observation Details Deleted Successfully.");
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

	// Preauth : save Observations Details 
	public ActionForward saveObserDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);

			log.debug("Inside PreAuthGenealAction addObserv ");
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			ObservationDetailsVO observationDetailsVO = new ObservationDetailsVO();
			
			// setting form value to vo
			String successMsg;
			observationDetailsVO = (ObservationDetailsVO) FormUtils.getFormValues(frmObservDetails, this, mapping, request);
			
		/*	String strobservType = observationDetailsVO.getObservType();
			FormFile formFile = null;
			formFile = (FormFile)frmObservDetails.get("file");*/
			Matcher matcher =null;
			Pattern pattern =null;
		    PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			if (observationDetailsVO.getObservSeqId() == null
					|| observationDetailsVO.getObservSeqId() == 0)
				successMsg = "Observation Details Added Successfully.";
			else
				successMsg = "Observation Details Updated Successfully.";
			observationDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
			String activeLink = TTKCommon.getActiveLink(request);												
			if("Pre-Authorization".equals(activeLink))
			{	
				if("FIL".equals(frmObservDetails.getString("observType")))
				{		
				FormFile formFile = (FormFile)frmObservDetails.get("fileName");		// file 
				pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
				matcher = pattern.matcher( formFile.getFileName() );
				if(matcher.find())
				{	
					 ActionMessages actionMessages = new ActionMessages();
	                 ActionMessage actionMessage = new ActionMessage("error.filetype.upload.format");
	                 actionMessages.add("global.error",actionMessage);
	                 saveErrors(request,actionMessages);
	                 return mapping.findForward("saveObservDetails");
				}
				
				String filename = formFile.toString();
				observationDetailsVO.setObservValue(filename);
				
				InputStream inputStream = formFile.getInputStream(); 		
				int formFileSize	=	formFile.getFileSize();				
			    if(formFileSize==0)
			    {
				   	 ActionMessages actionMessages = new ActionMessages();
		             ActionMessage actionMessage = new ActionMessage("error.formfilesize");			
		             actionMessages.add("global.error",actionMessage);
		             saveErrors(request,actionMessages);
		             return mapping.findForward("saveObservDetails");
			    }
			    else
				    {
			            //COPYNG FILE TO SERVER FOR BACKUP
			            FileOutputStream outputStream = null;
			            String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("ObservationFileUpload"));	
			            File folder = new File(path);
			            if(!folder.exists()){
			                  folder.mkdirs();
			            }
			            //String finalPath=(path+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+"_"+formFile);
			            String timeStamps	=	new SimpleDateFormat("dd-MM-yyyy-HH-mm-SSS").format(new Date());
			            String fileExc[]=formFile.getFileName().split("[.]");
			            String oFileName=formFile.getFileName().split("."+fileExc[fileExc.length-1])[0];
			            String filetypesName = fileExc[1];
			            if("mp4".equals(filetypesName) || "exe".equals(filetypesName) || "mp3".equals(filetypesName) || "wmv".equals(filetypesName))
			            	{
			            	 ActionMessages actionMessages = new ActionMessages();
			                 ActionMessage actionMessage = new ActionMessage("error.filetype.mp3.mp4.exe");
			                 actionMessages.add("global.error",actionMessage);
			                 saveErrors(request,actionMessages);
			                 return mapping.findForward("saveObservDetails");
			            	}
		            else
		            {
		            String fileName2=oFileName+"-"+timeStamps+"."+fileExc[fileExc.length-1];
		            String origFileName	=	formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
		            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf("."));
		            String finalPath=(path+formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
		            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf(".")));
		            
			            if(oFileName.length()>100)
			            {
			            	 ActionMessages actionMessages = new ActionMessages();
			                 ActionMessage actionMessage = new ActionMessage("error.fileNamePath.PolicyDOCUploads");
			                 actionMessages.add("global.error",actionMessage);
			                 saveErrors(request,actionMessages);
			                 return mapping.findForward("saveObservDetails");
			            }
		            else
			            {
		            	  observationDetailsVO.setObservValue(fileName2);
		            	  request.getSession().setAttribute("origFileName", origFileName);
		            	  outputStream = new FileOutputStream(new File(path+fileName2));
		
		            	  outputStream.write(formFile.getFileData());		//Uploaded file backUp
			            // E N D S FETCH FILE FROM LOCAL SYSTEM
						if(inputStream!=null)
							inputStream.close();
						if(outputStream!=null)
							outputStream.close();
			            }
		            }   
				  }     
			}
		}	    
			Object results[] = preAuthObject.saveObservationDetails(observationDetailsVO);
			request.getSession().setAttribute("observations", results[2]);
			request.setAttribute("successMsg", successMsg);

			// getting form values & setting back
			String activityDtlSeqId = frmObservDetails.getString("activityDtlSeqId");
			String authType = frmObservDetails.getString("authType");
			String preAuthSeqID = frmObservDetails.getString("preAuthSeqID");
			String claimSeqID = frmObservDetails.getString("claimSeqID");
			String modeFlag = frmObservDetails.getString("modeFlag");
			frmObservDetails.initialize(mapping);
			frmObservDetails.set("activityDtlSeqId", activityDtlSeqId);
			frmObservDetails.set("preAuthSeqID", preAuthSeqID);
			frmObservDetails.set("claimSeqID", claimSeqID);
			frmObservDetails.set("authType", authType);
			frmObservDetails.set("modeFlag", modeFlag);
			request.getSession().setAttribute("frmObservDetails",
					frmObservDetails);
			request.getSession().removeAttribute("observCodes");
			request.getSession().removeAttribute("observValueTypes");
			return mapping.findForward("saveObservDetails");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strPreAuthDetail));
		}// end of catch(Exception exp)

	}

	public ActionForward viewObservsHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction viewObservsHistory ");
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			String activityDtlSeqId = (String) request
					.getParameter("activityDtlSeqId");
			String preAuthRecvTypeID = (String) request.getParameter("preAuthRecvTypeID");
			String authSeqID = (String) request.getParameter("authSeqID");
			
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ArrayList<String[]> observations = preAuthObject
					.getAllObservDetails(new Long(activityDtlSeqId));
			request.getSession().setAttribute("observations",
					observations);
			request.setAttribute("preAuthRecvTypeID",preAuthRecvTypeID);
			request.setAttribute("authSeqID",authSeqID);
			request.setAttribute("activityDtlSeqId",activityDtlSeqId);
			
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
			frmPreAuthGeneral.set("gravida", "");
			frmPreAuthGeneral.set("para", "");
			frmPreAuthGeneral.set("live", "");
			frmPreAuthGeneral.set("abortion", "");
			String benefitTypes = frmPreAuthGeneral.getString("benefitType");
			  if(benefitTypes.equals("IPT") || benefitTypes.equals("MTI"))
			  {
				  frmPreAuthGeneral.set("encounterTypeId", "3");
			  }
			  else
			  {
				  
				  
				  frmPreAuthGeneral.set("encounterTypeId", "");
				  frmPreAuthGeneral.set("memberWeight", "");
			    	frmPreAuthGeneral.set("memberwtflag", "N");
			  }
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
			request.setAttribute("JS_Focus_ID","addDiagnosisBtn");
			String path = "Pre-Authorization.Processing.System Preauth Approval";
			HttpSession session = request.getSession();
			String reforward = request.getParameter("reforward");
		
			
			
			if ("providerSearch".equalsIgnoreCase(reforward)) {
				
				
				String  sourceType = frmPreAuthGeneral.getString("preAuthRecvTypeID");
				
				TableData providerListData = new TableData(); // create new
																// table data
																// object
				
				if("DHP".equals(sourceType) || "ONL1".equals(sourceType)){
					
					providerListData.createTableInfo("ProviderListTable", new ArrayList());
				}
				
				else{
					
					providerListData.createTableInfo("ProviderListNewTable", new ArrayList());
					
				}
				
				session.setAttribute("providerListData", providerListData);// create
																			// the
																			// required
																			// grid
																			// table
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				path = "providerSearchList";
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
			else if ("memberSearch".equalsIgnoreCase(reforward)) {
				String strflaghistory="";
				strflaghistory = request.getParameter("flaghistory");
				String memberId = frmPreAuthGeneral.getString("memberId");
				
				TableData memberListData = new TableData(); // create new
																// table data
																// object
				memberListData.createTableInfo("MemberListTable",
						new ArrayList());
				session.setAttribute("memberListData", memberListData);// create
				
				if("Y".equals(strflaghistory))
				{
					request.getSession().removeAttribute("ListmemberDetailVO");
				}
																			// the
				request.getSession().removeAttribute("benifitTypeVal");															// required
																			// grid
																			// table
				frmPreAuthGeneral.set("sEnrollmentId", memberId);
				session.setAttribute("frmPreAuthList", frmPreAuthGeneral);
				path = "memberSearchList";
				
				return mapping.findForward(path);
			}
			
			
			
			
			
			
			
			else if ("clinicianSearch".equalsIgnoreCase(reforward)) {
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
				HashMap<String, String> networkTypes = new HashMap<>();// preAuthObject.getNetworkTypeList();
				networkTypes.put("test", "test");
				session.setAttribute("networkTypes", networkTypes);
				path = "activitySearchList";
				return mapping.findForward(path);
			} else if ("viewActivityDetails".equals(reforward)) {
				
				String encounterTypeId = frmPreAuthGeneral.getString("encounterTypeId");
				String benefitType = frmPreAuthGeneral.getString("benefitType");
				
				String maternityStatus="";
				
				if("MTI".equals(benefitType)){
					
					if("3".equals(encounterTypeId)||"4".equals(encounterTypeId)){
						
						maternityStatus="IPTMTI";
						
					}
				}
				
				request.setAttribute("hmaternityStatus",maternityStatus);
				
				request.setAttribute("activityDtlSeqId",
						frmPreAuthGeneral.getString("activityDtlSeqId"));
				request.setAttribute("networkProviderType",
						frmPreAuthGeneral.getString("networkProviderType"));
				request.setAttribute("payerAuthority",frmPreAuthGeneral.getString("payerAuthority"));
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
				request.setAttribute("payerAuthority",frmPreAuthGeneral.getString("payerAuthority"));
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
				request.setAttribute("JS_Focus_ID","calculatepreauthbtnid");
				String preAuthSeqID = dynForm.getString("preAuthSeqID");
				request.setAttribute("preAuthSeqID", preAuthSeqID);
				request.setAttribute("invoked", null);
				if("System Preauth Approval".equals(TTKCommon.getActiveTab(request))){
				path = "Pre-Authorization.Processing.System Preauth Approval.GetAllPreAuthDetails";
				}else 	if("Oral Preauth Approval".equals(TTKCommon.getActiveTab(request))){
					request.setAttribute("onPolicyDocClose", "Y");
					path = "Pre-Authorization.Processing.Oral Preauth Approval";
					}
			} else if ("closeHistoryDetails".equalsIgnoreCase(reforward)) {
				path = "preauthHistoryList";
				return mapping.findForward(path);
			}
			else if ("viewBenefitDetails".equalsIgnoreCase(reforward)) {
				session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
				path = "Preauth.Processing.General.BenefitDetails";
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
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			// frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
			// String preAuthNo=(String)request.getAttribute("preAuthNo");
			String objPreAuthSeqID = TTKCommon.checkNull(request.getAttribute("preAuthSeqID")).toString();
			if("".equals(objPreAuthSeqID))
			{
				objPreAuthSeqID=null;
			}
			String preAuthSeqID = objPreAuthSeqID == null ? "0"
					: objPreAuthSeqID.toString();
		
			StringBuffer strCaption = new StringBuffer();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			HttpSession session = request.getSession();
			frmPreAuthGeneral.initialize(mapping);
            
			PreAuthDetailVO preAuthDetailVO = preAuthObject.getPreAuthDetails(new Long(preAuthSeqID));

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
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
			
		
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
			
			
			
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
			
			if (PreAuthWebBoardHelper.checkWebBoardId(request) == null) {
				ActionMessages actionMessages = new ActionMessages();
				ActionMessage actionMessage = new ActionMessage("error.PreAuthorization.required");
		        actionMessages.add("global.error",actionMessage); 
		        saveErrors(request,actionMessages);
		        return mapping.findForward("failure");
				
			}// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			
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
				request.setAttribute("errorMsg", "Select Pre-Auth Details");
				return this.getForward(strPreauthHistoryList, mapping, request);
			}

			// call the business layer to get the Pre-Auth detail
			// frmHistoryList.set("preAuthSeqID",
			// frmPreAuthGeneral.getString("preAuthSeqID"));
			ArrayList<String[]> authorizationList = preAuthObject
					.getPreauthHistoryList(
							new Long(frmPreAuthGeneral.getString("memberSeqID")),
							frmHistoryList.getString("historyMode"),null);
            request.setAttribute("historyListFlag", "HistoryTab");
			session.setAttribute("preauthHistoryList", authorizationList);
			session.setAttribute("frmHistoryList", frmHistoryList);
			session.setAttribute("tabFlag", "HistoryTab");
		//	request.setAttribute("tabFlag", request.getParameter("tabFlag"));
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

	
	public ActionForward doViewHistorypatclm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doViewHistorypatclm");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			DynaActionForm frmHistoryList = (DynaActionForm) form;
			
			Document historyDoc = null;
			String authSeqID = request.getParameter("authSeqID");
			authSeqID = (authSeqID == null || authSeqID.length() < 1) ? "0"
					: authSeqID;
			    String strflag  = request.getParameter("flag");
			request.getSession().setAttribute("WorkFlow", "Preauth");
			if ("P".equals(strflag)) {
				historyDoc = preAuthObject
						.getPreAuthHistory(new Long(authSeqID));
				request.setAttribute("flag", "P");
				request.setAttribute("preAuthHistoryDoc", historyDoc);
				return this.getForward(strPreAuthViewHistory, mapping, request);
			} else if ("C".equals(strflag)) {
				historyDoc = preAuthObject.getClaimHistory(new Long(authSeqID));
				request.setAttribute("claimHistoryDoc", historyDoc);
				request.setAttribute("flag", "C");
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
			log.info("Inside the uploadToDhpo method of PreAuthGeneralAction");
			setLinks(request);	
			
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
			HttpSession session=request.getSession();
			StringBuilder strCaption=new StringBuilder();
			
			//calling dhpo method
			dhpoPreauthUpload(preAuthSeqID,request);
			
			PreAuthDetailVO preAuthDetailVO = preAuthObject.getPreAuthDetails(new Long(preAuthSeqID));

			frmPreAuthGeneral = setFormValues(preAuthDetailVO, mapping, request);
			
			strCaption.append(" Edit");
			strCaption.append(" [ "+ frmPreAuthGeneral.getString("patientName") + " ]");
			strCaption.append(" [ " + frmPreAuthGeneral.getString("memberId") + " ]");
			strCaption.append(" [ " + preAuthDetailVO.getPreAuthNoStatus()+ " ] ");
			frmPreAuthGeneral.set("caption", strCaption.toString());
			
			if ("N".equals(frmPreAuthGeneral.getString("networkProviderType"))) {
				session.setAttribute("providerStates", TTKCommon.getStates(frmPreAuthGeneral.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon.getAreas(frmPreAuthGeneral.getString("providerEmirate")));
			}
			session.setAttribute("encounterTypes", preAuthObject.getEncounterTypes(frmPreAuthGeneral.getString("benefitType")));
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmPreAuthGeneral))
				frmPreAuthGeneral.set("memberwtflag","Y");
			}
			session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
			
           request.getSession().setAttribute("dhpoFlagYN","N");
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

	private void dhpoPreauthUpload(String preAuthSeqID,HttpServletRequest request) throws Exception{
		PreAuthManager preAuthObject = this.getPreAuthManagerObject();
		String userID="";
		String password="";
		
		DhpoWebServiceVO dhpoWebServiceVO=preAuthObject.getDhpoPreauthDetails(preAuthSeqID);
		String senderID=TTKCommon.checkNull(dhpoWebServiceVO.getDhpoLicenceNO());
		
		
			if(senderID.equals(TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.licence.NO"))){
		 
			userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.userID");
		    password=TTKPropertiesReader.getPropertyValue("DHPOWebService.GN.password");
			
			}else{
			
			userID=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.userID");
		    password=TTKPropertiesReader.getPropertyValue("DHPOWebService.VH.password");
	
			}
		
		
		
       if(dhpoWebServiceVO.getFileContent()!=null){
    	   
       SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyy HH:mm");
    		Reader xml=new StringReader(new String(dhpoWebServiceVO.getFileContent()));
			SAXReader saxreader = new SAXReader();
			 Document document = saxreader.read(xml);
		        Node header = document.selectSingleNode("/"+document.getRootElement().getName()+"/Header" );
		        header.selectSingleNode("TransactionDate").setText(sd.format(new Date()));
    			 dhpoWebServiceVO.setFileContent(document.asXML().getBytes());
		//DHPO Webservice code
		
ValidateTransactionsSoap transactionsSoap=getDhpoStubObj();	
Holder<Integer> uploadTransactionsResult=new Holder<>();
Holder<String> errorMessage=new Holder<>();
Holder<byte[]> errorReport=new Holder<>();



transactionsSoap.uploadTransaction(userID,password, dhpoWebServiceVO.getFileContent(), dhpoWebServiceVO.getFileName(), uploadTransactionsResult, errorMessage, errorReport);

String preAuthUploadStatus="N";
		if(uploadTransactionsResult.value==1||uploadTransactionsResult.value==0){	
			request.setAttribute("successMsg2", "DHPO Preauth details uploaded successfully ");	
			preAuthUploadStatus="Y";
		}else{
			request.setAttribute("errorMsg2", "DHPO Preauth details not uploaded please check logs");
		}
		DhpoWebServiceVO logDetails=new DhpoWebServiceVO();
		
		logDetails.setPreAuthSeqID(preAuthSeqID);
		logDetails.setTransactionResult(uploadTransactionsResult.value);
		logDetails.setFileName(dhpoWebServiceVO.getFileName());
		logDetails.setErrorMessage(errorMessage.value);
		logDetails.setErrorReport(errorReport.value);
		logDetails.setPreAuthUploadStatus(preAuthUploadStatus);
		
		//update dhpo log details
		preAuthObject.saveDhpoPreauthLogDetails(logDetails);
		
		
       }// if(dhpoWebServiceVO.getFileContent()!=null){
	}
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
			
			
			// Reader logReader=(logDetails.getXmlFileReader()==null?new StringReader("No Logs"):logDetails.getXmlFileReader());
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
              String fileName=logDetails.getFileName()==null?"FileName":logDetails.getFileName();
            			    	
					  response.setContentType("application/txt");
				      response.setHeader("Content-Disposition", "attachment;filename="+fileName+dateFormat.format(new Date())+".csv");
				                                                       
				      
				      PrintWriter pw= response.getWriter();
				     			                          
                      pw.write(TTKCommon.checkNull(logDetails.getErrorMessage()));	
                      pw.write("\n");
				      if(logDetails.getErrorText()!=null)  pw.write(logDetails.getErrorText());
				      else pw.write("No Logs Details");
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
		  
			
			String strFile	=	TTKPropertiesReader.getPropertyValue("mouUploads")+request.getParameter("filePath");
		  
		  
				File file=new File(strFile);
				  response.setContentType("application/txt");
			      response.setHeader("Content-Disposition", "attachment;filename="+request.getParameter("fileName"));
			                                                       
	                   fis= new FileInputStream(file);
	                  
	                   bis = new BufferedInputStream(fis);
	                   baos=new ByteArrayOutputStream();
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

	private ValidateTransactionsSoap getDhpoStubObj(){
		if(transactionsSoap==null){
			ValidateTransactions transactions=new ValidateTransactions();
			 transactionsSoap=transactions.getValidateTransactionsSoap();
		}
   return transactionsSoap;
	}
private ValidateTransactionsSoap 	transactionsSoap=null;
	
	
	
	//CEED Validation
	
			public ActionForward doCeedValidation(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				try {
					
					setLinks(request);
					//	String ceedResponse	=	"C:\\Users\\kishor.kumar\\Desktop\\ceedResponse.xml";
					//	String ceedRequest	=	"C:\\Users\\kishor.kumar\\Desktop\\test.xml";
						log.debug("Inside ClaimGeneralAction doSave");
						DynaActionForm frmClaimGeneral = (DynaActionForm) form;
						String preAuthSeqID	=	(String) frmClaimGeneral.get("preAuthSeqID");
						String preAuthNo	=	(String) frmClaimGeneral.get("preAuthNo");
						
						//GET CLAIM XML FROM DB  S T A R T S
						PreAuthManager preAuthObject = this.getPreAuthManagerObject();
						Document xmlData	=	preAuthObject.getPATXmlTOSendCEED(new Long(preAuthSeqID));
				//		System.out.println(" xmlDate ===> "+xmlData.asXML() );
					
						String strMode="PAT";
						String responseData=CeedSoapSaajClient.execute("1", xmlData,strMode,preAuthNo);

		/*	            System.out.println("==========responseData==============");
			            System.out.println(responseData); */
			      //      CeedSoapSaajClient.writeFileToDisk(responseData,ceedResponse);//RESPONSE XML WRITE TO FILE
			            
			          //READING XML RESPONSE FROM DESKTOP FILE
			            SAXReader reader	=	new SAXReader();
			            Document doc		=	(Document) reader.read(new StringReader(responseData));
			            //Document doc		=	(Document) reader.read(ceedResponse);	
			            
			            request.getSession().setAttribute("ceedResponse",doc);
			            
			            String namespaceArr[]	=	{"http://schemas.xmlsoap.org/soap/envelope/","http://www.w3.org/2001/XMLSchema-instance",
			            							"http://www.w3.org/2001/XMLSchema","http://www.Dimensions-healthcare.net/DHCEG/CommonTypes"
			            							};
			            
			            //REMOVE NAMESPACE CODE
			            Namespace oldNs = 	null;
			            Namespace newNs =	null;
			            Visitor visitor	=	null;
			            for(int i=0;i<namespaceArr.length;i++)
			            {
			            	oldNs = Namespace.get(namespaceArr[i]);
			     	        newNs = Namespace.get("", "");
			     	        visitor = new NamesapceChangingVisitor(oldNs, newNs);
			     	        doc.accept(visitor);
			            }
			           
			  //          System.out.println("doc after removing namespace::\n"+doc.asXML());
			            //REMOVE NAMESPACE CODE
			            
			            Node envelopeNode	=	doc.selectSingleNode(doc.getRootElement().getPath());
			            
			            Node bodyNode		=	doc.selectSingleNode(envelopeNode.getPath()+"/soap:Body");
			            
			            Node dhcgegResponseNode		=	doc.selectSingleNode(bodyNode.getPath()+"/DHCEGResponse");
			          
			            Node dhcegResultNode		=	doc.selectSingleNode(dhcgegResponseNode.getPath()+"/DHCEGResult");
			            
			            Node responseNode			=	doc.selectSingleNode(dhcgegResponseNode.getPath()+"/response");
			            
			     //       List<Node> claimEditNode			=	doc.selectNodes(responseNode.getPath()+"/ClaimEdit");
			     //       request.getSession().setAttribute("claimEditNode",claimEditNode);

			            		//DocumentHelper.parseText(((Node)userProfileDoc.selectSingleNode("/usersecurityprofile/user")).asXML());
			            List<Node> claimEditNode			=	null;
			            // claimEditNode			=	doc.selectNodes(responseNode.getPath()+"/ClaimEdit");
			            if(doc.asXML().contains("ClaimEdit"))
			            {
			               	claimEditNode			=	doc.selectNodes(responseNode.getPath()+"/ClaimEdit");
			                request.getSession().setAttribute("claimEditNode",claimEditNode);
			            }
			         	else
			         	{
			             	   request.getSession().setAttribute("claimEditNode",claimEditNode);
			         	}
			            request.setAttribute("successMsg", "Record updated successfully!");
			          
			            return this.getForward("preauthdetail", mapping, request);
				}// end of try

				catch (TTKException expTTK) {
					return this.processExceptions(request, mapping, expTTK);
				}// end of catch(TTKException expTTK)
				catch (Exception exp) {
					return this.processExceptions(request, mapping, new TTKException(
							exp, strAuthorizationError));
				}// end of catch(Exception exp)
			}// end of doCeedValidation(ActionMapping mapping,ActionForm
				// form,HttpServletRequest request,HttpServletResponse response)
		
			public ActionForward viewBenefitDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
				try {
					setLinks(request);
					log.info("Inside ClaimGeneralAction addActivityDetails ");
					
					HttpSession session = request.getSession();
					DynaActionForm frmPreAuthGeneral = (DynaActionForm) session.getAttribute("frmPreAuthGeneral");
					
					String benefitType = frmPreAuthGeneral.getString("benefitType");
					String benefitTypes= (String)request.getParameter("benefitType");
					String benefitTypes1= (String)request.getParameter("benefitType");
					PreAuthManager preAuthObject = this.getPreAuthManagerObject();
		    		//get the tbale data from session if exists
					TableData tableData =TTKCommon.getTableData(request);
					//PreAuthVO preAuthVO=new PreAuthVO;
					//clear the dynaform if visting from left links for the first time
					//else get the dynaform data from session
					if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
					{
						((DynaActionForm)form).initialize(mapping);//reset the form data
					}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
					String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
					String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
					//if the page number or sort id is clicked
					if(!strPageID.equals("") || !strSortID.equals(""))
					{
						if(!strPageID.equals(""))
						{
							tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
							return mapping.findForward("");
						}///end of if(!strPageID.equals(""))
						else
						{
							tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
							tableData.modifySearchData("sort");//modify the search data
						}//end of else
					}//end of if(!strPageID.equals("") || !strSortID.equals(""))
					else
					{
						//create the required grid table
						tableData.createTableInfo("BenefitTable",null);//DisplayBenefitsTable  BenefitTable
						
						
						if("OPTS".equals(benefitTypes)||"OPT".equals(benefitTypes))
							benefitTypes="Out-patient";
						if("DNTL".equals(benefitTypes)||"DNT".equals(benefitTypes))
							benefitTypes="Dental";
						if("IMTI".equals(benefitTypes)||"OMTI".equals(benefitTypes)||"MAT".equals(benefitTypes))
							benefitTypes="Maternity";
						if("OPTC".equals(benefitTypes)||"OPL".equals(benefitTypes))
							benefitTypes="Optical";
						if("IPT".equals(benefitTypes))
							benefitTypes="In-Patient";
						
						if("ALL".equals(benefitTypes))
							benefitTypes="All";
						if("PED".equals(benefitTypes))
							benefitTypes="PED";
						if("CHRC".equals(benefitTypes))
							benefitTypes="CHRONIC CONDITION";
						if("AYR".equals(benefitTypes))
							benefitTypes="AYURVEDA/HOMEOPATHY";
						if("HEA".equals(benefitTypes))
							benefitTypes="HEALTH CHECKUP/WELLNESS";
						
						ArrayList<Object> allinfo=new ArrayList<Object>();
						allinfo.add(frmPreAuthGeneral.getString("policySeqId"));
						allinfo.add(frmPreAuthGeneral.getString("memberSeqID"));
						allinfo.add(TTKCommon.getActiveLink(request));//TTKCommon.getActiveLink(request)
						allinfo.add(frmPreAuthGeneral.getString("preAuthSeqID"));
						allinfo.add(benefitTypes);//benefitType
						tableData.setSearchData(allinfo);
						tableData.modifySearchData("search");
					}//end of else
					
				ArrayList<Object> alBenefitList= preAuthObject.getBenefitDetails(tableData.getSearchData());
				tableData.setData((ArrayList<Object>)alBenefitList.get(0), "search");
				//set the table data object to session
				request.getSession().setAttribute("tableData",tableData);
				if("OPTS".equals(benefitTypes)||"OPT".equals(benefitTypes))
					benefitTypes="Out-patient";
				if("DNTL".equals(benefitTypes)||"DNT".equals(benefitTypes))
					benefitTypes="Dental";
				if("IMTI".equals(benefitTypes)||"OMTI".equals(benefitTypes)||"MAT".equals(benefitTypes))
					benefitTypes="Maternity";
				if("OPTC".equals(benefitTypes)||"OPL".equals(benefitTypes))
					benefitTypes="Optical";
				if("IPT".equals(benefitTypes))
					benefitTypes="In-Patient";
				if("OTH".equals(benefitTypes))
					benefitTypes="Others";
				if("ALL".equals(benefitTypes))
					benefitTypes="All";
				if("PED".equals(benefitTypes))
					benefitTypes="PED";
				if("CHRC".equals(benefitTypes))
					benefitTypes="CHRONIC CONDITION";
				if("AYR".equals(benefitTypes))
					benefitTypes="AYURVEDA/HOMEOPATHY";
				if("HEA".equals(benefitTypes))
					benefitTypes="HEALTH CHECKUP/WELLNESS";
				
				request.setAttribute("benefitType",benefitTypes1);
				request.setAttribute("availableSumInsured",alBenefitList.get(1));	
				//request.setAttribute("otherRemarks",alBenefitList.get(2));
				request.setAttribute("utilizeSuminsured",alBenefitList.get(2));
			return mapping.findForward(strBenefitDetails);

				}// end of try
				catch (TTKException expTTK) {
					return this.processExceptions(request, mapping, expTTK);
				}// end of catch(TTKException expTTK)
				catch (Exception exp) {
					return this.processExceptions(request, mapping, new TTKException(
							exp, strPreauthdetail));
				}// end of catch(Exception exp)
			}// end of AddActivityDetails(ActionMapping mapping,ActionForm
				// form,HttpServletRequest request,HttpServletResponse response)
			
			
			public ActionForward getMemberDetails(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				try {
					setLinks(request);
					log.debug("Inside ClaimGeneralAction getMemberDetails ");
					PreAuthManager preAuthObject = this.getPreAuthManagerObject();
					DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
					String memberId = frmPreAuthGeneral.getString("memberId");
					
					memberId = (memberId == null) ? "" : memberId.trim();
					PreAuthDetailVO preAuthDetailVO = preAuthObject
							.getMemberDetails(memberId);
					
					
					//frmClaimGeneral.set("preAuthSeqID", "");
					//frmClaimGeneral.set("authNum", "");
					if (preAuthDetailVO == null) {
						request.setAttribute("errorMsg", "Given Member ID Is Invalid");
						frmPreAuthGeneral.set("memberSeqID", "");
						frmPreAuthGeneral.set("patientName", "");
						frmPreAuthGeneral.set("memberAge", "");
						frmPreAuthGeneral.set("emirateId", "");
						frmPreAuthGeneral.set("payerId", "");
						frmPreAuthGeneral.set("insSeqId", "");
						frmPreAuthGeneral.set("policySeqId", "");
						frmPreAuthGeneral.set("payerName", "");
						frmPreAuthGeneral.set("patientGender", "");
						frmPreAuthGeneral.set("policyNumber", "");
						frmPreAuthGeneral.set("corporateName", "");
						frmPreAuthGeneral.set("policyStartDate", "");
						frmPreAuthGeneral.set("policyEndDate", "");
						frmPreAuthGeneral.set("nationality", "");
						frmPreAuthGeneral.set("sumInsured", "");
						frmPreAuthGeneral.set("availableSumInsured", "");
						frmPreAuthGeneral.set("policyType", "");
						frmPreAuthGeneral.set("emirateId",""); 
						frmPreAuthGeneral.set("productName", "");
						frmPreAuthGeneral.set("payerAuthority", "");
						frmPreAuthGeneral.set("vipYorN","");
						frmPreAuthGeneral.set("clmMemInceptionDate","");
						frmPreAuthGeneral.set("memCoveredAlert", "Patient is not a covered member"); 
						
		  }
					
					
					else{
			  request.setAttribute("successMsg","Member ID Is Valid");

			  frmPreAuthGeneral.set("memberIDValidYN", "Y");
			  frmPreAuthGeneral.set("memberSeqID",
								TTKCommon.checkNull(preAuthDetailVO.getMemberSeqID())
										.toString());
			  frmPreAuthGeneral.set("patientName",
								preAuthDetailVO.getClaimantName());
			  frmPreAuthGeneral.set("memberAge",
								TTKCommon.checkNull(preAuthDetailVO.getMemberAge())
										.toString());
			  frmPreAuthGeneral	.set("emirateId", preAuthDetailVO.getEmirateId());
			  frmPreAuthGeneral.set("payerId", preAuthDetailVO.getPayerId());
			  frmPreAuthGeneral.set("insSeqId",
								TTKCommon.checkNull(preAuthDetailVO.getInsSeqId())
										.toString());
			  frmPreAuthGeneral.set("policySeqId",
								TTKCommon.checkNull(preAuthDetailVO.getPolicySeqId())
										.toString());
			  frmPreAuthGeneral
								.set("payerName", preAuthDetailVO.getPayerName());
			  frmPreAuthGeneral.set("patientGender",
								preAuthDetailVO.getPatientGender());
			  frmPreAuthGeneral.set("policyNumber",
								preAuthDetailVO.getPolicyNumber());
			  frmPreAuthGeneral.set("corporateName",
								preAuthDetailVO.getCorporateName());
			  frmPreAuthGeneral.set("policyStartDate",
								preAuthDetailVO.getPolicyStartDate());
			  frmPreAuthGeneral.set("policyEndDate",
								preAuthDetailVO.getPolicyEndDate());
			  frmPreAuthGeneral.set("nationality",
								preAuthDetailVO.getNationality());
			  frmPreAuthGeneral.set("sumInsured",
								TTKCommon.checkNull(preAuthDetailVO.getSumInsured())
										.toString());
			  frmPreAuthGeneral.set(
								"availableSumInsured",
								TTKCommon.checkNull(
										preAuthDetailVO.getAvailableSumInsured())
										.toString());
			  frmPreAuthGeneral.set("policyType",
								preAuthDetailVO.getPolicyType());
			  frmPreAuthGeneral.set("productName",preAuthDetailVO.getProductName());
			  frmPreAuthGeneral.set("payerAuthority", preAuthDetailVO.getPayerAuthority());
			  frmPreAuthGeneral.set("vipYorN",preAuthDetailVO.getVipYorN());
			  frmPreAuthGeneral.set("policyCategory",preAuthDetailVO.getPolicyCategory());
			  
			 
			  frmPreAuthGeneral.set("clmMemInceptionDate",preAuthDetailVO.getClmMemInceptionDate());
			  frmPreAuthGeneral.set("memCoveredAlert", ""); 
		  }
					request.getSession().setAttribute("frmPreAuthGeneral",
							frmPreAuthGeneral);
					return mapping.findForward("preauthdetail");
				}// end of try
				catch (TTKException expTTK) {
					return this.processExceptions(request, mapping, expTTK);
				}// end of catch(TTKException expTTK)
				catch (Exception exp) {
					return this.processExceptions(request, mapping, new TTKException(
							exp, strPreauthdetail));
				}// end of catch(Exception exp)
			}// getMemberDetails
			
			
			public boolean	memberAgeValidation(DynaActionForm frmPreAuthGeneral)throws Exception{
				String strProvAuthority=frmPreAuthGeneral.getString("provAuthority");
				String strEncounterTypeId=frmPreAuthGeneral.getString("encounterTypeId");
				boolean status=false;
				if("DHA".equals(strProvAuthority)&&("3".equals(strEncounterTypeId)||"4".equals(strEncounterTypeId))){
					
					String strAdmissionDate=frmPreAuthGeneral.getString("admissionDate");
					String strMemberDOB=frmPreAuthGeneral.getString("memberDOB");
					if(strMemberDOB!=null&&!"".equals(strMemberDOB)){	
						if(strAdmissionDate!=null&&!"".equals(strAdmissionDate)){
						SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
						Date date1=simpleDateFormat.parse(strMemberDOB);
						
						Date date2=simpleDateFormat.parse(strAdmissionDate);
						long sd=(8*24*60*60*1000);
						long dif=date2.getTime()-date1.getTime();
						if(dif<sd){
							
							status=true;
							int days = (int) (dif / (1000*60*60*24));
							frmPreAuthGeneral.set("patientdays",days+"");
						}
					}
					}
					
				}
			return status;
			}
			
			
			
			
			public ActionForward setNetWorkMode(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
						
				log.debug("Inside PreAuthGenealAction setNetWorkMode");
				DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
				
				String processType = frmPreAuthGeneral.getString("processType");
				String partnerName = frmPreAuthGeneral.getString("partnerName");
				
				
				if("DBL".equals(processType)){
					
					if(partnerName!=""){
						frmPreAuthGeneral.set("networkProviderType","N");
					}
					else{
						frmPreAuthGeneral.set("networkProviderType","Y");
					}
					
				}
				
				else{
					frmPreAuthGeneral.set("networkProviderType","Y");
				}
				
				request.getSession().setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
				return mapping.findForward(strPreAuthDetail);	
			}
			
			public ActionForward doUploadPreauthDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
					HttpServletResponse response) throws Exception
			{
				try{
				
					setLinks(request);
					request.getSession().removeAttribute("successMsgs");
					MOUDocumentVO mouDocumentVO	=	new MOUDocumentVO();
					DynaActionForm frmPreauthDocsUpload=(DynaActionForm)form;
					String buttonEnableYN = frmPreauthDocsUpload.getString("buttonEnableYN");
					mouDocumentVO=(MOUDocumentVO)FormUtils.getFormValues(frmPreauthDocsUpload,this, mapping, request);
					String  preAuthSeqID = frmPreauthDocsUpload.getString("preAuthSeqID");
					
					ArrayList<MOUDocumentVO>alPreauthDocs = null;
					if(request.getSession().getAttribute("alPreauthDocs")!=null) 
					{
						alPreauthDocs=(ArrayList)(request.getSession()).getAttribute("alPreauthDocs");
					}
					else
					{
						alPreauthDocs=new ArrayList<MOUDocumentVO>();
					}
					
					mouDocumentVO=(MOUDocumentVO)FormUtils.getFormValues(frmPreauthDocsUpload,this, mapping, request);
					mouDocumentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
					StringBuffer sbfCaption= new StringBuffer();
					sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
					
					//..............File Upload from Local System.................
					mouDocumentVO =(MOUDocumentVO)FormUtils.getFormValues(frmPreauthDocsUpload, "frmPreauthDocsUpload",this, mapping, request);
					//Get the FormFile object from ActionForm.
					StringBuffer strCaption=new StringBuffer();
					ArrayList alFileAUploadList = new ArrayList();
					Long userSeqId	=	(Long) TTKCommon.getUserSeqId(request);
					
					
					//START FETCH FILE FROM LOCAL SYSTEM
					Matcher matcher =null;
					Pattern pattern =null;
					FormFile formFile = (FormFile)frmPreauthDocsUpload.get("file");
					
					pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
					matcher = pattern.matcher( formFile.getFileName() );
					
					if(matcher.find())
					{	
		                TTKException expTTK = new TTKException();
		            	expTTK.setMessage("error.filetype.upload.format");
		            	throw expTTK;
					}
					
					String filetype	=	(String)frmPreauthDocsUpload.get("fileType");
					String filedescription=	(String)frmPreauthDocsUpload.get("filedescription");
					if(filedescription.length()>250)
					{
						TTKException expTTK = new TTKException();
	            		expTTK.setMessage("error.fileDescription");
	            		throw expTTK;
					}
					else
					{
					frmPreauthDocsUpload=(DynaActionForm)request.getSession().getAttribute("frmPreauthDocsUpload");
					InputStream inputStream = formFile.getInputStream(); 		
					int formFileSize	=	formFile.getFileSize();				
				    if(formFileSize==0)
				    {
				    	TTKException expTTK = new TTKException();
	            		expTTK.setMessage("error.formfilesize");
	            		throw expTTK;
				    }
				    else
				    {
		            //COPYNG FILE TO SERVER FOR BACKUP
		            FileOutputStream outputStream = null;
		            String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("mouUploads"));
		            File folder = new File(path);
		            if(!folder.exists()){
		                  folder.mkdirs();
		            }
		            //String finalPath=(path+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+"_"+formFile);
		            String timeStamps	=	new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date());
		            String fileExc[]=formFile.getFileName().split("[.]");
		            String oFileName=formFile.getFileName().split("."+fileExc[fileExc.length-1])[0];
		            String filetypesName = fileExc[1];
		            if("mp4".equals(filetypesName) || "exe".equals(filetypesName) || "mp3".equals(filetypesName) || "wmv".equals(filetypesName))
		            	{
		               		TTKException expTTK = new TTKException();
		            		expTTK.setMessage("error.filetype.mp3.mp4.exe");
		            		throw expTTK;
		            		
		            	}
		            else
		            {
		            String fileName2=oFileName+"-"+timeStamps+"."+fileExc[fileExc.length-1];
		            String origFileName	=	formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
		            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf("."));
		            String finalPath=(path+formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
		            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf(".")));
		            
		            if(finalPath.length()>200)
		            {
		            	TTKException expTTK = new TTKException();
	            		expTTK.setMessage("error.fileNamePath.PolicyDOCUploads");
	            		throw expTTK;
		            }
		            else
		            {
		            	
		            outputStream = new FileOutputStream(new File(path+fileName2));
	
		            alFileAUploadList.add(TTKCommon.getUserSeqId(request));	
					alFileAUploadList.add(finalPath);						
					alFileAUploadList.add(filedescription);					
					alFileAUploadList.add(preAuthSeqID);					
				   outputStream.write(formFile.getFileData());		//Uploaded file backUp
		            // E N D S FETCH FILE FROM LOCAL SYSTEM

		          //get the session bean from the bean pool for each excecuting thread
				   PreAuthManager preAuthObject = this.getPreAuthManagerObject();	
		           int iSuccess	=	 preAuthObject.savePreauthDocsUploads(alFileAUploadList,userSeqId,preAuthSeqID,fileName2,inputStream,formFileSize);
					if(iSuccess>0)
					{   
						alPreauthDocs = preAuthObject.getPreauthDocsUploads(preAuthSeqID);
						request.getSession().setAttribute("alPreauthDocs", alPreauthDocs);
						frmPreauthDocsUpload.set("filedescription","");
						request.getSession().setAttribute("frmPreauthDocsUpload",frmPreauthDocsUpload);
						request.getSession().setAttribute("successMsgs", "Document Uploaded Successfully.");
					}
					else{
						request.getSession().setAttribute("notify", "Document not Uploaded Properly! Please again upload the Document.");
					//frmPolicyUploads.set("updated",updated);
					request.getSession().setAttribute("frmPreauthDocsUpload", frmPreauthDocsUpload);
					}
					//frmPolicyUploads.set("caption",strCaption);
					if(inputStream!=null)
						inputStream.close();
					if(outputStream!=null)
						outputStream.close();
		            }
		            }
				    }
					}
					return mapping.findForward("documentsUpload");
				}//end of try
				catch(TTKException expTTK)
			     {
			            return this.processExceptions(request, mapping, expTTK);
			     }//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request,mapping,new TTKException(exp,"strPreauthdetail"));
				}//end of catch(Exception exp)
			}
			
			public ActionForward doPreauthFileDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
					HttpServletResponse response) throws Exception
					{
				try{
					setLinks(request);
					ArrayList<MOUDocumentVO>alPreauthDocs = new ArrayList<MOUDocumentVO>();
		    		String moudocseqid = request.getParameter("moudocseqid");
					String deleteRemarks = request.getParameter("deleteRemarks");
					
					if(deleteRemarks.length() > 250)
					{
						 ActionMessages actionMessages = new ActionMessages();
			             ActionMessage actionMessage = new ActionMessage("error.deleteRemarks");
			             actionMessages.add("global.error",actionMessage);
			             saveErrors(request,actionMessages);
			             return mapping.findForward("documentsUpload");
					}
					else
					{
		    		Long userSeqId	=	(Long) TTKCommon.getUserSeqId(request);
		    		DynaActionForm frmPreauthDocsUpload=(DynaActionForm)request.getSession().getAttribute("frmPreauthDocsUpload");
		    		String preAuthSeqID = frmPreauthDocsUpload.getString("preAuthSeqID");
		            PreAuthManager preAuthObject = this.getPreAuthManagerObject();
		            int result = preAuthObject.delPreauthUploadedFile(moudocseqid,deleteRemarks,userSeqId);
		            if(result>0)
		            {
		            	request.getSession().setAttribute("successMsgs", "Document deleted successfully.");
		            	alPreauthDocs = preAuthObject.getPreauthDocsUploads(preAuthSeqID);
		            	request.getSession().setAttribute("alPreauthDocs", alPreauthDocs);
		            }
		            else
		            {
		            	request.getSession().setAttribute("notify", "Document deleted problem.");
		            }
					}
					return mapping.findForward("documentsUpload");
		    		}//end of try
				    catch(TTKException expTTK)
			        {
			            return this.processExceptions(request, mapping, expTTK);
			        }//end of catch(TTKException expTTK)
		    		catch(Exception exp)
		    		{
		    			return this.processExceptions(request,mapping,new TTKException(exp,"strPreauthdetail"));
		    		}//end of catch(Exception exp)
		    	}
			  
			
			public ActionForward doListEnhancedRelatedPreAuth(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				try{
					
					log.info("Inside doListEnhancedRelatedPreAuth() in PreAuthGeneralAction");
				      setLinks(request);
				      
				      HttpSession session = request.getSession();
						DynaActionForm frmHistoryList = (DynaActionForm) form;
						frmHistoryList.initialize(mapping);
						
						PreAuthManager preAuthObject = null;
						preAuthObject = this.getPreAuthManagerObject();
						DynaActionForm frmPreAuthGeneral = (DynaActionForm) session
								.getAttribute("frmPreAuthGeneral");
						
				//		if ("Y".equals(request.getParameter("Entry")))
							frmHistoryList.set("historyMode", "PAT");
						
						
			//	      request.setAttribute("historyListFlag", "GeneralTab");
				      String preauthNo = request.getParameter("preAuthNo");
				       
				    
				    ArrayList<String[]> authorizationList = preAuthObject
							.getPreauthHistoryList(
									new Long(frmPreAuthGeneral.getString("memberSeqID")),
									frmHistoryList.getString("historyMode"),preauthNo);
		      //      request.setAttribute("historyListFlag", "HistoryTab");
					session.setAttribute("preauthHistoryList", authorizationList);
					session.setAttribute("frmHistoryList", frmHistoryList);
					session.setAttribute("tabFlag", "EnhancedPreauth");
					return this.getForward(strPreauthHistoryList, mapping, request);
						
				}catch (TTKException expTTK) {
					return this.processExceptions(request, mapping, expTTK);
				}// end of catch(TTKException expTTK)
				catch (Exception exp) {
					return this.processExceptions(request, mapping, new TTKException(
							exp, strPreauthHistoryList));
				}// end of catch(Exception exp)
				    
			}
			public ActionForward viewObsDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			    		HttpServletResponse response) throws Exception{
				  log.debug("inside PreauthGeneralAction viewObsDetails()");
			 		
			 		String fileName=(String)request.getParameter("filename");
			 		String filaPath=TTKPropertiesReader.getPropertyValue("ObservationFileUpload");
			        
			         File file=new File(filaPath+fileName);
			         
			         if(!file.exists())
			         {
			        	 ActionMessages actionMessages = new ActionMessages();
		                 ActionMessage actionMessage = new ActionMessage("error.file.not.exist");
		                 actionMessages.add("global.error",actionMessage);
		                 saveErrors(request,actionMessages);
			         }
			         response.setContentType("application/txt");
			         response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			         
			         ServletOutputStream sos=response.getOutputStream();
			         FileInputStream fis=new FileInputStream(file);
			           byte[]fileData=new byte[fis.available()];
			           fis.read(fileData);
			           sos.write(fileData);
			           fis.close();
			           sos.flush();
			           sos.close();
					 return null;
		     }		
			   
			  public ActionForward saveProviderObserDetails(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
					try {
						setLinks(request);

						log.debug("Inside PreAuthGenealAction saveProviderObserDetails() ");
						DynaActionForm frmProviderObservDetails = (DynaActionForm) form;
						ObservationDetailsVO observationDetailsVO = new ObservationDetailsVO();
						String successMsg;
						observationDetailsVO = (ObservationDetailsVO) FormUtils.getFormValues(frmProviderObservDetails, this, mapping, request);
						
					    PreAuthManager preAuthObject = this.getPreAuthManagerObject();

						if (observationDetailsVO.getObservSeqId() == null || observationDetailsVO.getObservSeqId() == 0)
							successMsg = "Observation Details Added Successfully.";
						else
							successMsg = "Observation Details Updated Successfully.";
						observationDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
						
						if("FIL".equals(frmProviderObservDetails.getString("observType")))
						{	
							FormFile formFile = (FormFile)frmProviderObservDetails.get("file");		// file name
							String filename = formFile.toString();
							observationDetailsVO.setObservValue(filename);
							InputStream inputStream = formFile.getInputStream(); 		
							int formFileSize	=	formFile.getFileSize();				
						    if(formFileSize==0)
						    {
							   	 ActionMessages actionMessages = new ActionMessages();
					             ActionMessage actionMessage = new ActionMessage("error.formfilesize");			
					             actionMessages.add("global.error",actionMessage);
					             saveErrors(request,actionMessages);
						    }
						    else
							{
						            //COPYNG FILE TO SERVER FOR BACKUP
						            FileOutputStream outputStream = null;
						            String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("ProviderObserFileUpload"));
						            File folder = new File(path);
						            if(!folder.exists())
						            {
						                  folder.mkdirs();
						            }
						            //String finalPath=(path+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+"_"+formFile);
						            String timeStamps	=	new SimpleDateFormat("dd-MM-yyyy-HH-mm-SSS").format(new Date());
						            String fileExc[]=formFile.getFileName().split("[.]");
						            String oFileName=formFile.getFileName().split("."+fileExc[fileExc.length-1])[0];
						            String filetypesName = fileExc[1];
						            if("mp4".equals(filetypesName) || "exe".equals(filetypesName) || "mp3".equals(filetypesName) || "wmv".equals(filetypesName))
						            {
						            	 ActionMessages actionMessages = new ActionMessages();
						                 ActionMessage actionMessage = new ActionMessage("error.filetype.mp3.mp4.exe");
						                 actionMessages.add("global.error",actionMessage);
						                 saveErrors(request,actionMessages);
						            }
						            else
						            {
							            String fileName2=oFileName+"-"+timeStamps+"."+fileExc[fileExc.length-1];
							            String origFileName	=	formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
							            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf("."));
							            String finalPath=(path+formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
							            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf(".")));
					            
							            if(oFileName.length()>100)
							            {
							            	 ActionMessages actionMessages = new ActionMessages();
							                 ActionMessage actionMessage = new ActionMessage("error.fileNamePath.PolicyDOCUploads");
							                 actionMessages.add("global.error",actionMessage);
							                 saveErrors(request,actionMessages);
							            }
							            else
							            {
							            	  observationDetailsVO.setObservValue(fileName2);
							        //    	  request.getSession().setAttribute("origFileName", origFileName);
							            	  outputStream = new FileOutputStream(new File(path+fileName2));
							
											   outputStream.write(formFile.getFileData());		//Uploaded file backUp
									            // E N D S FETCH FILE FROM LOCAL SYSTEM
												if(inputStream!=null)
													inputStream.close();
												if(outputStream!=null)
													outputStream.close();
							            }
						            }   
							 }     
						}
						    
						Object results[] = preAuthObject.saveProviderObserDetails(observationDetailsVO);
						request.getSession().setAttribute("observations", results[2]);
						request.setAttribute("successMsg", successMsg);

						String activityDtlSeqId = frmProviderObservDetails.getString("activityDtlSeqId");
						String preAuthSeqID = frmProviderObservDetails.getString("preAuthSeqID");
						frmProviderObservDetails.initialize(mapping);
						frmProviderObservDetails.set("activityDtlSeqId", activityDtlSeqId);
						frmProviderObservDetails.set("preAuthSeqID", preAuthSeqID);
						request.getSession().setAttribute("frmProviderObservDetails",frmProviderObservDetails);
						request.getSession().removeAttribute("observCodes");
						request.getSession().removeAttribute("observValueTypes");
						return mapping.findForward("saveProviderObservDetails");
					}// end of try
					catch (TTKException expTTK) {
						return this.processExceptions(request, mapping, expTTK);
					}// end of catch(TTKException expTTK)
					catch (Exception exp) {
						return this.processExceptions(request, mapping, new TTKException(
								exp, strPreAuthDetail));
					}// end of catch(Exception exp)

				}
					  
		
		
			  public ActionForward getProviderObservTypeDetail(ActionMapping mapping,ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
						try {
								setLinks(request);
								log.debug("Inside PreAuthGenealAction getProviderObservTypeDetail ");
								DynaActionForm frmProviderObservDetails = (DynaActionForm) form;
								PreAuthManager preAuthObject = this.getPreAuthManagerObject();
								String observType = frmProviderObservDetails.getString("observType");
								Object[] observTypeDetails = preAuthObject.getObservTypeDetails(observType);
								request.getSession().setAttribute("observCodes",observTypeDetails[0]);
								request.getSession().setAttribute("observValueTypes",observTypeDetails[1]);
								return mapping.findForward("addProviderObservations");
						}// end of try
					catch (TTKException expTTK) {
						return this.processExceptions(request, mapping, expTTK);
					}// end of catch(TTKException expTTK)
					catch (Exception exp) {
						return this.processExceptions(request, mapping, new TTKException(
								exp, "addObservations"));
					}// end of catch(Exception exp)

				} 
			  
			  public ActionForward viewProviderObsDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			    		HttpServletResponse response) throws Exception{
				  log.debug("inside PreauthGeneralAction viewProviderObsDetails()");
				 
			 		String fileName=(String)request.getParameter("filename");
			 		String filaPath=TTKPropertiesReader.getPropertyValue("ProviderObserFileUpload");	
			        
			         File file=new File(filaPath+fileName);
			         
			         if(!file.exists()){
			        	 ActionMessages actionMessages = new ActionMessages();
		                 ActionMessage actionMessage = new ActionMessage("error.file.not.exist");
		                 actionMessages.add("global.error",actionMessage);
		                 saveErrors(request,actionMessages);
			        	 
			         }
			         response.setContentType("application/txt");
			         response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			         
			         ServletOutputStream sos=response.getOutputStream();
			         FileInputStream fis=new FileInputStream(file);
			           byte[]fileData=new byte[fis.available()];
			           fis.read(fileData);
			           sos.write(fileData);
			           fis.close();
			           sos.flush();
			           sos.close();
					 return null;
		     }	// end of viewProviderObsDetails()	
			  
			 
			
						
			
			
			  
			  public ActionForward doDocumentUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
				{
					log.info("Inside the doDocumentUpload method of PreauthGeneralAction");
					try
					{
						setLinks(request);

               if (PreAuthWebBoardHelper.checkWebBoardId(request) == null) {
				ActionMessages actionMessages = new ActionMessages();
				ActionMessage actionMessage = new ActionMessage("error.PreAuthorization.required");
		        actionMessages.add("global.error",actionMessage); 
		        saveErrors(request,actionMessages);
		        return mapping.findForward("failure");
			  }// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			
						request.getSession().removeAttribute("alPreauthDocs");
						request.getSession().removeAttribute("successMsgs");
						request.getSession().removeAttribute("notify");
					
						DynaActionForm frmPreAuthGeneral = (DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
						if(frmPreAuthGeneral == null || frmPreAuthGeneral.getString("preAuthSeqID").length()==0)	
						{
							TTKException expTTK = new TTKException();
							expTTK.setMessage("error.PreAuthorization.required");
							throw expTTK;
						}
						
						String preAuthSeqID= frmPreAuthGeneral.getString("preAuthSeqID");	
						String preAuthRecvTypeID= frmPreAuthGeneral.getString("preAuthRecvTypeID");
						String claimSeqId= frmPreAuthGeneral.getString("claimSeqId");
						
						DynaActionForm frmPreauthDocsUpload= (DynaActionForm) form;
						frmPreauthDocsUpload.set("filedescription", "");
						frmPreauthDocsUpload.set("caption",frmPreAuthGeneral.get("preAuthNo"));
						frmPreauthDocsUpload.set("preAuthSeqID",preAuthSeqID);
						frmPreauthDocsUpload.set("claimSeqId",claimSeqId);
						
						if("ONL1".equals(preAuthRecvTypeID))
							frmPreauthDocsUpload.set("buttonEnableYN","N");
						else
							frmPreauthDocsUpload.set("buttonEnableYN","Y");
						
						ArrayList alPreauthDocs = new ArrayList();
						
						PreAuthManager preAuthObject = this.getPreAuthManagerObject();		
						alPreauthDocs = preAuthObject.getPreauthDocsUploads(preAuthSeqID);
					
							
						if(!(alPreauthDocs.isEmpty()))
						{	
						//	MOUDocumentVO moudocObj = (MOUDocumentVO)alPreauthDocs.get(0);
							request.getSession().setAttribute("alPreauthDocs", alPreauthDocs);
							frmPreauthDocsUpload.set("claimSeqId",claimSeqId);
							request.getSession().setAttribute("frmPreauthDocsUpload",frmPreauthDocsUpload);
							return mapping.findForward("documentsUpload");
						}	
						else
						{
							frmPreauthDocsUpload.set("claimSeqId",claimSeqId);
							request.getSession().setAttribute("frmPreauthDocsUpload",frmPreauthDocsUpload);
			               return mapping.findForward("documentsUpload");
						}
					}//end of try
					catch(TTKException expTTK)
					{
						return this.processExceptions(request, mapping, expTTK);
					}//end of catch(TTKException expTTK)
					catch(Exception exp)
					{
						return this.processExceptions(request, mapping, new TTKException(exp,"strPreauthdetail"));
					}//end of catch(Exception exp)
				}
			  
			  
			  public ActionForward doPreauthHistoryFileDownload(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			    		HttpServletResponse response) throws Exception
			  {
				  			log.debug("inside PreauthGeneralAction doPreauthHistoryFileDownload()");
					 		String fileName=request.getParameter("file");
					 		String filaPath=TTKPropertiesReader.getPropertyValue("mouUploads");
					        
					        File file=new File(filaPath+fileName);
     				        if(!file.exists())
     				        {
					        	TTKException expTTK = new TTKException();
								expTTK.setMessage("error.file.not.exist");
						 		throw expTTK;
					         }
					         response.setContentType("application/txt");
					         response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			         
					         ServletOutputStream sos=response.getOutputStream();
					         FileInputStream fis=new FileInputStream(file);
					           byte[]fileData=new byte[fis.available()];
					           fis.read(fileData);
					           sos.write(fileData);
					           fis.close();
					           sos.flush();
					           sos.close();
							 return null;
		     }	// end of doPreauthHistoryFileDownload()	
			  
			  public ActionForward doCloseHistory(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
						throws Exception 
				{
					try 
					{
						log.debug("Inside PreAuthGenealAction doCloseHistory");
						setLinks(request);
						return this.getForward("ClaimGeneralScreen", mapping, request);
					}
					catch (TTKException expTTK) 
					{
						return this.processExceptions(request, mapping, expTTK);
					}
					catch (Exception exp) 
					{
						return this.processExceptions(request, mapping, new TTKException(
								exp, strPreauthdetail));
					}

				}	  
			  
			  
			  public ActionForward viewEpreauthDocs(ActionMapping mapping,ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
						try {
								setLinks(request);
								log.debug("Inside PreAuthGenealAction viewEpreauthDocs method ");
								PreAuthManager preAuthObject = this.getPreAuthManagerObject();
								
								String activityDtlSeqId = request.getParameter("activityDtlSeqId");
								String preAuthSeqID = request.getParameter("preAuthSeqID");
								String rowNO = request.getParameter("rowNO");
								
						//		System.out.println("activityDtlSeqId ="+ activityDtlSeqId + ":"+"preAuthSeqID = "+preAuthSeqID+" rowNO="+rowNO);
							
								Object[] observations = preAuthObject.getEpreauthDocs( preAuthSeqID == null ? 0 : Long.parseLong(preAuthSeqID),
										                                                    activityDtlSeqId == null ? 0 : Long.parseLong(activityDtlSeqId));
								
								Document document=(Document)observations[1];
								
								Node observNode=document.selectSingleNode("//Activity[ID='"+observations[0]+"']/Observation["+(new Integer(rowNO)+1 )+"]");
						//		System.out.println("observNode="+observNode);
								if(observNode!=null){
                        //        System.out.println("observNode xml="+observNode.asXML());
                                
                                Node valueNode=observNode.selectSingleNode("./Value");
                                if(valueNode!=null){
                                	
                                	String strPdfContent=valueNode.getText();
                                	if(strPdfContent!=null){
                                		 response.setContentType("application/pdf");
                                		  response.setHeader ("Content-Disposition", "attachment;filename=ObservationFile"+(new Integer(rowNO)+1 )+".pdf");
                                		 ServletOutputStream sos=response.getOutputStream();
                                		 
                                		 sos.write(DatatypeConverter.parseBase64Binary(strPdfContent));
                                		 sos.flush();
                                		 sos.close();
                                		 
                                		 return null;
                                	}//if(strPdfContent!=null){
                                	
                                }//if(valueNode!=null){
                                
                                
								}//if(observNode!=null){
								
								return mapping.findForward("addObservations");
								
						}// end of try
					catch (TTKException expTTK) {
						return this.processExceptions(request, mapping, expTTK);
					}// end of catch(TTKException expTTK)
					catch (Exception exp) {
						return this.processExceptions(request, mapping, new TTKException(
								exp, "addObservations"));
					}// end of catch(Exception exp)

				} 	
			  
			  public ActionForward viewObservsHistoryForPreauth(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
					try {
						setLinks(request);
						log.debug("Inside PreAuthGenealAction viewObservsHistory ");
						String activityDtlSeqId = (String) request
								.getParameter("activityDtlSeqId");
						String preAuthRecvTypeID = (String) request
								.getParameter("preAuthRecvTypeID");
						PreAuthManager preAuthObject = this.getPreAuthManagerObject();
						ArrayList<String[]> observations = preAuthObject
								.getAllObservDetails(new Long(activityDtlSeqId));
						request.getSession().setAttribute("observations",
								observations);
						request.setAttribute("preAuthRecvTypeID",preAuthRecvTypeID);
						return mapping.findForward("viewObservationDetailsPreauth");
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
			  
	private DynaActionForm setFormValues(PreAuthDetailVO preAuthDetailVO,ActionMapping mapping,HttpServletRequest request){
		DynaActionForm frmPreAuthGeneral=(DynaActionForm) FormUtils.setFormValues("frmPreAuthGeneral", preAuthDetailVO, this, mapping,request);
		frmPreAuthGeneral.set("diagnosisList", preAuthDetailVO.getDiagnosisList());
		frmPreAuthGeneral.set("activityList", preAuthDetailVO.getActivityList());
		frmPreAuthGeneral.set("shortfallList", preAuthDetailVO.getShortfallList());
		return frmPreAuthGeneral;
		
	}
	String uploadDetails="";
	 public ActionForward testUpload(ActionMapping mapping,ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
			
					request.setAttribute("errorDetails", "");
					if("y".equals(request.getParameter("page"))){
						
						return mapping.findForward("testUpload");
					}else if("r".equals(request.getParameter("page"))){
						if("y".equals(uploadDetails))
							request.setAttribute("errorDetails", "File Proccessing....");
							else if("c".equals(uploadDetails)) request.setAttribute("errorDetails", "Completed");
							else request.setAttribute("errorDetails", uploadDetails);
						return mapping.findForward("testUpload");
					}else if("y".equals(uploadDetails)){
						request.setAttribute("errorDetails", "File Proccessing....");
						return mapping.findForward("testUpload");
					}
					
					DynaActionForm actionForm=(DynaActionForm)form;
					FormFile formFile = (FormFile)actionForm.get("testUploadFile");
					
					request.setAttribute("errorDetails", "File Proccessing....");
					proccessFile(request, new String (formFile.getFileData()));
					
					return mapping.findForward("testUpload");
				}catch(Throwable throwable){
					throwable.printStackTrace();
					uploadDetails=throwable.getMessage();
					return mapping.findForward("testUpload");	
				}
	 }
	 
	 private void proccessFile(final HttpServletRequest request,final String fileContent){
		 Runnable runnable=new Runnable() {
			
			@Override
			public void run() {
				Connection conn = null;
				CallableStatement cStmtObject = null;
				try {
					 uploadDetails="y";
					conn = ResourceManager.getConnection();
					cStmtObject = (java.sql.CallableStatement)conn.prepareCall("{CALL intx.upload_test(?,?,?,?)}");
					SQLXML sqlxml=conn.createSQLXML();
					sqlxml.setString(fileContent);					
					cStmtObject.setSQLXML(1,sqlxml);
					cStmtObject.setString(2,request.getParameter("fileID"));
					cStmtObject.setString(3,request.getParameter("uploadType"));
					cStmtObject.registerOutParameter(4, Types.VARCHAR);
					cStmtObject.execute();
					uploadDetails="Completed="+cStmtObject.getString(4);
					conn.commit();
					
					
				}catch(Throwable throwable){
					throwable.printStackTrace();					
					uploadDetails=throwable.getMessage();
				}finally{
					try{
						if(cStmtObject!=null)cStmtObject.close();
						if(conn!=null)conn.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
		};
		new Thread(runnable).start();
	 }
	 public ActionForward doChangeCases(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			try {
				setLinks(request);
				log.debug("Inside PreauthGenealAction doChangeCases");
				return this.getForward(strPreauthdetail, mapping, request);
			}
			catch (TTKException expTTK) {
				return this.processExceptions(request, mapping, expTTK);
			}
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, strPreAuthDetail));
			}
		} 
}// end of viewBenefitDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)
