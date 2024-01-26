package com.ttk.common.tags.broker;

import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;

public class BrokerSummaryViews extends TagSupport{
	private static final Logger log=LogManager.getLogger(BrokerSummaryViews.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5857711697335259171L;
	
	@Override
	public int doStartTag() throws JspException {
		try{			
			JspWriter	out=pageContext.getOut();
			DynaActionForm frmBroDashBoard=(DynaActionForm)pageContext.getSession().getAttribute("frmBroDashBoard");
			HashMap<String,String> corporateSummaryMap=(HashMap<String,String>)pageContext.getSession().getAttribute("corporateSummaryMap");
		if(frmBroDashBoard !=null)	{
			String summaryView=frmBroDashBoard.getString("summaryView");
			if("ENR".equals(summaryView)){
			out.println("<table id='textTableLarge'>");
			out.println("<caption id='tableCaption'>ENROLMENT</caption>");
			out.println("<tr>");
			out.println("<th style='font-weight: bold;'>Discription</th>");
			out.println("<th style='font-weight: bold;'>No of employees</th>");
			out.println("<th style='font-weight: bold;'> No of dependants</th>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td style='text-align:left;'>Number of insured under the group</td>");
			out.println("<td>"+corporateSummaryMap.get("NUMBER_OF_EMPLOYEES")+"</td>");
			out.println("<td>"+corporateSummaryMap.get("NUMBER_OF_DEPENDENTS")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td style='text-align:left;'>Number of insured added by endorsement as on date from the policy inception date</td>");
			out.println("<td>"+corporateSummaryMap.get("NO_OF_EMPLOYEES_ADDED")+"</td>");
			out.println("<td>"+corporateSummaryMap.get("NO_OF_DEPENDANTS_ADDED")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td style='text-align:left;'>Number of insured deleted by endorsement as on date from the policy inception date</td>");
			out.println("<td>"+corporateSummaryMap.get("NO_OF_EMPLOYEES_DELETED")+"</td>");
			out.println("<td>"+corporateSummaryMap.get("NO_OF_DEPENDANTS_DELETED")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td></td>");
			out.println("<td></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td style='text-align:left;'>Premium at inception</td>");
			out.println("<td></td>");
			out.println("<td>"+corporateSummaryMap.get("NET_PREMIUM")+"</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td style='text-align:left;'>Total earned premium</td>");
			out.println("<td></td>");
			out.println("<td>"+corporateSummaryMap.get("EARNED_PREMIUM")+"</td>");
			out.println("</tr>");
			out.println("</table>");
			}else if("CLM".equals(summaryView)){
				out.println("<table id='textTableLarge' style='width: 600px;' >");
				out.println("<caption id='tableCaption'>CLAIMS</caption>");
				out.println("<tr>");
				out.println("<th>Claims Parameters</th>");
				out.println("<th colspan='2'>");
				out.println("<table style='width: 100%;margin: -10px;'>");
				out.println("<tr>");
				out.println("<td colspan='2'  style='font-weight: bold;text-align:center;'>Data</td>");				
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td style='font-weight: bold;text-align:center;'>No</td>");
				out.println("<td style='font-weight: bold;text-align:center;'>Amount</td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</th>");	
				out.println("<tr>");
				out.println("<td style='text-align:left;'>Claims Reported</td>");
				out.println("<td>"+corporateSummaryMap.get("NO_OF_CLAIMS_REPORTED")+"</td>");
				out.println("<td>"+corporateSummaryMap.get("REPORTED_AMOUNT")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td style='text-align:left;'>Claims Paid</td>");
				out.println("<td>"+corporateSummaryMap.get("NO_OF_CLAIMS_PAID")+"</td>");
				out.println("<td>"+corporateSummaryMap.get("PAID_AMOUNT")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td style='text-align:left;'>Claims Outstanding</td>");
				out.println("<td>"+corporateSummaryMap.get("NO_OF_CLAIMS_OUTSTANDING")+"</td>");
				out.println("<td>"+corporateSummaryMap.get("OUTSTANDING_AMOUNT")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td style='text-align:left;'>Claims Approved</td>");
				out.println("<td>"+corporateSummaryMap.get("NO_OF_CLAIMS_APPROVED")+"</td>");
				out.println("<td>"+corporateSummaryMap.get("APPROVED_AMOUNT")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td style='text-align:left;'>Claims Denied</td>");
				out.println("<td>"+corporateSummaryMap.get("NO_OF_CLAIMS_DENIED")+"</td>");
				out.println("<td>"+corporateSummaryMap.get("DENIED_AMOUNT")+"</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td style='text-align:left;'>Claims Shortfall</td>");
				out.println("<td>"+corporateSummaryMap.get("NO_OF_CLAIMS_SHORTFALL")+"</td>");
				out.println("<td>"+corporateSummaryMap.get("SHORTFALL_AMOUNT")+"</td>");
				out.println("</tr>");
				out.println("</table>");
				}else if("PAT".equals(summaryView)){
					out.println("<table id='textTableLarge' style='width: 600px;'>");
					out.println("<caption id='tableCaption'>PREAUTHORIZATION</caption>");
					out.println("<tr>");
					out.println("<th>Pre-Auth Parameters</th>");
					out.println("<th colspan='2'>");
					out.println("<table style='width: 100%;margin: -10px;'>");
					out.println("<tr>");
					out.println("<td colspan='2' style='font-weight: bold;text-align:center;'>Data</td>");				
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td style='font-weight: bold;text-align:center;'>No</td>");
					out.println("<td style='font-weight: bold;text-align:center;'>Amount</td>");
					out.println("</tr>");
					out.println("</table>");
					out.println("</th>");	
					out.println("<tr>");
					out.println("<td style='text-align:left;'>Pre-Auth Received</td>");
					out.println("<td>"+corporateSummaryMap.get("NO_OF_PREAUTH_RECEIVED")+"</td>");
					out.println("<td>"+corporateSummaryMap.get("REQUESTED_AMOUNT")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td style='text-align:left;'>Pre-Auth Approved</td>");
					out.println("<td>"+corporateSummaryMap.get("NO_OF_PREAUTH_APPROVED")+"</td>");
					out.println("<td>"+corporateSummaryMap.get("APPROVED_AMOUNT")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td style='text-align:left;'>Pre-Auth Denied</td>");
					out.println("<td>"+corporateSummaryMap.get("NO_OF_PREAUTH_DENIED")+"</td>");
					out.println("<td>"+corporateSummaryMap.get("DENIED_AMOUNT")+"</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td style='text-align:left;'>Pre-Auth Shortfall</td>");
					out.println("<td>"+corporateSummaryMap.get("NO_OF_PREAUTH_SHORTFALL")+"</td>");
					out.println("<td>"+corporateSummaryMap.get("SHORTFALL_AMOUNT")+"</td>");
					out.println("</tr>");
					out.println("</table>");
					}
		}//if(frmBroDashBoard!=null)	{
	}catch(Exception exception){
			log.error(exception);
   }
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;
	}

}
