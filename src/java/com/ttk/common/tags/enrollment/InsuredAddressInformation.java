/**
 * @ (#) InsuredAddressInformation.java Feb 2, 2006
 * Project       : TTK HealthCare Services
 * File          : InsuredAddressInformation.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 2, 2006
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




public class InsuredAddressInformation extends TagSupport
{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( InsuredAddressInformation.class );

	public int doStartTag() throws JspException {
	   try
	   {

		   	log.debug("Inside InsuredAddressInformation TAG :");
	    	JspWriter out = pageContext.getOut();//Writer object to write the file
	    	HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
	    	String strActiveSubLink=TTKCommon.getActiveSubLink(request);
	    	String strViewmode=" Disabled ";
	    	CacheObject cacheObject = null;
	    	//get the reference of the frmPolicyDetails to load the InsuredAddress information
	    	DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
	    	DynaActionForm frmPolicyAddress=(DynaActionForm)frmPolicyDetails.get("frmPolicyAddress");
	    	String strAddress1=(String)frmPolicyAddress.get("address1");
	    	String strAddress2=(String)frmPolicyAddress.get("address2");
	    	String strAddress3=(String)frmPolicyAddress.get("address3");
	    	String strPinCode=(String)frmPolicyAddress.get("pinCode");
	    	String strCityCode="",strStateCode="",strCountryCode="";
	    	String strEmailID=(String)frmPolicyAddress.get("emailID");
	    	String strPhoneNbr1=(String)frmPolicyAddress.get("phoneNbr1");
	    	String strPhoneNbr2=(String)frmPolicyAddress.get("phoneNbr2");
	    	String strHomePhoneNbr=(String)frmPolicyAddress.get("homePhoneNbr");
	    	String strMobileNbr=(String)frmPolicyAddress.get("mobileNbr");
	    	String strFaxNbr=(String)frmPolicyAddress.get("faxNbr");
	    	frmPolicyAddress.set("stateCode","DXB");
	    	frmPolicyAddress.set("countryCode","123");
	    	if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))
	    	{
	    		strCityCode=(String)frmPolicyAddress.get("cityCode");
	    		strStateCode=(String)frmPolicyAddress.get("stateCode");
	    		strCountryCode=(String)frmPolicyAddress.get("countryCode");
	    	}//end of if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))
	    	else
	    	{
	    		strCityCode=(String)frmPolicyAddress.get("cityDesc");
	    		strStateCode=(String)frmPolicyAddress.get("stateName");
	    		strCountryCode=(String)frmPolicyAddress.get("countryName");
	    	}//end of else if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))

	    	//get the required Cache Data to load select boxes
	    	ArrayList alStateCode = Cache.getCacheObject("stateCode");
//	    	ArrayList alCityCode = Cache.getCacheObject("cityCode");
	    	ArrayList alCountryCode = Cache.getCacheObject("countryCode");
	    	
	    	if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
        	{
        		strViewmode="";
        	}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))

	    	out.print("<fieldset><legend>Beneficiary Address Information</legend>");
	    	if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))
	    	{
		    	out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
			    		out.print("<tr>");
				    		out.print("<td width=\"21%\" nowrap CLASS=\"formLabel\">");
								out.print("Address 1: ");
								out.print("<span class=\"mandatorySymbol\">*</span>");
							out.print("</td>");

							out.print("<td width=\"33%\" nowrap>");
		   						out.print("<input type=\"text\" name=\"frmPolicyAddress.address1\"  value='"+TTKCommon.getHtmlString(strAddress1)+"' maxlength=\"250\" class=\"textBox textBoxMedium\""+strViewmode+">");
		   					out.print("</td>");

		   					out.print("<td width=\"23%\" nowrap CLASS=\"formLabel\">");
		   						out.print("Address 2:");
		   					out.print("</td>");

		   					out.print("<td width=\"23%\" nowrap>");
		   						out.print("<input type=\"text\" name=\"frmPolicyAddress.address2\"  value='"+TTKCommon.getHtmlString(strAddress2)+"' maxlength=\"250\" class=\"textBox textBoxMedium\""+strViewmode+">");
		   					out.print("</td>");
					    out.print("</tr>");

					    out.print("<tr>");
					    	out.print("<td CLASS=\"formLabel\">");
					    		out.print("Address 3:");
					    	out.print("</td>");
					    	out.print("<td>");
					    		out.print("<input type=\"text\" name=\"frmPolicyAddress.address3\"  value='"+TTKCommon.getHtmlString(strAddress3)+"' maxlength=\"250\" class=\"textBox textBoxMedium\""+strViewmode+">");
					    	out.print("</td>");

					    	out.print("<td CLASS=\"formLabel\">");
					    		out.print("	Governorate/State: ");
					    		out.print("<span class=\"mandatorySymbol\">*</span>");
					    	out.print("</td>");

					    	out.print("<td>");
						    	if(alStateCode != null && alStateCode.size() > 0)
				                {
				        			out.print("<select name=\"frmPolicyAddress.stateCode\" CLASS=\"selectBox selectBoxMedium\""+strViewmode+">");
				        			out.print("<option value=\"\">Select from list</option>");
					        			 for(int i=0; i < alStateCode.size(); i++)
					                     {
					        			 	cacheObject = (CacheObject)alStateCode.get(i);
					        			 	out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strStateCode, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
					                     }//end of for(int i=0; i < alPrefix.size(); i++)
				        			out.print("</select>");
				                }//end of if(alStateCode != null && alStateCode.size() > 0)
				        		else if(alStateCode != null && alStateCode.size() == 0)
			       	            {
				        			out.print("<select name=\"frmPolicyAddress.stateCode\" CLASS=\"selectBox selectBoxMedium\">");
				        			out.print("<option value=\"\">Select from list</option>");
			       	                out.print("</select>");
			       	            }//end of else if(alStateCode != null && alStateCode.size() == 0)
					    	out.print("</td>");
					    out.print("</tr>");

					    out.print("<tr>");
					    	out.print("<td CLASS=\"formLabel\">");
					    		out.print("Area: ");
					    		out.print("<span class=\"mandatorySymbol\">*</span>");
					    	out.print("</td>");
					    	out.print("<td>");
                                out.print("<input type=\"text\" name=\"frmPolicyAddress.cityCode\"  value='"+TTKCommon.getHtmlString(strCityCode)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
					    	out.print("</td>");
					    	out.print("<td CLASS=\"formLabel\">");
						    	out.print("PO Box: ");
					    		out.print("<span class=\"mandatorySymbol\">*</span>");
					    	out.print("</td>");
					    	out.print("<td>");
					    		out.print("<input type=\"text\" name=\"frmPolicyAddress.pinCode\"  value='"+TTKCommon.getHtmlString(strPinCode)+"' maxlength=\"10\" class=\"textBox textBoxSmall\""+strViewmode+">");
					    	out.print("</td>");

					    out.print("</tr>");

					    out.print("<tr>");
						    out.print("<td CLASS=\"formLabel\">");
							    out.print("Country:");  
					    		out.print("<span class=\"mandatorySymbol\">*</span>");
					    	out.print("</td>");
					    	out.print("<td>");
					    	out.print("<select name=\"frmPolicyAddress.countryCode\" CLASS=\"selectBox selectBoxMedium\""+strViewmode+">");
						    	if(alCountryCode != null && alCountryCode.size() > 0)
				                {
				        			
					        			 for(int i=0; i < alCountryCode.size(); i++)
					                     {
					        			 	cacheObject = (CacheObject)alCountryCode.get(i);
					        			 	out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strCountryCode, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
					                     }//end of for(int i=0; i < alPrefix.size(); i++)
					        			 out.print("</select></td>");
				                }
						    	//end of if(alCountryCode != null && alCountryCode.size() > 0)
				        		else if(alCountryCode != null && alCountryCode.size() == 0)
			       	            {
				        			out.print("<select name=\"frmPolicyAddress.countryCode\" CLASS=\"selectBox selectBoxMedium\">");
			       	                out.print("</select>");
			       	            }//end of else if(alCountryCode != null && alCountryCode.size() == 0)
					    	out.print("</td>");

					    	out.print("<td>");
					    		out.print("Email Id:");
					    	out.print("</td>");
					    	out.print("<td>");
					    		out.print("<input type=\"text\" name=\"frmPolicyAddress.emailID\"  value='"+TTKCommon.getHtmlString(strEmailID)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
					    	out.print("</td>");
					    out.print("</tr>");

					    out.print("<tr>");
					    	out.print("<td CLASS=\"formLabel\">");
					    		out.print("Office Phone 1:");
					    	out.print("</td>");
					    	out.print("<td>");
					    		out.print("<input type=\"text\" name=\"frmPolicyAddress.phoneNbr1\"  value='"+TTKCommon.getHtmlString(strPhoneNbr1)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
					    	out.print("</td>");
					    	out.print("<td CLASS=\"formLabel\">");
					    		out.print("Office Phone 2:");
					    	out.print("</td>");
					    	out.print("<td>");
					    		out.print("<input type=\"text\" name=\"frmPolicyAddress.phoneNbr2\"  value='"+TTKCommon.getHtmlString(strPhoneNbr2)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
					    	out.print("</td>");
					    out.print("</tr>");

					    out.print("<tr>");
						    out.print("<td CLASS=\"formLabel\">");
				    			out.print("Home Phone:");
				    	    out.print("</td>");
				    	    out.print("<td>");
				    			out.print("<input type=\"text\" name=\"frmPolicyAddress.homePhoneNbr\"  value='"+TTKCommon.getHtmlString(strHomePhoneNbr)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
				    	    out.print("</td>");
				    	    out.print("<td CLASS=\"formLabel\">");
			    				out.print("Mobile:");
			    	        out.print("</td>");
			    	        out.print("<td>");
			    				out.print("<input type=\"text\" name=\"frmPolicyAddress.mobileNbr\"  value='"+TTKCommon.getHtmlString(strMobileNbr)+"' maxlength=\"15\" class=\"textBox textBoxMedium\""+strViewmode+">");
			    	        out.print("</td>");
					    out.print("</tr>");
					    out.print("<tr>");
					    	out.print("<td>");
					    		out.print("Fax:");
					    	out.print("</td>");
					    	out.print("<td>");
					    		out.print("<input type=\"text\" name=\"frmPolicyAddress.faxNbr\"  value='"+TTKCommon.getHtmlString(strFaxNbr)+"' maxlength=\"15\" class=\"textBox textBoxMedium\""+strViewmode+">");
					   
					    out.print("</tr>");

		    		out.print("</table>");
	    	}//end of if(strActiveSubLink.equals("Individual Policy")||strActiveSubLink.equals("Ind. Policy as Group"))

	    	else if(strActiveSubLink.equals("Corporate Policy")||strActiveSubLink.equals("Non-Corporate Policy"))
	    	{

	    		out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
		    		out.print("<tr>");
			    		out.print("<td width=\"21%\" nowrap CLASS=\"formLabel\">");
							out.print("Address 1: ");
						out.print("</td>");
						out.print("<td width=\"33%\"  CLASS=\"textLabel\">");
							out.print(strAddress1);
						out.print("</td>");
						out.print("<td width=\"23%\"  CLASS=\"formLabel\">");
							out.print("Address 2: ");
					    out.print("</td>");
					    out.print("<td width=\"23%\"  CLASS=\"textLabel\">");
							out.print(strAddress2);
						out.print("</td>");
		    		out.print("</tr>");

		    		out.print("<tr>");
		    			out.print("<td CLASS=\"formLabel\">");
		    				out.print("Address 3: ");
		    			out.print("</td>");
		    			out.print("<td CLASS=\"textLabel\">");
		    				out.print(strAddress3);
		    			out.print("</td>");
		    			out.print("<td CLASS=\"formLabel\">");
		    				out.print("Governorate/State: ");
		    			out.print("</td>");
		    			out.print("<td CLASS=\"textLabel\">");
		    				out.print(strStateCode);
		    			out.print("</td>");
		    		out.print("</tr>");

		    		out.print("<tr>");
		    			out.print("<td CLASS=\"formLabel\">");
		    				out.print("Area: ");
		    			out.print("</td>");
		    			out.print("<td CLASS=\"textLabel\">");
	    					out.print(strCityCode);
                            out.print("<input type=\"hidden\" name=\"frmPolicyAddress.cityCode\"  value='"+TTKCommon.getHtmlString((String)frmPolicyAddress.get("cityDesc"))+"'>");
	    			    out.print("</td>");
	    			    out.print("<td CLASS=\"formLabel\">");
	    					out.print("PO Box:  ");
	    				out.print("</td>");
	    				out.print("<td CLASS=\"textLabel\">");
    						out.print(strPinCode);
    					out.print("</td>");
		    		out.print("</tr>");

		    		out.print("<tr>");
		    			out.print("<td CLASS=\"formLabel\">");
		    				out.print("Country: ");
		    			out.print("</td>");
		    			out.print("<td colspan=\"3\" CLASS=\"textLabel\">");
		    				out.print(strCountryCode);
		    			out.print("</td>");
		    		out.print("</tr>");
	    		out.print("</table>");

	    	}//end of else if(strActiveSubLink.equals("Corporate Policy")||strActiveSubLink.equals("Non-Corporate Policy"))
	    	out.print("</fieldset>");
	   }//end of try
	   catch(Exception exp)
       {
           exp.printStackTrace();
       }//end of catch block
       return SKIP_BODY;
	}//end of doStartTag
}//end of InsuredAddressInformation