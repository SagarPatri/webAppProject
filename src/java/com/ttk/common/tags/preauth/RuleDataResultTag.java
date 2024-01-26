/**
 * @ (#) ClauseDetails.java Jun 26, 2006
 * Project      : TTK HealthCare Services
 * File         : ClauseDetails.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jun 26, 2006
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
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.RuleConfig;

/**
 * This tag library is used to display the Results of the Evaluated Rules
 * with CL Factor in PraAuth/Claims flow.
 *
 */
public class RuleDataResultTag extends TagSupport {
	
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger( RuleDataResultTag.class );

    private static final String strPAY="1";
    private static final String strAPPLY="1";
    private static final String strDONT_PAY="2";
    private static final String strDONT_APPLY="2";
    private static final String strPAY_CONDITIONALLY="3";
    private static final String strAPPLY_CONDITIONALLY="3";
    private static final String strPAYABLE="Payable";
    private static final String strNOT_PAYABLE="Not Payable";
    private static final String strAPPLICABLE="Applicable";
    private static final String strNOT_APPLICABLE="Not Applicable";
    private static final String strDelimeter="|";
	//changes as per KOC 1099
	
	private List noRemarksClausesList=null;
	//changes as per KOC 1099
    public int doStartTag() throws JspException{
        try
        {
            JspWriter out = pageContext.getOut(); //Writer object to write the file
            Document ruledataDoc=(Document)pageContext.getSession().getAttribute("RuleDataDocument");

            //Declaration of the Variables used
            List clauseList=null;
            List coverageList=null;
            Element eleClause=null;
            Element eleCoverage=null;
//List zeroPercentageList=null;
			
			//changes as per KOC 1099
			int failedRulescount=0;
			List zeroPercentageList=null;
			//StringBuffer rmkDispList=new StringBuffer();
			//int count1=0;
			noRemarksClausesList=new ArrayList();
			noRemarksClausesList.add("Copayment per Claim");
			noRemarksClausesList.add("Global");
			noRemarksClausesList.add("Day care procedures");
			//noRemarksClausesList.add("Individual Account head limits");
			noRemarksClausesList.add("Important Notes");
			

			//changes as per KOC 1099
            if(ruledataDoc!=null)
            {
                //To Display Overall Confidence Level
                Element eleClauses=(Element)ruledataDoc.selectSingleNode("//clauses");
                out.println("<fieldset>");
                out.println("<legend>Confidence Level</legend>");
                out.println("<table align=\"center\" class=\"formContainer\" cellpadding=\"0\" cellspacing=\"0\" ");
                out.println("border=\"0\" width=\"100%\">");
                out.println("<tr class=\"formLabelBold\"><td width=\"30%\">Total Confidence Factor:</td>");
                out.println("<td width=\"70%\">");
                if(eleClauses.valueOf("@clpercentage").equals(""))
                {
                    out.println("<span class=\"pageTitleInfo\">Click on Initiate Check button to execute Rules</span>");
                }//end of if(eleClauses.valueOf("@clpercentage").equals(""))
                else
                {
                    out.println(eleClauses.valueOf("@clpercentage")+"%");
                }//end of else
                out.println("</td></tr>");
                out.println("</table>");
                out.println("</fieldset>");

                //To Display the Confidence Level of Selected Coverages and Error Details if any
                clauseList=ruledataDoc.selectNodes("//clause");
                if(clauseList!=null && clauseList.size()>0)
                {
                    for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
                    {
                        eleClause=(Element)clauseList.get(iClauseCnt);
                        if(eleClause.selectSingleNode("./coverage[@selected='YES']")==null)
                        {
                            continue; //need not display the clause
                        }//end of if(eleClause.selectSingleNode("./coverage[@selected='YES']")==null)

                        out.println("<fieldset><legend>");
                        out.println("<img src=\"/ttk/images/e.gif\" alt=\"Collapse\" width=\"16\" height=\"16\" ");
                        out.println("name=\"clauseimg"+eleClause.valueOf("@id")+"\" align=\"top\" ");
                        out.println(" onClick=\"showhide('clausetab"+eleClause.valueOf("@id")+
                                "','clauseimg"+eleClause.valueOf("@id")+"')\">&nbsp;");
                        out.println(eleClause.valueOf("@name")+"</legend>");
//Changes As Per KOC 1099(Remarks should appear in PreAuthorization.RuleData)
						/**
						 * Change Request: KOC1099 
						 * Modified Date : 3rd,JAN 2012
						 * Author        : Satya
						 * Description   : Display Text Area when clpercentage is zero
						 * **/
						zeroPercentageList=eleClause.selectNodes("./coverage[@clpercentage<'100']");
					
					if(zeroPercentageList.size()>0 && zeroPercentageList!=null)
					{
						if(!noRemarksClausesList.contains(eleClause.valueOf("@name")))
										{
											
										out.print("&nbsp;");
										out.print("&nbsp;");
										out.println("&nbsp;"+displayRemarksNode(eleClause,ruledataDoc));
										out.println("&nbsp;");
										failedRulescount++;
										
										}
					}
						//Changes As Per KOC 1099(Remarks should appear in PreAuthorization.RuleData)
                        out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" ");
                        out.println(" cellspacing=\"1\" cellpadding=\"3\" style=\"display:\" ");
                        out.println(" id=\"clausetab"+eleClause.valueOf("@id")+"\">");

                        coverageList=eleClause.selectNodes("./coverage[@selected='YES']");
                        if(coverageList!=null && coverageList.size()>0)
                        {
                            for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
                            {
                                eleCoverage=(Element)coverageList.get(iCoverageCnt);
                                String strCvrgAllowed=eleCoverage.valueOf("@allowed");

                                if(strCvrgAllowed.equals(strDONT_APPLY)||strCvrgAllowed.equals(strDONT_PAY))
                                {
                                    if(eleCoverage.selectSingleNode("./text[@value!='']")!=null)
                                    {
                                        out.println("<tr style=\"background-color:#EBEDF2;\">");
                                        out.println("<td width=\"80%\" class=\"formLabelBold\">");
                                        out.println("<img src=\"/ttk/images/c.gif\" alt=\"Expand\" width=\"16\" height=\"16\" ");
                                        out.println("name=\"coverageimg"+eleCoverage.valueOf("@id")+"\" align=\"top\" ");
                                        out.println("onClick=\"showhide('coveragetab"+eleCoverage.valueOf("@id")+
                                                "','coverageimg"+eleCoverage.valueOf("@id")+"')\">&nbsp;");
                                        out.println(eleCoverage.valueOf("@name")+"</td>");
                                        out.println("<td width=\"20%\" class=\"formLabelBold\">");

                                        if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        {
                                            out.println(eleCoverage.valueOf("@clpercentage")+"% &nbsp;");
                                        }//end of if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        out.println("(<span style=\"color:#A83108;\">");
                                        out.println(getCoverageStatus(eleCoverage.valueOf("@id"),strCvrgAllowed));
                                        out.println("</span>)");
                                        out.println("<td></tr>");
                                        out.println("<tr id=\"coveragetab"+eleCoverage.valueOf("@id")+"\" style=\"display:none;\">");
                                        out.println("<td colspan=\"2\" class=\"formLabel\">");
                                        out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" ");
                                        out.println("cellspacing=\"0\" cellpadding=\"0\"><ol>");
                                        //added for Personal Waiting Period clause Don't Apply conditions to should not display the text message koc 1278 
                                        if(!eleClause.valueOf("@name").equals("Personal waiting period"))
                                        {
                                        	out.println(displayTextNode(eleCoverage));
                                        }
                                        //added for koc 1278-02/07/2013
                                        //out.println(displayTextNode(eleCoverage));
                                        out.println("</ol></table>");
                                        out.println("</td>");
                                        out.println("</tr>");
                                    }//end of if(eleCoverage.selectSingleNode("./text[@value!='']")!=null)
                                    else
                                    {
                                        out.println("<tr style=\"background-color:#EBEDF2;\">");
                                        out.println("<td width=\"80%\" class=\"formLabelBold\">");
                                        out.println(eleCoverage.valueOf("@name")+"</td>");
                                        out.println("<td width=\"20%\" class=\"formLabelBold\">");
                                        if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        {
                                            out.println(eleCoverage.valueOf("@clpercentage")+"% &nbsp;");
                                        }//end of if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        out.println("(<span style=\"color:#A83108;\">");
                                        out.println(getCoverageStatus(eleCoverage.valueOf("@id"),strCvrgAllowed));
                                        out.println("</span>)");
                                        out.println("<td></tr>");
                                    }//end of else
                                }//end of if(strCvrgAllowed.equals(strDONT_APPLY)||strCvrgAllowed.equals(strDONT_PAY))

                                else if (strCvrgAllowed.equals(strAPPLY)||strCvrgAllowed.equals(strPAY))
                                {
                                    if(eleCoverage.selectSingleNode("./condition[@mandatory='YES']|./text[@value!='']")!=null)
                                    {
                                        out.println("<tr style=\"background-color:#EBEDF2;\">");
                                        out.println("<td width=\"80%\" class=\"formLabelBold\">");
                                        out.println("<img src=\"/ttk/images/c.gif\" alt=\"Expand\" width=\"16\" height=\"16\" ");
                                        out.println("name=\"coverageimg"+eleCoverage.valueOf("@id")+"\" align=\"top\" ");
                                        out.println("onClick=\"showhide('coveragetab"+eleCoverage.valueOf("@id")+
                                                "','coverageimg"+eleCoverage.valueOf("@id")+"')\">&nbsp;");
                                        out.println(eleCoverage.valueOf("@name")+"</td>");
                                        out.println("<td width=\"20%\" class=\"formLabelBold\">");

                                        if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        {
                                            out.println(eleCoverage.valueOf("@clpercentage")+"% &nbsp;");
                                        }//end of if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        out.println("(<span style=\"color:#041A68;\">");
                                        out.println(getCoverageStatus(eleCoverage.valueOf("@id"),strCvrgAllowed));
                                        out.println("</span>)");
                                        out.println("<td></tr>");
                                        out.println("<tr id=\"coveragetab"+eleCoverage.valueOf("@id")+"\" style=\"display:none;\">");
                                        out.println("<td colspan=\"2\" class=\"formLabel\">");
                                        out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" ");
                                        out.println("cellspacing=\"0\" cellpadding=\"0\"><ol>");
                                        out.println(displayConditions(eleCoverage));
                                        out.println(displayTextNode(eleCoverage));
                                        out.println("</ol></table>");
                                        out.println("</td>");
                                        out.println("</tr>");
                                    }//end of if(eleCoverage.selectSingleNode("./condition[@mandatory='YES']|./text[@value!='']")!=null)
                                    else
                                    {
                                        out.println("<tr style=\"background-color:#EBEDF2;\">");
                                        out.println("<td width=\"80%\" class=\"formLabelBold\">");
                                        out.println(eleCoverage.valueOf("@name")+"</td>");
                                        out.println("<td width=\"20%\" class=\"formLabelBold\">");
                                        if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        {
                                            out.println(eleCoverage.valueOf("@clpercentage")+"% &nbsp;");
                                        }//end of if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        out.println("(<span style=\"color:#041A68;\">");
                                        out.println(getCoverageStatus(eleCoverage.valueOf("@id"),strCvrgAllowed));
                                        out.println("</span>)");
                                        out.println("<td></tr>");
                                    }// end of else
                                }//end of else

                                else if(strCvrgAllowed.equals(strAPPLY_CONDITIONALLY)||strCvrgAllowed.equals(strPAY_CONDITIONALLY))
                                {
                                    if(eleCoverage.selectSingleNode("./condition|./text[@value!='']")!=null)
                                    {
                                        out.println("<tr style=\"background-color:#EBEDF2;\">");
                                        out.println("<td width=\"80%\" class=\"formLabelBold\">");
                                        out.println("<img src=\"/ttk/images/c.gif\" alt=\"Expand\" width=\"16\" height=\"16\" ");
                                        out.println("name=\"coverageimg"+eleCoverage.valueOf("@id")+"\" align=\"top\" ");
                                        out.println("onClick=\"showhide('coveragetab"+eleCoverage.valueOf("@id")+
                                                "','coverageimg"+eleCoverage.valueOf("@id")+"')\">&nbsp;");
                                        out.println(eleCoverage.valueOf("@name")+"</td>");
                                        out.println("<td width=\"20%\" class=\"formLabelBold\">");

                                        if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        {
                                            out.println(eleCoverage.valueOf("@clpercentage")+"% &nbsp;");
                                        }//end of if(!(eleCoverage.valueOf("@clpercentage").equals("")))

                                        out.println("<td></tr>");
                                        out.println("<tr id=\"coveragetab"+eleCoverage.valueOf("@id")+"\" style=\"display:none;\">");
                                        out.println("<td colspan=\"2\" class=\"formLabel\">");
                                        out.println("<table align=\"center\" class=\"formContainerWithoutPad order\" border=\"0\" ");
                                        out.println("cellspacing=\"0\" cellpadding=\"0\"><ol>");
                                        out.println(displayConditions(eleCoverage));
                                        out.println(displayTextNode(eleCoverage));
                                        out.println("</ol></table>");
                                        out.println("</td>");
                                        out.println("</tr>");
                                    }//end of if(eleCoverage.selectSingleNode("./condition|./text[@value!='']")!=null)
                                    else
                                    {
                                        out.println("<tr style=\"background-color:#EBEDF2;\">");
                                        out.println("<td width=\"80%\" class=\"formLabelBold\">");
                                        out.println(eleCoverage.valueOf("@name")+"</td>");
                                        out.println("<td width=\"20%\" class=\"formLabelBold\">");
                                        if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        {
                                            out.println(eleCoverage.valueOf("@clpercentage")+"% &nbsp;");
                                        }//end of if(!(eleCoverage.valueOf("@clpercentage").equals("")))
                                        out.println("<td></tr>");
                                    }// end of else
                                }//end of else if(strCvrgAllowed.equals(strAPPLY_CONDITIONALLY)||strCvrgAllowed.equals(strPAY_CONDITIONALLY))
                            }//end of for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
                        }//end of if(coverageList!=null && coverageList.size()>0)
                        out.println("</table>");
                        out.println("</fieldset>");
                    }//end of for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
               //changes of KOC1099 on JAN 2012
					if(failedRulescount>0)
					{
						
						out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" ");
						out.println("cellspacing=\"0\" cellpadding=\"0\">");
						out.println("<tr>");
						out.println("<td><a href=\"#\" onClick=\"javascript:onOverride();\"><img src=\"ttk/images/Overridebutton.gif\" alt=\"Override\" width=\"81\" height=\"17\" border=\"0\" align=\"absbottom\"></a> &nbsp;</td>");
					
						out.println("<td align=\"right\"><button type=\"button\" name=\"Save\" accesskey=\"U\" class=\"buttons\" onMouseout=\"this.className='buttons'\" onMouseover=\"this.className='buttons buttonsHover'\" onClick=\"javascript:updateRemarks();\" ><u>U</u>pdateRemarks</button></td> &nbsp; ");
						out.println("</tr>");
						out.println("</table>");
						//<!-- Changes KOC1099 -->
						noRemarksClausesList=null;
					}
					//changes of KOC1099 on JAN 2012
							
                }//end of if(clauseList!=null && clauseList.size()>0)
            }//end of if(ruledataDoc!=null)
        }//end of try
        catch(Exception exp)
        {
            log.info("Exception occured while processing the RuleDataResultTag library");
            exp.printStackTrace();
            throw new JspException("Error: in RuleDataResultTag Tag Library!!!" );
        }//end of catch(Exception exp)
        return SKIP_BODY;
    }//end of doStartTag()

    /**
     * This method is used to display the evaluated conditions of
     * the coverage with the result
     *
     * @param eleCoverage Coverage Node
     * @return String prepared displayElement
     * @throws TTKException
     */
    private String displayConditions(Element eleCoverage)throws TTKException
    {
        StringBuffer sbfDisplayCondition=new StringBuffer();

        //select the conditions for the present coverage
        List conditionList=null;
        if(eleCoverage.valueOf("@allowed").equals(strAPPLY)||eleCoverage.valueOf("@allowed").equals(strPAY))
        {
            conditionList=eleCoverage.selectNodes("./condition[@mandatory='YES']");
        }//end of if(eleCoverage.valueOf("@allowed").equals(strAPPLY)||eleCoverage.valueOf("@allowed").equals(strPAY))
        else
        {
            conditionList=eleCoverage.selectNodes("./condition");
        }//end of else

        Element eleCondition=null;
        HashMap hmDisplayNodes=(HashMap)pageContext.getServletContext().getAttribute("RULE_DISPLAY_NODES");
        HashMap hmCopayResultNodes=(HashMap)pageContext.getServletContext().getAttribute("RULE_COPAY_RESULT_NODES");
        int iCondNotDefinedCnt=0;
        if(conditionList==null ||conditionList.size()==0)
        {
            return sbfDisplayCondition.toString();
        }//end of if(conditionList==null ||conditionList.size()==0)

        for(Iterator itCondtion=conditionList.iterator();itCondtion.hasNext();)
        {
            eleCondition=(Element)itCondtion.next();
            String strFieldData=eleCondition.valueOf("@fieldData");
            String strValue=eleCondition.valueOf("@value");
            if(strFieldData.equals("~")||strFieldData.equals("null"))
            {
                strFieldData="";
            }//end of if(strFieldData.equals("~")||strFieldData.equals("null"))
            if(strValue.equals("~")||strValue.equals("null"))
            {
                strValue="";
            }//end of if(strValue.equals("~")||strValue.equals("null"))

            if(!(eleCondition.valueOf("@mandatory").equals("YES")) && (eleCondition.valueOf("@result").
                    equals("Rule undefined")))
            {
                iCondNotDefinedCnt++;
                continue;
            }//end of if(!(eleCondition.valueOf("@mandatory").equals("YES")) && (eleCondition.valueOf("@result").equals("Rule undefined")))

            if(hmDisplayNodes.get(eleCondition.valueOf("@id"))!=null && hmCopayResultNodes.get(eleCondition.valueOf("@id"))!=null){
            	Element eleCopayResult=(Element)((ArrayList)hmCopayResultNodes.get(eleCondition.valueOf("@id"))).get(0);
                StringBuffer sbfCopayResult=new StringBuffer();
                sbfCopayResult.append("<tr>");
                sbfCopayResult.append("<td width=\"80%\" class=\"formLabel leftMorePad\"><li>");
                sbfCopayResult.append(eleCopayResult.valueOf("@prelabel")).append("&nbsp;");

                if(eleCopayResult.valueOf("@lookup").equals(""))
                {
                	sbfCopayResult.append("[<span class=\"pageTitleInfo\">").append(strFieldData).append("</span>]&nbsp;");
                }//end of if(eleDisplay.valueOf("@lookup").equals(""))
                else
                {
                	sbfCopayResult.append("[<span class=\"pageTitleInfo\">");
                    StringTokenizer stFieldValues=new StringTokenizer(strFieldData,strDelimeter);
                    while(stFieldValues.hasMoreTokens())
                    {
                    	sbfCopayResult.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),
                        		eleCopayResult.valueOf("@lookup")));								            		
  if(eleCondition.valueOf("@id").equalsIgnoreCase("cnd.25.10.1") || eleCondition.valueOf("@id").equalsIgnoreCase("cnd.25.11.1") || eleCondition.valueOf("@id").equalsIgnoreCase("cnd.25.12.1")|| eleCondition.valueOf("@id").equalsIgnoreCase("cnd.25.13.1")  )
                        	{
                    			sbfCopayResult.append(" - ");
                        		sbfCopayResult.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),"allregioncode"));
                        	}
                        if(stFieldValues.countTokens()>0)
                        {
                        	sbfCopayResult.append(", ");
                        }//end of if(stFieldValues.countTokens()>0)
                    }//end of while(stFieldValues.hasMoreTokens())
                    sbfCopayResult.append("</span>]&nbsp;");
                }//end of else

                //If unit is empty or Per day don't display it while showing result
                if(!eleCondition.valueOf("@unit").equals(""))
                {
                	sbfCopayResult.append(RuleConfig.getLookupText("unit",eleCondition.valueOf("@unit")));
                	sbfCopayResult.append("&nbsp;");
                }//end of if(!eleCondition.valueOf("@unit").equals(""))

                sbfCopayResult.append(RuleConfig.getOperatorText(eleCondition.valueOf("@opType"),
                        eleCondition.valueOf("@op"))).append("&nbsp;");

                if(eleCopayResult.valueOf("@control").equals(""))
                {
                	sbfCopayResult.append(eleCopayResult.valueOf("@postlabel")).append("&nbsp;");
                }//end of if(eleDisplay.valueOf("@control").equals(""))
                
                if(eleCopayResult.valueOf("@lookup").equals(""))
                {
                	sbfCopayResult.append("[<span class=\"subHeader\">").append(strValue).append("</span>]&nbsp;");
                }//end of if(eleDisplay.valueOf("@lookup").equals(""))
                else
                {
                	sbfCopayResult.append("[<span class=\"subHeader\">");
                    StringTokenizer stFieldValues=new StringTokenizer(strValue,strDelimeter);
                    while(stFieldValues.hasMoreTokens())
                    {
                    	sbfCopayResult.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),
                        		eleCopayResult.valueOf("@lookup")));
                        if(stFieldValues.countTokens()>0)
                        {
                        	sbfCopayResult.append(", ");
                        }//end of if(stFieldValues.countTokens()>0)
                    }//end of while(stFieldValues.hasMoreTokens())
                    sbfCopayResult.append("</span>]&nbsp;");
                }//end of else

                //If unit is empty or Per day don't display it while showing result
                if(!eleCondition.valueOf("@unit").equals(""))
                {
                	sbfCopayResult.append(RuleConfig.getLookupText("unit",eleCondition.valueOf("@unit")));
                	sbfCopayResult.append("&nbsp;");
                }//end of if(!eleCondition.valueOf("@unit").equals(""))

                sbfCopayResult.append("</li></td>");
                if(eleCondition.valueOf("@result").equalsIgnoreCase("pass"))
                {
                	sbfCopayResult.append("<td width=\"20%\" class=\"formLabel\">");
                	sbfCopayResult.append("<img src=\"ttk/images/rightIcon.gif\" alt=\"Pass\" ");
                	sbfCopayResult.append("width=\"16\" height=\"15\"></td>");
                }//end of if(eleCondition.valueOf("@result").equalsIgnoreCase("pass"))
                else if(eleCondition.valueOf("@result").equalsIgnoreCase("fail"))
                {
                	sbfCopayResult.append("<td width=\"20%\" class=\"formLabel\">");
                	sbfCopayResult.append("<img src=\"ttk/images/DeleteIcon.gif\" alt=\"Fail\" ");
                	sbfCopayResult.append("width=\"16\" height=\"15\"></td>");
                }//end of else if(eleCondition.valueOf("@result").equalsIgnoreCase("fail"))
                else
                {
                	sbfCopayResult.append("<td width=\"20%\" class=\"mandatorySymbol\">");
                	sbfCopayResult.append(eleCondition.valueOf("@result")).append("</td>");
                }//end of else
                sbfCopayResult.append("</tr>");
                sbfDisplayCondition.append(sbfCopayResult.toString());
            }//end of if(hmDisplayNodes.get(eleCondition.valueOf("@id"))!=null && hmCopayResultNodes.get(eleCondition.valueOf("@id"))!=null)
            else if(hmDisplayNodes.get(eleCondition.valueOf("@id"))!=null && hmCopayResultNodes.get(eleCondition.valueOf("@id"))==null)
            {
                Element eleDisplay=(Element)((ArrayList)hmDisplayNodes.get(eleCondition.valueOf("@id"))).get(0);
                StringBuffer sbfDisplay=new StringBuffer();
                sbfDisplay.append("<tr>");
                sbfDisplay.append("<td width=\"80%\" class=\"formLabel leftMorePad\"><li>");
                sbfDisplay.append(eleDisplay.valueOf("@prelabel")).append("&nbsp;");

                if(eleDisplay.valueOf("@lookup").equals(""))
                {
                    sbfDisplay.append("[<span class=\"pageTitleInfo\">").append(strFieldData).append("</span>]&nbsp;");
                }//end of if(eleDisplay.valueOf("@lookup").equals(""))
                else
                {
                    sbfDisplay.append("[<span class=\"pageTitleInfo\">");
                    StringTokenizer stFieldValues=new StringTokenizer(strFieldData,strDelimeter);
                    while(stFieldValues.hasMoreTokens())
                    {
                       sbfDisplay.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),eleDisplay.valueOf("@lookup")));
                    	//Modification as per KOC 1284 Change request
                    	if(eleCondition.valueOf("@id").equalsIgnoreCase("cnd.25.10.1")||eleCondition.valueOf("@id").equalsIgnoreCase("cnd.25.11.1")|| eleCondition.valueOf("@id").equalsIgnoreCase("cnd.25.12.1")|| eleCondition.valueOf("@id").equalsIgnoreCase("cnd.25.13.1")  )
                    	{  
                          sbfDisplay.append(" - ");
                    		sbfDisplay.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),"allregioncode"));
                    	}
    	             //Modification as per KOC 1284 Change request
                        if(stFieldValues.countTokens()>0)
                        {
                            sbfDisplay.append(", ");
                        }//end of if(stFieldValues.countTokens()>0)
                    }//end of while(stFieldValues.hasMoreTokens())
                    sbfDisplay.append("</span>]&nbsp;");
                }//end of else

                //If unit is empty or Per day don't display it while showing result
                if(!eleCondition.valueOf("@unit").equals(""))
                {
                    sbfDisplay.append(RuleConfig.getLookupText("unit",eleCondition.valueOf("@unit")));
                    sbfDisplay.append("&nbsp;");
                }//end of if(!eleCondition.valueOf("@unit").equals(""))

                sbfDisplay.append(RuleConfig.getOperatorText(eleCondition.valueOf("@opType"),
                        eleCondition.valueOf("@op"))).append("&nbsp;");

                if(eleDisplay.valueOf("@control").equals(""))
                {
                    sbfDisplay.append(eleDisplay.valueOf("@postlabel")).append("&nbsp;");
                }//end of if(eleDisplay.valueOf("@control").equals(""))
                
                if(eleDisplay.valueOf("@lookup").equals(""))
                {
                    sbfDisplay.append("[<span class=\"subHeader\">").append(strValue).append("</span>]&nbsp;");
                }//end of if(eleDisplay.valueOf("@lookup").equals(""))
                else
                {
                    sbfDisplay.append("[<span class=\"subHeader\">");
                    StringTokenizer stFieldValues=new StringTokenizer(strValue,strDelimeter);
                    while(stFieldValues.hasMoreTokens())
                    {
                        sbfDisplay.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),
                                eleDisplay.valueOf("@lookup")));
                        if(stFieldValues.countTokens()>0)
                        {
                            sbfDisplay.append(", ");
                        }//end of if(stFieldValues.countTokens()>0)
                    }//end of while(stFieldValues.hasMoreTokens())
                    sbfDisplay.append("</span>]&nbsp;");
                }//end of else

                //If unit is empty or Per day don't display it while showing result
                if(!eleCondition.valueOf("@unit").equals(""))
                {
                    sbfDisplay.append(RuleConfig.getLookupText("unit",eleCondition.valueOf("@unit")));
                    sbfDisplay.append("&nbsp;");
                }//end of if(!eleCondition.valueOf("@unit").equals(""))

                sbfDisplay.append("</li></td>");
                if(eleCondition.valueOf("@result").equalsIgnoreCase("pass"))
                {
                    sbfDisplay.append("<td width=\"20%\" class=\"formLabel\">");
                    sbfDisplay.append("<img src=\"ttk/images/rightIcon.gif\" alt=\"Pass\" ");
                    sbfDisplay.append("width=\"16\" height=\"15\"></td>");
                }//end of if(eleCondition.valueOf("@result").equalsIgnoreCase("pass"))
                else if(eleCondition.valueOf("@result").equalsIgnoreCase("fail"))
                {
                    sbfDisplay.append("<td width=\"20%\" class=\"formLabel\">");
                    sbfDisplay.append("<img src=\"ttk/images/DeleteIcon.gif\" alt=\"Fail\" ");
                    sbfDisplay.append("width=\"16\" height=\"15\"></td>");
                }//end of else if(eleCondition.valueOf("@result").equalsIgnoreCase("fail"))
                else
                {
                    sbfDisplay.append("<td width=\"20%\" class=\"mandatorySymbol\">");
                    sbfDisplay.append(eleCondition.valueOf("@result")).append("</td>");
                }//end of else
                sbfDisplay.append("</tr>");
                sbfDisplayCondition.append(sbfDisplay.toString());
            }//end of if(displayList!=null && displayList.size()>0)
        }//end of for(Iterator itCondtion=conditionList.iterator();itCondtion.hasNext();)

        //If none of the conditions defined for the Coverage display the alert message
        if(conditionList.size()==iCondNotDefinedCnt)
        {
            sbfDisplayCondition.append("<tr>");
            sbfDisplayCondition.append("<td colspan=\"2\" width=\"85%\" class=\"formLabel leftMorePad\"><li>");
            sbfDisplayCondition.append("Rules not Defined, User discretion required.");
            sbfDisplayCondition.append("</li></td>");
            sbfDisplayCondition.append("</tr>");
        }//end of if(conditionList.size()==iCondNotDefinedCnt)
        return sbfDisplayCondition.toString();
    }//end of displayConditions(Element eleCoverage)

    /**
     * This method is used to display the Text nodes under the
     * given Coverage Node
     * @param eleCoverage Coverage Node
     * @return String prepared display element
     */
    private String displayTextNode(Element eleCoverage)
    {
        StringBuffer sbfTextNode=new StringBuffer();
        if(eleCoverage.selectSingleNode("./text[@value!='']")!=null)
        {
            List textList=eleCoverage.selectNodes("./text[@value!='']");
            Element eleText=null;
            for(Iterator itText=textList.iterator();itText.hasNext();)
            {
                eleText=(Element)itText.next();
                sbfTextNode.append("<tr><td colspan=\"2\" class=\"formLabel leftMorePad\"><li>");
                // UNNI Added for fixing Bug ID 42647
                if(eleText.valueOf("@value") !=null ){
                	if(eleText.valueOf("@value").indexOf("~") != -1)
                	{
                		String strNormalString = eleText.valueOf("@value").replaceAll("~", "<br>") ;
                		sbfTextNode.append(strNormalString);
                	}//end of if(eleText.valueOf("@value").indexOf("~") != -1)
                	else
                	{
                		sbfTextNode.append(eleText.valueOf("@value"));
                	}//end of else	
                }//end of if(eleText.valueOf("@value") !=null )
                //sbfTextNode.append(eleText.valueOf("@value"));
                // End of Adding
                sbfTextNode.append("</li></td></tr>");
            }//end of for(Iterator itText=textList.iterator();itText.hasNext())
        }//end of if(eleCoverage.selectSingleNode("./text")!=null)
        return sbfTextNode.toString();
    }//end of displayTextNode(Element eleCoverage)
//Changes on jan 2nd 2011 KOC1099
	/**
	 * This method is used to display the Remark nodes under the
	 * given Clause Node
	 * @param Eclause Clause Node
	 * @return String prepared display element
	 */
	private String displayRemarksNode(Element eleClause,Document ruledataDoc)
	{ 
		StringBuffer sbfRemarksNode=new StringBuffer();
		//String strPermission="disabled";
		String strPermission="";
		String strTargetValue="";
		List  rmkDisplayList=null;
		
		if(ruledataDoc.selectSingleNode("//remarks")!=null)
		{
		/*	if(!noRemarksClausesList.contains(eleClause.valueOf("@name")))
		{	*/
			
		if(eleClause.valueOf("@allowed").equalsIgnoreCase("Yes"))
		{
			
			
							try {
								rmkDisplayList= (List) eleClause.selectNodes("./remarks/display");	
								Iterator it=rmkDisplayList.iterator();
								while(it.hasNext())
								{
								Element eleDisplay=(Element)it.next();
								
								
							
							//	rmkList.add(eleDisplay.valueOf("@id").toString());
								 strTargetValue=(eleDisplay.getParent().valueOf("@value").equals("~")? eleDisplay.valueOf("@default"):
									 eleDisplay.getParent().valueOf("@value"));
								sbfRemarksNode.append(eleDisplay.valueOf("@prelabel")).append("&nbsp;");
								sbfRemarksNode.append(":");
								sbfRemarksNode.append("<span class=\"mandatorySymbol\">*</span>").append("&nbsp;");
								sbfRemarksNode.append("&nbsp;");
								sbfRemarksNode.append(TTKCommon.buildDisplayRemarks(eleDisplay,
										eleDisplay.valueOf("@id"),strTargetValue,strPermission,null));
								
								}
								rmkDisplayList=null;
			
				} catch (Exception e2) {
				log.info("Exception occured displaying Remarks in RuleDataResultTag library");
                   e2.printStackTrace();
					
			
				}
				
				

			}

		}
			
	//	}
           return sbfRemarksNode.toString();
	}//end of displayTextNode(Element eleCoverage)
	//changes on jan 2nd for KOC1099
	//Changes on jan 2nd 2011 KOC1099
	/**
	 * This method is used to display the Remark nodes under the
	 * given Clause Node
	 * @param Eclause Clause Node
	 * @return String prepared display element
	 */
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
     * This method will return the Coverage status description.
     * @param strCoverageId Coverage ID
     * @param strCoveragrAllowed Coverage status
     * @return Coverage status description
     */
    private String getCoverageStatus(String strCoverageId,String strCoveragrAllowed)
    {
        String strCoverageStatus="";
        HashMap hmDisplayNodes=(HashMap)pageContext.getServletContext().getAttribute("RULE_DISPLAY_NODES");
        if(hmDisplayNodes.get(strCoverageId)!=null)
        {
            Element eleDisplay=(Element)((ArrayList)hmDisplayNodes.get(strCoverageId)).get(0);
            String strOptValues[] =eleDisplay.valueOf("@optVal").split(",");
            String strOptTexts[] =eleDisplay.valueOf("@altText").split(",");
            if(strOptValues!=null && strOptTexts!=null && strOptValues.length==strOptTexts.length)
            {
                for(int ioptValCnt=0;ioptValCnt<strOptValues.length;ioptValCnt++)
                {
                    if(strOptValues[ioptValCnt].equals(strCoveragrAllowed))
                    {
                        if(strOptTexts[ioptValCnt].equals("Pay"))
                        {
                            strCoverageStatus=strPAYABLE;
                        }//end of if(strOptTexts[ioptValCnt].equals("Pay"))
                        else if(strOptTexts[ioptValCnt].equals("Don't Pay"))
                        {
                            strCoverageStatus=strNOT_PAYABLE;
                        }//end of else if(strOptTexts[ioptValCnt].equals("Don't Pay"))
                        else if(strOptTexts[ioptValCnt].equals("Apply"))
                        {
                            strCoverageStatus=strAPPLICABLE;
                        }//end of else if(strOptTexts[ioptValCnt].equals("Apply"))
                        else
                        {
                            strCoverageStatus=strNOT_APPLICABLE;
                        }//end of else
                        break;
                    }//end of if(strOptValues[ioptValCnt].equals(strCoveragrAllowed))
                }//end of for(int ioptValCnt=0;ioptValCnt<strOptValues.length;ioptValCnt++)
            }//end of if(strOptValues!=null && strOptTexts!=null && strOptValues.length==strOptTexts.length)
        }//end of if(hmDisplayNodes.get(strCoverageId)!=null)
        return strCoverageStatus;
    }//end of getCoverageStatus(String strCoverageId,String strCoveragrAllowed)
}//end of RuleDataResultTag.java
