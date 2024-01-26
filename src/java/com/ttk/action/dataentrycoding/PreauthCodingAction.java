/**
 * @ (#) ClaimsSearchAction.java Sep 13,2007
 * Project       : TTK HealthCare Services
 * File          : ClaimsSearchAction.java
 * Author        : Balakrishna Erram
 * Company       : Span Systems Corporation
 * Date Created  : Sep 13,2007

 * @author       : Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason :
 */

package com.ttk.action.dataentrycoding;

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
import com.ttk.business.dataentryclaims.ParallelClaimManager;
import com.ttk.business.dataentrycoding.ParallelCodingManager;
import com.ttk.business.dataentrypreauth.ParallelPreAuthManager;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ProcedureDetailVO;
import com.ttk.dto.administration.TariffItemVO;
import com.ttk.dto.claims.AdditionalHospitalDetailVO;
import com.ttk.dto.preauth.ICDPCSVO;
import com.ttk.dto.preauth.PreAuthAilmentVO;
import com.ttk.dto.preauth.PreAuthMedicalVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of Claims
 */

public class PreauthCodingAction extends TTKAction
{
	private static Logger log = Logger.getLogger(PreauthCodingAction.class);


	//Action mapping forwards.
	private static final  String strPreauthICDlist="dataentryPreauthICDList";
	private static final  String strPreauthCleanupICDlist="PreauthCleanupICDList";
	private static final  String strClaimsCleanupICDlist="ClaimsCleanupICDList";
	private static final String strClaimSearchError="hospitalsearch";
	private static final String strChangeWebBoard="doChangeWebBoard";

	//	forwards
	private static final String strSaveCodeIcdPcs = "savecodeicdpcs";
	private static final String strSaveCodeClaimsIcdPcs = "dataentrysavecodeclaimsicdpcs";
	private static final String strPreauthCleanupSave = "preauthcleanupsave";
	private static final String strClaimsCleanupSave = "claimscleanupsave";
	private static final String strCodePreauthIcdPcsCoding = "dataentrycodepreauthicdpcs";
	private static final String strCodeClaimsIcdPcsCoding ="dataentrycodeclaimsicdpcs";
	private static final String strCodingAssociate="dataentrycodingassociate";

	//Exception Message Identifier
	private static final String strICDPCSError="icdpcscoding";
	
	/**
	 * This method is used to initialize the search grid.
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
			HttpServletResponse response) throws Exception{
		try{

			setLinks(request);
			log.info("Inside PreauthCodingAction doDefault");
			String strForward="";
			String codeCompleteYN="";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			DynaActionForm frmICDPCSPolicy = (DynaActionForm)form;
			ICDPCSVO icdPcsVO = new ICDPCSVO();
			TariffItemVO tariffItemVO = new TariffItemVO();
			icdPcsVO.setTariffItemVO(tariffItemVO);
			StringBuffer strCaption=new StringBuffer();
			PreAuthMedicalVO preAuthMedicalVO=new PreAuthMedicalVO();
			ParallelPreAuthManager preAuthObject=this.getPreAuthManager();
			AdditionalHospitalDetailVO addHospDetailVO=new AdditionalHospitalDetailVO();

			//This  block set's the message if the user selects the medical tab directly with out coping any thing
			//to web board
			if(strLink.equals("Coding") || strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding"))
			{
				/*if(strSubLink.equals("PreAuth")){
					if(CodingWebBoardHelper.checkWebBoardId(request)==null)
					{
						TTKException expTTK = new TTKException();
						expTTK.setMessage("error.PreAuthorization.required");
						throw expTTK;
					}//end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)
				}//end of if(strSubLink.equals("PreAuth"))
				else*/ if(strSubLink.equals("Claims")){
					if (CodingClaimsWebBoardHelper.checkWebBoardId(request) == null)
					{
						TTKException expTTK = new TTKException();
						expTTK.setMessage("error.Claims.required");
						throw expTTK;
					}// end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)
				}//end of if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))

			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				/*if(strSubLink.equals("PreAuth")){
					strForward=strCodePreauthIcdPcsCoding;
				}//end of if(strSubLink.equals("PreAuth"))
				else*/ if(strSubLink.equals("Claims")){
					strForward=strCodeClaimsIcdPcsCoding;
				}//end of if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			else if(strLink.equals("Code CleanUp"))
			{
				strForward=strPreauthCleanupICDlist;
			}//end of else if(strLink.equals("Code CleanUp"))
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")||strChangeWebBoard.equals("doChangeWebBoard"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			ParallelCodingManager codingManager = this.getCodingManagerObject();
			TableData  medicalData =TTKCommon.getTableData(request);

			if(strLink.equals("Coding")||strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding")){
				/*if(strSubLink.equals("PreAuth")){
					strCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
					if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					{
						strCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of if(strSubLink.equals("PreAuth"))
				else */if(strSubLink.equals("Claims")){
					strCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
					if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					{
						strCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))

			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			ArrayList alMember=new ArrayList();
			ArrayList<Object> alAilmentList = new ArrayList<Object>();
			if(strSubLink.equals("PreAuth")){
				alAilmentList.add(CodingWebBoardHelper.getPreAuthSeqId(request));
				alAilmentList.add(null);
			}//end of if(strSubLink.equals("PreAuth"))
			else{
				alAilmentList.add(null);
				alAilmentList.add(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
			}//end of else

			Long lngClaimSeqId = CodingClaimsWebBoardHelper.getClaimsSeqId(request);
			
			ArrayList<Object> alDiagnosisList = new ArrayList<Object>();
			if(strSubLink.equals("Claims"))
			{			
				alDiagnosisList = codingManager.getDiagnosisList(lngClaimSeqId);				
			}
			ICDPCSVO icdPCSVO = new ICDPCSVO();
			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{		
				//long iResult = codingManager.saveDataEntryPromote(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
				alMember=codingManager.getAilmentList(alAilmentList);
				for(int i=0;i<alMember.size();i++)
				{  
					icdPCSVO = (ICDPCSVO)alMember.get(i);
					codeCompleteYN = icdPCSVO.getCodCompleteYN();
				}
			}//end ofif(strLink.equals("Coding"))
			else if(strLink.equals("Code CleanUp"))
			{
				alMember=codingManager.getCodeCleanupAilmentList(alAilmentList);
			}//end of else if(strLink.equals("Code CleanUp"))
			medicalData = new TableData();
			medicalData.createTableInfo("AilmentTable",alMember);
			request.getSession().setAttribute("AilmentTable",medicalData);
			
			
			frmICDPCSPolicy= (DynaActionForm)FormUtils.setFormValues("frmICDPCSPolicy",icdPcsVO,
					this,mapping,request);
			frmICDPCSPolicy.set("tariffItemVO", FormUtils.setFormValues("frmCodingAssociate",icdPcsVO.getTariffItemVO(),this,mapping,request));
			frmICDPCSPolicy.set("caption",strCaption.toString());
			frmICDPCSPolicy.set("codCompleteYN",icdPCSVO.getCodCompleteYN());
			request.getSession().setAttribute("frmICDPCSPolicy",frmICDPCSPolicy);
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding"))
			{
				/*if(strSubLink.equals("PreAuth")){
					preAuthMedicalVO=preAuthObject.getMedicalDetail(
							CodingWebBoardHelper.getPreAuthSeqId(request),
							TTKCommon.getUserSeqId(request));
					strCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
					if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					{
						strCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of if(strSubLink.equals("PreAuth"))
				else */if(strSubLink.equals("Claims")){
					preAuthMedicalVO=preAuthObject.getClaimMedicalDetail(CodingClaimsWebBoardHelper.getClaimsSeqId(request),
							TTKCommon.getUserSeqId(request));
				strCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
				if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of if
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
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
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding"))
			{
				if(strSubLink.equals("PreAuth")){
					if(CodingWebBoardHelper.getMemberSeqId(request)!=null&&!"".equals(CodingWebBoardHelper.getMemberSeqId(request).toString().trim())){
						frmPreAuthMedical.set("showped",(CodingWebBoardHelper.getMemberSeqId(request)));
					}//end of if
				}//end of if(strSubLink.equals("PreAuth"))
				if(strSubLink.equals("Claims")){
					if(CodingClaimsWebBoardHelper.getMemberSeqId(request)!=null&&!"".equals(CodingClaimsWebBoardHelper.getMemberSeqId(request).toString().trim())){
						frmPreAuthMedical.set("showped",(CodingClaimsWebBoardHelper.getMemberSeqId(request)));
					}//end of if
				}//end of if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			
			request.getSession().setAttribute("frmPreAuthMedical",frmPreAuthMedical);
			request.getSession().setAttribute("alDiagnosisList",alDiagnosisList);
			
			//return this.getForward(strForward, mapping, request);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimSearchError));
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
		log.debug("Inside PreauthCodingAction  doChangeWebBoard");
		return doDefault(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * This method is used to allow new ICD record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doNewICDCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception {
		log.info("Inside PreauthCodingAction  doNewICDCode");
		return doDefault(mapping,form,request,response);
	}//end of doNewICDCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside PreauthCodingAction doView");
			String strForward="";
			String codeCompleteYN = "";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ParallelPreAuthManager PreAuthManagerObject=this.getPreAuthManager();
			ParallelCodingManager codingManager = this.getCodingManagerObject();
			TableData AilmentTable =(TableData) request.getSession().getAttribute("AilmentTable");
			DynaActionForm frmICDPCSPolicy = (DynaActionForm)form;
			ArrayList alTemp=new ArrayList();
			ICDPCSVO icdPcsVO= null;
			String strRowNum=frmICDPCSPolicy.getString("rownum");
			//TariffItemVO tariffItemVO = new TariffItemVO();
			ArrayList alMember=new ArrayList();
			ArrayList<Object> alAilmentList = new ArrayList<Object>();
			if(strSubLink.equals("PreAuth")){
				alAilmentList.add(CodingWebBoardHelper.getPreAuthSeqId(request));
				alAilmentList.add(null);
			}//end of if(strSubLink.equals("PreAuth"))
			else{
				alAilmentList.add(null);
				alAilmentList.add(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
			}//end of else
			
			if(!(TTKCommon.checkNull(strRowNum).equals("")))//edit flow
			{
				icdPcsVO=(ICDPCSVO)AilmentTable.getRowInfo(Integer.parseInt((String)(frmICDPCSPolicy).get("rownum")));
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
				//icdPcsVO.setTariffItemVO(tariffItemVO);
			}//end of if(!(TTKCommon.checkNull((String)frmPreAuthMedical.get("rownum")).equals("")))
			
			ICDPCSVO icdPCSVO = new ICDPCSVO();
			alMember=codingManager.getAilmentList(alAilmentList);
			for(int i=0;i<alMember.size();i++)
			{  
				icdPCSVO = (ICDPCSVO)alMember.get(i);
				codeCompleteYN = icdPCSVO.getCodCompleteYN();
			}
			
			frmICDPCSPolicy= (DynaActionForm)FormUtils.setFormValues("frmICDPCSPolicy",icdPcsVO,
																							this,mapping,request);
			frmICDPCSPolicy.set("tariffItemVO", FormUtils.setFormValues("frmCodingAssociate",icdPcsVO.getTariffItemVO(),
																						this,mapping,request));
			frmICDPCSPolicy.set("asscCodes",alTemp);
			frmICDPCSPolicy.set("rownum",strRowNum);
			frmICDPCSPolicy.set("pEDCodeID",icdPcsVO.getPEDCodeID());
			frmICDPCSPolicy.set("sICDName",icdPcsVO.getDescription());
			frmICDPCSPolicy.set("sICDCode",icdPcsVO.getICDCode());
			frmICDPCSPolicy.set("codCompleteYN",icdPCSVO.getCodCompleteYN());
			frmICDPCSPolicy.set("diagSeqId",icdPcsVO.getDiagSeqId());

			StringBuffer sbfCaption= new StringBuffer();
			/*if(strSubLink.equals("PreAuth")){
				sbfCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
				if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
				{
					sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of if(strSubLink.equals("PreAuth"))
			else*/ if(strSubLink.equals("Claims")){
				sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
				if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				//log.info("CodingClaimsWebBoardHelper"+CodingClaimsWebBoardHelper.getAmmendmentYN(request));
			}//end of if(strSubLink.equals("Claims"))
			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				/*if(strSubLink.equals("PreAuth"))
				{
					strForward=strCodePreauthIcdPcsCoding;
				}//end of if(strSubLink.equals("PreAuth"))
				else */if(strSubLink.equals("Claims"))
				{
					strForward=strCodeClaimsIcdPcsCoding;
				}//end of if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			else if(strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth"))
				{
					strForward=strPreauthCleanupICDlist;
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims"))
				{
					strForward=strClaimsCleanupICDlist;
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			frmICDPCSPolicy.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmICDPCSPolicy",frmICDPCSPolicy);
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
			log.info("Inside PreauthCodingAction doSave");
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ParallelPreAuthManager PreAuthManagerObject=this.getPreAuthManager();
			DynaActionForm frmICDPCSPolicy =(DynaActionForm)form;
			
			ICDPCSVO icdPcsVO= null;
			String strModeValue=null;
			Long lnICDPCSSeqID=null;
			ArrayList alTemp=new ArrayList();
			icdPcsVO=(ICDPCSVO)FormUtils.getFormValues(frmICDPCSPolicy,this,mapping,request);
			ParallelCodingManager codingManager = this.getCodingManagerObject();
			
			if(frmICDPCSPolicy.get("pEDCodeID") == null )
			{
				//log.info("Inside ifrmICDPCSPolicy.get");
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.coding.PreAuthorization.pEDCodeID");
				throw expTTK;
			}//end of if(frmICDPCSPolicy.get("pEDCodeID") == null )
			
			icdPcsVO.setICDCode((String)frmICDPCSPolicy.get("sICDCode"));
			icdPcsVO.setDescription((String)frmICDPCSPolicy.get("sICDName"));
			icdPcsVO.setPEDCodeID((Long)frmICDPCSPolicy.get("pEDCodeID"));
			icdPcsVO.setDiagSeqId((Long)frmICDPCSPolicy.get("diagSeqId"));
			
			ActionForm frmTraiffItem=(ActionForm)frmICDPCSPolicy.get("tariffItemVO");
			TariffItemVO tariffItemVO=(TariffItemVO)FormUtils.getFormValues(frmTraiffItem,"frmCodingAssociate",this,
					mapping,request);
			String strSelectedProc=frmICDPCSPolicy.getString("selectedProcedureCode");
			//log.info("Selected Procedure is : "+strSelectedProc);
			tariffItemVO.setAssociateProcedure(strSelectedProc);
			alTemp = (ArrayList)frmICDPCSPolicy.get("asscCodes");
			//log.info("Size of arraylist is : "+alTemp.size());
			tariffItemVO.setAssociateProcedureList(alTemp);
			//Based on Treatment Plan and Tariff Item the value of package or procedure is set to null
			if(frmICDPCSPolicy.getString("deleteSeqId").length()==1)
			{
				tariffItemVO.setDeleteProcedure("");
			}//end of  if(frmICDPCSCoding.getString("deleteSeqId").length()==1)
			else
			{
				tariffItemVO.setDeleteProcedure(frmICDPCSPolicy.getString("deleteSeqId"));
			}//end of else
			icdPcsVO.setTariffItemVO(tariffItemVO);
			//if preauth then pass preauthseqid
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding"))
			{
				/*if(strSubLink.equals("PreAuth")){
					strModeValue="PAT";
					icdPcsVO.setPreAuthClaimSeqID(CodingWebBoardHelper.getPreAuthSeqId(request));
				}//end of if(strSubLink.equals("PreAuth"))
				else*/ if(strSubLink.equals("Claims")){
					strModeValue="CLM";
					icdPcsVO.setPreAuthClaimSeqID(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			
						
			icdPcsVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				lnICDPCSSeqID =PreAuthManagerObject.saveICDPackage(icdPcsVO,strModeValue);
			}//end of if(strLink.equals("Coding"))		
			
			else if(strLink.equals("Code CleanUp"))
			{
				lnICDPCSSeqID=codingManager.saveICDPackage(icdPcsVO,strModeValue);
			}//end of else if(strLink.equals("Code CleanUp"))
			//log.info("icdpcsseqid is : "+lnICDPCSSeqID);
			StringBuffer sbfCaption= new StringBuffer();
			
			if(lnICDPCSSeqID>0)
			{
				frmICDPCSPolicy.initialize(mapping);
				if(icdPcsVO.getPEDSeqID()!=null)//edit flow
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(!(TTKCommon.checkNull((String)formAddPED.get("rownum")).equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
				if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
				{
					/*if(strSubLink.equals("PreAuth")){
						sbfCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strSaveCodeIcdPcs;
					}//end of if(strSubLink.equals("PreAuth"))
					else */if(strSubLink.equals("Claims")){
						sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strSaveCodeClaimsIcdPcs;
					}//end of if(strSubLink.equals("Claims"))
				}//end of if(strLink.equals("Coding"))
				else if(strLink.equals("Code CleanUp"))
				{
					if(strSubLink.equals("PreAuth")){
						sbfCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strPreauthCleanupSave;
					}//end of if(strSubLink.equals("PreAuth"))
					else if(strSubLink.equals("Claims")){
						sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strClaimsCleanupSave;
					}//end of if(strSubLink.equals("Claims"))
				}//end of else if(strLink.equals("Code CleanUp"))
				ArrayList alMember=new ArrayList();
				ArrayList<Object> alAilmentList = new ArrayList<Object>();
				if(strSubLink.equals("PreAuth")){
					alAilmentList.add(CodingWebBoardHelper.getPreAuthSeqId(request));
					alAilmentList.add(null);
				}//end of if(strSubLink.equals("PreAuth"))
				else{
					alAilmentList.add(null);
					alAilmentList.add(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
				}//end of else
				
			
				TableData  ailmentTable =TTKCommon.getTableData(request);
				if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
				{	
					alMember=codingManager.getAilmentList(alAilmentList);
				}//end ofif(strLink.equals("Coding"))
				else if(strLink.equals("Code CleanUp"))
				{
					alMember=codingManager.getCodeCleanupAilmentList(alAilmentList);
					
				}//end of else if(strLink.equals("Code CleanUp"))
				ailmentTable = new TableData();
				ailmentTable.createTableInfo("AilmentTable",alMember);
				request.getSession().setAttribute("AilmentTable",ailmentTable);
			}//end of if(iCount>0)

			ICDPCSVO icdpcsVO = new ICDPCSVO();
			TariffItemVO tariffitemVO = new TariffItemVO();
			icdpcsVO.setTariffItemVO(tariffitemVO);
			frmICDPCSPolicy= (DynaActionForm)FormUtils.setFormValues("frmICDPCSPolicy",icdpcsVO,
					this,mapping,request);
			frmICDPCSPolicy.set("tariffItemVO", FormUtils.setFormValues("frmCodingAssociate",icdpcsVO.getTariffItemVO(),
				this,mapping,request));
			frmICDPCSPolicy.set("caption",sbfCaption.toString());
			frmICDPCSPolicy.set("frmChanged","");
			request.getSession().setAttribute("frmICDPCSPolicy",frmICDPCSPolicy);
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

	
	//added for CR KOC-Decoupling
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
	public ActionForward doDataEntryPromote(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.info("Inside PreauthCodingAction doDataEntryPromote");
			String strForward="";
			String codeCompleteYN="";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ParallelPreAuthManager PreAuthManagerObject=this.getPreAuthManager();
			DynaActionForm frmICDPCSPolicy =(DynaActionForm)form;
			
			ICDPCSVO icdPcsVO= null;
			String strModeValue=null;
			Long lnICDPCSSeqID=null;
			ArrayList alTemp=new ArrayList();
			icdPcsVO=(ICDPCSVO)FormUtils.getFormValues(frmICDPCSPolicy,this,mapping,request);
			ParallelCodingManager codingManager = this.getCodingManagerObject();
			
			/*if(frmICDPCSPolicy.get("pEDCodeID") == null )
			{
				//log.info("Inside ifrmICDPCSPolicy.get");
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.coding.PreAuthorization.pEDCodeID");
				throw expTTK;
			}//end of if(frmICDPCSPolicy.get("pEDCodeID") == null )
		*/	/*icdPcsVO.setICDCode((String)frmICDPCSPolicy.get("sICDCode"));
			icdPcsVO.setDescription((String)frmICDPCSPolicy.get("sICDName"));
			icdPcsVO.setPEDCodeID((Long)frmICDPCSPolicy.get("pEDCodeID"));
*/
			/*ActionForm frmTraiffItem=(ActionForm)frmICDPCSPolicy.get("tariffItemVO");
			TariffItemVO tariffItemVO=(TariffItemVO)FormUtils.getFormValues(frmTraiffItem,"frmCodingAssociate",this,
					mapping,request);
			String strSelectedProc=frmICDPCSPolicy.getString("selectedProcedureCode");
			//log.info("Selected Procedure is : "+strSelectedProc);
			tariffItemVO.setAssociateProcedure(strSelectedProc);
			alTemp = (ArrayList)frmICDPCSPolicy.get("asscCodes");
			//log.info("Size of arraylist is : "+alTemp.size());
			tariffItemVO.setAssociateProcedureList(alTemp);
			//Based on Treatment Plan and Tariff Item the value of package or procedure is set to null
			if(frmICDPCSPolicy.getString("deleteSeqId").length()==1)
			{
				tariffItemVO.setDeleteProcedure("");
			}//end of  if(frmICDPCSCoding.getString("deleteSeqId").length()==1)
			else
			{
				tariffItemVO.setDeleteProcedure(frmICDPCSPolicy.getString("deleteSeqId"));
			}//end of else
			icdPcsVO.setTariffItemVO(tariffItemVO);
			*///if preauth then pass preauthseqid
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding"))
			{
				/*if(strSubLink.equals("PreAuth")){
					strModeValue="PAT";
					icdPcsVO.setPreAuthClaimSeqID(CodingWebBoardHelper.getPreAuthSeqId(request));
				}//end of if(strSubLink.equals("PreAuth"))
				else*/ if(strSubLink.equals("Claims")){
					strModeValue="CLM";
					icdPcsVO.setPreAuthClaimSeqID(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			
						
			icdPcsVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			/*if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				lnICDPCSSeqID =PreAuthManagerObject.saveICDPackage(icdPcsVO,strModeValue);
			}//end of if(strLink.equals("Coding"))		
			
			else if(strLink.equals("Code CleanUp"))
			{
				lnICDPCSSeqID=codingManager.saveICDPackage(icdPcsVO,strModeValue);
			}//end of else if(strLink.equals("Code CleanUp"))
			//log.info("icdpcsseqid is : "+lnICDPCSSeqID);
			*/StringBuffer sbfCaption= new StringBuffer();
			
			//if(lnICDPCSSeqID>0)
			//{
				//frmICDPCSPolicy.initialize(mapping);
				/*if(icdPcsVO.getPEDSeqID()!=null)//edit flow
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(!(TTKCommon.checkNull((String)formAddPED.get("rownum")).equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			*/	if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
				{
					/*if(strSubLink.equals("PreAuth")){
						sbfCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strSaveCodeIcdPcs;
					}//end of if(strSubLink.equals("PreAuth"))
					else */if(strSubLink.equals("Claims")){
						sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strSaveCodeClaimsIcdPcs;
					}//end of if(strSubLink.equals("Claims"))
				}//end of if(strLink.equals("Coding"))
			/*	else if(strLink.equals("Code CleanUp"))
				{
					if(strSubLink.equals("PreAuth")){
						sbfCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strPreauthCleanupSave;
					}//end of if(strSubLink.equals("PreAuth"))
					else if(strSubLink.equals("Claims")){
						sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strClaimsCleanupSave;
					}//end of if(strSubLink.equals("Claims"))
				}//end of else if(strLink.equals("Code CleanUp"))
	*/			ArrayList alMember=new ArrayList();
				ArrayList<Object> alAilmentList = new ArrayList<Object>();
				if(strSubLink.equals("PreAuth")){
					alAilmentList.add(CodingWebBoardHelper.getPreAuthSeqId(request));
					alAilmentList.add(null);
				}//end of if(strSubLink.equals("PreAuth"))
				else{
					alAilmentList.add(null);
					alAilmentList.add(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
				}//end of else
				
				TableData  ailmentTable =TTKCommon.getTableData(request);
				if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
				{		
					
					long iResult = codingManager.saveDataEntryPromote(CodingClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
					alMember=codingManager.getAilmentList(alAilmentList);
					for(int i=0;i<alMember.size();i++)
					{  
						icdPcsVO = (ICDPCSVO)alMember.get(i);
						codeCompleteYN = icdPcsVO.getCodCompleteYN();
					}
				}//end ofif(strLink.equals("Coding"))
			/*	else if(strLink.equals("Code CleanUp"))
				{
					alMember=codingManager.getCodeCleanupAilmentList(alAilmentList);
					
				}//end of else if(strLink.equals("Code CleanUp"))
			*/	ailmentTable = new TableData();
				ailmentTable.createTableInfo("AilmentTable",alMember);
				request.getSession().setAttribute("AilmentTable",ailmentTable);
			//}//end of if(iCount>0)
			
			ICDPCSVO icdpcsVO = new ICDPCSVO();
			/*TariffItemVO tariffitemVO = new TariffItemVO();
			icdpcsVO.setTariffItemVO(tariffitemVO);
		*/				
			frmICDPCSPolicy= (DynaActionForm)FormUtils.setFormValues("frmICDPCSPolicy",icdpcsVO,
					this,mapping,request);
			
			/*frmICDPCSPolicy.set("tariffItemVO", FormUtils.setFormValues("frmCodingAssociate",icdpcsVO.getTariffItemVO(),
				this,mapping,request));
		*/	frmICDPCSPolicy.set("caption",sbfCaption.toString());
			frmICDPCSPolicy.set("frmChanged","");
			frmICDPCSPolicy.set("codCompleteYN",icdPcsVO.getCodCompleteYN());
			request.getSession().setAttribute("frmICDPCSPolicy",frmICDPCSPolicy);
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
	//ended
	
	
	//added for CR KOC-Decoupling
	/**
	 * This method is used to add/update the Promote status record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDataEntryRevert(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.info("Inside PreauthCodingAction doDataEntryRevert");
			String strForward="";
			String codeCompleteYN="";
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ParallelPreAuthManager PreAuthManagerObject=this.getPreAuthManager();
			DynaActionForm frmICDPCSPolicy =(DynaActionForm)form;
			
			ICDPCSVO icdPcsVO= null;
			String strModeValue=null;
			Long lnICDPCSSeqID=null;
			ArrayList alTemp=new ArrayList();
			icdPcsVO=(ICDPCSVO)FormUtils.getFormValues(frmICDPCSPolicy,this,mapping,request);
			ParallelCodingManager codingManager = this.getCodingManagerObject();
			
		/*	if(frmICDPCSPolicy.get("pEDCodeID") == null )
			{
				//log.info("Inside ifrmICDPCSPolicy.get");
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.coding.PreAuthorization.pEDCodeID");
				throw expTTK;
			}//end of if(frmICDPCSPolicy.get("pEDCodeID") == null )
		*/	/*icdPcsVO.setICDCode((String)frmICDPCSPolicy.get("sICDCode"));
			icdPcsVO.setDescription((String)frmICDPCSPolicy.get("sICDName"));
			icdPcsVO.setPEDCodeID((Long)frmICDPCSPolicy.get("pEDCodeID"));
*/
			/*ActionForm frmTraiffItem=(ActionForm)frmICDPCSPolicy.get("tariffItemVO");
			TariffItemVO tariffItemVO=(TariffItemVO)FormUtils.getFormValues(frmTraiffItem,"frmCodingAssociate",this,
					mapping,request);
			String strSelectedProc=frmICDPCSPolicy.getString("selectedProcedureCode");
			//log.info("Selected Procedure is : "+strSelectedProc);
			tariffItemVO.setAssociateProcedure(strSelectedProc);
			alTemp = (ArrayList)frmICDPCSPolicy.get("asscCodes");
			//log.info("Size of arraylist is : "+alTemp.size());
			tariffItemVO.setAssociateProcedureList(alTemp);
			//Based on Treatment Plan and Tariff Item the value of package or procedure is set to null
			if(frmICDPCSPolicy.getString("deleteSeqId").length()==1)
			{
				tariffItemVO.setDeleteProcedure("");
			}//end of  if(frmICDPCSCoding.getString("deleteSeqId").length()==1)
			else
			{
				tariffItemVO.setDeleteProcedure(frmICDPCSPolicy.getString("deleteSeqId"));
			}//end of else
			icdPcsVO.setTariffItemVO(tariffItemVO);
			//if preauth then pass preauthseqid
		*/	if(strLink.equals("Coding")||strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding"))
			{
				/*if(strSubLink.equals("PreAuth")){
					strModeValue="PAT";
					icdPcsVO.setPreAuthClaimSeqID(CodingWebBoardHelper.getPreAuthSeqId(request));
				}//end of if(strSubLink.equals("PreAuth"))
				else*/ if(strSubLink.equals("Claims")){
					strModeValue="CLM";
					icdPcsVO.setPreAuthClaimSeqID(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			
						
			icdPcsVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			/*if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				lnICDPCSSeqID =PreAuthManagerObject.saveICDPackage(icdPcsVO,strModeValue);
			}//end of if(strLink.equals("Coding"))		
			
			else if(strLink.equals("Code CleanUp"))
			{
				lnICDPCSSeqID=codingManager.saveICDPackage(icdPcsVO,strModeValue);
			}//end of else if(strLink.equals("Code CleanUp"))
			//log.info("icdpcsseqid is : "+lnICDPCSSeqID);
		*/	StringBuffer sbfCaption= new StringBuffer();
			
			//if(lnICDPCSSeqID>0)
			//{
				frmICDPCSPolicy.initialize(mapping);
				/*if(icdPcsVO.getPEDSeqID()!=null)//edit flow
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(!(TTKCommon.checkNull((String)formAddPED.get("rownum")).equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			*/	if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
				{
					/*if(strSubLink.equals("PreAuth")){
						sbfCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strSaveCodeIcdPcs;
					}//end of if(strSubLink.equals("PreAuth"))
					else */if(strSubLink.equals("Claims")){
						sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strSaveCodeClaimsIcdPcs;
					}//end of if(strSubLink.equals("Claims"))
				}//end of if(strLink.equals("Coding"))
			/*	else if(strLink.equals("Code CleanUp"))
				{
					if(strSubLink.equals("PreAuth")){
						sbfCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strPreauthCleanupSave;
					}//end of if(strSubLink.equals("PreAuth"))
					else if(strSubLink.equals("Claims")){
						sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
						if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						{
							sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
						}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
						strForward=strClaimsCleanupSave;
					}//end of if(strSubLink.equals("Claims"))
				}//end of else if(strLink.equals("Code CleanUp"))
		*/		ArrayList alMember=new ArrayList();
				ArrayList<Object> alAilmentList = new ArrayList<Object>();
				if(strSubLink.equals("PreAuth")){
					alAilmentList.add(CodingWebBoardHelper.getPreAuthSeqId(request));
					alAilmentList.add(null);
				}//end of if(strSubLink.equals("PreAuth"))
				else{
					alAilmentList.add(null);
					alAilmentList.add(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
				}//end of else
				
				TableData  ailmentTable =TTKCommon.getTableData(request);
				if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
				{		
					
					long iResult = codingManager.saveDataEntryRevert(CodingClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
					alMember=codingManager.getAilmentList(alAilmentList);
					for(int i=0;i<alMember.size();i++)
					{
						icdPcsVO = (ICDPCSVO)alMember.get(i);
						codeCompleteYN = icdPcsVO.getCodCompleteYN();
					}
				}//end ofif(strLink.equals("Coding"))
				else if(strLink.equals("Code CleanUp"))
				{
					alMember=codingManager.getCodeCleanupAilmentList(alAilmentList);
					
				}//end of else if(strLink.equals("Code CleanUp"))
				ailmentTable = new TableData();
				ailmentTable.createTableInfo("AilmentTable",alMember);
				request.getSession().setAttribute("AilmentTable",ailmentTable);
			//}//end of if(iCount>0)

			frmICDPCSPolicy= (DynaActionForm)FormUtils.setFormValues("frmICDPCSPolicy",icdPcsVO,
					this,mapping,request);
			ICDPCSVO icdpcsVO = new ICDPCSVO();
			/*TariffItemVO tariffitemVO = new TariffItemVO();
			icdpcsVO.setTariffItemVO(tariffitemVO);
		*/	frmICDPCSPolicy= (DynaActionForm)FormUtils.setFormValues("frmICDPCSPolicy",icdpcsVO,
					this,mapping,request);
		/*	frmICDPCSPolicy.set("tariffItemVO", FormUtils.setFormValues("frmCodingAssociate",icdpcsVO.getTariffItemVO(),
				this,mapping,request));
		*/	frmICDPCSPolicy.set("caption",sbfCaption.toString());
			frmICDPCSPolicy.set("frmChanged","");
			frmICDPCSPolicy.set("codCompleteYN",icdPcsVO.getCodCompleteYN());
			request.getSession().setAttribute("frmICDPCSPolicy",frmICDPCSPolicy);
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
	//ended

	
	
	
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
			return doDefault(mapping,form,request,response);
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
	public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.info("Inside  PreauthCodingAction doDelete");
			String strForward="";
			StringBuffer sbfCaption= new StringBuffer();
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			if(strSubLink.equals("PreAuth")){
				if(CodingWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.required");
					throw expTTK;
				}//end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)
			}//end of if(strSubLink.equals("PreAuth"))
			if(strSubLink.equals("Claims")){
				if (CodingClaimsWebBoardHelper.checkWebBoardId(request) == null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}// end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)
			}//end of if(strSubLink.equals("Claims"))
			TableData  ailmentTable =null;

			if((request.getSession()).getAttribute("AilmentTable") != null)
			{
				ailmentTable = (TableData)(request.getSession()).getAttribute("AilmentTable");
			}//end of if((request.getSession()).getAttribute("AilmentTable") != null)
			ParallelPreAuthManager preAuthObject=this.getPreAuthManager();
			ParallelClaimManager claimManager=this.getClaimManagerObject();
			ParallelCodingManager codingManager = this.getCodingManagerObject();
			DynaActionForm frmICDPCSPolicy = null;

			frmICDPCSPolicy = (DynaActionForm)request.getSession().getAttribute("frmICDPCSPolicy");
			ICDPCSVO icdpcsVO = new ICDPCSVO();
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				icdpcsVO=(ICDPCSVO)((TableData)request.getSession().getAttribute("AilmentTable")).
				          getData().get(Integer.parseInt(request.getParameter("rownum")));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))

			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alParameter=new ArrayList<Object>();
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append("|").append(icdpcsVO.getPEDSeqID()).append("|");
			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				alParameter.add("AIL");
				alParameter.add(sbfDeleteId.toString());
				/*if(strSubLink.equals("PreAuth")){
					alParameter.add(CodingWebBoardHelper.getEnrollmentDetailId(request)); //PAT_ENROLL_DETAIL_SEQ_ID
					alParameter.add(CodingWebBoardHelper.getPreAuthSeqId(request));	//PAT_GENERAL_DETAIL_SEQ_ID
					alParameter.add(TTKCommon.getUserSeqId(request));
					preAuthObject.deletePATGeneral(alParameter);
				}//end of if(strSubLink.equals("PreAuth"))
				else */if(strSubLink.equals("Claims")){
					alParameter.add(CodingClaimsWebBoardHelper.getEnrollDetailSeqId(request));
					alParameter.add(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
					alParameter.add(TTKCommon.getUserSeqId(request));
					claimManager.deleteClaimGeneral(alParameter);
				}//end of if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			else if(strLink.equals("Code CleanUp"))
			{
				alParameter.add(sbfDeleteId.toString());
				alParameter.add(TTKCommon.getUserSeqId(request));
				codingManager.deleteCleanupICD(alParameter);
			}//end of else if(strLink.equals("Code CleanUp"))
			ArrayList alMember=new ArrayList();
			ArrayList<Object> alAilmentList = new ArrayList<Object>();
			if(strSubLink.equals("PreAuth")){
				alAilmentList.add(CodingWebBoardHelper.getPreAuthSeqId(request));
				alAilmentList.add(null);
			}//end of if(strSubLink.equals("PreAuth"))
			else{
				alAilmentList.add(null);
				alAilmentList.add(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
			}//end of else
			if(strSubLink.equals("PreAuth")){
				sbfCaption.append(" [ "+CodingWebBoardHelper.getClaimantName(request)+ " ]");
				if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
				{
					sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of if(strSubLink.equals("PreAuth"))
			else if(strSubLink.equals("Claims")){
				sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getClaimantName(request)+ " ]");
				if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of if(strSubLink.equals("Claims"))

			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{		
				alMember=codingManager.getAilmentList(alAilmentList);
			}//end ofif(strLink.equals("Coding"))
			else if(strLink.equals("Code CleanUp"))
			{
				alMember=codingManager.getCodeCleanupAilmentList(alAilmentList);
			}//end of else if(strLink.equals("Code CleanUp"))
			ailmentTable = new TableData();
			ailmentTable.createTableInfo("AilmentTable",alMember);
			request.getSession().setAttribute("AilmentTable",ailmentTable);
			ICDPCSVO icdPcsVO = new ICDPCSVO();
			TariffItemVO tariffItemVO = new TariffItemVO();
			icdPcsVO.setTariffItemVO(tariffItemVO);
			frmICDPCSPolicy= (DynaActionForm)FormUtils.setFormValues("frmICDPCSPolicy",icdPcsVO,
					this,mapping,request);
			frmICDPCSPolicy.set("tariffItemVO", FormUtils.setFormValues("frmCodingAssociate",icdPcsVO.getTariffItemVO(),
				this,mapping,request));
			frmICDPCSPolicy.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmICDPCSPolicy",frmICDPCSPolicy);
			
			//check this part of code - CR KOC-Decoupling
			
			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				if(strSubLink.equals("PreAuth")){
					strForward= strPreauthICDlist ;
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					strForward=strPreauthICDlist;
				}//end of if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			
			//end
			
			if(strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth")){
					strForward= strPreauthCleanupICDlist ;
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					strForward=strPreauthCleanupICDlist;
				}//end of if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
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
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**check this method for changes for CR KOC-Decoupling
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
			log.info("Inside the doViewAssociateProcedure method of PreauthCodingAction");
			setLinks(request);
			
			DynaActionForm frmICDPCSPolicy=(DynaActionForm)request.getSession().getAttribute("frmICDPCSPolicy");
			String strAsscProcCode=getAsscProcedureCode(request);
			ArrayList<Object> alAsscProc=new ArrayList<Object>();
			ProcedureDetailVO procDetailVO = new ProcedureDetailVO();
			if(frmICDPCSPolicy.get("procSeqID") == null )
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.coding.PreAuthorization.procSeqID");
				throw expTTK;
			}//end of if(frmICDPCSPolicy.get("procSeqID") == null )
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			
			//check this part of code for CR KOC-Decoupling
			if(strLink.equals("Coding") || strLink.equals("DataEntryCoding"))
			{
				strForward=strCodingAssociate;
			}//end of if(strLink.equals("Coding"))
			//end
			
			
			else if(strLink.equals("Code CleanUp"))
			{
				strForward=strPreauthCleanupICDlist;
			}//end of else if(strLink.equals("Code CleanUp"))
			procDetailVO.setProcedureID((Long)frmICDPCSPolicy.get("procSeqID"));
			procDetailVO.setProcedureCode((String)frmICDPCSPolicy.get("procedurecode"));
			procDetailVO.setProcedureDescription((String)frmICDPCSPolicy.get("procedurename"));
			if(procDetailVO.getProcedureID() != null)
			{
				alAsscProc.add(procDetailVO);
			}else
			{
				frmICDPCSPolicy.set("procSeqID",null);
				frmICDPCSPolicy.set("procedurecode","");
				frmICDPCSPolicy.set("procedurename","");
			}//end of else

			String[] strAsscProc = strAsscProcCode.split(",");
			//log.info("Lenght of the associate procedure :"+strAsscProc.length);
			for(int i=0; i<strAsscProc.length; i++)
			{
				if(strAsscProc[i].equals(procDetailVO.getProcedureCode()))
				{
					//log.info("Inside if(strAsscProc[i].equals(procDetailVO.getProcedureID()))");
					frmICDPCSPolicy.set("procedurename","");
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.coding.PreAuthorization.PCSDuplication");
					throw expTTK;
				}//end of if(strAsscProc[i].equals(procDetailVO.getProcedureCode()))
			}//end of for(int i=0; i<strAsscProc.length; i++)
			modifyProcedureCode(request,alAsscProc);
			frmICDPCSPolicy.set("procSeqID",null);
			frmICDPCSPolicy.set("procedurecode","");
			frmICDPCSPolicy.set("procedurename","");
			
			//set the table data object to session
			request.getSession().setAttribute("frmICDPCSPolicy",frmICDPCSPolicy);
			return mapping.findForward(strForward);
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
	 *  Adds the selected procedure code to session object
	 *  @param request HttpServletRequest object which contains information about the selected procedure code
	 *  @param alNewAsscCode ArrayList which contains the new associated procedure code
	 * @throws TTKException
	 */
	private void modifyProcedureCode(HttpServletRequest request,ArrayList  alNewAsscCode) throws TTKException
	{
		String strLink=TTKCommon.getActiveLink(request);
		ArrayList<Object> alAsscProcedure=new ArrayList<Object>();
		DynaActionForm frmICDPCSPolicy=(DynaActionForm)request.getSession().getAttribute("frmICDPCSPolicy");
		if(strLink.equals("Coding")||strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding"))
		{
			alAsscProcedure=(ArrayList)frmICDPCSPolicy.get("asscCodes");
		}// end of else
		if(alNewAsscCode!=null)
		{
			for(int i=0;i<alNewAsscCode.size();i++)
			{
				alAsscProcedure.add(alNewAsscCode.get(i));
			}//end of for(int i=0;i<alNewAsscCode.size();i++)
			modifyDeletedSeqId(request,alNewAsscCode);//call the modify the deleted procedure code
		}//end of if(alNewAsscCode!=null)
	}//end of modifyProcedureCode(HttpServletRequest request,ArrayList  alNewAsscCode)

	/**
	 * Modifies the deleted procedure code
	 * @param request HttpServletRequest object which contains information about the deleted procedure code
	 * @param alNewAsscCode ArrayList which contains the information of procedure code which are associated
	 * @throws TTKException
	 */
	private void modifyDeletedSeqId(HttpServletRequest request,ArrayList alNewAsscCode) throws TTKException
	{
		log.debug("INSIDE THE MODFY DELETED SEQID :");
		String strLink=TTKCommon.getActiveLink(request);
		DynaActionForm frmICDPCSCoding=(DynaActionForm)request.getSession().getAttribute("frmICDPCSCoding");
		DynaActionForm frmICDPCSPolicy=(DynaActionForm)request.getSession().getAttribute("frmICDPCSPolicy");
		String strDeleteSeqId="";
		if(strLink.equals("Coding")||strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding"))
		{
			strDeleteSeqId=(String)frmICDPCSPolicy.get("deleteSeqId");
		}//end of else
		else
		{
			strDeleteSeqId=(String)frmICDPCSCoding.get("deleteSeqId");
		}//end of else
		//log.info("strDeleteSeqId is "+strDeleteSeqId);
		StringTokenizer stProcedure=new StringTokenizer(strDeleteSeqId,"|");
		String strNewDeletedId="",strTemp="",strCode="",strKey="";
		ProcedureDetailVO procDetailVO=null;
		boolean bFlag=true;
		if(alNewAsscCode!=null&&alNewAsscCode.size()>0)
		{
			for(int i=0;i<alNewAsscCode.size();i++)
			{
				stProcedure=new StringTokenizer(strDeleteSeqId,"|");
				procDetailVO=(ProcedureDetailVO)alNewAsscCode.get(i);
				strCode= procDetailVO.getProcedureID().toString();
				while(stProcedure.hasMoreTokens())
				{
					bFlag=true;
					strKey=stProcedure.nextToken();
					if(strKey.equals(strCode))
					{
						bFlag=false;
					}//end of if(strKey.equals(strCode))
					if(bFlag)
					{
						strTemp+=strKey+"|";
					}//end of if(bFlag)
				}//end of while
				strNewDeletedId="|"+strTemp;
				strDeleteSeqId=strNewDeletedId;
				strTemp="";
			}//end of for(int i=0;i<alNewAsscCode.size();i++)
		}//end of if(alNewAsscCode!=null)
		else
		{
			strNewDeletedId=strDeleteSeqId;
		}//end of else
		if(strLink.equals("Coding")||strLink.equals("Code CleanUp")||strLink.equals("DataEntryCoding"))
		{
			frmICDPCSPolicy.set("deleteSeqId",strNewDeletedId);
		}//end of else
		else
		{
			frmICDPCSCoding.set("deleteSeqId",strNewDeletedId);
		}//end of else
	}//end of modifyDeletedSeqId(HttpServletRequest request,ArrayList alNewAsscCode)

	/**check this method working for CR KOC-Decoupling 
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
	public ActionForward doGetDescription(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			if(strLink.equals("Code CleanUp"))
			{
				strForward=strPreauthCleanupICDlist;
			}//end of if(strLink.equals("Code CleanUp"))
			else if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				strForward=strCodePreauthIcdPcsCoding;
			}//end of else if(strLink.equals("Coding"))
			DynaActionForm frmICDPCSPolicy = (DynaActionForm)form;
			ParallelCodingManager codingManager = this.getCodingManagerObject();
			Object[] objArrayResult = codingManager.getExactICD(request.getParameter("sICDCode"));

			frmICDPCSPolicy.set("sICDName",objArrayResult[0]);
			frmICDPCSPolicy.set("pEDCodeID",objArrayResult[1]);
			request.getSession().setAttribute("frmICDPCSPolicy",frmICDPCSPolicy);
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
	}//end of public ActionForward doViewAssociateProcedure(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	 			//HttpServletResponse response)

	/**check this method also for CR-KOC- Decoupling
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
	public ActionForward doGetPCSDescription(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		try
		{
			log.info("inside dogetpcs description method");
			setLinks(request);
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			if(strLink.equals("Code CleanUp"))
			{
				strForward=strPreauthCleanupICDlist;
			}//end of if(strLink.equals("Code CleanUp"))
			else if(strLink.equals("Coding") || strLink.equals("DataEntryCoding"))
			{
				strForward=strCodePreauthIcdPcsCoding;
			}//end of else if(strLink.equals("Coding"))
			DynaActionForm frmICDPCSPolicy = (DynaActionForm)form;
			ParallelCodingManager codingManager = this.getCodingManagerObject();
			Object[] objArrayResult = codingManager.getExactProcedure(request.getParameter("procedurecode"));

			frmICDPCSPolicy.set("procedurename",objArrayResult[0]);
			frmICDPCSPolicy.set("procSeqID",objArrayResult[1]);
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
	}//end of public ActionForward doViewAssociateProcedure(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	 			//HttpServletResponse response)

	/**
	 *  Remove the deleted procedure code from session object
	 *  @param request HttpServletRequest object which contains information about the selected procedure code
	 *  @param alNewAsscCode ArrayList which contains the new associated procedure code
	 */
	private void modifyProcedureCode(HttpServletRequest request,DynaActionForm frmICDPCSPolicy)
	{
		log.debug("Inside PreauthCodingAction modifyProcedureCode**************");
		ArrayList alasscProcedure=null;
		alasscProcedure=(ArrayList)frmICDPCSPolicy.get("asscCodes");
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
			frmICDPCSPolicy.set("asscCodes",alasscProcedure);
		}//end of if(strDeleteSeqId!=null&&!strDeleteSeqId.equals(""))
	}//end of modifyProcedureCode(HttpServletRequest request,DynaActionForm frmICDPCSCoding)

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
			log.debug("Inside PreauthCodingAction doRemoveProcedure");
			DynaActionForm frmICDPCSPolicy = (DynaActionForm)form;
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			if(strLink.equals("Coding")||strLink.equals("DataEntryCoding"))
			{
				strForward=strCodingAssociate;
			}//end of if(strLink.equals("Coding"))
			else if(strLink.equals("Code CleanUp"))
			{
				strForward=strPreauthCleanupICDlist;
			}//end of else if(strLink.equals("Code CleanUp"))
			frmICDPCSPolicy.set("frmChanged","changed");
			modifyProcedureCode(request,frmICDPCSPolicy);
			return mapping.findForward(strForward);
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
	 * @return preAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ParallelPreAuthManager getPreAuthManager() throws TTKException
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
			throw new TTKException(exp, "");
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManager()

	/**
	 * This method returns the claimManager session object for invoking DAO methods from it.
	 * @return claimManager session object which can be used for method invokation
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
			throw new TTKException(exp, strICDPCSError);
		}//end of catch
		return claimManager;
	}//end getClaimManagerObject()

	/**
	 * Returns the MemberManager session object for invoking methods on it.
	 * @return MemberManager session object which can be used for method invokation
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
			throw new TTKException(exp, strICDPCSError);
		}//end of catch
		return codingManager;
	}//end getCodingManagerObject()

	/**
	 * Returns a String which contain the comma separated procedure code which are associated.
	 * @param request HttpServletRequest object which contain information about associated procedure code.
	 * @return String which contains the comma separated procedure code which are associated.
	 */
	private String getAsscProcedureCode(HttpServletRequest request) throws TTKException
	{
		try
		{
			StringBuffer sbfAsscProcCode= new StringBuffer();
			ArrayList alAsscProc=new ArrayList();
			ProcedureDetailVO procDetailVO=null;
			DynaActionForm frmICDPCSPolicy=(DynaActionForm)request.getSession().getAttribute("frmICDPCSPolicy");
			alAsscProc=(ArrayList)frmICDPCSPolicy.get("asscCodes");
			if(alAsscProc!=null)
			{
				for(int i=0;i<alAsscProc.size();i++)
				{
					procDetailVO=(ProcedureDetailVO)alAsscProc.get(i);
					if(!sbfAsscProcCode.toString().equals(""))
					{
						sbfAsscProcCode=sbfAsscProcCode.append(",");
					}//end of if(!sbfAsscProcCode.toString().equals(""))
					sbfAsscProcCode= sbfAsscProcCode.append((String)procDetailVO.getProcedureCode());
				}//end of for(int i=0;i<alAsscProc.size();i++)
			}//end of if(alAsscProc!=null)
			return sbfAsscProcCode.toString();
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp,"associate");
		}//end of catch
	}//end of getAsscProcedureCode(HttpServletRequest request)

}//end of PreauthCodingAction