/**
 * @ (#) ReportsLink.java Jun 22, 2006
 * Project      : TTK HealthCare Services
 * File         : ReportsLink.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jun 22, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.reports;

//import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
//import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 *  This class builds the Report List Screen
 */

public class ReportsLink extends TagSupport{
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
//	private static Logger log = Logger.getLogger(ReportsLink.class );
	public int doStartTag() throws JspException
	{
		try
		{
			List reportsList=null;
			List reportTypeList=null;
			Iterator itReportType=null;
			Iterator itReports=null;
			Element eleReport=null;
			Element eleReportType=null;
			String strAclPermission="";
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			Document reportListdoc=(Document)request.getAttribute("ReportsListDoc");
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			JspWriter out = pageContext.getOut(); //Writer object to write the file
			if(reportListdoc!=null)
			{
				reportTypeList=reportListdoc.selectNodes("/reports/module[@name='"+strActiveSubLink+"']/reporttype");
			}//end of if(reportListdoc!=null)
			if(reportTypeList!=null)
			{
				itReportType=reportTypeList.iterator();
				while(itReportType.hasNext())
				{
					eleReportType=(Element)itReportType.next();
					//get the Reports for the Current Report Type
					reportsList=eleReportType.selectNodes("report");
					if(reportsList!=null)
					{
						itReports=reportsList.iterator();
						out.println("<fieldset>");
						out.println("<legend>"+eleReportType.attribute("name").getText()+"</legend>");
						out.println("<table align=\"center\" class=\"formContainer\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
							out.println("<tr><td><ul  style=\"margin-bottom:0px;\">");
							while(itReports.hasNext())
							{
								eleReport=(Element)itReports.next();
								strAclPermission=TTKCommon.checkNull(eleReport.attribute("ACL").getText());
								if(strAclPermission.equals("") || this.checkPermission(request,strAclPermission))
								{
									out.println("<li class=\"liPad\"><a href=\"#\" onClick=\"javascript:onSelectLink('"+eleReport.valueOf("@xml")+"','"+eleReport.valueOf("@id")+"','"+eleReport.valueOf("@name")+"')\">"+eleReport.valueOf("@name")+"</a></li>");
								}//end of if(strAclPermission.equals("") || this.checkPermission(request,strAclPermission))"
							}//end of while(itReports.hasNext())
							out.println("</ul></td></tr>");
						out.println("</table>");
						out.println("</fieldset>");
					}//end of if(alReports!=null)
				}//end of while(itReportType.hasNext())
			}//end of if(alReportType!=null)
		}//end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag

	/**
	 *
	 * @param request
	 * @param strPermission
	 * @return
	 * @throws TTKException
	 */
	private boolean checkPermission(HttpServletRequest request,String strPermission)throws TTKException
	{
		return ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getSecurityProfile().isAuthorized(strPermission);
	}//end of checkPermission(HttpServletRequest request,String strPermission)
}//end of ReportsLink