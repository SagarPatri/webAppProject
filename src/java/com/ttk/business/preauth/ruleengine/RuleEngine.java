/**
 * @ (#) RuleEngine.java Jul 09, 2006
 * Project      : TTK HealthCare Services
 * File         : RuleEngine.java
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

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.common.exception.TTKException;
import com.ttk.common.security.RuleXMLHelper;


/**
 * This class is used to Execute the RuleEngine against the given rule.
 * in Enrollment,Pre-Authorisation and Claims.
 * It calculates the over all Confidence level after executing all the rules.
 *
 */
public class RuleEngine {

    /**
     * This method takes the rule xml file as input and executes it.
     * updates the result in xml file and returns the updated xml.
     *
     * @param  ruleDoc  Document object containg the rules to be Executed.
     * @return ruleDoc Document updated with results.
     * @throws TTKException
     */
    public Document applyClause(Document ruleDoc) throws TTKException
    {
        if(ruleDoc !=null)
        {
            //clear the current results of the document before executing rule
            ruleDoc=new RuleXMLHelper().clearRuleResults(ruleDoc);

            //get the clauses to execute rule for each Clause
            List clauseList = ruleDoc.selectNodes("//clauses/clause");
            if(clauseList !=null && clauseList.size()>0)
            {
                for(int i=0; i<clauseList.size(); i++)
                {
                    Node clauseNode = (Node) clauseList.get(i);
                    Clause clause = new Clause(clauseNode);
                 //changes for adding remarks node  KOC 1099
                  clause.addRemarksNode(clauseNode);
                    //changes for adding remarks node  KOC 1099
                    clause.evaluate();
                }//end of for(int i=0; i<clauseList.size(); i++)
            }//end of if(clauseList !=null && clauseList.size()>0)

            Node  rootNode=ruleDoc.selectSingleNode("//clauses");
            ActionFactory actionFactory = new ActionFactory();
            IAction iaction = actionFactory.getActionFactory("computeoverallclp");
            if(iaction !=null)
            {
                //here call the algorithm to calculate OverAll the CLFactor
                rootNode = iaction.execute(rootNode);
            }//end of if(iaction !=null)
         }//end of if(ruleDoc !=null)
ruleDoc.normalize();
        return ruleDoc;
    }//end of applyClause(Document ruleDoc)
}//end of RuleEngine.java
