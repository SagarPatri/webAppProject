/**
 * @ (#) ComputeOverallConfidenceLevel.java Oct 9, 2006
 * Project      : TTK HealthCare Services
 * File         : ComputeOverallConfidenceLevel.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Oct 9, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.business.preauth.ruleengine;

import java.util.List;

import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.common.exception.TTKException;

/**
 * This class will compute the Overall Confidence Level for the Pre-Auth/Claim
 * based on the confidence Levels of the Coverage.
 * @Logic:  Overall confidence level is average of all the evaluated coverage confidence levels
 *
 */
public class ComputeOverallConfidenceLevel implements IAction{

    public Node execute(Node clausesNode) throws TTKException
    {
        //Check for the CL Factor of Global rules if the Global rules failed make CL Factor as Zero
        if(clausesNode.selectSingleNode("//clause[@id='cls.1']/coverage[@id='cvg.1.1' and @clpercentage!='100']")!=null)
        {
            ((Element)(clausesNode)).addAttribute("clpercentage","0");
            return clausesNode;
        }//end of if

        List coverageList = clausesNode.selectNodes("//coverage[@selected='YES']");
        int iCounter=0;
        int iCLFactorSum=0;
        int iClFactor=0;
        int iOverAllCLFactor=0;
        for(int i=0; i<coverageList.size();i++)
        {
            Element eleCoverage = (Element) coverageList.get(i);
            if(!eleCoverage.valueOf("@clpercentage").equals(""))
            {
                try
                {
                    iClFactor=Integer.parseInt(eleCoverage.valueOf("@clpercentage"));
                }//end of try
                catch(NumberFormatException ne)
                {
                    iClFactor=0;
                }//end of catch(NumberFormatException ne)
                iCLFactorSum=iCLFactorSum+iClFactor;
                iCounter++;
            }//end of if(!eleCoverage.valueOf("@clpercentage").equals(""))
        }//end of for(int i=0; i<coverageList.size();i++)
        try
        {
            iOverAllCLFactor=Math.round((iCLFactorSum/iCounter));
        }//end of try
        catch(ArithmeticException ae)
        {
            iOverAllCLFactor=0;
        }//end of catch(NumberFormatException ne)
        ((Element)(clausesNode)).addAttribute("clpercentage",String.valueOf(iOverAllCLFactor));
        return clausesNode;
    }//end of execute(Node clausesNode)
}//end of ComputeOverallConfidenceLevel.java
