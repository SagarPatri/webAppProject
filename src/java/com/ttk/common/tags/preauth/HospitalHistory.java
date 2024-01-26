/**
 * @ (#) HospitalHistory.java May 29, 2006
 * Project 		: TTK HealthCare Services
 * File 		: HospitalHistory.java
 * Author 		: Srikanth H M
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
 *  This class builds the Hospital Details in the Pre-Auth History
 */

public class HospitalHistory extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( HospitalHistory.class );

    public int doStartTag() throws JspException{
        try
        {

//           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
           JspWriter out = pageContext.getOut();//Writer object to write the file
           String strGrade="",strAlt="";
           Element hospitalElement=null;
           List  hospitalList=null;
           if(historyDoc != null)
           {
               hospitalList = (List)historyDoc.selectNodes("//hospitaldetails");
               if(!((List)historyDoc.selectNodes("//hospitaldetails")).isEmpty())
               {
                   hospitalElement = (Element)hospitalList.get(0);

                   out.print("<fieldset>");
                       out.print("<legend>Provider Details</legend>");
                       out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                           out.print("<tr>");
                               out.print("<td width=\"22%\" height=\"20\" class=\"formLabel\">");
                                   out.print("Provider Name:");
                               out.print("</td>");
                               out.print("<td width=\"25%\" class=\"textLabelBold\">");
                                      out.print(hospitalElement.valueOf("@hospitalname"));
                                      out.print("&nbsp;&nbsp;");
                                      //check for the image and load appropriate image.
                                      if(hospitalElement.valueOf("@hospitalgrade").equals("G"))
                                      {
                                          strGrade="GoldStar";
                                          strAlt="Gold Star";
                                      }//end of if(hospitalElement.valueOf("hospitalgrade").getText().equals("G"))
                                      else if(hospitalElement.valueOf("@hospitalgrade").equals("R"))
                                      {
                                          strGrade="BlueStar";
                                          strAlt="Blue Star";
                                      }//end of else if(hospitalElement.valueOf("hospitalgrade").getText().equals("R"))
                                      else if(hospitalElement.valueOf("@hospitalgrade").equals("B"))
                                      {
                                          strGrade="BlackStar";
                                          strAlt="Black Star";
                                      }//end of else if(hospitalElement.valueOf("hospitalgrade").getText().equals("B"))
                                      if(!strGrade.equals(""))
                                      {
                                          out.print("<img src=\"/ttk/images/");
                                          out.print(strGrade);
                                          out.print(".gif\" alt=\""+strAlt+"\" width=\"15\" height=\"12\" align=\"absmiddle\">");
                                      }//end of if(!strGrade.equals(""))
                               out.print("</td>");

                               out.print("<td height=\"20\" class=\"formLabel\">");
                                    out.print("Empanelment Number:");
                               out.print("</td>");
                               out.print(" <td class=\"textLabel\">");
                                   out.print(hospitalElement.valueOf("@empanelnumber"));
                               out.print("</td>");
                           out.print("</tr>");

                           out.print("<tr>");
                               out.print("<td height=\"20\" class=\"formLabel\">");
                                   out.print("City: ");
                               out.print("</td>");
                               out.print("<td class=\"textLabel\">");
                                   out.print(hospitalElement.valueOf("@city"));
                               out.print("</td>");
                               out.print("<td class=\"formLabel\">");
                                   out.print("State: ");
                               out.print("</td>");
                               out.print("<td class=\"textLabel\">");
                                   out.print(hospitalElement.valueOf("@state"));
                               out.print("</td>");
                           out.print("</tr>");
                       out.print("</table>");
                  out.print("</fieldset>");
               }//end of if(!((List)historyDoc.selectNodes("//hospitaldetails")).isEmpty())
       }//end of if(historyDoc != null)
    }//end of try block
        catch(Exception exp)
        {
            exp.printStackTrace();
            log.debug("error occured in HospitalHistory Tag Library!!!!! ");
        }//end of catch block
        return SKIP_BODY;
    }//end of doStartTag()
    public int doEndTag() throws JspException {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()
}//end of class HospitalHistory