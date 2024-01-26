/**
 * @ (#) PersonalInformation.java Jan 10, 2006
 * Project       : TTK HealthCare Services
 * File          : PersonalInformation.java
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
import com.ttk.dto.usermanagement.PersonalInfoVO;

/**
 *  This class builds the personal information of individual
 */

public class PersonalInformation extends TagSupport
{
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	 private static Logger log = Logger.getLogger( PersonalInformation.class );

	 public int doStartTag() throws JspException {
        try
        {

        	log.debug("Inside PersonalInformation TAG :");
        	JspWriter out = pageContext.getOut();
        	//get the reference of the frmUserContact
        	HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           	DynaActionForm generalForm=(DynaActionForm)((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("frmUserContact");
        	String strPrefix="",strName="",strDesignationTypeID="",strDesignation="",strActiveYN="",strPrimaryEmailID="",strSecondaryEmailID="",strPhoneNbr1="",strPhoneNbr2="",strResidencePhoneNbr="",strMobileNbr="",strFaxNbr="",strPreauthAppYN="",strPreauthRejYN="",strClaimsAppYN="",strClaimsRejYN="",strProfessionalId="",strProfessionalAuthority="",strStartDate="",strEndDate="";//denial process
        	String strStdCode="",strIsdCode="",strGender="",strAge="";
			//Changes Added for Password Policy CR KOC 1235
        	String straccn_locked_YN=""; 
			//End changes for Password Policy CR KOC 1235
       		ArrayList alDesignationTypeID = null;
        	ArrayList alPrefix=null;
        	CacheObject cacheObject = null;
        	alPrefix = Cache.getCacheObject("prefix");
        	

        	
        	ArrayList algender=null;
        	algender = Cache.getCacheObject("gender");
        	String strSubLink=TTKCommon.getActiveSubLink(request);
        	
        	DynaActionForm personalInfoForm=(DynaActionForm)generalForm.get("personalInfoVO");//get the personalInfoVO reference
        	DynaActionForm userAccessInfoForm=(DynaActionForm)generalForm.get("userAccessVO");//get the userAccessVO reference
        	DynaActionForm frmUserContact=(DynaActionForm)request.getSession().getAttribute("frmUserContact");
        	String strUserType=(String)userAccessInfoForm.getString("userType");
        	String strDesignationTypeId="designation"+strUserType;
        	//load teh designation depending on different users
        	alDesignationTypeID = Cache.getCacheObject(strDesignationTypeId);
        	String strViewmode=" Disabled ";
        	if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
        	{
        		strViewmode="";
        	}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))

        	if(personalInfoForm!=null)
        	{
	        	strPrefix=(String)personalInfoForm.getString("prefix");
	        	strName=(String)personalInfoForm.getString("name");
	        	strDesignationTypeID=(String)personalInfoForm.getString("designationTypeID");
	        	strDesignation =(String)personalInfoForm.getString("designation");
	        	strActiveYN=(String)personalInfoForm.getString("activeYN");
	        	strPrimaryEmailID =(String)personalInfoForm.getString("primaryEmailID");
	        	strSecondaryEmailID=(String)personalInfoForm.getString("secondaryEmailID");
	        	strPhoneNbr1=(String)personalInfoForm.getString("phoneNbr1");
	        	strPhoneNbr2=(String)personalInfoForm.getString("phoneNbr2");
	        	strResidencePhoneNbr=(String)personalInfoForm.getString("residencePhoneNbr");
	        	strMobileNbr=(String)personalInfoForm.getString("mobileNbr");
	        	strFaxNbr=(String)personalInfoForm.getString("faxNbr");
				//Changes Added for Password Policy CR KOC 1235
	        	straccn_locked_YN=(String)personalInfoForm.getString("accn_locked_YN");
	        	//End changes for Password Policy CR KOC 1235
	        	strPreauthAppYN=(String)personalInfoForm.getString("preauthAppYN");
	        	strPreauthRejYN=(String)personalInfoForm.getString("preauthRejYN");
	        	strClaimsAppYN=(String)personalInfoForm.getString("claimsAppYN");
	        	strClaimsRejYN=(String)personalInfoForm.getString("claimsRejYN");
	        	strProfessionalId=(String)personalInfoForm.getString("professionalId");
	        	strProfessionalAuthority=(String)personalInfoForm.getString("professionalAuthority");
	        	strStartDate=(String)personalInfoForm.get("startDate");
				strEndDate=(String)personalInfoForm.get("endDate");
				strStdCode	=	(String)personalInfoForm.get("stdCode");
				strIsdCode	=	(String)personalInfoForm.get("isdCode");
				strGender	=	(String)personalInfoForm.get("gender");
				strAge		=	(String)personalInfoForm.get("age");
        	}//end of if(personalInfoForm!=null)

        	out.print("<fieldset><legend>Personal Information</legend>");
		        out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
		        	out.print("<tr>");
		        	/*out.print("<td width=\"22%\" CLASS=\"formLabel\">");
        			out.print("Professional ID: ");
        			out.print("</td>");
        		out.print("<td width=\"35%\">");
        			out.print("<input type=\"text\" name=\"personalInfoVO.professionalId\"  value='"+TTKCommon.getHtmlString(strProfessionalId)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
        		out.print("</td>");*/
		        		out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		        			out.print("Name: ");
		        			out.print("<span class=\"mandatorySymbol\">*</span>");
		        		out.print("</td>");
		        		out.print("<td width=\"35%\">");
		        		out.print("<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\">");
			        	out.print("<tr>");
			        	out.print("<td>");
			        		if(alPrefix != null && alPrefix.size() > 0)
			                {
			        			out.print("<select name=\"personalInfoVO.prefix\" CLASS=\"selectBox\""+strViewmode+">");
				        			 for(int i=0; i < alPrefix.size(); i++)
				                     {
				        			 	cacheObject = (CacheObject)alPrefix.get(i);
				        			 	out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strPrefix, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				                     }//end of for(int i=0; i < alPrefix.size(); i++)
			        			out.print("</select>");
			                }//end of if(alPrefix != null && alPrefix.size() > 0)
			        		else if(alPrefix != null && alPrefix.size() == 0)
		       	            {
			        			out.print("<select name=\"personalInfoVO.prefix\" CLASS=\"selectBox\">");
		       	                out.print("</select>");
		       	            }//end of else if(alPrefix != null && alPrefix.size() == 0)
			        		out.print("&nbsp;");
		        		out.print("</td>");
		        		out.print("<td>");
			        		out.print("<input type=\"text\" name=\"personalInfoVO.name\"  onkeypress=\"ConvertToUpperCase(event.srcElement)\" value='"+TTKCommon.getHtmlString(strName)+"' maxlength=\"60\" class=\"textBox textBoxMedLarge textBoxTransform\" "+strViewmode+">");
		        		out.print("</td>");
		        		out.print("</tr>");
			    	    out.print("</table>");
			    	    out.print("</td>");
		        		out.print("<td width=\"18%\" CLASS=\"formLabel\">");
       	 				out.print("&nbsp;");
       	 				out.print("</td>");
       	 				out.print("<td>");
       	 				out.print("&nbsp;");
       	 			out.print("</td>");
		        	out.print("</tr>");
		        	
		        	/*
		        	 * Adding Gender and Age for intx
		        	 */
					out.print("<td width=\"22%\" CLASS=\"formLabel\">");
					out.print("Gender:");
					out.print("</td>");
					out.print("<td width=\"35%\">");
					if(algender != null && algender.size() > 0)
	                {
	        			out.print("<select name=\"personalInfoVO.gender\" CLASS=\"selectBox\""+strViewmode+">");
	        			out.print("<option value=''>--Select from the list-- </option>");
		        			 for(int i=0; i < algender.size(); i++)
		                     {
		        			 	cacheObject = (CacheObject)algender.get(i);
		        			 	out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strGender, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
		                     }//end of for(int i=0; i < algender.size(); i++)
	        			out.print("</select>");
	                }//end of if(algender != null && algender.size() > 0)
	        		else if(algender != null && algender.size() == 0)
       	            {
	        			out.print("<select name=\"personalInfoVO.gender\" CLASS=\"selectBox\">");
       	                out.print("</select>");
       	            }//end of else if(algender != null && algender.size() == 0)
	        		out.print("&nbsp;");					out.print("</td>");
					out.print("<td  CLASS=\"formLabel\">");
					out.print("Age:");
					out.print("</td>");
					out.print("<td>");
					out.print("<input type=\"text\" name=\"personalInfoVO.age\"  value='"+TTKCommon.getHtmlString(strAge)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
					out.print("</td>");
					out.print("</tr>");
					out.print("<tr>");
		        	
		        	/*out.print("<tr>");
		        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		        			out.print("Authority Standard: ");
		        			//out.print("<span class=\"mandatorySymbol\">*</span>");
		        		out.print("</td>");
		        		out.print("<td width=\"35%\">");
		        			out.print("<input type=\"text\" name=\"personalInfoVO.professionalAuthority\"  value='"+TTKCommon.getHtmlString(strProfessionalAuthority)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
		        		out.print("</td>");
		        	
		        	out.print("</tr>");
		        	
		        	out.print("</tr>");


		        	
		        	out.print("<tr>");
                    out.print("<td class=\"formLabel\">");
                        out.print("Start Date:");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type=\"text\" name=\"personalInfoVO.startDate\"  value='"+TTKCommon.getHtmlString(strStartDate)+"' maxlength=\"30\" class=\"textBox textDate\""+strViewmode+">");
                    
                         out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmUserContact.elements[\\'personalInfoVO.startDate\\']',document.frmUserContact.elements['personalInfoVO.startDate'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                    
                    out.print("</td>");
                    out.print("<td class=\"formLabel\">");
                     out.print("End Date:");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type=\"text\" name=\"personalInfoVO.endDate\"  value='"+TTKCommon.getHtmlString(strEndDate)+"' maxlength=\"30\" class=\"textBox textDate\""+strViewmode+">");
                   
                         out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmUserContact.elements[\\'personalInfoVO.endDate\\']',document.frmUserContact.elements['personalInfoVO.endDate'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                     
                    out.print("</td>");
                out.print("</tr>");*/
					
		        	
					if(strSubLink.equals("Partner"))
					{ 
						
						out.print("<tr>");
						        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
						        			out.print("Designation: ");
						        			out.print("<span class=\"mandatorySymbol\">*</span>");
						        		out.print("</td>");
					
					out.print("<td width=\"35%\">");
						        			out.print("<input type=\"text\" name=\"personalInfoVO.designation\"  value='"+TTKCommon.getHtmlString(strDesignation)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
						        			out.print("</td>");	        		
						        		out.print("<td>");
				        				out.print("Active:");
			        				out.print("</td>");
			        				out.print("<td>");
				        				out.print("<input type=\"checkbox\" id=\"activeYNFlag\" name=\"personalInfoVO.activeYN\" value=\"Y\" onClick=\"javascript:activeCheck()\""+TTKCommon.isChecked(strActiveYN) +strViewmode+">" );
				        				out.print("<input type=\"hidden\" size=\"500\" name=\"personalInfoVO.loginType\"  value=\"PTR\" >");
				        				out.print("</td>");
					        	out.print("</tr>");
					} else{	
		        	out.print("<tr>");
		        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		        			out.print("Designation: ");
		        			out.print("<span class=\"mandatorySymbol\">*</span>");
		        		out.print("</td>");

		        		out.print("<td width=\"35%\">");
			        		if(alDesignationTypeID != null && alDesignationTypeID.size() > 0)
			                {
			        			out.print("<select name=\"personalInfoVO.designationTypeID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
			        				 /*out.print("<option value=\"\">Select from list</option>");*/
				        			 for(int i=0; i < alDesignationTypeID.size(); i++)
				                     {
				        			 	cacheObject = (CacheObject)alDesignationTypeID.get(i);
				        			 	out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strDesignationTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				                     }//end of for(int i=0; i < alDesignationTypeID.size(); i++)
			        			out.print("</select>");
			                }//end of if(alDesignationTypeID != null && alDesignationTypeID.size() > 0)
			        		else if(alDesignationTypeID == null)
		       	            {
			        			out.print("<select name=\"personalInfoVO.designationTypeID\" class=\"selectBox selectBoxMedium\""+strViewmode+">");
			        				/*out.print("<option value=\"\">Select from list</option>");*/
		       	                out.print("</select>");
		       	            }//end of else if(alDesignationTypeID != null && alDesignationTypeID.size() == 0)
		        		out.print("</td>");

	        			out.print("<td>");
	        				out.print("Active:");
        				out.print("</td>");
        				out.print("<td>");
	        				out.print("<input type=\"checkbox\" id=\"activeYNFlag\" name=\"personalInfoVO.activeYN\" value=\"Y\" onClick=\"javascript:activeCheck()\""+TTKCommon.isChecked(strActiveYN) +strViewmode+">" );
        				out.print("</td>");

		        	out.print("</tr>");
					}
		        	out.print("<tr>");
		        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		        			out.print("Primary Email ID: ");
		        			out.print("<span class=\"mandatorySymbol\">*</span>");
		        		out.print("</td>");
		        		out.print("<td width=\"35%\">");
		        			out.print("<input type=\"text\" name=\"personalInfoVO.primaryEmailID\"  value='"+TTKCommon.getHtmlString(strPrimaryEmailID)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
		        		out.print("</td>");
		        		out.print("<td  CLASS=\"formLabel\">");
		        			out.print("Alternate Email ID:");
		        		out.print("</td>");
		        		out.print("<td>");
		        			out.print("<input type=\"text\" size=\"500\" name=\"personalInfoVO.secondaryEmailID\"  value='"+TTKCommon.getHtmlString(strSecondaryEmailID)+"' class=\"textBox textBoxMedium\""+strViewmode+">");
		        		out.print("</td>");
		        	out.print("</tr>");
		        	
		        	
		        	//intx adding Std code and ISD code
		        	/*out.print("<tr>");
		        	out.print("<td  CLASS=\"formLabel\">");
        			out.print("ISD Code:");
        		out.print("</td>");
        		out.print("<td>");
        			out.print("<input type=\"text\" name=\"personalInfoVO.isdCode\"  value='"+TTKCommon.getHtmlString(strIsdCode)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
        		out.print("</td>");
		        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		        			out.print("STD Code: ");
		        			//out.print("<span class=\"mandatorySymbol\">*</span>");
		        		out.print("</td>");
		        		out.print("<td width=\"35%\">");
		        			out.print("<input type=\"text\" name=\"personalInfoVO.stdCode\"  value='"+TTKCommon.getHtmlString(strStdCode)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
		        		out.print("</td>");
		        		
		        	out.print("</tr>");*/
//----------
		        		        		
		        	out.print("<tr>");
		        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		        			out.print("Office Phone 1:");
		        			if(!"Group Registration".equals(TTKCommon.getActiveSubLink(request)))
		        			{
		        			out.print("<span class=\"mandatorySymbol\">*</span>");
		        			}
		        		out.print("</td>");
		        		out.print("<td width=\"35%\">");
        		if(!"User Management".equals(TTKCommon.getActiveSubLink(request)))
	        	{		
		        		if(strIsdCode==null){
		        			out.print("<input type=\"text\" name=\"personalInfoVO.isdCode\" id=\"isdCode\" value='ISD' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('ISD')\" onclick=\"changeMe(this)\" disabled>  ");
		        			out.print("&nbsp;");
		        		}else{
		        			out.print("<input type=\"text\" name=\"personalInfoVO.isdCode\" id=\"isdCode\" value='"+TTKCommon.getHtmlString(strIsdCode)+"' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('ISD')\" onclick=\"changeMe(this)\" disabled>");
		        			out.print("&nbsp;");
		        		}
		        		
		        		if(strStdCode==null){
		        			out.print("<input type=\"text\" name=\"personalInfoVO.stdCode\" id=\"stdCode\" value='STD' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('STD')\" onclick=\"changeMe(this)\" disabled>");
		        			out.print("&nbsp;");
		        		}else{
		        			out.print("<input type=\"text\" name=\"personalInfoVO.stdCode\" id=\"stdCode\" value='"+TTKCommon.getHtmlString(strStdCode)+"' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('STD')\" onclick=\"changeMe(this)\" disabled>");
		        			out.print("&nbsp;");
		        		}
		        }
		        		if(strPhoneNbr1==null || strPhoneNbr1==""){
		        			out.print("<input type=\"text\" name=\"personalInfoVO.phoneNbr1\" id=\"phoneNbr1\" value='Phone No1' class=\"disabledfieldType\" maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+" onblur=\"checkMe('Phone No1')\" onclick=\"changeMe(this)\">");
		        			out.print("&nbsp;");
		        		}else{
		        			out.print("<input type=\"text\" name=\"personalInfoVO.phoneNbr1\" id=\"phoneNbr1\" value='"+TTKCommon.getHtmlString(strPhoneNbr1)+"' class=\"disabledfieldType\" maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+" onblur=\"checkMe('Phone No1')\" onclick=\"changeMe(this)\">");
		        			out.print("&nbsp;");
		        		}
		        		
		        			
		        			
		        		out.print("</td>");
		        		out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		        			out.print("Office Phone 2:");
		        			out.print("</td>");
			        		out.print("<td width=\"35%\">");
        		if(!"User Management".equals(TTKCommon.getActiveSubLink(request)))
	        	{
		    		    if(strIsdCode==null){
		        			out.print("<input type=\"text\" name=\"personalInfoVO.isdCode\" id=\"isdCode\" value='ISD' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('ISD')\" onclick=\"changeMe(this)\" disabled>  ");
		        			out.print("&nbsp;");
		        		}else{
		        			out.print("<input type=\"text\" name=\"personalInfoVO.isdCode\" id=\"isdCode\" value='"+TTKCommon.getHtmlString(strIsdCode)+"' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('ISD')\" onclick=\"changeMe(this)\" disabled>");
		        			out.print("&nbsp;");
		        		}
		        		
		        		if(strStdCode==null){
		        			out.print("<input type=\"text\" name=\"personalInfoVO.stdCode\" id=\"stdCode\" value='STD' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('STD')\" onclick=\"changeMe(this)\" disabled>");
		        			out.print("&nbsp;");
		        		}else{
		        			out.print("<input type=\"text\" name=\"personalInfoVO.stdCode\" id=\"stdCode\" value='"+TTKCommon.getHtmlString(strStdCode)+"' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('STD')\" onclick=\"changeMe(this)\" disabled>");
		        			out.print("&nbsp;");
		        		}
	        	}
		        		if(strPhoneNbr2==null || strPhoneNbr2==""){
		        			out.print("<input type=\"text\" name=\"personalInfoVO.phoneNbr2\" id=\"phoneNbr2\" value='' class=\"disabledfieldType\" maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
		        			out.print("&nbsp;");
		        		}else{
		        			out.print("<input type=\"text\" name=\"personalInfoVO.phoneNbr2\" id=\"phoneNbr2\" value='"+TTKCommon.getHtmlString(strPhoneNbr2)+"' class=\"disabledfieldType\" maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
		        			out.print("&nbsp;");
		        		}

		    		    out.print("</td>");
		        		/*out.print("<td>");
		    		    	out.print("<input type=\"text\" name=\"personalInfoVO.phoneNbr2\"  value='"+TTKCommon.getHtmlString(strPhoneNbr2)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
		    		    out.print("</td>");*/
		        	out.print("</tr>");
		        	out.print("<tr>");
/*						out.print("<td width=\"22%\" CLASS=\"formLabel\">");
						out.print("Home Phone:");
						out.print("</td>");
			    		out.print("<td width=\"35%\">");
			    			out.print("<input type=\"text\" name=\"personalInfoVO.residencePhoneNbr\"  value='"+TTKCommon.getHtmlString(strResidencePhoneNbr)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
			    		out.print("</td>");*/
			    		out.print("<td  CLASS=\"formLabel\">");
			    			out.print("Mobile:");
					    out.print("</td>");
					    out.print("<td>");
					    	out.print("<input type=\"text\" name=\"personalInfoVO.isdCode\" id=\"isdCode\" value='"+TTKCommon.getHtmlString(strIsdCode)+"' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('ISD')\" onclick=\"changeMe(this)\" disabled>");
					    	out.print("&nbsp;");
					    	out.print("<input type=\"text\" name=\"personalInfoVO.mobileNbr\"  value='"+TTKCommon.getHtmlString(strMobileNbr)+"' maxlength=\"15\" class=\"textBox textBoxMedium\""+strViewmode+">");
					    out.print("</td>");
		    		out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		    	    		out.print("Fax:");
		    	    	out.print("</td>");
		    	    	out.print("<td width=\"35%\">");
		    	    	if(!"User Management".equals(TTKCommon.getActiveSubLink(request)))
			        	{
		    	    		
				    		    if(strIsdCode==null){
				        			out.print("<input type=\"text\" name=\"personalInfoVO.isdCode\" id=\"isdCode\" value='ISD' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('ISD')\" onclick=\"changeMe(this)\" disabled>  ");
				        			out.print("&nbsp;");
				        		}else{
				        			out.print("<input type=\"text\" name=\"personalInfoVO.isdCode\" id=\"isdCode\" value='"+TTKCommon.getHtmlString(strIsdCode)+"' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('ISD')\" onclick=\"changeMe(this)\" disabled>");
				        			out.print("&nbsp;");
				        		}
				        		
				        		if(strStdCode==null){
				        			out.print("<input type=\"text\" name=\"personalInfoVO.stdCode\" id=\"stdCode\" value='STD' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('STD')\" onclick=\"changeMe(this)\" disabled>");
				        			out.print("&nbsp;");
				        		}else{
				        			out.print("<input type=\"text\" name=\"personalInfoVO.stdCode\" id=\"stdCode\" value='"+TTKCommon.getHtmlString(strStdCode)+"' class=\"disabledfieldType\" maxlength=\"5\" size=\"4\" "+strViewmode+" onblur=\"checkMe('STD')\" onclick=\"changeMe(this)\" disabled>");
				        			out.print("&nbsp;");
				        		}
			        	}
		    	    		out.print("<input type=\"text\" name=\"personalInfoVO.faxNbr\"  value='"+TTKCommon.getHtmlString(strFaxNbr)+"' maxlength=\"15\" class=\"textBox textBoxMedium\""+strViewmode+">");
		    	    	out.print("</td>");
		    	    	out.print("<td>");
		    	    		out.print("&nbsp;");
		    	    	out.print("</td>");
		    	    	out.print("<td>");
		    	    		out.print("&nbsp;");
		    	    	out.print("</td>");
		    	    out.print("</tr>");
		    	  //denial process
		    	    	if(strUserType.equals("INS"))
		    	    	{
		    	    		out.print("<tr>");
				        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
					    			out.print("Preauth Approve:");
					    		out.print("</td>");
					    		out.print("<td width=\"35%\">");
					    			out.print("<input type=\"checkbox\" name=\"personalInfoVO.preauthAppYN\"  value=\"Y\" "+TTKCommon.isChecked(strPreauthAppYN)+strViewmode+">");					    		
					    		out.print("</td>");
					    		out.print("<td  CLASS=\"formLabel\">");
					    			out.print("Preauth Rejection:");
							    out.print("</td>");
							    out.print("<td>");
				    			out.print("<input type=\"checkbox\" name=\"personalInfoVO.preauthRejYN\"  value=\"Y\" "+TTKCommon.isChecked(strPreauthRejYN)+strViewmode+">");					    		
							    out.print("</td>");
				    	    out.print("</tr>");
				    	    out.print("<tr>");
				        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
					    			out.print("Claims Approve:");
					    		out.print("</td>");
					    		out.print("<td width=\"35%\">");
				    			out.print("<input type=\"checkbox\" name=\"personalInfoVO.claimsAppYN\"  value=\"Y\" "+TTKCommon.isChecked(strClaimsAppYN)+strViewmode+">");					    		
					    		out.print("</td>");
					    		out.print("<td  CLASS=\"formLabel\">");
					    			out.print("Claims Rejection:");
							    out.print("</td>");
							    out.print("<td>");
				    			out.print("<input type=\"checkbox\" name=\"personalInfoVO.claimsRejYN\"  value=\"Y\" "+TTKCommon.isChecked(strClaimsRejYN)+strViewmode+">");					    		
							    out.print("</td>");
				    	    out.print("</tr>");
		    	    	}
		    	    	//denial process
						//change for password policy
		    	    	if(strUserType.equalsIgnoreCase("TTK") || strUserType.equalsIgnoreCase("CAL") || strUserType.equalsIgnoreCase("DMC"))
		            	{
		    	    	out.print("<tr>");
		    	    		out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		    	    		out.print("Lock:");
		    	    		out.print("</td>");
		    	    		out.print("<td>");
		    	    			if(straccn_locked_YN.equalsIgnoreCase("Y"))
		    	    			{
		    	    				out.print("<input type=\"checkbox\" id=\"lockYNFlag\" name=\"personalInfoVO.accn_locked_YN\" value=\"Y\" onClick=\"SetLOCKState(this)\""+TTKCommon.isChecked(straccn_locked_YN) +strViewmode+">" );
		    	    				//out.print("<input type=\"checkbox\" id=\"lockYNFlag\" name=\"personalInfoVO.accn_locked_YN\" value=\"Y\" onClick=\"SetLOCKState(this)\""+TTKCommon.isChecked(straccn_locked_YN) +strViewmode+" class=\"checkbox checkBoxDisabled\"disabled>");
		    	    			}
		    	    			else
		    	    			{
		    	    				//out.print("<input type=\"checkbox\" id=\"lockYNFlag\" name=\"personalInfoVO.accn_locked_YN\" value=\"Y\" onClick=\"SetLOCKState(this)\""+TTKCommon.isChecked(straccn_locked_YN) +strViewmode+">" );
		    	    				out.print("<input type=\"checkbox\" id=\"lockYNFlag\" name=\"personalInfoVO.accn_locked_YN\" value=\"Y\" onClick=\"SetLOCKState(this)\""+TTKCommon.isChecked(straccn_locked_YN) +strViewmode+" class=\"checkbox checkBoxDisabled\"disabled>");
    	    				   	}
		    	    		out.print("</td>");
        				out.print("</tr>");
		            	}
		    	    	//change for password policy	

		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.activeYN\"  value=\"\">");
		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.preauthAppYN\"  value=\"\">");
		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.preauthRejYN\"  value=\"\">");
		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.claimsAppYN\"  value=\"\">");
		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.claimsRejYN\"  value=\"\">" );
		    	    if("Group Registration".equals(TTKCommon.getActiveSubLink(request)))
		    	    {
		    	    	
		    	    out.print("<input type=\"hidden\" name=\"flag\" value=\"Y\">");
		    	    }
		    	    else
		    	    {
		    	    	
		    	    	out.print("<input type=\"hidden\" name=\"flag\" value=\"N\">");
		    	    }
		    	    
	    	    out.print("</table>");
        	out.print("</fieldset>");
        	
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
}//end of PersonalInformation