/**

 * @ (#) ClaimantHistory.java May 26, 2006
 * Project : TTK HealthCare Services
 * File : ClaimantHistory.java
 * Author : Srikanth H M
 * Company : Span Systems Corporation
 * Date Created : May 26, 2006
 *
 * @author : Srikanth H M
 * Modified by :
 * Modified date :
 * Reason :
*/

package com.ttk.common.tags.preauth;

import java.util.List;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
//import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 *  This class builds the Claimant History in the Pre-Auth History
 */

public class ClaimantHistory extends TagSupport{

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
//    private static Logger log = Logger.getLogger( ClaimantHistory.class );
    public int doStartTag() throws JspException{
        try
        {

           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
//           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           JspWriter out = pageContext.getOut();//Writer object to write the file
           Element claimentElement=null;
           List  claimentlist=null;
               if(historyDoc != null)
               {
                   if(!((List)historyDoc.selectNodes("//claimantdetails")).isEmpty())
                   {
                       claimentlist = (List)historyDoc.selectNodes("//claimantdetails");
                       if(claimentlist!=null&& claimentlist.size() > 0 )
                       {
                           claimentElement = (Element)claimentlist.get(0);
                           out.print("<fieldset><legend>Member Details</legend>");
                               out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                                    out.print("<tr>");
                                       out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Enrollment Id:</td>");
                                       out.print("<td width=\"25%\" class=\"textLabel\">");
                                       out.print(claimentElement.valueOf("@enrollmentid"));
                                       out.print("</td>");
                                       out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
                                           out.print("Member Name:");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\" width=\"31%\">");
                                           out.print(claimentElement.valueOf("@claimantname"));
                                       out.print("</td>");
                                   out.print("</tr>");
                                   out.print("<tr>");
                                   		out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Diabetes Cover:</td>");
                                   		out.print("<td width=\"25%\" class=\"textLabel\">");
                                   		out.print(claimentElement.valueOf("@diabetes_cover_yn"));
                                        out.print("</td>");
                                        out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
                                        out.print("Hypertension Cover:");
                                    out.print("</td>");
                                    out.print("<td class=\"textLabel\" width=\"31%\">");
                                    out.print(claimentElement.valueOf("@hypertension_cover_yn"));
                                    out.print("</td>");
                                out.print("</tr>");
                                 out.print("</table>");
                           out.print("</fieldset>");
                       }//end of if(claimentlist!=null&& claimentlist.size() > 0 )
                   }//end of if(!((List)historyDoc.selectNodes("//claimantdetails")).isEmpty())
               }//end of if(historyDoc != null)
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
}//end of class MemberHistory