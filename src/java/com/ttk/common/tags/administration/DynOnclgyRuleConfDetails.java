/**
 * @ (#) DynAltTmtRuleConfDetails.java May 22, 2018
 * Project : ProjectX
 * File : DynAltTmtRuleConfDetails.java
 * Author : Hare Govind
 * Company :Vidal
 * Date Created : May 22, 2018
 *
 * @author :  Hare Govind
 * Modified by :
 * Modified date :
 * Reason :
*/

package com.ttk.common.tags.administration;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.dom4j.Node;

import com.ttk.dto.administration.InvestigationRuleVO;

public class DynOnclgyRuleConfDetails extends TagSupport{
	
	private static final long serialVersionUID = 1L;
	
	public int doStartTag() throws JspException
	{
		try
		{
		
			JspWriter out = pageContext.getOut();//Writer Object to write the File		
			String gridOddRow="'gridOddRow'";
		    String gridEvenRow="'gridEvenRow'";
			HttpSession session=pageContext.getSession();
			String delImage="<img src='/ttk/images/DeleteIcon.gif' alt='Delete Activity Details' width='12' height='12' border='0'>";
			ArrayList<InvestigationRuleVO> alInvestRules=(ArrayList<InvestigationRuleVO>)session.getAttribute("OnclgyRuleDetails");
			

			out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:98%;height:auto;'");
			out.print("<tr>");
			out.print("<td align='center' style='width:2%;' class='gridHeader'>S.NO</td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Claim</th></tr><tr><th>Type</th></tr></table></td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Network</th></tr><tr><th>Type</th></tr></table></td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Benefit</th></tr><tr><th>Types</th></tr></table></td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Pre-approval</th></tr><tr><th>Required</th></tr></table></td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Pre-approval</th></tr><tr><th>limit(AED)</th></tr></table></td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Geographical</th></tr><tr><th>Location</th></tr></table></td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'>Country</td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'>Emirates</td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Provider</th></tr><tr><th>Type</th></tr></table></td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Provider</th></tr><tr><th>Facility Type</th></tr></table></td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Per Policy Limit</th></tr><tr><th>(AED)</th></tr></table></td>");
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Per Claim Limit</th></tr><tr><th>(AED)</th></tr></table></td>");
			
			out.print("<td align='center' style='width:5%;' class='gridHeader'><table><tr><th>Copay/</th></tr><tr><th>Deductible</th></tr></table></td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'>");
			out.print("<table><tr><th>Per Policy Copay/</th></tr><tr><th>Deductable Limit</th></tr></table></td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'>");
			out.print("<table><tr><th>Per Claim Copay/</th></tr><tr><th>Deductable Limit</th></tr></table></td>");
			out.print("<td align='center' style='width:3%;' class='gridHeader'>Delete</td>");
			out.print("</tr>");
			int row=0;
		if(alInvestRules!=null){
	        	for(InvestigationRuleVO investigationRuleVO:alInvestRules){
	        		   out.print("<tr class="+(row%2==0?gridEvenRow:gridOddRow)+">");
	        		   Node fieldNode=investigationRuleVO.getStaticDoc().selectSingleNode("/Oncology/Fields");
	                String strPreAppYN=("Y".equals(fieldNode.valueOf("@pre-appr-yn"))?"YES":"NO");
	                
	        		 out.print("<td style='width:2%;' align='center'>"+(row+1)+"&nbsp;<a href=\"#\" onclick=\"editOnclgyConf('"+row+"');\">Edit</a></td>");
	                 
	        		 out.print("<td style='width:5%;' align='center'>"+investigationRuleVO.getClaimType()+"</td>");
	       	         out.print("<td style='width:5%;' align='center'>"+investigationRuleVO.getNetworkYN()+"</td>");
	       	      
                     String strEncHiddenBox="<input type=\"hidden\" id=\"encounterTypesID"+row+"\" value=\""+investigationRuleVO.getEncounterTypes()+"\">";
	                 
	                 out.print("<td style='width:5%;' align='center'><a href=\"#\" onclick=\"selectEncounterTypes('encounterTypesID"+row+"','V');\">View</a>"+strEncHiddenBox+"</td>");
	                
	                 out.print("<td style='width:5%;' align='center'>"+strPreAppYN+"</td>");
	                 out.print("<td style='width:5%;' align='center'>"+fieldNode.valueOf("@pre-appr-limit")+"</td>");
	                 

	                    String strGeoHiddenBox="<input type=\"hidden\" id=\"geoLocationID"+row+"\" value=\""+investigationRuleVO.getGeoLocationID()+"\">";
		                 
		                 out.print("<td style='width:5%;' align='center'><a href=\"#\" onclick=\"openGeoLocation('geoLocationID"+row+"','V');\">View</a>"+strGeoHiddenBox+"</td>");
		                
		                 String strCountryHiddenBox="<input type=\"hidden\" id=\"countryID"+row+"\" value=\""+investigationRuleVO.getCountryIDs()+"\">";
		                 
		                 out.print("<td style='width:5%;' align='center'><a href=\"#\" onclick=\"openGeoCountryList('countryID"+row+"','V','"+investigationRuleVO.getGeoLocationID()+"');\">View</a>"+strCountryHiddenBox+"</td>");
		               
		                 String strEmirateHiddenBox="<input type=\"hidden\" id=\"emiratesID"+row+"\" value=\""+investigationRuleVO.getEmiratesIDs()+"\">";
		                 
		                 out.print("<td style='width:5%;' align='center'><a href=\"#\" onclick=\"selectEmirates('emiratesID"+row+"','V');\">View</a>"+strEmirateHiddenBox+"</td>");
		                 out.print("<td style='width:5%;' align='center'>"+investigationRuleVO.getProviderTypes()+"</td>");
	                 //openList('providerTypesID','ProviderTypes')
	                 String strPTHiddenBox="<input type=\"hidden\" id=\"providerTypesID"+row+"\" value=\""+investigationRuleVO.getProviderTypesID()+"\">";
	                 
	                 out.print("<td style='width:5%;' align='center'><a href=\"#\" onclick=\"selectProviderTypes('providerTypesID"+row+"','V');\">View</a>"+strPTHiddenBox+"</td>");
	                
	                 out.print("<td style='width:5%;' align='center'>"+fieldNode.valueOf("@per-policy-limit")+"</td>");
	                 out.print("<td style='width:5%;' align='center'>"+fieldNode.valueOf("@per-claim-limit")+"</td>");
	               
	                 out.print("<td style='width:5%;' align='center'><a href=\"#\" onclick=\"viewOnclgyStaticConfDtls('"+row+"','CPD');\">View</a></td>");
	                 
	             
	                 out.print("<td style='width:10%;' align='center'>"+fieldNode.valueOf("@per-plcy-cd-limit")+"</td>");
	                 out.print("<td style='width:10%;' align='center'>"+fieldNode.valueOf("@per-clm-cd-limit")+"</td>");
	                 
	                 out.print("<td style='width:3%;' align='center'><a href=\"#\" onclick=\"deleteOnclgyDtls('"+row+"');\">"+delImage+"</a></td>");
	                 row++; 
	        	}
		  
		}//if(benefitConditions!=null){
		out.print("</table>");
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
	

}
