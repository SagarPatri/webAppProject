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

import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;
//import com.ttk.dto.usermanagement.UserSecurityProfile;
//import com.ttk.dto.usermanagement.UserVO;
public class ClaimantDetails  extends TagSupport  {
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger(ClaimantDetails.class );
	//public UserSecurityProfile userSecurityProfile=null;
	public int doStartTag() throws JspException
	{
		try
		{
			log.debug("Inside PolicyInfo TAG :");
			JspWriter out = pageContext.getOut();//Writer object to write the file
			CacheObject cacheObject = null;
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			//get the required Cache Data to load select boxes
			ArrayList alGender =Cache.getCacheObject("gender");
			//ArrayList alCloseProximity =Cache.getCacheObject("closeProximity");
			ArrayList alRelationshipCode =Cache.getCacheObject("relationshipCode");
			
			//get the reference of the frmPreAuthGeneral to load the Claimant Details
			DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			//String strProximityDate=(String)frmPreAuthGeneral.get("proximityDate");
			//String strProximityStatusTypeID=(String)frmPreAuthGeneral.get("proximityStatusTypeID");
			DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
			DynaActionForm frmClaimanDetail=(DynaActionForm)frmPreAuthGeneral.get("claimDetailVO");
			String strShowReAssignIDYN=(String)frmPreAuthGeneral.get("showReAssignIDYN");
//			String strAmmendmentYN=(String)frmPreAuthGeneral.get("ammendmentYN");
	        //log.info("ShowReAssignIDYN :"+ strShowReAssignIDYN);
	           
			String strEnrollmentID=(String)frmClaimantDetails.get("enrollmentID");
			String strName=(String)frmClaimantDetails.get("name");
			String strGenderTypeID=(String)frmClaimantDetails.get("genderTypeID");
			String strAge=(String)frmClaimantDetails.get("age");
			String strDateOfInception=(String)frmClaimantDetails.get("dateOfInception");
			String strDateOfExit=(String)frmClaimantDetails.get("dateOfExit");
			String strTotalSumInsured=(String)frmClaimantDetails.get("totalSumInsured");
			String strAvailSumInsured=(String)frmClaimantDetails.get("availSumInsured");
			String strAvblBufferAmount=(String)frmClaimantDetails.get("avblBufferAmount");
			String strCumulativeBonus=(String)frmClaimantDetails.get("cumulativeBonus");
			String strCategoryDesc=(String)frmClaimantDetails.get("categoryDesc");
			String strSumEnhancedYN=(String)frmClaimantDetails.get("sumEnhancedYN");
			String strClaimantNameDisc=(String)frmClaimantDetails.get("claimantNameDisc");
			String strGenderDiscrepancy=(String)frmClaimantDetails.get("genderDiscrepancy");
			String strAgeDiscrepancy=(String)frmClaimantDetails.get("ageDiscrepancy");
			String strEmployeeNbr=(String)frmClaimantDetails.get("employeeNbr");
			String strEmployeeName=(String)frmClaimantDetails.get("employeeName");
			String strRelshipTypeID=(String)frmClaimantDetails.get("relationTypeID");
			String strClaimantPhoneNbr=(String)frmClaimantDetails.get("claimantPhoneNbr");
			String strShowBandYN=(String)frmPreAuthGeneral.get("showBandYN");
			String strPreAuthTypeID=(String)frmPreAuthGeneral.get("preAuthTypeID");
			String strCompletedYN=(String)frmPreAuthGeneral.get("completedYN");
			String strRequestTypeID=(String)frmClaimanDetail.get("requestTypeID");
			String strEmailID=(String)frmClaimantDetails.get("emailID");
			String strNotifyPhoneNbr=(String)frmClaimantDetails.get("notifyPhoneNbr");
			String strInsCustCode=(String)frmClaimantDetails.get("insCustCode");
			
			String groupID=(String)frmClaimantDetails.get("groupID");
			String vipYN=(String)frmClaimantDetails.get("vipYN");
			
			String strViewmode=" Disabled ";
			String strViewmode1=" Disabled ";
			String strViewmode2=" Disabled ";
			String strActiveLink=TTKCommon.getActiveLink(request);
			
			String strClassMedium = "textBox textBoxMedium";
			String strClassSmall = "textBox textBoxSmall";
			String strClassSelect = "selectBox selectBoxMedium";
			String strClassDate = "textBox textDate";			
			
			/*if(TTKCommon.isAuthorized(request,"Edit"))
			{
				strViewmode2="";
			}
			if(TTKCommon.isAuthorized(request,"Edit")&& strEnrollmentID.equals(""))
			{
				strViewmode="";
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			if(TTKCommon.isAuthorized(request,"Edit") && (!"Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request))))
			{
				strViewmode1="";
			}*/
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
				strViewmode2="";
				if(strEnrollmentID.equals(""))
				{
					strViewmode="";
					strViewmode1="";
				}//end of if(strEnrollmentID.equals(""))
				else
				{
					//strClassLarge="textBox textBoxLarge textBoxDisabled";
					strClassMedium = "textBox textBoxMedium textBoxDisabled";
					strClassSmall = "textBox textBoxSmall textBoxDisabled";
					strClassSelect="selectBox selectBoxMedium selectBoxDisabled";
					//strClassTiny = "textBox textBoxTiny textBoxDisabled";
					strClassDate = "textBox textDate textBoxDisabled";			
				}//end of else
				if("Claims".equals(strActiveLink) ||("DataEntryClaims".equals(strActiveLink))  && (!"Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request))))
				{
					strViewmode1="";
				}//end of if("Claims".equals(strActiveLink) && (!"Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request))))
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			
			if(strActiveLink.equals("Claims")||(strActiveLink.equals("DataEntryClaims")))
			{
				strShowBandYN="N";
			}else
			{
				if(strPreAuthTypeID.equals("MAN"))
				{
					if(!strEnrollmentID.equals("")&&strShowBandYN.equals("Y"))
					{
						strShowBandYN="Y";
					}else
					{
						strShowBandYN="N";
					}//end of if(!strEnrollmentID.equals("")&&strShowBandYN.equals("Y"))
				}//end of else if(strPreAuthTypeID.equals("MAN"))
			}//end of if(strActiveTab.equals("Claims"))
			
			out.print("<fieldset><legend>Member Details</legend>");
			out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
			out.print("<tr>");
			out.print("<td width=\"22%\" class=\"formLabel\">");
			out.print("Enrollment Id: ");
			out.print("</td>");
			out.print("<td width=\"30%\" class=\"formLabel\">");
			out.print("<input type=\"text\" name=\"claimantDetailsVO.enrollmentID\"  value='"+TTKCommon.getHtmlString(strEnrollmentID)+"' maxlength=\"60\"  readonly=\"true\" class=\"textBoxDisabled textBoxLong\""+strViewmode+">");
			out.print("&nbsp;&nbsp;");
			log.debug("@@@@@@@@@@strShowReAssignIDYN is 	:"+strShowReAssignIDYN);
			log.debug(" strShowBandYN 					:"+strShowBandYN);
			log.debug(" strCompletedYN 					:"+strCompletedYN);
			log.debug(" strRequestTypeID 				:"+strRequestTypeID);
			log.debug(" Edit 							:"+TTKCommon.isAuthorized(request,"Edit"));		
			if(TTKCommon.isAuthorized(request,"Edit")&&!strShowBandYN.equals("Y")&& strCompletedYN.equals("N") && !"DTA".equals(strRequestTypeID) && (!TTKCommon.checkNull(strShowReAssignIDYN).equals("Y")))
			{		if(!(strActiveLink.equals("Support"))) //koc 11 koc11
					{	
					out.print("<a href=\"#\" onClick=\"javascript:selectEnrollmentID();\">");
					out.print("<img src=\"/ttk/images/EditIcon.gif\" alt=\"Select Enrollment\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");out.print("</a>&nbsp;&nbsp;");
					out.print("<a href=\"#\" onClick=\"javascript:clearEnrollmentID();\">");
					out.print("<img src=\"/ttk/images/DeleteIcon.gif\" alt=\"Clear Enrollment Id\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");
					out.print("</a>");
					}//end of if(!(strActiveLink.equals("Support")))
			}//end of  if(!strShowBandYN.equals("Y"))
			else if(strCompletedYN.equals("Y") && strPreAuthTypeID.equals("MAN")) {
				if(!(strActiveLink.equals("Support"))) //koc 11 koc11
				{
				out.print("<a href=\"#\" onClick=\"javascript:selectEnrollmentID();\">");
				out.print("<img src=\"/ttk/images/EditIcon.gif\" alt=\"Select Enrollment\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");out.print("</a>&nbsp;&nbsp;");
				out.print("<a href=\"#\" onClick=\"javascript:clearEnrollmentID();\">");
				out.print("<img src=\"/ttk/images/DeleteIcon.gif\" alt=\"Clear Enrollment Id\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");
				out.print("</a>");
				}
			}//end of else if(strCompletedYN.equals("Y") && strPreAuthTypeID.equals("MAN")) 
				
//			if(TTKCommon.isAuthorized(request,"Edit") && strCompletedYN.equals("N") && )
//			{
			if(strShowReAssignIDYN.equals("Y"))
			{
				if(!(strActiveLink.equals("Support"))) //koc 11 koc11
				{
				out.print("<a href=\"#\" onClick=\"javascript:reassignEnrID();\">");
				out.print("<img src=\"/ttk/images/Reassociate.gif\" alt=\"Reassign Enrollment ID\" width=\"20\" height=\"20\" border=\"0\" align=\"absmiddle\">");out.print("</a>&nbsp;&nbsp;");
				}
			}//end of if( !"DTA".equals(strRequestTypeID) && strShowReAssignIDYN.equals("Y"))
			//Before Changes of KOC1136 Change REQUEST
			/*out.print("</td>");
			out.print("<td width=\"19%\" class=\""+TTKCommon.compareValues(strClaimantNameDisc,"N","formLabel","labelRed")+"\">");
			out.print("Member Name: ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");
			out.print("<td width=\"29%\" class=\"formLabel\">");
			out.print("<input type=\"text\" name=\"claimantDetailsVO.name\"  value='"+TTKCommon.getHtmlString(strName)+"' onkeypress=\"ConvertToUpperCase(event.srcElement);\" maxlength=\"60\" class=\"textBox textBoxLarge\""+strViewmode1+">");
			out.print("</td>");
			out.print("</tr>");
			*/
			//Before Changes of KOC1136 Change REQUEST
			
			//Changes on date DEC 6th KOC 1136
			
			if(vipYN.equalsIgnoreCase("Y"))
			{
				out.print("</td>");
				out.print("<td width=\"19%\" class=\""+TTKCommon.compareValues(strClaimantNameDisc,"N","formLabel","labelRed")+"\">");
				out.print("Member Name: ");
				out.print("<span class=\"mandatorySymbol\">*</span>");
				//Change on date dec 6th
				out.print("<img src=\"/ttk/images/BlueStar.gif\" alt=\"VipCustomer\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");out.print("</a>&nbsp;&nbsp;");
				//Change on date dec 6th
				out.print("</td>");
				out.print("<td width=\"29%\" class=\"formLabel\">");
				out.print("<input type=\"text\" name=\"claimantDetailsVO.name\"  value='"+TTKCommon.getHtmlString(strName)+"' onkeypress=\"ConvertToUpperCase(event.srcElement);\" maxlength=\"60\" class=\"textBox textBoxLarge\""+strViewmode1+">");
				out.print("</td>");
				out.print("</tr>");
			}
			else{
				out.print("</td>");
				out.print("<td width=\"19%\" class=\""+TTKCommon.compareValues(strClaimantNameDisc,"N","formLabel","labelRed")+"\">");
				out.print("Member Name: ");
				out.print("<span class=\"mandatorySymbol\">*</span>");
				out.print("</td>");
				out.print("<td width=\"29%\" class=\"formLabel\">");
				out.print("<input type=\"text\" name=\"claimantDetailsVO.name\"  value='"+TTKCommon.getHtmlString(strName)+"' onkeypress=\"ConvertToUpperCase(event.srcElement);\" maxlength=\"60\" class=\"textBox textBoxLarge\""+strViewmode1+">");
				out.print("</td>");
				out.print("</tr>");
				
			}	//Changes on date DEC 6th KOC 1136
					
			
			out.print("<tr>");
			out.print("<td class=\""+TTKCommon.compareValues(strGenderDiscrepancy,"N","formLabel","labelRed")+"\">" );
			out.print("Gender: ");
			out.print("</td>");
			out.print("<td class=\"formLabel\">" );
			if(alGender != null && alGender.size() > 0)
			{
				out.print("<select name=\"claimantDetailsVO.genderTypeID\" onChange=\"javascript:selectageID();\" CLASS=\"selectBox selectBoxMedium\""+strViewmode1+">");//KOC FOR Grievance
				//out.print("<option value=\"\">Select from list</option>");
				for(int i=0; i < alGender.size(); i++)
				{
					cacheObject = (CacheObject)alGender.get(i);
					out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strGenderTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
				}//end of for(int i=0; i < alProductCode.size(); i++)
				out.print("</select>");
			}//end of if(alGender != null && alGender.size() > 0)
			else if(alGender != null && alGender.size() == 0)
			{
				out.print("<select name=\"claimantDetailsVO.genderTypeID\" onChange=\"javascript:selectageID();\" CLASS=\"selectBox selectBoxMedium\">");//KOC FOR Grievance
				out.print("<option value=\"\">Select from list</option>");
				out.print("</select>");
			}//end of else if(alGender != null && alGender.size() == 0)
			out.print("</td>");
			out.print("<td class=\""+TTKCommon.compareValues(strAgeDiscrepancy,"N","formLabel","labelRed")+"\">" );
			out.print("Age (Yrs): ");
			out.print("<span class=\"mandatorySymbol\">*</span>");
			out.print("</td>");
			out.print("<td class=\"textLabel\">" );
			out.print("<input type=\"text\" name=\"claimantDetailsVO.age\"  value='"+TTKCommon.getHtmlString(strAge)+"' maxlength=\"3\" onChange=\"javascript:selectageID();\" class=\"textBox textBoxTiny\""+strViewmode1+">");//KOC FOR Grievance
			out.print("</td>");
			out.print("</tr>");
			
			out.print("<tr>");
			out.print("<td class=\"formLabel\">" );
			out.print("Date of Inception:");
			out.print("</td>");
			out.print("<td class=\"formLabel\">" );
			out.print("<input type=\"text\" name=\"claimantDetailsVO.dateOfInception\"  value='"+TTKCommon.getHtmlString(strDateOfInception)+"' maxlength=\"10\" class=\""+strClassDate+"\""+strViewmode+">");
			if(TTKCommon.isAuthorized(request,"Edit")&&strEnrollmentID.equals(""))
			{
				out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPreAuthGeneral.elements[\\'claimantDetailsVO.dateOfInception\\']',document.frmPreAuthGeneral.elements['claimantDetailsVO.dateOfInception'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))                                                                                                                                                       //'frmAddHospital.elements[\'documentDetailVO.mouRcvdDate\']',document.frmAddHospital.elements['documentDetailVO.mouRcvdDate'].value,'',
			out.print("</td>");
			out.print("<td class=\"formLabel\">" );
			out.print("Date of Exit:");
			out.print("</td>");
			out.print("<td class=\"formLabel\">" );
			out.print("<input type=\"text\" name=\"claimantDetailsVO.dateOfExit\"  value='"+TTKCommon.getHtmlString(strDateOfExit)+"' maxlength=\"10\" class=\""+strClassDate+"\""+strViewmode+">");
			if(TTKCommon.isAuthorized(request,"Edit")&&strEnrollmentID.equals(""))
			{
				out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPreAuthGeneral.elements[\\'claimantDetailsVO.dateOfExit\\']',document.frmPreAuthGeneral.elements['claimantDetailsVO.dateOfExit'].value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			out.print("</td>");
			out.print("</tr>");
			//modified for Decoupling
			if(TTKCommon.isAuthorized(request,"SpecialPermission")&& (!TTKCommon.getActiveLink(request).equals("DataEntryClaims")))
			{
				out.print("<tr>");
				out.print("<td class=\"formLabel\">" );
				out.print("Total Sum Insured (Rs):");
				out.print("&nbsp;");
				if(strSumEnhancedYN.equals("Y"))
				{
					if(!(strActiveLink.equals("Support"))) //koc 11 koc11
					{
					out.print("<a href=\"#\" onclick=\"javascript:onEnhancementAmount()\">");out.println("<img src=\"/ttk/images/RedStar.gif\" alt=\"Sum Insured Enhanced\" width=\"15\" height=\"12\" border=\"0\" align=\"absmiddle\">");out.println("</a>");
					}
				}//end of if(strSumEnhancedYN.equals("Y"))
				out.print("</td>");
				out.print("<td class=\"formLabel\">" );
				out.print("<input type=\"text\" name=\"claimantDetailsVO.totalSumInsured\"  value='"+TTKCommon.getHtmlString(strTotalSumInsured)+"' maxlength=\"13\" class=\""+strClassSmall+"\""+strViewmode+">");
				out.print("</td>");
				out.print("<td class=\"formLabel\">" );
				out.print("Bal. Sum Insured (Rs):");
				out.print("</td>");
				out.print("<td class=\"formLabel\">" );
				out.print("<input type=\"text\" name=\"claimantDetailsVO.availSumInsured\"  value='"+TTKCommon.getHtmlString(strAvailSumInsured)+"' maxlength=\"13\" class=\""+strClassSmall+"\""+strViewmode+">");
				out.print("</td>");
				out.print("</tr>");
				
				
				out.print("<tr>");
				out.print("<td class=\"formLabel\">");
				out.print("Avail. Buffer Amt. (Rs):");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"claimantDetailsVO.avblBufferAmount\"  value='"+TTKCommon.getHtmlString(strAvblBufferAmount)+"' maxlength=\"13\" class=\"textBoxDisabled textBoxSmall \" Disabled>");
				out.print("</td>");
				
				out.print("<td class=\"formLabel\">");
				out.print("Cumulative Bonus (Rs):");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"claimantDetailsVO.cumulativeBonus\"  value='"+TTKCommon.getHtmlString(strCumulativeBonus)+"' maxlength=\"13\" class=\""+strClassSmall+"\""+strViewmode+">");
				out.print("</td>");
				out.print("</tr>");
			}//end of if(TTKCommon.isAuthorized(request,"SpecialPermission"))
			if(TTKCommon.getActiveLink(request).equals("Claims")||(TTKCommon.getActiveLink(request).equals("DataEntryClaims")))
			{
				out.print("<tr>");
				out.print("<td class=\"formLabel\">");
				out.print("Employee No.:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"claimantDetailsVO.employeeNbr\"  value='"+TTKCommon.getHtmlString(strEmployeeNbr)+"' maxlength=\"13\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				
				out.print("<td class=\"formLabel\">");
				out.print("Employee Name:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"claimantDetailsVO.employeeName\"  value='"+TTKCommon.getHtmlString(strEmployeeName)+"' maxlength=\"60\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<td class=\"formLabel\">");
				out.print("Phone (Notify):");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"claimantDetailsVO.notifyPhoneNbr\"  value='"+TTKCommon.getHtmlString(strNotifyPhoneNbr)+"' maxlength=\"13\" class=\"textBox textBoxMedium\""+strViewmode2+">");
				out.print("</td>");
				out.print("<td class=\"formLabel\">");
				out.print("Email Id:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"claimantDetailsVO.emailID\"  value='"+TTKCommon.getHtmlString(strEmailID)+"' maxlength=\"250\" class=\"textBox textBoxLarge\""+strViewmode2+">");
				out.print("</td>");
				out.print("</tr>");
				
				
				out.print("<tr>");
				out.print("<td class=\"formLabel\">");
				out.print("Relationship:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				if(alRelationshipCode != null && alRelationshipCode.size() > 0)
				{
					out.print("<select name=\"claimantDetailsVO.relshipTypeID\" CLASS=\""+strClassSelect+"\""+strViewmode+">");
					out.print("<option value=\"\">Select from list</option>");
					for(int i=0; i < alRelationshipCode.size(); i++)
					{
						cacheObject = (CacheObject)alRelationshipCode.get(i);
						out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strRelshipTypeID, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
					}//end of for(int i=0; i < alProductCode.size(); i++)
					out.print("</select>");
				}//end of if(alGender != null && alGender.size() > 0)
				else if(alRelationshipCode != null && alRelationshipCode.size() == 0)
				{
					out.print("<select name=\"claimantDetailsVO.relshipTypeID\" CLASS=\""+strClassSelect+"\">");
					out.print("<option value=\"\">Select from list</option>");
					out.print("</select>");
				}//end of else if(alGender != null && alGender.size() == 0)
				out.print("</td>");
				
				out.print("<td class=\"formLabel\">");
				out.print("Member Phone No.:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"claimantDetailsVO.claimantPhoneNbr\"  value='"+TTKCommon.getHtmlString(strClaimantPhoneNbr)+"' maxlength=\"13\" class=\"textBox textBoxMedium\""+strViewmode+" >");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td class=\"formLabel\">" );
				out.print("Customer Code:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">" );
				out.print("<input type=\"text\" name=\"claimantDetailsVO.insCustCode\"  value='"+TTKCommon.getHtmlString(strInsCustCode)+"' maxlength=\"60\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("</tr>");
			}//end of if(TTKCommon.getActiveLink(request).equals("Claims"))
			
			if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
			{
				out.print("<tr>");
				out.print("<td height=\"20\"  class=\"formLabel\">" );
				out.print("Category:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">" );
				out.print(strCategoryDesc);
				out.print("</td>");
				out.print("<td class=\"formLabel\">" );
				out.print("Email Id:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">" );
				out.print("<input type=\"text\" name=\"claimantDetailsVO.emailID\"  value='"+TTKCommon.getHtmlString(strEmailID)+"' maxlength=\"250\" class=\"textBox textBoxLarge\""+strViewmode2+">");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td class=\"formLabel\">" );
				out.print("Customer Code:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">" );
				out.print("<input type=\"text\" name=\"claimantDetailsVO.insCustCode\"  value='"+TTKCommon.getHtmlString(strInsCustCode)+"' maxlength=\"60\" class=\""+strClassMedium+"\""+strViewmode1+">");
				out.print("</td>");
				out.print("</tr>");
			}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
			
			out.print("</table>");
			out.print("</fieldset>");
		}//end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag
}//end of MemberDetails