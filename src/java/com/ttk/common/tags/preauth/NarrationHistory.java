/**
 * @ (#) NarrationHistory.java May 29, 2006
 * Project 		: TTK HealthCare Services
 * File 		: NarrationHistory.java
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
 *  This class builds the Narration History in the Cashless History
 */

public class NarrationHistory extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( NarrationHistory.class );
    public int doStartTag() throws JspException{
        try
        {

//           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
           JspWriter out = pageContext.getOut();//Writer object to write the file
           List  narrationdetailsList=null;
           Element narrationdetailsElement=null;
           String strClass="";
           if(historyDoc != null)
           {
	        	if(!((List)historyDoc.selectNodes("//narrationdetails/record")).isEmpty())
	            {
	        		narrationdetailsList = (List)historyDoc.selectNodes("//narrationdetails/record");
	        		if(narrationdetailsList!=null&& narrationdetailsList.size() > 0 )
	                {
	        			out.print("<fieldset><legend>Narration Details</legend>");
		        			out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                        out.print("<tr>");
	                            out.print("<td width=\"20%\" valign=\"top\" class=\"gridHeader\">Log Date</td>");
	                            out.print("<td width=\"60%\" valign=\"top\" class=\"gridHeader\">Description</td>");
	                            out.print("<td width=\"20%\" valign=\"top\" class=\"gridHeader\">User</td>");
	                        out.print("</tr>");

	                        for(int icdcount=0;icdcount<narrationdetailsList.size();icdcount++)
                            {
	                        	narrationdetailsElement = (Element)narrationdetailsList.get(icdcount);
                                strClass=icdcount%2==0 ? "gridOddRow" : "gridEvenRow" ;
                                out.print("<tr class=\""+strClass+"\">");
                                out.print("<td>");
                                	out.print(narrationdetailsElement.valueOf("@referencedate")+"&nbsp;");
                                out.print("</td>");
                                out.print("<td>");
                                	out.print(narrationdetailsElement.valueOf("@remarks")+"&nbsp;");
                                out.print("</td>");
                                out.print("<td>");
                            		out.print(narrationdetailsElement.valueOf("@user")+"&nbsp;");
                            	out.print("</td>");
                                out.print("</tr>");
                            }//end of for(int icdcount=0;icdcount<narrationdetailsList.size();icdcount++)
	                        out.print("</table>");
	                        out.print("</fieldset>");
	                   }//end of if(narrationdetailsList!=null&& narrationdetailsList.size() > 0 )

	            }//end of if(!((List)historyDoc.selectNodes("//narrationdetails/record")).isEmpty())
           }//end of if(historyDoc != null)
        }//end of try block
        catch(Exception exp)
        {
            exp.printStackTrace();
            log.debug("error occured in AuthorizationHistory Tag Library!!!!! ");
        }//end of catch block
        return SKIP_BODY;
    }//end of doStartTag()

    public int doEndTag() throws JspException
    {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()
}//end of class NarrationHistory