/**
 * @ (#) InsuranceRelatedInformation.java Feb 3, 2006
 * Project       : TTK HealthCare Services
 * File          : InsuranceRelatedInformation.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 3, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.enrollment;

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


public class InsuranceRelatedInformation  extends TagSupport
{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( InsuredAddressInformation.class );

	public int doStartTag() throws JspException
	{
		try
		{

			log.debug("Inside PolicyInfo TAG :");
			JspWriter out = pageContext.getOut();//Writer object to write the file
			CacheObject cacheObject = null;
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			// 	get the reference of the frmPolicyDetails to load the InsuranceRelated information
			DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			//	get the required Cache Data to load select boxes
			ArrayList alTermStatus =Cache.getCacheObject("INSStatusTypeID");
			ArrayList alPolicySubType=Cache.getCacheObject("productSubType");
			ArrayList alPolicyMedium=Cache.getCacheObject("policyMedium");
			ArrayList alDomiciliaryType=Cache.getCacheObject("floaterNonFloater");
			ArrayList alProductCode=(ArrayList)frmPolicyDetails.get("alProductCode");
			ArrayList alProductChangeYN =Cache.getCacheObject("productChange");
			ArrayList alTariffTypes =Cache.getCacheObject("tariffTypes");

			String strPolicyNbr=(String)frmPolicyDetails.get("policyNbr");
			
			String strPrevPolicyNbr=(String)frmPolicyDetails.get("prevPolicyNbr");
			String strPolicyCategory = (String)frmPolicyDetails.get("policyCategory");
			String strPrevPolicyCategory = (String)frmPolicyDetails.get("previouspolicyCategory");
			String strPolicyCategoryRemarks = (String)frmPolicyDetails.get("policyCategoryRemarks");
			String strPreviousPolicyCategoryRemarks = (String)frmPolicyDetails.get("previouspolicyCategoryRemarks");
			String strPolicyHolderName=(String)frmPolicyDetails.get("policyHolderName");
			String strPANNbr=(String)frmPolicyDetails.get("PANNbr");
			String strINSStatusTypeID=(String)frmPolicyDetails.get("INSStatusTypeID");
			String strProductChangeYN=(String)frmPolicyDetails.get("productChangeYN");
			String strEnrollmentDesc=(String)frmPolicyDetails.get("enrollmentDesc");
			//Ak
			String productAuthority=(String)frmPolicyDetails.get("productAuthority");
			String strSubTypeID=(String)frmPolicyDetails.get("subTypeID");
			String strPolicyMedium=(String)frmPolicyDetails.get("policyMedium");
			String strStartDate=(String)frmPolicyDetails.get("startDate");
			String strStartDate1=(String)frmPolicyDetails.get("startDate1");
			String strEndDate=(String)frmPolicyDetails.get("endDate");
			String strEndDate1=(String)frmPolicyDetails.get("endDate1");
			
			
			String strIssueDate=(String)frmPolicyDetails.get("issueDate");
			String strAgentCode=(String)frmPolicyDetails.get("agentCode");
			String strDevOffCode=(String)frmPolicyDetails.get("devOffCode");
			String strBrokerName=(String)frmPolicyDetails.get("brokerName");
			String strProposalYN=(String)frmPolicyDetails.get("proposalFormYN");
			String strDeclarationDate=(String)frmPolicyDetails.get("declarationDate");
			String strProductSeqID=(String)frmPolicyDetails.get("productSeqID");
			String strDomicilaryTypeID=(String)frmPolicyDetails.get("domicilaryTypeID");
			String strTenure=(String)frmPolicyDetails.get("tenure");
			String strPolicyStatusID=(String)frmPolicyDetails.get("policyStatusID");
			String strSchemeName=(String)frmPolicyDetails.get("schemeName");
			String strPrevSchemeName=(String)frmPolicyDetails.get("prevSchemeName");
			String strPolicyRemarks = (String)frmPolicyDetails.get("policyRemarks");
			String strSwichType = (String)frmPolicyList.get("switchType");
			String strDOBOChangeYN=(String)frmPolicyDetails.get("DOBOChangeYN");
			String strTarifftype=(String)frmPolicyDetails.get("tariffType");
			String strZoneCode=(String)frmPolicyDetails.get("zoneCode");
			// Changes added for CR KOC1170
			String strPolicyHolderCode=(String)frmPolicyDetails.get("policyHolderCode");
			// End changes added for CR KOC1170
		
		
			//officeCode added for policy renewal
			String strOfficeCode=(String)frmPolicyDetails.get("companyName");
			String productNetworkType=(String)frmPolicyDetails.get("productNetworkType");

			String capitationflag=(String)frmPolicyDetails.get("capitationflag");
			String capitationCategory=(String)frmPolicyDetails.get("capitationCategory");
			String portedYN=(String)frmPolicyDetails.get("portedYN");
			String strPredefinedYN=(String)frmPolicyDetails.get("sMEproductYN");
			String vatPercent=(String)frmPolicyDetails.get("vatPercent");
			String refundedYN=(String)frmPolicyDetails.get("refundedYN");
			String intermediatryId=(String)frmPolicyDetails.get("intermediatryId");
			String intermediatryFee=(String)frmPolicyDetails.get("intermediatryFee");
			
			
			String premiumDatesFlag=(String)frmPolicyDetails.get("premiumDatesFlag");
			String sponsorType = (String)frmPolicyDetails.get("sponsorType");
			String sponsorID = (String)frmPolicyDetails.get("sponsorID");
		
			String strViewmode=" Disabled ";
			if(TTKCommon.isAuthorized(request,"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
			{
				strViewmode="";
			}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
			String subTypeViewmode=" Disabled ";
			if(TTKCommon.isAuthorized(request,"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
			{
				if("Corporate".equals(strEnrollmentDesc))
				{
					subTypeViewmode="";
				}
			}	
			out.print("<fieldset><legend>Insurance Related Information</legend>");
			out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
			out.print("<tr>");
			out.print("<td width=\"21%\" nowrap class=\"formLabel\">");
			out.print("Policy No.: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");

			out.print("<td width=\"33%\" nowrap >");
			out.print("<input type=\"text\" name=\"policyNbr\"  onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strPolicyNbr)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
			out.print("</td>");
			out.print("<td width=\"23%\" nowrap class=\"formLabel\">");
			out.print("Previous Policy No.: ");
			out.print("</td>");
			out.print("<td width=\"23%\" >");
			out.print("<input type=\"text\" name=\"prevPolicyNbr\" disabled=\"disabled\"  onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strPrevPolicyNbr)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td width=\"21%\" nowrap class=\"formLabel\">");
			out.print("Policy Category: ");
			out.print("</td>");
			out.print("<td width=\"33%\" nowrap >");
			out.print(strPolicyCategory);
			out.print("</td>");
			out.print("<td width=\"23%\" nowrap class=\"formLabel\">");
			out.print("Previous Policy Category:");
			out.print("</td>");
			out.print("<td width=\"23%\" >");
			out.print("<input type=\"text\" name=\"previouspolicyCategory\"  onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strPrevPolicyCategory)+"' maxlength=\"60\" readOnly=\"true\" class=\"textBox textBoxMedium textBoxDisabled\""+strViewmode+">");
			out.print("</td>");
			

			out.print("<tr>");

			out.print("<td></td><td></td>");
			out.print("<td width=\"21%\" nowrap class=\"formLabel\">");
			out.print("Ported Policy: "); //out.print(portedYN);
			out.print("</td>");	
			out.print("<td>");	
			if("Y".equals(portedYN))
			{
				out.print("<input type=\"checkbox\" name=\"portedYN\" value=\"Y\"  onClick=\"javascript:termStatus();\"  checked>");
			}
			else
			{
				out.print("<input type=\"checkbox\" name=\"portedYN\" onClick=\"javascript:termStatus();\" value=\"Y\">");
			}
			out.print("</td>");	
			out.print("</tr>");
			
			out.print("<tr>");
			out.print("<td width=\"21%\" nowrap class=\"formLabel\">");
			out.print("Policy Category Remarks: ");
			out.print("</td>");
			out.print("<td width=\"23%\" >");
			out.print(strPolicyCategoryRemarks);
			out.print("</td>");
			out.print("<td width=\"23%\" nowrap class=\"formLabel\">");
			out.print("Previous Policy Category Remarks: ");
			out.print("</td>");
			out.print("<td width=\"23%\" >");
			out.print("<input type=\"text\" name=\"previouspolicyCategoryRemarks\"  onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strPreviousPolicyCategoryRemarks)+"' maxlength=\"60\" readOnly=\"true\" class=\"textBox textBoxMedium textBoxDisabled\""+strViewmode+">");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))
			{
				out.print("Policy Holder's Name: ");
				out.print("<span class=\"mandatorySymbol\">*</span>");
			}//end of if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))
			/*else
			{
				out.print("Trade License No.: ");
			}//end of else if */
			out.print("</td>");

			out.print("<td>");
			if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))
			{
				out.print("<input type=\"text\" name=\"policyHolderName\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strPolicyHolderName)+"' maxlength=\"60\" class=\"textBox textBoxLarge\""+strViewmode+">");
			}/*else
			{
				out.print("<input type=\"text\" name=\"PANNbr\"  value='"+TTKCommon.getHtmlString(strPANNbr)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
			}*/
			out.print("</td>");

			out.print("<td class=\"formLabel\">");
			out.print("Term Status: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");
			out.print("<td>");
            if(strINSStatusTypeID.equals("RTS")){
            	out.print("<select name=\"INSStatusTypeID\" class=\"selectBox selectBoxMedium\" disabled=\" disabled \">");
				out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alTermStatus.size(); i++)
				{
					cacheObject = (CacheObject)alTermStatus.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strINSStatusTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alTermStatus.size(); i++)
				out.print("</select>");
            }else{
			if(alTermStatus != null && alTermStatus.size() > 0)
			{
				out.print("<select name=\"INSStatusTypeID\" class=\"selectBox selectBoxMedium\" onChange=\"javascript:portedUncheck();\""+strViewmode+">");
				out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alTermStatus.size(); i++)
				{
					cacheObject = (CacheObject)alTermStatus.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strINSStatusTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alTermStatus.size(); i++)
				out.print("</select>");
			}//end of if(alTermStatus != null && alTermStatus.size() > 0)
			else if(alTermStatus != null && alTermStatus.size() == 0)
			{
				out.print("<select name=\"INSStatusTypeID\" class=\"selectBox selectBoxMedium\"onChange=\"javascript:portedUncheck();\""+strViewmode+">");
				out.print("</select>");
			}//end of else if(alTermStatus != null && alTermStatus.size() > 0)
         }
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td width=\"21%\" nowrap class=\"formLabel\">");
			out.print("Sponsor type.: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");

			out.print("<td width=\"33%\" nowrap >");
			
			out.print("<select id='sponsorType' name=\"sponsorType\"  value=\""+sponsorType+"\" CLASS=\"selectBox selectBoxMedium\" >");
            if(sponsorType==null || sponsorType.equals("")){
            out.print("<option value=\"\" >Select from list</option>");
            out.print("<option value='"+"person"+"'>Person</option>");
            out.print("<option value='"+"org"+"'>Organization</option>");
            }
            else{
                String name=null;
                if(sponsorType.equals("person")){
                    name="Person";
                }else if(sponsorType.equals("org")){
                    name="Organization";
                }
                out.print("<option value=\"\" >Select from list</option>");
                out.print("<option value="+sponsorType+" selected >"+name+"</option>");
                if(sponsorType.equals("person")){
                    out.print("<option value='"+"org"+"'>Organization</option>");}
                else if(sponsorType.equals("org")){
                	out.print("<option value='"+"person"+"'>Person</option>");
                }
            }
            out.print("</select>"); 
			out.print("</td>");
			out.print("<td width=\"21%\" nowrap class=\"formLabel\">");
			out.print("Sponsor ID: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");

			out.print("<td width=\"33%\" nowrap >");
			out.print("<input type=\"text\" name=\"sponsorID\"\" value='"+TTKCommon.getHtmlString(sponsorID)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("Policy Type.: ");
			out.print("</td>");
			out.print("<td class=\"formLabelBold\">");
			out.print(strEnrollmentDesc);
			out.print("</td>");
			out.print("<td class=\"formLabel\">");
			out.print("Policy Sub Type: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			
			out.print("</td>");
			
			out.print("<td>");
			if(alPolicySubType != null && alPolicySubType.size() > 0)
			{
				out.print("<select name=\"subTypeID\" CLASS=\"selectBox selectBoxMedium\""+subTypeViewmode+">");
				for(int i=0; i < alPolicySubType.size(); i++)
				{
					cacheObject = (CacheObject)alPolicySubType.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strSubTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alPrefix.size(); i++)
				out.print("</select>");
			}//end of if(alPolicySubType != null && alPolicySubType.size() > 0)
			else if(alPolicySubType != null && alPolicySubType.size() == 0)
			{
				out.print("<select name=\"subTypeID\" CLASS=\"selectBox selectBoxMedium\""+subTypeViewmode+">");
				out.print("<option value=\"\">Select from list</option>");
				out.print("</select>");
			}//end of else if(alPolicySubType != null && alPolicySubType.size() > 0)

			out.print("</td>");
			out.print("</tr>");


			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("Policy Start Date: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type=\"text\" name=\"startDate\"  value='"+TTKCommon.getHtmlString(strStartDate)+"' maxlength=\"10\" onblur=\"changeDate()\" class=\"textBox textDate\""+strViewmode+">");
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
				out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPolicyDetails.startDate',document.frmPolicyDetails.startDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
			}
			out.print("</td>");

			out.print("<td class=\"formLabel\">");
			out.print("Policy End Date: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");

			out.print("<td>");
			if(strSwichType.equalsIgnoreCase("ENM")){
				if(strPolicyStatusID.equals("POA")&&(strTenure!=null))
				{
					out.print("<input type=\"text\" name=\"endDate\"  value='"+TTKCommon.getHtmlString(strEndDate)+"' maxlength=\"10\" readonly class=\"textBox textDate\">");
				}else
				{
					out.print("<input type=\"text\" name=\"endDate\"  value='"+TTKCommon.getHtmlString(strEndDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
					if(TTKCommon.isAuthorized(request,"Edit"))
					{
						out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPolicyDetails.endDate',document.frmPolicyDetails.endDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
					}
				}
			}//end of if(strSwichType.equalsIgnoreCase("ENM"))
			else{
				out.print("<input type=\"text\" name=\"endDate\"  value='"+TTKCommon.getHtmlString(strEndDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
				if(TTKCommon.isAuthorized(request,"Edit"))
				{
					out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPolicyDetails.endDate',document.frmPolicyDetails.endDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
				}
			}//end of else
			

			out.print("</td>");

			out.print("</tr>");
			
			if(strActiveSubLink.equals("Individual Policy"))
			{
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("</td>");
			out.print("<td class=\"formLabelBold\">");
			out.print("</td>");
			out.print("<td class=\"formLabel\">");
			out.print("Policy medium: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");
			out.print("<td>");
			if(alPolicyMedium != null && alPolicyMedium.size() > 0)
			{
				out.print("<select name=\"policyMedium\" CLASS=\"selectBox selectBoxMedium\""+strViewmode+">");
				out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alPolicyMedium.size(); i++)
				{
					cacheObject = (CacheObject)alPolicyMedium.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strPolicyMedium, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alPrefix.size(); i++)
				out.print("</select>");
			}//end of if(alPolicySubType != null && alPolicySubType.size() > 0)
			else if(alPolicyMedium != null && alPolicyMedium.size() == 0)
			{
				out.print("<select name=\"policyMedium\" CLASS=\"selectBox selectBoxMedium\">");
				out.print("<option value=\"\">Select from list</option>");
				out.print("</select>");
			}//end of else if(alPolicySubType != null && alPolicySubType.size() > 0)

			out.print("</td>");
			out.print("</tr>");
			}
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			/* added by rekha for policy renewal*/
			
			if(strOfficeCode.equals("CIGNA INSURANCE COMPANY"))
			{
			
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("Policy  Actual Start Date(Long Term Policy): ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type=\"text\" name=\"startDate1\"  value='"+TTKCommon.getHtmlString(strStartDate1)+"' maxlength=\"10\" onblur=\"changeDate1()\" class=\"textBox textDate\""+strViewmode+">");
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
				out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPolicyDetails.startDate1',document.frmPolicyDetails.startDate1.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
			}
			out.print("</td>");

			out.print("<td class=\"formLabel\">");
			out.print("Policy Actual End Date (Long Term Policy): ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");

			out.print("<td>");
			if(strSwichType.equalsIgnoreCase("ENM")){
				if(strPolicyStatusID.equals("POA")&&(strTenure!=null))
				{
					out.print("<input type=\"text\" name=\"endDate1\"  value='"+TTKCommon.getHtmlString(strEndDate1)+"' maxlength=\"10\" readonly class=\"textBox textDate\">");
				}else
				{
					out.print("<input type=\"text\" name=\"endDate1\"  value='"+TTKCommon.getHtmlString(strEndDate1)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
					if(TTKCommon.isAuthorized(request,"Edit"))
					{
						out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPolicyDetails.endDate1',document.frmPolicyDetails.endDate1.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
					}
				}
			}//end of if(strSwichType.equalsIgnoreCase("ENM"))
			else{
				out.print("<input type=\"text\" name=\"endDate1\"  value='"+TTKCommon.getHtmlString(strEndDate1)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
				if(TTKCommon.isAuthorized(request,"Edit"))
				{
					out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPolicyDetails.endDate1',document.frmPolicyDetails.endDate1.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
				}
			}//end of else
			

			out.print("</td>");

			out.print("</tr>");

			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			/* added by rekha for policy renewal*/
			}
			
			
			
			out.print("Policy Issue Date: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type=\"text\" name=\"issueDate\"  value='"+TTKCommon.getHtmlString(strIssueDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+" onblur=\"comparewithpolicystartandenddate()\">");
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
				out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPolicyDetails.issueDate',document.frmPolicyDetails.issueDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
			}
			out.print("</td>");

			out.print("<td class=\"formLabel\">");
			out.print("Broker Code:");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type=\"text\" name=\"devOffCode\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strDevOffCode)+"' maxlength=\"10\" class=\"textBox textBoxMedium\">");
			out.print("</td>");
			out.print("</tr>");
			
			if(strActiveSubLink.equals("Individual Policy"))
			{
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("</td>");
			out.print("<td class=\"formLabelBold\">");
			out.print("</td>");
			out.print("<td class=\"formLabel\">");
			out.print("Broker Name: ");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type=\"text\" name=\"brokerName\"\" value='"+TTKCommon.getHtmlString(strBrokerName)+"' \" class=\"textBox textBoxMedium\">");
			out.print("</td>");
			out.print("</tr>");
			}
			if(strActiveSubLink.equals("Non-Corporate Policy"))
			{
				out.print("<tr>");
				out.print("<td class=\"formLabel\">");
				out.print("Policy Name:");
				out.print("<span class=\"mandatorySymbol\">*</span>");
				out.print("</td>");

				out.print("<td>");
				out.print("<input type=\"text\" name=\"schemeName\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strSchemeName)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");

				out.print("</td>");

				out.print("<td class=\"formLabel\">");
				out.print("Previous Policy Name:");
				out.print("</td>");

				out.print("<td>");
				out.print("<input type=\"text\" name=\"prevSchemeName\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strPrevSchemeName)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
				out.print("</td>");
				out.print("</tr>");
			}//end of if(strActiveSubLink.equals("Non-Corporate Policy"))

			if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))
			{
				out.print("<tr>");
				out.print("<td class=\"formLabel\">");
				out.print("Proposal Form: ");
				out.print("</td>");
				out.print("<td>");
				out.print("<input type=\"checkbox\" name=\"proposalFormYN\" value=\"Y\" "+TTKCommon.isChecked(strProposalYN) +strViewmode+">" );
				//out.print("<input type=\"hidden\" name=\"proposalFormYN\"  value=\"\">");
				out.print("</td>");

				out.print("<td class=\"formLabel\">");
				out.print("Proposal Date: ");
				out.print("</td>");
				out.print("<td>");
				out.print("<input type=\"text\" name=\"declarationDate\"  value='"+TTKCommon.getHtmlString(strDeclarationDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
				if(TTKCommon.isAuthorized(request,"Edit"))
				{
					out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPolicyDetails.declarationDate',document.frmPolicyDetails.declarationDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
				}
				out.print("</td>");
				out.print("</tr>");
			}//end of if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))

			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("Product Change: ");
			out.print("</td>");
			out.print("<td>");
			
			
			if(alProductChangeYN != null && alProductChangeYN.size() > 0)
			{
				out.print("<select name=\"productChangeYN\" class=\"selectBox selectBoxMedium\"  disabled=\"disabled\" "+strViewmode+">");
				out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alProductChangeYN.size(); i++)
				{
					cacheObject = (CacheObject)alProductChangeYN.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strProductChangeYN, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alTermStatus.size(); i++)
				out.print("</select>");
			}//end of if(alTermStatus != null && alTermStatus.size() > 0)
			else if(alProductChangeYN != null && alProductChangeYN.size() == 0)
			{
				out.print("<select name=\"productChangeYN\" class=\"selectBox selectBoxMedium\" disabled=\"disabled\" "+strViewmode+">");
				out.print("</select>");
			}//end of else if(alProductChangeYN != null && alProductChangeYN.size() > 0)
			out.print("</td>");
			
			out.print("<td width=\"23%\" nowrap class=\"formLabel\">");
			out.print("Product/Policy Authority :");
			out.print("</td>");
			out.print("<td width=\"23%\" >");
			out.print(productAuthority);
			out.print("</td>");
			
			//out.print("</td>");
			
			
			/*out.print("<td class=\"formLabel\">");
			out.print("Domiciliary Type (OPD): ");
			out.print("</td>");
			out.print("<td>");
			if(alDomiciliaryType != null && alDomiciliaryType.size() > 0){
				out.print("<select name=\"domicilaryTypeID\" CLASS=\"selectBox selectBoxMedium\""+strViewmode+">");
				out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alDomiciliaryType.size(); i++){
					cacheObject = (CacheObject)alDomiciliaryType.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strDomicilaryTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alDomiciliaryType(); i++)
				out.print("</select>");
			}//end of if(alDomiciliaryType != null && alDomiciliaryType() > 0)
			else if(alDomiciliaryType != null && alDomiciliaryType.size() == 0){
				out.print("<select name=\"domicilaryTypeID\" CLASS=\"selectBox selectBoxMedium\">");
				out.print("<option value=\"\">Select from list</option>");
				out.print("</select>");
			}//end of else if(alDomiciliaryType != null && alDomiciliaryType() == 0)
			out.print("</td>");
			*/
			
			out.print("<td class=\"formLabel\">");
			//out.print("Re-Insurer Name/Code:");
			
			out.print("</td>");
			out.print("<td>");
			//out.print("<input type=\"text\" name=\"reInsurer\" id=\"reInsurer\" class=\"textBox textBoxMedium\" value='"+TTKCommon.getHtmlString(strReInsurer)+"'>");
			
			out.print("</td>");	
			out.print("</tr>");
			out.print("<tr>");
			out.print("</tr>");
			
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("Product Name: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");
			out.print("<td>");
			if(alProductCode != null && alProductCode.size() > 0)
			{
				out.print("<select id='productSeqID' name=\"productSeqID\" CLASS=\"selectBox selectBoxLargest\"  disabled=\"disabled\""+strViewmode+" onchange='getNetworkType()'>");
				out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alProductCode.size(); i++)
				{
					cacheObject = (CacheObject)alProductCode.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strProductSeqID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alProductCode.size(); i++)
				out.print("</select>");
			}//end of if(alProductCode != null && alProductCode.size() > 0)
			else if(alProductCode != null && alProductCode.size() == 0)
			{
				out.print("<select name=\"ProductSeqID\" CLASS=\"selectBox selectBoxMedium\"  disabled=\"disabled\"  onchange='getNetworkType()'>");
				out.print("<option value=\"\">Select from list</option>");
				out.print("</select>");
			}//end of else if(alProductCode != null && alProductCode.size() == 0)
			out.print("</td>");
			out.print("<td class=\"formLabel\" colspan=\"2\">");
			
			//out.print("Risk Coverage: "); 
			//out.print("&nbsp;&nbsp;&nbsp;&nbsp;");
			
			//out.print("Re-Insurer Share: "); 
			//out.print("<span class=\"mandatorySymbol\">*</span>&nbsp;&nbsp;");
			//out.print("<input type=\"text\" name=\"reInsShare\"  value='"+TTKCommon.getHtmlString(strReInsShare)+"' maxlength=\"5\" class=\"textBox textBoxSmallest\""+strViewmode+" onblur=\"calcPercentage()\" onkeyup=\"isNumeric(this)\">");
			//out.print("%");
			
			//out.print("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			//out.print("Insurer Share: "); 
			//out.print("<span class=\"mandatorySymbol\">*</span>&nbsp;&nbsp;");
			//out.print("<input type=\"text\" name=\"insShare\"  value='"+TTKCommon.getHtmlString(strInsShare)+"' maxlength=\"5\" class=\"textBox textBoxSmallest\""+strViewmode+" onblur=\"calcPercentage()\" onkeyup=\"isNumeric(this)\" >");
			//out.print("%");	
			
			out.print("</td>");
			out.print("<td>");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");			
			out.print("<td class=\"formLabel\">");
			out.print("Tariff Type: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");
			out.print("<td>");
			
			if(alTariffTypes!= null && alTariffTypes.size() > 0)
			{
				out.print("<select name=\"tariffType\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
				out.print("<option value=''>Select from list</option>");
				for(int i=0; i < alTariffTypes.size(); i++)
				{
					cacheObject = (CacheObject)alTariffTypes.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strTarifftype, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alTermStatus.size(); i++)
				out.print("</select>");
			}//end of if(alTermStatus != null && alTermStatus.size() > 0)
			else if(alTariffTypes != null && alTariffTypes.size() == 0)
			{
				out.print("<select name=\"tariffType\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
				out.print("</select>");
			}//end of else if(alTariffTypes != null && alTariffTypes.size() > 0)
			out.print("</td>");			
			out.print("<td class=\"formLabel\">");			
			out.print("Dev. Officer Code:");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type=\"text\" name=\"agentCode\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strAgentCode)+"' maxlength=\"10\" class=\"textBox textBoxMedium\">");
			out.print("</td>");
			out.print("</tr>");
			
			
			
			out.print("<tr>");		
			out.print("<td class=\"formLabel\">");
			out.print("Predefined Product: ");
			out.print("</td>");
			out.print("<td>");
			out.print("<strong>");
			out.print("<div>");
			//out.print(productNetworkType);
			out.print(strPredefinedYN);
			out.print("</div>");
			out.print("</strong>");
			out.print("</td>");
			
			
			if("Y".equals(premiumDatesFlag)){
			out.print("<td class=\"formLabel\">");
			out.print("VAT(%): ");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type=\"text\" name=\"vatPercent\" onkeyup=\"isNumeric(this);\" value='"+TTKCommon.getHtmlString(vatPercent)+"' maxlength=\"5\" readOnly=\"true\"  class=\"textBox textBoxSmall textBoxDisabled\""+strViewmode+">");
			out.print("</td>");
			}
			out.print("</tr>");
			
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("Zone Code:");
			out.print("</td>");
			out.print("<td>");
			out.print("<input type=\"text\" name=\"zoneCode\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strZoneCode)+"' maxlength=\"100\" class=\"textBox textBoxMedium\">");
			out.print("</td>");		
			// Changes done for CR KOC1170			
			out.print("<td width=\"21%\" nowrap class=\"formLabel\">");
			out.print("Policy Holder Code:");
			out.print("</td>");
			out.print("<td width=\"33%\" nowrap >");
			out.print("<input type=\"text\" name=\"policyHolderCode\"  onkeypress=\"ConvertToUpperCase(event.srcElement);\" value='"+TTKCommon.getHtmlString(strPolicyHolderCode)+"' maxlength=\"60\" class=\"textBox textBoxMedium\">");
			out.print("</td>");
			out.print("</tr>");
			out.print("<tr>");			
    /*	    out.print("<td class=\"formLabel\">");
			out.print("DO/BO Change: ");
			out.print("</td>");
			out.print("<td>");
			
			if(alProductChangeYN != null && alProductChangeYN.size() > 0)
			{
				out.print("<select name=\"DOBOChangeYN\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
				out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alProductChangeYN.size(); i++)
				{
					cacheObject = (CacheObject)alProductChangeYN.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strDOBOChangeYN, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alTermStatus.size(); i++)
				out.print("</select>");
			}//end of if(alTermStatus != null && alTermStatus.size() > 0)
			else if(alProductChangeYN != null && alProductChangeYN.size() == 0)
			{
				out.print("<select name=\"DOBOChangeYN\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
				out.print("</select>");
			}//end of else if(alProductChangeYN != null && alProductChangeYN.size() > 0)
			out.print("</td>");	*/
			
			out.print("<td class=\"formLabel\">");
			out.print("Product Network Type: ");
			out.print("</td>");
			out.print("<td>");
			out.print("<strong><div id='networkType'>");
			out.print("<div>");
			out.print(productNetworkType);
			out.print("</div>");
			out.print("</div></strong>");
			out.print("</td>");
			out.print("</tr>");
			
			out.print("<tr>");
		/*	out.print("<td class=\"formLabel\">");
			out.print("Administration Policy Type: ");
			out.print("</td>");
			out.print("<td>");
			out.print("<strong>");
		
			if("2".equals(capitationflag)) out.print("AS Plus(OP)");
			else if("3".equals(capitationflag)) out.print("AS Plus(IP & OP)");
			else out.print("ASO");
			
			out.print("</strong>");
			out.print("</td>");   */
			
			
			
			out.print("<td class=\"formLabel\">");
			out.print("Premium to be refunded on policy cancellation: ");
			out.print("</td>");
			out.print("<td>");
			out.print("<strong>");
			out.print(refundedYN);
			out.print("</strong>");
			out.print("</td>");
			
			out.print("</tr>");
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("Intermediary Id: ");
			if("DHA".equals(productAuthority)){
			out.print("<span class=\"mandatorySymbol\">*</span>");
			}
			out.print("</td>");
			out.print("<td>");
			out.print("<input type=\"text\" maxlength=\"30\" name=\"intermediatryId\"  value='"+TTKCommon.getHtmlString(intermediatryId)+"' maxlength=\"100\" class=\"textBox textBoxMedium\">");
			out.print("</td>");
			
//			out.print("<td class=\"formLabel\" style=\"display=none;\">");
//			out.print("Intermediary Fee: ");
//			out.print("<span class=\"mandatorySymbol\">*</span>");
//			out.print("</td>");
//			out.print("<td style=\"display=none;\">");
//			out.print("<input type=\"text\" maxlength=\"2\" name=\"intermediatryFee\" onkeyup=\"isNumeric(this);\" value='"+TTKCommon.getHtmlString(intermediatryFee)+"' maxlength=\"100\" class=\"textBox textBoxMedium\">");
//			out.print("</td>");
			
			
			out.print("</tr>");
			/*out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("Administration Category : ");
			out.print("</td>");
			out.print("<td>");
			out.print("<strong>");
			out.print(capitationCategory);
			out.print("</strong>");
			out.print("</td>");
			out.print("<td></td><td></td>");
			out.print("</tr>");*/
			
				
		
			// End changes done for CR KOC1170			
			out.print("<tr>");
			out.print("<td class=\"formLabel\">");
			out.print("Policy Remarks: ");
			out.print("</td>");
			out.print("<td colspan=\"3\" class=\"textLabel\">");
			out.print("<textarea name=\"policyRemarks\" class=\"textBox textAreaLong\""+strViewmode+">");
			out.print(TTKCommon.getHtmlString(strPolicyRemarks));
			out.print("</textarea>");
			out.print("</td>");
			out.print("</td>");
			out.print("</tr>");			
			out.print("</table>");
			out.print("</fieldset>");

		}//end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag
}//end of HealthcareRelatedInformation