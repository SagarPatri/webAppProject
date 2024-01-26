/**
 * @ (#) DomiciliaryTreatment.java Feb 3, 2006
 * Project       : TTK HealthCare Services
 * File          : DomiciliaryTreatment.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 3, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.enrollment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import com.ttk.common.TTKCommon;


public class DomiciliaryTreatment extends TagSupport
{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( DomiciliaryTreatment.class );

	public int doStartTag() throws JspException
	{
	   try
	   {
			log.debug("Inside InsuredAddressInformation TAG :");
	    	JspWriter out = pageContext.getOut();//Writer object to write the file
	    	HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
	    	String strActiveSubLink=TTKCommon.getActiveSubLink(request);
	    	String strCorporatePolicy="Corporate Policy";

	    	if(strActiveSubLink.equals(strCorporatePolicy))
	    	{
	    		out.print("<fieldset><legend>Domiciliary Treatment</legend>");
		    		out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
		    			out.print("<tr>");
		    				out.print("<td width=\"21%\" nowrap CLASS=\"formLabel\">");
		    					out.print("Total Sum Limit (Rs): ");
		    				out.print("</td>");

		    				out.print("<td width=\"79%\" nowrap CLASS=\"textLabelBold\">");
		    					out.print("600000.00 (Rupees Six Lakh Only)");
		    				out.print("</td>");
		    			out.print("</tr>");
		    			out.print("<tr>");
		    				out.print("<td CLASS=\"formLabel\">");
		    					out.print("Overall Family Limit:");
		    				out.print("</td>");
		    				out.print("<td CLASS=\"textLabelBold\">");
		    					out.print("1200.00 (Rupees Twelve Hundred Only)");
		    				out.print("</td>");
		    			out.print("</tr>");
		    		out.print("</table>");
	    		out.print("</fieldset>");
	    	}//end of if(strActiveSubLink.equals(strCorporatePolicy))
	   }//end of try
	   catch(Exception exp)
       {
           exp.printStackTrace();
       }//end of catch block
       return SKIP_BODY;
	}//end of doStartTag
}//end of DomiciliaryTreatment