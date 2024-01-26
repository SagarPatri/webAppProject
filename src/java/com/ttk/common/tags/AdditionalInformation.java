/**
 * @ (#) AdditionalInformation.java Jan 10, 2006
 * Project       : TTK HealthCare Services
 * File          : AdditionalInformation.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Jan 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;

/**
 *  This class builds the additional information of individual
 */
public class AdditionalInformation extends TagSupport
{
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger( AdditionalInformation.class );
	public int doStartTag() throws JspException {
		try
		{
			log.debug("Inside AdditionalInformation TAG :");
			JspWriter out = pageContext.getOut();
			String strActiveSubLink=TTKCommon.getActiveSubLink((HttpServletRequest)pageContext.getRequest());
			// get the reference of the frmUserContact
			DynaActionForm generalForm=(DynaActionForm)((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("frmUserContact");
			DynaActionForm additionalInfoForm=(DynaActionForm)generalForm.get("additionalInfoVO");
			ArrayList alOfficeInfo=null;
			ArrayList alDepartment=new ArrayList();
			CacheObject cacheObject = null;
			alOfficeInfo=Cache.getCacheObject("officeInfo");
			alDepartment=Cache.getCacheObject("departmentID");
//			String strContactTypeID="",residentYN="";
			String strEmployeeNbr="",strOfficeSeqID="",strDepartmentID="",strDateofJoining="";
			String strUserManagement="User Management";
			String strHospital="Hospital";
			String strPartner="Partner";
			String strViewmode=" Disabled ";
			if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
			{
				strViewmode="";
			}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
			
			if(strActiveSubLink.equals(strUserManagement))
			{
				strEmployeeNbr=TTKCommon.checkNull((String)additionalInfoForm.get("employeeNbr"));
				String strContactPALimit=TTKCommon.checkNull((String)additionalInfoForm.get("contactPALimit"));
				String strContactClaimLimit=TTKCommon.checkNull((String)additionalInfoForm.get("contactClaimLimit"));
				strDepartmentID=TTKCommon.checkNull((String)additionalInfoForm.get("departmentID"));
				strOfficeSeqID=TTKCommon.checkNull((String)additionalInfoForm.get("officeSeqID"));
				DynaActionForm userAccessInfoForm=(DynaActionForm)generalForm.get("userAccessVO");
				String strUserType=TTKCommon.checkNull((String)userAccessInfoForm.getString("userType"));
				String strSftcpyAcc=TTKCommon.checkNull((String)additionalInfoForm.get("softcopyAccessYN"));
				String strSftcpyOtBra=TTKCommon.checkNull((String)additionalInfoForm.get("softcopyOtBranch"));
			    strDateofJoining=TTKCommon.checkNull((String)additionalInfoForm.get("dateOfJoining"));
			    String strDateofResignation=TTKCommon.checkNull((String)additionalInfoForm.get("dateOfResgn"));
				// Added new block if it is ttk user then only it will apply
			   
			 	if(strUserType.equals("TTK") )
				{
					out.print("<fieldset><legend>Approval Limit Information</legend>");
					out.print("<table align=\"center\" class=\"formContainer\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
					out.print("<tr>");
					out.print("<td width=\"22%\" class=\"formLabel\">");
					out.print("Cashless (Rs):");
					out.print("</td>");
					out.print("<td width=\"35%\">");
					out.print("<input type=\"text\" name=\"additionalInfoVO.contactPALimit\"  value='"+TTKCommon.getHtmlString(strContactPALimit)+"' maxlength=\"13\" class=\"textBox textBoxSmall\""+strViewmode+">");
					out.print("</td>");
					out.print("<td width=\"18%\" class=\"formLabel\">");
					out.print("Claim Processing (Rs):");
					out.print("</td>");
					out.print("<td>");
					out.print("<input type=\"text\" name=\"additionalInfoVO.contactClaimLimit\"  value='"+TTKCommon.getHtmlString(strContactClaimLimit)+"' maxlength=\"13\" class=\"textBox textBoxSmall\""+strViewmode+">");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
					out.print("</fieldset>");
				}//end of if(strUserType.equals("TTK"))
				if(!strUserType.equals("INS")&&!strUserType.equals("COR")&&!strUserType.equals("BRO"))
				{
					
					out.print("<fieldset><legend>Additional Information</legend>");
					out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
					if(strUserType.equals("TTK"))
					{
						out.print("<tr>");
						out.print("<td width=\"22%\" CLASS=\"formLabel\">");
						out.print("Employee No.:");
						out.print(" <span class=\"mandatorySymbol\">*</span>");
						out.print("</td>");
						
						out.print("<td width=\"35%\">");
						out.print("<input type=\"text\" name=\"additionalInfoVO.employeeNbr\"  value='"+TTKCommon.getHtmlString(strEmployeeNbr)+"' maxlength=\"10\" class=\"textBox textBoxMedium\""+strViewmode+">");
						out.print("</td>");
						
						out.print("<td width=\"18%\" CLASS=\"formLabel\">");
						out.print("Primary Vidal Health Branch:");
						out.print(" <span class=\"mandatorySymbol\">*</span>");
						out.print("</td>");
						
						out.print("<td class=\"formLabel\">");
						if(alOfficeInfo != null && alOfficeInfo.size() > 0)
						{
							out.print("<select name=\"additionalInfoVO.officeSeqID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
							for(int i=0; i < alOfficeInfo.size(); i++)
							{
								cacheObject = (CacheObject)alOfficeInfo.get(i);
								out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strOfficeSeqID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
							}//end of for(int i=0; i < alOfficeInfo.size(); i++)
							out.print("</select>");
						}//end of if(alOfficeInfo != null && alOfficeInfo.size() > 0)
						else if(alOfficeInfo != null && alOfficeInfo.size() == 0)
						{
							out.print("<select name=\"additionalInfoVO.officeSeqID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
							out.print("</select>");
						}//end of else if(alOfficeInfo != null && alOfficeInfo.size() == 0)
						out.print("</td>");
						out.print("</tr>");
						
						out.print("<tr  CLASS=\"formLabel\">");
						out.print("<td  CLASS=\"formLabel\">");
						out.print("Department:");
						out.print(" <span class=\"mandatorySymbol\">*</span>");
						out.print("</td>");
						
						out.print("<td class=\"formLabel\">");
						if(alDepartment != null && alDepartment.size() > 0)
						{
							out.print("<select name=\"additionalInfoVO.departmentID\" class=\"selectBox selectBoxMedium\""+strViewmode+" onChange=\"javascript:deptchange();\"> ");
							out.print("<option value=\"\">Select from list</option>");
							for(int i=0; i < alDepartment.size(); i++)
							{
								cacheObject = (CacheObject)alDepartment.get(i);
								out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strDepartmentID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
							}//end of for(int i=0; i < alDepartment.size(); i++)
							out.print("</select>");
						}//end of if(alDepartment != null && alDepartment.size() > 0)
						else if(alDepartment != null && alDepartment.size() == 0)
						{
							out.print("<select name=\"additionalInfoVO.departmentID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
							out.print("</select>");
						}//end of else if(alDepartment != null && alDepartment.size() == 0)
						out.print("</td>");
						out.print("<td width=\"22%\" CLASS=\"formLabel\">");
						out.print("Softcopy Access:");
						out.print("</td>");

						out.print("<td width=\"35%\">");
						out.print("<input type=\"checkbox\" id=\"softcopyAccessYNFlag\" name=\"additionalInfoVO.softcopyAccessYN\"  value=\"Y\"  onClick=\"javascript:softcopyAccess()\" "+TTKCommon.isChecked(strSftcpyAcc) +strViewmode+">");
						out.print("</td>");

						out.print("</tr>");
						out.print("<tr>");
						if(strDepartmentID.equals("ENR"))
						{
							out.print("<td width=\"22%\" CLASS=\"formLabel\">");
							out.print("Softcopy Other Branch:");
							out.print("</td>");

							out.print("<td width=\"35%\">");
							out.print("<input type=\"checkbox\" id=\"softcopyOtBranchFlag\" name=\"additionalInfoVO.softcopyOtBranch\"  value=\"Y\" onClick=\"javascript:softcopyBranch()\" "+TTKCommon.isChecked(strSftcpyOtBra) +strViewmode+">");
							out.print("</td>");
						}//end of if(strDepartmentID.equals("ENR"))
						out.print("<td  CLASS=\"formLabel\">");
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr  CLASS=\"formLabel\">");
						out.print("<td  CLASS=\"formLabel\">");
						out.print("Date Of Joining:");
						out.print(" <span class=\"mandatorySymbol\">*</span>");
						out.print("</td>");
						out.print("<td width=\"35%\">");
						out.print("<input type=\"text\" name=\"additionalInfoVO.dateOfJoining\"  value='"+TTKCommon.getHtmlString(strDateofJoining)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
                        out.print("<a name=\"CalendarObjectdojDate\" id=\"CalendarObjectdojDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdojDate','frmUserContact.[\\'additionalInfoVO.dateOfJoining\\']',document.frmUserContact.elements['additionalInfoVO.dateOfJoining'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" title=\"Calendar\" name=\"dojDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                        out.print("</td>");
                        out.print("<td  CLASS=\"formLabel\">");
						out.print("Date Of Resignation:");
						out.print("</td>");
						out.print("<td width=\"35%\">");
						out.print("<input type=\"text\" name=\"additionalInfoVO.dateOfResgn\"  value='"+TTKCommon.getHtmlString(strDateofResignation)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
                        out.print("<a name=\"CalendarObjectdorDate\" id=\"CalendarObjectdorDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdorDate','frmUserContact.[\\'additionalInfoVO.dateOfResgn\\']',document.frmUserContact.elements['additionalInfoVO.dateOfResgn'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" title=\"Calendar\" name=\"dorDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                        out.print("</td>");
                        out.print("</tr>");
					}//end of if(strUserType.equals("TTK"))
					else if(strUserType.equals("DMC")||strUserType.equals("CAL")){
						out.print("<tr>");
						out.print("<td width=\"22%\" CLASS=\"formLabel\">");
						out.print("Primary Vidal Health Branch:");
						out.print(" <span class=\"mandatorySymbol\">*</span>");
						out.print("</td>");
						out.print("<td width=\"35%\" class=\"formLabel\">");
						if(alOfficeInfo != null && alOfficeInfo.size() > 0)
						{
							out.print("<select name=\"additionalInfoVO.officeSeqID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
							for(int i=0; i < alOfficeInfo.size(); i++)
							{
								cacheObject = (CacheObject)alOfficeInfo.get(i);
								out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strOfficeSeqID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
							}//end of for(int i=0; i < alOfficeInfo.size(); i++)
							out.print("</select>");
						}//end of if(alOfficeInfo != null && alOfficeInfo.size() > 0)
						else if(alOfficeInfo != null && alOfficeInfo.size() == 0)
						{
							out.print("<select name=\"additionalInfoVO.officeSeqID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
							out.print("</select>");
						}//end of else if(alOfficeInfo != null && alOfficeInfo.size() == 0)
						out.print("</td>");
						out.print("<td width=\"18%\"/>");
						out.print("<td/>");
						out.print("</tr>");
					}//end of else if(strUserType.equals("DMC")||strUserType.equals("CAL"))
					else if(strUserType.equals("HOS"))
					{
						log.debug("Insid the Hospital Block :");
						loadHospitalInfo(out,additionalInfoForm,strViewmode);
					}//end of else if(strUserType.equals("HOS"))
					out.print("</table>");
					out.print("</fieldset>");
				}//end of if(!strUserType.equals("INS")||!strUserType.equals("COR"))
			}//end of if(strActiveSubLink.equals(strUserManagement))
			
			if(strActiveSubLink.equals(strHospital))
			{
				out.print("<fieldset><legend>Additional Information</legend>");
				out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
				loadHospitalInfo(out,additionalInfoForm,strViewmode);
				out.print("</table>");
				out.print("</fieldset>");
			}//end of 	if(strActiveSubLink.equals(strHospital))
			if(strActiveSubLink.equals(strPartner))
			{
				out.print("<fieldset><legend>Additional Information</legend>");
				out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
				loadHospitalInfo(out,additionalInfoForm,strViewmode);
				out.print("</table>");
				out.print("</fieldset>");
			}//end of 	if(strActiveSubLink.equals(strPartner))
		}//end of try block
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()
	
	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
	
	/**
	 * Generates the HTML information which is common in Hospital and User Management flow.
	 * @param additionalInfoForm DynaActionForm object to which contain the information.
	 * @param out JspWriter object which contains pageContext reference.
	 */
	
	private void loadHospitalInfo(JspWriter out,DynaActionForm additionalInfoForm,String strViewmode)throws JspException,TTKException,IOException
	{
		log.debug("Inside the loadHospitalInfo :");
		ArrayList alContactTypeID=null;
		CacheObject cacheObject = null;
		alContactTypeID = Cache.getCacheObject("contactTypeCode");
		ArrayList alSpecTypeID=null;
		alSpecTypeID = Cache.getCacheObject("drSpecialityCode");
		String strContactTypeID="",strSpecTypeID="",strRegistrationNbr="",strQualification="",strResidentYN="";
		strContactTypeID=(String)additionalInfoForm.get("contactTypeID");
		strSpecTypeID=(String)additionalInfoForm.get("specTypeID");
		strRegistrationNbr=(String)additionalInfoForm.get("registrationNbr");
		strQualification=(String)additionalInfoForm.get("qualification");
		strResidentYN=(String)additionalInfoForm.get("residentYN");
		
		out.print("<tr>");
		out.print("<td width=\"22%\" class=\"formLabel\">");
		out.print("Contact Type:");
		out.print("<span class=\"mandatorySymbol\">*</span>");
		out.print("</td>");
		out.print("<td width=\"35%\">");
		if(alContactTypeID != null && alContactTypeID.size() > 0)
		{
			out.print("<select name=\"additionalInfoVO.contactTypeID\" class=\"selectBox selectBoxMedium\" onChange=\"showhideContactInfo(this)\""+strViewmode+">");
			for(int i=0; i < alContactTypeID.size(); i++)
			{
				cacheObject = (CacheObject)alContactTypeID.get(i);
				out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strContactTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
			}//end of for(int i=0; i < alContactTypeID.size(); i++)
		}//end of if(alContactTypeID != null && alContactTypeID.size() > 0)
		else if(alContactTypeID != null && alContactTypeID.size() == 0)
		{
			out.print("<select name=\"additionalInfoVO.contactTypeID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
			out.print("</select>");
		}//end of else if(alContactTypeID != null && alContactTypeID.size() == 0)
		
		out.print("</td>");
		out.print("<td width=\"18%\">");
		out.print("&nbsp;");
		out.print("</td>");
		out.print("<td width=\"29%\">");
		out.print("&nbsp;");
		out.print("</td>");
		out.print("</tr>");
		out.print("</table>");
		
		out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" id=\"6\"  style=\"display:"+getStyle(strContactTypeID)+"\" cellspacing=\"0\" class=\"formContainerWithoutPad\">");
		out.print("<tr>");
		out.print("<td width=\"22%\">");
		out.print("Registration No.:");
		out.print("</td>");
		out.print("<td width=\"35%\">");
		out.print("<input type=\"text\" name=\"additionalInfoVO.registrationNbr\"  value='"+TTKCommon.getHtmlString(strRegistrationNbr)+"' maxlength=\"250\" class=\"textBox textBoxMedium\""+strViewmode+">");
		out.print("</td>");
		out.print("<td width=\"18%\" class=\"formLabel\">");
		out.print("Resident Doctor:");
		out.print("</td>");
		out.print("<td width=\"29%\">");
		out.print("<input type=\"checkbox\" name=\"additionalInfoVO.residentYN\" value=\"Y\" "+TTKCommon.isChecked(strResidentYN) +strViewmode+">" );
		out.print("<input type=\"hidden\" name=\"additionalInfoVO.residentYN\"  value=\"\" >");
		out.print("</td>");
		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<td class=\"formLabel\">");
		out.print("Qualification:");
		out.print("</td>");
		out.print("<td>");
		out.print("<input type=\"text\" name=\"additionalInfoVO.qualification\"  value='"+TTKCommon.getHtmlString(strQualification)+"' maxlength=\"250\" class=\"textBox textBoxMedium\""+strViewmode+">");
		out.print("</td>");
		out.print("<td>");
		out.print("Specialisation:");
		out.print("</td>");
		
		out.print("<td>");
		if(alSpecTypeID != null && alSpecTypeID.size() > 0)
		{
			out.print("<select name=\"additionalInfoVO.specTypeID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
			out.print("<option value=\"\">Select from list</option>");
			for(int i=0; i < alSpecTypeID.size(); i++)
			{
				cacheObject = (CacheObject)alSpecTypeID.get(i);
				out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strSpecTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				
			}//end of for(int i=0; i < alSpecTypeID.size(); i++)
			out.print("</select>");
		}//end of if(alSpecTypeID != null && alSpecTypeID.size() > 0)
		else if(alSpecTypeID != null && alSpecTypeID.size() == 0)
		{
			out.print("<select name=\"specTypeID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
			out.print("</select>");
		}//end of else if(alSpecTypeID != null && alSpecTypeID.size() == 0)
		out.print("</td>");
		out.print("</tr>");
	}//end of loadHospitalInfo(JspWriter out,DynaActionForm additionalInfoForm )
	
	/**
	 * this method is used to enable/disable the table
	 * @param strContactTypeID
	 * @return String
	 */
	private String getStyle(String strContactTypeID)
	{
		if(strContactTypeID.equals("6"))
		{
			return  "";
		}//end of if(strContactTypeID.equals("6"))
		else
		{
			return "none";
		}//end of else
	}//end of getStyle(String strContactTypeID)
}//end of AdditionalInformation
