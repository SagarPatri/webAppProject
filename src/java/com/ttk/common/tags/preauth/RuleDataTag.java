/**
 * @ (#) RuleDataTag.java Jul 7, 2006
 * Project      : TTK HealthCare Services
 * File         : RuleDataTag.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 7, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.preauth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;
import com.ttk.common.security.RuleConfig;
//import com.ttk.common.tags.ClauseDetails;

/**
 * This tag library is used to Collect the Information from the user
 * while executing the rules in PreAuth/Claims for the Condition nodes
 * with source as XML.
 *
 */
public class RuleDataTag extends TagSupport {

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger(RuleDataTag.class );
//    private String strShowStyle="display:";
//    private String strHideStyle="display:none;";
//    private String strReadOnly="readOnly";
    private String strDisabled="Disabled";
    /**
     * This method will be executed when customised tag begins
     * @return int
     * @throws JspException
     */
    public int doStartTag() throws JspException
    {

        try
        {
            log.debug("Inside RuleDataTag Tag library.............");
            JspWriter out = pageContext.getOut(); //Writer object to write the file
            HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
            Document ruleDataDoc=(Document)pageContext.getSession().getAttribute("RuleDataDocument");
            String strPermission="";
            if(!TTKCommon.isAuthorized(request,"Edit"))
            {
                strPermission=strDisabled;
            }//end of if(!TTKCommon.isAuthorized(request,"Edit"))
            if(ruleDataDoc!=null)
            {
                String strXpath="//clause/coverage[@allowed='3' and @selected='YES']/condition[@source='XML']";
                List conditionList=ruleDataDoc.selectNodes(strXpath);
                Element eleCondition=null;
                Element eleDisplay=null;

                HashMap hmDisplayNodes=(HashMap)pageContext.getServletContext().getAttribute("RULE_DISPLAY_NODES");
                if(conditionList!=null)
                {
                    out.println("<fieldset>");
                    out.println("<legend>Modify Rule Data </legend>");
                    out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" " +
                            "cellspacing=\"0\" cellpadding=\"0\" id=\"\">");
                    for(Iterator itCondition=conditionList.iterator();itCondition.hasNext();)
                    {
                        eleCondition=(Element)itCondition.next();
                        eleDisplay=getDisplyNode((ArrayList)hmDisplayNodes.get(eleCondition.valueOf("@id")));
                        if(eleDisplay!=null)
                        {
                            String strFieldata=eleCondition.valueOf("@fieldData");
                            if(strFieldata.equals("~"))
                            {
                                strFieldata=eleDisplay.valueOf("@default");
                            }//end of if(strFieldata.equals("~"))
                            out.println("<tr>");
                            out.println("<td width=\"30%\" class=\"formLabel\">"+eleDisplay.valueOf("@prelabel")+"</td>");
                            out.println("<td width=\"70%\" >");
                            out.println(TTKCommon.buildDisplayElement(eleDisplay,eleCondition.valueOf("@id"),
                                    strFieldata,strPermission,null));

                            if(!eleCondition.valueOf("@unit").equals(""))
                            {
                                out.println("&nbsp;"+RuleConfig.getLookupText("unit",eleCondition.valueOf("@unit")));
                            }//end of if(!eleCondition.valueOf("@unit").equals(""))
                            out.println("</td>");
                            out.println("</tr>");
                        }//end of if(eleDisplay!=null)
                    }//end of for(Iterator itCondition=conditionList.iterator();itCondition.hasNext();)
                    out.println("</table>");
                    out.println("</fieldset>");
                }//end of if(conditionList!=null)
            }//end of if(ruleDataDoc!=null)
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            throw new JspException("Error: in ClauseDetails Tag Library!!!" );
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

    /**
     * This method will return display node with target containing field data if exists
     * to collect the information required in Preauth/Claims for the given condition.
     *
     * @param alDisplayNodes ArrayList of Display nodes for the currnet Condition
     * @return eleDisplay Display node with target containg field data if any
     */
    private Element getDisplyNode(ArrayList alDisplayNodes)
    {
        Element eleDisplay=null;
        if(alDisplayNodes!=null)
        {
            for(Iterator itDisplayNodes=alDisplayNodes.iterator();itDisplayNodes.hasNext();)
            {
                eleDisplay=(Element)itDisplayNodes.next();
                if(eleDisplay.valueOf("@target").contains("fieldData"))
                {
                    return eleDisplay;
                }//end of if(eleDisplay.valueOf("@target").contains("fieldData"))
            }//end of for(Iterator itDisplayNodes=alDisplayNodes.iterator();itDisplayNodes.hasNext();)
        }//end of if(alDisplayNodes!=null)
        return null;
    }//end of etDisplyNode(ArrayList alDisplayNodes)
}//end of RuleDataTag.java
