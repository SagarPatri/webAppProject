/**
 * @ (#) RuleBenefitsDisplay.java NOV 15, 2017
 * Project      : TTK HealthCare Services
 * File         : RuleBenefitsDisplay.java
 * Author       : Nagababu K
 * Company      : VIDAL
 * Date Created : NOV 15, 2017
 *
 * @author       :  Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.administration;

//import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;

//import com.ttk.common.TTKCommon;

/**
 * This method will checks whether clauses/coverages ot conditions defined or not
 *  for the product/Policy Rules and if any one of the aboce is not defined.
 *  it will show that node.
 */
public class RuleBenefitsDisplay extends TagSupport{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger log = Logger.getLogger( RuleBenefitsDisplay.class );

    /**
     * This method will be executed when customised tag begins
     * @return int
     * @throws JspException
     */
    public int doStartTag() throws JspException
    {
        try
        {
            
            JspWriter out = pageContext.getOut(); //Writer object to write the file
            HttpSession session=pageContext.getSession();
           LinkedHashMap<String, String> ruleBenefitList=(LinkedHashMap<String, String>)pageContext.getServletContext().getAttribute("RuleBenefitList");
           DynaActionForm frmDefineRules =(DynaActionForm)session.getAttribute("frmDefineRules");
           LinkedHashMap<String,Long> lhmConfBenefitTypes=(LinkedHashMap)frmDefineRules.get("confBenefitTypes");
           if(lhmConfBenefitTypes==null)lhmConfBenefitTypes=new LinkedHashMap<>();
           
            if(ruleBenefitList!=null) {
                out.println("<table align=\"center\" width=\"100%\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\">");
                
                Set<Entry<String, String>>  beneSet=ruleBenefitList.entrySet();
                
                StringBuilder confSB=new StringBuilder();
                StringBuilder notConfSB=new StringBuilder();
                StringBuilder reportSB=new StringBuilder();
                
                for(Entry<String, String> beneEntry:beneSet){
                	
                	 if(lhmConfBenefitTypes.containsKey(beneEntry.getKey())){
                		
                		 confSB.append("<tr style=\"background-color:#EBEDF2;height: 22px;\">");
                     	confSB.append("<td class=\"autoIncrementTD\"></td>");
                     	confSB.append("<td class=\"formLabelBold\" width=\"100%\">");// out.println("");
                     	confSB.append(beneEntry.getValue());
                     	confSB.append("&nbsp;&nbsp;&nbsp;&nbsp;<a style=\"background-color:#DAF7A6;\" href=\"#\" onclick=\"onBenefitConfiguration('"+beneEntry.getKey()+"','R');\">Re-Configure</a>");
                     	confSB.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"onBenefitConfiguration('"+beneEntry.getKey()+"','V');\">View</a>");
                     	//confSB.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"hmoPolicyReport('"+beneEntry.getKey()+"','V');\">Report</a>"); // added by govind
                     	
               		  if("GLOB".equals(beneEntry.getKey()))confSB.append("&nbsp;&nbsp;&nbsp;&nbsp;");
               		  else confSB.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"removeBenefit('"+beneEntry.getKey()+"');\">Remove</a>");
               		
                     	confSB.append("</td>");
                    	 confSB.append("</tr>");
                	 }else{
                		 notConfSB.append("<tr style=\"background-color:#EBEDF2;height: 22px;\">");
                		 notConfSB.append("<td class=\"autoIncrementTD\"></td>");
                		 notConfSB.append("<td class=\"formLabelBold\" width=\"100%\">");// out.println("");
                		 notConfSB.append(beneEntry.getValue());
                		 notConfSB.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"onBenefitConfiguration('"+beneEntry.getKey()+"','C');\">Configure</a>");
                		 notConfSB.append("</td>");                		
                		 notConfSB.append("</tr>");
                	 }
                	 
                }
                out.println(confSB);
                out.println(notConfSB);
          /*      reportSB.append("<tr style=\"background-color:#EBEDF2;height: 22px;\">");
                reportSB.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"hmoPolicyReport();\">GENERATE EXCEL REPORT</a>");
                reportSB.append("</tr>");
                out.println(reportSB);*/
                out.println("</table>");
            }//if(ruleBenefitList!=null) {
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            throw new JspException("Error: in RuleBenefitsDisplay Tag Library!!!" );
        }//end of catch(Exception exp)
        return SKIP_BODY;
    }//end of doStartTag()

    /**
     * this method will be executed before  tag closes
     * @return int
     * @throws JspException
     */
    public int doEndTag() throws JspException
    {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()

   
}//end of RuleBenefitsDisplay.java
