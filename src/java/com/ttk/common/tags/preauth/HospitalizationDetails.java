/**1285
  * @ (#) HospitalizationDetails.java May 10, 2006
 * Project       : TTK HealthCare Services
 * File          : HospitalizationDetails.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : May 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.preauth; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;

import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.dto.claims.HospitalizationDetailVO;
//import com.ttk.dto.common.CacheObject;


public class HospitalizationDetails extends TagSupport  {

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( HospitalizationDetails.class );
    public int doStartTag() throws JspException
    {
       try
       {
            log.debug("Inside HospitalizationDetails TAG :");
            JspWriter out = pageContext.getOut();//Writer object to write the file
//            CacheObject cacheObject = null;
            HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
            String strViewmode=" Disabled ";
            String strViewmode1=" Disabled ";
            DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
            DynaActionForm frmClaimanDetail=(DynaActionForm)frmPreAuthGeneral.get("claimDetailVO");
			String strActiveLink=TTKCommon.getActiveLink(request);
            String strAuthNbr=(String)frmPreAuthGeneral.get("authNbr");
            String strPreAuthTypeDesc=(String)frmPreAuthGeneral.get("preauthTypeDesc");
            String strReceivedDate=(String)frmPreAuthGeneral.get("receivedDate");
            String strReceivedTime=(String)frmPreAuthGeneral.get("receivedTime");
            String strReceivedDay=(String)frmPreAuthGeneral.get("receivedDay");
            String strApprovedAmt=(String)frmPreAuthGeneral.get("approvedAmt");
            String strAdmissionDate=(String)frmPreAuthGeneral.get("clmAdmissionDate");
            String strAdmissionTime=(String)frmPreAuthGeneral.get("clmAdmissionTime");
            String strAdmissionDay=(String)frmPreAuthGeneral.get("admissionDay");
            String strDischargeDate=(String)frmPreAuthGeneral.get("dischargeDate");
            String strDischargeTime=(String)frmPreAuthGeneral.get("dischargeTime");
            String strDischargeDay=(String)frmPreAuthGeneral.get("dischargeDay");
            String strPrevHospClaimSeqID=(String)frmPreAuthGeneral.get("prevHospClaimSeqID");
            String strClaimTypeID=(String)frmClaimanDetail.get("claimTypeID");
            String strRequestTypeID=(String)frmClaimanDetail.get("requestTypeID");
            HashMap hmPrevHospList=(HashMap)frmPreAuthGeneral.get("hmPrevHospList");
	    //KOC1216C
            String strClaimSubTypeID=(String)frmClaimanDetail.get("claimSubTypeID");
            HospitalizationDetailVO hospDetailVO2=null;
	  
            if(TTKCommon.isAuthorized(request,"Edit"))
    		{
            	strViewmode="";
    		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
            if(!"Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request)))
            {
            	strViewmode1="";
            }

            out.print("<fieldset>");
            	out.print("<legend> Hospitalization Details</legend>");
            	out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
            	if(!strClaimTypeID.equals("CTM"))
            	{
                	out.print("<tr>");
                		out.print("<td width=\"22%\" height=\"20\" class=\"formLabel\">Authorization No.:</td>");
                		out.print("<td width=\"30%\" class=\"textLabelBold\">");
                		out.print(strAuthNbr);
                		out.print("&nbsp;&nbsp;&nbsp;");
                		if(strViewmode.equals("") && !"DTA".equals(strRequestTypeID))
                		{
							if(!(strActiveLink.equals("Support"))) //koc 11 koc11
        					{
                			out.print("<a href=\"#\"><img src=\"/ttk/images/EditIcon.gif\" onClick=\"javascript:selectAuthorization()\" alt=\"Select Authorization\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>&nbsp;&nbsp;<a href=\"#\"><img src=\"/ttk/images/DeleteIcon.gif\" onClick=\"javascript:clearAuthorization()\" alt=\"Clear Authorization\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a>");
							}
                		}
                		out.print("</td>");
                		out.print("<td width=\"19%\" class=\"formLabel\"> Cashless Type: </td>");
                		out.print("<td width=\"29%\" class=\"textLabel\">");
                		out.print(strPreAuthTypeDesc);
                		out.print("</td>");
                	out.print("</tr>");
                	out.print("<tr>");
                		out.print("<td height=\"20\" class=\"formLabel\"> Received Date / Time: </td>");
            			out.print("<td class=\"textLabel\">");
            			out.print(strReceivedDate+" "+strReceivedTime+" "+strReceivedDay);
            			out.print("</td>");
            			out.print("<td class=\"formLabel\">Approved Amt. (Rs): </td>");
            			out.print("<td class=\"textLabel\">");
            			out.print(strApprovedAmt);
            			out.print("</td>");
            		out.print("</tr>");
            	}
//KOC1216C
				if(strClaimSubTypeID.equals("OPD"))
            	{
            		out.print("<tr>");
            			out.print("<td width=\"22%\" class=\"formLabel\">Prev. Hospitalization: </td>");
            			out.print("<td width=\"30%\" class=\"textLabel\">");
						if(!(strActiveLink.equals("Support"))) //koc 11 koc11
    					{
            			out.print("<select name=\"prevHospClaimSeqID\" Id=\"PrevHospId\" CLASS=\"selectBox selectBoxMedium\""+strViewmode1+" disabled=\"true\" onChange=\"javascript:onPrevHospitalization()\">");
	        				out.print("<option value=\"\">Select from list</option>");
	        				if(hmPrevHospList!=null)
	        				{
	        					Set s=hmPrevHospList.keySet();
	        					for(Iterator i=s.iterator();i.hasNext();)
	                			{
	        						hospDetailVO2=(HospitalizationDetailVO)hmPrevHospList.get(i.next());
	        						out.print("<option value='"+hospDetailVO2.getPrevHospClaimSeqID()+"' "+TTKCommon.isSelected(strPrevHospClaimSeqID, hospDetailVO2.getPrevHospClaimSeqID().toString())+">"+hospDetailVO2.getPrevHospDesc()+"</option>");
	                			}//end of for(Iterator i=s.iterator();i.hasNext();)
	        				}
	        			out.print("</select>");
						}
						out.print("</td>");
						out.print("<td width=\"19%\" class=\"formLabel\">&nbsp;</td>");
					    out.print("<td class=\"textLabel\">&nbsp;</td>");
					out.print("</tr>");
				}
				else
            	{
            		out.print("<tr>");
            			out.print("<td width=\"22%\" class=\"formLabel\">Prev. Hospitalization: </td>");
            			out.print("<td width=\"30%\" class=\"textLabel\">");
						if(!(strActiveLink.equals("Support"))) //koc 11 koc11
    					{
            			out.print("<select name=\"prevHospClaimSeqID\" Id=\"PrevHospId\" CLASS=\"selectBox selectBoxMedium\""+strViewmode1+" onChange=\"javascript:onPrevHospitalization()\">");
	        				out.print("<option value=\"\">Select from list</option>");
	        				if(hmPrevHospList!=null)
	        				{
	        					Set s=hmPrevHospList.keySet();
	        					for(Iterator i=s.iterator();i.hasNext();)
	                			{
	        						hospDetailVO2=(HospitalizationDetailVO)hmPrevHospList.get(i.next());
	        						out.print("<option value='"+hospDetailVO2.getPrevHospClaimSeqID()+"' "+TTKCommon.isSelected(strPrevHospClaimSeqID, hospDetailVO2.getPrevHospClaimSeqID().toString())+">"+hospDetailVO2.getPrevHospDesc()+"</option>");
	                			}//end of for(Iterator i=s.iterator();i.hasNext();)
	        				}
	        			out.print("</select>");
						}
						out.print("</td>");
						out.print("<td width=\"19%\" class=\"formLabel\">&nbsp;</td>");
					    out.print("<td class=\"textLabel\">&nbsp;</td>");
					out.print("</tr>");
                         		}
					out.print("<tr>");
//out.print("<td class=\"formLabel\">Admission Date / Time: <span class=\"mandatorySymbol\">*</span></td>");
					 if(strClaimSubTypeID.equals("CSD")){
						 out.print("<td class=\"formLabel\"><div id=\"labelchange\">Treatment Commencement Date/Time:<span class=\"mandatorySymbol\">*</span><div></td>");
						}
						else		{
						 out.print("<td class=\"formLabel\"><div id=\"labelchange\">Admission Date/Time:<span class=\"mandatorySymbol\">*</span><div></td>");
						}					
						out.print("<td class=\"textLabel\">");
					out.print("<table cellpadding=\"1\" cellspacing=\"0\">");	
					out.print("<tr>");
					out.print("<td>");
					out.print("<input type=\"text\" name=\"clmAdmissionDate\"  value='"+TTKCommon.getHtmlString(strAdmissionDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode1+">");
                    if(TTKCommon.isAuthorized(request,"Edit")&&("".equals(strViewmode1)))
                    {
                    	out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPreAuthGeneral.clmAdmissionDate',document.frmPreAuthGeneral.clmAdmissionDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
                    out.print("&nbsp;");
					out.print("</td>");
					out.print("<td>");
					out.print("<input type=\"text\" name=\"clmAdmissionTime\"  value='"+TTKCommon.getHtmlString(strAdmissionTime)+"' maxlength=\"5\" class=\"textBox textTime\""+strViewmode1+">");
                    out.print("&nbsp;");
					out.print("</td>");
					out.print("<td>");
					out.print("<select name=\"admissionDay\" CLASS=\"selectBox\""+strViewmode1+">");
             		out.print("<option value=\"AM\" "+TTKCommon.isSelected(strAdmissionDay,"AM") +">AM</option>");
             		out.print("<option value=\"PM\" "+TTKCommon.isSelected(strAdmissionDay,"PM") +">PM</option>");
             		out.print("</select>");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>"); 
					out.print("</td>");
	//out.print("<td class=\"formLabel\"> Discharge Date / Time: <span class=\"mandatorySymbol\">*</span></td>");
					//Added as per KOC 1285 Change request 
					if(strClaimSubTypeID.equals("CSD"))			{
						out.print("<td class=\"formLabel\"><div id=\"labelchange1\">Treatment Completion Date/Time:<span class=\"mandatorySymbol\">*</span><div></td>");
					}
					else		{
						out.print("<td class=\"formLabel\"><div id=\"labelchange1\">Discharge Date/Time:<span class=\"mandatorySymbol\">*</span><div></td>");
					}			
					out.print("<td class=\"textLabel\">");
					out.print("<table cellpadding=\"1\" cellspacing=\"0\">");
					out.print("<tr>");
					out.print("<td>");
					out.print("<input type=\"text\" name=\"dischargeDate\"  value='"+TTKCommon.getHtmlString(strDischargeDate)+"' maxlength=\"10\" class=\"textBox textDate\""+strViewmode1+">");
                    if(TTKCommon.isAuthorized(request,"Edit")&&("".equals(strViewmode1)))
                    {
                     	out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmPreAuthGeneral.dischargeDate',document.frmPreAuthGeneral.dischargeDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
                    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
                     out.print("&nbsp;");
					out.print("</td>");
					out.print("<td>");
					out.print("<input type=\"text\" name=\"dischargeTime\"  value='"+TTKCommon.getHtmlString(strDischargeTime)+"' maxlength=\"5\" class=\"textBox textTime\""+strViewmode1+">");
                    out.print("&nbsp;");
					out.print("</td>");
					out.print("<td>");
					out.print("<select name=\"dischargeDay\" CLASS=\"selectBox\""+strViewmode1+">");
             		out.print("<option value=\"AM\" "+TTKCommon.isSelected(strDischargeDay,"AM") +">AM</option>");
             		out.print("<option value=\"PM\" "+TTKCommon.isSelected(strDischargeDay,"PM") +">PM</option>");
             		out.print("</select>");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
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
}//end of ClaimantDetails