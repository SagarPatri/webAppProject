/**
 * @ (#) CitibankEnrolHistory.java Sep 8th, 2008
 * Project : TTK HealthCare Services
 * File : CitibankEnrolHistory.java
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

//import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

public class CitibankEnrolHistory extends TagSupport
{
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( CitibankEnrolHistory.class );
	public int doStartTag() throws JspException
	{
		try
		{
			Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
	        JspWriter out = pageContext.getOut();//Writer object to write the file
	        Element enrollElement=null;
	        List  enrollList=null;
	        if(historyDoc != null)
	        {
	        	if(!((List)historyDoc.selectNodes("/enrollhistory")).isEmpty())
	        	{
	        		enrollList = (List)historyDoc.selectNodes("/enrollhistory/enrolldetails");
	                if(enrollList!=null&& enrollList.size() > 0 )
	                {
	                	enrollElement = (Element)enrollList.get(0);
	                	out.print("<fieldset>");
	                		out.print("<legend>Citibank Enroll History</legend>");
	                		out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                			out.print("<tr>");
	                				out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
	                					out.print("Member Name:");
	                				out.print("</td>");
	                				out.print("<td width=\"25%\" class=\"textLabelBold\">");
                                    	out.print(enrollElement.valueOf("@claimantname"));
                                    out.print("</td>");	
                                    out.print("<td class=\"formLabel\" width=\"22%\">");
                                    	out.print("Applicant Name:");
		                            out.print("</td>");
		                            out.print("<td class=\"textLabel\" width=\"31%\">");
		                            	out.print(enrollElement.valueOf("@applicantname"));
		                            out.print("</td>");
	                			out.print("</tr>");
	                			out.print("<tr>");
                                	out.print("<td height=\"25\" class=\"formLabel\">Gender:");
                                	out.print("</td>");
                                	out.print("<td class=\"textLabel\">");
                                		out.print(enrollElement.valueOf("@gender"));
                                	out.print("</td>");
                                	out.print("<td height=\"20\" class=\"formLabel\">Relationship:");
                                	out.print("</td>");
                                	out.print("<td class=\"textLabel\">");
                                		out.print(enrollElement.valueOf("@relationship"));
                                	out.print("</td>");
                                out.print("</tr>");
                                out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">DOB:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@claimantdob"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Account No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@acctno"));
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Certificate No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@certificateno"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Old Certificate No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@oldcertno"));
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Customer Code:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@custcode"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Old Order No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@oldordnum"));
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Order No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@ordnum"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">New Order No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@newordnum"));
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">MC Policy:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@mcpolicy"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">MC Plan:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@mcplan"));
	                            	out.print("</td>");
                            	out.print("</tr>");
                            	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">MC Premium:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@mcpremium"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">PA Policy:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@papolicy"));
	                            	out.print("</td>");
                            	out.print("</tr>");
	                        	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">PA Plan:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@paplan"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">PA Premium:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@papremium"));
	                            	out.print("</td>");
	                        	out.print("</tr>");
	                        	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Suminsured:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@pasumassured"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Card Number:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@cardnum"));
	                            	out.print("</td>");
	                        	out.print("</tr>");
	                        	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">PED:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@ped"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print("");
	                            	out.print("</td>");
                            	out.print("</tr>");
	                        	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Address1:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@add1"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Address2:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@add2"));
	                            	out.print("</td>");
	                        	out.print("</tr>");
	                        	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Address3:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@add3"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Address4:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@add4"));
	                            	out.print("</td>");
	                        	out.print("</tr>");
	                        	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">City:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@city"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Pincode:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@pincode"));
	                            	out.print("</td>");
	                        	out.print("</tr>");
	                        	out.print("<tr>");
	                            	out.print("<td height=\"25\" class=\"formLabel\">Res. Phone No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@restel"));
	                            	out.print("</td>");
	                            	out.print("<td height=\"20\" class=\"formLabel\">Office Phone No.:");
	                            	out.print("</td>");
	                            	out.print("<td class=\"textLabel\">");
	                            		out.print(enrollElement.valueOf("@offtel"));
	                            	out.print("</td>");
	                        	out.print("</tr>");
	                		out.print("</table>");
	                	out.print("</fieldset>");
	                }//end of if(enrollList!=null&& enrollList.size() > 0 )
	        	}//end of if(!((List)historyDoc.selectNodes("/enrollhistory")).isEmpty())
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
}//end of CitibankEnrolHistory class 
