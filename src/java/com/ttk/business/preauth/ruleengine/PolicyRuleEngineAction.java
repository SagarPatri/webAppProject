/**
 * @ (#) PolicyRuleEngineAction.java Oct 18, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicyRuleEngineAction.java
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Oct 18, 2006
 *
 * @author       : Krishna K H
 * Modified by   : Arun K N
 * Modified date : Jun 05, 2007
 * Reason        : For removing CL factor calculation as it is not needed in Validations.
 */

package com.ttk.business.preauth.ruleengine;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.RuleConfig;

/**
 * This class is used to generate the Error messages if any after evaluating Coverage against the Rule Engine.
 * It selects the evaluated conditions based on the coverages status. Constructs the Error message
 * based on the result of the condition.
 *
 */
public class PolicyRuleEngineAction implements IAction
{
    private static Logger log = Logger.getLogger(PolicyRuleEngineAction.class );
    private static final String strAPPLY_CONDITIONALLY="3";
    private static final String strDelimeter="|";
    /**
     * This method is used to generate the Error messages if any after evaluating Coverage against the Rule Engine.
     * It selects the evaluated conditions based on the coverages status. Constructs the Error message
     * based on the result of the condition.
     */
    public Node execute(Node coverageNode) throws TTKException
    {
       log.debug("Inside PolicyRuleEngineAction -- execute method");

        if(coverageNode.valueOf("@allowed").equals(strAPPLY_CONDITIONALLY) && coverageNode.
                valueOf("@selected").equals("YES"))
        {

            StringBuffer sbfActionMessage=new StringBuffer();
            sbfActionMessage.append(((Element)coverageNode.selectSingleNode("./action")).
                    valueOf("@Message")).append(" ");

            boolean bFail = false;
            List conditionList = coverageNode.selectNodes("./condition");
            for(int i=0; i<conditionList.size();i++)
            {
                Element elmtCondition = (Element) conditionList.get(i);
                if(elmtCondition.valueOf("@result").equalsIgnoreCase("fail"))
                {
                    bFail=true;
                    Element eleDisplay = (Element)elmtCondition.selectSingleNode("./display");

                    sbfActionMessage.append(eleDisplay.valueOf("@prelabel")).append(" ");
                    if(eleDisplay.valueOf("@lookup").equals(""))
                    {
                        sbfActionMessage.append(elmtCondition.valueOf("@fieldData")).append(" ");
                    }//end of if(eleDisplay.valueOf("@lookup").equals(""))
                    else
                    {
                        StringTokenizer stFieldValues=new StringTokenizer(elmtCondition.valueOf("@fieldData"),
                                strDelimeter);
                        while(stFieldValues.hasMoreTokens())
                        {
                            sbfActionMessage.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),
                                    eleDisplay.valueOf("@lookup")));
                            if(stFieldValues.countTokens()>0)
                                sbfActionMessage.append(", ");
                        }//end of while(stFieldValues.hasMoreTokens())
                        sbfActionMessage.append(" ");
                    }//end of else

                    sbfActionMessage.append(RuleConfig.getLookupText("unit",elmtCondition.valueOf("@unit"))).append(" ");
                    sbfActionMessage.append(RuleConfig.getOperatorText(elmtCondition.valueOf("@opType"),
                            elmtCondition.valueOf("@op"))).append(" ");
                    if(eleDisplay.valueOf("@lookup").equals(""))
                    {
                        sbfActionMessage.append(elmtCondition.valueOf("@value")).append(" ");
                    }//end of if(eleDisplay.valueOf("@lookup").equals(""))
                    else
                    {
                        StringTokenizer stFieldValues=new StringTokenizer(elmtCondition.valueOf("@value"),
                                strDelimeter);
                        while(stFieldValues.hasMoreTokens())
                        {
                            sbfActionMessage.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),
                                    eleDisplay.valueOf("@lookup")));
                            if(stFieldValues.countTokens()>0)
                                sbfActionMessage.append(", ");
                        }//end of while(stFieldValues.hasMoreTokens())
                        sbfActionMessage.append(" ");
                    }//end of else
                    sbfActionMessage.append(RuleConfig.getLookupText("unit",elmtCondition.
                            valueOf("@unit"))).append(".\n");
                }//end of if(elmtCondition.valueOf("@result").equalsIgnoreCase("fail"))
            }//end of for(int i=0; i<conditionList.size();i++)
            if(!bFail)
                ((Element)coverageNode.selectSingleNode("./action")).addAttribute("Message","OK");
            else
                ((Element)coverageNode.selectSingleNode("./action")).addAttribute("Message",sbfActionMessage.toString());
        }//end of if(coverageNode.valueOf("@allowed").equals(strAPPLY_CONDITIONALLY))
        return coverageNode;
    }//end of execute(Node coverageNode)
}//end of PolicyRuleEngineAction
