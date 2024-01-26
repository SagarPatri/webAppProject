package com.ttk.common.tags.hospital;

import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
//import org.apache.log4j.Logger;
import com.ttk.dto.preauth.DrugDetailsVO;


public class ActivityDetails extends TagSupport
{
	private String flow;
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( OnlineDrugDetails.class );
	
	public int doStartTag() throws JspException{
		ArrayList<ActivityDetailsVO> activities=null;
	
		try
		{
			if("PAT".equalsIgnoreCase(getFlow()))
				activities=(ArrayList<ActivityDetailsVO>)pageContext.getSession().getAttribute("ProviderActivities");
							
	        	JspWriter out = pageContext.getOut();//Writer Object to write the File
	        	String gridOddRow="'gridOddRow'";
	        	String gridEvenRow="'gridEvenRow'";
	        	if(activities != null)
	        	{
	        		if(activities.size()>=1)
	        		{
	               		out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'>");
	            		out.print("<tr>");
		                			out.print("<th align='center' class='gridHeader' title='Act Code'>Act Code</th>");
		                		//	out.print("<th align='center' class='gridHeader' title='Mod'>Mod</th>");
		                			out.print("<th align='center' class='gridHeader' title='Unit Type'>Unit Type</th>");
		                			out.print("<th align='center' class='gridHeader' title='Qty'>Qty</th>");
		                			out.print("<th align='center' class='gridHeader' title='Apr Qty'>Apr Qty</th>");
		                			out.print("<th align='center' class='gridHeader' title='Start Date'>Start Date</th>");
		                			out.print("<th align='center' class='gridHeader' title='Grs Amt'>Grs Amt</th>");
		                			out.print("<th align='center' class='gridHeader' title='Disc Amt'>Disc Amt</th>");
		                			out.print("<th align='center' class='gridHeader' title='Disc Grs'>Disc Grs</th>"); 		
		                			out.print("<th align='center' class='gridHeader' title='Ptn Share'>Ptn Share</th>");
		                			out.print("<th align='center' class='gridHeader' title='Net Amt'>Net Amt</th>");
		                			out.print("<th align='center' class='gridHeader' title='Alw Amt'>Alw Amt</th>");
		                			out.print("<th align='center' class='gridHeader' title='DisAlw Amt'>DisAlw Amt</th>");
		                			out.print("<th align='center' class='gridHeader' title='Den Code'>Den Code</th>");
		                			out.print("<th align='center' class='gridHeader' title='Remarks'>Remarks</th>");
		                			out.print("<th align='center' class='gridHeader' title='Observ'>Observ</th>");
	            		out.print("</tr>");
	            		String addImage2="<img src='/ttk/images/rsz_observation_red_pencil.jpg' alt='Add Observ Details' width='16' height='16' border='0' align='absmiddle'>";
	            		String addImage="<img src='/ttk/images/EditIcon.gif' alt='Add Observ Details' width='16' height='16' border='0' align='absmiddle'>";
	                			int i=0;

	                for(ActivityDetailsVO ActivitiesDetailsVO:activities)
	                {
	                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                		out.print("<td align='center'title='"+ActivitiesDetailsVO.getActivityCodeDesc()+"'>"+ActivitiesDetailsVO.getActivityCode()+"</td>");
	                	//	out.print("<td align='center'>"+ActivitiesDetailsVO.getModifier()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getUnitType()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getQuantity()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getApprovedQuantity()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getStartDate()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getGrossAmount()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getDiscount()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getDiscountedGross()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getPatientShare()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getNetAmount()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getAllowedAmt()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getApprovedAmount()+"</td>");
	                		out.print("<td align='center' title='"+ActivitiesDetailsVO.getDenialDescription()+"'>"+ActivitiesDetailsVO.getDenialCode()+"</td>");
	                		out.print("<td align='center'>"+ActivitiesDetailsVO.getActivityRemarks()+"</td>");
	                		
	                		if("Y".equals(ActivitiesDetailsVO.getObsYN()))
	                			out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addProvider('"+ActivitiesDetailsVO.getActivityDtlSeqId()+"');\">"+addImage2+"</a></td>");
	                		else
	                			out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addProvider('"+ActivitiesDetailsVO.getActivityDtlSeqId()+"');\">"+addImage+"</a></td>");
	                		
	                		out.print("</tr>");
	                		i++;
	                }
	               out.print("</table>");
	        	}
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
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	
}
