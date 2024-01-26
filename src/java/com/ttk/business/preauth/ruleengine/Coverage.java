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

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.common.exception.TTKException;

/**
 * This class is used to execute the given coverage against the Rule Engine.
 * It selects the conditions to be evaluated based on the coverages status. Executes the
 * rule against them. calculates the CL factor for the Coverage and returns the
 * Coverage node with the updated results to called metod.
 *
 */
public class Coverage {

    private static Logger log = Logger.getLogger( Coverage.class);

    //declaraion of the constants used
    private static final String strPAY="1";
    private static final String strDONT_PAY="2";
    private static final String strPAY_CONDITIONALLY="3";
    private static final String strAPPLY="1";
    private static final String strDONT_APPLY="2";
    private static final String strAPPLY_CONDITIONALLY="3";

    Node coverageNode = null;

    /**
     * Constructor for initialzing the coverage
     * @param coverageNode
     */
    public Coverage(Node coverageNode)
    {
        this.coverageNode = coverageNode;
    }//end of Coverage(Node coverageNode)

    /**
     * This method evaluates required Conditions of the current coverage
     * based on the allowed status of coverage
     * Calculates the Confidence Level  of the Coverage in terms of the percentage
     * and updates it in clpercentage attribute of the Coverage
     *
     * @return  coverageNode returns updated coverage Node
     */
    public Node evaluate()
    {
        List conditionList=null;

        //get the allowed status of Coverage
        String strCvrgAllowed=coverageNode.valueOf("@allowed");

        if(strCvrgAllowed.equals(strPAY)|| strCvrgAllowed.equals(strAPPLY))
        {
            //get only the mandatory conditions  and evaluate them
            conditionList = coverageNode.selectNodes("./condition[@mandatory='YES']");
        }//end of if(strCvrgAllowed.equals(strPAY)|| strCvrgAllowed.equals(strAPPLY))

        else if (strCvrgAllowed.equals(strPAY_CONDITIONALLY)||strCvrgAllowed.equals(strAPPLY_CONDITIONALLY))
        {
            //evaluate all the conditions
            conditionList = coverageNode.selectNodes("./condition");
        }//end of else if (strCvrgAllowed.equals(strPAY_CONDITIONALLY)||strCvrgAllowed.equals(strAPPLY_CONDITIONALLY))

        else if((strCvrgAllowed.equals(strDONT_PAY) || strCvrgAllowed.equals(strDONT_APPLY)) &&
                !coverageNode.valueOf("@id").equals("cvg.28.1"))
        {
            //if coverage status is don't pay or dont apply make CL factor as Zero
            ((Element)(coverageNode)).addAttribute("clpercentage","0");

            return coverageNode;
        }//end of else if(strCvrgAllowed.equals(strDONT_PAY) || strCvrgAllowed.equals(strDONT_APPLY))

        //Evaluate the selected Conditions in Rule Engine
        if(conditionList!=null && conditionList.size()>0)
        {
            for(int i=0; i<conditionList.size(); i++)
            {
                Node conditionNode = (Node) conditionList.get(i);
                Condition condition = new Condition(conditionNode);
                condition.evaluate();
            }//end of for(int i=0; i<conditionList.size(); i++)
        }//end of if(conditionList!=null && conditionList.size()>0)
//      call the algorithm to calculate the CLFactor of coverage
        String strActionName = ((Element)coverageNode.selectSingleNode("./action")).valueOf("@name");
        ActionFactory actionFactory = new ActionFactory();

        // getting the action attribute name from the xml
        IAction iaction = actionFactory.getActionFactory(strActionName);
        try{
            if(iaction !=null)
                coverageNode = iaction.execute(coverageNode);
        }catch(TTKException ttk)
        {
            log.error("Error occured while evaluating Coverage :"+coverageNode.valueOf("@id"));
        }
        return coverageNode;
    }//end of evaluate()
}//end of Coverage.java
