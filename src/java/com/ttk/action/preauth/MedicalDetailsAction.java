/**
 * @ (#) MedicalDetailsAction.java May 6, 2006
 * Project 		: TTK HealthCare Services
 * File 		: MedicalDetailsAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created : May 6, 2006
 *
 * @author 		: Pradeep R
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.preauth;

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
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.AdditionalHospitalDetailVO;
import com.ttk.dto.enrollment.PEDVO;
import com.ttk.dto.preauth.PreAuthAilmentVO;
import com.ttk.dto.preauth.PreAuthMedicalVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for displaying the medical information in pre-auth and claims flow.
 * This class also provides option for deletion on ICD/PCS Coding lists.
 */

public class MedicalDetailsAction extends TTKAction
{

	private static Logger log = Logger.getLogger(MedicalDetailsAction.class);

	//forwards
	private static final String  strPreAuthMedical="preauthmedical";
	private static final String  strCodingPreAuthMedical="codingpreauthmedical";
	private static final String  strClaimsMedical="claimsmedical";
	private static final String  strCodingClaimsMedical="codingclaimsmedical";
	private static final String  strPreAuthCleanupMedical="preauthcleanupmedical";
	private static final String  strClaimsCleanMedical="claimscleanupmedical";

	//Exception Message Identifier
	private static final String strMedical="preauthmedical";

	/**
	 * This method is used to navigate to the Details screen.
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
															HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalDetailsAction doDefault");
			String strForward="";
			StringBuffer strCaption=new StringBuffer();
			//This  block set's the message if the user selects the medical tab directly with out coping any thing
			//to web board
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			if(strLink.equals("Pre-Authorization"))
			{
				if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.required");
					throw expTTK;
				}//end of if(WebBoardHelper.checkWebBoardId(request)==null)
				if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"))
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.codingflow");
					throw expTTK;
				}//end of if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"))
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
				if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.codingflow");
					throw expTTK;
				}//end of if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding") || strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth")){
					if(CodingWebBoardHelper.checkWebBoardId(request)==null)
					{
						TTKException expTTK = new TTKException();
						expTTK.setMessage("error.PreAuthorization.required");
						throw expTTK;
					}//end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)
				}//end of if(strSubLink.equals("PreAuth"))
				if(strSubLink.equals("Claims")){
					if(CodingClaimsWebBoardHelper.checkWebBoardId(request)==null)
					{
						TTKException expTTK = new TTKException();
						expTTK.setMessage("error.Claims.required");
						throw expTTK;
					}//end of if(CodingClaimsWebBoardHelper.checkWebBoardId(request)==null)
				}//end of if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			PreAuthManager preAuthObject=this.getPreAuthManager();
			TableData medicalData=null;
			PreAuthMedicalVO preAuthMedicalVO=new PreAuthMedicalVO();
			AdditionalHospitalDetailVO addHospDetailVO=new AdditionalHospitalDetailVO();
			if(strLink.equals("Pre-Authorization"))
			{
				preAuthMedicalVO=preAuthObject.getMedicalDetail(PreAuthWebBoardHelper.getPreAuthSeqId(request),
																				TTKCommon.getUserSeqId(request));
				strCaption.append(" [ "+PreAuthWebBoardHelper.getClaimantName(request)+ " ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				preAuthMedicalVO=preAuthObject.getClaimMedicalDetail(ClaimsWebBoardHelper.getClaimsSeqId(request),
									TTKCommon.getUserSeqId(request));
				strCaption.append(" [ "+ClaimsWebBoardHelper.getClaimantName(request)+ " ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth")){
					preAuthMedicalVO=preAuthObject.getMedicalDetail(
							CodingWebBoardHelper.getPreAuthSeqId(request),
							TTKCommon.getUserSeqId(request));
					strCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
					if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					{
						strCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					//added for koc 1075
					if(strLink.equals("Coding"))
					{
						request.getSession().setAttribute("codingclaimChange","message.codingclaimChange");
					}
					//added for koc 1075
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					preAuthMedicalVO=preAuthObject.getClaimMedicalDetail(CodingClaimsWebBoardHelper.getClaimsSeqId(request),
							TTKCommon.getUserSeqId(request));
					strCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
					if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					{
						strCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					//added for koc 1075
					if(strLink.equals("Coding"))
					{
						request.getSession().setAttribute("codingclaimChange","message.codingclaimChange");
					}
					//added for koc 1075
				}//end of else if(strSubLink.equals("Claims"))
			}
			if(preAuthMedicalVO==null)  // when no information is there for corresponding preauth_seq_id then
			{
				preAuthMedicalVO=new PreAuthMedicalVO();
				PreAuthAilmentVO ailmentVO= new PreAuthAilmentVO();
				preAuthMedicalVO.setAilmentVO(ailmentVO);
				preAuthMedicalVO.setAddHospitalDetailVO(addHospDetailVO);
			}//end of if(preAuthMedicalVO==null)
			if(preAuthMedicalVO.getAilmentVO()==null)
			{
				PreAuthAilmentVO ailmentVO= new PreAuthAilmentVO();
				preAuthMedicalVO.setAilmentVO(ailmentVO);
			}//end of if(preAuthMedicalVO.getAilmentVO()==null)
			if(preAuthMedicalVO.getAddHospitalDetailVO()==null)
			{
				addHospDetailVO=new AdditionalHospitalDetailVO();
				preAuthMedicalVO.setAddHospitalDetailVO(addHospDetailVO);
			}//end of if(preAuthMedicalVO.getAilmentVO()==null)
			DynaActionForm frmPreAuthMedical= (DynaActionForm)FormUtils.setFormValues("frmPreAuthMedical",
																		preAuthMedicalVO,this,mapping,request);
			frmPreAuthMedical.set("ailmentVO", FormUtils.setFormValues("frmAliment",preAuthMedicalVO.getAilmentVO(),
																			this,mapping,request));
			frmPreAuthMedical.set("additionalHospitalDetailVO",FormUtils.setFormValues("frmAdditionalHospitalization",
													preAuthMedicalVO.getAddHospitalDetailVO(),this,mapping,request));
			frmPreAuthMedical.set("caption",strCaption.toString());
			//The variable showped is used either to display or hid the ped button in the ics/pcd coding
			//screen based on the memberseqid
			frmPreAuthMedical.set("showped",(PreAuthWebBoardHelper.getMemberSeqId(request)));
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth")){
					if(CodingWebBoardHelper.getMemberSeqId(request)!=null&&!"".equals(CodingWebBoardHelper.getMemberSeqId(request).toString().trim())){
						frmPreAuthMedical.set("showped",(CodingWebBoardHelper.getMemberSeqId(request)));
						//added for koc 1075
						if(strLink.equals("Coding"))
						{
							request.getSession().setAttribute("codingclaimChange","message.codingclaimChange");
						}
						//added for koc 1075
					}//end of if(CodingWebBoardHelper.getMemberSeqId(request)!=null&&!"".equals(CodingWebBoardHelper.getMemberSeqId(request).toString().trim())){
				}
				if(strSubLink.equals("Claims")){
					if(CodingClaimsWebBoardHelper.getMemberSeqId(request)!=null&&!"".equals(CodingClaimsWebBoardHelper.getMemberSeqId(request).toString().trim())){
						frmPreAuthMedical.set("showped",(CodingClaimsWebBoardHelper.getMemberSeqId(request)));
						//added for koc 1075
						if(strLink.equals("Coding"))
						{
							request.getSession().setAttribute("codingclaimChange","message.codingclaimChange");
						}
						//added for koc 1075
					}//end of if(CodingClaimsWebBoardHelper.getMemberSeqId(request)!=null&&!"".equals(CodingClaimsWebBoardHelper.getMemberSeqId(request).toString().trim())){
				}//end of if(strSubLink.equals("Claims")){
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			//koc for griavance
			
			if(preAuthMedicalVO.getSeniorCitizenYN().equals("Y"))
			{
				
				//frmPreAuthMedical.set("seniorCitizen","Please consider as a senior citizen");
				request.setAttribute("seniorCitizen","message.seniorCitizen");
			}
			//koc for griavance
			ArrayList alMember=new ArrayList();
			alMember=preAuthMedicalVO.getICDpackageList();
			frmPreAuthMedical.set("diagnosis",preAuthMedicalVO.getShowDiagnosisYN());
			
			medicalData = new TableData();
			medicalData.createTableInfo("MedicalTable",alMember);
			if((!"Coding".equals(strLink))||(!"Code CleanUp".equals(strLink)))
			{
            	((Column)((ArrayList)medicalData.getTitle()).get(5)).setVisibility(false);
            }//end of if((!"Coding".equals(strLink))||(!"Code CleanUp".equals(strLink)))
			request.getSession().setAttribute("MedicalTable",medicalData);
			if(strLink.equals("Pre-Authorization"))
			{
				frmPreAuthMedical.set("showClaims","N");
				strForward=strPreAuthMedical;
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				frmPreAuthMedical.set("showClaims","Y");
				strForward=strClaimsMedical;
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding"))
			{
				if(strSubLink.equals("PreAuth"))
				{
					frmPreAuthMedical.set("showClaims","N");
					strForward=strCodingPreAuthMedical;
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims"))
				{
					frmPreAuthMedical.set("showClaims","Y");
					strForward=strCodingClaimsMedical;
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			if(strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth"))
				{
					frmPreAuthMedical.set("showClaims","N");
					strForward=strPreAuthCleanupMedical;
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims"))
				{
					frmPreAuthMedical.set("showClaims","Y");
					strForward=strClaimsCleanMedical;
				}//end of else if(strSubLink.equals("Claims"))
			}//end ofif(strLink.equals("Code CleanUp"))
			
			request.getSession().setAttribute("frmPreAuthMedical",frmPreAuthMedical);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strMedical));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
														HttpServletResponse response) throws Exception {
		log.debug("Inside MedicalDetailsAction  doChangeWebBoard");
		return doDefault(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * This method is used to get the delete records from the grid screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside  MedicalDetailsAction  doDeleteList");
			String strForward="";
			//This  block set's the message if the user selects the medical tab directly with out coping any thing
			//to web board
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			if(strLink.equals("Pre-Authorization"))
			{
				if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.required");
					throw expTTK;
				}//end of if(WebBoardHelper.checkWebBoardId(request)==null)
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
			}//end of if(strLink.equals("Claims"))
			PreAuthManager preAuthObject=this.getPreAuthManager();
			TableData medicalData=null;
			ClaimManager claimManager=this.getClaimManagerObject();
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alParameter=new ArrayList<Object>();
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("MedicalTable")));
			alParameter.add("AIL");
			alParameter.add(sbfDeleteId.toString());
			if(strLink.equals("Pre-Authorization"))
			{
				alParameter.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request)); //PAT_ENROLL_DETAIL_SEQ_ID
				alParameter.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));	//PAT_GENERAL_DETAIL_SEQ_ID
				alParameter.add(TTKCommon.getUserSeqId(request));
				preAuthObject.deletePATGeneral(alParameter);
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				alParameter.add(ClaimsWebBoardHelper.getEnrollDetailSeqId(request));
				alParameter.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alParameter.add(TTKCommon.getUserSeqId(request));
				claimManager.deleteClaimGeneral(alParameter);
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth")){
					alParameter.add(CodingWebBoardHelper.getEnrollmentDetailId(request)); //PAT_ENROLL_DETAIL_SEQ_ID
					alParameter.add(CodingWebBoardHelper.getPreAuthSeqId(request));	//PAT_GENERAL_DETAIL_SEQ_ID
					alParameter.add(TTKCommon.getUserSeqId(request));
					preAuthObject.deletePATGeneral(alParameter);
				}//end of if(strSubLink.equals("PreAuth")){
				else if(strSubLink.equals("Claims")){
					alParameter.add(CodingClaimsWebBoardHelper.getEnrollDetailSeqId(request));
					alParameter.add(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
					alParameter.add(TTKCommon.getUserSeqId(request));
					claimManager.deleteClaimGeneral(alParameter);
				}//end of else if(strSubLink.equals("Claims")){
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			//re-quering
			PreAuthMedicalVO preAuthMedicalVO=new PreAuthMedicalVO();
			if(strLink.equals("Pre-Authorization"))
			{
				preAuthMedicalVO=preAuthObject.getMedicalDetail(PreAuthWebBoardHelper.getPreAuthSeqId(request),
																			TTKCommon.getUserSeqId(request));
				strForward= strPreAuthMedical ;
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				preAuthMedicalVO=preAuthObject.getClaimMedicalDetail(ClaimsWebBoardHelper.getClaimsSeqId(request),
																			TTKCommon.getUserSeqId(request));
				strForward=strClaimsMedical;
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding"))
			{
				if(strSubLink.equals("PreAuth")){
					preAuthMedicalVO=preAuthObject.getMedicalDetail(CodingWebBoardHelper.getPreAuthSeqId(request),
							TTKCommon.getUserSeqId(request));
					strForward= strCodingPreAuthMedical ;
				}
				else if(strSubLink.equals("Claims")){
					preAuthMedicalVO=preAuthObject.getClaimMedicalDetail(CodingClaimsWebBoardHelper.getClaimsSeqId(request),
							TTKCommon.getUserSeqId(request));
					strForward=strCodingClaimsMedical;
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			if(strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth"))
				{
					preAuthMedicalVO=preAuthObject.getMedicalDetail(CodingWebBoardHelper.getPreAuthSeqId(request),
							TTKCommon.getUserSeqId(request));
					strForward=strPreAuthCleanupMedical;
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims"))
				{
					preAuthMedicalVO=preAuthObject.getClaimMedicalDetail(CodingClaimsWebBoardHelper.getClaimsSeqId(request),
							TTKCommon.getUserSeqId(request));
					strForward=strClaimsCleanMedical;
				}//end of else if(strSubLink.equals("Claims"))
			}//end ofif(strLink.equals("Code CleanUp"))
			ArrayList alMember=new ArrayList();
			alMember=preAuthMedicalVO.getICDpackageList();
			medicalData = new TableData();
			medicalData.createTableInfo("MedicalTable",alMember);
			request.getSession().setAttribute("MedicalTable",medicalData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strMedical));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to get the delete record from the grid screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside  MedicalDetailsAction doDelete");
			String strForward="";
			//This  block set's the message if the user selects the medical tab directly with out coping any thing
			//to web board
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			if(strLink.equals("Pre-Authorization"))
			{
				if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.required");
					throw expTTK;
				}//end of if(WebBoardHelper.checkWebBoardId(request)==null)
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
			}//end of if(strLink.equals("Claims"))
			PreAuthManager preAuthObject=this.getPreAuthManager();
			TableData medicalData=null;
			DynaActionForm	frmICDPCSCoding=null;
			ClaimManager claimManager=this.getClaimManagerObject();
			frmICDPCSCoding = (DynaActionForm)request.getSession().getAttribute("frmICDPCSCoding");
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alParameter=new ArrayList<Object>();
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append("|").append(String.valueOf(frmICDPCSCoding.getString("PEDSeqID"))).append("|");
			alParameter.add("AIL");
			alParameter.add(sbfDeleteId.toString());
			if(strLink.equals("Pre-Authorization"))
			{
				alParameter.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request)); //PAT_ENROLL_DETAIL_SEQ_ID
				alParameter.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));	   //PAT_GENERAL_DETAIL_SEQ_ID
				alParameter.add(TTKCommon.getUserSeqId(request));
				preAuthObject.deletePATGeneral(alParameter);
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				alParameter.add(ClaimsWebBoardHelper.getEnrollDetailSeqId(request));
				alParameter.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alParameter.add(TTKCommon.getUserSeqId(request));
				claimManager.deleteClaimGeneral(alParameter);
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth")){
					alParameter.add(CodingWebBoardHelper.getEnrollmentDetailId(request)); //PAT_ENROLL_DETAIL_SEQ_ID
					alParameter.add(CodingWebBoardHelper.getPreAuthSeqId(request));	//PAT_GENERAL_DETAIL_SEQ_ID
					alParameter.add(TTKCommon.getUserSeqId(request));
					preAuthObject.deletePATGeneral(alParameter);
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					alParameter.add(CodingClaimsWebBoardHelper.getEnrollDetailSeqId(request));
					alParameter.add(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
					alParameter.add(TTKCommon.getUserSeqId(request));
					claimManager.deleteClaimGeneral(alParameter);
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			//re-quering
			PreAuthMedicalVO preAuthMedicalVO=new PreAuthMedicalVO();
			if(strLink.equals("Pre-Authorization"))
			{
				preAuthMedicalVO=preAuthObject.getMedicalDetail(PreAuthWebBoardHelper.getPreAuthSeqId(request),
																					TTKCommon.getUserSeqId(request));
				strForward= strPreAuthMedical ;
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				preAuthMedicalVO=preAuthObject.getClaimMedicalDetail(ClaimsWebBoardHelper.getClaimsSeqId(request),
																		TTKCommon.getUserSeqId(request));
				strForward=strClaimsMedical;
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding"))
			{
				if(strSubLink.equals("PreAuth")){
					preAuthMedicalVO=preAuthObject.getMedicalDetail(CodingWebBoardHelper.getPreAuthSeqId(request),
							TTKCommon.getUserSeqId(request));
					strForward= strCodingPreAuthMedical ;
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					preAuthMedicalVO=preAuthObject.getClaimMedicalDetail(CodingClaimsWebBoardHelper.getClaimsSeqId(request),
							TTKCommon.getUserSeqId(request));
					strForward=strCodingClaimsMedical;
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			if(strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth"))
				{
					preAuthMedicalVO=preAuthObject.getMedicalDetail(CodingWebBoardHelper.getPreAuthSeqId(request),
							TTKCommon.getUserSeqId(request));
					strForward=strPreAuthCleanupMedical;
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims"))
				{
					preAuthMedicalVO=preAuthObject.getClaimMedicalDetail(CodingClaimsWebBoardHelper.getClaimsSeqId(request),
							TTKCommon.getUserSeqId(request));
					strForward=strClaimsCleanMedical;
				}//end of else if(strSubLink.equals("Claims"))
			}//end ofif(strLink.equals("Code CleanUp"))
			ArrayList alMember=new ArrayList();
			alMember=preAuthMedicalVO.getICDpackageList();
			medicalData = new TableData();
			medicalData.createTableInfo("MedicalTable",alMember);
			request.getSession().setAttribute("MedicalTable",medicalData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strMedical));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns a string which contains the comma separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String populateDeleteId(HttpServletRequest request, TableData MedicalTable)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching
			//check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append("|").append(String.valueOf(((PEDVO)MedicalTable.getData().get(
															Integer.parseInt(strChk[i]))).getPEDSeqID().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((PEDVO)MedicalTable.getData().get(
															Integer.parseInt(strChk[i]))).getPEDSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData MedicalTable)

	/**
	 * @return preAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthManager getPreAuthManager() throws TTKException
	{
		PreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMedical);
		}//end of catch
		return preAuthManager;
	}//end getGroupRegnManagerObject()

	/**
	 * This method returns the claimManager session object for invoking DAO methods from it.
	 * @return claimManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ClaimManager getClaimManagerObject() throws TTKException
	{
		ClaimManager claimManager = null;
		try
		{
			if(claimManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMedical);
		}//end of catch
		return claimManager;
	}//end getClaimManagerObject()
}//end of class MedicalDetailsAction