/**
 
 * @ (#) OnlinePreAuthHistory.java Sep 27,2007
 * Project : TTK HealthCare Services
 * File : OnlinePreAuthHistory.java
 * Author : Balaji C R B
 * Company : Span Systems Corporation
 * Date Created : MSep 27,2007
 *
 * @author : Balaji C R B
 * Modified by :
 * Modified date :
 * Reason :
 */

package com.ttk.common.tags.onlineforms;

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

//import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 *  This class builds the PreAuth Details Pre-Auth History
 */
public class OnlinePreAuthHistory extends TagSupport{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
//	private static Logger log = Logger.getLogger( OnlinePreAuthHistory.class );
	
	@XmlElementWrapper()
	public int doStartTag() throws JspException{
		try
		{
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
       	 	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
				getAttribute("UserSecurityProfile");
			Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
			/*log.info("hai trying to print xml");
			log.info(historyDoc.asXML());*/
//			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			JspWriter out = pageContext.getOut();//Writer object to write the file
			
			Element preAuthElement=null;
			//Element preAuthShrtFallElement=null;
			List  preauthorizationdetailslist=null;
			//List  preautShrtFalllist=null;
			if(historyDoc != null)
			{	
				if(!((List)historyDoc.selectNodes("//preauthorizationdetails")).isEmpty())
				{
					preauthorizationdetailslist = (List)historyDoc.selectNodes("//preauthorizationdetails");
					for(int i=0;(preauthorizationdetailslist!=null && i<preauthorizationdetailslist.size());i++)
					{
						preAuthElement = (Element)preauthorizationdetailslist.get(i);						
						out.print("<fieldset>");
						out.print("<legend>Cashless Details</legend>");
						out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						if(preAuthElement.valueOf("@shortfall").equalsIgnoreCase("Y"))
						{
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("<a href=\"#\" onClick=\"javascript:online_Preauth_shortfall('"+preAuthElement.valueOf("@seq_id")+"');\"><font size=\"2\"><b>Shortfall</b></font></a>");
							out.print("</td>");
							out.print("<td width=\"25%\" class=\"textLabelBold\">");
							out.print("</td>");
							out.print("<td class=\"formLabel\" width=\"22%\">");
							out.print("</td>");
							out.print("<td class=\"textLabel\" width=\"31%\">");
							out.print("</td>");
						}//end of  if(preAuthElement.valueOf("@shortfall").equalsIgnoreCase("Y"))
						else
						{
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("</td>");
						}
						if(preAuthElement.valueOf("@statustypeid").equalsIgnoreCase("REJ"))
						{
							if(!userSecurityProfile.getLoginType().equals("B"))
            				{
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("<a href=\"#\" onClick=\"javascript:online_Rejection_clauses_pre('"+preAuthElement.valueOf("@seq_id")+"');\"><font size=\"2\"><b>Rejection Clauses</b></font></a>");
							out.print("</td>");
            				}
						}//end of  if(claimElement.valueOf("@shortfall").equalsIgnoreCase("Y"))
						else
						{
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("</td>");
						}	
						out.print("</tr>");
						out.print("</table>");
						
						out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">");
						out.print("Cashless No.:");
						out.print("</td>");
						out.print("<td width=\"30%\" class=\"textLabelBold\">");
						out.print(preAuthElement.valueOf("@preauthnumber"));
						out.print("</td>");
						out.print("<td class=\"formLabel\" width=\"22%\">");
						out.print("Received Date / Time:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(preAuthElement.valueOf("@recieveddate"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Admission Date / Time:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(preAuthElement.valueOf("@admissiondate"));
						out.print("</td>");
						out.print("<td class=\"formLabel\">Probable Date of Discharge:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(preAuthElement.valueOf("@dateofdischarge"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Provider Name:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(preAuthElement.valueOf("@hospitalname"));
						out.print("</td>");
						out.print("<td class=\"formLabel\">Authorization No:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(preAuthElement.valueOf("@authoriztionnumber"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Requested Amt. (Rs.):");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(preAuthElement.valueOf("@requestedamount"));
						out.print("</td>");
						out.print("<td height=\"20\" class=\"formLabel\">Approved Amt. (Rs.):");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(preAuthElement.valueOf("@approvedamount"));
						out.print("</td>");
						out.print("</tr>");
						
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Approval Status:");
						out.print("</td>");
						out.print("<td colspan=\"3\" class=\"textLabel\">");
						out.print(preAuthElement.valueOf("@status"));
						out.print("</td>");
						out.print("</tr>");
						
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Remarks:");
						out.print("</td>");
						out.print("<td colspan=\"3\" class=\"textLabel\">");
						out.print(preAuthElement.valueOf("@remarks"));
						out.print("</td>");
						out.print("</tr>");
						out.print("</table>");
						out.print("</fieldset>");
					}//end of if
				}//end of if(doc != null)
				else
				{
					out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
					out.print("<tr>");
					out.print("<td width=\"22%\" height=\"25\" class=\"formLabelBold\">");
					out.print("<font size=\"2\" color=\"#0C48A2\">No Cashless details exist for this enrollment id.</font>");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
				}//end of else
			}
		}//end of try block
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()
	public int doEndTag() throws JspException {
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}//end of class PreAuthHistory
