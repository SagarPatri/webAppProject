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

import com.ttk.common.TTKCommon;

//import com.ttk.common.TTKCommon;

/**
 * This method will checks whether clauses/coverages ot conditions defined or not
 *  for the product/Policy Rules and if any one of the aboce is not defined.
 *  it will show that node.
 */
public class MOHBenefitsWebBoards extends TagSupport{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger log = Logger.getLogger( MOHBenefitsWebBoards.class );

    /**
     * This method will be executed when customised tag begins
     * @return int
     * @throws JspException
     */
    public int doStartTag() throws JspException
    {
        try
        {
            log.debug("Inside MOHBenefitsWebBoards Tag library.............");
            
            JspWriter out = pageContext.getOut(); //Writer object to write the file
            HttpSession session=pageContext.getSession();
           LinkedHashMap<String, String> ruleBenefitList=(LinkedHashMap<String, String>)pageContext.getServletContext().getAttribute("RuleBenefitList");
         
           DynaActionForm frmDefineRules =(DynaActionForm)session.getAttribute("frmDefineRules");
           String strBenefitID=TTKCommon.checkNull(frmDefineRules.getString("benefitID"));
           String strBenefCompYN=TTKCommon.checkNull(frmDefineRules.getString("benefitRuleCompletedYN"));
           
           LinkedHashMap<String,Long> lhmConfBenefitTypes=(LinkedHashMap)frmDefineRules.get("confBenefitTypes");
           
           if(lhmConfBenefitTypes==null)lhmConfBenefitTypes=new LinkedHashMap<>();
           
            if(ruleBenefitList!=null) {
                
                Set<Entry<String, String>>  beneSet=ruleBenefitList.entrySet();
                
                StringBuilder confSB=new StringBuilder();
                StringBuilder notConfSB=new StringBuilder();
                
                
                confSB.append("<select class=\"SBMoreMedium\" name=\"confBenefitType\" onchange=\"onBenefitConfiguration(this.value,'R');\">");
                confSB.append("<option value=\"\">select from list</optio>");
                notConfSB.append("<select class=\"SBMoreMedium\" name=\"notConfBenefitType\" onchange=\"onBenefitConfiguration(this.value,'C');\">");
                notConfSB.append("<option value=\"\">select from list</optio>");
                for(Entry<String, String> beneEntry:beneSet){
                	
                	 if(lhmConfBenefitTypes.containsKey(beneEntry.getKey())){
                	
                		 if(strBenefitID.equals(beneEntry.getKey())){
                			 confSB.append("<option value=\"");
                			 confSB.append(beneEntry.getKey());
                			 confSB.append("\" selected>");
                			 confSB.append(beneEntry.getValue());
                			 confSB.append("</option>");
                		 }
                		 else {
                			 confSB.append("<option value=\"");
                			 confSB.append(beneEntry.getKey());
                			 confSB.append("\">");
                			 confSB.append(beneEntry.getValue());
                			 confSB.append("</option>");
                		 }
                     	
                	 }else{
                		 if(strBenefitID.equals(beneEntry.getKey())){
                			 notConfSB.append("<option value=\"");
                			 notConfSB.append(beneEntry.getKey());
                			 notConfSB.append("\" selected>");
                			 notConfSB.append(beneEntry.getValue());
                			 notConfSB.append("</option>");
                		 }
                		 else {
                			 notConfSB.append("<option value=\"");
                			 notConfSB.append(beneEntry.getKey());
                			 notConfSB.append("\">");
                			 notConfSB.append(beneEntry.getValue());
                			 notConfSB.append("</option>");
                		 }
                		 
                	 }
                	 
                }
                confSB.append("</select>");
                notConfSB.append("</select>");
                out.print("Re-Configure :");
                out.print(confSB);
                if(!"Y".equals(strBenefCompYN)){
                out.print("&nbsp;&nbsp;");
                out.print("Configure :");
                out.print(notConfSB);
            }
            }//if(ruleBenefitList!=null) {
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            throw new JspException("Error: in MOHBenefitsWebBoards Tag Library!!!" );
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

   
}//end of MOHBenefitsWebBoards.java
