/**
 * @ (#) BankAccountInformation.java Feb 24, 2006
 * Project       : TTK HealthCare Services
 * File          : BankAccountInformation.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 24, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.enrollment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.TTKCommon;
import com.ttk.common.tags.PersonalInformation;

/**
 *  This class builds the Bank Account Information
 */

public class BankAccountInformation extends TagSupport
{
	/**
     * Comment for <code>serialVersionUID</code>
     * changes done for cr koc 1103 and koc 1105 
     * bank related informatiom made as readonly for enroll module 
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger log = Logger.getLogger( PersonalInformation.class );

     public int doStartTag() throws JspException {
       try
       {

               log.debug("Inside BankAccountInformation TAG :");
               JspWriter out = pageContext.getOut();//Writer object to write the file
            HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
            //get the reference of the frmPolicyDetails to load the Beneficiary  information
            DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
            String strViewmode=" Disabled ";
            String strIndividualPolicy="Individual Policy";
            String strIndPolasGroup="Ind. Policy as Group";
            String strCorPolicy="Corporate Policy";
            String strBankName=(String)frmPolicyDetails.get("bankName");
            String strBankAccountName=(String)frmPolicyDetails.get("bankAccountName");
            String strBranch=(String)frmPolicyDetails.get("branch");
            String strBankAccNbr=(String)frmPolicyDetails.get("bankAccNbr");
            String strMICRCode=(String)frmPolicyDetails.get("MICRCode");
            String strBankPhone=(String)frmPolicyDetails.get("bankPhone");
            boolean cond=false;

            //get the Active Sublink
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
            {
                strViewmode="";
                cond=true;
            }//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))


            if(strActiveSubLink.equals(strIndividualPolicy)||strActiveSubLink.equals(strIndPolasGroup)||strActiveSubLink.equals(strCorPolicy))
            {

                out.print("<fieldset><legend>Bank Account Information</legend>");
                    out.print("<table align=\"center\" class=\"formContainer\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                    out.print("<tr>");
                            out.print("<td width=\"21%\" CLASS=\"formLabel\" nowrap>");
                                out.print("Bank Name:");
                            out.print("</td>");
                            out.print("<td width=\"33%\" class=\"formLabelBold\" nowrap>");
                                out.print("<input type=\"text\" name=\"bankName\"  value='"+TTKCommon.getHtmlString(strBankName)+"' maxlength=\"60\" class=\"textBox textBoxLarge\""+strViewmode+"readonly='"+cond+"'>");
                            out.print("</td>");

                            if(strActiveSubLink.equals(strCorPolicy))
                            {
                                out.print("<td width=\"23%\" CLASS=\"formLabel\" nowrap>");
                                    out.print("Branch:");
                                out.print("</td>");
                                out.print("<td width=\"23%\" nowrap CLASS=\"formLabelBold\">");
                                    out.print("<input type=\"text\" name=\"branch\"  value='"+TTKCommon.getHtmlString(strBranch)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+"readonly='"+cond+"'>");
                                out.print("</td>");
                           }// end of if(strActiveSubLink.equals(strCorPolicy))
                           else
                           {
                               out.print("<td width=\"23%\" CLASS=\"formLabel\" nowrap>");
                                   out.print("&nbsp;");
                               out.print("</td>");
                               out.print("<td width=\"23%\" nowrap CLASS=\"formLabelBold\">");
                                   out.print("&nbsp;");
                               out.print("</td>");
                           }//end of else
                       out.print("</tr>");

                       out.print("<tr>");
                           out.print("<td CLASS=\"formLabel\" nowrap>");
                               out.print("Account Type");
                           out.print("</td>");
                           out.print("<td CLASS=\"formLabelBold\" nowrap>");
                               out.print("<input type=\"text\" name=\"bankAccountName\"  value='"+TTKCommon.getHtmlString(strBankAccountName)+"' maxlength=\"60\" class=\"textBox textBoxLarge\""+strViewmode+"readonly='"+cond+"'>");
                           out.print("</td>");
                           out.print("<td CLASS=\"formLabel\">");
                               out.print("Bank Account No.:");
                           out.print("</td>");
                           out.print("<td>");
                               out.print("<input type=\"password\" name=\"bankAccNbr\"  value='"+TTKCommon.getHtmlString(strBankAccNbr)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+"readonly='"+cond+"'>");
                           out.print("</td>");
                       out.print("</tr>");

                       out.print("<tr>");
                           out.print("<td CLASS=\"formLabel\">");
                               out.print("IFSC Code:");
                           out.print("</td>");
                           out.print("<td>");
                               out.print("<input type=\"text\" name=\"MICRCode\"  value='"+TTKCommon.getHtmlString(strMICRCode)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+"readonly='"+cond+"'>");
                           out.print("</td>");
                           out.print("<td CLASS=\"formLabel\">");
                               out.print("Bank Phone:");
                           out.print("</td>");
                           out.print("<td>");
                               out.print("<input type=\"text\" name=\"bankPhone\"  value='"+TTKCommon.getHtmlString(strBankPhone)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+"readonly='"+cond+"'>");
                          out.print("</td>");
                       out.print("</tr>");
                    out.print("</table>");
                out.print("</fieldset>");

            }//end of if(strActiveSubLink.equals(strIndividualPolicy)||strActiveSubLink.equals(strIndPolasGroup)||strActiveSubLink.equals(strCorPolicy))

       }
       catch(Exception exp)
       {
           exp.printStackTrace();
       }//end of catch block
       return SKIP_BODY;
   }//end of doStartTag()

     public int doEndTag() throws JspException {
       return EVAL_PAGE;//to process the rest of the page
   }//end doEndTag()
}//end of BankAccountInformation