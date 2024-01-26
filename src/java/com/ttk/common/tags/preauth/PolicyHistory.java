/**
 * @ (#) PolicyHistory.java May 29, 2006
 * Project 		: TTK HealthCare Services
 * File 		: PolicyHistory.java
 * Author		: Srikanth H M
 * Company 		: Span Systems Corporation
 * Date Created : May 29, 2006
 *
 * @author 		: Srikanth H M
 * Modified by 	:
 * Modified date:
 * Reason 		:
*/

package com.ttk.common.tags.preauth;

import java.util.List;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 *  This class builds the Policy History in the Cashless History
 */

public class PolicyHistory extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger(PolicyHistory.class );

    public int doStartTag() throws JspException{
        try
        {

//           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
           JspWriter out = pageContext.getOut();//Writer object to write the file
           List  policydetailsList=null;
           Element policydetailsElement=null;
           if(historyDoc != null)
           {
               if(!((List)historyDoc.selectNodes("//policydetails")).isEmpty())
               {
                   policydetailsList= (List)historyDoc.selectNodes("//policydetails");
                   policydetailsElement = (Element)policydetailsList.get(0);
                   out.print("<fieldset>");
                       out.print("<legend>Scheme Details</legend>");
                       out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                               out.print("<tr>");
                                   out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
                                       out.print("Scheme No.:");
                                   out.print("</td>");
                                   out.print("<td width=\"25%\" class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@policynumber"));
                                   out.print("</td>");
                                   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
                                       out.print("Insurance Company:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\" width=\"31%\">");
                                       out.print(policydetailsElement.valueOf("@inscompname"));
                                   out.print("</td>");
                               out.print("</tr>");

                               out.print("<tr>");
                                   out.print("<td height=\"25\" class=\"formLabel\">");
                                       out.print("Scheme Holder's Name: ");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@policyholder"));
                                   out.print("</td>");
                                   out.print("<td class=\"formLabel\">");
                                       out.print("Phone:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@phone"));
                                   out.print("</td>");
                               out.print("</tr>");

                               out.print("<tr>");
                                   out.print("<td height=\"25\" class=\"formLabel\">");
                                       out.print("Product / Scheme Name:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@productname"));
                                   out.print("</td>");
                                   out.print("<td class=\"formLabel\">");
                                       out.print("Term Status:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@termstatus"));
                                   out.print("</td>");
                               out.print("</tr>");

                               out.print("<tr>");
                                   out.print("<td height=\"25\" class=\"formLabel\">");
                                       out.print("Scheme Type:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@policytype"));
                                   out.print("</td>");
                                   out.print("<td class=\"formLabel\">");
                                       out.print("Scheme Sub Type:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@policysubtype"));
                                   out.print("</td>");
                               out.print("</tr>");

                               out.print("<tr>");
                                   out.print("<td height=\"25\" class=\"formLabel\">");
                                       out.print("Scheme Start Date:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@startdate"));
                                   out.print("</td>");
                                   out.print("<td class=\"formLabel\">");
                                       out.print("Scheme End Date:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@enddate"));
                                   out.print("</td>");
                               out.print("</tr>");
                               //added by rekha <!-- added for koc1289_1272  -->
                            if(policydetailsElement.valueOf("@inscompname").equals("CIGNA INSURANCE COMPANY"))   
                            {
                               out.print("<tr>");
                               out.print("<td height=\"25\" class=\"formLabel\">");
                                   out.print("Scheme Actual Start Date(Long Term Scheme):");
                               out.print("</td>");
                               out.print("<td class=\"textLabel\">");
                                   out.print(policydetailsElement.valueOf("@actualstartdate"));
                               out.print("</td>");
                               out.print("<td class=\"formLabel\">");
                                   out.print("Scheme Actual End Date(Long Term Scheme):");
                               out.print("</td>");
                               out.print("<td class=\"textLabel\">");
                                   out.print(policydetailsElement.valueOf("@actualenddate"));
                               out.print("</td>");
                           out.print("</tr>");
                            }    
                             //added by rekha <!-- End added for koc1289_1272  -->
                          
//modified for hyundai buffer
                             
                              /*     out.print("<td height=\"25\" class=\"formLabel\">");
                                       out.print("Avail. Buffer Amt. (Rs):");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@availablebufferamount"));
                                   out.print("</td>");
                                  out.print("<tr>");
									out.print("<td height=\"25\" class=\"formLabel\">");
									out.print("Total Appr. Buffer Amt. (Rs):");
									out.print("</td>");
									out.print("<td class=\"textLabel\">");
									out.print(policydetailsElement.valueOf("@totalappbufferamount"));
									out.print("</td>");
								    out.print("<td class=\"formLabel\">");
								    out.print("Total Utilized Buffer Amt. (Rs):");
								    out.print("</td>");
								    out.print("<td class=\"textLabel\">");
								    out.print(policydetailsElement.valueOf("@totalutilbufferamount"));
								    out.print("</td>");
								    out.print("</tr>");*/
								  //end modified for hyundai buffer
                           
								   out.print("<tr>");
								   out.print("<td height=\"25\" class=\"formLabel\">");
								   out.print("Cumulative Bonus (Rs):");
								   out.print("</td>");
								   out.print("<td class=\"textLabel\">");
								   out.print(policydetailsElement.valueOf("@availablebonus"));
								   out.print("</td>");
								   out.print("<td class=\"formLabel\">");
								   out.print("Zone Code:");
								   out.print("</td>");
								   out.print("<td colspan=\"4\" class=\"textLabel\">");
								   out.print(policydetailsElement.valueOf("@zonecode"));
								   out.print("</td>");
								   out.print("</tr>");
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
}//end of class PolicyHistory