/**
 * @ (#) MemberPolicyInfo.java Feb 8th, 2006
 * Project       : TTK HealthCare Services
 * File          : MemberPolicyInfo.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Feb 8th, 2006
 *
 * @author       : Krishna K H
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


public class MemberPolicyInfo extends TagSupport
{
	/**
     * Comment for <code>serialVersionUID</code>
     * changes done for cr koc 1103 and koc 1105 
     * bank related informatiom made as readonly for enroll module 
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( MemberPolicyInfo.class );
	//For sub link name
    private String strIndividualPolicy="Individual Policy";
    private String strIndPolicyasGroup="Ind. Policy as Group";
    private String strCorporatePolicy="Corporate Policy";
    private String strNonCorporatePolicy="Non-Corporate Policy";

	public int doStartTag() throws JspException
	{
	   try
	   {

		   	log.debug("Inside MemberPolicyInfo TAG :");
	    	JspWriter out = pageContext.getOut();
	    	String strActiveSubLink=TTKCommon.getActiveSubLink((HttpServletRequest)pageContext.getRequest());
	    	String strViewmode=" Disabled ";
	    	String disabled=" Disabled ";
            CacheObject cacheObject=null;
            String officeCode = "";
            DynaActionForm frmPolicyDetails=(DynaActionForm)pageContext.getSession().getAttribute("frmPolicyDetails");
    	    ArrayList alPolicyStatus = Cache.getCacheObject("policyStatus");
            DynaActionForm frmAddMember = (DynaActionForm)((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("frmAddMember");
            DynaActionForm frmPolicyList=(DynaActionForm)((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            String strAuthorityProductID=(String)frmAddMember.get("authorityProductId");
            String strCategoryTypeID = (String)frmAddMember.get("categoryTypeID");
//            String strPEDPresentYN = (String)frmAddMember.get("PEDPresentYN");
            String strInceptionDate =(String)frmAddMember.get("inceptionDate");
            String strCustomerCode = (String)frmAddMember.get("customerCode");
            String strExitDate = (String)frmAddMember.get("exitDate");
            String strEnrollmentID = (String)frmAddMember.get("enrollmentID");
            String strCustomerID = (String)frmAddMember.get("customerID");
            String strStatus = (String)frmAddMember.get("status");
            String strStatusDesc = (String)frmAddMember.get("statusDesc");
//            String strRenew_YN = (String)frmAddMember.get("renewYN");
            String strRenewCount = (String)frmAddMember.get("renewCount");
//            String strCertificateNbr = (String)frmAddMember.get("certificateNbr");
            String strCustomerEndoseNbr =(String)frmAddMember.get("custEndorseNbr");
            Long lngMemberSeqID=TTKCommon.getLong((String)frmAddMember.get("memberSeqID"));
            String strPolicyRemarks = (String)frmAddMember.get("memberRemarks");
            String strInsuredCode = (String)frmAddMember.get("insuredCode");
            String strSerialNumber=(String)frmAddMember.get("serialNumber");
            String strDiabetesCoverYN=(String)frmAddMember.get("diabetesCoverYN");
            String strHyperTensCoverYN=(String)frmAddMember.get("hyperTensCoverYN");
            
            String strGlobalNetMemberID=(String)TTKCommon.checkNull(frmAddMember.get("globalNetMemberID"));
            String strMemAddedDate=(String)TTKCommon.checkNull(frmAddMember.get("memAddedDate"));
            
            String strInsCategoryCode=(String)frmAddMember.get("insCategoryCode");
            String strMemberCancelRemarks = (String)frmAddMember.get("memberCancelRemarks");
           
            
            String policyStatusForMember = (String) pageContext.getSession().getAttribute("policyStatusForMember");
            String portedYN=(String) pageContext.getSession().getAttribute("portedYN");
            String productAuthority=(String) pageContext.getSession().getAttribute("productAuthority");
            if((String) pageContext.getSession().getAttribute("officeCode") ==null||(String) pageContext.getSession().getAttribute("officeCode")==""){
            	officeCode=(String)frmPolicyDetails.get("officeCode");
            }
            else{
            	officeCode=(String) pageContext.getSession().getAttribute("officeCode");
            }
            
           
            String dhpoMemberId = (String)frmAddMember.get("dhpoMemberId");
            String policySequence = (String)frmAddMember.get("policySequence");
            String prodPolicyAuthority = (String) pageContext.getSession().getAttribute("prodPolicyAuthority");
            
           // String productAuthority=(String)frmPolicyDetails.get("productAuthority");
            
            //String strFamilyNumber=(String)frmAddMember.get("familyNumber");
            //String strReqHyperTensCoverYN=(String) ((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("hyper");
            //String strReqDiabetesCoverYN=pageContext.getRequest().getParameter("diabetesCoverYN");

            if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))
        	{
        		strViewmode="";
        	}//end of if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))

	    	if(strActiveSubLink.equals(strIndPolicyasGroup)||strActiveSubLink.equals(strIndividualPolicy)||strActiveSubLink.equals(strCorporatePolicy))
	    	{
                out.print("<fieldset>");
                    out.print("<legend>Policy Information</legend>");
                    out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                        out.print("<tr>");
                            out.print("<td width=\"20%\" class=\"formLabel\">Enrollment Id: </td>");
                            out.print("<td width=\"32%\" class=\"formLabelBold\">"+strEnrollmentID+" </td>");
                            out.print("<td width=\"24%\" class=\"formLabel\">Customer Id: </td>");
                            out.print("<td width=\"24%\" class=\"formLabelBold\">"+strCustomerID+"</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                            out.print("<td class=\"formLabel\">Status: <span class=\"mandatorySymbol\">*</span></td>");
                            //in enrollment flow status is shown as label and in endorsement as select box
                            if(strSwitchType.equals("")||strSwitchType.equals("ENM"))
                            {
                                out.print("<td class=\"formLabelBold\">"+strStatusDesc+" </td>");
                                out.print("<input type=\"hidden\" name=\"status\" value=\""+strStatus+"\">");
                            }//end of if(strSwitchType.equals("")||strSwitchType.equals("ENM"))
                            else if(strSwitchType.equals("END"))
                            {
                                if(lngMemberSeqID==null)    //display status as label while adding member
                                {
                                    out.print("<td class=\"formLabelBold\">"+strStatusDesc+" </td>");
                                    out.print("<input type=\"hidden\" name=\"status\" value=\""+strStatus+"\">");
                                    out.print("</td>");
                                }//end of if(lngMemberSeqID==null)
                                else
                                {
                                	if(policyStatusForMember.equals("POE") || policyStatusForMember.equals("POD")){
                                		out.print("<td width=\"32%\" class=\"formLabelBold\">"+strStatusDesc+" </td>");
                                	}else{
                                    out.print("<td class=\"formLabel\">");
                                    out.print("<select name=\"status\" class=\"selectBox selectBoxMedium\" "+strViewmode+" onChange=\"javascript:onClarificationTypeIDChange();\" id=\"memberpolicystatusid\">");
                                    //out.print("<option value=\"\">Select from list</option>");
                                    if(alPolicyStatus!=null && alPolicyStatus.size()>0)
                                    {
                                        for(int i=0;i<alPolicyStatus.size();i++)
                                        {
                                            cacheObject = (CacheObject)alPolicyStatus.get(i);
                                            out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strStatus,cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
                                        }//end of for(int i=0;i<alCategoryTypeID.size();i++)
                                    }//end of  if(alCategoryTypeID!=null && alCategoryTypeID.size()>0)
                                    out.print("</select></td>");
                                    out.print("</td>");
                                }
                                }//end of else
                            }//end of else if(strSwitchType.equals("END"))
                            out.print("<td nowrap class=\"formLabel\">Customer Endorsement No.: </td>");
                            out.print("<td class=\"formLabelBold\">"+strCustomerEndoseNbr+"</td>");
                        	out.print("</tr>");
                        	
                        	out.print("<tr>");
//                            out.print("<td class=\"formLabel\">GlobalNet Member ID: </td>");
//                            out.print("<td>");
//                            out.print("<input type=\"text\" name=\"globalNetMemberID\"  value='"+strGlobalNetMemberID+"'   class=\"textBox textBoxLarge\""+strViewmode+">");
//                            out.print("</td>");

                          
                            if("Y".equals(portedYN))
                            {  
                            	  out.print("<td class=\"formLabel\">Member First Inception Date:<span class=\"mandatorySymbol\">*</span> </td>");
                                  out.print("<td>");	
                                  out.print("<input type=\"text\" name=\"memAddedDate\"  value='"+TTKCommon.getHtmlString(strMemAddedDate)+"' maxlength=\"10\"  class=\"textBox textDate\">");
                            	  out.print("<a name=\"CalendarObjectdobDate\" id=\"CalendarObjectdobDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdobDate','frmAddMember.memAddedDate',document.frmAddMember.memAddedDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"dobDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                            	  out.print("</td>");
                            }
                            else{
                            	 out.print("<td class=\"formLabel\">Member First Inception Date:</td>");
                                 out.print("<td>");	
                            	 out.print("<input type=\"text\" name=\"memAddedDate\"  value='"+TTKCommon.getHtmlString(strMemAddedDate)+"' maxlength=\"10\" readOnly  class=\"textBox textDate textBoxDisabled\">");
                            	 out.print("</td>");
                            } 
                            out.print("</tr>");
                        	
                            out.print("<tr>");
                            out.print("<td width=\"20%\" class=\"formLabel\">Member Id: ");
							if("DHA".equals(prodPolicyAuthority)){
								out.print("<span class=\"mandatorySymbol\">*</span>");
							}
							out.print("</td>");
                          
//                            out.print("<td width=\"32%\" class=\"formLabelBold\">"+dhpoMemberId+" </td>");
                            
                            
                            out.print("<td>");
                            out.print("<input type=\"text\" name=\"dhpoMemberId\" maxlength=\"30\" value='"+dhpoMemberId+"'  disbale=\"true\" class=\"textBox textBoxLarge\""+strViewmode+">");
                            out.print("</td>");
                            
                            
                            
                            out.print("<td width=\"24%\" class=\"formLabel\">Policy Sequence:");
                            if("DHA".equals(prodPolicyAuthority)){
     							out.print("<span class=\"mandatorySymbol\">*</span>");
     							}
     						out.print("</td>");	
                            	
                            	
                            	
//                            out.print("<td width=\"24%\" class=\"formLabelBold\">"+policySequence+"</td>");
                            
                            out.print("<td>");
                            out.print("<input type=\"text\" name=\"policySequence\"  onkeyup=\"isNumeric(this);\" maxlength=\"3\" value='"+policySequence+"'   class=\"textBox textBoxLarge\""+strViewmode+">");
                            out.print("</td>");
                            
                            
                            out.print("</tr>");
                            
                            
                            
                        	out.print("<tr>");
                            out.print("<td class=\"formLabel\">Effective From:<span class=\"mandatorySymbol\">*</span></td>");
                            out.print("<td class=\"formLabelBold\">");
                            if(strRenewCount!=null && !strRenewCount.equals("0"))
                            {
                                out.print(strInceptionDate);
                                out.print("<input type=\"hidden\" name=\"inceptionDate\"  value='"+strInceptionDate+"'>");
                            }//end of if(strRenewCount==null || !strRenewCount.equals("0"))
                            else
                            {
                                /*out.print("<input type=\"text\" name=\"inceptionDate\" onblur=\"javascript:calcActiveDays(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'reInsBrkMargin');calculatePerAED(this,'tapMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');\" value='"+TTKCommon.getHtmlString(strInceptionDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");*/
                            	out.print("<input type=\"text\" name=\"inceptionDate\" onblur=\"javascript:calcActiveDays(this);\" value='"+TTKCommon.getHtmlString(strInceptionDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
                            	if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))
                                {
                                    out.print("<a name=\"CalendarObjectdobDate\" id=\"CalendarObjectdobDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdobDate','frmAddMember.inceptionDate',document.frmAddMember.inceptionDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"dobDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                                }//if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))
                            }
                            out.print("&nbsp;</td>");
                            out.print("<td class=\"formLabel\">Date of Exit: </td>");
                            out.print("<td class=\"formLabel\">");
                            	//in enrollment flow Exit Date will be readOnly
								                            if(strSwitchType.equals("")||strSwitchType.equals("ENM"))
								                            {
								                                out.print("<input type=\"text\" name=\"exitDate\" id=\"exitDate\" value='"+TTKCommon.getHtmlString(strExitDate)+"' maxlength=\"10\" readOnly class=\"textBox textDate\""+strViewmode+">");
								                                out.print("<span id=\"calexitdate\" style=\"display:none\"></span>"); //do not display calendar icon in enrollment module
								                            }//end of if(strSwitchType.equals("")||strSwitchType.equals("ENM"))
								                            //in endorsement flow it is made conditionally readonly based on the status value
																else if(strSwitchType.equals("END"))
																{
																	if(strStatus.equals("POA")||strStatus.equals("POC"))
																	{
																		out.print("<input type=\"text\" name=\"exitDate\" id=\"exitDate\"  value='"+TTKCommon.getHtmlString(strExitDate)+"' maxlength=\"10\" readOnly class=\"textBox textDate\""+strViewmode+">");
																		out.print("<span id=\"calexitdate\" style=\"display:none\"><a name=\"CalendarObjectdobDate\" id=\"CalendarObjectdobDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdobDate','frmAddMember.exitDate',document.frmAddMember.exitDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"dobDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a></span>");
																	}//end of if(strStatus.equals("POA")||strStatus.equals("POC"))
																	else
																	{
																		out.print("<input type=\"text\" name=\"exitDate\" id=\"exitDate\" value='"+TTKCommon.getHtmlString(strExitDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
																		out.print("<span id=\"calexitdate\" style=\"display:''\">");
																		if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))
																		{
																			out.print("<a name=\"CalendarObjectdobDate\" id=\"CalendarObjectdobDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdobDate','frmAddMember.exitDate',document.frmAddMember.exitDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"dobDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
																		}//if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))
																		out.print("</span>");
																	}//end of else
								}//end of else if(strSwitchType.equals("END"))
                            out.print("</td></tr>");
                         
                            
                            
                            out.print("<tr>");
                            if("DHA".equals(productAuthority) || "MOH".equals(productAuthority))
                            {
                            out.print("<td class=\"formLabel\">Authority Product ID of the Member: </td>");
                            }
                            else if("HAAD".equals(productAuthority))
                            {
                            	  out.print("<td class=\"formLabel\">Authority Product Reference No of the Member: </td>");
                                  
                            }
                            out.print("<td class=\"formLabelBold\">");
                            
                            out.print(TTKCommon.checkNull(strAuthorityProductID));
                            out.print("&nbsp;</td>");
                            out.print("</tr>");
                      
                            
                            /*      if(strActiveSubLink.equals(strCorporatePolicy))
                            {
                            	out.print("<td class=\"formLabel\">Insurance. Cust. Code/Bagi No.: </td>");//cr change
                            	out.print("<td>");
                                out.print("<input type=\"text\" name=\"insuredCode\"  value='"+TTKCommon.getHtmlString(strInsuredCode)+"' maxlength=\"15\"  class=\"textBox textBoxMedium\""+strViewmode+">");
                                out.print("</td>");
                            }//end of if(strActiveSubLink.equals(strCorporatePolicy))
                            else
                            {
                            	out.print("<td class=\"formLabel\">Insurance. Cust. Code: </td>");
                            	out.print("<td>");
                                out.print("<input type=\"text\" name=\"insuredCode\"  value='"+TTKCommon.getHtmlString(strInsuredCode)+"' maxlength=\"15\"  class=\"textBox textBoxMedium\""+strViewmode+">");
                                out.print("</td>");
                            }//end of else
                             // out.print("<td class=\"formLabelBold\">");
                            //out.print(TTKCommon.checkNull(strInsuredCode));
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("</td></tr>");
                            out.print("<tr>");
                            out.print("<td nowrap class=\"formLabel\">Serial No.: </td>");
                            out.print("<td>");
                            out.print("<input type=\"text\" name=\"serialNumber\"  value='"+TTKCommon.getHtmlString(strSerialNumber)+"' maxlength=\"15\"  class=\"textBox textBoxMedium\""+strViewmode+">");
                            out.print("</td>");
                            
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td nowrap class=\"formLabel\">HyperTension Cover:</td>");
                            out.print("<td>");
                            	out.print("<input type=\"checkbox\" name=\"hyperTensCoverYN\"  "+TTKCommon.isChecked(strHyperTensCoverYN)+strViewmode+">");
                            out.print("</td>");
                            out.print("<td nowrap class=\"formLabel\" width=\"20%\">Diabetes Cover:</td>");
                            out.print("<td width=\"32%\">");
                            	out.print("<input type=\"checkbox\" name=\"diabetesCoverYN\"  value=\"\" "+TTKCommon.isChecked(strDiabetesCoverYN)+strViewmode+">");
                            //out.print("<input type=\"checkbox\" name=\"diabetesCoverYN\" value=\"Y\" "+TTKCommon.isChecked(strDiabetesCoverYN) +strViewmode+">" );
                            out.print("</td></tr>");*/
                            
                            
                            
                            if("INS022".equals(officeCode) && "DHA".equals(productAuthority)){
                            	
                            	  out.print("<tr>");
                            	  out.print("<td class=\"formLabel\">"); 
                                  out.print("Insurance Member Category Code"); 
                                  out.print("");
                                  out.print(":");
                                  out.print("<span class=\"mandatorySymbol\">*</span>");
                                  out.print("</td>");
                                  out.print("<td>");
                                  out.print("<input type=\"text\" name=\"insCategoryCode\"  value='"+TTKCommon.getHtmlString(strInsCategoryCode)+"' maxlength=\"15\"  class=\"textBox textBoxMedium\""+strViewmode+">");
                                  out.print("</td>");
                                  out.print("</tr>");
                            }
                            else{
                            	
                            	  out.print("<tr>");
                                  out.print("<td nowrap class=\"formLabel\">Insurance Member Category Code: </td>");
                                  out.print("<td>");
                                  out.print("<input type=\"text\" name=\"insCategoryCode\"  value='"+TTKCommon.getHtmlString(strInsCategoryCode)+"' maxlength=\"15\"  class=\"textBox textBoxMedium\""+strViewmode+">");
                                  out.print("</td>");
                                  out.print("</tr>");
                            }
                          
                            
                            
                            out.print("<tr>");
                            out.print("<td class=\"formLabel\">");
                            out.print("Policy Remarks: ");
                            out.print("</td>");

		 		 	 out.print("<td colspan=\"3\" class=\"textLabel\">");
		 		 	 	out.print("<textarea name=\"memberRemarks\" class=\"textBox textAreaLong\""+strViewmode+">");
		 		 	 		out.print(TTKCommon.getHtmlString(strPolicyRemarks));
		 		 	 	out.print("</textarea>");
		 		 	 out.print("</td>");
		 		 	 out.print("</tr>");
		 		 	 
		 		 	 
		 		 	 
		 		 	 if(strSwitchType.equals("END")&&(strMemberCancelRemarks !=""))
                     { 
		 		 	 out.print("<tr>");
                     out.print("<td class=\"formLabel\">");
                     out.print("Member Cancellation Remarks: ");
                     out.print("</td>");

	 		 	 out.print("<td colspan=\"3\" class=\"textLabel\">");
	 		 	 	out.print("<textarea name=\"memberCancelRemarks\" readOnly=\"true\" class=\"textBox textAreaLong\""+strViewmode+">");
	 		 	 		out.print(TTKCommon.getHtmlString(strMemberCancelRemarks));
	 		 	 	out.print("</textarea>");
	 		 	 out.print("</td>");
	 		 	 out.print("</tr>");
		 		 	 
                     }	 
		 		 	 
		 		 	 
		 		 	 
		 		 	 
		 		 	 
                    out.print("</table>");
                out.print("</fieldset>");
	    	}//end of if(strActiveSubLink.equals(strIndPolicyasGroup)||strActiveSubLink.equals(strIndividualPolicy))
	    	if(strActiveSubLink.equals(strNonCorporatePolicy))
	    	{
                out.print("<fieldset>");
                out.print("<legend>Policy Information</legend>");
                out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                    out.print("<tr>");
                        out.print("<td width=\"20%\" class=\"formLabel\">Enrollment Id: </td>");
                        out.print("<td width=\"32%\" class=\"formLabelBold\">"+strEnrollmentID+" </td>");
                        out.print("<td width=\"24%\" class=\"formLabel\">Customer Id: </td>");
                        out.print("<td width=\"24%\" class=\"formLabelBold\">"+strCustomerID+"</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                        out.print("<td class=\"formLabel\">Customer Code: <span class=\"mandatorySymbol\">*</span></td>");
                        out.print("<td class=\"formLabelBold\">");
                        out.print("<input type=\"text\" name=\"customerCode\"  value='"+TTKCommon.getHtmlString(strCustomerCode)+"' maxlength=\"60\" class=\"textBox textBoxMedium\""+strViewmode+">");
                        out.print("</td>");
                        out.print("<td class=\"formLabel\">&nbsp;</td>");
                        out.print("<td>&nbsp;</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                        out.print("<td class=\"formLabel\">Status: </td>");
//                      in enrollment flow status is shown as label and in endorsement as select box
                        if(strSwitchType.equals("")||strSwitchType.equals("ENM"))
                        {
                            out.print("<td class=\"formLabelBold\">"+strStatusDesc+" </td>");
                            out.print("<input type=\"hidden\" name=\"status\" value=\""+strStatus+"\">");
                            out.print("</td>");
                        }//end of if(strSwitchType.equals("")||strSwitchType.equals("ENM"))
                        else if(strSwitchType.equals("END"))
                        {
                            if(lngMemberSeqID==null) //display status as label while adding member
                            {
                                out.print("<td class=\"formLabelBold\">"+strStatusDesc+" </td>");
                                out.print("<input type=\"hidden\" name=\"status\" value=\""+strStatus+"\">");
                            }//end of if (lngMemberSeqID==null)
                            else
                            {
                                out.print("<td class=\"formLabel\">");
                                out.print("<select name=\"status\" class=\"selectBox selectBoxMedium\" "+strViewmode+" onChange=\"javascript:onClarificationTypeIDChange();\">");
                                //out.print("<option value=\"\">Select from list</option>");
                                if(alPolicyStatus!=null && alPolicyStatus.size()>0)
                                {
                                    for(int i=0;i<alPolicyStatus.size();i++)
                                    {
                                        cacheObject = (CacheObject)alPolicyStatus.get(i);
                                        out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strStatus,cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
                                    }//end of for(int i=0;i<alCategoryTypeID.size();i++)
                                }//end of  if(alCategoryTypeID!=null && alCategoryTypeID.size()>0)
                                out.print("</select></td>");
                                out.print("</td>");
                            }//end of else
                        }//end of else if(strSwitchType.equals("END"))
                        out.print("<td nowrap class=\"formLabel\">Customer Endorsement No.: </td>");
                        out.print("<td class=\"formLabelBold\">"+strCustomerEndoseNbr+"</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    	out.print("<td class=\"formLabel\">Category: </td>");
                    	out.print("<td class=\"formLabelBold\">");
                    	out.print(TTKCommon.checkNull(strCategoryTypeID));
                    	out.print("&nbsp;</td>");
                    	out.print("<td class=\"formLabel\">Insurance Cust. Code: </td>");
                        out.print("<td>");
                        out.print("<input type=\"text\" name=\"insuredCode\"  value='"+TTKCommon.getHtmlString(strInsuredCode)+"' maxlength=\"15\"  class=\"textBox textBoxMedium\""+strViewmode+">");
                    	out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                        out.print("<td class=\"formLabel\">Date of Inception: <span class=\"mandatorySymbol\">*</span></td>");
                        out.print("<td class=\"formLabel\">");
                        if(strRenewCount!=null && !strRenewCount.equals("0"))
                        {
                            out.print(strInceptionDate);
                            out.print("<input type=\"hidden\" name=\"inceptionDate\"  value='"+strInceptionDate+"'>");
                        }//end of if(strRenewCount==null || !strRenewCount.equals("0"))
                        else
                        {
                            out.print("<input type=\"text\" name=\"inceptionDate\" onblur=\"javascript:calcActiveDays(this);\" value='"+TTKCommon.getHtmlString(strInceptionDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
                            if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))
                            {
                                out.print("<a name=\"CalendarObjectdobDate\" id=\"CalendarObjectdobDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdobDate','frmAddMember.inceptionDate',document.frmAddMember.inceptionDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"dobDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                            }//if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))
                        }
                        out.print("&nbsp;</td>");
                        out.print("<td class=\"formLabel\">Date of Exit: <span class=\"mandatorySymbol\">*</span></td>");
                        out.print("<td class=\"formLabel\">");
                        //in enrollment flow Exit Date will be readOnly
						                        if(strSwitchType.equals("")||strSwitchType.equals("ENM"))
						                        {
						                            out.print("<input type=\"text\" name=\"exitDate\"  value='"+TTKCommon.getHtmlString(strExitDate)+"' maxlength=\"10\" readOnly class=\"textBox textDate\""+strViewmode+">");
						                            out.print("<span id=\"calexitdate\" style=\"display:none\"></span>");	//do not display calendar icon in enrollment module
						                        }//end of if(strSwitchType.equals("")||strSwitchType.equals("ENM"))
						                        //in endorsement flow it is made conditionally readonly based on the status value
						                        else if(strSwitchType.equals("END"))
						                        {
						                            if(strStatus.equals("POA")||strStatus.equals("POC"))
						                            {
						                                out.print("<input type=\"text\" name=\"exitDate\"  value='"+TTKCommon.getHtmlString(strExitDate)+"' maxlength=\"10\" readOnly class=\"textBox textDate\""+strViewmode+">");
						                                out.print("<span id=\"calexitdate\" style=\"display:none\"><a name=\"CalendarObjectdobDate\" id=\"CalendarObjectdobDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdobDate','frmAddMember.exitDate',document.frmAddMember.exitDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"dobDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a></span>");
						                            }//end of if(strStatus.equals("POA")||strStatus.equals("POC"))
						                            else
						                            {
						                                out.print("<input type=\"text\" name=\"exitDate\"  value='"+TTKCommon.getHtmlString(strExitDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode+">");
						                                out.print("<span id=\"calexitdate\" style=\"display:''\">");
						                                if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))
						                                {
						                                    out.print("<a name=\"CalendarObjectdobDate\" id=\"CalendarObjectdobDate\" href=\"#\" onClick=\"javascript:show_calendar('CalendarObjectdobDate','frmAddMember.exitDate',document.frmAddMember.exitDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"/ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"dobDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
						                                }//if(TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))
						                                out.print("</span>");
						                            }//end of else
                        }//end of else if(strSwitchType.equals("END"))
                        out.print("</td></tr>");
                        
                        out.print("<tr>");
                        out.print("<td nowrap class=\"formLabel\">HyperTension Cover:</td>");
                        out.print("<td>");
                        	out.print("<input type=\"checkbox\" name=\"hyperTensCoverYN\"  "+TTKCommon.isChecked(strHyperTensCoverYN)+strViewmode+">");
                        out.print("</td>");
                        out.print("<td nowrap class=\"formLabel\" width=\"20%\">Diabetes Cover:</td>");
                        out.print("<td width=\"32%\">");
                        	out.print("<input type=\"checkbox\" name=\"diabetesCoverYN\"  value=\"\" "+TTKCommon.isChecked(strDiabetesCoverYN)+strViewmode+">");
                        //out.print("<input type=\"checkbox\" name=\"diabetesCoverYN\" value=\"Y\" "+TTKCommon.isChecked(strDiabetesCoverYN) +strViewmode+">" );
                        out.print("</td></tr>");
                        out.print("<tr>");
                        out.print("<td nowrap class=\"formLabel\">Serial No.: </td>");
                        out.print("<td>");
                        out.print("<input type=\"text\" name=\"serialNumber\"  value='"+TTKCommon.getHtmlString(strSerialNumber)+"' maxlength=\"15\"  class=\"textBox textBoxMedium\""+strViewmode+">");
                    out.print("</td>");
                    out.print("<td nowrap class=\"formLabel\" width=\"20%\">Diabetes Cover:</td>");
                    out.print("<td width=\"32%\">");
                    out.print("<input type=\"checkbox\" name=\"diabetesCoverYN\"  value=\"Y\" "+TTKCommon.isChecked(strDiabetesCoverYN) +strViewmode+">" );
                    out.print("</td></tr>");
                    out.print("<tr>");
                    out.print("<td nowrap class=\"formLabel\">HyperTension Cover:</td>");
                    out.print("<td>");
                    out.print("<input type=\"checkbox\" name=\"hyperTensCoverYN\"  value=\"Y\" "+TTKCommon.isChecked(strHyperTensCoverYN)+strViewmode+">");
                    out.print("</td></tr>");
                        out.print("<tr>");
                        out.print("<td class=\"formLabel\">");
		    	 		out.print("Policy Remarks: ");
		    	 	    out.print("</td>");
		 		 	 out.print("<td colspan=\"3\" class=\"textLabel\">");
		 		 	 	out.print("<textarea name=\"memberRemarks\" class=\"textBox textAreaLong\""+strViewmode+">");
		 		 	 		out.print(TTKCommon.getHtmlString(strPolicyRemarks));
		 		 	 	out.print("</textarea>");
		 		 	 out.print("</td>");
		 		 	 out.print("</tr>");
                    out.print("</table>");
                out.print("</fieldset>");
	    	}//end of if(strActiveSubLink.equals(strCorporatePolicy))
	   }//end of try
	   catch(Exception exp)
       {
           exp.printStackTrace();
       }//end of catch block
       return SKIP_BODY;
	}//end of doStartTag
}//end of MemberPolicyInfo
