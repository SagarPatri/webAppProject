/**
 * @ (#) PreAuthShortFfalls.java June 22, 2015
 * Project : ProjectX
 * File : PreAuthShortFfalls.java
 * Author : Nagababu K
 * Company :Vidal
 * Date Created : June 22, 2015
 *
 * @author : Nagababu K
 * Modified by :
 * Modified date :
 * Reason :
*/
package com.ttk.common.tags.preauth;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.DynaActionForm;
//import org.apache.log4j.Logger;


import org.apache.struts.action.DynaActionForm;

public class Shortfalls extends TagSupport
{
	private String flow;
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( CitibankEnrolHistory.class );
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
	{
		ArrayList<String[]> shortfalls=null;
		try
		{
			if("PAT".equals(getFlow())){
				DynaActionForm frmPreAuthGeneral =(DynaActionForm)pageContext.getSession().getAttribute("frmPreAuthGeneral");
				shortfalls = (ArrayList<String[]>)frmPreAuthGeneral.get("shortfallList");
			}
			else if("CLM".equals(getFlow())){
				
				DynaActionForm frmClaimGeneral =(DynaActionForm)pageContext.getSession().getAttribute("frmClaimGeneral");
				shortfalls = (ArrayList<String[]>)frmClaimGeneral.get("shortfallList");
			}
	        JspWriter out = pageContext.getOut();//Writer object to write the file
	       String gridOddRow="'gridOddRow'";
	       String gridEvenRow="'gridEvenRow'";
	        if(shortfalls != null){
	        	if(shortfalls.size()>=1){
	                		out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:750px;height:auto;'");
	                			out.print("<tr>");
	                			out.print("<th align='center' class='gridHeader'>Shortfall No.</th>");
	                			out.print("<th align='center' class='gridHeader'>Status</th>");
	                			out.print("<th align='center' class='gridHeader'>Date / Time</th>");
	                			out.print("<th align='center' class='gridHeader'>Delete</th>");
	                			out.print("</tr>");
	        	//shortfalls.add(new String[]{preauthSeqId/claimSeqId,shortFallSeqId,shortFallNo,shortFallsType,shortFallsStatus,sendDate});
	                			int i=0;
	                			String imagePath="<img src='/ttk/images/DeleteIcon.gif' alt='Delete Shortfalls Details'width='16' height='16' border='0'>";
	                			for(String[]falls:shortfalls){
	                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                		out.print("<td align='center'><a href='#' accesskey='f' title='Edit Shortfalls Details'  onClick=\"javascript:doViewShortFalls('"+falls[0]+"','"+falls[1]+"');\">"+falls[2]+"</a></td>");
	                		out.print("<td align='center'>"+falls[4]+"</td>");
	                		out.print("<td align='center'>"+falls[5]+"</td>");
	                		out.print("<td align='center'><a href='#' onClick=\"javascript:deleteShortfallsDetails('"+falls[0]+"','"+falls[1]+"');\">"+imagePath+"</a></td>");
	                		out.print("</tr>");
	                		i++;
	                			}
	                		out.print("</table>");
	        	}
	        }//end of if(shortfalls != null)
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
}//end of PreAuthShortfalls class 
