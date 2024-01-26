/**
 * @ (#) CorporateDetails.java May 15, 2006
 * Project 		: TTK HealthCare Services
 * File 		: CorporateDetails.java
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
 *  This class builds the Corporate Information
 */

public class CorporateDetails extends TagSupport  {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger( CorporateDetails.class );
	public int doStartTag() throws JspException
	{
		try
		{
			log.debug("Inside HealthcareCompany TAG :");
			JspWriter out = pageContext.getOut();//Writer object to write the file
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			// get the reference of the frmPreAuthGeneral to load the Claimant Details
			DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strGroupID=(String)frmClaimantDetails.get("groupID");
			String strGroupName=(String)frmClaimantDetails.get("groupName");
			String strInsSeqId=(String)frmClaimantDetails.get("policySeqID");
			String strPolicyTypeID=(String)frmClaimantDetails.get("policyTypeID");
			String strViewmode=" Disabled ";
			
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
				strViewmode="";
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			
			if(strPolicyTypeID.equals("COR")||strPolicyTypeID.equals("NCR"))
			{
				out.print("<fieldset style=\"display:\" id=\"corporate\">");
			}//end of if(strPolicyTypeID.equals("COR"))
			else
			{
				out.print("<fieldset style=\"display:none;\" id=\"corporate\">");
			}//end of else if(strPolicyTypeID.equals("COR"))
			
			out.print("<legend>Corporate/Non-Corporate Details</legend>");
			out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
			out.print("<tr>");
			out.print("<td width=\"22%\" class=\"formLabel\">");
			out.print("Group Id:");
			out.print("</td>");
			out.print("<td width=\"30%\" class=\"textLabelBold\">");
			out.print(strGroupID);
			out.print("</td>");
			out.print("<td width=\"19%\" class=\"formLabel\">");
			out.print("Group Name:");
			out.print("</td>");
			out.print("<td width=\"29%\" class=\"textLabelBold\">");
			out.print(strGroupName);
			out.print("&nbsp;&nbsp;&nbsp;");
			if(strViewmode.equals("")&&(strInsSeqId==null||strInsSeqId.equals("")))
			{
				if(!(strActiveLink.equals("Support"))) //koc 11 koc11
				{
				out.print("<a href=\"#\" onClick=\"selectCorporate();\">");
				out.print("<img src=\"/ttk/images/EditIcon.gif\" alt=\"Select Corporate\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>");
				}
			}//end of if(strInsSeqId==null||strInsSeqId.equals(""))
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
}//end of CorporateDetails