package com.ttk.common.tags.administration;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.ttk.dto.administration.PremiumConfigurationVO;

public class MemberEligibiltyRulesAuditDetails extends TagSupport {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( PremiumConfigurationDetails.class );

	public int doStartTag() throws JspException{
		ArrayList<PremiumConfigurationVO> alAuditlogstableData=null;

		try
		{

			StringBuilder AuditlogsData=new StringBuilder();
			alAuditlogstableData=(ArrayList<PremiumConfigurationVO>)pageContext.getSession().getAttribute("auditlogstableData");

			JspWriter out = pageContext.getOut();//Writer Object to write the File
			String gridOddRow="'gridOddRow'";
			String gridEvenRow="'gridEvenRow'";

			AuditlogsData.append("<tr>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Version'>Version</th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Minimum Age'>Minimum Age</th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Maximum Age'>Maximum Age </th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='MaritalStatus'>MaritalStatus</th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Gender Applicable'>Gender Applicable</th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Applicable To Relation'>Applicable To Relation </th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Salary Band'>Salary Band</th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Gross Premium'>Gross Premium</th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Last Updated Date'>Last Updated Date</th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Remarks'>Remarks</th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Updated Remarks'>Updated Remarks</th>");
			AuditlogsData.append("<th align='center' class='gridHeader' title='Updated By '>Updated By</th>");
			AuditlogsData.append("</tr>");


			if(alAuditlogstableData != null){
				if(alAuditlogstableData.size()>=1){
					for(PremiumConfigurationVO premiumconfigobj: alAuditlogstableData)
					{

						AuditlogsData.append("<tr>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getReverse_num()+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getMinAge()+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getMaxAge()+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getMaritalStatus()+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getGender()+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getRelation()+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getSalaryBand()+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getGrossPremium()+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getUpdatedDate()+"</td>");
						AuditlogsData.append("<td align='center'><a title='"+premiumconfigobj.getRemarks()+"')>"+"Please place the cursor over here."+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getUpdatedRemarks()+"</td>");
						AuditlogsData.append("<td align='center'>"+premiumconfigobj.getUpdtby()+"</td>");
						AuditlogsData.append("</tr>");



					}//for(ActivityDetailsVO activityDetails:alAuditlogstableData)


				}	

				out.print("<table align='center' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
				out.print(AuditlogsData); 
				out.print("</table>"); 

			}//if(alActivityDetails != null)
			else
			{	        

				out.print("<table align='center' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
				AuditlogsData.append("<tr>");
				AuditlogsData.append("<td align='center'>NO DATA</td>");
				AuditlogsData.append("</tr>");
				out.print(AuditlogsData);   
				out.print("</table>"); 

			}

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
}
