/**
 * @ (#) RuleAction.java Jul 8, 2006
 * Project      : TTK HealthCare Services
 * File         : RuleAction.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.Node;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.administration.RuleManager;
import com.ttk.common.RuleXmlUtility;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.administration.HMOGlobalVO;
import com.ttk.dto.administration.HmoAOCVo;
import com.ttk.dto.administration.HmoGnrlExlcusnVO;
import com.ttk.dto.administration.HmoInPatientVO;
import com.ttk.dto.administration.HmoOptcDntlVo;
import com.ttk.dto.administration.HmoOutPatientVO;
import com.ttk.dto.administration.HmoSpcbVO;
import com.ttk.dto.administration.InvestigationRuleVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.administration.RuleVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.xml.administration.Benefit;
import com.ttk.xml.administration.Condition;
import com.ttk.xml.administration.CountryCopayDetails;
import com.ttk.xml.administration.EmirateCopayDetails;
import com.ttk.xml.administration.GeoLocationCopayDetails;
import com.ttk.xml.administration.ProviderRules;

import formdef.plugin.util.FormUtils;
@SuppressWarnings("unchecked")
public class MOHRuleAction extends TTKAction {
	
    private static Logger log = Logger.getLogger(MOHRuleAction.class); // Getting Logger for this Class file

    //declaration of forward paths
    private static final String strPolicyRule="policyrule";
    private static final String strProductrule="productrule";
    
    private static final String strNewProductRules="NewProductRules";
    
    private static final String strNewMOHProductRules="NewMOHProductRules";
    private static final String strAddBenefitRule="AddBenefitRule";
    private static final String strFailure="failure";
    
    private static final String strGenReport="genReport";
    
    private LinkedHashMap<String,String> benefitTypes=null;
    
    
    private static  Collection<Object> resultListGeo=new ArrayList<Object>();
    private static Collection<Object> resultListPro=new ArrayList<Object>();
    private static Collection<Object> resultListEmr=new ArrayList<Object>();
    private static Collection<Object> resultListRmTyp=new ArrayList<Object>();
    private static Collection<Object> resultListTmtType=new ArrayList<Object>();
    
    static{
    	
    	try {
			resultListGeo=Cache.getCacheObject("GeoLocations");
			resultListPro=Cache.getCacheObject("providerType");
	    	resultListEmr=Cache.getDynamicCacheObject("EmirateList", "175");
	    	resultListRmTyp=Cache.getCacheObject("roomType");
	    	resultListTmtType=Cache.getCacheObject("treatementType");
		} catch (TTKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
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
            DynaActionForm frmDefineRules= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
        
            String strTTKActiveSubLink=TTKCommon.getActiveSubLink(request);
           
           
            if(benefitTypes==null){
            	benefitTypes=ruleManagerObject.getBenefitTypes();
            	request.getSession().getServletContext().setAttribute("RuleBenefitList", benefitTypes);
            }
            
            if("Add".equals( request.getAttribute("ruleBtnType"))){            	
            	
            	
            	frmDefineRules.set("confBenefitTypes", ruleManagerObject.getConfBenefitTypes(frmDefineRules.getString("prodPolicyRuleSeqID")));
            }
             if("Policies".equals(strTTKActiveSubLink)) {            	 
            	LinkedHashMap<String,Long> lhmConfBenefitTypes= ruleManagerObject.getConfBenefitTypes(frmDefineRules.getString("prodPolicyRuleSeqID"));
            	 if(lhmConfBenefitTypes.size()<1)
                 {
            		 ActionMessages actionMessages = new ActionMessages();
                     ActionMessage actionMessage = new ActionMessage("error.administration.policyrule.notdefined");
                     actionMessages.add("global.error",actionMessage);
                     saveErrors(request,actionMessages);
                    return mapping.findForward("failure"); 
                 }//end of if(ruleVO==null)
            	 
            	 frmDefineRules.set("confBenefitTypes", lhmConfBenefitTypes);
             }
            return this.getForward(strNewMOHProductRules, mapping, request);
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
    
	public ActionForward saveAndComplete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.info("MOHMOHRuleAction - inside saveAndComplete method....");
            setLinks(request);
            DynaActionForm frmDefineRules= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            LinkedHashMap<String, Long> lhmConfBenefitTypes=(LinkedHashMap<String, Long>)frmDefineRules.get("confBenefitTypes");
          
            if(lhmConfBenefitTypes.size()<2){
        	   TTKException ttkExc=new TTKException();
   			ttkExc.setMessage("error.add.atleat.one.rule");   			
   			throw ttkExc;
           }
            
            int update=ruleManagerObject.saveAndCompleteRules(frmDefineRules.getString("prodPolicyRuleSeqID"),TTKCommon.getUserSeqId(request));
            frmDefineRules.set("benefitRuleCompletedYN","Y");
            request.setAttribute("displayMsg","Rules are completed successfully!");
            return this.getForward(strNewMOHProductRules, mapping, request);
        }
            catch(TTKException expTTK)
            {
                return this.processExceptions(request, mapping, expTTK);
            }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of saveAndComlete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
  
    public ActionForward doBenefitConfiguration(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	//log.info("Inside doBenefitConfiguration ");
    	 try{
    		 setLinks(request);
    		 DynaActionForm frmDefineRules= (DynaActionForm)form;
    		 String strBenefitID=	request.getParameter("benefitID");
    		
    		 
    	if(TTKCommon.checkNull(frmDefineRules.getString("prodPolicyRuleSeqID")).length()<1){
    		    		
    		if(!"GLOB".equals(strBenefitID)){
    			TTKException ttkExc=new TTKException();
    			ttkExc.setMessage("error.global.rules.add.first");
    			throw ttkExc;
    		}
    	}//if(confBenfRulesList==null){
    	 LinkedHashMap<String, Node> ruleBenefitNodeList=(LinkedHashMap<String, Node>)request.getSession().getServletContext().getAttribute("RuleBenefitNodeList");
    	 	if(!ruleBenefitNodeList.containsKey(strBenefitID)){
    	 		TTKException ttkExc=new TTKException();
    			ttkExc.setMessage("error.global.rules.benefitid.notfound");
    			throw ttkExc;
    		}
    		RuleManager ruleManagerObject=this.getRuleManagerObject();
    		HashMap<String,ArrayList<Condition>> hmProCopayDetails=null;
    		String strConfType=request.getParameter("confType");
    		if("R".equals(strConfType)||"V".equals(strConfType)){
    		 
    		LinkedHashMap<String,Long> lhmConfBenefitTypes=(LinkedHashMap)frmDefineRules.get("confBenefitTypes");
    	/*	log.info("lhmConfBenefitTypes: "+lhmConfBenefitTypes.toString());
    		log.info("strBenefitID: "+lhmConfBenefitTypes.get(strBenefitID));*/
    		request.setAttribute("CurtNodeConfDtls", ruleManagerObject.getConfBenefitXml(lhmConfBenefitTypes.get(strBenefitID)));       
    		Reader reader=ruleManagerObject.getMohProCopayDetails(lhmConfBenefitTypes.get(strBenefitID));
    	
    		if(reader!=null){
    			hmProCopayDetails=getProviderCopayRulesUnmarshaller(reader);
    		}
    	}
    		request.getSession().setAttribute("OPConfType",strConfType);
    		request.getSession().setAttribute("MOHProviderRulesXmlConditions",hmProCopayDetails);
    	
    	return this.getForward(strAddBenefitRule, mapping, request);
    	 }
    	 catch(TTKException expTTK)
         {
             return this.processExceptions(request, mapping, expTTK);
         }//end of catch(TTKException expTTK)
         catch(Exception exp)
         {
             return this.processExceptions(request, mapping, new TTKException(exp, "product"));
         }//end of catch(Exception exp)
    	
    }
	
	
    public ActionForward hmoPolicyReport(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	log.info("Inside hmoPolicyReport ");
    	 try{
    		 setLinks(request);
    		 DynaActionForm frmDefineRules= (DynaActionForm)form;
    		 String prodPolicyRuleSeqID = frmDefineRules.getString("prodPolicyRuleSeqID");
    		 RuleManager ruleManagerObject=this.getRuleManagerObject();
    		 
    		 ArrayList<HMOGlobalVO> policyArray=ruleManagerObject.getActivePolicies(prodPolicyRuleSeqID);

    		
    		 String strBenefitID=null;

    	 LinkedHashMap<String, Node> ruleBenefitNodeList=(LinkedHashMap<String, Node>)request.getSession().getServletContext().getAttribute("RuleBenefitNodeList");
    	
    	 int policycount=1;
    		HSSFWorkbook wb = new HSSFWorkbook();
    	   	HSSFSheet sheet =null;
    	   	HSSFRow rowhead= null;
    	   	HSSFRow row = null;
    	   	log.info("policyArray size: "+policyArray.size());

    	   	
    	   	
    	for (HMOGlobalVO policyvo : policyArray) {
    		String policyNo=policyvo.getPolicyNo();
    		String policyNm = policyvo.getPolicyNm();
    		
    		log.info("policyNm: "+policyNm);
    	/*	log.info("getSumInsured: "+policyvo.getSumInsured());
    		log.info("getCorporateGroupID: "+policyvo.getCorporateGroupID());*/
    		//log.info("getPolicyAdmnstrtvSrvcType: "+policyvo.getPolicyAdmnstrtvSrvcType());
    		
    		 InvestigationRuleVO investigationRuleVO=new InvestigationRuleVO();
         	 investigationRuleVO.setProPolRuleSeqID(policyNo);
    		
    		
    		HashMap<String,ArrayList<Condition>> hmProCopayDetails=null;
    		//started by hare govind
    		HashMap hmoDataMap=new HashMap();
    		// Global
    		HMOGlobalVO globalVO=new HMOGlobalVO();
    		globalVO.setPolicyNo(policyvo.getPolicyNm());
    		globalVO.setPolicyNm(policyvo.getPolicyNm());
    		globalVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
    		globalVO.setProductName(policyvo.getProductName());
    		globalVO.setCorporateGroupID(policyvo.getCorporateGroupID());
    		
    		globalVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
    		globalVO.setCorporateGroupName(policyvo.getCorporateGroupName());
    		globalVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
    		globalVO.setSumInsured(policyvo.getSumInsured());
    	
    		// OUT PATIENT
        ArrayList<HmoOutPatientVO> outPatientVOs=new ArrayList<HmoOutPatientVO>();
        HmoOutPatientVO outPatientVO=new HmoOutPatientVO(); // first vo of op
    	HashMap<String, String> opOuterData=new HashMap<String, String>();
       	outPatientVO.setPolicyNo(policyNm);
     	outPatientVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
     	outPatientVO.setProductName(policyvo.getProductName());
     	outPatientVO.setCorporateGroupID(policyvo.getCorporateGroupID());
     	
     	outPatientVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
     	outPatientVO.setCorporateGroupName(policyvo.getCorporateGroupName());
     	outPatientVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
     	outPatientVO.setSumInsured(policyvo.getSumInsured());
    	
    	// IN PATIENT
    	
        ArrayList<HmoInPatientVO> inPatientVOs=new ArrayList<HmoInPatientVO>();
        HmoInPatientVO inPatientVO=new HmoInPatientVO();  // first vo for ip
       	HashMap<String, String> ipOuterData=new HashMap<String, String>();
       	inPatientVO.setPolicyNo(policyNm);
       	inPatientVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
       	inPatientVO.setProductName(policyvo.getProductName());
       	inPatientVO.setCorporateGroupID(policyvo.getCorporateGroupID());
       	
       	inPatientVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
       	inPatientVO.setCorporateGroupName(policyvo.getCorporateGroupName());
       	inPatientVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
       	inPatientVO.setSumInsured(policyvo.getSumInsured());
       	
     // DAY CARE
        ArrayList<HmoInPatientVO> dayCareVOs=new ArrayList<HmoInPatientVO>();
       	HashMap<String, String> dcOuterData=new HashMap<String, String>();
        HmoInPatientVO dcPatientVO=new HmoInPatientVO();
        dcPatientVO.setPolicyNo(policyNm);
        dcPatientVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
        dcPatientVO.setProductName(policyvo.getProductName());
        dcPatientVO.setCorporateGroupID(policyvo.getCorporateGroupID());
        
        dcPatientVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
        dcPatientVO.setCorporateGroupName(policyvo.getCorporateGroupName());
        dcPatientVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
        dcPatientVO.setSumInsured(policyvo.getSumInsured());
       	
       	// Optical And Dental
       	
       	HmoOptcDntlVo opticaVo=new HmoOptcDntlVo();
       	opticaVo.setPolicyNo(policyNm);
       	opticaVo.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
       	opticaVo.setProductName(policyvo.getProductName());
       	opticaVo.setCorporateGroupID(policyvo.getCorporateGroupID());
       	
       	opticaVo.setProductNetworkCategory(policyvo.getProductNetworkCategory());
       	opticaVo.setCorporateGroupName(policyvo.getCorporateGroupName());
       	opticaVo.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
       	opticaVo.setSumInsured(policyvo.getSumInsured());
       	
    	HmoOptcDntlVo dentalVo=new HmoOptcDntlVo();
    	dentalVo.setPolicyNo(policyNm);
    	dentalVo.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
    	dentalVo.setProductName(policyvo.getProductName());
    	dentalVo.setCorporateGroupID(policyvo.getCorporateGroupID());
    	
    	dentalVo.setProductNetworkCategory(policyvo.getProductNetworkCategory());
    	dentalVo.setCorporateGroupName(policyvo.getCorporateGroupName());
    	dentalVo.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
    	dentalVo.setSumInsured(policyvo.getSumInsured());
       	
    		// General Exclusion
    	    HmoGnrlExlcusnVO exlcusnVO=null; 
    	    List<HmoGnrlExlcusnVO> exclusionData=new ArrayList<HmoGnrlExlcusnVO>();
    	    ArrayList<String> ttlGnrxField=null;
    	    ArrayList<String> ttlAvlField=null;
    	    
    	 // Special Benefits
    	    HmoSpcbVO spclBeneVO=null;
    	    List<HmoSpcbVO> spclBeneData=new ArrayList<HmoSpcbVO>();
    	    List<HmoSpcbVO> spcbNp=new ArrayList<HmoSpcbVO>();
    	    ArrayList<String[]> spcbOuterData=new ArrayList<String[]>();
    	    String[] spcbStringArray=null;
    	    
    	    // Area of Coverage
    	    
    	 //   HmoAOCVo aocVo=new HmoAOCVo();
 
    		ArrayList<String> benefitList=new ArrayList<String>();

    		LinkedHashMap<String,Long> lhmConfBenefitTypes=ruleManagerObject.getConfBenefitTypes(policyNo);
    		benefitList.addAll(lhmConfBenefitTypes.keySet());
    	   // log.info("lhmConfBenefitTypes:"+lhmConfBenefitTypes);

    		for(String benefitId : benefitList){     
	          strBenefitID=benefitId;
	          if("GLOB".equals(strBenefitID) || "OPTS".equals(strBenefitID) || "IPT".equals(strBenefitID) || "DAYC".equals(strBenefitID) || "GNRX".equals(strBenefitID) || "DNTL".equals(strBenefitID) || "OPTC".equals(strBenefitID) ||"SPCB".equals(strBenefitID) ){
	        	  
	    			// end by govind
	    
	    		request.setAttribute("CurtNodeConfDtls", ruleManagerObject.getConfBenefitXml(lhmConfBenefitTypes.get(strBenefitID)));       
	    		Reader reader=ruleManagerObject.getMohProCopayDetails(lhmConfBenefitTypes.get(strBenefitID));

	    		if(reader!=null){
	    			hmProCopayDetails=getProviderCopayRulesUnmarshaller(reader);
	    		}
	
	       		 Node benefitNode=ruleBenefitNodeList.get(strBenefitID);

	       		 List<Node> sbNodes=benefitNode.selectNodes(".//sub-benefit");
	       	     Document benefitDoc=null;
	         	   
	         	    benefitDoc=(Document)request.getAttribute("CurtNodeConfDtls");
	         	   if("GNRX".equalsIgnoreCase(strBenefitID)){
	         		  ttlGnrxField=new ArrayList<String>();
	         	
	         		 ttlAvlField=new ArrayList<String>();
	         	   }
	            	for(Node sbNode:sbNodes){
	            	
	            		if("GNRX".equalsIgnoreCase(strBenefitID)){
	            			exlcusnVO=new HmoGnrlExlcusnVO();
	            			exlcusnVO.setPolicyNo(policyvo.getPolicyNm());
	            			exlcusnVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	            			exlcusnVO.setProductName(policyvo.getProductName());
	            			exlcusnVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	            			
	            			exlcusnVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	            			exlcusnVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	            			exlcusnVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	            			exlcusnVO.setSumInsured(policyvo.getSumInsured());
	            	
	            		if("S68".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            			
	            			ttlGnrxField.add("period");
	            			ttlGnrxField.add("days");
	            		}else if("S57".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            			
	            			ttlGnrxField.add("day");
	            			ttlGnrxField.add("date");
	            		
	            			
	            		}else{
	            			ttlGnrxField.add("date");
	            			ttlGnrxField.add("days");
	            			
	            		}
	            		
		               if("S55".equalsIgnoreCase(sbNode.valueOf("@id")) || "S56".equalsIgnoreCase(sbNode.valueOf("@id")) || "S57".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S58".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S59".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S60".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S61".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S64".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S68".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            			
		            	   ttlGnrxField.add("copay");
	            		}else{
	            			
	            			ttlGnrxField.add("copay-limit");
	            			
	            		}
	 
	        			ttlGnrxField.add("deductible");
	        			ttlGnrxField.add("mm");
	        		//	ttlGnrxField.add("per-policy-limit");
	        			 if("S61".equalsIgnoreCase(sbNode.valueOf("@id")) || "S62".equalsIgnoreCase(sbNode.valueOf("@id")) || "S63".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S64".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S65".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S66".equalsIgnoreCase(sbNode.valueOf("@id"))|| "S67".equalsIgnoreCase(sbNode.valueOf("@id"))){
		            			
			            	   ttlGnrxField.add("limit");
		            		}else{
		            			
		            			ttlGnrxField.add("per-policy-limit");
		            			
		            		}
	        			ttlGnrxField.add("per-claim-limit");
	        			
	        			ttlGnrxField.add("out-apply");
	        			ttlGnrxField.add("ttype");
	        			ttlGnrxField.add("Operators1");
	        			ttlGnrxField.add("Years");
	        			ttlGnrxField.add("age");
	            		}
	            		
	            	if("SPCB".equalsIgnoreCase(strBenefitID)){
	            		    spcbStringArray=new String[4];
	            		    
	            		    spclBeneVO=new HmoSpcbVO();
	            		}
	            		
	            		String strSBID=sbNode.valueOf("@id");
	            		String topHeading=sbNode.valueOf("@topHeading");
	            		
	            		String name=sbNode.valueOf("@name");
	  
	            		Node conTypeNode=sbNode.selectSingleNode("./condition-type");
	            		
	            		String strCondTypeDefault="";
	            		
	            			String strLookup=conTypeNode.valueOf("@lookup");
	   	
	            			if(benefitDoc!=null){
	            				Node condTypeNode=benefitDoc.selectSingleNode("//sub-benefit[@id ='"+strSBID+"']/condition-type");
	            				if(condTypeNode!=null){
	            				strCondTypeDefault=condTypeNode.valueOf("@default"); 
	            				}
	            				else strCondTypeDefault=conTypeNode.valueOf("@default");
	            			
	            			}
	            			else strCondTypeDefault=conTypeNode.valueOf("@default");

	            			
	            			List<Node> conNodes=sbNode.selectNodes(".//condition");
	           
	            		for(Node conNode:conNodes){
	            			
	            			String strConID=conNode.valueOf("@id");
	            			String strSerNO=conNode.valueOf("@serNO");
	            			String cssClass=conNode.valueOf("@cssClass");//"liDiv";
	            			 
	            		
	            			List<Node> fieldNodes=conNode.selectNodes(".//field");
	            			
	            			for(Node fieldNode:fieldNodes){
	            				String strDefaultValue=fieldNode.valueOf("@default");
	            			
	            				if(benefitDoc!=null&&"3".equals(strCondTypeDefault)){
	            					Node confFieldNode=benefitDoc.selectSingleNode("//sub-benefit[@id = '"+strSBID+"']/condition[@id = '"+strConID+"']/field[@name = '"+fieldNode.valueOf("@name")+"']");
	            				
	            					if(confFieldNode!=null&&TTKCommon.checkNull(confFieldNode.valueOf("@default")).length()>0)strDefaultValue=confFieldNode.valueOf("@default");         					
	            					
	            				}
	            				// Started by Govind
	            				
	             				if("GLOB".equalsIgnoreCase(strBenefitID)){
	            					
	            					if("mem-active".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                					globalVO.setGlobMemberStatus(fieldNode.valueOf("@optionLabels"));
	                				}
	                				else if("in-days".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                					
	                					globalVO.setGlobPreauthAdmisnPeriodWithinUAE(strDefaultValue);
	                					
	                				}else if("out-days".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                					
	                					globalVO.setGlobPreauthAdmisnPeriodOutsideUAE(strDefaultValue);
	                					
	                				}else if("ntw-in-days".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                					
	                					globalVO.setGlobClmSubPrdNtwkClmWithinUAE(strDefaultValue);
	                					
	                				}else if("ntw-out-days".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                					
	                					globalVO.setGlobClmSubPrdNtwkClmOutsideUAE(strDefaultValue);
	                					
	                				}else if("mem-in-days".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                					
	                					globalVO.setGlobClmSubPrdMemClmWithinUAE(strDefaultValue);
	                					
	                				}else if("mem-out-days".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                					
	                					globalVO.setGlobClmSubPrdMemClmOutsideUAE(strDefaultValue);
	                					
	                				}	
	                				
	            					
	            				}else if("OPTS".equalsIgnoreCase(strBenefitID)){
	            					
	            					if("S1".equalsIgnoreCase(strSBID)){
	            						
	            				        if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C4".equals(strConID)){
	                    					
	                    					opOuterData.put("OP_PPL", strDefaultValue);
	                    					
	                    				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C7".equals(strConID)){
	                    					
	                    					opOuterData.put("OP_CHRONIC_PPL", strDefaultValue);
	                    					
	                    				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))&& "C8".equals(strConID)){
	                    					
	                    					opOuterData.put("OP_PED_PPL", strDefaultValue);  
	                    				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C6".equals(strConID)){
	                    					
	                    					opOuterData.put("OP_CHRON_PLUS_PED_PPL", strDefaultValue);
	                    					
	                    					
	                    				}
	            						
	            					}else if("S4".equalsIgnoreCase(strSBID)){
	            						
	                                  if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C1".equals(strConID)){
	                    					
	                                	  opOuterData.put("OP_OVERALL_INVST_PPL", strDefaultValue);
	                    				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C2".equals(strConID)){
	                    					
	                    					opOuterData.put("OP_LAB_AND_PATH_PPL", strDefaultValue);
	                    				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C3".equals(strConID)){
	                    					
	                    					opOuterData.put("OP_EXCEPT_LAB_AND_PATH_PPL", strDefaultValue);
	                    				}
	            						
	            					}
	            					
	            					
	            				}else if("IPT".equalsIgnoreCase(strBenefitID)){
	            					
		                              if("S1".equalsIgnoreCase(strSBID)){
	            						
	            				        if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C2".equals(strConID)){
	                    					
	            				        	ipOuterData.put("IP_DAYC_PPL", strDefaultValue);
	                    					
	                    				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C7".equals(strConID)){
	                    					
	            				        	ipOuterData.put("IP_DAYC_CHRONIC_PPL", strDefaultValue);
	                    					
	                    				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C8".equals(strConID)){
	                    					
	            				        	ipOuterData.put("IP_DAYC_PED_PPL", strDefaultValue);
	                    					
	                    				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C6".equals(strConID)){
	                    					
	            				        	ipOuterData.put("IP_DAYC_PED_AND_CHRON_PPL", strDefaultValue);
	                    					
	                    				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C9".equals(strConID)){
	                    					
	            				        	ipOuterData.put("IP_DAYC_ONE_MEDICAL_PPL", strDefaultValue);
	                    					
	                    				}
		                        }else if("S2".equalsIgnoreCase(strSBID)){
	        						
	        				        if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C2".equals(strConID)){
	                					
	        				        	ipOuterData.put("IP_PPL", strDefaultValue);
	                					
	                				}
	                        }else if("S3".equalsIgnoreCase(strSBID)){
	    						
	    				        if("room-type".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            					
	    				        	ipOuterData.put("IP_ROOM_TYPES", strDefaultValue);
	            					
	            				}else if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            					
	    				        	ipOuterData.put("IP_ROOM_PPL", strDefaultValue);
	            					
	            				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            					
	    				        	ipOuterData.put("IP_ROOM_COPAY", strDefaultValue);
	            					
	            				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            					
	    				        	ipOuterData.put("IP_ROOM_DEDUCT", strDefaultValue);
	            					
	            				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            					
	    				        	ipOuterData.put("IP_ROOM_MinMax", strDefaultValue);
	            					
	            				}
	                    }else if("S4".equalsIgnoreCase(strSBID)){
							
					         if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
					        	ipOuterData.put("IP_ICU_PPL", strDefaultValue);
	        					
	        				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
					        	ipOuterData.put("IP_ICU_COPAY", strDefaultValue);
	        					
	        				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
					        	ipOuterData.put("IP_ICU_DEDUCT", strDefaultValue);
	        					
	        				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
					        	ipOuterData.put("IP_ICU_MinMax", strDefaultValue);
	        					
	        				}
	                }else if("S6".equalsIgnoreCase(strSBID)){
	                	
	                	if("C1".equals(strConID)){
	                		
	                	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            					
	     			        	ipOuterData.put("IP_SGR_PPL", strDefaultValue);
	        					
	        				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
	     			        	ipOuterData.put("IP_SGR_COPAY", strDefaultValue);
	        					
	        				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
	     			        	ipOuterData.put("IP_SGR_DEDUCT", strDefaultValue);
	        					
	        				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
	     			        	ipOuterData.put("IP_SGR_MinMax", strDefaultValue);
	        					
	        				}
	                		
	                	}else if("C2".equals(strConID)){
	                		
	                	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            					
	     			        	ipOuterData.put("IP_ANSTHES_PPL", strDefaultValue);
	        					
	        				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
	     			        	ipOuterData.put("IP_ANSTHES_COPAY", strDefaultValue);
	        					
	        				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
	     			        	ipOuterData.put("IP_ANSTHES_DEDUCT", strDefaultValue);
	        					
	        				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        					
	     			        	ipOuterData.put("IP_ANSTHES_MinMax", strDefaultValue);
	        					
	        				}
	                		
	                	}
						
				    
	           }else if("S8".equalsIgnoreCase(strSBID)){
					
			        if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C1".equals(strConID)){
						
			        	ipOuterData.put("IP_OVERALL_INVST_PPL", strDefaultValue);
						
					}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C2".equals(strConID)){
						
			        	ipOuterData.put("IP_LAB_AND_PATH_PPL", strDefaultValue);
						
					}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C3".equals(strConID)){
						
			        	ipOuterData.put("IP_EXCEPT_LAB_AND_PATH_PPL", strDefaultValue);
						
					}
	       }else if("S9".equalsIgnoreCase(strSBID)){
				
	    		if("C1".equals(strConID)){
	        		
	       	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	   					
			        	ipOuterData.put("IP_MEDICATIONS_PPL", strDefaultValue);
						
					}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
			        	ipOuterData.put("IP_MEDICATIONS_COPAY", strDefaultValue);
						
					}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
			        	ipOuterData.put("IP_MEDICATIONS_DEDUCT", strDefaultValue);
						
					}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
			        	ipOuterData.put("IP_MEDICATIONS_MinMax", strDefaultValue);
						
					}
	       		
	       	}else if("C2".equals(strConID)){
	    		
	   	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
		        	ipOuterData.put("IP_IVFLUID_PPL", strDefaultValue);
					
				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_IVFLUID_COPAY", strDefaultValue);
					
				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_IVFLUID_DEDUCT", strDefaultValue);
					
				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_IVFLUID_MinMax", strDefaultValue);
					
				}
	   		
	   	}else if("C3".equals(strConID)){
			
	  	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
		        	ipOuterData.put("IP_BLD_TRNSFSN_PPL", strDefaultValue);
					
				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_BLD_TRNSFSN_COPAY", strDefaultValue);
					
				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_BLD_TRNSFSN_DEDUCT", strDefaultValue);
					
				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_BLD_TRNSFSN_MinMax", strDefaultValue);
					
				}
	  		
	  	}else if("C4".equals(strConID)){
			
	  	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
		        	ipOuterData.put("IP_ANALGESICS_PPL", strDefaultValue);
					
				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_ANALGESICS_COPAY", strDefaultValue);
					
				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_ANALGESICS_DEDUCT", strDefaultValue);
					
				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_ANALGESICS_MinMax", strDefaultValue);
					
				}
	  		
	  	}else if("C5".equals(strConID)){
			
	  	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
		        	ipOuterData.put("IP_SGRIMPL_PPL", strDefaultValue);
					
				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_SGRIMP_COPAY", strDefaultValue);
					
				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_SGRIMP_DEDUCT", strDefaultValue);
					
				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_SGRIMP_MinMax", strDefaultValue);
					
				}
	  		
	  	}else if("C6".equals(strConID)){
			
	  	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
		        	ipOuterData.put("IP_CHEMO_PPL", strDefaultValue);
					
				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_CHEMO_COPAY", strDefaultValue);
					
				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_CHEMO_DEDUCT", strDefaultValue);
					
				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
					
		        	ipOuterData.put("IP_CHEMO_MinMax", strDefaultValue);
					
				}
	  		
	  	}
	  }else if("S10".equalsIgnoreCase(strSBID)){
			
		
				
		  	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
							
			        	ipOuterData.put("IP_DISPOSAL_CONSUM_PPL", strDefaultValue);
						
					}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
			        	ipOuterData.put("IP_DISPOSAL_CONSUM_COPAY", strDefaultValue);
						
					}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
			        	ipOuterData.put("IP_DISPOSAL_CONSUM_DEDUCT", strDefaultValue);
						
					}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
			        	ipOuterData.put("IP_DISPOSAL_CONSUM_MinMax", strDefaultValue);
						
					}else if("period".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
			        	ipOuterData.put("IP_DISPOSAL_CONSUM_PRD_VAL", strDefaultValue);
						
					}else if("period-type".equalsIgnoreCase(fieldNode.valueOf("@name"))){
						
			        	ipOuterData.put("IP_DISPOSAL_CONSUM_PRD_UNT", strDefaultValue);
						
					}
		  		
		  	
	}
	            					
	            				}else if("DAYC".equalsIgnoreCase(strBenefitID)){
	            		
	            					
	            					 if("S2".equalsIgnoreCase(strSBID)){
	             						
	             				        if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C2".equals(strConID)){
	                     					
	             				        	dcOuterData.put("DC_DAYC_PPL", strDefaultValue);
	                     					
	                     				}
	                         }else if("S3".equalsIgnoreCase(strSBID)){
	     						
	     				        if("room-type".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	             					
	     				        	dcOuterData.put("DC_ROOM_TYPES", strDefaultValue);
	             					
	             				}else if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	             					
	             					dcOuterData.put("DC_ROOM_PPL", strDefaultValue);
	             					
	             				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	             					
	             					dcOuterData.put("DC_ROOM_COPAY", strDefaultValue);
	             					
	             				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	             					
	             					dcOuterData.put("DC_ROOM_DEDUCT", strDefaultValue);
	             					
	             				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	             					
	             					dcOuterData.put("DC_ROOM_MinMax", strDefaultValue);
	             					
	             				}
	                     }else if("S4".equalsIgnoreCase(strSBID)){
	 						
	 				         if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	 				        	dcOuterData.put("DC_ICU_PPL", strDefaultValue);
	         					
	         				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	         					dcOuterData.put("DC_ICU_COPAY", strDefaultValue);
	         					
	         				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	         					dcOuterData.put("DC_ICU_DEDUCT", strDefaultValue);
	         					
	         				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	         					dcOuterData.put("DC_ICU_MinMax", strDefaultValue);
	         					
	         				}
	                 }else if("S6".equalsIgnoreCase(strSBID)){
	                 	
	                 	if("C1".equals(strConID)){
	                 		
	                 	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	             					
	                 	    	dcOuterData.put("DC_SGR_PPL", strDefaultValue);
	         					
	         				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	         					dcOuterData.put("DC_SGR_COPAY", strDefaultValue);
	         					
	         				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	         					dcOuterData.put("DC_SGR_DEDUCT", strDefaultValue);
	         					
	         				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	         					dcOuterData.put("DC_SGR_MinMax", strDefaultValue);
	         					
	         				}
	                 		
	                 	}else if("C2".equals(strConID)){
	                 		
	                 	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	             					
	                 	    	dcOuterData.put("DC_ANSTHES_PPL", strDefaultValue);
	         					
	         				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	         					dcOuterData.put("DC_ANSTHES_COPAY", strDefaultValue);
	         					
	         				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	         					dcOuterData.put("DC_ANSTHES_DEDUCT", strDefaultValue);
	         					
	         				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	         					
	         					dcOuterData.put("DC_ANSTHES_MinMax", strDefaultValue);
	         					
	         				}
	                 		
	                 	}
	 					
	 			    
	            }else if("S8".equalsIgnoreCase(strSBID)){
	 				
	 		        if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C1".equals(strConID)){
	 					
	 		        	dcOuterData.put("DC_OVERALL_INVST_PPL", strDefaultValue);
	 					
	 				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C2".equals(strConID)){
	 					
	 					dcOuterData.put("DC_LAB_AND_PATH_PPL", strDefaultValue);
	 					
	 				}else if("policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) && "C3".equals(strConID)){
	 					
	 					dcOuterData.put("DC_EXCEPT_LAB_AND_PATH_PPL", strDefaultValue);
	 					
	 				}
	        }else if("S9".equalsIgnoreCase(strSBID)){
	 			
	     		if("C1".equals(strConID)){
	         		
	        	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	    					
	        	    	 dcOuterData.put("DC_MEDICATIONS_PPL", strDefaultValue);
	 					
	 				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	 					dcOuterData.put("DC_MEDICATIONS_COPAY", strDefaultValue);
	 					
	 				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	 					dcOuterData.put("DC_MEDICATIONS_DEDUCT", strDefaultValue);
	 					
	 				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	 					dcOuterData.put("DC_MEDICATIONS_MinMax", strDefaultValue);
	 					
	 				}
	        		
	        	}else if("C2".equals(strConID)){
	     		
	    	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	    	    	 dcOuterData.put("DC_IVFLUID_PPL", strDefaultValue);
	 				
	 			}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_IVFLUID_COPAY", strDefaultValue);
	 				
	 			}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_IVFLUID_DEDUCT", strDefaultValue);
	 				
	 			}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_IVFLUID_MinMax", strDefaultValue);
	 				
	 			}
	    		
	    	}else if("C3".equals(strConID)){
	 		
	   	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	   	    	dcOuterData.put("DC_BLD_TRNSFSN_PPL", strDefaultValue);
	 				
	 			}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_BLD_TRNSFSN_COPAY", strDefaultValue);
	 				
	 			}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_BLD_TRNSFSN_DEDUCT", strDefaultValue);
	 				
	 			}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_BLD_TRNSFSN_MinMax", strDefaultValue);
	 				
	 			}
	   		
	   	}else if("C4".equals(strConID)){
	 		
	   	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	   	    	dcOuterData.put("DC_ANALGESICS_PPL", strDefaultValue);
	 				
	 			}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_ANALGESICS_COPAY", strDefaultValue);
	 				
	 			}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_ANALGESICS_DEDUCT", strDefaultValue);
	 				
	 			}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_ANALGESICS_MinMax", strDefaultValue);
	 				
	 			}
	   		
	   	}else if("C5".equals(strConID)){
	 		
	   	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	   	    	dcOuterData.put("DC_SGRIMPL_PPL", strDefaultValue);
	 				
	 			}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_SGRIMP_COPAY", strDefaultValue);
	 				
	 			}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_SGRIMP_DEDUCT", strDefaultValue);
	 				
	 			}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_SGRIMP_MinMax", strDefaultValue);
	 				
	 			}
	   		
	   	}else if("C6".equals(strConID)){
	 		
	   	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	   	    	dcOuterData.put("DC_CHEMO_PPL", strDefaultValue);
	 				
	 			}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_CHEMO_COPAY", strDefaultValue);
	 				
	 			}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_CHEMO_DEDUCT", strDefaultValue);
	 				
	 			}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 				
	 				dcOuterData.put("DC_CHEMO_MinMax", strDefaultValue);
	 				
	 			}
	   		
	   	}
	   }else if("S10".equalsIgnoreCase(strSBID)){
	 		
	 	
	 			
	 	  	     if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 						
	 	  	    	dcOuterData.put("DC_DISPOSAL_CONSUM_PPL", strDefaultValue);
	 					
	 				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	 					dcOuterData.put("DC_DISPOSAL_CONSUM_COPAY", strDefaultValue);
	 					
	 				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	 					dcOuterData.put("DC_DISPOSAL_CONSUM_DEDUCT", strDefaultValue);
	 					
	 				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	 					dcOuterData.put("DC_DISPOSAL_CONSUM_MinMax", strDefaultValue);
	 					
	 				}else if("period".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	 					dcOuterData.put("DC_DISPOSAL_CONSUM_PRD_VAL", strDefaultValue);
	 					
	 				}else if("period-type".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	 					
	 					dcOuterData.put("DC_DISPOSAL_CONSUM_PRD_UNT", strDefaultValue);
	 					
	 				}
	 	  		
	 	  	
	 }
	            					
	            				}else if("GNRX".equalsIgnoreCase(strBenefitID)){
	            					
	            					ttlAvlField.add(fieldNode.valueOf("@name"));
	            					exlcusnVO.setGnrxBenTypes(sbNode.valueOf("@name"));
	            					
	                               if(("days".equalsIgnoreCase(fieldNode.valueOf("@name")) && !("S68".equalsIgnoreCase(strSBID)))  || "day".equalsIgnoreCase(fieldNode.valueOf("@name")) || "period".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                            	   if("3".equals(strCondTypeDefault)){
	                            		   exlcusnVO.setExclusionPeriodValue(strDefaultValue);
	                            	   }else if("2".equals(strCondTypeDefault)){
	                            		   
	                            		   exlcusnVO.setExclusionPeriodValue("NC");
	                            		   
	                            	   }else{
	                            		   
	                            		   exlcusnVO.setExclusionPeriodValue("Y");
	                            	   }
	            						
	            					}else if ("date".equalsIgnoreCase(fieldNode.valueOf("@name")) || ("days".equalsIgnoreCase(fieldNode.valueOf("@name")) && "S68".equalsIgnoreCase(strSBID))){
	            						
	         
	            						   if("3".equals(strCondTypeDefault)){
	            							   exlcusnVO.setExclusionPeriodUnit(unitConverter(strDefaultValue));
	                                	   }else if("2".equals(strCondTypeDefault)){
	                                		   
	                                		   exlcusnVO.setExclusionPeriodUnit("NC");
	                                		   
	                                	   }else{
	                                		   
	                                		   exlcusnVO.setExclusionPeriodUnit("Y");
	                                	   }
	            					}else if ("copay-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))||"copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	        
	            						if("3".equals(strCondTypeDefault)){
	         								exlcusnVO.setCopay(strDefaultValue);
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   
	                             		   exlcusnVO.setCopay("NC");
	                             		   
	                             	   }else{
	                             		   
	                             		   exlcusnVO.setCopay("Y");
	                             	   }
	            					}else if ("deductible".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            						
	            						
	            						if("3".equals(strCondTypeDefault)){
	            							exlcusnVO.setDeductible(strDefaultValue);
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   
	                             		   exlcusnVO.setDeductible("NC");
	                             		   
	                             	   }else{
	                             		   
	                             		   exlcusnVO.setDeductible("Y");
	                             	   }
	            					}else if ("mm".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            						
	            						
	            						if("3".equals(strCondTypeDefault)){
	            							exlcusnVO.setCopayDeductibleMINMAX(strDefaultValue);
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   
	                             		   exlcusnVO.setCopayDeductibleMINMAX("NC");
	                             		   
	                             	   }else{
	                             		   
	                             		   exlcusnVO.setCopayDeductibleMINMAX("Y");
	                             	   }
	            					}else if ("per-policy-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) || "limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            					
	            						if("3".equals(strCondTypeDefault)){
	            							exlcusnVO.setPerPolicyLimit(strDefaultValue);
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   exlcusnVO.setPerPolicyLimit("NC");
	                             		   
	                             	   }else{
	                             		   exlcusnVO.setPerPolicyLimit("Y");
	                             	   }
	            					}else if ("per-claim-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            						
	            						
	            						if("3".equals(strCondTypeDefault)){
	            							exlcusnVO.setPerClaimLimit(strDefaultValue);
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   
	                             		   exlcusnVO.setPerClaimLimit("NC");
	                             		   
	                             	   }else{
	                             		   
	                             		   exlcusnVO.setPerClaimLimit("Y");
	                             	   }
	            					}else if ("out-apply".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            						
	            						
	            						if("3".equals(strCondTypeDefault)){
	            							exlcusnVO.setTreatmentTypeAllowedFlag(unitConverter(strDefaultValue));
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   
	                             		   exlcusnVO.setTreatmentTypeAllowedFlag("NC");
	                             		   
	                             	   }else{
	                             		   
	                             		   exlcusnVO.setTreatmentTypeAllowedFlag("Y");
	                             	   }
	            					}else if ("ttype".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            						
	            						
	            						if("3".equals(strCondTypeDefault)){
	            							exlcusnVO.setTreatmentTypeAllowed(keyConverter1(strDefaultValue,"TREATMENT_TYPE", null));
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   
	                             		   exlcusnVO.setTreatmentTypeAllowed("NC");
	                             		   
	                             	   }else{
	                             		   
	                             		   exlcusnVO.setTreatmentTypeAllowed("Y");
	                             	   }
	            					}/*else if ("Operators1".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            						
	            						
	            						if("3".equals(strCondTypeDefault)){
	            							exlcusnVO.setMedicationsRstrctdToMemAge(strDefaultValue);
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   
	                             		   exlcusnVO.setMedicationsRstrctdToMemAge("NC");
	                             		   
	                             	   }else{
	                             		   
	                             		   exlcusnVO.setMedicationsRstrctdToMemAge("Y");
	                             	   }
	            					}*/else if ("Years".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            						
	            						
	            						if("3".equals(strCondTypeDefault)){
	            							exlcusnVO.setMedicationsRstrctdToMemAgeUnit(strDefaultValue);
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   
	                             		   exlcusnVO.setMedicationsRstrctdToMemAgeUnit("NC");
	                             		   
	                             	   }else{
	                             		   
	                             		   exlcusnVO.setMedicationsRstrctdToMemAgeUnit("Y");
	                             	   }
	            					}else if ("age".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	            						
	            						
	            						if("3".equals(strCondTypeDefault)){
	            							exlcusnVO.setMedicationsRstrctdToMemAgeVal(strDefaultValue);
	                             	   }else if("2".equals(strCondTypeDefault)){
	                             		   
	                             		   exlcusnVO.setMedicationsRstrctdToMemAgeVal("NC");
	                             		   
	                             	   }else{
	                             		   
	                             		   exlcusnVO.setMedicationsRstrctdToMemAgeVal("Y");
	                             	   }
	            					}		
	            					
	            				}else if("DNTL".equalsIgnoreCase(strBenefitID)){
	            					
	            				       if("S1".equalsIgnoreCase(strSBID)){
	                 						
	                 				        if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                         					
	                 				        	dentalVo.setPpl(strDefaultValue);
	                         					
	                         				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                         					
	                 				        	dentalVo.setCopay(strDefaultValue);
	                         					
	                         				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                         					
	                 				        	dentalVo.setDeductible(strDefaultValue);
	                         					
	                         				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                         					
	                 				        	dentalVo.setCopDedMinMax(strDefaultValue);
	                         					
	                         				}
	     	                        }
	            					
	            				}else if("OPTC".equalsIgnoreCase(strBenefitID)){
	            	
	            				       if("S1".equalsIgnoreCase(strSBID)){
	            				    	   
	            				    	   if("pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                         					
	            				    		   opticaVo.setPpl(strDefaultValue);
	                         					
	                         				}else if("copay".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                         					
	                         					opticaVo.setCopay(strDefaultValue);
	                         					
	                         				}else if("deduct".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                         					
	                         					opticaVo.setDeductible(strDefaultValue);
	                         					
	                         				}else if("MM".equalsIgnoreCase(fieldNode.valueOf("@name"))){
	                         					
	                         					opticaVo.setCopDedMinMax(strDefaultValue);
	                         					
	                         				}
	                  						
	                  				     
	      	                        }
	            					
	            				}else if("SPCB".equalsIgnoreCase(strBenefitID)){
	            					
	            			    	   if("over-per-pol-limit".equalsIgnoreCase(fieldNode.valueOf("@name")) ){  // && "C1".equals(strConID)
	                    					
	            			    		   spcbStringArray[0]=strDefaultValue;
	                    				
	                    				}else if("copay-perc".equalsIgnoreCase(fieldNode.valueOf("@name")) ){  // && "C1".equals(strConID)
	                    					
	                    					spcbStringArray[1]=strDefaultValue;
	                    			
	                    				}else if("deduct-flat".equalsIgnoreCase(fieldNode.valueOf("@name")) ){  // && "C1".equals(strConID)
	                    					
	                    					spcbStringArray[2]=strDefaultValue;
	                    					
	                    				}else if("copay-deduct-flag".equalsIgnoreCase(fieldNode.valueOf("@name")) ){  // && "C1".equals(strConID)
	                    					
	                    					spcbStringArray[3]=strDefaultValue;
	                    				
	                    				}
	            					
	            				}
	            	
	            				
	            			}
	            			
	            		
	            		}
	            		
	            		if("GNRX".equalsIgnoreCase(strBenefitID)){
	            		
	                		ttlGnrxField.removeAll(ttlAvlField);
	                		
	                		for (String remainField : ttlGnrxField) {
	                			
	                			if("days".equalsIgnoreCase(remainField) ){
	                				
	                				if("Preventative Care".equalsIgnoreCase(exlcusnVO.getGnrxBenTypes())){
	                					exlcusnVO.setExclusionPeriodUnit("NA");
	                				}else{
	                					
	                					exlcusnVO.setExclusionPeriodValue("NA");
	                				}
	                				
	                			}else if("day".equalsIgnoreCase(remainField)){
	                				
	               				 exlcusnVO.setExclusionPeriodValue("NA");
	               				
	               			}else if("period".equalsIgnoreCase(remainField)){
	            				
	           				 exlcusnVO.setExclusionPeriodValue("NA");
	           				
	           			}else if("date".equalsIgnoreCase(remainField)){
	                				
	                				 exlcusnVO.setExclusionPeriodUnit("NA");
	                				
	                			}else if("copay".equalsIgnoreCase(remainField)){
	                				
	                				exlcusnVO.setCopay("NA");
	                				
	                			}else if("copay-limit".equalsIgnoreCase(remainField)){
	                				
	                				exlcusnVO.setCopay("NA");
	                				
	                			}else if("deductible".equalsIgnoreCase(remainField)){
	                				
	                				 exlcusnVO.setDeductible("NA");
	                				
	                			}else if("mm".equalsIgnoreCase(remainField)){
	                				
	                				exlcusnVO.setCopayDeductibleMINMAX("NA");
	                				
	                			}else if("per-policy-limit".equalsIgnoreCase(remainField)){
	                		
	                				exlcusnVO.setPerPolicyLimit("NA");
	                				
	                			}else if("per-claim-limit".equalsIgnoreCase(remainField)){
	                				
	                				exlcusnVO.setPerClaimLimit("NA");
	                				
	                			}else if("out-apply".equalsIgnoreCase(remainField)){
	                				
	                				exlcusnVO.setTreatmentTypeAllowedFlag("NA");
	                				
	                			}else if("ttype".equalsIgnoreCase(remainField)){
	                				
	                				exlcusnVO.setTreatmentTypeAllowed("NA");
	                				
	                			}/*else if("Operators1".equalsIgnoreCase(remainField)){
	                				
	                				 exlcusnVO.setMedicationsRstrctdToMemAge("NA");
	                				
	                			}*/else if("Years".equalsIgnoreCase(remainField)){
	                				
	                				exlcusnVO.setMedicationsRstrctdToMemAgeUnit("NA");
	                				
	                			}else if("age".equalsIgnoreCase(remainField)){
	                				
	                				exlcusnVO.setMedicationsRstrctdToMemAgeVal("NA");
	                				
	                			}
								
							}
	            			exclusionData.add(exlcusnVO);
	            			ttlGnrxField.clear();
	            			ttlAvlField.clear();
	            		}
	            		
	            		// enhancement part started
	            		
	            		if("OPTS".equalsIgnoreCase(strBenefitID)){
	            			
	            			if("1".equals(strCondTypeDefault)){
	            				
	            				if("S1".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					outPatientVO.setOpOpOpt("Y");
	            					
	            					outPatientVO.setOpPPL("Y");
	            	             	outPatientVO.setOpChronicPPL("Y");
	            	             	outPatientVO.setOpPedPPL("Y");
	            	             	outPatientVO.setOpChronPlusPedPPL("Y");
	            	
	            					
	            				}else if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            	
	            					ipOuterData.put("AocOpts","Y");
	            
	            				}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					outPatientVO.setOpConsOpt("Y");
	            					
	            					outPatientVO.setOPConsultGeographicalLocation("Y");
	            					outPatientVO.setOPConsultCountriesCovered("Y");
	            					outPatientVO.setOPConsultEncounterType("Y");
	            					outPatientVO.setOPConsultProviderTypes("Y");
	            					outPatientVO.setOPConsultPpl("Y");
	            					outPatientVO.setOPConsultEmiratesCovered("Y");
	            					
	            					  outPatientVO.setOPGpConsultCopay("Y");
	            	    				 outPatientVO.setOPGpConsultDEDUCTABLE("Y");
	            	    				 outPatientVO.setOPGpConsultMinMax("Y");
	            	    				 outPatientVO.setOPSpConsultCopay("Y");
	            	    				 outPatientVO.setOPSpConsultDEDUCTABLE("Y");
	            	    				 outPatientVO.setOPSpConsultMinMax("Y");
	            	    				 
	            	    				 outPatientVO.setOpConsultFlwPeriod("Y");
	            	    	            	outPatientVO.setOpConsultFlwPeriodUnit("Y");
	            			
	            				}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					outPatientVO.setOpInvOpt("Y");
	            					
	            					outPatientVO.setOpOverallInvstPPL("Y");
	            	             	outPatientVO.setOpLabAndPathPPL("Y");
	            	             	outPatientVO.setOpExceptLabAndPathPPL("Y");
	            	             	
	            	             	outPatientVO.setOpInvLabProviderFacilityTypes("Y"); 
	                        		outPatientVO.setOpInvLabPreapprovalReqdYesNo("Y"); 
	                        		outPatientVO.setOpInvLabPreapprovalReqdLimit("Y");
	                        		outPatientVO.setOpInvLabPpl("Y"); 
	                        		outPatientVO.setOpInvLabNoOfSess("Y"); 
	                        		outPatientVO.setOpInvLabCopay("Y"); 
	                        		outPatientVO.setOpInvLabDeductable("Y");
	                        		outPatientVO.setOpInvLabCopdedMINMAX("Y");
	                        		
	                        		outPatientVO.setOpInvPatProviderFacilityTypes("Y"); 
	                         		outPatientVO.setOpInvPatPreapprovalReqdYesNo("Y"); 
	                         		outPatientVO.setOpInvPatPreapprovalReqdLimit("Y");
	                         		outPatientVO.setOpInvPatPpl("Y");
	                         		outPatientVO.setOpInvPatNoOfSess("Y");
	                         		outPatientVO.setOpInvPatCopay("Y"); 
	                         		outPatientVO.setOpInvPatDeductable("Y");
	                         		outPatientVO.setOpInvPatCopdedMINMAX("Y");
	                         		
	                         		outPatientVO.setOpInvUltraProviderFacilityTypes("Y"); 
	                         		outPatientVO.setOpInvUltraPreapprovalReqdYesNo("Y"); 
	                         		outPatientVO.setOpInvUltraPreapprovalReqdLimit("Y");
	                         		outPatientVO.setOpInvUltraPpl("Y"); 
	                         		outPatientVO.setOpInvUltraNoOfSess("Y");
	                         		outPatientVO.setOpInvUltraCopay("Y"); 
	                         		outPatientVO.setOpInvUltraDeductable("Y");
	                         		outPatientVO.setOpInvUltraCopdedMINMAX("Y");
	                         		
	                         		outPatientVO.setOpInvCtScanProviderFacilityTypes("Y"); 
	                         		outPatientVO.setOpInvCtScanPreapprovalReqdYesNo("Y"); 
	                         		outPatientVO.setOpInvCtScanPreapprovalReqdLimit("Y");
	                         		outPatientVO.setOpInvCtScanPpl("Y"); 
	                         		outPatientVO.setOpInvCtScanNoOfSess("Y");
	                         		outPatientVO.setOpInvCtScanCopay("Y"); 
	                         		outPatientVO.setOpInvCtScanDeductable("Y");
	                         		outPatientVO.setOpInvCtScanCopdedMINMAX("Y");
	                         		
	                         		outPatientVO.setOpInvMriProviderFacilityTypes("Y"); 
	                         		outPatientVO.setOpInvMriPreapprovalReqdYesNo("Y"); 
	                         		outPatientVO.setOpInvMriPreapprovalReqdLimit("Y");
	                         		outPatientVO.setOpInvMriPpl("Y");
	                         		outPatientVO.setOpInvMriNoOfSess("Y");
	                         		outPatientVO.setOpInvMriCopay("Y"); 
	                         		outPatientVO.setOpInvMriDeductable("Y");
	                         		outPatientVO.setOpInvMriCopdedMINMAX("Y");
	                         		
	                         		outPatientVO.setOpInvDiagAndTherapProviderTypes("Y"); 
	                         		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdYesNo("Y"); 
	                         		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdLimit("Y");
	                         		outPatientVO.setOpInvDiagAndTherapPpl("Y");
	                         		outPatientVO.setOpInvDiagAndTherapNoOfSess("Y");
	                         		outPatientVO.setOpInvDiagAndTherapCopay("Y"); 
	                         		outPatientVO.setOpInvDiagAndTherapDeductable("Y");
	                         		outPatientVO.setOpInvDiagAndTherapCopdedMINMAX("Y");
	            			
	            				}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					outPatientVO.setOpPhyOpt("Y");
	            					
	            				  	outPatientVO.setOpPhysioProviderFacilityTypes("Y");
	            	            	outPatientVO.setOpPhysioPreapprovalReqdYesNo("Y");
	            	            	outPatientVO.setOpPhysioPreapprovalLimit("Y");
	            	            	outPatientVO.setOpPhysioPpl("Y");
	            	            	outPatientVO.setOpPhysioNOOfSess("Y");
	            	            	outPatientVO.setOpPhysioPerSessLimit("Y");
	            	            	outPatientVO.setOpPhysioCopay("Y");
	            	            	outPatientVO.setOpPhysioOvrDeductable("Y");
	            	            	outPatientVO.setOpPhysioCopdedMINMAX("Y");
	            			
	            				}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					outPatientVO.setOpPhrmOpt("Y");
	            					
	            					outPatientVO.setOpPharmactlProviderFacility("Y");
	            	            	outPatientVO.setOpPharmactlPreapprovalReqdYesNo("Y");
	            	            	outPatientVO.setOpPharmactlPreapprovalLimit("Y");
	            	            	outPatientVO.setOpPharmactlPpl("Y");
	            	            	outPatientVO.setOpPharmactlCopay("Y");
	            	            	outPatientVO.setOpPharmactlDeductable("Y");
	            	            	outPatientVO.setOpPharmactlCopdedMINMAX("Y");
	            			
	            				} 
	            				
	            				
	            			}else if("2".equals(strCondTypeDefault)){
	            				
                                  if("S1".equalsIgnoreCase(sbNode.valueOf("@id"))){
                                	  
                                	  outPatientVO.setOpOpOpt("NC");
	            					
	            					outPatientVO.setOpPPL("NC");
	            	             	outPatientVO.setOpChronicPPL("NC");
	            	             	outPatientVO.setOpPedPPL("NC");
	            	             	outPatientVO.setOpChronPlusPedPPL("NC");
	            	          
	            					
	            				}else if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					ipOuterData.put("AocOpts","NC");
	            			
	            				}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					outPatientVO.setOpConsOpt("NC");
	            					
	            					outPatientVO.setOPConsultGeographicalLocation("NC");
	            					outPatientVO.setOPConsultCountriesCovered("NC");
	            					outPatientVO.setOPConsultEncounterType("NC");
	            					outPatientVO.setOPConsultProviderTypes("NC");
	            					outPatientVO.setOPConsultPpl("NC");
	            					outPatientVO.setOPConsultEmiratesCovered("NC");
	            					
	            					  outPatientVO.setOPGpConsultCopay("NC");
	            	    				 outPatientVO.setOPGpConsultDEDUCTABLE("NC");
	            	    				 outPatientVO.setOPGpConsultMinMax("NC");
	            	    				 outPatientVO.setOPSpConsultCopay("NC");
	            	    				 outPatientVO.setOPSpConsultDEDUCTABLE("NC");
	            	    				 outPatientVO.setOPSpConsultMinMax("NC");
	            	    				 
	            	    				 outPatientVO.setOpConsultFlwPeriod("NC");
	            	    	            	outPatientVO.setOpConsultFlwPeriodUnit("NC");
	            			
	            				}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					outPatientVO.setOpInvOpt("NC");
	            					
	            					outPatientVO.setOpOverallInvstPPL("NC");
	            	             	outPatientVO.setOpLabAndPathPPL("NC");
	            	             	outPatientVO.setOpExceptLabAndPathPPL("NC");
	            	             	
	              	             	outPatientVO.setOpInvLabProviderFacilityTypes("NC"); 
	                        		outPatientVO.setOpInvLabPreapprovalReqdYesNo("NC"); 
	                        		outPatientVO.setOpInvLabPreapprovalReqdLimit("NC");
	                        		outPatientVO.setOpInvLabPpl("NC"); 
	                        		outPatientVO.setOpInvLabNoOfSess("NC"); 
	                        		outPatientVO.setOpInvLabCopay("NC"); 
	                        		outPatientVO.setOpInvLabDeductable("NC");
	                        		outPatientVO.setOpInvLabCopdedMINMAX("NC");
	                        		
	                        		outPatientVO.setOpInvPatProviderFacilityTypes("NC"); 
	                         		outPatientVO.setOpInvPatPreapprovalReqdYesNo("NC"); 
	                         		outPatientVO.setOpInvPatPreapprovalReqdLimit("NC");
	                         		outPatientVO.setOpInvPatPpl("NC");
	                         		outPatientVO.setOpInvPatNoOfSess("NC");
	                         		outPatientVO.setOpInvPatCopay("NC"); 
	                         		outPatientVO.setOpInvPatDeductable("NC");
	                         		outPatientVO.setOpInvPatCopdedMINMAX("NC");
	                         		
	                         		outPatientVO.setOpInvUltraProviderFacilityTypes("NC"); 
	                         		outPatientVO.setOpInvUltraPreapprovalReqdYesNo("NC"); 
	                         		outPatientVO.setOpInvUltraPreapprovalReqdLimit("NC");
	                         		outPatientVO.setOpInvUltraPpl("NC"); 
	                         		outPatientVO.setOpInvUltraNoOfSess("NC");
	                         		outPatientVO.setOpInvUltraCopay("NC"); 
	                         		outPatientVO.setOpInvUltraDeductable("NC");
	                         		outPatientVO.setOpInvUltraCopdedMINMAX("NC");
	                         		
	                         		outPatientVO.setOpInvCtScanProviderFacilityTypes("NC"); 
	                         		outPatientVO.setOpInvCtScanPreapprovalReqdYesNo("NC"); 
	                         		outPatientVO.setOpInvCtScanPreapprovalReqdLimit("NC");
	                         		outPatientVO.setOpInvCtScanPpl("NC"); 
	                         		outPatientVO.setOpInvCtScanNoOfSess("NC");
	                         		outPatientVO.setOpInvCtScanCopay("NC"); 
	                         		outPatientVO.setOpInvCtScanDeductable("NC");
	                         		outPatientVO.setOpInvCtScanCopdedMINMAX("NC");
	                         		
	                         		outPatientVO.setOpInvMriProviderFacilityTypes("NC"); 
	                         		outPatientVO.setOpInvMriPreapprovalReqdYesNo("NC"); 
	                         		outPatientVO.setOpInvMriPreapprovalReqdLimit("NC");
	                         		outPatientVO.setOpInvMriPpl("NC");
	                         		outPatientVO.setOpInvMriNoOfSess("NC");
	                         		outPatientVO.setOpInvMriCopay("NC"); 
	                         		outPatientVO.setOpInvMriDeductable("NC");
	                         		outPatientVO.setOpInvMriCopdedMINMAX("NC");
	                         		
	                         		outPatientVO.setOpInvDiagAndTherapProviderTypes("NC"); 
	                         		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdYesNo("NC"); 
	                         		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdLimit("NC");
	                         		outPatientVO.setOpInvDiagAndTherapPpl("NC");
	                         		outPatientVO.setOpInvDiagAndTherapNoOfSess("NC");
	                         		outPatientVO.setOpInvDiagAndTherapCopay("NC"); 
	                         		outPatientVO.setOpInvDiagAndTherapDeductable("NC");
	                         		outPatientVO.setOpInvDiagAndTherapCopdedMINMAX("NC");
	            			
	            				}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					outPatientVO.setOpPhyOpt("NC");
	            					
	            				  	outPatientVO.setOpPhysioProviderFacilityTypes("NC");
	            	            	outPatientVO.setOpPhysioPreapprovalReqdYesNo("NC");
	            	            	outPatientVO.setOpPhysioPreapprovalLimit("NC");
	            	            	outPatientVO.setOpPhysioPpl("NC");
	            	            	outPatientVO.setOpPhysioNOOfSess("NC");
	            	            	outPatientVO.setOpPhysioPerSessLimit("NC");
	            	            	outPatientVO.setOpPhysioCopay("NC");
	            	            	outPatientVO.setOpPhysioOvrDeductable("NC");
	            	            	outPatientVO.setOpPhysioCopdedMINMAX("NC");
	            			
	            				}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					outPatientVO.setOpPhrmOpt("NC");
	            					
	            					outPatientVO.setOpPharmactlProviderFacility("NC");
	            	            	outPatientVO.setOpPharmactlPreapprovalReqdYesNo("NC");
	            	            	outPatientVO.setOpPharmactlPreapprovalLimit("NC");
	            	            	outPatientVO.setOpPharmactlPpl("NC");
	            	            	outPatientVO.setOpPharmactlCopay("NC");
	            	            	outPatientVO.setOpPharmactlDeductable("NC");
	            	            	outPatientVO.setOpPharmactlCopdedMINMAX("NC");
	            			
	            				} 
	            				
	            			}else{
	            				
	            				
	                            if("S1".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	                            	opOuterData.put("OpOpt","PC");
	                            	outPatientVO.setOpPPL(opOuterData.get("OP_PPL"));
	                             	outPatientVO.setOpChronicPPL(opOuterData.get("OP_CHRONIC_PPL"));
	                             	outPatientVO.setOpPedPPL(opOuterData.get("OP_PED_PPL"));
	                             	outPatientVO.setOpChronPlusPedPPL(opOuterData.get("OP_CHRON_PLUS_PED_PPL"));
	         	            	          
	         	            					
	         	            				}else if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){

	         	            					ipOuterData.put("AocOpts","PC");
	         	            			
	         	            				}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					outPatientVO.setOpConsOpt("PC");
	         	            			
	         	            				}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					opOuterData.put("InvOpt","PC");
	         	            					
	         	            					outPatientVO.setOpOverallInvstPPL(opOuterData.get("OP_OVERALL_INVST_PPL"));
	         	            			     	outPatientVO.setOpLabAndPathPPL(opOuterData.get("OP_LAB_AND_PATH_PPL"));
	         	            			     	outPatientVO.setOpExceptLabAndPathPPL(opOuterData.get("OP_EXCEPT_LAB_AND_PATH_PPL"));
	         	            			
	         	            				}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					outPatientVO.setOpPhyOpt("PC");
	         	            			
	         	            				}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					outPatientVO.setOpPhrmOpt("PC");
	         	            				
	         	            			
	         	            				}
	            				
	            				
	            				
	            			}
	            			
	            		}
	            		
	            		if("IPT".equalsIgnoreCase(strBenefitID)){
	            			
	            			if("1".equals(strCondTypeDefault)){
	            				
	            				if("S1".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					ipOuterData.put("ipIpDcCmnRulOpt","Y");
	            					
	            					inPatientVO.setIpDaycPPL("Y");
	            			    	inPatientVO.setIpDaycChronicPPL("Y");
	            			    	inPatientVO.setIpDaycPedPPL("Y");
	            			    	inPatientVO.setIpDaycChronPlusPedPPL("Y");
	            			      	inPatientVO.setIpDaycOneMedPPL("Y");
	            			    	
	            	
	            					
	            				}else if("S1-1".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		
	            					ipOuterData.put("AocIPT","Y");
	            				
	            				}else if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					inPatientVO.setIpIpOpt("Y");
	            					inPatientVO.setIpPPL("Y");
	            					
	            					
	            			
	            				}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpRmBrdExpOpt("Y");
	            					
	            					inPatientVO.setIpRmAndBrdRmTypes("Y");
	            			    	inPatientVO.setIpRmAndBrdPPL("Y");
	            			    	inPatientVO.setIpRmAndBrdCopay("Y");
	            			    	inPatientVO.setIpRmAndBrdDEDUCTABLE("Y");
	            			    	inPatientVO.setIpRmAndBrdCopdedMINMAX("Y");
	            			
	            				}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpIcuExpOpt("Y");
	            					
	            					inPatientVO.setIpIcuExpPPL("Y");
	            			      	inPatientVO.setIpIcuExpCopay("Y");
	            			    	inPatientVO.setIpIcuExpDEDUCTABLE("Y");
	            			    	inPatientVO.setIpIcuExpCopdedMINMAX("Y");
	            		
	            			
	            				}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpConsOpt("Y");
	            					
	            			       	inPatientVO.setIpConsultGeoLocCov("Y");
	            	            	inPatientVO.setIpConsultConCov("Y");
	            	            	inPatientVO.setIpConsultEncType("Y");
	            	            	inPatientVO.setIpConsultProTypes("Y");
	            	            	inPatientVO.setIpConsultPPL("Y");
	            	            	inPatientVO.setIpConsultEmrCov("Y");
	            	            	
	            	            	   inPatientVO.setIpGpConsultCopay("Y");
	            	    				 inPatientVO.setIpGpConsultDEDUCTABLE("Y");
	            	    				 inPatientVO.setIpGpConsultMinMax("Y");
	            	    				 inPatientVO.setIpSpConsultCopay("Y");
	            	    				 inPatientVO.setIpSpConsultDEDUCTABLE("Y");
	            	    				 inPatientVO.setIpSpConsultMinMax("Y");
	            	    				 
	            	    				  inPatientVO.setIpConsultFlwPeriod("Y");
	            	    		          inPatientVO.setIpConsultFlwPeriodUnit("Y");
	            			
	            			
	            				}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpSrgAnsthOpt("Y");
	            					
	            				   	inPatientVO.setIpSrgAndAnstSrgPPL("Y");
	            			    	inPatientVO.setIpSrgAndAnstSrgCopay("Y");
	            			    	inPatientVO.setIpSrgAndAnstSrgDEDUCTABLE("Y");
	            			    	inPatientVO.setIpSrgAndAnstSrgCopdedMINMAX("Y");
	            			    	inPatientVO.setIpSrgAndAnstAnstPPL("Y");
	            			      	inPatientVO.setIpSrgAndAnstAnstCopay("Y");
	            			    	inPatientVO.setIpSrgAndAnstAnstDEDUCTABLE("Y");
	            			    	inPatientVO.setIpSrgAndAnstAnstCopdedMINMAX("Y");
	            			
	            				}else if("S7".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpCompChrgOpt("Y");
	            					
	            	             	inPatientVO.setIpCompnChargGeoLoc("Y");
	            	            	inPatientVO.setIpCompnChargCon("Y");
	            	            	inPatientVO.setIpCompnChargEmr("Y");
	            	            	inPatientVO.setIpCompnChargProTypes("Y");
	            	            	inPatientVO.setIpCompnChargProFaclityTypes("Y");
	            	            	inPatientVO.setIpCompnChargPreaprvReqdYesNo("Y");
	            	            	inPatientVO.setIpCompnChargPAL("Y");
	            	            	inPatientVO.setIpCompnChargFrmAge("Y");
	            	            	inPatientVO.setIpCompnChargToAge("Y");
	            	            	inPatientVO.setIpCompnChargFrmToAgeUt("Y");
	            	            	inPatientVO.setIpCompnChargPPL("Y");
	            	            	inPatientVO.setIpCompnChargNoDaysAlwd("Y");
	            	            	inPatientVO.setIpCompnChargMxLmtAlwdPerDay("Y");
	            	            	inPatientVO.setIpCompnChargCopay("Y");
	            	            	inPatientVO.setIpCompnChargDEDUCTABLE("Y");
	            	            	inPatientVO.setIpCompnChargCopdedMINMAX("Y");
	            					
	            			
	            			
	            				} else if("S8".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpInvOpt("Y");
	            					
	            					inPatientVO.setIpOverallInvstPPL("Y");
	            			    	inPatientVO.setIpLabAndPathPPL("Y");
	            			    	inPatientVO.setIpExceptLabAndPathPPL("Y");
	            			    	
	            			    	inPatientVO.setIpInvLabProviderTypes("Y"); 
	                        		inPatientVO.setIpInvLabPreapprovalReqdYesNo("Y"); 
	                        		inPatientVO.setIpInvLabPreapprovalReqdLimit("Y");
	                        		inPatientVO.setIpInvLabPpl("Y"); 
	                        		inPatientVO.setIpInvLabNoOfSess("Y");
	                        		inPatientVO.setIpInvLabCopay("Y"); 
	                        		inPatientVO.setIpInvLabDeductable("Y");
	                        		inPatientVO.setIpInvLabCopdedMINMAX("Y");
	                        		
	                        		inPatientVO.setIpInvPatProviderTypes("Y"); 
	                         		inPatientVO.setIpInvPatPreapprovalReqdYesNo("Y"); 
	                         		inPatientVO.setIpInvPatPreapprovalReqdLimit("Y");
	                         		inPatientVO.setIpInvPatPpl("Y");
	                         		inPatientVO.setIpInvPatNoOfSess("Y");
	                         		inPatientVO.setIpInvPatCopay("Y"); 
	                         		inPatientVO.setIpInvPatDeductable("Y");
	                         		inPatientVO.setIpInvPatCopdedMINMAX("Y");
	                         		
	                         		inPatientVO.setIpInvUltraProviderFacilityTypes("Y"); 
	                         		inPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y"); 
	                         		inPatientVO.setIpInvUltraPreapprovalReqdLimit("Y");
	                         		inPatientVO.setIpInvUltraPpl("Y"); 
	                         		inPatientVO.setIpInvUltraNoOfSess("Y");
	                         		inPatientVO.setIpInvUltraCopay("Y"); 
	                         		inPatientVO.setIpInvUltraDeductable("Y");
	                         		inPatientVO.setIpInvUltraCopdedMINMAX("Y");
	                         		
	                         		inPatientVO.setIpInvCtScanProviderFacilityTypes("Y"); 
	                         		inPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y"); 
	                         		inPatientVO.setIpInvCtScanPreapprovalReqdLimit("Y");
	                         		inPatientVO.setIpInvCtScanPpl("Y"); 
	                         		inPatientVO.setIpInvCtScanNoOfSess("Y");
	                         		inPatientVO.setIpInvCtScanCopay("Y"); 
	                         		inPatientVO.setIpInvCtScanDeductable("Y");
	                         		inPatientVO.setIpInvCtScanCopdedMINMAX("Y");
	                         		
	                         		inPatientVO.setIpInvMriProviderFacilityTypes("Y"); 
	                         		inPatientVO.setIpInvMriPreapprovalReqdYesNo("Y"); 
	                         		inPatientVO.setIpInvMriPreapprovalReqdLimit("Y");
	                         		inPatientVO.setIpInvMriPpl("Y"); 
	                         		inPatientVO.setIpInvMriNoOfSess("Y");
	                         		inPatientVO.setIpInvMriCopay("Y"); 
	                         		inPatientVO.setIpInvMriDeductable("Y");
	                         		inPatientVO.setIpInvMriCopdedMINMAX("Y");
	                         		
	                         		inPatientVO.setIpInvDiagAndTherapProviderTypes("Y"); 
	                         		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y"); 
	                         		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit("Y");
	                         		inPatientVO.setIpInvDiagAndTherapPpl("Y"); 
	                         		inPatientVO.setIpInvDiagAndTherapNoOfSess("Y");
	                         		inPatientVO.setIpInvDiagAndTherapCopay("Y"); 
	                         		inPatientVO.setIpInvDiagAndTherapDeductable("Y");
	                         		inPatientVO.setIpInvDiagAndTherapCopdedMINMAX("Y");
	            			
	            				} else if("S9".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpMedChemoOpt("Y");
	            					
	            			    	inPatientVO.setIpMedicationsPPL("Y");
	            			    	inPatientVO.setIpMedicationsCopay("Y");
	            			      	inPatientVO.setIpMedicationsDEDUCTABLE("Y");
	            			    	inPatientVO.setIpMedicationsCopdedMINMAX("Y");
	            			    	inPatientVO.setIpIVFFluidsPPL("Y");
	            			    	inPatientVO.setIpIVFFluidsCopay("Y");
	            			    	inPatientVO.setIpIVFFluidsDEDUCTABLE("Y");
	            			    	inPatientVO.setIpIVFFluidsCopdedMINMAX("Y");
	            			    	inPatientVO.setIpBldTrnsfusnPPL("Y");
	            			    	inPatientVO.setIpBldTrnsfusnCopay("Y");
	            			      	inPatientVO.setIpBldTrnsfusnDEDUCTABLE("Y");
	            			    	inPatientVO.setIpBldTrnsfusnCopdedMINMAX("Y");
	            			    	inPatientVO.setIpAnalegicsPPL("Y");
	            			    	inPatientVO.setIpAnalegicsCopay("Y");
	            			    	inPatientVO.setIpAnalegicsDEDUCTABLE("Y");
	            			    	inPatientVO.setIpAnalegicsCopdedMINMAX("Y");
	            			    	inPatientVO.setIpSrgImplPPL("Y");
	            			    	inPatientVO.setIpSrgImplCopay("Y");
	            			      	inPatientVO.setIpSrgImplDEDUCTABLE("Y");
	            			    	inPatientVO.setIpSrgImplCopdedMINMAX("Y");
	            			    	inPatientVO.setIpChemoPPL("Y");
	            			    	inPatientVO.setIpChemoCopay("Y");
	            			    	inPatientVO.setIpChemoDEDUCTABLE("Y");
	            			    	inPatientVO.setIpChemoCopdedMINMAX("Y");
	            			
	            			
	            				} else if("S10".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpDispConsOpt("Y");
	            					
	            			    	inPatientVO.setIpConsmAndDspsPPL("Y");
	            			    	inPatientVO.setIpConsmAndDspsCopay("Y");
	            			      	inPatientVO.setIpConsmAndDspsDEDUCTABLE("Y");
	            			    	inPatientVO.setIpConsmAndDspsCopdedMINMAX("Y");
	            			    	inPatientVO.setIpConsmAndDspsExPrdVal("Y");
	            			    	inPatientVO.setIpConsmAndDspsExPrdUnt("Y");
	            			
	            				} else if("S11".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpPhyOpt("Y");
	            					
	            					inPatientVO.setIpPhysioProviderFacilityTypes("Y");
	            	        		inPatientVO.setIpPhysioPreapprovalReqdYesNo("Y");
	            	        		inPatientVO.setIpPhysioPreapprovalLimit("Y");
	            	        		inPatientVO.setIpPhysioPpl("Y");
	            	        		inPatientVO.setIpPhysioNOOfSess("Y");
	            	        		inPatientVO.setIpPhysioPerSessLimit("Y");
	            	        		inPatientVO.setIpPhysioCopay("Y");
	            	        		inPatientVO.setIpPhysioOvrDeductable("Y");
	            	        		inPatientVO.setIpPhysioCopdedMINMAX("Y");
	            			
	            				} else if("S12".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpAmbOpt("Y");
	            					
	            			    	inPatientVO.setIpAmblncGeoLoc("Y");
	            	             	inPatientVO.setIpAmblncCon("Y");
	            	             	inPatientVO.setIpAmblncEmr("Y");
	            	             	inPatientVO.setIpAmblncProTypes("Y");
	            	             	inPatientVO.setIpAmblncProFaclityTypes("Y");
	            	             	inPatientVO.setIpAmblncEmrNonemr("Y");
	            	             	inPatientVO.setIpAmblncPreaprvReqdYesNo("Y");
	            	             	inPatientVO.setIpAmblncPAL("Y");
	            	             	inPatientVO.setIpAmblncPPL("Y");
	            	             	inPatientVO.setIpAmblncCopay("Y");
	            	             	inPatientVO.setIpAmblncDeductable("Y");
	            	             	inPatientVO.setIpAmblncCopdedMINMAX("Y");
	            			
	            				} 
	            				
	            				
	            			}else if("2".equals(strCondTypeDefault)){
	            				
                                  if("S1".equalsIgnoreCase(sbNode.valueOf("@id"))){
                                	  
                                		ipOuterData.put("ipIpDcCmnRulOpt","NC");
    	            					
    	            					inPatientVO.setIpDaycPPL("NC");
    	            			    	inPatientVO.setIpDaycChronicPPL("NC");
    	            			    	inPatientVO.setIpDaycPedPPL("NC");
    	            			    	inPatientVO.setIpDaycChronPlusPedPPL("NC");
    	            			      	inPatientVO.setIpDaycOneMedPPL("NC");
	            	          
	            					
	            				}else if("S1-1".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					ipOuterData.put("AocIPT","NC");
	            			
	            				}else if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpIpOpt("NC");
	            					inPatientVO.setIpPPL("NC");
	            			
	            				}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	                                inPatientVO.setIpRmBrdExpOpt("NC");
	            					
	            					inPatientVO.setIpRmAndBrdRmTypes("NC");
	            			    	inPatientVO.setIpRmAndBrdPPL("NC");
	            			    	inPatientVO.setIpRmAndBrdCopay("NC");
	            			    	inPatientVO.setIpRmAndBrdDEDUCTABLE("NC");
	            			    	inPatientVO.setIpRmAndBrdCopdedMINMAX("NC");
	            			
	            				}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
		                            inPatientVO.setIpIcuExpOpt("NC");
	            					
	            					inPatientVO.setIpIcuExpPPL("NC");
	            			      	inPatientVO.setIpIcuExpCopay("NC");
	            			    	inPatientVO.setIpIcuExpDEDUCTABLE("NC");
	            			    	inPatientVO.setIpIcuExpCopdedMINMAX("NC");
	            			
	            				}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
		                        	inPatientVO.setIpConsOpt("NC");
	            					
	            			       	inPatientVO.setIpConsultGeoLocCov("NC");
	            	            	inPatientVO.setIpConsultConCov("NC");
	            	            	inPatientVO.setIpConsultEncType("NC");
	            	            	inPatientVO.setIpConsultProTypes("NC");
	            	            	inPatientVO.setIpConsultPPL("NC");
	            	            	inPatientVO.setIpConsultEmrCov("NC");
	            	            	
	            	            	   inPatientVO.setIpGpConsultCopay("NC");
	            	    				 inPatientVO.setIpGpConsultDEDUCTABLE("NC");
	            	    				 inPatientVO.setIpGpConsultMinMax("NC");
	            	    				 inPatientVO.setIpSpConsultCopay("NC");
	            	    				 inPatientVO.setIpSpConsultDEDUCTABLE("NC");
	            	    				 inPatientVO.setIpSpConsultMinMax("NC");
	            	    				 
	            	    				  inPatientVO.setIpConsultFlwPeriod("NC");
	            	    		          inPatientVO.setIpConsultFlwPeriodUnit("NC");
	            			
	            				}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	                                inPatientVO.setIpSrgAnsthOpt("NC");
	            					
	            				   	inPatientVO.setIpSrgAndAnstSrgPPL("NC");
	            			    	inPatientVO.setIpSrgAndAnstSrgCopay("NC");
	            			    	inPatientVO.setIpSrgAndAnstSrgDEDUCTABLE("NC");
	            			    	inPatientVO.setIpSrgAndAnstSrgCopdedMINMAX("NC");
	            			    	inPatientVO.setIpSrgAndAnstAnstPPL("NC");
	            			      	inPatientVO.setIpSrgAndAnstAnstCopay("NC");
	            			    	inPatientVO.setIpSrgAndAnstAnstDEDUCTABLE("NC");
	            			    	inPatientVO.setIpSrgAndAnstAnstCopdedMINMAX("NC");
	            			
	            				} else if("S7".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
					                inPatientVO.setIpCompChrgOpt("NC");
	            					
	            	             	inPatientVO.setIpCompnChargGeoLoc("NC");
	            	            	inPatientVO.setIpCompnChargCon("NC");
	            	            	inPatientVO.setIpCompnChargEmr("NC");
	            	            	inPatientVO.setIpCompnChargProTypes("NC");
	            	            	inPatientVO.setIpCompnChargProFaclityTypes("NC");
	            	            	inPatientVO.setIpCompnChargPreaprvReqdYesNo("NC");
	            	            	inPatientVO.setIpCompnChargPAL("NC");
	            	            	inPatientVO.setIpCompnChargFrmAge("NC");
	            	            	inPatientVO.setIpCompnChargToAge("NC");
	            	            	inPatientVO.setIpCompnChargFrmToAgeUt("NC");
	            	            	inPatientVO.setIpCompnChargPPL("NC");
	            	            	inPatientVO.setIpCompnChargNoDaysAlwd("NC");
	            	            	inPatientVO.setIpCompnChargMxLmtAlwdPerDay("NC");
	            	            	inPatientVO.setIpCompnChargCopay("NC");
	            	            	inPatientVO.setIpCompnChargDEDUCTABLE("NC");
	            	            	inPatientVO.setIpCompnChargCopdedMINMAX("NC");
	            			
	            				}else if("S8".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					inPatientVO.setIpInvOpt("NC");
	            					
	            					inPatientVO.setIpOverallInvstPPL("NC");
	            			    	inPatientVO.setIpLabAndPathPPL("NC");
	            			    	inPatientVO.setIpExceptLabAndPathPPL("NC");
	            			    	
	            			    	inPatientVO.setIpInvLabProviderTypes("NC"); 
	                        		inPatientVO.setIpInvLabPreapprovalReqdYesNo("NC"); 
	                        		inPatientVO.setIpInvLabPreapprovalReqdLimit("NC");
	                        		inPatientVO.setIpInvLabPpl("NC"); 
	                        		inPatientVO.setIpInvLabNoOfSess("NC");
	                        		inPatientVO.setIpInvLabCopay("NC"); 
	                        		inPatientVO.setIpInvLabDeductable("NC");
	                        		inPatientVO.setIpInvLabCopdedMINMAX("NC");
	                        		
	                        		inPatientVO.setIpInvPatProviderTypes("NC"); 
	                         		inPatientVO.setIpInvPatPreapprovalReqdYesNo("NC"); 
	                         		inPatientVO.setIpInvPatPreapprovalReqdLimit("NC");
	                         		inPatientVO.setIpInvPatPpl("NC");
	                         		inPatientVO.setIpInvPatNoOfSess("NC");
	                         		inPatientVO.setIpInvPatCopay("NC"); 
	                         		inPatientVO.setIpInvPatDeductable("NC");
	                         		inPatientVO.setIpInvPatCopdedMINMAX("NC");
	                         		
	                         		inPatientVO.setIpInvUltraProviderFacilityTypes("NC"); 
	                         		inPatientVO.setIpInvUltraPreapprovalReqdYesNo("NC"); 
	                         		inPatientVO.setIpInvUltraPreapprovalReqdLimit("NC");
	                         		inPatientVO.setIpInvUltraPpl("NC"); 
	                         		inPatientVO.setIpInvUltraNoOfSess("NC");
	                         		inPatientVO.setIpInvUltraCopay("NC"); 
	                         		inPatientVO.setIpInvUltraDeductable("NC");
	                         		inPatientVO.setIpInvUltraCopdedMINMAX("NC");
	                         		
	                         		inPatientVO.setIpInvCtScanProviderFacilityTypes("NC"); 
	                         		inPatientVO.setIpInvCtScanPreapprovalReqdYesNo("NC"); 
	                         		inPatientVO.setIpInvCtScanPreapprovalReqdLimit("NC");
	                         		inPatientVO.setIpInvCtScanPpl("NC"); 
	                         		inPatientVO.setIpInvCtScanNoOfSess("NC");
	                         		inPatientVO.setIpInvCtScanCopay("NC"); 
	                         		inPatientVO.setIpInvCtScanDeductable("NC");
	                         		inPatientVO.setIpInvCtScanCopdedMINMAX("NC");
	                         		
	                         		inPatientVO.setIpInvMriProviderFacilityTypes("NC"); 
	                         		inPatientVO.setIpInvMriPreapprovalReqdYesNo("NC"); 
	                         		inPatientVO.setIpInvMriPreapprovalReqdLimit("NC");
	                         		inPatientVO.setIpInvMriPpl("NC"); 
	                         		inPatientVO.setIpInvMriNoOfSess("NC");
	                         		inPatientVO.setIpInvMriCopay("NC"); 
	                         		inPatientVO.setIpInvMriDeductable("NC");
	                         		inPatientVO.setIpInvMriCopdedMINMAX("NC");
	                         		
	                         		inPatientVO.setIpInvDiagAndTherapProviderTypes("NC"); 
	                         		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("NC"); 
	                         		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit("NC");
	                         		inPatientVO.setIpInvDiagAndTherapPpl("NC"); 
	                         		inPatientVO.setIpInvDiagAndTherapNoOfSess("NC");
	                         		inPatientVO.setIpInvDiagAndTherapCopay("NC"); 
	                         		inPatientVO.setIpInvDiagAndTherapDeductable("NC");
	                         		inPatientVO.setIpInvDiagAndTherapCopdedMINMAX("NC");
	            			
	            				}else if("S9".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
		                        	inPatientVO.setIpMedChemoOpt("NC");
	            					
	            			    	inPatientVO.setIpMedicationsPPL("NC");
	            			    	inPatientVO.setIpMedicationsCopay("NC");
	            			      	inPatientVO.setIpMedicationsDEDUCTABLE("NC");
	            			    	inPatientVO.setIpMedicationsCopdedMINMAX("NC");
	            			    	inPatientVO.setIpIVFFluidsPPL("NC");
	            			    	inPatientVO.setIpIVFFluidsCopay("NC");
	            			    	inPatientVO.setIpIVFFluidsDEDUCTABLE("NC");
	            			    	inPatientVO.setIpIVFFluidsCopdedMINMAX("NC");
	            			    	inPatientVO.setIpBldTrnsfusnPPL("NC");
	            			    	inPatientVO.setIpBldTrnsfusnCopay("NC");
	            			      	inPatientVO.setIpBldTrnsfusnDEDUCTABLE("NC");
	            			    	inPatientVO.setIpBldTrnsfusnCopdedMINMAX("NC");
	            			    	inPatientVO.setIpAnalegicsPPL("NC");
	            			    	inPatientVO.setIpAnalegicsCopay("NC");
	            			    	inPatientVO.setIpAnalegicsDEDUCTABLE("NC");
	            			    	inPatientVO.setIpAnalegicsCopdedMINMAX("NC");
	            			    	inPatientVO.setIpSrgImplPPL("NC");
	            			    	inPatientVO.setIpSrgImplCopay("NC");
	            			      	inPatientVO.setIpSrgImplDEDUCTABLE("NC");
	            			    	inPatientVO.setIpSrgImplCopdedMINMAX("NC");
	            			    	inPatientVO.setIpChemoPPL("NC");
	            			    	inPatientVO.setIpChemoCopay("NC");
	            			    	inPatientVO.setIpChemoDEDUCTABLE("NC");
	            			    	inPatientVO.setIpChemoCopdedMINMAX("NC");
	            			
	            				}else if("S10".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	                                inPatientVO.setIpDispConsOpt("NC");
	            					
	            			    	inPatientVO.setIpConsmAndDspsPPL("NC");
	            			    	inPatientVO.setIpConsmAndDspsCopay("NC");
	            			      	inPatientVO.setIpConsmAndDspsDEDUCTABLE("NC");
	            			    	inPatientVO.setIpConsmAndDspsCopdedMINMAX("NC");
	            			    	inPatientVO.setIpConsmAndDspsExPrdVal("NC");
	            			    	inPatientVO.setIpConsmAndDspsExPrdUnt("NC");
	            			
	            				}else if("S11".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
				                    inPatientVO.setIpPhyOpt("NC");
	            					
	            					inPatientVO.setIpPhysioProviderFacilityTypes("NC");
	            	        		inPatientVO.setIpPhysioPreapprovalReqdYesNo("NC");
	            	        		inPatientVO.setIpPhysioPreapprovalLimit("NC");
	            	        		inPatientVO.setIpPhysioPpl("NC");
	            	        		inPatientVO.setIpPhysioNOOfSess("NC");
	            	        		inPatientVO.setIpPhysioPerSessLimit("NC");
	            	        		inPatientVO.setIpPhysioCopay("NC");
	            	        		inPatientVO.setIpPhysioOvrDeductable("NC");
	            	        		inPatientVO.setIpPhysioCopdedMINMAX("NC");
	            			
	            				}else if("S12".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
		                            inPatientVO.setIpAmbOpt("NC");
	            					
	            			    	inPatientVO.setIpAmblncGeoLoc("NC");
	            	             	inPatientVO.setIpAmblncCon("NC");
	            	             	inPatientVO.setIpAmblncEmr("NC");
	            	             	inPatientVO.setIpAmblncProTypes("NC");
	            	             	inPatientVO.setIpAmblncProFaclityTypes("NC");
	            	             	inPatientVO.setIpAmblncEmrNonemr("NC");
	            	             	inPatientVO.setIpAmblncPreaprvReqdYesNo("NC");
	            	             	inPatientVO.setIpAmblncPAL("NC");
	            	             	inPatientVO.setIpAmblncPPL("NC");
	            	             	inPatientVO.setIpAmblncCopay("NC");
	            	             	inPatientVO.setIpAmblncDeductable("NC");
	            	             	inPatientVO.setIpAmblncCopdedMINMAX("NC");
	            			
	            				}
	            				
	            			}else{
	            				
	            				
	                            if("S1".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	                            	ipOuterData.put("ipIpDcCmnRulOpt","PC");
	                             	inPatientVO.setIpDaycPPL(ipOuterData.get("IP_DAYC_PPL"));
	                            	inPatientVO.setIpDaycChronicPPL(ipOuterData.get("IP_DAYC_CHRONIC_PPL"));
	                            	inPatientVO.setIpDaycPedPPL(ipOuterData.get("IP_DAYC_PED_PPL"));
	                            	inPatientVO.setIpDaycChronPlusPedPPL(ipOuterData.get("IP_DAYC_PED_AND_CHRON_PPL"));
	                              	inPatientVO.setIpDaycOneMedPPL(ipOuterData.get("IP_DAYC_ONE_MEDICAL_PPL"));
	         	            	          
	         	            					
	         	            				}else if("S1-1".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            				
	         	            					ipOuterData.put("AocIPT","PC");
	         	            			
	         	            				}else if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("ipIpOpt","PC");
	         	            					inPatientVO.setIpPPL(ipOuterData.get("IP_PPL"));
	         	            			
	         	            				}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("ipRmBrdExpOpt","PC");
	         	            		
	         	            					inPatientVO.setIpRmAndBrdRmTypes(keyConverter1(ipOuterData.get("IP_ROOM_TYPES"),"ROOM_TYPE",null));
	         	            			    	inPatientVO.setIpRmAndBrdPPL(ipOuterData.get("IP_ROOM_PPL"));
	         	            			    	inPatientVO.setIpRmAndBrdCopay(ipOuterData.get("IP_ROOM_COPAY"));
	         	            			    	inPatientVO.setIpRmAndBrdDEDUCTABLE(ipOuterData.get("IP_ROOM_DEDUCT"));
	         	            			    	inPatientVO.setIpRmAndBrdCopdedMINMAX(ipOuterData.get("IP_ROOM_MinMax"));
	         	            			
	         	            				}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("ipIcuExpOpt","PC");
	         	            					
	         	            			    	inPatientVO.setIpIcuExpPPL(ipOuterData.get("IP_ICU_PPL"));
	         	            			      	inPatientVO.setIpIcuExpCopay(ipOuterData.get("IP_ICU_COPAY"));
	         	            			    	inPatientVO.setIpIcuExpDEDUCTABLE(ipOuterData.get("IP_ICU_DEDUCT"));
	         	            			    	inPatientVO.setIpIcuExpCopdedMINMAX(ipOuterData.get("IP_ICU_MinMax"));
	         	            			
	         	            				}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("IpConsOpt","PC");
	         	            			
	         	            				}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("ipSrgAnsthOpt","PC");
	         	            			    	inPatientVO.setIpSrgAndAnstSrgPPL(ipOuterData.get("IP_SGR_PPL"));
	         	            			    	inPatientVO.setIpSrgAndAnstSrgCopay(ipOuterData.get("IP_SGR_COPAY"));
	         	            			    	inPatientVO.setIpSrgAndAnstSrgDEDUCTABLE(ipOuterData.get("IP_SGR_DEDUCT"));
	         	            			    	inPatientVO.setIpSrgAndAnstSrgCopdedMINMAX(ipOuterData.get("IP_SGR_MinMax"));
	         	            			    	inPatientVO.setIpSrgAndAnstAnstPPL(ipOuterData.get("IP_ANSTHES_PPL"));
	         	            			      	inPatientVO.setIpSrgAndAnstAnstCopay(ipOuterData.get("IP_ANSTHES_COPAY"));
	         	            			    	inPatientVO.setIpSrgAndAnstAnstDEDUCTABLE(ipOuterData.get("IP_ANSTHES_DEDUCT"));
	         	            			    	inPatientVO.setIpSrgAndAnstAnstCopdedMINMAX(ipOuterData.get("IP_ANSTHES_MinMax"));
	         	            				
	         	            			
	         	            				}else if("S7".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("IpCompChrgOpt","PC");
	         	            				
	         	            			
	         	            				}else if("S8".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("ipInvOpt","PC");
	         	            					inPatientVO.setIpOverallInvstPPL(ipOuterData.get("IP_OVERALL_INVST_PPL"));
	         	            			    	inPatientVO.setIpLabAndPathPPL(ipOuterData.get("IP_LAB_AND_PATH_PPL"));
	         	            			    	inPatientVO.setIpExceptLabAndPathPPL(ipOuterData.get("IP_EXCEPT_LAB_AND_PATH_PPL"));
	         	            				
	         	            			
	         	            				}else if("S9".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("ipMedChemoOpt","PC");
	         	            					
	         	            			    	inPatientVO.setIpMedicationsPPL(ipOuterData.get("IP_MEDICATIONS_PPL"));
	         	            			    	inPatientVO.setIpMedicationsCopay(ipOuterData.get("IP_MEDICATIONS_COPAY"));
	         	            			      	inPatientVO.setIpMedicationsDEDUCTABLE(ipOuterData.get("IP_MEDICATIONS_DEDUCT"));
	         	            			    	inPatientVO.setIpMedicationsCopdedMINMAX(ipOuterData.get("IP_MEDICATIONS_MinMax"));
	         	            			    	inPatientVO.setIpIVFFluidsPPL(ipOuterData.get("IP_IVFLUID_PPL"));
	         	            			    	inPatientVO.setIpIVFFluidsCopay(ipOuterData.get("IP_IVFLUID_COPAY"));
	         	            			    	inPatientVO.setIpIVFFluidsDEDUCTABLE(ipOuterData.get("IP_IVFLUID_DEDUCT"));
	         	            			    	inPatientVO.setIpIVFFluidsCopdedMINMAX(ipOuterData.get("IP_IVFLUID_MinMax"));
	         	            			    	inPatientVO.setIpBldTrnsfusnPPL(ipOuterData.get("IP_BLD_TRNSFSN_PPL"));
	         	            			    	inPatientVO.setIpBldTrnsfusnCopay(ipOuterData.get("IP_BLD_TRNSFSN_COPAY"));
	         	            			      	inPatientVO.setIpBldTrnsfusnDEDUCTABLE(ipOuterData.get("IP_BLD_TRNSFSN_DEDUCT"));
	         	            			    	inPatientVO.setIpBldTrnsfusnCopdedMINMAX(ipOuterData.get("IP_BLD_TRNSFSN_MinMax"));
	         	            			    	inPatientVO.setIpAnalegicsPPL(ipOuterData.get("IP_ANALGESICS_PPL"));
	         	            			    	inPatientVO.setIpAnalegicsCopay(ipOuterData.get("IP_ANALGESICS_COPAY"));
	         	            			    	inPatientVO.setIpAnalegicsDEDUCTABLE(ipOuterData.get("IP_ANALGESICS_DEDUCT"));
	         	            			    	inPatientVO.setIpAnalegicsCopdedMINMAX(ipOuterData.get("IP_ANALGESICS_MinMax"));
	         	            			    	inPatientVO.setIpSrgImplPPL(ipOuterData.get("IP_SGRIMPL_PPL"));
	         	            			    	inPatientVO.setIpSrgImplCopay(ipOuterData.get("IP_SGRIMP_COPAY"));
	         	            			      	inPatientVO.setIpSrgImplDEDUCTABLE(ipOuterData.get("IP_SGRIMP_DEDUCT"));
	         	            			    	inPatientVO.setIpSrgImplCopdedMINMAX(ipOuterData.get("IP_SGRIMP_MinMax"));
	         	            			    	inPatientVO.setIpChemoPPL(ipOuterData.get("IP_CHEMO_PPL"));
	         	            			    	inPatientVO.setIpChemoCopay(ipOuterData.get("IP_CHEMO_COPAY"));
	         	            			    	inPatientVO.setIpChemoDEDUCTABLE(ipOuterData.get("IP_CHEMO_DEDUCT"));
	         	            			    	inPatientVO.setIpChemoCopdedMINMAX(ipOuterData.get("IP_CHEMO_MinMax"));
	         	            				
	         	            			
	         	            				}else if("S10".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("ipDispConsOpt","PC");
	         	            					
	         	            					inPatientVO.setIpConsmAndDspsPPL(ipOuterData.get("IP_DISPOSAL_CONSUM_PPL"));
	         	            			    	inPatientVO.setIpConsmAndDspsCopay(ipOuterData.get("IP_DISPOSAL_CONSUM_COPAY"));
	         	            			      	inPatientVO.setIpConsmAndDspsDEDUCTABLE(ipOuterData.get("IP_DISPOSAL_CONSUM_DEDUCT"));
	         	            			    	inPatientVO.setIpConsmAndDspsCopdedMINMAX(ipOuterData.get("IP_DISPOSAL_CONSUM_MinMax"));
	         	            			    	inPatientVO.setIpConsmAndDspsExPrdVal(ipOuterData.get("IP_DISPOSAL_CONSUM_PRD_VAL"));
	         	            			    	inPatientVO.setIpConsmAndDspsExPrdUnt(unitConverter(ipOuterData.get("IP_DISPOSAL_CONSUM_PRD_UNT")));
	         	            			
	         	            				
	         	            			
	         	            				}else if("S11".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("IpPhyOpt","PC");
	         	            				
	         	            			
	         	            				}else if("S12".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					ipOuterData.put("IpAmbOpt","PC");
	         	            				
	         	            			
	         	            				}
	            				
	            				
	            				
	            			}
	            			
	            		}
	            		
	                   if("DAYC".equalsIgnoreCase(strBenefitID)){
	            			
	            			if("1".equals(strCondTypeDefault)){
	            				
	            			if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            				dcPatientVO.setDcDaycPPL("Y");
	            					
	            					
	            			
	            				}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					dcPatientVO.setIpRmAndBrdRmTypes("Y");
	            			    	dcPatientVO.setIpRmAndBrdPPL("Y");
	            			    	dcPatientVO.setIpRmAndBrdCopay("Y");
	            			    	dcPatientVO.setIpRmAndBrdDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpRmAndBrdCopdedMINMAX("Y");
	            			
	            				}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					dcPatientVO.setIpIcuExpPPL("Y");
	            			      	dcPatientVO.setIpIcuExpCopay("Y");
	            			    	dcPatientVO.setIpIcuExpDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpIcuExpCopdedMINMAX("Y");
	            		
	            			
	            				}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            			       	dcPatientVO.setIpConsultGeoLocCov("Y");
	            	            	dcPatientVO.setIpConsultConCov("Y");
	            	            	dcPatientVO.setIpConsultEncType("Y");
	            	            	dcPatientVO.setIpConsultProTypes("Y");
	            	            	dcPatientVO.setIpConsultPPL("Y");
	            	            	dcPatientVO.setIpConsultEmrCov("Y");
	            	            	
	            	            	   dcPatientVO.setIpGpConsultCopay("Y");
	            	    				 dcPatientVO.setIpGpConsultDEDUCTABLE("Y");
	            	    				 dcPatientVO.setIpGpConsultMinMax("Y");
	            	    				 dcPatientVO.setIpSpConsultCopay("Y");
	            	    				 dcPatientVO.setIpSpConsultDEDUCTABLE("Y");
	            	    				 dcPatientVO.setIpSpConsultMinMax("Y");
	            	    				 
	            	    				  dcPatientVO.setIpConsultFlwPeriod("Y");
	            	    		          dcPatientVO.setIpConsultFlwPeriodUnit("Y");
	            			
	            			
	            				}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            				   	dcPatientVO.setIpSrgAndAnstSrgPPL("Y");
	            			    	dcPatientVO.setIpSrgAndAnstSrgCopay("Y");
	            			    	dcPatientVO.setIpSrgAndAnstSrgDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpSrgAndAnstSrgCopdedMINMAX("Y");
	            			    	dcPatientVO.setIpSrgAndAnstAnstPPL("Y");
	            			      	dcPatientVO.setIpSrgAndAnstAnstCopay("Y");
	            			    	dcPatientVO.setIpSrgAndAnstAnstDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpSrgAndAnstAnstCopdedMINMAX("Y");
	            			
	            				}else if("S7".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            	             	dcPatientVO.setIpCompnChargGeoLoc("Y");
	            	            	dcPatientVO.setIpCompnChargCon("Y");
	            	            	dcPatientVO.setIpCompnChargEmr("Y");
	            	            	dcPatientVO.setIpCompnChargProTypes("Y");
	            	            	dcPatientVO.setIpCompnChargProFaclityTypes("Y");
	            	            	dcPatientVO.setIpCompnChargPreaprvReqdYesNo("Y");
	            	            	dcPatientVO.setIpCompnChargPAL("Y");
	            	            	dcPatientVO.setIpCompnChargFrmAge("Y");
	            	            	dcPatientVO.setIpCompnChargToAge("Y");
	            	            	dcPatientVO.setIpCompnChargFrmToAgeUt("Y");
	            	            	dcPatientVO.setIpCompnChargPPL("Y");
	            	            	dcPatientVO.setIpCompnChargNoDaysAlwd("Y");
	            	            	dcPatientVO.setIpCompnChargMxLmtAlwdPerDay("Y");
	            	            	dcPatientVO.setIpCompnChargCopay("Y");
	            	            	dcPatientVO.setIpCompnChargDEDUCTABLE("Y");
	            	            	dcPatientVO.setIpCompnChargCopdedMINMAX("Y");
	            					
	            			
	            			
	            				} else if("S8".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					dcPatientVO.setIpOverallInvstPPL("Y");
	            			    	dcPatientVO.setIpLabAndPathPPL("Y");
	            			    	dcPatientVO.setIpExceptLabAndPathPPL("Y");
	            			    	
	            			    	dcPatientVO.setIpInvLabProviderTypes("Y"); 
	                        		dcPatientVO.setIpInvLabPreapprovalReqdYesNo("Y"); 
	                        		dcPatientVO.setIpInvLabPreapprovalReqdLimit("Y");
	                        		dcPatientVO.setIpInvLabPpl("Y"); 
	                        		dcPatientVO.setIpInvLabNoOfSess("Y");
	                        		dcPatientVO.setIpInvLabCopay("Y"); 
	                        		dcPatientVO.setIpInvLabDeductable("Y");
	                        		dcPatientVO.setIpInvLabCopdedMINMAX("Y");
	                        		
	                        		dcPatientVO.setIpInvPatProviderTypes("Y"); 
	                         		dcPatientVO.setIpInvPatPreapprovalReqdYesNo("Y"); 
	                         		dcPatientVO.setIpInvPatPreapprovalReqdLimit("Y");
	                         		dcPatientVO.setIpInvPatPpl("Y");
	                         		dcPatientVO.setIpInvPatNoOfSess("Y");
	                         		dcPatientVO.setIpInvPatCopay("Y"); 
	                         		dcPatientVO.setIpInvPatDeductable("Y");
	                         		dcPatientVO.setIpInvPatCopdedMINMAX("Y");
	                         		
	                         		dcPatientVO.setIpInvUltraProviderFacilityTypes("Y"); 
	                         		dcPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y"); 
	                         		dcPatientVO.setIpInvUltraPreapprovalReqdLimit("Y");
	                         		dcPatientVO.setIpInvUltraPpl("Y"); 
	                         		dcPatientVO.setIpInvUltraNoOfSess("Y");
	                         		dcPatientVO.setIpInvUltraCopay("Y"); 
	                         		dcPatientVO.setIpInvUltraDeductable("Y");
	                         		dcPatientVO.setIpInvUltraCopdedMINMAX("Y");
	                         		
	                         		dcPatientVO.setIpInvCtScanProviderFacilityTypes("Y"); 
	                         		dcPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y"); 
	                         		dcPatientVO.setIpInvCtScanPreapprovalReqdLimit("Y");
	                         		dcPatientVO.setIpInvCtScanPpl("Y"); 
	                         		dcPatientVO.setIpInvCtScanNoOfSess("Y");
	                         		dcPatientVO.setIpInvCtScanCopay("Y"); 
	                         		dcPatientVO.setIpInvCtScanDeductable("Y");
	                         		dcPatientVO.setIpInvCtScanCopdedMINMAX("Y");
	                         		
	                         		dcPatientVO.setIpInvMriProviderFacilityTypes("Y"); 
	                         		dcPatientVO.setIpInvMriPreapprovalReqdYesNo("Y"); 
	                         		dcPatientVO.setIpInvMriPreapprovalReqdLimit("Y");
	                         		dcPatientVO.setIpInvMriPpl("Y"); 
	                         		dcPatientVO.setIpInvMriNoOfSess("Y");
	                         		dcPatientVO.setIpInvMriCopay("Y"); 
	                         		dcPatientVO.setIpInvMriDeductable("Y");
	                         		dcPatientVO.setIpInvMriCopdedMINMAX("Y");
	                         		
	                         		dcPatientVO.setIpInvDiagAndTherapProviderTypes("Y"); 
	                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y"); 
	                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit("Y");
	                         		dcPatientVO.setIpInvDiagAndTherapPpl("Y"); 
	                         		dcPatientVO.setIpInvDiagAndTherapNoOfSess("Y");
	                         		dcPatientVO.setIpInvDiagAndTherapCopay("Y"); 
	                         		dcPatientVO.setIpInvDiagAndTherapDeductable("Y");
	                         		dcPatientVO.setIpInvDiagAndTherapCopdedMINMAX("Y");
	            			
	            				} else if("S9".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            			    	dcPatientVO.setIpMedicationsPPL("Y");
	            			    	dcPatientVO.setIpMedicationsCopay("Y");
	            			      	dcPatientVO.setIpMedicationsDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpMedicationsCopdedMINMAX("Y");
	            			    	dcPatientVO.setIpIVFFluidsPPL("Y");
	            			    	dcPatientVO.setIpIVFFluidsCopay("Y");
	            			    	dcPatientVO.setIpIVFFluidsDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpIVFFluidsCopdedMINMAX("Y");
	            			    	dcPatientVO.setIpBldTrnsfusnPPL("Y");
	            			    	dcPatientVO.setIpBldTrnsfusnCopay("Y");
	            			      	dcPatientVO.setIpBldTrnsfusnDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpBldTrnsfusnCopdedMINMAX("Y");
	            			    	dcPatientVO.setIpAnalegicsPPL("Y");
	            			    	dcPatientVO.setIpAnalegicsCopay("Y");
	            			    	dcPatientVO.setIpAnalegicsDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpAnalegicsCopdedMINMAX("Y");
	            			    	dcPatientVO.setIpSrgImplPPL("Y");
	            			    	dcPatientVO.setIpSrgImplCopay("Y");
	            			      	dcPatientVO.setIpSrgImplDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpSrgImplCopdedMINMAX("Y");
	            			    	dcPatientVO.setIpChemoPPL("Y");
	            			    	dcPatientVO.setIpChemoCopay("Y");
	            			    	dcPatientVO.setIpChemoDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpChemoCopdedMINMAX("Y");
	            			
	            			
	            				} else if("S10".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            			    	dcPatientVO.setIpConsmAndDspsPPL("Y");
	            			    	dcPatientVO.setIpConsmAndDspsCopay("Y");
	            			      	dcPatientVO.setIpConsmAndDspsDEDUCTABLE("Y");
	            			    	dcPatientVO.setIpConsmAndDspsCopdedMINMAX("Y");
	            			    	dcPatientVO.setIpConsmAndDspsExPrdVal("Y");
	            			    	dcPatientVO.setIpConsmAndDspsExPrdUnt("Y");
	            			
	            				} else if("S11".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					dcPatientVO.setIpPhysioProviderFacilityTypes("Y");
	            	        		dcPatientVO.setIpPhysioPreapprovalReqdYesNo("Y");
	            	        		dcPatientVO.setIpPhysioPreapprovalLimit("Y");
	            	        		dcPatientVO.setIpPhysioPpl("Y");
	            	        		dcPatientVO.setIpPhysioNOOfSess("Y");
	            	        		dcPatientVO.setIpPhysioPerSessLimit("Y");
	            	        		dcPatientVO.setIpPhysioCopay("Y");
	            	        		dcPatientVO.setIpPhysioOvrDeductable("Y");
	            	        		dcPatientVO.setIpPhysioCopdedMINMAX("Y");
	            			
	            				} else if("S12".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            			    	dcPatientVO.setIpAmblncGeoLoc("Y");
	            	             	dcPatientVO.setIpAmblncCon("Y");
	            	             	dcPatientVO.setIpAmblncEmr("Y");
	            	             	dcPatientVO.setIpAmblncProTypes("Y");
	            	             	dcPatientVO.setIpAmblncProFaclityTypes("Y");
	            	             	dcPatientVO.setIpAmblncEmrNonemr("Y");
	            	             	dcPatientVO.setIpAmblncPreaprvReqdYesNo("Y");
	            	             	dcPatientVO.setIpAmblncPAL("Y");
	            	             	dcPatientVO.setIpAmblncPPL("Y");
	            	             	dcPatientVO.setIpAmblncCopay("Y");
	            	             	dcPatientVO.setIpAmblncDeductable("Y");
	            	             	dcPatientVO.setIpAmblncCopdedMINMAX("Y");
	            			
	            				} 
	            				
	            				
	            			}else if("2".equals(strCondTypeDefault)){
	            				
                               if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					dcPatientVO.setDcDaycPPL("NC");
	            			
	            				}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					dcPatientVO.setIpRmAndBrdRmTypes("NC");
	            			    	dcPatientVO.setIpRmAndBrdPPL("NC");
	            			    	dcPatientVO.setIpRmAndBrdCopay("NC");
	            			    	dcPatientVO.setIpRmAndBrdDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpRmAndBrdCopdedMINMAX("NC");
	            			
	            				}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					dcPatientVO.setIpIcuExpPPL("NC");
	            			      	dcPatientVO.setIpIcuExpCopay("NC");
	            			    	dcPatientVO.setIpIcuExpDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpIcuExpCopdedMINMAX("NC");
	            			
	            				}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            			       	dcPatientVO.setIpConsultGeoLocCov("NC");
	            	            	dcPatientVO.setIpConsultConCov("NC");
	            	            	dcPatientVO.setIpConsultEncType("NC");
	            	            	dcPatientVO.setIpConsultProTypes("NC");
	            	            	dcPatientVO.setIpConsultPPL("NC");
	            	            	dcPatientVO.setIpConsultEmrCov("NC");
	            	            	
	            	            	   dcPatientVO.setIpGpConsultCopay("NC");
	            	    				 dcPatientVO.setIpGpConsultDEDUCTABLE("NC");
	            	    				 dcPatientVO.setIpGpConsultMinMax("NC");
	            	    				 dcPatientVO.setIpSpConsultCopay("NC");
	            	    				 dcPatientVO.setIpSpConsultDEDUCTABLE("NC");
	            	    				 dcPatientVO.setIpSpConsultMinMax("NC");
	            	    				 
	            	    				  dcPatientVO.setIpConsultFlwPeriod("NC");
	            	    		          dcPatientVO.setIpConsultFlwPeriodUnit("NC");
	            			
	            				}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            				   	dcPatientVO.setIpSrgAndAnstSrgPPL("NC");
	            			    	dcPatientVO.setIpSrgAndAnstSrgCopay("NC");
	            			    	dcPatientVO.setIpSrgAndAnstSrgDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpSrgAndAnstSrgCopdedMINMAX("NC");
	            			    	dcPatientVO.setIpSrgAndAnstAnstPPL("NC");
	            			      	dcPatientVO.setIpSrgAndAnstAnstCopay("NC");
	            			    	dcPatientVO.setIpSrgAndAnstAnstDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpSrgAndAnstAnstCopdedMINMAX("NC");
	            			
	            				} else if("S7".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            	             	dcPatientVO.setIpCompnChargGeoLoc("NC");
	            	            	dcPatientVO.setIpCompnChargCon("NC");
	            	            	dcPatientVO.setIpCompnChargEmr("NC");
	            	            	dcPatientVO.setIpCompnChargProTypes("NC");
	            	            	dcPatientVO.setIpCompnChargProFaclityTypes("NC");
	            	            	dcPatientVO.setIpCompnChargPreaprvReqdYesNo("NC");
	            	            	dcPatientVO.setIpCompnChargPAL("NC");
	            	            	dcPatientVO.setIpCompnChargFrmAge("NC");
	            	            	dcPatientVO.setIpCompnChargToAge("NC");
	            	            	dcPatientVO.setIpCompnChargFrmToAgeUt("NC");
	            	            	dcPatientVO.setIpCompnChargPPL("NC");
	            	            	dcPatientVO.setIpCompnChargNoDaysAlwd("NC");
	            	            	dcPatientVO.setIpCompnChargMxLmtAlwdPerDay("NC");
	            	            	dcPatientVO.setIpCompnChargCopay("NC");
	            	            	dcPatientVO.setIpCompnChargDEDUCTABLE("NC");
	            	            	dcPatientVO.setIpCompnChargCopdedMINMAX("NC");
	            			
	            				}else if("S8".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					dcPatientVO.setIpOverallInvstPPL("NC");
	            			    	dcPatientVO.setIpLabAndPathPPL("NC");
	            			    	dcPatientVO.setIpExceptLabAndPathPPL("NC");
	            			    	
	            			    	dcPatientVO.setIpInvLabProviderTypes("NC"); 
	                        		dcPatientVO.setIpInvLabPreapprovalReqdYesNo("NC"); 
	                        		dcPatientVO.setIpInvLabPreapprovalReqdLimit("NC");
	                        		dcPatientVO.setIpInvLabPpl("NC"); 
	                        		dcPatientVO.setIpInvLabNoOfSess("NC");
	                        		dcPatientVO.setIpInvLabCopay("NC"); 
	                        		dcPatientVO.setIpInvLabDeductable("NC");
	                        		dcPatientVO.setIpInvLabCopdedMINMAX("NC");
	                        		
	                        		dcPatientVO.setIpInvPatProviderTypes("NC"); 
	                         		dcPatientVO.setIpInvPatPreapprovalReqdYesNo("NC"); 
	                         		dcPatientVO.setIpInvPatPreapprovalReqdLimit("NC");
	                         		dcPatientVO.setIpInvPatPpl("NC");
	                         		dcPatientVO.setIpInvPatNoOfSess("NC");
	                         		dcPatientVO.setIpInvPatCopay("NC"); 
	                         		dcPatientVO.setIpInvPatDeductable("NC");
	                         		dcPatientVO.setIpInvPatCopdedMINMAX("NC");
	                         		
	                         		dcPatientVO.setIpInvUltraProviderFacilityTypes("NC"); 
	                         		dcPatientVO.setIpInvUltraPreapprovalReqdYesNo("NC"); 
	                         		dcPatientVO.setIpInvUltraPreapprovalReqdLimit("NC");
	                         		dcPatientVO.setIpInvUltraPpl("NC"); 
	                         		dcPatientVO.setIpInvUltraNoOfSess("NC");
	                         		dcPatientVO.setIpInvUltraCopay("NC"); 
	                         		dcPatientVO.setIpInvUltraDeductable("NC");
	                         		dcPatientVO.setIpInvUltraCopdedMINMAX("NC");
	                         		
	                         		dcPatientVO.setIpInvCtScanProviderFacilityTypes("NC"); 
	                         		dcPatientVO.setIpInvCtScanPreapprovalReqdYesNo("NC"); 
	                         		dcPatientVO.setIpInvCtScanPreapprovalReqdLimit("NC");
	                         		dcPatientVO.setIpInvCtScanPpl("NC"); 
	                         		dcPatientVO.setIpInvCtScanNoOfSess("NC");
	                         		dcPatientVO.setIpInvCtScanCopay("NC"); 
	                         		dcPatientVO.setIpInvCtScanDeductable("NC");
	                         		dcPatientVO.setIpInvCtScanCopdedMINMAX("NC");
	                         		
	                         		dcPatientVO.setIpInvMriProviderFacilityTypes("NC"); 
	                         		dcPatientVO.setIpInvMriPreapprovalReqdYesNo("NC"); 
	                         		dcPatientVO.setIpInvMriPreapprovalReqdLimit("NC");
	                         		dcPatientVO.setIpInvMriPpl("NC"); 
	                         		dcPatientVO.setIpInvMriNoOfSess("NC");
	                         		dcPatientVO.setIpInvMriCopay("NC"); 
	                         		dcPatientVO.setIpInvMriDeductable("NC");
	                         		dcPatientVO.setIpInvMriCopdedMINMAX("NC");
	                         		
	                         		dcPatientVO.setIpInvDiagAndTherapProviderTypes("NC"); 
	                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("NC"); 
	                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit("NC");
	                         		dcPatientVO.setIpInvDiagAndTherapPpl("NC"); 
	                         		dcPatientVO.setIpInvDiagAndTherapNoOfSess("NC");
	                         		dcPatientVO.setIpInvDiagAndTherapCopay("NC"); 
	                         		dcPatientVO.setIpInvDiagAndTherapDeductable("NC");
	                         		dcPatientVO.setIpInvDiagAndTherapCopdedMINMAX("NC");
	            			
	            				}else if("S9".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            			    	dcPatientVO.setIpMedicationsPPL("NC");
	            			    	dcPatientVO.setIpMedicationsCopay("NC");
	            			      	dcPatientVO.setIpMedicationsDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpMedicationsCopdedMINMAX("NC");
	            			    	dcPatientVO.setIpIVFFluidsPPL("NC");
	            			    	dcPatientVO.setIpIVFFluidsCopay("NC");
	            			    	dcPatientVO.setIpIVFFluidsDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpIVFFluidsCopdedMINMAX("NC");
	            			    	dcPatientVO.setIpBldTrnsfusnPPL("NC");
	            			    	dcPatientVO.setIpBldTrnsfusnCopay("NC");
	            			      	dcPatientVO.setIpBldTrnsfusnDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpBldTrnsfusnCopdedMINMAX("NC");
	            			    	dcPatientVO.setIpAnalegicsPPL("NC");
	            			    	dcPatientVO.setIpAnalegicsCopay("NC");
	            			    	dcPatientVO.setIpAnalegicsDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpAnalegicsCopdedMINMAX("NC");
	            			    	dcPatientVO.setIpSrgImplPPL("NC");
	            			    	dcPatientVO.setIpSrgImplCopay("NC");
	            			      	dcPatientVO.setIpSrgImplDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpSrgImplCopdedMINMAX("NC");
	            			    	dcPatientVO.setIpChemoPPL("NC");
	            			    	dcPatientVO.setIpChemoCopay("NC");
	            			    	dcPatientVO.setIpChemoDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpChemoCopdedMINMAX("NC");
	            			
	            				}else if("S10".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            			    	dcPatientVO.setIpConsmAndDspsPPL("NC");
	            			    	dcPatientVO.setIpConsmAndDspsCopay("NC");
	            			      	dcPatientVO.setIpConsmAndDspsDEDUCTABLE("NC");
	            			    	dcPatientVO.setIpConsmAndDspsCopdedMINMAX("NC");
	            			    	dcPatientVO.setIpConsmAndDspsExPrdVal("NC");
	            			    	dcPatientVO.setIpConsmAndDspsExPrdUnt("NC");
	            			
	            				}else if("S11".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            					dcPatientVO.setIpPhysioProviderFacilityTypes("NC");
	            	        		dcPatientVO.setIpPhysioPreapprovalReqdYesNo("NC");
	            	        		dcPatientVO.setIpPhysioPreapprovalLimit("NC");
	            	        		dcPatientVO.setIpPhysioPpl("NC");
	            	        		dcPatientVO.setIpPhysioNOOfSess("NC");
	            	        		dcPatientVO.setIpPhysioPerSessLimit("NC");
	            	        		dcPatientVO.setIpPhysioCopay("NC");
	            	        		dcPatientVO.setIpPhysioOvrDeductable("NC");
	            	        		dcPatientVO.setIpPhysioCopdedMINMAX("NC");
	            			
	            				}else if("S12".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            					
	            					
	            			    	dcPatientVO.setIpAmblncGeoLoc("NC");
	            	             	dcPatientVO.setIpAmblncCon("NC");
	            	             	dcPatientVO.setIpAmblncEmr("NC");
	            	             	dcPatientVO.setIpAmblncProTypes("NC");
	            	             	dcPatientVO.setIpAmblncProFaclityTypes("NC");
	            	             	dcPatientVO.setIpAmblncEmrNonemr("NC");
	            	             	dcPatientVO.setIpAmblncPreaprvReqdYesNo("NC");
	            	             	dcPatientVO.setIpAmblncPAL("NC");
	            	             	dcPatientVO.setIpAmblncPPL("NC");
	            	             	dcPatientVO.setIpAmblncCopay("NC");
	            	             	dcPatientVO.setIpAmblncDeductable("NC");
	            	             	dcPatientVO.setIpAmblncCopdedMINMAX("NC");
	            			
	            				}
	            				
	            			}else{
	            				
	            				
	                           if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcDcOpt","PC");
	         	            					dcPatientVO.setDcDaycPPL(dcOuterData.get("DC_DAYC_PPL"));
	         	            			
	         	            				}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcRmBrdExpOpt","PC");
	         	            		
	         	            					dcPatientVO.setIpRmAndBrdRmTypes(keyConverter1(dcOuterData.get("DC_ROOM_TYPES"), "ROOM_TYPE", null));
	         	            			    	dcPatientVO.setIpRmAndBrdPPL(dcOuterData.get("DC_ROOM_PPL"));
	         	            			    	dcPatientVO.setIpRmAndBrdCopay(dcOuterData.get("DC_ROOM_COPAY"));
	         	            			    	dcPatientVO.setIpRmAndBrdDEDUCTABLE(dcOuterData.get("DC_ROOM_DEDUCT"));
	         	            			    	dcPatientVO.setIpRmAndBrdCopdedMINMAX(dcOuterData.get("DC_ROOM_MinMax"));
	         	            			
	         	            				}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcIcuExpOpt","PC");
	         	            					
	         	            					dcPatientVO.setIpIcuExpPPL(dcOuterData.get("DC_ICU_PPL"));
	         	            			      	dcPatientVO.setIpIcuExpCopay(dcOuterData.get("DC_ICU_COPAY"));
	         	            			    	dcPatientVO.setIpIcuExpDEDUCTABLE(dcOuterData.get("DC_ICU_DEDUCT"));
	         	            			    	dcPatientVO.setIpIcuExpCopdedMINMAX(dcOuterData.get("DC_ICU_MinMax"));
	         	            			
	         	            				}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcConsOpt","PC");
	         	            			
	         	            				}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcSrgAnsthOpt","PC");
	         	            					
	         	            					dcPatientVO.setIpSrgAndAnstSrgPPL(dcOuterData.get("DC_SGR_PPL"));
	         	            			    	dcPatientVO.setIpSrgAndAnstSrgCopay(dcOuterData.get("DC_SGR_COPAY"));
	         	            			    	dcPatientVO.setIpSrgAndAnstSrgDEDUCTABLE(dcOuterData.get("DC_SGR_DEDUCT"));
	         	            			    	dcPatientVO.setIpSrgAndAnstSrgCopdedMINMAX(dcOuterData.get("DC_SGR_MinMax"));
	         	            			    	dcPatientVO.setIpSrgAndAnstAnstPPL(dcOuterData.get("DC_ANSTHES_PPL"));
	         	            			      	dcPatientVO.setIpSrgAndAnstAnstCopay(dcOuterData.get("DC_ANSTHES_COPAY"));
	         	            			    	dcPatientVO.setIpSrgAndAnstAnstDEDUCTABLE(dcOuterData.get("DC_ANSTHES_DEDUCT"));
	         	            			    	dcPatientVO.setIpSrgAndAnstAnstCopdedMINMAX(dcOuterData.get("DC_ANSTHES_MinMax"));
	         	            				
	         	            			
	         	            				}else if("S7".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcCompChrgOpt","PC");
	         	            				
	         	            			
	         	            				}else if("S8".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcInvOpt","PC");
	         	            					dcPatientVO.setIpOverallInvstPPL(dcOuterData.get("DC_OVERALL_INVST_PPL"));
	         	            			    	dcPatientVO.setIpLabAndPathPPL(dcOuterData.get("DC_LAB_AND_PATH_PPL"));
	         	            			    	dcPatientVO.setIpExceptLabAndPathPPL(dcOuterData.get("DC_EXCEPT_LAB_AND_PATH_PPL"));
	         	            				
	         	            			
	         	            				}else if("S9".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcMedChemoOpt","PC");
	         	            					
	         	            					dcPatientVO.setIpMedicationsPPL(dcOuterData.get("DC_MEDICATIONS_PPL"));
	         	            			    	dcPatientVO.setIpMedicationsCopay(dcOuterData.get("DC_MEDICATIONS_COPAY"));
	         	            			      	dcPatientVO.setIpMedicationsDEDUCTABLE(dcOuterData.get("DC_MEDICATIONS_DEDUCT"));
	         	            			    	dcPatientVO.setIpMedicationsCopdedMINMAX(dcOuterData.get("DC_MEDICATIONS_MinMax"));
	         	            			    	dcPatientVO.setIpIVFFluidsPPL(dcOuterData.get("DC_IVFLUID_PPL"));
	         	            			    	dcPatientVO.setIpIVFFluidsCopay(dcOuterData.get("DC_IVFLUID_COPAY"));
	         	            			    	dcPatientVO.setIpIVFFluidsDEDUCTABLE(dcOuterData.get("DC_IVFLUID_DEDUCT"));
	         	            			    	dcPatientVO.setIpIVFFluidsCopdedMINMAX(dcOuterData.get("DC_IVFLUID_MinMax"));
	         	            			    	dcPatientVO.setIpBldTrnsfusnPPL(dcOuterData.get("DC_BLD_TRNSFSN_PPL"));
	         	            			    	dcPatientVO.setIpBldTrnsfusnCopay(dcOuterData.get("DC_BLD_TRNSFSN_COPAY"));
	         	            			      	dcPatientVO.setIpBldTrnsfusnDEDUCTABLE(dcOuterData.get("DC_BLD_TRNSFSN_DEDUCT"));
	         	            			    	dcPatientVO.setIpBldTrnsfusnCopdedMINMAX(dcOuterData.get("DC_BLD_TRNSFSN_MinMax"));
	         	            			    	dcPatientVO.setIpAnalegicsPPL(dcOuterData.get("DC_ANALGESICS_PPL"));
	         	            			    	dcPatientVO.setIpAnalegicsCopay(dcOuterData.get("DC_ANALGESICS_COPAY"));
	         	            			    	dcPatientVO.setIpAnalegicsDEDUCTABLE(dcOuterData.get("DC_ANALGESICS_DEDUCT"));
	         	            			    	dcPatientVO.setIpAnalegicsCopdedMINMAX(dcOuterData.get("DC_ANALGESICS_MinMax"));
	         	            			    	dcPatientVO.setIpSrgImplPPL(dcOuterData.get("DC_SGRIMPL_PPL"));
	         	            			    	dcPatientVO.setIpSrgImplCopay(dcOuterData.get("DC_SGRIMP_COPAY"));
	         	            			      	dcPatientVO.setIpSrgImplDEDUCTABLE(dcOuterData.get("DC_SGRIMP_DEDUCT"));
	         	            			    	dcPatientVO.setIpSrgImplCopdedMINMAX(dcOuterData.get("DC_SGRIMP_MinMax"));
	         	            			    	dcPatientVO.setIpChemoPPL(dcOuterData.get("DC_CHEMO_PPL"));
	         	            			    	dcPatientVO.setIpChemoCopay(dcOuterData.get("DC_CHEMO_COPAY"));
	         	            			    	dcPatientVO.setIpChemoDEDUCTABLE(dcOuterData.get("DC_CHEMO_DEDUCT"));
	         	            			    	dcPatientVO.setIpChemoCopdedMINMAX(dcOuterData.get("DC_CHEMO_MinMax"));
	         	            				
	         	            			
	         	            				}else if("S10".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcDispConsOpt","PC");
	         	            					
	         	            			    	dcPatientVO.setIpConsmAndDspsPPL(dcOuterData.get("DC_DISPOSAL_CONSUM_PPL"));
	         	            			    	dcPatientVO.setIpConsmAndDspsCopay(dcOuterData.get("DC_DISPOSAL_CONSUM_COPAY"));
	         	            			      	dcPatientVO.setIpConsmAndDspsDEDUCTABLE(dcOuterData.get("DC_DISPOSAL_CONSUM_DEDUCT"));
	         	            			    	dcPatientVO.setIpConsmAndDspsCopdedMINMAX(dcOuterData.get("DC_DISPOSAL_CONSUM_MinMax"));
	         	            			    	dcPatientVO.setIpConsmAndDspsExPrdVal(dcOuterData.get("DC_DISPOSAL_CONSUM_PRD_VAL"));
	         	            			    	dcPatientVO.setIpConsmAndDspsExPrdUnt(unitConverter(dcOuterData.get("DC_DISPOSAL_CONSUM_PRD_UNT")));
	         	            			
	         	            				
	         	            			
	         	            				}else if("S11".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcPhyOpt","PC");
	         	            				
	         	            			
	         	            				}else if("S12".equalsIgnoreCase(sbNode.valueOf("@id"))){
	         	            					
	         	            					dcOuterData.put("DcAmbOpt","PC");
	         	            				
	         	            			
	         	            				}
	            				
	            				
	            				
	            			}
	            			
	            		}
	            		
	                   if("DNTL".equalsIgnoreCase(strBenefitID)){
	                	   
	                	 if("S1".equalsIgnoreCase(sbNode.valueOf("@id"))){
            					
	                			if("1".equals(strCondTypeDefault)){
		                			
		                			dentalVo.setPpl("Y");
		                			dentalVo.setCopay("Y");
		                			dentalVo.setDeductible("Y");
		                			dentalVo.setCopDedMinMax("Y");
		                			
		                		}else if("2".equals(strCondTypeDefault)){
		                			
		                			dentalVo.setPpl("NC");
		                			dentalVo.setCopay("NC");
		                			dentalVo.setDeductible("NC");
		                			dentalVo.setCopDedMinMax("NC");
		                			
		                		}
            				
            			
            				}else if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
            					
	                               if("1".equals(strCondTypeDefault)){
		                			
	                            	   ipOuterData.put("AocDntl","Y");
		                			
		                		}else if("2".equals(strCondTypeDefault)){
		                			
		                			ipOuterData.put("AocDntl","NC");
		                		}else{
		                			
		                			ipOuterData.put("AocDntl","PC");
		                			
		                		}
            					
            					
            				
            			
            				}
	                	   
	                	
	                	   
	                   }
	                   
                        if("OPTC".equalsIgnoreCase(strBenefitID)){
                        	
                       	 if("S1".equalsIgnoreCase(sbNode.valueOf("@id"))){
         					
                             if("1".equals(strCondTypeDefault)){
 	                        	
 	                        	opticaVo.setPpl("Y");
 	                        	opticaVo.setCopay("Y");
 	                        	opticaVo.setDeductible("Y");
 	                        	opticaVo.setCopDedMinMax("Y");
 	                			
 	                		}else if("2".equals(strCondTypeDefault)){
 	                			
 	                			opticaVo.setPpl("NC");
 	                        	opticaVo.setCopay("NC");
 	                        	opticaVo.setDeductible("NC");
 	                        	opticaVo.setCopDedMinMax("NC");
 	                			
 	                		}
         				
         			
         				}else if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
        					
                            if("1".equals(strCondTypeDefault)){
	                			
                         	   ipOuterData.put("AocOptc","Y");
	                			
	                		}else if("2".equals(strCondTypeDefault)){
	                			
	                			ipOuterData.put("AocOptc","NC");
	                		}else{
	                			
	                			ipOuterData.put("AocOptc","PC");
	                			
	                		}
     					
     					
     				
     			
     				}
                        	
	                  
	                	   
	                   }
                        
                           if("GLOB".equalsIgnoreCase(strBenefitID)){
                        	
	                        if("1".equals(strCondTypeDefault)){
	                        	
	                        	globalVO.setGlobMemberStatus("Y");
	                        	globalVO.setGlobPreauthAdmisnPeriodWithinUAE("Y");
	                        	globalVO.setGlobPreauthAdmisnPeriodOutsideUAE("Y");
	                        	globalVO.setGlobClmSubPrdNtwkClmWithinUAE("Y");
	                        	globalVO.setGlobClmSubPrdNtwkClmOutsideUAE("Y");
	                        	globalVO.setGlobClmSubPrdMemClmWithinUAE("Y");
	                        	globalVO.setGlobClmSubPrdMemClmOutsideUAE("Y");
	                			
	                		}else if("2".equals(strCondTypeDefault)){
	                			
	                		   	globalVO.setGlobMemberStatus("NC");
	                        	globalVO.setGlobPreauthAdmisnPeriodWithinUAE("NC");
	                        	globalVO.setGlobPreauthAdmisnPeriodOutsideUAE("NC");
	                        	globalVO.setGlobClmSubPrdNtwkClmWithinUAE("NC");
	                        	globalVO.setGlobClmSubPrdNtwkClmOutsideUAE("NC");
	                        	globalVO.setGlobClmSubPrdMemClmWithinUAE("NC");
	                        	globalVO.setGlobClmSubPrdMemClmOutsideUAE("NC");
	                			
	                		}
	                	   
	                   }
	            		
	            		// enhancement part end
	            		
	            		
	            		
	            		if("SPCB".equalsIgnoreCase(strBenefitID)){
	   
	            			if("1".equals(strCondTypeDefault)){
	            		    	
	            		    	 spclBeneVO=new HmoSpcbVO();
	            		    	 spclBeneVO.setPolicyNo(policyNm);
	            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	            		    	 spclBeneVO.setProductName(policyvo.getProductName());
	            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	            		    	 
	            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
	            		    	 
	            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
	            		    	 spclBeneVO.setOvrlPplAED("Y");
	            		    	 spclBeneVO.setOvrlCopayPercent("Y");
	            		    	 spclBeneVO.setOvrlDeductibleAED("Y");
	            		    	 
	            		      	 spclBeneVO.setOvrlCopDedMINMAX("Y");
	            		    	 spclBeneVO.setOthrClmType("Y");
	            		    	 spclBeneVO.setOthrNtwType("Y");
	            		    	 spclBeneVO.setOthrEncBenTypeId("Y");
	            		    	 spclBeneVO.setOthrPreAprvReqd("Y");
	            		    	 spclBeneVO.setOthrPAL("Y");
	            		    	 spclBeneVO.setOthrGeoLoc("Y");
	            		    	 spclBeneVO.setOthrconCov("Y");
	            		    	 
	            		    	 spclBeneVO.setOthrEmrID("Y");
	            		    	 spclBeneVO.setOthrProType("Y");
	            		    	 spclBeneVO.setOthrProFacType("Y");
	            		    	 spclBeneVO.setOthrPplAED("Y");
	            		    	 spclBeneVO.setOthrCopayPercent("Y");
	            		    	 spclBeneVO.setOthrDeductibleAED("Y");
	            		    	 spclBeneVO.setOthrCopDedMINMAX("Y");
	            		    	 spclBeneData.add(spclBeneVO);
	            		    	
	            		    }else if("2".equals(strCondTypeDefault)){
	            		    	
	            		    	 spclBeneVO=new HmoSpcbVO();
	            		    	 spclBeneVO.setPolicyNo(policyNm);
	            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	            		    	 spclBeneVO.setProductName(policyvo.getProductName());
	            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	            		    	 
	            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
	            		    	 
	            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
	            		    	 spclBeneVO.setOvrlPplAED("NC");
	            		    	 spclBeneVO.setOvrlCopayPercent("NC");
	            		    	 spclBeneVO.setOvrlDeductibleAED("NC");
	            		    	 
	            		      	 spclBeneVO.setOvrlCopDedMINMAX("NC");
	            		    	 spclBeneVO.setOthrClmType("NC");
	            		    	 spclBeneVO.setOthrNtwType("NC");
	            		    	 spclBeneVO.setOthrEncBenTypeId("NC");
	            		    	 spclBeneVO.setOthrPreAprvReqd("NC");
	            		    	 spclBeneVO.setOthrPAL("NC");
	            		    	 spclBeneVO.setOthrGeoLoc("NC");
	            		    	 spclBeneVO.setOthrconCov("NC");
	            		    	 
	            		    	 spclBeneVO.setOthrEmrID("NC");
	            		    	 spclBeneVO.setOthrProType("NC");
	            		    	 spclBeneVO.setOthrProFacType("NC");
	            		    	 spclBeneVO.setOthrPplAED("NC");
	            		    	 spclBeneVO.setOthrCopayPercent("NC");
	            		    	 spclBeneVO.setOthrDeductibleAED("NC");
	            		    	 spclBeneVO.setOthrCopDedMINMAX("NC");
	            		    	 spclBeneData.add(spclBeneVO);
	            		    	
	            		    }else{
	            		    	
	            		    	
	            		    	investigationRuleVO.setBenefitID("SPCB");
	            		    	
	            		    	if("S1".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    
	            		    	        investigationRuleVO.setInvsType("ATMT");
	            		    	     
	            		    	        ArrayList<InvestigationRuleVO> spcbATMTData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    	        
	            		    	        if(spcbATMTData.size()>0){
	            		    	        	
	            		    	        	for (InvestigationRuleVO investigationRuleVO2 : spcbATMTData) {
	            		    	        		
	            		    	        		spclBeneVO=new HmoSpcbVO();
	            		    	        		
	            		    	        		
	            		    	              	 if("SAY".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())|| "SAH".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())||"OSP".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())||"CHM".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())||"SAN".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())||"ACU".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())||"ATR".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())||"CHP".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())) {
	            		    	               		 
	            		    	               		 if("SAY".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())){
	            		    	               			 
	            		    	               			 spclBeneVO.setSpecialBenefits("Alternate Treatments-Ayurvedic");
	            		    	               			 spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO2.getEncounterTypes(), "EncSPCB", "ALT"));
	            		    	               			 
	            		    	               		 }else if("SAH".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())){
	            		    	               			 
	            		    	               			 spclBeneVO.setSpecialBenefits("Alternate Treatments-Homeopathy");
	            		    	               			 spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO2.getEncounterTypes(), "EncSPCB", "ALT"));
	            		    	               			 
	            		    	               		 }else if("OSP".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())){
	            		    	               			 
	            		    	               			 spclBeneVO.setSpecialBenefits("Alternate Treatments-Osteopathy");
	            		    	               			 spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO2.getEncounterTypes(), "EncSPCB", "ALT"));
	            		    	               			 
	            		    	               		 }else if("CHM".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())){
	            		    	               			 
	            		    	               			 spclBeneVO.setSpecialBenefits("Alternate Treatments-Chinese herbal medicine");
	            		    	               			 spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO2.getEncounterTypes(), "EncSPCB", "ALT"));
	            		    	               			 
	            		    	               		 }else if("SAN".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())){
	            		    	               			 
	            		    	               			 spclBeneVO.setSpecialBenefits("Alternate Treatments-Naturopathy");
	            		    	               			 spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO2.getEncounterTypes(), "EncSPCB", "ALT"));
	            		    	               			 
	            		    	               		 }else if("ACU".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())){
	            		    	               			 
	            		    	               			 spclBeneVO.setSpecialBenefits("Alternate Treatments-Acupuncture");
	            		    	               			 spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO2.getEncounterTypes(), "EncSPCB", "ALT"));
	            		    	               			 
	            		    	               		 }else if("ATR".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())){
	            		    	               			 
	            		    	               			 spclBeneVO.setSpecialBenefits("Alternate Treatments-Alternate Treatments");
	            		    	               			 spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO2.getEncounterTypes(), "EncSPCB", "ALT"));
	            		    	               			 
	            		    	               		 }else if("CHP".equalsIgnoreCase(investigationRuleVO2.getActMasterCode())){
	            		    	               			 
	            		    	               			 spclBeneVO.setSpecialBenefits("Alternate Treatments-Chiropractic");
	            		    	               			 spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO2.getEncounterTypes(), "EncSPCB", "ALT"));
	            		    	               			 
	            		    	               		 }
	            		    	
	   			            		    	 spclBeneVO.setPolicyNo(policyNm);
	   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
	   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	   			            		    	 
	   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
	   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
	   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
	   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
	   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
	   			            		      	 
	   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO2.getClaimType()));
	   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO2.getNetworkYN()));
	   			            		       	 
	   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO2.getPreApprvReqYN())?"YES":"NO");
	   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO2.getPreApprvLimit());
	   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO2.getGeoLocationID(),"Geo",null));
	   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO2.getCountryIDs(),"Con",investigationRuleVO2.getGeoLocationID()));
	   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO2.getEmiratesIDs(),"Emr",investigationRuleVO2.getCountryIDs()));
	   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO2.getProviderTypes()));
	   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO2.getProviderTypesID(),"Pro",null));
	   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO2.getPerPolicyLimit());
	   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO2.getOvrCopay());
	   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO2.getOvrDeductible());
	   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO2.getOvrMinMaxFlag());
	   			            		       	 
	   			            		       	 
	   			            		       	 spclBeneData.add(spclBeneVO);
												
											}
	            		    	              	 
	            		    	        	}
	            		    	        	
	            		    	        }else{
	            		    	        	
	            		    	       	 spclBeneVO=new HmoSpcbVO();
			            		    	 spclBeneVO.setPolicyNo(policyNm);
			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
			            		    	 
			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
			            		    	 
			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
			            		      	 
			            		      	spclBeneData.add(spclBeneVO);
	            		    	        	
	            		    	        }
	            		    		
	            		    	}else if("S2".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		    investigationRuleVO.setInvsType("DEOT");
	            		    	        ArrayList<InvestigationRuleVO> spcbDEOTData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    	        
	            		    	        if(spcbDEOTData.size()>0){
	            		    	        	
	            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbDEOTData) {
	            		    	        		
	            		    	        		 spclBeneVO=new HmoSpcbVO();
	            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
		   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
		   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
		   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
		   			            		    	 
		   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
		   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
		   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
		   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
		   			            		    	 
		   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
		   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
		   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
		   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
		   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
		   			            		      	 
		   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "DEX"));
		   			            		      	 
		   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
		   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
		   			            		       	 
		   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
		   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
		   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
		   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
		   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
		   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
		   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
		   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
		   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
		   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
		   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
		   			            		       	 
		   			            		       	 
		   			            		       	 spclBeneData.add(spclBeneVO);
												
											}
	            		    	        	
	            		    	        }else{
	            		    	        	
	            		    	          	 spclBeneVO=new HmoSpcbVO();
				            		    	 spclBeneVO.setPolicyNo(policyNm);
				            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
				            		    	 spclBeneVO.setProductName(policyvo.getProductName());
				            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
				            		    	 
				            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
				            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
				            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
				            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
				            		    	 
				            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
				            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
				            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
				            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
				            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
				            		      	 
				            		      	spclBeneData.add(spclBeneVO);
	            		    	        	
	            		    	        }
	            		    		
	            		    	}else if("S3".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		investigationRuleVO.setInvsType("EYCR");
	            		            ArrayList<InvestigationRuleVO> spcbEYCRData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    		
            		    	        if(spcbEYCRData.size()>0){
            		    	        	
            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbEYCRData) {
            		    	        		
            		    	        		 spclBeneVO=new HmoSpcbVO();
            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
	   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
	   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	   			            		    	 
	   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
	   			            		    	 
	   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
	   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
	   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
	   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
	   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
	   			            		      	 
	   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "EYE"));
	   			            		      	 
	   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
	   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
	   			            		       	 
	   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
	   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
	   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
	   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
	   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
	   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
	   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
	   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
	   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
	   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
	   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
	   			            		       	 
	   			            		       	 
	   			            		       	 spclBeneData.add(spclBeneVO);
											
										}
            		    	        	
            		    	        }else{
            		    	        	
            		    	          	 spclBeneVO=new HmoSpcbVO();
			            		    	 spclBeneVO.setPolicyNo(policyNm);
			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
			            		    	 
			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
			            		    	 
			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
			            		      	 
			            		      	spclBeneData.add(spclBeneVO);
            		    	        	
            		    	        }
	            		            
	            		            
	            		    	}else if("S4".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		investigationRuleVO.setInvsType("GNGY");
	            		            ArrayList<InvestigationRuleVO> spcbGNGYData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    		
            		    	        if(spcbGNGYData.size()>0){
            		    	        	
            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbGNGYData) {
            		    	        		
            		    	        		 spclBeneVO=new HmoSpcbVO();
            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
	   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
	   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	   			            		    	 
	   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
	   			            		    	 
	   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
	   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
	   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
	   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
	   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
	   			            		      	 
	   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "GYC"));
	   			            		      	 
	   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
	   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
	   			            		       	 
	   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
	   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
	   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
	   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
	   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
	   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
	   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
	   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
	   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
	   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
	   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
	   			            		       	 
	   			            		       	 
	   			            		       	 spclBeneData.add(spclBeneVO);
											
										}
            		    	        	
            		    	        }else{
            		    	        	
            		    	          	 spclBeneVO=new HmoSpcbVO();
			            		    	 spclBeneVO.setPolicyNo(policyNm);
			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
			            		    	 
			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
			            		    	 
			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
			            		      	 
			            		      	spclBeneData.add(spclBeneVO);
            		    	        	
            		    	        }
	            		            
	            		            
	            		    	}else if("S5".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		investigationRuleVO.setInvsType("MISG");
	            		            ArrayList<InvestigationRuleVO> spcbMISGData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    		
            		    	        if(spcbMISGData.size()>0){
            		    	        	
            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbMISGData) {
            		    	        		
            		    	        		 spclBeneVO=new HmoSpcbVO();
            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
	   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
	   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	   			            		    	 
	   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
	   			            		    	 
	   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
	   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
	   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
	   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
	   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
	   			            		      	 
	   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "MIS"));
	   			            		      	 
	   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
	   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
	   			            		       	 
	   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
	   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
	   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
	   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
	   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
	   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
	   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
	   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
	   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
	   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
	   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
	   			            		       	 
	   			            		       	 
	   			            		       	 spclBeneData.add(spclBeneVO);
											
										}
            		    	        	
            		    	        }else{
            		    	        	
            		    	          	 spclBeneVO=new HmoSpcbVO();
			            		    	 spclBeneVO.setPolicyNo(policyNm);
			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
			            		    	 
			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
			            		    	 
			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
			            		      	 
			            		      	spclBeneData.add(spclBeneVO);
            		    	        	
            		    	        }
	            		            
	            		    	}else if("S6".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		    investigationRuleVO.setInvsType("NASL");
	            		    	        ArrayList<InvestigationRuleVO> spcbNASLData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    
	            		    	        if(spcbNASLData.size()>0){
	            		    	        	
	            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbNASLData) {
	            		    	        		
	            		    	        		 spclBeneVO=new HmoSpcbVO();
	            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
		   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
		   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
		   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
		   			            		    	 
		   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
		   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
		   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
		   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
		   			            		    	 
		   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
		   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
		   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
		   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
		   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
		   			            		      	 
		   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "NAS"));
		   			            		      	 
		   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
		   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
		   			            		       	 
		   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
		   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
		   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
		   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
		   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
		   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
		   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
		   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
		   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
		   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
		   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
		   			            		       	 
		   			            		       	 
		   			            		       	 spclBeneData.add(spclBeneVO);
												
											}
	            		    	        	
	            		    	        }else{
	            		    	        	
	            		    	          	 spclBeneVO=new HmoSpcbVO();
				            		    	 spclBeneVO.setPolicyNo(policyNm);
				            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
				            		    	 spclBeneVO.setProductName(policyvo.getProductName());
				            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
				            		    	 
				            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
				            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
				            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
				            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
				            		    	 
				            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
				            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
				            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
				            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
				            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
				            		      	 
				            		      	spclBeneData.add(spclBeneVO);
	            		    	        	
	            		    	        }
	            		    	        
	            		    	        
	            		    	}else if("S7".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		investigationRuleVO.setInvsType("ONGY");
	            		            ArrayList<InvestigationRuleVO> spcbONGYData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    		
            		    	        if(spcbONGYData.size()>0){
            		    	        	
            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbONGYData) {
            		    	        		
            		    	        		 spclBeneVO=new HmoSpcbVO();
            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
	   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
	   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	   			            		    	 
	   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
	   			            		    	 
	   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
	   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
	   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
	   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
	   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
	   			            		      	 
	   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "ONC"));
	   			            		      	 
	   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
	   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
	   			            		       	 
	   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
	   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
	   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
	   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
	   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
	   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
	   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
	   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
	   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
	   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
	   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
	   			            		       	 
	   			            		       	 
	   			            		       	 spclBeneData.add(spclBeneVO);
											
										}
            		    	        	
            		    	        }else{
            		    	        	
            		    	          	 spclBeneVO=new HmoSpcbVO();
			            		    	 spclBeneVO.setPolicyNo(policyNm);
			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
			            		    	 
			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
			            		    	 
			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
			            		      	 
			            		      	spclBeneData.add(spclBeneVO);
            		    	        	
            		    	        }
	            		            
	            		    	}else if("S8".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		    investigationRuleVO.setInvsType("OTTR");
	            		    	        ArrayList<InvestigationRuleVO> spcbOTTRData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    		
	            		    	        if(spcbOTTRData.size()>0){
	            		    	        	
	            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbOTTRData) {
	            		    	        		
	            		    	        		 spclBeneVO=new HmoSpcbVO();
	            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
		   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
		   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
		   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
		   			            		    	 
		   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
		   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
		   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
		   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
		   			            		    	 
		   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
		   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
		   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
		   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
		   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
		   			            		      	 
		   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "OTR"));
		   			            		      	 
		   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
		   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
		   			            		       	 
		   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
		   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
		   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
		   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
		   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
		   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
		   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
		   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
		   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
		   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
		   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
		   			            		       	 
		   			            		       	 
		   			            		       	 spclBeneData.add(spclBeneVO);
												
											}
	            		    	        	
	            		    	        }else{
	            		    	        	
	            		    	          	 spclBeneVO=new HmoSpcbVO();
				            		    	 spclBeneVO.setPolicyNo(policyNm);
				            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
				            		    	 spclBeneVO.setProductName(policyvo.getProductName());
				            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
				            		    	 
				            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
				            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
				            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
				            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
				            		    	 
				            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
				            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
				            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
				            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
				            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
				            		      	 
				            		      	spclBeneData.add(spclBeneVO);
	            		    	        	
	            		    	        }
	            		    	        
	            		    	}else if("S9".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		  investigationRuleVO.setInvsType("PSYC");
	            		    	        ArrayList<InvestigationRuleVO> spcbPSYCData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    	        if(spcbPSYCData.size()>0){
	            		    	        	
	            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbPSYCData) {
	            		    	        		
	            		    	        		 spclBeneVO=new HmoSpcbVO();
	            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
		   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
		   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
		   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
		   			            		    	 
		   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
		   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
		   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
		   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
		   			            		    	 
		   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
		   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
		   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
		   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
		   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
		   			            		      	 
		   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "SYT"));
		   			            		      	 
		   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
		   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
		   			            		       	 
		   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
		   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
		   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
		   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
		   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
		   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
		   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
		   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
		   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
		   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
		   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
		   			            		       	 
		   			            		       	 
		   			            		       	 spclBeneData.add(spclBeneVO);
												
											}
	            		    	        	
	            		    	        }else{
	            		    	        	
	            		    	          	 spclBeneVO=new HmoSpcbVO();
				            		    	 spclBeneVO.setPolicyNo(policyNm);
				            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
				            		    	 spclBeneVO.setProductName(policyvo.getProductName());
				            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
				            		    	 
				            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
				            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
				            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
				            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
				            		    	 
				            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
				            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
				            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
				            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
				            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
				            		      	 
				            		      	spclBeneData.add(spclBeneVO);
	            		    	        	
	            		    	        }
	            		    	        
	            		    	}else if("S10".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		investigationRuleVO.setInvsType("RNDL");
	            		            ArrayList<InvestigationRuleVO> spcbRNDLData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    		
            		    	        if(spcbRNDLData.size()>0){
            		    	        	
            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbRNDLData) {
            		    	        		
            		    	        		 spclBeneVO=new HmoSpcbVO();
            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
	   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
	   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	   			            		    	 
	   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
	   			            		    	 
	   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
	   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
	   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
	   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
	   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
	   			            		      	 
	   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "RND"));
	   			            		      	 
	   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
	   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
	   			            		       	 
	   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
	   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
	   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
	   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
	   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
	   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
	   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
	   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
	   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
	   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
	   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
	   			            		       	 
	   			            		       	 
	   			            		       	 spclBeneData.add(spclBeneVO);
											
										}
            		    	        	
            		    	        }else{
            		    	        	
            		    	          	 spclBeneVO=new HmoSpcbVO();
			            		    	 spclBeneVO.setPolicyNo(policyNm);
			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
			            		    	 
			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
			            		    	 
			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
			            		      	 
			            		      	spclBeneData.add(spclBeneVO);
            		    	        	
            		    	        }
	            		            
	            		    	}else if("S11".equalsIgnoreCase(sbNode.valueOf("@id"))){
	            		    		
	            		    		investigationRuleVO.setInvsType("VCIN");
	            		            ArrayList<InvestigationRuleVO> spcbVCINData=ruleManagerObject.getSpecialBenefitsDetails(investigationRuleVO);
	            		    		
            		    	        if(spcbVCINData.size()>0){
            		    	        	
            		    	        	for (InvestigationRuleVO investigationRuleVO3 : spcbVCINData) {
            		    	        		
            		    	        		 spclBeneVO=new HmoSpcbVO();
            		    	 		    	 spclBeneVO.setPolicyNo(policyNm);
	   			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
	   			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
	   			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
	   			            		    	 
	   			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
	   			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
	   			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
	   			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
	   			            		    	 
	   			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
	   			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
	   			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
	   			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
	   			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
	   			            		      	 
	   			                 		     spclBeneVO.setOthrEncBenTypeId(keyConverter1(investigationRuleVO3.getEncounterTypes(), "EncSPCB", "VCC"));
	   			            		      	 
	   			            		    	 spclBeneVO.setOthrClmType(unitConverter(investigationRuleVO3.getClaimType()));
	   			            		       	 spclBeneVO.setOthrNtwType(unitConverter(investigationRuleVO3.getNetworkYN()));
	   			            		       	 
	   			            		       	 spclBeneVO.setOthrPreAprvReqd("Y".equals(investigationRuleVO3.getPreApprvReqYN())?"YES":"NO");
	   			            		       	 spclBeneVO.setOthrPAL(investigationRuleVO3.getPreApprvLimit());
	   			            		       	 spclBeneVO.setOthrGeoLoc(keyConverter1(investigationRuleVO3.getGeoLocationID(),"Geo",null));
	   			            		         spclBeneVO.setOthrconCov(keyConverter1(investigationRuleVO3.getCountryIDs(),"Con",investigationRuleVO3.getGeoLocationID()));
	   			            		       	 spclBeneVO.setOthrEmrID(keyConverter1(investigationRuleVO3.getEmiratesIDs(),"Emr",investigationRuleVO3.getCountryIDs()));
	   			            		       	 spclBeneVO.setOthrProType(unitConverter(investigationRuleVO3.getProviderTypes()));
	   			            		       	 spclBeneVO.setOthrProFacType(keyConverter1(investigationRuleVO3.getProviderTypesID(),"Pro",null));
	   			            		       	 spclBeneVO.setOthrPplAED(investigationRuleVO3.getPerPolicyLimit());
	   			            		       	 spclBeneVO.setOthrCopayPercent(investigationRuleVO3.getOvrCopay());
	   			            		       	 spclBeneVO.setOthrDeductibleAED(investigationRuleVO3.getOvrDeductible());
	   			            		       	 spclBeneVO.setOthrCopDedMINMAX(investigationRuleVO3.getOvrMinMaxFlag());
	   			            		       	 
	   			            		       	 
	   			            		       	 spclBeneData.add(spclBeneVO);
											
										}
            		    	        	
            		    	        }else{
            		    	        	
            		    	          	 spclBeneVO=new HmoSpcbVO();
			            		    	 spclBeneVO.setPolicyNo(policyNm);
			            		    	 spclBeneVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
			            		    	 spclBeneVO.setProductName(policyvo.getProductName());
			            		    	 spclBeneVO.setCorporateGroupID(policyvo.getCorporateGroupID());
			            		    	 
			            		    	 spclBeneVO.setProductNetworkCategory(policyvo.getProductNetworkCategory());
			            		    	 spclBeneVO.setCorporateGroupName(policyvo.getCorporateGroupName());
			            		    	 spclBeneVO.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
			            		    	 spclBeneVO.setSumInsured(policyvo.getSumInsured());
			            		    	 
			            		    	 spclBeneVO.setSpecialBenefits(sbNode.valueOf("@name"));
			            		    	 spclBeneVO.setOvrlPplAED(spcbStringArray[0]);
			            		    	 spclBeneVO.setOvrlCopayPercent(spcbStringArray[1]);
			            		    	 spclBeneVO.setOvrlDeductibleAED(spcbStringArray[2]);
			            		      	 spclBeneVO.setOvrlCopDedMINMAX(spcbStringArray[3]);
			            		      	 
			            		      	spclBeneData.add(spclBeneVO);
            		    	        	
            		    	        }
	            		            
	            		    	}
	            		    	
	            		    }
	            			
	            			// new end
	            			
	            			//spcbOuterData.add(spcbStringArray);
	            		}
	            
	            		
	            	}
	          }
		
    		}
    		
    		if(benefitList.contains("GLOB")){
    			
    			hmoDataMap.put("GLOB", globalVO);
    			
    		}
if(benefitList.contains("OPTC")){
	
	hmoDataMap.put("OPTC", opticaVo);
    			
    		}
if(benefitList.contains("DNTL")){
	
	hmoDataMap.put("DNTL", dentalVo);
	
}
if(benefitList.contains("GNRX")){
	
	hmoDataMap.put("GNRX", exclusionData);
	
}

if(benefitList.contains("SPCB")){
	
	hmoDataMap.put("SPCB", spclBeneData);
	
}


    		

         	
         	
             ArrayList pal=new ArrayList();
             ArrayList ppl=new ArrayList();
             ArrayList pcl=new ArrayList();
             ArrayList noOfSess=new ArrayList();
             ArrayList ppc=new ArrayList();
             ArrayList pcc=new ArrayList();
             ArrayList pft=new ArrayList();
             
            Set<String> s=null;
         	String flag=null;
         	
         	 Integer palSize=null; Integer pplSize=null; Integer pclSize=null; Integer noOfSessSize=null; 
             Integer ppcSize=null; Integer pccSize=null; Integer pftSize=null;
             
             List<Integer> maxNum=new ArrayList<Integer>();
             ArrayList<String> setList=null; 
     

	if(benefitList.contains("OPTS")){
		
		 // OPTS configuration data fetching started
		// enhance start
		
		ArrayList<InvestigationRuleVO> consultData=null;
		Integer consultRows=0;
		
		 ArrayList<InvestigationRuleVO> invsData= null;
		 Integer invsRowsCount = 0;
		 Integer invstRows= 0;
		 
		 ArrayList<InvestigationRuleVO> physData=null;
		 Integer physRows= 0;
		 
		 ArrayList<InvestigationRuleVO> phrmData= null;
		 Integer phrmRows= 0;

		// enhance end
 		
        investigationRuleVO.setBenefitID("OPTS");
  
        
        if("PC".equals(outPatientVO.getOpConsOpt())){
        	
        	  investigationRuleVO.setInvsType("CONS");
              consultData=ruleManagerObject.getConsdDetails(investigationRuleVO);
              consultRows= consultData.size();
        }
        
        if("PC".equals(outPatientVO.getOpPhyOpt())){
        	
        	 investigationRuleVO.setInvsType("PHYS");
             physData=ruleManagerObject.getPhysioDetails(investigationRuleVO);
             physRows= physData.size();
      }
        
        if("PC".equals(outPatientVO.getOpPhrmOpt())){
        	
          investigationRuleVO.setInvsType("PHRM");
          phrmData=ruleManagerObject.getPharmaDetails(investigationRuleVO);
          phrmRows= phrmData.size();
      }
        
        if("PC".equals(opOuterData.get("InvOpt"))){
        	
        	 investigationRuleVO.setInvsType("INVS");
            invsData=ruleManagerObject.getInvestdDetails(investigationRuleVO);
            invsRowsCount = invsData.size();
            

            for (InvestigationRuleVO investigationRuleVO2 : invsData) {
            	
            	if(!investigationRuleVO2.getPreApprvLimit().equals("")){
            		
            		pal.add(investigationRuleVO2.getPreApprvLimit());
            		
            	}
               if(!investigationRuleVO2.getPerPolicyLimit().equals("")){

                    ppl.add(investigationRuleVO2.getPerPolicyLimit());
            		
            	}
               if(!investigationRuleVO2.getPerClaimLimit().equals("")){
            	   
            	   pcl.add(investigationRuleVO2.getPerClaimLimit());

                }
              if(!investigationRuleVO2.getNoOfSessAllowPerPolicy().equals("")){
            	  
            	  noOfSess.add(investigationRuleVO2.getNoOfSessAllowPerPolicy());

               }
             if(!investigationRuleVO2.getPerPolicyCpyDdctLimit().equals("")){
            	 
            	 ppc.add(investigationRuleVO2.getPerPolicyCpyDdctLimit());

                }
             if(!investigationRuleVO2.getPerClaimCpyDdctLimit().equals("")){
            	 
            	 pcc.add(investigationRuleVO2.getPerClaimCpyDdctLimit());

              }
            if(!investigationRuleVO2.getProviderTypes().equals("")){   // new
            	 
            	 pft.add(investigationRuleVO2.getProviderTypes());

              }
            	
      	
    		}
            
             palSize=pal.size();  pplSize=ppl.size();  pclSize=pcl.size();  noOfSessSize=noOfSess.size(); 
             ppcSize=ppc.size();  pccSize=pcc.size();  pftSize=pft.size();
        	
        	if(invsRowsCount==palSize){
        		
        		s=new HashSet<String>(pal);
        		flag="pal";
        		
        	}else if(invsRowsCount==pplSize){
        		
        		s=new HashSet<String>(ppl);
        		flag="ppl";
        		
        	}else if(invsRowsCount==pclSize){
        		
        		s=new HashSet<String>(pcl);
        		flag="pcl";
        		
        	}else if(invsRowsCount==noOfSessSize){
        		
        		s=new HashSet<String>(noOfSess);
        		flag="noOfSess";
        		
        	}else if(invsRowsCount==ppcSize){
        		
        		s=new HashSet<String>(ppc);
        		flag="ppc";
        		
        	}else if(invsRowsCount==pccSize){
        		
        		s=new HashSet<String>(pcc);
        		flag="pcc";
        		
        	}else if(invsRowsCount==pftSize){
        		
        		s=new HashSet<String>(pft);
        		flag="pft";
        	//	log.info("flag OP INVS:"+flag);
        	       
        		
        	}
     	 
            	 setList=new ArrayList<String>(s);
            	 invstRows= s.size();
      }
      

        
        maxNum.add(consultRows);
        maxNum.add(invstRows);
        maxNum.add(physRows);
        maxNum.add(phrmRows);
        
        Integer opClmCount=Collections.max(maxNum);

     	if(opClmCount>0){
     	
        for (int i = 0; i < opClmCount; i++) {
        	
        	if(i>0){
        		
        		 outPatientVO=new HmoOutPatientVO();
             	// setting common data of OP
             	outPatientVO.setPolicyNo(policyNm);
             	outPatientVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
             	outPatientVO.setProductName(policyvo.getProductName());
             	outPatientVO.setCorporateGroupID(policyvo.getCorporateGroupID());
             	
             	if("PC".equals(opOuterData.get("OpOpt"))){
             		
             	 	outPatientVO.setOpPPL(opOuterData.get("OP_PPL"));
                 	outPatientVO.setOpChronicPPL(opOuterData.get("OP_CHRONIC_PPL"));
                 	outPatientVO.setOpPedPPL(opOuterData.get("OP_PED_PPL"));
                 	outPatientVO.setOpChronPlusPedPPL(opOuterData.get("OP_CHRON_PLUS_PED_PPL"));
             		
             	}
             	
                if("PC".equals(opOuterData.get("InvOpt"))){
                	
                	outPatientVO.setOpOverallInvstPPL(opOuterData.get("OP_OVERALL_INVST_PPL"));
                 	outPatientVO.setOpLabAndPathPPL(opOuterData.get("OP_LAB_AND_PATH_PPL"));
                 	outPatientVO.setOpExceptLabAndPathPPL(opOuterData.get("OP_EXCEPT_LAB_AND_PATH_PPL"));
             		
             	}
            
             	
             
        		
        	}
        	
        
    
        	
        	if(i<consultRows){
        		
               	InvestigationRuleVO investigationRuleVO1=(InvestigationRuleVO)consultData.get(i);
            	
            	outPatientVO.setOPConsultGeographicalLocation(keyConverter1(investigationRuleVO1.getGeoLocationID(),"Geo",null));
            	outPatientVO.setOPConsultCountriesCovered(keyConverter1(investigationRuleVO1.getCountryIDs(),"Con",investigationRuleVO1.getGeoLocationID()));
            	outPatientVO.setOPConsultEncounterType(keyConverter1(investigationRuleVO1.getEncounterTypes(),"Enc","OPTS"));
            	outPatientVO.setOPConsultProviderTypes(keyConverter1(investigationRuleVO1.getProviderTypes(),"Pro",null));
            	outPatientVO.setOPConsultPpl(investigationRuleVO1.getPerPolicyLimit());
            	
            	
            	outPatientVO.setOPConsultEmiratesCovered(keyConverter1(investigationRuleVO1.getEmiratesIDs(),"Emr",investigationRuleVO1.getCountryIDs()));
            	
            	String strClinCopayDetails=investigationRuleVO1.getClinicianCopayDetails();
            	String opClncConsYN=investigationRuleVO1.getClncConsYN();
            	String OvrCopay = investigationRuleVO1.getOvrCopay();
      
            	if("Y".equalsIgnoreCase(opClncConsYN)){
            		
            		boolean OpClncConsGPH= false;
            		boolean OpClncConsSPH= false;
            		
              	     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
            	    	 
            	    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
            	    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
            	    	
            	    	 for(String copayDetails:arrrClinCopayDetails){
            	    		 
            	    		 String arrGenIds[]=copayDetails.split("[@]");
            	    		 if(arrGenIds!=null&&arrGenIds.length>0){
            	    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
            	    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
            	    			 if("GPH".equalsIgnoreCase(arrGenIds[0])){
            	    				 OpClncConsGPH= true;
            	    				 outPatientVO.setOPGpConsultCopay(arrCpDetails[0]);
            	    				 outPatientVO.setOPGpConsultDEDUCTABLE(arrCpDetails[1]);
            	    				 outPatientVO.setOPGpConsultMinMax(arrCpDetails[2]);
            	    			 }if("SPH".equalsIgnoreCase(arrGenIds[0])){
            	    				 OpClncConsSPH= true;
            	    				 outPatientVO.setOPSpConsultCopay(arrCpDetails[0]);
            	    				 outPatientVO.setOPSpConsultDEDUCTABLE(arrCpDetails[1]);
            	    				 outPatientVO.setOPSpConsultMinMax(arrCpDetails[2]);
            	    				 
            	    			 }
            	    	
            	    		 
            	    		 }
            	    		 }
            	    	 }
            	    }
            	     }
              	     
              	     if(OpClncConsGPH==false){
              	    	 
              	    	 outPatientVO.setOPGpConsultCopay(investigationRuleVO1.getOvrCopay());
	    				 outPatientVO.setOPGpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
	    				 outPatientVO.setOPGpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
              	    	 
              	     }
              	     
              	   if(OpClncConsSPH==false){
            	    	 
            	    	 outPatientVO.setOPSpConsultCopay(investigationRuleVO1.getOvrCopay());
	    				 outPatientVO.setOPSpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
	    				 outPatientVO.setOPSpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
            	    	 
            	     }
          		
            		
            	}else{
            		
              	     outPatientVO.setOPGpConsultCopay(investigationRuleVO1.getOvrCopay());
    				 outPatientVO.setOPGpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
    				 outPatientVO.setOPGpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
    				 outPatientVO.setOPSpConsultCopay(investigationRuleVO1.getOvrCopay());
    				 outPatientVO.setOPSpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
    				 outPatientVO.setOPSpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
            		
            	}
    
            	outPatientVO.setOpConsultFlwPeriod(investigationRuleVO1.getFollowUpPeriod());
            	outPatientVO.setOpConsultFlwPeriodUnit(unitConverter(investigationRuleVO1.getFollowUpPeriodType()));
        		
        	}
   
        	/////////////////////// Cons End ////////////////////////////////////////////////////////////
        	
            /////////////////////// INVS Start ////////////////////////////////////////////////////////////

           	for (int j = 0; j < invsRowsCount; j++) {
        		
        		InvestigationRuleVO investigationRuleVOInvs=invsData.get(j);
        		
        		if(i<setList.size()){
        			
              		String string=setList.get(i);
            		
            		if("pal".equals(flag)){
            			
            			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPreApprvLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		outPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName()); 
        
                        		outPatientVO.setOpInvLabProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		outPatientVO.setOpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		outPatientVO.setOpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		outPatientVO.setOpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		outPatientVO.setOpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy()); 
                        		outPatientVO.setOpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		outPatientVO.setOpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		outPatientVO.setOpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		outPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		outPatientVO.setOpInvPatProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		outPatientVO.setOpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                            
                     		outPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());
                     		outPatientVO.setOpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                            
                     		outPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
                     		outPatientVO.setOpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		outPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                     		outPatientVO.setOpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		outPatientVO.setOpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		outPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
                     		outPatientVO.setOpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		outPatientVO.setOpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("ppl".equals(flag)){
            			
    			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerPolicyLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		outPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		outPatientVO.setOpInvLabProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		outPatientVO.setOpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		outPatientVO.setOpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		outPatientVO.setOpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		outPatientVO.setOpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		outPatientVO.setOpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		outPatientVO.setOpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		outPatientVO.setOpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		outPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		

                     		outPatientVO.setOpInvPatProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		outPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		outPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		outPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		outPatientVO.setOpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		outPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		outPatientVO.setOpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		} else if("pcl".equals(flag)){
            			
    			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerClaimLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		outPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		outPatientVO.setOpInvLabProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		outPatientVO.setOpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		outPatientVO.setOpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		outPatientVO.setOpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		outPatientVO.setOpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		outPatientVO.setOpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		outPatientVO.setOpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		outPatientVO.setOpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		outPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		
                     		outPatientVO.setOpInvPatProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		outPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		outPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		outPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		outPatientVO.setOpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		outPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		outPatientVO.setOpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("noOfSess".equals(flag)){
            			
    			if(string.equalsIgnoreCase(investigationRuleVOInvs.getNoOfSessAllowPerPolicy())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		outPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		outPatientVO.setOpInvLabProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		outPatientVO.setOpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		outPatientVO.setOpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		outPatientVO.setOpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit());
                        		outPatientVO.setOpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		outPatientVO.setOpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		outPatientVO.setOpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		outPatientVO.setOpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		outPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		
                     		outPatientVO.setOpInvPatProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		outPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		outPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		outPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		outPatientVO.setOpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		outPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		outPatientVO.setOpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		outPatientVO.setOpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("ppc".equals(flag)){
            			
    			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerPolicyCpyDdctLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		outPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		outPatientVO.setOpInvLabProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		outPatientVO.setOpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		outPatientVO.setOpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		outPatientVO.setOpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit());
                        		outPatientVO.setOpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		outPatientVO.setOpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		outPatientVO.setOpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		outPatientVO.setOpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		outPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvPatProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		outPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		outPatientVO.setOpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		outPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		outPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		outPatientVO.setOpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		outPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		outPatientVO.setOpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("pcc".equals(flag)){
            			
    			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerClaimCpyDdctLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		outPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		outPatientVO.setOpInvLabProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		outPatientVO.setOpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		outPatientVO.setOpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		outPatientVO.setOpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		outPatientVO.setOpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		outPatientVO.setOpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		outPatientVO.setOpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		outPatientVO.setOpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		outPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		

                     		outPatientVO.setOpInvPatProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		outPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		outPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		outPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		outPatientVO.setOpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		outPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		outPatientVO.setOpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("pft".equals(flag)){
            			
    			if(string.equalsIgnoreCase(investigationRuleVOInvs.getProviderTypes())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		outPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		outPatientVO.setOpInvLabProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		outPatientVO.setOpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		outPatientVO.setOpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		outPatientVO.setOpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		outPatientVO.setOpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		outPatientVO.setOpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		outPatientVO.setOpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		outPatientVO.setOpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		outPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		

                     		outPatientVO.setOpInvPatProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		outPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		outPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());

                     		outPatientVO.setOpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		outPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		outPatientVO.setOpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		outPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		outPatientVO.setOpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		outPatientVO.setOpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		outPatientVO.setOpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		outPatientVO.setOpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		outPatientVO.setOpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		outPatientVO.setOpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		outPatientVO.setOpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}
        			
        		}
        		
  
				
			}
           	

        	
           /////////////////////// INVS End ////////////////////////////////////////////////////////////
        	
        	
            /////////////////////// PHYSIO Start ////////////////////////////////////////////////////////////
        	
        	if(i<physRows){
        		
        		InvestigationRuleVO investigationRuleVOPhys=(InvestigationRuleVO)physData.get(i);
            	
            	outPatientVO.setOpPhysioProviderFacilityTypes(keyConverter1(investigationRuleVOPhys.getProviderTypes(),"Pro",null));
            	outPatientVO.setOpPhysioPreapprovalReqdYesNo("Y".equals(investigationRuleVOPhys.getPreApprvReqYN())?"YES":"NO");
            	outPatientVO.setOpPhysioPreapprovalLimit(investigationRuleVOPhys.getPreApprvLimit());
            	outPatientVO.setOpPhysioPpl(investigationRuleVOPhys.getPerPolicyLimit());
            	outPatientVO.setOpPhysioNOOfSess(investigationRuleVOPhys.getNoOfSessAllowPerPolicy());
            	outPatientVO.setOpPhysioPerSessLimit(investigationRuleVOPhys.getLimitPerSession());
            	outPatientVO.setOpPhysioCopay(investigationRuleVOPhys.getOvrCopay());
            	outPatientVO.setOpPhysioOvrDeductable(investigationRuleVOPhys.getOvrDeductible());
            	outPatientVO.setOpPhysioCopdedMINMAX(investigationRuleVOPhys.getOvrMinMaxFlag());
        		
        	}
        	
        	
        	
        	
           /////////////////////// PHYSIO End ////////////////////////////////////////////////////////////
        	
           /////////////////////// PHARMA Start ////////////////////////////////////////////////////////////
        	
            if(i<phrmRows){
            	
            	
             	InvestigationRuleVO investigationRuleVOPhrm=(InvestigationRuleVO)phrmData.get(i);
            	
            	outPatientVO.setOpPharmactlProviderFacility(keyConverter1(investigationRuleVOPhrm.getProviderTypes(),"Pro",null));
            	outPatientVO.setOpPharmactlPreapprovalReqdYesNo("Y".equals(investigationRuleVOPhrm.getPreApprvReqYN())?"YES":"NO");
            	outPatientVO.setOpPharmactlPreapprovalLimit(investigationRuleVOPhrm.getPreApprvLimit());
            	outPatientVO.setOpPharmactlPpl(investigationRuleVOPhrm.getPerPolicyLimit());
            	outPatientVO.setOpPharmactlCopay(investigationRuleVOPhrm.getOvrCopay());
            	outPatientVO.setOpPharmactlDeductable(investigationRuleVOPhrm.getOvrDeductible());
            	outPatientVO.setOpPharmactlCopdedMINMAX(investigationRuleVOPhrm.getOvrMinMaxFlag());
        		
         }
        	
       
    
        	
        	
           /////////////////////// PHARMA End ////////////////////////////////////////////////////////////
			
	
      //========================================================================================================================   

        
        outPatientVOs.add(outPatientVO);
               
    	}
        
     	}else{
     		
     		outPatientVOs.add(outPatientVO);
     		
     	}
        
        // saving data govind for outpatient
   
        hmoDataMap.put("OPTS",outPatientVOs);    // outpatient complete data
        
     // OPTS configuration data fetching finished
        

    			
    		}  
	
	if(benefitList.contains("IPT")){
		
		 // IP configuration data fetching started
        
        investigationRuleVO.setBenefitID("IPT");
        
        ArrayList<InvestigationRuleVO> ipConsultData=null;
        Integer ipConsultDataSize=0;
        
        ArrayList<InvestigationRuleVO> ipCompData=null;
        Integer ipCompDataSize= 0;
        
        ArrayList<InvestigationRuleVO> ipInvsData= null;
        Integer ipInvsDataSize= 0;
        ArrayList<String> ipSetList= null;
        Integer ipInvsSetSize= 0;
        
        ArrayList<InvestigationRuleVO> ipPhysData= null;
        Integer ipPhysDataSize= 0;
        
        ArrayList<InvestigationRuleVO> ipAmblData= null;
        Integer ipAmblDataSize= 0;
        
        
        
        
        if("PC".equals(ipOuterData.get("IpConsOpt"))){
        	
        	 investigationRuleVO.setInvsType("CONS");
             ipConsultData=ruleManagerObject.getConsdDetails(investigationRuleVO);
             ipConsultDataSize=ipConsultData.size();
        }
        
        if("PC".equals(ipOuterData.get("IpCompChrgOpt"))){
        	
        	 investigationRuleVO.setInvsType("COMP");
             ipCompData=ruleManagerObject.getCompanDetails(investigationRuleVO);
             ipCompDataSize=ipCompData.size();
       }
        
    
        
        if("PC".equals(ipOuterData.get("IpPhyOpt"))){
        	
        	investigationRuleVO.setInvsType("PHYS");
            ipPhysData=ruleManagerObject.getPhysioDetails(investigationRuleVO);
            ipPhysDataSize=ipPhysData.size();
       }
        
        if("PC".equals(ipOuterData.get("IpAmbOpt"))){
        	
        	  investigationRuleVO.setInvsType("AMBL");
              ipAmblData=ruleManagerObject.getAmbulanceDetails(investigationRuleVO);
              ipAmblDataSize=ipAmblData.size();
       }
        
        if("PC".equals(ipOuterData.get("ipInvOpt"))){
        	
      	  investigationRuleVO.setInvsType("INVS");
            ipInvsData=ruleManagerObject.getInvestdDetails(investigationRuleVO);
            ipInvsDataSize=ipInvsData.size();
            
            if(pal!=null){
            	pal.clear();
            }
            
            if(ppl!=null){
            	ppl.clear();
            }
            
            if(pcl!=null){
            	 pcl.clear();
            }
           
            if(noOfSess!=null){
            	noOfSess.clear();
            }
            
            if(ppc!=null){
            	 ppc.clear();
            }
           
            if(pcc!=null){
            	pcc.clear();
            }
            
            if(s!=null){
            	s.clear();
            }
            if(setList!=null){
            	setList.clear();
            }
            if(pft!=null){
            	pft.clear();
            }
            
            for (InvestigationRuleVO investigationRuleVO2 : ipInvsData) {
            	
            	if(!investigationRuleVO2.getPreApprvLimit().equals("")){
            		
            		pal.add(investigationRuleVO2.getPreApprvLimit());
            		
            	}
               if(!investigationRuleVO2.getPerPolicyLimit().equals("")){

                    ppl.add(investigationRuleVO2.getPerPolicyLimit());
            		
            	}
               if(!investigationRuleVO2.getPerClaimLimit().equals("")){
            	   
            	   pcl.add(investigationRuleVO2.getPerClaimLimit());

                }
              if(!investigationRuleVO2.getNoOfSessAllowPerPolicy().equals("")){
            	  
            	  noOfSess.add(investigationRuleVO2.getNoOfSessAllowPerPolicy());

               }
             if(!investigationRuleVO2.getPerPolicyCpyDdctLimit().equals("")){
            	 
            	 ppc.add(investigationRuleVO2.getPerPolicyCpyDdctLimit());

                }
             if(!investigationRuleVO2.getPerClaimCpyDdctLimit().equals("")){
            	 
            	 pcc.add(investigationRuleVO2.getPerClaimCpyDdctLimit());

              }
           if(!investigationRuleVO2.getProviderTypes().equals("")){   // new
            	 
            	 pft.add(investigationRuleVO2.getProviderTypes());

              }
            	
      	
    		}
            
            
            
            
            palSize=pal.size(); pplSize=ppl.size(); pclSize=pcl.size();noOfSessSize=noOfSess.size();ppcSize=ppc.size();pccSize=pcc.size(); pftSize=pft.size();

        
        	flag=null;
        	if(ipInvsDataSize==palSize){
        		
        		s=new HashSet<String>(pal);
        		flag="pal";
        		
        	}else if(ipInvsDataSize==pplSize){
        		
        		s=new HashSet<String>(ppl);
        		flag="ppl";
        		
        	}else if(ipInvsDataSize==pclSize){
        		
        		s=new HashSet<String>(pcl);
        		flag="pcl";
        		
        	}else if(ipInvsDataSize==noOfSessSize){
        		
        		s=new HashSet<String>(noOfSess);
        		flag="noOfSess";
        		
        	}else if(ipInvsDataSize==ppcSize){
        		
        		s=new HashSet<String>(ppc);
        		flag="ppc";
        		
        	}else if(ipInvsDataSize==pccSize){
        		
        		s=new HashSet<String>(pcc);
        		flag="pcc";
        		
        	}else if(ipInvsDataSize==pftSize){
        		
        		s=new HashSet<String>(pft);
        		flag="pft";
        	//	log.info("Flag in ip:"+flag);
        		
        	}
       ipSetList=new ArrayList<String>(s);
       ipInvsSetSize = s.size();
     }
        
       
       
      
        
      
        
        
        if(maxNum!=null){
        	maxNum.clear();
        }

        maxNum.add(ipConsultDataSize);
        maxNum.add(ipCompDataSize);
        maxNum.add(ipPhysDataSize);
        maxNum.add(ipAmblDataSize);
        maxNum.add(ipInvsSetSize);
        
        Integer ipClmCount=Collections.max(maxNum);

        
        
    	if(ipClmCount>0){
    	
        for (int i = 0; i < ipClmCount; i++) {
        	
        	if(i>0){
        		
        	   	inPatientVO=new HmoInPatientVO();
            	// setting common data of OP
            	inPatientVO.setPolicyNo(policyNm);
            	inPatientVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
            	inPatientVO.setProductName(policyvo.getProductName());
            	inPatientVO.setCorporateGroupID(policyvo.getCorporateGroupID());
            	
            	if("PC".equals(ipOuterData.get("ipIpDcCmnRulOpt"))){
            		
            		inPatientVO.setIpDaycPPL(ipOuterData.get("IP_DAYC_PPL"));
                	inPatientVO.setIpDaycChronicPPL(ipOuterData.get("IP_DAYC_CHRONIC_PPL"));
                	inPatientVO.setIpDaycPedPPL(ipOuterData.get("IP_DAYC_PED_PPL"));
                	inPatientVO.setIpDaycChronPlusPedPPL(ipOuterData.get("IP_DAYC_PED_AND_CHRON_PPL"));
                  	inPatientVO.setIpDaycOneMedPPL(ipOuterData.get("IP_DAYC_ONE_MEDICAL_PPL"));
            		
            	}
            	
            	if("PC".equals(ipOuterData.get("ipIpOpt"))){
            		
            		inPatientVO.setIpPPL(ipOuterData.get("IP_PPL"));
            		
            	}
            	
            	
             if("PC".equals(ipOuterData.get("ipInvOpt"))){
	
             	inPatientVO.setIpOverallInvstPPL(ipOuterData.get("IP_OVERALL_INVST_PPL"));
	            inPatientVO.setIpLabAndPathPPL(ipOuterData.get("IP_LAB_AND_PATH_PPL"));
	            inPatientVO.setIpExceptLabAndPathPPL(ipOuterData.get("IP_EXCEPT_LAB_AND_PATH_PPL"));
            		
            	}

          
        		
        	}
        	

        
        	
        	if(i<ipConsultDataSize){
        		
               	InvestigationRuleVO investigationRuleVO1=(InvestigationRuleVO)ipConsultData.get(i);
            	
               	inPatientVO.setIpConsultGeoLocCov(keyConverter1(investigationRuleVO1.getGeoLocationID(),"Geo",null));
            	inPatientVO.setIpConsultConCov(keyConverter1(investigationRuleVO1.getCountryIDs(),"Con",investigationRuleVO1.getGeoLocationID()));
            	inPatientVO.setIpConsultEncType(keyConverter1(investigationRuleVO1.getEncounterTypes(),"Enc","IPT"));
            	inPatientVO.setIpConsultProTypes(keyConverter1(investigationRuleVO1.getProviderTypes(),"Pro",null));
            	inPatientVO.setIpConsultPPL(investigationRuleVO1.getPerPolicyLimit());
            	inPatientVO.setIpConsultEmrCov(keyConverter1(investigationRuleVO1.getEmiratesIDs(),"Emr",investigationRuleVO1.getCountryIDs()));
            	
            	String strClinCopayDetails=investigationRuleVO1.getClinicianCopayDetails();
            	String ipClncConsYN=investigationRuleVO1.getClncConsYN();
            	
            	if("Y".equalsIgnoreCase(ipClncConsYN)){
            		
            		boolean ipClncConsGPH= false;
            		boolean ipClncConsSPH= false;
            		
              	     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
            	    	 
            	    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
            	    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
            	    	
            	    	 for(String copayDetails:arrrClinCopayDetails){
            	    		 
            	    		 String arrGenIds[]=copayDetails.split("[@]");
            	    		 if(arrGenIds!=null&&arrGenIds.length>0){
            	    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
            	    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
            	    			 if("GPH".equalsIgnoreCase(arrGenIds[0])){
            	    				 ipClncConsGPH= true;
            	    				 inPatientVO.setIpGpConsultCopay(arrCpDetails[0]);
            	    				 inPatientVO.setIpGpConsultDEDUCTABLE(arrCpDetails[1]);
            	    				 inPatientVO.setIpGpConsultMinMax(arrCpDetails[2]);
            	    			 }if("SPH".equalsIgnoreCase(arrGenIds[0])){
            	    				 ipClncConsSPH= true;
            	    				 inPatientVO.setIpSpConsultCopay(arrCpDetails[0]);
            	    				 inPatientVO.setIpSpConsultDEDUCTABLE(arrCpDetails[1]);
            	    				 inPatientVO.setIpSpConsultMinMax(arrCpDetails[2]);
            	    				 
            	    			 }
            	    	
            	    		 
            	    		 }
            	    		 }
            	    	 }
            	    }
            	     }
              	     
              	     if(ipClncConsGPH==false){
              	    	 
              	    	 inPatientVO.setIpGpConsultCopay(investigationRuleVO1.getOvrCopay());
	    				 inPatientVO.setIpGpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
	    				 inPatientVO.setIpGpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
              	    	 
              	     }
              	     
              	   if(ipClncConsSPH==false){
            	    	 
            	    	 inPatientVO.setIpSpConsultCopay(investigationRuleVO1.getOvrCopay());
	    				 inPatientVO.setIpSpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
	    				 inPatientVO.setIpSpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
            	    	 
            	     }
          		
            		
            	}else{
            		
            	
              	     inPatientVO.setIpGpConsultCopay(investigationRuleVO1.getOvrCopay());
    				 inPatientVO.setIpGpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
    				 inPatientVO.setIpGpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
    				 inPatientVO.setIpSpConsultCopay(investigationRuleVO1.getOvrCopay());
    				 inPatientVO.setIpSpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
    				 inPatientVO.setIpSpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
            		
            	}
      
       	  inPatientVO.setIpConsultFlwPeriod(investigationRuleVO1.getFollowUpPeriod());
          inPatientVO.setIpConsultFlwPeriodUnit(unitConverter(investigationRuleVO1.getFollowUpPeriodType()));
        		
        	}
        	
        	

        	
        	/////////////////////// Cons End ////////////////////////////////////////////////////////////
        	
/////////////////////// Companian Start ////////////////////////////////////////////////////////////
        	
     	if(i<ipCompDataSize){
        		
               	InvestigationRuleVO investigationRuleVO1=(InvestigationRuleVO)ipCompData.get(i);
            	
               	inPatientVO.setIpCompnChargGeoLoc(keyConverter1(investigationRuleVO1.getGeoLocationID(),"Geo",null));
            	inPatientVO.setIpCompnChargCon(keyConverter1(investigationRuleVO1.getCountryIDs(),"Con",investigationRuleVO1.getGeoLocationID()));
            	inPatientVO.setIpCompnChargEmr(keyConverter1(investigationRuleVO1.getEmiratesIDs(),"Emr",investigationRuleVO1.getCountryIDs()));
            	inPatientVO.setIpCompnChargProTypes(keyConverter1(investigationRuleVO1.getProviderTypes(),"Pro",null));
            	inPatientVO.setIpCompnChargProFaclityTypes(unitConverter(investigationRuleVO1.getHospType()));
            	inPatientVO.setIpCompnChargPreaprvReqdYesNo("Y".equals(investigationRuleVO1.getPreApprvReqYN())?"YES":"NO");
            	inPatientVO.setIpCompnChargPAL(investigationRuleVO1.getPreApprvLimit());
            	inPatientVO.setIpCompnChargFrmAge(investigationRuleVO1.getAgeRangeFrom());
            	inPatientVO.setIpCompnChargToAge(investigationRuleVO1.getAgeRangeTo());
            	inPatientVO.setIpCompnChargFrmToAgeUt(unitConverter(investigationRuleVO1.getFollowUpPeriodType()));
            	inPatientVO.setIpCompnChargPPL(investigationRuleVO1.getPerPolicyLimit());
            	inPatientVO.setIpCompnChargNoDaysAlwd(investigationRuleVO1.getNoOfdaysAllowPerCalim());
            	inPatientVO.setIpCompnChargMxLmtAlwdPerDay(investigationRuleVO1.getMaxLimitAllowPerDay());
            	inPatientVO.setIpCompnChargCopay(investigationRuleVO1.getOvrCopay());
            	inPatientVO.setIpCompnChargDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
            	inPatientVO.setIpCompnChargCopdedMINMAX(investigationRuleVO1.getOvrMinMaxFlag());
     
        		
        	}
     	
/////////////////////// Companian End ////////////////////////////////////////////////////////////
        	
            /////////////////////// INVS Start ////////////////////////////////////////////////////////////
        	
           	for (int j = 0; j < ipInvsDataSize; j++) {
           		
           		
        		
        		InvestigationRuleVO investigationRuleVOInvs=ipInvsData.get(j);
        		if(i<ipSetList.size()){
        			
        			String string=ipSetList.get(i);
        			
            		if("pal".equals(flag)){
            			
            			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPreApprvLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		inPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName()); 
        
                        		inPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		inPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		inPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		inPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		inPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		inPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		inPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		inPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		inPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		inPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		inPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                            
                     		inPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());
                     		inPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                            
                     		inPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
                     		inPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		inPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                     		inPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		inPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
                     		inPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("ppl".equals(flag)){
            			
 			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerPolicyLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		inPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		inPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		inPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		inPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		inPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		inPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		inPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		inPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		inPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		inPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		
    
                     		inPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		inPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		inPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		inPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		inPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		inPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
 
                     		inPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		inPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		inPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		inPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		inPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		inPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		inPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		} else if("pcl".equals(flag)){
            			
 			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerClaimLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		inPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		inPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		inPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		inPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		inPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		inPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		inPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		inPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		inPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		inPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		
                     		inPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		inPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		inPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		inPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
 
                     		inPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		inPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		inPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		inPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		inPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("noOfSess".equals(flag)){
            			
 			if(string.equalsIgnoreCase(investigationRuleVOInvs.getNoOfSessAllowPerPolicy())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		inPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		inPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		inPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		inPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		inPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		inPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		inPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		inPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		inPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		inPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		
                     		inPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		inPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		inPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		inPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
 
                     		inPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		inPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		inPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		inPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		inPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		inPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("ppc".equals(flag)){
            			
 			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerPolicyCpyDdctLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		inPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		inPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		inPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		inPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		inPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit());
                        		inPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		inPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		inPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		inPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		inPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
    
                     		inPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		inPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		inPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		inPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		inPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
 
                     		inPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		inPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		inPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		inPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		inPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		inPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit());
                     		inPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("pcc".equals(flag)){
            			
 			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerClaimCpyDdctLimit())){
            				
                        	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                        		
                        		inPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
        
                        		inPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                        		inPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                        		inPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                        		inPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                        		inPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                        		inPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                        		inPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                        		inPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                     		
                     		inPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                     		
    
                     		inPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                     		
                     		inPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                     		inPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                     		
                     		inPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
 
                     		inPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                     		
                     		inPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                       
                     		inPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                     		
                     		inPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
              
                     		inPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                     		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                     		inPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                     		inPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                     		inPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                     		inPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                     		inPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                     		
                     	}
            				
            			}
            			
            		}else if("pft".equals(flag)){
            			
    			if(string.equalsIgnoreCase(investigationRuleVOInvs.getProviderTypes())){
            				
                	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                		
                		inPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());

                		inPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                		inPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                		inPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                		inPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                		inPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                		inPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                		inPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                		inPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
             		
             	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
             		
             		inPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
             		

             		inPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
             		inPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
             		inPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
             		inPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
             		inPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
             		inPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
             		inPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
             		inPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
             		
             	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
             		
             		inPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

             		inPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
             		inPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
             		inPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
             		inPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
             		inPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
             		inPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
             		inPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
             		inPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
             		
             	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
             		
             		inPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());

             		inPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
             		inPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
             		inPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
             		inPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
             		inPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
             		inPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
             		inPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
             		inPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
             		
             	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
             		
             		inPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
               
             		inPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
             		inPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
             		inPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
             		inPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
             		inPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
             		inPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
             		inPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
             		inPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
             		
             	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
             		
             		inPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
      
             		inPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
             		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
             		inPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
             		inPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
             		inPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
             		inPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
             		inPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
             		inPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
             		
             	}
    				
            				
            			}
            			
            		}
        			
        		}
        		
        		
    
				
			}
        	
        	
        	
           /////////////////////// INVS End ////////////////////////////////////////////////////////////
        	
        	
            /////////////////////// PHYSIO Start ////////////////////////////////////////////////////////////
        	
        	if(i<ipPhysDataSize){
        		
        		InvestigationRuleVO investigationRuleVOPhys=(InvestigationRuleVO)ipPhysData.get(i);
            	
        		inPatientVO.setIpPhysioProviderFacilityTypes(keyConverter1(investigationRuleVOPhys.getProviderTypes(),"Pro",null));
        		inPatientVO.setIpPhysioPreapprovalReqdYesNo("Y".equals(investigationRuleVOPhys.getPreApprvReqYN())?"YES":"NO");
        		inPatientVO.setIpPhysioPreapprovalLimit(investigationRuleVOPhys.getPreApprvLimit());
        		inPatientVO.setIpPhysioPpl(investigationRuleVOPhys.getPerPolicyLimit());
        		inPatientVO.setIpPhysioNOOfSess(investigationRuleVOPhys.getNoOfSessAllowPerPolicy());
        		inPatientVO.setIpPhysioPerSessLimit(investigationRuleVOPhys.getLimitPerSession());
        		inPatientVO.setIpPhysioCopay(investigationRuleVOPhys.getOvrCopay());
        		inPatientVO.setIpPhysioOvrDeductable(investigationRuleVOPhys.getOvrDeductible());
        		inPatientVO.setIpPhysioCopdedMINMAX(investigationRuleVOPhys.getOvrMinMaxFlag());
        		
        	}
        	
        	
        	
        	
           /////////////////////// PHYSIO End ////////////////////////////////////////////////////////////
        	
           /////////////////////// PHARMA Start ////////////////////////////////////////////////////////////
        	
            if(i<ipAmblDataSize){
            	
            	
             	InvestigationRuleVO investigationRuleVOAmb=(InvestigationRuleVO)ipAmblData.get(i);
            	
             	inPatientVO.setIpAmblncGeoLoc(keyConverter1(investigationRuleVOAmb.getGeoLocationID(),"Geo",null));
             	inPatientVO.setIpAmblncCon(keyConverter1(investigationRuleVOAmb.getCountryIDs(),"Con",investigationRuleVOAmb.getGeoLocationID()));
             	inPatientVO.setIpAmblncEmr(keyConverter1(investigationRuleVOAmb.getEmiratesIDs(),"Emr",investigationRuleVOAmb.getCountryIDs()));
             	inPatientVO.setIpAmblncProTypes(keyConverter1(investigationRuleVOAmb.getProviderTypes(),"Pro",null));
             	inPatientVO.setIpAmblncProFaclityTypes(unitConverter(investigationRuleVOAmb.getHospType()));
             	inPatientVO.setIpAmblncEmrNonemr(unitConverter(investigationRuleVOAmb.getEncounterTypes()));
             	inPatientVO.setIpAmblncPreaprvReqdYesNo("Y".equals(investigationRuleVOAmb.getPreApprvReqYN())?"YES":"NO");
             	inPatientVO.setIpAmblncPAL(investigationRuleVOAmb.getPreApprvLimit());
             	inPatientVO.setIpAmblncPPL(investigationRuleVOAmb.getPerPolicyLimit());
             	inPatientVO.setIpAmblncCopay(investigationRuleVOAmb.getOvrCopay());
             	inPatientVO.setIpAmblncDeductable(investigationRuleVOAmb.getOvrDeductible());
             	inPatientVO.setIpAmblncCopdedMINMAX(investigationRuleVOAmb.getOvrMinMaxFlag());
        		
         }
        	
       
    
        	
        	
           /////////////////////// PHARMA End ////////////////////////////////////////////////////////////
			
	
      //========================================================================================================================   

        
        inPatientVOs.add(inPatientVO);
               
    	}
        
    	}else{
    		
    		  inPatientVOs.add(inPatientVO);
    	}
        hmoDataMap.put("IPT",inPatientVOs);    // inpatient complete data
        
    // IP configuration data fetching finished
		
	}
        
                // DayCare
	
	if(benefitList.contains("DAYC")){
		
		// IP configuration data fetching started
        
        investigationRuleVO.setBenefitID("DAYC");
        
        ArrayList<InvestigationRuleVO> dcConsultData= null;
        Integer dcConsultDataSize= 0;
        
        ArrayList<InvestigationRuleVO> dcCompData= null;
        Integer dcCompDataSize= 0;
        
        ArrayList<InvestigationRuleVO> dcInvsData= null;
        ArrayList<String> dcSetList= null;
        Integer dcInvsDataSize= 0;
        Integer dcInvSetSize= 0;
        
        ArrayList<InvestigationRuleVO> dcPhysData= null;
        Integer dcPhysDataSize= 0;
        
        ArrayList<InvestigationRuleVO> dcAmblData= null;
        Integer dcAmblDataSize= 0;
        
        
 if("PC".equals(dcOuterData.get("DcConsOpt"))){
        	
	    investigationRuleVO.setInvsType("CONS");
        dcConsultData=ruleManagerObject.getConsdDetails(investigationRuleVO);
        dcConsultDataSize=dcConsultData.size();
       }
        
    
        
 if("PC".equals(dcOuterData.get("DcCompChrgOpt"))){
        	
	 investigationRuleVO.setInvsType("COMP");
     dcCompData=ruleManagerObject.getCompanDetails(investigationRuleVO);
     dcCompDataSize=dcCompData.size();
       }
        
 if("PC".equals(dcOuterData.get("DcPhyOpt"))){
 	
	  investigationRuleVO.setInvsType("PHYS");
     dcPhysData=ruleManagerObject.getPhysioDetails(investigationRuleVO);
     dcPhysDataSize=dcPhysData.size();
      }
       
     
       
if("PC".equals(dcOuterData.get("DcAmbOpt"))){
       	
	   investigationRuleVO.setInvsType("AMBL");
      dcAmblData=ruleManagerObject.getAmbulanceDetails(investigationRuleVO);
      dcAmblDataSize=dcAmblData.size();
      }
        
 if("PC".equals(dcOuterData.get("DcInvOpt"))){
        	
	  investigationRuleVO.setInvsType("INVS");
      dcInvsData=ruleManagerObject.getInvestdDetails(investigationRuleVO);
      dcInvsDataSize=dcInvsData.size();
      
      if(pal!=null){
      	pal.clear();
      }
      
      if(ppl!=null){
      	ppl.clear();
      }
      
      if(pcl!=null){
      	 pcl.clear();
      }
     
      if(noOfSess!=null){
      	noOfSess.clear();
      }
      
      if(ppc!=null){
      	 ppc.clear();
      }
     
      if(pcc!=null){
      	pcc.clear();
      }
      
      if(s!=null){
      	s.clear();
      }
      if(setList!=null){
      	setList.clear();
      }
      if(pft!=null){
      	pft.clear();
      }
      
      
      
      for (InvestigationRuleVO investigationRuleVO2 : dcInvsData) {
      	
      	if(!investigationRuleVO2.getPreApprvLimit().equals("")){
      		
      		pal.add(investigationRuleVO2.getPreApprvLimit());
      		
      	}
         if(!investigationRuleVO2.getPerPolicyLimit().equals("")){

              ppl.add(investigationRuleVO2.getPerPolicyLimit());
      		
      	}
         if(!investigationRuleVO2.getPerClaimLimit().equals("")){
      	   
      	   pcl.add(investigationRuleVO2.getPerClaimLimit());

          }
        if(!investigationRuleVO2.getNoOfSessAllowPerPolicy().equals("")){
      	  
      	  noOfSess.add(investigationRuleVO2.getNoOfSessAllowPerPolicy());

         }
       if(!investigationRuleVO2.getPerPolicyCpyDdctLimit().equals("")){
      	 
      	 ppc.add(investigationRuleVO2.getPerPolicyCpyDdctLimit());

          }
       if(!investigationRuleVO2.getPerClaimCpyDdctLimit().equals("")){
      	 
      	 pcc.add(investigationRuleVO2.getPerClaimCpyDdctLimit());

        }
      if(!investigationRuleVO2.getProviderTypes().equals("")){   // new
      	 
      	 pft.add(investigationRuleVO2.getProviderTypes());

        }
      	
	
		}
      
     
      
      palSize=pal.size(); pplSize=ppl.size(); pclSize=pcl.size();noOfSessSize=noOfSess.size();ppcSize=ppc.size();pccSize=pcc.size(); pftSize=pft.size();

  
  	flag=null;
  	if(dcInvsDataSize==palSize){
  		
  		s=new HashSet<String>(pal);
  		flag="pal";
  		
  	}else if(dcInvsDataSize==pplSize){
  		
  		s=new HashSet<String>(ppl);
  		flag="ppl";
  		
  	}else if(dcInvsDataSize==pclSize){
  		
  		s=new HashSet<String>(pcl);
  		flag="pcl";
  		
  	}else if(dcInvsDataSize==noOfSessSize){
  		
  		s=new HashSet<String>(noOfSess);
  		flag="noOfSess";
  		
  	}else if(dcInvsDataSize==ppcSize){
  		
  		s=new HashSet<String>(ppc);
  		flag="ppc";
  		
  	}else if(dcInvsDataSize==pccSize){
  		
  		s=new HashSet<String>(pcc);
  		flag="pcc";
  		
  	}else if(dcInvsDataSize==pftSize){
  		
  		s=new HashSet<String>(pft);
  		flag="pft";
  	//	log.info("Flag in dc:"+flag);
  		
  	}

  	dcSetList=new ArrayList<String>(s);
  	dcInvSetSize= s.size();
  	
       }
        
      
        

        
     
        
        if(maxNum!=null){
        	maxNum.clear();
        }

        maxNum.add(dcConsultDataSize);
        maxNum.add(dcCompDataSize);
        maxNum.add(dcPhysDataSize);
        maxNum.add(dcAmblDataSize);
        maxNum.add(dcInvSetSize);
        
        Integer dcClmCount=Collections.max(maxNum);
        
        
      	if("PC".equals(ipOuterData.get("ipIpDcCmnRulOpt"))){
    		
    		dcPatientVO.setIpDaycPPL(ipOuterData.get("IP_DAYC_PPL"));
            
        	dcPatientVO.setIpDaycChronicPPL(ipOuterData.get("IP_DAYC_CHRONIC_PPL"));
        	dcPatientVO.setIpDaycPedPPL(ipOuterData.get("IP_DAYC_PED_PPL"));
        	dcPatientVO.setIpDaycChronPlusPedPPL(ipOuterData.get("IP_DAYC_PED_AND_CHRON_PPL"));
          	dcPatientVO.setIpDaycOneMedPPL(ipOuterData.get("IP_DAYC_ONE_MEDICAL_PPL"));
    		
    	}else if("Y".equals(ipOuterData.get("ipIpDcCmnRulOpt"))){
    		
	        dcPatientVO.setIpDaycPPL("Y");
        	dcPatientVO.setIpDaycChronicPPL("Y");
        	dcPatientVO.setIpDaycPedPPL("Y");
        	dcPatientVO.setIpDaycChronPlusPedPPL("Y");
          	dcPatientVO.setIpDaycOneMedPPL("Y");
    		
    	}else if("NC".equals(ipOuterData.get("ipIpDcCmnRulOpt"))){
    		
    		   dcPatientVO.setIpDaycPPL("NC");
           	dcPatientVO.setIpDaycChronicPPL("NC");
           	dcPatientVO.setIpDaycPedPPL("NC");
           	dcPatientVO.setIpDaycChronPlusPedPPL("NC");
             	dcPatientVO.setIpDaycOneMedPPL("NC");
    		
	    
    		
    	}/*else{
    		
    	    dcPatientVO.setIpDaycPPL("NOT CONFIGURED IN INPATIENT");
        	dcPatientVO.setIpDaycChronicPPL("NOT CONFIGURED IN INPATIENT");
        	dcPatientVO.setIpDaycPedPPL("NOT CONFIGURED IN INPATIENT");
        	dcPatientVO.setIpDaycChronPlusPedPPL("NOT CONFIGURED IN INPATIENT");
          	dcPatientVO.setIpDaycOneMedPPL("NOT CONFIGURED IN INPATIENT");
    		
	     
    		
    	}*/
        
    	if(dcClmCount>0){
    		
            for (int i = 0; i < dcClmCount; i++) {
            	
            	if(i>0){
            		
            		dcPatientVO=new HmoInPatientVO();
                	// setting common data of OP
                	dcPatientVO.setPolicyNo(policyNm);
                	dcPatientVO.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
                	dcPatientVO.setProductName(policyvo.getProductName());
                	dcPatientVO.setCorporateGroupID(policyvo.getCorporateGroupID());
                	
                	dcPatientVO.setIpDaycPPL(ipOuterData.get("IP_DAYC_PPL"));
                	
         
                	
                	if("PC".equals(ipOuterData.get("ipIpDcCmnRulOpt"))){
                		
                		dcPatientVO.setIpDaycPPL(ipOuterData.get("IP_DAYC_PPL"));
                        
                    	dcPatientVO.setIpDaycChronicPPL(ipOuterData.get("IP_DAYC_CHRONIC_PPL"));
                    	dcPatientVO.setIpDaycPedPPL(ipOuterData.get("IP_DAYC_PED_PPL"));
                    	dcPatientVO.setIpDaycChronPlusPedPPL(ipOuterData.get("IP_DAYC_PED_AND_CHRON_PPL"));
                      	dcPatientVO.setIpDaycOneMedPPL(ipOuterData.get("IP_DAYC_ONE_MEDICAL_PPL"));
                		
                	}
                	
                	if("PC".equals(dcOuterData.get("DcDcOpt"))){
                		
                		dcPatientVO.setDcDaycPPL(dcOuterData.get("DC_DAYC_PPL"));
                	}
            	
            
                	
                	
if("PC".equals(dcOuterData.get("DcInvOpt"))){
	
	dcPatientVO.setIpOverallInvstPPL(dcOuterData.get("DC_OVERALL_INVST_PPL"));
	dcPatientVO.setIpLabAndPathPPL(dcOuterData.get("DC_LAB_AND_PATH_PPL"));
	dcPatientVO.setIpExceptLabAndPathPPL(dcOuterData.get("DC_EXCEPT_LAB_AND_PATH_PPL"));
                		
                	}
                	
                	
                
            		
            	}

     
            	if(i<dcConsultDataSize){
            		
                   	InvestigationRuleVO investigationRuleVO1=(InvestigationRuleVO)dcConsultData.get(i);
                	
                   	dcPatientVO.setIpConsultGeoLocCov(keyConverter1(investigationRuleVO1.getGeoLocationID(),"Geo",null));
                	dcPatientVO.setIpConsultConCov(keyConverter1(investigationRuleVO1.getCountryIDs(),"Con",investigationRuleVO1.getGeoLocationID()));
                	dcPatientVO.setIpConsultEncType(keyConverter1(investigationRuleVO1.getEncounterTypes(),"Enc","DAYC"));
                	dcPatientVO.setIpConsultProTypes(keyConverter1(investigationRuleVO1.getProviderTypes(),"Pro",null));
                	dcPatientVO.setIpConsultPPL(investigationRuleVO1.getPerPolicyLimit());
                	dcPatientVO.setIpConsultEmrCov(keyConverter1(investigationRuleVO1.getEmiratesIDs(),"Emr",investigationRuleVO1.getCountryIDs()));
                	
                	String strClinCopayDetails=investigationRuleVO1.getClinicianCopayDetails();
                	String dcClncConsYN=investigationRuleVO1.getClncConsYN();
                	
                      if("Y".equalsIgnoreCase(dcClncConsYN)){
                		
                		boolean dcClncConsGPH= false;
                		boolean dcClncConsSPH= false;
                		
                  	     if(strClinCopayDetails!=null&&strClinCopayDetails.length()>1){
                	    	 
                	    	 String[]arrrClinCopayDetails=strClinCopayDetails.split("[|]");
                	    if(arrrClinCopayDetails!=null&&arrrClinCopayDetails.length>0){
                	    	
                	    	 for(String copayDetails:arrrClinCopayDetails){
                	    		 
                	    		 String arrGenIds[]=copayDetails.split("[@]");
                	    		 if(arrGenIds!=null&&arrGenIds.length>0){
                	    		 String arrCpDetails[]=arrGenIds[1].split("[_]");
                	    		 if(arrCpDetails!=null&&arrCpDetails.length>0){
                	    			 if("GPH".equalsIgnoreCase(arrGenIds[0])){
                	    				 dcClncConsGPH= true;
                	    				 dcPatientVO.setIpGpConsultCopay(arrCpDetails[0]);
                	    				 dcPatientVO.setIpGpConsultDEDUCTABLE("".equals(arrCpDetails[1])?investigationRuleVO1.getOvrDeductible():arrCpDetails[1]);
                	    				 dcPatientVO.setIpGpConsultMinMax(arrCpDetails[2]);
                	    			 }if("SPH".equalsIgnoreCase(arrGenIds[0])){
                	    				 dcClncConsSPH= true;
                	    				 dcPatientVO.setIpSpConsultCopay("".equals(arrCpDetails[0])?investigationRuleVO1.getOvrCopay():arrCpDetails[0]);
                	    				 dcPatientVO.setIpSpConsultDEDUCTABLE("".equals(arrCpDetails[1])?investigationRuleVO1.getOvrDeductible():arrCpDetails[1]);
                	    				 dcPatientVO.setIpSpConsultMinMax(arrCpDetails[2]);
                	    				 
                	    			 }
                	    	
                	    		 
                	    		 }
                	    		 }
                	    	 }
                	    }
                	     }
                  	     
                  	     if(dcClncConsGPH==false){
                  	    	 
                  	    	 dcPatientVO.setIpGpConsultCopay(investigationRuleVO1.getOvrCopay());
    	    				 dcPatientVO.setIpGpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
    	    				 dcPatientVO.setIpGpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
                  	    	 
                  	     }
                  	     
                  	   if(dcClncConsSPH==false){
                	    	 
                	    	 dcPatientVO.setIpSpConsultCopay(investigationRuleVO1.getOvrCopay());
    	    				 dcPatientVO.setIpSpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
    	    				 dcPatientVO.setIpSpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
                	    	 
                	     }
              		
                		
                	}else{
                		
                	
                  	     dcPatientVO.setIpGpConsultCopay(investigationRuleVO1.getOvrCopay());
        				 dcPatientVO.setIpGpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
        				 dcPatientVO.setIpGpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
        				 dcPatientVO.setIpSpConsultCopay(investigationRuleVO1.getOvrCopay());
        				 dcPatientVO.setIpSpConsultDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
        				 dcPatientVO.setIpSpConsultMinMax(investigationRuleVO1.getOvrMinMaxFlag());
                		
                	}

           	  dcPatientVO.setIpConsultFlwPeriod(investigationRuleVO1.getFollowUpPeriod());
           	  dcPatientVO.setIpConsultFlwPeriodUnit(unitConverter(investigationRuleVO1.getFollowUpPeriodType()));
            		
            	}
            	

            	
            	/////////////////////// Cons End ////////////////////////////////////////////////////////////
            	
                /////////////////////// INVS Start ////////////////////////////////////////////////////////////
            	
               	for (int j = 0; j < dcInvsDataSize; j++) {
            		
            		InvestigationRuleVO investigationRuleVOInvs=dcInvsData.get(j);
            		if(i<dcSetList.size()){
            			
            			String string=dcSetList.get(i);
            			
                		if("pal".equals(flag)){
                			
                			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPreApprvLimit())){
                				
                            	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                            		
                            		dcPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName()); 
            
                            		dcPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO");
                            		dcPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                            		dcPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                            		dcPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                            		dcPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                            		dcPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                            		dcPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                         		
                         		dcPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                         		dcPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                                
                         		dcPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());
                         		dcPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                                
                         		dcPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
                         		dcPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit());
                         		dcPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                         		dcPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                         		dcPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                         		
                         		dcPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
                         		dcPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}
                				
                			}
                			
                		}else if("ppl".equals(flag)){
                			
     			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerPolicyLimit())){
                				
                            	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                            		
                            		dcPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
            
                            		dcPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                            		dcPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                            		dcPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                            		dcPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                            		dcPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                            		dcPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                         		
                         		dcPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                         		
        
                         		dcPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                         		
                         		dcPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                         		dcPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                         		
                         		dcPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
     
                         		dcPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                         		
                         		dcPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                           
                         		dcPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                         		
                         		dcPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
                  
                         		dcPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}
                				
                			}
                			
                		} else if("pcl".equals(flag)){
                			
     			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerClaimLimit())){
                				
                            	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                            		
                            		dcPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
            
                            		dcPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                            		dcPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                            		dcPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                            		dcPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                            		dcPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                            		dcPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                         		
                         		dcPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                         		
                         		dcPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                         		
                         		dcPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                         		dcPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                         		
                         		dcPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
     
                         		dcPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                         		
                         		dcPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                           
                         		dcPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                         		
                         		dcPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
                  
                         		dcPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}
                				
                			}
                			
                		}else if("noOfSess".equals(flag)){
                			
     			if(string.equalsIgnoreCase(investigationRuleVOInvs.getNoOfSessAllowPerPolicy())){
                				
                            	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                            		
                            		dcPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
            
                            		dcPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                            		dcPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                            		dcPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                            		dcPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                            		dcPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                            		dcPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                         		
                         		dcPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                         		
                         		dcPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                         		
                         		dcPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                         		dcPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                         		
                         		dcPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
     
                         		dcPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                         		
                         		dcPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                           
                         		dcPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                         		
                         		dcPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
                  
                         		dcPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit());
                         		dcPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}
                				
                			}
                			
                		}else if("ppc".equals(flag)){
                			
     			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerPolicyCpyDdctLimit())){
                				
                            	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                            		
                            		dcPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
            
                            		dcPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                            		dcPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                            		dcPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                            		dcPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                            		dcPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                            		dcPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                         		
                         		dcPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
        
                         		dcPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                         		
                         		dcPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                         		dcPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                         		
                         		dcPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
     
                         		dcPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                         		
                         		dcPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                           
                         		dcPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                         		
                         		dcPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
                  
                         		dcPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}
                				
                			}
                			
                		}else if("pcc".equals(flag)){
                			
     			if(string.equalsIgnoreCase(investigationRuleVOInvs.getPerClaimCpyDdctLimit())){
                				
                            	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                            		
                            		dcPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
            
                            		dcPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                            		dcPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                            		dcPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                            		dcPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                            		dcPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                            		dcPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                         		
                         		dcPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                         		
        
                         		dcPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                         		
                         		dcPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                         		dcPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                         		
                         		dcPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
     
                         		dcPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit());
                         		dcPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                         		
                         		dcPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                           
                         		dcPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit());
                         		dcPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                         		
                         		dcPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
                  
                         		dcPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}
                				
                			}
                			
                		}else if("pft".equals(flag)){
                			
     			       if(string.equalsIgnoreCase(investigationRuleVOInvs.getProviderTypes())){
                				
                            	if("80050".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // lab
                            		
                            		dcPatientVO.setInvsTypeNameLab(investigationRuleVOInvs.getInvestTypeName());
            
                            		dcPatientVO.setIpInvLabProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                            		dcPatientVO.setIpInvLabPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                            		dcPatientVO.setIpInvLabPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                            		dcPatientVO.setIpInvLabNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                            		dcPatientVO.setIpInvLabCopay(investigationRuleVOInvs.getOvrCopay()); 
                            		dcPatientVO.setIpInvLabDeductable(investigationRuleVOInvs.getOvrDeductible());
                            		dcPatientVO.setIpInvLabCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("88399".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // pathology
                         		
                         		dcPatientVO.setInvsTypeNamePat(investigationRuleVOInvs.getInvestTypeName());
                         		
        
                         		dcPatientVO.setIpInvPatProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvPatPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvPatPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvPatNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvPatCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvPatDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvPatCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("76999".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){   // Ultrasounds
                         		
                         		dcPatientVO.setInvsTypeNameUlt(investigationRuleVOInvs.getInvestTypeName());

                         		dcPatientVO.setIpInvUltraProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvUltraPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvUltraPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvUltraNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvUltraCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvUltraDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvUltraCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70450".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){  // CT Scans
                         		
                         		dcPatientVO.setInvsTypeNameCts(investigationRuleVOInvs.getInvestTypeName());
     
                         		dcPatientVO.setIpInvCtScanProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvCtScanPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvCtScanPpl(investigationRuleVOInvs.getPerPolicyLimit());
                         		dcPatientVO.setIpInvCtScanNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvCtScanCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvCtScanDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvCtScanCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("70551".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){        // MRI Scans
                         		
                         		dcPatientVO.setInvsTypeNameMri(investigationRuleVOInvs.getInvestTypeName());
                           
                         		dcPatientVO.setIpInvMriProviderFacilityTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvMriPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvMriPpl(investigationRuleVOInvs.getPerPolicyLimit());
                         		dcPatientVO.setIpInvMriNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvMriCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvMriDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvMriCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}else if("71010".equalsIgnoreCase(investigationRuleVOInvs.getInvestTypeName())){      // Diagnostic and Therapeutic Radiology
                         		
                         		dcPatientVO.setInvsTypeNameDig(investigationRuleVOInvs.getInvestTypeName());
                  
                         		dcPatientVO.setIpInvDiagAndTherapProviderTypes(keyConverter1(investigationRuleVOInvs.getProviderTypes(),"Pro",null)); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdYesNo("Y".equals(investigationRuleVOInvs.getPreApprvReqYN())?"YES":"NO"); 
                         		dcPatientVO.setIpInvDiagAndTherapPreapprovalReqdLimit(investigationRuleVOInvs.getPreApprvLimit());
                         		dcPatientVO.setIpInvDiagAndTherapPpl(investigationRuleVOInvs.getPerPolicyLimit()); 
                         		dcPatientVO.setIpInvDiagAndTherapNoOfSess(investigationRuleVOInvs.getNoOfSessAllowPerPolicy());
                         		dcPatientVO.setIpInvDiagAndTherapCopay(investigationRuleVOInvs.getOvrCopay()); 
                         		dcPatientVO.setIpInvDiagAndTherapDeductable(investigationRuleVOInvs.getOvrDeductible());
                         		dcPatientVO.setIpInvDiagAndTherapCopdedMINMAX(investigationRuleVOInvs.getOvrMinMaxFlag());
                         		
                         	}
                				
                			}
                			
                		}
            			
            		}
            		
            		
        
    				
    			}
            	
            	
            	
               /////////////////////// INVS End ////////////////////////////////////////////////////////////
            	
    /////////////////////// Companian Start ////////////////////////////////////////////////////////////
            	
       	if(i<dcCompDataSize){
          		
                 	InvestigationRuleVO investigationRuleVO1=(InvestigationRuleVO)dcCompData.get(i);
             
                 	dcPatientVO.setIpCompnChargGeoLoc(keyConverter1(investigationRuleVO1.getGeoLocationID(),"Geo",null));
              	dcPatientVO.setIpCompnChargCon(keyConverter1(investigationRuleVO1.getCountryIDs(),"Con",investigationRuleVO1.getGeoLocationID()));
              	dcPatientVO.setIpCompnChargEmr(keyConverter1(investigationRuleVO1.getEmiratesIDs(),"Emr",investigationRuleVO1.getCountryIDs()));
              	dcPatientVO.setIpCompnChargProTypes(keyConverter1(investigationRuleVO1.getProviderTypes(),"Pro",null));
              	dcPatientVO.setIpCompnChargProFaclityTypes(unitConverter(investigationRuleVO1.getHospType()));
              	dcPatientVO.setIpCompnChargPreaprvReqdYesNo("Y".equals(investigationRuleVO1.getPreApprvReqYN())?"YES":"NO");
              	dcPatientVO.setIpCompnChargPAL(investigationRuleVO1.getPreApprvLimit());
              	dcPatientVO.setIpCompnChargFrmAge(investigationRuleVO1.getAgeRangeFrom());
              	dcPatientVO.setIpCompnChargToAge(investigationRuleVO1.getAgeRangeTo());
              	dcPatientVO.setIpCompnChargFrmToAgeUt(unitConverter(investigationRuleVO1.getFollowUpPeriodType()));
              	dcPatientVO.setIpCompnChargPPL(investigationRuleVO1.getPerPolicyLimit());
              	dcPatientVO.setIpCompnChargNoDaysAlwd(investigationRuleVO1.getNoOfdaysAllowPerCalim());
              	dcPatientVO.setIpCompnChargMxLmtAlwdPerDay(investigationRuleVO1.getMaxLimitAllowPerDay());
              	dcPatientVO.setIpCompnChargCopay(investigationRuleVO1.getOvrCopay());
              	dcPatientVO.setIpCompnChargDEDUCTABLE(investigationRuleVO1.getOvrDeductible());
              	dcPatientVO.setIpCompnChargCopdedMINMAX(investigationRuleVO1.getOvrMinMaxFlag());
       
          		
          	}
       	
    /////////////////////// Companian End ////////////////////////////////////////////////////////////
            	
                /////////////////////// PHYSIO Start ////////////////////////////////////////////////////////////
            	
            	if(i<dcPhysDataSize){
            		
            		InvestigationRuleVO investigationRuleVOPhys=(InvestigationRuleVO)dcPhysData.get(i);
                	
            		dcPatientVO.setIpPhysioProviderFacilityTypes(keyConverter1(investigationRuleVOPhys.getProviderTypes(),"Pro",null));
            		dcPatientVO.setIpPhysioPreapprovalReqdYesNo("Y".equals(investigationRuleVOPhys.getPreApprvReqYN())?"YES":"NO");
            		dcPatientVO.setIpPhysioPreapprovalLimit(investigationRuleVOPhys.getPreApprvLimit());
            		dcPatientVO.setIpPhysioPpl(investigationRuleVOPhys.getPerPolicyLimit());
            		dcPatientVO.setIpPhysioNOOfSess(investigationRuleVOPhys.getNoOfSessAllowPerPolicy());
            		dcPatientVO.setIpPhysioPerSessLimit(investigationRuleVOPhys.getLimitPerSession());
            		dcPatientVO.setIpPhysioCopay(investigationRuleVOPhys.getOvrCopay());
            		dcPatientVO.setIpPhysioOvrDeductable(investigationRuleVOPhys.getOvrDeductible());
            		dcPatientVO.setIpPhysioCopdedMINMAX(investigationRuleVOPhys.getOvrMinMaxFlag());
            		
            	}
            	
            	
            	
            	
               /////////////////////// PHYSIO End ////////////////////////////////////////////////////////////
            	
               /////////////////////// PHARMA Start ////////////////////////////////////////////////////////////
            	
                if(i<dcAmblDataSize){
                	
                	
                 	InvestigationRuleVO investigationRuleVOAmb=(InvestigationRuleVO)dcAmblData.get(i);
                	
                 	dcPatientVO.setIpAmblncGeoLoc(keyConverter1(investigationRuleVOAmb.getGeoLocationID(),"Geo",null));
                 	dcPatientVO.setIpAmblncCon(keyConverter1(investigationRuleVOAmb.getCountryIDs(),"Con",investigationRuleVOAmb.getGeoLocationID()));
                 	dcPatientVO.setIpAmblncEmr(keyConverter1(investigationRuleVOAmb.getEmiratesIDs(),"Emr",investigationRuleVOAmb.getCountryIDs()));
                 	dcPatientVO.setIpAmblncProTypes(keyConverter1(investigationRuleVOAmb.getProviderTypes(),"Pro",null));
                 	dcPatientVO.setIpAmblncProFaclityTypes(unitConverter(investigationRuleVOAmb.getHospType()));
                 	dcPatientVO.setIpAmblncEmrNonemr(unitConverter(investigationRuleVOAmb.getEncounterTypes()));
                 	dcPatientVO.setIpAmblncPreaprvReqdYesNo("Y".equals(investigationRuleVOAmb.getPreApprvReqYN())?"YES":"NO");
                 	dcPatientVO.setIpAmblncPAL(investigationRuleVOAmb.getPreApprvLimit());
                 	dcPatientVO.setIpAmblncPPL(investigationRuleVOAmb.getPerPolicyLimit());
                 	dcPatientVO.setIpAmblncCopay(investigationRuleVOAmb.getOvrCopay());
                 	dcPatientVO.setIpAmblncDeductable(investigationRuleVOAmb.getOvrDeductible());
                 	dcPatientVO.setIpAmblncCopdedMINMAX(investigationRuleVOAmb.getOvrMinMaxFlag());
            		
             }
            	
           
        
            	
            	
               /////////////////////// PHARMA End ////////////////////////////////////////////////////////////
    			
    	
          //========================================================================================================================   

            
            dayCareVOs.add(dcPatientVO);
                   
        	}
    		
    	}else{
    		
    		 dayCareVOs.add(dcPatientVO);
    		
    	}
        

        hmoDataMap.put("DAYC",dayCareVOs);    // DAY CARE complete data
        
    // DAYCARE configuration data fetching finished
		
	}

	
	

	if(benefitList.contains("IPT")||benefitList.contains("OPTS")||benefitList.contains("OPTC")||benefitList.contains("DNTL")){
		
		 ArrayList<Long> beneTypeArray=new ArrayList<Long>();
         HashMap<Long, String> beneValue=new HashMap<>();
         
         if(lhmConfBenefitTypes.get("IPT")!=null){
        	 
        	 beneTypeArray.add((Long)lhmConfBenefitTypes.get("IPT"));
        	 beneValue.put((Long)lhmConfBenefitTypes.get("IPT"), "IPT");
        	
         }
         if(lhmConfBenefitTypes.get("OPTS")!=null){
        	 
        	 beneTypeArray.add((Long)lhmConfBenefitTypes.get("OPTS"));
        	 beneValue.put((Long)lhmConfBenefitTypes.get("OPTS"), "OPTS");
        	 
         }  
         if(lhmConfBenefitTypes.get("OPTC")!=null){
        	 
        	 beneTypeArray.add((Long)lhmConfBenefitTypes.get("OPTC"));
         	 beneValue.put((Long)lhmConfBenefitTypes.get("OPTC"), "OPTC");
        	
         }
         if(lhmConfBenefitTypes.get("DNTL")!=null){
        	 
        	   beneTypeArray.add((Long)lhmConfBenefitTypes.get("DNTL"));
            	 beneValue.put((Long)lhmConfBenefitTypes.get("DNTL"), "DNTL");
        	
         }
     
    	ArrayList<HmoAOCVo> aocDataList=new ArrayList<HmoAOCVo>();
    	HmoAOCVo aocVo=null;
        
        for (Long long1 : beneTypeArray) {
        	
        	Reader reader=ruleManagerObject.getMohProCopayDetails(long1);
        	ArrayList<Condition> providerRulesList=null;
        	if(reader!=null){
    			hmProCopayDetails=getProviderCopayRulesUnmarshaller(reader);
    		}
        	
  	 if(hmProCopayDetails!=null){
  		 
  		 // enhance
  		 
  		 String beneType = beneValue.get(long1);
  		 String beneTypeFlag = "";
  		 
  		// log.info("beneType:"+beneType);
  		 
  		 if("IPT".equals(beneType)){
  			 
  			beneTypeFlag = ipOuterData.get("AocIPT");
  			 
  			 if("PC".equals(ipOuterData.get("AocIPT"))){
          	   
  				ipOuterData.put(beneType, "VALID");
      		 
      	 }
  			 
  			 
  			 
  		 }else if("OPTS".equals(beneType)){
  			 
  			beneTypeFlag = ipOuterData.get("AocOpts");
  			 
  			 if("PC".equals(ipOuterData.get("AocOpts"))){
          	   
  				
  				ipOuterData.put(beneType, "VALID");
      		 
      	 }
  			 
  			 
  			 
  		 }else if("OPTC".equals(beneType)){
  			 
  			beneTypeFlag = ipOuterData.get("AocOptc");
  			 
  			 if("PC".equals(ipOuterData.get("AocOptc"))){
          	   
  				ipOuterData.put(beneType, "VALID");
      		 
      	 }
  			 
  			 
  			 
  		 }else if("DNTL".equals(beneType)){
  			 
  			beneTypeFlag = ipOuterData.get("AocDntl");
  			 
  			 if("PC".equals(ipOuterData.get("AocDntl"))){
          	   
  				ipOuterData.put(beneType, "VALID");
      		 
      	 }
  			 
  			 
  			 
  		 }
  		 
  		/* log.info("AOC STATUS:"+ipOuterData.get(beneType));
  		log.info("beneTypeFlag:"+beneTypeFlag);*/
  		 
  		 if("VALID".equals(ipOuterData.get(beneType))){
  			 
  			//log.info("hmProCopayDetails STATUS:"+hmProCopayDetails.get(beneType));
  	  	   providerRulesList=hmProCopayDetails.get(beneType);
  	  	   
         	    if(providerRulesList != null&&providerRulesList.size()>0){
     	        	int rowCount=providerRulesList.size()-1;
     	        	for(;0<=rowCount;rowCount--){

     	                aocVo=new HmoAOCVo();
     	                aocVo.setPolicyNo(policyvo.getPolicyNm());
     	                aocVo.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
     	                aocVo.setProductName(policyvo.getProductName());
  	                aocVo.setCorporateGroupID(policyvo.getCorporateGroupID());
  	                
  	              aocVo.setProductNetworkCategory(policyvo.getProductNetworkCategory());
  	            aocVo.setCorporateGroupName(policyvo.getCorporateGroupName());
  	          aocVo.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
  	        aocVo.setSumInsured(policyvo.getSumInsured());
  	                
     	        		Condition condition=providerRulesList.get(rowCount);
     	        		
     	        	    aocVo.setBenefitType(condition.getBenefitTypeDesc());
    	                aocVo.setClaimType(condition.getClaimTypeDesc());
    	                aocVo.setNtwrkType(condition.getNetworkYNDesc());
  	                    aocVo.setProviderType(condition.getProviderTypeDesc());
  	                    
  	                 aocVo.setEncTypeId(keyConverter1(condition.getEncounterTypes(), "Enc", beneValue.get(long1)));
  	                
                      aocVo.setProviderFaclityType(keyConverter1(condition.getProviderFacilities(),"Pro",null));
                      GeoLocationCopayDetails  geoLoc=condition.getGeoLocCopayDetails();
                      Integer locatCode=Integer.parseInt(geoLoc.getLocationCode());
                      aocVo.setGeoLocat(geoLoc.getLocationName());
                      aocVo.setGeoLocatPplAED(geoLoc.getPerPolicyLimit());
                      aocVo.setGeoLocatPclAED(geoLoc.getPerClaimLimit());
  	                    aocVo.setGeoLocatPdlAED(geoLoc.getPerDayLimit());
  	                    aocVo.setGeoLocatCpyPercnt(geoLoc.getCopay());
                      aocVo.setGeoLocatDedAED(geoLoc.getFlatamount());
                      aocVo.setGeoLocatCopDedMinMax(geoLoc.getMinmax());

     	        		ArrayList<CountryCopayDetails>  alCountries=condition.getCountries().getCountriesCopayDetails();
     	        		
     	        		boolean flagUAE=false;
     	        		boolean flagNonUAE=false;
     	        		boolean flagLocatUAE=false;
     	        		StringBuffer nonUaeCon=new StringBuffer();
     	        		StringBuffer nonUaePpl=new StringBuffer();
     	        		StringBuffer nonUaePcl=new StringBuffer();
     	        		StringBuffer nonUaePdl=new StringBuffer();
     	        		
     	        		StringBuffer nonUaeCpy=new StringBuffer();
     	        		StringBuffer nonUaeDed=new StringBuffer();
     	        		StringBuffer nonUaeCDMnMx=new StringBuffer();
     	 		    for(CountryCopayDetails countryCopayDetails:alCountries){

  	        		
  	        		if(locatCode==2||locatCode==8||locatCode==3||locatCode==7){
  	        			
  	        			flagLocatUAE=true;
  	 		    		
  	 		    		if("United Arab Emirates".equalsIgnoreCase(countryCopayDetails.getCountryName())){
  	 		    			
  	 		    		flagUAE=true;
  	 		    	    aocVo.setUAEPplAED(countryCopayDetails.getPerPolicyLimit());
  	 		    	    aocVo.setUAEPclAED(countryCopayDetails.getPerClaimLimit());
  	 		            aocVo.setUAEPdlAED(countryCopayDetails.getPerDayLimit());
  	 		            
  	 		            aocVo.setUAECpyPercnt(countryCopayDetails.getCopay());
   		    	    aocVo.setUAEDedAED(countryCopayDetails.getFlatamount());
   		            aocVo.setUAECopDedMinMax(countryCopayDetails.getMinmax());
  	 		    			
  	 		    		}else{
  	 		    			
  	 		    			
  	 		    	flagNonUAE=true;
  	 		    	if(!("".equals(countryCopayDetails.getCountryName()))){
  	 		    		
  	 		    	nonUaeCon.append(countryCopayDetails.getCountryName());
  	 		        nonUaeCon.append(",");
  	 		    	}
  	 		    	
  	 		    if(!("".equals(countryCopayDetails.getPerPolicyLimit()))){
   		    		
  	 		    nonUaePpl.append(countryCopayDetails.getPerPolicyLimit());
  	 		    nonUaePpl.append(",");
  	 		    	}
  	 		       
  	 		if(!("".equals(countryCopayDetails.getPerClaimLimit()))){
  	 			
  	 		 nonUaePcl.append(countryCopayDetails.getPerClaimLimit());
  	        nonUaePcl.append(",");
  	 			
  	 		}
  	 		
  	 	if(!("".equals(countryCopayDetails.getPerDayLimit()))){
  	 		
  	 	nonUaePdl.append(countryCopayDetails.getPerDayLimit());
          nonUaePdl.append(",");
   			
   		}
  	 if(!("".equals(countryCopayDetails.getCopay()))){
  		 
  		nonUaeCpy.append(countryCopayDetails.getCopay());
          nonUaeCpy.append(",");
  			
  		}
  	if(!("".equals(countryCopayDetails.getFlatamount()))){
  		
  	 nonUaeDed.append(countryCopayDetails.getFlatamount());
      nonUaeDed.append(",");
  		
  	}
  	if(!("".equals(countryCopayDetails.getMinmax()))){
  		
  	   nonUaeCDMnMx.append(countryCopayDetails.getMinmax());
          nonUaeCDMnMx.append(",");
  		
  	}
  	
  	 		    		}
  	 		    		
  	 		    	}else{
  	 		    		
  	 		    		
  	 		 	if(!("".equals(countryCopayDetails.getCountryName()))){
   		    		
  	 		    	nonUaeCon.append(countryCopayDetails.getCountryName());
  	 		        nonUaeCon.append(",");
  	 		    	}
  	 	        if(!("".equals(countryCopayDetails.getPerPolicyLimit()))){
  	    		
  	 		    nonUaePpl.append(countryCopayDetails.getPerPolicyLimit());
  	 		    nonUaePpl.append(",");
  	 		    	}
  	   	 		if(!("".equals(countryCopayDetails.getPerClaimLimit()))){
  	   	 			
  	   	 		 nonUaePcl.append(countryCopayDetails.getPerClaimLimit());
  			        nonUaePcl.append(",");
  	   	 			
  	   	 		}
  	   	 		
  	   	 	if(!("".equals(countryCopayDetails.getPerDayLimit()))){
  	   	 		
  	   	 	nonUaePdl.append(countryCopayDetails.getPerDayLimit());
  		        nonUaePdl.append(",");
  		 			
  		 		}
  	   	 if(!("".equals(countryCopayDetails.getCopay()))){
  	   		 
  	   		nonUaeCpy.append(countryCopayDetails.getCopay());
  		        nonUaeCpy.append(",");
  	 			
  	 		}
  	   	if(!("".equals(countryCopayDetails.getFlatamount()))){
  	   		
  	   	 nonUaeDed.append(countryCopayDetails.getFlatamount());
  	        nonUaeDed.append(",");
  				
  			}
  	   	if(!("".equals(countryCopayDetails.getMinmax()))){
  	   		
  	   	   nonUaeCDMnMx.append(countryCopayDetails.getMinmax());
  		        nonUaeCDMnMx.append(",");
  				
  			}
  	 		    	}
  	        		
     	 		    }
     	 		    
     	 		    
     	 		if(nonUaeCon.length()>0){
         	 		nonUaeCon.deleteCharAt(nonUaeCon.length()-1);
  	         	}
     	 	if(nonUaePpl.length()>0){
     	 	nonUaePpl.deleteCharAt(nonUaePpl.length()-1);
           	}
     		if(nonUaePcl.length()>0){
     			nonUaePcl.deleteCharAt(nonUaePcl.length()-1);
           	}
     		if(nonUaePdl.length()>0){
     			nonUaePdl.deleteCharAt(nonUaePdl.length()-1);
           	}
     		if(nonUaeCpy.length()>0){
     			nonUaeCpy.deleteCharAt(nonUaeCpy.length()-1);
           	}
     		if(nonUaeDed.length()>0){
     			nonUaeDed.deleteCharAt(nonUaeDed.length()-1);
           	}
     		if(nonUaeCDMnMx.length()>0){
     			nonUaeCDMnMx.deleteCharAt(nonUaeCDMnMx.length()-1);
           	}
         	 		
     	 		    
     	 		    if(flagLocatUAE==true){
     	 		    	
     	 		    
     	 		    if(flagUAE==true && flagNonUAE==true){
     	 		    	
     	 		    aocVo.setConCov("UAE and other countries in this geography location");
     	 		    aocVo.setOthrNonUAEconCov(nonUaeCon.toString());
     	 		    aocVo.setNonUAEConPplAED(nonUaePpl.toString());
     	 	        aocVo.setNonUAEConPclAED(nonUaePcl.toString());
     	            aocVo.setNonUAEConPdlAED(nonUaePdl.toString());
     	            aocVo.setNonUAECpyPercnt(nonUaeCpy.toString());
     	            aocVo.setNonUAEDedAED(nonUaeDed.toString());
              	aocVo.setNonUAECopDedMinMax(nonUaeCDMnMx.toString());
     	 		   
     	 		
     	 		    }else if(flagUAE==true){
     	 		    	
     	 		    aocVo.setConCov("Only UAE");
     	 		    aocVo.setOthrNonUAEconCov("NA");
     	 		    
     	 		    aocVo.setNonUAEConPplAED("NA");
  	    	    aocVo.setNonUAEConPclAED("NA");
  	            aocVo.setNonUAEConPdlAED("NA");
  	            aocVo.setNonUAECpyPercnt("NA");
  	    	    aocVo.setNonUAEDedAED("NA");
  	            aocVo.setNonUAECopDedMinMax("NA");
     	 		    	
     	 		    }else{
     	 		    	
     	 		    aocVo.setConCov("Only other Non-UAE countries in this geography location");
     	 		    aocVo.setOthrNonUAEconCov(nonUaeCon.toString());
     	 	    aocVo.setNonUAEConPplAED(nonUaePpl.toString());
  	 	        aocVo.setNonUAEConPclAED(nonUaePcl.toString());
  	            aocVo.setNonUAEConPdlAED(nonUaePdl.toString());
  	            aocVo.setNonUAECpyPercnt(nonUaeCpy.toString());
  	            aocVo.setNonUAEDedAED(nonUaeDed.toString());
          	aocVo.setNonUAECopDedMinMax(nonUaeCDMnMx.toString());
          	
     	   	        aocVo.setUAEPplAED("NA");
  	    	    aocVo.setUAEPclAED("NA");
  	            aocVo.setUAEPdlAED("NA");
  	            aocVo.setUAECpyPercnt("NA");
  	    	    aocVo.setUAEDedAED("NA");
  	            aocVo.setUAECopDedMinMax("NA");
     	 		    }
     	 		    	
     	 		    }else{
     	 		    	
     	 		    aocVo.setConCov("UAE is not included in this geography location group");
     	 		    aocVo.setOthrNonUAEconCov(nonUaeCon.toString());
     	 		    aocVo.setNonUAEConPplAED(nonUaePpl.toString());
    	 	        aocVo.setNonUAEConPclAED(nonUaePcl.toString());
    	            aocVo.setNonUAEConPdlAED(nonUaePdl.toString());
    	            aocVo.setNonUAECpyPercnt(nonUaeCpy.toString());
    	            aocVo.setNonUAEDedAED(nonUaeDed.toString());
              	aocVo.setNonUAECopDedMinMax(nonUaeCDMnMx.toString());
              	
     	 		    aocVo.setUAEPplAED("NA");
  	    	    aocVo.setUAEPclAED("NA");
  	            aocVo.setUAEPdlAED("NA");
  	            aocVo.setUAECpyPercnt("NA");
  	    	    aocVo.setUAEDedAED("NA");
  	            aocVo.setUAECopDedMinMax("NA");
     	 		    
     	 		    	
     	 		    }
     	 		    ArrayList<EmirateCopayDetails>  alEmirates=null;
     	 		    if(condition.getEmirates()!=null){
     	 		     alEmirates=condition.getEmirates().getEmirateCopayDetails();
     	 		    }
     	        		
     	        		StringBuffer sb=new StringBuffer();
     	        		if(alEmirates != null){
     	        		  for(EmirateCopayDetails emirateCopayDetails:alEmirates){
         	        		  sb.append(emirateCopayDetails.getEmirateName());
         	        		sb.append(",");
         	        		String EmrCode=emirateCopayDetails.getEmirateCode();
         	        		if("DXB".equalsIgnoreCase(EmrCode)){
         	        			
         	        			aocVo.setPplForDXB(emirateCopayDetails.getPerPolicyLimit());
         	        			aocVo.setPclForDXB(emirateCopayDetails.getPerClaimLimit());
         	        			aocVo.setPdlForDXB(emirateCopayDetails.getPerDayLimit());
         	        			aocVo.setCpyPercntForDXB(emirateCopayDetails.getCopay());
         	        			aocVo.setDedForDXB(emirateCopayDetails.getFlatamount());
         	        			aocVo.setCopDedMinMaxForDXB(emirateCopayDetails.getMinmax());
     	        				
         	        		
         	        			
         	        		}else if("AUH".equalsIgnoreCase(EmrCode)){
         	        			
      	          			aocVo.setPplForAUH(emirateCopayDetails.getPerPolicyLimit());
         	        			aocVo.setPclForAUH(emirateCopayDetails.getPerClaimLimit());
         	        			aocVo.setPdlForAUH(emirateCopayDetails.getPerDayLimit());
         	        			aocVo.setCpyPercntForAUH(emirateCopayDetails.getCopay());
         	        			aocVo.setDedForAUH(emirateCopayDetails.getFlatamount());
         	        			aocVo.setCopDedMinMaxForAUH(emirateCopayDetails.getMinmax());
         	   
         	        			
         	        		}else if("AJM".equalsIgnoreCase(EmrCode)){
         	        			
         	        		  	aocVo.setPplForAJM(emirateCopayDetails.getPerPolicyLimit());
         	        			aocVo.setPclForAJM(emirateCopayDetails.getPerClaimLimit());
         	        			aocVo.setPdlForAJM(emirateCopayDetails.getPerDayLimit());
         	        			aocVo.setCpyPercntForAJM(emirateCopayDetails.getCopay());
         	        			aocVo.setDedForAJM(emirateCopayDetails.getFlatamount());
         	        			aocVo.setCopDedMinMaxForAJM(emirateCopayDetails.getMinmax());
         	        			
         	        			
         	        			
         	        		}else if("FUJ".equalsIgnoreCase(EmrCode)){
         	        			
         	           	   aocVo.setPplForFUJ(emirateCopayDetails.getPerPolicyLimit());
    	        			aocVo.setPclForFUJ(emirateCopayDetails.getPerClaimLimit());
    	        			aocVo.setPdlForFUJ(emirateCopayDetails.getPerDayLimit());
    	        			aocVo.setCpyPercntForFUJ(emirateCopayDetails.getCopay());
    	        			aocVo.setDedForFUJ(emirateCopayDetails.getFlatamount());
    	        			aocVo.setCopDedMinMaxForFUJ(emirateCopayDetails.getMinmax());
         	        			
         	        		}else if("RAK".equalsIgnoreCase(EmrCode)){
         	        			
         	        		   aocVo.setPplForRAK(emirateCopayDetails.getPerPolicyLimit());
        	        			aocVo.setPclForRAK(emirateCopayDetails.getPerClaimLimit());
        	        			aocVo.setPdlForRAK(emirateCopayDetails.getPerDayLimit());
        	        			aocVo.setCpyPercntForRAK(emirateCopayDetails.getCopay());
        	        			aocVo.setDedForRAK(emirateCopayDetails.getFlatamount());
        	        			aocVo.setCopDedMinMaxForRAK(emirateCopayDetails.getMinmax());
         	        			
         	        			
         	        		}else if("SJH".equalsIgnoreCase(EmrCode)){
         	        			
         	        			aocVo.setPplForSJH(emirateCopayDetails.getPerPolicyLimit());
         	        			aocVo.setPclForSJH(emirateCopayDetails.getPerClaimLimit());
         	        			aocVo.setPdlForSJH(emirateCopayDetails.getPerDayLimit());
         	        			aocVo.setCpyPercntForSJH(emirateCopayDetails.getCopay());
         	        			aocVo.setDedForSJH(emirateCopayDetails.getFlatamount());
         	        			aocVo.setCopDedMinMaxForSJH(emirateCopayDetails.getMinmax());
         	        		
         	        			
         	        		}else if("UAQ".equalsIgnoreCase(EmrCode)){
         	        			
         	        			aocVo.setPplForUAQ(emirateCopayDetails.getPerPolicyLimit());
         	        			aocVo.setPclForUAQ(emirateCopayDetails.getPerClaimLimit());
         	        			aocVo.setPdlForUAQ(emirateCopayDetails.getPerDayLimit());
         	        			aocVo.setCpyPercntForUAQ(emirateCopayDetails.getCopay());
         	        			aocVo.setDedForUAQ(emirateCopayDetails.getFlatamount());
         	        			aocVo.setCopDedMinMaxForUAQ(emirateCopayDetails.getMinmax());
         	        			
         	        		
         	        			
         	        		}else if("KHO".equalsIgnoreCase(EmrCode)){
         	        			
         	        			aocVo.setPplForKOH(emirateCopayDetails.getPerPolicyLimit());
         	        			aocVo.setPclForKOH(emirateCopayDetails.getPerClaimLimit());
         	        			aocVo.setPdlForKOH(emirateCopayDetails.getPerDayLimit());
         	        			aocVo.setCpyPercntForKOH(emirateCopayDetails.getCopay());
         	        			aocVo.setDedForKOH(emirateCopayDetails.getFlatamount());
         	        			aocVo.setCopDedMinMaxForKOH(emirateCopayDetails.getMinmax());
         	        		
         	        			
         	        		}else if("ALA".equalsIgnoreCase(EmrCode)){
         	        			
         	        			aocVo.setPplForALA(emirateCopayDetails.getPerPolicyLimit());
         	        			aocVo.setPclForALA(emirateCopayDetails.getPerClaimLimit());
         	        			aocVo.setPdlForALA(emirateCopayDetails.getPerDayLimit());
         	        			aocVo.setCpyPercntForALA(emirateCopayDetails.getCopay());
         	        			aocVo.setDedForALA(emirateCopayDetails.getFlatamount());
         	        			aocVo.setCopDedMinMaxForALA(emirateCopayDetails.getMinmax());
         	        			
         	        		
         	        			
         	        		}
         	       
     	       	        	  }
     	        		if(sb.length()>0){
     	        			sb.deleteCharAt(sb.length()-1);
     	         	}
     	        			
     	        		}else{
     	        			
     	        			aocVo.setPplForDXB("NA");
     	        			aocVo.setPclForDXB("NA");
     	        			aocVo.setPdlForDXB("NA");
     	        			aocVo.setCpyPercntForDXB("NA");
     	        			aocVo.setDedForDXB("NA");
     	        			aocVo.setCopDedMinMaxForDXB("NA");
     	        			
     	      			aocVo.setPplForAUH("NA");
  	        			aocVo.setPclForAUH("NA");
  	        			aocVo.setPdlForAUH("NA");
  	        			aocVo.setCpyPercntForAUH("NA");
  	        			aocVo.setDedForAUH("NA");
  	        			aocVo.setCopDedMinMaxForAUH("NA");
     	        			
     	        			aocVo.setPplForAJM("NA");
     	        			aocVo.setPclForAJM("NA");
     	        			aocVo.setPdlForAJM("NA");
     	        			aocVo.setCpyPercntForAJM("NA");
     	        			aocVo.setDedForAJM("NA");
     	        			aocVo.setCopDedMinMaxForAJM("NA");
     	        			
     	        			aocVo.setPplForFUJ("NA");
     	        			aocVo.setPclForFUJ("NA");
     	        			aocVo.setPdlForFUJ("NA");
     	        			aocVo.setCpyPercntForFUJ("NA");
     	        			aocVo.setDedForFUJ("NA");
     	        			aocVo.setCopDedMinMaxForFUJ("NA");
  	        				
     	        			
     	        			aocVo.setPplForRAK("NA");
     	        			aocVo.setPclForRAK("NA");
     	        			aocVo.setPdlForRAK("NA");
     	        			aocVo.setCpyPercntForRAK("NA");
     	        			aocVo.setDedForRAK("NA");
     	        			aocVo.setCopDedMinMaxForRAK("NA");
     	        			
     	        			aocVo.setPplForSJH("NA");
     	        			aocVo.setPclForSJH("NA");
     	        			aocVo.setPdlForSJH("NA");
     	        			aocVo.setCpyPercntForSJH("NA");
     	        			aocVo.setDedForSJH("NA");
     	        			aocVo.setCopDedMinMaxForSJH("NA");
     	        			
     	        			aocVo.setPplForUAQ("NA");
     	        			aocVo.setPclForUAQ("NA");
     	        			aocVo.setPdlForUAQ("NA");
     	        			aocVo.setCpyPercntForUAQ("NA");
     	        			aocVo.setDedForUAQ("NA");
     	        			aocVo.setCopDedMinMaxForUAQ("NA");
     	        			
     	        			aocVo.setPplForKOH("NA");
     	        			aocVo.setPclForKOH("NA");
     	        			aocVo.setPdlForKOH("NA");
     	        			aocVo.setCpyPercntForKOH("NA");
     	        			aocVo.setDedForKOH("NA");
     	        			aocVo.setCopDedMinMaxForKOH("NA");
     	        			
     	        			
  	        				aocVo.setPplForALA("NA");
     	        			aocVo.setPclForALA("NA");
     	        			aocVo.setPdlForALA("NA");
     	        			aocVo.setCpyPercntForALA("NA");
     	        			aocVo.setDedForALA("NA");
     	        			aocVo.setCopDedMinMaxForALA("NA");

     	        			
     	        		}
     	        		
     	        		if(alEmirates != null){
     	        			
     	        			aocVo.setCovEmirates(sb.toString());
     	        		}else{
     	        			
     	        			aocVo.setCovEmirates("NA");
     	        		}
     	        		
     	        		aocDataList.add(aocVo);
     	        		
     	        	}
         	 
          }
         	    
         	   ipOuterData.remove(beneType);
         	   
         	//  log.info("AocIpt status after remove:"+ipOuterData.get(beneType));
  			 
  		 }else{
  			 
  			 aocVo=new HmoAOCVo();
  			 
  			 aocVo.setPolicyNo(policyvo.getPolicyNm());
             aocVo.setInsuranceCompanyName(policyvo.getInsuranceCompanyName());
             aocVo.setProductName(policyvo.getProductName());
          aocVo.setCorporateGroupID(policyvo.getCorporateGroupID());
          
          aocVo.setProductNetworkCategory(policyvo.getProductNetworkCategory());
            aocVo.setCorporateGroupName(policyvo.getCorporateGroupName());
          aocVo.setPolicyAdmnstrtvSrvcType(policyvo.getPolicyAdmnstrtvSrvcType());
        aocVo.setSumInsured(policyvo.getSumInsured());
  			 
  		if("Y".equals(beneTypeFlag)){
  			
            if("IPT".equals(beneType)){
 	  			 
            	aocVo.setBenefitType("IN-PATIENT");
	  		
	  			 

	  		 }else if("OPTS".equals(beneType)){
	  			 
	  			aocVo.setBenefitType("OUT-PATIENT");
	 
	  			 
	  		 }else if("OPTC".equals(beneType)){
	  			 
	  			aocVo.setBenefitType("OPTICAL");
		 
	  			 
	  		 }else if("DNTL".equals(beneType)){
	  			 
	  			aocVo.setBenefitType("DENTAL");
		 
	  			 
	  		 }
  			
  
            aocVo.setClaimType("Y");
            aocVo.setNtwrkType("Y");
              aocVo.setProviderType("Y");
              
           aocVo.setEncTypeId("Y");
          
          aocVo.setProviderFaclityType("Y");
         
          aocVo.setGeoLocat("Y");
          aocVo.setGeoLocatPplAED("Y");
          aocVo.setGeoLocatPclAED("Y");
              aocVo.setGeoLocatPdlAED("Y");
              aocVo.setGeoLocatCpyPercnt("Y");
          aocVo.setGeoLocatDedAED("Y");
          aocVo.setGeoLocatCopDedMinMax("Y");
          
          aocVo.setConCov("Y");
          
	 		    aocVo.setOthrNonUAEconCov("Y");
	 		    aocVo.setNonUAEConPplAED("Y");
 	        aocVo.setNonUAEConPclAED("Y");
            aocVo.setNonUAEConPdlAED("Y");
            aocVo.setNonUAECpyPercnt("Y");
            aocVo.setNonUAEDedAED("Y");
      	aocVo.setNonUAECopDedMinMax("Y");
      	
      
 	
      	
        aocVo.setUAEPplAED("Y");
	    aocVo.setUAEPclAED("Y");
        aocVo.setUAEPdlAED("Y");
        aocVo.setUAECpyPercnt("Y");
	    aocVo.setUAEDedAED("Y");
        aocVo.setUAECopDedMinMax("Y");
        
    	aocVo.setCovEmirates("Y");
          
            aocVo.setPplForDXB("Y");
			aocVo.setPclForDXB("Y");
			aocVo.setPdlForDXB("Y");
			aocVo.setCpyPercntForDXB("Y");
			aocVo.setDedForDXB("Y");
			aocVo.setCopDedMinMaxForDXB("Y");
			
			aocVo.setPplForAUH("Y");
			aocVo.setPclForAUH("Y");
			aocVo.setPdlForAUH("Y");
			aocVo.setCpyPercntForAUH("Y");
			aocVo.setDedForAUH("Y");
			aocVo.setCopDedMinMaxForAUH("Y");
			
			aocVo.setPplForAJM("Y");
			aocVo.setPclForAJM("Y");
			aocVo.setPdlForAJM("Y");
			aocVo.setCpyPercntForAJM("Y");
			aocVo.setDedForAJM("Y");
			aocVo.setCopDedMinMaxForAJM("Y");
			
			aocVo.setPplForFUJ("Y");
			aocVo.setPclForFUJ("Y");
			aocVo.setPdlForFUJ("Y");
			aocVo.setCpyPercntForFUJ("Y");
			aocVo.setDedForFUJ("Y");
			aocVo.setCopDedMinMaxForFUJ("Y");
				
			
			aocVo.setPplForRAK("Y");
			aocVo.setPclForRAK("Y");
			aocVo.setPdlForRAK("Y");
			aocVo.setCpyPercntForRAK("Y");
			aocVo.setDedForRAK("Y");
			aocVo.setCopDedMinMaxForRAK("Y");
			
			aocVo.setPplForSJH("Y");
			aocVo.setPclForSJH("Y");
			aocVo.setPdlForSJH("Y");
			aocVo.setCpyPercntForSJH("Y");
			aocVo.setDedForSJH("Y");
			aocVo.setCopDedMinMaxForSJH("Y");
			
			aocVo.setPplForUAQ("Y");
			aocVo.setPclForUAQ("Y");
			aocVo.setPdlForUAQ("Y");
			aocVo.setCpyPercntForUAQ("Y");
			aocVo.setDedForUAQ("Y");
			aocVo.setCopDedMinMaxForUAQ("Y");
			
			aocVo.setPplForKOH("Y");
			aocVo.setPclForKOH("Y");
			aocVo.setPdlForKOH("Y");
			aocVo.setCpyPercntForKOH("Y");
			aocVo.setDedForKOH("Y");
			aocVo.setCopDedMinMaxForKOH("Y");
			
			
				aocVo.setPplForALA("Y");
			aocVo.setPclForALA("Y");
			aocVo.setPdlForALA("Y");
			aocVo.setCpyPercntForALA("Y");
			aocVo.setDedForALA("Y");
			aocVo.setCopDedMinMaxForALA("Y");
 			 
  			
  		}else if("NC".equals(beneTypeFlag)){
  			
  	
          
                if("IPT".equals(beneType)){
  	  			 
                	aocVo.setBenefitType("IN-PATIENT");
  	  		
  	  			 
   
  	  		 }else if("OPTS".equals(beneType)){
  	  			 
  	  			aocVo.setBenefitType("OUT-PATIENT");
 	 
  	  			 
  	  		 }else if("OPTC".equals(beneType)){
  	  			 
  	  			aocVo.setBenefitType("OPTICAL");
 		 
  	  			 
  	  		 }else if("DNTL".equals(beneType)){
  	  			 
  	  			aocVo.setBenefitType("DENTAL");
 		 
  	  			 
  	  		 }
     		
            aocVo.setClaimType("NC");
            aocVo.setNtwrkType("NC");
              aocVo.setProviderType("NC");
              
           aocVo.setEncTypeId("NC");
          
          aocVo.setProviderFaclityType("NC");
       
          aocVo.setGeoLocat("NC");
          aocVo.setGeoLocatPplAED("NC");
          aocVo.setGeoLocatPclAED("NC");
              aocVo.setGeoLocatPdlAED("NC");
              aocVo.setGeoLocatCpyPercnt("NC");
          aocVo.setGeoLocatDedAED("NC");
          aocVo.setGeoLocatCopDedMinMax("NC");
          
          aocVo.setConCov("NC");
          aocVo.setOthrNonUAEconCov("NC");
          
          
    	    aocVo.setUAEPplAED("NC");
	    	    aocVo.setUAEPclAED("NC");
	            aocVo.setUAEPdlAED("NC");
	            
	            aocVo.setUAECpyPercnt("NC");
  	    aocVo.setUAEDedAED("NC");
          aocVo.setUAECopDedMinMax("NC");
          
  
	 		    aocVo.setNonUAEConPplAED("NC");
 	        aocVo.setNonUAEConPclAED("NC");
            aocVo.setNonUAEConPdlAED("NC");
            aocVo.setNonUAECpyPercnt("NC");
            aocVo.setNonUAEDedAED("NC");
      	aocVo.setNonUAECopDedMinMax("NC");
    
    	aocVo.setCovEmirates("NC"); 
          
  
			aocVo.setPplForDXB("NC");
			aocVo.setPclForDXB("NC");
			aocVo.setPdlForDXB("NC");
			aocVo.setCpyPercntForDXB("NC");
			aocVo.setDedForDXB("NC");
			aocVo.setCopDedMinMaxForDXB("NC");
			
			aocVo.setPplForAUH("NC");
			aocVo.setPclForAUH("NC");
			aocVo.setPdlForAUH("NC");
			aocVo.setCpyPercntForAUH("NC");
			aocVo.setDedForAUH("NC");
			aocVo.setCopDedMinMaxForAUH("NC");
			
			aocVo.setPplForAJM("NC");
			aocVo.setPclForAJM("NC");
			aocVo.setPdlForAJM("NC");
			aocVo.setCpyPercntForAJM("NC");
			aocVo.setDedForAJM("NC");
			aocVo.setCopDedMinMaxForAJM("NC");
			
			aocVo.setPplForFUJ("NC");
			aocVo.setPclForFUJ("NC");
			aocVo.setPdlForFUJ("NC");
			aocVo.setCpyPercntForFUJ("NC");
			aocVo.setDedForFUJ("NC");
			aocVo.setCopDedMinMaxForFUJ("NC");
				
			
			aocVo.setPplForRAK("NC");
			aocVo.setPclForRAK("NC");
			aocVo.setPdlForRAK("NC");
			aocVo.setCpyPercntForRAK("NC");
			aocVo.setDedForRAK("NC");
			aocVo.setCopDedMinMaxForRAK("NC");
			
			aocVo.setPplForSJH("NC");
			aocVo.setPclForSJH("NC");
			aocVo.setPdlForSJH("NC");
			aocVo.setCpyPercntForSJH("NC");
			aocVo.setDedForSJH("NC");
			aocVo.setCopDedMinMaxForSJH("NC");
			
			aocVo.setPplForUAQ("NC");
			aocVo.setPclForUAQ("NC");
			aocVo.setPdlForUAQ("NC");
			aocVo.setCpyPercntForUAQ("NC");
			aocVo.setDedForUAQ("NC");
			aocVo.setCopDedMinMaxForUAQ("NC");
			
			aocVo.setPplForKOH("NC");
			aocVo.setPclForKOH("NC");
			aocVo.setPdlForKOH("NC");
			aocVo.setCpyPercntForKOH("NC");
			aocVo.setDedForKOH("NC");
			aocVo.setCopDedMinMaxForKOH("NC");
			
			
			aocVo.setPplForALA("NC");
			aocVo.setPclForALA("NC");
			aocVo.setPdlForALA("NC");
			aocVo.setCpyPercntForALA("NC");
			aocVo.setDedForALA("NC");
			aocVo.setCopDedMinMaxForALA("NC");
 			 
  			
  		}

 
  		aocDataList.add(aocVo); 
			
  			 
  		 }
  		 
  		beneTypeFlag = ""; 
  		
  			 
  		// enhance end

	  		 
	  	 }
			
		}
        
        hmoDataMap.put("AOC",aocDataList);
		
	}

	      // if(frmDefineRules.getString("prodPolicyRuleSeqID").equals(policyvo.getPolicyNo())){
            generateHMOReport(hmoDataMap,policyNo,policycount,wb,sheet,rowhead,row);
            policycount++;
	      // }
        
    	}  //policy no
    	
    	request.setAttribute("displayMsg","HMO Policy Data Excel created successfully!");
    	request.setAttribute("GenRepYN","Y");
     // end by Govind
    	return this.getForward(strGenReport, mapping, request);
    	 }
    	 catch(TTKException expTTK)
         {
             return this.processExceptions(request, mapping, expTTK);
         }//end of catch(TTKException expTTK)
         catch(Exception exp)
         {
             return this.processExceptions(request, mapping, new TTKException(exp, "product"));
         }//end of catch(Exception exp)
    	
    }
    
  
    	 
    	 
     
    	 
    	 
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
    public ActionForward generateProPolReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
        	String File = "HMOReport";
        	String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xls";
        	File file=new File(fileDest);
             if(file.exists()){
            	 response.setContentType("application/vnd.ms-excel");
	    		response.addHeader("Content-Disposition","attachment; filename="+File+".xls");
	    		try(
	    		FileInputStream fileInputStream=new FileInputStream(file);
	    				){
	    		int data;
	    		try(
	    		ServletOutputStream servletOutputStream=response.getOutputStream();
	    		){
	    		while((data=fileInputStream.read())!=-1)servletOutputStream.write(data);
	    		}//ServletOutputStream
	    		}//FileInputStream
	    		return null;
             }else    	return this.getForward(strNewMOHProductRules, mapping, request);
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
    

    	 
    	 
    	 public void generateHMOReport(HashMap hmoMap, String pno,int policycount,HSSFWorkbook wb,HSSFSheet sheet,HSSFRow rowhead,HSSFRow row){
  
    	log.info("In generateHMOReport---"+policycount);
    	ArrayList<HmoOutPatientVO> outPatientVOs=null;
    	if(hmoMap.get("OPTS")!=null){
    		 outPatientVOs=(ArrayList<HmoOutPatientVO>)hmoMap.get("OPTS");
    		
    	}
    	
    	HMOGlobalVO globalVO=null;
if(hmoMap.get("GLOB")!=null){
	 globalVO=(HMOGlobalVO)hmoMap.get("GLOB");
    		
    	}
    	
HmoOptcDntlVo optcVo=null;
if(hmoMap.get("OPTC")!=null){
	 optcVo=(HmoOptcDntlVo)hmoMap.get("OPTC");
    		
    	}
    	
HmoOptcDntlVo dntlVo=null;
if(hmoMap.get("DNTL")!=null){
	 dntlVo=(HmoOptcDntlVo)hmoMap.get("DNTL");
    		
    	}
    	
ArrayList<HmoInPatientVO> inPatientVOs=null;
if(hmoMap.get("IPT")!=null){
	 inPatientVOs=(ArrayList<HmoInPatientVO>)hmoMap.get("IPT");
    		
    	}
    	
ArrayList<HmoInPatientVO> dcVOs=null;
if(hmoMap.get("DAYC")!=null){

	 dcVOs=(ArrayList<HmoInPatientVO>)hmoMap.get("DAYC");
    		
    	}
ArrayList<HmoSpcbVO> spcbVOs=null;	
if(hmoMap.get("SPCB")!=null){

	 spcbVOs=(ArrayList<HmoSpcbVO>)hmoMap.get("SPCB");
    		
    	}
    	
ArrayList<HmoAOCVo> aocVos=null;
if(hmoMap.get("AOC")!=null){
	
	 aocVos=(ArrayList<HmoAOCVo>)hmoMap.get("AOC");
    		
    	}
ArrayList<HmoGnrlExlcusnVO> exlcusnVOs=null;
if(hmoMap.get("GNRX")!=null){
	
	 exlcusnVOs=(ArrayList<HmoGnrlExlcusnVO>)hmoMap.get("GNRX");
    		
    	}
    	
 	String File = "HMOReport";
	String fileDest = TTKPropertiesReader.getPropertyValue("FileDestination")+File+".xls";
	File file=new File(fileDest);
	if(file.exists())file.delete();
	FileInputStream lFin = null;
  //  FileOutputStream lFout = null;
    POIFSFileSystem lPOIfs = null;
 //   File lFile = new File(fileDest);
    
    
    	try{
    		
    		if(policycount != 1){
//    		 lFout = new FileOutputStream(fileDest, true);
             lFin = new FileInputStream(fileDest);
             lPOIfs = new POIFSFileSystem(lFin);
             wb = new HSSFWorkbook(lPOIfs);
    		}
       	
          	 if(globalVO!=null){
           		 
                 sheet = wb.getSheet("GLOBAL");
                 if(sheet==null){
               	  
               	  sheet = wb.createSheet("GLOBAL");
              	      rowhead = sheet.createRow(0);
              	      
        /*      //   }
          
           		if(policycount == 1){
             	  sheet = wb.createSheet("GLOBAL");
             	  rowhead = sheet.createRow(0);
               	 
          		}else{
                    sheet = wb.getSheet("GLOBAL");
          		}
           		
           		if(policycount == 1){*/
         		    rowhead.createCell((short)0).setCellValue(new HSSFRichTextString("S.No"));
      			rowhead.createCell((short)1).setCellValue(new HSSFRichTextString("Policy No"));
      			rowhead.createCell((short)2).setCellValue(new HSSFRichTextString("Insurance Company Name"));
      			rowhead.createCell((short)3).setCellValue(new HSSFRichTextString("Product Name"));
      			rowhead.createCell((short)4).setCellValue(new HSSFRichTextString("PRODUCT NETWORK CATEGORY"));
      		    rowhead.createCell((short)5).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group ID"));
      		  rowhead.createCell((short)6).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group Name"));
				rowhead.createCell((short)7).setCellValue(new HSSFRichTextString("POLICY ADMINISTRATIVE SERVICE TYPE"));
				rowhead.createCell((short)8).setCellValue(new HSSFRichTextString("SUM INSURED"));
      		        rowhead.createCell((short)9).setCellValue(new HSSFRichTextString("GLOB-MEMBER-STATUS"));
      				rowhead.createCell((short)10).setCellValue(new HSSFRichTextString("GLOB-PREAUTH-ADMISN-PERIOD-WITHIN-UAE"));
      				rowhead.createCell((short)11).setCellValue(new HSSFRichTextString("GLOB-PREAUTH-ADMISN-PERIOD-OUTSIDE-UAE"));
      				rowhead.createCell((short)12).setCellValue(new HSSFRichTextString("GLOB-CLM-SUB-PRD-NTWK-CLM-WITHIN-UAE"));
      			    rowhead.createCell((short)13).setCellValue(new HSSFRichTextString("GLOB-CLM-SUB-PRD-NTWK-CLM-OUTSIDE-UAE"));
      			    rowhead.createCell((short)14).setCellValue(new HSSFRichTextString("GLOB-CLM-SUB-PRD-MEM-CLM-WITHIN-UAE"));
      				rowhead.createCell((short)15).setCellValue(new HSSFRichTextString("GLOB-CLM-SUB-PRD-MEM-CLM-OUTSIDE-UAE"));
           		}
           		
      				int rowCount = sheet.getLastRowNum()+1;
      			
       				 row = sheet.createRow(rowCount);
       				 
       			 
       			//	String slNo = String.valueOf(policycount);
       				 
       				String slNo = String.valueOf(rowCount);
       				
       				row.createCell((short)0).setCellValue(new HSSFRichTextString(slNo));
       				row.createCell((short)1).setCellValue(new HSSFRichTextString(globalVO.getPolicyNo()));
       				row.createCell((short)2).setCellValue(new HSSFRichTextString(globalVO.getInsuranceCompanyName()));
       				row.createCell((short)3).setCellValue(new HSSFRichTextString(globalVO.getProductName()));
       				row.createCell((short)4).setCellValue(new HSSFRichTextString(globalVO.getProductNetworkCategory()));
       				row.createCell((short)5).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupID()));
       				row.createCell((short)6).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupName()));
       				row.createCell((short)7).setCellValue(new HSSFRichTextString(globalVO.getPolicyAdmnstrtvSrvcType()));
       				row.createCell((short)8).setCellValue(new HSSFRichTextString(globalVO.getSumInsured()));
       				row.createCell((short)9).setCellValue(new HSSFRichTextString(globalVO.getGlobMemberStatus()));
       				row.createCell((short)10).setCellValue(new HSSFRichTextString(globalVO.getGlobPreauthAdmisnPeriodWithinUAE()));
       				row.createCell((short)11).setCellValue(new HSSFRichTextString(globalVO.getGlobPreauthAdmisnPeriodOutsideUAE()));
       				row.createCell((short)12).setCellValue(new HSSFRichTextString(globalVO.getGlobClmSubPrdNtwkClmWithinUAE()));
       				row.createCell((short)13).setCellValue(new HSSFRichTextString(globalVO.getGlobClmSubPrdNtwkClmOutsideUAE()));
       				row.createCell((short)14).setCellValue(new HSSFRichTextString(globalVO.getGlobClmSubPrdMemClmWithinUAE()));
       				row.createCell((short)15).setCellValue(new HSSFRichTextString(globalVO.getGlobClmSubPrdMemClmOutsideUAE()));
       				
       
           		 
           	 }
   		  
       	int index;
 			
       	if(outPatientVOs!=null){
       		
       		
            sheet = wb.getSheet("OUTPATIENT");
            if(sheet==null){
          	  
          	  sheet = wb.createSheet("OUTPATIENT");
         	      rowhead = sheet.createRow(0);
         	      
         //   }
       		
     /*  		if(policycount == 1){
       		 sheet = wb.createSheet("OUTPATIENT");
           	      rowhead = sheet.createRow(0);

        		}else{

                  sheet = wb.getSheet("OUTPATIENT");
                  if(sheet==null){
                	  
                	  sheet = wb.createSheet("OUTPATIENT");
               	      rowhead = sheet.createRow(0);
               	      
                	  
                  }
                 
        		}*/
       		
       	//	if(policycount == 1){
     		    rowhead.createCell((short)0).setCellValue(new HSSFRichTextString("S.No"));
  			rowhead.createCell((short)1).setCellValue(new HSSFRichTextString("Policy No"));
  			rowhead.createCell((short)2).setCellValue(new HSSFRichTextString("Insurance Company Name"));
  			rowhead.createCell((short)3).setCellValue(new HSSFRichTextString("Product Name"));
			rowhead.createCell((short)4).setCellValue(new HSSFRichTextString("PRODUCT NETWORK CATEGORY"));
  		    rowhead.createCell((short)5).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group ID"));
  		  rowhead.createCell((short)6).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group Name"));
			rowhead.createCell((short)7).setCellValue(new HSSFRichTextString("POLICY ADMINISTRATIVE SERVICE TYPE"));
			rowhead.createCell((short)8).setCellValue(new HSSFRichTextString("SUM INSURED"));
  		    rowhead.createCell((short)9).setCellValue(new HSSFRichTextString("OP_PPL"));
      		rowhead.createCell((short)10).setCellValue(new HSSFRichTextString("OP_CHRONIC_PPL"));
  	     			
  			rowhead.createCell((short)11).setCellValue(new HSSFRichTextString("OP_PED_PPL"));
  			rowhead.createCell((short)12).setCellValue(new HSSFRichTextString("OP_CHRON_PLUS_PED  _PPL"));
  			rowhead.createCell((short)13).setCellValue(new HSSFRichTextString("OP-Consult-GEOGRAPHICAL-LOCATION"));
  			rowhead.createCell((short)14).setCellValue(new HSSFRichTextString("OP-Consult-COUNTRIES COVERED"));
     	     			rowhead.createCell((short)15).setCellValue(new HSSFRichTextString("OP-Consult-EMIRATES COVERED"));
     	     		    rowhead.createCell((short)16).setCellValue(new HSSFRichTextString("OP-Consult-Encounter Type"));
  	     			rowhead.createCell((short)17).setCellValue(new HSSFRichTextString("OP-Consult-Provider Types"));
  	     			rowhead.createCell((short)18).setCellValue(new HSSFRichTextString("OP_CONSULT_PPL"));
  	     			rowhead.createCell((short)19).setCellValue(new HSSFRichTextString("OP_GP_CONSULT_COPAY"));
  	      		    rowhead.createCell((short)20).setCellValue(new HSSFRichTextString("OP_GP-CONSULT_DEDUCTABLE"));
  	    			
  	    			rowhead.createCell((short)21).setCellValue(new HSSFRichTextString("OP_GP_CONSULT_MIN/MAX"));
  	    			rowhead.createCell((short)22).setCellValue(new HSSFRichTextString("OP_SP_CONSULT_COPAY"));
  	    			rowhead.createCell((short)23).setCellValue(new HSSFRichTextString("OP_SP_CONSULT_DEDUCTABLE"));
  	    		    rowhead.createCell((short)24).setCellValue(new HSSFRichTextString("OP_SP_CONSULT_MIN/MAX"));
  	    		    rowhead.createCell((short)25).setCellValue(new HSSFRichTextString("OP_CONSULT_FOLLOW_PERIOD"));
  	        		rowhead.createCell((short)26).setCellValue(new HSSFRichTextString("OP_CONSULT_FLW_PERIOD_UNIT"));
  	    			rowhead.createCell((short)27).setCellValue(new HSSFRichTextString("OP_OVERALL_INVST_PPL"));
  	    			rowhead.createCell((short)28).setCellValue(new HSSFRichTextString("OP_LAB AND PATH_PPL"));
  	    			rowhead.createCell((short)29).setCellValue(new HSSFRichTextString("OP_EXCEPT LAB & PATH_PPL"));
  	    			rowhead.createCell((short)30).setCellValue(new HSSFRichTextString("OP_INVEST_LAB_PROVIDER_TYPES"));
  	    	     			
  	       	     			rowhead.createCell((short)31).setCellValue(new HSSFRichTextString("OP_INVEST_LAB_PREAPVL_REQD_YES/NO"));
  	       	     			rowhead.createCell((short)32).setCellValue(new HSSFRichTextString("OP_INVEST_LAB_PREAPVL_REQD_LIMIT"));
  	       	     		    rowhead.createCell((short)33).setCellValue(new HSSFRichTextString("OP_INV_LAB_PPL"));
  	    	     			rowhead.createCell((short)34).setCellValue(new HSSFRichTextString("OP_INV_LAB_NUM_OF_SESSIONS"));
  	    	     			rowhead.createCell((short)35).setCellValue(new HSSFRichTextString("OP_INV_LAB_COPAY"));
  	    	     			rowhead.createCell((short)36).setCellValue(new HSSFRichTextString("OP_INV_LAB_DEDUCTABLE"));
  	    	     			rowhead.createCell((short)37).setCellValue(new HSSFRichTextString("OP_INV_LAB_COPDED_MINMAX"));
  	    	      			rowhead.createCell((short)38).setCellValue(new HSSFRichTextString("OP_INVEST_PAT_PROVIDER_TYPES"));
  	       	     			rowhead.createCell((short)39).setCellValue(new HSSFRichTextString("OP_INVEST_PAT_PREAPVL_REQD_YES/NO"));
  	       	     			rowhead.createCell((short)40).setCellValue(new HSSFRichTextString("OP_INVEST_PAT_PREAPVL_REQD_LIMIT"));
  	    	     			
  	       	     		    rowhead.createCell((short)41).setCellValue(new HSSFRichTextString("OP_INV_PAT_PPL"));
  	    	     			rowhead.createCell((short)42).setCellValue(new HSSFRichTextString("OP_INV_PAT_NUM_OF_SESSIONS"));
  	    	     			rowhead.createCell((short)43).setCellValue(new HSSFRichTextString("OP_INV_PAT_COPAY"));
  	    	     			rowhead.createCell((short)44).setCellValue(new HSSFRichTextString("OP_INV_PAT_DEDUCTABLE"));
  	    	     			rowhead.createCell((short)45).setCellValue(new HSSFRichTextString("OP_INV_PAT_COPDED_MINMAX"));
  	    	      			rowhead.createCell((short)46).setCellValue(new HSSFRichTextString("OP_INVEST_ULTRA_PROVIDER_TYPES"));
  	       	     			rowhead.createCell((short)47).setCellValue(new HSSFRichTextString("OP_INVEST_ULTRA_PREAPVL_REQD_YES/NO"));
  	       	     			rowhead.createCell((short)48).setCellValue(new HSSFRichTextString("OP_INVEST_ULTRA_PREAPVL_REQD_LIMIT"));
  	       	     		    rowhead.createCell((short)49).setCellValue(new HSSFRichTextString("OP_INV_ULTRA_PPL"));
  	    	     			rowhead.createCell((short)50).setCellValue(new HSSFRichTextString("OP_INV_ULTRA_NUM_OF_SESSIONS"));
  	    	     			
  	    	     			rowhead.createCell((short)51).setCellValue(new HSSFRichTextString("OP_INV_ULTRA_COPAY"));
  	    	     			rowhead.createCell((short)52).setCellValue(new HSSFRichTextString("OP_INV_ULTRA_DEDUCTABLE"));
  	    	     			rowhead.createCell((short)53).setCellValue(new HSSFRichTextString("OP_INV_ULTRA_COPDED_MINMAX"));
  	    	     			rowhead.createCell((short)54).setCellValue(new HSSFRichTextString("OP_INVEST_CTSCAN_PROVIDER_TYPES"));
  	    	     			rowhead.createCell((short)55).setCellValue(new HSSFRichTextString("OP_INVEST_CTSCAN_PREAPVL_REQD_YES/NO"));
  	       	     			rowhead.createCell((short)56).setCellValue(new HSSFRichTextString("OP_INVEST_CTSCAN_PREAPVL_REQD_LIMIT"));
  	       	     		    rowhead.createCell((short)57).setCellValue(new HSSFRichTextString("OP_INV_CTSCAN_PPL"));
  	    	     			rowhead.createCell((short)58).setCellValue(new HSSFRichTextString("OP_INV_CTSCAN_NUM_OF_SESSIONS"));
  	    	     			rowhead.createCell((short)59).setCellValue(new HSSFRichTextString("OP_INV_CTSCAN_COPAY"));
  	    	     			rowhead.createCell((short)60).setCellValue(new HSSFRichTextString("OP_INV_CTSCAN_DEDUCTABLE"));
  	    	     			
  	    	     			rowhead.createCell((short)61).setCellValue(new HSSFRichTextString("OP_INV_CTSCAN_COPDED_MINMAX"));
  	    	     			rowhead.createCell((short)62).setCellValue(new HSSFRichTextString("OP_INVEST_MRI_PROVIDER_TYPES"));
  	    	     			rowhead.createCell((short)63).setCellValue(new HSSFRichTextString("OP_INVEST_MRI_PREAPVL_REQD_YES/NO"));
  	       	     			rowhead.createCell((short)64).setCellValue(new HSSFRichTextString("OP_INVEST_MRI_PREAPVL_REQD_LIMIT"));
  	       	     		    rowhead.createCell((short)65).setCellValue(new HSSFRichTextString("OP_INV_MRI_PPL"));
  	    	     			rowhead.createCell((short)66).setCellValue(new HSSFRichTextString("OP_INV_MRI_NUM_OF_SESSIONS"));
  	    	     			rowhead.createCell((short)67).setCellValue(new HSSFRichTextString("OP_INV_MRI_COPAY"));
  	    	     			rowhead.createCell((short)68).setCellValue(new HSSFRichTextString("OP_INV_MRI_DEDUCTABLE"));
  	    	     			rowhead.createCell((short)69).setCellValue(new HSSFRichTextString("OP_INV_MRI_COPDED_MINMAX"));
  	    	     			rowhead.createCell((short)70).setCellValue(new HSSFRichTextString("OP_INVEST_DIAG&THERAP_PROVIDER_TYPES"));
  	    	     			
  	    	     			rowhead.createCell((short)71).setCellValue(new HSSFRichTextString("OP_INVEST_DIAG&THERAP_PREAPVL_REQD_YES/NO"));
  	       	     			rowhead.createCell((short)72).setCellValue(new HSSFRichTextString("OP_INVEST_DIAG&THERAP_PREAPVL_REQD_LIMIT"));
  	       	     		    rowhead.createCell((short)73).setCellValue(new HSSFRichTextString("OP_INV_DIAG&THERAP_PPL"));
  	    	     			rowhead.createCell((short)74).setCellValue(new HSSFRichTextString("OP_INV_DIAG&THERAP_NUM_OF_SESSIONS"));
  	    	     			rowhead.createCell((short)75).setCellValue(new HSSFRichTextString("OP_INV_DIAG&THERAP_COPAY"));
  	    	     			rowhead.createCell((short)76).setCellValue(new HSSFRichTextString("OP_INV_DIAG&THERAP_DEDUCTABLE"));
  	    	     			rowhead.createCell((short)77).setCellValue(new HSSFRichTextString("OP_INV_DIAG&THERAP_COPDED_MINMAX"));
  	    	     			rowhead.createCell((short)78).setCellValue(new HSSFRichTextString("OP_PHYSIO_PROVIDER_FACILITY_TYPE"));
  	       	     			rowhead.createCell((short)79).setCellValue(new HSSFRichTextString("OP_PHYSIO_PREAPPROVAL_REQD_YES/NO"));
  	       	     		    rowhead.createCell((short)80).setCellValue(new HSSFRichTextString("OP_PHYSIO_PREAPPROVAL_LIMIT"));
  	    	     			
  	    	     			rowhead.createCell((short)81).setCellValue(new HSSFRichTextString("OP_PHYSIO_PPL"));
  	    	     			rowhead.createCell((short)82).setCellValue(new HSSFRichTextString("OP_PHYSIO_NO-OF-SESS"));
  	    	     			rowhead.createCell((short)83).setCellValue(new HSSFRichTextString("OP_PHYSIO_PER_SESS_LIMIT"));
  	    	     			rowhead.createCell((short)84).setCellValue(new HSSFRichTextString("OP_PHYSIO_COPAY"));
  	    	     			rowhead.createCell((short)85).setCellValue(new HSSFRichTextString("OP_PHYSIO_OVR_DEDUCTABLE"));
  	    	     			rowhead.createCell((short)86).setCellValue(new HSSFRichTextString("OP_PHYSIO_COPDED_MINMAX"));
  	    	     			rowhead.createCell((short)87).setCellValue(new HSSFRichTextString("OP_PHARMACTL_PROVIDER_FACILITY"));
  	       	     			rowhead.createCell((short)88).setCellValue(new HSSFRichTextString("OP_PHARMACTL_PREAPPROVAL_REQD_YES/NO"));
  	       	     		    rowhead.createCell((short)89).setCellValue(new HSSFRichTextString("OP_PHARMACTL_PREAPPROVAL_LIMIT"));
  	    	     			rowhead.createCell((short)90).setCellValue(new HSSFRichTextString("OP_PHARMACTL_PPL"));
  	    	     			
  	    	     			rowhead.createCell((short)91).setCellValue(new HSSFRichTextString("OP_PHARMACTL_COPAY"));
  	    	     			rowhead.createCell((short)92).setCellValue(new HSSFRichTextString("OP_PHARMACTL_DEDUCTABLE"));
  	    	     			rowhead.createCell((short)93).setCellValue(new HSSFRichTextString("OP_PHARMACTL_COPDED_MINMAX"));
  	    	     			
       		}
  	     			 index = 1;
  	     		
  	     			 
  	     			for (HmoOutPatientVO hmoOutPatientVO : outPatientVOs) {
  	     				// row = sheet.createRow(index);
  	     				int rowCount = sheet.getLastRowNum()+1;
  	    				 row = sheet.createRow(rowCount);
  	     				
  	     				row.createCell((short)0).setCellValue(new HSSFRichTextString(String.valueOf(rowCount)));
  	     				row.createCell((short)1).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getPolicyNo()));
  	     				row.createCell((short)2).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getInsuranceCompanyName()));
  	     				row.createCell((short)3).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getProductName()));
  	     				row.createCell((short)4).setCellValue(new HSSFRichTextString(globalVO.getProductNetworkCategory()));
  	       				row.createCell((short)5).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupID()));
  	       				row.createCell((short)6).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupName()));
  	       				row.createCell((short)7).setCellValue(new HSSFRichTextString(globalVO.getPolicyAdmnstrtvSrvcType()));
  	       				row.createCell((short)8).setCellValue(new HSSFRichTextString(globalVO.getSumInsured()));
  	     				
  	     				row.createCell((short)9).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPPL()));
  	     				row.createCell((short)10).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpChronicPPL()));
  	     				row.createCell((short)11).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPedPPL()));
  	     				row.createCell((short)12).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpChronPlusPedPPL()));
  	     				row.createCell((short)13).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPConsultGeographicalLocation()));
  	     				row.createCell((short)14).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPConsultCountriesCovered()));
  	     				row.createCell((short)15).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPConsultEmiratesCovered()));
  	     				row.createCell((short)16).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPConsultEncounterType()));
  	     				row.createCell((short)17).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPConsultProviderTypes()));
  	     				row.createCell((short)18).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPConsultPpl()));
  	     				row.createCell((short)19).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPGpConsultCopay()));
  	     				row.createCell((short)20).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPGpConsultDEDUCTABLE()));
  	     				row.createCell((short)21).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPGpConsultMinMax()));
  	     				row.createCell((short)22).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPSpConsultCopay()));
  	     				row.createCell((short)23).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPSpConsultDEDUCTABLE()));
  	     				row.createCell((short)24).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOPSpConsultMinMax()));
  	     				row.createCell((short)25).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpConsultFlwPeriod()));
  	     				row.createCell((short)26).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpConsultFlwPeriodUnit()));
  	     				row.createCell((short)27).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpOverallInvstPPL()));
  	     				row.createCell((short)28).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpLabAndPathPPL()));
  	     				row.createCell((short)29).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpExceptLabAndPathPPL()));
  	     				row.createCell((short)30).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvLabProviderFacilityTypes()));
  	     				row.createCell((short)31).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvLabPreapprovalReqdYesNo()));
  	     				row.createCell((short)32).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvLabPreapprovalReqdLimit()));
  	     				row.createCell((short)33).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvLabPpl()));
  	     				row.createCell((short)34).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvLabNoOfSess()));
  	     				row.createCell((short)35).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvLabCopay()));
  	     				row.createCell((short)36).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvLabDeductable()));
  	     				row.createCell((short)37).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvLabCopdedMINMAX()));
  	     				row.createCell((short)38).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvPatProviderFacilityTypes()));
  	     				row.createCell((short)39).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvPatPreapprovalReqdYesNo()));
  	     				row.createCell((short)40).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvPatPreapprovalReqdLimit()));
  	     				row.createCell((short)41).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvPatPpl()));
  	     				row.createCell((short)42).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvPatNoOfSess()));
  	     				row.createCell((short)43).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvPatCopay()));
  	     				row.createCell((short)44).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvPatDeductable()));
  	     				row.createCell((short)45).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvPatCopdedMINMAX()));
  	     				row.createCell((short)46).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvUltraProviderFacilityTypes()));
  	     				row.createCell((short)47).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvUltraPreapprovalReqdYesNo()));
  	     				row.createCell((short)48).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvUltraPreapprovalReqdLimit()));
  	     				row.createCell((short)49).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvUltraPpl()));
  	     				row.createCell((short)50).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvUltraNoOfSess()));
  	     				row.createCell((short)51).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvUltraCopay()));
  	     				row.createCell((short)52).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvUltraDeductable()));
  	     				row.createCell((short)53).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvUltraCopdedMINMAX()));
  	     				row.createCell((short)54).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvCtScanProviderFacilityTypes()));
  	     				row.createCell((short)55).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvCtScanPreapprovalReqdYesNo()));
  	     				row.createCell((short)56).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvCtScanPreapprovalReqdLimit()));
  	     				row.createCell((short)57).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvCtScanPpl()));
  	     				row.createCell((short)58).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvCtScanNoOfSess()));
  	     				row.createCell((short)59).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvCtScanCopay()));
  	     				row.createCell((short)60).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvCtScanDeductable()));
  	     				row.createCell((short)61).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvCtScanCopdedMINMAX()));
  	     				row.createCell((short)62).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvMriProviderFacilityTypes()));
  	     				row.createCell((short)63).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvMriPreapprovalReqdYesNo()));
  	     				row.createCell((short)64).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvMriPreapprovalReqdLimit()));
  	     				row.createCell((short)65).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvMriPpl()));
  	     				row.createCell((short)66).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvMriNoOfSess()));
  	     				row.createCell((short)67).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvMriCopay()));
  	     				row.createCell((short)68).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvMriDeductable()));
  	     				row.createCell((short)69).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvMriCopdedMINMAX()));
  	     				row.createCell((short)70).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvDiagAndTherapProviderTypes()));
  	     				row.createCell((short)71).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvDiagAndTherapPreapprovalReqdYesNo()));
  	     				row.createCell((short)72).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvDiagAndTherapPreapprovalReqdLimit()));
  	     				row.createCell((short)73).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvDiagAndTherapPpl()));
  	     				row.createCell((short)74).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvDiagAndTherapNoOfSess()));
  	     				row.createCell((short)75).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvDiagAndTherapCopay()));
  	     				row.createCell((short)76).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvDiagAndTherapDeductable()));
  	     				row.createCell((short)77).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpInvDiagAndTherapCopdedMINMAX()));
  	     				
  	     				row.createCell((short)78).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPhysioProviderFacilityTypes()));
  	     				row.createCell((short)79).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPhysioPreapprovalReqdYesNo()));
  	     				row.createCell((short)80).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPhysioPreapprovalLimit()));
  	     				row.createCell((short)81).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPhysioPpl()));
  	     				row.createCell((short)82).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPhysioNOOfSess()));
  	     				row.createCell((short)83).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPhysioPerSessLimit()));
  	     				row.createCell((short)84).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPhysioCopay()));
  	     				row.createCell((short)85).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPhysioOvrDeductable()));
  	     				row.createCell((short)86).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPhysioCopdedMINMAX()));
  	     				
  	     				row.createCell((short)87).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPharmactlProviderFacility()));
  	     				row.createCell((short)88).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPharmactlPreapprovalReqdYesNo()));
  	     				row.createCell((short)89).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPharmactlPreapprovalLimit()));
  	     				row.createCell((short)90).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPharmactlPpl()));
  	     				row.createCell((short)91).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPharmactlCopay()));
  	     				row.createCell((short)92).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPharmactlDeductable()));
  	     				row.createCell((short)93).setCellValue(new HSSFRichTextString(hmoOutPatientVO.getOpPharmactlCopdedMINMAX()));
  	     	
  	     	
  	     				index++;
  	     				
  					}
  	     			
  	   
       		
       	}
		    
       	  			// INPATIENT STARTED
       	
       	if(inPatientVOs!=null){
       		
       		
       		sheet = wb.getSheet("INPATIENT");
       		if(sheet==null){
       			
       			sheet = wb.createSheet("INPATIENT");
         	      rowhead = sheet.createRow(0);
       			
       	//	}
       		
  /*     		int  policycnt = 0;
	   	
	   		if(policycount == 1){
	       		 sheet = wb.createSheet("INPATIENT");
	           	      rowhead = sheet.createRow(0);
	           	   policycnt = 1;

	        		}else{
	        					//System.out.println("policycount else IPT---"+wb.getSheet("INPATIENT"));
	        				
	        					String inpatientData = String.valueOf(wb.getSheet("INPATIENT"));
        					//System.out.println("policycount inpatientData ---"+inpatientData);

					   	if(inpatientData.equals("null")){
					   	 sheet = wb.createSheet("INPATIENT");
					   	rowhead = sheet.createRow(0);
					    	policycnt = 1;
					   	}else{
			                  sheet = wb.getSheet("INPATIENT");
					   	}
	        		}*/
	   		  
	   	//	if(policycnt == 1){
	   		    rowhead.createCell((short)0).setCellValue(new HSSFRichTextString("S.No"));
				rowhead.createCell((short)1).setCellValue(new HSSFRichTextString("Policy No"));
				rowhead.createCell((short)2).setCellValue(new HSSFRichTextString("Insurance Company Name"));
				rowhead.createCell((short)3).setCellValue(new HSSFRichTextString("Product Name"));
				rowhead.createCell((short)4).setCellValue(new HSSFRichTextString("PRODUCT NETWORK CATEGORY"));
      		    rowhead.createCell((short)5).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group ID"));
      		  rowhead.createCell((short)6).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group Name"));
				rowhead.createCell((short)7).setCellValue(new HSSFRichTextString("POLICY ADMINISTRATIVE SERVICE TYPE"));
				rowhead.createCell((short)8).setCellValue(new HSSFRichTextString("SUM INSURED"));
			    rowhead.createCell((short)9).setCellValue(new HSSFRichTextString("IP_DAYC_PPL"));
	    		rowhead.createCell((short)10).setCellValue(new HSSFRichTextString("IP_DAYC_CHRONIC_PPL"));
				
				rowhead.createCell((short)11).setCellValue(new HSSFRichTextString("IP_DAYC_PED_PPL"));
				rowhead.createCell((short)12).setCellValue(new HSSFRichTextString("IP_DAYC_CHRON_PLUS_PED_PPL"));
				rowhead.createCell((short)13).setCellValue(new HSSFRichTextString("IP_DAYC_ONE_MEDICAL_PPL"));
				rowhead.createCell((short)14).setCellValue(new HSSFRichTextString("IP_PPL"));
				rowhead.createCell((short)15).setCellValue(new HSSFRichTextString("IP-ROOM&BOARD-ROOM-TYPES"));
				rowhead.createCell((short)16).setCellValue(new HSSFRichTextString("IP-ROOM&BOARD-PPL"));
				rowhead.createCell((short)17).setCellValue(new HSSFRichTextString("IP-ROOM&BOARD-COPAY"));
				rowhead.createCell((short)18).setCellValue(new HSSFRichTextString("IP-ROOM&BOARD-DEDUCTIBLES"));
				rowhead.createCell((short)19).setCellValue(new HSSFRichTextString("IP-ROOM&BOARD-COPDED-MINMAX"));
				rowhead.createCell((short)20).setCellValue(new HSSFRichTextString("IP-ICU-EXP-PPL"));
				
				rowhead.createCell((short)21).setCellValue(new HSSFRichTextString("IP-ICU-EXP-COPAY"));
				rowhead.createCell((short)22).setCellValue(new HSSFRichTextString("IP-ICU-EXP-DEDUCTIBLES"));
				rowhead.createCell((short)23).setCellValue(new HSSFRichTextString("IP-ICU-EXP-COPDED-MINMAX"));
				rowhead.createCell((short)24).setCellValue(new HSSFRichTextString("IP-Consult-GEOGRAPHICAL-LOCATION-COVERED"));
				rowhead.createCell((short)25).setCellValue(new HSSFRichTextString("IP-Consult-COUNTRIES-COVERED"));
	   	     			rowhead.createCell((short)26).setCellValue(new HSSFRichTextString("IP-Consult-EMIRATES COVERED"));
	   	     		    rowhead.createCell((short)27).setCellValue(new HSSFRichTextString("IP-Consult-Encounter Type"));
		     			rowhead.createCell((short)28).setCellValue(new HSSFRichTextString("IP-Consult-Provider Types"));
		     			rowhead.createCell((short)29).setCellValue(new HSSFRichTextString("IP_CONSULT_PPL"));
		     			rowhead.createCell((short)30).setCellValue(new HSSFRichTextString("IP_GP_CONSULT_COPAY"));
				
		      		    rowhead.createCell((short)31).setCellValue(new HSSFRichTextString("IP_GP-CONSULT_DEDUCTABLE"));
		    			rowhead.createCell((short)32).setCellValue(new HSSFRichTextString("IP_GP_CONSULT_MIN/MAX"));
		    			rowhead.createCell((short)33).setCellValue(new HSSFRichTextString("IP_SP_CONSULT_COPAY"));
		    			rowhead.createCell((short)34).setCellValue(new HSSFRichTextString("IP_SP_CONSULT_DEDUCTABLE"));
		    		    rowhead.createCell((short)35).setCellValue(new HSSFRichTextString("IP_SP_CONSULT_MIN/MAX"));
		    		    rowhead.createCell((short)36).setCellValue(new HSSFRichTextString("IP_CONSULT_FOLLOW_PERIOD"));
		        		rowhead.createCell((short)37).setCellValue(new HSSFRichTextString("IP_CONSULT_FLW_PERIOD_UNIT"));
		        		rowhead.createCell((short)38).setCellValue(new HSSFRichTextString("IP-SURG&ANAEST-SURG-PPL"));
	     				rowhead.createCell((short)39).setCellValue(new HSSFRichTextString("IP-SURG&ANAEST-SURG-COPAY"));
	     				rowhead.createCell((short)40).setCellValue(new HSSFRichTextString("IP-SURG&ANAEST-SURG-DEDUCTIBLES"));
		     			
	     				rowhead.createCell((short)41).setCellValue(new HSSFRichTextString("IP-SURG&ANAEST-SURG-COPDED-MINMAX"));
	     				rowhead.createCell((short)42).setCellValue(new HSSFRichTextString("IP-SURG&ANAEST-ANAEST-PPL"));
	     				rowhead.createCell((short)43).setCellValue(new HSSFRichTextString("IP-SURG&ANAEST-ANAEST-COPAY"));
	     				rowhead.createCell((short)44).setCellValue(new HSSFRichTextString("IP-SURG&ANAEST-ANAEST-DEDUCTIBLES"));
	     				rowhead.createCell((short)45).setCellValue(new HSSFRichTextString("IP-SURG&ANAEST-ANAEST-COPDED-MINMAX"));
	     				rowhead.createCell((short)46).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-GEO-LOCAT"));
	     				rowhead.createCell((short)47).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-COUNTRIES"));
	     				rowhead.createCell((short)48).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-EMIRATES"));
	     				rowhead.createCell((short)49).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-PROVID-TYPE"));
	     				rowhead.createCell((short)50).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-PROVID-FAC-TYPE"));
		        		
	     				rowhead.createCell((short)51).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-PREAPRVL-REQD-YES/NO"));
	     				rowhead.createCell((short)52).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-PREAPRVL-LIMIT"));
	     				rowhead.createCell((short)53).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-FROM-AGE"));
	     				rowhead.createCell((short)54).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-TO-AGE"));
	     				rowhead.createCell((short)55).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-FROM-TO-AGE-UNIT"));
	     				rowhead.createCell((short)56).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-PPL"));
	     				rowhead.createCell((short)57).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-NUM-DAYS-ALLWD"));
	     				rowhead.createCell((short)58).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-MAX-LIM-ALLWD-PER-DAY"));
	     				rowhead.createCell((short)59).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-COPAY"));
	     				rowhead.createCell((short)60).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-DEDUCTIBLE"));
	     				
	     				rowhead.createCell((short)61).setCellValue(new HSSFRichTextString("IP-COMPN-CHARG-COPDED-MINMAX"));
		    			rowhead.createCell((short)62).setCellValue(new HSSFRichTextString("IP_OVERALL_INVST_PPL"));
		    			rowhead.createCell((short)63).setCellValue(new HSSFRichTextString("IP_LAB AND PATH_PPL"));
		    			rowhead.createCell((short)64).setCellValue(new HSSFRichTextString("IP_EXCEPT LAB & PATH_PPL"));
		    			        rowhead.createCell((short)65).setCellValue(new HSSFRichTextString("IP_INVEST_LAB_PROVIDER_TYPES"));
		       	     			rowhead.createCell((short)66).setCellValue(new HSSFRichTextString("IP_INVEST_LAB_PREAPVL_REQD_YES/NO"));
		       	     			rowhead.createCell((short)67).setCellValue(new HSSFRichTextString("IP_INVEST_LAB_PREAPVL_REQD_LIMIT"));
		       	     		    rowhead.createCell((short)68).setCellValue(new HSSFRichTextString("IP_INV_LAB_PPL"));
		    	     			rowhead.createCell((short)69).setCellValue(new HSSFRichTextString("IP_INV_LAB_NUM_OF_SESSIONS"));
		    	     			rowhead.createCell((short)70).setCellValue(new HSSFRichTextString("IP_INV_LAB_COPAY"));
		        		
		    	     			rowhead.createCell((short)71).setCellValue(new HSSFRichTextString("IP_INV_LAB_DEDUCTABLE"));
		    	     			rowhead.createCell((short)72).setCellValue(new HSSFRichTextString("IP_INV_LAB_COPDED_MINMAX"));
		    	      			rowhead.createCell((short)73).setCellValue(new HSSFRichTextString("IP_INVEST_PAT_PROVIDER_TYPES"));
		       	     			rowhead.createCell((short)74).setCellValue(new HSSFRichTextString("IP_INVEST_PAT_PREAPVL_REQD_YES/NO"));
		       	     			rowhead.createCell((short)75).setCellValue(new HSSFRichTextString("IP_INVEST_PAT_PREAPVL_REQD_LIMIT"));
		       	     		    rowhead.createCell((short)76).setCellValue(new HSSFRichTextString("IP_INV_PAT_PPL"));
		    	     			rowhead.createCell((short)77).setCellValue(new HSSFRichTextString("IP_INV_PAT_NUM_OF_SESSIONS"));
		    	     			rowhead.createCell((short)78).setCellValue(new HSSFRichTextString("IP_INV_PAT_COPAY"));
		    	     			rowhead.createCell((short)79).setCellValue(new HSSFRichTextString("IP_INV_PAT_DEDUCTABLE"));
		    	     			rowhead.createCell((short)80).setCellValue(new HSSFRichTextString("IP_INV_PAT_COPDED_MINMAX"));
		    			
		    	      			rowhead.createCell((short)81).setCellValue(new HSSFRichTextString("IP_INVEST_ULTRA_PROVIDER_TYPES"));
		       	     			rowhead.createCell((short)82).setCellValue(new HSSFRichTextString("IP_INVEST_ULTRA_PREAPVL_REQD_YES/NO"));
		       	     			rowhead.createCell((short)83).setCellValue(new HSSFRichTextString("IP_INVEST_ULTRA_PREAPVL_REQD_LIMIT"));
		       	     		    rowhead.createCell((short)84).setCellValue(new HSSFRichTextString("IP_INV_ULTRA_PPL"));
		    	     			rowhead.createCell((short)85).setCellValue(new HSSFRichTextString("IP_INV_ULTRA_NUM_OF_SESSIONS"));
		    	     			rowhead.createCell((short)86).setCellValue(new HSSFRichTextString("IP_INV_ULTRA_COPAY"));
		    	     			rowhead.createCell((short)87).setCellValue(new HSSFRichTextString("IP_INV_ULTRA_DEDUCTABLE"));
		    	     			rowhead.createCell((short)88).setCellValue(new HSSFRichTextString("IP_INV_ULTRA_COPDED_MINMAX"));
		    	     			rowhead.createCell((short)89).setCellValue(new HSSFRichTextString("IP_INVEST_CTSCAN_PROVIDER_TYPES"));
		    	     			rowhead.createCell((short)90).setCellValue(new HSSFRichTextString("IP_INVEST_CTSCAN_PREAPVL_REQD_YES/NO"));
		    	     			
		       	     			rowhead.createCell((short)91).setCellValue(new HSSFRichTextString("IP_INVEST_CTSCAN_PREAPVL_REQD_LIMIT"));
		       	     		    rowhead.createCell((short)92).setCellValue(new HSSFRichTextString("IP_INV_CTSCAN_PPL"));
		    	     			rowhead.createCell((short)93).setCellValue(new HSSFRichTextString("IP_INV_CTSCAN_NUM_OF_SESSIONS"));
		    	     			rowhead.createCell((short)94).setCellValue(new HSSFRichTextString("IP_INV_CTSCAN_COPAY"));
		    	     			rowhead.createCell((short)95).setCellValue(new HSSFRichTextString("IP_INV_CTSCAN_DEDUCTABLE"));
		    	     			rowhead.createCell((short)96).setCellValue(new HSSFRichTextString("IP_INV_CTSCAN_COPDED_MINMAX"));
		    	     			rowhead.createCell((short)97).setCellValue(new HSSFRichTextString("IP_INVEST_MRI_PROVIDER_TYPES"));
		    	     			rowhead.createCell((short)98).setCellValue(new HSSFRichTextString("IP_INVEST_MRI_PREAPVL_REQD_YES/NO"));
		       	     			rowhead.createCell((short)99).setCellValue(new HSSFRichTextString("IP_INVEST_MRI_PREAPVL_REQD_LIMIT"));
		       	     		    rowhead.createCell((short)100).setCellValue(new HSSFRichTextString("IP_INV_MRI_PPL"));
		    	     			
		    	     			rowhead.createCell((short)101).setCellValue(new HSSFRichTextString("IP_INV_MRI_NUM_OF_SESSIONS"));
		    	     			rowhead.createCell((short)102).setCellValue(new HSSFRichTextString("IP_INV_MRI_COPAY"));
		    	     			rowhead.createCell((short)103).setCellValue(new HSSFRichTextString("IP_INV_MRI_DEDUCTABLE"));
		    	     			rowhead.createCell((short)104).setCellValue(new HSSFRichTextString("IP_INV_MRI_COPDED_MINMAX"));
		    	     			rowhead.createCell((short)105).setCellValue(new HSSFRichTextString("IP_INVEST_DIAG&THERAP_PROVIDER_TYPES"));
		    	     			rowhead.createCell((short)106).setCellValue(new HSSFRichTextString("IP_INVEST_DIAG&THERAP_PREAPVL_REQD_YES/NO"));
		       	     			rowhead.createCell((short)107).setCellValue(new HSSFRichTextString("IP_INVEST_DIAG&THERAP_PREAPVL_REQD_LIMIT"));
		       	     		    rowhead.createCell((short)108).setCellValue(new HSSFRichTextString("IP_INV_DIAG&THERAP_PPL"));
		    	     			rowhead.createCell((short)109).setCellValue(new HSSFRichTextString("IP_INV_DIAG&THERAP_NUM_OF_SESSIONS"));
		    	     			rowhead.createCell((short)110).setCellValue(new HSSFRichTextString("IP_INV_DIAG&THERAP_COPAY"));
		    	     			
		    	     			rowhead.createCell((short)111).setCellValue(new HSSFRichTextString("IP_INV_DIAG&THERAP_DEDUCTABLE"));
		    	     			rowhead.createCell((short)112).setCellValue(new HSSFRichTextString("IP_INV_DIAG&THERAP_COPDED_MINMAX"));
		    	     			rowhead.createCell((short)113).setCellValue(new HSSFRichTextString("IP-MEDICATIONS-PPL"));
			     				rowhead.createCell((short)114).setCellValue(new HSSFRichTextString("IP-MEDICATIONS-COPAY"));
			     				rowhead.createCell((short)115).setCellValue(new HSSFRichTextString("IP-MEDICATIONS-DEDUCTIBLES"));
			     				rowhead.createCell((short)116).setCellValue(new HSSFRichTextString("IP-MEDICATIONS-COPDED-MINMAX"));
			     				rowhead.createCell((short)117).setCellValue(new HSSFRichTextString("IP-IVFLUIDS-PPL"));
			     				rowhead.createCell((short)118).setCellValue(new HSSFRichTextString("IP-IVFLUIDS-COPAY"));
			     				rowhead.createCell((short)119).setCellValue(new HSSFRichTextString("IP-IVFLUIDS-DEDUCTIBLES"));
			     				rowhead.createCell((short)120).setCellValue(new HSSFRichTextString("IP-IVFLUIDS-COPDED-MINMAX"));
		    	     			
			     				rowhead.createCell((short)121).setCellValue(new HSSFRichTextString("IP-BLOOD-TRANSFUSN-PPL"));
			     				rowhead.createCell((short)122).setCellValue(new HSSFRichTextString("IP-BLOOD-TRANSFUSN-COPAY"));
			     				rowhead.createCell((short)123).setCellValue(new HSSFRichTextString("IP-BLOOD-TRANSFUSN-DEDUCTIBLES"));
			     				rowhead.createCell((short)124).setCellValue(new HSSFRichTextString("IP-BLOOD-TRANSFUSN-COPDED-MINMAX"));
			     				rowhead.createCell((short)125).setCellValue(new HSSFRichTextString("IP-ANALGESICS-PPL"));
			     				rowhead.createCell((short)126).setCellValue(new HSSFRichTextString("IP-ANALGESICS-COPAY"));
			     				rowhead.createCell((short)127).setCellValue(new HSSFRichTextString("IP-ANALGESICS-DEDUCTIBLES"));
			     				rowhead.createCell((short)128).setCellValue(new HSSFRichTextString("IP-ANALGESICS-COPDED-MINMAX"));
			     				rowhead.createCell((short)129).setCellValue(new HSSFRichTextString("IP-SURGCL-IMPLTS-PPL"));
			     				rowhead.createCell((short)130).setCellValue(new HSSFRichTextString("IP-SURGCL-IMPLTS-COPAY"));
		    	     			
			     				rowhead.createCell((short)131).setCellValue(new HSSFRichTextString("IP-SURGCL-IMPLTS-DEDUCTIBLES"));
			     				rowhead.createCell((short)132).setCellValue(new HSSFRichTextString("IP-SURGCL-IMPLTS-COPDED-MINMAX"));
			     				rowhead.createCell((short)133).setCellValue(new HSSFRichTextString("IP-CHEMO-PPL"));
			     				rowhead.createCell((short)134).setCellValue(new HSSFRichTextString("IP-CHEMO-COPAY"));
			     				rowhead.createCell((short)135).setCellValue(new HSSFRichTextString("IP-CHEMO-DEDUCTIBLES"));
			     				rowhead.createCell((short)136).setCellValue(new HSSFRichTextString("IP-CHEMO-COPDED-MINMAX"));
			     				rowhead.createCell((short)137).setCellValue(new HSSFRichTextString("IP-CONSUM&DISPOS-PPL"));
			     				rowhead.createCell((short)138).setCellValue(new HSSFRichTextString("IP-CONSUM&DISPOS-COPAY"));
			     				rowhead.createCell((short)139).setCellValue(new HSSFRichTextString("IP-CONSUM&DISPOS-DEDUCTIBLES"));
			     				rowhead.createCell((short)140).setCellValue(new HSSFRichTextString("IP-CONSUM&DISPOS-COPDED-MINMAX"));
		    	     			
			     				rowhead.createCell((short)141).setCellValue(new HSSFRichTextString("IP-CONSUM&DISPOS-EXCLU-PERIOD-VALUE"));
			     				rowhead.createCell((short)142).setCellValue(new HSSFRichTextString("IP-CONSUM&DISPOS-EXCLU-PERIOD-UNIT"));
		    	     			rowhead.createCell((short)143).setCellValue(new HSSFRichTextString("IP_PHYSIO_PROVIDER_FACILITY_TYPE"));
		       	     			rowhead.createCell((short)144).setCellValue(new HSSFRichTextString("IP_PHYSIO_PREAPPROVAL_REQD_YES/NO"));
		       	     		    rowhead.createCell((short)145).setCellValue(new HSSFRichTextString("IP_PHYSIO_PREAPPROVAL_LIMIT"));
		    	     			rowhead.createCell((short)146).setCellValue(new HSSFRichTextString("IP_PHYSIO_PPL"));
		    	     			rowhead.createCell((short)147).setCellValue(new HSSFRichTextString("IP_PHYSIO_NO-OF-SESS"));
		    	     			rowhead.createCell((short)148).setCellValue(new HSSFRichTextString("IP_PHYSIO_PER_SESS_LIMIT"));
		    	     			rowhead.createCell((short)149).setCellValue(new HSSFRichTextString("IP_PHYSIO_COPAY"));
		    	     			rowhead.createCell((short)150).setCellValue(new HSSFRichTextString("IP_PHYSIO_OVR_DEDUCTABLE"));
			     				
		    	     			rowhead.createCell((short)151).setCellValue(new HSSFRichTextString("IP_PHYSIO_COPDED_MINMAX"));
		    	     			rowhead.createCell((short)152).setCellValue(new HSSFRichTextString("IP-AMBULANCE-GEO-LOCAT"));
		       	     			rowhead.createCell((short)153).setCellValue(new HSSFRichTextString("IP-AMBULANCE-COUNTRIES"));
		       	     		    rowhead.createCell((short)154).setCellValue(new HSSFRichTextString("IP-AMBULANCE-EMIRATES"));
		    	     			rowhead.createCell((short)155).setCellValue(new HSSFRichTextString("IP-AMBULANCE-PROVID-TYPE"));
		    	     			rowhead.createCell((short)156).setCellValue(new HSSFRichTextString("IP-AMBULANCE-PROVID-FAC-TYPE"));
		    	     			rowhead.createCell((short)157).setCellValue(new HSSFRichTextString("IP-AMBULANCE-EMERG/NONEMERG"));
		    	     			rowhead.createCell((short)158).setCellValue(new HSSFRichTextString("IP-AMBULANCE-PREAPRVL-REQD-YES/NO"));
		    	     			rowhead.createCell((short)159).setCellValue(new HSSFRichTextString("IP-AMBULANCE-PREAPRVL-LIMIT"));
		    	     			rowhead.createCell((short)160).setCellValue(new HSSFRichTextString("IP-AMBULANCE-PPL"));
			     				
		    	     			rowhead.createCell((short)161).setCellValue(new HSSFRichTextString("IP-AMBULANCE-COPAY"));
		    	     			rowhead.createCell((short)162).setCellValue(new HSSFRichTextString("IP-AMBULANCE-DEDUCTIBLE"));
		    	     			rowhead.createCell((short)163).setCellValue(new HSSFRichTextString("IP-AMBULANCE-COPDED-MINMAX"));
			     				
	   		}
		     			 index = 1;
		     		
		     			for (HmoInPatientVO hmoinPatientVO : inPatientVOs) {
		     				int rowCount = sheet.getLastRowNum()+1;
		     		  	    row = sheet.createRow(rowCount);
		     				// row = sheet.createRow(index);
		     				row.createCell((short)0).setCellValue(new HSSFRichTextString(String.valueOf(rowCount)));
		     				row.createCell((short)1).setCellValue(new HSSFRichTextString(hmoinPatientVO.getPolicyNo()));
		     				row.createCell((short)2).setCellValue(new HSSFRichTextString(hmoinPatientVO.getInsuranceCompanyName()));
		     				row.createCell((short)3).setCellValue(new HSSFRichTextString(hmoinPatientVO.getProductName()));
		     				row.createCell((short)4).setCellValue(new HSSFRichTextString(globalVO.getProductNetworkCategory()));
		       				row.createCell((short)5).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupID()));
		       				row.createCell((short)6).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupName()));
		       				row.createCell((short)7).setCellValue(new HSSFRichTextString(globalVO.getPolicyAdmnstrtvSrvcType()));
		       				row.createCell((short)8).setCellValue(new HSSFRichTextString(globalVO.getSumInsured()));
		       				
		     				row.createCell((short)9).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpDaycPPL()));
		     				row.createCell((short)10).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpDaycChronicPPL()));
		     				row.createCell((short)11).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpDaycPedPPL()));
		     				row.createCell((short)12).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpDaycChronPlusPedPPL()));
		     				row.createCell((short)13).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpDaycOneMedPPL()));
		     				row.createCell((short)14).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPPL()));
		     				if(index==1){
		     					
		     					row.createCell((short)15).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpRmAndBrdRmTypes()));
			     				row.createCell((short)16).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpRmAndBrdPPL()));
			     				row.createCell((short)17).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpRmAndBrdCopay()));
			     				row.createCell((short)18).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpRmAndBrdDEDUCTABLE()));
			     				row.createCell((short)19).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpRmAndBrdCopdedMINMAX()));
			     				
			     				row.createCell((short)20).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpIcuExpPPL()));
			     				row.createCell((short)21).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpIcuExpCopay()));
			     				row.createCell((short)22).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpIcuExpDEDUCTABLE()));
			     				row.createCell((short)23).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpIcuExpCopdedMINMAX()));
			     				
			     				row.createCell((short)38).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgAndAnstSrgPPL()));
			     				row.createCell((short)39).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgAndAnstSrgCopay()));
			     				row.createCell((short)40).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgAndAnstSrgDEDUCTABLE()));
			     				row.createCell((short)41).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgAndAnstSrgCopdedMINMAX()));
			     				row.createCell((short)42).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgAndAnstAnstPPL()));
			     				row.createCell((short)43).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgAndAnstAnstCopay()));
			     				row.createCell((short)44).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgAndAnstAnstDEDUCTABLE()));
			     				row.createCell((short)45).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgAndAnstAnstCopdedMINMAX()));
			     				
		     					
		     				}
		     				
		     				
		     				row.createCell((short)24).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsultGeoLocCov()));
		     				row.createCell((short)25).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsultConCov()));
		     				row.createCell((short)26).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsultEmrCov()));
		     				row.createCell((short)27).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsultEncType()));
		     				row.createCell((short)28).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsultProTypes()));
		     				row.createCell((short)29).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsultPPL()));
		     				row.createCell((short)30).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpGpConsultCopay()));
		     				row.createCell((short)31).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpGpConsultDEDUCTABLE()));
		     				row.createCell((short)32).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpGpConsultMinMax()));
		     				row.createCell((short)33).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSpConsultCopay()));
		     				row.createCell((short)34).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSpConsultDEDUCTABLE()));
		     				row.createCell((short)35).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSpConsultMinMax()));
		     				row.createCell((short)36).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsultFlwPeriod()));
		     				row.createCell((short)37).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsultFlwPeriodUnit()));
		     				
		     				
		   				row.createCell((short)46).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargGeoLoc()));
	     				row.createCell((short)47).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargCon()));
	     				row.createCell((short)48).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargEmr()));
	     				row.createCell((short)49).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargProTypes()));
	     				row.createCell((short)50).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargProFaclityTypes()));
	     				row.createCell((short)51).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargPreaprvReqdYesNo()));
	     				row.createCell((short)52).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargPAL()));
	     				row.createCell((short)53).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargFrmAge()));
	   				row.createCell((short)54).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargToAge()));
     				row.createCell((short)55).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargFrmToAgeUt()));
     				row.createCell((short)56).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargPPL()));
     				row.createCell((short)57).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargNoDaysAlwd()));
     				row.createCell((short)58).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargMxLmtAlwdPerDay()));
     				row.createCell((short)59).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargCopay()));
     				row.createCell((short)60).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargDEDUCTABLE()));
     				row.createCell((short)61).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpCompnChargCopdedMINMAX()));
		     				
		     				row.createCell((short)62).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpOverallInvstPPL()));
		     				row.createCell((short)63).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpLabAndPathPPL()));
		     				row.createCell((short)64).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpExceptLabAndPathPPL()));
		     				

		     				row.createCell((short)65).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvLabProviderTypes()));
		     				row.createCell((short)66).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvLabPreapprovalReqdYesNo()));
		     				row.createCell((short)67).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvLabPreapprovalReqdLimit()));
		     				row.createCell((short)68).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvLabPpl()));
		     				row.createCell((short)69).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvLabNoOfSess()));
		     				row.createCell((short)70).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvLabCopay()));
		     				row.createCell((short)71).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvLabDeductable()));
		     				row.createCell((short)72).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvLabCopdedMINMAX()));
		     				row.createCell((short)73).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvPatProviderTypes()));
		     				row.createCell((short)74).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvPatPreapprovalReqdYesNo()));
		     				row.createCell((short)75).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvPatPreapprovalReqdLimit()));
		     				row.createCell((short)76).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvPatPpl()));
		     				row.createCell((short)77).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvPatNoOfSess()));
		     				row.createCell((short)78).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvPatCopay()));
		     				row.createCell((short)79).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvPatDeductable()));
		     				row.createCell((short)80).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvPatCopdedMINMAX()));
		     				row.createCell((short)81).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvUltraProviderFacilityTypes()));
		     				row.createCell((short)82).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvUltraPreapprovalReqdYesNo()));
		     				row.createCell((short)83).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvUltraPreapprovalReqdLimit()));
		     				row.createCell((short)84).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvUltraPpl()));
		     				row.createCell((short)85).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvUltraNoOfSess()));
		     				row.createCell((short)86).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvUltraCopay()));
		     				row.createCell((short)87).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvUltraDeductable()));
		     				row.createCell((short)88).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvUltraCopdedMINMAX()));
		     				row.createCell((short)89).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvCtScanProviderFacilityTypes()));
		     				row.createCell((short)90).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvCtScanPreapprovalReqdYesNo()));
		     				row.createCell((short)91).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvCtScanPreapprovalReqdLimit()));
		     				row.createCell((short)92).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvCtScanPpl()));
		     				row.createCell((short)93).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvCtScanNoOfSess()));
		     				row.createCell((short)94).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvCtScanCopay()));
		     				row.createCell((short)95).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvCtScanDeductable()));
		     				row.createCell((short)96).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvCtScanCopdedMINMAX()));
		     				row.createCell((short)97).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvMriProviderFacilityTypes()));
		     				row.createCell((short)98).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvMriPreapprovalReqdYesNo()));
		     				row.createCell((short)99).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvMriPreapprovalReqdLimit()));
		     				row.createCell((short)100).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvMriPpl()));
		     				row.createCell((short)101).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvMriNoOfSess()));
		     				row.createCell((short)102).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvMriCopay()));
		     				row.createCell((short)103).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvMriDeductable()));
		     				row.createCell((short)104).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvMriCopdedMINMAX()));
		     				
		     				row.createCell((short)105).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvDiagAndTherapProviderTypes()));
		     				row.createCell((short)106).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvDiagAndTherapPreapprovalReqdYesNo()));
		     				row.createCell((short)107).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvDiagAndTherapPreapprovalReqdLimit()));
		     				row.createCell((short)108).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvDiagAndTherapPpl()));
		     				row.createCell((short)109).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvDiagAndTherapNoOfSess()));
		     				row.createCell((short)110).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvDiagAndTherapCopay()));
		     				row.createCell((short)111).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvDiagAndTherapDeductable()));
		     				row.createCell((short)112).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpInvDiagAndTherapCopdedMINMAX()));
		     				
if(index==1){
		row.createCell((short)113).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpMedicationsPPL()));
			row.createCell((short)114).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpMedicationsCopay()));
			row.createCell((short)115).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpMedicationsDEDUCTABLE()));
			row.createCell((short)116).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpMedicationsCopdedMINMAX()));
			
			row.createCell((short)117).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpIVFFluidsPPL()));
			row.createCell((short)118).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpIVFFluidsCopay()));
			row.createCell((short)119).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpIVFFluidsDEDUCTABLE()));
			row.createCell((short)120).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpIVFFluidsCopdedMINMAX()));
			
			row.createCell((short)121).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpBldTrnsfusnPPL()));
			row.createCell((short)122).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpBldTrnsfusnCopay()));
			row.createCell((short)123).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpBldTrnsfusnDEDUCTABLE()));
			row.createCell((short)124).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpBldTrnsfusnCopdedMINMAX()));
			
			row.createCell((short)125).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAnalegicsPPL()));
			row.createCell((short)126).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAnalegicsCopay()));
			row.createCell((short)127).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAnalegicsDEDUCTABLE()));
			row.createCell((short)128).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAnalegicsCopdedMINMAX()));
			
			row.createCell((short)129).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgImplPPL()));
			row.createCell((short)130).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgImplCopay()));
			row.createCell((short)131).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgImplDEDUCTABLE()));
			row.createCell((short)132).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpSrgImplCopdedMINMAX()));
			
			row.createCell((short)133).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpChemoPPL()));
			row.createCell((short)134).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpChemoCopay()));
			row.createCell((short)135).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpChemoDEDUCTABLE()));
			row.createCell((short)136).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpChemoCopdedMINMAX()));
			
			row.createCell((short)137).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsmAndDspsPPL()));
			row.createCell((short)138).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsmAndDspsCopay()));
			row.createCell((short)139).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsmAndDspsDEDUCTABLE()));
			row.createCell((short)140).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsmAndDspsCopdedMINMAX()));
			row.createCell((short)141).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsmAndDspsExPrdVal()));
			row.createCell((short)142).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpConsmAndDspsExPrdUnt()));		
		     				}
	
		     				
		     				row.createCell((short)143).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPhysioProviderFacilityTypes()));
		     				row.createCell((short)144).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPhysioPreapprovalReqdYesNo()));
		     				row.createCell((short)145).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPhysioPreapprovalLimit()));
		     				row.createCell((short)146).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPhysioPpl()));
		     				row.createCell((short)147).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPhysioNOOfSess()));
		     				row.createCell((short)148).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPhysioPerSessLimit()));
		     				row.createCell((short)149).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPhysioCopay()));
		     				row.createCell((short)150).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPhysioOvrDeductable()));
		     				row.createCell((short)151).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpPhysioCopdedMINMAX()));
		     				
		     				row.createCell((short)152).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncGeoLoc()));
		     				row.createCell((short)153).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncCon()));
		     				row.createCell((short)154).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncEmr()));
		     				row.createCell((short)155).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncProTypes()));
		     				row.createCell((short)156).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncProFaclityTypes()));
		     				row.createCell((short)157).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncEmrNonemr()));
		     				row.createCell((short)158).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncPreaprvReqdYesNo()));
		     				row.createCell((short)159).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncPAL()));
		     				row.createCell((short)160).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncPPL()));
		     				row.createCell((short)161).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncCopay()));
		     				row.createCell((short)162).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncDeductable()));
		     				row.createCell((short)163).setCellValue(new HSSFRichTextString(hmoinPatientVO.getIpAmblncCopdedMINMAX()));
		     	
		     	
		     				index++;
		     				
						}
   			

   			// INPATIENT END

       		
       	}
	     			
    			    		if(dcVOs!=null) {
    			    			
    			    			
    			    			sheet = wb.getSheet("DAYCARE");
    			    			if(sheet==null){
    			    				
    			    				 sheet = wb.createSheet("DAYCARE");
	     		   	           	      rowhead = sheet.createRow(0);
    			    				
    			    	/*		}
	     			    
		     		     			int  policycnt = 0;
		     		   	   		
		     		   	   		
		     		   	   		if(policycount == 1){
		     		   	       		 sheet = wb.createSheet("DAYCARE");
		     		   	           	      rowhead = sheet.createRow(0);
		     		   	           	   policycnt = 1;
		     		   	  	   			//System.out.println("policycount inside IPT");

		     		   	        		}else{
		     		   	        					//System.out.println("policycount else IPT---"+wb.getSheet("DAYCARE"));
		     		   	        				
		     		   	        					String dayCareData = String.valueOf(wb.getSheet("DAYCARE"));
		     		           					//System.out.println("policycount inpatientData ---"+dayCareData);

		     		   					   	if(dayCareData.equals("null")){
		     		   					   	 sheet = wb.createSheet("DAYCARE");
		     		   					   	rowhead = sheet.createRow(0);
		     		   					    	policycnt = 1;
		     		   					   	}else{
		     		   			                  sheet = wb.getSheet("DAYCARE");
		     		   					   	}
		     		   	        		}
		     		   	   		  
		     		   	   		if(policycnt == 1){*/
		     		     	   	
		     		     	   		    rowhead.createCell((short)0).setCellValue(new HSSFRichTextString("S.No"));
		     		     				rowhead.createCell((short)1).setCellValue(new HSSFRichTextString("Policy No"));
		     		     				rowhead.createCell((short)2).setCellValue(new HSSFRichTextString("Insurance Company Name"));
		     		     				rowhead.createCell((short)3).setCellValue(new HSSFRichTextString("Product Name"));
		     		     				rowhead.createCell((short)4).setCellValue(new HSSFRichTextString("PRODUCT NETWORK CATEGORY"));
		     		         		    rowhead.createCell((short)5).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group ID"));
		     		         		  rowhead.createCell((short)6).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group Name"));
		     		   				rowhead.createCell((short)7).setCellValue(new HSSFRichTextString("POLICY ADMINISTRATIVE SERVICE TYPE"));
		     		   				rowhead.createCell((short)8).setCellValue(new HSSFRichTextString("SUM INSURED"));
		     		     				
		     		     			    rowhead.createCell((short)9).setCellValue(new HSSFRichTextString("IP_DAYC_PPL"));                    
		     			    		    rowhead.createCell((short)10).setCellValue(new HSSFRichTextString("IP_DAYC_CHRONIC_PPL"));                
		     		     				rowhead.createCell((short)11).setCellValue(new HSSFRichTextString("IP_DAYC_PED_PPL"));               
		     		     				rowhead.createCell((short)12).setCellValue(new HSSFRichTextString("IP_DAYC_CHRON_PLUS_PED_PPL"));    
		     		     				rowhead.createCell((short)13).setCellValue(new HSSFRichTextString("IP_DAYC_ONE_MEDICAL_PPL"));       
		     		     				rowhead.createCell((short)14).setCellValue(new HSSFRichTextString("DAYCARE_PPL"));                       
		     		     				
		     		     				rowhead.createCell((short)15).setCellValue(new HSSFRichTextString("DAYCARE-ROOM&BOARD-ROOM-TYPES"));
		     		     				rowhead.createCell((short)16).setCellValue(new HSSFRichTextString("DAYCARE-ROOM&BOARD-PPL"));
		     		     				rowhead.createCell((short)17).setCellValue(new HSSFRichTextString("DAYCARE-ROOM&BOARD-COPAY"));
		     		     				rowhead.createCell((short)18).setCellValue(new HSSFRichTextString("DAYCARE-ROOM&BOARD-DEDUCTIBLES"));
		     		     				rowhead.createCell((short)19).setCellValue(new HSSFRichTextString("DAYCARE-ROOM&BOARD-COPDED-MINMAX"));
		     		     				
		     		     				rowhead.createCell((short)20).setCellValue(new HSSFRichTextString("DAYCARE-ICU-EXP-PPL"));
		     		     				rowhead.createCell((short)21).setCellValue(new HSSFRichTextString("DAYCARE-ICU-EXP-COPAY"));
		     		     				rowhead.createCell((short)22).setCellValue(new HSSFRichTextString("DAYCARE-ICU-EXP-DEDUCTIBLES"));
		     		     				rowhead.createCell((short)23).setCellValue(new HSSFRichTextString("DAYCARE-ICU-EXP-COPDED-MINMAX"));
		     		     		     			
		     		     				rowhead.createCell((short)24).setCellValue(new HSSFRichTextString("DAYCARE-Consult-GEOGRAPHICAL-LOCATION-COVERED")); //
		     		     				rowhead.createCell((short)25).setCellValue(new HSSFRichTextString("DAYCARE-Consult-COUNTRIES-COVERED"));
		     		     	   	     			rowhead.createCell((short)26).setCellValue(new HSSFRichTextString("DAYCARE-Consult-EMIRATES COVERED"));   //
		     		     	   	     		    rowhead.createCell((short)27).setCellValue(new HSSFRichTextString("DAYCARE-Consult-Encounter Type"));
		     		     		     			rowhead.createCell((short)28).setCellValue(new HSSFRichTextString("DAYCARE-Consult-Provider Types"));
		     		     		     			rowhead.createCell((short)29).setCellValue(new HSSFRichTextString("DAYCARE_CONSULT_PPL"));
		     		     		     			rowhead.createCell((short)30).setCellValue(new HSSFRichTextString("DAYCARE_GP_CONSULT_COPAY"));
		     		     		        		
		     		     		      		    rowhead.createCell((short)31).setCellValue(new HSSFRichTextString("DAYCARE_GP-CONSULT_DEDUCTABLE"));
		     		     		    			rowhead.createCell((short)32).setCellValue(new HSSFRichTextString("DAYCARE_GP_CONSULT_MIN/MAX"));
		     		     		    			rowhead.createCell((short)33).setCellValue(new HSSFRichTextString("DAYCARE_SP_CONSULT_COPAY"));
		     		     		    			rowhead.createCell((short)34).setCellValue(new HSSFRichTextString("DAYCARE_SP_CONSULT_DEDUCTABLE"));
		     		     		    		    rowhead.createCell((short)35).setCellValue(new HSSFRichTextString("DAYCARE_SP_CONSULT_MIN/MAX"));
		     		     		    		    rowhead.createCell((short)36).setCellValue(new HSSFRichTextString("DAYCARE_CONSULT_FOLLOW_PERIOD"));
		     		     		        		rowhead.createCell((short)37).setCellValue(new HSSFRichTextString("DAYCARE_CONSULT_FLW_PERIOD_UNIT"));
		     		    	     				
		     		     		        		rowhead.createCell((short)38).setCellValue(new HSSFRichTextString("DAYCARE-SURG&ANAEST-SURG-PPL"));
		     		    	     				rowhead.createCell((short)39).setCellValue(new HSSFRichTextString("DAYCARE-SURG&ANAEST-SURG-COPAY"));
		     		    	     				rowhead.createCell((short)40).setCellValue(new HSSFRichTextString("DAYCARE-SURG&ANAEST-SURG-DEDUCTIBLES"));
		     		    	     				rowhead.createCell((short)41).setCellValue(new HSSFRichTextString("DAYCARE-SURG&ANAEST-SURG-COPDED-MINMAX"));
		     		    	     				rowhead.createCell((short)42).setCellValue(new HSSFRichTextString("DAYCARE-SURG&ANAEST-ANAEST-PPL"));
		     		    	     				rowhead.createCell((short)43).setCellValue(new HSSFRichTextString("DAYCARE-SURG&ANAEST-ANAEST-COPAY"));
		     		    	     				rowhead.createCell((short)44).setCellValue(new HSSFRichTextString("DAYCARE-SURG&ANAEST-ANAEST-DEDUCTIBLES"));
		     		    	     				rowhead.createCell((short)45).setCellValue(new HSSFRichTextString("DAYCARE-SURG&ANAEST-ANAEST-COPDED-MINMAX"));
		     		     		        		
		     		    	     				rowhead.createCell((short)46).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-GEO-LOCAT"));
		     		    	     				rowhead.createCell((short)47).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-COUNTRIES"));
		     		    	     				rowhead.createCell((short)48).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-EMIRATES"));
		     		    	     				rowhead.createCell((short)49).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-PROVID-TYPE"));
		     		    	     				rowhead.createCell((short)50).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-PROVID-FAC-TYPE"));
		     		    	     				rowhead.createCell((short)51).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-PREAPRVL-REQD-YES/NO"));
		     		    	     				rowhead.createCell((short)52).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-PREAPRVL-LIMIT"));
		     		    	     				rowhead.createCell((short)53).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-FROM-AGE"));
		     		    	     				rowhead.createCell((short)54).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-TO-AGE"));
		     		    	     				rowhead.createCell((short)55).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-FROM-TO-AGE-UNIT"));
		     		    	     				rowhead.createCell((short)56).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-PPL"));
		     		    	     				rowhead.createCell((short)57).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-NUM-DAYS-ALLWD"));
		     		    	     				rowhead.createCell((short)58).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-MAX-LIM-ALLWD-PER-DAY"));
		     		    	     				rowhead.createCell((short)59).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-COPAY"));
		     		    	     				rowhead.createCell((short)60).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-DEDUCTIBLE"));
		     		    	     				rowhead.createCell((short)61).setCellValue(new HSSFRichTextString("DAYCARE-COMPN-CHARG-COPDED-MINMAX"));
		     		     		    			
		     		     		    			rowhead.createCell((short)62).setCellValue(new HSSFRichTextString("DAYCARE_OVERALL_INVST_PPL"));
		     		     		    			rowhead.createCell((short)63).setCellValue(new HSSFRichTextString("DAYCARE_LAB AND PATH_PPL"));
		     		     		    			rowhead.createCell((short)64).setCellValue(new HSSFRichTextString("DAYCARE_EXCEPT LAB & PATH_PPL"));
		     		     		    	     			
		     		     		    			        rowhead.createCell((short)65).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_LAB_PROVIDER_TYPES"));
		     		     		       	     			rowhead.createCell((short)66).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_LAB_PREAPVL_REQD_YES/NO"));
		     		     		       	     			rowhead.createCell((short)67).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_LAB_PREAPVL_REQD_LIMIT"));
		     		     		       	     		    rowhead.createCell((short)68).setCellValue(new HSSFRichTextString("DAYCARE_INV_LAB_PPL"));
		     		     		    	     			rowhead.createCell((short)69).setCellValue(new HSSFRichTextString("DAYCARE_INV_LAB_NUM_OF_SESSIONS")); //
		     		     		    	     			rowhead.createCell((short)70).setCellValue(new HSSFRichTextString("DAYCARE_INV_LAB_COPAY"));
		     		     		    	     			rowhead.createCell((short)71).setCellValue(new HSSFRichTextString("DAYCARE_INV_LAB_DEDUCTABLE"));
		     		     		    	     			rowhead.createCell((short)72).setCellValue(new HSSFRichTextString("DAYCARE_INV_LAB_COPDED_MINMAX"));
		     		     		    	     			
		     		     		    	      			rowhead.createCell((short)73).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_PAT_PROVIDER_TYPES"));
		     		     		       	     			rowhead.createCell((short)74).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_PAT_PREAPVL_REQD_YES/NO"));
		     		     		       	     			rowhead.createCell((short)75).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_PAT_PREAPVL_REQD_LIMIT"));
		     		     		       	     		    rowhead.createCell((short)76).setCellValue(new HSSFRichTextString("DAYCARE_INV_PAT_PPL"));
		     		     		    	     			rowhead.createCell((short)77).setCellValue(new HSSFRichTextString("DAYCARE_INV_PAT_NUM_OF_SESSIONS"));  //
		     		     		    	     			rowhead.createCell((short)78).setCellValue(new HSSFRichTextString("DAYCARE_INV_PAT_COPAY"));
		     		     		    	     			rowhead.createCell((short)79).setCellValue(new HSSFRichTextString("DAYCARE_INV_PAT_DEDUCTABLE"));
		     		     		    	     			rowhead.createCell((short)80).setCellValue(new HSSFRichTextString("DAYCARE_INV_PAT_COPDED_MINMAX"));
		     		     		    	     			
		     		     		    	      			rowhead.createCell((short)81).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_ULTRA_PROVIDER_TYPES"));
		     		     		       	     			rowhead.createCell((short)82).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_ULTRA_PREAPVL_REQD_YES/NO"));
		     		     		       	     			rowhead.createCell((short)83).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_ULTRA_PREAPVL_REQD_LIMIT"));
		     		     		       	     		    rowhead.createCell((short)84).setCellValue(new HSSFRichTextString("DAYCARE_INV_ULTRA_PPL"));
		     		     		    	     			rowhead.createCell((short)85).setCellValue(new HSSFRichTextString("DAYCARE_INV_ULTRA_NUM_OF_SESSIONS"));  //
		     		     		    	     			rowhead.createCell((short)86).setCellValue(new HSSFRichTextString("DAYCARE_INV_ULTRA_COPAY"));
		     		     		    	     			rowhead.createCell((short)87).setCellValue(new HSSFRichTextString("DAYCARE_INV_ULTRA_DEDUCTABLE"));
		     		     		    	     			rowhead.createCell((short)88).setCellValue(new HSSFRichTextString("DAYCARE_INV_ULTRA_COPDED_MINMAX"));
		     		     		    	     			
		     		     		    	     			rowhead.createCell((short)89).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_CTSCAN_PROVIDER_TYPES"));
		     		     		    	     			rowhead.createCell((short)90).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_CTSCAN_PREAPVL_REQD_YES/NO"));
		     		     		       	     			rowhead.createCell((short)91).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_CTSCAN_PREAPVL_REQD_LIMIT"));
		     		     		       	     		    rowhead.createCell((short)92).setCellValue(new HSSFRichTextString("DAYCARE_INV_CTSCAN_PPL"));
		     		     		    	     			rowhead.createCell((short)93).setCellValue(new HSSFRichTextString("DAYCARE_INV_CTSCAN_NUM_OF_SESSIONS"));  //
		     		     		    	     			rowhead.createCell((short)94).setCellValue(new HSSFRichTextString("DAYCARE_INV_CTSCAN_COPAY"));
		     		     		    	     			rowhead.createCell((short)95).setCellValue(new HSSFRichTextString("DAYCARE_INV_CTSCAN_DEDUCTABLE"));
		     		     		    	     			rowhead.createCell((short)96).setCellValue(new HSSFRichTextString("DAYCARE_INV_CTSCAN_COPDED_MINMAX"));
		     		     		    	     			
		     		     		    	     			rowhead.createCell((short)97).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_MRI_PROVIDER_TYPES"));
		     		     		    	     			rowhead.createCell((short)98).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_MRI_PREAPVL_REQD_YES/NO"));
		     		     		       	     			rowhead.createCell((short)99).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_MRI_PREAPVL_REQD_LIMIT"));
		     		     		       	     		    rowhead.createCell((short)100).setCellValue(new HSSFRichTextString("DAYCARE_INV_MRI_PPL"));
		     		     		    	     			rowhead.createCell((short)101).setCellValue(new HSSFRichTextString("DAYCARE_INV_MRI_NUM_OF_SESSIONS"));   //
		     		     		    	     			rowhead.createCell((short)102).setCellValue(new HSSFRichTextString("DAYCARE_INV_MRI_COPAY"));
		     		     		    	     			rowhead.createCell((short)103).setCellValue(new HSSFRichTextString("DAYCARE_INV_MRI_DEDUCTABLE"));
		     		     		    	     			rowhead.createCell((short)104).setCellValue(new HSSFRichTextString("DAYCARE_INV_MRI_COPDED_MINMAX"));
		     		     		    	     			
		     		     		    	     			rowhead.createCell((short)105).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_DIAG&THERAP_PROVIDER_TYPES"));
		     		     		    	     			rowhead.createCell((short)106).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_DIAG&THERAP_PREAPVL_REQD_YES/NO"));
		     		     		       	     			rowhead.createCell((short)107).setCellValue(new HSSFRichTextString("DAYCARE_INVEST_DIAG&THERAP_PREAPVL_REQD_LIMIT"));
		     		     		       	     		    rowhead.createCell((short)108).setCellValue(new HSSFRichTextString("DAYCARE_INV_DIAG&THERAP_PPL"));
		     		     		    	     			rowhead.createCell((short)109).setCellValue(new HSSFRichTextString("DAYCARE_INV_DIAG&THERAP_NUM_OF_SESSIONS"));  //
		     		     		    	     			rowhead.createCell((short)110).setCellValue(new HSSFRichTextString("DAYCARE_INV_DIAG&THERAP_COPAY"));
		     		     		    	     			rowhead.createCell((short)111).setCellValue(new HSSFRichTextString("DAYCARE_INV_DIAG&THERAP_DEDUCTABLE"));
		     		     		    	     			rowhead.createCell((short)112).setCellValue(new HSSFRichTextString("DAYCARE_INV_DIAG&THERAP_COPDED_MINMAX"));
		     		     			     				
		     		     		    	     			rowhead.createCell((short)113).setCellValue(new HSSFRichTextString("DAYCARE-MEDICATIONS-PPL"));
		     		     			     				rowhead.createCell((short)114).setCellValue(new HSSFRichTextString("DAYCARE-MEDICATIONS-COPAY"));
		     		     			     				rowhead.createCell((short)115).setCellValue(new HSSFRichTextString("DAYCARE-MEDICATIONS-DEDUCTIBLES"));
		     		     			     				rowhead.createCell((short)116).setCellValue(new HSSFRichTextString("DAYCARE-MEDICATIONS-COPDED-MINMAX"));
		     		     			     				
		     		     			     				rowhead.createCell((short)117).setCellValue(new HSSFRichTextString("DAYCARE-IVFLUIDS-PPL"));
		     		     			     				rowhead.createCell((short)118).setCellValue(new HSSFRichTextString("DAYCARE-IVFLUIDS-COPAY"));
		     		     			     				rowhead.createCell((short)119).setCellValue(new HSSFRichTextString("DAYCARE-IVFLUIDS-DEDUCTIBLES"));
		     		     			     				rowhead.createCell((short)120).setCellValue(new HSSFRichTextString("DAYCARE-IVFLUIDS-COPDED-MINMAX"));
		     		     			     				
		     		     			     				rowhead.createCell((short)121).setCellValue(new HSSFRichTextString("DAYCARE-BLOOD-TRANSFUSN-PPL"));
		     		     			     				rowhead.createCell((short)122).setCellValue(new HSSFRichTextString("DAYCARE-BLOOD-TRANSFUSN-COPAY"));
		     		     			     				rowhead.createCell((short)123).setCellValue(new HSSFRichTextString("DAYCARE-BLOOD-TRANSFUSN-DEDUCTIBLES"));
		     		     			     				rowhead.createCell((short)124).setCellValue(new HSSFRichTextString("DAYCARE-BLOOD-TRANSFUSN-COPDED-MINMAX"));
		     		     			     				
		     		     			     				rowhead.createCell((short)125).setCellValue(new HSSFRichTextString("DAYCARE-ANALGESICS-PPL"));
		     		     			     				rowhead.createCell((short)126).setCellValue(new HSSFRichTextString("DAYCARE-ANALGESICS-COPAY"));
		     		     			     				rowhead.createCell((short)127).setCellValue(new HSSFRichTextString("DAYCARE-ANALGESICS-DEDUCTIBLES"));
		     		     			     				rowhead.createCell((short)128).setCellValue(new HSSFRichTextString("DAYCARE-ANALGESICS-COPDED-MINMAX"));
		     		     			     				
		     		     			     				rowhead.createCell((short)129).setCellValue(new HSSFRichTextString("DAYCARE-SURGCL-IMPLTS-PPL"));
		     		     			     				rowhead.createCell((short)130).setCellValue(new HSSFRichTextString("DAYCARE-SURGCL-IMPLTS-COPAY"));
		     		     			     				rowhead.createCell((short)131).setCellValue(new HSSFRichTextString("DAYCARE-SURGCL-IMPLTS-DEDUCTIBLES"));
		     		     			     				rowhead.createCell((short)132).setCellValue(new HSSFRichTextString("DAYCARE-SURGCL-IMPLTS-COPDED-MINMAX"));
		     		     			     				
		     		     			     				rowhead.createCell((short)133).setCellValue(new HSSFRichTextString("DAYCARE-CHEMO-PPL"));
		     		     			     				rowhead.createCell((short)134).setCellValue(new HSSFRichTextString("DAYCARE-CHEMO-COPAY"));
		     		     			     				rowhead.createCell((short)135).setCellValue(new HSSFRichTextString("DAYCARE-CHEMO-DEDUCTIBLES"));
		     		     			     				rowhead.createCell((short)136).setCellValue(new HSSFRichTextString("DAYCARE-CHEMO-COPDED-MINMAX"));
		     		     			     				
		     		     			     				rowhead.createCell((short)137).setCellValue(new HSSFRichTextString("DAYCARE-CONSUM&DISPOS-PPL"));
		     		     			     				rowhead.createCell((short)138).setCellValue(new HSSFRichTextString("DAYCARE-CONSUM&DISPOS-COPAY"));
		     		     			     				rowhead.createCell((short)139).setCellValue(new HSSFRichTextString("DAYCARE-CONSUM&DISPOS-DEDUCTIBLES"));
		     		     			     				rowhead.createCell((short)140).setCellValue(new HSSFRichTextString("DAYCARE-CONSUM&DISPOS-COPDED-MINMAX"));
		     		     			     				rowhead.createCell((short)141).setCellValue(new HSSFRichTextString("DAYCARE-CONSUM&DISPOS-EXCLU-PERIOD-VALUE")); //
		     		     			     				rowhead.createCell((short)142).setCellValue(new HSSFRichTextString("DAYCARE-CONSUM&DISPOS-EXCLU-PERIOD-UNIT"));  //
		     		     		    	     			
		     		     		    	     			
		     		     		    	     			
		     		     		    	     			rowhead.createCell((short)143).setCellValue(new HSSFRichTextString("DAYCARE_PHYSIO_PROVIDER_FACILITY_TYPE"));
		     		     		       	     			rowhead.createCell((short)144).setCellValue(new HSSFRichTextString("DAYCARE_PHYSIO_PREAPPROVAL_REQD_YES/NO"));
		     		     		       	     		    rowhead.createCell((short)145).setCellValue(new HSSFRichTextString("DAYCARE_PHYSIO_PREAPPROVAL_LIMIT"));
		     		     		    	     			rowhead.createCell((short)146).setCellValue(new HSSFRichTextString("DAYCARE_PHYSIO_PPL"));
		     		     		    	     			rowhead.createCell((short)147).setCellValue(new HSSFRichTextString("DAYCARE_PHYSIO_NO-OF-SESS"));
		     		     		    	     			rowhead.createCell((short)148).setCellValue(new HSSFRichTextString("DAYCARE_PHYSIO_PER_SESS_LIMIT"));
		     		     		    	     			rowhead.createCell((short)149).setCellValue(new HSSFRichTextString("DAYCARE_PHYSIO_COPAY"));
		     		     		    	     			rowhead.createCell((short)150).setCellValue(new HSSFRichTextString("DAYCARE_PHYSIO_OVR_DEDUCTABLE"));
		     		     		    	     			rowhead.createCell((short)151).setCellValue(new HSSFRichTextString("DAYCARE_PHYSIO_COPDED_MINMAX"));
		     		     		    	     			
		     		     		    	     			rowhead.createCell((short)152).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-GEO-LOCAT"));
		     		     		       	     			rowhead.createCell((short)153).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-COUNTRIES"));
		     		     		       	     		    rowhead.createCell((short)154).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-EMIRATES"));
		     		     		    	     			rowhead.createCell((short)155).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-PROVID-TYPE"));
		     		     		    	     			rowhead.createCell((short)156).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-PROVID-FAC-TYPE"));
		     		     		    	     			rowhead.createCell((short)157).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-EMERG/NONEMERG"));
		     		     		    	     			rowhead.createCell((short)158).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-PREAPRVL-REQD-YES/NO"));
		     		     	   	     			
		     		     		    	     			rowhead.createCell((short)159).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-PREAPRVL-LIMIT"));
		     		     		    	     			rowhead.createCell((short)160).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-PPL"));
		     		     		    	     			rowhead.createCell((short)161).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-COPAY"));
		     		     		    	     			rowhead.createCell((short)162).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-DEDUCTIBLE"));
		     		     		    	     			rowhead.createCell((short)163).setCellValue(new HSSFRichTextString("DAYCARE-AMBULANCE-COPDED-MINMAX"));
		     		     	   	     			
		     		   	   		            }
		     		     		     			 index = 1;
		     		     		     		
		     		     		     			for (HmoInPatientVO hmodcVo : dcVOs) {
		     		     		     				int rowCount = sheet.getLastRowNum()+1;
		     		     		     				 row = sheet.createRow(rowCount);
		     		     		     				
		     		     		     				row.createCell((short)0).setCellValue(new HSSFRichTextString(String.valueOf(rowCount)));
		     		     		     				row.createCell((short)1).setCellValue(new HSSFRichTextString(hmodcVo.getPolicyNo()));
		     		     		     				row.createCell((short)2).setCellValue(new HSSFRichTextString(hmodcVo.getInsuranceCompanyName()));
		     		     		     				row.createCell((short)3).setCellValue(new HSSFRichTextString(hmodcVo.getProductName()));
		     		     		     				row.createCell((short)4).setCellValue(new HSSFRichTextString(globalVO.getProductNetworkCategory()));
		     		     		       				row.createCell((short)5).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupID()));
		     		     		       				row.createCell((short)6).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupName()));
		     		     		       				row.createCell((short)7).setCellValue(new HSSFRichTextString(globalVO.getPolicyAdmnstrtvSrvcType()));
		     		     		       				row.createCell((short)8).setCellValue(new HSSFRichTextString(globalVO.getSumInsured()));
		     		     		     				
		     		     		     				row.createCell((short)9).setCellValue(new HSSFRichTextString(hmodcVo.getIpDaycPPL()));
		     		     		     				row.createCell((short)10).setCellValue(new HSSFRichTextString(hmodcVo.getIpDaycChronicPPL()));
		     		     		     				row.createCell((short)11).setCellValue(new HSSFRichTextString(hmodcVo.getIpDaycPedPPL()));
		     		     		     				row.createCell((short)12).setCellValue(new HSSFRichTextString(hmodcVo.getIpDaycChronPlusPedPPL()));
		     		     		     				row.createCell((short)13).setCellValue(new HSSFRichTextString(hmodcVo.getIpDaycOneMedPPL()));
		     		     		     				row.createCell((short)14).setCellValue(new HSSFRichTextString(hmodcVo.getDcDaycPPL()));
		     		     		     				
		     		     		     				if(index==1){
		     		     		     					
		     		     		     					row.createCell((short)15).setCellValue(new HSSFRichTextString(hmodcVo.getIpRmAndBrdRmTypes()));
			     		     		     				row.createCell((short)16).setCellValue(new HSSFRichTextString(hmodcVo.getIpRmAndBrdPPL()));
			     		     		     				row.createCell((short)17).setCellValue(new HSSFRichTextString(hmodcVo.getIpRmAndBrdCopay()));
			     		     		     				row.createCell((short)18).setCellValue(new HSSFRichTextString(hmodcVo.getIpRmAndBrdDEDUCTABLE()));
			     		     		     				row.createCell((short)19).setCellValue(new HSSFRichTextString(hmodcVo.getIpRmAndBrdCopdedMINMAX()));
			     		     		     				
			     		     		     				row.createCell((short)20).setCellValue(new HSSFRichTextString(hmodcVo.getIpIcuExpPPL()));
			     		     		     				row.createCell((short)21).setCellValue(new HSSFRichTextString(hmodcVo.getIpIcuExpCopay()));
			     		     		     				row.createCell((short)22).setCellValue(new HSSFRichTextString(hmodcVo.getIpIcuExpDEDUCTABLE()));
			     		     		     				row.createCell((short)23).setCellValue(new HSSFRichTextString(hmodcVo.getIpIcuExpCopdedMINMAX()));
			     		     		     				
			     		     		   
			     		     		     				
			     		     		     				row.createCell((short)38).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgAndAnstSrgPPL()));
			     		     		     				row.createCell((short)39).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgAndAnstSrgCopay()));
			     		     		     				row.createCell((short)40).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgAndAnstSrgDEDUCTABLE()));
			     		     		     				row.createCell((short)41).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgAndAnstSrgCopdedMINMAX()));
			     		     		     				row.createCell((short)42).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgAndAnstAnstPPL()));
			     		     		     				row.createCell((short)43).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgAndAnstAnstCopay()));
			     		     		     				row.createCell((short)44).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgAndAnstAnstDEDUCTABLE()));
			     		     		     				row.createCell((short)45).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgAndAnstAnstCopdedMINMAX()));
		     		   		     					
		     		   		     				}
		     		     		     		
		     		     		    				row.createCell((short)24).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsultGeoLocCov()));
		     		     		     				row.createCell((short)25).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsultConCov()));
		     		     		     				row.createCell((short)26).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsultEmrCov()));
		     		     		     				row.createCell((short)27).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsultEncType()));
		     		     		     				row.createCell((short)28).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsultProTypes()));
		     		     		     				row.createCell((short)29).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsultPPL()));
		     		     		     				row.createCell((short)30).setCellValue(new HSSFRichTextString(hmodcVo.getIpGpConsultCopay()));
		     		     		     				row.createCell((short)31).setCellValue(new HSSFRichTextString(hmodcVo.getIpGpConsultDEDUCTABLE()));
		     		     		     				row.createCell((short)32).setCellValue(new HSSFRichTextString(hmodcVo.getIpGpConsultMinMax()));
		     		     		     				row.createCell((short)33).setCellValue(new HSSFRichTextString(hmodcVo.getIpSpConsultCopay()));
		     		     		     				row.createCell((short)34).setCellValue(new HSSFRichTextString(hmodcVo.getIpSpConsultDEDUCTABLE()));
		     		     		     				row.createCell((short)35).setCellValue(new HSSFRichTextString(hmodcVo.getIpSpConsultMinMax()));
		     		     		     				row.createCell((short)36).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsultFlwPeriod()));
		     		     		     				row.createCell((short)37).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsultFlwPeriodUnit()));
		     		     		     				
		     		     		   				row.createCell((short)46).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargGeoLoc()));
		     	     		     				row.createCell((short)47).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargCon()));
		     	     		     				row.createCell((short)48).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargEmr()));
		     	     		     				row.createCell((short)49).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargProTypes()));
		     	     		     				row.createCell((short)50).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargProFaclityTypes()));
		     	     		     				row.createCell((short)51).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargPreaprvReqdYesNo()));
		     	     		     				row.createCell((short)52).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargPAL()));
		     	     		     				row.createCell((short)53).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargFrmAge()));
		     	     		   				row.createCell((short)54).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargToAge()));
		     	 		     				row.createCell((short)55).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargFrmToAgeUt()));
		     	 		     				row.createCell((short)56).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargPPL()));
		     	 		     				row.createCell((short)57).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargNoDaysAlwd()));
		     	 		     				row.createCell((short)58).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargMxLmtAlwdPerDay()));
		     	 		     				row.createCell((short)59).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargCopay()));
		     	 		     				row.createCell((short)60).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargDEDUCTABLE()));
		     	 		     				row.createCell((short)61).setCellValue(new HSSFRichTextString(hmodcVo.getIpCompnChargCopdedMINMAX()));
		     		     		     				
		     		     		     				row.createCell((short)62).setCellValue(new HSSFRichTextString(hmodcVo.getIpOverallInvstPPL()));
		     		     		     				row.createCell((short)63).setCellValue(new HSSFRichTextString(hmodcVo.getIpLabAndPathPPL()));
		     		     		     				row.createCell((short)64).setCellValue(new HSSFRichTextString(hmodcVo.getIpExceptLabAndPathPPL()));
		     		     		     				row.createCell((short)65).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvLabProviderTypes()));
		     		     		     				row.createCell((short)66).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvLabPreapprovalReqdYesNo()));
		     		     		     				row.createCell((short)67).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvLabPreapprovalReqdLimit()));
		     		     		     				row.createCell((short)68).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvLabPpl()));
		     		     		     				row.createCell((short)69).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvLabNoOfSess()));
		     		     		     				row.createCell((short)70).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvLabCopay()));
		     		     		     				row.createCell((short)71).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvLabDeductable()));
		     		     		     				row.createCell((short)72).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvLabCopdedMINMAX()));
		     		     		     				row.createCell((short)73).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvPatProviderTypes()));
		     		     		     				row.createCell((short)74).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvPatPreapprovalReqdYesNo()));
		     		     		     				row.createCell((short)75).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvPatPreapprovalReqdLimit()));
		     		     		     				row.createCell((short)76).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvPatPpl()));
		     		     		     				row.createCell((short)77).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvPatNoOfSess()));
		     		     		     				row.createCell((short)78).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvPatCopay()));
		     		     		     				row.createCell((short)79).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvPatDeductable()));
		     		     		     				row.createCell((short)80).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvPatCopdedMINMAX()));
		     		     		     				row.createCell((short)81).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvUltraProviderFacilityTypes()));
		     		     		     				row.createCell((short)82).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvUltraPreapprovalReqdYesNo()));
		     		     		     				row.createCell((short)83).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvUltraPreapprovalReqdLimit()));
		     		     		     				row.createCell((short)84).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvUltraPpl()));
		     		     		     				row.createCell((short)85).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvUltraNoOfSess()));
		     		     		     				row.createCell((short)86).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvUltraCopay()));
		     		     		     				row.createCell((short)87).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvUltraDeductable()));
		     		     		     				row.createCell((short)88).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvUltraCopdedMINMAX()));
		     		     		     				row.createCell((short)89).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvCtScanProviderFacilityTypes()));
		     		     		     				row.createCell((short)90).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvCtScanPreapprovalReqdYesNo()));
		     		     		     				row.createCell((short)91).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvCtScanPreapprovalReqdLimit()));
		     		     		     				row.createCell((short)92).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvCtScanPpl()));
		     		     		     				row.createCell((short)93).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvCtScanNoOfSess()));
		     		     		     				row.createCell((short)94).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvCtScanCopay()));
		     		     		     				row.createCell((short)95).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvCtScanDeductable()));
		     		     		     				row.createCell((short)96).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvCtScanCopdedMINMAX()));
		     		     		     				row.createCell((short)97).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvMriProviderFacilityTypes()));
		     		     		     				row.createCell((short)98).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvMriPreapprovalReqdYesNo()));
		     		     		     				row.createCell((short)99).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvMriPreapprovalReqdLimit()));
		     		     		     				row.createCell((short)100).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvMriPpl()));
		     		     		     				row.createCell((short)101).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvMriNoOfSess()));
		     		     		     				row.createCell((short)102).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvMriCopay()));
		     		     		     				row.createCell((short)103).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvMriDeductable()));
		     		     		     				row.createCell((short)104).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvMriCopdedMINMAX()));
		     		     		     				
		     		     		     				row.createCell((short)105).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvDiagAndTherapProviderTypes()));
		     		     		     				row.createCell((short)106).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvDiagAndTherapPreapprovalReqdYesNo()));
		     		     		     				row.createCell((short)107).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvDiagAndTherapPreapprovalReqdLimit()));
		     		     		     				row.createCell((short)108).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvDiagAndTherapPpl()));
		     		     		     				row.createCell((short)109).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvDiagAndTherapNoOfSess()));
		     		     		     				row.createCell((short)110).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvDiagAndTherapCopay()));
		     		     		     				row.createCell((short)111).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvDiagAndTherapDeductable()));
		     		     		     				row.createCell((short)112).setCellValue(new HSSFRichTextString(hmodcVo.getIpInvDiagAndTherapCopdedMINMAX()));
		     		     		     				
		     		     		     				if(index==1){
		     		     		     					
		     		     		     					row.createCell((short)113).setCellValue(new HSSFRichTextString(hmodcVo.getIpMedicationsPPL()));
			     		     		     				row.createCell((short)114).setCellValue(new HSSFRichTextString(hmodcVo.getIpMedicationsCopay()));
			     		     		     				row.createCell((short)115).setCellValue(new HSSFRichTextString(hmodcVo.getIpMedicationsDEDUCTABLE()));
			     		     		     				row.createCell((short)116).setCellValue(new HSSFRichTextString(hmodcVo.getIpMedicationsCopdedMINMAX()));
			     		     		     				
			     		     		     				row.createCell((short)117).setCellValue(new HSSFRichTextString(hmodcVo.getIpIVFFluidsPPL()));
			     		     		     				row.createCell((short)118).setCellValue(new HSSFRichTextString(hmodcVo.getIpIVFFluidsCopay()));
			     		     		     				row.createCell((short)119).setCellValue(new HSSFRichTextString(hmodcVo.getIpIVFFluidsDEDUCTABLE()));
			     		     		     				row.createCell((short)120).setCellValue(new HSSFRichTextString(hmodcVo.getIpIVFFluidsCopdedMINMAX()));
			     		     		     				
			     		     		     				row.createCell((short)121).setCellValue(new HSSFRichTextString(hmodcVo.getIpBldTrnsfusnPPL()));
			     		     		     				row.createCell((short)122).setCellValue(new HSSFRichTextString(hmodcVo.getIpBldTrnsfusnCopay()));
			     		     		     				row.createCell((short)123).setCellValue(new HSSFRichTextString(hmodcVo.getIpBldTrnsfusnDEDUCTABLE()));
			     		     		     				row.createCell((short)124).setCellValue(new HSSFRichTextString(hmodcVo.getIpBldTrnsfusnCopdedMINMAX()));
			     		     		     				
			     		     		     				row.createCell((short)125).setCellValue(new HSSFRichTextString(hmodcVo.getIpAnalegicsPPL()));
			     		     		     				row.createCell((short)126).setCellValue(new HSSFRichTextString(hmodcVo.getIpAnalegicsCopay()));
			     		     		     				row.createCell((short)127).setCellValue(new HSSFRichTextString(hmodcVo.getIpAnalegicsDEDUCTABLE()));
			     		     		     				row.createCell((short)128).setCellValue(new HSSFRichTextString(hmodcVo.getIpAnalegicsCopdedMINMAX()));
			     		     		     				
			     		     		     				row.createCell((short)129).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgImplPPL()));
			     		     		     				row.createCell((short)130).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgImplCopay()));
			     		     		     				row.createCell((short)131).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgImplDEDUCTABLE()));
			     		     		     				row.createCell((short)132).setCellValue(new HSSFRichTextString(hmodcVo.getIpSrgImplCopdedMINMAX()));
			     		     		     				
			     		     		     				row.createCell((short)133).setCellValue(new HSSFRichTextString(hmodcVo.getIpChemoPPL()));
			     		     		     				row.createCell((short)134).setCellValue(new HSSFRichTextString(hmodcVo.getIpChemoCopay()));
			     		     		     				row.createCell((short)135).setCellValue(new HSSFRichTextString(hmodcVo.getIpChemoDEDUCTABLE()));
			     		     		     				row.createCell((short)136).setCellValue(new HSSFRichTextString(hmodcVo.getIpChemoCopdedMINMAX()));
			     		     		     				
			     		     		     				row.createCell((short)137).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsmAndDspsPPL()));
			     		     		     				row.createCell((short)138).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsmAndDspsCopay()));
			     		     		     				row.createCell((short)139).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsmAndDspsDEDUCTABLE()));
			     		     		     				row.createCell((short)140).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsmAndDspsCopdedMINMAX()));
			     		     		     				row.createCell((short)141).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsmAndDspsExPrdVal()));
			     		     		     				row.createCell((short)142).setCellValue(new HSSFRichTextString(hmodcVo.getIpConsmAndDspsExPrdUnt()));
			     		     		     		
		     		   		     					
		     		   		     				}
		     		     		     						
		     		     		     				row.createCell((short)143).setCellValue(new HSSFRichTextString(hmodcVo.getIpPhysioProviderFacilityTypes()));
		     		     		     				row.createCell((short)144).setCellValue(new HSSFRichTextString(hmodcVo.getIpPhysioPreapprovalReqdYesNo()));
		     		     		     				row.createCell((short)145).setCellValue(new HSSFRichTextString(hmodcVo.getIpPhysioPreapprovalLimit()));
		     		     		     				row.createCell((short)146).setCellValue(new HSSFRichTextString(hmodcVo.getIpPhysioPpl()));
		     		     		     				row.createCell((short)147).setCellValue(new HSSFRichTextString(hmodcVo.getIpPhysioNOOfSess()));
		     		     		     				row.createCell((short)148).setCellValue(new HSSFRichTextString(hmodcVo.getIpPhysioPerSessLimit()));
		     		     		     				row.createCell((short)149).setCellValue(new HSSFRichTextString(hmodcVo.getIpPhysioCopay()));
		     		     		     				row.createCell((short)150).setCellValue(new HSSFRichTextString(hmodcVo.getIpPhysioOvrDeductable()));
		     		     		     				row.createCell((short)151).setCellValue(new HSSFRichTextString(hmodcVo.getIpPhysioCopdedMINMAX()));
		     		     		     				
		     		     		     				row.createCell((short)152).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncGeoLoc()));
		     		     		     				row.createCell((short)153).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncCon()));
		     		     		     				row.createCell((short)154).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncEmr()));
		     		     		     				row.createCell((short)155).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncProTypes()));
		     		     		     				row.createCell((short)156).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncProFaclityTypes()));
		     		     		     				row.createCell((short)157).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncEmrNonemr()));
		     		     		     				row.createCell((short)158).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncPreaprvReqdYesNo()));
		     		     		     				row.createCell((short)159).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncPAL()));
		     		     		     				row.createCell((short)160).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncPPL()));
		     		     		     				row.createCell((short)161).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncCopay()));
		     		     		     				row.createCell((short)162).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncDeductable()));
		     		     		     				row.createCell((short)163).setCellValue(new HSSFRichTextString(hmodcVo.getIpAmblncCopdedMINMAX()));
		     		     		     	
		     		     		     	
		     		     		     				index++;
		     		     		     				
		     		     						}
		     		     			
		     		     // DAYC END
		     		  
	     			    			
	     			    		}
	     			    		
    			    		
	     			    		if(optcVo!=null){
	     			    			
	     			    			
	     			    			sheet = wb.getSheet("OPTICAL");
	     			    			if(sheet==null){
	     			    				
	     			    				 sheet = wb.createSheet("OPTICAL");
		     		   	           	      rowhead = sheet.createRow(0);
	     			    				
	     	/*		    			}
	     			    
 		     		         		int  policycnt = 0;
 			     		   	   		
 			     		   	   		
 			     		   	   		if(policycount == 1){
 			     		   	       		 sheet = wb.createSheet("OPTICAL");
 			     		   	           	      rowhead = sheet.createRow(0);
 			     		   	           	   policycnt = 1;

 			     		   	        		}else{
 			     		   	        				
 			     		   	        					String dayCareData = String.valueOf(wb.getSheet("OPTICAL"));

 			     		   					   	if(dayCareData.equals("null")){
 			     		   					   	 sheet = wb.createSheet("OPTICAL");
 			     		   					   	rowhead = sheet.createRow(0);
 			     		   					    	policycnt = 1;
 			     		   					   	}else{
 			     		   			                  sheet = wb.getSheet("OPTICAL");
 			     		   					   	}
 			     		   	        		}
 			     		   	   		  
 			     		   	   		if(policycnt == 1){*/
 		     		         	
 		     		         		    rowhead.createCell((short)0).setCellValue(new HSSFRichTextString("S.No"));
 		     		      			rowhead.createCell((short)1).setCellValue(new HSSFRichTextString("Policy No"));
 		     		      			rowhead.createCell((short)2).setCellValue(new HSSFRichTextString("Insurance Company Name"));
 		     		      			rowhead.createCell((short)3).setCellValue(new HSSFRichTextString("Product Name"));
 		     		  			rowhead.createCell((short)4).setCellValue(new HSSFRichTextString("PRODUCT NETWORK CATEGORY"));
 		     	      		    rowhead.createCell((short)5).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group ID"));
 		     	      		  rowhead.createCell((short)6).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group Name"));
 		     					rowhead.createCell((short)7).setCellValue(new HSSFRichTextString("POLICY ADMINISTRATIVE SERVICE TYPE"));
 		     					rowhead.createCell((short)8).setCellValue(new HSSFRichTextString("SUM INSURED"));
 		     		      		    
 		     		      		        rowhead.createCell((short)9).setCellValue(new HSSFRichTextString("OPTICAL-PPL"));
 		     		      				rowhead.createCell((short)10).setCellValue(new HSSFRichTextString("OPTICAL-COPAY"));
 		     		      				rowhead.createCell((short)11).setCellValue(new HSSFRichTextString("OPTICAL-DEDUCTIBLE"));
 		     		      				rowhead.createCell((short)12).setCellValue(new HSSFRichTextString("OPTICAL-COPDED-MINMAX"));
 		     		      	
 			     		   	   		}	
 		     		      		    
 		     		      		        int rowCount = sheet.getLastRowNum()+1;
 		     		       				 row = sheet.createRow(rowCount);
 		     		       				
 		     		       				row.createCell((short)0).setCellValue(new HSSFRichTextString(String.valueOf(rowCount)));
 		     		       				row.createCell((short)1).setCellValue(new HSSFRichTextString(optcVo.getPolicyNo()));
 		     		       				row.createCell((short)2).setCellValue(new HSSFRichTextString(optcVo.getInsuranceCompanyName()));
 		     		       				row.createCell((short)3).setCellValue(new HSSFRichTextString(optcVo.getProductName()));
 		     		       			row.createCell((short)4).setCellValue(new HSSFRichTextString(globalVO.getProductNetworkCategory()));
 		     	       				row.createCell((short)5).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupID()));
 		     	       				row.createCell((short)6).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupName()));
 		     	       				row.createCell((short)7).setCellValue(new HSSFRichTextString(globalVO.getPolicyAdmnstrtvSrvcType()));
 		     	       				row.createCell((short)8).setCellValue(new HSSFRichTextString(globalVO.getSumInsured()));
 		     		       				
 		     		       				row.createCell((short)9).setCellValue(new HSSFRichTextString(optcVo.getPpl()));
 		     		       				row.createCell((short)10).setCellValue(new HSSFRichTextString(optcVo.getCopay()));
 		     		       				row.createCell((short)11).setCellValue(new HSSFRichTextString(optcVo.getDeductible()));
 		     		       				row.createCell((short)12).setCellValue(new HSSFRichTextString(optcVo.getCopDedMinMax()));
 		     		       				
 		     		       		// OPTICAL END
	     			    			
	     			    		}
	     			    		 			
	     		     		     			if(dntlVo!=null){
	     		     		     				
	     		     		     				
	     		     		     				sheet = wb.getSheet("DENTAL");
	     		     			    			if(sheet==null){
	     		     			    				
	     		     			    				 sheet = wb.createSheet("DENTAL");
	     			     		   	           	      rowhead = sheet.createRow(0);
	     		     /*			    			}
		     		     		         		int  policycnt = 0;
		     			     		   	   		
		     			     		   	   		
		     			     		   	   		if(policycount == 1){
		     			     		   	       		 sheet = wb.createSheet("DENTAL");
		     			     		   	           	      rowhead = sheet.createRow(0);
		     			     		   	           	   policycnt = 1;

		     			     		   	        		}else{
		     			     		   	        				
		     			     		   	        					String dayCareData = String.valueOf(wb.getSheet("DENTAL"));

		     			     		   					   	if(dayCareData.equals("null")){
		     			     		   					   		System.out.println("is empty");
		     			     		   					   	 sheet = wb.createSheet("DENTAL");
		     			     		   					   	rowhead = sheet.createRow(0);
		     			     		   					    	policycnt = 1;
		     			     		   					   	}else{
		     			     		   					   		System.out.println("is not empty");
		     			     		   			                  sheet = wb.getSheet("DENTAL");
		     			     		   					   	}
		     			     		   	        		}
		     			     		   	   		  
		     			     		   	   		if(policycnt == 1){*/
		     		     		         	
		     		     		         		    rowhead.createCell((short)0).setCellValue(new HSSFRichTextString("S.No"));
		     		     		      			rowhead.createCell((short)1).setCellValue(new HSSFRichTextString("Policy No"));
		     		     		      			rowhead.createCell((short)2).setCellValue(new HSSFRichTextString("Insurance Company Name"));
		     		     		      			rowhead.createCell((short)3).setCellValue(new HSSFRichTextString("Product Name"));
		     		     		  			rowhead.createCell((short)4).setCellValue(new HSSFRichTextString("PRODUCT NETWORK CATEGORY"));
		     		     	      		    rowhead.createCell((short)5).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group ID"));
		     		     	      		  rowhead.createCell((short)6).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group Name"));
		     		     					rowhead.createCell((short)7).setCellValue(new HSSFRichTextString("POLICY ADMINISTRATIVE SERVICE TYPE"));
		     		     					rowhead.createCell((short)8).setCellValue(new HSSFRichTextString("SUM INSURED"));
		     		     		      		    
		     		     		      		        rowhead.createCell((short)9).setCellValue(new HSSFRichTextString("DENTAL-PPL"));
		     		     		      				rowhead.createCell((short)10).setCellValue(new HSSFRichTextString("DENTAL-COPAY"));
		     		     		      				rowhead.createCell((short)11).setCellValue(new HSSFRichTextString("DENTAL-DEDUCTIBLE"));
		     		     		      				rowhead.createCell((short)12).setCellValue(new HSSFRichTextString("DENTAL-COPDED-MINMAX"));
		     		     		      	
		     			     		   	   		}
		     		     		      		    
		     		     		      		         int rowCount = sheet.getLastRowNum()+1;
		     		     		       				 row = sheet.createRow(rowCount);
		     		     		       				
		     		     		       				row.createCell((short)0).setCellValue(new HSSFRichTextString(String.valueOf(rowCount)));
		     		     		       				row.createCell((short)1).setCellValue(new HSSFRichTextString(dntlVo.getPolicyNo()));
		     		     		       				row.createCell((short)2).setCellValue(new HSSFRichTextString(dntlVo.getInsuranceCompanyName()));
		     		     		       				row.createCell((short)3).setCellValue(new HSSFRichTextString(dntlVo.getProductName()));
		     		     		       			row.createCell((short)4).setCellValue(new HSSFRichTextString(globalVO.getProductNetworkCategory()));
		     		     	       				row.createCell((short)5).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupID()));
		     		     	       				row.createCell((short)6).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupName()));
		     		     	       				row.createCell((short)7).setCellValue(new HSSFRichTextString(globalVO.getPolicyAdmnstrtvSrvcType()));
		     		     	       				row.createCell((short)8).setCellValue(new HSSFRichTextString(globalVO.getSumInsured()));
		     		     		       				
		     		     		       				row.createCell((short)9).setCellValue(new HSSFRichTextString(dntlVo.getPpl()));
		     		     		       				row.createCell((short)10).setCellValue(new HSSFRichTextString(dntlVo.getCopay()));
		     		     		       				row.createCell((short)11).setCellValue(new HSSFRichTextString(dntlVo.getDeductible()));
		     		     		       				row.createCell((short)12).setCellValue(new HSSFRichTextString(dntlVo.getCopDedMinMax()));
		     		     		       				
		     		     		       		// DENTAL END
	     		     		     				
	     		     		     			}	
	     		     		     			
	     		     		     			
// SPCB STARTED
	     		     		     			
	     		     		     			
	     		     		     			if(spcbVOs!=null){
	     		     		     				
	     		     		     				
	     		     		     				if(!spcbVOs.isEmpty()){
	     		     		     					
	     		     		     					sheet = wb.getSheet("SPECIAL BENEFITS");
		     		     		     				if(sheet==null){
		     		     		     					
		     		     		     			  	 sheet = wb.createSheet("SPECIAL BENEFITS");
	  			     		   					   	rowhead = sheet.createRow(0);
		     		     		     					
		     	/*	     		     				}
	     		     		     			
		     		     		         		  int  policycnt = 0;
		     			     		   	   		
		     			     		   	   		if(policycount == 1){
		     			     		   	       		 sheet = wb.createSheet("SPECIAL BENEFITS");
		     			     		   	           	      rowhead = sheet.createRow(0);
		     			     		   	           	   policycnt = 1;
		     			     		   	  	   		
		     			     		   	        		}else{
		     			     		   	        		
		     			     		   	        					String dayCareData = String.valueOf(wb.getSheet("SPECIAL BENEFITS"));

		     			     		   					   	if(dayCareData.equals("null")){
		     			     		   					   	 sheet = wb.createSheet("SPECIAL BENEFITS");
		     			     		   					   	rowhead = sheet.createRow(0);
		     			     		   					    	policycnt = 1;
		     			     		   					   	}else{
		     			     		   			                  sheet = wb.getSheet("SPECIAL BENEFITS");
		     			     		   					   	}
		     			     		   	        		}
		     			     		   	   		  
		     			     		   	   		if(policycnt == 1){*/
		     		     		         	
		     		     		         		    rowhead.createCell((short)0).setCellValue(new HSSFRichTextString("S.No"));
		     		     		      			rowhead.createCell((short)1).setCellValue(new HSSFRichTextString("Policy No"));
		     		     		      			rowhead.createCell((short)2).setCellValue(new HSSFRichTextString("Insurance Company Name"));
		     		     		      			rowhead.createCell((short)3).setCellValue(new HSSFRichTextString("Product Name"));
		     		     		  			rowhead.createCell((short)4).setCellValue(new HSSFRichTextString("PRODUCT NETWORK CATEGORY"));
		     		     	      		    rowhead.createCell((short)5).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group ID"));
		     		     	      		  rowhead.createCell((short)6).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group Name"));
		     		     					rowhead.createCell((short)7).setCellValue(new HSSFRichTextString("POLICY ADMINISTRATIVE SERVICE TYPE"));
		     		     					rowhead.createCell((short)8).setCellValue(new HSSFRichTextString("SUM INSURED"));
		     		     					
		     		     		      		    rowhead.createCell((short)9).setCellValue(new HSSFRichTextString("SPECIAL/MISCELLANEOUS  BENEFITS"));
		     		     		          		rowhead.createCell((short)10).setCellValue(new HSSFRichTextString("OVERALL-PPL-AED"));
		     		     		      			rowhead.createCell((short)11).setCellValue(new HSSFRichTextString("OVERALL-COPAY-PERCENT"));
		     		     		      			rowhead.createCell((short)12).setCellValue(new HSSFRichTextString("OVERALL-DEDUCTIBLE-AED"));
		     		     		      			rowhead.createCell((short)13).setCellValue(new HSSFRichTextString("OVERALL-COPDED-MINMAX"));
		     		     		      			rowhead.createCell((short)14).setCellValue(new HSSFRichTextString("OTHER-CLMTYPE"));
		     		     		         	     			rowhead.createCell((short)15).setCellValue(new HSSFRichTextString("OTHER-NTWKTYPE"));
		     		     		         	     		    rowhead.createCell((short)16).setCellValue(new HSSFRichTextString("OTHER-ENCOUNTER(BENEFIT)-TYPE-ID"));
		     		     		      	     			rowhead.createCell((short)17).setCellValue(new HSSFRichTextString("OTHER-PREAPP-REQD"));
		     		     		      	     			rowhead.createCell((short)18).setCellValue(new HSSFRichTextString("OTHER-PREAPP-LIMIT"));
		     		     		      	     			rowhead.createCell((short)19).setCellValue(new HSSFRichTextString("OTHER-GEOGRAPHY-LOCATION"));
		     		     		      	     		    rowhead.createCell((short)20).setCellValue(new HSSFRichTextString("OTHER-CONTRIES-COVERED"));
		     		     		      	      		    rowhead.createCell((short)21).setCellValue(new HSSFRichTextString("OTHER-EMIRATES-ID"));
		     		     		      	    			rowhead.createCell((short)22).setCellValue(new HSSFRichTextString("OTHER-PROVD-TYPE"));
		     		     		      	    			rowhead.createCell((short)23).setCellValue(new HSSFRichTextString("OTHER-PROVD-FAC-TYPE"));
		     		     		      	    			rowhead.createCell((short)24).setCellValue(new HSSFRichTextString("OTHER-PPL-AED"));
		     		     		      	    		    rowhead.createCell((short)25).setCellValue(new HSSFRichTextString("OTHER-COPAY-PERCENT"));
		     		     		      	    		    rowhead.createCell((short)26).setCellValue(new HSSFRichTextString("OTHER-DEDUCTIBLE-AED"));
		     		     		      	        		rowhead.createCell((short)27).setCellValue(new HSSFRichTextString("OTHER-COPDED-MINMAX"));
		     		     		      	    		}	    			
		     		     		      	     			
		     		     		      	     			 index = 1;
		     		     		      
		     		     		      	     	
		     		     		      	     			for (HmoSpcbVO spcbVO : spcbVOs) {
		     		     		      	     			int rowCount = sheet.getLastRowNum()+1;
		     		     		      	     				 row = sheet.createRow(rowCount);
		     		     		      	     				
		     		     		      	     				row.createCell((short)0).setCellValue(new HSSFRichTextString(String.valueOf(rowCount)));
		     		     		      	     				row.createCell((short)1).setCellValue(new HSSFRichTextString(spcbVO.getPolicyNo()));
		     		     		      	     				row.createCell((short)2).setCellValue(new HSSFRichTextString(spcbVO.getInsuranceCompanyName()));
		     		     		      	     				row.createCell((short)3).setCellValue(new HSSFRichTextString(spcbVO.getProductName()));
		     		     		      	     			row.createCell((short)4).setCellValue(new HSSFRichTextString(globalVO.getProductNetworkCategory()));
		     		     		         				row.createCell((short)5).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupID()));
		     		     		         				row.createCell((short)6).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupName()));
		     		     		         				row.createCell((short)7).setCellValue(new HSSFRichTextString(globalVO.getPolicyAdmnstrtvSrvcType()));
		     		     		         				row.createCell((short)8).setCellValue(new HSSFRichTextString(globalVO.getSumInsured()));
		     		     		         				
		     		     		      	     				row.createCell((short)9).setCellValue(new HSSFRichTextString(spcbVO.getSpecialBenefits()));
		     		     		      	     				row.createCell((short)10).setCellValue(new HSSFRichTextString(spcbVO.getOvrlPplAED()));
		     		     		      	     				row.createCell((short)11).setCellValue(new HSSFRichTextString(spcbVO.getOvrlCopayPercent()));
		     		     		      	     				row.createCell((short)12).setCellValue(new HSSFRichTextString(spcbVO.getOvrlDeductibleAED()));
		     		     		      	     				row.createCell((short)13).setCellValue(new HSSFRichTextString(spcbVO.getOvrlCopDedMINMAX()));
		     		     		      	     				row.createCell((short)14).setCellValue(new HSSFRichTextString(spcbVO.getOthrClmType()));
		     		     		      	     				row.createCell((short)15).setCellValue(new HSSFRichTextString(spcbVO.getOthrNtwType()));
		     		     		      	     				row.createCell((short)16).setCellValue(new HSSFRichTextString(spcbVO.getOthrEncBenTypeId()));
		     		     		      	     				row.createCell((short)17).setCellValue(new HSSFRichTextString(spcbVO.getOthrPreAprvReqd()));
		     		     		      	     				row.createCell((short)18).setCellValue(new HSSFRichTextString(spcbVO.getOthrPAL()));
		     		     		      	     				row.createCell((short)19).setCellValue(new HSSFRichTextString(spcbVO.getOthrGeoLoc()));
		     		     		      	     			    row.createCell((short)20).setCellValue(new HSSFRichTextString(spcbVO.getOthrconCov()));
		     		     		      	     				row.createCell((short)21).setCellValue(new HSSFRichTextString(spcbVO.getOthrEmrID()));
		     		     		      	     				row.createCell((short)22).setCellValue(new HSSFRichTextString(spcbVO.getOthrProType()));
		     		     		      	     				row.createCell((short)23).setCellValue(new HSSFRichTextString(spcbVO.getOthrProFacType()));
		     		     		      	     				row.createCell((short)24).setCellValue(new HSSFRichTextString(spcbVO.getOthrPplAED()));
		     		     		      	     				row.createCell((short)25).setCellValue(new HSSFRichTextString(spcbVO.getOthrCopayPercent()));
		     		     		      	     				row.createCell((short)26).setCellValue(new HSSFRichTextString(spcbVO.getOthrDeductibleAED()));
		     		     		      	     				row.createCell((short)27).setCellValue(new HSSFRichTextString(spcbVO.getOthrCopDedMINMAX()));
		     		     		      	     					      	     				index++;
		     		     		      	     				
		     		     		      					}
	     		     		     			}
		     		   // SPCB END              
	     		     		     				
	     		     		     	} 
	     		     		      	     			
	     		    // AOC STARTED
	     		     		     			
	     		     		     			if(aocVos!=null){
	     		     		     				sheet = wb.getSheet("AREA OF COVERAGE");
	     		     		     				if(sheet==null){
	     		     		     					
	     		     		     				 sheet = wb.createSheet("AREA OF COVERAGE");
    			     		   	           	      rowhead = sheet.createRow(0);
	     		     		     					
	     		     	/*	     				}
	     		     		     	
                                               int  policycnt = 0;
		     			     		   	   		
		     			     		   	   		
		     			     		   	   		if(policycount == 1){
		     			     		   	       		 sheet = wb.createSheet("AREA OF COVERAGE");
		     			     		   	           	      rowhead = sheet.createRow(0);
		     			     		   	           	   policycnt = 1;

		     			     		   	        		}else{
		     			     		   	        					
		     			     		   	        					String dayCareData = String.valueOf(wb.getSheet("AREA OF COVERAGE"));
		     			     		           				
		     			     		   					   	if(dayCareData.equals("null")){
		     			     		   					   	 sheet = wb.createSheet("AREA OF COVERAGE");
		     			     		   					   	rowhead = sheet.createRow(0);
		     			     		   					    	policycnt = 1;
		     			     		   					   	}else{
		     			     		   			                  sheet = wb.getSheet("AREA OF COVERAGE");
		     			     		   					   	}
		     			     		   	        		}
		     			     		   	   		  
		     			     		   	   		if(policycnt == 1){*/
	     		     		     				
	     		     		      	   		    rowhead.createCell((short)0).setCellValue(new HSSFRichTextString("S.No"));
	     		     		      				rowhead.createCell((short)1).setCellValue(new HSSFRichTextString("Policy No"));
	     		     		      				rowhead.createCell((short)2).setCellValue(new HSSFRichTextString("Insurance Company Name"));
	     		     		      				rowhead.createCell((short)3).setCellValue(new HSSFRichTextString("Product Name"));
	     		     		      			rowhead.createCell((short)4).setCellValue(new HSSFRichTextString("PRODUCT NETWORK CATEGORY"));
	     		     		      		    rowhead.createCell((short)5).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group ID"));
	     		     		      		  rowhead.createCell((short)6).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group Name"));
	     		     						rowhead.createCell((short)7).setCellValue(new HSSFRichTextString("POLICY ADMINISTRATIVE SERVICE TYPE"));
	     		     						rowhead.createCell((short)8).setCellValue(new HSSFRichTextString("SUM INSURED"));
	     		     		      				
	     		     		      			    rowhead.createCell((short)9).setCellValue(new HSSFRichTextString("BENEFIT TYPE"));
	     		     		      	    		rowhead.createCell((short)10).setCellValue(new HSSFRichTextString("CLAIM-TYPE"));
	     		     		      				rowhead.createCell((short)11).setCellValue(new HSSFRichTextString("NETWOK-TYPE"));
	     		     		      				rowhead.createCell((short)12).setCellValue(new HSSFRichTextString("PROVIDER_TYPE"));
	     		     		      				rowhead.createCell((short)13).setCellValue(new HSSFRichTextString("ENCOUNTER-TYPE-ID"));
	     		     		      				rowhead.createCell((short)14).setCellValue(new HSSFRichTextString("PROVDR-FACLTY-TYPE"));
	     		     		      		    			
	     		     		      	   	     			rowhead.createCell((short)15).setCellValue(new HSSFRichTextString("GEOGRAPHICAL-LOCATION"));
	     		     		      	   	     		    rowhead.createCell((short)16).setCellValue(new HSSFRichTextString("GEO-LOCAT-PPL-AED"));
	     		     		      		     			rowhead.createCell((short)17).setCellValue(new HSSFRichTextString("GEO-LOCAT-PCL-AED"));
	     		     		      		     			rowhead.createCell((short)18).setCellValue(new HSSFRichTextString("GEO-LOCAT-PDL-AED"));
	     		     		      		     			rowhead.createCell((short)19).setCellValue(new HSSFRichTextString("GEO-LOCAT-COPAY-PERCENT"));
	     		     		      		      		    rowhead.createCell((short)20).setCellValue(new HSSFRichTextString("GEO-LOCAT-DED-AED"));
	     		     		      		    			rowhead.createCell((short)21).setCellValue(new HSSFRichTextString("GEO-LOCAT-COPDED-MINMAX"));
	     		     		      		    	     			
	     		     		      		    			rowhead.createCell((short)22).setCellValue(new HSSFRichTextString("COUNTRIES COVERED"));
	     		     		      		    			rowhead.createCell((short)23).setCellValue(new HSSFRichTextString("OTHER-NON-UAE-COUNTRIES-COVERED"));
	     		     		      		    		    rowhead.createCell((short)24).setCellValue(new HSSFRichTextString("UAE-PPL-AED"));
	     		     		      		    		    rowhead.createCell((short)25).setCellValue(new HSSFRichTextString("NON-UAE-COUNTRIES-PPL-AED"));
	     		     		      		        		rowhead.createCell((short)26).setCellValue(new HSSFRichTextString("UAE-PCL-AED"));
	     		     		      		    			rowhead.createCell((short)27).setCellValue(new HSSFRichTextString("NON-UAE-COUNTRIES-PCL-AED"));
	     		     		      		    			rowhead.createCell((short)28).setCellValue(new HSSFRichTextString("UAE-PDL-AED"));
	     		     		      		    			rowhead.createCell((short)29).setCellValue(new HSSFRichTextString("NON-UAE-COUNTRIES-PDL-AED"));
	     		     		      		    			        rowhead.createCell((short)30).setCellValue(new HSSFRichTextString("UAE-COPAY-PERCENT"));
	     		     		      		       	     			rowhead.createCell((short)31).setCellValue(new HSSFRichTextString("UAE-DEDUCTIBLE-AED"));
	     		     		      		       	     			rowhead.createCell((short)32).setCellValue(new HSSFRichTextString("UAE-COPDED-MINMAX"));
	     		     		      		       	     		    rowhead.createCell((short)33).setCellValue(new HSSFRichTextString("NON-UAE-COUNTRIES-COPAY-PERCENT"));
	     		     		      		    	     			rowhead.createCell((short)34).setCellValue(new HSSFRichTextString("NON-UAE-COUNTRIES-DED-AED"));
	     		     		      		    	     			rowhead.createCell((short)35).setCellValue(new HSSFRichTextString("NON-UAE-COUNTRIES-COPDED-MINMAX"));
	     		     		      		    	     			
	     		     		      		    	     			rowhead.createCell((short)36).setCellValue(new HSSFRichTextString("COVERED-EMIRATES"));
	     		     		      		    	   
	     		     		      		    	     			rowhead.createCell((short)37).setCellValue(new HSSFRichTextString("PPL-FOR-DXB"));
	     		     		      		    	      			rowhead.createCell((short)38).setCellValue(new HSSFRichTextString("PCL-FOR-DXB"));
	     		     		      		       	     			rowhead.createCell((short)39).setCellValue(new HSSFRichTextString("PDL-FOR-DXB"));
	     		     		      		       	     			rowhead.createCell((short)40).setCellValue(new HSSFRichTextString("COPAY-FOR-DXB"));
	     		     		      		       	     		    rowhead.createCell((short)41).setCellValue(new HSSFRichTextString("DEDUCTIBLE-FOR-DXB"));
	     		     		      		    	     			rowhead.createCell((short)42).setCellValue(new HSSFRichTextString("COPDED-MINMAX-FOR-DXB"));
	     		     		      		    	   
	     		     		      		    	    		rowhead.createCell((short)43).setCellValue(new HSSFRichTextString("PPL-FOR-AUH"));
    		     		      		    	      			rowhead.createCell((short)44).setCellValue(new HSSFRichTextString("PCL-FOR-AUH"));
    		     		      		       	     			rowhead.createCell((short)45).setCellValue(new HSSFRichTextString("PDL-FOR-AUH"));
    		     		      		       	     			rowhead.createCell((short)46).setCellValue(new HSSFRichTextString("COPAY-FOR-AUH"));
    		     		      		       	     		    rowhead.createCell((short)47).setCellValue(new HSSFRichTextString("DEDUCTIBLE-FOR-AUH"));
    		     		      		    	     			rowhead.createCell((short)48).setCellValue(new HSSFRichTextString("COPDED-MINMAX-FOR-AUH"));


    		     		      		    	    		rowhead.createCell((short)49).setCellValue(new HSSFRichTextString("PPL-FOR-AJM"));
		     		      		    	      			rowhead.createCell((short)50).setCellValue(new HSSFRichTextString("PCL-FOR-AJM"));
		     		      		       	     			rowhead.createCell((short)51).setCellValue(new HSSFRichTextString("PDL-FOR-AJM"));
		     		      		       	     			rowhead.createCell((short)52).setCellValue(new HSSFRichTextString("COPAY-FOR-AJM"));
		     		      		       	     		    rowhead.createCell((short)53).setCellValue(new HSSFRichTextString("DEDUCTIBLE-FOR-AJM"));
		     		      		    	     			rowhead.createCell((short)54).setCellValue(new HSSFRichTextString("COPDED-MINMAX-FOR-AJM"));


		     		      		    	    		rowhead.createCell((short)55).setCellValue(new HSSFRichTextString("PPL-FOR-FUJ"));
		     		      		    	      			rowhead.createCell((short)56).setCellValue(new HSSFRichTextString("PCL-FOR-FUJ"));
		     		      		       	     			rowhead.createCell((short)57).setCellValue(new HSSFRichTextString("PDL-FOR-FUJ"));
		     		      		       	     			rowhead.createCell((short)58).setCellValue(new HSSFRichTextString("COPAY-FOR-FUJ"));
		     		      		       	     		    rowhead.createCell((short)59).setCellValue(new HSSFRichTextString("DEDUCTIBLE-FOR-FUJ"));
		     		      		    	     			rowhead.createCell((short)60).setCellValue(new HSSFRichTextString("COPDED-MINMAX-FOR-FUJ"));
	     		     		      		    	     			
		     		      		    	     		rowhead.createCell((short)61).setCellValue(new HSSFRichTextString("PPL-FOR-RAK"));
		     		      		    	      			rowhead.createCell((short)62).setCellValue(new HSSFRichTextString("PCL-FOR-RAK"));
		     		      		       	     			rowhead.createCell((short)63).setCellValue(new HSSFRichTextString("PDL-FOR-RAK"));
		     		      		       	     			rowhead.createCell((short)64).setCellValue(new HSSFRichTextString("COPAY-FOR-RAK"));
		     		      		       	     		    rowhead.createCell((short)65).setCellValue(new HSSFRichTextString("DEDUCTIBLE-FOR-RAK"));
		     		      		    	     			rowhead.createCell((short)66).setCellValue(new HSSFRichTextString("COPDED-MINMAX-FOR-RAK"));
	     		     		      		    	     			
	     		     		      		    	     			
		     		      		    	     		rowhead.createCell((short)67).setCellValue(new HSSFRichTextString("PPL-FOR-SJH"));
		     		      		    	      			rowhead.createCell((short)68).setCellValue(new HSSFRichTextString("PCL-FOR-SJH"));
		     		      		       	     			rowhead.createCell((short)69).setCellValue(new HSSFRichTextString("PDL-FOR-SJH"));
		     		      		       	     			rowhead.createCell((short)70).setCellValue(new HSSFRichTextString("COPAY-FOR-SJH"));
		     		      		       	     		    rowhead.createCell((short)71).setCellValue(new HSSFRichTextString("DEDUCTIBLE-FOR-SJH"));
		     		      		    	     			rowhead.createCell((short)72).setCellValue(new HSSFRichTextString("COPDED-MINMAX-FOR-SJH"));
		     		      		    	     			
		     		      		    	     		rowhead.createCell((short)73).setCellValue(new HSSFRichTextString("PPL-FOR-UAQ"));
	     		      		    	      			rowhead.createCell((short)74).setCellValue(new HSSFRichTextString("PCL-FOR-UAQ"));
	     		      		       	     			rowhead.createCell((short)75).setCellValue(new HSSFRichTextString("PDL-FOR-UAQ"));
	     		      		       	     			rowhead.createCell((short)76).setCellValue(new HSSFRichTextString("COPAY-FOR-UAQ"));
	     		      		       	     		    rowhead.createCell((short)77).setCellValue(new HSSFRichTextString("DEDUCTIBLE-FOR-UAQ"));
	     		      		    	     			rowhead.createCell((short)78).setCellValue(new HSSFRichTextString("COPDED-MINMAX-FOR-UAQ"));
	     		      		    	     			
	     		      		    	     		rowhead.createCell((short)79).setCellValue(new HSSFRichTextString("PPL-FOR-KOH"));
    		      		    	      			rowhead.createCell((short)80).setCellValue(new HSSFRichTextString("PCL-FOR-KOH"));
    		      		       	     			rowhead.createCell((short)81).setCellValue(new HSSFRichTextString("PDL-FOR-KOH"));
    		      		       	     			rowhead.createCell((short)82).setCellValue(new HSSFRichTextString("COPAY-FOR-KOH"));
    		      		       	     		    rowhead.createCell((short)83).setCellValue(new HSSFRichTextString("DEDUCTIBLE-FOR-KOH"));
    		      		    	     			rowhead.createCell((short)84).setCellValue(new HSSFRichTextString("COPDED-MINMAX-FOR-KOH"));
    		      		    	     			
    		      		    	     		rowhead.createCell((short)85).setCellValue(new HSSFRichTextString("PPL-FOR-ALA"));
		      		    	      			rowhead.createCell((short)86).setCellValue(new HSSFRichTextString("PCL-FOR-ALA"));
		      		       	     			rowhead.createCell((short)87).setCellValue(new HSSFRichTextString("PDL-FOR-ALA"));
		      		       	     			rowhead.createCell((short)88).setCellValue(new HSSFRichTextString("COPAY-FOR-ALA"));
		      		       	     		    rowhead.createCell((short)89).setCellValue(new HSSFRichTextString("DEDUCTIBLE-FOR-ALA"));
		      		    	     			rowhead.createCell((short)90).setCellValue(new HSSFRichTextString("COPDED-MINMAX-FOR-ALA"));
		     			     		   	   
		     			     		   	   		}    			
	     		     		      		     			 index = 1;
	     		     		      		     		
	     		     		      		     			for (HmoAOCVo aocVo : aocVos) {
	     		     		      		     				int rowCount = sheet.getLastRowNum()+1;
	     		     		      		     				 row = sheet.createRow(rowCount);
	     		     		      		     				
	     		     		      		     				row.createCell((short)0).setCellValue(new HSSFRichTextString(String.valueOf(rowCount)));
	     		     		      		     				row.createCell((short)1).setCellValue(new HSSFRichTextString(aocVo.getPolicyNo()));
	     		     		      		     				row.createCell((short)2).setCellValue(new HSSFRichTextString(aocVo.getInsuranceCompanyName()));
	     		     		      		     				row.createCell((short)3).setCellValue(new HSSFRichTextString(aocVo.getProductName()));
	     		     		      		     			row.createCell((short)4).setCellValue(new HSSFRichTextString(globalVO.getProductNetworkCategory()));
	     		     		             				row.createCell((short)5).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupID()));
	     		     		             				row.createCell((short)6).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupName()));
	     		     		             				row.createCell((short)7).setCellValue(new HSSFRichTextString(globalVO.getPolicyAdmnstrtvSrvcType()));
	     		     		             				row.createCell((short)8).setCellValue(new HSSFRichTextString(globalVO.getSumInsured()));
	     		     		      		     				
	     		     		      		     				row.createCell((short)9).setCellValue(new HSSFRichTextString(aocVo.getBenefitType()));
	     		     		      		     				row.createCell((short)10).setCellValue(new HSSFRichTextString(aocVo.getClaimType()));
	     		     		      		     				row.createCell((short)11).setCellValue(new HSSFRichTextString(aocVo.getNtwrkType()));
	     		     		      		     				row.createCell((short)12).setCellValue(new HSSFRichTextString(aocVo.getProviderType()));
	     		     		      		     				row.createCell((short)13).setCellValue(new HSSFRichTextString(aocVo.getEncTypeId()));
	     		     		      		     				row.createCell((short)14).setCellValue(new HSSFRichTextString(aocVo.getProviderFaclityType()));
	     		     		      		     				
	     		     		      		     				row.createCell((short)15).setCellValue(new HSSFRichTextString(aocVo.getGeoLocat()));
	     		     		      		     				row.createCell((short)16).setCellValue(new HSSFRichTextString(aocVo.getGeoLocatPplAED()));
	     		     		      		     				row.createCell((short)17).setCellValue(new HSSFRichTextString(aocVo.getGeoLocatPclAED()));
	     		     		      		     				row.createCell((short)18).setCellValue(new HSSFRichTextString(aocVo.getGeoLocatPdlAED()));
	     		     		      		     				row.createCell((short)19).setCellValue(new HSSFRichTextString(aocVo.getGeoLocatCpyPercnt()));
	     		     		      		     				row.createCell((short)20).setCellValue(new HSSFRichTextString(aocVo.getGeoLocatDedAED()));
	     		     		      		     				row.createCell((short)21).setCellValue(new HSSFRichTextString(aocVo.getGeoLocatCopDedMinMax()));
	     		     		      		     				
	     		     		      		     				row.createCell((short)22).setCellValue(new HSSFRichTextString(aocVo.getConCov()));
	     		     		      		     				row.createCell((short)23).setCellValue(new HSSFRichTextString(aocVo.getOthrNonUAEconCov()));
	     		     		      		     				row.createCell((short)24).setCellValue(new HSSFRichTextString(aocVo.getUAEPplAED()));
	     		     		      		     				row.createCell((short)25).setCellValue(new HSSFRichTextString(aocVo.getNonUAEConPplAED()));
	     		     		      		     				row.createCell((short)26).setCellValue(new HSSFRichTextString(aocVo.getUAEPclAED()));
	     		     		      		     				row.createCell((short)27).setCellValue(new HSSFRichTextString(aocVo.getNonUAEConPclAED()));
	     		     		      		     				row.createCell((short)28).setCellValue(new HSSFRichTextString(aocVo.getUAEPdlAED()));
	     		     		      		     				row.createCell((short)29).setCellValue(new HSSFRichTextString(aocVo.getNonUAEConPdlAED()));
	     		     		      		     				row.createCell((short)30).setCellValue(new HSSFRichTextString(aocVo.getUAECpyPercnt()));
	     		     		      		     				row.createCell((short)31).setCellValue(new HSSFRichTextString(aocVo.getUAEDedAED()));
	     		     		      		     				row.createCell((short)32).setCellValue(new HSSFRichTextString(aocVo.getUAECopDedMinMax()));
	     		     		      		     				row.createCell((short)33).setCellValue(new HSSFRichTextString(aocVo.getNonUAECpyPercnt()));
	     		     		      		     				row.createCell((short)34).setCellValue(new HSSFRichTextString(aocVo.getNonUAEDedAED()));
	     		     		      		     				row.createCell((short)35).setCellValue(new HSSFRichTextString(aocVo.getNonUAECopDedMinMax()));
	     		     		      		     				
	     		     		      		     				row.createCell((short)36).setCellValue(new HSSFRichTextString(aocVo.getCovEmirates()));
	     		     		      		     				
	     		     		      		     				row.createCell((short)37).setCellValue(new HSSFRichTextString(aocVo.getPplForDXB()));
	     		     		      		     				row.createCell((short)38).setCellValue(new HSSFRichTextString(aocVo.getPclForDXB()));
	     		     		      		     				row.createCell((short)39).setCellValue(new HSSFRichTextString(aocVo.getPdlForDXB()));
	     		     		      		     				row.createCell((short)40).setCellValue(new HSSFRichTextString(aocVo.getCpyPercntForDXB()));
	     		     		      		     				row.createCell((short)41).setCellValue(new HSSFRichTextString(aocVo.getDedForDXB()));
	     		     		      		     				row.createCell((short)42).setCellValue(new HSSFRichTextString(aocVo.getCopDedMinMaxForDXB()));
    		     		      		     				
		     		      		     				
    		     		      		     			row.createCell((short)43).setCellValue(new HSSFRichTextString(aocVo.getPplForAUH()));
		     		      		     				row.createCell((short)44).setCellValue(new HSSFRichTextString(aocVo.getPclForAUH()));
		     		      		     				row.createCell((short)45).setCellValue(new HSSFRichTextString(aocVo.getPdlForAUH()));
		     		      		     				row.createCell((short)46).setCellValue(new HSSFRichTextString(aocVo.getCpyPercntForAUH()));
		     		      		     				row.createCell((short)47).setCellValue(new HSSFRichTextString(aocVo.getDedForAUH()));
		     		      		     				row.createCell((short)48).setCellValue(new HSSFRichTextString(aocVo.getCopDedMinMaxForAUH()));
		     		      		     				
		     		      		     			row.createCell((short)49).setCellValue(new HSSFRichTextString(aocVo.getPplForAJM()));
		     		      		     				row.createCell((short)50).setCellValue(new HSSFRichTextString(aocVo.getPclForAJM()));
		     		      		     				row.createCell((short)51).setCellValue(new HSSFRichTextString(aocVo.getPdlForAJM()));
		     		      		     				row.createCell((short)52).setCellValue(new HSSFRichTextString(aocVo.getCpyPercntForAJM()));
		     		      		     				row.createCell((short)53).setCellValue(new HSSFRichTextString(aocVo.getDedForAJM()));
		     		      		     				row.createCell((short)54).setCellValue(new HSSFRichTextString(aocVo.getCopDedMinMaxForAJM()));
		     		      		     				
		     		      		     			row.createCell((short)55).setCellValue(new HSSFRichTextString(aocVo.getPplForFUJ()));
		     		      		     				row.createCell((short)56).setCellValue(new HSSFRichTextString(aocVo.getPclForFUJ()));
		     		      		     				row.createCell((short)57).setCellValue(new HSSFRichTextString(aocVo.getPdlForFUJ()));
		     		      		     				row.createCell((short)58).setCellValue(new HSSFRichTextString(aocVo.getCpyPercntForFUJ()));
		     		      		     				row.createCell((short)59).setCellValue(new HSSFRichTextString(aocVo.getDedForFUJ()));
		     		      		     				row.createCell((short)60).setCellValue(new HSSFRichTextString(aocVo.getCopDedMinMaxForFUJ()));
		     		      		     				
		     		      		     			row.createCell((short)61).setCellValue(new HSSFRichTextString(aocVo.getPplForRAK()));
		     		      		     				row.createCell((short)62).setCellValue(new HSSFRichTextString(aocVo.getPclForRAK()));
		     		      		     				row.createCell((short)63).setCellValue(new HSSFRichTextString(aocVo.getPdlForRAK()));
		     		      		     				row.createCell((short)64).setCellValue(new HSSFRichTextString(aocVo.getCpyPercntForRAK()));
		     		      		     				row.createCell((short)65).setCellValue(new HSSFRichTextString(aocVo.getDedForRAK()));
		     		      		     				row.createCell((short)66).setCellValue(new HSSFRichTextString(aocVo.getCopDedMinMaxForRAK()));
		     		      		     				
		     		      		     			row.createCell((short)67).setCellValue(new HSSFRichTextString(aocVo.getPplForSJH()));
		     		      		     				row.createCell((short)68).setCellValue(new HSSFRichTextString(aocVo.getPclForSJH()));
		     		      		     				row.createCell((short)69).setCellValue(new HSSFRichTextString(aocVo.getPdlForSJH()));
		     		      		     				row.createCell((short)70).setCellValue(new HSSFRichTextString(aocVo.getCpyPercntForSJH()));
		     		      		     				row.createCell((short)71).setCellValue(new HSSFRichTextString(aocVo.getDedForSJH()));
		     		      		     				row.createCell((short)72).setCellValue(new HSSFRichTextString(aocVo.getCopDedMinMaxForSJH()));
		     		      		     				
		     		      		     			row.createCell((short)73).setCellValue(new HSSFRichTextString(aocVo.getPplForUAQ()));
		     		      		     				row.createCell((short)74).setCellValue(new HSSFRichTextString(aocVo.getPclForUAQ()));
		     		      		     				row.createCell((short)75).setCellValue(new HSSFRichTextString(aocVo.getPdlForUAQ()));
		     		      		     				row.createCell((short)76).setCellValue(new HSSFRichTextString(aocVo.getCpyPercntForUAQ()));
		     		      		     				row.createCell((short)77).setCellValue(new HSSFRichTextString(aocVo.getDedForUAQ()));
		     		      		     				row.createCell((short)78).setCellValue(new HSSFRichTextString(aocVo.getCopDedMinMaxForUAQ()));
	     		      		     				
		     		      		     			row.createCell((short)79).setCellValue(new HSSFRichTextString(aocVo.getPplForKOH()));
	     		      		     				row.createCell((short)80).setCellValue(new HSSFRichTextString(aocVo.getPclForKOH()));
	     		      		     				row.createCell((short)81).setCellValue(new HSSFRichTextString(aocVo.getPdlForKOH()));
	     		      		     				row.createCell((short)82).setCellValue(new HSSFRichTextString(aocVo.getCpyPercntForKOH()));
	     		      		     				row.createCell((short)83).setCellValue(new HSSFRichTextString(aocVo.getDedForKOH()));
	     		      		     				row.createCell((short)84).setCellValue(new HSSFRichTextString(aocVo.getCopDedMinMaxForKOH()));
	     		     		      		     	
	     		      		     			row.createCell((short)85).setCellValue(new HSSFRichTextString(aocVo.getPplForALA()));
    		      		     				row.createCell((short)86).setCellValue(new HSSFRichTextString(aocVo.getPclForALA()));
    		      		     				row.createCell((short)87).setCellValue(new HSSFRichTextString(aocVo.getPdlForALA()));
    		      		     				row.createCell((short)88).setCellValue(new HSSFRichTextString(aocVo.getCpyPercntForALA()));
    		      		     				row.createCell((short)89).setCellValue(new HSSFRichTextString(aocVo.getDedForALA()));
    		      		     				row.createCell((short)90).setCellValue(new HSSFRichTextString(aocVo.getCopDedMinMaxForALA()));
	     		     		      		     	
	     		     		      		     				index++;
	     		     		      		     				
	     		     		      						}
	     		     		      		
	     		     		      	     			
	     		     		      	     			
	     		    // AOC END
	     		     		     				
	     		     		     			}
	     		     		      	     		
	     		     		      		     			
	     		    // General Exclusion Started
	     		     		     			
	     		     		     			if(exlcusnVOs!=null){
	     		     		     				
	     		     		     				
	     		     		     				sheet = wb.getSheet("GENERAL EXCLUSION");
	     		     		     				if(sheet==null){
	     		     		     					
	     		     		     			 	 sheet = wb.createSheet("GENERAL EXCLUSION");
  			     		   					   	rowhead = sheet.createRow(0);
	     		     		     					
	     		  /*   		     				}
	     		     		       
                                          int  policycnt = 0;
		     			     		  
		     			     		   	   		if(policycount == 1){
		     			     		   	       		 sheet = wb.createSheet("GENERAL EXCLUSION");
		     			     		   	           	      rowhead = sheet.createRow(0);
		     			     		   	           	   policycnt = 1;

		     			     		   	        		}else{
		     			     		   	        				
		     			     		   	        					String dayCareData = String.valueOf(wb.getSheet("GENERAL EXCLUSION"));

		     			     		   					   	if(dayCareData.equals("null")){
		     			     		   					   	 sheet = wb.createSheet("GENERAL EXCLUSION");
		     			     		   					   	rowhead = sheet.createRow(0);
		     			     		   					    	policycnt = 1;
		     			     		   					   	}else{
		     			     		   			                  sheet = wb.getSheet("GENERAL EXCLUSION");
		     			     		   					   	}
		     			     		   	        		}
		     			     		   	   		  
		     			     		   	   		if(policycnt == 1){*/
		     		     		         		    rowhead.createCell((short)0).setCellValue(new HSSFRichTextString("S.No"));
		     		     		      			rowhead.createCell((short)1).setCellValue(new HSSFRichTextString("Policy No"));
		     		     		      			rowhead.createCell((short)2).setCellValue(new HSSFRichTextString("Insurance Company Name"));
		     		     		      			rowhead.createCell((short)3).setCellValue(new HSSFRichTextString("Product Name"));
		     		     		  			rowhead.createCell((short)4).setCellValue(new HSSFRichTextString("PRODUCT NETWORK CATEGORY"));
		     		     	      		    rowhead.createCell((short)5).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group ID"));
		     		     	      		  rowhead.createCell((short)6).setCellValue(new HSSFRichTextString("Corporate Group/Sub Group Name"));
		     		     					rowhead.createCell((short)7).setCellValue(new HSSFRichTextString("POLICY ADMINISTRATIVE SERVICE TYPE"));
		     		     					rowhead.createCell((short)8).setCellValue(new HSSFRichTextString("SUM INSURED"));
		     		     		      	     			
		     		     		      		    rowhead.createCell((short)9).setCellValue(new HSSFRichTextString("General Exclusion Benefit Types"));
		     		     		          		rowhead.createCell((short)10).setCellValue(new HSSFRichTextString("Exclusion Period Value"));
		     		     		      			rowhead.createCell((short)11).setCellValue(new HSSFRichTextString("Exclusion Period Unit (Days/Years/Months)"));
		     		     		      			rowhead.createCell((short)12).setCellValue(new HSSFRichTextString("Copay (%)"));
		     		     		      			rowhead.createCell((short)13).setCellValue(new HSSFRichTextString("Deductible (AED)"));
		     		     		      			rowhead.createCell((short)14).setCellValue(new HSSFRichTextString("Copay-Deductible-MINMAX"));
		     		     		         	     			rowhead.createCell((short)15).setCellValue(new HSSFRichTextString("Per policy Limit"));
		     		     		         	     		    rowhead.createCell((short)16).setCellValue(new HSSFRichTextString("Per Claim Limit"));
		     		     		      	     			rowhead.createCell((short)17).setCellValue(new HSSFRichTextString("Treatment type allowed (Contains/Does not contains)"));
		     		     		      	     			rowhead.createCell((short)18).setCellValue(new HSSFRichTextString("Treatment type allowed"));
		     		     		      	     			
		     		     		      	      		    rowhead.createCell((short)19).setCellValue(new HSSFRichTextString("Medications restricted to Members age value"));
		     		     		      	    			rowhead.createCell((short)20).setCellValue(new HSSFRichTextString("Medications restricted to Members age Unit (Days/Years/Months)"));
		     			     		   	   		}
		     		     		      	    			    			
		     		     		      	     			
		     		     		      	     			 index = 1;
		     		     		      	     		
		     		     		      	     			for (HmoGnrlExlcusnVO exlcusnVO : exlcusnVOs) {
		     		     		      	     				int rowCount = sheet.getLastRowNum()+1;
		     		     		      	     				
		     		     		      	     				 row = sheet.createRow(rowCount);
		     		     		      	     				
		     		     		      	     				row.createCell((short)0).setCellValue(new HSSFRichTextString(String.valueOf(rowCount)));
		     		     		      	     				row.createCell((short)1).setCellValue(new HSSFRichTextString(exlcusnVO.getPolicyNo()));
		     		     		      	     				row.createCell((short)2).setCellValue(new HSSFRichTextString(exlcusnVO.getInsuranceCompanyName()));
		     		     		      	     				row.createCell((short)3).setCellValue(new HSSFRichTextString(exlcusnVO.getProductName()));
		     		     		      	     			row.createCell((short)4).setCellValue(new HSSFRichTextString(globalVO.getProductNetworkCategory()));
		     		     		         				row.createCell((short)5).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupID()));
		     		     		         				row.createCell((short)6).setCellValue(new HSSFRichTextString(globalVO.getCorporateGroupName()));
		     		     		         				row.createCell((short)7).setCellValue(new HSSFRichTextString(globalVO.getPolicyAdmnstrtvSrvcType()));
		     		     		         				row.createCell((short)8).setCellValue(new HSSFRichTextString(globalVO.getSumInsured()));
		     		     		      	     		
		     		     		      	     				row.createCell((short)9).setCellValue(new HSSFRichTextString(exlcusnVO.getGnrxBenTypes()));
		     		     		      	     				row.createCell((short)10).setCellValue(new HSSFRichTextString(exlcusnVO.getExclusionPeriodValue()));
		     		     		      	     				row.createCell((short)11).setCellValue(new HSSFRichTextString(exlcusnVO.getExclusionPeriodUnit()));
		     		     		      	     				row.createCell((short)12).setCellValue(new HSSFRichTextString(exlcusnVO.getCopay()));
		     		     		      	     				row.createCell((short)13).setCellValue(new HSSFRichTextString(exlcusnVO.getDeductible()));
		     		     		      	     				row.createCell((short)14).setCellValue(new HSSFRichTextString(exlcusnVO.getCopayDeductibleMINMAX()));
		     		     		      	     		
		     		     		      	     				row.createCell((short)15).setCellValue(new HSSFRichTextString(exlcusnVO.getPerPolicyLimit()));
		     		     		      	     				row.createCell((short)16).setCellValue(new HSSFRichTextString(exlcusnVO.getPerClaimLimit()));
		     		     		      	     				row.createCell((short)17).setCellValue(new HSSFRichTextString(exlcusnVO.getTreatmentTypeAllowedFlag()));
		     		     		      	     				row.createCell((short)18).setCellValue(new HSSFRichTextString(exlcusnVO.getTreatmentTypeAllowed()));
		     		     		      	     				row.createCell((short)19).setCellValue(new HSSFRichTextString(exlcusnVO.getMedicationsRstrctdToMemAgeVal()));
		     		     		      	     				row.createCell((short)20).setCellValue(new HSSFRichTextString(exlcusnVO.getMedicationsRstrctdToMemAgeUnit()));
		     		     		      	     		
		     		     		      	     					      	     				index++;
		     		     		      	     				
		     		     		      					}
		     		      		     			
		    // General Exclusion End
	     		     		     				
	     		     		     			}
	
   					FileOutputStream fileOut = new FileOutputStream(fileDest);
   					wb.write(fileOut);
   					if(lFin!=null)   lFin.close();
   					fileOut.close();

    		
    	}catch(Exception e){
    		
    		log.error("printStackTrace");
    		e.printStackTrace();
    	
    	}

    	
    }
    

    public String unitConverter(String param){
    	
    	String strfpt="";
    	 if("DD".equals(param))strfpt="Days";
         else if("MM".equals(param))strfpt="Months";
         else if("YY".equals(param))strfpt="Years";
         else if("SGO".equals(param))strfpt="Government";
         else if("SPR".equals(param))strfpt="Private";
         else if("SGO|SPR".equals(param))strfpt="All";
         else if("CTM".equals(param))strfpt="Reimbursement";
         else if("CNH".equals(param))strfpt="Cashless";
         else if("CNH|CTM".equals(param))strfpt="All";
         else if("Y".equals(param))strfpt="NETWORK";
         else if("N".equals(param))strfpt="NON-NETWORK";
         else if("Y|N".equals(param))strfpt="All";
         else if("4".equals(param))strfpt="Emergency";
         else if("3".equals(param))strfpt="NON-Emergency";
         else if("4|3".equals(param))strfpt="All";
         else if("6".equals(param))strfpt="Emergency";
         else if("5".equals(param))strfpt="NON-Emergency";
         else if("6|5".equals(param))strfpt="All";
         else if("IN".equals(param))strfpt="Contains";
         else if("NOTIN".equals(param))strfpt="Does Not Contains";
    	 
		return strfpt;
    	
    }
    
    public String keyConverter1(String s,String ident, String param) throws TTKException{
    	
    	
    	String conVal=new String();
    	RuleManager ruleManagerObject=this.getRuleManagerObject();
    	
    	Collection<Object> resultList=null;
    	if("Geo".equals(ident)){
    		
    		// resultList=Cache.getCacheObject("GeoLocations");
    		 resultList=resultListGeo;
    		
    	}else if("Pro".equals(ident)){
    		
   		 //resultList=Cache.getCacheObject("providerType");
   		 resultList=resultListPro;
   		
   	}else if("Con".equals(ident)){
   		Integer p=Integer.parseInt(param);
   		if(p==2||p==8||p==3||p==7){
   			
   			ArrayList<String> val=new ArrayList<String>();
   			
   	    	
   	    	StringTokenizer st=new StringTokenizer(s, "|");
   	    	while(st.hasMoreTokens()){
   	    		String k=st.nextToken();
   				val.add(k);
   			
   			}
   	    
   	    	if(val.contains("175")){
  
   	    		if(val.size()>1){
   	    			conVal="UAE and other countries in this geography location";
   	    		}else{
   	    			conVal="Only UAE";
   	    		}
   	    		
   	    	}else{
   	    		
   	    		conVal="Only other Non-UAE countries in this geography location";
   	    		
   	    	}
   			
   			
   		}else{
   			
   			conVal="UAE is not included in this geography location group";
   			
   		}
		
	}else if("Emr".equals(ident)){
		
		
		ArrayList<String> val=new ArrayList<String>();
			
	    	if(param!=null){
	    		
	    		StringTokenizer st=new StringTokenizer(param, "|");
		    	while(st.hasMoreTokens()){
		    		String k=st.nextToken();
					val.add(k);
				
				}
	    		
	    	}
	    
	    	if(val.contains("175")){

	    		//resultList=Cache.getDynamicCacheObject("EmirateList", "175");
	    		 resultList=resultListEmr;
	    	}else{
	    		
				conVal="NA";
	    		
	    	}
 
		
	}else if("EncSPCB".equals(ident)){
		
			resultList=ruleManagerObject.getEncounterTypesSPCB(param);   
			
	}else if("Enc".equals(ident)){
		
		resultList=ruleManagerObject.getEncounterTypes(param);   

	}else if("Hos".equals(ident)){
		
		ArrayList alDaynValues=new ArrayList<>();
		
		CacheObject cache1=new CacheObject();         			
		cache1.setCacheId("SGO");
		cache1.setCacheDesc("Government");
		
		CacheObject cache2=new CacheObject();         			
		cache2.setCacheId("SPR");
		cache2.setCacheDesc("Private");
		alDaynValues.add(cache2);
		
	}else if("Inv".equals(ident)){
		ArrayList alDaynValues=getInvestTypes();            			
		
	}else if("ROOM_TYPE".equals(ident)){
		
		 resultList=resultListRmTyp;
		
	}else if("TREATMENT_TYPE".equals(ident)){
		
		 resultList=resultListTmtType;
		
	}

		if(!("Con".equals(ident))){
			
			StringBuffer val=new StringBuffer();
			
			if(s!=null){
				
			   	StringTokenizer st=new StringTokenizer(s, "|");
		    	while(st.hasMoreTokens()){
		    		String k=st.nextToken();
					
					if(resultList!=null){
						
						Iterator itera=resultList.iterator();
						
						while(itera.hasNext()){
							
							CacheObject voBean=(CacheObject)itera.next();
							if(voBean.getCacheId().equals(k)){
								val.append(voBean.getCacheDesc());
								val.append(",");
							}
						}
						
					}
				
					
				}
				
			}
			

	    	if(val.length()>0){
	    		val.deleteCharAt(val.length()-1);
	    		conVal= val.toString();
	    	}
	    	
	    	
			
		}
    	
		return conVal;
    	
    }
    

    
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

    
	/*public String keyVal(String key){
		String val="";
		if("DXB".equalsIgnoreCase(key))
		{
			val="Durban";
		}
		else if("AUH".equalsIgnoreCase(key))
		{
			val="Australia";
		}else if("AJM".equalsIgnoreCase(key))
		{
			val="Ajmer";
		}else if("FUJ".equalsIgnoreCase(key))
		{
			val="Fulka";
		}else if("RAK".equalsIgnoreCase(key))
		{
			val="Raka";
		}else if("SJH".equalsIgnoreCase(key))
		{
			val="Sarjah";
		}else if("UAQ".equalsIgnoreCase(key))
		{
			val="America";
		}else if("ALA".equalsIgnoreCase(key))
		{
			val="Alamoda";
		}else if("KHO".equalsIgnoreCase(key))
		{
			val="KHOJA";
		}
		return val;
		
	}*/
    
    public ActionForward addBenefit(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    //	log.info("Inside addBenefit");
    	try{
    		 setLinks(request);
    	RuleManager ruleManagerObject=this.getRuleManagerObject();
    	 DynaActionForm frmDefineRules= (DynaActionForm)form;
    	 
         LinkedHashMap<String,Long> lhmConfBenefitTypes=(LinkedHashMap)frmDefineRules.get("confBenefitTypes");
         
         if(lhmConfBenefitTypes==null)lhmConfBenefitTypes=new LinkedHashMap<>();
        
    	 RuleVO ruleVO=null;
    	 ruleVO=(RuleVO)FormUtils.getFormValues(frmDefineRules,this, mapping, request);         
         ruleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    	 String strBenefitID=	request.getParameter("benefitID");
    	 ruleVO.setBenefitRuleSeqID(lhmConfBenefitTypes.get(strBenefitID));
    	 Document confDocument=RuleXmlUtility.getConfRuleConfigDetails(request,lhmConfBenefitTypes.get(strBenefitID));
    	 
    	// String strProPolRulSeqID=TTKCommon.checkNull(frmDefineRules.getString("prodPolicyRuleSeqID"));
    	 if("GLOB".equals(strBenefitID)&&ruleVO.getProdPolicyRuleSeqID()==null) {            
    		 String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            
            long lngProdPolicyRuleSeqId=0;
              
                ruleVO.setSeqID(TTKCommon.getWebBoardId(request));
                lngProdPolicyRuleSeqId=ruleManagerObject.saveProdPolicyRule(ruleVO,"P");               
                frmDefineRules.set("prodPolicyRuleSeqID",new Long(lngProdPolicyRuleSeqId).toString());
                frmDefineRules.set("benefitRuleCompletedYN","N");
                ruleVO.setProdPolicyRuleSeqID(lngProdPolicyRuleSeqId);
                request.getSession().setAttribute("frmDefineRules",frmDefineRules);
        }
    	 ruleVO.setBenefitID(strBenefitID);
    	 ruleVO.setBenefitRuleXml(confDocument.asXML());  
    	 request.setAttribute("CurtNodeConfDtls", confDocument);  
    	
    	 //Provider Copay Details Configuration Setting
    	 StringWriter stringWriter=getProviderCopayRulesMarshaller(request);    	 
    	 ruleVO.setProviderCopayRulesWriter(stringWriter);
    	 
    	 
    	int updateRow= ruleManagerObject.saveBenefitRules(ruleVO);
    	frmDefineRules.set("confBenefitTypes", ruleManagerObject.getConfBenefitTypes(frmDefineRules.getString("prodPolicyRuleSeqID")));
    	HashMap<String,ArrayList<Condition>> hmProCopayDetails=null;
    	Reader reader=null;
    	if(lhmConfBenefitTypes.get(strBenefitID)!=null){
    	reader=ruleManagerObject.getMohProCopayDetails(lhmConfBenefitTypes.get(strBenefitID));
    	}		
    	    		if(reader!=null){
    	    			hmProCopayDetails=getProviderCopayRulesUnmarshaller(reader);
    	    		}
    	request.getSession().setAttribute("MOHProviderRulesXmlConditions",hmProCopayDetails);
    	return this.getForward(strAddBenefitRule, mapping, request);
    	}
    	catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
		}//end of catch(Exception exp)
    }
    
  
  
  
  
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
  public ActionForward doCloseConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
          HttpServletResponse response) throws Exception{
	  try{
	  setLinks(request);
              log.info("MOHRuleAction - inside doCloseConf method....");
              return this.getForward(strNewMOHProductRules, mapping, request);
	  }
  	catch(TTKException expTTK)
      {
          return this.processExceptions(request, mapping, expTTK);
      }//end of catch(TTKException expTTK)
  	catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
		}//end of catch(Exception exp)
  }//end of doCloseConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
  public ActionForward doCloseConfList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
          HttpServletResponse response) throws Exception{
	  try{
           //   log.info("MOHRuleAction - inside doCloseConfList method....");
              setLinks(request);
              String strTTKActiveSubLink=TTKCommon.getActiveSubLink(request);
              if("Products".equals(strTTKActiveSubLink))return mapping.findForward("Administration.Products.Product Rules");
              else if("Policies".equals(strTTKActiveSubLink))return mapping.findForward("Administration.Policies.General");
              else  return mapping.findForward(strFailure);
	  }
  	catch(TTKException expTTK)
      {
          return this.processExceptions(request, mapping, expTTK);
      }//end of catch(TTKException expTTK)
  	catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
		}//end of catch(Exception exp)
  }//end of doCloseConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 
  
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
  public ActionForward removeBenefit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
          HttpServletResponse response) throws Exception{
           //   log.info("MOHRuleAction - inside removeBenefit method....");
              try{
            	  setLinks(request);
              RuleManager ruleManagerObject=this.getRuleManagerObject();
             	 DynaActionForm frmDefineRules= (DynaActionForm)form;
             	 LinkedHashMap<String,Long> lhmConfBenefitTypes=(LinkedHashMap)frmDefineRules.get("confBenefitTypes");
                        String strbenefitType=request.getParameter("benefitID");
                        String strProductRuleSeqID=frmDefineRules.getString("prodPolicyRuleSeqID");
                        RuleVO ruleVO=new RuleVO();
                        ruleVO.setBenefitRuleSeqID(lhmConfBenefitTypes.get(strbenefitType));
                        ruleVO.setBenefitID(strbenefitType);
                        ruleVO.setProdPolicyRuleSeqID(new Long(strProductRuleSeqID));
                        ruleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
             	int updated=ruleManagerObject.removeBenefitXml(ruleVO);
             	
            		 frmDefineRules.set("confBenefitTypes",ruleManagerObject.getConfBenefitTypes(strProductRuleSeqID));          
            	  request.setAttribute("displayMsg",benefitTypes.get(strbenefitType)+" Configuration Details Removed Successfully!");
              return this.getForward(strNewMOHProductRules, mapping, request);
              }
              catch(TTKException expTTK)
              {
                  return this.processExceptions(request, mapping, expTTK);
              }//end of catch(TTKException expTTK)
              catch(Exception exp)
      		{
      			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
      		}//end of catch(Exception exp)
  }//end of doCloseConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 
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
  public ActionForward overrideRules(ActionMapping mapping,ActionForm form,HttpServletRequest request,
          HttpServletResponse response) throws Exception{
           //   log.info("MOHRuleAction - inside overrideRules method....");
              try{
            	  setLinks(request);
            	  RuleManager ruleManagerObject=this.getRuleManagerObject();
             	 DynaActionForm frmDefineRules= (DynaActionForm)form;
                        String strProductRuleSeqID=frmDefineRules.getString("prodPolicyRuleSeqID");
             	int updated=ruleManagerObject.overideBenefitRules(strProductRuleSeqID, TTKCommon.getUserSeqId(request));
             	frmDefineRules.set("benefitRuleCompletedYN","N");
             	return this.getForward(strNewMOHProductRules, mapping, request);
              }
              catch(TTKException expTTK)
              {
                  return this.processExceptions(request, mapping, expTTK);
              }//end of catch(TTKException expTTK)
              catch(Exception exp)
      		{
      			return this.processExceptions(request, mapping, new TTKException(exp,"product"));
      		}//end of catch(Exception exp)
  }//end of doCloseConf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 
  
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
    public ActionForward doNewRulesConfig(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        //	log.info("Inside the doNewRulesConfig method of HospitalSearchAction");
            		setLinks(request);
            		 RuleManager ruleManagerObject=this.getRuleManagerObject();
            		DynaActionForm frmRules= (DynaActionForm)form;
            		
            		StringBuffer sbfCaption=new StringBuffer();
            		
                    //get the Rules for the Product
                    ArrayList alProductRule=ruleManagerObject.getProductRuleList(TTKCommon.getWebBoardId(request));

                    //create the tableData for the Rules
                    TableData  tableData = new TableData();
                    tableData.createTableInfo("ProductRulesTable",null);
                    tableData.setData(alProductRule,"search");
                    request.getSession().setAttribute("tableData",tableData);
                    String strActiveSubLink=TTKCommon.getActiveSubLink(request);
                    sbfCaption.append(buildCaption(strActiveSubLink,request));
                    
                    frmRules.set("caption",(sbfCaption.substring(0, sbfCaption.indexOf("[", 2))).toString());
            		return this.getForward(strNewProductRules, mapping, request);
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

    /**
     * Returns the ProductPolicyManager session object for invoking methods on it.
     * @return ProductPolicyManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
    {
    //	log.info("Inside ProductPolicyManager");
        ProductPolicyManager productPolicyManager = null;
        try
        {
            if(productPolicyManager == null)
            {
                InitialContext ctx = new InitialContext();
                productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
               // log.info("Inside ProductPolicyManager: productPolicyManager: " + productPolicyManager);
            }//end if(productPolicyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "ProductManager");
        }//end of catch
        return productPolicyManager;
    }//end of getProductPolicyManagerObject()

    /**
     * This method returns the forward path of next view based on the Flow
     *
     * @param strActiveSubLink String current sublink
     * @return strRulePath String forward path for the next view
     */
    private String getRulePath(String strActiveSubLink)
    {
    //	log.info("Inside getRulePath");
        String strRulePath="";
        if(strActiveSubLink.equals("Products"))
        {
            strRulePath=strProductrule;
        }//end of if(strActiveSubLink.equals("Products"))
        else if(strActiveSubLink.equals("Policies"))
        {
            strRulePath=strPolicyRule;
        }//end of else if(strActiveSubLink.equals("Policies"))
        return strRulePath;
    }//end of getRulePath(String strActiveSubLink)
 
    /**
     * This method is prepares the Caption based on the flow and retunrs it
     * @param strActiveSubLink current Active sublink
     * @param request current HttpRequest
     * @return String caption built
     * @throws TTKException
     */
    private String buildCaption(String strActiveSubLink,HttpServletRequest request)throws TTKException
    {
    //	log.info("Inside buildCaption");
        StringBuffer sbfCaption=new StringBuffer();
        //String strMode=TTKCommon.checkNull(request.getParameter("mode"));
        ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
        if(strActiveSubLink.equals("Products"))
        {
            ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
            sbfCaption.append("[").append(productVO.getCompanyName()).append("]");
            sbfCaption.append("[").append(productVO.getProductName()).append("]");
        }//end of if(strActiveSubLink.equals("Products"))
        else if(strActiveSubLink.equals("Policies"))
        {
            PolicyDetailVO policyDetailVO= productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId(request),
                    TTKCommon.getUserSeqId(request));
            sbfCaption.append("[").append(policyDetailVO.getCompanyName()).append("]");
            sbfCaption.append("[").append(policyDetailVO.getPolicyNbr()).append("]");
        }//end of else if(strActiveSubLink.equals("Policies"))
        return sbfCaption.toString();
    }//end of buildCaption(String strActiveSubLink,HttpServletRequest request)

    private StringWriter getProviderCopayRulesMarshaller(HttpServletRequest request)throws Exception{
    	
    //	log.info("inside getProviderCopayRulesMarshaller");
    	
    	StringWriter stringWriter=null;
    	
    	HashMap<String,ArrayList<Condition>>  proRulesXmlConditions=(HashMap<String,ArrayList<Condition>>)request.getSession().getAttribute("MOHProviderRulesXmlConditions");
    	ArrayList<Benefit> allBenefits=new ArrayList<>();
    	Benefit benefit=new Benefit();
       String strBenefType=request.getParameter("benefitID");
       benefit.setBenefitType(strBenefType);
    	if(proRulesXmlConditions!=null&&proRulesXmlConditions.size()>0){
    	
    	
    		ArrayList<Condition> configuredAllConditions =proRulesXmlConditions.get(strBenefType);
    		
    		if(configuredAllConditions!=null&&configuredAllConditions.size()>0){
    		
    		if("OPTS".equals(strBenefType)&&"3".equals(request.getParameter("SBS2"))){
    		benefit.setConditions(configuredAllConditions);
    		allBenefits.add(benefit);
    		stringWriter =benefitMarshaller(allBenefits);
    		}else if("IPT".equals(strBenefType)&&"3".equals(request.getParameter("SBS1-1"))){
        		benefit.setConditions(configuredAllConditions);
        		allBenefits.add(benefit);
        		stringWriter =benefitMarshaller(allBenefits);
        			} else if("MTI".equals(strBenefType)&&"3".equals(request.getParameter("SBS1-1"))){
            		benefit.setConditions(configuredAllConditions);
            		allBenefits.add(benefit);
            		stringWriter =benefitMarshaller(allBenefits);
            		}
    		
    		else if("OPTC".equals(strBenefType)&&"3".equals(request.getParameter("SBS2"))){
        		benefit.setConditions(configuredAllConditions);
        		allBenefits.add(benefit);
        		stringWriter =benefitMarshaller(allBenefits);
        		}
    		else if("DNTL".equals(strBenefType)&&"3".equals(request.getParameter("SBS2"))){
        		benefit.setConditions(configuredAllConditions);
        		allBenefits.add(benefit);
        		stringWriter =benefitMarshaller(allBenefits);
        		}
	
    		}//if(configuredAllConditions!=null&&configuredAllConditions.size()>0){
    		}//if(proRulesXmlConditions!=null&&proRulesXmlConditions.size()>0){
    return stringWriter;
    }
    
private StringWriter benefitMarshaller(ArrayList<Benefit> allBenefits)throws Exception{
	log.debug("Inside benefitMarshaller");
	StringWriter stringWriter=new StringWriter();
	JAXBContext jaxbContext = JAXBContext.newInstance(ProviderRules.class); 
	Marshaller marshallerObj = jaxbContext.createMarshaller(); 
	marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);	
	ProviderRules providerRules=new ProviderRules();
	providerRules.setBenefits(allBenefits);

	//ruleDocument
	//File filePath=new File(TTKPropertiesReader.getPropertyValue("ProviderCopayRules"));
	 marshallerObj.marshal(providerRules,stringWriter);
	 
	 return stringWriter;
}
    private HashMap<String,ArrayList<Condition>> getProviderCopayRulesUnmarshaller(Reader reader)throws Exception{
    	
    	log.debug("inside getProviderCopayRulesUnmarshaller");
    	HashMap<String,ArrayList<Condition>>    proRulesXmlConditions=null;
    if(reader!=null){

    	JAXBContext jaxbContext = JAXBContext.newInstance(ProviderRules.class); 
    	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
        
    	ProviderRules providerRules=(ProviderRules)jaxbUnmarshaller.unmarshal(reader);
    	
    	if(providerRules!=null){
    		
    		ArrayList<Benefit> allBenefits=providerRules.getBenefits();
    		proRulesXmlConditions=new HashMap<>();
    		if(allBenefits!=null){
    	
    			for(Benefit benefit:allBenefits){
    			if(benefit!=null){
    				proRulesXmlConditions.put(benefit.getBenefitType(), benefit.getConditions());
    			}//if(benefit!=null){
    		}//for(Benefit benefit:allBenefits){
    		}//if(allBenefits!=null){
    	}//if(providerRules!=null){
    	 
    }//if(reader!=null){
    return proRulesXmlConditions;
    }
    
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
                log.debug("MOHRuleAction Action - inside doChangeWebBoard method....");
                return mapping.findForward("DefaultRuleAction");//doDefault(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
}//end of RuleAction.java