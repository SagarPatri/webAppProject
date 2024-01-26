/**
 * @ (#) Coverage.java Jul 09, 2006
 * Project      : TTK HealthCare Services
 * File         : Coverage.java
 * Author       : Unni V M
 * Company      : Span Systems Corporation
 * Date Created : Jul 09, 2006
 *
 * @author       : Unni V M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.preauth.ruleengine;


import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.common.exception.TTKException;

public class RuleEngineAction implements IAction
{

//  declaraion of the constants used
    private static final String strPAY="1";
//    private static final String strDONT_PAY="2";
    private static final String strPAY_CONDITIONALLY="3";
    private static final String strAPPLY="1";
//    private static final String strDONT_APPLY="2";
    private static final String strAPPLY_CONDITIONALLY="3";

    /**
     * This method calculates the clfactor for Coverage node in percentage based on
     * the results of the Condtion node
     *
     * @param coverageNode Node Coverage node to be Evaluated
     * @return coverageNode updated Coverage node with Results
     * @throws TTKException if any run time Exception occures
     *
     * @Logic :if the result of a condition node is pass, then the clfactor for the same will be the
     *         default clfactor mentioned in the clfactor attribute.If the rule is not defined for the Condition
     *         then both new and default clfacotr will be zero. If the result is fail, then the
     *         new clfactor will be 0. Take all these newly calculated clfactors and old clfactors apply the formula
     *         (sum of all the new clfactor after evaluation)/(sum ofthe original clfactor) * 100.
     *         That will give the 'clfactor' (confidence level) for that particular coverage node in percentage
     */
    public Node execute(Node coverageNode) throws TTKException
    {
        //Check if All the mandatory Condtions are passed
        if(coverageNode.selectSingleNode("./condition[@mandatory='YES' and @result!='pass']")!=null)
        {
            //Make CL Factor as Zero as one of the madatory Conditions Failed
            ((Element)(coverageNode)).attribute("clpercentage").setValue("0");
            return coverageNode;
        }//end of if(coverageNode.selectSingleNode("./condition[@mandatory='YES' and @result!='pass')")!=null)*/

        //get the allowed status of Coverage
        String strCvrgAllowed=coverageNode.valueOf("@allowed");

        List conditionList = null;
        if(strCvrgAllowed.equals(strPAY)|| strCvrgAllowed.equals(strAPPLY))
        {
            //get only the mandatory conditions to calculate CL Factor
            conditionList = coverageNode.selectNodes("./condition[@mandatory='YES']");
        }//end of if(strCvrgAllowed.equals(strPAY)|| strCvrgAllowed.equals(strAPPLY))
        else if (strCvrgAllowed.equals(strPAY_CONDITIONALLY)||strCvrgAllowed.equals(strAPPLY_CONDITIONALLY))
        {
            //get all the conditions to calculate CL Factor
            conditionList = coverageNode.selectNodes("./condition");
        }//end of else if (strCvrgAllowed.equals(strPAY_CONDITIONALLY)||strCvrgAllowed.equals(strAPPLY_CONDITIONALLY))

        Element eleCondition=null;
        int iConfidenceLevel = 0;

        float iTotConfidenceLevel=0;
        float iCalcConfidenceLevel=0;

        if(conditionList!=null && conditionList.size()>0)
        {
            for(int i=0; i<conditionList.size();i++)
            {
                eleCondition = (Element) conditionList.get(i);
                try
                {
                    iConfidenceLevel = Integer.parseInt(eleCondition.valueOf("@clfactor"));
                }//end of try
                catch(NumberFormatException e)
                {
                    iConfidenceLevel=0;
                }//end of catch(NumberFormatException e)

                if(eleCondition.valueOf("@result").equalsIgnoreCase("pass"))
                {
                    //if rule passed make CL factor after evaluation same as original CLFactor
                    iCalcConfidenceLevel=iCalcConfidenceLevel+iConfidenceLevel;
                    iTotConfidenceLevel=iTotConfidenceLevel+iConfidenceLevel;
                }//end of if(eleCondition.valueOf("@result").equalsIgnoreCase("pass"))
                else if(eleCondition.valueOf("@result").equalsIgnoreCase("Rule undefined"))
                {
                    continue;
                }//end of else if(eleCondition.valueOf("@result").equalsIgnoreCase("Rule undefined"))
                else
                {
                    iTotConfidenceLevel=iTotConfidenceLevel+iConfidenceLevel;
                }//end of else
            }//end of for(int i=0; i<conditionList.size();i++)

            //now we are ready to caculate the clfactor of the coverage node
            if(iTotConfidenceLevel>0)
            {
                //formula for calculating the clfactor is
                // Sum of clfactor of all the conditions in a coverage (after evaluation)
                // ---------------------------------------------------------------------------   X 100
                // Total sum of clfactor of all the conditions in a coverage
                int iCLPercentage=Math.round((iCalcConfidenceLevel/iTotConfidenceLevel) * 100);
                ((Element)(coverageNode)).addAttribute("clpercentage",String.valueOf(iCLPercentage));
            }//end of if(iTotConfidenceLevel>0)
        }//end of if(conditionList!=null && conditionList.size()>0)
        else
        {
           if(strCvrgAllowed.equals(strPAY)||strCvrgAllowed.equals(strAPPLY))
           {
               ((Element)(coverageNode)).addAttribute("clpercentage","100");
           }//end of if(strCvrgAllowed.equals(strPAY)||strCvrgAllowed.equals(strAPPLY))
        }//end of else
        return coverageNode;
    }//end of execute() method
}//end of RuleEngineAction.java
