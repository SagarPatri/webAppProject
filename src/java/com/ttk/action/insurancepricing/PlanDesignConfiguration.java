

//Bajaj Change request 1274A

/**
* @ (#) InsureApproveConfiguration.java Jun 23, 2011
* Project       : TTK HealthCare Services
* File          : InsureApproveConfiguration.java
* Author        : Satya Moganti
* Company       : RCS 
* Date Created  : Jun 23, 2011
*
* @author       :  Satya 
* Modified date :  
* Reason        :  Bajaj Insurance Approve Configuartion
*/


/**
* 
*/

package com.ttk.action.insurancepricing;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.InsuranceApproveVO;
import com.ttk.business.empanelment.InsuranceManager;


import com.ttk.dto.insurancepricing.PolicyConfigVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.PreAuthDetailVO;

import formdef.plugin.util.FormUtils;


public class PlanDesignConfiguration extends TTKAction  {
	
	//private static final Logger log = Logger.getLogger( PlanDesignConfiguration.class );
	
	 
	private static final String strInsuranceConfig="insuranceconfig";
	private static final String strGenerateQuote="generatequote";
	private static final String strProductClose="productclose";
	private static final String strPolicyClose="policyclose";
	private static final String strGenerateConfig="generateConfig";
	private static final String strInsError="insurance";
	
	/**
	 * This method is used to forward to configuration screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
					DynaActionForm frmPolicyConfig = (DynaActionForm)form;
	
					PolicyConfigVO policyConfigVO = null;
			
					 
					long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
					String showflag = (String)request.getSession().getAttribute("showflag");
				
				/*	 
						if(TTKCommon.checkNull(request.getParameter("Message")).equals("Y"))
						{
							frmPolicyConfig.set("Message",request.getParameter("Message")); 
			            	TTKException expTTK = new TTKException();
							expTTK.setMessage("error.pricing.required");
							throw expTTK;
							
						}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			    		else
			    		{
			    			frmPolicyConfig.set("Message","N");  	
			    		}
					//Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
*/						if(GroupProfileSeqID > 0)
						{
						policyConfigVO = insuranceObject.getPlanDesignConfigData(GroupProfileSeqID);
						
		
						
						frmPolicyConfig.set("Message","N"); 
						}
						else
						{
							frmPolicyConfig.set("Message",request.getParameter("Message")); 
							frmPolicyConfig.set("Message","Y"); 
			            	TTKException expTTK = new TTKException();
							expTTK.setMessage("error.pricing.required");
							throw expTTK;
							
						}
						
						
						if(policyConfigVO.getShowflag().equals("Y"))
						{
						frmPolicyConfig = (DynaActionForm)FormUtils.setFormValues("frmPolicyConfig",policyConfigVO, this, mapping, request);
						}
						else
						{
							frmPolicyConfig.initialize(mapping);
		/*GROUP LEVEL FEATRUES*/
						frmPolicyConfig.set("transportaionOverseasLimit", "0");
						frmPolicyConfig.set("repatriationRemainsLimit", "0");
						frmPolicyConfig.set("emergencyEvalAML", "0");
						frmPolicyConfig.set("electiveOutsideCoverDays", "0");
						frmPolicyConfig.set("groundTransportaionNumeric", "0");
						frmPolicyConfig.set("perLifeAML", "0");
						frmPolicyConfig.set("maternityCopayGroupNumeric", "0");

						
						
		/*INPATIENT*/
						frmPolicyConfig.set("annualMaxLimit", "0");
						frmPolicyConfig.set("cashBenefitAML", "0");
						frmPolicyConfig.set("emergencyDental", "0");
						frmPolicyConfig.set("emergencyMaternity", "0");
						frmPolicyConfig.set("companionBenefitAMl", "0");
						frmPolicyConfig.set("inpatientICUAML", "0");
						
		/*PHARMACY*/
						
						frmPolicyConfig.set("amlPharmacy", "0");
						frmPolicyConfig.set("chronicAML", "0");
						frmPolicyConfig.set("preauthLimitVIP", "0");
						frmPolicyConfig.set("preauthLimitNonVIP", "0");
						frmPolicyConfig.set("chronicPharmacyCopayNum", "0");
						
	/*LAB AND DIAGNOSTICS*/
						
						frmPolicyConfig.set("oncologyTestAML", "0");
						frmPolicyConfig.set("labsAndDiagnosticsAML", "0");
						
	/*OP CONSULTATIONS*/
						frmPolicyConfig.set("consultationAML", "0");
						frmPolicyConfig.set("gpConsultationNum", "0");
						frmPolicyConfig.set("specConsultationNum", "0");
						frmPolicyConfig.set("physiotherapySession", "0");
						frmPolicyConfig.set("physiotherapyAMLLimit", "0");
						
						frmPolicyConfig.set("noOfmaternityConsults", "0");
						frmPolicyConfig.set("maternityConsultationsNum", "0");
						frmPolicyConfig.set("chronicDiseaseConsults", "0");
						frmPolicyConfig.set("chronicDiseaseAML", "0");
						frmPolicyConfig.set("chronicDiseaseDeductible", "0");
						frmPolicyConfig.set("opConsultationCopayListNum", "0");
						
	/*MATERNITY*/
						
						frmPolicyConfig.set("normalDeliveryAML", "0");
						frmPolicyConfig.set("maternityCsectionAML", "0");
						frmPolicyConfig.set("maternityComplicationAML", "0");
						frmPolicyConfig.set("emergencyMaternityAML", "0");


						
	/*DENTAL*/
						frmPolicyConfig.set("dentalAML", "0"); 
						frmPolicyConfig.set("dentalDeductible", "0");
						frmPolicyConfig.set("cleaningAML", "0");
						frmPolicyConfig.set("orthodonticsAML", "0");
						frmPolicyConfig.set("emergencyDentalAML", "0");
						
						
	/*CHRONIC*/
						frmPolicyConfig.set("chronicAMLNum", "0");
						frmPolicyConfig.set("pharmacyAML", "0");
						frmPolicyConfig.set("pharmacyAMLAmount", "0");
						frmPolicyConfig.set("chronicLabDiag", "0");
						frmPolicyConfig.set("chronicLabDiagAmount", "0");
						frmPolicyConfig.set("chronicConsultationAML", "0");
						frmPolicyConfig.set("chronicConsultation", "0");
						frmPolicyConfig.set("chronicCopayDeductibleAmount", "0");
						
						
/*	PED*/
						frmPolicyConfig.set("pedAML", "0");
						frmPolicyConfig.set("pedDeductible", "0");
	/*PSHYCIATRY*/
						frmPolicyConfig.set("inpatientAML", "0");
						frmPolicyConfig.set("noofInpatientDays", "0");
						frmPolicyConfig.set("outpatientAML", "0");
						frmPolicyConfig.set("noofOutpatientConsul", "0");
						frmPolicyConfig.set("psychiatryDeductible", "0");
						
	/*OPTICAL	*/
						
						frmPolicyConfig.set("frameContactLensAML", "0");
						frmPolicyConfig.set("opticalConsulAmount", "0");
						frmPolicyConfig.set("opticalAML", "0");
						frmPolicyConfig.set("emergencyOpticalAML", "0");
						
		
	/*ALTERNATIVE MEDICINES*/
						frmPolicyConfig.set("alternativeAML", "0"); 
						 
						}
						if(GroupProfileSeqID > 0)
						{
							frmPolicyConfig.set("Message","N"); 
							
						}
						if(policyConfigVO.getShowflag().equals("Y"))
						{
							if(policyConfigVO.getInitialWatiingPeriod() != null)
							{
							frmPolicyConfig.set("initialWatiingPeriod",policyConfigVO.getInitialWatiingPeriod().toString()); 
							}
							if(policyConfigVO.getOpConsultationCopayList() != null)
							{
							frmPolicyConfig.set("opConsultationCopayList",policyConfigVO.getOpConsultationCopayList().toString()); 
							}
							if(policyConfigVO.getMaternityConsultations() != null)
							{
							frmPolicyConfig.set("maternityConsultations",policyConfigVO.getMaternityConsultations().toString()); 
							}
							if(policyConfigVO.getNonNetworkRemCopayGroup() != null)
							{
							frmPolicyConfig.set("nonNetworkRemCopayGroup",policyConfigVO.getNonNetworkRemCopayGroup().toString()); 
							}
							if(policyConfigVO.getChronicCopayDeductibleCopay() != null)
							{
							frmPolicyConfig.set("chronicCopayDeductibleCopay",policyConfigVO.getChronicCopayDeductibleCopay().toString()); 
							}
							if(policyConfigVO.getElectiveOutsideCoverDays() != null)
							{
							frmPolicyConfig.set("electiveOutsideCoverDays",(policyConfigVO.getElectiveOutsideCoverDays().toString())); 
							}
							
							if(policyConfigVO.getTransportaionOverseasLimit() != null)
							{
							frmPolicyConfig.set("transportaionOverseasLimit",(policyConfigVO.getTransportaionOverseasLimit().toString())); 
							}
							if(policyConfigVO.getRepatriationRemainsLimit() != null)
							{
							frmPolicyConfig.set("repatriationRemainsLimit",(policyConfigVO.getRepatriationRemainsLimit().toString())); 
							}
							if(policyConfigVO.getEmergencyEvalAML() != null)
							{
							frmPolicyConfig.set("emergencyEvalAML",(policyConfigVO.getEmergencyEvalAML().toString())); 
							}
							if(policyConfigVO.getPerLifeAML() != null)
							{
							frmPolicyConfig.set("perLifeAML",(policyConfigVO.getPerLifeAML().toString())); 
							}
							if(policyConfigVO.getGroundTransportaionNumeric() != null)
							{
							frmPolicyConfig.set("groundTransportaionNumeric",(policyConfigVO.getGroundTransportaionNumeric().toString())); 
							}
							if(policyConfigVO.getMaternityCopayGroupNumeric() != null)
							{
							frmPolicyConfig.set("maternityCopayGroupNumeric",(policyConfigVO.getMaternityCopayGroupNumeric().toString())); 
							}
							
							if(policyConfigVO.getInpatientICUAML() != null)
							{
							frmPolicyConfig.set("inpatientICUAML",(policyConfigVO.getInpatientICUAML().toString())); 
							}
							if(policyConfigVO.getAnnualMaxLimit() != null)
							{
							frmPolicyConfig.set("annualMaxLimit",(policyConfigVO.getAnnualMaxLimit().toString())); 
							}
							if(policyConfigVO.getCompanionBenefitAMl() != null)
							{
							frmPolicyConfig.set("companionBenefitAMl",(policyConfigVO.getCompanionBenefitAMl().toString())); 
							}
							if(policyConfigVO.getCashBenefitAML() != null)
							{
							frmPolicyConfig.set("cashBenefitAML",(policyConfigVO.getCashBenefitAML().toString())); 
							}

							
							if(policyConfigVO.getAmlPharmacy() != null)
							{
							frmPolicyConfig.set("amlPharmacy",(policyConfigVO.getAmlPharmacy().toString())); 
							}
							if(policyConfigVO.getPreauthLimitVIP() != null)
							{
							frmPolicyConfig.set("preauthLimitVIP",(policyConfigVO.getPreauthLimitVIP().toString())); 
							}
							if(policyConfigVO.getPreauthLimitNonVIP() != null)
							{
							frmPolicyConfig.set("preauthLimitNonVIP",(policyConfigVO.getPreauthLimitNonVIP().toString())); 
							}

							if(policyConfigVO.getOncologyTestAML() != null)
							{
							frmPolicyConfig.set("oncologyTestAML",(policyConfigVO.getOncologyTestAML().toString())); 
							}
							if(policyConfigVO.getLabsAndDiagnosticsAML() != null)
							{
							frmPolicyConfig.set("labsAndDiagnosticsAML",(policyConfigVO.getLabsAndDiagnosticsAML().toString())); 
							}

							
							if(policyConfigVO.getConsultationAML() != null)
							{
							frmPolicyConfig.set("consultationAML",(policyConfigVO.getConsultationAML().toString())); 
							}
							if(policyConfigVO.getSpecConsultationNum() != null)
							{
							frmPolicyConfig.set("specConsultationNum",(policyConfigVO.getSpecConsultationNum().toString())); 
							}
							if(policyConfigVO.getPhysiotherapySession() != null)
							{
							frmPolicyConfig.set("physiotherapySession",(policyConfigVO.getPhysiotherapySession().toString())); 
							}
							if(policyConfigVO.getPhysiotherapyAMLLimit() != null)
							{
							frmPolicyConfig.set("physiotherapyAMLLimit",(policyConfigVO.getPhysiotherapyAMLLimit().toString())); 
							}
							if(policyConfigVO.getOpConsultationCopayListNum() != null)
							{
							frmPolicyConfig.set("opConsultationCopayListNum",(policyConfigVO.getOpConsultationCopayListNum().toString())); 
							}
							
							
							if(policyConfigVO.getNormalDeliveryAML() != null)
							{
							frmPolicyConfig.set("normalDeliveryAML",(policyConfigVO.getNormalDeliveryAML().toString())); 
							}
							if(policyConfigVO.getMaternityCsectionAML() != null)
							{
							frmPolicyConfig.set("maternityCsectionAML",(policyConfigVO.getMaternityCsectionAML().toString())); 
							}
							if(policyConfigVO.getMaternityComplicationAML() != null)
							{
							frmPolicyConfig.set("maternityComplicationAML",(policyConfigVO.getMaternityComplicationAML().toString())); 
							}
							if(policyConfigVO.getNoOfmaternityConsults() != null)
							{
							frmPolicyConfig.set("noOfmaternityConsults",(policyConfigVO.getNoOfmaternityConsults().toString())); 
							}
							if(policyConfigVO.getMaternityConsultationsNum() != null)
							{
							frmPolicyConfig.set("maternityConsultationsNum",(policyConfigVO.getMaternityConsultationsNum().toString())); 
							}
							if(policyConfigVO.getEmergencyMaternityAML() != null)
							{
							frmPolicyConfig.set("emergencyMaternityAML",(policyConfigVO.getEmergencyMaternityAML().toString())); 
							}
							
							
							if(policyConfigVO.getDentalAML() != null)
							{
							frmPolicyConfig.set("dentalAML",(policyConfigVO.getDentalAML().toString())); 
							}
							if(policyConfigVO.getDentalDeductible() != null)
							{
							frmPolicyConfig.set("dentalDeductible",(policyConfigVO.getDentalDeductible().toString())); 
							}
							if(policyConfigVO.getCleaningAML() != null)
							{
							frmPolicyConfig.set("cleaningAML",(policyConfigVO.getCleaningAML().toString())); 
							}
							if(policyConfigVO.getOrthodonticsAML() != null)
							{
							frmPolicyConfig.set("orthodonticsAML",(policyConfigVO.getOrthodonticsAML().toString())); 
							}
							if(policyConfigVO.getEmergencyDentalAML() != null)
							{
							frmPolicyConfig.set("emergencyDentalAML",(policyConfigVO.getEmergencyDentalAML().toString())); 
							}
							
	
							if(policyConfigVO.getChronicAMLNum() != null)
							{
							frmPolicyConfig.set("chronicAMLNum",(policyConfigVO.getChronicAMLNum().toString())); 
							}
							if(policyConfigVO.getPharmacyAML() != null)
							{
							frmPolicyConfig.set("pharmacyAML",(policyConfigVO.getPharmacyAML().toString())); 
							}
							if(policyConfigVO.getPharmacyAMLAmount() != null)
							{
							frmPolicyConfig.set("pharmacyAMLAmount",(policyConfigVO.getPharmacyAMLAmount().toString())); 
							}
							if(policyConfigVO.getChronicLabDiag() != null)
							{
							frmPolicyConfig.set("chronicLabDiag",(policyConfigVO.getChronicLabDiag().toString())); 
							}
							if(policyConfigVO.getChronicLabDiagAmount() != null)
							{
							frmPolicyConfig.set("chronicLabDiagAmount",(policyConfigVO.getChronicLabDiagAmount().toString())); 
							}
							if(policyConfigVO.getChronicConsultationAML() != null)
							{
							frmPolicyConfig.set("chronicConsultationAML",(policyConfigVO.getChronicConsultationAML().toString())); 
							}
							if(policyConfigVO.getChronicConsultation() != null)
							{
							frmPolicyConfig.set("chronicConsultation",(policyConfigVO.getChronicConsultation().toString())); 
							}
							if(policyConfigVO.getChronicCopayDeductibleAmount() != null)
							{
							frmPolicyConfig.set("chronicCopayDeductibleAmount",(policyConfigVO.getChronicCopayDeductibleAmount().toString())); 
							}

							if(policyConfigVO.getPedAML() != null)
							{
							frmPolicyConfig.set("pedAML",(policyConfigVO.getPedAML().toString())); 
							}
							if(policyConfigVO.getPedDeductible() != null)
							{
							frmPolicyConfig.set("pedDeductible",(policyConfigVO.getPedDeductible().toString())); 
							}
						
							
							if(policyConfigVO.getInpatientAML() != null)
							{
							frmPolicyConfig.set("inpatientAML",(policyConfigVO.getInpatientAML().toString())); 
							}
							if(policyConfigVO.getNoofInpatientDays() != null)
							{
							frmPolicyConfig.set("noofInpatientDays",(policyConfigVO.getNoofInpatientDays().toString())); 
							}
							if(policyConfigVO.getOutpatientAML() != null)
							{
							frmPolicyConfig.set("outpatientAML",(policyConfigVO.getOutpatientAML().toString())); 
							}
							if(policyConfigVO.getNoofOutpatientConsul() != null)
							{
							frmPolicyConfig.set("noofOutpatientConsul",(policyConfigVO.getNoofOutpatientConsul().toString())); 
							}
							if(policyConfigVO.getPsychiatryDeductible() != null)
							{
							frmPolicyConfig.set("psychiatryDeductible",(policyConfigVO.getPsychiatryDeductible().toString())); 
							}
							
							
							if(policyConfigVO.getFrameContactLensAML() != null)
							{
							frmPolicyConfig.set("frameContactLensAML",(policyConfigVO.getFrameContactLensAML().toString())); 
							}
							if(policyConfigVO.getOpticalConsulAmount() != null)
							{
							frmPolicyConfig.set("opticalConsulAmount",(policyConfigVO.getOpticalConsulAmount().toString())); 
							}
							if(policyConfigVO.getOpticalAML() != null)
							{
							frmPolicyConfig.set("opticalAML",(policyConfigVO.getOpticalAML().toString())); 
							}
							if(policyConfigVO.getEmergencyOpticalAML() != null)
							{
							frmPolicyConfig.set("emergencyOpticalAML",(policyConfigVO.getEmergencyOpticalAML().toString())); 
							}
							
							if(policyConfigVO.getAlternativeAML() != null)
							{
							frmPolicyConfig.set("alternativeAML",(policyConfigVO.getAlternativeAML().toString())); 
							}
					
				
		

						}
						
					request.getSession().setAttribute("GroupProfileSeqID", GroupProfileSeqID); 
					 request.getSession().setAttribute("frmPolicyConfig",frmPolicyConfig); 
					return this.getForward(strInsuranceConfig, mapping, request);
				}
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
				}//end of catch(Exception exp)
		
			}//end of  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           // HttpServletResponse response)
	
	/**
	 * This method is used to forward to configuration screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward  doDefaultQuotation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);

					DynaActionForm frmPolicyConfigQuote = (DynaActionForm)form;
					long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");

					PolicyConfigVO policyConfigVO = null;
					InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
					ArrayList<PolicyConfigVO> PremiumFirst=null;
					ArrayList<PolicyConfigVO> PremiumSecond=null;
					if(GroupProfileSeqID > 0)
					{
						Object[]	premiumProposal = insuranceObject.selectGenerateQuote(GroupProfileSeqID);
						PolicyConfigVO policyConfigVO1 = (PolicyConfigVO) premiumProposal[0];
						PremiumFirst = (ArrayList<PolicyConfigVO>) premiumProposal[1];
						PremiumSecond = (ArrayList<PolicyConfigVO>) premiumProposal[2];
					frmPolicyConfigQuote = (DynaActionForm)FormUtils.setFormValues("frmPolicyConfigQuote",policyConfigVO1, this, mapping, request);
					frmPolicyConfigQuote.set("Message","N"); 
					}
					else
					{
						((DynaActionForm)form).initialize(mapping);
						frmPolicyConfigQuote.set("Message","Y"); 
		            	TTKException expTTK = new TTKException();
						expTTK.setMessage("error.pricing.required");
						throw expTTK;
					}
					//Long  lngProdPolicySeqId = TTKCommon.getWebBoardId(request);
					/*insuranceApproveVO = productpolicyObject.getConfigInsuranceApproveData(lngProdPolicySeqId);
					 
					frmConfigInsuranceApprove = (DynaActionForm)FormUtils.setFormValues("frmConfigInsuranceApprove",insuranceApproveVO,this,mapping,request);
					frmConfigInsuranceApprove.set("caption",buildCaption(request));*/ 
					request.getSession().setAttribute("PremiumFirst",PremiumFirst);
					frmPolicyConfigQuote.set("PremiumFirst",PremiumFirst);
					frmPolicyConfigQuote.set("PremiumSecond",PremiumSecond);
					request.getSession().setAttribute("frmPolicyConfigQuote",frmPolicyConfigQuote); 
					return this.getForward(strGenerateQuote, mapping, request);
				}
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
				}//end of catch(Exception exp)
		
			}//end of  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           // HttpServletResponse response)
	
	
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
			HttpServletResponse response) throws TTKException
	{
		try
		{
			setLinks(request); 
			InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			DynaActionForm frmPolicyConfig = (DynaActionForm)form;
			PolicyConfigVO policyConfigVO=null;
			 
			long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
			
			policyConfigVO =(PolicyConfigVO)FormUtils.getFormValues(frmPolicyConfig, "frmPolicyConfig",this, mapping, request); 
			
			 
			
			
			//as per 1274 A latest changes
			policyConfigVO.setGroupProfileSeqID(GroupProfileSeqID);
			 
			int  iResult = insuranceObject.savePlanDesignConfig(policyConfigVO);
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
					policyConfigVO = insuranceObject.getPlanDesignConfigData(GroupProfileSeqID);
					frmPolicyConfig = (DynaActionForm)FormUtils.setFormValues("frmPolicyConfig",policyConfigVO, this, mapping, request);
					frmPolicyConfig.set("Message","N"); 
					//BigDecimal test=policyConfigVO.getInitialWatiingPeriod();
					if(policyConfigVO.getInitialWatiingPeriod() != null)
					{
					frmPolicyConfig.set("initialWatiingPeriod",policyConfigVO.getInitialWatiingPeriod().toString()); 
					}
					if(policyConfigVO.getOpConsultationCopayList() != null)
					{
					frmPolicyConfig.set("opConsultationCopayList",policyConfigVO.getOpConsultationCopayList().toString()); 
					}
					if(policyConfigVO.getMaternityConsultations() != null)
					{
					frmPolicyConfig.set("maternityConsultations",policyConfigVO.getMaternityConsultations().toString()); 
					}
					if(policyConfigVO.getNonNetworkRemCopayGroup() != null)
					{
					frmPolicyConfig.set("nonNetworkRemCopayGroup",policyConfigVO.getNonNetworkRemCopayGroup().toString()); 
					}
					if(policyConfigVO.getChronicCopayDeductibleCopay() != null)
					{
					frmPolicyConfig.set("chronicCopayDeductibleCopay",policyConfigVO.getChronicCopayDeductibleCopay().toString()); 
					}
					if(policyConfigVO.getElectiveOutsideCoverDays() != null)
					{
					frmPolicyConfig.set("electiveOutsideCoverDays",(policyConfigVO.getElectiveOutsideCoverDays().toString())); 
					}
					
					if(policyConfigVO.getTransportaionOverseasLimit() != null)
					{
					frmPolicyConfig.set("transportaionOverseasLimit",(policyConfigVO.getTransportaionOverseasLimit().toString())); 
					}
					if(policyConfigVO.getRepatriationRemainsLimit() != null)
					{
					frmPolicyConfig.set("repatriationRemainsLimit",(policyConfigVO.getRepatriationRemainsLimit().toString())); 
					}
					if(policyConfigVO.getEmergencyEvalAML() != null)
					{
					frmPolicyConfig.set("emergencyEvalAML",(policyConfigVO.getEmergencyEvalAML().toString())); 
					}
					if(policyConfigVO.getPerLifeAML() != null)
					{
					frmPolicyConfig.set("perLifeAML",(policyConfigVO.getPerLifeAML().toString())); 
					}
					if(policyConfigVO.getGroundTransportaionNumeric() != null)
					{
					frmPolicyConfig.set("groundTransportaionNumeric",(policyConfigVO.getGroundTransportaionNumeric().toString())); 
					}
					if(policyConfigVO.getMaternityCopayGroupNumeric() != null)
					{
					frmPolicyConfig.set("maternityCopayGroupNumeric",(policyConfigVO.getMaternityCopayGroupNumeric().toString())); 
					}
					
					if(policyConfigVO.getInpatientICUAML() != null)
					{
					frmPolicyConfig.set("inpatientICUAML",(policyConfigVO.getInpatientICUAML().toString())); 
					}
					if(policyConfigVO.getAnnualMaxLimit() != null)
					{
					frmPolicyConfig.set("annualMaxLimit",(policyConfigVO.getAnnualMaxLimit().toString())); 
					}
					if(policyConfigVO.getCompanionBenefitAMl() != null)
					{
					frmPolicyConfig.set("companionBenefitAMl",(policyConfigVO.getCompanionBenefitAMl().toString())); 
					}
					if(policyConfigVO.getCashBenefitAML() != null)
					{
					frmPolicyConfig.set("cashBenefitAML",(policyConfigVO.getCashBenefitAML().toString())); 
					}

					
					if(policyConfigVO.getAmlPharmacy() != null)
					{
					frmPolicyConfig.set("amlPharmacy",(policyConfigVO.getAmlPharmacy().toString())); 
					}
					if(policyConfigVO.getPreauthLimitVIP() != null)
					{
					frmPolicyConfig.set("preauthLimitVIP",(policyConfigVO.getPreauthLimitVIP().toString())); 
					}
					if(policyConfigVO.getPreauthLimitNonVIP() != null)
					{
					frmPolicyConfig.set("preauthLimitNonVIP",(policyConfigVO.getPreauthLimitNonVIP().toString())); 
					}

					if(policyConfigVO.getOncologyTestAML() != null)
					{
					frmPolicyConfig.set("oncologyTestAML",(policyConfigVO.getOncologyTestAML().toString())); 
					}
					if(policyConfigVO.getLabsAndDiagnosticsAML() != null)
					{
					frmPolicyConfig.set("labsAndDiagnosticsAML",(policyConfigVO.getLabsAndDiagnosticsAML().toString())); 
					}

					
					if(policyConfigVO.getConsultationAML() != null)
					{
					frmPolicyConfig.set("consultationAML",(policyConfigVO.getConsultationAML().toString())); 
					}
					if(policyConfigVO.getSpecConsultationNum() != null)
					{
					frmPolicyConfig.set("specConsultationNum",(policyConfigVO.getSpecConsultationNum().toString())); 
					}
					if(policyConfigVO.getPhysiotherapySession() != null)
					{
					frmPolicyConfig.set("physiotherapySession",(policyConfigVO.getPhysiotherapySession().toString())); 
					}
					if(policyConfigVO.getPhysiotherapyAMLLimit() != null)
					{
					frmPolicyConfig.set("physiotherapyAMLLimit",(policyConfigVO.getPhysiotherapyAMLLimit().toString())); 
					}
					if(policyConfigVO.getOpConsultationCopayListNum() != null)
					{
					frmPolicyConfig.set("opConsultationCopayListNum",(policyConfigVO.getOpConsultationCopayListNum().toString())); 
					}
					
					
					if(policyConfigVO.getNormalDeliveryAML() != null)
					{
					frmPolicyConfig.set("normalDeliveryAML",(policyConfigVO.getNormalDeliveryAML().toString())); 
					}
					if(policyConfigVO.getMaternityCsectionAML() != null)
					{
					frmPolicyConfig.set("maternityCsectionAML",(policyConfigVO.getMaternityCsectionAML().toString())); 
					}
					if(policyConfigVO.getMaternityComplicationAML() != null)
					{
					frmPolicyConfig.set("maternityComplicationAML",(policyConfigVO.getMaternityComplicationAML().toString())); 
					}
					if(policyConfigVO.getNoOfmaternityConsults() != null)
					{
					frmPolicyConfig.set("noOfmaternityConsults",(policyConfigVO.getNoOfmaternityConsults().toString())); 
					}
					if(policyConfigVO.getMaternityConsultationsNum() != null)
					{
					frmPolicyConfig.set("maternityConsultationsNum",(policyConfigVO.getMaternityConsultationsNum().toString())); 
					}
					if(policyConfigVO.getEmergencyMaternityAML() != null)
					{
					frmPolicyConfig.set("emergencyMaternityAML",(policyConfigVO.getEmergencyMaternityAML().toString())); 
					}
					
					
					if(policyConfigVO.getDentalAML() != null)
					{
					frmPolicyConfig.set("dentalAML",(policyConfigVO.getDentalAML().toString())); 
					}
					if(policyConfigVO.getDentalDeductible() != null)
					{
					frmPolicyConfig.set("dentalDeductible",(policyConfigVO.getDentalDeductible().toString())); 
					}
					if(policyConfigVO.getCleaningAML() != null)
					{
					frmPolicyConfig.set("cleaningAML",(policyConfigVO.getCleaningAML().toString())); 
					}
					if(policyConfigVO.getOrthodonticsAML() != null)
					{
					frmPolicyConfig.set("orthodonticsAML",(policyConfigVO.getOrthodonticsAML().toString())); 
					}
					if(policyConfigVO.getEmergencyDentalAML() != null)
					{
					frmPolicyConfig.set("emergencyDentalAML",(policyConfigVO.getEmergencyDentalAML().toString())); 
					}
					

					if(policyConfigVO.getChronicAMLNum() != null)
					{
					frmPolicyConfig.set("chronicAMLNum",(policyConfigVO.getChronicAMLNum().toString())); 
					}
					if(policyConfigVO.getPharmacyAML() != null)
					{
					frmPolicyConfig.set("pharmacyAML",(policyConfigVO.getPharmacyAML().toString())); 
					}
					if(policyConfigVO.getPharmacyAMLAmount() != null)
					{
					frmPolicyConfig.set("pharmacyAMLAmount",(policyConfigVO.getPharmacyAMLAmount().toString())); 
					}
					if(policyConfigVO.getChronicLabDiag() != null)
					{
					frmPolicyConfig.set("chronicLabDiag",(policyConfigVO.getChronicLabDiag().toString())); 
					}
					if(policyConfigVO.getChronicLabDiagAmount() != null)
					{
					frmPolicyConfig.set("chronicLabDiagAmount",(policyConfigVO.getChronicLabDiagAmount().toString())); 
					}
					if(policyConfigVO.getChronicConsultationAML() != null)
					{
					frmPolicyConfig.set("chronicConsultationAML",(policyConfigVO.getChronicConsultationAML().toString())); 
					}
					if(policyConfigVO.getChronicConsultation() != null)
					{
					frmPolicyConfig.set("chronicConsultation",(policyConfigVO.getChronicConsultation().toString())); 
					}
					if(policyConfigVO.getChronicCopayDeductibleAmount() != null)
					{
					frmPolicyConfig.set("chronicCopayDeductibleAmount",(policyConfigVO.getChronicCopayDeductibleAmount().toString())); 
					}

					if(policyConfigVO.getPedAML() != null)
					{
					frmPolicyConfig.set("pedAML",(policyConfigVO.getPedAML().toString())); 
					}
					if(policyConfigVO.getPedDeductible() != null)
					{
					frmPolicyConfig.set("pedDeductible",(policyConfigVO.getPedDeductible().toString())); 
					}
				
					
					if(policyConfigVO.getInpatientAML() != null)
					{
					frmPolicyConfig.set("inpatientAML",(policyConfigVO.getInpatientAML().toString())); 
					}
					if(policyConfigVO.getNoofInpatientDays() != null)
					{
					frmPolicyConfig.set("noofInpatientDays",(policyConfigVO.getNoofInpatientDays().toString())); 
					}
					if(policyConfigVO.getOutpatientAML() != null)
					{
					frmPolicyConfig.set("outpatientAML",(policyConfigVO.getOutpatientAML().toString())); 
					}
					if(policyConfigVO.getNoofOutpatientConsul() != null)
					{
					frmPolicyConfig.set("noofOutpatientConsul",(policyConfigVO.getNoofOutpatientConsul().toString())); 
					}
					if(policyConfigVO.getPsychiatryDeductible() != null)
					{
					frmPolicyConfig.set("psychiatryDeductible",(policyConfigVO.getPsychiatryDeductible().toString())); 
					}
					
					
					if(policyConfigVO.getFrameContactLensAML() != null)
					{
					frmPolicyConfig.set("frameContactLensAML",(policyConfigVO.getFrameContactLensAML().toString())); 
					}
					if(policyConfigVO.getOpticalConsulAmount() != null)
					{
					frmPolicyConfig.set("opticalConsulAmount",(policyConfigVO.getOpticalConsulAmount().toString())); 
					}
					if(policyConfigVO.getOpticalAML() != null)
					{
					frmPolicyConfig.set("opticalAML",(policyConfigVO.getOpticalAML().toString())); 
					}
					if(policyConfigVO.getEmergencyOpticalAML() != null)
					{
					frmPolicyConfig.set("emergencyOpticalAML",(policyConfigVO.getEmergencyOpticalAML().toString())); 
					}
					
					if(policyConfigVO.getAlternativeAML() != null)
					{
					frmPolicyConfig.set("alternativeAML",(policyConfigVO.getAlternativeAML().toString())); 
					}
			

					
			}//end of if(iResult>0)
		 
			//frmConfigSumInsured.set("preAuthClaimsExistAlert",preAuthClaimsExistAlert);
			request.getSession().setAttribute("frmPolicyConfig",frmPolicyConfig);

			 
			return this.getForward(strInsuranceConfig, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)
	
	
	public ActionForward doSaveCalculateQuote(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try
		{
			setLinks(request); 
			InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);

			DynaActionForm frmPolicyConfigQuote = (DynaActionForm)form;
			PolicyConfigVO policyConfigVO=null;
			 
			long GroupProfileSeqID = (long)request.getSession().getAttribute("GroupProfileSeqID");
			
			policyConfigVO =(PolicyConfigVO)FormUtils.getFormValues(frmPolicyConfigQuote, "frmPolicyConfigQuote",this, mapping, request); 
			
			 
			
			
			//as per 1274 A latest changes
			policyConfigVO.setGroupProfileSeqID(GroupProfileSeqID);
			 
			policyConfigVO = insuranceObject.saveGenerateQuote(policyConfigVO);
			//Object[]	premiumProposal=null;
			ArrayList<PolicyConfigVO> PremiumFirst=null;
			ArrayList<PolicyConfigVO> PremiumSecond=null;
			if(policyConfigVO.getGenerateflag().equals("Y"))
			{
					request.setAttribute("updated","message.saved");
					Object[]	premiumProposal = insuranceObject.selectGenerateQuote(GroupProfileSeqID);
					PolicyConfigVO policyConfigVO1 = (PolicyConfigVO) premiumProposal[0];
					 PremiumFirst = (ArrayList<PolicyConfigVO>) premiumProposal[1];
					 PremiumSecond = (ArrayList<PolicyConfigVO>) premiumProposal[2];
					frmPolicyConfigQuote = (DynaActionForm)FormUtils.setFormValues("frmPolicyConfigQuote",policyConfigVO1, this, mapping, request);
					frmPolicyConfigQuote.set("Message","N"); 
					
			}//end of if(iResult>0)
		 
	
			
			//frmConfigSumInsured.set("preAuthClaimsExistAlert",preAuthClaimsExistAlert);
			request.getSession().setAttribute("PremiumFirst",PremiumFirst);
			frmPolicyConfigQuote.set("PremiumFirst",PremiumFirst);
			frmPolicyConfigQuote.set("PremiumSecond",PremiumSecond);
			request.getSession().setAttribute("frmPolicyConfigQuote",frmPolicyConfigQuote);

			 
			return this.getForward(strGenerateConfig, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
		}//end of catch(Exception exp)
	}
	
	
	
	
	/**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
		public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws TTKException
	    {
				setLinks(request);
				return doDefault(mapping, form, request, response);
	    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
		
		/**
		 * This method is used to navigate to previous screen when close  button is clicked.
		 * Finally it forwards to the appropriate view based on the specified forward mappings
		 *
		 * @param mapping The ActionMapping used to select this instance
		 * @param form The optional ActionForm bean for this request (if any)
		 * @param request The HTTP request we are processing
		 * @param response The HTTP response we are creating
		 * @return ActionForward Where the control will be forwarded, after this request is processed
		 * @throws Exception if any error occurs
		 */
		public ActionForward doClose(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response) throws TTKException
		{
			try
			{
				setLinks(request);
				String strActiveSubLink=TTKCommon.getActiveSubLink(request);
				String strForward="";
				if("Products".equals(strActiveSubLink)){
					strForward=strProductClose;
				}//end of if("Products".equals(strActiveSubLink))
				else {
					strForward=strPolicyClose;
				}//end of else
				if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				{
					((DynaActionForm)form).initialize(mapping);//reset the form data
				}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				request.getSession().removeAttribute("frmConfigInsuranceApprove");
				return this.getForward(strForward, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, "Configuration"));
			}//end of catch(Exception exp)
		}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		
		
		
		
	/**
	 * This method is to build the Caption 
	 * @param request current HttpRequest
	 * @param form 
	 * @return String caption built
	 * @throws TTKException
	 */
	private String buildCaption(HttpServletRequest request)throws Exception
	{
		String strCaption=null;
		StringBuffer sbfCaption= new StringBuffer();
		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
		if(strActiveSubLink.equals("Policies"))
		{
			DynaActionForm frmPolicies = (DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			sbfCaption.append("Configuration - [ ").append(frmPolicies.get("companyName")).append(" ] [ ").append(frmPolicies.get("policyNbr")).append(" ] ");
		}//end of else if(strActiveSubLink.equals("Policies"))
		if(strActiveSubLink.equals("Products"))
		{
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("Configuration - [").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append(" [").append(frmProductList.get("productName")).append("]");			
		}//end of if(strActiveSubLink.equals("Products"))
		strCaption=String.valueOf(sbfCaption);
		return strCaption;
	}//end of buildCaption(HttpServletRequest request)
	
	/**
     * Returns the InsuranceManager session object for invoking methods on it.
     * @return InsuranceManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private InsuranceManager getInsuranceManagerObject() throws TTKException
	{
		InsuranceManager insuremanager = null;
		try
		{
			if(insuremanager == null)
			{
				InitialContext ctx = new InitialContext();
				insuremanager = (InsuranceManager) ctx.lookup("java:global/TTKServices/business.ejb3/InsuranceManagerBean!com.ttk.business.empanelment.InsuranceManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strInsError);
		}//end of catch
		return insuremanager;
	} // end of private InsuranceManager getInsManagerObject() throws TTKException
    
    
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManager() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end of if(productPolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "Configuration");
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()
}//end of InsureApproveConfiguration
	
