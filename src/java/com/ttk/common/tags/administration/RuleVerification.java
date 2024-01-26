/**
 * @ (#) RuleVerification.java Aug 12, 2006
 * Project      : TTK HealthCare Services
 * File         : RuleVerification.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Aug 12, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.administration;

//import java.util.Iterator;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

//import com.ttk.common.TTKCommon;

/**
 * This method will checks whether clauses/coverages ot conditions defined or not
 *  for the product/Policy Rules and if any one of the aboce is not defined.
 *  it will show that node.
 */
public class RuleVerification extends TagSupport{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger log = Logger.getLogger( RuleVerification.class );

    /**
     * This method will be executed when customised tag begins
     * @return int
     * @throws JspException
     */
    public int doStartTag() throws JspException
    {
        try
        {
            log.info("Inside RuleVerification Tag library.............");
            JspWriter out = pageContext.getOut(); //Writer object to write the file
            Document ruleDocuement=(Document)pageContext.getSession().getAttribute("RuleDocument");
            String strDisplayClauses=displayClauses(ruleDocuement);
            if(strDisplayClauses.equals(""))    //if rules for applicable clauses defined
            {
                out.println("<table align=\"center\" width=\"100%\" class=\"successContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                out.println("<tr>");
                out.println("<td>All applicable Rules defined.</td>");
                out.println("</tr>");
                out.println("</table>");
            }//end of if(strDisplayClauses.equals(""))
            else            //if one of the clause/coverage/condition is undefined
            {
                out.println(strDisplayClauses);
            }//end of else
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            throw new JspException("Error: in RuleVerification Tag Library!!!" );
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
     * This method checks if any clause/coverage/condtion defined or not.
     * if any of the above is not defined from the applicable clauses
     * It will display the error
     * @param ruleDocuement XML Document to be checked.
     * @return
     */
    private String displayClauses(Document ruleDocuement)
    {
        StringBuffer sbfDisplayClause=new StringBuffer();
        if(ruleDocuement!=null)
        {
            List clauseList=null;
            Element eleClause=null;
            clauseList=ruleDocuement.selectNodes("//clause");
            if(clauseList!=null && clauseList.size()>0)
            {
                for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
                {
                    eleClause=(Element)clauseList.get(iClauseCnt);
                    String strDisplayCoverages=displayCoverages(eleClause);
                    if(!strDisplayCoverages.equals(""))
                    {
                        sbfDisplayClause.append("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                        sbfDisplayClause.append("<tr>");
                        sbfDisplayClause.append("<td class=\"fieldHeader\">");

                        if(eleClause.valueOf("@allowed").equals("YES"))
                            sbfDisplayClause.append("<img src=\"ttk/images/rightIcon.gif\" alt=\"Allowed\" width=\"16\" height=\"15\">");
                        else
                            sbfDisplayClause.append("<img src=\"ttk/images/DeleteIcon.gif\" alt=\"Not allowed\" width=\"16\" height=\"15\">");
                        sbfDisplayClause.append("&nbsp;&nbsp;").append(eleClause.valueOf("@name")).append("</td>");
                        sbfDisplayClause.append("</tr>");
                        sbfDisplayClause.append("<tr>");
                        sbfDisplayClause.append("<td>");
                        sbfDisplayClause.append("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                        sbfDisplayClause.append(strDisplayCoverages);
                        sbfDisplayClause.append("</table>");
                        sbfDisplayClause.append("</td>");
                        sbfDisplayClause.append("</tr>");
                        sbfDisplayClause.append("</table>");
                    }//end of if(!strDisplaycontent.equals(""))
                    else if(!eleClause.valueOf("@allowed").equals("YES"))
                    {
                        sbfDisplayClause.append("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                        sbfDisplayClause.append("<tr>");
                        sbfDisplayClause.append("<td width=\"30%\" class=\"fieldHeader\">"+eleClause.valueOf("@name")+"</td>");
                        sbfDisplayClause.append("<td width=\"70%\" class=\"formLabelBold labelRed\">Not allowed</td>");
                        sbfDisplayClause.append("</tr>");
                        sbfDisplayClause.append("</table>");
                    }//end of else if(!eleClause.valueOf("@allowed").equals("YES"))
                }//end of for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
            }//end of if(clauseList!=null && clauseList.size()>0)
        }//end of if(ruleDocuement!=null)
        return sbfDisplayClause.toString();
    }//end of displayClauses(Document ruleDocuement)

    /**
     * This method checks if any condition or coverage is not entered for the
     * given clause.
     * @param eleClause Element current clause
     * @return String out put to be displayed if applicable
     */
    private String displayCoverages(Element eleClause)
    {
        StringBuffer sbfDisplayCoverages=new StringBuffer();
        //select coverages with module Product or Policy
        List coverageList=eleClause.selectNodes("./coverage[contains(@module,'P')]");
        Element eleCoverage=null;
        if(coverageList!=null && coverageList.size()>0)
        {
            for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
            {
                eleCoverage=(Element)coverageList.get(iCoverageCnt);
                String strDisplayCondition=displayCondition(eleCoverage);
                if(!strDisplayCondition.equals(""))
                {
                    sbfDisplayCoverages.append("<tr>");
                    sbfDisplayCoverages.append("<td class=\"formLabelBold leftMorePad\">");
                    if(eleCoverage.valueOf("@allowed").equals("YES"))
                        sbfDisplayCoverages.append("<img src=\"ttk/images/rightIcon.gif\" alt=\"Allowed\" width=\"16\" height=\"15\">");
                    else
                        sbfDisplayCoverages.append("<img src=\"ttk/images/DeleteIcon.gif\" alt=\"Not allowed\" width=\"16\" height=\"15\">");
                    sbfDisplayCoverages.append("&nbsp;&nbsp;").append(eleCoverage.valueOf("@name")).append("</td>");
                    sbfDisplayCoverages.append("</tr>");
                    sbfDisplayCoverages.append("<tr>");
                    sbfDisplayCoverages.append("<td>");
                    sbfDisplayCoverages.append("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                    sbfDisplayCoverages.append(strDisplayCondition);
                    sbfDisplayCoverages.append("</table>");
                    sbfDisplayCoverages.append("</td>");
                    sbfDisplayCoverages.append("</tr>");
                }//end of if(!strDisplayCondition.equals(""))
            }//end of for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
        }//end of if(coverageList!=null && coverageList.size()>0)
        return sbfDisplayCoverages.toString();
    }//end of displayCoverages(Element eleClause)

    /**
     * This method will check if all the conditions of the given coverage are defined or not.
     * displays the conditions which are not defined
     * @param eleCoverage Element Current coverage for which conditions to be checked.
     * @return String condtions to be displayed.
     */
    private String displayCondition(Element eleCoverage)
    {
        StringBuffer sbfDisplayCondition=new StringBuffer();

        List conditionList=eleCoverage.selectNodes("./condition[@module='P']");
        List displayList=null;
        List copayResultList=null;
        Element eleCondition=null;
        Element eleDisplay=null;
        Element eleCopayResult=null;
        if(conditionList!=null && conditionList.size()>0)
        {
            for(int iConditioneCnt=0;iConditioneCnt<conditionList.size();iConditioneCnt++)
            {
                eleCondition=(Element)conditionList.get(iConditioneCnt);
                displayList=eleCondition.selectNodes("./display[@target!='']");     //select the display nodes with target value
                copayResultList=eleCondition.selectNodes("./copayresult[@target!='']");     //select the display nodes with target value
                if(displayList!=null && displayList.size()>0)
                {
                    boolean blnRuleDefined=false;
                    if(!(eleCondition.selectSingleNode("./display")).valueOf("@control").equals(""))
                    {
                        if(eleCoverage.valueOf("@allowed").equals("YES"))   //if coverage is allowed check for the conditions.
                        {
                            for(int iDisplayCnt=0;iDisplayCnt<displayList.size();iDisplayCnt++)
                            {
                                eleDisplay=(Element)displayList.get(iDisplayCnt);
                                String strControl=eleDisplay.valueOf("@control");

                                if(!(strControl.equals("")||strControl.equalsIgnoreCase("image")||strControl.equalsIgnoreCase("select")))
                                {
                                    if(!eleDisplay.valueOf("@default").equals(""))
                                    {
                                        blnRuleDefined=true;
                                        break;
                                    }//end of if(!eleDisplay.valueOf("@default").equals(""))
                                }//end of if(!(strControl.equals("")||strControl.equalsIgnoreCase("image")||strControl.equalsIgnoreCase("select")))
                            }//end of for(int iDisplayCnt=0;iDisplayCnt<displayList.size();iDisplayCnt++)

                            //check the flag to see whether Rule is defined/not Defined
                            if(blnRuleDefined==false)
                            {
                                eleDisplay=(Element)displayList.get(0);
                                if(!(eleDisplay.valueOf("@control").equals("") || eleDisplay.valueOf("@control").equalsIgnoreCase("select")))
                                {
                                    sbfDisplayCondition.append("<tr>");
                                    sbfDisplayCondition.append("<td width=\"50%\" class=\"formLabel leftMorePad\">"+eleDisplay.valueOf("@prelabel")+"</td>");
                                    sbfDisplayCondition.append("<td width=\"50%\" class=\"labelRed\">Rule not defined</td>");
                                    sbfDisplayCondition.append("</tr>");
                                }//end of if(!eleDisplay.valueOf("@control").equals(""))
                            }//end of if(blnRuleDefined==false)
                        }//end of if(eleCoverage.valueOf("@allowed").equals("YES"))
                        else
                        {
                            eleDisplay=(Element)displayList.get(0);
                            if(!eleDisplay.valueOf("@control").equals(""))
                            {
                                sbfDisplayCondition.append("<tr>");
                                sbfDisplayCondition.append("<td width=\"50%\" class=\"formLabel leftMorePad\">"+eleDisplay.valueOf("@prelabel")+"</td>");
                                sbfDisplayCondition.append("<td width=\"50%\" class=\"labelRed\">Rule not defined</td>");
                                sbfDisplayCondition.append("</tr>");
                            }//end of if(!eleDisplay.valueOf("@control").equals(""))
                        }//end of else
                    }//end of if(!(eleCondition.selectSingleNode("./display")).valueOf("@control").equals(""))
                }//end of  if(displayList!=null && displayList.size()>0)
                if(copayResultList!=null && copayResultList.size()>0)
                {
                    boolean blnRuleDefined=false;
                    if(!(eleCondition.selectSingleNode("./copayresult")).valueOf("@control").equals(""))
                    {
                        if(eleCoverage.valueOf("@allowed").equals("YES"))   //if coverage is allowed check for the conditions.
                        {
                            for(int iDisplayCnt=0;iDisplayCnt<copayResultList.size();iDisplayCnt++)
                            {
                            	eleCopayResult=(Element)copayResultList.get(iDisplayCnt);
                                String strControl=eleCopayResult.valueOf("@control");

                                if(!(strControl.equals("")||strControl.equalsIgnoreCase("image")||strControl.equalsIgnoreCase("select")))
                                {
                                    if(!eleCopayResult.valueOf("@default").equals(""))
                                    {
                                        blnRuleDefined=true;
                                        break;
                                    }//end of if(!eleDisplay.valueOf("@default").equals(""))
                                }//end of if(!(strControl.equals("")||strControl.equalsIgnoreCase("image")||strControl.equalsIgnoreCase("select")))
                            }//end of for(int iDisplayCnt=0;iDisplayCnt<displayList.size();iDisplayCnt++)

                            //check the flag to see whether Rule is defined/not Defined
                            if(blnRuleDefined==false)
                            {
                            	eleCopayResult=(Element)displayList.get(0);
                                if(!(eleCopayResult.valueOf("@control").equals("") || eleCopayResult.valueOf("@control").equalsIgnoreCase("select")))
                                {
                                    sbfDisplayCondition.append("<tr>");
                                    sbfDisplayCondition.append("<td width=\"50%\" class=\"formLabel leftMorePad\">"+eleCopayResult.valueOf("@prelabel")+"</td>");
                                    sbfDisplayCondition.append("<td width=\"50%\" class=\"labelRed\">Rule not defined</td>");
                                    sbfDisplayCondition.append("</tr>");
                                }//end of if(!eleDisplay.valueOf("@control").equals(""))
                            }//end of if(blnRuleDefined==false)
                        }//end of if(eleCoverage.valueOf("@allowed").equals("YES"))
                        else
                        {
                        	eleCopayResult=(Element)copayResultList.get(0);
                            if(!eleCopayResult.valueOf("@control").equals(""))
                            {
                                sbfDisplayCondition.append("<tr>");
                                sbfDisplayCondition.append("<td width=\"50%\" class=\"formLabel leftMorePad\">"+eleCopayResult.valueOf("@prelabel")+"</td>");
                                sbfDisplayCondition.append("<td width=\"50%\" class=\"labelRed\">Rule not defined</td>");
                                sbfDisplayCondition.append("</tr>");
                            }//end of if(!eleCopayResult.valueOf("@control").equals(""))
                        }//end of else
                    }//end of if(!(eleCondition.selectSingleNode("./copayresult")).valueOf("@control").equals(""))
                }//end of  if(copayResultList!=null && copayResultList.size()>0)
            }//end of for(int iConditioneCnt=0;iConditioneCnt<conditionList.size();iConditioneCnt++)
        }//end of if(conditionList!=null && conditionList.size()>0)
        return sbfDisplayCondition.toString();
    }//end of displayCondition(Element eleCoverage)

    /**
     * This method checks whether the value is defined or not
     * and returns the boolean value
     * @param strValue String input value to be checked
     * @return blnFlag boolean
     */
    /*private boolean checkValue(String strValue)
    {
        boolean blnFlag=false;
        if(strValue.startsWith("'"))
        {
            if(!(strValue.equals("''")|| strValue.equals("'null'")|| strValue.equals("'~'")))
                blnFlag=true;
        }//end of if(strValue.startsWith("'"))
        else
        {
            if(!(strValue.equals("")|| strValue.equals("null")|| strValue.equals("~")))
                blnFlag=true;
        }//end of else
        return blnFlag;
    }//end of checkValue(String strValue)
*/}//end of RuleVerification.java
