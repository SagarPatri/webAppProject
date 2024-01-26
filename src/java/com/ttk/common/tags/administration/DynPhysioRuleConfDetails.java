/**
 * @ (#) DynPhysioRuleConfDetails.java June 27, 2015
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

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.dom4j.Node;

import com.ttk.dto.administration.InvestigationRuleVO;

public class DynPhysioRuleConfDetails extends TagSupport
{
	/**CountryCopayDetails
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( ConsInvestConfDetails.class );
	public int doStartTag() throws JspException
	{
		try
		{
		
			JspWriter out = pageContext.getOut();//Writer Object to write the File		
			String gridOddRow="'gridOddRow'";
		    String gridEvenRow="'gridEvenRow'";
			HttpSession session=pageContext.getSession();
			String delImage="<img src='/ttk/images/DeleteIcon.gif' alt='Delete Activity Details' width='12' height='12' border='0'>";
			ArrayList<InvestigationRuleVO> alInvestRules=(ArrayList<InvestigationRuleVO>)session.getAttribute("PhysiotherapyRuleDetails");

			out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:98%;height:auto;'");
			out.print("<tr>");
			out.print("<td align='center' style='width:2%;' class='gridHeader'>S.NO</td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'><table><tr><th>Provider</th></tr><tr><th>Facility Type</th></tr></table></td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'><table><tr><th>Pre-Approval</th></tr><tr><th>Required</th></tr></table></td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'><table><tr><th>Pre-Approval</th></tr><tr><th>Limit(AED)</th></tr></table></td>");
			
			out.print("<td align='center' style='width:10%;' class='gridHeader'><table><tr><th>Per-Policy</th></tr><tr><th>Limit</th></tr></table></td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'><table><tr><th>No. of sessions</th></tr><tr><th>allowed per-policy</th></tr></table></td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'>Limit Per Session</td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'>Copay/Deductible</td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'>");
			out.print("<table><tr><th>Per Policy Copay/</th></tr><tr><th>Deductable Limit</th></tr></table></td>");
			out.print("<td align='center' style='width:10%;' class='gridHeader'>");
			out.print("<table><tr><th>Per Session Copay/</th></tr><tr><th>Deductable Limit</th></tr></table></td>");
			out.print("<td align='center' style='width:3%;' class='gridHeader'>Delete</td>");
			out.print("</tr>");
			int row=0;
		if(alInvestRules!=null){
	        	for(InvestigationRuleVO investigationRuleVO:alInvestRules){
	        		   out.print("<tr class="+(row%2==0?gridEvenRow:gridOddRow)+">");
	        		   Node fieldNode=investigationRuleVO.getStaticDoc().selectSingleNode("/Physiotherapy/Fields");
	                String strPreAppYN=("Y".equals(fieldNode.valueOf("@pre-appr-yn"))?"YES":"NO");
	                
	        		 out.print("<td style='width:2%;' align='center'>"+(row+1)+"&nbsp;<a href=\"#\" onclick=\"editPhysioConf('"+row+"');\">Edit</a></td>");
	        		 
	                 String strPTHiddenBox="<input type=\"hidden\" id=\"providerTypesID"+row+"\" value=\""+investigationRuleVO.getProviderTypes()+"\">";
	                 
	                 out.print("<td style='width:10%;' align='center'><a href=\"#\" onclick=\"selectProviderTypes('providerTypesID"+row+"','V');\">View</a>"+strPTHiddenBox+"</td>");
	                
	        		 out.print("<td style='width:10%;' align='center'>"+strPreAppYN+"</td>");
	        		 out.print("<td style='width:10%;' align='center'>"+fieldNode.valueOf("@pre-appr-limit")+"</td>");
	               
	                 out.print("<td style='width:10%;' align='center'>"+fieldNode.valueOf("@per-policy-limit")+"</td>");
	                 out.print("<td style='width:10%;' align='center'>"+fieldNode.valueOf("@noof-sess-per-policy")+"</td>");
	                 out.print("<td style='width:10%;' align='center'>"+fieldNode.valueOf("@per-sess-limit")+"</td>");
	                 out.print("<td style='width:10%;' align='center'><a href=\"#\" onclick=\"viewPhysiotStaticConfDtls('"+row+"','CPD');\">View</a></td>");
	                 
	                 out.print("<td style='width:10%;' align='center'>"+fieldNode.valueOf("@per-plcy-cd-limit")+"</td>");
	                 out.print("<td style='width:10%;' align='center'>"+fieldNode.valueOf("@per-sess-cd-limit")+"</td>");
	                 
	                 out.print("<td style='width:3%;' align='center'><a href=\"#\" onclick=\"deletePhysioDtls('"+row+"');\">"+delImage+"</a></td>");
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
	
	
}//end of ConsInvestConfDetails class 
