/**
 * @ (#) BusinessErrors.java Dec 21, 2007
 * Project 	     : TTK HealthCare Services
 * File          : BusinessErrors.java
 * Author        : Arun K N
 * Company       : Span Systems Corporation
 * Date Created  : Dec 21, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

//import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;
import com.ttk.common.security.RuleConfig;
import com.ttk.dto.administration.ErrorLogVO;

public class BusinessErrors extends TagSupport{
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
//	private static Logger log = Logger.getLogger( BusinessErrors.class );
	private static final String strDelimeter="|";
	String strScope = "";
	String strName = "";
	public void setScope(String strScope) {
		this.strScope = strScope;
	}
	public void setName(String strName) {
		this.strName = strName;
	}
	
	public int doStartTag() throws JspException {
		try {
			ArrayList alBusinessErrors=null;
			StringBuffer sbfActionMessage=null;
			ErrorLogVO errorLogVO=null;
			JspWriter out = pageContext.getOut(); //Writer object to write the file
			pageContext.getResponse().setContentType("image/gif");
			
			if(strScope.equals("request"))
			{
				alBusinessErrors = (ArrayList)pageContext.getRequest().getAttribute(this.strName);
			}//end of if(strScope.equals("request"))
			else
			{
				alBusinessErrors = (ArrayList)pageContext.getSession().getAttribute(this.strName);
			}//end of else
			
			if(alBusinessErrors!=null && !alBusinessErrors.isEmpty())
			{
				out.print("<table align=\"center\" class=\"errorContainer\" style=\"display:\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
				out.print("<tr><td><strong><img src=\"/ttk/images/ErrorIcon.gif\" alt=\"Error\" width=\"16\" height=\"16\" align=\"absmiddle\">&nbsp;");
				out.print("The following errors have occurred - </strong><ol style=\"padding:0px;margin-top:3px;margin-bottom:0px;margin-left:25px;\">");
				for(int i=0; i<alBusinessErrors.size();i++)
				{
					errorLogVO =(ErrorLogVO)alBusinessErrors.get(i);
					sbfActionMessage=new StringBuffer();
					
					HashMap hmDisplayNodes=(HashMap)pageContext.getServletContext().getAttribute("RULE_DISPLAY_NODES");
					Element eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(errorLogVO.getConditionID())).get(0);
					out.print("<li>");
					// Added for constructing proper error message by Unni on 03-04-2008
					//sbfActionMessage.append(eleDisplay.valueOf("@prelabel")).append(" ");
					sbfActionMessage.append(errorLogVO.getErrorMessage()+" ").append(eleDisplay.valueOf("@prelabel")).append(" ");
					// End of Addition
					
					//This part is commented not to display the field data as it will be visible in screen itself
					/*if(eleDisplay.valueOf("@lookup").equals(""))
					 {
					 sbfActionMessage.append(validationErrorVO.getComputedValue()).append(" ");
					 }//end of if(eleDisplay.valueOf("@lookup").equals(""))
					 else
					 {
					 StringTokenizer stFieldValues=new StringTokenizer(validationErrorVO.getComputedValue(),strDelimeter);
					 while(stFieldValues.hasMoreTokens())
					 {
					 sbfActionMessage.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),
					 eleDisplay.valueOf("@lookup")));
					 if(stFieldValues.countTokens()>0)
					 sbfActionMessage.append(", ");
					 }//end of while(stFieldValues.hasMoreTokens())
					 sbfActionMessage.append(" ");
					 }//end of else
					 */		 
					//					sbfActionMessage.append(RuleConfig.getLookupText("unit",validationErrorVO.getUnit())).append(" ");
					sbfActionMessage.append(RuleConfig.getOperatorText(errorLogVO.getOperatorType(),
							errorLogVO.getOperator())).append(" ");
					if(eleDisplay.valueOf("@lookup").equals(""))
					{
						sbfActionMessage.append(errorLogVO.getConfiguredValue()).append(" ");
					}//end of if(eleDisplay.valueOf("@lookup").equals(""))
					else
					{
						StringTokenizer stFieldValues=new StringTokenizer(errorLogVO.getConfiguredValue(),
								strDelimeter);
						while(stFieldValues.hasMoreTokens())
						{
							sbfActionMessage.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),
									eleDisplay.valueOf("@lookup")));
							if(stFieldValues.countTokens()>0)
							{
								sbfActionMessage.append(", ");
							}//end of if(stFieldValues.countTokens()>0)
						}//end of while(stFieldValues.hasMoreTokens())
						sbfActionMessage.append(" ");
					}//end of else
					sbfActionMessage.append(RuleConfig.getLookupText("unit",errorLogVO.getUnit()));
					out.print(sbfActionMessage.toString());
					out.print("</li>");
				}//end of for(int i=0; i<conditionList.size();i++)
				out.print("</ol></td></tr></table><script>TC_PageDataChanged=true; ClientReset=false;JS_Focus_Disabled=true</script>");
			}
		}//end of try block
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw new JspException("Error: in ClauseDetails Tag Library!!!" );
		}//end of catch(Exception exp)
		return SKIP_BODY;
	}//end of doStartTag()
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}
