/**
 * @ (#) RuleBenefitsDisplay.java NOV 15, 2017
 * Project      : TTK HealthCare Services
 * File         : RuleBenefitsDisplay.java
 * Author       : Nagababu K
 * Company      : VIDAL
 * Date Created : NOV 15, 2017
 *
 * @author       :  Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.administration;

//import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;






import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.common.RulesFieldsLookup;
import com.ttk.common.TTKCommon;

//import com.ttk.common.TTKCommon;

/**
 * This method will checks whether clauses/coverages ot conditions defined or not
 *  for the product/Policy Rules and if any one of the aboce is not defined.
 *  it will show that node.
 */
public class RuleBenefitsConfiguration extends TagSupport{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger log = Logger.getLogger( RuleBenefitsConfiguration.class );

    /**
     * This method will be executed when customised tag begins
     * @return int
     * @throws JspException
     */
    public int doStartTag() throws JspException
    {
        try
        {
            log.info("Inside RuleBenefitsConfiguration Tag library.............");
            
            
            JspWriter out = pageContext.getOut(); //Writer object to write the file
            
           LinkedHashMap<String, Node> ruleBenefitNodeList=(LinkedHashMap<String, Node>)pageContext.getServletContext().getAttribute("RuleBenefitNodeList");
           if(ruleBenefitNodeList!=null) {
               String strBenefit=pageContext.getRequest().getParameter("benefitID");  
               String strConfType=pageContext.getRequest().getParameter("confType");  
        
               Document benefitDoc=null;
        	   Node benefitNode=ruleBenefitNodeList.get(strBenefit);
        	   
        	    benefitDoc=(Document)pageContext.getRequest().getAttribute("CurtNodeConfDtls");
        	  
        	    String styleDisplayType=" style=\"display:;\" ";
        	    String dispCTV="";
        	    
                 if(benefitDoc!=null)dispCTV=benefitDoc.getRootElement().valueOf("@condType");
        	 
                 //if("1".equals(dispCTV)||"2".equals(dispCTV))styleDisplayType=" style=\"display:none;\" ";
        	   
        	   if(benefitNode!=null) {
        		   out.println("<input type=\"hidden\" id=\"benefitID\" name=\"benefitID\" value=\""+strBenefit+"\"/>");
        		   out.println("<fieldset>");
        		   out.println("<legend>"+benefitNode.valueOf("@name")+"</legend>");//getCondetionSelectBox(dispCTV)
        		   
            	out.println("<table"+styleDisplayType+" id=\"mbdtID\"  align=\"center\" width=\"100%\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\">");
                
            	List<Node> sbNodes=benefitNode.selectNodes(".//sub-benefit");
            	
            	
            	for(Node sbNode:sbNodes){
            		
            		String strSBID=sbNode.valueOf("@id");
            		String topHeading=sbNode.valueOf("@topHeading");
            		if(topHeading!=null&&!"".equals(topHeading)){
            			out.println("<tr>");
            			out.println("<td class=\""+sbNode.valueOf("@cssClass")+"\" colspan=\"2\" width=\"100%\">"+topHeading+"</td>");
            			out.println("</tr>");
            		}
            		
            		out.println("<tr style=\"background-color:#EBEDF2;\">");
            		
            		out.println("<td class=\"formLabelBold\" width=\"70%\">"+sbNode.valueOf("@name")+"</td>");
            		
            		out.println("<td align=\"right\" width=\"30%\" class=\"rulesRadioOptions\" nowrap>");
            		
            		Node conTypeNode=sbNode.selectSingleNode("./condition-type");
            		
            		String strCondTypeDefault="";
            		
            			String strLookup=conTypeNode.valueOf("@lookup");
            			
            			
            			if(benefitDoc!=null){
            				Node condTypeNode=benefitDoc.selectSingleNode("//sub-benefit[@id ='"+strSBID+"']/condition-type");
            				if(condTypeNode!=null){
            				strCondTypeDefault=condTypeNode.valueOf("@default"); 
            				}
            				else strCondTypeDefault=conTypeNode.valueOf("@default");
            			
            			}
            			else strCondTypeDefault=conTypeNode.valueOf("@default");
            			
            			
            			out.print(displayConditionType(strSBID,strLookup,strCondTypeDefault));    	
            		
            		
            		out.println("</td>");            		
            		out.println("</tr>");   
            		
            		if("3".equals(strCondTypeDefault))
            			out.println("<tr id=\"SB"+strSBID+"\" style=\"display:;\">");
            		else
            		out.println("<tr id=\"SB"+strSBID+"\" style=\"display:none;\">");
            		
            		List<Node> conNodes=sbNode.selectNodes(".//condition");
            		
            		out.println("<td colspan=\"2\">");  
            		
        			out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        		
        			out.println("<tr>");
        			out.println("<td class=\"order\" nowrap>");
        			out.println("<ol>");
        			
            		for(Node conNode:conNodes){
            			
            			String strConID=conNode.valueOf("@id");
            			String strSerNO=conNode.valueOf("@serNO");
            			String cssClass=conNode.valueOf("@cssClass");//"liDiv";
            			 
            			if(cssClass==null||"".equals(cssClass))
            				cssClass="liDiv";
            			
            			if("N".equals(strSerNO))out.println("<div class=\""+cssClass+"\">");
            			else out.println("<li>");
            			List<Node> fieldNodes=conNode.selectNodes(".//field");
            			
            			for(Node fieldNode:fieldNodes){
            				String strDefaultValue=fieldNode.valueOf("@default");
            			
            				if(benefitDoc!=null&&"3".equals(strCondTypeDefault)){
            					Node confFieldNode=benefitDoc.selectSingleNode("//sub-benefit[@id = '"+strSBID+"']/condition[@id = '"+strConID+"']/field[@name = '"+fieldNode.valueOf("@name")+"']");
            				
            					if(confFieldNode!=null&&TTKCommon.checkNull(confFieldNode.valueOf("@default")).length()>0)strDefaultValue=confFieldNode.valueOf("@default");         					
            					
            				}
     
            				out.print(displayField(strSBID,strConID,fieldNode,strDefaultValue));
            			}
            			
            			if("N".equals(strSerNO))out.println("</div>");
            			else  out.println("</li>");
            		}
            		out.println("</ol>");
            		out.println("</td>");            		
            		out.println("</tr>"); 
            		out.println("</table>"); 
            		out.println("</td>"); 
            		out.println("</tr>");
            		
            	}//for(Node sbNode:sbNodes){
            	
            	
              //  Set<Entry<String, String>>  beneSet=ruleBenefitList.entrySet();
                
                out.println("</table>");
        	   }//if(benefitNode!=null) {
            }//if(ruleBenefitNodeList!=null) {
       
       
        }//end of try
        catch(Exception exp)
        {
            exp.printStackTrace();
            throw new JspException("Error: in RuleBenefitsConfiguration Tag Library!!!" );
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

    private String displayConditionType(String strSbDI,String strLookup,String strDefault){
    	String conType="";
    	
    	
			
    		if(strLookup!=null&&strLookup.length()>1){
    			conType=RulesFieldsLookup.getConditionRadioBoxes(strLookup, strSbDI, strDefault);
    	}//if(conTypeNode!=null){
    	
    	return conType;	
    }

 private String displayField(String strSBID,String strConID,Node fieldNode,String strDefaultValue){
 	
	 StringBuilder stringBuilder=new StringBuilder();
	 	
	String strLookup=fieldNode.valueOf("@lookup");
	String strName=fieldNode.valueOf("@name");
	String strID=strSBID+"-"+strConID+"-"+strName;
	strName=strSBID+strConID+strName;	
	stringBuilder.append(fieldNode.valueOf("@prelabel"));
	if("select".equals(fieldNode.valueOf("@ctrType"))){
 		if(strLookup!=null&&strLookup.length()>1){
 			
 			stringBuilder.append(RulesFieldsLookup.getSelectBox(strLookup, strName, fieldNode.valueOf("@class"), strID, strDefaultValue,fieldNode.valueOf("@jsCall")));
 			
 	}//if(conTypeNode!=null){
 	else{
 		stringBuilder.append(RulesFieldsLookup.populateSelectBox( strName, fieldNode.valueOf("@class"), strID, strDefaultValue,fieldNode.valueOf("@optionValues"),fieldNode.valueOf("@optionLabels"),fieldNode.valueOf("@jsCall")));
 		
 	}
	}else if("text".equals(fieldNode.valueOf("@ctrType"))){
		stringBuilder.append("<input type=\"text\"").append(" name=\"").append(strName).append("\"");
		stringBuilder.append(" id=\"").append(strID).append("\"");
		
		String strCalss=fieldNode.valueOf("@class");
		strCalss=(strCalss==null||strCalss.length()<1?"textBoxForRule textBoxTiny":strCalss);
		
		stringBuilder.append(" class=\"").append(strCalss).append("\"");
		
		stringBuilder.append(" value=\"").append(strDefaultValue).append("\" ");
		stringBuilder.append(fieldNode.valueOf("@jsCall"));
		if("true".equals(fieldNode.valueOf("@readonly")))stringBuilder.append(" readonly");
		stringBuilder.append(">");
 	}else if("hidden".equals(fieldNode.valueOf("@ctrType"))){
		stringBuilder.append("<input type=\"hidden\"").append(" name=\"").append(strName).append("\"");	
		stringBuilder.append(" id=\"").append(strID).append("\"");
		stringBuilder.append(" value=\"").append(strDefaultValue).append("\" ");
		
		stringBuilder.append(">");
 	}else if("img".equals(fieldNode.valueOf("@ctrType"))){
 		stringBuilder.append("<a href=\"#\" ").append(fieldNode.valueOf("@jsCall")).append(">");
		stringBuilder.append("<img src=\"/ttk/images/EditIcon.gif\" width=\"16\" height=\"16\" alt=\"");
		stringBuilder.append(fieldNode.valueOf("@alt")).append("\"");
		stringBuilder.append(" title=\""+fieldNode.valueOf("@alt")).append("\"");
		
		stringBuilder.append(" border=\"0\" align=\"absmiddle\"></a>");
 	}
	
 	stringBuilder.append(fieldNode.valueOf("@postlabel"));
 	//log.info("Static data:"+stringBuilder.toString());
 	return stringBuilder.toString();	
 }
 public  String getCondetionSelectBox(String selectvalue){
		StringBuilder selBoxBuild=new StringBuilder();
		selBoxBuild.append("&nbsp;&nbsp;");
		selBoxBuild.append("<select name=\"").append("benefitPayType").append("\"");
		
		
	selBoxBuild.append(" class=\"").append("selectBoxForRule").append("\"");
	
	selBoxBuild.append(" onchange=\"").append("checkMainBenefitType(this);").append("\"");
		
		selBoxBuild.append(" id=\"").append("benefitPayTypeID").append("\">");
		
		String options="";
		
		
		if("1".equals(selectvalue))options="<option value=\"1\" selected>Pay</option><option value=\"2\" >Don't Pay</option><option value=\"3\">Pay Conditionally</option>";
		else if("2".equals(selectvalue))options="<option value=\"1\">Pay</option><option value=\"2\" selected>Don't Pay</option><option value=\"3\">Pay Conditionally</option>";
		else options="<option value=\"1\">Pay</option><option value=\"2\">Don't Pay</option><option value=\"3\" selected>Pay Conditionally</option>";
		
		selBoxBuild.append(options);
		
		selBoxBuild.append("</select>");
		return selBoxBuild.toString();
	
	}
}//end of RuleBenefitsDisplay.java
