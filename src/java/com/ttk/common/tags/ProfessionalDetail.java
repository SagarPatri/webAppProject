
/**
 * @ (#) ProfessionalDetail.java Jan 10, 2006
 * Project       : TTK HealthCare Services
 * File          : ProfessionalDetail.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 06 Jan 2015
 *
 * @author       :  Kishor kumar S H
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

public class ProfessionalDetail extends TagSupport
{
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	 private static Logger log = Logger.getLogger( ProfessionalDetail.class );

	 public int doStartTag() throws JspException {
        try
        {

        	log.info("Inside ProfessionalDetail TAG :");
        	JspWriter out = pageContext.getOut();
        	//get the reference of the frmProfessionalContact
        	HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           	DynaActionForm generalForm=(DynaActionForm)((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("frmProfessionalContact");
        	String strPrefix="",strName="",strDesignationTypeID="",strActiveYN="",strPrimaryEmailID="",strSecondaryEmailID="",strPhoneNbr1="",strPhoneNbr2="",strResidencePhoneNbr="",strMobileNbr="",strFaxNbr="",strPreauthAppYN="",strPreauthRejYN="",strClaimsAppYN="",strClaimsRejYN="",strProfessionalId="",strProfessionalAuthority="",strStartDate="",strEndDate="";//denial process
			//Changes Added for Password Policy CR KOC 1235
        	String straccn_locked_YN="",strDateofJoining=""; 
			//End changes for Password Policy CR KOC 1235
       		ArrayList alDesignationTypeID = null;
        	ArrayList alPrefix=null;
        	CacheObject cacheObject = null;
        	alPrefix = Cache.getCacheObject("prefix");
        	DynaActionForm personalInfoForm=(DynaActionForm)generalForm.get("personalInfoVO");//get the personalInfoVO reference
        	DynaActionForm userAccessInfoForm=(DynaActionForm)generalForm.get("userAccessVO");//get the userAccessVO reference
        	DynaActionForm frmProfessionalContact=(DynaActionForm)request.getSession().getAttribute("frmProfessionalContact");
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
        	}//end of if(personalInfoForm!=null)

        	out.print("<fieldset><legend>Personal Information</legend>");
		        out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
		        	out.print("<tr>");
		        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
        			out.print("Professional ID: ");
        			//out.print("<span class=\"mandatorySymbol\">*</span>");
        		out.print("</td>");
        		out.print("<td width=\"35%\">");
        			out.print("<input type=\"text\" name=\"personalInfoVO.professionalId\"  value='"+TTKCommon.getHtmlString(strProfessionalId)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
        		out.print("</td>");
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
			        		out.print("<input type=\"text\" name=\"personalInfoVO.name\"  onkeypress=\"ConvertToUpperCase(event.srcElement)\" value='"+TTKCommon.getHtmlString(strName)+"' maxlength=\"60\" class=\"textBox textBoxMedLarge\" "+strViewmode+">");
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
		        	
		        	out.print("<tr>");
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
                    
                         out.print("<A NAME=\"CalendarObjectStartDate\" ID=\"CalendarObjectStartDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectStartDate','frmUserContact.[\\'personalInfoVO.startDate\\']',document.frmUserContact.elements['personalInfoVO.startDate'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"startDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");

                    
                    out.print("</td>");
                    
                    out.print("<td width=\"35%\">");
					out.print("<input type=\"text\" name=\"additionalInfoVO.dateOfJoining\"  value='"+TTKCommon.getHtmlString(strDateofJoining)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
                    out.print("<a name=\"CalendarObjectdojDate\" id=\"CalendarObjectdojDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdojDate','frmUserContact.[\\'additionalInfoVO.dateOfJoining\\']',document.frmUserContact.elements['additionalInfoVO.dateOfJoining'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"dojDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                    out.print("</td>");
                    
                    
                    out.print("<td class=\"formLabel\">");
                    out.print("End Date:");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<input type=\"text\" name=\"personalInfoVO.endDate\"  value='"+TTKCommon.getHtmlString(strEndDate)+"' maxlength=\"30\" class=\"textBox textDate\""+strViewmode+">");
                   
                         out.print("<A NAME=\"CalendarObjectEndDate\" ID=\"CalendarObjectEndDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectEndDate','frmUserContact.[\\'personalInfoVO.endDate\\']',document.frmUserContact.elements['personalInfoVO.endDate'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"endDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                     
                    out.print("</td>");
                    out.print("</tr>");
					
		        	
		        	
		        	out.print("<tr>");
		        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		        			out.print("Primary Email ID: ");
		        			//out.print("<span class=\"mandatorySymbol\">*</span>");
		        		out.print("</td>");
		        		out.print("<td width=\"35%\">");
		        			out.print("<input type=\"text\" name=\"personalInfoVO.primaryEmailID\"  value='"+TTKCommon.getHtmlString(strPrimaryEmailID)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
		        		out.print("</td>");
		        		/*out.print("<td  CLASS=\"formLabel\">");
		        			out.print("Alternate Email ID:");
		        		out.print("</td>");
		        		out.print("<td>");
		        			out.print("<input type=\"text\" name=\"personalInfoVO.secondaryEmailID\"  value='"+TTKCommon.getHtmlString(strSecondaryEmailID)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
		        		out.print("</td>");*/
		        	out.print("</tr>");
		        	out.print("<tr>");
		        	out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		        			out.print("Office Phone 1:");
		        		out.print("</td>");
		        		out.print("<td width=\"35%\">");
		        			out.print("<input type=\"text\" name=\"personalInfoVO.phoneNbr1\"  value='"+TTKCommon.getHtmlString(strPhoneNbr1)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
		        		out.print("</td>");
		        		out.print("<td  CLASS=\"formLabel\">");
		        			out.print("Office Phone 2:");
		    		    out.print("</td>");
		    		    out.print("<td>");
		    		    	out.print("<input type=\"text\" name=\"personalInfoVO.phoneNbr2\"  value='"+TTKCommon.getHtmlString(strPhoneNbr2)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
		    		    out.print("</td>");
		        	out.print("</tr>");
		        	out.print("<tr>");
		        	/*out.print("<td width=\"22%\" CLASS=\"formLabel\">");
			    			out.print("Home Phone:");
			    		out.print("</td>");
			    		out.print("<td width=\"35%\">");
			    			out.print("<input type=\"text\" name=\"personalInfoVO.residencePhoneNbr\"  value='"+TTKCommon.getHtmlString(strResidencePhoneNbr)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
			    		out.print("</td>");*/
			    		out.print("<td  CLASS=\"formLabel\">");
			    			out.print("Mobile:");
					    out.print("</td>");
					    out.print("<td>");
					    	out.print("<input type=\"text\" name=\"personalInfoVO.mobileNbr\"  value='"+TTKCommon.getHtmlString(strMobileNbr)+"' maxlength=\"15\" class=\"textBox textBoxMedium\""+strViewmode+">");
					    out.print("</td>");
		    		out.print("<td width=\"22%\" CLASS=\"formLabel\">");
		    	    		out.print("Fax:");
		    	    	out.print("</td>");
		    	    	out.print("<td width=\"35%\">");
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
		    	    	
		    	    	
		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.activeYN\"  value=\"\">");
		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.preauthAppYN\"  value=\"\">");
		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.preauthRejYN\"  value=\"\">");
		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.claimsAppYN\"  value=\"\">");
		    	    out.print("<input type=\"hidden\" name=\"personalInfoVO.claimsRejYN\"  value=\"\">");
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
}//end of ProfessionalDetail