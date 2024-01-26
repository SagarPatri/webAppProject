/**
 * @ (#) PolicyInfo.java Feb 3, 2006
 * Project       : TTK HealthCare Services
 * File          : PolicyInfo.java
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.TTKCommon;


public class PolicyInfo extends TagSupport
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
	    	HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
	    	String strActiveSubLink=TTKCommon.getActiveSubLink(request);
	    	String strViewmode=" Disabled ";
	    	// 	get the reference of the frmPolicyDetails to load the PolicyInfo information
	    	DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");
	    	String strGroupId=(String)frmPolicyDetails.get("groupID");
	    	String strGroupName=(String)frmPolicyDetails.get("groupName");
	    	String strEmployeeCnt=(String)frmPolicyDetails.get("employeeCnt");
	    	String strMemberCnt=(String)frmPolicyDetails.get("memberCnt");
	    	String strTradeLicenseNumber = (String)frmPolicyDetails.get("strTradeLicenseNumber");


	    	if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
        	{
        		strViewmode="";
        	}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))

	    	if(strActiveSubLink.equals("Ind. Policy as Group")||strActiveSubLink.equals("Non-Corporate Policy"))
	    	{

	    		out.print("<fieldset><legend>Group Information</legend>");
		    		out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
		    			out.print("<tr>");
			    			out.print("<td width=\"21%\" CLASS=\"formLabel\">");
			    				out.print("Group Name:");
			    			out.print("</td>");
			    			out.print("<td width=\"33%\" CLASS=\"textLabelBold\">");
			    				out.print(strGroupName);
			    			out.print("</td>");
			    			out.print("<td width=\"23%\" CLASS=\"formLabel\">");
			    				out.print("Group Id: ");
			    				out.print("<span class=\"mandatorySymbol\">*</span>");
			    			out.print("</td>");
			    			out.print("<td width=\"23%\" CLASS=\"textLabelBold\">");
			    				out.print(strGroupId);
			    				out.print("&nbsp;&nbsp;");
			    				out.print("<a href=\"#\" onClick=\"javascript:onGroupList()\"><img src=\"ttk/images/EditIcon.gif\" alt=\"Change Corporate\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>");
			    			out.print("</td>");
			    		out.print("</tr>");
			    	out.print("</table>");
	    		out.print("</fieldset>");
	    	}//end of if(strActiveSubLink.equals("Ind. Policy as Group")||strActiveSubLink.equals("Non-Corporate Policy"))

	    	if(strActiveSubLink.equals("Corporate Policy"))
	    	{
	    		out.print("<fieldset><legend>Corporate Information</legend>");
		    		out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
		    			out.print("<tr>");
			    			out.print("<td width=\"21%\" CLASS=\"formLabel\">");
			    				out.print("Corporate Name: ");
		    			    out.print("</td>");
		    			    out.print("<td width=\"33%\" CLASS=\"textLabelBold\">");
		    			    	out.print(strGroupName);
		    			    out.print("</td>");
		    			    out.print("<td width=\"23%\" CLASS=\"formLabel\">");
		    			    	out.print("Group-Sub Group Id: ");
		    			    	out.print("<span class=\"mandatorySymbol\">*</span>");
	    					out.print("</td>");
	    					out.print("<td width=\"23%\" CLASS=\"textLabelBold\">");
	    						out.print(strGroupId);
			    				out.print("&nbsp;&nbsp;");
			    				if(TTKCommon.isAuthorized(request,"Edit"))
                                {
			    				    out.print("<a href=\"#\" onClick=\"javascript:onGroupList()\"><img src=\"ttk/images/EditIcon.gif\" alt=\"Change Corporate\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>");
                                }//end of if(TTKCommon.isAuthorized(request,"Edit"))
		    			    out.print("</td>");
			    		out.print("</tr>");

			    		/*out.print("<tr>");
			    			out.print("<td  CLASS=\"formLabel\">");
			    				out.print("No. of Employees: ");
			    			out.print("</td>");
			    			out.print("<td CLASS=\"textLabelBold\">");
			    				out.print("<input type=\"text\" name=\"employeeCnt\"  value='"+TTKCommon.getHtmlString(strEmployeeCnt)+"' maxlength=\"10\" class=\"textBox textBoxSmall\""+strViewmode+">");
			    			out.print("</td>");
			    			out.print("<td  CLASS=\"formLabel\">");
			    				out.print("Total Members: ");
			    			out.print("</td>");
			    			out.print("<td  CLASS=\"textLabelBold\">");
			    				out.print("<input type=\"text\" name=\"memberCnt\"  value='"+TTKCommon.getHtmlString(strMemberCnt)+"' maxlength=\"10\" class=\"textBox textBoxSmall\""+strViewmode+">");
			    			out.print("</td>");
			    		out.print("</tr>");*/
			    		out.print("<tr>");
			    		out.print("<td width=\"21%\" CLASS=\"formLabel\">");
	    				out.print("Trade License Number: ");
    			    out.print("</td>");
    			    out.print("<td width=\"21%\" CLASS=\"textLabelBold\">");
    			    	out.print(strTradeLicenseNumber);
    			    out.print("</td>");
			    		out.print("</tr>");
			    	out.print("</table>");
	    		out.print("</fieldset>");
	    	}//end of if(strActiveSubLink.equals("Corporate Policy"))
	   }//end of try
	   catch(Exception exp)
       {
           exp.printStackTrace();
       }//end of catch block
       return SKIP_BODY;
	}//end of doStartTag
}//end of PolicyInfo
