/**
 * @ (#) ProviderCopayDetailsDisplay.java June 27, 2017
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
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.DynaActionForm;

import com.ttk.common.TTKCommon;
import com.ttk.xml.administration.Condition;

public class ProviderCopayCondionDetails extends TagSupport
{
	/**
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
		    String imagePath="<img src='/ttk/images/DeleteIcon.gif' alt='Delete Condition Details'width='16' height='16' border='0'>";
		    
		    HashMap<String,ArrayList<Condition>> allBenefits=(HashMap<String,ArrayList<Condition>>)pageContext.getSession().getAttribute("ProviderRulesXmlConditions");
		   
		    DynaActionForm frmProviderCopayRules=(DynaActionForm)pageContext.getSession().getAttribute("frmProviderCopayRules");
	      
		    String strBenefitType=frmProviderCopayRules.getString("benefitType");
		    String strbenefittypeflag=frmProviderCopayRules.getString("benefittypeflag");
		    strBenefitType=TTKCommon.checkNull(strBenefitType);
		    
		    out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:99%;height:auto;'");
			
		    out.print("<tr>");
			out.print("<th align='center' style='width:5%;' class='gridHeader'>S.NO</th>");
			if("Y".equals(strbenefittypeflag))
			out.print("<th align='center' style='width:10%;' class='gridHeader' title=\"Encounter Type\">Encounter Type</th>");
			out.print("<th align='center' style='width:10%;' class='gridHeader' title=\"Claim Type\">Claim Type</th>");
			out.print("<th align='center' style='width:10%;' class='gridHeader' title=\"Network Type\">Network Type</th>");
			out.print("<th align='center' style='width:10%;' class='gridHeader' title=\"Provider Type\">Provider Type</th>");
			out.print("<th align='center' style='width:15%;' class='gridHeader' title=\"Provider Facility Type\">Provider Facility Type</th>");
			out.print("<th align='center' style='width:15%;' class='gridHeader' title=\"Geographical Location\">Geographical Location</th>");
			out.print("<th align='center' style='width:10%;' class='gridHeader' title=\"Country/Emirates\">Country/Emirates</th>");
			out.print("<th align='center' style='width:20%;' class='gridHeader' title=\"Provider Copay Details\">Provider Copay Details</th>");
			out.print("<th align='center' style='width:5%;' class='gridHeader' title=\"Delete\">Delete</th>");
		    out.print("</tr>");
	       
		    if(allBenefits!=null){
		    
		    ArrayList<Condition> providerRulesList=allBenefits.get(strBenefitType);
		    
	        if(providerRulesList != null&&providerRulesList.size()>0){
	        	int rowCount=providerRulesList.size()-1;
	        	int sNO=1;
	        	for(;0<=rowCount;rowCount--){
	                			               			
	        		Condition condition=providerRulesList.get(rowCount);
	        	
	        		        		
	                out.print("<tr class="+(sNO%2==0?gridEvenRow:gridOddRow)+">");
	                	
	                out.print("<td style='width:5%;' align='center'><a href=\"#\" onclick=\"onEditCondition('"+rowCount+"');\">"+sNO+" &nbsp;Edit</a></td>");
	            	if("Y".equals(strbenefittypeflag))
	                out.print("<td style='width:10%;' align='center'>"+condition.getEncountertypeId()+"</td>");
	                out.print("<td style='width:10%;' align='center'>"+condition.getClaimTypeDesc()+"</td>");
	                out.print("<td style='width:10%;' align='center'>"+condition.getNetworkYNDesc()+"</td>");
	                out.print("<td style='width:10%;' align='center'>"+condition.getProviderTypeDesc()+"</td>");
	                out.print("<td style='width:15%;' align='center'><a href=\"#\" onclick=\"onViewDetails('PFT','"+rowCount+"');\">View</a></td>");
	                out.print("<td style='width:15%;' align='center'><a href=\"#\" onclick=\"onViewDetails('GEO','"+rowCount+"');\">View</a></td>");
	                out.print("<td style='width:10%;' align='center'><a href=\"#\" onclick=\"onViewDetails('CON','"+rowCount+"');\">View</a></td>");
	                out.print("<td style='width:20%;' align='center'><a href=\"#\" onclick=\"onViewDetails('PRO','"+rowCount+"');\">View</a></td>");
	                
	                out.print("<td style='width:5%;' align='center'><a href='#' onClick=\"onDeleteCondition('"+rowCount+"');\">"+imagePath+"</a></td>");
	                
	                out.print("</tr>");	 
	                sNO++;
	        		
	        	}//for(;0<=rowCount;rowCount--){
	                		 
	        }
		    }//if(allBenefits!=null){
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
	
}//end of DiagnosisDetails class 
