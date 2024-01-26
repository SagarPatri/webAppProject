/**
 * @ (#) ProviderConfDetailsView.java June 27, 2015
 * Project : ProjectX
 * File : ProviderConfDetailsView.java
 * Author : Nagababu K
 * Company :Vidal
 * Date Created : June 27, 2015
 *
 * @author : Nagababu K
 * Modified by :
 * Modified date :
 * Reason :
*/
package com.ttk.common.tags.administration;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ttk.dto.common.CacheObject;

public class ConfigWindowDetails extends TagSupport
{
	/**CountryCopayDetails
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( CitibankEnrolHistory.class );
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
	{
		try
		{
		
			JspWriter out = pageContext.getOut();//Writer Object to write the File		
			
			ServletRequest request=pageContext.getRequest();
			
			HashMap<String, String> populateMapDetails=null;
			ArrayList<String> configList=(ArrayList<String>)request.getAttribute("configList");
			 
			ArrayList 	populateList=(ArrayList)request.getAttribute("populateList");
			
			if(configList==null) configList=new ArrayList<>();
		if(populateList!=null){
			
		
		if("RD".equals(request.getParameter("boxType"))){
			
		  out.print("<table align=\"center\" class=\"gridWithCheckBox\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">");
		  out.print("<tr><td width=\"5%\" ID=\"listsubheader\" CLASS=\"gridHeader\"></td>");
		  out.print("<td width=\"95%\" ID=\"listsubheader\" CLASS=\"gridHeader\">"+request.getParameter("windowTitle")+"&nbsp;</td></tr>");
			
		 
				int   i=0;
		  for(Object obj:populateList){
			  
			  if(i%2==0)out.print("<tr class=\"gridEvenRow\">");
			  else out.print("<tr class=\"gridOddRow\">");
					
			  out.print("<td width=\"5%\" align=\"center\">");
			  CacheObject cacheObject=(CacheObject)obj;
			  if(configList.contains(cacheObject.getCacheId()))				  
			  out.print("<input type=\"radio\" checked  name=\"optRadio\" id=\"radid"+i+"\" value=\""+cacheObject.getCacheId()+"\">");
			  else 		
			out.print("<input type=\"radio\"  name=\"optRadio\" id=\"radid"+i+"\" value=\""+cacheObject.getCacheId()+"\">");
			  out.print("</td>");
			  out.print("<td width=\"95%\" class=\"textLabelBold\">"+cacheObject.getCacheDesc()+"</td>");
			  out.print("</tr>");
		    i++;
		  }	 //  for(Entry<String, String> entry:valueSet){       	
	       out.print("</table>");
		    }//if("RD".equals(request.getParameter("boxType"))){
		else if("CH".equals(request.getParameter("boxType"))){
			
			String strChAll="<input name=\"checkall\" id=\"checkall\" type=\"checkbox\" onclick=\"javascript:oncheck('checkall')\">&nbsp;";
			  out.print("<table align=\"center\" class=\"gridWithCheckBox\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">");
			  out.print("<tr><td width=\"5%\" ID=\"listsubheader\" CLASS=\"gridHeader\">"+strChAll+"</td>");
			  out.print("<td width=\"95%\" ID=\"listsubheader\" CLASS=\"gridHeader\">"+request.getParameter("windowTitle")+"&nbsp;</td></tr>");
				
			 
					int   i=0;
			  for(Object obj:populateList){
				  
				  if(i%2==0)out.print("<tr class=\"gridEvenRow\">");
				  else out.print("<tr class=\"gridOddRow\">");
						
				  out.print("<td width=\"5%\" align=\"center\">");
				  CacheObject cacheObject=(CacheObject)obj;
				  if(configList.contains(cacheObject.getCacheId()))				  
				  out.print("<input type=\"checkbox\" checked onclick=\"javascript:oncheck('');\"  name=\"optSelect\" id=\"checkid"+i+"\" value=\""+cacheObject.getCacheId()+"\">");
				  else 		
				out.print("<input type=\"checkbox\" onclick=\"javascript:oncheck('');\"  name=\"optSelect\" id=\"checkid"+i+"\" value=\""+cacheObject.getCacheId()+"\">");
				  out.print("</td>");
				  out.print("<td width=\"95%\" class=\"textLabelBold\">"+cacheObject.getCacheDesc()+"</td>");
				  out.print("</tr>");
			    i++;
			  }	 //  for(Entry<String, String> entry:valueSet){       	
		       out.print("</table>");
			    }//if("RD".equals(request.getParameter("boxType"))){
		
		out.print("<table style='position: fixed;bottom: 0;left: 100px;' border='0' align='center' cellpadding='0' cellspacing='0'>");
		  out.print("<tr>");
		  out.print("<td>");	
		  if(!"V".equals(request.getParameter("OPMode")))
		  out.print("<input type=\"button\" name=\"Button\" value=\"Save\" class=\"buttons\" onClick=\"javascript:save();\">&nbsp;");
		
		  out.print("<button type=\"button\" name=\"Button\" accesskey=\"c\" class=\"buttons\" onMouseout=\"this.className='buttons'\" onMouseover=\"this.className='buttons buttonsHover'\" onClick=\"javascript:self.close();\"><u>C</u>lose</button>");
		  out.print("</td>");	
		out.print("</tr>");
		  out.print("</table>");
		  
		}//if(benefitConditions!=null){
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
	
}//end of DiagnosisDetails class 
