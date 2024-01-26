/**
 * @ (#) HospitalDetails.java May 11, 2006
 * Project       : TTK HealthCare Services
 * File          : HospitalDetails.java
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;

import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
//import com.ttk.dto.common.CacheObject;


public class HospitalDetails  extends TagSupport  {
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger( HospitalDetails.class );
	
	public int doStartTag() throws JspException
	{
		try
		{
			log.debug("Inside HospitalDetails TAG :");
			JspWriter out = pageContext.getOut();//Writer object to write the file
//			CacheObject cacheObject = null;
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			String switchType=TTKCommon.checkNull((String)request.getSession().getAttribute("switchType")); //koc11 koc 11
			//get the reference of the frmPreAuthGeneral to load the Claimant Details
			DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			DynaActionForm frmPreAuthHospital=(DynaActionForm)frmPreAuthGeneral.get("preAuthHospitalVO");
			//DynaActionForm frmClaimDetail=(DynaActionForm)frmPreAuthGeneral.get("preAuthHospitalVO");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strHospSeqId=(String)frmPreAuthHospital.get("hospSeqId");
			String strHospitalName=(String)frmPreAuthHospital.get("hospitalName");
			String strEmplNumber=(String)frmPreAuthHospital.get("emplNumber");
			String strAddress1=(String)frmPreAuthHospital.get("address1");
			String strAddress2=(String)frmPreAuthHospital.get("address2");
			String strAddress3=(String)frmPreAuthHospital.get("address3");
			String strStateName=(String)frmPreAuthHospital.get("stateName");
			String strCityDesc=(String)frmPreAuthHospital.get("cityDesc");
			String strPincode=(String)frmPreAuthHospital.get("pincode");
			String strCountryName=(String)frmPreAuthHospital.get("countryName");
			String strEmailID=(String)frmPreAuthHospital.get("emailID");
			String strPhoneNbr1=(String)frmPreAuthHospital.get("phoneNbr1");
			String strPhoneNbr2=(String)frmPreAuthHospital.get("phoneNbr2");
			String strFaxNbr=(String)frmPreAuthHospital.get("faxNbr");
			String strHospStatus=(String)frmPreAuthHospital.get("hospStatus");
			String strHospServiceTaxRegnNbr=(String)frmPreAuthHospital.get("hospServiceTaxRegnNbr");
			String strHospRemarks=(String)frmPreAuthHospital.get("hospRemarks");
			
			String strRating=(String)frmPreAuthHospital.get("rating");
			String strRatingImageTitle=(String)frmPreAuthHospital.get("ratingImageTitle");
			String strRatingImageName=(String)frmPreAuthHospital.get("ratingImageName");
			String strStatusDisYN=(String)frmPreAuthHospital.get("statusDisYN");
			
			String strAuthNbr=(String)frmPreAuthGeneral.get("authNbr");
			String strPrevHospClaimSeqID=(String)frmPreAuthGeneral.get("prevHospClaimSeqID");
			String strShowBandYN=(String)frmPreAuthGeneral.get("showBandYN");
			String str="false";
			
			if(strPrevHospClaimSeqID==null)
				strPrevHospClaimSeqID="";
			
			String strPreAuthViewmode=" Disabled ";
			String strImgViewmode=" Disabled ";
			String strViewmode=" Disabled ";
			
			String strClassMedium = "textBox textBoxMedium";
		    String strClassArea = "textBox textAreaLong";
		       
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
				if(!strAuthNbr.equals("")||!strPrevHospClaimSeqID.equals(""))
				{
					strImgViewmode=" Disabled ";
				}else
				{
					strImgViewmode="";
				}//end of if(!strAuthNbr.equals("")||!strPrevHospClaimSeqID.equals(""))
				
				if(strHospSeqId!=null&&!strHospSeqId.equals(""))
				{
					strClassMedium = "textBox textBoxMedium textBoxDisabled";
		        	strClassArea = "textBox textAreaLong textBoxDisabled";
		        	strViewmode=" Disabled ";
				}//end of if(strHospSeqId!=null&&!strHospSeqId.equals(""))
				else
				{
					strViewmode="";
				}//end of else
				strPreAuthViewmode="";
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
			
			String strShow="";
			if(strActiveLink.equals("Pre-Authorization")||((strActiveLink.equals("Support") && switchType.equals("PreAuth"))))			
			{
				strShow="Y";
			}//end of if(strActiveLink.equals("Pre-Authorization"))
			if(strActiveLink.equals("Claims")||strActiveLink.equals("DataEntryClaims")||(strActiveLink.equals("Support") && switchType.equals("Claim")))
			{
				
				DynaActionForm frmClaimDetail=(DynaActionForm)frmPreAuthGeneral.get("claimDetailVO");
				String strClaimSubTypeID=(String)frmClaimDetail.get("claimSubTypeID");
				String strClaimTypeDesc =(String)frmClaimDetail.get("claimTypeDesc");
				if(strClaimSubTypeID.equals("CSD"))
				{
					strShow="N";
				}//end of if(strClaimSubTypeID.equals("CSD"))
				else
				{
					strShow="Y";
				}//end of else if(strClaimSubTypeID.equals("CSD"))
				if(strClaimTypeDesc.equals("Member"))
				{
					str="true";
				}//end of if(strClaimTypeDesc.equals("Member"))
			}//end of if(strActiveLink.equals("Claims"))
			if(strShow.equals("Y"))
			{
				out.print("<div id=\"hospitalinfo\" style=\"display:\">");
			}//end of if(strShow.equals("Y"))
			else
			{
				out.print("<div id=\"hospitalinfo\" style=\"display:none;\">");
			}//end of else if(strShow.equals("Y"))
			
			out.print("<fieldset>");
			out.print("<legend>Provider Details</legend>");
			out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			if(strActiveLink.equals("Pre-Authorization")||((strActiveLink.equals("Support") && switchType.equals("PreAuth"))))
			{
				out.print("<tr>");
				out.print("<td width=\"22%\" height=\"20\" class=\"formLabel\">Provider Name: <span class=\"mandatorySymbol\">*</span></td>");
				out.print("<td width=\"30%\" class=\"textLabelBold\">");
				out.print(strHospitalName);
				out.print("&nbsp;&nbsp;");
				if(strRating!=null&&!strRating.equals(""))
				{
					out.print("<img src=\"/ttk/images/"+strRatingImageName+".gif\" alt=\""+strRatingImageTitle+"\" width=\"15\" height=\"12\" align=\"absmiddle\">");
				}//end of if(strRating!=null&&!strRating.equals(""))
				out.print("</td>");
				out.print("<td class=\"formLabel\" width=\"19%\">Empanelment No.:</td>");
				out.print("<td class=\"textLabelBold\" width=\"29%\">");
				out.print(strEmplNumber);
				out.print("&nbsp;&nbsp;&nbsp;");
				if(strPreAuthViewmode.equals("")&&!strShowBandYN.equals("Y"))
				{
					if(!(strActiveLink.equals("Support"))) //koc 11 koc11
					{
					out.print("<a href=\"#\" onclick=\"javascript:selectHospital();\">");
					out.print("<img src=\"/ttk/images/EditIcon.gif\" alt=\"Select Hospital\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");
					out.print("</a>");
					}//end of if(!(strActiveLink.equals("Support")))
				}//end of if(strPreAuthViewmode.equals("")&&!strShowBandYN.equals("Y"))
				
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">Address 1: </td>");
				out.print("<td class=\"textLabel\">");
				out.print(strAddress1);
				out.print("</td>");
				out.print("<td class=\"formLabel\">Address 2: </td>");
				out.print("<td class=\"textLabel\">");
				out.print(strAddress2);
				out.print("</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">Address 3:</td>");
				out.print("<td class=\"textLabel\">");
				out.print(strAddress3);
				out.print("</td>");
				out.print("<td class=\"formLabel\">State: </td>");
				out.print("<td class=\"textLabel\">");
				out.print(strStateName);
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">City: </td>");
				out.print("<td class=\"textLabel\">");
				out.print(strCityDesc);
				out.print("</td>");
				out.print("<td class=\"formLabel\">Pincode:</td>");
				out.print("<td class=\"textLabel\">");
				out.print(strPincode);
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">Country:</td>");
				out.print("<td class=\"textLabel\">");
				out.print(strCountryName);
				out.print("</td>");
				out.print("<td class=\"formLabel\">Email Id:</td>");
				out.print("<td class=\"textLabel\">");
				out.print(strEmailID);
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">Office Phone 1: </td>");
				out.print("<td class=\"textLabel\">");
				out.print(strPhoneNbr1);
				out.print("</td>");
				out.print("<td class=\"formLabel\">Office Phone 2:</td>");
				out.print("<td class=\"textLabel\">");
				out.print(strPhoneNbr2);
				out.print("</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">Fax:</td>");
				out.print("<td class=\"textLabel\">");
				out.print(strFaxNbr);
				out.print("</td>");
				
				if(strStatusDisYN.equals("Y"))
					out.print("<td height=\"20\" class=\"labelRed\">");
				
				if(!strStatusDisYN.equals("Y"))
					
					out.print("<td height=\"20\" class=\"formLabel\">");
				
				out.print("Empanel. Status:");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				out.print(strHospStatus);
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td height=\"20\" class =\"formLabel\">Service Tax Regn. No.:</td>");
				out.print("<td class =\"textLabel\">");
				out.print(strHospServiceTaxRegnNbr);
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td height=\"20\" valign=\"top\" class=\"formLabel\">Remarks:</td>");
				out.print("<td colspan=\"3\" class=\"textLabel\">");
				out.print(strHospRemarks);
				out.print("</td>");
				out.print("</tr>");
			}//end of if(strActiveLink.equals("Pre-Authorization"))
			if(strActiveLink.equals("Claims")||strActiveLink.equals("DataEntryClaims")||(strActiveLink.equals("Support") && switchType.equals("Claim")))
			{
				//To disable all fields, Edit and Clear Provider Icons in Ammendment flow
				if("Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request)))
				{
					strImgViewmode=" Disabled ";
					strViewmode=" Disabled ";
				}//end of if("Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request)))
				
				out.print("<tr>");
				out.print("<td width=\"22%\" height=\"20\" class=\"formLabel\">Provider Name: <span class=\"mandatorySymbol\">*</span></td>");
				out.print("<td width=\"30%\" class=\"textLabelBold\">");
				
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.hospitalName\"  value='"+TTKCommon.getHtmlString(strHospitalName)+"' onkeypress=\"ConvertToUpperCase(event.srcElement);\" maxlength=\"60\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("&nbsp;&nbsp;");
				if(strRating!=null&&!strRating.equals(""))
				{
					out.print("<img src=\"/ttk/images/"+strRatingImageName+".gif\" alt=\""+strRatingImageTitle+"\" width=\"15\" height=\"12\" align=\"absmiddle\">");
				}//end of if(strRating!=null&&!strRating.equals(""))
				out.print("</td>");
				out.print("<td class=\"formLabel\" width=\"19%\">Empanelment No.:</td>");
				out.print("<td class=\"textLabelBold\" width=\"29%\">");
				//out.print("<input type=\"text\" name=\"preAuthHospitalVO.emplNumber\"  value='"+TTKCommon.getHtmlString(strEmplNumber)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
				out.print(strEmplNumber);
				out.print("&nbsp;&nbsp;&nbsp;");
				
				if(strImgViewmode.equals(""))
				{
					if(!(strActiveLink.equals("Support"))) //koc 11 koc11
					{
					out.print("<a href=\"#\" onclick=\"javascript:selectHospital();\">");
					out.print("<img src=\"/ttk/images/EditIcon.gif\" alt=\"Select Hospital\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");
					out.print("</a>");
					
					out.print("&nbsp;&nbsp;<a href=\"#\" onclick=\"javascript:onClearHospital();\">");
					out.print("<img src=\"/ttk/images/DeleteIcon.gif\" alt=\"Clear Hospital\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");
					out.print("</a>");
					}
				}//end of if(strImgViewmode.equals(""))
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">Address 1:");
				out.print("<span class=\"mandatorySymbol\">*</span>");
				out.print("</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.address1\"  value='"+TTKCommon.getHtmlString(strAddress1)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("<td class=\"formLabel\">Address 2: </td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.address2\"  value='"+TTKCommon.getHtmlString(strAddress2)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">Address 3:</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.address3\"  value='"+TTKCommon.getHtmlString(strAddress3)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("<td class=\"formLabel\">State: <span class=\"mandatorySymbol\">*</span></td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.stateName\"  value='"+TTKCommon.getHtmlString(strStateName)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">City: <span class=\"mandatorySymbol\">*</span></td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.cityDesc\"  value='"+TTKCommon.getHtmlString(strCityDesc)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("<td class=\"formLabel\">Pincode:</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.pincode\"  value='"+TTKCommon.getHtmlString(strPincode)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("</tr>");
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">Country: </td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.countryName\"  value='"+TTKCommon.getHtmlString(strCountryName)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				//added for Decoupling
		        if(!TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
		        { 
						out.print("<td class=\"formLabel\">Email Id:</td>");
						out.print("<td class=\"textLabel\">");
						out.print("<input type=\"text\" name=\"preAuthHospitalVO.emailID\"  value='"+TTKCommon.getHtmlString(strEmailID)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
						out.print("</td>");
				}		
				out.print("</tr>");
				//added for Decoupling
				//if(!TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
		        //{ 
					out.print("<tr>");
					out.print("<td height=\"20\" class=\"formLabel\">Office Phone 1: <span class=\"mandatorySymbol\">*</span></td>");
					out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.phoneNbr1\"  value='"+TTKCommon.getHtmlString(strPhoneNbr1)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("<td class=\"formLabel\">Office Phone 2:</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.phoneNbr2\"  value='"+TTKCommon.getHtmlString(strPhoneNbr2)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				out.print("</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<td height=\"20\" class=\"formLabel\">Fax:</td>");
				out.print("<td class=\"textLabel\">");
				out.print("<input type=\"text\" name=\"preAuthHospitalVO.faxNbr\"  value='"+TTKCommon.getHtmlString(strFaxNbr)+"' maxlength=\"25\" class=\""+strClassMedium+"\""+strViewmode+">");
				
				out.print("</td>");
				//}
				if(strStatusDisYN.equals("Y"))
				{
					out.print("<td height=\"20\" class=\"labelRed\">");
				}//end of if(strStatusDisYN.equals("Y"))
				
				if(!strStatusDisYN.equals("Y"))
				{
					out.print("<td height=\"20\" class=\"formLabel\">");
				}//end of if(!strStatusDisYN.equals("Y"))
				
				out.print("Empanel. Status:");
				out.print("</td>");
				//added for Decoupling
		        if(!TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
		        {
					out.print("<td class=\"textLabel\">");
					//out.print("<input type=\"text\" name=\"preAuthHospitalVO.hospStatus\"  value='"+TTKCommon.getHtmlString(strHospStatus)+"' maxlength=\"25\" class=\"textBox textBoxMedium\""+strViewmode+">");
					out.print(strHospStatus);
					out.print("</td>");
					
					out.print("</tr>");
					out.print("<tr>");
					out.print("<td height=\"20\" class=\"formLabel\">Service Tax Regn. No.:</td>");
					out.print("<td class=\"textLabel\">");
					out.print("<input type=\"text\" name=\"preAuthHospitalVO.hospServiceTaxRegnNbr\"  value='"+TTKCommon.getHtmlString(strHospServiceTaxRegnNbr)+"' maxlength=\"15\"  readonly=\""+str +"\" class=\""+strClassMedium+"\""+strViewmode+">");
					out.print("</td>");
					out.print("</tr>");
				}	
					out.print("<tr>");
					out.print("<td height=\"20\" valign=\"top\" class=\"formLabel\">Remarks:</td>");
					out.print("<td colspan=\"3\" class=\"textLabel\">");
					out.print("<textarea name=\"preAuthHospitalVO.hospRemarks\" class=\""+strClassArea+"\""+strViewmode+">");
				out.print(TTKCommon.getHtmlString(strHospRemarks));
				out.print("</textarea>");
				out.print("</td>");
				out.print("</tr>");
			}//end of if(strActiveLink.equals("Claims"))
			out.print("</table>");
			out.print("</fieldset>");
			out.print("</div>");
		}//end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag
}//end of HospitalDetails