/**
 * @ (#) SettlementHistory.java Aug 11, 2006
 * Project : TTK HealthCare Services
 * File : SettlementHistory.java
 * Author : Raghavendra T M
 * Company : Span Systems Corporation
 * Date Created : Aug 11, 2006
 *
 * @author : Raghavendra T M
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
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 *  This class builds the Settlement Details in the Claim History
 */

public class SettlementHistory extends TagSupport{
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( SettlementHistory.class );

    public int doStartTag() throws JspException{
        try
        {

//           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
           JspWriter out = pageContext.getOut();//Writer object to write the file
           List  settlementList=null;
           Element settlementElement=null;
           List  bufferList=null;
           Element bufferElement=null;
           List  approvalstatusList=null;
           Element approvalstatusElement=null;
           if(historyDoc != null)
           {
               if(!((List)historyDoc.selectNodes("//settlementdetails")).isEmpty())
               {
            	   settlementList= (List)historyDoc.selectNodes("//settlementdetails");
            	   settlementElement = (Element)settlementList.get(0);

                   out.print("<fieldset>");
                       out.print("<legend>Settlement Details</legend>");
                       out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                           out.print("<tr>");
                               out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
                                   out.print("Claim Settlement No:");
                               out.print("</td>");
                               out.print("<td class=\"textLabelBold\" width=\"25%\">");
                                   out.print(settlementElement.valueOf("@settlementnumber"));
                               out.print("</td>");
                               out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">&nbsp;</td>");
                               out.print("<td class=\"textLabel\" width=\"31%\">&nbsp;</td>");
                            out.print("</tr>");

                            out.print("<tr>");
                                out.print("<td height=\"25\" class=\"formLabel\">");
                                    out.print("Total Sum Insured (Rs):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(settlementElement.valueOf("@totalsuminsured"));
                                out.print("</td>");
                                out.print("<td>");
                                    out.print("Bal. Sum Insured (Rs):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(settlementElement.valueOf("@availablesuminsured"));
                                out.print("</td>");
                            out.print("</tr>");

                            out.print("<tr>");
                                out.print("<td height=\"25\" class=\"formLabel\">");
                                    out.print("Avail. Cumu. Bonus (Rs):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(settlementElement.valueOf("@availablebonus"));
                                out.print("</td>");
                                out.print("<td class=\"formLabel\">");
                                    out.print("Requested Amt. (Rs):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(settlementElement.valueOf("@requestedamount"));
                                out.print("</td>");
                            out.print("</tr>");

                            out.print("<tr>");
                                out.print("<td height=\"25\" class=\"formLabel\">");
                                    out.print("Prev. Approved Amt. (Rs):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(settlementElement.valueOf("@preauthapprovedamount"));
                                out.print("</td>");
                                out.print("<td height=\"20\" class=\"formLabel\">");
                                    out.print("Approved Amt. (Rs):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(settlementElement.valueOf("@approvedamount"));
                                out.print("</td>");
                           out.print("</tr>");
             }//end of if(!((List)historyDoc.selectNodes("//settlementdetails")).isEmpty())

               if(!((List)historyDoc.selectNodes("//bufferamount")).isEmpty())
               {
            	   bufferList = (List)historyDoc.selectNodes("//bufferamount");
            	   bufferElement = (Element)bufferList.get(0);

			               out.print("<tr>");
			                   out.print("<td height=\"20\" colspan=\"4\" class=\"textLabelBold\">");
			                       out.print("Buffer Amount");
			                   out.print("</td>");
			               out.print("</tr>");
			               //added for hyundai
                           out.print("<tr>");
                           out.print("<td height=\"25\" class=\"formLabel\">");
                               out.print("Normal Corpus Appr. Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@nrmlbufferamount"));
                           out.print("</td>");
                           out.print("<td height=\"20\" class=\"formLabel\">");
                               out.print("Normal Corpus Utilized  Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@utilbufferamount"));
                           out.print("</td>");
                           out.print("</tr>");
                           ///2
                           out.print("<tr>");
                           out.print("<td height=\"25\" class=\"formLabel\">");
                               out.print("Normal Medical Appr. Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@medbufferamount"));
                           out.print("</td>");
                           out.print("<td height=\"20\" class=\"formLabel\">");
                               out.print("Normal Medical Utilized  Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@utilmedbufferamount"));
                           out.print("</td>");
                           out.print("</tr>");
                           ///3
                           out.print("<tr>");
                           out.print("<td height=\"25\" class=\"formLabel\">");
                               out.print("Critical Illness Buffer. Appr. Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@critbufferamount"));
                           out.print("</td>");
                           out.print("<td height=\"20\" class=\"formLabel\">");
                               out.print("Critical Illness Buffer. Utilized  Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@utilcritbufferamount"));
                           out.print("</td>");
                           out.print("</tr>");
                           ///4
                           out.print("<tr>");
                           out.print("<td height=\"25\" class=\"formLabel\">");
                               out.print("Critical Medical Appr. Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@critmedbufferamount"));
                           out.print("</td>");
                           out.print("<td height=\"20\" class=\"formLabel\">");
                               out.print("Critical Medical Utilized  Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@utilcritmedbufferamount"));
                           out.print("</td>");
                           out.print("</tr>");
                           ///5
                           out.print("<tr>");
                           out.print("<td height=\"25\" class=\"formLabel\">");
                               out.print("Critical Corpus Appr. Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@critcorpbufferamount"));
                           out.print("</td>");
                           out.print("<td height=\"20\" class=\"formLabel\">");
                               out.print("Critical Corpus Utilized  Amt (Rs.):");
                           out.print("</td>");
                           out.print("<td class=\"textLabel\">");
                               out.print(bufferElement.valueOf("@utilcritcorpbufferamount"));
                           out.print("</td>");
                           out.print("</tr>");
                           
                           
                           //end  for hyundai
			               
			               
			               
			              /* out.print("<tr>");
			                   out.print("<td height=\"25\" class=\"formLabel\">");
			                       out.print("Avail. Buffer Amt. (Rs):");
			                   out.print("</td>");
			                   out.print("<td class=\"textLabel\">");
			                       out.print(bufferElement.valueOf("@availablebufferamount"));
			                   out.print("</td>");
			                   out.print("<td class=\"formLabel\">");
			                   out.print("&nbsp;");
			                   out.print("</td>");
			                   out.print("<td class=\"textLabel\">");
			                   out.print("&nbsp;");
			                   out.print("</td>");
			               out.print("</tr>");
			               out.print("<tr>");
	                        out.print("<td height=\"25\">");
	                            out.print("Approved Buffer Amt. (Rs):");
	                        out.print("</td>");
	                        out.print("<td class=\"textLabel\">");
	                            out.print(bufferElement.valueOf("@approvedbufferamount"));
	                        out.print("</td>");
	                        out.print("<td class=\"formLabel\">");
	                            out.print("Approved Date / Time:");
	                        out.print("</td>");
	                        out.print("<td class=\"textLabel\">");
	                            out.print(bufferElement.valueOf("@bufferapproveddate"));
	                        out.print("</td>");
	                    out.print("</tr>");*/
	               }//end of if(!((List)historyDoc.selectNodes("//bufferamount")).isEmpty())

	               if(!((List)historyDoc.selectNodes("//approvalstatus")).isEmpty())
	               {
	                   approvalstatusList= (List)historyDoc.selectNodes("//approvalstatus");
	                   approvalstatusElement= (Element)approvalstatusList.get(0);

	                    out.print("<tr>");
	                        out.print("<td height=\"25\" colspan=\"4\" class=\"textLabelBold\">");
	                            out.print("Approval Status");
	                        out.print("</td>");
	                    out.print("</tr>");

	                    out.print("<tr>");
	                        out.print("<td height=\"25\" class=\"formLabel\">");
	                            out.print("Status:");
	                        out.print("</td>");
	                        out.print("<td class=\"textLabel\">");
	                            out.print(approvalstatusElement.valueOf("@status"));
	                        out.print("</td>");
	                        out.print("<td height=\"20\" class=\"formLabel\">");
	                            out.print("Settled By:");
	                        out.print("</td>");
	                        out.print("<td class=\"textLabel\">");
	                            out.print(approvalstatusElement.valueOf("@approvedby"));
	                        out.print("</td>");
	                    out.print("</tr>");

	                    out.print("<tr>");
	                        out.print("<td height=\"25\" class=\"formLabel\">");
	                            out.print("Settled Date / Time:");
	                        out.print("</td>");
	                        out.print("<td class=\"textLabel\">");
	                            out.print(approvalstatusElement.valueOf("@approveddate"));
	                        out.print("</td>");
	                        out.print("<td>");
	                            out.print("Reason: ");
	                        out.print("</td>");
	                        out.print("<td class=\"textLabel\">");
	                            out.print(approvalstatusElement.valueOf("@reason"));
	                        out.print("</td>");
	                    out.print("</tr>");

	                    out.print("<tr>");
	                        out.print("<td class=\"formLabel\" height=\"25\">");
	                            out.print("Remarks:");
	                        out.print("</td>");
	                        out.print("<td height=\"25\" colspan=\"3\" class=\"textLabel\">");
	                            out.print(approvalstatusElement.valueOf("@remarks"));
	                        out.print("</td>");
	                   out.print("</tr>");
	                  out.print("</table>");
	                 out.print("</fieldset>");
	         }//end of if(!((List)historyDoc.selectNodes("//approvalstatus")).isEmpty())
       }//end of if(historyDoc != null)
    }//end of try block
    catch(Exception exp)
    {
        exp.printStackTrace();
        log.debug("error occured in ClaimsDetails Tag Library!!!!! ");
    }//end of catch block
    return SKIP_BODY;
}//end of doStartTag()
public int doEndTag() throws JspException {
    return EVAL_PAGE;//to process the rest of the page
}//end doEndTag()
}//end of class SettlementHistory