
package com.ttk.common.tags.hospital;

import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;


import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.DrugDetailsVO;

public class ProviderDiagnosisDetails extends TagSupport
{
	private String flow;
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( OnlineDrugDetails.class );
	
	public int doStartTag() throws JspException{
		ArrayList<DiagnosisDetailsVO> diagnosis=null;
	
		try
		{
			if("PAT".equalsIgnoreCase(getFlow()))
				diagnosis=(ArrayList<DiagnosisDetailsVO>)pageContext.getSession().getAttribute("ProviderDiagnosis");
							
	        	JspWriter out = pageContext.getOut();//Writer Object to write the File
	        	String gridOddRow="'gridOddRow'";
	        	String gridEvenRow="'gridEvenRow'";
	        	if(diagnosis != null)
	        	{
	        		if(diagnosis.size()>=1)
	        		{
	               		out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'>");
	            		out.print("<tr>");
		                out.print("<th align='center' class='gridHeader' title='ICD Code'>ICD Code</th>");
		                out.print("<th align='center' class='gridHeader' title='Ailment Description'>Ailment Description</th>");
		                out.print("<th align='center' class='gridHeader' title='Primary Ailment'>Primary Ailment</th>");
	            		out.print("</tr>");
	        	
	            		int i=0;

	                for(DiagnosisDetailsVO diagnosisDetailsVO:diagnosis)
	                {
	                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                		out.print("<td align='center'>"+diagnosisDetailsVO.getIcdCode()+"</td>");
	                		out.print("<td align='center'>"+diagnosisDetailsVO.getAilmentDescription()+"</td>");
	                		out.print("<td align='center'>"+diagnosisDetailsVO.getPrimaryAilment()+"</td>");
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
