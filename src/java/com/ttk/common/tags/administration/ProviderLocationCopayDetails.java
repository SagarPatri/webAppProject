/**
 * @ (#) ProviderCopayDetails.java June 27, 2015
 * Project : ProjectX
 * File : ProviderCopayDetails.java
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.DynaActionForm;

import com.ttk.common.TTKCommon;
import com.ttk.dto.administration.ProviderDetails;
import com.ttk.dto.common.CacheObject;
import com.ttk.xml.administration.CountryCopayDetails;
import com.ttk.xml.administration.GeoLocationCopayDetails;
import com.ttk.xml.administration.ProviderCopayDetails;

public class ProviderLocationCopayDetails extends TagSupport
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
			String defSelect=" selected=\"selected\"";
			HttpSession session=pageContext.getSession();
			
			DynaActionForm frmProviderCopayRules=(DynaActionForm)session.getAttribute("frmProviderCopayRules");
			
			String strLocationType=frmProviderCopayRules.getString("locationType");
			
			
			
		if("GEO".equals(strLocationType)){
				
			String strGeoLocation=TTKCommon.checkNull(frmProviderCopayRules.getString("geoLocation"));
			
			GeoLocationCopayDetails geoLocationCopayDetails=(GeoLocationCopayDetails)frmProviderCopayRules.get("XmlGeoLocationCopayDetails");
			
		
			if(geoLocationCopayDetails==null)geoLocationCopayDetails=new GeoLocationCopayDetails();
			
				ArrayList<GeoLocationCopayDetails> locationList=null;
				locationList= (ArrayList<GeoLocationCopayDetails>)session.getAttribute("GeographicLocationsList");
			
	       out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'");
			out.print("<tr>");
			out.print("<th align='center' style='width:5%;' class='gridHeader'></th>");
			out.print("<th align='center' style='width:45%;' class='gridHeader' title=\"Geographical Locations\">Geographical Locations</th>");
			out.print("<th align='center' style='width:50%;' class='gridHeader' title=\"Co-pay Details\">Co-pay Details</th>");
		    out.print("</tr>");
	       
			
	        if(locationList != null&&locationList.size()>0){
	        	int rowCount=locationList.size();
	        	
	        	for(int i=0;i<rowCount;i++){
	                			               			
	        		GeoLocationCopayDetails location=locationList.get(i);
	                	
	        		
	        		if(strGeoLocation.equals(location.getLocationCode())){
	        		
	                out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                	
	                    out.print("<td style='width:5%;' align='center'><input type=\"radio\" class=\"copayChBox\" onclick=\"selectGeolocation();\" checked   name=\"checkedIndex\" value=\""+i+"\"></td>");
	                		out.print("<td style='width:45%;' align='left'>"+location.getLocationName()+"</td>");
	                		out.print("<td style='width:50%;' align='left'>");
	                		out.print("<table>");             		
	                		out.print("<tr>");
	                		
	                		out.print("<td>");
	                		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+geoLocationCopayDetails.getCopay()+"\" class=\"CurrenttextBoxTS\" type=\"text\"  id=\"copay"+i+"\" name=\"copay"+i+"\" >");
	                		out.print("%(or)");
	                		out.print("</td>");
	                		out.print("<td>");
	                		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+geoLocationCopayDetails.getFlatamount()+"\" class=\"CurrenttextBoxTS\" type=\"text\"  id=\"flatamount"+i+"\" name=\"flatamount"+i+"\">");
	                		out.print("</td>");
	                		out.print("<td>");
	                		out.print("<input readonly value=\"AED\" class=\"CurrenttextBoxTS\" type=\"text\" name=\"currency"+i+"\">");
	                		out.print("</td>");
	                		out.print("<td>");
	                		
	                		out.print("<select class=\"selBoxTTS\" id=\"minmax"+i+"\"  name=\"minmax"+i+"\" >");
	                		
	                		if("MIN".equals(geoLocationCopayDetails.getMinmax())){
	                			out.print( "<option value=\"MIN\""+defSelect+">MIN</option><option value=\"MAX\">MAX</option>");	  
	                		}else {
	                			out.print( "<option value=\"MAX\""+defSelect+">MAX</option><option value=\"MIN\">MIN</option>");	  
	                		}
	                		
	                		out.print("</select>");
	                		out.print("</td>");
	                		out.print("</tr>");
	                		out.print("</table>");
	                		out.print("</td>");	
	                		
	                		out.print("</tr>");	  
	        		}else{
		        		
		                out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
		                	
		                    out.print("<td style='width:5%;' align='center'><input type=\"radio\" class=\"copayChBox\" onclick=\"selectGeolocation();\"   name=\"checkedIndex\" value=\""+i+"\"></td>");
		                		out.print("<td style='width:45%;' align='left'>"+location.getLocationName()+"</td>");
		                		out.print("<td style='width:50%;' align='left'>");
		                		out.print("<table>");             		
		                		out.print("<tr>");
		                		
		                		out.print("<td>");
		                		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+location.getCopay()+"\" class=\"textBoxTS\" type=\"text\" readonly id=\"copay"+i+"\" name=\"copay"+i+"\" >");
		                		out.print("%(or)");
		                		out.print("</td>");
		                		out.print("<td>");
		                		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+location.getFlatamount()+"\" class=\"textBoxTS\" type=\"text\" readonly id=\"flatamount"+i+"\" name=\"flatamount"+i+"\">");
		                		out.print("</td>");
		                		out.print("<td>");
		                		out.print("<input  value=\"AED\" readonly class=\"textBoxTS\" type=\"text\" name=\"currency"+i+"\">");
		                		out.print("</td>");
		                		out.print("<td>");
		                		
		                		out.print("<select class=\"selBoxTTS\" id=\"minmax"+i+"\"  name=\"minmax"+i+"\" >");
		                		
		                		if("MIN".equals(location.getMinmax())){
		                			out.print( "<option value=\"MIN\""+defSelect+">MIN</option><option value=\"MAX\">MAX</option>");	  
		                		}else {
		                			out.print( "<option value=\"MAX\""+defSelect+">MAX</option><option value=\"MIN\">MIN</option>");	  
		                		}
		                		
		                		out.print("</select>");
		                		out.print("</td>");
		                		out.print("</tr>");
		                		out.print("</table>");
		                		out.print("</td>");	
		                		
		                		out.print("</tr>");	  
		        		
	        		}
	                			}//for(int i=0;i<rowCount;i++){
	                	out.print("</table>");
	        	}//if(locationList != null&&locationList.size()>0){
	        
				
				
			}//if("GEO".equals(strLocationType)){
			
		//country copay details configuration
		
		else if("CON".equals(strLocationType)){
				
			       out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'");
					out.print("<tr>");
					out.print("<th align='center' style='width:5%;' class='gridHeader'>Check All<input title=\"Select All\" type=\"checkbox\" class=\"copayChBox\" onclick=\"checkAllCountries(this);\" name=\"checkAll\"></th>");
					out.print("<th align='center' style='width:45%;' class='gridHeader' title=\"Countries\">Countries/"+frmProviderCopayRules.getString("geoLocationName")+"</th>");
					out.print("<th align='center' style='width:50%;' class='gridHeader' title=\"Co-pay Details\">Co-pay Details</th>");
				    out.print("</tr>");
				
				    ArrayList<CountryCopayDetails> contryList=null;
					contryList= (ArrayList<CountryCopayDetails>)session.getAttribute("CountryLocationsList");
				 
			if(contryList != null){
				    
				String strCountries=TTKCommon.checkNull(frmProviderCopayRules.getString("countryIDs"));
									
					if(strCountries.length()>0){
					
				HashMap<String, CountryCopayDetails>	countriesCopayDetails=(HashMap<String, CountryCopayDetails>)frmProviderCopayRules.get("XmlConrtiesCopayDetails");
		        	
		        	int rowCount=contryList.size();		        	
		        	for(int i=0;i<rowCount;i++){
		                			               			
		        		CountryCopayDetails countryLocation=contryList.get(i);
		        		String strChecked;
		        		String strTextBoxTS;
		        		String strReadonly;
		        		
		        		
		        		  out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
		        		  
		        		if(countriesCopayDetails.get(countryLocation.getCountryCode())!=null){		        	
		        			
		        			countryLocation=countriesCopayDetails.get(countryLocation.getCountryCode());
		        			strTextBoxTS="CurrenttextBoxTS";
		        			strChecked=" checked ";
		        			strReadonly="";
		        		}else{
		        			strTextBoxTS="textBoxTS";
		        			strChecked="";
		        			strReadonly=" readonly ";
		        		}
		                   

	        			 out.print("<td style='width:5%;' align='center'><input type=\"checkbox\" class=\"copayChBox\" onclick=\"checkCountry(this);\" "+strChecked+" name=\"checkedIndex\" value=\""+i+"\"></td>");
	                		out.print("<td style='width:45%;' align='left'>"+countryLocation.getCountryName()+"</td>");
	                		out.print("<td style='width:50%;' align='left'>");
	                		out.print("<table>");             		
	                		out.print("<tr>");
	                		
	                		out.print("<td>");
	                		out.print("<input onkeyup=\"isNumeric(this);\" "+strReadonly+" value=\""+countryLocation.getCopay()+"\" class=\""+strTextBoxTS+"\" type=\"text\"  id=\"copay"+i+"\" name=\"copay"+i+"\" >");
	                		out.print("%(or)");
	                		out.print("</td>");
	                		out.print("<td>");
	                		out.print("<input onkeyup=\"isNumeric(this);\" "+strReadonly+" value=\""+countryLocation.getFlatamount()+"\" class=\""+strTextBoxTS+"\" type=\"text\"  id=\"flatamount"+i+"\" name=\"flatamount"+i+"\">");
	                		out.print("</td>");
	                		out.print("<td>");
	                		out.print("<input readonly value=\"AED\" class=\""+strTextBoxTS+"\" type=\"text\" name=\"currency"+i+"\">");
	                		out.print("</td>");
	                		out.print("<td>");
	                		
	                		out.print("<select class=\"selBoxTTS\" id=\"minmax"+i+"\"  name=\"minmax"+i+"\" >");
	                		
	                		if("MIN".equals(countryLocation.getMinmax())){
	                			out.print( "<option value=\"MAX\">MAX</option><option value=\"MIN\""+defSelect+">MIN</option>");	  
	                		}else {
	                			out.print( "<option value=\"MAX\""+defSelect+">MAX</option><option value=\"MIN\">MIN</option>");	  
	                		}
	                		
	                		out.print("</select>");
	                		out.print("</td>");
	                		out.print("</tr>");
	                		out.print("</table>");
	                		out.print("</td>");	
	                		
	                		out.print("</tr>");	
	                		
		        		}//for(int i=0;i<rowCount;i++){		                			
				
				}else{

					GeoLocationCopayDetails geoLocationCopayDetails=(GeoLocationCopayDetails)frmProviderCopayRules.get("XmlGeoLocationCopayDetails");
				
					if(geoLocationCopayDetails==null)geoLocationCopayDetails=new GeoLocationCopayDetails();
				
		       
		        	int rowCount=contryList.size();
		        	
		        	for(int i=0;i<rowCount;i++){
		                			               			
		        		CountryCopayDetails countryLocation=contryList.get(i);
		        				        		
		                out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
		                	
		                    out.print("<td style='width:5%;' align='center'><input type=\"checkbox\" class=\"copayChBox\" onclick=\"checkCountry(this);\" checked   name=\"checkedIndex\" value=\""+i+"\"></td>");
		                		out.print("<td style='width:45%;' align='left'>"+countryLocation.getCountryName()+"</td>");
		                		out.print("<td style='width:50%;' align='left'>");
		                		out.print("<table>");             		
		                		out.print("<tr>");
		                		
		                		out.print("<td>");
		                		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+geoLocationCopayDetails.getCopay()+"\" class=\"CurrenttextBoxTS\" type=\"text\"  id=\"copay"+i+"\" name=\"copay"+i+"\" >");
		                		out.print("%(or)");
		                		out.print("</td>");
		                		out.print("<td>");
		                		out.print("<input onkeyup=\"isNumeric(this);\" value=\""+geoLocationCopayDetails.getFlatamount()+"\" class=\"CurrenttextBoxTS\" type=\"text\"  id=\"flatamount"+i+"\" name=\"flatamount"+i+"\">");
		                		out.print("</td>");
		                		out.print("<td>");
		                		out.print("<input readonly value=\"AED\" class=\"CurrenttextBoxTS\" type=\"text\" name=\"currency"+i+"\">");
		                		out.print("</td>");
		                		out.print("<td>");
		                		
		                		out.print("<select class=\"selBoxTTS\" id=\"minmax"+i+"\"  name=\"minmax"+i+"\" >");
		                		
		                		if("MIN".equals(geoLocationCopayDetails.getMinmax())){
		                			out.print( "<option value=\"MAX\">MAX</option><option value=\"MIN\""+defSelect+">MIN</option>");	  
		                		}else {
		                			out.print( "<option value=\"MAX\""+defSelect+">MAX</option><option value=\"MIN\">MIN</option>");	  
		                		}
		                		
		                		out.print("</select>");
		                		out.print("</td>");
		                		out.print("</tr>");
		                		out.print("</table>");
		                		out.print("</td>");		                		
		                		out.print("</tr>");	  
		        		
		                			}//for(int i=0;i<rowCount;i++){
		                
		        	}//if(locationList != null&&locationList.size()>0){
					
			   
			}//if(contryList != null){
			out.print("</table>");
		}//if("CON".equals(strLocationType)){
		else if("PFT".equals(strLocationType)){
				
			      
			out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:30%;height:auto;'");
					out.print("<tr>");
					out.print("<th align='center' style='width:5%;' class='gridHeader'><input title=\"Select All\" type=\"checkbox\" class=\"copayChBox\" onclick=\"checkAllFecilities(this);\" name=\"checkAll\"></th>");
					out.print("<th align='center' style='width:25%;' class='gridHeader' title=\"Provider Facility Type \">Provider Facility Type </th>");
				    out.print("</tr>");
				    
				    ArrayList alProviderFecilityTypes=(ArrayList)session.getAttribute("ProviderFecilityList");
				  
				    String strPFT=TTKCommon.checkNull(frmProviderCopayRules.getString("providerFacilities"));
				  
				    int rowCount=alProviderFecilityTypes.size();
				    
			        	for(int i=0;i<rowCount;i++){
			        		CacheObject  cachObj=(CacheObject)alProviderFecilityTypes.get(i);
			        		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");	                	
			                
			        		if(strPFT.contains(cachObj.getCacheId())) out.print("<td align='center'><input type=\"checkbox\" class=\"copayChBox\" checked name=\"checkedIndex\" value=\""+cachObj.getCacheId()+"\"></td>");
			                else out.print("<td align='center'><input type=\"checkbox\" class=\"copayChBox\" name=\"checkedIndex\" value=\""+cachObj.getCacheId()+"\"></td>");
			                
			        		out.print("<td align='left'>"+cachObj.getCacheDesc()+"</td>");
			                
			                out.print("</tr>");
			                
			        	}//for(int i=0;i<rowCount;i++){
			        	
			        	 out.print("</table>"); 
		}
		else if("MTI".equals(strLocationType)  || "OPTS".equals(strLocationType) || "IPT".equals(strLocationType))
		{
			out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:40%;height:auto;'");
			out.print("<tr>");
			out.print("<th align='center' style='width:5%;' class='gridHeader'>Check All<input title=\"Select All\" type=\"checkbox\" class=\"copayChBox\" onclick=\"checkAllEncounters(this);\" name=\"checkAll\"></th>");

			out.print("<th align='center' style='width:20%;' class='gridHeader' title=\"Provider Name\">Encounter Type Description</th>");


			out.print("</tr>");
			HashMap<String, String>hmencounterdata=(HashMap)session.getAttribute("encounterTypedatahm");

			String strencountertypeids=TTKCommon.checkNull(frmProviderCopayRules.getString("encountertypeId"));


			if(!hmencounterdata.isEmpty())
			{
			
				Set keys = hmencounterdata.keySet();
				Iterator itr = keys.iterator();

				String key;
				String value;
				int i=0;
				while(itr.hasNext())
				{

					out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");	                	
					key = (String)itr.next();

					if(strencountertypeids.contains(key))
						out.print("<td align='center'><input type=\"checkbox\" class=\"copayChBox\" checked name=\"checkedIndex\" value=\""+key+"\"></td>");
					else
						out.print("<td align='center'><input type=\"checkbox\" class=\"copayChBox\"  name=\"checkedIndex\" value=\""+key+"\"></td>");

					value = (String)hmencounterdata.get(key);


					out.print("<td align='left'>"+value+"</td>");

					out.print("</tr>");

				}
			}
		}
		
		else if("PRO".equals(strLocationType)){
				

			       out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'");
					out.print("<tr>");
					out.print("<th align='center' style='width:5%;' class='gridHeader'>Check All<input title=\"Select All\" type=\"checkbox\" class=\"copayChBox\" onclick=\"checkAllProvider(this);\" name=\"checkAll\"></th>");
					
					out.print("<th align='center' style='width:20%;' class='gridHeader' title=\"Provider Name\">Provider Name</th>");
					out.print("<th align='center' style='width:13%;' class='gridHeader' title=\"Provider Licence ID\">Provider Licence ID</th>");
					out.print("<th align='center' style='width:8%;' class='gridHeader' title=\"Country/Emirate\">Country/Emirate</th>");
					out.print("<th align='center' style='width:8%;' class='gridHeader' title=\"Provider Type\">Provider Type</th>");
					out.print("<th align='center' style='width:10%;' class='gridHeader' title=\"Provider Fecility Type\">Provider Fecility Type</th>");
					out.print("<th align='center' style='width:8%;' class='gridHeader' title=\"Provider Fecility Type\">Primary Network</th>");
					out.print("<th align='center' style='width:28%;' class='gridHeader' title=\"Co-pay Details\">Co-pay Details</th>");
				   
					out.print("</tr>");
				
					
				    ArrayList<ProviderDetails> alProviderDetails=null;
				    alProviderDetails= (ArrayList<ProviderDetails>)session.getAttribute("ProviderLocationDetailsList");
				  
				    if(alProviderDetails==null) alProviderDetails=new ArrayList<>();
				  
				    int rowCount=alProviderDetails.size();	
				     					    
				  String strProviderSeqIDs=TTKCommon.checkNull(frmProviderCopayRules.getString("providerSeqIDs"));
												
			if(strProviderSeqIDs.length()>0){
									
				HashMap<String, ProviderCopayDetails>  xmlProviderCopayDetails=(HashMap<String, ProviderCopayDetails>)frmProviderCopayRules.get("XmlProviderCopayDetails");
                if(xmlProviderCopayDetails==null)xmlProviderCopayDetails=new HashMap<>(); 
                
	        		String strCopay;
	        		String strFlatAmt;
	        		String strCurrency;
	        		String strMinMax;
	        		
	        		 StringBuilder checkedData=new StringBuilder();
	        	     StringBuilder unCheckedData=new StringBuilder();
	        	     
	        	for(int i=0;i<rowCount;i++){
	                			               			
	     ProviderDetails providerDetails=alProviderDetails.get(i);
	     
	     ProviderCopayDetails confProviderDetails= xmlProviderCopayDetails.get(providerDetails.getProviderSeqID());
	    
	     
	      if(confProviderDetails==null){
	    	  
    		  unCheckedData.append("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
    		  
			unCheckedData.append("<td style='width:5%;' align='center'><input type=\"checkbox\" class=\"copayChBox\" onclick=\"checkProvider(this);\" name=\"checkedIndex\" value=\""+i+"\"><input type=\"hidden\" name=\"providerSeqID"+i+"\" value=\""+providerDetails.getProviderSeqID()+"\"></td>");
			unCheckedData.append("<td style='width:20%;' align='left'>"+providerDetails.getProviderName()+"</td>");
      		unCheckedData.append("<td style='width:13%;' align='left'>"+providerDetails.getProviderLicenceID()+"</td>");
      		unCheckedData.append("<td style='width:8%;' align='left'>"+providerDetails.getProviderCountryName()+"</td>");
      		unCheckedData.append("<td style='width:8%;' align='left'>"+providerDetails.getProviderType()+"</td>");
      		unCheckedData.append("<td style='width:10%;' align='left'>"+providerDetails.getProviderFacilityType()+"</td>");
      		unCheckedData.append("<td style='width:8%;' align='left'>"+providerDetails.getSupportedNetworks()+"</td>");
      		unCheckedData.append("<td style='width:28%;' align='left'>");
      		unCheckedData.append("<table>");             		
      		unCheckedData.append("<tr>");
      		
      		unCheckedData.append("<td>");
      		unCheckedData.append("<input onkeyup=\"isNumeric(this);\" readonly value=\"\" class=\"textBoxTS\" type=\"text\"  id=\"copay"+i+"\" name=\"copay"+i+"\" >");
      		unCheckedData.append("%(or)");
      		unCheckedData.append("</td>");
      		unCheckedData.append("<td>");
      		unCheckedData.append("<input onkeyup=\"isNumeric(this);\" readonly value=\"\" class=\"textBoxTS\" type=\"text\"  id=\"flatamount"+i+"\" name=\"flatamount"+i+"\">");
      		unCheckedData.append("</td>");
      		unCheckedData.append("<td>");
      		unCheckedData.append("<input readonly value=\"AED\" class=\"textBoxTS\" type=\"text\" name=\"currency"+i+"\">");
      		unCheckedData.append("</td>");
      		unCheckedData.append("<td>");
      		
      		unCheckedData.append("<select class=\"selBoxTTS\" id=\"minmax"+i+"\"  name=\"minmax"+i+"\" >");
      		
      		unCheckedData.append( "<option value=\"MAX\" selecded>MAX</option><option value=\"MIN\">MIN</option>");	  
      		
      		unCheckedData.append("</select>");
      		unCheckedData.append("</td>");
      		unCheckedData.append("</tr>");
      		unCheckedData.append("</table>");
      		unCheckedData.append("</td>");	
      		
      		unCheckedData.append("</tr>");	
	    	  
      		
	      }//if(confProviderDetails==null){	
	      else{
	    	  
	    	  strCopay=confProviderDetails.getCopay();
	    	  strFlatAmt=confProviderDetails.getFlatamount();
	    	  strCurrency=confProviderDetails.getCurrency();
	    	  strMinMax=confProviderDetails.getMinmax();
	    	  

	    	  checkedData.append("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
    		  
			 checkedData.append("<td style='width:5%;' align='center'><input type=\"checkbox\" class=\"copayChBox\" onclick=\"checkProvider(this);\" checked name=\"checkedIndex\" value=\""+i+"\"><input type=\"hidden\" name=\"providerSeqID"+i+"\" value=\""+providerDetails.getProviderSeqID()+"\"></td>");
			 checkedData.append("<td style='width:20%;' align='left'>"+providerDetails.getProviderName()+"</td>");
			 checkedData.append("<td style='width:13%;' align='left'>"+providerDetails.getProviderLicenceID()+"</td>");
			 checkedData.append("<td style='width:8%;' align='left'>"+providerDetails.getProviderCountryName()+"</td>");
			 checkedData.append("<td style='width:8%;' align='left'>"+providerDetails.getProviderType()+"</td>");
			 checkedData.append("<td style='width:10%;' align='left'>"+providerDetails.getProviderFacilityType()+"</td>");
			 checkedData.append("<td style='width:8%;' align='left'>"+providerDetails.getSupportedNetworks()+"</td>");
			 checkedData.append("<td style='width:28%;' align='left'>");
			 checkedData.append("<table>");             		
			 checkedData.append("<tr>");
      		
			 checkedData.append("<td>");
			 checkedData.append("<input onkeyup=\"isNumeric(this);\"  value=\""+strCopay+"\" class=\"CurrenttextBoxTS\" type=\"text\"  id=\"copay"+i+"\" name=\"copay"+i+"\" >");
			 checkedData.append("%(or)");
			 checkedData.append("</td>");
			 checkedData.append("<td>");
			 checkedData.append("<input onkeyup=\"isNumeric(this);\" value=\""+strFlatAmt+"\" class=\"CurrenttextBoxTS\" type=\"text\"  id=\"flatamount"+i+"\" name=\"flatamount"+i+"\">");
			 checkedData.append("</td>");
			 checkedData.append("<td>");
			 checkedData.append("<input readonly value=\"AED\" class=\"CurrenttextBoxTS\" type=\"text\" name=\"currency"+i+"\">");
			 checkedData.append("</td>");
			 checkedData.append("<td>");
      		
			 checkedData.append("<select class=\"selBoxTTS\" id=\"minmax"+i+"\"  name=\"minmax"+i+"\" >");
      		
      		if("MIN".equals(strMinMax)){
      			checkedData.append( "<option value=\"MIN\""+defSelect+">MIN</option><option value=\"MAX\">MAX</option>");	  
      		}else {
      			checkedData.append( "<option value=\"MAX\""+defSelect+">MAX</option><option value=\"MIN\">MIN</option>");	  
      		}
      		
      		checkedData.append("</select>");
      		checkedData.append("</td>");
      		checkedData.append("</tr>");
      		checkedData.append("</table>");
      		checkedData.append("</td>");	
      		
      		checkedData.append("</tr>");	
	      }
	        		
              		
	       }//for(int i=0;i<rowCount;i++){		                			
	        	if(!"yes".equals(pageContext.getRequest().getAttribute("providerSearch"))){
	        		out.print(checkedData);	
	        	}
	        	
	        	out.print(unCheckedData);
	        	
	        	
							}else{
								
								HashMap<String, CountryCopayDetails>	countriesCopayDetails=(HashMap<String, CountryCopayDetails>)frmProviderCopayRules.get("XmlConrtiesCopayDetails");
			                    if(countriesCopayDetails==null)countriesCopayDetails=new HashMap<>(); 
						
                            	  String strChecked="";
					        		String strTextBoxTS="textBoxTS";
					        		String  strReadonly=" readonly ";
					        		
					        		String strCopay="";
					        		String strFlatAmt="";
					        		String strCurrency="AED";
					        		String strMinMax="";
					        		
					        	for(int i=0;i<rowCount;i++){
					                			               			
					     ProviderDetails providerDetails=alProviderDetails.get(i);
					     
					     CountryCopayDetails countryLocation= countriesCopayDetails.get(providerDetails.getProviderCountryID());
					            
					      if(countryLocation==null||"N".equals(providerDetails.getCheckStatus())){
					    	  strTextBoxTS="textBoxTS";
					    	  strChecked="";
					    	  strReadonly=" readonly ";
					    	  
					    	  strCopay="";
					    	  strFlatAmt="";
					    	  strCurrency="AED";
					    	  strMinMax="";
					      }	else{
					    	  if(!"yes".equals(pageContext.getRequest().getAttribute("providerSearch"))){
					        		
					        	
					    	  strTextBoxTS="CurrenttextBoxTS";
					    	  strChecked=" checked ";
					    	  strReadonly="";
					    	  
					    	  strCopay=countryLocation.getCopay();
					    	  strFlatAmt=countryLocation.getFlatamount();
					    	  strCurrency=countryLocation.getCurrency();
					    	  strMinMax=countryLocation.getMinmax();
					    	  }
					      }
					        		
					        		  out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
					        		  
				        			  	out.print("<td style='width:5%;' align='center'><input type=\"checkbox\" class=\"copayChBox\" onclick=\"checkProvider(this);\" "+strChecked+" name=\"checkedIndex\" value=\""+i+"\"><input type=\"hidden\" name=\"providerSeqID"+i+"\" value=\""+providerDetails.getProviderSeqID()+"\"></td>");
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
				                		out.print("<input onkeyup=\"isNumeric(this);\" "+strReadonly+" value=\""+strCopay+"\" class=\""+strTextBoxTS+"\" type=\"text\"  id=\"copay"+i+"\" name=\"copay"+i+"\" >");
				                		out.print("%(or)");
				                		out.print("</td>");
				                		out.print("<td>");
				                		out.print("<input onkeyup=\"isNumeric(this);\" "+strReadonly+" value=\""+strFlatAmt+"\" class=\""+strTextBoxTS+"\" type=\"text\"  id=\"flatamount"+i+"\" name=\"flatamount"+i+"\">");
				                		out.print("</td>");
				                		out.print("<td>");
				                		out.print("<input readonly value=\"AED\" class=\""+strTextBoxTS+"\" type=\"text\" name=\"currency"+i+"\">");
				                		out.print("</td>");
				                		out.print("<td>");
				                		
				                		out.print("<select class=\"selBoxTTS\" id=\"minmax"+i+"\"  name=\"minmax"+i+"\" >");
				                		
				                		if("MIN".equals(strMinMax)){
				                			out.print( "<option value=\"MIN\""+defSelect+">MIN</option><option value=\"MAX\">MAX</option>");	  
				                		}else {
				                			out.print( "<option value=\"MAX\""+defSelect+">MAX</option><option value=\"MIN\">MIN</option>");	  
				                		}
				                		
				                		out.print("</select>");
				                		out.print("</td>");
				                		out.print("</tr>");
				                		out.print("</table>");
				                		out.print("</td>");	
				                		
				                		out.print("</tr>");	
				                		
					        		}//for(int i=0;i<rowCount;i++){		                			
							
				     }//else
			out.print("</table>");
			}//else if("PRO".equals(strLocationType)){
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
	
}//end of DiagnosisDetails class 
