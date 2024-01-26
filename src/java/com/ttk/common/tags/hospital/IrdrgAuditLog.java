
package com.ttk.common.tags.hospital;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.ttk.dto.empanelment.HaadTariffFactorHistoryVO;
import com.ttk.dto.empanelment.HospitalDetailVO;


public class IrdrgAuditLog extends TagSupport
{
	private String flow;
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( HistoryOfTariffUpdates.class );
	
	public int doStartTag() throws JspException{
	ArrayList<HospitalDetailVO> IrdrgParameters=null;
	
		try
		{
			IrdrgParameters= (ArrayList<HospitalDetailVO>)pageContext.getSession().getAttribute("alIrdrg");
				
	        JspWriter out = pageContext.getOut();//Writer Object to write the File
	       String gridOddRow="'gridOddRow'";
	       String gridEvenRow="'gridEvenRow'";
	        
	                		    out.print("<table width='100%'>");
	                			out.print("<tr>");
	                			out.print("<th align='center' class='gridHeader' title='S.No'>Sl. No.</th>");
	                			out.print("<th align='center' class='gridHeader' title='Remarks'>Remarks</th>");
	                			out.print("<th align='center' class='gridHeader' title='Added By'>Added By</th>");
	                			out.print("<th align='center' class='gridHeader' title='Added Date'>Added Date</th>");
	                			
	                			out.print("</tr>");
	                if(IrdrgParameters != null)
	                {
	                	if(IrdrgParameters.size()>=1)
	                	{
	                		int i=0;

	                		for(HospitalDetailVO Irdrg:IrdrgParameters){
	                		out.print("<tr  class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                		if(Irdrg.getUpdateRemarks()!=null)
	                		{	
	                			out.print("<td align='cenetr'>"+(i+1)+"</td>");
	                		}
	                		if(Irdrg.getUpdateRemarks()!=null)
	                			out.print("<td align='center'>"+Irdrg.getUpdateRemarks()+"</td>");
		                	else
		                		out.print("<td align='center'>"+" "+"</td>");
	                		
	                		if(Irdrg.getAuditAddedBy()!=null)
	                			out.print("<td align='center'>"+Irdrg.getAuditAddedBy()+"</td>");
		                	else
		                		out.print("<td align='center'>"+" "+"</td>");
	                		
	                		if(Irdrg.getAuditAddedDate()!=null)
	                			out.print("<td align='center'>"+Irdrg.getAuditAddedDate()+"</td>");
		                	else
		                		out.print("<td align='center'>"+" "+"</td>");
	                		
	                	 i++;
            			}
	               out.print("</table>");
	        	}
	        }
	        
	        else
			{	        
				out.print("<table align='center' border-collapse='collapse' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
				out.print("<tr>");
				out.print("<td align='center'>NO DATA</td>");
				out.print("</tr>");
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
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
}//end of DiagnosisDetails class 
