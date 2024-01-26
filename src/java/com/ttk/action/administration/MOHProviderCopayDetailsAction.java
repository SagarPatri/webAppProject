/**
* @ (#) BufferAction.java Jun 19, 2006
* Project 		: TTK HealthCare Services
* File			: BufferAction.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created  : Jun 19, 2006
*
* @author 		: Pradeep R
* Modified by 	:
* Modified date :
* Reason :
*/
package com.ttk.action.administration;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

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
import com.ttk.dto.administration.ProviderRuleVO;
import com.ttk.xml.administration.Condition;
import com.ttk.xml.administration.Countries;
import com.ttk.xml.administration.CountryCopayDetails;
import com.ttk.xml.administration.EmirateCopayDetails;
import com.ttk.xml.administration.Emirates;
import com.ttk.xml.administration.GeoLocationCopayDetails;
import com.ttk.xml.administration.ProviderCopayDetails;
import com.ttk.xml.administration.ProviderSeqIDList;
import com.ttk.xml.administration.Providers;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of List of Buffer Amount.
 * This class also provides option for update the buffer details.
 */
public class MOHProviderCopayDetailsAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( MOHProviderCopayDetailsAction.class );
	
	//Forwards
	private static final String strBufferList="bufferlist";
	
	//Exception Message Identifier
	private static final String strProductExp="product";
	BigDecimal NorCorpus=new BigDecimal(0);
	BigDecimal NorMed=new BigDecimal(0);
	BigDecimal CriCorpus=new BigDecimal(0);
	BigDecimal CriMed=new BigDecimal(0);
	BigDecimal CriIll=new BigDecimal(0);

	
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
	public ActionForward  providerCopayDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					//String strActiveSubLink=TTKCommon.getActiveSubLink(request);
				
					DynaActionForm frmMOHProviderCopayRules = (DynaActionForm)form;					
					
					// Object[] objArry=productpolicyObject.getPreApprovedLimit(lngProdPolicySeqId);
					
					 frmMOHProviderCopayRules.initialize(mapping);
					 
					 frmMOHProviderCopayRules.set("caption","Configure Co-pay");
					 frmMOHProviderCopayRules.set("benefitTypeDesc",request.getParameter("benefitName"));
					 frmMOHProviderCopayRules.set("benefitType", request.getParameter("benefitType"));
					request.getSession().setAttribute("frmMOHProviderCopayRules",frmMOHProviderCopayRules);
				
					return mapping.findForward("mohProviderCopayRules");
					
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
	public ActionForward  doSelectProviderFacilities(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					
					//ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
					  
					  request.setAttribute("dataList", Cache.getCacheObject("providerType"));
					  
					return mapping.findForward("ShowDynamicList");
					
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
	public ActionForward  doSelectEncounters(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
					
					//ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
					  request.setAttribute("dataList", getRuleManagerObject().getEncounterTypes(request.getParameter("benefitType")));
					return mapping.findForward("ShowDynamicList");
					
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
												   HttpServletResponse response) throws Exception{
		try{
			log.info("Inside the doReset method of BufferAction");
			setLinks(request);
			
			 DynaActionForm frmMOHProviderCopayRules = (DynaActionForm)form;
			 String strBenefitType=frmMOHProviderCopayRules.getString("benefitType");
			 String strBenefitTypeDesc=frmMOHProviderCopayRules.getString("benefitTypeDesc");
			 
	         frmMOHProviderCopayRules.initialize(mapping);
	         frmMOHProviderCopayRules.set("caption","Configure Co-pay");
	         frmMOHProviderCopayRules.set("benefitType", strBenefitType);
	         frmMOHProviderCopayRules.set("benefitTypeDesc", strBenefitTypeDesc);
	        
	        request.getSession().setAttribute("frmMOHProviderCopayRules",frmMOHProviderCopayRules);
		  return mapping.findForward("mohProviderCopayRules");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	
	public ActionForward doChangeClaimType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			
			DynaActionForm frmMOHProviderCopayRules = (DynaActionForm)form;	
			String claimType=frmMOHProviderCopayRules.getString("claimType");
			
			if("CNH".equals(claimType))frmMOHProviderCopayRules.set("networkYN", "Y");
			else frmMOHProviderCopayRules.set("networkYN", "");
			
			 
			request.getSession().setAttribute("frmMOHProviderCopayRules",frmMOHProviderCopayRules);
			
			return mapping.findForward("mohProviderCopayRules");
			
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
	
	public ActionForward doSelectLocations(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			
			DynaActionForm frmMOHProviderCopayRules = (DynaActionForm)form;
			HttpSession  session=request.getSession();
			
			String strLocationType=request.getParameter("locationType");
			
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			
			if("GEO".equals(strLocationType)){
				
				session.setAttribute("GeographicLocationsList", productpolicyObject.getGeographicalLocationts("MOH"));
			
			}else 	if("CON".equals(strLocationType)){
				session.setAttribute("CountryLocationsList", productpolicyObject.getGeoCountries(frmMOHProviderCopayRules.getString("geoLocation")));
			
			}else 	if("EMR".equals(strLocationType)){
				session.setAttribute("EmirateLocationsList", productpolicyObject.getEmirates("175"));
			
			}else  if("PFT".equals(strLocationType)){
				
				session.setAttribute("ProviderFecilityList", Cache.getCacheObject("providerType"));
			
			}else if("PRO".equals(strLocationType)){
				
				 String strActiveSubLink=TTKCommon.getActiveSubLink(request);
				 String linkMode="";
				 Long   seqID=0l;
				 seqID=TTKCommon.getWebBoardId(request);
				 if("Products".equals(strActiveSubLink)){
					 linkMode="PRO";					
				 }else if("Policies".equals(strActiveSubLink)){
					 linkMode="POL";
				 }
				 
				
				ProviderRuleVO providerRuleVO = (ProviderRuleVO) FormUtils.getFormValues(frmMOHProviderCopayRules, this, mapping, request);
				// String strproSeqID=providerRuleVO.getProviderSeqIDs();
		        //  if(strproSeqID!=null&&strproSeqID.length()>1)strproSeqID=strproSeqID.replaceAll("[|]", ",");
				// String strproSeqID=providerRuleVO.getProviderSeqIDs();
		        providerRuleVO.setProviderSeqIDs("");
		        providerRuleVO.setProviderLicenseNO("");
		        providerRuleVO.setProviderName("");
		        providerRuleVO.setLinkMode(linkMode);
				providerRuleVO.setSeqID(seqID);
				
				session.setAttribute("configProviderRuleVO",providerRuleVO);
				
				session.setAttribute("ProviderLocationDetailsList", productpolicyObject.getMOHProviderList(providerRuleVO));
			
				
			}
			
			session.setAttribute("frmMOHProviderCopayRules",frmMOHProviderCopayRules);
			
			return mapping.findForward("mohlocationlistwithcopay");
			
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
	
	public ActionForward doSearchProviders(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			
			HttpSession session=request.getSession();
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			
			ProviderRuleVO providerRuleVO=(ProviderRuleVO)session.getAttribute("configProviderRuleVO");
			
			providerRuleVO.setProviderLicenseNO(TTKCommon.checkNull(request.getParameter("providerLicenseNO")));
			providerRuleVO.setProviderName(TTKCommon.checkNull(request.getParameter("providerName")));
			
			request.setAttribute("providerSearch","yes");
			
			session.setAttribute("ProviderLocationDetailsList", productpolicyObject.getMOHProviderList(providerRuleVO));
		    return mapping.findForward("mohlocationlistwithcopay");		
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
	
	public ActionForward doSaveLocationCopay(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			
			DynaActionForm frmMOHProviderCopayRules = (DynaActionForm)form;
      HttpSession session=request.getSession();
      
      if("GEO".equals(frmMOHProviderCopayRules.getString("locationType"))){
    	  
    	  GeoLocationCopayDetails xmlGeoLocationCopayDetails=new GeoLocationCopayDetails();
    	  
       ArrayList<GeoLocationCopayDetails> geoList=(ArrayList<GeoLocationCopayDetails>)session.getAttribute("GeographicLocationsList");
       
       String strCheckedIndexes=request.getParameter("checkedIndex");
       
       frmMOHProviderCopayRules.set("countryIDs","");
       frmMOHProviderCopayRules.set("XmlConrtiesCopayDetails", null);       
       frmMOHProviderCopayRules.set("providerFacilities", "");    
       frmMOHProviderCopayRules.set("providerSeqIDs", ""); 
       frmMOHProviderCopayRules.set("XmlProviderCopayDetails",null);
       frmMOHProviderCopayRules.set("XmlSeqSplitList",null);
       if(geoList!=null&&strCheckedIndexes!=null){
    	   
    	   frmMOHProviderCopayRules.set("geoLocation", geoList.get(Integer.parseInt(strCheckedIndexes)).getLocationCode());
    	   frmMOHProviderCopayRules.set("geoLocationName", geoList.get(Integer.parseInt(strCheckedIndexes)).getLocationName());
    	   
    	   xmlGeoLocationCopayDetails.setLocationCode(geoList.get(Integer.parseInt(strCheckedIndexes)).getLocationCode());
    	   xmlGeoLocationCopayDetails.setLocationName(geoList.get(Integer.parseInt(strCheckedIndexes)).getLocationName());
    	   
    	   xmlGeoLocationCopayDetails.setCopay(request.getParameter("copay"+strCheckedIndexes));
    	   xmlGeoLocationCopayDetails.setCurrency(request.getParameter("currency"+strCheckedIndexes));
    	   xmlGeoLocationCopayDetails.setFlatamount(request.getParameter("flatamount"+strCheckedIndexes));
    	   xmlGeoLocationCopayDetails.setMinmax(request.getParameter("minmax"+strCheckedIndexes));
    	   xmlGeoLocationCopayDetails.setPerPolicyLimit(request.getParameter("perPolicyLimit"+strCheckedIndexes));
    	   xmlGeoLocationCopayDetails.setPerClaimLimit(request.getParameter("perClaimLimit"+strCheckedIndexes));
    	   xmlGeoLocationCopayDetails.setPerDayLimit(request.getParameter("perDayLimit"+strCheckedIndexes));
    	   xmlGeoLocationCopayDetails.setCoveragePeriod(request.getParameter("coveragePeriod"+strCheckedIndexes));
    	   xmlGeoLocationCopayDetails.setCoveragePeriodType(request.getParameter("coveragePeriodType"+strCheckedIndexes));
    	  // xmlGeoLocationCopayDetails.setApplyOn(request.getParameter("applyOn"+strCheckedIndexes));
    	   frmMOHProviderCopayRules.set("XmlGeoLocationCopayDetails",xmlGeoLocationCopayDetails);
       }
		
      }else if("PFT".equals(frmMOHProviderCopayRules.getString("locationType"))){
    	  
    	  
    	  String strCheckedIndexes[]=request.getParameterValues("checkedIndex");
          StringBuilder strBPFIDs=new StringBuilder();
        
          if(strCheckedIndexes!=null){
        	  for(String strIndex:strCheckedIndexes){
        		  
       		strBPFIDs.append(strIndex);
       		strBPFIDs.append("|");
          }//for(String strIndex:strCheckedIndexes){
          }//  if(strCheckedIndexes!=null){
         
          frmMOHProviderCopayRules.set("providerSeqIDs", "");       
          frmMOHProviderCopayRules.set("XmlProviderCopayDetails",null);
          frmMOHProviderCopayRules.set("XmlSeqSplitList",null);
          String strPFTIDs=strBPFIDs.toString();
          if(strPFTIDs.length()>1)strPFTIDs=strPFTIDs.substring(0, strPFTIDs.length()-1);
         
          frmMOHProviderCopayRules.set("providerFacilities", strPFTIDs);
        	  
          }//
      else if("EMR".equals(frmMOHProviderCopayRules.getString("locationType"))){

    	     	  
       ArrayList<EmirateCopayDetails> emirateList=(ArrayList<EmirateCopayDetails>)request.getSession().getAttribute("EmirateLocationsList");
      
       EmirateCopayDetails emirateLocationCopayDetails=null;
       HashMap<String, EmirateCopayDetails> xmlEmirateLocationCopayDetails=new HashMap<>();
       
       
       String strCheckedIndexes[]=request.getParameterValues("checkedIndex");
       StringBuilder strBEmirateIDs=new StringBuilder();
     
       if(emirateList!=null&&strCheckedIndexes!=null){
    	   
    	   for(String strIndex:strCheckedIndexes){
    		  
    		   emirateLocationCopayDetails=emirateList.get(Integer.parseInt(strIndex));
    		
    		   strBEmirateIDs.append(emirateLocationCopayDetails.getEmirateCode());
    		   strBEmirateIDs.append("|");
    	  
  		 emirateLocationCopayDetails.setCopay(request.getParameter("copay"+strIndex));
  		 emirateLocationCopayDetails.setCurrency(request.getParameter("currency"+strIndex));
  		 emirateLocationCopayDetails.setFlatamount(request.getParameter("flatamount"+strIndex));
  		 emirateLocationCopayDetails.setMinmax(request.getParameter("minmax"+strIndex));
  		 emirateLocationCopayDetails.setPerPolicyLimit(request.getParameter("perPolicyLimit"+strIndex));
  		 emirateLocationCopayDetails.setPerClaimLimit(request.getParameter("perClaimLimit"+strIndex));
  		 emirateLocationCopayDetails.setPerDayLimit(request.getParameter("perDayLimit"+strIndex));
  		 emirateLocationCopayDetails.setCoveragePeriod(request.getParameter("coveragePeriod"+strIndex));
  		 emirateLocationCopayDetails.setCoveragePeriodType(request.getParameter("coveragePeriodType"+strIndex));
    	 //  geoLocationCopayDetails.setApplyOn(request.getParameter("applyOn"+strIndex));
    	  
  		xmlEmirateLocationCopayDetails.put(emirateLocationCopayDetails.getEmirateCode(), emirateLocationCopayDetails);
    	   
    	   }//for(String strIndex:strLocationcodeIndexes){    	   
    	   
       }// if(geoCountryList!=null&&strLocationcodeIndexes!=null){
     
       String strEmiratesIDs=strBEmirateIDs.toString();
       if(strEmiratesIDs.length()>1)strEmiratesIDs=strEmiratesIDs.substring(0, strEmiratesIDs.length()-1);
      
       
       frmMOHProviderCopayRules.set("providerSeqIDs", "");       
       frmMOHProviderCopayRules.set("XmlProviderCopayDetails",null);
       frmMOHProviderCopayRules.set("XmlSeqSplitList",null);
       frmMOHProviderCopayRules.set("providerFacilities", ""); 
       
      frmMOHProviderCopayRules.set("emirateIDs", strEmiratesIDs);
       
       frmMOHProviderCopayRules.set("XmlEmiratesCopayDetails",xmlEmirateLocationCopayDetails);
    	  
      }
      else if("CON".equals(frmMOHProviderCopayRules.getString("locationType"))){
     	  
          ArrayList<CountryCopayDetails> geoCountryList=(ArrayList<CountryCopayDetails>)request.getSession().getAttribute("CountryLocationsList");
         
          CountryCopayDetails geoLocationCopayDetails=null;
          HashMap<String, CountryCopayDetails> xmlCountryLocationCopayDetails=new HashMap<>();
          
          
          String strCheckedIndexes[]=request.getParameterValues("checkedIndex");
          StringBuilder strBCountryIDs=new StringBuilder();
        
          if(geoCountryList!=null&&strCheckedIndexes!=null){
       	   
       	   for(String strIndex:strCheckedIndexes){
       		  
       		   geoLocationCopayDetails=geoCountryList.get(Integer.parseInt(strIndex));
       		
       		 strBCountryIDs.append(geoLocationCopayDetails.getCountryCode());
     		   strBCountryIDs.append("|");
       	  
       	   geoLocationCopayDetails.setCopay(request.getParameter("copay"+strIndex));
       	   geoLocationCopayDetails.setCurrency(request.getParameter("currency"+strIndex));
       	   geoLocationCopayDetails.setFlatamount(request.getParameter("flatamount"+strIndex));
       	   geoLocationCopayDetails.setMinmax(request.getParameter("minmax"+strIndex));
       	   geoLocationCopayDetails.setPerPolicyLimit(request.getParameter("perPolicyLimit"+strIndex));
       	   geoLocationCopayDetails.setPerClaimLimit(request.getParameter("perClaimLimit"+strIndex));
       	   geoLocationCopayDetails.setPerDayLimit(request.getParameter("perDayLimit"+strIndex));
       	   geoLocationCopayDetails.setCoveragePeriod(request.getParameter("coveragePeriod"+strIndex));
       	   geoLocationCopayDetails.setCoveragePeriodType(request.getParameter("coveragePeriodType"+strIndex));
       	 //  geoLocationCopayDetails.setApplyOn(request.getParameter("applyOn"+strIndex));
       	  
       	   xmlCountryLocationCopayDetails.put(geoLocationCopayDetails.getCountryCode(), geoLocationCopayDetails);
       	   
       	   }//for(String strIndex:strLocationcodeIndexes){    	   
       	   
          }// if(geoCountryList!=null&&strLocationcodeIndexes!=null){
        
          String strCountriesIDs=strBCountryIDs.toString();
          if(strCountriesIDs.length()>1)strCountriesIDs=strCountriesIDs.substring(0, strCountriesIDs.length()-1);
         
          
          frmMOHProviderCopayRules.set("providerSeqIDs", "");       
          frmMOHProviderCopayRules.set("XmlProviderCopayDetails",null);
          frmMOHProviderCopayRules.set("emirateIDs", "");       
          frmMOHProviderCopayRules.set("XmlEmiratesCopayDetails",null);
          frmMOHProviderCopayRules.set("XmlSeqSplitList",null);
          frmMOHProviderCopayRules.set("providerFacilities", ""); 
          
         frmMOHProviderCopayRules.set("countryIDs", strCountriesIDs);
          
          frmMOHProviderCopayRules.set("XmlConrtiesCopayDetails",xmlCountryLocationCopayDetails);   	  
         
    } else if("PRO".equals(frmMOHProviderCopayRules.getString("locationType"))){
     	  
          HashMap<String, ProviderCopayDetails> hmProviderCopayDetails=new HashMap<>();
            
          StringBuilder strBProSeqIDs=new StringBuilder();
          StringBuilder strBProSeqIDsSplit=new StringBuilder();
          ArrayList<String> seqSplitList=new ArrayList<>();
          String strProviderIndexes[]=request.getParameterValues("checkedIndex");
          ProviderCopayDetails providerCopayDetails=null;
          
          if(strProviderIndexes!=null){
       	   int seqCount=0;
       	   for(String strIndex:strProviderIndexes){
       		
       		seqCount++;
       		
       		providerCopayDetails=new ProviderCopayDetails();
       		
       		String proSeqID=request.getParameter("providerSeqID"+strIndex);
       		
       		providerCopayDetails.setCopay(request.getParameter("copay"+strIndex));
       		providerCopayDetails.setCurrency(request.getParameter("currency"+strIndex));
       		providerCopayDetails.setFlatamount(request.getParameter("flatamount"+strIndex));
       		providerCopayDetails.setMinmax(request.getParameter("minmax"+strIndex));
       		providerCopayDetails.setProviderSeqID(proSeqID);
       		
       		hmProviderCopayDetails.put(proSeqID, providerCopayDetails);
       	   
       		strBProSeqIDs.append(proSeqID);
       		strBProSeqIDs.append("|");
       		
       		strBProSeqIDsSplit.append(proSeqID);
       		strBProSeqIDsSplit.append("|");
       		
       		
       		if(seqCount==999){
       			String strproSeqIDSplit=strBProSeqIDsSplit.toString();
                if(strproSeqIDSplit.length()>1)strproSeqIDSplit=strproSeqIDSplit.substring(0, strproSeqIDSplit.length()-1);
       			
                seqSplitList.add(strproSeqIDSplit);
       			seqCount=0;
       			strBProSeqIDsSplit=new StringBuilder();
       		}
       	   }//for(String strIndex:strLocationcodeIndexes){    	   
       	   
          }// if(geoCountryList!=null&&strLocationcodeIndexes!=null){
              
          String strproSeqID=strBProSeqIDs.toString();
          if(strproSeqID.length()>1)strproSeqID=strproSeqID.substring(0, strproSeqID.length()-1);
         
          String strproSeqIDSplit=strBProSeqIDsSplit.toString();
          if(strproSeqIDSplit.length()>1){
        	  strproSeqIDSplit=strproSeqIDSplit.substring(0, strproSeqIDSplit.length()-1);
        	  seqSplitList.add(strproSeqIDSplit);
          }
          
      
          //Nag
          frmMOHProviderCopayRules.set("providerSeqIDs", strproSeqID);
          
          frmMOHProviderCopayRules.set("XmlSeqSplitList", seqSplitList);
          frmMOHProviderCopayRules.set("XmlProviderCopayDetails", hmProviderCopayDetails);
          
         }

      request.getSession().setAttribute("frmMOHProviderCopayRules",frmMOHProviderCopayRules);
	  return mapping.findForward("mohProviderCopayRules");
			
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
	

	public ActionForward doAddHospCoayDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			//DynaActionForm frmMOHProviderCopayRules = (DynaActionForm)form;
           HttpSession session=request.getSession();
           DynaActionForm frmMOHProviderCopayRules=(DynaActionForm)session.getAttribute("frmMOHProviderCopayRules");
      HashMap<String, ProviderCopayDetails> hmAddedProviderCopayDetailsList=new HashMap<>();
          
          StringBuilder strBProSeqIDs=new StringBuilder();
          
          String strProviderIndexes[]=request.getParameterValues("checkedIndex");
          ProviderCopayDetails providerCopayDetails=null;
          
          if(strProviderIndexes!=null){
       	   
       	   for(String strIndex:strProviderIndexes){
       		   
       		providerCopayDetails=new ProviderCopayDetails();
       		
       		String proSeqID=request.getParameter("providerSeqID"+strIndex);
       		
       		providerCopayDetails.setCopay(request.getParameter("copay"+strIndex));
       		providerCopayDetails.setCurrency(request.getParameter("currency"+strIndex));
       		providerCopayDetails.setFlatamount(request.getParameter("flatamount"+strIndex));
       		providerCopayDetails.setMinmax(request.getParameter("minmax"+strIndex));
       		providerCopayDetails.setProviderSeqID(proSeqID);
       		
       		hmAddedProviderCopayDetailsList.put(proSeqID, providerCopayDetails);
       	   
       		strBProSeqIDs.append(proSeqID);
       		strBProSeqIDs.append("|");
       		
       	   }//for(String strIndex:strLocationcodeIndexes){    	   
       	   
          }// if(geoCountryList!=null&&strLocationcodeIndexes!=null){
         // String strproSeqID=strBProSeqIDs.toString();
        //  if(strproSeqID.length()>1)strproSeqID=strproSeqID.substring(0, strproSeqID.length()-1);
         
			String strProviderSeqIDs= frmMOHProviderCopayRules.getString("providerSeqIDs");	         
			HashMap<String, ProviderCopayDetails> hmConfigProviderCopayDetailsList=(HashMap<String, ProviderCopayDetails>)frmMOHProviderCopayRules.get("XmlProviderCopayDetails");
			ArrayList<String>   seqSplitList =(ArrayList<String>)frmMOHProviderCopayRules.get("XmlSeqSplitList");
			
		if(strProviderSeqIDs==null){
        	strProviderSeqIDs="";
        }
        if(hmConfigProviderCopayDetailsList==null){
        	hmConfigProviderCopayDetailsList=new HashMap<>();
        }
        if(seqSplitList==null||seqSplitList.size()==0){
        	seqSplitList=new ArrayList<>();
        	seqSplitList.add(strBProSeqIDs.toString());
        	
        }else{
        	String seqId=seqSplitList.get(seqSplitList.size()-1);        	
           String[]seqArr=seqId.split("[|]");        		
          if(seqArr.length>999){
        	  String strProSeqIDs=strBProSeqIDs.toString();
              if(strProSeqIDs.length()>1)strProSeqIDs=strProSeqIDs.substring(0, strProSeqIDs.length()-1);
             
        				seqSplitList.add(strProSeqIDs);
        			
           }//if(seqArr.length>9){
          else{
        	  strBProSeqIDs.append(seqId);
          	seqSplitList.add(strBProSeqIDs.toString());
          }
        	
        }
        
        strBProSeqIDs.append(strProviderSeqIDs);
        frmMOHProviderCopayRules.set("providerSeqIDs", strBProSeqIDs.toString());
        hmConfigProviderCopayDetailsList.putAll(hmAddedProviderCopayDetailsList);
        frmMOHProviderCopayRules.set("XmlProviderCopayDetails",hmConfigProviderCopayDetailsList);
        frmMOHProviderCopayRules.set("XmlSeqSplitList",seqSplitList);
        session.setAttribute("frmMOHProviderCopayRules",frmMOHProviderCopayRules);
        
	   request.setAttribute("addedMsg", "Hospital Copay Details Added Successfully");
      
	   //Re loading all provider data
	   
	   ProviderRuleVO providerRuleVO=(ProviderRuleVO)session.getAttribute("configProviderRuleVO");
	   
	   providerRuleVO.setProviderSeqIDs("");
       providerRuleVO.setProviderLicenseNO("");
       providerRuleVO.setProviderName("");
	  
	   session.setAttribute("ProviderLocationDetailsList", productpolicyObject.getMOHProviderList(providerRuleVO));
	   
       return mapping.findForward("mohlocationlistwithcopay");
			
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
	
	public ActionForward doAddCondition(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{

		try
		{
			setLinks(request);
			HashMap<String, ArrayList<Condition>> benefitConditions;
			 
			DynaActionForm frmMOHProviderCopayRules = (DynaActionForm)form;
			
			benefitConditions=(HashMap<String, ArrayList<Condition>>)request.getSession().getAttribute("MOHProviderRulesXmlConditions");
			
          if(benefitConditions==null) benefitConditions=new HashMap<>();
          
          String strRownum=frmMOHProviderCopayRules.getString("rownum");
        
          ProviderRuleVO providerRuleVO = (ProviderRuleVO) FormUtils.getFormValues(frmMOHProviderCopayRules, this, mapping, request);
         
          String strProSeqIDs=providerRuleVO.getProviderSeqIDs();
          String strBenefitType=providerRuleVO.getBenefitType();
          
          
          Providers providers=new Providers();
          
          ProviderSeqIDList providerSeqIDList=new ProviderSeqIDList();
          
          providers.setProviderSeqIDs(strProSeqIDs);
          
          if(strProSeqIDs!=null&&strProSeqIDs.length()>0){
        	  
        	  HashMap<String, ProviderCopayDetails> xmlProviderCopayDetails=(HashMap<String, ProviderCopayDetails>)frmMOHProviderCopayRules.get("XmlProviderCopayDetails");
        	  ArrayList<String>   seqSplitList =(ArrayList<String>)frmMOHProviderCopayRules.get("XmlSeqSplitList");
        	  if(xmlProviderCopayDetails!=null&&xmlProviderCopayDetails.size()>0){
        		  providers.setProviderConfStatus("Y");
        		  providers.setProvidersCopayDetails(new ArrayList<ProviderCopayDetails>(xmlProviderCopayDetails.values()));
        		  providerSeqIDList.setProviderSeqIDs(seqSplitList);
        		  providers.setProviderSeqIDList(providerSeqIDList);
        	  }
          
          }else{
        	  providers.setProviderSeqIDs("");
        	  providers.setProviderConfStatus("N");  
          }
        //Adding Emirate Co-pay Details 
          HashMap<String, EmirateCopayDetails> xmlEmiratesCopayDetails=(HashMap<String, EmirateCopayDetails>)frmMOHProviderCopayRules.get("XmlEmiratesCopayDetails");
          Emirates emirates=new Emirates();
          emirates.setConfStatus("N");
          if(xmlEmiratesCopayDetails!=null&&xmlEmiratesCopayDetails.size()>0){
        	  emirates.setConfStatus("Y");
        	  emirates.setEmirateIDs(frmMOHProviderCopayRules.getString("emirateIDs"));
        	  emirates.setEmirateCopayDetails(new ArrayList<EmirateCopayDetails>(xmlEmiratesCopayDetails.values()));
        	 
          }
          
          //Adding Country Co-pay Details 
          HashMap<String, CountryCopayDetails> xmlConrtiesCopayDetails=(HashMap<String, CountryCopayDetails>)frmMOHProviderCopayRules.get("XmlConrtiesCopayDetails");
          
          Countries countries=new Countries();
          countries.setCountryIDs(frmMOHProviderCopayRules.getString("countryIDs"));
         
          if(xmlConrtiesCopayDetails!=null){
        	  countries.setCountriesCopayDetails(new ArrayList<CountryCopayDetails>(xmlConrtiesCopayDetails.values()));
          }
          
          //Adding Geo Location Co-pay Details
          GeoLocationCopayDetails geoLocationCopayDetails=(GeoLocationCopayDetails)frmMOHProviderCopayRules.get("XmlGeoLocationCopayDetails");
          
          Condition condition=new Condition();
          
          condition.setBenefitType(strBenefitType);
          condition.setBenefitTypeDesc(providerRuleVO.getBenefitTypeDesc());
          condition.setClaimType(providerRuleVO.getClaimType());
          condition.setClaimTypeDesc(providerRuleVO.getClaimTypeDesc());
          condition.setNetworkYN(providerRuleVO.getNetworkYN());
          condition.setNetworkYNDesc(providerRuleVO.getNetworkYNDesc());
          condition.setProviderType(providerRuleVO.getProviderType());
          condition.setProviderTypeDesc(providerRuleVO.getProviderTypeDesc());
          condition.setProviderFacilities(providerRuleVO.getProviderFacilities());
          condition.setEncounterTypes(providerRuleVO.getEncounterTypes());
          condition.setGeoLocCopayDetails(geoLocationCopayDetails);
          
          condition.setEmirates(emirates);
          
          condition.setCountries(countries);
          
          condition.setProviders(providers);
          
         
          ArrayList<Condition> alConditions= benefitConditions.get(strBenefitType);
           if(alConditions==null)alConditions=new ArrayList<>();
           
           if(strRownum!=null&&strRownum.length()>0){
        	   alConditions.set(Integer.parseInt(strRownum), condition);
        	   request.setAttribute("successMsg","Condition Updated Successfully");
           }else{
        	   alConditions.add(condition);
        	   request.setAttribute("successMsg","Condition Added Successfully");
           }
           
           benefitConditions.put(strBenefitType, alConditions);
           
         request.getSession().setAttribute("MOHProviderRulesXmlConditions",benefitConditions);
         
         frmMOHProviderCopayRules.initialize(mapping);
         frmMOHProviderCopayRules.set("caption","Configure Co-pay");
         frmMOHProviderCopayRules.set("benefitType", strBenefitType);
         frmMOHProviderCopayRules.set("benefitTypeDesc", providerRuleVO.getBenefitTypeDesc());
         
         
        request.getSession().setAttribute("frmMOHProviderCopayRules",frmMOHProviderCopayRules);
	  return mapping.findForward("mohProviderCopayRules");
			
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
												   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			
			return mapping.findForward("mohProviderCopayRules");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	

	

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
	public ActionForward doEditCondition(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			HashMap<String, ArrayList<Condition>> benefitConditions;
			 
			DynaActionForm frmMOHProviderCopayRules = (DynaActionForm)form;
			String strBenefit=frmMOHProviderCopayRules.getString("benefitType");
			String strBenefitDesc=frmMOHProviderCopayRules.getString("benefitTypeDesc");			
			String strRownum=frmMOHProviderCopayRules.getString("rownum");
			
			frmMOHProviderCopayRules.initialize(mapping);

			benefitConditions=(HashMap<String, ArrayList<Condition>>)request.getSession().getAttribute("MOHProviderRulesXmlConditions");
			if(strRownum!=null){
			if(benefitConditions!=null){
				
				 ArrayList<Condition> alConditions=benefitConditions.get(strBenefit);
				 if(alConditions!=null){
					 
					 Condition condition=alConditions.get(Integer.parseInt(strRownum));
					 
					 if(condition!=null){
						 
						 //Getting geo location copay details and setting into form
						 GeoLocationCopayDetails xmlGeoLocationCopayDetails=condition.getGeoLocCopayDetails();
						
						 frmMOHProviderCopayRules.set("geoLocation", xmlGeoLocationCopayDetails.getLocationCode());
				    	 frmMOHProviderCopayRules.set("geoLocationName", xmlGeoLocationCopayDetails.getLocationName());
				    
						 frmMOHProviderCopayRules.set("XmlGeoLocationCopayDetails",xmlGeoLocationCopayDetails);
						 
						 //Getting country location copay details and setting into form
						 Countries countries=condition.getCountries();
						 frmMOHProviderCopayRules.set("countryIDs", countries.getCountryIDs());						 

						 HashMap<String, CountryCopayDetails> xmlCountryLocationCopayDetails=new HashMap<>();
						 
						 ArrayList<CountryCopayDetails> countryLocationCopayDetails=countries.getCountriesCopayDetails();
						 
						 for(CountryCopayDetails countryCopayDetails:countryLocationCopayDetails){
							 xmlCountryLocationCopayDetails.put(countryCopayDetails.getCountryCode(), countryCopayDetails);
						 }
						
						 frmMOHProviderCopayRules.set("XmlConrtiesCopayDetails",xmlCountryLocationCopayDetails);
					 
						Emirates emirates= condition.getEmirates();
						if(emirates!=null&&"Y".equals(emirates.getConfStatus())){
							 frmMOHProviderCopayRules.set("emirateIDs",emirates.getEmirateIDs());
							 HashMap<String, EmirateCopayDetails> xmlEmirateLocationCopayDetails=new HashMap<>();
							 ArrayList<EmirateCopayDetails> emirateLocationCopayDetails=emirates.getEmirateCopayDetails();
							 
							 for(EmirateCopayDetails emirateCopayDetails:emirateLocationCopayDetails){
								 xmlEmirateLocationCopayDetails.put(emirateCopayDetails.getEmirateCode(), emirateCopayDetails);
							 }
							 frmMOHProviderCopayRules.set("XmlEmiratesCopayDetails",xmlEmirateLocationCopayDetails);
						}
						 //Getting provider copay details and setting into form
						 HashMap<String, ProviderCopayDetails> hmProviderCopayDetails=new HashMap<>();
						 
						 Providers providers=condition.getProviders();
						 ProviderSeqIDList providerSeqIDList=providers.getProviderSeqIDList();
                         if(providerSeqIDList!=null){
                        	frmMOHProviderCopayRules.set("XmlSeqSplitList", providerSeqIDList.getProviderSeqIDs());
						 }
						 frmMOHProviderCopayRules.set("providerSeqIDs", providers.getProviderSeqIDs());  
						 
						 ArrayList<ProviderCopayDetails> alProviderCopayDetails=providers.getProvidersCopayDetails();
						 if(alProviderCopayDetails!=null){
						 for(ProviderCopayDetails providerCopayDetails:alProviderCopayDetails){
							 hmProviderCopayDetails.put(providerCopayDetails.getProviderSeqID(), providerCopayDetails);
						 }
						 }
						 
						 frmMOHProviderCopayRules.set("XmlProviderCopayDetails", hmProviderCopayDetails);
						 
						 //general fields setting
						 frmMOHProviderCopayRules.set("caption","Configure Co-pay");
						 frmMOHProviderCopayRules.set("rownum", strRownum);
						 frmMOHProviderCopayRules.set("benefitType", strBenefit);
						 frmMOHProviderCopayRules.set("benefitTypeDesc", strBenefitDesc);
						 frmMOHProviderCopayRules.set("claimType", condition.getClaimType());
						 frmMOHProviderCopayRules.set("networkYN", condition.getNetworkYN());
						 frmMOHProviderCopayRules.set("providerType", condition.getProviderType());
						 frmMOHProviderCopayRules.set("providerFacilities", condition.getProviderFacilities());
						 frmMOHProviderCopayRules.set("encounterTypes", condition.getEncounterTypes());
					 }//if(condition!=null){
						
						
						request.getSession().setAttribute("frmMOHProviderCopayRules",frmMOHProviderCopayRules);
				 }//alConditions
			}//if(benefitConditions!=null){
			}//if(strRownum!=null){
		
			return mapping.findForward("mohProviderCopayRules");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of doDeleteCondition(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	
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
	public ActionForward doDeleteCondition(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			HashMap<String, ArrayList<Condition>> benefitConditions;
			 
			DynaActionForm frmMOHProviderCopayRules = (DynaActionForm)form;
			String strBenefit=frmMOHProviderCopayRules.getString("benefitType");
			String strBenefitDesc=frmMOHProviderCopayRules.getString("benefitTypeDesc");
			
			frmMOHProviderCopayRules.initialize(mapping);
			
			benefitConditions=(HashMap<String, ArrayList<Condition>>)request.getSession().getAttribute("MOHProviderRulesXmlConditions");
			if(request.getParameter("rownum")!=null){
			if(benefitConditions!=null){
				
				 ArrayList<Condition> alConditions=benefitConditions.get(strBenefit);
				 if(alConditions!=null){
					 
					 alConditions.remove(Integer.parseInt(request.getParameter("rownum")));
						request.setAttribute("successMsg","Condition Deleted Successfully");
						
						benefitConditions.put(strBenefit, alConditions);
						
						request.getSession().setAttribute("MOHProviderRulesXmlConditions",benefitConditions);
				 }//alConditions
			}//if(benefitConditions!=null){
			}//if(request.getParameter("rownum")!=null){
			
			frmMOHProviderCopayRules.set("benefitType", strBenefit);
			frmMOHProviderCopayRules.set("benefitTypeDesc", strBenefitDesc);
			request.getSession().setAttribute("frmMOHProviderCopayRules",frmMOHProviderCopayRules);
			return mapping.findForward("mohProviderCopayRules");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProductExp));
		}//end of catch(Exception exp)
	}//end of doDeleteCondition(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	
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
	public ActionForward  doViewConfDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
			{
				try
				{
					setLinks(request);
	               if("PFT".equals(request.getParameter("viewModeType"))){
	            	   request.setAttribute("caption", "Configured Provider Fecility Types");
	               }else  if("GEO".equals(request.getParameter("viewModeType"))){
	            	   request.setAttribute("caption", "Configured Geographical Location With Copay Details");
	               }else  if("CON".equals(request.getParameter("viewModeType"))){
	            	   request.setAttribute("caption", "Configured Countries With Copay Details");
	               } else  if("EMR".equals(request.getParameter("viewModeType"))){
	            	   request.setAttribute("caption", "Configured Emirates With Copay Details");
	               } else  if("PRO".equals(request.getParameter("viewModeType"))){


            		   String strActiveSubLink=TTKCommon.getActiveSubLink(request);
      				 String linkMode="";
      				 Long   seqID=0l;
      				 seqID=TTKCommon.getWebBoardId(request);
      				 if("Products".equals(strActiveSubLink)){
      					 linkMode="PRO";					
      				 }else if("Policies".equals(strActiveSubLink)){
      					 linkMode="POL";
      				 }
	            	   
	            	   
	       			HttpSession session=request.getSession();
	       			HashMap<String, ArrayList<Condition>> benefitConditions;
	       			 
	       			benefitConditions=(HashMap<String, ArrayList<Condition>>)session.getAttribute("MOHProviderRulesXmlConditions");
	       			ProviderRuleVO providerRuleVO=new ProviderRuleVO();
	       			
	       			if(benefitConditions!=null){
	       				DynaActionForm frmMOHProviderCopayRules=(DynaActionForm)session.getAttribute("frmMOHProviderCopayRules");
	       				String strBenefitType=frmMOHProviderCopayRules.getString("benefitType");
	       				ArrayList<Condition> alConditions=benefitConditions.get(strBenefitType);
	       				String strRownum=request.getParameter("rownum");
	       				
	       				if(alConditions!=null){
	       					
	       			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
	       			
	       		
	       			
	            	   Condition condition=alConditions.get(Integer.parseInt(strRownum));
	            	   Providers providers=condition.getProviders();
	            	   String strProSeqIDs=providers.getProviderSeqIDs();
	            	  String strGeoLocCode=condition.getGeoLocCopayDetails().getLocationCode();
	            	  
	            	   if(strProSeqIDs!=null&&strProSeqIDs.length()>0){
	            		  
	            		   providerRuleVO.setGeoLocation(strGeoLocCode);
	            		   providerRuleVO.setCountryIDs(condition.getCountries().getCountryIDs());
	            		   if(condition.getEmirates()!=null){
	            		   providerRuleVO.setEmirateIDs(condition.getEmirates().getEmirateIDs());
	            		   }
	            		   providerRuleVO.setProviderType(condition.getProviderType());
	            		   providerRuleVO.setProviderFacilities(condition.getProviderFacilities());
	            		   
	            		   String proSeqIDs=providers.getProviderSeqIDs();
	            		   if(proSeqIDs!=null)proSeqIDs=proSeqIDs.replaceAll("[|]", ",");
	            		   providerRuleVO.setProviderSeqIDs(proSeqIDs);
	      				 
	      				providerRuleVO.setLinkMode(linkMode);
	      				providerRuleVO.setSeqID(seqID);
	      				
	      				providerRuleVO.setProviderSeqIDList(providers.getProviderSeqIDList().getProviderSeqIDs());
	      				
	            		session.setAttribute("ProviderNameList",productpolicyObject.getProviderNameList(providerRuleVO));
	            	   
	            	   }//if(strProSeqIDs!=null){
	            	   else{
	            		   
	            		  
	            		   ArrayList<CountryCopayDetails> alCopayDetails= condition.getCountries().getCountriesCopayDetails();
	            		  HashMap<String, CountryCopayDetails> hmCountryCopayDetails=new HashMap<>();
	            		  
	            		   for(CountryCopayDetails countryCopayDetails:alCopayDetails){
	            			   hmCountryCopayDetails.put(countryCopayDetails.getCountryCode(), countryCopayDetails);
	            		   }
	            		   
	            		   providerRuleVO.setGeoLocation(condition.getGeoLocCopayDetails().getLocationCode());
	            		   providerRuleVO.setCountryIDs(condition.getCountries().getCountryIDs());
	            		   providerRuleVO.setProviderType(condition.getProviderType());
	            		   providerRuleVO.setProviderFacilities(condition.getProviderFacilities());
	            		   
	            		   String proSeqIDs=condition.getProviders().getProviderSeqIDs();
	            		   if(proSeqIDs!=null)proSeqIDs=proSeqIDs.replaceAll("[|]", ",");
	            		   providerRuleVO.setProviderSeqIDs(proSeqIDs);	            		  
	      				 
	      				providerRuleVO.setLinkMode(linkMode);
	      				providerRuleVO.setSeqID(seqID);
	      				
	            		session.setAttribute("CountryCopayDetails", hmCountryCopayDetails);
	            		   
	            		   session.setAttribute("ProviderLocationDetailsList", productpolicyObject.getMOHProviderList(providerRuleVO));
	            	  
	            	   }
	       				}//alConditions
	       			}//if(benefitConditions!=null){
	            	   request.setAttribute("caption", "Configured Providers With Copay Details");
	              
	               }
	               
					return mapping.findForward("mohProviderConfDetailsView");
					
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
	
	
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return productPolicyManager session object which can be used for method invokation
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
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBufferList);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()
	
	 
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
                log.info("Inside RuleManager: RuleManager: " + ruleManager);
            }//end if(ruleManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "ruledata");
        }//end of catch
        return ruleManager;
    }//end of getRuleManagerObject()

}//end of class BufferAction

