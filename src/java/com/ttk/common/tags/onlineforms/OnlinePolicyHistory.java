/**
 * @ (#) OnlinePolicyHistory.java July 26,2007
 * Project       : TTK HealthCare Services
 * File          : OnlinePolicyHistory.java
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : July 26,2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.onlineforms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.dto.usermanagement.UserSecurityProfile;

public class OnlinePolicyHistory extends TagSupport{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( OnlinePolicyHistory.class );

    public int doStartTag() throws JspException{
        try
        {
           log.debug("## Inside OnlinePolicyHistory  ### ");
           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
           												request.getSession().getAttribute("UserSecurityProfile");
           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
           JspWriter out = pageContext.getOut();//Writer object to write the file
           List  policylist=null;
           Element policyElement=null;
           if(historyDoc != null)
           {
               if(!((List)historyDoc.selectNodes("/memberpolicyhistory")).isEmpty())
               {
            	   policylist= (List)historyDoc.selectNodes("/memberpolicyhistory/member");
            	   policyElement = (Element)policylist.get(0);
                      
            	   out.print("<fieldset>");
                       out.print("<legend>Policy Information</legend>");
                       out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
                       if(policylist!=null&& policylist.size() > 0 )
                       {

                           policyElement = (Element)policylist.get(0);
                           out.print("<tr>");
                               out.print("<td colspan=\"4\" class=\"formLabel\" height=\"4\">");
                               out.print("</td>");
                           out.print("</tr>");
                           out.print("<tr>");
                               out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
                                   out.print("Policy No.:");
                               out.print("</td>");
                               out.print("<td width=\"25%\" class=\"textLabelBold\">");
                                   out.print(policyElement.valueOf("@policynumber"));
                               out.print("</td>");
                               out.print("<td width=\"22%\" class=\"formLabel\">");
                               out.print("Insurance Company:");
                               out.print("</td>");
                               out.print("<td class=\"textLabelBold\" width=\"31%\">");
                               out.print(policyElement.valueOf("@inscompname"));
                               out.print("</td>");
                           out.print("</tr>");

                           out.print("<tr>");
                               out.print("<td height=\"25\" class=\"formLabel\">");
                               if(userSecurityProfile.getLoginType().equals("I"))
                               {
                            	   out.print("Beneficiary Name:");
                            	   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policyElement.valueOf("@insuredname"));
                                   out.print("</td>");
                               }
                               else
                               {
                            	   out.print("Corporate Name:");
                            	   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policyElement.valueOf("@groupname"));
                                   out.print("</td>");
                               }
                               out.print("<td height=\"25\" class=\"formLabel\">");
                                       out.print("Enrollment Id:");
                               out.print("</td>");
                               out.print("<td class=\"textLabel\">");
                               out.print(policyElement.valueOf("@enrollmentid"));
                               out.print("</td>");
                           out.print("</tr>");

                           out.print("<tr>");
                               out.print("<td height=\"25\" class=\"formLabel\">");
                                       out.print("Member Name:");
                               out.print("</td>");
                               out.print("<td class=\"textLabel\">");
                                   out.print(policyElement.valueOf("@memname"));
                               out.print("</td>");
                               out.print("<td height=\"25\" class=\"formLabel\">");
                                   out.print("Age:");
                               out.print("</td>");
                               out.print("<td class=\"textLabel\">");
                                   out.print(policyElement.valueOf("@age"));
                               out.print("</td>");
                            out.print("</tr>");

                           out.print("<tr>");
	                                   out.print("<td height=\"25\" class=\"formLabel\">");
	                                           out.print("Gender:");
	                                   out.print("</td>");
	                                   out.print("<td class=\"textLabel\">");
	                                       out.print(policyElement.valueOf("@gender"));
	                                   out.print("</td>");
	                                   out.print("<td height=\"25\" class=\"formLabel\">");
	                                   out.print("Member Status:");
	                                   out.print("</td>");
	                                   out.print("<td class=\"textLabel\">");
	                                   out.print(policyElement.valueOf("@memberstatus"));
	                                   out.print("</td>");
	                       out.print("</tr>");
	                       
	                       out.print("<tr>");
	                                   out.print("<td height=\"25\" class=\"formLabel\">");
	                          	 	   out.print("Address:");
	                          	 	   out.print("</td>");
	                                   out.print("<td colspan=\"3\" class=\"textLabel\">");
	                                 	   out.print(policyElement.valueOf("@add_1"));
	                                   out.print("</td>");
	                       out.print("</tr>");  
                            
                           out.print("<tr>");
                                out.print("<td height=\"25\" class=\"formLabel\">");
                                    out.print("Date of Inception:");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(policyElement.valueOf("@dateofinception"));
                                out.print("</td>");
                                out.print("<td height=\"25\" class=\"formLabel\">");
                                    out.print("Date of Exit:");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(policyElement.valueOf("@dateofexit"));
                                out.print("</td>");
                            out.print("</tr>");

                            out.print("<tr>");
                                out.print("<td nowrap height=\"25\" class=\"formLabel\">");
                                    out.print("Member Sum Insured (Rs.):");
                                out.print("</td>");
                                out.print("<td  class=\"textLabel\">");
                                    out.print(policyElement.valueOf("@suminsured"));
                                out.print("</td>");
                                out.print("<td nowrap height=\"25\" class=\"formLabel\">");
                                    out.print("Balance Sum Insured (Rs.):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(policyElement.valueOf("@availablesuminsured"));
                                out.print("</td>");
                            out.print("</tr>");
                            /*Changes As per Sum Insured issue*/
							 out.print("<tr>");
							out.print("<td nowrap height=\"25\" class=\"formLabel\">");
                                out.print("Maximum Liability Amount (Rs.):");
                            out.print("</td>");
                            out.print("<td  class=\"textLabel\">");
                                out.print(policyElement.valueOf("@maxrestrictamt"));
                            out.print("</td>");
                               out.print("<td nowrap height=\"25\" class=\"formLabel\">");
                            out.print("Zone Code:");
                        out.print("</td>");
                        out.print("<td colspan=\"4\" class=\"textLabel\">");
                        out.print(policyElement.valueOf("@zonecode"));
                        out.print("</td>"); 
                        out.print("</tr>");
							
							
                            /*out.print("<tr>");                            
                            out.print("<td nowrap height=\"25\" class=\"formLabel\">");
                            out.print("Diabetes Cover:");
                            out.print("</td>");
                            out.print("<td class=\"textLabel\">");
                            out.print(policyElement.valueOf("@diabetes_cover_yn"));
                            out.print("</td>");
                            out.print("<td nowrap height=\"25\" class=\"formLabel\">");
                            out.print("Hypertension Cover:");
                            out.print("</td>");
                            out.print("<td class=\"textLabel\">");

                            out.print(policyElement.valueOf("@hypertension_cover_yn"));
                            out.print("</td>");
                            out.print("</tr>");*/
                            
                           

                          }//end of if(policylist!=null&& policylist.size() > 0 )
                  out.print("</table>");
               out.print("</fieldset>");
               out.print("<fieldset>");
               out.print("<legend>Card Details</legend>");
               out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
               if(policylist!=null&& policylist.size() > 0 )
               {
                   policyElement = (Element)policylist.get(0);
                   out.print("<tr>");
                       out.print("<td colspan=\"4\" class=\"formLabel\" height=\"4\">");
                       out.print("</td>");
                   out.print("</tr>");
                   out.print("<tr>");
                       out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
                           out.print("No. of Card Printed:");
                       out.print("</td>");
                       out.print("<td width=\"25%\" class=\"textLabelBold\">");
                           out.print(policyElement.valueOf("@cardsprinted"));
                       out.print("</td>");
                       out.print("<td width=\"22%\" class=\"formLabel\">");
                       out.print("Card Printed Date:");
                       out.print("</td>");
                       out.print("<td class=\"textLabelBold\" width=\"31%\">");
                       out.print(policyElement.valueOf("@printeddate"));
                       out.print("</td>");
                   out.print("</tr>");

                   out.print("<tr>");
                       out.print("<td height=\"25\" class=\"formLabel\">");
                               out.print("Courier No.:");
                       out.print("</td>");
                       out.print("<td class=\"textLabel\">");
                           out.print(policyElement.valueOf("@courierNo"));
                       out.print("</td>");
                       out.print("<td height=\"25\" class=\"formLabel\">");
                           out.print("Dispatch Date:");
                       out.print("</td>");
                       out.print("<td class=\"textLabel\">");
                           out.print(policyElement.valueOf("@despatchdate"));
                       out.print("</td>");
                    out.print("</tr>");

                  }//end of if(policylist!=null&& policylist.size() > 0 )
          out.print("</table>");
       out.print("</fieldset>");
             }//end of if(!((List)historyDoc.selectNodes("//policydetails")).isEmpty())
       }//end of if(doc != null)
    }//end of try block
    catch(Exception exp)
    {
        exp.printStackTrace();
        log.debug("error occured in PolicyHistory Tag Library!!!!! ");
    }//end of catch block
    return SKIP_BODY;
}//end of doStartTag()
    public int doEndTag() throws JspException {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()
}//end of OnlinePolicyHistory
