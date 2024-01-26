/**
 * @ (#) PreAuthTariffAction.java May 31, 2006
 * Project      : TTK HealthCare Services
 * File         : PreAuthTariffAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : May 31, 2006
 *
 * @author       :  Arun K N
 * Modified by   : 	Pradeep R
 * Modified date :  july 4th, 2006
 * Reason        :  To implement all the logic which was not done when the screen was build.
 */

package com.ttk.action.dataentrypreauth;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.preauth.PreAuthTariffVO;
import com.ttk.dto.preauth.TariffAilmentVO;
import com.ttk.dto.preauth.TariffDetailVO;
import com.ttk.dto.preauth.TariffProcedureVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used to get the current tariffdetils for a particular pre-auth, allows to enter/modify the amount
 * charged for the particular Ailment and also provides an option to save the Tariffdetails.
 *
 */
public class PreAuthTariffAction extends TTKAction{

	private static Logger log = Logger.getLogger( PreAuthTariffAction.class );

	private static final String strTariffDetails="TariffDetails";
	//private static final String strCalculate="Calculate";

	//declaration of forward paths
	private static final String strTariffdetails="tariffdetails";

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
		try{
			setLinks(request);
			log.debug("Inside PreAuthTariffAction doView");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();    //get the business object to call DAO
			//check for the web board ID
			if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.PreAuthorization.required");
				throw expTTK;
			}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"))
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.PreAuthorization.codingflow");
				throw expTTK;
			}//end of if(PreAuthWebBoardHelper.getCodingReviewYN(request)=="Y")
			DynaActionForm frmPreAuthTariffDetail=(DynaActionForm)form;
			PreAuthTariffVO preAuthTariffVO=new PreAuthTariffVO();
			ArrayList<Object> alParameters  = new ArrayList<Object>();
			alParameters.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			alParameters.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
			alParameters.add(PreAuthWebBoardHelper.getPolicySeqId(request));
			alParameters.add(TTKCommon.getUserSeqId(request));
			preAuthTariffVO= preAuthObject.getAccountHeadTariffDetail(alParameters);
			if(preAuthTariffVO==null)
			{
				preAuthTariffVO=new PreAuthTariffVO();
			}//end of if(preAuthTariffVO==null)
			else
			{
				ArrayList<Object> alTariffDetailInfo =preAuthTariffVO.getTariffDetailVOList();
				ArrayList<Object> alAilmentInfo =preAuthTariffVO.getAilmentVOList();
				frmPreAuthTariffDetail = (DynaActionForm)FormUtils.setFormValues("frmPreAuthTariffDetail",
																				preAuthTariffVO,this,mapping,request);
				if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)
				{
					frmPreAuthTariffDetail.set("tariffInfo",(TariffDetailVO[])alTariffDetailInfo.toArray(
																							new TariffDetailVO[0]));
				}//end of  if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)
				if(alAilmentInfo!=null && alAilmentInfo.size()>0)
				{
					frmPreAuthTariffDetail.set("ailmentInfo",(TariffAilmentVO[])alAilmentInfo.toArray(
																							new TariffAilmentVO[0]));
				}//end of  if(alAilmentInfo!=null && alAilmentInfo.size()>0)
				//This block is used to set the oters days stay to 0 if additional preAuthTariffVO.getOtherDaysStay <=0
				if(preAuthTariffVO.getOtherDaysStay()!=null && preAuthTariffVO.getOtherDaysStay()<=0 )
				{
					frmPreAuthTariffDetail.set("otherDaysStay","0");
				}//end of if(preAuthTariffVO.getOtherDaysStay()!=null && preAuthTariffVO.getOtherDaysStay()<=0 )
				if(preAuthTariffVO.getAccountYN().equals("Y"))
				{
					request.setAttribute("medicalnonpackage","error.claims.medical.nonpackage");
				}//end of if(preAuthTariffVO.getAccountYN().equals("Y"))
			}//end of else
			frmPreAuthTariffDetail.set("showSave","N");
			request.getSession().setAttribute("frmPreAuthTariffDetail",frmPreAuthTariffDetail);
			return this.getForward(strTariffdetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTariffDetails));
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
		log.debug("Inside PreAuthTariffAction doChangeWebBoard");
		return doView(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)

	/**
	 * This method is used to reset the Tariff Detail Screen.
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
															HttpServletResponse response) throws Exception{
		log.debug("Inside PreAuthGenealAction doReset");
		return doView(mapping,form,request,response);
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to submit the details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside PreAuthGenealAction doSubmit");
			String strForward="";
			DynaActionForm frmPreAuthTariffDetail=(DynaActionForm)form;
			PreAuthTariffVO preAuthTariffVO= new PreAuthTariffVO();
			Long lngSelectedTariffHdrSeqID= (Long)frmPreAuthTariffDetail.get("selectedTariffHdrSeqID");
			Long[] lngSelectedTariffSeqID = (Long[])frmPreAuthTariffDetail.get("selectedTariffSeqID");
			Long[] lngSelectedWardAccGroupSeqID = (Long[])frmPreAuthTariffDetail.get("selectedWardAccGroupSeqID");
			//Tariff values
			String[] strWardDesc = request.getParameterValues("wardDesc");
			String[] strAccGroupDesc = request.getParameterValues("accGroupDesc");
			String[] strTariffRequestedAmt = request.getParameterValues("tariffRequestedAmt");
			String[] strTariffApprovedAmt = request.getParameterValues("tariffApprovedAmt");
			String[] strTariffMaxAmtAllowed = request.getParameterValues("tariffMaxAmtAllowed");
			String[] strTariffNotes = request.getParameterValues("selectedTariffNotes");
			String[] strWardTypeID = request.getParameterValues("wardTypeID");
			String[] strCaptureNbrOfDaysYN = request.getParameterValues("captureNbrOfDaysYN");
			
			ArrayList<Object> alTariffDetail = new ArrayList<Object>();
			if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
			{
				for(int i=0;i<lngSelectedTariffSeqID.length;i++)
				{
					TariffDetailVO tariffDetailVO = new TariffDetailVO();
					tariffDetailVO.setTariffSeqID(lngSelectedTariffSeqID[i]);
					tariffDetailVO.setWardAccGroupSeqID(lngSelectedWardAccGroupSeqID[i]);
					tariffDetailVO.setWardDesc((strWardDesc[i]));
					tariffDetailVO.setAccGroupDesc(strAccGroupDesc[i]);
					tariffDetailVO.setTariffRequestedAmt((strTariffRequestedAmt[i]));
					tariffDetailVO.setTariffApprovedAmt((strTariffApprovedAmt[i]));
					tariffDetailVO.setTariffMaxAmtAllowed((strTariffMaxAmtAllowed[i]));
					tariffDetailVO.setTariffNotes(strTariffNotes[i]);
					tariffDetailVO.setWardTypeID(strWardTypeID[i]);
					tariffDetailVO.setCaptureNbrOfDaysYN(strCaptureNbrOfDaysYN[i]);
					
					alTariffDetail.add(tariffDetailVO);
				}//end of  for(int i=0;i<lngTariffSeqID.length;i++)
			}//end of if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
			preAuthTariffVO.setTariffDetailVOList(alTariffDetail);
			//Ailment Values
			Long[] lngAilmentCapsSeqID = (Long[])frmPreAuthTariffDetail.get("selectedAilmentCapsSeqID");
			Long[] lngICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("selectedICDPCSSeqID");
			String[] strDescription = request.getParameterValues("description");
			String[] strApprovedAilmentAmt = request.getParameterValues("approvedAilmentAmt");
			Long[] lngAsscICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("asscICDPCSSeqID");			
			Long[] lngPatProcSeqID = (Long[])frmPreAuthTariffDetail.get("patProcSeqID");
			Long[] lngProcSeqId = (Long[])frmPreAuthTariffDetail.get("procSeqID");
			String[] strProcedureAmt = request.getParameterValues("procedureAmt");
			String[] strProcDesc = request.getParameterValues("procDesc");
			//String[] strMaxAilmentAllowedAmt = request.getParameterValues("maxAilmentAllowedAmt");
			//String[] strAilmentNotes = request.getParameterValues("selectedAilmentNotes");
			ArrayList<Object> alAilmentDetail = new ArrayList<Object>();
			ArrayList<Object> alProcedureList=null;
			
			if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
			{
				for(int i=0;i<lngICDPCSSeqID.length;i++)
				{
					TariffAilmentVO tariffAilmentVO = new TariffAilmentVO();
					tariffAilmentVO.setAilmentCapsSeqID((lngAilmentCapsSeqID[i]));
					tariffAilmentVO.setICDPCSSeqID((lngICDPCSSeqID[i]));
					tariffAilmentVO.setDescription(strDescription[i]);
					tariffAilmentVO.setApprovedAilmentAmt(strApprovedAilmentAmt[i]);
					//tariffAilmentVO.setMaxAilmentAllowedAmt((strMaxAilmentAllowedAmt[i]));
					//tariffAilmentVO.setAilmentNotes(strAilmentNotes[i]);
					
					alProcedureList=null;
					for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
					{
						log.debug("****** Values lngAsscICDPCSSeqID*****"+lngAsscICDPCSSeqID[j]);
						log.debug("****** Values lngICDPCSSeqID *****"+lngICDPCSSeqID[i]);
						if(lngAsscICDPCSSeqID[j].longValue()==lngICDPCSSeqID[i].longValue())
						{
							if(alProcedureList == null){
								alProcedureList = new ArrayList<Object>();							
							}//end of if(alLineItemList == null)
							TariffProcedureVO tariffProcedureVO=new TariffProcedureVO();
							tariffProcedureVO.setPatProcSeqID(lngPatProcSeqID[j]);
							tariffProcedureVO.setProcSeqID(lngProcSeqId[j]);
							tariffProcedureVO.setProcDesc(strProcDesc[j]);
							tariffProcedureVO.setProcedureAmt(strProcedureAmt[j]);
							alProcedureList.add(tariffProcedureVO);	
							if(alProcedureList!=null)
							{
								log.debug("****** alProcedureList *****"+alProcedureList.size());
							}
						}//end of if(lngAsscICDPCSSeqID[j]==lngICDPCSSeqID[i])
					}//end of for
					
					tariffAilmentVO.setProcedureList(alProcedureList);
					alAilmentDetail.add(tariffAilmentVO);
					//For printing the values
					alProcedureList=tariffAilmentVO.getProcedureList();
					TariffProcedureVO tariffProcedureVO1=null;
					log.debug("******************************** Local checking*******************");
					if(alProcedureList!=null)
					{
						for(int j=0;j<alProcedureList.size();j++)
						{
							
							tariffProcedureVO1=(TariffProcedureVO)alProcedureList.get(j);
							
							log.debug("PAT_PROC_SEQ_ID is 	: "+tariffProcedureVO1.getPatProcSeqID());
							log.debug("PROC_SEQ_ID is 		: "+tariffProcedureVO1.getProcSeqID());
							log.debug("PROC_DESCRIPTION is 	: "+tariffProcedureVO1.getProcDesc());
							log.debug("PROCEDURE_AMOUNT is 	: "+tariffProcedureVO1.getProcedureAmt());
						}//end of for(int j=0;j<alProcedureList.size();j++)
					}else
					{
						log.debug("******************************** else block checking*******************");
					}//end of else
				}//end of for(int i=0;i<lngAilmentCapsSeqID.length;i++)
			}//end of if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
			preAuthTariffVO.setAilmentVOList(alAilmentDetail);
			preAuthTariffVO.setTariffHdrSeqID(lngSelectedTariffHdrSeqID);
			frmPreAuthTariffDetail.set("tariffInfo",(TariffDetailVO[])alTariffDetail.toArray(new TariffDetailVO[0]));
			frmPreAuthTariffDetail.set("ailmentInfo",(TariffAilmentVO[])alAilmentDetail.toArray(new TariffAilmentVO[0]));
			strForward="submittariffform";
			return mapping.findForward(strForward);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTariffDetails));
		}//end of catch(Exception exp)
	}//end of doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Calculate the Approved and Max Allowed amount.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCalculate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthTariffAction doCalculate");
			setLinks(request);
			String strForward="";
			DynaActionForm frmPreAuthTariffDetail=(DynaActionForm)form;
			PreAuthTariffVO preAuthTariffVO= new PreAuthTariffVO();
			Long lngSelectedTariffHdrSeqID= (Long)frmPreAuthTariffDetail.get("selectedTariffHdrSeqID");
			Long[] lngSelectedTariffSeqID = (Long[])frmPreAuthTariffDetail.get("selectedTariffSeqID");
			Long[] lngSelectedWardAccGroupSeqID = (Long[])frmPreAuthTariffDetail.get("selectedWardAccGroupSeqID");
			
			String[] strWardDesc = request.getParameterValues("wardDesc");
			String[] strAccGroupDesc = request.getParameterValues("accGroupDesc");
			String[] strTariffRequestedAmt = request.getParameterValues("tariffRequestedAmt");
			String[] strTariffApprovedAmt = request.getParameterValues("tariffApprovedAmt");
			String[] strTariffMaxAmtAllowed = request.getParameterValues("tariffMaxAmtAllowed");
			String[] strTariffNotes = request.getParameterValues("selectedTariffNotes");
			String[] strDaysOfStay = request.getParameterValues("daysOfStay");
			String[] strRoomTypeID = request.getParameterValues("roomTypeID");
			String[] strVaccineTypeID = request.getParameterValues("vaccineTypeID");//added for maternity
		
			String[] strCaptureNbrOfDaysYN = request.getParameterValues("captureNbrOfDaysYN");
			String[] strWardTypeID = request.getParameterValues("wardTypeID");
			
			
			ArrayList<Object> alTariffDetail = new ArrayList<Object>();
			if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
			{
				for(int i=0;i<lngSelectedTariffSeqID.length;i++)
				{
					TariffDetailVO tariffDetailVO = new TariffDetailVO();
					tariffDetailVO.setTariffSeqID(lngSelectedTariffSeqID[i]);
					tariffDetailVO.setWardAccGroupSeqID(lngSelectedWardAccGroupSeqID[i]);
					tariffDetailVO.setWardDesc((strWardDesc[i]));
					tariffDetailVO.setAccGroupDesc(strAccGroupDesc[i]);
					tariffDetailVO.setRequestedAmt(TTKCommon.getBigDecimal(strTariffRequestedAmt[i]));
					tariffDetailVO.setApprovedAmt(TTKCommon.getBigDecimal(strTariffApprovedAmt[i]));
					tariffDetailVO.setMaxAmtAllowed(TTKCommon.getBigDecimal(strTariffMaxAmtAllowed[i]));
					tariffDetailVO.setTariffRequestedAmt((strTariffRequestedAmt[i]));
					tariffDetailVO.setTariffApprovedAmt((strTariffApprovedAmt[i]));
					tariffDetailVO.setTariffMaxAmtAllowed((strTariffMaxAmtAllowed[i]));
					tariffDetailVO.setTariffNotes(strTariffNotes[i]);                    
					tariffDetailVO.setCaptureNbrOfDaysYN(strCaptureNbrOfDaysYN[i]);
					tariffDetailVO.setWardTypeID(strWardTypeID[i]);
					tariffDetailVO.setRoomTypeID(strRoomTypeID[i]);
					tariffDetailVO.setVaccineTypeID(strVaccineTypeID[i]);
					tariffDetailVO.setDaysOfStay(strDaysOfStay[i]);    				
					alTariffDetail.add(tariffDetailVO);
				}//end of  for(int i=0;i<lngTariffSeqID.length;i++)
			}//end of if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
			preAuthTariffVO.setTariffDetailVOList(alTariffDetail);
			//Ailment Values
			Long[] lngAilmentCapsSeqID = (Long[])frmPreAuthTariffDetail.get("selectedAilmentCapsSeqID");
			Long[] lngICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("selectedICDPCSSeqID");
			String[] strDescription = request.getParameterValues("description");
			String[] strApprovedAilmentAmt = request.getParameterValues("approvedAilmentAmt");
			//String[] strMaxAilmentAllowedAmt = request.getParameterValues("maxAilmentAllowedAmt");
			//String[] strAilmentNotes = request.getParameterValues("selectedAilmentNotes");
			Long[] lngPatProcSeqID = (Long[])frmPreAuthTariffDetail.get("patProcSeqID");
			Long[] lngProcSeqId = (Long[])frmPreAuthTariffDetail.get("procSeqID");
			Long[] lngAsscICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("asscICDPCSSeqID");			
			String[] strProcedureAmt = request.getParameterValues("procedureAmt");
			String[] strProcDesc = request.getParameterValues("procDesc");
			
			ArrayList<Object> alAilmentDetail = new ArrayList<Object>();
			TariffProcedureVO tariffProcedureVO=null;
			ArrayList<Object> alProcedureList=null;
			if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
			{
				for(int i=0;i<lngICDPCSSeqID.length;i++)
				{
					TariffAilmentVO tariffAilmentVO = new TariffAilmentVO();
					tariffAilmentVO.setAilmentCapsSeqID((lngAilmentCapsSeqID[i]));
					tariffAilmentVO.setICDPCSSeqID((lngICDPCSSeqID[i]));
					tariffAilmentVO.setDescription(strDescription[i]);
					//approvedAilmentAmt
					tariffAilmentVO.setAilmentApprovedAmt(TTKCommon.getBigDecimal(strApprovedAilmentAmt[i]));
					//tariffAilmentVO.setAilmentMaxAllowedAmt(TTKCommon.getBigDecimal(strMaxAilmentAllowedAmt[i]));
					//tariffAilmentVO.setApprovedAilmentAmt(strApprovedAilmentAmt[i]);
					//tariffAilmentVO.setMaxAilmentAllowedAmt((strMaxAilmentAllowedAmt[i]));
					//tariffAilmentVO.setAilmentNotes(strAilmentNotes[i]);
					alProcedureList=null;
					for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
					{
						if(lngAsscICDPCSSeqID[j].longValue()==lngICDPCSSeqID[i].longValue())
						{
							if(alProcedureList == null){
								alProcedureList = new ArrayList<Object>();							
							}//end of if(alLineItemList == null)
							tariffProcedureVO=new TariffProcedureVO();
							tariffProcedureVO.setPatProcSeqID(lngPatProcSeqID[j]);
							tariffProcedureVO.setProcSeqID(lngProcSeqId[j]);
							tariffProcedureVO.setProcDesc(strProcDesc[j]);
							tariffProcedureVO.setProcedureAmt(strProcedureAmt[j]);
							alProcedureList.add(tariffProcedureVO);						
						}//end ofif(lngAsscICDPCSSeqID[j]==lngICDPCSSeqID[i])
						
					}//end of  for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
					tariffAilmentVO.setProcedureList(alProcedureList);                    
					alAilmentDetail.add(tariffAilmentVO);
				}//end of for(int i=0;i<lngAilmentCapsSeqID.length;i++)
			}//end of if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
			preAuthTariffVO.setAilmentVOList(alAilmentDetail);
			preAuthTariffVO.setTariffHdrSeqID(lngSelectedTariffHdrSeqID);
			frmPreAuthTariffDetail.set("tariffInfo",(TariffDetailVO[])alTariffDetail.toArray(new TariffDetailVO[0]));
			frmPreAuthTariffDetail.set("ailmentInfo",(TariffAilmentVO[])alAilmentDetail.toArray(new TariffAilmentVO[0]));
			strForward="submitcalculate";
			request.setAttribute("tariffcalculation","message.tariffcalculation");
			return mapping.findForward(strForward);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTariffDetails));
		}//end of catch(Exception exp)
	}//end of doCalculate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used for update the tariff details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSaveTariffDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside PreAuthTariffAction doSaveTariffDetails");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();    //get the business object to call DAO
			DynaActionForm frmPreAuthTariffDetail=(DynaActionForm)form;
			frmPreAuthTariffDetail = (DynaActionForm)request.getSession().getAttribute("frmPreAuthTariffDetail");
            PreAuthTariffVO preAuthTariffVO= (PreAuthTariffVO)FormUtils.getFormValues(frmPreAuthTariffDetail,
            														"frmPreAuthTariffDetail",this, mapping, request);
            Long lngSelectedTariffHdrSeqID= (Long)frmPreAuthTariffDetail.get("selectedTariffHdrSeqID");
            Long[] lngSelectedTariffSeqID = (Long[])frmPreAuthTariffDetail.get("selectedTariffSeqID");
            Long[] lngSelectedWardAccGroupSeqID = (Long[])frmPreAuthTariffDetail.get("selectedWardAccGroupSeqID");
            //Tariff values
            String[] strWardDesc = request.getParameterValues("wardDesc");
            String[] strAccGroupDesc = request.getParameterValues("accGroupDesc");
            String[] strTariffRequestedAmt = request.getParameterValues("tariffRequestedAmt");
            String[] strTariffApprovedAmt = request.getParameterValues("tariffApprovedAmt");
            String[] strTariffMaxAmtAllowed = request.getParameterValues("tariffMaxAmtAllowed");
            String[] strTariffNotes = request.getParameterValues("selectedTariffNotes");
            String[] strCaptureNbrOfDaysYN = request.getParameterValues("captureNbrOfDaysYN");

            String[] strWardTypeID = request.getParameterValues("wardTypeID");
            Long[] lngAsscICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("asscICDPCSSeqID");	
            String[] strProcedureAmt = request.getParameterValues("procedureAmt");
    		String[] strProcDesc = request.getParameterValues("procDesc");
    		Long[] lngProcSeqId = (Long[])frmPreAuthTariffDetail.get("procSeqID");
    		Long[] lngPatProcSeqID = (Long[])frmPreAuthTariffDetail.get("patProcSeqID");
    		String[] strRoomTypeID = request.getParameterValues("roomTypeID");
    		String[] strVaccineTypeID = request.getParameterValues("vaccineTypeID");
    		String[] strDaysOfStay = request.getParameterValues("daysOfStay");
    		
            ArrayList<Object> alTariffDetail = new ArrayList<Object>();
            if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
            {
                for(int i=0;i<lngSelectedTariffSeqID.length;i++)
                {
                    TariffDetailVO tariffDetailVO = new TariffDetailVO();
                    tariffDetailVO.setTariffSeqID(lngSelectedTariffSeqID[i]);
                    tariffDetailVO.setWardAccGroupSeqID(lngSelectedWardAccGroupSeqID[i]);
                    tariffDetailVO.setWardDesc((strWardDesc[i]));
                    tariffDetailVO.setAccGroupDesc(strAccGroupDesc[i]);
                    tariffDetailVO.setRequestedAmt(TTKCommon.getBigDecimal(strTariffRequestedAmt[i]));
                    tariffDetailVO.setApprovedAmt(TTKCommon.getBigDecimal(strTariffApprovedAmt[i]));
                    tariffDetailVO.setMaxAmtAllowed(TTKCommon.getBigDecimal(strTariffMaxAmtAllowed[i]));
                    tariffDetailVO.setTariffRequestedAmt((strTariffRequestedAmt[i]));
                    tariffDetailVO.setTariffApprovedAmt((strTariffApprovedAmt[i]));
                    tariffDetailVO.setTariffMaxAmtAllowed((strTariffMaxAmtAllowed[i]));
                    tariffDetailVO.setTariffNotes(strTariffNotes[i]);                    
                    tariffDetailVO.setCaptureNbrOfDaysYN(strCaptureNbrOfDaysYN[i]);
                    tariffDetailVO.setWardTypeID(strWardTypeID[i]);
                    tariffDetailVO.setRoomTypeID(strRoomTypeID[i]);
                    tariffDetailVO.setVaccineTypeID(strVaccineTypeID[i]);
    				tariffDetailVO.setDaysOfStay(strDaysOfStay[i]);    				
                    alTariffDetail.add(tariffDetailVO);
                }//end of  for(int i=0;i<lngTariffSeqID.length;i++)
            }//end of if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
            preAuthTariffVO.setTariffDetailVOList(alTariffDetail);
            //Ailment Values
            Long[] lngAilmentCapsSeqID = (Long[])frmPreAuthTariffDetail.get("selectedAilmentCapsSeqID");
            Long[] lngICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("selectedICDPCSSeqID");
            String[] strDescription = request.getParameterValues("description");
            String[] strApprovedAilmentAmt = request.getParameterValues("approvedAilmentAmt");
            //String[] strMaxAilmentAllowedAmt = request.getParameterValues("maxAilmentAllowedAmt");
            //String[] strAilmentNotes = request.getParameterValues("selectedAilmentNotes");
            ArrayList<Object> alAilmentDetail = new ArrayList<Object>();
            ArrayList<Object> alProcedureList=null;
            TariffProcedureVO tariffProcedureVO=null;
            if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
            {
                for(int i=0;i<lngICDPCSSeqID.length;i++)
                {
                    TariffAilmentVO tariffAilmentVO = new TariffAilmentVO();
                    tariffAilmentVO.setAilmentCapsSeqID((lngAilmentCapsSeqID[i]));
                    tariffAilmentVO.setICDPCSSeqID((lngICDPCSSeqID[i]));
                    tariffAilmentVO.setDescription(strDescription[i]);
                    //approvedAilmentAmt
                    tariffAilmentVO.setAilmentApprovedAmt(TTKCommon.getBigDecimal(strApprovedAilmentAmt[i]));
                    //tariffAilmentVO.setAilmentMaxAllowedAmt(TTKCommon.getBigDecimal(strMaxAilmentAllowedAmt[i]));
                    //tariffAilmentVO.setApprovedAilmentAmt(strApprovedAilmentAmt[i]);
                    //tariffAilmentVO.setMaxAilmentAllowedAmt((strMaxAilmentAllowedAmt[i]));
                    //tariffAilmentVO.setAilmentNotes(strAilmentNotes[i]);
                    alProcedureList=null;
                    for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
    				{
                    	if(lngAsscICDPCSSeqID[j].longValue()==lngICDPCSSeqID[i].longValue())
    					{
    						if(alProcedureList == null){
    							alProcedureList = new ArrayList<Object>();							
    						}//end of if(alLineItemList == null)
    						tariffProcedureVO=new TariffProcedureVO();
    						tariffProcedureVO.setPatProcSeqID(lngPatProcSeqID[j]);
    						tariffProcedureVO.setProcSeqID(lngProcSeqId[j]);
    						tariffProcedureVO.setProcDesc(strProcDesc[j]);
    						tariffProcedureVO.setProcedureAmt(strProcedureAmt[j]);
    						alProcedureList.add(tariffProcedureVO);						
    					}//end ofif(lngAsscICDPCSSeqID[j]==lngICDPCSSeqID[i])
    					
    				}//end of for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
    				tariffAilmentVO.setProcedureList(alProcedureList);                    
                    alAilmentDetail.add(tariffAilmentVO);
                }//end of for(int i=0;i<lngAilmentCapsSeqID.length;i++)
            }//end of if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
            preAuthTariffVO.setAilmentVOList(alAilmentDetail);
            preAuthTariffVO.setTariffHdrSeqID(lngSelectedTariffHdrSeqID);
            preAuthTariffVO.setPkgRequestedAmt(TTKCommon.getBigDecimal(request.getParameter("requestedPkgAmt")));
            preAuthTariffVO.setPkgApprovedAmt(TTKCommon.getBigDecimal(request.getParameter("approvedPkgAmt")));
            preAuthTariffVO.setPkgMaxAmtAllowed(TTKCommon.getBigDecimal(request.getParameter("maxPkgAmtAllowed")));
            preAuthTariffVO.setNotes(request.getParameter("selectedPkgNotes"));
            preAuthTariffVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
            preAuthTariffVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            preAuthTariffVO.setClickSave("Y");
            long lngHedSeqId=preAuthObject.saveAccountHeadTariffDetail(preAuthTariffVO);
            log.debug("lngHedSeqId value is :"+lngHedSeqId);
            /*if(lngHedSeqId>0)
            {*/
                //Requery to get the updated data
                request.setAttribute("updated","message.saved");                
                frmPreAuthTariffDetail.initialize(mapping);
                ArrayList<Object> alParameters  = new ArrayList<Object>();
                alParameters.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
                alParameters.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
                alParameters.add(PreAuthWebBoardHelper.getPolicySeqId(request));
                alParameters.add(TTKCommon.getUserSeqId(request));
                preAuthTariffVO= preAuthObject.getAccountHeadTariffDetail(alParameters);
                if(preAuthTariffVO==null)
                {
                    preAuthTariffVO=new PreAuthTariffVO();
                }//end of if(preAuthTariffVO==null)
                else
                {
                    ArrayList<Object> alTariffDetailInfo =preAuthTariffVO.getTariffDetailVOList();
                    ArrayList<Object> alAilmentInfo =preAuthTariffVO.getAilmentVOList();
                    frmPreAuthTariffDetail = (DynaActionForm)FormUtils.setFormValues("frmPreAuthTariffDetail",
                    															preAuthTariffVO,this,mapping,request);
                    if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)
                    {
                        frmPreAuthTariffDetail.set("tariffInfo",(TariffDetailVO[])alTariffDetailInfo.toArray(
                        																	new TariffDetailVO[0]));
                    }//end of  if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)
                    if(alAilmentInfo!=null && alAilmentInfo.size()>0)
                    {
                        frmPreAuthTariffDetail.set("ailmentInfo",(TariffAilmentVO[])alAilmentInfo.toArray(
                        																	new TariffAilmentVO[0]));
                    }//end of  if(alAilmentInfo!=null && alAilmentInfo.size()>0)
                    if(preAuthTariffVO.getAccountYN().equals("Y"))
    				{
    					request.setAttribute("medicalnonpackage","error.claims.medical.nonpackage");
    				}//end of if(preAuthTariffVO.getAccountYN().equals("Y"))
                }//end of else
                frmPreAuthTariffDetail.set("showSave","Y");
                request.getSession().setAttribute("frmPreAuthTariffDetail",frmPreAuthTariffDetail);
            //}//end of if(iCount>0)
            return this.getForward(strTariffdetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTariffDetails));
		}//end of catch(Exception exp)
	}//end of doSaveTariffDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * This method is used for calulate the updated tariff details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSaveCalculateTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside PreAuthTariffAction doSaveCalculateTariff");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();    //get the business object to call DAO
			DynaActionForm frmPreAuthTariffDetail=(DynaActionForm)form;
			frmPreAuthTariffDetail = (DynaActionForm)request.getSession().getAttribute("frmPreAuthTariffDetail");
			PreAuthTariffVO preAuthTariffVO= (PreAuthTariffVO)FormUtils.getFormValues(frmPreAuthTariffDetail,
																	"frmPreAuthTariffDetail",this, mapping, request);
			Long lngSelectedTariffHdrSeqID= (Long)frmPreAuthTariffDetail.get("selectedTariffHdrSeqID");
			Long[] lngSelectedTariffSeqID = (Long[])frmPreAuthTariffDetail.get("selectedTariffSeqID");
			Long[] lngSelectedWardAccGroupSeqID = (Long[])frmPreAuthTariffDetail.get("selectedWardAccGroupSeqID");
			//Tariff values
			String[] strWardDesc = request.getParameterValues("wardDesc");
			String[] strAccGroupDesc = request.getParameterValues("accGroupDesc");
			String[] strTariffRequestedAmt = request.getParameterValues("tariffRequestedAmt");
			String[] strTariffMaxAmtAllowed = request.getParameterValues("tariffMaxAmtAllowed");
			String[] strTariffNotes = request.getParameterValues("selectedTariffNotes");
			String[] strDaysOfStay = request.getParameterValues("daysOfStay");
			String[] strRoomTypeID = request.getParameterValues("roomTypeID");
			String[] strVaccineTypeID = request.getParameterValues("vaccineTypeID");//added for maternity
			String[] strCaptureNbrOfDaysYN = request.getParameterValues("captureNbrOfDaysYN");
			String[] strTariffApprovedAmt = request.getParameterValues("tariffApprovedAmt");
			String[] strWardTypeID = request.getParameterValues("wardTypeID");
			Long[] lngAsscICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("asscICDPCSSeqID");	
			Long[] lngProcSeqId = (Long[])frmPreAuthTariffDetail.get("procSeqID");
    		Long[] lngPatProcSeqID = (Long[])frmPreAuthTariffDetail.get("patProcSeqID");
			ArrayList<Object> alTariffDetail = new ArrayList<Object>();
			if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
            {
                for(int i=0;i<lngSelectedTariffSeqID.length;i++)
                {
                    TariffDetailVO tariffDetailVO = new TariffDetailVO();
                    tariffDetailVO.setTariffSeqID(lngSelectedTariffSeqID[i]);
                    tariffDetailVO.setWardAccGroupSeqID(lngSelectedWardAccGroupSeqID[i]);
                    tariffDetailVO.setWardDesc((strWardDesc[i]));
                    tariffDetailVO.setAccGroupDesc(strAccGroupDesc[i]);
                    tariffDetailVO.setRequestedAmt(TTKCommon.getBigDecimal(strTariffRequestedAmt[i]));
                    tariffDetailVO.setApprovedAmt(TTKCommon.getBigDecimal(strTariffApprovedAmt[i]));
                    tariffDetailVO.setMaxAmtAllowed(TTKCommon.getBigDecimal(strTariffMaxAmtAllowed[i]));
                    tariffDetailVO.setTariffRequestedAmt((strTariffRequestedAmt[i]));
                    tariffDetailVO.setTariffApprovedAmt((strTariffApprovedAmt[i]));
                    tariffDetailVO.setTariffMaxAmtAllowed((strTariffMaxAmtAllowed[i]));
                    tariffDetailVO.setTariffNotes(strTariffNotes[i]);                    
                    tariffDetailVO.setCaptureNbrOfDaysYN(strCaptureNbrOfDaysYN[i]);
                    tariffDetailVO.setWardTypeID(strWardTypeID[i]);
                    tariffDetailVO.setRoomTypeID(strRoomTypeID[i]);
                    tariffDetailVO.setVaccineTypeID(strVaccineTypeID[i]);//added for maternity
    				tariffDetailVO.setDaysOfStay(strDaysOfStay[i]);    				
                    alTariffDetail.add(tariffDetailVO);
                }//end of  for(int i=0;i<lngTariffSeqID.length;i++)
            }//end of if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
			preAuthTariffVO.setTariffDetailVOList(alTariffDetail);
			//Ailment Values
			Long[] lngAilmentCapsSeqID = (Long[])frmPreAuthTariffDetail.get("selectedAilmentCapsSeqID");
			Long[] lngICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("selectedICDPCSSeqID");
			String[] strDescription = request.getParameterValues("description");
			//String[] strApprovedAilmentAmt = request.getParameterValues("approvedAilmentAmt");
			//String[] strProcedureAmt = request.getParameterValues("procedureAmt");
    		String[] strProcDesc = request.getParameterValues("procDesc");
			//String[] strMaxAilmentAllowedAmt = request.getParameterValues("maxAilmentAllowedAmt");
			//String[] strAilmentNotes = request.getParameterValues("selectedAilmentNotes");
			ArrayList<Object> alAilmentDetail = new ArrayList<Object>();
			ArrayList<Object> alProcedureList=null;
            TariffProcedureVO tariffProcedureVO=null;
			if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
            {
                for(int i=0;i<lngICDPCSSeqID.length;i++)
                {
                    TariffAilmentVO tariffAilmentVO = new TariffAilmentVO();
                    tariffAilmentVO.setAilmentCapsSeqID((lngAilmentCapsSeqID[i]));
                    tariffAilmentVO.setICDPCSSeqID((lngICDPCSSeqID[i]));
                    tariffAilmentVO.setDescription(strDescription[i]);
                    //approvedAilmentAmt
                    //NOT TO STORE THE AILEMENT APPROVED AMOUNT
                    tariffAilmentVO.setAilmentApprovedAmt(TTKCommon.getBigDecimal(""));
                    //tariffAilmentVO.setAilmentMaxAllowedAmt(TTKCommon.getBigDecimal(strMaxAilmentAllowedAmt[i]));
                    //tariffAilmentVO.setApprovedAilmentAmt(strApprovedAilmentAmt[i]);
                    //tariffAilmentVO.setMaxAilmentAllowedAmt((strMaxAilmentAllowedAmt[i]));
                    //tariffAilmentVO.setAilmentNotes(strAilmentNotes[i]);
                    alProcedureList=null;
                    for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
    				{
                    	if(lngAsscICDPCSSeqID[j].longValue()==lngICDPCSSeqID[i].longValue())
    					{
    						if(alProcedureList == null){
    							alProcedureList = new ArrayList<Object>();							
    						}//end of if(alLineItemList == null)
    						tariffProcedureVO=new TariffProcedureVO();
    						tariffProcedureVO.setPatProcSeqID(lngPatProcSeqID[j]);
    						tariffProcedureVO.setProcSeqID(lngProcSeqId[j]);
    						tariffProcedureVO.setProcDesc(strProcDesc[j]);
    						tariffProcedureVO.setProcedureAmt("");
    						alProcedureList.add(tariffProcedureVO);						
    					}//end of if(lngAsscICDPCSSeqID[j]==lngICDPCSSeqID[i])
    				}//end of for
    				tariffAilmentVO.setProcedureList(alProcedureList);                    
                    alAilmentDetail.add(tariffAilmentVO);
                }//end of for(int i=0;i<lngAilmentCapsSeqID.length;i++)
            }//end of if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
			
			preAuthTariffVO.setAilmentVOList(alAilmentDetail);
			preAuthTariffVO.setTariffHdrSeqID(lngSelectedTariffHdrSeqID);
			preAuthTariffVO.setPkgRequestedAmt(TTKCommon.getBigDecimal(request.getParameter("requestedPkgAmt")));
			//PkgApprovedAmt has set with "" value to make sure that on hit of calculate button the value is removed
			//from the jsp
			preAuthTariffVO.setPkgApprovedAmt(TTKCommon.getBigDecimal(""));
			preAuthTariffVO.setPkgMaxAmtAllowed(TTKCommon.getBigDecimal(request.getParameter("maxPkgAmtAllowed")));
			preAuthTariffVO.setNotes(request.getParameter("selectedPkgNotes"));
			preAuthTariffVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			preAuthTariffVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			preAuthTariffVO.setClickSave("N");			
			
			
			log.debug("Saving the tariffdetails and calculating the limits");
			long lngHedSeqId=preAuthObject.saveAccountHeadTariffDetail(preAuthTariffVO);
			
			frmPreAuthTariffDetail=getTariffCalDetails(frmPreAuthTariffDetail, preAuthTariffVO, mapping, request, response);
			request.getSession().setAttribute("frmPreAuthTariffDetail",frmPreAuthTariffDetail);
			
			log.debug("---------------------------lngHedSeqId --------------------------------------"+lngHedSeqId);
			int iCalculate= preAuthObject.calculateTariff(PreAuthWebBoardHelper.getPreAuthSeqId(request),
						TTKCommon.getUserSeqId(request));
			log.debug("iCalculate value is :"+iCalculate);
			frmPreAuthTariffDetail=getTariffCalDetails(frmPreAuthTariffDetail, preAuthTariffVO, mapping, request, response);
			frmPreAuthTariffDetail.set("showSave","Y");
			request.getSession().setAttribute("frmPreAuthTariffDetail",frmPreAuthTariffDetail);
			return this.getForward(strTariffdetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTariffDetails));
		}//end of catch(Exception exp)
	}//end of doSaveCalculateTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
	public ActionForward doSubmitAccountHead(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthTariffAction doSubmitAccountHead");
			setLinks(request);
			String strForward="";
			DynaActionForm frmPreAuthTariffDetail=(DynaActionForm)form;
			PreAuthTariffVO preAuthTariffVO= new PreAuthTariffVO();
			Long lngSelectedTariffHdrSeqID= (Long)frmPreAuthTariffDetail.get("selectedTariffHdrSeqID");
			Long[] lngSelectedTariffSeqID = (Long[])frmPreAuthTariffDetail.get("selectedTariffSeqID");
			Long[] lngSelectedWardAccGroupSeqID = (Long[])frmPreAuthTariffDetail.get("selectedWardAccGroupSeqID");
			//Tariff values
			String[] strWardDesc = request.getParameterValues("wardDesc");
			String[] strAccGroupDesc = request.getParameterValues("accGroupDesc");
			String[] strTariffRequestedAmt = request.getParameterValues("tariffRequestedAmt");
			String[] strTariffApprovedAmt = request.getParameterValues("tariffApprovedAmt");
			String[] strTariffMaxAmtAllowed = request.getParameterValues("tariffMaxAmtAllowed");
			String[] strTariffNotes = request.getParameterValues("selectedTariffNotes");
			String[] strCaptureNbrOfDaysYN = request.getParameterValues("captureNbrOfDaysYN");
			String[] strWardTypeID = request.getParameterValues("wardTypeID");
			String[] strRoomTypeID = request.getParameterValues("roomTypeID");
			String[] strDaysOfStay = request.getParameterValues("daysOfStay");
			
			ArrayList<Object> alTariffDetail = new ArrayList<Object>();
			if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
			{
				for(int i=0;i<lngSelectedTariffSeqID.length;i++)
				{
					TariffDetailVO tariffDetailVO = new TariffDetailVO();
					tariffDetailVO.setTariffSeqID(lngSelectedTariffSeqID[i]);
					tariffDetailVO.setWardAccGroupSeqID(lngSelectedWardAccGroupSeqID[i]);
					tariffDetailVO.setWardDesc((strWardDesc[i]));
					tariffDetailVO.setAccGroupDesc(strAccGroupDesc[i]);
					tariffDetailVO.setRequestedAmt(TTKCommon.getBigDecimal(strTariffRequestedAmt[i]));
					tariffDetailVO.setApprovedAmt(TTKCommon.getBigDecimal(strTariffApprovedAmt[i]));
					tariffDetailVO.setMaxAmtAllowed(TTKCommon.getBigDecimal(strTariffMaxAmtAllowed[i]));
					tariffDetailVO.setTariffRequestedAmt((strTariffRequestedAmt[i]));
					tariffDetailVO.setTariffApprovedAmt((strTariffApprovedAmt[i]));
					tariffDetailVO.setTariffMaxAmtAllowed((strTariffMaxAmtAllowed[i]));
					tariffDetailVO.setTariffNotes(strTariffNotes[i]);                    
					tariffDetailVO.setCaptureNbrOfDaysYN(strCaptureNbrOfDaysYN[i]);
					tariffDetailVO.setWardTypeID(strWardTypeID[i]);
					tariffDetailVO.setRoomTypeID(strRoomTypeID[i]);
					tariffDetailVO.setDaysOfStay(strDaysOfStay[i]);    				
					alTariffDetail.add(tariffDetailVO);
				}//end of  for(int i=0;i<lngTariffSeqID.length;i++)
			}//end of if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
			preAuthTariffVO.setTariffDetailVOList(alTariffDetail);
			//Ailment Values
			Long[] lngAilmentCapsSeqID = (Long[])frmPreAuthTariffDetail.get("selectedAilmentCapsSeqID");
			Long[] lngICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("selectedICDPCSSeqID");
			String[] strDescription = request.getParameterValues("description");
			String[] strApprovedAilmentAmt = request.getParameterValues("approvedAilmentAmt");
			//String[] strMaxAilmentAllowedAmt = request.getParameterValues("maxAilmentAllowedAmt");
			//String[] strAilmentNotes = request.getParameterValues("selectedAilmentNotes");
			Long[] lngAsscICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("asscICDPCSSeqID");	
			Long[] lngPatProcSeqID = (Long[])frmPreAuthTariffDetail.get("patProcSeqID");
			Long[] lngProcSeqId = (Long[])frmPreAuthTariffDetail.get("procSeqID");
			String[] strProcedureAmt = request.getParameterValues("procedureAmt");
			String[] strProcDesc = request.getParameterValues("procDesc");
			ArrayList<Object> alAilmentDetail = new ArrayList<Object>();
			ArrayList<Object> alProcedureList=null;
			TariffProcedureVO tariffProcedureVO=null;
			if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
			{
				for(int i=0;i<lngICDPCSSeqID.length;i++)
				{
					TariffAilmentVO tariffAilmentVO = new TariffAilmentVO();
					tariffAilmentVO.setAilmentCapsSeqID((lngAilmentCapsSeqID[i]));
					tariffAilmentVO.setICDPCSSeqID((lngICDPCSSeqID[i]));
					tariffAilmentVO.setDescription(strDescription[i]);
					//approvedAilmentAmt
					tariffAilmentVO.setAilmentApprovedAmt(TTKCommon.getBigDecimal(strApprovedAilmentAmt[i]));
					//tariffAilmentVO.setAilmentMaxAllowedAmt(TTKCommon.getBigDecimal(strMaxAilmentAllowedAmt[i]));
					//tariffAilmentVO.setApprovedAilmentAmt(strApprovedAilmentAmt[i]);
					//tariffAilmentVO.setMaxAilmentAllowedAmt((strMaxAilmentAllowedAmt[i]));
					//tariffAilmentVO.setAilmentNotes(strAilmentNotes[i]);
					alProcedureList=null;
					for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
					{
						if(lngAsscICDPCSSeqID[j].longValue()==lngICDPCSSeqID[i].longValue())
						{
							if(alProcedureList == null){
								alProcedureList = new ArrayList<Object>();							
							}//end of if(alLineItemList == null)
							tariffProcedureVO=new TariffProcedureVO();
							tariffProcedureVO.setPatProcSeqID(lngPatProcSeqID[j]);
							tariffProcedureVO.setProcSeqID(lngProcSeqId[j]);
							tariffProcedureVO.setProcDesc(strProcDesc[j]);
							tariffProcedureVO.setProcedureAmt(strProcedureAmt[j]);
							alProcedureList.add(tariffProcedureVO);						
						}//end ofif(lngAsscICDPCSSeqID[j]==lngICDPCSSeqID[i])
						
					}//end of for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
					tariffAilmentVO.setProcedureList(alProcedureList);                    
					alAilmentDetail.add(tariffAilmentVO);
				}//end of for(int i=0;i<lngAilmentCapsSeqID.length;i++)
			}//end of if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
			preAuthTariffVO.setAilmentVOList(alAilmentDetail);
			preAuthTariffVO.setTariffHdrSeqID(lngSelectedTariffHdrSeqID);
			frmPreAuthTariffDetail.set("tariffInfo",(TariffDetailVO[])alTariffDetail.toArray(new TariffDetailVO[0]));
			frmPreAuthTariffDetail.set("ailmentInfo",(TariffAilmentVO[])alAilmentDetail.toArray(new TariffAilmentVO[0]));
			strForward="accountHead";
			//request.setAttribute("tariffcalculation","message.tariffcalculation");
			return mapping.findForward(strForward);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTariffDetails));
		}//end of catch(Exception exp)
	}//end of doSubmitAccountHead(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)
	
	/**
	 * This method is used for calulate the updated tariff details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewAccountHead(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside PreAuthTariffAction doViewAccountHead");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();    //get the business object to call DAO
			DynaActionForm frmPreAuthTariffDetail=(DynaActionForm)form;
			frmPreAuthTariffDetail = (DynaActionForm)request.getSession().getAttribute("frmPreAuthTariffDetail");
			PreAuthTariffVO preAuthTariffVO= (PreAuthTariffVO)FormUtils.getFormValues(frmPreAuthTariffDetail,
																	"frmPreAuthTariffDetail",this, mapping, request);
			Long lngSelectedTariffHdrSeqID= (Long)frmPreAuthTariffDetail.get("selectedTariffHdrSeqID");
			Long[] lngSelectedTariffSeqID = (Long[])frmPreAuthTariffDetail.get("selectedTariffSeqID");
			Long[] lngSelectedWardAccGroupSeqID = (Long[])frmPreAuthTariffDetail.get("selectedWardAccGroupSeqID");
			 
			//Tariff values
			String[] strWardDesc = request.getParameterValues("wardDesc");
			String[] strAccGroupDesc = request.getParameterValues("accGroupDesc");
			String[] strTariffRequestedAmt = request.getParameterValues("tariffRequestedAmt");
			String[] strTariffMaxAmtAllowed = request.getParameterValues("tariffMaxAmtAllowed");
			String[] strTariffNotes = request.getParameterValues("selectedTariffNotes");
			String[] strCaptureNbrOfDaysYN = request.getParameterValues("captureNbrOfDaysYN");
			String[] strWardTypeID = request.getParameterValues("wardTypeID");
			String[] strRoomTypeID = request.getParameterValues("roomTypeID");
			String[] strDaysOfStay = request.getParameterValues("daysOfStay");
			String[] strTariffApprovedAmt = request.getParameterValues("tariffApprovedAmt");
			
			ArrayList<Object> alTariffDetail = new ArrayList<Object>();
			if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
            {
                for(int i=0;i<lngSelectedTariffSeqID.length;i++)
                {
                    TariffDetailVO tariffDetailVO = new TariffDetailVO();
                    tariffDetailVO.setTariffSeqID(lngSelectedTariffSeqID[i]);
                    tariffDetailVO.setWardAccGroupSeqID(lngSelectedWardAccGroupSeqID[i]);
                    tariffDetailVO.setWardDesc((strWardDesc[i]));
                    tariffDetailVO.setAccGroupDesc(strAccGroupDesc[i]);
                    tariffDetailVO.setRequestedAmt(TTKCommon.getBigDecimal(strTariffRequestedAmt[i]));
                    tariffDetailVO.setApprovedAmt(TTKCommon.getBigDecimal(strTariffApprovedAmt[i]));
                    tariffDetailVO.setMaxAmtAllowed(TTKCommon.getBigDecimal(strTariffMaxAmtAllowed[i]));
                    tariffDetailVO.setTariffRequestedAmt((strTariffRequestedAmt[i]));
                    tariffDetailVO.setTariffApprovedAmt((strTariffApprovedAmt[i]));
                    tariffDetailVO.setTariffMaxAmtAllowed((strTariffMaxAmtAllowed[i]));
                    tariffDetailVO.setTariffNotes(strTariffNotes[i]);                    
                    tariffDetailVO.setCaptureNbrOfDaysYN(strCaptureNbrOfDaysYN[i]);
                    tariffDetailVO.setWardTypeID(strWardTypeID[i]);
                    tariffDetailVO.setRoomTypeID(strRoomTypeID[i]);
    				tariffDetailVO.setDaysOfStay(strDaysOfStay[i]);    				
                    alTariffDetail.add(tariffDetailVO);
                }//end of  for(int i=0;i<lngTariffSeqID.length;i++)
            }//end of if(lngSelectedTariffSeqID !=null && lngSelectedTariffSeqID.length>0)
			preAuthTariffVO.setTariffDetailVOList(alTariffDetail);
			//Ailment Values
			Long[] lngAilmentCapsSeqID = (Long[])frmPreAuthTariffDetail.get("selectedAilmentCapsSeqID");
			Long[] lngICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("selectedICDPCSSeqID");
			String[] strDescription = request.getParameterValues("description");
			//String[] strApprovedAilmentAmt = request.getParameterValues("approvedAilmentAmt");
			//String[] strMaxAilmentAllowedAmt = request.getParameterValues("maxAilmentAllowedAmt");
			//String[] strAilmentNotes = request.getParameterValues("selectedAilmentNotes");
			Long[] lngPatProcSeqID = (Long[])frmPreAuthTariffDetail.get("patProcSeqID");
			Long[] lngProcSeqId = (Long[])frmPreAuthTariffDetail.get("procSeqID");
			Long[] lngAsscICDPCSSeqID = (Long[])frmPreAuthTariffDetail.get("asscICDPCSSeqID");			
			//String[] strProcedureAmt = request.getParameterValues("procedureAmt");
			String[] strProcDesc = request.getParameterValues("procDesc");

			ArrayList<Object> alAilmentDetail = new ArrayList<Object>();
			ArrayList<Object> alProcedureList=null;
			TariffProcedureVO tariffProcedureVO=null;	
			if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
            {
                for(int i=0;i<lngICDPCSSeqID.length;i++)
                {
                    TariffAilmentVO tariffAilmentVO = new TariffAilmentVO();
                    tariffAilmentVO.setAilmentCapsSeqID((lngAilmentCapsSeqID[i]));
                    tariffAilmentVO.setICDPCSSeqID((lngICDPCSSeqID[i]));
                    tariffAilmentVO.setDescription(strDescription[i]);
                    //Clear Ailment approved amount and Procedure amount before navigating to Account heads selection screen
                    tariffAilmentVO.setAilmentApprovedAmt(null);
                    //tariffAilmentVO.setAilmentMaxAllowedAmt(TTKCommon.getBigDecimal(strMaxAilmentAllowedAmt[i]));
                    //tariffAilmentVO.setApprovedAilmentAmt(strApprovedAilmentAmt[i]);
                    //tariffAilmentVO.setMaxAilmentAllowedAmt((strMaxAilmentAllowedAmt[i]));
                    //tariffAilmentVO.setAilmentNotes(strAilmentNotes[i]);
                    alProcedureList=null;
                    for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
    				{
                    	if(lngAsscICDPCSSeqID[j].longValue()==lngICDPCSSeqID[i].longValue())
    					{
    						if(alProcedureList == null){
    							alProcedureList = new ArrayList<Object>();							
    						}//end of if(alLineItemList == null)
    						tariffProcedureVO=new TariffProcedureVO();
    						tariffProcedureVO.setPatProcSeqID(lngPatProcSeqID[j]);
    						tariffProcedureVO.setProcSeqID(lngProcSeqId[j]);
    						tariffProcedureVO.setProcDesc(strProcDesc[j]);
    						tariffProcedureVO.setProcedureAmt("");
    						alProcedureList.add(tariffProcedureVO);						
    					}//end of if(lngAsscICDPCSSeqID[j]==lngICDPCSSeqID[i])
    				}//end of for
    				tariffAilmentVO.setProcedureList(alProcedureList);                    
                    alAilmentDetail.add(tariffAilmentVO);
                }//end of for(int i=0;i<lngAilmentCapsSeqID.length;i++)
            }//end of if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
			preAuthTariffVO.setAilmentVOList(alAilmentDetail);
			preAuthTariffVO.setTariffHdrSeqID(lngSelectedTariffHdrSeqID);
			preAuthTariffVO.setPkgRequestedAmt(TTKCommon.getBigDecimal(request.getParameter("requestedPkgAmt")));
			//PkgApprovedAmt has set with "" value to make sure that on hit of calculate button the value is removed
			//from the jsp
			preAuthTariffVO.setPkgApprovedAmt(TTKCommon.getBigDecimal(""));
			preAuthTariffVO.setPkgMaxAmtAllowed(TTKCommon.getBigDecimal(request.getParameter("maxPkgAmtAllowed")));
			preAuthTariffVO.setNotes(request.getParameter("selectedPkgNotes"));
			preAuthTariffVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			preAuthTariffVO.setUpdatedBy(TTKCommon.getUserSeqId(request));			
			preAuthTariffVO.setClickSave("Y");
			
			log.debug("Saving the tariffdetails and calculating the limits");
			long lngHedSeqId=preAuthObject.saveAccountHeadTariffDetail(preAuthTariffVO);
			log.debug("lngHedSeqId value is :"+lngHedSeqId);
			HashMap hmAccountHeadInfo=preAuthObject.getAccountHeadInfoList(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			request.setAttribute("alCommonAccountHead",hmAccountHeadInfo.get("N"));
			request.setAttribute("alOtherAccountHead",hmAccountHeadInfo.get("Y"));
			
			return this.getForward("accountHead",mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTariffDetails));
		}//end of catch(Exception exp)
	}//end of doViewAccountHead(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used for calulate the updated tariff details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSaveAccountHead(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside PreAuthTariffAction doSaveAccountHead");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();    //get the business object to call DAO
			DynaActionForm frmPreAuthTariffDetail=(DynaActionForm)form;
			//log.info("----  selectedWardIds   ------ "+frmPreAuthTariffDetail.get("selectedWardIds"));
			
			preAuthObject.saveSelectedAccountHeads(PreAuthWebBoardHelper.getPreAuthSeqId(request),frmPreAuthTariffDetail.get("selectedWardIds").toString(),TTKCommon.getUserSeqId(request));
			request.setAttribute("updated","message.addedSuccessfully");
			//Requery the information
			HashMap hmAccountHeadInfo=preAuthObject.getAccountHeadInfoList(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			request.setAttribute("alCommonAccountHead",hmAccountHeadInfo.get("N"));
			request.setAttribute("alOtherAccountHead",hmAccountHeadInfo.get("Y"));
		
			return this.getForward("accountHead",mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTariffDetails));
		}//end of catch(Exception exp)
	}//end of doSaveAccountHead(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside PreAuthTariffAction doClose");
						
			return this.getForward(strTariffdetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strTariffDetails));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	

	public DynaActionForm getTariffCalDetails(DynaActionForm frmPreAuthTariffDetail,PreAuthTariffVO preAuthTariffVO, ActionMapping mapping, HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try{
			PreAuthManager preAuthObject= this.getPreAuthManagerObject();    //get the business object to call DAO
			//DynaActionForm frmPreAuthTariffDetail=(DynaActionForm)form;
			ArrayList<Object> alParameters  = new ArrayList<Object>();
			alParameters.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			alParameters.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
			alParameters.add(PreAuthWebBoardHelper.getPolicySeqId(request));
			alParameters.add(TTKCommon.getUserSeqId(request));
			preAuthTariffVO= preAuthObject.getAccountHeadTariffDetail(alParameters);
			if(preAuthTariffVO==null)
			{
				preAuthTariffVO=new PreAuthTariffVO();
			}//end of if(preAuthTariffVO==null)
			else
			{
				ArrayList<Object> alTariffDetailInfo =preAuthTariffVO.getTariffDetailVOList();
				ArrayList<Object> alAilmentInfo =preAuthTariffVO.getAilmentVOList();
				frmPreAuthTariffDetail = (DynaActionForm)FormUtils.setFormValues("frmPreAuthTariffDetail",
																			preAuthTariffVO,this,mapping,request);
				if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)
				{
					frmPreAuthTariffDetail.set("tariffInfo",(TariffDetailVO[])alTariffDetailInfo.toArray(
																						new TariffDetailVO[0]));
				}//end of  if(alTariffDetailInfo!=null && alTariffDetailInfo.size()>0)
				if(alAilmentInfo!=null && alAilmentInfo.size()>0)
				{
					frmPreAuthTariffDetail.set("ailmentInfo",(TariffAilmentVO[])alAilmentInfo.toArray(
																						new TariffAilmentVO[0]));
				}//end of  if(alAilmentInfo!=null && alAilmentInfo.size()>0)
				if(preAuthTariffVO.getAccountYN().equals("Y"))
				{
					request.setAttribute("medicalnonpackage","error.claims.medical.nonpackage");
				}//end of if(preAuthTariffVO.getAccountYN().equals("Y"))
			}//end of else		
		}catch(Exception exp)
		{
			throw new TTKException(exp, strTariffDetails);
		}//end of catch
		return frmPreAuthTariffDetail;
	}//end of getTariffCalDetails
	
	/**
	 * This method returns the PreAuthManager session object for invoking DAO methods from it.
	 * @return PreAuthManager session object which can be used for method invokation
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
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strTariffDetails);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()
}//end of PreAuthTariffAction.java
