/**
 * @ (#) CoverageList.java Jul 9, 2006
 * Project      : TTK HealthCare Services
 * File         : CoverageList.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 9, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.preauth;

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

/**
 * This Tag library is used in Pre-Authorization and Cliams module.
 * It is used to display the applicable clauses from which User can select the applicable coverages
 * to run the Rule Engine.
 *
 */
public class CoverageList extends TagSupport{

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger log = Logger.getLogger(CoverageList.class );
//    private String strShowStyle="display:";
//    private String strHideStyle="display:none;";
//    private String strReadOnly="readOnly";
//    private String strDisabled="Disabled";
    /**
     * This method will be executed when customised tag begins
     * @return int
     * @throws JspException
     */
    public int doStartTag() throws JspException
    {
        try{
            log.debug("Inside CoverageList Tag library.............");
            JspWriter out = pageContext.getOut(); //Writer object to write the file
            HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
            String[] strCoverageIDs=(String[])request.getAttribute("CoverageIDs");
            String strActiveLink=TTKCommon.getActiveLink(request);
            Document ruleDataDocument=(Document)pageContext.getSession().getAttribute("RuleDataDocument");
            
            if(ruleDataDocument!=null)
            {
                Element eleClause=null;
                Element eleCoverage=null;
                List clauseList=null;
                List coverageList=null;
                if(strActiveLink.equals("Pre-Authorization"))
                {
                    clauseList=ruleDataDocument.selectNodes("//clause[@type='rule' and contains(@execution,'PRE')]");   //get the applicable clauses to be processed in Pre-Authorization
                }//end of if(strActiveLink.equals("Pre-Authorization"))
                else
                {
                    clauseList=ruleDataDocument.selectNodes("//clause[@type='rule' and contains(@execution,'CLA')]");   //get the applicable clauses to be processed in Claims
                }//end of else

                if(clauseList==null || clauseList.size()==0)
                {
                    return SKIP_BODY;
                }//end of if(clauseList==null || clauseList.size()==0)

                for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
                {
                    eleClause=(Element)clauseList.get(iClauseCnt);
                    if(eleClause.selectSingleNode("./coverage")==null)
                    {
                        continue;
                    }//end of if(eleClause.selectSingleNode("./coverage")==null)

                    coverageList=eleClause.selectNodes("./coverage");
                    out.println("<fieldset><legend>");
                    if(eleClause.selectSingleNode("./coverage[@selected='YES']")!=null)
                    {
                        out.println("<img src=\"/ttk/images/e.gif\" alt=\"Collapse\" width=\"16\" height=\"16\" ");
                        out.println("name=\"clauseimg"+eleClause.valueOf("@id")+"\" onClick=\"showhide('clausetab"+eleClause.valueOf("@id")+
                                "','clauseimg"+eleClause.valueOf("@id")+"')\" align=\"absmiddle\">&nbsp;");
                        out.println("<span style=\"color:#A83108;\">"+eleClause.valueOf("@name")+"</span></legend>");
                        out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" ");
                        out.println("cellpadding=\"0\" style=\"display:\" id=\"clausetab"+eleClause.valueOf("@id")+"\">");
                    }//end of if(eleClause.selectSingleNode("./coverage[@selected='YES']")!=null)
                    else
                    {
                        out.println("<img src=\"/ttk/images/c.gif\" alt=\"Expand\" width=\"16\" height=\"16\" ");
                        out.println("name=\"clauseimg"+eleClause.valueOf("@id")+"\" onClick=\"showhide('clausetab"+eleClause.valueOf("@id")+
                                "','clauseimg"+eleClause.valueOf("@id")+"')\" align=\"absmiddle\">&nbsp;");
                        out.println(eleClause.valueOf("@name")+"</legend>");
                        out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" ");
                        out.println("cellpadding=\"0\" style=\"display:none;\" id=\"clausetab"+eleClause.valueOf("@id")+"\">");
                    }//end of else

                    for(Iterator itCoverage=coverageList.iterator();itCoverage.hasNext();)
                    {
                        eleCoverage=(Element)itCoverage.next();
                        out.println("<tr>");
                        out.println("<td>");
                        //for making the coverages of global clause as always selected
                        if(eleClause.valueOf("@id").equals("cls.1"))
                        {
                            out.println("<input type=\"checkbox\" name=\""+eleCoverage.valueOf("@id")+"\" value=\"YES\" checked Disabled >&nbsp;&nbsp;");
                            out.println("<input type=\"hidden\" name=\""+eleCoverage.valueOf("@id")+"\" value=\"YES\">");
                        }//end of if(eleClause.valueOf("@id").equals("cls.1"))
                        else
                        {
                            out.println("<input type=\"checkbox\" name=\""+eleCoverage.valueOf("@id")+"\" value=\"YES\"  ");
                            out.println(((isCoverageSelected(eleCoverage.valueOf("@id"),strCoverageIDs)==true)? " checked ":"")+" >&nbsp;&nbsp;");
                        }//end of else
                        out.println(eleCoverage.valueOf("@name"));
                        out.print("</td>");
                        out.println("</tr>");
                    }//end of for(Iterator itCoverage=coverageList.iterator();itCoverage.hasNext();)
                    out.println("</table>");
                    out.println("</fieldset>");
                }//end of for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
            }//end of if(ruleDataDocument!=null)
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
     * This method checks whether coverage is already selected or not
     * and returns true/false.
     * @param strFieldId String field name of the coverage
     * @param strCoverageIds String[] Array of selected covergares
     * @return blnFlag boolean value
     */
    private boolean isCoverageSelected(String strFieldId,String[] strCoverageIds)
    {
        boolean blnFlag=false;
        for(int iCoverageCnt=0;iCoverageCnt<strCoverageIds.length;iCoverageCnt++)
        {
            if(strCoverageIds[iCoverageCnt].equals(strFieldId))
            {
                blnFlag=true;
                break;
            }//end of if(strCoverages[iCoverageCnt].equals(strFieldName))
        }//end of for(int iCoverageCnt=0;iCoverageCnt<strCoverages.length;iCoverageCnt++)
        return blnFlag;
    }//end of isCoverageSelected(String strFieldName,String[] strCoverages)
}//end of CoverageList.java
