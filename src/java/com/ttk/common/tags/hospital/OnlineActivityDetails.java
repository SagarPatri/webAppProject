
package com.ttk.common.tags.hospital;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.ttk.dto.preauth.ActivityDetailsVO;

public class OnlineActivityDetails extends TagSupport
{
	private String flow;
	private String flag;
	private String preAuthNoYN;
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( OnlineActivityDetails.class );
	
	public int doStartTag() throws JspException{
	ArrayList<ActivityDetailsVO> alActivityDetails=null;
	
		try
		{
			if(!"PATCreate".equals(getFlag())){
			if("CLM".equalsIgnoreCase(getFlow()))
			  alActivityDetails=(ArrayList<ActivityDetailsVO>)pageContext.getSession().getAttribute("claimActivities");
			else if("PAT".equalsIgnoreCase(getFlow()))
			 alActivityDetails= (ArrayList<ActivityDetailsVO>)pageContext.getSession().getAttribute("preauthActivities");
				
	        JspWriter out = pageContext.getOut();//Writer Object to write the File
	       String gridOddRow="'gridOddRow'";
	       String gridEvenRow="'gridEvenRow'";
	        if(alActivityDetails != null){
	        	if(alActivityDetails.size()>=1){
	                		out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'>");
	                			out.print("<tr>");
	                			out.print("<th align='center' class='gridHeader' title='Serial Number'>S.No</th>");						// 1
	                			out.print("<th align='center' class='gridHeader' title='Activity Code'>Act Code</th>");					// 2
	                			out.print("<th align='center' class='gridHeader' title='Activity Description'>Activity Desc</th>");  	// 3
	                			out.print("<th align='center' class='gridHeader' title='Gross Price'>Gross Price</th>");				// 4 grossAmount
	                			out.print("<th align='center' class='gridHeader' title='Discount Amount'>Disc Amt</th>");				// 5 discountAmt
	                			out.print("<th align='center' class='gridHeader' title='Net Amount After Discount'>Net Amt</th>"); 		// 6 netAmt
	                			out.print("<th align='center' class='gridHeader' title='Quantity Claimed'>Qty Clmd</th>");				// 7 qty
	                			out.print("<th align='center' class='gridHeader' title='Total Amount'>Total Amt</th>");					// 8 netAmt
	                			out.print("<th align='center' class='gridHeader' title='Observations'>Observ</th>");
	                			out.print("<th align='center' class='gridHeader' title='Delete'>Delete</th>");
	                			out.print("</tr>");
	        	
	                			int i=0;
	                			String Type = "A";
	                			String delImage="<img src='/ttk/images/DeleteIcon.gif' alt='Delete Activity Details'width='12' height='12' border='0'>";
	                			String addImage="<img src='/ttk/images/EditIcon.gif' alt='Add Observ Details' width='16' height='16' border='0' align='absmiddle'>";
	                			String addImage2="<img src='/ttk/images/rsz_observation_red_pencil.jpg' alt='Add Observ Details' width='16' height='16' border='0' align='absmiddle'>";	
	                for(ActivityDetailsVO activityDetails:alActivityDetails){
	                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                		out.print("<td align='cenetr'> <font size='4'>"+(i+1)+"</font></td>");			// 1
	                		out.print("<td align='center'>"+activityDetails.getActivityCode()+"</td>");		// 2
	                		out.print("<td align='center'>"+activityDetails.getActivityCodeDesc()+"</td>");	// 3
	                		out.print("<td align='center'>"+activityDetails.getGrossAmount()+"</td>");		// 4 grossAmount
	                		out.print("<td align='center'>"+activityDetails.getDiscount()+"</td>");			// 5 discountAmt
	                		out.print("<td align='center'>"+(activityDetails.getNetAmount())+"</td>");		// 6 netAmt
	                		out.print("<td align='center'>"+(activityDetails.getQuantity())+"</td>");		// 7 qty
	                		out.print("<td align='center'>"+(activityDetails.getApprovedAmount())+"</td>");	// 8 netAmt
	                		if(("Y").equals(activityDetails.getObsYN()))
	                		{
	                			out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addProviderObserv('"+i+"','"+Type+"');\">"+addImage2+"</a></td>");
	                		}
	                		else
	                		{
	                			out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addProviderObserv('"+i+"','"+Type+"');\">"+addImage+"</a></td>");
	                		}
	                		out.print("<td align='center'><a href='#' accesskey='d'  onClick=\"javascript:deleteActivityDetails('"+i+"');\">"+delImage+"</a></td>");
	                		out.print("</tr>");
	                		i++;
	                			}//for(ActivityDetailsVO activityDetails:alActivityDetails)
	               out.print("</table>");
	               
	        	}//if(alActivityDetails.size()>=1)
	        }//if(alActivityDetails != null)
			}
else{
				
				if("CLM".equalsIgnoreCase(getFlow()))
					  alActivityDetails=(ArrayList<ActivityDetailsVO>)pageContext.getSession().getAttribute("claimActivities");
					else if("PAT".equalsIgnoreCase(getFlow()))
					 alActivityDetails= (ArrayList<ActivityDetailsVO>)pageContext.getSession().getAttribute("preauthActivities");
						
			        JspWriter out = pageContext.getOut();//Writer Object to write the File
			       String gridOddRow="'gridOddRow'";
			       String gridEvenRow="'gridEvenRow'";
			        if(alActivityDetails != null){
			        	if(alActivityDetails.size()>=1){
			                		out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'>");
			                		out.print("<tr>");
		                			out.print("<th align='center' class='gridHeader' colspan='12'>Activities</th>");
		                			out.print("</tr>");
			                			out.print("<tr>");
			                			out.print("<th align='center' class='gridHeader' title='Serial Number'>S.No</th>");
			                			out.print("<th align='center' class='gridHeader' title='Activity Code'>ID</th>");
			                			out.print("<th align='center' class='gridHeader' title='Internal Code'>Int Code</th>");
			                			out.print("<th align='center' class='gridHeader' title='Activity Description'>Activity Description</th>");
			                			out.print("<th align='center' class='gridHeader' title='Status'>Status</th>");
			                			out.print("<th align='center' class='gridHeader' title='Quantity'>Quantity</th>");
			                			out.print("<th align='center' class='gridHeader' title='Request Amount'>Req. Amnt</th>");
			                			out.print("<th align='center' class='gridHeader' title='Approved Maount'>Appr. Amnt</th>");
			                			out.print("<th align='center' class='gridHeader' title='Patient Share'>Pat. Share</th>");
			                			out.print("<th align='center' class='gridHeader' title='Duration'>Duration</th>");
			                			out.print("<th align='center' class='gridHeader' title='Denial'>Denial</th>");
			                			out.print("<th align='center' class='gridHeader' title='Comments'>Comments</th>");
			                			out.print("</tr>");
			        	
			                			int i=0;

			                for(ActivityDetailsVO activityDetails:alActivityDetails){
			                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
			                		out.print("<td align='cenetr'> <font size='2'>"+(i+1)+"</font></td>");
			                		out.print("<td align='center'>"+activityDetails.getActivityCode()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getInternalCode()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getActivityCodeDesc()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getActivityStatus()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getQuantity()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getProviderRequestedAmt()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getApprovedAmount()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getPatientShare()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getMedicationDays()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getDenialCode()+"</td>");
			                		out.print("<td align='center'>"+activityDetails.getActivityRemarks()+"</td>");
			                		out.print("</tr>");
			                		i++;
			                			}//for(ActivityDetailsVO activityDetails:alActivityDetails)
			               out.print("</table>");
			        	}//if(alActivityDetails.size()>=1)
			        }//if(alActivityDetails != null)
			        
	        	
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
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPreAuthNoYN() {
		return preAuthNoYN;
	}
	public void setPreAuthNoYN(String preAuthNoYN) {
		this.preAuthNoYN = preAuthNoYN;
	}
}//end of DiagnosisDetails class 
