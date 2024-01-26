/** @ (#) PolicyAdditionalInformaion.java Feb 1st, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : PolicyAdditionalInformaion.java
 * Author     	 : Bhaskar Sandra
 * Company    	 : Span Systems Corporation
 * Date Created	 : Feb 1st, 2006
 * @author 		 : Bhaskar Sandra
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

/**
 *  This class builds the additional information of policy
 */
public class PolicyAdditionalInformaion extends TagSupport {
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger( PersonalInformation.class );
	public int doStartTag() throws JspException {
		try
		{
			log.debug("Inside PolicyAdditionalInformaion TAG :");
			JspWriter out = pageContext.getOut();
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			String disabled="disabled",readonly="readonly";
			if(TTKCommon.isAuthorized(request,"Edit") || TTKCommon.isAuthorized(request,"Add"))
			{
				disabled="";
				readonly="";
			} //end of if(TTKCommon.isAuthorized(request,"Edit") || TTKCommon.isAuthorized(request,"Add"))
			DynaActionForm frmPolicyDetail=(DynaActionForm)pageContext.getSession().getAttribute("frmPolicyDetail");
			if(!frmPolicyDetail.get("groupID").equals(""))
			{
				loadCorporatPolicy(out,frmPolicyDetail,disabled,readonly);
			}//end of if(!frmPolicyDetail.get("groupID").equals(""))
			else
			{
				loadIndividualPolicy(out,frmPolicyDetail,disabled,readonly);
			}//end of else if(!frmPolicyDetail.get("groupID").equals(""))
		}catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()

	/**
	 * This method loads  the individual policy  details
	 * @param out
	 * @param frmPolicyDetail
	 */
	private void loadIndividualPolicy(JspWriter out, DynaActionForm frmPolicyDetail,String disabled,String readonly) {
		try
		{
			ArrayList alPolicyType=Cache.getCacheObject("enrollTypeCode");
			String strPolicyTypeID=(String)frmPolicyDetail.get("policyTypeID");
			out.println("<fieldset><legend>Policy Information</legend>");
			out.println("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			out.println("<tr>");
			out.println("<td class=\"formLabel\" width=\"21%\">Policy Type:</td>");
			out.println("<td width=\"28%\">");
			if(alPolicyType != null && alPolicyType.size() > 0)
			{
				out.print("<select name=\"additionalInfoVO.departmentID\" class=\"selectBox selectBoxMedium\" "+disabled+">");
				out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alPolicyType.size(); i++)
				{
					CacheObject cacheObject = (CacheObject)alPolicyType.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strPolicyTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alDepartment.size(); i++)
				out.print("</select>");
			}//end of if(alDepartment != null && alDepartment.size() > 0)
			else if(alPolicyType != null && alPolicyType.size() == 0)
			{
				out.print("<select name=\"additionalInfoVO.departmentID\" class=\"selectBox selectBoxMedium\" "+disabled+">");
				out.print("</select>");
			}//end of else if(alDepartment != null && alDepartment.size() == 0)
			out.println("</td>");
			out.println("<td class=\"formLabel\" width=\"21%\">&nbsp;</td>");
			out.println("<td width=\"28%\">&nbsp;</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=\"21%\" class=\"formLabel\">Policy No.:</td>");
			out.println("<td width=\"28%\"><input type=\"text\" name=\"policyNbr\"  class=\"textBox textBoxMedium\" "+disabled+" "+readonly+"\" value="+frmPolicyDetail.get("policyNbr")+"></td>");
			out.println("<td>Cust. Endorsement No.:</td>");
			out.println("<td><input type=\"text\" name=\"endorsementNbr\" class=\"textBox textBoxMedium\" "+disabled+" "+readonly+" value="+frmPolicyDetail.get("endorsementNbr")+" ></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td class=\"formLabel\">Renewal Policy:</td>");
			out.println("<td><input name=\"Input\" type=\"checkbox\" value=\"\" "+disabled+"></td>");
			out.println("<td class=\"formLabel\">Previous Policy No.:</td>");
			out.println("<td><input type=\"text\" name=\"prevPolicyNbr\" class=\"textBox textBoxMedium\" "+disabled+" "+readonly+" value="+frmPolicyDetail.get("prevPolicyNbr")+"  ></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</fieldset>");
		}// end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
	}//end of loadIndividualPolicy()
	/**
	 * This method loads  the Corporat policy  details
	 * @param out
	 * @param frmPolicyDetail
	 */
	private void loadCorporatPolicy(JspWriter out, DynaActionForm frmPolicyDetail,String disabled,String readonly) {

		try
		{
			ArrayList alPolicyType=Cache.getCacheObject("enrollTypeCode");
			String strPolicyTypeID=(String)frmPolicyDetail.get("policyTypeID");
			out.print("<fieldset><legend>Policy Information</legend>");
			out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			out.print("<tr>");
			out.print("<td class=\"formLabel\">Enrollment Type:</td>");
			out.print("<td>");
			if(alPolicyType != null && alPolicyType.size() > 0)
			{
				out.print("<select name=\"additionalInfoVO.departmentID\" class=\"selectBox selectBoxMedium\" "+disabled+">");
				out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alPolicyType.size(); i++)
				{
					CacheObject cacheObject = (CacheObject)alPolicyType.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strPolicyTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alDepartment.size(); i++)
				out.print("</select>");
			}//end of if(alDepartment != null && alDepartment.size() > 0)
			else if(alPolicyType != null && alPolicyType.size() == 0)
			{
				out.print("<select name=\"additionalInfoVO.departmentID\" class=\"selectBox selectBoxMedium\" "+disabled+">");
				out.print("</select>");
			}//end of else if(alDepartment != null && alDepartment.size() == 0)
			out.println("</td>");
			out.println("<td class=\"formLabel\">&nbsp;</td>");
			out.println("<td>&nbsp;</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=\"21%\" class=\"formLabel\">Policy No.:</td>");
			out.println("<td width=\"28%\"><input name=\"policyNbr\" type=\"text\" class=\"textBox textBoxMedium\" "+disabled+" "+readonly+"  value="+frmPolicyDetail.get("policyNbr")+"></td>");
			out.println("<td width=\"23%\" class=\"formLabel\">&nbsp;</td>");
			out.println("<td width=\"28%\">&nbsp;</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td class=\"formLabel\">Proposal Form: </td>");
			String strCbkStatus="";
			if(((String)frmPolicyDetail.get("proposalFormYN")).equals("Y"))
				strCbkStatus="checked";
			else
				strCbkStatus="unchecked";
			out.println("<td><input name=\"proposalFormYN\" type=\"checkbox\" value=\"\" "+strCbkStatus+"  "+disabled+"></td>");
			out.println("<td>Endorsement No.:</td>");
			out.println("<td><input name=\"endorsementNbr\" type=\"text\" class=\"textBox textBoxMedium\" "+disabled+" "+readonly+" value="+frmPolicyDetail.get("endorsementNbr")+" ></td>");
			out.println("</tr>");
			out.println("<tr>");
			if(((String)frmPolicyDetail.get("renewalPolicyYn")).equals("Y"))
				strCbkStatus="checked";
			else
				strCbkStatus="unchecked";
			out.println("<td class=\"formLabel\">Renewal Policy:</td>");
			out.println("<td><input name=\"renewalPolicyYn\" type=\"checkbox\" value=\"\" "+strCbkStatus+" "+disabled+"></td>");
			out.println("<td class=\"formLabel\">Previous Policy No.:</td>");
			out.println("<td><input name=\"prevPolicyNbr\" type=\"text\" class=\"textBox textBoxMedium\" "+disabled+" "+readonly+"\" value="+frmPolicyDetail.get("prevPolicyNbr")+" ></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td colspan=\"4\" height=\"5\"></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td colspan=\"2\" class=\"textLabelBold\">Validity Period</td>");
			out.println("<td class=\"formLabel\">&nbsp;</td>");
			out.println("<td>&nbsp;</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td class=\"formLabel\">Start Date: </td>");
			out.println("<td><input name=\"startDate\" "+disabled+" "+readonly+"\" type=\"text\" class=\"textBox textDate\" value="+frmPolicyDetail.get("startDate")+"><A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPolicyDetail.startDate',document.frmPolicyDetail.startDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a></td>");
			out.println("<td class=\"formLabel\">End Date: </td>");
			out.println("<td><input name=\"endDate\" "+disabled+" "+readonly+"\" type=\"text\" class=\"textBox textDate\" value="+frmPolicyDetail.get("endDate")+"><A NAME=\"CalendarObjectToDate\" ID=\"CalendarObjectToDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectToDate','frmPolicyDetail.endDate',document.frmPolicyDetail.endDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</fieldset>");
			out.println("<fieldset><legend>Corporate Information </legend>");
			out.println("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			out.println("<tr>");
			out.println("<td width=\"21%\" class=\"formLabel\">Group Id: </td>");
			out.println("<td width=\"28%\" class=\"textLabelBold\">"+frmPolicyDetail.get("groupID")+"</td>");
			out.println("<td width=\"23%\" class=\"formLabel\">Corporate Name:</td>");
			out.println("<td width=\"28%\" class=\"textLabelBold\">"+frmPolicyDetail.get("groupName")+"&nbsp;&nbsp;&nbsp;<a href=\"SelectCorporate.htm\"><img src=\"/ttk/images/EditIcon.gif\" alt=\"Change Corporate\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</fieldset>");
			out.println("<fieldset><legend>Card Rules</legend>");
			out.println("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			out.println("<tr>");
			out.println("<td width=\"21%\">Generate Enrollment No.:</td>");
			out.println("<td width=\"28%\"><select name=\"eType\" ID=\"Select1\" class=\"selectBox selectBoxMedium\" "+disabled+">");
			out.println("<option value=\"\" selected>Select from list</option>");
			out.println(" <option value=\"\" >Default</option>");
			out.println("</select>");
			out.println("</td>");
			out.println("<td width=\"23%\" class=\"formLabel\">Card Dispatched to: </td>");
			out.println("<td width=\"28%\"><select name=\"eType\" ID=\"Select1\" class=\"selectBox selectBoxMedium\" "+disabled+">");
			out.println("<option value=\"\" selected>Select from list</option>");
			out.println("<option value=\"\" >Individual Address</option>");
			out.println("</select>");
			out.println("</td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</fieldset>");
		}// end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
	} // end of loadCorporatPolicy()
}//end of PolicyAdditionalInformation