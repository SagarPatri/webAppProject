

/**
 * @ (#) HistoryOfTariffUpdates.java July 18, 2015
 * Project : ProjectX
 * File : HistoryOfTariffUpdates.java
 * Author : Kishor kumar  S H
 * Company :RCS Technologies
 * Date Created : 02 Dec 2016
 *
 * @author : Kishor kumar  S H
 * Modified by :
 * Modified date :
 * Reason :
*/
package com.ttk.common.tags.hospital;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.ttk.dto.empanelment.HaadTariffFactorHistoryVO;
//import org.apache.log4j.Logger;

public class HistoryOfTariffUpdates extends TagSupport
{
	private String flow;
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( HistoryOfTariffUpdates.class );
	
	public int doStartTag() throws JspException{
	ArrayList<HaadTariffFactorHistoryVO> alHistoryOfTariffUpdates=null;
	
		try
		{
			 alHistoryOfTariffUpdates= (ArrayList<HaadTariffFactorHistoryVO>)pageContext.getSession().getAttribute("alHistoryOfTariffUpdates");
				
	        JspWriter out = pageContext.getOut();//Writer Object to write the File
	       String gridOddRow="'gridOddRow'";
	       String gridEvenRow="'gridEvenRow'";
	        if(alHistoryOfTariffUpdates != null){
	        	if(alHistoryOfTariffUpdates.size()>=1){
	        	
	                		    out.print("<table width='100%'>");
	                			out.print("<tr>");
	                			out.print("<th align='center' class='gridHeader' title='Serial No.'>Sl. No.</th>");
	                			out.print("<th align='center' class='gridHeader' title='Service Type'>Service Type</th>");
	                			out.print("<th align='center' class='gridHeader' title='HAAD Factor'>Factor</th>");
	                			out.print("<th align='center' class='gridHeader' title='Network'>Network</th>");
	                			out.print("<th align='center' class='gridHeader' title='Value'>Value</th>");
	                			out.print("<th align='center' class='gridHeader' title='Tariff Start Date'>Tariff Start Date</th>");
	                			out.print("<th align='center' class='gridHeader' title='Tariff End Date'>Tariff End Date</th>");
	                			out.print("<th align='center' class='gridHeader' title='Updated Date'>Updated Date</th>");
	                			out.print("<th align='center' class='gridHeader' title='Updated By'>Updated By</th>");
	                			out.print("</tr>");
	        	
	                			int i=0;

	                for(HaadTariffFactorHistoryVO historyOfTariffUpdates:alHistoryOfTariffUpdates){
	                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                		out.print("<td align='cenetr'>"+(i+1)+"</td>");
	                		out.print("<td align='center'>"+historyOfTariffUpdates.getService()+"</td>");
	                		out.print("<td align='center'>"+historyOfTariffUpdates.getFactor()+"</td>");
	                		out.print("<td align='center'>"+historyOfTariffUpdates.getNetwork()+"</td>");
	                		out.print("<td align='center'>"+(historyOfTariffUpdates.getNewValue())+"</td>");
	                		out.print("<td align='center'>"+(historyOfTariffUpdates.getStartDate())+"</td>");
	                		if(historyOfTariffUpdates.getEndDate()!=null)
	                		out.print("<td align='center'>"+(historyOfTariffUpdates.getEndDate())+"</td>");
	                		else
	                			out.print("<td align='center'>"+"NA"+"</td>");	
	                		out.print("<td align='center'>"+historyOfTariffUpdates.getAddedDate()+"</td>");
	                		out.print("<td align='center'>"+historyOfTariffUpdates.getUpdatedByName()+"</td>");
	                		out.print("<td align='center'>");
	                		out.print("</tr>");
	        				  
	                		i++;
            			}//for(HaadTariffFactorHistoryVO HistoryOfTariffUpdates:alHistoryOfTariffUpdates)
	               out.print("</table>");
	        	}//if(alHistoryOfTariffUpdates.size()>=1)
	        }//if(alHistoryOfTariffUpdates != null)
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
}//end of DiagnosisDetails class 
