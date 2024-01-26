/**
 * @ (#) InsuranceCompany.java May 15, 2006
 * Project 		: TTK HealthCare Services
 * File 		: InsuranceCompany.java
 * Author 		: Srikanth H M
 * Company 		: Span Systems Corporation
 * Date Created : May 15, 2006
 *
 * @author 		: Srikanth H M
 * Modified by 	:
 * Modified date:
 * Reason 		:
*/

package com.ttk.common.tags.preauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.TTKCommon;


/**
 *  This class builds the Insurance Company Information
 */

public class InsuranceCompany  extends TagSupport  {
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( InsuranceCompany.class );

   public int doStartTag() throws JspException
   {
      try
      {
           log.debug("Inside InsuranceCompany TAG :");
           JspWriter out = pageContext.getOut();//Writer object to write the file
           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           // get the reference of the frmPreAuthGeneral to load the Claimant Details
           DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
           DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
           String strCompanyName=(String)frmClaimantDetails.get("companyName");
           String strCompanyCode=(String)frmClaimantDetails.get("companyCode");
           String strInsSeqId=(String)frmClaimantDetails.get("policySeqID");
           String strActiveLink=TTKCommon.getActiveLink(request);
         //added for koc insurance reference No
           String strInsuranceRefNo=(String)frmClaimantDetails.get("insuranceRefNo");
           
           String strViewmode=" Disabled ";
           if(TTKCommon.isAuthorized(request,"Edit"))
           {
               strViewmode="";
           }//end of if(TTKCommon.isAuthorized(request,"Edit"))

           out.print("<fieldset><legend>Insurance Company</legend>");
           out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
		   
		   //added for Decoupling
           if(!TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
           {
		   
               out.print("<tr>");
                       out.print("<td width=\"22%\" class=\"formLabel\">");
                           out.print("Insurance Company:");
                       out.print("</td>");
                      out.print("<td width=\"30%\" class=\"textLabelBold\">");
                           out.print(strCompanyName);
                       out.print("</td>");
                       out.print("<td width=\"19%\" class=\"formLabel\">");
                           out.print("Company Code:");
                       out.print("</td>");
                       out.print("<td width=\"29%\" class=\"textLabelBold\">");
                           out.print(strCompanyCode);
                           out.print("&nbsp;&nbsp;&nbsp;");
                           if(strViewmode.equals("")&&(strInsSeqId==null||strInsSeqId.equals("")))
                           {							  
                        	   if(!(strActiveLink.equals("Support"))) //koc 11 koc11
                        	   {
                               out.print("<a href=\"#\" onClick=\"selectInsurance();\"><img src=\"/ttk/images/EditIcon.gif\" alt=\"Select Insurance Co.\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>");
							   }
                           }//end of if(strInsSeqId==null||strInsSeqId.equals(""))
                       out.print("</td>");
                   out.print("</tr>");
           }
//added for koc insurance reference No
                out.print("<tr>");
       			out.print("<td class=\"formLabel\">");
				out.print("Healthcare Reference No.:");
				out.print("</td>");
       			out.print("<td class=\"textLabel\">");
               // out.print("<input type=\"text\" name=\"claimantDetailsVO.insuranceRefNo\"  value='"+TTKCommon.getHtmlString(strInsuranceRefNo)+"' maxlength=\"25\" class=\"formLabel\""+strViewmode+">");
       		    out.print("<input type=\"text\" name=\"claimantDetailsVO.insuranceRefNo\"  value='"+(strInsuranceRefNo)+"'  maxlength=\"60\" class=\"textBox textBoxMedium\" >");
				 out.print("</td>");
                out.print("<td width=\"19%\" class=\"formLabel\">&nbsp;</td>");
			    out.print("<td class=\"textLabel\">&nbsp;</td>");
				out.print("</tr>");     
                   
                 //added for koc insurance reference No
               out.print("</table>");
           out.print("</fieldset>");
      }//end of try
      catch(Exception exp)
      {
          exp.printStackTrace();
      }//end of catch block
      return SKIP_BODY;
   }//end of doStartTag
}//end of HealthcareCompany