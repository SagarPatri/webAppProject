/**
 * @ (#) BeneficiaryInformation.java Feb 2, 2006
 * Project       : TTK HealthCare Services
 * File          : BeneficiaryInformation.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 2, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.enrollment;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.TTKCommon;
import com.ttk.common.security.Cache;
import com.ttk.common.tags.PersonalInformation;
import com.ttk.dto.common.CacheObject;

/**
 *  This class builds the Beneficiary Information of policies
 */

public class BeneficiaryInformation extends TagSupport
{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( PersonalInformation.class );

	public int doStartTag() throws JspException {
	   try
	   {

		   	log.debug("Inside BeneficiaryInformation TAG :");
	    	JspWriter out = pageContext.getOut();//Writer object to write the file
	    	HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
	    	//get the reference of the frmPolicyDetails to load the Beneficiary  information
	    	DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
	    	String strViewmode=" Disabled ";
	    	String strIndividualPolicy="Individual Policy";
	    	String strIndPolasGroup="Ind. Policy as Group";
	    	CacheObject cacheObject = null;
	    	//	get the required Cache Data to load select boxes
	    	ArrayList alRelationshipCode= Cache.getCacheObject("relationshipCode");
	    	//	get the Active Sublink
	    	String strActiveSubLink=TTKCommon.getActiveSubLink(request);

	    	String strBeneficiaryName=(String)frmPolicyDetails.get("beneficiaryName");
	    	String strRelationTypeID=(String)frmPolicyDetails.get("relationTypeID");

	    	if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
        	{
        		strViewmode="";
        	}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))


	    	if(strActiveSubLink.equals(strIndividualPolicy)||strActiveSubLink.equals(strIndPolasGroup))
	    	{
	    		out.print("<fieldset><legend>Beneficiary Information</legend>");
	    				out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
	    					out.print("<tr>");
	    						out.print("<td width=\"21%\" CLASS=\"formLabel\">");
	    							out.print("Beneficiary Name:");
	    						out.print("</td>");

	    						out.print("<td width=\"33%\">");
	    							out.print("<input type=\"text\" name=\"beneficiaryName\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strBeneficiaryName)+"' maxlength=\"60\" class=\"textBox textBoxLarge\""+strViewmode+">");
			        			out.print("</td>");

			        			out.print("<td width=\"23%\" CLASS=\"formLabel\">");
			        				out.print("Relationship: ");
    						    out.print("</td>");
    						    out.print("<td width=\"23%\">");
	    						    if(alRelationshipCode != null && alRelationshipCode.size() > 0)
					                {
					        			out.print("<select name=\"relationTypeID\" CLASS=\"selectBox selectBoxMedium\""+strViewmode+">");
					        			out.print("<option value=\"\">Select from list</option>");
						        			 for(int i=0; i < alRelationshipCode.size(); i++)
						                     {
						        			 	cacheObject = (CacheObject)alRelationshipCode.get(i);
						        			 	out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strRelationTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
						                     }//end of for(int i=0; i < alPrefix.size(); i++)
					        			out.print("</select>");
					                }//end of if(alStateCode != null && alStateCode.size() > 0)
					        		else if(alRelationshipCode != null && alRelationshipCode.size() == 0)
				       	            {
					        			out.print("<select name=\"relationTypeID\" CLASS=\"selectBox selectBoxMedium\">");
					        			out.print("<option value=\"\">Select from list</option>");
				       	                out.print("</select>");
				       	            }//end of else if(alStateCode != null && alStateCode.size() == 0)
    						    out.print("</td>");

	    					out.print("</tr>");

	    				out.print("</table>");
	    		out.print("</fieldset>");
	    	}//end of if(strActiveSubLink.equals(strIndividualPolicy)||strActiveSubLink.equals(strIndPolasGroup))
	   }//end of try
	   catch(Exception exp)
       {
           exp.printStackTrace();
       }//end of catch block
       return SKIP_BODY;
	}//end of doStartTag
}//end of BeneficiaryInformation