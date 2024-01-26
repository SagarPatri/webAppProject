/**
 * @ (#) ChequeInfoHistory.java Apr 16, 2007
 * Project 	     : TTK HealthCare Services
 * File          : ChequeInfoHistory.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 16, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
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

public class ChequeInfoHistory extends TagSupport{

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( ChequeInfoHistory.class );

	public int doStartTag() throws JspException{
        try
        {

//           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
           JspWriter out = pageContext.getOut();//Writer object to write the file
           List  chequeList=null;
           Element chequeElement=null;
           String strClass="";
           if(historyDoc != null)
           {
               if(!((List)historyDoc.selectNodes("//chequeinformation/record")).isEmpty())
               {
            	   chequeList = (List)historyDoc.selectNodes("//chequeinformation/record");
            	   out.print("<fieldset><legend>Cheque Details</legend>");
            	   if(chequeList!=null&& chequeList.size() > 0 ){
            		   out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                       out.print("<tr>");
                       out.print("<td width=\"30%\" valign=\"top\" class=\"gridHeader\">Payee Name</td>");
               		   out.print("<td width=\"12%\" valign=\"top\" class=\"gridHeader\">Cheque No.</td>");
               		   out.print("<td width=\"12%\" valign=\"top\" class=\"gridHeader\">Cheque Amt.(Rs)</td>");
               		   out.print("<td width=\"10%\" valign=\"top\" class=\"gridHeader\">TDS Amt.(Rs)</td>");
                       out.print("<td width=\"10%\" valign=\"top\" class=\"gridHeader\">Cheque Date</td>");
                       out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Cheque Status</td>");
                       out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Consignment No.</td>");//added for ibm4changes
                       out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Service Provider Name/Helpdesk Loc.</td>");//added for ibm4changes
                       out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Dispatch Date</td>");//added for ibm4changes
                       out.print("</tr>");

                       for(int chqcount=0;chqcount<chequeList.size();chqcount++)
                       {
                    	   chequeElement = (Element)chequeList.get(chqcount);
                           strClass=chqcount%2==0 ? "gridOddRow" : "gridEvenRow" ;
                           out.print("<tr class=\""+strClass+"\">");
                           out.print("<td>");
                       			out.print(chequeElement.valueOf("@payeename")+"&nbsp;");
                       	   out.print("</td>");
                       	   out.print("<td>");
                       	   		out.print(chequeElement.valueOf("@checknumber")+"&nbsp;");
                       	   out.print("</td>");
                       	   out.print("<td>");
                       	   		out.print(chequeElement.valueOf("@checkamount")+"&nbsp;");
                       	   out.print("</td>");
                       	   out.print("<td>");
               	   			    out.print(chequeElement.valueOf("@tdsamount")+"&nbsp;");
               	   		   out.print("</td>");
                       	   out.print("<td>");
               	   				out.print(chequeElement.valueOf("@checkdate")+"&nbsp;");
               	   		   out.print("</td>");
               	   		   out.print("<td>");
               	   		   		out.print(chequeElement.valueOf("@chequestatus")+"&nbsp;");
               	   		   out.print("</td>");
               	   	//added for ibm4changes
               	   	     out.print("<td>");
 	   		   		     out.print(chequeElement.valueOf("@consignmentno")+"&nbsp;");
 	   		             out.print("</td>"); 
 	   		             out.print("<td>");
	   		   		     out.print(chequeElement.valueOf("@providername")+"&nbsp;");
	   		             out.print("</td>"); 
               	   	      out.print("<td>");
     	   		   		  out.print(chequeElement.valueOf("@despatchdate")+"&nbsp;");
     	   		          out.print("</td>"); 
               	   	//End added for ibm4changes
                           out.print("</tr>");
                       }//end of for(int chqcount=0;chqcount<chequeList.size();chqcount++)
                       out.print("</table>");

                   }//end of if(chequeList!=null&& chequeList.size() > 0 )
            	   out.print("</fieldset>");
               }//end of if(!((List)historyDoc.selectNodes("//chequeinformation")).isEmpty())
       }//end of if(historyDoc != null)
    }//end of try block
    catch(Exception exp)
    {
        exp.printStackTrace();
        log.debug("error occured in ChequeInfoHistory Tag Library!!!!! ");
    }//end of catch block
    return SKIP_BODY;
}//end of doStartTag()
public int doEndTag() throws JspException {
    return EVAL_PAGE;//to process the rest of the page
}//end doEndTag()
}//end of ChequeInfoHistory
