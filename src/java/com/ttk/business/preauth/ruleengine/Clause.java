/**
 * @ (#) Clause.java Jul 09, 2006
 * Project      : TTK HealthCare Services
 * File         : Clause.java
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

/**
 * This class is used to evaluate each Clause in Rule Engine.
 * It evaluates all the coverages of the selected clause.
 *
 */
public class Clause {

    Node clauseNode = null;

    /**
     * Constructor for initialzing the Clause
     * @param clauseNode
     */
    public Clause(Node clauseNode)
    {
        this.clauseNode = clauseNode;
    }//end of Clause(Node clauseNode)

    /**
     * This method evaluates the all the coverages in the Clause updates the
     * document and returns updated node to called method.
     *
     * @return clauseNode Document updated clause with the results
     */
    public Node evaluate()
    {
        //get the coverage nodes of the selected clause to execute the results
        List coverageList = clauseNode.selectNodes("./coverage");
        if(coverageList!=null && coverageList.size()>0)
        {
            for(int i=0; i<coverageList.size(); i++)
            {
                Node coverageNode = (Node) coverageList.get(i);
                Coverage coverage = new Coverage(coverageNode);
                coverage.evaluate();
            }//end of for(int i=0; i<coverageList.size(); i++)
        }//end of if(coverageList!=null && coverageList.size()>0)
        return clauseNode;
    }//end of evaluate()
 // Changes on Jan 7th 4:14 2012 KOC1099 
    /**
     * This method adds Remark Node to the Clause Node updates the
     * document and returns updated node to called method.
     *
     * @return clauseNode Document updated clause with the Remarks Node
     * 
     */
    public  Node addRemarksNode(Node clauseNode)
	{
    	if(clauseNode.selectSingleNode("./remarks")!=null)
    	{
    	clauseNode.selectSingleNode("./remarks").detach();
    	((Element)clauseNode).normalize();
    	}
    	((Element)clauseNode).normalize();
    	 	   	Element eleremarks=((Element) (clauseNode)).addElement("remarks");
			String cls=clauseNode.valueOf("@id");
			String clsnumber=cls.substring(cls.indexOf("."), cls.length())+(".1");
			eleremarks.addAttribute("id","rmk"+clsnumber+" ");
		    eleremarks.addAttribute("mandatory","Yes");
		    eleremarks.addAttribute("value","~");
			String dispNuumber=clsnumber+(".1");
			Element eledisplay=eleremarks.addElement("display");
			eledisplay.addAttribute("id", "dsp"+dispNuumber+"");
			eledisplay.addAttribute("target", "value,1");
			eledisplay.addAttribute("prelabel","Remarks");
			eledisplay.addAttribute("control","textArea");
			eledisplay.addAttribute("default","");
			eledisplay.addAttribute("postlabel","");
			eledisplay.addAttribute( "jscall","");
			eleremarks.normalize();
		//<display id="dsp.1.1.1" target="value,1" prelabel="Remarks" control="textArea" type="textArea" default="" postlabel="" jscall=""/>	
		//  <remarks id="rmk.1.1 " mandatory="Yes" value="~"><display id="dsp.1.1.1" target="value,1" prelabel="Remarks" control="textArea" default="" postlabel="" jscall=""/></remarks></clause>
		/*<remarks id="rmk.1.1" mandatory="YES" value="~"        <display id="dsp.1.1.1" target="value,1" prelabel="Remarks" control="textArea" type="textArea" default="" postlabel="" jscall=""/>
      </remarks>*/
			
			((Element)clauseNode).normalize();
			
			//clauseNode.
		return clauseNode;
		

	}//end of addRemarksNode method
    // Changes on Jan 7th 4:14 2012 KOC1099 
    
}//end of Clause.java
