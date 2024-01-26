/**
 * @ (#) ProviderConfDetailsView.java June 27, 2015
 * Project : ProjectX
 * File : ProviderConfDetailsView.java
 * Author : Nagababu K
 * Company :Vidal
 * Date Created : June 27, 2015
 *
 * @author : Nagababu K
 * Modified by :
 * Modified date :
 * Reason :
*/
package com.ttk.common.tags.administration;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.DynaActionForm;

import com.ttk.common.security.Cache;
import com.ttk.dto.administration.ProviderDetails;
import com.ttk.dto.common.CacheObject;
import com.ttk.xml.administration.Condition;
import com.ttk.xml.administration.CountryCopayDetails;
import com.ttk.xml.administration.GeoLocationCopayDetails;
import com.ttk.xml.administration.ProviderCopayDetails;

public class ProviderConfDetailsView extends TagSupport
{
	/**CountryCopayDetails
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( CitibankEnrolHistory.class );
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
	{
		try
		{
		
			JspWriter out = pageContext.getOut();//Writer Object to write the File		
			String gridOddRow="'gridOddRow'";
		    String gridEvenRow="'gridEvenRow'";
			HttpSession session=pageContext.getSession();
			
			DynaActionForm frmProviderCopayRules=(DynaActionForm)session.getAttribute("frmProviderCopayRules");
			
			HashMap<String, ArrayList<Condition>> benefitConditions;
			 
			benefitConditions=(HashMap<String, ArrayList<Condition>>)session.getAttribute("ProviderRulesXmlConditions");
			
			String strViewModeType=pageContext.getRequest().getParameter("viewModeType");
			String strRownum=pageContext.getRequest().getParameter("rownum");
			String strBenefitType=frmProviderCopayRules.getString("benefitType");
			
		if(benefitConditions!=null){
			
	ArrayList<Condition> alConditions=benefitConditions.get(strBenefitType);
			
	if(alConditions!=null){
		
		Condition condition=alConditions.get(Integer.parseInt(strRownum));
		
		if("PFT".equals(strViewModeType)){
			
			out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'");
			out.print("<tr>");
			out.print("<th align='center' class='gridHeader'>Provider Fecility Types</th>");
		    out.print("</tr>");
		    
		    
		    
		    String strArrFecilities[]=condition.getProviderFacilities().split("[|]");
		    int rowCount=strArrFecilities.length;
		    
	        	for(int i=0;i<rowCount;i++){
	               
	        		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");	                	
	                out.print("<td align='left'>"+getFecilityName(strArrFecilities[i])+"</td>");
	                out.print("</tr>");
	                
	        	}//for(int i=0;i<rowCount;i++){
	        	
	       out.print("</table>");
		    }//if("PFT".equals(strViewModeType)){
	        	
		else if("GEO".equals(strViewModeType)){
            
			GeoLocationCopayDetails  geoLoc=condition.getGeoLocCopayDetails();
			
			out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:98%;height:auto;'");
			out.print("<tr>");
			out.print("<th align='center' style='width:38%;' class='gridHeader'>Geographical Location</th>");
			out.print("<th align='center' style='width:60%;' class='gridHeader'>Copay Details</th>");
		    out.print("</tr>");
		    
		    
		    out.print("<tr class=\"gridOddRow\">");	                	
            out.print("<td style='width:38%;' align='center'>"+geoLoc.getLocationName()+"</td>");
            
            out.print("<td style='width:60%;' align='left'>");
    		out.print("<table>");             		
    		out.print("<tr>");
    		
    		out.print("<td>");
    		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+geoLoc.getCopay()+"\" class=\"textBoxTS\" type=\"text\" readonly>");
    		out.print("%(or)");
    		out.print("</td>");
    		out.print("<td>");
    		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+geoLoc.getFlatamount()+"\" class=\"textBoxTS\" type=\"text\" readonly>");
    		out.print("</td>");
    		out.print("<td>");
    		out.print("<input onkeyup=\"isNumeric(this);\" value=\"AED\" readonly class=\"textBoxTS\" type=\"text\">");
    		out.print("</td>");
    		out.print("<td>");
    		
    		out.print("<select disabled class=\"selBoxTTS\">");
    		
    		if("MAX".equals(geoLoc.getMinmax())){
    			out.print( "<option value=\"MIN\">MIN</option><option value=\"MAX\" selected>MAX</option>");	  
    		}else {
    			out.print( "<option value=\"MIN\" selected>MIN</option><option value=\"MAX\">MAX</option>");	  
    		}
    		
    		out.print("</select>");
    		out.print("</td>");
    		out.print("</tr>");
    		out.print("</table>");
    		
    		out.print("</td>");	
            out.print("</tr>");
	               
	        	
	       out.print("</table>");
		    }//if("GEO".equals(strViewModeType)){
		  
         else if("CON".equals(strViewModeType)){
            
        	 ArrayList<CountryCopayDetails>  alCountries=condition.getCountries().getCountriesCopayDetails();
			
			out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:98%;height:auto;'");
			out.print("<tr>");
			out.print("<th align='center' style='width:38%;' class='gridHeader'>Country/Emirate Name</th>");
			out.print("<th align='center' style='width:60%;' class='gridHeader'>Copay Details</th>");
		    out.print("</tr>");
		    
		    int rowCount=1;
		    for(CountryCopayDetails countryCopayDetails:alCountries){
		    
		    	out.print("<tr class="+(rowCount%2==0?gridEvenRow:gridOddRow)+">");     
		    	
            out.print("<td style='width:38%;' align='left'>"+countryCopayDetails.getCountryName()+"</td>");
            
            out.print("<td style='width:60%;' align='left'>");
    		out.print("<table>");             		
    		out.print("<tr>");
    		
    		out.print("<td>");
    		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+countryCopayDetails.getCopay()+"\" class=\"textBoxTS\" type=\"text\" readonly>");
    		out.print("%(or)");
    		out.print("</td>");
    		out.print("<td>");
    		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+countryCopayDetails.getFlatamount()+"\" class=\"textBoxTS\" type=\"text\" readonly>");
    		out.print("</td>");
    		out.print("<td>");
    		out.print("<input onkeyup=\"isNumeric(this);\" value=\"AED\" readonly class=\"textBoxTS\" type=\"text\">");
    		out.print("</td>");
    		out.print("<td>");
    		
    		out.print("<select disabled class=\"selBoxTTS\">");
    		
    		if("MAX".equals(countryCopayDetails.getMinmax())){
    			out.print( "<option value=\"MIN\">MIN</option><option value=\"MAX\" selected>MAX</option>");	  
    		}else {
    			out.print( "<option value=\"MIN\" selected>MIN</option><option value=\"MAX\">MAX</option>");	  
    		}
    		
    		out.print("</select>");
    		out.print("</td>");
    		out.print("</tr>");
    		out.print("</table>");
    		
    		out.print("</td>");	
            out.print("</tr>");
            rowCount++;
		    }//for(CountryCopayDetails countryCopayDetails:alCountries){
	        	
	       out.print("</table>");
		    }//if("CON".equals(strViewModeType)){
		
         else if("PRO".equals(strViewModeType)){
        	 String strProSeqIDs=condition.getProviders().getProviderSeqIDs();
        	 ArrayList<ProviderCopayDetails>  alPrCopayDetails=condition.getProviders().getProvidersCopayDetails();
        	 HashMap<String,ProviderDetails>  hmProviderNameList=(HashMap<String,ProviderDetails>)session.getAttribute("ProviderNameList");
        	 if(alPrCopayDetails==null)alPrCopayDetails=new ArrayList<>();
        	 if(hmProviderNameList==null)hmProviderNameList=new HashMap<>();
			out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:98%;height:auto;'");
			out.print("<tr>");
			out.print("<th align='center' style='width:20%;' class='gridHeader'>Provider Name</th>");
			out.print("<th align='center' style='width:13%;' class='gridHeader'>Provider License ID</th>");
			out.print("<th align='center' style='width:8%;' class='gridHeader'>Country/Emirate Name</th>");
			out.print("<th align='center' style='width:8%;' class='gridHeader'>Provider Type</th>");
			out.print("<th align='center' style='width:10%;' class='gridHeader'>Provider Fecility Type</th>");
			out.print("<th align='center' style='width:8%;' class='gridHeader'>Supported Networks</th>");
			out.print("<th align='center' style='width:28%;' class='gridHeader'>Copay Details</th>");
		    out.print("</tr>");
		    
		    int rowCount=1;
		    if(strProSeqIDs!=null&&strProSeqIDs.length()>0){
		   
		    	for(ProviderCopayDetails providerCopayDetails:alPrCopayDetails){
		    		
		    	ProviderDetails providerDetails=hmProviderNameList.get(providerCopayDetails.getProviderSeqID());
		    	if(providerDetails==null)providerDetails=new ProviderDetails();
		    	out.print("<tr class="+(rowCount%2==0?gridEvenRow:gridOddRow)+">");     
		    	
            out.print("<td style='width:20%;' align='left'>"+providerDetails.getProviderName()+"</td>");
            out.print("<td style='width:13%;' align='left'>"+providerDetails.getProviderLicenceID()+"</td>");
            out.print("<td style='width:8%;' align='left'>"+providerDetails.getProviderCountryName()+"</td>");
            out.print("<td style='width:8%;' align='left'>"+providerDetails.getProviderType()+"</td>");
            out.print("<td style='width:10%;' align='left'>"+providerDetails.getProviderFacilityType()+"</td>");
            out.print("<td style='width:8%;' align='left'>"+providerDetails.getSupportedNetworks()+"</td>");
            out.print("<td style='width:28%;' align='left'>");
    		out.print("<table>");             		
    		out.print("<tr>");
    		
    		out.print("<td>");
    		out.print("<input  value=\""+providerCopayDetails.getCopay()+"\" class=\"textBoxTS\" type=\"text\" readonly>");
    		out.print("%(or)");
    		out.print("</td>");
    		out.print("<td>");
    		out.print("<input  value=\""+providerCopayDetails.getFlatamount()+"\" class=\"textBoxTS\" type=\"text\" readonly>");
    		out.print("</td>");
    		out.print("<td>");
    		out.print("<input  value=\"AED\" readonly class=\"textBoxTS\" type=\"text\">");
    		out.print("</td>");
    		out.print("<td>");
    		
    		out.print("<select disabled class=\"selBoxTTS\">");
    		
    		if("MAX".equals(providerCopayDetails.getMinmax())){
    			out.print( "<option value=\"MIN\">MIN</option><option value=\"MAX\" selected>MAX</option>");	  
    		}else {
    			out.print( "<option value=\"MIN\" selected>MIN</option><option value=\"MAX\">MAX</option>");	  
    		}
    		
    		out.print("</select>");
    		out.print("</td>");
    		out.print("</tr>");
    		out.print("</table>");
    		
    		out.print("</td>");	
            out.print("</tr>");
            rowCount++;
		    }//for(CountryCopayDetails countryCopayDetails:alCountries){
	        	
	       out.print("</table>");
		    }//if(strProSeqIDs!=null&&strProSeqIDs.length()>0){
		    else{


		    	 HashMap<String, CountryCopayDetails> hmCountryCopayDetails=( HashMap<String, CountryCopayDetails>)session.getAttribute("CountryCopayDetails");
     		   
		    	 ArrayList<ProviderDetails> alProviderDetails=(ArrayList<ProviderDetails>)session.getAttribute("ProviderLocationDetailsList");
     	  if(hmCountryCopayDetails!=null){
		    	 if(alProviderDetails!=null){
				   
		    for(ProviderDetails providerCopayDetails:alProviderDetails){
		    
		    	CountryCopayDetails countryCopayDetails=hmCountryCopayDetails.get(providerCopayDetails.getProviderCountryID());
		    	if(countryCopayDetails==null)countryCopayDetails=new CountryCopayDetails();
		    	
		    	out.print("<tr class="+(rowCount%2==0?gridEvenRow:gridOddRow)+">");     
		    	            
            out.print("<td style='width:20%;' align='left'>"+providerCopayDetails.getProviderName()+"</td>");
            out.print("<td style='width:13%;' align='left'>"+providerCopayDetails.getProviderLicenceID()+"</td>");
            out.print("<td style='width:8%;' align='left'>"+providerCopayDetails.getProviderCountryName()+"</td>");
            out.print("<td style='width:8%;' align='left'>"+providerCopayDetails.getProviderType()+"</td>");
            out.print("<td style='width:10%;' align='left'>"+providerCopayDetails.getProviderFacilityType()+"</td>");
            out.print("<td style='width:8%;' align='left'>"+providerCopayDetails.getSupportedNetworks()+"</td>");
            out.print("<td style='width:28%;' align='left'>");
    		out.print("<table>");             		
    		out.print("<tr>");
    		
    		out.print("<td>");
    		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+countryCopayDetails.getCopay()+"\" class=\"textBoxTS\" type=\"text\" readonly>");
    		out.print("%(or)");
    		out.print("</td>");
    		out.print("<td>");
    		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+countryCopayDetails.getFlatamount()+"\" class=\"textBoxTS\" type=\"text\" readonly>");
    		out.print("</td>");
    		out.print("<td>");
    		out.print("<input onkeyup=\"isNumeric(this);\" value=\"AED\" readonly class=\"textBoxTS\" type=\"text\">");
    		out.print("</td>");
    		out.print("<td>");
    		
    		out.print("<select disabled class=\"selBoxTTS\">");
    		
    		if("MAX".equals(countryCopayDetails.getMinmax())){
    			out.print( "<option value=\"MIN\">MIN</option><option value=\"MAX\" selected>MAX</option>");	  
    		}else {
    			out.print( "<option value=\"MIN\" selected>MIN</option><option value=\"MAX\">MAX</option>");	  
    		}
    		
    		out.print("</select>");
    		out.print("</td>");
    		out.print("</tr>");
    		out.print("</table>");
    		
    		out.print("</td>");	
            out.print("</tr>");
            rowCount++;
		    }//for(CountryCopayDetails countryCopayDetails:alCountries){
		    	 }//if(alProviderDetails!=null){
     	  }//if(hmCountryCopayDetails!=null){
	       out.print("</table>");
		    
		    }
		    }//if("PRO".equals(strViewModeType)){
		
		out.print("<table style='margin-top:10px;' border='0' align='center' cellpadding='0' cellspacing='0'>");
		  out.print("<tr>");
		  out.print("<td>");	
		  out.print("<button type=\"button\" name=\"Button\" accesskey=\"c\" class=\"buttons\" onMouseout=\"this.className='buttons'\" onMouseover=\"this.className='buttons buttonsHover'\" onClick=\"javascript:self.close();\"><u>C</u>lose</button>");
		  out.print("</td>");	
		out.print("</tr>");
		  out.print("</table>");
		  
	}//if(conditions!=null){
		}//if(benefitConditions!=null){
	      }//end of try
		catch(Exception exp)
        {
            exp.printStackTrace();
        }//end of catch block
        return SKIP_BODY;
	}//end of doStartTag()
	public int doEndTag() throws JspException 
	{
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
	private ArrayList alFecilities;
	private String getFecilityName(String fecilityID)throws Exception{
	if(alFecilities==null)alFecilities=Cache.getCacheObject("providerType");
	String fecilityDesc="";
		for(int i=0;i<alFecilities.size();i++){
			CacheObject  cachObj=(CacheObject)alFecilities.get(i);
			
			if(cachObj.getCacheId().equals(fecilityID)){
				fecilityDesc=cachObj.getCacheDesc();
				break;
			}
		}
		return fecilityDesc;
	}
	
}//end of DiagnosisDetails class 
