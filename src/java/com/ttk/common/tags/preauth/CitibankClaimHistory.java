/**
 * @ (#) CitibankClaimHistory Sep 8th, 2008
 * Project : TTK HealthCare Services
 * File : CitibankClaimHistory
 * Author : Chandrasekaran J
 * Company : Span Systems Corporation
 * Date Created : Sep 8th, 2008
 *
 * @author : Chandrasekaran J
 * Modified by :
 * Modified date :
 * Reason :
*/
package com.ttk.common.tags.preauth;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.dom4j.Document;
import org.dom4j.Element;

public class CitibankClaimHistory extends TagSupport
{
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	public int doStartTag() throws JspException
	{
		try
		{
			Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
	        JspWriter out = pageContext.getOut();//Writer object to write the file
	        Element claimElement=null;
	        List  claimList=null;
	        if(historyDoc != null)
	        {
	        	if(!((List)historyDoc.selectNodes("/citiclmhistory")).isEmpty())
	        	{
	        		claimList = (List)historyDoc.selectNodes("/citiclmhistory/claimdetails");
	                if(claimList!=null&& claimList.size() > 0 )
	                {
	                	claimElement = (Element)claimList.get(0);
	                	out.print("<fieldset>");
	                		out.print("<legend>Citibank Claim History</legend>");
	                		out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                			out.print("<tr>");
	                				out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
	                					out.print("Enrollment No.:");
	                				out.print("</td>");
	                				out.print("<td width=\"25%\" class=\"textLabelBold\">");
                                    	out.print(claimElement.valueOf("@enrno"));
                                    out.print("</td>");	
                                    out.print("<td class=\"formLabel\" width=\"22%\">");
                                    	out.print("Beneficiary Name:");
		                            out.print("</td>");
		                            out.print("<td class=\"textLabel\" width=\"31%\">");
		                            	out.print(claimElement.valueOf("@insurename"));
		                            out.print("</td>");
	                			out.print("</tr>");
	                			out.print("<tr>");
                                	out.print("<td height=\"25\" class=\"formLabel\">Certificate No.:");
                                	out.print("</td>");
                                	out.print("<td class=\"textLabel\">");
                                		out.print(claimElement.valueOf("@certno"));
                                	out.print("</td>");
                                	out.print("<td height=\"20\" class=\"formLabel\">Customer Code:");
                                	out.print("</td>");
                                	out.print("<td class=\"textLabel\">");
                                		out.print(claimElement.valueOf("@custcode"));
                                	out.print("</td>");
                                out.print("</tr>");
                                out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Scheme Name:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(claimElement.valueOf("@scheme"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Claim No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(claimElement.valueOf("@claimno"));
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Claim File No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(claimElement.valueOf("@fileno"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Claim Date:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(claimElement.valueOf("@claimyear"));
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">File Ref No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(claimElement.valueOf("@filerefno"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Settled Amt.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(claimElement.valueOf("@amtsettled"));
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Date of Loss:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(claimElement.valueOf("@dateofloss"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print("");
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Disease:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(claimElement.valueOf("@disease"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print("");
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Remarks:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(claimElement.valueOf("@remarks"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print("");
	                            	out.print("</td>");
                            	out.print("</tr>");
	                		out.print("</table>");
	                	out.print("</fieldset>");
	                }//end of if(claimList!=null&& claimList.size() > 0 )
	        	}//end of if(!((List)historyDoc.selectNodes("/citiclmhistory")).isEmpty())
	        }//end of if(historyDoc != null)
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
}//end of CitibankClaimHistory class
