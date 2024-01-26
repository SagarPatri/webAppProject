/**

 * @ (#) PreAuthHistory.java May 26, 2006
 * Project : TTK HealthCare Services
 * File : PreAuthHistory.java
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
//import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;

/**
 *  This class builds the PreAuth Details Pre-Auth History
 */

public class PreAuthHistory extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;

//    private static Logger log = Logger.getLogger( PreAuthHistory.class );

    public int doStartTag() throws JspException{
        try
        {

           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           JspWriter out = pageContext.getOut();//Writer object to write the file
           
           String strActiveLink=TTKCommon.getActiveLink(request);//added as per Hospital Login
           String strActiveSubLink=TTKCommon.getActiveSubLink(request);//added as per Hospital Login

           Element preAuthElement=null;
           Element preAuthShrtFallElement=null;
           List  preauthorizationdetailslist=null;
           List  preautShrtFalllist=null;
           if(historyDoc != null)
           {
        	   if(!((List)historyDoc.selectNodes("//shortfall")).isEmpty())
               {
        		   preautShrtFalllist = (List)historyDoc.selectNodes("//shortfall");
               }
        	   if(!((List)historyDoc.selectNodes("//preauthorizationdetails")).isEmpty())
               {
                   preauthorizationdetailslist = (List)historyDoc.selectNodes("//preauthorizationdetails");
                   if(preauthorizationdetailslist!=null&& preauthorizationdetailslist.size() > 0 )
                   {
                       preAuthElement = (Element)preauthorizationdetailslist.get(0);
                       if(preautShrtFalllist !=null && preautShrtFalllist.size() >0)
                       {
                    	   preAuthShrtFallElement = (Element)preautShrtFalllist.get(0);
                    	   //out.print("print "+preAuthShrtFallElement.valueOf("@shortfall")+"  "+preAuthShrtFallElement.valueOf("@seq_id"));
	                       if("Y".equals(preAuthShrtFallElement.valueOf("@shortfall")))
	                       {
	                       out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		                       out.print("<tr>");
		                         out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
		                             out.print("<a href=\"#\" onClick=\"javascript:online_Preauth_shortfall('"+preAuthShrtFallElement.valueOf("@seq_id")+"');\"><font size=\"2\">Shortfall</font></a>");
		                         out.print("</td>");
		                         out.print("<td width=\"25%\" class=\"textLabelBold\">");
		                         out.print("</td>");
		                         out.print("<td class=\"formLabel\" width=\"22%\">");
		                         out.print("</td>");
		                         out.print("<td class=\"textLabel\" width=\"31%\">");
		                         out.print("</td>");
		                       out.print("</tr>");
	                       out.print("</table>");
	                       }
                   		}
                       out.print("<fieldset>");
                               out.print("<legend>Cashless Details</legend>");
                               out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                                 out.print("<tr>");
                                   out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
                                       out.print("Cashless No.:");
                                   out.print("</td>");
                                   out.print("<td width=\"25%\" class=\"textLabelBold\">");
                                           out.print(preAuthElement.valueOf("@preauthnumber"));
                                   out.print("</td>");
                                   out.print("<td class=\"formLabel\" width=\"22%\">");
                                           out.print("Cashless Type:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\" width=\"31%\">");
                                           out.print(preAuthElement.valueOf("@preauthtype"));
                                   out.print("</td>");
                                 out.print("</tr>");
                                 out.print("<tr>");
                                   out.print("<td height=\"25\" class=\"formLabel\">Received Date / Time:");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                           out.print(preAuthElement.valueOf("@recieveddate"));
                                   out.print("</td>");
                                   out.print("<td class=\"formLabel\">Admission Date / Time:");
                                       out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(preAuthElement.valueOf("@admissiondate"));
                                           out.print("</td>");
                                 out.print("</tr>");
                                 out.print("<tr>");
                                   out.print("<td height=\"25\" class=\"formLabel\">Prev. Approved Amt. (Rs):");
                                       out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(preAuthElement.valueOf("@prevapprovedamount"));
                                   out.print("</td>");
                                   out.print("<td class=\"formLabel\">Requested Amt. (Rs):");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(preAuthElement.valueOf("@requestedamount"));
                                   out.print("</td>");
                                 out.print("</tr>");
                                 out.print("<tr>");
                                 out.print("<td height=\"25\" class=\"formLabel\">Treating Doctor's Name:");
                                         out.print("</td>");
                                 out.print("<td class=\"textLabel\">");
                                     out.print(preAuthElement.valueOf("@treatingdoctorname"));
                                 out.print("</td>");
                                 out.print("<td height=\"20\" class=\"formLabel\">Vidal Health Healthcare Administrator Branch:");
                                 out.print("</td>");
                                 out.print("<td class=\"textLabel\">");
                                     out.print(preAuthElement.valueOf("@ttkbranch"));
                                         out.print("</td>");
                                 out.print("</tr>");

                                 out.print("<tr>");
                                  out.print("<td height=\"25\" class=\"formLabel\">Probable Date of Discharge:");
                                      out.print("</td>");
                                  out.print("<td class=\"textLabel\">");
                                  out.print(preAuthElement.valueOf("@dateofdischarge"));
                                          out.print("</td>");
                                  out.print("<td height=\"20\" class=\"formLabel\">Enhanced:");
                                  out.print("</td>");
                                  out.print("<td class=\"textLabel\">");
                                      out.print(preAuthElement.valueOf("@enhanced"));
                                  out.print("</td>");
                                  out.print("</tr>");

                                  if(!TTKCommon.getActiveLink(request).equals("Online Forms"))
                                  {
	                                  out.print("<tr>");
	                                  out.print("<td height=\"25\" class=\"formLabel\">Workflow:");
	                                      out.print("</td>");
	                                  out.print("<td class=\"textLabel\">");
	                                  out.print(preAuthElement.valueOf("@workflow"));
	                                          out.print("</td>");
	                                  out.print("<td height=\"20\" class=\"formLabel\">");
	                                  out.print("</td>");
	                                  out.print("<td class=\"textLabel\">");
	                                      out.print("");
	                                  out.print("</td>");
	                                  out.print("</tr>");
                                  }

                                  out.print("<tr>");
	                                  out.print("<td height=\"25\" class=\"formLabel\">Remarks:");
	                                      out.print("</td>");
	                                  out.print("<td colspan=\"3\" class=\"textLabel\">");
	                                  	  out.print(preAuthElement.valueOf("@remarks"));
	                                  out.print("</td>");
                                  out.print("</tr>");
                                  out.print("</table>");
                                  
                                 
                                  if(strActiveLink.equalsIgnoreCase("Hospital Information")){
                                	   if(strActiveSubLink.equalsIgnoreCase("Pre-Auth")){
                                		  if(preAuthElement.valueOf("@completed").equalsIgnoreCase("Y")) {
                                			  out.print("<table align=\"center\" class=\"buttonsContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                                              out.print("<tr>");
                                              out.print("<td align=\"center\">");
                                			  out.print("<button type=\"button\" name=\"Button\" accesskey=\"g\" id=\"authletter\" class=\"buttons\" style=\"display:\" onMouseout=\"this.className='buttons'\" onMouseover=\"this.className='buttons buttonsHover'\" onClick=\"javascript:onReport();\"><u>G</u>enerate Letter</button>");//&nbsp;
                                			  out.print("</td>");
                                              out.print("</tr>");
                                              out.print("</table>");
                                              out.print("<input type=\"hidden\" name=\"authLtrTypeID\"  id=\"authLtrTypeID\" value=\""+preAuthElement.valueOf("@authtypeid")+"\"/>");out.print("</br>");
                                              out.print("<input type=\"hidden\" name=\"preAuthSeqID\"  id=\"preAuthSeqID\" value=\""+preAuthElement.valueOf("@seqid")+"\"/>");out.print("</br>");
                                              out.print("<input type=\"hidden\" name=\"statusTypeID\"  id=\"statusTypeID\" value=\""+preAuthElement.valueOf("@statusid")+"\"/>");out.print("</br>");
                                              out.print("<input type=\"hidden\" name=\"completedYN\"   id=\"completedYN\" value=\""+preAuthElement.valueOf("@completed")+"\"/>");out.print("<br>");
                                              out.print("<input type=\"hidden\" name=\"cignastatusYN\" id=\"cignastatusYN\" value=\""+preAuthElement.valueOf("@cignastatus")+"\"/>");out.print("<br>");
                                              out.print("<input type=\"hidden\" name=\"authorizationNo\" id=\"authorizationNo\"  value=\""+preAuthElement.valueOf("@authorizationnumber")+"\"/>");out.print("</br>");
                                              out.print("<input type=\"hidden\" name=\"preAuthNo\" id=\"preAuthNo\" value=\""+preAuthElement.valueOf("@preauthnumber")+"\"/>");out.print("<br>");
                                        
                                		  }//end of if(preAuthElement.valueOf("@completed").equalsIgnoreCase("Y"))
                                	  }//end of if(strActiveSubLink.equalsIgnoreCase("Pre-Auth"))
                                  }//end of     if(strActiveLink.equalsIgnoreCase("Hospital Information")){  
                               out.print("</fieldset>");
                                  
                                  
                                  
                                
                   }//end of if(preauthorizationdetailslist!=null&& preauthorizationdetailslist.size() > 0 )
               }//end of if(!((List)doc.selectNodes("//preauthorizationdetails")).isEmpty())
           }//end of if(doc != null)
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
