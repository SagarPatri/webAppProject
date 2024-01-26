/**
 * @ (#) MedicalAilmentAction.java May 10th, 2006
 * Project 	     : TTK HealthCare Services
 * File          : MedicalAilmentAction.java
 * Author        : Raghavendra T M
 * Company       : Span Systems Corporation
 * Date Created  : May 10th, 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
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
import com.ttk.business.preauth.PreAuthManager;
//added for KOC-Decoupling
import com.ttk.action.table.TableData;
//ended
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.preauth.AssociatedIllnessVO;
import com.ttk.dto.preauth.PreAuthAilmentVO;
import formdef.plugin.util.FormUtils;


/**
 * This class is reusable for adding the medical ailmenet information in  pre-auth and claims flow.
 */

public class MedicalAilmentAction extends TTKAction  {

	private static Logger log = Logger.getLogger( MedicalAilmentAction.class ); // Getting Logger for this Class file

	//  Action mapping forwards
	private static final String strEditMedicalAilment="ailmentdetails";
	private static final String strEditClaimMedicalAilment="claimsailmentdetails";
	private static final String strSaveAilmentDetails="saveailment";

	//Exception Message Identifier
	private static final String strMedicalAilmentError="Medical Ailment";

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
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilmentAction  doView");
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strFwdMode = null;
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			StringBuffer sbfCaption= new StringBuffer();
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
				//added for KOC-Decoupling
			TableData diagnosisData=null; 
			ArrayList alDiagnosis=new ArrayList();
			ArrayList<Object> alDiagnosisList = new ArrayList<Object>();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				strFwdMode = strEditMedicalAilment;
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(null);	//added for KOC-Decoupling								
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				strFwdMode = strEditClaimMedicalAilment;
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(lngSeqId); //added for KOC-Decoupling
			}//end of else if(strLink.equals("Claims"))
			//added for KOC-Decoupling
			alDiagnosis = preAuthManagerObject.getDiagnosisList(alDiagnosisList);//added for KOC-Decoupling
			diagnosisData = new TableData();
			diagnosisData.createTableInfo("DiagnosisTable",alDiagnosis);
			request.getSession().setAttribute("DiagnosisTable",diagnosisData);		
		
			
			DynaActionForm frmPreAuthMedical = (DynaActionForm)request.getSession().getAttribute("frmPreAuthMedical");
			
			String medicalAilmentCertificateShow = (String)frmPreAuthMedical.getString("showCertificateDateYN");
			
			//log.info("medicalAilmentCertificateShow--:"+medicalAilmentCertificateShow);
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",preauthAilmentVO.getDurationTypeID());
			//physiotherapy cr 1307
			if(preauthAilmentVO.getAilmentSeqID()!=null)
			{
				frmAilmentDetails.set("claimSubTypeID",preauthAilmentVO.getClaimSubTypeID());
			}
			else
			{
				frmAilmentDetails.set("claimSubTypeID",request.getSession().getAttribute("claimSubTypeIDs"));
			}
			//physiotherapy cr 1307
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			//koc for griavance
			
			if(preauthAilmentVO.getSeniorCitizenYN().equals("Y"))
			{
				
				//frmAilmentDetails.set("seniorCitizen","Please consider as a senior citizen");
				request.setAttribute("seniorCitizen","message.seniorCitizen");
			}
			//koc for griavance
			frmAilmentDetails.set("linkMode",strLinkMode);
			frmAilmentDetails.set("showCertificateDateYN",medicalAilmentCertificateShow);
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strFwdMode,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Save the Ailment Details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doSave");
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			AssociatedIllnessVO associatedIllnessVO = null;
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals("Claims"))
			frmAilmentDetails=(DynaActionForm)form;
			preauthAilmentVO =null;
			associatedIllnessVO = new AssociatedIllnessVO();
			preauthAilmentVO=(PreAuthAilmentVO)FormUtils.getFormValues(frmAilmentDetails, "frmAilmentDetails",this, mapping, request);
			ArrayList<Object> alassocIllnessVO = new ArrayList<Object>();
			Long[] lngSelectedAssocSeqID = (Long[])frmAilmentDetails.get("selectedAssocSeqID");
			String[] strSelectedAssocIllTypeID = (String[])frmAilmentDetails.get("selectedAssocIllTypeID");
			String[] strSelectedDurationTypeID = request.getParameterValues("durationTypeID");
			String[] strSelectedAssocIllDuration = request.getParameterValues("assocIllDuration");//SelectAssocIllTypeDuration//(Integer[])frmAilmentDetails.get("assocIllDuration");

			for(int i=0;i<lngSelectedAssocSeqID.length;i++)
			{
				associatedIllnessVO = new AssociatedIllnessVO();
				associatedIllnessVO.setAssocIllTypeID(strSelectedAssocIllTypeID[i]);
				associatedIllnessVO.setDurationTypeID(strSelectedDurationTypeID[i]);
				if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				{
					associatedIllnessVO.setAssocIllDuration(null);
				}//end of if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				else
				{
					associatedIllnessVO.setAssocIllDuration((strSelectedAssocIllDuration[i]));
				}//end of else
				associatedIllnessVO.setAssocSeqID(lngSelectedAssocSeqID[i]);
				alassocIllnessVO.add(associatedIllnessVO);
			}//end of for
			preauthAilmentVO.setAssocIllnessVO(alassocIllnessVO);
			preauthAilmentVO.setDurationTypeID(request.getParameter("durationTypeId"));
			if(strLink.equals("Pre-Authorization"))
			{
				preauthAilmentVO.setPreAuthSeqID(lngSeqId);
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				preauthAilmentVO.setClaimSeqID(lngSeqId);
			}//end of else if(strLink.equals("Claims"))
			preauthAilmentVO.setAilmentSeqID(TTKCommon.getLong(frmAilmentDetails.getString("ailmentSeqID")));
			preauthAilmentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			
			DynaActionForm frmPreAuthMedical = (DynaActionForm)request.getSession().getAttribute("frmPreAuthMedical");
			String medicalAilmentCertificateShow = (String)frmPreAuthMedical.getString("showCertificateDateYN");
						
			log.info("medicalAilmentCertificateShow--:"+medicalAilmentCertificateShow);
			
			Long iResult=new Long(0);
			if(preauthAilmentVO.getShowDiagnosisYN().contains("Y"))
			{
				String diagnosisDate = request.getParameter("diagnosisDate");
				
				if((diagnosisDate=="")||(diagnosisDate==null))
				{
					request.setAttribute("diagnosisvalidation","message.diagnosis");
				}
				else if(medicalAilmentCertificateShow.contains("Y"))
				{
					String certificateDate = request.getParameter("certificateDate");
					
					if((certificateDate=="")||(certificateDate==null))
					{
						request.setAttribute("certificatevalidation","message.certificateDate");
					}
					else
					{
						iResult = preAuthManagerObject.saveAilment(preauthAilmentVO);
					}
				}
				else
				{
					iResult = preAuthManagerObject.saveAilment(preauthAilmentVO);
				}
			}
			
			else
			{
			      iResult = preAuthManagerObject.saveAilment(preauthAilmentVO);
			}
					
			if((iResult!=0))
			{
				//setting updated message to add and edit modes	appropriatly
				if(!TTKCommon.checkNull(frmAilmentDetails.getString("ailmentSeqID")).equals(""))
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}// end of if(!(frmAilmentDetails.getString("batchNbr").equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(iResult!=0)
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",frmAilmentDetails.getString("durationTypeID"));
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			frmAilmentDetails.set("linkMode",strLinkMode);
			frmAilmentDetails.set("showCertificateDateYN",medicalAilmentCertificateShow);
            request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strSaveAilmentDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	//doSaveDiagnosis- added for KOC-Decoupling
	public ActionForward doSaveDiagnosis(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doSave");
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			ArrayList<Object> alDiagnosisList = new ArrayList<Object>();
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(null);	
				
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(lngSeqId);				
			}//end of else if(strLink.equals("Claims"))
			frmAilmentDetails=(DynaActionForm)form;
			preauthAilmentVO =null;
			preauthAilmentVO =(PreAuthAilmentVO)FormUtils.getFormValues(frmAilmentDetails, "frmAilmentDetails",this, mapping, request);			
			//preauthAilmentVO.setAilmentSeqID(TTKCommon.getLong(frmAilmentDetails.getString("ailmentSeqID")));
			preauthAilmentVO.setDiagnosisSeqId((Long)frmAilmentDetails.get("diagnosisSeqId"));
			preauthAilmentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			
			if(strLink.equals("Pre-Authorization"))
			{
				preauthAilmentVO.setPreAuthSeqID(lngSeqId);
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				preauthAilmentVO.setClaimSeqID(lngSeqId);
			}//end of else if(strLink.equals("Claims"))
			
			
			Long iResult = preAuthManagerObject.saveDiagnosis(preauthAilmentVO);
			if((iResult!=0))
			{
				//setting updated message to add and edit modes	appropriatly
				if(!TTKCommon.checkNull(frmAilmentDetails.getString("diagnosisSeqId")).equals(""))
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}// end of if(!(frmAilmentDetails.getString("batchNbr").equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(iResult!=0)
			ArrayList alDiagnosis=new ArrayList();
			TableData diagnosisData=null; //added for KOC-Decoupling
			alDiagnosis = preAuthManagerObject.getDiagnosisList(alDiagnosisList);
			diagnosisData = new TableData();
			diagnosisData.createTableInfo("DiagnosisTable",alDiagnosis);
			request.getSession().setAttribute("DiagnosisTable",diagnosisData);		
			//return this.getForward(strSaveAilmentDetails,mapping,request);
			
			//added for form refresh and get the previous data other than diagnosis
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",frmAilmentDetails.getString("durationTypeID"));
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			frmAilmentDetails.set("linkMode",strLinkMode);
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strSaveAilmentDetails,mapping,request);
			//ended
			//return mapping.findForward("dataentrysavediagnosis");
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)		
		
	}	
	
	//added for KOC- Decoupling
	/**check this method for changes for CR KOC-Decoupling
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
	
	public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.info("Inside  MedicalAilmentAction doDelete");
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			StringBuffer sbfCaption= new StringBuffer();
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			TableData diagnosisDataTable=null; //added for KOC-Decoupling
			if((request.getSession()).getAttribute("DiagnosisTable") != null)
			{
				diagnosisDataTable = (TableData)(request.getSession()).getAttribute("DiagnosisTable");
			}//end of if((request.getSession()).getAttribute("AilmentTable") != null)
			PreAuthAilmentVO preauthAilmentVO = new PreAuthAilmentVO();			
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				preauthAilmentVO=(PreAuthAilmentVO)((TableData)request.getSession().getAttribute("DiagnosisTable")).
				          getData().get(Integer.parseInt(request.getParameter("rownum")));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			preAuthManagerObject.deleteDiagnosis(preauthAilmentVO.getDiagnosisSeqId());
			ArrayList<Object> alDiagnosisList = new ArrayList<Object>();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(null);	
				
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(lngSeqId);
				
			}//end of else if(strLink.equals("Claims"))
			/*ArrayList alDiagnosis=new ArrayList();
			alDiagnosis = preAuthManagerObject.getDiagnosisList(alDiagnosisList);
			diagnosisTable = new TableData();
			diagnosisTable.createTableInfo("DiagnosisTable",alDiagnosis);
			request.getSession().setAttribute("DiagnosisTable",alDiagnosis);
		*/			
			ArrayList alDiagnosis=new ArrayList();
			alDiagnosis = preAuthManagerObject.getDiagnosisList(alDiagnosisList);
			diagnosisDataTable = new TableData();
			diagnosisDataTable.createTableInfo("DiagnosisTable",alDiagnosis);
			request.getSession().setAttribute("DiagnosisTable",diagnosisDataTable);	
			
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",preauthAilmentVO.getDurationTypeID());
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			frmAilmentDetails.set("linkMode",strLinkMode);		
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);			
			//return mapping.findForward("dataentrysavediagnosis");
			return this.getForward(strEditClaimMedicalAilment,mapping,request);
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strMedicalAilmentError));
		}//end of catch(Exception exp)
		
		
	}
	
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
	
	public ActionForward doViewDiagnosis(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		
		try{
			
			setLinks(request);
			log.debug("Inside MedicalAilmentAction doView");
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			PreAuthAilmentVO diagnosisVO = null;
			PreAuthManager PreAuthManagerObject=this.getPreAuthManagerObject();
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			String strRowNum=frmAilmentDetails.getString("rownum");
			TableData DiagnosisTable =(TableData) request.getSession().getAttribute("DiagnosisTable");
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				//strFwdMode = strEditMedicalAilment;
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
								
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                					sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				
			}//end of else if(strLink.equals("Claims"))
			if(!(TTKCommon.checkNull(strRowNum).equals("")))//edit flow
			{
				diagnosisVO =(PreAuthAilmentVO)DiagnosisTable.getRowInfo(Integer.parseInt((String)(frmAilmentDetails).get("rownum")));
				diagnosisVO = PreAuthManagerObject.getDiagnosisDetail(diagnosisVO.getDiagnosisSeqId());						
			}
			//frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",diagnosisVO,this,mapping,request);
			
			preauthAilmentVO = PreAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",preauthAilmentVO.getDurationTypeID());
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			frmAilmentDetails.set("linkMode",strLinkMode);		
			
			frmAilmentDetails.set("diagnosisSeqId",diagnosisVO.getDiagnosisSeqId());
			frmAilmentDetails.set("diagnosisDesc",diagnosisVO.getDiagnosisDesc());
			frmAilmentDetails.set("diagnosisType",diagnosisVO.getDiagnosisType());
			frmAilmentDetails.set("diagHospGenTypeId",diagnosisVO.getDiagHospGenTypeId());
			frmAilmentDetails.set("diagTreatmentPlanGenTypeId",diagnosisVO.getDiagTreatmentPlanGenTypeId());			
			frmAilmentDetails.set("freqOfVisit",diagnosisVO.getFreqOfVisit());
			frmAilmentDetails.set("noOfVisits",diagnosisVO.getNoOfVisits());	
			frmAilmentDetails.set("tariffGenTypeId",diagnosisVO.getTariffGenTypeId());
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strEditClaimMedicalAilment,mapping,request);
			//return mapping.findForward("dataentrysavediagnosis");
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)		
		
	}	
	/**
	 * This method is used to the Submit the Ailment.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doSubmit");
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			AssociatedIllnessVO associatedIllnessVO = null;
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
			}//end of else if(strLink.equals("Claims"))
			frmAilmentDetails=(DynaActionForm)form;
			associatedIllnessVO = new AssociatedIllnessVO();
			ArrayList<Object> alassocIllnessVO = new ArrayList<Object>();
			Long[] lngSelectedAssocSeqID = (Long[])frmAilmentDetails.get("selectedAssocSeqID");
			String[] strSelectedAssocIllTypeDesc = request.getParameterValues("assocIllTypeDesc");
			String[] strSelectedAssocIllDuration = request.getParameterValues("assocIllDuration");
			String[] strSelectedAssocIllTypeID = (String[])frmAilmentDetails.get("selectedAssocIllTypeID");
			String[] strSelectedDurationTypeID = request.getParameterValues("durationTypeID");
			for(int i=0;i<lngSelectedAssocSeqID.length;i++)
			{
				associatedIllnessVO = new AssociatedIllnessVO();
				associatedIllnessVO.setAssocIllTypeID(strSelectedAssocIllTypeID[i]);
				associatedIllnessVO.setDurationTypeID(strSelectedDurationTypeID[i]);
				associatedIllnessVO.setAssocIllTypeDesc(strSelectedAssocIllTypeDesc[i]);
				if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				{
					associatedIllnessVO.setAssocIllDuration(null);
				}//end of if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				else{
					associatedIllnessVO.setAssocIllDuration(strSelectedAssocIllDuration[i]);
				}//end of else
				associatedIllnessVO.setAssocSeqID(lngSelectedAssocSeqID[i]);
				alassocIllnessVO.add(associatedIllnessVO);
			}//end of for
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])alassocIllnessVO.toArray(new AssociatedIllnessVO[0]));
			frmAilmentDetails.set("linkMode",strLinkMode);
			return mapping.findForward("submitform");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to reset the Ailment values.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doReset");
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strFwdMode = null;
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				strFwdMode = strEditMedicalAilment;
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				strFwdMode = strEditClaimMedicalAilment;
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals("Claims"))
			preauthAilmentVO = new PreAuthAilmentVO();
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",preauthAilmentVO.getDurationTypeID());
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			frmAilmentDetails.set("linkMode",strLinkMode);
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strFwdMode,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthManager getPreAuthManagerObject() throws TTKException
	{
		PreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}//end of if(preAuthManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMedicalAilmentError);
		}//end of catch
		return preAuthManager;
	}//end of getPreAuthManagerObject()
}//end of MedicalAilmentAction