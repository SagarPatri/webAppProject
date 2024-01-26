package com.ttk.common.tags.onlineforms.insuranceLogin;

/**
 * @ (#) InsGoblalEnrollmentDetails.java 14th July 2015
 * Project       : Dubai TTK
 * File          : InsGoblalEnrollmentDetails.java
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

import org.bouncycastle.ocsp.Req;

import com.ttk.common.TTKCommon;
import com.ttk.dto.onlineforms.insuranceLogin.EnrollMemberVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsGlobalViewVO;

//import org.apache.log4j.Logger;

public class InsGoblalEnrollmentDetails extends TagSupport
{

	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( InsGoblalEnrollmentDetails.class );
	public int doStartTag() throws JspException
	{
                try
                {
                	String CorOrRet 	=	(String)pageContext.getRequest().getAttribute("CorOrRet");
                	
                	InsGlobalViewVO insGlobalViewVO	=	(InsGlobalViewVO)pageContext.getSession().getAttribute("insGlobalViewVO");
                	EnrollMemberVO enrollMemberVO	=	null;
                	enrollMemberVO	=	insGlobalViewVO.getEnrollMemberVO();
				   JspWriter out = pageContext.getOut();//Writer object to write the jsp file
				   String gridOddRow="'gridOddRow'";
				   String gridEvenRow="'gridEvenRow'";
				   if(enrollMemberVO != null)
					{
						
					   if("CORP".equals(CorOrRet)){
			                out.print("<table border='1' align='center' cellpadding='0' cellspacing='0' class='table table-striped'  style='width:500px;height:auto;'");
					        out.print("<tr>");
					        out.print("<th style='border: 1px' align='center' class='gridHeader'>Enrolment Parameters</th>");
					        out.print("<th style='border: 1px' align='center' class='gridHeader'>No of Employees</th>");
					        out.print("<th style='border: 1px' align='center' class='gridHeader'>No of Dependents</th>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">No of Insured under the group</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getNumberOfEmployees()+"  </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getNumberOfDependents()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">No of Insured added by endorsement for the month</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+enrollMemberVO.getNoOfEmpCurMonAdded()+"  </td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+enrollMemberVO.getNoOfDependantsCurMonAdded()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">No of Insured deleted by endorsement for the month</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getNoOfEmpCurMonDeleted()+"  </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getNoOfDepndCurMonDeleted()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">No of Insured added by endorsement as on date from the policy inception date</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+enrollMemberVO.getNoOfEmployeesAdded()+" </td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+enrollMemberVO.getNoOfDependantsAdded()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">No of Insured deleted by endorsement as on date from the policy inception date</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getNoOfEmployeesDeleted()+" </td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getNoOfDependantsDeleted()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Total Premium at policy inception </td>");
					        out.print("<td align='center' class="+gridEvenRow+" colspan='2'> "+enrollMemberVO.getTtlPremiumAtPolicy()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Total Earned Premium</td>");
					        out.print("<td align='center' class="+gridEvenRow+" colspan='2'> "+enrollMemberVO.getTtlEarnedPremium()+" </td>");
					        out.print("</tr>");
					        
					        out.print("</table");
					   }else if("RETAIL".equals(CorOrRet)){
						   
						    out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='table table-striped'  style='width:500px;height:auto;'");
					        out.print("<tr style='border: 1px'>");
					        out.print("<th align='center' class='gridHeader'>No of Policies</th>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getNumberOfPolicies()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<th align='center' class='gridHeader'>No of Lives</th>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getNumberOfLives()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<th align='center' class='gridHeader'>Total Gross  Premium </th>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getTotalGrossPremium()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<th align='center' class='gridHeader'>Total Earned  Premium </th>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+enrollMemberVO.getTotalEarnedPremium()+" </td>");
					        out.print("</tr>");
					        
					        out.print("</table");
					   }
		
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
}//end of InsGoblalEnrollmentDetails class