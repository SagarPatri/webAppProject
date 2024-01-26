package com.ttk.action.insurancepricing;



import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.RuleManager;
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.insurancepricing.AreaOfCoverVO;
import com.ttk.dto.insurancepricing.InsPricingVO;
import com.ttk.dto.insurancepricing.MasterFactorVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class SwInsPricingOpAreaOfCoverAction extends TTKAction  {
	private static Logger log = Logger.getLogger( SwInsPricingOpAreaOfCoverAction.class );
	  
    private static final String strInsError="insurance";
	
	private static final String strPricingOpAOC="pricingOpAOC";


	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDefault method of SwInsPricingOpAreaOfCoverAction");
			setLinks(request);
			
			DynaActionForm frmSwPricingOpAOC = (DynaActionForm)form;
			ArrayList<AreaOfCoverVO> opAocList = null;
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();

            opAocList = insuranceObject.getPriceOpAOC();
            
            
            frmSwPricingOpAOC.set("opAocList",opAocList);
   		    request.getSession().setAttribute("frmSwPricingOpAOC",frmSwPricingOpAOC);
           
            return mapping.findForward(strPricingOpAOC);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		}//end of catch(Exception exp)	
			
			
	}// end of doDefaultTendMaster(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
	   public ActionForward openConfigWindow(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
	       try
	       {
	       	log.debug("Inside the openConfigWindow method of SpecialBenefitsRuleAction");
	           		setLinks(request);
	           		 DynaActionForm frmDynVaccinRulesConfig= (DynaActionForm)form;  // frmDynVaccinRulesConfig
	                    RuleManager ruleManagerObject=this.getRuleManagerObject();

	           		 String strConfigValues=request.getParameter("configValues");
	           		 if("NTWK".equals(request.getParameter("windoType"))){
	            			request.setAttribute("populateList", Cache.getCacheObject("networkList"));
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
	
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of SwInsPricingOpAreaOfCoverAction");
			setLinks(request);
			
			DynaActionForm frmSwPricingOpAOC = (DynaActionForm)form;
			ArrayList<AreaOfCoverVO> opAocList = null;
			opAocList=(ArrayList<AreaOfCoverVO>)frmSwPricingOpAOC.get("frmSwPricingOpAOC");
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
            

            Long result = insuranceObject.savePriceOpAOC(opAocList);

        	

            opAocList = insuranceObject.getPriceOpAOC();
            
            frmSwPricingOpAOC.set("opAocList",opAocList);
            
            request.getSession().setAttribute("frmSwPricingOpAOC", frmSwPricingOpAOC);
            
			return mapping.findForward(strPricingOpAOC);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		}//end of catch(Exception exp)	
			
			
	}// end of doDefaultTendMaster(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	 private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSwPricingHome,
				HttpServletRequest request)throws TTKException
		{
			//build the column names along with their values to be searched
			ArrayList<Object> alSearchParams = new ArrayList<Object>();
			//alSearchParams.add(new SearchCriteria("EMPANELED_DATE", (String)frmSearchInsurance.get("sEmpanelDate")));
			alSearchParams.add(TTKCommon.checkNull(frmSwPricingHome.get("pricingRefno")));
			alSearchParams.add(TTKCommon.checkNull(frmSwPricingHome.get("previousPolicyNo")));
			alSearchParams.add(TTKCommon.checkNull(frmSwPricingHome.get("clientCode")));

	    	return alSearchParams;
		}
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
    
   
} //end of InsPricingAction


