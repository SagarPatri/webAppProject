package com.ttk.common.tags.onlineforms.insuranceLogin;

/**
 * @ (#) InsGoblalClaimDetails.java 14th July 2015
 * Project       : Dubai TTK
 * File          : InsGoblalClaimDetails.java
 * Author        : Kishor kumar S H
 * Company       : RCS Technologies
 * Date Created  : 14th July 2015
 *
 * @author       : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ttk.dto.onlineforms.insuranceLogin.ClaimsVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsGlobalViewVO;

//import org.apache.log4j.Logger;

public class InsGoblalClaimDetails extends TagSupport
{

	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( InsGoblalClaimDetails.class );
	public int doStartTag() throws JspException
	{
                try
                {
                	
                	InsGlobalViewVO insGlobalViewVO	=	(InsGlobalViewVO)pageContext.getSession().getAttribute("insGlobalViewVO");
                	ClaimsVO claimsVO	=	null;
                	claimsVO	=	insGlobalViewVO.getClaimsVO();
				   JspWriter out = pageContext.getOut();//Writer object to write the file
				   String gridOddRow="'gridOddRow'";
				   String gridEvenRow="'gridEvenRow'";
				   if(claimsVO != null)
					{
						
			                out.print("<table border='1' align='center' cellpadding='0' cellspacing='0' class='table table-striped'  style='width:342px;height:auto;'>");
					        out.print("<tr style='border: 1px'>");

					        out.print("<th style='border: 1px' align='center' class='gridEvenRow' nowrap>Claims Report as on Date</th>");
					        out.print("<th style='text-align:center; border: 1px' colspan='2' class='gridEvenRow'>Data</th>");
					        out.print("</tr>");
					        out.print("<tr>");
					        out.print("<th align='center'>&nbsp;</th>");
					        out.print("<th align='center' class='gridHeader' nowrap>No.</th>");
					        out.print("<th align='center' class='gridHeader' nowrap>Amount</th>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Claims Reported</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getNoOfClaimsReported()+"  </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getReportedAmount()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">Claims Paid</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+claimsVO.getNoOfClaimsPaid()+"  </td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+claimsVO.getPaidAmount()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Claims Outstanding</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getNoOfClaimsOutstanding()+"  </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getOutstandingAmount()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">Claims Approved</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+claimsVO.getNoOfClaimsApproved()+" </td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+claimsVO.getApprovedAmount()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Claims Denied</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getNoOfClaimsDenied()+" </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getDeniedAmount()+" </td>");
					        out.print("</tr>");

					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Claims Shortfall</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getNoOfClaimsShortfall()+" </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getShortfallAmount()+" </td>");
					        out.print("</tr>");

					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+" colspan='3'>&nbsp;</td>");
					        out.print("</tr>");
					        
					        out.print("<tr style='border: 1px'>");
					        out.print("<th style='border: 1px' align='center' class='gridEvenRow'>Claims Report for the Month</th>");
					        out.print("<th style='text-align:center; border: 1px' colspan='2' class='gridEvenRow'>Data</th>");
					        out.print("</tr>");
					        out.print("<tr>");
					        out.print("<th align='center'>&nbsp;</th>");
					        out.print("<th align='center' class='gridHeader' nowrap>No.</th>");
					        out.print("<th align='center' class='gridHeader' nowrap>Amount</th>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Claims Reported</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getNoOfClmCurMonRptd()+"  </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getCurMonReportedAmount()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">Claims Paid</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+claimsVO.getNoOfClmCurMonPaid()+"  </td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+claimsVO.getCurMonPaidAmount()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Claims Outstanding</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getNoOfClmCurMonOutstanding()+"  </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getCurMonOutstandingAmount()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">Claims Approved</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+claimsVO.getNoOfClmCurMonApproved()+" </td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+claimsVO.getCurMonApprovedAmount()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Claims Denied</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getNoOfClmCurMonDenied()+" </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getCurMonDeniedAmount()+" </td>");
					        out.print("</tr>");

					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Claims Shortfall</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getNoOfClmCurMonShortfall()+" </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+claimsVO.getCurMonShortfallAmount()+" </td>");
					        out.print("</tr>");
					        out.print("</table>");
					}//end of if(alFileUploads != null)
                }//end of try
                catch(Exception exp)
				{
				    exp.printStackTrace();
				}//end of catch block
            return SKIP_BODY;
	        }//end of doStartTag()
	public int doEndTag() throws JspException 
	{
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}//end of InsGoblalClaimDetails class