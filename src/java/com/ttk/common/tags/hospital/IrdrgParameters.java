
 
package com.ttk.common.tags.hospital;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.ttk.dto.empanelment.HaadTariffFactorHistoryVO;
import com.ttk.dto.empanelment.HospitalDetailVO;
//import org.apache.log4j.Logger;

public class IrdrgParameters extends TagSupport
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
	                			out.print("<th align='center' class='gridHeader' title='Payer'>Payer</th>");
	                			out.print("<th align='center' class='gridHeader' title='Network'>Network</th>");
	                			out.print("<th align='center' class='gridHeader' title='Negotiation factor'>Negotiation Factor</th>");
	                			out.print("<th align='center' class='gridHeader' title='Effective from Date'>Effective from Date</th>");
	                			out.print("<th align='center' class='gridHeader' title='Expiry Date'>Expiry Date</th>");
	                			out.print("<th align='center' class='gridHeader' title='Audit Logs'>Audit Logs</th>");
	                			out.print("<th align='center' class='gridHeader' title='Delete'>Delete</th>");
	                			out.print("</tr>");
	                if(IrdrgParameters != null)
	                {
	                	if(IrdrgParameters.size()>=1)
	                	{
	                		int i=0;
	                		
	                		for(HospitalDetailVO Irdrg:IrdrgParameters){
	                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                	
	                		out.print("<td align='center'><A onclick='javascript:onEditIrdrg("+Irdrg.getDhaNagSeqId()+");' href='#'>"+(i+1)+"</A></td>");
	                	//	out.print("<td align='cenetr'>"+(i+1)+"</td>");	
	                		out.print("<td align='center'>"+Irdrg.getPayerCode()+"</td>");
	                				
	                		if(Irdrg.getNetworkType()!=null)
	                			out.print("<td align='center'>"+Irdrg.getNetworkType()+"</td>");
		                	else
		                		out.print("<td align='center'>"+"NA"+"</td>");
	                		
	                		if(Irdrg.getNegotiationFactor()!=null)
	                			out.print("<td align='center'>"+Irdrg.getNegotiationFactor()+"</td>");
		                	else
		                		out.print("<td align='center'>"+"NA"+"</td>");
	                		
	                		if(Irdrg.getStartDate()!=null)
		                		out.print("<td align='center'>"+(Irdrg.getStartDate())+"</td>");
		                	else
		                		out.print("<td align='center'>"+"NA"+"</td>");
	                		
	                		if(Irdrg.getEndDate()!=null)
		                		out.print("<td align='center'>"+(Irdrg.getEndDate())+"</td>");
		                	else
		                		out.print("<td align='center'>"+"NA"+"</td>");
	                		
	                		out.print("<td align='center'><A onclick='javascript:onAuditLog("+Irdrg.getDhaNagSeqId()+");' href='#'><IMG border=0 alt='Audit Log' src='/ttk/images/EditIcon.gif' width=16 height=16></A></td>");
	                		
	                		out.print("<td align='center'><A onclick='javascript:onDelete("+Irdrg.getDhaNagSeqId()+");' href='#'><IMG border=0 alt='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A></td>");
	                		
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
			//	out.print(PreauthUploadDocs);   
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
}//end of IrdrgParameters class 
