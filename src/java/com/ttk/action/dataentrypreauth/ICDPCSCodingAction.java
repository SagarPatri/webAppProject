/**
 * @ (#) ICDPCSCodingAction.java Apr 17, 2006
 * Project 		: TTK HealthCare Services
 * File 		: ICDPCSCodingAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created : Apr 17, 2006
 *
 * @author 		: Pradeep R
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.dataentrypreauth;

import java.util.ArrayList;
import java.util.StringTokenizer;
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
import com.ttk.business.dataentryenrollment.ParallelMemberManager;
import com.ttk.business.dataentrypreauth.ParallelPreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ProcedureDetailVO;
import com.ttk.dto.administration.TariffItemVO;
import com.ttk.dto.enrollment.PEDVO;
import com.ttk.dto.preauth.ICDPCSVO;
import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for showing the list of Pre-Existing Diseases in prea-auth and claims flow.
 * This class also provides option for adding Pre-Existing Diseases information.
 */
public class ICDPCSCodingAction extends TTKAction {

	private static Logger log = Logger.getLogger(ICDPCSCodingAction.class);

	//Forwarding paths
	private static final String strIcdPcsCoding="icdpcscoding";
	private static final String strGetICDValue="geticdvalue";
	private static final String strICDList="dataentryicdlist";
	private static final String strClose="close";
	private static final String strAssociate="dataentryassociate";
	private static final String strSaveICDPCS="saveicdpcs";
	private static final String strSaveCodeIcdPcs = "savecodeicdpcs";
	private static final String strPackageItem="packageitem";
	private static final String strCodePreauthIcdPcsCoding = "codepreauthicdpcscoding";
	private static final String strCodeClaimsIcdPcsCoding = "dataentrycodeclaimsicdpcscoding";

	//claims forwards
	private static final String strClaimsIcdPcsCoding="dataentryclaimsicdpcscoding";
	private static final String strSaveClaimsICDPCS="dataentrysaveclaimsicdpcs";
	private static final String strSaveCodeClaimsIcdPcs = "dataentrysavecodeclaimsicdpcs";
	private static final String strPreauth="Pre-Authorization";
	private static final String strClaims="DataEntryClaims";

	//Exception Message Identifier
	private static final String strICDPCSError="icdpcscoding";

	/**
	 * This method is called from the struts framework.
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
																		HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside ICDPCSCodingAction doAdd");
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			DynaActionForm frmPreAuthMedical = (DynaActionForm)form;
			ArrayList alTemp=new ArrayList();
			ICDPCSVO icdPcsVO= new ICDPCSVO();
			TariffItemVO tariffItemVO = new TariffItemVO();
			String strRowNum=frmPreAuthMedical.getString("rownum");
			String strAddEdit="";
			icdPcsVO.setTariffItemVO(tariffItemVO);
			strAddEdit=" Add - ";
			DynaActionForm frmPreAuthMedicalDetail = (DynaActionForm)request.getSession().getAttribute("frmPreAuthMedical");
			DynaActionForm frmICDPCSCoding= (DynaActionForm)FormUtils.setFormValues("frmICDPCSCoding",icdPcsVO,
																								this,mapping,request);
			frmICDPCSCoding.set("tariffItemVO", FormUtils.setFormValues("frmAssociate",icdPcsVO.getTariffItemVO(),
																								this,mapping,request));
			frmICDPCSCoding.set("asscCodes",alTemp);
			frmICDPCSCoding.set("rownum",strRowNum);
			frmICDPCSCoding.set("codingReviewYN",frmPreAuthMedicalDetail.getString("codingReviewYN").trim());
			StringBuffer sbfCaption= new StringBuffer();
			/*if(strLink.equals(strPreauth))
			{
				sbfCaption.append(strAddEdit).append("[ ").append(PreAuthWebBoardHelper.getClaimantName(request)).
				append(" ] [ ").append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
					sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				frmICDPCSCoding.set("caption",sbfCaption.toString());
				strForward=strIcdPcsCoding;
			}//end of if(strLink.equals("Pre-Authorization"))
			*/if(strLink.equals(strClaims))
			{
				sbfCaption.append(strAddEdit).append("[ ").append(ClaimsWebBoardHelper.getClaimantName(request)).
									append(" ] [ ").append(ClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				frmICDPCSCoding.set("caption",sbfCaption.toString());
				strForward=strClaimsIcdPcsCoding;
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				/*if(strSubLink.equals("PreAuth")){
					sbfCaption.append(strAddEdit).append("[ ").append(CodingWebBoardHelper.getClaimantName(request)).
					append(" ] [ ").append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					{
						sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					frmICDPCSCoding.set("caption",sbfCaption.toString());
					strForward=strCodePreauthIcdPcsCoding;
				}
				else*/ if(strSubLink.equals("Claims")){
					sbfCaption.append(strAddEdit).append("[ ").append(CodingClaimsWebBoardHelper.getClaimantName(request)).
					append(" ] [ ").append(CodingClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					{
						sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					frmICDPCSCoding.set("caption",sbfCaption.toString());
					strForward=strCodeClaimsIcdPcsCoding;
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			request.getSession().setAttribute("frmICDPCSCoding",frmICDPCSCoding);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside ICDPCSCodingAction doView");
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ParallelPreAuthManager PreAuthManagerObject=this.getPreAuthManagerObject();
			TableData MedicalTable =(TableData) request.getSession().getAttribute("MedicalTable");
			DynaActionForm frmPreAuthMedical = (DynaActionForm)form;
			ArrayList alTemp=new ArrayList();
			ICDPCSVO icdPcsVO= new ICDPCSVO();
			TariffItemVO tariffItemVO = new TariffItemVO();
			String strRowNum=frmPreAuthMedical.getString("rownum");
			String strAddEdit="";
			if(!(TTKCommon.checkNull(strRowNum).equals("")))//edit flow
			{
				icdPcsVO=(ICDPCSVO)MedicalTable.getRowInfo(Integer.parseInt((String)(frmPreAuthMedical).get("rownum")));
				icdPcsVO=PreAuthManagerObject.getICDPCSDetail(icdPcsVO.getPEDSeqID(),TTKCommon.getUserSeqId(request));
				if(icdPcsVO.getPEDCodeID()==null)
				{
					//if the value of the ped code is null then, the ped code id is set to -1
					//so that others is selected in the drop down
					icdPcsVO.setPEDCodeID(new Long(-1));
				}//end of if(icdPcsVO.getPEDCodeID()==null)
				if(icdPcsVO.getTariffItemVO().getAssociateProcedureList() !=null)
				{
					alTemp=icdPcsVO.getTariffItemVO().getAssociateProcedureList();
				}//end of if(icdPcsVO.getTariffItemVO().getAssociateProcedureList() !=null)
				if(icdPcsVO.getTreatmentPlanTypeID().equals("MDC"))
				{
					icdPcsVO.setTariffItemVO(tariffItemVO);
				}//end of else
				strAddEdit=" Edit - ";
			}//end of if(!(TTKCommon.checkNull((String)frmPreAuthMedical.get("rownum")).equals("")))
			DynaActionForm frmICDPCSCoding= (DynaActionForm)FormUtils.setFormValues("frmICDPCSCoding",icdPcsVO,
																							this,mapping,request);
			frmICDPCSCoding.set("tariffItemVO", FormUtils.setFormValues("frmAssociate",icdPcsVO.getTariffItemVO(),
																						this,mapping,request));
			frmICDPCSCoding.set("asscCodes",alTemp);
			frmICDPCSCoding.set("rownum",strRowNum);
			DynaActionForm frmPreAuthMedicalDetail = (DynaActionForm)request.getSession().getAttribute("frmPreAuthMedical");
			frmICDPCSCoding.set("codingReviewYN",frmPreAuthMedicalDetail.getString("codingReviewYN").trim());
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals(strPreauth))
			{
				sbfCaption.append(strAddEdit).append("[ ").append(PreAuthWebBoardHelper.getClaimantName(request)).
									append(" ] [ ").append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				frmICDPCSCoding.set("caption",sbfCaption.toString());
				strForward=strIcdPcsCoding;
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals(strClaims))
			{
				sbfCaption.append(strAddEdit).append("[ ").append(ClaimsWebBoardHelper.getClaimantName(request)).
									append(" ] [ ").append(ClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				frmICDPCSCoding.set("caption",sbfCaption.toString());
				strForward=strClaimsIcdPcsCoding;
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				/*if(strSubLink.equals("PreAuth")){
					sbfCaption.append(strAddEdit).append("[ ").append(CodingWebBoardHelper.getClaimantName(request)).
					append(" ] [ ").append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					{
						sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					frmICDPCSCoding.set("caption",sbfCaption.toString());
					strForward=strCodePreauthIcdPcsCoding;
				}
				else*/ if(strSubLink.equals("Claims")){
					sbfCaption.append(strAddEdit).append("[ ").append(CodingClaimsWebBoardHelper.getClaimantName(request)).
					append(" ] [ ").append(CodingClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					{
						sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					frmICDPCSCoding.set("caption",sbfCaption.toString());
					log.info("CodingClaimsWebBoardHelper"+CodingClaimsWebBoardHelper.getAmmendmentYN(request));
					strForward=strCodeClaimsIcdPcsCoding;
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			request.getSession().setAttribute("frmICDPCSCoding",frmICDPCSCoding);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside ICDPCSCODINGAction doSave");
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ParallelPreAuthManager PreAuthManagerObject=this.getPreAuthManagerObject();
			DynaActionForm frmICDPCSCoding =(DynaActionForm)form;
			String strRowNum=frmICDPCSCoding.getString("rownum");
			ICDPCSVO icdPcsVO= null;
			String strModeValue=null;
			if(request.getParameter("primaryAilmentYN")==null)
			{
				frmICDPCSCoding.set("primaryAilmentYN","N");
			}//end of if(request.getParameter("primaryAilmentYN")==null)
			icdPcsVO=(ICDPCSVO)FormUtils.getFormValues(frmICDPCSCoding,this,mapping,request);
			ActionForm frmTraiffItem=(ActionForm)frmICDPCSCoding.get("tariffItemVO");
			TariffItemVO tariffItemVO=(TariffItemVO)FormUtils.getFormValues(frmTraiffItem,"frmAssociate",this,
																				mapping,request);
			String strSelectedProc=frmICDPCSCoding.getString("selectedProcedureCode");
			tariffItemVO.setAssociateProcedure(strSelectedProc);
			//Based on Treatment Plan and Tariff Item the value of package or procedure is set to null
			if(frmICDPCSCoding.getString("deleteSeqId").length()==1)
			{
				tariffItemVO.setDeleteProcedure("");
			}//end of  if(frmICDPCSCoding.getString("deleteSeqId").length()==1)
			else
			{
				tariffItemVO.setDeleteProcedure(frmICDPCSCoding.getString("deleteSeqId"));
			}//end of else
			icdPcsVO.setTariffItemVO(tariffItemVO);
			//if preauth then pass preauthseqid
			if(strLink.equals(strPreauth))
			{
				strModeValue="PAT";
				icdPcsVO.setPreAuthClaimSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			}//end of if(strLink.equals(strPreauth))
			//if claims then pass claims seq id
			if(strLink.equals(strClaims))
			{
				strModeValue="CLM";
				icdPcsVO.setPreAuthClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
			}//end of if(strLink.equals(strClaims))
			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				if(strSubLink.equals("PreAuth")){
					strModeValue="PAT";
					icdPcsVO.setPreAuthClaimSeqID(CodingWebBoardHelper.getPreAuthSeqId(request));
				}
				else if(strSubLink.equals("Claims")){
					strModeValue="CLM";
					icdPcsVO.setPreAuthClaimSeqID(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			icdPcsVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			String strTreatmentPlan= icdPcsVO.getTreatmentPlanTypeID();
			String strPakProc= icdPcsVO.getTariffItemVO().getTariffItemType();
			// if the Treatment Plan is medical then both the package and procedure will hold null
			if(strTreatmentPlan.equals("MDC"))
			{
				icdPcsVO.getTariffItemVO().setTariffItemId(null);
				//icdPcsVO.getTariffItemVO().setAssociateProcedureList(null);
				//icdPcsVO.getTariffItemVO().setTariffItemType(null);
			}//end of if(strTreatmentPlan.equals("MDC"))
			if((strTreatmentPlan.equals("SUR")) && (strPakProc.equals("NPK")))
			{
				icdPcsVO.getTariffItemVO().setTariffItemId(null);
			}//end of if((strTreatmentPlan.equals("SUR")) && (strPakProc.equals("NPK")))
			Long lnICDPCSSeqID =PreAuthManagerObject.saveICDPackage(icdPcsVO,strModeValue);
			StringBuffer sbfCaption= new StringBuffer();
			String strAddEdit="";
			if(lnICDPCSSeqID>0)
			{
				if(icdPcsVO.getPEDSeqID()!=null)//edit flow
				{
					request.setAttribute("updated","message.savedSuccessfully");
					strAddEdit=" Edit - ";
				}//end of if(!(TTKCommon.checkNull((String)formAddPED.get("rownum")).equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
					strAddEdit=" Add - ";
				}//end of else
				if(strLink.equals(strPreauth))
				{
					sbfCaption.append(strAddEdit).append("[ ").append(PreAuthWebBoardHelper.getClaimantName(request)).
									append(" ] [ ").append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
					{
	    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of if(strLink.equals("Pre-Authorization"))
				if(strLink.equals(strClaims))
				{
					sbfCaption.append(strAddEdit).append("[ ").append(ClaimsWebBoardHelper.getClaimantName(request)).
										append(" ] [ ").append(ClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					{
	                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of if(strLink.equals("Claims"))
				if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
				{
					/*if(strSubLink.equals("PreAuth")){
						sbfCaption.append(strAddEdit).append("[ ").append(CodingWebBoardHelper.getClaimantName(request)).
						append(" ] [ ").append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
						if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strSaveCodeIcdPcs;
					}
					else*/ if(strSubLink.equals("Claims")){
						sbfCaption.append(strAddEdit).append("[ ").append(CodingClaimsWebBoardHelper.getClaimantName(request)).
						append(" ] [ ").append(CodingClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
						if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strSaveCodeClaimsIcdPcs;
					}//end of else if(strSubLink.equals("Claims"))
				}//end of if(strLink.equals("Coding"))
				ArrayList alTemp=new ArrayList();
				icdPcsVO=PreAuthManagerObject.getICDPCSDetail(lnICDPCSSeqID,TTKCommon.getUserSeqId(request));
				if(icdPcsVO.getPEDCodeID()==null)
				{
					//if the value of the ped code is null then, the ped code id is set to -1 so that others is
					//selected in the drop down
					icdPcsVO.setPEDCodeID(new Long(-1));
				}//end of if(icdPcsVO.getPEDCodeID()==null)
				if(icdPcsVO.getTariffItemVO().getAssociateProcedureList() !=null)
				{
					alTemp=icdPcsVO.getTariffItemVO().getAssociateProcedureList();
				}//end of if(icdPcsVO.getTariffItemVO().getAssociateProcedureList() !=null)
				if(icdPcsVO.getTreatmentPlanTypeID().equals("MDC"))
				{
					icdPcsVO.setTariffItemVO(tariffItemVO);
				}//end of else
				frmICDPCSCoding= (DynaActionForm)FormUtils.setFormValues("frmICDPCSCoding",icdPcsVO,this,
																						mapping,request);
				frmICDPCSCoding.set("tariffItemVO", FormUtils.setFormValues("frmAssociate",icdPcsVO.getTariffItemVO(),
																				this,mapping,request));
				frmICDPCSCoding.set("asscCodes",alTemp);
				frmICDPCSCoding.set("caption",sbfCaption.toString());
				frmICDPCSCoding.set("rownum",strRowNum);
				DynaActionForm frmPreAuthMedicalDetail = (DynaActionForm)request.getSession().getAttribute("frmPreAuthMedical");
				frmICDPCSCoding.set("codingReviewYN",frmPreAuthMedicalDetail.getString("codingReviewYN").trim());
				request.getSession().setAttribute("frmICDPCSCoding",frmICDPCSCoding);
			}//end of if(iCount>0)
			modifyProcedureCode(request,frmICDPCSCoding);
			if(strLink.equals("Pre-Authorization"))
			{
				strForward=strSaveICDPCS;
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("DataEntryClaims"))
			{
				strForward=strSaveClaimsICDPCS;
			}//end of if(strLink.equals("Claims"))
			frmICDPCSCoding.set("frmChanged","");
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
																	HttpServletResponse response) throws Exception {
		try{
			setLinks(request);
			log.debug("Inside ICDPCSCODINGAction doReset");
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ParallelPreAuthManager PreAuthManagerObject=this.getPreAuthManagerObject();
			TableData MedicalTable =(TableData) request.getSession().getAttribute("MedicalTable");
			DynaActionForm frmPreAuthMedical = (DynaActionForm)form;
			ArrayList alTemp=new ArrayList();
			ICDPCSVO icdPcsVO= new ICDPCSVO();
			TariffItemVO tariffItemVO = new TariffItemVO();
			String strRowNum=frmPreAuthMedical.getString("rownum");
			String strAddEdit="";
			if(!(TTKCommon.checkNull(strRowNum).equals("")))//edit flow
			{
				icdPcsVO=(ICDPCSVO)MedicalTable.getRowInfo(Integer.parseInt((String)(frmPreAuthMedical).get("rownum")));
				icdPcsVO=PreAuthManagerObject.getICDPCSDetail(icdPcsVO.getPEDSeqID(),TTKCommon.getUserSeqId(request));
				if(icdPcsVO.getPEDCodeID()==null)
				{
					//if the value of the ped code is null then, the ped code id is set to -1 so that others is
					//selected in the drop down
					icdPcsVO.setPEDCodeID(new Long(-1));
				}//end of if(icdPcsVO.getPEDCodeID()==null)
				if(icdPcsVO.getTariffItemVO().getAssociateProcedureList() !=null)
				{
					alTemp=icdPcsVO.getTariffItemVO().getAssociateProcedureList();
				}//end of if(icdPcsVO.getTariffItemVO().getAssociateProcedureList() !=null)
				if(icdPcsVO.getTreatmentPlanTypeID().equals("MDC"))
				{
					icdPcsVO.setTariffItemVO(tariffItemVO);
				}//end of else
				strAddEdit=" Edit - ";
			}//end of if(!(TTKCommon.checkNull((String)frmPreAuthMedical.get("rownum")).equals("")))
			else{
				icdPcsVO.setTariffItemVO(tariffItemVO);
                strAddEdit=" Add - ";
			}//end of else
			DynaActionForm frmICDPCSCoding= (DynaActionForm)FormUtils.setFormValues("frmICDPCSCoding",icdPcsVO,
																			this,mapping,request);
			frmICDPCSCoding.set("tariffItemVO", FormUtils.setFormValues("frmAssociate",icdPcsVO.getTariffItemVO(),
																			this,mapping,request));
			frmICDPCSCoding.set("asscCodes",alTemp);
			frmICDPCSCoding.set("rownum",strRowNum);
			DynaActionForm frmPreAuthMedicalDetail = (DynaActionForm)request.getSession().getAttribute("frmPreAuthMedical");
			frmICDPCSCoding.set("codingReviewYN",frmPreAuthMedicalDetail.getString("codingReviewYN").trim());
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals(strPreauth))
			{
				sbfCaption.append(strAddEdit).append("[ ").append(PreAuthWebBoardHelper.getClaimantName(request)).
									append(" ] [ ").append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				frmICDPCSCoding.set("caption",sbfCaption.toString());
				strForward=strIcdPcsCoding;
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals(strClaims))
			{
				sbfCaption.append(strAddEdit).append("[ ").append(ClaimsWebBoardHelper.getClaimantName(request)).
										append(" ] [ ").append(ClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				frmICDPCSCoding.set("caption",sbfCaption.toString());
				strForward=strClaimsIcdPcsCoding;
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding"))
			{
				if(strSubLink.equals("PreAuth")){
					sbfCaption.append(strAddEdit).append("[ ").append(CodingWebBoardHelper.getClaimantName(request)).
					append(" ] [ ").append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					{
						sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					frmICDPCSCoding.set("caption",sbfCaption.toString());
					strForward=strCodePreauthIcdPcsCoding;
				}
				else if(strSubLink.equals("Claims")){
					sbfCaption.append(strAddEdit).append("[ ").append(CodingClaimsWebBoardHelper.getClaimantName(request)).
					append(" ] [ ").append(CodingClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					{
						sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					frmICDPCSCoding.set("caption",sbfCaption.toString());
					strForward=strCodeClaimsIcdPcsCoding;
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			request.getSession().setAttribute("frmICDPCSCoding",frmICDPCSCoding);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the ICD Codes.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doGetICDCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside ICDPCSCODINGAction doGetICDCode");
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ParallelMemberManager memberManagerObject=this.getMemberManagerObject();
			if(strLink.equals(strPreauth))
			{
				strForward=strGetICDValue;
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals(strClaims))
			{
				strForward=strClaimsIcdPcsCoding;
			}//end of if(strLink.equals("Claims"))
			if(strLink.equals("Coding"))
			{
				if(strSubLink.equals("PreAuth")){
					strForward=strCodePreauthIcdPcsCoding;
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					strForward=strCodeClaimsIcdPcsCoding;
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			DynaActionForm frmICDPCSCoding =(DynaActionForm)form;
			Long lngPedCodeId=TTKCommon.getLong(frmICDPCSCoding.getString("PEDCodeID"));
			if(lngPedCodeId==null)
			{
				lngPedCodeId=new Long(-1);
			}//end of if(lngPedCodeId==null)
			PEDVO PedVO=memberManagerObject.getDescriptionList(lngPedCodeId);
			if(PedVO!=null)
			{
				frmICDPCSCoding.set("otherDesc",PedVO.getDescription());
				frmICDPCSCoding.set("ICD",PedVO.getICDCode());
			}//end of if(PedVO!=null)
			else
			{
				frmICDPCSCoding.set("otherDesc","");
				frmICDPCSCoding.set("ICD","");
			}//end of else
			if(request.getParameter("primaryAilmentYN")==null)
			{
				frmICDPCSCoding.set("primaryAilmentYN","N");
			}//end of if(request.getParameter("primaryAilmentYN")==null)
			frmICDPCSCoding.set("frmChanged","changed");
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doGetICDCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
	public ActionForward doViewPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside ICDPCSCODINGAction doViewPackage");
			DynaActionForm frmICDPCSCoding =(DynaActionForm)form;
			frmICDPCSCoding.set("frmChanged","changed");
			if(request.getParameter("primaryAilmentYN")==null)
			{
				frmICDPCSCoding.set("primaryAilmentYN","N");
			}//end of if(request.getParameter("primaryAilmentYN")==null)
			return mapping.findForward(strPackageItem);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
													HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside ICDPCSCODINGAction doClose");
			return mapping.findForward(strClose);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doViewAssociateProcedure(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside ICDPCSCODINGAction doViewAssociateProcedure");
			DynaActionForm frmICDPCSCoding = (DynaActionForm)form;
			frmICDPCSCoding.set("frmChanged","changed");
			if(request.getParameter("primaryAilmentYN")==null)
			{
				frmICDPCSCoding.set("primaryAilmentYN","N");
			}//end of if(request.getParameter("primaryAilmentYN")==null)
			modifyProcedureCode(request,frmICDPCSCoding);
			return mapping.findForward(strAssociate);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doAssociateProcedure(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to Remove the Associate Procedure.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doRemoveProcedure(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside ICDPCSCodingAction doRemoveProcedure");
			DynaActionForm frmICDPCSCoding = (DynaActionForm)form;
			frmICDPCSCoding.set("frmChanged","changed");
			if(request.getParameter("primaryAilmentYN")==null)
			{
				frmICDPCSCoding.set("primaryAilmentYN","N");
			}//end of if(request.getParameter("primaryAilmentYN")==null)
			modifyProcedureCode(request,frmICDPCSCoding);
			return mapping.findForward(strIcdPcsCoding);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doRemoveProcedure(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
	public ActionForward doViewICDList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside ICDPCSCODINGAction doViewICDList");
			DynaActionForm frmICDPCSCoding =(DynaActionForm)form;
			
			
			String diagSeqId = request.getParameter("diagSeqId");
			request.getSession().setAttribute("diagSeqId",diagSeqId);
			
			frmICDPCSCoding.set("frmChanged","changed");
			if(request.getParameter("primaryAilmentYN")==null)
			{
				frmICDPCSCoding.set("primaryAilmentYN","N");
			}//end of if(request.getParameter("primaryAilmentYN")==null)
			return mapping.findForward(strICDList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strICDPCSError));
		}//end of catch(Exception exp)
	}//end of doViewICDList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * Returns the ParallelMemberManager session object for invoking methods on it.
	 * @return ParallelMemberManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ParallelMemberManager getMemberManagerObject() throws TTKException
	{
		ParallelMemberManager memberManager = null;
		try
		{
			if(memberManager == null)
			{
				InitialContext ctx = new InitialContext();
				memberManager = (ParallelMemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelMemberManagerBean!com.ttk.business.dataentryenrollment.ParallelMemberManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strICDPCSError);
		}//end of catch
		return memberManager;
	}//end getMemberManager()

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
			throw new TTKException(exp, strICDPCSError);
		}//end of catch
		return preAuthManager;
	}//end getMemberManager()

	/**
	 *  Remove the deleted procedure code from session object
	 *  @param request HttpServletRequest object which contains information about the selected procedure code
	 *  @param alNewAsscCode ArrayList which contains the new associated procedure code
	 */
	private void modifyProcedureCode(HttpServletRequest request,DynaActionForm frmICDPCSCoding)
	{
		ArrayList alasscProcedure=null;
		alasscProcedure=(ArrayList)frmICDPCSCoding.get("asscCodes");
		String strDeleteSeqId=request.getParameter("deleteSeqId");
		StringTokenizer stProcedure=null;
		String strKey="";
		ProcedureDetailVO procDetailVO=null;
		if(strDeleteSeqId!=null&&!strDeleteSeqId.equals(""))
		{
			stProcedure=new StringTokenizer(strDeleteSeqId,"|");
			while(stProcedure.hasMoreTokens())
			{
				strKey = stProcedure.nextToken();
				if(alasscProcedure != null && alasscProcedure.size() > 0)
				{
					for(int i=(alasscProcedure.size()-1); i >= 0; i--)
					{
						procDetailVO=(ProcedureDetailVO)alasscProcedure.get(i);
						if((procDetailVO.getProcedureID().toString()).equals(strKey))
						{
							alasscProcedure.remove(i);
						}//end of if((procDetailVO.getProcedureID().toString()).equals(strKey))
					}//end of for
				}//end of if(alasscProcedure != null && alasscProcedure.size() > 0)
			}//end of while(stProcedure.hasMoreTokens())
			frmICDPCSCoding.set("asscCodes",alasscProcedure);
		}//end of if(strDeleteSeqId!=null&&!strDeleteSeqId.equals(""))
	}//end of modifyProcedureCode(HttpServletRequest request,DynaActionForm frmICDPCSCoding)
}//end of ICDPCSCodingAction