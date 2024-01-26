/**
 * @ (#) DynInvestConsultRuleAction.java Jul 8, 2018
 * Project      : TTK HealthCare Services
 * File         : DynInvestConsultRuleAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 8, 2006
 *
 * @author       :  Arun K N
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
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.administration.RuleManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.administration.InvestigationRuleVO;
import com.ttk.dto.common.CacheObject;

import formdef.plugin.util.FormUtils;
public class DynInvestConsultRuleAction extends TTKAction {
	
    private static Logger log = Logger.getLogger(DynInvestConsultRuleAction.class); // Getting Logger for this Class file

    //declaration of forward paths
    private static final String strConsultationRuleConfig="ConsultationRuleConfig";
    private static final String strStaticRuleConfigDetails="StaticRuleConfigDetails";
    private static final String strInvestigationRuleConfig="InvestRuleConfig";
    private static final String strPhysioRuleConfig="PhysioRuleConfig";
    private static final String strPharmaRuleConfig="PharmaRuleConfig";
    private static final String strCompanRuleConfig="CompanRuleConfig";
    private static final String strAmbulanceRuleConfig="AmbulanceRuleConfig";
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
    public ActionForward consultConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.debug("InvestigationRuleAction - inside consultConfiguration method....");
            setLinks(request);
            DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            frmDynConsRulesConfig.initialize(mapping);
            frmDynConsRulesConfig.set("benefitID",request.getParameter("benefitID"));
            frmDynConsRulesConfig.set("invsType",request.getParameter("invsType"));
            DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
    		
            frmDynConsRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
            InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
            if("CONS".equals(frmDynConsRulesConfig.getString("invsType"))&&"OPTS".equals(request.getParameter("benefitID"))){
            	frmDynConsRulesConfig.set("actMasterCode","9");
            	investigationRuleVO.setActMasterCode("9"); 
     	   }else if("CONS".equals(frmDynConsRulesConfig.getString("invsType"))&&"IPT".equals(request.getParameter("benefitID"))){
           	frmDynConsRulesConfig.set("actMasterCode","63");
           	investigationRuleVO.setActMasterCode("63"); 
    	   }
            investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
            investigationRuleVO.setInvsType(frmDynConsRulesConfig.getString("invsType"));
     	    request.getSession().setAttribute("ConsulationRuleDetails", ruleManagerObject.getConsdDetails(investigationRuleVO));
            
            return mapping.findForward(strConsultationRuleConfig);
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
    public ActionForward openConfigWindow(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the openConfigWindow method of InvestigationRuleAction");
            		setLinks(request);
            		 DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
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
            			request.setAttribute("populateList", ruleManagerObject.getEncounterTypes(frmDynConsRulesConfig.getString("benefitID")));
            		}
            		else if("Hos".equals(request.getParameter("windoType"))){
            			
            			ArrayList alDaynValues=new ArrayList<>();
            			
            			CacheObject cache1=new CacheObject();         			
            			cache1.setCacheId("SGO");
            			cache1.setCacheDesc("Government");
            			
            			CacheObject cache2=new CacheObject();         			
            			cache2.setCacheId("SPR");
            			cache2.setCacheDesc("Private");
            			alDaynValues.add(cache2);
            			
            			request.setAttribute("populateList", alDaynValues);
            		} else if("Inv".equals(request.getParameter("windoType"))){
            			ArrayList alDaynValues=getInvestTypes();            			
            			
            			request.setAttribute("populateList", alDaynValues);
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
    public ActionForward addConsultDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the addConsultDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            		
            		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynConsRulesConfig,this,mapping,request);
            	   
            	    
            	   if("CONS".equals(frmDynConsRulesConfig.getString("invsType"))&&"OPTS".equals(investigationRuleVO.getBenefitID())){
            		   investigationRuleVO.setActMasterCode("9"); 
            		   frmDynConsRulesConfig.set("actMasterCode", "9");
            	   }else if("CONS".equals(frmDynConsRulesConfig.getString("invsType"))&&("IPT".equals(investigationRuleVO.getBenefitID())||"DAYC".equals(investigationRuleVO.getBenefitID()))){
            		   frmDynConsRulesConfig.set("actMasterCode","63");
                       	investigationRuleVO.setActMasterCode("63"); 
                	   }
            	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            	  int i=  ruleManagerObject.addConsdDetails(investigationRuleVO);
            	  
            	  session.setAttribute("ConsulationRuleDetails", ruleManagerObject.getConsdDetails(investigationRuleVO));
            	  
            	  frmDynConsRulesConfig.initialize(mapping);
            	  
            	  frmDynConsRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
            	  frmDynConsRulesConfig.set("invsType",investigationRuleVO.getInvsType());
            	  frmDynConsRulesConfig.set("actMasterCode",investigationRuleVO.getActMasterCode());
            	  frmDynConsRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
            	  
            	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
            	  else request.setAttribute("displayMsg","Updated Successfully!");
            	
            		return mapping.findForward(strConsultationRuleConfig) ;
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
    public ActionForward deleteConsDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the addCunsDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("ConsulationRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		  int i=  ruleManagerObject.deleteConsdtls(investigationRuleVO);
            		
            		  investigationRuleVO.setBenefitID(frmDynConsRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setActMasterCode(frmDynConsRulesConfig.getString("actMasterCode"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynConsRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  request.setAttribute("displayMsg","Deleted Successfully!");
            		  session.setAttribute("ConsulationRuleDetails", ruleManagerObject.getConsdDetails(investigationRuleVO));
            	  }
            		return mapping.findForward(strConsultationRuleConfig) ;
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
    public ActionForward editConsultConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the editConsultConf method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("ConsulationRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		 
            		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
            		  
            		  investigationRuleVO.setBenefitID(frmDynConsRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setActMasterCode(frmDynConsRulesConfig.getString("actMasterCode"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynConsRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  frmDynConsRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynConsRulesConfig", investigationRuleVO, this, mapping,request);
            		  session.setAttribute("frmDynConsRulesConfig", frmDynConsRulesConfig);
            		  
            	  }
            		return mapping.findForward(strConsultationRuleConfig) ;
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
    public ActionForward viewConsStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the viewConsStaticConfDtls method of InvestigationRuleAction");
            		setLinks(request);
            		// RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("ConsulationRuleDetails");
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
    public ActionForward investConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.debug("InvestigationRuleAction - inside investConfiguration method....");
            setLinks(request);
            DynaActionForm frmDynInvestRulesConfig= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            frmDynInvestRulesConfig.initialize(mapping);
            frmDynInvestRulesConfig.set("benefitID",request.getParameter("benefitID"));
            frmDynInvestRulesConfig.set("invsType",request.getParameter("invsType"));
            DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
    		
            frmDynInvestRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
            InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
            investigationRuleVO.setInvsType(request.getParameter("invsType"));
            investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
     	  
     	   request.getSession().setAttribute("InvestgationRuleDetails", ruleManagerObject.getInvestdDetails(investigationRuleVO));
            
            return mapping.findForward(strInvestigationRuleConfig);
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
    public ActionForward addInvestDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the addInvestDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            		
            		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynConsRulesConfig,this,mapping,request);
            	   
            	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            	   investigationRuleVO.setInvsType(frmDynConsRulesConfig.getString("invsType"));
            	 
            	   String[] investArr=investigationRuleVO.getInvestTypeName().split("[|]");
            	   for(String invest:investArr){
            		   investigationRuleVO.setActMasterCode(invest);
            		   investigationRuleVO.setInvestTypeName(invest);
            		   investigationRuleVO.setInvestTypeLabel(getInvestLabel(invest));
            		   
            		   int i=  ruleManagerObject.addInvestDetails(investigationRuleVO);
                   }
            	  session.setAttribute("InvestgationRuleDetails", ruleManagerObject.getInvestdDetails(investigationRuleVO));
            	  
            	  frmDynConsRulesConfig.initialize(mapping);
            	  
            	  frmDynConsRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
            	  frmDynConsRulesConfig.set("invsType",investigationRuleVO.getInvsType());            	  
            	  frmDynConsRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
            	  
            	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
            	  else request.setAttribute("displayMsg","Updated Successfully!");
            	
            		return mapping.findForward(strInvestigationRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of addInvestDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward editInvestConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the editInvestConf method of InvestigationRuleAction");
            		setLinks(request);
            		 //RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynInvestRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("InvestgationRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		 
            		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
            		  
            		  investigationRuleVO.setBenefitID(frmDynInvestRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynInvestRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  frmDynInvestRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynInvestRulesConfig", investigationRuleVO, this, mapping,request);
            		  session.setAttribute("frmDynInvestRulesConfig", frmDynInvestRulesConfig);
            		  
            	  }
            		return mapping.findForward(strInvestigationRuleConfig) ;
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
    public ActionForward deleteInvestDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the deleteInvestDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("InvestgationRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		  int i=  ruleManagerObject.deleteConsdtls(investigationRuleVO);
            		
            		  investigationRuleVO.setBenefitID(frmDynConsRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynConsRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  request.setAttribute("displayMsg","Deleted Successfully!");
            		  session.setAttribute("InvestgationRuleDetails", ruleManagerObject.getInvestdDetails(investigationRuleVO));
            	  }
            		return mapping.findForward(strInvestigationRuleConfig) ;
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
    public ActionForward viewInvestStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the viewInvestStaticConfDtls method of InvestigationRuleAction");
            		setLinks(request);
            		// RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("InvestgationRuleDetails");
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
    public ActionForward physioConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.debug("InvestigationRuleAction - inside physioConfiguration method....");
            setLinks(request);
            DynaActionForm frmDynPhysioRulesConfig= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            frmDynPhysioRulesConfig.initialize(mapping);
            frmDynPhysioRulesConfig.set("benefitID",request.getParameter("benefitID"));
            frmDynPhysioRulesConfig.set("invsType",request.getParameter("invsType"));
            DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
    		
            frmDynPhysioRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
            InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
            investigationRuleVO.setInvsType(request.getParameter("invsType"));
            investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
     	  
     	   request.getSession().setAttribute("PhysiotherapyRuleDetails", ruleManagerObject.getPhysioDetails(investigationRuleVO));
            
            return mapping.findForward(strPhysioRuleConfig);
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
    public ActionForward addPhysioDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the addPhysioDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		 DynaActionForm frmDynPhysioRulesConfig= (DynaActionForm)form;
            		
            		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynPhysioRulesConfig,this,mapping,request);
            	    
            	   if("PHYS".equals(frmDynPhysioRulesConfig.getString("invsType"))){
            		   investigationRuleVO.setActMasterCode("97001"); 
            		   
            	   }
            	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            	   investigationRuleVO.setInvsType(frmDynPhysioRulesConfig.getString("invsType"));
            	 
            	   int i=  ruleManagerObject.addPhysioDetails(investigationRuleVO);
            	  
            	  session.setAttribute("PhysiotherapyRuleDetails", ruleManagerObject.getPhysioDetails(investigationRuleVO));
            	  
            	  frmDynPhysioRulesConfig.initialize(mapping);
            	  
            	  frmDynPhysioRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
            	  frmDynPhysioRulesConfig.set("invsType",investigationRuleVO.getInvsType());            	  
            	  frmDynPhysioRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
            	  
            	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
            	  else request.setAttribute("displayMsg","Updated Successfully!");
            	
            		return mapping.findForward(strPhysioRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of addInvestDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    

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
    public ActionForward editPhysioConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the editPhysioConf method of InvestigationRuleAction");
            		setLinks(request);
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynPhysioRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("PhysiotherapyRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		 
            		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
            		  
            		  investigationRuleVO.setBenefitID(frmDynPhysioRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynPhysioRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  frmDynPhysioRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynPhysioRulesConfig", investigationRuleVO, this, mapping,request);
            		  session.setAttribute("frmDynPhysioRulesConfig", frmDynPhysioRulesConfig);
            		  
            	  }
            		return mapping.findForward(strPhysioRuleConfig) ;
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
    public ActionForward viewPhysiotStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the viewPhysioStaticConfDtls method of InvestigationRuleAction");
            		setLinks(request);
            		// RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("PhysiotherapyRuleDetails");
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
    public ActionForward deletePhysioDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the deletePhysioDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("PhysiotherapyRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		  int i=  ruleManagerObject.deleteConsdtls(investigationRuleVO);
            		
            		  investigationRuleVO.setBenefitID(frmDynConsRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynConsRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  request.setAttribute("displayMsg","Deleted Successfully!");
            		  session.setAttribute("PhysiotherapyRuleDetails", ruleManagerObject.getPhysioDetails(investigationRuleVO));
            	  }
            		return mapping.findForward(strPhysioRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of deletePhysioDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    public ActionForward pharmaConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.debug("InvestigationRuleAction - inside pharmaConfiguration method....");
            setLinks(request);
            DynaActionForm frmDynPharmaRulesConfig= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            frmDynPharmaRulesConfig.initialize(mapping);
            frmDynPharmaRulesConfig.set("benefitID",request.getParameter("benefitID"));
            frmDynPharmaRulesConfig.set("invsType",request.getParameter("invsType"));
            DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
    		
            frmDynPharmaRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
            InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
            investigationRuleVO.setInvsType(request.getParameter("invsType"));
            investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
     	   request.getSession().setAttribute("PharmaceuticalRuleDetails", ruleManagerObject.getPharmaDetails(investigationRuleVO));
            
            return mapping.findForward(strPharmaRuleConfig);
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
    public ActionForward addPharmaDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the addPharmaDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		 DynaActionForm frmDynPharmaRulesConfig= (DynaActionForm)form;
            		
            		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynPharmaRulesConfig,this,mapping,request);
            	    
            	   if("PHRM".equals(frmDynPharmaRulesConfig.getString("invsType"))){
            		   investigationRuleVO.setActMasterCode("0000-000000-0000"); 
            		   
            	   }
            	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            	   investigationRuleVO.setInvsType(frmDynPharmaRulesConfig.getString("invsType"));
            	 
            	   int i=  ruleManagerObject.addPharmaDetails(investigationRuleVO);
            	  
            	  session.setAttribute("PharmaceuticalRuleDetails", ruleManagerObject.getPharmaDetails(investigationRuleVO));
            	  
            	  frmDynPharmaRulesConfig.initialize(mapping);
            	  
            	  frmDynPharmaRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
            	  frmDynPharmaRulesConfig.set("invsType",investigationRuleVO.getInvsType());            	  
            	  frmDynPharmaRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
            	  
            	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
            	  else request.setAttribute("displayMsg","Updated Successfully!");
            	
            		return mapping.findForward(strPharmaRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of addInvestDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    

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
    public ActionForward editPharmaConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the editPharmaConf method of InvestigationRuleAction");
            		setLinks(request);
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynPharmaRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("PharmaceuticalRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		 
            		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
            		  
            		  investigationRuleVO.setBenefitID(frmDynPharmaRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynPharmaRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  frmDynPharmaRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynPharmaRulesConfig", investigationRuleVO, this, mapping,request);
            		  session.setAttribute("frmDynPharmaRulesConfig", frmDynPharmaRulesConfig);
            		  
            	  }
            		return mapping.findForward(strPharmaRuleConfig) ;
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
    public ActionForward viewPharmaStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the viewPharmaStaticConfDtls method of InvestigationRuleAction");
            		setLinks(request);
            		// RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("PharmaceuticalRuleDetails");
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
            }//end of viewPharmatStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   

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
    public ActionForward deletePharmaDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the deletePharmaDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynPharmaRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("PharmaceuticalRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		  int i=  ruleManagerObject.deleteConsdtls(investigationRuleVO);
            		
            		  investigationRuleVO.setBenefitID(frmDynPharmaRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynPharmaRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  request.setAttribute("displayMsg","Deleted Successfully!");
            		  session.setAttribute("PharmaceuticalRuleDetails", ruleManagerObject.getPharmaDetails(investigationRuleVO));
            	  }
            		return mapping.findForward(strPharmaRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of deletePhysioDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    

    
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
    public ActionForward companConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.debug("InvestigationRuleAction - inside companConfiguration method....");
            setLinks(request);
            DynaActionForm frmDynCompanRulesConfig= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            frmDynCompanRulesConfig.initialize(mapping);
            frmDynCompanRulesConfig.set("benefitID",request.getParameter("benefitID"));
            frmDynCompanRulesConfig.set("invsType",request.getParameter("invsType"));
            DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
    		
            frmDynCompanRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
            InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
            investigationRuleVO.setInvsType(request.getParameter("invsType"));
            investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
     	  
     	    request.getSession().setAttribute("CompanionRuleDetails", ruleManagerObject.getCompanDetails(investigationRuleVO));
             
            return mapping.findForward(strCompanRuleConfig);
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
    public ActionForward addCompanDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the addCompanDtls** method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		 DynaActionForm frmDynCompanRulesConfig= (DynaActionForm)form;
            		
            		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynCompanRulesConfig,this,mapping,request);
            	    
            	   if("COMP".equals(frmDynCompanRulesConfig.getString("invsType"))){
            		   investigationRuleVO.setActMasterCode("26"); 
            		   
            	   }
            	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            	   investigationRuleVO.setInvsType(frmDynCompanRulesConfig.getString("invsType"));
            	 
            	   int i=  ruleManagerObject.addCompanDetails(investigationRuleVO);
            	  
            	  session.setAttribute("CompanionRuleDetails", ruleManagerObject.getCompanDetails(investigationRuleVO));
            	  frmDynCompanRulesConfig.initialize(mapping);
            	  
            	  frmDynCompanRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
            	  frmDynCompanRulesConfig.set("invsType",investigationRuleVO.getInvsType());            	  
            	  frmDynCompanRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
            	  
            	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
            	  else request.setAttribute("displayMsg","Updated Successfully!");
            	
            		return mapping.findForward(strCompanRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of addCompanDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward editCompanConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the editCompanConf method of InvestigationRuleAction");
            		setLinks(request);
            		// RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynCompanRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("CompanionRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		 
            		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
            		  
            		  investigationRuleVO.setBenefitID(frmDynCompanRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynCompanRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  frmDynCompanRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynCompanRulesConfig", investigationRuleVO, this, mapping,request);
            		  session.setAttribute("frmDynCompanRulesConfig", frmDynCompanRulesConfig);
            		  
            	  }
            		return mapping.findForward(strCompanRuleConfig) ;
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
    public ActionForward deleteCompanDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the deleteCompanDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynPharmaRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("CompanionRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		  int i=  ruleManagerObject.deleteConsdtls(investigationRuleVO);
            		
            		  investigationRuleVO.setBenefitID(frmDynPharmaRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynPharmaRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  request.setAttribute("displayMsg","Deleted Successfully!");
            		  session.setAttribute("CompanionRuleDetails", ruleManagerObject.getCompanDetails(investigationRuleVO));
            	  }
            		return mapping.findForward(strPharmaRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of deleteCompanDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    

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
    public ActionForward viewCompanStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the viewCompanStaticConfDtls method of InvestigationRuleAction");
            		setLinks(request);
            		// RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("CompanionRuleDetails");
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
            }//end of viewCompanStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    

    

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
    public ActionForward ambulanceConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.debug("InvestigationRuleAction - inside ambulanceConfiguration method....");
            setLinks(request);
            DynaActionForm frmDynAmbulanceRulesConfig= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            frmDynAmbulanceRulesConfig.initialize(mapping);
            frmDynAmbulanceRulesConfig.set("benefitID",request.getParameter("benefitID"));
            frmDynAmbulanceRulesConfig.set("invsType",request.getParameter("invsType"));
            DynaActionForm frmDefineRules= (DynaActionForm)request.getSession().getAttribute("frmDefineRules");
    		
            frmDynAmbulanceRulesConfig.set("proPolRuleSeqID",frmDefineRules.getString("prodPolicyRuleSeqID"));
            InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            investigationRuleVO.setBenefitID(request.getParameter("benefitID"));
            investigationRuleVO.setInvsType(request.getParameter("invsType"));
            investigationRuleVO.setProPolRuleSeqID(frmDefineRules.getString("prodPolicyRuleSeqID"));
     	  
     	    request.getSession().setAttribute("AmbulanceRuleDetails", ruleManagerObject.getAmbulanceDetails(investigationRuleVO));
             
            return mapping.findForward(strAmbulanceRuleConfig);
        }//end of catch(TTKException expTTK)
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of ambulanceConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    

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
    public ActionForward addAmbulanceDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.info("Inside the addAmbulanceDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		 DynaActionForm frmDynAmbulanceRulesConfig= (DynaActionForm)form;
            		
            		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
            	    investigationRuleVO= (InvestigationRuleVO)FormUtils.getFormValues(frmDynAmbulanceRulesConfig,this,mapping,request);
            	    
            	   if("AMBL".equals(frmDynAmbulanceRulesConfig.getString("invsType"))){
            		   investigationRuleVO.setActMasterCode("A0080"); 
            		   
            	   }
            	   investigationRuleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            	   investigationRuleVO.setInvsType(frmDynAmbulanceRulesConfig.getString("invsType"));
            	 
            	   int i=  ruleManagerObject.addAmbulanceDetails(investigationRuleVO);
            	  
            	  session.setAttribute("AmbulanceRuleDetails", ruleManagerObject.getAmbulanceDetails(investigationRuleVO));
            	  frmDynAmbulanceRulesConfig.initialize(mapping);
            	  
            	  frmDynAmbulanceRulesConfig.set("benefitID",investigationRuleVO.getBenefitID());
            	  frmDynAmbulanceRulesConfig.set("invsType",investigationRuleVO.getInvsType());            	  
            	  frmDynAmbulanceRulesConfig.set("proPolRuleSeqID",investigationRuleVO.getProPolRuleSeqID());
            	  
            	  if(investigationRuleVO.getInvestgationSeqID()==null||"".equals(investigationRuleVO.getInvestgationSeqID())) request.setAttribute("displayMsg","Added Successfully!");
            	  else request.setAttribute("displayMsg","Updated Successfully!");
            	
            		return mapping.findForward(strAmbulanceRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of addAmbulanceDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward editAmbulanceConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the editCompanConf method of InvestigationRuleAction");
            		setLinks(request);
            		// RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynAmbulanceRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("AmbulanceRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		 
            		 // investigationRuleVO= ruleManagerObject.getConsDtls(investigationRuleVO);
            		  
            		  investigationRuleVO.setBenefitID(frmDynAmbulanceRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynAmbulanceRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  frmDynAmbulanceRulesConfig = (DynaActionForm) FormUtils.setFormValues("frmDynAmbulanceRulesConfig", investigationRuleVO, this, mapping,request);
            		  session.setAttribute("frmDynAmbulanceRulesConfig", frmDynAmbulanceRulesConfig);
            		  
            	  }
            		return mapping.findForward(strAmbulanceRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of editAmbulanceConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    

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
    public ActionForward deleteAmbulanceDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the deleteAmbulanceDtls method of InvestigationRuleAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		DynaActionForm frmDynAmbulanceRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("AmbulanceRuleDetails");
            	  if(request.getParameter("rowNum")!=null&&alInvetVO!=null){
            		  InvestigationRuleVO  investigationRuleVO =alInvetVO.get(new Integer(request.getParameter("rowNum")).intValue());
            		  int i=  ruleManagerObject.deleteConsdtls(investigationRuleVO);
            		
            		  investigationRuleVO.setBenefitID(frmDynAmbulanceRulesConfig.getString("benefitID"));
            		  investigationRuleVO.setProPolRuleSeqID(frmDynAmbulanceRulesConfig.getString("proPolRuleSeqID"));
            		  
            		  request.setAttribute("displayMsg","Deleted Successfully!");
            		  session.setAttribute("AmbulanceRuleDetails", ruleManagerObject.getAmbulanceDetails(investigationRuleVO));
            	  }
            		return mapping.findForward(strAmbulanceRuleConfig) ;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
        		}//end of catch(Exception exp)
            }//end of deleteAmbulanceDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    

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
    public ActionForward viewAmbulanceStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the viewAmbulanceStaticConfDtls method of InvestigationRuleAction");
            		setLinks(request);
            		// RuleManager ruleManagerObject=this.getRuleManagerObject();
            		 HttpSession session=request.getSession();
            		//DynaActionForm frmDynConsRulesConfig= (DynaActionForm)form;
            	  
            	 ArrayList<InvestigationRuleVO> alInvetVO=(ArrayList<InvestigationRuleVO>)session.getAttribute("AmbulanceRuleDetails");
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
            }//end of viewAmbulanceStaticConfDtls(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    private ArrayList getInvestTypes(){
    	ArrayList alDaynValues=new ArrayList();
		
		CacheObject cache1=new CacheObject();         			
		cache1.setCacheId("80050");
		cache1.setCacheDesc("Lab");
		alDaynValues.add(cache1);
		
		CacheObject cache2=new CacheObject();         			
		cache2.setCacheId("88399");
		cache2.setCacheDesc("Pathology");
		alDaynValues.add(cache2);
		
		CacheObject cache3=new CacheObject();         			
		cache3.setCacheId("76999");
		cache3.setCacheDesc("Ultrasounds");
		alDaynValues.add(cache3);
		
		
		CacheObject cache4=new CacheObject();         			
		cache4.setCacheId("70450");
		cache4.setCacheDesc("CT Scans");
		alDaynValues.add(cache4);
		
		CacheObject cache5=new CacheObject();         			
		cache5.setCacheId("70551");
		cache5.setCacheDesc("MRI Scans");
		alDaynValues.add(cache5);
		
		CacheObject cache6=new CacheObject();         			
		cache6.setCacheId("71010");
		cache6.setCacheDesc("Diagnostic and Therapeutic Radiology");
		alDaynValues.add(cache6);
		
		return alDaynValues;
    }
    private String getInvestLabel(String investCode){
    	String investLabel="";
		
		
		if("80050".equals(investCode)) investLabel="Lab";
		else if("88399".equals(investCode)) investLabel="Pathology";
		else if("76999".equals(investCode)) investLabel="Ultrasounds";
		else if("70450".equals(investCode)) investLabel="CT Scans";
		else if("70551".equals(investCode)) investLabel="MRI Scans";
		else if("71010".equals(investCode)) investLabel="Diagnostic and Therapeutic Radiology";
		
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