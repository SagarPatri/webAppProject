package com.ttk.common.tags.onlineforms.insuranceLogin;

/**
 * @ (#) InsGoblalCallCenteretails.java 14th July 2015
 * Project       : Dubai TTK
 * File          : InsGoblalCallCenteretails.java
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

import com.ttk.dto.onlineforms.insuranceLogin.CallCenterDetailsVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsGlobalViewVO;

//import org.apache.log4j.Logger;

public class InsGoblalCallCenteretails extends TagSupport
{

	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( InsGoblalCallCenteretails.class );
	public int doStartTag() throws JspException
	{
                try
                {
                	
                	InsGlobalViewVO insGlobalViewVO	=	(InsGlobalViewVO)pageContext.getSession().getAttribute("insGlobalViewVO");
                	CallCenterDetailsVO callCenterDetailsVO	=	null;
                	callCenterDetailsVO	=	insGlobalViewVO.getCallCenterDetailsVO();
				   JspWriter out = pageContext.getOut();//Writer object to write the file
				   String gridOddRow="'gridOddRow'";
				   String gridEvenRow="'gridEvenRow'";
				   if(callCenterDetailsVO != null)
					{
						
			                out.print("<table border='1' align='center' cellpadding='0' cellspacing='0' class='table table-striped'  style='width:500px;height:auto;'");
					        out.print("<tr style='border: 1px'>");

					        out.print("<th style='border: 1px' align='center' class='gridEvenRow' nowrap>Call Center Parameters</th>");
					        out.print("<th style='text-align:center; border: 1px' colspan='2' class='gridEvenRow'>Data</th>");
					        out.print("</tr>");
					        out.print("<tr>");
					        out.print("<th align='center'>&nbsp;</th>");
					        out.print("<th align='center' class='gridHeader' nowrap>Month</th>");
					        out.print("<th align='center' class='gridHeader' nowrap>As on Date</th>");
					        out.print("</tr>");
					        
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">No Of calls received for the month</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+callCenterDetailsVO.getNoOfCallsCurMon()+"  </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+callCenterDetailsVO.getNoOfCallsUptodate()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">% abondonded calls</td>");
					        out.print("<td align='center' class="+gridOddRow+">   </td>");
					        out.print("<td align='center' class="+gridOddRow+">  </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Average call handling time</td>");
					        out.print("<td align='center' class="+gridEvenRow+">   </td>");
					        out.print("<td align='center' class="+gridEvenRow+">  </td>");
					        out.print("</tr>");
					        out.print("</table>"); 
		
					        out.print("<br>");
					        
			                out.print("<table border='1' align='center' cellpadding='0' cellspacing='0' class='table table-striped'  style='width:500px;height:auto;'");
					        out.print("<tr style='border: 1px'>");
					        out.print("<th style='border: 1px' align='center' class='gridEvenRow' nowrap>Type of calls</th>");
					        out.print("<th style='text-align:center; border: 1px' colspan='2' class='gridEvenRow'>Data</th>");
					        out.print("</tr>");
					        out.print("<tr>");
					        out.print("<th align='center'>&nbsp;</th>");
					        out.print("<th style='border: 1px' align='center' class='gridHeader' nowrap>Month</th>");
					        out.print("<th style='border: 1px' align='center' class='gridHeader' nowrap>As on Date</th>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">General</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+callCenterDetailsVO.getNoOfGenCallCurMon()+"  </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+callCenterDetailsVO.getNoOfEnrCallCurMon()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">Enrollment</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+callCenterDetailsVO.getNoOfClmCallCurMon()+"  </td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+callCenterDetailsVO.getNoOfPreCallCurMon()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Claims</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+callCenterDetailsVO.getNoOfGenCallUptodate()+"  </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+callCenterDetailsVO.getNoOfEnrCallUptodate()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">Pre-Auth</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+callCenterDetailsVO.getNoOfClmCallUptodate()+" </td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+callCenterDetailsVO.getNoOfPreCallUptodate()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Network</td>");
					        out.print("<td align='center' class="+gridEvenRow+">  </td>");
					        out.print("<td align='center' class="+gridEvenRow+">  </td>");
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
}//end of InsGoblalCallCenteretails class