/**
 * @ (#) DynSpecialBenefitsRuleAction.java May 10, 2018
 * Project      : TTK HealthCare Services
 * File         : DynSpecialBenefitsRuleAction.java
 * Author       : Hare Govind
 * Company      : Span Systems Corporation
 * Date Created : May 10, 2006
 *
 * @author       :  Hare Govind
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.administration;

import java.util.ArrayList;
import java.util.Arrays;

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
import com.ttk.business.administration.RuleManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.administration.InvestigationRuleVO;
import com.ttk.dto.common.CacheObject;

import formdef.plugin.util.FormUtils;

public class DynSpecialBenefitsRuleAction extends TTKAction{
	
	private static Logger log = Logger.getLogger(DynSpecialBenefitsRuleAction.class); // Getting Logger for this Class file 
	// vaccinations
	private static final String strVaccinRuleConfig="VaccinRuleConfig";
	private static final String strStaticRuleConfigDetails="StaticRuleConfigDetails";
	
	// alternate treatments
	private static final String strAltTmtRuleConfig="AltTmtRuleConfig"; 
	private static final String strDEOTRuleConfig="DEOTRuleConfig";
	private static final String strEyeCareRuleConfig="EyeCareRuleConfig";
	private static final String strGyncolgyRuleConfig="GyncolgyRuleConfig";
	private static final String strMinrSrgryRuleConfig="MinrSrgryRuleConfig";
	private static final String strNaslCrctionRuleConfig="NaslCrctionRuleConfig";
	private static final String strOnclgyRuleConfig="OnclgyRuleConfig";
	private static final String strOgnTrnspltRcptRuleConfig="OgnTrnspltRcptRuleConfig";
	private static final String strPsychiatricRuleConfig="PsychiatricRuleConfig";
	private static final String strRnlDlsRuleConfig="RnlDlsRuleConfig";
    
	
	// vaccination  starts
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
    public ActionForward vaccinConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.debug("SpecialBenefitsRuleAction - inside vaccinConfiguration method....");
            setLinks(request);
            DynaActionForm frmDynVaccinRulesConfig= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            frmDynVaccinRulesConfig.initialize(mapping);
            frmDynVaccinRulesConfig.set("benefitID",request.getParameter("benefitID"));
            frmDynVaccinRulesConfig.set("invsType",request.getParameter("invsType"));
            DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
    		
            frmDynVaccinRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
            InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
            if("VCIN".equals(frmDynVaccinRulesConfig.getString("invsType"))){
            	frmDynVaccinRulesConfig.set("actMasterCode","90476");
            	investigationRuleVO.setActMasterCode("90476"); 
     	   }
            investigationRuleVO.setInvsType(request.getParameter("invsType"));
            investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
     	   request.getSession().setAttribute("VaccinationsRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
            
            return mapping.findForward(strVaccinRuleConfig);
        }//end of catch(TTKException expTTK)
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * This method is used to add vaccin details based on professional ID
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward addVaccinDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the addVaccinDtls() method of SpecisalBenefitsRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynVaccinRulesConfig= (DynaActionForm)form;
            		
            		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynVaccinRulesConfig,this,mapping,request);
            	   
            	    
            	   if("VCIN".equals(frmDynVaccinRulesConfig.getString("invsType"))){
            		   investigationRuleVO.setActMasterCode("90476"); 
            		   frmDynVaccinRulesConfig.set("actMasterCode", "90476");
            	   }
            	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
            	  
            	  session.setAttribute("VaccinationsRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
            	  
            	  frmDynVaccinRulesConfig.initialize(mapping);
            	  
            	  frmDynVaccinRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
            	  frmDynVaccinRulesConfig.set("invsType",investigationRuleVO.getInvsType());
            	//  frmDynVaccinRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
            	  frmDynVaccinRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
            	  
            	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
            	  else request.setAttribute("displayMsg","Updated Successfully!");
            	
            		return mapping.findForward(strVaccinRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward openConfigWindow(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the openConfigWindow method of SpecialBenefitsRuleAction");
           		setLinks(request);
           		 DynaActionForm frmDynVaccinRulesConfig= (DynaActionForm)form;  // frmDynVaccinRulesConfig
                    RuleManager ruleManagerObject=this.getRuleManagerObject();

           		 String strConfigValues=request.getParameter("configValues");
           		 if("Pro".equals(request.getParameter("windoType"))){
            			request.setAttribute("populateList", Cache.getCacheObject("providerType"));
            		}else if("Geo".equals(request.getParameter("windoType"))){
           			request.setAttribute("populateList", Cache.getCacheObject("GeoLocations"));
           		}else if("Con".equals(request.getParameter("windoType"))){
           			request.setAttribute("populateList", Cache.getDynamicCacheObject("GeoCountryList", request.getParameter("dynaParam")));
           		}
           		else if("Emr".equals(request.getParameter("windoType"))){
           			request.setAttribute("populateList", Cache.getDynamicCacheObject("EmirateList", "175"));
           		}else if("Enc".equals(request.getParameter("windoType"))){
        			request.setAttribute("populateList", ruleManagerObject.getEncounterTypesSPCB(request.getParameter("spType")));
        		}
           		else if("Alt".equals(request.getParameter("windoType"))){
        			request.setAttribute("populateList", getAltTmtTypes());
        		}
           		
                    if(strConfigValues!=null&&strConfigValues.length()>0){
                   	 request.setAttribute("configList", new ArrayList<>( Arrays.asList(strConfigValues.split("[|]"))));
                    }
           		
           		return mapping.findForward("ConfigOpenWindow");
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewVaccinStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewConsStaticConfDtls method of InvestigationRuleAction");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("VaccinationsRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editVaccinConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the editVaccinConf method of VaccinationRuleAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynVaccinRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("VaccinationsRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynVaccinRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynVaccinRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynVaccinRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  
           		frmDynVaccinRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynVaccinRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynVaccinRulesConfig", frmDynVaccinRulesConfig);
           		  
           	  }
           		return mapping.findForward(strVaccinRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteVaccinDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteVaccinDtls method of InvestigationRuleAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynVaccinRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("VaccinationsRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynVaccinRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynVaccinRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynVaccinRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("VaccinationsRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strVaccinRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
    // vaccination  end
   
   // Alternate Treatments start 
    
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
   public ActionForward altTmtConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside altTmtConfiguration method....");
           setLinks(request);
           DynaActionForm frmDynAltTmtRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynAltTmtRulesConfig.initialize(mapping);
           frmDynAltTmtRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynAltTmtRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynAltTmtRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("ATMT".equals(frmDynAltTmtRulesConfig.getString("invsType"))){
        	   frmDynAltTmtRulesConfig.set("actMasterCode","62");
           	investigationRuleVO.setActMasterCode("62"); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynAltTmtRulesConfig.getString("invsType"));
    	//   request.getSession().setAttribute("AltTmtRuleDetails", ruleManagerObject.getAltTmtDetails(investigationRuleVO));
    	   request.getSession().setAttribute("AltTmtRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           
           return mapping.findForward(strAltTmtRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward addAltTmtDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the addConsultDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynAltTmtRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynAltTmtRulesConfig,this,mapping,request);
           	    
         	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
        	   investigationRuleVO.setInvsType(frmDynAltTmtRulesConfig.getString("invsType"));
        	 
        	   String[] investArr=investigationRuleVO.getAltTmtTypes().split("[|]");
        	   for(String invest:investArr){
        		   investigationRuleVO.setActMasterCode(invest);
        		   investigationRuleVO.setInvestTypeName(invest);
        		   investigationRuleVO.setInvestTypeLabel(getInvestLabel(invest));
        		   
        		//   int i=  ruleManagerObject.addAltTmtDetails(investigationRuleVO);
        		   int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
               }
           	
           	 session.setAttribute("AltTmtRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  
           	frmDynAltTmtRulesConfig.initialize(mapping);
           	  
           	frmDynAltTmtRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynAltTmtRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynAltTmtRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynAltTmtRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strAltTmtRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editAltTmtConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the editAltTmtConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynAltTmtRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("AltTmtRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynAltTmtRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynAltTmtRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynAltTmtRulesConfig.getString("proPolRuleSeqID"));
           		frmDynAltTmtRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynAltTmtRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynAltTmtRulesConfig", frmDynAltTmtRulesConfig);
           		  
           	  }
           		return mapping.findForward(strAltTmtRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewAltTmtStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewAltTmtStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("AltTmtRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteAltTmtDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteAltTmtDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynAltTmtRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("AltTmtRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynAltTmtRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynAltTmtRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynAltTmtRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("AltTmtRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strAltTmtRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   
   
  // start dnrExpnsOgnTrnspltConfig
   
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
   public ActionForward dEOTConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside dEOTConfig method....");
           setLinks(request);
           DynaActionForm frmDynDEOTRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynDEOTRulesConfig.initialize(mapping);
           frmDynDEOTRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynDEOTRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynDEOTRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("DEOT".equals(frmDynDEOTRulesConfig.getString("invsType"))){
        	   frmDynDEOTRulesConfig.set("actMasterCode","Z00.5");
           	investigationRuleVO.setActMasterCode("Z00.5"); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynDEOTRulesConfig.getString("invsType"));
    	   request.getSession().setAttribute("DEOTRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); 
           
           return mapping.findForward(strDEOTRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
  
   public ActionForward addDEOTDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the addDEOTDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynDEOTRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynDEOTRulesConfig,this,mapping,request);
           	   
           	    
           	   if("DEOT".equals(frmDynDEOTRulesConfig.getString("invsType"))){
           		   investigationRuleVO.setActMasterCode("Z00.5"); 
           		frmDynDEOTRulesConfig.set("actMasterCode", "Z00.5");
           	   }
           	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
           	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
           	  
           	  session.setAttribute("DEOTRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); 
           	  
           	frmDynDEOTRulesConfig.initialize(mapping);
           	  
           	frmDynDEOTRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynDEOTRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynDEOTRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynDEOTRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strDEOTRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editDEOTConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the editDEOTConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynDEOTRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("DEOTRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynDEOTRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynDEOTRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynDEOTRulesConfig.getString("proPolRuleSeqID"));
           	 frmDynDEOTRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynDEOTRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynDEOTRulesConfig", frmDynDEOTRulesConfig);
           		  
           	  }
           		return mapping.findForward(strDEOTRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   

   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewDEOTStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewDEOTStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("DEOTRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   

   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteDEOTDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteDEOTDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynDEOTRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("DEOTRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynDEOTRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynDEOTRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynDEOTRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("DEOTRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strDEOTRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   
   

// end dnrExpnsOgnTrnspltConfig
   
   // start eyeCareConf
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
   public ActionForward eyeCareConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside eyeCareConfig method....");
           setLinks(request);
           DynaActionForm frmDynEyeCareRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynEyeCareRulesConfig.initialize(mapping);
           frmDynEyeCareRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynEyeCareRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynEyeCareRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("EYCR".equals(frmDynEyeCareRulesConfig.getString("invsType"))){
        	   frmDynEyeCareRulesConfig.set("actMasterCode","H00.011");
           	investigationRuleVO.setActMasterCode("H00.011"); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynEyeCareRulesConfig.getString("invsType"));
    	   request.getSession().setAttribute("EyeCareRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); // pending
           
           return mapping.findForward(strEyeCareRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward addEyeCareDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the addEyeCareDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynEyeCareRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynEyeCareRulesConfig,this,mapping,request);
           	   
           	    
           	   if("EYCR".equals(frmDynEyeCareRulesConfig.getString("invsType"))){
           		   investigationRuleVO.setActMasterCode("H00.011"); 
           		frmDynEyeCareRulesConfig.set("actMasterCode", "H00.011");
           	   }
           	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
           	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
           	  
           	  session.setAttribute("EyeCareRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  
           	frmDynEyeCareRulesConfig.initialize(mapping);
           	  
           	frmDynEyeCareRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynEyeCareRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynEyeCareRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynEyeCareRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strEyeCareRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editEyeCareConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the editEyeCareConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynEyeCareRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("EyeCareRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynEyeCareRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynEyeCareRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynEyeCareRulesConfig.getString("proPolRuleSeqID"));
           	 frmDynEyeCareRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynEyeCareRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynEyeCareRulesConfig", frmDynEyeCareRulesConfig);
           		  
           	  }
           		return mapping.findForward(strEyeCareRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewEyeCareStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewEyeCareStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("EyeCareRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteEyeCareDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteEyeCareDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynEyeCareRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("EyeCareRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynEyeCareRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynEyeCareRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynEyeCareRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("EyeCareRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strEyeCareRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   

  // end eye care
   
   // start gyncolgyConf
   
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
   public ActionForward gyncolgyConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside gyncolgyConfig method....");
           setLinks(request);
           DynaActionForm frmDynGyncolgyRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynGyncolgyRulesConfig.initialize(mapping);
           frmDynGyncolgyRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynGyncolgyRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynGyncolgyRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("GNGY".equals(frmDynGyncolgyRulesConfig.getString("invsType"))){
        	   frmDynGyncolgyRulesConfig.set("actMasterCode","N70.01");
           	investigationRuleVO.setActMasterCode("N70.01"); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynGyncolgyRulesConfig.getString("invsType"));
    	   request.getSession().setAttribute("GyncolgyRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); 
           
           return mapping.findForward(strGyncolgyRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward addGyncolgyDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the addGyncolgyDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynGyncolgyRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynGyncolgyRulesConfig,this,mapping,request);
           	   
           	    
           	   if("GNGY".equals(frmDynGyncolgyRulesConfig.getString("invsType"))){
           		   investigationRuleVO.setActMasterCode("N70.01"); 
           		frmDynGyncolgyRulesConfig.set("actMasterCode", "N70.01");
           	   }
           	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
           	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
           	  
           	  session.setAttribute("GyncolgyRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  
           	frmDynGyncolgyRulesConfig.initialize(mapping);
           	  
           	frmDynGyncolgyRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynGyncolgyRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynGyncolgyRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynGyncolgyRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strGyncolgyRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editGyncolgyConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the editGyncolgyConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynGyncolgyRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("GyncolgyRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynGyncolgyRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynGyncolgyRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynGyncolgyRulesConfig.getString("proPolRuleSeqID"));
           	 frmDynGyncolgyRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynGyncolgyRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynGyncolgyRulesConfig", frmDynGyncolgyRulesConfig);
           		  
           	  }
           		return mapping.findForward(strGyncolgyRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewGyncolgyStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.info("Inside the viewGyncolgyStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("GyncolgyRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteGyncolgyDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteGyncolgyDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynGyncolgyRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("GyncolgyRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynGyncolgyRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynGyncolgyRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynGyncolgyRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("GyncolgyRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strGyncolgyRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   

   
  // end gyncolgyConf
   
   // start minrSrgryConfig
   
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
   public ActionForward minrSrgryConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside minrSrgryConfig method....");
           setLinks(request);
           DynaActionForm frmDynMinrSrgryRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynMinrSrgryRulesConfig.initialize(mapping);
           frmDynMinrSrgryRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynMinrSrgryRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynMinrSrgryRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("MISG".equals(frmDynMinrSrgryRulesConfig.getString("invsType"))){
        	   frmDynMinrSrgryRulesConfig.set("actMasterCode","");
           	investigationRuleVO.setActMasterCode(""); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynMinrSrgryRulesConfig.getString("invsType"));
    	   request.getSession().setAttribute("MinrSrgryRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); 
           
           return mapping.findForward(strMinrSrgryRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward addMinrSrgryDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.info("Inside the addMinrSrgryDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynMinrSrgryRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynMinrSrgryRulesConfig,this,mapping,request);
           	   
           	    
           	   if("MISG".equals(frmDynMinrSrgryRulesConfig.getString("invsType"))){
           		   investigationRuleVO.setActMasterCode(""); 
           		frmDynMinrSrgryRulesConfig.set("actMasterCode", "");
           	   }
           	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
           	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
           	  
           	  session.setAttribute("MinrSrgryRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  
           	frmDynMinrSrgryRulesConfig.initialize(mapping);
           	  
           	frmDynMinrSrgryRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynMinrSrgryRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynMinrSrgryRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynMinrSrgryRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strMinrSrgryRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editMinrSrgryConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the editMinrSrgryConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynMinrSrgryRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("MinrSrgryRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynMinrSrgryRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynMinrSrgryRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynMinrSrgryRulesConfig.getString("proPolRuleSeqID"));
           	  	log.info("before setFormValues");
           	 frmDynMinrSrgryRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynMinrSrgryRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynMinrSrgryRulesConfig", frmDynMinrSrgryRulesConfig);
           		  
           	  }
           		return mapping.findForward(strMinrSrgryRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewMinrSrgryStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewMinrSrgryStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("MinrSrgryRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteMinrSrgryDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteMinrSrgryDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynMinrSrgryRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("MinrSrgryRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynMinrSrgryRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynMinrSrgryRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynMinrSrgryRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("MinrSrgryRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strMinrSrgryRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   

   // end minrSrgryConfig
   
   // start naslCrctionConfig
   
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
   public ActionForward naslCrctionConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside naslCrctionConfig method....");
           setLinks(request);
           DynaActionForm frmDynNaslCrctionRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynNaslCrctionRulesConfig.initialize(mapping);
           frmDynNaslCrctionRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynNaslCrctionRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynNaslCrctionRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("NASL".equals(frmDynNaslCrctionRulesConfig.getString("invsType"))){
        	   frmDynNaslCrctionRulesConfig.set("actMasterCode","30520");
           	investigationRuleVO.setActMasterCode("30520"); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynNaslCrctionRulesConfig.getString("invsType"));
    	   request.getSession().setAttribute("NaslCrctionRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); 
           
           return mapping.findForward(strNaslCrctionRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward addNaslCrctionDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.info("Inside the addNaslCrctionDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynNaslCrctionRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynNaslCrctionRulesConfig,this,mapping,request);
           	   
           	    
           	   if("NASL".equals(frmDynNaslCrctionRulesConfig.getString("invsType"))){
           		   investigationRuleVO.setActMasterCode("30520"); 
           		frmDynNaslCrctionRulesConfig.set("actMasterCode", "30520");
           	   }
           	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
           	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
           	  
           	  session.setAttribute("NaslCrctionRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  
           	frmDynNaslCrctionRulesConfig.initialize(mapping);
           	  
           	frmDynNaslCrctionRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynNaslCrctionRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynNaslCrctionRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynNaslCrctionRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strNaslCrctionRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editNaslCrctionConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.info("Inside the editNaslCrctionConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynNaslCrctionRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("NaslCrctionRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynNaslCrctionRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynNaslCrctionRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynNaslCrctionRulesConfig.getString("proPolRuleSeqID"));
           	 frmDynNaslCrctionRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynNaslCrctionRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynNaslCrctionRulesConfig", frmDynNaslCrctionRulesConfig);
           		  
           	  }
           		return mapping.findForward(strNaslCrctionRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewNaslCrctionStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewNaslCrctionStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("NaslCrctionRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteNaslCrctionDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteNaslCrctionDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynNaslCrctionRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("NaslCrctionRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynNaslCrctionRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynNaslCrctionRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynNaslCrctionRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("NaslCrctionRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strNaslCrctionRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   

// end naslCrctionConfig
   
   // start onclgyConfig
   
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
   public ActionForward onclgyConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside onclgyConfig method....");
           setLinks(request);
           DynaActionForm frmDynOnclgyRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynOnclgyRulesConfig.initialize(mapping);
           frmDynOnclgyRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynOnclgyRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynOnclgyRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("ONGY".equals(frmDynOnclgyRulesConfig.getString("invsType"))){
        	   frmDynOnclgyRulesConfig.set("actMasterCode","D70.1");
           	investigationRuleVO.setActMasterCode("D70.1"); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynOnclgyRulesConfig.getString("invsType"));
    	   request.getSession().setAttribute("OnclgyRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); 
           
           return mapping.findForward(strOnclgyRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward addOnclgyDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.info("Inside the addOnclgyDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynOnclgyRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynOnclgyRulesConfig,this,mapping,request);
           	   
           	    
           	   if("ONGY".equals(frmDynOnclgyRulesConfig.getString("invsType"))){
           		   investigationRuleVO.setActMasterCode("D70.1"); 
           		frmDynOnclgyRulesConfig.set("actMasterCode", "D70.1");
           	   }
           	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
           	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
           	  
           	  session.setAttribute("OnclgyRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  
           	frmDynOnclgyRulesConfig.initialize(mapping);
           	  
           	frmDynOnclgyRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynOnclgyRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynOnclgyRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynOnclgyRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strOnclgyRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editOnclgyConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.info("Inside the editOnclgyConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynOnclgyRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("OnclgyRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynOnclgyRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynOnclgyRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynOnclgyRulesConfig.getString("proPolRuleSeqID"));
           	 frmDynOnclgyRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynOnclgyRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynOnclgyRulesConfig", frmDynOnclgyRulesConfig);
           		  
           	  }
           		return mapping.findForward(strOnclgyRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewOnclgyStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewOnclgyStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("OnclgyRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteOnclgyDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteOnclgyDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynOnclgyRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("OnclgyRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynOnclgyRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynOnclgyRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynOnclgyRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("OnclgyRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strOnclgyRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   

// end onclgyConfig
   

   // start ognTrnspltRcptConf
   
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
   public ActionForward ognTrnspltRcptConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside ognTrnspltRcptConf method....");
           setLinks(request);
           DynaActionForm frmDynOgnTrnspltRcptRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynOgnTrnspltRcptRulesConfig.initialize(mapping);
           frmDynOgnTrnspltRcptRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynOgnTrnspltRcptRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynOgnTrnspltRcptRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("OTTR".equals(frmDynOgnTrnspltRcptRulesConfig.getString("invsType"))){
        	   frmDynOgnTrnspltRcptRulesConfig.set("actMasterCode","C80.2");
           	investigationRuleVO.setActMasterCode("C80.2"); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynOgnTrnspltRcptRulesConfig.getString("invsType"));
    	   request.getSession().setAttribute("OgnTrnspltRcptRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); 
           
           return mapping.findForward(strOgnTrnspltRcptRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward addOgnTrnspltRcptDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the addOgnTrnspltRcptDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynOgnTrnspltRcptRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynOgnTrnspltRcptRulesConfig,this,mapping,request);
           	   
           	    
           	   if("OTTR".equals(frmDynOgnTrnspltRcptRulesConfig.getString("invsType"))){
           		   investigationRuleVO.setActMasterCode("C80.2"); 
           		frmDynOgnTrnspltRcptRulesConfig.set("actMasterCode", "C80.2");
           	   }
           	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
           	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
           	  
           	  session.setAttribute("OgnTrnspltRcptRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  
           	frmDynOgnTrnspltRcptRulesConfig.initialize(mapping);
           	  
           	frmDynOgnTrnspltRcptRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynOgnTrnspltRcptRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynOgnTrnspltRcptRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynOgnTrnspltRcptRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strOgnTrnspltRcptRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editOgnTrnspltRcptConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the editOgnTrnspltRcptConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynOgnTrnspltRcptRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("OgnTrnspltRcptRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynOgnTrnspltRcptRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynOgnTrnspltRcptRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynOgnTrnspltRcptRulesConfig.getString("proPolRuleSeqID"));
           	 frmDynOgnTrnspltRcptRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynOgnTrnspltRcptRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynOgnTrnspltRcptRulesConfig", frmDynOgnTrnspltRcptRulesConfig);
           		  
           	  }
           		return mapping.findForward(strOgnTrnspltRcptRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewOgnTrnspltRcptStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewOgnTrnspltRcptStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("OgnTrnspltRcptRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteOgnTrnspltRcptDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteOgnTrnspltRcptDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynOgnTrnspltRcptRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("OgnTrnspltRcptRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynOgnTrnspltRcptRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynOgnTrnspltRcptRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynOgnTrnspltRcptRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("OgnTrnspltRcptRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strOgnTrnspltRcptRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   

// end ognTrnspltRcptConf
   
   // start psychiatricConfig
   
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
   public ActionForward psychiatricConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside PsychiatricConf method....");
           setLinks(request);
           DynaActionForm frmDynPsychiatricRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynPsychiatricRulesConfig.initialize(mapping);
           frmDynPsychiatricRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynPsychiatricRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynPsychiatricRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("PSYC".equals(frmDynPsychiatricRulesConfig.getString("invsType"))){
        	   frmDynPsychiatricRulesConfig.set("actMasterCode","F09");
           	investigationRuleVO.setActMasterCode("F09"); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynPsychiatricRulesConfig.getString("invsType"));
    	   request.getSession().setAttribute("PsychiatricRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); 
           
           return mapping.findForward(strPsychiatricRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward addPsychiatricDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the addPsychiatricDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynPsychiatricRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynPsychiatricRulesConfig,this,mapping,request);
           	   
           	    
           	   if("PSYC".equals(frmDynPsychiatricRulesConfig.getString("invsType"))){
           		   investigationRuleVO.setActMasterCode("F09"); 
           		frmDynPsychiatricRulesConfig.set("actMasterCode", "F09");
           	   }
           	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
           	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
           	  
           	  session.setAttribute("PsychiatricRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  
           	frmDynPsychiatricRulesConfig.initialize(mapping);
           	  
           	frmDynPsychiatricRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynPsychiatricRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynPsychiatricRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynPsychiatricRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strPsychiatricRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editPsychiatricConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the editPsychiatricConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynPsychiatricRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("PsychiatricRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynPsychiatricRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynPsychiatricRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynPsychiatricRulesConfig.getString("proPolRuleSeqID"));
           	 frmDynPsychiatricRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynPsychiatricRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynPsychiatricRulesConfig", frmDynPsychiatricRulesConfig);
           		  
           	  }
           		return mapping.findForward(strPsychiatricRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewPsychiatricStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewPsychiatricStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("PsychiatricRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deletePsychiatricDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deletePsychiatricDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynPsychiatricRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("PsychiatricRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynPsychiatricRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynPsychiatricRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynPsychiatricRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("PsychiatricRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strPsychiatricRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   

// end psychiatricConfig
  
   
   
   // start rnlDlsConfig
   
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
   public ActionForward rnlDlsConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try{
           log.debug(" inside rnlDlsConfig method....");
           setLinks(request);
           DynaActionForm frmDynRnlDlsRulesConfig= (DynaActionForm)form;
           RuleManager ruleManagerObject=this.getRuleManagerObject();
           frmDynRnlDlsRulesConfig.initialize(mapping);
           frmDynRnlDlsRulesConfig.set("benefitID",request.getParameter("benefitID"));
           frmDynRnlDlsRulesConfig.set("invsType",request.getParameter("invsType"));
           DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
   		
           frmDynRnlDlsRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
           InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
           if("RNDL".equals(frmDynRnlDlsRulesConfig.getString("invsType"))){
        	   frmDynRnlDlsRulesConfig.set("actMasterCode","I12.0");
           	investigationRuleVO.setActMasterCode("I12.0"); 
    	   }
           investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
           investigationRuleVO.setInvsType(frmDynRnlDlsRulesConfig.getString("invsType"));
    	   request.getSession().setAttribute("RnlDlsRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO)); 
           
           return mapping.findForward(strRnlDlsRuleConfig);
       }//end of catch(TTKException expTTK)
       catch(TTKException expTTK)
       {
           return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processExceptions(request, mapping, new TTKException(exp, "product"));
       }//end of catch(Exception exp)
   }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward addRnlDlsDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the addRnlDlsDtls method of SBAction");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynRnlDlsRulesConfig= (DynaActionForm)form;
           		
           		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
           	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynRnlDlsRulesConfig,this,mapping,request);
           	   
           	    
           	   if("RNDL".equals(frmDynRnlDlsRulesConfig.getString("invsType"))){
           		   investigationRuleVO.setActMasterCode("I12.0"); 
           		frmDynRnlDlsRulesConfig.set("actMasterCode", "I12.0");
           	   }
           	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
           	  int i=  ruleManagerObject.addSpecialBenefitsDetails(investigationRuleVO);
           	  
           	  session.setAttribute("RnlDlsRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  
           	frmDynRnlDlsRulesConfig.initialize(mapping);
           	  
           	frmDynRnlDlsRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
           	frmDynRnlDlsRulesConfig.set("invsType",investigationRuleVO.getInvsType());
           	frmDynRnlDlsRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
           	frmDynRnlDlsRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
           	  
           	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
           	  else request.setAttribute("displayMsg","Updated Successfully!");
           	
           		return mapping.findForward(strRnlDlsRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward editRnlDlsConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the editRnlDlsConf method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynRnlDlsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("RnlDlsRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
           		  
           		  investigationRuleVO.setBenefitID(frmDynRnlDlsRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynRnlDlsRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynRnlDlsRulesConfig.getString("proPolRuleSeqID"));
           	    frmDynRnlDlsRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynRnlDlsRulesConfig", investigationRuleVO, this, mapping,request);
           		  session.setAttribute("frmDynRnlDlsRulesConfig", frmDynRnlDlsRulesConfig);
           		  
           	  }
           		return mapping.findForward(strRnlDlsRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
   
   
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward viewRnlDlsStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the viewRnlDlsStaticConfDtls method of SB");
           		setLinks(request);
           		// RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("RnlDlsRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		 
           		  request.setAttribute("staticConfRuleVO", investigationRuleVO);
           	  }
           		return mapping.findForward(strStaticRuleConfigDetails) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   /**
    * This method is used to getLicenceNumbers based on professional ID
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward deleteRnlDlsDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
       try
       {
       	log.debug("Inside the deleteRnlDlsDtls method of SB");
           		setLinks(request);
           		 RuleManager ruleManagerObject=this.getRuleManagerObject();
           		 HttpSession session=request.getSession();
           		DynaActionForm frmDynRnlDlsRulesConfig= (DynaActionForm)form;
           	  
           	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("RnlDlsRuleDetails");
           	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
           		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
           		  int i=  ruleManagerObject.deleteSBdtls(investigationRuleVO);
           		
           		  investigationRuleVO.setBenefitID(frmDynRnlDlsRulesConfig.getString("benefitID"));
           		  investigationRuleVO.setActMasterCode(frmDynRnlDlsRulesConfig.getString("actMasterCode"));
           		  investigationRuleVO.setProPolRuleSeqID(frmDynRnlDlsRulesConfig.getString("proPolRuleSeqID"));
           		  
           		  request.setAttribute("displayMsg","Deleted Successfully!");
           		  session.setAttribute("RnlDlsRuleDetails", ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO));
           	  }
           		return mapping.findForward(strRnlDlsRuleConfig) ;
           	}//end of try
           	catch(TTKException expTTK)
       		{
       			return this.processExceptions(request, mapping, expTTK);
       		}//end of catch(TTKException expTTK)
       		catch(Exception exp)
       		{
       			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
       		}//end of catch(Exception exp)
           }//end of doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
   

// end rnlDlsConfig
   
	
	public ActionForward doChangeClaimType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			
			DynaActionForm frmDynAltTmtRulesConfig = (DynaActionForm)form;	
			String claimType=frmDynAltTmtRulesConfig.getString("claimType");
			
			if("CNH".equals(claimType))frmDynAltTmtRulesConfig.set("networkYN", "Y");
			else frmDynAltTmtRulesConfig.set("networkYN", "");
			
			 
			request.getSession().setAttribute("frmDynAltTmtRulesConfig",frmDynAltTmtRulesConfig);
			
			return mapping.findForward(strAltTmtRuleConfig);
			
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
		}//end of catch(Exception exp)
	}
	
	   
	
		public ActionForward doChangeClaimTypeDEOT(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynDEOTRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynDEOTRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynDEOTRulesConfig.set("networkYN", "Y");
				else frmDynDEOTRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynDEOTRulesConfig",frmDynDEOTRulesConfig);
				
				return mapping.findForward(strDEOTRuleConfig);
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp)
		}
		
		public ActionForward doChangeClaimTypeEyeCare(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynEyeCareRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynEyeCareRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynEyeCareRulesConfig.set("networkYN", "Y");
				else frmDynEyeCareRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynEyeCareRulesConfig",frmDynEyeCareRulesConfig);
				
				return mapping.findForward(strEyeCareRuleConfig);
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp)
		}
		
		public ActionForward doChangeClaimTypeGyncolgy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynGyncolgyRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynGyncolgyRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynGyncolgyRulesConfig.set("networkYN", "Y");
				else frmDynGyncolgyRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynGyncolgyRulesConfig",frmDynGyncolgyRulesConfig);
				
				return mapping.findForward(strGyncolgyRuleConfig);
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp) 
		}
		
	   // MinrSrgry
		
		public ActionForward doChangeClaimTypeMinrSrgry(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynMinrSrgryRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynMinrSrgryRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynMinrSrgryRulesConfig.set("networkYN", "Y");
				else frmDynMinrSrgryRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynMinrSrgryRulesConfig",frmDynMinrSrgryRulesConfig);
				
				return mapping.findForward(strMinrSrgryRuleConfig);
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp) 
		}
	
   // NaslCrction
		
		public ActionForward doChangeClaimTypeNaslCrction(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynNaslCrctionRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynNaslCrctionRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynNaslCrctionRulesConfig.set("networkYN", "Y");
				else frmDynNaslCrctionRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynNaslCrctionRulesConfig",frmDynNaslCrctionRulesConfig);
				
				return mapping.findForward(strNaslCrctionRuleConfig);
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp) 
		}
		
		public ActionForward doChangeClaimTypeOnclgy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynOnclgyRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynOnclgyRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynOnclgyRulesConfig.set("networkYN", "Y");
				else frmDynOnclgyRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynOnclgyRulesConfig",frmDynOnclgyRulesConfig);
				
				return mapping.findForward(strOnclgyRuleConfig);
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp) 
		}
	
		// OgnTrnspltRcpt
	  
		public ActionForward doChangeClaimTypeOgnTrnspltRcpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynOgnTrnspltRcptRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynOgnTrnspltRcptRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynOgnTrnspltRcptRulesConfig.set("networkYN", "Y");
				else frmDynOgnTrnspltRcptRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynOgnTrnspltRcptRulesConfig",frmDynOgnTrnspltRcptRulesConfig);
				
				return mapping.findForward(strOgnTrnspltRcptRuleConfig);
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp) 
		}
	
	 // Psychiatric  
		
		public ActionForward doChangeClaimTypePsychiatric(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynPsychiatricRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynPsychiatricRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynPsychiatricRulesConfig.set("networkYN", "Y");
				else frmDynPsychiatricRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynPsychiatricRulesConfig",frmDynPsychiatricRulesConfig);
				
				return mapping.findForward(strPsychiatricRuleConfig);
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp) 
		}
	
		// RnlDls
		
		public ActionForward doChangeClaimTypeRnlDls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynRnlDlsRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynRnlDlsRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynRnlDlsRulesConfig.set("networkYN", "Y");
				else frmDynRnlDlsRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynRnlDlsRulesConfig",frmDynRnlDlsRulesConfig);
				
				return mapping.findForward(strRnlDlsRuleConfig);
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp) 
		}
		
		public ActionForward doChangeClaimTypeVaccin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				setLinks(request);
				
				DynaActionForm frmDynVaccinRulesConfig = (DynaActionForm)form;	
				String claimType=frmDynVaccinRulesConfig.getString("claimType");
				
				if("CNH".equals(claimType))frmDynVaccinRulesConfig.set("networkYN", "Y");
				else frmDynVaccinRulesConfig.set("networkYN", "");
				
				 
				request.getSession().setAttribute("frmDynVaccinRulesConfig",frmDynVaccinRulesConfig);
				
				return mapping.findForward(strVaccinRuleConfig); 
				
			}
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"Configuration"));
			}//end of catch(Exception exp) 
		}
		 
	    private ArrayList getAltTmtTypes(){
	    	ArrayList alDaynValues=new ArrayList();
	    	
	    	CacheObject cache1=new CacheObject();         			
	    	cache1.setCacheId("SAY");
	    	cache1.setCacheDesc("Ayurvedic");
			alDaynValues.add(cache1);
			
			CacheObject cache2=new CacheObject();         			
			cache2.setCacheId("SAH");
			cache2.setCacheDesc("Homeopathy");
			alDaynValues.add(cache2);
			
			CacheObject cache3=new CacheObject();         			
			cache3.setCacheId("SAN");
			cache3.setCacheDesc("Naturopathy");
			alDaynValues.add(cache3);
			
			CacheObject cache4=new CacheObject();         			
			cache4.setCacheId("ACU");
			cache4.setCacheDesc("Acupuncture");
			alDaynValues.add(cache4);
			
			CacheObject cache5=new CacheObject();         			
			cache5.setCacheId("OSP");
			cache5.setCacheDesc("Osteopathy");
			alDaynValues.add(cache5);
			
			CacheObject cache6=new CacheObject();         			
			cache6.setCacheId("CHM");
			cache6.setCacheDesc("Chinese herbal medicine");
			alDaynValues.add(cache6);
			
			CacheObject cache7=new CacheObject();         			
			cache7.setCacheId("ATR");
			cache7.setCacheDesc("Alternate Treatments");
			alDaynValues.add(cache7);
			
			CacheObject cache8=new CacheObject();         			
			cache8.setCacheId("CHP");
			cache8.setCacheDesc("Chiropractic");
			alDaynValues.add(cache8);
			
			return alDaynValues;
	    }
	    
	    private String getInvestLabel(String investCode)
	    {
	    	String investLabel="";
			if("CHP".equals(investCode)) investLabel="Chiropractic";
			else if("OSP".equals(investCode)) investLabel="Osteopathy";
			else if("SAH".equals(investCode)) investLabel="Homoeopathy";
			else if("ACU".equals(investCode)) investLabel="Acupuncture";
			else if("CHM".equals(investCode)) investLabel="Chinese herbal medicine";
			else if("SAY".equals(investCode)) investLabel="Ayurvedic";
			else if("ATR".equals(investCode)) investLabel="Alternate Treatments";
			else if("SAN".equals(investCode)) investLabel="Naturopathy";
			
			return investLabel;
			
	    }
	    
   
    /**
     * Returns the RuleManager session object for invoking methods on it.
     * @return RuleManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private RuleManager getRuleManagerObject() throws TTKException
    {
        RuleManager ruleManager = null;
        try
        {
            if(ruleManager == null)
            {
                InitialContext ctx = new InitialContext();
                ruleManager = (RuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/RuleManagerBean!com.ttk.business.administration.RuleManager");
            }//end if(ruleManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "ruledata");
        }//end of catch
        return ruleManager;
    }//end of getRuleManagerObject()
    
}//end of InvestigationRuleAction.java

  


