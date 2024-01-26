/**

 * @ (#) IcsPcsHistory.java May 26, 2006
 * Project : TTK HealthCare Services
 * File : IcsPcsHistory.java
 * Author : Srikanth H M
 * Company : Span Systems Corporation
 * Date Created : May 26, 2006
 *
 * @author : Srikanth H M
 * Modified by :
 * Modified date :
 * Reason :
*/

package com.ttk.common.tags.preauth;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.Element;
import com.ttk.common.TTKCommon;

/**
 *  This class builds the IcsPcs Details in the Cashless History
 */

public class IcsPcsHistory extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    public int doStartTag() throws JspException{
        try
        {

           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           JspWriter out = pageContext.getOut();//Writer object to write the file
           Element icdpcsElement=null,provisionaldiagnosisElement=null;
           List  icdpcscodingdetailsList=null,provisionaldiagnosisList=null;
           DynaActionForm frmHistoryList=(DynaActionForm)request.getSession().getAttribute("frmHistoryList");
           String strProcedure="",strClass="";
           String strActiveTab=TTKCommon.getActiveLink(request);
           if(historyDoc != null)
           {


           	if(!((List)historyDoc.selectNodes("//icdpcscodingdetails/provisionaldiagnosis")).isEmpty())
           	{
           		 provisionaldiagnosisList= (List)historyDoc.selectNodes("//icdpcscodingdetails/provisionaldiagnosis");
           		 provisionaldiagnosisElement = (Element)provisionaldiagnosisList.get(0);
           		out.print("<fieldset><legend>Ailment Details</legend>");
           		 out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	           		out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
	           		if(frmHistoryList.get("PreAuthHistoryTypeID").equals("HPR")||frmHistoryList.get("PreAuthHistoryTypeID").equals("HMP"))
		            {
	           			out.print("Provisional Diagnosis:");
		            }//end of if(frmHistoryList.get("PreAuthHistoryTypeID").equals("HPR"))
	           		else if(frmHistoryList.get("PreAuthHistoryTypeID").equals("HCL"))
		            {
	           			out.print("Final Diagnosis:");
		            }//end of else if(frmHistoryList.get("PreAuthHistoryTypeID").equals("HCL"))
		            out.print("</td>");
		            out.print("<td width=\"78%\" class=\"textLabel\">");
		            	out.print(provisionaldiagnosisElement.valueOf("@description"));
		            out.print("</td>");
	            out.print("</table>");
	            out.print("</fieldset>");
	            //out.print("<br>");

           	}
           	if(!(strActiveTab.equals("Online Forms")))
           	{	
	           	if(!((List)historyDoc.selectNodes("//icdpcscodingdetails/record")).isEmpty())
	               {
	                   icdpcscodingdetailsList = (List)historyDoc.selectNodes("//icdpcscodingdetails/record");
	                   //double dbPackagerate=0,dbValidatedamount=0,dbApprovedamount=0;
	                   BigDecimal dbApprovedamount=new BigDecimal("0.00");
	                   out.print("<fieldset><legend>ICD / PCS Coding Details</legend>");
	                   if(icdpcscodingdetailsList!=null&& icdpcscodingdetailsList.size() > 0 )
	                   {
	
	
	                               out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                               out.print("<tr>");
	                                   out.print("<td width=\"26%\" valign=\"top\" class=\"gridHeader\">Ailment</td>");
	                                   		   out.print("<td width=\"20%\" valign=\"top\" class=\"gridHeader\">Hospitalization Type</td>");
	                                   		   out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">ICD Code</td>");
	                                           out.print("<td width=\"22%\" valign=\"top\" class=\"gridHeader\">Package/Procedures</td>");
	                                           //out.print("<td width=\"14%\" align=\"right\" valign=\"top\" class=\"gridHeader\">Validated Amt. (Rs)</td>");
	                                           out.print("<td width=\"21%\" align=\"right\" valign=\"top\" class=\"gridHeader\">Approved Amt. (Rs)</td>");
	                                    out.print("</tr>");
	
	                                   for(int icdcount=0;icdcount<icdpcscodingdetailsList.size();icdcount++)
	                                   {
	                                       icdpcsElement = (Element)icdpcscodingdetailsList.get(icdcount);
	                                       strClass=icdcount%2==0 ? "gridOddRow" : "gridEvenRow" ;
	
	
	
	                                       out.print("<tr class=\""+strClass+"\">");
	
	                                           out.print("<td>");
	                                               out.print(icdpcsElement.valueOf("@ailment")+"&nbsp;");
	                                           out.print("</td>");
	
	                                           out.print("<td>");
			                                  	   out.print(icdpcsElement.valueOf("@hospitalizationdescription")+"&nbsp;");
			                                  		if(icdpcsElement.valueOf("@hospitalizationtype").equals("REP"))
			                                  		{
			                                  			out.print(icdpcsElement.valueOf("@frequency")+"&nbsp;");
			                                  		}//end of if(icdpcsElement.valueOf("@hospitalizationtype").equals("REP"))
		                                  		out.print("</td>");
	
	                                           out.print("<td>");
	                                               out.print(icdpcsElement.valueOf("@icdcode")+"&nbsp;");
	                                           out.print("</td>");
	                                           out.print("<td>");
	                                               strProcedure=TTKCommon.replaceInString(icdpcsElement.valueOf("@pckageorprocedure"),",","<br>");
	                                               out.print(strProcedure+"&nbsp;");
	                                           out.print("</td>");
	//                                           out.print("<td align=\"right\">");
	//                                               if(!icdpcsElement.valueOf("@packagerate").equals(""))
	//                                               {
	//                                                   dbPackagerate+=TTKCommon.getDouble(icdpcsElement.valueOf("@packagerate"));
	//                                               }//end of if(!icdpcsElement.valueOf("packagerate").getText().equals(""))
	//                                               out.print(icdpcsElement.valueOf("@packagerate")+"&nbsp;");
	//                                           out.print("</td>");
	//
	//                                           out.print("<td align=\"right\">");
	//                                               if(!icdpcsElement.valueOf("@validatedamount").equals(""))
	//                                               {
	//                                                   dbValidatedamount+=TTKCommon.getDouble(icdpcsElement.valueOf("@validatedamount"));
	//                                               }//end of if(!icdpcsElement.valueOf("validatedamount").getText().equals(""))
	//                                               out.print(icdpcsElement.valueOf("@validatedamount")+"&nbsp;");
	//                                           out.print("</td>");
	
	                                           out.print("<td align=\"right\">");
	                                               if(!icdpcsElement.valueOf("@approvedamount").equals(""))
	                                               {
	                                                   dbApprovedamount=dbApprovedamount.add(TTKCommon.getBigDecimal(icdpcsElement.valueOf("@approvedamount")));
	                                               }//end of if(!icdpcsElement.valueOf("approvedamount").getText().equals(""))
	                                               out.print(icdpcsElement.valueOf("@approvedamount")+"&nbsp;");
	                                           out.print("</td>");
	                                       out.print("</tr>");
	                                   }//end of for(int icdcount=0;icdcount<icdpcscodingdetailsList.size();icdcount++)
	                           out.print("</table>");
	
	                           out.print("<table width=\"96%\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                               out.print("<tr>");
	                               out.print("<td width=\"19%\" height=\"25\" align=\"right\" class=\"textLabelBold\">&nbsp;</td>");
	                               out.print("<td width=\"10%\" align=\"right\" class=\"textLabelBold\">&nbsp;</td>");
	                               out.print("<td width=\"15%\" height=\"25\" align=\"right\" class=\"textLabelBold\">Total (Rs):</td>");
	//                               out.print("<td width=\"14%\" align=\"right\" class=\"textLabelBold\">");
	//                                   out.print(dbPackagerate);
	//                               out.print("</td>");
	//                               out.print("<td width=\"14%\" align=\"right\" class=\"textLabelBold\">");
	//                                   out.print(dbValidatedamount);
	//                               out.print("</td>");
	                               out.print("<td width=\"14%\" align=\"right\" class=\"textLabelBold\">");
	                                   out.print(dbApprovedamount);
	                               out.print("</td>");
	                               out.print("</tr>");
	                           out.print("</table>");
	
	
	                   }//end of if(icdpcscodingdetailsList!=null&& icdpcscodingdetailsList.size() > 0 )
	                   out.print("</fieldset>");
	               }//end of if(!((List)doc.selectNodes("//icdpcscodingdetails/record")).isEmpty())
           	}//end of if(!(strActiveTab.equals("Online Forms")))
           }//end of if(historyDoc != null)
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
}//end of class IcsPcsHistory