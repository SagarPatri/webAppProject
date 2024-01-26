/**
 * @ (#) ClaimantDetails.java May 11, 2006
 * Project       : TTK HealthCare Services
 * File          : ClaimantDetails.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 3, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.preauth;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.TTKCommon;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;

public class PolicyDetails extends TagSupport  {
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( PolicyDetails.class );

   public int doStartTag() throws JspException
   {
      try
      {
           log.debug("Inside PolicyDetails TAG :");
           JspWriter out = pageContext.getOut();//Writer object to write the file
           CacheObject cacheObject = null;
           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		   String strActiveLink=TTKCommon.getActiveLink(request);
           //get the required Cache Data to load select boxes
           ArrayList alEnrollTypeCode =Cache.getCacheObject("enrollTypeCode");
           ArrayList alTermStatus =Cache.getCacheObject("INSStatusTypeID");
           ArrayList alPolicySubType=Cache.getCacheObject("subTypeID");

           //get the reference of the frmPreAuthGeneral to load the Claimant Details
           DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
           DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");


           String strEnrollmentID=(String)frmClaimantDetails.get("enrollmentID");
           String strPolicySeqId=(String)frmClaimantDetails.get("policySeqID");
           if(strPolicySeqId==null)
           {
           	strPolicySeqId="";
           }//end of if(strPolicySeqId==null)
           String strPolicyNbr=(String)frmClaimantDetails.get("policyNbr");
           String strPolicyTypeID=(String)frmClaimantDetails.get("policyTypeID");
           String strPolicySubTypeID=(String)frmClaimantDetails.get("policySubTypeID");
           String strPolicyHolderName=(String)frmClaimantDetails.get("policyHolderName");
           String strTermStatusID=(String)frmClaimantDetails.get("termStatusID");
           String strPhone=(String)frmClaimantDetails.get("phone");
           String strStartDate=(String)frmClaimantDetails.get("startDate");
           String strEndDate=(String)frmClaimantDetails.get("endDate");
           String strProposalDate=(String)frmClaimantDetails.get("proposalDate");
           String strPolicyNbrDisc=(String)frmClaimantDetails.get("policyNbrDisc");
           String strCertificateNo=(String)frmClaimantDetails.get("certificateNo");
           String strInsScheme=(String)frmClaimantDetails.get("insScheme");
           String strViewmode=" Disabled ";
           
           String strReadonly="";
           String strReadOnlyFields="readonly";
           
	       String strClassLarge = "textBox textBoxLarge";
	       String strClassMedium = "textBox textBoxMedium";
	       String strClassSelect = "selectBox selectBoxMedium";
	       String strClassDate = "textBox textDate";
	       if(!strPolicyNbr.equals("") && strPolicyNbr != null)
           {
        	   strClassLarge="textBox textBoxLarge textBoxDisabled";
        	   strClassMedium = "textBox textBoxMedium textBoxDisabled textBoxDisabled";
        	   strClassSelect = "selectBox selectBoxMedium selectBoxDisabled";
        	   strClassDate = "textBox textDate textBoxDisabled";
           }//end of if(!strPolicyNbr.equals("") && strPolicyNbr!="")
           if(strPolicyTypeID.equals("COR"))
           {
        	   strReadonly=" readonly";
           }//end of if(strPolicyTypeID.equals("COR"))
           if(TTKCommon.isAuthorized(request,"Edit")&&strPolicySeqId.equals(""))
           {
               strViewmode="";
           }//end of if(TTKCommon.isAuthorized(request,"Edit"))
           out.print("<fieldset><legend>Scheme Details</legend>");
               out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
                   out.print("<tr>");
                       out.print("<td width=\"22%\" height=\"20\"  class=\""+TTKCommon.compareValues(strPolicyNbrDisc,"N","formLabel","labelRed")+"\">");
                           out.print("Scheme No.: ");
                       out.print("</td>");
                       out.print("<td width=\"30%\" class=\"textLabelBold\">");
                           out.print("<input type=\"text\" name=\"claimantDetailsVO.policyNbr\"  value='"+TTKCommon.getHtmlString(strPolicyNbr)+"' onkeypress=\"ConvertToUpperCase(event.srcElement);\" maxlength=\"60\" class=\""+strClassMedium+"\""+strViewmode+">");
                           out.print("&nbsp;&nbsp;");
                           if(strViewmode.equals("")&&strEnrollmentID.equals(""))
                           {
							  if(!(strActiveLink.equals("Support"))) //koc 11 koc11 //if(!TTKCommon.isAuthorized(request,"Editnot")) //koc 11 koc11
               				{
	                           out.print("<a href=\"#\" onClick=\"selectPolicy();\">");
	                           out.print("<img src=\"/ttk/images/EditIcon.gif\" alt=\"Select Policy\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>&nbsp;&nbsp;<a href=\"#\" onClick=\"clearPolicy();\"><img src=\"/ttk/images/DeleteIcon.gif\" alt=\"Clear Scheme No.\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>");
							}
                           }//end of if(strEnrollmentID.equals(""))

                       out.print("</td>");
                       out.print("<td class=\"formLabel\" width=\"19%\">");
                           out.print("Scheme Type:");
                       out.print("</td>");
					   //added for Decoupling
                       if(!TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
                       {	
                       out.print("<td class=\"textLabel\" width=\"29%\">");
                           if(alEnrollTypeCode != null && alEnrollTypeCode.size() > 0)
                           {
                               out.print("<select name=\"claimantDetailsVO.policyTypeID\" onChange=\"showhideCorporateDet(this);\" CLASS=\""+strClassSelect+"\""+strViewmode+">");
                               out.print("<option value=\"\">Select from list</option>");
                                    for(int i=0; i < alEnrollTypeCode.size(); i++)
                                    {
                                       cacheObject = (CacheObject)alEnrollTypeCode.get(i);
                                       out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strPolicyTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
                                    }//end of for(int i=0; i < alEnrollTypeCode.size(); i++)
                               out.print("</select>");
                           }//end of if(alEnrollTypeCode != null && alEnrollTypeCode.size() > 0)
                           else if(alEnrollTypeCode != null && alEnrollTypeCode.size() == 0)
                           {
                               out.print("<select name=\"claimantDetailsVO.policyTypeID\" CLASS=\""+strClassSelect+"\">");
                               out.print("<option value=\"\">Select from list</option>");
                               out.print("</select>");
                           }//end of else if(alEnrollTypeCode != null && alEnrollTypeCode.size() == 0)
                       out.print("</td>");
						}
                   out.print("</tr>");


                   out.print("<tr>");
                       out.print("<td class=\"formLabel\">");
                           out.print("Term Status:");
                       out.print("</td>");
                       out.print("<td class=\"textLabelBold\">");
                               if(alTermStatus != null && alTermStatus.size() > 0)
                               {
                                   out.print("<select name=\"claimantDetailsVO.termStatusID\" class=\""+strClassSelect+"\""+strViewmode+">");
                                        out.print("<option value=\"\">Select from list</option>");
                                        for(int i=0; i < alTermStatus.size(); i++)
                                        {
                                           cacheObject = (CacheObject)alTermStatus.get(i);
                                           out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strTermStatusID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
                                        }//end of for(int i=0; i < alTermStatus.size(); i++)
                                   out.print("</select>");
                               }//end of if(alTermStatus != null && alTermStatus.size() > 0)
                               else if(alTermStatus != null && alTermStatus.size() == 0)
                               {
                                   out.print("<select name=\"claimantDetailsVO.termStatusID\" class=\""+strClassSelect+"\""+strViewmode+">");
                                   out.print("</select>");
                               }//end of else if(alTermStatus != null && alTermStatus.size() > 0)
                       out.print("</td>");

                       out.print("<td class=\"formLabel\">");
                           out.print("Scheme Sub Type:");
                       out.print("</td>");
                        //added for Decoupling
                       if(!TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
                       {                     
                               out.print("<td class=\"formLabel\">");
                               if(alPolicySubType != null && alPolicySubType.size() > 0)
                                {
                                    out.print("<select name=\"claimantDetailsVO.policySubTypeID\" CLASS=\""+strClassSelect+"\""+strViewmode+">");
                                    out.print("<option value=\"\">Select from list</option>");
                                         for(int i=0; i < alPolicySubType.size(); i++)
                                         {
                                            cacheObject = (CacheObject)alPolicySubType.get(i);
                                            out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strPolicySubTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
                                         }//end of for(int i=0; i < alPrefix.size(); i++)
                                    out.print("</select>");
                                }//end of if(alPolicySubType != null && alPolicySubType.size() > 0)
                                else if(alPolicySubType != null && alPolicySubType.size() == 0)
                                {
                                    out.print("<select name=\"claimantDetailsVO.policySubTypeID\" CLASS=\""+strClassSelect+"\">");
                                    out.print("<option value=\"\">Select from list</option>");
                                    out.print("</select>");
                                }//end of else if(alPolicySubType != null && alPolicySubType.size() > 0)
                       out.print("</td>");
					   }
                   out.print("</tr>");

                   out.print("<tr id=\"polholder\">");
                          out.print("<td class=\"formLabel\">");
                              out.print("Scheme Holder's Name:");
                          out.print("</td>");
                          out.print("<td class=\"textLabel\">");
                              out.print("<input type=\"text\" name=\"claimantDetailsVO.policyHolderName\"  value='"+TTKCommon.getHtmlString(strPolicyHolderName)+"' onkeypress=\"ConvertToUpperCase(event.srcElement);\" maxlength=\"60\""+strReadonly+" class=\""+strClassLarge+"\""+strViewmode+">");
                          out.print("</td>");
                          out.print("<td class=\"formLabel\">");
                              out.print("Phone:");
                          out.print("</td>");
                          //added for Decoupling
                          if(!TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
                          {
								out.print("<td class=\"textLabel\">");
                              out.print("<input type=\"text\" name=\"claimantDetailsVO.phone\"  value='"+TTKCommon.getHtmlString(strPhone)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
                          out.print("</td>");
						  }
                   out.print("</tr>");

                   out.print("<tr>");
                       out.print("<td class=\"formLabel\">");
                           out.print("Scheme Start Date:");
                       out.print("</td>");
                       out.print("<td>");
                       out.print("<input type=\"text\" name=\"claimantDetailsVO.startDate\"  value='"+TTKCommon.getHtmlString(strStartDate)+"' maxlength=\"10\" class=\""+strClassDate+"\""+strViewmode+">");
                       if(TTKCommon.isAuthorized(request,"Edit")&&strEnrollmentID.equals(""))
                        {
                            out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPreAuthGeneral.elements[\\'claimantDetailsVO.startDate\\']',document.frmPreAuthGeneral.elements['claimantDetailsVO.startDate'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                        }//end of if(TTKCommon.isAuthorized(request,"Edit"))
                       out.print("</td>");
                       out.print("<td class=\"formLabel\">");
                        out.print("Scheme End Date:");
                       out.print("</td>");
                       out.print("<td>");
                       out.print("<input type=\"text\" name=\"claimantDetailsVO.endDate\"  value='"+TTKCommon.getHtmlString(strEndDate)+"' maxlength=\"10\" class=\""+strClassDate+"\""+strViewmode+">");
                       if(TTKCommon.isAuthorized(request,"Edit")&&strEnrollmentID.equals(""))
                        {
                            out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPreAuthGeneral.elements[\\'claimantDetailsVO.endDate\\']',document.frmPreAuthGeneral.elements['claimantDetailsVO.endDate'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                        }//end of if(TTKCommon.isAuthorized(request,"Edit"))
                       out.print("</td>");
                   out.print("</tr>");
                   
                   out.print("<tr>");
                   out.print("<td class=\"formLabel\">");
                   out.print("Scheme Name:");
                   out.print("</td>");
				   //added for Decoupling
                   if(!TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
                   {
                   out.print("<td class=\"textLabel\">");
                   out.print("<input type=\"text\" name=\"claimantDetailsVO.insScheme\"  value='"+TTKCommon.getHtmlString(strInsScheme)+"'  maxlength=\"60\""+strReadOnlyFields+" class=\""+strClassMedium+"\"Disabled>");
                   out.print("</td>");
				   }
                   out.print("<td class=\"formLabel\">");
                   out.print("Certificate No.:");
                   out.print("</td>");
                   out.print("<td class=\"textLabel\">");
                   out.print("<input type=\"text\" name=\"claimantDetailsVO.certificateNo\"  value='"+TTKCommon.getHtmlString(strCertificateNo)+"' maxlength=\"25\" "+strReadOnlyFields+" class=\""+strClassMedium+"\"Disabled>");
                   out.print("</td>");
                   out.print("</tr>");
                   
                   
                   /*out.print("<tr>");
                   out.print("<td class=\"formLabel\">");
                   out.print("Proposal Date:");
                   out.print("</td>");
                   out.print("<td class=\"textLabel\">");
                   out.print("<input type=\"text\" name=\"claimantDetailsVO.proposalDate\"  value='"+TTKCommon.getHtmlString(strProposalDate)+"' maxlength=\"10\" class=\""+strClassDate+"\""+strViewmode+">");
                   out.print("</td>");
                   out.print("<td class=\"formLabel\">");
                   out.print("&nbsp;");
                   out.print("</td>");
                   out.print("<td class=\"textLabel\">");
                   out.print("&nbsp;");
                   out.print("</td>");
                   out.print("</tr>");*/
                   
                   
                   out.print("</table>");
                   out.print("</fieldset>");
      }//end of try
      catch(Exception exp)
      {
          exp.printStackTrace();
      }//end of catch block
      return SKIP_BODY;
   }//end of doStartTag
}//end of PolicyDetails




